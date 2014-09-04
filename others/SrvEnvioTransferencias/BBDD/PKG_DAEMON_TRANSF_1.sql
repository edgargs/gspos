--------------------------------------------------------
--  DDL for Package Body PKG_DAEMON_TRANSF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PKG_DAEMON_TRANSF" AS

  FUNCTION GET_LOCALES_ENVIO(cCodGrupoCia_in     IN CHAR,
                                        cCodCia_in  IN CHAR)
  RETURN FarmaCursor
  IS
	curLocales FarmaCursor;
  BEGIN
	OPEN curLocales FOR
	SELECT DISTINCT TRA.COD_GRUPO_CIA,TRA.COD_CIA,TRA.COD_LOCAL_DEST "COD_LOCAL", LOC.IP_SERVIDOR_LOCAL
	FROM REL_TRANSFERENCIAS TRA JOIN PBL_LOCAL LOC ON (TRA.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
														AND TRA.COD_LOCAL_DEST = LOC.COD_LOCAL)
	WHERE TRA.COD_GRUPO_CIA = cCodGrupoCia_in
	  AND TRA.COD_CIA = cCodCia_in
	  AND TRA.FEC_ENV_LOCAL IS NULL
	  ;
	
	RETURN curLocales;
  END;
  
  FUNCTION GET_NOTAS_PENDIENTES(cCodGrupoCia_in     IN CHAR,
                                        cCodCia_in  IN CHAR,
										cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
	curNotas FarmaCursor;
  BEGIN
	OPEN curNotas FOR
	SELECT COD_GRUPO_CIA,COD_CIA,COD_LOCAL,NUM_NOTA_ES,
		NVL((SELECT 
          CASE
            WHEN A.CIA in ('10','11') AND B.FCH_MIGRACION IS NULL THEN 'BV'
            ELSE
              'FV' END
          FROM PTOVENTA.MAE_LOCAL A, PTOVENTA.AUX_MAE_LOCAL B
          WHERE A.COD_LOCAL = B.COD_LOCAL
          AND B.COD_LOCAL_SAP = T.COD_LOCAL_DEST),'FV') NOM_SIST,
		  T.COD_LOCAL_DEST
	FROM REL_TRANSFERENCIAS T
	WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
	  AND T.COD_CIA = cCodCia_in
	  AND T.COD_LOCAL_DEST = cCodLocal_in
	  AND T.FEC_ENV_LOCAL IS NULL
	;
	RETURN curNotas;
  END;

  PROCEDURE UPD_ENVIO_DESTINO(cCodGrupoCia_in     IN CHAR,
                                        cCodCia_in  IN CHAR,
										cCodLocal_in IN CHAR,
										cNumNotaEs_in IN CHAR)
  IS
  BEGIN
	UPDATE REL_TRANSFERENCIAS
		SET FEC_ENV_LOCAL = SYSDATE,
		EST_NOTA_ES_CAB = 'L'
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	  AND COD_CIA = cCodCia_in
	  AND COD_LOCAL = cCodLocal_in
	  AND NUM_NOTA_ES = cNumNotaEs_in;
	  
	  PTOVENTA.PTOVENTA_TRANSF.ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in, cCodLocal_in,cNumNotaEs_in,'L');
  END;

  PROCEDURE GRABA_REG_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,
									cCodCia_in  IN CHAR,
									cCodLocal_in IN CHAR,
									cNumNotaEs_in IN CHAR)
  IS
  BEGIN
	IF cNumNotaEs_in IS NOT NULL THEN
		INSERT INTO REL_TRANSFERENCIAS(COD_GRUPO_CIA,COD_CIA,COD_LOCAL,NUM_NOTA_ES,COD_LOCAL_DEST,FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB)
		SELECT CAB.COD_GRUPO_CIA, LOC.COD_CIA, CAB.COD_LOCAL,CAB.NUM_NOTA_ES,CAB.COD_DESTINO_NOTA_ES,CAB.FEC_NOTA_ES_CAB,CAB.EST_NOTA_ES_CAB
		FROM T_LGT_NOTA_ES_CAB CAB JOIN PBL_LOCAL LOC ON (CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
															AND CAB.COD_LOCAL = LOC.COD_LOCAL)
		WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
			AND CAB.COD_LOCAL = cCodLocal_in
			AND CAB.NUM_NOTA_ES = cNumNotaEs_in;
	ELSE
		INSERT INTO REL_TRANSFERENCIAS(COD_GRUPO_CIA,COD_CIA,COD_LOCAL,NUM_NOTA_ES,COD_LOCAL_DEST,FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB)
		SELECT CAB.COD_GRUPO_CIA, LOC.COD_CIA, CAB.COD_LOCAL,CAB.NUM_NOTA_ES,CAB.COD_DESTINO_NOTA_ES,CAB.FEC_NOTA_ES_CAB,CAB.EST_NOTA_ES_CAB
		FROM T_LGT_NOTA_ES_CAB CAB JOIN PBL_LOCAL LOC ON (CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
															AND CAB.COD_LOCAL = LOC.COD_LOCAL)
		WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
			AND CAB.COD_LOCAL = cCodLocal_in
			AND CAB.EST_NOTA_ES_CAB = 'M'
			AND NOT EXISTS (SELECT 1 FROM REL_TRANSFERENCIAS TRA 
								WHERE TRA.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
									AND TRA.COD_CIA = LOC.COD_CIA
									AND TRA.COD_LOCAL = CAB.COD_LOCAL
									AND TRA.NUM_NOTA_ES = CAB.NUM_NOTA_ES);
	END IF	;
  END;  
  
END PKG_DAEMON_TRANSF;

/