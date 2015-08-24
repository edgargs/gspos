CREATE OR REPLACE PACKAGE PTOVENTA.FARMA_LEALTAD AS

	TYPE FarmaCursor IS REF CURSOR;
	
  --Descripcion: Verifica si el producto participa de X+1.
  --Fecha       Usuario	  Comentario
  --05/02/2015  ERIOS     Creacion
  FUNCTION VERIFICA_ACUMULA_X1 (cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR) RETURN INTEGER;

  --Descripcion: Lista que participa de X+1.
  --Fecha       Usuario	  Comentario
  --05/02/2015  ERIOS     Creacion
  FUNCTION LISTA_ACUMULA_X1(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Voucher que participa de X+1.
  --Fecha       Usuario	  Comentario
  --05/02/2015  ERIOS     Creacion  
  FUNCTION VOUCHER_ACUMULA_X1(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cDniCli IN CHAR, cCodCamp_in IN CHAR, cCodProd_in IN CHAR) 
  RETURN FarmaCursor;

  --Descripcion: Indicador de impresion de Voucher X+1.
  --Fecha       Usuario	  Comentario
  --11/02/2015  ERIOS     Creacion    
  FUNCTION IND_IMPR_VOUCHER_X1 RETURN VARCHAR2;

  --Descripcion: Retorna los parametros para venta online.
  --Fecha       Usuario	  Comentario
  --13/02/2015  ERIOS     Creacion      
  FUNCTION GET_PARAMETROS_VENTA(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR) 
  RETURN VARCHAR2;

  --Descripcion: Actualiza los parametros para venta online.
  --Fecha       Usuario	  Comentario
  --13/02/2015  ERIOS     Creacion        
  PROCEDURE GET_ACTUALIZAR_VENTA(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, vIdTransaccion_in IN VARCHAR2,
  vNumAutorizacion_in IN VARCHAR2, vIdUsu_in IN VARCHAR2);

  --Descripcion: Elimina productos en bonificacion.
  --Fecha       Usuario	  Comentario
  --17/02/2015  ERIOS     Creacion          
  PROCEDURE GET_ELIMINA_BONIFICA(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR);

  --Descripcion: Descarta pedido sin beneficios.
  --Fecha       Usuario	  Comentario
  --18/02/2015  ERIOS     Creacion          
  PROCEDURE GET_DESCARTAR_PEDIDO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR);

  --Descripcion: Obtiene monto minimo para redencion.
  --Fecha       Usuario	  Comentario
  --19/02/2015  ERIOS     Creacion            
  FUNCTION GET_MONTO_MIN_PUNTOS RETURN VARCHAR2;
  
  --Descripcion: Obtiene saldos de puntos.
  --Fecha       Usuario	  Comentario
  --19/02/2015  ERIOS     Creacion          
  FUNCTION GET_SALDO_PUNTOS(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene indicadores de mostrar puntos.
  --Fecha       Usuario	  Comentario
  --25/02/2015  ERIOS     Creacion  
  FUNCTION GET_MOSTRAR_PUNTOS(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  RETURN VARCHAR2;

  FUNCTION IND_VER_PUNTOS_ACUM RETURN VARCHAR2;  
  
  FUNCTION IND_VER_PUNTOS_REDI RETURN VARCHAR2;  

  --Descripcion: Verifica que la NCR no haya sido usado.
  --Fecha       Usuario	  Comentario
  --02/03/2015  ERIOS     Creacion    
  FUNCTION GET_USO_NCR(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, cTipoBusqueda_in IN CHAR DEFAULT 'NCR')
  RETURN VARCHAR2;

  --Descripcion: Verifica la fecha de uso NCR.
  --Fecha       Usuario	  Comentario
  --03/03/2015  ERIOS     Creacion    
  FUNCTION GET_FECHA_NCR(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, cFechaNCR_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Verifica la NCR no sea comprobrante de credito.
  --Fecha       Usuario	  Comentario
  --04/03/2015  ERIOS     Creacion    
  FUNCTION GET_CREDITO_NCR(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, cFechaNCR_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene monto de la NCR.
  --Fecha       Usuario	  Comentario
  --19/03/2015  ERIOS     Creacion    
  FUNCTION GET_MONTO_NCR(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Recalcula los montos del pedido.
  --Fecha       Usuario	  Comentario
  --23/03/2015  ERIOS     Creacion      
  PROCEDURE RECALCULO_REDENCION_PEDIDO(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in  IN CHAR       ,
                                          cNumPedVta_in  IN CHAR      ,
										  nImTotalPago_in IN NUMBER);
  
  --Descripcion: Indicador de impresion de puntos redimidos en doc. elect.
  --Fecha       Usuario	  Comentario
  --23/03/2015  ERIOS     Creacion                          
  FUNCTION IND_IMPR_PUNTOS_REDI(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cNumPedVta_in IN CHAR) RETURN VARCHAR2;
  
  --Descripcion: Mensaje de impresion de puntos redimidos en doc. elect.
  --Fecha       Usuario	  Comentario
  --23/03/2015  ERIOS     Creacion                          
  FUNCTION MSJ_IMPR_PUNTOS_REDI RETURN VARCHAR2;

  --Descripcion: Retorna los indicadores para venta online.
  --Fecha       Usuario	  Comentario
  --27/03/2015  ERIOS     Creacion      
  FUNCTION GET_INDICADORES_VENTA(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR) 
  RETURN VARCHAR2;
  
  FUNCTION GET_MULTIPLO_PTO(cCodGrupoCia_in IN CHAR, 
                               cCodCia_in IN CHAR, 
                               cCodLocal_in IN CHAR)
  RETURN VARCHAR2;
  
  --Descripcion: Registra de inscripcion por turno.
  --Fecha       Usuario	  Comentario
  --22/06/2015  ERIOS     Creacion      
  PROCEDURE REGISTRA_INSCRIPCION_TURNO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cSecMovCaja_in IN CHAR, vCodTarjeta_in IN VARCHAR2, vIdUsu_in IN VARCHAR2);

  --Descripcion: Obtiene de inscripcion por turno.
  --Fecha       Usuario	  Comentario
  --22/06/2015  ERIOS     Creacion      
  FUNCTION GET_INSCRIPCION_TURNO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cSecMovCaja_in IN CHAR) RETURN VARCHAR2;  
  
END FARMA_LEALTAD;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA.FARMA_LEALTAD AS

  --Descripcion: Verifica si el producto participa de X+1.
  --Fecha       Usuario	  Comentario
  --05/02/2015  ERIOS     Creacion
  FUNCTION VERIFICA_ACUMULA_X1 (cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR) RETURN INTEGER
  IS
    nRetorno INTEGER;
  BEGIN
    SELECT COUNT(DISTINCT MA.EQUIVALENTE_ACU||
						   MA.COD_MATRIZ_ACU||
						   MA.COD_CAMP_EQ||
						   MA.AGRUPA)
		INTO nRetorno
	FROM VTA_ACUMULA AC JOIN VTA_MATRIZ_ACUMULA MA ON (AC.COD_ACUMULA = MA.COD_ACUMULA)
	WHERE AC.COD_PROD = cCodProd_in
	AND AC.ESTADO = 'A'
	AND TRUNC(SYSDATE) BETWEEN AC.FECHA_INICIO AND AC.FECHA_FIN
	;
	
	RETURN nRetorno;
  END;
  
  --Descripcion: Lista que participa de X+1.
  --Fecha       Usuario	  Comentario
  --05/02/2015  ERIOS     Creacion
  FUNCTION LISTA_ACUMULA_X1(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR) RETURN FarmaCursor
  IS
    cLista FarmaCursor;
  BEGIN
	OPEN cLista FOR
	WITH
	MA AS (SELECT DISTINCT MA.EQUIVALENTE_ACU,
						   MA.COD_MATRIZ_ACU,
						   MA.COD_CAMP_EQ,
						   MA.AGRUPA
		  FROM VTA_ACUMULA AC JOIN VTA_MATRIZ_ACUMULA MA ON (AC.COD_ACUMULA = MA.COD_ACUMULA)
		  WHERE AC.COD_PROD = cCodProd_in		
			AND AC.ESTADO = 'A'
			AND TRUNC(SYSDATE) BETWEEN AC.FECHA_INICIO AND AC.FECHA_FIN)
	SELECT (CC.COD_CAMP_CUPON || '@' || 
		   CC.DESC_CUPON || '@' || 
		   MA.EQUIVALENTE_ACU || '@' || 
		   MA.COD_MATRIZ_ACU) RESULTADO
	FROM MA
      JOIN VTA_EQUIVALENTE_CAMPANA EC ON (MA.COD_CAMP_EQ = EC.COD_CAMP_EQ)
      JOIN VTA_CAMPANA_CUPON CC ON (CC.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND CC.COD_CAMP_CUPON = EC.COD_CAMP_CUPON)
	;
	RETURN cLista;
  END;
  
  --Descripcion: Voucher que participa de X+1.
  --Fecha       Usuario	  Comentario
  --05/02/2015  ERIOS     Creacion  
  FUNCTION VOUCHER_ACUMULA_X1(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cDniCli IN CHAR, cCodCamp_in IN CHAR, cCodProd_in IN CHAR) 
  RETURN FarmaCursor
  IS
    cVoucher FarmaCursor;
    vIdDoc VARCHAR2(50);
    vIpPc  VARCHAR2(50);
    vValor VARCHAR2(100);
  BEGIN
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc := FARMA_PRINTER.F_GET_IP_SESS;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => 'INSCRIPCION DE CAMPAÑA', 
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
    select 'LOCAL: '|| l.cod_local || '-' || l.desc_corta_local
    INTO vValor
    from   pbl_local l
    where  l.cod_grupo_cia = cCodGrupoCia_in
    and    l.cod_local = cCodLocal_in;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => vValor, 
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => 'FECHA: ' || to_char(sysdate,'dd/mm/yyyy HH24:MI:SS'), 
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
    
    SELECT TRIM(NOM_CLI||' '||APE_PAT_CLI||' '||APE_MAT_CLI)
	  INTO vValor
    FROM PBL_CLIENTE
	  WHERE DNI_CLI = cDniCli;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => vValor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => 'Ha sido inscrito para participar de la campaña:',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    
    SELECT CC.DESC_CUPON 
    INTO vValor
	  FROM VTA_CAMPANA_CUPON CC 
	  WHERE CC.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CC.COD_CAMP_CUPON = cCodCamp_in;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => vValor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => 'Del producto:',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    SELECT NVL(DESC_PROD, ' ')
    INTO vValor
	  FROM LGT_PROD
	  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	  AND COD_PROD = cCodProd_in;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => vValor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    
    cVoucher := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
    RETURN cVoucher;
    
/*                                   
	DELETE TMP_DOCUMENTO_ELECTRONICOS;
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'0','C','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
    SELECT 'INSCRIPCION DE CAMPAÑA'||'@'||
           ' '||'@'||
           'LOCAL: '||( select l.cod_local || '-' || l.desc_corta_local
                        from   pbl_local l
                        where  l.cod_grupo_cia = cCodGrupoCia_in
                        and    l.cod_local = cCodLocal_in)||'@'||
           'FECHA: ' || to_char(sysdate,'dd/mm/yyyy HH24:MI:SS')||'@'||
--           ' '||'@'||
           ' '
           FROM DUAL
    ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
	
	INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
	SELECT TRIM(NOM_CLI||' '||APE_PAT_CLI||' '||APE_MAT_CLI),'0','C','S','N'
	FROM PBL_CLIENTE
	WHERE DNI_CLI = cDniCli;
    
	INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT ' ' ,'0','I','N','N' FROM DUAL;
	
	INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT 'Ha sido inscrito para participar de la campana:' ,'0','I','N','N' FROM DUAL;
	
  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
	SELECT CC.DESC_CUPON ,'0','I','S','N'
	FROM VTA_CAMPANA_CUPON CC 
	WHERE CC.COD_GRUPO_CIA = cCodGrupoCia_in
          AND CC.COD_CAMP_CUPON = cCodCamp_in;
	
	INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT ' ' ,'0','I','N','N' FROM DUAL;
	
	INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
    SELECT 'Del producto:' ,'0','I','N','N' FROM DUAL;
	INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
	SELECT DESC_PROD,'0','I','S','N'
	FROM LGT_PROD
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	AND COD_PROD = cCodProd_in;
	
	OPEN cVoucher FOR
	SELECT A.VALOR, A.TAMANIO, A.ALINEACION, A.BOLD, A.AJUSTE
	FROM TMP_DOCUMENTO_ELECTRONICOS A;
*/
    
  END;  
  
  --Descripcion: Indicador de impresion de Voucher X+1.
  --Fecha       Usuario	  Comentario
  --11/02/2015  ERIOS     Creacion    
  FUNCTION IND_IMPR_VOUCHER_X1 RETURN VARCHAR2
  IS
    vRetorno VARCHAR2(10);
  BEGIN
    SELECT NVL(DESC_CORTA,'N') || '@' || NVL(DESC_LARGA,'N') INTO vRetorno
	FROM PBL_TAB_GRAL 
	WHERE --ID_TAB_GRAL = 449;
	COD_APL = 'PTO_VENTA'
	AND COD_TAB_GRAL = 'IND_IMPR_VOUCHER_X1'
	AND LLAVE_TAB_GRAL = '01';
	RETURN vRetorno;
  END;  
  
  --Descripcion: Retorna los parametros para venta online.
  --Fecha       Usuario	  Comentario
  --13/02/2015  ERIOS     Creacion      
  FUNCTION GET_PARAMETROS_VENTA(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR) 
  RETURN VARCHAR2
  IS
	vRetorno VARCHAR2(100);
  BEGIN
  SELECT NVL(NUM_TARJ_PUNTOS, ' ') || '@' || 
         NUM_PED_VTA || '@' ||
         TO_CHAR(FEC_PED_VTA, 'DD/MM/YYYY') || '@' ||
         TRIM(TO_CHAR(VAL_NETO_PED_VTA, '999999990.00')) || '@' ||
         USL.DNI_USU || '@' ||
         TRIM(TO_CHAR(NVL(PT_REDIMIDO, 0), '999999990.00')) || '@' ||
         (SELECT COUNT(1)
            FROM VTA_PEDIDO_VTA_DET DET
           WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
             AND DET.COD_LOCAL = cCodLocal_in
             AND DET.NUM_PED_VTA = cNumPedVta_in
             AND DET.IND_BONIFICADO = 'S'
             AND ROWNUM = 1) || '@' || 
         NVL(CAB.PT_ACUMULADO, 0) || '@' ||
         (SELECT COUNT(1)
            FROM VTA_PEDIDO_VTA_DET DET
           WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
             AND DET.COD_LOCAL = cCodLocal_in
             AND DET.NUM_PED_VTA = cNumPedVta_in
             AND DET.COD_PROD_PUNTOS IS NOT NULL
             AND ROWNUM = 1) || '@' ||
             -- KMONCADA 11.08.2015 SE AGREGA PTOS POR AHORRO PACK
         TRIM(TO_CHAR((SELECT SUM(NVL(D.PTOS_AHORRO, 0) + NVL(D.PTOS_AHORRO_PACK, 0))
                        FROM VTA_PEDIDO_VTA_DET D
                       WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND D.COD_LOCAL = cCodLocal_in
                         AND D.NUM_PED_VTA = cNumPedVta_in),
                      '999999990.00'))
    INTO vRetorno
    from VTA_PEDIDO_VTA_CAB CAB
    JOIN PBL_USU_LOCAL USL
      ON (USL.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA AND
         USL.COD_LOCAL = CAB.COD_LOCAL AND
         USL.SEC_USU_LOCAL = CAB.SEC_USU_LOCAL)
   WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
     AND CAB.COD_LOCAL = cCodLocal_in
     AND CAB.NUM_PED_VTA = cNumPedVta_in;
	RETURN vRetorno;
  END;

  --Descripcion: Actualiza los parametros para venta online.
  --Fecha       Usuario	  Comentario
  --13/02/2015  ERIOS     Creacion        
  PROCEDURE GET_ACTUALIZAR_VENTA(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, vIdTransaccion_in IN VARCHAR2,
  vNumAutorizacion_in IN VARCHAR2, vIdUsu_in IN VARCHAR2)
  IS
  BEGIN
	UPDATE VTA_PEDIDO_VTA_CAB CAB
	SET --ID_TRANSACCION = vIdTransaccion_in,
		NUMERO_AUTORIZACION = vNumAutorizacion_in,
		EST_TRX_ORBIS = 'E', --Enviado
		FEC_PROC_PUNTOS = SYSDATE,
		FEC_MOD_PED_VTA_CAB = SYSDATE,
		USU_MOD_PED_VTA_CAB = vIdUsu_in
	WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
	AND CAB.COD_LOCAL = cCodLocal_in
	AND CAB.NUM_PED_VTA = cNumPedVta_in;
	
  END;

  --Descripcion: Elimina productos en bonificacion.
  --Fecha       Usuario	  Comentario
  --17/02/2015  ERIOS     Creacion          
  PROCEDURE GET_ELIMINA_BONIFICA(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  IS
  BEGIN
	DELETE VTA_PEDIDO_VTA_DET DET
	WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
	AND DET.COD_LOCAL = cCodLocal_in
	AND DET.NUM_PED_VTA = cNumPedVta_in
	AND DET.IND_BONIFICADO = 'S'
	;
	
	UPDATE VTA_PEDIDO_VTA_CAB CAB
	SET (CANT_ITEMS_PED_VTA , PT_ACUMULADO) = (SELECT COUNT(1),SUM(CTD_PUNTOS_ACUM)
												FROM VTA_PEDIDO_VTA_DET DET
												WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
												AND DET.COD_LOCAL = cCodLocal_in
												AND DET.NUM_PED_VTA = cNumPedVta_in)
	WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
	AND CAB.COD_LOCAL = cCodLocal_in
	AND CAB.NUM_PED_VTA = cNumPedVta_in;
	
	UPDATE VTA_PEDIDO_VTA_CAB CAB
	SET PT_TOTAL = PT_INICIAL+PT_ACUMULADO-PT_REDIMIDO
	WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
	AND CAB.COD_LOCAL = cCodLocal_in
	AND CAB.NUM_PED_VTA = cNumPedVta_in;
	
  END;  

  --Descripcion: Descarta pedido sin beneficios.
  --Fecha       Usuario	  Comentario
  --18/02/2015  ERIOS     Creacion          
  PROCEDURE GET_DESCARTAR_PEDIDO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  IS
  BEGIN
	UPDATE VTA_PEDIDO_VTA_CAB CAB
	SET EST_TRX_ORBIS = 'D' --Descartado
	WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
	AND CAB.COD_LOCAL = cCodLocal_in
	AND CAB.NUM_PED_VTA = cNumPedVta_in;
  END;  

  --Descripcion: Obtiene monto minimo para redencion.
  --Fecha       Usuario	  Comentario
  --19/02/2015  ERIOS     Creacion            
  FUNCTION GET_MONTO_MIN_PUNTOS RETURN VARCHAR2
  IS
	vRetorno VARCHAR2(10);
  BEGIN
	SELECT LLAVE_TAB_GRAL
		INTO vRetorno
	FROM PBL_TAB_GRAL
	WHERE ID_TAB_GRAL = 411;
	
	RETURN vRetorno;
  END;  
  
  --Descripcion: Obtiene saldos de puntos.
  --Fecha       Usuario	  Comentario
  --19/02/2015  ERIOS     Creacion          
  FUNCTION GET_SALDO_PUNTOS(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  RETURN VARCHAR2
  IS
	vRetorno VARCHAR2(100);
  BEGIN
	SELECT
	TRIM(TO_CHAR(NVL(CAB.PT_INICIAL,0),'999999990.00'))||'@'||
	GET_MONTO_MIN_PUNTOS||'@'|| -- 
	TRIM(TO_CHAR(NVL(CAB.VAL_NETO_PED_VTA,0),'999999990.00'))||'@'||
	(SELECT LLAVE_TAB_GRAL FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 488)||'@'||
  -- dubilluz 27.04.2015
  (SELECT LLAVE_TAB_GRAL FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 686) --  
			INTO vRetorno
	from VTA_PEDIDO_VTA_CAB CAB
	WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
	AND CAB.COD_LOCAL = cCodLocal_in
	AND CAB.NUM_PED_VTA = cNumPedVta_in;
	RETURN vRetorno;
  END;

  --Descripcion: Obtiene indicadores de mostrar puntos.
  --Fecha       Usuario	  Comentario
  --25/02/2015  ERIOS     Creacion  
  FUNCTION GET_MOSTRAR_PUNTOS(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  RETURN VARCHAR2
  IS
	vRetorno VARCHAR2(200);
  BEGIN
    SELECT
	TRIM(TO_CHAR(NVL(CAB.PT_ACUMULADO,0),'999999990.00'))||'@'||
	IND_VER_PUNTOS_ACUM||'@'||
	IND_VER_PUNTOS_REDI||'@'||
	TRIM(TO_CHAR(NVL(CAB.PT_INICIAL,0),'999999990.00'))||'@'||
	(SELECT LLAVE_TAB_GRAL FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 472)||':'||'@'||
	(SELECT LLAVE_TAB_GRAL FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 476)||':'||'@'||
--	(SELECT LLAVE_TAB_GRAL FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 469)||':'||'@'||
--  (SELECT LLAVE_TAB_GRAL FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 470)||':'
  (SELECT LLAVE_TAB_GRAL FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 504)||':'||'@'||
	(SELECT LLAVE_TAB_GRAL FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 505)||':'
			INTO vRetorno
	from VTA_PEDIDO_VTA_CAB CAB
	WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
	AND CAB.COD_LOCAL = cCodLocal_in
	AND CAB.NUM_PED_VTA = cNumPedVta_in;
	RETURN vRetorno;
  END;

  FUNCTION IND_VER_PUNTOS_ACUM RETURN VARCHAR2
  IS
    vRetorno VARCHAR2(10);
  BEGIN
    SELECT NVL(DESC_CORTA,'N') INTO vRetorno
	FROM PBL_TAB_GRAL 
	WHERE --ID_TAB_GRAL = 449;
	COD_APL = 'PTO_VENTA'
	AND COD_TAB_GRAL = 'IND_VER_PUNTOS_ACUM'
	AND LLAVE_TAB_GRAL = '01';
	RETURN vRetorno;
  END;  
  
  FUNCTION IND_VER_PUNTOS_REDI RETURN VARCHAR2
  IS
    vRetorno VARCHAR2(10);
  BEGIN
    SELECT NVL(DESC_CORTA,'N') || '@' || NVL(DESC_LARGA,'N') INTO vRetorno
	FROM PBL_TAB_GRAL 
	WHERE --ID_TAB_GRAL = 449;
	COD_APL = 'PTO_VENTA'
	AND COD_TAB_GRAL = 'IND_VER_PUNTOS_REDI'
	AND LLAVE_TAB_GRAL = '01';
	RETURN vRetorno;
  END;  

  --Descripcion: Verifica que la NCR no haya sido usado.
  --Fecha       Usuario	  Comentario
  --02/03/2015  ERIOS     Creacion    
  FUNCTION GET_USO_NCR(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, cTipoBusqueda_in IN CHAR DEFAULT 'NCR')
  RETURN VARCHAR2
  IS
	vRetorno VARCHAR2(10);
	nCantidad INTEGER;
  BEGIN
    IF cTipoBusqueda_in = 'NCR' THEN
		SELECT COUNT(1) INTO nCantidad
		FROM VTA_PEDIDO_VTA_CAB
		WHERE COD_GRUPO_CIA = cCodGrupoCia_in
		AND COD_LOCAL = cCodLocal_in
		AND NUM_PED_VTA = cNumPedVta_in
		AND NUM_PED_VTA_USO_NC IS NOT NULL;
	ELSIF cTipoBusqueda_in = 'ORI' THEN
		SELECT COUNT(1) INTO nCantidad
		FROM VTA_PEDIDO_VTA_CAB
		WHERE COD_GRUPO_CIA = cCodGrupoCia_in
		AND COD_LOCAL = cCodLocal_in
		AND NUM_PED_VTA_ORIGEN = cNumPedVta_in
		AND NUM_PED_VTA_USO_NC IS NOT NULL;
	END IF;
	
	SELECT DECODE(nCantidad,0,'N','S') INTO vRetorno
	FROM DUAL;
	
	RETURN vRetorno;
  END;  
  
  --Descripcion: Verifica la fecha de uso NCR.
  --Fecha       Usuario	  Comentario
  --03/03/2015  ERIOS     Creacion    
  FUNCTION GET_FECHA_NCR(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, cFechaNCR_in IN CHAR)
  RETURN VARCHAR2
  IS
	vRetorno VARCHAR2(10);
	nCantidad INTEGER;
	nDiferencia INTEGER;
  BEGIN
    
	SELECT TO_NUMBER(NVL(DESC_CORTA,'0')) DIAS,
		TRUNC(SYSDATE)-TO_DATE(cFechaNCR_in,'DD/MM/YYYY') DIF
		INTO nCantidad,nDiferencia
	FROM PBL_TAB_GRAL
	WHERE 
	COD_APL = 'PTO_VENTA'
	AND COD_TAB_GRAL = 'DIAS_USO_NCR'
	AND LLAVE_TAB_GRAL = '01'
	;
	
	IF nDiferencia > nCantidad THEN
		vRetorno := 'S';
	ELSE 
		vRetorno := 'N';
	END IF;
		
	RETURN vRetorno;
  END;

  --Descripcion: Verifica la NCR no sea comprobrante de credito.
  --Fecha       Usuario	  Comentario
  --04/03/2015  ERIOS     Creacion    
  FUNCTION GET_CREDITO_NCR(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, cFechaNCR_in IN CHAR)
  RETURN VARCHAR2
  IS
	vRetorno VARCHAR2(10);
  BEGIN
	SELECT NVL(IND_COMP_CREDITO,'N')
		INTO vRetorno
	FROM VTA_COMP_PAGO
	WHERE (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO) = (
						SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA_ORIGEN,SEC_COMP_PAGO
						FROM VTA_PEDIDO_VTA_CAB
						WHERE COD_GRUPO_CIA = cCodGrupoCia_in
						AND COD_LOCAL = cCodLocal_in
						AND NUM_PED_VTA = cNumPedVta_in
						);
	
	RETURN vRetorno;
  END;
  
  --Descripcion: Obtiene monto de la NCR.
  --Fecha       Usuario	  Comentario
  --19/03/2015  ERIOS     Creacion    
  FUNCTION GET_MONTO_NCR(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
  RETURN VARCHAR2
  IS
	vRetorno VARCHAR2(20);
  BEGIN
	SELECT TRIM(TO_CHAR(SUM(VAL_NETO_COMP_PAGO+VAL_REDONDEO_COMP_PAGO),'999999990.00'))
		INTO vRetorno
	FROM VTA_COMP_PAGO
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	AND COD_LOCAL = cCodLocal_in
	AND NUM_PED_VTA = ( SELECT NUM_PED_VTA_ORIGEN
						FROM VTA_PEDIDO_VTA_CAB
						WHERE COD_GRUPO_CIA = cCodGrupoCia_in
						AND COD_LOCAL = cCodLocal_in
						AND NUM_PED_VTA = cNumPedVta_in)
	AND NVL(IND_COMP_CREDITO,'N') = 'N';
	
	RETURN vRetorno;
  END;

  --Descripcion: Recalcula los montos del pedido.
  --Fecha       Usuario	  Comentario
  --23/03/2015  ERIOS     Creacion      
  PROCEDURE RECALCULO_REDENCION_PEDIDO(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in  IN CHAR       ,
                                          cNumPedVta_in  IN CHAR      ,
										  nImTotalPago_in IN NUMBER)
  AS
    v_nValNetoPedVta VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    
    CURSOR curComprobantes is
    select *
    from   vta_comp_pago g
    where  g.cod_grupo_cia = cCodGrupoCia_in
    and    g.cod_local = cCodLocal_in
    and    g.num_ped_vta = cNumPedVta_in;
    fSC curComprobantes%rowtype;
    vRedimeTodo CHAR(1) := 'N';
    vCantDetalle INTEGER;
    vPosicion INTEGER;
    vCantTotalDetalle NUMBER(9,2);
    vRestoElectronico CHAR(1) := 'N';
    vValRestoElectronico VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    vContarAfecto INTEGER := 0;
  BEGIN
	  --1.Calcula factor de puntos
    SELECT CAB.VAL_NETO_PED_VTA
      INTO v_nValNetoPedVta
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    SELECT COUNT(1)
    INTO vContarAfecto
    FROM VTA_PEDIDO_VTA_DET DET
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in
    AND DET.VAL_IGV != 0;
    
    IF v_nValNetoPedVta = nImTotalPago_in THEN
      vRedimeTodo := 'S';
    ELSE
      IF (v_nValNetoPedVta - nImTotalPago_in) < 0.04 THEN
        vRestoElectronico := 'S';
      END IF;
    END IF;
    
    
    
    UPDATE VTA_PEDIDO_VTA_DET DET
    SET DET.FACTOR_PUNTOS = ROUND((DET.VAL_PREC_TOTAL/v_nValNetoPedVta),2)
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in;
    
    UPDATE VTA_PEDIDO_VTA_DET DET
    SET DET.FACTOR_PUNTOS = 1-NVL((SELECT SUM(NVL(AUX.FACTOR_PUNTOS,0))
                                FROM VTA_PEDIDO_VTA_DET AUX
                              WHERE AUX.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND AUX.COD_LOCAL = cCodLocal_in
                              AND AUX.NUM_PED_VTA = cNumPedVta_in
                              AND AUX.SEC_PED_VTA_DET > 1),0)
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in
    AND DET.SEC_PED_VTA_DET = 1;
    
    --2. Prorrateo de ahorro puntos dinero
    UPDATE VTA_PEDIDO_VTA_DET DET
    SET DET.AHORRO_PUNTOS = CASE 
                              WHEN vRedimeTodo = 'S' THEN
                                VAL_PREC_TOTAL
                              ELSE  
                                ROUND((nImTotalPago_in*DET.FACTOR_PUNTOS),2)
                            END
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in;
    
    UPDATE VTA_PEDIDO_VTA_DET DET
    SET DET.AHORRO_PUNTOS = nImTotalPago_in-NVL((SELECT SUM(NVL(AUX.AHORRO_PUNTOS,0))
                                FROM VTA_PEDIDO_VTA_DET AUX
                              WHERE AUX.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND AUX.COD_LOCAL = cCodLocal_in
                              AND AUX.NUM_PED_VTA = cNumPedVta_in
                              AND AUX.SEC_PED_VTA_DET > 1),0)
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in
    AND DET.SEC_PED_VTA_DET = 1;
    
    --3. Salvar datos actuales
    UPDATE VTA_PEDIDO_VTA_DET DET
    SET DET.VAL_PREC_LISTA = DET.VAL_PREC_VTA/*,
        DET.AHORRO_CAMP = DET.AHORRO */--?Consultar si va el cambio por el tema de reportes
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in;
    
    
    
    --4. Recalculo de montos
    UPDATE VTA_PEDIDO_VTA_DET DET
    SET DET.VAL_PREC_TOTAL = CASE 
                               WHEN vRedimeTodo = 'S' THEN
                                 0.01
                               WHEN DET.VAL_PREC_TOTAL-DET.AHORRO_PUNTOS < 0 THEN
                                 0
                               ELSE
                                 DET.VAL_PREC_TOTAL-DET.AHORRO_PUNTOS
                             END,
        --DET.AHORRO = DET.AHORRO_CAMP+DET.AHORRO_PUNTOS--?Consultar si va el cambio por el tema de reportes
        DET.AHORRO = NVL(DET.AHORRO_CAMP,0) + NVL(DET.AHORRO_PACK,0) + NVL(DET.AHORRO_PUNTOS,0)--?Consultar si va el cambio por el tema de reportes
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in;
    
    -- KMONCADA 02.06.2015 SE AGREGARA AL DETALLE UN TOTAL DE 0.04 PARA CUANDO SE REDIMA TODO
    -- EL PEDIDO Y EL DETALLE TENGA MENOS DE 4 ITEMS
    IF vContarAfecto > 0 THEN
    IF vRedimeTodo = 'S' THEN
      SELECT COUNT(1)
      INTO vCantDetalle
      FROM VTA_PEDIDO_VTA_DET DET
      WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA = cNumPedVta_in
      AND DET.VAL_IGV != 0;
      vPosicion := 1;
      vCantTotalDetalle := 0.04 - (vCantDetalle*0.01);
      IF vCantDetalle < 4 THEN
        WHILE vCantTotalDetalle > 0
        LOOP
          UPDATE VTA_PEDIDO_VTA_DET DET
          SET DET.VAL_PREC_TOTAL = DET.VAL_PREC_TOTAL + 0.01
          WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
          AND DET.COD_LOCAL = cCodLocal_in
          AND DET.NUM_PED_VTA = cNumPedVta_in
          AND DET.SEC_PED_VTA_DET = vPosicion
          AND DET.VAL_IGV != 0;
          IF NOT SQL%NOTFOUND THEN
            vCantTotalDetalle := vCantTotalDetalle - 0.01;
          END IF;
          vPosicion := vPosicion + 1;
          IF vPosicion > vCantDetalle THEN
            vPosicion := 1;
          END IF;
          
        END LOOP;
      END IF;
    ELSE
      IF vRestoElectronico = 'S' THEN
        SELECT COUNT(1)
        INTO vCantDetalle
        FROM VTA_PEDIDO_VTA_DET DET
        WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND DET.COD_LOCAL = cCodLocal_in
        AND DET.NUM_PED_VTA = cNumPedVta_in
        AND DET.VAL_IGV != 0;
      
        SELECT SUM(DET.VAL_PREC_TOTAL)
        INTO vValRestoElectronico
        FROM VTA_PEDIDO_VTA_DET DET
        WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND DET.COD_LOCAL = cCodLocal_in
        AND DET.NUM_PED_VTA = cNumPedVta_in
        AND DET.VAL_IGV != 0; 
        vCantTotalDetalle := 0.04 - vValRestoElectronico;
        vPosicion := 1;
          WHILE vCantTotalDetalle > 0
          LOOP
            UPDATE VTA_PEDIDO_VTA_DET DET
            SET DET.VAL_PREC_TOTAL = DET.VAL_PREC_TOTAL + 0.01
            WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
            AND DET.COD_LOCAL = cCodLocal_in
            AND DET.NUM_PED_VTA = cNumPedVta_in
            AND DET.SEC_PED_VTA_DET = vPosicion
            AND DET.VAL_IGV != 0; 
            IF NOT SQL%NOTFOUND THEN
              vCantTotalDetalle := vCantTotalDetalle - 0.01;
            END IF;
            vPosicion := vPosicion + 1;
            IF vPosicion > vCantDetalle THEN
              vPosicion := 1;
            END IF;
            
          END LOOP;
        
      END IF;
    END IF;
    END IF;
    
    UPDATE VTA_PEDIDO_VTA_DET DET
    SET DET.VAL_PREC_VTA = DET.VAL_PREC_TOTAL/DET.CANT_ATENDIDA
    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DET.COD_LOCAL = cCodLocal_in
    AND DET.NUM_PED_VTA = cNumPedVta_in;
    
    UPDATE VTA_PEDIDO_VTA_CAB CAB
    SET CAB.VAL_NETO_PED_VTA = (SELECT SUM(DET.VAL_PREC_TOTAL) FROM VTA_PEDIDO_VTA_DET DET
                                WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND DET.COD_LOCAL = cCodLocal_in
                                AND DET.NUM_PED_VTA = cNumPedVta_in)+CAB.VAL_REDONDEO_PED_VTA,
        CAB.VAL_IGV_PED_VTA = (SELECT SUM(DET.VAL_PREC_TOTAL*DET.VAL_IGV/100) FROM VTA_PEDIDO_VTA_DET DET
                                WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND DET.COD_LOCAL = cCodLocal_in
                                AND DET.NUM_PED_VTA = cNumPedVta_in)
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    --5. Actualiza campos para Fact. Electronico
     open curComprobantes;
     LOOP
     FETCH curComprobantes INTO fSC;
     EXIT WHEN curComprobantes%NOTFOUND;
                 /* ****************************************************************** */
                --Descripcion: Actulizar los campos nuevo de la tabla VTA_COMP_PAGO
                --Fecha       Usuario        Comentario
                --22/07/2014  LTAVARA         Creacion
                --07/10/2014  DUBILLUZ        Modificacion
                FARMA_EPOS.sp_upd_comp_pago_e(fSC.Cod_Grupo_Cia,
                                                    fSC.Cod_Local,
                                                    fSC.Num_Ped_Vta,
                                                    fSC.Sec_Comp_Pago,
                                                    fSC.Tip_Clien_Convenio); 
     END LOOP;
     close curComprobantes;
     
     --6. Recalculo de puntos acumulados
     FARMA_PUNTOS.F_I_CALCULA_PUNTOS(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,'S','S');
     
  END;  

  --Descripcion: Indicador de impresion de puntos redimidos en doc. elect.
  --Fecha       Usuario	  Comentario
  --23/03/2015  ERIOS     Creacion                          
  FUNCTION IND_IMPR_PUNTOS_REDI(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cNumPedVta_in IN CHAR) RETURN VARCHAR2
  IS
    vRetorno VARCHAR2(10);
  BEGIN
    vRetorno := FARMA_PUNTOS.F_VAR_IND_ACT_PUNTOS(cCodGrupoCia_in,
                                cCodLocal_in);
    IF vRetorno = 'S' THEN 
      SELECT LLAVE_TAB_GRAL
        INTO vRetorno
      FROM PBL_TAB_GRAL
      WHERE ID_TAB_GRAL = 475;
    END IF; 
    
    IF vRetorno = 'S' THEN
      BEGIN
      SELECT DECODE(NVL(CAB.PT_REDIMIDO,0),0,'N','S')
          INTO vRetorno
       FROM VTA_PEDIDO_VTA_CAB CAB
       WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CAB.COD_LOCAL = cCodLocal_in
       AND CAB.NUM_PED_VTA   = cNumPedVta_in;
      EXCEPTION
       WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20000, 'Fatal error.:'||cNumPedVta_in);
      END;
    END IF;
    
	  RETURN vRetorno;
  END;

  --Descripcion: Mensaje de impresion de puntos redimidos en doc. elect.
  --Fecha       Usuario	  Comentario
  --23/03/2015  ERIOS     Creacion                          
  FUNCTION MSJ_IMPR_PUNTOS_REDI RETURN VARCHAR2
    IS
    vRetorno VARCHAR2(100);
  BEGIN
    SELECT LLAVE_TAB_GRAL
      INTO vRetorno
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL = 483;
	RETURN vRetorno;
  END;

  --Descripcion: Retorna los indicadores para venta online.
  --Fecha       Usuario	  Comentario
  --27/03/2015  ERIOS     Creacion      
  FUNCTION GET_INDICADORES_VENTA(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR) 
  RETURN VARCHAR2
  IS
    vRetorno VARCHAR2(100);
  BEGIN
    SELECT DECODE(NVL(CAB.PT_REDIMIDO,0),0,'N','S')||'@'||
      FARMA_PUNTOS.F_IND_ACUMULA_REDIME
        INTO vRetorno
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    RETURN vRetorno;
  END;  
  
  FUNCTION GET_MULTIPLO_PTO(cCodGrupoCia_in IN CHAR, 
                               cCodCia_in IN CHAR, 
                               cCodLocal_in IN CHAR) 
  RETURN VARCHAR2
  IS
    vRetorno VARCHAR2(100);
  BEGIN
     begin
      select t.llave_tab_gral
      into   vRetorno
      from   pbl_tab_gral t
      where  id_tab_gral = 682;
     exception
     when others then
       vRetorno := '0';
     end; 
    RETURN vRetorno;
  END;
  
  --Descripcion: Registra de inscripcion por turno.
  --Fecha       Usuario	  Comentario
  --22/06/2015  ERIOS     Creacion      
  PROCEDURE REGISTRA_INSCRIPCION_TURNO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cSecMovCaja_in IN CHAR, 
										vCodTarjeta_in IN VARCHAR2, vIdUsu_in IN VARCHAR2)
  IS
  BEGIN
	INSERT INTO PTOVENTA.CE_MOV_CAJA_TARJETA(COD_GRUPO_CIA,COD_CIA,COD_LOCAL,SEC_MOV_CAJA,COD_TARJETA,USU_CREA)
	VALUES(cCodGrupoCia_in, cCodCia_in, cCodLocal_in, cSecMovCaja_in, vCodTarjeta_in, vIdUsu_in);
  END;

  --Descripcion: Obtiene de inscripcion por turno.
  --Fecha       Usuario	  Comentario
  --22/06/2015  ERIOS     Creacion      
  FUNCTION GET_INSCRIPCION_TURNO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cSecMovCaja_in IN CHAR) RETURN VARCHAR2
  IS
	v_vRetorno VARCHAR2(100);
  BEGIN
	SELECT COUNT(1)
		INTO v_vRetorno
	FROM PTOVENTA.CE_MOV_CAJA_TARJETA
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	AND COD_CIA = cCodCia_in
	AND COD_LOCAL = cCodLocal_in
	AND SEC_MOV_CAJA = (SELECT SEC_MOV_CAJA_ORIGEN FROM PTOVENTA.CE_MOV_CAJA WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND 
																					COD_LOCAL = cCodLocal_in AND SEC_MOV_CAJA = cSecMovCaja_in)
	;
	RETURN v_vRetorno;
  END;    
END FARMA_LEALTAD;
/
