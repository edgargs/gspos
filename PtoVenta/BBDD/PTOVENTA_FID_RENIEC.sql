--------------------------------------------------------
--  DDL for Package PTOVENTA_FID_RENIEC
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_FID_RENIEC" AS

  -- Author  : DVELIZ
  -- Created : 26/09/2008 11:08:41 a.m.
  -- Purpose :

  ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
  INDICADOR_NO      CHAR(1):='N';

  COL_ET_RENIEC_DNI integer := 1;
  COL_ET_RENIEC_NOMBRE integer := 2;
  COL_ET_RENIEC_APE_PAT integer := 3;
  COL_ET_RENIEC_APE_MAT integer := 4;
  COL_ET_RENIEC_SEXO    integer := 5;
  COL_ET_RENIEC_FN    integer := 6  ;

  TYPE FarmaCursor IS REF CURSOR;
  CC_MOD_TAR_FID char(2):='TF';--TARJETAS DE FIFELIZACION
  CC_COD_NUMERA  CHAR(3):='070';


    --Descripcion: Se valida que exista en table RENIEC y sea menor que el año definido
    --Fecha       Usuario      Comentario
    --05/10/2009  JCORTEZ     Creacion
  FUNCTION FID_F_VALIDA_DNI_REC(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cDni_in          IN CHAR,
                               cFecha           IN CHAR)
  RETURN CHAR;

  --Descripcion: Se obtiene mensaje de
  --Fecha       Usuario      Comentario
  --05/10/2009  JCORTEZ     Creacion
  FUNCTION F_VAR2_GET_MENSAJE(cGrupoCia_in  IN CHAR,
                                cCodLocal_in  IN CHAR)
  RETURN VARCHAR2;

    FUNCTION FID_F_TIP_DOC  (cGrupoCia_in  IN CHAR)
  RETURN FarmaCursor;

    /***********************************************************/
  PROCEDURE ENVIA_CORREO_CONFIRMACION(cCodGrupoCia  IN CHAR,
                                      cCodLocal        IN CHAR,
                                      cUsuCrea         IN CHAR,
                                      cSecUsu          IN CHAR,
                                      cTipDoc          IN CHAR,
                                      cNumDoc          IN CHAR,
                                      cNomCli          IN CHAR,
                                      cFecNac          IN CHAR,
                                      pCodTarjeta      IN CHAR DEFAULT 'N');

   FUNCTION F_GET_ROL_CONFIRMACION(cGrupoCia_in  IN CHAR,
                                    cCodLocal_in  IN CHAR)
  RETURN VARCHAR2;

  /* ********************************************************* */
  FUNCTION F_VAR2_GET_IND_VALIDA_RENIEC
  RETURN VARCHAR2;
  /* ************************************************** */
  FUNCTION F_VAR2_GET_IND_CLAVE_CONF
  RETURN VARCHAR2;


  /* ************************************************** */
  PROCEDURE P_GENERA_TARJETA_DNI(
                                 cCodGrupoCia  IN CHAR,
                                 cCodLocal_in  IN CHAR,
                                 cDni_in       IN CHAR
                                );
 /* ***************************************************** */
  FUNCTION F_GENERA_EAN13(vCodigo_in IN VARCHAR2)
  RETURN CHAR;
 /* ***************************************************** */
  FUNCTION F_VALIDACION_FINAL_DNI(cCodGrupoCia_in        IN CHAR,
                                  cCodLocal_in           IN CHAR,
                                  cUsuLogin_in           IN CHAR,
                                  cFrmDni_in             IN CHAR,
                                  cFrmNombre_in          IN CHAR,
                                  cFrmFechaNacimiento_in IN CHAR,
                                  cValidaDni_in          IN CHAR,
                                  cTercerDni_in          IN CHAR)
    RETURN varchar2;


END PTOVENTA_FID_RENIEC;

/
