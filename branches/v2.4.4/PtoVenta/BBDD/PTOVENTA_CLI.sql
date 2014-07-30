--------------------------------------------------------
--  DDL for Package PTOVENTA_CLI
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CLI" AS

  C_C_TIP_DOC_NATURAL VTA_CLI_LOCAL.TIP_DOC_IDENT%TYPE :='01';
  C_C_TIP_DOC_JURIDICO VTA_CLI_LOCAL.TIP_DOC_IDENT%TYPE :='02';
  C_C_ESTADO_ACTIVO VTA_CLI_LOCAL.EST_CLI_LOCAL%TYPE :='A';
  C_C_INDICADOR_NO  VTA_CLI_LOCAL.IND_CLI_JUR%TYPE :='N';

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Busca cliente juridico por RUC o por Razon Social
  --Fecha       Usuario		Comentario
  --23/02/2006  LMESIA     Creación
  FUNCTION CLI_BUSCA_CLI_JURIDICO(cCodGrupoCia_in  IN CHAR,
  		   				  		  cCodLocal_in	   IN CHAR,
								  cRuc_RazSoc_in   IN CHAR,
								  cTipoBusqueda_in IN CHAR)
	RETURN FarmaCursor;

  --Descripcion: Agrega un cliente juridico al local
  --Fecha       Usuario		Comentario
  --23/02/2006  LMESIA     Creación
  FUNCTION CLI_AGREGA_CLI_LOCAL(cCodGrupoCia_in	    IN CHAR,
  		   						cCodLocal_in	  	IN CHAR,
								cCodNumera_in	  	IN CHAR,
								cRazSoc_in 	     	IN CHAR,
 		   						cTipDocIdent_in	 	IN CHAR,
								cNumDocIdent_in  	IN CHAR,
								cDirCliLocal_in  	IN CHAR,
								cUsuCreaCliLocal_in IN CHAR)
  	RETURN CHAR;
   --Descripcion: Agrega un nuevo cliente natural
   --Fecha       Usuario		Comentario
   --03/03/2006  Paulo     		Creación
   FUNCTION CLI_AGREGA_CLI_NATURAL(cCodGrupoCia_in	    IN CHAR,
  		   						   cCodLocal_in	  	    IN CHAR,
								   cCodNumera_in	  	IN CHAR,
								   cNombre_in 	     	IN CHAR,
								   cApellido_Pat_in 	IN CHAR,
								   cApellido_Mat_in   	IN CHAR,
 		   						   cTipDocIdent_in	 	IN CHAR,
								   cNumDocIdent_in  	IN CHAR,
								   cDirCliLocal_in  	IN CHAR,
								   cUsuCreaCliLocal_in  IN CHAR)
    RETURN CHAR;

	--Descripcion: Lista Un cliente Natural dependiendo de su codigo
   	--Fecha       Usuario			Comentario
   	--03/03/2006  Paulo     		Creación
  	FUNCTION CLI_OBTIENE_INFO_CLI_NATURAL (cCodGrupoCia_in	IN CHAR,
  		   						   		   cCodLocal_in	  	IN CHAR,
										   cCod_Cli_Juri    IN NUMBER)
	RETURN FarmaCursor;

	--Descripcion: Lista las tarjetas de un cliente
   	--Fecha       Usuario			Comentario
   	--06/03/2006  Paulo     		Creación
  	FUNCTION CLI_OBTIENE_TARJETAS_CLIENTE (cCodGrupoCia_in	    IN CHAR,
  		   						   		   cCodLocal_in	  		IN CHAR,
										   cCod_Cliente         IN NUMBER)
	RETURN FarmaCursor;


  --Descripcion: Actualiza un cliente juridico al local
  --Fecha       Usuario		Comentario
  --23/02/2006  LMESIA     Creación
  FUNCTION CLI_ACTUALIZA_CLI_LOCAL(cCodGrupoCia_in	    IN CHAR,
  		   						   cCodLocal_in	  		IN CHAR,
								   cCodCliLocal_in	  	IN CHAR,
								   cRazSoc_in 	     	IN CHAR,
								   cNumDocIdent_in  	IN CHAR,
								   cDirCliLocal_in  	IN CHAR,
								   cUsuModCliLocal_in 	IN CHAR)
  	RETURN CHAR;

	--Descripcion: Actualiza un cliente natural al local
 	--Fecha       Usuario		Comentario
    --03/03/2006  Paulo     Creación
 	FUNCTION CLI_ACTUALIZA_CLI_NATURAL(cCodGrupoCia_in	    IN CHAR,
  		   						   	   cCodLocal_in	  		IN CHAR,
								       cCodCliLocal_in	  	IN CHAR,
								       cNomCliNatural_in   	IN CHAR,
									   cApePatCliNatural_in	IN CHAR,
								   	   cAPeMatCliNatural_in	IN CHAR,
									   cNumDocIdent_in  	IN CHAR,
								   	   cDirCliLocal_in  	IN CHAR,
									   cUsuModCliLocal_in 	IN CHAR)
  	RETURN CHAR;

	--Descripcion: Agrega tarjetas nuevas a clientes
 	--Fecha       Usuario		Comentario
    --07/03/2006  Paulo     Creación
	FUNCTION CLI_AGREGA_TARJETAS_CLI(cCodGrupoCia_in	 IN CHAR,
  		   							 cCodLocal_in	  	 IN CHAR,
									 cCodCli_in		  	 IN CHAR,
									 cCodOper_in     	 IN CHAR,
									 cFechaVen_in	 	 IN CHAR,
									 cNumeroTarj_in   	 IN CHAR,
 		   							 cNombreTarj_in	 	 IN CHAR,
									 cUsuCreaCliLocal_in IN CHAR)

	RETURN CHAR;

	--Descripcion: Actualiza las tarjetas de un cliente
 	--Fecha       Usuario		Comentario
    --07/03/2006  Paulo     Creación
	FUNCTION CLI_ACTUALIZA_TARJETA  (cCodCli_in		  	IN CHAR,
			 						 cCodOperadorN_in	IN CHAR,
									 cCodOperadorA_in   IN CHAR,
								     cFecVencimiento_in IN CHAR,
								     cNumTarjeta_in  	IN CHAR,
								   	 cNomTarjeta_in  	IN CHAR,
								     cUsuModCliLocal_in IN CHAR)
  	RETURN CHAR;

	END;

/
