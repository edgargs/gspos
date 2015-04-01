--------------------------------------------------------
--  DDL for Package PKG_DAEMON_TRANSF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_DAEMON_TRANSF" AS 

  TYPE FarmaCursor IS REF CURSOR;

  FUNCTION GET_LOCALES_ENVIO(cCodGrupoCia_in     IN CHAR,
                                        cCodCia_in  IN CHAR)
  RETURN FarmaCursor;

  FUNCTION GET_NOTAS_PENDIENTES(cCodGrupoCia_in     IN CHAR,
                                        cCodCia_in  IN CHAR,
										cCodLocal_in IN CHAR)
  RETURN FarmaCursor;
  
  PROCEDURE UPD_ENVIO_DESTINO(cCodGrupoCia_in     IN CHAR,
                                        cCodCia_in  IN CHAR,
										cCodLocal_in IN CHAR,
										cNumNotaEs_in IN CHAR);
										
  PROCEDURE GRABA_REG_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,
									cCodCia_in  IN CHAR,
									cCodLocal_in IN CHAR,
									cNumNotaEs_in IN CHAR);
  
END PKG_DAEMON_TRANSF;

/
