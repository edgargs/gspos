CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CUARENTENA" AS

  g_cTipoMotKardexIngresoC LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '120';
  g_cTipoMotKardexSalidaC LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '121';
  g_cTipDocKdxGuiaES LGT_KARDEX.Tip_Comp_Pago%TYPE := '02';
  SEC_KARDEX_CUARENTENA      PBL_NUMERA.COD_NUMERA%TYPE := '078';

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Ingresa productos de recepcion
  --Fecha       Usuario	  Comentario
  --03/05/2016  ERIOS     Creacion  
  PROCEDURE P_INGRESO_KARDEX_RECEP(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
								  cNroRecepcion_in IN CHAR,
								  cUsuCreaKardex_in   IN CHAR);

  --Descripcion: Opera kardex productos de recepcion
  --Fecha       Usuario	  Comentario
  --03/05/2016  ERIOS     Creacion  
  PROCEDURE P_OPERA_KARDEX_RECEP(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
								  cNroRecepcion_in IN CHAR,
								  cUsuCreaKardex_in   IN CHAR,
								  nIndTipo_in IN INTEGER);

  --Descripcion: Salida productos de recepcion
  --Fecha       Usuario	  Comentario
  --03/05/2016  ERIOS     Creacion  
  PROCEDURE P_SALIDA_KARDEX_RECEP(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
								  cNroRecepcion_in IN CHAR,
								  cUsuCreaKardex_in   IN CHAR);

  --Descripcion: Graba kardex productos de recepcion
  --Fecha       Usuario	  Comentario
  --03/05/2016  ERIOS     Creacion  								  
  PROCEDURE RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
                                  cCodProd_in         IN CHAR,
                                  cCodMotKardex_in    IN CHAR,
                                  cTipDocKardex_in    IN CHAR,
                                  cNumTipDoc_in       IN CHAR,
                                  nStkAnteriorProd_in IN NUMBER,
                                  nCantMovProd_in     IN NUMBER,
                                  nValFrac_in         IN NUMBER,
                                  cDescUnidVta_in     IN CHAR,
                                  cUsuCreaKardex_in   IN CHAR,
                                  cCodNumera_in       IN CHAR,
                                  cGlosa_in           IN CHAR DEFAULT NULL,
                                  cTipDoc_in          IN CHAR DEFAULT NULL,
                                  cNumDoc_in          IN CHAR DEFAULT NULL,
                                  nNumLoteProd_in     IN CHAR DEFAULT NULL,
                                  nFecVctoProd_in     IN DATE DEFAULT NULL);
								  
  --Descripcion: Listado detalle de kardex y cuarentena
  --Fecha       Usuario	  Comentario
  --03/05/2016  ERIOS     Creacion  
  FUNCTION F_REPORTE_KARDEX (cCodGrupoCia_in IN CHAR ,
                        cCodLocal_in    IN CHAR ,
                 cCod_Prod_in   IN CHAR,
                 cFecIni_in     IN CHAR,
                 cFecFin_in    IN CHAR)
  RETURN FarmaCursor;
  
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CUARENTENA" AS


  PROCEDURE P_INGRESO_KARDEX_RECEP(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
								  cNroRecepcion_in IN CHAR,
								  cUsuCreaKardex_in   IN CHAR) IS
  BEGIN
    P_OPERA_KARDEX_RECEP(cCodGrupoCia_in,cCodLocal_in,cNroRecepcion_in,cUsuCreaKardex_in,1);
  END;
  
  PROCEDURE P_OPERA_KARDEX_RECEP(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
								  cNroRecepcion_in IN CHAR,
								  cUsuCreaKardex_in   IN CHAR,
								  nIndTipo_in IN INTEGER) IS								  
    CURSOR c_detalle IS
	SELECT DET.COD_GRUPO_CIA,
	DET.COD_LOCAL,
	DET.COD_PROD,
	RCP.NUM_GUIA_REM,
	DET.CANT_MOV,
	DET.VAL_FRAC,
	DET.DESC_UNID_VTA,
	DET.NUM_LOTE_PROD,
	DET.FEC_VCTO_PROD
	FROM PTOVENTA.LGT_RECEP_ENTREGA RCP
	  JOIN PTOVENTA.LGT_NOTA_ES_DET DET ON (DET.COD_GRUPO_CIA = RCP.COD_GRUPO_CIA
		AND DET.COD_LOCAL = RCP.COD_LOCAL
		AND DET.NUM_NOTA_ES = RCP.NUM_NOTA_ES
		AND DET.SEC_GUIA_REM = RCP.SEC_GUIA_REM)
	WHERE RCP.COD_GRUPO_CIA = cCodGrupoCia_in
	AND RCP.COD_LOCAL = cCodLocal_in
	AND RCP.NRO_RECEP = cNroRecepcion_in
	;	
  BEGIN
    FOR fila IN c_detalle
	LOOP
	  RECEP_P_GRABAR_KARDEX(fila.COD_GRUPO_CIA,
                                  fila.COD_LOCAL,
                                  fila.COD_PROD,
                                  CASE WHEN nIndTipo_in = -1 THEN g_cTipoMotKardexSalidaC 
								       ELSE g_cTipoMotKardexIngresoC 
								  END,
                                  g_cTipDocKdxGuiaES,
                                  fila.NUM_GUIA_REM,
                                  0,
                                  fila.CANT_MOV*nIndTipo_in,
                                  fila.VAL_FRAC,
                                  fila.DESC_UNID_VTA,
                                  cUsuCreaKardex_in,
                                  SEC_KARDEX_CUARENTENA,
                                  NULL,
                                  NULL,
                                  NULL,
                                  fila.NUM_LOTE_PROD,
                                  fila.FEC_VCTO_PROD);
	END LOOP;
  END;

  PROCEDURE P_SALIDA_KARDEX_RECEP(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
								  cNroRecepcion_in IN CHAR,
								  cUsuCreaKardex_in   IN CHAR) IS
  BEGIN
    P_OPERA_KARDEX_RECEP(cCodGrupoCia_in,cCodLocal_in,cNroRecepcion_in,cUsuCreaKardex_in,-1);
  END;
  
  PROCEDURE RECEP_P_GRABAR_KARDEX(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
                                  cCodProd_in         IN CHAR,
                                  cCodMotKardex_in    IN CHAR,
                                  cTipDocKardex_in    IN CHAR,
                                  cNumTipDoc_in       IN CHAR,
                                  nStkAnteriorProd_in IN NUMBER,
                                  nCantMovProd_in     IN NUMBER,
                                  nValFrac_in         IN NUMBER,
                                  cDescUnidVta_in     IN CHAR,
                                  cUsuCreaKardex_in   IN CHAR,
                                  cCodNumera_in       IN CHAR,
                                  cGlosa_in           IN CHAR DEFAULT NULL,
                                  cTipDoc_in          IN CHAR DEFAULT NULL,
                                  cNumDoc_in          IN CHAR DEFAULT NULL,
                                  nNumLoteProd_in     IN CHAR DEFAULT NULL,
                                  nFecVctoProd_in     IN DATE DEFAULT NULL) IS
    v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;
    v_Lote varchar2(10) := nNumLoteProd_in;
    v_FechaVencimiento DATE:= nFecVctoProd_in;
  BEGIN
    IF (FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in) = 'S') THEN
      IF (TRIM(nNumLoteProd_in) <> '' OR nNumLoteProd_in IS NULL) THEN
         v_Lote := PTOVENTA_TOMA_INV.GET_SIN_LOTE;
         v_FechaVencimiento := TRUNC(SYSDATE);
       END IF;
    END IF;
    
    v_cSecKardex := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                                                         cCodLocal_in,
                                                                                         cCodNumera_in),
                                                        10,
                                                        '0',
                                                        'I');
    INSERT INTO LGT_KARDEX_CUARENTENA
      (COD_GRUPO_CIA,
       COD_LOCAL,
       SEC_KARDEX,
       COD_PROD,
       COD_MOT_KARDEX,
       TIP_DOC_KARDEX,
       NUM_TIP_DOC,
       STK_ANTERIOR_PROD,
       CANT_MOV_PROD,
       STK_FINAL_PROD,
       VAL_FRACC_PROD,
       DESC_UNID_VTA,
       USU_CREA_KARDEX,
       DESC_GLOSA_AJUSTE,
       TIP_COMP_PAGO,
       NUM_COMP_PAGO,
       NRO_LOTE,
       FECHA_VENCIMIENTO_LOTE)
    VALUES
      (cCodGrupoCia_in,
       cCodLocal_in,
       v_cSecKardex,
       cCodProd_in,
       cCodMotKardex_in,
       cTipDocKardex_in,
       cNumTipDoc_in,
       nStkAnteriorProd_in,
       nCantMovProd_in,
       nStkAnteriorProd_in,
       nValFrac_in,
       cDescUnidVta_in,
       cUsuCreaKardex_in,
       cGlosa_in,
       cTipDoc_in,
       cNumDoc_in,
       v_Lote,
       v_FechaVencimiento);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cCodNumera_in,
                                               cUsuCreaKardex_in);
  END;

  FUNCTION F_REPORTE_KARDEX (cCodGrupoCia_in IN CHAR ,
                        cCodLocal_in    IN CHAR ,
                 cCod_Prod_in   IN CHAR,
                 cFecIni_in     IN CHAR,
                 cFecFin_in    IN CHAR)
  RETURN FarmaCursor
  IS
      curListado FarmaCursor;
  BEGIN
      OPEN curListado FOR
	  WITH
		VK AS (
		SELECT COD_GRUPO_CIA,
		COD_LOCAL,
		COD_PROD,
		COD_MOT_KARDEX,
		FEC_KARDEX,
		num_tip_doc,
		sec_kardex,
		DESC_GLOSA_AJUSTE,NUM_COMP_PAGO,tip_comp_pago,
		TIP_DOC_KARDEX,
		STK_ANTERIOR_PROD,
		STK_FINAL_PROD,
		CANT_MOV_PROD,VAL_FRACC_PROD,
		NRO_LOTE,FECHA_VENCIMIENTO_LOTE
		FROM PTOVENTA.LGT_KARDEX LK 
		WHERE LK.COD_GRUPO_CIA  = cCodGrupoCia_in    AND
				  LK.COD_LOCAL      = cCodLocal_in      AND
				  LK.COD_PROD     = cCod_Prod_in      AND
				  LK.FEC_KARDEX BETWEEN TO_DATE(cFecIni_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') 
				  AND TO_DATE(cFecFin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
		UNION
		SELECT COD_GRUPO_CIA,
		COD_LOCAL,
		COD_PROD,
		COD_MOT_KARDEX,
		FEC_KARDEX,
		num_tip_doc,
		sec_kardex,
		DESC_GLOSA_AJUSTE,NUM_COMP_PAGO,tip_comp_pago,
		TIP_DOC_KARDEX,
		STK_ANTERIOR_PROD,
		STK_FINAL_PROD,
		CANT_MOV_PROD,VAL_FRACC_PROD,
		NRO_LOTE,FECHA_VENCIMIENTO_LOTE
		FROM PTOVENTA.LGT_KARDEX_CUARENTENA LKC
		WHERE LKC.COD_GRUPO_CIA  = cCodGrupoCia_in    AND
				  LKC.COD_LOCAL      = cCodLocal_in      AND
				  LKC.COD_PROD     = cCod_Prod_in      AND
				  LKC.FEC_KARDEX BETWEEN TO_DATE(cFecIni_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') 
				  AND TO_DATE(cFecFin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss'))
				  
		SELECT DISTINCT
           TO_CHAR(K.FEC_KARDEX,'dd/MM/yyyy HH24:MI:SS') ,
           NVL(MK.DESC_CORTA_MOT_KARDEX,' ')         ,
          CASE k.tip_comp_pago
			  WHEN '01' THEN 'BOLETA'
			  WHEN '02' THEN 'FACTURA'
			  WHEN '03' THEN 'GUIA'
			  WHEN '04' THEN 'NOTA CREDITO'
			  WHEN '05' THEN DECODE(K.TIP_DOC_KARDEX,'01','TICKET','ENTREGA')
			  ELSE NVL(DECODE(K.TIP_DOC_KARDEX,'01','VENTA','02','GUIA ENTRADA/SALIDA','03','AJUSTE DE INVENTARIO'),' ')
          END       ,
           DECODE(K.NUM_COMP_PAGO,NULL,NVL(K.NUM_TIP_DOC,' '),
								NVL((select 
								Farma_Utility.GET_T_COMPROBANTE_2(P.COD_TIP_PROC_PAGO,P.NUM_COMP_PAGO_E,P.NUM_COMP_PAGO)
								   from vta_comp_pago p 
								   where p.num_comp_pago= k.num_comp_pago 
								   and p.tip_comp_pago = k.tip_comp_pago
								   and   K.COD_LOCAL      = cCodLocal_in 
								   AND   P.FEC_CREA_COMP_PAGO BETWEEN 
															  TRUNC(K.FEC_KARDEX) AND
															  TRUNC(K.FEC_KARDEX)  + 1 -24/60/60
												), K.NUM_COMP_PAGO))       , 
           NVL(K.STK_ANTERIOR_PROD,0)            ,
           NVL(K.CANT_MOV_PROD,0)              ,
           NVL(K.STK_FINAL_PROD,0)              ,
           NVL(K.VAL_FRACC_PROD,0)              ,
          nvl(decode(K.TIP_DOC_KARDEX,'01',nvl(vtas.usuario,' ')),' ') ,
          NVL(k.DESC_GLOSA_AJUSTE,' '),
          k.sec_kardex ,
          NVL(K.CANT_MOV_PROD,0) * NVL(K.VAL_FRACC_PROD,0),
          K.NUM_TIP_DOC ,
          nvl(VTAS.IND,' ') ,
          k.cod_mot_kardex ,
          NVL(K.NRO_LOTE,' ') , 
          NVL(to_char(K.FECHA_VENCIMIENTO_LOTE, 'dd/mm/yyyy'), ' ')
          
    FROM VK K,
         LGT_MOT_KARDEX MK,
         (SELECT PVD.COD_PROD codigo,
                  pvd.cod_local LOCAL,
                  PVD.USU_CREA_PED_VTA_DET usuario,
                  pvd.num_ped_vta venta,
                  PVD.IND_CALCULO_MAX_MIN IND
           FROM   Lgt_kardex K,
                  vTa_pedido_vta_det pvd
           WHERE  K.COD_GRUPO_CIA = ccodgrupocia_in AND
                  K.COD_LOCAL = ccodlocal_in AND
                  k.cod_prod = ccod_prod_in AND
                  K.COD_GRUPO_CIA = PVD.COD_GRUPO_CIA AND
                  K.COD_LOCAL = PVD.COD_LOCAL AND
                  K.COD_PROD = PVD.COD_PROD AND
                  k.num_tip_doc(+) = pvd.num_ped_vta)vtas
    WHERE K.COD_GRUPO_CIA  = MK.COD_GRUPO_CIA    AND
          K.COD_MOT_KARDEX = MK.COD_MOT_KARDEX AND
          k.num_tip_doc = vtas.venta(+) ;
	  
      RETURN curListado;
  END;  
END;
/
