--------------------------------------------------------
--  DDL for Package Body PTOVENTA_MATRIZ_CONV_BTLMF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_MATRIZ_CONV_BTLMF" is

    FUNCTION BTLMF_F_CUR_OBT_BENIFICIARIO(CCODGRUPOCIA_IN CHAR,
                                          CCODLOCAL_IN    CHAR,
                                          CSECUSULOCAL_IN CHAR,
                                          VCODCONVENIO_IN CHAR,
                                          VCODBENIF_IN    CHAR

                                          ) RETURN FARMACURSOR IS
      CURCONV        FARMACURSOR;
      FLGDATARIMAC   CHAR(1);
      FLGBENEFONLINE CHAR(1);

    BEGIN

      SELECT C.FLG_DATA_RIMAC, C.FLG_BENEF_ONLINE
        INTO FLGDATARIMAC, FLGBENEFONLINE
        FROM MAE_CONVENIO C
       WHERE C.COD_CONVENIO = VCODCONVENIO_IN;

      IF FLGDATARIMAC = '1' THEN
        --EN CONSTRUCCION. LISTA DE BENIFICIARIOS DE RIMAC.
        OPEN CURCONV FOR
          SELECT BENIF.NOMBRES "DES_NOMBRE"
            FROM PTOVENTA.V_CON_BENEFIC_RIMAC BENIF
           WHERE BENIF.COD_CONVENIO = VCODCONVENIO_IN
             AND BENIF.COD_REFERENCIA = VCODBENIF_IN;
      ELSE
        OPEN CURCONV FOR
          SELECT (
				   NVL(A.NUM_DOCUMENTO, '        ')               || 'Ã' ||
                   NVL(A.DES_CLIENTE,' ')                            || ' ' ||
                   ''                                                || 'Ã' ||
                   TO_CHAR(NVL(A.IMP_LINEA_CREDITO_ORI, '0'),'999,9999.90') || 'Ã' || -- KMONCADA 01.10.2014 FORMATO DE NUMERO DECIMAL
                   NVL(DECODE(A.FLG_ACTIVO, '1', 'A', 'N'), 'A')     || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   NVL(A.COD_CLIENTE,' ')
				   ) RESULTADO
              FROM CMR.MAE_BENEFICIARIO A
			 WHERE A.CIA = '10'
       --and   A.FLG_ACTIVO = 1
       AND   A.COD_CONVENIO  =  VCODCONVENIO_IN
       AND   A.COD_CLIENTE = VCODBENIF_IN;
      END IF;

      RETURN CURCONV;
    END;

	FUNCTION GET_CANT_LISTA_BENEFICIARIO(CCODGRUPOCIA_IN  CHAR,
                                            CCODLOCAL_IN     CHAR,
                                            CSECUSULOCAL_IN  CHAR,
                                            VCODCONVENIO_IN  CHAR,
                                            VBENIFICIARIO_IN VARCHAR2)
	RETURN INTEGER IS
		nCantRemoto INTEGER;
		FLGDATARIMAC   CHAR(1);
		FLGBENEFONLINE CHAR(1);
	BEGIN
		SELECT C.FLG_DATA_RIMAC, C.FLG_BENEF_ONLINE
        INTO FLGDATARIMAC, FLGBENEFONLINE
        FROM MAE_CONVENIO C
       WHERE C.COD_CONVENIO = VCODCONVENIO_IN;

	   IF FLGDATARIMAC = '1' THEN

			/*SELECT COUNT(1) INTO nCantRemoto
				  FROM PTOVENTA.V_CON_BENEFIC_RIMAC BENIF
				 WHERE BENIF.COD_CONVENIO = VCODCONVENIO_IN
				   AND BENIF.NOMBRES LIKE '%' || UPPER(TRIM(VBENIFICIARIO_IN)) || '%';			 */
			SELECT COUNT(1) INTO nCantRemoto
			FROM NEWCNV.MAE_ASEGURADO_RIMAC   A,
					NEWCNV.MAE_POLIZA_RIMAC      B,
					NEWCNV.MAE_CONTRATANTE_RIMAC C,
					NEWCNV.REL_CONTRANTE_CONVENIO D
			  WHERE C.CODCONT = B.CODCONT
				 AND B.POLIZA = A.POLIZA
				 AND C.CODCONT = D.CODCONT
				 and a.flg_visualizacion='1'
				 AND B.POLIZA = NVL(D.POLIZA, B.POLIZA)
				 AND  EXISTS (select 1
									   from   cmr.mae_convenio c
									   where  c.flg_activo = 1
									   and    c.flg_data_rimac = 1
									   AND c.cod_convenio = D.COD_CONVENIO )
				 AND D.COD_CONVENIO = VCODCONVENIO_IN
				 AND A.NOMBRES LIKE '%' || UPPER(TRIM(VBENIFICIARIO_IN)) || '%';
		ELSE

			/*SELECT COUNT(1) INTO nCantRemoto
				  FROM PTOVENTA.V_CON_BENEFICIARIO BENIF
				 WHERE BENIF.COD_CONVENIO = VCODCONVENIO_IN
				   AND BENIF.DES_CLIENTE LIKE '%' || UPPER(TRIM(VBENIFICIARIO_IN)) || '%';*/
			SELECT COUNT(1) INTO nCantRemoto
			FROM CMR.MAE_BENEFICIARIO A,
                       CMR.MAE_CLIENTE      B,
                       CMR.MAE_DOC_IDENT    C
                 WHERE A.COD_CLIENTE = B.COD_CLIENTE
                   AND A.FLG_ACTIVO = NVL('1',A.FLG_ACTIVO)
                   AND B.COD_DOCUMENTO_IDENTIDAD =
                       C.COD_DOCUMENTO_IDENTIDAD(+)
                   AND   A.COD_CONVENIO  =  VCODCONVENIO_IN
                   AND B.DES_CLIENTE LIKE '%' || UPPER(TRIM(VBENIFICIARIO_IN)) || '%'
                   ;
		END IF;

		RETURN nCantRemoto;
	END;

    FUNCTION BTLMF_F_CUR_LISTA_BENIFICIARIO(CCODGRUPOCIA_IN  CHAR,
                                            CCODLOCAL_IN     CHAR,
                                            CSECUSULOCAL_IN  CHAR,
                                            VCODCONVENIO_IN  CHAR,
                                            VBENIFICIARIO_IN VARCHAR2

                                            ) RETURN FARMACURSOR IS
      PRAGMA AUTONOMOUS_TRANSACTION;
	  CURCONV        FARMACURSOR;
      REG            VARCHAR2(32000);
      FLGDATARIMAC   CHAR(1);

      FLGFOUND       CHAR(1) DEFAULT 'N';
	  nCantLocal INTEGER;
	  nCantRemoto INTEGER;
    BEGIN

      SELECT C.FLG_DATA_RIMAC
        INTO FLGDATARIMAC
        FROM CMR.MAE_CONVENIO C
       WHERE C.COD_CONVENIO = VCODCONVENIO_IN;

        IF FLGDATARIMAC = '1' THEN
          /* -- Si el convenio tiene el flag online activo busca en matriz -- */
          OPEN CURCONV FOR
            SELECT (
				   '        '           || 'Ã' ||
                   NVL(A.NOMBRES,' ')        || ' ' ||
                   ' '                  || 'Ã' ||
                   '0'                  || 'Ã' ||
                   'A'                  || 'Ã' ||
                   NVL(A.NOMBRES,' ')        || 'Ã' ||
                   NVL(A.POLIZA,' ')         || 'Ã' ||
                   NVL(A.PLAN,' ')           || 'Ã' ||
                   NVL(A.CODASG,' ')         || 'Ã' ||
                   NVL(A.NUMITM,' ')         || 'Ã' ||
                   NVL(A.PRT,' ')            || 'Ã' ||
                   rpad(NVL(C.CODCONT,' '),8,' ') || '-' || RPAD(C.NOMCONT,50,' ') || 'Ã' ||
                   NVL(A.TIPO_SEGURO,' ')            || 'Ã' ||
                   rpad(NVL(A.CODASG,' '),5,' ')||'-'||rpad(A.poLIZA,6,' ')||'-'||rpad(A.PLAN,5,' ')||'-'||RPAD(A.NUMITM,2,' ')
				   ) RESULTADO
              FROM NEWCNV.MAE_ASEGURADO_RIMAC   A,
					NEWCNV.MAE_POLIZA_RIMAC      B,
					NEWCNV.MAE_CONTRATANTE_RIMAC C,
					NEWCNV.REL_CONTRANTE_CONVENIO D
			  WHERE C.CODCONT = B.CODCONT
				 AND B.POLIZA = A.POLIZA
				 AND C.CODCONT = D.CODCONT
				 and a.flg_visualizacion='1'
				 AND B.POLIZA = NVL(D.POLIZA, B.POLIZA)
				 AND  EXISTS (select 1
									   from   cmr.mae_convenio c
									   where  c.flg_activo = 1
									   and    c.flg_data_rimac = 1
									   AND c.cod_convenio = D.COD_CONVENIO )
				 AND D.COD_CONVENIO = VCODCONVENIO_IN
				 AND A.NOMBRES LIKE '%' || UPPER(TRIM(VBENIFICIARIO_IN)) || '%';
        ELSE
          /* -- Si el convenio tiene el flag online activo busca en matriz -- */
          OPEN CURCONV FOR
            SELECT (
				   NVL(A.NUM_DOCUMENTO, '        ')               || 'Ã' ||
                   NVL(A.DES_CLIENTE,' ')                            || ' ' ||
                   ''                                                || 'Ã' ||
                   TO_CHAR(NVL(A.IMP_LINEA_CREDITO_ORI, '0'),'999,9999.90') || 'Ã' || -- KMONCADA 01.10.2014 FORMATO DE NUMERO DECIMAL
                   NVL(DECODE(A.FLG_ACTIVO, '1', 'A', 'N'), 'A')     || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   ' '                                               || 'Ã' ||
                   NVL(A.COD_CLIENTE,' ')
				   ) RESULTADO
              FROM CMR.MAE_BENEFICIARIO A
			 WHERE A.CIA = '10'
       and   A.FLG_ACTIVO = 1
       AND   A.COD_CONVENIO  =  VCODCONVENIO_IN
       AND   A.DES_CLIENTE LIKE UPPER(TRIM(VBENIFICIARIO_IN)) || '%';
        END IF;

		ROLLBACK;
      RETURN CURCONV;

    END;

end PTOVENTA_MATRIZ_CONV_BTLMF;



/
