--------------------------------------------------------
--  DDL for Package Body PTOVENTA_REPORTE
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_REPORTE" AS

 FUNCTION REPORTE_REGISTRO_VENTA (cCodGrupoCia_in	IN CHAR,
                                  cCodLocal_in	  	IN CHAR,
                                  cFechaInicio      IN CHAR,
                                  cFechaFin         IN CHAR)
 RETURN FarmaCursor
 IS
 curRep FarmaCursor;
 BEGIN
 	  OPEN curRep FOR
 	  	SELECT VPC.num_ped_vta || '�' ||
			 	     FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || '�' || --DECODE(CP.tip_comp_pago,01,' ',02,C_C_TIP_COMP_PAGO_FACTURA,03,C_C_TIP_COMP_PAGO_GUIA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO,05,C_C_TIP_COMP_PAGO_TICKET) || '�' ||
				     NVL(SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7),' ') || '�' ||
				     --TO_CHAR(VPC.FEC_CREA_PED_VTA_CAB,'dd/MM/yyyy HH24:MI:SS') || '�' ||
             TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || '�' ||
				     NVL(VPC.NOM_CLI_PED_VTA,' ') || '�' ||
				     DECODE(VPC.IND_PEDIDO_ANUL,C_C_INDICADOR_SI,'ANUL.ORIG.',' ') || '�' ||
				     TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,990.00')|| '�' ||
	--			  NVL(VPC.DIR_CLI_PED_VTA,' ') 			   		   	|| '�' ||
				     NVL(VPC.RUC_CLI_PED_VTA,' ') || '�' ||
				     TO_CHAR(VPC.FEC_PED_VTA,'HH24:MI:SS') || '�' ||
				     NVL(VPC.USU_CREA_PED_VTA_CAB,' ') || '�' ||
				     DECODE(VPC.IND_PEDIDO_ANUL,C_C_INDICADOR_NO,'COBRADO','ANULADO') || '�' ||
				     TO_CHAR(VPC.FEC_PED_VTA,'yyyy/MM/dd')|| '�' ||
			 	     FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || --DECODE(CP.tip_comp_pago,01,' ',02,C_C_TIP_COMP_PAGO_FACTURA,03,C_C_TIP_COMP_PAGO_GUIA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO,05,C_C_TIP_COMP_PAGO_TICKET) ||
				     NVL(SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7),' ')
			FROM VTA_PEDIDO_VTA_CAB VPC
      INNER JOIN VTA_COMP_PAGO CP on VPC.cod_grupo_cia = cp.cod_grupo_cia AND
                                     VPC.COD_LOCAL = cp.cod_local AND
                                     VPC.NUM_PED_VTA=CP.NUM_PED_VTA
        --INNER JOIN VTA_TIP_COMP VTC on VPC.TIP_COMP_PAGO = VTC.TIP_COMP
       INNER JOIN VTA_TIP_COMP VTC on CP.TIP_COMP_PAGO = VTC.TIP_COMP --Cesar Huanes
			WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND   VPC.COD_LOCAL = cCodLocal_in
			AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			AND   VPC.EST_PED_VTA = C_C_EST_PED_VTA_COBRADO
			UNION
			SELECT VPC.num_ped_vta || '�' ||
			 	     FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || '�' || --DECODE(CP.tip_comp_pago,01,' ',02,C_C_TIP_COMP_PAGO_FACTURA,03,C_C_TIP_COMP_PAGO_GUIA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO,05,C_C_TIP_COMP_PAGO_TICKET) || '�' ||
			 	     NVL(SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7),' ') || '�' ||
			 	     --TO_CHAR(VPC.FEC_CREA_PED_VTA_CAB,'dd/MM/yyyy HH24:MI:SS') || '�' ||
			 	     TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || '�' ||
             NVL(VPC.NOM_CLI_PED_VTA,' ') || '�' ||
			 	     DECODE(VPC.IND_PEDIDO_ANUL,C_C_INDICADOR_NO,'ANULADO',' ')	|| '�' ||
             -- 31/10/2007 JOLIVA
             -- Se estaba mostrando el monto de la cabecera pero debe mostrarse el monto del comprobante anulado
				     TO_CHAR((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO) * -1,'999,990.00')|| '�' ||
             --TO_CHAR(VPC.VAL_NETO_PED_VTA /*+ VPC.VAL_REDONDEO_PED_VTA*/,'999,990.00') || '�' ||
			 	     -- NVL(VPC.DIR_CLI_PED_VTA,' ') || '�' ||
			 	     NVL(VPC.RUC_CLI_PED_VTA,' ') || '�' ||
			 	     TO_CHAR(VPC.FEC_PED_VTA,'HH24:MI:SS') || '�' ||
			 	     NVL(VPC.USU_CREA_PED_VTA_CAB,' ') || '�' ||
			 	     DECODE(VPC.IND_PEDIDO_ANUL,C_C_INDICADOR_NO,'COBRADO','ANULADO') || '�' ||
			 	     TO_CHAR(VPC.FEC_PED_VTA,'yyyy/MM/dd')|| '�' ||
			 	     FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP)|| --DECODE(CP.tip_comp_pago,01,' ',02,C_C_TIP_COMP_PAGO_FACTURA,03,C_C_TIP_COMP_PAGO_GUIA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO,05,C_C_TIP_COMP_PAGO_TICKET) ||
				     NVL(SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7),' ')
			FROM VTA_PEDIDO_VTA_CAB VPC
      INNER JOIN VTA_COMP_PAGO CP on VPC.cod_grupo_cia = cp.cod_grupo_cia AND
                                     VPC.COD_LOCAL = cp.cod_local AND
                                     VPC.NUM_PED_VTA=CP.NUM_PEDIDO_ANUL
      --INNER JOIN VTA_TIP_COMP VTC on VPC.TIP_COMP_PAGO = VTC.TIP_COMP
       INNER JOIN VTA_TIP_COMP VTC on CP.TIP_COMP_PAGO = VTC.TIP_COMP --Cesar Huanes
			WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND   VPC.COD_LOCAL = cCodLocal_in
			AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			AND	  VPC.EST_PED_VTA= C_C_EST_PED_VTA_COBRADO;
 RETURN curRep;
 END;

  FUNCTION REPORTE_DETALLE_REGISTRO_VENTA ( cCodGrupoCia_in	IN CHAR,
  		   						  		 cCodLocal_in	  	IN CHAR,
								  		 cCodNumeroPed 	IN CHAR)
 RETURN FarmaCursor
 IS
 curRep FarmaCursor;
 --jcallo 14.10.2008
 v_promocion  CHAR(9):='';
 BEGIN

    SELECT LLAVE_TAB_GRAL INTO v_promocion
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL='188'
    AND COD_APL='PTO_VENTA';

 	  OPEN curRep FOR
	  	 /*SELECT VPD.COD_PROD	|| '�' ||
			   	    VPD.CANT_ATENDIDA	|| '�' ||
			   	    NVL(P.DESC_PROD,' ')|| '�' ||
			   	    TO_CHAR(VPD.VAL_PREC_VTA,'99,990.00')	  	|| '�' ||
			   	    TO_CHAR(VPD.PORC_DCTO_TOTAL,'999,990.00')	|| '�' ||
			   	    TO_CHAR(VPD.VAL_PREC_LISTA,'999,990.00')		|| '�' ||
			   	    TO_CHAR(VPD.VAL_PREC_TOTAL,'999,990.00') 	|| '�' ||
			   	    TO_CHAR(vpc.VAL_BRUTO_PED_VTA,'999,990.00')	|| '�' ||
			   	    TO_CHAR(vpc.VAL_DCTO_PED_VTA,'999,990.00')	|| '�' ||
			   	    TO_CHAR(vpc.VAL_IGV_PED_VTA,'999,990.00')	|| '�' ||
			   	    TO_CHAR(vpc.VAL_NETO_PED_VTA,'999,990.00')|| '�' ||
              vpd.usu_crea_ped_vta_det
		   FROM
		   		    VTA_PEDIDO_VTA_DET VPD,
		   		    LGT_PROD P,
				      LGT_PROD_LOCAL PL,
		   		    VTA_PEDIDO_VTA_CAB vpc
		   WHERE  vpd.COD_GRUPO_CIA =cCodGrupoCia_in
		   AND	  vpd.cod_local =cCodLocal_in
		   AND	  VPD.NUM_PED_VTA=cCodNumeroPed
		   AND	  P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
		   AND	  P.COD_PROD = PL.COD_PROD
		   AND	  vpd.COD_GRUPO_CIA = pl.cod_grupo_cia
		   AND	  vpd.cod_local = pl.cod_local
		   AND	  VPD.COD_PROD=PL.COD_PROD
		   AND	  VPC.COD_GRUPO_CIA = VPD.COD_GRUPO_CIA
		   AND	  VPC.COD_LOCAL = VPD.COD_LOCAL
		   AND	  VPC.NUM_PED_VTA = VPD.NUM_PED_VTA;*/

       SELECT  VTA_DET.COD_PROD                                      || '�' ||
               VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC)|| '�' ||
    --CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0 THEN  SUBSTR('BONIF.',1,9) ||' '|| PROD.DESC_PROD ELSE  PROD.DESC_PROD END || '�' ||
    	         PROD.DESC_PROD             || '�' ||
               VTA_DET.UNID_VTA           || '�' ||
      		     LAB.NOM_LAB                                           || '�' ||
    			     TRIM(TO_CHAR(VTA_DET.VAL_PREC_VTA,'999,990.000'))           || '�' ||
               --VTA_DET.UNID_VTA                                      || '�' ||
               DECODE(VTA_DET.AHORRO,null,' ',0,' ', TO_CHAR(VTA_DET.AHORRO, '999,990.000' ))  || '�' ||
    			     TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')          || '�' ||

               TO_CHAR(VTA_CAB.VAL_BRUTO_PED_VTA,'999,990.00')	|| '�' ||
			   	     TO_CHAR(VTA_CAB.VAL_DCTO_PED_VTA,'999,990.00')	|| '�' ||
			   	     TO_CHAR(VTA_CAB.VAL_IGV_PED_VTA,'999,990.00')	|| '�' ||
			   	     TO_CHAR(VTA_CAB.VAL_NETO_PED_VTA,'999,990.00')|| '�' ||
               VTA_DET.usu_crea_ped_vta_det|| '�'||' '


        FROM   VTA_PEDIDO_VTA_CAB VTA_CAB,
               VTA_PEDIDO_VTA_DET VTA_DET,
    			     LGT_PROD_LOCAL PROD_LOCAL,
    			     LGT_PROD PROD,
    			     LGT_LAB LAB
    		WHERE  VTA_DET.COD_GRUPO_CIA    = cCodGrupoCia_in
    		AND	   VTA_DET.COD_LOCAL        = cCodLocal_in
    		AND	   VTA_DET.NUM_PED_VTA      = cCodNumeroPed
    		AND	   VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
    		AND	   VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
    		AND	   VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
    		AND	   PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
    		AND	   PROD_LOCAL.COD_PROD      = PROD.COD_PROD
    		AND	   PROD.COD_LAB 			      = LAB.COD_LAB

        AND	  VTA_CAB.COD_GRUPO_CIA = VTA_DET.COD_GRUPO_CIA
		    AND	  VTA_CAB.COD_LOCAL = VTA_DET.COD_LOCAL
		    AND	  VTA_CAB.NUM_PED_VTA = VTA_DET.NUM_PED_VTA

    		ORDER BY VTA_DET.SEC_PED_VTA_DET;

	   RETURN curRep;
 END;


 FUNCTION REPORTE_COMPROBANTES_VENTA( cCodGrupoCia_in	IN CHAR,
  		   						  	  cCodLocal_in	  	IN CHAR,
									  cCodNumeroPed 	IN CHAR)
 RETURN FarmaCursor
 IS
 curRep FarmaCursor;
 BEGIN
 	  OPEN curRep FOR
      SELECT VTC.DESC_COMP || '�' || --DECODE(CP.tip_comp_pago,01,'BOLETA',02,'FACTURA',05,'TICKET','03','GUIA') || '�' ||
           NVL(CP.NUM_COMP_PAGO,' ') || '�' ||
           --TO_CHAR(VPC.FEC_CREA_PED_VTA_CAB,'DD/MM/YYYY') || '�' ||
           TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || '�' ||
           --TO_CHAR(CP.VAL_BRUTO_COMP_PAGO,'999,990.00') || '�' ||
           TO_CHAR(CP.CANT_ITEM) || '�' ||

           TO_CHAR(CP.VAL_AFECTO_COMP_PAGO,'999,990.00') || '�' ||
           TO_CHAR(CP.VAL_IGV_COMP_PAGO,'999,990.00') || '�' ||           
           TO_CHAR(CP.VAL_REDONDEO_COMP_PAGO,'999,990.00') || '�' ||
           TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,990.00')  || '�' ||
           
           TO_CHAR(CP.VAL_DCTO_COMP_PAGO,'999,990.00') || '�' ||
           NVL(CP.NUM_DOC_IMPR,' ')||'�'||
           CP.TIP_COMP_PAGO--RHERRERA: OBTENER TIPO DE COMPROBANTE
      FROM VTA_COMP_PAGO CP
      INNER JOIN VTA_PEDIDO_VTA_CAB VPC on vpc.COD_GRUPO_CIA = cp.COD_GRUPO_CIA AND
                                           vpc.COD_LOCAL = cp.COD_LOCAL AND
                                           VPC.NUM_PED_VTA=CP.NUM_PED_VTA
      INNER JOIN VTA_TIP_COMP VTC on CP.TIP_COMP_PAGO = VTC.TIP_COMP
			WHERE  vpc.COD_GRUPO_CIA = cCodGrupoCia_in
			AND    vpc.cod_local = cCodLocal_in
			AND	   VPC.NUM_PED_VTA= cCodNumeroPed;

	   RETURN curRep;
  END;

  FUNCTION REPORTE_DETALLE_COMPROBANTE(cCodGrupoCia_in	IN CHAR,
  		   						  	  cCodLocal_in	  	IN CHAR,
									  cNumCompPago IN CHAR,
                    cNumPedVta IN CHAR DEFAULT NULL,
                    cTipComPago IN VARCHAR2)--RHERRERA

  RETURN FarmaCursor
  IS
  curRep FarmaCursor;
  BEGIN
 	  OPEN curRep FOR
	  	    SELECT VPD.COD_PROD	|| '�' ||
				   NVL(P.DESC_PROD,' ') || '�' ||
				   VPD.UNID_VTA	|| '�' ||
				   TO_CHAR(VPD.VAL_PREC_LISTA,'999,990.00')	|| '�' ||
				   TO_CHAR(VPD.PORC_DCTO_TOTAL,'999,990.00') || '�' ||
				   TO_CHAR(VPD.VAL_PREC_VTA,'999,990.00') || '�' ||
				   VPD.CANT_ATENDIDA || '�' ||
				   TO_CHAR(VPD.VAL_PREC_TOTAL,'999,990.00')	|| '�' ||
--				   TO_CHAR(VPD.VAL_TOTAL_BONO,'999,990.00')
           NVL(VPD.IND_ZAN,'-')
			FROM
				   VTA_PEDIDO_VTA_DET VPD,
				   LGT_PROD P,
				   LGT_PROD_LOCAL PL,
				   VTA_COMP_PAGO CP
			WHERE  cp.COD_GRUPO_CIA = cCodGrupoCia_in
			AND	   cp.cod_local = cCodLocal_in
		--	AND	   CP.NUM_COMP_PAGO=cNumCompPago
      /* RHERRERA: DETALLA LOS ITEMS DE CADA COMPROBANTE, SI ES CONVENIO O MEZON*/
     AND    cp.sec_comp_pago =(SELECT vcpp.sec_comp_pago FROM vta_comp_pago VCPP
                                WHERE vcpp.num_comp_pago=cNumCompPago
                                AND vcpp.NUM_PED_VTA = cNumPedVta
                                AND vcpp.TIP_COMP_PAGO = cTipComPago)--RHERRERA
     --------------------------------------------------------------
      AND    CP.NUM_PED_VTA=NVL(cNumPedVta,CP.NUM_PED_VTA)
			AND	   P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
		    AND	   P.COD_PROD = PL.COD_PROD
			AND	   vpd.COD_GRUPO_CIA = pl.COD_GRUPO_CIA
			AND    vpd.cod_local = pl.cod_local
			AND	   VPD.COD_PROD=PL.COD_PROD
			AND	   vpd.cod_grupo_cia = cp.COD_GRUPO_CIA
			AND	   vpd.cod_local = cp.cod_local
			AND	   VPD.NUM_PED_VTA=CP.NUM_PED_VTA
      --		AND	   vpd.SEC_COMP_PAGO=cp.SEC_COMP_PAGO;
      --RHERRERA
      AND	   DECODE(cp.tip_clien_convenio,
              1,vpd.sec_comp_pago_benef,
              2,vpd.sec_comp_pago_empre,
              vpd.sec_comp_pago)=CP.SEC_COMP_PAGO ;

	   RETURN curRep;
  END;

  FUNCTION REPORTE_RESUMEN_VENTA (cCodGrupoCia_in	IN CHAR,
  		   						  cCodLocal_in	  	IN CHAR,
 		  						  cFechaInicio 		IN CHAR,
 		  						  cFechaFin 		IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
    OPEN curRep FOR
		SELECT C_C_INDICADOR_NO || '�' ||
           VP.TIP_COMP_PAGO || '�' ||
	         COUNT(VP.SEC_COMP_PAGO) || '�' ||
	         TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),'999,990.00'))
	  FROM   VTA_COMP_PAGO VP ,
			     VTA_PEDIDO_VTA_CAB VPC
	  WHERE  VP.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   VP.COD_LOCAL  = cCodLocal_in
		AND	   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		AND 	 TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		AND	   vpc.EST_PED_VTA    = C_C_EST_PED_VTA_COBRADO
		AND	   vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
		AND	   vpc.cod_local = vp.cod_local
		AND	   VP.NUM_PED_VTA = VPC.NUM_PED_VTA
		GROUP BY C_C_INDICADOR_NO,VP.TIP_COMP_PAGO
		UNION
		SELECT C_C_INDICADOR_SI || '�' ||
           VP.TIP_COMP_PAGO || '�' ||
	         COUNT(VP.SEC_COMP_PAGO)|| '�' ||
	         TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VAL_REDONDEO_COMP_PAGO),'999,990.00'))
	  FROM   VTA_COMP_PAGO VP ,
			     VTA_PEDIDO_VTA_CAB VPC
	  WHERE  VP.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   VP.COD_LOCAL  = cCodLocal_in
		AND	   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		AND	   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		AND	   vpc.EST_PED_VTA    = C_C_EST_PED_VTA_COBRADO
		AND	   VP.IND_COMP_ANUL  = C_C_INDICADOR_SI
		AND	   vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
		AND	   vpc.cod_local = vp.cod_local
		AND	   VPC.NUM_PED_VTA = VP.NUM_PEDIDO_ANUL
		GROUP BY C_C_INDICADOR_SI,VP.TIP_COMP_PAGO
		UNION
		SELECT C_C_INDICADOR_NO || '�' ||
           'D' || '�' ||
	         COUNT(VP.SEC_COMP_PAGO)|| '�' ||
	         TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),'999,990.00'))
	  FROM   VTA_COMP_PAGO VP ,
			     VTA_PEDIDO_VTA_CAB VPC
	  WHERE  VP.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   VP.COD_LOCAL  = cCodLocal_in
		AND	   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		AND	   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		AND	   vpc.EST_PED_VTA    = C_C_EST_PED_VTA_COBRADO
		AND	   vpc.tip_ped_vta = C_C_TIP_PED_VTA_DELIVERY
		AND	   vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
		AND	   vpc.cod_local = vp.cod_local
		AND	   VP.NUM_PED_VTA = VPC.NUM_PED_VTA
		GROUP BY C_C_INDICADOR_NO,'D'
		UNION
		SELECT C_C_INDICADOR_SI|| '�' ||
           'D' || '�' ||
	         COUNT(VP.SEC_COMP_PAGO)|| '�' ||
	         TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VAL_REDONDEO_COMP_PAGO),'999,990.00'))
	  FROM   VTA_COMP_PAGO VP ,
			     VTA_PEDIDO_VTA_CAB VPC
	  WHERE  VP.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   VP.COD_LOCAL  = cCodLocal_in
		AND	   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		AND	   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		AND	   vpc.tip_ped_vta = C_C_TIP_PED_VTA_DELIVERY
		AND	   vpc.EST_PED_VTA    = C_C_EST_PED_VTA_COBRADO
		AND	   VP.IND_COMP_ANUL  = C_C_INDICADOR_SI
		AND	   vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
		AND	   vpc.cod_local = vp.cod_local
		AND    VPC.NUM_PED_VTA = VP.NUM_PEDIDO_ANUL
		GROUP BY C_C_INDICADOR_SI,'D'
    UNION
    SELECT C_C_INDICADOR_NO || '�' ||
           'I' || '�' ||
	         COUNT(VP.SEC_COMP_PAGO)|| '�' ||
	         TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),'999,990.00'))
	  FROM   VTA_COMP_PAGO VP ,
			     VTA_PEDIDO_VTA_CAB VPC
	  WHERE  VP.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   VP.COD_LOCAL  = cCodLocal_in
		AND	   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		AND	   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		AND	   vpc.EST_PED_VTA    = C_C_EST_PED_VTA_COBRADO
		AND	   vpc.tip_ped_vta = C_C_TIP_PED_VTA_INSTITUCIONAL
		AND	   vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
		AND	   vpc.cod_local = vp.cod_local
		AND	   VP.NUM_PED_VTA = VPC.NUM_PED_VTA
		GROUP BY C_C_INDICADOR_NO,'I'
		UNION
		SELECT C_C_INDICADOR_SI|| '�' ||
           'I' || '�' ||
	         COUNT(VP.SEC_COMP_PAGO)|| '�' ||
	         TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VAL_REDONDEO_COMP_PAGO),'999,990.00'))
	  FROM   VTA_COMP_PAGO VP ,
			     VTA_PEDIDO_VTA_CAB VPC
	  WHERE  VP.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   VP.COD_LOCAL  = cCodLocal_in
		AND	   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		AND	   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		AND	   vpc.tip_ped_vta = C_C_TIP_PED_VTA_INSTITUCIONAL
		AND	   vpc.EST_PED_VTA    = C_C_EST_PED_VTA_COBRADO
		AND	   VP.IND_COMP_ANUL  = C_C_INDICADOR_SI
		AND	   vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
		AND	   vpc.cod_local = vp.cod_local
		AND    VPC.NUM_PED_VTA = VP.NUM_PEDIDO_ANUL
		GROUP BY C_C_INDICADOR_SI,'I';
   RETURN curRep;
  END;

  FUNCTION REPORTE_FORMAS_DE_PAGO(cCodGrupoCia_in	IN CHAR,
  		   						  cCodLocal_in	  	IN CHAR,
 		  						  cFechaInicio 		IN CHAR,
 		  						  cFechaFin 		IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
    OPEN curRep FOR
		 SELECT FPP.COD_FORMA_PAGO			|| '�' ||
			   	FP.DESC_CORTA_FORMA_PAGO		|| '�' ||
			   	TO_CHAR(SUM(FPP.IM_TOTAL_PAGO - FPP.VAL_VUELTO),'999,990.00')
		 FROM
				VTA_FORMA_PAGO_PEDIDO FPP,
				VTA_FORMA_PAGO FP,
				VTA_FORMA_PAGO_LOCAL FPL,
				VTA_PEDIDO_VTA_CAB CAB
		 WHERE CAB.COD_GRUPO_CIA  = cCodGrupoCia_in
		 AND   CAB.COD_LOCAL      = cCodLocal_in
		 AND   CAB.FEC_PED_VTA BETWEEN TO_DATE( cFechaInicio ||' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		 AND   				   		   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		 AND   CAB.EST_PED_VTA    =  C_C_EST_PED_VTA_COBRADO
		 --AND   --CAB.IND_PEDIDO_ANUL    = 'N'
		 AND   FP.COD_GRUPO_CIA   = FPL.COD_GRUPO_CIA
		 AND   FP.COD_FORMA_PAGO  = FPL.COD_FORMA_PAGO
		 AND   fpl.cod_grupo_cia  = fpp.cod_grupo_cia
		 AND   fpl.cod_local      = fpp.COD_LOCAL
		 AND   FPL.COD_FORMA_PAGO = FPP.COD_FORMA_PAGO
		 AND   CAB.COD_GRUPO_CIA  = FPP.COD_GRUPO_CIA
		 AND   CAB.COD_LOCAL      = FPP.COD_LOCAL
		 AND   CAB.NUM_PED_VTA    = FPP.NUM_PED_VTA
		GROUP BY  FPP.COD_FORMA_PAGO,DESC_CORTA_FORMA_PAGO,TIP_MONEDA;

	RETURN curRep;
  END;

  FUNCTION REPORTE_DETALLE_VENTAS (cCodGrupoCia_in	IN CHAR,
    		   						   cCodLocal_in	  	IN CHAR,
 			   						   cFechaInicio 	IN CHAR,
 		  						       cFechaFin 		IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
    OPEN curRep FOR
		 SELECT VPD.NUM_PED_VTA || '�' ||
		 		    FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || '�' || --DECODE(CP.tip_comp_pago,01,C_C_TIP_COMP_PAGO_BOLETA,02,C_C_TIP_COMP_PAGO_FACTURA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO,05,C_C_TIP_COMP_PAGO_TICKET,'03',C_C_TIP_COMP_PAGO_GUIA) || '�' ||--JCHAVEZ 14.07.2009 se cambio 'TICKET' por C_C_TIP_COMP_PAGO_TICKET
				    NVL(SUBSTR(CP.NUM_COMP_PAGO,1,3) ||'-'|| SUBSTR(CP.NUM_COMP_PAGO,-7),' ') || '�' ||
				    --TO_CHAR(VPC.FEC_CREA_PED_VTA_CAB,'dd/MM/yyyy') || '�' ||
            TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || '�' ||
				    NVL(P.DESC_PROD,' ') || '�' ||
				    VPD.UNID_VTA || '�' ||
				    NVL(TO_CHAR(VPD.CANT_ATENDIDA,'999,999'),' ')  || '�' ||
				    TO_CHAR(VPD.VAL_PREC_TOTAL,'999,990.00') || '�' ||
				    --TO_CHAR(FEC_CREA_PED_VTA_CAB,'yyyy/MM/dd')
            TO_CHAR(FEC_PED_VTA,'yyyy/MM/dd')
		 FROM VTA_PEDIDO_VTA_DET VPD
     INNER JOIN VTA_COMP_PAGO CP on VPD.COD_GRUPO_CIA = CP.COD_GRUPO_CIA AND
                                    VPD.COD_LOCAL = CP.COD_LOCAL AND
                                    VPD.NUM_PED_VTA = CP.NUM_PED_VTA AND
                                    VPD.SEC_COMP_PAGO = CP.SEC_COMP_PAGO
     INNER JOIN LGT_PROD_LOCAL PL on PL.COD_GRUPO_CIA = VPD.COD_GRUPO_CIA AND
                                     PL.COD_LOCAL = VPD.COD_LOCAL AND
                                     PL.COD_PROD = VPD.COD_PROD
     INNER JOIN LGT_PROD P on P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                              P.COD_PROD = PL.COD_PROD
     INNER JOIN VTA_PEDIDO_VTA_CAB VPC on VPC.COD_GRUPO_CIA = VPD.COD_GRUPO_CIA AND
                                       VPC.COD_LOCAL= VPD.COD_LOCAL AND
                                       VPC.NUM_PED_VTA = VPD.NUM_PED_VTA AND
                                       VPC.NUM_PED_VTA = CP.NUM_PED_VTA
     INNER JOIN VTA_TIP_COMP VTC on VPC.TIP_COMP_PAGO = VTC.TIP_COMP
		 WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
		 AND	 VPC.COD_LOCAL = cCodLocal_in
		 AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio ||' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		 AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		 AND   VPC.EST_PED_VTA = C_C_EST_PED_VTA_COBRADO

		 UNION

		 SELECT VPD.NUM_PED_VTA || '�' ||
		 		    FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || '�' || --DECODE(CP.tip_comp_pago,01,C_C_TIP_COMP_PAGO_BOLETA,02,C_C_TIP_COMP_PAGO_FACTURA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO,05,C_C_TIP_COMP_PAGO_TICKET,'03',C_C_TIP_COMP_PAGO_GUIA)    || '�' ||
            NVL(CP.NUM_COMP_PAGO,' ') || '�' ||
				    --TO_CHAR(VPC.FEC_CREA_PED_VTA_CAB,'dd/MM/yyyy')	   	|| '�' ||
            TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy')	|| '�' ||
				    NVL(P.DESC_PROD,' ') || '�' ||
				    VPD.UNID_VTA || '�' ||
				    VPD.CANT_ATENDIDA	|| '�' ||
				    TO_CHAR(VPD.VAL_PREC_TOTAL,'999,990.00') || '�' ||
		 		    --TO_CHAR(FEC_CREA_PED_VTA_CAB,'yyyy/MM/dd')
            TO_CHAR(FEC_PED_VTA,'yyyy/MM/dd')
		 FROM	VTA_PEDIDO_VTA_DET VPD
     INNER JOIN VTA_COMP_PAGO CP ON VPD.COD_GRUPO_CIA = CP.COD_GRUPO_CIA AND
                                    VPD.COD_LOCAL = CP.COD_LOCAL
     INNER JOIN LGT_PROD_LOCAL PL ON PL.COD_GRUPO_CIA = VPD.COD_GRUPO_CIA AND
                                     PL.COD_LOCAL = VPD.COD_LOCAL AND
                                     PL.COD_PROD = VPD.COD_PROD
     INNER JOIN LGT_PROD P ON	P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                              P.COD_PROD = PL.COD_PROD
     INNER JOIN VTA_PEDIDO_VTA_CAB VPC ON VPD.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA AND
                                          VPD.COD_LOCAL= VPC.COD_LOCAL AND
                                          VPD.NUM_PED_VTA= VPC.NUM_PED_VTA AND
                                          VPC.NUM_PED_VTA = CP.NUM_PEDIDO_ANUL
     INNER JOIN VTA_TIP_COMP VTC on VPC.TIP_COMP_PAGO = VTC.TIP_COMP
     WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
     AND	VPC.COD_LOCAL = cCodLocal_in
     AND	VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio ||' 00:00:00','dd/MM/yyyy HH24:MI:SS')
     AND	TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
     AND	VPC.EST_PED_VTA= C_C_EST_PED_VTA_COBRADO;

		 RETURN curRep;
  END;

  FUNCTION REPORTE_RESUMEN_PRODUCTOS_VEND (cCodGrupoCia_in	IN CHAR,
  		       		   					   cCodLocal_in	  	IN CHAR,
 					   					   cFechaInicio 	IN CHAR,
 		  						       	   cFechaFin 		IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
    OPEN curRep FOR
		 	SELECT PL.COD_PROD				   					   || '�' ||
				   NVL(P.DESC_PROD,' ')						   || '�' ||
				   VPD.UNID_VTA		  							   || '�' ||
				   TO_CHAR(SUM((VPD.CANT_ATENDIDA / VPD.VAL_FRAC)),'999,990.00')			   || '�' ||
				   TO_CHAR(SUM(VPD.VAL_PREC_TOTAL),'999,990.00')
		    FROM   VTA_PEDIDO_VTA_DET VPD,
		    	   LGT_PROD P,
				   LGT_PROD_LOCAL PL,
		   		   VTA_PEDIDO_VTA_CAB VPC
		    WHERE  VPC.COD_LOCAL= cCodLocal_in
			AND	   VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND	   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio ||' 00:00:00','DD/MM/YYYY HH24:MI:SS')
			AND	   TO_DATE(cFechaFin ||' 23:59:59','DD/MM/YYYY HH24:MI:SS')
			AND	   P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
		    AND	   P.COD_PROD = PL.COD_PROD
			AND	   VPC.EST_PED_VTA=C_C_EST_PED_VTA_COBRADO
			AND	   VPC.COD_GRUPO_CIA=VPD.COD_GRUPO_CIA
			AND	   VPC.COD_LOCAL=VPD.COD_LOCAL
			AND	   vpc.NUM_PED_VTA = vpd.NUM_PED_VTA
			AND	   VPD.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
			AND	   VPD.COD_LOCAL=PL.COD_LOCAL
			AND	   VPD.COD_PROD = PL.COD_PROD
			GROUP BY PL.COD_PROD,
	  	 	NVL(P.DESC_PROD,' '),
	     	VPD.UNID_VTA;
			RETURN CURREP;
  END;

  FUNCTION REPORTE_FILTRO_PRODUCTOS_VEND(cCodGrupoCia_in	IN CHAR,
    		   						   	 cCodLocal_in	  	IN CHAR,
 			   						     cFechaInicio 	IN CHAR,
 		  						         cFechaFin 		IN CHAR,
									     cTipoFiltro      IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
  		  OPEN curRep FOR
			  SELECT VPD.NUM_PED_VTA	|| '�' ||
          FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || '�' ||
			 		--DECODE(CP.tip_comp_pago,01,C_C_TIP_COMP_PAGO_BOLETA,02,C_C_TIP_COMP_PAGO_FACTURA,05,C_C_TIP_COMP_PAGO_TICKET,'03',C_C_TIP_COMP_PAGO_GUIA)  || '�' ||
					NVL(SUBSTR(CP.NUM_COMP_PAGO,1,3) ||'-'|| SUBSTR(CP.NUM_COMP_PAGO,-7),' ') || '�' ||
					--TO_CHAR(VPC.FEC_CREA_PED_VTA_CAB,'dd/MM/yyyy HH24:MI:SS') || '�' ||
					TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || '�' ||
					NVL(P.DESC_PROD,' ') || '�' ||
					VPD.UNID_VTA	|| '�' ||
					NVL(TO_CHAR(VPD.CANT_ATENDIDA,'999,999'),' ')  || '�' ||
					TO_CHAR(VPD.VAL_PREC_TOTAL,'999,990.00') || '�' ||
					--TO_CHAR(FEC_CREA_PED_VTA_CAB,'yyyy/MM/dd')
          TO_CHAR(FEC_PED_VTA,'yyyy/MM/dd')
			  FROM VTA_PEDIDO_VTA_DET VPD
        INNER JOIN VTA_COMP_PAGO CP on VPD.COD_GRUPO_CIA = CP.COD_GRUPO_CIA AND
                                       VPD.COD_LOCAL = CP.COD_LOCAL AND
                                       VPD.NUM_PED_VTA = CP.NUM_PED_VTA AND
                                       VPD.SEC_COMP_PAGO = CP.SEC_COMP_PAGO
        INNER JOIN LGT_PROD_LOCAL PL on PL.COD_GRUPO_CIA = VPD.COD_GRUPO_CIA AND
                                        PL.COD_LOCAL = VPD.COD_LOCAL AND
                                       PL.COD_PROD = VPD.COD_PROD
			  INNER JOIN	LGT_PROD P on P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                                  P.COD_PROD = PL.COD_PROD
			  INNER JOIN VTA_PEDIDO_VTA_CAB VPC on VPC.COD_GRUPO_CIA = VPD.COD_GRUPO_CIA AND
                                             VPC.COD_LOCAL= VPD.COD_LOCAL AND
                                             VPC.NUM_PED_VTA = VPD.NUM_PED_VTA AND
                                             VPC.NUM_PED_VTA = CP.NUM_PED_VTA
        INNER JOIN VTA_TIP_COMP VTC on VPC.TIP_COMP_PAGO = VTC.TIP_COMP
			 WHERE  VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	VPC.COD_LOCAL = cCodLocal_in
			 AND	CP.tip_comp_pago = cTipoFiltro
			 AND	VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio ||' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			 AND	TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			 AND	VPC.EST_PED_VTA=C_C_EST_PED_VTA_COBRADO
 			 UNION
			 SELECT VPD.NUM_PED_VTA	|| '�' ||
			 		FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || '�' ||
          --DECODE(CP.tip_comp_pago,01,C_C_TIP_COMP_PAGO_BOLETA,02,C_C_TIP_COMP_PAGO_FACTURA,05,C_C_TIP_COMP_PAGO_TICKET,'03',C_C_TIP_COMP_PAGO_GUIA)  || '�' ||
					NVL(CP.NUM_COMP_PAGO,' ')	|| '�' ||
					--TO_CHAR(VPC.FEC_CREA_PED_VTA_CAB,'dd/MM/yyyy HH24:MI:SS')|| '�' ||
					TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || '�' ||
					NVL(P.DESC_PROD,' ') || '�' ||
					VPD.UNID_VTA || '�' ||
					VPD.CANT_ATENDIDA  || '�' ||
					TO_CHAR(VPD.VAL_PREC_TOTAL,'999,990.00') || '�' ||
					--TO_CHAR(FEC_CREA_PED_VTA_CAB,'yyyy/MM/dd')
					TO_CHAR(FEC_PED_VTA,'yyyy/MM/dd')
			  FROM VTA_PEDIDO_VTA_DET VPD
        INNER JOIN VTA_COMP_PAGO CP on VPD.COD_GRUPO_CIA = CP.COD_GRUPO_CIA AND
                                       VPD.COD_LOCAL = CP.COD_LOCAL
				INNER JOIN LGT_PROD_LOCAL PL on PL.COD_GRUPO_CIA = VPD.COD_GRUPO_CIA AND
                                        PL.COD_LOCAL = VPD.COD_LOCAL AND
                                        PL.COD_PROD = VPD.COD_PROD
				INNER JOIN LGT_PROD P on P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                                 P.COD_PROD = PL.COD_PROD
			  INNER JOIN VTA_PEDIDO_VTA_CAB VPC on VPD.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA AND
                                             VPD.COD_LOCAL= VPC.COD_LOCAL AND
                                             VPD.NUM_PED_VTA= VPC.NUM_PED_VTA AND
                                             VPC.NUM_PED_VTA = CP.NUM_PEDIDO_ANUL
        INNER JOIN VTA_TIP_COMP VTC on VPC.TIP_COMP_PAGO = VTC.TIP_COMP
			  WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			  AND	VPC.COD_LOCAL = cCodLocal_in
			  AND	CP.tip_comp_pago = cTipoFiltro
			  AND	VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio ||' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			  AND	TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			  AND	VPC.EST_PED_VTA=C_C_EST_PED_VTA_COBRADO;
		RETURN curRep;
  END;
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : Generar el Reporte de Ventas x Vendedor para un rango de fecha (Maximo 40 Dias)
History : 23-JUN-14  TCT  Agrega proceso para carga de temporal
-------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION REPORTE_VENTAS_POR_VENDEDOR(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in     IN CHAR,
 			   						                   cFechaInicio 	IN CHAR,
 		  						                     cFechaFin 		IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
   cPorc_GG    number;
   cPorc_G     number;
   cPorc_Otros number;
   cPorc_Calc  number;

-- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
   nValNeto           NUMBER(15,3);
   nValNetoSIGV       NUMBER(15,3);
   -- dubilluz 03.04.2014
   nAnio_v integer;
   nMes_v  integer;
   nCalidadInv number;
   nAtencionCliente number;
   -- dubilluz 03.04.2014
   vn_Dias_Dif NUMBER;
  BEGIN

   -- 10.- Validacion de Rango de Fechas para Proceso de Ventas
   BEGIN
   SELECT TO_NUMBER(G.LLAVE_TAB_GRAL)
   INTO VN_MAX_DIAS_REP
   FROM PBL_TAB_GRAL G
   WHERE G.ID_TAB_GRAL  = 520
    AND G.COD_APL      = 'PTO_VENTA'
    AND G.COD_TAB_GRAL = 'MAX_DIAS_REP_VEND'
    AND G.EST_TAB_GRAL = 'A';
   EXCEPTION
    WHEN OTHERS THEN  
     VN_MAX_DIAS_REP:=40;
   END; 
  
    vn_Dias_Dif:= abs(to_date(cFechaFin,'dd/MM/yyyy') - to_date(cFechaInicio,'dd/MM/yyyy'));
    IF vn_Dias_Dif > VN_MAX_DIAS_REP THEN
     raise_application_error(-20001,'ERROR, La cantidad Maxima de Dias a procesar es : '||VN_MAX_DIAS_REP);
    END IF;

   -- 20.- Carga Resumen de  Ventas para Rango de Fechas
    sp_load_ventas_ven_loc(ccodgrupocia_in => cCodGrupoCia_in,
                                              ccodlocal_in => cCodLocal_in,
                                              cfecha_ini_in => cFechaInicio,
                                              cfecha_fin_in => cFechaFin);


   -- dubilluz 03.04.2014
   select to_number(to_char(to_date(cFechaInicio,'dd/mm/yyyy'),'yyyy')),
          to_number(to_char(to_date(cFechaInicio,'dd/mm/yyyy'),'mm'))
   into   nAnio_v,nMes_v
   from   dual;
   nAtencionCliente := fn_dev_meta('018',nAnio_v,nMes_v,cCodLocal_in);
   nCalidadInv      := fn_dev_meta('019',nAnio_v,nMes_v,cCodLocal_in);
   -- dubilluz 03.04.2014

  --SZ CONSULTA SI LA FECHA FINAL ES MAYOR O IGUAL A LA ACTUAL DEL SISTEMA
/*  IF  cFechaFin >= TO_CHAR(SYSDATE,'DD/MM/YYYY')   THEN
    NULL;
      --ACT_RES_VENTAS_VENDEDOR(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE,'DD/MM/YYYY'));

  END IF;*/



    SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
    INTO   cPorc_GG
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 205
    AND    T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
    INTO   cPorc_G
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 206
    AND    T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
    INTO   cPorc_Otros
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 207
    AND    T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
    INTO   cPorc_Calc
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 208
    AND    T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

-- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
      SELECT SUM(C.VAL_NETO_PED_VTA) T_C_IGV,
             SUM(C.VAL_NETO_PED_VTA - C.VAL_IGV_PED_VTA) T_S_IGV
      INTO nValNeto, nValNetoSIGV
      FROM VTA_PEDIDO_VTA_CAB C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL     = cCodLocal_in
      AND    C.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                             AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
      AND    C.EST_PED_VTA = 'C';

  	OPEN curRep FOR
    SELECT NVL(USU.COD_TRAB,' ')  || '�' ||
     	     USU.NOM_USU ||' '|| USU.APE_PAT ||' '|| NVL(USU.APE_MAT,' ')  || '�' ||
      	   nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ') || '�' ||
       	   nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV),'999,990.00') ,' ') || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
		   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00') ,' ')|| '�' ||
           nvl(TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00') ,' ')|| '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--           nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||

           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN

           nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
--           '-' || '�' || -- COLUMNAS VACIAS
--           nvl(TO_CHAR((100-cPorc_Calc)*((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100)/100,'999,990.00'),' ') || '�' || -- COLUMNAS VACIAS
           TO_CHAR('VENDEDOR') || '�' || -- COLUMNAS VACIAS
           USU.SEC_USU_LOCAL    || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ')|| '�' ||
           nvl(usu.login_usu,'')
    FROM   TMP_VTA_VEND_LOCAL V,
           PBL_USU_LOCAL USU
    WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    V.COD_LOCAL     = cCodLocal_in
    AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                           AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
    AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
    AND    V.COD_LOCAL     = USU.COD_LOCAL
    AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
		GROUP BY NVL(USU.COD_TRAB,' '),
              USU.NOM_USU , USU.APE_PAT , NVL(USU.APE_MAT,' '),
              USU.SEC_USU_LOCAL,usu.login_usu
    UNION
    SELECT ' '  || '�' ||
    	   	 'LOCAL S/.'  || '�' ||
-- 2009-10-01 JOLIVA: SE MUESTRA EL TOTAL CON IGV Y SIN IGV DE LAS CABECERAS
      	   nvl(TO_CHAR(nValNeto,'999,990.00'),' ')  || '�' ||
       	   nvl(TO_CHAR(nValNetoSIGV,'999,990.00')  ,' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
		   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00'),' ') || '�' ||
           nvl(TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.00'),' ') || '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--           nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||

           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN

           nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
--           '-' || '�' ||
--           nvl(TO_CHAR((100-cPorc_Calc)*((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100)/100,'999,990.00'),' ') || '�' || -- COLUMNAS VACIAS

           TO_CHAR(' ') || '�' || -- COLUMNAS VACIAS
           ' '    || '�' ||
           nvl(TO_CHAR(nValNeto-1,'999,990.00'),' ')|| '�' ||
           ' '
    FROM   TMP_VTA_VEND_LOCAL V,
           PBL_USU_LOCAL USU
    WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    V.COD_LOCAL     = cCodLocal_in
    AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                           AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
    AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
    AND    V.COD_LOCAL     = USU.COD_LOCAL
    AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
    GROUP BY ' ','LOCAL S/'
    UNION
    SELECT ' '  || '�' ||
    	   	 'LOCAL %.'  || '�' ||
      	   ' '  || '�' ||
       	   ' '  || '�' ||
-- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
/*
           nvl(TO_CHAR(SUM(V.MON_TOT_GG)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ')   || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_G)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ')    || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_OTROS)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_SERV)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ') || '�' ||
           nvl(TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00'),' ') || '�' ||
*/
           nvl(TO_CHAR(SUM(V.MON_TOT_GG)*100/nValNetoSIGV,'999,990.000'),' ')   || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_G)*100/nValNetoSIGV,'999,990.000'),' ')    || '�' ||
		   nvl(TO_CHAR(SUM(V.MON_TOT_GP)*100/nValNetoSIGV,'999,990.000'),' ')    || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_OTROS)*100/nValNetoSIGV,'999,990.000'),' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_SERV)*100/nValNetoSIGV,'999,990.000'),' ') || '�' ||
           nvl(TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.000'),' ') || '�' ||

           TO_CHAR(' ') || '�' || -- CALCULADO
           -- DUBILLUZ 02.04.2014
           -- INICIO
           TO_CHAR(' ') || '�' ||
           TO_CHAR(' ') || '�' ||
           TO_CHAR(' ') || '�' ||
           --- FIN
           nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
--           TO_CHAR(' ') || '�' || -- COLUMNAS VACIAS
           TO_CHAR('PORCENTAJE') || '�' || -- COLUMNAS QUE INDICA QUE SON PORCENTAJES ESTO SE UTILIZARA EN JAVA
           ' '    || '�' ||
           nvl(TO_CHAR(nValNetoSIGV,'999,990.00') ,' ')|| '�' ||
--           nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ')|| '�' ||
           ' '
    FROM   TMP_VTA_VEND_LOCAL V,
           PBL_USU_LOCAL USU
    WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    V.COD_LOCAL     = cCodLocal_in
    AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                           AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
    AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
    AND    V.COD_LOCAL     = USU.COD_LOCAL
    AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
    GROUP BY ' ','LOCAL %.';


		RETURN curRep;
  END;
----------------------------------------------------------------------------------------------
  FUNCTION REPORTE_DETALLE_VENTAS_VEND(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in     IN CHAR,
 			   						                   cFechaInicio 	  IN CHAR,
 		  						                     cFechaFin 		    IN CHAR,
									                     cUsuario_in      IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
  		  OPEN curRep FOR
        SELECT  DET.NUM_PED_VTA                   || '�' ||
                PROD.COD_PROD                     || '�' ||
                NVL(PROD.DESC_PROD,' ')           || '�' ||
                DET.UNID_VTA                      || '�' ||
                TO_CHAR((DET.CANT_ATENDIDA ),'999,990.00') || '�' ||
                TO_CHAR(DET.VAL_PREC_TOTAL,'999,990.00')   || '�' ||
--                TO_CHAR(DET.VAL_TOTAL_BONO,'999,990.00')
                NVL(DET.IND_ZAN,'-')
        FROM    LGT_PROD PROD,
                VTA_PEDIDO_VTA_DET DET,
                VTA_PEDIDO_VTA_CAB CAB
        WHERE   DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND	    DET.COD_LOCAL = cCodLocal_in
        AND	    DET.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                                         AND	   TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND	    DET.SEC_USU_LOCAL = cUsuario_in
        --AND   CAB.IND_PEDIDO_ANUL<>C_C_INDICADOR_SI
        AND     CAB.EST_PED_VTA='C'
        AND	    PROD.COD_GRUPO_CIA=DET.COD_GRUPO_CIA
        AND	    PROD.COD_PROD = DET.COD_PROD
        AND     DET.COD_GRUPO_CIA=CAB.COD_GRUPO_CIA
        AND     DET.COD_LOCAL=CAB.COD_LOCAL
        AND     DET.NUM_PED_VTA=CAB.NUM_PED_VTA;
        RETURN curRep;
  END;

  FUNCTION REPORTE_VENTAS_POR_PRODUCTO(cCodGrupoCia_in	    IN CHAR,
        		   					   	   cCodLocal_in     IN CHAR,
 			   						   	   cFechaInicio 	IN CHAR,
 		  						       	   cFechaFin 		IN CHAR
										   )
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
  		  OPEN curRep FOR
		  SELECT PL.COD_PROD			   					   	   	 				|| '�' ||
		         NVL(P.DESC_PROD,' ')  		   	   	 							|| '�' ||
			   	   p.desc_unid_present || '�' ||
				     L.NOM_LAB															|| '�' ||
			   	   TRIM(TO_CHAR(SUM(NVL(CANT_ATENDIDA/VAL_FRAC,0)),'999,990.00')) 	|| '�' ||
			   	   TRIM(TO_CHAR(SUM(NVL(VAL_PREC_TOTAL,0)),'999,990.00')) 			|| '�' ||
			   	   to_char(PL.STK_FISICO/VAL_FRAC,'999,990.00')
          FROM 	 VTA_PEDIDO_VTA_DET VD,
	 	       	 LGT_PROD_LOCAL PL,
			   	 LGT_LAB L,
			   	 LGT_PROD P,
			   	 VTA_PEDIDO_VTA_CAB VPC
		  WHERE VD.COD_GRUPO_CIA=cCodGrupoCia_in  AND
	  	  		VD.COD_LOCAL=cCodLocal_in 	      AND
	  			VD.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
	  			AND TO_DATE(cFechaFin||' 23:59:59','dd/MM/yyyy HH24:mi:ss')   AND
				VPC.EST_PED_VTA=EST_PED_COBRADO  					AND
			    VPC.COD_GRUPO_CIA = VD.COD_GRUPO_CIA AND
				VPC.COD_LOCAL= VD.COD_LOCAL 			AND
			    VPC.NUM_PED_VTA = VD.NUM_PED_VTA		AND
	  			VD.COD_GRUPO_CIA=PL.COD_GRUPO_CIA AND
	  			VD.COD_LOCAL=PL.COD_LOCAL         AND
	  			VD.COD_PROD=PL.COD_PROD			  AND
				P.COD_GRUPO_CIA=VD.COD_GRUPO_CIA  AND
				P.COD_PROD = VD.COD_PROD		  AND
				L.COD_LAB=P.COD_LAB
		   GROUP BY PL.COD_PROD,P.DESC_PROD,p.desc_unid_present,L.NOM_LAB,to_char(PL.STK_FISICO/VAL_FRAC,'999,990.00');
	RETURN curRep;

  END;

   FUNCTION REPORTE_VENDEDORES_PRODUCTO(cCodGrupoCia_in	IN CHAR,
        		   					   	   cCodLocal_in     IN CHAR,
										   cCodProd_in		IN CHAR,
 			   						   	   cFechaInicio 	IN CHAR,
 		  						       	   cFechaFin 		IN CHAR
										   )
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
  		  OPEN curRep FOR
		 SELECT UL.SEC_USU_LOCAL 		   					   	   || '�' ||
	   UL.COD_TRAB 											   	   || '�' ||
	   UL.APE_MAT || ' ' || UL.APE_PAT || ', '|| UL.NOM_USU 	   || '�' ||
	   COUNT(*)   	   	 			   	  	 	 				   || '�' ||
	   TRIM(TO_CHAR(NVL(SUM(CANT_ATENDIDA),0),'999,990.00'))  	   || '�' ||
	   TRIM(TO_CHAR( (SUM(CANT_ATENDIDA)/COUNT(*)),'999,990.00'))
          FROM VTA_PEDIDO_VTA_DET VD,
		  	   PBL_USU_LOCAL UL

		  WHERE VD.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	  	  		VD.COD_LOCAL     = cCodLocal_in 	AND
				VD.COD_PROD      = cCodProd_in		AND
				VD.SEC_USU_LOCAL IS NOT NULL AND
	  			VD.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
	  					       AND TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')  AND
				UL.COD_GRUPO_CIA = VD.COD_GRUPO_CIA AND
				UL.COD_LOCAL     = VD.COD_LOCAL     AND
				UL.SEC_USU_LOCAL = VD.SEC_USU_LOCAL

		  GROUP BY UL.SEC_USU_LOCAL,UL.COD_TRAB,UL.APE_MAT || ' ' || UL.APE_PAT || ', '|| UL.NOM_USU;
	RETURN curRep;

  END;


  FUNCTION REPORTE_VENTAS_POR_PRODUCTO_F(cCodGrupoCia_in	    IN CHAR,
          		   					   	   cCodLocal_in     	IN CHAR,
   			   						   	   cFechaInicio 		IN CHAR,
   		  						       	   cFechaFin 			IN CHAR,
  										   cTipoFiltro_in  		IN CHAR,
    		   						 		   cCodFiltro_in 	 	IN CHAR
  										   )
    RETURN FarmaCursor
    IS
     curRep FarmaCursor;
    BEGIN
  		    IF(cTipoFiltro_in = 1) THEN --principio activo
       	OPEN curRep FOR

  		SELECT PL.COD_PROD			   					   	   	 				|| '�' ||
  		         NVL(P.DESC_PROD,' ')  		   	   	 							|| '�' ||
  			   	 VD.UNID_VTA 														|| '�' ||
  				 L.NOM_LAB															|| '�' ||
  			   	 TRIM(TO_CHAR(SUM(NVL(CANT_ATENDIDA/VAL_FRAC,0)),'999,990.00')) 	|| '�' ||
  			   	 TRIM(TO_CHAR(SUM(NVL(VAL_PREC_TOTAL,0)),'999,990.00')) 			|| '�' ||
  			   	 PL.STK_FISICO
            FROM VTA_PEDIDO_VTA_DET VD,
  	 	       LGT_PROD_LOCAL PL,
  			   LGT_LAB L,
  			   LGT_PROD P,
  			   VTA_PEDIDO_VTA_CAB VPC,
  			   LGT_PRINC_ACT_PROD PRINC_ACT_PROD
  		  WHERE VD.COD_GRUPO_CIA=cCodGrupoCia_in  AND
  	  	  		VD.COD_LOCAL=cCodLocal_in 	      AND
  	  			VD.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
  	  			AND TO_DATE(cFechaFin||' 23:59:59','dd/MM/yyyy HH24:mi:ss')   AND
  				VPC.EST_PED_VTA=EST_PED_COBRADO  				AND
  				VPC.COD_GRUPO_CIA = VD.COD_GRUPO_CIA    AND
  				VPC.COD_LOCAL= VD.COD_LOCAL 			AND
  				VPC.NUM_PED_VTA = VD.NUM_PED_VTA		AND
  	  			VD.COD_GRUPO_CIA=PL.COD_GRUPO_CIA AND
  	  			VD.COD_LOCAL=PL.COD_LOCAL         AND
  	  			VD.COD_PROD=PL.COD_PROD			  AND
  				PRINC_ACT_PROD.COD_PRINC_ACT = cCodFiltro_in AND
  				P.COD_PROD = PRINC_ACT_PROD.COD_PROD AND
  				P.COD_GRUPO_CIA=VD.COD_GRUPO_CIA  AND
  				P.COD_PROD = VD.COD_PROD		  AND
  				L.COD_LAB=P.COD_LAB
  		   GROUP BY PL.COD_PROD,P.DESC_PROD,VD.UNID_VTA,L.NOM_LAB,PL.STK_FISICO;
  	ELSIF(cTipoFiltro_in = 2) THEN --accion terapeutica
  		OPEN curRep FOR
                         SELECT PL.COD_PROD			   					   	   	 				|| '�' ||
  		         NVL(P.DESC_PROD,' ')  		   	   	 							|| '�' ||
  			   	 VD.UNID_VTA 														|| '�' ||
  				 L.NOM_LAB															|| '�' ||
  			   	 TRIM(TO_CHAR(SUM(NVL(CANT_ATENDIDA/VAL_FRAC,0)),'999,990.00')) 	|| '�' ||
  			   	 TRIM(TO_CHAR(SUM(NVL(VAL_PREC_TOTAL,0)),'999,990.00')) 			|| '�' ||
  			   	 PL.STK_FISICO
            FROM VTA_PEDIDO_VTA_DET VD,
  	 	       LGT_PROD_LOCAL PL,
  			   LGT_LAB L,
  			   LGT_PROD P,
  			   VTA_PEDIDO_VTA_CAB VPC,
  			   LGT_ACC_TERAP ACC_TER,
  			   LGT_ACC_TERAP_PROD ACC_TERAP_PROD
  		  WHERE VD.COD_GRUPO_CIA=cCodGrupoCia_in  AND
  	  	  		VD.COD_LOCAL=cCodLocal_in 	      AND
  				ACC_TER.COD_ACC_TERAP = cCodFiltro_in AND
  	  			VD.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
  	  			AND TO_DATE(cFechaFin||' 23:59:59','dd/MM/yyyy HH24:mi:ss')   AND
  				VPC.EST_PED_VTA=EST_PED_COBRADO  				AND
  				VPC.COD_GRUPO_CIA = VD.COD_GRUPO_CIA    AND
  				VPC.COD_LOCAL= VD.COD_LOCAL 			AND
  				VPC.NUM_PED_VTA = VD.NUM_PED_VTA		AND
  			    P.COD_PROD = ACC_TERAP_PROD.COD_PROD AND
  			    ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP AND
  	  			VD.COD_GRUPO_CIA=PL.COD_GRUPO_CIA AND
  	  			VD.COD_LOCAL=PL.COD_LOCAL         AND
  	  			VD.COD_PROD=PL.COD_PROD			  AND
  				P.COD_GRUPO_CIA=VD.COD_GRUPO_CIA  AND
  				P.COD_PROD = VD.COD_PROD		  AND
  				L.COD_LAB=P.COD_LAB
  		   GROUP BY PL.COD_PROD,P.DESC_PROD,VD.UNID_VTA,L.NOM_LAB,PL.STK_FISICO;
  	ELSIF(cTipoFiltro_in = 3) THEN --laboratorio
  		OPEN curRep FOR
                         SELECT PL.COD_PROD			   					   	   	 				|| '�' ||
  		         NVL(P.DESC_PROD,' ')  		   	   	 							|| '�' ||
  			   	 VD.UNID_VTA 														|| '�' ||
  				 L.NOM_LAB															|| '�' ||
  			   	 TRIM(TO_CHAR(SUM(NVL(CANT_ATENDIDA/VAL_FRAC,0)),'999,990.00')) 	|| '�' ||
  			   	 TRIM(TO_CHAR(SUM(NVL(VAL_PREC_TOTAL,0)),'999,990.00')) 			|| '�' ||
  			   	 PL.STK_FISICO
            FROM VTA_PEDIDO_VTA_DET VD,
  	 	       LGT_PROD_LOCAL PL,
  			   LGT_LAB L,
  			   LGT_PROD P,
  			   VTA_PEDIDO_VTA_CAB VPC
  		  WHERE VD.COD_GRUPO_CIA=cCodGrupoCia_in  AND
  	  	  		VD.COD_LOCAL=cCodLocal_in 	      AND
  	  			VD.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
  	  			AND TO_DATE(cFechaFin||' 23:59:59','dd/MM/yyyy HH24:mi:ss')   AND
  				VPC.EST_PED_VTA=EST_PED_COBRADO  				AND
  				VPC.COD_GRUPO_CIA = VD.COD_GRUPO_CIA    AND
  				VPC.COD_LOCAL= VD.COD_LOCAL 			AND
  				VPC.NUM_PED_VTA = VD.NUM_PED_VTA		AND
  	  			VD.COD_GRUPO_CIA=PL.COD_GRUPO_CIA AND
  	  			VD.COD_LOCAL=PL.COD_LOCAL         AND
  	  			VD.COD_PROD=PL.COD_PROD			  AND
  				P.COD_LAB=cCodFiltro_in			  AND
  				P.COD_GRUPO_CIA=VD.COD_GRUPO_CIA  AND
  				P.COD_PROD = VD.COD_PROD		  AND
  				L.COD_LAB=P.COD_LAB
  		   GROUP BY PL.COD_PROD,P.DESC_PROD,VD.UNID_VTA,L.NOM_LAB,PL.STK_FISICO;

    	END IF;
  	RETURN curRep;
    END;

/*  FUNCTION REPORTE_VETAS_POR_DIA(cCodGrupoCia_in IN CHAR,
        		   				 cCodLocal_in    IN CHAR,
 			   					 cFechaInicio 	 IN CHAR,
 		  						 cFechaFin 		 IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
  		  OPEN curRep FOR
		  SELECT
				TOTALES.FECHA || '�' ||
				TO_CHAR(NVL(TOTALES.CANTIDADTOTAL,0),'999990')|| '�' ||
				NVL(BOLETAE.MINIMOBOLETA,' ') || '�' ||
				NVL(FACTURAE.MINIMOFACTURA,' ') || '�' ||
				TO_CHAR(NVL(BOLETAE.CANTIDADBOLETA,0),'999990')|| '�' ||
				TO_CHAR(NVL(FACTURAE.CANTIDADFACTURA,0),'999990')|| '�' ||
				TO_CHAR(NVL(BOLETAA.CANTIDADBANULADO,0),'999990')|| '�' ||
				TO_CHAR(NVL(FACTURAA.CANTIDADFANULADO,0),'999990')|| '�' ||
				TO_CHAR(NVL(TOTALES.TOTAL - TOTALES.TOTALNULO ,0),'999,999.00')
		  FROM
				--1 qery fecha pedidos total
				(SELECT TO_CHAR(VPC.FEC_PED_VTA,'DD/MM/YYYY')FECHA,
					   COUNT(VPC.NUM_PED_VTA)CANTIDADTOTAL,
					   SUM(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO)TOTAL,
					   NVL(NULOS.TOTALNULO,0)TOTALNULO
				FROM   VTA_PEDIDO_VTA_CAB VPC,
					   VTA_COMP_PAGO CP,
					   (SELECT trunc(VPC.FEC_PED_VTA) FEC_PED_VTA,
                     SUM(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO)TOTALNULO
						FROM VTA_PEDIDO_VTA_CAB VPC,
						VTA_COMP_PAGO CP
						WHERE  CP.COD_LOCAL =  cCodLocal_in
						AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
						AND VPC.EST_PED_VTA = C_C_EST_PED_VTA_COBRADO
						AND VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
						AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
						AND CP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
						AND CP.COD_LOCAL = VPC.COD_LOCAL
						AND CP.NUM_PEDIDO_ANUL = VPC.NUM_PED_VTA
            GROUP BY trunc(VPC.FEC_PED_VTA))NULOS
				WHERE  CP.COD_LOCAL = cCodLocal_in
					   AND CP.COD_GRUPO_CIA=cCodGrupoCia_in
					   AND VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
					   AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
					   AND CP.COD_LOCAL = VPC.COD_LOCAL
					   AND CP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
					   AND CP.NUM_PED_VTA = VPC.NUM_PED_VTA
             AND trunc(vpc.FEC_PED_VTA) = NULOS.FEC_PED_VTA(+)
				GROUP BY TO_CHAR(VPC.FEC_PED_VTA,'DD/MM/YYYY'),NULOS.TOTALNULO)TOTALES,
				--2 QERY BOLETA INICIAL, NO BOLETA, FECHA
				(SELECT TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY')FECHA,
					   MIN(CP.NUM_COMP_PAGO)MINIMOBOLETA,
					   COUNT(CP.TIP_COMP_PAGO)CANTIDADBOLETA
				FROM   VTA_COMP_PAGO CP
				WHERE  CP.COD_LOCAL = cCodLocal_in
					   AND CP.COD_GRUPO_CIA=cCodGrupoCia_in
					   AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_BOL
					   AND CP.FEC_CREA_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
					   AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
				GROUP BY TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY'))BOLETAE,
				--3 QERY FACTURA INICIAL,NO FACTURA, FECHA
				(SELECT TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY')FECHA,
					   MIN(CP.NUM_COMP_PAGO)MINIMOFACTURA,
					   COUNT(CP.TIP_COMP_PAGO)CANTIDADFACTURA
				FROM   VTA_COMP_PAGO CP
				WHERE  CP.COD_LOCAL = cCodLocal_in
					   AND CP.COD_GRUPO_CIA=cCodGrupoCia_in
					   AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_FACT
					   AND CP.FEC_CREA_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
					   AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
				GROUP BY TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY'))FACTURAE,
				--4 QERY BOLETA FECHA ANULADO
				(SELECT TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY')FECHA,
					   COUNT(CP.TIP_COMP_PAGO)CANTIDADBANULADO
				FROM   VTA_COMP_PAGO CP
				WHERE  CP.COD_LOCAL =cCodLocal_in
					   AND CP.COD_GRUPO_CIA=cCodGrupoCia_in
					   AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_BOL
				       AND CP.IND_COMP_ANUL =C_C_INDICADOR_SI
					   AND CP.FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
					   AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
				GROUP BY TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY'))BOLETAA,
				--5 QERY FACTURA FECHA ANULADO
				(SELECT TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY')FECHA,
					   COUNT(CP.TIP_COMP_PAGO)CANTIDADFANULADO
				FROM   VTA_COMP_PAGO CP
				WHERE  CP.COD_LOCAL = cCodLocal_in
					   AND CP.COD_GRUPO_CIA=cCodGrupoCia_in
					   AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_FACT
				       AND CP.IND_COMP_ANUL =C_C_INDICADOR_SI
					   AND CP.FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
					   AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
				GROUP BY TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY'))FACTURAA
			WHERE
				TOTALES.FECHA=BOLETAE.FECHA(+)
				AND TOTALES.FECHA=FACTURAE.FECHA(+)
				AND TOTALES.FECHA=BOLETAA.FECHA(+)
				AND TOTALES.FECHA=FACTURAA.FECHA(+);

		RETURN curRep;

	END;
*/

--Modificado DUBILLUZ 28/08/2007
  FUNCTION REPORTE_VETAS_POR_DIA(cCodGrupoCia_in IN CHAR,
        		   				 cCodLocal_in    IN CHAR,
 			   					 cFechaInicio 	 IN CHAR,
 		  						 cFechaFin 		 IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
  		  OPEN curRep FOR
		  SELECT
				TOTALES.FECHA || '�' ||
        TO_CHAR(NVL(TOTALES.CANT,0),'999990') || '�' ||
        NVL(TICKETE.MINIMO,' ') || '�' ||                                           --JCHAVEZ 13.07.2009.n
        NVL(BOLETAE.MINIMO,' ') || '�' ||
        NVL(FACTURAE.MINIMO,' ') || '�' ||
        TO_CHAR(NVL(TICKETE.CANT,0),'999990') || '�' ||                             --JCHAVEZ 13.07.2009.n
        TO_CHAR(NVL(BOLETAE.CANT,0),'999990') || '�' ||
        TO_CHAR(NVL(FACTURAE.CANT,0),'999990') || '�' ||
        TO_CHAR(NVL(TICKETFACE.CANT,0),'999990') || '�' ||                          --LLEIVA 06-Feb-2014
        TO_CHAR(NVL(TICKETA.CANT,0),'999990') || '�' ||                             --JCHAVEZ 13.07.2009.n
        TO_CHAR(NVL(BOLETAA.CANT,0),'999990') || '�' ||
        TO_CHAR(NVL(FACTURAA.CANT,0),'999990') || '�' ||
        TO_CHAR(NVL(TICKETFACA.CANT,0),'999990') || '�' ||                          --LLEIVA 06-Feb-2014
        TO_CHAR(NVL(TOTALES.TOTAL - TOTALES.TOTALNULO ,0),'999,999.00') || '�' ||
        TOTALES.FECHA_ORD
		  FROM
				--1 qery fecha pedidos total
        ( SELECT TO_CHAR(VPC.FEC_PED_VTA,'DD/MM/YYYY') FECHA,
               COUNT(VPC.NUM_PED_VTA) CANT,
               SUM(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO) TOTAL,
               NVL(NULOS.TOTALNULO,0) TOTALNULO ,
               ---
               TO_CHAR(VPC.FEC_PED_VTA,'YYYYMMDD') FECHA_ORD
        FROM VTA_PEDIDO_VTA_CAB VPC
        INNER JOIN VTA_COMP_PAGO CP on CP.COD_LOCAL = VPC.COD_LOCAL AND
                                       CP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA AND
                                       CP.NUM_PED_VTA = VPC.NUM_PED_VTA
      LEFT JOIN
      (     SELECT trunc(VPC.FEC_PED_VTA) FEC_PED_VTA,
                     SUM(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO) TOTALNULO
              FROM VTA_PEDIDO_VTA_CAB VPC,
              VTA_COMP_PAGO CP
              WHERE  CP.COD_LOCAL = cCodLocal_in
              AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
              AND VPC.EST_PED_VTA = C_C_EST_PED_VTA_COBRADO
              AND VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
              AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
              AND CP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
              AND CP.COD_LOCAL = VPC.COD_LOCAL
              AND CP.NUM_PEDIDO_ANUL = VPC.NUM_PED_VTA
              GROUP BY trunc(VPC.FEC_PED_VTA)
        ) NULOS on trunc(vpc.FEC_PED_VTA) = NULOS.FEC_PED_VTA
        WHERE CP.COD_LOCAL = cCodLocal_in
        AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
        AND VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                    TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        GROUP BY TO_CHAR(VPC.FEC_PED_VTA,'DD/MM/YYYY'),
                 NULOS.TOTALNULO,
                 TO_CHAR(VPC.FEC_PED_VTA,'YYYYMMDD')
      ) TOTALES

			--2 QERY BOLETA INICIAL, NO BOLETA, FECHA
      LEFT JOIN
      (SELECT TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY') FECHA,
               MIN(CP.NUM_COMP_PAGO) MINIMO,
               COUNT(CP.TIP_COMP_PAGO) CANT
        FROM VTA_COMP_PAGO CP
        WHERE CP.COD_LOCAL = cCodLocal_in
        AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_BOL
        --AND CP.IND_COMP_ANUL = C_C_INDICADOR_NO
        AND CP.FEC_CREA_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
        AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        GROUP BY TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY')
      )
      BOLETAE on TOTALES.FECHA=BOLETAE.FECHA

			 --3 QERY FACTURA INICIAL,NO FACTURA, FECHA
      LEFT JOIN
      (SELECT TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY') FECHA,
              MIN(CP.NUM_COMP_PAGO) MINIMO,
              COUNT(CP.TIP_COMP_PAGO) CANT
       FROM VTA_COMP_PAGO CP
       WHERE CP.COD_LOCAL = cCodLocal_in
       AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CP.TIP_COMP_PAGO = C_C_IND_TIP_COMP_PAGO_FACT
       --AND CP.IND_COMP_ANUL = C_C_INDICADOR_NO
       AND CP.FEC_CREA_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
       AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
       GROUP BY TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY')
      )
      FACTURAE on TOTALES.FECHA=FACTURAE.FECHA

      --X QERY TICKET INICIAL,NO TICKET, FECHA //JCHAVEZ 13.07.2009.sn
      LEFT JOIN
      (SELECT TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY') FECHA,
              MIN(CP.NUM_COMP_PAGO) MINIMO,
              COUNT(CP.TIP_COMP_PAGO) CANT
       FROM VTA_COMP_PAGO CP
       WHERE CP.COD_LOCAL = cCodLocal_in
       AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_TICKET
       --AND CP.IND_COMP_ANUL = C_C_INDICADOR_NO
       AND CP.FEC_CREA_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
       AND TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
       GROUP BY TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY')
      )
      TICKETE on TOTALES.FECHA=TICKETE.FECHA

      --X QUERY TICKET FAC, FECHA //LLEIVA 06-Feb-2014
      LEFT JOIN
      (SELECT TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY') FECHA,
              COUNT(CP.TIP_COMP_PAGO) CANT
       FROM VTA_COMP_PAGO CP
       WHERE CP.COD_LOCAL = cCodLocal_in
       AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CP.TIP_COMP_PAGO = C_C_IND_TIP_COMP_PAGO_TIC_FAC
       --AND CP.IND_COMP_ANUL = C_C_INDICADOR_NO
       AND CP.FEC_CREA_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                         TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
       GROUP BY TO_CHAR(CP.FEC_CREA_COMP_PAGO,'DD/MM/YYYY')
      )
      TICKETFACE on TOTALES.FECHA=TICKETFACE.FECHA         --//JCHAVEZ 13.07.2009.en

				--4 QERY BOLETA FECHA ANULADO
      LEFT JOIN
      (SELECT TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY')FECHA,
              COUNT(CP.TIP_COMP_PAGO) CANT
       FROM VTA_COMP_PAGO CP
       WHERE CP.COD_LOCAL = cCodLocal_in
       AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_BOL
       AND CP.IND_COMP_ANUL = C_C_INDICADOR_SI
       AND CP.FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                         TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
       GROUP BY TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY')
      )
      BOLETAA on TOTALES.FECHA=BOLETAA.FECHA

				--5 QERY FACTURA FECHA ANULADO
      LEFT JOIN
      (SELECT TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY')FECHA,
              COUNT(CP.TIP_COMP_PAGO) CANT
       FROM   VTA_COMP_PAGO CP
       WHERE  CP.COD_LOCAL = cCodLocal_in
       AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_FACT
       AND CP.IND_COMP_ANUL = C_C_INDICADOR_SI
       AND CP.FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                         TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
       GROUP BY TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY')
      )
      FACTURAA on TOTALES.FECHA=FACTURAA.FECHA

        	--X QERY TICKET FECHA ANULADO -- //JCHAVEZ 13.07.2009.sn

      LEFT JOIN
      (SELECT TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY') FECHA,
              COUNT(CP.TIP_COMP_PAGO) CANT
       FROM VTA_COMP_PAGO CP
       WHERE CP.COD_LOCAL = cCodLocal_in
       AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CP.TIP_COMP_PAGO = C_C_IND_TIP_COMP_PAGO_TICKET
       AND CP.IND_COMP_ANUL = C_C_INDICADOR_SI
       AND CP.FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                         TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
       GROUP BY TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY')
      )
      TICKETA on TOTALES.FECHA=TICKETA.FECHA               -- //JCHAVEZ 13.07.2009.en

      --X QERY TICKET FACT FECHA ANULADO                   -- //LLEIVA 06-Feb-2014
      LEFT JOIN
      (SELECT TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY') FECHA,
              COUNT(CP.TIP_COMP_PAGO) CANT
       FROM VTA_COMP_PAGO CP
       WHERE CP.COD_LOCAL = cCodLocal_in
       AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CP.TIP_COMP_PAGO= C_C_IND_TIP_COMP_PAGO_TIC_FAC
       AND CP.IND_COMP_ANUL = C_C_INDICADOR_SI
       AND CP.FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                         TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
       GROUP BY TO_CHAR(CP.FEC_ANUL_COMP_PAGO,'DD/MM/YYYY')
      ) TICKETFACA on TOTALES.FECHA=TICKETFACA.FECHA;

		RETURN curRep;

	END;

	FUNCTION REPORTE_RESUMEN_VTA_NOT_CREDIT (cCodGrupoCia_in	IN CHAR,
  		   						  			   cCodLocal_in	  	IN CHAR,
 		  						  			   cFechaInicio 	IN CHAR,
 		  						  			   cFechaFin 		IN CHAR)
  	RETURN FarmaCursor
	IS
   	  curRep FarmaCursor;
   	BEGIN
  		OPEN curRep FOR
		SELECT VCP.TIP_COMP_PAGO || '�' ||
			   COUNT(VPC.NUM_PED_VTA) || '�' ||
			   TO_CHAR(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),'999,990.00')
		FROM VTA_PEDIDO_VTA_CAB VPC ,VTA_PEDIDO_VTA_CAB VPC2 , VTA_COMP_PAGO VCP
		WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
		AND   VPC.COD_LOCAL = cCodLocal_in
		AND	  VPC.TIP_COMP_PAGO =  C_C_IND_TIP_COMP_PAGO_NC
		AND   VPC.EST_PED_VTA = C_C_EST_PED_VTA_COBRADO
		AND   VPC2.TIP_COMP_PAGO = C_C_IND_TIP_COMP_PAGO_BOL
		AND   VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
		AND   VPC.COD_LOCAL = VPC2.COD_LOCAL
		AND   VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
		AND   VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
		AND	  VCP.COD_LOCAL = VPC.COD_LOCAL
		AND	  VCP.NUM_PED_VTA = VPC.NUM_PED_VTA
		AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		GROUP BY VCP.TIP_COMP_PAGO
		UNION
		SELECT VCP.TIP_COMP_PAGO || '�' ||
			   COUNT(VPC.NUM_PED_VTA) || '�' ||
			   TO_CHAR(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),'999,990.00')
		FROM VTA_PEDIDO_VTA_CAB VPC ,VTA_PEDIDO_VTA_CAB VPC2 , VTA_COMP_PAGO VCP
		WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
		AND   VPC.COD_LOCAL = cCodLocal_in
		AND	  VPC.TIP_COMP_PAGO =  C_C_IND_TIP_COMP_PAGO_NC
		AND   VPC.EST_PED_VTA = C_C_EST_PED_VTA_COBRADO
		AND   VPC2.TIP_COMP_PAGO = C_C_IND_TIP_COMP_PAGO_FACT
		AND   VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
		AND   VPC.COD_LOCAL = VPC2.COD_LOCAL
		AND   VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
		AND   VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
		AND	  VCP.COD_LOCAL = VPC.COD_LOCAL
		AND	  VCP.NUM_PED_VTA = VPC.NUM_PED_VTA
		AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
		AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
		GROUP BY VCP.TIP_COMP_PAGO;

		RETURN curRep;

	END;

  FUNCTION REPORTE_DETALLADO_RESUMEN_VTA (cCodGrupoCia_in  	IN CHAR,
  		   						  			              cCodLocal_in	  	IN CHAR,
                                          cFechaInicio 	    IN CHAR,
 		  						  			                cFechaFin 		    IN CHAR,
                                          cCodProd_in 	    IN CHAR)
  RETURN FarmaCursor
	IS
   	curRep FarmaCursor;
   	BEGIN
  		OPEN curRep FOR
/*           SELECT TO_CHAR(vpd.fec_crea_ped_vta_det,'dd/MM/yyyy HH24:MI:SS')|| '�' ||
                  --vcp.tip_comp_pago,
                  --DECODE(vcp.tip_comp_pago,01,' ',02,'F',03,'G',04,'N'),
                  DECODE(vcp.tip_comp_pago,01,' ',02,C_C_TIP_COMP_PAGO_FACTURA,03,C_C_TIP_COMP_PAGO_GUIA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO) || '�' ||
                  NVL(vcp.num_comp_pago,' ') || '�' ||
                  DECODE(vcp.ind_comp_anul,'N','COBRADO','ANULADO') || '�' ||
                  vpd.cant_atendida || '�' ||
                  vpd.unid_vta || '�' ||
                  TO_CHAR(vpd.val_prec_vta,'999,990.00') || '�' ||
                  TO_CHAR(vpd.val_prec_total,'999,990.00') || '�' ||
                  vpd.usu_crea_ped_vta_det
           FROM   vta_pedido_vta_det vpd,
                  vta_comp_pago vcp
           WHERE  vpd.cod_grupo_cia = cCodGrupoCia_in
           AND    vpd.cod_local = cCodLocal_in
           AND    vpd.cod_prod = cCodProd_in
           AND    VPd.fec_crea_ped_vta_det BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			     AND    TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
           AND    vpd.cod_grupo_cia = vcp.cod_grupo_cia
           AND    vpd.cod_local = vcp.cod_local
           AND    vpd.num_ped_vta = vcp.num_ped_vta;*/
          SELECT TO_CHAR(D.fec_crea_ped_vta_det,'dd/MM/yyyy HH24:MI:SS')|| '�' ||
                 FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || '�' || --DECODE(COMP.tip_comp_pago,01,' ',02,C_C_TIP_COMP_PAGO_FACTURA,03,C_C_TIP_COMP_PAGO_GUIA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO,05,C_C_TIP_COMP_PAGO_TICKET) || '�' ||
                 NVL(COMP.num_comp_pago,' ') || '�' ||
                 DECODE(COMP.ind_comp_anul,'N','COBRADO','ANULADO') || '�' ||
                 D.cant_atendida || '�' ||
                 D.unid_vta || '�' ||
                 TO_CHAR(D.val_prec_vta,'999,990.00') || '�' ||
                 TO_CHAR(D.val_prec_total,'999,990.00') || '�' ||
                 D.usu_crea_ped_vta_det
          FROM   VTA_PEDIDO_VTA_CAB C
          INNER JOIN VTA_PEDIDO_VTA_DET D on D.COD_GRUPO_CIA = C.COD_GRUPO_CIA AND
                                             D.COD_LOCAL = C.COD_LOCAL AND
                                             D.NUM_PED_VTA = C.NUM_PED_VTA
          INNER JOIN VTA_COMP_PAGO COMP on COMP.COD_GRUPO_CIA = D.COD_GRUPO_CIA AND
                                           COMP.COD_LOCAL = D.COD_LOCAL AND
                                           COMP.NUM_PED_VTA = C.NUM_PED_VTA
          INNER JOIN VTA_TIP_COMP VTC on C.TIP_COMP_PAGO = VTC.TIP_COMP
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL = cCodLocal_in
          AND    D.COD_PROD = cCodProd_in
          AND    C.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                   AND TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')

          UNION
          SELECT TO_CHAR(D.fec_crea_ped_vta_det,'dd/MM/yyyy HH24:MI:SS')|| '�' ||
                 FARMA_UTILITY.OBTIENE_INICIALES(VTC.DESC_COMP) || '�' || --DECODE(COMP.tip_comp_pago,01,' ',02,C_C_TIP_COMP_PAGO_FACTURA,03,C_C_TIP_COMP_PAGO_GUIA,04,C_C_TIP_COMP_PAGO_NOTA_CREDITO,05,C_C_TIP_COMP_PAGO_TICKET) || '�' ||
                 NVL(COMP.num_comp_pago,' ') || '�' ||
                 DECODE(COMP.ind_comp_anul,'N','COBRADO','ANULADO') || '�' ||
                 D.cant_atendida || '�' ||
                 D.unid_vta || '�' ||
                 TO_CHAR(D.val_prec_vta,'999,990.00') || '�' ||
                 TO_CHAR(D.val_prec_total,'999,990.00') || '�' ||
                 D.usu_crea_ped_vta_det
          FROM VTA_PEDIDO_VTA_CAB C
          INNER JOIN VTA_PEDIDO_VTA_DET D on D.COD_GRUPO_CIA = C.COD_GRUPO_CIA AND
                                             D.COD_LOCAL = C.COD_LOCAL AND
                                             D.NUM_PED_VTA = C.NUM_PED_VTA
          INNER JOIN VTA_COMP_PAGO COMP on COMP.COD_GRUPO_CIA = D.COD_GRUPO_CIA AND
                                           COMP.COD_LOCAL = D.COD_LOCAL AND
                                           COMP.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
          INNER JOIN VTA_TIP_COMP VTC on C.TIP_COMP_PAGO = VTC.TIP_COMP
          WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                AND C.COD_LOCAL = cCodLocal_in
                AND D.COD_PROD = cCodProd_in
                AND C.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                      AND TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS');

       RETURN curRep ;
  END;


  FUNCTION REPORTE_VTA_PRODUCTO_LAB(cCodGrupoCia_in  	IN CHAR,
  		   						  			        cCodLocal_in	  	IN CHAR,
                                    cFechaInicio 	    IN CHAR,
 		  						  			          cFechaFin 		    IN CHAR)
  RETURN FarmaCursor
	IS
   	curRep FarmaCursor;
   	BEGIN
  		OPEN curRep FOR
           SELECT L.COD_LAB|| '�' ||
                  l.Nom_Lab|| '�' ||
                  to_char(SUM(vd.val_prec_total),'999,990.00')|| '�' ||
                  to_char(SUM(vd.cant_atendida),'999,990.00')
           FROM 	 VTA_PEDIDO_VTA_DET VD,
     	             LGT_PROD_LOCAL PL,
	   	             LGT_LAB L,
	   	             LGT_PROD P,
	   	             VTA_PEDIDO_VTA_CAB VPC
           WHERE   VD.COD_GRUPO_CIA=cCodGrupoCia_in
	  	     AND     VD.COD_LOCAL=cCodLocal_in
	  	     AND     VD.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio ||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
	  	     AND     TO_DATE(cFechaFin||' 23:59:59','dd/MM/yyyy HH24:mi:ss')
			     AND     VPC.EST_PED_VTA= C_C_EST_PED_VTA_COBRADO
			     AND     VPC.COD_GRUPO_CIA = VD.COD_GRUPO_CIA
			     AND     VPC.COD_LOCAL= VD.COD_LOCAL
		       AND     VPC.NUM_PED_VTA = VD.NUM_PED_VTA
	  	     AND     VD.COD_GRUPO_CIA=PL.COD_GRUPO_CIA
	  	     AND     VD.COD_LOCAL=PL.COD_LOCAL
	  	     AND     VD.COD_PROD=PL.COD_PROD
			     AND     P.COD_GRUPO_CIA=VD.COD_GRUPO_CIA
			     AND     P.COD_PROD = VD.COD_PROD
			     AND     L.COD_LAB=P.COD_LAB
           GROUP BY L.COD_LAB,l.nom_lab;
       RETURN curRep ;
  END;

  FUNCTION REPORTE_CONSOLIDADO_VTA_PROD(cCodGrupoCia_in  	IN CHAR,
  		   						  			            cCodLocal_in	  	IN CHAR,
                                        cFechaInicio_in 	IN CHAR,
 		  						  			              cFechaFin_in 		  IN CHAR)
  RETURN FarmaCursor
	IS
   	curRep FarmaCursor;
   	BEGIN
  		OPEN curRep FOR
          SELECT nvl(usu.nom_usu ||' '|| usu.ape_pat||' '|| usu.ape_mat,' ')|| '�' ||
                 to_char(SUM(vpd.cant_atendida/vpd.val_frac),'999,990.00')|| '�' ||
                 to_char(SUM(vpd.val_prec_total),'999,990.00')
          FROM   vta_pedido_vta_det vpd,
                 vta_pedido_vta_cab vpc,
                 pbl_usu_local usu
          WHERE  vpd.cod_grupo_cia = ccodgrupocia_in
          AND    vpd.cod_local = ccodlocal_in
          AND    vpd.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(Cfechainicio_in||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
          AND    TO_DATE(cfechafin_in||' 23:59:59','dd/MM/yyyy HH24:mi:ss')
          AND    vpc.EST_PED_VTA = EST_PED_COBRADO
          AND    usu.sec_usu_local = vpd.sec_usu_local
          AND    usu.cod_grupo_cia = vpd.cod_grupo_cia
          AND    usu.cod_local = vpd.cod_local
          AND	   VPC.COD_GRUPO_CIA = VPD.COD_GRUPO_CIA
          AND  	 VPC.COD_LOCAL= VPD.COD_LOCAL
          AND  	 VPC.NUM_PED_VTA = VPD.NUM_PED_VTA
          GROUP BY nvl(usu.nom_usu ||' '|| usu.ape_pat||' '|| usu.ape_mat,' ');
       RETURN curRep ;
  END;

  FUNCTION REPORTE_VTA_PRODUCTO_VIRTUAL(cCodGrupoCia_in	 IN CHAR,
        		   					   	            cCodLocal_in     IN CHAR,
 			   						   	                cFechaInicio 	   IN CHAR,
 		  						       	              cFechaFin 		   IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
  		  OPEN curRep FOR
		  SELECT PL.COD_PROD			   					   	   	 				|| '�' ||
		         NVL(P.DESC_PROD,' ')  		   	   	 							|| '�' ||
             p.desc_unid_present|| '�' ||
				     L.NOM_LAB															|| '�' ||
			   	   TO_CHAR(SUM(NVL(CANT_ATENDIDA/VAL_FRAC,0)),'999,990.00') 	|| '�' ||
		   	     TO_CHAR(SUM(NVL(VAL_PREC_TOTAL,0)),'999,990.00') 			|| '�' ||
			   	   to_char(PL.STK_FISICO/VAL_FRAC,'999,990.00')
      FROM 	 VTA_PEDIDO_VTA_DET VD,
	 	       	 LGT_PROD_LOCAL PL,
			   	   LGT_LAB L,
			   	   LGT_PROD P,
			   	   VTA_PEDIDO_VTA_CAB VPC
		  WHERE VD.COD_GRUPO_CIA=cCodGrupoCia_in  AND
	  	  		VD.COD_LOCAL=cCodLocal_in 	      AND
	  			  VD.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
	  			  AND TO_DATE(cFechaFin||' 23:59:59','dd/MM/yyyy HH24:mi:ss')   AND
				    VPC.EST_PED_VTA=EST_PED_COBRADO  					AND
            pl.cod_prod IN (SELECT p.cod_prod FROM lgt_prod_virtual p) AND
			      VPC.COD_GRUPO_CIA = VD.COD_GRUPO_CIA AND
				    VPC.COD_LOCAL= VD.COD_LOCAL 			AND
			      VPC.NUM_PED_VTA = VD.NUM_PED_VTA		AND
	  			  VD.COD_GRUPO_CIA=PL.COD_GRUPO_CIA AND
	  			  VD.COD_LOCAL=PL.COD_LOCAL         AND
	  			  VD.COD_PROD=PL.COD_PROD			  AND
				    P.COD_GRUPO_CIA=VD.COD_GRUPO_CIA  AND
				    P.COD_PROD = VD.COD_PROD		  AND
				    L.COD_LAB=P.COD_LAB
		        GROUP BY PL.COD_PROD,P.DESC_PROD,p.desc_unid_present,L.NOM_LAB,to_char(PL.STK_FISICO/VAL_FRAC,'999,990.00');
	RETURN curRep;

  END;

  PROCEDURE REPORTE_BORRAR_DET_FALTA_CERO(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodProd_in     IN CHAR,
                                          cFecha_in       IN CHAR,
                                          cSecUsuLocal_in IN CHAR) IS

  BEGIN
    DELETE FROM LGT_PROD_LOCAL_FALTA_STK
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL     = cCodLocal_in
    AND    COD_PROD      = cCodProd_in
    AND    SEC_USU_LOCAL = cSecUsuLocal_in
    AND    TO_CHAR(FEC_CREA_PROD_LOCAL_FALTA_STK,'yyyyMMddHH24MISS') = cFecha_in;
  END REPORTE_BORRAR_DET_FALTA_CERO;

  FUNCTION REPORTE_PEDIDOS_ANUL_NO_COB(cCodGrupoCia_in	IN CHAR,
  		   						                   cCodLocal_in	  	IN CHAR,
  		   						                   cFechaInicio     IN CHAR,
 		  						                     cFechaFin        IN CHAR) RETURN FarmaCursor
  IS
    curPed FarmaCursor;
  BEGIN
       OPEN curPed FOR
          SELECT VPC.NUM_PED_VTA                                              || '�' ||
                 --TO_CHAR(VPC.FEC_CREA_PED_VTA_CAB,'dd/MM/yyyy HH24:MI:SS')    || '�' ||
       					 TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || '�' ||
                 NVL(VPC.NOM_CLI_PED_VTA,' ')                                 || '�' ||
                 TO_CHAR(VPC.VAL_NETO_PED_VTA,'999,990.00')                   || '�' ||
                 NVL(VPC.RUC_CLI_PED_VTA,' ')                                 || '�' ||
                 NVL(VPC.DIR_CLI_PED_VTA,' ')                                 || '�' ||
                 NVL(VPC.USU_CREA_PED_VTA_CAB,' ')
          FROM   VTA_PEDIDO_VTA_CAB VPC
          WHERE  VPC.COD_GRUPO_CIA   = cCodGrupoCia_in
          AND    VPC.COD_LOCAL       = cCodLocal_in
          AND    VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                 AND     TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND    VPC.IND_PEDIDO_ANUL = C_C_INDICADOR_SI
          AND    VPC.SEC_MOV_CAJA    IS NULL;
      RETURN curPed;
  END;

  FUNCTION REPORTE_UNID_VTA_LOCAL_FILTRO(cTipoFiltro_in  IN CHAR,
		   						                       cCodFiltro_in 	 IN CHAR) RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
       IF(cTipoFiltro_in = g_nTipoFiltroPrincAct) THEN --principio activo
          OPEN curVta FOR
            SELECT  VISTA.CODIGO                                     || '�' ||
                    VISTA.PRODUCTO                                   || '�' ||
                    VISTA.UNIDAD                                     || '�' ||
                    VISTA.LABORATORIO                                || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.STK,'999,990')),' ')            || '�' ||
                    VISTA.FARMA                                            || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.PER_VTA,'999,990')),' ')        || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_PER,'999,990')),' ')   || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.DIAS_VTA_REP,'999,990')),' ')   || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_REP,'999,990')),' ')   || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.PROM_VTA,'999,990')),' ')       || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.DIAS_INV,'999,990')),' ')
            FROM    V_REP_UNID_VTA_LOCAL_90_DIAS VISTA,
                    LGT_PRINC_ACT_PROD           PAP
            WHERE   PAP.COD_PRINC_ACT    = cCodFiltro_in
            AND	    VISTA.CODIGO         = PAP.COD_PROD;
            RETURN curVta;
       ELSIF(cTipoFiltro_in = g_nTipoFiltroAccTerap) THEN --accion terapeutica
          OPEN curVta FOR
            SELECT  VISTA.CODIGO                                     || '�' ||
                    VISTA.PRODUCTO                                   || '�' ||
                    VISTA.UNIDAD                                     || '�' ||
                    VISTA.LABORATORIO                                || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.STK,'999,990')),' ')            || '�' ||
                    VISTA.FARMA                                            || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.PER_VTA,'999,990')),' ')        || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_PER,'999,990')),' ')   || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.DIAS_VTA_REP,'999,990')),' ')   || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_REP,'999,990')),' ')   || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.PROM_VTA,'999,990')),' ')       || '�' ||
                    NVL(TRIM(TO_CHAR(VISTA.DIAS_INV,'999,990')),' ')
            FROM    V_REP_UNID_VTA_LOCAL_90_DIAS VISTA,
                    LGT_ACC_TERAP 	   AT,
					          LGT_ACC_TERAP_PROD ATP
            WHERE   AT.COD_ACC_TERAP  = cCodFiltro_in
            AND	    VISTA.CODIGO      = ATP.COD_PROD
			      AND	    ATP.COD_ACC_TERAP = AT.COD_ACC_TERAP;
            RETURN curVta;
       ELSIF(cTipoFiltro_in = g_nTipoFiltroLab) THEN --laboratorio
           OPEN curVta FOR
              SELECT  VISTA.CODIGO                                     || '�' ||
                      VISTA.PRODUCTO                                   || '�' ||
                      VISTA.UNIDAD                                     || '�' ||
                      VISTA.LABORATORIO                                || '�' ||
                      NVL(TRIM(TO_CHAR(VISTA.STK,'999,990')),' ')            || '�' ||
                      VISTA.FARMA                                            || '�' ||
                      NVL(TRIM(TO_CHAR(VISTA.PER_VTA,'999,990')),' ')        || '�' ||
                      NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_PER,'999,990')),' ')   || '�' ||
                      NVL(TRIM(TO_CHAR(VISTA.DIAS_VTA_REP,'999,990')),' ')   || '�' ||
                      NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_REP,'999,990')),' ')   || '�' ||
                      NVL(TRIM(TO_CHAR(VISTA.PROM_VTA,'999,990')),' ')       || '�' ||
                      NVL(TRIM(TO_CHAR(VISTA.DIAS_INV,'999,990')),' ')
              FROM    V_REP_UNID_VTA_LOCAL_90_DIAS VISTA,
                      LGT_PROD                     PROD
              WHERE   PROD.COD_LAB  = cCodFiltro_in
              AND	    VISTA.CODIGO  = PROD.COD_PROD;
              RETURN curVta;
       END IF;
  END REPORTE_UNID_VTA_LOCAL_FILTRO;

--------------------------------------------------------------------------------------------------


  FUNCTION REPORTE_UNID_VTA_LOCAL
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
       OPEN curVta FOR
            SELECT  VISTA.CODIGO                                     || '�' ||
                    VISTA.PRODUCTO                                   || '�' ||
                    VISTA.UNIDAD                                     || '�' ||
                    VISTA.LABORATORIO                                || '�' ||
                    NVL(TO_CHAR(VISTA.STK,'999,990'),' ')            || '�' ||
                    VISTA.FARMA                                      || '�' ||
                    NVL(TO_CHAR(VISTA.PER_VTA,'999,990'),' ')        || '�' ||
                    NVL(TO_CHAR(VISTA.UNID_VTA_PER,'999,990'),' ')   || '�' ||
                    NVL(TO_CHAR(VISTA.DIAS_VTA_REP,'999,990'),' ')   || '�' ||
                    NVL(TO_CHAR(VISTA.UNID_VTA_REP,'999,990'),' ')   || '�' ||
                    NVL(TO_CHAR(VISTA.PROM_VTA,'999,990'),' ')       || '�' ||
                    NVL(TO_CHAR(VISTA.DIAS_INV,'999,990'),' ')
            FROM    V_REP_UNID_VTA_LOCAL_90_DIAS VISTA;
       RETURN curVta;
  END REPORTE_UNID_VTA_LOCAL;

--------------------------------------------------------------------------------------------------


  FUNCTION REPORTE_PROD_SIN_VTA_N_DIAS(cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
       OPEN curVta FOR
         SELECT      VISTA.CODIGO                                     || '�' ||
                     VISTA.PRODUCTO                                   || '�' ||
                     VISTA.UNIDAD                                     || '�' ||
                     VISTA.LABORATORIO                                || '�' ||
                     NVL(TO_CHAR(VISTA.STK,'999,990.00'),'-')            || '�' ||
                     VISTA.FARMA                                      || '�' ||
                     NVL(TO_CHAR(VISTA.PER_SIN_VTA,'999,990'),'-')    || '�' ||
                     NVL(TO_CHAR(VISTA.PER_CON_STK,'999,990'),'-')    || '�' ||
                     --NVL(TO_CHAR(VISTA.STK_LIBRE_UTIL,'999,990'),' ') || '�' ||
                     --NVL(TO_CHAR(VISTA.STK_TRASLADO,'999,990'),' ')   || '�' ||
                     NVL(TO_CHAR(VISTA.UNID_VTA,'999,990'),'-')       || '�' ||
                     NVL(TO_CHAR(VISTA.PER_VTA,'999,990'),'-')        || '�' ||
                     NVL(TO_CHAR(VISTA.UNID_VTA_PER,'999,990'),'-')   || '�' ||
                     NVL(TO_CHAR(VISTA.NUM_RESULTADO,'999,990'),'-')  || '�' ||
                     VISTA.RESULTADO
         FROM        V_PROD_SIN_VTA_N_DIAS_PTOVTA VISTA
         WHERE       VISTA.CODLOCAL   = cCodLocal_in;
       RETURN curVta;

  END REPORTE_PROD_SIN_VTA_N_DIAS;

--------------------------------------------------------------------------------------------------


  FUNCTION REPORTE_PROD_SIN_VTA_NDIAS_FIL(cCodLocal_in    IN CHAR,
                                          cTipoFiltro_in  IN CHAR,
		   						                        cCodFiltro_in 	IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
       IF(cTipoFiltro_in = g_nTipoFiltroPrincAct) THEN --principio activo
           OPEN curVta FOR
             SELECT      VISTA.CODIGO          || '�' ||
                         VISTA.PRODUCTO        || '�' ||
                         VISTA.UNIDAD          || '�' ||
                         VISTA.LABORATORIO     || '�' ||
                         NVL(TRIM(TO_CHAR(VISTA.STK,'999,990.00')),'-')            || '�' ||
                         VISTA.FARMA                                            || '�' ||
                         NVL(TRIM(TO_CHAR(VISTA.PER_SIN_VTA,'999,990')),'-')    || '�' ||
                         NVL(TRIM(TO_CHAR(VISTA.PER_CON_STK,'999,990')),'-')    || '�' ||
                         --NVL(TRIM(TO_CHAR(VISTA.STK_LIBRE_UTIL,'999,990')),' ') || '�' ||
                         --NVL(TRIM(TO_CHAR(VISTA.STK_TRASLADO,'999,990')),' ')   || '�' ||
                         NVL(TRIM(TO_CHAR(VISTA.UNID_VTA,'999,990')),'-')       || '�' ||
                         NVL(TRIM(TO_CHAR(VISTA.PER_VTA,'999,990')),'-')        || '�' ||
                         NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_PER,'999,990')),'-')    --|| '�' ||
                         --NVL(TRIM(TO_CHAR(VISTA.NUM_RESULTADO,'999,990')),' ')   || '�' ||
                         --VISTA.RESULTADO
             FROM        V_PROD_SIN_VTA_N_DIAS_PTOVTA VISTA,
                         LGT_PRINC_ACT_PROD        PAP
             WHERE       VISTA.CODLOCAL       = cCodLocal_in
             AND         PAP.COD_PRINC_ACT    = cCodFiltro_in
             AND	       VISTA.CODIGO         = PAP.COD_PROD;
           RETURN curVta;
        ELSIF(cTipoFiltro_in = g_nTipoFiltroAccTerap) THEN --accion terapeutica
           OPEN curVta FOR
               SELECT      VISTA.CODIGO          || '�' ||
                           VISTA.PRODUCTO        || '�' ||
                           VISTA.UNIDAD          || '�' ||
                           VISTA.LABORATORIO     || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.STK,'999,990.00')),'-')            || '�' ||
                           VISTA.FARMA                                            || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.PER_SIN_VTA,'999,990')),'-')    || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.PER_CON_STK,'999,990')),'-')    || '�' ||
                           --NVL(TRIM(TO_CHAR(VISTA.STK_LIBRE_UTIL,'999,990')),' ') || '�' ||
                           --NVL(TRIM(TO_CHAR(VISTA.STK_TRASLADO,'999,990')),' ')   || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.UNID_VTA,'999,990')),'-')       || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.PER_VTA,'999,990')),'-')        || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_PER,'999,990')),'-')    --|| '�' ||
                           --NVL(TRIM(TO_CHAR(VISTA.NUM_RESULTADO,'999,990')),' ')   || '�' ||
                           --VISTA.RESULTADO
               FROM        V_PROD_SIN_VTA_N_DIAS_PTOVTA VISTA,
                           LGT_ACC_TERAP 	           AT,
					                 LGT_ACC_TERAP_PROD        ATP
               WHERE       AT.COD_ACC_TERAP  = cCodFiltro_in
               AND	       VISTA.CODIGO      = ATP.COD_PROD
			         AND	       ATP.COD_ACC_TERAP = AT.COD_ACC_TERAP;
           RETURN curVta;
        ELSIF(cTipoFiltro_in = g_nTipoFiltroLab) THEN --laboratorio
           OPEN curVta FOR
               SELECT      VISTA.CODIGO          || '�' ||
                           VISTA.PRODUCTO        || '�' ||
                           VISTA.UNIDAD          || '�' ||
                           VISTA.LABORATORIO     || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.STK,'999,990.00')),'-')            || '�' ||
                           VISTA.FARMA                                            || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.PER_SIN_VTA,'999,990')),'-')    || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.PER_CON_STK,'999,990')),'-')    || '�' ||
                           --NVL(TRIM(TO_CHAR(VISTA.STK_LIBRE_UTIL,'999,990')),' ') || '�' ||
                           --NVL(TRIM(TO_CHAR(VISTA.STK_TRASLADO,'999,990')),' ')   || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.UNID_VTA,'999,990')),'-')       || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.PER_VTA,'999,990')),'-')        || '�' ||
                           NVL(TRIM(TO_CHAR(VISTA.UNID_VTA_PER,'999,990')),'-')    --|| '�' ||
                           --NVL(TRIM(TO_CHAR(VISTA.NUM_RESULTADO,'999,990')),' ')   || '�' ||
                           --VISTA.RESULTADO
               FROM        V_PROD_SIN_VTA_N_DIAS_PTOVTA VISTA,
                           LGT_PROD                  PROD
               WHERE       PROD.COD_LAB  = cCodFiltro_in
               AND	       VISTA.CODIGO  = PROD.COD_PROD;
           RETURN curVta;
         END IF;
  END REPORTE_PROD_SIN_VTA_NDIAS_FIL;

  FUNCTION REPORTE_DETALLE_FORMAS_PAGO(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in	  	IN CHAR,
  		   							                 cNumPedVta IN CHAR)
  RETURN FarmaCursor
  IS
  curRep FarmaCursor;
  BEGIN
 	  OPEN curRep FOR
	  	   select a.num_ped_vta  || '�' ||
         b.desc_forma_pago  || '�' ||
         TO_CHAR(a.im_pago,'999,990.000') || '�' ||
         to_char(case when a.tip_moneda='01' then 'SOLES' when tip_moneda='02' then 'DOLARES' end)  || '�' ||
         TO_CHAR(a.val_vuelto,'999,990.000') || '�' ||
         TO_CHAR(a.im_total_pago,'999,990.000') || '�' ||
         a.usu_crea_forma_pago_ped
         from vta_forma_pago_pedido a,
              vta_forma_pago b
         where a.cod_forma_pago=b.cod_forma_pago
           AND     a.COD_GRUPO_CIA = cCodGrupoCia_in
	         AND	   a.cod_local = cCodLocal_in
	         AND	   a.num_ped_vta=cNumPedVta;

	   RETURN curRep;

   END;
   
  FUNCTION NUMERO_DIAS_SIN_VENTAS
  RETURN CHAR
  IS
  num_dias char(10);
  BEGIN
        select P.LLAVE_TAB_GRAL  into num_dias
         from PBL_TAB_GRAL P
         WHERE
            P.ID_TAB_GRAL = C_CID_TAB_GRAL_NUM_DIAS ;

    return num_dias;
  END;
/*----------------------------------------------------------------------------------------------------------------------------------------
Goal : Carga Auxliar de Ventas x Rango de Fechas (Maximo 40 Dias)
History : 23-JUN-14    TCT  Procesa por Rango de Fechas
-----------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_LOAD_VENTAS_VEN_LOC(cCodGrupoCia_in   IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cFecha_Ini_in   IN CHAR,
                                    cFecha_Fin_in   IN CHAR DEFAULT
                                    TO_CHAR(SYSDATE - 1,'dd/mm/yyyy') )
  IS
  BEGIN

  --ELIMINA LOS REGISTROS Q ESTAN EN ESTA FECHA
  --DELETE VTA_RES_VTA_VEND_LOCAL R
/*  DELETE AUX_VTA_VEND_LOCAL R
  WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    R.COD_LOCAL     = cCodLocal_in
  AND    R.FEC_DIA_VENTA BETWEEN  TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                         AND      TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');*/

	--ERIOS 2.3.3 Se agrega el tipo GP
      --INSERT INTO VTA_RES_VTA_VEND_LOCAL
      
  --- 10.- Elimina Datos Antiguos    
   DELETE TMP_VTA_VEND_LOCAL R
   WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
     AND  R.COD_LOCAL     = cCodLocal_in
     AND  R.FEC_DIA_VENTA BETWEEN  TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                         AND      TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');
                         
  --- 20.- Carga Nuevos Datos                        
     INSERT INTO TMP_VTA_VEND_LOCAL
				 (
         COD_GRUPO_CIA, COD_LOCAL, SEC_USU_LOCAL, FEC_DIA_VENTA,
         MON_TOT_C_IGV,
         MON_TOT_S_IGV,
         MON_TOT_G,
		     MON_TOT_GP,
         MON_TOT_GG,
         MON_TOT_OTROS,
         MON_TOT_SERV,
         CANT_PED,
         CANT_PED_ANUL,
         MON_TOT_FARMA,
         MON_TOT_NO_FARMA,
         TOT_COM
         )
      (
        SELECT
              VTA.COD_GRUPO_CIA, VTA.COD_LOCAL, VTA.SEC_USU_LOCAL, VTA.FECHA,
              NVL(VTA.MON_TOT_CIGV,0) MON_TOT_CIGV,
              NVL(VTA.MON_TOT_SIGV,0) MON_TOT_SIGV,
              NVL(VTA.MON_G,0) TOT_G,
			        NVL(VTA.MON_GP,0) TOT_GP,
              NVL(VTA.MON_GG,0) TOT_GG,
              NVL(VTA.MON_OTROS,0) TOT_OTROS,
              NVL(VTA.MON_S,0) TOT_S,
              (VTA.CANT_PED - NVL(PED_NEG.CANT_PED,0)) CANT_PED,
              NVL(PED_NEG.CANT_PED,0) CANT_PED_ANUL,
              NVL(MON_FARMA,0),
              NVL(MON_NO_FARMA,0),
              NVL(TOT_COM, 0)
        FROM
           (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   SUM(DET.VAL_PREC_TOTAL) MON_TOT_CIGV,
                   SUM(DET.VAL_PREC_TOTAL / (1+VAL_IGV/100)) MON_TOT_SIGV,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_G,
				   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GP' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GP,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GG,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) IS NULL THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_OTROS,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'S' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_S,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'S' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_FARMA,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'N' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_NO_FARMA,
                   SUM
                      (
                       CASE
                            WHEN DET.PORC_ZAN IS NOT NULL THEN (DET.PORC_ZAN * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
							--ERIOS 21.04.2014 No deberia entrar en esta logica
                            WHEN TRIM(DET.IND_ZAN) IS NULL THEN (0.4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'G' THEN (4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN (8 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                       END
                       ) "TOT_COM"
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD P--,

--                   LGT_PROD_ZAN_LOCAL ZL
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.EST_PED_VTA = 'C'
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
              AND  P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
              AND  P.COD_PROD = DET.COD_PROD
            --   AND  CAB.TIP_PED_VTA = '01'--TIPO VENTA

--              AND  ZL.COD_PROD(+) = P.COD_PROD
            GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) VTA,
            (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.EST_PED_VTA = 'C'
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.VAL_NETO_PED_VTA < 0      -- CUENTO S�LO PEDIDOS NEGATIVOS
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
          -- AND  CAB.TIP_PED_VTA = '01'--TIPO VENTA
             GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) PED_NEG
    WHERE VTA.COD_GRUPO_CIA    = cCodGrupoCia_in
         AND VTA.COD_GRUPO_CIA = PED_NEG.COD_GRUPO_CIA(+)
         AND VTA.COD_LOCAL     = PED_NEG.COD_LOCAL(+)
         AND VTA.SEC_USU_LOCAL = PED_NEG.SEC_USU_LOCAL(+)
         AND VTA.FECHA         = PED_NEG.FECHA(+)
      );


     COMMIT;
 END;

------------------------------------------------------------------------------------------------
  FUNCTION REPORTE_VENTAS_VENDEDOR_IMP(cCodGrupoCia_in	IN CHAR,
       		   					   	             cCodLocal_in     IN CHAR,
			                       					 cFechaInicio 	  IN CHAR,
 		  						                     cFechaFin 		    IN CHAR
                                       )
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
   cPorc_GG    number;
   cPorc_G     number;
   cPorc_Otros number;
   cPorc_Calc  number;
  BEGIN

  	OPEN curRep FOR
    SELECT V.A||'�'||ROW_NUMBER()OVER(ORDER BY V.S DESC)
    FROM
    (
      SELECT NVL(USU.COD_TRAB,' ')  || '�' ||
       	     USU.NOM_USU ||' '|| USU.APE_PAT ||' '|| NVL(USU.APE_MAT,' ')  || '�' ||

             nvl(
             CASE
               WHEN SUM(V.MON_TOT_S_IGV) = 0 THEN TO_CHAR(0,'999,990.00')
               ELSE TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00')
               END
               ,' ')|| '�' ||
             nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV),'999,990.00') ,' ') A,
               CASE
               WHEN SUM(V.MON_TOT_S_IGV) = 0 THEN 0
               ELSE
                   DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G)*100/SUM(V.MON_TOT_S_IGV))
               END S
      FROM   VTA_RES_VTA_VEND_LOCAL V,
             PBL_USU_LOCAL USU
      WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    V.COD_LOCAL     = cCodLocal_in
      AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                             AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
      AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
      AND    V.COD_LOCAL     = USU.COD_LOCAL
      AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
  		GROUP BY NVL(USU.COD_TRAB,' '),
                USU.NOM_USU , USU.APE_PAT , NVL(USU.APE_MAT,' ')
    ) V;

		RETURN curRep;

    END;

/*---------------------------------------------------------------------------------------------------------------------------------------
GOAL : Carga Temporales de Ventas
History : 24-JUN-14   TCT   Agrega Tablas Temporales y Rango de Fechas
----------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE ACT_RES_VENTAS_VENDEDOR_TIPO(
                                        cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR,
                                        cFecha_Ini_in       IN CHAR,
                                        cFecha_Fin_in       IN CHAR,
                                        cTipoVenta          IN CHAR
                                       )

  IS
  BEGIN



     IF(cTipoVenta = C_C_TIPO_VENTA_MEZON) THEN
/*        DELETE VTA_RES_VTA_VEND_LOCAL_MEZON R
        WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    R.COD_LOCAL     = cCodLocal_in
        AND    R.FEC_DIA_VENTA BETWEEN  TO_DATE(cFecha_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                               AND      TO_DATE(cFecha_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');*/
                               
        DELETE TMP_RES_VTA_VEND_LOCAL_MEZON R
        WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    R.COD_LOCAL     = cCodLocal_in
        AND    R.FEC_DIA_VENTA BETWEEN  TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                               AND      TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');                          

      INSERT INTO TMP_RES_VTA_VEND_LOCAL_MEZON
				 (
         COD_GRUPO_CIA, COD_LOCAL, SEC_USU_LOCAL, FEC_DIA_VENTA,
         MON_TOT_C_IGV,
         MON_TOT_S_IGV,
         MON_TOT_G,
		     MON_TOT_GP,
         MON_TOT_GG,
         MON_TOT_OTROS,
         MON_TOT_SERV,
         CANT_PED,
         CANT_PED_ANUL,
         MON_TOT_FARMA,
         MON_TOT_NO_FARMA,
         TOT_COM
         )
      (
        SELECT
              VTA.COD_GRUPO_CIA, VTA.COD_LOCAL, VTA.SEC_USU_LOCAL, VTA.FECHA,
              NVL(VTA.MON_TOT_CIGV,0) MON_TOT_CIGV,
              NVL(VTA.MON_TOT_SIGV,0) MON_TOT_SIGV,
              NVL(VTA.MON_G,0) TOT_G,
			        NVL(VTA.MON_GP,0) TOT_GP,
              NVL(VTA.MON_GG,0) TOT_GG,
              NVL(VTA.MON_OTROS,0) TOT_OTROS,
              NVL(VTA.MON_S,0) TOT_S,
              (VTA.CANT_PED - NVL(PED_NEG.CANT_PED,0)) CANT_PED,
              NVL(PED_NEG.CANT_PED,0) CANT_PED_ANUL,
              NVL(MON_FARMA,0),
              NVL(MON_NO_FARMA,0),
              NVL(TOT_COM, 0)
        FROM
           (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   SUM(DET.VAL_PREC_TOTAL) MON_TOT_CIGV,
                   SUM(DET.VAL_PREC_TOTAL / (1+VAL_IGV/100)) MON_TOT_SIGV,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_G,
				           SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GP' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GP,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GG,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) IS NULL THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_OTROS,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'S' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_S,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'S' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_FARMA,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'N' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_NO_FARMA,
                   SUM
                      (
                       CASE
                            WHEN DET.PORC_ZAN IS NOT NULL THEN (DET.PORC_ZAN * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
							--ERIOS 21.04.2014 No deberia entrar en esta logica
                            WHEN TRIM(DET.IND_ZAN) IS NULL THEN (0.4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'G' THEN (4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN (8 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                       END
                       ) "TOT_COM"
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD P--,

--                   LGT_PROD_ZAN_LOCAL ZL
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.EST_PED_VTA = 'C'
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
              AND  P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
              AND  P.COD_PROD = DET.COD_PROD
              AND  CAB.TIP_PED_VTA = cTipoVenta--TIPO VENTA

--              AND  ZL.COD_PROD(+) = P.COD_PROD
            GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) VTA,
            (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.EST_PED_VTA = 'C'
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.VAL_NETO_PED_VTA < 0      -- CUENTO S�LO PEDIDOS NEGATIVOS
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND  CAB.TIP_PED_VTA = cTipoVenta--TIPO VENTA
             GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) PED_NEG
    WHERE
         VTA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND VTA.COD_GRUPO_CIA = PED_NEG.COD_GRUPO_CIA(+)
         AND VTA.COD_LOCAL = PED_NEG.COD_LOCAL(+)
         AND VTA.SEC_USU_LOCAL = PED_NEG.SEC_USU_LOCAL(+)
         AND VTA.FECHA = PED_NEG.FECHA(+)
      );


ELSIF  (cTipoVenta = C_C_TIPO_VENTA_DELIVERY) THEN
/*  DELETE VTA_RES_VTA_VEND_LOCAL_DEL R
  WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    R.COD_LOCAL     = cCodLocal_in
  AND    R.FEC_DIA_VENTA BETWEEN  TO_DATE(cFecha_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                         AND      TO_DATE(cFecha_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');*/
        DELETE TMP_RES_VTA_VEND_LOCAL_DEL R
        WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    R.COD_LOCAL     = cCodLocal_in
        AND    R.FEC_DIA_VENTA BETWEEN  TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                               AND      TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');                     


      INSERT INTO TMP_RES_VTA_VEND_LOCAL_DEL
				 (
         COD_GRUPO_CIA, COD_LOCAL, SEC_USU_LOCAL, FEC_DIA_VENTA,
         MON_TOT_C_IGV,
         MON_TOT_S_IGV,
         MON_TOT_G,
		     MON_TOT_GP,
         MON_TOT_GG,
         MON_TOT_OTROS,
         MON_TOT_SERV,
         CANT_PED,
         CANT_PED_ANUL,
         MON_TOT_FARMA,
         MON_TOT_NO_FARMA,
         TOT_COM
         )
      (
        SELECT
              VTA.COD_GRUPO_CIA, VTA.COD_LOCAL, VTA.SEC_USU_LOCAL, VTA.FECHA,
              NVL(VTA.MON_TOT_CIGV,0) MON_TOT_CIGV,
              NVL(VTA.MON_TOT_SIGV,0) MON_TOT_SIGV,
              NVL(VTA.MON_G,0) TOT_G,
			        NVL(VTA.MON_G,0) TOT_GP,
              NVL(VTA.MON_GG,0) TOT_GG,
              NVL(VTA.MON_OTROS,0) TOT_OTROS,
              NVL(VTA.MON_S,0) TOT_S,
              (VTA.CANT_PED - NVL(PED_NEG.CANT_PED,0)) CANT_PED,
              NVL(PED_NEG.CANT_PED,0) CANT_PED_ANUL,
              NVL(MON_FARMA,0),
              NVL(MON_NO_FARMA,0),
              NVL(TOT_COM, 0)
        FROM
           (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   SUM(DET.VAL_PREC_TOTAL) MON_TOT_CIGV,
                   SUM(DET.VAL_PREC_TOTAL / (1+VAL_IGV/100)) MON_TOT_SIGV,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_G,
				           SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GP' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GP,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GG,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) IS NULL THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_OTROS,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'S' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_S,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'S' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_FARMA,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'N' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_NO_FARMA,
                   SUM
                      (
                       CASE
                            WHEN DET.PORC_ZAN IS NOT NULL THEN (DET.PORC_ZAN * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
							--ERIOS 21.04.2014 No deberia entrar en esta logica
                            WHEN TRIM(DET.IND_ZAN) IS NULL THEN (0.4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'G' THEN (4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN (8 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                       END
                       ) "TOT_COM"
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD P--,

--                   LGT_PROD_ZAN_LOCAL ZL
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.EST_PED_VTA = 'C'
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
              AND  P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
              AND  P.COD_PROD = DET.COD_PROD
              AND  CAB.TIP_PED_VTA = cTipoVenta--TIPO VENTA

--              AND  ZL.COD_PROD(+) = P.COD_PROD
            GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) VTA,
            (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.EST_PED_VTA = 'C'
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.VAL_NETO_PED_VTA < 0      -- CUENTO S�LO PEDIDOS NEGATIVOS
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND  CAB.TIP_PED_VTA = cTipoVenta--TIPO VENTA
             GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) PED_NEG
    WHERE
         VTA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND VTA.COD_GRUPO_CIA = PED_NEG.COD_GRUPO_CIA(+)
         AND VTA.COD_LOCAL = PED_NEG.COD_LOCAL(+)
         AND VTA.SEC_USU_LOCAL = PED_NEG.SEC_USU_LOCAL(+)
         AND VTA.FECHA = PED_NEG.FECHA(+)
      );

   ELSIF  (cTipoVenta = C_C_TIPO_VENTA_INSTITUCIONAL) THEN

  -- RAISE_APPLICATION_ERROR(-20018,'ELIMINA:'||cFecha_in);

/*  DELETE TMP_RES_VTA_VEND_LOCAL_INS R
  WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    R.COD_LOCAL     = cCodLocal_in
  AND    R.FEC_DIA_VENTA BETWEEN  TO_DATE(cFecha_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                         AND      TO_DATE(cFecha_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');*/
                         
     DELETE TMP_RES_VTA_VEND_LOCAL_INS R
      WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    R.COD_LOCAL     = cCodLocal_in
        AND    R.FEC_DIA_VENTA BETWEEN  TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                               AND      TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss');                                              


      INSERT INTO TMP_RES_VTA_VEND_LOCAL_INS
				 (
         COD_GRUPO_CIA, COD_LOCAL, SEC_USU_LOCAL, FEC_DIA_VENTA,
         MON_TOT_C_IGV,
         MON_TOT_S_IGV,
         MON_TOT_G,
		     MON_TOT_GP,
         MON_TOT_GG,
         MON_TOT_OTROS,
         MON_TOT_SERV,
         CANT_PED,
         CANT_PED_ANUL,
         MON_TOT_FARMA,
         MON_TOT_NO_FARMA,
         TOT_COM
         )
      (
        SELECT
              VTA.COD_GRUPO_CIA, VTA.COD_LOCAL, VTA.SEC_USU_LOCAL, VTA.FECHA,
              NVL(VTA.MON_TOT_CIGV,0) MON_TOT_CIGV,
              NVL(VTA.MON_TOT_SIGV,0) MON_TOT_SIGV,
              NVL(VTA.MON_G,0) TOT_G,
			        NVL(VTA.MON_G,0) TOT_GP,
              NVL(VTA.MON_GG,0) TOT_GG,
              NVL(VTA.MON_OTROS,0) TOT_OTROS,
              NVL(VTA.MON_S,0) TOT_S,
              (VTA.CANT_PED - NVL(PED_NEG.CANT_PED,0)) CANT_PED,
              NVL(PED_NEG.CANT_PED,0) CANT_PED_ANUL,
              NVL(MON_FARMA,0),
              NVL(MON_NO_FARMA,0),
              NVL(TOT_COM, 0)
        FROM
           (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   SUM(DET.VAL_PREC_TOTAL) MON_TOT_CIGV,
                   SUM(DET.VAL_PREC_TOTAL / (1+VAL_IGV/100)) MON_TOT_SIGV,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_G,
				           SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GP' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GP,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GG,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) IS NULL THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_OTROS,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'S' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_S,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'S' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_FARMA,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'N' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_NO_FARMA,
                   SUM
                      (
                       CASE
                            WHEN DET.PORC_ZAN IS NOT NULL THEN (DET.PORC_ZAN * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
							--ERIOS 21.04.2014 No deberia entrar en esta logica
                            WHEN TRIM(DET.IND_ZAN) IS NULL THEN (0.4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'G' THEN (4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN (8 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                       END
                       ) "TOT_COM"
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD P--,

--                   LGT_PROD_ZAN_LOCAL ZL
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.EST_PED_VTA = 'C'
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
              AND  P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
              AND  P.COD_PROD = DET.COD_PROD
              AND  CAB.TIP_PED_VTA = cTipoVenta--TIPO VENTA

--              AND  ZL.COD_PROD(+) = P.COD_PROD
            GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) VTA,
            (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.EST_PED_VTA = 'C'
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.VAL_NETO_PED_VTA < 0      -- CUENTO S�LO PEDIDOS NEGATIVOS
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND  CAB.TIP_PED_VTA = cTipoVenta--TIPO VENTA
             GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) PED_NEG
    WHERE
         VTA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND VTA.COD_GRUPO_CIA = PED_NEG.COD_GRUPO_CIA(+)
         AND VTA.COD_LOCAL = PED_NEG.COD_LOCAL(+)
         AND VTA.SEC_USU_LOCAL = PED_NEG.SEC_USU_LOCAL(+)
         AND VTA.FECHA = PED_NEG.FECHA(+)
      );

      END IF ;
      COMMIT;

 END ACT_RES_VENTAS_VENDEDOR_TIPO;


/* -------------------------------------------------------------------------------------------------------------------------------------
GOAL : Generar Reportes por Rango de Fechas (m�ximo 40 dias) y Tipo
History : 24-JUN-14   TCT   modifica para uso de global temporary tables en carga
---------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION REPORTE_VENTAS_POR_VEND_TIPO(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in     IN CHAR,
 			   						                   cFechaInicio 	IN CHAR,
 		  						                     cFechaFin 		IN CHAR,
                                       cTipoVenta  IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
   cPorc_GG    number;
   cPorc_G     number;
   cPorc_Otros number;
   cPorc_Calc  number;

-- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
   nValNeto           NUMBER(15,3);
   nValNetoSIGV       NUMBER(15,3);

   -- dubilluz 03.04.2014
   nAnio_v integer;
   nMes_v  integer;
   nCalidadInv number;
   nAtencionCliente number;
   -- dubilluz 03.04.2014
   vn_Dias_Dif NUMBER;

  BEGIN
   -- 10.- Verificar Candidad de Dias en Rango de Fechas
   BEGIN
     SELECT TO_NUMBER(G.LLAVE_TAB_GRAL)
     INTO VN_MAX_DIAS_REP
     FROM PBL_TAB_GRAL G
     WHERE G.ID_TAB_GRAL  = 520
      AND G.COD_APL      = 'PTO_VENTA'
      AND G.COD_TAB_GRAL = 'MAX_DIAS_REP_VEND'
      AND G.EST_TAB_GRAL = 'A';
   EXCEPTION
    WHEN OTHERS THEN  
     VN_MAX_DIAS_REP:=40;
   END; 
   
   vn_Dias_Dif:= abs(to_date(cFechaFin,'dd/MM/yyyy') - to_date(cFechaInicio,'dd/MM/yyyy'));
   IF vn_Dias_Dif > VN_MAX_DIAS_REP THEN
    raise_application_error(-20001,'ERROR, La cantidad M�xima de D�as a procesar es :'||VN_MAX_DIAS_REP);

   END IF;

   -- 20.- Carga de Temporales x Rango de fechas
   ACT_RES_VENTAS_VENDEDOR_TIPO(cCodGrupoCia_in,cCodLocal_in,cFechaInicio,cFechaFin,cTipoVenta);

   -- dubilluz 03.04.2014
   select to_number(to_char(to_date(cFechaInicio,'dd/mm/yyyy'),'yyyy')),
          to_number(to_char(to_date(cFechaInicio,'dd/mm/yyyy'),'mm'))
   into   nAnio_v,nMes_v
   from   dual;
   nAtencionCliente := fn_dev_meta('018',nAnio_v,nMes_v,cCodLocal_in);
   nCalidadInv      := fn_dev_meta('019',nAnio_v,nMes_v,cCodLocal_in);
   -- dubilluz 03.04.2014


  --SZ CONSULTA SI LA FECHA FINAL ES MAYOR O IGUAL A LA ACTUAL DEL SISTEMA
/*  IF  cFechaFin >= TO_CHAR(SYSDATE,'DD/MM/YYYY')   THEN
      ACT_RES_VENTAS_VENDEDOR_TIPO(cCodGrupoCia_in,cCodLocal_in,cFechaInicio,cFechaFin,cTipoVenta);
  END IF;*/

    IF(cTipoVenta = C_C_TIPO_VENTA_MEZON) THEN

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_GG
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 205
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_G
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 206
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_Otros
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 207
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_Calc
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 208
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    -- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
          SELECT SUM(C.VAL_NETO_PED_VTA) T_C_IGV,
                 SUM(C.VAL_NETO_PED_VTA - C.VAL_IGV_PED_VTA) T_S_IGV
          INTO nValNeto, nValNetoSIGV
          FROM VTA_PEDIDO_VTA_CAB C
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL     = cCodLocal_in
          AND    C.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                                 AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
          AND    C.EST_PED_VTA = 'C'
          AND    C.TIP_PED_VTA = C_C_TIPO_VENTA_MEZON;


      	OPEN curRep FOR

        SELECT NVL(USU.COD_TRAB,' ')  || '�' ||
         	     USU.NOM_USU ||' '|| USU.APE_PAT ||' '|| NVL(USU.APE_MAT,' ')  || '�' ||
          	   nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ') || '�' ||
           	   nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV),'999,990.00') ,' ') || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
			   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00') ,' ')|| '�' ||
               nvl(
                   CASE
                   WHEN SUM(V.MON_TOT_S_IGV) = 0 THEN TO_CHAR(0,'999,990.00')
                   ELSE
                   TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00')
                   END
               ,' ')|| '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
/*
               nvl(
                   CASE
                   WHEN SUM(V.MON_TOT_S_IGV) = 0 THEN TO_CHAR(0,'999,990.00')
                   ELSE
                     TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00')
                   END
                     ,' ') || '�' || -- CALCULADO
*/
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||
           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
               TO_CHAR('VENDEDOR') || '�' || -- COLUMNAS VACIAS
               USU.SEC_USU_LOCAL    || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ')|| '�' ||
               nvl(usu.login_usu,'')
        FROM   TMP_RES_VTA_VEND_LOCAL_MEZON V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
    		GROUP BY NVL(USU.COD_TRAB,' '),
                  USU.NOM_USU , USU.APE_PAT , NVL(USU.APE_MAT,' '),
                  USU.SEC_USU_LOCAL,usu.login_usu
        UNION
        SELECT ' '  || '�' ||
        	   	 'LOCAL S/.'  || '�' ||
    -- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
    /*
          	   nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00'),' ')  || '�' ||
           	   nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV),'999,990.00')  ,' ')|| '�' ||
    */
          	   nvl(TO_CHAR(nValNeto,'999,990.00'),' ')  || '�' ||
           	   nvl(TO_CHAR(nValNetoSIGV,'999,990.00')  ,' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
			   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00'),' ') || '�' ||
    /*
               nvl(
                   CASE
                   WHEN SUM(V.MON_TOT_S_IGV) = 0 THEN TO_CHAR(0,'999,990.00')
                   ELSE
                   TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00')
                   END
                   ,' ') || '�' ||
    */
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.00')
                   ELSE
                   TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.00')
                   END
                   ,' ') || '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--               nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||
           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
               TO_CHAR(' ') || '�' || -- COLUMNAS VACIAS
               ' '    || '�' ||
    --           nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV)-1,'999,990.00'),' ')|| '�' ||
               nvl(TO_CHAR(nValNeto-1,'999,990.00'),' ')|| '�' ||
               ' '
        FROM   TMP_RES_VTA_VEND_LOCAL_MEZON V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
        GROUP BY ' ','LOCAL S/'
        UNION
        SELECT ' '  || '�' ||
        	   	 'LOCAL %.'  || '�' ||
          	   ' '  || '�' ||
           	   ' '  || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                   TO_CHAR(SUM(V.MON_TOT_GG)*100/nValNetoSIGV,'999,990.000')
                   END,

                   ' ')   || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                   TO_CHAR(SUM(V.MON_TOT_G)*100/nValNetoSIGV,'999,990.000')
                   END,
                   ' ')    || '�' ||
			   nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                   TO_CHAR(SUM(V.MON_TOT_GP)*100/nValNetoSIGV,'999,990.000')
                   END,
                   ' ')    || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                   TO_CHAR(SUM(V.MON_TOT_OTROS)*100/nValNetoSIGV,'999,990.000')
                   END,
                    ' ')|| '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                   TO_CHAR(SUM(V.MON_TOT_SERV)*100/nValNetoSIGV,'999,990.000')
                   END
                   ,' ') || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                       TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_G)*100/nValNetoSIGV),'999,990.000')
                   END
                       ,' ') || '�' ||
               TO_CHAR(' ') || '�' || -- CALCULADO
           -- DUBILLUZ 02.04.2014
           -- INICIO
 TO_CHAR(' ') || '�' || -- CALCULADO
  TO_CHAR(' ') || '�' || -- CALCULADO
   TO_CHAR(' ') || '�' || -- CALCULADO
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||

               TO_CHAR('PORCENTAJE') || '�' || -- COLUMNAS QUE INDICA QUE SON PORCENTAJES ESTO SE UTILIZARA EN JAVA
               ' '    || '�' ||
               nvl(TO_CHAR(nValNeto,'999,990.00') ,' ')|| '�' ||

               ' '
        FROM   TMP_RES_VTA_VEND_LOCAL_MEZON V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
        GROUP BY ' ','LOCAL %.'
        ;


    		RETURN curRep;

  ELSIF(cTipoVenta = C_C_TIPO_VENTA_DELIVERY) THEN

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_GG
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 205
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_G
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 206
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_Otros
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 207
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_Calc
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 208
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    -- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
          SELECT SUM(C.VAL_NETO_PED_VTA) T_C_IGV,
                 SUM(C.VAL_NETO_PED_VTA - C.VAL_IGV_PED_VTA) T_S_IGV
          INTO nValNeto, nValNetoSIGV
          FROM VTA_PEDIDO_VTA_CAB C
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL     = cCodLocal_in
          AND    C.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                                 AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
          AND    C.EST_PED_VTA = 'C'
          AND    C.TIP_PED_VTA = C_C_TIPO_VENTA_DELIVERY;


      	OPEN curRep FOR

        SELECT NVL(USU.COD_TRAB,' ')  || '�' ||
         	     USU.NOM_USU ||' '|| USU.APE_PAT ||' '|| NVL(USU.APE_MAT,' ')  || '�' ||
          	   nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ') || '�' ||
           	   nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV),'999,990.00') ,' ') || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
			   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00') ,' ')|| '�' ||
               nvl(
                   CASE
                   WHEN SUM(V.MON_TOT_S_IGV) = 0 THEN TO_CHAR(0,'999,990.00')
                   ELSE
                   TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00')
                   END
                    ,' ')|| '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--               nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||
           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
               TO_CHAR('VENDEDOR') || '�' || -- COLUMNAS VACIAS
               USU.SEC_USU_LOCAL    || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ')|| '�' ||
               nvl(usu.login_usu,'')
        FROM   TMP_RES_VTA_VEND_LOCAL_DEL V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
    		GROUP BY NVL(USU.COD_TRAB,' '),
                  USU.NOM_USU , USU.APE_PAT , NVL(USU.APE_MAT,' '),
                  USU.SEC_USU_LOCAL,usu.login_usu
        UNION
        SELECT ' '  || '�' ||
        	   	 'LOCAL S/.'  || '�' ||
          	   nvl(TO_CHAR(nValNeto,'999,990.00'),' ')  || '�' ||
           	   nvl(TO_CHAR(nValNetoSIGV,'999,990.00')  ,' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
			   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00'),' ') || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.00')
                   ELSE
                   TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.00')
                   END
                   ,' ') || '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--               nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||
           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
               TO_CHAR(' ') || '�' || -- COLUMNAS VACIAS
               ' '    || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV)-1,'999,990.00'),' ')|| '�' ||
               ' '
        FROM   TMP_RES_VTA_VEND_LOCAL_DEL V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
        GROUP BY ' ','LOCAL S/'
        UNION
        SELECT ' '  || '�' ||
        	   	 'LOCAL %.'  || '�' ||
          	   ' '  || '�' ||
           	   ' '  || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                       TO_CHAR(SUM(V.MON_TOT_GG)*100/nValNetoSIGV,'999,990.000')
                   END ,' ')   || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                        TO_CHAR(SUM(V.MON_TOT_G)*100/nValNetoSIGV,'999,990.000')
                   END ,' ')    || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                        TO_CHAR(SUM(V.MON_TOT_GP)*100/nValNetoSIGV,'999,990.000')
                   END ,' ')    || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                   TO_CHAR(SUM(V.MON_TOT_OTROS)*100/nValNetoSIGV,'999,990.000')
                   END,
                   ' ')|| '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                       TO_CHAR(SUM(V.MON_TOT_SERV)*100/nValNetoSIGV,'999,990.000')
                   END ,' ') || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                    TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.000')
                   END
                    ,' ') || '�' ||
               TO_CHAR(' ') || '�' || -- CALCULADO
           -- DUBILLUZ 02.04.2014
           -- INICIO
               TO_CHAR(' ') || '�' || -- CALCULADO
               TO_CHAR(' ') || '�' || -- CALCULADO
               TO_CHAR(' ') || '�' || -- CALCULADO
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||

               TO_CHAR('PORCENTAJE') || '�' || -- COLUMNAS QUE INDICA QUE SON PORCENTAJES ESTO SE UTILIZARA EN JAVA
               ' '    || '�' ||
               nvl(TO_CHAR(nValNeto,'999,990.00') ,' ')|| '�' ||

               ' '
        FROM   TMP_RES_VTA_VEND_LOCAL_DEL V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
        GROUP BY ' ','LOCAL %.'
        ;


    		RETURN curRep;
  ELSIF(cTipoVenta = C_C_TIPO_VENTA_INSTITUCIONAL) THEN

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_GG
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 205
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_G
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 206
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_Otros
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 207
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

        SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
        INTO   cPorc_Calc
        FROM   PBL_TAB_GRAL T
        WHERE  T.ID_TAB_GRAL = 208
        AND    T.COD_APL = 'PTO_VENTA'
        AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    -- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
          SELECT SUM(C.VAL_NETO_PED_VTA) T_C_IGV,
                 SUM(C.VAL_NETO_PED_VTA - C.VAL_IGV_PED_VTA) T_S_IGV
          INTO nValNeto, nValNetoSIGV
          FROM VTA_PEDIDO_VTA_CAB C
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL     = cCodLocal_in
          AND    C.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                                 AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
          AND    C.EST_PED_VTA = 'C'
          AND    C.TIP_PED_VTA = C_C_TIPO_VENTA_INSTITUCIONAL;


      	OPEN curRep FOR

        SELECT NVL(USU.COD_TRAB,' ')  || '�' ||
         	     USU.NOM_USU ||' '|| USU.APE_PAT ||' '|| NVL(USU.APE_MAT,' ')  || '�' ||
          	   nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ') || '�' ||
           	   nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV),'999,990.00') ,' ') || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
			   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00') ,' ')|| '�' ||
               nvl(
                   CASE
                   WHEN SUM(V.MON_TOT_S_IGV) = 0 THEN TO_CHAR(0,'999,990.00')
                   ELSE
                        TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00')
                        END
                         ,' ')|| '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--               nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||
           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
               TO_CHAR('VENDEDOR') || '�' || -- COLUMNAS VACIAS
               USU.SEC_USU_LOCAL    || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ')|| '�' ||
               nvl(usu.login_usu,'')
        FROM   TMP_RES_VTA_VEND_LOCAL_INS V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
    		GROUP BY NVL(USU.COD_TRAB,' '),
                  USU.NOM_USU , USU.APE_PAT , NVL(USU.APE_MAT,' '),
                  USU.SEC_USU_LOCAL,usu.login_usu
        UNION
        SELECT ' '  || '�' ||
        	   	 'LOCAL S/.'  || '�' ||
          	   nvl(TO_CHAR(nValNeto,'999,990.00'),' ')  || '�' ||
           	   nvl(TO_CHAR(nValNetoSIGV,'999,990.00')  ,' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
			   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
               nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00'),' ') || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.00')
                   ELSE
                        TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.00')
                   END ,' ') || '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--               nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||
           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||


               TO_CHAR(' ') || '�' || -- COLUMNAS VACIAS
               ' '    || '�' ||
               nvl(TO_CHAR(nValNeto-1,'999,990.00'),' ')|| '�' ||
               ' '
        FROM   TMP_RES_VTA_VEND_LOCAL_INS V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
        GROUP BY ' ','LOCAL S/'
        UNION
        SELECT ' '  || '�' ||
        	   	 'LOCAL %.'  || '�' ||
          	   ' '  || '�' ||
           	   ' '  || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                       TO_CHAR(SUM(V.MON_TOT_GG)*100/nValNetoSIGV,'999,990.000')
                   END
                       ,' ')   || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                         TO_CHAR(SUM(V.MON_TOT_G)*100/nValNetoSIGV,'999,990.000')
                         END
                         ,' ')    || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                         TO_CHAR(SUM(V.MON_TOT_GP)*100/nValNetoSIGV,'999,990.000')
                         END
                         ,' ')    || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                         TO_CHAR(SUM(V.MON_TOT_OTROS)*100/nValNetoSIGV,'999,990.000')
                    END
                         ,' ')|| '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                        TO_CHAR(SUM(V.MON_TOT_SERV)*100/nValNetoSIGV,'999,990.000')
                   END,' ') || '�' ||
               nvl(
                   CASE
                   WHEN nValNetoSIGV = 0 THEN TO_CHAR(0,'999,990.000')
                   ELSE
                        TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.000')
                   END
                        ,' ') || '�' ||
               TO_CHAR(' ') || '�' || -- CALCULADO
           -- DUBILLUZ 02.04.2014
           -- INICIO
               TO_CHAR(' ') || '�' || -- CALCULADO
               TO_CHAR(' ') || '�' || -- CALCULADO
               TO_CHAR(' ') || '�' || -- CALCULADO
           --- FIN
               nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||

               TO_CHAR('PORCENTAJE') || '�' || -- COLUMNAS QUE INDICA QUE SON PORCENTAJES ESTO SE UTILIZARA EN JAVA
               ' '    || '�' ||
               nvl(TO_CHAR(nValNeto,'999,990.00') ,' ')|| '�' ||

               ' '
        FROM   TMP_RES_VTA_VEND_LOCAL_INS V,
               PBL_USU_LOCAL USU
        WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    V.COD_LOCAL     = cCodLocal_in
        AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                               AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
        AND    V.COD_LOCAL     = USU.COD_LOCAL
        AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
        GROUP BY ' ','LOCAL %.'
        ;


    		RETURN curRep;


 END IF;


END;


  -----------------------------------------------------------------------------------------
    FUNCTION REPORTE_DET_VENTAS_VEND_TIPO(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in     IN CHAR,
 			   						                   cFechaInicio 	  IN CHAR,
 		  						                     cFechaFin 		    IN CHAR,
									                     cUsuario_in      IN CHAR,
                                       cTipoVenta       IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
  		  OPEN curRep FOR
        SELECT  DET.NUM_PED_VTA                   || '�' ||
                PROD.COD_PROD                     || '�' ||
                NVL(PROD.DESC_PROD,' ')           || '�' ||
                DET.UNID_VTA                      || '�' ||
                TO_CHAR((DET.CANT_ATENDIDA ),'999,990.00') || '�' ||
                TO_CHAR(DET.VAL_PREC_TOTAL,'999,990.00')   || '�' ||
--                TO_CHAR(DET.VAL_TOTAL_BONO,'999,990.00')
                NVL(DET.IND_ZAN,'-')
        FROM    LGT_PROD PROD,
                VTA_PEDIDO_VTA_DET DET,
                VTA_PEDIDO_VTA_CAB CAB
        WHERE   DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND	    DET.COD_LOCAL = cCodLocal_in
        AND	    DET.FEC_CREA_PED_VTA_DET BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                                         AND	   TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND	    DET.SEC_USU_LOCAL = cUsuario_in
        --AND   CAB.IND_PEDIDO_ANUL<>C_C_INDICADOR_SI
        AND     CAB.EST_PED_VTA='C'
        AND	    PROD.COD_GRUPO_CIA=DET.COD_GRUPO_CIA
        AND	    PROD.COD_PROD = DET.COD_PROD
        AND     DET.COD_GRUPO_CIA=CAB.COD_GRUPO_CIA
        AND     DET.COD_LOCAL=CAB.COD_LOCAL
        AND     DET.NUM_PED_VTA=CAB.NUM_PED_VTA
        AND    CAB.tip_ped_vta = cTipoVenta;
        RETURN curRep;
  END;

  --Descripcion: Lista un reporte de guias presentes en el sistema
  --Fecha       Usuario		Comentario
  --28/MAR/2014 LLEIVA    Creacion
  FUNCTION REPORTE_GUIAS(cCodGrupoCia_in	 IN CHAR,
       		   					   cCodLocal_in      IN CHAR,
									       cFecInicial_in 	 IN CHAR,
 		  						       cFecFinal_in 	   IN CHAR,
									       cNumGuia_in       IN CHAR,
                         cTipoGuia_in      IN CHAR)
  RETURN FarmaCursor
  IS
      curRep FarmaCursor;
      query varchar2(5000);
  BEGIN
      query := query ||
               'select VCP.NUM_COMP_PAGO                       || ''�'' || ' ||
               'TO_CHAR(VCP.FEC_CREA_COMP_PAGO,''dd/mm/YYYY'') || ''�'' || ' ||
               'VCP.CANT_ITEM                                  || ''�'' || ' ||
               'VCP.VAL_BRUTO_COMP_PAGO                        || ''�'' || ' ||
               '''GUIAS COMP. PAGO'' '                                       ||
               ' from VTA_COMP_PAGO VCP '                                    ||
               'where VCP.TIP_COMP_PAGO = ''03'' '                           ||
               'and VCP.COD_GRUPO_CIA = '''|| cCodGrupoCia_in ||''' '        ||
               'and VCP.COD_LOCAL = '''|| cCodLocal_in ||''' ';
      IF (cFecInicial_in IS NOT NULL) THEN
         query := query || 'AND VCP.FEC_CREA_COMP_PAGO >= TO_DATE(''' || cFecInicial_in
                        || ' 00:00:00'',''dd/MM/yyyy HH24:mi:ss'') ';
      END IF;
      IF (cFecFinal_in IS NOT NULL) THEN
         query := query || 'AND VCP.FEC_CREA_COMP_PAGO <= TO_DATE(''' || cFecFinal_in
                        || ' 23:59:59'',''dd/MM/yyyy HH24:mi:ss'') ';
      END IF;
      IF (cNumGuia_in IS NOT NULL) THEN
         query := query || 'AND VCP.NUM_COMP_PAGO = '''|| cNumGuia_in ||''' ';
      END IF;
      query := query || 'UNION ';
      query := query ||
               'select LGR.NUM_GUIA_REM                        || ''�'' || ' ||
               'TO_CHAR(LGR.FEC_CREA_GUIA_REM,''dd/mm/YYYY'')  || ''�'' || ' ||
               'SUM(LNEC.CANT_ITEMS)                           || ''�'' || ' ||
               'SUM(LNEC.VAL_TOTAL_NOTA_ES_CAB)                || ''�'' || ' ||
               '''GUIAS REM.'' '                                             ||
               'from LGT_GUIA_REM LGR '                                      ||
               'inner join LGT_NOTA_ES_CAB LNEC on LNEC.COD_GRUPO_CIA = LGR.COD_GRUPO_CIA ' ||
               'AND LNEC.COD_LOCAL = LGR.COD_LOCAL '                                        ||
               'AND LNEC.NUM_NOTA_ES = LGR.NUM_NOTA_ES '                                    ||
               'where LNEC.TIP_DOC = ''03'' ';
      IF (cFecInicial_in IS NOT NULL) THEN
         query := query || 'AND LGR.FEC_CREA_GUIA_REM >= TO_DATE(''' || cFecInicial_in
                        || ' 00:00:00'',''dd/MM/yyyy HH24:mi:ss'') ';
      END IF;
      IF (cFecFinal_in IS NOT NULL) THEN
         query := query || 'AND LGR.FEC_CREA_GUIA_REM <= TO_DATE(''' || cFecFinal_in
                        || ' 23:59:59'',''dd/MM/yyyy HH24:mi:ss'') ';
      END IF;
      IF (cNumGuia_in IS NOT NULL) THEN
         query := query || 'AND LGR.NUM_GUIA_REM = '''|| cNumGuia_in ||''' ';
      END IF;
      query := query ||'group by LGR.NUM_GUIA_REM, LGR.FEC_CREA_GUIA_REM';

      DBMS_OUTPUT.put_line (query);
      OPEN curRep FOR query;

      return curRep;
  END;
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : Generar el Reporte de Ventas x Vendedor para un rango de fecha (Maximo 40 Dias)
History : 23-JUN-14  TCT  Agrega proceso para carga de temporal
-------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION REP_VENTAS_POR_VEND_TEST(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in     IN CHAR,
 			   						                   cFechaInicio 	IN CHAR,
 		  						                     cFechaFin 		IN CHAR)
                                       RETURN CHAR

IS
BEGIN
 /* RETURN FarmaCursor
  IS
   curRep FarmaCursor;
   cPorc_GG    number;
   cPorc_G     number;
   cPorc_Otros number;
   cPorc_Calc  number;

-- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
   nValNeto           NUMBER(15,3);
   nValNetoSIGV       NUMBER(15,3);
   -- dubilluz 03.04.2014
   nAnio_v integer;
   nMes_v  integer;
   nCalidadInv number;
   nAtencionCliente number;
   -- dubilluz 03.04.2014
   vn_Dias_Dif NUMBER;
  BEGIN

   -- 10.- Validacion de Rango de Fechas para Proceso de Ventas
    vn_Dias_Dif:= abs(to_date(cFechaFin,'dd/MM/yyyy') - to_date(cFechaInicio,'dd/MM/yyyy'));
    IF vn_Dias_Dif > 40 THEN
     raise_application_error(-20001,'ERROR, La Cantidad de Dias a Procesar no puede ser mas de 40 !!!');
    END IF;

   -- 20.- Carga Resumen de  Ventas para Rango de Fechas
   ptoventa_reporte_v10.sp_load_ventas_ven_loc(ccodgrupocia_in => cCodGrupoCia_in,
                                              ccodlocal_in => cCodLocal_in,
                                              cfecha_ini_in => cFechaInicio,
                                              cfecha_fin_in => cFechaFin);


   -- dubilluz 03.04.2014
   select to_number(to_char(to_date(cFechaInicio,'dd/mm/yyyy'),'yyyy')),
          to_number(to_char(to_date(cFechaInicio,'dd/mm/yyyy'),'mm'))
   into   nAnio_v,nMes_v
   from   dual;
   nAtencionCliente := fn_dev_meta('018',nAnio_v,nMes_v,cCodLocal_in);
   nCalidadInv      := fn_dev_meta('019',nAnio_v,nMes_v,cCodLocal_in);
   -- dubilluz 03.04.2014

  --SZ CONSULTA SI LA FECHA FINAL ES MAYOR O IGUAL A LA ACTUAL DEL SISTEMA
\*  IF  cFechaFin >= TO_CHAR(SYSDATE,'DD/MM/YYYY')   THEN
    NULL;
      --ACT_RES_VENTAS_VENDEDOR(cCodGrupoCia_in,cCodLocal_in,TO_CHAR(SYSDATE,'DD/MM/YYYY'));

  END IF;*\



    SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
    INTO   cPorc_GG
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 205
    AND    T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
    INTO   cPorc_G
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 206
    AND    T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
    INTO   cPorc_Otros
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 207
    AND    T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

    SELECT TO_NUMBER(LLAVE_TAB_GRAL,'9999.000')
    INTO   cPorc_Calc
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 208
    AND    T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'REP_VTA_VENDEDOR';

-- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
      SELECT SUM(C.VAL_NETO_PED_VTA) T_C_IGV,
             SUM(C.VAL_NETO_PED_VTA - C.VAL_IGV_PED_VTA) T_S_IGV
      INTO nValNeto, nValNetoSIGV
      FROM VTA_PEDIDO_VTA_CAB C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL     = cCodLocal_in
      AND    C.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                             AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
      AND    C.EST_PED_VTA = 'C';

  	OPEN curRep FOR
    SELECT NVL(USU.COD_TRAB,' ')  || '�' ||
     	     USU.NOM_USU ||' '|| USU.APE_PAT ||' '|| NVL(USU.APE_MAT,' ')  || '�' ||
      	   nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ') || '�' ||
       	   nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV),'999,990.00') ,' ') || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
		   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00') ,' ')|| '�' ||
           nvl(TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00') ,' ')|| '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--           nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||

           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN

           nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
--           '-' || '�' || -- COLUMNAS VACIAS
--           nvl(TO_CHAR((100-cPorc_Calc)*((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100)/100,'999,990.00'),' ') || '�' || -- COLUMNAS VACIAS
           TO_CHAR('VENDEDOR') || '�' || -- COLUMNAS VACIAS
           USU.SEC_USU_LOCAL    || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_C_IGV),'999,990.00') ,' ')|| '�' ||
           nvl(usu.login_usu,'')
    FROM   (
                    SELECT
              VTA.COD_GRUPO_CIA, VTA.COD_LOCAL, VTA.SEC_USU_LOCAL, VTA.FECHA,
              NVL(VTA.MON_TOT_CIGV,0) MON_TOT_CIGV,
              NVL(VTA.MON_TOT_SIGV,0) MON_TOT_SIGV,
              NVL(VTA.MON_G,0) TOT_G,
			        NVL(VTA.MON_GP,0) TOT_GP,
              NVL(VTA.MON_GG,0) TOT_GG,
              NVL(VTA.MON_OTROS,0) TOT_OTROS,
              NVL(VTA.MON_S,0) TOT_S,
              (VTA.CANT_PED - NVL(PED_NEG.CANT_PED,0)) CANT_PED,
              NVL(PED_NEG.CANT_PED,0) CANT_PED_ANUL,
              NVL(MON_FARMA,0),
              NVL(MON_NO_FARMA,0),
              NVL(TOT_COM, 0)
        FROM
           (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   SUM(DET.VAL_PREC_TOTAL) MON_TOT_CIGV,
                   SUM(DET.VAL_PREC_TOTAL / (1+VAL_IGV/100)) MON_TOT_SIGV,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_G,
				   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GP' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GP,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GG,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) IS NULL THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_OTROS,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'S' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_S,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'S' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_FARMA,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'N' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_NO_FARMA,
                   SUM
                      (
                       CASE
                            WHEN DET.PORC_ZAN IS NOT NULL THEN (DET.PORC_ZAN * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
							--ERIOS 21.04.2014 No deberia entrar en esta logica
                            WHEN TRIM(DET.IND_ZAN) IS NULL THEN (0.4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'G' THEN (4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN (8 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                       END
                       ) "TOT_COM"
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD P--,

--                   LGT_PROD_ZAN_LOCAL ZL
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.EST_PED_VTA = 'C'
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
              AND  P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
              AND  P.COD_PROD = DET.COD_PROD
            --   AND  CAB.TIP_PED_VTA = '01'--TIPO VENTA

--              AND  ZL.COD_PROD(+) = P.COD_PROD
            GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) VTA,
            (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.EST_PED_VTA = 'C'
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.VAL_NETO_PED_VTA < 0      -- CUENTO S�LO PEDIDOS NEGATIVOS
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
          -- AND  CAB.TIP_PED_VTA = '01'--TIPO VENTA
             GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) PED_NEG
    WHERE VTA.COD_GRUPO_CIA    = cCodGrupoCia_in
         AND VTA.COD_GRUPO_CIA = PED_NEG.COD_GRUPO_CIA(+)
         AND VTA.COD_LOCAL     = PED_NEG.COD_LOCAL(+)
         AND VTA.SEC_USU_LOCAL = PED_NEG.SEC_USU_LOCAL(+)
         AND VTA.FECHA         = PED_NEG.FECHA(+)
           )V,
           PBL_USU_LOCAL USU
    WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    V.COD_LOCAL     = cCodLocal_in
    AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                           AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
    AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
    AND    V.COD_LOCAL     = USU.COD_LOCAL
    AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
		GROUP BY NVL(USU.COD_TRAB,' '),
              USU.NOM_USU , USU.APE_PAT , NVL(USU.APE_MAT,' '),
              USU.SEC_USU_LOCAL,usu.login_usu
    UNION
    SELECT ' '  || '�' ||
    	   	 'LOCAL S/.'  || '�' ||
-- 2009-10-01 JOLIVA: SE MUESTRA EL TOTAL CON IGV Y SIN IGV DE LAS CABECERAS
      	   nvl(TO_CHAR(nValNeto,'999,990.00'),' ')  || '�' ||
       	   nvl(TO_CHAR(nValNetoSIGV,'999,990.00')  ,' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_GG),'999,990.00') ,' ')  || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_G),'999,990.00')  ,' ')  || '�' ||
		   nvl(TO_CHAR(SUM(V.MON_TOT_GP),'999,990.00')  ,' ')  || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_OTROS),'999,990.00'),' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_SERV),'999,990.00'),' ') || '�' ||
           nvl(TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.00'),' ') || '�' ||
-- 2010-01-11 JOLIVA: Se muestra nueva comision calculada
--           nvl(TO_CHAR((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100,'999,990.00'),' ') || '�' || -- CALCULADO
           nvl(TO_CHAR(SUM(V.TOT_COM),'999,990.00') ,' ')|| '�' ||

           -- DUBILLUZ 02.04.2014
           -- INICIO
           case
           when nCalidadInv = 0 then ' '
           else nvl(TO_CHAR(nCalidadInv,'S999,990')||'%' ,' ')
           end  || '�' ||
           case
           when nAtencionCliente = 0 then ' '
           else nvl(TO_CHAR(nAtencionCliente,'S999,990')||'%' ,' ')
           end  || '�' ||
           nvl(TO_CHAR(SUM(V.TOT_COM+
                           V.TOT_COM*nCalidadInv/100+
                           V.TOT_COM*nAtencionCliente/100
                           ),'9999,990.00') ,' ')|| '�' ||
           --- FIN

           nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
--           '-' || '�' ||
--           nvl(TO_CHAR((100-cPorc_Calc)*((SUM(V.MON_TOT_GG)*cPorc_GG + SUM(V.MON_TOT_G)*cPorc_G + SUM(V.MON_TOT_OTROS)*cPorc_Otros)/100)/100,'999,990.00'),' ') || '�' || -- COLUMNAS VACIAS

           TO_CHAR(' ') || '�' || -- COLUMNAS VACIAS
           ' '    || '�' ||
           nvl(TO_CHAR(nValNeto-1,'999,990.00'),' ')|| '�' ||
           ' '
    FROM   (
                    SELECT
              VTA.COD_GRUPO_CIA, VTA.COD_LOCAL, VTA.SEC_USU_LOCAL, VTA.FECHA,
              NVL(VTA.MON_TOT_CIGV,0) MON_TOT_CIGV,
              NVL(VTA.MON_TOT_SIGV,0) MON_TOT_SIGV,
              NVL(VTA.MON_G,0) TOT_G,
			        NVL(VTA.MON_GP,0) TOT_GP,
              NVL(VTA.MON_GG,0) TOT_GG,
              NVL(VTA.MON_OTROS,0) TOT_OTROS,
              NVL(VTA.MON_S,0) TOT_S,
              (VTA.CANT_PED - NVL(PED_NEG.CANT_PED,0)) CANT_PED,
              NVL(PED_NEG.CANT_PED,0) CANT_PED_ANUL,
              NVL(MON_FARMA,0),
              NVL(MON_NO_FARMA,0),
              NVL(TOT_COM, 0)
        FROM
           (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   SUM(DET.VAL_PREC_TOTAL) MON_TOT_CIGV,
                   SUM(DET.VAL_PREC_TOTAL / (1+VAL_IGV/100)) MON_TOT_SIGV,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_G,
				   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GP' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GP,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GG,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) IS NULL THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_OTROS,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'S' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_S,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'S' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_FARMA,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'N' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_NO_FARMA,
                   SUM
                      (
                       CASE
                            WHEN DET.PORC_ZAN IS NOT NULL THEN (DET.PORC_ZAN * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
							--ERIOS 21.04.2014 No deberia entrar en esta logica
                            WHEN TRIM(DET.IND_ZAN) IS NULL THEN (0.4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'G' THEN (4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN (8 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                       END
                       ) "TOT_COM"
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD P--,

--                   LGT_PROD_ZAN_LOCAL ZL
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.EST_PED_VTA = 'C'
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
              AND  P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
              AND  P.COD_PROD = DET.COD_PROD
            --   AND  CAB.TIP_PED_VTA = '01'--TIPO VENTA

--              AND  ZL.COD_PROD(+) = P.COD_PROD
            GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) VTA,
            (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.EST_PED_VTA = 'C'
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.VAL_NETO_PED_VTA < 0      -- CUENTO S�LO PEDIDOS NEGATIVOS
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
          -- AND  CAB.TIP_PED_VTA = '01'--TIPO VENTA
             GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) PED_NEG
    WHERE VTA.COD_GRUPO_CIA    = cCodGrupoCia_in
         AND VTA.COD_GRUPO_CIA = PED_NEG.COD_GRUPO_CIA(+)
         AND VTA.COD_LOCAL     = PED_NEG.COD_LOCAL(+)
         AND VTA.SEC_USU_LOCAL = PED_NEG.SEC_USU_LOCAL(+)
         AND VTA.FECHA         = PED_NEG.FECHA(+)
           ) V,
           PBL_USU_LOCAL USU
    WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    V.COD_LOCAL     = cCodLocal_in
    AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                           AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
    AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
    AND    V.COD_LOCAL     = USU.COD_LOCAL
    AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
    GROUP BY ' ','LOCAL S/'
    UNION
    SELECT ' '  || '�' ||
    	   	 'LOCAL %.'  || '�' ||
      	   ' '  || '�' ||
       	   ' '  || '�' ||
-- 2009-10-01 JOLIVA: Se va a mostrar el total de venta (cabeceras) en el total de ventas
\*
           nvl(TO_CHAR(SUM(V.MON_TOT_GG)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ')   || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_G)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ')    || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_OTROS)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_SERV)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ') || '�' ||
           nvl(TO_CHAR(DECODE(SUM(V.MON_TOT_S_IGV),0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G)*100/SUM(V.MON_TOT_S_IGV)),'999,990.00'),' ') || '�' ||
*\
           nvl(TO_CHAR(SUM(V.MON_TOT_GG)*100/nValNetoSIGV,'999,990.000'),' ')   || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_G)*100/nValNetoSIGV,'999,990.000'),' ')    || '�' ||
		   nvl(TO_CHAR(SUM(V.MON_TOT_GP)*100/nValNetoSIGV,'999,990.000'),' ')    || '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_OTROS)*100/nValNetoSIGV,'999,990.000'),' ')|| '�' ||
           nvl(TO_CHAR(SUM(V.MON_TOT_SERV)*100/nValNetoSIGV,'999,990.000'),' ') || '�' ||
           nvl(TO_CHAR(DECODE(nValNetoSIGV,0,0,SUM(V.MON_TOT_GG + V.MON_TOT_G + V.MON_TOT_GP)*100/nValNetoSIGV),'999,990.000'),' ') || '�' ||

           TO_CHAR(' ') || '�' || -- CALCULADO
           -- DUBILLUZ 02.04.2014
           -- INICIO
           TO_CHAR(' ') || '�' ||
           TO_CHAR(' ') || '�' ||
           TO_CHAR(' ') || '�' ||
           --- FIN
           nvl(TO_CHAR(SUM(V.CANT_PED),'999,990') ,' ')|| '�' ||
--           TO_CHAR(' ') || '�' || -- COLUMNAS VACIAS
           TO_CHAR('PORCENTAJE') || '�' || -- COLUMNAS QUE INDICA QUE SON PORCENTAJES ESTO SE UTILIZARA EN JAVA
           ' '    || '�' ||
           nvl(TO_CHAR(nValNetoSIGV,'999,990.00') ,' ')|| '�' ||
--           nvl(TO_CHAR(SUM(V.MON_TOT_S_IGV)*100/SUM(V.MON_TOT_S_IGV),'999,990.00'),' ')|| '�' ||
           ' '
    FROM   (
                    SELECT
              VTA.COD_GRUPO_CIA, VTA.COD_LOCAL, VTA.SEC_USU_LOCAL, VTA.FECHA,
              NVL(VTA.MON_TOT_CIGV,0) MON_TOT_CIGV,
              NVL(VTA.MON_TOT_SIGV,0) MON_TOT_SIGV,
              NVL(VTA.MON_G,0) TOT_G,
			        NVL(VTA.MON_GP,0) TOT_GP,
              NVL(VTA.MON_GG,0) TOT_GG,
              NVL(VTA.MON_OTROS,0) TOT_OTROS,
              NVL(VTA.MON_S,0) TOT_S,
              (VTA.CANT_PED - NVL(PED_NEG.CANT_PED,0)) CANT_PED,
              NVL(PED_NEG.CANT_PED,0) CANT_PED_ANUL,
              NVL(MON_FARMA,0),
              NVL(MON_NO_FARMA,0),
              NVL(TOT_COM, 0)
        FROM
           (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   SUM(DET.VAL_PREC_TOTAL) MON_TOT_CIGV,
                   SUM(DET.VAL_PREC_TOTAL / (1+VAL_IGV/100)) MON_TOT_SIGV,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_G,
				   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GP' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GP,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_GG,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) IS NULL THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_OTROS,
                   SUM(CASE WHEN TRIM(DET.IND_ZAN) = 'S' THEN DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) ELSE 0 END) MON_S,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'S' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_FARMA,
                   SUM(CASE WHEN P.IND_PROD_FARMA = 'N' THEN DET.VAL_PREC_TOTAL ELSE 0 END) MON_NO_FARMA,
                   SUM
                      (
                       CASE
                            WHEN DET.PORC_ZAN IS NOT NULL THEN (DET.PORC_ZAN * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
							--ERIOS 21.04.2014 No deberia entrar en esta logica
                            WHEN TRIM(DET.IND_ZAN) IS NULL THEN (0.4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'G' THEN (4 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                            WHEN TRIM(DET.IND_ZAN) = 'GG' OR TRIM(DET.IND_ZAN) = '3G' THEN (8 * DET.VAL_PREC_TOTAL / (1+VAL_IGV/100) / 100)
                       END
                       ) "TOT_COM"
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD P--,

--                   LGT_PROD_ZAN_LOCAL ZL
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.EST_PED_VTA = 'C'
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
              AND  P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
              AND  P.COD_PROD = DET.COD_PROD
            --   AND  CAB.TIP_PED_VTA = '01'--TIPO VENTA

--              AND  ZL.COD_PROD(+) = P.COD_PROD
            GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) VTA,
            (
            SELECT
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA) FECHA,
                   COUNT(DISTINCT DET.NUM_PED_VTA) CANT_PED
            FROM   VTA_PEDIDO_VTA_CAB CAB,
                   VTA_PEDIDO_VTA_DET DET
            WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND  CAB.EST_PED_VTA = 'C'
              AND  CAB.COD_LOCAL = cCodLocal_in
    	  			AND  CAB.FEC_PED_VTA BETWEEN TO_DATE(cFecha_Ini_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                   AND     TO_DATE(cFecha_Fin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              AND  CAB.VAL_NETO_PED_VTA < 0      -- CUENTO S�LO PEDIDOS NEGATIVOS
              AND  DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
              AND  DET.COD_LOCAL = CAB.COD_LOCAL
              AND  DET.NUM_PED_VTA = CAB.NUM_PED_VTA
          -- AND  CAB.TIP_PED_VTA = '01'--TIPO VENTA
             GROUP BY
                   CAB.COD_GRUPO_CIA,
                   CAB.COD_LOCAL,
                   DET.SEC_USU_LOCAL,
                   TRUNC(CAB.FEC_PED_VTA)
            ) PED_NEG
    WHERE VTA.COD_GRUPO_CIA    = cCodGrupoCia_in
         AND VTA.COD_GRUPO_CIA = PED_NEG.COD_GRUPO_CIA(+)
         AND VTA.COD_LOCAL     = PED_NEG.COD_LOCAL(+)
         AND VTA.SEC_USU_LOCAL = PED_NEG.SEC_USU_LOCAL(+)
         AND VTA.FECHA         = PED_NEG.FECHA(+)
           ) V,
           PBL_USU_LOCAL USU
    WHERE  V.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    V.COD_LOCAL     = cCodLocal_in
    AND    V.FEC_DIA_VENTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                           AND     TO_DATE(cFechaFin || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
    AND    V.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
    AND    V.COD_LOCAL     = USU.COD_LOCAL
    AND    V.SEC_USU_LOCAL = USU.SEC_USU_LOCAL
    GROUP BY ' ','LOCAL %.';


		RETURN curRep;*/
    RETURN '';
  END;
/***********************************************************************************************************************************************/


  FUNCTION REPORTER_REG_VTA_DELIVERY (cCodGrupoCia_in	 IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cFecInicial_in 	 IN CHAR,
                                     cFecFinal_in 	   IN CHAR, 
                                     cWhere_in         IN VARCHAR2) 
  RETURN FarmaCursor
  IS
    curReg FarmaCursor;
    sqltext varchar2(1500);
    fila V_REPOR_VENTA_DELIVERY%ROWTYPE;
  BEGIN
    
 

      sqltext := 'SELECT
      VD.TIPO_PROF_VENTA || ''�'' ||
      VD.NUM_PED_VTA || ''�'' ||
      VD.TIPO_COMP_PAGO || ''�'' ||
      VD.NUM_COMP_PAGO || ''�'' ||
            TO_CHAR(VD.FECHA, ''dd/MM/yyyy HH24:MI:SS'') || ''�'' ||
            VD.NOMBRE_CLIENTE || ''�'' ||
            VD.TELEF_CLIENTE || ''�'' ||
            TO_CHAR(VD.MONTO_PEDIDO, ''999,990.00'') || ''�'' ||
            VD.CONVENIO || ''�'' ||
            VD.PED_ANULADO || ''�'' ||
            VD.ESTADO_PEDIDO || ''�'' ||
            VD.USUARIO || ''�'' ||
            VD.CANT_ITEMS || ''�'' ||
            VD.RUC_CLIENTE || ''�'' ||
            VD.DIREC_ENVIO || ''�'' ||
            VD.REF_DIREC_ENVIO || ''�'' ||
            VD.MOTORIZADO || ''�'' ||
            VD.OBS_PEDIDO || ''�'' ||
            VD.NOMBRE_DE || ''�'' ||
            VD.DIREC_CLIENTE 
      FROM V_REPOR_VENTA_DELIVERY VD
      WHERE
            VD.COD_GRUPO_CIA = ' || cCodGrupoCia_in || '
         AND VD.COD_LOCAL = ' || cCodLocal_in || '
         AND VD.FECHA BETWEEN
            TO_DATE( ''' || cFecInicial_in || '''  || '' 00:00:00'', ''dd/MM/yyyy HH24:MI:SS'') AND
             TO_DATE(''' || cFecFinal_in || ''' || '' 23:59:59'', ''dd/MM/yyyy HH24:MI:SS'')';
       
      IF cWhere_in IS NOT NULL AND LENGTH(TRIM(cWhere_in))>0 THEN
        sqltext := sqltext || ' AND '||cWhere_in;
      END IF;
      
      dbms_output.put_line(sqltext);
      OPEN curReg FOR sqltext ;
   
  RETURN curReg;
  END;


  FUNCTION GET_CAMPOS_REPORTE(cTabla_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCampos FarmaCursor;
  BEGIN
    BEGIN 
      OPEN curCampos FOR
        SELECT TRIM(A.COLUMN_NAME)|| '�' ||
               TRIM(A.COMMENTS)
          FROM ALL_COL_COMMENTS A
         WHERE A.OWNER = 'PTOVENTA'
           AND A.TABLE_NAME = cTabla_in
           AND (A.COMMENTS IS NOT NULL OR LENGTH(TRIM(A.COMMENTS))>0);
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        OPEN curCampos FOR
        SELECT ' ' FROM DUAL;
    END;
  RETURN curCampos;
  END;
  
  FUNCTION GET_TIPO_DATO_COLUMN(cTabla_in IN CHAR,
                                cColumna IN CHAR)
  RETURN VARCHAR2
  IS
    vTipoDato ALL_TAB_COLUMNS.DATA_TYPE%TYPE;
  BEGIN
    BEGIN
      SELECT A.DATA_TYPE
      INTO   vTipoDato
        FROM ALL_TAB_COLUMNS A
       WHERE A.OWNER = 'PTOVENTA'
         AND A.TABLE_NAME = cTabla_in
         AND A.COLUMN_NAME = cColumna;
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        vTipoDato := 'N';
    END;
    RETURN vTipoDato;
  END;
  
  FUNCTION REPORTE_FORMAS_PAGO_RESUMEN(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in	  	IN CHAR,
  		   							                 cNumPedVta IN CHAR)
  RETURN FarmaCursor
  IS
  curRep FarmaCursor;
  BEGIN
 	  OPEN curRep FOR
         select 
         b.cod_forma_pago || '�' ||
         b.desc_forma_pago || '�' ||
         TO_CHAR(a.im_pago,'999,990.000') || '�' ||
         TO_CHAR(a.val_vuelto,'999,990.000')
         from vta_forma_pago_pedido a,
              vta_forma_pago b
         where a.cod_forma_pago=b.cod_forma_pago
           AND     a.COD_GRUPO_CIA = cCodGrupoCia_in
	         AND	   a.cod_local = cCodLocal_in
	         AND	   a.num_ped_vta=cNumPedVta;

	   RETURN curRep;

   END;
  
END;

/
