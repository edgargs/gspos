CREATE OR REPLACE FORCE VIEW PTOVENTA.V_AUX_DET_TOMA_INV AS
SELECT
                  (L.COD_LOCAL ||'-'|| L.DESC_CORTA_LOCAL) LOCAL,
                  TRUNC(C.FEC_CREA_TOMA_INV) FECHA,
                  C.SEC_TOMA_INV,
                  TILP.COD_PROD  CODIGO,
           		    NVL(P.DESC_PROD,' ') PRODUCTO ,
          	      P.DESC_UNID_PRESENT  UNIDAD,
                  LAB.NOM_LAB,
                  TILP.VAL_PREC_PROM COST_PROM,
                  STK_ANTERIOR STK_ANT,
                  NVL(TILP.CANT_TOMA_INV_PROD,0) STK_FIN,
                  TILP.VAL_FRAC,
                  (NVL(TILP.CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) DIF,
                  (CASE WHEN (NVL(TILP.CANT_TOMA_INV_PROD,0) + NVL(AJUSTES.CANT_MOV_PROD,0)) < STK_ANTERIOR THEN ((((NVL(TILP.CANT_TOMA_INV_PROD,0) + NVL(AJUSTES.CANT_MOV_PROD,0))-STK_ANTERIOR) * TILP.VAL_PREC_PROM) / TILP.VAL_FRAC) ELSE 0 END) FALTANTE,
                  (CASE WHEN (NVL(TILP.CANT_TOMA_INV_PROD,0) + NVL(AJUSTES.CANT_MOV_PROD,0)) > STK_ANTERIOR THEN ((((NVL(TILP.CANT_TOMA_INV_PROD,0) + NVL(AJUSTES.CANT_MOV_PROD,0))-STK_ANTERIOR) * TILP.VAL_PREC_PROM) / TILP.VAL_FRAC) ELSE 0 END) SOBRANTE,
                  (STK_ANTERIOR * TILP.VAL_PREC_PROM / TILP.VAL_FRAC) VAL_ANT,
                  ( (NVL(TILP.CANT_TOMA_INV_PROD,0) + NVL(AJUSTES.CANT_MOV_PROD,0)) * TILP.VAL_PREC_PROM / TILP.VAL_FRAC) VAL_FIN,
                  AJUSTES.CANT_MOV_PROD C1,
                  AJUSTES.VAL_FRACC_PROD C2,
                  (NVL(TILP.CANT_TOMA_INV_PROD,0) + NVL(AJUSTES.CANT_MOV_PROD,0)) - STK_ANTERIOR C3,
                  C.IND_CICLICO C4,
                  (SELECT VAL_PREC_VTA * VAL_FRAC_LOCAL FROM LGT_PROD_LOCAL PL WHERE PL.COD_GRUPO_CIA = TILP.COD_GRUPO_CIA AND PL.COD_LOCAL = TILP.COD_LOCAL AND PL.COD_PROD = TILP.COD_PROD) C5,
                  C.COD_LOCAL C6,
                  SYSDATE C7,
                  ' ' C8,
                  ' ' C9,
                  ' ' C10,
                  GEY.DESC_GRUPO_REP_EDMUNDO
          FROM    PBL_LOCAL L,
                  LGT_TOMA_INV_CAB C,
                  LGT_TOMA_INV_LAB_PROD TILP,
          		    LGT_PROD P,
                  LGT_GRUPO_REP_EDMUNDO GEY,
                  LGT_LAB LAB,
                  (
                      SELECT COD_GRUPO_CIA, COD_LOCAL, COD_PROD, CANT_MOV_PROD, VAL_FRACC_PROD,
                             FEC_KARDEX FECHA
                      FROM
                           (
                              SELECT
                                     K.COD_GRUPO_CIA,
                                     K.COD_LOCAL,
                                     K.COD_PROD,
                                     K.VAL_FRACC_PROD,
                                     MIN(K.FEC_KARDEX) FEC_KARDEX,
                                     SUM(K.CANT_MOV_PROD) CANT_MOV_PROD
                              FROM LGT_KARDEX K,
                                   LGT_TOMA_INV_CAB TIC
                              WHERE K.COD_GRUPO_CIA = '001'
                                AND K.COD_MOT_KARDEX IN ('508', '515')
                                AND K.FEC_KARDEX > ADD_MONTHS(TRUNC(SYSDATE,'MM'),-1)
                                AND TIC.COD_GRUPO_CIA = '001'
                                AND TIC.COD_LOCAL = K.COD_LOCAL
                                AND TIC.FEC_CREA_TOMA_INV < K.FEC_KARDEX
                                AND TIC.SEC_TOMA_INV = (SELECT MAX(SEC_TOMA_INV)
                                                       FROM LGT_TOMA_INV_CAB C1
                                                       WHERE C1.COD_GRUPO_CIA = TIC.COD_GRUPO_CIA
                                                         AND C1.COD_LOCAL = TIC.COD_LOCAL
                                                         AND C1.TIP_TOMA_INV = 'T'
                                                         AND C1.EST_TOMA_INV ='C'
                                                       )
                              GROUP BY
                                     K.COD_GRUPO_CIA,
                                     K.COD_LOCAL,
                                     K.COD_PROD,
                                     K.VAL_FRACC_PROD
                          )
                  ) AJUSTES
          WHERE
                  1 = 1 AND
                  C.EST_TOMA_INV = 'C' AND
                  L.COD_GRUPO_CIA = TILP.COD_GRUPO_CIA AND
                  L.COD_LOCAL = TILP.COD_LOCAL AND
                  C.COD_GRUPO_CIA = TILP.COD_GRUPO_CIA AND
                  C.COD_LOCAL = TILP.COD_LOCAL AND
                  C.SEC_TOMA_INV = TILP.SEC_TOMA_INV AND
                  C.FEC_CREA_TOMA_INV BETWEEN TRUNC(SYSDATE-5) AND SYSDATE AND
                  TILP.CANT_TOMA_INV_PROD IS NOT NULL        AND
               	  P.COD_GRUPO_CIA = TILP.COD_GRUPO_CIA AND
                  P.COD_PROD = TILP.COD_PROD AND
                  P.COD_LAB = LAB.COD_LAB
                  AND AJUSTES.COD_GRUPO_CIA(+) = TILP.COD_GRUPO_CIA
                  AND AJUSTES.COD_LOCAL(+) = TILP.COD_LOCAL
                  AND AJUSTES.COD_PROD(+) = TILP.COD_PROD
                  AND NVL(AJUSTES.FECHA(+),TILP.FEC_CREA_TOMA_INV_LAB_PROD) >= TILP.FEC_CREA_TOMA_INV_LAB_PROD
                  AND P.COD_GRUPO_REP_EDMUNDO = GEY.COD_GRUPO_REP_EDMUNDO(+);

