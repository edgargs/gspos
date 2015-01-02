CREATE OR REPLACE PACKAGE PTOVENTA."TMP_REP_ERN" AS

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Obtiene el reporte de ventas por Hora.
  --Fecha       Usuario		Comentario
  --27/03/2006  ERIOS    	Creación
  FUNCTION REP_VENTAS_POR_HORA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR,cDiaSem_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene las unidades vendidas por pedido.
  --Fecha       Usuario		Comentario
  --27/03/2006  ERIOS    	Creación
  FUNCTION REP_GET_UNIDADES_PEDIDO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR) RETURN FLOAT;

  --Descripcion: Obtiene el reporte de productos Falta Cero.
  --Fecha       Usuario		Comentario
  --06/07/2006  ERIOS    	Creación
  --30-03-2007  LREQUE    Modificación
  FUNCTION REP_PRODUCTOS_FALTA_CERO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR,cDiaSem_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de productos Falta Cero.
  --Fecha       Usuario		Comentario
  --06/07/2006  ERIOS    	Creación
  FUNCTION REP_DET_FALTA_CERO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de productos ABC.
  --Fecha       Usuario		Comentario
  --17/07/2006  ERIOS    	Creación
  FUNCTION REP_PRODUCTO_ABC(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cFiltro_in IN CHAR,
                            cInd_in IN CHAR,
                            cFechaIni_in IN CHAR,
                            cFechaFin_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Determina el tipo de cada productos.
  --Fecha       Usuario		Comentario
  --17/07/2006  ERIOS    	Creacion
  --07/09/2007  ERIOS    	Modificacion
  PROCEDURE REP_DETERMINAR_TIPO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR DEFAULT 'S');

  --Descripcion: Determina el tipo de cada productos en la compañia
  --Fecha       Usuario		Comentario
  --28/07/2006  JOLIVA    	Creación
  /*PROCEDURE REP_DETERMINAR_TIPO(cCodGrupoCia_in IN CHAR, nDiasRot_in IN NUMBER);*/

  --Descripcion: Lista por filtro de tipo de producto
  --Fecha       Usuario		Comentario
  --29/09/2006  paulo    	Creación
  FUNCTION REP_FILTRO_PRODUCTO_ABC(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cFiltro_in IN CHAR,
                            cInd_in IN CHAR,
                            cTipoProducto_in IN CHAR,
                            cFechaIni_in IN CHAR,
                            cFechaFin_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Determina el tipo de cada productos,
  --             segun la cantidad de unidades vendidas.
  --Fecha       Usuario		Comentario
  --13/03/2007  ERIOS    	Creación
  PROCEDURE REP_DETERMINAR_TIPO_UND(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR);

  --Descripcion: Determina el tipo por monto de cada productos.
  --Fecha       Usuario		Comentario
  --07/09/2007  ERIOS    	Creacion
  PROCEDURE REP_DETERMINAR_TIPO_MONTO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR);
END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."TMP_REP_ERN" AS

  FUNCTION REP_VENTAS_POR_HORA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR, cDiaSem_in IN CHAR)
  RETURN FarmaCursor
  IS
    curVentas FarmaCursor;
    cant INTEGER;
  BEGIN
    SELECT COUNT(*)
            INTO cant
    FROM VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND C.FEC_PED_VTA BETWEEN TO_DATE(vFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS') AND TO_DATE(vFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND TO_CHAR(C.FEC_PED_VTA,'D') LIKE cDiaSem_in;

    IF cant > 0 THEN

      OPEN curVentas FOR
      SELECT NUM_HORA||':00 .. '||NUM_HORA||':59' || 'Ã' ||
            TO_CHAR(NVL(TRIM(CANT),0),'999,990') || 'Ã' ||
            TO_CHAR(NVL(TRIM(VENT),0),'999,990.00') || 'Ã' ||
            TO_CHAR(NVL(TRIM(PROM),0),'999,990.00') || 'Ã' ||
            TO_CHAR(NVL(TRIM(UNID),0),'999,990.00') || 'Ã' ||
            TO_CHAR(NVL(TRIM(PREC),0),'999,990.00')
      FROM
        (SELECT TO_CHAR(C.FEC_PED_VTA,'HH24') "HORA",
              COUNT(C.NUM_PED_VTA) "CANT",
              NVL(SUM(C.VAL_NETO_PED_VTA),0) "VENT",
              NVL(SUM(C.VAL_NETO_PED_VTA)/DECODE(COUNT(C.NUM_PED_VTA),0,1,COUNT(C.NUM_PED_VTA)),0) "PROM",
              NVL(SUM(REP_GET_UNIDADES_PEDIDO(cCodGrupoCia_in, cCodLocal_in , C.NUM_PED_VTA))/DECODE(COUNT(C.NUM_PED_VTA),0,1,COUNT(C.NUM_PED_VTA)),0) "UNID",
              NVL((SUM(C.VAL_NETO_PED_VTA)/DECODE(COUNT(C.NUM_PED_VTA),0,1,COUNT(C.NUM_PED_VTA)))/(DECODE( SUM(REP_GET_UNIDADES_PEDIDO(cCodGrupoCia_in, cCodLocal_in , C.NUM_PED_VTA)),0,1,SUM(REP_GET_UNIDADES_PEDIDO(cCodGrupoCia_in, cCodLocal_in , C.NUM_PED_VTA)) )/DECODE(COUNT(C.NUM_PED_VTA),0,1,COUNT(C.NUM_PED_VTA))),0) "PREC"
        FROM VTA_PEDIDO_VTA_CAB C
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
              AND C.COD_LOCAL = cCodLocal_in
              AND C.EST_PED_VTA = 'C'
              AND C.FEC_PED_VTA BETWEEN TO_DATE(vFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS') AND TO_DATE(vFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
              AND TO_CHAR(C.FEC_PED_VTA,'D') LIKE cDiaSem_in
        GROUP BY TO_CHAR(C.FEC_PED_VTA,'HH24')),
        PBL_HORAS
      WHERE   HORA(+) = NUM_HORA;
      --ORDER BY 1  ;
    ELSE
      OPEN curVentas FOR
      SELECT '' || 'Ã' ||
            TO_CHAR(0,'999,990') || 'Ã' ||
            TO_CHAR(0,'999,990.00') || 'Ã' ||
            TO_CHAR(0,'999,990.00') || 'Ã' ||
            TO_CHAR(0,'999,990.00') || 'Ã' ||
            TO_CHAR(0,'999,990.00')
      FROM DUAL
      WHERE 1 = 2;
    END IF;
    RETURN curVentas;
  END;

  /****************************************************************************/
  FUNCTION REP_GET_UNIDADES_PEDIDO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  RETURN FLOAT
  IS
    v_nUnid FLOAT;
  BEGIN
    SELECT SUM(CANT_ATENDIDA/VAL_FRAC)
          INTO v_nUnid
    FROM VTA_PEDIDO_VTA_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;
    RETURN v_nUnid;
  END;

  /****************************************************************************/
  FUNCTION REP_PRODUCTOS_FALTA_CERO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR,cDiaSem_in IN CHAR)
  RETURN FarmaCursor
  IS
    curReporte FarmaCursor;
    --cant INTEGER;
  BEGIN
    /*SELECT COUNT(*)
            INTO cant
    FROM VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.EST_PED_VTA = 'C'
          AND C.FEC_PED_VTA BETWEEN TO_DATE(vFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS') AND TO_DATE(vFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND TO_CHAR(C.FEC_PED_VTA,'D') LIKE cDiaSem_in;

    IF cant > 0 THEN*/

      OPEN curReporte FOR
      SELECT COD_PROD|| 'Ã' ||DESC_PROD|| 'Ã' ||UNID_VTA|| 'Ã' ||NOM_LAB|| 'Ã' ||CANTIDAD|| 'Ã' ||DESC_PROD||UNID_VTA
      FROM (
       SELECT F.COD_PROD,
                    P.DESC_PROD,
                    NVL(TRIM(L.UNID_VTA),P.DESC_UNID_PRESENT) AS UNID_VTA,
                    B.NOM_LAB,
                    COUNT(F.SEC_USU_LOCAL) AS CANTIDAD
            FROM LGT_PROD_LOCAL_FALTA_STK F,LGT_PROD_LOCAL L, LGT_PROD P, LGT_LAB B
            WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND F.COD_LOCAL = cCodLocal_in
                  AND F.FEC_CREA_PROD_LOCAL_FALTA_STK BETWEEN TO_DATE(vFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS') AND TO_DATE(vFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
                  AND F.COD_GRUPO_CIA = L.COD_GRUPO_CIA
                  AND F.COD_LOCAL = L.COD_LOCAL
                  AND F.COD_PROD = L.COD_PROD
                  AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                  AND L.COD_PROD = P.COD_PROD
                  AND P.COD_LAB = B.COD_LAB
            GROUP BY F.COD_PROD,
                    P.DESC_PROD,
                    NVL(TRIM(L.UNID_VTA),P.DESC_UNID_PRESENT),
                    B.NOM_LAB
          );

    RETURN curReporte;
  END;

  /****************************************************************************/
  FUNCTION REP_DET_FALTA_CERO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR)
  RETURN FarmaCursor
  IS
    curReporte FarmaCursor;
  BEGIN
    OPEN curReporte FOR
    SELECT U.NOM_USU||' '||APE_PAT||' '|| APE_MAT || 'Ã' ||
          TO_CHAR(F.FEC_CREA_PROD_LOCAL_FALTA_STK,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
          TO_CHAR(F.FEC_CREA_PROD_LOCAL_FALTA_STK,'yyyyMMddHH24MISS')|| 'Ã' ||
          NVL(U.SEC_USU_LOCAL,' ')
    FROM LGT_PROD_LOCAL_FALTA_STK F,PBL_USU_LOCAL U
    WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
          AND F.COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in
          AND F.FEC_CREA_PROD_LOCAL_FALTA_STK BETWEEN TO_DATE(vFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS') AND TO_DATE(vFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND F.COD_GRUPO_CIA = U.COD_GRUPO_CIA
          AND F.COD_LOCAL = U.COD_LOCAL
          AND F.SEC_USU_LOCAL = U.SEC_USU_LOCAL;
    RETURN curReporte;
  END;

  /****************************************************************************/
  FUNCTION REP_PRODUCTO_ABC(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cFiltro_in IN CHAR,
                            cInd_in IN CHAR,
                            cFechaIni_in IN CHAR,
                            cFechaFin_in IN CHAR)
  RETURN FarmaCursor
  IS
    --v_nDiasRot PBL_LOCAL.NUM_DIAS_ROT%TYPE:=0;

    curReporte FarmaCursor;
  BEGIN
    --OBTENER DIAS ROTACION
    /*SELECT NUM_DIAS_ROT
      INTO v_nDiasRot
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;*/
    --DETERMINAR TIPO
    IF cInd_in = 'S' THEN
      REP_DETERMINAR_TIPO(cCodGrupoCia_in, cCodLocal_in,cFechaIni_in,cFechaFin_in);
      --REP_DETERMINAR_TIPO_UND(cCodGrupoCia_in, cCodLocal_in,cFechaIni_in,cFechaFin_in);
    END IF;

    --RETORNAR LISTADO
    OPEN curReporte FOR
    SELECT L.COD_PROD|| 'Ã' ||
            P.DESC_PROD|| 'Ã' ||
            NVL(TRIM(L.UNID_VTA),P.DESC_UNID_PRESENT)|| 'Ã' ||
            B.NOM_LAB || 'Ã' ||
            TO_CHAR((L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990.00')|| 'Ã' ||
            TO_CHAR(V.CANT_UNID_VTA,'999,990.00')|| 'Ã' ||
            TO_CHAR(MON_TOT_VTA,'999,990.00')|| 'Ã' ||
            --NVL(R.TIP_ABC_UNID_VTA,' ')
            NVL(R.TIPO,' ')--11/10/2007 ERIOS Ahora es ABC por monto.
    FROM LGT_PROD_LOCAL L, LGT_PROD P, LGT_LAB B, LGT_PROD_LOCAL_REP R,
          (
          SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
                  NVL(SUM(CANT_UNID_VTA),0) AS CANT_UNID_VTA,
                  NVL(SUM(MON_TOT_VTA),0) AS MON_TOT_VTA
          FROM VTA_RES_VTA_ACUM_LOCAL ---VTA_RES_VTA_REP_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND FEC_DIA_VTA BETWEEN TO_DATE(cFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                     AND TO_DATE(cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
           GROUP BY COD_GRUPO_CIA,COD_LOCAL,COD_PROD
          ) V
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND TRIM(P.COD_LAB) LIKE cFiltro_in
          AND R.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND R.COD_LOCAL = L.COD_LOCAL
          AND R.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
          AND P.COD_LAB = B.COD_LAB
          AND V.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND V.COD_LOCAL = L.COD_LOCAL
          AND V.COD_PROD = L.COD_PROD
      ORDER BY R.TIPO,
               V.CANT_UNID_VTA DESC
     ;

    RETURN curReporte;
  END;

  /****************************************************************************/
  PROCEDURE REP_DETERMINAR_TIPO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR DEFAULT 'S')
  AS

  BEGIN
    REP_DETERMINAR_TIPO_UND(cCodGrupoCia_in,cCodLocal_in,cFechaIni_in,cFechaFin_in,cIndGeneraResumen);
    REP_DETERMINAR_TIPO_MONTO(cCodGrupoCia_in,cCodLocal_in,cFechaIni_in,cFechaFin_in,cIndGeneraResumen);
  END;

  /****************************************************************************/
  /*PROCEDURE REP_DETERMINAR_TIPO(cCodGrupoCia_in IN CHAR, nDiasRot_in IN NUMBER)
  AS
    v_nMontoTotal NUMBER(15,3);
    v_nMontoA NUMBER(15,3);
    v_nMontoB NUMBER(15,3);

    CURSOR curProd IS
    SELECT COD_PROD,SUM(MON_TOT_VTA) AS MONTO
    FROM VTA_RES_VTA_ACUM_LOCAL ---VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND FEC_DIA_VTA BETWEEN SYSDATE-nDiasRot_in AND SYSDATE
    GROUP BY COD_PROD
    ORDER BY 2 DESC;
    v_rCurProd curProd%ROWTYPE;

    v_nMontoAaux NUMBER(15,3) :=0;
    v_nMontoBaux NUMBER(15,3) :=0;
  BEGIN
    --LIMPIAR TABLA
    UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = 'SISTEMAS', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        TIPO = 'C'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = g_cCodMatriz;

    --CALCULAR VENTAS
--    PTOVENTA_REP.INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
--    PTOVENTA_REP.INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE,'dd/MM/yyyy'));
    --OBTENER MONTO TOTAL
    SELECT SUM(MON_TOT_VTA)
      INTO v_nMontoTotal
    FROM VTA_RES_VTA_ACUM_LOCAL ---VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND FEC_DIA_VTA BETWEEN SYSDATE-nDiasRot_in AND SYSDATE
    ;
    v_nMontoA := v_nMontoTotal*85/100;
    v_nMontoB := v_nMontoTotal*10/100;

    FOR v_rCurProd IN curProd
    LOOP
      IF v_nMontoAaux < v_nMontoA THEN
        v_nMontoAaux := v_nMontoAaux+v_rCurProd.MONTO;
        UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = 'SISTEMAS', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
            TIPO = 'A'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = g_cCodMatriz
              AND COD_PROD = v_rCurProd.COD_PROD;
      ELSIF v_nMontoBaux < v_nMontoB THEN
        v_nMontoBaux := v_nMontoBaux+v_rCurProd.MONTO;
        UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = 'SISTEMAS', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
            TIPO = 'B'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = g_cCodMatriz
              AND COD_PROD = v_rCurProd.COD_PROD;
      ELSE
        EXIT;
      END IF;
    END LOOP;
    COMMIT;
  END;*/

/****************************************************************************/
  FUNCTION REP_FILTRO_PRODUCTO_ABC(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cFiltro_in IN CHAR,
                                   cInd_in IN CHAR,
                                   cTipoProducto_in IN CHAR,
                                   cFechaIni_in IN CHAR,
                                   cFechaFin_in IN CHAR)  RETURN FarmaCursor
  IS
    --v_nDiasRot PBL_LOCAL.NUM_DIAS_ROT%TYPE:=0;

    curReporte FarmaCursor;
  BEGIN
    --OBTENER DIAS ROTACION
    /*SELECT NUM_DIAS_ROT
      INTO v_nDiasRot
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;*/
    --DETERMINAR TIPO
    IF cInd_in = 'S' THEN
      REP_DETERMINAR_TIPO(cCodGrupoCia_in, cCodLocal_in,cFechaIni_in,cFechaFin_in);
      --REP_DETERMINAR_TIPO_UND(cCodGrupoCia_in, cCodLocal_in,cFechaIni_in,cFechaFin_in);
    END IF;

    --RETORNAR LISTADO
    OPEN curReporte FOR
    SELECT L.COD_PROD|| 'Ã' ||
            P.DESC_PROD|| 'Ã' ||
            NVL(TRIM(L.UNID_VTA),P.DESC_UNID_PRESENT)|| 'Ã' ||
            B.NOM_LAB || 'Ã' ||
            TO_CHAR((L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990.00')|| 'Ã' ||
            TO_CHAR(V.CANT_UNID_VTA,'999,990.00')|| 'Ã' ||
            TO_CHAR(MON_TOT_VTA,'999,990.00')|| 'Ã' ||
            --NVL(R.TIP_ABC_UNID_VTA,' ')
            NVL(R.TIPO,' ')--11/10/2007 ERIOS Ahora es ABC por monto.
    FROM LGT_PROD_LOCAL L, LGT_PROD P, LGT_LAB B, LGT_PROD_LOCAL_REP R,
          (
          SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
                  NVL(SUM(CANT_UNID_VTA),0) AS CANT_UNID_VTA,
                  NVL(SUM(MON_TOT_VTA),0) AS MON_TOT_VTA
          FROM VTA_RES_VTA_ACUM_LOCAL ---VTA_RES_VTA_REP_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND FEC_DIA_VTA BETWEEN TO_DATE(cFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                     AND TO_DATE(cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
           GROUP BY COD_GRUPO_CIA,COD_LOCAL,COD_PROD
          ) V
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND TRIM(P.COD_LAB) LIKE cfiltro_in
          AND R.TIPO LIKE cTipoProducto_in
          AND R.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND R.COD_LOCAL = L.COD_LOCAL
          AND R.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
          AND P.COD_LAB = B.COD_LAB
          AND V.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND V.COD_LOCAL = L.COD_LOCAL
          AND V.COD_PROD = L.COD_PROD
      ORDER BY R.TIPO,
               V.CANT_UNID_VTA DESC
     ;

    RETURN curReporte;
  END;
  /****************************************************************************/
  PROCEDURE REP_DETERMINAR_TIPO_UND(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR)
  AS
    --TIPO POR UNIDADES

    v_nMontoTotal VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE;
    v_nMontoA VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE;
    v_nMontoB VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE;

    CURSOR curProd IS
    SELECT COD_PROD,SUM(CANT_UNID_VTA) AS MONTO,SUM(MON_TOT_VTA) AS ORD
    FROM VTA_RES_VTA_ACUM_LOCAL ---VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_DIA_VTA BETWEEN TO_DATE(cFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                     AND TO_DATE(cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    GROUP BY COD_PROD
    ORDER BY 2 DESC,3 DESC;
    v_rCurProd curProd%ROWTYPE;

    v_nMontoAaux VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE:=0;
    v_nMontoBaux VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE:=0;

    v_nValorA INTEGER;
    v_nValorB INTEGER;
  BEGIN
    --LIMPIAR TABLA
    UPDATE LGT_PROD_LOCAL_REP SET --USU_MOD_PROD_LOCAL_REP = 'PCK_REP_ABC02', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        TIP_ABC_UNID_VTA = 'C'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    --CALCULAR VENTAS
    --07/01/2007 ERIOS Se verifica el parametro.
    IF cIndGeneraResumen = 'S' THEN
      PTOVENTA_REP.INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
      PTOVENTA_REP.INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE,'dd/MM/yyyy'));
    END IF;
    --OBTENER MONTO TOTAL
    SELECT SUM(CANT_UNID_VTA)
      INTO v_nMontoTotal
    FROM VTA_RES_VTA_ACUM_LOCAL ---VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_DIA_VTA BETWEEN TO_DATE(cFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                     AND TO_DATE(cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    ;
    --11/10/2007 ERIOS Se obtienen los valores de BBDD
    v_nValorA := PTOVENTA_GRAL.GET_ABC_VALOR_A;
    v_nValorB := PTOVENTA_GRAL.GET_ABC_VALOR_B;

    v_nMontoA := v_nMontoTotal*v_nValorA/100;
    v_nMontoB := v_nMontoTotal*v_nValorB/100;

    FOR v_rCurProd IN curProd
    LOOP
      IF v_nMontoAaux < v_nMontoA THEN
        v_nMontoAaux := v_nMontoAaux+v_rCurProd.MONTO;
        UPDATE LGT_PROD_LOCAL_REP SET --USU_MOD_PROD_LOCAL_REP = 'PCK_REP_ABC02', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
            TIP_ABC_UNID_VTA = 'A'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = v_rCurProd.COD_PROD;
      ELSIF v_nMontoBaux < v_nMontoB THEN
        v_nMontoBaux := v_nMontoBaux+v_rCurProd.MONTO;
        UPDATE LGT_PROD_LOCAL_REP SET --USU_MOD_PROD_LOCAL_REP = 'PCK_REP_ABC02', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
            TIP_ABC_UNID_VTA = 'B'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = v_rCurProd.COD_PROD;
      ELSE
        EXIT;
      END IF;
    END LOOP;
    COMMIT;
  END;
  /****************************************************************************/
  PROCEDURE REP_DETERMINAR_TIPO_MONTO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR)
  AS
    --TIPO POR MONTO VENTA

    v_nMontoTotal VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE;
    v_nMontoA VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE;
    v_nMontoB VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE;

     CURSOR curProd IS
    SELECT COD_PROD,SUM(MON_TOT_VTA) AS MONTO,SUM(CANT_UNID_VTA) AS ORD
    FROM VTA_RES_VTA_ACUM_LOCAL ---VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_DIA_VTA BETWEEN TO_DATE(cFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                     AND TO_DATE(cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    GROUP BY COD_PROD
    ORDER BY 2 DESC,3 DESC;
    v_rCurProd curProd%ROWTYPE;

    v_nMontoAaux VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE:=0;
    v_nMontoBaux VTA_RES_VTA_PROD_LOCAL.MON_TOT_VTA%TYPE:=0;

    v_nValorA INTEGER;
    v_nValorB INTEGER;
  BEGIN
    --LIMPIAR TABLA
    UPDATE LGT_PROD_LOCAL_REP SET --USU_MOD_PROD_LOCAL_REP = 'PCK_REP_ABC01', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        TIPO = 'C'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    --CALCULAR VENTAS
    --07/01/2007 ERIOS Se verifica el parametro.
    IF cIndGeneraResumen = 'S' THEN
      PTOVENTA_REP.INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE-1,'dd/MM/yyyy'));
      PTOVENTA_REP.INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE,'dd/MM/yyyy'));
    END IF;
    --OBTENER MONTO TOTAL
    SELECT SUM(MON_TOT_VTA)
      INTO v_nMontoTotal
    FROM VTA_RES_VTA_ACUM_LOCAL ---VTA_RES_VTA_REP_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_DIA_VTA BETWEEN TO_DATE(cFechaIni_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                     AND TO_DATE(cFechaFin_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    ;
    --11/10/2007 ERIOS Se obtienen los valores de BBDD
    v_nValorA := PTOVENTA_GRAL.GET_ABC_VALOR_A;
    v_nValorB := PTOVENTA_GRAL.GET_ABC_VALOR_B;

    v_nMontoA := v_nMontoTotal*v_nValorA/100;
    v_nMontoB := v_nMontoTotal*v_nValorB/100;

    FOR v_rCurProd IN curProd
    LOOP
      IF v_nMontoAaux < v_nMontoA THEN
        v_nMontoAaux := v_nMontoAaux+v_rCurProd.MONTO;
        UPDATE LGT_PROD_LOCAL_REP SET --USU_MOD_PROD_LOCAL_REP = 'PCK_REP_ABC01', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
            TIPO = 'A'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = v_rCurProd.COD_PROD;
      ELSIF v_nMontoBaux < v_nMontoB THEN
        v_nMontoBaux := v_nMontoBaux+v_rCurProd.MONTO;
        UPDATE LGT_PROD_LOCAL_REP SET --USU_MOD_PROD_LOCAL_REP = 'PCK_REP_ABC01', FEC_MOD_PROD_LOCAL_REP = SYSDATE,
            TIPO = 'B'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND COD_PROD = v_rCurProd.COD_PROD;
      ELSE
        EXIT;
      END IF;
    END LOOP;
    COMMIT;
  END;
  /****************************************************************************/

END;
/

