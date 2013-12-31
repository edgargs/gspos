--------------------------------------------------------
--  DDL for Package Body PTOVENTA_VTA_LISTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_VTA_LISTA" is
  --21/11/2007 dubilluz modificacion
  FUNCTION VTA_LISTA_PROD(cCodGrupoCia_in IN CHAR,
  		   				          cCodLocal_in	  IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
	SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO) || 'Ã' ||
           TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
           NVL(PROD.IND_ZAN,' ') || 'Ã' ||
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
           DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG          || 'Ã' ||
           PROD.IND_TIPO_PROD          || 'Ã' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
           PROD.DESC_PROD || DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--JCORTEZ ind encarte
           IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           --21/11/2007 DUBILLUZ MODIFICADO
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P

                   /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                          TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                     AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')

                    */
                   WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
            AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
            AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO  = 'A'
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
           ) Z,
           (
           SELECT * FROM
            (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                  PROD_ENCARTE.COD_ENCARTE,
                  PROD_ENCARTE.COD_PROD,
                  rank() over(partition by
                  PROD_ENCARTE.COD_GRUPO_CIA,
                  PROD_ENCARTE.COD_PROD order by PROD_ENCARTE.COD_ENCARTE asc) AS ORDEN
           FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                  VTA_ENCARTE ENCARTE
           WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
           AND    ENCARTE.ESTADO = 'A'
           AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
           AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
           ) A
           WHERE A.ORDEN=1
           )   V2
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    PROD.COD_PROD = z.cod (+)
    AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
    AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
    ;

    RETURN curVta;
  END;
  /* ************************************************************************ */

  FUNCTION VTA_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
  		   				  		           cCodLocal_in	   IN CHAR,
								                 cTipoFiltro_in  IN CHAR,
  		   						             cCodFiltro_in 	 IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vFecha_actual date;
  BEGIN
    SELECT TRUNC(SYSDATE)
    INTO   vFecha_actual
    FROM   DUAL;

  	 IF(cTipoFiltro_in = 1) THEN --principio activo
     	OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
			        DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    LGT_PRINC_ACT_PROD PRINC_ACT_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = '001'
                      ) V1,
                      VTA_PROMOCION    P

                   /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                 AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                      AND  P.COD_GRUPO_CIA = 001
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
              ) Z,
             (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                    PROD_ENCARTE.COD_ENCARTE,
                    PROD_ENCARTE.COD_PROD
             FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                    VTA_ENCARTE ENCARTE
             WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
             AND    ENCARTE.ESTADO = 'A'
             AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
             AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
             )   V2
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			 AND	  PRINC_ACT_PROD.COD_PRINC_ACT = cCodFiltro_in
			 AND	  PROD.COD_PROD = PRINC_ACT_PROD.COD_PROD
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
       ;
	ELSIF(cTipoFiltro_in = 2) THEN --accion terapeutica
		OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
	      	    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    LGT_ACC_TERAP ACC_TER,
					    LGT_ACC_TERAP_PROD ACC_TERAP_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = '001'
                      ) V1,
                      VTA_PROMOCION    P

                 /*    WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                AND  TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */         WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.COD_GRUPO_CIA = 001
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
              ) Z,
              (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			 AND	  ACC_TER.COD_ACC_TERAP = cCodFiltro_in
			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD
			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
       ;
	ELSIF(cTipoFiltro_in = 3) THEN --laboratorio
		OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
	    		    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                      ) V1,
                      VTA_PROMOCION    P

                   /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                AND  TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
              ) Z,
              (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			 AND	  LAB.COD_LAB = cCodFiltro_in
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
       ;
  ELSIF(trim(cTipoFiltro_in) = 4) THEN -- JCORTTEZ 17.04.08 productos de encarte
		OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
	      	    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    LGT_ACC_TERAP ACC_TER,
					    LGT_ACC_TERAP_PROD ACC_TERAP_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                      ) V1,
                      VTA_PROMOCION    P

                    /* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                      TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                  AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND   P.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))) Z,
              (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			 --AND	  ACC_TER.COD_ACC_TERAP = cCodFiltro_in
			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD
			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    V2.COD_ENCARTE = '00001' --preguntar a DIEGO
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND    PROD.COD_PROD=V2.COD_PROD--JCORTEZ
       ;
	ELSIF(cTipoFiltro_in = 5) THEN -- JCORTTEZ 17.04.08 productos Cupon
		OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
	      	    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    LGT_ACC_TERAP ACC_TER,
					    LGT_ACC_TERAP_PROD ACC_TERAP_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                      ) V1,
                      VTA_PROMOCION    P

                    /* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

             AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */

             WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
              ) Z,
              (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2,
                VTA_CAMPANA_PROD V3,
                VTA_CAMPANA_CUPON V4 -- DUBILLUZ 03/07/2008
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			-- AND	  ACC_TER.COD_ACC_TERAP = cCodFiltro_in
			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD
			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    V4.ESTADO = 'A'
       AND    vFecha_actual between V4.FECH_INICIO AND  V4.FECH_FIN
       and    v4.cod_grupo_cia = v3.cod_grupo_cia
       and    v4.cod_camp_cupon = v3.cod_camp_cupon
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
       AND    PROD.COD_GRUPO_CIA=V3.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD=V3.COD_PROD
       ;
  	END IF;
  RETURN curVta;
  END;

  FUNCTION VTA_LISTA_PROD_ALTERNATIVOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
  	   OPEN curVta FOR
       SELECT   PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
          		   PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_ALTE --ERIOS 11/04/2008 Origen
           || 'Ã'
          FROM   LGT_PROD PROD,
          		   LGT_PROD_LOCAL PROD_LOCAL,
          		   LGT_LAB LAB,
          		   PBL_IGV IGV,
                 LGT_PROD_VIRTUAL PR_VRT,
                 --21/11/2007 DUBILLUZ MODIFICADO

                 (SELECT DISTINCT(V1.COD_PROD) COD
                  FROM  (SELECT COD_PAQUETE,COD_PROD
                         FROM   VTA_PROD_PAQUETE
                         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                         ) V1,
                         VTA_PROMOCION    P
                /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

             AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              */
                WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND    P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND    P.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    P.ESTADO  = 'A'
                  AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                          OR
                           P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                 ) Z,
                 (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                        PROD_ENCARTE.COD_ENCARTE,
                        PROD_ENCARTE.COD_PROD
                 FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                        VTA_ENCARTE ENCARTE
                 WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                 AND    ENCARTE.ESTADO = 'A'
                 AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                 AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
                 )   V2
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND	   PROD_LOCAL.COD_PROD IN (select cod_prod
                                         from   lgt_prod
                                         where  COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND    cod_prod in (select cod_prod
                                                             from   lgt_princ_act_prod
                                                             where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                             AND    cod_prod != cCodProd_in
                                                             and    cod_princ_act in (select cod_princ_act
                                                                                      from   lgt_princ_act_prod
                                                                                      where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                                                      AND    cod_prod = cCodProd_in)
                                                             group by cod_prod
                                                             having count(*) = (select count(*)
                                                                                from   lgt_princ_act_prod
                                                                                where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                                                AND    cod_prod = cCodProd_in))
                                         and cod_prod IN (select cod_prod
                                                          from   lgt_princ_act_prod
                                                          where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                          AND    cod_prod != cCodProd_in
                                                          group by cod_prod
                                                          having count(*) = (select count(*)
                                                                             from   lgt_princ_act_prod
                                                                             where  COD_GRUPO_CIA = cCodGrupoCia_in
                                                                             AND    cod_prod = cCodProd_in)))
          AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD.COD_LAB = LAB.COD_LAB
          --AND	   LAB.IND_LAB_PROPIO = 'S' --LABORATORIO PROPIO
          AND	   PROD.COD_IGV = IGV.COD_IGV
          AND	   PROD.EST_PROD = 'A'
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
          AND    PROD.COD_PROD = Z.COD (+)
          AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
          AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
          AND    PROD.COD_PROD IN ( SELECT distinct  HIJO.COD_PROD
                                      FROM LGT_PROD PADRE,
                                           LGT_REL_UNID UPADRE,
                                           LGT_REL_UNID UHIJO,
                                           LGT_PROD HIJO
                                      WHERE HIJO.EST_PROD ='A'
                                            AND PADRE.COD_PROD = cCodProd_in
                                            AND UPADRE.COD_UNID_MEDIDA = PADRE.COD_UNID_MIN_FRAC
                                            AND UHIJO.COD_REL = UPADRE.COD_REL
                                            AND HIJO.COD_UNID_MIN_FRAC = UHIJO.COD_UNID_MEDIDA
                                    ) --JCORTEZ 18.04.08 productos con misma unidad de medida
          ORDER BY NVL(to_char(prod.val_bono_vig/PROD_LOCAL.VAL_FRAC_LOCAL,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
          ;

  	RETURN curVta;
  END;
    /* ************************************************************************ */

 FUNCTION VTA_LISTA_PROD_SUSTITUTOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)

 RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  /*  v_nCantCatSust INTEGER := 0;
    v_vCodIms LGT_PROD.COD_IMS_IV%TYPE;
    v_nAux INTEGER := 0;

   CURSOR curCat IS
    SELECT COD_CAT_SUST,IND_CAT
    FROM LGT_CAT_SUST_CAB C
    WHERE C.EST_CAT = 'A'
    ORDER BY C.ORD_CAT;*/
  BEGIN
/*
    SELECT COUNT(*)
      INTO v_nCantCatSust
    FROM LGT_CAT_SUST_CAB C
    WHERE C.EST_CAT = 'A'
    ORDER BY C.ORD_CAT;

    BEGIN
   IF v_nCantCatSust > 0 THEN
      --Determina la categoria a utilizar
      FOR fila IN curCat
      LOOP
        IF TRIM(fila.IND_CAT) = 'IMS' THEN

          SELECT P.COD_IMS_IV INTO v_vCodIms
          FROM LGT_PROD P
          WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                AND P.COD_PROD = cCodProd_in;

          SELECT SUM(INSTR(v_vCodIms,TRIM(COD_IND_CAT),1,1))
            INTO v_nAux
          FROM LGT_CAT_SUST_DET
          WHERE COD_CAT_SUST = fila.COD_CAT_SUST;

          IF v_nAux > 0 THEN
            DBMS_OUTPUT.PUT_LINE('CATEGORIA RETORNADA: '||TRIM(fila.IND_CAT)||' CODIGO: '||fila.COD_CAT_SUST);
            curVta := VTA_LISTA_PROD_SUST_IMS(cCodGrupoCia_in,
                          								 	   cCodLocal_in,
                          									   cCodProd_in,
                                               fila.COD_CAT_SUST);
            EXIT;
          END IF;

        ELSIF TRIM(fila.IND_CAT) = 'AGR' THEN

          SELECT COD_AGRUPACION INTO v_vCodIms
          FROM LGT_AGRUPA_PRODUCTO
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_PROD = cCodProd_in;

          SELECT SUM(INSTR(v_vCodIms,TRIM(COD_IND_CAT),1,1))
            INTO v_nAux
          FROM LGT_CAT_SUST_DET
          WHERE COD_CAT_SUST = fila.COD_CAT_SUST;

          IF v_nAux > 0 THEN
            DBMS_OUTPUT.PUT_LINE('CATEGORIA RETORNADA: '||TRIM(fila.IND_CAT)||' CODIGO: '||fila.COD_CAT_SUST);
            curVta := VTA_LISTA_PROD_SUST_AGR(cCodGrupoCia_in,
                          								 	   cCodLocal_in,
                          									   cCodProd_in,
                                               fila.COD_CAT_SUST);
            EXIT;
          END IF;

        ELSIF TRIM(fila.IND_CAT) = 'PROD' THEN

          SELECT COD_PROD INTO v_vCodIms
          FROM LGT_PROD
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_PROD = cCodProd_in;

          SELECT SUM(INSTR(v_vCodIms,TRIM(COD_IND_CAT),1,1))
            INTO v_nAux
          FROM LGT_CAT_SUST_DET
          WHERE COD_CAT_SUST = fila.COD_CAT_SUST;

          IF v_nAux > 0 THEN
            DBMS_OUTPUT.PUT_LINE('CATEGORIA RETORNADA: '||TRIM(fila.IND_CAT)||' CODIGO: '||fila.COD_CAT_SUST);
            curVta := VTA_LISTA_PROD_SUST_PROD(cCodGrupoCia_in,
                          								 	   cCodLocal_in,
                          									   cCodProd_in,
                                               fila.COD_CAT_SUST);
            EXIT;
          END IF;
        ELSE
          DBMS_OUTPUT.PUT_LINE('CATEGORIA NO ESTA SOPORTADA: '||TRIM(fila.IND_CAT));
        END IF;
      END LOOP;
    END IF;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_nCantCatSust := 0;
    END;
*/
 --   IF v_nCantCatSust = 0 OR v_nAux = 0 THEN
 --     DBMS_OUTPUT.PUT_LINE('RETORNA CONSULTA POR DEFECTO');

  	   OPEN curVta FOR --POR DEFECTO
--NUEVO

SELECT
      CASE WHEN PROD_LOCAL.IND_PROD_FRACCIONADO = 'N'  THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(  ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

         WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S'
              AND PROD.VAL_FRAC_VTA_SUG < PROD_LOCAL.VAL_FRAC_LOCAL     THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',TRIM(PROD.desc_unid_vta_sug)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen


             WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S'
             AND PROD.VAL_FRAC_VTA_SUG =  PROD_LOCAL.VAL_FRAC_LOCAL  THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

            WHEN PROD.VAL_FRAC_VTA_SUG IS NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S' THEN

                  PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||

                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||


          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

         ELSE
                  PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||

                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

                 END



          FROM   LGT_PROD PROD,
          		   LGT_PROD_LOCAL PROD_LOCAL,
          		   LGT_LAB LAB,
          		   PBL_IGV IGV,
                 LGT_PROD_VIRTUAL PR_VRT,
                 --21/11/2007 DUBILLUZ MODIFICADO
                 (SELECT DISTINCT(V1.COD_PROD) COD
                  FROM  (SELECT COD_PAQUETE,COD_PROD
                         FROM   VTA_PROD_PAQUETE
                         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                         ) V1,
                         VTA_PROMOCION    P
  /*                 WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
             AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
  */           --SE CAMBIO POR LA SIGUIENTE LINEA SIENDO QUE UNA PROMO TIENE FECHA DE INICIO SIEMPRE EN DIAS ENTEROS JLUNA 20090127
                  WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND    P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    P.ESTADO  = 'A'
                  AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                          OR
                           P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                ) Z,
                (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                        PROD_ENCARTE.COD_ENCARTE,
                        PROD_ENCARTE.COD_PROD
                 FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                        VTA_ENCARTE ENCARTE
                 WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                 AND    ENCARTE.ESTADO = 'A'
                 AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                 AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
                 )   V2
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND    (PROD_LOCAL.STK_FISICO) > 0

          AND	   PROD_LOCAL.COD_PROD IN ((
                                        -- 1. obtener los productos con mismos principios activos
                                         select cod_prod
                                         from   lgt_prod pp
                                         where  COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND    cod_prod in (select cod_prod
                                                             from   lgt_princ_act_prod
                                                             where  COD_GRUPO_CIA = cCodGrupoCia_in

                                                             AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
                                                             and    cod_princ_act in (select cod_princ_act
                                                                                      from   lgt_princ_act_prod
                                                                                      where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                                      AND    cod_prod      = cCodProd_in)
                                                             group by cod_prod
                                                             having count(*) = (select count(*)
                                                                                from   lgt_princ_act_prod
                                                                                where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                                AND    cod_prod      =cCodProd_in))
                                         and cod_prod IN (select cod_prod
                                                          from   lgt_princ_act_prod
                                                          where  COD_GRUPO_CIA =cCodGrupoCia_in
--
                                                          AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
                                                          group by cod_prod
                                                          having count(*) = (select count(*)
                                                                             from   lgt_princ_act_prod
                                                                             where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                             AND    cod_prod = cCodProd_in)))
                                             UNION
                                             --asolis
                                              -- 2. obtener los productos que pertenecen al mismo grupo lgt_cab_sust_cab y lgt_cab_sust_det
                                             (
                                             select cod_prod
                                             from   lgt_prod PP
                                             where  COD_GRUPO_CIA =cCodGrupoCia_in
                                             AND    EXISTS       (SELECT 1
                                                                  FROM  LGT_CAT_SUST_CAB C,
                                                                        LGT_CAT_SUST_DET D
                                                                  WHERE C.COD_CAT_SUST = D.COD_CAT_SUST
                                                                  AND   D.cod_ind_cat  = PP.cod_prod
--
                                                                  AND  (D.cod_ind_cat > cCodProd_in or D.cod_ind_cat < cCodProd_in )
                                                                  AND   C.IND_CAT = 'PROD'
                                                                  AND   C.EST_CAT = 'A'
                                                                  and   EXISTS            (SELECT 1
                                                                                           FROM   LGT_CAT_SUST_CAB C1,
                                                                                                  LGT_CAT_SUST_DET D
                                                                                           WHERE  C1.COD_CAT_SUST = D.COD_CAT_SUST
                                                                                           AND    C1.COD_CAT_SUST = C.COD_CAT_SUST
                                                                                           AND    C1.IND_CAT      = 'PROD'
                                                                                           AND    C1.EST_CAT      = 'A'
                                                                                           AND    D.COD_IND_CAT   = cCodProd_in))
                                            )

                                           --asolis
                                           -- 3. obtener los productos que pertenecen al mismo grupo lgt_grupo_similar y lgt_prod_grupo_similar

                                               UNION
                                                (select cod_prod
                                                 from   lgt_prod pp
                                                 where  COD_GRUPO_CIA = cCodGrupoCia_in

                                                 and  exists (select  1
                                                                FROM  lgt_prod_grupo_similar p,lgt_grupo_similar g
                                                                where p.cod_grupo     = g.cod_grupo
                                                                and   p.cod_grupo_cia = cCodGrupoCia_in

                                                                and   (p.cod_prod     > cCodProd_in or p.cod_prod     < cCodProd_in)
                                                                and   p.est_prod_grupo_similar='A'
                                                                and   g.est_grupo_similar='A'
                                                                and   p.cod_prod=pp.cod_prod
                                                                and   p.cod_grupo IN (SELECT cod_grupo
                                                                                   FROM lgt_prod_grupo_similar
                                                                                   WHERE cod_prod = cCodProd_in)))

                           )


          AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD.COD_LAB = LAB.COD_LAB
          --AND	   LAB.IND_LAB_PROPIO = 'S' --LABORATORIO PROPIO
          AND	   PROD.COD_IGV = IGV.COD_IGV
          AND	   PROD.EST_PROD = 'A'
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
          AND    PROD.COD_PROD = Z.COD (+)
          AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
          AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
          AND   exists( SELECT 1
                                      FROM LGT_PROD     PADRE,
                                           LGT_REL_UNID UPADRE,
                                           LGT_REL_UNID UHIJO,
                                           LGT_PROD     HIJO
                                      WHERE HIJO.EST_PROD ='A'
                                            AND PADRE.COD_PROD = cCodProd_in
                                            and HIJO.cod_prod=PROD.COD_PROD
                                            and HIJO.COD_GRUPO_CIA=cCodGrupoCia_in
                                            and PADRE.COD_GRUPO_CIA=cCodGrupoCia_in
                                            AND UPADRE.COD_UNID_MEDIDA = PADRE.COD_UNID_MIN_FRAC
                                            AND UHIJO.COD_REL = UPADRE.COD_REL
                                            AND HIJO.COD_UNID_MIN_FRAC = UHIJO.COD_UNID_MEDIDA
                                    ) --JCORTEZ 18.04.08 productos con misma unidad de medida
                                    --JLUNA USO DE EXISTS EN LUGAR DE IN
         -- ORDER BY NVL(to_char(prod.val_bono_vig/PROD_LOCAL.VAL_FRAC_LOCAL,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
         --asolis
          ORDER BY NVL(PROD.IND_ZAN || TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
          ;
-- END IF;

  	RETURN curVta;
  END;

   /***************************************************************************/

  /********************************************************************************************/
  FUNCTION VTA_LISTA_PROD_COMP1(cCodGrupoCia_in IN CHAR,
								 	             cCodLocal_in	   IN CHAR,
                               cIP_in      IN CHAR,
                               cProdVentaPedido_in in varchar2)
     RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    indActivComplentario char(1);
  BEGIN

  begin
      select trim(t.llave_tab_gral)
      into   indActivComplentario
      from   pbl_tab_gral t
      where  t.id_tab_gral = 400;
  exception
  when others then
      indActivComplentario := 'S';
  end;

  if indActivComplentario = 'S' then
--  EXECUTE IMMEDIATE 'TRUNCATE TABLE TT_CAT_PROD_COMP';

  DELETE FROM AUX_CAT_PROD_COMP WHERE   IP_PC = cIP_in;

  delete from aux_prod_pedido_pc where  IP_PC = cIP_in;

  insert into aux_prod_pedido_pc
  (Cod_Ims,cod_prod_original,IP_PC)
  select p.Cod_Ims_Iv   ,P.cod_prod ,cIP_in
   from   lgt_prod p
   WHERE  p.cod_grupo_cia = cCodGrupoCia_in
   and    p.cod_prod in (SELECT trim(EXTRACTVALUE(xt.column_value,'e'))
                         FROM   TABLE(XMLSEQUENCE
                                 ( EXTRACT
                                   ( XMLTYPE('<coll><e>' ||
                                     REPLACE(cProdVentaPedido_in,'@','</e><e>') ||
                                       '</e></coll>')
                                     , '/coll/e') )) xt
                          )
   AND    COD_IMS_IV IS NOT NULL;



  insert into AUX_CAT_PROD_COMP(COD_PROD_COMP,COD_PROD_ORIGINAL,DESC_MOTIVO, IP_PC)
  select DISTINCT prod.cod_prod,
                  cod_prod_original,
                  desc_motivo,
                  cIP_in
                from   lgt_prod prod ,
                       lgt_prod prod2 ,
                       lgt_prod_local PLOCAL,
                       lgt_prod_local PLOCAL2,
--                       pbl_igv,
--                       pbl_igv pbl_igv2,
                       (
                        select crtc.cod_cat_padre,
                               crtc.cod_cat_hijo,
                               c_iv2.ims_iv     ims_iv_hijo,
                               c_iv_ped.COD_ims ims_iv_original, --
                               c_iv_ped.cod_prod_original,  --
                               crtc.desc_motivo
                        from   CAT_REL_CAT_COMPLEMENT_IMS crtc,
                               (select p.Cod_Ims ,p.cod_prod_original
                                from   aux_prod_pedido_pc p
                               ) c_iv_ped,
                               cat_producto_Iv c_iv2
                        where   trim(crtc.cod_cat_padre)= substr(c_iv_ped.COD_ims,1,length(trim(crtc.cod_cat_padre)))
                        and     trim(crtc.cod_cat_hijo)=  substr(c_iv2.ims_iv,1,length(trim(crtc.cod_cat_hijo)))
                       ) q2
                where  prod.Cod_Ims_Iv     = q2.ims_iv_hijo
--                where  substr(prod.Cod_Ims_Iv,1,length(trim(q2.cod_cat_hijo)))     = trim(q2.cod_cat_hijo)
                and    prod2.cod_prod      = q2.cod_prod_original
                and    prod.cod_grupo_cia  = plocal.cod_grupo_cia
                and    prod.cod_prod       = plocal.cod_prod
                and    prod.cod_grupo_cia  = cCodGrupoCia_in
                and    plocal.cod_local    = cCodLocal_in
                and    prod2.cod_grupo_cia = plocal2.cod_grupo_cia
                and    prod2.cod_prod      = plocal2.cod_prod
                and    prod2.cod_grupo_cia = cCodGrupoCia_in
                and    plocal2.cod_local   = cCodLocal_in
                and    plocal.stk_fisico >0
--                and    prod.cod_igv=pbl_igv.cod_igv
--                and    prod2.cod_igv=pbl_igv.cod_igv
/* 2008-04-19 JOLIVA: YA NO SE CONSIDERA LA CONTRIBUCION PARA LOS COMPLEMENTARIOS
                and    (plocal.val_prec_vta *plocal.val_frac_local /(1+pbl_igv.porc_igv/100) -prod.val_prec_prom) /prod.val_max_frac >=
                       (plocal2.val_prec_vta*plocal2.val_frac_local/(1+pbl_igv2.porc_igv/100)-prod2.val_prec_prom)/prod2.val_max_frac
*/
                and    plocal.val_prec_vta <
                       2*plocal2.val_prec_vta
                and    q2.ims_iv_hijo not in
                                           (select p.Cod_Ims
                                            from   aux_prod_pedido_pc p
                                           );

/*
  delete TT_CAT_PROD_COMP t
  where  t.cod_prod_original<(select max(t1.cod_prod_original) from tt_cat_prod_comp t1 where t1.cod_prod_comp=t.cod_prod_comp);
*/
  delete AUX_CAT_PROD_COMP t
  where  t.ip_pc = cIP_in
         and t.cod_prod_original<(select max(t1.cod_prod_original) from aux_cat_prod_comp t1 where t1.cod_prod_comp=t.cod_prod_comp and t1.ip_pc = cIP_in);

  delete from aux_prod_pedido_pc where  IP_PC = cIP_in;

  COMMIT;

   OPEN curVta FOR
   --SELECT * FROM LGT_PROD WHERE COD_PROD='1';
        SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			     LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			     TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			     NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			     PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			     TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
           DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG          || 'Ã' ||
           PROD.IND_TIPO_PROD          || 'Ã' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
           DECODE(PROD.IND_PROD_PROPIO,'S','1','N','0')||NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')|| 'Ã' ||
           NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
           IND_ORIGEN_COMP --ERIOS 11/04/2008 Origen
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           --21/11/2007 DUBILLUZ MODIFICADO
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P
           /* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                AND  TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
        */
           WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
            AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
            AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO  = 'A'
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
            ) Z,
            (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                        PROD_ENCARTE.COD_ENCARTE,
                        PROD_ENCARTE.COD_PROD
                 FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                        VTA_ENCARTE ENCARTE
                 WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                 AND    ENCARTE.ESTADO = 'A'
                 AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                 AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
                 )   V2
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    PROD.COD_PROD = z.cod (+)
    AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
    AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
    and    prod.cod_prod in
               (
--                  select cod_prod_comp from TT_CAT_PROD_COMP
                  select cod_prod_comp from AUX_CAT_PROD_COMP WHERE ip_pc = cIP_in
                )
    ;
   else
OPEN curVta FOR
   --SELECT * FROM LGT_PROD WHERE COD_PROD='1';
        SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			     LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			     TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			     NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			     PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			     TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
           DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG          || 'Ã' ||
           PROD.IND_TIPO_PROD          || 'Ã' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
           DECODE(PROD.IND_PROD_PROPIO,'S','1','N','0')||NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')|| 'Ã' ||
           NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
           IND_ORIGEN_COMP --ERIOS 11/04/2008 Origen
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           --21/11/2007 DUBILLUZ MODIFICADO
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P
           /* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                AND  TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
        */
           WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
            AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
            AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO  = 'A'
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
            ) Z,
            (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                        PROD_ENCARTE.COD_ENCARTE,
                        PROD_ENCARTE.COD_PROD
                 FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                        VTA_ENCARTE ENCARTE
                 WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                 AND    ENCARTE.ESTADO = 'A'
                 AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                 AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
                 )   V2
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    PROD.COD_PROD = z.cod (+)
    AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
    AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
    and   1=2
    and    prod.cod_prod in
               (
--                  select cod_prod_comp from TT_CAT_PROD_COMP
                  select cod_prod_comp from AUX_CAT_PROD_COMP WHERE ip_pc = cIP_in
                )
    ;

   end if;
    RETURN curVta;
  END;

  FUNCTION VTA_LISTA_PROD_OFERTA(cCodGrupoCia_in IN CHAR,
								 	             cCodLocal_in	   IN CHAR,
                               cIP_in      IN CHAR,
							   cProdVentaPedido_in in varchar2)
     RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN

  delete from aux_prod_pedido_pc where  IP_PC = cIP_in;

  insert into aux_prod_pedido_pc
  (Cod_Ims,cod_prod_original,IP_PC)
  select p.Cod_Ims_Iv   ,P.cod_prod ,cIP_in
   from   lgt_prod p
   WHERE  p.cod_grupo_cia = cCodGrupoCia_in
   and    p.cod_prod in (SELECT trim(EXTRACTVALUE(xt.column_value,'e'))
                         FROM   TABLE(XMLSEQUENCE
                                 ( EXTRACT
                                   ( XMLTYPE('<coll><e>' ||
                                     REPLACE(cProdVentaPedido_in,'@','</e><e>') ||
                                       '</e></coll>')
                                     , '/coll/e') )) xt
                          );
   OPEN curVta FOR
    	SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			     LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			     TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA-(PROD_LOCAL.VAL_PREC_VTA * V3.PORC_DCTO_OFERTA/100)),'999,990.000')|| 'Ã' ||--JCHAVEZ 29102009  precio redondeado
			     NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			     PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			     TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
           DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG          || 'Ã' ||
           PROD.IND_TIPO_PROD          || 'Ã' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
           PROD.DESC_PROD ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
           IND_ORIGEN_OFER --ERIOS 11/04/2008 Origen

		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           --21/11/2007 DUBILLUZ MODIFICADO
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P

           /* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                    TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
             WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
            AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
            AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO  = 'A'
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))) Z,
           (
           SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                  PROD_ENCARTE.COD_ENCARTE,
                  PROD_ENCARTE.COD_PROD
           FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                  VTA_ENCARTE ENCARTE
           WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
           AND    ENCARTE.ESTADO = 'A'
           AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
           AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
           )   V2 ,
           LGT_PROD_LOCAL_OFERTA V3,
           LGT_OFERTA V4
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
    AND    V4.EST_OFERTA='A'
    AND    PROD_LOCAL.STK_FISICO > 0
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND  PROD.COD_PROD = z.cod (+)
    AND  PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
    AND  PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
    AND  PROD.COD_PROD=V3.COD_PROD --OFERTA
    AND  PROD_LOCAL.COD_LOCAL=V3.COD_LOCAL --OFERTA
    AND  V3.COD_OFERTA=V4.COD_OFERTA --OFERTA
    AND  PROD.COD_PROD IN (SELECT COD_PROD
                           FROM LGT_PROD_LOCAL_OFERTA Y
                           WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
                           AND Y.COD_LOCAL=cCodLocal_in)
    --dubilluz 15.10.2011
	AND  PROD.COD_PROD NOT IN (SELECT X.cod_prod_original
                               FROM aux_prod_pedido_pc X
                               WHERE IP_PC=cIP_in)
    AND TRUNC(SYSDATE) BETWEEN TO_DATE(TO_CHAR(V3.FEC_INI_VIG_OFERTA,'DD/MM/YYYY')||'00:00:00','DD/MM/YYYY HH24:MI:SS')
                       AND TO_DATE(TO_CHAR(V3.FEC_FIN_VIG_OFERTA,'DD/MM/YYYY')||'23:59:59','DD/MM/YYYY HH24:MI:SS')
    ;
	delete from aux_prod_pedido_pc where  IP_PC = cIP_in;
    RETURN curVta;
  END;


  /****************************************************************************************/
   FUNCTION VTA_OBTIENE_INFO_PROD_OFER(cCodGrupoCia_in IN CHAR,
        		   				  		           cCodLocal_in	   IN CHAR,
      								                 cCodProd_in	   IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
		SELECT (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			   PROD.DESC_UNID_PRESENT || 'Ã' ||
			   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   TO_CHAR((PROD_LOCAL.VAL_PREC_VTA * PROD_LOCAL.VAL_FRAC_LOCAL - (PROD_LOCAL.VAL_PREC_VTA * V3.PORC_DCTO_OFERTA/100)),'999,990.000') || 'Ã' ||
			   PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA-(PROD_LOCAL.VAL_PREC_VTA * V3.PORC_DCTO_OFERTA/100),'999,990.000')|| 'Ã' ||
			   NVL(DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA),' ')|| 'Ã' ||
         PROD_LOCAL.IND_PROD_HABIL_VTA || 'Ã' ||
			   TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'990.000') || 'Ã' ||
         PROD.IND_TIPO_PROD || 'Ã' ||
		     NVL(PROD.IND_ZAN,' ') || 'Ã' ||
         TO_CHAR(V3.PORC_DCTO_OFERTA,'990.000') || 'Ã' ||-- DCTO 2
         TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000')|| 'Ã' ||
         TO_CHAR(PROD_LOCAL.VAL_PREC_VTA * V3.PORC_DCTO_OFERTA/100,'999,990.000')
		FROM LGT_PROD PROD,
			   LGT_PROD_LOCAL PROD_LOCAL,
         LGT_PROD_LOCAL_OFERTA V3,
         LGT_OFERTA V4
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
		AND	   PROD_LOCAL.COD_PROD = cCodProd_in
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
    AND  PROD.COD_PROD=V3.COD_PROD
    AND  PROD_LOCAL.COD_LOCAL=V3.COD_LOCAL
    AND  V3.COD_OFERTA=V4.COD_OFERTA
    AND TRUNC(SYSDATE) BETWEEN TO_DATE(TO_CHAR(V3.FEC_INI_VIG_OFERTA,'DD/MM/YYYY')||'00:00:00','DD/MM/YYYY HH24:MI:SS')
                       AND TO_DATE(TO_CHAR(V3.FEC_FIN_VIG_OFERTA,'DD/MM/YYYY')||'23:59:59','DD/MM/YYYY HH24:MI:SS');

    RETURN curVta;
  END;

  /****************************************************************************************/
   FUNCTION VTA_OBTIENE_INFO_PROD_COMP(cCodGrupoCia_in IN CHAR,
                                       cCodProdComp_in IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR


    SELECT B.COD_PROD || 'Ã' ||
           B.DESC_PROD  || 'Ã' ||
           B.DESC_UNID_PRESENT  || 'Ã' ||
           C.NOM_LAB || 'Ã' ||
           NVL(A.DESC_MOTIVO,' ')
--    FROM  TT_CAT_PROD_COMP A,
    FROM  AUX_CAT_PROD_COMP A,
          LGT_PROD B,
          LGT_LAB C
    WHERE  B.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_PROD_COMP=cCodProdComp_in
    AND A.COD_PROD_ORIGINAL=B.COD_PROD
    AND B.COD_LAB=C.COD_LAB
    AND A.IP_PC = SUBSTR(SYS_CONTEXT('USERENV','IP_ADDRESS'),1,50);

    RETURN curVta;
  END;

  /****************************************************************************************/
   FUNCTION VTA_LISTA_FILTRO_PROD(cCodGrupoCia_in IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR

    /* SELECT  'ENCARTE MES DE LA MADRE'|| 'Ã' ||
             '4'
     FROM DUAL
     UNION
     SELECT  'PROMOCION MULTIMARCA - CUPON'|| 'Ã' ||
             '5'
     FROM DUAL
     UNION
     SELECT  'OFERTAS'|| 'Ã' ||
             '6'
     FROM DUAL;*/

     -- 03.07.2008 DUBILLUZ

     SELECT  'PROMOCION CUZCO - MACHUPICHU'|| 'Ã' ||
             '5'
     FROM DUAL
     UNION
     SELECT  'OFERTAS'|| 'Ã' ||
             '6'
     FROM DUAL;

    RETURN curVta;
  END;
 /* ********************************************************/
  FUNCTION VTA_LISTA_FILTRO_ESPECIALIZADO(cCodGrupoCia_in IN CHAR,
                  		   				          cCodLocal_in	  IN CHAR,
                                          cDescProd_in   IN VARCHAR2)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
SELECT *
FROM
(SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			     LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			     TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' ||
			     --TO_CHAR(PROD.VAL_BONO_VIG,'990.00') || 'Ã' ||
			     PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			     --PROD_LOCAL.VAL_PREC_LISTA || 'Ã' ||
			     TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
           DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG          || 'Ã' ||
           PROD.IND_TIPO_PROD          || 'Ã' ||
           --DECODE(NVL(PROM.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
           PROD.DESC_PROD ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--JCORTEZ ind encarte
           IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã' || '1'||PROD.DESC_PROD
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           --21/11/2007 DUBILLUZ MODIFICADO
           --VTA_PROD_PAQUETE  PROM
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P
           /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')

                          */

          WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
           AND P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
            AND P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO  = 'A'
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))) Z,
           (
           SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                  PROD_ENCARTE.COD_ENCARTE,
                  PROD_ENCARTE.COD_PROD
           FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                  VTA_ENCARTE ENCARTE
           WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
           AND    ENCARTE.ESTADO = 'A'
           AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
           AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
           )   V2
           --VTA_PROD_ENCARTE V2
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
    AND    PROD.DESC_PROD LIKE cDescProd_in||'%'
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    --AND    PROD.COD_PROD = PROM.COD_PROD(+)
    --AND    PROD.COD_GRUPO_CIA= PROM.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = z.cod (+)
    AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
    AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ

    UNION
SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			     LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			     TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			     NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			     PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			     TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
           DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG          || 'Ã' ||
           PROD.IND_TIPO_PROD          || 'Ã' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
           PROD.DESC_PROD ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--JCORTEZ ind encarte
           IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã' || '2'||PROD.DESC_PROD
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           --21/11/2007 DUBILLUZ MODIFICADO
           --VTA_PROD_PAQUETE  PROM
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P

           /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN


                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
             WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)

            AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO  = 'A'
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))) Z,
           (
           SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                  PROD_ENCARTE.COD_ENCARTE,
                  PROD_ENCARTE.COD_PROD
           FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                  VTA_ENCARTE ENCARTE
           WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
           AND    ENCARTE.ESTADO = 'A'
           AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
           AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
           )   V2
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
    AND    PROD.DESC_PROD LIKE '%'||cDescProd_in||'%'
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    PROD.COD_PROD = z.cod (+)
    AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
    AND   PROD.COD_PROD=V2.COD_PROD(+)
    AND   PROD.COD_PROD NOT IN (SELECT COD_PROD FROM LGT_PROD WHERE DESC_PROD LIKE cDescProd_in||'%')
    );--JCORTEZ

    RETURN curVta;
  END;
  /***************************************************************************/
  FUNCTION VTA_LISTA_PROD_SUST_IMS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLista FarmaCursor;
  BEGIN
    OPEN curLista FOR
    SELECT PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
          		   PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen
          FROM   LGT_PROD PROD,
          		   LGT_PROD_LOCAL PROD_LOCAL,
          		   LGT_LAB LAB,
          		   PBL_IGV IGV,
                 LGT_PROD_VIRTUAL PR_VRT,
                 --21/11/2007 DUBILLUZ MODIFICADO

                 (SELECT DISTINCT(V1.COD_PROD) COD
                  FROM  (SELECT COD_PAQUETE,COD_PROD
                         FROM   VTA_PROD_PAQUETE
                         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                         ) V1,
                         VTA_PROMOCION    P
              /*   WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                 WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    P.ESTADO  = 'A'
                  AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                          OR
                           P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                 ) Z,
                 (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                        PROD_ENCARTE.COD_ENCARTE,
                        PROD_ENCARTE.COD_PROD
                 FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                        VTA_ENCARTE ENCARTE
                 WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                 AND    ENCARTE.ESTADO = 'A'
                 AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                 AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
                 )   V2
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND    (PROD_LOCAL.STK_FISICO) > 0
          --AND prod.ind_prod_propio = 'N' --ERIOS 16/04/2008
          AND (((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC > =
                                                                                       (SELECT (((PL.VAL_PREC_VTA*PL.VAL_FRAC_LOCAL)/(1+I.PORC_IGV/100))-
                                                                                               (P.VAL_PREC_PROM))/P.VAL_MAX_FRAC AS CONTRIB
                                                                                        FROM LGT_PROD P,
                                                                                             LGT_PROD_LOCAL PL,
                                                                                             PBL_IGV I
                                                                                        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                                                                              AND PL.COD_LOCAL = cCodLocal_in
                                                                                              AND P.COD_PROD = cCodProd_in
                                                                                              AND P.EST_PROD = 'A'
                                                                                              AND P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                                                                                              AND P.COD_PROD = PL.COD_PROD
                                                                                              AND P.COD_IGV = I.COD_IGV)
          --ERIOS 11/06/2008 Filtro por IMS
          AND	   PROD_LOCAL.COD_PROD IN (SELECT COD_PROD
                                          FROM LGT_PROD P,
                                               (SELECT COD_IND_CAT
                                                FROM LGT_CAT_SUST_DET
                                                WHERE COD_CAT_SUST = cCodCatSust_in) V
                                          WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND P.COD_PROD NOT IN cCodProd_in
                                                AND P.COD_IMS_IV LIKE TRIM(V.COD_IND_CAT)||'%')
          AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD.COD_LAB = LAB.COD_LAB
          --AND	   LAB.IND_LAB_PROPIO = 'S' --LABORATORIO PROPIO
          AND	   PROD.COD_IGV = IGV.COD_IGV
          AND	   PROD.EST_PROD = 'A'
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
          AND    PROD.COD_PROD = Z.COD (+)
          AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
          AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
          AND    PROD.COD_PROD IN ( SELECT distinct  HIJO.COD_PROD
                                      FROM LGT_PROD PADRE,
                                           LGT_REL_UNID UPADRE,
                                           LGT_REL_UNID UHIJO,
                                           LGT_PROD HIJO
                                      WHERE HIJO.EST_PROD ='A'
                                            AND PADRE.COD_PROD = cCodProd_in
                                            AND UPADRE.COD_UNID_MEDIDA = PADRE.COD_UNID_MIN_FRAC
                                            AND UHIJO.COD_REL = UPADRE.COD_REL
                                            AND HIJO.COD_UNID_MIN_FRAC = UHIJO.COD_UNID_MEDIDA
                                    ) --JCORTEZ 18.04.08 productos con misma unidad de medida
          ORDER BY NVL(to_char(prod.val_bono_vig/PROD_LOCAL.VAL_FRAC_LOCAL,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
          ;
    RETURN curLista;
  END;
  /***************************************************************************/
  FUNCTION VTA_LISTA_PROD_SUST_AGR(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLista FarmaCursor;
  BEGIN
    OPEN curLista FOR
    SELECT PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
          		   PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen
          FROM   LGT_PROD PROD,
          		   LGT_PROD_LOCAL PROD_LOCAL,
          		   LGT_LAB LAB,
          		   PBL_IGV IGV,
                 LGT_PROD_VIRTUAL PR_VRT,
                 --21/11/2007 DUBILLUZ MODIFICADO

                 (SELECT DISTINCT(V1.COD_PROD) COD
                  FROM  (SELECT COD_PAQUETE,COD_PROD
                         FROM   VTA_PROD_PAQUETE
                         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                         ) V1,
                         VTA_PROMOCION    P
               /*   WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                  TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')*/
                 WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                 AND   P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    P.ESTADO  = 'A'
                  AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                          OR
                           P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                ) Z,
               (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND    (PROD_LOCAL.STK_FISICO) > 0
          --AND prod.ind_prod_propio = 'N' --ERIOS 16/04/2008
          AND (((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC > =
                                                                                       (SELECT (((PL.VAL_PREC_VTA*PL.VAL_FRAC_LOCAL)/(1+I.PORC_IGV/100))-
                                                                                               (P.VAL_PREC_PROM))/P.VAL_MAX_FRAC AS CONTRIB
                                                                                        FROM LGT_PROD P,
                                                                                             LGT_PROD_LOCAL PL,
                                                                                             PBL_IGV I
                                                                                        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                                                                              AND PL.COD_LOCAL = cCodLocal_in
                                                                                              AND P.COD_PROD = cCodProd_in
                                                                                              AND P.EST_PROD = 'A'
                                                                                              AND P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                                                                                              AND P.COD_PROD = PL.COD_PROD
                                                                                              AND P.COD_IGV = I.COD_IGV)
          --ERIOS 11/06/2008 Filtro por AGRUPACION
          AND	   PROD_LOCAL.COD_PROD IN (SELECT COD_PROD
                                        FROM LGT_AGRUPA_PRODUCTO P,
                                             (SELECT COD_IND_CAT
                                              FROM LGT_CAT_SUST_DET
                                              WHERE COD_CAT_SUST = cCodCatSust_in) V
                                        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND P.COD_PROD NOT IN cCodProd_in
                                              AND P.COD_AGRUPACION LIKE TRIM(V.COD_IND_CAT)||'%')
          AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD.COD_LAB = LAB.COD_LAB
          --AND	   LAB.IND_LAB_PROPIO = 'S' --LABORATORIO PROPIO
          AND	   PROD.COD_IGV = IGV.COD_IGV
          AND	   PROD.EST_PROD = 'A'
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
          AND    PROD.COD_PROD = Z.COD (+)
          AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
          AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
          AND    PROD.COD_PROD IN ( SELECT distinct  HIJO.COD_PROD
                                      FROM LGT_PROD PADRE,
                                           LGT_REL_UNID UPADRE,
                                           LGT_REL_UNID UHIJO,
                                           LGT_PROD HIJO
                                      WHERE HIJO.EST_PROD ='A'
                                            AND PADRE.COD_PROD = cCodProd_in
                                            AND UPADRE.COD_UNID_MEDIDA = PADRE.COD_UNID_MIN_FRAC
                                            AND UHIJO.COD_REL = UPADRE.COD_REL
                                            AND HIJO.COD_UNID_MIN_FRAC = UHIJO.COD_UNID_MEDIDA
                                    ) --JCORTEZ 18.04.08 productos con misma unidad de medida
          ORDER BY NVL(to_char(prod.val_bono_vig/PROD_LOCAL.VAL_FRAC_LOCAL,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
          --ORDER BY NVL(to_char(prod.val_bono_vig/PROD_LOCAL.VAL_FRAC_LOCAL,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA)/(1+IGV.PORC_IGV/100))-(PROD.VAL_PREC_PROM/PROD_LOCAL.VAL_FRAC_LOCAL)),'999990.000'),' ') DESC
          ;
    RETURN curLista;
  END;
  /***************************************************************************/
  FUNCTION VTA_LISTA_PROD_SUST_PROD(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLista FarmaCursor;
  BEGIN
    OPEN curLista FOR
    SELECT PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
          		   PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

          FROM   LGT_PROD PROD,
          		   LGT_PROD_LOCAL PROD_LOCAL,
          		   LGT_LAB LAB,
          		   PBL_IGV IGV,
                 LGT_PROD_VIRTUAL PR_VRT,
                 --21/11/2007 DUBILLUZ MODIFICADO
                 (SELECT DISTINCT(V1.COD_PROD) COD
                  FROM  (SELECT COD_PAQUETE,COD_PROD
                         FROM   VTA_PROD_PAQUETE
                         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                         ) V1,
                         VTA_PROMOCION    P
                /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                    WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    P.ESTADO  = 'A'
                  AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                          OR
                           P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                 ) Z,
                 (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                        PROD_ENCARTE.COD_ENCARTE,
                        PROD_ENCARTE.COD_PROD
                 FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                        VTA_ENCARTE ENCARTE
                 WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                 AND    ENCARTE.ESTADO = 'A'
                 AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                 AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
                 )   V2
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND    (PROD_LOCAL.STK_FISICO) > 0
          --AND prod.ind_prod_propio = 'N' --ERIOS 16/04/2008
          AND (((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC > =
                                                                                       (SELECT (((PL.VAL_PREC_VTA*PL.VAL_FRAC_LOCAL)/(1+I.PORC_IGV/100))-
                                                                                               (P.VAL_PREC_PROM))/P.VAL_MAX_FRAC AS CONTRIB
                                                                                        FROM LGT_PROD P,
                                                                                             LGT_PROD_LOCAL PL,
                                                                                             PBL_IGV I
                                                                                        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                                                                              AND PL.COD_LOCAL = cCodLocal_in
                                                                                              AND P.COD_PROD = cCodProd_in
                                                                                              AND P.EST_PROD = 'A'
                                                                                              AND P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                                                                                              AND P.COD_PROD = PL.COD_PROD
                                                                                              AND P.COD_IGV = I.COD_IGV)
          --ERIOS 11/06/2008 Filtro por PRODUCTOS
          AND	   PROD_LOCAL.COD_PROD IN (SELECT COD_PROD
                                          FROM LGT_PROD P,
                                               (SELECT COD_IND_CAT
                                                FROM LGT_CAT_SUST_DET
                                                WHERE COD_CAT_SUST = cCodCatSust_in) V
                                          WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND P.COD_PROD NOT IN cCodProd_in
                                                AND P.COD_PROD LIKE TRIM(V.COD_IND_CAT)||'%')
          AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD.COD_LAB = LAB.COD_LAB
          --AND	   LAB.IND_LAB_PROPIO = 'S' --LABORATORIO PROPIO
          AND	   PROD.COD_IGV = IGV.COD_IGV
          AND	   PROD.EST_PROD = 'A'
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
          AND    PROD.COD_PROD = Z.COD (+)
          AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
          AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
          AND    PROD.COD_PROD IN ( SELECT distinct  HIJO.COD_PROD
                                      FROM LGT_PROD PADRE,
                                           LGT_REL_UNID UPADRE,
                                           LGT_REL_UNID UHIJO,
                                           LGT_PROD HIJO
                                      WHERE HIJO.EST_PROD ='A'
                                            AND PADRE.COD_PROD = cCodProd_in
                                            AND UPADRE.COD_UNID_MEDIDA = PADRE.COD_UNID_MIN_FRAC
                                            AND UHIJO.COD_REL = UPADRE.COD_REL
                                            AND HIJO.COD_UNID_MIN_FRAC = UHIJO.COD_UNID_MEDIDA
                                    ) --JCORTEZ 18.04.08 productos con misma unidad de medida
          ORDER BY NVL(to_char(prod.val_bono_vig/PROD_LOCAL.VAL_FRAC_LOCAL,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
          ;
    RETURN curLista;
  END;
  /***************************************************************************/
end;

/
