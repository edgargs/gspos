--------------------------------------------------------
--  DDL for Package Body BTL_SAC_INT_RECEP_CIEGA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."BTL_SAC_INT_RECEP_CIEGA" is
  /****************************************************************************/
  
  PROCEDURE P_SOBRANTES_FALTANTES/*(cFechaProceso    in char,cFechaProcesoFin in char)*/ AS
  BEGIN

    FOR listaRecep in (
                         SELECT COD_LOCAL, NRO_RECEP,rm.fec_recep  FECHA_RECEPCION
                         FROM LGT_RECEP_MERCADERIA RM
                         WHERE RM.ESTADO = 'T'                           
                         --AND RM.FEC_CREA_RECEP  between
                         --to_date(cFechaProceso, 'dd/mm/yyyy') and
                         --to_date(cFechaProcesoFin, 'dd/mm/yyyy') + 1 - 1 / 24 / 60 / 60
                         AND RM.IND_AFEC_RECEP_CIEGA = 'S'
                         and  FEC_PROC_INT is null  
                         --AND RM.NRO_RECEP='0000000001'                                     
                         order by 1 asc, 2 asc        
                        ) loop

       BEGIN             
            P_CREA_FALTANTES(listaRecep.COD_LOCAL, listaRecep.NRO_RECEP);
            P_CREA_SOBRANTES(listaRecep.COD_LOCAL, listaRecep.NRO_RECEP);

            update LGT_RECEP_MERCADERIA a
            set    a.FEC_PROC_INT = sysdate
            where  a.COD_LOCAL = listaRecep.COD_LOCAL
            and    a.NRO_RECEP = listaRecep.NRO_RECEP;    
            COMMIT;   
        END;
    END LOOP;
        
    PTOVENTA.BTL_SAC_INT_RECEP_CIEGA.INT_PED_FALTANTE('001'); 
    PTOVENTA.BTL_SAC_INT_RECEP_CIEGA.INT_PED_SOBRANTE('001');    
    COMMIT;    
  END;
  
  
  
  PROCEDURE P_CREA_FALTANTES(cCodLocal_in    in char,
                             cNroRecepcion in char) AS
  BEGIN
       
        INSERT INTO PTOVENTA.AUX_DET_FALTAN
        SELECT 
        (SELECT DISTINCT NUM_FACTURA_SAP FROM INT_RECEP_PROD_QS RC 
        WHERE RC.COD_LOCAL=cCodLocal_in AND RC.NUM_ENTREGA =M.NUM_ENTREGA AND RC.COD_PROD=M.PRODUCTO) NUM_GUIA,
        NUM_ENTREGA,
        '' NUM_LOTE, 
        cCodLocal_in COD_LOCAL,cCodLocal_in COD_LOCAL_SAP,
        PRODUCTO COD_PRODUCTO, PRODUCTO COD_CODIGO_SAP,
        (SELECT DESC_PROD||' '||DESC_UNID_PRESENT FROM LGT_PROD WHERE COD_PROD=PRODUCTO) DES_PRODUCTO,
        (SELECT NOM_LAB FROM LGT_LAB WHERE COD_LAB =(SELECT COD_LAB FROM LGT_PROD WHERE COD_PROD=PRODUCTO )) DES_LABORATORIO,
        ABS(DIFERENCIA)  DIFERENCIA2,
        'FALTANTE' TIPO,
        ABS(DIFERENCIA) CTD,
        NULL FECHA_PROCESO,
        (SELECT DISTINCT NUM_FACTURA_SAP FROM INT_RECEP_PROD_QS RC 
        WHERE RC.COD_LOCAL=cCodLocal_in AND RC.NUM_ENTREGA =M.NUM_ENTREGA AND RC.COD_PROD=M.PRODUCTO) FACTURA_OUT,
        cNroRecepcion ID_ENTREGA,
        'S' FLG_COMPLETO,
        SYSDATE FEC_CREA,
        CANT_ENTREGADA CTD_ENTREGADA,
        CANT_CONTADA CTD_CONTADA
        FROM
        (
         SELECT PRODUCTO , DESCRIP_PROD , UNIDAD , LAB , CANT_CONTADA , CANT_ENTREGADA ,    
                      NUM_ENTREGA  ,
                     (SELECT CASE
                               WHEN CANT < 0 THEN
                                CANT * (-1)
                               ELSE
                                CANT
                             END
                        FROM DUAL) DIFERENCIA                         
                FROM (SELECT Q1.PROD PRODUCTO,
                             (Q2.CANT2 - Q2.CANT_CONTADA) CANT,                             
                             Q3.DESC_PROD DESCRIP_PROD,
                             Q3.DESC_UNID_PRESENT UNIDAD,
                             (SELECT L.NOM_LAB
                                FROM LGT_LAB L
                               WHERE L.COD_LAB = Q3.COD_LAB) LAB,
                             Q1.SEC_CONTEO,
                             Q1.CANT1 CANTTOTAL_CONTADA,
                             Q2.CANT2 CANT_ENTREGADA,
                             Q2.NUM_ENTREGA,
                             Q2.CANT_CONTADA CANT_CONTADA
                        FROM (SELECT A.COD_PROD PROD,
                                     NVL(A.CANT_SEG_CONTEO, A.CANTIDAD) CANT1,                                    
                                     A.SEC_CONTEO SEC_CONTEO
                                FROM LGT_PROD_CONTEO A 
                               WHERE A.COD_GRUPO_CIA = '001'
                                 AND A.COD_LOCAL = cCodLocal_in
                                 AND A.NRO_RECEP = cNroRecepcion) Q1,
        
                             (SELECT B.COD_PROD PROD,
                                     SUM(B.CANT_ENVIADA_MATR) CANT2,
                                     ' ' COD_BARRA,
                                     B.NUM_ENTREGA NUM_ENTREGA,
                                     SUM(B.CANT_CONTADA) CANT_CONTADA
                                FROM LGT_NOTA_ES_DET B
                               WHERE B.COD_GRUPO_CIA = '001'
                                 AND B.COD_LOCAL = cCodLocal_in
                                 AND B.NUM_ENTREGA || B.NUM_NOTA_ES IN
                                     (SELECT C.NUM_ENTREGA || C.NUM_NOTA_ES
                                        FROM LGT_RECEP_ENTREGA C
                                       WHERE C.COD_GRUPO_CIA = '001'
                                         AND C.COD_LOCAL = cCodLocal_in
                                         AND C.NRO_RECEP = cNroRecepcion)
                               GROUP BY B.COD_PROD,B.NUM_ENTREGA,B.CANT_CONTADA) Q2,
                             LGT_PROD Q3
        
                       WHERE Q1.PROD = Q2.PROD
                         AND Q3.COD_GRUPO_CIA = '001'
                         AND Q3.COD_PROD = Q1.PROD
                      )
               WHERE CANT > 0
               
               UNION      
               SELECT K.COD_PROD , K.DESC_PROD , K.DESC_UNID_PRESENT ,  (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = K.COD_LAB) , 0 , I.CANT_ENVIADA_MATR ,I.NUM_ENTREGA , I.CANT_ENVIADA_MATR 
               FROM LGT_NOTA_ES_DET I, LGT_PROD K
               WHERE I.COD_GRUPO_CIA = '001'
               AND I.COD_LOCAL       = cCodLocal_in
               AND I.NUM_ENTREGA IN (SELECT J.NUM_ENTREGA FROM  LGT_RECEP_ENTREGA J
                                      WHERE J.COD_GRUPO_CIA = '001'
                                      AND J.COD_LOCAL = cCodLocal_in
                                      AND J.NRO_RECEP = cNroRecepcion
                                     )
                AND I.COD_PROD NOT IN (
                                        SELECT K.COD_PROD FROM LGT_PROD_CONTEO K
                                        WHERE K.COD_GRUPO_CIA = '001'
                                        AND K.COD_LOCAL       = cCodLocal_in
                                        AND K.NRO_RECEP       = cNroRecepcion
                                      )
                AND I.COD_GRUPO_CIA = K.COD_GRUPO_CIA
                AND I.COD_PROD      = K.COD_PROD       
          ) M;
          
          COMMIT;
  END;
  
  
  
    PROCEDURE P_CREA_SOBRANTES(cCodLocal_in    in char,
                             cNroRecepcion in char) AS
  BEGIN
       
        INSERT INTO PTOVENTA.AUX_DET_SOBRAN
        SELECT 
          '' NUM_GUIA,NUM_ENTREGA,'' NUM_LOTE, cCodLocal_in COD_LOCAL,cCodLocal_in COD_LOCAL_SAP,
        PRODUCTO COD_PRODUCTO, PRODUCTO COD_CODIGO_SAP,
        (SELECT DESC_PROD||' '||DESC_UNID_PRESENT FROM LGT_PROD WHERE COD_PROD=PRODUCTO) DES_PRODUCTO,
        (SELECT NOM_LAB FROM LGT_LAB WHERE COD_LAB =(SELECT COD_LAB FROM LGT_PROD WHERE COD_PROD=PRODUCTO )) DES_LABORATORIO,
        ABS(DIFERENCIA)  DIFERENCIA2,
        'SOBRANTE' TIPO,
        ABS(DIFERENCIA) CTD,
        NULL FECHA_PROCESO,
        '' FACTURA_OUT,
        cNroRecepcion ID_ENTREGA,
        'S' FLG_COMPLETO,
        SYSDATE FEC_CREA,
        CANT_ENTREGADA CTD_ENTREGADA,
        CANT_CONTADA CTD_CONTADA
        FROM
        (
        SELECT  PRODUCTO , DESCRIP_PROD , UNIDAD , LAB , CANT_CONTADA , CANT_ENTREGADA ,      
                    NUM_ENTREGA  ,
                   (SELECT CASE
                             WHEN CANT < 0 THEN
                              CANT * (-1)
                             ELSE
                              CANT
                           END
                      FROM DUAL)  DIFERENCIA               
              FROM (SELECT Q1.PROD PRODUCTO,
                           (Q2.CANT2 - Q2.CANT_CONTADA) CANT,                   
                           Q3.DESC_PROD DESCRIP_PROD,
                           Q3.DESC_UNID_PRESENT UNIDAD,
                           (SELECT L.NOM_LAB
                              FROM LGT_LAB L
                             WHERE L.COD_LAB = Q3.COD_LAB) LAB,
                           Q1.SEC_CONTEO,
                           Q1.CANT1 CANTTOTAL_CONTADA,
                           Q2.CANT2 CANT_ENTREGADA,
                           Q2.NUM_ENTREGA,
                           Q2.CANT_CONTADA CANT_CONTADA
                      FROM (SELECT A.COD_PROD PROD,
                                   NVL(A.CANT_SEG_CONTEO, A.CANTIDAD) CANT1,                            
                                   A.SEC_CONTEO SEC_CONTEO
                              FROM LGT_PROD_CONTEO A 
                             WHERE A.COD_GRUPO_CIA = '001'
                               AND A.COD_LOCAL = cCodLocal_in
                               AND A.NRO_RECEP = cNroRecepcion) Q1,
      
                           (SELECT B.COD_PROD PROD,
                                   SUM(B.CANT_ENVIADA_MATR) CANT2,
                                   ' ' COD_BARRA,
                                   B.NUM_ENTREGA NUM_ENTREGA,
                                   SUM(B.CANT_CONTADA) CANT_CONTADA
                              FROM LGT_NOTA_ES_DET B
                             WHERE B.COD_GRUPO_CIA = '001'
                               AND B.COD_LOCAL = cCodLocal_in
                               AND B.NUM_ENTREGA || B.NUM_NOTA_ES IN
                                   (SELECT C.NUM_ENTREGA || C.NUM_NOTA_ES
                                      FROM LGT_RECEP_ENTREGA C
                                     WHERE C.COD_GRUPO_CIA = '001'
                                       AND C.COD_LOCAL = cCodLocal_in
                                       AND C.NRO_RECEP = cNroRecepcion)
                             GROUP BY B.COD_PROD,B.NUM_ENTREGA,B.CANT_CONTADA) Q2,
                           LGT_PROD Q3
      
                     WHERE Q1.PROD = Q2.PROD
                       AND Q3.COD_GRUPO_CIA = '001'
                       AND Q3.COD_PROD = Q1.PROD
                    )
             WHERE CANT < 0
      
            UNION
            --FUERA DE GUIA
            SELECT H.COD_PROD PRODUCTO,
                   H.DESC_PROD  DESCRIP_PROD,
                   H.DESC_UNID_PRESENT UNIDAD,
                   (SELECT L.NOM_LAB FROM LGT_LAB L WHERE L.COD_LAB = H.COD_LAB) LAB,
                   NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) CANT_CONTADA,
                   0  CANT_ENTREGADA,
                   ' ' NUM_ENTREGA,
                    NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) DIFERENCIA              
              FROM LGT_PROD_CONTEO E, LGT_PROD H
             WHERE E.COD_GRUPO_CIA = '001'
               AND E.COD_LOCAL = cCodLocal_in
               AND E.NRO_RECEP = cNroRecepcion
               AND E.COD_PROD NOT IN
                   (SELECT F.COD_PROD
                      FROM LGT_NOTA_ES_DET F, LGT_RECEP_ENTREGA G
                     WHERE F.COD_GRUPO_CIA = '001'
                       AND F.COD_LOCAL = cCodLocal_in
                       AND G.NRO_RECEP = cNroRecepcion          
                       AND F.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                       AND F.COD_LOCAL = G.COD_LOCAL
                       AND F.NUM_NOTA_ES = G.NUM_NOTA_ES
                       AND F.NUM_ENTREGA = G.NUM_ENTREGA
                       AND F.SEC_GUIA_REM = G.SEC_GUIA_REM)
               AND E.COD_GRUPO_CIA = H.COD_GRUPO_CIA
               AND E.COD_PROD = H.COD_PROD
               and NVL(E.CANT_SEG_CONTEO, E.CANTIDAD) > 0
         );
      
          
        COMMIT;
  END;

  
  
  PROCEDURE INT_PED_FALTANTE(cCodGrupoCia_in IN CHAR) AS
    g_cNumProdQuiebre INTEGER := NULL;
    j                 INTEGER := 0;
    
    CURSOR curFactFaltTotal IS
      select distinct A.COD_LOCAL_SAP, FACTURA_OUT
        from ptoventa.aux_det_faltan a
       where a.fecha_proceso is  null      
       and  a.flg_completo = 'S'       
       order by 1, 2;
  
    CURSOR curProd(cCodLocalSap_in VARCHAR2, cFactOut_in in VARCHAR2) IS
      SELECT gt.cod_local_sap,
             gt.cod_codigo_sap,
             sum(gt.ctd) "CTD",
             '' as "NUM_LOTE",         
             gt.factura_out,
            GT.ID_ENTREGA,
             gt.rowid FILA
        from ptoventa.aux_det_faltan gt
       where gt.cod_local_sap = cCodLocalSap_in
         and gt.factura_out = cFactOut_in
         group by gt.cod_local_sap,
             gt.cod_codigo_sap,
             '',
             gt.factura_out,
             GT.ID_ENTREGA,gt.rowid ;
  
    v_rCurFact_Faltante curFactFaltTotal%ROWTYPE;
    v_rCurProd          curProd%ROWTYPE;
  
    v_nNumSecDoc NUMBER(10);  
    v_vIdUsu_in  VARCHAR2(200) := 'PCK_INT_FALT';
  
    nOrden integer := 0;
  
    g_cNumIntPedFALTANTE PBL_NUMERA.COD_NUMERA%TYPE := '055';
    nCantMalHomo number; 
  begin  
  
    SELECT LLAVE_TAB_GRAL
      INTO g_cNumProdQuiebre
      FROM PBL_TAB_GRAL
     WHERE ID_TAB_GRAL = '387';
  
    --- genera las interfaces aun por generar Archivos
  
    FOR v_rCurFact_Faltante IN curFactFaltTotal LOOP    
      j := 0;   

     SELECT count(1)
     into   nCantMalHomo
            from ptoventa.aux_det_faltan gt
           where gt.cod_local_sap = v_rCurFact_Faltante.Cod_Local_Sap
             and gt.factura_out = v_rCurFact_Faltante.Factura_Out
             and gt.cod_codigo_sap = 0;
             
    if nCantMalHomo = 0 then

      OPEN curProd(v_rCurFact_Faltante.Cod_Local_Sap,
                   v_rCurFact_Faltante.Factura_Out);
      LOOP
        FETCH curProd
          INTO v_rCurProd;
        EXIT WHEN curProd%NOTFOUND;      

      
        IF j = 0 THEN
          v_nNumSecDoc := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                           v_rCurFact_Faltante.Cod_Local_Sap,
                                                           g_cNumIntPedFALTANTE);
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                     v_rCurFact_Faltante.Cod_Local_Sap,
                                                     g_cNumIntPedFALTANTE,
                                                     v_vIdUsu_in);
        ELSIF j = g_cNumProdQuiebre THEN
          v_nNumSecDoc := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                           v_rCurFact_Faltante.Cod_Local_Sap,
                                                           g_cNumIntPedFALTANTE);
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                     v_rCurFact_Faltante.Cod_Local_Sap,
                                                     g_cNumIntPedFALTANTE,
                                                     v_vIdUsu_in);
          j := 0;
        END IF;
        j      := j + 1;
        nOrden := nOrden + 1;
        INSERT INTO PTOVENTA.INT_PED_FALTANTE
          (COD_GRUPO_CIA,
           COD_LOCAL,
           COD_SOLICITUD,
           FEC_PEDIDO,
           FEC_ENTREGA,
           IND_URGENCIA,
           COD_PROD,
           CANT_SOLICITADA,
           IND_PED_APROV,
           STK_SALDO,
           ID_ENTREGA,
           STK_LIBRE_UTIL,
           STK_BLOQUEADO,
           ORDEN_INT,
           IND_ORDEN_REP,
           MOTIVO_PEDIDO,
           NUM_FACT_QS,
           NUM_LOTE,FILA)
        VALUES
          (cCodGrupoCia_in,
           v_rCurFact_Faltante.Cod_Local_Sap,
           v_rCurFact_Faltante.Cod_Local_Sap ||
           Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNumSecDoc, 7, '0', 'I'),
           TRUNC(SYSDATE),
           TRUNC(SYSDATE),
           'S',
           v_rCurProd.Cod_Codigo_Sap,
           v_rCurProd.Ctd,
           'S',
           0,
           v_rCurProd.ID_ENTREGA, 
           NULL, -- v_rCurProd.STK_LIBRE_UTIL,
           NULL -- v_rCurProd.STK_BLOQUEADO
          ,
           nOrden,
           'S',
           '176',
           v_rCurFact_Faltante.Factura_Out,
           v_rCurProd.Num_Lote,
           v_rCurProd.FILA
           );
      
   
      END LOOP;
      CLOSE curProd;
    
      UPDATE PTOVENTA.aux_det_faltan S
         SET S.FECHA_PROCESO = SYSDATE
       WHERE S.COD_LOCAL_SAP = v_rCurFact_Faltante.Cod_Local_Sap
         AND S.FACTURA_OUT = v_rCurFact_Faltante.Factura_Out;
   /*  else 
       btl_sac_int_recep_ciega.int_envia_correo_informacion('001',
                                                       v_rCurFact_Faltante.Cod_Local_Sap,
                                                       'ERROR INT FALT REC CIEGA ',
                                                       'Error Int Faltante ',
                                                       'Productos homologados a CERO la Fact Sap  '|| v_rCurFact_Faltante.Factura_Out);*/
     end if;
    END LOOP;
    
    INT_GEN_TXT_FALTANTE(cCodGrupoCia_in);
    
  end;
  /****************************************************************************/

  /* ********************************************************************************* */
  PROCEDURE INT_GEN_TXT_FALTANTE(cCodGrupoCia_in IN CHAR) AS
    CURSOR curSolicitud(cCodLocal_in IN CHAR) IS
      SELECT DISTINCT PP.COD_SOLICITUD, pp.motivo_pedido, pp.num_fact_qs
        FROM INT_PED_FALTANTE PP
       WHERE PP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND PP.COD_LOCAL = cCodLocal_in
         AND PP.FEC_GENERACION_TXT IS NULL
       ORDER BY 1 ASC;
  
    CURSOR curResumen(vCodSolicitud_in IN CHAR, cCodLocal_in IN CHAR) IS
     select va.a||TRIM(TO_CHAR(fila * 100, '999999999999'))
            ||va.b  AS RESUMEN
     from   (  
              SELECT rownum fila,
                     '2|' || TRIM(RPAD(PP.COD_PROD, 18, ' ')) || '|' ||
                     TRIM(TO_CHAR(PP.CANT_SOLICITADA, '999999999999')) || '|' a,
                     '' --999999999999
                     || '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|' 
                     || '|' || '|' || '|' || '|' || '|' || '|' || '|'  b
                FROM INT_PED_FALTANTE PP
               WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND PP.COD_LOCAL = cCodLocal_in
                 AND PP.FEC_GENERACION_TXT IS NULL
                 AND PP.COD_SOLICITUD = vCodSolicitud_in
               --ORDER BY ORDEN_INT asc
             ) va;
     
  
    v_rCurResumen curResumen%ROWTYPE;
  
    v_vNombreArchivo VARCHAR2(100);
    v_nCant          INTEGER;
  
    v_Cabecera    VARCHAR2(3000) := '';
    v_ClasePedido CHAR(4) := 'ZFA3';
    v_Cliente     CHAR(6) := '10035'; --'352697';
    v_OrgVentas   CHAR(4) := '1170';
    v_Canal       CHAR(2) := '30'; --'10';
    v_Sector      CHAR(2) := '01';
    v_OrigePedido CHAR(4) := '0014';
    v_Dest        CHAR(10) := ''; --VA en Blanco
    v_OrdCompra   CHAR(10) := '';
  
    cCodLocal_in varchar2(10);
  
    v_gNombreDiretorio VARCHAR2(700) := 'DIR_INTERFACES';
  
  BEGIN
  
    -- GENERA TODOS LOS ARCHIVOS  
    for listGen in (SELECT distinct pp.cod_local/*, pp.cod_solicitud*/
                      FROM INT_PED_FALTANTE PP
                     WHERE PP.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND PP.FEC_GENERACION_TXT IS NULL
                      -- and   rownum < 50
                     ORDER BY 1 ASC) loop                     
                  
    
      cCodLocal_in := listGen.Cod_Local;
      --DBMS_OUTPUT.PUT_LINE('cCodLocal_in:' || cCodLocal_in);
      SELECT COUNT(*)
        INTO v_nCant
        FROM INT_PED_FALTANTE
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND fec_generacion_txt IS NULL
         AND NVL(fec_generacion_txt, TO_DATE('30000101', 'YYYYMMDD')) =
             TO_DATE('30000101', 'YYYYMMDD');
    
      v_Dest := FN_GET_COD_CLIENTE_SAP(cCodLocal_in);
      
      v_Cliente := FN_GET_CLIENTE(cCodLocal_in);
      
      --RAISE_APPLICATION_ERROR(-20000,v_nCant||';'||v_Cliente||';'||v_Dest);       
    
      IF v_nCant > 0 AND v_Cliente != '-01' 
        AND v_Dest  != '-01' AND v_Dest  != '-00' THEN        
        
        
        FOR c_Sol IN curSolicitud(cCodLocal_in) LOOP
          v_OrdCompra := c_Sol.Cod_Solicitud;
        
          --DBMS_OUTPUT.PUT_LINE('v_gNombreDiretorio:' || v_gNombreDiretorio);
        
          v_vNombreArchivo := 'FT' || cCodLocal_in ||
                              TO_CHAR(SYSDATE, 'yyyyMMdd') ||
                              TO_CHAR(SYSDATE, 'HH24MISS') || '.TXT';
        
          
        
          v_Cabecera := '1|' || trim(v_ClasePedido) || '|' || trim(v_Cliente) || '|' ||
                        trim(v_OrgVentas) || '|' || trim(v_Canal) || '|' || trim(v_Sector) || '|' ||
                        trim(v_OrigePedido) || '|' || trim(v_Dest) || '|' ||
                        --trim(cCodLocal_in) || trim(substr(v_OrdCompra, 4, 7)) --trim(v_OrdCompra)
                        trim(v_OrdCompra)
                        || '|' ||/* to_char(SYSDATE, 'yyyymmdd')*/ ''|| '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|' ||
                        trim(c_Sol.Motivo_Pedido) || '|' || trim(c_Sol.Num_Fact_Qs) || '|';
        
          ARCHIVO_TEXTO := UTL_FILE.FOPEN(TRIM(v_gNombreDiretorio),
                                          TRIM(v_vNombreArchivo),
                                          'W');
        
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO, v_Cabecera);
        
          FOR v_rCurResumen IN curResumen(c_Sol.COD_SOLICITUD, cCodLocal_in) LOOP
            UTL_FILE.PUT_LINE(ARCHIVO_TEXTO, v_rCurResumen.RESUMEN);
          END LOOP;
        
          if utl_file.is_open(ARCHIVO_TEXTO) = true then
        --DBMS_OUTPUT.put_line('cierra aaaa');
        begin
             UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
             exception
              when  others then
                null;
                end;
         --DBMS_OUTPUT.put_line('cierra');
    end if;  
        
          UPDATE INT_PED_FALTANTE
             SET DIR_RUTA_SAVE = v_gNombreDiretorio,
                 NAME_NEW_TXT  = TRIM(v_vNombreArchivo)
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND COD_LOCAL = cCodLocal_in
             AND FEC_GENERACION_TXT IS NULL
             AND COD_SOLICITUD = c_Sol.COD_SOLICITUD;
          --COMMIT;
        
          --MAIL DE EXITO DE INTERFACE DE INVENTARIO
        
          --pausa para generar el siguiente archivo
          DBMS_LOCK.sleep(1);
        END LOOP;
      END IF;
    
    end loop;

    

   -- GENERA EL OUTPUT DE LO GENERADO 
   
   FOR curListRuta IN(
             select COUNT(1) CTD,ase.DIR_RUTA_SAVE
             from (
                   SELECT distinct GR.DIR_RUTA_SAVE,gr.name_new_txt
                   FROM   INT_PED_FALTANTE GR
                   WHERE  GR.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND GR.FEC_GENERACION_TXT IS NULL
                  ) ase
              GROUP BY ase.DIR_RUTA_SAVE
              ORDER BY 2 ASC
          )LOOP
   NULL;
    
   v_vNombreArchivo := 'END_IN_V'|| TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      --DBMS_OUTPUT.put_line(curListRuta.DIR_RUTA_SAVE);     
      
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(curListRuta.DIR_RUTA_SAVE,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,curListRuta.CTD);         
    for curDetRuta in (
                         SELECT distinct  GR.NAME_NEW_TXT
                         FROM   INT_PED_FALTANTE GR
                         WHERE  GR.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND GR.FEC_GENERACION_TXT IS NULL
                         and GR.DIR_RUTA_SAVE = curListRuta.DIR_RUTA_SAVE
                         order by 1 asc
                      )loop
                      
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,curDetRuta.Name_New_Txt);
          
    end loop;
     if utl_file.is_open(ARCHIVO_TEXTO) = true then
        --DBMS_OUTPUT.put_line('cierra aaaa');
        begin
             UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
             exception
              when  others then
                null;
                end;
         --DBMS_OUTPUT.put_line('cierra');
    end if;   
    
    update INT_PED_FALTANTE se
    set    se.fec_generacion_txt = sysdate
    where  se.cod_grupo_cia = cCodGrupoCia_in
    and    se.fec_generacion_txt  IS NULL
    and    se.DIR_RUTA_SAVE = curListRuta.DIR_RUTA_SAVE;
    
   END LOOP;
   /*
    update INT_PED_FALTANTE se
    set    se.fec_generacion_txt = sysdate
    where  se.cod_grupo_cia = cCodGrupoCia_in
    and    se.fec_generacion_txt  IS NULL;
    */
    commit;
    --and    se.DIR_RUTA_SAVE = curListRuta.DIR_RUTA_SAVE;
    -- FIN DE GENERA TODOS LOS ARCHIVOS 
  END;
  /* ******************************************************************** */
  PROCEDURE INT_PED_SOBRANTE(cCodGrupoCia_in IN CHAR) AS
    g_cNumProdQuiebre INTEGER := NULL;
    j                 INTEGER := 0;
  
    CURSOR curFactFaltTotal IS
      select distinct Cod_Local_Sap,A.ID_ENTREGA,a.cod_local
        from PTOVENTA.AUX_DET_SOBRAN a
       where a.fecha_proceso is null
       and  a.flg_completo = 'S'     
       order by 1, 2;

    CURSOR curProd(cCodLocalSap_in VARCHAR2, cIdEntrega_in in VARCHAR2) IS
      SELECT gt.cod_local_sap,
             gt.cod_codigo_sap,
             gt.ctd,
             gt.num_lote,
             gt.factura_out,
             GT.ID_ENTREGA
        from PTOVENTA.AUX_DET_SOBRAN gt
       where gt.cod_local_sap = cCodLocalSap_in
         and gt.id_entrega = cIdEntrega_in;
  
    v_rCurFact_Faltante curFactFaltTotal%ROWTYPE;
    v_rCurProd          curProd%ROWTYPE;
  
    v_nNumSecDoc NUMBER(10);
    --v_cNumSecPed LGT_PED_REP_CAB.NUM_PED_REP%TYPE := NULL;
    v_vIdUsu_in  VARCHAR2(200) := 'PCK_INT_SOB';
  
    nOrden integer := 0;
  
    g_cNumIntPedFALTANTE PBL_NUMERA.COD_NUMERA%TYPE := '056';
    nProdSinLote number;
    nCantMalHomo number;
  begin
    --DBMS_OUTPUT.put_line(cCodGrupoCia_in);
  
    SELECT LLAVE_TAB_GRAL
      INTO g_cNumProdQuiebre
      FROM PBL_TAB_GRAL
     WHERE ID_TAB_GRAL = '387';
  
    --- genera las interfaces aun por generar Archivos
  
    FOR v_rCurFact_Faltante IN curFactFaltTotal LOOP
    
      j := 0;

      -- COMPLETA LOTE
      p_completa_LOTE(v_rCurFact_Faltante.Cod_Local,v_rCurFact_Faltante.Id_Entrega);

       SELECT count(1)
       into  nProdSinLote
        from PTOVENTA.AUX_DET_SOBRAN gt
       where gt.cod_local_sap = v_rCurFact_Faltante.Cod_Local_Sap
         and gt.id_entrega =  v_rCurFact_Faltante.Id_Entrega
         and gt.num_lote is null
         and gt.cod_codigo_sap in (
                                   select distinct a.material
                                   from   ptoventa.aux_mae_lote_qs@xe_000 a
                                   where  a.suj_lote = 'X'
                                  );
                                                  
     SELECT count(1)
     into   nCantMalHomo
            from PTOVENTA.AUX_DET_SOBRAN gt
           where gt.cod_local_sap = v_rCurFact_Faltante.Cod_Local_Sap
             and gt.factura_out = v_rCurFact_Faltante.Id_Entrega
             and gt.cod_codigo_sap = 0;
             
      -- no genera los que no tienen lote                             
	    if nProdSinLote = 0 and  nCantMalHomo = 0 then 
      
      begin
          
      OPEN curProd(v_rCurFact_Faltante.Cod_Local_Sap,
                   v_rCurFact_Faltante.Id_Entrega);
      LOOP
        FETCH curProd
          INTO v_rCurProd;
        EXIT WHEN curProd%NOTFOUND;

        IF j = 0 THEN
          v_nNumSecDoc := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                           v_rCurFact_Faltante.Cod_Local_Sap,
                                                           g_cNumIntPedFALTANTE);
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                     v_rCurFact_Faltante.Cod_Local_Sap,
                                                     g_cNumIntPedFALTANTE,
                                                     v_vIdUsu_in);
        ELSIF j = g_cNumProdQuiebre THEN
          v_nNumSecDoc := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                           v_rCurFact_Faltante.Cod_Local_Sap,
                                                           g_cNumIntPedFALTANTE);
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                     v_rCurFact_Faltante.Cod_Local_Sap,
                                                     g_cNumIntPedFALTANTE,
                                                     v_vIdUsu_in);
          j := 0;
        END IF;
        j      := j + 1;
        nOrden := nOrden + 1;
        INSERT INTO PTOVENTA.INT_PED_SOBRANTE
          (COD_GRUPO_CIA,
           COD_LOCAL,
           COD_SOLICITUD,
           FEC_PEDIDO,
           FEC_ENTREGA,
           IND_URGENCIA,
           COD_PROD,
           CANT_SOLICITADA,
           IND_PED_APROV,
           STK_SALDO,
           ID_ENTREGA,
           STK_LIBRE_UTIL,
           STK_BLOQUEADO,
           ORDEN_INT,
           IND_ORDEN_REP,
           MOTIVO_PEDIDO,
           NUM_FACT_QS,
           NUM_LOTE
           )
        VALUES
          (cCodGrupoCia_in,
           v_rCurFact_Faltante.Cod_Local_Sap,
           v_rCurFact_Faltante.Cod_Local_Sap ||
           Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNumSecDoc, 7, '0', 'I'),
           TRUNC(SYSDATE),
           TRUNC(SYSDATE),
           'S',
           v_rCurProd.Cod_Codigo_Sap,
           v_rCurProd.Ctd,
           'S',
           0,
           v_rCurProd.ID_ENTREGA, --AGREGADO POR DVELIZ 24.09.08
           NULL, -- v_rCurProd.STK_LIBRE_UTIL,
           NULL -- v_rCurProd.STK_BLOQUEADO
          ,
           nOrden,
           'S',
           '',
           v_rCurProd.Factura_Out,
           v_rCurProd.Num_Lote);
      
       -- v_cNumSecPed := v_rCurProd.Factura_Out;
      
      END LOOP;
      CLOSE curProd;
    
      UPDATE PTOVENTA.Aux_Det_Sobran  S
         SET S.FECHA_PROCESO = SYSDATE
       WHERE S.COD_LOCAL_SAP = v_rCurFact_Faltante.Cod_Local_Sap
         AND S.ID_ENTREGA = v_rCurFact_Faltante.Id_Entrega;
     exception 
     when others then  
        CLOSE curProd;      
       /* btl_sac_int_recep_ciega.int_envia_correo_informacion('001',
                                                 v_rCurFact_Faltante.Cod_Local_Sap,
                                                 'ERROR INT SOBRANTE REC CIEGA ',
                                                 'Error Int Faltante ',
                                                 ' Entrega  '|| v_rCurFact_Faltante.Id_Entrega||' '||sqlerrm);*/
     end;    
     /*else 
              btl_sac_int_recep_ciega.int_envia_correo_informacion('001',
                                                       v_rCurFact_Faltante.Cod_Local_Sap,
                                                       'ERROR INT SOBRANTE REC CIEGA ',
                                                       'Error Int Faltante ',
                                                       'Productos homologados a CERO o Sin Lote >>  Entrega  '|| v_rCurFact_Faltante.Id_Entrega);*/
         
     end if;
    END LOOP;
    
    INT_GEN_TXT_SOBRANTE(cCodGrupoCia_in);
  end;
  /****************************************************************************/

  /* ********************************************************************************* */
  PROCEDURE INT_GEN_TXT_SOBRANTE(cCodGrupoCia_in IN CHAR) AS
    CURSOR curSolicitud(cCodLocal_in IN CHAR) IS
      SELECT DISTINCT PP.COD_SOLICITUD--, pp.motivo_pedido, pp.num_fact_qs
        FROM INT_PED_SOBRANTE PP
       WHERE PP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND PP.COD_LOCAL = cCodLocal_in
         AND PP.FEC_GENERACION_TXT IS NULL
       ORDER BY 1 ASC;
  
    CURSOR curResumen(vCodSolicitud_in IN CHAR, cCodLocal_in IN CHAR) IS
    select  vas.a||
            TRIM(TO_CHAR(vas.fila * 100, '999999999999'))||
            vas.b	 AS RESUMEN
    from  (
      SELECT '2|' || TRIM(RPAD(PP.COD_PROD, 18, ' ')) || '|' ||
             TRIM(TO_CHAR(PP.CANT_SOLICITADA, '999999999999')) || '|' a,
             rownum fila,
             '' 
             ||'|'||pp.num_lote||'|' || 
             '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|'  b
        FROM INT_PED_SOBRANTE PP
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND PP.COD_LOCAL = cCodLocal_in
         AND PP.FEC_GENERACION_TXT IS NULL
         AND PP.COD_SOLICITUD = vCodSolicitud_in
      -- ORDER BY ORDEN_INT asc
       )vas;
  
    v_rCurResumen curResumen%ROWTYPE;
  
    v_vNombreArchivo VARCHAR2(100);
    v_nCant          INTEGER;
  
    v_Cabecera    VARCHAR2(3000) := '';
    v_ClasePedido CHAR(4) := 'ZFA4';
    v_Cliente     CHAR(6) := '10035'; --'352697';
    v_OrgVentas   CHAR(4) := '1170';
    v_Canal       CHAR(2) := '30'; --'10';
    v_Sector      CHAR(2) := '01';
    v_OrigePedido CHAR(4) := '0014';
    v_Dest        CHAR(10) := ''; --VA en Blanco
    v_OrdCompra   CHAR(10) := '';
  
    cCodLocal_in varchar2(10);
  
    v_gNombreDiretorio VARCHAR2(700) := 'DIR_INTERFACES';
  
  BEGIN
  
    -- GENERA TODOS LOS ARCHIVOS  
    for listGen in (SELECT distinct pp.cod_local/*, pp.cod_solicitud*/
                      FROM INT_PED_SOBRANTE PP
                     WHERE PP.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND PP.FEC_GENERACION_TXT IS NULL
                     ORDER BY 1 ASC) loop
    
      cCodLocal_in := listGen.Cod_Local;
      --DBMS_OUTPUT.PUT_LINE('cCodLocal_in:' || cCodLocal_in);
      SELECT COUNT(*)
        INTO v_nCant
        FROM INT_PED_SOBRANTE
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND fec_generacion_txt IS NULL
         AND NVL(fec_generacion_txt, TO_DATE('30000101', 'YYYYMMDD')) =
             TO_DATE('30000101', 'YYYYMMDD');
    
      v_Dest := FN_GET_COD_CLIENTE_SAP(cCodLocal_in);
      v_Cliente := FN_GET_CLIENTE(cCodLocal_in);
              
      --IF v_nCant > 0 THEN
      IF v_nCant > 0 AND v_Cliente != '-01' 
        AND v_Dest  != '-01' AND v_Dest  != '-00' THEN        
      
        FOR c_Sol IN curSolicitud(cCodLocal_in) LOOP
          v_OrdCompra := c_Sol.Cod_Solicitud;
        
          --DBMS_OUTPUT.PUT_LINE('v_gNombreDiretorio:' || v_gNombreDiretorio);
        
          v_vNombreArchivo := 'SB' || cCodLocal_in ||
                              TO_CHAR(SYSDATE, 'yyyyMMdd') ||
                              TO_CHAR(SYSDATE, 'HH24MISS') || '.TXT';
        

        
          v_Cabecera := '1|' || trim(v_ClasePedido) || '|' || trim(v_Cliente) || '|' ||
                        trim(v_OrgVentas) || '|' || trim(v_Canal) || '|' || trim(v_Sector) || '|' ||
                        trim(v_OrigePedido) || '|' || trim(v_Dest) || '|' ||
                        -- trim(cCodLocal_in) || trim(substr(v_OrdCompra, 4, 7)) --
                        trim(v_OrdCompra)
                        || '|' ||/* to_char(SYSDATE, 'yyyymmdd')*/ ''|| '|' || '|' || '|' || '|' || '|' || '|' || '|' || '|' ||
                        '' || '|' || '' || '|';
        
          ARCHIVO_TEXTO := UTL_FILE.FOPEN(TRIM(v_gNombreDiretorio),
                                          TRIM(v_vNombreArchivo),
                                          'W');
        
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO, v_Cabecera);
        
          FOR v_rCurResumen IN curResumen(c_Sol.COD_SOLICITUD, cCodLocal_in) LOOP
            UTL_FILE.PUT_LINE(ARCHIVO_TEXTO, v_rCurResumen.RESUMEN);
          END LOOP;
        
          
          --UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
          if utl_file.is_open(ARCHIVO_TEXTO) = true then
            --DBMS_OUTPUT.put_line('cierra aaaa');
            begin
            begin
             UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
             exception
              when  others then
                null;
                end;
             exception
              when  others then
                null;
                end;
             --DBMS_OUTPUT.put_line('cierra');
          end if;
          
        
          UPDATE INT_PED_SOBRANTE
             SET DIR_RUTA_SAVE = v_gNombreDiretorio,
                 NAME_NEW_TXT  = TRIM(v_vNombreArchivo)--,
                 -- fec_generacion_txt = sysdate
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND COD_LOCAL = cCodLocal_in
             AND FEC_GENERACION_TXT IS NULL
             AND COD_SOLICITUD = c_Sol.COD_SOLICITUD;
          COMMIT;
        
          --MAIL DE EXITO DE INTERFACE DE INVENTARIO
        
          --pausa para generar el siguiente archivo
          DBMS_LOCK.sleep(1);
        END LOOP;
      END IF;
    
    end loop;
    
    commit;
  
    -- FIN DE GENERA TODOS LOS ARCHIVOS 


   -- GENERA EL OUTPUT DE LO GENERADO 
   
   FOR curListRuta IN(
           select COUNT(1) CTD,ase.DIR_RUTA_SAVE
           from (
                 SELECT distinct GR.DIR_RUTA_SAVE,gr.name_new_txt
                 FROM   INT_PED_SOBRANTE GR
                 WHERE  GR.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND GR.FEC_GENERACION_TXT IS NULL
                ) ase
            GROUP BY ase.DIR_RUTA_SAVE
            ORDER BY 2 ASC
          )LOOP
   NULL;
    
   v_vNombreArchivo := 'END_IN_V'|| TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      --DBMS_OUTPUT.put_line(curListRuta.DIR_RUTA_SAVE);
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(curListRuta.DIR_RUTA_SAVE,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,curListRuta.CTD);         
    for curDetRuta in (
                         SELECT distinct  GR.NAME_NEW_TXT
                         FROM   INT_PED_SOBRANTE GR
                         WHERE  GR.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND GR.FEC_GENERACION_TXT IS NULL
                         and GR.DIR_RUTA_SAVE = curListRuta.DIR_RUTA_SAVE
                         order by 1 asc
                      )loop
                      
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,curDetRuta.Name_New_Txt);
          
    end loop;
 if utl_file.is_open(ARCHIVO_TEXTO) = true then
        --DBMS_OUTPUT.put_line('cierra aaaa');
        begin
             UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
             exception
              when  others then
                null;
                end;
         --DBMS_OUTPUT.put_line('cierra');
    end if;      
    
    update INT_PED_SOBRANTE se
    set    se.fec_generacion_txt = sysdate
    where  se.cod_grupo_cia = cCodGrupoCia_in
    and    se.fec_generacion_txt  IS NULL
    and    se.DIR_RUTA_SAVE = curListRuta.DIR_RUTA_SAVE;
    
   END LOOP;
   
   
   
   
    
    commit;
   
    -- FIN DE GENERA TODOS LOS ARCHIVOS     
  END;
  /* ******************************************************************** */  
  FUNCTION FN_GET_COD_CLIENTE_SAP(COD_LOCAL_IN IN CHAR) RETURN VARCHAR2 IS
    v_rpta VARCHAR2(50) := '';
  BEGIN
    BEGIN
      SELECT NVL(L.COD_DESTINO_REP, '-00')
        INTO v_rpta
        FROM PTOVENTA.MAE_CLIENTE_DEST_LOCAL L
      --FROM AUX_MAE_LOCAL L
       WHERE L.COD_LOCAL = COD_LOCAL_IN; --14374
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_rpta := '-01';
    END;
    RETURN v_rpta;
  END;
  /* ***************************************************** */
  FUNCTION FN_GET_CLIENTE(COD_LOCAL_IN IN CHAR) RETURN VARCHAR2 IS
    v_rpta VARCHAR2(50) := '';
  BEGIN
    BEGIN            
          SELECT decode(l.COD_CIA,
            '003',
            '10034',
            --'BTL_SAC',
            '004',
            '10035',
            --'BTL_AMAZONIA',
            'X') 
            INTO v_rpta
            FROM PBL_LOCAL l  WHERE COD_LOCAL=COD_LOCAL_IN;     
         
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_rpta := '-01';
    END;
  
    IF v_rpta = 'X' THEN
      --DBMS_OUTPUT.put_line('---error!!');
      v_rpta := '-01';
    END IF;
  
    RETURN v_rpta;
  END;
  /*********************************************************************************** */
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         vAsunto_in      IN VARCHAR2,
                                         vTitulo_in      IN VARCHAR2,
                                         vMensaje_in     IN VARCHAR2) AS
  
    --ReceiverAddress   VARCHAR2(300) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTERFACE;
    CCReceiverAddress VARCHAR2(120) := NULL;
  
    mesg_body VARCHAR2(32767);
  
    v_vDescLocal VARCHAR2(120);
  BEGIN
    select DESC_CORTA_LOCAL INTO v_vDescLocal
    from pbl_local       
    where cod_local=cCodLocal_in;

  
    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>';
  
    FARMA_EMAIL.envia_correo(v_vDescLocal ||
                             FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             'dubilluz@mifarma.com.pe'||','||
                             'pmiguel@mifarma.com.pe'||','||
                             'pyovera@mifarma.com.pe',
                             --'DUBILLUZ',
                             vAsunto_in || v_vDescLocal, --'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in, --'EXITO',
                             mesg_body,
                             'dflores@mifarma.com.pe'||','||
                             'jmelgar@mifarma.com.pe'||'',
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);
  
  END;

/*********************************************************************************** */
 PROCEDURE p_completa_LOTE(cCodLocal_in IN CHAR,cEntrega in char) AS
 
 BEGIN
   begin
 delete TMP_LOTE_PROD;
  insert into TMP_LOTE_PROD
    (material, lote, fec_crea, orden)
    select material, lote, fec_crea, orden
    
      from (select m.material,
                   m.lote,
                   m.fec_crea,
                   RANK() OVER(PARTITION BY m.material ORDER BY m.fec_crea desc, m.lote desc) orden
              from ptoventa.aux_mae_lote_qs@xe_000 m
             where m.suj_lote = 'X'             
               and m.material in
                   (
                    SELECT gt.cod_codigo_sap
                      from PTOVENTA.AUX_DET_SOBRAN gt
                     where gt.cod_local=cCodLocal_in
                     and gt.id_entrega=cEntrega                     
                     and gt.num_lote is null
                     and gt.cod_codigo_sap in
                     (select distinct a.material
                            from ptoventa.aux_mae_lote_qs@xe_000 a
                      where a.suj_lote = 'X')                    
                   )
           )
     where orden = 1;

    update PTOVENTA.AUX_DET_SOBRAN gt
       set gt.num_lote =
           (select g.lote
              from ptoventa.TMP_LOTE_PROD g
             where g.lote is not null
               and g.material = gt.cod_codigo_sap)
     where gt.cod_local=cCodLocal_in
       and gt.id_entrega =cEntrega         
       and gt.num_lote is null
       and gt.cod_codigo_sap in
           (select distinct a.material
              from ptoventa.aux_mae_lote_qs@xe_000 a
             where a.suj_lote = 'X')
       and exists (select g.lote
              from PTOVENTA.TMP_LOTE_PROD g
             where g.lote is not null
               and g.material = gt.cod_codigo_sap);
     commit;
   exception
    when others then
    rollback;
    end;
                          
 
 END;

end;

/
