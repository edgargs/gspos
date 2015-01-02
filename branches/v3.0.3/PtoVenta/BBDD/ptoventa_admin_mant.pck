CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_ADMIN_MANT" is

  -- Author  : LMESIA
  -- Created : 19/07/2006 09:41:02 a.m.
  -- Purpose :
  POS_INICIO		      CHAR(1):='I';
  -- Public type declarations
  TYPE FarmaCursor IS REF CURSOR;

   C_C_RETORNO_EXITO   CHAR(1) := '1';
   C_C_RETORNO_ERROR_1 CHAR(1) := '2';
   C_C_RETORNO_ERROR_2 CHAR(1) := '3';
   --C_C_RETORNO_ERROR_3 CHAR(1) := '4';


  -- Public function and procedure declarations

  --Descripcion: Obtiene las series asignadas al local.
  --Fecha       Usuario		Comentario
  --12/07/2006  ERIOS    	Creación
  --05/07/2008  ASOLIS    MODIFICACION
  FUNCTION GET_PARAMETROS_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Actualiza los parametros del local.
  --Fecha       Usuario		Comentario
  --12/07/2006  ERIOS    	Creación
  --05/09/2007  DUBILLUZ  Modificacion
  --05/07/2008  ASOLIS    MODIFICACION
  PROCEDURE ACTUALIZAR_PARAMETROS_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vRutaImpReporte_in IN VARCHAR2,
                                        nMinPedPendientes_in IN NUMBER,
                                        vIndCambioPrecio_in IN CHAR,
                                       vIndCambioModeloImpresora_in IN CHAR,
                                        vIdUsu_in IN VARCHAR2);


  --Descripcion: LISTA LOS INDICARES DE HORARIO
  --Fecha       Usuario		Comentario
  --23/04/2007  PAULO    	Creación
  FUNCTION LISTA_INDICADORES_HORARIO
  RETURN FarmaCursor;

  --Descripcion: obtiene el secuencial por usuario
  --Fecha       Usuario		Comentario
  --23/04/2007  PAULO    	Creación
 /* FUNCTION GET_SECUENCIAL_HORARIOS(cCodGrupoCia_in      IN CHAR,
                                   cCodLocal_in         IN CHAR,
                                   cSecUsulocal_in      IN CHAR)
  RETURN CHAR;*/

  --Descripcion: LISTA LAS HORAS DEL CONTROL DE USUARUOS
  --Fecha       Usuario		Comentario
  --23/04/2007  PAULO    	Creación
 /* FUNCTION LISTA_CONTROL_HORAS_USU(cCodGrupoCia_in      IN CHAR,
                                  cCodLocal_in          IN CHAR,
                                  cSecUsulocal_in       IN CHAR)
  RETURN FarmaCursor;   */

 /* PROCEDURE GRABA_CONTROL_HORAS (cCodGrupoCia_in      IN CHAR,
                                 cCodLocal_in         IN CHAR,
                                 cSecUsulocal_in      IN CHAR,
                                 cIndControl_in       IN CHAR)  ;    */

  --Descripcion: Obtiene los motivos del control de horas
  --Fecha       Usuario		Comentario
  --27/04/2007  LREQUE    Creación
   FUNCTION ADMMANT_OBTIENE_MOTIVOS_CTRL
   RETURN FarmaCursor;

  --Descripcion: Verifica si el motivo ingresado es correcto
  --Fecha       Usuario		Comentario
  --27/04/2007  LREQUE    Creación
   FUNCTION ADMMANT_VERIFICA_INGRESO_CTRL(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodMotivo_in   IN CHAR,
                                          cSecUsu_in      IN CHAR)
   RETURN CHAR;

  --Descripcion: Registra en el control de horas
  --Fecha       Usuario		Comentario
  --27/04/2007  LREQUE    Creación
   PROCEDURE ADMMANT_INGRESA_CONTROL(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecUsu_in      IN CHAR,
                                     cCodMotES_in    IN CHAR,
                                     cObservac_in    IN CHAR);

  --Descripcion: Obtiene el listado de control de horas
  --Fecha       Usuario		Comentario
  --27/04/2007  LREQUE    Creación
   FUNCTION ADMMANT_OBTIENE_CONTROL_HORAS(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cSecUsu_ini     IN CHAR)
   RETURN FarmaCursor;

  --Descripcion: Actualiza la ruta de Impresora en Delivery
  --Fecha       Usuario		Comentario
  --05/09/2007  DUBILLUZ  Creación
  PROCEDURE ACTUALIZA_IMP_DELIVERY_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vRutaImpReporte_in IN VARCHAR2,
                                        vIdUsu_in IN VARCHAR2);

  --Fecha       Usuario		Comentario
  --05/09/2007  dubilluz   creacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);



end PTOVENTA_ADMIN_MANT;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_ADMIN_MANT" is

  FUNCTION GET_PARAMETROS_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curParametros FarmaCursor;
  BEGIN
    OPEN curParametros FOR



           SELECT nvl(L.RUTA_IMPR_REPORTE,' ')|| 'Ã' ||
           L.CANT_MAX_MIN_PED_PENDIENTE||' ' || 'Ã' ||
          nvl(DECODE(L.IND_HABILITADO,'S','SI','NO'),' ') || 'Ã' ||
          nvl(G.DESC_CORTA,' ')


           FROM PBL_LOCAL L,PBL_TAB_GRAL G

           WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in

           AND L.COD_LOCAL = cCodLocal_in
           AND G.LLAVE_TAB_GRAL=L.TIPO_IMPR_TERMICA
           AND G.COD_TAB_GRAL='MODELO_IMP_TERMICA'
           AND G.COD_APL='PTO_VENTA';




    RETURN curParametros;
  END;

  /****************************************************************************/
  /*PROCEDURE ACTUALIZAR_PARAMETROS_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vRutaImpReporte_in IN VARCHAR2,
                                        nMinPedPendientes_in IN NUMBER,
                                        vIndCambioPrecio_in IN CHAR,
                                        vIdUsu_in IN VARCHAR2)
  AS
  BEGIN
    UPDATE PBL_LOCAL SET USU_MOD_LOCAL = vIdUsu_in,FEC_MOD_LOCAL = SYSDATE,
          RUTA_IMPR_REPORTE = vRutaImpReporte_in,
          CANT_MAX_MIN_PED_PENDIENTE = nMinPedPendientes_in,
          IND_HABILITADO = vIndCambioPrecio_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
  END;*/
  --05/09/2007  dubilluz  modificacion

  PROCEDURE ACTUALIZAR_PARAMETROS_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vRutaImpReporte_in IN VARCHAR2,
                                        nMinPedPendientes_in IN NUMBER,
                                        vIndCambioPrecio_in IN CHAR,
                                        vIndCambioModeloImpresora_in IN CHAR,
                                        vIdUsu_in IN VARCHAR2)
  AS
  v_cambio_impresora char(1);
  v_Indicador_Linea     Varchar2(3453);
  v_DATOS_LOCAL         Varchar2(3453);
  BEGIN
  ----Verifica si se cambio la Impresora
  SELECT DECODE(UPPER(trim(L.RUTA_IMPR_REPORTE)),UPPER(trim(vRutaImpReporte_in)),'N','S')
         INTO v_cambio_impresora
  FROM   PBL_LOCAL l
  WHERE  l.cod_grupo_cia = cCodGrupoCia_in and
         l.cod_local     = cCodLocal_in;
  --------------------------------------
  dbms_output.put_line('¿cambió la ruta de Impresora? '|| v_cambio_impresora);
  ---
    UPDATE PBL_LOCAL SET USU_MOD_LOCAL = vIdUsu_in,FEC_MOD_LOCAL = SYSDATE,
          RUTA_IMPR_REPORTE = vRutaImpReporte_in,
          CANT_MAX_MIN_PED_PENDIENTE = nMinPedPendientes_in,
          IND_HABILITADO = vIndCambioPrecio_in,
          TIPO_IMPR_TERMICA = vIndCambioModeloImpresora_in


    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
   --------------------------------------
   IF v_cambio_impresora = 'S' THEN --ACTUALIZA EN DELIVERY LA RUTA DE IMPRESORA
       --Atualizando el Indicador de Linea con el Local
       FARMA_GRAL.INV_ACTUALIZA_IND_LINEA(vIdUsu_in,cCodLocal_in,cCodGrupoCia_in);
       v_Indicador_Linea :=FARMA_GRAL.INV_OBTIENE_IND_LINEA(cCodLocal_in,cCodGrupoCia_in);
         dbms_output.put_line('v_Indicador_Linea'||v_Indicador_Linea);
       IF v_Indicador_Linea = 'FALSE' THEN
        --ENVIA MAIL
         dbms_output.put_line('envio correo');
        SELECT L.COD_LOCAL ||'-'||L.DESC_CORTA_LOCAL INTO v_DATOS_LOCAL
        FROM PBL_LOCAL L
        WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in AND
              L.COD_LOCAL     = cCodLocal_in;
        INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ACTUALIZACION INCOMPLETA ',
                                        'ALERTA',
                                        'NO SE ACTUALIZO LA RUTA DE LA IMPRESORA DE REPORTE DEL LOCAL '||v_DATOS_LOCAL||' EN DELIVERY.' ||
                                        '<L> NO SE ENCONTRO CONEXION DEL LOCAL CON MATRIZ </L>');
       elsif v_Indicador_Linea = 'TRUE' THEN
        --SI HAY CONEXION CON DELIVERY
         ACTUALIZA_IMP_DELIVERY_LOCAL(cCodGrupoCia_in,cCodLocal_in, vRutaImpReporte_in,vIdUsu_in);
       END IF;
    end if;
   ---------------------------------------------------
  END;

  /****************************************************************************/

  FUNCTION LISTA_INDICADORES_HORARIO
  RETURN FarmaCursor
  IS
    curParametros FarmaCursor;
  BEGIN
    OPEN curParametros FOR
      SELECT T.LLAVE_TAB_GRAL|| 'Ã' ||
             T.DESC_CORTA
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'CONTROL_HORARIOS';
   RETURN CURPARAMETROS;
 END;

 /****************************************************************************/
/*  FUNCTION GET_SECUENCIAL_HORARIOS(cCodGrupoCia_in      IN CHAR,
                                   cCodLocal_in         IN CHAR,
                                   cSecUsulocal_in      IN CHAR)
  RETURN CHAR
  IS
    v_cSecIntCe PBL_CONTROL_HORAS_LOCAL.SEC_CONTROL_HORAS%TYPE;
  BEGIN
       SELECT FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(COUNT(P.SEC_CONTROL_HORAS) + 1, 3, '0', POS_INICIO)
       INTO   v_cSecIntCe
       FROM   PBL_CONTROL_HORAS_LOCAL P
       WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    P.COD_LOCAL = cCodLocal_in
       AND    P.SEC_USU_LOCAL = cSecUsulocal_in
       AND    trunc(P.FECHA_REGISTRO) = TO_CHAR(SYSDATE,'dd/MM/yyyy') ;
    RETURN v_cSecIntCe;
  END;
*/
 /****************************************************************************/
 /*FUNCTION LISTA_CONTROL_HORAS_USU(cCodGrupoCia_in      IN CHAR,
                                  cCodLocal_in         IN CHAR,
                                  cSecUsulocal_in       IN CHAR)
  RETURN FarmaCursor
  IS
    curParametros FarmaCursor;
  BEGIN
    OPEN curParametros FOR
         SELECT TO_CHAR(P.FECHA_REGISTRO,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                T.DESC_CORTA || 'Ã' ||
                P.SEC_USU_LOCAL
         FROM   PBL_CONTROL_HORAS_LOCAL P,
                PBL_TAB_GRAL T
         WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    P.COD_LOCAL = cCodLocal_in
         AND    P.SEC_USU_LOCAL = cSecUsulocal_in
         AND    T.COD_APL = 'PTO_VENTA'
         AND    T.COD_TAB_GRAL = 'CONTROL_HORARIOS'
         AND    P.IND_CONTROL = T.LLAVE_TAB_GRAL;
    RETURN curParametros;
  END;*/

 /****************************************************************************/
  /*PROCEDURE GRABA_CONTROL_HORAS (cCodGrupoCia_in      IN CHAR,
                                 cCodLocal_in         IN CHAR,
                                 cSecUsulocal_in      IN CHAR,
                                 cIndControl_in       IN CHAR)
  IS
  v_sec CHAR(3);
  BEGIN
       v_sec:= GET_SECUENCIAL_HORARIOS(cCodGrupoCia_in,cCodLocal_in,cSecUsulocal_in);

       INSERT INTO PBL_CONTROL_HORAS_LOCAL(COD_GRUPO_CIA,COD_LOCAL,SEC_USU_LOCAL,SEC_CONTROL_HORAS,IND_CONTROL)
                   VALUES(cCodGrupoCia_in,cCodLocal_in,cSecUsulocal_in,v_sec,cIndControl_in);
  END;*/

/********************************************************************************************/

   FUNCTION ADMMANT_OBTIENE_MOTIVOS_CTRL
   RETURN FarmaCursor
   IS
     curMotivo FarmaCursor;
   BEGIN
        OPEN curMotivo FOR
          SELECT    MES.COD_MOT_ES   || 'Ã' ||
                    MES.DESC_MOT_ES
          FROM      PBL_MOTIVO_E_S MES
          WHERE     MES.EST_MOTIVO = 'A'
          ORDER BY  MES.DESC_MOT_ES ASC;
        RETURN curMotivo;
   END ADMMANT_OBTIENE_MOTIVOS_CTRL;

------------------------------------------------------------------------

   FUNCTION ADMMANT_VERIFICA_INGRESO_CTRL(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodMotivo_in   IN CHAR,
                                          cSecUsu_in      IN CHAR)
   RETURN CHAR
   IS
  --Variables para el motivo
  vCodMot_mot      PBL_MOTIVO_E_S.COD_MOT_ES%TYPE;
  vCodMotSal_mot   PBL_MOTIVO_E_S.COD_MOT_SAL%TYPE;
  vDescMotEs_mot   PBL_MOTIVO_E_S.DESC_MOT_ES%TYPE;
  vIndLimReg_mot   PBL_MOTIVO_E_S.IND_LIM_REG%TYPE;
  vInfES_mot       PBL_MOTIVO_E_S.IND_E_S%TYPE;

  --Variables locales del procedimiento
  vCont            INTEGER;
  vCodMotSal       PBL_MOTIVO_E_S.COD_MOT_SAL%TYPE;
   BEGIN
        SELECT    MES.COD_MOT_ES,
                  MES.DESC_MOT_ES,
                  MES.COD_MOT_SAL,
                  MES.IND_LIM_REG,
                  MES.IND_E_S  INTO vCodMot_mot, vDescMotEs_mot, vCodMotSal_mot, vIndLimReg_mot, vInfES_mot
        FROM      PBL_MOTIVO_E_S MES
        WHERE     MES.COD_MOT_ES = cCodMotivo_in;

        --Veces registrado en el control del dia
        SELECT    COUNT(*) INTO vCont
        FROM      PBL_CONTROL_HORAS_LOCAL CH
        WHERE     CH.COD_GRUPO_CIA                     = cCodGrupoCia_in
        AND       CH.COD_LOCAL                         = cCodLocal_in
        AND       TO_CHAR(CH.FEC_CONTROL,'dd/MM/yyyy') = TO_CHAR(SYSDATE,'dd/MM/yyyy')
        AND       CH.COD_MOT_ES                        = cCodMotivo_in
        AND       CH.SEC_USU_LOCAL                     = cSecUsu_in;

        IF vIndLimReg_mot = 'S' AND vCont = 1 THEN
           RETURN C_C_RETORNO_ERROR_1;
        ELSE
           SELECT MES.COD_MOT_SAL INTO vCodMotSal
           FROM   PBL_MOTIVO_E_S MES
           WHERE  MES.COD_MOT_ES = cCodMotivo_in;

           IF vCodMotSal IS NULL THEN
               RETURN C_C_RETORNO_EXITO;--DBMS_OUTPUT.put_line('Procede A');
           ELSE
               SELECT  COUNT(*) INTO vCont
               FROM    PBL_CONTROL_HORAS_LOCAL CH
               WHERE   CH.COD_GRUPO_CIA                     = cCodGrupoCia_in
               AND     CH.COD_LOCAL                         = cCodLocal_in
               AND     TO_CHAR(CH.FEC_CONTROL,'dd/MM/yyyy') = TO_CHAR(SYSDATE,'dd/MM/yyyy')
               AND     CH.COD_MOT_ES                        = vCodMotSal
               AND     CH.SEC_USU_LOCAL                     = cSecUsu_in;

               IF vCont = 0 THEN
                  RETURN C_C_RETORNO_ERROR_2;--DBMS_OUTPUT.put_line('No puede registrar este motivo.');
               ELSE
                  RETURN C_C_RETORNO_EXITO;--DBMS_OUTPUT.put_line('Procede B');
               END IF;
           END IF;
        END IF;
   END ADMMANT_VERIFICA_INGRESO_CTRL;

------------------------------------------------------------------------

   PROCEDURE ADMMANT_INGRESA_CONTROL(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecUsu_in      IN CHAR,
                                     cCodMotES_in    IN CHAR,
                                     cObservac_in    IN CHAR)
   IS
   BEGIN
        INSERT INTO PBL_CONTROL_HORAS_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_MOT_ES,SEC_USU_LOCAL,OBSERVAC)
               VALUES(cCodGrupoCia_in,cCodLocal_in,cCodMotES_in,cSecUsu_in,cObservac_in);
   END ADMMANT_INGRESA_CONTROL;

------------------------------------------------------------------------

   FUNCTION ADMMANT_OBTIENE_CONTROL_HORAS(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cSecUsu_ini     IN CHAR)
   RETURN FarmaCursor
   IS
   curControl FarmaCursor;
   BEGIN
        OPEN curControl FOR
          SELECT   TO_CHAR(CHL.FEC_CONTROL,'dd/MM/yyyy HH24:mi:SS')   || 'Ã' ||
                   MES.DESC_MOT_ES                                    || 'Ã' ||
                   NVL(CHL.OBSERVAC,'-NINGUNA-')
          FROM     PBL_CONTROL_HORAS_LOCAL CHL,
                   PBL_MOTIVO_E_S          MES
          WHERE    CHL.COD_GRUPO_CIA                     = cCodGrupoCia_in
          AND      CHL.COD_LOCAL                         = cCodLocal_in
          AND      CHL.SEC_USU_LOCAL                     = cSecUsu_ini
          AND      TO_CHAR(CHL.FEC_CONTROL,'dd/MM/yyyy') = TO_CHAR(SYSDATE,'dd/MM/yyyy')
          AND      CHL.COD_MOT_ES = MES.COD_MOT_ES;

        RETURN curControl;
   END ADMMANT_OBTIENE_CONTROL_HORAS;

  --Actualiza la ruta de la Impresora en Delivery
  --05/09/2007  dubilluz  creacion
  PROCEDURE ACTUALIZA_IMP_DELIVERY_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        vRutaImpReporte_in IN VARCHAR2,
                                        vIdUsu_in IN VARCHAR2)
   IS
   BEGIN
    EXECUTE IMMEDIATE
    'UPDATE pbl_local@XE_DEL_999
     SET ruta_impr_reporte = :1,
         usu_mod_local     = :2,
         fec_mod_local     = sysdate
     WHERE   cod_grupo_cia = :3 and
             cod_local     = :4
    ' USING vRutaImpReporte_in,vIdUsu_in,cCodGrupoCia_in,cCodLocal_in;
   END ACTUALIZA_IMP_DELIVERY_LOCAL;


  --Envia Correo
  --05/09/2007  dubilluz  creacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_IMP_LOCAL;
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
                          vAsunto_in||v_vDescLocal,
                          vTitulo_in,
                          mesg_body,
                          CCReceiverAddress,
                          FARMA_EMAIL.GET_EMAIL_SERVER,
                          true);

  END;


end PTOVENTA_ADMIN_MANT;
/

