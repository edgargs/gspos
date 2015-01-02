--------------------------------------------------------
--  DDL for Package Body MF_INT_VTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."MF_INT_VTA" AS
/* ************************************************************************** */
PROCEDURE INT_EJECT_RESUMEN_RANGO_DIA(cCodGrupoCia_in IN CHAR,
                                      vDesde_in IN VARCHAR2,
                                      vHasta_in IN VARCHAR2)
  as
  cCodLocal_in CHAR(3);
  begin

    UPDATE vta_pedido_vta_cab T
    SET    T.IND_CONV_BTL_MF = NULL
    WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    T.FEC_PED_VTA between TO_DATE(vDesde_in, 'DD/MM/YYYY') and TO_DATE(vHasta_in, 'DD/MM/YYYY')+ 1-1/24/60/60
    AND    T.IND_CONV_BTL_MF =  'N'
    and    T.IND_PED_CONVENIO = 'S';

    commit;


  select distinct l.cod_local
  into   cCodLocal_in
  from  vta_impr_ip l;

        for listados in (
                       SELECT *
                       FROM (select TO_DATE(vDesde_in, 'DD/MM/YYYY') + ROWNUM - 1 fecha
                       from PTOVENTA.LGT_PROD)
                       WHERE fecha <= TO_DATE(vHasta_in, 'DD/MM/YYYY')
                       order by 1 asc
                     )loop
          ------------------------------
          for lista in (
                                select distinct i.cod_local,i.documento,i.cod_tipo_documento,i.num_documento_refer,
                                       Substr(i.num_documento_refer,6,3) SERIE,
                                       Substr(i.num_documento_refer,10,7) NUM_INI,
                                       NVL(Substr(i.num_documento_refer,18,7),Substr(i.num_documento_refer,10,7)) NUM_FIN
                                from   ptoventa.int_vta_mf i
                                where  i.fch_venta = listados.fecha -1
                                order by 1 asc) loop

                        UPDATE ptoventa.VTA_COMP_PAGO nologging
                                SET NUM_SEC_DOC_SAP = lista.documento,
                                    FEC_PROCESO_SAP = SYSDATE,
                                    FEC_MOD_COMP_PAGO = SYSDATE,
                                    USU_MOD_COMP_PAGO = 'JB_INT_VENTA_24'
                                WHERE COD_GRUPO_CIA = '001'
                                AND   COD_LOCAL = lista.cod_local
                                AND   TIP_COMP_PAGO in DECODE(LISTA.cod_tipo_documento,1,'01',2,'02',7,'05')
                                AND   trim(Substr(num_comp_pago,1,3)) = lista.serie
                                AND   trim(Substr(num_comp_pago,4,10))*1 BETWEEN
                                                                           trim(lista.num_ini)*1 AND
                                                                           trim(lista.num_fin)*1;
                           commit;

                  end loop;
          ------------------------------

        mf_int_vta.aux_save_clie_sap_comp(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          TO_CHAR(listados.FECHA,'DD/MM/YYYY'));
        commit;

        ptoventa.mf_int_vta.INT_REVERTIR_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(listados.FECHA,'DD/MM/YYYY'),'DUBILLUZ');
        ptoventa.mf_int_vta.int_eject_resumen_dia(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(listados.FECHA,'DD/MM/YYYY'));
        for lista in (
                      select distinct i.cod_local,i.documento,i.cod_tipo_documento,i.num_documento_refer,
                             Substr(i.num_documento_refer,6,3) SERIE,
                             Substr(i.num_documento_refer,10,7) NUM_INI,
                             NVL(Substr(i.num_documento_refer,18,7),Substr(i.num_documento_refer,10,7)) NUM_FIN
                      from   ptoventa.int_vta_mf i
                      where  i.fch_venta = listados.fecha
                      order by 1 asc) loop

              UPDATE ptoventa.VTA_COMP_PAGO nologging
                      SET NUM_SEC_DOC_SAP = lista.documento,
                          FEC_PROCESO_SAP = SYSDATE,
                          FEC_MOD_COMP_PAGO = SYSDATE,
                          USU_MOD_COMP_PAGO = 'JB_INT_VENTA_24'
                      WHERE COD_GRUPO_CIA = '001'
                      AND   COD_LOCAL = lista.cod_local
                      AND   TIP_COMP_PAGO in DECODE(LISTA.cod_tipo_documento,1,'01',2,'02',7,'05')
                      AND   trim(Substr(num_comp_pago,1,3)) = lista.serie
                      AND   trim(Substr(num_comp_pago,4,10))*1 BETWEEN
                                                                 trim(lista.num_ini)*1 AND
                                                                 trim(lista.num_fin)*1;
                 commit;

        end loop;

        end loop;
end;
  /* ************************************************************************ */
PROCEDURE INT_EJECT_RESUMEN_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2)
  AS
    --CTIPDOC_IN CHAR(2);
    --CTIPDOCORIGEN_IN CHAR(2);

  vIni TIMESTAMP;

  valor number;  

  BEGIN
      select systimestamp
      into   vIni
      from   dual;

     DELETE TMP_AUX_DET_PEDIDO_INT_VTA nologging;
     DELETE TMP_ANULA_DET_PEDIDO_INT_VTA nologging;
     DELETE TMP_ANULA_DET_PED_NOTA_CREDITO nologging;
     COMMIT;

      SELECT  EXTRACT(second FROM (systimestamp - vIni))
      into    valor
      FROM    dual;

      dbms_output.put_line('eliminar datos 1 : '||valor)  ;

      -- obtiene la fecha desde que se inicio la interfaz de ventas nueva
      select nvl(min(i.fch_venta),to_date(vFecProceso_in,'dd/mm/yyyy'))
      into   nFch_Ini_New_int_vta
      from   int_vta_mf i;



  IF V_PREVIAS_INT_VTA(cCodGrupoCia_in, cCodLocal_in,vFecProceso_in) = 'S' THEN
        ---------------------------------------------------------
     select systimestamp
        into   vIni
        from   dual;
        ---indicador 'S' cuando es convenios


        --IF VALIDA_VTAS_CARGA THEN
          creaIntPorIndConvenio ( cCodGrupoCia_in, cCodLocal_in, vFecProceso_in , 'S' );
        --END IF;

      SELECT  EXTRACT(second FROM (systimestamp - vIni))
        into    valor
        FROM    dual;

        dbms_output.put_line('creaIntPorIndConvenio con S : '||valor)  ;

     select systimestamp
        into   vIni
        from   dual;

       --IF VALIDA_VTAS_CARGA THEN
        ---indicador 'N' cuando no es convenios, pago efectivo
        creaIntPorIndConvenio ( cCodGrupoCia_in, cCodLocal_in, vFecProceso_in , 'N' );

        --END IF;
     SELECT  EXTRACT(second FROM (systimestamp - vIni))
        into    valor
        FROM    dual;

        ---dbms_output.put_line('creaIntPorIndConvenio con N : '||valor)  ;
  ---------------------------------------------------------

      select systimestamp
      into   vIni
      from   dual;
      V_MONTO_VENTAS(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECPROCESO_IN);

      SELECT  EXTRACT(second FROM (systimestamp - vIni))
      into    valor
      FROM    dual;

      dbms_output.put_line('V_MONTO_VENTAS : '||valor)  ;

      commit;
      select systimestamp
      into   vIni
      from   dual;

    /************************************************/
    /*update   INT_VTA_MF i
    set   i.fechaemision = to_char(add_months(to_date(vFecProceso_in,'dd/mm/yyyy'),1),'yyyymmdd'),
          i.fch_venta  = to_char(add_months(to_date(vFecProceso_in,'dd/mm/yyyy'),1),'dd/mm/yyyy')
    where i.COD_GRUPO_CIA = cCodGrupoCia_in
    and   i.COD_LOCAL = cCodLocal_in
    and   i.fechaemision = to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd');

    commit;*/

      ---- GENERA_ARCHIVO(CCODGRUPOCIA_IN,CCODLOCAL_IN,to_char(add_months(to_date(vFecProceso_in,'dd/mm/yyyy'),1),'dd/mm/yyyy')/*VFECPROCESO_IN*/);
      
      GENERA_ARCHIVO(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECPROCESO_IN);

      mf_int_vta.grabaLog_java(CCODLOCAL_IN,VFECPROCESO_IN,'Exito Gen INT');

      SELECT  EXTRACT(second FROM (systimestamp - vIni))
      into    valor
      FROM    dual;

      dbms_output.put_line('GENERA_ARCHIVO : '||valor)  ;

    END IF;

      select systimestamp
      into   vIni
      from   dual;

     /*DELETE TMP_AUX_DET_PEDIDO_INT_VTA nologging;
     DELETE TMP_ANULA_DET_PEDIDO_INT_VTA nologging;
     DELETE TMP_ANULA_DET_PED_NOTA_CREDITO nologging;
     COMMIT;*/

      SELECT  EXTRACT(second FROM (systimestamp - vIni))
      into    valor
      FROM    dual;

      dbms_output.put_line('eliminar datos 2 : '||valor)  ;
EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      ----------------------------------------------------------------------------
      --MAIL DE ERROR DE INTERFACE VENTAS
      DELETE TMP_INT_VTA_MF I
      where i.cod_local = CCODLOCAL_IN
      and   i.FCH_VENTA = to_date(VFECPROCESO_IN,'dd/mm/yyyy');
      insert into TMP_INT_VTA_MF
      select * from int_vta_mf i
      where i.cod_local = CCODLOCAL_IN
      and   i.FCH_VENTA = to_date(VFECPROCESO_IN,'dd/mm/yyyy');


      ----------------------------------------------------------------------------
      mf_int_vta.int_revertir_ventas('001',CCODLOCAL_IN,VFECPROCESO_IN,'DUBILLUZ');
      commit;
      mf_int_vta.grabaLog_java(CCODLOCAL_IN,VFECPROCESO_IN,'Exito Gen INT - ERROR');
      mf_int_vta.grabaLog(CCODLOCAL_IN,VFECPROCESO_IN,'Error:'||SUBSTR(SQLERRM, 1, 250));

  END;

  /* *********************************************************************************** */
FUNCTION AUX_REEMPLAZAR_CHAR(vCadena_in IN VARCHAR2)
  RETURN VARCHAR2
  IS
    v_vCadenaOut VARCHAR2(250);
  BEGIN

    SELECT REGEXP_REPLACE(vCadena_in,'[^A-Z0-9.]',' ')
    INTO   v_vCadenaOut
    FROM   dual ;

    v_vCadenaOut := REPLACE(v_vCadenaOut,'Ç',' ');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ñ','N'),'ñ','n');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ä','A'),'ä','a');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ë','E'),'ë','e');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ï','I'),'ï','i');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ö','O'),'ö','o');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ü','U'),'ü','u');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Á','A'),'á','a');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'É','E'),'é','e');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Í','I'),'í','i');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ó','O'),'ó','o');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ú','U'),'ú','u');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'À','A'),'à','a');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'È','E'),'è','e');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ì','I'),'ì','i');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ò','O'),'ò','o');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ù','U'),'ù','u');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Â','A'),'â','a');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ê','E'),'ê','e');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Î','I'),'î','i');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ô','O'),'ô','o');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Û','U'),'û','u');

    v_vCadenaOut := REPLACE(v_vCadenaOut,'°',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'´',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'¨',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'~',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'^',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'`',' ');

    RETURN v_vCadenaOut;
  END;
  /* ****************************************************************************** */
PROCEDURE AUX_SAVE_DET_X_NO_AGRUPA (
                                    cCodGrupoCia_in in char,
                                    cCodLocal_in in char,
                                    cTipComp_in in char,
                                    cSerie_in in char,
                                    cFechProceso_in in char,
                                    cIndConvenio_in in char
                                   )
  AS
   cursor vCurDetalle is
select distinct c.cod_grupo_cia,c.cod_local,p.cod_cliente_sap,p.tip_comp_pago,p.num_comp_pago,p.sec_comp_pago,
                                 trim(Substr(p.num_comp_pago,1,3)) SERIE,
                                 trim(Substr(p.num_comp_pago,4,10))*1 sec_num_comp,
                                 c.num_ped_vta,
                                 100-
                                 (
                                   case
                                   -- 1. Este Caso es para Venta Convenio Credito 100 que emite 1 solo comprobante
                                   -- Ejemplo. Convenio BTL Credito 100%
                                   when p.pct_beneficiario =  0 and p.pct_empresa = 100 and nvl(p.tip_clien_convenio,'X') = '1' then
                                        100
                                   else decode(nvl(p.tip_clien_convenio,'X'),'1',p.pct_beneficiario,'2',p.pct_empresa,'X',100)
                                   end
                                 ) pct_dcto_vta,
                                 c.cod_convenio,p.ind_comp_credito,
                                 nvl(p.tip_clien_convenio,'0') tip_comp_cliente,
                                 nvl(p.ind_afecta_kardex,'S') IND_MUEVE_KARDEX,
                                 c.tip_ped_vta,
                                    decode(
                               p.tip_comp_pago, '05',
                               (
                               select m.serie_ticket
                               from   lgt_mae_ticket m
                               where  m.cod_grupo_cia = p.cod_grupo_cia
                               and    m.cod_local  = p.cod_local
                               and    m.serie_sistema = trim(Substr(p.num_comp_pago,1,3))
                               ),' ')  SERIE_TICK
                          from   vta_comp_pago  p,
                                 vta_pedido_vta_cab c
                          where  c.est_ped_vta = 'C'
                          and    c.cod_grupo_cia = cCodGrupoCia_in
                          and    c.cod_local = cCodLocal_in
                          AND    c.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy') AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                          --AND    (p.IND_COMP_ANUL = 'N' OR P.FEC_ANUL_COMP_PAGO > TO_DATE(cFechProceso_in,'dd/MM/yyyy')+1)
                          AND    C.NUM_PED_VTA_ORIGEN IS NULL
                          and    c.cod_grupo_cia = p.cod_grupo_cia
                          and    c.cod_local = p.cod_local
                          and    c.num_ped_vta = p.num_ped_vta
                          and    p.tip_comp_pago = COMP_FACTURA--cTipComp_in
                          and    p.num_comp_pago like cSerie_in||'%'
                          -----jquispe 17.09.2012 . filtro segun el indicador Convenio
                          and    c.ind_ped_convenio = cIndConvenio_in
                          and    nvl(c.ind_conv_btl_mf,c.ind_ped_convenio)  = cIndConvenio_in;

    listaCompTB vCurDetalle%ROWTYPE;

  BEGIN
      --for listaCompTB in vCurDetalle loop
      open vCurDetalle;
      LOOP
        FETCH vCurDetalle INTO listaCompTB;
        EXIT WHEN vCurDetalle%NOTFOUND;


--dbms_output.put_line('listaCompTB.Tip_Comp_Cliente-'||listaCompTB.Tip_Comp_Cliente);

        if listaCompTB.Tip_Comp_Cliente = DOC_EMPRESA then
          /* dbms_output.put_line('listaCompTB.Num_Ped_Vta-'||listaCompTB.Num_Ped_Vta);
           dbms_output.put_line('listaCompTB.Sec_Comp_Pago-'||listaCompTB.Sec_Comp_Pago);
                      dbms_output.put_line('listaCompTB.Tip_Comp_Pago-'||listaCompTB.Tip_Comp_Pago);
                      dbms_output.put_line('listaCompTB.Serie-'||listaCompTB.Serie);         */
              insert into TMP_AUX_DET_PEDIDO_INT_VTA nologging
              (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
               TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
               VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,TIP_PED_VTA,
               SERIE_TICKETERA)
              select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         --d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         NVL(d.val_prec_total_benef,d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100),
                         -- dubilluz 26.09.2013
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,
                         listaCompTB.SERIE_TICK
              from   vta_pedido_vta_det  d
              where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
              and    d.cod_local     = listaCompTB.Cod_Local
              and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
              and    d.sec_comp_pago_empre = listaCompTB.Sec_Comp_Pago;

        else

          if listaCompTB.Tip_Comp_Cliente = DOC_BENEFICIARIO then

                  insert into TMP_AUX_DET_PEDIDO_INT_VTA nologging
                  (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
                   TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
                   VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,
                   TIP_PED_VTA,
                   serie_ticketera)
                  select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         --d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         NVL(d.val_prec_total_empre,d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ),
                         --dubilluz 26.09.2013
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,
                         listaCompTB.SERIE_TICK
                  from   vta_pedido_vta_det  d
                  where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
                  and    d.cod_local     = listaCompTB.Cod_Local
                  and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
                  and    d.sec_comp_pago_benef = listaCompTB.Sec_Comp_Pago;

          else
                  insert into TMP_AUX_DET_PEDIDO_INT_VTA nologging
                  (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
                   TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
                   VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,
                   TIP_PED_VTA,SERIE_TICKETERA)
                  select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,
                         listaCompTB.SERIE_TICK
                  from   vta_pedido_vta_det  d
                  where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
                  and    d.cod_local     = listaCompTB.Cod_Local
                  and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
                  and    d.sec_comp_pago = listaCompTB.Sec_Comp_Pago;

          end if;
        end if;
      end loop;
      close vCurDetalle;
  END;

  /* *********************************************************************************** */
  PROCEDURE AUX_SAVE_DET_X_COMPROBANTE (
                                    cCodGrupoCia_in in char,
                                    cCodLocal_in in char,
                                    cTipComp_in in char,
                                    cSerie_in in char,
                                    cFechProceso_in in char,
                                    cIndConvenio_in in char
                                    )
  AS
   cursor vCurDetalle is
select distinct c.cod_grupo_cia,c.cod_local,p.cod_cliente_sap,p.tip_comp_pago,p.num_comp_pago,p.sec_comp_pago,
                                 trim(Substr(p.num_comp_pago,1,3)) SERIE,
                                 trim(Substr(p.num_comp_pago,4,10))*1 sec_num_comp,
                                 c.num_ped_vta,
                                 100-
                                 (
                                   case
                                   -- 1. Este Caso es para Venta Convenio Credito 100 que emite 1 solo comprobante
                                   -- Ejemplo. Convenio BTL Credito 100%
                                   when p.pct_beneficiario =  0 and p.pct_empresa = 100 and nvl(p.tip_clien_convenio,'X') = '1' then
                                        100
                                   else decode(nvl(p.tip_clien_convenio,'X'),'1',p.pct_beneficiario,'2',p.pct_empresa,'X',100)
                                   end
                                 ) pct_dcto_vta,
                                 c.cod_convenio,p.ind_comp_credito,
                                 nvl(p.tip_clien_convenio,'0') tip_comp_cliente,
                                 nvl(p.ind_afecta_kardex,'S') IND_MUEVE_KARDEX,
                                 c.tip_ped_vta,
                                    decode(
                               p.tip_comp_pago, '05',
                               (
                               select m.serie_ticket
                               from   lgt_mae_ticket m
                               where  m.cod_grupo_cia = p.cod_grupo_cia
                               and    m.cod_local  = p.cod_local
                               and    m.serie_sistema = trim(Substr(p.num_comp_pago,1,3))
                               ),' ')  SERIE_TICK
                          from   vta_comp_pago  p,
                                 vta_pedido_vta_cab c
                          where  c.est_ped_vta = 'C'
                          and    c.cod_grupo_cia = cCodGrupoCia_in
                          and    c.cod_local = cCodLocal_in
                          AND    c.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy') AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                          AND    (p.IND_COMP_ANUL = 'N' OR P.FEC_ANUL_COMP_PAGO > TO_DATE(cFechProceso_in,'dd/MM/yyyy')+1)
                          AND    C.NUM_PED_VTA_ORIGEN IS NULL
                          and    c.cod_grupo_cia = p.cod_grupo_cia
                          and    c.cod_local = p.cod_local
                          and    c.num_ped_vta = p.num_ped_vta
                          and    p.tip_comp_pago = cTipComp_in
                          and    p.num_comp_pago like cSerie_in||'%'
                          -----jquispe 17.09.2012 . filtro segun el indicador Convenio
                          and    c.ind_ped_convenio = cIndConvenio_in
                          and    nvl(c.ind_conv_btl_mf,c.ind_ped_convenio)  = cIndConvenio_in
union
select distinct c.cod_grupo_cia,c.cod_local,p.cod_cliente_sap,p.tip_comp_pago,p.num_comp_pago,p.sec_comp_pago,
                                 trim(Substr(p.num_comp_pago,1,3)) SERIE,
                                 trim(Substr(p.num_comp_pago,4,10))*1 sec_num_comp,
                                 c.num_ped_vta,
                                 100-
                                 (
                                   case
                                   -- 1. Este Caso es para Venta Convenio Credito 100 que emite 1 solo comprobante
                                   -- Ejemplo. Convenio BTL Credito 100%
                                   when p.pct_beneficiario =  0 and p.pct_empresa = 100 and nvl(p.tip_clien_convenio,'X') = '1' then
                                        100
                                   else decode(nvl(p.tip_clien_convenio,'X'),'1',p.pct_beneficiario,'2',p.pct_empresa,'X',100)
                                   end
                                 ) pct_dcto_vta,
                                 c.cod_convenio,p.ind_comp_credito,
                                 nvl(p.tip_clien_convenio,'0') tip_comp_cliente,
                                 nvl(p.ind_afecta_kardex,'S') IND_MUEVE_KARDEX,
                                 c.tip_ped_vta,
                                    decode(
                               p.tip_comp_pago, '05',
                               (
                               select m.serie_ticket
                               from   lgt_mae_ticket m
                               where  m.cod_grupo_cia = p.cod_grupo_cia
                               and    m.cod_local  = p.cod_local
                               and    m.serie_sistema = trim(Substr(p.num_comp_pago,1,3))
                               ),' ')  SERIE_TICK
                          from   vta_comp_pago  p,
                                 vta_pedido_vta_cab c
                          where  c.est_ped_vta = 'C'
                          and    c.cod_grupo_cia = cCodGrupoCia_in
                          and    c.cod_local = cCodLocal_in
                          AND    c.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy') AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                          AND    P.FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy') AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                          AND    C.NUM_PED_VTA_ORIGEN IS NULL
                          and    c.cod_convenio is not null
                          and    c.cod_grupo_cia = p.cod_grupo_cia
                          and    c.cod_local = p.cod_local
                          and    c.num_ped_vta = p.num_ped_vta
                          and    p.tip_comp_pago = cTipComp_in
                          and    p.num_comp_pago like cSerie_in||'%'
                          -----jquispe 17.09.2012 . filtro segun el indicador Convenio
                          and    c.ind_ped_convenio = cIndConvenio_in
                          and    nvl(c.ind_conv_btl_mf,c.ind_ped_convenio)  = cIndConvenio_in;

    listaCompTB vCurDetalle%ROWTYPE;

  BEGIN
      --for listaCompTB in vCurDetalle loop
      open vCurDetalle;
      LOOP
        FETCH vCurDetalle INTO listaCompTB;
        EXIT WHEN vCurDetalle%NOTFOUND;

--dbms_output.put_line('deta anula comp >>> ');
--dbms_output.put_line('lis -'||listaCompTB.Serie||'>'||listaCompTB.Num_Comp_Pago);

        if listaCompTB.Tip_Comp_Cliente = DOC_EMPRESA then
          /* dbms_output.put_line('listaCompTB.Num_Ped_Vta-'||listaCompTB.Num_Ped_Vta);
           dbms_output.put_line('listaCompTB.Sec_Comp_Pago-'||listaCompTB.Sec_Comp_Pago);
                      dbms_output.put_line('listaCompTB.Tip_Comp_Pago-'||listaCompTB.Tip_Comp_Pago);
                      dbms_output.put_line('listaCompTB.Serie-'||listaCompTB.Serie);         */
              insert into TMP_AUX_DET_PEDIDO_INT_VTA nologging
              (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
               TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
               VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,TIP_PED_VTA,
               SERIE_TICKETERA)
              select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         --d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         -- descuento de empresa es de la siguiente manera
                         --d.val_prec_vta - d.val_prec_total_empre,
                         NVL(d.val_prec_total_benef,d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100),
                         -- dubilluz 26.09.2013
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,
                         listaCompTB.SERIE_TICK
              from   vta_pedido_vta_det  d
              where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
              and    d.cod_local     = listaCompTB.Cod_Local
              and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
              and    d.sec_comp_pago_empre = listaCompTB.Sec_Comp_Pago;

        else

          if listaCompTB.Tip_Comp_Cliente = DOC_BENEFICIARIO then

                  insert into TMP_AUX_DET_PEDIDO_INT_VTA nologging
                  (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
                   TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
                   VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,
                   TIP_PED_VTA,
                   serie_ticketera)
                  select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         --d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         -- descuento de empresa es de la siguiente manera
                         --d.val_prec_vta - d.val_prec_total_benef,
                         NVL(d.val_prec_total_empre,d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100),
                         -- dubilluz 26.09.2013
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,
                         listaCompTB.SERIE_TICK
                  from   vta_pedido_vta_det  d
                  where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
                  and    d.cod_local     = listaCompTB.Cod_Local
                  and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
                  and    d.sec_comp_pago_benef = listaCompTB.Sec_Comp_Pago;

          else
                  insert into TMP_AUX_DET_PEDIDO_INT_VTA nologging
                  (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
                   TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
                   VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,
                   TIP_PED_VTA,SERIE_TICKETERA)
                  select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,
                         listaCompTB.SERIE_TICK
                  from   vta_pedido_vta_det  d
                  where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
                  and    d.cod_local     = listaCompTB.Cod_Local
                  and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
                  and    d.sec_comp_pago = listaCompTB.Sec_Comp_Pago;

          end if;
        end if;
      end loop;
      close vCurDetalle;
  END;
  /****************************************************************************/
  PROCEDURE AUX_SAVE_DET_ANULA_X_COMP (
                                      cCodGrupoCia_in in char,
                                      cCodLocal_in in char,
                                      cTipComp_in in char,
                                      cSerie_in in char,
                                      cFechProceso_in in char,
                                      cIndConvenio_in  in char
                                      )
  AS

  cursor vCurDet is
select distinct c.cod_grupo_cia,c.cod_local,p.cod_cliente_sap,p.tip_comp_pago,p.num_comp_pago,p.sec_comp_pago,
                                 trim(Substr(p.num_comp_pago,1,3)) SERIE,
                                 trim(Substr(p.num_comp_pago,4,10))*1 sec_num_comp,
                                 c.num_ped_vta,
                                 100-
                                 (
                                   case
                                   -- 1. Este Caso es para Venta Convenio Credito 100 que emite 1 solo comprobante
                                   -- Ejemplo. Convenio BTL Credito 100%
                                   when p.pct_beneficiario =  0 and p.pct_empresa = 100 and nvl(p.tip_clien_convenio,'X') = '1' then
                                        100
                                   else decode(nvl(p.tip_clien_convenio,'X'),'1',p.pct_beneficiario,'2',p.pct_empresa,'X',100)
                                   end
                                 ) pct_dcto_vta,
                                 c.cod_convenio,p.ind_comp_credito,
                                 nvl(p.tip_clien_convenio,'0') tip_comp_cliente,
                                 nvl(p.ind_afecta_kardex,'S') IND_MUEVE_KARDEX,
                                 c.tip_ped_vta,
                                  --ped_anula.num_ped_vta anul_num_ped,
                                 c.num_ped_vta_origen anul_num_ped,
                                 p.NUM_SEC_DOC_SAP,
                                 --ped_anula.fec_pedido_negativo,
                                 trunc(ped_anula.fec_pedido_negativo)as "FECHA_PEDIDO_NEGATIVO" ,
                                 c.fec_ped_vta,
                                 c.ind_conv_btl_mf
                          from   vta_comp_pago  p,
                                 vta_pedido_vta_cab c,
                                 (
                                 --se obtiene los pedidos anulados para obtener los datos del original
                                 select t.cod_grupo_cia,t.cod_local,t.num_ped_vta,t.num_ped_vta_origen,t.fec_ped_vta fec_pedido_negativo
                                 from   vta_pedido_vta_cab t
                                 where  t.est_ped_vta = 'C'
                                 and    t.cod_grupo_cia = cCodGrupoCia_in
                                 and    t.cod_local = cCodLocal_in
                                 AND    T.NUM_PED_VTA_ORIGEN IS NOT NULL
                                                                 AND    t.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy')
                                                          AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                                 ) ped_anula
                          where  c.est_ped_vta = 'C'
                          and    c.cod_grupo_cia = cCodGrupoCia_in
                          and    c.cod_local = cCodLocal_in
                          --AND    c.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy') AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                          --AND    (p.IND_COMP_ANUL = 'N' OR p.FEC_ANUL_COMP_PAGO > TO_DATE(cFechProceso_in,'dd/MM/yyyy')+1)
                          AND    P.num_comp_pago like cSerie_in||'%'
                          ---- indicador si es anulado el comp ---
                          ---- INICIO ----
                          AND    p.IND_COMP_ANUL='S'
                          AND    p.FEC_ANUL_COMP_PAGO IS NOT NULL
                          --AND    p.NUM_SEC_DOC_SAP IS NOT NULL
                          AND    p.NUM_SEC_DOC_SAP_ANUL IS NULL
                          ---- FIN ----
                          and    c.cod_grupo_cia = p.cod_grupo_cia
                          and    c.cod_local = p.cod_local
                          and    c.num_ped_vta = p.num_ped_vta
                          and    p.tip_comp_pago = cTipComp_in
                          AND    ped_anula.cod_grupo_cia = c.cod_grupo_cia
                          and    ped_anula.cod_local = c.cod_local
                          and    ped_anula.num_ped_vta_origen = c.num_ped_vta
                          and    ( p.cod_grupo_cia,p.cod_local,p.num_ped_vta,p.num_comp_pago)
                                        not in
                                        (
                                        select ca.cod_grupo_cia,ca.cod_local,ca.num_ped_vta,cp.num_ped_vta
                                        from   vta_pedido_vta_cab ca,
                                               vta_comp_pago cp
                                        where  ca.cod_grupo_cia =   cCodGrupoCia_in
                                        and    ca.cod_local = cCodLocal_in
                                        and    ca.est_ped_vta = 'C'
                                        AND    ca.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy')
                                                                  AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                                        and    ca.cod_grupo_cia = cp.cod_grupo_cia
                                        and    ca.cod_local = cp.cod_local
                                        and    ca.num_ped_vta = cp.num_ped_vta
                                        and    cp.tip_comp_pago not in  ('05','01')
                                        union
                                        select ca.cod_grupo_cia,ca.cod_local,ca.num_ped_vta,cp.num_ped_vta
                                        from   vta_pedido_vta_cab ca,
                                               vta_comp_pago cp
                                        where  ca.cod_grupo_cia =   cCodGrupoCia_in
                                        and    ca.cod_local = cCodLocal_in
                                        and    ca.est_ped_vta = 'C'
                                        AND    ca.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy')
                                                                  AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                                        and    ca.cod_grupo_cia = cp.cod_grupo_cia
                                        and    ca.cod_local = cp.cod_local
                                        and    ca.num_ped_vta = cp.num_ped_vta
                                        and    ca.cod_convenio is not null
                                        and    nvl(ca.ind_conv_btl_mf,'N') = 'S'
                                        and    cp.tip_comp_pago in  ('05','01')
                                        )
                          -----jquispe 17.09.2012 . filtro segun el indicador Convenio
                          and    c.ind_ped_convenio = cIndConvenio_in
                          and    nvl(c.ind_conv_btl_mf,ind_ped_convenio)  = cIndConvenio_in;
  listaCompTB vCurDet%rowtype;

  vPermitoGrabar char(1) := 'S';
  BEGIN

    open vCurDet;
    LOOP
      FETCH vCurDet INTO listaCompTB;
      EXIT WHEN vCurDet%NOTFOUND;

      vPermitoGrabar := 'S';
      dbms_output.put_line('proc anula '||listaCompTB.Num_Comp_Pago|| ' - '|| listaCompTB.Tip_Comp_Pago
                           ||'-'||listaCompTB.Cod_Convenio
                           ||'-'||listaCompTB.ind_conv_btl_mf
                           ||'-'||listaCompTB.Tip_Comp_Cliente
                           ||'-NUm-'||listaCompTB.Num_Ped_Vta
                           ||'-Sec-'||listaCompTB.Sec_Comp_Pago
                           );
      -----------------------------------------------
      if cTipComp_in = COMP_BOLETA or  cTipComp_in = COMP_TK_BOL  then
         -- revisar si es anulado del dia
         if trunc(listaCompTB.fec_ped_vta) = trunc(to_date(cFechProceso_in,'dd/mm/yyyy'))
           and (listaCompTB.Cod_Convenio is null and nvl(listaCompTB.ind_conv_btl_mf,'N') = 'N')
           then
           -- es del mismo dia la anulacion NO DEBE DE GRABAR DETALLE
           vPermitoGrabar := 'N';
           dbms_output.put_line('...aqi');
         end if;
      end if;

      -----------------------------------------------

      if vPermitoGrabar = 'S' then
        if listaCompTB.Tip_Comp_Cliente = DOC_EMPRESA then
          /* dbms_output.put_line('listaCompTB.Num_Ped_Vta-'||listaCompTB.Num_Ped_Vta);
           dbms_output.put_line('listaCompTB.Sec_Comp_Pago-'||listaCompTB.Sec_Comp_Pago);
                      dbms_output.put_line('listaCompTB.Tip_Comp_Pago-'||listaCompTB.Tip_Comp_Pago);
                      dbms_output.put_line('listaCompTB.Serie-'||listaCompTB.Serie);         */
              insert into TMP_ANULA_DET_PEDIDO_INT_VTA nologging
              (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
               TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
               VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,TIP_PED_VTA,NT_NUM_PED_VTA,NUM_SEC_DOC_SAP)
              select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                     listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                     ------------------------------------------
                     d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                     d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                     -------------------------------------------
                     d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                     d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                     --d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                     NVL(d.val_prec_total_benef,d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ),
                     -- dubilluz 26.09.2013
                     listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,listaCompTB.anul_num_ped,listaCompTB.NUM_SEC_DOC_SAP
              from   vta_pedido_vta_det  d
              where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
              and    d.cod_local     = listaCompTB.Cod_Local
              and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
              and    d.sec_comp_pago_empre = listaCompTB.Sec_Comp_Pago;

        else

          if listaCompTB.Tip_Comp_Cliente = DOC_BENEFICIARIO then

                  insert into TMP_ANULA_DET_PEDIDO_INT_VTA nologging
                  (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
                   TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
                   VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,TIP_PED_VTA,NT_NUM_PED_VTA,NUM_SEC_DOC_SAP)
                  select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         --d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         nvl(d.val_prec_total_empre,d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100),
                         -- dubilluz 26.09.2013
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,listaCompTB.anul_num_ped,listaCompTB.NUM_SEC_DOC_SAP
                  from   vta_pedido_vta_det  d
                  where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
                  and    d.cod_local     = listaCompTB.Cod_Local
                  and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
                  and    d.sec_comp_pago_benef = listaCompTB.Sec_Comp_Pago;

          else
          --dbms_output.put_line('listaCompTB.Num_Ped_Vta'||listaCompTB.Num_Ped_Vta);
          --dbms_output.put_line('listaCompTB.Sec_Comp_Pago'||listaCompTB.Sec_Comp_Pago);
                  insert into TMP_ANULA_DET_PEDIDO_INT_VTA nologging
                  (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
                   TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
                   VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,TIP_PED_VTA,NT_NUM_PED_VTA,NUM_SEC_DOC_SAP)
                  select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,listaCompTB.anul_num_ped,listaCompTB.NUM_SEC_DOC_SAP
                  from   vta_pedido_vta_det  d
                  where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
                  and    d.cod_local     = listaCompTB.Cod_Local
                  and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
                  and    d.sec_comp_pago = listaCompTB.Sec_Comp_Pago;

          end if;
        end if;
       end if;
      end loop;
      close vCurDet;
  END;
  /****************************************************************************/
PROCEDURE AUX_SAVE_DET_NOTA_CREDITO (
                                      cCodGrupoCia_in in char,
                                      cCodLocal_in in char,
                                      cTipComp_in in char,
                                      cSerie_in in char,
                                      cFechProceso_in in char,
                                      cIndConvenio_in in char
                                    )
  AS

  cursor vCurDet is
select distinct c.cod_grupo_cia,c.cod_local,p.cod_cliente_sap,p.tip_comp_pago,p.num_comp_pago,p.sec_comp_pago,
                                 trim(Substr(p.num_comp_pago,1,3)) SERIE,
                                 trim(Substr(p.num_comp_pago,4,10))*1 sec_num_comp,
                                 c.num_ped_vta,
                                 100-
                                 (
                                   case
                                   -- 1. Este Caso es para Venta Convenio Credito 100 que emite 1 solo comprobante
                                   -- Ejemplo. Convenio BTL Credito 100%
                                   when p.pct_beneficiario =  0 and p.pct_empresa = 100 and nvl(p.tip_clien_convenio,'X') = '1' then
                                        100
                                   else decode(nvl(p.tip_clien_convenio,'X'),'1',p.pct_beneficiario,'2',p.pct_empresa,'X',100)
                                   end
                                 ) pct_dcto_vta,
                                 c.cod_convenio,p.ind_comp_credito,
                                 nvl(p.tip_clien_convenio,'0') tip_comp_cliente,
                                 nvl(p.ind_afecta_kardex,'S') IND_MUEVE_KARDEX,
                                 c.tip_ped_vta,
                                 c.num_ped_vta_origen anul_num_ped,
                                 --------------
                                 oc.tip_comp_pago tip_comp_pago_origen,
                                 oc.NUM_SEC_DOC_SAP num_sec_doc_sap_origen,
                                 oc.num_comp_pago num_comp_pago_origen,
                                 oc.sec_comp_pago sec_comp_origen
                                 /*(
                                 select oc.tip_comp_pago
                                 from  vta_comp_pago oc
                                 where oc.cod_grupo_cia = c.cod_grupo_cia
                                 and   oc.cod_local = c.cod_local
                                 and   oc.num_ped_vta = c.num_ped_vta_origen
                                 and   oc.sec_comp_pago in ( select distinct od.sec_comp_pago_origen
                                                             from  vta_pedido_vta_det od
                                                             where od.cod_grupo_cia = c.cod_grupo_cia
                                                             and   od.cod_local = c.cod_local
                                                             and   od.num_ped_vta = c.num_ped_vta )
                                 ) tip_comp_pago_origen,
                                 (
                                 select oc.old_NUM_SEC_DOC_SAP
                                 from  vta_comp_pago oc
                                 where oc.cod_grupo_cia = c.cod_grupo_cia
                                 and   oc.cod_local = c.cod_local
                                 and   oc.num_ped_vta = c.num_ped_vta_origen
                                 and   oc.sec_comp_pago in ( select distinct od.sec_comp_pago_origen
                                                             from  vta_pedido_vta_det od
                                                             where od.cod_grupo_cia = c.cod_grupo_cia
                                                             and   od.cod_local = c.cod_local
                                                             and   od.num_ped_vta = c.num_ped_vta )
                                 ) num_sec_doc_sap_origen,
                                 (
                                 select oc.num_comp_pago
                                 from  vta_comp_pago oc
                                 where oc.cod_grupo_cia = c.cod_grupo_cia
                                 and   oc.cod_local = c.cod_local
                                 and   oc.num_ped_vta = c.num_ped_vta_origen
                                 and   oc.sec_comp_pago in ( select distinct od.sec_comp_pago_origen
                                                             from  vta_pedido_vta_det od
                                                             where od.cod_grupo_cia = c.cod_grupo_cia
                                                             and   od.cod_local = c.cod_local
                                                             and   od.num_ped_vta = c.num_ped_vta )
                                 ) num_comp_pago_origen        */
                          from   vta_comp_pago  p,
                                 vta_pedido_vta_cab c,

                                 ------------------------------------
                                 vta_comp_pago OC,
                                 (
                                   SELECT DISTINCT DE.COD_GRUPO_CIA,DE.COD_LOCAL,DE.NUM_PED_VTA,
                                   DE.SEC_COMP_PAGO,
                                   DE.SEC_COMP_PAGO_ORIGEN
                                   FROM   vta_pedido_vta_det DE
                                 ) OD
                                 ------------------------------------
                          where  c.est_ped_vta = 'C'
                          AND    c.NUM_PED_VTA_ORIGEN IS NOT NULL
                          AND    c.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy')
                                                  AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                          and    c.tip_comp_pago = cTipComp_in
                          and    c.cod_grupo_cia = cCodGrupoCia_in
                          and    c.cod_local = cCodLocal_in
                          --AND    c.FEC_PED_VTA BETWEEN TO_DATE(cFechProceso_in,'dd/MM/yyyy') AND TO_DATE(cFechProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                          --AND    (p.IND_COMP_ANUL = 'N' OR p.FEC_ANUL_COMP_PAGO > TO_DATE(cFechProceso_in,'dd/MM/yyyy')+1)
                          AND    P.num_comp_pago like cSerie_in||'%'
                          ---- indicador si es anulado el comp ---
                          ---- INICIO ----
                          and    p.tip_comp_pago = cTipComp_in
                          AND    p.NUM_SEC_DOC_SAP IS NULL
                          ---- FIN ----
                          and    c.cod_grupo_cia = p.cod_grupo_cia
                          and    c.cod_local = p.cod_local
                          and    c.num_ped_vta = p.num_ped_vta


                          AND  oc.cod_grupo_cia = c.cod_grupo_cia
                           and   oc.cod_local = c.cod_local
                           and   oc.num_ped_vta = c.num_ped_vta_origen
                           AND   OC.sec_comp_pago = od.sec_comp_pago_origen

                           AND od.cod_grupo_cia = c.cod_grupo_cia
                           and   od.cod_local = c.cod_local
                           and   od.num_ped_vta = c.num_ped_vta

                           AND   OD.SEC_COMP_PAGO = P.SEC_COMP_PAGO
                          -- AND   P.SEC_COMP_PAGO  = od.sec_comp_pago_origen
                           -----jquispe 17.09.2012 . filtro segun el indicador Convenio
                          AND    c.ind_ped_convenio = cIndConvenio_in
                          AND    nvl(c.ind_conv_btl_mf,c.ind_ped_convenio)  = cIndConvenio_in;

    listaCompTB vCurDet%rowtype;

  BEGIN
      open vCurDet;
      LOOP
        FETCH vCurDet INTO listaCompTB;
        EXIT WHEN vCurDet%NOTFOUND;

        if listaCompTB.Tip_Comp_Cliente = DOC_EMPRESA then
          /* dbms_output.put_line('listaCompTB.Num_Ped_Vta-'||listaCompTB.Num_Ped_Vta);
           dbms_output.put_line('listaCompTB.Sec_Comp_Pago-'||listaCompTB.Sec_Comp_Pago);
                      dbms_output.put_line('listaCompTB.Tip_Comp_Pago-'||listaCompTB.Tip_Comp_Pago);
                      dbms_output.put_line('listaCompTB.Serie-'||listaCompTB.Serie);         */
              insert into TMP_ANULA_DET_PED_NOTA_CREDITO nologging
              (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
               TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
               VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,TIP_PED_VTA,NT_NUM_PED_VTA,
               ORIGEN_TIP_COMP_PAGO,ORIGEN_NUM_SEC_DOC_SAP,ORIGEN_NUM_COMP_PAGO,SEC_COMP_PAGO_origen)
              select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                     listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                     ------------------------------------------
                     d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                     d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                     -------------------------------------------
                     d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                     d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                     --d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                     nvl(d.val_prec_total_benef,d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100) ,
                     --dubilluz 26.09.2013
                     listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,listaCompTB.anul_num_ped,
                     listaCompTB.Tip_Comp_Pago_Origen,listaCompTB.Num_Sec_Doc_Sap_Origen,listaCompTB.num_comp_pago_origen,
                     listaCompTB.Sec_Comp_Origen
              from   vta_pedido_vta_det  d
              where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
              and    d.cod_local     = listaCompTB.Cod_Local
              and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
              and    d.sec_comp_pago_empre = listaCompTB.Sec_Comp_Pago
                  ---  ---- ---
                  and    d.sec_comp_pago_origen = listaCompTB.Sec_Comp_Origen;

        else

          if listaCompTB.Tip_Comp_Cliente = DOC_BENEFICIARIO then

                  insert into TMP_ANULA_DET_PED_NOTA_CREDITO nologging
                  (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
                   TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
                   VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,TIP_PED_VTA,NT_NUM_PED_VTA,
                   ORIGEN_TIP_COMP_PAGO,ORIGEN_NUM_SEC_DOC_SAP,ORIGEN_NUM_COMP_PAGO,SEC_COMP_PAGO_origen)
                  select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         --d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         nvl(d.val_prec_total_empre,d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100),
                         --dubilluz 26.09.2013
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,listaCompTB.Anul_Num_Ped,
                         listaCompTB.Tip_Comp_Pago_Origen,listaCompTB.Num_Sec_Doc_Sap_Origen,listaCompTB.num_comp_pago_origen,
                     listaCompTB.Sec_Comp_Origen
                  from   vta_pedido_vta_det  d
                  where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
                  and    d.cod_local     = listaCompTB.Cod_Local
                  and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
                  and    d.sec_comp_pago_benef = listaCompTB.Sec_Comp_Pago
                  ---  ---- ---
                  and    d.sec_comp_pago_origen = listaCompTB.Sec_Comp_Origen;

          else
                  insert into TMP_ANULA_DET_PED_NOTA_CREDITO nologging
                  (COD_CLIENTE_SAP,TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_COMP_PAGO,SERIE,SEC_NUM_COMP,PCT_DCTO_VTA,IND_COMP_CREDITO,
                   TIP_COMP_CLIENTE,COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,OLD_VAL_PREC_VTA,OLD_VAL_PREC_TOTAL,
                   VAL_FRAC,VAL_IGV,NEW_VAL_PREC_VTA,NEW_VAL_PREC_TOTAL,NEW_DCTO_X_PROD,IND_MUEVE_KARDEX,TIP_PED_VTA,NT_NUM_PED_VTA,
               ORIGEN_TIP_COMP_PAGO,ORIGEN_NUM_SEC_DOC_SAP,ORIGEN_NUM_COMP_PAGO,SEC_COMP_PAGO_origen)
                  select listaCompTB.Cod_Cliente_Sap,listaCompTB.Tip_Comp_Pago,listaCompTB.Num_Comp_Pago,listaCompTB.Sec_Comp_Pago,
                         listaCompTB.Serie,listaCompTB.Sec_Num_Comp,listaCompTB.Pct_Dcto_Vta,listaCompTB.Ind_Comp_Credito,listaCompTB.Tip_Comp_Cliente,
                         ------------------------------------------
                         d.COD_GRUPO_CIA,d.COD_LOCAL,d.NUM_PED_VTA,d.SEC_PED_VTA_DET,d.COD_PROD,d.CANT_ATENDIDA,
                         d.VAL_PREC_VTA,d.VAL_PREC_TOTAL,d.VAL_FRAC,d.VAL_IGV,
                         -------------------------------------------
                         d.VAL_PREC_VTA*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(100-listaCompTB.Pct_Dcto_Vta)/100,
                         d.VAL_PREC_TOTAL*(listaCompTB.Pct_Dcto_Vta)/100 ,
                         listaCompTB.IND_MUEVE_KARDEX,listaCompTB.Tip_Ped_Vta,listaCompTB.Anul_Num_Ped,
                     listaCompTB.Tip_Comp_Pago_Origen,listaCompTB.Num_Sec_Doc_Sap_Origen,listaCompTB.num_comp_pago_origen,
                     listaCompTB.Sec_Comp_Origen
                  from   vta_pedido_vta_det  d
                  where  d.cod_grupo_cia = listaCompTB.Cod_Grupo_Cia
                  and    d.cod_local     = listaCompTB.Cod_Local
                  and    d.num_ped_vta   = listaCompTB.Num_Ped_Vta
                  and    d.sec_comp_pago = listaCompTB.Sec_Comp_Pago
                  ---  ---- ---
                  and    d.sec_comp_pago_origen = listaCompTB.Sec_Comp_Origen;
          end if;
        end if;
      end loop;

  END;
  /****************************************************************************/
  PROCEDURE AUX_AGRUP_0105_MUEVE_KARDEX(
                           cCodGrupoCia_in in char,
                           cCodLocal_in    in char,
                           cTipComp_in     in char,
                           cSerie_in       in char,
                           cCodCliSAP_in   in char
                           )
  AS
      nItemQuiebre number(10) := 450;
      --vCadenaProducto varchar2(30000) := '';
     -- vCadenaNumComp  varchar2(30000) := '';
      vGrupo_in number(15) := 0;
      pos number(4):= 0;
      nCantItems number(10);

      cursor vCurDetComp is
      select  TIP_COMP_PAGO,num_comp_pago,
                                       sec_comp_pago,
                                       serie,sec_num_comp,
                                       SEC_PED_VTA_DET,
                                       cod_prod,
                                       num_ped_vta,
                                       lead(sec_num_comp,1) over (order by serie asc,sec_num_comp asc,sec_ped_vta_det asc) pos_sec_num_comp,
                                       lead(cod_prod,1) over (order by serie asc,sec_num_comp asc,sec_ped_vta_det asc) pos_cod_prod,
                                       lead(TIP_PED_VTA,1) over (order by serie asc,sec_num_comp asc,sec_ped_vta_det asc) pos_TIP_PED_VTA,
                                       TIP_PED_VTA
                                from   TMP_AUX_DET_PEDIDO_INT_VTA t
                                where  COD_GRUPO_CIA = cCodGrupoCia_in
                                and    COD_LOCAL     = cCodLocal_in
                                and    tip_comp_pago = cTipComp_in
                                and    serie = cSerie_in
                                and    ind_mueve_kardex = 'S'
                                and    grupo_doc is null
                                and    t.cod_cliente_sap = cCodCliSAP_in ;

    det_serie vCurDetComp%ROWTYPE;

    existe number;
    existe_comp number;
  BEGIN
      -- t.grupo_doc esta compuesto de
      -- [TIP COMPROBANTE][SERIE][NUMERAL]
      begin
        select to_number(cTipComp_in||cSerie_in||
               lpad(nvl(decode(max(nvl(Substr(t.grupo_doc,5),0)),0,0,max(nvl(Substr(t.grupo_doc,5),0))+1),'0'),2,'0')
               ,'9999990')
        into   vGrupo_in
        from   TMP_AUX_DET_PEDIDO_INT_VTA t
        WHERE  T.TIP_COMP_PAGO = cTipComp_in
        and    t.serie = cSerie_in
        and    t.cod_grupo_cia = cCodGrupoCia_in
        and    t.cod_local = cCodLocal_in
        and    t.grupo_doc is not null
        and    t.cod_cliente_sap = cCodCliSAP_in
        and    t.ind_mueve_kardex = 'S';
      exception
      when no_data_found then
        vGrupo_in := to_number(cTipComp_in||cSerie_in||'00','99999990');
      end;

          --for det_serie in vCurDetComp loop
      open vCurDetComp;
      LOOP
        FETCH vCurDetComp INTO det_serie;
        EXIT WHEN vCurDetComp%NOTFOUND;

          -- GRUPO_DOC
          if  pos = 0 then

              --vCadenaProducto := det_serie.cod_prod||'-'||det_serie.tip_ped_vta;
              insert into TMP_AUX_PROD_AGRUPA nologging (COD_PROD_AUX)
              values (det_serie.cod_prod||'-'||det_serie.tip_ped_vta);

              --vCadenaNumComp := det_serie.sec_num_comp;

              insert into TMP_AUX_COMP_PAGO nologging (SEC_NUM_COMP)
              values (det_serie.sec_num_comp);

              pos := pos + 1;
          else
              -- el producto no existe
              select count(1)
              into   existe
              from   TMP_AUX_PROD_AGRUPA a
              where  a.cod_prod_aux in (det_serie.cod_prod||'-'||det_serie.tip_ped_vta);

              --if instr(vCadenaProducto,(det_serie.cod_prod||'-'||det_serie.tip_ped_vta)) = 0 then
              if existe = 0 then
                 --vCadenaProducto := vCadenaProducto || '@' ||det_serie.cod_prod||'-'||det_serie.tip_ped_vta;
                 insert into TMP_AUX_PROD_AGRUPA nologging (COD_PROD_AUX)
                 values (det_serie.cod_prod||'-'||det_serie.tip_ped_vta);

                 --vCadenaNumComp := vCadenaNumComp || '@' ||det_serie.sec_num_comp;

                 insert into TMP_AUX_COMP_PAGO nologging (SEC_NUM_COMP)
                 values (det_serie.sec_num_comp);

                 pos := pos + 1;
              else
              -- el producto existe
              -- no aumenta el item
                 null;
                 --vCadenaNumComp := vCadenaNumComp || '@' ||det_serie.sec_num_comp;
                 insert into TMP_AUX_COMP_PAGO nologging (SEC_NUM_COMP)
                 values (det_serie.sec_num_comp);
              end if;
          end if;

          update TMP_AUX_DET_PEDIDO_INT_VTA
          set    GRUPO_DOC = vGrupo_in
          where  TIP_COMP_PAGO = det_serie.tip_comp_pago
          and    NUM_COMP_PAGO = det_serie.num_comp_pago
          and    SEC_PED_VTA_DET = det_serie.sec_ped_vta_det
          and    COD_PROD = det_serie.cod_prod
          and    cod_cliente_sap = cCodCliSAP_in;

          select count(1)
          into   existe
          from   TMP_AUX_PROD_AGRUPA a
          where  a.cod_prod_aux in (det_serie.pos_cod_prod||'-'||det_serie.pos_tip_ped_vta);

          ---------------------------------------
                if existe = 0 then
             -- if instr(vCadenaProducto,det_serie.pos_cod_prod||'-'||det_serie.pos_tip_ped_vta) = 0 then

                  /*SELECT count(EXTRACTVALUE(xt.column_value, 'e'))
                  into   nCantItems
                    FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                           REPLACE(vCadenaProducto,
                                                                   '@',
                                                                   '</e><e>') ||
                                                           '</e></coll>'),
                                                   '/coll/e'))) xt;*/
                  select count(1)
                  into   nCantItems
                  from   TMP_AUX_PROD_AGRUPA;


                if nCantItems = nItemQuiebre then

                   -- existe el mismo numero de comprobante en la siguiente fila
                   -- entonces ese comprobante se debe de quitar
                   select count(1)
                   into   existe_comp
                   from   aux_comp_pago
                   where  sec_num_comp in (det_serie.pos_sec_num_comp);

                   if existe_comp != 0 then
                   --if instr(vCadenaNumComp,det_serie.pos_sec_num_comp) != 0 then
                      --vCadenaNumComp := replace(vCadenaNumComp,det_serie.pos_sec_num_comp,'');
                      --vCadenaNumComp := Substr(vCadenaNumComp,1,Length(vCadenaNumComp)-1);

                      delete aux_comp_pago
                      where  SEC_NUM_COMP = det_serie.pos_sec_num_comp;

                      update TMP_AUX_DET_PEDIDO_INT_VTA  a
                      set    GRUPO_DOC = null
                      where  a.TIP_COMP_PAGO = det_serie.tip_comp_pago
                      and    a.NUM_COMP_PAGO = det_serie.num_comp_pago
                      and    a.cod_cliente_sap = cCodCliSAP_in;

                      -- AQUI SE INVOCA LA RECURSIVIDAD PARA QUE ASI TERMINE DE REVISAR TODOS LOS GRUPOS
                      -- EL ALGORITMO ESTA DOCUMENTADO EN EL PROYECTO DE NUEVA INTERFAZ
                      -- invoca a todos los demas comprobantes detalles que esten NULLOS
                      --- ================================================================== --
                      --  INICIO
                      delete TMP_AUX_PROD_AGRUPA;
                      close vCurDetComp;
                      AUX_AGRUP_0105_MUEVE_KARDEX(cCodGrupoCia_in,cCodLocal_in,cTipComp_in,cSerie_in,cCodCliSAP_in);
                      exit;
                      --- FIN
                      --- ================================================================== --

                   end if;
                   vGrupo_in := vGrupo_in + 1;
                   pos := 0;
                end if;
              else
              -- el producto existe
              -- no aumenta el item
                 null;
              end if;
          end loop;
          if vCurDetComp%ISOPEN = true then
             close vCurDetComp;
          end if;
  END;
  /* *************************************************************************** */
PROCEDURE AUX_GRABA_REDONDEO(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cNumSecDoc_in   IN VARCHAR2,
                             cNumDocRef_in   IN VARCHAR2,
                             cTipCompSAP_in   IN VARCHAR2,
                             cTotalDocumento_in   IN NUMBER,
                             cTipCompPago_in   IN VARCHAR2,
                             cSerie_in         IN VARCHAR2,
                             cGrupo_Doc_in     IN NUMBER,
                             cFechaProceso_in IN VARCHAR2,
                             cNumDocAnul_in   in varchar2,
                             cIndAnulado_in   in char ,--default 'N',
                             cSecCompOrigen in char ,--default 'N',
                             cNumPedVta_in char ,--default 'N',
                             cIndServicio_in char ,--default 'N',
                             cNumDoc_in varchar2 ,
                             cRazSoc_in varchar2,
                             cCodClieSap varchar2 default 'N'
                             --cNumCompPago_in char default 'N'
                             )
  AS
    v_nCantRedondeo NUMBER(8,2) := 0;
    v_cCorr int_vta_mf.correlativo%TYPE;
    v_CodCliSap_in int_vta_mf.Cod_Cli_Sap%type;
    ExisteRedondeo char(1);
    cSerieCompPago int_vta_mf.serie_comprobante%type;
    v_cia char(3);

    VCANT INTEGER;
  BEGIN
    /*Dbms_Output.put_line('cGrupo_Doc_in-'||cGrupo_Doc_in);
    --OBTENER CORRELATIVO
    for la in(select i.correlativo
            from   INT_VTA_MF i
            where  i.cod_local = cCodLocal_in
            and    i.documento = cNumSecDoc_in
            order by 1 asc
            ) loop
      Dbms_Output.put_line('corre-'||la.correlativo);
    end loop;*/
    Dbms_Output.put_line('cCodLocal_in-'||cCodLocal_in);
    Dbms_Output.put_line('cNumSecDoc_in-'||cNumSecDoc_in);

    select Farma_Utility.COMPLETAR_CON_SIMBOLO(max(i.correlativo*1)+1,6,'0','I')
    into   v_cCorr
    from   INT_VTA_MF i
    where  i.cod_local = cCodLocal_in
    and    i.documento = cNumSecDoc_in;

    select distinct i.serie_comprobante
    into   cSerieCompPago
    from   INT_VTA_MF i
    where  i.cod_local = cCodLocal_in
    and    i.documento = cNumSecDoc_in;


    --GET_REDONDEO
    if cTipCompPago_in = COMP_BOLETA or cTipCompPago_in =COMP_TK_BOL  then
        if cIndAnulado_in = 'N' then
             if cIndServicio_in = 'N' then
              select distinct t.cod_cliente_sap
              into   v_CodCliSap_in
              from   TMP_AUX_DET_PEDIDO_INT_VTA t
              where  t.cod_grupo_cia = cCodGrupoCia_in
              and    t.cod_local = cCodLocal_in
              and    t.grupo_doc = cGrupo_Doc_in
              and    t.cod_cliente_sap = cCodClieSap;
             else
              select distinct t.cod_cliente_sap
              into   v_CodCliSap_in
              from   TMP_AUX_DET_PEDIDO_INT_VTA t
              where  t.cod_grupo_cia = cCodGrupoCia_in
              and    t.cod_local = cCodLocal_in
              and    t.num_comp_pago = cGrupo_Doc_in;
             end if;

             if cCodClieSap != 'N' then
              SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO),2)
              INTO v_nCantRedondeo
              FROM  VTA_COMP_PAGO e
              WHERE exists (
                            select 1
                            from   TMP_AUX_DET_PEDIDO_INT_VTA a
                            where  a.cod_grupo_cia = cCodGrupoCia_in
                            and    a.cod_local = cCodLocal_in
                            and    a.tip_comp_pago = cTipCompPago_in
                            and    a.grupo_doc = cGrupo_Doc_in
                            and    a.cod_cliente_sap = cCodClieSap
                            and    a.cod_grupo_cia = e.cod_grupo_cia
                            and    a.cod_local = e.cod_local
                            and    a.num_ped_vta = e.num_ped_vta
                            and    a.num_comp_pago = e.num_comp_pago
                           );
             else
              SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO),2)
              INTO v_nCantRedondeo
              FROM  VTA_COMP_PAGO e
              WHERE exists (
                            select 1
                            from   TMP_AUX_DET_PEDIDO_INT_VTA a
                            where  a.cod_grupo_cia = cCodGrupoCia_in
                            and    a.cod_local = cCodLocal_in
                            and    a.tip_comp_pago = cTipCompPago_in
                            and    a.grupo_doc = cGrupo_Doc_in
                            and    a.cod_grupo_cia = e.cod_grupo_cia
                            and    a.cod_local = e.cod_local
                            and    a.num_ped_vta = e.num_ped_vta
                            and    a.num_comp_pago = e.num_comp_pago
                           );
             end if;
        else
           -- es anulado
              select distinct t.cod_cliente_sap
              into   v_CodCliSap_in
              from   TMP_ANULA_DET_PEDIDO_INT_VTA t
              where  t.cod_grupo_cia = cCodGrupoCia_in
              and    t.cod_local = cCodLocal_in
              and    t.tip_comp_pago = cTipCompPago_in
              and    t.serie = cSerie_in
              and    t.num_comp_pago = cGrupo_Doc_in;

              SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO),2)
              INTO v_nCantRedondeo
              FROM  VTA_COMP_PAGO e
              WHERE exists (
                            select 1
                            from   TMP_ANULA_DET_PEDIDO_INT_VTA a
                            where  a.cod_grupo_cia = cCodGrupoCia_in
                            and    a.cod_local = cCodLocal_in
                            and    a.tip_comp_pago = cTipCompPago_in
                            and    a.num_comp_pago = cGrupo_Doc_in
                            and    a.cod_grupo_cia = e.cod_grupo_cia
                            and    a.cod_local = e.cod_local
                            and    a.num_ped_vta = e.num_ped_vta
                            and    a.num_comp_pago = e.num_comp_pago
                           );
        end if;
    else
     if cTipCompPago_in = COMP_FACTURA then

        if cIndAnulado_in = 'N' then
              select distinct t.cod_cliente_sap
              into   v_CodCliSap_in
              from   TMP_AUX_DET_PEDIDO_INT_VTA t
              where  t.cod_grupo_cia = cCodGrupoCia_in
              and    t.cod_local = cCodLocal_in
              and    t.tip_comp_pago = cTipCompPago_in
              and    t.serie = cSerie_in
              and    t.num_comp_pago = cGrupo_Doc_in;

              SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO),2)
              INTO v_nCantRedondeo
              FROM  VTA_COMP_PAGO e
              WHERE exists (
                            select 1
                            from   TMP_AUX_DET_PEDIDO_INT_VTA a
                            where  a.cod_grupo_cia = cCodGrupoCia_in
                            and    a.cod_local = cCodLocal_in
                            and    a.tip_comp_pago = cTipCompPago_in
                            and    a.serie = cSerie_in
                            and    a.num_comp_pago = cGrupo_Doc_in
                            and    a.cod_grupo_cia = e.cod_grupo_cia
                            and    a.cod_local = e.cod_local
                            and    a.num_ped_vta = e.num_ped_vta
                            and    a.num_comp_pago = e.num_comp_pago
                           );
        else
            -- es anulado de factura
              select distinct t.cod_cliente_sap
              into   v_CodCliSap_in
              from   TMP_ANULA_DET_PEDIDO_INT_VTA t
              where  t.cod_grupo_cia = cCodGrupoCia_in
              and    t.cod_local = cCodLocal_in
              and    t.tip_comp_pago = cTipCompPago_in
              and    t.serie = cSerie_in
              and    t.num_comp_pago = cGrupo_Doc_in;

              SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO),2)
              INTO v_nCantRedondeo
              FROM  VTA_COMP_PAGO e
              WHERE exists (
                            select 1
                            from   TMP_ANULA_DET_PEDIDO_INT_VTA a
                            where  a.cod_grupo_cia = cCodGrupoCia_in
                            and    a.cod_local = cCodLocal_in
                            and    a.tip_comp_pago = cTipCompPago_in
                            and    a.serie = cSerie_in
                            and    a.num_comp_pago = cGrupo_Doc_in
                            and    a.cod_grupo_cia = e.cod_grupo_cia
                            and    a.cod_local = e.cod_local
                            and    a.num_ped_vta = e.num_ped_vta
                            and    a.num_comp_pago = e.num_comp_pago
                           );
        end if;
     else
     /*dbms_output.put_line('--------------------');
     dbms_output.put_line(cTipCompPago_in);
     dbms_output.put_line(cSerie_in);
     dbms_output.put_line(cGrupo_Doc_in);
     dbms_output.put_line(cNumPedVta_in);
     dbms_output.put_line('--------------------');*/
        -- ES NOTA DE CREDITO --


        select distinct t.cod_cliente_sap
        into   v_CodCliSap_in
        from   TMP_ANULA_DET_PED_NOTA_CREDITO t
        where  t.cod_grupo_cia = cCodGrupoCia_in
        and    t.cod_local = cCodLocal_in
        and    t.tip_comp_pago = cTipCompPago_in
        and    t.serie = cSerie_in
        and    t.num_comp_pago = cGrupo_Doc_in
        and    t.sec_comp_pago_origen = cSecCompOrigen;

        SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO*-1),2)
        INTO v_nCantRedondeo
        FROM  VTA_COMP_PAGO e
        WHERE exists (
                      select 1
                      from   TMP_ANULA_DET_PED_NOTA_CREDITO a
                      where  a.cod_grupo_cia = cCodGrupoCia_in
                      and    a.cod_local = cCodLocal_in
                      and    a.tip_comp_pago = cTipCompPago_in
                      and    a.serie = cSerie_in
                      and    a.num_comp_pago = cGrupo_Doc_in
                      and    a.num_ped_vta = cNumPedVta_in
                      --and    a.sec_comp_pago_origen = cSecCompOrigen
                      and    a.cod_grupo_cia = e.cod_grupo_cia
                      and    a.cod_local = e.cod_local
                      and    a.num_ped_vta = e.num_ped_vta
                      and    a.num_comp_pago = e.num_comp_pago
                     );

          select case
                 when b.val_redondeo_comp_pago = 0 then 'N'
                 else 'S'
                 end
          into   ExisteRedondeo
          from   vta_comp_pago b
          where  b.cod_local = cCodLocal_in
          and    b.cod_grupo_cia = cCodGrupoCia_in
          and    b.sec_comp_pago =  cSecCompOrigen;



         if ExisteRedondeo = 'N' then
            v_nCantRedondeo := 0;
         end if;

     end if;
    end if;

    IF v_nCantRedondeo <> 0 THEN
       select cod_cia into v_cia from pbl_local where cod_local=cCodLocal_in;
    
         insert into INT_VTA_MF nologging
         (
         /*1*/ MANDANTE,COD_LOCAL,DOCUMENTO,
         /*2*/ CORRELATIVO,FECHAEMISION,
         /*3*/ RUCCLIENTE,DES_RAZON_SOCIAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO_REFER,
         /*4*/ NUM_DOCUMENTO_ANU,COD_TIPO_VENTA,
         /*5*/ TIPO_LABORATORIO,TIPO_POSICION,
         /*6*/ COD_PRODUCTO,CANTIDAD,UNIDAD,
         /*7*/ FRACCIONADA,FACTOR,
         /*8*/ MTO_TOTAL_POS,MONEDA,MTO_TOTAL,AJUSTE,
         /*9*/ COD_CLI_SAP,COD_PAGO,IMP_DSCTO,
        /*10*/ FCH_ARCHIVO,FCH_VENTA,USU_CREA,USU_MOD,FECH_CREA,FECH_MOD,COD_GRUPO_CIA,
               SEC_COMP_PAGO_ORIGEN,
               SERIE_COMPROBANTE
               )
      VALUES(C_MANDANTE,cCodLocal_in,cNumSecDoc_in,
             v_cCorr,to_char(to_date(cFechaProceso_in,'dd/mm/yyyy'),'yyyymmdd'),
             cNumDoc_in,cRazSoc_in,cTipCompSAP_in,cNumDocRef_in,
             cNumDocAnul_in,'',
             '','2',
             '',decode(v_cia,MARCA_BTL,to_char(0,'99999999990.000'),TO_CHAR(0,'99999999990.00')),C_UNIDAD,
             '','',
             case
                when v_nCantRedondeo < 0 then
                 to_char(v_nCantRedondeo, '9,999,990.00S')
                else
                 to_char(v_nCantRedondeo, '99,999,990.00')
             end,C_MONEDA_SOLES,
             case
                when cTotalDocumento_in < 0 then
                 to_char(cTotalDocumento_in, '9,999,990.00S')
                else
                 to_char(cTotalDocumento_in, '99,999,990.00')
             end,
             case
                when v_nCantRedondeo < 0 then
                 to_char(v_nCantRedondeo, '9,999,990.00S')
                else
                 to_char(v_nCantRedondeo, '99,999,990.00')
             end,
              v_CodCliSap_in,'',to_char(0, '9999999990.00'),
              null,to_date(cFechaProceso_in,'dd/mm/yyyy'),'SISTEMAS','',sysdate,null,
              cCodGrupoCia_in,
              cSecCompOrigen,
              cSerieCompPago
              );
      END IF;


    UPDATE int_vta_mf i
    SET    i.ajuste  = (case
                          when v_nCantRedondeo < 0 then
                           to_char(v_nCantRedondeo, '9,999,990.00S')
                          else
                           to_char(v_nCantRedondeo, '99,999,990.00')
                       end)
    WHERE COD_LOCAL = cCodLocal_in
    AND   I.DOCUMENTO = cNumSecDoc_in;

  END;
  /****************************************************************************/
FUNCTION GET_NUM_DOC_REF(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cTipComp_in      IN CHAR,
                                cNumSerie_in    IN CHAR,
                                cNumCompI_in    IN CHAR,
                                cNumCompF_in    IN CHAR,
                                cTipDoc_SAP_in  IN CHAR,
                                cIndAnulado_in in char
                                )
  RETURN VARCHAR2
  IS
    v_Referencia VARCHAR2(25);
    v_vBoletaInicial VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
    v_vBoletaFinal VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
    v_gUltimoDoc VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE := NULL;
  BEGIN
   if cTipDoc_SAP_in = TIP_COMP_SAP_SERVICIO then

      SELECT DECODE(cTipComp_in,
                          COMP_BOLETA,'03',
                          COMP_FACTURA,'01',
                          COMP_TK_BOL,'12'
                          )
             ||'-00'||cNumSerie_in||'-'||SUBSTR(cNumCompI_in,4)
      INTO   v_Referencia
      FROM   DUAL;

   else
       IF cIndAnulado_in = 'N'  THEN
             if cTipComp_in = COMP_BOLETA or cTipComp_in = COMP_TK_BOL then
             /*
              if cTipComp_in = COMP_BOLETA THEN
                SELECT cNumSerie_in ||
                   LPAD(NVL(TRIM(SUBSTR(MAX(NUM_DOC_REF), 018) + 1), '0000001'),
                        7,
                        '0')
                 INTO v_gUltimoDoc
                 FROM (
                        select e.cod_local,e.clase_doc,e.num_doc_ref
                        from  (
                                SELECT COD_LOCAL,CLASE_DOC,NUM_DOC_REF
                                  FROM INT_VENTA h
                                WHERE  h.COD_GRUPO_CIA = cCodGrupoCia_in
                                and    h.cod_local = cCodLocal_in
                                --and    h.fec_proceso <= to_date('01/02/2012','dd/mm/yyyy') - 1/24/60/60
                                and    h.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
                                union
                                select t.cod_local,t.cod_tipo_documento,t.num_documento_refer
                                from   INT_VTA_MF t
                                WHERE  t.COD_GRUPO_CIA = cCodGrupoCia_in
                                and    t.cod_local = cCodLocal_in
                               )e
                        )
                 WHERE COD_LOCAL = cCodLocal_in
                   AND trim(CLASE_DOC) = TRIM(cTipDoc_SAP_in); -- BOLETA
             ELSE
                SELECT cNumSerie_in ||
                 LPAD(NVL(TRIM(SUBSTR(MAX(NUM_DOC_REF), 018) + 1), '0000001'),
                      7,
                      '0')
               INTO v_gUltimoDoc
               FROM (
                      select e.cod_local,e.clase_doc,e.num_doc_ref,E.FEC_PROCESO
                      from  (
                              SELECT COD_LOCAL,CLASE_DOC,NUM_DOC_REF,H.FEC_PROCESO
                                FROM INT_VENTA h
                              WHERE  h.COD_GRUPO_CIA = cCodGrupoCia_in
                              and    h.cod_local = cCodLocal_in
                              --and    h.fec_proceso <= to_date('01/02/2012','dd/mm/yyyy') - 1/24/60/60
                                and    h.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
                              union
                              select t.cod_local,t.cod_tipo_documento,t.num_documento_refer,T.FCH_VENTA AS "FEC_PROCESO"
                              from   INT_VTA_MF t
                              WHERE  t.COD_GRUPO_CIA = cCodGrupoCia_in
                              and    t.cod_local = cCodLocal_in
                             )e
                      )
               WHERE COD_LOCAL = cCodLocal_in
                 AND trim(CLASE_DOC) = cTipDoc_SAP_in
                AND  FEC_PROCESO = (SELECT MAX(FEC_PROCESO)
                                          FROM (
                                                SELECT U.COD_LOCAL,U.CLASE_DOC,U.NUM_DOC_REF,U.FEC_PROCESO
                                                  FROM INT_VENTA U
                                                WHERE  U.COD_GRUPO_CIA = cCodGrupoCia_in
                                                and    U.cod_local = cCodLocal_in
                                                --and    U.fec_proceso <= to_date('01/02/2012','dd/mm/yyyy') - 1/24/60/60
                                                and    u.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
                                                union
                                                select I.cod_local,I.cod_tipo_documento,I.num_documento_refer,I.FCH_VENTA AS "FEC_PROCESO"
                                                from   INT_VTA_MF I
                                                WHERE  I.COD_GRUPO_CIA = cCodGrupoCia_in
                                                and    I.cod_local = cCodLocal_in
                                                ) G
                                          WHERE G.COD_LOCAL = cCodLocal_in
                                          AND G.CLASE_DOC = 7 --TICKET
                                          AND SUBSTR(G.NUM_DOC_REF,0,8) LIKE '12-00'||TRIM(cNumSerie_in)
                                          )
                 AND SUBSTR(NUM_DOC_REF,0,8) LIKE '12-00'||TRIM(cNumSerie_in);
             END IF;
             */
              --v_vBoletaInicial := v_gUltimoDoc;
              v_vBoletaInicial := TRIM(cNumCompI_in);
              v_vBoletaFinal   := TRIM(cNumCompF_in);

              IF v_vBoletaFinal*1 < v_vBoletaInicial*1 THEN
                v_vBoletaFinal := v_vBoletaInicial;
              END IF;

              if cTipComp_in = COMP_BOLETA THEN
                  -- boleta
                  v_Referencia := '03-00' || cNumSerie_in     || '-' ||
                                SUBSTR(v_vBoletaInicial, 4) || '-' ||
                                SUBSTR(v_vBoletaFinal  , 4);
              else
                  -- ticket boleta
                  v_Referencia := '12-00' || cNumSerie_in     || '-' ||
                                SUBSTR(v_vBoletaInicial, 4) || '-' ||
                                SUBSTR(v_vBoletaFinal  , 4);
              end if;
              else
                if cTipComp_in = COMP_FACTURA THEN
                -- factura
                v_Referencia :=  '01-00'||cNumSerie_in||'-'||SUBSTR(cNumCompI_in,4);
                END IF;
              end if;
        ELSE
         IF cIndAnulado_in = 'S' THEN
            SELECT DECODE(cTipComp_in,
                          COMP_BOLETA,'03',
                          COMP_FACTURA,'01',
                          COMP_TK_BOL,'12'
                          )
                   ||'-00'||cNumSerie_in||'-'||SUBSTR(cNumCompI_in,4)
            INTO   v_Referencia
            FROM   DUAL;

         END IF;
        END IF;

    END IF;

    RETURN v_Referencia;
  END;
  /* *************************************************************************** */
  FUNCTION GET_SEC_DOC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN NUMBER
  IS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    g_cNumIntVentas PBL_NUMERA.COD_NUMERA%TYPE := '018';
  BEGIN
    v_nNumSecDoc:= OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntVentas);
    ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntVentas,'ADMIN');
    RETURN v_nNumSecDoc;
  END;
  /* *************************************************************************** */
  FUNCTION GET_TIP_COMP_SAP(cTipCompPago_in IN CHAR,cIndAnulado in char,
                            cOrigenTipComp in char default 'X')
  return varchar2
  is
    v_nNumTipo varchar2(5);
  begin

   if cIndAnulado = 'N' then
       select DECODE(cTipCompPago_in,COMP_BOLETA,'1',COMP_FACTURA,'2',COMP_TK_BOL,'7')
       into   v_nNumTipo
       from   dual;
   else
     if cOrigenTipComp = 'X' then
       select DECODE(cTipCompPago_in,COMP_BOLETA,'3',COMP_FACTURA,'4',COMP_TK_BOL,'11')
       into   v_nNumTipo
       from   dual;
     else
       if cTipCompPago_in = COMP_NC then
           select DECODE(cOrigenTipComp,COMP_BOLETA,'6',COMP_FACTURA,'5',COMP_TK_BOL,'10')
           into   v_nNumTipo
           from   dual;
       end if;
     end if;
   end if;

    return v_nNumTipo;
  end;
  /* *************************************************************************** */
  FUNCTION GET_TIP_VTA_SAP(cTipPedVta_in IN CHAR,cIndConvenio_in IN CHAR DEFAULT 'N')
  return varchar2
  is
    v_nNumTipo varchar2(5);
  begin

   select DECODE(cTipPedVta_in,'01',decode(cIndConvenio_in,'S','6','3'),'02','4','03','5')
   into   v_nNumTipo
   from   dual;

    return v_nNumTipo;
  end;
  /* ********************************************************************** */
  FUNCTION GET_COD_SERVICIO_PEDIDO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumPedVta_in in char)
  return varchar2
  is
    vCodServicio_in VARCHAR2(100);--btlprod.mae_conveniocod_material_sap@btlrac%type;
  begin
--   dbms_output.put_line('antes cNumPedVta_in'||cNumPedVta_in);

select    m.cod_material_sap
   into   vCodServicio_in
   from   mae_convenio m
   where  m.cod_convenio in (
                            select C.COD_CONVENIO
                            from   vta_pedido_vta_cab  c
                            where  c.cod_grupo_cia = cCodGrupoCia_in
                            and    c.cod_local = cCodLocal_in
                            and    c.num_ped_vta = cNumPedVta_in
                            );
--   dbms_output.put_line('despues vCodServicio_in'||vCodServicio_in);
    return vCodServicio_in;
  end;
  /* ********************************************************************** */
  PROCEDURE SET_NUN_DOC_SAP_COMP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                                 cSecComp_in     IN CHAR, nNumSecDoc_in IN number,
                                 nNumPedVta_in IN number,
                                 cIndAnulado in char
                                 )
  AS
  BEGIN
   if cIndAnulado = 'S' then
    UPDATE /*+ RULE*/VTA_COMP_PAGO
    SET NUM_SEC_DOC_SAP_ANUL  = nNumSecDoc_in,
        FEC_PROCESO_SAP_ANUL = SYSDATE,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = 'JB_INT_VENTA_26'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND   COD_LOCAL = cCodLocal_in
    and   NUM_PED_VTA = nNumPedVta_in
    AND   SEC_COMP_PAGO = cSecComp_in;
  else

    UPDATE /*+ RULE*/VTA_COMP_PAGO
    SET NUM_SEC_DOC_SAP = nNumSecDoc_in,
        FEC_PROCESO_SAP = SYSDATE,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = 'JB_INT_VENTA_24'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND   COD_LOCAL = cCodLocal_in
    and   NUM_PED_VTA = nNumPedVta_in
    AND   SEC_COMP_PAGO = cSecComp_in;

  end if;
  END;
 /* **************************************************************************** */
 PROCEDURE C_COBRADOS(cCodGrupoCia_in IN CHAR, cCodLocal_in    IN CHAR,vFecProceso_in IN VARCHAR2,cIndConvenio_in IN CHAR )
 is
 BEGIN
     -- COBRADOS
      -- 0.- TICKET BOLETA y TICKET
      C_0105_COMP_AGRUP(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECPROCESO_IN,CINDCONVENIO_IN);

      C_02_COMP_NO_AGRUP(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECPROCESO_IN,CINDCONVENIO_IN);

      S_SERVICIOS_COBRADOS(cCodGrupoCia_in,cCodLocal_in,vFecProceso_in,cIndConvenio_in);
 end;
  /* *************************************************************************** */
  -- 0.- PEDIDOS COBRADOS BOLETA(01) Y TICKET BOLETA (5)
  PROCEDURE C_0105_COMP_AGRUP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2,cIndConvenio_in IN CHAR)
  AS
    CURSOR curSeries_Bol_TK_Bol IS
    SELECT c.TIP_COMP, NUM_SERIE_LOCAL
      FROM VTA_SERIE_LOCAL c
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND TIP_COMP in (COMP_BOLETA, COMP_TK_BOL)
       AND EST_SERIE_LOCAL = 'A'
       AND EXISTS
     (SELECT 1
              FROM vta_comp_pago e
             WHERE e.COD_GRUPO_CIA = cCodGrupoCia_in
               AND e.COD_LOCAL = cCodLocal_in
               AND e.TIP_COMP_PAGO in (COMP_BOLETA, COMP_TK_BOL)
               AND SUBSTR(e.NUM_COMP_PAGO, 1, 3) = c.NUM_SERIE_LOCAL
               AND E.NUM_SEC_DOC_SAP IS NULL
               AND exists (
                           select 1
                           from   vta_pedido_vta_cab t
                           where  t.est_ped_vta = 'C'
                           and    t.cod_grupo_cia = e.cod_grupo_cia
                           and    t.cod_local = e.cod_local
                           and    t.num_ped_vta = e.num_ped_vta
                           AND    t.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                           and    nvl(t.ind_conv_btl_mf,t.ind_ped_convenio)= cIndConvenio_in
                           and    t.ind_ped_convenio= cIndConvenio_in
                           )
                   )
    order by 1,2;

    fila curSeries_Bol_TK_Bol%rowtype;

    CURSOR curSerie_X_Grupo is
    select j.tip_comp_pago,j.grupo_doc,j.serie,min(j.num_comp_pago) num_cp_min,max(j.num_comp_pago) num_cp_max,
           j.SERIE_TICKETERA,J.COD_CLIENTE_SAP
    from   TMP_AUX_DET_PEDIDO_INT_VTA j
    where  j.ind_mueve_kardex = 'S'
    and    TIP_COMP_PAGO in (COMP_BOLETA, COMP_TK_BOL)
    AND    J.GRUPO_DOC IS NOT NULL
    group by j.tip_comp_pago,j.grupo_doc,j.serie,
           j.SERIE_TICKETERA,J.COD_CLIENTE_SAP
    order by 1,2;

    fSC curSerie_X_Grupo%rowtype;
    --=====================================================--
    --------------   VARIBLES AUXILIARES -------------------
    v_nNumSecDoc int_vta_mf.documento%TYPE;
    v_vNumDocRef int_vta_mf.num_documento_refer %TYPE;
    v_TipComp_SAP varchar2(10);
    --=====================================================--
    vTotalDocumento number(8,2);
    v_nCantRedondeo number(8,2);

    cursor curCliSap(serie_in in varchar2,TipComp_in in char) is
      select distinct d.cod_cliente_sap
      from  TMP_AUX_DET_PEDIDO_INT_VTA d
      where d.cod_grupo_cia = cCodGrupoCia_in
      and   d.cod_local = cCodLocal_in
      and   d.tip_comp_pago = TipComp_in
      and   d.serie = serie_in
      and   d.ind_mueve_kardex = 'S';

    fCurCliSap   curCliSap%rowtype;
    v_cia        char(3);

  BEGIN
    --OBTIENE LAS SERIES VENDIDAS DE TICKET BOLETA Y BOLETA Y GRABARA EL DETALLE DE PEDIDOS
    --Estos grabara detalles de todos los del tipo 01 y 05
    -- save int para los que MUEVEN KARDEX y en este caso se tienen que agrupar.
    dbms_output.put_line(''||cCodGrupoCia_in);
    dbms_output.put_line(''||cCodLocal_in);
    dbms_output.put_line(''||vFecProceso_in);

    dbms_output.put_line('cIndConvenio_in-->1');
    dbms_output.put_line('cIndConvenio_in-->1');
    dbms_output.put_line('cIndConvenio_in-->1');

    OPEN curSeries_Bol_TK_Bol;
    LOOP
    --- BEGIN
    FETCH curSeries_Bol_TK_Bol INTO fila;
     EXIT WHEN curSeries_Bol_TK_Bol%NOTFOUND;
    --FOR fila IN curSeries_Bol_TK_Bol
      dbms_output.put_line('cIndConvenio_in-->2');
      AUX_SAVE_DET_X_COMPROBANTE(cCodGrupoCia_in,cCodLocal_in,fila.tip_comp,fila.num_serie_local,vFecProceso_in,cIndConvenio_in);
      --- AGRUPA LOS QUE MUEVEN KARDEX ---
      OPEN curCliSap(fila.num_serie_local,fila.tip_comp);
       LOOP
        FETCH curCliSap INTO fCurCliSap;
        EXIT WHEN curCliSap%NOTFOUND;

      AUX_AGRUP_0105_MUEVE_KARDEX(cCodGrupoCia_in,cCodLocal_in,
                                  fila.tip_comp,fila.num_serie_local,
                                  fCurCliSap.Cod_Cliente_Sap
                                  );
      end loop;
      close curCliSap;
       /*EXCEPTION
      WHEN OTHERS THEN
        dbms_output.put_line(' '||SQLERRM);
      END; */
      -- FIN DE AGRUPACION
    END LOOP;
    close curSeries_Bol_TK_Bol;
    -- genera el documento e int vta por cada serie y grupo de los que mueven Kardex
dbms_output.put_line('cIndConvenio_in-->3');
    OPEN curSerie_X_Grupo;
    LOOP
    FETCH curSerie_X_Grupo INTO fSC;
    EXIT WHEN curSerie_X_Grupo%NOTFOUND;
    --FOR fSC IN curSerie_X_Grupo
    --LOOP
        --0.- obtiene el numero de documento y Cadena de REFERENCIA
       v_TipComp_SAP := GET_TIP_COMP_SAP(fSC.Tip_Comp_Pago,'N');

       v_nNumSecDoc:= GET_SEC_DOC(cCodGrupoCia_in,cCodLocal_in);
       v_vNumDocRef:= GET_NUM_DOC_REF(cCodGrupoCia_in,cCodLocal_in,fSC.Tip_Comp_Pago,fSC.Serie,
                                     fSC.Num_Cp_Min,fSC.Num_Cp_Max,v_TipComp_SAP,'N');



      --1.- calcula el total del documento de ese grupo
      --select round(sum(det.prec_total - det.dcto_total),2)
      select round(sum(det.prec_total-det.dcto_total),2)
      into   vTotalDocumento
      from   (
              select j.cod_grupo_cia,j.cod_local,j.cod_prod,
                     sum(j.cant_atendida/j.val_frac) ctd_vendido,
                     --sum(j.new_val_prec_total) prec_total,
                     sum(j.old_val_prec_total) prec_total,
                     sum(j.new_dcto_x_prod) dcto_total
              from   TMP_AUX_DET_PEDIDO_INT_VTA j
              where  j.grupo_doc = fSC.Grupo_Doc
              and    j.tip_comp_pago = fSC.Tip_Comp_Pago
              and    j.serie = fSC.Serie
              AND    J.COD_CLIENTE_SAP = FSC.COD_CLIENTE_SAP
              group by j.cod_grupo_cia,j.cod_local,j.cod_prod
             )det;

     SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO),2)
                  INTO v_nCantRedondeo
                  FROM  VTA_COMP_PAGO e
                  WHERE exists (
                                select 1
                                from   TMP_AUX_DET_PEDIDO_INT_VTA a
                                where  a.grupo_doc = fSC.Grupo_Doc
                                and    a.tip_comp_pago = fSC.Tip_Comp_Pago
                                and    a.serie = fSC.Serie
                                AND    A.COD_CLIENTE_SAP = FSC.COD_CLIENTE_SAP
                                and    a.cod_grupo_cia = e.cod_grupo_cia
                                and    a.cod_local = e.cod_local
                                and    a.num_ped_vta = e.num_ped_vta
                                and    a.num_comp_pago = e.num_comp_pago
                               );
     vTotalDocumento := vTotalDocumento ;-- - v_nCantRedondeo;
     
     select cod_cia into v_cia from pbl_local where cod_local=cCodLocal_in;
     
dbms_output.put_line('cIndConvenio_in-->4');

       /*--=========================================================--
        GRABA INTERFAZ PARA LOS DOCUMENTOS BOLETA Y TICKET BOLETA
        Doc agrupados y que mueven kardex.
       --=========================================================--*/
       insert into INT_VTA_MF nologging
       (
       /*1*/ MANDANTE,COD_LOCAL,DOCUMENTO,
       /*2*/ CORRELATIVO,FECHAEMISION,
       /*3*/ RUCCLIENTE,DES_RAZON_SOCIAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO_REFER,
       /*4*/ NUM_DOCUMENTO_ANU,COD_TIPO_VENTA,
       /*5*/ TIPO_LABORATORIO,TIPO_POSICION,
       /*6*/ COD_PRODUCTO,CANTIDAD,UNIDAD,
       /*7*/ FRACCIONADA,FACTOR,
       /*8*/ MTO_TOTAL_POS,MONEDA,MTO_TOTAL,AJUSTE,
       /*9*/ COD_CLI_SAP,COD_PAGO,IMP_DSCTO,
      /*10*/ FCH_ARCHIVO,FCH_VENTA,USU_CREA,USU_MOD,FECH_CREA,FECH_MOD,COD_GRUPO_CIA,
      SERIE_COMPROBANTE

      )

       select
       /*1*/  C_MANDANTE,cCodLocal_in,v_nNumSecDoc,
       /*2*/  Farma_Utility.COMPLETAR_CON_SIMBOLO(rownum,6,'0','I'),to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd'),
       /*3*/  '','',v_TipComp_SAP,v_vNumDocRef,
       /*4*/  '',GET_TIP_VTA_SAP(Det_Doc.tip_ped_vta),
       /*5*/  DECODE(Det_Doc.IND_PROD_PROPIO,'S','1','N','2'),C_POS_VENTA,
       /*6*/  Det_Doc.cod_prod,to_char(Det_Doc.cant_vend_final,decode(v_cia,MARCA_BTL,'99999999990.000','000000000.00')),C_UNIDAD,
       /*7*/  det_doc.ind_fraccion,DECODE(det_doc.ind_fraccion,'X',decode(v_cia,MARCA_BTL,det_doc.fac_conversion,TO_CHAR(det_doc.fac_conversion,'0000')),'1'),
       /*8*/  case
                  when det_doc.prec_total < 0 then
                   to_char(det_doc.prec_total, '9,999,990.00S')
                  else
                   to_char(det_doc.prec_total, '99,999,990.00')
              end,C_MONEDA_SOLES,
              case
                  when vTotalDocumento < 0 then
                   to_char(vTotalDocumento, '9,999,990.00S')
                  else
                   to_char(vTotalDocumento, '99,999,990.00')
              end,
              to_char(0,'9999,999,990.00')/*redondeo cero de manera temporal*/,
       /*9*/  det_doc.cod_cliente_sap,C_CONDICION_PAGO,to_char(det_doc.dcto_total,'9999999990.00'),
      /*10*/  null,to_date(vFecProceso_in,'dd/mm/yyyy'),'SISTEMAS',null,sysdate,null,cCodGrupoCia_in,
              fSC.SERIE_TICKETERA
     from
            (
             select det.cod_grupo_cia,det.cod_local,det.cod_prod,det.cod_cliente_sap,det.tip_ped_vta,p.ind_prod_propio,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then det.ctd_vendido*p.val_max_frac
                   else det.ctd_vendido
                   end cant_vend_final,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then p.val_max_frac
                   else 1
                   end fac_conversion,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then 'X'
                   else ' '
                   end ind_fraccion,
                   det.prec_total,
                   det.dcto_total,
                   rank() over (order by det.prec_total asc) orden
            from   (
                    select j.cod_grupo_cia,j.cod_local,j.cod_prod,j.cod_cliente_sap,j.tip_ped_vta,
                           sum(j.cant_atendida/j.val_frac) ctd_vendido,
                           --sum(j.new_val_prec_total) prec_total,
                           sum(j.old_val_prec_total) prec_total,
                           sum(j.new_dcto_x_prod) dcto_total
                    from   TMP_AUX_DET_PEDIDO_INT_VTA j
                    where  j.grupo_doc = fSC.Grupo_Doc
                    and    j.tip_comp_pago = fSC.Tip_Comp_Pago
                    and    j.serie = fSC.Serie
                    AND    J.COD_CLIENTE_SAP = FSC.COD_CLIENTE_SAP
                    group by j.cod_grupo_cia,j.cod_local,j.cod_prod,j.cod_cliente_sap,j.tip_ped_vta
                   )det,
                   lgt_prod p
            where  det.cod_grupo_cia = p.cod_grupo_cia
            and    det.cod_prod = p.cod_prod
            order by rank() over (order by det.prec_total asc)
            ) Det_Doc;

       --=========================================================--
       -- REGISTRO DEL REDONDEO DE CADA DOCUMENTO
       --=========================================================--
       AUX_GRABA_REDONDEO(cCodGrupoCia_in,cCodLocal_in,v_nNumSecDoc,
                          v_vNumDocRef,v_TipComp_SAP,vTotalDocumento,
                          fSC.Tip_Comp_Pago,fSC.Serie,fSC.Grupo_Doc,vFecProceso_in,
                          '','N','N','N','N','','',FSC.COD_CLIENTE_SAP);
       -------------------------------------------------------------
       -------------------------------------------------------------
        /*
        -- NO SE HARA PARA ESTE DIA PERO HAY QUE REVISAR COMO HACERLO
        17.06.2013
        UPDATE VTA_COMP_PAGO
        SET NUM_SEC_DOC_SAP = v_nNumSecDoc,
            FEC_PROCESO_SAP = SYSDATE,
            FEC_MOD_COMP_PAGO = SYSDATE,
            USU_MOD_COMP_PAGO = 'JB_INT_VENTA_24'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND   COD_LOCAL = cCodLocal_in
        AND   TIP_COMP_PAGO in (COMP_TK_BOL,COMP_BOLETA)
        AND   trim(Substr(num_comp_pago,1,3)) = fSC.Serie
        AND   trim(Substr(num_comp_pago,4,10))*1 BETWEEN
                                                   trim(Substr(fSC.Num_Cp_Min,4,10))*1 AND
                                                   trim(Substr(fSC.Num_Cp_Max,4,10))*1;*/

   end loop;
   close curSerie_X_Grupo;
  END;
 /* *************************************************************************** */
PROCEDURE C_02_COMP_NO_AGRUP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2,CINDCONVENIO_IN IN CHAR)
  AS

    CURSOR curSeries_Factura IS
        SELECT c.TIP_COMP, NUM_SERIE_LOCAL
          FROM VTA_SERIE_LOCAL c
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND COD_LOCAL = cCodLocal_in
           AND TIP_COMP in (COMP_FACTURA)
           AND EST_SERIE_LOCAL = 'A'
           AND EXISTS
         (SELECT 1
                  FROM vta_comp_pago e
                 WHERE e.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND e.COD_LOCAL = cCodLocal_in
                   AND e.TIP_COMP_PAGO in (COMP_FACTURA)
                   AND SUBSTR(e.NUM_COMP_PAGO, 1, 3) = c.NUM_SERIE_LOCAL
                   AND E.NUM_SEC_DOC_SAP IS NULL
                   AND exists (
                               select 1
                               from   vta_pedido_vta_cab t
                               where  t.est_ped_vta = 'C'
                               and    t.cod_grupo_cia = e.cod_grupo_cia
                               and    t.cod_local = e.cod_local
                               and    t.num_ped_vta = e.num_ped_vta
                               /*and    t.ind_conv_btl_mf  = CINDCONVENIO_IN
                               and    t.ind_ped_convenio = CINDCONVENIO_IN*/
                               and    nvl(t.ind_conv_btl_mf,t.ind_ped_convenio)= cIndConvenio_in
                               and    t.ind_ped_convenio= cIndConvenio_in
                               AND    t.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                               )
                       )
        order by 1,2;
   fila   curSeries_Factura%rowtype;

 cursor curFactMueveKardex is
  select distinct g.tip_comp_pago,g.num_comp_pago,g.serie,g.sec_num_comp,g.num_ped_vta,g.sec_comp_pago
  from   TMP_AUX_DET_PEDIDO_INT_VTA g
  where  g.tip_comp_pago = COMP_FACTURA
  and    g.ind_mueve_kardex = 'S'
  order by g.serie asc,g.sec_num_comp asc;

    --=====================================================--
    --------------   VARIBLES AUXILIARES -------------------
    v_nNumSecDoc int_vta_mf.documento%TYPE;
    v_vNumDocRef int_vta_mf.num_documento_refer %TYPE;
    v_TipComp_SAP varchar2(10);
    vRUC_empresa int_vta_mf.ruccliente%type;
    vRazonSocial int_vta_mf.des_razon_social%type;
    --=====================================================--
    vTotalDocumento number(8,2);
    v_nCantRedondeo number(8,2);

  fSC curFactMueveKardex%rowtype;
   v_cia char(3);

 begin
dbms_output.put_line('cIndConvenio_in-->'||cIndConvenio_in);
   open curSeries_Factura;
   LOOP
    FETCH curSeries_Factura INTO fila;
    EXIT WHEN curSeries_Factura%NOTFOUND;

      AUX_SAVE_DET_X_NO_AGRUPA(cCodGrupoCia_in,cCodLocal_in,fila.tip_comp,fila.num_serie_local,vFecProceso_in,CINDCONVENIO_IN);
   END LOOP;
   close curSeries_Factura;

   open curFactMueveKardex;
   LOOP
    FETCH curFactMueveKardex INTO fSC;
    EXIT WHEN curFactMueveKardex%NOTFOUND;

       v_TipComp_SAP := GET_TIP_COMP_SAP(fSC.Tip_Comp_Pago,'N');

       v_nNumSecDoc:= GET_SEC_DOC(cCodGrupoCia_in,cCodLocal_in);
       v_vNumDocRef:= GET_NUM_DOC_REF(cCodGrupoCia_in,cCodLocal_in,fSC.Tip_Comp_Pago,fSC.Serie,
                                      fSC.Num_Comp_Pago,fSC.Num_Comp_Pago,v_TipComp_SAP,'N');



      --1.- calcula el total del documento de ese grupo
      select round(sum(det.prec_total-det.dcto_total),2)
      into   vTotalDocumento
      from   (
              select j.cod_grupo_cia,j.cod_local,j.cod_prod,
                     sum(j.cant_atendida/j.val_frac) ctd_vendido,
                     --sum(j.new_val_prec_total) prec_total,
                     sum(j.old_val_prec_total) prec_total,
                     sum(j.new_dcto_x_prod) dcto_total
              from   TMP_AUX_DET_PEDIDO_INT_VTA j
              where  j.tip_comp_pago = fSC.Tip_Comp_Pago
              and    j.num_comp_pago = fSC.Num_Comp_Pago
              group by j.cod_grupo_cia,j.cod_local,j.cod_prod
             )det;

     SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO),2)
                  INTO v_nCantRedondeo
                  FROM  VTA_COMP_PAGO e
                  WHERE exists (
                                select 1
                                from   TMP_AUX_DET_PEDIDO_INT_VTA a
                                where  a.tip_comp_pago = fSC.Tip_Comp_Pago
                                and    a.num_comp_pago = fSC.Num_Comp_Pago
                                and    a.cod_grupo_cia = e.cod_grupo_cia
                                and    a.cod_local = e.cod_local
                                and    a.num_ped_vta = e.num_ped_vta
                                and    a.num_comp_pago = e.num_comp_pago
                               );
     vTotalDocumento := vTotalDocumento ;-- - v_nCantRedondeo;

      select  trim(C.RUC_CLI_PED_VTA),AUX_REEMPLAZAR_CHAR(SUBSTR(C.NOM_CLI_PED_VTA,1,40))
      into    vRUC_empresa,vRazonSocial
      from    vta_pedido_vta_cab c
      where   c.cod_grupo_cia = cCodGrupoCia_in
      and     c.cod_local = cCodLocal_in
      and     c.num_ped_vta = fSC.Num_Ped_Vta
      /*and     c.ind_conv_btl_mf  = CINDCONVENIO_IN
      and     c.ind_ped_convenio = CINDCONVENIO_IN*/

      ;
/*dbms_output.put_line('---------------------------------------');
dbms_output.put_line('cCodLocal_in-'||cCodLocal_in);
dbms_output.put_line('v_nNumSecDoc-'||v_nNumSecDoc);
dbms_output.put_line('Tip_Comp_Pago-'||fSC.Tip_Comp_Pago);
dbms_output.put_line('Num_Comp_Pago-'||fSC.Num_Comp_Pago);
dbms_output.put_line('---------------------------------------');*/


       /*--=========================================================--
        GRABA INTERFAZ PARA LOS DOCUMENTOS BOLETA Y TICKET BOLETA
        Doc agrupados y que mueven kardex.
       --=========================================================--*/
       select cod_cia into v_cia from pbl_local where cod_local=cCodLocal_in;
       
       insert into INT_VTA_MF nologging
       (
       /*1*/ MANDANTE,COD_LOCAL,DOCUMENTO,
       /*2*/ CORRELATIVO,FECHAEMISION,
       /*3*/ RUCCLIENTE,DES_RAZON_SOCIAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO_REFER,
       /*4*/ NUM_DOCUMENTO_ANU,COD_TIPO_VENTA,
       /*5*/ TIPO_LABORATORIO,TIPO_POSICION,
       /*6*/ COD_PRODUCTO,CANTIDAD,UNIDAD,
       /*7*/ FRACCIONADA,FACTOR,
       /*8*/ MTO_TOTAL_POS,MONEDA,MTO_TOTAL,AJUSTE,
       /*9*/ COD_CLI_SAP,COD_PAGO,IMP_DSCTO,
      /*10*/ FCH_ARCHIVO,FCH_VENTA,USU_CREA,USU_MOD,FECH_CREA,FECH_MOD,COD_GRUPO_CIA)

       select
       /*1*/  C_MANDANTE,cCodLocal_in,v_nNumSecDoc,
       /*2*/  Farma_Utility.COMPLETAR_CON_SIMBOLO(rownum,6,'0','I'),to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd'),
       /*3*/  vRUC_empresa,vRazonSocial,v_TipComp_SAP,v_vNumDocRef,
       /*4*/  '',GET_TIP_VTA_SAP(Det_Doc.tip_ped_vta),
       /*5*/  DECODE(Det_Doc.IND_PROD_PROPIO,'S','1','N','2'),C_POS_VENTA,
       /*6*/  Det_Doc.cod_prod,to_char(Det_Doc.cant_vend_final,decode(v_cia,MARCA_BTL,'99999999990.000','000000000.00')),C_UNIDAD,
       /*7*/  det_doc.ind_fraccion,DECODE(det_doc.ind_fraccion,'X',decode(v_cia,MARCA_BTL,det_doc.fac_conversion,TO_CHAR(det_doc.fac_conversion,'0000')),'1'),
       /*8*/  case
                  when det_doc.prec_total < 0 then
                   to_char(det_doc.prec_total, '9,999,990.00S')
                  else
                   to_char(det_doc.prec_total, '99,999,990.00')
              end,C_MONEDA_SOLES,
              case
                  when vTotalDocumento < 0 then
                   to_char(vTotalDocumento, '9,999,990.00S')
                  else
                   to_char(vTotalDocumento, '99,999,990.00')
              end,
              to_char(0,'9999,999,990.00')/*redondeo cero de manera temporal*/,
       /*9*/  det_doc.cod_cliente_sap,C_CONDICION_PAGO,to_char(det_doc.dcto_total,'9999999990.00'),
      /*10*/  null,to_date(vFecProceso_in,'dd/mm/yyyy'),'SISTEMAS',null,sysdate,null,cCodGrupoCia_in
     from
            (
             select det.cod_grupo_cia,det.cod_local,det.cod_prod,det.cod_cliente_sap,det.tip_ped_vta,p.ind_prod_propio,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then det.ctd_vendido*p.val_max_frac
                   else det.ctd_vendido
                   end cant_vend_final,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then p.val_max_frac
                   else 1
                   end fac_conversion,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then 'X'
                   else ' '
                   end ind_fraccion,
                   det.prec_total,
                   det.dcto_total,
                   rank() over (order by det.prec_total asc) orden
            from   (
                    select j.cod_grupo_cia,j.cod_local,j.cod_prod,j.cod_cliente_sap,j.tip_ped_vta,
                           sum(j.cant_atendida/j.val_frac) ctd_vendido,
                           --sum(j.new_val_prec_total) prec_total,
                           sum(j.old_val_prec_total) prec_total,
                           sum(j.new_dcto_x_prod) dcto_total
                    from   TMP_AUX_DET_PEDIDO_INT_VTA j
                    where  j.tip_comp_pago = fSC.Tip_Comp_Pago
                    and    j.num_comp_pago = fSC.Num_Comp_Pago
                    group by j.cod_grupo_cia,j.cod_local,j.cod_prod,j.cod_cliente_sap,j.tip_ped_vta
                   )det,
                   lgt_prod p
            where  det.cod_grupo_cia = p.cod_grupo_cia
            and    det.cod_prod = p.cod_prod
            order by rank() over (order by det.prec_total asc)
            ) Det_Doc;

       --=========================================================--
       -- REGISTRO DEL REDONDEO DE CADA DOCUMENTO
       --=========================================================--
       AUX_GRABA_REDONDEO(cCodGrupoCia_in,cCodLocal_in,v_nNumSecDoc,
                          v_vNumDocRef,v_TipComp_SAP,vTotalDocumento,
                          fSC.Tip_Comp_Pago,fSC.Serie,fSC.Num_Comp_Pago,vFecProceso_in,
                          '','N','N','N','N',
                          vRUC_empresa,vRazonSocial);
       -------------------------------------------------------------
       SET_NUN_DOC_SAP_COMP(cCodGrupoCia_in,cCodLocal_in,fSC.Sec_Comp_Pago,v_nNumSecDoc,
                          fSC.Num_Ped_Vta,'N');

    END LOOP;
    close curFactMueveKardex;
 end;

 /* *************************************************************************** */
 PROCEDURE A_DEVOLUCIONES(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR,
                          vFecProceso_in IN VARCHAR2,
                          CINDCONVENIO_IN IN CHAR
                          )
  AS
  -- SE BUSCAN LOS PEDIDOS QUE SEAN NEGATIVOS (NumPedVta_Origen is not null)
  -- luego de estos pedidos se bscan sus comprobantes originales y ver cuales estan anulados
  -- dado que la anulacion puede ser
  -- Pedido Completo(Anula todos los comprobantes)
  -- Pedido Parcial (Anula solo algunos Comprobantes)
  CURSOR curSeries_Anula IS
    SELECT c.TIP_COMP, NUM_SERIE_LOCAL
      FROM VTA_SERIE_LOCAL c
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND TIP_COMP in (COMP_BOLETA, COMP_TK_BOL,COMP_FACTURA)
       AND EST_SERIE_LOCAL = 'A'
       AND EXISTS
     (SELECT 1
              FROM vta_comp_pago e,
                   vta_pedido_vta_cab ca
             WHERE e.cod_grupo_cia = ca.cod_grupo_cia
               and e.cod_local = ca.cod_local
               and e.num_ped_vta = ca.num_ped_vta
               and e.COD_GRUPO_CIA = cCodGrupoCia_in
               AND e.COD_LOCAL = cCodLocal_in
               AND e.TIP_COMP_PAGO in (COMP_BOLETA, COMP_TK_BOL,COMP_FACTURA)
               AND SUBSTR(e.NUM_COMP_PAGO, 1, 3) = c.NUM_SERIE_LOCAL
               -- ESTO ES PARA SABER QUE COMPROBANTES ESTAN ANULADOS
               AND e.IND_COMP_ANUL='S'
               AND E.FEC_ANUL_COMP_PAGO IS NOT NULL
               and e.fec_anul_comp_pago BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
               AND E.NUM_SEC_DOC_SAP IS NOT NULL
               AND E.NUM_SEC_DOC_SAP_ANUL IS NULL
               and ca.fec_ped_vta >= (
                                      select nvl( (select t.fech_migra_mfa
                                                    from   PBL_LOCAL_MIGRA t
                                                    where  t.cod_local = cCodLocal_in),
                                      			to_date('01/01/2012','dd/mm/yyyy') ) from dual
                                      )
               AND exists (
                           select 1
                           from   vta_pedido_vta_cab t
                           where  t.est_ped_vta = 'C'
                           and    t.cod_grupo_cia = e.cod_grupo_cia
                           and    t.cod_local = e.cod_local
                           --- ESTO ES PARA SABER SI ES UN PEDIDO Q CORRESPONDE A UNA ANULACION
                           -- INICIO
                           and    nvl(t.ind_conv_btl_mf,t.ind_ped_convenio)= cIndConvenio_in
                           and    t.ind_ped_convenio= cIndConvenio_in
                           and    t.Num_Ped_Vta_Origen = e.Num_Ped_Vta
                           AND    T.NUM_PED_VTA_ORIGEN IS NOT NULL
                           -- FIN
                           AND    t.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                           )
        union
        SELECT 1
              FROM vta_comp_pago e,
                   vta_pedido_vta_cab ca
             WHERE e.cod_grupo_cia = ca.cod_grupo_cia
               and e.cod_local = ca.cod_local
               and e.num_ped_vta = ca.num_ped_vta
               and  e.COD_GRUPO_CIA = cCodGrupoCia_in
               AND e.COD_LOCAL = cCodLocal_in
               AND e.TIP_COMP_PAGO in (COMP_BOLETA, COMP_TK_BOL,COMP_FACTURA)
               AND SUBSTR(e.NUM_COMP_PAGO, 1, 3) = c.NUM_SERIE_LOCAL
               -- ESTO ES PARA SABER QUE COMPROBANTES ESTAN ANULADOS
               AND e.IND_COMP_ANUL='S'
               AND E.FEC_ANUL_COMP_PAGO IS NOT NULL
               and e.fec_anul_comp_pago BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
               --AND E.NUM_SEC_DOC_SAP IS NOT NULL
               --AND E.NUM_SEC_DOC_SAP_ANUL IS NULL
               and ca.fec_ped_vta < (
                                      select nvl( (select t.fech_migra_mfa
                                                    from   PBL_LOCAL_MIGRA t
                                                    where  t.cod_local = cCodLocal_in),
                                      			to_date('01/01/2012','dd/mm/yyyy') ) from dual
                   )
               AND exists (
                           select 1
                           from   vta_pedido_vta_cab t
                           where  t.est_ped_vta = 'C'
                           and    t.cod_grupo_cia = e.cod_grupo_cia
                           and    t.cod_local = e.cod_local
                           --- ESTO ES PARA SABER SI ES UN PEDIDO Q CORRESPONDE A UNA ANULACION
                           -- INICIO
                           and    nvl(t.ind_conv_btl_mf,t.ind_ped_convenio)= cIndConvenio_in
                           and    t.ind_ped_convenio= cIndConvenio_in
                           and    t.Num_Ped_Vta_Origen = e.Num_Ped_Vta
                           AND    T.NUM_PED_VTA_ORIGEN IS NOT NULL
                           -- FIN
                           AND    t.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                           )
                   )
    order by 1,2;

 fila curSeries_Anula%rowtype;

 cursor curDocAnulMuevenKardex is
  select distinct g.tip_comp_pago,g.num_comp_pago,g.serie,g.sec_num_comp,
                  g.num_ped_vta,g.sec_comp_pago,g.nt_num_ped_vta,g.NUM_SEC_DOC_SAP
  from   TMP_ANULA_DET_PEDIDO_INT_VTA g
  where  g.tip_comp_pago in (COMP_BOLETA, COMP_TK_BOL,COMP_FACTURA)
  and    g.ind_mueve_kardex = 'S'
  order by g.tip_comp_pago asc,g.serie asc,g.sec_num_comp asc;

 fSC curDocAnulMuevenKardex%rowtype;
    --=====================================================--
    --------------   VARIBLES AUXILIARES -------------------
    v_nNumSecDoc int_vta_mf.documento%TYPE;
    v_vNumDocRef int_vta_mf.num_documento_refer%TYPE;
    v_vNumDocANUL int_vta_mf.num_documento_anu%TYPE;
    v_TipComp_SAP varchar2(10);
    vRUC_empresa int_vta_mf.ruccliente%type;
    vRazonSocial int_vta_mf.des_razon_social%type;
    --=====================================================--
    vTotalDocumento number(8,2);
    v_nCantRedondeo number(8,2);
     v_cia char(3);

  BEGIN
    -- graba detalle de las anulaciones
    open curSeries_Anula;
    LOOP
      FETCH curSeries_Anula INTO fila;
      EXIT WHEN curSeries_Anula%NOTFOUND;
      dbms_output.put_line('>>>> ANULAA >>>');
      dbms_output.put_line('fila.tip_comp:'||fila.tip_comp);
      dbms_output.put_line('fila.num_se:'||fila.num_serie_local);

      AUX_SAVE_DET_ANULA_X_COMP(cCodGrupoCia_in,cCodLocal_in,fila.tip_comp,fila.num_serie_local,vFecProceso_in,CINDCONVENIO_IN);
    END LOOP;
    close curSeries_Anula;


    open curDocAnulMuevenKardex;
    LOOP
      FETCH curDocAnulMuevenKardex INTO fSC;
      EXIT WHEN curDocAnulMuevenKardex%NOTFOUND;
      dbms_output.put_line('fila>>> :'||fSC.Num_Comp_Pago);
       ---
       v_TipComp_SAP := GET_TIP_COMP_SAP(fSC.Tip_Comp_Pago,'S');
       ---
       v_nNumSecDoc:= GET_SEC_DOC(cCodGrupoCia_in,cCodLocal_in);
       dbms_output.put_line('GOGOOOO>>>> '||fSC.NUM_SEC_DOC_SAP);
       ------------------------------------------------------

       if fSC.num_sec_doc_sap is not null then
           SELECT DISTINCT NUM_DOC_REF
           into   v_vNumDocRef
           FROM   (
                  select e.cod_local,e.num_sec_doc,e.num_doc_ref
                  from  INT_VENTA e
                  where  e.FEC_PROCESO <= to_date('01/06/2013','dd/mm/yyyy') - 1/24/60/60
                  --where  e.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
                  union
                  select a.cod_local,trim(a.documento)*1,a.num_documento_refer
                  from   int_vta_mf a
                  union
                  select null,null,null
                  from  dual
                  UNION
                  SELECT V.COD_LOCAL,V.NUM_SEC_DOC_SAP*1,V.num_doc_ref
                  FROM   INT_VTA_AUX  V
                  ) T_INT
           WHERE  T_INT.NUM_SEC_DOC = fSC.NUM_SEC_DOC_SAP
           AND    T_INT.COD_LOCAL = cCodLocal_in;

       else
         -- SI ES NULO SE ARMARA LA REFERENCIA DEL COMPROBANTE
         v_vNumDocRef :=  GET_NUM_DOC_REF(
                            cCodGrupoCia_in,cCodLocal_in,fsc.tip_comp_pago,fSC.Serie,
                            fSC.Num_Comp_Pago ,fSC.Num_Comp_Pago,
                            GET_TIP_COMP_SAP(fsc.tip_comp_pago,'N'),'S');
       end if;
       -------------------------------------------------------
       v_vNumDocANUL := GET_NUM_DOC_REF(cCodGrupoCia_in,cCodLocal_in,fSC.Tip_Comp_Pago,fSC.Serie,
                                      fSC.Num_Comp_Pago,fSC.Num_Comp_Pago,v_TipComp_SAP,'S');


      --1.- calcula el total del documento de ese grupo
      select round(sum(det.prec_total - det.dcto_total),2)
      into   vTotalDocumento
      from   (
              select j.cod_grupo_cia,j.cod_local,j.cod_prod,
                     sum(j.cant_atendida/j.val_frac) ctd_vendido,
                     --sum(j.new_val_prec_total) prec_total,
                     sum(j.old_val_prec_total) prec_total,
                     sum(j.new_dcto_x_prod) dcto_total
              from   TMP_ANULA_DET_PEDIDO_INT_VTA j
              where  j.tip_comp_pago = fSC.Tip_Comp_Pago
              and    j.num_comp_pago = fSC.Num_Comp_Pago
              group by j.cod_grupo_cia,j.cod_local,j.cod_prod
             )det;

     SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO),2)
                  INTO v_nCantRedondeo
                  FROM  VTA_COMP_PAGO e
                  WHERE exists (
                                select 1
                                from   TMP_ANULA_DET_PEDIDO_INT_VTA a
                                where  a.tip_comp_pago = fSC.Tip_Comp_Pago
                                and    a.num_comp_pago = fSC.Num_Comp_Pago
                                and    a.cod_grupo_cia = e.cod_grupo_cia
                                and    a.cod_local = e.cod_local
                                and    a.num_ped_vta = e.num_ped_vta
                                and    a.num_comp_pago = e.num_comp_pago
                               );
     vTotalDocumento := vTotalDocumento ;-- - v_nCantRedondeo;

      IF fSC.Tip_Comp_Pago = COMP_FACTURA then
          select  trim(C.RUC_CLI_PED_VTA),AUX_REEMPLAZAR_CHAR(SUBSTR(C.NOM_CLI_PED_VTA,1,40))
          into    vRUC_empresa,vRazonSocial
          from    vta_pedido_vta_cab c
          where   c.cod_grupo_cia = cCodGrupoCia_in
          and     c.cod_local = cCodLocal_in
          and     c.num_ped_vta = fSC.Num_Ped_Vta
          /*and     c.ind_conv_btl_mf  = CINDCONVENIO_IN
          and     c.ind_ped_convenio = CINDCONVENIO_IN*/;
      ELSE
          vRUC_empresa := '';
          vRazonSocial := '';
      END IF;

       /*--=========================================================--
        GRABA INTERFAZ PARA LOS DOCUMENTOS BOLETA Y TICKET BOLETA
        Doc agrupados y que mueven kardex.
       --=========================================================--*/
       select cod_cia into v_cia from pbl_local where cod_local=cCodLocal_in;
       
       insert into INT_VTA_MF nologging
       (
       /*1*/ MANDANTE,COD_LOCAL,DOCUMENTO,
       /*2*/ CORRELATIVO,FECHAEMISION,
       /*3*/ RUCCLIENTE,DES_RAZON_SOCIAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO_REFER,
       /*4*/ NUM_DOCUMENTO_ANU,COD_TIPO_VENTA,
       /*5*/ TIPO_LABORATORIO,TIPO_POSICION,
       /*6*/ COD_PRODUCTO,CANTIDAD,UNIDAD,
       /*7*/ FRACCIONADA,FACTOR,
       /*8*/ MTO_TOTAL_POS,MONEDA,MTO_TOTAL,AJUSTE,
       /*9*/ COD_CLI_SAP,COD_PAGO,IMP_DSCTO,
      /*10*/ FCH_ARCHIVO,FCH_VENTA,USU_CREA,USU_MOD,FECH_CREA,FECH_MOD,COD_GRUPO_CIA)
       select
       /*1*/  C_MANDANTE,cCodLocal_in,v_nNumSecDoc,
       /*2*/  Farma_Utility.COMPLETAR_CON_SIMBOLO(rownum,6,'0','I'),to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd'),
       /*3*/  vRUC_empresa,vRazonSocial,v_TipComp_SAP,v_vNumDocRef,
       /*4*/  v_vNumDocANUL,GET_TIP_VTA_SAP(Det_Doc.tip_ped_vta),
       /*5*/  DECODE(Det_Doc.IND_PROD_PROPIO,'S','1','N','2'),C_POS_VENTA,
       /*6*/  Det_Doc.cod_prod,to_char(Det_Doc.cant_vend_final,decode(v_cia,MARCA_BTL,'99999999990.000','000000000.00')),C_UNIDAD,
       /*7*/  det_doc.ind_fraccion,DECODE(det_doc.ind_fraccion,'X',decode(v_cia,MARCA_BTL,det_doc.fac_conversion,TO_CHAR(det_doc.fac_conversion,'0000')),'1'),
       /*8*/  case
                  when det_doc.prec_total < 0 then
                   to_char(det_doc.prec_total, '9,999,990.00S')
                  else
                   to_char(det_doc.prec_total, '99,999,990.00')
              end,C_MONEDA_SOLES,
              case
                  when vTotalDocumento < 0 then
                   to_char(vTotalDocumento, '9,999,990.00S')
                  else
                   to_char(vTotalDocumento, '99,999,990.00')
              end,
              to_char(0,'9999,999,990.00')/*redondeo cero de manera temporal*/,
       /*9*/  det_doc.cod_cliente_sap,C_CONDICION_PAGO,to_char(det_doc.dcto_total,'9999999990.00'),
      /*10*/  null,to_date(vFecProceso_in,'dd/mm/yyyy'),'SISTEMAS',null,sysdate,null,cCodGrupoCia_in
     from
            (
             select det.cod_grupo_cia,det.cod_local,det.cod_prod,det.cod_cliente_sap,det.tip_ped_vta,p.ind_prod_propio,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then det.ctd_vendido*p.val_max_frac
                   else det.ctd_vendido
                   end cant_vend_final,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then p.val_max_frac
                   else 1
                   end fac_conversion,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then 'X'
                   else ' '
                   end ind_fraccion,
                   det.prec_total,
                   det.dcto_total,
                   rank() over (order by det.prec_total asc) orden
            from   (
                    select j.cod_grupo_cia,j.cod_local,j.cod_prod,j.cod_cliente_sap,j.tip_ped_vta,
                           sum(j.cant_atendida/j.val_frac) ctd_vendido,
                           --sum(j.new_val_prec_total) prec_total,
                            sum(j.old_val_prec_total) prec_total,
                           sum(j.new_dcto_x_prod) dcto_total
                    from   TMP_ANULA_DET_PEDIDO_INT_VTA j
                    where  j.tip_comp_pago = fSC.Tip_Comp_Pago
                    and    j.num_comp_pago = fSC.Num_Comp_Pago
                    group by j.cod_grupo_cia,j.cod_local,j.cod_prod,j.cod_cliente_sap,j.tip_ped_vta
                   )det,
                   lgt_prod p
            where  det.cod_grupo_cia = p.cod_grupo_cia
            and    det.cod_prod = p.cod_prod
            order by rank() over (order by det.prec_total asc)
            ) Det_Doc;

       --=========================================================--
       -- REGISTRO DEL REDONDEO DE CADA DOCUMENTO
       --=========================================================--
       AUX_GRABA_REDONDEO(cCodGrupoCia_in,cCodLocal_in,v_nNumSecDoc,
                          v_vNumDocRef,v_TipComp_SAP,vTotalDocumento,
                          fSC.Tip_Comp_Pago,fSC.Serie,fSC.Num_Comp_Pago,vFecProceso_in,v_vNumDocANUL,
                          'S','N','N','N',
                          nvl(vRUC_empresa,''),nvl(vRazonSocial,''));
       -------------------------------------------------------------
       SET_NUN_DOC_SAP_COMP(cCodGrupoCia_in,cCodLocal_in,fSC.Sec_Comp_Pago,v_nNumSecDoc,
                          fSC.Num_Ped_Vta,                          'S');


    END LOOP;
    close curDocAnulMuevenKardex;

  END;
 /* *************************************************************************** */
PROCEDURE A_NOTAS_CREDITO(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR,
                          vFecProceso_in IN VARCHAR2,
                          cIndConvenio_in IN CHAR)
  AS

  CURSOR curSeries_NC IS
    SELECT c.TIP_COMP, NUM_SERIE_LOCAL
      FROM VTA_SERIE_LOCAL c
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND TIP_COMP in (COMP_NC)
       AND EST_SERIE_LOCAL = 'A'
       AND EXISTS
               (SELECT 1
                        FROM vta_comp_pago e
                       WHERE e.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND e.COD_LOCAL = cCodLocal_in
                         AND e.TIP_COMP_PAGO in (COMP_NC)
                         AND SUBSTR(e.NUM_COMP_PAGO, 1, 3) = c.NUM_SERIE_LOCAL
                         -- ESTO ES PARA SABER QUE COMPROBANTES ESTAN ANULADOS
                         AND e.NUM_SEC_DOC_SAP is null
                         AND exists (
                                     select 1
                                     from   vta_pedido_vta_cab t
                                     where  t.est_ped_vta = 'C'
                                     and    t.cod_grupo_cia = e.cod_grupo_cia
                                     and    t.cod_local = e.cod_local
                                     and    nvl(t.ind_conv_btl_mf,t.ind_ped_convenio)= cIndConvenio_in
                                     and    t.ind_ped_convenio= cIndConvenio_in
                                     --- ESTO ES PARA SABER SI ES UN PEDIDO Q CORRESPONDE A UNA ANULACION
                                     -- INICIO
                                     and    t.num_ped_vta = e.Num_Ped_Vta
                                     AND    T.NUM_PED_VTA_ORIGEN IS NOT NULL
                                     and    t.tip_comp_pago = COMP_NC -- es NOTA DE CREDITO
                                     -- FIN
                                     AND    t.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in,'dd/MM/yyyy') + 1 - 1/24/60/60
                                     )
                             )
    order by 1,2;
 fila curSeries_NC%rowtype;

 cursor curNCMuevenKardex is
  select distinct g.tip_comp_pago,g.num_comp_pago,g.serie,g.sec_num_comp,
                  g.num_ped_vta,g.sec_comp_pago,g.nt_num_ped_vta,
                  g.origen_tip_comp_pago,g.origen_num_sec_doc_sap,g.origen_num_comp_pago,
                  SUBSTR(g.origen_num_comp_pago,1,3) Serie_origen,
                  g.sec_comp_pago_origen
  from   TMP_ANULA_DET_PED_NOTA_CREDITO g
  where  g.tip_comp_pago in (COMP_NC)
  and    g.ind_mueve_kardex = 'S'
  order by g.tip_comp_pago asc,g.serie asc,g.sec_num_comp asc;

 fSC curNCMuevenKardex%rowtype;

    --=====================================================--
    --------------   VARIBLES AUXILIARES -------------------
    v_nNumSecDoc int_vta_mf.documento%TYPE;
    v_vNumDocRef int_vta_mf.num_documento_refer%TYPE;
    v_vNumDocANUL int_vta_mf.num_documento_anu%TYPE;
    v_TipComp_SAP varchar2(10);
    vRUC_empresa int_vta_mf.ruccliente%type;
    vRazonSocial int_vta_mf.des_razon_social%type;
    --=====================================================--
    vTotalDocumento number(8,2);
    v_nCantRedondeo number(8,2);
    v_cia char(3);
  BEGIN
    -- graba detalle de las anulaciones
    open curSeries_NC;
    LOOP
      FETCH curSeries_NC INTO fila;
      EXIT WHEN curSeries_NC%NOTFOUND;

      AUX_SAVE_DET_NOTA_CREDITO(cCodGrupoCia_in,cCodLocal_in,fila.tip_comp,fila.num_serie_local,vFecProceso_in,cIndConvenio_in);
    END LOOP;
    close curSeries_NC;

    open curNCMuevenKardex;
    LOOP
      FETCH curNCMuevenKardex INTO fSC;
      EXIT WHEN curNCMuevenKardex%NOTFOUND;

       ---
       v_TipComp_SAP := GET_TIP_COMP_SAP(fSC.Tip_Comp_Pago,'S',fSC.Origen_Tip_Comp_Pago);
       ---
       v_nNumSecDoc:= GET_SEC_DOC(cCodGrupoCia_in,cCodLocal_in);
       ------------------------------------------------------
       dbms_output.put_line('GOOGOO >> '||fSC.Origen_Num_Sec_Doc_Sap);
       dbms_output.put_line('GOOGOO >> '||fSC.Num_Comp_Pago);

       if fSC.Origen_Num_Sec_Doc_Sap is not null then
          SELECT DISTINCT NUM_DOC_REF
                 into   v_vNumDocRef
                 FROM   (
                        select e.cod_local,e.num_sec_doc,e.num_doc_ref
                        from  INT_VENTA e
                        where e.FEC_PROCESO <= to_date('01/06/2013','dd/mm/yyyy') - 1/24/60/60
                        --where e.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
                        union
                        select a.cod_local,trim(a.documento)*1,a.num_documento_refer
                        from   int_vta_mf a
                        union
                        select null,null,null
                        from  dual
                        UNION
                        SELECT V.COD_LOCAL,V.NUM_SEC_DOC_SAP*1,V.num_doc_ref
                        FROM   INT_VTA_AUX  V
                        ) T_INT
                 WHERE  T_INT.NUM_SEC_DOC = fSC.Origen_Num_Sec_Doc_Sap
                 AND    T_INT.COD_LOCAL = cCodLocal_in;

       else
         -- SI ES NULO SE ARMARA LA REFERENCIA DEL COMPROBANTE
         v_vNumDocRef :=  GET_NUM_DOC_REF(
                            cCodGrupoCia_in,cCodLocal_in,fsc.tip_comp_pago,fSC.Serie,
                            fSC.Num_Comp_Pago ,fSC.Num_Comp_Pago,
                            GET_TIP_COMP_SAP(fsc.tip_comp_pago,'N'),'S');
       end if;


       -------------------------------------------------------
       v_vNumDocANUL := GET_NUM_DOC_REF(cCodGrupoCia_in,cCodLocal_in,fSC.Origen_Tip_Comp_Pago,fSC.Serie_origen,
                                      fSC.Origen_Num_Comp_Pago,fSC.Origen_Num_Comp_Pago,v_TipComp_SAP,'S');


      --1.- calcula el total del documento de ese grupo
      select round(sum(det.prec_total-det.dcto_total),2)
      into   vTotalDocumento
      from   (
              select j.cod_grupo_cia,j.cod_local,j.cod_prod,
                     sum(abs(j.cant_atendida/j.val_frac)) ctd_vendido,
                     --sum(abs(j.new_val_prec_total)) prec_total,
                     sum(abs(j.old_val_prec_total)) prec_total,
                     sum(abs(j.new_dcto_x_prod)) dcto_total
              from   TMP_ANULA_DET_PED_NOTA_CREDITO j
              where  j.tip_comp_pago = fSC.Tip_Comp_Pago
              and    j.num_comp_pago = fSC.Num_Comp_Pago
              and    j.origen_tip_comp_pago = fSC.Origen_Tip_Comp_Pago
              and    j.origen_num_sec_doc_sap = fSC.Origen_Num_Sec_Doc_Sap
              and    j.origen_num_comp_pago = fSC.Origen_Num_Comp_Pago
              and    j.sec_comp_pago_origen = fsc.sec_comp_pago_origen
              group by j.cod_grupo_cia,j.cod_local,j.cod_prod
             )det;

    SELECT ROUND(SUM(E.VAL_REDONDEO_COMP_PAGO*-1),2)
            INTO v_nCantRedondeo
            FROM  VTA_COMP_PAGO e
            WHERE exists (
                          select 1
                          from   TMP_ANULA_DET_PED_NOTA_CREDITO a
                          where  a.cod_grupo_cia = cCodGrupoCia_in
                          and    a.cod_local = cCodLocal_in
                          and    a.tip_comp_pago = fSC.Tip_Comp_Pago
                          and    a.serie =  fSC.Serie
                          and    a.num_ped_vta =  fSC.Num_Ped_Vta
                          --and    a.sec_comp_pago_origen = cSecCompOrigen
                          and    a.cod_grupo_cia = e.cod_grupo_cia
                          and    a.cod_local = e.cod_local
                          and    a.num_ped_vta = e.num_ped_vta
                          and    a.num_comp_pago = e.num_comp_pago
                         );

     vTotalDocumento := vTotalDocumento ;-- - v_nCantRedondeo;

      IF fSC.Tip_Comp_Pago = COMP_FACTURA then
          select  trim(C.RUC_CLI_PED_VTA),AUX_REEMPLAZAR_CHAR(SUBSTR(C.NOM_CLI_PED_VTA,1,40))
          into    vRUC_empresa,vRazonSocial
          from    vta_pedido_vta_cab c
          where   c.cod_grupo_cia = cCodGrupoCia_in
          and     c.cod_local = cCodLocal_in
          and     c.num_ped_vta = fSC.Num_Ped_Vta;
      ELSE
          vRUC_empresa := '';
          vRazonSocial := '';
      END IF;

       /*--=========================================================--
        GRABA INTERFAZ PARA LOS DOCUMENTOS BOLETA Y TICKET BOLETA
        Doc agrupados y que mueven kardex.
       --=========================================================--*/
       select cod_cia into v_cia from pbl_local where cod_local=cCodLocal_in;
       
       insert into INT_VTA_MF nologging
       (
       /*1*/ MANDANTE,COD_LOCAL,DOCUMENTO,
       /*2*/ CORRELATIVO,FECHAEMISION,
       /*3*/ RUCCLIENTE,DES_RAZON_SOCIAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO_REFER,
       /*4*/ NUM_DOCUMENTO_ANU,COD_TIPO_VENTA,
       /*5*/ TIPO_LABORATORIO,TIPO_POSICION,
       /*6*/ COD_PRODUCTO,CANTIDAD,UNIDAD,
       /*7*/ FRACCIONADA,FACTOR,
       /*8*/ MTO_TOTAL_POS,MONEDA,MTO_TOTAL,AJUSTE,
       /*9*/ COD_CLI_SAP,COD_PAGO,IMP_DSCTO,
      /*10*/ FCH_ARCHIVO,FCH_VENTA,USU_CREA,USU_MOD,FECH_CREA,FECH_MOD,COD_GRUPO_CIA,
             SEC_COMP_PAGO_ORIGEN
             )
       select
       /*1*/  C_MANDANTE,cCodLocal_in,v_nNumSecDoc,
       /*2*/  Farma_Utility.COMPLETAR_CON_SIMBOLO(rownum,6,'0','I'),to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd'),
       /*3*/  vRUC_empresa,vRazonSocial,v_TipComp_SAP,v_vNumDocRef,
       /*4*/  v_vNumDocANUL,GET_TIP_VTA_SAP(Det_Doc.tip_ped_vta),
       /*5*/  DECODE(Det_Doc.IND_PROD_PROPIO,'S','1','N','2'),C_POS_VENTA,
       /*6*/  Det_Doc.cod_prod,to_char(Det_Doc.cant_vend_final,decode(v_cia,MARCA_BTL,'99999999990.000','000000000.00')),C_UNIDAD,
       /*7*/  det_doc.ind_fraccion,DECODE(det_doc.ind_fraccion,'X',decode(v_cia,MARCA_BTL,det_doc.fac_conversion,TO_CHAR(det_doc.fac_conversion,'0000')),'1'),
       /*8*/  case
                  when det_doc.prec_total < 0 then
                   to_char(det_doc.prec_total, '9,999,990.00S')
                  else
                   to_char(det_doc.prec_total, '99,999,990.00')
              end,C_MONEDA_SOLES,
              case
                  when vTotalDocumento < 0 then
                   to_char(vTotalDocumento, '9,999,990.00S')
                  else
                   to_char(vTotalDocumento, '99,999,990.00')
              end,
              to_char(0,'9999,999,990.00')/*redondeo cero de manera temporal*/,
       /*9*/  det_doc.cod_cliente_sap,C_CONDICION_PAGO,to_char(det_doc.dcto_total,'9999999990.00'),
      /*10*/  null,to_date(vFecProceso_in,'dd/mm/yyyy'),'SISTEMAS',null,sysdate,null,cCodGrupoCia_in,
              fSC.Sec_Comp_Pago_Origen
     from
            (
             select det.cod_grupo_cia,det.cod_local,det.cod_prod,det.cod_cliente_sap,det.tip_ped_vta,p.ind_prod_propio,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then det.ctd_vendido*p.val_max_frac
                   else det.ctd_vendido
                   end cant_vend_final,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then p.val_max_frac
                   else 1
                   end fac_conversion,
                   case
                   when det.ctd_vendido - trunc(det.ctd_vendido) > 0 then 'X'
                   else ' '
                   end ind_fraccion,
                   det.prec_total,
                   det.dcto_total,
                   rank() over (order by det.prec_total asc) orden
            from   (
                    select j.cod_grupo_cia,j.cod_local,j.cod_prod,j.cod_cliente_sap,j.tip_ped_vta,
                           sum(abs(j.cant_atendida/j.val_frac)) ctd_vendido,
                           --sum(abs(j.new_val_prec_total)) prec_total,
                           sum(abs(j.old_val_prec_total)) prec_total,
                           sum(abs(j.new_dcto_x_prod)) dcto_total
                    from   TMP_ANULA_DET_PED_NOTA_CREDITO j
                    where  j.tip_comp_pago = fSC.Tip_Comp_Pago
                    and    j.num_comp_pago = fSC.Num_Comp_Pago
                    and    j.sec_comp_pago_origen = fSC.Sec_Comp_Pago_Origen
                    group by j.cod_grupo_cia,j.cod_local,j.cod_prod,j.cod_cliente_sap,j.tip_ped_vta
                   )det,
                   lgt_prod p
            where  det.cod_grupo_cia = p.cod_grupo_cia
            and    det.cod_prod = p.cod_prod
            order by rank() over (order by det.prec_total asc)
            ) Det_Doc;

       --=========================================================--
       -- REGISTRO DEL REDONDEO DE CADA DOCUMENTO
       --=========================================================--
       AUX_GRABA_REDONDEO(cCodGrupoCia_in,cCodLocal_in,v_nNumSecDoc,
                          v_vNumDocRef,v_TipComp_SAP,vTotalDocumento,
                          fSC.Tip_Comp_Pago,fSC.Serie,fSC.Num_Comp_Pago,vFecProceso_in,v_vNumDocANUL,'N',
                          fSC.Sec_Comp_Pago_Origen,fSC.Num_Ped_Vta,
                          'N',
                           nvl(vRUC_empresa,''),nvl(vRazonSocial,'')
                          );
       -------------------------------------------------------------
       SET_NUN_DOC_SAP_COMP(cCodGrupoCia_in,cCodLocal_in,fSC.Sec_Comp_Pago,v_nNumSecDoc,fSC.Num_Ped_Vta,'S');


    END LOOP;
    close curNCMuevenKardex;

  END;
  /* ********************************************************************* */
  PROCEDURE S_SERVICIOS(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 vFecProceso_in IN VARCHAR2,
                                 cIndConvenio_in IN CHAR)
  as
  begin
    dbms_output.put_line('antes de S_SERVICIOS_COBRADOS');
     --S_SERVICIOS_COBRADOS(cCodGrupoCia_in,cCodLocal_in,vFecProceso_in,cIndConvenio_in);
    dbms_output.put_line('antes de S_SERVICIOS_ANULADOS');
     S_SERVICIOS_ANULADOS(cCodGrupoCia_in,cCodLocal_in,vFecProceso_in,cIndConvenio_in);
    dbms_output.put_line('antes de S_SERVICIOS_NOTA_CREDITO');
     S_SERVICIOS_NOTA_CREDITO(cCodGrupoCia_in,cCodLocal_in,vFecProceso_in,cIndConvenio_in);
  end;
 /* *************************************************************************** */
  PROCEDURE S_SERVICIOS_COBRADOS(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 vFecProceso_in IN VARCHAR2,
                                 CINDCONVENIO_IN IN CHAR)
  AS

    CURSOR curServCobrados is
      select distinct g.tip_comp_pago,
                    g.num_comp_pago,
             g.serie,g.sec_num_comp,g.num_ped_vta,g.sec_comp_pago,
             g.num_comp_pago as "XXX"
      from   TMP_AUX_DET_PEDIDO_INT_VTA g
      where  g.ind_mueve_kardex = 'N'
      order by g.tip_comp_pago asc,g.serie asc,g.sec_num_comp asc;
    fSC curServCobrados%rowtype;
    --=====================================================--
    --------------   VARIBLES AUXILIARES -------------------
    v_nNumSecDoc int_vta_mf.documento%TYPE;
    v_vNumDocRef int_vta_mf.num_documento_refer %TYPE;
    v_TipComp_SAP varchar2(10);
    vRUC_empresa int_vta_mf.ruccliente%type;
    vRazonSocial int_vta_mf.des_razon_social%type;
    --=====================================================--
    vTotalDocumento number(8,2);
    v_cia char(3);

  begin
   null;

   /* ==== 1.- SERVICIOS DE COBRO ===== */
      open curServCobrados;
      LOOP
        FETCH curServCobrados INTO fSC;
        EXIT WHEN curServCobrados%NOTFOUND;

           v_TipComp_SAP := TIP_COMP_SAP_SERVICIO;

           v_nNumSecDoc:= GET_SEC_DOC(cCodGrupoCia_in,cCodLocal_in);
           v_vNumDocRef:= GET_NUM_DOC_REF(cCodGrupoCia_in,cCodLocal_in,fSC.Tip_Comp_Pago,fSC.Serie,
                                          fSC.Num_Comp_Pago,fSC.Num_Comp_Pago,v_TipComp_SAP,'N'
                                          );



          --1.- calcula el total del documento de ese grupo
          --select round(sum(det.prec_total - det.dcto_total),2)
          select round(sum(det.prec_total-det.dcto_total),2)
          into   vTotalDocumento
          from   (
                  select j.cod_grupo_cia,j.cod_local,j.cod_prod,
                         sum(j.cant_atendida/j.val_frac) ctd_vendido,
                         --sum(j.new_val_prec_total) prec_total,
                         sum(j.old_val_prec_total) prec_total,
                         sum(j.new_dcto_x_prod) dcto_total
                  from   TMP_AUX_DET_PEDIDO_INT_VTA j
                  where  j.tip_comp_pago = fSC.Tip_Comp_Pago
                  and    j.num_comp_pago = fSC.Num_Comp_Pago
                  group by j.cod_grupo_cia,j.cod_local,j.cod_prod
                 )det;

      IF fSC.Tip_Comp_Pago = COMP_FACTURA then
          select  trim(C.RUC_CLI_PED_VTA),AUX_REEMPLAZAR_CHAR(SUBSTR(C.NOM_CLI_PED_VTA,1,40))
          into    vRUC_empresa,vRazonSocial
          from    vta_pedido_vta_cab c
          where   c.cod_grupo_cia = cCodGrupoCia_in
          and     c.cod_local = cCodLocal_in
          and     c.num_ped_vta = fSC.Num_Ped_Vta
          /*and     c.ind_conv_btl_mf= cIndConvenio_in
          and     c.ind_ped_convenio= cIndConvenio_in*/;
      ELSE
          vRUC_empresa := '';
          vRazonSocial := '';
      END IF;

      dbms_output.put_line(fSC.Tip_Comp_Pago||'-2');
      dbms_output.put_line(fSC.Num_Comp_Pago||'-3');
      dbms_output.put_line(fSC.Xxx||'-4');


           /*--=========================================================--
            GRABA INTERFAZ PARA LOS DOCUMENTOS BOLETA Y TICKET BOLETA
            Doc agrupados y que mueven kardex.
           --=========================================================--*/
           select cod_cia into v_cia from pbl_local where cod_local=cCodLocal_in;
           
           insert into INT_VTA_MF nologging
           (
           /*1*/ MANDANTE,COD_LOCAL,DOCUMENTO,
           /*2*/ CORRELATIVO,FECHAEMISION,
           /*3*/ RUCCLIENTE,DES_RAZON_SOCIAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO_REFER,
           /*4*/ NUM_DOCUMENTO_ANU,COD_TIPO_VENTA,
           /*5*/ TIPO_LABORATORIO,TIPO_POSICION,
           /*6*/ COD_PRODUCTO,CANTIDAD,UNIDAD,
           /*7*/ FRACCIONADA,FACTOR,
           /*8*/ MTO_TOTAL_POS,MONEDA,MTO_TOTAL,AJUSTE,
           /*9*/ COD_CLI_SAP,COD_PAGO,IMP_DSCTO,
          /*10*/ FCH_ARCHIVO,FCH_VENTA,USU_CREA,USU_MOD,FECH_CREA,FECH_MOD,COD_GRUPO_CIA)

           select
           /*1*/  C_MANDANTE,cCodLocal_in,v_nNumSecDoc,
           /*2*/  Farma_Utility.COMPLETAR_CON_SIMBOLO(rownum,6,'0','I'),to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd'),
           /*3*/  vRUC_empresa,vRazonSocial,v_TipComp_SAP,v_vNumDocRef,
           /*4*/  '', GET_TIP_VTA_SAP(Det_Doc.tip_ped_vta,CINDCONVENIO_IN),
           /*5*/  DECODE(Det_Doc.IND_PROD_PROPIO,'S','1','N','2'),C_POS_VENTA,
           /*6*/  Det_Doc.cod_serv_conv,to_char(Det_Doc.cant_vend_final,decode(v_cia,MARCA_BTL,'99999999990.000','000000000.00')),C_UNIDAD,
           /*7*/  det_doc.ind_fraccion,DECODE(det_doc.ind_fraccion,'X',decode(v_cia,MARCA_BTL,det_doc.fac_conversion,TO_CHAR(det_doc.fac_conversion,'0000')),'1'),
           /*8*/  case
                      when det_doc.prec_total < 0 then
                       to_char(det_doc.prec_total, '9,999,990.00S')
                      else
                       to_char(det_doc.prec_total, '99,999,990.00')
                  end,C_MONEDA_SOLES,
                  case
                      when vTotalDocumento < 0 then
                       to_char(vTotalDocumento, '9,999,990.00S')
                      else
                       to_char(vTotalDocumento, '99,999,990.00')
                  end,
                  to_char(0,'9999,999,990.00')/*redondeo cero de manera temporal*/,
           /*9*/  det_doc.cod_cliente_sap,C_CONDICION_PAGO,to_char(det_doc.dcto_total,'9999999990.00'),
          /*10*/  null,to_date(vFecProceso_in,'dd/mm/yyyy'),'SISTEMAS',null,sysdate,null,cCodGrupoCia_in
         from
                (
                 select det.cod_grupo_cia,det.cod_local,det.cod_serv_conv,det.cod_cliente_sap,det.tip_ped_vta,
                       'N' IND_PROD_PROPIO,--CONSTANTE p.ind_prod_propio,
                       1 cant_vend_final,
                       1 fac_conversion,
                       ' ' ind_fraccion,
                       det.prec_total,
                       det.dcto_total,
                       rank() over (order by det.prec_total asc) orden
                from   (
                        select j.cod_grupo_cia,j.cod_local,j.cod_serv_conv,j.cod_cliente_sap,j.tip_ped_vta,
                               sum(j.cant_atendida/j.val_frac) ctd_vendido,
                               --sum(j.new_val_prec_total) prec_total,
                               sum(j.old_val_prec_total) prec_total,
                               sum(j.new_dcto_x_prod) dcto_total
                        from   (
                               select b.cod_grupo_cia,b.cod_local,b.cod_cliente_sap,b.num_comp_pago,b.tip_comp_pago,
                                      GET_COD_SERVICIO_PEDIDO(b.cod_grupo_cia,b.cod_local,b.num_ped_vta) cod_serv_conv,
                                      b.tip_ped_vta,b.cant_atendida,b.val_frac,b.new_val_prec_vta,b.new_dcto_x_prod,
                                      b.new_val_prec_total,b.old_val_prec_total
                               from  TMP_AUX_DET_PEDIDO_INT_VTA b
                               )j
                        where  j.tip_comp_pago = fSC.Tip_Comp_Pago
                        and    j.num_comp_pago = fSC.Num_Comp_Pago
                        group by j.cod_grupo_cia,j.cod_local,j.cod_serv_conv,j.cod_cliente_sap,j.tip_ped_vta
                       )det
                order by rank() over (order by det.prec_total asc)
                ) Det_Doc;

           --=========================================================--
           -- REGISTRO DEL REDONDEO DE CADA DOCUMENTO
           --=========================================================--
           AUX_GRABA_REDONDEO(cCodGrupoCia_in,cCodLocal_in,v_nNumSecDoc,
                              v_vNumDocRef,v_TipComp_SAP,vTotalDocumento,
                              fSC.Tip_Comp_Pago,fSC.Serie,fSC.Num_Comp_Pago,vFecProceso_in,
                              '','N','N','N','S',
                              vRUC_empresa,vRazonSocial
                              );
           -------------------------------------------------------------
           SET_NUN_DOC_SAP_COMP(cCodGrupoCia_in,cCodLocal_in,fSC.Sec_Comp_Pago,v_nNumSecDoc,fSC.Num_Ped_Vta,'N');


        END LOOP;
        close curServCobrados;
   /*****************************************************/


  end;
  /* ******************************************************************* */
  PROCEDURE S_SERVICIOS_ANULADOS(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 vFecProceso_in IN VARCHAR2,
                                 cIndConvenio_in IN CHAR)
  AS

 cursor curServAnulados is
  select distinct g.tip_comp_pago,g.num_comp_pago,g.serie,g.sec_num_comp,
                  g.num_ped_vta,g.sec_comp_pago,g.nt_num_ped_vta,g.NUM_SEC_DOC_SAP
  from   TMP_ANULA_DET_PEDIDO_INT_VTA g
  where  g.tip_comp_pago in (COMP_BOLETA, COMP_TK_BOL,COMP_FACTURA)
  and    g.ind_mueve_kardex = 'N'
  order by g.tip_comp_pago asc,g.serie asc,g.sec_num_comp asc;

 fSC curServAnulados%rowtype;

 nCant number;

    --=====================================================--
    --------------   VARIBLES AUXILIARES -------------------
    v_nNumSecDoc int_vta_mf.documento%TYPE;
    v_vNumDocRef int_vta_mf.num_documento_refer%TYPE;
    v_vNumDocANUL int_vta_mf.num_documento_anu%TYPE;
    v_TipComp_SAP varchar2(10);
    vRUC_empresa int_vta_mf.ruccliente%type;
    vRazonSocial int_vta_mf.des_razon_social%type;
    --=====================================================--
    vTotalDocumento number(8,2);
    v_cia char(3);

  BEGIN
  open curServAnulados;
  LOOP
    FETCH curServAnulados INTO fSC;
    EXIT WHEN curServAnulados%NOTFOUND;
    dbms_output.put_line('>> TIp SERV ANULA >'||fSC.Num_Comp_Pago);
     ---
       v_TipComp_SAP := TIP_COMP_SAP_SERVICIO_ANUL;
       ---
       v_nNumSecDoc:= GET_SEC_DOC(cCodGrupoCia_in,cCodLocal_in);
       ------------------------------------------------------
       SELECT DISTINCT NUM_DOC_REF
       into   v_vNumDocRef
       FROM   (
              select e.cod_local,e.num_sec_doc,e.num_doc_ref
              from  INT_VENTA e
              where e.FEC_PROCESO <= to_date('01/06/2013','dd/mm/yyyy') - 1/24/60/60
              --where e.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
              union
              select a.cod_local,trim(a.documento)*1,a.num_documento_refer
              from   int_vta_mf a
              union
              select null,null,null
              from  dual
              ) T_INT
       WHERE  T_INT.NUM_SEC_DOC = fSC.NUM_SEC_DOC_SAP
       AND    T_INT.COD_LOCAL = cCodLocal_in;

       -------------------------------------------------------
       v_vNumDocANUL := GET_NUM_DOC_REF(cCodGrupoCia_in,cCodLocal_in,fSC.Tip_Comp_Pago,fSC.Serie,
                                      fSC.Num_Comp_Pago,fSC.Num_Comp_Pago,v_TipComp_SAP,'S');

          /*select round(sum(det.prec_total-det.dcto_total),2)
          into   vTotalDocumento
          from   (
                  select j.cod_grupo_cia,j.cod_local,j.cod_prod,
                         sum(j.cant_atendida/j.val_frac) ctd_vendido,
                         --sum(j.new_val_prec_total) prec_total,
                         sum(j.old_val_prec_total) prec_total,
                         sum(j.new_dcto_x_prod) dcto_total
                  from   TMP_AUX_DET_PEDIDO_INT_VTA j
                  where  j.tip_comp_pago = fSC.Tip_Comp_Pago
                  and    j.num_comp_pago = fSC.Num_Comp_Pago
                  group by j.cod_grupo_cia,j.cod_local,j.cod_prod
                 )det;
                 */
      --1.- calcula el total del documento de ese grupo
      select round(sum(det.prec_total - det.dcto_total),2)
      into   vTotalDocumento
      from   (
              select j.cod_grupo_cia,j.cod_local,j.cod_prod,
                     sum(j.cant_atendida/j.val_frac) ctd_vendido,
                     --sum(j.new_val_prec_total) prec_total,
                     sum(j.old_val_prec_total) prec_total,
                     sum(j.new_dcto_x_prod) dcto_total
              from   TMP_ANULA_DET_PEDIDO_INT_VTA j
              where  j.tip_comp_pago = fSC.Tip_Comp_Pago
              and    j.num_comp_pago = fSC.Num_Comp_Pago
              group by j.cod_grupo_cia,j.cod_local,j.cod_prod
             )det;


      IF fSC.Tip_Comp_Pago = COMP_FACTURA then
          select  trim(C.RUC_CLI_PED_VTA),AUX_REEMPLAZAR_CHAR(SUBSTR(C.NOM_CLI_PED_VTA,1,40))
          into    vRUC_empresa,vRazonSocial
          from    vta_pedido_vta_cab c
          where   c.cod_grupo_cia = cCodGrupoCia_in
          and     c.cod_local = cCodLocal_in
          and     c.num_ped_vta = fSC.Num_Ped_Vta
          /*and     c.ind_conv_btl_mf= cIndConvenio_in
          and     c.ind_ped_convenio= cIndConvenio_in*/
          ;
      ELSE
          vRUC_empresa := '';
          vRazonSocial := '';
      END IF;

       /*--=========================================================--
        GRABA INTERFAZ PARA LOS DOCUMENTOS BOLETA Y TICKET BOLETA
        Doc agrupados y que mueven kardex.
       --=========================================================--*/
       select cod_cia into v_cia from pbl_local where cod_local=cCodLocal_in;
       
       insert into INT_VTA_MF nologging
       (
       /*1*/ MANDANTE,COD_LOCAL,DOCUMENTO,
       /*2*/ CORRELATIVO,FECHAEMISION,
       /*3*/ RUCCLIENTE,DES_RAZON_SOCIAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO_REFER,
       /*4*/ NUM_DOCUMENTO_ANU,COD_TIPO_VENTA,
       /*5*/ TIPO_LABORATORIO,TIPO_POSICION,
       /*6*/ COD_PRODUCTO,CANTIDAD,UNIDAD,
       /*7*/ FRACCIONADA,FACTOR,
       /*8*/ MTO_TOTAL_POS,MONEDA,MTO_TOTAL,AJUSTE,
       /*9*/ COD_CLI_SAP,COD_PAGO,IMP_DSCTO,
      /*10*/ FCH_ARCHIVO,FCH_VENTA,USU_CREA,USU_MOD,FECH_CREA,FECH_MOD,COD_GRUPO_CIA)
       select
       /*1*/  C_MANDANTE,cCodLocal_in,v_nNumSecDoc,
       /*2*/  Farma_Utility.COMPLETAR_CON_SIMBOLO(rownum,6,'0','I'),to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd'),
       /*3*/  vRUC_empresa,vRazonSocial,v_TipComp_SAP,v_vNumDocRef,
       /*4*/  v_vNumDocANUL,GET_TIP_VTA_SAP(Det_Doc.tip_ped_vta,cIndConvenio_in),
       /*5*/  DECODE(Det_Doc.IND_PROD_PROPIO,'S','1','N','2'),C_POS_VENTA,
       /*6*/  Det_Doc.cod_serv_conv,to_char(Det_Doc.cant_vend_final,decode(v_cia,MARCA_BTL,'99999999990.000','000000000.00')),C_UNIDAD,
       /*7*/  det_doc.ind_fraccion,DECODE(det_doc.ind_fraccion,'X',decode(v_cia,MARCA_BTL,det_doc.fac_conversion,TO_CHAR(det_doc.fac_conversion,'0000')),'1'),
       /*8*/  case
                  when det_doc.prec_total < 0 then
                   to_char(det_doc.prec_total, '9,999,990.00S')
                  else
                   to_char(det_doc.prec_total, '99,999,990.00')
              end,C_MONEDA_SOLES,
              case
                  when vTotalDocumento < 0 then
                   to_char(vTotalDocumento, '9,999,990.00S')
                  else
                   to_char(vTotalDocumento, '99,999,990.00')
              end,
              to_char(0,'9999,999,990.00')/*redondeo cero de manera temporal*/,
       /*9*/  det_doc.cod_cliente_sap,C_CONDICION_PAGO,to_char(det_doc.dcto_total,'9999999990.00'),
      /*10*/  null,to_date(vFecProceso_in,'dd/mm/yyyy'),'SISTEMAS',null,sysdate,null,cCodGrupoCia_in
     from
            (
             select det.cod_grupo_cia,det.cod_local,det.cod_serv_conv,det.cod_cliente_sap,det.tip_ped_vta,
                    'N' IND_PROD_PROPIO,--CONSTANTE p.ind_prod_propio,
                    1 cant_vend_final,
                    1 fac_conversion,
                    ' ' ind_fraccion,
                   det.prec_total,
                   det.dcto_total,
                   rank() over (order by det.prec_total asc) orden
            from   (
                    select j.cod_grupo_cia,j.cod_local,j.cod_serv_conv,j.cod_cliente_sap,j.tip_ped_vta,
                           sum(j.cant_atendida/j.val_frac) ctd_vendido,
                           --sum(j.new_val_prec_total) prec_total,
                           sum(j.old_val_prec_total) prec_total,
                           sum(j.new_dcto_x_prod) dcto_total
                    from   (
                           select b.cod_grupo_cia,b.cod_local,b.cod_cliente_sap,b.num_comp_pago,b.tip_comp_pago,
                                  GET_COD_SERVICIO_PEDIDO(b.cod_grupo_cia,b.cod_local,b.num_ped_vta) cod_serv_conv,
                                  b.tip_ped_vta,b.cant_atendida,b.val_frac,b.new_val_prec_vta,b.new_dcto_x_prod,
                                  b.new_val_prec_total,b.old_val_prec_total
                           from  TMP_ANULA_DET_PEDIDO_INT_VTA b
                           )j
                    where  j.tip_comp_pago = fSC.Tip_Comp_Pago
                    and    j.num_comp_pago = fSC.Num_Comp_Pago
                    group by j.cod_grupo_cia,j.cod_local,j.cod_serv_conv,j.cod_cliente_sap,j.tip_ped_vta
                   )det
            order by rank() over (order by det.prec_total asc)
            ) Det_Doc;

            select count(1)
            into   nCant
            from   TMP_ANULA_DET_PEDIDO_INT_VTA j
            where  j.tip_comp_pago = fSC.Tip_Comp_Pago
            and    j.num_comp_pago = fSC.Num_Comp_Pago;

            dbms_output.put_line(nCant);
            dbms_output.put_line(fSC.Tip_Comp_Pago||';'||fSC.Num_Comp_Pago);

       --=========================================================--
       -- REGISTRO DEL REDONDEO DE CADA DOCUMENTO
       --=========================================================--
       AUX_GRABA_REDONDEO(cCodGrupoCia_in,cCodLocal_in,v_nNumSecDoc,
                          v_vNumDocRef,v_TipComp_SAP,vTotalDocumento,
                          fSC.Tip_Comp_Pago,fSC.Serie,fSC.Num_Comp_Pago,vFecProceso_in,v_vNumDocANUL,
                          'S','N','N','S',
                           vRUC_empresa ,vRazonSocial
                          );
       -------------------------------------------------------------
       SET_NUN_DOC_SAP_COMP(cCodGrupoCia_in,cCodLocal_in,fSC.Sec_Comp_Pago,v_nNumSecDoc,fSC.Num_Ped_Vta,'S');


    END LOOP;

    close curServAnulados;
  end;
 /* *************************************************************************** */
  PROCEDURE S_SERVICIOS_NOTA_CREDITO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 vFecProceso_in IN VARCHAR2,
                                 cIndConvenio_in IN CHAR)
  AS
    cursor curNCMuevenKardex is
   /*
    select distinct g.tip_comp_pago,g.num_comp_pago,g.serie,g.sec_num_comp,
                    g.num_ped_vta,g.sec_comp_pago,g.nt_num_ped_vta,
                    g.origen_tip_comp_pago,g.origen_num_sec_doc_sap,g.origen_num_comp_pago,
                    SUBSTR(g.origen_num_comp_pago,1,3) Serie_origen
    from   TMP_ANULA_DET_PED_NOTA_CREDITO g
    where  g.tip_comp_pago in (COMP_NC)
    and    g.ind_mueve_kardex = 'N'
    order by g.tip_comp_pago asc,g.serie asc,g.sec_num_comp asc;
   */
  select distinct g.tip_comp_pago,g.num_comp_pago,g.serie,g.sec_num_comp,
                  g.num_ped_vta,g.sec_comp_pago,g.nt_num_ped_vta,
                  g.origen_tip_comp_pago,g.origen_num_sec_doc_sap,g.origen_num_comp_pago,
                  SUBSTR(g.origen_num_comp_pago,1,3) Serie_origen,
                  g.sec_comp_pago_origen
  from   TMP_ANULA_DET_PED_NOTA_CREDITO g
  where  g.tip_comp_pago in (COMP_NC)
  and    g.ind_mueve_kardex = 'N'
  order by g.tip_comp_pago asc,g.serie asc,g.sec_num_comp asc;

    fSC curNCMuevenKardex%rowtype;
 --=====================================================--
    --------------   VARIBLES AUXILIARES -------------------
    v_nNumSecDoc int_vta_mf.documento%TYPE;
    v_vNumDocRef int_vta_mf.num_documento_refer%TYPE;
    v_vNumDocANUL int_vta_mf.num_documento_anu%TYPE;
    v_TipComp_SAP varchar2(10);
    vRUC_empresa int_vta_mf.ruccliente%type;
    vRazonSocial int_vta_mf.des_razon_social%type;
    --=====================================================--
    vTotalDocumento number(8,2);
    v_cia char(3);

  BEGIN

    open curNCMuevenKardex;
    LOOP
      FETCH curNCMuevenKardex INTO fSC;
      EXIT WHEN curNCMuevenKardex%NOTFOUND;

       ---
       v_TipComp_SAP := TIP_COMP_SAP_SERVICIO;
       ---
       v_nNumSecDoc:= GET_SEC_DOC(cCodGrupoCia_in,cCodLocal_in);
       ------------------------------------------------------
       SELECT DISTINCT NUM_DOC_REF
       into   v_vNumDocRef
       FROM   (
              select e.cod_local,e.num_sec_doc,e.num_doc_ref
              from  INT_VENTA e
              where e.FEC_PROCESO <= to_date('01/06/2013','dd/mm/yyyy') - 1/24/60/60
              --where e.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
              union
              select a.cod_local,trim(a.documento)*1,a.num_documento_refer
              from   int_vta_mf a
              union
              select null,null,null
              from  dual
              ) T_INT
       WHERE  T_INT.NUM_SEC_DOC = fSC.Origen_Num_Sec_Doc_Sap
       AND    T_INT.COD_LOCAL = cCodLocal_in;
       -------------------------------------------------------
       --fSC.Origen_Num_Sec_Doc_Sap
       v_vNumDocANUL := GET_NUM_DOC_REF(cCodGrupoCia_in,cCodLocal_in,fSC.Origen_Tip_Comp_Pago,fSC.Serie_origen,
                                      fSC.Origen_Num_Comp_Pago,fSC.Origen_Num_Comp_Pago,v_TipComp_SAP,'S');


      --1.- calcula el total del documento de ese grupo

      select round(sum(det.prec_total - det.dcto_total),2)
      into   vTotalDocumento
      from   (
              select j.cod_grupo_cia,j.cod_local,j.cod_prod,
                     sum(j.cant_atendida/j.val_frac) ctd_vendido,
                     ----sum(j.new_val_prec_total) prec_total,
                     sum(j.old_val_prec_total) prec_total,
                     sum(j.new_dcto_x_prod) dcto_total
              from   TMP_ANULA_DET_PED_NOTA_CREDITO j
              where  j.tip_comp_pago = fSC.Tip_Comp_Pago
              and    j.num_comp_pago = fSC.Num_Comp_Pago
              and    j.origen_tip_comp_pago = fSC.Origen_Tip_Comp_Pago
              and    j.origen_num_sec_doc_sap = fSC.Origen_Num_Sec_Doc_Sap
              and    j.origen_num_comp_pago = fSC.Origen_Num_Comp_Pago
              group by j.cod_grupo_cia,j.cod_local,j.cod_prod
             )det;

      IF fSC.Tip_Comp_Pago = COMP_FACTURA then
          select  trim(C.RUC_CLI_PED_VTA),AUX_REEMPLAZAR_CHAR(SUBSTR(C.NOM_CLI_PED_VTA,1,40))
          into    vRUC_empresa,vRazonSocial
          from    vta_pedido_vta_cab c
          where   c.cod_grupo_cia = cCodGrupoCia_in
          and     c.cod_local = cCodLocal_in
          and     c.num_ped_vta = fSC.Num_Ped_Vta
          /*and     c.ind_conv_btl_mf= cIndConvenio_in
          and     c.ind_ped_convenio= cIndConvenio_in*/;
      ELSE
          vRUC_empresa := '';
          vRazonSocial := '';
      END IF;

       /*--=========================================================--
        GRABA INTERFAZ PARA LOS DOCUMENTOS BOLETA Y TICKET BOLETA
        Doc agrupados y que mueven kardex.
       --=========================================================--*/
       select cod_cia into v_cia from pbl_local where cod_local=cCodLocal_in;
       
       insert into INT_VTA_MF nologging
       (
       /*1*/ MANDANTE,COD_LOCAL,DOCUMENTO,
       /*2*/ CORRELATIVO,FECHAEMISION,
       /*3*/ RUCCLIENTE,DES_RAZON_SOCIAL,COD_TIPO_DOCUMENTO,NUM_DOCUMENTO_REFER,
       /*4*/ NUM_DOCUMENTO_ANU,COD_TIPO_VENTA,
       /*5*/ TIPO_LABORATORIO,TIPO_POSICION,
       /*6*/ COD_PRODUCTO,CANTIDAD,UNIDAD,
       /*7*/ FRACCIONADA,FACTOR,
       /*8*/ MTO_TOTAL_POS,MONEDA,MTO_TOTAL,AJUSTE,
       /*9*/ COD_CLI_SAP,COD_PAGO,IMP_DSCTO,
      /*10*/ FCH_ARCHIVO,FCH_VENTA,USU_CREA,USU_MOD,FECH_CREA,FECH_MOD,COD_GRUPO_CIA)
       select
       /*1*/  C_MANDANTE,cCodLocal_in,v_nNumSecDoc,
       /*2*/  Farma_Utility.COMPLETAR_CON_SIMBOLO(rownum,6,'0','I'),to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd'),
       /*3*/  vRUC_empresa,vRazonSocial,v_TipComp_SAP,v_vNumDocRef,
       /*4*/  v_vNumDocANUL,GET_TIP_VTA_SAP(Det_Doc.tip_ped_vta,cIndConvenio_in),
       /*5*/  DECODE(Det_Doc.IND_PROD_PROPIO,'S','1','N','2'),C_POS_VENTA,
       /*6*/  Det_Doc.cod_serv_conv,to_char(Det_Doc.cant_vend_final,decode(v_cia,MARCA_BTL,'99999999990.000','000000000.00')),C_UNIDAD,
       /*7*/  det_doc.ind_fraccion,DECODE(det_doc.ind_fraccion,'X',decode(v_cia,MARCA_BTL,det_doc.fac_conversion,TO_CHAR(det_doc.fac_conversion,'0000')),'1'),
       /*8*/  case
                  when det_doc.prec_total < 0 then
                   to_char(det_doc.prec_total, '9,999,990.00S')
                  else
                   to_char(det_doc.prec_total, '99,999,990.00')
              end,C_MONEDA_SOLES,
              case
                  when vTotalDocumento < 0 then
                   to_char(vTotalDocumento, '9,999,990.00S')
                  else
                   to_char(vTotalDocumento, '99,999,990.00')
              end,
              to_char(0,'9999,999,990.00')/*redondeo cero de manera temporal*/,
       /*9*/  det_doc.cod_cliente_sap,C_CONDICION_PAGO,to_char(det_doc.dcto_total,'9999999990.00'),
      /*10*/  null,to_date(vFecProceso_in,'dd/mm/yyyy'),'SISTEMAS',null,sysdate,null,cCodGrupoCia_in
     from
            (
             select det.cod_grupo_cia,det.cod_local,det.cod_serv_conv,det.cod_cliente_sap,det.tip_ped_vta,
                    'N' IND_PROD_PROPIO,--CONSTANTE p.ind_prod_propio,
                    1 cant_vend_final,
                    1 fac_conversion,
                    ' ' ind_fraccion,
                   det.prec_total,
                   det.dcto_total,
                   rank() over (order by det.prec_total asc) orden
            from   (
                    select j.cod_grupo_cia,j.cod_local,j.cod_serv_conv,j.cod_cliente_sap,j.tip_ped_vta,
                           sum(j.cant_atendida/j.val_frac) ctd_vendido,
                           --sum(j.new_val_prec_total) prec_total,
                            sum(j.old_val_prec_total) prec_total,
                           sum(j.new_dcto_x_prod) dcto_total
                    from   --TMP_ANULA_DET_PED_NOTA_CREDITO j
                           (
                           select b.cod_grupo_cia,b.cod_local,b.cod_cliente_sap,b.num_comp_pago,b.tip_comp_pago,
                                  GET_COD_SERVICIO_PEDIDO(b.cod_grupo_cia,b.cod_local,b.num_ped_vta) cod_serv_conv,
                                  b.tip_ped_vta,b.cant_atendida,b.val_frac,b.new_val_prec_vta,b.new_dcto_x_prod,
                                  b.new_val_prec_total,b.old_val_prec_total
                           from  TMP_ANULA_DET_PED_NOTA_CREDITO b
                           )j
                    where  j.tip_comp_pago = fSC.Tip_Comp_Pago
                    and    j.num_comp_pago = fSC.Num_Comp_Pago
                    group by j.cod_grupo_cia,j.cod_local,j.cod_serv_conv,j.cod_cliente_sap,j.tip_ped_vta
                   )det
            order by rank() over (order by det.prec_total asc)
            ) Det_Doc;

       --=========================================================--
       -- REGISTRO DEL REDONDEO DE CADA DOCUMENTO
       --=========================================================--
       /*
       AUX_GRABA_REDONDEO(cCodGrupoCia_in,cCodLocal_in,v_nNumSecDoc,
                          v_vNumDocRef,v_TipComp_SAP,vTotalDocumento,
                          fSC.Tip_Comp_Pago,fSC.Serie,fSC.Num_Comp_Pago,vFecProceso_in,v_vNumDocANUL,
                          'N','N','N','S',
                           vRUC_empresa,vRazonSocial
                          );*/

       AUX_GRABA_REDONDEO(cCodGrupoCia_in,cCodLocal_in,v_nNumSecDoc,
                          v_vNumDocRef,v_TipComp_SAP,vTotalDocumento,
                          fSC.Tip_Comp_Pago,fSC.Serie,fSC.Num_Comp_Pago,vFecProceso_in,v_vNumDocANUL,
                          'N',fSC.Sec_Comp_Pago_Origen,fSC.Num_Ped_Vta,
                          'N',
                           nvl(vRUC_empresa,''),nvl(vRazonSocial,'')
                          );

       -------------------------------------------------------------
       SET_NUN_DOC_SAP_COMP(cCodGrupoCia_in,cCodLocal_in,fSC.Sec_Comp_Pago,v_nNumSecDoc,fSC.Num_Ped_Vta,'S');


    END LOOP;
    close curNCMuevenKardex;
  end;
 /* *************************************************************************** */
PROCEDURE ENVIA_CORREO(cCodGrupoCia_in      IN CHAR,
                       cCodLocal_in         IN CHAR,
                       vAsunto_in IN VARCHAR2,
                       vTitulo_in IN VARCHAR2,
                       vMensaje_in IN VARCHAR2)
  AS

    ReceiverAddress VARCHAR2(30) := 'DUBILLUZ';--FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTERFACE;
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(32767);

    v_vDescLocal VARCHAR2(120);
  BEGIN
  null;
    --DESCRIPCION DE LOCAL
    /*SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in ||
                          '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                          ReceiverAddress,
                          vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                          vTitulo_in,--'EXITO',
                          mesg_body,
                          CCReceiverAddress,
                          FARMA_EMAIL.GET_EMAIL_SERVER,
                          true);*/

  END;
  /* ************************************************************************** */
  FUNCTION V_PREVIAS_INT_VTA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              vFecProceso_in IN VARCHAR2)
  RETURN CHAR
  IS
    v_eControlDia EXCEPTION;
    v_eControlFecha EXCEPTION;
    v_eControlFechaComp EXCEPTION;
    v_nCant INTEGER;

    CURSOR curFechaComp IS
    SELECT TRUNC(FEC_PED_VTA)
    FROM pbl_local t1, vta_pedido_vta_cab t2
    WHERE t1.cod_grupo_cia = cCodGrupoCia_in
              AND t1.cod_local = cCodLocal_in
              AND t1.cod_grupo_cia = t2.cod_grupo_cia
              AND t1.cod_local = t2.cod_local
              AND EST_PED_VTA = 'C'
     and FEC_PED_VTA >= (
                         select nvl( (select t.fech_migra_mfa
                                      from   PBL_LOCAL_MIGRA t
                                      where  t.cod_local = cCodLocal_in),
                                			to_date('01/01/2012','dd/mm/yyyy') ) from dual
                         )
    GROUP BY TRUNC(FEC_PED_VTA)
    HAVING SUM(VAL_NETO_PED_VTA) > 0
    ORDER BY 1 ASC;

    v_dFechaComp DATE;

    vResultado char(1) := 'N';
  begin
     -- VER LO QUE DEMORA EN MATRIZ
     -- 17.06.2013

    IF TO_CHAR(SYSDATE,'dd/MM/yyyy') = vFecProceso_in THEN --VALIDA SI SE EJECUTA EL MISMO DIA
      RAISE v_eControlDia;
    ELSIF TO_DATE(vFecProceso_in,'dd/MM/yyyy') > SYSDATE THEN
      RAISE v_eControlDia;
    ELSE
      SELECT COUNT(*) INTO v_nCant
      FROM (
           select E.COD_GRUPO_CIA,e.cod_local,e.num_sec_doc
           from  INT_VENTA e
           where  e.FEC_PROCESO <= to_date('01/06/2013','dd/mm/yyyy') - 1/24/60/60
           --where  e.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
           union
           select A.COD_GRUPO_CIA,a.cod_local,trim(a.documento)*1
           from   int_vta_mf a
           union
           select null,null,null
           from   dual
           )
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

      IF v_nCant > 0 THEN

      -- 2011-12-20 JOLIVA: VERIFICA QUE EL ÚLTIMO DÍA DE VENTA ANTERIOR A LA FECHA DE PROCESO TENGA INTERFASE DE VENTA GENERADA
         SELECT COUNT(*)
         INTO v_nCant
         FROM (
                 select E.COD_GRUPO_CIA,e.cod_local,e.num_sec_doc,E.FEC_PROCESO
                 from  INT_VENTA e
                 where e.FEC_PROCESO <= to_date('01/06/2013','dd/mm/yyyy') - 1/24/60/60
                 --where  e.fec_proceso <= nFch_Ini_New_int_vta + 1 - 1/24/60/60
                 union
                 select A.COD_GRUPO_CIA,a.cod_local,trim(a.documento)*1,A.FCH_VENTA AS "FEC_PROCESO"
                 from   int_vta_mf a
                 union
                 select null,null,null,null
                 from   dual
               ) V
         WHERE V.COD_GRUPO_CIA = cCodGrupoCia_in
           AND V.COD_LOCAL = cCodLocal_in
           AND V.FEC_PROCESO = (
                                 SELECT TRUNC(MAX(FEC_PED_VTA))
                                 FROM VTA_PEDIDO_VTA_CAB
                                 WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND COD_LOCAL = cCodLocal_in
                                 AND EST_PED_VTA = 'C'
                                 AND FEC_PED_VTA < TO_DATE(vFecProceso_in,'dd/MM/yyyy')
                                 and FEC_PED_VTA >= (
                                                    select nvl( (select t.fech_migra_mfa
                                                                  from   PBL_LOCAL_MIGRA t
                                                                  where  t.cod_local = cCodLocal_in),
                                                    			to_date('01/01/2012','dd/mm/yyyy') ) from dual
                                                    )
                               );

          IF v_nCant = 0 THEN
              DBMS_OUTPUT.PUT_LINE('EXISTEN FECHAS DE GENERACION DE INTERFASE PENDIENTES DE PROCESO');
              RAISE v_eControlFecha;
          END IF;

      ELSE  --VALIDA FECHA DE APERTURA DEL LOCAL
        OPEN curFechaComp;
        FETCH curFechaComp INTO v_dFechaComp;
        CLOSE curFechaComp;
        IF TO_CHAR(v_dFechaComp,'dd/MM/yyyy') <> vFecProceso_in THEN
          RAISE v_eControlFechaComp;
        END IF;
      END IF;

      vResultado := 'S';

   END IF;

   --vResultado := 'S';
   if PERMITE_GEN_INT_VTA(cCodGrupoCia_in,cCodLocal_in,vFecProceso_in) = true then
     vResultado := 'S';
   else
     vResultado := 'N';
   end if;

   return  vResultado;

   EXCEPTION
   WHEN v_eControlFechaComp THEN
     ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('DEBE GENERAR LA INTERFACE A PARTIR DE LA FECHA DE APERTURA DEL LOCAL:'||TO_CHAR(v_dFechaComp,'dd/MM/yyyy'));
      --MAIL DE ERROR DE INTERFACE VENTAS
      ENVIA_CORREO(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||'DEBE GENERAR LA INTERFACE A PARTIR DE LA FECHA DE APERTURA DEL LOCAL:'||TO_CHAR(v_dFechaComp,'dd/MM/yyyy')||'<B>');
      return  vResultado;
    WHEN v_eControlDia THEN
       ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('NO PUEDE GENERAR LA INTERFACE EL MISMO DIA');
      --MAIL DE ERROR DE INTERFACE VENTAS
      ENVIA_CORREO(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||'NO PUEDE GENERAR LA INTERFACE EL MISMO DIA.'||'<B>');
      return  vResultado;
   WHEN v_eControlFecha THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('LA INTERFACE TIENE FECHAS PENDIENTES DE PROCESO. VERIFIQUE');
      --MAIL DE ERROR DE INTERFACE VENTAS
      ENVIA_CORREO(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||'LA INTERFACE TIENE FECHAS PENDIENTES DE PROCESO.'||'<B>');
      return  vResultado;
  end;
  /* ************************************************************************** */
  PROCEDURE V_MONTO_VENTAS(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                           vFecProceso_in IN VARCHAR2)
  AS
        v_mto_total_vta number := 0;
        v_mto_total_int number := 0;

        v_mto_t_emitidos number := 0;
        v_mto_t_anulados number := 0;
        datoMalGrabado number := 0;
        nVtaTOTAL_DIA number := 0;
        v_mto_total_vta_GUIAS number:=0;
  BEGIN
    corrigeDifRedondeo(cCodLocal_in,vFecProceso_in);

/*SELECT SUM(cp.val_neto_comp_pago + cp.val_redondeo_comp_pago)
  INTO v_mto_t_emitidos
    FROM  vta_pedido_vta_cab c,
          Vta_Comp_Pago cp
    WHERE c.cod_grupo_cia = cCodGrupoCia_in
    AND   c.cod_local = cCodLocal_in
    AND   c.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
    AND   c.EST_PED_VTA in ('C','S')
    and  cp.tip_comp_pago != '03'
    and   c.cod_grupo_cia = cp.cod_grupo_cia
    and   c.cod_local = cp.cod_local
    and   c.num_ped_vta = cp.num_ped_vta;

    SELECT SUM((cp.val_neto_comp_pago + cp.val_redondeo_comp_pago)*-1)
    INTO v_mto_t_anulados
    FROM  Vta_Comp_Pago cp
    WHERE (CP.COD_GRUPO_CIA,CP.COD_LOCAL,CP.NUM_PED_VTA)
          IN (
              SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,C.NUM_PED_VTA
              FROM   vta_pedido_vta_cab c
              WHERE  c.cod_grupo_cia = cCodGrupoCia_in
              AND   c.cod_local = cCodLocal_in
              AND   c.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                          TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
              AND   c.EST_PED_VTA in ('C','S')
             )
    and  cp.tip_comp_pago != '03'
    and   cp.fec_anul_comp_pago BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60;*/
    begin
    SELECT SUM(C.VAL_NETO_PED_VTA)
    into   nVtaTOTAL_DIA
    FROM  vta_pedido_vta_cab c
    WHERE c.cod_grupo_cia = cCodGrupoCia_in
    AND   c.cod_local = cCodLocal_in
    AND   c.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
    AND   c.EST_PED_VTA in ('C','S');
    exception
    when others then
      nVtaTOTAL_DIA := 0;
    end;

   -----
   --v_mto_total_vta := v_mto_t_emitidos + v_mto_t_anulados;
      begin
      SELECT
            SUM(VAL_NETO_COMP_PAGO)
      INTO  v_mto_total_vta
      FROM
          (
              SELECT
                    TRUNC(C.FEC_PED_VTA) FECHA,
                    CP.TIP_COMP_PAGO,
                    CP.COD_CLIENTE_SAP,
                    CP.NUM_COMP_PAGO,
                    (CP.VAL_NETO_COMP_PAGO+cp.val_redondeo_comp_pago) AS "VAL_NETO_COMP_PAGO",
                    COUNT(*)
              FROM VTA_PEDIDO_VTA_CAB C,
                   VTA_COMP_PAGO CP
              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND C.COD_LOCAL =cCodLocal_in
                AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                      TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
                AND C.EST_PED_VTA = 'C'
                AND CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                AND CP.COD_LOCAL = C.COD_LOCAL
                AND CP.NUM_PED_VTA = C.NUM_PED_VTA
                AND CP.TIP_COMP_PAGO != '03'
                AND C.VAL_NETO_PED_VTA >  0
                GROUP BY
                    TRUNC(C.FEC_PED_VTA),
                    CP.TIP_COMP_PAGO,
                    CP.COD_CLIENTE_SAP,
                    CP.NUM_COMP_PAGO,
                    CP.VAL_NETO_COMP_PAGO+cp.val_redondeo_comp_pago

              UNION ALL

              SELECT
                    TRUNC(C.FEC_PED_VTA),
                    CP.TIP_COMP_PAGO,
                    CP.COD_CLIENTE_SAP,
                    CP.NUM_COMP_PAGO,
                    (CP.VAL_NETO_COMP_PAGO+cp.val_redondeo_comp_pago)*-1  AS "VAL_NETO_COMP_PAGO",
                    COUNT(*)
              FROM VTA_PEDIDO_VTA_CAB C,
                   VTA_COMP_PAGO CP
              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND C.COD_LOCAL =cCodLocal_in
                AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                      TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
                AND C.EST_PED_VTA = 'C'
                AND CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                AND CP.COD_LOCAL = C.COD_LOCAL
                --AND CP.NUM_PEDIDO_ANUL = C.NUM_PED_VTA
                AND CP.Num_Ped_Vta = C.Num_Ped_Vta_Origen
                AND CP.TIP_COMP_PAGO != '03'
              GROUP BY
                    TRUNC(C.FEC_PED_VTA),
                    CP.TIP_COMP_PAGO,
                    CP.COD_CLIENTE_SAP,
                    CP.NUM_COMP_PAGO,
                    CP.VAL_NETO_COMP_PAGO+cp.val_redondeo_comp_pago
          )
      GROUP BY FECHA;
      exception
      when others then
      v_mto_total_vta := 0;

      end;

      BEGIN
      SELECT
            SUM(VAL_NETO_COMP_PAGO)
      INTO  v_mto_total_vta_GUIAS
      FROM
          (
              SELECT
                    TRUNC(C.FEC_PED_VTA) FECHA,
                    CP.TIP_COMP_PAGO,
                    CP.COD_CLIENTE_SAP,
                    CP.NUM_COMP_PAGO,
                    (CP.VAL_NETO_COMP_PAGO+cp.val_redondeo_comp_pago) AS "VAL_NETO_COMP_PAGO",
                    COUNT(*)
              FROM VTA_PEDIDO_VTA_CAB C,
                   VTA_COMP_PAGO CP
              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND C.COD_LOCAL =cCodLocal_in
                AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                      TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
                AND C.EST_PED_VTA = 'C'
                AND CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                AND CP.COD_LOCAL = C.COD_LOCAL
                AND CP.NUM_PED_VTA = C.NUM_PED_VTA
                AND CP.TIP_COMP_PAGO = '03'
              GROUP BY
                    TRUNC(C.FEC_PED_VTA),
                    CP.TIP_COMP_PAGO,
                    CP.COD_CLIENTE_SAP,
                    CP.NUM_COMP_PAGO,
                    CP.VAL_NETO_COMP_PAGO+cp.val_redondeo_comp_pago

              UNION ALL

              SELECT
                    TRUNC(C.FEC_PED_VTA),
                    CP.TIP_COMP_PAGO,
                    CP.COD_CLIENTE_SAP,
                    CP.NUM_COMP_PAGO,
                    (CP.VAL_NETO_COMP_PAGO+cp.val_redondeo_comp_pago)*-1  AS "VAL_NETO_COMP_PAGO",
                    COUNT(*)
              FROM VTA_PEDIDO_VTA_CAB C,
                   VTA_COMP_PAGO CP
              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND C.COD_LOCAL =cCodLocal_in
                AND C.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                      TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
                AND C.EST_PED_VTA = 'C'
                AND CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                AND CP.COD_LOCAL = C.COD_LOCAL
                --AND CP.NUM_PEDIDO_ANUL = C.NUM_PED_VTA
                AND CP.Num_Ped_Vta = C.Num_Ped_Vta_Origen
                AND CP.TIP_COMP_PAGO = '03'
              GROUP BY
                    TRUNC(C.FEC_PED_VTA),
                    CP.TIP_COMP_PAGO,
                    CP.COD_CLIENTE_SAP,
                    CP.NUM_COMP_PAGO,
                    CP.VAL_NETO_COMP_PAGO+cp.val_redondeo_comp_pago
          )
      GROUP BY FECHA;
      EXCEPTION
       WHEN OTHERS THEN
         v_mto_total_vta_GUIAS := 0;
      END;

      select ROUND(sum((case
                             when instr(mto_total_pos, '-') <> 0 then
                              to_number(mto_total_pos, '9,999,990.00S')
                             else
                              to_number(mto_total_pos, '99,999,990.00')
                         end) * (case
                             when cod_tipo_documento in ('1', '2', '7', '8', '13') then
                              1
                             else
                              -1
                         end) - (to_number(imp_dscto, '9999999990.00') *
                                     (case
                                          when cod_tipo_documento in ('1', '2', '7', '8', '13') then
                                           1
                                          else
                                           -1
                                      end))),2)
          into v_mto_total_int
          from int_vta_mf a
         where COD_LOCAL     = cCodLocal_in
           and COD_GRUPO_CIA = cCodGrupoCia_in
           and FECHAEMISION  = to_char(TO_DATE(vFecProceso_in,'dd/MM/yyyy'), 'YYYYMMDD');

select count(1)
into datoMalGrabado
  from (select a.documento,
               a.num_documento_refer,
               sum((case
                     when instr(mto_total_pos, '-') <> 0 then
                      to_number(mto_total_pos, '9,999,990.00S')
                     else
                      to_number(mto_total_pos, '99,999,990.00')
                   end) - (case
                     when instr(IMP_DSCTO, '-') <> 0 then
                      to_number(IMP_DSCTO, '9,999,990.00S')
                     else
                      to_number(IMP_DSCTO, '99,999,990.00')
                   end)

                   ) MONTO_DET,
               (case
                 when instr(a.mto_total, '-') <> 0 then
                  to_number(a.mto_total, '9,999,990.00S')
                 else
                  to_number(a.mto_total, '99,999,990.00')
               end) Monto_TOTAL
          from int_vta_mf a
         where COD_LOCAL = cCodLocal_in
           and COD_GRUPO_CIA = cCodGrupoCia_in
           and FECHAEMISION =
               to_char(TO_DATE(vFecProceso_in, 'dd/MM/yyyy'), 'YYYYMMDD')
           and a.tipo_posicion = 1
         group by a.documento,a.num_documento_refer,
                  (case
                    when instr(a.mto_total, '-') <> 0 then
                     to_number(a.mto_total, '9,999,990.00S')
                    else
                     to_number(a.mto_total, '99,999,990.00')
                  end)) TT
 where abs(tt.monto_det - tt.monto_total) > 0;

    --DBMS_OUTPUT.PUT_LINE('LOS MONTOS DE VENTAS: FECHA= ' || vFecProceso_in || ' VENTAS='||v_nMontoVentas||':: INTERFAZ='||v_nMontoInterfaz);
    IF abs(v_mto_total_vta - v_mto_total_int) >= 1 and
        ( v_mto_total_vta <> v_mto_total_int or datoMalGrabado > 0
        or v_mto_total_vta <> nVtaTOTAL_DIA
        ) THEN

        if v_mto_total_vta_GUIAS + v_mto_total_int = nVtaTOTAL_DIA then
          DBMS_OUTPUT.PUT_LINE('DIF_ES DE GUIAS');
          DBMS_OUTPUT.PUT_LINE('LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= ' || vFecProceso_in || ' VENTAS='||v_mto_total_vta||':: INTERFAZ='||v_mto_total_int
      || '  datoMalGrabado > '||datoMalGrabado || ' nVtaTOTAL_DIA >> '||nVtaTOTAL_DIA);
        else

      DBMS_OUTPUT.PUT_LINE('LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= ' || vFecProceso_in || ' VENTAS='||v_mto_total_vta||':: INTERFAZ='||v_mto_total_int
      || '  datoMalGrabado > '||datoMalGrabado || ' nVtaTOTAL_DIA >> '||nVtaTOTAL_DIA);
      RAISE_APPLICATION_ERROR(-20001,'LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= '
      || vFecProceso_in ||
      ' VENTAS='||v_mto_total_vta||
      ':: INTERFAZ='||v_mto_total_int || '  datoMalGrabado > '||datoMalGrabado
      || ' nVtaTOTAL_DIA >> '||nVtaTOTAL_DIA
      );
      end if;
    ELSE
       IF v_mto_total_vta <> v_mto_total_int or datoMalGrabado > 0
          or v_mto_total_vta <> nVtaTOTAL_DIA
         THEN
               if v_mto_total_vta_GUIAS + v_mto_total_int = nVtaTOTAL_DIA then
          DBMS_OUTPUT.PUT_LINE('DIF_ES DE GUIAS');
          DBMS_OUTPUT.PUT_LINE('LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= ' || vFecProceso_in || ' VENTAS='||v_mto_total_vta||':: INTERFAZ='||v_mto_total_int
      || '  datoMalGrabado > '||datoMalGrabado || ' nVtaTOTAL_DIA >> '||nVtaTOTAL_DIA);
        else

        mf_int_vta.grabaLog(cCodLocal_in,vFecProceso_in,
  'LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= ' || vFecProceso_in ||
        ' VENTAS='||v_mto_total_vta||':: INTERFAZ='||v_mto_total_int|| '  datoMalGrabado > '||datoMalGrabado
        || ' nVtaTOTAL_DIA >> '||nVtaTOTAL_DIA
        );
        end if;
       END IF;
    END IF;
DBMS_OUTPUT.PUT_LINE('LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= ' || vFecProceso_in || ' VENTAS='||v_mto_total_vta||':: INTERFAZ='||v_mto_total_int
      || '  datoMalGrabado > '||datoMalGrabado);

      --------------------------------------------
      if datoMalGrabado > 0 then
         DBMS_OUTPUT.PUT_LINE('ERROR DE INCONS DE DOCUMENTO >>> datoMalGrabado>> '||datoMalGrabado);
        RAISE_APPLICATION_ERROR(-20001,'LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= '
        || vFecProceso_in ||
        ' VENTAS='||v_mto_total_vta||
        ':: INTERFAZ='||v_mto_total_int || '  datoMalGrabado > '||datoMalGrabado
        || ' nVtaTOTAL_DIA >> '||nVtaTOTAL_DIA
        );
      end if;
      --------------------------------------------

  END;
  /* ************************************************************************** */
 PROCEDURE GENERA_ARCHIVO(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in IN CHAR,
                          vFecProceso_in IN VARCHAR2)
  AS
    ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
    v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';--'INTERFASE_VTA';

    CURSOR curResumen IS
    select rpad(nvl(r_cur_info.mandante, ' '), 3, ' ') ||
                              rpad(nvl(r_cur_info.cod_local, ' '), 3, ' ') ||
                              lpad(nvl(r_cur_info.documento, ' '), 10, '0') ||
                              rpad(nvl(r_cur_info.correlativo, ' '), 6, ' ') ||
                              rpad(nvl(r_cur_info.fechaemision, ' '), 8, ' ') ||
                              rpad(nvl(r_cur_info.ruccliente, ' '), 16, ' ') ||
                              rpad(nvl(r_cur_info.des_razon_social, ' '), 40, ' ') ||
                              rpad(nvl(r_cur_info.cod_tipo_documento, ' '), 2, ' ') ||
                              rpad(nvl(r_cur_info.num_documento_refer, ' '), 25, ' ') ||
                              rpad(nvl(r_cur_info.num_documento_anu, ' '), 16, ' ') ||
                              nvl(r_cur_info.cod_tipo_venta, ' ') ||
                              nvl(r_cur_info.tipo_laboratorio, ' ') ||
                              nvl(r_cur_info.tipo_posicion, ' ') ||
                              rpad(nvl(r_cur_info.cod_producto, ' '), 18, ' ') ||
                              lpad(nvl(r_cur_info.cantidad, ' '), 19, ' ') ||
                              rpad(nvl(r_cur_info.unidad, ' '), 3, ' ') ||
                              nvl(r_cur_info.fraccionada, ' ') ||
                              lpad(nvl(r_cur_info.factor, ' '), 5, ' ') ||
                              lpad(nvl(r_cur_info.mto_total_pos, ' '), 14, ' ') ||
                              rpad(nvl(r_cur_info.moneda, ' '), 5, ' ') ||
                              lpad(nvl(r_cur_info.mto_total, ' '), 16, ' ') ||
                              lpad(nvl(r_cur_info.ajuste, ' '), 16, ' ') ||
                              lpad(nvl(trim(r_cur_info.cod_cli_sap), '0'), 10, '0') ||
                              lpad(nvl(r_cur_info.cod_pago, ' '), 4, ' ') ||
                              lpad(nvl(r_cur_info.imp_dscto, ' '), 16, ' ')
                              ||
                              ' '|| --  EN TODAS VACIO xq DEBE DE IMPRIMIR PARA TODO EN X
                              --lpad(nvl(replace(r_cur_info.serie_comprobante,'-',''), ' '), 20, ' ')
                               rpad(nvl(replace(replace(r_cur_info.serie_comprobante,'-',''),' ',''), ' '), 20, ' ')
                              RESUMEN
    from   INT_VTA_MF r_cur_info
    where COD_GRUPO_CIA = cCodGrupoCia_in
    and   COD_LOCAL = cCodLocal_in
    and   r_cur_info.fechaemision = to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd')
    --order by MANDANTE,COD_LOCAL,DOCUMENTO,CORRELATIVO;
    order by MANDANTE,COD_LOCAL,DOCUMENTO,CORRELATIVO desc; 

    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;
    ctdExiste number;
    cCodCia  CHAR(3);
    ctdExisteTotal number;
    ctdExisteNegativo number;
  BEGIN
    EXECUTE IMMEDIATE 'ALTER SESSION SET NLS_DATE_FORMAT = ''DD/MM/YYYY HH24:MI:SS'' ';
    EXECUTE IMMEDIATE 'ALTER SESSION SET NLS_NUMERIC_CHARACTERS = ''.,'' ';

    SELECT COUNT(*) INTO v_nCant
    FROM int_vta_mf a
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND fch_venta = to_date(vFecProceso_in,'dd/mm/yyyy');

    -- si no existe CLIENTE SAP
    -- DEL PERSONAL
    select count(1)
    into   ctdExiste
    from   int_vta_mf i
    where  i.cod_cli_sap like '%SIN_CCLIE%'
    and    COD_GRUPO_CIA  = cCodGrupoCia_in
    and    COD_LOCAL      = cCodLocal_in
    and    fechaemision = to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd');

    if ctdExiste > 0 then
        grabaLog(cCodLocal_in,vFecProceso_in,'ALERTA ARCHIVO SIN CODIGO CLIE SAP REVISAR >> ');
    end if;


    select count(1)
    into   ctdExisteTotal
    from   int_vta_mf i
    where  MTO_TOTAL IS NULL
    and    COD_GRUPO_CIA  = cCodGrupoCia_in
    and    COD_LOCAL      = cCodLocal_in
    and    fechaemision = to_char(to_date(vFecProceso_in,'dd/mm/yyyy'),'yyyymmdd');

    if ctdExisteTotal > 0 then
        grabaLog(cCodLocal_in,vFecProceso_in,'Monto Total de Documento no puede ser nulo');
    end if;



    select count(1)
    into ctdExisteNegativo
    from   int_vta_mf i
    where COD_GRUPO_CIA  = cCodGrupoCia_in
    and   COD_LOCAL      = cCodLocal_in
    and   fechaemision = to_char(to_date(vFecProceso_in ,'dd/mm/yyyy'),'yyyymmdd')
    and tipo_posicion=1
    and  (cast(decode(instr(trim(MTO_TOTAL),'-'),0,replace(trim(MTO_TOTAL),',','') ,replace(trim(MTO_TOTAL),'-','')*-1) as number)<0
    or cast(decode(instr(trim(MTO_TOTAL_POS),'-'),0,replace(trim(MTO_TOTAL_POS),',','') ,replace(trim(MTO_TOTAL_POS),'-','')*-1) as number)<0);


    if ctdExisteNegativo > 0 then
        grabaLog(cCodLocal_in,vFecProceso_in,'Monto Total o Monto Total Pos no puede ser negativo');
    end if;


    SELECT cod_cia into cCodCia
    FROM Pbl_Local WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND COD_lOCAL=cCodLocal_in;

    IF v_nCant > 0 and ctdExiste = 0 and ctdExisteTotal=0 and ctdExisteNegativo=0 THEN

        IF cCodCia=MARCA_MIFARMA THEN
           v_vNombreArchivo := 'VTAMIFARMA1170'||cCodLocal_in||TO_CHAR(TO_DATE(vFecProceso_in,'dd/MM/yyyy'),'yyyyMMdd')||'.TXT';
        ELSE
            IF cCodCia=MARCA_FASA THEN
               v_vNombreArchivo := 'VTAMIFARMA1177'||cCodLocal_in||TO_CHAR(TO_DATE(vFecProceso_in,'dd/MM/yyyy'),'yyyyMMdd')||'.TXT';
            ELSE
                v_vNombreArchivo := 'VTAMIFARMA1174'||cCodLocal_in||TO_CHAR(TO_DATE(vFecProceso_in,'dd/MM/yyyy'),'yyyyMMdd')||'.TXT';
            END IF;
        END IF;
        DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));
        ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'INICIO');
  
        FOR v_rCurResumen IN curResumen
            LOOP
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
        END LOOP;
     
        --UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'FIN');
        UTL_FILE.PUT(ARCHIVO_TEXTO,'FIN');
        UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
    else
      if ctdExiste<>0 then
         RAISE_APPLICATION_ERROR(-20020,'Dia VTA sin SIN_CCLIE');
      else
          if ctdExisteTotal <>0 then
             RAISE_APPLICATION_ERROR(-20020,'Monto Total Doc Nulo');
          else
             if ctdExisteNegativo<>0 then
                RAISE_APPLICATION_ERROR(-20020,'Monto Total Doc Negativo');
             end if;
          end if;
      end if;
    END IF;



     --MAIL DE EXITO DE INTERFACE VENTAS
    ENVIA_CORREO(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE VENTAS EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B>');
  EXCEPTION
    WHEN OTHERS THEN
      ENVIA_CORREO(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;
  /* ************************************************************************** */
FUNCTION OBTENER_NUMERACION(cCodGrupoCia_in   IN CHAR,
                cCodLocal_in     IN CHAR,
                cCodNumera_in    IN CHAR)
  RETURN NUMBER
  IS
    v_nNumero  NUMBER;
  BEGIN

    sELECT NUM.VAL_NUMERA
    INTO   v_nNumero
    FROM   PBL_NUMERA NUM
    WHERE  NUM.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     NUM.COD_LOCAL = cCodLocal_in
    AND    NUM.COD_NUMERA = cCodNumera_in FOR UPDATE;
    IF ( v_nNumero IS NULL OR v_nNumero=0 ) THEN
      v_nNumero := 1;
    END IF;

  RETURN v_nNumero;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
    RETURN 0;
  END;

  /* ************************************************************************** */
  PROCEDURE ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cCodNumera_in     IN CHAR,
                         vIdUsuario_in     IN VARCHAR2) IS
  BEGIN
    UPDATE PBL_NUMERA
       SET VAL_NUMERA = NVL(VAL_NUMERA ,1) + 1,
         USU_MOD_NUMERA = vIdUsuario_in,
       FEC_MOD_NUMERA = SYSDATE
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
   AND   COD_LOCAL = cCodLocal_in
   AND   COD_NUMERA = cCodNumera_in;
  END;
 /* *************************************************************************** */
 FUNCTION PERMITE_GEN_INT_VTA(CodGrupoCia_in  IN CHAR,
                     cCodLocal_in      IN CHAR,
                     vFecha_in   IN VARCHAR2)
  RETURN BOOLEAN is
   VRETURN boolean := false;
    vFechaIni date;
    vFechaFin date;
    nExisteNulo number;
  BEGIN

     select to_date(vFecha_in,'dd/mm/yyyy'),to_date(vFecha_in,'dd/mm/yyyy')+1-1/24/60/60
     into   vFechaIni,vFechaFin
     from   dual;

    select count(1)
    into   nExisteNulo
     from   VTA_COMP_PAGO CO,
            VTA_PEDIDO_VTA_CAB CA
     WHERE  CA.COD_GRUPO_CIA = CodGrupoCia_in
     AND    CA.COD_LOCAL = cCodLocal_in
     AND    CA.FEC_PED_VTA BETWEEN  vFechaIni AND vFechaFin
     and    co.cod_cliente_sap is null
     and    CO.tip_comp_pago != COMP_GUIA
     AND    CA.COD_GRUPO_CIA = CO.COD_GRUPO_CIA
     AND    CA.COD_LOCAL = CO.COD_LOCAL
     AND    CA.NUM_PED_VTA = CO.NUM_PED_VTA;

     if nExisteNulo > 0 then
         VRETURN := false;
     else
        if nExisteNulo = 0 then
          VRETURN := true;
        else
          VRETURN := false;
        end if;
     end if;

    return VRETURN;
  END;
 /* *************************************************************************** */
 PROCEDURE LOAD_CLI_SAP_COMP_PAGO(vFecha_in IN VARCHAR2) IS

    vCodCliSap_local MAE_CLIENTE_SAP_LOCAL.COD_CLIENTE_SAP%TYPE := 'N';

    VCodLocal_in     VTA_IMPR_LOCAL.COD_LOCAL%type := 'N';
    VCodGrupoCia_in  VTA_IMPR_LOCAL.COD_GRUPO_CIA%type := 'N';

    vFechaIni date;
    vFechaFin date;

    nExistIT_CE number;
    nExistIT_VTA number;

   begin
    null;
/*
     select to_date(vFecha_in,'dd/mm/yyyy'),to_date(vFecha_in,'dd/mm/yyyy')+1-1/24/60/60
     into   vFechaIni,vFechaFin
     from   dual;



     --- obtiene el codigo de local donde se esta ejecutando el proceso
     begin
       SELECT distinct I.COD_GRUPO_CIA,I.COD_LOCAL
       INTO   VCodGrupoCia_in,VCodLocal_in
       FROM   VTA_IMPR_LOCAL I;
     exception
      when others then
         VCodGrupoCia_in := 'N';
         VCodLocal_in    := 'N';
     end;
     --------------------------------
     if VCodLocal_in = 'N' then
        -- error en el proceso no se ejecutó en el local
            ENVIA_CORREO(VCodGrupoCia_in,VCodLocal_in,
                                              'ERROR AL LOAD_CLI_SAP_COMP_PAGO: ',
                                              'ALERTA',
                                              'HA OCURRIDO UN ERROR AL LOAD_CLI_SAP_COMP_PAGO PARA LA FECHA: '||vFecha_in||'</B>'||
                                              '<BR> <I>VERIFIQUE:</I> <BR>no se esta ejecutando en el local<B>');
     else
        begin
          select m.cod_cliente_sap
          into   vCodCliSap_local
          from   MAE_CLIENTE_SAP_LOCAL m
          where  m.cod_grupo_cia = VCodGrupoCia_in
          and    m.cod_local     = VCodLocal_in;
        exception
        when others then
           vCodCliSap_local := 'N';
        end;

        if  vCodCliSap_local = 'N' then
           -- no existe codigo de cliente sap
            ENVIA_CORREO(VCodGrupoCia_in,VCodLocal_in,
                                              'ERROR AL LOAD_CLI_SAP_COMP_PAGO: ',
                                              'ALERTA',
                                              'HA OCURRIDO UN ERROR AL LOAD_CLI_SAP_COMP_PAGO PARA LA FECHA: '||vFecha_in||'</B>'||
                                              '<BR> <I>VERIFIQUE:</I> <BR>no existe el cod cliente sap del local<B>');

        else

             select count(1)
             into   nExistIT_CE
             from   int_caja_electronica ce
             where  ce.fec_operacion =  to_date(vFecha_in,'dd/mm/yyyy')
             and    ce.cod_grupo_cia = VCodGrupoCia_in
             and    ce.cod_local = VCodLocal_in;

             select count(1)
             into   nExistIT_VTA
             from   int_vta_mf vta
             where  vta.cod_local =  VCodLocal_in
             and    vta.cod_grupo_cia = VCodGrupoCia_in
             and    vta.fechaemision =  to_char(to_date(vFecha_in,'dd/mm/yyyy'),'yyyymmdd');

            if nExistIT_CE = 0  and nExistIT_VTA = 0 then
             --------------------------------------------------------------------
             DELETE AUX_COMP_PAGO_ANALISIS;

             insert into AUX_COMP_PAGO_ANALISIS
             (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO,COD_CLI_CONV,COD_CONVENIO)
             select CO.COD_GRUPO_CIA,CO.COD_LOCAL,CO.NUM_PED_VTA,CO.SEC_COMP_PAGO,CA.COD_CLI_CONV,CA.COD_CONVENIO
             from   VTA_COMP_PAGO CO,
                    VTA_PEDIDO_VTA_CAB CA
             WHERE  CA.COD_GRUPO_CIA = VCodGrupoCia_in
             AND    CA.COD_LOCAL = VCodLocal_in
             AND    CA.FEC_PED_VTA BETWEEN  vFechaIni AND vFechaFin
             AND    CA.COD_GRUPO_CIA = CO.COD_GRUPO_CIA
             AND    CA.COD_LOCAL = CO.COD_LOCAL
             AND    CA.NUM_PED_VTA = CO.NUM_PED_VTA;

             COMMIT;
             --------------------------------------------------------------------
              -- Ventas NO CONVENIOS se actualizan con el codigo de Cliente SA
              update vta_comp_pago p
              set    p.cod_cliente_sap = vCodCliSap_local
              where  exists (
                              select 1
                              from   vta_pedido_vta_cab a,
                                     AUX_COMP_PAGO_ANALISIS AUX
                              where  nvl(a.ind_conv_btl_mf,a.ind_ped_convenio) = 'N'
                              and    a.cod_grupo_cia = p.cod_grupo_cia
                              and    a.cod_local = p.cod_local
                              and    a.num_ped_vta = p.num_ped_vta
                              AND    A.COD_GRUPO_CIA = AUX.COD_GRUPO_CIA
                              AND    A.COD_LOCAL = AUX.COD_LOCAL
                              AND    A.NUM_PED_VTA = AUX.NUM_PED_VTA
                            )
              and    p.tip_comp_pago != COMP_GUIA;

              -- Venta Convenio Crédito y el comprobante es CoPago.
              --- Ventas a RUC mifarma se saca de la tabla PBL_CIA
              update vta_comp_pago p
              set    p.cod_cliente_sap = /* AQUI DEBE DE SACAR EN BASE DEL COD_CLIENTE de la tabla
                                            mae_cliente el campo Cod_Cliente_SAP_Benef

                                         (

                                         nvl(
                                             (select v.cod_cliente_sap
                                              from   V_CON_BENEFICIARIO v,
                                                     AUX_COMP_PAGO_ANALISIS w
                                              where  v.cod_cliente = w.cod_cli_conv
                                              and    v.cod_convenio = W.COD_CONVENIO
                                              AND    W.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                                              AND    W.COD_LOCAL = P.COD_LOCAL
                                              AND    W.NUM_PED_VTA = P.NUM_PED_VTA
                                              AND    W.SEC_COMP_PAGO = P.SEC_COMP_PAGO
                                              ),
                                              vCodCliSap_local
                                              )
                                         )
              where  exists (
                              select 1
                              from   vta_pedido_vta_cab a,
                                     AUX_COMP_PAGO_ANALISIS AUX
                              where  nvl(a.ind_conv_btl_mf,a.ind_ped_convenio) = 'S'
                              and    a.cod_grupo_cia = p.cod_grupo_cia
                              and    a.cod_local = p.cod_local
                              and    a.num_ped_vta = p.num_ped_vta
                              and    a.cod_convenio in (
                                                        select t.cod_convenio
                                                        from   mae_conv_ruc_empresa t
                                                        where  t.ruc_empresa =  (
                                                                              select num_ruc_cia
                                                                              from   pbl_cia
                                                                              where  cod_cia = VCodGrupoCia_in)
                                                       )
                              AND    A.COD_GRUPO_CIA = AUX.COD_GRUPO_CIA
                              AND    A.COD_LOCAL = AUX.COD_LOCAL
                              AND    A.NUM_PED_VTA = AUX.NUM_PED_VTA
                            )
              and    p.ind_comp_credito = 'S'
              --and    p.tip_clien_convenio = 1
              and    p.tip_clien_convenio = DOC_BENEFICIARIO
              and    p.tip_comp_pago != COMP_GUIA;

              ---- NO son RUC Mifarma
              update vta_comp_pago p
              set    p.cod_cliente_sap = vCodCliSap_local
              where  exists (
                              select 1
                              from   vta_pedido_vta_cab a,
                                     AUX_COMP_PAGO_ANALISIS AUX
                              where  nvl(a.ind_conv_btl_mf,a.ind_ped_convenio) = 'S'
                              and    a.cod_grupo_cia = p.cod_grupo_cia
                              and    a.cod_local = p.cod_local
                              and    a.num_ped_vta = p.num_ped_vta
                              and    a.cod_convenio not in (
                                                        select t.cod_convenio
                                                        from   mae_conv_ruc_empresa t
                                                        where  t.ruc_empresa =  (
                                                                              select num_ruc_cia
                                                                              from   pbl_cia
                                                                              where  cod_cia = VCodGrupoCia_in)
                                                       )

                              AND    A.COD_GRUPO_CIA = AUX.COD_GRUPO_CIA
                              AND    A.COD_LOCAL = AUX.COD_LOCAL
                              AND    A.NUM_PED_VTA = AUX.NUM_PED_VTA
                            )
              and    p.ind_comp_credito = 'S'
              --and    p.tip_clien_convenio = 1
              and    p.tip_clien_convenio = DOC_BENEFICIARIO
              and    p.tip_comp_pago != COMP_GUIA;

              -- Convenios CREDITO y QUE NO SON COPAGO.
              -- estos o tienen Codigo de Bolsa o NO.
              update vta_comp_pago p
              set    p.cod_cliente_sap = /* AQUI DEBE DE SACAR EN BASE DEL COD_CLIENTE de la tabla
                                          *  mae_cliente el campo Cod_Cliente_SAP_Benef

                                        ( select nvl(m.cod_cliente_sap_bolsa,vCodCliSap_local)
                                         from   mae_conv_ruc_empresa m,
                                                vta_pedido_vta_cab c
                                         where  c.cod_grupo_cia = p.cod_grupo_cia
                                         and    c.cod_local = p.cod_local
                                         and    c.num_ped_vta = p.num_ped_vta
                                         and    m.cod_convenio = c.cod_convenio)
              where  exists (
                              select 1
                              from   vta_pedido_vta_cab a,
                                     AUX_COMP_PAGO_ANALISIS AUX
                              where  nvl(a.ind_conv_btl_mf,a.ind_ped_convenio) = 'S'
                              and    a.cod_grupo_cia = p.cod_grupo_cia
                              and    a.cod_local = p.cod_local
                              and    a.num_ped_vta = p.num_ped_vta

                              AND    A.COD_GRUPO_CIA = AUX.COD_GRUPO_CIA
                              AND    A.COD_LOCAL = AUX.COD_LOCAL
                              AND    A.NUM_PED_VTA = AUX.NUM_PED_VTA
                            )
              and    p.ind_comp_credito = 'S'
              --and    p.tip_clien_convenio = 2
              and    p.tip_clien_convenio = DOC_EMPRESA
              and    p.tip_comp_pago != COMP_GUIA;

              -- comprobantes q no son credito y son convenios..  y NO SON GUIAS
              update vta_comp_pago p
              set    p.cod_cliente_sap = vCodCliSap_local
              where  exists (
                              select 1
                              from   vta_pedido_vta_cab a,
                                     AUX_COMP_PAGO_ANALISIS AUX
                              where  nvl(a.ind_conv_btl_mf,a.ind_ped_convenio) = 'S'
                              and    a.cod_grupo_cia = p.cod_grupo_cia
                              and    a.cod_local = p.cod_local
                              and    a.num_ped_vta = p.num_ped_vta
                              AND    A.COD_GRUPO_CIA = AUX.COD_GRUPO_CIA
                              AND    A.COD_LOCAL = AUX.COD_LOCAL
                              AND    A.NUM_PED_VTA = AUX.NUM_PED_VTA
                            )
              and    p.ind_comp_credito = 'N'
              --and    p.tip_clien_convenio = 1
              and    p.tip_clien_convenio = DOC_BENEFICIARIO
              and    p.tip_comp_pago != COMP_GUIA;

              commit;

              ENVIA_CORREO(VCodGrupoCia_in,VCodLocal_in,
                                  'EXITO DE PROCESO DE LOAD_CLI_SAP_COMP_PAGO: ',
                                  'EXITO',
                                  'EXITO AL LOAD_CLI_SAP_COMP_PAGO PARA LA FECHA: '||vFecha_in||'</B>'||
                                  '<BR> <I>VERIFIQUE:</I> <BR>Exito al colocar el COD_CLI_SAP<B>');
              end if;
          end if;
     end if;
*/
   end;
 /* *************************************************************************** */
  PROCEDURE INT_REVERTIR_VENTAS(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                vFecha_in IN VARCHAR2,
                                vIdUsu_in IN VARCHAR2)
  AS
    v_dFecha DATE;
    v_nNumSec INT_VENTA.NUM_SEC_DOC%TYPE;
     g_cNumIntVentas PBL_NUMERA.COD_NUMERA%TYPE := '018';
  BEGIN
    v_dFecha := TO_DATE(vFecha_in,'dd/MM/yyyy');

    --BORRA LAS VENTAS EN LA INTERFAZ
    DELETE int_vta_mf
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FCH_VENTA BETWEEN v_dFecha AND SYSDATE;

   delete log_int_vta_mf
   where  fecha_vta BETWEEN v_dFecha AND SYSDATE
   and    cod_local = cCodLocal_in;

    --REVIERTE EL PROCESO DE LA INTERFAZ
    UPDATE VTA_COMP_PAGO p
    SET NUM_SEC_DOC_SAP = NULL,
        FEC_PROCESO_SAP = NULL,
        NUM_SEC_DOC_SAP_ANUL = NULL,
        FEC_PROCESO_SAP_ANUL = NULL,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = vIdUsu_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          and exists (
                select 1
                from   vta_pedido_vta_cab a
                where  a.cod_grupo_cia = p.cod_grupo_cia
                and    a.cod_local = p.cod_local
                and    a.num_ped_vta = p.num_ped_vta
                and    a.cod_grupo_cia = cCodGrupoCia_in
                and    a.cod_local = cCodLocal_in
                and    a.fec_ped_vta between v_dFecha and sysdate
              );
          --AND FEC_CREA_COMP_PAGO BETWEEN v_dFecha AND SYSDATE;

    UPDATE VTA_COMP_PAGO p
    SET NUM_SEC_DOC_SAP_ANUL = NULL,
        FEC_PROCESO_SAP_ANUL = NULL,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = vIdUsu_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          and exists (
                select 1
                from   vta_pedido_vta_cab a
                where  a.num_ped_vta_origen is not null
                and    a.cod_grupo_cia = p.cod_grupo_cia
                and    a.cod_local = p.cod_local
                and    a.num_ped_vta_origen = p.num_ped_vta
                and    a.cod_grupo_cia = cCodGrupoCia_in
                and    a.cod_local = cCodLocal_in
                and    a.fec_ped_vta between v_dFecha and sysdate
              );
          --AND FEC_ANUL_COMP_PAGO BETWEEN v_dFecha AND SYSDATE;

    --ACTUALIZA EL NUMERA PARA LA INTERFAZ DE VENTAS
    /*select NVL(MAX(av.NUM_SEC_DOC)+1,1)
    INTO   v_nNumSec
    from   (
            select E.COD_GRUPO_CIA, e.cod_local, e.num_sec_doc
              from INT_VENTA e
              WHERE E.COD_GRUPO_CIA = cCodGrupoCia_in
              AND   E.COD_LOCAL = cCodLocal_in
              AND   E.FEC_PROCESO < v_dFecha
            union
            select A.COD_GRUPO_CIA,
                   a.cod_local,
                   trim(a.documento) * 1 as "NUM_SEC_DOC"
              from int_vta_mf a
              WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
              AND    A.COD_LOCAL = cCodLocal_in
              AND    A.FCH_VENTA < v_dFecha
            ) av  ;



    UPDATE PBL_NUMERA
    SET VAL_NUMERA = v_nNumSec,
        FEC_MOD_NUMERA = SYSDATE,
        USU_MOD_NUMERA = vIdUsu_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_NUMERA = g_cNumIntVentas;*/


    DBMS_OUTPUT.PUT_LINE('EXITO AL REVERTIR VENTAS. LOCAL:'||cCodLocal_in||' FECHA: '||v_dFecha);
   insert into LOG_JAVA
   (cod_local, fecha_vta, log_error)
   values
   (cCodLocal_in,v_dFecha,'EXITO REVIERTE');
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('ERROR AL REVERTIR VENTAS. '||SQLERRM);
  END;


PROCEDURE creaIntPorIndConvenio ( CCODGRUPOCIA_IN IN CHAR, CCODLOCAL_IN IN CHAR, VFECPROCESO_IN IN VARCHAR2 , IND_CONV IN CHAR)

 AS

  vIni TIMESTAMP;
  valor number;

 BEGIN

     DELETE TMP_AUX_DET_PEDIDO_INT_VTA nologging;
     DELETE TMP_ANULA_DET_PEDIDO_INT_VTA nologging;
     DELETE TMP_ANULA_DET_PED_NOTA_CREDITO nologging;

     COMMIT;

-- CALCULA LAS VENTAS DE CADA TIPO DE COMPROBANTE Y SEGUN LOS TIPOS.
      -- ============================================================ --
      /*
         .- Resumen de las ventas COBRADAS NO ANULADAS, estas para los tipos que
         ya existen Boleta, Factura y Ticket Boleta
         .- Comprobantes que AFECTAN KARDEX
      */
      select systimestamp
      into   vIni
      from   dual;

      C_COBRADOS(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECPROCESO_IN,IND_CONV);

      SELECT  EXTRACT(second FROM (systimestamp - vIni))
      into    valor
      FROM    dual;

      dbms_output.put_line('C_COBRADOS : '||valor)  ;
      -- ============================================================ --
      /*
         .- Opera todas las anulaciones de cada tipo existente pero que no son NOTAS DE CREDITO.
         .- Comprobantes que AFECTAN KARDEX
      */
          for lista in (
                                select distinct i.cod_local,i.documento,i.cod_tipo_documento,i.num_documento_refer,
                                       Substr(i.num_documento_refer,6,3) SERIE,
                                       Substr(i.num_documento_refer,10,7) NUM_INI,
                                       NVL(Substr(i.num_documento_refer,18,7),Substr(i.num_documento_refer,10,7)) NUM_FIN
                                from   ptoventa.int_vta_mf i
                                where  i.fch_venta =  to_date(VFECPROCESO_IN,'dd/mm/yyyy')
                                order by 1 asc) loop

                        UPDATE ptoventa.VTA_COMP_PAGO nologging
                                SET NUM_SEC_DOC_SAP = lista.documento,
                                    FEC_PROCESO_SAP = SYSDATE,
                                    FEC_MOD_COMP_PAGO = SYSDATE,
                                    USU_MOD_COMP_PAGO = 'JB_INT_VENTA_24'
                                WHERE COD_GRUPO_CIA = '001'
                                AND   COD_LOCAL = lista.cod_local
                                AND   TIP_COMP_PAGO in DECODE(LISTA.cod_tipo_documento,1,'01',2,'02',7,'05')
                                AND   trim(Substr(num_comp_pago,1,3)) = lista.serie
                                AND   trim(Substr(num_comp_pago,4,10))*1 BETWEEN
                                                                           trim(lista.num_ini)*1 AND
                                                                           trim(lista.num_fin)*1
                               and (cod_grupo_cia,cod_local,num_ped_vta)in(
                                          select ca.cod_grupo_cia,ca.cod_local,ca.num_ped_vta
                                          from   vta_pedido_vta_cab ca
                                          where  ca.est_ped_vta = 'C'
                                          and    ca.fec_ped_vta between  to_date(VFECPROCESO_IN,'dd/mm/yyyy') and
                                                                          to_date(VFECPROCESO_IN,'dd/mm/yyyy')+1-1/24/60/60
                                          and    nvl(ca.ind_conv_btl_mf,ca.ind_ped_convenio)= IND_CONV
                                          and    ca.ind_ped_convenio= IND_CONV
                                          );
                  end loop;

      select systimestamp
      into   vIni
      from   dual;
      A_DEVOLUCIONES(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECPROCESO_IN,IND_CONV);
      SELECT  EXTRACT(second FROM (systimestamp - vIni))
      into    valor
      FROM    dual;

      dbms_output.put_line('A_DEVOLUCIONES : '||valor)  ;
      -- ============================================================ --
      /*
         .- Opera todas las Notas de Credito de los diferentes tipos de comprobantes q existen
         .- Comprobantes que AFECTAN KARDEX
      */
      select systimestamp
      into   vIni
      from   dual;
      A_NOTAS_CREDITO(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECPROCESO_IN,IND_CONV);
      SELECT  EXTRACT(second FROM (systimestamp - vIni))
      into    valor
      FROM    dual;

      dbms_output.put_line('A_NOTAS_CREDITO : '||valor)  ;
      -- ============================================================ --
      /*
         .- Opera todos los comprobantes que NO AFECTAN KARDEX y no son GUIAS
         .- Se opera cada tipo de accion, Cobro, Anulacion y/o Nota de Credito
         .- Comprobantes que NO AFECTAN KARDEX
      */
      select systimestamp
      into   vIni
      from   dual;

      S_SERVICIOS(CCODGRUPOCIA_IN,CCODLOCAL_IN,VFECPROCESO_IN,IND_CONV);

      SELECT  EXTRACT(second FROM (systimestamp - vIni))
      into    valor
      FROM    dual;

      dbms_output.put_line('S_SERVICIOS : '||valor)  ;

/*     ------------------------------------------------------
     \*
     CREATE TABLE TMP_AUX_DET_INT_VTA_du AS
     SELECT * FROM TMP_AUX_DET_PEDIDO_INT_VTA;

     CREATE TABLE TMP_ANULA_DET_INT_VTA_du AS
     SELECT * FROM TMP_ANULA_DET_PEDIDO_INT_VTA;

     CREATE TABLE TMP_ANULA_DET_NC_du AS
     SELECT * FROM TMP_ANULA_DET_PED_NOTA_CREDITO;
     *\

     INSERT INTO TMP_AUX_DET_INT_VTA_du
     SELECT * FROM TMP_AUX_DET_PEDIDO_INT_VTA;

     INSERT INTO TMP_ANULA_DET_INT_VTA_du
     SELECT * FROM TMP_ANULA_DET_PEDIDO_INT_VTA;

     INSERT INTO TMP_ANULA_DET_NC_du
     SELECT * FROM TMP_ANULA_DET_PED_NOTA_CREDITO;
     */
     ------------------------------------------------------


    COMMIT;
  END;


  FUNCTION VALIDA_VTAS_CARGA RETURN BOOLEAN
  AS
  CANT_A NUMBER;
  CANT_B NUMBER;
  CANT_C NUMBER;
  RSPTA BOOLEAN;
  BEGIN


    SELECT COUNT(1) INTO CANT_A FROM TMP_AUX_DET_PEDIDO_INT_VTA;
    SELECT COUNT(1) INTO CANT_B FROM TMP_ANULA_DET_PEDIDO_INT_VTA;
    SELECT COUNT(1) INTO CANT_C FROM TMP_ANULA_DET_PED_NOTA_CREDITO;


    IF CANT_A = CANT_B AND CANT_B = CANT_C AND CANT_C = 0 THEN
      RSPTA := TRUE;
    ELSE
      RSPTA := FALSE;
    END IF;

    RETURN  RSPTA;

  END;

 /* *************************************************************************** */

procedure   grabaLog(tcodiloca_in  in char ,
                     cFechaProceso in char,
                     vMsg in varchar2 )
 IS PRAGMA AUTONOMOUS_TRANSACTION;

 BEGIN
   insert into LOG_INT_VTA_MF
   (cod_local, fecha_vta, log_error)
   values
   (tcodiloca_in,TO_DATE(cFechaProceso, 'DD/MM/YYYY'),vMsg);
   commit;
 end;
 /**************************************************** */
  FUNCTION listaLocalFecha(cCodGrupoCia_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curLab FarmaCursor;
  BEGIN
    OPEN curLab FOR
    select *
    from(
      select t.cod_local|| 'Ã' ||to_char(t.fecha,'dd/mm/yyyy')
      from   TMP_LISTA_LOCAL_VTA t
      where  t.fecha = '10/06/2013'
      and    t.cod_local not in ('160')
      /*and    (t.cod_local,t.fecha) not in (
                                 select distinct l.cod_local,l.fecha_vta
                                 from   LOG_JAVA l
                                 where  l.log_error like 'Exito Gen INT%'
                                 and   l.fecha_vta = '03/06/2013'
                                 )*/
     and   (t.cod_local,t.fecha) not in (
                                        select lo.cod_local,lo.fch_venta
                                        from   int_vta_mf lo
                                        where  lo.fch_venta = '10/06/2013'
                                        )
      order by t.fecha,1 asc)
      where rownum <= 40;
    RETURN curLab;
  END;

  /* ***************************************** */
procedure   grabaLog_JAVA(tcodiloca_in  in char ,
                     cFechaProceso in char,
                     vMsg in varchar2 )
 IS PRAGMA AUTONOMOUS_TRANSACTION;

 BEGIN
   insert into LOG_JAVA
   (cod_local, fecha_vta, log_error)
   values
   (tcodiloca_in,TO_DATE(cFechaProceso, 'DD/MM/YYYY'),vMsg);
   commit;
 end;
 /* ****************************************** */
procedure   corrigeDifRedondeo(tcodiloca_in  in char ,
                               cFechaProceso in char)
is
cCodGrupoCia_in VARCHAR2(20);
cCodLocal_in   VARCHAR2(20);
vFecProceso_in VARCHAR2(20);
v_correlativo  int_vta_mf.correlativo%type;
v_cod_producto int_vta_mf.cod_producto%type;
v_mto_total_pos int_vta_mf.mto_total_pos%type;
v_mto_total int_vta_mf.mto_total%type;

v_mto_total_vta number := 0;
v_mto_total_int number := 0;
dif number:=0;

v_mto_t_emitidos number := 0;
v_mto_t_anulados number := 0;
datoMalGrabado number := 0;
vDOcumento int_vta_mf.documento%type;
begin

 cCodGrupoCia_in := '001';
    cCodLocal_in   := tcodiloca_in;
    vFecProceso_in := cFechaProceso;

SELECT SUM(cp.val_neto_comp_pago + cp.val_redondeo_comp_pago)
  INTO v_mto_t_emitidos
    FROM  vta_pedido_vta_cab c,
          Vta_Comp_Pago cp
    WHERE c.cod_grupo_cia = cCodGrupoCia_in
    AND   c.cod_local = cCodLocal_in
    AND   c.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
    AND   c.EST_PED_VTA in ('C','S')
    and   cp.tip_comp_pago != '03'
    and   c.cod_grupo_cia = cp.cod_grupo_cia
    and   c.cod_local = cp.cod_local
    and   c.num_ped_vta = cp.num_ped_vta;

    -- aqui estan los pedidos Anulados que no sean NOTA DE CREDITO
    SELECT SUM((cp.val_neto_comp_pago + cp.val_redondeo_comp_pago)*-1)
     INTO v_mto_t_anulados
    FROM  vta_pedido_vta_cab c,
          Vta_Comp_Pago cp
    WHERE c.cod_grupo_cia = cCodGrupoCia_in
    AND   c.cod_local = cCodLocal_in
    and   cp.tip_comp_pago != '03'
    AND   c.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
    AND   c.EST_PED_VTA in ('C','S')
    and   cp.fec_anul_comp_pago BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND
                                TO_DATE(vFecProceso_in,'dd/MM/yyyy')+ 1 - 1/24/60/60
    and   c.cod_grupo_cia = cp.cod_grupo_cia
    and   c.cod_local = cp.cod_local
    and   c.num_ped_vta_origen = cp.num_ped_vta;

   -----
   v_mto_total_vta := v_mto_t_emitidos + v_mto_t_anulados;
      select ROUND(sum((case
                             when instr(mto_total_pos, '-') <> 0 then
                              to_number(mto_total_pos, '9,999,990.00S')
                             else
                              to_number(mto_total_pos, '99,999,990.00')
                         end) * (case
                             when cod_tipo_documento in ('1', '2', '7', '8', '13') then
                              1
                             else
                              -1
                         end) - (to_number(imp_dscto, '9999999990.00') *
                                     (case
                                          when cod_tipo_documento in ('1', '2', '7', '8', '13') then
                                           1
                                          else
                                           -1
                                      end))),2)
          into v_mto_total_int
          from int_vta_mf a
         where COD_LOCAL     = cCodLocal_in
           and COD_GRUPO_CIA = cCodGrupoCia_in
           and FECHAEMISION  = to_char(TO_DATE(vFecProceso_in,'dd/MM/yyyy'), 'YYYYMMDD');

select count(1)
into datoMalGrabado
  from (select a.documento,
               a.num_documento_refer,
               sum((case
                     when instr(mto_total_pos, '-') <> 0 then
                      to_number(mto_total_pos, '9,999,990.00S')
                     else
                      to_number(mto_total_pos, '99,999,990.00')
                   end) - (case
                     when instr(IMP_DSCTO, '-') <> 0 then
                      to_number(IMP_DSCTO, '9,999,990.00S')
                     else
                      to_number(IMP_DSCTO, '99,999,990.00')
                   end)

                   ) MONTO_DET,
               (case
                 when instr(a.mto_total, '-') <> 0 then
                  to_number(a.mto_total, '9,999,990.00S')
                 else
                  to_number(a.mto_total, '99,999,990.00')
               end) Monto_TOTAL
          from int_vta_mf a
         where COD_LOCAL = cCodLocal_in
           and COD_GRUPO_CIA = cCodGrupoCia_in
           and FECHAEMISION =
               to_char(TO_DATE(vFecProceso_in, 'dd/MM/yyyy'), 'YYYYMMDD')
           and a.tipo_posicion = 1
         group by a.documento,a.num_documento_refer,
                  (case
                    when instr(a.mto_total, '-') <> 0 then
                     to_number(a.mto_total, '9,999,990.00S')
                    else
                     to_number(a.mto_total, '99,999,990.00')
                  end)) TT
 where abs(tt.monto_det - tt.monto_total) > 0;
  dbms_output.put_line('v_mto_total_vta >>'||v_mto_total_vta);
    dbms_output.put_line('v_mto_total_int >>'||v_mto_total_int);
    --DBMS_OUTPUT.PUT_LINE('LOS MONTOS DE VENTAS: FECHA= ' || vFecProceso_in || ' VENTAS='||v_nMontoVentas||':: INTERFAZ='||v_nMontoInterfaz);
    IF v_mto_total_vta <> v_mto_total_int THEN

        select documento
        into   vDOcumento
          from (select i.documento, count(1)
                  from int_vta_mf i
                 where i.cod_local = cCodLocal_in
                   and i.fch_venta = TO_DATE(vFecProceso_in, 'dd/MM/yyyy')
                 group by i.documento
                 order by 2 desc)
         where rownum = 1;

       dif := v_mto_total_vta - v_mto_total_int;
       dbms_output.put_line('difaaa >>'||dif);

       if dif > 0  and dif<1.5 then

                select i.correlativo,i.cod_producto,
                       to_char((case
                                 when instr(i.mto_total_pos, '-') <> 0 then
                                  to_number(i.mto_total_pos, '9,999,990.00S')
                                 else
                                  to_number(i.mto_total_pos, '99,999,990.00')
                               end) + dif, '99,999,990.00'),
                       to_char((case
                                 when instr(i.mto_total, '-') <> 0 then
                                  to_number(i.mto_total, '9,999,990.00S')
                                 else
                                  to_number(i.mto_total, '99,999,990.00')
                               end) + dif, '99,999,990.00')
                into   v_correlativo,v_cod_producto,v_mto_total_pos,v_mto_total
                  from int_vta_mf i
                 where i.cod_local = cCodLocal_in
                   and i.fch_venta = TO_DATE(vFecProceso_in, 'dd/MM/yyyy')
                   and i.documento = vDOcumento
                   and i.tipo_posicion = 1
                   and rownum = 1;
           dbms_output.put_line('dif>>'||dif);
          update int_vta_mf i
          set   i.mto_total_pos = v_mto_total_pos
          where i.cod_local = cCodLocal_in
           and i.fch_venta = TO_DATE(vFecProceso_in, 'dd/MM/yyyy')
           and i.documento = vDOcumento
           and i.tipo_posicion = 1
           and i.correlativo = v_correlativo
           and i.cod_producto = v_cod_producto;

          update int_vta_mf i
          set    i.mto_total = v_mto_total
          where i.cod_local = cCodLocal_in
           and i.fch_venta = TO_DATE(vFecProceso_in, 'dd/MM/yyyy')
           and i.documento = vDOcumento;

       end if;


    END IF;

end;
/* ******************************************************************************************** */
PROCEDURE AUX_SAVE_CLIE_SAP_COMP (
                                  cCodGrupoCia_in in char,
                                  cCodLocal_in in char,
                                  cFechProceso_in in char
                                  )
  AS
  cCodLocal char(3);
  cCodCia   char(3);
begin
  cCodLocal := cCodLocal_in;

  SELECT cod_cia into cCodCia
  FROM Pbl_Local WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND COD_lOCAL=cCodLocal_in;

  for  lista in (select *
               from   ptoventa.mae_cliente_sap_local G
               WHERE  G.COD_LOCAL = cCodLocal) loop

                  update ptoventa.vta_comp_pago nologging
                  set    cod_cliente_sap  = lista.cod_cliente_sap
                  where  (cod_grupo_cia,cod_local,num_ped_vta) in (select c.cod_grupo_cia,c.cod_local,c.num_ped_vta
                                                                    from   vta_pedido_vta_cab c
                                                                    where  c.cod_grupo_cia = '001'
                                                                    and    c.cod_local = lista.cod_local
                                                                    and    c.est_ped_vta = 'C'
                                                                    and    c.fec_ped_vta between  to_date(cFechProceso_in,'dd/mm/yyyy')
                                                                                              and to_date(cFechProceso_in,'dd/mm/yyyy') + 1 -1/24/60/60
                                                                  );

  end loop;

--- cliente de convenio
delete TMP_COMP_CONV_UPDATE;

IF cCodCia=MARCA_MIFARMA THEN

       insert into  TMP_COMP_CONV_UPDATE
      (cod_grupo_cia, cod_local, num_ped_vta, sec_comp_pago, cod_convenio, cod_cli_conv, cod_cli_sap_comp)
      select cp.COD_GRUPO_CIA,cp.COD_LOCAL,cp.NUM_PED_VTA,cp.SEC_COMP_PAGO,
             t.cod_convenio,t.cod_cli_conv,
             (
             select nvl(v.cod_cliente_sap_bolsa,
                        case
                          when v.tmp_ruc = RUC_MIFARMA then
                                                           decode(FLG_TIPO_CONVENIO,2,null,(
                                                           nvl(
                                                           (
                                                           select nvl(mcs.cod_cliente_sap,'SIN_CCLIE')
                                                           from   MAE_CLIENTE_SAP_MF mcs
                                                           where  mcs.cod_convenio = t.cod_convenio
                                                           and    mcs.cod_cliente =  t.cod_cli_conv
                                                           )
                                                           ,'SIN_CCLIE')
                                                          ))
                          when v.tmp_ruc = RUC_FASA then
                                                        decode(FLG_TIPO_CONVENIO,2,null,BOLSA_FASA)
                          when v.tmp_ruc = RUC_BTL then
                                                        decode(FLG_TIPO_CONVENIO,2,null,BOLSA_BTL)
                        else
                            decode(FLG_TIPO_CONVENIO,2,null,nvl(v.cod_cliente_sap,'SIN_CCLIE'))
                        end
                       )
             from   mae_convenio v
             where  v.cod_convenio = t.cod_convenio
             )COD_CLI_SAP_COMP
      from   vta_comp_pago cp,
             (
               select c.cod_grupo_cia, c.cod_local, c.num_ped_vta,
                     c.cod_convenio,
                     c.cod_cli_conv
                from vta_pedido_vta_cab c
               where c.cod_grupo_cia = '001'
                 and c.cod_local = cCodLocal
                 and c.est_ped_vta = 'C'
                 and c.fec_ped_vta between to_date(cFechProceso_in,'dd/mm/yyyy')
                                       and to_date(cFechProceso_in,'dd/mm/yyyy') + 1 -1/24/60/60
                 and c.ind_ped_convenio = 'S'
                 and c.ind_conv_btl_mf  = 'S'
             ) T
      where  cp.tip_comp_pago != '03'
      and    cp.cod_grupo_cia = t.cod_grupo_cia
      and    cp.cod_local = t.cod_local
      and    cp.num_ped_vta = t.num_ped_vta;


ELSE
     IF cCodCia=MARCA_FASA THEN
           insert into  TMP_COMP_CONV_UPDATE
      (cod_grupo_cia, cod_local, num_ped_vta, sec_comp_pago, cod_convenio, cod_cli_conv, cod_cli_sap_comp)
      select cp.COD_GRUPO_CIA,cp.COD_LOCAL,cp.NUM_PED_VTA,cp.SEC_COMP_PAGO,
             t.cod_convenio,t.cod_cli_conv,
             (
             select nvl(v.cod_cliente_sap_bolsa,
                        case
                           when v.tmp_ruc = RUC_FASA then
                                                          decode(v.flg_tipo_convenio,2,null,(
                                                           nvl(
                                                               (
                                                               select nvl(mcs.cod_cliente_sap,'SIN_CCLIE')
                                                               from   MAE_CLIENTE_SAP_FASA mcs
                                                               where  mcs.cod_convenio = t.cod_convenio
                                                               and    mcs.cod_cliente =  t.cod_cli_conv
                                                               )
                                                           ,'SIN_CCLIE'
                                                               )
                                                          ))
                           when v.tmp_ruc = RUC_MIFARMA then
                                                            decode(v.flg_tipo_convenio,2,null,BOLSA_MF)
                           when v.tmp_ruc = RUC_BTL then
                                                        decode(v.flg_tipo_convenio,2,null,BOLSA_BTL)
                           else
                               decode(v.flg_tipo_convenio,2,null,nvl(v.cod_cliente_sap,'SIN_CCLIE'))
                        end
                       )
             from   mae_convenio v
             where  v.cod_convenio = t.cod_convenio
             )COD_CLI_SAP_COMP
      from   vta_comp_pago cp,
             (
               select c.cod_grupo_cia, c.cod_local, c.num_ped_vta,
                     c.cod_convenio,
                     c.cod_cli_conv
                from vta_pedido_vta_cab c
               where c.cod_grupo_cia = '001'
                 and c.cod_local = cCodLocal
                 and c.est_ped_vta = 'C'
                 and c.fec_ped_vta between to_date(cFechProceso_in,'dd/mm/yyyy')
                                       and to_date(cFechProceso_in,'dd/mm/yyyy') + 1 -1/24/60/60
                 and c.ind_ped_convenio = 'S'
                 and c.ind_conv_btl_mf  = 'S'
             ) T
      where  cp.tip_comp_pago != '03'
      and    cp.cod_grupo_cia = t.cod_grupo_cia
      and    cp.cod_local = t.cod_local
      and    cp.num_ped_vta = t.num_ped_vta;      
     ELSE
         IF cCodCia=MARCA_BTL THEN
              insert into  TMP_COMP_CONV_UPDATE
              (cod_grupo_cia, cod_local, num_ped_vta, sec_comp_pago, cod_convenio, cod_cli_conv, cod_cli_sap_comp)
                select cp.COD_GRUPO_CIA,cp.COD_LOCAL,cp.NUM_PED_VTA,cp.SEC_COMP_PAGO,
                       t.cod_convenio,t.cod_cli_conv,
                       (
                       select nvl(v.cod_cliente_sap_bolsa,
                                  case
                                     when v.tmp_ruc = RUC_BTL then
                                                                    decode(v.flg_tipo_convenio,2,null,(
                                                                     nvl(
                                                                         (
                                                                         select nvl(mcs.cod_cliente_sap,'SIN_CCLIE')
                                                                         from   MAE_CLIENTE_SAP_BTL mcs
                                                                         where  mcs.cod_convenio = t.cod_convenio
                                                                         and    mcs.cod_cliente =  t.cod_cli_conv
                                                                         )
                                                                     ,'SIN_CCLIE'
                                                                         )
                                                                    ))
                                     when v.tmp_ruc = RUC_MIFARMA then
                                                                      decode(v.flg_tipo_convenio,2,null,BOLSA_MF)
                                     when v.tmp_ruc = RUC_FASA then
                                                                  decode(v.flg_tipo_convenio,2,null,BOLSA_FASA)
                                     else
                                         decode(v.flg_tipo_convenio,2,null,nvl(v.cod_cliente_sap,'SIN_CCLIE'))
                                  end
                                 )
                       from   mae_convenio v
                       where  v.cod_convenio = t.cod_convenio
                       )COD_CLI_SAP_COMP
                from   vta_comp_pago cp,
                       (
                         select c.cod_grupo_cia, c.cod_local, c.num_ped_vta,
                               c.cod_convenio,
                               c.cod_cli_conv
                          from vta_pedido_vta_cab c
                         where c.cod_grupo_cia = '001'
                           and c.cod_local = cCodLocal
                           and c.est_ped_vta = 'C'
                           and c.fec_ped_vta between to_date(cFechProceso_in,'dd/mm/yyyy')
                                                 and to_date(cFechProceso_in,'dd/mm/yyyy') + 1 -1/24/60/60
                           and c.ind_ped_convenio = 'S'
                           and c.ind_conv_btl_mf  = 'S'
                       ) T
                where  cp.tip_comp_pago != '03'
                and    cp.cod_grupo_cia = t.cod_grupo_cia
                and    cp.cod_local = t.cod_local
                and    cp.num_ped_vta = t.num_ped_vta;              
         END IF;
     END IF;
END IF;

--select v.cod_cliente_sap_bolsa,v.cod_cliente_sap,v.tmp_ruc
--from   mae_convenio v;

update vta_comp_pago p
   set p.cod_cliente_sap =
       (select t.cod_cli_sap_comp
          from TMP_COMP_CONV_UPDATE t
         where t.COD_GRUPO_CIA = p.cod_grupo_cia
           and t.COD_LOCAL = p.cod_local
           and t.NUM_PED_VTA = p.num_ped_vta
           and t.SEC_COMP_PAGO = p.sec_comp_pago)
 where exists (select t.cod_cli_sap_comp
          from TMP_COMP_CONV_UPDATE t
         where t.COD_GRUPO_CIA = p.cod_grupo_cia
           and t.COD_LOCAL = p.cod_local
           and t.NUM_PED_VTA = p.num_ped_vta
           and t.SEC_COMP_PAGO = p.sec_comp_pago);

update vta_comp_pago p
   set p.cod_cliente_sap = (select G.COD_CLIENTE_SAP
                           from   ptoventa.mae_cliente_sap_local G
                           WHERE  G.COD_LOCAL = cCodLocal)
 where P.COD_CLIENTE_SAP IS NULL
 AND   (P.COD_GRUPO_CIA,P.COD_LOCAL,P.NUM_PED_VTA) IN
                                                   (
                                                   SELECT CC.COD_GRUPO_CIA,
                                                          CC.COD_LOCAL,
                                                          CC.NUM_PED_VTA
                                                   FROM   VTA_PEDIDO_VTA_CAB CC
                                                   WHERE  CC.COD_GRUPO_CIA = cCodGrupoCia_in
                                                   AND    CC.COD_LOCAL = cCodLocal
                                                   AND    CC.FEC_PED_VTA >= (
                                                                            select nvl((select t.fech_migra_mfa
                                                                                          from   PBL_LOCAL_MIGRA t
                                                                                          where  t.cod_local = cCodLocal_in),
                                                                            		to_date('01/01/2012','dd/mm/yyyy')) from dual
                                                                            )
                                                   );

--- cliente de convenio
UPDATE VTA_COMP_PAGO p
    SET NUM_SEC_DOC_SAP = NULL,
        FEC_PROCESO_SAP = NULL,
        NUM_SEC_DOC_SAP_ANUL = NULL,
        FEC_PROCESO_SAP_ANUL = NULL,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = 'DUBILLUZ'
    WHERE COD_GRUPO_CIA = '001'
          AND COD_LOCAL = cCodLocal
          and exists (
                select 1
                from   vta_pedido_vta_cab a
                where  a.cod_grupo_cia = p.cod_grupo_cia
                and    a.cod_local = p.cod_local
                and    a.num_ped_vta = p.num_ped_vta
                and    a.cod_grupo_cia = '001'
                and    a.cod_local = cCodLocal
                and    a.fec_ped_vta between to_date(cFechProceso_in,'dd/mm/yyyy') and to_date(cFechProceso_in,'dd/mm/yyyy') + 1 -1/24/60/60
              );

          --AND FEC_CREA_COMP_PAGO BETWEEN v_dFecha AND SYSDATE;

    UPDATE VTA_COMP_PAGO p
    SET NUM_SEC_DOC_SAP_ANUL = NULL,
        FEC_PROCESO_SAP_ANUL = NULL,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = 'DUBILLUZ'
    WHERE COD_GRUPO_CIA = '001'
          AND COD_LOCAL = cCodLocal
          and exists (
                select 1
                from   vta_pedido_vta_cab a
                where  a.num_ped_vta_origen is not null
                and    a.cod_grupo_cia = p.cod_grupo_cia
                and    a.cod_local = p.cod_local
                and    a.num_ped_vta_origen = p.num_ped_vta
                and    a.cod_grupo_cia = '001'
                and    a.cod_local = cCodLocal
                and    a.fec_ped_vta between  to_date(cFechProceso_in,'dd/mm/yyyy') and to_date(cFechProceso_in,'dd/mm/yyyy') + 1 -1/24/60/60
              );
  commit;
end;
/* ********************************************************************************************* */
END MF_INT_VTA;

/
