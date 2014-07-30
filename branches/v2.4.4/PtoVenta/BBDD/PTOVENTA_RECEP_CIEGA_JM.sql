--------------------------------------------------------
--  DDL for Package PTOVENTA_RECEP_CIEGA_JM
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RECEP_CIEGA_JM" IS

 TYPE FarmaCursor IS REF CURSOR;
  g_nCodNumeraAuxConteoProd       PBL_NUMERA.COD_NUMERA%TYPE := '075';
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
  v_gNombreDiretorioAlert VARCHAR2(50) := 'DIR_INTERFACES';
/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario	  	Comentario
  --16/11/2009  jmiranda    Creación
 PROCEDURE RECEP_P_INS_AUX_CONTEO (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      nSecAuxConteo_in IN NUMBER,
                                      cCodBarra_in IN VARCHAR2,
                                      nCantidad_in IN NUMBER,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR,
                                      vNroBloque_in IN NUMBER,
                                      vIndDeteriorado_in IN CHAR DEFAULT 'N',
                                      vIndFueraLote_in IN CHAR DEFAULT 'N',
                                      vIndNoFound_in IN CHAR DEFAULT 'N');


/****************************************************************************/
 FUNCTION RECEP_F_DATOS_PROD(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cNroBloque_in IN CHAR,
                             cAuxSecProd_in IN CHAR)

  RETURN FarmaCursor;

/****************************************************************************/
  FUNCTION RECEP_F_CUR_LIS_PRIMER_CONTEO(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	 IN CHAR,
                                        cNroRecep_in IN CHAR,
                                        cNroBloque_in IN CHAR)
  RETURN FarmaCursor;

/****************************************************************************/
  FUNCTION RECEP_F_NRO_BLOQUE_CONTEO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cNroRecep_in IN CHAR,
                                     vIp_in IN VARCHAR)
  RETURN NUMBER;


/****************************************************************************/

  PROCEDURE RECEP_P_INS_PROD_CONTEO(   cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cNroRecep_in IN CHAR,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR);

/****************************************************************************/
  PROCEDURE RECEP_P_UPD_CAB (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cEstado_in IN CHAR,
                             cUsuCuenta_in IN CHAR,
                             cIpModRecep_in IN VARCHAR2);

/****************************************************************************/

 FUNCTION RECEP_P_VER_ESTADO (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR)
 RETURN CHAR;

/****************************************************************************/
  PROCEDURE RECEP_P_ELIMINA_AUX (cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cNroRecep_in IN CHAR,
                                cNroBloque_in IN CHAR,
                                cSecAuxConteo_in IN NUMBER);

/****************************************************************************/
    PROCEDURE RECEP_P_UPD_AUX_CONTEO (cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             cNroRecep_in IN CHAR,
                             cNroBloque_in IN CHAR,
                             cSecAuxConteo_in IN NUMBER,
                             cCantidad_in IN NUMBER,
                             cUsuCuenta_in IN CHAR,
                             cIpModRecep_in IN VARCHAR2);
/****************************************************************************/
   PROCEDURE RECEP_P_ENVIA_CORREO_CONTEO(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         cNroRecepcion IN CHAR,
                                         cNroBloque IN CHAR);

/****************************************************************************/
  PROCEDURE RECEP_P_CORREO_ADJUNTO_CONTEO(vAsunto_in        IN CHAR,
                                     vTitulo_in        IN CHAR,
                                     vMensaje_in       IN CHAR,
                                     vInd_Archivo_in   IN CHAR DEFAULT 'N',
                                     vNombre_Archivo_in IN VARCHAR2 DEFAULT null
                                     );
/***********************************************************************************************************************/
  FUNCTION RECEP_F_EMAIL_CB_NO_HALLADO
  RETURN VARCHAR2;

/***********************************************************************************************************************/
  FUNCTION RECEP_F_VERIF_AUX_PROD(cGrupoCia_in  IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cNroRecepcion IN CHAR)
  RETURN CHAR;
/************************************************************************************/
    /*
    FUNCTION RECEP_F_VAR2_IP_CONTEO(cGrupoCia_in  IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    vIp_in        IN VARCHAR2)
    RETURN VARCHAR2;
    */

/* ********************************************************************* */
  PROCEDURE RECEP_P_BLOQUEO_RECEPCION(cGrupoCia_in  IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cNroRecepcion IN CHAR);
/* ********************************************************************* */

  FUNCTION RECEP_F_GET_MSG_PEND
  RETURN VARCHAR2;

/***********************************************************************************************************************/
  --Descripcion: Destinatario ingreso transportista.
  --Fecha       Usuario   Comentario
  --16/04/2014  ERIOS     Creacion  
  FUNCTION RECEP_F_EMAIL_ING_TRANSP
  RETURN VARCHAR2;
  
END PTOVENTA_RECEP_CIEGA_JM;

/
