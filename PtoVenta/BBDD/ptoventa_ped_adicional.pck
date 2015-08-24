CREATE OR REPLACE PACKAGE "PTOVENTA_PED_ADICIONAL" is

  -- Author  : DUBILLUZ
  -- Created : 09/09/2008 12:18:41 p.m.
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;
  cCodGrupoIns     CHAR(3):='010';--codGrupo insumo.


  --Descripcion: LISTA LOS PRODUCTOS CON EL SUGERIDO DEL ULTIMO PEDIDO DE REPOSICION
  --Fecha       Usuario		Comentario
  --10/09/2008  DVELIZ    CREACION
  FUNCTION PED_F_CUR_LISTA_PROD_PED(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: OBTIENE EL DETALLE DE REPOSICION POR PEDIDO
  --Fecha       Usuario		Comentario
  --10/09/2008  DVELIZ    CREACION
  FUNCTION PED_F_CUR_DET_PROD_PED(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: INSERTA CANTIDAD SOLICITADA AL PEDIDO ADICIONAL LOCAL
  --Fecha       Usuario		Comentario
  --10/09/2008  DVELIZ    CREACION
  PROCEDURE PED_P_INSER_PED_REP_ADI_LOCAL(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodProd_in     IN CHAR,
                                          cCantSol_in     IN NUMBER,
                                          cCantAuto_in    IN NUMBER,
                                          cIndAutori_in   IN CHAR,
                                          cCodUsu_in      IN VARCHAR2);

  --Descripcion: INSERTA CANTIDAD SOLICITADA AL PEDIDO ADICIONAL MATRIZ
  --Fecha       Usuario		Comentario
  --10/09/2008  DVELIZ    CREACION
  PROCEDURE PED_P_INSER_PED_REP_ADI_MATRIZ(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodProd_in     IN CHAR,
                                          cCantAuto_in    IN NUMBER,
                                          cIndAutori_in   IN CHAR,
                                          cCodUsu_in      IN VARCHAR2);

  --Descripcion: MUESTRA EL RESUMEN DEL PEDIDO ADICIONAL
  --Fecha       Usuario		Comentario
  --12/09/2008  DVELIZ    CREACION
  FUNCTION PED_F_CUR_LISTA_PED_BK(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN FarmaCursor;

  FUNCTION PED_F_CUR_CARGAR_MESES
  RETURN FarmaCursor;

  FUNCTION PED_F_CHAR_GET_TRANSITO (cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR)
  RETURN VARCHAR2;


  --Descripcion: MUESTRA PRODUCTO Y SU MOVIMIENTO EN LOS ULTIMOS MESES
  --Fecha       Usuario		Comentario
  --12/09/2008  DVELIZ    CREACION
    FUNCTION PED_F_PROD_PED_INS(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCodProd        IN CHAR)

    RETURN FarmaCursor;
/****************************************************************************/
  --Descripcion: OBTIENE EL STOCK MAXIMO DEL PRODUCTO SEGUN SU PVM INGRESADO
  --Fecha       Usuario		Comentario
  --09/04/2015  EMAQUERA  CREACION
  FUNCTION PED_F_STK_MAXIMO( cCodLocal_in    IN CHAR,
                             cCodProd_in     IN CHAR,
                             cCantPvm       IN CHAR)
  RETURN VARCHAR2;
end;
/
CREATE OR REPLACE PACKAGE BODY "PTOVENTA_PED_ADICIONAL" is

  /**********************************************************************/
  FUNCTION PED_F_CUR_LISTA_PROD_PED(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
/*          --MODIFICADO X EMAQUERA 10/06/2014
               SELECT C.COD_PROD                                          || '�' ||
               CASE C.IND_TIPO_PROD
               WHEN 'V' THEN 'VIGENTE'
               WHEN 'N' THEN 'NO DISPONIBLE'
               WHEN 'D' THEN 'DESCONTINUADO'
               WHEN 'A' THEN 'AGOTADO'
               ELSE 'VENDER OTRO'
               END                                                         || '�' ||
               C.DESC_PROD                                                 || '�' ||
               C.DESC_UNID_PRESENT                                         || '�' ||
               TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00')        || '�' ||
               TO_CHAR(B.CANT_MAX_STK,'99999990')                                            || '�' ||
               NVL(TO_CHAR(E.TOT_UNID_VTA_QS,'99999990'),'        ')                         || '�' ||
               NVL(TO_CHAR((CASE WHEN TRUNC(E.IND_ACTIVO) <= TRUNC(SYSDATE) THEN 0
                                                       ELSE E.TOT_UNID_VTA_RDM
                                                       END),'99999990'),'        ')          || '�' ||
               D.NOM_LAB                                                   || '�' ||
               --A.STK_FISICO                                              || '�' ||
               TRIM(TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00'))  || '�' ||
               A.VAL_FRAC_LOCAL                                            || '�' ||
               NVL(TRIM(TO_CHAR(B.MIN_EXHIB_FIJO ,'999990')),0)            || '�' ||
               NVL(TRIM(TO_CHAR(B.STK_TRANSITO ,'999990')),0)              || '�' ||
               NVL(TO_CHAR(PEDIDO.CANT_SUGERIDA,'999990'),0)               || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_0, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_1, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_2, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_3, '999990.00')),0)
        FROM LGT_PROD C,
             LGT_PROD_LOCAL A,
             LGT_LAB D,
             LGT_PARAM_PROD_LOCAL E,
             MF_LGT_PROD_LOCAL_REP B, --LGT_PROD_LOCAL_REP B,
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
     --     AND B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
          AND B.COD_LOCAL_MF = A.COD_LOCAL
          AND B.COD_PROD_MF = A.COD_PROD
          AND PEDIDO.COD_PROD(+) = C.COD_PROD;*/

          --MODIFICADO X EMAQUERA 10/04/2015
               SELECT C.COD_PROD                                          || '�' ||
               CASE C.IND_TIPO_PROD
               WHEN 'V' THEN 'VIGENTE'
               WHEN 'N' THEN 'NO DISPONIBLE'
               WHEN 'D' THEN 'DESCONTINUADO'
               WHEN 'A' THEN 'AGOTADO'
               ELSE 'VENDER OTRO'
               END                                                         || '�' ||
               C.DESC_PROD                                                 || '�' ||
               C.DESC_UNID_PRESENT                                         || '�' ||
               TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00')        || '�' ||
               NVL(TO_CHAR((CASE WHEN E.IND_ACTIVO IS NULL THEN 0
                                 WHEN TRUNC(E.IND_ACTIVO) <= TRUNC(SYSDATE) THEN 0
                                 WHEN E.IND_AUTORIZADO='S' AND TRUNC(E.IND_ACTIVO) <= TRUNC(SYSDATE)  THEN E.TOT_UNID_VTA_RDM
                                                       ELSE E.TOT_UNID_VTA_RDM
                                                       END),'99999990'),0) || '�' ||
               NVL(TO_CHAR((CASE WHEN TRUNC(E.IND_ACTIVO) <= TRUNC(SYSDATE) THEN 0
                                                       ELSE E.TOT_UNID_VTA_QS
                                                       END),'99999990'),'        ')          || '�' ||
               NVL(TO_CHAR((CASE WHEN E.IND_ACTIVO IS NULL THEN NULL
                                 WHEN TRUNC(E.IND_ACTIVO) <= TRUNC(SYSDATE) THEN NULL
                                 WHEN E.IND_AUTORIZADO='N' THEN NULL
                                                       ELSE E.TOT_UNID_VTA_RDM
                                                       END),'99999990'),'        ')          || '�' ||
               D.NOM_LAB                                                   || '�' ||
               --A.STK_FISICO                                              || '�' ||
               TRIM(TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00'))  || '�' ||
               A.VAL_FRAC_LOCAL                                            || '�' ||
               NVL(TRIM(TO_CHAR(B.MIN_EXHIB_FIJO ,'999990')),0)            || '�' ||
               NVL(TRIM(TO_CHAR(B.STK_TRANSITO ,'999990')),0)              || '�' ||
               NVL(TO_CHAR(PEDIDO.CANT_SUGERIDA,'999990'),0)               || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_0, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_1, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_2, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_3, '999990.00')),0)
        FROM LGT_PROD C,
             LGT_PROD_LOCAL A,
             LGT_LAB D,
             LGT_PARAM_PROD_LOCAL E,
             MF_LGT_PROD_LOCAL_REP B, --LGT_PROD_LOCAL_REP B,
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
     --     AND B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
          AND B.COD_LOCAL_MF = A.COD_LOCAL
          AND B.COD_PROD_MF = A.COD_PROD
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
      SELECT NVL(TRIM(TO_CHAR(D.CANT_EXHIB ,'999990')),0)               || '�' ||
         NVL(TRIM(TO_CHAR(D.CANT_TRANSITO ,'999990')),0)                || '�' ||
         NVL(TRIM(TO_CHAR(A.CANT_SUGERIDA, '999990')),0)                || '�' ||
         NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_0, '999990.00')),0)               || '�' ||
         NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_1, '999990.00')),0)               || '�' ||
         NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_2, '999990.00')),0)               || '�' ||
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

       NVL(TRIM(TO_CHAR(D.CANT_EXHIB ,'999990')),0)                   || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_TRANSITO ,'999990')),0)                || '�' ||


       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_0, '999990')),0)               || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_1, '999990')),0)               || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_2, '999990')),0)               || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_3, '999990')),0)
    FROM LGT_PROD_LOCAL_REP D
    INNER JOIN
    WHERE D.COD_GRUPO_CIA     = cCodGrupoCia_in
          AND D.COD_LOCAL     = cCodLocal_in
          AND D.COD_PROD      = cCodProd_in;*/


/*
      SELECT
       NVL(TRIM(TO_CHAR(D.CANT_DIA_ROT*D.VAL_ROT_PROD ,'999990')),0)  || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_MIN_STK ,'999990')),0)                 || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_MAX_STK ,'999990')),0)                 || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_DIA_ROT ,'999990')),0)                 || '�' ||
       NVL(TRIM(TO_CHAR(D.NUM_DIAS ,'999990')),0)                     || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_EXHIB ,'999990')),0)                   || '�' ||
       NVL(TRIM(TO_CHAR(D.STK_TRANSITO ,'999990')),0)                 || '�' ||
       NVL(TRIM(TO_CHAR(D.VAL_ROT_PROD,'999990')),0)              || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_0, '999990')),0)           || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_1, '999990')),0)           || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_2, '999990')),0)           || '�' ||
       NVL(TRIM(TO_CHAR(D.CANT_VTA_PER_3, '999990')),0)           || '�' ||
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
          A.TOT_UNID_VTA_QS                             || '�' ||
          A.TOT_UNID_VTA_RDM                            || '�' ||
          NVL(A.USU_CREA_PARAM_PROD_LOCAL,' ')          || '�' ||
          NVL(TO_CHAR(A.FEC_CREA_PARAM_PROD_LOCAL),' ') || '�' ||
          DECODE(A.USU_PROCESA_MATRIZ,NULL,'LOCAL','MATRIZ')|| '�' ||
          /*NVL(A.USU_MOD_PARAM_PROD_LOCAL,' ')           || '�' ||
          NVL(TO_CHAR(A.FEC_MOD_PARAM_PROD_LOCAL),' ')  || '�' ||
          NVL(A.USU_PROCESA_MATRIZ,' ')                 || '�' ||
          NVL(TO_CHAR(A.FEC_PROCESA_MATRIZ),' ')
        FROM LGT_PARAM_PROD_LOCAL_BK A, LGT_PROD B
        WHERE A.COD_PROD = B.COD_PROD
          AND A.COD_LOCAL = cCodLocal_in
          AND A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_PROD = cCodProd_in*/

          SELECT
            A.TOT_UNID_VTA_QS                                                                     || '�' ||
            A.TOT_UNID_VTA_RDM                                                                    || '�' ||
            DECODE(A.USU_PROCESA_MATRIZ, NULL,
                    DECODE(A.USU_CREA_PARAM_PROD_LOCAL, NULL,
                          A.USU_MOD_PARAM_PROD_LOCAL,
                          A.USU_CREA_PARAM_PROD_LOCAL), A.USU_PROCESA_MATRIZ)                     || '�' ||
            DECODE(TO_CHAR(A.FEC_PROCESA_MATRIZ,'DD/MM/YYYY HH24:MI:SS'), NULL,
                    DECODE(TO_CHAR(A.FEC_CREA_PARAM_PROD_LOCAL,'DD/MM/YYYY HH24:MI:SS'), NULL,
                           TO_CHAR(A.FEC_MOD_PARAM_PROD_LOCAL,'DD/MM/YYYY HH24:MI:SS'),
                           TO_CHAR(A.FEC_CREA_PARAM_PROD_LOCAL,'DD/MM/YYYY HH24:MI:SS')),
                           TO_CHAR(A.FEC_PROCESA_MATRIZ,'DD/MM/YYYY HH24:MI:SS'))                 || '�' ||
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
      SELECT TO_CHAR(ADD_MONTHS(SYSDATE,-0),'MON')                          || '�' ||
         TO_CHAR(ADD_MONTHS(SYSDATE,-1),'MON')                          || '�' ||
         TO_CHAR(ADD_MONTHS(SYSDATE,-2),'MON')                          || '�' ||
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
  FUNCTION PED_F_PROD_PED_INS(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCodProd        IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
               SELECT C.COD_PROD                                          || '�' ||
               CASE C.IND_TIPO_PROD
               WHEN 'V' THEN 'VIGENTE'
               WHEN 'N' THEN 'NO DISPONIBLE'
               WHEN 'D' THEN 'DESCONTINUADO'
               WHEN 'A' THEN 'AGOTADO'
               ELSE 'VENDER OTRO'
               END                                                         || '�' ||
               C.DESC_PROD                                                 || '�' ||
               C.DESC_UNID_PRESENT                                         || '�' ||
               TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00')        || '�' ||
               TO_CHAR(B.CANT_MAX_STK,'99999990')                                            || '�' ||
               NVL(TO_CHAR(E.TOT_UNID_VTA_QS,'99999990'),'        ')                         || '�' ||
               NVL(TO_CHAR((CASE WHEN TRUNC(E.IND_ACTIVO) <= TRUNC(SYSDATE) THEN 0
                                                       ELSE E.TOT_UNID_VTA_RDM
                                                       END),'99999990'),'        ')          || '�' ||
               D.NOM_LAB                                                   || '�' ||
               --A.STK_FISICO                                              || '�' ||
               TRIM(TO_CHAR(A.STK_FISICO /A.VAL_FRAC_LOCAL ,'999990.00'))  || '�' ||
               A.VAL_FRAC_LOCAL                                            || '�' ||
               NVL(TRIM(TO_CHAR(B.MIN_EXHIB_FIJO ,'999990')),0)            || '�' ||
               NVL(TRIM(TO_CHAR(B.STK_TRANSITO ,'999990')),0)              || '�' ||
               NVL(TO_CHAR(PEDIDO.CANT_SUGERIDA,'999990'),0)               || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_0, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_1, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_2, '999990.00')),0)          || '�' ||
               NVL(TRIM(TO_CHAR(B.UND_VTA_MES_3, '999990.00')),0)
        FROM LGT_PROD C,
             LGT_PROD_LOCAL A,
             LGT_LAB D,
             LGT_PARAM_PROD_LOCAL E,
             MF_LGT_PROD_LOCAL_REP B, --LGT_PROD_LOCAL_REP B,
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
     --     AND B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
          AND B.COD_LOCAL_MF = A.COD_LOCAL
          AND B.COD_PROD_MF = A.COD_PROD
          AND PEDIDO.COD_PROD(+) = C.COD_PROD
          AND C.COD_GRUPO_REP    = cCodGrupoIns
          AND C.COD_PROD         = cCodProd;

    RETURN curProd;
  END;
/****************************************************************************/
  FUNCTION PED_F_STK_MAXIMO( cCodLocal_in    IN CHAR,
                             cCodProd_in     IN CHAR,
                             cCantPvm       IN CHAR)
  RETURN VARCHAR2
  IS
--    curProd FarmaCursor;
    v_StkMax  VARCHAR2(10000);
    flg       CHAR(1); 
  BEGIN
      BEGIN
       SELECT NVL(LLAVE_TAB_GRAL,'N') INTO flg FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL='494';
       
       IF flg = 'S' THEN
       SELECT REP_3_CADENAS_MIFARMA.F_GET_STK_CALCULADO('MAX',
                                                         99999,
                                                         cCantPvm,
                                                         7,
                                                         cCodLocal_in,
                                                         cCodProd_in
                                                         ) INTO v_StkMax
                                                         FROM DUAL;
       ELSE
         v_StkMax := 'N'; -- DESHABILITA LA CASILLA DEL MENSAJE DE STK MAXIMO
       END IF;                                                                                                                         

      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          v_StkMax := '0';
      END;
    RETURN v_StkMax;
  END;
/****************************************************************************/  
end ;
/
