CREATE OR REPLACE PACKAGE PTOVENTA."FARMA_SECURITY" AS

  /**
  * Copyright (c) 2006 MiFarma S.A.C.
  *
  * Entorno de Desarrollo : Oracle9i
  * Nombre del Paquete    : FARMA_SECURITY
  *
  * Histórico de Creación/Modificación
  * LMESIA       01.02.2006   Creación
  *
  * @author Luis Mesia Rivera
  * @version 1.0
  */
  -- Variable que almacenara el resultado de los query's
  TYPE FarmaCursor IS REF CURSOR;

  CANT_MAX_MINUTOS_PED_PEND PBL_LOCAL.CANT_MAX_MIN_PED_PENDIENTE%TYPE := 30;

  /* 1. ************************************************************************ */
  /* VALORES 01 : Usuario OK
         02 : Usuario Inactivo en el Local
       03 : Usuario no registrado en el Local
       04 : Clave Errada
       05 : Usuario No Existe
  */
  FUNCTION VERIFICA_USUARIO(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cCodUsu_in      IN CHAR,
                            cClaveUsu_in    IN CHAR) RETURN CHAR;

  FUNCTION VERIFICA_USUARIO_LOGIN(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodUsu_in      IN CHAR,
                                  cClaveUsu_in    IN CHAR) RETURN CHAR;

  /* 2. ************************************************************************ */
  /* 01 : Usuario Rol OK
     02 : Rol Inactivo para el Usuario
   03 : Rol No registrado para el Usuario
  */
  /*
    FUNCTION VERIFICA_ROL(codigocompania_in   IN CMTS_USUARIO_ROL.CO_COMPANIA%TYPE,
                              codigousuario_in IN CMTS_USUARIO_ROL.NU_SEC_USUARIO%TYPE,
                              codigorol_in     IN CMTS_USUARIO_ROL.CO_ROL%TYPE)
                RETURN CHAR;
  */
  /*3. ************************************************************************ */
  FUNCTION LISTA_ROL(cCodGrupoCia_in IN CHAR,
                     cCodLocal_in    IN CHAR,
                     cCodUsu_in      IN CHAR) RETURN FarmaCursor;

  /*4. ************************************************************************ */
  FUNCTION OBTIENE_DATO_USUARIO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodUsu_in      IN CHAR) RETURN FarmaCursor;

  FUNCTION OBTIENE_DATO_USUARIO_LOGIN(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodUsu_in      IN CHAR)
    RETURN FarmaCursor;

  /****************************************************************************/
  FUNCTION OBTIENE_DATO_LOCAL(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR) RETURN FarmaCursor;

  /* 3. ************************************************************************ */
  --Descripcion: Registra el primer ingreso de un QF Regente del local en el dia.
  --Fecha       Usuario   Comentario
  --17/03/2008  JOLIVA     Creación
  PROCEDURE REG_PRIMER_INGR_LOCAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodUsu_in      IN CHAR);

  PROCEDURE ENVIA_EMAIL_REGISTRO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR);

  /************************************************************************* */
  --Descripcion: Se cambia el password del usuario
  --Fecha       Usuario   Comentario
  --04/09/2009  JCORTEZ     Creación
  PROCEDURE CAMBIO_CLAVE(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in    IN CHAR,
                        cIdUsu_in       IN CHAR,
                        cSecUsu_in      IN CHAR,
                        cNewClave       IN CHAR);

  /************************************************************************* */
  --Descripcion: Se valida cambio de clave por rango de dias
  --Fecha       Usuario   Comentario
  --04/09/2009  JCORTEZ     Creación
  FUNCTION VALIDA_CAMBIO_CLAVE(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in     IN CHAR,
                              cSecUsuLoc_in    IN CHAR)
  RETURN CHAR;

	  --Descripcion: Se muestra version del sistema
	  --Fecha       Usuario   Comentario
	  --17/03/2013  ERIOS     Creacion
	PROCEDURE SET_MODULO_VERSION(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR,
								vModulo_in IN VARCHAR, vVersion_in IN VARCHAR, vCompilacion_in IN CHAR);

END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."FARMA_SECURITY" IS

  /* ************************************************************************ */
  /* VALORES 01 : Usuario OK
             02 : Usuario Inactivo en el Local
             03 : Usuario no registrado en el Local
             04 : Clave Errada
             05 : Usuario No Existe
             98 : Version de aplicacion no valida
  */
  FUNCTION VERIFICA_USUARIO(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cCodUsu_in      IN CHAR,
                            cClaveUsu_in    IN CHAR) RETURN CHAR IS
    v_vClaveUsu  PBL_USU_LOCAL.CLAVE_USU%TYPE;
    v_cEstUsu    PBL_USU_LOCAL.EST_USU%TYPE;
    v_cResultado CHAR(2);
    v_iVersion   integer := 0;
  BEGIN
    -- Valida la version del aplicativo
    select count(1) into v_iVersion
      from rel_aplicacion_version
     where cod_aplicacion = '01'
       and num_version = trim(sys_context('USERENV', 'ACTION'))
       and flg_permitido = '1';

    if v_iVersion > 0 then
        SELECT CLAVE_USU, EST_USU
          INTO v_vClaveUsu, v_cEstUsu
          FROM PBL_USU_LOCAL
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND COD_LOCAL = cCodLocal_in
           AND SEC_USU_LOCAL = cCodUsu_in;
        IF (RTRIM(LTRIM(cClaveUsu_in)) = RTRIM(LTRIM(v_vClaveUsu))) THEN
          IF (v_cEstUsu = 'A') THEN
            v_cResultado := '01';
            REG_PRIMER_INGR_LOCAL(cCodGrupoCia_in, cCodLocal_in, cCodUsu_in);
          ELSE
            v_cResultado := '02';
          END IF;
        ELSE
          v_cResultado := '04';
        END IF;
    else
        v_cResultado := '98';
    end if;

    RETURN v_cResultado;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      v_cResultado := '05';
      RETURN v_cResultado;
  END;

  /* ************************************************************************ */
  /* VALORES 01 : Usuario OK
             02 : Usuario Inactivo en el Local
             03 : Usuario no registrado en el Local
             04 : Clave Errada
             05 : Usuario No Existe
             98 : Version de aplicacion no valida
  */
  FUNCTION VERIFICA_USUARIO_LOGIN(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodUsu_in      IN CHAR,
                                  cClaveUsu_in    IN CHAR) RETURN CHAR IS
    v_vClaveUsu  PBL_USU_LOCAL.CLAVE_USU%TYPE;
    v_cEstUsu    PBL_USU_LOCAL.EST_USU%TYPE;
    v_cResultado CHAR(2);
    v_iVersion   integer := 0;
  BEGIN
    -- Valida la version del aplicativo
    select count(1) into v_iVersion
      from rel_aplicacion_version
     where cod_aplicacion = '01'
       and num_version = trim(sys_context('USERENV', 'ACTION'))
       and flg_permitido = '1';

    if v_iVersion > 0 then

        SELECT RTRIM(CLAVE_USU), EST_USU
          INTO v_vClaveUsu, v_cEstUsu
          FROM PBL_USU_LOCAL
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND COD_LOCAL = cCodLocal_in
              --AND SEC_USU_LOCAL = cCodUsu_in
           AND (TRIM(LOGIN_USU) = cCodUsu_in OR
               TRIM(SEC_USU_LOCAL) = cCodUsu_in);
        IF (cClaveUsu_in = v_vClaveUsu) THEN
          IF (v_cEstUsu = 'A') THEN
            v_cResultado := '01';
            REG_PRIMER_INGR_LOCAL(cCodGrupoCia_in, cCodLocal_in, cCodUsu_in);
          ELSE
            v_cResultado := '02';
          END IF;
        ELSE
          v_cResultado := '04';
        END IF;
    else
        v_cResultado := '98';
    end if;

    RETURN v_cResultado;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      v_cResultado := '05';
      RETURN v_cResultado;
  END;

  /* ************************************************************************ */

  FUNCTION LISTA_ROL(cCodGrupoCia_in IN CHAR,
                     cCodLocal_in    IN CHAR,
                     cCodUsu_in      IN CHAR) RETURN FarmaCursor IS
    curSecurity FarmaCursor;
  BEGIN
    OPEN curSecurity FOR
      SELECT NVL(COD_ROL, ' ')
        FROM PBL_ROL_USU
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND SEC_USU_LOCAL = cCodUsu_in;
    RETURN curSecurity;
  END;

  /* ************************************************************************ */

  FUNCTION OBTIENE_DATO_USUARIO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodUsu_in      IN CHAR) RETURN FarmaCursor IS
    curSecurity FarmaCursor;
  BEGIN
    OPEN curSecurity FOR
      SELECT NVL(LOGIN_USU, ' ') || 'Ã' || NVL(APE_PAT, ' ') || ' ' ||
             NVL(APE_MAT, ' ') || ',' || NVL(NOM_USU, ' ') || 'Ã' ||
             NVL(SEC_USU_LOCAL, ' ')
        FROM PBL_USU_LOCAL
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND SEC_USU_LOCAL = cCodUsu_in;
    RETURN curSecurity;
  END;

  /** *********************************************************************** */

  FUNCTION OBTIENE_DATO_USUARIO_LOGIN(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodUsu_in      IN CHAR)
    RETURN FarmaCursor IS
    curSecurity FarmaCursor;
  BEGIN
    OPEN curSecurity FOR
      SELECT NVL(SEC_USU_LOCAL, ' ') || 'Ã' || NVL(APE_PAT, ' ') || 'Ã' ||
             NVL(APE_MAT, ' ') || 'Ã' || NVL(NOM_USU, ' ') || 'Ã' ||
             NVL(LOGIN_USU, ' ')
        FROM PBL_USU_LOCAL
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND (TRIM(LOGIN_USU) = cCodUsu_in OR
             TRIM(SEC_USU_LOCAL) = cCodUsu_in);
    RETURN curSecurity;
  END;

  /** *********************************************************************** */
  FUNCTION OBTIENE_DATO_LOCAL(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    farcur FarmaCursor;
  BEGIN
    OPEN farcur FOR
      SELECT NVL(DESC_CORTA_LOCAL, ' ') || 'Ã' || NVL(DESC_LOCAL, ' ') || 'Ã' ||
             NVL(TIP_LOCAL, ' ') || 'Ã' || NVL(TIP_CAJA, ' ') || 'Ã' ||
             NVL(NOM_CIA, ' ') || 'Ã' || NVL(NUM_RUC_CIA, ' ') || 'Ã' ||
             NVL(CANT_MAX_MIN_PED_PENDIENTE, CANT_MAX_MINUTOS_PED_PEND) || 'Ã' ||
             NVL(RUTA_IMPR_REPORTE, ' ') || 'Ã' ||
             NVL(l.Ind_Habilitado, ' ') || 'Ã' ||
             NVL(L.DIREC_LOCAL_CORTA, ' ')
        FROM PBL_LOCAL L, PBL_CIA C
       WHERE L.COD_CIA = C.COD_CIA
         AND L.COD_GRUPO_CIA = cCodGrupoCia_in
         AND L.COD_LOCAL = cCodLocal_in;
    RETURN farcur;
  END;

  /** *********************************************************************** */
  PROCEDURE REG_PRIMER_INGR_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				                 cCodLocal_in    IN CHAR,
                                 cCodUsu_in    	IN CHAR)
  IS PRAGMA AUTONOMOUS_TRANSACTION;
     vIndEnvioMail PBL_REG_INGRESO_LOCAL.IND_ENVIO_MAIL%TYPE;
     vSecUsuLocal PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
  BEGIN

        INSERT INTO PBL_REG_INGRESO_LOCAL (COD_GRUPO_CIA, COD_LOCAL, FEC_DIA_REGISTRO, SEC_USU_LOCAL)
        SELECT U.COD_GRUPO_CIA, U.COD_LOCAL, TRUNC(SYSDATE), U.SEC_USU_LOCAL
        FROM PBL_USU_LOCAL U,
             CE_MAE_TRAB T,
             PBL_TAB_GRAL G
        WHERE U.COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND ( TRIM(LOGIN_USU) = cCodUsu_in OR TRIM(SEC_USU_LOCAL)= cCodUsu_in )
              AND T.COD_TRAB_RRHH = U.COD_TRAB_RRHH
              AND G.ID_TAB_GRAL = 187
              AND INSTR(LLAVE_TAB_GRAL, TRIM(T.COD_CARGO)) > 0
              AND TO_CHAR(SYSDATE,'HH24:MI:SS') >= '06:00:00'
              AND T.COD_CARGO IS NOT NULL;

        COMMIT;

        SELECT SEC_USU_LOCAL
        INTO vSecUsuLocal
        FROM PBL_USU_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND ( TRIM(LOGIN_USU) = cCodUsu_in OR TRIM(SEC_USU_LOCAL)= cCodUsu_in );

        SELECT I.IND_ENVIO_MAIL
        INTO vIndEnvioMail
        FROM PBL_REG_INGRESO_LOCAL I
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND FEC_DIA_REGISTRO = TRUNC(SYSDATE)
              AND SEC_USU_LOCAL = vSecUsuLocal;

        IF vIndEnvioMail = 'N' THEN
           ENVIA_EMAIL_REGISTRO(cCodGrupoCia_in, cCodLocal_in);

           UPDATE PBL_REG_INGRESO_LOCAL I
           SET I.IND_ENVIO_MAIL = 'S'
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND COD_LOCAL = cCodLocal_in
             AND FEC_DIA_REGISTRO = TRUNC(SYSDATE)
             AND SEC_USU_LOCAL = vSecUsuLocal;

        END IF;

        COMMIT;

--

  EXCEPTION
           WHEN OTHERS THEN
                NULL;
  END;


  /* ************************************************************************ */

  PROCEDURE ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                     vAsunto_in      IN CHAR,
                                     vTitulo_in      IN CHAR,
                                     vMensaje_in     IN CHAR,
                                     v_EmailTrab     IN CHAR,
                                     v_EmailCopia    IN CHAR
                                     ) AS

    mesg_body      VARCHAR2(32767);
    v_vDescLocal   VARCHAR2(120);
    v_Destinatario VARCHAR2(120);
  BEGIN
    mesg_body      := '<L>' || vMensaje_in || '</L>';
    v_Destinatario := v_EmailTrab;
    dbms_output.put_line('envio correo');
    FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             v_Destinatario,
                             vAsunto_in,
                             vTitulo_in,
                             mesg_body,
                             'joliva' || CASE WHEN v_EmailCopia IS NOT NULL THEN ',' || v_EmailCopia END,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             TRUE);

  END;

  /* ************************************************************************ */

  PROCEDURE ENVIA_EMAIL_REGISTRO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR) IS
    mesg_body       VARCHAR2(20000);
    v_NombreLocal   PBL_LOCAL.DESC_CORTA_LOCAL%TYPE;
    v_EmailJefeZona VTA_ZONA_VTA.EMAIL_JEFE_ZONA%TYPE;
    v_EmailSubGerente VTA_ZONA_VTA.NOM_SUBGERENTE%TYPE;

    CURSOR curRegistro IS
      SELECT Z.COD_ZONA_VTA || '-' || Z.DESC_CORTA_ZONA_VTA ZONA,
             UPPER(Z.EMAIL_JEFE_ZONA) JEFE,
             R.FEC_DIA_REGISTRO FECHA,
             (L.COD_LOCAL || '-' || L.DESC_CORTA_LOCAL) LOCAL,
             (U.NOM_USU || ' ' || U.APE_PAT || ' ' || U.APE_MAT) NOMBRE,
             TO_CHAR(R.FEC_CREA, 'HH24:MI:SS') HORA,
             Z.NOM_SUBGERENTE
        FROM PBL_REG_INGRESO_LOCAL R,
             PBL_USU_LOCAL         U,
             PBL_LOCAL             L,
             VTA_LOCAL_X_ZONA      LZ,
             VTA_ZONA_VTA          Z
       WHERE U.COD_GRUPO_CIA = cCodGrupoCia_in
         AND U.COD_LOCAL = cCodLocal_in
         AND R.FEC_DIA_REGISTRO = TRUNC(SYSDATE)
         AND R.COD_GRUPO_CIA = U.COD_GRUPO_CIA
         AND R.COD_LOCAL = U.COD_LOCAL
         AND R.SEC_USU_LOCAL = U.SEC_USU_LOCAL
         AND L.COD_GRUPO_CIA = U.COD_GRUPO_CIA
         AND L.COD_LOCAL = U.COD_LOCAL
         AND LZ.COD_GRUPO_CIA = L.COD_GRUPO_CIA
         AND LZ.COD_LOCAL = L.COD_LOCAL
         AND Z.COD_GRUPO_CIA = LZ.COD_GRUPO_CIA
         AND Z.COD_ZONA_VTA = LZ.COD_ZONA_VTA
       ORDER BY R.FEC_CREA DESC;

  BEGIN

    mesg_body := '';
    mesg_body := mesg_body ||
                 '<table style="text-align: left; width: 95%;" border="1"';
    mesg_body := mesg_body || ' cellpadding="2" cellspacing="1">';
    mesg_body := mesg_body || '  <tbody>';

    mesg_body := mesg_body || '    <tr>';
    mesg_body := mesg_body || '      <th><small>ZONA</small></th>';
    mesg_body := mesg_body || '      <th><small>JEFE ZONA</small></th>';
    mesg_body := mesg_body || '      <th><small>FECHA</small></th>';
    mesg_body := mesg_body || '      <th><small>LOCAL</small></th>';
    mesg_body := mesg_body || '      <th><small>USUARIO</small></th>';
    mesg_body := mesg_body || '      <th><small>HORA</small></th>';
    mesg_body := mesg_body || '    </tr>';

    FOR v_Reg IN curRegistro LOOP
      v_NombreLocal   := v_Reg.Local;
      v_EmailJefeZona := v_Reg.Jefe;
      v_EmailSubGerente := v_Reg.NOM_SUBGERENTE;

      mesg_body := mesg_body || '   <tr>' || '      <td><small>' ||
                   v_Reg.Zona || '</small></td>' || '      <td><small>' ||
                   v_Reg.Jefe || '</small></td>' || '      <td><small>' ||
                   v_Reg.Fecha || '</small></td>' || '      <td><small>' ||
                   v_Reg.Local || '</small></td>' || '      <td><small>' ||
                   v_Reg.Nombre || '</small></td>' || '      <td><small>' ||
                   v_Reg.Hora || '</small></td>' || '   </tr>';
    END LOOP;

    mesg_body := mesg_body || '  </tbody>';
    mesg_body := mesg_body || '</table>';
    mesg_body := mesg_body || '<br>';

    IF v_NombreLocal IS NOT NULL THEN
      ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,
                               'INGRESO LOCAL: ' || v_NombreLocal,
                               '',
                               '<BR>REPORTE DE INGRESO EN LOCAL: ' ||
                               TRUNC(SYSDATE) || '</BR>' || '<BR>' ||
                               mesg_body || '</BR>',
                               --                                          'joliva');
                               v_EmailJefeZona,
                               v_EmailSubGerente
                               );
    END IF;

    mesg_body := '';

  END;


    /** *********************************************************************** */
  PROCEDURE CAMBIO_CLAVE(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in    IN CHAR,
                        cIdUsu_in       IN CHAR,
                        cSecUsu_in      IN CHAR,
                        cNewClave       IN CHAR)
 IS

 CLAVE PBL_USU_LOCAL.CLAVE_USU%TYPE;
 v_igualClave EXCEPTION;
  BEGIN

    SELECT TRIM(A.CLAVE_USU) INTO CLAVE
    FROM PBL_USU_LOCAL A
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.SEC_USU_LOCAL=cSecUsu_in;

    IF(TRIM(CLAVE)=TRIM(cNewClave))THEN
     RAISE v_igualClave;
    ELSE

     UPDATE PBL_USU_LOCAL X
     SET X.CLAVE_USU=cNewClave,
         X.FEC_ULT_CAMB_CLAVE=SYSDATE,
         X.USU_MOD_USU_LOCAL=cIdUsu_in,
         X.FEC_MOD_USU_LOCAL=SYSDATE
     WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
     AND X.COD_LOCAL=cCodLocal_in
     AND X.SEC_USU_LOCAL=cSecUsu_in;

       COMMIT;
    END IF;

  EXCEPTION
  WHEN v_igualClave THEN
      RAISE_APPLICATION_ERROR(-20010,'DEBE INGRESAR UNA CLAVE DISTINTA A LA ACTUAL');
  WHEN OTHERS THEN
      NULL;
  END;


    /* ************************************************************************ */
  FUNCTION VALIDA_CAMBIO_CLAVE(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecUsuLoc_in   IN CHAR)
  RETURN CHAR IS
    v_vClaveUsu  PBL_USU_LOCAL.CLAVE_USU%TYPE;
    v_cEstUsu    PBL_USU_LOCAL.EST_USU%TYPE;
    v_cResultado CHAR(2);
    nDiasCamb    NUMBER;
    CANT         NUMBER;
  BEGIN

  SELECT TO_NUMBER(TRIM(A.LLAVE_TAB_GRAL)) INTO  nDiasCamb
  FROM PBL_TAB_GRAL A
  WHERE A.ID_TAB_GRAL=292;

    IF(nDiasCamb>0) THEN

      SELECT TRUNC(SYSDATE)-TRUNC(NVL(X.FEC_ULT_CAMB_CLAVE,SYSDATE)) INTO CANT
      FROM PBL_USU_LOCAL X
      WHERE X.EST_USU='A'
      AND X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.SEC_USU_LOCAL=cSecUsuLoc_in;

        IF(CANT >=nDiasCamb) THEN
        v_cResultado:='S';
        ELSE
        v_cResultado:='N';
        END IF;

    ELSE
      v_cResultado:='N';
    END IF;

    RETURN v_cResultado;

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      v_cResultado := 'N';
      RETURN v_cResultado;

  END;
    /* ************************************************************************ */
	PROCEDURE SET_MODULO_VERSION(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR,
								vModulo_in IN VARCHAR, vVersion_in IN VARCHAR, vCompilacion_in IN CHAR)
	IS
	BEGIN
		dbms_application_info.set_module(vModulo_in, vVersion_in||' '||vCompilacion_in);
		dbms_application_info.set_client_info(cCodCia_in||' '||cCodLocal_in);
	end;
    /* ************************************************************************ */

END;
/

