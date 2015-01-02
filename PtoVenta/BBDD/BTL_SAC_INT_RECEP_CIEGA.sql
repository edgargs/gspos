--------------------------------------------------------
--  DDL for Package BTL_SAC_INT_RECEP_CIEGA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."BTL_SAC_INT_RECEP_CIEGA" is

  --  g_cNumProdQuiebre INTEGER := 750;

  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;

  C_CANTIDAD_ARCHIVOS INTEGER := 5;
  
  PROCEDURE P_SOBRANTES_FALTANTES/*(cFechaProceso    in char,cFechaProcesoFin in char)*/;                           
  
  PROCEDURE P_CREA_FALTANTES(cCodLocal_in    in char,
                             cNroRecepcion in char);
                             
  PROCEDURE P_CREA_SOBRANTES(cCodLocal_in    in char,
                             cNroRecepcion in char);                             

  PROCEDURE INT_PED_FALTANTE(cCodGrupoCia_in IN CHAR);
  PROCEDURE INT_GEN_TXT_FALTANTE(cCodGrupoCia_in IN CHAR);


  PROCEDURE INT_PED_SOBRANTE(cCodGrupoCia_in IN CHAR);
  PROCEDURE INT_GEN_TXT_SOBRANTE(cCodGrupoCia_in IN CHAR);  
  /* ********************************************************************************* */


  FUNCTION FN_GET_COD_CLIENTE_SAP(COD_LOCAL_IN IN CHAR) RETURN VARCHAR2;

  FUNCTION FN_GET_CLIENTE(COD_LOCAL_IN IN CHAR) RETURN VARCHAR2;

  --Fecha       Usuario   Comentario
  --10/07/2006  ERIOS     Creacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         vAsunto_in      IN VARCHAR2,
                                         vTitulo_in      IN VARCHAR2,
                                         vMensaje_in     IN VARCHAR2);
                                         
 PROCEDURE p_completa_LOTE(cCodLocal_in IN CHAR,cEntrega in char);

end;

/
