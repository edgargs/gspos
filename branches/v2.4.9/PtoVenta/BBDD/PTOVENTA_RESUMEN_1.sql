--------------------------------------------------------
--  DDL for Package Body PTOVENTA_RESUMEN
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_RESUMEN" is

  /***************************************************************************/

  PROCEDURE CARGA_CANT_CUPON_X_CAMPANA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR) IS
  BEGIN

      DELETE FROM AUX_CONSOLIDADO_CAMP_AUX_CUP A
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.FECHA_VENTA = TO_DATE(cFechaVta,'DD/MM/YYYY')
        AND A.COD_LOCAL = cCodLocal_in;

      INSERT INTO AUX_CONSOLIDADO_CAMP_AUX_CUP
      (
       COD_GRUPO_CIA,
       FECHA_VENTA,
       COD_LOCAL,
       COD_CAMP_CUPON,
       CUP_EMITIDOS,
       CUP_USADOS,
       CUP_ANULADOS,
       USU_CREA_CONS_CAMP
      )
        SELECT
             CP.COD_GRUPO_CIA,
             TRUNC(CAB.FEC_PED_VTA) FECHA_VENTA,
             CP.COD_LOCAL ,
             C.COD_CAMPANA,
             SUM(CASE WHEN CP.ESTADO = 'E' THEN 1 ELSE 0 END) CUP_EMITIDOS,
             SUM(CASE WHEN CP.ESTADO = 'S' AND CP.IND_USO = 'S' AND C.ESTADO = 'U' THEN 1 ELSE 0 END) CUP_USADOS,
             SUM(CASE WHEN C.ESTADO = 'N' THEN 1 ELSE 0 END) CUP_ANULADOS,
             'SISTEMAS' USU_CREA_CONS_CAMP
        FROM VTA_PEDIDO_VTA_CAB CAB,
             VTA_CAMP_PEDIDO_CUPON CP,
             VTA_CUPON C
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND CAB.COD_LOCAL = cCodLocal_in
          AND CAB.FEC_PED_VTA BETWEEN  TO_DATE(cFechaVta,'DD/MM/YYYY') AND
                                       TO_DATE(cFechaVta,'DD/MM/YYYY') + 1 - 1/24/60/60
          AND CAB.EST_PED_VTA = 'C'
          AND CP.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
          AND CP.COD_LOCAL = CAB.COD_LOCAL
          AND CP.NUM_PED_VTA = CAB.NUM_PED_VTA
          AND C.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
          AND C.COD_CUPON = CP.COD_CUPON
        GROUP BY
             CP.COD_GRUPO_CIA,
             TRUNC(CAB.FEC_PED_VTA),
             CP.COD_LOCAL ,
             C.COD_CAMPANA ;

      COMMIT;

  END;

  /***************************************************************************/

  PROCEDURE CARGA_USO_CUPON_X_CAMPANA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR) IS
  BEGIN

        DELETE FROM AUX_CONSOLIDADO_CAMP_AUX_VENTA
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FECHA_VENTA = TO_DATE(cFechaVta,'DD/MM/YYYY');

        INSERT INTO AUX_CONSOLIDADO_CAMP_AUX_VENTA
            (
             COD_GRUPO_CIA,
             FECHA_VENTA,
             COD_LOCAL,
             COD_CAMP_CUPON,
             CANTIDAD,
             T_C_IGV,
             AHORRO_C_IGV,
             USU_CREA_CONS_CAMP,
             --NUEVO
             T_S_IGV,
             AHORRO_S_IGV,
             COSTO,
             CONTRIB
            )
        SELECT C.COD_GRUPO_CIA,
               TRUNC(C.FEC_PED_VTA),
               C.COD_LOCAL,
               TRIM(D.COD_CAMP_CUPON),
               ROUND(SUM(D.CANT_ATENDIDA/D.VAL_FRAC),3) CANTIDAD,
               ROUND(SUM(D.VAL_PREC_TOTAL),3) PREC_TOTAL,
               ROUND(SUM(D.AHORRO),3) AHORRO,
               'SISTEMAS',
               --NUEVO
               ROUND(SUM(VAL_PREC_TOTAL / (1+D.VAL_IGV/100)),3) PREC_TOTAL_SIN_IGV,
               ROUND(SUM(D.AHORRO / (1+D.VAL_IGV/100)),3) AHORRO_SIN_IGV,
               ROUND(SUM(D.VAL_PREC_PROM * D.CANT_ATENDIDA / D.VAL_FRAC),3) COSTO,
               (ROUND(SUM(VAL_PREC_TOTAL / (1+D.VAL_IGV/100)),3) - ROUND(SUM(D.VAL_PREC_PROM * D.CANT_ATENDIDA / D.VAL_FRAC),3)) CONTRIB
          FROM VTA_PEDIDO_VTA_CAB C,
               VTA_PEDIDO_VTA_DET D
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.COD_LOCAL = cCodLocal_in
           AND C.FEC_PED_VTA BETWEEN  TO_DATE(cFechaVta,'DD/MM/YYYY') AND
                                      TO_DATE(cFechaVta,'DD/MM/YYYY') + 1 - 1/24/60/60
           AND C.EST_PED_VTA = 'C'
           AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
           AND D.COD_LOCAL = C.COD_LOCAL
           AND D.NUM_PED_VTA = C.NUM_PED_VTA
           AND TRIM(D.COD_CAMP_CUPON) IS NOT NULL
         GROUP BY C.COD_GRUPO_CIA,
               TRUNC(C.FEC_PED_VTA),
               C.COD_LOCAL,
               TRIM(D.COD_CAMP_CUPON);

        COMMIT;

  END;


  /***************************************************************************/

  PROCEDURE CARGA_USU_PACKS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR) IS
  BEGIN

      DELETE FROM AUX_CONSOLIDADO_PACK
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL = cCodLocal_in
        AND FECHA_VENTA = TO_DATE(cFechaVta,'DD/MM/YYYY');

      INSERT INTO AUX_CONSOLIDADO_PACK
      (
          COD_GRUPO_CIA,
          FECHA_VENTA,
          COD_LOCAL,
          COD_PROM,
          COD_PROD,
          CANT_REGALO,
          T_C_IGV,
          T_S_IGV,
          AHORRO_C_IGV,
          AHORRO_S_IGV,
          COSTO,
          USU_CREA_CONS_PACK
      )
      SELECT
             C.COD_GRUPO_CIA,
             TRUNC(C.FEC_PED_VTA) FEC_VENTA,
             C.COD_LOCAL,
             D.COD_PROM,
             D.COD_PROD,
             ROUND(SUM(D.CANT_ATENDIDA/D.VAL_FRAC),3) CANT_REGALO,
             ROUND(SUM(D.VAL_PREC_TOTAL),3) T_C_IGV,
             ROUND(SUM(D.VAL_PREC_TOTAL / (1 + D.VAL_IGV/100)),3) T_S_IGV,
             ROUND(SUM(D.AHORRO),3) AHORRO_C_IGV,
             ROUND(SUM(D.AHORRO / (1 + D.VAL_IGV/100)),3) AHORRO_S_IGV,
             ROUND(SUM(D.VAL_PREC_PROM * D.CANT_ATENDIDA / D.VAL_FRAC),3) "COSTO",
             'JMIRANDA'
        FROM VTA_PEDIDO_VTA_CAB C,
             VTA_PEDIDO_VTA_DET D
       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
         AND C.COD_LOCAL = cCodLocal_in
         AND C.FEC_PED_VTA BETWEEN TO_DATE(cFechaVta,'dd/MM/yyyy')
                               AND TO_DATE(cFechaVta,'dd/MM/yyyy') + 1 - 1/24/60/60
         AND C.EST_PED_VTA = 'C'
         AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND D.COD_LOCAL = C.COD_LOCAL
         AND D.NUM_PED_VTA = C.NUM_PED_VTA
         AND D.VAL_PREC_VTA = 0
         AND D.COD_PROM IS NOT NULL
       GROUP BY
             C.COD_GRUPO_CIA,
             TRUNC(C.FEC_PED_VTA),
             C.COD_LOCAL,
             D.COD_PROM,
             D.COD_PROD;

  END;


  /***************************************************************************/

  PROCEDURE CARGA_AUX_LISTA_PED_FID(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR)
  IS
  BEGIN

     DELETE FROM AUX_LISTA_PED_FIDELIZADOS
     WHERE COD_LOCAL = cCodLocal_in
       AND FECHA BETWEEN TO_DATE(cFechaVta,'dd/MM/yyyy') AND TO_DATE(cFechaVta,'dd/MM/yyyy') + 1 - 1/24/60/60;

         INSERT INTO AUX_LISTA_PED_FIDELIZADOS (TIPO, FECHA, COD_LOCAL, NUM_PED_VTA, DNI_CLI)
         SELECT
               'EMIT' TIPO,
    		   TRUNC(C.FEC_PED_VTA) FECHA,
               C.COD_LOCAL,
               C.NUM_PED_VTA,
               TP.DNI_CLI
         FROM VTA_PEDIDO_VTA_CAB C,
              FID_TARJETA_PEDIDO TP
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.COD_LOCAL = cCodLocal_in
           AND C.FEC_PED_VTA BETWEEN TO_DATE(cFechaVta,'dd/MM/yyyy') AND TO_DATE(cFechaVta,'dd/MM/yyyy') + 1 - 1/24/60/60
           AND C.EST_PED_VTA = 'C'
           AND C.TIP_PED_VTA = '01'
           AND TP.COD_LOCAL = C.COD_LOCAL
           AND TP.NUM_PEDIDO = C.NUM_PED_VTA
         UNION
         SELECT
               'ANUL',
    		   TRUNC(C.FEC_PED_VTA) ,
               C.COD_LOCAL,
               C.NUM_PED_VTA,
               TP.DNI_CLI
         FROM VTA_PEDIDO_VTA_CAB C,
              FID_TARJETA_PEDIDO TP
         WHERE C.COD_GRUPO_CIA = '001'
           AND C.COD_LOCAL = cCodLocal_in
           AND C.FEC_PED_VTA BETWEEN TO_DATE(cFechaVta,'dd/MM/yyyy') AND TO_DATE(cFechaVta,'dd/MM/yyyy') + 1 - 1/24/60/60
           AND C.EST_PED_VTA = 'C'
           AND C.TIP_PED_VTA = '01'
           AND TP.COD_LOCAL = C.COD_LOCAL
           AND TP.NUM_PEDIDO = C.NUM_PED_VTA_ORIGEN;

     COMMIT;

  END;


  /***************************************************************************/

  PROCEDURE CARGA_AUX_VTA_FID_DIA_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR)
  IS
  BEGIN
          DELETE FROM AUX_VTA_FID_DIA_LOCAL
          WHERE LOCAL LIKE cCodLocal_in || '%'
            AND FECHA BETWEEN TO_DATE(cFechaVta,'dd/MM/yyyy') AND TO_DATE(cFechaVta,'dd/MM/yyyy') + 1 - 1/24/60/60;

           INSERT INTO AUX_VTA_FID_DIA_LOCAL
           (
          	   TIPO,
          	   LOCAL,
          	   FECHA,
          	   SEXO_CLI,
          	   CANT_DNI,
          	   EDAD,
          	   PED,
          	   TOT_C_IGV,
          	   TOT_S_IGV,
          	   AHORRO_C_IGV,
          	   AHORRO_S_IGV,
          	   COSTO
           )
           SELECT
          	   PF.TIPO,
          	   (L.COD_LOCAL ||'-'|| L.DESC_CORTA_LOCAL) "LOCAL",
          	   TRUNC(C.FEC_PED_VTA) "FECHA",
          	   NVL(CLI.SEXO_CLI,' '),
          	   COUNT(DISTINCT PF.DNI_CLI) "CANT_DNI",
          	   NVL(TRUNC(MONTHS_BETWEEN(SYSDATE, CLI.FEC_NAC_CLI) / 12),0) "EDAD",
          	   COUNT(DISTINCT CASE WHEN D.VAL_PREC_TOTAL < 0 THEN '0' ELSE PF.NUM_PED_VTA END) "PED",
          	   SUM(D.VAL_PREC_TOTAL) "TOT_C_IGV",
          	   SUM(D.VAL_PREC_TOTAL / (1 + D.VAL_IGV/100)) "TOT_S_IGV",
          	   SUM(D.AHORRO) "AHORRO_C_IGV",
          	   SUM(D.AHORRO / (1 + D.VAL_IGV/100)) "AHORRO_S_IGV",
          	   SUM(D.CANT_ATENDIDA * D.VAL_PREC_PROM / D.VAL_FRAC) "COSTO"
           FROM VTA_PEDIDO_VTA_CAB C,
          	  AUX_LISTA_PED_FIDELIZADOS PF,
          	  VTA_PEDIDO_VTA_DET D,
          	  PBL_LOCAL L,
          	  PBL_CLIENTE CLI
           WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
             AND C.COD_LOCAL = cCodLocal_in
             AND C.FEC_PED_VTA BETWEEN TO_DATE(cFechaVta,'dd/MM/yyyy') AND TO_DATE(cFechaVta,'dd/MM/yyyy') + 1 - 1/24/60/60
             AND C.EST_PED_VTA = 'C'
             AND C.TIP_PED_VTA = '01'
             AND PF.COD_LOCAL = C.COD_LOCAL
             AND PF.NUM_PED_VTA = C.NUM_PED_VTA
             AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
             AND D.COD_LOCAL = C.COD_LOCAL
             AND D.NUM_PED_VTA = C.NUM_PED_VTA
             AND L.COD_LOCAL = C.COD_LOCAL
             AND CLI.DNI_CLI = PF.DNI_CLI
           GROUP BY
          	   PF.TIPO,
          	   (L.COD_LOCAL ||'-'|| L.DESC_CORTA_LOCAL),
          	   TRUNC(C.FEC_PED_VTA),
          	   NVL(CLI.SEXO_CLI,' '),
          	   NVL(TRUNC(MONTHS_BETWEEN(SYSDATE, CLI.FEC_NAC_CLI) / 12),0);
  END;

  /***************************************************************************/

  PROCEDURE CARGA_RESUMENES(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR)
  IS
  BEGIN

     declare  --añadido 20100521 JOLIVA -- Genera tablas de resumen de uso de campañas y packs
     begin
          PTOVENTA_RESUMEN.CARGA_CANT_CUPON_X_CAMPANA (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          PTOVENTA_RESUMEN.CARGA_USO_CUPON_X_CAMPANA (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          PTOVENTA_RESUMEN.CARGA_USU_PACKS (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          PTOVENTA_RESUMEN.CARGA_AUX_LISTA_PED_FID (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          PTOVENTA_RESUMEN.CARGA_AUX_VTA_FID_DIA_LOCAL (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          PTOVENTA_RESUMEN.CARGA_AUX_CONS_REP_CAMP (cCodGrupoCia_in, cCodLocal_in, cFechaVta);

          -- JMIRANDA 21.06.2010
          PTOVENTA_RESUMEN.CARGA_RESUMENES_2(cCodGrupoCia_in,cCodLocal_in, cFechaVta );
          --
          -- JMIRANDA 15.07.2010
          PTOVENTA_RESUMEN.CARGA_AUX_VTA_DET(cCodGrupoCia_in,cCodLocal_in, cFechaVta );
          COMMIT;
          DBMS_OUTPUT.PUT_LINE('TERMINÓ CARGA_RESUMENES: cCodLocal_in=' || cCodLocal_in ||', cFechaVta=' || cFechaVta);
     exception
       when others then
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||cCodLocal_in||' Resumen de uso de campañas y packs NO OK ',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     end;

  END;

  /***************************************************************************/

  PROCEDURE CARGA_AUX_CONS_REP_CAMP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR)
  IS
  BEGIN
          DELETE FROM AUX_CONS_REP_CAMP
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND FECHA_VENTA = TO_DATE(cFechaVta,'dd/MM/yyyy')
            AND COD_LOCAL = cCodLocal_in;

          INSERT INTO AUX_CONS_REP_CAMP
          (
           COD_GRUPO_CIA,
           FECHA_VENTA,
           COD_LOCAL,
           COD_CAMP_CUPON,
           cantidad,
           val_prec_total,
           ahorro,
           cup_emitidos,
           cup_usados,
           cup_anulados,
           usu_crea_cons_camp,
           prec_total_sin_igv ,
           ahorro_sin_igv,
    	   costo,
    	   contrib
          )
          SELECT b.cod_grupo_cia, b.fecha_venta, b.cod_local, TRIM(b.cod_camp_cupon) cod_camp_cupon,
                 b.cantidad, b.t_c_igv, b.ahorro_c_igv, a.cup_emitidos, a.cup_usados, a.cup_anulados,
                 'PR_ACT_RES', b.t_s_igv, b.ahorro_s_igv,
                 b.costo,
                 b.contrib
            FROM AUX_CONSOLIDADO_CAMP_AUX_CUP a, AUX_CONSOLIDADO_CAMP_AUX_VENTA b
           WHERE a.cod_grupo_cia(+) = b.cod_grupo_cia
             AND a.cod_local(+) = b.cod_local
             AND a.fecha_venta(+) = b.fecha_venta
             AND a.cod_camp_cupon(+) = b.cod_camp_cupon
             AND b.cod_grupo_cia = cCodGrupoCia_in
             AND b.fecha_venta = TO_DATE(cFechaVta,'DD/MM/YYYY')
             AND b.cod_local = cCodLocal_in
          UNION
          SELECT a.cod_grupo_cia, a.fecha_venta, a.cod_local, TRIM(a.cod_camp_cupon),
                 b.cantidad, b.t_c_igv, b.ahorro_c_igv, a.cup_emitidos, a.cup_usados, a.cup_anulados,
                 'PR_ACT_RES', b.t_s_igv, b.ahorro_s_igv,
                 b.costo,
                 b.contrib
            FROM AUX_CONSOLIDADO_CAMP_AUX_CUP a, AUX_CONSOLIDADO_CAMP_AUX_VENTA b
           WHERE a.cod_grupo_cia = b.cod_grupo_cia(+)
             AND a.cod_local = b.cod_local(+)
             AND a.fecha_venta = b.fecha_venta(+)
             AND a.cod_camp_cupon = b.cod_camp_cupon (+)
             AND a.cod_grupo_cia = cCodGrupoCia_in
             AND a.fecha_venta = TO_DATE(cFechaVta,'DD/MM/YYYY')
             AND a.cod_local = cCodLocal_in;

  END;

  /***************************************************************************/

  PROCEDURE CARGA_CANT_CUPON_X_CAMPANA_2(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR) IS
  BEGIN

      DELETE FROM AUX_CONS_CAMP_AUX_CUP_V2 A
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.FECHA_VENTA = TO_DATE(cFechaVta,'dd/MM/yyyy')
        AND A.COD_LOCAL = cCodLocal_in;

      INSERT INTO AUX_CONS_CAMP_AUX_CUP_V2
      (
       COD_GRUPO_CIA,
       FECHA_VENTA,
       COD_LOCAL,
       COD_CAMP_CUPON,
       CUP_EMITIDOS,
       CUP_USADOS,
       CUP_ANULADOS,
       USU_CREA,
       FEC_CREA
      )
        SELECT CP.COD_GRUPO_CIA,
               TRUNC(CAB.FEC_PED_VTA), --CUANDO SE USA EL CUPON
               CP.COD_LOCAL,
               C.COD_CAMPANA COD_CAMP_CUPON,
               SUM(CASE
                     WHEN CP.ESTADO = 'E' AND CP.IND_IMPR = 'S' THEN
                      1
                     ELSE
                      0
                   END) CUP_EMITIDOS,
               SUM(CASE
                     WHEN CP.ESTADO = 'S' AND C.ESTADO = 'U' AND
                          CAB.IND_PEDIDO_ANUL = 'N' THEN
                      1
                     ELSE
                      0
                   END) CUP_USADOS,
               SUM(CASE
                     WHEN C.ESTADO = 'N' AND CAB.IND_PEDIDO_ANUL = 'S' THEN
                      1
                     ELSE
                      0
                   END) CUP_ANULADOS,
               'SISTEMAS' USU_CREA_CONS_CAMP,
               SYSDATE FEC_CREA
          FROM VTA_PEDIDO_VTA_CAB             CAB,
               PTOVENTA.VTA_CAMP_PEDIDO_CUPON CP,
               PTOVENTA.VTA_CUPON             C
         WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
           AND CAB.EST_PED_VTA = 'C'
           AND CAB.COD_LOCAL = cCodLocal_in
           AND CAB.FEC_PED_VTA BETWEEN
               TO_DATE(cFechaVta , 'DD/MM/YYYY') AND
               TO_DATE(cFechaVta , 'DD/MM/YYYY') + 1 - 1/24/60/60

           AND CP.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
           AND CP.COD_LOCAL = CAB.COD_LOCAL
           AND CP.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND C.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
--           AND C.COD_LOCAL = CP.COD_LOCAL
           AND C.COD_CUPON = CP.COD_CUPON
         GROUP BY
               CP.COD_GRUPO_CIA,
               TRUNC(CAB.FEC_PED_VTA),
               CP.COD_LOCAL,
               C.COD_CAMPANA ;

      COMMIT;

  END;
  /***************************************************************************/

  PROCEDURE CARGA_RESUMENES_2(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR, cFechaVta 	IN CHAR)
  IS
  BEGIN

     declare  --añadido 17.06.2010 JMIRANDA-- Genera tablas de resumen de uso de campañas y packs 2
     begin
          PTOVENTA_RESUMEN.CARGA_CANT_CUPON_X_CAMPANA_2 (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          PTOVENTA_RESUMEN.CARGA_USO_CUPON_X_CAMPANA (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          PTOVENTA_RESUMEN.CARGA_USU_PACKS_2 (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          PTOVENTA_RESUMEN.CARGA_AUX_CONS_REP_CAMP_2 (cCodGrupoCia_in, cCodLocal_in, cFechaVta);
          COMMIT;
          DBMS_OUTPUT.PUT_LINE('TERMINÓ CARGA_RESUMENES: cCodLocal_in=' || cCodLocal_in ||', cFechaVta=' || cFechaVta);
     exception
       when others then
         farma_email.envia_correo(csendoraddress_in => 'jluna@mifarma.com.pe',creceiveraddress_in => 'operador@mifarma.com.pe',
         csubject_in => 'Tarea Nocturna Local: '||cCodLocal_in||' Resumen de uso de campañas y packs v.2 NO OK ',ctitulo_in => 'Operacion Realizada con error .Coordinar con Jluna y Joliva',
         cmensaje_in => 'Fecha y Hora:'||to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'),cccreceiveraddress_in => 'jluna@mifarma.com.pe',
         cip_servidor => '10.11.1.252',cin_html => false);
     end;

  END;

    /***************************************************************************/

  PROCEDURE CARGA_AUX_CONS_REP_CAMP_2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR)
  IS
  BEGIN
          DELETE FROM AUX_CONS_REP_CAMP_2
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND FECHA_VENTA = TO_DATE(cFechaVta,'dd/MM/yyyy')
            AND COD_LOCAL = cCodLocal_in;

          INSERT INTO AUX_CONS_REP_CAMP_2
          (
           COD_GRUPO_CIA,
           FECHA_VENTA,
           COD_LOCAL,
           COD_CAMP_CUPON,
           cantidad,
           val_prec_total,
           ahorro,
           cup_emitidos,
           cup_usados,
           cup_anulados,
           usu_crea_cons_camp,
           prec_total_sin_igv ,
           ahorro_sin_igv,
    	   costo,
    	   contrib
          )
          SELECT b.cod_grupo_cia, b.fecha_venta, b.cod_local, TRIM(b.cod_camp_cupon) cod_camp_cupon,
                 b.cantidad, b.t_c_igv, b.ahorro_c_igv, a.cup_emitidos, a.cup_usados, a.cup_anulados,
                 'PR_ACT_RES', b.t_s_igv, b.ahorro_s_igv,
                 b.costo,
                 b.contrib
            FROM AUX_CONS_CAMP_AUX_CUP_V2 a, AUX_CONSOLIDADO_CAMP_AUX_VENTA b
           WHERE a.cod_grupo_cia(+) = b.cod_grupo_cia
             AND a.cod_local(+) = b.cod_local
             AND a.fecha_venta(+) = b.fecha_venta
             AND a.cod_camp_cupon(+) = b.cod_camp_cupon
             AND b.cod_grupo_cia = cCodGrupoCia_in
             AND b.fecha_venta = TO_DATE(cFechaVta,'DD/MM/YYYY')
             AND b.cod_local = cCodLocal_in
          UNION
          SELECT a.cod_grupo_cia, a.fecha_venta, a.cod_local, TRIM(a.cod_camp_cupon),
                 b.cantidad, b.t_c_igv, b.ahorro_c_igv, a.cup_emitidos, a.cup_usados, a.cup_anulados,
                 'PR_ACT_RES', b.t_s_igv, b.ahorro_s_igv,
                 b.costo,
                 b.contrib
            FROM AUX_CONS_CAMP_AUX_CUP_V2 a, AUX_CONSOLIDADO_CAMP_AUX_VENTA b
           WHERE a.cod_grupo_cia = b.cod_grupo_cia(+)
             AND a.cod_local = b.cod_local(+)
             AND a.fecha_venta = b.fecha_venta(+)
             AND a.cod_camp_cupon = b.cod_camp_cupon (+)
             AND a.cod_grupo_cia = cCodGrupoCia_in
             AND a.fecha_venta = TO_DATE(cFechaVta,'DD/MM/YYYY')
             AND a.cod_local = cCodLocal_in;

  END;

  /***************************************************************************/

  PROCEDURE CARGA_USU_PACKS_2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR) IS
  BEGIN

      DELETE FROM AUX_CONSOLIDADO_PACK_2
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL = cCodLocal_in
        AND FECHA_VENTA = TO_DATE(cFechaVta,'DD/MM/YYYY');

      INSERT INTO AUX_CONSOLIDADO_PACK_2
      (
          COD_GRUPO_CIA,
          FECHA_VENTA,
          COD_LOCAL,
          COD_PROM,
          COD_PROD,
          CANT_REGALO,
          T_C_IGV,
          T_S_IGV,
          AHORRO_C_IGV,
          AHORRO_S_IGV,
          COSTO,
          USU_CREA_CONS_PACK
      )
      SELECT
             C.COD_GRUPO_CIA,
             TRUNC(C.FEC_PED_VTA) FEC_VENTA,
             C.COD_LOCAL,
             D.COD_PROM,
             D.COD_PROD,
             ROUND(SUM(D.CANT_ATENDIDA/D.VAL_FRAC),3) CANT_REGALO,
             ROUND(SUM(D.VAL_PREC_TOTAL),3) T_C_IGV,
             ROUND(SUM(D.VAL_PREC_TOTAL / (1 + D.VAL_IGV/100)),3) T_S_IGV,
             ROUND(SUM(D.AHORRO),3) AHORRO_C_IGV,
             ROUND(SUM(D.AHORRO / (1 + D.VAL_IGV/100)),3) AHORRO_S_IGV,
             ROUND(SUM(D.VAL_PREC_PROM * D.CANT_ATENDIDA / D.VAL_FRAC),3) "COSTO",
             'JMIRANDA'
        FROM VTA_PEDIDO_VTA_CAB C,
             VTA_PEDIDO_VTA_DET D
       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
         AND C.COD_LOCAL = cCodLocal_in
         AND C.FEC_PED_VTA BETWEEN TO_DATE(cFechaVta,'dd/MM/yyyy')
                               AND TO_DATE(cFechaVta,'dd/MM/yyyy') + 1 - 1/24/60/60
         AND C.EST_PED_VTA = 'C'
         AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND D.COD_LOCAL = C.COD_LOCAL
         AND D.NUM_PED_VTA = C.NUM_PED_VTA
         --AND D.VAL_PREC_VTA = 0
         AND D.COD_PROM IS NOT NULL
       GROUP BY
             C.COD_GRUPO_CIA,
             TRUNC(C.FEC_PED_VTA),
             C.COD_LOCAL,
             D.COD_PROM,
             D.COD_PROD;

  END;

  /***************************************************************************/
  PROCEDURE CARGA_AUX_VTA_DET (cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cFechaVta 	IN CHAR) IS
  BEGIN

    DELETE FROM AUX_CONS_CAMP_AUX_VENTA_DET D
     WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
       AND D.COD_LOCAL = cCodLocal_in
       AND D.FECHA_VENTA = TO_DATE(cFechaVta,'DD/MM/YYYY');

        INSERT INTO AUX_CONS_CAMP_AUX_VENTA_DET
            (
             COD_GRUPO_CIA,
             FECHA_VENTA,
             COD_LOCAL,
             COD_CAMP_CUPON,
	           COD_PROD,
             CANTIDAD,
             T_C_IGV,
             AHORRO_C_IGV,
             USU_CREA_CONS_CAMP,
             --NUEVO
             T_S_IGV,
             AHORRO_S_IGV,
             COSTO,
             CONTRIB
            )
        SELECT C.COD_GRUPO_CIA,
               TRUNC(C.FEC_PED_VTA),
               C.COD_LOCAL,
               TRIM(D.COD_CAMP_CUPON),
 	             D.COD_PROD,
               ROUND(SUM(D.CANT_ATENDIDA/D.VAL_FRAC),3) CANTIDAD,
               ROUND(SUM(D.VAL_PREC_TOTAL),3) PREC_TOTAL,
               ROUND(SUM(D.AHORRO),3) AHORRO,
               'SISTEMAS',
               --NUEVO
               ROUND(SUM(VAL_PREC_TOTAL / (1+D.VAL_IGV/100)),3) PREC_TOTAL_SIN_IGV,
               ROUND(SUM(D.AHORRO / (1+D.VAL_IGV/100)),3) AHORRO_SIN_IGV,
               ROUND(SUM(D.VAL_PREC_PROM * D.CANT_ATENDIDA / D.VAL_FRAC),3) COSTO,
               (ROUND(SUM(VAL_PREC_TOTAL / (1+D.VAL_IGV/100)),3) - ROUND(SUM(D.VAL_PREC_PROM * D.CANT_ATENDIDA / D.VAL_FRAC),3)) CONTRIB
              FROM VTA_PEDIDO_VTA_CAB C,
                   VTA_PEDIDO_VTA_DET D
             WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
               AND C.COD_LOCAL = cCodLocal_in
               AND C.FEC_PED_VTA BETWEEN  TO_DATE(cFechaVta,'DD/MM/YYYY') AND
					                                TO_DATE(cFechaVta,'DD/MM/YYYY')  + 1 - 1/24/60/60

               AND C.EST_PED_VTA = 'C'
               AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
               AND D.COD_LOCAL = C.COD_LOCAL
               AND D.NUM_PED_VTA = C.NUM_PED_VTA
			   AND TRIM(D.COD_CAMP_CUPON) IS NOT NULL
             GROUP BY C.COD_GRUPO_CIA,
                   TRUNC(C.FEC_PED_VTA),
                   C.COD_LOCAL,
                   TRIM(D.COD_CAMP_CUPON),
                   D.COD_PROD;

 END;

end;

/
