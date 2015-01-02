--------------------------------------------------------
--  DDL for Package PKG_REPORTES
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_REPORTES" IS

  FUNCTION FN_LISTA_KARDEX(A_FCH_INICIO VARCHAR2, A_FCH_FIN VARCHAR2) RETURN SYS_REFCURSOR;
  PROCEDURE SP_CARGA_KARDEX(A_FCH_INICIO VARCHAR2, A_FCH_FIN VARCHAR2);
END;

/
