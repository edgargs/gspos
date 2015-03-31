CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_VTA_LISTA" is

  TYPE FarmaCursor IS REF CURSOR;

  IND_ORIGEN_PROD CONSTANT CHAR(1) := '1';
  IND_ORIGEN_SUST CONSTANT CHAR(1) := '2';
  IND_ORIGEN_ALTE CONSTANT CHAR(1) := '3';
  IND_ORIGEN_COMP CONSTANT CHAR(1) := '4';
  IND_ORIGEN_OFER CONSTANT CHAR(1) := '5';
  IND_ORIGEN_REGA CONSTANT CHAR(1) := '6';

  --Descripcion: Obtiene el listado de los productos
  --Fecha       Usuario		Comentario
  --14/02/2006  LMESIA     Creación
  --21/11/2007 dubilluz modificacion
  FUNCTION VTA_LISTA_PROD(cCodGrupoCia_in IN CHAR,
  		   				  cCodLocal_in	  IN CHAR)
	RETURN FarmaCursor;
  --Descripcion: Obtiene el listado de los productos filtrado
  --Fecha       Usuario		Comentario
  --22/02/2006  LMESIA     Creación
  --21/11/2007 dubilluz modificacion
  FUNCTION VTA_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
  		   				  		 cCodLocal_in	 IN CHAR,
								 cTipoFiltro_in  IN CHAR,
  		   						 cCodFiltro_in 	 IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de los productos alternativos
  --Fecha       Usuario		Comentario
  --24/02/2006  LMESIA     Creación
  --04/12/2007  dubilluz   Modificacion
  FUNCTION VTA_LISTA_PROD_ALTERNATIVOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Listado de productos sustitutos
  --Fecha       Usuario		Comentario
  --08/04/2008  ERIOS     Creacion
  FUNCTION VTA_LISTA_PROD_SUSTITUTOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se obtiene los productos complementarios de los que estan en el pedido
  --Fecha       Usuario		    Comentario
  --09/04/2008  JCORTEZ     Creación
  FUNCTION VTA_LISTA_PROD_COMP1(cCodGrupoCia_in IN CHAR,
								 	             cCodLocal_in	   IN CHAR,
                               cIP_in      IN CHAR,
                               cProdVentaPedido_in in varchar2
                               )
  RETURN FarmaCursor;

  --Descripcion: Se obtiene los productos en oferta
  --Fecha       Usuario		    Comentario
  --09/04/2008  JCORTEZ     Creación
  FUNCTION VTA_LISTA_PROD_OFERTA(cCodGrupoCia_in IN CHAR,
								 	             cCodLocal_in	   IN CHAR,
                               cIP_in      IN CHAR,
							   cProdVentaPedido_in in varchar2)
  RETURN FarmaCursor;

  --Descripcion: Se obtiene informacion de producto oferta
  --Fecha       Usuario		    Comentario
  --09/04/2008  JCORTEZ     Creación
  FUNCTION VTA_OBTIENE_INFO_PROD_OFER(cCodGrupoCia_in IN CHAR,
        		   				  		           cCodLocal_in	   IN CHAR,
      								                 cCodProd_in	   IN CHAR)
  RETURN FarmaCursor;

   --Descripcion: Se la descripcion del producto padre del complementario
  --Fecha       Usuario		    Comentario
  --15/04/2008  JCORTEZ     Creación
  FUNCTION VTA_OBTIENE_INFO_PROD_COMP(cCodGrupoCia_in IN CHAR,
                                       cCodProdComp_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se lista los tipo de filtro desde resumen de pedido (Encarte, Cupon)
  --Fecha       Usuario		    Comentario
  --18/04/2008  JCORTEZ     Creación
  FUNCTION VTA_LISTA_FILTRO_PROD(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se realiza un filtro especializado
  --Fecha       Usuario		    Comentario
  --08/05/2008  DUBILLUZ       Creación
  FUNCTION VTA_LISTA_FILTRO_ESPECIALIZADO(cCodGrupoCia_in IN CHAR,
                  		   				          cCodLocal_in	  IN CHAR,
                                          cDescProd_in   IN VARCHAR2)
  RETURN FarmaCursor;

  --Descripcion: Listado de productos sustitos por CATEGORIA:IMS
  --Fecha       Usuario		    Comentario
  --11/06/2008  ERIOS         Creacion
  FUNCTION VTA_LISTA_PROD_SUST_IMS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Listado de productos sustitos por CATEGORIA:AGRUPACION
  --Fecha       Usuario		    Comentario
  --11/06/2008  ERIOS         Creacion
  FUNCTION VTA_LISTA_PROD_SUST_AGR(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Listado de productos sustitos por CATEGORIA:PRODUCTOS
  --Fecha       Usuario		    Comentario
  --11/06/2008  ERIOS         Creacion
  FUNCTION VTA_LISTA_PROD_SUST_PROD(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: NUEVO FILTRO PARA LISTADO DE PRODUCTOS
  --Fecha       Usuario		    Comentario
  --09/01/2015  KMONCADA      CREACION
  FUNCTION VTA_LISTA_PROD_FILTRO_NEW(cCodGrupoCia_in IN CHAR,
  		   				  		           cCodLocal_in	   IN CHAR,
								                 cTipoFiltro_in  IN CHAR,
  		   						             cCodFiltro_in 	 IN CHAR,
                                 cTipoOrden      IN CHAR DEFAULT 'A')
    RETURN FarmaCursor;

end;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_VTA_LISTA" is
  --21/11/2007 dubilluz modificacion
  FUNCTION VTA_LISTA_PROD(cCodGrupoCia_in IN CHAR,
  		   				          cCodLocal_in	  IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
  -- 07.01.2015 ERIOS Garantizado por local
    OPEN curVta FOR
	SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO) || 'Ã' ||
           TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
           NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
           IND_ORIGEN_PROD  --ERIOS 11/04/2008 Origen
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
    AND    (PROD.IND_TIPO_CONSUMO IS NULL OR PROD.IND_TIPO_CONSUMO <> 'C')       --ASOSA - 01/10/2014 - PANHD
    AND    PROD.COD_PROD NOT IN (            --ASOSA - 19/01/2015 - RIMAC
                                                                                 SELECT nvl(RI.COD_PROD_REGALO,' ')
                                                                                 FROM LGT_PROD_RIMAC RI
                                                                                 )
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
-- 07.01.2015 ERIOS Garantizado por local
  	 IF(cTipoFiltro_in = 0) THEN -- SINTOMAS
       OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			   		  NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
           || 'Ã' ||
           NVL(--PROD.IND_ZAN 
			          DECODE(TRIM(PROD_LOCAL.IND_ZAN),'3G','40','GG','30','GP','20','G','10','')
				        || TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    PBL_SINTOMA SINTOMA, --ACC_TER,
					    PBL_SINTOMA_PROD SINTOMA_PROD, --ACC_TERAP_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = '001'
                      ) V1,
                      VTA_PROMOCION    P
               WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
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
       
			 AND	  SINTOMA.COD_SINTOMA = cCodFiltro_in
       
			 AND	  PROD.COD_GRUPO_CIA = SINTOMA_PROD.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = SINTOMA_PROD.COD_PROD
			 AND	  SINTOMA.COD_SINTOMA = SINTOMA_PROD.COD_SINTOMA
       
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
     
     ELSIF(cTipoFiltro_in = 1) THEN --principio activo
     	OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
			   		  NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
			   		  NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
			   		  NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
			   		  NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
			   		  NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
  -- 07.01.2015 ERIOS Garantizado por local
  	   OPEN curVta FOR
       SELECT   PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
  BEGIN

	OPEN curVta FOR
	  SELECT
	    PROD.COD_PROD || 'Ã' ||
        PROD.DESC_PROD || 'Ã' ||
        CASE WHEN PROD_LOCAL.IND_PROD_FRACCIONADO = 'N'  THEN          
			  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) 
			WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S'
					AND PROD.VAL_FRAC_VTA_SUG < PROD_LOCAL.VAL_FRAC_LOCAL     THEN
			  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',TRIM(PROD.desc_unid_vta_sug)) 
			WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S'
					AND PROD.VAL_FRAC_VTA_SUG =  PROD_LOCAL.VAL_FRAC_LOCAL  THEN
			  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',TRIM(PROD_LOCAL.UNID_VTA)) 
			WHEN PROD.VAL_FRAC_VTA_SUG IS NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S' THEN
			  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,TRIM(PROD_LOCAL.UNID_VTA)) 
			ELSE
			  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,TRIM(PROD_LOCAL.UNID_VTA)) 
		END || 'Ã' ||
		LAB.NOM_LAB || 'Ã' ||
		(PROD_LOCAL.STK_FISICO) || 'Ã' ||
		TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' ||
		NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
	    PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
	    PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
		TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' ||
		TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
        PROD.IND_PROD_FARMA || 'Ã' ||
        DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
        NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
        PROD.IND_PROD_REFRIG          || 'Ã' ||
        PROD.IND_TIPO_PROD            || 'Ã' ||
        DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
		' ' || 'Ã' ||
        NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
        IND_ORIGEN_SUST
      FROM   LGT_PROD PROD,
             LGT_PROD_LOCAL PROD_LOCAL,
          	 LGT_LAB LAB,
          	 PBL_IGV IGV,
             LGT_PROD_VIRTUAL PR_VRT,
			 (SELECT DISTINCT(V1.COD_PROD) COD
			  FROM  (SELECT COD_PAQUETE,COD_PROD
					 FROM   VTA_PROD_PAQUETE
					 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
					 ) V1,
					 VTA_PROMOCION    P
			  WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND    P.IND_DELIVERY='N' --solo para locales
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
          AND	   PROD_LOCAL.COD_PROD IN (
					(-- 1. obtener los productos con mismos principios activos
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
									  AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
									  group by cod_prod
									  having count(*) = (select count(*)
														 from   lgt_princ_act_prod
														 where  COD_GRUPO_CIA =cCodGrupoCia_in
														 AND    cod_prod = cCodProd_in)))
					 UNION                                              
					 (-- 2. obtener los productos que pertenecen al mismo grupo lgt_cab_sust_cab y lgt_cab_sust_det
					 select cod_prod
					 from   lgt_prod PP
					 where  COD_GRUPO_CIA =cCodGrupoCia_in
					 AND    EXISTS       (SELECT 1
										  FROM  LGT_CAT_SUST_CAB C,
												LGT_CAT_SUST_DET D
										  WHERE C.COD_CAT_SUST = D.COD_CAT_SUST
										  AND   D.cod_ind_cat  = PP.cod_prod
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
				   UNION
					(-- 3. obtener los productos que pertenecen al mismo grupo lgt_grupo_similar y lgt_prod_grupo_similar
					select cod_prod
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
				/*UNION
				  SELECT COD_PROD FROM(--4. ERIOS 08/01/2014 Incluye productos con los mismos sintomas
					select pp.cod_prod
					from lgt_prod pp
					where pp.cod_grupo_cia = cCodGrupoCia_in
					--and pp.est_prod = 'A'
					and exists (
						SELECT 1
						FROM pbl_sintoma_prod s
						where cod_grupo_cia = cCodGrupoCia_in
						and cod_prod = pp.cod_prod
						and cod_prod != cCodProd_in
						AND EXISTS (
							  select 1
							  from pbl_sintoma_prod p
							  where cod_grupo_cia = cCodGrupoCia_in
							  and cod_prod = cCodProd_in
							  and p.cod_sintoma = s.cod_sintoma)
					))
					WHERE 'S' = (SELECT DESC_CORTA
								from PBL_TAB_GRAL
								WHERE COD_APL = 'PTO_VENTA'
								AND COD_TAB_GRAL = 'IND_SUST_SINTOMAS'
								AND LLAVE_TAB_GRAL = '01')*/
          )
          AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD.COD_LAB = LAB.COD_LAB          
          AND	   PROD.COD_IGV = IGV.COD_IGV
          AND	   PROD.EST_PROD = 'A'
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
          AND    PROD.COD_PROD = Z.COD (+)
          AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD=V2.COD_PROD(+)
          AND   exists (  --productos con misma unidad de medida
						SELECT 1
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
						) 
         --ERIOS 2.3.3 Se agrega el tipo GP
          ORDER BY NVL(--PROD.IND_ZAN 
			  DECODE(TRIM(PROD_LOCAL.IND_ZAN),'3G','40','GG','30','GP','20','G','10','')
				|| TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') 
			  DESC
          ;

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
	--ERIOS 12.11.2014 Nueva logica de EYANEZ
	
--  EXECUTE IMMEDIATE 'TRUNCATE TABLE TT_CAT_PROD_COMP';

  DELETE FROM AUX_CAT_PROD_COMP WHERE   IP_PC = cIP_in;

  delete from aux_prod_pedido_pc where  IP_PC = cIP_in;

  insert into aux_prod_pedido_pc
  (Cod_Ims,cod_prod_original,IP_PC)
  select NULL Cod_Ims_Iv   ,P.cod_prod ,cIP_in
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
   --AND    COD_IMS_IV IS NOT NULL
   ;

  insert into AUX_CAT_PROD_COMP(COD_PROD_COMP,COD_PROD_ORIGINAL,DESC_MOTIVO, IP_PC)
  select comp.COD_PROD_COMP,p.COD_PROD_ORIGINAL,comp.MSJ_PROD_COMP,p.IP_PC
                                from   aux_prod_pedido_pc p
          join VTA_PROD_COMPLEMENTARIO comp ON (comp.COD_GRUPO_CIA = cCodGrupoCia_in
                                        and comp.COD_PROD = p.COD_PROD_ORIGINAL)
         join lgt_prod_local pl on (pl.COD_GRUPO_CIA = comp.COD_GRUPO_CIA
                                    AND pl.COD_LOCAL = cCodLocal_in
                                    AND pl.COD_PROD = comp.COD_PROD_COMP)   
  where p.IP_PC = cIP_in
  AND comp.EST_PROD_COMP = 'A'
  and pl.STK_FISICO > 0
  ;

  /*delete AUX_CAT_PROD_COMP t
  where  t.ip_pc = cIP_in
         and t.cod_prod_original<(select max(t1.cod_prod_original) from aux_cat_prod_comp t1 where t1.cod_prod_comp=t.cod_prod_comp and t1.ip_pc = cIP_in);

  delete from aux_prod_pedido_pc where  IP_PC = cIP_in;*/

  COMMIT;
-- 07.01.2015 ERIOS Garantizado por local
   OPEN curVta FOR
   --SELECT * FROM LGT_PROD WHERE COD_PROD='1';
        SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			     LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO) || 'Ã' ||
			     TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			     NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
           --DECODE(PROD.IND_PROD_PROPIO,'S','1','N','0')||NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')|| 'Ã' ||
		   NVL(DECODE(TRIM(PROD_LOCAL.IND_ZAN),'3G','40','GG','30','GP','20','G','10','')
				|| TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') || 'Ã' ||
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
    and not exists (select 1 from aux_prod_pedido_pc where ip_pc = cIP_in and cod_prod_original = prod.cod_prod)
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
			     NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
			     NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
		     NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
	--19/01/2015 ERIOS La data de complementarios, asocia un mismo producto para varios codigos como producto padre.
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
    AND A.IP_PC = SUBSTR(SYS_CONTEXT('USERENV','IP_ADDRESS'),1,50)
	AND ROWNUM = 1;

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
			     NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
  -- 07.01.2015 ERIOS Garantizado por local
    OPEN curLista FOR
    SELECT PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
  -- 07.01.2015 ERIOS Garantizado por local
    OPEN curLista FOR
    SELECT PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
  -- 07.01.2015 ERIOS Garantizado por local
    OPEN curLista FOR
    SELECT PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO) || 'Ã' ||
          		   TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
          		   NVL(PROD_LOCAL.IND_ZAN,' ') || 'Ã' ||
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
  
  
  
  FUNCTION VTA_LISTA_PROD_FILTRO_NEW(cCodGrupoCia_in IN CHAR,
  		   				  		           cCodLocal_in	   IN CHAR,
								                 cTipoFiltro_in  IN CHAR,
  		   						             cCodFiltro_in 	 IN CHAR,
                                 cTipoOrden      IN CHAR DEFAULT 'A') -- A ALFABETICO C COMERCIAL
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vFecha_actual date;
    vSelect varchar(5000);
    query varchar(7000);
    vOrden varchar(500);
    VPRUEBA varchar(5000);
  BEGIN
    SELECT TRUNC(SYSDATE)
    INTO   vFecha_actual
    FROM   DUAL;
    vSelect := 
             'SELECT PROD.COD_PROD || ''Ã'' ||
			   		  PROD.DESC_PROD || ''Ã'' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,''N'',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || ''Ã'' ||
			   		  LAB.NOM_LAB || ''Ã'' ||
			   		  (PROD_LOCAL.STK_FISICO) || ''Ã'' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,''999,990.000'') || ''Ã'' ||
			   		  NVL(PROD_LOCAL.IND_ZAN,'' '') || ''Ã'' ||
			   		  PROD_LOCAL.IND_PROD_CONG || ''Ã'' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || ''Ã'' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,''999,990.000'') || ''Ã'' ||
			        TO_CHAR(IGV.PORC_IGV,''990.00'') || ''Ã'' ||
			   		  PROD.IND_PROD_FARMA || ''Ã'' ||
              DECODE(NVL(PR_VRT.COD_PROD,''N''),''N'',''N'',''S'') || ''Ã'' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,'' '')|| ''Ã'' ||
              PROD.IND_PROD_REFRIG          || ''Ã'' ||
              PROD.IND_TIPO_PROD  || ''Ã'' ||
              DECODE(NVL(Z.COD,''N''),''N'',''N'',''S'')|| ''Ã'' ||
              PROD.DESC_PROD ||
	      	    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,''N'',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| ''Ã'' ||
              NVL(trim(V2.COD_ENCARTE),'' '')|| ''Ã'' ||
              ''1''  || ''Ã'' ';
              
-- 07.01.2015 ERIOS Garantizado por local
  	 IF(cTipoFiltro_in = 0) THEN -- SINTOMAS
  

 query  := 
        ' FROM   LGT_PROD PROD,'||chr(13)||
				'   	  LGT_PROD_LOCAL PROD_LOCAL,'||chr(13)||
				'   	  LGT_LAB LAB,'||chr(13)||
				'   	  PBL_IGV IGV,'||chr(13)||
			  '		    PBL_SINTOMA SINTOMA, '||chr(13)||
		    '		    PBL_SINTOMA_PROD SINTOMA_PROD, '||chr(13)||
        '      LGT_PROD_VIRTUAL PR_VRT,'||chr(13)||

        '      (SELECT DISTINCT(V1.COD_PROD) COD'||chr(13)||
        '       FROM  (SELECT COD_PAQUETE,COD_PROD'||chr(13)||
        '              FROM   VTA_PROD_PAQUETE'||chr(13)||
        '              WHERE  COD_GRUPO_CIA = ''001'' '||chr(13)||
        '              ) V1,'||chr(13)||
        '              VTA_PROMOCION    P'||chr(13)||

        'WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)'||chr(13)||
        '              AND  P.COD_GRUPO_CIA = 001'||chr(13)||
        '              AND    P.ESTADO  = ''A'' '||chr(13)||
        '              AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)  '||chr(13)||
        '                     OR  '||chr(13)||
        '                       P.COD_PAQUETE_2 = (V1.COD_PAQUETE))  '||chr(13)||
        '      ) Z, '||chr(13)||
        '      (SELECT PROD_ENCARTE.COD_GRUPO_CIA, '||chr(13)||
        '              PROD_ENCARTE.COD_ENCARTE, '||chr(13)||
        '              PROD_ENCARTE.COD_PROD '||chr(13)||
        '       FROM   VTA_PROD_ENCARTE PROD_ENCARTE, '||chr(13)||
        '              VTA_ENCARTE ENCARTE '||chr(13)||
        '       WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN '||chr(13)||
        '       AND    ENCARTE.ESTADO = ''A'' '||chr(13)||
        '       AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA '||chr(13)||
        '       AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE '||chr(13)||
        '       )   V2 '||chr(13)||
			 'WHERE  PROD_LOCAL.COD_GRUPO_CIA = '||cCodGrupoCia_in ||chr(13)||
			 'AND	  PROD_LOCAL.COD_LOCAL = '||cCodLocal_in ||chr(13)||
			' AND	  SINTOMA.COD_SINTOMA = '||cCodFiltro_in ||chr(13)||
			' AND	  PROD.COD_GRUPO_CIA = SINTOMA_PROD.COD_GRUPO_CIA '||chr(13)||
			' AND	  PROD.COD_PROD = SINTOMA_PROD.COD_PROD '||chr(13)||
			' AND	  SINTOMA.COD_SINTOMA = SINTOMA_PROD.COD_SINTOMA '||chr(13)||
			' AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA '||chr(13)||
			' AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD '||chr(13)||
			' AND	  PROD.COD_LAB = LAB.COD_LAB '||chr(13)||
			' AND	  PROD.COD_IGV = IGV.COD_IGV '||chr(13)||
			' AND	  PROD.EST_PROD = ''A'' '||chr(13)||
      ' AND    PR_VRT.EST_PROD_VIRTUAL(+) = ''A'' '||chr(13)||
      ' AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+) '||chr(13)||
      ' AND    PROD.COD_PROD = PR_VRT.COD_PROD(+) '||chr(13)||
      ' AND    PROD.COD_PROD = Z.COD(+) '||chr(13)||
      ' AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+)  '||chr(13)||
      ' AND   PROD.COD_PROD=V2.COD_PROD(+) '
      ;
     
     ELSIF(cTipoFiltro_in = 1) THEN --principio activo
       query :=

			 ' FROM   LGT_PROD PROD, '||chr(13)||
				   	  'LGT_PROD_LOCAL PROD_LOCAL, '||chr(13)||
				   	  'LGT_LAB LAB, '||chr(13)||
				   	  'PBL_IGV IGV, '||chr(13)||
					    'LGT_PRINC_ACT_PROD PRINC_ACT_PROD, '||chr(13)||
              'LGT_PROD_VIRTUAL PR_VRT, '||chr(13)||
              '(SELECT DISTINCT(V1.COD_PROD) COD '||chr(13)||
              ' FROM  (SELECT COD_PAQUETE,COD_PROD '||chr(13)||
              '        FROM   VTA_PROD_PAQUETE '||chr(13)||
              '        WHERE  COD_GRUPO_CIA = ''001'' '||chr(13)||
              '        ) V1, '||chr(13)||
              '        VTA_PROMOCION    P '||chr(13)||
              '        WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE) '||chr(13)||
              '        AND  P.IND_DELIVERY=''N''  '||chr(13)||
              '        AND  P.COD_GRUPO_CIA = 001 '||chr(13)||
              '        AND    P.ESTADO  = ''A'' '||chr(13)||
              '        AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE) '||chr(13)||
              '               OR '||chr(13)||
              '                 P.COD_PAQUETE_2 = (V1.COD_PAQUETE)) '||chr(13)||
              ') Z, '||chr(13)||
             '(SELECT PROD_ENCARTE.COD_GRUPO_CIA, '||chr(13)||
             '       PROD_ENCARTE.COD_ENCARTE, '||chr(13)||
             '       PROD_ENCARTE.COD_PROD '||chr(13)||
             'FROM   VTA_PROD_ENCARTE PROD_ENCARTE, '||chr(13)||
             '       VTA_ENCARTE ENCARTE '||chr(13)||
             'WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN '||chr(13)||
             'AND    ENCARTE.ESTADO = ''A'' '||chr(13)||
             'AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA '||chr(13)||
             'AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE '||chr(13)||
             ')   V2 '||chr(13)||
'			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = '''||cCodGrupoCia_in ||''' '||chr(13)||
'			 AND	  PROD_LOCAL.COD_LOCAL = '''||cCodLocal_in ||''' '||chr(13)||
'			 AND	  PRINC_ACT_PROD.COD_PRINC_ACT = '''||cCodFiltro_in||''' '||chr(13)||
'			 AND	  PROD.COD_PROD = PRINC_ACT_PROD.COD_PROD '||chr(13)||
'			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA '||chr(13)||
'			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD '||chr(13)||
'			 AND	  PROD.COD_LAB = LAB.COD_LAB '||chr(13)||
'			 AND	  PROD.COD_IGV = IGV.COD_IGV '||chr(13)||
'			 AND	  PROD.EST_PROD = ''A'' '||chr(13)||
'       AND    PR_VRT.EST_PROD_VIRTUAL(+) = ''A'' '||chr(13)||
'       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+) '||chr(13)||
'       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+) '||chr(13)||
'       AND    PROD.COD_PROD = Z.COD(+) '||chr(13)||
'       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+)  '||chr(13)||
'       AND   PROD.COD_PROD=V2.COD_PROD(+) '
       ;
	ELSIF(cTipoFiltro_in = 2) THEN --accion terapeutica
query:=
			 'FROM   LGT_PROD PROD, ' ||chr(13)||
				'   	  LGT_PROD_LOCAL PROD_LOCAL, ' ||chr(13)||
				'   	  LGT_LAB LAB, ' ||chr(13)||
				'   	  PBL_IGV IGV, ' ||chr(13)||
			'		    LGT_ACC_TERAP ACC_TER, ' ||chr(13)||
		'			    LGT_ACC_TERAP_PROD ACC_TERAP_PROD, ' ||chr(13)||
    '          LGT_PROD_VIRTUAL PR_VRT, ' ||chr(13)||
'              (SELECT DISTINCT(V1.COD_PROD) COD ' ||chr(13)||
'               FROM  (SELECT COD_PAQUETE,COD_PROD ' ||chr(13)||
'                      FROM   VTA_PROD_PAQUETE ' ||chr(13)||
'                      WHERE  COD_GRUPO_CIA = ''001''  ' ||chr(13)||
'                      ) V1, ' ||chr(13)||
'                      VTA_PROMOCION    P ' ||chr(13)||
'         WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE) ' ||chr(13)||
'                      AND  P.COD_GRUPO_CIA = 001 ' ||chr(13)||
'                      AND    P.ESTADO  = ''A'' ' ||chr(13)||
'                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE) ' ||chr(13)||
'                             OR ' ||chr(13)||
'                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE)) ' ||chr(13)||
'              ) Z, ' ||chr(13)||
'              (SELECT PROD_ENCARTE.COD_GRUPO_CIA, ' ||chr(13)||
'                      PROD_ENCARTE.COD_ENCARTE, ' ||chr(13)||
'                      PROD_ENCARTE.COD_PROD ' ||chr(13)||
'               FROM   VTA_PROD_ENCARTE PROD_ENCARTE, ' ||chr(13)||
'                      VTA_ENCARTE ENCARTE ' ||chr(13)||
'               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN ' ||chr(13)||
'               AND    ENCARTE.ESTADO = ''A'' ' ||chr(13)||
'               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA ' ||chr(13)||
'               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE ' ||chr(13)||
'               )   V2 ' ||chr(13)||
'			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'			 AND	  PROD_LOCAL.COD_LOCAL = '''||cCodLocal_in||''' ' ||chr(13)||
'			 AND	  ACC_TER.COD_ACC_TERAP = '''||cCodFiltro_in||''' ' ||chr(13)||
'			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA ' ||chr(13)||
'			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD ' ||chr(13)||
'			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP ' ||chr(13)||
'			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA ' ||chr(13)||
'			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD ' ||chr(13)||
'			 AND	  PROD.COD_LAB = LAB.COD_LAB ' ||chr(13)||
'			 AND	  PROD.COD_IGV = IGV.COD_IGV ' ||chr(13)||
'			 AND	  PROD.EST_PROD = ''A'' ' ||chr(13)||
'      AND    PR_VRT.EST_PROD_VIRTUAL(+) = ''A'' ' ||chr(13)||
'     AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+) ' ||chr(13)||
'       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+) ' ||chr(13)||
'       AND    PROD.COD_PROD = Z.COD(+) ' ||chr(13)||
'       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) ' ||chr(13)||
'       AND   PROD.COD_PROD=V2.COD_PROD(+) '
       ;
	ELSIF(cTipoFiltro_in = 3) THEN --laboratorio
	query:= 

'			 FROM   LGT_PROD PROD, ' ||chr(13)||
'				   	  LGT_PROD_LOCAL PROD_LOCAL, ' ||chr(13)||
'				   	  LGT_LAB LAB, ' ||chr(13)||
'				   	  PBL_IGV IGV, ' ||chr(13)||
'              LGT_PROD_VIRTUAL PR_VRT, ' ||chr(13)||
'              (SELECT DISTINCT(V1.COD_PROD) COD ' ||chr(13)||
'               FROM  (SELECT COD_PAQUETE,COD_PROD ' ||chr(13)||
'                      FROM   VTA_PROD_PAQUETE ' ||chr(13)||
'                      WHERE  COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'                      ) V1, ' ||chr(13)||
'                      VTA_PROMOCION    P ' ||chr(13)||
'                      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE) ' ||chr(13)||
'                     AND  P.COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'                     AND    P.ESTADO  = ''A'' ' ||chr(13)||
'                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE) ' ||chr(13)||
'                             OR ' ||chr(13)||
'                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE)) ' ||chr(13)||
'              ) Z, ' ||chr(13)||
'              (SELECT PROD_ENCARTE.COD_GRUPO_CIA, ' ||chr(13)||
'                      PROD_ENCARTE.COD_ENCARTE, ' ||chr(13)||
'                      PROD_ENCARTE.COD_PROD ' ||chr(13)||
'               FROM   VTA_PROD_ENCARTE PROD_ENCARTE, ' ||chr(13)||
'                      VTA_ENCARTE ENCARTE ' ||chr(13)||
'               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN ' ||chr(13)||
'               AND    ENCARTE.ESTADO = ''A'' ' ||chr(13)||
'               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA ' ||chr(13)||
'               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE ' ||chr(13)||
'               )   V2 ' ||chr(13)||
'			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'			 AND	  PROD_LOCAL.COD_LOCAL = '''||cCodLocal_in||''' ' ||chr(13)||
'			 AND	  LAB.COD_LAB = '''||cCodFiltro_in||''' ' ||chr(13)||
'			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA ' ||chr(13)||
'			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD ' ||chr(13)||
'			 AND	  PROD.COD_LAB = LAB.COD_LAB ' ||chr(13)||
'			 AND	  PROD.COD_IGV = IGV.COD_IGV ' ||chr(13)||
'			 AND	  PROD.EST_PROD = ''A'' ' ||chr(13)||
'      AND    PR_VRT.EST_PROD_VIRTUAL(+) = ''A'' ' ||chr(13)||
'       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+) ' ||chr(13)||
'       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+) ' ||chr(13)||
'       AND    PROD.COD_PROD = Z.COD(+) ' ||chr(13)||
'       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+)  ' ||chr(13)||
'       AND   PROD.COD_PROD=V2.COD_PROD(+) '
       ;
  ELSIF(trim(cTipoFiltro_in) = 4) THEN -- JCORTTEZ 17.04.08 productos de encarte
query:=
			 
'			 FROM   LGT_PROD PROD, ' ||chr(13)||
'				   	  LGT_PROD_LOCAL PROD_LOCAL, ' ||chr(13)||
'				   	  LGT_LAB LAB, ' ||chr(13)||
'				   	  PBL_IGV IGV, ' ||chr(13)||
'					    LGT_ACC_TERAP ACC_TER, ' ||chr(13)||
'					    LGT_ACC_TERAP_PROD ACC_TERAP_PROD, ' ||chr(13)||
'              LGT_PROD_VIRTUAL PR_VRT, ' ||chr(13)||
'              (SELECT DISTINCT(V1.COD_PROD) COD ' ||chr(13)||
'               FROM  (SELECT COD_PAQUETE,COD_PROD ' ||chr(13)||
'                      FROM   VTA_PROD_PAQUETE ' ||chr(13)||
'                      WHERE  COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'                      ) V1, ' ||chr(13)||
'                      VTA_PROMOCION    P ' ||chr(13)||
'                      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE) ' ||chr(13)||
'                      AND   P.COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'                      AND    P.ESTADO  = ''A'' ' ||chr(13)||
'                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE) ' ||chr(13)||
'                             OR ' ||chr(13)||
'                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))) Z, ' ||chr(13)||
'              (SELECT PROD_ENCARTE.COD_GRUPO_CIA, ' ||chr(13)||
'                      PROD_ENCARTE.COD_ENCARTE, ' ||chr(13)||
'                      PROD_ENCARTE.COD_PROD ' ||chr(13)||
'               FROM   VTA_PROD_ENCARTE PROD_ENCARTE, ' ||chr(13)||
'                      VTA_ENCARTE ENCARTE ' ||chr(13)||
'               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN ' ||chr(13)||
'               AND    ENCARTE.ESTADO = ''A'' ' ||chr(13)||
'               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA ' ||chr(13)||
'               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE ' ||chr(13)||
'               )   V2 ' ||chr(13)||
'			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'			 AND	  PROD_LOCAL.COD_LOCAL = '''||cCodLocal_in||''' ' ||chr(13)||
'			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA ' ||chr(13)||
'			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD ' ||chr(13)||
'			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP ' ||chr(13)||
'			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA ' ||chr(13)||
'			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD ' ||chr(13)||
'			 AND	  PROD.COD_LAB = LAB.COD_LAB ' ||chr(13)||
'			 AND	  PROD.COD_IGV = IGV.COD_IGV ' ||chr(13)||
'			 AND	  PROD.EST_PROD = ''A'' ' ||chr(13)||
'       AND    V2.COD_ENCARTE = ''00001''  ' ||chr(13)||
'       AND    PR_VRT.EST_PROD_VIRTUAL(+) = ''A'' ' ||chr(13)||
'       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+) ' ||chr(13)||
'       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+) ' ||chr(13)||
'       AND    PROD.COD_PROD = Z.COD(+) ' ||chr(13)||
'       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) ' ||chr(13)|| 
'       AND    PROD.COD_PROD=V2.COD_PROD ' 
       ;
	ELSIF(cTipoFiltro_in = 5) THEN -- JCORTTEZ 17.04.08 productos Cupon

			 query := 
'			 FROM   LGT_PROD PROD, ' ||chr(13)||
'				   	  LGT_PROD_LOCAL PROD_LOCAL, ' ||chr(13)||
'				   	  LGT_LAB LAB, ' ||chr(13)||
'				   	  PBL_IGV IGV, ' ||chr(13)||
'					    LGT_ACC_TERAP ACC_TER, ' ||chr(13)||
'					    LGT_ACC_TERAP_PROD ACC_TERAP_PROD, ' ||chr(13)||
'              LGT_PROD_VIRTUAL PR_VRT, ' ||chr(13)||
'              (SELECT DISTINCT(V1.COD_PROD) COD ' ||chr(13)||
'               FROM  (SELECT COD_PAQUETE,COD_PROD ' ||chr(13)||
'                      FROM   VTA_PROD_PAQUETE ' ||chr(13)||
'                      WHERE  COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'                      ) V1, ' ||chr(13)||
'                      VTA_PROMOCION    P ' ||chr(13)||
'             WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE) ' ||chr(13)||
'                      AND  P.COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'                      AND    P.ESTADO  = ''A'' ' ||chr(13)||
'                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE) ' ||chr(13)||
'                             OR ' ||chr(13)||
'                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE)) ' ||chr(13)||
'              ) Z, ' ||chr(13)||
'              (SELECT PROD_ENCARTE.COD_GRUPO_CIA, ' ||chr(13)||
'                      PROD_ENCARTE.COD_ENCARTE, ' ||chr(13)||
'                      PROD_ENCARTE.COD_PROD ' ||chr(13)||
'               FROM   VTA_PROD_ENCARTE PROD_ENCARTE, ' ||chr(13)||
'                      VTA_ENCARTE ENCARTE ' ||chr(13)||
'               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN ' ||chr(13)||
'               AND    ENCARTE.ESTADO = ''A'' ' ||chr(13)||
'               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA ' ||chr(13)||
'               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE ' ||chr(13)||
'            )   V2, ' ||chr(13)||
'              VTA_CAMPANA_PROD V3, ' ||chr(13)||
'               VTA_CAMPANA_CUPON V4  ' ||chr(13)||
'			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = '''||cCodGrupoCia_in||''' ' ||chr(13)||
'			 AND	  PROD_LOCAL.COD_LOCAL = '''||cCodLocal_in||''' ' ||chr(13)||

'			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA ' ||chr(13)||
'			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD ' ||chr(13)||
'			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP ' ||chr(13)||
'			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA ' ||chr(13)||
'			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD ' ||chr(13)||
'			 AND	  PROD.COD_LAB = LAB.COD_LAB ' ||chr(13)||
'			 AND	  PROD.COD_IGV = IGV.COD_IGV ' ||chr(13)||
'			 AND	  PROD.EST_PROD = ''A'' ' ||chr(13)||
'       AND    V4.ESTADO = ''A'' ' ||chr(13)||
'       AND    vFecha_actual between V4.FECH_INICIO AND  V4.FECH_FIN ' ||chr(13)||
'       and    v4.cod_grupo_cia = v3.cod_grupo_cia ' ||chr(13)||
'       and    v4.cod_camp_cupon = v3.cod_camp_cupon ' ||chr(13)||
'       AND    PR_VRT.EST_PROD_VIRTUAL(+) = ''A'' ' ||chr(13)||
'       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+) ' ||chr(13)||
'       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+) ' ||chr(13)||
'       AND    PROD.COD_PROD = Z.COD(+) ' ||chr(13)||
'       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+)  ' ||chr(13)||
'       AND    PROD.COD_PROD=V2.COD_PROD(+) ' ||chr(13)||
'       AND    PROD.COD_GRUPO_CIA=V3.COD_GRUPO_CIA(+) ' ||chr(13)||
'       AND    PROD.COD_PROD=V3.COD_PROD ' 
       ;
  	END IF;
    

      
    IF cTipoOrden = 'C' THEN -- COMERCIAL
      vOrden := 'order by NVL( DECODE(TRIM(PROD_LOCAL.IND_ZAN),''3G'',''40'',''GG'',''30'',''GP'',''20'',''G'',''10'','''')
                || TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,''999990.000''),'' '')
      desc';
    ELSIF cTipoOrden = 'A' THEN -- COMERCIAL
      vOrden := ' order by PROD.DESC_PROD ASC ';
    END IF;
    DBMS_OUTPUT.put_line(vSelect||CHR(13) ||query||CHR(13)||vOrden);
    DBMS_OUTPUT.put_line(length(vSelect||CHR(13) ||query||CHR(13)||vOrden));
    VPRUEBA := vSelect||' '|| query||' '||vOrden;
    DBMS_OUTPUT.put_line(length(VPRUEBA));
    OPEN curVta FOR VPRUEBA; 

  RETURN curVta;
  END;
  
  
end;
/
