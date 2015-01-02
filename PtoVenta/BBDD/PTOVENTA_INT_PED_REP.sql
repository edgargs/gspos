--------------------------------------------------------
--  DDL for Package PTOVENTA_INT_PED_REP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_INT_PED_REP" is

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
