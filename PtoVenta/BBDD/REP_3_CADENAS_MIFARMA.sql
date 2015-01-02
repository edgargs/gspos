--------------------------------------------------------
--  DDL for Package REP_3_CADENAS_MIFARMA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."REP_3_CADENAS_MIFARMA" AS

  TYPE FarmaCursor IS REF CURSOR;

  C_TIP_LOCAL_PEQUENO     NUMBER := '1';
  C_TIP_LOCAL_PEQUENO_DOS NUMBER := '2';
  C_MOTIVO_SAL_INSUMO     CHAR(3):='522';
  /* ************************************************************* */
  PROCEDURE INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2);
  PROCEDURE INV_CALC_VTA_SIN_PROM(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2);
  PROCEDURE REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProdAnt_in IN CHAR, cCodProdNue_in IN CHAR, dFecha_in IN DATE);
  PROCEDURE P_CREA_RES_VTA_PROD_LOCAL(cCodGrupoCia_in IN VARCHAR2,
                                      cCodLocal_in    IN VARCHAR2);

  /* ************************************************************* */
  PROCEDURE P_PROCESO_CALCULA_PICOS(cCodGrupoCia_in IN VARCHAR2,
                                    cCodLocal_in    IN VARCHAR2);

  /* ************************************************************* */
  PROCEDURE P_STK_LOCAL_AND_TRANSITO(cCodLocal_in IN VARCHAR2);

  /* ************************************************************* */
  function f_get_trans_prod(cCodGrupoCia_in in char,
                            cCodLocal_in    in char,
                            cCodProd_in     in char) return number;
  function f_get_pvm_prod(cCodLocal_in in char, cCodProd_in in char)
    return number;

 function f_get_stk_calculado
    (vTipStk varchar2,cCantUnidVta_S number,cCantUnidVta_M number,cFrec number,cCodLocal char,
    vCodProd_in varchar2
    ) return number;

  /* ************************************************************* */
  PROCEDURE P_VTA_MES_PROD_LOCAL(cCodLocal_in in char);
  procedure P_VTA_PVM_PROD_LOCAL(cCodLocal_in in char);
  /* ************************************************************* */

  PROCEDURE P_PROD_LOCAL_SEMANA(cCodLocal_in in char);
  /* ************************************************************* */
  PROCEDURE P_AUX_MEJOR_PROD_LOCAL(cCodLocal_in in char);
  /* ************************************************************* */
  PROCEDURE P_CALCULO_PED_REP_LOCAL(cCodLocal_in in char);
  /* *************************************************************** */
  PROCEDURE P_OPERA_ALGORITMO_MF(cCodLocal_in in char);
  /* *************************************************************** */
  procedure P_ACTUALIZA_PROD_LOCAL_REP(cCodLocal_in in char);

  procedure P_CALCULO_PROMO_COMPRA(cCodLocal_MF varchar2);

  /* *************************************************************** */
  PROCEDURE REP_ACTUALIZA_CANT_SUG(vCodGrupoCia IN CHAR, vCodLocal IN CHAR,
                                   vCodGrupo IN CHAR DEFAULT NULL);
 /* *************************************************************** */
  PROCEDURE VERIFICA_VENTAS_REP(vCodLocal IN CHAR);
 /* *************************************************************** */                                   

END;

/
