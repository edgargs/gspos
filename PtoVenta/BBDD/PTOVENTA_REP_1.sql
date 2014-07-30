--------------------------------------------------------
--  DDL for Package Body PTOVENTA_REP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_REP" AS
  /*REVISION 6 - 28/08/2007*/
  PROCEDURE INV_CALCULA_MAX_MIN(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cIndAutomatico_in IN CHAR DEFAULT 'N')
  AS

    CURSOR curProd IS
    WITH LISTA_PROD AS
         (
          SELECT COD_PROD
          FROM VTA_RES_VTA_EFECTIVA_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FECHA_MES BETWEEN ADD_MONTHS(TRUNC(SYSDATE-1,'MM'),-4) AND SYSDATE
          UNION
          SELECT COD_PROD
          FROM LGT_PARAM_PROD_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND IND_ACTIVO > SYSDATE
         )
    SELECT P.COD_PROD,
           T.CANT_DIAS_VTA AS CANT_DIAS_EFECT,
           T.NUM_DIAS AS CANT_DIAS_REP,
           P.STK_FISICO,
           P.VAL_FRAC_LOCAL,
           --PTOVENTA_INV.INV_GET_STK_TRANS_PROD(T.COD_GRUPO_CIA,T.COD_LOCAL,T.COD_PROD) AS STK_TRANSITO,
           --INV_GET_STK_TRANS_PROD(T.COD_GRUPO_CIA,T.COD_LOCAL,T.COD_PROD) AS STK_TRANSITO,
           NVL(S.STK_TRANSITO,0) AS STK_TRANSITO,
           P.CANT_EXHIB,
           T.CANT_DIA_ROT,
           T.CANT_UNID_VTA
    FROM LGT_PROD_LOCAL_REP T, LGT_PROD_LOCAL P,
         AUX_STK_TRANSITO S,
         LISTA_PROD LP
    WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T.COD_LOCAL = cCodLocal_in
--          AND (T.CANT_ROT > 0 OR T.COD_PROD IN (SELECT COD_PROD FROM LGT_PARAM_PROD_LOCAL PPL WHERE IND_AUTORIZADO = 'S' AND TOT_UNID_VTA_RDM != 0))
          AND T.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND T.COD_LOCAL = P.COD_LOCAL
          AND T.COD_PROD = P.COD_PROD
          AND T.COD_GRUPO_CIA = S.COD_GRUPO_CIA(+)
          AND T.COD_LOCAL = S.COD_LOCAL(+)
          AND T.COD_PROD = S.COD_PROD(+)
          AND T.COD_PROD = LP.COD_PROD;

/*
    SELECT P.COD_PROD,
           T.CANT_DIAS_VTA AS CANT_DIAS_EFECT,
           T.NUM_DIAS AS CANT_DIAS_REP,
           P.STK_FISICO,
           P.VAL_FRAC_LOCAL,
           --PTOVENTA_INV.INV_GET_STK_TRANS_PROD(T.COD_GRUPO_CIA,T.COD_LOCAL,T.COD_PROD) AS STK_TRANSITO,
           --INV_GET_STK_TRANS_PROD(T.COD_GRUPO_CIA,T.COD_LOCAL,T.COD_PROD) AS STK_TRANSITO,
           NVL(S.STK_TRANSITO,0) AS STK_TRANSITO,
           P.CANT_EXHIB,
           T.CANT_DIA_ROT,
           T.CANT_UNID_VTA
    FROM LGT_PROD_LOCAL_REP T, LGT_PROD_LOCAL P,
         AUX_STK_TRANSITO S
    WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T.COD_LOCAL = cCodLocal_in
--          AND (T.CANT_ROT > 0 OR T.COD_PROD IN (SELECT COD_PROD FROM LGT_PARAM_PROD_LOCAL PPL WHERE IND_AUTORIZADO = 'S' AND TOT_UNID_VTA_RDM != 0))
          AND T.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND T.COD_LOCAL = P.COD_LOCAL
          AND T.COD_PROD = P.COD_PROD
          AND T.COD_GRUPO_CIA = S.COD_GRUPO_CIA(+)
          AND T.COD_LOCAL = S.COD_LOCAL(+)
          AND T.COD_PROD = S.COD_PROD(+);

*/

    v_rProd curProd%ROWTYPE;
--    v_nBajaRot PBL_LOCAL.FAC_BAJA_ROT%TYPE;

    v_nCantMin LGT_PROD_LOCAL_REP.CANT_MIN_STK%TYPE;
    v_nCantMax LGT_PROD_LOCAL_REP.CANT_MAX_STK%TYPE;

    v_nCantSugeridaReponer LGT_PROD_LOCAL_REP.CANT_SUG%TYPE;
--    v_nCantMaxAux LGT_PROD_LOCAL_REP.CANT_MAX_STK%TYPE;

    v_cPorcAdic PBL_LOCAL.PORC_ADIC_REP%TYPE;
    v_nCantMaxAdic PBL_LOCAL.CANT_UNIDAD_MAX%TYPE;

    v_cValidacion  PBL_TAB_GRAL.DESC_CORTA%TYPE;
    v_cIndPedRep PBL_LOCAL.IND_PED_REP%TYPE;

    ipaddr VARCHAR2(100);
  BEGIN

    --04/12/2007 ERIOS Temporal para matriz
    IF cCodLocal_in = g_cCodMatriz THEN
      select SYS_CONTEXT('USERENV', 'IP_ADDRESS', 15) INTO ipaddr from dual;
      FARMA_EMAIL.envia_correo('009-MATRIZ '||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            'joliva,operador',
                            'INICIO EJECUCION PTOVENTA_REP 009-MATRIZ: '||TO_CHAR(SYSDATE,'dd/MM/yyyy'),
                            'VERIFIQUE EJECUCION',
                            'INICIO '||cCodLocal_in||', '||cIndAutomatico_in||', '||ipaddr,
                            NULL,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);
    END IF;
    --13/08/2007  ERIOS  Verifica si se esta procesando el calculo.
    -- 03/09/2007 PAMEGHINO ELIMINA LOS RESUMEN Y VUELVE A PROCESARLOS
    -- 11/12/2007 PAMEGHINO NO BORRA RESUMEN EN MATRIZ
    IF cCodLocal_in != g_cCodMatriz THEN
       REP_ELIMINA_FECHA_RESUMEN(cCodGrupoCia_in,cCodLocal_in,cIndAutomatico_in);
    END IF;

    SELECT IND_PED_REP
      INTO v_cIndPedRep
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in FOR UPDATE;
    IF v_cIndPedRep = 'P' THEN
      RAISE_APPLICATION_ERROR(-20001,'EL CALCULO SE ENCUENTRA EN PROCESO, NO PUEDE REALIZAR ESTA OPERACION.');
    ELSE
      UPDATE PBL_LOCAL SET USU_MOD_LOCAL = 'PK_CAL_REP', FEC_MOD_LOCAL = SYSDATE,
            IND_PED_REP = 'P'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;
      COMMIT;
    END IF;

    IF cIndAutomatico_in = 'S' THEN
      --BLANQUEAR TABLA TEMPORAL
      INV_LIMPIA_TMP_PROD(cCodGrupoCia_in,cCodLocal_in);
      -- REGENERA UNID VTA MENSUAL Y DIAS EFECTIVOS DE ESTE MES
      INI_VTA_RES_VTA_EFECTIVA_LOCAL(cCodGrupoCia_in, cCodLocal_in);

      COMMIT;
      --CALCULAR RESUMEN DE VENTAS
      --14/05/2007 ERIOS Si es matriz, se hace el acumulado para toda la cadena.
      IF cCodLocal_in = g_cCodMatriz THEN
        --17/07/2007  ERIOS  Se Calculan el resumen de stock en la cadena y cant. exhib
        REP_CALCULA_STOCK_M(cCodGrupoCia_in,cCodLocal_in);
        REP_CALCULA_EXHIB_M(cCodGrupoCia_in,cCodLocal_in);
        REP_CALCULA_HIST_STK_M(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
        INV_CALCULA_RESUMEN_VENTAS_M(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
      ELSE
        INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
      END IF;

      --CALCULAR ROTACION PRODUCTOS
      --17/08/2007 ERIOS Se modifico la cant de dias a considerar de 90 a 30
      --11/10/2007 ERIOS Se modifico la cant de dias a considerar de 30 a 90 (again)
      --28/10/2008 ERIOS Se modifico la cant de dias a considerar de 30 a 60
      -- 2012-02-21 JOLIVA: Ya no se usa estos datos
--      INV_CALCULA_ROTACION_PRODS(cCodGrupoCia_in,cCodLocal_in,60);
    ELSE --cIndAutomatico_in = 'N'
      --CALCULAR RESUMEN DE VENTAS
      INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE,'dd/MM/yyyy'));
      --BLANQUEAR TABLA TEMPORAL
      INV_LIMPIA_TMP_PROD_DIA(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE,'dd/MM/yyyy'));
      --CALCULAR ROTACION PRODUCTOS
      -- 2012-02-21 JOLIVA: Ya no se usa estos datos
--      INV_CALCULA_ROTACION_PRODS(cCodGrupoCia_in,cCodLocal_in,0);
    END IF;

    --CALCULAR UNIDADES VENDIDAS PRODUCTOS
    INV_CALCULA_UNID_VEND_PRODS(cCodGrupoCia_in,cCodLocal_in);
    --19/07/2007  ERIOS  Calcula Stock en transito
    REP_CALCULA_TRANSITO(cCodGrupoCia_in,cCodLocal_in);

    --FACTOR DE BAJA ROTACION DEL LOCAL
--    v_nBajaRot:=INV_GET_FAC_BAJA_ROT(cCodGrupoCia_in,cCodLocal_in);

    --CALCULAR CANTIDAD MAXIMA A PEDIR
    SELECT PORC_ADIC_REP,CANT_UNIDAD_MAX
        INTO v_cPorcAdic,v_nCantMaxAdic
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    SELECT to_number(DESC_CORTA,'0.999999999') INTO v_cValidacion
    FROM   PBL_TAB_GRAL
    WHERE  ID_TAB_GRAL = '59';

    FOR v_rProd IN curProd
    LOOP
/*
      --DETERMINA MAX Y MIN SEGUN EL FACTOR BAJA ROTACION
      IF v_rProd.CANT_DIAS_EFECT < v_rProd.CANT_DIAS_REP THEN
        v_nCantMaxAux := v_rProd.CANT_UNID_VTA;
      ELSE
        v_nCantMaxAux := (v_rProd.CANT_UNID_VTA*v_rProd.CANT_DIAS_REP)/v_rProd.CANT_DIAS_EFECT;
      END IF;

      IF (v_nCantMaxAux) < v_nBajaRot THEN
        v_nCantMin:=1;
        v_nCantMax:=1;
      ELSIF (v_nCantMaxAux) < 1 THEN
        v_nCantMin:=1;
        v_nCantMax:=1;
      ELSE
-- 2008-08-22 JOLIVA (SE REDONDEA HACIA ARRIBA)
        v_nCantMin:=CEIL(v_nCantMaxAux);
        v_nCantMax:=CEIL(v_nCantMaxAux);
      END IF;
*/
      v_nCantMax := REP_GET_CANT_MAX_PROD(cCodGrupoCia_in,cCodLocal_in,v_rProd.Cod_Prod);

      --SE SUMA LA EXHIBICION
      v_nCantMax := v_nCantMax+v_rProd.Cant_Exhib;

      v_nCantMin := v_nCantMax;

      IF (v_nCantMax = 1) THEN
			    -- Si se tiene menos de 33% de entero de stock en el local --> Pedir 1 unidad
          --23/03/2007 ERIOS Se suma el transito y se evalua en razon de 1/5
          --12/04/2007 JOLIVA Se vuelve al valor de 1/3
			    IF ((v_rProd.STK_FISICO/v_rProd.VAL_FRAC_LOCAL)+v_rProd.STK_TRANSITO < v_cValidacion) THEN
				     v_nCantSugeridaReponer := 1;
				  ELSE
				     v_nCantSugeridaReponer := 0;
				  END IF;
			ELSE
				  v_nCantSugeridaReponer := FLOOR((v_nCantMax)-(v_rProd.STK_FISICO/v_rProd.VAL_FRAC_LOCAL)-v_rProd.STK_TRANSITO);
          v_nCantSugeridaReponer := CASE WHEN v_nCantSugeridaReponer < 0 THEN 0 ELSE v_nCantSugeridaReponer END;
			END IF;


      --DBMS_OUTPUT.PUT_LINE('MAX='||v_nCantMax);
      --DBMS_OUTPUT.PUT_LINE('MAX='||ROUND(v_nCantMax));
      UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = 'PK_CAL_REP',FEC_MOD_PROD_LOCAL_REP = SYSDATE,
          CANT_MIN_STK = v_nCantMin,
          CANT_MAX_STK = v_nCantMax,
          CANT_SUG = v_nCantSugeridaReponer,
          --CANT_ROT = ,
          CANT_TRANSITO = v_rProd.STK_TRANSITO,
          STK_FISICO = v_rProd.STK_FISICO,
          VAL_FRAC_LOCAL = v_rProd.VAL_FRAC_LOCAL,
          CANT_EXHIB = v_rProd.Cant_Exhib,
          CANT_MAX_ADIC = INV_GET_CANT_MAX(v_nCantSugeridaReponer,v_cPorcAdic,
          v_nCantMaxAdic,v_rProd.VAL_FRAC_LOCAL,(v_rProd.STK_FISICO/v_rProd.VAL_FRAC_LOCAL))
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = v_rProd.COD_PROD;
    END LOOP;

    --19/03/2007  ERIOS  Se solicitan stock para exhibicion de los productos
    --                   que no tengan rotacion.
    UPDATE LGT_PROD_LOCAL_REP R
    SET USU_MOD_PROD_LOCAL_REP = 'PK_CAL_REP',FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        (CANT_MAX_STK,CANT_SUG,STK_FISICO,VAL_FRAC_LOCAL,CANT_EXHIB,CANT_MAX_ADIC) =
        (SELECT L.CANT_EXHIB,L.CANT_EXHIB-ROUND(L.STK_FISICO/L.VAL_FRAC_LOCAL),L.STK_FISICO,L.VAL_FRAC_LOCAL,L.CANT_EXHIB,L.CANT_EXHIB-ROUND(L.STK_FISICO/L.VAL_FRAC_LOCAL)
                     FROM LGT_PROD_LOCAL L
                     WHERE L.COD_GRUPO_CIA = R.COD_GRUPO_CIA
                           AND L.COD_LOCAL = R.COD_LOCAL
                           AND L.COD_PROD = R.COD_PROD
                           AND (L.CANT_EXHIB-ROUND(L.STK_FISICO/L.VAL_FRAC_LOCAL)) > 0 )
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND R.CANT_ROT = 0
            AND EXISTS (SELECT 1
                       FROM LGT_PROD_LOCAL L
                       WHERE L.COD_GRUPO_CIA = R.COD_GRUPO_CIA
                             AND L.COD_LOCAL = R.COD_LOCAL
                             AND L.COD_PROD = R.COD_PROD
                             AND (L.CANT_EXHIB-ROUND(L.STK_FISICO/L.VAL_FRAC_LOCAL)) > 0);

    IF cIndAutomatico_in = 'N' THEN
      --SETEAR EL INDICADOR DE REP = 'S'
      v_cIndPedRep := 'S';
    ELSE
      v_cIndPedRep := 'N';
    END IF;
    UPDATE PBL_LOCAL SET USU_MOD_LOCAL = 'PK_CAL_REP', FEC_MOD_LOCAL = SYSDATE,
          IND_PED_REP = v_cIndPedRep,
          FEC_GENERA_MAX_MIN = SYSDATE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    COMMIT;

    --Actualiza Productos ABC
    --07/9/2007 ERIOS Se calcula ambos tipos ABC.
    --07/01/2008 ERIOS Se agrega el ultimo parametro.
    TMP_REP_ERN.REP_DETERMINAR_TIPO(cCodGrupoCia_in,cCodLocal_in,to_char(Sysdate-30,'dd/MM/YYYY'),to_char(Sysdate,'dd/MM/YYYY'),'N');

    --04/12/2007 ERIOS Temporal para matriz
    IF cCodLocal_in = g_cCodMatriz THEN
      select SYS_CONTEXT('USERENV', 'IP_ADDRESS', 15) INTO ipaddr from dual;
      FARMA_EMAIL.envia_correo('009-MATRIZ '||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            'joliva,operador',
                            'FIN EJECUCION PTOVENTA_REP 009-MATRIZ: '||TO_CHAR(SYSDATE,'dd/MM/yyyy'),
                            'VERIFIQUE EJECUCION',
                            'FIN '||cCodLocal_in||', '||cIndAutomatico_in||', '||ipaddr,
                            NULL,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);
    END IF;
  END;

  /*****************************************************************************/
  PROCEDURE INV_LIMPIA_TMP_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
  BEGIN
    UPDATE LGT_PROD_LOCAL_REP R1 SET USU_MOD_PROD_LOCAL_REP = 'PK_CAL_REP', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        CANT_MIN_STK = 0,
        CANT_MAX_STK = 0,
        CANT_SUG = 0,
        CANT_SOL = NULL,
        CANT_ROT = 0,
        CANT_TRANSITO = 0,
        CANT_DIA_ROT = 0,
        NUM_DIAS = 0,
        STK_FISICO = 0,
        VAL_FRAC_LOCAL = 1,
        CANT_EXHIB = 0,
        CANT_VTA_PER_0 = 0,
        CANT_VTA_PER_1 = 0,
        CANT_VTA_PER_2 = 0,
        CANT_VTA_PER_3 = 0,
        CANT_DIAS_VTA = NULL, --DIAS DE VENTAS DEL PRODUCTO
        CANT_UNID_VTA = 0,
        CANT_MAX_ADIC = 0,
        CANT_ADIC     = NULL,
        -- 2009-06-03
        IND_STK_LOCALES = 'N',
        IND_STK_ALMACEN = 'S',
        STK_ALMACEN = 0,
        UNID_MINIMA = 0,
        CANT_SUG_BK = 0,
        UNID_MIN_SUG = 0,
        STK_UNID_MIN = 0,
        VAL_FACT_CONV = 0,
        CANT_ADICIONAL = 0

    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
  END;

  /*****************************************************************************/
  PROCEDURE INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2)
  AS
    v_dFecha DATE;
    CURSOR curCambio IS
    SELECT C.COD_PROD_ANT,C.COD_PROD_NUE
    FROM LGT_CAMBIO_PROD C, VTA_RES_VTA_ACUM_LOCAL A
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
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
       AND C.EST_CAMBIO_PROD = 'A'
       AND A.CANT_UNID_VTA <> 0
--       AND A.FEC_DIA_VTA BETWEEN TRUNC(v_dFecha - 90) AND TRUNC(v_dFecha)
       AND A.FEC_DIA_VTA BETWEEN ADD_MONTHS(TRUNC(v_dFecha,'MM'),-3) AND TRUNC(v_dFecha)
       AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
       AND A.COD_PROD = C.COD_PROD_ANT
    ORDER BY C.FEC_INI_CAMBIO_PROD;
    rowHistorico curHistorico%ROWTYPE;

  BEGIN
    v_dFecha := TO_DATE(vFecha_in,'dd/MM/yyyy');

    --VTA_RES_VTA_PROD_LOCAL
    DELETE VTA_RES_VTA_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND FEC_DIA_VTA = v_dFecha AND COD_LOCAL = cCodLocal_in;
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
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0 AND SUM(VAL_PREC_TOTAL) != 0;

    --VTA_RES_VTA_REP_LOCAL
    DELETE VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND FEC_DIA_VTA = v_dFecha AND COD_LOCAL = cCodLocal_in;
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
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0;

    --VTA_RES_VTA_ACUM_LOCAL
    DELETE VTA_RES_VTA_ACUM_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND FEC_DIA_VTA = v_dFecha AND COD_LOCAL = cCodLocal_in;
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
                   );

    DELETE FROM VTA_RES_VTA_REP_LOCAL V1
    where  exists (
                   SELECT 1
                   FROM   TMP_PROD_EXCLUIR_VTA T
                   where  t.cod_prod = v1.cod_prod
                   and    v1.fec_dia_vta between trunc(t.fech_inicio) and trunc(t.fech_fin) + 1 -1/24/60/60
                   );

    DELETE FROM VTA_RES_VTA_ACUM_LOCAL V2
    where  exists (
                   SELECT 1
                   FROM   TMP_PROD_EXCLUIR_VTA T
                   where  t.cod_prod = v2.cod_prod
                   and    v2.fec_dia_vta between trunc(t.fech_inicio) and trunc(t.fech_fin) + 1 -1/24/60/60
                   );

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
  /*****************************************************************************/
  PROCEDURE INV_CALCULA_ROTACION_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  nDiasVenta_in IN NUMBER)
  AS
    CURSOR curProd IS
    SELECT V1.COD_GRUPO_CIA,V1.COD_PROD,
    V2.NUM_DIAS_ROT AS ROT_PROD,
    V2.NUM_MAX_DIAS_REP AS NUM_DIAS
    FROM
    (
    SELECT DISTINCT COD_GRUPO_CIA,COD_PROD
    FROM VTA_RES_VTA_ACUM_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_DIA_VTA BETWEEN TRUNC(SYSDATE-nDiasVenta_in) AND SYSDATE
    --09/08/2007  JOLIVA  Se agregar recalcular cant. solicitada a productos que están en tránsito
    UNION
    SELECT COD_GRUPO_CIA,COD_PROD
    FROM AUX_STK_TRANSITO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
    --14/08/2007  ERIOS  Se considera los productos que han tenido ventas en el dia.
    UNION
    SELECT DISTINCT D.COD_GRUPO_CIA,D.COD_PROD
    FROM VTA_PEDIDO_VTA_CAB C,
         VTA_PEDIDO_VTA_DET D
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.FEC_PED_VTA BETWEEN TRUNC(SYSDATE) AND SYSDATE
          AND C.EST_PED_VTA = 'C'
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    UNION
    -- Añadido por Paulo
    SELECT DISTINCT D.COD_GRUPO_CIA,D.COD_PROD
    FROM VTA_PEDIDO_VTA_CAB C,
         VTA_PEDIDO_VTA_DET D
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          --AND C.FEC_PED_VTA BETWEEN TRUNC(SYSDATE) AND SYSDATE
          AND C.EST_PED_VTA = 'C'
          AND D.IND_CALCULO_MAX_MIN = 'N'
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
     ) V1,
     (SELECT R.COD_GRUPO_CIA,R.COD_GRUPO_REP,DECODE(NVL(R.NUM_DIAS_ROT,0),0,L.NUM_DIAS_ROT,R.NUM_DIAS_ROT) AS NUM_DIAS_ROT,
       DECODE(NVL(R.NUM_MAX_DIAS_REP,0),0,L.NUM_MAX_DIAS_REP,R.NUM_MAX_DIAS_REP) AS NUM_MAX_DIAS_REP
      FROM LGT_GRUPO_REP_LOCAL R, PBL_LOCAL L
      WHERE R.COD_GRUPO_CIA = cCodGrupoCia_in
            AND R.COD_LOCAL = cCodLocal_in
            AND R.EST_GRUPO_REP_LOCAL = 'A'
            AND R.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND R.COD_LOCAL = L.COD_LOCAL) V2,
       LGT_PROD P,
       LGT_PROD_LOCAL L
     WHERE V1.COD_GRUPO_CIA = cCodGrupoCia_in
           AND L.COD_LOCAL = cCodLocal_in
           AND L.IND_REPONER = 'S'
           AND V1.COD_GRUPO_CIA = P.COD_GRUPO_CIA
           AND V1.COD_PROD = P.COD_PROD
           AND P.COD_GRUPO_CIA = V2.COD_GRUPO_CIA
           AND P.COD_GRUPO_REP = V2.COD_GRUPO_REP
           AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
           AND P.COD_PROD = L.COD_PROD;
    /*SELECT DISTINCT COD_PROD,
           INV_GET_ROTACION_PROD(cCodGrupoCia_in,cCodLocal_in,COD_PROD) AS ROT_PROD
    FROM
    (
    SELECT DISTINCT COD_PROD
    FROM VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_DIA_VTA BETWEEN TO_DATE(TO_CHAR(SYSDATE-90,'dd/MM/yyyy'),'dd/MM/yyyy') AND SYSDATE
          AND COD_PROD IN (SELECT COD_PROD
                           FROM LGT_PROD_LOCAL
                           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND COD_LOCAL = cCodLocal_in
                                 AND IND_REPONER = 'S')
     UNION
     SELECT COD_PROD_NUE
      FROM LGT_CAMBIO_PROD
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      --AND COD_PROD_NUE = cCodProd_in
      AND EST_CAMBIO_PROD = 'A'
     ) V1;*/
    v_rProd curProd%ROWTYPE;
    v_nRot LGT_PROD_LOCAL_REP.Cant_Rot%TYPE;
    v_Periodo NUMBER(1);
    v_DiasVta LGT_PROD_LOCAL_REP.CANT_DIAS_VTA%TYPE;
    v_nCantVtaUlt LGT_PROD_LOCAL_REP.CANT_UNID_VTA%TYPE;
  BEGIN
    FOR v_rProd IN curProd
    LOOP
      /*v_nRot:=INV_GET_CANT_VTA_ULT_DIAS(cCodGrupoCia_in,cCodLocal_in,v_rProd.Cod_Prod,
                                        v_rProd.Rot_Prod,3,SYSDATE,
                                        v_Periodo)/v_rProd.Rot_Prod;*/
      --17/08/2007 ERIOS Se modifico la cant de periodos a considerar de 3 a 1
      --11/10/2007 ERIOS Se modifico la cant de periodos a considerar de 1 a 3 (again)
      v_nCantVtaUlt:=INV_GET_CANT_VTA_ULT_DIAS(cCodGrupoCia_in,cCodLocal_in,v_rProd.Cod_Prod,
                                        v_rProd.Rot_Prod,1,SYSDATE,
                                        v_Periodo);
      --14/05/2007 ERIOS Si es matriz
      IF cCodLocal_in = g_cCodMatriz THEN
        v_DiasVta := INV_GET_CANT_DIAS_EFECTIVO_M(cCodGrupoCia_in, cCodLocal_in, v_rProd.Cod_Prod,
                     v_Periodo,v_rProd.Rot_Prod);
      ELSE
        v_DiasVta := INV_GET_CANT_DIAS_EFECTIVO(cCodGrupoCia_in, cCodLocal_in, v_rProd.Cod_Prod,
                     v_Periodo,v_rProd.Rot_Prod);
      END IF;
      --ROTACION EFECTIVA
      IF v_DiasVta = 0 THEN
        v_nRot := 0;
      ELSE
        v_nRot := v_nCantVtaUlt/v_DiasVta;
      END IF;

      UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = 'PK_CAL_REP', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
          CANT_ROT = v_nRot,--SE GRABA LA ROTACION EFECTIVA
          CANT_DIA_ROT = v_rProd.Rot_Prod,
          NUM_DIAS = v_rProd.NUM_DIAS,
          CANT_DIAS_VTA = v_DiasVta,
          CANT_UNID_VTA = v_nCantVtaUlt
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = v_rProd.Cod_Prod;
    END LOOP;
    --COMMIT;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_FAC_BAJA_ROT(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN NUMBER
  IS
    v_nBajaRot PBL_LOCAL.FAC_BAJA_ROT%TYPE;
  BEGIN
    SELECT FAC_BAJA_ROT INTO v_nBajaRot
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
    RETURN v_nBajaRot;
  END;

  /*****************************************************************************/
  /*FUNCTION INV_GET_ROTACION_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN NUMBER
  IS
    v_nRot LGT_PROD_LOCAL_REP.Cant_Rot%TYPE;
  BEGIN
    SELECT NVL(R.NUM_DIAS_ROT,0) INTO v_nRot
    FROM LGT_PROD P, LGT_GRUPO_REP_LOCAL R
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.COD_PROD = cCodProd_in
          AND P.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND R.COD_LOCAL = cCodLocal_in
          AND R.COD_GRUPO_REP = P.COD_GRUPO_REP;
    IF v_nRot=0 THEN
      SELECT NUM_DIAS_ROT INTO v_nRot
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;
    END IF;
    RETURN v_nRot;
  END;*/

  /*****************************************************************************/
  FUNCTION INV_GET_CANT_VTA_ULT_DIAS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                     cCodProd_in IN CHAR,nRotProd_in IN NUMBER,nCantVeces_in IN NUMBER,dFecha_in IN DATE,
                                     nPeriodo_out OUT CHAR)
  RETURN NUMBER
  IS
    v_nCantVta VTA_RES_VTA_REP_LOCAL.CANT_UNID_VTA%TYPE:=0;
    CURSOR curVta IS
    SELECT TRUNC((TRUNC(dFecha_in) - A.FEC_DIA_VTA) / nRotProd_in) AS PERIODO,
           SUM(A.CANT_UNID_VTA) AS CANT_UNID_VTA
      FROM VTA_RES_VTA_ACUM_LOCAL A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.COD_PROD = cCodProd_in
       AND A.FEC_DIA_VTA BETWEEN
           (TRUNC(dFecha_in) - nRotProd_in * nCantVeces_in)+1 AND dFecha_in
     GROUP BY TRUNC((TRUNC(dFecha_in) - A.FEC_DIA_VTA) / nRotProd_in)
    HAVING SUM(A.CANT_UNID_VTA) <> 0
     ORDER BY 1;
    rowVta curVta%ROWTYPE;
  BEGIN
    /*FOR i IN 1..nCantVeces_in
    LOOP
      SELECT NVL(SUM(CANT_UNID_VTA),0)
        INTO v_nCantVta
      FROM VTA_RES_VTA_ACUM_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProd_in
            AND FEC_DIA_VTA BETWEEN TRUNC(dFecha_in-(nRotProd_in*i))
                                      AND dFecha_in;

    EXIT WHEN v_nCantVta > 0;
    END LOOP;*/
    OPEN curVta;
    FETCH curVta INTO rowVta;
    v_nCantVta := rowVta.CANT_UNID_VTA;
    nPeriodo_out := rowVta.PERIODO;
    CLOSE curVta;
    IF v_nCantVta IS NULL THEN
       v_nCantVta := 0;
    END IF;
    RETURN v_nCantVta;
  END;

  /*****************************************************************************/
  --Dubilluz 17.08.2007
  PROCEDURE INV_GENERAR_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  nCantItems_in IN NUMBER,nCantProds_in IN NUMBER,cIdUsu_in IN CHAR)
  AS
    v_cNumera LGT_PED_REP_CAB.NUM_PED_REP%TYPE;

	v_nNumMinDiasRep LGT_PED_REP_CAB.NUM_MIN_DIAS_REP%TYPE;
	v_cNumMaxDiasRep LGT_PED_REP_CAB.NUM_MAX_DIAS_REP%TYPE;
	v_cNumDiasRot LGT_PED_REP_CAB.NUM_DIAS_ROT%TYPE;
        v_dFecGenera LGT_PED_REP_CAB.FEC_GENERA_MAX_MIN%TYPE;
  BEGIN
   SELECT NUM_MIN_DIAS_REP,NUM_MAX_DIAS_REP,NUM_DIAS_ROT,FEC_GENERA_MAX_MIN
   INTO   v_nNumMinDiasRep, v_cNumMaxDiasRep ,v_cNumDiasRot,v_dFecGenera
   FROM   PBL_LOCAL
   WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND
   		 COD_LOCAL=cCodLocal_in;

    v_cNumera :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPedRep),10,'0','I' );

	--obtener valores de rposicion del local
    INSERT INTO LGT_PED_REP_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_REP,CANT_ITEMS,
    CANT_PROD,TIP_PED_REP,USU_CREA_PED_REP_CAB,NUM_MIN_DIAS_REP, NUM_MAX_DIAS_REP,
    NUM_DIAS_ROT ,FEC_GENERA_MAX_MIN)
    VALUES(cCodGrupoCia_in,cCodLocal_in,v_cNumera,nCantItems_in,
    nCantProds_in,'01',cIdUsu_in,v_nNumMinDiasRep, v_cNumMaxDiasRep ,
    v_cNumDiasRot,v_dFecGenera);

    INSERT INTO LGT_PED_REP_DET(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_REP,COD_PROD,
    DESC_UNID_VTA,VAL_FRAC_PROD,FEC_PED_REP_DET,CANT_SOLICITADA,CANT_SUGERIDA,
    STK_DISPONIBLE,STK_TRANSITO,VAL_ROT_PROD,CANT_MIN_STK,CANT_MAX_STK,
    USU_CREA_PED_REP_DET,CANT_DIA_ROT,NUM_DIAS,CANT_EXHIB,
    Cant_Vta_Per_0,Cant_Vta_Per_1,Cant_Vta_Per_2,Cant_Vta_Per_3,
    CANT_DIAS_VTA,CANT_UNID_VTA,Cant_Adic,
    --AÑADIDO
    --DUBILLUZ 15/08/2007
    IND_STK_LOCALES , IND_STK_ALMACEN,
    PVM_AUTORIZADO, UNID_STK_IDEAL_REAL, MES_STK_IDEAL_REAL,
    DIA_EFEC_STK_IDEAL_REAL, MAX_STK_IDEAL_REAL, MAX_STK_IDEAL_EFECT,
    UN_VTA_M0, UN_VTA_M1, UN_VTA_M2, UN_VTA_M3, UN_VTA_M4,
    DIA_EF_M0, DIA_EF_M1, DIA_EF_M2, DIA_EF_M3, DIA_EF_M4,
    -- 2010-01-06 JOLIVA: SE AGREGAN CAMPOS NUEVOS
    FACTOR_AUMENTO_STK_EFECT, STK_IDEAL_REAL, STK_IDEAL_EFECT, STK_IDEAL_EFICIENTE,
-- 2010-01-11 JOLIVA: SE MODIFICA PARA QUE LA REPOSICIÓN CONSIDERE 6 MESES DE ROTACION DE PRODUCTOS
    UN_VTA_M5, DIA_EF_M5
    )
    SELECT cCodGrupoCia_in,cCodLocal_in,v_cNumera, T.COD_PROD,
    L.UNID_VTA,T.VAL_FRAC_LOCAL,SYSDATE,T.CANT_SOL,DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),
    T.STK_FISICO,T.CANT_TRANSITO,T.CANT_ROT,T.CANT_MIN_STK,T.CANT_MAX_STK,
    cIdUsu_in,T.CANT_DIA_ROT,T.NUM_DIAS,T.CANT_EXHIB,
    t.cant_vta_per_0, t.cant_vta_per_1,
    t.cant_vta_per_2,t.cant_vta_per_3,
    T.CANT_DIAS_VTA,T.CANT_UNID_VTA,t.cant_adic,
    --AÑADIDO
    --DUBILLUZ 15/08/2007
    T.IND_STK_LOCALES , T.IND_STK_ALMACEN,
    -- 2009-09-29 JOLIVA
    T.PVM_AUTORIZADO, T.UNID_STK_IDEAL_REAL, T.MES_STK_IDEAL_REAL,
    T.DIA_EFEC_STK_IDEAL_REAL, T.MAX_STK_IDEAL_REAL, T.MAX_STK_IDEAL_EFECT,
    T.UN_VTA_M0, T.UN_VTA_M1, T.UN_VTA_M2, T.UN_VTA_M3, T.UN_VTA_M4,
    T.DIA_EF_M0, T.DIA_EF_M1, T.DIA_EF_M2, T.DIA_EF_M3, T.DIA_EF_M4,
    -- 2010-01-06 JOLIVA: SE AGREGAN CAMPOS NUEVOS
    T.FACTOR_AUMENTO_STK_EFECT, T.STK_IDEAL_REAL, T.STK_IDEAL_EFECT, T.STK_IDEAL_EFICIENTE,
-- 2010-01-11 JOLIVA: SE MODIFICA PARA QUE LA REPOSICIÓN CONSIDERE 6 MESES DE ROTACION DE PRODUCTOS
    T.UN_VTA_M5, T.DIA_EF_M5
    FROM LGT_PROD_LOCAL_REP T, LGT_PROD_LOCAL L, LGT_PROD P
    WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T.COD_LOCAL = cCodLocal_in
          AND T.CANT_SOL IS NOT NULL
          --AND T.CANT_SOL > 0
          AND L.IND_REPONER = 'S'
          AND P.EST_PROD = 'A'
	  AND T.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND T.COD_LOCAL = L.COD_LOCAL
          AND T.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
    UNION ALL
    SELECT cCodGrupoCia_in,cCodLocal_in,v_cNumera, T.COD_PROD,
    L.UNID_VTA,T.VAL_FRAC_LOCAL,SYSDATE,T.CANT_SUG,T.CANT_SUG,
    T.STK_FISICO,T.CANT_TRANSITO,T.CANT_ROT,T.CANT_MIN_STK,T.CANT_MAX_STK,
    cIdUsu_in,T.CANT_DIA_ROT,T.NUM_DIAS,T.CANT_EXHIB,
    t.cant_vta_per_0, t.cant_vta_per_1,
    t.cant_vta_per_2,t.cant_vta_per_3,
    T.CANT_DIAS_VTA,T.CANT_UNID_VTA,t.cant_adic,
    --AÑADIDO
    --DUBILLUZ 15/08/2007
    T.IND_STK_LOCALES , T.IND_STK_ALMACEN,
    -- 2009-09-29 JOLIVA
    T.PVM_AUTORIZADO, T.UNID_STK_IDEAL_REAL, T.MES_STK_IDEAL_REAL,
    T.DIA_EFEC_STK_IDEAL_REAL, T.MAX_STK_IDEAL_REAL, T.MAX_STK_IDEAL_EFECT,
    T.UN_VTA_M0, T.UN_VTA_M1, T.UN_VTA_M2, T.UN_VTA_M3, T.UN_VTA_M4,
    T.DIA_EF_M0, T.DIA_EF_M1, T.DIA_EF_M2, T.DIA_EF_M3, T.DIA_EF_M4,
    -- 2010-01-06 JOLIVA: SE AGREGAN CAMPOS NUEVOS
    T.FACTOR_AUMENTO_STK_EFECT, T.STK_IDEAL_REAL, T.STK_IDEAL_EFECT, T.STK_IDEAL_EFICIENTE,
-- 2010-01-11 JOLIVA: SE MODIFICA PARA QUE LA REPOSICIÓN CONSIDERE 6 MESES DE ROTACION DE PRODUCTOS
    T.UN_VTA_M5, T.DIA_EF_M5
    FROM LGT_PROD_LOCAL_REP T, LGT_PROD_LOCAL L, LGT_PROD P
    WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T.COD_LOCAL = cCodLocal_in
          AND T.CANT_SOL IS NULL
          AND T.CANT_SUG > 0
          AND L.IND_REPONER = 'S'
          AND P.EST_PROD = 'A'
          AND T.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND T.COD_LOCAL = L.COD_LOCAL
          AND T.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD;

    --dubilluz 07.07.2011
          update lgt_ped_rep_det q
          set    (q.cant_solicitada_old,q.cant_solicitada) =
                                                        (
                                                         select vh.cant_solicitada,vh.cantidad_reponer_simulada
                                                         from  (
                                                                SELECT d.cod_grupo_cia,d.cod_local,d.num_ped_rep,d.cod_prod,d.cant_solicitada,
                                                                       case
                                                                       when  d.CANT_MAX_STK >= mp.CANT_PACK*1 then
                                                                             round(d.cant_solicitada/mp.CANT_PACK*1)*mp.CANT_PACK*1
                                                                       else
                                                                           -- 2012-04-24 JOLIVA: SE MODIFICA LA CANTIDAD A REPONER EN CASO DE QUE EL STK_MAX SEA MENOR AL TAMAÑO DEL MP
                                                                              CASE WHEN D.COD_PROD IN ('133113', '133128', '133169', '133189', '133206', '512798') THEN
                                                                                   CASE WHEN TO_NUMBER(MP.CANT_PACK) < D.CANT_MAX_STK * 2 THEN
                                                                                      CASE WHEN (D.STK_DISPONIBLE / D.VAL_FRAC_PROD) <= CASE WHEN NVL(D.PVM_AUTORIZADO,0) = 0 THEN GREATEST(D.UN_VTA_M0, D.UN_VTA_M1, D.UN_VTA_M2, D.UN_VTA_M3) ELSE GREATEST(NVL(D.PVM_AUTORIZADO,0), D.UN_VTA_M0) END * 2 / 30 THEN
                                                                                         TO_NUMBER(MP.CANT_PACK)
                                                                                      ELSE
                                                                                           d.cant_solicitada
                                                                                      END
                                                                                   ELSE
                                                                                       d.cant_solicitada
                                                                                   END
                                                                              ELSE
                                                                                  d.cant_solicitada
                                                                              END
                                                                       end cantidad_reponer_simulada
                                                                FROM   lgt_ped_rep_det d,
                                                                       MAE_PROD_MASTER_PACK mp
                                                                where  d.num_ped_rep = v_cNumera
                                                                and    d.cod_local = cCodLocal_in
                                                                and    d.cod_grupo_cia = cCodGrupoCia_in
                                                                and    d.cod_grupo_cia = mp.cod_grupo_cia
                                                                and    d.cod_prod = mp.cod_prod
                                                                ) vh
                                                            where  vh.cant_solicitada != vh.cantidad_reponer_simulada
                                                            and    vh.cod_grupo_cia = q.cod_grupo_cia
                                                            and    vh.cod_local = q.cod_local
                                                            and    vh.num_ped_rep = q.num_ped_rep
                                                            and    vh.cod_prod = q.cod_prod
                                                         )
          where  exists (
                      select 1
                       from  (
                              SELECT d.cod_grupo_cia,d.cod_local,d.num_ped_rep,d.cod_prod,d.cant_solicitada,
                                     case
                                     when  d.CANT_MAX_STK >= mp.CANT_PACK*1 then
                                           round(d.cant_solicitada/mp.CANT_PACK*1)*mp.CANT_PACK*1
                                     else
										   -- 2012-04-24 JOLIVA: SE MODIFICA LA CANTIDAD A REPONER EN CASO DE QUE EL STK_MAX SEA MENOR AL TAMAÑO DEL MP
											  CASE WHEN D.COD_PROD IN ('133113', '133128', '133169', '133189', '133206', '512798') THEN
												   CASE WHEN TO_NUMBER(MP.CANT_PACK) < D.CANT_MAX_STK * 2 THEN
													  CASE WHEN (D.STK_DISPONIBLE / D.VAL_FRAC_PROD) <= CASE WHEN NVL(D.PVM_AUTORIZADO,0) = 0 THEN GREATEST(D.UN_VTA_M0, D.UN_VTA_M1, D.UN_VTA_M2, D.UN_VTA_M3) ELSE GREATEST(NVL(D.PVM_AUTORIZADO,0), D.UN_VTA_M0) END * 2 / 30 THEN
														 TO_NUMBER(MP.CANT_PACK)
													  ELSE
														   d.cant_solicitada
													  END
												   ELSE
													   d.cant_solicitada
												   END
											  ELSE
												  d.cant_solicitada
											  END
                                     end cantidad_reponer_simulada
                              FROM   lgt_ped_rep_det d,
                                     MAE_PROD_MASTER_PACK mp
                              where  d.num_ped_rep = v_cNumera
                              and    d.cod_local = cCodLocal_in
                              and    d.cod_grupo_cia = cCodGrupoCia_in
                              and    d.cod_grupo_cia = mp.cod_grupo_cia
                              and    d.cod_prod = mp.cod_prod
                              ) vh
                          where  vh.cant_solicitada != vh.cantidad_reponer_simulada
                          and    vh.cod_grupo_cia = q.cod_grupo_cia
                          and    vh.cod_local = q.cod_local
                          and    vh.num_ped_rep = q.num_ped_rep
                          and    vh.cod_prod = q.cod_prod
                        );
    --fin dubilluz 07.07.2011

    UPDATE PBL_LOCAL SET USU_MOD_LOCAL = cIdUsu_in, FEC_MOD_LOCAL = SYSDATE,
          IND_PED_REP = 'N'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumPedRep,cIdUsu_in);

    --AGREGADO 05/07/2006 ERIOS --FALTA CAMPOS MOD
    UPDATE LGT_PROD_LOCAL_FALTA_STK
    SET FEC_GENERA_PED_REP = SYSDATE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL = cCodLocal_in
        AND FEC_GENERA_PED_REP IS NULL;
  END;
  /*****************************************************************************/
  PROCEDURE INV_CALCULA_UNID_VEND_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
    CURSOR curVend IS
    SELECT A.COD_PROD,
           --TRUNC((TRUNC(SYSDATE) - A.FEC_DIA_VTA) / 30) AS PERIODO,
           TRUNC(MONTHS_BETWEEN(TRUNC(SYSDATE,'MM'),TRUNC(A.FEC_DIA_VTA,'MM')))AS PERIODO, --JCORTEZ 05/03/2009
           SUM(A.CANT_UNID_VTA) AS CANT_UNID_VTA
      FROM VTA_RES_VTA_ACUM_LOCAL A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.FEC_DIA_VTA BETWEEN (TRUNC(SYSDATE) - 120) AND SYSDATE
     --GROUP BY A.COD_PROD, TRUNC((TRUNC(SYSDATE) - A.FEC_DIA_VTA) / 30);
     GROUP BY A.COD_PROD, TRUNC(MONTHS_BETWEEN(TRUNC(SYSDATE,'MM'),TRUNC(A.FEC_DIA_VTA,'MM')));
     rowVend curVend%ROWTYPE;
  BEGIN
    FOR rowVend IN curVend
    LOOP
      IF rowVend.PERIODO = 0 THEN
       UPDATE LGT_PROD_LOCAL_REP
          SET CANT_VTA_PER_0 = rowVend.CANT_UNID_VTA
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = rowVend.COD_PROD;
      ELSIF rowVend.PERIODO = 1 THEN
       UPDATE LGT_PROD_LOCAL_REP
          SET CANT_VTA_PER_1 = rowVend.CANT_UNID_VTA
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = rowVend.COD_PROD;
      ELSIF rowVend.PERIODO = 2 THEN
       UPDATE LGT_PROD_LOCAL_REP
          SET CANT_VTA_PER_2 = rowVend.CANT_UNID_VTA
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = rowVend.COD_PROD;
      ELSIF rowVend.PERIODO = 3 THEN
       UPDATE LGT_PROD_LOCAL_REP
          SET CANT_VTA_PER_3 = rowVend.CANT_UNID_VTA
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = rowVend.COD_PROD;
      END IF;

      UPDATE LGT_PROD_LOCAL_REP
         SET USU_MOD_PROD_LOCAL_REP = 'PK_CAL_REP',
             FEC_MOD_PROD_LOCAL_REP = SYSDATE
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND COD_PROD = rowVend.COD_PROD;
    END LOOP;
  END;
  /*****************************************************************************/
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
  FUNCTION INV_GET_CANT_DIAS_EFECTIVO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cCodProd_in IN CHAR,
                                      cPeriodo_in IN CHAR,
                                      nRotProd_in IN NUMBER)
    RETURN NUMBER
  IS
    v_nCantDiasVta NUMBER;
    v_Periodo NUMBER(1) := TO_NUMBER(cPeriodo_in);
  BEGIN
       SELECT COUNT(V5.FECHA)
       INTO   v_nCantDiasVta
       FROM   (SELECT V1.FECHA,
                      NVL(V2.CANTIDAD, 0) CANT_1,
                      NVL(V3.CANTIDAD, 0) CANT_2
               FROM   (SELECT DISTINCT D.FEC_DIA_VTA FECHA
                       --FROM   VTA_RES_VTA_REP_LOCAL D
                       FROM   VTA_RES_VTA_ACUM_LOCAL D
                       WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND    D.COD_LOCAL = cCodLocal_in
                       --AND    TRUNC((TRUNC(SYSDATE) - D.FEC_DIA_VTA) / nRotProd_in) = cPeriodo_in
                       AND D.FEC_DIA_VTA BETWEEN TRUNC(SYSDATE - (nRotProd_in*(1+v_Periodo)))+1 AND (SYSDATE - (nRotProd_in*v_Periodo))
                       ) V1,
                      (SELECT A.FEC_DIA_VTA FECHA,
                              A.CANT_UNID_VTA CANTIDAD
                       FROM   VTA_RES_VTA_ACUM_LOCAL A
                       --FROM   VTA_RES_VTA_REP_LOCAL A
                       WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND    A.COD_LOCAL = cCodLocal_in
                       AND    A.COD_PROD = cCodProd_in
                       --AND    TRUNC((TRUNC(SYSDATE) - A.FEC_DIA_VTA) / nRotProd_in) = cPeriodo_in
                       AND A.FEC_DIA_VTA BETWEEN TRUNC(SYSDATE - (nRotProd_in*(1+v_Periodo)))+1 AND (SYSDATE - (nRotProd_in*v_Periodo))
                       ) V2,
                      (SELECT B.FEC_STK FECHA,
                              B.CANT_STK CANTIDAD
                       FROM   LGT_HIST_STK_LOCAL B
                       WHERE  B.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND    B.COD_LOCAL = cCodLocal_in
                       AND    B.COD_PROD = cCodProd_in
                       --AND    TRUNC((TRUNC(SYSDATE) - B.FEC_STK) / nRotProd_in) = cPeriodo_in
                       AND B.FEC_STK BETWEEN TRUNC(SYSDATE - (nRotProd_in*(1+v_Periodo)))+1 AND (SYSDATE - (nRotProd_in*v_Periodo))
                       ) V3
               WHERE  V1.FECHA = V2.FECHA(+)
               AND    V1.FECHA = V3.FECHA(+)) V5
       WHERE  V5.CANT_1 + V5.CANT_2 > 0;
    RETURN v_nCantDiasVta;
  END;
  /*****************************************************************************/
  /*PROCEDURE REP_CALCULA_DIAS_EFECTIVO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR)
  AS
    CURSOR curProd IS
    SELECT V1.COD_GRUPO_CIA,V1.COD_PROD,
    V2.NUM_DIAS_ROT AS ROT_PROD,
    V2.NUM_MAX_DIAS_REP AS NUM_DIAS
    FROM
    (
    SELECT DISTINCT COD_GRUPO_CIA,COD_PROD
    FROM VTA_RES_VTA_ACUM_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_DIA_VTA BETWEEN TRUNC(SYSDATE-90) AND SYSDATE
     ) V1,
     (SELECT R.COD_GRUPO_CIA,R.COD_GRUPO_REP,DECODE(NVL(R.NUM_DIAS_ROT,0),0,L.NUM_DIAS_ROT,R.NUM_DIAS_ROT) AS NUM_DIAS_ROT,
       DECODE(NVL(R.NUM_MAX_DIAS_REP,0),0,L.NUM_MAX_DIAS_REP,R.NUM_MAX_DIAS_REP) AS NUM_MAX_DIAS_REP
      FROM LGT_GRUPO_REP_LOCAL R, PBL_LOCAL L
      WHERE R.COD_GRUPO_CIA = cCodGrupoCia_in
            AND R.COD_LOCAL = cCodLocal_in
            AND R.EST_GRUPO_REP_LOCAL = 'A'
            AND R.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND R.COD_LOCAL = L.COD_LOCAL) V2,
       LGT_PROD P,
       LGT_PROD_LOCAL L
     WHERE V1.COD_GRUPO_CIA = cCodGrupoCia_in
           AND L.COD_LOCAL = cCodLocal_in
           AND L.IND_REPONER = 'S'
           AND V1.COD_GRUPO_CIA = P.COD_GRUPO_CIA
           AND V1.COD_PROD = P.COD_PROD
           AND P.COD_GRUPO_CIA = V2.COD_GRUPO_CIA
           AND P.COD_GRUPO_REP = V2.COD_GRUPO_REP
           AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
           AND P.COD_PROD = L.COD_PROD;
    v_rProd curProd%ROWTYPE;

    v_nRot LGT_PROD_LOCAL_REP.Cant_Rot%TYPE;
    v_Periodo NUMBER(1);
    v_DiasVta LGT_PROD_LOCAL_REP.CANT_DIAS_VTA%TYPE;
  BEGIN
    --SE LIMPIA EL CAMPO CANT_DIAS_VTA
    UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = 'SISTEMAS', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        CANT_DIAS_VTA = NULL --DIAS DE VENTAS DEL PRODUCTO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
    --SE CALCULA DIAS_VTA
    FOR v_rProd IN curProd
    LOOP
      v_nRot:=INV_GET_CANT_VTA_ULT_DIAS(cCodGrupoCia_in,cCodLocal_in,v_rProd.Cod_Prod,
                                        v_rProd.Rot_Prod,3,SYSDATE,
                                        v_Periodo)/v_rProd.Rot_Prod;

      v_DiasVta := INV_GET_CANT_DIAS_EFECTIVO(cCodGrupoCia_in, cCodLocal_in, v_rProd.Cod_Prod, v_Periodo);

      UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = 'SISTEMAS', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
          CANT_DIAS_VTA = v_DiasVta
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = v_rProd.Cod_Prod;
    END LOOP;

    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
  END;*/
  /*****************************************************************************/
  PROCEDURE INV_LIMPIA_TMP_PROD_DIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2)
  AS
  BEGIN
    UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = 'PK_CAL_REP', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        CANT_MIN_STK = 0,
        CANT_MAX_STK = 0,
        CANT_SUG = 0,
        CANT_SOL = NULL,
        CANT_ROT = 0,
        CANT_TRANSITO = 0,
        CANT_DIA_ROT = 0,
        NUM_DIAS = 0,
        STK_FISICO = 0,
        VAL_FRAC_LOCAL = 1,
        CANT_EXHIB = 0,
        CANT_VTA_PER_0 = 0,
        CANT_VTA_PER_1 = 0,
        CANT_VTA_PER_2 = 0,
        CANT_VTA_PER_3 = 0,
        CANT_DIAS_VTA = NULL, --DIAS DE VENTAS DEL PRODUCTO
        CANT_UNID_VTA = 0,
        CANT_MAX_ADIC = 0,
        CANT_ADIC     = NULL
    WHERE (COD_GRUPO_CIA,COD_LOCAL,COD_PROD) IN
          (SELECT DISTINCT COD_GRUPO_CIA,COD_LOCAL,COD_PROD
            FROM VTA_RES_VTA_ACUM_LOCAL
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_DIA_VTA = TO_DATE(vFecha_in,'dd/MM/yyyy')
            --09/08/2007  JOLIVA  Se blanquean los productos que tienen stock en tránsito
           UNION
           SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD
           FROM AUX_STK_TRANSITO
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
           --14/08/2007  ERIOS  Se considera los productos que han tenido ventas en el dia.
          UNION
          SELECT DISTINCT D.COD_GRUPO_CIA,D.COD_LOCAL,D.COD_PROD
          FROM VTA_PEDIDO_VTA_CAB C,
               VTA_PEDIDO_VTA_DET D
          WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND C.COD_LOCAL = cCodLocal_in
                AND C.FEC_PED_VTA BETWEEN TRUNC(SYSDATE) AND SYSDATE
                AND C.EST_PED_VTA = 'C'
                AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                AND C.COD_LOCAL = D.COD_LOCAL
                AND C.NUM_PED_VTA = D.NUM_PED_VTA
          UNION
          SELECT DISTINCT D.COD_GRUPO_CIA,D.COD_LOCAL,D.COD_PROD
          FROM VTA_PEDIDO_VTA_CAB C,
               VTA_PEDIDO_VTA_DET D
          WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND C.COD_LOCAL = cCodLocal_in
                --AND C.FEC_PED_VTA BETWEEN TRUNC(SYSDATE) AND SYSDATE
                AND C.EST_PED_VTA = 'C'
                AND D.IND_CALCULO_MAX_MIN = 'N'
                AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                AND C.COD_LOCAL = D.COD_LOCAL
                AND C.NUM_PED_VTA = D.NUM_PED_VTA
            );
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_CANT_MAX(nCantSug_in IN NUMBER,nPorcAdic_in IN NUMBER,
  nCantMax_in IN NUMBER,nValFrac_in IN NUMBER,nStock_in IN NUMBER)
  RETURN NUMBER
  IS
    v_nCantMax LGT_PROD_LOCAL_REP.CANT_SUG%TYPE;
    v_nSugAux LGT_PROD_LOCAL_REP.CANT_SUG%TYPE;
    v_nMaxAux LGT_PROD_LOCAL_REP.CANT_SUG%TYPE;
  BEGIN
    IF nValFrac_in = 1 AND nStock_in = 0 AND nCantSug_in > 0 THEN
        v_nSugAux := CEIL(nCantSug_in*((100+nPorcAdic_in)/100));
        v_nMaxAux := nCantSug_in + nCantMax_in;
        IF v_nSugAux > v_nMaxAux THEN
          v_nCantMax := v_nSugAux;
        ELSE
          v_nCantMax := v_nMaxAux;
        END IF;

    ELSIF nCantSug_in < 0 THEN
      v_nCantMax := 0;
    ELSE
      v_nCantMax := nCantSug_in;
    END IF;

    --ERIOS 19/12/2006: SI LA FRAC >= 20, EL CANT MAXIMA ES LA CANT SUGERIDA.
    /*IF v_nSugAux > v_nMaxAux OR nValFrac_in >= 20 THEN
      v_nCantMax := v_nSugAux;
    ELSE
      v_nCantMax := v_nMaxAux;
    END IF;*/

    RETURN v_nCantMax;
  END;
  /*****************************************************************************/
  PROCEDURE INV_GENERA_PED_AUTO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cIdUsu_in       IN CHAR)
  AS
    nCantItems_in LGT_PED_REP_CAB.CANT_ITEMS%TYPE := 1;
    nCantProds_in LGT_PED_REP_CAB.CANT_PROD%TYPE := 1;

    v_cNumPedRep LGT_PED_REP_CAB.NUM_PED_REP%TYPE;
  BEGIN
    INV_CALCULA_MAX_MIN(cCodGrupoCia_in,cCodLocal_in);

    INV_GENERAR_PED_REP(cCodGrupoCia_in,cCodLocal_in,nCantItems_in,nCantProds_in,cIdUsu_in);
    COMMIT;

    SELECT TRIM(MAX(NUM_PED_REP))
      INTO v_cNumPedRep
    FROM LGT_PED_REP_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    UPDATE LGT_PED_REP_CAB C
    SET (CANT_ITEMS,CANT_PROD) = (SELECT COUNT(COD_PROD), SUM(CANT_SOLICITADA)
                                  FROM LGT_PED_REP_DET
                                  WHERE COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                        AND COD_LOCAL = C.COD_LOCAL
                                        AND NUM_PED_REP = C.NUM_PED_REP)
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL = cCodLocal_in
        AND NUM_PED_REP = v_cNumPedRep
    ;
    COMMIT;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_STK_TRANS_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN NUMBER
  IS
    v_nTrans LGT_PROD_LOCAL_REP.CANT_TRANSITO%TYPE;
  BEGIN
    --14/05/2007 ERIOS Si es matriz, obtiene el stock de la cadena.
    IF cCodLocal_in = g_cCodMatriz THEN
      /*SELECT NVL(SUM(CANT_ENVIADA_MATR),0) INTO v_nTrans
      FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            --AND C.COD_LOCAL = cCodLocal_in
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
            AND D.COD_PROD = cCodProd_in
            AND C.TIP_NOTA_ES = g_cTipoNotaRecepcion
            AND D.IND_PROD_AFEC = 'N';*/
      --v_nTrans := 0;
      SELECT NVL(SUM(STK_TRANSITO),0) INTO v_nTrans
      FROM AUX_STK_TRANSITO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProd_in;
    ELSE
      SELECT NVL(SUM(CANT_ENVIADA_MATR),0) INTO v_nTrans
      FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
            AND D.COD_PROD = cCodProd_in
            AND C.TIP_NOTA_ES = g_cTipoNotaRecepcion
            AND D.IND_PROD_AFEC = 'N';
    END IF;
    RETURN v_nTrans;
  END;
  /*****************************************************************************/
  PROCEDURE INV_CALCULA_RESUMEN_VENTAS_M(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2)
  AS
    v_dFecha DATE;
    CURSOR curCambio IS
    SELECT C.COD_PROD_ANT,C.COD_PROD_NUE
    FROM LGT_CAMBIO_PROD C, VTA_RES_VTA_ACUM_LOCAL A
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
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
       AND C.EST_CAMBIO_PROD = 'A'
       AND A.CANT_UNID_VTA <> 0
       AND A.FEC_DIA_VTA BETWEEN TRUNC(v_dFecha - 90) AND TRUNC(v_dFecha)
       AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
       AND A.COD_PROD = C.COD_PROD_ANT
    ORDER BY C.FEC_INI_CAMBIO_PROD;
    rowHistorico curHistorico%ROWTYPE;
    --28/08/2007 ERIOS Cambio Prod para el resumen por prod, mes y local.
    CURSOR curHistoricoMesLoc IS
    SELECT A.COD_LOCAL,C.COD_PROD_ANT, C.COD_PROD_NUE, A.FEC_DIA_VTA
      FROM VTA_RES_VTA_PROD_MES_LOC A, LGT_CAMBIO_PROD C
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.EST_CAMBIO_PROD = 'A'
       AND A.CANT_UNID_VTA <> 0
       AND A.FEC_DIA_VTA = TRUNC(SYSDATE-1,'MM')
       AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
       AND A.COD_PROD = C.COD_PROD_ANT
    ORDER BY C.FEC_INI_CAMBIO_PROD;

  BEGIN
    v_dFecha := TO_DATE(vFecha_in,'dd/MM/yyyy');

    --17/07/2007  ERIOS  El resumen se hace al traer las ventas.
    --VTA_RES_VTA_PROD_LOCAL
    /*DELETE VTA_RES_VTA_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND FEC_DIA_VTA = v_dFecha
          AND COD_LOCAL = cCodLocal_in;
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_VTA_PROD_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          --AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0 AND SUM(VAL_PREC_TOTAL) != 0;*/

    --17/07/2007  ERIOS  El resumen se hace al traer las ventas.
    --VTA_RES_VTA_REP_LOCAL
    /*DELETE VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND FEC_DIA_VTA = v_dFecha AND COD_LOCAL = cCodLocal_in;
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    INSERT INTO VTA_RES_VTA_REP_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          --AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND C.TIP_PED_VTA IN ('01','02')
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0;*/

    --VTA_RES_VTA_ACUM_LOCAL
    DELETE VTA_RES_VTA_ACUM_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND FEC_DIA_VTA = v_dFecha
          AND COD_LOCAL = cCodLocal_in;
    --DBMS_OUTPUT.PUT_LINE(vFecha_in);

    --17/07/2007  ERIOS  El resumen se hace por todas las ventas.
    INSERT INTO VTA_RES_VTA_ACUM_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
    CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
    SELECT C.COD_GRUPO_CIA,cCodLocal_in,COD_PROD,v_dFecha,
           SUM(CANT_ATENDIDA/VAL_FRAC) AS CANT_ATENDIDA,
           SUM(VAL_PREC_TOTAL) AS VAL_PREC_TOTAL,
           SYSDATE
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          --AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          --AND C.TIP_PED_VTA IN ('01','02')
          AND TRUNC(C.FEC_PED_VTA) = v_dFecha
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
    GROUP BY C.COD_GRUPO_CIA,COD_PROD--C.COD_LOCAL,
    HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0;

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

    --28/08/2007 ERIOS Mueve res vta prod, mes y local
    FOR rowHistoricoMesLoc IN curHistoricoMesLoc
    LOOP
      REP_MOVER_STK_MES_LOC(cCodGrupoCia_in, rowHistoricoMesLoc.COD_LOCAL,
      rowHistoricoMesLoc.COD_PROD_ANT, rowHistoricoMesLoc.COD_PROD_NUE, rowHistoricoMesLoc.FEC_DIA_VTA);
    END LOOP;
    COMMIT;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_CANT_DIAS_EFECTIVO_M(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cCodProd_in IN CHAR,
                                      cPeriodo_in IN CHAR,
                                      nRotProd_in IN NUMBER)
    RETURN NUMBER
  IS
    v_nCantDiasVta NUMBER;
    v_Periodo NUMBER(1) := TO_NUMBER(cPeriodo_in);
  BEGIN
       SELECT COUNT(V5.FECHA)
       INTO   v_nCantDiasVta
       FROM   (SELECT V1.FECHA,
                      NVL(V2.CANTIDAD, 0) CANT_1,
                      NVL(V3.CANTIDAD, 0) CANT_2
               FROM   (SELECT DISTINCT D.FEC_DIA_VTA FECHA
                       --FROM   VTA_RES_VTA_REP_LOCAL D
                       FROM   VTA_RES_VTA_ACUM_LOCAL D
                       WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND    D.COD_LOCAL = cCodLocal_in
                       --AND    TRUNC((TRUNC(SYSDATE) - D.FEC_DIA_VTA) / ) = cPeriodo_in
                       AND D.FEC_DIA_VTA BETWEEN TRUNC(SYSDATE - (nRotProd_in*(1+v_Periodo)))+1 AND (SYSDATE - (nRotProd_in*v_Periodo))
                       ) V1,
                      (SELECT A.FEC_DIA_VTA FECHA,
                              A.CANT_UNID_VTA CANTIDAD
                       FROM   VTA_RES_VTA_ACUM_LOCAL A
                       --FROM   VTA_RES_VTA_REP_LOCAL A
                       WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND    A.COD_LOCAL = cCodLocal_in
                       AND    A.COD_PROD = cCodProd_in
                       --AND    TRUNC((TRUNC(SYSDATE) - A.FEC_DIA_VTA) / nRotProd_in) = cPeriodo_in
                       AND A.FEC_DIA_VTA BETWEEN TRUNC(SYSDATE - (nRotProd_in*(1+v_Periodo)))+1 AND (SYSDATE - (nRotProd_in*v_Periodo))
                       ) V2,
                      (SELECT B.FEC_STK FECHA,
                              B.CANT_STK CANTIDAD
                       FROM   LGT_HIST_STK_LOCAL B
                       WHERE  B.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND    B.COD_LOCAL = cCodLocal_in
                       AND    B.COD_PROD = cCodProd_in
                       --AND    TRUNC((TRUNC(SYSDATE) - B.FEC_STK) / nRotProd_in) = cPeriodo_in
                       AND B.FEC_STK BETWEEN TRUNC(SYSDATE - (nRotProd_in*(1+v_Periodo)))+1 AND (SYSDATE - (nRotProd_in*v_Periodo))
                       --GROUP BY B.FEC_STK
                       ) V3
               WHERE  V1.FECHA = V2.FECHA(+)
               AND    V1.FECHA = V3.FECHA(+)) V5
       WHERE  V5.CANT_1 + V5.CANT_2 > 0;
    RETURN v_nCantDiasVta;
  END;
  /*****************************************************************************/
   PROCEDURE INV_SET_CANT_ADIC_TMP(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   nCantTmp_in     IN NUMBER,
                                   cIdUsu_in       IN VARCHAR2)
  AS
  BEGIN
    UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = cIdUsu_in,FEC_MOD_PROD_LOCAL_REP = SYSDATE,
           CANT_ADIC = nCantTmp_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND COD_PROD = cCodProd_in;

  END;

  /*****************************************************************************/

  FUNCTION INV_GET_DET_REP_VER(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNroPedido_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
    /*SELECT D.COD_PROD        || 'Ã' ||
           P.DESC_PROD       || 'Ã' ||
           P.DESC_UNID_PRESENT   || 'Ã' ||
           TO_CHAR(D.CANT_SOLICITADA,'99,990') || 'Ã' ||
           TO_CHAR(D.CANT_SUGERIDA,'99,990') || 'Ã' ||
           TO_CHAR(D.STK_DISPONIBLE/D.VAL_FRAC_PROD ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_DIA_ROT*D.VAL_ROT_PROD ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_MIN_STK ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_MAX_STK ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_DIA_ROT ,'999,990')  || 'Ã' ||
           TO_CHAR(D.NUM_DIAS ,'999,990')  || 'Ã' ||
           TO_CHAR(D.CANT_EXHIB ,'999,990')  || 'Ã' ||
           A.NOM_LAB         || 'Ã' ||
           TO_CHAR(D.STK_TRANSITO ,'999,990')   || 'Ã' ||
           TO_CHAR(D.VAL_ROT_PROD,'999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
           P.IND_TIPO_PROD || 'Ã' ||
           nvl(TO_CHAR(D.Cant_Adic,'999,990.00'),'0')*/

    --Modificado por DVELIZ 16.09.08
    SELECT D.COD_PROD        || 'Ã' ||
           P.DESC_PROD       || 'Ã' ||
           P.DESC_UNID_PRESENT   || 'Ã' ||
           TO_CHAR(D.CANT_SOLICITADA,'99,990') || 'Ã' ||
           --TO_CHAR(D.CANT_SUGERIDA,'99,990') || 'Ã' ||
           TO_CHAR(D.STK_DISPONIBLE/D.VAL_FRAC_PROD ,'999,990.00')   || 'Ã' ||
           --trunc(d.stk_disponible*(p.val_max_frac/d.val_frac_prod)/p.val_max_frac) ||'/'|| mod(d.stk_disponible*(p.val_max_frac/d.val_frac_prod),p.val_max_frac) || 'Ã' ||
           --TO_CHAR(D.CANT_DIA_ROT*D.VAL_ROT_PROD ,'999,990')   || 'Ã' ||
           --TO_CHAR(D.CANT_MIN_STK ,'999,990')   || 'Ã' ||
           --TO_CHAR(D.CANT_MAX_STK ,'999,990')   || 'Ã' ||
           TO_CHAR(d.CANT_MAX_STK ,'999,990')   || 'Ã' || --pvm
           --TO_CHAR(D.CANT_DIA_ROT ,'999,990')  || 'Ã' ||
           --TO_CHAR(D.NUM_DIAS ,'999,990')  || 'Ã' ||
           TO_CHAR(D.CANT_EXHIB ,'999,990')  || 'Ã' ||
           A.NOM_LAB         || 'Ã' ||
           TO_CHAR(D.STK_TRANSITO ,'999,990')   || 'Ã' ||
           --TO_CHAR(D.VAL_ROT_PROD,'999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
           P.IND_TIPO_PROD || 'Ã' ||
           nvl(TO_CHAR(D.Cant_Adic,'999,990.00'),'0')|| 'Ã' ||
           TO_CHAR(ADD_MONTHS(C.FEC_CREA_PED_REP_CAB,-0),'MON') || 'Ã' ||
           TO_CHAR(ADD_MONTHS(C.FEC_CREA_PED_REP_CAB,-1),'MON')|| 'Ã' ||
           TO_CHAR(ADD_MONTHS(C.FEC_CREA_PED_REP_CAB,-2),'MON')|| 'Ã' ||
           TO_CHAR(ADD_MONTHS(C.FEC_CREA_PED_REP_CAB,-3),'MON')
    FROM LGT_PED_REP_DET D,
		 LGT_PED_REP_CAB C,
		 LGT_PROD_LOCAL L,
		 LGT_PROD P,
		 LGT_LAB  A,
     LGT_PROD_LOCAL_REP B
    WHERE C.COD_GRUPO_CIA     = cCodGrupoCia_in
          AND C.COD_LOCAL     = cCodLocal_in
          AND C.NUM_PED_REP   = cNroPedido_in
          AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL     = D.COD_LOCAL
          AND C.NUM_PED_REP   = D.NUM_PED_REP
          AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND D.COD_LOCAL     = L.COD_LOCAL
          AND D.COD_PROD      = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD      = P.COD_PROD
          AND P.COD_LAB       = A.COD_LAB
          --JCORTEZ 16/10/2008
          AND D.COD_GRUPO_CIA=B.COD_GRUPO_CIA(+)
          AND D.COD_LOCAL=B.COD_LOCAL(+)
          AND D.COD_PROD=B.COD_PROD(+);

    RETURN curProd;
  END;

  /* ************************************************************************ */

  FUNCTION INV_GET_DET_REP_FILTRO(cCodGrupoCia_in IN CHAR,
  		   					                cCodLocal_in    IN CHAR,
							                    cNroPedido_in   IN CHAR,
                                  cCodFiltro_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
    SELECT D.COD_PROD        || 'Ã' ||
           P.DESC_PROD       || 'Ã' ||
           P.DESC_UNID_PRESENT   || 'Ã' ||
           TO_CHAR(D.CANT_SOLICITADA,'99,990') || 'Ã' ||
           --TO_CHAR(D.CANT_SUGERIDA,'99,990') || 'Ã' ||
           TO_CHAR(D.STK_DISPONIBLE/D.VAL_FRAC_PROD ,'999,990')   || 'Ã' ||
           --TO_CHAR(D.CANT_DIA_ROT*D.VAL_ROT_PROD ,'999,990')   || 'Ã' ||
           --TO_CHAR(D.CANT_MIN_STK ,'999,990')   || 'Ã' ||
           --TO_CHAR(D.CANT_MAX_STK ,'999,990')   || 'Ã' ||
           TO_CHAR(B.CANT_MAX_STK ,'999,990')   || 'Ã' ||
           --TO_CHAR(D.CANT_DIA_ROT ,'999,990')  || 'Ã' ||
           --TO_CHAR(D.NUM_DIAS ,'999,990')  || 'Ã' ||
           TO_CHAR(D.CANT_EXHIB ,'999,990')  || 'Ã' ||
           A.NOM_LAB         || 'Ã' ||
           TO_CHAR(D.STK_TRANSITO ,'999,990')   || 'Ã' ||
           --TO_CHAR(D.VAL_ROT_PROD,'999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
           P.IND_TIPO_PROD  || 'Ã' ||
           nvl(TO_CHAR(D.Cant_Adic,'999,990.00'),'0') || 'Ã' ||
           TO_CHAR(ADD_MONTHS(C.FEC_CREA_PED_REP_CAB,-0),'MON') || 'Ã' ||
           TO_CHAR(ADD_MONTHS(C.FEC_CREA_PED_REP_CAB,-1),'MON')|| 'Ã' ||
           TO_CHAR(ADD_MONTHS(C.FEC_CREA_PED_REP_CAB,-2),'MON')|| 'Ã' ||
           TO_CHAR(ADD_MONTHS(C.FEC_CREA_PED_REP_CAB,-3),'MON')
    FROM   LGT_PED_REP_DET D,
		       LGT_PED_REP_CAB C,
		       LGT_PROD_LOCAL L,
		       LGT_PROD P,
		       LGT_LAB  A,
           LGT_PROD_LOCAL_REP B
    WHERE  C.COD_GRUPO_CIA     = cCodGrupoCia_in
    AND    C.COD_LOCAL     = cCodLocal_in
    AND    C.NUM_PED_REP   = cNroPedido_in
    AND    A.COD_LAB    = ccodfiltro_in
    AND    C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    C.COD_LOCAL     = D.COD_LOCAL
    AND    C.NUM_PED_REP   = D.NUM_PED_REP
    AND    D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
    AND    D.COD_LOCAL     = L.COD_LOCAL
    AND    D.COD_PROD      = L.COD_PROD
    AND    L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
    AND    L.COD_PROD      = P.COD_PROD
    AND    P.COD_LAB       = A.COD_LAB
    --JCORTEZ 16/10/2008
    AND D.COD_GRUPO_CIA=B.COD_GRUPO_CIA(+)
    AND D.COD_LOCAL=B.COD_LOCAL(+)
    AND D.COD_PROD=B.COD_PROD(+);
    RETURN curProd;
  END;

  /****************************************************************************/
  FUNCTION INV_GET_ULTIMO_PED_REP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN CHAR
  IS
    v_cNumPed LGT_PED_REP_CAB.NUM_PED_REP%TYPE;
  BEGIN
    SELECT NVL(MAX(NUM_PED_REP) ,'X')
      INTO v_cNumPed
    FROM LGT_PED_REP_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_CREA_PED_REP_CAB BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE)+0.9999;
    RETURN v_cNumPed;
  END;

  /* ************************************************************************ */

  PROCEDURE INV_SET_CANT_PEDREP_TMP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR,nCantTmp_in IN NUMBER,cIdUsu_in IN VARCHAR2)
  AS
  BEGIN
    UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = cIdUsu_in,FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        CANT_SOL = nCantTmp_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;

    --COMMIT;
  END;

  /* ************************************************************************ */

  FUNCTION INV_GET_CAB_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCab FarmaCursor;
  BEGIN
    OPEN curCab FOR
    SELECT NUM_DIAS_ROT || 'Ã' ||
           NUM_MIN_DIAS_REP || 'Ã' ||
           NUM_MAX_DIAS_REP
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
    RETURN curCab;
  END;

  /* ************************************************************************ */

  FUNCTION INV_GET_ULT_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPed FarmaCursor;
  BEGIN
    OPEN curPed FOR
    SELECT NVL(CANT_ITEMS,0) || 'Ã' ||
           NVL(CANT_PROD,0)
    FROM LGT_PED_REP_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_REP = (SELECT MAX(NUM_PED_REP)FROM LGT_PED_REP_CAB
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL = cCodLocal_in);
    RETURN curPed;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_CANT_ANT_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vCant NUMBER(6):=0;
  BEGIN
    BEGIN
      /*SELECT D.CANT_SOLICITADA INTO v_vCant
      FROM LGT_PED_REP_CAB C, LGT_PED_REP_DET D
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND D.COD_PROD = cCodProd_in
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_PED_REP = D.NUM_PED_REP
            AND ROWNUM = 1
      ORDER BY D.NUM_PED_REP DESC;*/
      --30/03/2007 ERIOS
      SELECT D.CANT_SOLICITADA INTO v_vCant
                FROM LGT_PED_REP_DET D
                WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND D.COD_LOCAL = cCodLocal_in
                      AND D.COD_PROD = cCodProd_in
                      AND (D.NUM_PED_REP) =
                      (SELECT MAX(D.NUM_PED_REP)
                      FROM LGT_PED_REP_DET D
                      WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND D.COD_LOCAL = cCodLocal_in
                            AND D.COD_PROD = cCodProd_in
                       GROUP BY D.COD_GRUPO_CIA,D.COD_LOCAL,D.COD_PROD
                            );
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_vCant:=0;
    END;
    RETURN v_vCant || '';
  END;
  /*****************************************************************************/

  FUNCTION INV_GET_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN CHAR
  IS
    v_cInd CHAR(1);
    v_cDiaGenera CHAR(2);
    v_cDiaHoy CHAR(2);
  BEGIN
    SELECT NVL(TO_CHAR(FEC_GENERA_MAX_MIN,'dd'),'00'),TO_CHAR(SYSDATE,'dd')
      INTO v_cDiaGenera, v_cDiaHoy
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    IF v_cDiaGenera <> v_cDiaHoy THEN
      UPDATE PBL_LOCAL SET USU_MOD_LOCAL = 'PK_CAL_REP', FEC_MOD_LOCAL = SYSDATE,
            IND_PED_REP = 'N'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
    END IF;

    SELECT IND_PED_REP INTO v_cInd
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    RETURN v_cInd;
  END;

  /********************************************************************************/

  FUNCTION  INV_OBTIENE_FECHA_REPOSICION(cCodGrupoCia_in 	  IN CHAR,
                                         cCodLocal_in    	  IN CHAR)
  RETURN VARCHAR2
  IS
  RETORNO VARCHAR2(30);
  BEGIN
       SELECT TO_CHAR(LOCAL.FEC_GENERA_MAX_MIN,'dd/MM/yyyy HH24:MI:SS') INTO RETORNO
       FROM   PBL_LOCAL LOCAL
       WHERE  LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    LOCAL.COD_LOCAL = cCodLocal_in;

    RETURN RETORNO ;
  END;

  /* ************************************************************************ */
  FUNCTION INV_OBTIENE_IND_STOCK(cCodProd_in IN CHAR)
  RETURN VARCHAR2
  IS
   v_vSentencia VARCHAR2(32767);
  BEGIN
        -- PRIMERO ES DE LOCALES, SEGUNDO ES DE ALMACEN
        EXECUTE IMMEDIATE ' SELECT CASE WHEN SUM(CASE WHEN COD_LOCAL = ''009'' THEN 0 ELSE 1 END) > 0 THEN ''S'' ELSE ''N'' END|| ''Ã'' ||' ||
                          '        CASE WHEN SUM(CASE WHEN COD_LOCAL = ''009'' THEN 1 ELSE 0 END) > 0 THEN ''S'' ELSE ''N'' END' ||
                          ' FROM LGT_PROD_LOCAL@XE_DEL_999 ' ||
                          ' WHERE COD_PROD = '''|| cCodProd_in ||''' ' ||
                          ' AND STK_FISICO > 0 ' INTO v_vSentencia ;

   dbms_output.put_line(v_vSentencia);

   RETURN V_VSENTENCIA ;
   END;

 /******************************************************************************/

  FUNCTION INV_LISTA_PEDIDO_REPOSICION(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cFecInicio_in   IN CHAR,
                                       cFecFin_in      IN CHAR,
                                       cTipoFiltro_in  IN CHAR)
  RETURN FarmaCursor
  IS
    curPedRep FarmaCursor;
     v_vSentencia VARCHAR2(32767);
  BEGIN
   --OPEN curPedRep FOR
 v_vSentencia:= ' SELECT NUM_PED_REP || ''Ã'' ||'||
                ' TO_CHAR(FEC_CREA_PED_REP_CAB,''dd/MM/yyyy HH24:MI:SS'') || ''Ã'' ||'||
                ' CANT_ITEMS || ''Ã'' ||'||
                ' R.NUM_MIN_DIAS_REP || ''Ã'' ||'||
                ' R.NUM_MAX_DIAS_REP || ''Ã'' ||'||
                ' R.NUM_DIAS_ROT'||
          ' FROM LGT_PED_REP_CAB R, PBL_LOCAL L'||
          ' WHERE R.COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' '||
                ' AND R.COD_LOCAL = '''||cCodLocal_in||''' '||
                ' AND R.COD_GRUPO_CIA = L.COD_GRUPO_CIA'||
                ' AND R.COD_LOCAL = L.COD_LOCAL';
                --' AND R.FEC_CREA_PED_REP_CAB BETWEEN TRUNC(SYSDATE-30)AND TRUNC(SYSDATE)';

     IF cTipoFiltro_in = 'S' THEN
     v_vSentencia:= v_vSentencia ||' '||  ' AND R.FEC_CREA_PED_REP_CAB BETWEEN TO_DATE('''||cFecInicio_in||''' || '' 00:00:00'',''DD/MM/YYYY HH24:MI:SS'')'||
                      ' AND     TO_DATE('''|| cFecFin_in ||'''|| '' 23:59:59'',''DD/MM/YYYY HH24:MI:SS'')';
     ELSIF cTipoFiltro_in = 'N' THEN
     v_vSentencia:= v_vSentencia ||' '||  ' AND R.FEC_CREA_PED_REP_CAB BETWEEN TRUNC(SYSDATE-30)AND TRUNC(SYSDATE)';
     END IF;

 OPEN curPedRep  FOR  v_vSentencia;
       RETURN curPedRep;
  END;

  /*****************************************************************************/

  FUNCTION INV_LISTA_PROD_REP_VER(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN

    OPEN curProd FOR
      /*SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND T.CANT_SOL IS NOT NULL
            --AND T.CANT_SOL > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
      UNION ALL
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND T.CANT_SOL IS NULL
            AND T.CANT_SUG > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;
      */
        SELECT P.COD_PROD                               || 'Ã' ||
               P.DESC_PROD                              || 'Ã' ||
               P.DESC_UNID_PRESENT                      || 'Ã' ||
               TO_CHAR(T.CANT_MIN_STK,'999,990')        || 'Ã' ||
               TO_CHAR(T.CANT_MAX_STK,'999,990')        || 'Ã' ||
               TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
               DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
               TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
               DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))            || 'Ã' ||--SOL
               DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))          || 'Ã' ||--ADIC NUVA COLUMNA!
               B.NOM_LAB                                                            || 'Ã' ||
               L.CANT_EXHIB                                                         || 'Ã' ||
               T.CANT_TRANSITO                                                      || 'Ã' ||
               DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP)         || 'Ã' ||
               DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP)         || 'Ã' ||
               DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT)                 || 'Ã' ||
               TO_CHAR(T.CANT_ROT,'99,990.000')                                     || 'Ã' ||
               0                                                                    || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_0, '999,990.000')                             || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_1, '999,990.000')                             || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_2, '999,990.000')                             || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_3, '999,990.000')                             || 'Ã' ||
               L.VAL_FRAC_LOCAL                                                     || 'Ã' ||
               T.CANT_MAX_ADIC                                                      || 'Ã' ||
               P.IND_TIPO_PROD                                                      || 'Ã' ||
               NVL(T.IND_STK_LOCALES,' ')                                           || 'Ã' ||
               NVL(T.IND_STK_ALMACEN,' ')
        FROM   LGT_PROD P,
               LGT_PROD_LOCAL L,
               LGT_PROD_LOCAL_REP T,
               LGT_LAB B,
               LGT_GRUPO_REP_LOCAL G
        WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    L.COD_LOCAL     = cCodLocal_in
        AND    T.CANT_SOL      IS NOT NULL
        AND    L.IND_REPONER   = 'S'
        AND    P.EST_PROD      = 'A'
        AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
        AND    L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    L.COD_PROD      = P.COD_PROD
        AND    L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
        AND    L.COD_LOCAL     = T.COD_LOCAL
        AND    L.COD_PROD      = T.COD_PROD
        AND    P.COD_LAB       = B.COD_LAB
        AND    L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
        AND    L.COD_LOCAL     = G.COD_LOCAL
        AND    G.COD_GRUPO_REP = P.COD_GRUPO_REP
        UNION ALL
        SELECT P.COD_PROD                                     || 'Ã' ||
               P.DESC_PROD                                    || 'Ã' ||
               P.DESC_UNID_PRESENT                            || 'Ã' ||
               TO_CHAR(T.CANT_MIN_STK,'999,990')              || 'Ã' ||
               TO_CHAR(T.CANT_MAX_STK,'999,990')              || 'Ã' ||
               TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
               DECODE(L.IND_REPONER,'N','NO REPONER',' ')                                || 'Ã' ||
               TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990')      || 'Ã' ||
               DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
               DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
               B.NOM_LAB                                                                 || 'Ã' ||
               L.CANT_EXHIB                                                              || 'Ã' ||
               T.CANT_TRANSITO                                                           || 'Ã' ||
               DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP)              || 'Ã' ||
               DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP)              || 'Ã' ||
               DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT)                      || 'Ã' ||
               TO_CHAR(T.CANT_ROT,'99,990.000')                                          || 'Ã' ||
               0                                                                         || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_0, '999,990.000')                                  || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_1, '999,990.000')                                  || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_2, '999,990.000')                                  || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_3, '999,990.000')                                  || 'Ã' ||
               L.VAL_FRAC_LOCAL                                                          || 'Ã' ||
               T.CANT_MAX_ADIC                                                           || 'Ã' ||
               P.IND_TIPO_PROD                                                           || 'Ã' ||
               NVL(T.IND_STK_LOCALES,' ')                                                || 'Ã' ||
               NVL(T.IND_STK_ALMACEN,' ')
        FROM   LGT_PROD P,
               LGT_PROD_LOCAL L,
               LGT_PROD_LOCAL_REP T,
               LGT_LAB B,
               LGT_GRUPO_REP_LOCAL G
        WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   L.COD_LOCAL     = cCodLocal_in
        AND   T.CANT_SOL      IS NULL
        AND   T.CANT_SUG      > 0
        AND   L.IND_REPONER   = 'S'
        AND   P.EST_PROD      = 'A'
        AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
        AND   L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND   L.COD_PROD      = P.COD_PROD
        AND   L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
        AND   L.COD_LOCAL     = T.COD_LOCAL
        AND   L.COD_PROD      = T.COD_PROD
        AND   P.COD_LAB       = B.COD_LAB
        AND   L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
        AND   L.COD_LOCAL     = G.COD_LOCAL
        AND   G.COD_GRUPO_REP = P.COD_GRUPO_REP;
    RETURN curProd;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_PED_ACT_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPed FarmaCursor;
  BEGIN
    OPEN curPed FOR
    SELECT COUNT(*) || 'Ã' || NVL(SUM(CANT_SOL),0)
    FROM LGT_PROD_LOCAL_REP T, LGT_PROD_LOCAL L, LGT_PROD P
    WHERE CANT_SOL IS NOT NULL
          AND CANT_SOL > 0
          AND T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T.COD_LOCAL = cCodLocal_in
          AND L.IND_REPONER = 'S'
          AND P.EST_PROD = 'A'
          AND T.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND T.COD_LOCAL = L.COD_LOCAL
          AND T.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
    UNION ALL
    SELECT COUNT(*) || 'Ã' || NVL(SUM(CANT_SUG),0)
    FROM LGT_PROD_LOCAL_REP T, LGT_PROD_LOCAL L, LGT_PROD P
    WHERE CANT_SOL IS NULL
          AND CANT_SUG > 0
          AND T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T.COD_LOCAL = cCodLocal_in
          AND L.IND_REPONER = 'S'
          AND P.EST_PROD = 'A'
          AND T.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND T.COD_LOCAL = L.COD_LOCAL
          AND T.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD;
    RETURN curPed;
  END;

  /*****************************************************************************/
  FUNCTION INV_LISTA_PROD_REP_VER_FILTRO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipoFiltro_in IN CHAR, cCodFiltro_in IN CHAR)
    RETURN FarmaCursor
    IS
      curProd FarmaCursor;
    BEGIN

      IF(cTipoFiltro_in = g_nTipoFiltroPrincAct) THEN --principio activo
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G,
           LGT_PRINC_ACT_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_PRINC_ACT = cCodFiltro_in
            AND T.CANT_SOL IS NOT NULL
            --AND T.CANT_SOL > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND A.COD_PROD = P.COD_PROD
      UNION ALL
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G,
           LGT_PRINC_ACT_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_PRINC_ACT = cCodFiltro_in
            AND T.CANT_SOL IS NULL
            AND T.CANT_SUG > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND A.COD_PROD = P.COD_PROD;

      ELSIF(cTipoFiltro_in = g_nTipoFiltroAccTerap) THEN --accion terapeutica
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G,
           LGT_ACC_TERAP_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_ACC_TERAP = cCodFiltro_in
            AND T.CANT_SOL IS NOT NULL
            --AND T.CANT_SOL > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND A.COD_PROD = P.COD_PROD
      UNION ALL
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G,
           LGT_ACC_TERAP_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_ACC_TERAP = cCodFiltro_in
            AND T.CANT_SOL IS NULL
            AND T.CANT_SUG > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND A.COD_PROD = P.COD_PROD;

      ELSIF(cTipoFiltro_in = g_nTipoFiltroLab) THEN --laboratorio
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND B.COD_LAB = cCodFiltro_in
            AND T.CANT_SOL IS NOT NULL
            --AND T.CANT_SOL > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
      UNION ALL
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND B.COD_LAB = cCodFiltro_in
            AND T.CANT_SOL IS NULL
            AND T.CANT_SUG > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;

      END IF;
      RETURN curProd;
    END;
  /*****************************************************************************/

  FUNCTION INV_LISTA_PROD_REP_VER_ADIC(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
       OPEN curProd FOR
          SELECT P.COD_PROD                            || 'Ã' ||
                 P.DESC_PROD                           || 'Ã' ||
                 P.DESC_UNID_PRESENT                   || 'Ã' ||
                 TO_CHAR(T.CANT_MIN_STK,'999,990')     || 'Ã' ||
                 TO_CHAR(T.CANT_MAX_STK,'999,990')     || 'Ã' ||
                 TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
                 DECODE(L.IND_REPONER,'N','NO REPONER',' ')                           || 'Ã' ||
                 TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
                 DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))            || 'Ã' ||--SOL
                 DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))          || 'Ã' ||--ADIC
                 B.NOM_LAB                                                            || 'Ã' ||
                 L.CANT_EXHIB                                                         || 'Ã' ||
                 T.CANT_TRANSITO                                                      || 'Ã' ||
                 DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP)         || 'Ã' ||
                 DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP)         || 'Ã' ||
                 DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT)                 || 'Ã' ||
                 TO_CHAR(T.CANT_ROT,'99,990.000')                                     || 'Ã' ||
                 0                                                                    || 'Ã' ||
                 TO_CHAR(T.CANT_VTA_PER_0, '999,990.000')                             || 'Ã' ||
                 TO_CHAR(T.CANT_VTA_PER_1, '999,990.000')                             || 'Ã' ||
                 TO_CHAR(T.CANT_VTA_PER_2, '999,990.000')                             || 'Ã' ||
                 TO_CHAR(T.CANT_VTA_PER_3, '999,990.000')                             || 'Ã' ||
                 L.VAL_FRAC_LOCAL                                                     || 'Ã' ||
                 T.CANT_MAX_ADIC                                                      || 'Ã' ||
                 P.IND_TIPO_PROD                                                      || 'Ã' ||
                 NVL(T.IND_STK_LOCALES,' ')                                           || 'Ã' ||
                 NVL(T.IND_STK_ALMACEN,' ')
          FROM   LGT_PROD P,
                 LGT_PROD_LOCAL L,
                 LGT_PROD_LOCAL_REP T,
                 LGT_LAB B,
                 LGT_GRUPO_REP_LOCAL G
          WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    L.COD_LOCAL     = cCodLocal_in
          AND    T.CANT_ADIC     IS NOT NULL
          AND    L.IND_REPONER   = 'S'
          AND    P.EST_PROD      = 'A'
          ------------------------------------------
          --26.09.2007  DUBILLUZ  MODIFICACION
          AND    T.CANT_ADIC > 0
          AND    L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND    L.COD_PROD      = P.COD_PROD
          AND    L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
          AND    L.COD_LOCAL     = T.COD_LOCAL
          AND    L.COD_PROD      = T.COD_PROD
          AND    P.COD_LAB       = B.COD_LAB
          AND    L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND    L.COD_LOCAL     = G.COD_LOCAL
          AND    G.COD_GRUPO_REP = P.COD_GRUPO_REP;
       RETURN curProd;
  END INV_LISTA_PROD_REP_VER_ADIC;

  /*****************************************************************************/

  FUNCTION INV_GET_PED_ACT_REP_ADIC(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPed FarmaCursor;
  BEGIN
    OPEN curPed FOR
        SELECT COUNT(*)                 || 'Ã' ||
               NVL(SUM(CANT_ADIC),0)
        FROM   LGT_PROD_LOCAL_REP T,
               LGT_PROD_LOCAL L, LGT_PROD P
        WHERE  CANT_ADIC         IS NOT NULL
        AND    CANT_ADIC         > 0
        AND    T.COD_GRUPO_CIA   = cCodGrupoCia_in
        AND    T.COD_LOCAL       = cCodLocal_in
        AND    L.IND_REPONER     = 'S'
        AND    P.EST_PROD        = 'A'
        AND    T.COD_GRUPO_CIA   = L.COD_GRUPO_CIA
        AND    T.COD_LOCAL       = L.COD_LOCAL
        AND    T.COD_PROD        = L.COD_PROD
        AND    L.COD_GRUPO_CIA   = P.COD_GRUPO_CIA
        AND    L.COD_PROD        = P.COD_PROD;
    RETURN curPed;
  END;
  /*****************************************************************************/

  FUNCTION INV_LISTA_PROD_REP_STK_CERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    --RECUPERA LOS PRODUCTOS
    OPEN curProd FOR
    SELECT P.COD_PROD || 'Ã' ||
           T.TIPO  || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND P.COD_PROD IN (SELECT DISTINCT COD_PROD
                              FROM LGT_PROD_LOCAL_FALTA_STK
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND COD_LOCAL = cCodLocal_in
                                  AND FEC_GENERA_PED_REP IS NULL)
          ;
    RETURN curProd;
  END;
  /****************************************************************************/
  FUNCTION INV_LISTA_PROD_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
    RETURN FarmaCursor
    IS
      curProd FarmaCursor;
      --v_cPorcAdic PBL_LOCAL.PORC_ADIC_REP%TYPE;
      --v_nCantMax PBL_LOCAL.CANT_UNIDAD_MAX%TYPE;
    BEGIN
      /*SELECT PORC_ADIC_REP,CANT_UNIDAD_MAX
        INTO v_cPorcAdic,v_nCantMax
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;*/
     /* OPEN curProd FOR
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             INV_GET_DET_PROD_REP(cCodGrupoCia_in,cCodLocal_in,P.COD_PROD) || 'Ã' ||
             INV_GET_ROT_PROD_REP(cCodGrupoCia_in,cCodLocal_in,P.COD_PROD) || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC --INV_GET_CANT_MAX(T.CANT_SUG,v_cPorcAdic,v_nCantMax,L.VAL_FRAC_LOCAL)
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD;*/
      --30/03/2007 ERIOS Se modificó la consulta.
      OPEN curProd FOR
      SELECT P.COD_PROD || 'Ã' ||
             T.TIPO  || 'Ã' || -- dubilluz 10.07.2007
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;

      RETURN curProd;
  END;
  /*****************************************************************************/
  FUNCTION INV_LISTA_PROD_REP_FILTRO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipoFiltro_in IN CHAR, cCodFiltro_in IN CHAR)
    RETURN FarmaCursor
    IS
      curProd FarmaCursor;
    BEGIN
      --RECUPERA LOS PRODUCTOS
      IF(cTipoFiltro_in = g_nTipoFiltroPrincAct) THEN --principio activo
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             T.TIPO  || 'Ã' || -- dubilluz 10.07.2007
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B, LGT_GRUPO_REP_LOCAL G,
           LGT_PRINC_ACT_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_PRINC_ACT = cCodFiltro_in
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND A.COD_PROD = L.COD_PROD;
      ELSIF(cTipoFiltro_in = g_nTipoFiltroAccTerap) THEN --accion terapeutica
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             T.TIPO  || 'Ã' || -- dubilluz 10.07.2007
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B, LGT_GRUPO_REP_LOCAL G,
           LGT_ACC_TERAP_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_ACC_TERAP = cCodFiltro_in
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND A.COD_PROD = L.COD_PROD;
      ELSIF(cTipoFiltro_in = g_nTipoFiltroLab) THEN --laboratorio
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             T.TIPO  || 'Ã' || -- dubilluz 10.07.2007
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD || 'Ã' ||
             NVL(T.IND_STK_LOCALES,' ') || 'Ã' ||
             NVL(T.IND_STK_ALMACEN,' ')
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B, LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND B.COD_LAB = cCodFiltro_in
            AND P.EST_PROD = 'A'
            AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;
      END IF;
      RETURN curProd;
  END;
  /*****************************************************************************/
  PROCEDURE  INV_ACTUALIZA_IND_LINEA (cIdUsuario_in IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cCodGrupoCia_in IN CHAR)
--  RETURN VARCHAR2
  IS
--   v_valor VARCHAR2(3);
   v_time_1 TIMESTAMP;
   v_IndLinea CHAR(1);
   v_time_2 TIMESTAMP;
   V_RESULT INTERVAL DAY TO SECOND;
   V_TIME_ESTIMADO CHAR(20);
   --VARIABLE AÑADIDA
   --06.11.2007 DUBILLUZ MODIFICACION
   v_DATOS_LOCAL         Varchar2(3453);
  BEGIN
        SELECT IND_EN_LINEA INTO v_IndLinea
        FROM   PBL_LOCAL
        WHERE  COD_LOCAL = cCodLocal_in
        AND    COD_GRUPO_CIA = cCodGrupoCia_in ;

        SELECT LLAVE_TAB_GRAL INTO V_TIME_ESTIMADO
        FROM   PBL_TAB_GRAL
        WHERE  ID_TAB_GRAL = 76
        AND    COD_APL = 'PTO_VENTA'
        AND    COD_TAB_GRAL = 'REPOSICION';

        IF (v_IndLinea = 'S')THEN

         SELECT CURRENT_TIMESTAMP INTO v_time_1 FROM dual ;
         EXECUTE IMMEDIATE ' SELECT COD_LOCAL ' ||
                           ' FROM LGT_PROD_LOCAL@XE_DEL_999 ' ||
                           ' WHERE ROWNUM < 2 ';

         SELECT CURRENT_TIMESTAMP INTO v_time_2 FROM dual ;

         V_RESULT := v_time_2 - v_time_1 ;

         IF(TO_CHAR(V_RESULT) > TRIM(V_TIME_ESTIMADO)) THEN
           UPDATE PBL_LOCAL SET FEC_MOD_LOCAL = SYSDATE, USU_MOD_LOCAL = cIdUsuario_in,
                                IND_EN_LINEA = 'N'
           WHERE   COD_LOCAL = cCodLocal_in
           AND     COD_GRUPO_CIA = cCodGrupoCia_in ;

        --ENVIA MAIL
         dbms_output.put_line('envio correo');
        SELECT L.COD_LOCAL ||'-'||L.DESC_CORTA_LOCAL INTO v_DATOS_LOCAL
        FROM PBL_LOCAL L
        WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in AND
              L.COD_LOCAL     = cCodLocal_in;
        INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        ' INDICADOR EN LINEA ',
                                        'ALERTA',
                                        'SE ACTUALIZO A "N" EL INDICADOR EN LINEA EN EL LOCAL '||v_DATOS_LOCAL||'.' ||
                                        '<BR><L> LA LINEA ESTA LENTA O NO HAY LINEA CON MATRIZ </L></BR>' ||
                                        '<BR>EL TIEMPO DE DURACION DE LA CONEXION FUE DE  '||V_RESULT ||'  MILISEGUNDOS </BR>'||
                                        '<BR>TIEMPO ESTIMADO ' || V_TIME_ESTIMADO || '</BR>');



         END IF;
        END IF;
/*        EXECUTE IMMEDIATE ' SELECT COD_LOCAL ' ||
                          ' FROM LGT_PROD_LOCAL@XE_DEL_999 ' ||
                          ' WHERE ROWNUM < 2 ' INTO v_valor;
        IF(v_valor IS NOT NULL) THEN
           UPDATE PBL_LOCAL SET FEC_MOD_LOCAL = SYSDATE, USU_MOD_LOCAL = cIdUsuario_in,
                                IND_EN_LINEA = 'S'
           WHERE   COD_LOCAL = cCodLocal_in
           AND     COD_GRUPO_CIA = cCodGrupoCia_in;
        END IF ;
*/
        EXCEPTION
          WHEN OTHERS THEN
             dbms_output.put_line('se cayo');
           UPDATE PBL_LOCAL SET FEC_MOD_LOCAL = SYSDATE, USU_MOD_LOCAL = cIdUsuario_in,
                                IND_EN_LINEA = 'N'
           WHERE   COD_LOCAL = cCodLocal_in
           AND     COD_GRUPO_CIA = cCodGrupoCia_in ;
   END;


  /*****************************************************************************/

   FUNCTION INV_OBTIENE_IND_LINEA(cCodLocal_in  IN CHAR,
                                  cCodGrupoCia_in IN CHAR)
   RETURN VARCHAR2
   IS
     v_Retorno VARCHAR2(10);
     v_IndLinea CHAR(1);
     BEGIN
          SELECT IND_EN_LINEA INTO v_IndLinea
          FROM PBL_LOCAL
          WHERE COD_LOCAL = cCodLocal_in
          AND   COD_GRUPO_CIA = cCodGrupoCia_in ;

          IF(v_IndLinea = 'S') THEN
           v_Retorno := 'TRUE' ;
          ELSIF (v_IndLinea = 'N') THEN
           v_retorno := 'FALSE' ;
          END IF ;

    RETURN V_RETORNO;
   END;
   /*****************************************************************************/
   PROCEDURE REP_CALCULA_STOCK_M(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in  IN CHAR)
   AS
   BEGIN
     EXECUTE IMMEDIATE 'UPDATE LGT_PROD_LOCAL PL
     SET PL.STK_FISICO = (SELECT SUM(D.STK_FISICO/VAL_FRAC_LOCAL)
                         FROM LGT_PROD_LOCAL@XE_DEL_999 D,
                              PBL_LOCAL L
                          WHERE L.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                                AND D.COD_PROD = PL.COD_PROD
                                AND L.TIP_LOCAL IN (''V'',''A'')
                                AND L.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                AND L.COD_LOCAL = D.COD_LOCAL),
         PL.FEC_MOD_PROD_LOCAL = SYSDATE,
         PL.USU_MOD_PROD_LOCAL = ''PK_CAL_STK_M''
      WHERE PL.COD_GRUPO_CIA = :1
            AND PL.COD_LOCAL = :2
            AND EXISTS (SELECT 1
                         FROM LGT_PROD_LOCAL@XE_DEL_999 D
                          WHERE D.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                                AND D.COD_PROD = PL.COD_PROD)' USING cCodGrupoCia_in,cCodLocal_in;

     COMMIT;
   END;
   /*****************************************************************************/
   PROCEDURE REP_CALCULA_EXHIB_M(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in  IN CHAR)
   AS
   BEGIN
     UPDATE LGT_PROD_LOCAL PL
     SET PL.CANT_EXHIB = (SELECT SUM(CANT_EXHIB)
                          FROM LGT_PROD_LOCAL
                          WHERE COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                                AND COD_PROD = PL.COD_PROD),
         PL.FEC_MOD_PROD_LOCAL = SYSDATE,
         PL.USU_MOD_PROD_LOCAL = 'PK_CAL_EXH_M'
     WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
            AND PL.COD_LOCAL = cCodLocal_in
            AND EXISTS (SELECT 1
                          FROM LGT_PROD_LOCAL
                          WHERE COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                                AND COD_PROD = PL.COD_PROD);
     COMMIT;
   END;
  /*****************************************************************************/
  PROCEDURE REP_CALCULA_HIST_STK_M(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    vFecha_in IN VARCHAR2)
  AS
    v_dFecha DATE;
  BEGIN
    v_dFecha := TO_DATE(vFecha_in,'dd/MM/yyyy');

    DELETE LGT_HIST_STK_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_STK = v_dFecha;

    INSERT INTO LGT_HIST_STK_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_STK,
    CANT_STK,VAL_FRAC_PROD_LOCAL,USU_CREA_HIST_STK_LOCAL,STK_TRANSITO)
    SELECT COD_GRUPO_CIA,cCodLocal_in,COD_PROD,FEC_STK,
    SUM(SL.CANT_STK/SL.VAL_FRAC_PROD_LOCAL),1,'PK_CAL_HIST_STK',SUM(SL.STK_TRANSITO)
    FROM LGT_HIST_STK_LOCAL SL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL NOT IN (cCodLocal_in)
          AND FEC_STK = v_dFecha
    GROUP BY COD_GRUPO_CIA,COD_PROD,FEC_STK;

    COMMIT;
  END;
  /*****************************************************************************/
  PROCEDURE REP_CALCULA_TRANSITO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  AS
  BEGIN
    DELETE AUX_STK_TRANSITO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    IF cCodLocal_in = g_cCodMatriz THEN
      INSERT INTO AUX_STK_TRANSITO(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,STK_TRANSITO)
      SELECT D.COD_GRUPO_CIA,cCodLocal_in,D.COD_PROD,NVL(SUM(CANT_ENVIADA_MATR),0) AS STK_TRANSITO --INTO v_nTrans
      FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            --AND C.COD_LOCAL = cCodLocal_in
            AND C.TIP_NOTA_ES = g_cTipoNotaRecepcion
            AND D.IND_PROD_AFEC = 'N'
            --AND D.COD_PROD = R.COD_PROD
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
      GROUP BY D.COD_GRUPO_CIA,D.COD_PROD;
    ELSE
      INSERT INTO AUX_STK_TRANSITO(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,STK_TRANSITO)
      SELECT D.COD_GRUPO_CIA,D.COD_LOCAL,D.COD_PROD,NVL(SUM(CANT_ENVIADA_MATR),0) AS STK_TRANSITO --INTO v_nTrans
      FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.TIP_NOTA_ES = g_cTipoNotaRecepcion
            AND D.IND_PROD_AFEC = 'N'
            --AND D.COD_PROD = R.COD_PROD
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
      GROUP BY D.COD_GRUPO_CIA,D.COD_LOCAL,D.COD_PROD;
    END IF;

    COMMIT;
  END;

  /* ************************************************************************ */

  PROCEDURE REP_GRABA_INDC_STOCK(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsurio_in IN CHAR)
  IS
--     v_vSentencia VARCHAR2(32767);
  BEGIN
        EXECUTE IMMEDIATE
        ' INSERT INTO TMP_PBL_INDICADORES_STK ' ||
        ' (SELECT ' || '''' ||cCodGrupoCia_in ||''',' ||
        ' ''' || cCodLocal_in ||''',' ||
        '       cod_prod,'  ||
        '       CASE WHEN SUM(CASE WHEN COD_LOCAL = ''009'' THEN 0 ELSE 1 END) > 0 THEN ''S'' ELSE ''N''   END ,' ||
        '       CASE WHEN SUM(CASE WHEN COD_LOCAL = ''009'' THEN 1 ELSE 0 END) > 0 THEN ''S'' ELSE ''N''   END ,' ||
        '       SYSDATE , ' ||
        ' ''' || cIdUsurio_in ||''',' ||
        '       NULL, ' ||
        '       NULL '||
        ' FROM LGT_PROD_LOCAL@XE_DEL_999   ' ||
        ' WHERE COD_GRUPO_CIA = '''|| cCodGrupoCia_in ||''' ' ||
        ' AND STK_FISICO > 0 ' ||
--        ' AND   COD_LOCAL = '''|| cCodLocalDBLink||'''' ||
        ' GROUP BY cod_prod ' ||
        ' minus SELECT null,null,NULL,NULL,NULL,NULL,NULL,NULL,NULL FROM DUAL) ' ;

/*        COMMIT;

        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK ;
*/   END;

  /* ************************************************************************ */
  PROCEDURE REP_ACTUALIZA_INDS_STOCK_NULL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsurio_in IN CHAR)
  IS
  BEGIN
       UPDATE LGT_PROD_LOCAL_REP R SET R.FEC_MOD_PROD_LOCAL_REP = SYSDATE, R.USU_MOD_PROD_LOCAL_REP = cIdUsurio_in,
              R.IND_STK_LOCALES = NULL , R.IND_STK_ALMACEN = NULL
       WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    R.COD_LOCAL= cCodLocal_in ;
       --COMMIT ;

/*        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK ;
*/   END;
  /* ************************************************************************ */
  PROCEDURE REP_ACTUALIZA_INDS_STOCK(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsurio_in IN CHAR)
  IS
  BEGIN
       UPDATE LGT_PROD_LOCAL_REP R SET R.FEC_MOD_PROD_LOCAL_REP = SYSDATE, R.USU_MOD_PROD_LOCAL_REP = cIdUsurio_in,
              R.IND_STK_LOCALES = 'N', R.IND_STK_ALMACEN = 'N'
       WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    R.COD_LOCAL= cCodLocal_in  ;

       UPDATE LGT_PROD_LOCAL_REP R SET R.FEC_MOD_PROD_LOCAL_REP = SYSDATE, R.USU_MOD_PROD_LOCAL_REP = cIdUsurio_in,
              (R.IND_STK_LOCALES,R.IND_STK_ALMACEN) = (SELECT P.IND_STK_LOCALES,
                                                              P.IND_STK_ALMACEN
                                                       FROM   TMP_PBL_INDICADORES_STK P
                                                       WHERE  P.COD_GRUPO_CIA = R.COD_GRUPO_CIA
                                                       AND    P.COD_LOCAL = R.COD_LOCAL
                                                       AND    P.COD_PROD = R.COD_PROD)
       WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    R.COD_LOCAL= cCodLocal_in
       AND    EXISTS (SELECT 1
                      FROM   TMP_PBL_INDICADORES_STK P
                      WHERE  P.COD_GRUPO_CIA = R.COD_GRUPO_CIA
                      AND    P.COD_LOCAL = R.COD_LOCAL
                      AND    P.COD_PROD = R.COD_PROD);

/*       COMMIT ;

        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK ;
*/  END;

  PROCEDURE REP_PROCESO_IND_STOCK(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsurio_in IN CHAR)
  IS
  BEGIN
       REP_ACTUALIZA_INDS_STOCK_NULL(cCodGrupoCia_in,cCodLocal_in,cIdUsurio_in);
       EXECUTE IMMEDIATE 'TRUNCATE TABLE TMP_PBL_INDICADORES_STK' ;
       REP_GRABA_INDC_STOCK(cCodGrupoCia_in,cCodLocal_in,cIdUsurio_in);
       REP_ACTUALIZA_INDS_STOCK(cCodGrupoCia_in,cCodLocal_in,cIdUsurio_in);
       COMMIT;
        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK ;
   END;
  /*****************************************************************************/
  PROCEDURE REP_MOVER_STK_MES_LOC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProdAnt_in IN CHAR, cCodProdNue_in IN CHAR, dFecha_in IN DATE)
  AS
    v_nCant VTA_RES_VTA_REP_LOCAL.CANT_UNID_VTA%TYPE;
    v_nMonTot VTA_RES_VTA_REP_LOCAL.MON_TOT_VTA%TYPE;

  BEGIN
      SELECT CANT_UNID_VTA,MON_TOT_VTA
        INTO v_nCant,v_nMonTot
      FROM VTA_RES_VTA_PROD_MES_LOC
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProdAnt_in
            AND FEC_DIA_VTA = dFecha_in FOR UPDATE;
      BEGIN
        INSERT INTO VTA_RES_VTA_PROD_MES_LOC(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
        CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
        VALUES(cCodGrupoCia_in,cCodLocal_in,cCodProdNue_in,dFecha_in,
        v_nCant,v_nMonTot,SYSDATE);
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          UPDATE VTA_RES_VTA_PROD_MES_LOC
          SET CANT_UNID_VTA = CANT_UNID_VTA + v_nCant,
              MON_TOT_VTA = MON_TOT_VTA + v_nMonTot,
              FEC_CREA_VTA_PROD_LOCAL = SYSDATE
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND COD_PROD = cCodProdNue_in
                AND FEC_DIA_VTA = dFecha_in;
      END;

      UPDATE VTA_RES_VTA_PROD_MES_LOC
      SET CANT_UNID_VTA = 0,
          MON_TOT_VTA = 0,
          FEC_CREA_VTA_PROD_LOCAL = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProdAnt_in
            AND FEC_DIA_VTA = dFecha_in;
  END;
  /*****************************************************************************/

  PROCEDURE REP_ELIMINA_FECHA_RESUMEN(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cIndAutomatico_in IN CHAR)
  IS
    BEGIN
         /*DELETE FROM VTA_RES_VTA_ACUM_LOCAL
         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
         AND    COD_LOCAL = cCodLocal_in
         AND    FEC_DIA_VTA IN (
                                 SELECT DISTINCT TRUNC(K.FEC_KARDEX)
                                 FROM   LGT_KARDEX K,
                                        VTA_PEDIDO_VTA_DET D,
                                        LGT_MOT_KARDEX M
                                 WHERE  K.COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND    K.COD_LOCAL = cCodLocal_in
                                 AND    D.IND_CALCULO_MAX_MIN = 'N'
                                 AND    K.COD_MOT_KARDEX IN ('001','003','002')
                                 AND    K.FEC_KARDEX < TRUNC (SYSDATE)
                                 AND    K.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                 AND    K.COD_LOCAL = D.COD_LOCAL
                                 AND    K.COD_PROD = D.COD_PROD
                                 AND    K.COD_GRUPO_CIA = M.COD_GRUPO_CIA
                                 AND    K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
                                 );

          INSERT INTO VTA_RES_VTA_ACUM_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
          CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
          SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,TRUNC(C.FEC_PED_VTA),
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
                AND TRUNC(C.FEC_PED_VTA) IN (SELECT DISTINCT TRUNC(K.FEC_KARDEX)
                                             FROM   LGT_KARDEX K,
                                                    VTA_PEDIDO_VTA_DET D,
                                                    LGT_MOT_KARDEX M
                                             WHERE  K.COD_GRUPO_CIA = cCodGrupoCia_in
                                             AND    K.COD_LOCAL = cCodLocal_in
                                             AND    D.IND_CALCULO_MAX_MIN = 'N'
                                             AND    K.COD_MOT_KARDEX IN ('001','003','002')
                                             AND    K.FEC_KARDEX < TRUNC (SYSDATE)
                                             AND    K.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                             AND    K.COD_LOCAL = D.COD_LOCAL
                                             AND    K.COD_PROD = D.COD_PROD
                                             AND    K.COD_GRUPO_CIA = M.COD_GRUPO_CIA
                                             AND    K.COD_MOT_KARDEX = M.COD_MOT_KARDEX)
                AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                AND C.COD_LOCAL = D.COD_LOCAL
                AND C.NUM_PED_VTA = D.NUM_PED_VTA
          GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,TRUNC(C.FEC_PED_VTA)
          HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0;
         COMMIT;*/
 DELETE FROM VTA_RES_VTA_ACUM_LOCAL
         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
         AND    COD_LOCAL = cCodLocal_in
         AND    FEC_DIA_VTA IN (
                                 SELECT DISTINCT TRUNC(K.FEC_KARDEX)
                                 FROM   LGT_KARDEX K,
                                        VTA_PEDIDO_VTA_DET D,
                                        LGT_MOT_KARDEX M
                                 WHERE  K.COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND    K.COD_LOCAL = cCodLocal_in
                                 AND    D.IND_CALCULO_MAX_MIN = 'N'
                                 AND    K.COD_MOT_KARDEX IN ('001','003','002')
                                 and k.fec_kardex between d.fec_crea_ped_vta_det-1 and d.fec_crea_ped_vta_det+1
                                 AND K.COD_GRUPO_CIA  = D.COD_GRUPO_CIA
                                 AND K.COD_LOCAL      = D.COD_LOCAL
                                 AND K.COD_PROD       = D.COD_PROD
                                 AND K.COD_GRUPO_CIA  = M.COD_GRUPO_CIA
                                 AND K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
                                 and k.fec_kardex>sysdate-150
                                 );

          INSERT INTO VTA_RES_VTA_ACUM_LOCAL(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,FEC_DIA_VTA,
          CANT_UNID_VTA,MON_TOT_VTA,FEC_CREA_VTA_PROD_LOCAL)
          SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,TRUNC(C.FEC_PED_VTA),
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
                AND TRUNC(C.FEC_PED_VTA) IN (SELECT DISTINCT TRUNC(K.FEC_KARDEX)
                                             FROM   LGT_KARDEX K,
                                                    VTA_PEDIDO_VTA_DET D,
                                                    LGT_MOT_KARDEX M
                                             WHERE  K.COD_GRUPO_CIA = cCodGrupoCia_in
                                             AND    K.COD_LOCAL = cCodLocal_in
                                             AND    D.IND_CALCULO_MAX_MIN = 'N'
                                             AND    K.COD_MOT_KARDEX IN ('001','003','002')
                                             AND    K.FEC_KARDEX < TRUNC (SYSDATE)
                                             and k.fec_kardex between d.fec_crea_ped_vta_det-1 and d.fec_crea_ped_vta_det+1
                                             AND K.COD_GRUPO_CIA  = D.COD_GRUPO_CIA
                                             AND K.COD_LOCAL      = D.COD_LOCAL
                                             AND K.COD_PROD       = D.COD_PROD
                                             AND K.COD_GRUPO_CIA  = M.COD_GRUPO_CIA
                                             AND K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
                                             and k.fec_kardex>sysdate-150)
                AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                AND C.COD_LOCAL = D.COD_LOCAL
                AND C.NUM_PED_VTA = D.NUM_PED_VTA
          GROUP BY C.COD_GRUPO_CIA,C.COD_LOCAL,COD_PROD,TRUNC(C.FEC_PED_VTA)
          HAVING SUM(CANT_ATENDIDA/VAL_FRAC) != 0;
         COMMIT;
       EXCEPTION WHEN OTHERS THEN
          ROLLBACK ;

    END;

/*************************************************************************/

  --Descripcion: Obtiene el Tiempo Estimado de Consulta
  --Fecha       Usuario		    Comentario
  --13/09/2007  DUBILLUZ      Creación
  FUNCTION REP_GET_TIEMPOESTIMADO(cCodGrupoCia_in  IN CHAR)
                                           RETURN varchar2
   IS
     V_TIME_ESTIMADO varchar2(2121);
   BEGIN
    SELECT LLAVE_TAB_GRAL INTO V_TIME_ESTIMADO
    FROM   PBL_TAB_GRAL
    WHERE  ID_TAB_GRAL = 111
    AND    COD_APL = 'PTO_VENTA'
    AND    COD_TAB_GRAL = 'TIEMPO';
   return  V_TIME_ESTIMADO;
  END;

  --Descripcion: Actualiza el Ind
  --Fecha       Usuario		    Comentario
  --17/09/2007  dubilluz       creacion
  --25/09/2007  DUBILLUZ       MODIFICACION
  PROCEDURE  INV_ACTUALIZA_IND_LINEA(cIdUsuario_in IN CHAR,
                                     cCodLocal_in  IN CHAR,
                                     cCodGrupoCia_in IN CHAR,
                                     cIndActualizar_in IN CHAR,
                                     cTiempoConexion_in  IN CHAR)
 IS
  v_DATOS_LOCAL         Varchar2(3453);
 BEGIN
       UPDATE PBL_LOCAL
       SET FEC_MOD_LOCAL = SYSDATE,
           USU_MOD_LOCAL = cIdUsuario_in,
           IND_EN_LINEA = cIndActualizar_in
       WHERE   COD_LOCAL = cCodLocal_in
       AND     COD_GRUPO_CIA = cCodGrupoCia_in ;
    --enviando un correo al operador si se actualizo indLinea a N
       IF cIndActualizar_in = 'N' THEN
        --ENVIA MAIL
         dbms_output.put_line('envio correo');
        SELECT L.COD_LOCAL ||'-'||L.DESC_CORTA_LOCAL INTO v_DATOS_LOCAL
        FROM PBL_LOCAL L
        WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in AND
              L.COD_LOCAL     = cCodLocal_in;
        INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        ' INDICADOR EN LINEA ',
                                        'ALERTA',
                                        'SE ACTUALIZO A "N" EL INDICADOR EN LINEA EN EL LOCAL '||v_DATOS_LOCAL||'.' ||
                                        '<BR><L> LA LINEA ESTA LENTA O NO HAY LINEA CON MATRIZ </L></BR>' ||
                                        '<BR>EL TIEMPO DE DURACION DE LA CONEXION FUE DE  '||cTiempoConexion_in ||'  MILISEGUNDOS </BR>');
     END IF;

 END;

/*************************************************************************/

 --Descripcion: OBTIENE EL IND LINEA ACTUAL
 --Fecha       Usuario		    Comentario
 --24/09/2007  dubilluz       creacion
 FUNCTION  REP_GET_INDLINEA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in  IN CHAR) RETURN CHAR
 IS
  v_IndLinea CHAR(1);
 BEGIN
        SELECT IND_EN_LINEA INTO v_IndLinea
        FROM   PBL_LOCAL
        WHERE  COD_LOCAL = cCodLocal_in
        AND    COD_GRUPO_CIA = cCodGrupoCia_in ;

  RETURN v_IndLinea;
 END;

  --Envia Correo
  --25/09/2007  dubilluz  creacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2)
  AS
    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_IND_LINEA;
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(32767);

    v_vDescLocal VARCHAR2(120);
  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in ||
                          '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                          ReceiverAddress,
                          vAsunto_in||v_vDescLocal,
                          vTitulo_in,
                          mesg_body,
                          CCReceiverAddress,
                          FARMA_EMAIL.GET_EMAIL_SERVER,
                          true);


  END;

  /***************************************************************************/
  FUNCTION DIST_OBTIENE_PROD_REVISION(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNroPedido_in   IN CHAR,
                                       cCodProd_in     IN CHAR)
  RETURN VARCHAR2
  IS
    n_RevisionRdm VARCHAR2(7);
    n_CantAdicRdm LGT_PED_REP_DET.CANT_ADIC_RDM%TYPE;
  BEGIN

    SELECT  A.CANT_ADIC_RDM INTO n_CantAdicRdm
    FROM  LGT_PED_REP_DET A
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.NUM_PED_REP = cNroPedido_in
         AND A.COD_PROD = cCodProd_in;

    -- IF(TO_CHAR(n_CantAdicRdm)<>' ')THEN
    IF (n_CantAdicRdm IS NOT NULL) THEN
      n_RevisionRdm:='FALSE';
    ELSE
      n_RevisionRdm:='TRUE';
    END IF;

    RETURN n_RevisionRdm;
  END;
  /***************************************************************************/

 FUNCTION  REP_GET_CANT_MAX_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   cIndActualiza   IN CHAR DEFAULT 'S')
   RETURN NUMBER
       IS
--         v_nCantMax NUMBER;
         v_nNumMesesAnteriores number;

         v_FechaInicial date;
         v_nStockIdelReal  number;
         v_nStockIdelEfectivo  number;
         v_nStockIdealEficiente  number;

--         v_nMaxStockIdelReal  number;
--         v_nMaxStockIdelEfecivo  number;
        /* v_nRotDiaria   number;
         v_nRotMensual  number;*/

         v_num_meses_res_vta LGT_PARAM_REP_LOC.NUM_MESES_RES_VTA%TYPE;
         v_max_stk_g1        LGT_PARAM_REP_LOC.MAX_STK_G1%TYPE;
         v_fac_g1            LGT_PARAM_REP_LOC.FAC_G1%TYPE;
         v_max_stk_g2        LGT_PARAM_REP_LOC.MAX_STK_G1%TYPE;
         v_fac_g2            LGT_PARAM_REP_LOC.FAC_G1%TYPE;
         v_max_stk_g3        LGT_PARAM_REP_LOC.MAX_STK_G1%TYPE;
         v_fac_g3            LGT_PARAM_REP_LOC.FAC_G1%TYPE;
         v_max_stk_g4        LGT_PARAM_REP_LOC.MAX_STK_G1%TYPE;
         v_fac_g4            LGT_PARAM_REP_LOC.FAC_G1%TYPE;

         v_fact_aumento_stk_efect LGT_PARAM_REP_LOC.FACTOR_AUMENTO_STK_EFECT%TYPE;

        --Agregado DVELIZ 16.09.08
        vValorAutorizado     LGT_PARAM_PROD_LOCAL.TOT_UNID_VTA_RDM%TYPE;
--        v_PVM_autorizado     LGT_PROD_LOCAL_REP.PVM_AUTORIZADO%TYPE;

        -- 2009-09-28 JOLIVA ( CAMPOS NUEVOS )
        v_nMaxStockIdelReal     LGT_PROD_LOCAL_REP.MAX_STK_IDEAL_REAL%TYPE;
--        v_UnidStkIdealReal   LGT_PROD_LOCAL_REP.UNID_STK_IDEAL_REAL%TYPE;

        v_nMaxStockIdelEfecivo LGT_PROD_LOCAL_REP.MAX_STK_IDEAL_EFECT%TYPE;
--        v_MaxStkIdealEfect    LGT_PROD_LOCAL_REP.MAX_STK_IDEAL_EFECT%TYPE;

        v_UnidStkIdealReal      LGT_PROD_LOCAL_REP.UNID_STK_IDEAL_REAL%TYPE;
        v_MesStkIdealReal    LGT_PROD_LOCAL_REP.MES_STK_IDEAL_REAL%TYPE;
        v_DiaEfecStkIdealReal LGT_PROD_LOCAL_REP.DIA_EFEC_STK_IDEAL_REAL%TYPE;


        v_UnVtaM0             LGT_PROD_LOCAL_REP.UN_VTA_M0%TYPE;
        v_UnVtaM1             LGT_PROD_LOCAL_REP.UN_VTA_M1%TYPE;
        v_UnVtaM2             LGT_PROD_LOCAL_REP.UN_VTA_M2%TYPE;
        v_UnVtaM3             LGT_PROD_LOCAL_REP.UN_VTA_M3%TYPE;
        v_UnVtaM4             LGT_PROD_LOCAL_REP.UN_VTA_M4%TYPE;
        v_DiaEfM0             LGT_PROD_LOCAL_REP.DIA_EF_M0%TYPE;
        v_DiaEfM1             LGT_PROD_LOCAL_REP.DIA_EF_M1%TYPE;
        v_DiaEfM2             LGT_PROD_LOCAL_REP.DIA_EF_M2%TYPE;
        v_DiaEfM3             LGT_PROD_LOCAL_REP.DIA_EF_M3%TYPE;
        v_DiaEfM4             LGT_PROD_LOCAL_REP.DIA_EF_M4%TYPE;

-- 2010-01-11 JOLIVA: SE MODIFICA PARA QUE LA REPOSICIÓN CONSIDERE 6 MESES DE ROTACION DE PRODUCTOS
        v_UnVtaM5             LGT_PROD_LOCAL_REP.UN_VTA_M5%TYPE;
        v_DiaEfM5             LGT_PROD_LOCAL_REP.DIA_EF_M5%TYPE;

       BEGIN

         -- 2008-09-04 JOLIVA: Se obtienen parametros de reposición por local
         SELECT NUM_MESES_RES_VTA, MAX_STK_G1, FAC_G1, MAX_STK_G2, FAC_G2, MAX_STK_G3, FAC_G3, MAX_STK_G4, FAC_G4, FACTOR_AUMENTO_STK_EFECT
         INTO v_num_meses_res_vta, v_max_stk_g1, v_fac_g1, v_max_stk_g2, v_fac_g2, v_max_stk_g3, v_fac_g3, v_max_stk_g4, v_fac_g4 , v_fact_aumento_stk_efect
         FROM LGT_PARAM_REP_LOC
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND COD_LOCAL = cCodLocal_in;


         --Modificado por DVELIZ 16.09.08
          BEGIN
         -- 2010-01-06 JOLIVA: Se toma en cuenta la fecha fin de PVM, no sólo el indicador de activo
             SELECT CASE WHEN IND_AUTORIZADO = 'S' AND IND_ACTIVO > SYSDATE THEN TOT_UNID_VTA_RDM ELSE 0 END
--             SELECT DECODE(IND_AUTORIZADO, 'S', TOT_UNID_VTA_RDM, 0)
             INTO vValorAutorizado
             FROM LGT_PARAM_PROD_LOCAL
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND COD_LOCAL = cCodLocal_in
             AND COD_PROD = cCodProd_in;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
                 vValorAutorizado := 0;
          END;


         --SE REVISARAN LOS MESES ANTERIORES SEGUN ESTE PARAMETROS
         --EJEMPLO : SI LA FECHA DE HOY ES 26/08/2008(AGOSTO)
         --SE CALCULARA DESDE MAYO(4 MESES ANTES)
    --     v_nNumMesesAnteriores := 4;
         v_nNumMesesAnteriores := v_num_meses_res_vta;


         --04.06.2009 DUBILLUZ
         IF vValorAutorizado != 0 THEN
            v_nNumMesesAnteriores := 1;
         END IF;
         ------

         select to_date( '01/'||
                         trim(to_char(add_months(sysdate,-(v_nNumMesesAnteriores-1)),'MM'))||'/'||
                         trim(to_char(add_months(sysdate,-(v_nNumMesesAnteriores-1)),'YYYY'))||'/'
                         || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
         into   v_FechaInicial
         from   dual;


        --CALCULO DE STOCK_IDEAL
        -- 2009-11-24 JOLIVA: TRABAJAMOS SOLO CON LOS ENTEROS
        -- 2010-01-05 JOLIVA: SI LA MAXIMO ROTACION ES MENOR A 1, CONSIDERAR 1
        select TRUNC(NVL(CASE WHEN max(r.cant_unid_vta) BETWEEN 0.0001 AND 1 THEN 1 ELSE max(r.cant_unid_vta) END,0))
        into   v_nMaxStockIdelReal
        from   VTA_RES_VTA_EFECTIVA_LOCAL r
        where  r.fecha_mes BETWEEN v_FechaInicial
                           AND     sysdate
        and    r.cod_grupo_cia = cCodGrupoCia_in
        and    r.cod_local = cCodLocal_in
        and    r.cod_prod = cCodProd_in;


        BEGIN
            SELECT CANT_UNID_VTA, NUM_DIA_EFECTIVO
            INTO v_UnVtaM0, v_DiaEfM0
            FROM VTA_RES_VTA_EFECTIVA_LOCAL RRR
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = cCodProd_in
              AND FECHA_MES = ADD_MONTHS(TRUNC(SYSDATE,'MM'), 0);
        EXCEPTION
                 WHEN OTHERS THEN
                      v_UnVtaM0 := 0;
                      v_DiaEfM0 := 0;
        END;

        BEGIN
            SELECT CANT_UNID_VTA, NUM_DIA_EFECTIVO
            INTO v_UnVtaM1, v_DiaEfM1
            FROM VTA_RES_VTA_EFECTIVA_LOCAL RRR
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = cCodProd_in
              AND FECHA_MES = ADD_MONTHS(TRUNC(SYSDATE,'MM'), -1);
        EXCEPTION
                 WHEN OTHERS THEN
                      v_UnVtaM1 := 0;
                      v_DiaEfM1 := 0;
        END;

        BEGIN
            SELECT CANT_UNID_VTA, NUM_DIA_EFECTIVO
            INTO v_UnVtaM2, v_DiaEfM2
            FROM VTA_RES_VTA_EFECTIVA_LOCAL RRR
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = cCodProd_in
              AND FECHA_MES = ADD_MONTHS(TRUNC(SYSDATE,'MM'), -2);
        EXCEPTION
                 WHEN OTHERS THEN
                      v_UnVtaM2 := 0;
                      v_DiaEfM2 := 0;
        END;

        BEGIN
            SELECT CANT_UNID_VTA, NUM_DIA_EFECTIVO
            INTO v_UnVtaM3, v_DiaEfM3
            FROM VTA_RES_VTA_EFECTIVA_LOCAL RRR
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = cCodProd_in
              AND FECHA_MES = ADD_MONTHS(TRUNC(SYSDATE,'MM'), -3);
        EXCEPTION
                 WHEN OTHERS THEN
                      v_UnVtaM3 := 0;
                      v_DiaEfM3 := 0;
        END;

        BEGIN
            SELECT CANT_UNID_VTA, NUM_DIA_EFECTIVO
            INTO v_UnVtaM4, v_DiaEfM4
            FROM VTA_RES_VTA_EFECTIVA_LOCAL RRR
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = cCodProd_in
              AND FECHA_MES = ADD_MONTHS(TRUNC(SYSDATE,'MM'), -4);
        EXCEPTION
                 WHEN OTHERS THEN
                      v_UnVtaM4 := 0;
                      v_DiaEfM4 := 0;
        END;

-- 2010-01-11 JOLIVA: SE MODIFICA PARA QUE LA REPOSICIÓN CONSIDERE 6 MESES DE ROTACION DE PRODUCTOS
        BEGIN
            SELECT CANT_UNID_VTA, NUM_DIA_EFECTIVO
            INTO v_UnVtaM5, v_DiaEfM5
            FROM VTA_RES_VTA_EFECTIVA_LOCAL RRR
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = cCodProd_in
              AND FECHA_MES = ADD_MONTHS(TRUNC(SYSDATE,'MM'), -5);
        EXCEPTION
                 WHEN OTHERS THEN
                      v_UnVtaM5 := 0;
                      v_DiaEfM5 := 0;
        END;

        -- 2008-10-16 Agregado por JOLIVA
        -- Se valida contra el PVM aprobado por el RDM
        SELECT CASE WHEN vValorAutorizado > v_nMaxStockIdelReal THEN vValorAutorizado ELSE v_nMaxStockIdelReal END
        INTO v_nMaxStockIdelReal
        FROM DUAL;

        select CASE WHEN v_nMaxStockIdelReal > 1	 THEN v_nMaxStockIdelReal/2
                    WHEN v_nMaxStockIdelReal <= 1 and
                         v_nMaxStockIdelReal >0  THEN 1
                    WHEN v_nMaxStockIdelReal = 0	 THEN 0
        		   END
        into   v_nStockIdelReal
        from   dual;

        v_nStockIdelReal := CEIL(v_nStockIdelReal);

        --CALCULO DE STOCK_IDEAL EFECTIVO
    --    select max((r.cant_unid_vta/r.num_dia_efectivo)*30)
/*
        select NVL(max((r.cant_unid_vta/DECODE(r.num_dia_efectivo,0,1,r.num_dia_efectivo))* DECODE(r.fecha_mes,TRUNC(SYSDATE,'MM'),1,30)),0)
        into   v_nMaxStockIdelEfecivo
        from   VTA_RES_VTA_EFECTIVA_LOCAL r
        where  r.fecha_mes BETWEEN v_FechaInicial
                           AND     sysdate
        and    r.cod_grupo_cia = cCodGrupoCia_in
        and    r.cod_local = cCodLocal_in
        and    r.cod_prod = cCodProd_in;
*/
     -- 2008-12-11 Se modifica para que sólo multiplique por 30 cuando los días efectivos >= 15
/*
        SELECT NVL(MAX(
                   r.cant_unid_vta * CASE WHEN r.fecha_mes = TRUNC(SYSDATE,'MM') THEN 1
                                          WHEN r.num_dia_efectivo < 15 THEN 1
                                          ELSE 30 / DECODE(r.num_dia_efectivo,0,1,r.num_dia_efectivo)
                                     END
               ),0)
        into   v_nMaxStockIdelEfecivo
        from   VTA_RES_VTA_EFECTIVA_LOCAL r
        where  r.fecha_mes BETWEEN v_FechaInicial
                           AND     sysdate
        and    r.cod_grupo_cia = cCodGrupoCia_in
        and    r.cod_local = cCodLocal_in
        and    r.cod_prod = cCodProd_in;

*/
     -- 2009-09-28  JOLIVA
        BEGIN
            SELECT UNID_VTA_EFECT, FECHA_MES, CANT_UNID_VTA, NUM_DIA_EFECTIVO
            into   v_nMaxStockIdelEfecivo, v_MesStkIdealReal, v_UnidStkIdealReal, v_DiaEfecStkIdealReal
            FROM
                (
                  SELECT
                         r.cant_unid_vta * CASE WHEN r.fecha_mes = TRUNC(SYSDATE,'MM') THEN 1
                                                WHEN r.num_dia_efectivo < 15 THEN 1
                                                ELSE 30 / DECODE(r.num_dia_efectivo,0,1,r.num_dia_efectivo)
                                           END UNID_VTA_EFECT,
                         r.FECHA_MES,
                         r.CANT_UNID_VTA,
                         r.NUM_DIA_EFECTIVO,
                         RANK() OVER (ORDER BY
                                         r.cant_unid_vta * CASE WHEN r.fecha_mes = TRUNC(SYSDATE,'MM') THEN 1
                                                                WHEN r.num_dia_efectivo < 15 THEN 1
                                                                ELSE 30 / DECODE(r.num_dia_efectivo,0,1,r.num_dia_efectivo)
                                                           END DESC,
                                         r.FECHA_MES DESC
                                     ) PUESTO
                  from   VTA_RES_VTA_EFECTIVA_LOCAL r
                  where  r.fecha_mes BETWEEN v_FechaInicial
                                     AND     sysdate
                  and    r.cod_grupo_cia = cCodGrupoCia_in
                  and    r.cod_local = cCodLocal_in
                  and    r.cod_prod = cCodProd_in
                )
  --         ORDER BY PUESTO;
            WHERE PUESTO = 1;

        EXCEPTION
                 WHEN OTHERS THEN
                      v_nMaxStockIdelEfecivo := 0;
                      v_MesStkIdealReal := NULL;
                      v_UnidStkIdealReal := 0;
                      v_DiaEfecStkIdealReal := 0;
        END;


        -- 2008-10-16 Agregado por JOLIVA
        -- Se valida contra el PVM aprobado por el RDM
        SELECT CASE WHEN vValorAutorizado > v_nMaxStockIdelEfecivo THEN vValorAutorizado ELSE v_nMaxStockIdelEfecivo END
        INTO v_nMaxStockIdelEfecivo
        FROM DUAL;

    /*
        select CASE WHEN v_nMaxStockIdelEfecivo > 30	THEN v_nMaxStockIdelEfecivo/4
                    WHEN v_nMaxStockIdelEfecivo >15 	THEN v_nMaxStockIdelEfecivo/3
                    WHEN v_nMaxStockIdelEfecivo >1 	  THEN v_nMaxStockIdelEfecivo/2
                    WHEN v_nMaxStockIdelEfecivo <=1 	THEN 1
                    WHEN v_nMaxStockIdelEfecivo =0 	  THEN 0
         		   END
        into   v_nStockIdelEfectivo
        from   dual;
    */

-- 2010-08-23 JOLIVA: SE AGREGA NUEVO RANGO DE FILTRO: SI VTA > 120 --> VTA / 5
/*
        select CASE WHEN v_nMaxStockIdelEfecivo > v_max_stk_g1	THEN v_nMaxStockIdelEfecivo/ v_fac_g1
                    WHEN v_nMaxStockIdelEfecivo > v_max_stk_g2 	THEN v_nMaxStockIdelEfecivo/ v_fac_g2
                    WHEN v_nMaxStockIdelEfecivo > v_max_stk_g3 	  THEN v_nMaxStockIdelEfecivo/ v_fac_g3
                    WHEN v_nMaxStockIdelEfecivo <= v_max_stk_g4 	THEN v_fac_g4
                    WHEN v_nMaxStockIdelEfecivo =0 	  THEN 0
         		   END
        into   v_nStockIdelEfectivo
        from   dual;
*/
        select CASE
                    WHEN v_nMaxStockIdelEfecivo > 120	THEN v_nMaxStockIdelEfecivo/5
                    WHEN v_nMaxStockIdelEfecivo > 30	THEN v_nMaxStockIdelEfecivo/4
                    WHEN v_nMaxStockIdelEfecivo >15 	THEN v_nMaxStockIdelEfecivo/3
                    WHEN v_nMaxStockIdelEfecivo >1 	  THEN v_nMaxStockIdelEfecivo/2
                    WHEN v_nMaxStockIdelEfecivo <=1 	THEN 1
                    WHEN v_nMaxStockIdelEfecivo =0 	  THEN 0
         		   END
        into   v_nStockIdelEfectivo
        from   dual;

        v_nStockIdelEfectivo := CEIL(v_nStockIdelEfectivo);

       -- calculo stock ideal eficiente
       v_nStockIdealEficiente  := floor((v_nStockIdelReal + v_nStockIdelEfectivo)/2);

       -- 2008-09-05 Aplico factor de aumento al stock efectivo
       v_nStockIdealEficiente  := floor(v_nStockIdealEficiente * v_fact_aumento_stk_efect);


       IF cIndActualiza = 'S' THEN

            UPDATE LGT_PROD_LOCAL_REP
            SET USU_MOD_PROD_LOCAL_REP = 'PK_CAL_REP_TMP',
                FEC_MOD_PROD_LOCAL_REP = SYSDATE,
                PVM_AUTORIZADO = vValorAutorizado,
                UNID_STK_IDEAL_REAL = v_UnidStkIdealReal,
                MES_STK_IDEAL_REAL = v_MesStkIdealReal,
                DIA_EFEC_STK_IDEAL_REAL = v_DiaEfecStkIdealReal,
                MAX_STK_IDEAL_REAL = v_nMaxStockIdelReal,
                MAX_STK_IDEAL_EFECT = v_nMaxStockIdelEfecivo,
                UN_VTA_M0 = v_UnVtaM0,
                UN_VTA_M1 = v_UnVtaM1,
                UN_VTA_M2 = v_UnVtaM2,
                UN_VTA_M3 = v_UnVtaM3,
                UN_VTA_M4 = v_UnVtaM4,
                DIA_EF_M0 = v_DiaEfM0,
                DIA_EF_M1 = v_DiaEfM1,
                DIA_EF_M2 = v_DiaEfM2,
                DIA_EF_M3 = v_DiaEfM3,
                DIA_EF_M4 = v_DiaEfM4,
                FACTOR_AUMENTO_STK_EFECT = v_fact_aumento_stk_efect,
                STK_IDEAL_REAL      = v_nStockIdelReal,
                STK_IDEAL_EFECT     = v_nStockIdelEfectivo,
                STK_IDEAL_EFICIENTE = v_nStockIdealEficiente,
-- 2010-01-11 JOLIVA: SE MODIFICA PARA QUE LA REPOSICIÓN CONSIDERE 6 MESES DE ROTACION DE PRODUCTOS
                UN_VTA_M5 = v_UnVtaM5,
                DIA_EF_M5 = v_DiaEfM5
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                  AND COD_LOCAL = cCodLocal_in
                  AND COD_PROD = cCodProd_in;

       END IF;

       -- calculo stock ideal eficiente
       return NVL(v_nStockIdealEficiente,0);

   END;

  /***************************************************************************/
   PROCEDURE INI_VTA_RES_VTA_EFECTIVA_LOCAL(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR)
   IS
   BEGIN

--    EXECUTE IMMEDIATE 'truncate table VTA_RES_VTA_EFECTIVA_LOCAL';

    DELETE FROM VTA_RES_VTA_EFECTIVA_LOCAL R
    WHERE R.COD_GRUPO_CIA = cCodGrupoCia_in
      AND R.COD_LOCAL = cCodLocal_in
      AND R.FECHA_MES = TRUNC(SYSDATE,'MM');

    INSERT INTO VTA_RES_VTA_EFECTIVA_LOCAL
    (COD_GRUPO_CIA, COD_LOCAL, COD_PROD, FECHA_MES, CANT_UNID_VTA) --, NUM_DIA_EFECTIVO
    SELECT
           R.COD_GRUPO_CIA,
           R.COD_LOCAL,
           R.COD_PROD,
           TRUNC(R.FEC_DIA_VTA,'MM'),
           SUM(R.CANT_UNID_VTA)
    FROM VTA_RES_VTA_ACUM_LOCAL R
    WHERE R.COD_GRUPO_CIA = cCodGrupoCia_in
      AND R.COD_LOCAL = cCodLocal_in
      AND R.FEC_DIA_VTA BETWEEN TRUNC(SYSDATE,'MM') AND TRUNC(SYSDATE)
--      AND R.FEC_DIA_VTA BETWEEN TRUNC(ADD_MONTHS(SYSDATE,-3),'MM') AND TRUNC(SYSDATE)
    GROUP BY
           R.COD_GRUPO_CIA,
           R.COD_LOCAL,
           R.COD_PROD,
           TRUNC(R.FEC_DIA_VTA,'MM');
    COMMIT;
      --********************
      DECLARE
             CURSOR CPROD IS
                    SELECT DISTINCT COD_GRUPO_CIA, COD_LOCAL, COD_PROD, FECHA_MES
                    FROM VTA_RES_VTA_EFECTIVA_LOCAL R
                    WHERE
                         R.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND R.COD_LOCAL = cCodLocal_in
                         AND R.FECHA_MES = TRUNC(SYSDATE,'MM')
--                         AND FEC_MOD IS NULL
      --                AND ROWNUM < 2000
                    ORDER BY COD_PROD, FECHA_MES;

             CONT INTEGER := 0;
             DIAS INTEGER;
      BEGIN
          FOR C1 IN CPROD
          LOOP
              DIAS := 0;

               SELECT COUNT(V5.FECHA)
               INTO DIAS
               FROM   (SELECT V1.FECHA,
                              NVL(V2.CANTIDAD, 0) CANT_1,
                              NVL(V3.CANTIDAD, 0) CANT_2
                       FROM   (SELECT DISTINCT D.FEC_DIA_VTA FECHA
                               FROM   VTA_RES_VTA_ACUM_LOCAL D
                               WHERE  D.COD_GRUPO_CIA = C1.COD_GRUPO_CIA
                               AND    D.COD_LOCAL = C1.COD_LOCAL
                               AND D.FEC_DIA_VTA BETWEEN C1.FECHA_MES AND ADD_MONTHS(C1.FECHA_MES,1)-1
                               ) V1,
                              (SELECT A.FEC_DIA_VTA FECHA,
                                      A.CANT_UNID_VTA CANTIDAD
                               FROM   VTA_RES_VTA_ACUM_LOCAL A
                               WHERE  A.COD_GRUPO_CIA = C1.COD_GRUPO_CIA
                               AND    A.COD_LOCAL = C1.COD_LOCAL
                               AND    A.COD_PROD = C1.COD_PROD
                               AND A.FEC_DIA_VTA BETWEEN C1.FECHA_MES AND ADD_MONTHS(C1.FECHA_MES,1)-1
                               ) V2,
                              (SELECT B.FEC_STK FECHA,
                                      B.CANT_STK CANTIDAD
                               FROM   LGT_HIST_STK_LOCAL B
                               WHERE  B.COD_GRUPO_CIA = C1.COD_GRUPO_CIA
                               AND    B.COD_LOCAL = C1.COD_LOCAL
                               AND    B.COD_PROD = C1.COD_PROD
                               AND B.FEC_STK BETWEEN C1.FECHA_MES AND ADD_MONTHS(C1.FECHA_MES,1)-1
                               ) V3
                       WHERE  V1.FECHA = V2.FECHA(+)
                       AND    V1.FECHA = V3.FECHA(+)) V5
               WHERE  V5.CANT_1 + V5.CANT_2 > 0;


               UPDATE VTA_RES_VTA_EFECTIVA_LOCAL R
               SET R.NUM_DIA_EFECTIVO = DIAS,
                   R.FEC_MOD = SYSDATE
               WHERE R.COD_GRUPO_CIA = C1.COD_GRUPO_CIA
                 AND R.COD_LOCAL = C1.COD_LOCAL
                 AND R.COD_PROD = C1.COD_PROD
                 AND R.FECHA_MES = C1.FECHA_MES;


               IF MOD(CONT, 1000) = 0 THEN
                  COMMIT;
                  DBMS_OUTPUT.put_line('CONT=' || CONT);
               END IF;


               CONT := CONT + 1;

          END LOOP;

           UPDATE VTA_RES_VTA_EFECTIVA_LOCAL R
           SET R.NUM_DIA_EFECTIVO = 1,
               R.FEC_MOD = SYSDATE
           WHERE R.COD_GRUPO_CIA = cCodGrupoCia_in
             AND R.COD_LOCAL = cCodLocal_in
             AND NUM_DIA_EFECTIVO = 0;

          COMMIT;
          DBMS_OUTPUT.put_line('CONT=' || CONT);

      END;
      --********************
   END;


-- *************************************************************************************

   FUNCTION  REP_GET_CANT_REP_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   cCantMeses_in   IN NUMBER)
   RETURN NUMBER
       IS
    v_nCantMax LGT_PROD_LOCAL_REP.CANT_MAX_STK%TYPE;

    v_nStkFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFracLocal LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_nCantExhib LGT_PROD_LOCAL.CANT_EXHIB%TYPE;

    v_nStkTransito AUX_STK_TRANSITO.STK_TRANSITO%TYPE;

    v_nCantSugeridaReponer LGT_PROD_LOCAL_REP.CANT_SUG%TYPE;
   BEGIN

      SELECT PL.STK_FISICO, PL.VAL_FRAC_LOCAL, PL.CANT_EXHIB, NVL(A.STK_TRANSITO,0)
      INTO v_nStkFisico, v_nValFracLocal, v_nCantExhib, v_nStkTransito
      FROM LGT_PROD_LOCAL PL,
           AUX_STK_TRANSITO A
      WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
        AND PL.COD_LOCAL = cCodLocal_in
        AND PL.COD_PROD = cCodProd_in
        AND A.COD_GRUPO_CIA(+) = PL.COD_GRUPO_CIA
        AND A.COD_LOCAL(+) = PL.COD_LOCAL
        AND A.COD_PROD(+) = PL.COD_PROD;

      v_nCantMax := REP_GET_CANT_MAX_PROD(cCodGrupoCia_in, cCodLocal_in, cCodProd_in);

      --SE SUMA LA EXHIBICION
      v_nCantMax := v_nCantMax+v_nCantExhib;

      IF (v_nCantMax = 1) THEN
			    IF ( (v_nStkFisico/v_nValFracLocal) + v_nStkTransito < 0.5) THEN
				     v_nCantSugeridaReponer := 1;
				  ELSE
				     v_nCantSugeridaReponer := 0;
				  END IF;
			ELSE
				  v_nCantSugeridaReponer := FLOOR((v_nCantMax)-(v_nStkFisico/v_nValFracLocal) - v_nStkTransito);
          v_nCantSugeridaReponer := CASE WHEN v_nCantSugeridaReponer < 0 THEN 0 ELSE v_nCantSugeridaReponer END;
			END IF;

      RETURN v_nCantSugeridaReponer;

   END;

/******************************************************************************/
PROCEDURE REP_ACTUALIZA_CANT_SUG(vCodGrupoCia IN CHAR, vCodLocal IN CHAR, vCodGrupo IN CHAR DEFAULT NULL) AS
--DECLARE
  --Obtengo en este cursor los codigos de los grupos de productos activos.
  CURSOR curGrupos IS
    SELECT DISTINCT G.COD_GRUPO
      FROM LGT_GRUPO_SIMILAR G,
           LGT_PROD_GRUPO_SIMILAR GP,
           LGT_PROD_LOCAL_REP PLR
     WHERE EST_GRUPO_SIMILAR = 'A'
       AND GP.EST_PROD_GRUPO_SIMILAR = 'A'
       AND GP.COD_GRUPO = G.COD_GRUPO
       AND PLR.COD_GRUPO_CIA = GP.COD_GRUPO_CIA
       AND PLR.COD_PROD = GP.COD_PROD
       AND PLR.CANT_SUG > 0
       AND G.COD_GRUPO = NVL(vCodGrupo, G.COD_GRUPO)
     ORDER BY COD_GRUPO;

/*
  -- 2009-05-20 DOCUMENTADO POR JOLIVA
  -- Obtengo en este cursor los productos:
  1. Pertenecientes al grupo indicado
  2. Que solicitan reponer mercaderia
  3. Que no tienen stock en almacen
*/
  CURSOR curProdGrupo(cCodGrupo CHAR) IS

        SELECT T.COD_PROD,
               T.CANT_SUG,
               T.CANT_SUG_BK,
               P.VAL_MAX_FRAC,
               T.UNID_MIN_SUG,
               T.STK_ALMACEN,
               P.IND_ZAN
        FROM LGT_PROD P,
             LGT_PROD_GRUPO_SIMILAR D,
             LGT_PROD_LOCAL_REP    T
       WHERE P.COD_GRUPO_CIA = vCodGrupoCia
         AND D.EST_PROD_GRUPO_SIMILAR = 'A'
         AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND D.COD_GRUPO = cCodGrupo
         AND D.COD_PROD = P.COD_PROD
         AND T.COD_GRUPO_CIA = D.COD_GRUPO_CIA
         AND T.COD_PROD = D.COD_PROD
         AND T.CANT_SUG > 0
--         AND D.COD_GRUPO = '001040'
         AND T.IND_STK_ALMACEN = 'N';

  vStockIdealEfectivo NUMBER;
  vStockIdealReal     NUMBER;
  vStockMaximoGrupo   NUMBER;
  vCodProdTemp        CHAR(6);

  -- 2009-06-03 JOLIVA
  vFactorConversion NUMBER(4);
  vStockTotalGrupoUnidMinimas NUMBER(9);
  vStockAdicionalProdSugerido NUMBER(9,2);
  vSugeridoUnidMinimaProdTmp  LGT_PROD_LOCAL_REP.UNID_MIN_SUG%TYPE;

  --dubilluz 19.03.2012
  vStkFisico_Actual number;
  vStkTransito number;
  vSugeridoActual number;
  v_nCantSugeridaRepCorrecto number;

BEGIN

  -- 2009-05-20 DOCUMENTADO POR JOLIVA
  -- Saca backup a la última cantidad SUGERIDA y obtiene el sugerido convertido a la unidad mínima del producto
   UPDATE LGT_PROD_LOCAL_REP lplrp
         SET lplrp.UNID_MIN_SUG = CANT_SUG * (select CANT_EQ_GRU_SIM from lgt_prod p where p.cod_grupo_cia=lplrp.cod_grupo_cia and p.cod_prod=lplrp.cod_prod),
             lplrp.STK_UNID_MIN = lplrp.STK_FISICO * (select CANT_EQ_GRU_SIM from lgt_prod p where p.cod_grupo_cia=lplrp.cod_grupo_cia and p.cod_prod=lplrp.cod_prod) / lplrp.VAL_FRAC_LOCAL,
             lplrp.CANT_SUG_BK = CANT_SUG,
             lplrp.IND_STK_ALMACEN = (SELECT IND_STK_ALMACEN
                                    FROM TMP_PBL_INDICADORES_STK t
                                   WHERE t.COD_PROD       = lplrp.cod_prod
                                     AND t.COD_GRUPO_CIA  = lplrp.cod_grupo_cia
                                     AND t.COD_LOCAL      = lplrp.cod_local )
       WHERE exists (
                     select 1
                     from   LGT_PROD_LOCAL_REP    T,
                            LGT_PROD_GRUPO_SIMILAR D
                      WHERE T.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                        AND T.COD_PROD      = D.COD_PROD
                    );


  FOR v_cur_Grupos IN curGrupos LOOP

    BEGIN
         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.put_line('v_cur_Grupos.COD_GRUPO=' || v_cur_Grupos.COD_GRUPO);
         END IF;

  -- Se obtiene el stock efectivo ideal por grupo de productos.
/*
      SELECT MAX(SUM(A.CANT_UNID_VTA * B.CANT_EQ_GRU_SIM * 30 /
                     A.NUM_DIA_EFECTIVO))
*/

  -- 2009-11-24 JOLIVA: SE MODIFICA PARA QUE SÓLO TRABAJE CON ENTEROS
        SELECT CEIL(NVL(MAX(
                       SUM(
                   A.cant_unid_vta * CASE WHEN A.fecha_mes = TRUNC(SYSDATE,'MM') THEN 1
                                          WHEN A.num_dia_efectivo < 15 THEN 1
                                          ELSE 30 / DECODE(A.num_dia_efectivo,0,1,A.num_dia_efectivo)
                                     END
                       )
               ),0))
        INTO vStockIdealEfectivo
        FROM LGT_PROD_GRUPO_SIMILAR C,
             VTA_RES_VTA_EFECTIVA_LOCAL   A,
             LGT_PROD                     B
       WHERE C.COD_GRUPO_CIA = vCodGrupoCia
         AND C.EST_PROD_GRUPO_SIMILAR = 'A'
         AND C.COD_GRUPO = v_cur_Grupos.COD_GRUPO
         AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND A.FECHA_MES BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3) AND TRUNC(SYSDATE)
         AND A.COD_PROD = C.COD_PROD
         AND B.COD_PROD = C.COD_PROD
       GROUP BY C.COD_GRUPO, A.FECHA_MES;


-- 2009-06-26 FALTA AGREGAR ESTA LOGICA PARA LA ROTACION REAL
-- 2010-06-24 JOLIVA: SE AGREGA NUEVO RANGO DE FILTRO: SI VTA > 120 --> VTA / 5
        select CASE
                    WHEN vStockIdealEfectivo > 120	THEN vStockIdealEfectivo/5
                    WHEN vStockIdealEfectivo > 30	THEN vStockIdealEfectivo/4
                    WHEN vStockIdealEfectivo >15 	THEN vStockIdealEfectivo/3
                    WHEN vStockIdealEfectivo >1 	  THEN vStockIdealEfectivo/2
                    WHEN vStockIdealEfectivo <=1 	THEN 1
                    WHEN vStockIdealEfectivo =0 	  THEN 0
         		   END
        into   vStockIdealEfectivo
        from   dual;
/*
        select CASE WHEN v_nMaxStockIdelEfecivo > v_max_stk_g1	THEN v_nMaxStockIdelEfecivo/ v_fac_g1
                    WHEN v_nMaxStockIdelEfecivo > v_max_stk_g2 	THEN v_nMaxStockIdelEfecivo/ v_fac_g2
                    WHEN v_nMaxStockIdelEfecivo > v_max_stk_g3 	  THEN v_nMaxStockIdelEfecivo/ v_fac_g3
                    WHEN v_nMaxStockIdelEfecivo <= v_max_stk_g4 	THEN v_fac_g4
                    WHEN v_nMaxStockIdelEfecivo =0 	  THEN 0
         		   END
        into   v_nStockIdelEfectivo
        from   dual;
*/

      --Se obtiene el stock real ideal por grupo de productos.
      SELECT MAX(SUM(A.CANT_UNID_VTA * B.CANT_EQ_GRU_SIM))
        INTO vStockIdealReal
        FROM LGT_PROD_GRUPO_SIMILAR C,
             VTA_RES_VTA_EFECTIVA_LOCAL   A,
             LGT_PROD                     B
       WHERE C.COD_GRUPO_CIA = vCodGrupoCia
         AND C.EST_PROD_GRUPO_SIMILAR = 'A'
         AND C.COD_GRUPO = v_cur_Grupos.COD_GRUPO
         AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND A.FECHA_MES BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3) AND TRUNC(SYSDATE)
         AND A.COD_PROD = C.COD_PROD
         AND B.COD_PROD = C.COD_PROD
       GROUP BY C.COD_GRUPO, A.FECHA_MES;


-- 2009-06-26 FALTA AGREGAR ESTA LOGICA PARA LA ROTACION REAL
        select CASE WHEN vStockIdealReal > 1	 THEN vStockIdealReal/2
                    WHEN vStockIdealReal <= 1 and
                         vStockIdealReal >0  THEN 1
                    WHEN vStockIdealReal = 0	 THEN 0
        		   END
        into   vStockIdealReal
        from   dual;


      --Se obtiene el promedio del stock efectivo y el real para obtener el
      --stock maximo por grupos.
      vStockMaximoGrupo := FLOOR((vStockIdealEfectivo + vStockIdealReal) / 2);

      --Se actualiza el campo MAX_GRUPO de la tabla LGT_GRUPO_PROD_LOCAL_REP_CAB
      --con el promedio obtenido en el paso anterior.
      UPDATE LGT_GRUPO_SIMILAR_LOCAL
         SET MAX_GRUPO = vStockMaximoGrupo
       WHERE COD_GRUPO_CIA = vCodGrupoCia
         AND COD_LOCAL = vCodLocal
         AND COD_GRUPO = v_cur_Grupos.COD_GRUPO;

      COMMIT;

         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('MAXIMO DE GRUPO:' || vStockMaximoGrupo);
         END IF;
    END;

    -- 2009-05-20 JOLIVA: Obtener el código de producto que se usará para reemplazar a los SOLICITADOS que no tengan stock en almacen
      BEGIN
          SELECT COD_PROD, CANT_EQ_GRU_SIM -- , UNID_MIN_SUG
          INTO vCodProdTemp, vFactorConversion --, vSugeridoUnidMinimaProdTmp
          FROM
               (
                  SELECT
                         P.COD_PROD, P.DESC_PROD, P.DESC_UNID_PRESENT,
                         P.CANT_EQ_GRU_SIM,
                         PLR.UNID_MIN_SUG,
                         TRIM(P.IND_ZAN),
                         PLR.IND_STK_ALMACEN,
                         RANK() OVER(ORDER BY NVL(TRIM(P.IND_ZAN),' ') DESC, P.COD_PROD DESC) POS
                  FROM LGT_PROD P,
                       LGT_PROD_GRUPO_SIMILAR GP,
                       LGT_PROD_LOCAL_REP PLR
                  WHERE GP.COD_GRUPO = v_cur_Grupos.COD_GRUPO
                    AND GP.EST_PROD_GRUPO_SIMILAR = 'A'
                    AND PLR.COD_GRUPO_CIA = vCodGrupoCia
                    AND PLR.COD_LOCAL = vCodLocal
                    AND PLR.COD_PROD = GP.COD_PROD
                    AND P.COD_GRUPO_CIA = PLR.COD_GRUPO_CIA
                    AND P.COD_PROD = PLR.COD_PROD
                    AND PLR.IND_STK_ALMACEN = 'S'
               )
          WHERE POS = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          vCodProdTemp := NULL;
      END;


    -- 2009-06-29 JOLIVA: Voy a asumir que todo lo que se va a pedir de los productos que tienen stock en almacen será atendido
    --                    Y ya lo consideraré como parte del stock del grupo
      BEGIN
            SELECT
                   SUM(PLR.UNID_MIN_SUG)
            INTO vSugeridoUnidMinimaProdTmp
            FROM LGT_PROD P,
                 LGT_PROD_GRUPO_SIMILAR GP,
                 LGT_PROD_LOCAL_REP PLR
            WHERE GP.COD_GRUPO = v_cur_Grupos.COD_GRUPO
              AND GP.EST_PROD_GRUPO_SIMILAR = 'A'
              AND PLR.COD_GRUPO_CIA = vCodGrupoCia
              AND PLR.COD_LOCAL = vCodLocal
              AND PLR.COD_PROD = GP.COD_PROD
              AND P.COD_GRUPO_CIA = PLR.COD_GRUPO_CIA
              AND P.COD_PROD = PLR.COD_PROD
              AND PLR.IND_STK_ALMACEN = 'S';
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          vSugeridoUnidMinimaProdTmp := 0;
      END;

         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('vCodProdTemp=' || vCodProdTemp ||', vFactorConversion=' || vFactorConversion);
         END IF;


--        SELECT SUM(T.UNID_MIN_SUG)
        SELECT SUM(T.STK_UNID_MIN)
          INTO vStockTotalGrupoUnidMinimas
          FROM LGT_PROD_LOCAL_REP    T,
               LGT_PROD_GRUPO_SIMILAR D
         WHERE T.COD_GRUPO_CIA = D.COD_GRUPO_CIA
           AND D.EST_PROD_GRUPO_SIMILAR = 'A'
           AND T.COD_PROD = D.COD_PROD
           AND D.COD_GRUPO = v_cur_Grupos.COD_GRUPO;

         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('vStockTotalGrupoUnidMinimas=' || vStockTotalGrupoUnidMinimas);
            DBMS_OUTPUT.PUT_LINE('vStockMaximoGrupo=' || vStockMaximoGrupo);
         END IF;

    -- Si no se encuentra ningun producto en algun grupo con stock disponible
    -- no ingresa al proceso; esto es validado por el vFlag.
    IF (vCodProdTemp IS NOT NULL) THEN

    -- Empiezo con adicional igual a cero
      vStockAdicionalProdSugerido := 0;

    -- Asumo que
    -- A partir de aqui no se debe superar el máximo del grupo
      vStockTotalGrupoUnidMinimas :=  vStockTotalGrupoUnidMinimas + vSugeridoUnidMinimaProdTmp;
         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('Empieza vStockTotalGrupoUnidMinimas=' || vStockTotalGrupoUnidMinimas);
         END IF;
      FOR v_cur_ProdGrupo IN curProdGrupo(v_cur_Grupos.COD_GRUPO) LOOP

         IF vCodGrupo IS NOT NULL THEN
            DBMS_OUTPUT.PUT_LINE('v_cur_ProdGrupo.COD_PROD=' || v_cur_ProdGrupo.COD_PROD ||', v_cur_ProdGrupo.UNID_MIN_SUG=' || v_cur_ProdGrupo.UNID_MIN_SUG);
            DBMS_OUTPUT.PUT_LINE('vStockAdicionalProdSugerido=' || vStockAdicionalProdSugerido);
            DBMS_OUTPUT.PUT_LINE('vStockTotalGrupoUnidMinimas=' || vStockTotalGrupoUnidMinimas);
            DBMS_OUTPUT.PUT_LINE('vStockMaximoGrupo=' || vStockMaximoGrupo);
         END IF;


          IF ( ( vStockAdicionalProdSugerido + vStockTotalGrupoUnidMinimas + v_cur_ProdGrupo.UNID_MIN_SUG ) <= vStockMaximoGrupo) THEN
             vStockAdicionalProdSugerido :=  vStockAdicionalProdSugerido + (1.0 * v_cur_ProdGrupo.UNID_MIN_SUG / vFactorConversion);
             IF vCodGrupo IS NOT NULL THEN
                DBMS_OUTPUT.PUT_LINE('1. vStockAdicionalProdSugerido=' || vStockAdicionalProdSugerido);
             END IF;
          ELSE
              IF (vStockAdicionalProdSugerido + vStockTotalGrupoUnidMinimas < vStockMaximoGrupo ) THEN
                 vStockAdicionalProdSugerido :=  vStockAdicionalProdSugerido + ( ( vStockMaximoGrupo - (vStockAdicionalProdSugerido + vStockTotalGrupoUnidMinimas)) / vFactorConversion);
                 IF vCodGrupo IS NOT NULL THEN
                    DBMS_OUTPUT.PUT_LINE('2. vStockAdicionalProdSugerido=' || vStockAdicionalProdSugerido);
                 END IF;
              ELSE
                 IF vCodGrupo IS NOT NULL THEN
                    DBMS_OUTPUT.PUT_LINE('Ya se superó el stock máximo del grupo=' || (vStockAdicionalProdSugerido + vStockTotalGrupoUnidMinimas));
                 END IF;
              END IF;
          END IF;

      END LOOP;

      --Se actualiza el campo CANT_SUG de la tabla LGT_PROD_LOCAL_REP
      --con la nueva cantidad sugerida del producto que reemplazara
      --a los que no tienen stock.
      --Modificado por dveliz 15.01.2009

      DBMS_OUTPUT.PUT_LINE('a silicitar CEIL(vStockAdicionalProdSugerido):'||CEIL(vStockAdicionalProdSugerido));

      -- v_nCantSugeridaReponer := FLOOR((v_nCantMax)-(v_rProd.STK_FISICO/v_rProd.VAL_FRAC_LOCAL)-v_rProd.STK_TRANSITO);
  --dubilluz 19.03.2012
  --vStkFisico_Actual number;
  --vStkTransito number;

     select l.stk_fisico/l.val_frac_local
     into   vStkFisico_Actual
     from   lgt_prod_local l
      WHERE COD_GRUPO_CIA = vCodGrupoCia
      AND COD_LOCAL = vCodLocal
      AND COD_PROD = vCodProdTemp;

      begin
      select nvl(a.stk_transito,0)
      into   vStkTransito
      from   AUX_STK_TRANSITO a
      where  a.cod_grupo_cia = vCodGrupoCia
      and    a.cod_local = vCodLocal
      and    a.cod_prod = vCodProdTemp;
      exception
      when no_data_found then
        vStkTransito := 0;
      end;

      begin
      select nvl(CANT_SUG,0)
      into   vSugeridoActual
      from   lgt_prod_local_rep
      WHERE COD_GRUPO_CIA = vCodGrupoCia
         AND COD_LOCAL = vCodLocal
         AND COD_PROD = vCodProdTemp;
           exception
      when no_data_found then
        vSugeridoActual := 0;
      end;

     select
           case
           when FLOOR((vSugeridoActual+CEIL(vStockAdicionalProdSugerido))- (vStkFisico_Actual)-vStkTransito) <= 0 then 0
           else
               FLOOR((vSugeridoActual+CEIL(vStockAdicionalProdSugerido))- (vStkFisico_Actual)-vStkTransito)
           end
     into v_nCantSugeridaRepCorrecto
     from dual;

     DBMS_OUTPUT.PUT_LINE('vStkFisico_Actual:'||vStkFisico_Actual);
     DBMS_OUTPUT.PUT_LINE('vStkTransito:'||vStkTransito);
     DBMS_OUTPUT.PUT_LINE('vSugeridoActual:'||vSugeridoActual);

     DBMS_OUTPUT.PUT_LINE('--->>>> deberia reponer ..>>> '||v_nCantSugeridaRepCorrecto);

     if v_nCantSugeridaRepCorrecto > 0 then
     /*
     UPDATE LGT_PROD_LOCAL_REP
         --SET CANT_SUG = vNuevoSugerido
         SET CANT_SUG = CANT_SUG + CEIL(vStockAdicionalProdSugerido),
             CANT_ADICIONAL = CEIL(vStockAdicionalProdSugerido),
             FEC_MOD_PROD_LOCAL_REP = SYSDATE
      WHERE COD_GRUPO_CIA = vCodGrupoCia
         AND COD_LOCAL = vCodLocal
         AND COD_PROD = vCodProdTemp;
      */
     UPDATE LGT_PROD_LOCAL_REP
         --SET CANT_SUG = vNuevoSugerido
         SET CANT_SUG = CEIL(v_nCantSugeridaRepCorrecto),
             CANT_SOL = CEIL(v_nCantSugeridaRepCorrecto),
             CANT_ADICIONAL = CEIL(v_nCantSugeridaRepCorrecto),
             FEC_MOD_PROD_LOCAL_REP = SYSDATE
      WHERE COD_GRUPO_CIA = vCodGrupoCia
         AND COD_LOCAL = vCodLocal
         AND COD_PROD = vCodProdTemp;

      end if;
      COMMIT;
    ELSE
        NULL;
    END IF;
  END LOOP;
END REP_ACTUALIZA_CANT_SUG;


/***************************************************************************/
  PROCEDURE  REP_EJECUTA_PROCESO_REPOSICION AS

  vCodGrupoCia    CHAR(3);
  vCodLocal       CHAR(3);
  nIndNuevoAlg_max_min char(1);
  BEGIN
    BEGIN
      SELECT DISTINCT COD_GRUPO_CIA, COD_LOCAL
      INTO vCodGrupoCia, vCodLocal
      FROM VTA_IMPR_LOCAL;
      DBMS_OUTPUT.PUT_LINE('LOCAL :'||vCodLocal);
    EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('SE PRODUJO UN ERROR AL OBTENER CODCIA Y CODLOCAL');
      RAISE;
    END;

    BEGIN

      select nvl(e.ind_nuevo_alg_max_min,'N')
      into   nIndNuevoAlg_max_min
      from   lgt_param_rep_loc e;

      IF nIndNuevoAlg_max_min = 'N' THEN
        PTOVENTA_REP.inv_calcula_max_min(vCodGrupoCia, vCodLocal, 'S');
      else
        REP_3_CADENAS_MIFARMA.P_OPERA_ALGORITMO_MF(vCodLocal);
      end if;

      DBMS_OUTPUT.PUT_LINE('EL CALCULO DE MAX Y MIN SE EJECUTO CORRECTAMENTE');
    EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('SE PRODUJO UN ERROR AL EJECUTAR CALCULO MAX Y MIN');
      RAISE;
    END;

    /*
    comentado para que no venga a matriz a obtener los datos
    dubilluz 02.06.2011
    BEGIN
      PTOVENTA_REP.REP_PROCESO_IND_STOCK(vCodGrupoCia, vCodLocal, 'SISTEMAS');
    EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('SE PRODUJO UN ERROR AL EJECUTAR REP_PROCESO_IND_STOCK');
      RAISE;
    END;
    */

    BEGIN
      REP_ACTUALIZA_CANT_SUG(vCodGrupoCia, vCodLocal);
      DBMS_OUTPUT.PUT_LINE('LA ACTUALIZACION DE CANT SUG SE EJECUTO CORRECTAMENTE');
    EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('SE PRODUJO UN ERROR AL EJECUTAR ACTUALIZA CANT_SUG');
      RAISE;
    END;

    -- LAS RECARGAS NO SE DEBEN INTENTAR REPONER
    UPDATE LGT_PROD_LOCAL_REP
    SET CANT_SOL = 0, CANT_SUG = 0
    WHERE COD_PROD IN ('510558', '510559');

    DELETE FROM LGT_PROD_LOCAL_REP_HIST WHERE FEC_CREA < TRUNC(SYSDATE - 10);

    INSERT INTO LGT_PROD_LOCAL_REP_HIST
    (
    COD_GRUPO_CIA, COD_LOCAL, COD_PROD, CANT_MIN_STK, CANT_MAX_STK, CANT_SUG, CANT_SOL, CANT_ROT, CANT_TRANSITO,
    CANT_DIA_ROT, NUM_DIAS, STK_FISICO, VAL_FRAC_LOCAL, CANT_EXHIB, USU_CREA_PROD_LOCAL_REP, FEC_CREA_PROD_LOCAL_REP,
    USU_MOD_PROD_LOCAL_REP, FEC_MOD_PROD_LOCAL_REP, TIPO, CANT_DIAS_VTA, CANT_VTA_PER_0, CANT_VTA_PER_1,
    CANT_VTA_PER_2, CANT_VTA_PER_3, CANT_UNID_VTA, CANT_MAX_ADIC, TIP_ABC_UNID_VTA, CANT_ADIC, IND_STK_LOCALES,
    IND_STK_ALMACEN, STK_ALMACEN, UNID_MINIMA, CANT_SUG_BK, UNID_MIN_SUG, STK_UNID_MIN, VAL_FACT_CONV,
    CANT_ADICIONAL,
    -- 2009-11-24 JOLIVA: SE AGREGAN CAMPOS FALTANTES
    PVM_AUTORIZADO, UNID_STK_IDEAL_REAL, MES_STK_IDEAL_REAL, DIA_EFEC_STK_IDEAL_REAL,
    MAX_STK_IDEAL_REAL, MAX_STK_IDEAL_EFECT,
    UN_VTA_M0, UN_VTA_M1, UN_VTA_M2, UN_VTA_M3, UN_VTA_M4,
    DIA_EF_M0, DIA_EF_M1, DIA_EF_M2, DIA_EF_M3, DIA_EF_M4,
    -- 2010-01-06 JOLIVA: SE AGREGAN CAMPOS NUEVOS
    FACTOR_AUMENTO_STK_EFECT, STK_IDEAL_REAL, STK_IDEAL_EFECT, STK_IDEAL_EFICIENTE
    )
    SELECT
    COD_GRUPO_CIA, COD_LOCAL, COD_PROD, CANT_MIN_STK, CANT_MAX_STK, CANT_SUG, CANT_SOL, CANT_ROT, CANT_TRANSITO,
    CANT_DIA_ROT, NUM_DIAS, STK_FISICO, VAL_FRAC_LOCAL, CANT_EXHIB, USU_CREA_PROD_LOCAL_REP, FEC_CREA_PROD_LOCAL_REP,
    USU_MOD_PROD_LOCAL_REP, FEC_MOD_PROD_LOCAL_REP, TIPO, CANT_DIAS_VTA, CANT_VTA_PER_0, CANT_VTA_PER_1,
    CANT_VTA_PER_2, CANT_VTA_PER_3, CANT_UNID_VTA, CANT_MAX_ADIC, TIP_ABC_UNID_VTA, CANT_ADIC, IND_STK_LOCALES,
    IND_STK_ALMACEN, STK_ALMACEN, UNID_MINIMA, CANT_SUG_BK, UNID_MIN_SUG, STK_UNID_MIN, VAL_FACT_CONV,
    CANT_ADICIONAL,
    -- 2009-11-24 JOLIVA: SE AGREGAN CAMPOS FALTANTES
    PVM_AUTORIZADO, UNID_STK_IDEAL_REAL, MES_STK_IDEAL_REAL, DIA_EFEC_STK_IDEAL_REAL,
    MAX_STK_IDEAL_REAL, MAX_STK_IDEAL_EFECT,
    UN_VTA_M0, UN_VTA_M1, UN_VTA_M2, UN_VTA_M3, UN_VTA_M4,
    DIA_EF_M0, DIA_EF_M1, DIA_EF_M2, DIA_EF_M3, DIA_EF_M4,
    -- 2010-01-06 JOLIVA: SE AGREGAN CAMPOS NUEVOS
    FACTOR_AUMENTO_STK_EFECT, STK_IDEAL_REAL, STK_IDEAL_EFECT, STK_IDEAL_EFICIENTE
    FROM LGT_PROD_LOCAL_REP;

    COMMIT;

  END REP_EJECUTA_PROCESO_REPOSICION;

END;

/
