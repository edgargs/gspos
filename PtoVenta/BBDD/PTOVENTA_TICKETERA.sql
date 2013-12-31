--------------------------------------------------------
--  DDL for Package PTOVENTA_TICKETERA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TICKETERA" AS

   FUNCTION CAMP_F_VAR_MSJ_ANULACION(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in 	IN CHAR,
                                   cajero_in    	IN CHAR,
                                   turno_in   	IN CHAR,
                                   numpedido_in IN CHAR,
                                   cod_igv_in IN CHAR ,
                                   cIndReimpresion_in in CHAR)

  RETURN VARCHAR2;


PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR);

END;

/
