CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_INT_PED_REP" is

  g_cNumIntPed PBL_NUMERA.COD_NUMERA%TYPE := '022';
  g_cNumProdQuiebre INTEGER := 450;
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';

  --Descripcion: Obtiene el pedido de reposicion a ser enviado.
  --Fecha       Usuario		Comentario
  --05/04/2006  ERIOS    	Creacion
  PROCEDURE INT_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  vFecProceso_in IN VARCHAR2, cIndUrgencia_in IN CHAR DEFAULT 'N',
  cIndPedAprov_in IN CHAR DEFAULT 'N');

  --Descripcion: Genera archivo de Interface Rep Ped.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS     	Creacion
  PROCEDURE INT_GET_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Fecha       Usuario		Comentario
  --10/07/2006  ERIOS    	Creacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);

end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_INT_PED_REP" is
  /* 03/09/2007 ERIOS Pedidos de locales, que se generan en Matriz */
  /****************************************************************************/
  PROCEDURE INT_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  vFecProceso_in IN VARCHAR2, cIndUrgencia_in IN CHAR DEFAULT 'N',
  cIndPedAprov_in IN CHAR DEFAULT 'N')
  AS
    --Obtiene los grupos de reposicion
    CURSOR curGrupoRep IS
    SELECT COD_GRUPO_REP
    FROM LGT_GRUPO_REP
    ORDER BY 1;
    v_rCurGrupoRep curGrupoRep%ROWTYPE;
    --Obtiene los productos por grupo de reposición
    CURSOR curProd(cCodGrupoRep_in IN CHAR) IS
    SELECT D.COD_PROD,
            D.CANT_SOLICITADA,
              P.COD_GRUPO_QS,
              C.NUM_PED_REP,
              P.COD_GRUPO_REP,
              V_SALDO.STK_FISICO
    FROM LGT_PED_REP_CAB C, LGT_PED_REP_DET D, LGT_PROD P,
         (
         SELECT COD_PROD,STK_FISICO
         FROM DELIVERY.LGT_PROD_LOCAL@XE_DEL_999
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
               AND COD_LOCAL = '009'
               --AND STK_FISICO > 0
         ) V_SALDO
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.NUM_PED_REP =
              (SELECT MAX(NUM_PED_REP) FROM LGT_PED_REP_CAB
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                      AND COD_LOCAL = cCodLocal_in
                      AND TO_CHAR(FEC_CREA_PED_REP_CAB,'dd/MM/yyyy') = vFecProceso_in)
          AND C.EST_PED_REP = 'E'
          AND D.CANT_SOLICITADA > 0
          AND P.EST_PROD = 'A'
          AND C.FEC_PROCESO_SAP IS NULL
          AND P.COD_GRUPO_REP = cCodGrupoRep_in
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_REP = D.NUM_PED_REP
          AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND D.COD_PROD = P.COD_PROD
          AND D.COD_PROD = V_SALDO.COD_PROD
    ORDER BY P.COD_GRUPO_QS,P.DESC_PROD,P.DESC_UNID_PRESENT;
    v_rCurProd curProd%ROWTYPE;

    j INTEGER:=0;
    jj INTEGER:=0;

    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_cNumSecPed LGT_PED_REP_CAB.NUM_PED_REP%TYPE := NULL;
    v_vIdUsu_in VARCHAR2(15) := 'PCK_INT_PEDREP';
  BEGIN

    FOR v_rCurGrupoRep IN curGrupoRep
    LOOP
      j := 0;
      OPEN curProd(v_rCurGrupoRep.COD_GRUPO_REP);
      --OPEN curProd;
      --DBMS_OUTPUT.PUT_LINE(v_rCurGrupo.COD_GRUPO_QS);
      LOOP
        FETCH curProd INTO v_rCurProd;
      EXIT WHEN curProd%NOTFOUND;
        IF j = 0 THEN
          v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntPed);
          --ACTUALIZAR NUMERACION
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntPed,v_vIdUsu_in);
        ELSIF j = g_cNumProdQuiebre THEN
          v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntPed);
          --ACTUALIZAR NUMERACION
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntPed,v_vIdUsu_in);
          j := 0;
        END IF;
        j := j+1;
        --DBMS_OUTPUT.PUT_LINE(cCodGrupoCia_in||','||cCodLocal_in||','||i||','||v_rCurGrupo.COD_GRUPO_QS||','|| v_rCurProd.COD_PROD||','||v_rCurProd.CANT_SOLICITADA);
        INSERT INTO INT_PEDIDO_REP(COD_GRUPO_CIA	,
                                  COD_LOCAL	,
                                  COD_SOLICITUD	,
                                  COD_GRUPO_QS	,
                                  FEC_PEDIDO	,
                                  FEC_ENTREGA	,
                                  IND_URGENCIA	,
                                  COD_PROD	,
                                  CANT_SOLICITADA	,
                                  IND_PED_APROV,
                                  STK_SALDO)
        VALUES(cCodGrupoCia_in,
              cCodLocal_in,
              Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNumSecDoc,10,'0','I'),
              v_rCurProd.COD_GRUPO_QS,
              SYSDATE,
              SYSDATE+1,
              DECODE(cIndUrgencia_in,'S','X','N',' '),
              v_rCurProd.COD_PROD,
              v_rCurProd.CANT_SOLICITADA,
              DECODE(cIndPedAprov_in,'S','X','N',' '),
              v_rCurProd.STK_FISICO);

        v_cNumSecPed := v_rCurProd.NUM_PED_REP;
      END LOOP;
      CLOSE curProd;

      jj := jj + j;
    END LOOP;

      IF jj = 0 THEN
        RAISE_APPLICATION_ERROR(-20051,'NO HAY INFORMACION QUE ENVIAR. ESTO PUEDE DEBERSE A QUE YA GENERO EL PEDIDO.');
      END IF;

      --ACTUALIZAR CABECERA
      UPDATE LGT_PED_REP_CAB SET USU_MOD_PED_REP_CAB = v_vIdUsu_in ,FEC_MOD_PED_REP_CAB = SYSDATE,
            FEC_PROCESO_SAP = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_REP = v_cNumSecPed;

      COMMIT;

      --AGREGADO 18/08/2006
      --MODIFICADO 28/12/2006: LOS DATOS SE GUARDAN EN EL DETALLE.
      /*DELETE FROM TMP_LGT_PROD_LOCAL_REP;

      INSERT INTO TMP_LGT_PROD_LOCAL_REP(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
                                          CANT_MIN_STK,CANT_MAX_STK,CANT_SUG,
                                          CANT_SOL,CANT_ROT,CANT_TRANSITO,
                                          CANT_DIA_ROT,NUM_DIAS,STK_FISICO,
                                          VAL_FRAC_LOCAL,CANT_EXHIB,
                                          USU_CREA_PROD_LOCAL_REP,
                                          FEC_CREA_PROD_LOCAL_REP,
                                          USU_MOD_PROD_LOCAL_REP,
                                          FEC_MOD_PROD_LOCAL_REP,
                                          TIPO	)
      SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
              CANT_MIN_STK,CANT_MAX_STK,CANT_SUG,
              CANT_SOL,CANT_ROT,CANT_TRANSITO,
              CANT_DIA_ROT,NUM_DIAS,STK_FISICO,
              VAL_FRAC_LOCAL,CANT_EXHIB,
              USU_CREA_PROD_LOCAL_REP,
              FEC_CREA_PROD_LOCAL_REP,
              USU_MOD_PROD_LOCAL_REP,
              FEC_MOD_PROD_LOCAL_REP,
              TIPO
      FROM LGT_PROD_LOCAL_REP
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

      COMMIT;*/

      INT_GET_RESUMEN_PED_REP(cCodGrupoCia_in, cCodLocal_in);

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --MAIL DE ERROR DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE PEDIDO REPOSICION PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');

  END;
  /****************************************************************************/
  PROCEDURE INT_GET_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    CURSOR curResumen IS
    SELECT COD_LOCAL||
          Farma_Utility.COMPLETAR_CON_SIMBOLO(COD_SOLICITUD,10,'0','I')||
          TO_CHAR(FEC_PEDIDO,'yyyyMMdd')||
          TO_CHAR(FEC_ENTREGA,'yyyyMMdd')||
          IND_URGENCIA||
          RPAD(COD_PROD,18,' ')||
          TO_CHAR(CANT_SOLICITADA,'999999999999')||--999999999999
          TRIM(TO_CHAR(MARGEN,'0.0000'))||
          TO_CHAR(ROTACION,'9999999990.0')||
          IND_PED_APROV
          AS RESUMEN
    FROM INT_PEDIDO_REP
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          --AND TO_CHAR(FEC_PEDIDO,'dd/MM/yyyy') = vFecProceso_in
          AND FEC_GENERACION IS NULL
          AND STK_SALDO > 0
     ORDER BY Farma_Utility.COMPLETAR_CON_SIMBOLO(COD_SOLICITUD,10,'0','I');
    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;
  BEGIN

    SELECT COUNT(*) INTO v_nCant
    FROM INT_PEDIDO_REP
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          --AND TO_CHAR(FEC_PEDIDO,'dd/MM/yyyy') = vFecProceso_in
          AND FEC_GENERACION IS NULL
          ;

    IF v_nCant > 0 THEN
      v_vNombreArchivo := 'SR'||cCodLocal_in||TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));

      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

      UPDATE INT_PEDIDO_REP
      SET FEC_GENERACION = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_GENERACION IS NULL
            AND STK_SALDO > 0;
      COMMIT;
      --MAIL DE EXITO DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE PEDIDO REPOSICON EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||SYSDATE||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B>');
    END IF;

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE PEDIDO REPOSICION.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;

  /***************************************************************************/
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTERFACE;
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(32767);

    v_vDescLocal VARCHAR2(120);
  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
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
                          true);

  END;

  /****************************************************************************/

end ;
/

