--------------------------------------------------------
--  DDL for Package Body FARMA_VERIFICA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."FARMA_VERIFICA" AS

  PROCEDURE VER_PROD_KARDEX(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cIndImpProdOk_in IN CHAR DEFAULT NULL)
  AS
    CURSOR curProd IS
    SELECT DISTINCT COD_PROD
    FROM LGT_KARDEX
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          --AND COD_PROD = '138172'
    ORDER BY 1;
    v_rCurProd curProd%ROWTYPE;

    CURSOR curKardex(cCodProd_in IN CHAR) IS
    SELECT COD_GRUPO_CIA,COD_LOCAL,SEC_KARDEX,STK_ANTERIOR_PROD, CANT_MOV_PROD,STK_FINAL_PROD,VAL_FRACC_PROD
    FROM LGT_KARDEX
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in
    ORDER BY FEC_KARDEX,SEC_KARDEX;
    v_rCurKardexActual curKardex%ROWTYPE;
    v_rCurKardexAnterior curKardex%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);

    v_nStk LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFracLocal LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;

    v_nRow INTEGER;
  BEGIN
    v_vNombreArchivo := 'VER'||cCodLocal_in||TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MMSS')||'.TXT';
    DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));

    ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
    FOR v_rCurProd IN curProd
    LOOP
      BEGIN
        FOR v_rCurKardexActual IN curKardex(v_rCurProd.COD_PROD)
        LOOP
          --1
          IF curKardex%ROWCOUNT = 1 THEN
            IF v_rCurKardexActual.STK_ANTERIOR_PROD <> 0 THEN
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||'Stock Inicial diferente de cero');
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||
                                              curKardex%ROWCOUNT||CHR(9)||
                                              v_rCurKardexActual.SEC_KARDEX||CHR(9)||
                                              v_rCurKardexActual.STK_ANTERIOR_PROD||CHR(9)||
                                              v_rCurKardexActual.CANT_MOV_PROD||CHR(9)||
                                              v_rCurKardexActual.STK_FINAL_PROD||CHR(9)||
                                              v_rCurKardexActual.VAL_FRACC_PROD);
              RAISE_APPLICATION_ERROR(-20002,'Stock Inicial igual a cero');
            END IF;
          END IF;
          --2
          IF v_rCurKardexActual.CANT_MOV_PROD <> 0 THEN
            IF (v_rCurKardexActual.STK_ANTERIOR_PROD+v_rCurKardexActual.CANT_MOV_PROD) <> v_rCurKardexActual.STK_FINAL_PROD THEN
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||'Cantidad Movimiento erroneo');
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||
                                              curKardex%ROWCOUNT||CHR(9)||
                                              v_rCurKardexActual.SEC_KARDEX||CHR(9)||
                                              v_rCurKardexActual.STK_ANTERIOR_PROD||CHR(9)||
                                              v_rCurKardexActual.CANT_MOV_PROD||CHR(9)||
                                              v_rCurKardexActual.STK_FINAL_PROD||CHR(9)||
                                              v_rCurKardexActual.VAL_FRACC_PROD);
              RAISE_APPLICATION_ERROR(-20003,'Cantidad Movimiento erroneo');
            END IF;
          END IF;
          IF curKardex%ROWCOUNT > 1 THEN
            --3
            IF v_rCurKardexAnterior.STK_FINAL_PROD <> v_rCurKardexActual.STK_ANTERIOR_PROD THEN
              v_nRow := curKardex%ROWCOUNT;
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||'Secuencia de stock erroneo');
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||
                                              TO_CHAR(v_nRow-1)||CHR(9)||
                                              v_rCurKardexAnterior.SEC_KARDEX||CHR(9)||
                                              v_rCurKardexAnterior.STK_ANTERIOR_PROD||CHR(9)||
                                              v_rCurKardexAnterior.CANT_MOV_PROD||CHR(9)||
                                              v_rCurKardexAnterior.STK_FINAL_PROD||CHR(9)||
                                              v_rCurKardexAnterior.VAL_FRACC_PROD);
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||
                                              v_nRow||CHR(9)||
                                              v_rCurKardexActual.SEC_KARDEX||CHR(9)||
                                              v_rCurKardexActual.STK_ANTERIOR_PROD||CHR(9)||
                                              v_rCurKardexActual.CANT_MOV_PROD||CHR(9)||
                                              v_rCurKardexActual.STK_FINAL_PROD||CHR(9)||
                                              v_rCurKardexActual.VAL_FRACC_PROD);
              RAISE_APPLICATION_ERROR(-20004,'Secuencia de stock erroneo');
            END IF;
            --4
            IF (v_rCurKardexAnterior.STK_FINAL_PROD/v_rCurKardexAnterior.VAL_FRACC_PROD) <> ((v_rCurKardexActual.STK_FINAL_PROD-v_rCurKardexActual.CANT_MOV_PROD)/v_rCurKardexActual.VAL_FRACC_PROD) THEN
              v_nRow := curKardex%ROWCOUNT;
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||'Fraccionamiento erroneo');
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||
                                              TO_CHAR(v_nRow-1)||CHR(9)||
                                              v_rCurKardexAnterior.SEC_KARDEX||CHR(9)||
                                              v_rCurKardexAnterior.STK_ANTERIOR_PROD||CHR(9)||
                                              v_rCurKardexAnterior.CANT_MOV_PROD||CHR(9)||
                                              v_rCurKardexAnterior.STK_FINAL_PROD||CHR(9)||
                                              v_rCurKardexAnterior.VAL_FRACC_PROD);
              UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||
                                              v_nRow||CHR(9)||
                                              v_rCurKardexActual.SEC_KARDEX||CHR(9)||
                                              v_rCurKardexActual.STK_ANTERIOR_PROD||CHR(9)||
                                              v_rCurKardexActual.CANT_MOV_PROD||CHR(9)||
                                              v_rCurKardexActual.STK_FINAL_PROD||CHR(9)||
                                              v_rCurKardexActual.VAL_FRACC_PROD);
              RAISE_APPLICATION_ERROR(-20005,'Fraccionamiento erroneo');
            END IF;
          END IF;

          v_rCurKardexAnterior := v_rCurKardexActual;

        END LOOP;
        --5
        SELECT STK_FISICO,VAL_FRAC_LOCAL
          INTO v_nStk,v_nValFracLocal
        FROM LGT_PROD_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = v_rCurProd.COD_PROD;

        IF v_rCurKardexActual.STK_FINAL_PROD <> v_nStk  OR v_rCurKardexActual.VAL_FRACC_PROD <> v_nValFracLocal THEN
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||'Stock/Fraccion No coincide');
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||
                                              v_rCurKardexActual.STK_FINAL_PROD||CHR(9)||v_nStk||CHR(9)||'|'||
											  v_rCurKardexActual.VAL_FRACC_PROD||CHR(9)||v_nValFracLocal);
              RAISE_APPLICATION_ERROR(-20006,'Stock No coincide');
        END IF;

        --OK
        IF cIndImpProdOk_in IS NULL OR cIndImpProdOk_in = 'S' THEN
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurProd.COD_PROD||CHR(9)||'OK');
        END IF;

      EXCEPTION
        WHEN OTHERS THEN
        NULL;
      END;
    END LOOP;
    UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
  END;

  /****************************************************************************/
  PROCEDURE CORRECCION_PROD_KARDEX(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cCodProd_in IN CHAR,vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curKardex IS
    SELECT COD_GRUPO_CIA,COD_LOCAL,SEC_KARDEX,COD_MOT_KARDEX,STK_ANTERIOR_PROD, CANT_MOV_PROD,STK_FINAL_PROD,VAL_FRACC_PROD
    FROM LGT_KARDEX
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in
    ORDER BY FEC_KARDEX,SEC_KARDEX;
    v_rCurKardexActual curKardex%ROWTYPE;

    v_nStockInicialAux LGT_KARDEX.STK_FINAL_PROD%TYPE;
    v_nStockFinalAnt LGT_KARDEX.STK_FINAL_PROD%TYPE;
    v_nFracAnt LGT_KARDEX.VAL_FRACC_PROD%TYPE;

    v_nStk LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFracLocal LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;

    v_nValNumera PBL_NUMERA.VAL_NUMERA%TYPE;
  BEGIN
    --BLOQUEO DE TABLAS
    SELECT STK_FISICO,VAL_FRAC_LOCAL
          INTO v_nStk,v_nValFracLocal
    FROM LGT_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in FOR UPDATE;

    SELECT VAL_NUMERA
      INTO v_nValNumera
    FROM PBL_NUMERA
    WHERE COD_GRUPO_CIA	= cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_NUMERA = '016' FOR UPDATE;

    --PROCESO
     FOR v_rCurKardexActual IN curKardex
      LOOP

        IF curKardex%ROWCOUNT = 1 THEN
          --SUMA EL MOVIMIENTO
          v_nStockFinalAnt := v_rCurKardexActual.STK_ANTERIOR_PROD + v_rCurKardexActual.CANT_MOV_PROD;

          UPDATE LGT_KARDEX
          SET STK_FINAL_PROD = v_nStockFinalAnt,
              FEC_MOD_KARDEX = SYSDATE,
              USU_MOD_KARDEX = vIdUsu_in
          WHERE COD_GRUPO_CIA = v_rCurKardexActual.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCurKardexActual.COD_LOCAL
                AND SEC_KARDEX = v_rCurKardexActual.SEC_KARDEX;

          v_nFracAnt := v_rCurKardexActual.VAL_FRACC_PROD;
        ELSE
          IF v_rCurKardexActual.COD_MOT_KARDEX = 100 THEN
            --RECALCULA CAMBIO FRACCION
            v_nStockInicialAux := v_nStockFinalAnt;
            v_nStockFinalAnt := (v_nStockInicialAux*v_rCurKardexActual.VAL_FRACC_PROD)/v_nFracAnt;

            UPDATE LGT_KARDEX
            SET STK_ANTERIOR_PROD = v_nStockInicialAux,
                STK_FINAL_PROD = v_nStockFinalAnt,
                FEC_MOD_KARDEX = SYSDATE,
                USU_MOD_KARDEX = vIdUsu_in
            WHERE COD_GRUPO_CIA = v_rCurKardexActual.COD_GRUPO_CIA
                  AND COD_LOCAL = v_rCurKardexActual.COD_LOCAL
                  AND SEC_KARDEX = v_rCurKardexActual.SEC_KARDEX;

            v_nFracAnt := v_rCurKardexActual.VAL_FRACC_PROD;
          ELSE
            --CALCULA LOS STOCKS/FRACION Y SUMA EL MOVIMIENTO
            v_nStockInicialAux := (v_nStockFinalAnt*v_rCurKardexActual.VAL_FRACC_PROD)/v_nFracAnt;
            v_nStockFinalAnt := v_nStockInicialAux + v_rCurKardexActual.CANT_MOV_PROD;

            UPDATE LGT_KARDEX
            SET STK_ANTERIOR_PROD = v_nStockInicialAux,
                STK_FINAL_PROD = v_nStockFinalAnt,
                FEC_MOD_KARDEX = SYSDATE,
                USU_MOD_KARDEX = vIdUsu_in
            WHERE COD_GRUPO_CIA = v_rCurKardexActual.COD_GRUPO_CIA
                  AND COD_LOCAL = v_rCurKardexActual.COD_LOCAL
                  AND SEC_KARDEX = v_rCurKardexActual.SEC_KARDEX;

            v_nFracAnt := v_rCurKardexActual.VAL_FRACC_PROD;
          END IF;
        END IF;

      END LOOP;

      --MODIFICAR EL STOCK EN EL LOCAL
      IF v_rCurKardexActual.VAL_FRACC_PROD <> v_nValFracLocal THEN
          RAISE_APPLICATION_ERROR(-20007,'Fraccion No coincide');
      ELSE
        --MODIFICA STOCK
        UPDATE LGT_PROD_LOCAL
        SET STK_FISICO = v_nStockFinalAnt
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = cCodProd_in;
      END IF;

      --COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR, VERIFIQUE. ' ||SQLCODE||' -ERROR- '||SQLERRM);
  END;

  /****************************************************************************/

  PROCEDURE VERIFICA_COMPROBANTES(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecIni_in IN VARCHAR2,vFecFin_in IN VARCHAR2)
  AS
    v_cTipDoc CHAR(2);

    CURSOR curSeries(cTipDoc_in IN CHAR) IS
    SELECT NUM_SERIE_LOCAL
    FROM VTA_SERIE_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_COMP = cTipDoc_in
          AND EST_SERIE_LOCAL = 'A'
          AND NUM_SERIE_LOCAL IN (SELECT DISTINCT SUBSTR(NUM_COMP_PAGO,1,3)
                                  FROM VTA_COMP_PAGO
                                  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND COD_LOCAL = cCodLocal_in
                                        AND TIP_COMP_PAGO = cTipDoc_in
                                        AND FEC_CREA_COMP_PAGO BETWEEN TO_DATE(vFecIni_in,'dd/MM/yyyy') AND TO_DATE(vFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS'));
    --SERIE GUIAS
    CURSOR curSeriesGuias(cTipDoc_in IN CHAR) IS
    SELECT NUM_SERIE_LOCAL
    FROM VTA_SERIE_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_COMP = cTipDoc_in
          AND EST_SERIE_LOCAL = 'A'
          AND NUM_SERIE_LOCAL IN (SELECT DISTINCT SUBSTR(NUM_GUIA_REM,1,3)
                                  FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
                                  WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND G.COD_LOCAL = cCodLocal_in
                                        --AND SUBSTR(G.NUM_GUIA_REM,0,3) = cTipDoc_in
                                        AND C.TIP_NOTA_ES = '02'
                                        AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                        AND G.COD_LOCAL = C.COD_LOCAL
                                        AND G.NUM_NOTA_ES = C.NUM_NOTA_ES
                                        AND G.FEC_CREA_GUIA_REM BETWEEN TO_DATE(vFecIni_in,'dd/MM/yyyy') AND TO_DATE(vFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS'));

    v_rCurSeries curSeries%ROWTYPE;

    v_nMax NUMBER(7);
    v_nMin NUMBER(7);
    v_cNum CHAR(7);

    v_cComp CHAR(10);
    v_dFecha DATE;

    CURSOR curFechaComp IS
    SELECT DISTINCT TO_DATE(TO_CHAR(FEC_CREA_COMP_PAGO,'dd/MM/yyyy'))
    FROM VTA_COMP_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
    ORDER BY 1 ASC;
    v_dFechaComp DATE;

    v_cVerAux CHAR(1) := 'V';
    v_cVer CHAR(1);
    v_nCompIni CHAR(7) := NULL;
    v_nCompFin CHAR(7) := NULL;
  BEGIN
    OPEN curFechaComp;
    FETCH curFechaComp INTO v_dFechaComp;
    CLOSE curFechaComp;
    /*****************************BOLETAS************************************/
    v_cTipDoc:='01';
    FOR v_rCurSeries IN curSeries(v_cTipDoc)
    LOOP
      --COMPROBANTE MAXIMO DE BOLETAS
      SELECT MIN(TO_NUMBER(SUBSTR(NUM_COMP_PAGO,4))),MAX(TO_NUMBER(SUBSTR(NUM_COMP_PAGO,4)))
        INTO v_nMin,v_nMax
      FROM VTA_COMP_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND TIP_COMP_PAGO = v_cTipDoc
            AND SUBSTR(NUM_COMP_PAGO,0,3) = v_rCurSeries.NUM_SERIE_LOCAL
            AND FEC_CREA_COMP_PAGO BETWEEN TO_DATE(vFecIni_in,'dd/MM/yyyy') AND TO_DATE(vFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
      ;
      --SI ES LA FECHA DE INICIO ES LA DE APERTURA EL COMP =1
      IF v_dFechaComp >= TO_DATE(vFecIni_in,'dd/MM/yyyy') THEN
        v_nMin := 1;
      END IF;
      --VERIFICAR LOS COMPROBANTES DE BOLETAS
      FOR i IN v_nMin..v_nMax
      LOOP
        BEGIN
          v_cNum := Farma_Utility.COMPLETAR_CON_SIMBOLO(i,7,'0','I');
          SELECT NUM_COMP_PAGO,FEC_CREA_COMP_PAGO,'V'
            INTO v_cComp,v_dFecha,v_cVer
          FROM VTA_COMP_PAGO
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND TIP_COMP_PAGO = v_cTipDoc
                AND SUBSTR(NUM_COMP_PAGO,0,3) = v_rCurSeries.NUM_SERIE_LOCAL
                AND SUBSTR(NUM_COMP_PAGO,4) = v_cNum
                AND FEC_CREA_COMP_PAGO BETWEEN TO_DATE(vFecIni_in,'dd/MM/yyyy') AND TO_DATE(vFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          ;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            v_cVer := 'X';
        END;

        IF v_cVerAux = 'V' AND v_cVer = 'X' THEN
          --GUARDA COMP INICIAL
          v_nCompIni := v_cNum;
        ELSIF v_cVerAux = 'X' AND v_cVer = 'V' THEN
          --GUARDA COMP FINAL
          v_nCompFin := v_cNum;
          --GRABAR LOG DE COMP FALTANTE
          GRABA_LOG_COMP(cCodLocal_in,v_cTipDoc,'BOLETAS',v_rCurSeries.NUM_SERIE_LOCAL,v_dFecha,v_nCompIni,v_nCompFin-1);
          v_nCompIni := NULL;
          v_nCompFin := NULL;
        END IF;

        v_cVerAux := v_cVer;
      END LOOP;

      v_cVerAux := 'V';
      v_cVer := NULL;
    END LOOP;

    /*****************************FACTURAS************************************/
    v_cTipDoc:='02';
    FOR v_rCurSeries IN curSeries(v_cTipDoc)
    LOOP
      --COMPROBANTE MAXIMO DE FACTURAS
      SELECT MIN(TO_NUMBER(SUBSTR(NUM_COMP_PAGO,4))),MAX(TO_NUMBER(SUBSTR(NUM_COMP_PAGO,4)))
        INTO v_nMin,v_nMax
      FROM VTA_COMP_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND TIP_COMP_PAGO = v_cTipDoc
            AND SUBSTR(NUM_COMP_PAGO,0,3) = v_rCurSeries.NUM_SERIE_LOCAL
            AND FEC_CREA_COMP_PAGO BETWEEN TO_DATE(vFecIni_in,'dd/MM/yyyy') AND TO_DATE(vFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
      ;
      --SI ES LA FECHA DE INICIO ES LA DE APERTURA EL COMP =1
      IF v_dFechaComp >= TO_DATE(vFecIni_in,'dd/MM/yyyy') THEN
        v_nMin := 1;
      END IF;
      --VERIFICAR LOS COMPROBANTES DE FACTURAS
      FOR i IN v_nMin..v_nMax
      LOOP
        BEGIN
          v_cNum := Farma_Utility.COMPLETAR_CON_SIMBOLO(i,7,'0','I');
          SELECT NUM_COMP_PAGO,FEC_CREA_COMP_PAGO
            INTO v_cComp,v_dFecha
          FROM VTA_COMP_PAGO
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND TIP_COMP_PAGO = v_cTipDoc
                AND SUBSTR(NUM_COMP_PAGO,0,3) = v_rCurSeries.NUM_SERIE_LOCAL
                AND SUBSTR(NUM_COMP_PAGO,4) = v_cNum
                AND FEC_CREA_COMP_PAGO BETWEEN TO_DATE(vFecIni_in,'dd/MM/yyyy') AND TO_DATE(vFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          ;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            v_cVer := 'X';
        END;

        IF v_cVerAux = 'V' AND v_cVer = 'X' THEN
          --GUARDA COMP INICIAL
          v_nCompIni := v_cNum;
        ELSIF v_cVerAux = 'X' AND v_cVer = 'V' THEN
          --GUARDA COMP FINAL
          v_nCompFin := v_cNum;
          --GRABAR LOG DE COMP FALTANTE
          GRABA_LOG_COMP(cCodLocal_in,v_cTipDoc,'FACTURAS',v_rCurSeries.NUM_SERIE_LOCAL,v_dFecha,v_nCompIni,v_nCompFin-1);
          v_nCompIni := NULL;
          v_nCompFin := NULL;
        END IF;

        v_cVerAux := v_cVer;
      END LOOP;

      v_cVerAux := 'V';
      v_cVer := NULL;
    END LOOP;

    /*****************************GUIAS************************************/
    v_cTipDoc:='03';
    FOR v_rCurSeries IN curSeriesGuias(v_cTipDoc)
    LOOP
      --COMPROBANTE MAXIMO DE FACTURAS
      SELECT MIN(TO_NUMBER(SUBSTR(NUM_GUIA_REM,4))),MAX(TO_NUMBER(SUBSTR(NUM_GUIA_REM,4)))
        INTO v_nMin,v_nMax
      FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
      WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
            AND G.COD_LOCAL = cCodLocal_in
            AND SUBSTR(G.NUM_GUIA_REM,0,3) = v_rCurSeries.NUM_SERIE_LOCAL
            AND C.TIP_NOTA_ES = '02'
            AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
            AND G.COD_LOCAL = C.COD_LOCAL
            AND G.NUM_NOTA_ES = C.NUM_NOTA_ES
            AND G.FEC_CREA_GUIA_REM BETWEEN TO_DATE(vFecIni_in,'dd/MM/yyyy') AND TO_DATE(vFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
      ;
      --SI ES LA FECHA DE INICIO ES LA DE APERTURA EL COMP =1
      IF v_dFechaComp >= TO_DATE(vFecIni_in,'dd/MM/yyyy') THEN
        v_nMin := 1;
      END IF;
      --VERIFICAR LOS COMPROBANTES DE GUIAS
      FOR i IN v_nMin..v_nMax
      LOOP
        BEGIN
          v_cNum := Farma_Utility.COMPLETAR_CON_SIMBOLO(i,7,'0','I');
          SELECT NUM_GUIA_REM,FEC_CREA_GUIA_REM
            INTO v_cComp,v_dFecha
          FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
          WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
                AND G.COD_LOCAL = cCodLocal_in
                AND SUBSTR(G.NUM_GUIA_REM,0,3) = v_rCurSeries.NUM_SERIE_LOCAL
                AND SUBSTR(G.NUM_GUIA_REM,4) = v_cNum
                AND C.TIP_NOTA_ES = '02'
                AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                AND G.COD_LOCAL = C.COD_LOCAL
                AND G.NUM_NOTA_ES = C.NUM_NOTA_ES
                AND G.FEC_CREA_GUIA_REM BETWEEN TO_DATE(vFecIni_in,'dd/MM/yyyy') AND TO_DATE(vFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          ;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            v_cVer := 'X';
        END;

        IF v_cVerAux = 'V' AND v_cVer = 'X' THEN
          --GUARDA COMP INICIAL
          v_nCompIni := v_cNum;
        ELSIF v_cVerAux = 'X' AND v_cVer = 'V' THEN
          --GUARDA COMP FINAL
          v_nCompFin := v_cNum;
          --GRABAR LOG DE COMP FALTANTE
          GRABA_LOG_COMP(cCodLocal_in,v_cTipDoc,'GUIAS',v_rCurSeries.NUM_SERIE_LOCAL,v_dFecha,v_nCompIni,v_nCompFin-1);
          v_nCompIni := NULL;
          v_nCompFin := NULL;
        END IF;

        v_cVerAux := v_cVer;
      END LOOP;

      v_cVerAux := 'V';
      v_cVer := NULL;
    END LOOP;

    COMMIT;

    --ENVIA CORREO
    VER_ENVIA_CORREO_ALERTA(cCodGrupoCia_in,cCodLocal_in);
  END;

  /****************************************************************************/

  PROCEDURE GRABA_LOG_COMP(cCodLocal_in IN CHAR,cTipDoc_in IN CHAR,vDescDoc_in IN VARCHAR2,
  cSerie_in IN CHAR,dFechaDoc_in IN DATE,cNumFalta_in IN CHAR,cNumFaltaFin_in IN CHAR)
  AS
  BEGIN
    INSERT INTO TMP_LOG_COMP(COD_LOCAL	,
                              DOC	,
                              SERIE	,
                              FEC_DOC	,
                              NUM_DOC	,
                              NUM_DOC_FIN)
    VALUES(cCodLocal_in,vDescDoc_in,cSerie_in,dFechaDoc_in,
    cSerie_in||cNumFalta_in,cSerie_in||Farma_Utility.COMPLETAR_CON_SIMBOLO(cNumFaltaFin_in,7,'0','I'));
  END;

  /****************************************************************************/

  PROCEDURE VER_ENVIA_CORREO_ALERTA(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30);
    CCReceiverAddress VARCHAR2(120) := 'inunez@mifarma.com.pe; vangeles@mifarma.com.pe; operador@mifarma.com.pe';

    mesg_body VARCHAR2(32767);

    CURSOR curLog IS
    SELECT L.*,C.DESC_LOCAL
    FROM TMP_LOG_COMP L, PBL_LOCAL C
    WHERE L.COD_LOCAL = cCodLocal_in
          AND L.COD_LOCAL = C.COD_LOCAL
          AND L.FEC_ENVIO_MAIL IS NULL
    ORDER BY 2,3,5;

    v_rCurLog curLog%ROWTYPE;

    existeLog NUMBER(7):=0;
    v_vDescLocal VARCHAR2(120);

  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL, MAIL_LOCAL
        INTO v_vDescLocal,  ReceiverAddress
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    mesg_body := mesg_body||'<table style="text-align: left; width: 100%;" border="1"';
    mesg_body := mesg_body||' cellpadding="2" cellspacing="1">';
    mesg_body := mesg_body||'  <tbody>';
    mesg_body := mesg_body||'    <tr>';
    mesg_body := mesg_body||'      <th><small>#</small></th>';
    mesg_body := mesg_body||'      <th><small>DOCUMENTO</small></th>';
    mesg_body := mesg_body||'      <th><small>SERIE</small></th>';
    mesg_body := mesg_body||'      <th><small>FECHA</small></th>';
    mesg_body := mesg_body||'      <th><small>COMPROBANTE</small></th>';
    mesg_body := mesg_body||'    </tr>';

    --CREAR CUERPO MENSAJE;
    FOR v_rCurLog IN curLog
    LOOP
      existeLog := existeLog+1;

      mesg_body := mesg_body||'   <tr>'||
                              '      <td><small>'||existeLog||'</small></td>'||
                              '      <td><small>'||v_rCurLog.DOC||'</small></td>'||
                              '      <td><small>'||v_rCurLog.SERIE||'</small></td>'||
                              '      <td><small>'||v_rCurLog.FEC_DOC||'</small></td>'||
                              '      <td><small>'||v_rCurLog.NUM_DOC||'-'||v_rCurLog.NUM_DOC_FIN||'</small></td>'||
                              '   </tr>';
    END LOOP;
    --FIN HTML
    mesg_body := mesg_body||'  </tbody>';
    mesg_body := mesg_body||'</table>';
    mesg_body := mesg_body||'<br>';

    --ENVIA MAIL
    IF existeLog > 0 THEN

      UPDATE TMP_LOG_COMP
      SET FEC_ENVIO_MAIL = SYSDATE
      WHERE COD_LOCAL = cCodLocal_in
            AND FEC_ENVIO_MAIL IS NULL;

      IF ReceiverAddress IS NULL THEN
        ReceiverAddress := 'joliva@mifarma.com.pe';
      END IF;

      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            ReceiverAddress||', '||FARMA_EMAIL.GET_RECEIVER_ADDRESS_VERIFICA,
                            'COMPROBANTES FALTANTES: '||v_vDescLocal,
                            'ALERTA',
                            mesg_body,
                            CCReceiverAddress,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);
    END IF;

    COMMIT;
  END;

  /***************************************************************************/
  PROCEDURE VER_ENVIA_FALTA_CERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30);
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(32767);

    CURSOR curLog IS
    SELECT L.COD_PROD,P.DESC_PROD,P.DESC_UNID_PRESENT,T.UNID_VTA,B.NOM_LAB,USU_CREA_PROD_LOCAL_FALTA_STK,COUNT(SEC_USU_LOCAL) AS CANTIDAD
    FROM LGT_PROD_LOCAL_FALTA_STK L, LGT_PROD_LOCAL T, LGT_PROD P, LGT_LAB B
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND L.FEC_ENVIO_MAIL IS NULL
          AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
          AND L.COD_LOCAL = T.COD_LOCAL
          AND L.COD_PROD = T.COD_PROD
          AND T.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND T.COD_PROD = P.COD_PROD
          AND P.COD_LAB = B.COD_LAB
    GROUP BY L.COD_GRUPO_CIA,L.COD_LOCAL,L.COD_PROD,P.DESC_PROD,P.DESC_UNID_PRESENT,T.UNID_VTA,B.NOM_LAB,USU_CREA_PROD_LOCAL_FALTA_STK
    ORDER BY 2;

    v_rCurLog curLog%ROWTYPE;

    existeLog NUMBER(7):=0;
    v_vDescLocal VARCHAR2(120);

  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL, MAIL_LOCAL
        INTO v_vDescLocal,  ReceiverAddress
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    mesg_body := mesg_body||'<table style="text-align: left; width: 100%;" border="1"';
    mesg_body := mesg_body||' cellpadding="2" cellspacing="1">';
    mesg_body := mesg_body||'  <tbody>';
    mesg_body := mesg_body||'    <tr>';
    mesg_body := mesg_body||'      <th><small>#</small></th>';
    mesg_body := mesg_body||'      <th><small>COD_PROD</small></th>';
    mesg_body := mesg_body||'      <th><small>DESC_PROD</small></th>';
    mesg_body := mesg_body||'      <th><small>UNID_PRESENT</small></th>';
    mesg_body := mesg_body||'      <th><small>UNID_VTA</small></th>';
    mesg_body := mesg_body||'      <th><small>LABORATORIO</small></th>';
    mesg_body := mesg_body||'      <th><small>SOLICITANTE</small></th>';
    mesg_body := mesg_body||'      <th><small>CANTIDAD</small></th>';
    mesg_body := mesg_body||'    </tr>';

    --CREAR CUERPO MENSAJE;
    FOR v_rCurLog IN curLog
    LOOP
      existeLog := existeLog+1;

      mesg_body := mesg_body||'   <tr>'||
                              '      <td><small>'||existeLog||'</small></td>'||
                              '      <td style="font-weight: bold;"><small>'||v_rCurLog.COD_PROD||'</small></td>'||
                              '      <td><small>'||v_rCurLog.DESC_PROD||'</small></td>'||
                              '      <td><small>'||v_rCurLog.DESC_UNID_PRESENT||'</small></td>'||
                              '      <td><small>'||NVL(v_rCurLog.UNID_VTA,' ')||'</small></td>'||
                              '      <td><small>'||v_rCurLog.NOM_LAB||'</small></td>'||
                              '      <td><small>'||v_rCurLog.USU_CREA_PROD_LOCAL_FALTA_STK||'</small></td>'||
                              '      <td style="color: rgb(255, 0, 0);"><small>'||v_rCurLog.CANTIDAD||'</small></td>'||
                              '   </tr>';
    END LOOP;
    --FIN HTML
    mesg_body := mesg_body||'  </tbody>';
    mesg_body := mesg_body||'</table>';
    mesg_body := mesg_body||'<br>';

    --ENVIA MAIL
    IF existeLog > 0 THEN

      UPDATE LGT_PROD_LOCAL_FALTA_STK
      SET FEC_ENVIO_MAIL = SYSDATE
      WHERE COD_LOCAL = cCodLocal_in
            AND FEC_ENVIO_MAIL IS NULL;

      IF ReceiverAddress IS NULL THEN
        ReceiverAddress := 'joliva@mifarma.com.pe';
      END IF;

      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            ReceiverAddress||', '||FARMA_EMAIL.GET_RECEIVER_ADDRESS_VERIFICA,
                            'PRODUCTOS FALTA CERO: '||v_vDescLocal,
                            'INFORMACION',
                            mesg_body,
                            CCReceiverAddress,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);
    END IF;

    COMMIT;
  END;

END;

/
