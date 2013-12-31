--------------------------------------------------------
--  DDL for Package PKG_SOL_STOCK
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_SOL_STOCK" is

  FUNCTION SP_GRABA_SOL(A_COD_GRUPO_CIA CAB_SOLICITUD_STOCK.COD_GRUPO_CIA%TYPE,
                         A_COD_LOCAL     CAB_SOLICITUD_STOCK.COD_LOCAL%TYPE,
                         A_USU_SOLICITUD CAB_SOLICITUD_STOCK.USU_SOLICITUD%TYPE,
                         A_QF_APROBADOR  CAB_SOLICITUD_STOCK.QF_APROBADOR%TYPE,
                         A_COD_PRODUCTO  DET_SOLICITUD_STOCK.COD_PRODUCTO%TYPE,
                         A_CANT_PRODUCTO DET_SOLICITUD_STOCK.CANT_PRODUCTO%TYPE,
                         A_VAL_FRACCION  DET_SOLICITUD_STOCK.VAL_FRACCION%TYPE,
                         A_ID_SOLICITUD  varchar2
                         ) RETURN VARCHAR2;
                         
  FUNCTION FN_DEV_SEC RETURN CAB_SOLICITUD_STOCK.ID_SOLICITUD%TYPE;                       
  PROCEDURE INV_INGRESA_AJUSTE_KARDEX(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodProd_in      IN CHAR,
                                      cCodMotKardex_in IN CHAR,
                                      cNeoCant_in      IN CHAR,
                                      cGlosa_in        IN CHAR,
                                      cTipDoc_in       IN CHAR,
                                      cUsu_in          IN CHAR,
                                      cIndCorreoAjuste IN CHAR DEFAULT 'S');

PROCEDURE SP_APRUEBA_SOL(A_COD_GRUPO_CIA CAB_SOLICITUD_STOCK.COD_GRUPO_CIA%TYPE,
                           A_COD_LOCAL     CAB_SOLICITUD_STOCK.COD_LOCAL%TYPE,
                           --A_ID_SOLICITUD  CAB_SOLICITUD_STOCK.ID_SOLICITUD%TYPE,
                           A_NUM_PED_VTA   CAB_SOLICITUD_STOCK.NUM_PED_VTA%TYPE) ;
                           
  FUNCTION SOL_F_PERMITE_VTA_NEGATIVA(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodProd_in      IN CHAR,
                                      cValFracVta_in IN NUMBER,
                                      cCtd_in        IN NUMBER)    RETURN VARCHAR2;                                      

FUNCTION F_PERMITE_VTA_NEGATIVA  RETURN VARCHAR2;
                                      
end;

/
