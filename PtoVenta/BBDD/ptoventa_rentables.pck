CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_RENTABLES" AS


  TYPE FarmaCursor IS REF CURSOR;
  
  -- Descripcion: Obtiene puntos
  -- Fecha       Usuario		Comentario
  --18/04/2016   ERIOS          Creacion
  --31/08/2016   ERIOS          Se agrega el parametro cIndMostrar_in
    FUNCTION GET_PUNTOS(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProd_in	  	IN CHAR,
						cIndMostrar_in IN CHAR DEFAULT 'N')
	RETURN NUMBER;

  -- Descripcion: Lista programa rentables
  -- Fecha       Usuario		Comentario
  --18/07/2016   ERIOS          Creacion	
	FUNCTION F_GET_PROGRAMA_RENTABLES(cCodGrupoCia_in	  IN CHAR,
										cCodLocal_in IN CHAR)
	RETURN FarmaCursor;

  -- Descripcion: Lista presupuesto por vendedor
  -- Fecha       Usuario		Comentario
  --18/07/2016   ERIOS          Creacion		
	FUNCTION F_GET_PRESUPUESTO_VENDEDOR(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProg_in	  	IN CHAR,
						cCodLocal_in IN CHAR)
    RETURN FarmaCursor;						

  -- Descripcion: Lista de vendedores
  -- Fecha       Usuario		Comentario
  --19/07/2016   ERIOS          Creacion		
  FUNCTION F_LISTA_USUARIOS_LOCAL(cCodGrupoCia_in    IN CHAR,
  		   				            cCodLocal_in	   IN CHAR,
									cCodProg_in IN CHAR)
  RETURN FarmaCursor;

  -- Descripcion: Datos de vendedor
  -- Fecha       Usuario		Comentario
  --19/07/2016   ERIOS          Creacion
  FUNCTION F_DATOS_USUARIOS_LOCAL(cCodGrupoCia_in    IN CHAR,
  		   				            cCodLocal_in	   IN CHAR,
									cSecUsuLocal_in    IN CHAR)
  RETURN FarmaCursor;

  -- Descripcion: Borra datos Presupuesto Vendedor
  -- Fecha       Usuario		Comentario
  --19/07/2016   ERIOS          Creacion
	PROCEDURE P_LIMPIA_PRESUPUESTO_VENDEDOR(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProg_in	  	IN CHAR,
						cCodLocal_in IN CHAR);  

  -- Descripcion: Agrega Presupuesto Vendedor
  -- Fecha       Usuario		Comentario
  --19/07/2016   ERIOS          Creacion
	PROCEDURE P_SET_PRESUPUESTO_VENDEDOR(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProg_in	  	IN CHAR,
						cCodLocal_in IN CHAR,
						cSecUsuLocal_in    IN CHAR,
						nVolumen_in IN NUMBER,
						nLLEE_in IN NUMBER,
						vUsuCrea_in IN VARCHAR);						

  -- Descripcion: Valida Presupuesto Vendedor
  -- Fecha       Usuario		Comentario
  --19/07/2016   ERIOS          Creacion
	PROCEDURE P_VALIDA_PRESUPUESTO_VENDEDOR(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProg_in	  	IN CHAR,
						cCodLocal_in IN CHAR,
						cIsOperador_in IN CHAR);

  -- Descripcion: Valida dias para registrar
  -- Fecha       Usuario		Comentario
  --21/07/2016   ERIOS          Creacion
	FUNCTION F_VALIDA_DIAS_REGISTRAR(cCodGrupoCia_in IN CHAR,
										cCodProg_in IN CHAR,
										cCodLocal_in IN CHAR)
	RETURN VARCHAR2;

  -- Descripcion: Envia informacion de registro pendiente
  -- Fecha       Usuario		Comentario
  --22/07/2016   ERIOS          Creacion
	PROCEDURE ENV_ALERTA_REGISTRO_PPTO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR);
	
END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_RENTABLES" AS

	FUNCTION GET_PUNTOS(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProd_in	  	IN CHAR,
						cIndMostrar_in IN CHAR DEFAULT 'N')
	RETURN NUMBER
	IS
	  nRetorno PROG_RENTABLES_DET.PTS_PROG%TYPE;
	BEGIN
	  --ERIOS 29.08.2016 Solicitud de JOLIVA
	  nRetorno := 0.0;
	  IF cIndMostrar_in = 'S' THEN 
		 BEGIN
			 SELECT D.PTS_PROG INTO nRetorno
			 FROM PTOVENTA.PROG_RENTABLES C JOIN PTOVENTA.PROG_RENTABLES_DET D 
												ON (C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
													AND C.COD_PROG = D.COD_PROG)
			 WHERE TRUNC(SYSDATE) BETWEEN C.FEC_INI AND C.FEC_FIN
			 AND C.EST_PROG = 'A'
			 AND D.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND D.COD_PROD =cCodProd_in;
		 EXCEPTION
			WHEN NO_DATA_FOUND THEN
			  nRetorno := 0.0;
		 END;
	 END IF;
	 RETURN nRetorno;
	END;
	
	FUNCTION F_GET_PROGRAMA_RENTABLES(cCodGrupoCia_in	  IN CHAR,
										cCodLocal_in IN CHAR)
	RETURN FarmaCursor
	IS
	  v_cRetorno FarmaCursor;
	BEGIN
	  OPEN v_cRetorno FOR
	  SELECT PPTO.COD_PROG || 'Ã' ||
			RENT.DESC_PROG || 'Ã' ||
			TO_CHAR(PPTO.PPTO_VOLUMEN,'999,990.00') || 'Ã' ||
			TO_CHAR(PPTO.PPTO_LLEE,'999,990.00') || 'Ã' ||
			CASE WHEN (SELECT COUNT(1) FROM PTOVENTA.PROG_RENTABLES_PPTO_LOC LOC
			  WHERE LOC.COD_GRUPO_CIA = PPTO.COD_GRUPO_CIA
			  AND LOC.COD_PROG = PPTO.COD_PROG
			  AND LOC.COD_LOCAL = PPTO.COD_LOCAL) > 0 THEN 'TRABAJADO'
			  ELSE 'PENDIENTE' END
		FROM PTOVENTA.PROG_RENTABLES_PPTO PPTO JOIN 
		PTOVENTA.PROG_RENTABLES RENT ON (PPTO.COD_GRUPO_CIA = RENT.COD_GRUPO_CIA
										AND PPTO.COD_PROG = RENT.COD_PROG)
		WHERE PPTO.COD_GRUPO_CIA = cCodGrupoCia_in
		AND PPTO.COD_LOCAL = cCodLocal_in;
	  RETURN v_cRetorno;
	END;
	
	FUNCTION F_GET_PRESUPUESTO_VENDEDOR(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProg_in	  	IN CHAR,
						cCodLocal_in IN CHAR)
	RETURN FarmaCursor
	IS
	  v_cRetorno FarmaCursor;
	BEGIN
	  OPEN v_cRetorno FOR
	  SELECT LOC.SEC_USU_LOCAL || 'Ã' ||
		USU.APE_PAT       || ' '  ||
		USU.APE_MAT       || ', ' ||
		USU.NOM_USU || 'Ã' ||
		TO_CHAR(LOC.PPTO_VOLUMEN,'999,990.00') || 'Ã' ||
		TO_CHAR(LOC.PPTO_LLEE,'999,990.00')
		FROM PTOVENTA.PROG_RENTABLES_PPTO_LOC LOC 
		JOIN PTOVENTA.PBL_USU_LOCAL USU ON (LOC.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
		AND LOC.COD_LOCAL = USU.COD_LOCAL
		AND LOC.SEC_USU_LOCAL = USU.SEC_USU_LOCAL)
		WHERE LOC.COD_GRUPO_CIA = cCodGrupoCia_in
		AND LOC.COD_PROG = cCodProg_in
		AND LOC.COD_LOCAL = cCodLocal_in;
	  RETURN v_cRetorno;
	END;

  FUNCTION F_LISTA_USUARIOS_LOCAL(cCodGrupoCia_in    IN CHAR,
  		   				            cCodLocal_in	   IN CHAR,
									cCodProg_in IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
		SELECT SEC_USU_LOCAL || 'Ã'  ||
			   APE_PAT       || ' '  ||
			   APE_MAT       || ', ' ||
			   NOM_USU
		FROM   PBL_USU_LOCAL USU
		WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
		       COD_LOCAL     = cCodLocal_in    AND
			   EST_USU       = 'A'   AND
			   COD_GRUPO_CIA || COD_LOCAL || SEC_USU_LOCAL IN (SELECT COD_GRUPO_CIA  ||
														  		 	   COD_LOCAL     ||
																 	   SEC_USU_LOCAL
														  		FROM   PBL_ROL_USU
														  		WHERE  COD_ROL = '010' --010	Tecnico Vendedor
               AND    SEC_USU_LOCAL BETWEEN '001' AND '899'--ERIOS 25/10/20006: RANGO VALIDO
			   AND NOT EXISTS (SELECT 1 FROM PTOVENTA.PROG_RENTABLES_PPTO_LOC LOC
								   WHERE LOC.COD_GRUPO_CIA = cCodGrupoCia_in
								   AND LOC.COD_PROG = cCodProg_in
								   AND LOC.COD_LOCAL = cCodLocal_in
								   AND LOC.SEC_USU_LOCAL = USU.SEC_USU_LOCAL)
	  ); 
    RETURN curVta;
  END;

  FUNCTION F_DATOS_USUARIOS_LOCAL(cCodGrupoCia_in    IN CHAR,
  		   				            cCodLocal_in	   IN CHAR,
									cSecUsuLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
		SELECT SEC_USU_LOCAL || 'Ã'  ||
			   APE_PAT       || ' '  ||
			   APE_MAT       || ', ' ||
			   NOM_USU
		FROM   PBL_USU_LOCAL
		WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
		       COD_LOCAL     = cCodLocal_in    AND
			   EST_USU       = 'A'   AND
			    SEC_USU_LOCAL = cSecUsuLocal_in AND
			   COD_GRUPO_CIA || COD_LOCAL || SEC_USU_LOCAL IN (SELECT COD_GRUPO_CIA  ||
														  		 	   COD_LOCAL     ||
																 	   SEC_USU_LOCAL
														  		FROM   PBL_ROL_USU
														  		WHERE  COD_ROL = '010' --010	Tecnico Vendedor
               AND    SEC_USU_LOCAL BETWEEN '001' AND '899'--ERIOS 25/10/20006: RANGO VALIDO
	  ); 
    RETURN curVta;
  END;

	PROCEDURE P_LIMPIA_PRESUPUESTO_VENDEDOR(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProg_in	  	IN CHAR,
						cCodLocal_in IN CHAR)
    IS
	BEGIN
		DELETE PTOVENTA.PROG_RENTABLES_PPTO_LOC
		WHERE COD_GRUPO_CIA = cCodGrupoCia_in
		AND COD_PROG = cCodProg_in
		AND COD_LOCAL = cCodLocal_in;
	END;

	PROCEDURE P_SET_PRESUPUESTO_VENDEDOR(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProg_in	  	IN CHAR,
						cCodLocal_in IN CHAR,
						cSecUsuLocal_in    IN CHAR,
						nVolumen_in IN NUMBER,
						nLLEE_in IN NUMBER,
						vUsuCrea_in IN VARCHAR)
    IS
	BEGIN
		INSERT INTO PTOVENTA.PROG_RENTABLES_PPTO_LOC(COD_GRUPO_CIA,COD_PROG,COD_LOCAL,SEC_USU_LOCAL,
													PPTO_VOLUMEN,PPTO_LLEE,USU_CREA)
		VALUES(cCodGrupoCia_in,cCodProg_in,cCodLocal_in,cSecUsuLocal_in,
				nVolumen_in,nLLEE_in,vUsuCrea_in);
	END;

	PROCEDURE P_VALIDA_PRESUPUESTO_VENDEDOR(cCodGrupoCia_in	  IN CHAR,
  		   				cCodProg_in	  	IN CHAR,
						cCodLocal_in IN CHAR,
						cIsOperador_in IN CHAR)
    IS
	  v_nPptoVolumen PROG_RENTABLES_PPTO.PPTO_VOLUMEN%TYPE;
	  v_nPptoLLEE PROG_RENTABLES_PPTO.PPTO_LLEE%TYPE;
	  
	  v_nPptoVolumenLoc PROG_RENTABLES_PPTO.PPTO_VOLUMEN%TYPE;
	  v_nPptoLLEELoc PROG_RENTABLES_PPTO.PPTO_LLEE%TYPE;
	  
	  v_nDias INTEGER;
	  v_nVeces INTEGER;
	  v_nDifDias INTEGER;
	  v_nDifVeces INTEGER;
	  v_nCargoVendedor INTEGER;
	  
	  v_vFecIni VARCHAR2(10);
	  v_vFecFin VARCHAR2(10);
	  v_nDiasProy INTEGER;
	  v_nValorRango INTEGER;
	  v_nMontoMayor INTEGER;
	BEGIN
	    --1. Valida montos
		SELECT PPTO_VOLUMEN,PPTO_LLEE
		  INTO v_nPptoVolumen, v_nPptoLLEE
		FROM PTOVENTA.PROG_RENTABLES_PPTO
		WHERE COD_GRUPO_CIA = cCodGrupoCia_in
		AND COD_PROG = cCodProg_in
		AND COD_LOCAL = cCodLocal_in;
		
		SELECT SUM(PPTO_VOLUMEN),SUM(PPTO_LLEE)
		  INTO v_nPptoVolumenLoc, v_nPptoLLEELoc
		FROM PTOVENTA.PROG_RENTABLES_PPTO_LOC
		WHERE COD_GRUPO_CIA = cCodGrupoCia_in
		AND COD_PROG = cCodProg_in
		AND COD_LOCAL = cCodLocal_in;
		
		IF v_nPptoVolumen < v_nPptoVolumenLoc THEN
		  RAISE_APPLICATION_ERROR(-20014,'El presupuesto de VOLUMEN asignado no debe ser mayor a la META VOLUMEN.');
		ELSIF v_nPptoVolumen > v_nPptoVolumenLoc THEN
		  RAISE_APPLICATION_ERROR(-20014,'El presupuesto de VOLUMEN asignado no debe ser menor a la META VOLUMEN.');
		ELSIF v_nPptoLLEE < v_nPptoLLEELoc THEN
		  RAISE_APPLICATION_ERROR(-20014,'El presupuesto de LLEE asignado no debe ser mayor a la META LLEE.');
		ELSIF v_nPptoLLEE > v_nPptoLLEELoc THEN
		  RAISE_APPLICATION_ERROR(-20014,'El presupuesto de LLEE asignado no debe ser menor a la META LLEE.');
		END IF;
		
		--2. Dias para registrar
		/*SELECT TO_NUMBER(TRIM(SUBSTR(DESC_CORTA,6))),
			   TO_NUMBER(TRIM(SUBSTR(DESC_LARGA,7)))
		  INTO v_nDias,v_nVeces
		FROM PTOVENTA.PBL_TAB_GRAL
		WHERE ID_TAB_GRAL = 643;
			
		IF cIsOperador_in = 'N' THEN			
			SELECT TRUNC(SYSDATE)-TRUNC(FEC_CREA)
			  INTO v_nDifDias
			FROM PTOVENTA.PROG_RENTABLES_PPTO
			WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND COD_PROG = cCodProg_in;
			
			IF v_nDifDias > v_nDias THEN
			  RAISE_APPLICATION_ERROR(-20014,'Esta opción ya no se encuentra habilitada, debido a que superó los días disponibles para registrar su presupuesto.'
			  ||CHR(10)|| 'Por favor, comuníquese con su Supervisor de Ventas para cualquier consulta adicional.');
			END IF;
		END IF;*/
		
		--3. Veces para modificar 
		/*IF cIsOperador_in = 'N' THEN	
			BEGIN
			SELECT TO_NUMBER(TRIM(SUBSTR(DESC_CORTA,5)))
			  INTO v_nDifVeces
			FROM PTOVENTA.PBL_TAB_GRAL
			WHERE ID_TAB_GRAL = 644
			AND DESC_CORTA LIKE cCodProg_in||':%';
			 EXCEPTION
			 WHEN NO_DATA_FOUND THEN
			   v_nDifVeces := 0;
			END;
			--3.1 Valida
			IF v_nDifVeces >= v_nVeces THEN
			  RAISE_APPLICATION_ERROR(-20014,'Ha superado las veces disponibles para registrar: '||v_nVeces);
			END IF;
			
			--3.2 Registra 
			UPDATE PTOVENTA.PBL_TAB_GRAL
			SET DESC_CORTA = cCodProg_in||':'||(v_nDifVeces+1)
			WHERE ID_TAB_GRAL = 644;
		END IF;*/
		
		--4. Montos ceros
		FOR fila IN (SELECT * FROM PTOVENTA.PROG_RENTABLES_PPTO_LOC LOC
						WHERE COD_GRUPO_CIA = cCodGrupoCia_in
						AND COD_PROG = cCodProg_in
						AND COD_LOCAL = cCodLocal_in)
		LOOP
		  
		  IF fila.PPTO_VOLUMEN = 0 THEN
		    RAISE_APPLICATION_ERROR(-20014,'El presupuesto de VOLUMEN no puede ser CERO.');
		  END IF;
		  
		  IF fila.PPTO_LLEE = 0 THEN
		    SELECT COUNT(1)
			  INTO v_nCargoVendedor
			FROM PTOVENTA.PBL_USU_LOCAL USU 
				JOIN PTOVENTA.CE_MAE_TRAB TRAB ON (1=1 --TRAB.COD_CIA = USU.COD_CIA
													AND TRAB.COD_TRAB = USU.COD_TRAB)
				JOIN PTOVENTA.CE_CARGO CAR ON (CAR.COD_CARGO = TRAB.COD_CARGO
											AND CAR.DESC_CARGO LIKE '%VENDEDOR%')
			WHERE USU.COD_GRUPO_CIA = fila.COD_GRUPO_CIA
			AND USU.COD_LOCAL = fila.COD_LOCAL
			AND USU.SEC_USU_LOCAL = fila.SEC_USU_LOCAL;
			
			IF v_nCargoVendedor > 0 THEN
		    RAISE_APPLICATION_ERROR(-20014,'El presupuesto de LLEE no puede ser CERO, para un VENDEDOR.');
			END IF;
		  END IF;
		END LOOP;
		
		--5. Rango de ventas por vendedor
		begin
		    --TODO Colocar como variables
			v_vFecIni := TO_CHAR( TRUNC(SYSDATE)-INTERVAL '4' MONTH ,'DD/MM/YYYY');
			v_vFecFin := TO_CHAR(TRUNC(SYSDATE),'DD/MM/YYYY');
			v_nDiasProy := 26;
			v_nValorRango := 1.3; --30%+
			
			PTOVENTA_REPORTE.ACT_RES_VENTAS_VENDEDOR_TIPO(cCodGrupoCia_in,cCodLocal_in,
											    v_vFecIni,
												v_vFecFin,
												'00' --C_C_TIPO_VENTA_TOTAL
											   );
			
			WITH
			PROM AS (
			SELECT SEC_USU_LOCAL,
			TRUNC(SUM(MON_TOT_S_IGV)/COUNT(FEC_DIA_VENTA)*v_nDiasProy*v_nValorRango) PROY_EST
			FROM TMP_VTA_VEND_LOCAL R
			WHERE  R.COD_GRUPO_CIA = cCodGrupoCia_in
				 AND  R.COD_LOCAL     = cCodLocal_in
				 AND  R.FEC_DIA_VENTA BETWEEN  TO_DATE(v_vFecIni || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
									 AND       TO_DATE(v_vFecFin || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
			GROUP BY SEC_USU_LOCAL
			)
			SELECT COUNT(1)
			  INTO v_nMontoMayor
			FROM PTOVENTA.PROG_RENTABLES_PPTO_LOC LOC JOIN PROM ON (LOC.SEC_USU_LOCAL = PROM.SEC_USU_LOCAL)
			WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND COD_PROG = cCodProg_in
			AND COD_LOCAL = cCodLocal_in
			AND PPTO_VOLUMEN > PROY_EST;
			
			IF v_nMontoMayor > 0 THEN
		      RAISE_APPLICATION_ERROR(-20014,'El presupuesto de VOLUMEN no debe ser mayor al promedio de ventas.');
			END IF;
		end;
		
	END;
	
	FUNCTION F_VALIDA_DIAS_REGISTRAR(cCodGrupoCia_in IN CHAR,
										cCodProg_in IN CHAR,
										cCodLocal_in IN CHAR)
	RETURN VARCHAR2
	IS
	  v_vRetorno VARCHAR2(500) := 'S';
	  
	  v_nDias INTEGER;
	  v_nVeces INTEGER;
	  v_nDifDias INTEGER;
	BEGIN
	    SELECT TO_NUMBER(TRIM(SUBSTR(DESC_CORTA,6))),
			   TO_NUMBER(TRIM(SUBSTR(DESC_LARGA,7)))
		  INTO v_nDias,v_nVeces
		FROM PTOVENTA.PBL_TAB_GRAL
		WHERE ID_TAB_GRAL = 643;
			
			SELECT TRUNC(SYSDATE)-TRUNC(FEC_CREA)
			  INTO v_nDifDias
			FROM PTOVENTA.PROG_RENTABLES_PPTO
			WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND COD_PROG = cCodProg_in
			AND COD_LOCAL = cCodLocal_in;
			
			IF v_nDifDias > v_nDias THEN
			  v_vRetorno := 'Esta opción ya no se encuentra habilitada, debido a que superó los días disponibles para registrar su presupuesto.'
			  ||CHR(10)|| 'Por favor, comuníquese con su Supervisor de Ventas para cualquier consulta adicional.';
			END IF;
		
		RETURN v_vRetorno;
	END;

	PROCEDURE ENV_ALERTA_REGISTRO_PPTO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR)
	IS
		v_vDescLocal VARCHAR2(120);
		v_vReceiverAddress VARCHAR2(120);
		v_vCCReceiverAddress VARCHAR2(120);
		v_vmesg VARCHAR2( 4000 );
		
		v_nDias INTEGER;
	  v_nVeces INTEGER;
	  v_nDifDias INTEGER;
	  v_vEstado VARCHAR2(10);
	  
	  cCodProg_in PTOVENTA.PROG_RENTABLES.COD_PROG%TYPE;
	BEGIN
	  SELECT CAB.COD_PROG
	    INTO cCodProg_in
		FROM PTOVENTA.PROG_RENTABLES_PPTO PPTO JOIN PTOVENTA.PROG_RENTABLES CAB
		ON (PPTO.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
		AND PPTO.COD_PROG = CAB.COD_PROG)
		WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
		AND TRUNC(SYSDATE) BETWEEN CAB.FEC_INI AND CAB.FEC_FIN
		AND PPTO.COD_LOCAL = cCodLocal_in;
		
	    SELECT TO_NUMBER(TRIM(SUBSTR(DESC_CORTA,6))),
			   TO_NUMBER(TRIM(SUBSTR(DESC_LARGA,7)))
		  INTO v_nDias,v_nVeces
		FROM PTOVENTA.PBL_TAB_GRAL
		WHERE ID_TAB_GRAL = 643;
			
			SELECT TRUNC(SYSDATE)-TRUNC(FEC_CREA)
			  INTO v_nDifDias
			FROM PTOVENTA.PROG_RENTABLES_PPTO
			WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND COD_PROG = cCodProg_in
			AND COD_LOCAL = cCodLocal_in;
			
		SELECT CASE WHEN COUNT(1) > 0 THEN 'TRABAJADO'
					ELSE 'PENDIENTE' END
			  INTO v_vEstado
		FROM PTOVENTA.PROG_RENTABLES_PPTO_LOC LOC
			  WHERE LOC.COD_GRUPO_CIA = cCodGrupoCia_in
			  AND LOC.COD_PROG = cCodProg_in
			  AND LOC.COD_LOCAL = cCodLocal_in;
		
		--ENVIA MAIL
		IF v_nDifDias > v_nDias AND v_vEstado = 'PENDIENTE' THEN
		  --DESCRIPCION DE LOCAL
		  SELECT COD_LOCAL ||' - '|| DESC_LOCAL,
				MAIL_LOCAL
			INTO v_vDescLocal, v_vReceiverAddress
		  FROM PTOVENTA.PBL_LOCAL
		  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
				AND COD_LOCAL = cCodLocal_in;
			--JEFE ZONA
			SELECT Z.EMAIL_JEFE_ZONA || NVL2(Z.NOM_SUBGERENTE,','||Z.NOM_SUBGERENTE,'')
				INTO v_vCCReceiverAddress
			FROM PTOVENTA.VTA_LOCAL_X_ZONA LZ JOIN PTOVENTA.VTA_ZONA_VTA Z ON (LZ.COD_GRUPO_CIA = Z.COD_GRUPO_CIA
			AND LZ.COD_ZONA_VTA = Z.COD_ZONA_VTA)
			WHERE LZ.COD_GRUPO_CIA = cCodGrupoCia_in
			AND LZ.COD_LOCAL = cCodLocal_in
			;
			
			v_vmesg := '<H2>Aún no ha registrado el Presupuesto por Vendedor.</H2>';
				   
		  FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
								   v_vReceiverAddress,
								   'Alerta Pendiente Registro de PPTO '||v_vDescLocal,
								   'PENDIENTE',
								   v_vmesg,
								   v_vCCReceiverAddress,
								   FARMA_EMAIL.GET_EMAIL_SERVER,
								   true);
		END IF;
	EXCEPTION
		WHEN NO_DATA_FOUND THEN	
			--No existe presupuesto asignado al local
			NULL;
		WHEN OTHERS THEN
			DBMS_OUTPUT.PUT_LINE('ERROR: '||SQLERRM);
	END;
	
END;
/
