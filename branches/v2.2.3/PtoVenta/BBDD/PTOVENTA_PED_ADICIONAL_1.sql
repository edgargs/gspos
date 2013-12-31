--------------------------------------------------------
--  DDL for Package Body PTOVENTA_PED_ADICIONAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_PED_ADICIONAL" is

  /**********************************************************************/
  FUNCTION PED_F_CUR_LISTA_PROD_PED(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
     /* SELECT P.COD_PROD        || 'Ã' ||
         P.IND_TIPO_PROD     || 'Ã' ||
         P.DESC_PROD         || 'Ã' ||
         P.DESC_UNID_PRESENT || 'Ã' ||
         TRIM(TO_CHAR(L.STK_FISICO /L.VAL_FRAC_LOCAL ,'999990'))  || 'Ã' ||
         NVL((SELECT TRIM(TO_CHAR(LGT_PED_REP_DET.CANT_SUGERIDA,'999990'))
                FROM LGT_PED_REP_DET,
                 LGT_PED_REP_CAB
                 WHERE LGT_PED_REP_CAB.FEC_CREA_PED_REP_CAB IN (
                            SELECT MAX(LGT_PED_REP_CAB.FEC_CREA_PED_REP_CAB)
                            FROM LGT_PED_REP_CAB)
                      AND LGT_PED_REP_CAB.COD_GRUPO_CIA = LGT_PED_REP_DET.COD_GRUPO_CIA
                      AND LGT_PED_REP_CAB.COD_LOCAL     = LGT_PED_REP_DET.COD_LOCAL
                      AND LGT_PED_REP_CAB.NUM_PED_REP   = LGT_PED_REP_DET.NUM_PED_REP
                      AND LGT_PED_REP_DET.COD_PROD      = P.COD_PROD
                      AND LGT_PED_REP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND LGT_PED_REP_CAB.COD_LOCAL     = cCodLocal_in),0) || 'Ã' ||
         NVL((SELECT TRIM(TO_CHAR(LGT_PARAM_PROD_LOCAl.TOT_UNID_VTA_QS,'999990'))
                FROM LGT_PARAM_PROD_LOCAl
                WHERE LGT_PARAM_PROD_LOCAl.COD_PROD = P.COD_PROD
                AND LGT_PARAM_PROD_LOCAl.COD_GRUPO_CIA = cCodGrupoCia_in
                AND LGT_PARAM_PROD_LOCAl.COD_LOCAL     = cCodLocal_in),0) || 'Ã' ||
         NVL((SELECT TRIM(TO_CHAR(LGT_PARAM_PROD_LOCAl.TOT_UNID_VTA_RDM,'999990'))
                FROM LGT_PARAM_PROD_LOCAl
                WHERE LGT_PARAM_PROD_LOCAl.COD_PROD = P.COD_PROD
                AND LGT_PARAM_PROD_LOCAl.COD_GRUPO_CIA = cCodGrupoCia_in
                AND LGT_PARAM_PROD_LOCAl.COD_LOCAL     = cCodLocal_in),0) || 'Ã' ||
         A.NOM_LAB || 'Ã' ||
         L.STK_FISICO || 'Ã' ||
         L.VAL_FRAC_LOCAL
    FROM LGT_PROD_LOCAL L,
         LGT_PROD P,
         LGT_LAB  A
   WHERE L.COD_GRUPO_CIA     = cCodGrupoCia_in
         AND L.COD_LOCAL     = cCodLocal_in
         AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND L.COD_PROD      = P.COD_PROD
         AND A.COD_LAB       = P.COD_LAB;*/

         --MODIFICADO X DVELIZ 16.10.08
/*
        SELECT C.COD_PROD                                                  || 'Ã' ||
               CASE C.IND_TIPO_PROD
               WHEN 'V' THEN 'VIGENTE'
               WHEN 'N' THEN 'NO DISPONIBLE'
               WHEN 'D' THEN 'DESCONTINUADO'
               WHEN 'A' THEN 'AGOTADO'
               ELSE 'VENDER OTRO'
               END                                                          || 'Ã' ||
               C.DESC_PROD                                                  || 'Ã' ||
               C.DESC_UNID_PRESENT                                          || 'Ã' ||
               TRIM(TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00'))      || 'Ã' ||
               B.CANT_MAX_STK                                               || 'Ã' ||
               NVL(TO_CHAR(E.TOT_UNID_VTA_QS), ' ')                         || 'Ã' ||
               NVL(TO_CHAR(E.TOT_UNID_VTA_RDM), ' ')                        || 'Ã' ||
               D.NOM_LAB                                                    || 'Ã' ||
               A.STK_FISICO                                                 || 'Ã' ||
               A.VAL_FRAC_LOCAL                                             || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_EXHIB ,'999990')),0)                 || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_TRANSITO ,'999990')),0)              || 'Ã' ||
               NVL((SELECT TRIM(TO_CHAR(DET.CANT_SUGERIDA,'999990'))
                    FROM LGT_PED_REP_DET DET, LGT_PED_REP_CAB CAB
                    WHERE CAB.FEC_CREA_PED_REP_CAB IN (SELECT MAX(LGT_PED_REP_CAB.FEC_CREA_PED_REP_CAB)
                                                       FROM LGT_PED_REP_CAB)
                    AND CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                    AND CAB.COD_LOCAL     = DET.COD_LOCAL
                    AND CAB.NUM_PED_REP   = DET.NUM_PED_REP
                    AND DET.COD_PROD      = A.COD_PROD),0)                  || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_VTA_PER_0, '999990')),0)             || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_VTA_PER_1, '999990')),0)             || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_VTA_PER_2, '999990')),0)             || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_VTA_PER_3, '999990')),0)
        FROM LGT_PROD_LOCAL A
        INNER JOIN LGT_PROD_LOCAL_REP B ON(A.COD_PROD = B.COD_PROD)
        INNER JOIN LGT_PROD C ON(A.COD_PROD = C.COD_PROD)
        INNER JOIN LGT_LAB D ON(C.COD_LAB = D.COD_LAB)
        LEFT OUTER JOIN LGT_PARAM_PROD_LOCAL E ON(A.COD_PROD = E.COD_PROD)
        AND A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_LOCAL = cCodLocal_in;*/

        SELECT C.COD_PROD                                                   || 'Ã' ||
               CASE C.IND_TIPO_PROD
               WHEN 'V' THEN 'VIGENTE'
               WHEN 'N' THEN 'NO DISPONIBLE'
               WHEN 'D' THEN 'DESCONTINUADO'
               WHEN 'A' THEN 'AGOTADO'
               ELSE 'VENDER OTRO'
               END                                                          || 'Ã' ||
               C.DESC_PROD                                                  || 'Ã' ||
               C.DESC_UNID_PRESENT                                          || 'Ã' ||
               TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00')   || 'Ã' ||
               TO_CHAR(B.CANT_MAX_STK,'99999990')                                               || 'Ã' ||
               NVL(TO_CHAR(E.TOT_UNID_VTA_QS,'99999990'),'        ')                         || 'Ã' ||
               NVL(TO_CHAR(E.TOT_UNID_VTA_RDM,'99999990'),'        ')                         || 'Ã' ||
               D.NOM_LAB                                                    || 'Ã' ||
               --A.STK_FISICO                                                 || 'Ã' ||
               TRIM(TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00'))   || 'Ã' ||
               A.VAL_FRAC_LOCAL                                             || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_EXHIB ,'999990')),0)                 || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_TRANSITO ,'999990')),0)              || 'Ã' ||
               NVL(TO_CHAR(PEDIDO.CANT_SUGERIDA,'999990'),0)                || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_VTA_PER_0, '999990.00')),0)             || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_VTA_PER_1, '999990.00')),0)             || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_VTA_PER_2, '999990.00')),0)             || 'Ã' ||
               NVL(TRIM(TO_CHAR(B.CANT_VTA_PER_3, '999990.00')),0)
        FROM LGT_PROD C,
             LGT_PROD_LOCAL A,
             LGT_LAB D,
             LGT_PARAM_PROD_LOCAL E,
             LGT_PROD_LOCAL_REP B,
             (
                SELECT DET.COD_PROD, DET.CANT_SUGERIDA
                FROM LGT_PED_REP_CAB CAB,
                     LGT_PED_REP_DET DET
                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.NUM_PED_REP = (SELECT MAX(NUM_PED_REP) FROM LGT_PED_REP_CAB)
                  AND CAB.EST_PED_REP = 'E'
                  AND DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                  AND DET.COD_LOCAL = CAB.COD_LOCAL
                  AND DET.NUM_PED_REP = CAB.NUM_PED_REP
             ) PEDIDO
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.EST_PROD = 'A'
          AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND A.COD_LOCAL = cCodLocal_in
          AND A.COD_PROD = C.COD_PROD
          AND D.COD_LAB = C.COD_LAB
          AND E.COD_GRUPO_CIA(+) = A.COD_GRUPO_CIA
          AND E.COD_LOCAL(+) = A.COD_LOCAL
          AND E.COD_PROD(+) = A.COD_PROD
          AND B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
          AND B.COD_LOCAL = A.COD_LOCAL
          AND B.COD_PROD = A.COD_PROD
          AND PEDIDO.COD_PROD(+) = C.COD_PROD;
    RETURN curProd;
  END;

  /****************************************************************************/
  FUNCTION PED_F_CUR_DET_PROD_PED(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
      SELECT NVL(TRIM(TO_CHAR(D.CANT_EXHIB ,'999990')),0)               || 'Ã' ||
         NVL(TRIM(TO_CHAR(D.CANT_TRANSITO ,'999990')),0)                || 'Ã' ||
         NVL(TRIM(TO_CHAR(A.CANT_SUGERIDA, '999990')),0)                || 'Ã' ||
         NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_0, '999990.00')),0)               || 'Ã' ||
         NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_1, '999990.00')),0)               || 'Ã' ||
         NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_2, '999990.00')),0)               || 'Ã' ||
         NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_3, '999990.00')),0)

      FROM LGT_PROD_LOCAL_REP D
      INNER JOIN LGT_PED_REP_DET A ON(A.COD_PROD = D.COD_PROD)
      INNER JOIN LGT_PED_REP_CAB B ON (A.NUM_PED_REP = B.NUM_PED_REP)
      WHERE B.FEC_CREA_PED_REP_CAB IN (SELECT MAX(LGT_PED_REP_CAB.FEC_CREA_PED_REP_CAB)
                                       FROM LGT_PED_REP_CAB)
      AND D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND D.COD_LOCAL = cCodLocal_in
      AND D.COD_PROD = cCodProd_in;


      /*SELECT

       NVL(TRIM(TO_CHAR(D.CANT_EXHIB ,'999990')),0)                   || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_TRANSITO ,'999990')),0)                || 'Ã' ||


       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_0, '999990')),0)               || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_1, '999990')),0)               || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_2, '999990')),0)               || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_3, '999990')),0)
    FROM LGT_PROD_LOCAL_REP D
    INNER JOIN
    WHERE D.COD_GRUPO_CIA     = cCodGrupoCia_in
          AND D.COD_LOCAL     = cCodLocal_in
          AND D.COD_PROD      = cCodProd_in;*/


/*
      SELECT
       NVL(TRIM(TO_CHAR(D.CANT_DIA_ROT*D.VAL_ROT_PROD ,'999990')),0)  || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_MIN_STK ,'999990')),0)                 || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_MAX_STK ,'999990')),0)                 || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_DIA_ROT ,'999990')),0)                 || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.NUM_DIAS ,'999990')),0)                     || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_EXHIB ,'999990')),0)                   || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.STK_TRANSITO ,'999990')),0)                 || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.VAL_ROT_PROD,'999990')),0)              || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_0, '999990')),0)           || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_1, '999990')),0)           || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_2, '999990')),0)           || 'Ã' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_3, '999990')),0)           || 'Ã' ||
       TRIM(nvl(TO_CHAR(D.Cant_Adic,'999990'),'0'))
    FROM LGT_PED_REP_DET D,
		 LGT_PED_REP_CAB C,
		 LGT_PROD_LOCAL L,
		 LGT_PROD P,
		 LGT_LAB  A
    WHERE C.COD_GRUPO_CIA     = cCodGrupoCia_in
          AND C.COD_LOCAL     = cCodLocal_in
          AND D.COD_PROD      = cCodProd_in
          AND C.FEC_CREA_PED_REP_CAB IN (SELECT MAX(FEC_CREA_PED_REP_CAB) FROM LGT_PED_REP_CAB)
          AND P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A')
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL     = D.COD_LOCAL
          AND C.NUM_PED_REP   = D.NUM_PED_REP
          AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND D.COD_LOCAL     = L.COD_LOCAL
          AND D.COD_PROD      = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD      = P.COD_PROD
          AND P.COD_LAB       = A.COD_LAB;
*/

    RETURN curProd;
  END;

  /**************************************************************************/
  PROCEDURE PED_P_INSER_PED_REP_ADI_LOCAL(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodProd_in     IN CHAR,
                                          cCantSol_in     IN NUMBER,
                                          cCantAuto_in    IN NUMBER,
                                          cIndAutori_in   IN CHAR,
                                          cCodUsu_in      IN VARCHAR2)
  AS
  vCount NUMBER(10);
  BEGIN
    SELECT count(*) INTO vCount
    FROM LGT_PARAM_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND COD_PROD = cCodProd_in;

    IF (vCount = 0) THEN
      INSERT INTO LGT_PARAM_PROD_LOCAL(
              COD_GRUPO_CIA,
              COD_LOCAL,
              COD_PROD,
              TOT_UNID_VTA_QS,
              TOT_UNID_VTA_RDM,
              IND_AUTORIZADO,
              USU_CREA_PARAM_PROD_LOCAL,
              FEC_CREA_PARAM_PROD_LOCAL,
              USU_MOD_PARAM_PROD_LOCAL,
              FEC_MOD_PARAM_PROD_LOCAL,
              FEC_PROCESA_MATRIZ,
              USU_PROCESA_MATRIZ,
              IND_ACTIVO)
      VALUES( cCodGrupoCia_in,
              cCodLocal_in,
              cCodProd_in,
              cCantSol_in,
              cCantAuto_in,
              cIndAutori_in,
              cCodUsu_in,
              SYSDATE,
              NULL,
              NULL,
              NULL,
              NULL,
              NULL);
      COMMIT;
    ELSIF (vCount > 0) THEN
      UPDATE LGT_PARAM_PROD_LOCAL
            SET TOT_UNID_VTA_QS = cCantSol_in,
            IND_AUTORIZADO = cIndAutori_in,
            USU_MOD_PARAM_PROD_LOCAL = cCodUsu_in,
            FEC_MOD_PARAM_PROD_LOCAL = SYSDATE,
-- 2009-07-20 JOLIVA: Se considera el nuevo campo FEC_PROCESO_PVM para saber si ya fue procesado un PVM del local
            FEC_PROCESO_PVM = NULL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND COD_PROD = cCodProd_in;

      COMMIT;
    END IF;

  END;

  /**************************************************************************/
  PROCEDURE PED_P_INSER_PED_REP_ADI_MATRIZ(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodProd_in     IN CHAR,
                                          cCantAuto_in    IN NUMBER,
                                          cIndAutori_in   IN CHAR,
                                          cCodUsu_in      IN VARCHAR2)
  AS
  BEGIN
      UPDATE LGT_PARAM_PROD_LOCAL
      SET TOT_UNID_VTA_RDM = cCantAuto_in,
          IND_AUTORIZADO = cIndAutori_in,
          USU_PROCESA_MATRIZ = cCodUsu_in,
          FEC_PROCESA_MATRIZ = SYSDATE,
-- 2009-07-20 JOLIVA: Se considera el nuevo campo FEC_PROCESO_PVM para saber si ya fue procesado un PVM del local
          FEC_PROCESO_PVM = NULL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND COD_PROD = cCodProd_in;
      COMMIT;
  END;

  /****************************************************************************/
  FUNCTION PED_F_CUR_LISTA_PED_BK(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
    BEGIN
      OPEN curProd FOR
        /*SELECT
          A.TOT_UNID_VTA_QS                             || 'Ã' ||
          A.TOT_UNID_VTA_RDM                            || 'Ã' ||
          NVL(A.USU_CREA_PARAM_PROD_LOCAL,' ')          || 'Ã' ||
          NVL(TO_CHAR(A.FEC_CREA_PARAM_PROD_LOCAL),' ') || 'Ã' ||
          DECODE(A.USU_PROCESA_MATRIZ,NULL,'LOCAL','MATRIZ')|| 'Ã' ||
          /*NVL(A.USU_MOD_PARAM_PROD_LOCAL,' ')           || 'Ã' ||
          NVL(TO_CHAR(A.FEC_MOD_PARAM_PROD_LOCAL),' ')  || 'Ã' ||
          NVL(A.USU_PROCESA_MATRIZ,' ')                 || 'Ã' ||
          NVL(TO_CHAR(A.FEC_PROCESA_MATRIZ),' ')
        FROM LGT_PARAM_PROD_LOCAL_BK A, LGT_PROD B
        WHERE A.COD_PROD = B.COD_PROD
          AND A.COD_LOCAL = cCodLocal_in
          AND A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_PROD = cCodProd_in*/

          SELECT
            A.TOT_UNID_VTA_QS                                                                     || 'Ã' ||
            A.TOT_UNID_VTA_RDM                                                                    || 'Ã' ||
            DECODE(A.USU_PROCESA_MATRIZ, NULL,
                    DECODE(A.USU_CREA_PARAM_PROD_LOCAL, NULL,
                          A.USU_MOD_PARAM_PROD_LOCAL,
                          A.USU_CREA_PARAM_PROD_LOCAL), A.USU_PROCESA_MATRIZ)                     || 'Ã' ||
            DECODE(TO_CHAR(A.FEC_PROCESA_MATRIZ,'DD/MM/YYYY HH24:MI:SS'), NULL,
                    DECODE(TO_CHAR(A.FEC_CREA_PARAM_PROD_LOCAL,'DD/MM/YYYY HH24:MI:SS'), NULL,
                           TO_CHAR(A.FEC_MOD_PARAM_PROD_LOCAL,'DD/MM/YYYY HH24:MI:SS'),
                           TO_CHAR(A.FEC_CREA_PARAM_PROD_LOCAL,'DD/MM/YYYY HH24:MI:SS')),
                           TO_CHAR(A.FEC_PROCESA_MATRIZ,'DD/MM/YYYY HH24:MI:SS'))                 || 'Ã' ||
            DECODE(A.USU_PROCESA_MATRIZ, NULL,'LOCAL','MATRIZ')
          FROM LGT_PARAM_PROD_LOCAL_BK A, LGT_PROD B
          WHERE A.COD_PROD = B.COD_PROD
            AND A.COD_LOCAL = cCodLocal_in
            AND A.COD_GRUPO_CIA = cCodGrupoCia_in
            AND A.COD_PROD = cCodProd_in
        ORDER BY A.INDICE DESC;

      RETURN curProd;
  END;

/****************************************************************************/
  FUNCTION PED_F_CUR_CARGAR_MESES
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
      SELECT TO_CHAR(ADD_MONTHS(SYSDATE,-0),'MON')                          || 'Ã' ||
         TO_CHAR(ADD_MONTHS(SYSDATE,-1),'MON')                          || 'Ã' ||
         TO_CHAR(ADD_MONTHS(SYSDATE,-2),'MON')                          || 'Ã' ||
         TO_CHAR(ADD_MONTHS(SYSDATE,-3),'MON')
      FROM DUAL;
    RETURN curProd;
  END;

/****************************************************************************/
  FUNCTION PED_F_CHAR_GET_TRANSITO( cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN VARCHAR2
  IS
--    curProd FarmaCursor;
    v_nTrans  VARCHAR2(10000);
  BEGIN
      BEGIN
        SELECT NVL(TRIM(TO_CHAR(NVL(SUM(CANT_ENVIADA_MATR), 0), '999990')), 0)
          INTO v_nTrans
          FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.COD_LOCAL = cCodLocal_in
           AND D.COD_PROD = cCodProd_in
           AND C.TIP_NOTA_ES = '01'
           AND D.IND_PROD_AFEC = 'N'
           AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
           AND C.COD_LOCAL = D.COD_LOCAL
           AND C.NUM_NOTA_ES = D.NUM_NOTA_ES;
        --GROUP BY D.COD_GRUPO_CIA,D.COD_LOCAL,D.COD_PROD;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          v_nTrans := '0';
      END;
    RETURN v_nTrans;
  END;
  /*
  */
end ;

/
