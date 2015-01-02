CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_REPORTE_PACK_DU" AS

  -- Author  : JCALLO
  -- Created : 28/10/2008
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion:
  --Fecha       Usuario		Comentario
  --28/10/2008  JCALLO    CREACION
  --07/08/2014  ERIOS       Se agregan parametros de local
  PROCEDURE CARGA_DETALLE_CABECERA(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,fecInicio in char, fecFin    in char);

  PROCEDURE CARGA_PEDIDO_X_PACK ( fecInicio in char, fecFin    in char);

 PROCEDURE CARGA_REPORTE_PACK ( fecInicio in char, fecFin    in char);


  PROCEDURE DROP_TABLAS;

  PROCEDURE DELETE_TABLAS (fecInicio in char, fecFin    in char);

  PROCEDURE CREA_RESUMEN(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,fecInicio in char, fecFin    in char);


END PTOVENTA_REPORTE_PACK_DU;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_REPORTE_PACK_DU" AS

  /************************************************************* */

  PROCEDURE CARGA_DETALLE_CABECERA(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,fecInicio in char, fecFin    in char)
  IS

  BEGIN
	--ERIOS 2.4.5 Cambios proyecto Conveniencia
   INSERT INTO V_RES_VTA_REP_PACK
        (COD_GRUPO_CIA,
         COD_LOCAL,
         NUM_PED_VTA,
         FEC_PED_VTA,
         VAL_NETO_PED_VTA,
         VAL_REDONDEO_PED_VTA,
         SEC_PED_VTA_DET,
         COD_PROD,
         CANT_ATENDIDA,
         VAL_FRAC,
         VAL_PREC_VTA,
         VAL_PREC_TOTAL,
         COD_PROM,
         VAL_PREC_PUBLIC)
        SELECT C.COD_GRUPO_CIA,
               C.COD_LOCAL,
               C.NUM_PED_VTA,
               C.FEC_PED_VTA,
               C.VAL_NETO_PED_VTA,
               C.VAL_REDONDEO_PED_VTA,
               D.SEC_PED_VTA_DET,
               D.COD_PROD,
               D.CANT_ATENDIDA,
               D.VAL_FRAC,
               D.VAL_PREC_VTA,
               D.VAL_PREC_TOTAL,
               D.COD_PROM,
               D.VAL_PREC_PUBLIC
          FROM VTA_PEDIDO_VTA_CAB C, VTA_PEDIDO_VTA_DET D
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.COD_LOCAL = cCodLocal_in
           AND C.FEC_PED_VTA BETWEEN
               TO_DATE(fecInicio || ' 00:00:00', 'dd/MM/yyyy HH24:MI:SS') AND
               TO_DATE(fecFin    || ' 23:59:59', 'dd/MM/yyyy HH24:MI:SS')
           AND C.EST_PED_VTA = 'C'
           AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
           AND C.COD_LOCAL = D.COD_LOCAL
           AND C.NUM_PED_VTA = D.NUM_PED_VTA;

  END;
/* ******************************************************************************* */
 PROCEDURE CARGA_PEDIDO_X_PACK (fecInicio in char, fecFin    in char)
  IS

  BEGIN

  FOR U IN (
            SELECT P.COD_PROM
              FROM VTA_PROMOCION P
             WHERE p.cod_prom IN (
                                  SELECT P.COD_PROM
                                  FROM VTA_PROMOCION P
                                  WHERE P.FEC_PROMOCION_FIN >= TO_DATE(fecInicio, 'dd/MM/yyyy')
/*
                                  SELECT P.COD_PROM
                                  FROM VTA_PROMOCION P
                                  WHERE P.DESC_CORTA_PROM LIKE '%ENSOY%'
                                  AND   P.FEC_PROMOCION_INICIO = '01/04/2010'
                                  AND   P.FEC_PROMOCION_FIN    = '15/06/2010'
*/
                                  )
               AND P.COD_GRUPO_CIA = '001'
           )
         LOOP
--         DBMS_OUTPUT.PUT_LINE('U: ' || U.COD_PROM);

     INSERT INTO V_RES_VENTA_LOCAL_PACK
      (COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, COD_PROM)
      SELECT '001' COD_GRUPO_CIA, AA.COD_LOCAL, AA.NUM_PED_VTA, AA.COD_PROM
        FROM (SELECT COD_LOCAL,
                     NUM_PED_VTA,
                     COD_PROM,
                     COUNT(DISTINCT A.COD_PROD) CANT_PROD
                FROM (SELECT R1.COD_LOCAL,
                             R1.NUM_PED_VTA,
                             R1.COD_PROD,
                             COUNT(DISTINCT R1.VAL_PREC_VTA) CANTIDAD_PRECIOS_PED
                        FROM V_RES_VTA_REP_PACK R1
                       GROUP BY R1.COD_LOCAL, R1.NUM_PED_VTA, R1.COD_PROD) A,
                     (SELECT COD_PROM,
                             COD_PROD,
                             COUNT(DISTINCT COD_PAQUETE) CANTIDAD_PAQUETES_CON_PROD
                        FROM (SELECT VP2.*, PP2.COD_PROD
                                FROM (SELECT Q.COD_GRUPO_CIA,
                                             P.COD_PROM,
                                             Q.COD_PAQUETE,
                                             COUNT(1) CANTIDAD
                                        FROM VTA_PROMOCION    P,
                                             VTA_PROD_PAQUETE Q
                                       WHERE P.COD_GRUPO_CIA = '001'
                                         AND P.COD_PROM = U.COD_PROM
                                         AND P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
                                         AND P.COD_PAQUETE_2 = Q.COD_PAQUETE
                                       GROUP BY Q.COD_GRUPO_CIA,
                                                P.COD_PROM,
                                                Q.COD_PAQUETE) VP2,
                                     VTA_PROD_PAQUETE PP2
                               WHERE PP2.COD_GRUPO_CIA = VP2.COD_GRUPO_CIA
                                 AND PP2.COD_PAQUETE = VP2.COD_PAQUETE
                              UNION
                              SELECT VP1.*, PP1.COD_PROD
                                FROM (SELECT Q.COD_GRUPO_CIA,
                                             P.COD_PROM,
                                             Q.COD_PAQUETE,
                                             COUNT(1) CANTIDAD
                                        FROM VTA_PROMOCION    P,
                                             VTA_PROD_PAQUETE Q
                                       WHERE P.COD_GRUPO_CIA = '001'
                                         AND P.COD_PROM = U.COD_PROM
                                         AND P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
                                         AND P.COD_PAQUETE_1 = Q.COD_PAQUETE
                                       GROUP BY Q.COD_GRUPO_CIA,
                                                P.COD_PROM,
                                                Q.COD_PAQUETE) VP1,
                                     VTA_PROD_PAQUETE PP1
                               WHERE PP1.COD_GRUPO_CIA = VP1.COD_GRUPO_CIA
                                 AND PP1.COD_PAQUETE = VP1.COD_PAQUETE)
                       GROUP BY COD_PROM, COD_PROD) B
               WHERE A.COD_PROD = B.COD_PROD
                 AND A.CANTIDAD_PRECIOS_PED >= B.CANTIDAD_PAQUETES_CON_PROD
               GROUP BY COD_LOCAL, NUM_PED_VTA, COD_PROM) AA,
             (SELECT COD_PROM, COUNT(COD_PROD) CANT_PROD
                FROM (SELECT COD_PROM,
                             COD_PROD,
                             COUNT(DISTINCT COD_PAQUETE) CANTIDAD_PAQUETES_CON_PROD
                        FROM (SELECT VP2.*, PP2.COD_PROD
                                FROM (SELECT Q.COD_GRUPO_CIA,
                                             P.COD_PROM,
                                             Q.COD_PAQUETE,
                                             COUNT(1) CANTIDAD
                                        FROM VTA_PROMOCION    P,
                                             VTA_PROD_PAQUETE Q
                                       WHERE P.COD_GRUPO_CIA = '001'
                                         AND P.COD_PROM = U.COD_PROM
                                         AND P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
                                         AND P.COD_PAQUETE_2 = Q.COD_PAQUETE
                                       GROUP BY Q.COD_GRUPO_CIA,
                                                P.COD_PROM,
                                                Q.COD_PAQUETE) VP2,
                                     VTA_PROD_PAQUETE PP2
                               WHERE PP2.COD_GRUPO_CIA = VP2.COD_GRUPO_CIA
                                 AND PP2.COD_PAQUETE = VP2.COD_PAQUETE
                              UNION
                              SELECT VP1.*, PP1.COD_PROD
                                FROM (SELECT Q.COD_GRUPO_CIA,
                                             P.COD_PROM,
                                             Q.COD_PAQUETE,
                                             COUNT(1) CANTIDAD
                                        FROM VTA_PROMOCION    P,
                                             VTA_PROD_PAQUETE Q
                                       WHERE P.COD_GRUPO_CIA = '001'
                                         AND P.COD_PROM = U.COD_PROM
                                         AND P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
                                         AND P.COD_PAQUETE_1 = Q.COD_PAQUETE
                                       GROUP BY Q.COD_GRUPO_CIA,
                                                P.COD_PROM,
                                                Q.COD_PAQUETE) VP1,
                                     VTA_PROD_PAQUETE PP1
                               WHERE PP1.COD_GRUPO_CIA = VP1.COD_GRUPO_CIA
                                 AND PP1.COD_PAQUETE = VP1.COD_PAQUETE)
                       GROUP BY COD_PROM, COD_PROD)
               GROUP BY COD_PROM) BB
       WHERE AA.COD_PROM = BB.COD_PROM
         AND AA.CANT_PROD = BB.CANT_PROD;

  END LOOP;
  --END;
  -- COMMIT;
  DBMS_OUTPUT.put_line('SE INSERTARON REGISTROS CORRECTAMENTE');

  EXCEPTION
  WHEN OTHERS THEN
   --ROLLBACK;
   DBMS_OUTPUT.put_line('ERROR EN LA CARGA DE LAS TABLAS: ' || SQLERRM);
  END;
  /* ******************************************************************************* */
 PROCEDURE CARGA_REPORTE_PACK (fecInicio in char, fecFin    in char)
  IS

  BEGIN

  INSERT INTO REP_LOCAL_X_PACK
  (COD_GRUPO_CIA,COD_LOCAL,DIA,COD_PROM,CANTIDAD_PACKS)
  SELECT VR.COD_GRUPO_CIA,
         VR.COD_LOCAL,
         TRUNC(VR.FEC_PED_VTA) DIA,
         VR.COD_PROM,
         COUNT(VR.COD_PROM) CANTIDAD_PACKS --, SUM(VR.CANT_ATENDIDA/VR.VAL_FRAC) CANT
    FROM V_RES_VTA_REP_PACK  VR, V_RES_VENTA_LOCAL_PACK VL
   WHERE VR.COD_GRUPO_CIA = VL.COD_GRUPO_CIA
     AND VR.COD_LOCAL = VL.COD_LOCAL
     AND VR.COD_PROM = VL.COD_PROM
     AND VR.NUM_PED_VTA = VL.NUM_PED_VTA
     AND VR.FEC_PED_VTA BETWEEN TO_DATE(fecInicio, 'dd/MM/yyyy') AND TO_DATE(fecFin, 'dd/MM/yyyy') + 1 - 1/24/60/60
   GROUP BY VR.COD_GRUPO_CIA,
            VR.COD_LOCAL,
            TRUNC(VR.FEC_PED_VTA),
            VR.COD_PROM;
   -- ORDER BY VR.FEC_PED_VTA ASC;

  END;

  PROCEDURE DROP_TABLAS
  IS
  BEGIN
    DBMS_OUTPUT.put_line('INICIO borra tablas');

    EXECUTE IMMEDIATE ' DROP TABLE v_RES_VTA_REP_PACK';
    EXECUTE IMMEDIATE ' DROP TABLE V_RES_VENTA_LOCAL_PACK';
    EXECUTE IMMEDIATE ' DROP TABLE REP_LOCAL_X_PACK';

    DBMS_OUTPUT.put_line('borra tablas');
  END;

  PROCEDURE DELETE_TABLAS (fecInicio in char, fecFin    in char)
  IS
  BEGIN

    DBMS_OUTPUT.put_line('INICIO elimina tablas');

--    DELETE V_RES_VTA_REP_PACK WHERE FEC_PED_VTA BETWEEN TO_DATE(fecInicio, 'dd/MM/yyyy') AND TO_DATE(fecFin, 'dd/MM/yyyy') + 1 - 1/24/60/60;
    DELETE V_RES_VTA_REP_PACK;
    DELETE V_RES_VENTA_LOCAL_PACK;
    DELETE REP_LOCAL_X_PACK WHERE DIA BETWEEN TO_DATE(fecInicio, 'dd/MM/yyyy') AND TO_DATE(fecFin, 'dd/MM/yyyy') + 1 - 1/24/60/60;

    DBMS_OUTPUT.put_line('elimina tablas');

  END;

/* ********************************************************* */
/* ********************************************************* */

 PROCEDURE CREA_RESUMEN(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,fecInicio in char, fecFin    in char)
 IS
 BEGIN

  DELETE_TABLAS (fecInicio, fecFin);

  COMMIT;

  carga_detalle_cabecera(cCodGrupoCia_in,cCodCia_in,cCodLocal_in,fecInicio, fecFin);
  COMMIT;

  CARGA_PEDIDO_X_PACK(fecInicio, fecFin);
  COMMIT;

  CARGA_REPORTE_PACK(fecInicio, fecFin);
  COMMIT;

 END;


END PTOVENTA_REPORTE_PACK_DU;
/

