CREATE OR REPLACE FORCE VIEW PTOVENTA.V_MF_DET_SIM_REP_LOC AS
SELECT
                   GRUPO,
                   COD_PROD_MF,
                   COD_LOCAL_MF,
                   COSTO_PROMEDIO,
                   CANT_SEM,
                   T_S_IGV_SEM,
                   FREC_SEM,
                   CANT_MES,
                   T_S_IGV_MES,
                   FREC_MES,
                   STK_IDEAL,
                   STK_MINIMO,
                   STK_MAXIMO,
                   STK_IDEAL * NVL(COSTO_PROMEDIO,0) "VAL_STK_IDEAL",
                   STK_TRANSITO,
                   STK_LOC,
                   STK_LOC * NVL(COSTO_PROMEDIO,0) "VAL_STK_LOC",
                   STK_CD,
                   -- MIN_EXHIB_FIJO,
                   nvL(
                    (select vv.cant_exhib
                    from   VTA_MIN_EXHIB_PROD_LOCAL vv
                    where  vv.cod_local_sap = COD_LOCAL_MF
                    and    vv.cod_prod_sap = COD_PROD_MF),0
                   ) as "MIN_EXHIB_FIJO",
                   MIN_EXHIB_PROM,
                   CASE
                        WHEN STK_IDEAL <= STK_LOC                THEN 0
                        ELSE
                             CASE
                                  WHEN CANT_MES < 4 THEN ROUND(STK_IDEAL - STK_LOC)
                                  WHEN CANT_MES >= 4 THEN FLOOR(STK_IDEAL - STK_LOC)
                             END
                   END  "X_REPONER",
                   CASE
                        WHEN STK_LOC > STK_MINIMO                THEN 0
                        ELSE CEIL(STK_MAXIMO - STK_LOC)
                   END  "X_REPONER_NUEVO",
                   CASE
                        WHEN STK_IDEAL <= STK_LOC                THEN 0
                        ELSE STK_IDEAL - ROUND(STK_LOC)
                   END * NVL(COSTO_PROMEDIO,0) "VAL_X_REPONER_MF",
                   COD_PROD_SAP,
                   UNID_VTA_M_0,
                   UNID_VTA_M_1,
                   UNID_VTA_M_2,
                   UNID_VTA_M_3,
                   UNID_VTA_M_4,
                    null CANT_PED_ESP,
                    sysdate FEC_CREA,
                    null FEC_MOD,
                    null USU_MOD,
                    null COD_LOCAL,
                    PVM_AUTORIZADO,
                    OLD_CANT_UNID_VTA
             FROM (
                    SELECT
                          PL.COD_LOCAL                     "COD_LOCAL_MF",
                          PL.COD_PROD                      "COD_PROD_MF",
                          S.CANT_UNID_VTA "CANT_SEM", S.SEMANA, S.T_S_IGV "T_S_IGV_SEM", S.FREC       "FREC_SEM",
                          M.CANT_UNID_VTA "CANT_MES", M.MES_ID, M.T_S_IGV "T_S_IGV_MES", M.FRECUENCIA "FREC_MES",
                          CASE
                               WHEN NVL(M.CANT_UNID_VTA,0) <= 0                  THEN   '0. MENOR O IGUAL A CERO'
                               WHEN M.CANT_UNID_VTA = 1                  THEN   '1. ENTRE 1 Y 1'
                               WHEN M.CANT_UNID_VTA = 2                  THEN   '1. ENTRE 2 Y 2'
                               WHEN M.CANT_UNID_VTA = 3                  THEN   '1. ENTRE 3 Y 3'
                               WHEN M.CANT_UNID_VTA <= 10                 THEN   '2. ENTRE 4 Y 10'
                               WHEN M.CANT_UNID_VTA <= 35 AND S.FREC <= 3 THEN   '3. ENTRE 11 Y 35 - FREC ENTRE 1 Y 3'
                               WHEN M.CANT_UNID_VTA <= 35 AND S.FREC <= 7 THEN   '4. ENTRE 11 Y 35 - FREC ENTRE 4 Y 7'
                               WHEN M.CANT_UNID_VTA > 35  AND S.FREC <= 3 THEN   '5. MAS DE 36 - FREC ENTRE 1 Y 3'
                               WHEN M.CANT_UNID_VTA > 35  AND S.FREC <= 7 THEN   '5. MAS DE 36 - FREC ENTRE 4 Y 7'
                          END "GRUPO",
                          CASE
                               WHEN M.CANT_UNID_VTA <= 0                  THEN   0
                               WHEN M.CANT_UNID_VTA <= 1                  THEN   CEIL(M.CANT_UNID_VTA)
                               WHEN M.CANT_UNID_VTA <= 2                  THEN   1
                               WHEN M.CANT_UNID_VTA <= 3                  THEN   2
                               WHEN M.CANT_UNID_VTA <= 10                 THEN   CEIL(M.CANT_UNID_VTA / 2)
                               WHEN M.CANT_UNID_VTA <= 35 AND S.FREC <= 3 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/3) * 1.3)
                               WHEN M.CANT_UNID_VTA <= 35 AND S.FREC <= 7 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/3) * 1)
                               WHEN M.CANT_UNID_VTA > 35  AND S.FREC <= 3 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/4) * 8/7)
                               WHEN M.CANT_UNID_VTA > 35  AND S.FREC <= 7 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/4) * 1)
                          END "STK_IDEAL_VTA",
                          NVL(TRA.STK,0)                                                                "STK_TRANSITO",
                          NVL(TRA.STK,0) +  CASE WHEN NVL(SL.STK,0) < 0 THEN 0 ELSE NVL(SL.STK,0) END   "STK_LOC",
                          0   "STK_CD",
                          NVL(0,0) COSTO_PROMEDIO,
                          NVL(0,0)                               "MIN_EXHIB_FIJO",
                          NVL(0,0)                              "MIN_EXHIB_PROM",
                          CASE WHEN
                                   M.CANT_UNID_VTA <= 0 THEN   0
                               ELSE
                                      GREATEST
                                              (
                                                  CASE
                                                       WHEN M.CANT_UNID_VTA <= 0                  THEN   0
                                                       WHEN M.CANT_UNID_VTA <= 1                  THEN   CEIL(M.CANT_UNID_VTA)
                                                       WHEN M.CANT_UNID_VTA <= 2                  THEN   1
                                                       WHEN M.CANT_UNID_VTA <= 3                  THEN   2
                                                       WHEN M.CANT_UNID_VTA <= 10                 THEN   CEIL(M.CANT_UNID_VTA / 2)
                                                       WHEN M.CANT_UNID_VTA <= 35 AND S.FREC <= 3 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/3) * 1.3)
                                                       WHEN M.CANT_UNID_VTA <= 35 AND S.FREC <= 7 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/3) * 1)
                                                       WHEN M.CANT_UNID_VTA >  35 AND S.FREC <= 3 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/4) * 8/7)
                                                       WHEN M.CANT_UNID_VTA >  35 AND S.FREC <= 7 THEN   CEIL(LEAST(S.CANT_UNID_VTA, M.CANT_UNID_VTA/4) * 1)
                                                  END,
                                                  NVL(0,0) + NVL(0,0)
                                              )
                          END "STK_IDEAL",
                          nvl(rep_3_cadenas_mifarma.f_get_stk_calculado('MIN',
                                                       S.CANT_UNID_VTA,
                                                       M.CANT_UNID_VTA,
                                                       S.FREC,
                                                       pl.cod_local,pl.cod_prod),0) "STK_MINIMO",
                           nvl(rep_3_cadenas_mifarma.f_get_stk_calculado('MAX',
                                                       S.CANT_UNID_VTA,
                                                       M.CANT_UNID_VTA,
                                                       S.FREC,
                                                       pl.cod_local,pl.cod_prod),0) "STK_MAXIMO",
                          PL.COD_PROD      "COD_PROD_SAP",
                          NVL(VMES_PROD_LOC_MF.MES_0,0) UNID_VTA_M_0,
                         NVL(VMES_PROD_LOC_MF.MES_1,0) UNID_VTA_M_1,
                         NVL(VMES_PROD_LOC_MF.MES_2,0) UNID_VTA_M_2,
                         NVL(VMES_PROD_LOC_MF.MES_3,0) UNID_VTA_M_3,
                         NVL(VMES_PROD_LOC_MF.MES_4,0) UNID_VTA_M_4,
                         M.PVM_AUTORIZADO,
                    M.OLD_CANT_UNID_VTA
                    FROM
                        LGT_PROD_LOCAL PL,
                        MF_AUX_MEJOR_VTA_SEM_PROD_LOC S,
                        MF_AUX_MEJOR_VTA_MES_PROD_LOC M,
                        MF_VTA_STK_PROD_LOCAL SL,
                        MF_STK_TRAN_PROD_LOCAL TRA,
                        (
                          SELECT COD_LOCAL_MF,
                                 COD_PROD_MF,
                                 sum(case
                                     when a.MES between
                                          Add_months(trunc(sysdate, 'MM'), -4) and
                                          Add_months(trunc(sysdate, 'MM'), -3) - 1 / 24 / 60 / 60 then
                                      a.CANT_UNID_VTA
                                     else
                                      0
                                   end) MES_4,
                                 sum(case
                                     when a.MES between
                                          Add_months(trunc(sysdate, 'MM'), -3) and
                                          Add_months(trunc(sysdate, 'MM'), -2) - 1 / 24 / 60 / 60 then
                                      a.CANT_UNID_VTA
                                     else
                                      0
                                   end) MES_3,
                                 sum(case
                                     when a.MES between
                                          Add_months(trunc(sysdate, 'MM'), -2) and
                                          Add_months(trunc(sysdate, 'MM'), -1) - 1 / 24 / 60 / 60 then
                                      a.CANT_UNID_VTA
                                     else
                                      0
                                   end) MES_2,
                                 sum(case
                                     when a.MES between
                                          Add_months(trunc(sysdate, 'MM'), -1) and
                                          Add_months(trunc(sysdate, 'MM'), -0) - 1 / 24 / 60 / 60 then
                                      a.CANT_UNID_VTA
                                     else
                                      0
                                   end) MES_1,
                                 sum(case
                                     when a.MES between
                                          Add_months(trunc(sysdate, 'MM'), -0) and
                                          Add_months(trunc(sysdate, 'MM'), +1) - 1 / 24 / 60 / 60 then
                                      a.CANT_UNID_VTA
                                     else
                                      0
                                   end) MES_0
                          FROM   MF_VTA_RES_VTA_MES_PROD_LOCAL A
                          GROUP BY COD_LOCAL_MF,COD_PROD_MF
                        ) VMES_PROD_LOC_MF
                    WHERE 1 = 1
                      -- AND NVL(PL.IND_REPONER,'S') = 'S'
                      and pl.est_prod_loc='A'
                      AND PL.COD_LOCAL = S.COD_LOCAL_MF(+)
                      AND PL.COD_PROD = S.COD_PROD_MF(+)
                      AND PL.COD_LOCAL = M.COD_LOCAL_MF(+)
                      AND PL.COD_PROD = M.COD_PROD_MF(+)
                      AND PL.COD_PROD = SL.COD_PROD_MF(+)
                      AND PL.COD_LOCAL = SL.COD_LOCAL_MF(+)
                      AND PL.COD_LOCAL = TRA.COD_LOCAL_MF(+)
                      AND PL.COD_PROD       = TRA.COD_PROD_MF(+)
                      AND PL.COD_LOCAL = VMES_PROD_LOC_MF.COD_LOCAL_MF(+)
                      AND PL.COD_PROD = VMES_PROD_LOC_MF.COD_PROD_MF(+)
                 )
;

