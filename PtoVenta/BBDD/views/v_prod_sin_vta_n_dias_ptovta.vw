CREATE OR REPLACE FORCE VIEW PTOVENTA.V_PROD_SIN_VTA_N_DIAS_PTOVTA AS
SELECT DISTINCT "CODLOCAL","LOCAL","CODIGO","PRODUCTO","UNIDAD","LABORATORIO","STK","FARMA","COSTO_PROM","PER_SIN_VTA","PER_CON_STK",
       0 "UNID_VTA",
       0 "PER_VTA", 0 "UNID_VTA_PER",
       "NUM_RESULTADO",
--       "PER_VTA","UNID_VTA_PER",
       "RESULTADO"
FROM
(
    SELECT LOC.COD_LOCAL CODLOCAL,
           (LOC.COD_LOCAL ||'-'|| NVL(LOC.DESC_ABREV,' ')) LOCAL,
           P.COD_PROD CODIGO,
           P.DESC_PROD PRODUCTO,
           P.DESC_UNID_PRESENT UNIDAD,
           LAB.NOM_LAB LABORATORIO,
           (PL.STK_FISICO / PL.VAL_FRAC_LOCAL) STK,
           P.IND_PROD_FARMA FARMA,
           P.VAL_PREC_PROM COSTO_PROM,
           ULT_VTA.DIAS_SIN_VTA PER_SIN_VTA,
           STOCK.DIAS_CON_STK PER_CON_STK,
           VTA_30_D.UNID_VTA,
           VTA_LOC.PER_VTA,
           VTA_LOC.UNID_VTA UNID_VTA_PER,
           CASE WHEN ULT_VTA.MES_SIN_VTA IS NULL THEN NVL(STOCK.PER_STK,0)
                WHEN NVL(STOCK.PER_STK,0) < ULT_VTA.MES_SIN_VTA THEN NVL(STOCK.PER_STK,0)
                ELSE ULT_VTA.MES_SIN_VTA
           END NUM_RESULTADO,
           CASE
               CASE WHEN ULT_VTA.MES_SIN_VTA IS NULL THEN NVL(STOCK.PER_STK,0)
                    WHEN NVL(STOCK.PER_STK,0) < ULT_VTA.MES_SIN_VTA THEN NVL(STOCK.PER_STK,0)
                    ELSE ULT_VTA.MES_SIN_VTA
               END
               WHEN 0 THEN '< 30'
               WHEN 1 THEN '>= 30'
               WHEN 2 THEN '>= 60'
               ELSE '>= 90'
           END RESULTADO
    FROM   LGT_PROD P,
           LGT_LAB LAB,
           PBL_LOCAL LOC,
           LGT_PROD_LOCAL PL,
           (
            SELECT     R.COD_GRUPO_CIA,
                       R.COD_LOCAL,
                       R.COD_PROD,
                       (TRUNC(SYSDATE-1) - MAX(R.FEC_DIA_VTA)) DIAS_SIN_VTA,
                       CASE WHEN TRUNC((TRUNC(SYSDATE-1) - MAX(R.FEC_DIA_VTA)) / 30) > 3 THEN 3
                            ELSE TRUNC((TRUNC(SYSDATE-1) - MAX(R.FEC_DIA_VTA)) / 30)
                       END MES_SIN_VTA
            FROM       VTA_RES_VTA_PROD_LOCAL R
            WHERE      R.COD_GRUPO_CIA = '001'
            GROUP BY   R.COD_GRUPO_CIA,
                       R.COD_LOCAL,
                       R.COD_PROD
            HAVING     SUM(R.CANT_UNID_VTA) != 0
            ) ULT_VTA,
            (
              SELECT    COD_GRUPO_CIA,
                        COD_PROD,
                        COD_LOCAL,
                        SUM(CANT_UNID_VTA) UNID_VTA,
                        SUM(MON_TOT_VTA) MON_VTA
              FROM      VTA_RES_VTA_PROD_LOCAL
              WHERE     COD_GRUPO_CIA = '001'
              AND       FEC_DIA_VTA BETWEEN (TRUNC(SYSDATE) - 30)
                                 AND     (TRUNC(SYSDATE) - 1)
              GROUP BY  COD_GRUPO_CIA,
                        COD_PROD,
                        COD_LOCAL
            ) VTA_30_D,
            (
              SELECT    COD_GRUPO_CIA,
                        COD_PROD,
                        COD_LOCAL,
                        TRUNC((TRUNC(SYSDATE-1)-FEC_DIA_VTA)/30) PER_VTA,
                        SUM(CANT_UNID_VTA) UNID_VTA,
                        SUM(MON_TOT_VTA) MON_VTA
              FROM      VTA_RES_VTA_PROD_LOCAL
              WHERE     COD_GRUPO_CIA = '001'
              AND       FEC_DIA_VTA BETWEEN (TRUNC(SYSDATE) - 90)
                                    AND     (TRUNC(SYSDATE) - 1)
              GROUP BY  COD_GRUPO_CIA,
                        COD_PROD,
                        COD_LOCAL,
                        TRUNC((TRUNC(SYSDATE-1)-FEC_DIA_VTA)/30)
              UNION
              SELECT    COD_GRUPO_CIA,
                        COD_PROD,
                        COD_LOCAL,
                        -1,
                        (STK_FISICO / VAL_FRAC_LOCAL) UNID_VTA,
                        0  MON_VTA
              FROM      LGT_PROD_LOCAL
            ) VTA_LOC,
           --LGT_DIAS_STK_PROD STOCK
           -- DUBILLUZ 18.09.2013
           LGT_DIAS_STK_MAE_PROD STOCK
    WHERE  P.EST_PROD                 = 'A'
    AND    LOC.TIP_LOCAL              = 'V'
    AND    P.COD_LAB                  = LAB.COD_LAB
    AND    PL.STK_FISICO              > 0
    AND    PL.COD_GRUPO_CIA           = LOC.COD_GRUPO_CIA
    AND    PL.COD_LOCAL               = LOC.COD_LOCAL
    AND    PL.COD_PROD                = P.COD_PROD
    AND    ULT_VTA.COD_GRUPO_CIA(+)   = PL.COD_GRUPO_CIA
    AND    ULT_VTA.COD_LOCAL(+)       = PL.COD_LOCAL
    AND    ULT_VTA.COD_PROD(+)        = PL.COD_PROD
    AND    VTA_30_D.COD_GRUPO_CIA(+)  = PL.COD_GRUPO_CIA
    AND    VTA_30_D.COD_LOCAL(+)      = PL.COD_LOCAL
    AND    VTA_30_D.COD_PROD(+)       = PL.COD_PROD
    AND    STOCK.COD_GRUPO_CIA(+)     = PL.COD_GRUPO_CIA
    AND    STOCK.COD_LOCAL(+)         = PL.COD_LOCAL
    AND    STOCK.COD_PROD(+)          = PL.COD_PROD
    AND    VTA_LOC.COD_GRUPO_CIA(+)   = PL.COD_GRUPO_CIA
    AND    VTA_LOC.COD_LOCAL(+)       = PL.COD_LOCAL
    AND    VTA_LOC.COD_PROD(+)        = PL.COD_PROD
)
-- WHERE RESULTADO = '< 30'
WHERE NUM_RESULTADO >=
      (
        SELECT
               CASE TO_NUMBER(LLAVE_TAB_GRAL)
                       WHEN 0 THEN 0
                       WHEN 30 THEN 1
                       WHEN 60 THEN 2
                       ELSE 3
               END
        FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL  = '109'  AND COD_APL = 'PTO_VENTA' AND COD_TAB_GRAL = 'NUM_DIAS_SIN_VENTA'
      )
;

