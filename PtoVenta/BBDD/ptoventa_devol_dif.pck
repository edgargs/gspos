CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_DEVOL_DIF" AS

  TYPE FarmaCursor IS REF CURSOR;
  
  PROCEDURE P_GRABA_DIFERENCIA(cCodGrupoCia_in IN CHAR,
                         cCodCia_in IN CHAR,
						 cCodLocal_in IN CHAR,
						 cNumPedVta_in IN CHAR,
						 nMontoDif_in IN NUMBER,
						 vUsuCrea_in IN VARCHAR2);
  
  FUNCTION F_GET_VOUCHER(cCodGrupoCia_in IN CHAR,
                         cCodCia_in IN CHAR,
						 cCodLocal_in IN CHAR,
						 cNumPedVta_in IN CHAR) RETURN FarmaCursor;
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_DEVOL_DIF" AS

  PROCEDURE P_GRABA_DIFERENCIA(cCodGrupoCia_in IN CHAR,
                         cCodCia_in IN CHAR,
						 cCodLocal_in IN CHAR,
						 cNumPedVta_in IN CHAR,
						 nMontoDif_in IN NUMBER,
						 vUsuCrea_in IN VARCHAR2) IS
  BEGIN
    INSERT INTO PTOVENTA.CAJ_DEVOLUCION_DIFERENCIA(COD_GRUPO_CIA,COD_CIA,COD_LOCAL,NUM_PED_VTA,EST_DEV_DIF,USU_CREA,VAL_DIF)
	     VALUES(cCodGrupoCia_in,cCodCia_in,cCodLocal_in,cNumPedVta_in,'A',vUsuCrea_in,nMontoDif_in);
  END;
  
  FUNCTION F_GET_VOUCHER(cCodGrupoCia_in IN CHAR,
                         cCodCia_in IN CHAR,
						 cCodLocal_in IN CHAR,
						 cNumPedVta_in IN CHAR) RETURN FarmaCursor IS
  
    cursorComprobante    FarmaCursor;
	vIdDoc               VARCHAR2(30);
    vIpPc                VARCHAR2(30);
	
	v_vMonto VARCHAR2(100);
	v_vFechaEmision VARCHAR2(100);
	v_nExisteDevol INTEGER;
	
	vVendedor varchar2(300);
  BEGIN
  
    SELECT COUNT(1)
	  INTO v_nExisteDevol
	FROM PTOVENTA.CAJ_DEVOLUCION_DIFERENCIA
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	AND COD_CIA = cCodCia_in
	AND COD_LOCAL = cCodLocal_in
	AND NUM_PED_VTA = cNumPedVta_in;
	
	IF v_nExisteDevol = 0 THEN
	  GOTO retorno;
	END IF;
	
    SELECT TO_CHAR(VAL_DIF,'99,990.00'), TO_CHAR(FEC_CREA,'DD/MM/YYYY')
	  INTO v_vMonto, v_vFechaEmision
	FROM PTOVENTA.CAJ_DEVOLUCION_DIFERENCIA
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	AND COD_CIA = cCodCia_in
	AND COD_LOCAL = cCodLocal_in
	AND NUM_PED_VTA = cNumPedVta_in;
	
	     --Obtenemos el nombre del vendedor.
     BEGIN 
     SELECT USUARIO.NOM_USU||' '||USUARIO.APE_PAT||' '||USUARIO.APE_MAT 
     INTO   vVendedor
     FROM VTA_PEDIDO_VTA_CAB CAB,
          PBL_USU_LOCAL USUARIO
     WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
     AND   CAB.COD_LOCAL     = cCodLocal_in
     AND   CAB.NUM_PED_VTA   = cNumPedVta_in
     AND   CAB.COD_GRUPO_CIA = USUARIO.COD_GRUPO_CIA
     AND   CAB.COD_LOCAL     = USUARIO.COD_LOCAL
     AND   CAB.SEC_USU_LOCAL = USUARIO.SEC_USU_LOCAL;
     EXCEPTION 
       WHEN NO_DATA_FOUND THEN
         SELECT USUARIO.NOM_USU || ' ' || USUARIO.APE_PAT || ' ' || USUARIO.APE_MAT
           INTO vVendedor
           FROM VTA_PEDIDO_VTA_CAB CAB, PBL_USU_LOCAL USUARIO
          WHERE CAB.COD_GRUPO_CIA        = cCodGrupoCia_in
            AND CAB.COD_LOCAL            = cCodLocal_in
            AND CAB.NUM_PED_VTA          = cNumPedVta_in
            AND CAB.COD_GRUPO_CIA        = USUARIO.COD_GRUPO_CIA
            AND CAB.COD_LOCAL            = USUARIO.COD_LOCAL
            AND CAB.USU_CREA_PED_VTA_CAB = USUARIO.LOGIN_USU;
     END;
    
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
	
	-- TITULO
	FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'DEVOLUCION',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                              vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
	FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
	-- VENDEDOR
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Vendedor:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vVendedor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
	
	--FECHA
	FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('F. Emision:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
	FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => v_vFechaEmision,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
											 
	-- CODIGO DE BARRA
	FARMA_PRINTER.P_AGREGA_BARCODE_CODE39(vIdDoc_in => vIdDoc,
                                               vIpPc_in => vIpPc,
                                               vValor_in => cCodLocal_in||cNumPedVta_in);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
											 
    -- FIRMA
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Firma:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
                                             
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                        
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => RPAD(' ',12)||'___________________________________',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);

	<<retorno>>										 
    cursorComprobante := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                                       vIpPc_in => vIpPc);
    
	RETURN cursorComprobante;
  END;
END;
/
