--------------------------------------------------------
--  DDL for Package PKG_PRE_VENTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_PRE_VENTA" IS
    /**********************************************************************/
    -------------------------------------------------------------
    CONS_PREVTA_REGULAR         CHAR(3) := '001';
    CONS_PREVTA_CONVENIO        CHAR(3) := '001';
    CONS_PREVTA_MAYORISTA       CHAR(3) := '002';
    CONS_PREVTA_COBRORESPON     CHAR(3) := '001';
    CONS_PREVTA_CANJE           CHAR(3) := '001';
    CONS_PREVTA_SERVICIOS       CHAR(3) := '001';
    CONS_PREVTA_COBRANZAVTACRED CHAR(3) := '001';
    CONS_PREVTA_COTIZACION      CHAR(3) := '001';
    CONS_PREVTA_FACT_SERVICIOS  CHAR(3) := '001';
    CONS_PREVTA_GUIAS           CHAR(3) := '001';
    CONS_PREVTA_MAGISTRAL       CHAR(3) := '001';
    -------------------------------------------------------------
    /**********************************************************************/
    /**********************************************************************/
    -- Author  : VTIJERO
    -- Created : 10/11/2006 03:30:13 p.m.
    -- Purpose :
    /*
    001   POR IMPORTE
    002   POR CANTIDAD
    004   COMERCIALIZACION
    005   ALMACENAR
    006   POR DESCUENTO
    */
    V_TIP_IMPORTE   VARCHAR2(3) := '001';
    V_TIP_CANTIDAD  VARCHAR2(3):= '002';
    V_TIP_DESCUENTO VARCHAR2(3) := '006';

FUNCTION FN_EVALUA_PETITORIO
                      (A_COD_PETITORIO             CAB_PETITORIO.COD_PETITORIO%TYPE ,
                       A_COD_PRODUCTO              LGT_PROD.COD_PROD%TYPE   ,
                       A_CTD_FRACCIONAM            FLOAT
                       )
             RETURN VARCHAR ;
    FUNCTION FN_EVALUA_PRC_PETITORIO(A_CIA                     VARCHAR2,
                                     A_COD_LOCAL               PBL_LOCAL.COD_LOCAL%TYPE,
                                     A_COD_PETITORIO           CAB_PETITORIO.COD_PETITORIO%TYPE,
                                     A_COD_PRODUCTO            LGT_PROD.COD_PROD%TYPE,
                                     A_PRC_UNITARIO            FLOAT,
                                     A_CTD_PRODUCTO             FLOAT DEFAULT 0,
                                      A_CTD_PRODUCTO_FRAC       FLOAT DEFAULT 0,
                                     A_CTD_FRACCIONAMIENTO     FLOAT DEFAULT 0,
                                     A_TIP_PRECIO              CHAR DEFAULT NULL,
                                     A_TIP_VTA                 CHAR  DEFAULT NULL,
                                     A_COD_CONVENIO            MAE_CONVENIO.COD_CONVENIO%TYPE DEFAULT NULL) RETURN FLOAT;

END ;

/
