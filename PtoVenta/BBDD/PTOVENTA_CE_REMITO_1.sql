--------------------------------------------------------
--  DDL for Package Body PTOVENTA_CE_REMITO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_CE_REMITO" AS


 FUNCTION SEG_F_CUR_SOBRE_REMITO_DU(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cCodigoRemito   IN CHAR)
 RETURN FarmaCursor IS
    curDet FarmaCursor;
 BEGIN
    OPEN curDet FOR
     SELECT to_char(A.FEC_DIA_VTA,'dd/mm/yyyy') || 'Ã' ||
             A.COD_SOBRE || 'Ã' ||
             DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
             TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00') || 'Ã' ||
             NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
             NVL(B.USU_CREA_FORMA_PAGO_ENT,' ')|| 'Ã' ||
             NVL(R.Usu_Crea_Remito,' ')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '01' THEN 1 END,0)|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '02' THEN 1 END,0)
        FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, VTA_FORMA_PAGO C,
             CE_REMITO R,
             CE_DIA_REMITO DR
       WHERE R.COD_REMITO = cCodigoRemito
         AND R.COD_GRUPO_CIA = cCodGrupoCia_in
         AND R.COD_LOCAL = cCodLocal_in
         AND A.ESTADO = 'A'
         AND A.COD_GRUPO_CIA = R.COD_GRUPO_CIA
         AND A.COD_LOCAL     = R.COD_LOCAL
         AND A.COD_REMITO    = R.COD_REMITO

         AND A.FEC_DIA_VTA   = DR.FEC_DIA_VTA
         AND A.COD_GRUPO_CIA = DR.COD_GRUPO_CIA
         AND A.COD_LOCAL     = DR.COD_LOCAL


         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         UNION ALL
SELECT to_char(A.FEC_DIA_VTA,'dd/mm/yyyy') || 'Ã' ||
             A.COD_SOBRE || 'Ã' ||
             DECODE(A.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
             TO_CHAR(NVL(CASE
                           WHEN A.TIP_MONEDA = '01' THEN
                            A.MON_ENTREGA_TOTAL
                           WHEN A.TIP_MONEDA = '02' THEN
                            A.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00') || 'Ã' ||
             NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
             NVL(A.USU_CREA_SOBRE,' ')|| 'Ã' ||
             NVL(R.Usu_Crea_Remito,' ')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN A.TIP_MONEDA = '01' THEN
                            A.MON_ENTREGA_TOTAL
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN A.TIP_MONEDA = '02' THEN
                            A.MON_ENTREGA
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                         NVL(CASE WHEN A.TIP_MONEDA = '01' THEN 1 END,0)|| 'Ã' ||
                         NVL(CASE WHEN A.TIP_MONEDA = '02' THEN 1 END,0)
        FROM CE_SOBRE_TMP A, VTA_FORMA_PAGO C,
             CE_REMITO R--,
             --CE_DIA_REMITO DR
       WHERE R.COD_REMITO = cCodigoRemito
         AND R.COD_GRUPO_CIA = cCodGrupoCia_in
         AND R.COD_LOCAL = cCodLocal_in
         AND A.ESTADO = 'A'
         AND A.COD_GRUPO_CIA = R.COD_GRUPO_CIA
         AND A.COD_LOCAL     = R.COD_LOCAL
         AND A.COD_REMITO    = R.COD_REMITO
         /*
         AND A.FEC_DIA_VTA   = DR.FEC_DIA_VTA
         AND A.COD_GRUPO_CIA = DR.COD_GRUPO_CIA
         AND A.COD_LOCAL     = DR.COD_LOCAL
         */
         AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND A.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND NOT EXISTS (
                        SELECT 1
                        FROM   CE_SOBRE SOBRE
                        WHERE  SOBRE.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                        AND    SOBRE.COD_LOCAL = A.COD_LOCAL
                        AND    SOBRE.COD_SOBRE =  A.COD_SOBRE
                        );
    RETURN curDet;

  END ;
  /* ************************************************************** */
 FUNCTION SEG_F_CUR_REMITOS_DU(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             FechaIni        IN CHAR,
                             FechaFin        IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN

    IF (LENGTH(NVL(FechaIni, 0)) < 2 AND LENGTH(NVL(FechaFin, 0)) < 2) THEN
      OPEN curDet FOR
        /*SELECT --TO_CHAR(MIN (FECHA),'DD/MM/YYYY HH:MM:SS')|| 'Ã' ||
        distinct (TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS')) || 'Ã' || CODIGO || 'Ã' || USU || 'Ã' ||
                 TO_CHAR(SUM(SOLES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(DOLARES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(TOTAL), '999,999,990.00') || 'Ã' || SUM(CANT) || 'Ã' ||
                 RANGO || 'Ã' || TO_CHAR(FECHA, 'YYYYMMDDHHMMSS')
          FROM (SELECT V1.FECHA,
                       V1.CODIGO,
                       V1.USU,
                       V1.SOLES,
                       V1.DOLARES,
                       V1.TOTAL,
                       V1.CANT,
                       V1.RANGO
                  FROM (SELECT b.fec_crea_remito FECHA,
                               NVL(B.COD_REMITO, ' ') CODIGO,
                               B.USU_CREA_REMITO USU,
                               NVL(SUM(CASE
                                         WHEN D.TIP_MONEDA = '01' THEN
                                          D.MON_ENTREGA_TOTAL
                                       END),
                                   0) SOLES,
                               NVL(SUM(CASE
                                         WHEN D.TIP_MONEDA = '02' THEN
                                          D.MON_ENTREGA
                                       END),
                                   0) DOLARES,
                               NVL(SUM(D.MON_ENTREGA_TOTAL), 0) TOTAL,
                               SUM(CASE
                                     WHEN C.COD_SOBRE IS NOT NULL THEN
                                      1
                                   END) CANT,
                               TO_CHAR(SYSDATE - 30, 'DD/MM/YYYY') || '' ||
                               TO_CHAR(SYSDATE, 'DD/MM/YYYY') RANGO
                          FROM CE_DIA_REMITO         A,
                               CE_REMITO             B,
                               CE_SOBRE              C,
                               CE_FORMA_PAGO_ENTREGA D
                         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND A.COD_LOCAL = cCodLocal_in
                           AND b.fec_crea_remito -- BETWEEN SYSDATE-30 AND SYSDATE
                               BETWEEN
                               TO_DATE(to_char(trunc(SYSDATE - 30),
                                               'dd/mm/yyyy') || ' 00:00:00',
                                       'dd/MM/yyyy HH24:mi:ss') AND
                               TO_DATE(to_char(trunc(SYSDATE), 'dd/mm/yyyy') ||
                                       ' 23:59:59',
                                       'dd/MM/yyyy HH24:mi:ss')
                                  and c.estado = 'A'
                           AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                           AND C.COD_LOCAL = B.COD_LOCAL
                           AND C.COD_REMITO = B.COD_REMITO
                           AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                           AND TRUNC(A.FEC_DIA_VTA) = TRUNC(C.FEC_DIA_VTA)
                           AND A.COD_LOCAL = C.COD_LOCAL
                           AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                           AND C.COD_LOCAL = D.COD_LOCAL
                           AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                           AND C.SEC_FORMA_PAGO_ENTREGA =
                               D.SEC_FORMA_PAGO_ENTREGA --
                         GROUP BY b.fec_crea_remito,
                                  b.COD_REMITO,
                                  B.USU_CREA_REMITO) V1)
        --GROUP BY CODIGO,USU,RANGO;
         GROUP BY TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS'),
                  CODIGO,
                  USU,
                  RANGO,
                  TO_CHAR(FECHA, 'YYYYMMDDHHMMSS');*/
SELECT  distinct (TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS')) || 'Ã' || CODIGO || 'Ã' || USU || 'Ã' ||
                 TO_CHAR(SUM(SOLES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(DOLARES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(TOTAL), '999,999,990.00') || 'Ã' || SUM(CANT) || 'Ã' ||
                 RANGO || 'Ã' || TO_CHAR(FECHA, 'YYYYMMDDHHMMSS')
          FROM (SELECT V1.FECHA,
                       V1.CODIGO,
                       V1.USU,
                       V1.SOLES,
                       V1.DOLARES,
                       V1.TOTAL,
                       V1.CANT,
                       V1.RANGO
                  FROM (SELECT B.fec_crea_remito FECHA,
                               NVL(B.COD_REMITO, ' ') CODIGO,
                               B.USU_CREA_REMITO USU,
                               NVL(SUM(CASE
                                         WHEN SOBRES.TIP_MONEDA = '01' THEN
                                          SOBRES.MON_ENTREGA_TOTAL
                                       END),
                                   0) SOLES,
                               NVL(SUM(CASE
                                         WHEN SOBRES.TIP_MONEDA = '02' THEN
                                          SOBRES.MON_ENTREGA
                                       END),
                                   0) DOLARES,
                               NVL(SUM(SOBRES.MON_ENTREGA_TOTAL), 0) TOTAL,
                               SUM(CASE
                                     WHEN SOBRES.COD_SOBRE IS NOT NULL THEN
                                      1
                                   END) CANT,
                               TO_CHAR(SYSDATE - 30, 'DD/MM/YYYY') || '' ||
                               TO_CHAR(SYSDATE, 'DD/MM/YYYY') RANGO
                          FROM CE_REMITO             B,
                               (
                                  select C.COD_GRUPO_CIA,C.COD_LOCAL,C.COD_REMITO,C.COD_SOBRE,C.FEC_DIA_VTA,D.TIP_MONEDA,D.MON_ENTREGA,D.MON_ENTREGA_TOTAL
                                  from   CE_SOBRE C,
                                         CE_FORMA_PAGO_ENTREGA D
                                  where  C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                  AND    C.COD_LOCAL = D.COD_LOCAL
                                  AND    C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                                  AND    C.ESTADO = 'A'
                                  AND    C.SEC_FORMA_PAGO_ENTREGA = D.SEC_FORMA_PAGO_ENTREGA
                                  UNION
                                  select T.COD_GRUPO_CIA,T.COD_LOCAL,T.COD_REMITO,T.COD_SOBRE,T.FEC_DIA_VTA,T.TIP_MONEDA,T.MON_ENTREGA,T.MON_ENTREGA_TOTAL
                                  from   CE_SOBRE_TMP T
                                  WHERE  T.ESTADO = 'A'
                                  AND    NOT EXISTS (
                                                    SELECT 1
                                                    FROM   CE_SOBRE G
                                                    WHERE  G.COD_GRUPO_CIA = T.COD_GRUPO_CIA
                                                    AND    G.COD_LOCAL = T.COD_LOCAL
                                                    AND    G.COD_SOBRE = T.COD_SOBRE
                                                    )
                               )SOBRES
                         WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND B.COD_LOCAL = cCodLocal_in
                           AND B.fec_crea_remito -- BETWEEN SYSDATE-30 AND SYSDATE
                               BETWEEN
                               TO_DATE(to_char(trunc(SYSDATE - 30),
                                               'dd/mm/yyyy') || ' 00:00:00',
                                       'dd/MM/yyyy HH24:mi:ss') AND
                               TO_DATE(to_char(trunc(SYSDATE), 'dd/mm/yyyy') ||
                                       ' 23:59:59',
                                       'dd/MM/yyyy HH24:mi:ss')
                           AND B.COD_GRUPO_CIA = SOBRES.COD_GRUPO_CIA
                           AND B.COD_LOCAL = SOBRES.COD_LOCAL
                           AND B.COD_REMITO = SOBRES.COD_REMITO
                         GROUP BY B.fec_crea_remito,
                                  B.COD_REMITO,
                                  B.USU_CREA_REMITO) V1)
         GROUP BY TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS'),
                  CODIGO,
                  USU,
                  RANGO,
                  TO_CHAR(FECHA, 'YYYYMMDDHHMMSS');
    ELSE
      OPEN curDet FOR
        /*SELECT --TO_CHAR(MIN (FECHA),'DD/MM/YYYY HH:MM:SS')|| 'Ã' ||
        distinct (TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS')) || 'Ã' || CODIGO || 'Ã' || USU || 'Ã' ||
                 TO_CHAR(SUM(SOLES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(DOLARES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(TOTAL), '999,999,990.00') || 'Ã' || SUM(CANT) || 'Ã' ||
                 RANGO || 'Ã' || TO_CHAR(FECHA, 'YYYYMMDDHHMMSS')
          FROM (SELECT V1.FECHA,
                       V1.CODIGO,
                       V1.USU,
                       V1.SOLES,
                       V1.DOLARES,
                       V1.TOTAL,
                       V1.CANT,
                       V1.RANGO
                  FROM (SELECT b.fec_crea_remito FECHA,
                               NVL(B.COD_REMITO, ' ') CODIGO,
                               B.USU_CREA_REMITO USU,
                               NVL(SUM(CASE
                                         WHEN D.TIP_MONEDA = '01' THEN
                                          D.MON_ENTREGA_TOTAL
                                       END),
                                   0) SOLES,
                               NVL(SUM(CASE
                                         WHEN D.TIP_MONEDA = '02' THEN
                                          D.MON_ENTREGA
                                       END),
                                   0) DOLARES,
                               NVL(SUM(D.MON_ENTREGA_TOTAL), 0) TOTAL,
                               SUM(CASE
                                     WHEN C.COD_SOBRE IS NOT NULL THEN
                                      1
                                   END) CANT,
                               TO_CHAR(SYSDATE - 30, 'DD/MM/YYYY') || '' ||
                               TO_CHAR(SYSDATE, 'DD/MM/YYYY') RANGO
                          FROM CE_DIA_REMITO         A,
                               CE_REMITO             B,
                               CE_SOBRE              C,
                               CE_FORMA_PAGO_ENTREGA D
                         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND A.COD_LOCAL = cCodLocal_in
                           AND b.fec_crea_remito BETWEEN
                               TO_DATE(FechaIni || ' 00:00:00',
                                       'dd/MM/yyyy HH24:mi:ss') AND
                               TO_DATE(FechaFin || ' 23:59:59',
                                       'dd/MM/yyyy HH24:mi:ss')
                                              and c.estado = 'A'
                           AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                           AND C.COD_LOCAL = B.COD_LOCAL
                           AND C.COD_REMITO = B.COD_REMITO
                           AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                           AND TRUNC(A.FEC_DIA_VTA) = TRUNC(C.FEC_DIA_VTA)
                           AND A.COD_LOCAL = C.COD_LOCAL
                           AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                           AND C.COD_LOCAL = D.COD_LOCAL
                           AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                           AND C.SEC_FORMA_PAGO_ENTREGA =
                               D.SEC_FORMA_PAGO_ENTREGA --
                         GROUP BY b.fec_crea_remito,
                                  b.COD_REMITO,
                                  B.USU_CREA_REMITO) V1)
        -- GROUP BY CODIGO,USU,RANGO;
         GROUP BY TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS'),
                  CODIGO,
                  USU,
                  RANGO,
                  TO_CHAR(FECHA, 'YYYYMMDDHHMMSS');*/
SELECT  distinct (TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS')) || 'Ã' || CODIGO || 'Ã' || USU || 'Ã' ||
                 TO_CHAR(SUM(SOLES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(DOLARES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(TOTAL), '999,999,990.00') || 'Ã' || SUM(CANT) || 'Ã' ||
                 RANGO || 'Ã' || TO_CHAR(FECHA, 'YYYYMMDDHHMMSS')
          FROM (SELECT V1.FECHA,
                       V1.CODIGO,
                       V1.USU,
                       V1.SOLES,
                       V1.DOLARES,
                       V1.TOTAL,
                       V1.CANT,
                       V1.RANGO
                  FROM (SELECT B.fec_crea_remito FECHA,
                               NVL(B.COD_REMITO, ' ') CODIGO,
                               B.USU_CREA_REMITO USU,
                               NVL(SUM(CASE
                                         WHEN SOBRES.TIP_MONEDA = '01' THEN
                                          SOBRES.MON_ENTREGA_TOTAL
                                       END),
                                   0) SOLES,
                               NVL(SUM(CASE
                                         WHEN SOBRES.TIP_MONEDA = '02' THEN
                                          SOBRES.MON_ENTREGA
                                       END),
                                   0) DOLARES,
                               NVL(SUM(SOBRES.MON_ENTREGA_TOTAL), 0) TOTAL,
                               SUM(CASE
                                     WHEN SOBRES.COD_SOBRE IS NOT NULL THEN
                                      1
                                   END) CANT,
                               TO_CHAR(SYSDATE - 30, 'DD/MM/YYYY') || '' ||
                               TO_CHAR(SYSDATE, 'DD/MM/YYYY') RANGO
                          FROM CE_REMITO             B,
                               (
                                  select C.COD_GRUPO_CIA,C.COD_LOCAL,C.COD_REMITO,C.COD_SOBRE,C.FEC_DIA_VTA,D.TIP_MONEDA,D.MON_ENTREGA,D.MON_ENTREGA_TOTAL
                                  from   CE_SOBRE C,
                                         CE_FORMA_PAGO_ENTREGA D
                                  where  C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                  AND    C.COD_LOCAL = D.COD_LOCAL
                                  AND    C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                                  AND    C.ESTADO = 'A'
                                  AND    C.SEC_FORMA_PAGO_ENTREGA = D.SEC_FORMA_PAGO_ENTREGA
                                  UNION
                                  select T.COD_GRUPO_CIA,T.COD_LOCAL,T.COD_REMITO,T.COD_SOBRE,T.FEC_DIA_VTA,T.TIP_MONEDA,T.MON_ENTREGA,T.MON_ENTREGA_TOTAL
                                  from   CE_SOBRE_TMP T
                                  WHERE  T.ESTADO = 'A'
                                  AND    NOT EXISTS (
                                                    SELECT 1
                                                    FROM   CE_SOBRE G
                                                    WHERE  G.COD_GRUPO_CIA = T.COD_GRUPO_CIA
                                                    AND    G.COD_LOCAL = T.COD_LOCAL
                                                    AND    G.COD_SOBRE = T.COD_SOBRE
                                                    )
                               )SOBRES
                         WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND B.COD_LOCAL = cCodLocal_in
                           AND B.fec_crea_remito -- BETWEEN SYSDATE-30 AND SYSDATE
                               BETWEEN
                                TO_DATE(FechaIni || ' 00:00:00',
                                       'dd/MM/yyyy HH24:mi:ss') AND
                               TO_DATE(FechaFin || ' 23:59:59',
                                       'dd/MM/yyyy HH24:mi:ss')
                           AND B.COD_GRUPO_CIA = SOBRES.COD_GRUPO_CIA
                           AND B.COD_LOCAL = SOBRES.COD_LOCAL
                           AND B.COD_REMITO = SOBRES.COD_REMITO
                         GROUP BY B.fec_crea_remito,
                                  B.COD_REMITO,
                                  B.USU_CREA_REMITO) V1)
         GROUP BY TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS'),
                  CODIGO,
                  USU,
                  RANGO,
                  TO_CHAR(FECHA, 'YYYYMMDDHHMMSS');
    END IF;

    RETURN curDet;
  END;

PROCEDURE CE_P_AGREGA_REMITO_DU(cCodGrupoCia_in IN CHAR,
                                cCodLocal       IN CHAR,
                                cIdUsu_in       IN CHAR,
                                cNumRemito      IN CHAR,
                                cFecha          IN CHAR,
                                cCodSobre       IN CHAR,
                                cPrecinto       IN CHAR DEFAULT '-'
                                )

   IS

    v_nCant  NUMBER;
    v_nCant2 NUMBER;
    v_nBoveda VARCHAR2(10);
  BEGIN
    SELECT COUNT(*)
      INTO v_nCant2
      FROM CE_REMITO C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal
       AND TRIM(C.COD_REMITO) = TRIM(cNumRemito);

    --LLEIVA 14-Ene-2014 Se lee el indicador de Boveda (PROSEGUR, HERMES, etc) según local
    select COD_ETV INTO v_nBoveda
    from PBL_ETV_LOCAL
    where COD_GRUPO_CIA = cCodGrupoCia_in and COD_LOCAL = cCodLocal;

    IF (v_nCant2 = 0) THEN
      INSERT INTO CE_REMITO
        (COD_REMITO,
         COD_GRUPO_CIA,
         COD_LOCAL,
         USU_CREA_REMITO,
         USU_MOD_REMITO,
         FEC_MOD_REMITO,
         FEC_PROCESO_ARCHIVO,
         FEC_PROCESO_INT_CE,
         PRECINTO,
         COD_PBL_ETV
         )
      VALUES
        (cNumRemito,
         cCodGrupoCia_in,
         cCodLocal,
         cIdUsu_in,
         NULL,
         NULL,
         NULL,
         NULL,
         cPrecinto,
         v_nBoveda);
    END IF;

    UPDATE CE_SOBRE SO
         SET SO.COD_REMITO = cNumRemito
       WHERE SO.COD_SOBRE = cCodSobre
         AND SO.COD_GRUPO_CIA = cCodGrupoCia_in
         AND SO.COD_LOCAL = cCodLocal;

    UPDATE Ce_Sobre_Tmp SO
         SET SO.COD_REMITO = cNumRemito
       WHERE SO.COD_SOBRE = cCodSobre
         AND SO.COD_GRUPO_CIA = cCodGrupoCia_in
         AND SO.COD_LOCAL = cCodLocal;

  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
      RAISE_APPLICATION_ERROR(-20002, 'Ya existe asignacion de remito');

  END;

PROCEDURE CE_P_SAVE_HIST_REMI(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cCodRemito      IN CHAR,
                             vUsu_in IN VARCHAR2)
AS
cursor01 FarmaCursor := SEG_F_CUR_GET_DATA4(cCodGrupoCia_in,
                                            cCodLocal_in,
                                            cCodRemito);
cursor02 FarmaCursor := SEG_F_CUR_GET_DATA3(cCodGrupoCia_in,
                                            cCodLocal_in,
                                            cCodRemito);
cursor03 FarmaCursor := CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in,
                                                  cCodLocal_in);
cursor04 FarmaCursor := CE_F_LISTA_TOTALES_SD(cCodGrupoCia_in,
                                              cCodLocal_in,
                                              vUsu_in);

fecha VARCHAR2(200);
cants VARCHAR2(200);
montos VARCHAR2(200);
cantd VARCHAR2(200);
montod VARCHAR2(200);
usuario VARCHAR2(200);
montototals VARCHAR2(200);
montototald VARCHAR(200);
BEGIN
     LOOP --cursor01
     FETCH cursor01 INTO fecha, cants, montos, cantd, montod;
     EXIT WHEN cursor01%NOTFOUND;
          INSERT INTO CE_SOBRE_REMI_SD(COD_GRUPO_CIA,COD_LOCAL,COD_REMITO,IND_REMITO,FEC_DIA_VTA_REM,CANT_S,MONT_S,
                                       CANT_D,MONT_D,FEC_CREA,USU_CREA,FEC_MOD,USU_MOD)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodRemito,'S',TO_DATE(fecha,'dd/MM/yyyy'),
                 to_number(cants,'999,999,990'),to_number(montos,'999,999,990.00'),
                 to_number(cantd,'999,999,990'),to_number(montod,'999,999,990.00'),SYSDATE,vUsu_in,NULL,NULL);
     END LOOP;
     LOOP --cursor02
     FETCH cursor02 INTO usuario, montototals, montototald;
     EXIT WHEN cursor02%NOTFOUND;
          INSERT INTO CE_TOTS_REMI_SD(COD_GRUPO_CIA,COD_LOCAL,COD_REMITO,IND_REMITO,USUARIO,TOTAL_S,TOTAL_D,
                                      USU_CREA,FEC_CREA,USU_MOD,FEC_MOD)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodRemito,'S',usuario,
                 to_number(montototals,'999,999,990.00'),to_number(montototald,'999,999,990.00'),vUsu_in,SYSDATE,NULL,NULL);
     END LOOP;
     /*
     LOOP --cursor03
     FETCH cursor03 INTO fecha, cants, montos, cantd, montod;
     EXIT WHEN cursor03%NOTFOUND;
          INSERT INTO CE_SOBRE_REMI_SD(COD_GRUPO_CIA,COD_LOCAL,COD_REMITO,IND_REMITO,FEC_DIA_VTA_REM,CANT_S,MONT_S,
                                       CANT_D,MONT_D,FEC_CREA,USU_CREA,FEC_MOD,USU_MOD)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodRemito,'N',TO_DATE(fecha,'dd/MM/yyyy'),
                 to_number(cants,'999,999,990'),to_number(montos,'999,999,990.00'),
                 to_number(cantd,'999,999,990'),to_number(montod,'999,999,990.00'),SYSDATE,vUsu_in,NULL,NULL);
     END LOOP;
     LOOP --cursor04
     FETCH cursor04 INTO usuario, montototals, montototald;
     EXIT WHEN cursor04%NOTFOUND;
          INSERT INTO CE_TOTS_REMI_SD(COD_GRUPO_CIA,COD_LOCAL,COD_REMITO,IND_REMITO,USUARIO,TOTAL_S,TOTAL_D,
                                      USU_CREA,FEC_CREA,USU_MOD,FEC_MOD)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodRemito,'N',usuario,
                 to_number(montototals,'999,999,990.00'),to_number(montototald,'999,999,990.00'),vUsu_in,SYSDATE,NULL,NULL);
     END LOOP;
     */
END;
/* *********************************************************************** */
FUNCTION SEG_F_CUR_GET_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor IS
    curVta FarmaCursor;
    --int_total number;
    --vCod_local_origen char(3);
  BEGIN
    OPEN curVta FOR
      /*SELECT distinct  V2.FECHA,
             V2.CANT_S,
             TO_CHAR(V2.SOLES, '999,999,990.00'),
             V2.CANT_D,
             TO_CHAR(V2.DOLARES, '999,999,990.00')
        FROM (SELECT TO_CHAR(B.FEC_DIA_VTA, 'DD/MM/YYYY') FECHA,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                C.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                1
                             END),
                         0) CANT_S,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                C.MON_ENTREGA
                             END),
                         0) DOLARES,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                1
                             END),
                         0) CANT_D--,
                     --V1.CANT CANT
                FROM --CE_DIA_REMITO A,
                     CE_SOBRE B,
                     CE_FORMA_PAGO_ENTREGA C,
                     (SELECT COUNT(*) CANT,
                             TRUNC(X.FEC_DIA_VTA) FEC,
                             X.COD_GRUPO_CIA,
                             X.COD_LOCAL
                        FROM CE_SOBRE X
                        where x.estado = 'A'
                        and   x.cod_remito = TRIM(cCodRemito)
                       GROUP BY TRUNC(X.FEC_DIA_VTA),
                                X.COD_GRUPO_CIA,
                                X.COD_LOCAL) V1
               WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND B.COD_LOCAL = cCodLocal_in
                 AND B.COD_REMITO = TRIM(cCodRemito)
                 and b.estado = 'A'
                 \*AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND A.FEC_DIA_VTA = B.FEC_DIA_VTA
                 *\
                 AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND B.COD_LOCAL = C.COD_LOCAL
                 AND B.SEC_MOV_CAJA = C.SEC_MOV_CAJA
                 AND B.SEC_FORMA_PAGO_ENTREGA = C.SEC_FORMA_PAGO_ENTREGA --
                 \*
                 AND TRUNC(A.FEC_DIA_VTA) = V1.FEC
                 AND A.COD_GRUPO_CIA = V1.COD_GRUPO_CIA
                 AND A.COD_LOCAL = V1.COD_LOCAL
                 *\
               GROUP BY TO_CHAR(B.FEC_DIA_VTA, 'DD/MM/YYYY'), V1.CANT
               ORDER BY 1 ASC) V2
               union all
               -- les quita
               SELECT distinct  V2.FECHA,
             V2.CANT_S,
             TO_CHAR(V2.SOLES, '999,999,990.00'),
             V2.CANT_D,
             TO_CHAR(V2.DOLARES, '999,999,990.00')
        FROM (SELECT TO_CHAR(B.FEC_DIA_VTA, 'DD/MM/YYYY') FECHA,
                     NVL(SUM(CASE
                               WHEN b.TIP_MONEDA = '01' THEN
                                b.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN b.TIP_MONEDA = '01' THEN
                                1
                             END),
                         0) CANT_S,
                     NVL(SUM(CASE
                               WHEN b.TIP_MONEDA = '02' THEN
                                b.MON_ENTREGA
                             END),
                         0) DOLARES,
                     NVL(SUM(CASE
                               WHEN b.TIP_MONEDA = '02' THEN
                                1
                             END),
                         0) CANT_D--,
                     --V1.CANT CANT
                FROM --CE_DIA_REMITO A,
                     CE_SOBRE_tmp B,
                     --CE_FORMA_PAGO_ENTREGA C,
                     (SELECT COUNT(*) CANT,
                             TRUNC(X.FEC_DIA_VTA) FEC,
                             X.COD_GRUPO_CIA,
                             X.COD_LOCAL
                        FROM CE_SOBRE_tmp X
                        where x.estado = 'A'
                        and   x.cod_remito = TRIM(cCodRemito)
                        and not exists
                               (
                               select 1
                               from   ce_sobre st
                               where  st.cod_sobre = X.cod_sobre
                               and    st.cod_remito = TRIM(cCodRemito)
                               )
                       GROUP BY TRUNC(X.FEC_DIA_VTA),
                                X.COD_GRUPO_CIA,
                                X.COD_LOCAL) V1
               WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND B.COD_LOCAL = cCodLocal_in
                 AND B.COD_REMITO = TRIM(cCodRemito)
                 and b.estado = 'A'
                 and not exists
                               (
                               select 1
                               from   ce_sobre st
                               where  st.cod_sobre = b.cod_sobre
                               and    st.cod_remito = TRIM(cCodRemito)
                               )
               GROUP BY TO_CHAR(B.FEC_DIA_VTA, 'DD/MM/YYYY'), V1.CANT
               ORDER BY 1 ASC) V2;*/

--------------------------------------
SELECT distinct  V2.FECHA,
             V2.CANT_S,
             TO_CHAR(V2.SOLES, '999,999,990.00'),
             V2.CANT_D,
             TO_CHAR(V2.DOLARES, '999,999,990.00')
        FROM (SELECT TO_CHAR(sobres.FEC_DIA_VTA, 'DD/MM/YYYY') FECHA,
                     NVL(SUM(CASE
                               WHEN sobres.TIP_MONEDA = '01' THEN
                                sobres.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN sobres.TIP_MONEDA = '01' THEN
                                1
                             END),
                         0) CANT_S,
                     NVL(SUM(CASE
                               WHEN sobres.TIP_MONEDA = '02' THEN
                                sobres.MON_ENTREGA
                             END),
                         0) DOLARES,
                     NVL(SUM(CASE
                               WHEN sobres.TIP_MONEDA = '02' THEN
                                1
                             END),
                         0) CANT_D
                FROM (
                        select C.COD_GRUPO_CIA,C.COD_LOCAL,C.COD_REMITO,C.COD_SOBRE,C.FEC_DIA_VTA,D.TIP_MONEDA,D.MON_ENTREGA,D.MON_ENTREGA_TOTAL
                        from   CE_SOBRE C,
                               CE_FORMA_PAGO_ENTREGA D
                        where  C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                        AND    C.COD_LOCAL = D.COD_LOCAL
                        AND    C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                        AND    C.ESTADO = 'A'
                        AND    C.SEC_FORMA_PAGO_ENTREGA = D.SEC_FORMA_PAGO_ENTREGA
                        UNION
                        select T.COD_GRUPO_CIA,T.COD_LOCAL,T.COD_REMITO,T.COD_SOBRE,T.FEC_DIA_VTA,T.TIP_MONEDA,T.MON_ENTREGA,T.MON_ENTREGA_TOTAL
                        from   CE_SOBRE_TMP T
                        WHERE  T.ESTADO = 'A'
                        AND    NOT EXISTS (
                                          SELECT 1
                                          FROM   CE_SOBRE G
                                          WHERE  G.COD_GRUPO_CIA = T.COD_GRUPO_CIA
                                          AND    G.COD_LOCAL = T.COD_LOCAL
                                          AND    G.COD_SOBRE = T.COD_SOBRE
                                          )
                     ) sobres

               WHERE sobres.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND sobres.COD_LOCAL = cCodLocal_in
                 AND sobres.COD_REMITO = TRIM(cCodRemito)

               GROUP BY TO_CHAR(sobres.FEC_DIA_VTA, 'DD/MM/YYYY')
               ORDER BY 1 ASC) V2;
    RETURN curVta;
  END;

/* ************************************************************************ */

/*****************************************************************************************************************************************/
  FUNCTION SEG_F_CUR_GET_DATA3(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      /*
      SELECT distinct  V1.USU,
             TO_CHAR(V1.SOLES, '999,999,990.00'),
             TO_CHAR(V1.DOLARES, '999,999,990.00')
        FROM (SELECT B.USU_CREA_REMITO USU,
                     NVL(SUM(CASE
                               WHEN D.TIP_MONEDA = '01' THEN
                                D.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN D.TIP_MONEDA = '02' THEN
                                D.MON_ENTREGA
                             END),
                         0) DOLARES
                FROM --CE_DIA_REMITO         A,
                     CE_REMITO             B,
                     CE_SOBRE              C,
                     CE_FORMA_PAGO_ENTREGA D
               WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND C.COD_LOCAL = cCodLocal_in
                 and  C.cod_remito  = TRIM(cCodRemito)
                 AND c.estado = 'A'
                 AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                 AND C.COD_LOCAL = D.COD_LOCAL
                 AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                 AND C.SEC_FORMA_PAGO_ENTREGA = D.SEC_FORMA_PAGO_ENTREGA
                 AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND C.COD_LOCAL = B.COD_LOCAL
                 AND C.COD_REMITO = B.COD_REMITO
               GROUP BY B.USU_CREA_REMITO) V1;
*/
SELECT distinct  V1.USU,
             TO_CHAR(V1.SOLES, '999,999,990.00'),
             TO_CHAR(V1.DOLARES, '999,999,990.00')
        FROM (
              SELECT vt.usu_crea USU,
                     NVL(SUM(CASE
                               WHEN vt.TIP_MONEDA = '01' THEN
                                vt.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN vt.TIP_MONEDA = '02' THEN
                                vt.MON_ENTREGA
                             END),
                         0) DOLARES
             from (
                 SELECT B.USU_CREA_REMITO usu_crea,
                       D.TIP_MONEDA,
                       d.mon_entrega,
                       D.MON_ENTREGA_TOTAL
                FROM --CE_DIA_REMITO         A,
                     CE_REMITO             B,
                     CE_SOBRE              C,
                     CE_FORMA_PAGO_ENTREGA D
               WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND C.COD_LOCAL = cCodLocal_in
                 and  C.cod_remito  = TRIM(cCodRemito)
                 AND c.estado = 'A'
                 AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                 AND C.COD_LOCAL = D.COD_LOCAL
                 AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                 AND C.SEC_FORMA_PAGO_ENTREGA = D.SEC_FORMA_PAGO_ENTREGA
                 AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND C.COD_LOCAL = B.COD_LOCAL
                 AND C.COD_REMITO = B.COD_REMITO
                 UNION all
                 SELECT REM.USU_CREA_REMITO usu_crea,TMP.TIP_MONEDA,tmp.mon_entrega,TMP.MON_ENTREGA_TOTAL
                 FROM   CE_SOBRE_TMP TMP,
                        CE_REMITO REM
                 WHERE  tmp.cod_grupo_cia = cCodGrupoCia_in
                 and    tmp.cod_local     = cCodLocal_in
                 and    tmp.cod_remito    = TRIM(cCodRemito)
                 AND    TMP.COD_GRUPO_CIA = REM.COD_GRUPO_CIA
                 AND    TMP.COD_LOCAL = REM.COD_LOCAL
                 AND    TMP.COD_REMITO = REM.COD_REMITO
                 and    tmp.estado = 'A'
                 and    not exists
                                  (
                                  select 1
                                  from   ce_sobre te
                                  where  te.cod_sobre = tmp.cod_sobre
                                  and    te.cod_grupo_cia = tmp.cod_grupo_cia
                                  and    te.cod_local = tmp.cod_local
                                  )
                 ) vt
               GROUP BY vt.usu_crea) V1;

    RETURN curDet;
  END;

  /* **************************************************************** */

/*****************************************************************************************************************************************/

FUNCTION CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    curSobresCajero FarmaCursor;
  BEGIN
    OPEN curSobresCajero FOR
      SELECT distinct  TO_CHAR(D.Fec_Dia_Vta,'dd/MM/yyyy') FECHA,
             (SELECT COUNT(*)
              FROM CE_FORMA_PAGO_ENTREGA G, Ce_Sobre K
              WHERE G.COD_FORMA_PAGO='00001' AND G.COD_GRUPO_CIA=K.COD_GRUPO_CIA
              AND G.COD_LOCAL=K.COD_LOCAL AND G.SEC_MOV_CAJA=K.SEC_MOV_CAJA
              AND G.SEC_FORMA_PAGO_ENTREGA=K.SEC_FORMA_PAGO_ENTREGA
              AND K.FEC_DIA_VTA = D.FEC_DIA_VTA
              AND G.EST_FORMA_PAGO_ENT='A') CANT_S,
             TO_CHAR((SELECT NVL(SUM(G.MON_ENTREGA_TOTAL),0)  --ASOSA, 15.06.2010
              FROM CE_FORMA_PAGO_ENTREGA G, Ce_Sobre K
              WHERE G.COD_FORMA_PAGO='00001' AND G.COD_GRUPO_CIA=K.COD_GRUPO_CIA
              AND G.COD_LOCAL=K.COD_LOCAL AND G.SEC_MOV_CAJA=K.SEC_MOV_CAJA
              AND G.SEC_FORMA_PAGO_ENTREGA=K.SEC_FORMA_PAGO_ENTREGA
              AND K.FEC_DIA_VTA = D.FEC_DIA_VTA
              AND G.EST_FORMA_PAGO_ENT='A'),'999,999,990.00') MONT_S,
              (SELECT COUNT(*)
              FROM CE_FORMA_PAGO_ENTREGA G, Ce_Sobre K
              WHERE G.COD_FORMA_PAGO='00002' AND G.COD_GRUPO_CIA=K.COD_GRUPO_CIA
              AND G.COD_LOCAL=K.COD_LOCAL AND G.SEC_MOV_CAJA=K.SEC_MOV_CAJA
              AND G.SEC_FORMA_PAGO_ENTREGA=K.SEC_FORMA_PAGO_ENTREGA
              AND K.FEC_DIA_VTA = D.FEC_DIA_VTA
              AND G.EST_FORMA_PAGO_ENT='A') CANT_D,
             TO_CHAR((SELECT NVL(SUM(G.MON_ENTREGA_TOTAL),0) --ASOSA, 15.06.2010
              FROM CE_FORMA_PAGO_ENTREGA G, Ce_Sobre K
              WHERE G.COD_FORMA_PAGO='00002' AND G.COD_GRUPO_CIA=K.COD_GRUPO_CIA
              AND G.COD_LOCAL=K.COD_LOCAL AND G.SEC_MOV_CAJA=K.SEC_MOV_CAJA
              AND G.SEC_FORMA_PAGO_ENTREGA=K.SEC_FORMA_PAGO_ENTREGA
              AND K.FEC_DIA_VTA = D.FEC_DIA_VTA
              AND G.EST_FORMA_PAGO_ENT='A'),'999,999,990.00') MONT_D
             --nvl(SUM(CASE WHEN b.cod_forma_pago='00001' THEN 1 END),0) CANT_SOBRE_S,
             --NVL(SUM(CASE WHEN B.COD_FORMA_PAGO='00002' THEN B.MON_ENTREGA_TOTAL END),0) MONTO_S
      FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B,
             CE_DIA_REMITO D
      WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
      AND B.COD_LOCAL=cCodLocal_in
      -- sobres pendientes que no tienen remito
      and a.estado = 'P'
      AND a.cod_remito is null
      AND B.COD_GRUPO_CIA=A.COD_GRUPO_CIA
      AND B.COD_LOCAL=A.COD_LOCAL
      AND B.SEC_MOV_CAJA=A.SEC_MOV_CAJA
      AND B.SEC_FORMA_PAGO_ENTREGA=A.SEC_FORMA_PAGO_ENTREGA
      AND B.EST_FORMA_PAGO_ENT='A'
      AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
      AND A.COD_LOCAL=D.COD_LOCAL
      AND A.FEC_DIA_VTA=D.FEC_DIA_VTA
      GROUP BY D.Fec_Dia_Vta;
    return curSobresCajero;
  end;
  /* ************************************************************* */

FUNCTION CE_F_LISTA_TOTALES_SD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                vUsu_in IN VARCHAR2) RETURN FarmaCursor IS
    curSobresCajero FarmaCursor;
    cursor02 FarmaCursor:=CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in,cCodLocal_in);
    montoSoles NUMBER(9,2):=0;
    montoDolares NUMBER(9,2):=0;
    cants VARCHAR2(200);
    cantd VARCHAR2(200);
    monts VARCHAR(200);
    montd VARCHAR(200);
    fecha VARCHAR(200);
  BEGIN
    LOOP
      FETCH cursor02
        INTO fecha, cants, monts, cantd, montd;
      EXIT WHEN cursor02%NOTFOUND;
      montoSoles:=montoSoles+to_number(monts,'999,999,990.00');
      montoDolares:=montoDolares+to_number(montd,'999,999,990.00');
    END LOOP;
    OPEN curSobresCajero FOR
    SELECT vUsu_in, to_char(montoSoles,'999,999,990.00') MONTO_S, to_char(montoDolares,'999,999,990.00') MONTO_D
    FROM dual;
    return curSobresCajero;
  end;
/* ******************************************************************* */
  FUNCTION CE_F_HTML_VOUCHER_REMITO_DU(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodRemito      IN CHAR,
                                        cIpServ_in      IN CHAR)
    RETURN VARCHAR2 IS
    vMsg_out varchar2(32767) := '';
    vFila_2  VARCHAR2(32767) := '';
    vFila_3  VARCHAR2(32767) := '';
    vFila_4  VARCHAR2(32767) := '';
    vFila_41 VARCHAR2(32767) := '';

    vFila_3_AS  VARCHAR2(32767) := '';
    vFila_4_AS  VARCHAR2(32767) := '';
    vFila_41_AS VARCHAR2(32767) := '';

    vFecha      varchar2(200);
    vNumSobresS varchar2(200);
    vMontoS     varchar2(200);
    vNumSobresD varchar2(200);
    vMontoD     varchar2(200);

    vMontotTotalS varchar2(200);
    vMontotTotalD varchar2(200);
    vUsuRem       varchar2(200);

    i NUMBER(7) := 0;


    /*cursor3 FarmaCursor := SEG_F_CUR_GET_DATA3(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cCodRemito);
    cursor4 FarmaCursor := SEG_F_CUR_GET_DATA4(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cCodRemito);
    curSobSol FarmaCursor := CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in,cCodLocal_in);
    curSobDol FarmaCursor := CE_F_LISTA_TOTALES_SD(cCodGrupoCia_in,cCodLocal_in);*/
    CURSOR cursor3 IS SELECT xx.usuario,
                             to_char(xx.total_s,'999,999,990.00') total_s,
                             to_char(xx.Total_d,'999,999,990.00') Total_d
                      FROM CE_TOTS_REMI_SD xx
                      WHERE xx.cod_grupo_cia=cCodGrupoCia_in
                      AND xx.cod_local=cCodLocal_in
                      AND xx.cod_remito=cCodRemito
                      AND xx.Ind_Remito='S';
    CURSOR cursor4 IS SELECT to_char(yy.fec_dia_vta_rem,'dd/MM/yyyy') fec_dia_vta_rem,
                             yy.cant_s,
                             to_char(yy.mont_s,'999,999,990.00') mont_s,
                             yy.cant_d,
                             to_char(yy.mont_d,'999,999,990.00') mont_d
                      FROM CE_SOBRE_REMI_SD yy
                      WHERE yy.cod_grupo_cia=cCodGrupoCia_in
                      AND yy.cod_local=cCodLocal_in
                      AND yy.cod_remito=cCodRemito
                      AND yy.Ind_Remito='S';
    CURSOR curSobSol IS SELECT to_char(zz.fec_dia_vta_rem,'dd/MM/yyyy') fec_dia_vta_rem,
                               zz.cant_s,
                               to_char(zz.mont_s,'999,999,990.00') mont_s,
                               zz.cant_d,
                               to_char(zz.mont_d,'999,999,990.00') mont_d
                        FROM CE_SOBRE_REMI_SD zz
                        WHERE zz.cod_grupo_cia=cCodGrupoCia_in
                        AND zz.cod_local=cCodLocal_in
                        AND zz.cod_remito=cCodRemito
                        AND zz.Ind_Remito='N';
    CURSOR curSobDol IS SELECT ww.usuario,
                               to_char(ww.total_s,'999,999,990.00') total_s,
                               to_char(ww.Total_d,'999,999,990.00') Total_d
                        FROM CE_TOTS_REMI_SD ww
                        WHERE ww.cod_grupo_cia=cCodGrupoCia_in
                        AND ww.cod_local=cCodLocal_in
                        AND ww.cod_remito=cCodRemito
                        AND ww.Ind_Remito='N';
    fila4 cursor4%ROWTYPE;
    fila3 cursor3%ROWTYPE;
    filasol curSobSol%ROWTYPE;
    filadol curSobDol%ROWTYPE;

    -- dubilluz 27.07.2010
    vCabecera  VARCHAR2(32767) := '';
    vFechaCreacionRemito varchar2(100) := '';
    vCuentaSoles      varchar2(100) := '';
    vCuentaDolares    varchar2(100) := '';
    vCliente          varchar2(2000) := '';
    vRecibidoDe       varchar2(2000) := '';
    vPara             varchar2(2000) := '';
    vCalle            varchar2(2000) := '';
    vTotalSoles       varchar2(2000) := '';
    vTotalDolares     varchar2(2000) := '';
    vMontoTotalSobres varchar2(2000) := '';
    vCodCia           char(3) := '';

    vCantidadSobres number;

    v_nBoveda VARCHAR2(10);

  BEGIN
    OPEN cursor4;
    LOOP
      FETCH cursor4
        INTO fila4;--vFecha, vNumSobresS, vMontoS, vNumSobresD, vMontoD;
      EXIT WHEN cursor4%NOTFOUND;
      IF (LENGTH(vFila_4) >= 32767 - 20) THEN
        i := i + 1;
        IF (i = 1) THEN
          vFila_41 := vFila_41 || vFila_4;
        END IF;
        vFila_41 := vFila_41 || ' <tr> ' || ' <td>' || fila4.fec_dia_vta_rem || '</td> ' ||
                    ' <td>' || fila4.cant_s || '</td> ' || ' <td><p>S/' ||
                    fila4.mont_s || '</p></td> ' || ' <td>' || fila4.fec_dia_vta_rem ||
                    '</td> ' || ' <td>' || fila4.cant_d || '</td> ' ||
                    ' <td><p>US$' || fila4.mont_d || '</p></td> ' || ' </tr> ';
      ELSE
        vFila_4 := vFila_4 || ' <tr> ' || ' <td>' || fila4.fec_dia_vta_rem || '</td> ' ||
                   ' <td>' || fila4.cant_s || '</td> ' || ' <td><p>S/' ||
                   fila4.mont_s || '</p></td> ' || ' <td>' || fila4.fec_dia_vta_rem || '</td> ' ||
                   ' <td>' || fila4.cant_d || '</td> ' || ' <td><p>US$' ||
                   fila4.mont_d || '</p></td> ' || ' </tr> ';

      END IF;
    END LOOP;
    CLOSE cursor4;
    -------------------------------------------------------------------------------
    OPEN cursor3;
    LOOP
      FETCH cursor3
        INTO fila3; --vUsuRem, vMontotTotalS, vMontotTotalD;
      EXIT WHEN cursor3%NOTFOUND;
      vUsuRem:=fila3.usuario;
      vFila_3 := vFila_3 || ' <tr>  ' ||
                 ' <td><strong>TOTAL</strong></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>S/.' ||
                 fila3.total_s || '</strong></p></td>  ' ||
                 ' <td><p><strong>TOTAL</strong></p></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>US$' ||
                 fila3.total_d || '</strong></p></td> ' || ' </tr>';

    END LOOP;
    CLOSE cursor3;
    dbms_output.put_line('vFila_3: '||vFila_3);
dbms_output.put_line('hito05');
    -----------------------------------------------------
    vFila_2 := vFila_2 || '   <tr> ' ||
               ' <td height="68" colspan="3"><p>REMITO N&deg; :  <strong>' ||
               cCodRemito || '</strong></p> ' || ' <p><strong>' || vUsuRem ||
               '</strong></p></td> ' || ' <td colspan="3"><center>' ||
               cCodLocal_in || '  -  ' || TRUNC(SYSDATE) ||
               '</center></td> ' || ' </tr>';
               dbms_output.put_line('COPIA');
               dbms_output.put_line(C_INICIO_MSG_2);
               dbms_output.put_line(vFila_4);
               dbms_output.put_line(vFila_41);
               dbms_output.put_line(vFila_3);
               dbms_output.put_line(vFila_2);
               dbms_output.put_line('</td></tr>');
               dbms_output.put_line(C_INI_NODEBENIR);
               dbms_output.put_line(C_DEPOSITO_SD);

    ---------------------------------------------------------------------------------------------------------------
    OPEN curSobSol;
    LOOP
      FETCH curSobSol
        INTO filasol;--vFecha, vNumSobresS, vMontoS, vNumSobresD, vMontoD;
      EXIT WHEN curSobSol%NOTFOUND;
      IF (LENGTH(vFila_4_AS) >= 32767 - 20) THEN
        i := i + 1;
        IF (i = 1) THEN
          vFila_41_AS := vFila_41_AS || vFila_4_AS;
        END IF;
        vFila_41_AS := vFila_41_AS || ' <tr> ' || ' <td>' || filasol.fec_dia_vta_rem || '</td> ' ||
                    ' <td>' || filasol.cant_s || '</td> ' || ' <td><p>S/' ||
                    filasol.mont_s || '</p></td> ' || ' <td>' || filasol.fec_dia_vta_rem ||
                    '</td> ' || ' <td>' || filasol.cant_d || '</td> ' ||
                    ' <td><p>US$' || filasol.mont_d || '</p></td> ' || ' </tr> ';
      ELSE
        vFila_4_AS := vFila_4_AS || ' <tr> ' || ' <td>' || filasol.fec_dia_vta_rem || '</td> ' ||
                   ' <td>' || filasol.cant_s || '</td> ' || ' <td><p>S/' ||
                   filasol.mont_s || '</p></td> ' || ' <td>' || filasol.fec_dia_vta_rem || '</td> ' ||
                   ' <td>' || filasol.cant_d || '</td> ' || ' <td><p>US$' ||
                   filasol.mont_d || '</p></td> ' || ' </tr> ';

      END IF;
    END LOOP;
    CLOSE curSobSol;
    ----------------------------------------------------------------------------------------------------------------
    OPEN curSobDol;
    LOOP
      FETCH curSobDol
        INTO  filadol;--vMontotTotalS, vMontotTotalD;
      EXIT WHEN curSobDol%NOTFOUND;
      vFila_3_AS := vFila_3_AS || ' <tr>  ' ||
                 ' <td><strong>TOTAL</strong></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>S/.' ||
                 filadol.total_s || '</strong></p></td>  ' ||
                 ' <td><p><strong>TOTAL</strong></p></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>US$' ||
                 filadol.Total_d || '</strong></p></td> ' || ' </tr>';

    END LOOP;
    CLOSE curSobDol;
    ----------------------------------------------------------------------------------------------------------------
        /*
        dbms_output.put_line(vFila_4_AS);
        dbms_output.put_line(vFila_41_AS);
        dbms_output.put_line(vFila_3_AS);
        dbms_output.put_line(C_FIN_MSG);
        dbms_output.put_line('FIN COPIA');
        */

   -- dubilluz - 27.07.2010
    /*
    vCabecera  VARCHAR2(32767) := '';
    vFechaCreacionRemito varchar2(100) := '';
    vCuentaSoles      varchar2(100) := '';
    vCuentaDolares    varchar2(100) := '';
    vCliente          varchar2(2000) := '';
    vRecibidoDe       varchar2(2000) := '';
    vPara             varchar2(2000) := '';
    vCalle            varchar2(2000) := '';
    vTotalSoles       varchar2(2000) := '';
    vTotalDolares     varchar2(2000) := '';
    vMontoTotalSobres varchar2(2000) := '';
    */
   vCabecera := 'REMITO&nbsp;N&deg;' || cCodRemito;

   select to_char(s.fec_crea_remito,'dd/mm/yyyy')
   into   vFechaCreacionRemito
   from   ce_remito s
   where  s.cod_remito = cCodRemito
   and    s.cod_grupo_cia = cCodGrupoCia_in
   and    s.cod_local = cCodLocal_in;


   select t.llave_tab_gral
   into   vCuentaDolares
   from   pbl_tab_gral t
   where  t.id_tab_gral = TBL_GRAL_CTA_DOLARES;

   select t.llave_tab_gral
   into   vCuentaSoles
   from   pbl_tab_gral t
   where  t.id_tab_gral = TBL_GRAL_CTA_SOLES;

  select g.raz_soc_cia
   into   vCliente
   from   PBL_LOCAL E, PBL_CIA G
   where  E.COD_GRUPO_CIA = cCodGrupoCia_in
   AND    E.COD_LOCAL     = cCodLocal_in
   AND    E.COD_GRUPO_CIA=G.COD_GRUPO_CIA
   AND    E.COD_CIA=G.COD_CIA;

	--Local/direccion
	select 
		case when E.cod_cia = '003' THEN g.nom_cia||' S.A.C.'||' - '||SUBSTR(e.desc_corta_local,1,6)
        else g.raz_soc_cia||' '|| e.cod_local||' - '||e.desc_corta_local end ,
                   nvl(e.DIREC_LOCAL, e.DIREC_LOCAL_CORTA),
				   G.COD_CIA
		INTO vRecibidoDe,vCalle,vCodCia
   from   PBL_LOCAL E, PBL_CIA G
   where  E.COD_GRUPO_CIA = cCodGrupoCia_in
   AND    E.COD_LOCAL     = cCodLocal_in
   AND    E.COD_GRUPO_CIA=G.COD_GRUPO_CIA
   AND    E.COD_CIA=G.COD_CIA;   

   --Para
	SELECT DESC_CORTA into vPara
	FROM PBL_TAB_GRAL
	where cod_apl = 'PTOVENTA'
	AND COD_TAB_GRAL = 'PROSEGUR_PARA'
	AND EST_TAB_GRAL = 'A'
	AND COD_CIA = vCodCia
	;


    SELECT to_char(xx.total_s,'999,999,990.00') total_s,
           to_char(xx.Total_d,'999,999,990.00') Total_d
    into   vTotalSoles,vTotalDolares
    FROM   CE_TOTS_REMI_SD xx
    WHERE  xx.cod_grupo_cia=cCodGrupoCia_in
    AND    xx.cod_local=cCodLocal_in
    AND    xx.cod_remito=cCodRemito
    AND    xx.Ind_Remito='S';

    /*
    select to_char(sum(f.mon_entrega_total),'999,999,990.00')
    into   vMontoTotalSobres
    from   ce_sobre s,
           ce_forma_pago_entrega f
    where  s.cod_remito = cCodRemito
    and    s.cod_grupo_cia = cCodGrupoCia_in
    and    s.cod_local = cCodLocal_in
    and    s.cod_grupo_cia = f.cod_grupo_cia
    and    s.cod_local = f.cod_local
    and    s.sec_mov_caja = f.sec_mov_caja
    and    s.sec_forma_pago_entrega = f.sec_forma_pago_entrega;
    */
    /*
    select count(1)
    into   vCantidadSobres
    from   ce_sobre s
    where  s.cod_remito = cCodRemito
    and    s.cod_grupo_cia = cCodGrupoCia_in
    and    s.cod_local = cCodLocal_in;
    */

    select count(1)
    into   vCantidadSobres
    from (
    select s.cod_sobre
    from   ce_sobre s
    where  s.cod_remito = cCodRemito
    and    s.cod_grupo_cia = cCodGrupoCia_in
    and    s.cod_local = cCodLocal_in
    union
    select s.cod_sobre
    from   ce_sobre_tmp s
    where  s.cod_remito = cCodRemito
    and    s.cod_grupo_cia = cCodGrupoCia_in
    and    s.cod_local = cCodLocal_in
    );

   --fin cambio
   /*
                                     ' <table width="510"border="0">' ||
                                    ' <tr>'||
                                    ' <td width="487" align="center" valign="top"><h1>REMITO</h1></td>' ||
                                    ' </tr>' ||
                                    ' </table>' ||
                                    ' <table width="504" border="1">' ||
                                    ' <tr>' ||
                                    ' <td height="43" colspan="3"><h2>Deposito En Soles (CTA. CTE. N&deg;)</h2></td>' ||
                                    ' <td colspan="3"><h2>Deposito En Dolares (CTA. CTE. N&deg;)</h2> </td>' ||
                                    ' </tr>' ||
                                    ' <tr>' ||
                                    ' <td width="78" height="61"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES </strong></td>' ||
                                    ' <td width="110"><strong>MONTO  S/.</strong></td>' ||
                                    ' <td width="78"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES</strong></td>' ||
                                    ' <td width="110"><strong>MONTO  US$</strong></td>' ||
                                    ' </tr>';
   */


   vMsg_out := C_INICIO_MSG_2 ||
               ' <table width="510"border="1">' ||
               ' <tr>'||
               ' <td width="487" align="center" valign="top"><h1>'||vCabecera||'</h1></td>' ||
               ' </tr>' ||
               ' <tr>'||
               ' <td width="300" valign="top"><h1>Fecha Recepci&oacute;n:&nbsp;'|| vFechaCreacionRemito ||'</h1></td>' ||
               ' </tr>' ;
               --IF vCodCia = COD_CIA_MIFARMA THEN
               --   vMsg_out := vMsg_out
               --            ||  ' <tr>'
               --            ||  ' <td width="487" valign="top"><h1>Cta Soles&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;'
               --            ||   vCuentaSoles ||'</h1></td>'
               --            ||  ' </tr>'
               --            ||  ' <tr>'
               --            ||  ' <td width="487" valign="top"><h1>Cta Dolares:&nbsp;&nbsp;'
               --            ||   vCuentaDolares||'</h1></td>'
               --            ||  ' </tr>';
               --ELSIF vCodCia = '002' THEN
			   --
               --END IF;
               vMsg_out := vMsg_out || ' <tr>'||
               ' <td width="487" valign="top"><h1>Cliente:&nbsp;&nbsp;'||vCliente||'</h1></td>' ||
               ' </tr>' ||
               ' <tr>'||
               ' <td width="487" valign="top"><h1>Recibido de:&nbsp;&nbsp;'||vRecibidoDe||'</h1></td>' ||
               ' </tr>' ||
               ' <tr>'||
               ' <td width="487" valign="top"><h1>Para:&nbsp;&nbsp;'||vPara||'</h1></td>' ||
               ' </tr>' ||
               ' <tr>'||
               ' <td width="487" valign="top"><h1>Calle:&nbsp;&nbsp;'||vCalle||'</h1></td>' ||
               ' </tr>' ||
               ' <tr>'||
               ' <td width="487" valign="top"><h1>Total Soles&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;S/.'||vTotalSoles||'</h1></td>' ||
               ' </tr>' ||
               ' <tr>'||
               ' <td width="487" valign="top"><h1>Total D&oacute;lares&nbsp;:&nbsp;US$'||vTotalDolares||'</h1></td>' ||
               ' </tr>' ||
               ' <tr>'||
               ' <td width="487" valign="top"><h1>N&uacute;mero Total de Sobres:'||vCantidadSobres||'</h1></td>' ||
               ' </tr>' ||
               ' </table>'||
               ' <table width="504" border="1">' ||
                                    ' <tr>' ||
                                    ' <td height="43" colspan="3"><h2>Deposito En Soles (CTA. CTE. N&deg;)</h2></td>' ||
                                    ' <td colspan="3"><h2>Deposito En Dolares (CTA. CTE. N&deg;)</h2> </td>' ||
                                    ' </tr>' ||
                                    ' <tr>' ||
                                    ' <td width="78" height="61"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES </strong></td>' ||
                                    ' <td width="110"><strong>MONTO  S/.</strong></td>' ||
                                    ' <td width="78"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES</strong></td>' ||
                                    ' <td width="110"><strong>MONTO  US$</strong></td>' ||
                                    ' </tr>';
    filasol := null;
    filadol := null;

    vMsg_out := vMsg_out || vFila_4 || vFila_41 ||
                vFila_3 || vFila_2 || '</td></tr>' ;
                --|| C_INI_NODEBENIR || C_DEPOSITO_SD;--C_FIN_MSG;

    vMsg_out := vMsg_out
                --|| vFila_4_AS
                --vFila_41_AS ||
                -- vFila_3_AS
                || C_FIN_MSG;

                dbms_output.put_line('hito07');
                dbms_output.put_line(vMsg_out);

    RETURN vMsg_out;

  END;

  /***********************************************************************************************************/

  PROCEDURE CE_P_CAMBIAR_COD_REMITO(cCodCia_in IN CHAR,
                                   cCodLoca_in IN CHAR,
                                   cCodRemitoNew_in IN VARCHAR2,
                                   cCodRemitoOld_in IN VARCHAR2,
                                   cCodPrecinto_in IN VARCHAR2)
  AS
    cant NUMBER(3);
  BEGIN

    SELECT COUNT(*) INTO cant
    FROM int_ce_remito a
    WHERE a.cod_grupo_cia=cCodCia_in
    AND a.cod_local=cCodLoca_in
    AND a.cod_remito=cCodRemitoOld_in;

    dbms_output.put_line('cant:'||cant||' - '||cCodRemitoOld_in);

    IF cant=0 THEN

        insert into ce_remito t
        (COD_REMITO,COD_GRUPO_CIA,COD_LOCAL,ESTADO,USU_CREA_REMITO,FEC_CREA_REMITO,IND_INT_REMITO,PRECINTO,COD_PBL_ETV)
        select cCodRemitoNew_in,COD_GRUPO_CIA,COD_LOCAL,ESTADO,USU_CREA_REMITO,FEC_CREA_REMITO,IND_INT_REMITO,
               cCodPrecinto_in,cod_pbl_etv
        from   ce_remito
        where  cod_remito = cCodRemitoOld_in
        and    Cod_Grupo_Cia = cCodCia_in
        and    cod_local = cCodLoca_in;

        update ce_sobre s
        set    s.cod_remito = cCodRemitoNew_in
        where  s.cod_sobre in (
                                select u.cod_sobre
                                from   ce_sobre u
                                where  u.cod_remito = cCodRemitoOld_in
                                and    u.Cod_Grupo_Cia = cCodCia_in
                                and    u.cod_local = cCodLoca_in
                                union
                                select t.cod_sobre
                                from   ce_sobre_tmp t
                                where  t.cod_remito = cCodRemitoOld_in
                                and    t.Cod_Grupo_Cia = cCodCia_in
                                and    t.cod_local = cCodLoca_in
                               );

        update ce_sobre_tmp s
        set    s.cod_remito = cCodRemitoNew_in
        where  s.cod_sobre in (
                                select u.cod_sobre
                                from   ce_sobre u
                                where  u.cod_remito = cCodRemitoOld_in
                                and    u.Cod_Grupo_Cia = cCodCia_in
                                and    u.cod_local = cCodLoca_in
                                union
                                select t.cod_sobre
                                from   ce_sobre_tmp t
                                where  t.cod_remito = cCodRemitoOld_in
                                and    t.Cod_Grupo_Cia = cCodCia_in
                                and    t.cod_local = cCodLoca_in
                               );


        update ce_sobre_remi_sd s
        set    s.cod_remito = cCodRemitoNew_in
        where  s.cod_remito = cCodRemitoOld_in
        and    s.Cod_Grupo_Cia = cCodCia_in
        and    s.cod_local = cCodLoca_in;

        update ce_tots_remi_sd s
        set    s.cod_remito = cCodRemitoNew_in
        where  s.cod_remito = cCodRemitoOld_in
        and    s.Cod_Grupo_Cia = cCodCia_in
        and    s.cod_local = cCodLoca_in;

        delete ce_remito r
        where  r.cod_remito = cCodRemitoOld_in
        and    r.Cod_Grupo_Cia = cCodCia_in
        and    r.cod_local = cCodLoca_in;
        commit;

    ELSE
         RAISE_APPLICATION_ERROR(-20002, 'No se puede cambiar el remito porque ya se genero Interfaz para SAP.');
    END IF;
  END;
  /* **************************************************************************** */
  FUNCTION GET_DAT_RMT_MATRICIAL(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cCodRemito      IN CHAR) RETURN VARCHAR2 IS
  vResultado varchar2(32767);
  vPara             varchar2(2000) := '';
  vLocal             varchar2(2000) := '';
  vDireccion             varchar2(2000) := '';
  vCodCia           char(3) := '';
  BEGIN
    /*
    Remito - 028 - 0010099676
    <<Razon Social y Nombre Local>> Mifarma S.A.C 028-LA MOLINA-PRE_PROD
    <<   Direccion del Local     >> AV. LA MOLINA MZA. J LOTE. 21 URB. RINCONADA DEL LAGO (NO INDICA) LIMA LIMA LA MOLINA
    << El envio se hace para     >> Boveda Prosegur - Banco Citibank
    <<        NUmero de Sobres   >> 61
    <<        Monto Soles        >> 20,486.75
    <<        Monto Dolares      >> 390.00
    */

	--Local/direccion
	select 
		case when E.cod_cia = '003' THEN g.nom_cia||' S.A.C.'||' - '||SUBSTR(e.desc_corta_local,1,6)
        else g.raz_soc_cia||' '|| e.cod_local||' - '||e.desc_corta_local end ,
                   nvl(e.DIREC_LOCAL, e.DIREC_LOCAL_CORTA),
				   G.COD_CIA
		INTO vLocal,vDireccion,vCodCia
   from   PBL_LOCAL E, PBL_CIA G
   where  E.COD_GRUPO_CIA = cCodGrupoCia_in
   AND    E.COD_LOCAL     = cCodLocal_in
   AND    E.COD_GRUPO_CIA=G.COD_GRUPO_CIA
   AND    E.COD_CIA=G.COD_CIA;
   
   --Para
	SELECT DESC_CORTA into vPara
	FROM PBL_TAB_GRAL
	where cod_apl = 'PTOVENTA'
	AND COD_TAB_GRAL = 'PROSEGUR_PARA'
	AND EST_TAB_GRAL = 'A'
	AND COD_CIA = vCodCia
	;

    select
		vLocal|| '@' ||
		vDireccion || '@' ||                 
           vPara || '@' ||
           trim(to_char((select count(1)
                          from (select s.cod_sobre
                                  from ce_sobre s
                                 where s.cod_remito = cCodRemito
                                   and s.cod_grupo_cia = cCodGrupoCia_in
                                   and s.cod_local = cCodLocal_in
                                union
                                select s.cod_sobre
                                  from ce_sobre_tmp s
                                 where s.cod_remito = cCodRemito
                                   and s.cod_grupo_cia = cCodGrupoCia_in
                                   and s.cod_local = cCodLocal_in)),
                        '999999990')) || '@' ||
           (SELECT distinct trim(TO_CHAR(V1.SOLES, '999,999,990.00')) || '@' ||
                            trim(TO_CHAR(V1.DOLARES, '999,999,990.00'))
              FROM (SELECT vt.usu_crea USU,
                           NVL(SUM(CASE
                                     WHEN vt.TIP_MONEDA = '01' THEN
                                      vt.MON_ENTREGA_TOTAL
                                   END),
                               0) SOLES,
                           NVL(SUM(CASE
                                     WHEN vt.TIP_MONEDA = '02' THEN
                                      vt.MON_ENTREGA
                                   END),
                               0) DOLARES
                      from (SELECT B.USU_CREA_REMITO usu_crea,
                                   D.TIP_MONEDA,
                                   d.mon_entrega,
                                   D.MON_ENTREGA_TOTAL
                              FROM --CE_DIA_REMITO         A,
                                   CE_REMITO             B,
                                   CE_SOBRE              C,
                                   CE_FORMA_PAGO_ENTREGA D
                             WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                               AND C.COD_LOCAL = cCodLocal_in
                               and C.cod_remito = TRIM(cCodRemito)
                               AND c.estado = 'A'
                               AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                               AND C.COD_LOCAL = D.COD_LOCAL
                               AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                               AND C.SEC_FORMA_PAGO_ENTREGA =
                                   D.SEC_FORMA_PAGO_ENTREGA
                               AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                               AND C.COD_LOCAL = B.COD_LOCAL
                               AND C.COD_REMITO = B.COD_REMITO
                            UNION all
                            SELECT REM.USU_CREA_REMITO usu_crea,
                                   TMP.TIP_MONEDA,
                                   tmp.mon_entrega,
                                   TMP.MON_ENTREGA_TOTAL
                              FROM CE_SOBRE_TMP TMP, CE_REMITO REM
                             WHERE tmp.cod_grupo_cia = cCodGrupoCia_in
                               and tmp.cod_local = cCodLocal_in
                               and tmp.cod_remito = TRIM(cCodRemito)
                               AND TMP.COD_GRUPO_CIA = REM.COD_GRUPO_CIA
                               AND TMP.COD_LOCAL = REM.COD_LOCAL
                               AND TMP.COD_REMITO = REM.COD_REMITO
                               and tmp.estado = 'A'
                               and not exists
                             (select 1
                                      from ce_sobre te
                                     where te.cod_sobre = tmp.cod_sobre
                                       and te.cod_grupo_cia = tmp.cod_grupo_cia
                                       and te.cod_local = tmp.cod_local)) vt
                     GROUP BY vt.usu_crea) V1)|| '@' ||
           (
              --ERIOS 2.4.4 No se muestra numero de cuenta
              select   d.raz_soc_cia|| '@' || to_char(c.fec_crea_remito,'ddmmyy') || '@' ||
                       nvl(PRECINTO,'XXXXXXXX') || '@' || ' '
              from     ce_remito c, pbl_cia d
              where    c.cod_grupo_cia = d.cod_grupo_cia
              and      c.cod_cia = d.cod_cia
              and      c.cod_local = cCodLocal_in
              and      c.cod_grupo_cia = cCodGrupoCia_in
              and      c.cod_remito = cCodRemito
           )
      into vResultado
      from dual;
    return vResultado;
  end;
  /* ********************************************************************** */
  FUNCTION GET_IND_IMPR_MATRI(cCodGrupoCia_in IN CHAR) RETURN VARCHAR2 IS
  vResultado varchar2(10);
  begin
    BEGIN
       SELECT L.LLAVE_TAB_GRAL
       INTO   vResultado
       FROM   PBL_TAB_GRAL L
       WHERE  L.ID_TAB_GRAL = 394;
    EXCEPTION
    WHEN OTHERS THEN
       vResultado := 'N';
    END;
    return vResultado;
  end;
  /* ********************************************************************** */
  PROCEDURE UPDATE_DATA_REMITO(CodGrupoCia IN CHAR,CodLocal IN CHAR,CodRemito IN CHAR, CodPblEtv IN CHAR, Msg OUT CHAR)
  IS

    vCodRemito CE_REMITO.COD_REMITO%TYPE;
    vCodPblEtv CE_REMITO.COD_PBL_ETV%TYPE;
    mMessage VARCHAR2(50);

    BEGIN

    SELECT
    COD_REMITO,COD_PBL_ETV INTO
    vCodRemito,vCodPblEtv FROM CE_REMITO
    WHERE COD_GRUPO_CIA=CodGrupoCia
    AND COD_LOCAL=CodLocal
    AND COD_REMITO=CodRemito;

    IF vCodPblEtv IS NULL THEN

        IF  CodPblEtv IS NOT NULL THEN

        UPDATE CE_REMITO SET COD_PBL_ETV=CodPblEtv  WHERE
        COD_GRUPO_CIA=CodGrupoCia
        AND COD_LOCAL=CodLocal
        AND COD_REMITO=CodRemito;
        COMMIT;
        mMessage:='REG.ACTUALIZADO';
        Msg:=mMessage;

        ELSE
        mMessage:='INGRESE COD_PBL_ETV NO NULLO ' ;
        Msg:=mMessage;

       END IF;

      ELSE
      mMessage:='COD_PBL_ETV NO ES NULLO';
      Msg:=mMessage;
      END IF;

      EXCEPTION
      WHEN OTHERS THEN

      DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);

    END;
  /* ********************************************************************** */


END;

/
