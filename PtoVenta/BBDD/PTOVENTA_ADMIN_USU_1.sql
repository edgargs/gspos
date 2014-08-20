--------------------------------------------------------
--  DDL for Package Body PTOVENTA_ADMIN_USU
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_ADMIN_USU" AS
  /* ******************************************************************************************************* */
  FUNCTION USU_LISTA_USUARIOS_LOCAL(cCodGrupoCia_in    IN CHAR,
                                  cCodLocal_in       IN CHAR,
                                  cEstadoActivo_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curLab FarmaCursor;
  BEGIN
    OPEN curLab FOR
      SELECT SEC_USU_LOCAL || 'Ã' ||
             APE_PAT || 'Ã' ||
             NVL(APE_MAT,' ') || 'Ã' ||
             NOM_USU || 'Ã' ||
             LOGIN_USU || 'Ã' ||
             CASE  WHEN EST_USU = ESTADO_ACTIVO
              THEN 'Activo'
             WHEN EST_USU = ESTADO_INACTIVO
             THEN 'Inactivo'
             ELSE EST_USU
             END  || 'Ã' ||
             --CLAVE_USU || 'Ã' ||--modificado dveliz 02.09.08
             NVL(DIRECC_USU,' ') || 'Ã' ||
             TELEF_USU || 'Ã' ||
             NVL(TO_CHAR(FEC_NAC,'dd/MM/yyyy'),' ')  || 'Ã' ||
             COD_GRUPO_CIA || 'Ã' ||
             COD_LOCAL     || 'Ã' ||
             NVL(COD_TRAB,' ') || 'Ã' ||
             nvl(DNI_USU,' ')  || 'Ã' ||
             --SE MODIFICO
             NVL(COD_TRAB_RRHH,' ')
      FROM   PBL_USU_LOCAL
      WHERE  COD_LOCAL     = cCodLocal_in
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    EST_USU LIKE '%'||cEstadoActivo_in||'%'
      AND    SEC_USU_LOCAL BETWEEN '001' AND '899'--ERIOS 25/10/20006: RANGO VALIDO
      ;
    RETURN curLab;
  END;
  /* ******************************************************************************************************* */

FUNCTION USU_NUEVO_SECUENCIA_USU(cCodLocal_in    IN CHAR,
                                 cCodGrupoCia_in IN CHAR)
RETURN NUMBER
IS
 v_secGenerado NUMBER;
BEGIN
   SELECT NVL(TO_NUMBER(MAX(SEC_USU_LOCAL))+1,1)
   INTO   v_secGenerado
   FROM   PBL_USU_LOCAL
   WHERE  COD_LOCAL     = cCodLocal_in
   AND    COD_GRUPO_CIA = cCodGrupoCia_in;
RETURN v_secGenerado;
END;

 /* ******************************************************************************************************* */

PROCEDURE USU_INGRESA_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in      IN CHAR,
                              cCodLocal_in     IN CHAR,
                              cCodTrab_in     IN CHAR,
                              cNomUsu_in       IN CHAR,
                              cApePat_in       IN CHAR,
                              cApeMat_in       IN CHAR,
                              cLoginUsu_in     IN CHAR,
                              cClaveUsu_in     IN CHAR,
                              cTelefUsu_in     IN CHAR,
                              cDireccUsu_in   IN CHAR,
                              cFecNac_in       IN CHAR,
                              cCodUsu_in       IN CHAR,
                              cDni_in         IN CHAR,
                              cCodTrabRRHH    IN CHAR
                              )
IS
  v_cNeoCod  CHAR(3);
  v_nNeoCod  NUMBER;
  vCantUsersLogin NUMBER;
BEGIN
   SELECT TO_NUMBER(COUNT(*)) INTO vCantUsersLogin
   FROM PBL_USU_LOCAL
   WHERE LOGIN_USU     = cLoginUsu_in    AND
        COD_LOCAL     = cCodLocal_in    AND
       COD_GRUPO_CIA = cCodGrupoCia_in ;

   IF vCantUsersLogin>0 THEN
     RAISE_APPLICATION_ERROR(-20014,'El Login especificado ya existe');
   END IF;

    v_nNeoCod:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_USU);
  v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 3, '0', POS_INICIO);

                INSERT INTO PBL_USU_LOCAL (COD_GRUPO_CIA,
                                           COD_LOCAL,
                               SEC_USU_LOCAL,
                               COD_TRAB,
                               COD_CIA,
                               NOM_USU,
                               APE_PAT,
                               APE_MAT,
                               LOGIN_USU,
                               CLAVE_USU,
                               TELEF_USU,
                               DIRECC_USU,
                               FEC_NAC,
                               EST_USU,
                               FEC_CREA_USU_LOCAL,
                               USU_CREA_USU_LOCAL,
                               FEC_MOD_USU_LOCAL,
                               USU_MOD_USU_LOCAL,
                               DNI_USU,                               --AÑADIDO
                               COD_TRAB_RRHH)
                VALUES(cCodGrupoCia_in,
                       cCodLocal_in,
                     v_cNeoCod ,
                     cCodTrab_in,
                     cCodCia_in,
                     cNomUsu_in,
                     cApePat_in,
                     cApeMat_in,
                     cLoginUsu_in,
                     cClaveUsu_in,
                     cTelefUsu_in,
                     cDireccUsu_in,
                     TO_DATE(cFecNac_in,'dd/MM/yyyy'),
                     ESTADO_ACTIVO,
                      SYSDATE,
                     cCodUsu_in,
                     NULL,
                     NULL,
                     CDNI_IN,
                     cCodTrabRRHH);

END;

 /* ******************************************************************************************************* */

PROCEDURE USU_MODIFICA_USUARIO(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                                cSecUsuLocal_in IN CHAR,
                               cCodTrab_in     IN CHAR,
                                cNomUsu_in      IN CHAR,
                                cApePat_in      IN CHAR,
                                cApeMat_in      IN CHAR,
                                cLoginUsu_in    IN CHAR,
                                cClaveUsu_in    IN CHAR,
                                cTelefUsu_in    IN CHAR,
                                cDireccUsu_in   IN CHAR,
                                cFecNac_in      IN CHAR,
                                cCodUsu_in      IN CHAR,
                               cDni_in         IN CHAR)
IS
BEGIN

               UPDATE PBL_USU_LOCAL SET FEC_MOD_USU_LOCAL = SYSDATE, USU_MOD_USU_LOCAL = cCodUsu_in,
                      COD_TRAB =  cCodTrab_in,
                      NOM_USU    = cNomUsu_in,
                      APE_PAT    = cApePat_in,
                      APE_MAT    = cApeMat_in,
                      LOGIN_USU  = cLoginUsu_in,
                      CLAVE_USU  = cClaveUsu_in,
                      TELEF_USU  = cTelefUsu_in,
                      DIRECC_USU = cDireccUsu_in,
                      FEC_NAC    = TO_DATE(cFecNac_in,'dd/MM/yyyy'),
                      DNI_USU    = CDNI_IN
               WHERE
                     COD_GRUPO_CIA = cCodGrupoCia_in AND
                    COD_LOCAL     = cCodLocal_in    AND
                    SEC_USU_LOCAL = cSecUsuLocal_in;
              END;


  /* ******************************************************************************************************* */

/*PROCEDURE USU_CAMBIA_ESTADO_USU(cCodGrupoCia_in IN CHAR,
                    cCodLocal_in    IN CHAR,
                  cSecUsuLocal_in IN CHAR,
                cCodUsu_in      IN CHAR)
IS
v_est CHAR(1);
vCantCajasAsig NUMBER;
BEGIN
      SELECT EST_USU
    INTO   v_est
    FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
             COD_LOCAL     = cCodLocal_in    AND
             SEC_USU_LOCAL = cSecUsuLocal_in;

       IF   v_est = ESTADO_ACTIVO THEN
            v_est:= ESTADO_INACTIVO;
       ELSE v_est:= ESTADO_ACTIVO;
       END IF;

     IF v_est=ESTADO_INACTIVO THEN
      BEGIN
      SELECT TO_NUMBER(COUNT(*)) INTO vCantCajasAsig
      FROM VTA_CAJA_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
                COD_LOCAL     = cCodLocal_in    AND
        SEC_USU_LOCAL = cSecUsuLocal_in;

        IF vCantCajasAsig>0 THEN
           RAISE_APPLICATION_ERROR(-20015,'No se puede inactivar a un usuario que este asignado a una caja.');
        END IF;
    END;
     END IF;

         UPDATE PBL_USU_LOCAL SET    FEC_MOD_USU_LOCAL = SYSDATE,USU_MOD_USU_LOCAL = cCodUsu_in,
         EST_USU = v_est
         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                COD_LOCAL     = cCodLocal_in    AND
                SEC_USU_LOCAL = cSecUsuLocal_in;
END;*/

PROCEDURE USU_CAMBIA_ESTADO_USU(cCodGrupoCia_in IN CHAR,
                    cCodLocal_in    IN CHAR,
                    cSecUsuLocal_in IN CHAR,
                    cCodUsu_in      IN CHAR)
IS
v_est CHAR(1);
vCantCajasAsig NUMBER;
BEGIN
      SELECT EST_USU
    INTO   v_est
    FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
             COD_LOCAL     = cCodLocal_in    AND
             SEC_USU_LOCAL = cSecUsuLocal_in;

       IF   v_est = ESTADO_ACTIVO THEN
            v_est:= ESTADO_INACTIVO;
       ELSE v_est:= ESTADO_ACTIVO;
       END IF;

     IF v_est=ESTADO_INACTIVO THEN
      BEGIN
      SELECT TO_NUMBER(COUNT(*)) INTO vCantCajasAsig
      FROM VTA_CAJA_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
            COD_LOCAL     = cCodLocal_in    AND
        SEC_USU_LOCAL = cSecUsuLocal_in     AND
        Est_Caja_Pago =ESTADO_ACTIVO ;  --Añadido  --dubilluz 12.07.2007


        IF vCantCajasAsig>0 THEN
           RAISE_APPLICATION_ERROR(-20015,'No se puede inactivar a un usuario que este asignado a una caja.');
        END IF;
    END;
     END IF;

         UPDATE PBL_USU_LOCAL SET    FEC_MOD_USU_LOCAL = SYSDATE,USU_MOD_USU_LOCAL = cCodUsu_in,
         EST_USU = v_est
         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                COD_LOCAL     = cCodLocal_in    AND
                SEC_USU_LOCAL = cSecUsuLocal_in;
END;

   /* ******************************************************************************************************* */

  FUNCTION USU_LISTA_ROLES_USUARIO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                   cSecUsuLocal_in IN CHAR)
RETURN FarmaCursor
IS
    mifcur FarmaCursor;
  BEGIN
    OPEN mifcur FOR
  SELECT PBL_ROL.COD_ROL  || 'Ã' ||
         PBL_ROL.DESC_ROL
    FROM   PBL_ROL,
       PBL_ROL_USU
    WHERE
       PBL_ROL_USU.COD_GRUPO_CIA = cCodGrupoCia_in     AND
         PBL_ROL_USU.COD_LOCAL     = cCodLocal_in        AND
         PBL_ROL_USU.SEC_USU_LOCAL = cSecUsuLocal_in      AND
       PBL_ROL.COD_ROL           = PBL_ROL_USU.COD_ROL ;
    RETURN mifcur;
  END;

   /* ******************************************************************************************************* */

  FUNCTION USU_LISTA_ROLES
  RETURN FarmaCursor
  IS
    curLab FarmaCursor;
  BEGIN
    OPEN curLab FOR
      SELECT COD_ROL   || 'Ã' ||
         DESC_ROL
      FROM   PBL_ROL
      WHERE  EST_ROL = ESTADO_ACTIVO   AND
         TIP_ROL = ROLES_PTOVENTA;
    RETURN curLab;
  END;

  /* ******************************************************************************************************* */

  PROCEDURE USU_LIMPIA_ROLES_USUARIO(cCodGrupoCia_in IN CHAR,
                       cCodLocal_in    IN CHAR,
                     cSecUsuLocal_in IN CHAR)
  IS
     BEGIN
      DELETE FROM PBL_ROL_USU
           WHERE  COD_GRUPO_CIA = cCodGrupoCia_in   AND
                  COD_LOCAL     = cCodLocal_in      AND
                   SEC_USU_LOCAL = cSecUsuLocal_in   AND
                  COD_ROL  <> TIPO_ROL_AUDITOR;

END;

 /* ******************************************************************************************************* */

PROCEDURE USU_AGREGA_ROL_USUARIO(cCodGrupoCia_in IN CHAR,
                       cCodLocal_in    IN CHAR,
                  cSecUsuLocal_in IN CHAR,
                  cCodRol_in      IN CHAR,
                  cUsuCreaRol_in  IN CHAR)
IS
 BEGIN
    INSERT INTO PBL_ROL_USU (COD_ROL,
                  COD_GRUPO_CIA,
               COD_LOCAL,
               SEC_USU_LOCAL,
               EST_ROL_USU,
               FEC_CREA_ROL_USU,
               USU_CREA_ROL_USU,
               FEC_MOD_ROL_USU,
               USU_MOD_ROL_USU)
       VALUES(       cCodRol_in,
                   cCodGrupoCia_in,
                 cCodLocal_in,
                  cSecUsuLocal_in,
                  ESTADO_ACTIVO,
                  SYSDATE,
                  cUsuCreaRol_in,
                  NULL,
                  NULL);
 END;

  /* ******************************************************************************************************* */
  FUNCTION USU_OBTIENE_DATA_TRABAJADOR(cCodCia_in  IN CHAR,
                          cCodTrab_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLab     FarmaCursor;
    v_cNeoCod  CHAR(6);
    v_nNeoCod  NUMBER;
  BEGIN
    v_nNeoCod:= TO_NUMBER(cCodTrab_in);
    v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 6, '0', POS_INICIO);
    --29/11/2007  DUBILLUZ SE AGREGO EL DNI
    OPEN curLab FOR
    SELECT COD_TRAB          || 'Ã' ||
           NVL(APE_PAT_TRAB,' ') || 'Ã' ||
           NVL(APE_MAT_TRAB,' ') || 'Ã' ||
           NVL(NOM_TRAB,' ')     || 'Ã' ||
           NVL(TELEF_TRAB,' ')   || 'Ã' ||
           NVL(DIRECC_TRAB,' ')  || 'Ã' ||
           NVL(TO_CHAR(FEC_NAC_TRAB,'dd/MM/yyyy'),' ')|| 'Ã' ||
           NVL(C.NUM_DOC_IDEN,' ')
    FROM CE_MAE_TRAB C
    WHERE  /*COD_CIA  = cCodCia_in
           --AND COD_TRAB = v_cNeoCod
           AND*/ C.COD_TRAB_RRHH = v_cNeoCod
    ;

    RETURN curLab;
  END;
  /* ******************************************************************************************************* */

    FUNCTION USU_EXISTE_DUPLICADO(cCodGrupoCia_in  IN CHAR,
                   cCodLocal_in     IN CHAR,
                   cCodTrab_in      IN CHAR)
    RETURN VARCHAR2
    IS
        v_cNeoCod  CHAR(6);
        v_nNeoCod  NUMBER;
        v_rpta     VARCHAR(1);
        v_cant     NUMBER;
    BEGIN
      v_nNeoCod:= TO_NUMBER(cCodTrab_in);
      v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 6, '0', POS_INICIO);
      v_rpta   := '0';

      SELECT COUNT(*)
       INTO   v_cant
      FROM   PBL_USU_LOCAL C
      WHERE  --COD_TRAB       = v_cNeoCod
             COD_TRAB_RRHH = v_cNeoCod
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in;

          IF v_cant = 0 THEN
             v_rpta :='0';
          ELSE
             v_rpta :='1';
          END IF;
       RETURN v_rpta;
    END;

    /* ******************************************************************************************************* */
    FUNCTION USU_EXISTE_LOGIN_DUPLICADO(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in    IN CHAR,
                           cLogin_in      IN CHAR)
RETURN VARCHAR2
IS
    v_rpta     VARCHAR(1);
    v_cant     NUMBER;
  BEGIN

    v_rpta   := '0';

    SELECT COUNT(*)
  INTO   v_cant
  FROM   PBL_USU_LOCAL
  WHERE  LOGIN_USU    = cLogin_in       AND
       COD_GRUPO_CIA = cCodGrupoCia_in AND
       COD_LOCAL   = cCodLocal_in;

        IF v_cant = 0 THEN
           v_rpta :='0';
        ELSE
           v_rpta :='1';
        END IF;
     RETURN v_rpta;
  END;

 /* ******************************************************************************************************* */


  --SE AGREGO LA COLUMNA DE CODIGO DE RECURSOS HUMANOS
  --27/11/2007 DUBILLUZ MODIFICACION
  FUNCTION USU_LISTA_TRABAJADORES(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTrab FarmaCursor;
  BEGIN

    OPEN curTrab FOR
         SELECT COD_TRAB             || 'Ã' ||
                nvl(Cod_Trab_Rrhh,' ') || 'Ã' ||
                APE_PAT_TRAB         || 'Ã' ||
                NVL(APE_MAT_TRAB,' ')|| 'Ã' ||
                NOM_TRAB             || 'Ã' ||
                NVL(NUM_DOC_IDEN,' ')|| 'Ã' ||
                EST_TRAB             || 'Ã' ||
                DIRECC_TRAB          || 'Ã' ||
                TELEF_TRAB           || 'Ã' ||
                NVL(TO_CHAR(FEC_NAC_TRAB,'dd/mm/YYYY'),' ')         || 'Ã' ||
                COD_CIA
          FROM  CE_MAE_TRAB m
          --WHERE COD_CIA=cCodGrupoCia_in
		  ;
    RETURN curTrab;
  END;

  /********************************************************************************/
  -- Busca si el usuario tiene aluna caja asignada
  --Fecha       Usuario    Comentario
  --11/07/2007  DUBILLUZ    Creación
  FUNCTION USU_EXISTE_CAJA(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR,
                           cSec_usu_local_in IN VARCHAR2)
  RETURN VARCHAR2
  IS
  cCant NUMBER;
  vNumCaja VARCHAR2(5);
  BEGIN
   SELECT count(*) INTO cCant
   FROM   VTA_CAJA_PAGO
   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in            AND
          COD_LOCAL     = cCodLocal_in               AND
          SEC_USU_LOCAL = NVL(cSec_usu_local_in,'_');

  if cCant > 0 then
   SELECT NUM_CAJA_PAGO INTO vNumCaja
   FROM   VTA_CAJA_PAGO
   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in            AND
          COD_LOCAL     = cCodLocal_in               AND
          SEC_USU_LOCAL = NVL(cSec_usu_local_in,'_');
   return vNumCaja ;
   else
         return 'N';
   end if;

  END USU_EXISTE_CAJA;

  /********************************************************************************/
  -- Busca si existe usuarios sin asignar caja
  --Fecha       Usuario    Comentario
  --12/07/2007  DUBILLUZ    Creación
  FUNCTION USU_EXISTE_USU_SIN_CAJA(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in  IN CHAR)
  RETURN VARCHAR2
  IS
  cCant NUMBER;
  vRetorno VARCHAR2(5);

  BEGIN
    SELECT COUNT(*)
    INTO   cCant
    FROM   PBL_USU_LOCAL
    WHERE
        COD_GRUPO_CIA = cCodGrupoCia_in  AND
        COD_LOCAL     = cCodLocal_in     AND
        EST_USU       = ESTADO_ACTIVO    AND
        COD_GRUPO_CIA || COD_LOCAL ||
        SEC_USU_LOCAL IN (
                          SELECT COD_GRUPO_CIA||COD_LOCAL||SEC_USU_LOCAL
                           FROM   PBL_ROL_USU
                          WHERE  COD_ROL = TIPO_ROL_CAJERO ) AND
        COD_GRUPO_CIA || COD_LOCAL ||
        SEC_USU_LOCAL NOT IN (
                              SELECT COD_GRUPO_CIA || COD_LOCAL ||  SEC_USU_LOCAL
                              FROM   VTA_CAJA_PAGO );

    if (cCant > 0) then
      vRetorno:='TRUE';
    else
      vRetorno:='FALSE';
    end if;

   return vRetorno;

  END USU_EXISTE_USU_SIN_CAJA;

--Valida Rango de dias al modificar el codigo de trabajador para el usuario
  --Fecha       Usuario    Comentario
  --06/07/2007  JCORTEZ    Creación
  FUNCTION USU_VALIDA_ACTULIZACION(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    vLogin IN VARCHAR2)

  RETURN VARCHAR2
  IS
  cCantDias NUMBER;
  cMaxDias NUMBER;
  vRetorno VARCHAR2(5);

  BEGIN
    SELECT TO_DATE(SYSDATE,'dd/MM/YYYY')-TO_DATE(A.FEC_CREA_USU_LOCAL,'dd/MM/YYYY') INTO cCantDias
    FROM   PBL_USU_LOCAL A
    WHERE a.login_usu=vLogin
    AND   A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   A.COD_LOCAL     = cCodLocal_in;

    SELECT TO_NUMBER(B.LLAVE_TAB_GRAL) INTO cMaxDias
    FROM PBL_TAB_GRAL B WHERE B.ID_TAB_GRAL='75';

    if(cCantDias>cMaxDias) then
    vRetorno:='FALSE';
    else
    vRetorno:='TRUE';
    end if;
    return vRetorno;

  END USU_VALIDA_ACTULIZACION;

  /********************************************************************************/
  FUNCTION USU_GET_IND_VALIDA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR )
  RETURN CHAR
  IS
    v_indicador CHAR(1);
  BEGIN

    SELECT IND_VALIDA_MANT_USU
      INTO   v_indicador
    FROM PBL_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in;

    RETURN v_indicador;
  END;
  /********************************************************************************/
  FUNCTION USU_EXISTE_USUARIO(cCodGrupoCia_in   IN CHAR,
                              cCodTrabRR_HH_in IN VARCHAR2)
  RETURN CHAR
  IS
  v_valor char(1):= 'N';
  nCant   number;
  BEGIN

  SELECT COUNT(*)
  INTO   nCant
  FROM   CE_MAE_TRAB L
  WHERE  /*L.COD_CIA       = cCodGrupoCia_in
  AND    */L.COD_TRAB_RRHH = cCodTrabRR_HH_in;

  IF nCant > 0 THEN
     v_valor := 'S';
  END IF;

  return v_valor;

  END;
  /***************************************************************************/
  FUNCTION GET_MENSAJE_USU(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cSecUsu_in IN CHAR)
  RETURN FarmaCursor
  IS
    curMensaje FarmaCursor;
  BEGIN
    OPEN curMensaje FOR
    SELECT M.MENSAJE
    FROM PBL_USU_LOCAL U,
         PBL_USU_MENSAJE M
    WHERE U.COD_GRUPO_CIA = cCodGrupoCia_in
          AND U.COD_LOCAL = cCodLocal_in
          AND U.SEC_USU_LOCAL = cSecUsu_in
          AND U.COD_TRAB_RRHH = M.COD_TRAB_RRHH
          AND TRUNC(SYSDATE) BETWEEN M.FEC_INI AND M.FEC_FIN
          AND M.REPITE > M.CANT_VECES;
    RETURN curMensaje;
  END;
  /***************************************************************************/
  PROCEDURE ACT_MENSAJE_USU(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cSecUsu_in IN CHAR,vIdUsu_in IN VARCHAR2)
  IS
    PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
    UPDATE PBL_USU_MENSAJE
    SET CANT_VECES = CANT_VECES+1,
        USU_MOD = vIdUsu_in,
        FEC_MOD = SYSDATE
    WHERE COD_TRAB_RRHH = (SELECT COD_TRAB_RRHH FROM PBL_USU_LOCAL
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL = cCodLocal_in
                                    AND SEC_USU_LOCAL = cSecUsu_in);
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
  END;
  /***************************************************************************/

  FUNCTION OBTENER_CLAVE(cCodGrupoCia_in  IN CHAR,
                          cCodLocal_in      IN CHAR,
                          cSecUsu_in        IN CHAR)
    RETURN VARCHAR2
    IS
    vClave   VARCHAR2(20);
    BEGIN
    SELECT   CLAVE_USU INTO vclave
      FROM   PBL_USU_LOCAL
      WHERE  COD_LOCAL     = cCodLocal_in
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    SEC_USU_LOCAL = cSecUsu_in;

    RETURN vclave;
    END;

  /************************asolis*****************************************************/
  FUNCTION USU_LISTA_TRABAJADORES_LOCAL(cCodGrupoCia_in    IN CHAR,
                                        cCodLocal_in       IN CHAR)

   RETURN FarmaCursor
  IS
    curLab FarmaCursor;
  BEGIN
    OPEN curLab FOR
    SELECT  X.SEC_USU_LOCAL || 'Ã' ||
            nvl(X.Cod_Trab_Rrhh,' ') || 'Ã' ||
            X.APE_PAT || 'Ã' ||
            NVL(X.APE_MAT,' ') || 'Ã' ||
            X.NOM_USU || 'Ã' ||
            nvl(DNI_USU,' ')  || 'Ã' ||
            X.EST_USU  || 'Ã' ||
            NVL(X.CARNE_SANIDAD,' ') || 'Ã' ||
            NVL(TO_CHAR(X.FEC_VENCIMIENTO,'dd/mm/YYYY'),' ') || 'Ã' ||
            TELEF_USU || 'Ã' ||
            COD_GRUPO_CIA || 'Ã' ||
            COD_LOCAL
    FROM   PBL_USU_LOCAL X
    WHERE  X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND    X.COD_LOCAL=cCodLocal_in
    AND    X.EST_USU ='A'
    AND    X.SEC_USU_LOCAL BETWEEN '001' AND '899';
    RETURN curLab;

  END;
  /****************************asolis************************************************/

FUNCTION USU_OBTIENE_DATA_TRAB_LOCAL(cCodCia_in  IN CHAR,
                                    cCodGrupoCia_in    IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                      cCodTrab_in IN CHAR)
    RETURN FarmaCursor
  IS
    curLab     FarmaCursor;
    v_cNeoCod  CHAR(6);
    v_nNeoCod  NUMBER;
  BEGIN
    v_nNeoCod:= TO_NUMBER(cCodTrab_in);
    v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 6, '0', POS_INICIO);

    OPEN curLab FOR

      SELECT nvl(Cod_Trab_Rrhh,' ') || 'Ã' ||
             NVL(APE_PAT,' ') || 'Ã' ||
             NVL(APE_MAT,' ') || 'Ã' ||
             NVL(NOM_USU,' ') || 'Ã' ||
             NVL(TELEF_USU,' ') || 'Ã' ||
             NVL(DIRECC_USU,' ') || 'Ã' ||
             NVL(TO_CHAR(FEC_NAC,'dd/MM/yyyy'),' ')  || 'Ã' ||
             nvl(DNI_USU,' ')  || 'Ã' ||
             NVL(CARNE_SANIDAD,' ') || 'Ã' ||
             FEC_EXPEDICION  || 'Ã' ||
             FEC_VENCIMIENTO
      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    Cod_Trab_Rrhh = v_cNeoCod;

    RETURN curLab;

  END;

/***************************asolis*******************************************************/

FUNCTION USU_EXISTE_USUARIO_CARNE(cCodGrupoCia_in  IN CHAR,
                   cCodLocal_in     IN CHAR,
                   cCodTrab_in      IN CHAR)
    RETURN VARCHAR2
    IS
        v_cNeoCod  CHAR(6);
        v_nNeoCod  NUMBER;
        v_rpta     VARCHAR(1);
        v_cant     NUMBER;
    BEGIN
      v_nNeoCod:= TO_NUMBER(cCodTrab_in);
      v_cNeoCod:= Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNeoCod , 6, '0', POS_INICIO);
      v_rpta   := '0';

      SELECT COUNT(*)
       INTO   v_cant
      FROM   PBL_USU_LOCAL C
      WHERE  --COD_TRAB       = v_cNeoCod
             COD_TRAB_RRHH = v_cNeoCod
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    CARNE_SANIDAD IS NOT NULL;

          IF v_cant = 0 THEN
             v_rpta :='0';
          ELSE
             v_rpta :='1';
          END IF;
       RETURN v_rpta;
    END;

/******************************asolis********************************************************/


PROCEDURE USU_INGRESA_CARNE_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in         IN CHAR,
                              cCodLocal_in        IN CHAR,
                              cCodTrab_in        IN CHAR,
                              cCodTrabRRHH_in    IN CHAR,
                              cNumCarne_in       IN CHAR,
                              cFechaExpe_in      IN CHAR,
                              cFechaVenc_in      IN CHAR
                               )

IS

 vFechaExp DATE  ;
 vFechaVenc DATE ;
 bad_date EXCEPTION;



 PRAGMA EXCEPTION_INIT (bad_date, -01843);


 BEGIN

   vFechaExp := TO_DATE(cFechaExpe_in,'dd/MM/yyyy');
   vFechaVenc := TO_DATE(cFechaVenc_in,'dd/MM/yyyy');

  IF vFechaExp>TRUNC(SYSDATE) THEN
      RAISE bad_date;
   END IF;

  UPDATE  PBL_USU_LOCAL
  SET CARNE_SANIDAD = cNumCarne_in,
      FEC_EXPEDICION =   TO_DATE(cFechaExpe_in,'dd/MM/yyyy'),
      FEC_VENCIMIENTO =  TO_DATE(cFechaVenc_in,'dd/MM/yyyy')
  WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND sec_usu_local  = cCodTrab_in
    AND COD_TRAB_RRHH = cCodTrabRRHH_in ;

     /* commit;*/

 EXCEPTION
   WHEN bad_date THEN
  RAISE_APPLICATION_ERROR(-20001,'FECHA EXPEDICION INVALIDA');
 /*  rollback;*/


 END;

 /***************************asolis***********************************************************/


PROCEDURE USU_ALERTA_INSR_CARNE_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCodCia_in         IN CHAR,
                              cCodLocal_in        IN CHAR,
                              cCodTrab_in        IN CHAR,
                              cCodTrabRRHH_in    IN CHAR,
                              cNumCarne_in       IN CHAR,
                              cFechaExpe_in      IN CHAR,
                              cFechaVenc_in      IN CHAR

                              )

IS


  vNOM_USU VARCHAR2(50);
  vDNI_USU  CHAR(8);





  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (2000);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  cFechaMensaje  varchar (150);
   v_vDescLocal varchar (150);

BEGIN

    ---OBTENER LOS DATOS ACTUALIZADOS DEL TRABAJADOR

  SELECT  NOM_USU || ' ' || APE_PAT  || ' ' ||  APE_MAT , DNI_USU
          INTO  vNOM_USU , vDNI_USU

      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA =  cCodGrupoCia_in
      AND    COD_LOCAL  = cCodLocal_in
      AND    COD_TRAB_RRHH = cCodTrabRRHH_in

      AND    EST_USU ='A'
      AND    CARNE_SANIDAD = cNumCarne_in
      AND    SEC_USU_LOCAL  = cCodTrab_in ;

      ---envia correo alerta cuando ingresa el nro carné sanidad del trabajador.

            --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;


             vMensajeInfoUsuario:= '<TR><TD align="center">' || cCodTrabRRHH_in
                           || '</TD>' ||'<TD align="center">' || vNOM_USU || '</TD>'
                           || '<TD align="center">' ||  vDNI_USU  || '</TD>'
                           || '<TD align="center">' ||  cNumCarne_in  || '</TD>'
                           || '<TD align="center">' ||  cFechaExpe_in  || '</TD>'
                           || '<TD align="center">' ||  cFechaVenc_in  || '</TD></TR>';

             vMensajeFila :=   vMensajeInfoUsuario;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' ||
                                              '<TR><TD align="center">' || 'Cod Trab Rrhh' || '</TD>'||
                                              '<TD align="center">'||' Nombres' || '</TD>' ||
                                              '<TD align="center">' ||'Dni' || '</TD>' ||
                                              '<TD align="center">' ||'Nro. Carné Sanidad' || '</TD>'||
                                              '<TD align="center">' ||'Fecha Expedición' || '</TD>'||
                                              '<TD align="center">' ||'Fecha Vencimiento' || '</TD></TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');

                    ENVIA_CORREO_INF_MG(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA REGISTRO DE CARNÉ SANIDAD DEL TRABAJADOR:',
                                                'ALERTA',
                                                ' SE REGISTRÓ EL CARNÉ SANIDAD  EN LOCAL:'|| '   '|| v_vDescLocal || '  ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR>DATOS DEL TRABAJADOR<BR>' ||
                                                '</B>'||
                                                '<BR> <I>VERIFIQUE:</I> <BR>'|| vMensajeFinal || '<B>');


END;




/********************************asolis********************************************************/


  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL)
  AS


   --Corregido por Arturo Escate 01/08/2012
   --ReceiverAddress      VARCHAR2(30):='';
   ReceiverAddress        pbl_tab_gral.llave_tab_gral%type;

   --CCReceiverAddress VARCHAR2(120);
   CCReceiverAddress      pbl_local.mail_local%type;


    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

     select  llave_tab_gral
     into    ReceiverAddress
     from  pbl_tab_gral
     where id_tab_gral=265;

----------------
   select mail_local
   into   CCReceiverAddress
   from   pbl_local
   where  cod_grupo_cia = cCodGrupoCia_in
   and    cod_local = cCodLocal_in;
-----------------------------------------------------
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             NVL(cCopiaCorreo,CCReceiverAddress),
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

/************************************asolis***********************************************/



  PROCEDURE ENVIA_CORREO_INF_MAIL_LOCAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL)
  AS

   --Corregido por Arturo Escate 01/08/212
   --ReceiverAddress VARCHAR2(30):='';
   ReceiverAddress     pbl_local.mail_local%type;
   CCReceiverAddress   VARCHAR2(120);

    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

-----BORRAR CUANDO SE USE EL JOB------
 /*    select  llave_tab_gral
     into   ReceiverAddress
     from  pbl_tab_gral
     where id_tab_gral=253;--asolis
*/
----------------------------------------

   select mail_local
   into   ReceiverAddress
   from   pbl_local
   where  cod_grupo_cia = cCodGrupoCia_in
   and    cod_local = cCodLocal_in;
----------------------------------------------------------------
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             NVL(cCopiaCorreo,CCReceiverAddress),
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

/**********************************asolis*************************************************/



  PROCEDURE ENVIA_CORREO_INF_MG(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL)
  AS

   --Cambiado por Arturo Escate 01/08/2012
   --ReceiverAddress VARCHAR2(30);
   ReceiverAddress pbl_tab_gral.llave_tab_gral%type;

   CCReceiverAddress VARCHAR2(120);

    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN


     select  llave_tab_gral
     into   ReceiverAddress
     from  pbl_tab_gral
     where id_tab_gral=265;


    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             NVL(cCopiaCorreo,CCReceiverAddress),
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;
 /***********************************asolis**********************************************/
 --Usado en Job
 PROCEDURE USU_ALERTA_TRAB_SIN_CARNE(cCodCia_in       IN CHAR,
                                    cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR)

  IS

  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (900);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  vResult_EXISTE_USU_SIN_CARNE char(1);
  cFechaMensaje  varchar (150);
   v_vDescLocal varchar (150);

  CURSOR  curValUsu  IS

     SELECT SEC_USU_LOCAL ,
             nvl(Cod_Trab_Rrhh,' ') Cod_Trab_Rrhh,
             APE_PAT ,
             NVL(APE_MAT,' ') APE_MAT,
             NOM_USU ,
             nvl(DNI_USU,' ') DNI_USU ,
             COD_GRUPO_CIA ,
             COD_LOCAL
      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    EST_USU ='A'
      AND    CARNE_SANIDAD IS  NULL
      AND    SEC_USU_LOCAL BETWEEN '001' AND '899'
      order by Cod_Trab_Rrhh;

      v_rcurValUsu curValUsu%ROWTYPE;

      BEGIN

       FOR v_rcurValUsu IN curValUsu
         LOOP
         EXIT WHEN curValUsu%NOTFOUND;
             IF  v_rcurValUsu.Sec_Usu_Local IS NOT NULL THEN
                 DBMS_OUTPUT.PUT_LINE('si hay datos');

                 vMensajeInfoUsuario:=  '<TR><TD align="center">' || v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||'<TD align="center">' || v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' || '<TD align="center">' ||  v_rcurValUsu.Dni_Usu  || '</TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;

             END IF;

             vResult_EXISTE_USU_SIN_CARNE :='S';

         END LOOP;


             IF (vResult_EXISTE_USU_SIN_CARNE = 'S') then

             --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' || '<TR><TD  >' || 'Cod Trab Rrhh' || '</TD>' || '<TD align="center">' ||' Nombres' || '</TD>' || '<TD align="center">' ||'Dni' || '</TD></TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');

                    INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA USUARIO(S) SIN CARNÉ SANIDAD:',
                                                'ALERTA',
                                                ' EXISTE(N) USUARIO(S) SIN CARNÉ SANIDAD EN LOCAL:'|| '   '|| v_vDescLocal || '       ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR>Datos del Trabajador<BR>' ||
                                                '</B>'||
                                                '<BR> <I>Verifique:</I> <BR>'|| vMensajeFinal || '<B>');

             END IF;

       END;

 /****************************************asolis*****************************************/
 --Usado en Job
 PROCEDURE USU_ALERTA_CARNE_P_VENCER_ADL(cCodCia_in    IN CHAR,
                                    cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR)

  IS

  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (900);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  vResult_EXISTE_USU_SIN_CARNE char(1):='N';
  cFechaMensaje  varchar (150);
   v_vDescLocal varchar (150);

  CURSOR  curValUsu  IS

     SELECT SEC_USU_LOCAL ,
             nvl(Cod_Trab_Rrhh,' ') Cod_Trab_Rrhh,
             APE_PAT ,
             NVL(APE_MAT,' ') APE_MAT,
             NOM_USU ,
             nvl(DNI_USU,' ') DNI_USU ,
             CARNE_SANIDAD,
             FEC_EXPEDICION,
             FEC_VENCIMIENTO,
             COD_GRUPO_CIA ,
             COD_LOCAL
      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    EST_USU ='A'
      AND    CARNE_SANIDAD IS NOT  NULL
      AND    ((TRUNC((FEC_VENCIMIENTO)-30)<=TRUNC(SYSDATE) AND TRUNC(FEC_VENCIMIENTO)>=TRUNC(SYSDATE))--POR VENCER
               OR TRUNC(FEC_VENCIMIENTO)<TRUNC(SYSDATE))--VENCIDAS

      AND    SEC_USU_LOCAL BETWEEN '001' AND '899'
      order by Cod_Trab_Rrhh;

      v_rcurValUsu curValUsu%ROWTYPE;

      BEGIN

       FOR v_rcurValUsu IN curValUsu
         LOOP
         EXIT WHEN curValUsu%NOTFOUND;

             IF  v_rcurValUsu.Sec_Usu_Local IS NOT NULL THEN
                    DBMS_OUTPUT.PUT_LINE('si hay datos');

                  IF TRUNC(v_rcurValUsu.Fec_Vencimiento)<=TRUNC(SYSDATE) THEN
                     DBMS_OUTPUT.PUT_LINE('VENCIDO');
                     --mostrará la fecha de vencimiento en rojo
                 vMensajeInfoUsuario:=
                 '<TR><TD align="center">' ||v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Dni_Usu  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Carne_Sanidad  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Expedicion  || '</TD>' ||
                     '<TD align="center"><font color="red"><b>' ||v_rcurValUsu.Fec_Vencimiento  || '</b></font></TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;


                  ELSE

                 vMensajeInfoUsuario:=
                 '<TR><TD align="center">' ||v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Dni_Usu  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Carne_Sanidad  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Expedicion  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Vencimiento  || '</TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;


                  END IF;

             END IF;

             vResult_EXISTE_USU_SIN_CARNE :='S';

         END LOOP;


             IF (vResult_EXISTE_USU_SIN_CARNE = 'S') then

             --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' || '<TR><TD align="center">' || 'Cod Trab Rrhh' || '</TD>' || '<TD align="center">' ||' Nombres' || '</TD>'
                                                                                  || '<TD align="center">' ||'Dni' || '</TD>'
                                                                                  || '<TD align="center">' ||'Nro Carné' || '</TD>'
                                                                                  || '<TD align="center">' ||'Fecha Expedición' || '</TD>'
                                                                                  || '<TD align="center">' ||'Fecha Vencimiento' || '</TD>'
                                                                                  || '</TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');
              ---INT_ENVIA_CORREO_INFORMACION
                    ENVIA_CORREO_INF_MAIL_LOCAL(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA VENCIMIENTO DE CARNÉ SANIDAD:',
                                                'ALERTA',
                                                ' EXISTE(N) CARNÉ(S) DE SANIDAD VENCIDO(S) O POR VENCER  EN EL LOCAL:'|| '   '|| v_vDescLocal || '  ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR><BR>' ||
                                                '</B>'||
                                                 '<BR>Datos del Trabajador<BR>' ||
                                                '<BR><I>Verifique Fecha de Vencimiento del Carné</I> <BR>'|| vMensajeFinal || '<B>');
             END IF;

       END;




 /*********************************asolis************************************************/
 --Usado en Job
 PROCEDURE USU_ALERTA_CARNE_P_VENCER_MG(cCodCia_in    IN CHAR,
                                    cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR)

  IS

  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (900);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  vResult_EXISTE_USU_SIN_CARNE char(1);
  cFechaMensaje  varchar (150);
   v_vDescLocal varchar (150);

  CURSOR  curValUsu  IS

     SELECT SEC_USU_LOCAL ,
             nvl(Cod_Trab_Rrhh,' ') Cod_Trab_Rrhh,
             APE_PAT ,
             NVL(APE_MAT,' ') APE_MAT,
             NOM_USU ,
             nvl(DNI_USU,' ') DNI_USU ,
             CARNE_SANIDAD,
             FEC_EXPEDICION,
             FEC_VENCIMIENTO,
             COD_GRUPO_CIA ,
             COD_LOCAL
      FROM   PBL_USU_LOCAL
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND   COD_LOCAL     = cCodLocal_in
      AND   EST_USU ='A'
      AND   CARNE_SANIDAD IS NOT  NULL
      AND   (TRUNC((FEC_VENCIMIENTO)-30)=TRUNC(SYSDATE) OR TRUNC((FEC_VENCIMIENTO)-10)=TRUNC(SYSDATE) --POR VENCER
                  OR TRUNC(FEC_VENCIMIENTO)<TRUNC(SYSDATE)) --vencidas


      AND    SEC_USU_LOCAL BETWEEN '001' AND '899'
      order by Cod_Trab_Rrhh;

      v_rcurValUsu curValUsu%ROWTYPE;

      BEGIN

       FOR v_rcurValUsu IN curValUsu
         LOOP
           EXIT WHEN curValUsu%NOTFOUND;
             IF  v_rcurValUsu.Sec_Usu_Local IS NOT NULL THEN
                 DBMS_OUTPUT.PUT_LINE('si hay datos');

                  IF TRUNC(v_rcurValUsu.Fec_Vencimiento)<=TRUNC(SYSDATE) THEN
                     DBMS_OUTPUT.PUT_LINE('VENCIDO');
                     --mostrará la fecha de vencimiento en rojo
                 vMensajeInfoUsuario:=
                 '<TR><TD align="center">' ||v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Dni_Usu  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Carne_Sanidad  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Expedicion  || '</TD>' ||
                     '<TD align="center"><font color="red"><b>' ||v_rcurValUsu.Fec_Vencimiento  || '</b></font></TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;


                  ELSE
                 DBMS_OUTPUT.PUT_LINE('NO VENCIDO');

                 vMensajeInfoUsuario:=
                 '<TR><TD align="center">' ||v_rcurValUsu.Cod_Trab_Rrhh || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Nom_Usu || ' ' ||  v_rcurValUsu.Ape_Pat || ' ' || v_rcurValUsu.Ape_Mat || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Dni_Usu  || '</TD>' ||
                      '<TD align="center">' ||v_rcurValUsu.Carne_Sanidad  || '</TD>' ||
                       '<TD align="center">' ||v_rcurValUsu.Fec_Expedicion  || '</TD>' ||
                     '<TD align="center">' ||v_rcurValUsu.Fec_Vencimiento  || '</TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;

                 end if;

             END IF;

             vResult_EXISTE_USU_SIN_CARNE :='S';

         END LOOP;


             IF (vResult_EXISTE_USU_SIN_CARNE = 'S') then

             --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' || '<TR><TD align="center">' || 'Cod Trab Rrhh' || '</TD>' || '<TD align="center">' ||' Nombres' || '</TD>'
                                                                                  || '<TD align="center">' ||'Dni' || '</TD>'
                                                                                  || '<TD align="center">' ||'Nro Carné' || '</TD>'
                                                                                  || '<TD align="center">' ||'Fecha Expedición' || '</TD>'
                                                                                  || '<TD align="center">' ||'Fecha Vencimiento' || '</TD>'
                                                                                  || '</TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');

                    --INT_ENVIA_CORREO_INFORMACION
                    ENVIA_CORREO_INF_MG(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA VENCIMIENTO DE CARNÉ SANIDAD:',
                                                'ALERTA',
                                              ' EXISTE(N) CARNÉ(S) DE SANIDAD VENCIDO(S) O POR VENCER  EN EL LOCAL:'|| '   '|| v_vDescLocal || '  ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR><BR>' ||
                                                '</B>'||
                                                 '<BR>Datos del Trabajador<BR>' ||
                                                '<BR><I>Verifique Fecha de Vencimiento del Carné</I> <BR>'|| vMensajeFinal || '<B>');

             END IF;

       END;
/*-----------------------------------------asolis---------------------------------------------------------------------------*/

  FUNCTION USU_OBTIENE_FECVENC_PROX (cCodGrupoCia_in   IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cCodTrab_in IN CHAR)
  RETURN CHAR

  IS
   v_Fecha  CHAR(12):='';

  BEGIN

 SELECT
   CASE
   WHEN TRUNC((FEC_VENCIMIENTO)-30)<=TRUNC(SYSDATE) AND TRUNC(FEC_VENCIMIENTO)>=TRUNC(SYSDATE) THEN
       NVL(TO_CHAR(FEC_VENCIMIENTO,'dd/MM/yyyy'),' ')   --POR VENCER


   WHEN TRUNC(FEC_VENCIMIENTO)<TRUNC(SYSDATE) THEN
       NVL(TO_CHAR(FEC_VENCIMIENTO,'dd/MM/yyyy'),' ')  --VENCIDOS
   END

   INTO v_Fecha

   FROM   PBL_USU_LOCAL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          --AND    Cod_Trab_Rrhh = cCodTrabRR_HH_in;
          AND    SEC_USU_LOCAL = cCodTrab_in;

      BEGIN

      IF v_Fecha IS NULL THEN
          v_Fecha :='NV'; --No se ha vencido el nro carné
          DBMS_OUTPUT.put_line('v_Fecha:'||v_Fecha);

      ELSIF TO_DATE(v_Fecha)<TRUNC(sysdate) THEN
          v_Fecha :='V'; --Carné Vencido
           DBMS_OUTPUT.put_line('v_Fechav:'||v_Fecha);

      ELSE
           v_Fecha :='P'; --Carné próximo a Vencer
            DBMS_OUTPUT.put_line('v_Fechap:'||v_Fecha);

      END IF;

      END;

  return v_Fecha;

  END;

/***********************************asolis************************************************/


  FUNCTION USU_EXISTE_DUPLICADO_CARNE(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        COD_TRAB_RRHH_IN IN CHAR,
                                       cNumCarne_in     IN CHAR)
    RETURN VARCHAR2

    IS
    v_rpta CHAR(1);
    v_cant CHAR(3);

    BEGIN

     SELECT COUNT(*)
       INTO   v_cant
      FROM   PBL_USU_LOCAL C
      WHERE
             COD_TRAB_RRHH != COD_TRAB_RRHH_IN
      AND    COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    CARNE_SANIDAD  = cNumCarne_in;

          IF v_cant = 0 THEN
             v_rpta :='0';
          ELSE
             v_rpta :='1';
          END IF;
       RETURN v_rpta;

    END;


/**************************asolis***************************/

   FUNCTION USU_OBTIENE_FECVENC_PROX_CARNE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodTrab_in IN CHAR)
    RETURN char
  IS
   v_Fecha  CHAR(12);

  BEGIN

 SELECT
   CASE
   WHEN TRUNC((FEC_VENCIMIENTO)-30)<=TRUNC(SYSDATE) AND TRUNC(FEC_VENCIMIENTO)>=TRUNC(SYSDATE) THEN
       NVL(TO_CHAR(FEC_VENCIMIENTO,'dd/MM/yyyy'),' ')   --POR VENCER


   WHEN TRUNC(FEC_VENCIMIENTO)<TRUNC(SYSDATE) THEN
       NVL(TO_CHAR(FEC_VENCIMIENTO,'dd/MM/yyyy'),' ')  --VENCIDOS
   END

   INTO v_Fecha

   FROM   PBL_USU_LOCAL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND    SEC_USU_LOCAL = cCodTrab_in;


      BEGIN

      IF v_Fecha IS NULL THEN
          v_Fecha :='NV'; --No se ha vencido el nro carné
          DBMS_OUTPUT.put_line('v_Fecha:'||v_Fecha);



      END IF;

      END;

  return v_Fecha;

  END;

 /*************************asolis********************************/

  FUNCTION USU_VERIFICA_EXISTENCIA_CARNE(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in   IN CHAR,
                                           cCodTrab_in    IN CHAR)

    RETURN char

    IS
    vCarne char(20);

 BEGIN

      SELECT CARNE_SANIDAD INTO vCarne FROM  PBL_USU_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND   SEC_USU_LOCAL = cCodTrab_in
          AND   EST_USU ='A';


         BEGIN
         IF  vCarne is null THEN
             vCarne := '0';
         END IF;
         END;


     RETURN vCarne;

 END;
/***********************************************************/


  PROCEDURE USU_ALERTA_TRAB_S_CARNE_M_ING(cCodGrupoCia_in    IN CHAR,
                                          cCodLocal_in       IN CHAR,
                                          cCodTrab_in        IN CHAR)


   IS

  vMensajeTitulo  varchar (500);
  vMensajeInfoUsuario   varchar2 (900);
  vMensajeFila   varchar2 (5200);
  vMensajeFinal  varchar2 (8000);
  vResult_EXISTE_USU_SIN_CARNE char(1);
  cFechaMensaje  varchar (150);
  v_vDescLocal varchar (150);

  v_vSEC_USU_LOCAL  PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
  v_vCod_Trab_Rrhh  PBL_USU_LOCAL.Cod_Trab_Rrhh%TYPE;
  v_vAPE_PAT  PBL_USU_LOCAL.APE_PAT%TYPE;
  v_vAPE_MAT  PBL_USU_LOCAL.APE_MAT%TYPE;
  v_vNOM_USU  PBL_USU_LOCAL.NOM_USU%TYPE;
  v_vDNI_USU  PBL_USU_LOCAL.DNI_USU%TYPE;
  v_CARN_SANIDAD  PBL_USU_LOCAL.CARNE_SANIDAD%TYPE;

      BEGIN
      SELECT A.SEC_USU_LOCAL ,
             nvl(A.COD_TRAB_RRHH,' ') Cod_Trab_Rrhh,
             A.APE_PAT ,
             NVL(A.APE_MAT,' ') APE_MAT,
             A.NOM_USU ,
             nvl(A.DNI_USU,' ') DNI_USU,
             A.CARNE_SANIDAD
      INTO   v_vSEC_USU_LOCAL,
             v_vCod_Trab_Rrhh,
             v_vAPE_PAT,
             v_vAPE_MAT,
             v_vNOM_USU,
             v_vDNI_USU,
             v_CARN_SANIDAD
      FROM   PBL_USU_LOCAL A
      WHERE  A.COD_GRUPO_CIA    = cCodGrupoCia_in
      AND    A.COD_LOCAL  = cCodLocal_in
      AND    A.EST_USU ='A'
      AND    A.SEC_USU_LOCAL = cCodTrab_in;

             IF  v_vSEC_USU_LOCAL IS NOT NULL THEN
                 DBMS_OUTPUT.PUT_LINE('si hay datos');
                 vMensajeInfoUsuario:=  '<TR><TD align="center">' || v_vCod_Trab_Rrhh || '</TD>' ||'<TD align="center">' || v_vNOM_USU || ' ' ||  v_vAPE_PAT || ' ' || v_vAPE_MAT || '</TD>' || '<TD align="center">' ||  v_vDNI_USU  || '</TD></TR>';
                 vMensajeFila := vMensajeFila || vMensajeInfoUsuario;
             END IF;

             --JCORTEZ 09/03/2009  solo si no tiene carne de sanidad
             IF(v_CARN_SANIDAD IS NULL)THEN
               vResult_EXISTE_USU_SIN_CARNE :='S';
             ELSE
               vResult_EXISTE_USU_SIN_CARNE :='N';
             END IF;


             IF (vResult_EXISTE_USU_SIN_CARNE = 'S') then

             --DESCRIPCION DE LOCAL
                        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                        INTO   v_vDescLocal
                        FROM   PBL_LOCAL
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in;

              vMensajeTitulo :='<BR>  <BR>' || '<TABLE  BORDER=1 align="center">' || '<TR><TD  >' || 'Cod Trab Rrhh' || '</TD>' || '<TD align="center">' ||' Nombres' || '</TD>' || '<TD align="center">' ||'Dni' || '</TD></TR>';
              vMensajeFinal := vMensajeTitulo || vMensajeFila  ||'</TABLE>' ;
              cFechaMensaje := TO_CHAR (TRUNC (SYSDATE), 'DD/MM/YYYY ');

                    ENVIA_CORREO_INF_MAIL_LOCAL(cCodGrupoCia_in,cCodLocal_in,
                                                'ALERTA USUARIO SIN CARNÉ SANIDAD:',
                                                'ALERTA',
                                                ' EXISTE USUARIO SIN CARNÉ SANIDAD EN LOCAL:'|| '   '|| v_vDescLocal || '       ' || '<B> Fecha :</B>' || cFechaMensaje ||'<BR>' ||
                                                '<BR>Datos del Trabajador<BR>' ||
                                                '</B>'||
                                                '<BR> <I>Verifique:</I> <BR>'|| vMensajeFinal || '<B>');

             END IF;

       END;

/************************************************************/

END;

/
