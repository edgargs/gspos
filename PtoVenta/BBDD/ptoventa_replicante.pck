CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_REPLICANTE" AS

--Descripcion: Graba respaldo
--Fecha       Usuario		Comentario
--2.4.8       ERIOS         Creacion
  PROCEDURE REGISTRA_PEDIDO_RESPALDO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, vIdUsu_in IN VARCHAR) ;

--Descripcion: Inserta registros faltantes
--Fecha       Usuario		Comentario
--2.4.8       ERIOS         Creacion
  PROCEDURE ACTUALIZA_TABLA(vTabla_in IN VARCHAR2, vTablaAux_in IN VARCHAR2, cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR);
  
--Descripcion: Procesa replicante
--Fecha       Usuario		Comentario
--2.4.8       ERIOS         Creacion
  PROCEDURE PROCESA_TABLAS;  

 TYPE DocumentoCursor IS REF CURSOR;

	FUNCTION GET_REENVIO_COMP(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cTipCompE_in IN CHAR, cSerieCompE_in IN CHAR, vNumCompE_in IN VARCHAR2)
	RETURN DocumentoCursor;	
  
END PTOVENTA_REPLICANTE;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_REPLICANTE" AS

--Descripcion: Graba respaldo
--Fecha       Usuario		Comentario
--2.4.8       ERIOS         Creacion
  PROCEDURE REGISTRA_PEDIDO_RESPALDO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, vIdUsu_in IN VARCHAR)
  IS
	v_CodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN

    v_CodCia := cCodCia_in;
	IF v_CodCia IS NULL THEN
		SELECT COD_CIA INTO v_CodCia
		FROM PBL_LOCAL
		WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND COD_LOCAL = cCodLocal_in;
	END IF;
	
	INSERT INTO RPL_VTA_PEDIDO(COD_GRUPO_CIA,COD_CIA,COD_LOCAL,NUM_PED_VTA,USU_CREA)
	VALUES(cCodGrupoCia_in,v_CodCia,cCodLocal_in,cNumPedVta_in,vIdUsu_in);
  END;
  
	--Descripcion: Inserta registros faltantes
	--Fecha       Usuario		Comentario
	--2.4.8       ERIOS         Creacion
	PROCEDURE ACTUALIZA_TABLA(vTabla_in IN VARCHAR2, vTablaAux_in IN VARCHAR2, cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR)
	IS
	  CURSOR cols IS
	  SELECT COLUMN_NAME,DATA_TYPE,COLUMN_ID
	  FROM ALL_TAB_COLS
	  WHERE TABLE_NAME = vTabla_in
	  ORDER BY COLUMN_ID;
	  v_cols VARCHAR2(2000);
	  v_sentencia VARCHAR2(6000);
	BEGIN
		
		FOR col IN cols
		LOOP
		  IF col.COLUMN_ID = 1 THEN
			v_cols := v_cols || col.COLUMN_NAME ;
		  ELSE
			v_cols := v_cols ||','|| col.COLUMN_NAME ;
		  END IF;
	   END LOOP;
	   
	   v_sentencia := 'INSERT INTO '||vTabla_in||'('||v_cols||') ';
	   v_sentencia := v_sentencia || 'SELECT '||v_cols||' FROM '||vTablaAux_in||' T ';
	   v_sentencia := v_sentencia || 'WHERE T.COD_GRUPO_CIA = &1 AND T.COD_LOCAL = &2 AND T.NUM_PED_VTA = &3 ';
	   dbms_output.put_line(v_sentencia);
	   EXECUTE IMMEDIATE v_sentencia USING cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in;
	   
	END;
  
--Descripcion: Procesa replicante
--Fecha       Usuario		Comentario
--2.4.8       ERIOS         Creacion
  PROCEDURE PROCESA_TABLAS
  IS	
	--Recupera pedidos
	v_sentencia VARCHAR2(6000) := 'SELECT *
	FROM RPL_PEDIDO_VTA_CAB T
	WHERE NOT EXISTS (SELECT 1 FROM VTA_PEDIDO_VTA_CAB X WHERE X.COD_GRUPO_CIA = T.COD_GRUPO_CIA AND X.COD_LOCAL = T.COD_LOCAL AND X.NUM_PED_VTA = T.NUM_PED_VTA)'
	;
	TYPE FarmaCursor IS REF CURSOR;
	pedidos FarmaCursor;
	pedido VTA_PEDIDO_VTA_CAB%ROWTYPE;
	
	CURSOR comprobantes(cCodGrupoCia IN CHAR, cCodLocal IN CHAR,  cNumPedVta IN CHAR) IS
	SELECT * FROM VTA_COMP_PAGO
	WHERE COD_GRUPO_CIA = cCodGrupoCia
	AND COD_LOCAL = cCodLocal
	AND NUM_PED_VTA = cNumPedVta;
	v_cCodMotKardex LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE;
		
	vTipoComprobantePago VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%type;
	vIndAfectaKardex_in vta_comp_pago.IND_AFECTA_KARDEX%type;
  BEGIN
    
	OPEN pedidos FOR v_sentencia;
	LOOP
		FETCH pedidos INTO pedido;
		EXIT WHEN pedidos%NOTFOUND;
		dbms_output.put_line('Recupera :'||pedido.NUM_PED_VTA);
	 
		PTOVENTA_REPLICANTE.ACTUALIZA_TABLA('VTA_PEDIDO_VTA_CAB','RPL_PEDIDO_VTA_CAB',pedido.COD_GRUPO_CIA,pedido.COD_LOCAL,pedido.NUM_PED_VTA);
		PTOVENTA_REPLICANTE.ACTUALIZA_TABLA('VTA_COMP_PAGO','RPL_COMP_PAGO',pedido.COD_GRUPO_CIA,pedido.COD_LOCAL,pedido.NUM_PED_VTA);
		PTOVENTA_REPLICANTE.ACTUALIZA_TABLA('VTA_PEDIDO_VTA_DET','RPL_PEDIDO_VTA_DET',pedido.COD_GRUPO_CIA,pedido.COD_LOCAL,pedido.NUM_PED_VTA);
		PTOVENTA_REPLICANTE.ACTUALIZA_TABLA('VTA_FORMA_PAGO_PEDIDO','RPL_FORMA_PAGO_PEDIDO',pedido.COD_GRUPO_CIA,pedido.COD_LOCAL,pedido.NUM_PED_VTA);
		PTOVENTA_REPLICANTE.ACTUALIZA_TABLA('CON_BTL_MF_PED_VTA','RPL_BTL_MF_PED_VTA',pedido.COD_GRUPO_CIA,pedido.COD_LOCAL,pedido.NUM_PED_VTA);
		--Kardex		
		IF pedido.VAL_NETO_PED_VTA < 0 THEN
				SELECT  A.TIP_COMP_PAGO
				INTO    vTipoComprobantePago
				FROM    VTA_PEDIDO_VTA_CAB A
				WHERE   A.COD_GRUPO_CIA = pedido.COD_GRUPO_CIA
				AND     A.COD_LOCAL = pedido.COD_LOCAL
				AND     A.NUM_PED_VTA = pedido.NUM_PED_VTA;
				
				-- KMONCADA 09.07.14 NO GENERA NC DE GUIAS 
				IF vTipoComprobantePago = '03' THEN 
				-- KMONCADA 09.07.14 ANULA LA GUIA DE REMISION
				  UPDATE VTA_COMP_PAGO
				  SET    IND_COMP_ANUL = 'S',
						 FEC_ANUL_COMP_PAGO = SYSDATE,
						 NUM_PEDIDO_ANUL   = pedido.NUM_PED_VTA,
						 SEC_MOV_CAJA_ANUL = pedido.SEC_MOV_CAJA
				  WHERE  COD_GRUPO_CIA = pedido.COD_GRUPO_CIA
				  AND    COD_LOCAL = pedido.COD_LOCAL
				  AND    NUM_PED_VTA = pedido.NUM_PED_VTA_ORIGEN
				  AND    SEC_COMP_PAGO = pedido.SEC_COMP_PAGO;
				END IF;
				
				  -- actualiza kardex x cada comprobante
				  -- SOLO SI EL COMPROBANTE ORIGINAL FUE EL QUE MOVIO KARDEX
				  select  nvl(IND_AFECTA_KARDEX,'S')
				  into    vIndAfectaKardex_in
				  from    vta_comp_pago cp
				  where   cp.cod_grupo_cia = pedido.COD_GRUPO_CIA
				  and     cp.cod_local = pedido.COD_LOCAL
				  and     cp.num_ped_vta = pedido.NUM_PED_VTA_ORIGEN
				  and     cp.sec_comp_pago = pedido.SEC_COMP_PAGO;
				  
				  if vIndAfectaKardex_in = 'S' then
					PTOVENTA_CAJ_ANUL.CAJ_ACTUALIZA_STK_PROD_DETALLE(pedido.COD_GRUPO_CIA,pedido.COD_LOCAL,pedido.NUM_PED_VTA,pedido.NUM_PED_VTA_ORIGEN,
								   '106',--cCodMotKardex_in,
								   '01',--cTipDocKardex_in,
								   '016',--cCodNumeraKardex_in,
										   'REPLICANTE');
				end if;
		ELSE
			/*MOT_KARDEX_VENTA_NORMAL := '001';
			MOT_KARDEX_VENTA_DELIVERY := '002';
			MOT_KARDEX_VENTA_ESPECIAL := '003;*/
			IF pedido.TIP_PED_VTA = '01' THEN v_cCodMotKardex := '001';
			ELSIF pedido.TIP_PED_VTA = '02' THEN v_cCodMotKardex := '002';
			ELSIF pedido.TIP_PED_VTA = '03' THEN v_cCodMotKardex := '003';
			END IF;
			PTOVTA_RESPALDO_STK.CAJ_UPD_STK_PROD_DETALLE(pedido.COD_GRUPO_CIA,pedido.COD_LOCAL,pedido.NUM_PED_VTA,
															v_cCodMotKardex,
															'01', --TIP_DOC_KARDEX_VENTA
															'016', --COD_NUMERA_SEC_KARDEX
															'REPLICANTE');
		END IF;
	END LOOP;
	/*--Actualiza numeras
	--007	CORRELATIVO DE PEDIDO
	UPDATE PBL_NUMERA
	SET VAL_NUMERA = (SELECT MAX(NUM_PED_VTA)+1 FROM VTA_PEDIDO_VTA_CAB)
	WHERE COD_NUMERA = '007';
	--009	NUMERO DE PEDIDO DIARIO
	UPDATE PBL_NUMERA
	SET VAL_NUMERA = (SELECT NVL(MAX(NUM_PED_DIARIO),0)+1 FROM VTA_PEDIDO_VTA_CAB WHERE FEC_PED_VTA > TRUNC(SYSDATE))
	WHERE COD_NUMERA = '009';	
	--015	SECUENCIAL DE COMPROBANTE DE PAGO
	UPDATE PBL_NUMERA
	SET VAL_NUMERA = (SELECT MAX(SEC_COMP_PAGO)+1 FROM VTA_COMP_PAGO)
	WHERE COD_NUMERA = '015';	*/
  END;    
  
  FUNCTION GET_REENVIO_COMP(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cTipCompE_in IN CHAR, cSerieCompE_in IN CHAR, vNumCompE_in IN VARCHAR2)
  RETURN DocumentoCursor
  IS
    cur DocumentoCursor;
  BEGIN
    OPEN cur FOR
    SELECT P.TIP_COMP_PAGO vTipComp,
	P.NUM_PED_VTA vNumPed,
	P.SEC_COMP_PAGO vSecComp,
	nvl(P.TIP_CLIEN_CONVENIO,' ') vTipCli,
    C.TIP_COMP_PAGO vTipCompPed,
    N.NUM_RUC_CIA vRucCia,
	NUM_COMP_PAGO_E vNumCompEAux
    FROM VTA_COMP_PAGO P JOIN VTA_PEDIDO_VTA_CAB C ON (C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                              AND C.COD_LOCAL = P.COD_LOCAL
                              AND C.NUM_PED_VTA = P.NUM_PED_VTA),      
      (SELECT NUM_RUC_CIA
      FROM PBL_CIA
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_CIA = cCodCia_in) N
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
    AND P.COD_LOCAL = cCodLocal_in
    AND P.FEC_CREA_COMP_PAGO > TRUNC(SYSDATE)
    AND P.NUM_COMP_PAGO_E IS NOT NULL
    AND P.TIP_COMP_PAGO = cTipCompE_in
        AND substr(P.NUM_COMP_PAGO_E,1,4) = cSerieCompE_in
    AND substr(P.NUM_COMP_PAGO_E,-8) > vNumCompE_in
    ORDER BY P.NUM_COMP_PAGO_E
    ;
    RETURN cur;
  END;
  
END PTOVENTA_REPLICANTE;
/

