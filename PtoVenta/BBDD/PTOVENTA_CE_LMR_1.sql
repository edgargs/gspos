--------------------------------------------------------
--  DDL for Package Body PTOVENTA_CE_LMR
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_CE_LMR" is

/************************CIERRE DE TURNO*****************************/

  /****************************************************************************/
  FUNCTION CE_BUSCA_CAJAS_USU_DIAVENTA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in	   IN CHAR,
                                       cDiaVenta_in    IN CHAR,
                                       cUsuCaja_in     IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT MOV_CAJA.NUM_CAJA_PAGO
         FROM   CE_MOV_CAJA MOV_CAJA
         WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
         AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cDiaVenta_in,'dd/MM/yyyy')
         AND    MOV_CAJA.SEC_USU_LOCAL = cUsuCaja_in
         AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_TURNOS_CAJA_USU_DIA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in	   IN CHAR,
                                        cDiaVenta_in    IN CHAR,
                                        cUsuCaja_in     IN CHAR,
                                        cNumeroCaja_in  IN NUMBER)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT MOV_CAJA.NUM_TURNO_CAJA || '�' ||
                MOV_CAJA.NUM_TURNO_CAJA
         FROM   CE_MOV_CAJA MOV_CAJA
         WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
         AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cDiaVenta_in,'dd/MM/yyyy')
         AND    MOV_CAJA.SEC_USU_LOCAL = cUsuCaja_in
         AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE
         AND    MOV_CAJA.NUM_CAJA_PAGO = cNumeroCaja_in;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_VALIDA_DATO_CAJ(cCodGrupoCia_in	IN CHAR,
  		   						          cCodLocal_in	  IN CHAR,
  		   						          cDiaVenta_in    IN CHAR,
 		  						            cUsuCaja_in     IN CHAR,
                              cNumCaja_in     IN NUMBER,
                              cTurnoCaja_in   IN NUMBER)
    RETURN CHAR
  IS
  	v_cSecMovCaja  CHAR(10);
    v_cIndVBCajero CHAR(1);
    v_cIndVBQF     CHAR(1);
  	v_cIndicador   CHAR(1);
    v_cRpta        CHAR(11);
  BEGIN
       SELECT MOV_CAJA.SEC_MOV_CAJA,
              MOV_CAJA.IND_VB_CAJERO,
              MOV_CAJA.IND_VB_QF
       INTO   v_cSecMovCaja,
              v_cIndVBCajero,
              v_cIndVBQF
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cDiaVenta_in,'dd/MM/yyyy')
       AND    MOV_CAJA.SEC_USU_LOCAL = cUsuCaja_in
       AND    MOV_CAJA.NUM_CAJA_PAGO = cNumCaja_in
       AND    MOV_CAJA.NUM_TURNO_CAJA = cTurnoCaja_in
       AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE;

       IF v_cIndVBCajero = INDICADOR_NO THEN
          v_cIndicador := '1';--existe mov de cierre sin VB
       ELSIF v_cIndVBCajero = INDICADOR_SI AND v_cIndVBQF = INDICADOR_NO THEN
          v_cIndicador := '2';--existe mov de cierre con VB de Cajero pero sin VB de QF
       ELSIF v_cIndVBCajero = INDICADOR_SI AND v_cIndVBQF = INDICADOR_SI THEN
          v_cIndicador := '3';--existe mov de cierre con VB de Cajero y con VB de QF
       END IF;

       v_cRpta := v_cIndicador || v_cSecMovCaja;
     RETURN v_cRpta;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cIndicador := '4';--NO SE ENCONTRO MOVIMIENTO PARA LOS DATOS INGRESADOS
          v_cRpta := v_cIndicador;
		 RETURN v_cRpta;
 END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_FEC_APER_CER(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in	     IN CHAR,
                                   cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT MOV_CAJA.TIP_MOV_CAJA || '�' ||
               TO_CHAR(MOV_CAJA.FEC_CREA_MOV_CAJA,'dd/MM/yyyy HH12:MI:SS AM')
        FROM   CE_MOV_CAJA MOV_CAJA
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in
        UNION
        SELECT MOV_CAJA.TIP_MOV_CAJA || '�' ||
               TO_CHAR(MOV_CAJA.FEC_CREA_MOV_CAJA,'dd/MM/yyyy HH12:MI:SS AM')
        FROM   CE_MOV_CAJA MOV_CAJA
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA IN (SELECT MOV_CAJA.SEC_MOV_CAJA_ORIGEN
                                         FROM   CE_MOV_CAJA MOV_CAJA
                                         WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
                                         AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in);

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_NOMBRE_USUARIO(cCodGrupoCia_in IN CHAR,
  		   						                 cCodLocal_in	   IN CHAR,
 		  						                   cUsuCaja_in     IN CHAR)
    RETURN CHAR
  IS
    v_cNombreUsu CHAR(100);
  BEGIN
       SELECT USU.NOM_USU || ' ' ||
              USU.APE_PAT || ' ' ||
              USU.APE_MAT
       INTO   v_cNombreUsu
       FROM   PBL_USU_LOCAL USU
       WHERE  USU.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    USU.COD_LOCAL = cCodLocal_in
       AND    USU.SEC_USU_LOCAL = cUsuCaja_in;

     RETURN v_cNombreUsu;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cNombreUsu := '';--NO SE ENCONTRO EL USUARIO
		 RETURN v_cNombreUsu;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_FORMA_PAGO_CIERRE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in	     IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT FP.DESC_CORTA_FORMA_PAGO || '�' ||
               DECODE(FPE.TIP_MONEDA,'02','DOLARES','SOLES') || '�' ||
               TO_CHAR(SUM(FPE.MON_ENTREGA),'999,990.00') || '�' ||
               TO_CHAR(SUM(FPE.MON_ENTREGA_TOTAL),'999,990.00') || '�' ||
               FPE.COD_FORMA_PAGO
        FROM   CE_FORMA_PAGO_ENTREGA FPE,
               VTA_FORMA_PAGO FP
        WHERE  FPE.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    FPE.COD_LOCAL = cCodLocal_in
        AND    FPE.SEC_MOV_CAJA = cMovCajaCierre_in
        AND    FPE.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
        AND    FPE.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
        AND    FPE.EST_FORMA_PAGO_ENT = 'A'
        GROUP BY FP.DESC_CORTA_FORMA_PAGO,FPE.TIP_MONEDA,FPE.COD_FORMA_PAGO;

    RETURN curCE;
  END;
  /****************************************************************************/
  FUNCTION CE_LISTA_CUADRATURA_CIERRE(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in	     IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT CUADRATURA_CAJA.COD_CUADRATURA || '�' ||
               NVL(CUADRATURA.DESC_CUADRATURA,' ') || '�' ||
               TO_CHAR(SUM(CUADRATURA_CAJA.MON_TOTAL) * CUADRATURA.VAL_SIGNO,'999,999,990.00')
        FROM   CE_CUADRATURA_CAJA CUADRATURA_CAJA,
               CE_CUADRATURA CUADRATURA
        WHERE  CUADRATURA_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CUADRATURA_CAJA.COD_LOCAL = cCodLocal_in
        AND    CUADRATURA_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in
        AND    CUADRATURA_CAJA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
        AND    CUADRATURA_CAJA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
        GROUP BY CUADRATURA_CAJA.COD_CUADRATURA,
                 CUADRATURA.DESC_CUADRATURA,
                 CUADRATURA.VAL_SIGNO;

    RETURN curCE;
  END;

  /****************************************************************************/
  PROCEDURE CE_REGISTRA_HIST_VB_CAJ(cCodGrupoCia_in IN CHAR,
						   	                     cCodLocal_in    IN CHAR,
							                       cSecMovCaja_in  IN CHAR,
                                     cIndVB_in       IN CHAR,
                                     cObsCierreTurno IN CHAR,
							                       cUsuCreaVB_in   IN CHAR)
  IS
    v_nSecHistVB CE_HIST_VB_MOV_CAJA.SEC_HIST_VB_MOV_CAJA%TYPE;

    CURSOR formasPagoEntrega IS
		   		SELECT *
				  FROM   CE_FORMA_PAGO_ENTREGA F
				  WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
				  AND	   F.COD_LOCAL = cCodLocal_in
				  AND	   F.SEC_MOV_CAJA = cSecMovCaja_in
          AND    F.EST_FORMA_PAGO_ENT = 'A'
          ORDER BY F.SEC_FORMA_PAGO_ENTREGA;

    CURSOR cuadraturasEntrega IS
		   		SELECT *
				  FROM   CE_CUADRATURA_CAJA C
				  WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
				  AND	   C.COD_LOCAL = cCodLocal_in
				  AND	   C.SEC_MOV_CAJA = cSecMovCaja_in
          ORDER BY C.SEC_CUADRATURA_CAJA;
  BEGIN
      SELECT COUNT(*) + 1
      INTO   v_nSecHistVB
      FROM   CE_HIST_VB_MOV_CAJA A
      WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    A.COD_LOCAL = cCodLocal_in
      AND    A.SEC_MOV_CAJA = cSecMovCaja_in;

      INSERT INTO CE_HIST_VB_MOV_CAJA
                   (COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA,SEC_HIST_VB_MOV_CAJA,
                    FEC_VB_MOV_CAJA,IND_VB_MOV_CAJA,TIP_VB_MOV_CAJA,
                    USU_CREA_VB_MOV_CAJA,FEC_CREA_VB_MOV_CAJA,DESC_OBS_CIERRE_TURNO_HIST)
             VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,v_nSecHistVB,
                    SYSDATE, cIndVB_in, TIP_VB_CAJERO,
                    cUsuCreaVB_in, SYSDATE,NULL);

      --dbms_output.put_line('v_cIndGraboComp: ' || v_cIndGraboComp );

      IF cIndVB_in = INDICADOR_NO THEN
         UPDATE CE_HIST_VB_MOV_CAJA
            SET DESC_OBS_CIERRE_TURNO_HIST = cObsCierreTurno
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND   SEC_MOV_CAJA = cSecMovCaja_in
          AND   SEC_HIST_VB_MOV_CAJA = v_nSecHistVB;

         FOR formasPagoEntrega_rec IN formasPagoEntrega
         LOOP
             INSERT INTO CE_HIST_FORMA_PAGO_ENTREGA
                           (COD_GRUPO_CIA,COD_LOCAL,
                            SEC_MOV_CAJA,SEC_HIST_VB_MOV_CAJA,
                            SEC_FORMA_PAGO_ENTREGA,COD_FORMA_PAGO_HIST,
                            TIP_MONEDA_HIST,CANT_VOUCHER_HIST,
                            MON_ENTREGA_HIST,MON_ENTREGA_TOTAL_HIST,
                            USU_CREA_HIST_FORMA_PAGO_ENT,FEC_CREA_HIST_FORMA_PAGO_ENT)
                    VALUES (formasPagoEntrega_rec.COD_GRUPO_CIA, formasPagoEntrega_rec.COD_LOCAL,
                            formasPagoEntrega_rec.SEC_MOV_CAJA, v_nSecHistVB,
                            formasPagoEntrega_rec.SEC_FORMA_PAGO_ENTREGA, formasPagoEntrega_rec.COD_FORMA_PAGO,
                            formasPagoEntrega_rec.TIP_MONEDA, formasPagoEntrega_rec.CANT_VOUCHER,
                            formasPagoEntrega_rec.MON_ENTREGA, formasPagoEntrega_rec.MON_ENTREGA_TOTAL,
                            cUsuCreaVB_in, SYSDATE);
         END LOOP;
         FOR cuadraturasEntrega_rec IN cuadraturasEntrega
         LOOP
             INSERT INTO CE_HIST_CUADRATURA_CAJA
                           (COD_GRUPO_CIA,COD_LOCAL,
                            SEC_MOV_CAJA,SEC_HIST_VB_MOV_CAJA,
                            SEC_CUADRATURA_CAJA,COD_CUADRATURA,
                            NUM_SERIE_LOCAL_HIST,TIP_COMP_HIST,
                            NUM_COMP_PAGO_HIST,MON_MONEDA_ORIGEN_HIST,
                            MON_TOTAL_HIST,MON_VUELTO_HIST,
                            TIP_DINERO_HIST,TIP_MONEDA_HIST,
                            SERIE_BILLETE_HIST,COD_FORMA_PAGO_HIST,
                            USU_CREA_CUADRA_CAJ_HIST,FEC_CREA_CUADRA_CAJ_HIST)
                    VALUES (cuadraturasEntrega_rec.COD_GRUPO_CIA, cuadraturasEntrega_rec.COD_LOCAL,
                            cuadraturasEntrega_rec.SEC_MOV_CAJA, v_nSecHistVB,
                            cuadraturasEntrega_rec.SEC_CUADRATURA_CAJA, cuadraturasEntrega_rec.COD_CUADRATURA,
                            cuadraturasEntrega_rec.NUM_SERIE_LOCAL, cuadraturasEntrega_rec.TIP_COMP,
                            cuadraturasEntrega_rec.NUM_COMP_PAGO, cuadraturasEntrega_rec.MON_MONEDA_ORIGEN,
                            cuadraturasEntrega_rec.MON_TOTAL, cuadraturasEntrega_rec.MON_VUELTO,
                            cuadraturasEntrega_rec.TIP_DINERO, cuadraturasEntrega_rec.TIP_MONEDA,
                            cuadraturasEntrega_rec.SERIE_BILLETE, cuadraturasEntrega_rec.COD_FORMA_PAGO,
                            cUsuCreaVB_in, SYSDATE);
         END LOOP;
      END IF;
  END;

  /****************************************************************************/
  PROCEDURE CE_REGISTRA_HIST_VB_QF(cCodGrupoCia_in IN CHAR,
						   	                    cCodLocal_in    IN CHAR,
							                      cSecMovCaja_in  IN CHAR,
                                    cIndVB_in       IN CHAR,
							                      cUsuCreaVB_in   IN CHAR)
  IS
    v_nSecHistVB CE_HIST_VB_MOV_CAJA.SEC_HIST_VB_MOV_CAJA%TYPE;

  BEGIN
      SELECT COUNT(*) + 1
      INTO   v_nSecHistVB
      FROM   CE_HIST_VB_MOV_CAJA A
      WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    A.COD_LOCAL = cCodLocal_in
      AND    A.SEC_MOV_CAJA = cSecMovCaja_in;

      INSERT INTO CE_HIST_VB_MOV_CAJA
                   (COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA,SEC_HIST_VB_MOV_CAJA,
                    FEC_VB_MOV_CAJA,IND_VB_MOV_CAJA,TIP_VB_MOV_CAJA,
                    USU_CREA_VB_MOV_CAJA,FEC_CREA_VB_MOV_CAJA,DESC_OBS_CIERRE_TURNO_HIST)
             VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,v_nSecHistVB,
                    SYSDATE, cIndVB_in, TIP_VB_QF,
                    cUsuCreaVB_in, SYSDATE,NULL);

  END;

  /* ************************************************************************* */
  FUNCTION CE_OBTIENE_IND_VB_FOR_UPDATE(cCodGrupoCia_in IN CHAR,
						   	                         cCodLocal_in    IN CHAR,
							                           cSecMovCaja_in  IN CHAR,
                                         cTipVB_in       IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    IF cTipVB_in = TIP_VB_CAJERO THEN
       OPEN curCE FOR
		        SELECT MOV_CAJA.IND_VB_CAJERO
		        FROM   CE_MOV_CAJA MOV_CAJA
		        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
		        AND	  MOV_CAJA.COD_LOCAL = cCodLocal_in
		        AND	  MOV_CAJA.SEC_MOV_CAJA = cSecMovCaja_in FOR UPDATE;
    ELSIF cTipVB_in = TIP_VB_QF THEN
       OPEN curCE FOR
		        SELECT MOV_CAJA.IND_VB_QF
		        FROM   CE_MOV_CAJA MOV_CAJA
		        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
		        AND	  MOV_CAJA.COD_LOCAL = cCodLocal_in
		        AND	  MOV_CAJA.SEC_MOV_CAJA = cSecMovCaja_in FOR UPDATE;
    END IF;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACTUALIZA_VB(cCodGrupoCia_in  IN CHAR,
   	                        cCodLocal_in     IN CHAR,
	                          cSecMovCaja_in   IN CHAR,
                            cIndVB_in        IN CHAR,
                            cTipVB_in        IN CHAR,
                            cUsuModMovCaj_in IN CHAR,
                            cSecUsuQf in char DEFAULT '000')
  IS
    cIndicador VARCHAR2(1);
    indProsegur CHAR(1); --ASOSA, 02.06.2010
  BEGIN
       IF cTipVB_in = TIP_VB_CAJERO THEN
          UPDATE CE_MOV_CAJA MOV_CAJA SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in, MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE ,
                 MOV_CAJA.Ind_Vb_Cajero = cIndVB_in,
                 MOV_CAJA.FEC_CIERRE_TURNO_CAJA = DECODE(cIndVB_in,INDICADOR_SI,SYSDATE,NULL)
          WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
          AND    MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;
       ELSIF cTipVB_in = TIP_VB_QF THEN
          UPDATE CE_MOV_CAJA MOV_CAJA SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in, MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE ,
                 MOV_CAJA.Ind_Vb_Qf = cIndVB_in
          WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
          AND    MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;


       --SELECT TRIM(NVL(A.DESC_CORTA,' ')) INTO cIndicador
       SELECT TRIM(NVL(A.Llave_Tab_Gral,'N')) INTO cIndicador --ASOSA, 02.06.2010
       FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL=317;
       -- AND a.cod_grupo_cia=cCodGrupoCia_in;

       --ASOSA, 02.06.2010
       SELECT nvl(x.ind_prosegur,'N') INTO indProsegur
       FROM pbl_local x
       WHERE x.cod_grupo_cia=cCodGrupoCia_in
       AND x.cod_local=cCodLocal_in;

            IF(cIndicador='S' OR indProsegur='S')THEN

              --Se aprueban automaticamente los sobres del turno.
              UPDATE CE_SOBRE A
              SET A.ESTADO='A',
              A.FEC_MOD_SOBRE=SYSDATE,
              A.USU_MOD_SOBRE=cUsuModMovCaj_in,
              a.sec_usu_qf = cSecUsuQf
              WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
              AND A.COD_LOCAL=cCodLocal_in
              AND A.SEC_MOV_CAJA=cSecMovCaja_in --todos los sobres creados en cierre de turno
              AND A.ESTADO IN ('P'); --ASOSA, 14.06.2010

              UPDATE CE_SOBRE_TMP B
              SET B.ESTADO='A',
              B.FEC_MOD_SOBRE=SYSDATE,
              B.USU_MOD_SOBRE=cUsuModMovCaj_in,
              b.sec_usu_qf = cSecUsuQf
              WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
              AND B.COD_LOCAL=cCodLocal_in
              AND B.ESTADO IN ('P') --ASOSA, 14.06.2010
              AND B.SEC_MOV_CAJA= (SELECT C.SEC_MOV_CAJA_ORIGEN
                           FROM CE_MOV_CAJA C
                           WHERE  C.COD_GRUPO_CIA=B.COD_GRUPO_CIA
                           AND C.COD_LOCAL=B.COD_LOCAL
                           AND C.SEC_MOV_CAJA=cSecMovCaja_in); --todos los sobres creados en el ingreso parcial de cobro
            END IF;

       END IF;
  END;

  /* ************************************************************************ */
  FUNCTION CE_OBTIENE_MONTO_TOTAL_SISTEMA(cCodGrupoCia_in IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
 		  						                        cSecMovCaja_in  IN CHAR)
    RETURN CHAR
  IS
    v_cTotalSistema CHAR(20);
  BEGIN
       SELECT TO_CHAR(NVL(E.MON_TOT,0),'999,990.00')
       INTO   v_cTotalSistema
       FROM   CE_MOV_CAJA E
       WHERE  E.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    E.COD_LOCAL = cCodLocal_in
       AND    E.SEC_MOV_CAJA = cSecMovCaja_in;

     RETURN v_cTotalSistema;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cTotalSistema := '0.00';--NO SE ENCONTRO MONTO TOTAL DE SISTEMA
		 RETURN v_cTotalSistema;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_EVALUA_DEFICIT_ASUMIDO_CAJ(cCodGrupoCia_in  IN CHAR,
  		   						                      cCodLocal_in	   IN CHAR,
 		  						                        cSecMovCaja_in   IN CHAR,
                                          nMontoDeficit_in IN NUMBER,
                                          vIdUsu_in        IN CHAR)
  IS
    v_nSecCuadraturaCaj NUMBER;
  BEGIN
       IF nMontoDeficit_in <> 0 THEN --sirve para eliminar si o si el deficit asumido
          DELETE FROM CE_CUADRATURA_CAJA E
                WHERE E.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   E.COD_LOCAL = cCodLocal_in
                AND   E.SEC_MOV_CAJA = cSecMovCaja_in
                AND   E.COD_CUADRATURA = COD_CUADRATURA_DEFICIT_CAJERO;
       END IF;

       IF nMontoDeficit_in > 0 THEN

          SELECT NVL(MAX(A.SEC_CUADRATURA_CAJA),0) + 1
          INTO   v_nSecCuadraturaCaj
          FROM   CE_CUADRATURA_CAJA A
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL = cCodLocal_in
          AND    SEC_MOV_CAJA = cSecMovCaja_in;

          INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA ,COD_LOCAL ,SEC_MOV_CAJA ,
                                         SEC_CUADRATURA_CAJA, COD_CUADRATURA ,MON_MONEDA_ORIGEN	,
                                         MON_TOTAL	,TIP_MONEDA	,
                                         USU_CREA_CUADRATURA_CAJA)
                                  VALUES(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in,
                                         v_nSecCuadraturaCaj,COD_CUADRATURA_DEFICIT_CAJERO,nMontoDeficit_in,
                                         nMontoDeficit_in,TIP_MONEDA_SOLES,
                                         vIdUsu_in);
       END IF;
  END;

  /* ************************************************************************ */
  FUNCTION CE_EVALUA_ELIMINACION_VB_CAJ(cCodGrupoCia_in IN CHAR,
  		   						                    cCodLocal_in	  IN CHAR,
 		  						                      cSecMovCaja_in  IN CHAR)
    RETURN NUMBER
  IS
    v_nCant NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nCant
       FROM   CE_HIST_VB_MOV_CAJA HVBM
       WHERE  HVBM.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    HVBM.COD_LOCAL = cCodLocal_in
       AND    HVBM.SEC_MOV_CAJA = cSecMovCaja_in
       AND    HVBM.IND_VB_MOV_CAJA = INDICADOR_NO
       AND    HVBM.TIP_VB_MOV_CAJA = TIP_VB_CAJERO;

     RETURN v_nCant;
   EXCEPTION
  	 WHEN OTHERS THEN
          v_nCant := 1;--devolvemos 1 y asi no pasara la validacion
		 RETURN v_nCant;
  END;

  /****************************************************************************/

  FUNCTION CE_COMPROBANTES_VALIDOS_CT(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in	     IN CHAR,
                                      cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT NVL(MOV_CAJA.NUM_BOLETA_INICIAL,' ') || '�' ||
                NVL(MOV_CAJA.NUM_BOLETA_FINAL,' ') || '�' ||
                NVL(MOV_CAJA.NUM_FACTURA_INICIAL,' ') || '�' ||
                NVL(MOV_CAJA.NUM_FACTURA_FINAL,' ')
         FROM   CE_MOV_CAJA MOV_CAJA
         WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
         AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_IND_COMP_VALIDOS_USUARIO(cCodGrupoCia_in   IN CHAR,
  		   						                   cCodLocal_in	     IN CHAR,
 		  						                     cMovCajaCierre_in IN CHAR)
    RETURN CHAR
  IS
    v_cIndCompValidos CHAR(1);
  BEGIN
       SELECT MOV_CAJA.IND_COMP_VALIDOS
       INTO   v_cIndCompValidos
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in;

     RETURN v_cIndCompValidos;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cIndCompValidos := INDICADOR_NO;--NO SE ENCONTRO EL REGISTRO
		 RETURN v_cIndCompValidos;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACTUALIZA_COMPROBANTES_CT(cCodGrupoCia_in   IN CHAR,
   	                                     cCodLocal_in      IN CHAR,
	                                       cSecMovCaja_in    IN CHAR,
                                         cBoletaIni_in     IN CHAR,
                                         cBoletaFin_in     IN CHAR,
                                         cFacturaIni_in    IN CHAR,
                                         cFacturaFin_in    IN CHAR,
                                         cIndCompValido_in IN CHAR,
                                         cUsuModMovCaj_in  IN CHAR)
  IS
  BEGIN
        UPDATE CE_MOV_CAJA MOV_CAJA SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in, MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE ,
               MOV_CAJA.NUM_BOLETA_INICIAL = cBoletaIni_in,
               MOV_CAJA.NUM_BOLETA_FINAL = cBoletaFin_in,
               MOV_CAJA.NUM_FACTURA_INICIAL = cFacturaIni_in,
               MOV_CAJA.NUM_FACTURA_FINAL = cFacturaFin_in,
               MOV_CAJA.IND_COMP_VALIDOS = cIndCompValido_in
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_OBS_CIERRE_TURNO(cCodGrupoCia_in   IN CHAR,
  		   						                   cCodLocal_in	     IN CHAR,
 		  						                     cMovCajaCierre_in IN CHAR)
    RETURN VARCHAR2
  IS
    v_cObsCierreTurno VARCHAR2(300);
  BEGIN
       SELECT NVL(MOV_CAJA.DESC_OBS_CIERRE_TURNO,' ')
       INTO   v_cObsCierreTurno
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.SEC_MOV_CAJA = cMovCajaCierre_in;

     RETURN v_cObsCierreTurno;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cObsCierreTurno := ' ';--NO SE ENCONTRO EL REGISTRO
		 RETURN v_cObsCierreTurno;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACT_OBSERV_CIERRE_TURNO(cCodGrupoCia_in    IN CHAR,
   	                                   cCodLocal_in       IN CHAR,
	                                     cSecMovCaja_in     IN CHAR,
                                       cObsCierreTurno_in IN CHAR,
                                       cUsuModMovCaj_in   IN CHAR)
  IS
  BEGIN
        UPDATE CE_MOV_CAJA MOV_CAJA SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in, MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE ,
               MOV_CAJA.DESC_OBS_CIERRE_TURNO = cObsCierreTurno_in
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACT_IND_COMP_VALIDO_CIERRET(cCodGrupoCia_in   IN CHAR,
   	                                       cCodLocal_in      IN CHAR,
	                                         cSecMovCaja_in    IN CHAR,
                                           cIndCompValido_in IN CHAR,
                                           cUsuModMovCaj_in  IN CHAR)
  IS
  BEGIN
        UPDATE CE_MOV_CAJA MOV_CAJA SET MOV_CAJA.Usu_Mod_Mov_Caja = cUsuModMovCaj_in, MOV_CAJA.Fec_Mod_Mov_Caja = SYSDATE ,
               MOV_CAJA.IND_COMP_VALIDOS = cIndCompValido_in
        WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
        AND    MOV_CAJA.SEC_MOV_CAJA= cSecMovCaja_in;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_CAJEROS_DIA_VENTA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT USU_LOCAL.SEC_USU_LOCAL || '�' ||
                NVL(TRIM(USU_LOCAL.NOM_USU || ' ' || USU_LOCAL.APE_PAT || ' ' || USU_LOCAL.APE_MAT),' ')
         FROM   PBL_USU_LOCAL USU_LOCAL
         WHERE  USU_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    USU_LOCAL.COD_LOCAL = cCodLocal_in
         AND    USU_LOCAL.SEC_USU_LOCAL IN (SELECT MOV_CAJA.SEC_USU_LOCAL
                                            FROM   CE_MOV_CAJA MOV_CAJA
                                            WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
                                            AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in ,'dd/MM/yyyy'));
    RETURN curCE;
  END;

/************************CIERRE DE DIA*****************************/

  /****************************************************************************/
FUNCTION CE_LISTA_HIST_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in	  IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
    vNumeroDias number;
  BEGIN

 SELECT to_number(nvl(A.LLAVE_TAB_GRAL,'60'))
   INTO vNumeroDias
   FROM PBL_TAB_GRAL A
  WHERE A.ID_TAB_GRAL = 386;


    OPEN curCE FOR
        SELECT TO_CHAR(CIERRE.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy') || '�' ||
               NVL(USU_LOCAL.NOM_USU || ' ' || USU_LOCAL.APE_PAT || ' ' || USU_LOCAL.APE_MAT,' ') || '�' ||
               NVL(TO_CHAR(CIERRE.FEC_VB_CIERRE_DIA,'dd/MM/yyyy HH24:MI'),' ') || '�' ||
               NVL(TO_CHAR(CIERRE.FEC_VB_CONTABLE,'dd/MM/yyyy HH24:MI'),' ') || '�' ||--NVL(CIERRE.IND_VB_CONTABLE,' ')|| '�' ||
               NVL(TO_CHAR(CIERRE.FEC_PROCESO,'dd/MM/yyyy HH24:MI'),' ') || '�' ||
               NVL(TO_CHAR(CIERRE.FEC_ARCHIVO,'dd/MM/yyyy HH24:MI'),' ') || '�' ||
               NVL(CIERRE.SEC_USU_LOCAL_CREA,' ') || '�' ||
               CIERRE.IND_VB_CIERRE_DIA || '�' ||
               TO_CHAR(NVL(CIERRE.TIP_CAMBIO_CIERRE_DIA,0),'990.00') || '�' ||
               TO_CHAR(CIERRE.FEC_CIERRE_DIA_VTA,'yyyyMMdd') || '�' ||
               -----
               NVL(CIERRE.IND_VB_CONTABLE,' ')
        FROM   CE_CIERRE_DIA_VENTA CIERRE,
               PBL_USU_LOCAL USU_LOCAL
        WHERE  CIERRE.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CIERRE.COD_LOCAL = cCodLocal_in
        AND    CIERRE.FEC_CIERRE_DIA_VTA BETWEEN TRUNC(SYSDATE - vNumeroDias) AND TRUNC(SYSDATE) + 1 -1/24/60/60
        AND    CIERRE.COD_GRUPO_CIA = USU_LOCAL.COD_GRUPO_CIA
        AND    CIERRE.COD_LOCAL = USU_LOCAL.COD_LOCAL
        AND    CIERRE.SEC_USU_LOCAL_CREA = USU_LOCAL.SEC_USU_LOCAL;
    RETURN curCE;
  END;

  /****************************************************************************/
  PROCEDURE CE_REGISTRA_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
						   	                   cCodLocal_in    IN CHAR,
							                     cCierreDia_in   IN CHAR,
                                   cSecUsuLocal_in IN CHAR,
                                   nTipoCambio_in  IN NUMBER,
							                     cUsuCrea_in     IN CHAR)
  IS
    v_nSecHistVB CE_HIST_VB_MOV_CAJA.SEC_HIST_VB_MOV_CAJA%TYPE;

    cursor vCurPedInvDiario is
    select c.cod_grupo_cia,c.cod_local,trunc(c.fec_ped_vta) fecha,
       '021' CodigoCuadratura,c.tip_comp_pago,c.val_neto_ped_vta,
       d.cod_grupo_cia dCia,d.cod_trab,d.monto,
       cUsuCrea_in,'TOMA INV DIARIO' motivo,f.tip_moneda,c.num_ped_vta
    from   vta_pedido_vta_cab c,
           vta_forma_pago_pedido f,
           tmp_ped_cab_inv_diario t,
           tmp_ped_dcto_x_trab d
    where  c.cod_grupo_cia = cCodGrupoCia_in
    and    c.cod_local = cCodLocal_in
    AND    c.est_ped_vta = 'C'
    and    c.fec_ped_vta BETWEEN TO_DATE(cCierreDia_in || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                         	AND	   TO_DATE(cCierreDia_in || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    and    t.estado = 'P'
    and    c.cod_grupo_cia = f.cod_grupo_cia
    and    c.cod_local = f.cod_local
    and    c.num_ped_vta = f.num_ped_vta
    and    c.cod_grupo_cia = T.COD_GRUPO_CIA
    and    c.cod_local = t.cod_local
    and    c.num_ped_vta = t.num_ped_vta
    and    t.cod_grupo_cia = d.cod_grupo_cia
    and    t.cod_local = d.cod_local
    and    t.sec_pedido = d.sec_pedido;



  BEGIN

      INSERT INTO CE_CIERRE_DIA_VENTA
                   (COD_GRUPO_CIA,COD_LOCAL,FEC_CIERRE_DIA_VTA,
                    SEC_USU_LOCAL_CREA,IND_VB_CIERRE_DIA,FEC_VB_CIERRE_DIA,DESC_OBSV_CIERRE_DIA,TIP_CAMBIO_CIERRE_DIA,
                    USU_CREA_CIERRE_DIA,FEC_CREA_CIERRE_DIA,USU_MOD_CIERRE_DIA,FEC_MOD_CIERRE_DIA)
             VALUES(cCodGrupoCia_in, cCodLocal_in, TO_DATE(cCierreDia_in,'dd/MM/yyyy'),
                    cSecUsuLocal_in, INDICADOR_NO, NULL, NULL, nTipoCambio_in,
                    cUsuCrea_in ,SYSDATE, NULL, NULL);

      UPDATE CE_MOV_CAJA MOV_CAJA
         SET MOV_CAJA.USU_MOD_MOV_CAJA = cUsuCrea_in, MOV_CAJA.FEC_MOD_MOV_CAJA = SYSDATE,
             MOV_CAJA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       WHERE MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND   MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND   MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND   MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE;


       ---coloca las cuadratura de Descuento a Personal
       for vCurPed in vCurPedInvDiario  loop
          ptoventa_ce_ern.valida_cuadratura_021(vCurPed.Cod_Grupo_Cia,
                                                vCurPed.Cod_Local,
                                                cCierreDia_in,
                                                vCurPed.Codigocuadratura,
                                                vCurPed.Tip_Comp_Pago,
                                                vCurPed.Val_Neto_Ped_Vta,
                                                vCurPed.Dcia,
                                                vCurPed.cod_trab,
                                                vCurPed.Monto,
                                                cUsuCrea_in,
                                                vCurPed.Monto,
                                                vCurPed.Tip_Moneda,
                                                vCurPed.Num_Ped_Vta);
       end loop;

  END;

  /* ************************************************************************ */
  FUNCTION CE_VALIDA_REGISTRO_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
  		   						                     cCodLocal_in	   IN CHAR,
 		  						                       cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nContador NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nContador
       FROM   CE_CIERRE_DIA_VENTA CIERRE
       WHERE  CIERRE.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CIERRE.COD_LOCAL = cCodLocal_in
       AND    CIERRE.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

     RETURN v_nContador;
   EXCEPTION
  	 WHEN OTHERS THEN
          v_nContador := 1;--ERROR Y DEVOLVEMOS 1 PARA QUE NO PASE LA VALIDACION
		 RETURN v_nContador;
  END;

  /* ************************************************************************ */
  FUNCTION CE_OBTIENE_CAJ_APERTURADAS_DIA(cCodGrupoCia_in IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
 		  						                        cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nContador NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nContador
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_APERTURA;

     RETURN v_nContador;
   EXCEPTION
  	 WHEN OTHERS THEN
          v_nContador := 0;--ERROR Y DEVOLVEMOS 0 PARA QUE NO PASE LA VALIDACION
		 RETURN v_nContador;
  END;

  /* ************************************************************************ */
  FUNCTION CE_VALIDA_CAJA_CON_VB_CAJERO(cCodGrupoCia_in IN CHAR,
  		   						                    cCodLocal_in	  IN CHAR,
 		  						                      cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nCantCajAperturadas NUMBER;
    v_nCantCajCerradas    NUMBER;
    v_nDiferencia         NUMBER;
  BEGIN
       v_nCantCajAperturadas := CE_OBTIENE_CAJ_APERTURADAS_DIA(cCodGrupoCia_in,
  		   						                                           cCodLocal_in,
                                                               cCierreDia_in);

       SELECT COUNT(1)
       INTO   v_nCantCajCerradas
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE
       AND    MOV_CAJA.IND_VB_CAJERO = INDICADOR_SI;

       v_nDiferencia := v_nCantCajAperturadas - v_nCantCajCerradas;

     RETURN v_nDiferencia;
   EXCEPTION
  	 WHEN OTHERS THEN
          v_nDiferencia := 1;--ERROR Y DEVOLVEMOS 1 PARA QUE NO PASE LA VALIDACION
		 RETURN v_nDiferencia;
  END;

  /****************************************************************************/
  FUNCTION CE_CONSO_FOR_PAG_ENT_CIER_DIA(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in	   IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT P.DESC_CORTA_FORMA_PAGO || '�' ||
               DECODE(F.TIP_MONEDA,TIP_MONEDA_DOLARES,'DOLARES','SOLES') || '�' ||
               TO_CHAR(SUM(F.MON_ENTREGA),'999,990.00') || '�' ||
               TO_CHAR(SUM(F.MON_ENTREGA_TOTAL),'999,990.00') || '�' ||
               F.COD_FORMA_PAGO
        FROM   CE_FORMA_PAGO_ENTREGA F,
               VTA_FORMA_PAGO P
        WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    F.COD_LOCAL = cCodLocal_in
        AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                  FROM   CE_MOV_CAJA A
                                  WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND    A.COD_LOCAL = cCodLocal_in
                                  AND    A.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                                  AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
        AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
        AND    F.EST_FORMA_PAGO_ENT = 'A'
        GROUP BY P.DESC_CORTA_FORMA_PAGO, F.TIP_MONEDA, F.COD_FORMA_PAGO;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_CONSO_CUADRATURA_CIER_DIA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in	  IN CHAR,
                                        cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT CAJ.COD_CUADRATURA || '�' ||
               CUA.DESC_CUADRATURA || '�' ||
               TO_CHAR(SUM(CAJ.MON_TOTAL) * CUA.VAL_SIGNO,'999,990.00')
        FROM   CE_CUADRATURA_CAJA CAJ,
               CE_CUADRATURA CUA
        WHERE  CAJ.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CAJ.COD_LOCAL = cCodLocal_in
        AND    CAJ.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                    FROM   CE_MOV_CAJA A
                                    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    A.COD_LOCAL = cCodLocal_in
                                    AND    A.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                                    AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
        AND    CUA.COD_GRUPO_CIA = CAJ.COD_GRUPO_CIA
        AND    CUA.COD_CUADRATURA= CAJ.COD_CUADRATURA
        GROUP BY CAJ.COD_CUADRATURA, CUA.DESC_CUADRATURA, CUA.VAL_SIGNO;

    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_CONSO_EFEC_RECAUDADO_CIERRE(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in	   IN CHAR,
                                          cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT P.DESC_CORTA_FORMA_PAGO || '�' ||
               DECODE(F.TIP_MONEDA,TIP_MONEDA_DOLARES,'DOLARES','SOLES') || '�' ||
               TO_CHAR(SUM(F.MON_ENTREGA),'999,990.00') || '�' ||
               TO_CHAR(SUM(F.MON_ENTREGA_TOTAL),'999,990.00') || '�' ||
               F.COD_FORMA_PAGO
        FROM   CE_FORMA_PAGO_ENTREGA F,
               VTA_FORMA_PAGO P
        WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    F.COD_LOCAL = cCodLocal_in
        AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                  FROM   CE_MOV_CAJA A
                                  WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND    A.COD_LOCAL = cCodLocal_in
                                  AND    A.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                                  AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
        AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
        AND    F.EST_FORMA_PAGO_ENT = 'A'
        AND    P.IND_FORMA_PAGO_EFECTIVO = INDICADOR_SI
        GROUP BY P.DESC_CORTA_FORMA_PAGO, F.TIP_MONEDA, F.COD_FORMA_PAGO;

    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_MONTO_TOTAL_SIST_CIERRE_DIA(cCodGrupoCia_in IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
 		  						                        cCierreDia_in   IN CHAR)
    RETURN CHAR
  IS
    v_cTotalSistema CHAR(20);
  BEGIN
       SELECT TO_CHAR(SUM(NVL(E.MON_TOT,0)),'999,990.00')
       INTO   v_cTotalSistema
       FROM   CE_MOV_CAJA E
       WHERE  E.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    E.COD_LOCAL = cCodLocal_in
       AND    E.Fec_Cierre_Dia_Vta = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

     RETURN v_cTotalSistema;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cTotalSistema := '0.00';--NO SE ENCONTRO MONTO TOTAL DE SISTEMA
		 RETURN v_cTotalSistema;
  END;

  /****************************************************************************/
  FUNCTION CE_CONSO_EFEC_RENDIDO_CIERRE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in	  IN CHAR,
                                        cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT CUADRATURA_CIERRE_DIA.COD_CUADRATURA || '�' ||
               NVL(CUADRATURA.DESC_CUADRATURA,' ') || '�' ||
               --TO_CHAR(SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL) ),'999,999,990.00')
               TO_CHAR(DECODE(CUADRATURA.COD_CUADRATURA,COD_CUADRATURA_DEL_PERDIDO,SUM(CUADRATURA_CIERRE_DIA.MON_PERDIDO_TOTAL),SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL * DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)))),'999,999,990.00')
               --TO_CHAR(SUM( DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL) * DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)),'999,999,990.00')
        FROM   CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
               CE_CUADRATURA CUADRATURA,
               CE_CIERRE_DIA_VENTA DV
        WHERE  CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CUADRATURA_CIERRE_DIA.COD_LOCAL = cCodLocal_in
        AND    CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
        AND    CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
        AND    CUADRATURA_CIERRE_DIA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
        AND    DV.COD_GRUPO_CIA = CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA
        AND    DV.COD_LOCAL = CUADRATURA_CIERRE_DIA.COD_LOCAL
        AND    DV.FEC_CIERRE_DIA_VTA = CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA
        GROUP BY CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                 CUADRATURA.DESC_CUADRATURA,
                 CUADRATURA.COD_CUADRATURA
        union
        SELECT '011' ||'�'||
               'PROSEGUR'||'�'||
               TO_CHAR(V.MONT_SOBRES,'999,999,990.00')
        FROM   DUAL,
               (
               SELECT nvl(sum(CF.MON_ENTREGA_TOTAL),0) MONT_SOBRES
                FROM   CE_SOBRE S,
                       CE_FORMA_PAGO_ENTREGA CF
                WHERE  CF.COD_GRUPO_CIA = cCodGrupoCia_in
                AND    CF.COD_LOCAL     = cCodLocal_in
                AND    CF.EST_FORMA_PAGO_ENT = 'A'
                AND    S.FEC_DIA_VTA    = to_date(cCierreDia_in,'dd/MM/yyyy')
                AND    S.COD_GRUPO_CIA = CF.COD_GRUPO_CIA
                AND    S.COD_LOCAL = CF.COD_LOCAL
                AND    S.SEC_MOV_CAJA = CF.SEC_MOV_CAJA
                AND    S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA
                ) V
        WHERE   V.MONT_SOBRES > 0;



    RETURN curCE;
  END;

  /* ************************************************************************* */
  FUNCTION CE_IND_VB_CIERRE_DIA_FORUPDATE(cCodGrupoCia_in IN CHAR,
						   	                          cCodLocal_in    IN CHAR,
							                            cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT CIERRE_DIA_VENTA.IND_VB_CIERRE_DIA
		     FROM   CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA
		     WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
		     AND	  CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
		     AND	  CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy') FOR UPDATE;
    RETURN curCE;
  END;

  /* ************************************************************************* */
  PROCEDURE CE_ACTUALIZA_VB_CIERRE_DIA(cCodGrupoCia_in     IN CHAR,
   	                                   cCodLocal_in        IN CHAR,
	                                     cCierreDia_in       IN CHAR,
                                       cIndVBCierreDia_in  IN CHAR,
                                       cDescObs_in         IN CHAR,
                                       cSecUsuVB_in        IN CHAR,
                                       cUsuModCierreDia_in IN CHAR)
  IS
  vMontDif number;
  vMontVentas number;
  vMontTurno  number;
  BEGIN
       IF cIndVBCierreDia_in = INDICADOR_SI THEN


          SELECT SUM(C.VAL_NETO_PED_VTA) MONTO_VTA
          into   vMontVentas
          FROM VTA_PEDIDO_VTA_CAB C
          WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.EST_PED_VTA = 'C'
            AND C.FEC_PED_VTA BETWEEN TO_DATE(cCierreDia_in || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                      TO_DATE(cCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS');

        SELECT SUM(M.MON_TOT) MONTO_CJA
        into   vMontTurno
        FROM   CE_MOV_CAJA M
        WHERE  M.FEC_DIA_VTA BETWEEN TO_DATE(cCierreDia_in  || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                      TO_DATE(cCierreDia_in || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
        AND M.TIP_MOV_CAJA = 'C'
        AND M.COD_GRUPO_CIA = cCodGrupoCia_in
        AND M.COD_LOCAL = cCodLocal_in;

        SELECT ABS(vMontVentas - vMontTurno) INTO vMontDif FROM DUAL;

          IF vMontDif !=0 THEN
            RAISE_APPLICATION_ERROR(-20002,'Existe diferencia de S/.'||
                                           trim(to_char(vMontDif,'999999.00'))||
                                           ' en los cierre de turno y las ventas del dia.');
          END IF;


          UPDATE CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA SET CIERRE_DIA_VENTA.Usu_Mod_Cierre_Dia = cUsuModCierreDia_in, CIERRE_DIA_VENTA.Fec_Mod_Cierre_Dia = SYSDATE ,
                 CIERRE_DIA_VENTA.Ind_Vb_Cierre_Dia = cIndVBCierreDia_in,
                 CIERRE_DIA_VENTA.DESC_OBSV_CIERRE_DIA = cDescObs_in,
                 CIERRE_DIA_VENTA.SEC_USU_LOCAL_VB = cSecUsuVB_in,
                 CIERRE_DIA_VENTA.FEC_VB_CIERRE_DIA= SYSDATE
          WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
    		  AND	   CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
    		  AND	   CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');


       ELSIF cIndVBCierreDia_in = INDICADOR_NO THEN
          UPDATE CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA SET CIERRE_DIA_VENTA.Usu_Mod_Cierre_Dia = cUsuModCierreDia_in, CIERRE_DIA_VENTA.Fec_Mod_Cierre_Dia = SYSDATE ,
                 CIERRE_DIA_VENTA.Ind_Vb_Cierre_Dia = cIndVBCierreDia_in,
                 CIERRE_DIA_VENTA.FEC_VB_CIERRE_DIA = NULL,
                 CIERRE_DIA_VENTA.SEC_USU_LOCAL_VB = NULL
          WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
    		  AND	   CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
    		  AND	   CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
       END IF;
  END;

  /****************************************************************************/
  PROCEDURE CE_REGISTRA_HIST_VB_CIERRE(cCodGrupoCia_in      IN CHAR,
						   	                       cCodLocal_in         IN CHAR,
							                         cCierreDia_in        IN CHAR,
                                       cIndVBCierreDia_in   IN CHAR,
                                       cSecUsuCrea_in       IN CHAR,
                                       cSecUsuVB_in         IN CHAR,
                                       cDescObs_in          IN CHAR,
                                       cTipoCambio_in       IN NUMBER,
							                         cUsuCreaCierreDia_in IN CHAR)
  IS
    v_nSecHistVBCierreDia CE_HIST_VB_CIERRE_DIA.Sec_Hist_Vb_Cierre_Dia%TYPE;

    CURSOR efectivoRendido IS
		   		SELECT *
				  FROM   CE_CUADRATURA_CIERRE_DIA C
				  WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
				  AND	   C.COD_LOCAL = cCodLocal_in
				  AND	   C.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
          ORDER BY C.SEC_CUADRATURA_CIERRE_DIA;
  BEGIN
      SELECT COUNT(*) + 1
      INTO   v_nSecHistVBCierreDia
      FROM   CE_HIST_VB_CIERRE_DIA A
      WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    A.COD_LOCAL = cCodLocal_in
      AND    A.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

      INSERT INTO CE_HIST_VB_CIERRE_DIA
                   (COD_GRUPO_CIA,COD_LOCAL,FEC_CIERRE_DIA_VTA,
                    SEC_HIST_VB_CIERRE_DIA,SEC_USU_CREA,SEC_USU_VB,IND_VB_CIERRE_DIA,DESC_MOTIVO,
                    DESC_OBSV_HIST, TIP_CAMBIO_HIST, USU_CREA_VB_CIERRE_DIA,FEC_CREA_VB_CIERRE_DIA)
             VALUES(cCodGrupoCia_in, cCodLocal_in, TO_DATE(cCierreDia_in,'dd/MM/yyyy'),
                    v_nSecHistVBCierreDia, cSecUsuCrea_in, cSecUsuVB_in, cIndVBCierreDia_in, NULL,
                    cDescObs_in, cTipoCambio_in, cUsuCreaCierreDia_in, SYSDATE);

      IF cIndVBCierreDia_in = INDICADOR_NO THEN
         FOR efectivoRendido_rec IN efectivoRendido
         LOOP
             INSERT INTO CE_HIST_CUADRATURA_CIERRE_DIA
                         (COD_GRUPO_CIA,COD_LOCAL,
                          FEC_CIERRE_DIA_VTA,SEC_HIST_VB_CIERRE_DIA,
                          SEC_CUADRATURA_CIERRE_DIA,COD_CUADRATURA_HIST,
                          TIP_MONEDA_HIST,MON_MONEDA_ORIGEN_HIST,
                          MON_TOTAL_HIST,SEC_ENT_FINAN_CUENTA_HIST,
                          FEC_OPERACION_HIST,NUM_OPERACION_HIST,
                          NOM_AGENCIA_HIST,FEC_EMISION_HIST,
                          NUM_SERIE_LOCAL_HIST,TIP_COMP_HIST,
                          NUM_COMP_PAGO_HIST,NOM_TITULAR_SERVICIO_HIST,
                          COD_AUTORIZACION_HIST,COD_CIA_HIST,
                          COD_TRABAJADOR_HIST,DESC_MOTIVO_HIST,
                          COD_FORMA_PAGO_HIST,SERIE_BILLETE_HIST,
                          TIP_DINERO_HIST,MON_PARCIAL_HIST,
                          DESC_RUC_HIST,DESC_RAZON_SOCIAL_HIST,
                          COD_LOCAL_NUEVO_HIST,COD_SERVICIO_HIST,
                          USU_CREA_CUADRA_CIERRE_HIST,FEC_CREA_CUADRA_CIERRE_HIST,
                          NUM_NOTA_ES_HIST,COD_PROVEEDOR_HIST,
                          FEC_VENCIMIENTO_HIST, TIP_DOCUMENTO_HIST,
                          NUM_DOCUMENTO_HIST,MES_PERIODO_HIST,
                          ANO_EJERCICIO_HIST, NUM_REFERENCIA_HIST)
                  VALUES (efectivoRendido_rec.COD_GRUPO_CIA, efectivoRendido_rec.COD_LOCAL,
                          efectivoRendido_rec.FEC_CIERRE_DIA_VTA, v_nSecHistVBCierreDia,
                          efectivoRendido_rec.SEC_CUADRATURA_CIERRE_DIA, efectivoRendido_rec.COD_CUADRATURA,
                          efectivoRendido_rec.TIP_MONEDA, efectivoRendido_rec.MON_MONEDA_ORIGEN,
                          efectivoRendido_rec.MON_TOTAL, efectivoRendido_rec.SEC_ENT_FINAN_CUENTA,
                          efectivoRendido_rec.FEC_OPERACION, efectivoRendido_rec.NUM_OPERACION,
                          efectivoRendido_rec.NOM_AGENCIA, efectivoRendido_rec.FEC_EMISION,
                          efectivoRendido_rec.NUM_SERIE_LOCAL, efectivoRendido_rec.TIP_COMP,
                          efectivoRendido_rec.NUM_COMP_PAGO, efectivoRendido_rec.NOM_TITULAR_SERVICIO,
                          efectivoRendido_rec.COD_AUTORIZACION, efectivoRendido_rec.COD_CIA,
                          efectivoRendido_rec.COD_TRAB, efectivoRendido_rec.DESC_MOTIVO,
                          efectivoRendido_rec.COD_FORMA_PAGO, efectivoRendido_rec.SERIE_BILLETE,
                          efectivoRendido_rec.TIP_DINERO, efectivoRendido_rec.MON_PARCIAL,
                          efectivoRendido_rec.DESC_RUC, efectivoRendido_rec.DESC_RAZON_SOCIAL,
                          efectivoRendido_rec.COD_LOCAL_NUEVO, efectivoRendido_rec.COD_SERVICIO,
                          cUsuCreaCierreDia_in, SYSDATE,
                          efectivoRendido_rec.NUM_NOTA_ES, efectivoRendido_rec.COD_PROVEEDOR,
                          efectivoRendido_rec.FEC_VENCIMIENTO, efectivoRendido_rec.TIP_DOCUMENTO,
                          efectivoRendido_rec.NUM_DOCUMENTO,efectivoRendido_rec.MES_PERIODO,
                          efectivoRendido_rec.ANO_EJERCICIO, efectivoRendido_rec.NUM_REFERENCIA );
         END LOOP;
      END IF;
  END;

  /* ************************************************************************* */
  PROCEDURE CE_ACT_INFO_HIST_VB_CIER_DIA(cCodGrupoCia_in     IN CHAR,
   	                                     cCodLocal_in        IN CHAR,
	                                       cCierreDia_in       IN CHAR,
                                         cSecUsuVB_in        IN CHAR,
                                         cDescMotivo_in      IN CHAR)
  IS
  PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
      UPDATE CE_HIST_VB_CIERRE_DIA HVBCD
             SET HVBCD.SEC_USU_VB= cSecUsuVB_in,
                 HVBCD.DESC_MOTIVO = cDescMotivo_in
           WHERE HVBCD.COD_GRUPO_CIA = cCodGrupoCia_in
           AND   HVBCD.COD_LOCAL = cCodLocal_in
           AND   HVBCD.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
           AND   HVBCD.SEC_HIST_VB_CIERRE_DIA IN (SELECT MAX(SEC_HIST_VB_CIERRE_DIA)
                                                  FROM   CE_HIST_VB_CIERRE_DIA
                                                  WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                                  AND    COD_LOCAL = cCodLocal_in
                                                  AND    FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy'));
      COMMIT;
  EXCEPTION
	    WHEN OTHERS THEN
			  	 ROLLBACK;
  END;

  /* ************************************************************************* */
  FUNCTION CE_LISTA_SERIES_TIP_DOC(cCodGrupoCia_in IN CHAR,
						   	                   cCodLocal_in    IN CHAR,
							                     cTipDoc_in      IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT NUM_SERIE_LOCAL || '�' ||
                NUM_SERIE_LOCAL
         FROM   VTA_SERIE_LOCAL SERIE_LOCAL
         WHERE  SERIE_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    SERIE_LOCAL.COD_LOCAL = cCodLocal_in
         AND    SERIE_LOCAL.TIP_COMP = cTipDoc_in
         AND    SERIE_LOCAL.EST_SERIE_LOCAL = ESTADO_ACTIVO;
    RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_OBS_USU_VB_CD(cCodGrupoCia_in IN CHAR,
  		   						                cCodLocal_in	  IN CHAR,
 		  						                  cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
       SELECT NVL(CIERRE_DIA_VENTA.DESC_OBSV_CIERRE_DIA,' ') || '�' ||
              NVL(USU_LOCAL.NOM_USU || ' ' || USU_LOCAL.APE_PAT || ' ' || USU_LOCAL.APE_MAT,' ')
       FROM   CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA,
              PBL_USU_LOCAL USU_LOCAL
       WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
       AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA= TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    CIERRE_DIA_VENTA.COD_GRUPO_CIA = USU_LOCAL.COD_GRUPO_CIA(+)
       AND    CIERRE_DIA_VENTA.COD_LOCAL = USU_LOCAL.COD_LOCAL(+)
       AND    CIERRE_DIA_VENTA.Sec_Usu_Local_Vb = USU_LOCAL.SEC_USU_LOCAL(+                   );

     RETURN curCE;
  END;

  /****************************************************************************/
  FUNCTION CE_IND_COMP_VALIDOS_DIA(cCodGrupoCia_in IN CHAR,
  		   						               cCodLocal_in	   IN CHAR,
 		  						                 cCierreDia_in   IN CHAR)
    RETURN CHAR
  IS
    v_cIndCompValidos CHAR(1);
  BEGIN
       SELECT CIERRE_DIA_VENTA.IND_COMP_VALIDOS
       INTO   v_cIndCompValidos
       FROM   CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA
       WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
       AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

     RETURN v_cIndCompValidos;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cIndCompValidos := INDICADOR_NO;--NO SE ENCONTRO EL REGISTRO
		 RETURN v_cIndCompValidos;
  END;

  /****************************************************************************/
  FUNCTION CE_COMPROBANTES_VALIDOS_CD(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT NVL(CIERRE_DIA_VENTA.NUM_BOLETA_INICIAL,' ') || '�' ||
                NVL(CIERRE_DIA_VENTA.NUM_BOLETA_FINAL,' ') || '�' ||
                NVL(CIERRE_DIA_VENTA.NUM_FACTURA_INICIAL,' ') || '�' ||
                NVL(CIERRE_DIA_VENTA.NUM_FACTURA_FINAL,' ')
         FROM   CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA
         WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
         AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

    RETURN curCE;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACTUALIZA_COMPROBANTES_CD(cCodGrupoCia_in   IN CHAR,
   	                                     cCodLocal_in      IN CHAR,
	                                       cCierreDia_in     IN CHAR,
                                         cBoletaIni_in     IN CHAR,
                                         cBoletaFin_in     IN CHAR,
                                         cFacturaIni_in    IN CHAR,
                                         cFacturaFin_in    IN CHAR,
                                         cIndCompValido_in IN CHAR,
                                         cUsuModCD_in      IN CHAR)
  IS
  BEGIN
        UPDATE CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA SET CIERRE_DIA_VENTA.USU_MOD_CIERRE_DIA = cUsuModCD_in, CIERRE_DIA_VENTA.FEC_MOD_CIERRE_DIA = SYSDATE ,
               CIERRE_DIA_VENTA.NUM_BOLETA_INICIAL = cBoletaIni_in,
               CIERRE_DIA_VENTA.NUM_BOLETA_FINAL = cBoletaFin_in,
               CIERRE_DIA_VENTA.NUM_FACTURA_INICIAL = cFacturaIni_in,
               CIERRE_DIA_VENTA.NUM_FACTURA_FINAL = cFacturaFin_in,
               CIERRE_DIA_VENTA.IND_COMP_VALIDOS = cIndCompValido_in
        WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
        AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ACT_IND_COMP_VALIDO_CIERRED(cCodGrupoCia_in   IN CHAR,
   	                                       cCodLocal_in      IN CHAR,
	                                         cCierreDia_in     IN CHAR,
                                           cIndCompValido_in IN CHAR,
                                           cUsuModCD_in      IN CHAR)
  IS
  BEGIN
        UPDATE CE_CIERRE_DIA_VENTA CIERRE_DIA_VENTA SET CIERRE_DIA_VENTA.USU_MOD_CIERRE_DIA = cUsuModCD_in, CIERRE_DIA_VENTA.FEC_MOD_CIERRE_DIA = SYSDATE ,
               CIERRE_DIA_VENTA.IND_COMP_VALIDOS = cIndCompValido_in
        WHERE  CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CIERRE_DIA_VENTA.COD_LOCAL = cCodLocal_in
        AND    CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
  END;

  /* ************************************************************************ */
  PROCEDURE CE_EVALUA_DEFICIT_ASUMIDO_QF(cCodGrupoCia_in  IN CHAR,
  		   						                     cCodLocal_in	    IN CHAR,
 		  						                       cCierreDia_in    IN CHAR,
                                         nMontoDeficit_in IN NUMBER,
                                         vSecUsuLocal_in  IN CHAR,
                                         vIdUsu_in        IN CHAR)
  IS
    v_nSecCuadraturaCierreDia NUMBER;
    v_cCodCiaTrab             PBL_USU_LOCAL.COD_CIA%TYPE;
    v_cCodTrab                PBL_USU_LOCAL.COD_TRAB%TYPE;
  BEGIN
       IF nMontoDeficit_in <> 0 THEN --sirve para eliminar si o si el deficit asumido
          DELETE FROM CE_CUADRATURA_CIERRE_DIA CD
                WHERE CD.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   CD.COD_LOCAL = cCodLocal_in
                AND   CD.FEC_CIERRE_DIA_VTA= TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                AND   CD.COD_CUADRATURA= COD_CUADRATURA_DEFICIT_QF;
       END IF;

       IF nMontoDeficit_in > 0 THEN

          SELECT NVL(MAX(CD.SEC_CUADRATURA_CIERRE_DIA),0) + 1
          INTO   v_nSecCuadraturaCierreDia
          FROM   CE_CUADRATURA_CIERRE_DIA CD
          WHERE  CD.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    CD.COD_LOCAL = cCodLocal_in
          AND    CD.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');

          SELECT NVL(U.COD_CIA,''),
                 NVL(U.COD_TRAB,'')
          INTO   v_cCodCiaTrab,
                 v_cCodTrab
          FROM   PBL_USU_LOCAL U
          WHERE  U.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    U.COD_LOCAL = cCodLocal_in
          AND    U.SEC_USU_LOCAL = vSecUsuLocal_in;

          /*dbms_output.put_line('v_cCodCiaTrab: ' || v_cCodCiaTrab );
          dbms_output.put_line('v_cCodTrab: ' || v_cCodTrab );
          dbms_output.put_line('cCierreDia_in : ' || cCierreDia_in );
          dbms_output.put_line('TO_DATE(cCierreDia_in,dd/MM/yyyy): ' || TO_DATE(cCierreDia_in,'dd/MM/yyyy') );*/

          INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA ,COD_LOCAL ,FEC_CIERRE_DIA_VTA ,
                                               SEC_CUADRATURA_CIERRE_DIA, COD_CUADRATURA ,MON_MONEDA_ORIGEN	,
                                               MON_TOTAL , MON_PARCIAL, TIP_MONEDA	, COD_CIA, COD_TRAB,
                                               MES_PERIODO, ANO_EJERCICIO, USU_CREA_CUADRA_CIERRE_DIA)
                                        VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(cCierreDia_in,'dd/MM/yyyy'),
                                               v_nSecCuadraturaCierreDia,COD_CUADRATURA_DEFICIT_QF,nMontoDeficit_in,
                                               nMontoDeficit_in,nMontoDeficit_in,TIP_MONEDA_SOLES,v_cCodCiaTrab, v_cCodTrab,
                                               To_char(TO_date(cCierreDia_in,'dd/MM/yyyy'),'MM'),
                                               to_char(TO_date(cCierreDia_in,'dd/MM/yyyy'),'yyyy'),
                                               vIdUsu_in);
       END IF;
  END;

  /* ************************************************************************ */
  FUNCTION CE_VALIDA_CAJA_CON_VB_QF(cCodGrupoCia_in IN CHAR,
  		   						                cCodLocal_in	  IN CHAR,
 		  						                  cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nCantCajAperturadas NUMBER;
    v_nCantCajCerradas    NUMBER;
    v_nDiferencia         NUMBER;
  BEGIN
       v_nCantCajAperturadas := CE_OBTIENE_CAJ_APERTURADAS_DIA(cCodGrupoCia_in,
  		   						                                           cCodLocal_in,
                                                               cCierreDia_in);

       SELECT COUNT(1)
       INTO   v_nCantCajCerradas
       FROM   CE_MOV_CAJA MOV_CAJA
       WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    MOV_CAJA.COD_LOCAL = cCodLocal_in
       AND    MOV_CAJA.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    MOV_CAJA.TIP_MOV_CAJA = TIP_MOV_CIERRE
       AND    MOV_CAJA.IND_VB_QF = INDICADOR_SI;

       v_nDiferencia := v_nCantCajAperturadas - v_nCantCajCerradas;

     RETURN v_nDiferencia;
   EXCEPTION
  	 WHEN OTHERS THEN
          v_nDiferencia := 1;--ERROR Y DEVOLVEMOS 1 PARA QUE NO PASE LA VALIDACION
		 RETURN v_nDiferencia;
  END;

  /* ************************************************************************ */
  FUNCTION CE_VALIDA_CIERRE_DIA_CON_VB(cCodGrupoCia_in IN CHAR,
  		   						                   cCodLocal_in	   IN CHAR,
 		  						                     cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nContador NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nContador
       FROM   CE_CIERRE_DIA_VENTA CIERRE
       WHERE  CIERRE.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CIERRE.COD_LOCAL = cCodLocal_in
       AND    CIERRE.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    CIERRE.IND_VB_CIERRE_DIA = INDICADOR_SI;

     RETURN v_nContador;
   EXCEPTION
  	 WHEN OTHERS THEN
          v_nContador := 0;--ERROR Y DEVOLVEMOS 0 PARA QUE PASE LA VALIDACION
		 RETURN v_nContador;
  END;

  FUNCTION CE_LISTA_TIP_COMP(cCodGrupoCia_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT TIP_COMP.TIP_COMP || '�' ||
                TIP_COMP.DESC_COMP
         FROM   VTA_TIP_COMP TIP_COMP
         WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_LISTA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in	     IN CHAR,
                                       cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT TIP.DESC_COMP || '�' ||
                CMC.NUM_SERIE_LOCAL || '�' ||
                CMC.NUM_INICIAL || '�' ||
                CMC.NUM_FINAL || '�' ||
                CMC.TIP_COMP || '�' ||
                'S'
         FROM   CE_COMP_MOV_CAJA CMC,
                VTA_TIP_COMP TIP
         WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CMC.COD_LOCAL = cCodLocal_in
         AND    CMC.SEC_MOV_CAJA = cMovCajaCierre_in
         AND    CMC.COD_GRUPO_CIA = TIP.COD_GRUPO_CIA
         AND    CMC.TIP_COMP = TIP.TIP_COMP;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_LISTA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in	   IN CHAR,
                                       cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT TIP.DESC_COMP || '�' ||
                CCD.NUM_SERIE_LOCAL || '�' ||
                CCD.NUM_INICIAL || '�' ||
                CCD.NUM_FINAL || '�' ||
                CCD.TIP_COMP || '�' ||
                'S'
         FROM   CE_COMP_CIERRE_DIA_VENTA CCD,
                VTA_TIP_COMP TIP
         WHERE  CCD.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CCD.COD_LOCAL = cCodLocal_in
         AND    CCD.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in, 'dd/MM/yyyy')
         AND    CCD.COD_GRUPO_CIA = TIP.COD_GRUPO_CIA
         AND    CCD.TIP_COMP = TIP.TIP_COMP;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_OBTIENE_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in   IN CHAR,
                                         cCodLocal_in	     IN CHAR,
                                         cMovCajaCierre_in IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
    isLocalMultifuncional char(1);
  BEGIN
  select decode(trim(l.tip_caja),'M','S','N')
  into  isLocalMultifuncional
  from   pbl_local l
  where  l.cod_grupo_cia = cCodGrupoCia_in
  and    l.cod_local = cCodLocal_in;

  if isLocalMultifuncional = 'N' then
    OPEN curCE FOR
         SELECT CP.TIP_COMP_PAGO || '�' ||
                SUBSTR(CP.NUM_COMP_PAGO,1,3) || '�' ||
                SUBSTR(MIN(CP.NUM_COMP_PAGO),4) || '�' ||
                SUBSTR(MAX(CP.NUM_COMP_PAGO),4)
         FROM   VTA_COMP_PAGO CP
         WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CP.COD_LOCAL = cCodLocal_in
         AND    CP.TIP_COMP_PAGO IN (SELECT TIP_COMP.TIP_COMP
                                     FROM   VTA_TIP_COMP TIP_COMP
                                     WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                                     AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI)
         AND    CP.SEC_MOV_CAJA IN (SELECT CMC.SEC_MOV_CAJA_ORIGEN
                                    FROM   CE_MOV_CAJA CMC
                                    WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CMC.COD_LOCAL = cCodLocal_in
                                    AND    CMC.SEC_MOV_CAJA = cMovCajaCierre_in)
         GROUP BY CP.TIP_COMP_PAGO,
                  SUBSTR(CP.NUM_COMP_PAGO,1,3)
         ORDER BY CP.TIP_COMP_PAGO, SUBSTR(CP.NUM_COMP_PAGO,1,3);
  else
    OPEN curCE FOR
             SELECT  trim('01�'||cCodLocal_in||'�'||
                     '0000000'||'�'||'0000000')
             FROM   dual;
  end if;
    RETURN curCE;
  END;

  /* ************************************************************************ */
  FUNCTION CE_OBTIENE_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in	   IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
         SELECT CP.TIP_COMP_PAGO || '�' ||
                SUBSTR(CP.NUM_COMP_PAGO,1,3) || '�' ||
                SUBSTR(MIN(CP.NUM_COMP_PAGO),4) || '�' ||
                SUBSTR(MAX(CP.NUM_COMP_PAGO),4)
         FROM   VTA_COMP_PAGO CP
         WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    CP.COD_LOCAL = cCodLocal_in
         AND    CP.TIP_COMP_PAGO IN (SELECT TIP_COMP.TIP_COMP
                                     FROM   VTA_TIP_COMP TIP_COMP
                                     WHERE  TIP_COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                                     AND    TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI)
         AND    CP.SEC_MOV_CAJA IN (SELECT CMC.SEC_MOV_CAJA_ORIGEN
                                    FROM   CE_MOV_CAJA CMC
                                    WHERE  CMC.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CMC.COD_LOCAL = cCodLocal_in
                                    AND    CMC.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy'))
         GROUP BY CP.TIP_COMP_PAGO,
                  SUBSTR(CP.NUM_COMP_PAGO,1,3)
         ORDER BY CP.TIP_COMP_PAGO, SUBSTR(CP.NUM_COMP_PAGO,1,3);
    RETURN curCE;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ELIMINA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in	IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
                                          cSecMovCaja_in  IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR)
  IS
  BEGIN
       DELETE FROM CE_COMP_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    SEC_MOV_CAJA = cSecMovCaja_in
       AND    NUM_SERIE_LOCAL = cNumSerie_in
       AND    TIP_COMP = cTipComp_in;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_ELIMINA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in	  IN CHAR,
                                          cCierreDia_in   IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR)
  IS
  BEGIN
       DELETE FROM CE_COMP_CIERRE_DIA_VENTA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    NUM_SERIE_LOCAL = cNumSerie_in
       AND    TIP_COMP = cTipComp_in;
  END ;

  /* ************************************************************************ */
  PROCEDURE CE_INSERTA_RANGO_COMP_MOV_CAJ(cCodGrupoCia_in	IN CHAR,
  		   						                      cCodLocal_in	  IN CHAR,
                                          cSecMovCaja_in  IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR,
                                          cRangoIni_in    IN CHAR,
                                          cRangoFin_in    IN CHAR,
                                          cUsuCrea_in     IN CHAR)
  IS
  nCant NUMBER;
  BEGIN

  SELECT COUNT(*) INTO nCant
  FROM CE_COMP_MOV_CAJA X
  WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
  AND X.COD_LOCAL=cCodLocal_in
  AND X.SEC_MOV_CAJA=cSecMovCaja_in;

  IF(nCant=0)THEN
   INSERT INTO CE_COMP_MOV_CAJA(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA, NUM_SERIE_LOCAL,TIP_COMP,
                                NUM_INICIAL, NUM_FINAL, USU_CREA_COMP_MOV_CAJA)
     VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in, cNumSerie_in,cTipComp_in,
                                cRangoIni_in, cRangoFin_in, cUsuCrea_in);
  END IF;
  END;

  /* ************************************************************************ */
  PROCEDURE CE_INSERTA_RANGO_COMP_CIE_DIA(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in	  IN CHAR,
                                          cCierreDia_in   IN CHAR,
                                          cTipComp_in     IN CHAR,
                                          cNumSerie_in    IN CHAR,
                                          cRangoIni_in    IN CHAR,
                                          cRangoFin_in    IN CHAR,
                                          cUsuCrea_in     IN CHAR)
  IS
  BEGIN
       INSERT INTO CE_COMP_CIERRE_DIA_VENTA
                   (COD_GRUPO_CIA, COD_LOCAL, FEC_CIERRE_DIA_VTA, NUM_SERIE_LOCAL,
                    TIP_COMP, NUM_INICIAL, NUM_FINAL, USU_CREA_COMP_CIERRE_DIA)
             VALUES(cCodGrupoCia_in, cCodLocal_in, TO_DATE(cCierreDia_in,'dd/MM/yyyy'), cNumSerie_in,
                    cTipComp_in, cRangoIni_in, cRangoFin_in, cUsuCrea_in);
  END ;

  /*******************************************************************************/
  FUNCTION GET_TIPO_CAJA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vTipoCaja PBL_LOCAL.TIP_CAJA%TYPE;
  BEGIN

    SELECT  DECODE(trim(X.TIP_CAJA),'M','S','N') INTO v_vTipoCaja
    FROM PBL_LOCAL X
    WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
          AND X.COD_LOCAL = cCodLocal_in;

    RETURN v_vTipoCaja;
  END;

 /*
 * JQUISPE 14.04.2010
 * VALIDACION PARA VISA MANUAL DEL CIERRE DIA
 */
 FUNCTION CE_F_VALIDA_VISA_MANUAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in	  IN CHAR,
                                  cCierreDia_in   IN CHAR
                                  )
                                          RETURN CHAR
  IS
        IND CHAR(1):='S';
        monto_cuadratura number:=0.0;
        monto_FPago number:=0.0;

  BEGIN
     SELECT NVL(MAX(A.MONTO),0) INTO monto_cuadratura
     FROM
       (SELECT
               CUADRATURA_CIERRE_DIA.COD_FORMA_PAGO forma_pago,
               CUADRATURA_CIERRE_DIA.COD_CUADRATURA cod_cuadratura,
               NVL(CUADRATURA.DESC_CUADRATURA,' ') desc_cuadratura,
               DECODE(CUADRATURA.COD_CUADRATURA,
               COD_CUADRATURA_DEL_PERDIDO,
               SUM(CUADRATURA_CIERRE_DIA.MON_PERDIDO_TOTAL),
               SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL * DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)))) MONTO
        FROM   CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
               CE_CUADRATURA CUADRATURA,
               CE_CIERRE_DIA_VENTA DV
        WHERE  CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CUADRATURA_CIERRE_DIA.COD_LOCAL = cCodLocal_in
        AND    CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
        AND    CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
        AND    CUADRATURA_CIERRE_DIA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
        AND    DV.COD_GRUPO_CIA = CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA
        AND    DV.COD_LOCAL = CUADRATURA_CIERRE_DIA.COD_LOCAL
        AND    DV.FEC_CIERRE_DIA_VTA = CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA
        AND CUADRATURA_CIERRE_DIA.COD_FORMA_PAGO ='00005'
        AND CUADRATURA_CIERRE_DIA.COD_CUADRATURA='011'
        GROUP BY CUADRATURA_CIERRE_DIA.COD_FORMA_PAGO,
                 CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                 CUADRATURA.DESC_CUADRATURA,
                 CUADRATURA.COD_CUADRATURA
        ) A;


        SELECT NVL(MAX(SUM(F.MON_ENTREGA_TOTAL)),0) INTO monto_FPago
        FROM   CE_FORMA_PAGO_ENTREGA F,
               VTA_FORMA_PAGO P
        WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    F.COD_LOCAL = cCodLocal_in
        AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                  FROM   CE_MOV_CAJA A
                                  WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND    A.COD_LOCAL = cCodLocal_in
                                  AND    A.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                                  AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
        AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
        AND    F.EST_FORMA_PAGO_ENT = 'A'
        AND    F.Cod_Forma_Pago='00005'
        GROUP BY P.DESC_CORTA_FORMA_PAGO,
        --F.TIP_MONEDA,
        F.COD_FORMA_PAGO;

       if monto_cuadratura <> monto_FPago then
           IND:='N';
       else
           IND:='S';
       end if;

       DBMS_OUTPUT.put_line('INDICADOR: '||IND);
       DBMS_OUTPUT.put_line('cuadratura: '||monto_cuadratura);
       DBMS_OUTPUT.put_line('forma pago: '||monto_FPago);

       RETURN IND;

  END ;

PROCEDURE CE_APROBAR_SOBRES(cCodGrupoCia_in  IN CHAR,
   	                        cCodLocal_in     IN CHAR,
	                          cSecMovCaja_in   IN CHAR,
                            cUsuModMovCaj_in IN CHAR,
                            cSecUsuQf in char DEFAULT '000')
  IS
  BEGIN
      --Se aprueban automaticamente los sobres del turno.
      UPDATE CE_SOBRE A
      SET A.ESTADO='A',
      A.FEC_MOD_SOBRE=SYSDATE,
      A.USU_MOD_SOBRE=cUsuModMovCaj_in,
      a.sec_usu_qf = cSecUsuQf
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCodLocal_in
      AND A.SEC_MOV_CAJA=cSecMovCaja_in --todos los sobres creados en cierre de turno
      AND A.ESTADO IN ('P'); --ASOSA, 14.06.2010

      UPDATE CE_SOBRE_TMP B
      SET B.ESTADO='A',
      B.FEC_MOD_SOBRE=SYSDATE,
      B.USU_MOD_SOBRE=cUsuModMovCaj_in,
      b.sec_usu_qf = cSecUsuQf
      WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
      AND B.COD_LOCAL=cCodLocal_in
      AND B.ESTADO IN ('P') --ASOSA, 14.06.2010
      AND B.SEC_MOV_CAJA= (SELECT C.SEC_MOV_CAJA_ORIGEN
                   FROM CE_MOV_CAJA C
                   WHERE  C.COD_GRUPO_CIA=B.COD_GRUPO_CIA
                   AND C.COD_LOCAL=B.COD_LOCAL
                   AND C.SEC_MOV_CAJA=cSecMovCaja_in); --todos los sobres creados en el ingreso parcial de cobro

  END;

end PTOVENTA_CE_LMR;

/
