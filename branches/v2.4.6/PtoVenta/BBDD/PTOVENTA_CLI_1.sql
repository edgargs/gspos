--------------------------------------------------------
--  DDL for Package Body PTOVENTA_CLI
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_CLI" AS

  FUNCTION CLI_BUSCA_CLI_JURIDICO(cCodGrupoCia_in  IN CHAR,
  		   				  		  cCodLocal_in	   IN CHAR,
								  cRuc_RazSoc_in   IN CHAR,
								  cTipoBusqueda_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
  	DBMS_OUTPUT.PUT_LINE('cTipoBusqueda_in : ' || cTipoBusqueda_in);
  	IF(cTipoBusqueda_in = '1') THEN --RUC
	    OPEN curCli FOR
			SELECT CLI_LOCAL.COD_CLI_LOCAL 	 || 'Ã' ||
				   CLI_LOCAL.NUM_DOC_IDEN 	 || 'Ã' ||
				   CLI_LOCAL.NOM_CLI 		 || 'Ã' ||
				   NVL(CLI_LOCAL.DIR_CLI_LOCAL,' ')
			FROM   VTA_CLI_LOCAL CLI_LOCAL
			WHERE  CLI_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			AND	   CLI_LOCAL.COD_LOCAL = cCodLocal_in
			AND	   CLI_LOCAL.TIP_DOC_IDENT = C_C_TIP_DOC_JURIDICO --CLIENTE JURIDICO
			AND	   CLI_LOCAL.NUM_DOC_IDEN = cRuc_RazSoc_in
			AND	   CLI_LOCAL.EST_CLI_LOCAL = C_C_ESTADO_ACTIVO;
	ELSIF(cTipoBusqueda_in = '2') THEN --RAZON SOCIAL
		OPEN curCli FOR
			SELECT CLI_LOCAL.COD_CLI_LOCAL || 'Ã' ||
				   CLI_LOCAL.NUM_DOC_IDEN  || 'Ã' ||
				   CLI_LOCAL.NOM_CLI 	   || 'Ã' ||
				   NVL(CLI_LOCAL.DIR_CLI_LOCAL,' ')
			FROM   VTA_CLI_LOCAL CLI_LOCAL
			WHERE  CLI_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			AND	   CLI_LOCAL.COD_LOCAL = cCodLocal_in
			AND	   CLI_LOCAL.TIP_DOC_IDENT = C_C_TIP_DOC_JURIDICO --CLIENTE JURIDICO
			AND	   CLI_LOCAL.NOM_CLI LIKE cRuc_RazSoc_in || '%'
			AND	   CLI_LOCAL.EST_CLI_LOCAL = C_C_ESTADO_ACTIVO;
	ELSIF(cTipoBusqueda_in= '3' )THEN --DNI
		OPEN curCli FOR
			SELECT CLI_LOCAL.COD_CLI_LOCAL || 'Ã' ||
				   CLI_LOCAL.NUM_DOC_IDEN  || 'Ã' ||
				   CLI_LOCAL.NOM_CLI ||' '|| CLI_LOCAL.APE_PAT_CLI ||' '|| CLI_LOCAL.APE_MAT_CLI || 'Ã' ||
				   NVL(CLI_LOCAL.DIR_CLI_LOCAL,' ')
			FROM   VTA_CLI_LOCAL CLI_LOCAL
			WHERE  CLI_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			AND	   CLI_LOCAL.COD_LOCAL = cCodLocal_in
			AND	   CLI_LOCAL.TIP_DOC_IDENT = C_C_TIP_DOC_NATURAL --CLIENTE NATURAL
			AND	   CLI_LOCAL.NUM_DOC_IDEN = cRuc_RazSoc_in
			AND	   CLI_LOCAL.EST_CLI_LOCAL = C_C_ESTADO_ACTIVO;
	ELSIF(cTipoBusqueda_in = '4') THEN --APELLIDO
		OPEN curCli FOR
			SELECT CLI_LOCAL.COD_CLI_LOCAL || 'Ã' ||
				   CLI_LOCAL.NUM_DOC_IDEN  || 'Ã' ||
				   CLI_LOCAL.NOM_CLI ||' '|| CLI_LOCAL.APE_PAT_CLI ||' '|| CLI_LOCAL.APE_MAT_CLI || 'Ã' ||
				   NVL(CLI_LOCAL.DIR_CLI_LOCAL,' ')
			FROM   VTA_CLI_LOCAL CLI_LOCAL
			WHERE  CLI_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			AND	   CLI_LOCAL.COD_LOCAL = cCodLocal_in
			AND	   CLI_LOCAL.TIP_DOC_IDENT = C_C_TIP_DOC_NATURAL --CLIENTE NATURAL
			AND	   CLI_LOCAL.APE_PAT_CLI LIKE cRuc_RazSoc_in || '%'
			AND	   CLI_LOCAL.EST_CLI_LOCAL = C_C_ESTADO_ACTIVO;

	END IF;
  RETURN curCli;
  END;

  /* ************************************************************************* */

  FUNCTION CLI_AGREGA_CLI_LOCAL(cCodGrupoCia_in	    IN CHAR,
  		   						cCodLocal_in	  	IN CHAR,
								cCodNumera_in	  	IN CHAR,
								cRazSoc_in 	     	IN CHAR,
 		   						cTipDocIdent_in	 	IN CHAR,
								cNumDocIdent_in  	IN CHAR,
								cDirCliLocal_in  	IN CHAR,
								cUsuCreaCliLocal_in IN CHAR)
  RETURN CHAR
  IS PRAGMA AUTONOMOUS_TRANSACTION;
  	v_nCodCliLocal	NUMBER;
	v_cCodCliLocal	CHAR(7);
	v_cResultado	CHAR(1);
  	BEGIN
	   v_nCodCliLocal := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in);
	   v_cCodCliLocal := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_nCodCliLocal), 7, '0', 'I');
  	   INSERT INTO VTA_CLI_LOCAL(COD_GRUPO_CIA, COD_LOCAL, COD_CLI_LOCAL, NOM_CLI,
	   		  	   				 TIP_DOC_IDENT, NUM_DOC_IDEN, DIR_CLI_LOCAL, USU_CREA_CLI_LOCAL)
						VALUES  (cCodGrupoCia_in, cCodLocal_in, v_cCodCliLocal, cRazSoc_in,
							 	 cTipDocIdent_in, cNumDocIdent_in, cDirCliLocal_in, cUsuCreaCliLocal_in);
	   Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in,cUsuCreaCliLocal_in);
	   COMMIT;
	   --v_cResultado := '1';--grabo corectamente
	   RETURN v_cCodCliLocal;
  	   /*EXCEPTION
  		   WHEN OTHERS THEN
		   		ROLLBACK;
				v_cResultado := '2';--hubo error
  				RETURN v_cResultado;*/
  END;

  /* ************************************************************************* */
  FUNCTION CLI_AGREGA_CLI_NATURAL(cCodGrupoCia_in	    IN CHAR,
  		   							cCodLocal_in	  	IN CHAR,
									cCodNumera_in	  	IN CHAR,
									cNombre_in 	     	IN CHAR,
									cApellido_Pat_in 	IN CHAR,
									cApellido_Mat_in   	IN CHAR,
 		   							cTipDocIdent_in	 	IN CHAR,
									cNumDocIdent_in  	IN CHAR,
									cDirCliLocal_in  	IN CHAR,
									cUsuCreaCliLocal_in IN CHAR)
	RETURN CHAR
	IS PRAGMA AUTONOMOUS_TRANSACTION;
	  	v_nCodCliLocal	NUMBER;
		v_cCodCliLocal	CHAR(7);
		v_cResultado	CHAR(1);
		BEGIN
		   v_nCodCliLocal := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in);
		   v_cCodCliLocal := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_nCodCliLocal), 7, '0', 'I');
	  	   INSERT INTO VTA_CLI_LOCAL(COD_GRUPO_CIA, COD_LOCAL, COD_CLI_LOCAL, NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,
		   		  	   				 TIP_DOC_IDENT, NUM_DOC_IDEN, DIR_CLI_LOCAL,IND_CLI_JUR, USU_CREA_CLI_LOCAL)
							VALUES  (cCodGrupoCia_in, cCodLocal_in, v_cCodCliLocal, cNombre_in,cApellido_Pat_in,
								 	 cApellido_Mat_in,cTipDocIdent_in, cNumDocIdent_in, cDirCliLocal_in,C_C_INDICADOR_NO, cUsuCreaCliLocal_in);
		   Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in,cUsuCreaCliLocal_in);
		   COMMIT;
	   	   v_cResultado := '1';--grabo corectamente
	   	   RETURN v_cResultado;
  	   	   EXCEPTION
	  		   WHEN OTHERS THEN
			  		ROLLBACK;
					v_cResultado := '2';--hubo error
	  				RETURN v_cResultado;
  END;

  /* ************************************************************************* */

  FUNCTION CLI_ACTUALIZA_CLI_LOCAL(cCodGrupoCia_in	    IN CHAR,
  		   						   cCodLocal_in	  		IN CHAR,
								   cCodCliLocal_in	  	IN CHAR,
								   cRazSoc_in 	     	IN CHAR,
								   cNumDocIdent_in  	IN CHAR,
								   cDirCliLocal_in  	IN CHAR,
								   cUsuModCliLocal_in 	IN CHAR)
  RETURN CHAR
  IS PRAGMA AUTONOMOUS_TRANSACTION;
	v_cResultado	CHAR(1);
  	BEGIN

	   UPDATE VTA_CLI_LOCAL
	   SET    NOM_CLI = cRazSoc_in,
			  NUM_DOC_IDEN = cNumDocIdent_in,
			  DIR_CLI_LOCAL = cDirCliLocal_in,
			  FEC_MOD_CLI_LOCAL = SYSDATE,
			  USU_MOD_CLI_LOCAL = cUsuModCliLocal_in
	   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
	   AND	  COD_LOCAL = cCodLocal_in
	   AND	  COD_CLI_LOCAL = cCodCliLocal_in;
	   COMMIT;
	   --v_cResultado := '1';--grabo corectamente
	   RETURN cCodCliLocal_in;
  	   EXCEPTION
  		   WHEN OTHERS THEN
		   		ROLLBACK;
				v_cResultado := '2';--hubo error
		   		RETURN v_cResultado;
  END;

  /* ************************************************************************* */

  FUNCTION CLI_OBTIENE_INFO_CLI_NATURAL (cCodGrupoCia_in IN CHAR,
  		   						   		  cCodLocal_in	 IN CHAR,
  		   								  cCod_Cli_Juri  IN NUMBER)
	RETURN FarmaCursor
	IS
		curProd FarmaCursor ;
	BEGIN
	OPEN curProd FOR
		 SELECT COD_CLI_LOCAL || 'Ã' ||
		 		NUM_DOC_IDEN  || 'Ã' ||
			  	NOM_CLI 	  || 'Ã' ||
				NVL(APE_PAT_CLI,' ')   || 'Ã' ||
				NVL(APE_MAT_CLI,' ')   || 'Ã' ||
			   	NVL(DIR_CLI_LOCAL,' ')
		FROM   VTA_CLI_LOCAL
		WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
			  COD_LOCAL = cCodLocal_in 		  AND
			  COD_CLI_LOCAL = cCod_Cli_Juri;
    RETURN curProd;
  END;

  /* ************************************************************************* */
    FUNCTION CLI_ACTUALIZA_CLI_NATURAL(cCodGrupoCia_in	    	IN CHAR,
  		   						   		   cCodLocal_in	  		IN CHAR,
								           cCodCliLocal_in	  	IN CHAR,
								           cNomCliNatural_in   	IN CHAR,
										   cApePatCliNatural_in	IN CHAR,
								   		   cAPeMatCliNatural_in	IN CHAR,
										   cNumDocIdent_in  	IN CHAR,
								   		   cDirCliLocal_in  	IN CHAR,
										   cUsuModCliLocal_in 	IN CHAR)
  	RETURN CHAR
  	IS PRAGMA AUTONOMOUS_TRANSACTION;
	v_cResultado	CHAR(1);
  	BEGIN

	   UPDATE VTA_CLI_LOCAL
	   SET    NOM_CLI = cNomCliNatural_in,
			  APE_PAT_CLI = cApePatCliNatural_in,
			  APE_MAT_CLI = cAPeMatCliNatural_in,
			  NUM_DOC_IDEN = cNumDocIdent_in,
			  DIR_CLI_LOCAL = cDirCliLocal_in,
			  FEC_MOD_CLI_LOCAL = SYSDATE,
			  USU_MOD_CLI_LOCAL = cUsuModCliLocal_in
	   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
	   AND	  COD_LOCAL = cCodLocal_in
	   AND	  COD_CLI_LOCAL = cCodCliLocal_in;
	   COMMIT;
	   v_cResultado := '1';--grabo corectamente
	   RETURN v_cResultado;
  	   EXCEPTION
  		   WHEN OTHERS THEN
		   		ROLLBACK;
				v_cResultado := '2';--hubo error
		   		RETURN v_cResultado;
  END;

    FUNCTION CLI_OBTIENE_TARJETAS_CLIENTE (cCodGrupoCia_in IN CHAR,
  		   						   		   cCodLocal_in	   IN CHAR,
			 							   cCod_Cliente    IN NUMBER)
	RETURN FarmaCursor
	IS
    curCli FarmaCursor;

  	BEGIN

	  OPEN curCli FOR
	  SELECT T.DESC_OPE_TARJ || 'Ã' ||
	  		 TC.NUM_TARJ || 'Ã' ||
			 TC.NOM_TARJ || 'Ã' ||
			 NVL(TO_CHAR(TC.FEC_VENC_TARJ,'DD/MM/YYYY'),' ') || 'Ã' ||
			 T.COD_OPE_TARJ || 'Ã' ||
		 	 TO_CHAR(TC.fec_venc_tarj,'MM')|| 'Ã' ||
			 TO_CHAR(TC.fec_venc_tarj,'YYYY')
	  FROM   PBL_OPE_TARJ T,
	  	     CE_TARJ_CLI TC
	  WHERE  T.COD_OPE_TARJ=TC.COD_OPE_TARJ
	  AND    tc.COD_GRUPO_CIA = cCodGrupoCia_in
	  AND    tc.COD_LOCAL = cCodLocal_in
	  AND    TC.COD_CLI_LOCAL=cCod_Cliente;
	 RETURN curCli;
	END;

	FUNCTION CLI_AGREGA_TARJETAS_CLI(cCodGrupoCia_in	 IN CHAR,
  		   							 cCodLocal_in	  	 IN CHAR,
									 cCodCli_in		  	 IN CHAR,
									 cCodOper_in     	 IN CHAR,
									 cFechaVen_in	 	 IN CHAR,
									 cNumeroTarj_in   	 IN CHAR,
 		   							 cNombreTarj_in	 	 IN CHAR,
									 cUsuCreaCliLocal_in IN CHAR)

	RETURN CHAR
  	IS PRAGMA AUTONOMOUS_TRANSACTION;
  	v_numSec	NUMBER;
	v_cResultado	CHAR(1);

	BEGIN
	SELECT (COUNT(num_sec_tarj_cli)+1) INTO v_numSec FROM CE_TARJ_CLI WHERE cod_cli_local=cCodCli_in;

		   INSERT INTO CE_TARJ_CLI (COD_GRUPO_CIA,COD_LOCAL,COD_CLI_LOCAL,NUM_SEC_TARJ_CLI,COD_OPE_TARJ,
		   		  	   			    FEC_VENC_TARJ,NUM_TARJ,NOM_TARJ,EST_TARJ_CLI,FEC_CREA_TARJ_CLI,USU_CREA_TARJ_CLI)
		   					VALUES (cCodGrupoCia_in,cCodLocal_in,cCodCli_in,v_numSec,cCodOper_in,cFechaVen_in,
								    cNumeroTarj_in,cNombreTarj_in,C_C_ESTADO_ACTIVO,SYSDATE,cUsuCreaCliLocal_in);
	COMMIT;
	   v_cResultado := '1';--grabo corectamente
	   RETURN v_cResultado;
  	   EXCEPTION
  		   WHEN OTHERS THEN
		   		ROLLBACK;
				v_cResultado := '2';--hubo error
  				RETURN v_cResultado;
  END;


  FUNCTION CLI_ACTUALIZA_TARJETA(cCodCli_in		  	IN CHAR,
	  		   					 cCodOperadorN_in	IN CHAR,
								 cCodOperadorA_in	IN CHAR,
								 cFecVencimiento_in IN CHAR,
								 cNumTarjeta_in  	IN CHAR,
								 cNomTarjeta_in  	IN CHAR,
								 cUsuModCliLocal_in IN CHAR)
  RETURN CHAR
  IS PRAGMA AUTONOMOUS_TRANSACTION;
	v_cResultado	CHAR(1);
  BEGIN

	   UPDATE CE_TARJ_CLI
	   SET    COD_OPE_TARJ = cCodOperadorN_in,
			  FEC_VENC_TARJ = cFecVencimiento_in,
			  NUM_TARJ = cNumTarjeta_in,
			  NOM_TARJ= cNomTarjeta_in,
			  FEC_MOD_TARJ_CLI = SYSDATE,
			  USU_MOD_TARJ_CLI = cUsuModCliLocal_in
	   WHERE  COD_OPE_TARJ = cCodOperadorA_in
	   AND   COD_CLI_LOCAL = cCodCli_in;

	   COMMIT;
	   v_cResultado := '1';--grabo corectamente
	   RETURN v_cResultado;
  	   EXCEPTION
  		   WHEN OTHERS THEN
		   		ROLLBACK;
				v_cResultado := '2';--hubo error
		   		RETURN v_cResultado;
  END;




  END;

/
