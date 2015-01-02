--------------------------------------------------------
--  DDL for Package FARMA_SECURITY
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FARMA_SECURITY" AS

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
