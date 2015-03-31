CREATE OR REPLACE PACKAGE PTOVENTA."FASA_INT_CONFIRMACION" is

  --  g_cNumProdQuiebre INTEGER := 750;

  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

  C_CANTIDAD_ARCHIVOS INTEGER := 5;

  PROCEDURE P_OPERA_DIF_RECEP_FASA(cFechaProceso    in char,
                                   cFechaProcesoFin in char);
  PROCEDURE P_CREA_AUX_DIF_RECEP(CodiLoca_in   IN varchar2,
                                 Id_Entrega_in IN varchar2);

  PROCEDURE P_CREA_INT_CONFIRMACION(CodiLoca_in   IN varchar2,
                                    Id_Entrega_in IN varchar2,
                                    fchRecepcion  in date);

  PROCEDURE INT_TXT_CONFIRMACION;
  --Fecha       Usuario   Comentario
  --10/07/2006  ERIOS     Creacion
  procedure P_ACTUALIZA_LOTE;

  PROCEDURE P_COMPLETA_SOBRANTE_ADICIONAL(CodiLoca_in   IN varchar2,
                                          Id_Entrega_in IN varchar2);

end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."FASA_INT_CONFIRMACION" is

  PROCEDURE P_OPERA_DIF_RECEP_FASA(cFechaProceso    in char,
                                   cFechaProcesoFin in char) AS
  begin

    for listaRecep in (
                        SELECT COD_LOCAL, NRO_RECEP,rm.fec_recep  FECHA_RECEPCION
                          FROM LGT_RECEP_MERCADERIA RM
                         WHERE RM.ESTADO = 'T'
                           AND RM.FEC_CREA_RECEP  >= to_date('07/10/2013', 'dd/mm/yyyy')
                           AND RM.FEC_CREA_RECEP  between
                                to_date(cFechaProceso, 'dd/mm/yyyy') and
                                to_date(cFechaProcesoFin, 'dd/mm/yyyy') + 1 -
                                1 / 24 / 60 / 60
                           AND RM.IND_AFEC_RECEP_CIEGA = 'S'
                           and  FEC_PROC_INT is null
                           --and rm.nro_recep = '0000000007'
                        order by 1 asc, 2 asc
                        ) loop

       begin
          P_CREA_AUX_DIF_RECEP(listaRecep.COD_LOCAL, listaRecep.NRO_RECEP);
          P_CREA_INT_CONFIRMACION(listaRecep.COD_LOCAL,
                                  listaRecep.NRO_RECEP,
                                  listaRecep.FECHA_RECEPCION);

         update LGT_RECEP_MERCADERIA a
         set    a.FEC_PROC_INT = sysdate
         where  a.COD_LOCAL = listaRecep.COD_LOCAL
         and    a.NRO_RECEP = listaRecep.NRO_RECEP;

         --commit;

        /*exception
          when others then
            dbms_output.put_line('ERROR:' || listaRecep.COD_LOCAL || '-' ||
                                 listaRecep.NRO_RECEP||'--- '||sqlerrm);
            rollback;*/
        end;


    end loop;

    -- genera archivo  de lo procesado
    int_txt_confirmacion;

  END;

  PROCEDURE P_CREA_AUX_DIF_RECEP(CodiLoca_in   IN varchar2,
                                 Id_Entrega_in IN varchar2) AS

  begin

    DELETE V_DIF_TOTAL_FASA NOLOGGING;

      insert into V_DIF_TOTAL_FASA NOLOGGING
      (id_entrega, cod_producto, cod_local, ctd_entregado, ctd_conteo_fin, dif, tipo,
      posicion,num_entrega)
      SELECT Id_Entrega_in,va.PROD,CodiLoca_in,va.cant2 CANT_ENTREGADA,
             va.cant_contada CANT_CONTADA,abs(va.cant2-va.cant_contada) DIf,
             case
               when va.cant2-va.cant_contada > 0 then 'FALTANTE'
               when va.cant2-va.cant_contada < 0 then 'SOBRANTE'
               else 'X'
             end tipo,
             lpad(va.posicion,6,'0'),
             va.num_entrega
      FROM   (
              SELECT B.COD_PROD PROD,
                     SUM(B.CANT_ENVIADA_MATR) CANT2,
                     ' ' COD_BARRA,
                     SUM(B.CANT_CONTADA) CANT_CONTADA,
                     --B.NUM_ENTREGA NUM_ENTREGA,
                     b.posicion,
                     b.num_entrega
                FROM LGT_NOTA_ES_DET B
               WHERE B.COD_GRUPO_CIA = '001'
                 AND B.COD_LOCAL = CodiLoca_in
                 AND B.NUM_ENTREGA || B.NUM_NOTA_ES IN
                     (SELECT C.NUM_ENTREGA || C.NUM_NOTA_ES
                        FROM LGT_RECEP_ENTREGA C
                       WHERE C.COD_GRUPO_CIA = '001'
                         AND C.COD_LOCAL = CodiLoca_in
                         AND C.NRO_RECEP = Id_Entrega_in)
               GROUP BY B.COD_PROD, B.CANT_CONTADA, B.NUM_ENTREGA,
                        b.posicion,b.num_entrega
         )  va;
     --WHERE  ABS(CANT2 - CANT_CONTADA) > 0;

      insert into V_DIF_TOTAL_FASA NOLOGGING
      (id_entrega, cod_producto, cod_local, ctd_entregado, ctd_conteo_fin, dif, tipo)
    SELECT Id_Entrega_in,E.COD_PROD,CodiLoca_in,0, NVL(E.CANT_SEG_CONTEO, E.CANTIDAD),
           abs(NVL(E.CANT_SEG_CONTEO, E.CANTIDAD)),
           'SOBRANTE'
      FROM LGT_PROD_CONTEO E
     WHERE E.COD_GRUPO_CIA = '001'
       AND E.COD_LOCAL = CodiLoca_in
       AND E.NRO_RECEP = Id_Entrega_in
       AND E.COD_PROD NOT IN
           (SELECT F.COD_PROD
              FROM LGT_NOTA_ES_DET F, LGT_RECEP_ENTREGA G
             WHERE F.COD_GRUPO_CIA = '001'
               AND F.COD_LOCAL = CodiLoca_in
               AND G.NRO_RECEP = Id_Entrega_in
               AND F.COD_GRUPO_CIA = G.COD_GRUPO_CIA
               AND F.COD_LOCAL = G.COD_LOCAL
               AND F.NUM_NOTA_ES = G.NUM_NOTA_ES
               AND F.NUM_ENTREGA = G.NUM_ENTREGA
               AND F.SEC_GUIA_REM = G.SEC_GUIA_REM)
       AND NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) > 0;

  END;

  /* ************************************************************************** */
  PROCEDURE P_CREA_INT_CONFIRMACION(CodiLoca_in   IN varchar2,
                                    Id_Entrega_in IN varchar2,
                                    fchRecepcion  in date) AS

  begin
   dbms_output.put_line('CodiLoca_in>>'||CodiLoca_in);
   dbms_output.put_line('Id_Entrega_in>>'||Id_Entrega_in);
   delete AUX_INT_CONFIRMACION_GUIAS;
    INSERT INTO AUX_INT_CONFIRMACION_GUIAS NOLOGGING
      (Num_Guia,
       Num_Entrega,
       Num_Lote,
       Cod_Local,
       Cod_Producto,
       Diferencia2,
       Tipo,
       Ctd,
       factura_out,
       id_entrega,
       FECHA_RECEPCION,
       -------------------------------------
       TXT_01_CTA_CLIENTE,
       TXT_02_DEST,
       TXT_03_NOMB_DEST,
       TXT_04_DOC_VTA,
       TXT_05_CLASE_PEDIDO,
       TXT_06_FEC_CREA_PEDIDO,
       TXT_07_OC,
       TXT_08_NUM_ENTREGA,
       TXT_09_NUM_GUIA,
       TXT_10_FEC_CREA_ENTREGA,
       TXT_11_NUM_DOC_MAT_SM,
       TXT_12_FEC_CONTA_SM,
       TXT_13_NUM_FACTURA_SAP,
       TXT_14_FEC_FACT_SAP,
       TXT_15_CLASE_FACTURA,
       TXT_16_POS_FACTURA,
       TXT_17_MATERIAL,
       TXT_18_DESC_PROD,
       TXT_19_CTD_FACTURADA,
       TXT_20_UNIDAD_MEDIDA,
       TXT_21_LOTE,
       TXT_22_FEC_VENCIMIENTO
       -------------------------------------
       )
      SELECT FALTAN.NUM_GUIA,
             FALTAN.NUM_ENTREGA,
             FALTAN.NUM_LOTE,
             CodiLoca_in            AS "COD_LOCAL",
             FALTAN.COD_PRODUCTO,
             FALTAN.DIF,
             FALTAN.TIPO,
             FALTAN.DIF,
             FALTAN.NUM_FACTURA_SAP,
             Id_Entrega_in          AS "ID_ENTREGA",
             fchRecepcion,
             -------------------------------------
             TXT_01_CTA_CLIENTE,
             TXT_02_DEST,
             TXT_03_NOMB_DEST,
             TXT_04_DOC_VTA,
             TXT_05_CLASE_PEDIDO,
             TXT_06_FEC_CREA_PEDIDO,
             TXT_07_OC,
             TXT_08_NUM_ENTREGA,
             TXT_09_NUM_GUIA,
             TXT_10_FEC_CREA_ENTREGA,
             TXT_11_NUM_DOC_MAT_SM,
             TXT_12_FEC_CONTA_SM,
             TXT_13_NUM_FACTURA_SAP,
             TXT_14_FEC_FACT_SAP,
             TXT_15_CLASE_FACTURA,
             TXT_16_POS_FACTURA,
             TXT_17_MATERIAL,
             TXT_18_DESC_PROD,
             TXT_19_CTD_FACTURADA,
             TXT_20_UNIDAD_MEDIDA,
             TXT_21_LOTE,
             TXT_22_FEC_VENCIMIENTO
      -------------------------------------
        FROM (select T_F.ID_ENTREGA,
                     T_F.COD_PRODUCTO,
                     T_F.COD_LOCAL,
                     T_F.CTD_ENTREGADO,
                     T_F.CTD_CONTEO_FIN,
                     T_F.DIF AS "DIF_TOTAL",
                    /* CASE
                       WHEN T_F.BALANCE <= T_F.DIF THEN
                        T_F.CTD_ACUM_CONF
                       WHEN T_F.BALANCE > T_F.DIF THEN
                       --THEN T_F.DIF - T_F.PREVIO_BALANCE
                        case
                          when T_F.PREVIO_BALANCE < 0 then
                           T_F.DIF
                          else
                           T_F.DIF - T_F.PREVIO_BALANCE
                        end
                     --
                     END*/
                     0 AS "DIF",
                     T_F.TIPO,
                     T_F.NUM_GUIA,
                     T_F.NUM_ENTREGA,
                     T_F.NUM_LOTE,
                     T_F.NUM_FACTURA_SAP,
                     T_F.CTD_ACUM_CONF,
                     T_F.POSICION,
                     T_F.ORDEN,
                     T_F.BALANCE,
                     T_F.PREVIO_BALANCE,
                     case
                       when T_F.BALANCE <= T_F.DIF then
                        'S'
                       WHEN T_F.PREVIO_BALANCE IS NULL THEN
                        'S'
                       ELSE
                        CASE
                          when T_F.DIF - T_F.PREVIO_BALANCE < 0 THEN
                           'N'
                          when T_F.DIF - T_F.PREVIO_BALANCE <= T_F.CTD_ACUM_CONF THEN
                           'S'
                          ELSE
                           'N'
                        END
                     end IND_APLICA,
                     -------------------------------------
                     TXT_01_CTA_CLIENTE,
                     TXT_02_DEST,
                     TXT_03_NOMB_DEST,
                     TXT_04_DOC_VTA,
                     TXT_05_CLASE_PEDIDO,
                     TXT_06_FEC_CREA_PEDIDO,
                     TXT_07_OC,
                     TXT_08_NUM_ENTREGA,
                     TXT_09_NUM_GUIA,
                     TXT_10_FEC_CREA_ENTREGA,
                     TXT_11_NUM_DOC_MAT_SM,
                     TXT_12_FEC_CONTA_SM,
                     TXT_13_NUM_FACTURA_SAP,
                     TXT_14_FEC_FACT_SAP,
                     TXT_15_CLASE_FACTURA,
                     TXT_16_POS_FACTURA,
                     TXT_17_MATERIAL,
                     TXT_18_DESC_PROD,
                     TXT_19_CTD_FACTURADA,
                     TXT_20_UNIDAD_MEDIDA,
                     TXT_21_LOTE,
                     TXT_22_FEC_VENCIMIENTO
              -------------------------------------
                from (SELECT T_D.ID_ENTREGA,
                             T_D.COD_PRODUCTO,
                             T_D.COD_LOCAL,
                             T_D.CTD_ENTREGADO,
                             T_D.CTD_CONTEO_FIN,
                             T_D.DIF,
                             T_D.TIPO,
                             T_D.NUM_GUIA,
                             T_D.NUM_ENTREGA,
                             T_D.NUM_LOTE,
                             T_D.NUM_FACTURA_SAP,
                             T_D.CTD_ACUM_CONF,
                             T_D.POSICION,
                             T_D.ORDEN,
                             T_D.BALANCE,
                             -------------------------------------
                             TXT_01_CTA_CLIENTE,
                             TXT_02_DEST,
                             TXT_03_NOMB_DEST,
                             TXT_04_DOC_VTA,
                             TXT_05_CLASE_PEDIDO,
                             TXT_06_FEC_CREA_PEDIDO,
                             TXT_07_OC,
                             TXT_08_NUM_ENTREGA,
                             TXT_09_NUM_GUIA,
                             TXT_10_FEC_CREA_ENTREGA,
                             TXT_11_NUM_DOC_MAT_SM,
                             TXT_12_FEC_CONTA_SM,
                             TXT_13_NUM_FACTURA_SAP,
                             TXT_14_FEC_FACT_SAP,
                             TXT_15_CLASE_FACTURA,
                             TXT_16_POS_FACTURA,
                             TXT_17_MATERIAL,
                             TXT_18_DESC_PROD,
                             TXT_19_CTD_FACTURADA,
                             TXT_20_UNIDAD_MEDIDA,
                             TXT_21_LOTE,
                             TXT_22_FEC_VENCIMIENTO,
                             -------------------------------------
                             nvl(lag(T_D.BALANCE, 1)
                                 over(PARTITION BY T_D.COD_PRODUCTO ORDER BY
                                      T_D.COD_PRODUCTO,
                                      T_D.BALANCE),
                                 -1) AS PREVIO_BALANCE
                        FROM (select DET_FA.ID_ENTREGA,
                                     DET_FA.COD_PRODUCTO,
                                     DET_FA.COD_LOCAL,
                                     DET_FA.CTD_ENTREGADO,
                                     DET_FA.CTD_CONTEO_FIN,
                                     DET_FA.DIF,
                                     DET_FA.TIPO,
                                     DET_FA.NUM_GUIA,
                                     DET_FA.NUM_ENTREGA,
                                     DET_FA.NUM_LOTE,
                                     DET_FA.NUM_FACTURA_SAP,
                                     DET_FA.CTD_ACUM_CONF,
                                     DET_FA.POSICION,
                                     DET_FA.ORDEN,
                                     -------------------------------------
                                     DET_FA.TXT_01_CTA_CLIENTE,
                                     DET_FA.TXT_02_DEST,
                                     DET_FA.TXT_03_NOMB_DEST,
                                     DET_FA.TXT_04_DOC_VTA,
                                     DET_FA.TXT_05_CLASE_PEDIDO,
                                     DET_FA.TXT_06_FEC_CREA_PEDIDO,
                                     DET_FA.TXT_07_OC,
                                     DET_FA.TXT_08_NUM_ENTREGA,
                                     DET_FA.TXT_09_NUM_GUIA,
                                     DET_FA.TXT_10_FEC_CREA_ENTREGA,
                                     DET_FA.TXT_11_NUM_DOC_MAT_SM,
                                     DET_FA.TXT_12_FEC_CONTA_SM,
                                     DET_FA.TXT_13_NUM_FACTURA_SAP,
                                     DET_FA.TXT_14_FEC_FACT_SAP,
                                     DET_FA.TXT_15_CLASE_FACTURA,
                                     DET_FA.TXT_16_POS_FACTURA,
                                     DET_FA.TXT_17_MATERIAL,
                                     DET_FA.TXT_18_DESC_PROD,
                                     DET_FA.TXT_19_CTD_FACTURADA,
                                     DET_FA.TXT_20_UNIDAD_MEDIDA,
                                     DET_FA.TXT_21_LOTE,
                                     DET_FA.TXT_22_FEC_VENCIMIENTO,
                                     -------------------------------------
                                     SUM(DET_FA.CTD_ACUM_CONF) OVER(PARTITION BY DET_FA.COD_PRODUCTO ORDER BY DET_FA.ORDEN asc rows unbounded preceding) BALANCE
                                from (
                                      ---
                                      select TT.*,
                                              DET_GUIAS.NUM_GUIA,
                                              --DET_GUIAS.NUM_ENTREGA,
                                              DET_GUIAS.NUM_LOTE,
                                              DET_GUIAS.NUM_FACTURA_SAP,
                                              DET_GUIAS.CTD_ACUM_CONF,
                                              --DET_GUIAS.POSICION,
                                              -------------------------------------
                                              DET_GUIAS.TXT_01_CTA_CLIENTE,
                                              DET_GUIAS.TXT_02_DEST,
                                              DET_GUIAS.TXT_03_NOMB_DEST,
                                              DET_GUIAS.TXT_04_DOC_VTA,
                                              DET_GUIAS.TXT_05_CLASE_PEDIDO,
                                              DET_GUIAS.TXT_06_FEC_CREA_PEDIDO,
                                              DET_GUIAS.TXT_07_OC,
                                              DET_GUIAS.TXT_08_NUM_ENTREGA,
                                              DET_GUIAS.TXT_09_NUM_GUIA,
                                              DET_GUIAS.TXT_10_FEC_CREA_ENTREGA,
                                              DET_GUIAS.TXT_11_NUM_DOC_MAT_SM,
                                              DET_GUIAS.TXT_12_FEC_CONTA_SM,
                                              DET_GUIAS.TXT_13_NUM_FACTURA_SAP,
                                              DET_GUIAS.TXT_14_FEC_FACT_SAP,
                                              DET_GUIAS.TXT_15_CLASE_FACTURA,
                                              DET_GUIAS.TXT_16_POS_FACTURA,
                                              DET_GUIAS.TXT_17_MATERIAL,
                                              DET_GUIAS.TXT_18_DESC_PROD,
                                              DET_GUIAS.TXT_19_CTD_FACTURADA,
                                              DET_GUIAS.TXT_20_UNIDAD_MEDIDA,
                                              DET_GUIAS.TXT_21_LOTE,
                                              DET_GUIAS.TXT_22_FEC_VENCIMIENTO,
                                              -------------------------------------
                                              RANK() OVER(PARTITION BY TT.COD_PRODUCTO ORDER BY DET_GUIAS.CTD_ACUM_CONF ASC, DET_GUIAS.NUM_FACTURA_SAP, DET_GUIAS.POSICION) ORDEN
                                        from (select vs.*
                                                 from V_DIF_TOTAL_FASA vs
                                                where vs.tipo = 'X'
                                                  and vs.id_entrega =
                                                      Id_Entrega_in
                                                  and vs.cod_local = CodiLoca_in) TT,
                                              (select Id_Entrega_in as "ID_ENTREGA",
                                                      vDetGuia.Cod_Prod AS "COD_PRODUCTO",
                                                      CodiLoca_in as "COD_LOCAL",
                                                      vDetGuia.Tnumeentr as "NUM_GUIA",
                                                      vDetGuia.Tfactsap as "NUM_FACTURA_SAP",
                                                      vDetGuia.tnumelote as "NUM_LOTE",
                                                      vDetGuia.tnumeentr as "NUM_ENTREGA",
                                                      vDetGuia.cant_solic AS "CTD_ACUM_CONF",
                                                      ROWNUM AS "POSICION",
                                                      -------------------------------------
                                                      TXT_01_CTA_CLIENTE,
                                                      TXT_02_DEST,
                                                      TXT_03_NOMB_DEST,
                                                      TXT_04_DOC_VTA,
                                                      TXT_05_CLASE_PEDIDO,
                                                      TXT_06_FEC_CREA_PEDIDO,
                                                      TXT_07_OC,
                                                      TXT_08_NUM_ENTREGA,
                                                      TXT_09_NUM_GUIA,
                                                      TXT_10_FEC_CREA_ENTREGA,
                                                      TXT_11_NUM_DOC_MAT_SM,
                                                      TXT_12_FEC_CONTA_SM,
                                                      TXT_13_NUM_FACTURA_SAP,
                                                      TXT_14_FEC_FACT_SAP,
                                                      TXT_15_CLASE_FACTURA,
                                                      TXT_16_POS_FACTURA,
                                                      TXT_17_MATERIAL,
                                                      TXT_18_DESC_PROD,
                                                      TXT_19_CTD_FACTURADA,
                                                      TXT_20_UNIDAD_MEDIDA,
                                                      TXT_21_LOTE,
                                                      TXT_22_FEC_VENCIMIENTO
                                               -------------------------------------
                                                 from (select a.num_entrega AS tnumeentr,
                                                              a.Num_Factura_Sap AS tfactsap,
                                                              a.Num_Lote        AS tnumelote,
                                                              a.cod_prod,
                                                              -- dubilluz 27.08.2013
                                                              a.cant_solic ,
                                                              -------------------------------------
                                                              TXT_01_CTA_CLIENTE,
                                                              TXT_02_DEST,
                                                              TXT_03_NOMB_DEST,
                                                              TXT_04_DOC_VTA,
                                                              TXT_05_CLASE_PEDIDO,
                                                              TXT_06_FEC_CREA_PEDIDO,
                                                              TXT_07_OC,
                                                              TXT_08_NUM_ENTREGA,
                                                              TXT_09_NUM_GUIA,
                                                              TXT_10_FEC_CREA_ENTREGA,
                                                              TXT_11_NUM_DOC_MAT_SM,
                                                              TXT_12_FEC_CONTA_SM,
                                                              TXT_13_NUM_FACTURA_SAP,
                                                              TXT_14_FEC_FACT_SAP,
                                                              TXT_15_CLASE_FACTURA,
                                                              TXT_16_POS_FACTURA,
                                                              TXT_17_MATERIAL,
                                                              TXT_18_DESC_PROD,
                                                              TXT_19_CTD_FACTURADA,
                                                              TXT_20_UNIDAD_MEDIDA,
                                                              TXT_21_LOTE,
                                                              TXT_22_FEC_VENCIMIENTO
                                                       --------------------------------------
                                                         from INT_RECEP_PROD_QS a
                                                        where a.guia_nueva = 2
                                                              ) vDetGuia
                                                where vDetGuia.tnumeentr in
                                                      (
                                                        SELECT  C.NUM_ENTREGA
                                                          FROM LGT_RECEP_ENTREGA C
                                                         WHERE C.COD_GRUPO_CIA = '001'
                                                           AND C.COD_LOCAL = CodiLoca_in
                                                           AND C.NRO_RECEP =Id_Entrega_in)
                                              ) DET_GUIAS
                                       where TT.COD_LOCAL = DET_GUIAS.COD_LOCAL(+)
                                         AND TT.COD_PRODUCTO = DET_GUIAS.COD_PRODUCTO(+)
                                         AND TT.NUM_ENTREGA = DET_GUIAS.NUM_ENTREGA(+)
                                         AND TT.POSICION = DET_GUIAS.TXT_16_POS_FACTURA(+)
                                         AND TT.TIPO = 'X'
                                      ---
                                      ) DET_FA) T_D) T_F) FALTAN;
       --WHERE FALTAN.IND_APLICA = 'S';
    dbms_output.put_line('filas insertt>>>');
    dbms_output.put_line('>>>'||sql%rowcount);


    INSERT INTO AUX_INT_CONFIRMACION_GUIAS NOLOGGING
      (Num_Guia,
       Num_Entrega,
       Num_Lote,
       Cod_Local,
       Cod_Producto,
       Diferencia2,
       Tipo,
       Ctd,
       factura_out,
       id_entrega,
       FECHA_RECEPCION,
       -------------------------------------
       TXT_01_CTA_CLIENTE,
       TXT_02_DEST,
       TXT_03_NOMB_DEST,
       TXT_04_DOC_VTA,
       TXT_05_CLASE_PEDIDO,
       TXT_06_FEC_CREA_PEDIDO,
       TXT_07_OC,
       TXT_08_NUM_ENTREGA,
       TXT_09_NUM_GUIA,
       TXT_10_FEC_CREA_ENTREGA,
       TXT_11_NUM_DOC_MAT_SM,
       TXT_12_FEC_CONTA_SM,
       TXT_13_NUM_FACTURA_SAP,
       TXT_14_FEC_FACT_SAP,
       TXT_15_CLASE_FACTURA,
       TXT_16_POS_FACTURA,
       TXT_17_MATERIAL,
       TXT_18_DESC_PROD,
       TXT_19_CTD_FACTURADA,
       TXT_20_UNIDAD_MEDIDA,
       TXT_21_LOTE,
       TXT_22_FEC_VENCIMIENTO
       -------------------------------------
       )
      SELECT FALTAN.NUM_GUIA,
             FALTAN.NUM_ENTREGA,
             FALTAN.NUM_LOTE,
             CodiLoca_in            AS "COD_LOCAL",
             FALTAN.COD_PRODUCTO,
                          case
               when FALTAN.DIF < 0 then 0
                 else FALTAN.DIF
             end as "DIF"      ,
             FALTAN.TIPO,
             case
               when FALTAN.DIF < 0 then 0
                 else FALTAN.DIF
             end as "DIF"      ,
             FALTAN.NUM_FACTURA_SAP,
             Id_Entrega_in          AS "ID_ENTREGA",
             fchRecepcion,
             -------------------------------------
             TXT_01_CTA_CLIENTE,
             TXT_02_DEST,
             TXT_03_NOMB_DEST,
             TXT_04_DOC_VTA,
             TXT_05_CLASE_PEDIDO,
             TXT_06_FEC_CREA_PEDIDO,
             TXT_07_OC,
             TXT_08_NUM_ENTREGA,
             TXT_09_NUM_GUIA,
             TXT_10_FEC_CREA_ENTREGA,
             TXT_11_NUM_DOC_MAT_SM,
             TXT_12_FEC_CONTA_SM,
             TXT_13_NUM_FACTURA_SAP,
             TXT_14_FEC_FACT_SAP,
             TXT_15_CLASE_FACTURA,
             TXT_16_POS_FACTURA,
             TXT_17_MATERIAL,
             TXT_18_DESC_PROD,
             TXT_19_CTD_FACTURADA,
             TXT_20_UNIDAD_MEDIDA,
             TXT_21_LOTE,
             TXT_22_FEC_VENCIMIENTO
      -------------------------------------
        FROM (select T_F.ID_ENTREGA,
                     T_F.COD_PRODUCTO,
                     T_F.COD_LOCAL,
                     T_F.CTD_ENTREGADO,
                     T_F.CTD_CONTEO_FIN,
                     T_F.DIF AS "DIF_TOTAL",
                     /*CASE
                       WHEN T_F.BALANCE <= T_F.DIF THEN
                        T_F.CTD_ACUM_CONF
                       WHEN T_F.BALANCE > T_F.DIF THEN
                       --THEN T_F.DIF - T_F.PREVIO_BALANCE
                        case
                          when T_F.PREVIO_BALANCE < 0 then
                           T_F.DIF
                          else
                           T_F.DIF - T_F.PREVIO_BALANCE
                        end
                     --
                     END AS "DIF",*/
                     case
                     when T_F.DIF < 0 then 0
                         else T_F.DIF
                     end as "DIF" ,
                     T_F.TIPO,
                     T_F.NUM_GUIA,
                     T_F.NUM_ENTREGA,
                     T_F.NUM_LOTE,
                     T_F.NUM_FACTURA_SAP,
                     T_F.CTD_ACUM_CONF,
                     T_F.POSICION,
                     T_F.ORDEN,
                     T_F.BALANCE,
                     T_F.PREVIO_BALANCE,
                     case
                       when T_F.BALANCE <= T_F.DIF then
                        'S'
                       WHEN T_F.PREVIO_BALANCE IS NULL THEN
                        'S'
                       ELSE
                        CASE
                          when T_F.DIF - T_F.PREVIO_BALANCE < 0 THEN
                           'N'
                          when T_F.DIF - T_F.PREVIO_BALANCE <= T_F.CTD_ACUM_CONF THEN
                           'S'
                          ELSE
                           'N'
                        END
                     end IND_APLICA,
                     -------------------------------------
                     TXT_01_CTA_CLIENTE,
                     TXT_02_DEST,
                     TXT_03_NOMB_DEST,
                     TXT_04_DOC_VTA,
                     TXT_05_CLASE_PEDIDO,
                     TXT_06_FEC_CREA_PEDIDO,
                     TXT_07_OC,
                     TXT_08_NUM_ENTREGA,
                     TXT_09_NUM_GUIA,
                     TXT_10_FEC_CREA_ENTREGA,
                     TXT_11_NUM_DOC_MAT_SM,
                     TXT_12_FEC_CONTA_SM,
                     TXT_13_NUM_FACTURA_SAP,
                     TXT_14_FEC_FACT_SAP,
                     TXT_15_CLASE_FACTURA,
                     TXT_16_POS_FACTURA,
                     TXT_17_MATERIAL,
                     TXT_18_DESC_PROD,
                     TXT_19_CTD_FACTURADA,
                     TXT_20_UNIDAD_MEDIDA,
                     TXT_21_LOTE,
                     TXT_22_FEC_VENCIMIENTO
              -------------------------------------
                from (SELECT T_D.ID_ENTREGA,
                             T_D.COD_PRODUCTO,
                             T_D.COD_LOCAL,
                             T_D.CTD_ENTREGADO,
                             T_D.CTD_CONTEO_FIN,
                             T_D.DIF,
                             T_D.TIPO,
                             T_D.NUM_GUIA,
                             T_D.NUM_ENTREGA,
                             T_D.NUM_LOTE,
                             T_D.NUM_FACTURA_SAP,
                             T_D.CTD_ACUM_CONF,
                             T_D.POSICION,
                             T_D.ORDEN,
                             T_D.BALANCE,
                             -------------------------------------
                             TXT_01_CTA_CLIENTE,
                             TXT_02_DEST,
                             TXT_03_NOMB_DEST,
                             TXT_04_DOC_VTA,
                             TXT_05_CLASE_PEDIDO,
                             TXT_06_FEC_CREA_PEDIDO,
                             TXT_07_OC,
                             TXT_08_NUM_ENTREGA,
                             TXT_09_NUM_GUIA,
                             TXT_10_FEC_CREA_ENTREGA,
                             TXT_11_NUM_DOC_MAT_SM,
                             TXT_12_FEC_CONTA_SM,
                             TXT_13_NUM_FACTURA_SAP,
                             TXT_14_FEC_FACT_SAP,
                             TXT_15_CLASE_FACTURA,
                             TXT_16_POS_FACTURA,
                             TXT_17_MATERIAL,
                             TXT_18_DESC_PROD,
                             TXT_19_CTD_FACTURADA,
                             TXT_20_UNIDAD_MEDIDA,
                             TXT_21_LOTE,
                             TXT_22_FEC_VENCIMIENTO,
                             -------------------------------------
                             nvl(lag(T_D.BALANCE, 1)
                                 over(PARTITION BY T_D.COD_PRODUCTO ORDER BY
                                      T_D.COD_PRODUCTO,
                                      T_D.BALANCE),
                                 -1) AS PREVIO_BALANCE
                        FROM (select DET_FA.ID_ENTREGA,
                                     DET_FA.COD_PRODUCTO,
                                     DET_FA.COD_LOCAL,
                                     DET_FA.CTD_ENTREGADO,
                                     DET_FA.CTD_CONTEO_FIN,
                                     DET_FA.DIF,
                                     DET_FA.TIPO,
                                     DET_FA.NUM_GUIA,
                                     DET_FA.NUM_ENTREGA,
                                     DET_FA.NUM_LOTE,
                                     DET_FA.NUM_FACTURA_SAP,
                                     DET_FA.CTD_ACUM_CONF,
                                     DET_FA.POSICION,
                                     DET_FA.ORDEN,
                                     -------------------------------------
                                     DET_FA.TXT_01_CTA_CLIENTE,
                                     DET_FA.TXT_02_DEST,
                                     DET_FA.TXT_03_NOMB_DEST,
                                     DET_FA.TXT_04_DOC_VTA,
                                     DET_FA.TXT_05_CLASE_PEDIDO,
                                     DET_FA.TXT_06_FEC_CREA_PEDIDO,
                                     DET_FA.TXT_07_OC,
                                     DET_FA.TXT_08_NUM_ENTREGA,
                                     DET_FA.TXT_09_NUM_GUIA,
                                     DET_FA.TXT_10_FEC_CREA_ENTREGA,
                                     DET_FA.TXT_11_NUM_DOC_MAT_SM,
                                     DET_FA.TXT_12_FEC_CONTA_SM,
                                     DET_FA.TXT_13_NUM_FACTURA_SAP,
                                     DET_FA.TXT_14_FEC_FACT_SAP,
                                     DET_FA.TXT_15_CLASE_FACTURA,
                                     DET_FA.TXT_16_POS_FACTURA,
                                     DET_FA.TXT_17_MATERIAL,
                                     DET_FA.TXT_18_DESC_PROD,
                                     DET_FA.TXT_19_CTD_FACTURADA,
                                     DET_FA.TXT_20_UNIDAD_MEDIDA,
                                     DET_FA.TXT_21_LOTE,
                                     DET_FA.TXT_22_FEC_VENCIMIENTO,
                                     -------------------------------------
                                     SUM(DET_FA.CTD_ACUM_CONF) OVER(PARTITION BY DET_FA.COD_PRODUCTO ORDER BY DET_FA.ORDEN asc rows unbounded preceding) BALANCE
                                from (
                                      ---
                                      select TT.*,
                                              DET_GUIAS.NUM_GUIA,
                                              --DET_GUIAS.NUM_ENTREGA,
                                              DET_GUIAS.NUM_LOTE,
                                              DET_GUIAS.NUM_FACTURA_SAP,
                                              DET_GUIAS.CTD_ACUM_CONF,
                                              --DET_GUIAS.POSICION,
                                              -------------------------------------
                                              DET_GUIAS.TXT_01_CTA_CLIENTE,
                                              DET_GUIAS.TXT_02_DEST,
                                              DET_GUIAS.TXT_03_NOMB_DEST,
                                              DET_GUIAS.TXT_04_DOC_VTA,
                                              DET_GUIAS.TXT_05_CLASE_PEDIDO,
                                              DET_GUIAS.TXT_06_FEC_CREA_PEDIDO,
                                              DET_GUIAS.TXT_07_OC,
                                              DET_GUIAS.TXT_08_NUM_ENTREGA,
                                              DET_GUIAS.TXT_09_NUM_GUIA,
                                              DET_GUIAS.TXT_10_FEC_CREA_ENTREGA,
                                              DET_GUIAS.TXT_11_NUM_DOC_MAT_SM,
                                              DET_GUIAS.TXT_12_FEC_CONTA_SM,
                                              DET_GUIAS.TXT_13_NUM_FACTURA_SAP,
                                              DET_GUIAS.TXT_14_FEC_FACT_SAP,
                                              DET_GUIAS.TXT_15_CLASE_FACTURA,
                                              DET_GUIAS.TXT_16_POS_FACTURA,
                                              DET_GUIAS.TXT_17_MATERIAL,
                                              DET_GUIAS.TXT_18_DESC_PROD,
                                              DET_GUIAS.TXT_19_CTD_FACTURADA,
                                              DET_GUIAS.TXT_20_UNIDAD_MEDIDA,
                                              DET_GUIAS.TXT_21_LOTE,
                                              DET_GUIAS.TXT_22_FEC_VENCIMIENTO,
                                              -------------------------------------
                                              RANK() OVER(PARTITION BY TT.COD_PRODUCTO ORDER BY DET_GUIAS.CTD_ACUM_CONF ASC, DET_GUIAS.NUM_FACTURA_SAP, DET_GUIAS.POSICION) ORDEN
                                        from (select vs.*
                                                 from V_DIF_TOTAL_FASA vs
                                                where vs.tipo = 'FALTANTE'
                                                  and vs.id_entrega =
                                                      Id_Entrega_in
                                                  and vs.cod_local = CodiLoca_in) TT,
                                              (select Id_Entrega_in as "ID_ENTREGA",
                                                      vDetGuia.Cod_Prod AS "COD_PRODUCTO",
                                                      CodiLoca_in as "COD_LOCAL",
                                                      vDetGuia.Tnumeentr as "NUM_GUIA",
                                                      vDetGuia.Tfactsap as "NUM_FACTURA_SAP",
                                                      vDetGuia.tnumelote as "NUM_LOTE",
                                                      vDetGuia.tnumeentr as "NUM_ENTREGA",
                                                      vDetGuia.cant_solic AS "CTD_ACUM_CONF",
                                                      ROWNUM AS "POSICION",
                                                      -------------------------------------
                                                      TXT_01_CTA_CLIENTE,
                                                      TXT_02_DEST,
                                                      TXT_03_NOMB_DEST,
                                                      TXT_04_DOC_VTA,
                                                      TXT_05_CLASE_PEDIDO,
                                                      TXT_06_FEC_CREA_PEDIDO,
                                                      TXT_07_OC,
                                                      TXT_08_NUM_ENTREGA,
                                                      TXT_09_NUM_GUIA,
                                                      TXT_10_FEC_CREA_ENTREGA,
                                                      TXT_11_NUM_DOC_MAT_SM,
                                                      TXT_12_FEC_CONTA_SM,
                                                      TXT_13_NUM_FACTURA_SAP,
                                                      TXT_14_FEC_FACT_SAP,
                                                      TXT_15_CLASE_FACTURA,
                                                      TXT_16_POS_FACTURA,
                                                      TXT_17_MATERIAL,
                                                      TXT_18_DESC_PROD,
                                                      TXT_19_CTD_FACTURADA,
                                                      TXT_20_UNIDAD_MEDIDA,
                                                      TXT_21_LOTE,
                                                      TXT_22_FEC_VENCIMIENTO
                                               -------------------------------------
                                                 from (select a.num_entrega AS tnumeentr,
                                                              --a.Num_Factura_Sap AS tnumefact,
                                                              a.Num_Factura_Sap AS tfactsap,
                                                              a.Num_Lote        AS tnumelote,
                                                              a.cod_prod ,
                                                              a.cant_solic,
                                                              -------------------------------------
                                                              TXT_01_CTA_CLIENTE,
                                                              TXT_02_DEST,
                                                              TXT_03_NOMB_DEST,
                                                              TXT_04_DOC_VTA,
                                                              TXT_05_CLASE_PEDIDO,
                                                              TXT_06_FEC_CREA_PEDIDO,
                                                              TXT_07_OC,
                                                              TXT_08_NUM_ENTREGA,
                                                              TXT_09_NUM_GUIA,
                                                              TXT_10_FEC_CREA_ENTREGA,
                                                              TXT_11_NUM_DOC_MAT_SM,
                                                              TXT_12_FEC_CONTA_SM,
                                                              TXT_13_NUM_FACTURA_SAP,
                                                              TXT_14_FEC_FACT_SAP,
                                                              TXT_15_CLASE_FACTURA,
                                                              TXT_16_POS_FACTURA,
                                                              TXT_17_MATERIAL,
                                                              TXT_18_DESC_PROD,
                                                              TXT_19_CTD_FACTURADA,
                                                              TXT_20_UNIDAD_MEDIDA,
                                                              TXT_21_LOTE,
                                                              TXT_22_FEC_VENCIMIENTO
                                                       --------------------------------------
                                                         from INT_RECEP_PROD_QS a
                                                        where a.guia_nueva = 2
                                                              ) vDetGuia
                                                  where vDetGuia.tnumeentr in
                                                                    (
                                                                      SELECT  C.NUM_ENTREGA
                                                                        FROM LGT_RECEP_ENTREGA C
                                                                       WHERE C.COD_GRUPO_CIA = '001'
                                                                         AND C.COD_LOCAL = CodiLoca_in
                                                                         AND C.NRO_RECEP =Id_Entrega_in
                                                                     )
                                                      ) DET_GUIAS
                                       where TT.COD_LOCAL =DET_GUIAS.COD_LOCAL(+)
                                         AND TT.COD_PRODUCTO =DET_GUIAS.COD_PRODUCTO(+)
                                         AND TT.POSICION =DET_GUIAS.TXT_16_POS_FACTURA (+)
                                         AND TT.NUM_ENTREGA =DET_GUIAS.NUM_ENTREGA(+)
                                         AND TT.TIPO = 'FALTANTE'
                                      ---
                                      ) DET_FA) T_D) T_F) FALTAN;
       --WHERE FALTAN.IND_APLICA = 'S';

    INSERT INTO AUX_INT_CONFIRMACION_GUIAS NOLOGGING
      (Num_Guia,
       Num_Entrega,
       Num_Lote,
       Cod_Local,
       Cod_Producto,
       Diferencia2,
       Tipo,
       Ctd,
       factura_out,
       id_entrega,
       FECHA_RECEPCION,
       -------------------------------------
       TXT_01_CTA_CLIENTE,
       TXT_02_DEST,
       TXT_03_NOMB_DEST,
       TXT_04_DOC_VTA,
       TXT_05_CLASE_PEDIDO,
       TXT_06_FEC_CREA_PEDIDO,
       TXT_07_OC,
       TXT_08_NUM_ENTREGA,
       TXT_09_NUM_GUIA,
       TXT_10_FEC_CREA_ENTREGA,
       TXT_11_NUM_DOC_MAT_SM,
       TXT_12_FEC_CONTA_SM,
       TXT_13_NUM_FACTURA_SAP,
       TXT_14_FEC_FACT_SAP,
       TXT_15_CLASE_FACTURA,
       TXT_16_POS_FACTURA,
       TXT_17_MATERIAL,
       TXT_18_DESC_PROD,
       TXT_19_CTD_FACTURADA,
       TXT_20_UNIDAD_MEDIDA,
       TXT_21_LOTE,
       TXT_22_FEC_VENCIMIENTO
       -------------------------------------
       )
      SELECT FALTAN.NUM_GUIA,
             FALTAN.NUM_ENTREGA,
             FALTAN.NUM_LOTE,
             CodiLoca_in            AS "COD_LOCAL",
             FALTAN.COD_PRODUCTO,
             CASE
               WHEN FALTAN.DIF < 0 THEN 0
               ELSE FALTAN.DIF
             END    AS "DIF"  ,
             FALTAN.TIPO,
             CASE
               WHEN FALTAN.DIF < 0 THEN 0
               ELSE FALTAN.DIF
             END    AS "DIF"  ,
             FALTAN.NUM_FACTURA_SAP,
             Id_Entrega_in          AS "ID_ENTREGA",
             fchRecepcion,
             -------------------------------------
             TXT_01_CTA_CLIENTE,
             TXT_02_DEST,
             TXT_03_NOMB_DEST,
             TXT_04_DOC_VTA,
             TXT_05_CLASE_PEDIDO,
             TXT_06_FEC_CREA_PEDIDO,
             TXT_07_OC,
             TXT_08_NUM_ENTREGA,
             TXT_09_NUM_GUIA,
             TXT_10_FEC_CREA_ENTREGA,
             TXT_11_NUM_DOC_MAT_SM,
             TXT_12_FEC_CONTA_SM,
             TXT_13_NUM_FACTURA_SAP,
             TXT_14_FEC_FACT_SAP,
             TXT_15_CLASE_FACTURA,
             TXT_16_POS_FACTURA,
             TXT_17_MATERIAL,
             TXT_18_DESC_PROD,
             TXT_19_CTD_FACTURADA,
             TXT_20_UNIDAD_MEDIDA,
             TXT_21_LOTE,
             TXT_22_FEC_VENCIMIENTO
      -------------------------------------
        FROM (select T_F.ID_ENTREGA,
                     T_F.COD_PRODUCTO,
                     T_F.COD_LOCAL,
                     T_F.CTD_ENTREGADO,
                     T_F.CTD_CONTEO_FIN,
                     T_F.DIF AS "DIF_TOTAL",
                     CASE
                       WHEN T_F.BALANCE <= T_F.DIF THEN
                        --T_F.CTD_ACUM_CONF     COMENTADO Y MODIFICADO POR DFLORES 16-09-2014
                        T_F.DIF
                       WHEN T_F.BALANCE > T_F.DIF THEN
                       --THEN T_F.DIF - T_F.PREVIO_BALANCE
                        case
                          when T_F.PREVIO_BALANCE < 0 then
                           T_F.DIF
                          else
                           T_F.DIF - T_F.PREVIO_BALANCE
                        end
                     --
                     END AS "DIF",
                     T_F.TIPO,
                     T_F.NUM_GUIA,
                     T_F.NUM_ENTREGA,
                     T_F.NUM_LOTE,
                     T_F.NUM_FACTURA_SAP,
                     T_F.CTD_ACUM_CONF,
                     T_F.POSICION,
                     T_F.ORDEN,
                     T_F.BALANCE,
                     T_F.PREVIO_BALANCE,
                     /*case
                       when T_F.BALANCE <= T_F.DIF then
                        'S'
                       WHEN T_F.PREVIO_BALANCE IS NULL THEN
                        'S'
                       ELSE
                        CASE
                          when T_F.PREVIO_BALANCE - T_F.DIF< 0 THEN
                           'N'
                          when T_F.PREVIO_BALANCE - T_F.DIF <= T_F.CTD_ACUM_CONF THEN
                           'S'
                          ELSE
                           'N'
                        END
                     end IND_APLICA,*/
                     case
                       when T_F.PREVIO_BALANCE < 0 THEN
                           'S'
                          ELSE
                           'N'
                       -- END
                     end   as IND_APLICA,
                     -------------------------------------
                     TXT_01_CTA_CLIENTE,
                     TXT_02_DEST,
                     TXT_03_NOMB_DEST,
                     TXT_04_DOC_VTA,
                     TXT_05_CLASE_PEDIDO,
                     TXT_06_FEC_CREA_PEDIDO,
                     TXT_07_OC,
                     TXT_08_NUM_ENTREGA,
                     TXT_09_NUM_GUIA,
                     TXT_10_FEC_CREA_ENTREGA,
                     TXT_11_NUM_DOC_MAT_SM,
                     TXT_12_FEC_CONTA_SM,
                     TXT_13_NUM_FACTURA_SAP,
                     TXT_14_FEC_FACT_SAP,
                     TXT_15_CLASE_FACTURA,
                     TXT_16_POS_FACTURA,
                     TXT_17_MATERIAL,
                     TXT_18_DESC_PROD,
                     TXT_19_CTD_FACTURADA,
                     TXT_20_UNIDAD_MEDIDA,
                     TXT_21_LOTE,
                     TXT_22_FEC_VENCIMIENTO
              -------------------------------------
                from (SELECT T_D.ID_ENTREGA,
                             T_D.COD_PRODUCTO,
                             T_D.COD_LOCAL,
                             T_D.CTD_ENTREGADO,
                             T_D.CTD_CONTEO_FIN,
                             T_D.DIF,
                             T_D.TIPO,
                             T_D.NUM_GUIA,
                             T_D.NUM_ENTREGA,
                             T_D.NUM_LOTE,
                             T_D.NUM_FACTURA_SAP,
                             T_D.CTD_ACUM_CONF,
                             T_D.POSICION,
                             T_D.ORDEN,
                             T_D.BALANCE,
                             -------------------------------------
                             TXT_01_CTA_CLIENTE,
                             TXT_02_DEST,
                             TXT_03_NOMB_DEST,
                             TXT_04_DOC_VTA,
                             TXT_05_CLASE_PEDIDO,
                             TXT_06_FEC_CREA_PEDIDO,
                             TXT_07_OC,
                             TXT_08_NUM_ENTREGA,
                             TXT_09_NUM_GUIA,
                             TXT_10_FEC_CREA_ENTREGA,
                             TXT_11_NUM_DOC_MAT_SM,
                             TXT_12_FEC_CONTA_SM,
                             TXT_13_NUM_FACTURA_SAP,
                             TXT_14_FEC_FACT_SAP,
                             TXT_15_CLASE_FACTURA,
                             TXT_16_POS_FACTURA,
                             TXT_17_MATERIAL,
                             TXT_18_DESC_PROD,
                             TXT_19_CTD_FACTURADA,
                             TXT_20_UNIDAD_MEDIDA,
                             TXT_21_LOTE,
                             TXT_22_FEC_VENCIMIENTO,
                             -------------------------------------
                             nvl(lag(T_D.BALANCE, 1)
                                 over(PARTITION BY T_D.COD_PRODUCTO ORDER BY
                                      T_D.COD_PRODUCTO,
                                      T_D.BALANCE),
                                 -1) AS PREVIO_BALANCE
                        FROM (select DET_FA.ID_ENTREGA,
                                     DET_FA.COD_PRODUCTO,
                                     DET_FA.COD_LOCAL,
                                     DET_FA.CTD_ENTREGADO,
                                     DET_FA.CTD_CONTEO_FIN,
                                     DET_FA.DIF,
                                     DET_FA.TIPO,
                                     DET_FA.NUM_GUIA,
                                     DET_FA.NUM_ENTREGA,
                                     DET_FA.NUM_LOTE,
                                     DET_FA.NUM_FACTURA_SAP,
                                     DET_FA.CTD_ACUM_CONF,
                                     DET_FA.POSICION,
                                     DET_FA.ORDEN,
                                     -------------------------------------
                                     DET_FA.TXT_01_CTA_CLIENTE,
                                     DET_FA.TXT_02_DEST,
                                     DET_FA.TXT_03_NOMB_DEST,
                                     DET_FA.TXT_04_DOC_VTA,
                                     DET_FA.TXT_05_CLASE_PEDIDO,
                                     DET_FA.TXT_06_FEC_CREA_PEDIDO,
                                     DET_FA.TXT_07_OC,
                                     DET_FA.TXT_08_NUM_ENTREGA,
                                     DET_FA.TXT_09_NUM_GUIA,
                                     DET_FA.TXT_10_FEC_CREA_ENTREGA,
                                     DET_FA.TXT_11_NUM_DOC_MAT_SM,
                                     DET_FA.TXT_12_FEC_CONTA_SM,
                                     DET_FA.TXT_13_NUM_FACTURA_SAP,
                                     DET_FA.TXT_14_FEC_FACT_SAP,
                                     DET_FA.TXT_15_CLASE_FACTURA,
                                     DET_FA.TXT_16_POS_FACTURA,
                                     DET_FA.TXT_17_MATERIAL,
                                     DET_FA.TXT_18_DESC_PROD,
                                     DET_FA.TXT_19_CTD_FACTURADA,
                                     DET_FA.TXT_20_UNIDAD_MEDIDA,
                                     DET_FA.TXT_21_LOTE,
                                     DET_FA.TXT_22_FEC_VENCIMIENTO,
                                     -------------------------------------
                                     SUM(DET_FA.CTD_ACUM_CONF) OVER(PARTITION BY DET_FA.COD_PRODUCTO ORDER BY DET_FA.ORDEN asc rows unbounded preceding) BALANCE
                                from (
                                      ---
                                      select  --TT.*,
                                              TT.id_entrega,
                                              TT.cod_producto,
                                              TT.cod_local,
                                              TT.ctd_entregado,
                                              TT.ctd_conteo_fin,
                                              TT.dif,
                                              TT.tipo,
                                              --TT.posicion,
                                              --TT.num_entrega,
                                              -----------------------------------
                                              DET_GUIAS.NUM_GUIA,
                                              DET_GUIAS.NUM_ENTREGA,
                                              DET_GUIAS.NUM_LOTE,
                                              DET_GUIAS.NUM_FACTURA_SAP,
                                              DET_GUIAS.CTD_ACUM_CONF,
                                              DET_GUIAS.POSICION,
                                              -------------------------------------
                                              DET_GUIAS.TXT_01_CTA_CLIENTE,
                                              DET_GUIAS.TXT_02_DEST,
                                              DET_GUIAS.TXT_03_NOMB_DEST,
                                              DET_GUIAS.TXT_04_DOC_VTA,
                                              DET_GUIAS.TXT_05_CLASE_PEDIDO,
                                              DET_GUIAS.TXT_06_FEC_CREA_PEDIDO,
                                              DET_GUIAS.TXT_07_OC,
                                              DET_GUIAS.TXT_08_NUM_ENTREGA,
                                              DET_GUIAS.TXT_09_NUM_GUIA,
                                              DET_GUIAS.TXT_10_FEC_CREA_ENTREGA,
                                              DET_GUIAS.TXT_11_NUM_DOC_MAT_SM,
                                              DET_GUIAS.TXT_12_FEC_CONTA_SM,
                                              DET_GUIAS.TXT_13_NUM_FACTURA_SAP,
                                              DET_GUIAS.TXT_14_FEC_FACT_SAP,
                                              DET_GUIAS.TXT_15_CLASE_FACTURA,
                                              DET_GUIAS.TXT_16_POS_FACTURA,
                                              DET_GUIAS.TXT_17_MATERIAL,
                                              DET_GUIAS.TXT_18_DESC_PROD,
                                              DET_GUIAS.TXT_19_CTD_FACTURADA,
                                              DET_GUIAS.TXT_20_UNIDAD_MEDIDA,
                                              DET_GUIAS.TXT_21_LOTE,
                                              DET_GUIAS.TXT_22_FEC_VENCIMIENTO,
                                              -------------------------------------
                                              RANK() OVER(PARTITION BY TT.COD_PRODUCTO ORDER BY DET_GUIAS.CTD_ACUM_CONF ASC, DET_GUIAS.NUM_FACTURA_SAP, DET_GUIAS.POSICION) ORDEN
                                        from (select vs.*
                                                 from V_DIF_TOTAL_FASA vs
                                                where vs.tipo = 'SOBRANTE'
                                                  and vs.id_entrega =
                                                      Id_Entrega_in
                                                  and vs.cod_local = CodiLoca_in) TT,
                                              (select Id_Entrega_in as "ID_ENTREGA",
                                                      vDetGuia.Cod_Prod AS "COD_PRODUCTO",
                                                      CodiLoca_in as "COD_LOCAL",
                                                      vDetGuia.Tnumeentr as "NUM_GUIA",
                                                      vDetGuia.Tfactsap as "NUM_FACTURA_SAP",
                                                      vDetGuia.tnumelote as "NUM_LOTE",
                                                      vDetGuia.tnumeentr as "NUM_ENTREGA",
                                                      vDetGuia.cant_solic AS "CTD_ACUM_CONF",
                                                      ROWNUM AS "POSICION",
                                                      -------------------------------------

                                                      -------------------------------------
                                                      TXT_01_CTA_CLIENTE,
                                                      TXT_02_DEST,
                                                      TXT_03_NOMB_DEST,
                                                      TXT_04_DOC_VTA,
                                                      TXT_05_CLASE_PEDIDO,
                                                      TXT_06_FEC_CREA_PEDIDO,
                                                      TXT_07_OC,
                                                      TXT_08_NUM_ENTREGA,
                                                      TXT_09_NUM_GUIA,
                                                      TXT_10_FEC_CREA_ENTREGA,
                                                      TXT_11_NUM_DOC_MAT_SM,
                                                      TXT_12_FEC_CONTA_SM,
                                                      TXT_13_NUM_FACTURA_SAP,
                                                      TXT_14_FEC_FACT_SAP,
                                                      TXT_15_CLASE_FACTURA,
                                                      TXT_16_POS_FACTURA,
                                                      TXT_17_MATERIAL,
                                                      TXT_18_DESC_PROD,
                                                      TXT_19_CTD_FACTURADA,
                                                      TXT_20_UNIDAD_MEDIDA,
                                                      TXT_21_LOTE,
                                                      TXT_22_FEC_VENCIMIENTO
                                               -------------------------------------
                                                 from (select a.num_entrega AS tnumeentr,
                                                              --a.Num_Factura_Sap AS tnumefact,
                                                              a.Num_Factura_Sap AS tfactsap,
                                                              a.Num_Lote        AS tnumelote,
                                                              a.cod_prod,
                                                              a.cant_solic ,
                                                              -------------------------------------
                                                              TXT_01_CTA_CLIENTE,
                                                              TXT_02_DEST,
                                                              TXT_03_NOMB_DEST,
                                                              TXT_04_DOC_VTA,
                                                              TXT_05_CLASE_PEDIDO,
                                                              TXT_06_FEC_CREA_PEDIDO,
                                                              TXT_07_OC,
                                                              TXT_08_NUM_ENTREGA,
                                                              TXT_09_NUM_GUIA,
                                                              TXT_10_FEC_CREA_ENTREGA,
                                                              TXT_11_NUM_DOC_MAT_SM,
                                                              TXT_12_FEC_CONTA_SM,
                                                              TXT_13_NUM_FACTURA_SAP,
                                                              TXT_14_FEC_FACT_SAP,
                                                              TXT_15_CLASE_FACTURA,
                                                              TXT_16_POS_FACTURA,
                                                              TXT_17_MATERIAL,
                                                              TXT_18_DESC_PROD,
                                                              TXT_19_CTD_FACTURADA,
                                                              TXT_20_UNIDAD_MEDIDA,
                                                              TXT_21_LOTE,
                                                              TXT_22_FEC_VENCIMIENTO
                                                       --------------------------------------
                                                         from INT_RECEP_PROD_QS a
                                                        where a.guia_nueva = 2
                                                              ) vDetGuia
                                                  where vDetGuia.tnumeentr in
                                                                    (
                                                                      SELECT  C.NUM_ENTREGA
                                                                        FROM LGT_RECEP_ENTREGA C
                                                                       WHERE C.COD_GRUPO_CIA = '001'
                                                                         AND C.COD_LOCAL = CodiLoca_in
                                                                         AND C.NRO_RECEP =Id_Entrega_in
                                                                     )

                                                      ) DET_GUIAS
                                       where TT.COD_LOCAL    = DET_GUIAS.COD_LOCAL(+)
                                         AND TT.COD_PRODUCTO = DET_GUIAS.COD_PRODUCTO(+)
                                         AND TT.NUM_ENTREGA = DET_GUIAS.NUM_ENTREGA(+)
                                         AND TT.POSICION = DET_GUIAS.TXT_16_POS_FACTURA(+)
                                         AND TT.TIPO = 'SOBRANTE'
                                      ---
                                      ) DET_FA) T_D) T_F) FALTAN;
       --WHERE FALTAN.IND_APLICA = 'S';


      delete AUX_INT_CONFIRMACION_GUIAS
       where  num_entrega is null
       and    FECHA_PROC_TXT IS NULL
       and    TIPO != 'SOBRANTE';


       update AUX_INT_CONFIRMACION_GUIAS v
       set    (v.ctd,v.diferencia2) = (
                                         select b.dif,b.dif
                                         from   V_DIF_TOTAL_FASA b
                                         where  b.cod_local = CodiLoca_in
                                         and    b.id_entrega = Id_Entrega_in
                                         and    b.cod_producto = v.cod_producto
                                      )
       where  v.num_entrega is null
       and    v.FECHA_PROC_TXT IS NULL
       and    v.TIPO = 'SOBRANTE'
       and    exists (
                         select 1
                         from   V_DIF_TOTAL_FASA b
                         where  b.cod_local = CodiLoca_in
                         and    b.id_entrega = Id_Entrega_in
                         and    b.cod_producto = v.cod_producto
                      );

       fasa_int_confirmacion.p_completa_sobrante_adicional(CodiLoca_in,Id_Entrega_in);

    dbms_output.put_line('INT_CONFIRMACION_GUIAS>>'||'Entro a int conf');

    INSERT INTO INT_CONFIRMACION_GUIAS
         (
          num_guia,num_entrega, num_lote, cod_local, cod_local_sap, cod_producto, cod_codigo_sap,
          des_producto, des_laboratorio, diferencia2, tipo, ctd, fecha_proceso, factura_out,
          id_entrega, flg_completo, fecha_recepcion, fecha_crea, txt_01_cta_cliente, txt_02_dest,
          txt_03_nomb_dest, txt_04_doc_vta, txt_05_clase_pedido, txt_06_fec_crea_pedido, txt_07_oc,
          txt_08_num_entrega, txt_09_num_guia, txt_10_fec_crea_entrega, txt_11_num_doc_mat_sm,
          txt_12_fec_conta_sm, txt_13_num_factura_sap, txt_14_fec_fact_sap, txt_15_clase_factura,
          txt_16_pos_factura, txt_17_material, txt_18_desc_prod, txt_19_ctd_facturada, txt_20_unidad_medida,
          txt_21_lote, txt_22_fec_vencimiento, fecha_proc_txt, name_txt
         )
         SELECT
          distinct num_guia,num_entrega, num_lote, cod_local, cod_local_sap, cod_producto, cod_codigo_sap,
          des_producto, des_laboratorio, diferencia2, tipo, ctd, fecha_proceso, factura_out,
          id_entrega, flg_completo, fecha_recepcion, fecha_crea, txt_01_cta_cliente, txt_02_dest,
          txt_03_nomb_dest, txt_04_doc_vta, txt_05_clase_pedido, txt_06_fec_crea_pedido, txt_07_oc,
          txt_08_num_entrega, txt_09_num_guia, txt_10_fec_crea_entrega, txt_11_num_doc_mat_sm,
          txt_12_fec_conta_sm, txt_13_num_factura_sap, txt_14_fec_fact_sap, txt_15_clase_factura,
          txt_16_pos_factura, txt_17_material, txt_18_desc_prod, txt_19_ctd_facturada, txt_20_unidad_medida,
          txt_21_lote, txt_22_fec_vencimiento, fecha_proc_txt, name_txt
         FROM   AUX_INT_CONFIRMACION_GUIAS T;

/*       delete INT_CONFIRMACION_GUIAS
       where  num_entrega is not null
       and    FECHA_PROC_TXT IS NULL
       and    num_entrega not in (
                               select tp.num_entrega
                               from   TMP_PROCESAR tp);*/

  END;
  /****************************************************************************/
  PROCEDURE INT_TXT_CONFIRMACION AS

    CURSOR curSolicitud IS
      SELECT DISTINCT pp.cod_local,pp.num_entrega
        FROM INT_CONFIRMACION_GUIAS PP
       WHERE PP.FECHA_PROC_TXT IS NULL
       ORDER BY pp.cod_local asc,pp.num_entrega ASC;

    CURSOR curResumen(cCodLoca_in    IN VARCHAR2,
                      cNumEntrega_in IN VARCHAR2) IS
    /*
    SELECT LISTADO.DATO_TXT_GR||'|'||
           TRIM(to_char(LISTADO.CANTIDAD_CONFIRMADA, '9,999,990'))||'|'||
           LISTADO.FECHA_CONFIRMACION||'|'||
           (
           CASE
             WHEN LISTADO.DIFERENCIA < 0 THEN       TRIM(to_char(LISTADO.DIFERENCIA, 'S9,999,990'))
             ELSE TRIM(to_char(LISTADO.DIFERENCIA, '9,999,990'))
           END
           )||'|'||
           LISTADO.FECHA_ENVIO_INFORMACION||'|'||
           LISTADO.FECHA_CONFIRMACION as RESUMEN
    */
    SELECT LISTADO.DATO_TXT_GR||'|'||
           TRIM(to_char(LISTADO.CANTIDAD_CONFIRMADA, '999999999999'))||'|'||
           LISTADO.FECHA_CONFIRMACION||'|'||
           (
           CASE
             WHEN LISTADO.DIFERENCIA < 0 THEN       TRIM(to_char(LISTADO.DIFERENCIA, 'S999999999999'))
             ELSE TRIM(to_char(LISTADO.DIFERENCIA, '999999999999'))
           END
           )||'|'||
           LISTADO.FECHA_ENVIO_INFORMACION||'|'||
           LISTADO.FECHA_CONFIRMACION as RESUMEN
    FROM  (
    select TXT_01_CTA_CLIENTE || '|' || TXT_02_DEST || '|' || TXT_03_NOMB_DEST || '|' ||
           TXT_04_DOC_VTA || '|' || TXT_05_CLASE_PEDIDO || '|' ||
           TXT_06_FEC_CREA_PEDIDO || '|' || TXT_07_OC || '|' ||
           TXT_08_NUM_ENTREGA || '|' || TXT_09_NUM_GUIA || '|' ||
           TXT_10_FEC_CREA_ENTREGA || '|' || TXT_11_NUM_DOC_MAT_SM || '|' ||
           TXT_12_FEC_CONTA_SM || '|' || TXT_13_NUM_FACTURA_SAP || '|' ||
           TXT_14_FEC_FACT_SAP || '|' || TXT_15_CLASE_FACTURA || '|' ||
           TXT_16_POS_FACTURA || '|' || TXT_17_MATERIAL || '|' ||
           TXT_18_DESC_PROD || '|' || TXT_19_CTD_FACTURADA || '|' ||
           TXT_20_UNIDAD_MEDIDA || '|' || TXT_21_LOTE || '|' ||
           TXT_22_FEC_VENCIMIENTO DATO_TXT_GR,
           CASE
             WHEN T.TIPO = 'X' THEN round(to_number(trim(t.TXT_19_ctd_facturada),'999999999999.000'))
             WHEN T.TIPO = 'FALTANTE' THEN round(to_number(trim(t.TXT_19_ctd_facturada),'999999999999.000')) - t.ctd
             WHEN T.TIPO = 'SOBRANTE' THEN round(to_number(trim(t.TXT_19_ctd_facturada),'999999999999.000')) + t.ctd
           END        CANTIDAD_CONFIRMADA,
           to_char(t.fecha_recepcion,'yyyymmdd') FECHA_CONFIRMACION,
           CASE
             WHEN T.TIPO = 'X' THEN t.ctd
             WHEN T.TIPO = 'FALTANTE' THEN t.ctd*-1
             WHEN T.TIPO = 'SOBRANTE' THEN t.ctd*+1
           END
           DIFERENCIA,
           to_char(sysdate,'yyyymmdd') FECHA_ENVIO_INFORMACION      ,
           t.txt_08_num_entrega,t.txt_16_pos_factura
      from INT_CONFIRMACION_GUIAS t
      WHERE T.Num_Entrega = cNumEntrega_in
      AND   T.COD_LOCAL = cCodLoca_in
      --and   t.fecha_crea >= trunc(sysdate)
      and   t.FECHA_PROC_TXT IS NULL
      and   t.num_entrega is not null
      ORDER BY T.COD_LOCAL,T.ID_ENTREGA,t.txt_08_num_entrega,t.txt_16_pos_factura
      ) LISTADO;

    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);

    v_gNombreDiretorio VARCHAR2(700) := 'DIR_INTERFACES';
    --'DIR_FASA_CONFIRMACION';

  BEGIN

        FOR c_Sol IN curSolicitud LOOP
          -- GCONF_20130829_122530.TXT
          v_vNombreArchivo := 'GCONF_'||c_Sol.cod_local ||'_'||
                              TO_CHAR(SYSDATE, 'yyyyMMdd') ||'_'||
                              TO_CHAR(SYSDATE, 'HH24MISS') || '.TXT';

          ARCHIVO_TEXTO := UTL_FILE.FOPEN(TRIM(v_gNombreDiretorio),
                                          TRIM(v_vNombreArchivo),
                                          'W');

          FOR v_rCurResumen IN curResumen(c_Sol.cod_local,
                                          c_Sol.num_entrega) LOOP
            UTL_FILE.PUT_LINE(ARCHIVO_TEXTO, v_rCurResumen.RESUMEN);
          END LOOP;

          if utl_file.is_open(ARCHIVO_TEXTO) = true then
             begin
             UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
             exception
             when  others then
               null;
             end;
          end if;

          UPDATE INT_CONFIRMACION_GUIAS t
             SET t.fecha_proc_txt = sysdate ,
                 t.name_txt =  v_vNombreArchivo
           WHERE t.cod_local = c_Sol.cod_local
           and   t.num_entrega = c_Sol.num_entrega
           and   t.fecha_proc_txt is null;

           commit;
         DBMS_LOCK.sleep(1);
        END LOOP;


  END;
  /*********************************************************************************** */
  procedure P_ACTUALIZA_LOTE IS
  BEGIN
      execute immediate 'truncate table V_MAE_LOTE_QS';
      execute immediate 'insert into V_MAE_LOTE_QS '||
      '(CENTRO,ALMACEN,MATERIAL,DESCRIPCION,SUJ_LOTE,LOTE,FEC_CREA) '||
      'select CENTRO,ALMACEN,MATERIAL,DESCRIPCION,SUJ_LOTE,LOTE,FEC_CREA '||
      'from interfase_sap.V_MAE_LOTE_QS_XE_000@XE_000 a  where a.suj_lote = '||''''||'X'||''''||'';
      commit;
  END;
  /* ********************************************************************************* */
  PROCEDURE P_COMPLETA_SOBRANTE_ADICIONAL(CodiLoca_in   IN varchar2,
                                          Id_Entrega_in IN varchar2) AS

    vFilaGUIA  INT_CONFIRMACION_GUIAS%rowtype;
    vNumEntrega_in INT_CONFIRMACION_GUIAS.num_entrega%type := 'X';
    vExiste_Sob_Adicional number;

    vNPosActual number;
    vDescProd   lgt_prod.desc_prod%type;
    vLoteProd   V_MAE_LOTE_QS.LOTE%type;
    vCodProd_in lgt_prod.cod_prod%type;
  BEGIN
      -- Verifica que exista datos sobrantes adicionales
      select count(1)
      into    vExiste_Sob_Adicional
      from   (
                select ROWID ID,T.*
                from   AUX_INT_CONFIRMACION_GUIAS t
                where  t.cod_local  = CodiLoca_in
                and    t.id_entrega = Id_Entrega_in
                and    t.tipo       = 'SOBRANTE'
                and    t.num_entrega is null
               );

      if vExiste_Sob_Adicional > 0 then
        if vNumEntrega_in = 'X' then
          -- lo va asociar a una entrega cualquiera
            select t.num_entrega
            into   vNumEntrega_in
            from   AUX_INT_CONFIRMACION_GUIAS t
            where  t.cod_local = CodiLoca_in
            and    t.id_entrega = Id_Entrega_in
            and    t.num_entrega is not null
            and    rownum = 1;
        end if;

        -- obtiene la ultima fila de la entrega
        -- todos los valores de la fila cargada
        -- 4003571549  4003571549
        select *
        into   vFilaGUIA
        from   (
                select *
                from   AUX_INT_CONFIRMACION_GUIAS t
                where  t.cod_local = CodiLoca_in
                and    t.id_entrega = Id_Entrega_in
                and    t.num_entrega = vNumEntrega_in
                order by t.txt_16_pos_factura desc
               ) vt
        where  rownum = 1;

        vNPosActual := TO_NUMBER(vFilaGUIA.Txt_16_Pos_Factura,'000000');

       end if;

    DBMS_OUTPUT.put_line('...PRUEBA ..');
    --- ahora va recrear todos los registros
    --- debe de hacer update a los datos que estan en NULL
    FOR LISTA in (
                  select ROWID ID,T.*
                  from   AUX_INT_CONFIRMACION_GUIAS t
                  where  t.cod_local  = CodiLoca_in
                  and    t.id_entrega = Id_Entrega_in
                  and    t.tipo       = 'SOBRANTE'
                  and    t.num_entrega is null
                  ) loop
        vNPosActual := vNPosActual + 1;
    DBMS_OUTPUT.put_line('...vNPosActual ..'||vNPosActual);
        select upper(p.desc_prod),p.cod_prod
        into   vDescProd,vCodProd_in
        from   lgt_prod p
        where  p.cod_prod = lista.cod_producto;

        BEGIN
            select M.LOTE
            into   vLoteProd
            --from   V_MAE_LOTE_QS M
            -- lo sacara de matriz
            from   ptoventa.aux_mae_lote_qs@xe_000 m
            WHERE  M.MATERIAL = lista.cod_producto
            AND    M.SUJ_LOTE = 'X'
            AND    ROWNUM = 1;
        EXCEPTION
        WHEN OTHERS THEN
          vLoteProd := '';
        END ;
        /*
        posicion,
        el codigo del producto,
        descripcion,
        lote,
        cantidad confirmada,
        cantidad diferencia,
        fecha de confirmacion,
        fecha de envio de confirmacion
        */
        update  AUX_INT_CONFIRMACION_GUIAS v
        set     v.TXT_01_CTA_CLIENTE   =  vFilaGUIA.TXT_01_CTA_CLIENTE,
                v.TXT_02_DEST   =  vFilaGUIA.TXT_02_DEST,
                v.TXT_03_NOMB_DEST   =  vFilaGUIA.TXT_03_NOMB_DEST,
                v.TXT_04_DOC_VTA   =  vFilaGUIA.TXT_04_DOC_VTA,
                v.TXT_05_CLASE_PEDIDO   =  vFilaGUIA.TXT_05_CLASE_PEDIDO,
                v.TXT_06_FEC_CREA_PEDIDO   =  vFilaGUIA.TXT_06_FEC_CREA_PEDIDO,
                v.TXT_07_OC   =  vFilaGUIA.TXT_07_OC,
                v.TXT_08_NUM_ENTREGA   =  vFilaGUIA.TXT_08_NUM_ENTREGA,
                v.TXT_09_NUM_GUIA   =  vFilaGUIA.TXT_09_NUM_GUIA,
                v.TXT_10_FEC_CREA_ENTREGA   =  vFilaGUIA.TXT_10_FEC_CREA_ENTREGA,
                v.TXT_11_NUM_DOC_MAT_SM   =  vFilaGUIA.TXT_11_NUM_DOC_MAT_SM,
                v.TXT_12_FEC_CONTA_SM   =  vFilaGUIA.TXT_12_FEC_CONTA_SM,
                v.TXT_13_NUM_FACTURA_SAP   =  vFilaGUIA.TXT_13_NUM_FACTURA_SAP,
                v.TXT_14_FEC_FACT_SAP   =  vFilaGUIA.TXT_14_FEC_FACT_SAP,
                v.TXT_15_CLASE_FACTURA   =  vFilaGUIA.TXT_15_CLASE_FACTURA,
                v.TXT_16_POS_FACTURA   =  TRIM(TO_CHAR(vNPosActual,'000000')),--LISTA.TXT_16_POS_FACTURA,
                v.TXT_17_MATERIAL   =  trim(to_char(vCodProd_in,'000000000000000000')),--vFilaGUIA.TXT_17_MATERIAL,
                v.TXT_18_DESC_PROD   =  RPAD(Substr(TRIM(vDescProd),0,40),40,' '),--LISTA.TXT_18_DESC_PROD,
                v.TXT_19_CTD_FACTURADA   =  TO_CHAR(0,'99999999990.000')||' ',--LISTA.TXT_19_CTD_FACTURADA,
                v.TXT_20_UNIDAD_MEDIDA   =  vFilaGUIA.TXT_20_UNIDAD_MEDIDA,
                v.TXT_21_LOTE   =  RPAD(vLoteProd,10,' '),--LISTA.TXT_21_LOTE,
                v.TXT_22_FEC_VENCIMIENTO   =  vFilaGUIA.TXT_22_FEC_VENCIMIENTO,
                v.NAME_TXT   =  vFilaGUIA.NAME_TXT,
                v.FECHA_PROC_TXT   =  vFilaGUIA.FECHA_PROC_TXT,
                v.DIFERENCIA2   =  lista.DIFERENCIA2,
                v.DES_LABORATORIO   =  vFilaGUIA.DES_LABORATORIO,
                v.CTD   =  lista.ctd,
                v.NUM_GUIA   =  vFilaGUIA.NUM_GUIA,
                v.DES_PRODUCTO   =  vFilaGUIA.DES_PRODUCTO,
                v.NUM_LOTE   =  vFilaGUIA.NUM_LOTE,
                v.NUM_ENTREGA   =  vFilaGUIA.NUM_ENTREGA,
                v.COD_CODIGO_SAP   =  vFilaGUIA.COD_CODIGO_SAP,
                v.COD_LOCAL_SAP   =  vFilaGUIA.COD_LOCAL_SAP,
                v.FLG_COMPLETO   =  vFilaGUIA.FLG_COMPLETO,
                v.FECHA_PROCESO   =  vFilaGUIA.FECHA_PROCESO,
                v.FACTURA_OUT   =  vFilaGUIA.FACTURA_OUT
        where  v.cod_local  = CodiLoca_in
        and    v.id_entrega = Id_Entrega_in
        and    v.tipo       = 'SOBRANTE'
        and    v.rowid      = lista.id;

        --select * from   V_MAE_LOTE_QS;

       end loop;
  END;
  /* ********************************************************************************* */
end;
/

