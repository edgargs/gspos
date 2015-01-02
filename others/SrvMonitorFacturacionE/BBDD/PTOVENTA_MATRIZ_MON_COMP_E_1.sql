--------------------------------------------------------
--  DDL for Package Body PTOVENTA_MATRIZ_MON_COMP_E
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_MATRIZ_MON_COMP_E" AS

  PROCEDURE INSERTA_MON_COMP_E(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR) AS
  BEGIN
    INSERT INTO MON_VTA_COMP_PAGO_E(COD_GRUPO_CIA,
      COD_CIA,
      COD_LOCAL,
      NUM_PED_VTA,
      SEC_COMP_PAGO,
      TIP_DOC_SUNAT,
      NUM_COMP_PAGO_E,
      ESTADO,
      USU_CREA,
      IND_VALIDA)
      SELECT C.COD_GRUPO_CIA,
      (SELECT COD_CIA FROM PBL_LOCAL L WHERE L.COD_GRUPO_CIA = C.COD_GRUPO_CIA AND L.COD_LOCAL = C.COD_LOCAL) COD_CIA,
      C.COD_LOCAL,C.NUM_PED_VTA,C.SEC_COMP_PAGO,
      DECODE(C.TIP_COMP_PAGO,'01','03','02','01','04','07','99') TIP_COMP_E,
      SUBSTR(C.NUM_COMP_PAGO_E,1,4)||'-'||SUBSTR(C.NUM_COMP_PAGO_E,5) NUM_COMP_E,
      'R','JOB_CARGA_COM_E',      
	  --ERIOS 19.12.2014 No se valida Boletas ni Notas de Credito de Boletas.
	  CASE WHEN C.TIP_COMP_PAGO = '01' THEN 'N'
           WHEN C.TIP_COMP_PAGO = '04' AND SUBSTR(C.NUM_COMP_PAGO_E,1,1) = 'B' THEN 'N'
      ELSE 'S' END
      FROM VTA_COMP_PAGO C
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND C.COD_LOCAL = cCodLocal_in
      --AND C.TIP_COMP_PAGO = '01'
      AND C.COD_TIP_PROC_PAGO = '1'
      AND C.NUM_COMP_PAGO_E IS NOT NULL
      AND C.FEC_CREA_COMP_PAGO > TRUNC(SYSDATE-7)
      AND NOT EXISTS (SELECT 1 FROM MON_VTA_COMP_PAGO_E M
                      WHERE M.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                        --AND M.COD_CIA = C.COD_CIA
                        AND M.COD_LOCAL = C.COD_LOCAL
                        AND M.NUM_PED_VTA = C.NUM_PED_VTA
                        AND M.SEC_COMP_PAGO = C.SEC_COMP_PAGO
                        );
    COMMIT;
  END INSERTA_MON_COMP_E;
  
  PROCEDURE EJECUTA_MON_COMP_E(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR)
  IS
  
    CURSOR locales IS
	SELECT COD_LOCAL
	FROM PBL_LOCAL
	WHERE 1=1
	AND COD_GRUPO_CIA = cCodGrupoCia_in
	AND COD_CIA = cCodCia_in
	--AND IND_ELECTRONICO = 'S'
	AND COD_LOCAL IN ('506','001','605','414')
	;
	verifica INTEGER := 0;
  BEGIN
	
	FOR loc in locales
	LOOP
	  
	  SELECT count(1)
					into verifica
	FROM VTA_COMP_PAGO C
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND C.COD_LOCAL = loc.COD_LOCAL
      --AND C.TIP_COMP_PAGO = '01'
      AND C.COD_TIP_PROC_PAGO = '1'
      AND C.NUM_COMP_PAGO_E IS NOT NULL
      AND C.FEC_CREA_COMP_PAGO > TRUNC(SYSDATE-7)
      AND NOT EXISTS (SELECT 1 FROM MON_VTA_COMP_PAGO_E M
                      WHERE M.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                        --AND M.COD_CIA = C.COD_CIA
                        AND M.COD_LOCAL = C.COD_LOCAL
                        AND M.NUM_PED_VTA = C.NUM_PED_VTA
                        AND M.SEC_COMP_PAGO = C.SEC_COMP_PAGO
                        )
	  AND ROWNUM = 1
	  ;
	  IF verifica > 0 THEN				
        PTOVENTA_MATRIZ_MON_COMP_E.INSERTA_MON_COMP_E(cCodGrupoCia_in, cCodCia_in, loc.COD_LOCAL);
	  END IF;
	END LOOP;
  END;
  
  PROCEDURE REPROCESO_COMP_E(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, cSecCompPago_in IN CHAR)
  IS
  BEGIN
	update
	MON_VTA_COMP_PAGO_E
	SET
	ESTADO = 'R',
	CODIGO = NULL,
	MENSAJE = NULL,
	USU_CREA = 'REP_COMP_E',
	FEC_CREA = SYSDATE
	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	AND COD_CIA = cCodCia_in
	AND COD_LOCAL = cCodLocal_in
	AND NUM_PED_VTA = cNumPedVta_in
	AND SEC_COMP_PAGO = cSecCompPago_in;
	COMMIT;
  END;

END PTOVENTA_MATRIZ_MON_COMP_E;

/
