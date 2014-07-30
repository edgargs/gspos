--------------------------------------------------------
--  DDL for Package REP_3_CADENAS_PROMO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."REP_3_CADENAS_PROMO" AS

  TYPE FarmaCursor IS REF CURSOR;

  C_TIP_LOCAL_PEQUENO     NUMBER := '1';
  C_TIP_LOCAL_PEQUENO_DOS NUMBER := '2';

  /* ************************************************************* */
  PROCEDURE INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2);
  PROCEDURE REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProdAnt_in IN CHAR, cCodProdNue_in IN CHAR, dFecha_in IN DATE);
  PROCEDURE P_CREA_RES_VTA_PROD_LOCAL(cCodGrupoCia_in IN VARCHAR2,
                                      cCodLocal_in    IN VARCHAR2);

  /* ************************************************************* */
  PROCEDURE P_PROCESO_CALCULA_PICOS(cCodLocal_in    IN VARCHAR2);

  /* ************************************************************* */
  PROCEDURE P_STK_LOCAL_AND_TRANSITO(cCodLocal_in IN VARCHAR2);
  PROCEDURE P_CALULA_VTA_PROM(cCodLocal_in in char);
  /* ************************************************************* */
  function f_get_trans_prod(cCodGrupoCia_in in char,
                            cCodLocal_in    in char,
                            cCodProd_in     in char) return number;

  /* ************************************************************* */
  PROCEDURE P_OPERA_MF_PROMO(cCodGrupoCia_in in Char,cCodLocal_in in char);
  /* *************************************************************** */


END;

/
