--------------------------------------------------------
--  DDL for Package FARMA_ALERTUP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FARMA_ALERTUP" AS

  TYPE FarmaCursor IS REF CURSOR;

   FUNCTION F_CUR_ALERTA_MENSAJES(cCodGrupoCia   IN CHAR,
                                  cCodLocal       IN CHAR)
   RETURN FarmaCursor;

END FARMA_ALERTUP;

/
