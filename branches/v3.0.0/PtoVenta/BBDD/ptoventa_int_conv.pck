CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_INT_CONV" is

  --ESTADO_ACTIVO		  CHAR(1):='A';
	--ESTADO_INACTIVO		CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';
	POS_INICIO		    CHAR(1):='I';

  TIP_MONEDA_SOLES CHAR(2) := '01';
  TIP_MONEDA_DOLARES CHAR(2) := '02';
  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
  ARCHIVO_TEXTO      UTL_FILE.FILE_TYPE;

  C_C_ESTADO_ACTIVO           CHAR(1) := 'A';
  C_C_ESTADO_INACTIVO         CHAR(1) := 'I';

  C_COD_GRUPO_CIA             CHAR(3) := '001';
  C_COD_LOCAL                 CHAR(3) := '009';
  C_COD_CONVENIO              CHAR(10):= '0000000001';

  --vFechaActual                DATE    := SYSDATE;
  vPrefijo                    CHAR(4) := 'CONV';
  vSumaDia                    NUMBER  := 0;
  vCantFilasAfect             NUMBER  := 0;

  C_COD_NUMERA_COD_CLIENTE    CHAR(3) := '032';
  vNombreDirectorio           VARCHAR2(15) := 'DIR_SAP_EMP';
  vNombreArchivo              VARCHAR2(50) := 'Log_Carga_Trab_QS_'||
                                              TO_CHAR(SYSDATE,'dd')||'-'||
                                              TO_CHAR(SYSDATE,'mm')||'-'||
                                              TO_CHAR(SYSDATE,'yyyy')||'.txt';

 TYPE FarmaCursor IS REF CURSOR;

 PROCEDURE INT_GRABA_INTERFCAE_CONV (cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCodTrabConv_in IN CHAR,
                                     cCodConvenio_in IN CHAR,
                                     cValImporte_in  IN CHAR,
                                     cNomTrab_in     IN CHAR,
                                     cUsuCrea_in     IN CHAR);

/* PROCEDURE INT_EJECUTA_CONVENIOS(cCodGrupoCia_in IN CHAR,
                                 cCodConvenio_in IN CHAR,
                                 cDiaInicio_in IN CHAR,
                                 cDiaFin_in IN CHAR,
                                 cFechaInicio_in IN CHAR,
                                 cFechaFin_in IN CHAR);
*/
/* PROCEDURE INT_EJECUTA_CIERRE_CONV(cCodGrupoCia_in IN CHAR);*/

 FUNCTION  INT_GENERA_ARCHIVO(cCodGrupoCia_in IN CHAR,
                              cCodEmpresa_in IN CHAR)
 RETURN VARCHAR2;

 PROCEDURE INT_ACTUALIZA_PROCESO;

 PROCEDURE INT_ENVIA_CORREO_INFORMACION(vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCodGrupoCia_in IN CHAR,
                                        cCodEmpresa_in IN CHAR);

/* PROCEDURE INT_GENERA_DETALLE_COMP(cCodGrupoCia_in IN CHAR,
                                   cCodConvenio_in IN CHAR,
                                   cDiaInicio_in IN CHAR,
                                   cDiaFin_in IN CHAR,
                                   cFechaInicio_in IN CHAR,
                                   cFechaFin_in IN CHAR);

*/----------------------------------------------------------------------


  --Descripcion: Actualiza la relacion trabajador- convenio
  --Fecha       Usuario		Comentario
  --16/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_CARGA_TRAB_QS;*/

  --Descripcion: Carga el archivo enviado por QS
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INSERTA_AUX_TRAB_QS;*/

  --Descripcion: Carga el maestro de trabajadores QS con los nuevos trabajadores
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INSERTA_TRAB_QS;*/

  --Descripcion: Actualiza el estado del trabajador, en caso no se encuentre en el archivo
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INACTIVA_TRAB_QS;--INT_ACTUALIZA_ESTADO_TRAB_QS;*/

  --Descripcion: Inserta la relacion trabajador - convenio
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INSERTA_CON_CLI_CONV(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR);*/

  --Descripcion: Actualiza el estado del trabajador en la tabla TMP_CON_CLI_CONV
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_INACTIVA_TRAB_CONV;*/

  --Descripcion: Obtiene un cliente por el DNI
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
  --FUNCTION INT_OBTIENE_CLIENTE_X_DNI(cDni_in    IN CHAR) RETURN FarmaCursor;

  --Descripcion: Inserta en el maestro de trabajadores
  --Fecha       Usuario		Comentario
  --17/04/2007  LREQUE     Creacion
/*  FUNCTION INT_INSERTA_CLIENTE_TRAB(cCodGrupoCia_in      IN CHAR,
                                    cCodLocal_in         IN CHAR,
                                    cNomCompletoCli_in   IN CHAR,
                                    cTipoDoc_in          IN CHAR,
                                    cNumDoc_in           IN CHAR) RETURN CHAR;*/

  --Descripcion: Activa los trabajadores de QS
  --Fecha       Usuario		Comentario
  --18/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_ACTIVA_TRAB_QS;*/

  --Descripcion: Inactiva los trabajadores de QS
  --Fecha       Usuario		Comentario
  --18/04/2007  LREQUE     Creacion
/*  PROCEDURE INT_ACTIVA_TRAB_CONV;*/

  --Descripcion: GRABA CONVENIOS POR EMPRESAS
  --Fecha       Usuario		Comentario
  --09/05/2007  PAULO     Creacion
   PROCEDURE INT_EJECUTA_CONVENIOS_EMPR(cCodGrupoCia_in IN CHAR,
                                        cCodConvenio_in IN CHAR,
                                        cFechaInicio_in IN CHAR,
                                        cFechaFin_in IN CHAR);
  --Descripcion: EJECUTA CONVENIOR PARA EMPRESAS
  --Fecha       Usuario		Comentario
  --09/05/2007  PAULO     Creacion
  PROCEDURE INT_EJECUTA_CIERRE_CONV_EMPR(cCodGrupoCia_in IN CHAR);

  --Descripcion: GENERA ARCHIVO DE COMPROBANTES
  --Fecha       Usuario		Comentario
  --09/05/2007  PAULO     Creacion
  PROCEDURE INT_GENERA_DETALLE_COMP_EMPR(cCodGrupoCia_in IN CHAR,
                                         cCodConvenio_in IN CHAR,
                                         cFechaInicio_in IN CHAR,
                                         cFechaFin_in IN CHAR);

  --Descripcion: OBTIENE LA FECHA DE INICIO DEL CICLO DE FACTURACION PARA EL CONVENIO
  --Fecha       Usuario		Comentario
  --04/07/2007  PAULO     Creacion
  FUNCTION INT_OBTIENE_FECHA_INICIO_FACT(cCodGrupoCia_in IN CHAR,
                                         cCodEmpresa_in IN CHAR)
  RETURN CHAR;

  --Descripcion: OBTIENE LA FECHA DE FIN DEL CICLO DE FACTURACION PARA EL CONVENIO
  --Fecha       Usuario		Comentario
  --04/07/2007  PAULO     Creacion
  FUNCTION INT_OBTIENE_FECHA_FIN_FACT(cCodGrupoCia_in IN CHAR,
                                      cCodEmpresa_in IN CHAR)
  RETURN CHAR;

  --Descripcion: OBTIENE los numeros de comprobantes para los pedidos
  --Fecha       Usuario		Comentario
  --24/09/2007  PAULO     Creacion
    FUNCTION INT_OBTIENE_COMP_PEDIDO (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNumPedVta IN CHAR)
   RETURN VARCHAR2;

  --Descripcion: MUESTRA LOS PEDIDOS DE LOS COLABORADORES CON SUS CREDITOS UTILIZADOS Y LOS COMPROBANTES CONCATENADOS
  --Fecha       Usuario		Comentario
  --24/09/2007  PAULO     Creacion
  PROCEDURE INT_CREDITOS_PEDIDO(cCodGrupoCia_in IN CHAR,
                                cCodConvenio_in IN CHAR,
                                cFechaInicio_in IN CHAR,
                                cFechaFin_in IN CHAR);




end PTOVENTA_INT_CONV;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_INT_CONV" IS

 PROCEDURE INT_GRABA_INTERFCAE_CONV (cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCodTrabConv_in IN CHAR,
                                     cCodConvenio_in IN CHAR,
                                     cValImporte_in  IN CHAR,
                                     cNomTrab_in     IN CHAR,
                                     cUsuCrea_in     IN CHAR)
 IS
   BEGIN
        INSERT INTO INT_CONVENIO (COD_GRUPO_CIA,
                                  COD_LOCAL,
                                  COD_TRAB_CONV,
                                  COD_CONVENIO,
                                  VAL_IMPORTE,
                                  Nom_Trab,
                                  Usu_Crea_Int_Conv)
                    VALUES        (cCodGrupoCia_in,
                                   cCodLocal_in,
                                   cCodTrabConv_in,
                                   cCodConvenio_in,
                                   cValImporte_in,
                                   cNomTrab_in,
                                   cUsuCrea_in) ;
   END;

   /****************************************************************************/
/*   PROCEDURE INT_EJECUTA_CONVENIOS(cCodGrupoCia_in IN CHAR,
                                   cCodConvenio_in IN CHAR,
                                   cDiaInicio_in IN CHAR,
                                   cDiaFin_in IN CHAR,
                                   cFechaInicio_in IN CHAR,
                                   cFechaFin_in IN CHAR)
   AS
     CURSOR curConvenios IS
        SELECT CAB.COD_GRUPO_CIA COD_GRUPO_CIA ,
               CAB.COD_LOCAL COD_LOCAL ,
               CLI_CONV.COD_TRAB_CONV COD_TRAB,
               CLI_CONV.COD_CONVENIO COD_CONVENIO,
               TO_CHAR(SUM(CAB.VAL_NETO_PED_VTA) * 100) NETO,
               SUBSTR(NVL(TRIM(MAE_CLI.NOM_COMPLETO),NVL(MAE_CLI.APE_PAT_CLI,' ') ||' '|| NVL(MAE_CLI.APE_MAT_CLI,' ') ||' '|| NVL(MAE_CLI.NOM_CLI,' ')),0,35)NOMBRE,
               'SISTEMAS' USUARIO
        FROM   VTA_PEDIDO_VTA_CAB CAB,
               CON_PED_VTA_CLI CON_PED,
               CON_CLI_CONV CLI_CONV,
               CON_MAE_CLIENTE MAE_CLI
        WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CAB.IND_PED_CONVENIO = INDICADOR_SI
        AND    CAB.IND_PEDIDO_ANUL = INDICADOR_NO
        AND    CAB.EST_PED_VTA = 'C'
        AND    CAB.SEC_MOV_CAJA IS NOT NULL
        AND    CON_PED.COD_CONVENIO = cCodConvenio_in
        AND    CAB.FEC_PED_VTA BETWEEN TO_DATE('09/05/2007' || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
  	    AND    TO_DATE('10/05/2007' || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
--        AND    CAB.FEC_PED_VTA BETWEEN TO_DATE(cDiaInicio_in||'/'|| cFechaInicio_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
--			  AND    TO_DATE(cDiaFin_in - 1 ||'/'|| cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
        AND    CAB.COD_GRUPO_CIA = CON_PED.COD_GRUPO_CIA
        AND    CAB.COD_LOCAL = CON_PED.COD_LOCAL
        AND    CAB.NUM_PED_VTA = CON_PED.NUM_PED_VTA
        AND    CON_PED.COD_CONVENIO = CLI_CONV.COD_CONVENIO
        AND    CON_PED.COD_CLI = CLI_CONV.COD_CLI
        AND    CLI_CONV.COD_CLI = MAE_CLI.COD_CLI
        GROUP BY CAB.COD_GRUPO_CIA,
               CAB.COD_LOCAL,
               --CLI_CONV.COD_CLI,
               CLI_CONV.COD_TRAB_CONV,
               CLI_CONV.COD_CONVENIO,
               SUBSTR(NVL(TRIM(MAE_CLI.NOM_COMPLETO),NVL(MAE_CLI.APE_PAT_CLI,' ') ||' '|| NVL(MAE_CLI.APE_MAT_CLI,' ') ||' '|| NVL(MAE_CLI.NOM_CLI,' ')),0,35),
               'SISTEMAS';

    v_CurConvenios curConvenios%ROWTYPE;

  BEGIN
       FOR v_CurConvenios IN curConvenios
	     LOOP
           INT_GRABA_INTERFCAE_CONV(v_CurConvenios.Cod_Grupo_Cia,
                                    v_CurConvenios.Cod_Local,
                                    v_CurConvenios.Cod_Trab,
                                    v_CurConvenios.Cod_Convenio,
                                    v_CurConvenios.Neto,
                                    v_CurConvenios.Nombre,
                                    v_CurConvenios.Usuario);
       DBMS_OUTPUT.PUT_LINE('GRABO EN TABLA DE INTERFAZ EL CONVENIO ' || v_CurConvenios.Cod_Convenio);
	     END LOOP;
  END;
*/
  /****************************************************************************/

/*  PROCEDURE INT_EJECUTA_CIERRE_CONV(cCodGrupoCia_in IN CHAR)
  AS
    --v_DiaFact con_mae_convenio.num_dia_ini_fact%TYPE ;
    v_Dia CHAR(2) ;
    v_count NUMBER ;
    vFechaInicio CHAR(8);
    vFechaFin CHAR(8);

    CURSOR curConvenio(dia NUMBER)IS
       SELECT  C.COD_CONVENIO COD_CONVENIO,
               C.DESC_CORTA_CONV DES_CONVENIO
       FROM    CON_MAE_CONVENIO  C
       WHERE   C.NUM_DIA_INI_FACT = V_DIA;
    v_curConvenio curConvenio%ROWTYPE;

  BEGIN
    SELECT to_number(to_char(SYSDATE,'dd')) INTO v_dia FROM dual;

    SELECT TO_CHAR(SYSDATE - (TO_CHAR(SYSDATE,'DD') + 1),'mm/yyyy') INTO vFechaInicio
    FROM DUAL;

    SELECT TO_CHAR(SYSDATE,'mm/yyyy') INTO vFechaFin
    FROM DUAL;
    v_dia := '10';
    SELECT COUNT(*) INTO v_count
    FROM   CON_MAE_CONVENIO C
    WHERE  C.NUM_DIA_INI_FACT = V_DIA;

    DBMS_OUTPUT.PUT_LINE('dia' || v_dia);
    DBMS_OUTPUT.PUT_LINE('vFechaInicio' || vFechaInicio);
    DBMS_OUTPUT.PUT_LINE('vFechaFin' || vFechaFin);

    IF (v_count > 0 ) THEN
       FOR v_curConvenio IN curConvenio (v_dia)
        LOOP
           BEGIN
            INT_EJECUTA_CONVENIOS(cCodGrupoCia_in,v_curConvenio.Cod_Convenio,v_dia,v_dia-1,vFechaInicio,vFechaFin);
            INT_GENERA_ARCHIVO(cCodGrupoCia_in,v_curConvenio.Cod_Convenio);
            INT_ACTUALIZA_PROCESO;
            INT_GENERA_DETALLE_COMP(cCodGrupoCia_in,v_curConvenio.Cod_Convenio,v_dia,v_dia-1,vFechaInicio,vFechaFin);
            COMMIT;
            INT_ENVIA_CORREO_INFORMACION('EXITO AL GENERAR INTERFAZ DE CONVENIOS',
                                         'EXITO',
                                         'SE GENERO LA INTERFAZ DE CONVENIO PARA EL CONVENIO ' || v_curConvenio.Des_Convenio|| 'el dia '|| SYSDATE);
           EXCEPTION
            WHEN OTHERS THEN
              DBMS_OUTPUT.PUT_LINE('ERROR AL GENERAR DATA EN TABLA DE INTERFACE - ' || SQLERRM);
              ROLLBACK;
              INT_ENVIA_CORREO_INFORMACION('ERROR AL GENERAR INTERFAZ DE CONVENIOS',
                                           'ERROR',
                                           'ERROR AL INTERFAZ DE CONVENIO PARA EL CONVENIO ' || v_curConvenio.Des_Convenio||'</B>'||
                                           '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR AL GENERAR ARCHIVO DE INTERFACE DE CONVENIO - ' || SQLERRM ||'<B>');
            END;
       END LOOP;
     END IF ;
  END;
*/
  /****************************************************************************/

  FUNCTION INT_GENERA_ARCHIVO (cCodGrupoCia_in IN CHAR,
                                cCodEmpresa_in IN CHAR)
  RETURN VARCHAR2
  AS
    CURSOR curResumen IS
    SELECT  C.COD_TRAB_CONV ||
            Farma_Utility.COMPLETAR_CON_SIMBOLO(C.VAL_IMPORTE,21,'0','I') ||
            C.NOM_TRAB RESUMEN
    FROM    INT_CONVENIO C
    WHERE   C.IND_PROCESO IS NULL
    AND     C.COD_GRUPO_CIA = cCodGrupoCia_in;
    v_rCurResumen curResumen%ROWTYPE;
    v_vNombreArchivo VARCHAR2(50);

  BEGIN
      v_vNombreArchivo := vPrefijo||cCodEmpresa_in || 'mf'||to_char(SYSDATE,'yy')||to_char(SYSDATE,'MM')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'INICIO');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'FIN');
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
      DBMS_OUTPUT.PUT_LINE('SE GENERO EL ARCHIVO DE INTERFACE CONVENIO CORRECTAMENTE');

     RETURN v_vNombreArchivo;

  END;

  PROCEDURE INT_ACTUALIZA_PROCESO
  IS
  BEGIN
       UPDATE INT_CONVENIO
       SET    IND_PROCESO = INDICADOR_SI
       WHERE  IND_PROCESO  IS NULL;
  END;

  /****************************************************************************/

 PROCEDURE INT_ENVIA_CORREO_INFORMACION(vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCodGrupoCia_in IN CHAR,
                                        cCodEmpresa_in IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INT_CONV;
    CCReceiverAddress VARCHAR2(120) := NULL;
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120):= 'LOCAL MATRIZ';
    v_nombreArchivo VARCHAR2(50):= INT_GENERA_ARCHIVO(cCodGrupoCia_in,cCodEmpresa_in);
  BEGIN

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.ENVIA_CORREO_ATTACH3(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                                     vAsunto_in|| ' - ' || v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                                     v_gNombreDiretorio,
                                     v_nombreArchivo,
                             CCReceiverAddress,
                                     FARMA_EMAIL.GET_EMAIL_SERVER);
    /*FARMA_EMAIL.ENVIA_CORREO(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                               ReceiverAddress,
                               vAsunto_in|| ' - ' || v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                               vTitulo_in,--'EXITO',
                               mesg_body,
                               CCReceiverAddress,
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               TRUE);*/


  END;

/*  PROCEDURE INT_GENERA_DETALLE_COMP(cCodGrupoCia_in IN CHAR,
                                    cCodConvenio_in IN CHAR,
                                    cDiaInicio_in IN CHAR,
                                    cDiaFin_in IN CHAR,
                                    cFechaInicio_in IN CHAR,
                                    cFechaFin_in IN CHAR)
  AS
       CURSOR curDetalleComp IS
       SELECT LOCAL.COD_LOCAL ||' - '||LOCAL.DESC_LOCAL ||','||
              MAE_CONVENIO.DESC_LARGA_CONV ||','||
              CLI_CONV.COD_TRAB_CONV ||','||
              NVL(TRIM(MAE_CLI.NOM_COMPLETO),NVL(MAE_CLI.APE_PAT_CLI,' ') ||' '|| NVL(MAE_CLI.APE_MAT_CLI,' ') ||' '|| NVL(MAE_CLI.NOM_CLI,' ')) ||','||
              SUBSTR(COMP_PAGO.NUM_COMP_PAGO,0,3)||'-'||SUBSTR(COMP_PAGO.NUM_COMP_PAGO,4) ||','||
              TO_CHAR(COMP_PAGO.VAL_NETO_COMP_PAGO + COMP_PAGO.VAL_REDONDEO_COMP_PAGO,'999,999.00') DETALLE_RESUMEN
       FROM   VTA_COMP_PAGO COMP_PAGO,
              VTA_PEDIDO_VTA_CAB CAB,
              CON_PED_VTA_CLI CON_PED,
              CON_CLI_CONV CLI_CONV,
              CON_MAE_CLIENTE MAE_CLI,
              PBL_LOCAL LOCAL,
              CON_MAE_CONVENIO MAE_CONVENIO
       WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CAB.IND_PED_CONVENIO = INDICADOR_SI
       AND    CAB.IND_PEDIDO_ANUL = INDICADOR_NO
       AND    CAB.SEC_MOV_CAJA IS NOT NULL
       AND    CAB.EST_PED_VTA = 'C'
       AND    CON_PED.COD_CONVENIO = cCodConvenio_in
       AND    CAB.FEC_PED_VTA BETWEEN TO_DATE('18/04/2007' || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
  	   AND    TO_DATE('19/04/2007' || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
--       AND    CAB.FEC_PED_VTA BETWEEN TO_DATE(cDiaInicio_in||'/'|| cFechaInicio_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
--  		 AND    TO_DATE(cDiaFin_in - 1 ||'/'|| cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
       AND    CAB.COD_GRUPO_CIA = CON_PED.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = CON_PED.COD_LOCAL
       AND    CAB.NUM_PED_VTA = CON_PED.NUM_PED_VTA
       AND    CAB.COD_GRUPO_CIA = COMP_PAGO.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = COMP_PAGO.COD_LOCAL
       AND    CAB.NUM_PED_VTA = COMP_PAGO.NUM_PED_VTA
       AND    CAB.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = LOCAL.COD_LOCAL
       AND    CON_PED.COD_CONVENIO = CLI_CONV.COD_CONVENIO
       AND    CON_PED.COD_CLI = CLI_CONV.COD_CLI
       AND    CON_PED.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    CON_PED.COD_LOCAL = LOCAL.COD_LOCAL
       AND    COMP_PAGO.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    COMP_PAGO.COD_LOCAL = LOCAL.COD_LOCAL
       AND    CLI_CONV.COD_CLI = MAE_CLI.COD_CLI
       AND    CLI_CONV.COD_CONVENIO = MAE_CONVENIO.COD_CONVENIO
       ORDER BY 1;
       v_curDetalleComp curDetalleComp%ROWTYPE;
       v_vNombreArchivo VARCHAR2(50);

  BEGIN
      v_vNombreArchivo := cCodConvenio_in || 'mf'||to_char(SYSDATE,'yy')||to_char(SYSDATE,'MM')||'detallado'||'.CSV';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'INICIO');
      FOR v_curDetalleComp IN curDetalleComp
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_curDetalleComp.DETALLE_RESUMEN);
      END LOOP;
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'FIN');
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
      DBMS_OUTPUT.PUT_LINE('SE GENERO EL ARCHIVO DE INTERFACE CONVENIO DETTALLADO DE COMPROBANTES CORRECTAMENTE');
    END;
*/
------------------------------------------------------------------------------
------------------------------------------------------------------------------

/*  PROCEDURE INT_CARGA_TRAB_QS
  IS
  BEGIN
       ARCHIVO_TEXTO:=UTL_FILE.FOPEN(vNombreDirectorio,TRIM(vNombreArchivo),'W');

       UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'Fecha de Carga: '||TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:MI:SS'));
       UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'***Inicio proceso de carga...');
       UTL_FILE.put_line(ARCHIVO_TEXTO,' ');

       UTL_FILE.put_line(ARCHIVO_TEXTO,'Insertando registros a tabla auxiliar desde la tabla externa...');
       PTOVENTA_INT_CONV.INT_INSERTA_AUX_TRAB_QS;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Terminado: '||SQL%ROWCOUNT||' filas afectadas.');
       UTL_FILE.put_line(ARCHIVO_TEXTO,' ');

       UTL_FILE.put_line(ARCHIVO_TEXTO,'Insertando registros a maestro de trabajadores QS...');
       PTOVENTA_INT_CONV.INT_INSERTA_TRAB_QS;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Terminado: '||vCantFilasAfect||' filas afectadas.');
       vCantFilasAfect := 0;
       UTL_FILE.put_line(ARCHIVO_TEXTO,' ');

       UTL_FILE.put_line(ARCHIVO_TEXTO,'Activando trabajadores en CE_MAE_TRAB_QS...');
       PTOVENTA_INT_CONV.INT_ACTIVA_TRAB_QS;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Terminado: '||SQL%ROWCOUNT||' filas afectadas.');
       UTL_FILE.put_line(ARCHIVO_TEXTO,' ');

       UTL_FILE.put_line(ARCHIVO_TEXTO,'Inactivando trabajadores en CE_MAE_TRAB_QS...');
       PTOVENTA_INT_CONV.INT_INACTIVA_TRAB_QS;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Terminado: '||SQL%ROWCOUNT||' filas afectadas.');
       UTL_FILE.put_line(ARCHIVO_TEXTO,' ');

       UTL_FILE.put_line(ARCHIVO_TEXTO,'Insertando registros a la tabla CON_CLI_CONV...');
       PTOVENTA_INT_CONV.INT_INSERTA_CON_CLI_CONV(C_COD_GRUPO_CIA,C_COD_LOCAL);
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Terminado: '||vCantFilasAfect||' filas afectadas.');
       UTL_FILE.put_line(ARCHIVO_TEXTO,' ');

       UTL_FILE.put_line(ARCHIVO_TEXTO,'Inactivando trabajadores en CON_CLI_CONV...');
       PTOVENTA_INT_CONV.INT_INACTIVA_TRAB_CONV;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Terminado: '||SQL%ROWCOUNT||' filas afectadas.');
       UTL_FILE.put_line(ARCHIVO_TEXTO,' ');

       UTL_FILE.put_line(ARCHIVO_TEXTO,'Activando trabajadores en CON_CLI_CONV...');
       PTOVENTA_INT_CONV.INT_ACTIVA_TRAB_CONV;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Terminado: '||SQL%ROWCOUNT||' filas afectadas.');
       UTL_FILE.put_line(ARCHIVO_TEXTO,' ');
       COMMIT;

       UTL_FILE.put_line(ARCHIVO_TEXTO,'***Proceso de carga finalizado exitosamente.');
       UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

      EXCEPTION
         WHEN OTHERS THEN
         ROLLBACK;
           UTL_FILE.put_line(ARCHIVO_TEXTO,'***Proceso de carga cancelado por: ');
           UTL_FILE.put_line(ARCHIVO_TEXTO,'    '||SQLERRM);
           UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

  END INT_CARGA_TRAB_QS;
*/
-------------------------------------------------------------------------------------

/*  PROCEDURE INT_INSERTA_AUX_TRAB_QS
  IS
    CURSOR curDuplicado IS
      SELECT (docide) NOMBRE
      FROM   et_mae_trab_qs
      GROUP BY docide
      HAVING COUNT (docide) > 1;
    v_curDuplicado curDuplicado%ROWTYPE;

    CURSOR curDuplicadoCod IS
      SELECT trim(CODIGO) CODIGO
      FROM   et_mae_trab_qs
      GROUP BY CODIGO
      HAVING COUNT (codigo) > 1;
    v_curDuplicadoCod curDuplicadoCod%ROWTYPE;

    CURSOR curLimiteCredito IS
      SELECT DISTINCT et.nombres nombre,
             ET.LIMCRED creditoet,
             QS.LIMCRED creditpqs
      FROM   et_mae_trab_qs et ,
             aux_mae_trab_qs qs
      WHERE  et.codigo = qs.cod_trab
      AND    et.limcred <> qs.limcred
      AND    TRUNC(QS.FEC_CREA_TRAB) = TRUNC (SYSDATE-1);
    v_curLimiteCredito curLimiteCredito%ROWTYPE;

  BEGIN
    INSERT INTO AUX_MAE_TRAB_QS(FECHA, FEC_CREA_TRAB, COD_EMPRESA, COD_TRAB, NOM_TRAB,DEPTO,LIMCRED,TIPODOC,NUMDOC)
    (SELECT  SYSDATE,
             SYSDATE,
             QS.EMPRESA,
             QS.CODIGO,
             QS.NOMBRES,
             QS.DEPTO,
             TO_NUMBER(QS.LIMCRED),
             QS.TIPDOC,
             QS.DOCIDE
     FROM    ET_MAE_TRAB_QS QS);

     open curDuplicado;
     loop
       fetch curDuplicado into v_curDuplicado;
       exit when curDuplicado%notfound;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Documentos Duplicados : '  || v_curDuplicado.Nombre);
     end loop;
     close curDuplicado;

     open curDuplicadoCod;
     loop
       fetch curDuplicadoCod into v_curDuplicadoCod;
       exit when curDuplicadoCod%notfound;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'Codigos de Trabajadores Duplicados : '  || v_curDuplicadoCod.CODIGO);
     end loop;
     close curDuplicadoCod;

     open curLimiteCredito;
     loop
       fetch curLimiteCredito into v_curLimiteCredito;
       exit when curLimiteCredito%notfound;
       UTL_FILE.put_line(ARCHIVO_TEXTO,'USUARIOS CON CAMBIO DE CREDITO');
       UTL_FILE.put_line(ARCHIVO_TEXTO,'NOMBRES                 CRED QS       CRED EXISTENTE');
       UTL_FILE.put_line(ARCHIVO_TEXTO,v_curLimiteCredito.Nombre||' '|| v_curLimiteCredito.Creditoet||' '||v_curLimiteCredito.Creditpqs);
     end loop;
     close curLimiteCredito;

  END INT_INSERTA_AUX_TRAB_QS;
*/
-------------------------------------------------------------------------------------

/*  PROCEDURE INT_INSERTA_TRAB_QS
  IS
    --Obtiene los trabajadores de la tabla auxiliar que no se encuentran
    --en la tabla CE_MAE_TRAB_QS
    CURSOR curAux IS
        SELECT  AUX.COD_TRAB,
                AUX.NOM_TRAB,
                AUX.DEPTO,
                AUX.LIMCRED,
                AUX.COD_EMPRESA,
                AUX.TIPODOC,
                AUX.NUMDOC
        FROM    AUX_MAE_TRAB_QS AUX
        WHERE   AUX.COD_TRAB NOT IN (SELECT COD_TRAB
                                     FROM   CE_MAE_TRAB_QS)
        AND     TO_CHAR(AUX.FEC_CREA_TRAB,'dd/MM/yyyy')=TO_CHAR(SYSDATE,'dd/MM/yyyy');

    vAuxCodTrab    AUX_MAE_TRAB_QS.COD_TRAB%TYPE;
    vAuxNomTrab    AUX_MAE_TRAB_QS.NOM_TRAB%TYPE;
    vAuxDptTrab    AUX_MAE_TRAB_QS.DEPTO%TYPE;
    vAuxLimCreTrab AUX_MAE_TRAB_QS.LIMCRED%TYPE;
    vAuxEmpTrab    AUX_MAE_TRAB_QS.COD_EMPRESA%TYPE;
    vAuxTipDocTrab AUX_MAE_TRAB_QS.TIPODOC%TYPE;
    vAuxNumDocTrab AUX_MAE_TRAB_QS.NUMDOC%TYPE;

  BEGIN

  OPEN curAux;
    vCantFilasAfect :=0;
    LOOP
         FETCH curAux INTO vAuxCodTrab, vAuxNomTrab, vAuxDptTrab, vAuxLimCreTrab, vAuxEmpTrab, vAuxTipDocTrab, vAuxNumDocTrab;
         EXIT WHEN curAux%NOTFOUND;

         INSERT INTO CE_MAE_TRAB_QS(COD_TRAB,NOM_TRAB,DEPTO,LIMCRED,COD_EMPRESA,TIP_DOC,NUM_DOC)
         VALUES (vAuxCodTrab,
                 vAuxNomTrab,
                 vAuxDptTrab,
                 vAuxLimCreTrab,
                 vAuxEmpTrab,
                 vAuxTipDocTrab,
                 vAuxNumDocTrab
                 );

          vCantFilasAfect:=vCantFilasAfect+1;
    END LOOP;
  CLOSE curAux;

  END INT_INSERTA_TRAB_QS;
*/
-------------------------------------------------------------------------------------
/*
  PROCEDURE INT_INACTIVA_TRAB_QS
  IS
  BEGIN
       --Inactiva al trabajador que ya no se encuentra en la tabla auxiliar
     UPDATE  CE_MAE_TRAB_QS SET FEC_CESE_TRAB = SYSDATE,
                                EST_TRAB      = C_C_ESTADO_INACTIVO
     WHERE   COD_TRAB IN (
        SELECT  TRAB_QS.COD_TRAB
        FROM    CE_MAE_TRAB_QS TRAB_QS
        WHERE   TRAB_QS.IND_ESTADO_MANUAL = C_C_ESTADO_INACTIVO
        MINUS
        SELECT  AUX.COD_TRAB
        FROM    AUX_MAE_TRAB_QS AUX
        WHERE   TO_CHAR(AUX.FEC_CREA_TRAB,'dd/MM/yyyy')=TO_CHAR(SYSDATE,'dd/MM/yyyy')
     );
  END INT_INACTIVA_TRAB_QS;
*/
-------------------------------------------------------------------------------------
/*
  PROCEDURE INT_INSERTA_CON_CLI_CONV(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR)
  IS
    curTrabQS FarmaCursor;
    vTrabQsCodConv      CON_MAE_CONVENIO.COD_CONVENIO%TYPE;
    vTrabQsCodTrab      CE_MAE_TRAB_QS.Cod_Trab%TYPE;
    vTrabQsNomTrab      CE_MAE_TRAB_QS.Nom_Trab%TYPE;
    vTrabQsLimCred      CE_MAE_TRAB_QS.LIMCRED%TYPE;
    vTrabTipDoc         CE_MAE_TRAB_QS.TIP_DOC%TYPE;
    vTrabQsNumDoc       CE_MAE_TRAB_QS.Num_Doc%TYPE;

    --curCliente          FarmaCursor;
    CURSOR curCliente(pDni_in CHAR, pCodConvenio CHAR) IS
         SELECT   CLI.COD_CLI
         FROM     CON_MAE_CLIENTE CLI,
                  CON_CLI_CONV CLI_CONV
         WHERE    CLI.COD_CLI = CLI_CONV.COD_CLI
         AND      CLI.NUM_DOC_CLI  = pDni_in
         AND      CLI_CONV.COD_CONVENIO = pCodConvenio;
    cliente_rec CHAR(10);

    vCodCli             CON_MAE_CLIENTE.COD_CLI%TYPE;
    vCodCliCliente      CON_MAE_CLIENTE.COD_CLI%TYPE;
    vNomCompletoCliente CON_MAE_CLIENTE.Nom_Cli%TYPE;
    vNumDocCliente      CON_MAE_CLIENTE.Num_Doc_Cli%TYPE;

    nCount CHAR(1) := 'N';
  BEGIN
     OPEN curTrabQS FOR
        SELECT  ME.COD_CONVENIO,
                TRAB_QS.COD_TRAB,
                TRAB_QS.NOM_TRAB,
                TRAB_QS.LIMCRED,
                TRAB_QS.TIP_DOC,
                TRAB_QS.NUM_DOC
        FROM    CE_MAE_TRAB_QS TRAB_QS,
                CON_MAE_CONVENIO ME
        WHERE   TRAB_QS.COD_TRAB NOT IN ( SELECT COD_TRAB_CONV
                                          FROM   CON_CLI_CONV
                                          WHERE  COD_TRAB_CONV <> ' '
                                          )
        AND     ME.IND_MULTIPLE_CONV = INDICADOR_SI;
     vCantFilasAfect := 0;
     --Inserta en la tabla CON_CLI_CON desde el maestro CE_MAE_TRAB_QS
     LOOP
         FETCH curTrabQS INTO vTrabQsCodConv, vTrabQsCodTrab, vTrabQsNomTrab, vTrabQsLimCred, vTrabTipDoc, vTrabQsNumDoc;
         EXIT WHEN curTrabQS%NOTFOUND;

         --Verifica si el DNI existe en CON_MAE_CLIENTE
         --curCliente := PTOVENTA_INT_CONV.INT_OBTIENE_CLIENTE_X_DNI(vTrabQsNumDoc);

         FOR cliente_rec IN curCliente(vTrabQsNumDoc,vTrabQsCodConv)
         LOOP
             nCount := 'S';
             vCodCli := cliente_rec.cod_cli;
         END LOOP;

         IF nCount = 'N' THEN
           vCodCli := PTOVENTA_INT_CONV.INT_INSERTA_CLIENTE_TRAB(cCodGrupoCia_in,
                                                                 cCodLocal_in,
                                                                 vTrabQsNomTrab,
                                                                 vTrabTipDoc,
                                                                 vTrabQsNumDoc);
         END IF;

         INSERT INTO CON_CLI_CONV (COD_CONVENIO,COD_CLI,COD_TRAB_CONV,VAL_CREDITO_MAX,VAL_CREDITO_UTIL,Usu_Crea_Cli_Conv)
         VALUES(vTrabQsCodConv,
                vCodCli,
                Farma_Utility.COMPLETAR_CON_SIMBOLO(vTrabQsCodTrab,6,'0','I'),
                vTrabQsLimCred,
                0,
                'SISTEMAS');
         vCantFilasAfect := vCantFilasAfect+1;
         /*
         IF curCliente%ROWCOUNT > 0 THEN --Existe
            FETCH curCliente INTO vCodCliCliente;--, vNomCompletoCliente, vNumDocCliente;
            EXIT WHEN curCliente%NOTFOUND;
               vCodCli := vCodCliCliente;
         ELSE
             vCodCli := PTOVENTA_INT_CONV.INT_INSERTA_CLIENTE_TRAB(cCodGrupoCia_in,
                                                                   cCodLocal_in,
                                                                   vTrabQsNomTrab,
                                                                   vTrabQsNumDoc);
         END IF;
         */
  /*   END LOOP;

     DBMS_OUTPUT.put_line('INSERTA CON_CLI_CONV: '||vCantFilasAfect);
     EXCEPTION
              WHEN OTHERS THEN
              dbms_output.put_line('error PK : '  ||vTrabQsCodConv || ' - ' || vTrabQsNumDoc );
  END INT_INSERTA_CON_CLI_CONV;
*/
-------------------------------------------------------------------------------------
/*
  PROCEDURE INT_INACTIVA_TRAB_CONV
  IS

  BEGIN
      --Actualiza el estado de los trabajadores en CON_CLI_CONV
      UPDATE CON_CLI_CONV SET EST_CONV_CLI = C_C_ESTADO_INACTIVO
      WHERE  COD_TRAB_CONV IN(
        SELECT  TRAB_QS.COD_TRAB
        FROM    CE_MAE_TRAB_QS TRAB_QS
        WHERE   TRAB_QS.EST_TRAB = C_C_ESTADO_INACTIVO
        AND     TRAB_QS.IND_ESTADO_MANUAL = C_C_ESTADO_INACTIVO
      );
  END INT_INACTIVA_TRAB_CONV;
*/
-------------------------------------------------------------------------------------

  /*FUNCTION INT_OBTIENE_CLIENTE_X_DNI(cDni_in    IN CHAR) RETURN FarmaCursor
  IS
  curCliente FarmaCursor;
  BEGIN
    OPEN curCliente FOR
         SELECT   CLI.COD_CLI,
                  CLI.NOM_CLI,
                  CLI.APE_PAT_CLI,
                  CLI.APE_MAT_CLI,
                  CLI.TIP_DOC_CLI,
                  CLI.NUM_DOC_CLI
         FROM     CON_MAE_CLIENTE CLI
         WHERE    CLI.NUM_DOC_CLI  = cDni_in;

     RETURN curCliente;

  END INT_OBTIENE_CLIENTE_X_DNI;
*/
-------------------------------------------------------------------------------------
/*
  FUNCTION INT_INSERTA_CLIENTE_TRAB(cCodGrupoCia_in      IN CHAR,
                                    cCodLocal_in         IN CHAR,
                                    cNomCompletoCli_in   IN CHAR,
                                    cTipoDoc_in          IN CHAR,
                                    cNumDoc_in           IN CHAR) RETURN CHAR
  IS
      cCodCliente NUMBER;
      v_cCodCliente CHAR(10);
  BEGIN
       cCodCliente := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,C_COD_NUMERA_COD_CLIENTE);
       v_cCodCliente:= Farma_Utility.COMPLETAR_CON_SIMBOLO(cCodCliente, 10, '0', 'I');

       --DBMS_OUTPUT.put_line('Val Cliente: '||v_cCodCliente||','||cNumDoc_in||','||cNomCompletoCli_in);
       INSERT INTO CON_MAE_CLIENTE (COD_CLI,TIP_DOC_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,NUM_DOC_CLI,NOM_COMPLETO,USU_CREA_MAE_CLIENTE)
                            VALUES (v_cCodCliente,cTipoDoc_in,' ',' ',' ',cNumDoc_in,cNomCompletoCli_in,'SISTEMAS');
       Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,C_COD_NUMERA_COD_CLIENTE,'SISTEMAS');

       RETURN v_cCodCliente;
  END INT_INSERTA_CLIENTE_TRAB;
*/
-------------------------------------------------------------------------------------
/*
  PROCEDURE INT_ACTIVA_TRAB_QS
  IS
  BEGIN
       --Activa al trabajador, en caso anteriormente haya sido inactivado
      UPDATE CE_MAE_TRAB_QS
      SET    EST_TRAB = C_C_ESTADO_ACTIVO
      WHERE  COD_TRAB IN (
        SELECT  AUX_QS.COD_TRAB
        FROM    AUX_MAE_TRAB_QS AUX_QS
        WHERE   TRIM(FECHA) = TO_CHAR(SYSDATE,'dd/MM/yy')
        AND     AUX_QS.COD_TRAB IN (SELECT  TRAB_QS.COD_TRAB
                                    FROM    CE_MAE_TRAB_QS TRAB_QS
                                    WHERE   TRAB_QS.EST_TRAB = C_C_ESTADO_INACTIVO
                                    AND     TRAB_QS.IND_ESTADO_MANUAL = C_C_ESTADO_INACTIVO));
  END INT_ACTIVA_TRAB_QS;
*/
-------------------------------------------------------------------------------------
/*
  PROCEDURE INT_ACTIVA_TRAB_CONV
  IS

  BEGIN
      --Actualiza el estado de los trabajadores en CON_CLI_CONV
      /*UPDATE  CON_CLI_CONV
      SET     EST_CONV_CLI = C_C_ESTADO_ACTIVO
      WHERE   COD_TRAB_CONV IN (
      SELECT  AUX_QS.COD_TRAB
      FROM    AUX_MAE_TRAB_QS AUX_QS
      WHERE   TRIM(FECHA) = TO_CHAR(SYSDATE+3,'dd/MM/yy')
      AND     AUX_QS.COD_TRAB IN (SELECT  TRAB_QS.COD_TRAB
                                  FROM    CE_MAE_TRAB_QS TRAB_QS
                                  WHERE   TRAB_QS.EST_TRAB = C_C_ESTADO_INACTIVO));
      */
  /*    UPDATE  CON_CLI_CONV
      SET     EST_CONV_CLI = C_C_ESTADO_ACTIVO
      WHERE   COD_TRAB_CONV IN (
              SELECT  TRAB_QS.COD_TRAB
              FROM    CE_MAE_TRAB_QS TRAB_QS
              WHERE   TRAB_QS.EST_TRAB = C_C_ESTADO_ACTIVO
              AND     TRAB_QS.IND_ESTADO_MANUAL = C_C_ESTADO_ACTIVO);

  END INT_ACTIVA_TRAB_CONV;
*/
-------------------------------------------------------------------------------------
   PROCEDURE INT_EJECUTA_CONVENIOS_EMPR(cCodGrupoCia_in IN CHAR,
                                        cCodConvenio_in IN CHAR,
                                        cFechaInicio_in IN CHAR,
                                        cFechaFin_in IN CHAR)
   AS
     v_cCodConvenio CHAR(10):= cCodConvenio_in ;

     CURSOR curConvenios IS

  /*    SELECT  '001' COD_GRUPO_CIA ,
              '009' COD_LOCAL ,
              CLI_CONV.COD_TRAB_CONV COD_TRAB,
              MAE_CONV.COD_EMPRESA COD_CONVENIO,
              TO_CHAR(SUM(VAL_CREDITO_UTIL)*100) NETO,
              --SUM(VAL_CREDITO_UTIL) NETO2,
              --MIN(CLI_CONV.VAL_CREDITO_MAX) MAX,
              SUBSTR(NVL(TRIM(MAE_CLI.NOM_COMPLETO),NVL(MAE_CLI.APE_PAT_CLI,' ') ||' '|| NVL(MAE_CLI.APE_MAT_CLI,' ') ||' '|| NVL(MAE_CLI.NOM_CLI,' ')),0,35) NOMBRE,
              'SISTEMAS' USUARIO
      FROM    CON_CLI_CONV CLI_CONV,
              CON_MAE_CLIENTE MAE_CLI,
              CON_MAE_CONVENIO MAE_CONV
      WHERE   MAE_CONV.COD_EMPRESA = cCodConvenio_in
      AND     CLI_CONV.COD_CLI = MAE_CLI.COD_CLI
      AND     CLI_CONV.COD_CONVENIO = MAE_CONV.COD_CONVENIO
      GROUP BY CLI_CONV.COD_TRAB_CONV,MAE_CONV.COD_EMPRESA,SUBSTR(NVL(TRIM(MAE_CLI.NOM_COMPLETO),NVL(MAE_CLI.APE_PAT_CLI,' ') ||' '|| NVL(MAE_CLI.APE_MAT_CLI,' ') ||' '|| NVL(MAE_CLI.NOM_CLI,' ')),0,35)
      HAVING SUM(VAL_CREDITO_UTIL)*100 > 0 ;--AND SUM(VAL_CREDITO_UTIL) > MIN(CLI_CONV.VAL_CREDITO_MAX)*/

      SELECT '001' COD_GRUPO_CIA ,
             '009' COD_LOCAL ,
             CC.COD_TRAB_CONV COD_TRAB,
             v_cCodConvenio COD_CONVENIO,
             --Farma_Utility.COMPLETAR_CON_SIMBOLO(SUM(FPP.IM_PAGO*100),21,'0','I') ,
             TO_CHAR(SUM(FPP.IM_PAGO)*100) NETO,
             SUBSTR(NVL(TRIM(MC.NOM_COMPLETO),NVL(MC.APE_PAT_CLI,' ') ||' '|| NVL(MC.APE_MAT_CLI,' ') ||' '|| NVL(MC.NOM_CLI,' ')),0,35) NOMBRE,
             'SISTEMAS' USUARIO
             --CC.VAL_CREDITO_UTIL,
             --CC.VAL_CREDITO_UTIL - SUM(FPP.IM_PAGO),
             --MC.NOM_COMPLETO
      FROM   CON_PED_VTA_CLI C,
             CON_CLI_CONV CC,
             VTA_PEDIDO_VTA_CAB CAB,
             VTA_FORMA_PAGO_PEDIDO FPP,
             CON_MAE_CLIENTE MC
      WHERE  FEC_CREA_PED_VTA_CLI BETWEEN TO_DATE(cFechaInicio_in||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
      AND    TO_DATE(cFechaFin_in||' 23:59:59','dd/MM/yyyy HH24:mi:ss')
      AND    CAB.EST_PED_VTA = 'C'
      AND    CAB.IND_PEDIDO_ANUL = 'N'
      AND    CAB.IND_PED_CONVENIO = 'S'
      AND    FPP.COD_FORMA_PAGO IN(SELECT VP.COD_FORMA_PAGO
                                                   FROM   CON_MAE_EMPRESA ME,
                                                          VTA_FORMA_PAGO VP,
                                                          CON_MAE_CONVENIO MC
                                                   WHERE  ME.COD_EMPRESA = v_cCodConvenio
                                                   AND    ME.COD_EMPRESA = MC.COD_CONVENIO
                                                   AND    MC.COD_CONVENIO = VP.COD_CONVENIO)
      AND    C.COD_CLI = CC.COD_CLI
      AND    C.COD_CONVENIO = CC.COD_CONVENIO
      AND    C.COD_LOCAL = CAB.COD_LOCAL
      AND    C.NUM_PED_VTA = CAB.NUM_PED_VTA
      AND    CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA
      AND    CAB.COD_LOCAL = FPP.COD_LOCAL
      AND    CAB.NUM_PED_VTA = FPP.NUM_PED_VTA
      AND    C.COD_CLI = MC.COD_CLI
      AND    CC.COD_CLI = MC.COD_CLI
      GROUP BY   '001','009',CC.COD_TRAB_CONV,v_cCodConvenio,SUBSTR(NVL(TRIM(MC.NOM_COMPLETO),NVL(MC.APE_PAT_CLI,' ') ||' '|| NVL(MC.APE_MAT_CLI,' ') ||' '|| NVL(MC.NOM_CLI,' ')),0,35) ,
                 'SISTEMAS' ;

    v_CurConvenios curConvenios%ROWTYPE;

  BEGIN
       DBMS_OUTPUT.put_line('cCodConvenio_in: '||cCodConvenio_in);
       FOR v_CurConvenios IN curConvenios
	     LOOP
           INT_GRABA_INTERFCAE_CONV(v_CurConvenios.Cod_Grupo_Cia,
                                    v_CurConvenios.Cod_Local,
                                    v_CurConvenios.Cod_Trab,
                                    v_CurConvenios.Cod_Convenio,
                                    v_CurConvenios.Neto,
                                    v_CurConvenios.Nombre,
                                    v_CurConvenios.Usuario);
       --DBMS_OUTPUT.PUT_LINE('GRABO EN TABLA DE INTERFAZ EL CONVENIO ' || v_CurConvenios.Cod_Convenio);
	     END LOOP;
  END;

-------------------------------------------------------------------------------------

  PROCEDURE INT_EJECUTA_CIERRE_CONV_EMPR(cCodGrupoCia_in IN CHAR)
  AS
    --v_DiaFact con_mae_convenio.num_dia_ini_fact%TYPE ;
    v_Dia CHAR(2) ;
    v_count NUMBER ;
    vFechaInicio CHAR(10);
    vFechaFin CHAR(10);

    CURSOR curConvenio IS
       SELECT  DISTINCT E.COD_EMPRESA COD_EMPRESA,
               E.DESC_RAZON_SOCIAL RAZON_SOCIAL
       FROM    CON_MAE_EMPRESA   E,
               CONV_CICLO_FACT C
       WHERE   TRUNC(C.FEC_FIN_CICLO_FACT) = TRUNC(SYSDATE-1)
       AND     C.COD_EMPRESA = E.COD_EMPRESA
       AND     C.FEC_PROCESO_CICLO_FACT IS NULL;

    v_curConvenio curConvenio%ROWTYPE;
    v_NombreArchivo VARCHAR2(50);

  BEGIN

/*    SELECT to_number(to_char(SYSDATE,'dd')) INTO v_dia FROM dual;

    SELECT TO_CHAR(SYSDATE - (TO_CHAR(SYSDATE,'DD') + 1),'mm/yyyy') INTO vFechaInicio
    FROM DUAL;

    SELECT TO_CHAR(SYSDATE,'mm/yyyy') INTO vFechaFin
    FROM DUAL;*/

    SELECT COUNT(*) INTO v_count
    FROM   CONV_CICLO_FACT C
    WHERE  TRUNC(C.FEC_FIN_CICLO_FACT) = TRUNC(SYSDATE-1);
    DBMS_OUTPUT.PUT_LINE('v_count : ' || v_count);

/*  DBMS_OUTPUT.PUT_LINE('dia' || v_dia);
    DBMS_OUTPUT.PUT_LINE('vFechaInicio' || vFechaInicio);
    DBMS_OUTPUT.PUT_LINE('vFechaFin' || vFechaFin);
*/
    IF (v_count > 0 ) THEN
       FOR v_curConvenio IN curConvenio
        LOOP
         DBMS_OUTPUT.PUT_LINE('v_curConveniO.Cod_Empresa : ' || v_curConveniO.Cod_Empresa);
           BEGIN
            vFechaInicio := INT_OBTIENE_FECHA_INICIO_FACT (cCodGrupoCia_in,v_curConveniO.Cod_Empresa);
            vFechaFin := INT_OBTIENE_FECHA_FIN_FACT (cCodGrupoCia_in,v_curConveniO.Cod_Empresa);
            DBMS_OUTPUT.PUT_LINE('FECHA INICIO : ' || vFechaInicio);
            DBMS_OUTPUT.PUT_LINE('FECHA FIN : ' || vFechaFin);
            INT_EJECUTA_CONVENIOS_EMPR(cCodGrupoCia_in,v_curConveniO.Cod_Empresa,vFechaInicio,vFechaFin);
            INT_CREDITOS_PEDIDO(cCodGrupoCia_in,v_curConveniO.Cod_Empresa,vFechaInicio,vFechaFin);
            v_NombreArchivo := INT_GENERA_ARCHIVO(cCodGrupoCia_in,v_curConvenio.Cod_Empresa);
            --INT_ACTUALIZA_PROCESO;
            INT_GENERA_DETALLE_COMP_EMPR(cCodGrupoCia_in,v_curConvenio.Cod_Empresa,vFechaInicio,vFechaFin);

            --18/05/2007 ERIOS Actualiza el credito utilizado.
            --                 Se asume que el viajero actualiza el campo val_credito_max
            UPDATE CON_CLI_CONV C
            SET C.VAL_CREDITO_UTIL = 0
            WHERE C.COD_CONVENIO IN (SELECT COD_CONVENIO FROM CON_MAE_EMPRESA E
                                    WHERE E.COD_EMPRESA = v_curConvenio.Cod_Empresa);
            --05/07/2007 PAMEGHINO ACTUALIZA LA FECHA DE PROCESO SI YA GENERO TODA LA IINTERFAZ
            UPDATE CONV_CICLO_FACT C SET C.FEC_MOD_CICLO_FACT = SYSDATE, C.USU_MOD_CICLO_FACT = 'SISTEMAS',
                   C.FEC_PROCESO_CICLO_FACT  = SYSDATE
            WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    C.SEC_CICLO_FACT IN (SELECT MIN(CF.SEC_CICLO_FACT)
                                         FROM   CONV_CICLO_FACT CF
                                         WHERE  CF.COD_EMPRESA = v_curConvenio.Cod_Empresa
                                         AND    CF.FEC_PROCESO_CICLO_FACT IS NULL);


            COMMIT;
            INT_ENVIA_CORREO_INFORMACION('EXITO AL GENERAR INTERFAZ DE CONVENIOS',
                                         'EXITO',
                                         'SE GENERO LA INTERFAZ DE CONVENIO PARA EL CONVENIO ' || v_curConvenio.Razon_Social|| ' el dia '|| SYSDATE,
                                         cCodGrupoCia_in,
                                         v_curConvenio.Cod_Empresa);
            INT_ACTUALIZA_PROCESO;
           EXCEPTION
            WHEN OTHERS THEN
              DBMS_OUTPUT.PUT_LINE('ERROR AL GENERAR DATA EN TABLA DE INTERFACE - ' || SQLERRM);
              ROLLBACK;
              INT_ENVIA_CORREO_INFORMACION('ERROR AL GENERAR INTERFAZ DE CONVENIOS',
                                           'ERROR',
                                           'ERROR AL INTERFAZ DE CONVENIO PARA EL CONVENIO ' || v_curConvenio.Razon_Social||'</B>'||
                                           '<BR> <I>VERIFIQUE:</I> <BR>'||'ERROR AL GENERAR ARCHIVO DE INTERFACE DE CONVENIO - ' || SQLERRM ||'<B>',
                                           cCodGrupoCia_in,
                                           v_curConvenio.Cod_Empresa);
            END;
       END LOOP;
     END IF ;
  END;

  /****************************************************************************/
  PROCEDURE INT_GENERA_DETALLE_COMP_EMPR(cCodGrupoCia_in IN CHAR,
                                         cCodConvenio_in IN CHAR,
                                         cFechaInicio_in IN CHAR,
                                         cFechaFin_in IN CHAR)
  AS
     CURSOR curDetalleComp IS

       SELECT LOCAL.COD_LOCAL ||' - '||LOCAL.DESC_LOCAL ||','||
              MAE_CONVENIO.DESC_LARGA_CONV ||','||
              CLI_CONV.COD_TRAB_CONV ||','||
              NVL(TRIM(MAE_CLI.NOM_COMPLETO),NVL(MAE_CLI.APE_PAT_CLI,' ') ||' '|| NVL(MAE_CLI.APE_MAT_CLI,' ') ||' '|| NVL(MAE_CLI.NOM_CLI,' ')) ||','||
              SUBSTR(COMP_PAGO.NUM_COMP_PAGO,0,3)||'-'||SUBSTR(COMP_PAGO.NUM_COMP_PAGO,4) ||','||
              TO_CHAR(CAB.VAL_NETO_PED_VTA,'999990.00') ||','||
              to_char(VFPP.IM_TOTAL_PAGO,'999990.00') ||','||
              to_char(COMP_PAGO.VAL_NETO_COMP_PAGO + COMP_PAGO.VAL_REDONDEO_COMP_PAGO,'999990.00') DETALLE_RESUMEN
       FROM   VTA_COMP_PAGO COMP_PAGO,
              VTA_PEDIDO_VTA_CAB CAB,
              VTA_FORMA_PAGO_PEDIDO VFPP,
              CON_PED_VTA_CLI CON_PED,
              CON_CLI_CONV CLI_CONV,
              CON_MAE_CLIENTE MAE_CLI,
              PBL_LOCAL LOCAL,
              CON_MAE_CONVENIO MAE_CONVENIO,
              CON_MAE_EMPRESA  MAE_EMPRESA
       WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CAB.IND_PED_CONVENIO = INDICADOR_SI
       AND    CAB.IND_PEDIDO_ANUL = INDICADOR_NO
       --AND    CAB.SEC_MOV_CAJA IS NOT NULL
       AND    CAB.EST_PED_VTA = 'C'
       AND    MAE_EMPRESA.COD_EMPRESA = cCodConvenio_in
       AND    VFPP.COD_FORMA_PAGO IN (       SELECT VP.COD_FORMA_PAGO
                                             FROM   CON_MAE_EMPRESA ME,
                                                    VTA_FORMA_PAGO VP,
                                                    CON_MAE_CONVENIO MC
                                             WHERE  ME.COD_EMPRESA = cCodConvenio_in
                                             AND    ME.COD_EMPRESA = MC.COD_CONVENIO
                                             AND    MC.COD_CONVENIO = VP.COD_CONVENIO )
       --AND    CAB.FEC_PED_VTA BETWEEN TO_DATE('01/05/2007' || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
  	   --AND    TO_DATE('18/06/2007' || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
       AND    CAB.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
  		 AND    TO_DATE(cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
       AND    CAB.COD_GRUPO_CIA = CON_PED.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = CON_PED.COD_LOCAL
       AND    CAB.NUM_PED_VTA = CON_PED.NUM_PED_VTA
       AND    CAB.COD_GRUPO_CIA = COMP_PAGO.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = COMP_PAGO.COD_LOCAL
       AND    CAB.NUM_PED_VTA = COMP_PAGO.NUM_PED_VTA
       AND    CAB.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = LOCAL.COD_LOCAL
       AND    CON_PED.COD_CONVENIO = CLI_CONV.COD_CONVENIO
       AND    CON_PED.COD_CLI = CLI_CONV.COD_CLI
       AND    CON_PED.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    CON_PED.COD_LOCAL = LOCAL.COD_LOCAL
       AND    CON_PED.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    CON_PED.COD_LOCAL = LOCAL.COD_LOCAL
       AND    COMP_PAGO.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    COMP_PAGO.COD_LOCAL = LOCAL.COD_LOCAL
       AND    CLI_CONV.COD_CLI = MAE_CLI.COD_CLI
       AND    CLI_CONV.COD_CONVENIO = MAE_CONVENIO.COD_CONVENIO
       AND    MAE_CONVENIO.COD_EMPRESA = MAE_EMPRESA.COD_EMPRESA
       AND    CAB.COD_GRUPO_CIA = VFPP.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = VFPP.COD_LOCAL
       AND    CAB.NUM_PED_VTA = VFPP.NUM_PED_VTA
       AND    VFPP.COD_GRUPO_CIA = COMP_PAGO.COD_GRUPO_CIA
       AND    VFPP.COD_LOCAL = COMP_PAGO.COD_LOCAL
       AND    VFPP.NUM_PED_VTA  = COMP_PAGO.NUM_PED_VTA
       ORDER BY CLI_CONV.COD_TRAB_CONV,COMP_PAGO.NUM_COMP_PAGO;

       v_curDetalleComp curDetalleComp%ROWTYPE;
       v_vNombreArchivo VARCHAR2(50);

  BEGIN
      v_vNombreArchivo := vPrefijo||cCodConvenio_in||'mf'||to_char(SYSDATE,'yy')||to_char(SYSDATE,'MM')||'detallado'||'.CSV';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO, 'LOCAL'|| ',' || 'DESCRIPCION'||',' ||'CODIGO'||',' ||'COLABORADOR'||',' ||'COMPROBANTE'||',' ||'NETO PEDIDO'||',' ||'MONTO CREDITO'||',' ||'MONTO COMPROBANTE');
      FOR v_curDetalleComp IN curDetalleComp
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_curDetalleComp.DETALLE_RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
      DBMS_OUTPUT.PUT_LINE('SE GENERO EL ARCHIVO DE INTERFACE CONVENIO DETTALLADO DE COMPROBANTES CORRECTAMENTE');
    END;

    FUNCTION INT_OBTIENE_FECHA_INICIO_FACT(cCodGrupoCia_in IN CHAR,
                                           cCodEmpresa_in IN CHAR)
    RETURN CHAR

    IS
    v_FechaInicio CHAR(10);
    BEGIN
      SELECT TO_CHAR(CF.FEC_INI_CICLO_FACT,'dd/MM/yyyy') INTO v_FechaInicio
      FROM   CON_MAE_EMPRESA  ME,
             CONV_CICLO_FACT CF
      WHERE  ME.COD_EMPRESA = cCodEmpresa_in
      AND    CF.COD_EMPRESA = ME.COD_EMPRESA
      AND    CF.FEC_PROCESO_CICLO_FACT IS NULL
      AND    CF.SEC_CICLO_FACT IN (SELECT MIN(CF.SEC_CICLO_FACT)
                                   FROM   CONV_CICLO_FACT CF
                                   WHERE  CF.COD_EMPRESA = cCodEmpresa_in
                                   AND    CF.FEC_PROCESO_CICLO_FACT IS NULL);
      RETURN v_FechaInicio;
    END;

    FUNCTION INT_OBTIENE_FECHA_FIN_FACT(cCodGrupoCia_in IN CHAR,
                                        cCodEmpresa_in IN CHAR)
    RETURN CHAR

    IS
    v_FechaFin CHAR(10);
    BEGIN
      SELECT TO_CHAR(CF.FEC_FIN_CICLO_FACT,'dd/MM/yyyy') INTO v_FechaFin
      FROM   CON_MAE_EMPRESA  ME,
             CONV_CICLO_FACT CF
      WHERE  ME.COD_EMPRESA = cCodEmpresa_in
      AND    CF.COD_EMPRESA = ME.COD_EMPRESA
      AND    CF.FEC_PROCESO_CICLO_FACT IS NULL
      AND    CF.SEC_CICLO_FACT IN (SELECT MIN(CF.SEC_CICLO_FACT)
                                   FROM   CONV_CICLO_FACT CF
                                   WHERE  CF.COD_EMPRESA = cCodEmpresa_in
                                   AND    CF.FEC_PROCESO_CICLO_FACT IS NULL);
      RETURN v_FechaFin;
    END;

    FUNCTION INT_OBTIENE_COMP_PEDIDO (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNumPedVta IN CHAR)
    RETURN VARCHAR2

    IS
    v_cadenaComp VARCHAR2(1000);
       CURSOR comp IS
       SELECT DECODE(VTA_CAB.TIP_COMP_PAGO,'01','B','02','F')DOC,
              COMP_PAGO.NUM_COMP_PAGO
       FROM   VTA_PEDIDO_VTA_CAB VTA_CAB,
              VTA_COMP_PAGO COMP_PAGO
       WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    VTA_CAB.COD_LOCAL = cCodLocal_in
       AND    VTA_CAB.NUM_PED_VTA = cNumPedVta
       AND    COMP_PAGO.COD_GRUPO_CIA =  VTA_CAB.COD_GRUPO_CIA
       AND    COMP_PAGO.COD_LOCAL = VTA_CAB.COD_LOCAL
       AND    COMP_PAGO.NUM_PED_VTA =  VTA_CAB.NUM_PED_VTA ;
       v_comp comp%ROWTYPE    ;

      BEGIN

        FOR v_comp IN comp
        LOOP
          v_cadenaComp:= v_cadenaComp|| ' - ' ||  v_comp.DOC || '; ' ||v_comp.num_comp_pago ;
        END LOOP;

        RETURN substr(v_cadenaComp,4);
    END;


  PROCEDURE INT_CREDITOS_PEDIDO(cCodGrupoCia_in IN CHAR,
                                cCodConvenio_in IN CHAR,
                                cFechaInicio_in IN CHAR,
                                cFechaFin_in IN CHAR)
  AS
     CURSOR curDetalleComp IS

       SELECT LOCAL.COD_LOCAL ||' - '||LOCAL.DESC_LOCAL ||','||
              MAE_CONVENIO.DESC_LARGA_CONV ||','||
              CLI_CONV.COD_TRAB_CONV ||','||
              NVL(TRIM(MAE_CLI.NOM_COMPLETO),NVL(MAE_CLI.APE_PAT_CLI,' ') ||' '|| NVL(MAE_CLI.APE_MAT_CLI,' ') ||' '|| NVL(MAE_CLI.NOM_CLI,' ')) ||','||
              CAB.NUM_PED_VTA ||','||
              INT_OBTIENE_COMP_PEDIDO(LOCAL.COD_GRUPO_CIA,LOCAL.COD_LOCAL,CAB.NUM_PED_VTA) ||','||
              to_char(VFPP.IM_TOTAL_PAGO,'999990.00') DETALLE_RESUMEN
       FROM   VTA_PEDIDO_VTA_CAB CAB,
              VTA_FORMA_PAGO_PEDIDO VFPP,
              CON_PED_VTA_CLI CON_PED,
              CON_CLI_CONV CLI_CONV,
              CON_MAE_CLIENTE MAE_CLI,
              PBL_LOCAL LOCAL,
              CON_MAE_CONVENIO MAE_CONVENIO,
              CON_MAE_EMPRESA  MAE_EMPRESA
       WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CAB.IND_PED_CONVENIO = INDICADOR_SI
       AND    CAB.IND_PEDIDO_ANUL = INDICADOR_NO
       AND    CAB.EST_PED_VTA = 'C'
       AND    MAE_EMPRESA.COD_EMPRESA = cCodConvenio_in
       AND    VFPP.COD_FORMA_PAGO IN (       SELECT VP.COD_FORMA_PAGO
                                             FROM   CON_MAE_EMPRESA ME,
                                                    VTA_FORMA_PAGO VP,
                                                    CON_MAE_CONVENIO MC
                                             WHERE  ME.COD_EMPRESA = cCodConvenio_in
                                             AND    ME.COD_EMPRESA = MC.COD_CONVENIO
                                             AND    MC.COD_CONVENIO = VP.COD_CONVENIO )
       AND    CAB.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
  		 AND    TO_DATE(cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
       AND    CAB.COD_GRUPO_CIA = CON_PED.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = CON_PED.COD_LOCAL
       AND    CAB.NUM_PED_VTA = CON_PED.NUM_PED_VTA
       AND    CAB.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = LOCAL.COD_LOCAL
       AND    CON_PED.COD_CONVENIO = CLI_CONV.COD_CONVENIO
       AND    CON_PED.COD_CLI = CLI_CONV.COD_CLI
       AND    CON_PED.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    CON_PED.COD_LOCAL = LOCAL.COD_LOCAL
       AND    CON_PED.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
       AND    CON_PED.COD_LOCAL = LOCAL.COD_LOCAL
       AND    CLI_CONV.COD_CLI = MAE_CLI.COD_CLI
       AND    CLI_CONV.COD_CONVENIO = MAE_CONVENIO.COD_CONVENIO
       AND    MAE_CONVENIO.COD_EMPRESA = MAE_EMPRESA.COD_EMPRESA
       AND    CAB.COD_GRUPO_CIA = VFPP.COD_GRUPO_CIA
       AND    CAB.COD_LOCAL = VFPP.COD_LOCAL
       AND    CAB.NUM_PED_VTA = VFPP.NUM_PED_VTA
       ORDER BY CLI_CONV.COD_TRAB_CONV,CAB.NUM_PED_VTA;

       v_curDetalleComp curDetalleComp%ROWTYPE;
       v_vNombreArchivo VARCHAR2(50);

  BEGIN
      v_vNombreArchivo := vPrefijo||cCodConvenio_in||'mf'||to_char(SYSDATE,'yy')||to_char(SYSDATE,'MM')||'pedidos'||'.CSV';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO, 'LOCAL'|| ',' || 'DESCRIPCION'||',' ||'CODIGO'||',' ||'COLABORADOR'||',' ||'PEDIDO'||',' ||'COMPROBANTES'||',' ||'MONTO CREDITO');
      FOR v_curDetalleComp IN curDetalleComp
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_curDetalleComp.DETALLE_RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
      DBMS_OUTPUT.PUT_LINE('SE GENERO EL ARCHIVO DE INTERFACE CONVENIO POR PEDIDOS Y MONTOS DE CREDITO');
    END;


end PTOVENTA_INT_CONV;
/

