--------------------------------------------------------
--  DDL for Package PKG_OPERACIONES_LOCAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_OPERACIONES_LOCAL" AS

  TYPE FarmaCursor IS REF CURSOR;
  /* *************************************************************** */
  PROCEDURE P_EJECUTA_PEDIDO_LOCAL;
  /* *************************************************************** */

END;

/
