CREATE OR REPLACE PACKAGE PTOVENTA."REP_3_CADENAS_PROMO" AS

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

CREATE OR REPLACE PACKAGE BODY PTOVENTA."REP_3_CADENAS_PROMO" AS

 PROCEDURE INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2)
  AS
    v_dFecha DATE;
    CURSOR curCambio IS
    SELECT C.COD_PROD_ANT,C.COD_PROD_NUE
    FROM LGT_CAMBIO_PROD C, VTA_RES_VTA_ACUM_LOCAL A
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND EXISTS (
                      SELECT 1
                      FROM   AUX_PROD_PROM_ANALISIS AU
                      WHERE  AU.COD_PROD = A.COD_PROD
                     )
          AND C.EST_CAMBIO_PROD = 'A'
          AND A.COD_LOCAL = cCodLocal_in
          AND A.FEC_DIA_VTA = v_dFecha
          AND C.COD_GRUPO_CIA = A.COD_GRUPO_CIA
          AND C.COD_PROD_ANT = A.COD_PROD
    ORDER BY C.FEC_INI_CAMBIO_PROD;
    --fec_ini
    rowCambio curCambio%ROWTYPE;

    CURSOR curHistorico IS
    SELECT C.COD_PROD_ANT, C.COD_PROD_NUE, A.FEC_DIA_VTA
      FROM VTA_RES_VTA_ACUM_LOCAL A, LGT_CAMBIO_PROD C
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND EXISTS (
                  SELECT 1
                  FROM   AUX_PROD_PROM_ANALISIS AU
                  WHERE  AU.COD_PROD = A.COD_PROD
                 )
       AND C.EST_CAMBIO_PROD = 'A'
       AND A.CANT_UNID_VTA <> 0
       AND A.FEC_DIA_VTA BETWEEN ADD_MONTHS(TRUNC(v_dFecha,'MM'),-4) AND TRUNC(v_dFecha,'MM')-1/24/60/60
       AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
       AND A.COD_PROD = C.COD_PROD_ANT
    ORDER BY C.FEC_INI_CAMBIO_PROD;
    rowHistorico curHistorico%ROWTYPE;

  BEGIN
    v_dFecha := TO_DATE(vFecha_in,'dd/MM/yyyy');

    --VTA_RES_VTA_PROD_LOCAL
    DELETE VTA_RES_VTA_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND   FEC_DIA_VTA = v_dFecha
    AND   COD_LOCAL = cCodLocal_in
    AND   EXISTS (
              SELECT 1
              FROM   AUX_PROD_PROM_ANALISIS AU
              WHERE  AU.COD_PROD = COD_PROD
             );
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_VTA_PROD_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND   EXISTS (
                    SELECT 1
                    FROM   AUX_PROD_PROM_ANALISIS AU
                    WHERE  AU.COD_PROD = D.COD_PROD
                   )
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0 AND SUM(VAL_PREC_TOTAL) != 0;

    --VTA_RES_VTA_REP_LOCAL
    DELETE VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND   FEC_DIA_VTA = v_dFecha
    AND   COD_LOCAL = cCodLocal_in
    AND   EXISTS (
          SELECT 1
          FROM   AUX_PROD_PROM_ANALISIS AU
          WHERE  AU.COD_PROD = COD_PROD
         );
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_VTA_REP_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND C.TIP_PED_VTA IN ('01','02')
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND   EXISTS (
                    SELECT 1
                    FROM   AUX_PROD_PROM_ANALISIS AU
                    WHERE  AU.COD_PROD = D.COD_PROD
                   )
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0;

    --VTA_RES_VTA_ACUM_LOCAL
    DELETE VTA_RES_VTA_ACUM_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND   FEC_DIA_VTA = v_dFecha
    AND   COD_LOCAL = cCodLocal_in
    AND   EXISTS (
          SELECT 1
          FROM   AUX_PROD_PROM_ANALISIS AU
          WHERE  AU.COD_PROD = COD_PROD
          );
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_VTA_ACUM_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
    CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND C.TIP_PED_VTA IN ('01','02')
          AND D.IND_CALCULO_MAX_MIN = 'S'
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND   EXISTS (
                    SELECT 1
                    FROM   AUX_PROD_PROM_ANALISIS AU
                    WHERE  AU.COD_PROD = D.COD_PROD
                   )
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0;
    ---JQUISPE 23/03/2012 , SE PIDIO QUE NO TOME EN CUENTA LOS PRODUCTOS QUE ESTEN EN CAMPAÑA IMBATIBLES MARZO
/*    DELETE FROM VTA_RES_VTA_PROD_LOCAL V WHERE V.COD_PROD IN (SELECT T.COD_PROD FROM TMP_PROD_EXCLUIR_VTA T)   AND TRUNC(V.FEC_DIA_VTA)  BETWEEN TO_DATE('08/03/2012','DD/MM/YYYY') AND TO_DATE('22/03/2012','DD/MM/YYYY');
    DELETE FROM VTA_RES_VTA_REP_LOCAL  V1 WHERE V1.COD_PROD IN (SELECT T.COD_PROD FROM TMP_PROD_EXCLUIR_VTA T) AND TRUNC(V1.FEC_DIA_VTA) BETWEEN TO_DATE('08/03/2012','DD/MM/YYYY') AND TO_DATE('22/03/2012','DD/MM/YYYY');
    DELETE FROM VTA_RES_VTA_ACUM_LOCAL V2 WHERE V2.COD_PROD IN (SELECT T.COD_PROD FROM TMP_PROD_EXCLUIR_VTA T) AND TRUNC(V2.FEC_DIA_VTA) BETWEEN TO_DATE('08/03/2012','DD/MM/YYYY') AND TO_DATE('22/03/2012','DD/MM/YYYY');*/
    DELETE FROM VTA_RES_VTA_PROD_LOCAL V
    where  exists (
                   SELECT 1
                   FROM   TMP_PROD_EXCLUIR_VTA T
                   where  t.cod_prod = v.cod_prod
                   and    v.fec_dia_vta between trunc(t.fech_inicio) and trunc(t.fech_fin) + 1 -1/24/60/60
                   )
    AND    EXISTS (
                   SELECT 1
                   FROM   AUX_PROD_PROM_ANALISIS AU
                   WHERE  AU.COD_PROD = COD_PROD
                  );
    ----------------------------------------------------------
    DELETE FROM VTA_RES_VTA_REP_LOCAL V1
    where  exists (
                   SELECT 1
                   FROM   TMP_PROD_EXCLUIR_VTA T
                   where  t.cod_prod = v1.cod_prod
                   and    v1.fec_dia_vta between trunc(t.fech_inicio) and trunc(t.fech_fin) + 1 -1/24/60/60
                   )
    AND   EXISTS (
                  SELECT 1
                  FROM   AUX_PROD_PROM_ANALISIS AU
                  WHERE  AU.COD_PROD = COD_PROD
                 );
    ------------------------------------------------------------
    DELETE FROM VTA_RES_VTA_ACUM_LOCAL V2
    where  exists (
                   SELECT 1
                   FROM   TMP_PROD_EXCLUIR_VTA T
                   where  t.cod_prod = v2.cod_prod
                   and    v2.fec_dia_vta between trunc(t.fech_inicio) and trunc(t.fech_fin) + 1 -1/24/60/60
                   )
    AND   EXISTS (
                  SELECT 1
                  FROM   AUX_PROD_PROM_ANALISIS AU
                  WHERE  AU.COD_PROD = COD_PROD
                 );
    -------------------------------------------------------------
    --MUEVE HISTORICO
    FOR rowHistorico IN curHistorico
    LOOP
      REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in, cCodLocal_in,
      rowHistorico.COD_PROD_ANT, rowHistorico.COD_PROD_NUE, rowHistorico.FEC_DIA_VTA);
    END LOOP;
    --MUEVE VENTAS DEL DIA
    FOR rowCambio IN curCambio
    LOOP
      REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in, cCodLocal_in,
      rowCambio.COD_PROD_ANT, rowCambio.COD_PROD_NUE, v_dFecha);
    END LOOP;

    COMMIT;
  END;
  ---------------------------------------------------------------------------------------------
PROCEDURE REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProdAnt_in IN CHAR, cCodProdNue_in IN CHAR, dFecha_in IN DATE)
  AS
    v_nCant VTA_RES_VTA_REP_LOCAL.CANT_UNID_VTA%TYPE;
    v_nMonTot VTA_RES_VTA_REP_LOCAL.MON_TOT_VTA%TYPE;

  BEGIN
      SELECT CANT_UNID_VTA,MON_TOT_VTA
        INTO v_nCant,v_nMonTot
      FROM VTA_RES_VTA_ACUM_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProdAnt_in
            AND FEC_DIA_VTA = dFecha_in FOR UPDATE;
      BEGIN
        INSERT INTO VTA_RES_VTA_ACUM_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
        CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
        VALUES(cCodGrupoCia_in,cCodLocal_in,cCodProdNue_in,dFecha_in,
        v_nCant,v_nMonTot,SYSDATE);
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE VTA_RES_VTA_ACUM_LOCAL
          SET CANT_UNID_VTA = CANT_UNID_VTA + v_nCant,
              MON_TOT_VTA = MON_TOT_VTA + v_nMonTot,
              FEC_CREA_VTA_PROD_LOCAL = SYSDATE
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND COD_PROD = cCodProdNue_in
                AND FEC_DIA_VTA = dFecha_in;
      END;

      UPDATE VTA_RES_VTA_ACUM_LOCAL
      SET CANT_UNID_VTA = 0,
          MON_TOT_VTA = 0,
          FEC_CREA_VTA_PROD_LOCAL = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProdAnt_in
            AND FEC_DIA_VTA = dFecha_in;
  END;
/*****************************************************************************/

    PROCEDURE P_CREA_RES_VTA_PROD_LOCAL(cCodGrupoCia_in IN VARCHAR2,
                                      cCodLocal_in    IN VARCHAR2) IS

  BEGIN
     -- este proceso se copio del REP ACTUAL
     -- debido que el proceso de cargar VENTAS
     -- mueve las ventas de
        -- x Cambio de Codigo
        -- x Exclucion de Venta promocion.
        -- x exclucion de Prod.
     INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
     COMMIT;
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_PROM_RES_VTA_PROD_LOCAL DROP STORAGE';

    INSERT INTO MF_PROM_RES_VTA_PROD_LOCAL NOLOGGING
      (COD_LOCAL_MF,
       COD_PROD_MF,
       FEC_DIA_VTA,
       CANT_UNID_VTA,
       MON_TOT_VTA,
       COS_TOT_VTA,
       CANT_UNID_VTA_SIN_PROM,
       CANT_UNID_VTA_TOT,
       MES
       )
      SELECT C.COD_LOCAL,
             COD_PROD,
             TRUNC(C.FEC_DIA_VTA),
             0,-- XQ VA CERO
             --SUM(CANT_UNID_VTA),
             SUM(MON_TOT_VTA),
             0,
             SUM(CANT_UNID_VTA),
             SUM(CANT_UNID_VTA),
             TO_NUMBER(TO_CHAR(C.FEC_DIA_VTA,'YYYYMM'))
        FROM VTA_RES_VTA_ACUM_LOCAL C
        where FEC_DIA_VTA between Add_months(trunc(sysdate), -4) and TRUNC(SYSDATE,'MM')-1/24/60/60
        AND   EXISTS (
              SELECT 1
              FROM   AUX_PROD_PROM_ANALISIS AU
              WHERE  AU.COD_PROD = C.COD_PROD
             )
        GROUP BY C.COD_LOCAL,
             COD_PROD,
             TRUNC(C.FEC_DIA_VTA),
             TO_NUMBER(TO_CHAR(C.FEC_DIA_VTA,'YYYYMM'));

    COMMIT;
  END;
  /* *********************************************************************************** */
  PROCEDURE P_PROCESO_CALCULA_PICOS(cCodLocal_in    IN VARCHAR2) IS

       D_FECHA_PROCESO DATE := NULL; --
       D_DIAS_TOPE_MES INTEGER := 5;

         -- PARA ACTUALIZAR DATOS DEL MES CERRADO
       CURSOR CUR1 (C_COD_LOCAL_MF_IN IN CHAR, C_FECHA_IN IN DATE) IS
            SELECT /*+ INDEX (MF_PROM_RES_VTA_PROD_LOCAL IX_PROM_RES_VTA_PROD_LOCAL_02) */
                  COD_LOCAL_MF,
                  COD_PROD_MF,
                  FEC_DIA_VTA,
                  MES
            FROM MF_PROM_RES_VTA_PROD_LOCAL
            WHERE MES = TO_NUMBER(TO_CHAR(C_FECHA_IN - D_DIAS_TOPE_MES,'YYYYMM'))
              AND COD_LOCAL_MF = C_COD_LOCAL_MF_IN
            ORDER BY
                  COD_LOCAL_MF,
                  COD_PROD_MF,
                  FEC_DIA_VTA;


         -- PARA ACTUALIZAR DATOS DEL MES VIGENTE
       CURSOR CUR2 (C_COD_LOCAL_MF_IN IN CHAR, C_FECHA_IN IN DATE) IS
            SELECT /*+ INDEX (MF_PROM_RES_VTA_PROD_LOCAL IX_PROM_RES_VTA_PROD_LOCAL_02) */
                  COD_LOCAL_MF,
                  COD_PROD_MF,
                  FEC_DIA_VTA
            FROM MF_PROM_RES_VTA_PROD_LOCAL
            WHERE MES = TO_NUMBER(TO_CHAR(C_FECHA_IN,'YYYYMM'))
              AND COD_LOCAL_MF = C_COD_LOCAL_MF_IN
            ORDER BY
                  COD_LOCAL_MF,
                  COD_PROD_MF,
                  FEC_DIA_VTA;

         -- OBTIENE LOCALES POR ACTUALIZAR
       CURSOR CUR3 (C_FECHA_IN IN DATE) IS
            SELECT DISTINCT COD_LOCAL_MF
            FROM MF_PROM_RES_VTA_PROD_LOCAL
            WHERE MES BETWEEN TO_NUMBER(TO_CHAR(C_FECHA_IN - D_DIAS_TOPE_MES,'YYYYMM')) AND TO_NUMBER(TO_CHAR(C_FECHA_IN,'YYYYMM'))
              AND COD_LOCAL_MF = cCodLocal_in
            ORDER BY COD_LOCAL_MF;

       CURSOR CUR4 IS
            select mes
            from  (
                   select ADD_MONTHS(TRUNC(sysdate,'MM'),-rownum) MEs
                   from   lgt_prod
                   where  rownum < 5
                  )
            ORDER BY 1;


         N_REEMPLAZO            NUMBER;
         N_PROM_DIARIO_MES      NUMBER;
  BEGIN

      EXECUTE IMMEDIATE 'ALTER INDEX PK_MF_PROM_RES_VTA_PROD_LOCAL REBUILD';
      EXECUTE IMMEDIATE 'ALTER INDEX IX_PROM_RES_VTA_PROD_LOCAL_01 REBUILD';
      EXECUTE IMMEDIATE 'ALTER INDEX IX_PROM_RES_VTA_PROD_LOCAL_02 REBUILD';
      EXECUTE IMMEDIATE 'ALTER INDEX IX_PROM_RES_VTA_PROD_LOCAL_03 REBUILD';

       FOR LOC4 IN CUR4
       LOOP

           D_FECHA_PROCESO := LOC4.MES;

             FOR LOC3 IN CUR3 (D_FECHA_PROCESO)
             LOOP
                       -- ACTUALIZA DATA DEL MES ANTERIOR
                       IF TO_NUMBER(TO_CHAR(D_FECHA_PROCESO,'DD')) <= D_DIAS_TOPE_MES THEN
                         Dbms_Output.put_line('entro..'||D_FECHA_PROCESO);
                           FOR LOC1 IN CUR1 (LOC3.COD_LOCAL_MF, D_FECHA_PROCESO)
                           LOOP
                               BEGIN

                                        SELECT /*+ INDEX (MF_PROM_RES_VTA_PROD_LOCAL IX_PROM_RES_VTA_PROD_LOCAL_03) */
                                              NVL(CASE WHEN COUNT(*) = 0 THEN 0 ELSE SUM(CASE WHEN CANT_UNID_VTA_SIN_PROM <= 0 THEN 0 ELSE CANT_UNID_VTA_SIN_PROM END) / COUNT(*) END,0) "PROM_DIARIO_MES"
                                        INTO N_PROM_DIARIO_MES
                                        FROM MF_PROM_RES_VTA_PROD_LOCAL R
                                        WHERE 1 = 1
                                          AND R.MES = LOC1.MES
                                          AND R.COD_LOCAL_MF = LOC1.COD_LOCAL_MF
                                          AND R.COD_PROD_MF = LOC1.COD_PROD_MF;
                                    Dbms_Output.put_line('N_PROM_DIARIO_MES..'||N_PROM_DIARIO_MES);
                                    UPDATE MF_PROM_RES_VTA_PROD_LOCAL T
                                    SET FEC_PROC_PICKS = SYSDATE,
                                        CANT_UNID_VTA =
                                              CASE
                                                   WHEN CANT_UNID_VTA_SIN_PROM < 0                   THEN CANT_UNID_VTA_SIN_PROM
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) > 20             THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 1.5 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) BETWEEN 6 AND 20 THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 2.0 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 5              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 2.5 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 4              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 3.0 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 3              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 3.5 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 2              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 4.0 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) = 1              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 5.0 THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                                   WHEN TRUNC(N_PROM_DIARIO_MES) >= 0             THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > 5.0                        THEN N_PROM_DIARIO_MES ELSE CANT_UNID_VTA_SIN_PROM END
                                              END
                                    WHERE 1 = 1
                                      AND T.MES = LOC1.MES
                                      AND T.COD_LOCAL_MF = LOC1.COD_LOCAL_MF
                                      AND T.COD_PROD_MF = LOC1.COD_PROD_MF
                                      AND T.FEC_DIA_VTA = LOC1.FEC_DIA_VTA;

                                 COMMIT;

                                 -- DBMS_OUTPUT.put_line(LOC1.COD_LOCAL_MF ||';'|| LOC1.COD_PROD_MF ||';'|| TO_CHAR(LOC1.MES_INI,'YYYY-MM-DD') ||':'|| SQLERRM);

                               EXCEPTION
                                        WHEN OTHERS THEN
                                             DBMS_OUTPUT.put_line('ERR=' || LOC1.COD_LOCAL_MF ||';'|| LOC1.COD_PROD_MF ||';'|| LOC1.MES ||':'|| SQLERRM);
                               END;

                           END LOOP;
                       END IF;

                       -- ESTOY PROCESANDO UNA FECHA DEL MES ACTUAL
                       IF TRUNC(D_FECHA_PROCESO,'MM') = TRUNC(SYSDATE,'MM') THEN

                               -- ACTUALIZA DATA DEL MES ACTUAL
                               FOR LOC2 IN CUR2 (LOC3.COD_LOCAL_MF, D_FECHA_PROCESO)
                               LOOP
                                   BEGIN
                                        N_REEMPLAZO := 0;

                                        SELECT
                                              NVL(CASE WHEN SUM(CASE WHEN CANT_UNID_VTA_SIN_PROM <= 0 THEN 0 ELSE 1 END) = 0 THEN 0 ELSE SUM(CANT_UNID_VTA_SIN_PROM) / SUM(CASE WHEN CANT_UNID_VTA_SIN_PROM <= 0 THEN 0 ELSE 1 END) END,0) "CANT_UNID_VTA_SIN_PROM_NVO"
                                        INTO N_REEMPLAZO
                                        FROM
                                            (
                                              SELECT
                                                    COD_LOCAL_MF,
                                                    COD_PROD_MF,
                                                    FEC_DIA_VTA,
                                                    CANT_UNID_VTA_SIN_PROM,
                                                    RANK() OVER(PARTITION BY COD_LOCAL_MF, COD_PROD_MF ORDER BY FEC_DIA_VTA DESC) "PUESTO"
                                              FROM MF_PROM_RES_VTA_PROD_LOCAL R
                                              WHERE 1 = 1
                                                AND R.COD_LOCAL_MF = LOC2.COD_LOCAL_MF
                                                AND R.COD_PROD_MF = LOC2.COD_PROD_MF
                                                AND R.FEC_DIA_VTA < LOC2.FEC_DIA_VTA
                                            )
                                        WHERE PUESTO <= 7;

                                        SELECT /*+ INDEX (MF_PROM_RES_VTA_PROD_LOCAL IX_PROM_RES_VTA_PROD_LOCAL_03) */
                                              NVL(CASE WHEN COUNT(*) = 0 THEN 0 ELSE SUM(CASE WHEN CANT_UNID_VTA_SIN_PROM <= 0 THEN 0 ELSE CANT_UNID_VTA_SIN_PROM END) / COUNT(*) END,0) "PROM_DIARIO_MES"
                                        INTO N_PROM_DIARIO_MES
                                        FROM MF_PROM_RES_VTA_PROD_LOCAL R
                                        WHERE 1 = 1
                                          AND R.MES = TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMM'))
                                          AND R.COD_LOCAL_MF = LOC2.COD_LOCAL_MF
                                          AND R.COD_PROD_MF = LOC2.COD_PROD_MF;

                                        -- DBMS_OUTPUT.put_line(LOC2.COD_LOCAL_MF ||';'|| LOC2.COD_PROD_MF ||';'|| LOC2.FEC_DIA_VTA ||';'|| N_REEMPLAZO);

                                        UPDATE MF_PROM_RES_VTA_PROD_LOCAL T
                                        SET FEC_PROC_PICKS = SYSDATE,
                                            CANT_UNID_VTA =
                                                CASE
                                                     WHEN CANT_UNID_VTA_SIN_PROM < 0                            THEN CANT_UNID_VTA_SIN_PROM
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) > 20             THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 1.5 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) BETWEEN 6 AND 20 THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 2.0 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 5              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 2.5 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 4              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 3.0 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 3              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 3.5 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 2              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 4.0 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) = 1              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > N_PROM_DIARIO_MES * 5.0 THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     WHEN TRUNC(N_PROM_DIARIO_MES) > 0              THEN CASE WHEN CANT_UNID_VTA_SIN_PROM > 5.0                     THEN N_REEMPLAZO ELSE CANT_UNID_VTA_SIN_PROM END
                                                     ELSE CANT_UNID_VTA_SIN_PROM
                                                END
                                        WHERE T.COD_LOCAL_MF = LOC2.COD_LOCAL_MF
                                          AND T.COD_PROD_MF = LOC2.COD_PROD_MF
                                          AND T.FEC_DIA_VTA = LOC2.FEC_DIA_VTA;


                                   END;
                               END LOOP;

                               COMMIT;

                         END IF;

             END LOOP;

       END LOOP;


  END;
  /* *********************************************************************************** */
  PROCEDURE P_STK_LOCAL_AND_TRANSITO(cCodLocal_in IN VARCHAR2) IS
  BEGIN
    null;
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_PROM_STK_PROD_LOCAL DROP STORAGE';

    INSERT INTO MF_PROM_STK_PROD_LOCAL NOLOGGING
      (COD_PROD_MF, COD_LOCAL_MF, STK)
      SELECT S.COD_PROD COD_PROD_MF, S.COD_LOCAL COD_LOCAL_MF, S.STK_FISICO / S.VAL_FRAC_LOCAL
        FROM LGT_PROD_LOCAL S
       WHERE COD_LOCAL = cCodLocal_in
       AND   EXISTS (
              SELECT 1
              FROM   AUX_PROD_PROM_ANALISIS AU
              WHERE  AU.COD_PROD = S.COD_PROD
             );

    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_PROM_STK_TRAN_PROD_LOCAL DROP STORAGE';

    INSERT INTO MF_PROM_STK_TRAN_PROD_LOCAL NOLOGGING
      (COD_PROD_MF, COD_LOCAL_MF, STK)
      SELECT S.COD_PROD COD_PROD_MF,
             S.COD_LOCAL COD_LOCAL_MF,
             F_GET_TRANS_PROD('001', S.COD_LOCAL, S.COD_PROD)
        FROM LGT_PROD_LOCAL S
       WHERE COD_LOCAL = cCodLocal_in
       AND   EXISTS (
              SELECT 1
              FROM   AUX_PROD_PROM_ANALISIS AU
              WHERE  AU.COD_PROD = S.COD_PROD
             );

    COMMIT;

  END;
  /* *********************************************************************************** */
  PROCEDURE P_CALULA_VTA_PROM(cCodLocal_in in char) is
            nCant number;
  begin

  EXECUTE IMMEDIATE 'TRUNCATE TABLE MAE_PROM_X_LOCAL DROP STORAGE';
  EXECUTE IMMEDIATE 'TRUNCATE TABLE MAE_PROM_X_LOCAL_EQ DROP STORAGE';

  INSERT INTO MAE_PROM_X_LOCAL NOLOGGING
      (cod_grupo_cia, sec_prom_compra,COD_PROD_MF,cod_local,
       vta_estimada, ind_aprobado, vta_estimada_old,
       unid_vta_total, frecuencia, cant_unid_prom_diario,
       stk_x_local, stk_x_tra,
       usu_crea, fech_crea,
       usu_mod, fech_mod)
      select MP.COD_GRUPO_CIA,MP.SEC_PROM_COMPRA,VR.COD_PROD_MF,vr.cod_local_mf,
             0,'N',0,
             vr.CANT_UNID_VTA,vr.FRECUENCIA,
             round(NVL(CASE
                                             WHEN vr.FRECUENCIA != 0 THEN vr.CANT_UNID_VTA/vr.FRECUENCIA
                                             ELSE 0
                                           END,0),3) CANT_UNID_PROM_DIARIO,
             NVL(SL.STK,0),NVL(ST.STK,0),'DUBILLUZ',SYSDATE,NULL,NULL
      from  (
              SELECT COD_LOCAL_MF,
                     COD_PROD_MF,
                     SUM(CANT_UNID_VTA) CANT_UNID_VTA,
                     SUM(CASE
                           WHEN R.CANT_UNID_VTA < 0 THEN
                            0
                           ELSE
                            1
                         END) FRECUENCIA
                FROM MF_PROM_RES_VTA_PROD_LOCAL r
               WHERE R.COD_LOCAL_MF = cCodLocal_in
               GROUP BY COD_LOCAL_MF, COD_PROD_MF
            )  vr,
            MAE_PROM_X_COMPRA MP,
            MF_PROM_STK_PROD_LOCAL SL,
            MF_PROM_STK_TRAN_PROD_LOCAL ST
      WHERE VR.COD_PROD_MF = MP.COD_PROD_PROM
      AND   VR.COD_PROD_MF = SL.COD_PROD_MF(+)
      AND   VR.COD_LOCAL_MF = SL.COD_LOCAL_MF(+)
      AND   VR.COD_PROD_MF = ST.COD_PROD_MF(+)
      AND   VR.COD_LOCAL_MF= ST.COD_LOCAL_MF(+);

  INSERT INTO MAE_PROM_X_LOCAL_EQ NOLOGGING
          (cod_grupo_cia, sec_prom_compra,COD_PROD_MF, cod_local,
           vta_estimada, ind_aprobado, vta_estimada_old,
           unid_vta_total, frecuencia, cant_unid_prom_diario,
           stk_x_local, stk_x_tra,
           usu_crea, fech_crea,
           usu_mod, fech_mod)
          select MP.COD_GRUPO_CIA,MP.SEC_PROM_COMPRA,VR.COD_PROD_MF,vr.cod_local_mf,
                 0,'N',0,
                 vr.CANT_UNID_VTA,vr.FRECUENCIA,NVL(CASE
                                                 WHEN vr.FRECUENCIA != 0 THEN vr.CANT_UNID_VTA/vr.FRECUENCIA
                                                 ELSE 0
                                               END,0) CANT_UNID_PROM_DIARIO,
                 NVL(SL.STK,0),NVL(ST.STK,0),'DUBILLUZ',SYSDATE,NULL,NULL
          from  (
                  SELECT COD_LOCAL_MF,
                         COD_PROD_MF,
                         SUM(CANT_UNID_VTA) CANT_UNID_VTA,
                         SUM(CASE
                               WHEN R.CANT_UNID_VTA < 0 THEN
                                0
                               ELSE
                                1
                             END) FRECUENCIA
                    FROM MF_PROM_RES_VTA_PROD_LOCAL r
                   WHERE R.COD_LOCAL_MF = cCodLocal_in
                   GROUP BY COD_LOCAL_MF, COD_PROD_MF
                )  vr,
                MAE_PROM_X_COMPRA MP,
                MF_PROM_STK_PROD_LOCAL SL,
                MF_PROM_STK_TRAN_PROD_LOCAL ST
          WHERE VR.COD_PROD_MF = MP.COD_PROD_EQUI
          AND   VR.COD_PROD_MF = SL.COD_PROD_MF(+)
          AND   VR.COD_LOCAL_MF = SL.COD_LOCAL_MF(+)
          AND   VR.COD_PROD_MF = ST.COD_PROD_MF(+)
          AND   VR.COD_LOCAL_MF= ST.COD_LOCAL_MF(+);



  end;
  /* *********************************************************************************** */

  function f_get_trans_prod(cCodGrupoCia_in in char,
                            cCodLocal_in    in char,
                            cCodProd_in     in char) return number is
    g_cTipoNotaRecepcion LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '03';
    v_nTrans             number;
  begin
    SELECT NVL(SUM(CANT_ENVIADA_MATR), 0)
      INTO v_nTrans
      FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal_in
       AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
       AND C.COD_LOCAL = D.COD_LOCAL
       AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
       AND D.COD_PROD = cCodProd_in
       AND C.TIP_NOTA_ES = g_cTipoNotaRecepcion
       AND D.IND_PROD_AFEC = 'N';
    return v_nTrans;
  end;
  /* *********************************************************************************** */

  /* *********************************************************************************** */
  /* *********************************************************************************** */
  /* *********************************************************************************** */
  PROCEDURE P_OPERA_MF_PROMO(cCodGrupoCia_in in Char,cCodLocal_in in char) is
  begin
  ----------------------------------------------------------------
  DELETE AUX_PROD_PROM_ANALISIS;
  INSERT INTO AUX_PROD_PROM_ANALISIS
  (COD_PROD)
  --SELECT '640100' FROM DUAL;
  SELECT COD_PRODUCTO
  FROM  (
          SELECT A.COD_PROD_PROM AS "COD_PRODUCTO"
          FROM   MAE_PROM_X_COMPRA A
          WHERE  A.IND_PEND_ANALISIS = 'N'
          AND    A.FECH_ANALISIS IS NULL
          UNION
          SELECT A.COD_PROD_EQUI AS "COD_PRODUCTO"
          FROM   MAE_PROM_X_COMPRA A
          WHERE  A.IND_PEND_ANALISIS = 'N'
          AND    A.FECH_ANALISIS IS NULL
        );
       -- where cod_producto = '184926';
    commit;
  ----------------------------------------------------------------
    P_CREA_RES_VTA_PROD_LOCAL(cCodGrupoCia_in, cCodLocal_in);
    Dbms_Output.put_line('..P_CREA_RES_VTA_PROD_LOCAL');
    commit;
    P_PROCESO_CALCULA_PICOS(cCodLocal_in);
    Dbms_Output.put_line('..P_PROCESO_CALCULA_PICOS');
    commit;
    P_STK_LOCAL_AND_TRANSITO(cCodLocal_in);
    Dbms_Output.put_line('..P_STK_LOCAL_AND_TRANSITO');
    commit;
    P_CALULA_VTA_PROM(cCodLocal_in);
    Dbms_Output.put_line('..P_CALULA_VTA_PROM');
    COMMIT;

    UPDATE MAE_PROM_X_COMPRA A
    SET    A.IND_PEND_ANALISIS = 'S',
           A.FECH_ANALISIS = SYSDATE
    WHERE  A.IND_PEND_ANALISIS = 'N'
    AND    A.FECH_ANALISIS IS NULL;
    commit;

   -- BORRA TABLAS CALCULADAS X ESPACIO EN EL LOCAL
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_PROM_RES_VTA_PROD_LOCAL DROP STORAGE';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_PROM_STK_PROD_LOCAL DROP STORAGE';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE MF_PROM_STK_TRAN_PROD_LOCAL DROP STORAGE';

  end;
  /* *********************************************************************************** */

  /* ********************************************************************************** */

END;
/

