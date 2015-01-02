--------------------------------------------------------
--  DDL for Package FARMA_RECARGA_VIRTUAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FARMA_RECARGA_VIRTUAL" AS

  FUNCTION F_NUM_INTENTOS_RESPUESTA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR) RETURN NUMBER;

  FUNCTION F_LEAD_TIEMPO_RPTA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR) RETURN NUMBER;

  FUNCTION F_TIME_OUT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
    RETURN NUMBER;

END FARMA_RECARGA_VIRTUAL;

/
