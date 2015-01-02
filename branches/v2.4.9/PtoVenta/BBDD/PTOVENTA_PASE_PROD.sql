--------------------------------------------------------
--  DDL for Package PTOVENTA_PASE_PROD
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_PASE_PROD" is

  -- Author  : DUBILLUZ
  -- Created : 07/12/2007 05:36:29 p.m.
  -- Purpose :
    TYPE FarmaCursor IS REF CURSOR;

  FUNCTION PROC_PRELIMINAR_PASE_PROD(vIdUserName_in   IN CHAR)
  RETURN CHAR;

  /**********************************************************************/
  FUNCTION PROC_EJECUTA_RESPALDO(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                 vIdUsuario_in    IN CHAR,
                                 vIdUserName_in   IN CHAR)
  RETURN CHAR;

end PTOVENTA_PASE_PROD;

/
