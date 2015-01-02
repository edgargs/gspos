--------------------------------------------------------
--  DDL for Package PTOVENTA_ADMIN_IMP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_ADMIN_IMP" AS
TYPE FarmaCursor IS REF CURSOR;
/*************************************************************/
	 COD_NUMERA_IMPR   	  PBL_NUMERA.COD_NUMERA%TYPE := '006';
   COD_NUMERA_IMPR_TERM 	  PBL_NUMERA.COD_NUMERA%TYPE := '073';
	 ESTADO_ACTIVO		  CHAR(1):='A';
	 ESTADO_INACTIVO	  CHAR(1):='I';
	 INDICADOR_SI		  CHAR(1):='S';
	 POS_INICIO		      CHAR(1):='I';

/*************************************************************/
  --Descripcion: Obtiene el listado de las impresoras
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación
  FUNCTION IMP_LISTA_IMPRESORAS(cCodGrupoCia_in IN CHAR,
  		   				                cCodLocal_in	  IN CHAR)
  RETURN FarmaCursor;

/*************************************************************/
  --Descripcion: Obtiene el listado de asignacion de cajas - impresoras
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación
  FUNCTION IMP_LISTA_ASIGNACION_CAJA_IMPR(cCodGrupoCia_in IN CHAR,
  		   				                          cCodLocal_in	  IN CHAR)
  RETURN FarmaCursor;

  /*************************************************************/
  --Descripcion: Ingresa una impresora
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación
  --07/06/2013  ERIOS       Se agrega vModelo_in
  --07/08/2013  ERIOS       Se agrega vSerieImpr_in
   PROCEDURE IMP_INGRESA_IMPRESORA(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumSerieLocal_in IN CHAR,
                             cTipComp_in       IN CHAR,
                             cDescImprLocal_in IN CHAR,
                             cNumComp_in       IN CHAR,
                             cRutaImp_in       IN CHAR,
                             vModelo_in        IN VARCHAR,
							               vSerieImpr_in     IN VARCHAR,
                             cCodUsu_in        IN CHAR);

/*************************************************************/
  --Descripcion: Modifica una impresora
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación
  --22/03/2006  PAULO		Modificacion para completar los simbolos al campo de comprobante
  --07/06/2013  ERIOS       Se agrega vModel_in
  --07/08/2013  ERIOS       Se agrega vSerieImpr_in
  PROCEDURE IMP_MODIFICA_IMPRESORA( cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                               	   nSecImprLocal_in  IN NUMBER,
                               	   cNumSerieLocal_in IN CHAR,
                               	   cTipComp_in       IN CHAR,
                               	   cDescImprLocal_in IN CHAR,
                               	   cNumComp_in       IN CHAR,
                               	   cRutaImp_in       IN CHAR,
                                   vModelo_in        IN VARCHAR,
								                   vSerieImpr_in     IN VARCHAR,
                               	   cCodUsu_in        IN CHAR);

  /*************************************************************/
  --Descripcion: Cambia el estado de una impresora
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación

  PROCEDURE IMP_CAMBIA_ESTADO_IMPRESORA(cCodGrupoCia_in  IN CHAR,
		  					                        cCodLocal_in     IN CHAR,
							     		                  nSecImprLocal_in IN NUMBER,
										                    cCodUsu_in       IN CHAR);

  /*************************************************************/
  --Descripcion: Muestra la lista de comprobantes que necesitan impresion
  --Fecha       Usuario		Comentario
  --14/02/2006  MHUAYTA     Creación

  FUNCTION IMP_LISTA_TIPOS_COMPROBANTE(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor;

	/*************************************************************/
	--Descripcion: Muestra la lista de series disponibles para el tipo de comprobante especificado
    --Fecha       Usuario		Comentario
    --14/02/2006  MHUAYTA     Creación
   FUNCTION IMP_LISTA_SERIES_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cTipComp_in     IN CHAR,
                                         cNumSerie_in    IN CHAR DEFAULT NULL)
   RETURN FarmaCursor;

  --Se verifica que la ruta de impresion no existe para otras impresoras ticket
  --14/04/2009 JCORTEZ
  FUNCTION IMP_GET_RUTA_EXIST(pCodCia       IN CHAR,
                              pCodLocal     IN CHAR,
                              pTipoComp     IN CHAR,
                              pRuta         IN CHAR,
                              SecImpr       IN NUMBER)
  RETURN VARCHAR2;


  /***************************************************************************************************/

  --Descripcion: Muestra lista de IPs registradas en el sistema
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
  --08/06/2009  JCHAVEZ    Modificacion
  FUNCTION IMP_LISTA_IP(cCodGrupoCia_in IN CHAR,
                        pCodLocal       IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se ingresa un nuevo IP para la relacion maquina - impresora
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
  PROCEDURE IMP_INGRESA_IP(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cIP_in            IN CHAR);

  --Descripcion: Se elimina el IP seleccionado de la relacion maquina - impresora
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
  PROCEDURE IMP_ELIMINA_IP(cCodGrupoCia_in   IN CHAR,
                            cCodLocal_in      IN CHAR,
                            cIdUsu_in         IN CHAR,
                            cSecIP_in         IN CHAR,
                            cIP_in            IN CHAR);

	--Descripcion: Muestra lista de impresoras ticket disponibles
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
  FUNCTION IMP_LISTA_IMP(cCodGrupoCia_in IN CHAR,
                         pCodLocal       IN CHAR,
                         SecIP           IN CHAR,
                         cTipoComp       IN CHAR)
    RETURN FarmaCursor;


	--Descripcion: Se actualiza la relacion maquina - impresora
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
  PROCEDURE IMP_ACTUALIZA_IP(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cSecIP_in         IN CHAR,
                             cSecImpr_in       IN CHAR,
                             cNumSer_in        IN CHAR,
                             cTipComp          IN CHAR,
                             cIndTipComp       IN CHAR);


	--Descripcion: Se actualiza la relacion maquina - impresora
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
  PROCEDURE IMP_QUITAR_IMPR(cCodGrupoCia_in   IN CHAR,
                            cCodLocal_in      IN CHAR,
                            cIdUsu_in         IN CHAR,
                            cSecIP_in         IN CHAR,
                            cIndTipComp       IN CHAR);

	--Descripcion: Se valida ip para que cumpla la relacion maquina - impresora
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
   FUNCTION IMP_VALIDA_IP(pCodCia     IN CHAR,
                          pCodLocal   IN CHAR,
                          pIP         IN CHAR,
                          pTipoComp   IN CHAR)
   RETURN VARCHAR2;


 	--Descripcion: Se obtiene tipo de comprobante resecto a la IP
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
  FUNCTION IMP_GET_TIPCOMP_IP(pCodCia    IN CHAR,
                              pCodLocal  IN CHAR,
                              pIP        IN CHAR,
                              pTipoComp  IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: se obtiene secuencial de impresora  respecto a la IP
  --Fecha       Usuario		Comentario
  --15/06/2009  JCORTEZ    Creación
  FUNCTION IMP_GET_SECIMPR_IP(pCodCia   IN CHAR,
                              pCodLocal IN CHAR,
                              pIP       IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: se obtiene secuencial de factura de impresora  respecto a la IP
  --Fecha       Usuario		Comentario
  --31/01/2014  LLEIVA    Creación
  FUNCTION IMP_GET_SECIMPR_FAC_IP(pCodCia    IN CHAR,
                                  pCodLocal  IN CHAR,
                                  pIP        IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Se obtiene secuencial de impresion origen
  --Fecha       Usuario		Comentario
  --15/06/2009  JCORTEZ    Creación
  FUNCTION IMP_GET_SECIMPR_ORIGEN(pCodCia     IN CHAR,
                                  pCodLocal   IN CHAR,
                                  pTipComp    IN CHAR,
                                  pNumPedVta  IN CHAR,
                                  pIP         IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Verifica si existe secuencial de impresora
  --Fecha       Usuario		Comentario
  --15/06/2009  JCORTEZ    Creación
  FUNCTION IMP_EXIST_SECIMPR(pCodCia    IN CHAR,
                             pCodLocal  IN CHAR,
                             pSecImpr   IN CHAR,
                             pIP        IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Se obtiene descripcion de impresora
  --Fecha       Usuario		Comentario
  --15/06/2009  JCORTEZ    Creación
  FUNCTION IMP_GET_DESC(pCodCia   IN CHAR,
                        pCodLocal IN CHAR,
                        pSecImpr  IN CHAR)
  RETURN VARCHAR2;


 --Descripcion: Se obtiene descripcion de impresora termica
  --Fecha       Usuario		Comentario
  --25/06/2009  JCHAVEZ    Creación
  FUNCTION IMP_F_CUR_LISTA_IMP_TERMICA(cCodGrupoCia_in IN CHAR,
                                       pCodLocal       IN CHAR,
                                       SecIP           IN CHAR)
  RETURN FarmaCursor;


   --Descripcion: Se obtiene descripcion de impresora termica
  --Fecha       Usuario		Comentario
  --25/06/2009  JCHAVEZ    Creación
  PROCEDURE IMP_P_UPDATE_IP_IMP_TERMICA(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        vIdUsu_in       IN VARCHAR,
                                        cSecIP_in       IN CHAR,
                                        cSecImpTerm_in  IN NUMBER);


  --Descripcion: Obtiene el listado de las impresoras TERMICAS
  --Fecha       Usuario		Comentario
  --26/06/2009  JMIRANDA    Creación
  FUNCTION IMP_F_CUR_LISTA_IMP_TERMICAS(cCodGrupoCia_in IN CHAR,
  		   				        cCodLocal_in	 IN CHAR)
  RETURN FarmaCursor;

/*************************************************************/
  --Descripcion: Ingresa una impresora TERMICA
  --Fecha       Usuario		Comentario
  --26/06/2009  JMIRANDA     Creación
  PROCEDURE IMP_P_INSERT_IMP_TERMICA(cCodGrupoCia_in       IN CHAR,
                                     cCodLocal_in          IN CHAR,
                             	       cDescImprLocalTerm_in IN CHAR,
                                     cTipoImprTermica_in   IN Char,
                                     cEstImprLocal_in      IN Char,
                             	       cCodUsu_in            IN Char);

  /*************************************************************/
  --Descripcion: Modifica una impresora
  --Fecha       Usuario		Comentario
  --26/06/2009  JMIRANDA     Creación

	PROCEDURE IMP_P_UPDATE_IMP_TERMICA(cCodGrupoCia_in       IN CHAR,
                                     cCodLocal_in          IN CHAR,
                                     nSecImprLocTerm_in    IN NUMBER,
                                     cDescImprLocalTerm_in IN CHAR,
                                     cTipoImprTermica_in   IN CHAR,
                                     cEstImprLocalTerm_in  IN CHAR,
                                     cCodUsu_in            IN CHAR);

	--Descripcion: Muestra lista de IPs registradas en el sistema
  --Fecha       Usuario		Comentario
  --30/06/2009  JMIRANDA   Creación
  FUNCTION IMP_F_VAR2_EXIST_IMP_TERMICA(pCodCia       IN CHAR,
                                        pCodLocal     IN CHAR,
                                        pDescripcion  IN Char)
  Return Varchar2;

  /***************************************************************************************************/
  --Descripcion: Obtiene el modelo de la impresora
  --Fecha       Usuario	 Comentario
  --13/06/2013  ERIOS    Creacion
   FUNCTION IMP_GET_MODELO(cCodGrupoCia_in IN CHAR,
                           cCodCia_in      IN CHAR,
                           cCodLocal_in    IN CHAR,
                           nSecImpTerm_in  IN NUMBER)
   RETURN VARCHAR2;

  --Descripcion: Obtiene lista de modelos
  --Fecha       Usuario	 Comentario
  --17/06/2013  ERIOS    Creacion
   FUNCTION IMP_LISTA_MODELOS(cCodGrupoCia_in IN CHAR,
                              cCodCia_in      IN CHAR)
   RETURN FarmaCursor;

   FUNCTION IMP_IMPRESORAS_IP(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              nIp_in          IN CHAR)
     RETURN FarmaCursor;
    --Author:Cesar Huanes
   --Descripción:Obtiene el secuencial de guias y de boletas
   --Fecha:15-04-2014
  FUNCTION GET_SEC_IMPR_GUIA_BOLETA(pCodGrupoCia  IN CHAR,
                                  pCodLocal     IN CHAR,
                                  pTipComp      IN CHAR,
                                  pIP           IN CHAR)

   RETURN VARCHAR2   ;

END;

/
