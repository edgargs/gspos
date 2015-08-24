CREATE OR REPLACE PACKAGE PTOVENTA."TMP_REP_MHR" AS
 TYPE FarmaCursor IS REF CURSOR;

 --Descripcion: Obtiene el reporte de dias-mes
  --Fecha       Usuario		Comentario
  --29/03/2006  MHUAYTA     Creación
 FUNCTION REPORTE_VENTAS_DIA_MES(cCodGrupoCia_in IN CHAR,
        		   			     cCodLocal_in    IN CHAR,
 			   					 cFechaInicio 	 IN CHAR,
 		  						 cFechaFin 		 IN CHAR,
								 cCodFiltro		 IN CHAR
										   )
  RETURN FarmaCursor;
END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."TMP_REP_MHR" AS

 FUNCTION REPORTE_VENTAS_DIA_MES(cCodGrupoCia_in IN CHAR,
        		   				 cCodLocal_in    IN CHAR,
 			   					 cFechaInicio 	 IN CHAR,
 		  						 cFechaFin 		 IN CHAR,
								 cCodFiltro		 IN CHAR
							     )
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
    OPEN curRep FOR
		 			/*select   TO_CHAR(COUNT(distinct to_char(FEC_PED_VTA,'dd/MM/yyyy')))  								||' - '||
						   	 to_char(FEC_PED_VTA,'fmDay')								   								|| 'Ã' ||
	   					   	 TO_CHAR(COUNT(*),'999990')				 				   									|| 'Ã' ||
	   					   	 TO_CHAR(SUM(VAL_NETO_PED_VTA+VAL_REDONDEO_PED_VTA),'999,990.00')				   			|| 'Ã' ||
	   					   	 TO_CHAR(((SUM(VAL_NETO_PED_VTA+VAL_REDONDEO_PED_VTA))/COUNT(*)),'999,990.00')   			|| 'Ã' ||
	   					   	 TO_CHAR( SUM(CANT_ATENDIDA/DECODE(VAL_FRAC,0,1,VAL_FRAC)) /COUNT(*),'999,990.00') 			|| 'Ã' ||
	   					   	 TO_CHAR(SUM(VAL_NETO_PED_VTA+VAL_REDONDEO_PED_VTA) / DECODE( SUM(CANT_ATENDIDA/DECODE(VAL_FRAC,0,1,VAL_FRAC)) ,0,1,SUM(CANT_ATENDIDA/DECODE(VAL_FRAC,0,1,VAL_FRAC))),'999,990.00')		|| 'Ã' ||
	   					   	 to_char(FEC_PED_VTA,'D')
	 				from   	 VTA_PEDIDO_VTA_CAB VC,
						   	 VTA_PEDIDO_VTA_DET VD
	 				where  	 VC.COD_GRUPO_CIA  = cCodGrupoCia_in 				  									 AND
	  					   	 VC.COD_LOCAL      = cCodLocal_in	 													 AND
	  					   	 VC.FEC_PED_VTA between TO_DATE(cFechaInicio||' 00:00:00','dd/MM/yyyy HH24:mi:ss')     	 AND
	  			  		   	 TO_DATE(cFechaFin   ||' 23:59:59','dd/MM/yyyy HH24:mi:ss')	   						 	 AND
	  					   	 VC.COD_GRUPO_CIA  = VD.COD_GRUPO_CIA  												 	 AND
	  					   	 VC.COD_LOCAL      = VD.COD_LOCAL 	   												 	 AND
	  					     VC.NUM_PED_VTA    = VD.NUM_PED_VTA
	  			    GROUP BY to_char(FEC_PED_VTA,'fmDay'),
						  	 to_char(FEC_PED_VTA,'D') ;*/
              --24/10/2007 dubilluz modificado
              SELECT TO_CHAR(COUNT(distinct to_char(CA.FEC_PED_VTA,'dd/MM/yyyy')))		||' - '||
              			 TO_CHAR(CA.FEC_PED_VTA,'fmDay')	|| 'Ã' ||
                     --TO_CHAR(COUNT(*),'999990')       || 'Ã' ||
                     TO_CHAR(COUNT(DISTINCT ca.num_ped_vta),'999990')       || 'Ã' || --ASOSA, 09.09.2010
                     TO_CHAR(V.NETO,'999,990.00')  || 'Ã' ||
                     TO_CHAR((V.NETO)/COUNT(*),'999,990.00') || 'Ã' ||
              	   	 TO_CHAR( SUM(DE.CANT_ATENDIDA/DECODE(DE.VAL_FRAC,0,1,DE.VAL_FRAC)) /COUNT(*),'999,990.00') 			|| 'Ã' ||
              	   	 TO_CHAR(SUM(CA.VAL_NETO_PED_VTA) / DECODE( SUM(DE.CANT_ATENDIDA/DECODE(DE.VAL_FRAC,0,1,DE.VAL_FRAC)) ,0,1,SUM(DE.CANT_ATENDIDA/DECODE(DE.VAL_FRAC,0,1,DE.VAL_FRAC))),'999,990.00')		|| 'Ã' ||
              	   	 to_char(FEC_PED_VTA,'D')
              FROM  (SELECT  DISTINCT(TRUNC(C.FEC_PED_VTA)) FECHA ,SUM(C.VAL_NETO_PED_VTA ) NETO
                      FROM    VTA_PEDIDO_VTA_CAB C
                      WHERE   C.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND     C.COD_LOCAL     = cCodLocal_in
                      AND     C.EST_PED_VTA = 'C'
                      AND     C.FEC_PED_VTA   between TO_DATE(cFechaInicio||' 00:00:00','dd/MM/yyyy HH24:mi:ss') 	 AND
                                                	    TO_DATE(cFechaFin  ||' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     GROUP BY  TRUNC(C.FEC_PED_VTA)   )  V
                     ,
                      VTA_PEDIDO_VTA_CAB CA,
                      VTA_PEDIDO_VTA_DET DE
              WHERE   CA.COD_GRUPO_CIA = cCodGrupoCia_in
              AND     CA.COD_LOCAL     = cCodLocal_in
              AND     CA.EST_PED_VTA = 'C'
              AND     CA.FEC_PED_VTA   between TO_DATE( TO_CHAR(V.FECHA,'dd/mm/yyyy') ||' 00:00:00','dd/MM/yyyy HH24:mi:ss') 	 AND
                                        	     TO_DATE( TO_CHAR(V.FECHA,'dd/mm/yyyy')  ||' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND     CA.COD_GRUPO_CIA = DE.COD_GRUPO_CIA
              AND     CA.COD_LOCAL     = DE.COD_LOCAL
              AND     CA.NUM_PED_VTA   = DE.NUM_PED_VTA
              AND     CA.Num_Ped_Vta_Origen IS NULL    --ASOSA, 09.09.2010
              GROUP BY to_char(FEC_PED_VTA,'fmDay'), to_char(FEC_PED_VTA,'D') , V.NETO, TRUNC(FEC_PED_VTA);

	RETURN curRep;
  END;

END;
/

