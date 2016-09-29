CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_ADMIN_IMP" AS
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
  FUNCTION IMP_GET_SECIMPR_IP(pCodGrupoCia_in     IN CHAR,
                              pCodLocal_in        IN CHAR,
                              pIP_in              IN CHAR,
                              -- dubilluz 14.10.2014
                              pTipComp_in         IN CHAR,
                              pIndPedManual_in    IN CHAR)
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
  --KMONCADA 13.05.2016 CAMBIO PARA QUE SE REGISTRE EL PISO DE DESPACHO EN QUE SE ENCUENTRA LA IMPRESORA
  PROCEDURE IMP_P_INSERT_IMP_TERMICA(cCodGrupoCia_in       IN CHAR,
                                     cCodLocal_in          IN CHAR,
                             	       cDescImprLocalTerm_in IN CHAR,
                                     cTipoImprTermica_in   IN Char,
                                     cEstImprLocal_in      IN Char,
                             	       cCodUsu_in            IN Char,
                                     cNroPiso_in           IN CHAR DEFAULT NULL);

  /*************************************************************/
  --Descripcion: Modifica una impresora
  --Fecha       Usuario		Comentario
  --26/06/2009  JMIRANDA     Creación
  --KMONCADA 13.05.2016 CAMBIO PARA QUE SE REGISTRE EL PISO DE DESPACHO EN QUE SE ENCUENTRA LA IMPRESORA
	PROCEDURE IMP_P_UPDATE_IMP_TERMICA(cCodGrupoCia_in       IN CHAR,
                                     cCodLocal_in          IN CHAR,
                                     nSecImprLocTerm_in    IN NUMBER,
                                     cDescImprLocalTerm_in IN CHAR,
                                     cTipoImprTermica_in   IN CHAR,
                                     cEstImprLocalTerm_in  IN CHAR,
                                     cCodUsu_in            IN CHAR,
                                     cNroPiso_in           IN CHAR DEFAULT NULL);

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
   
     FUNCTION IMP_LISTA_TIPOS_COMP_MANUAL(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor;

FUNCTION IMP_F_VAR_MAX_COMP(cCodGrupoCia_in   IN CHAR,
                            cCodLocal_in      IN CHAR,
                            nSecImprLocal_in  IN NUMBER) RETURN VARCHAR2;  
  
  FUNCTION IMP_P_VALIDA_CAMBIO_COMP(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                             	     nSecImprLocal_in  IN NUMBER,
                             	     cNumComp_in       IN NUMBER,
                             	     cCodUsu_in        IN CHAR) RETURN VARCHAR2;  

FUNCTION F_VALIDA_APERTURA_CAJA(cCodGrupoCia_in   IN CHAR,
                                cCodLocal_in      IN CHAR,
                             	  nSecUsu_in        IN char) RETURN VARCHAR2;

FUNCTION F_CUR_CAJA_SIN_VB(cCodGrupoCia_in   IN CHAR,
                           cCodLocal_in      IN CHAR,
                           nSecUsu_in        IN char) RETURN FarmaCursor;  
--Author:Cesar Huanes
--Descripción:Lista serie por tipo de comprobante
--Fecha:31.03.2015                           
FUNCTION IMP_LISTA_SERIE_COMP(cCodGrupoCia_in IN CHAR,cCodLocal IN CHAR,cTipComp IN CHAR )
  RETURN FarmaCursor;

  FUNCTION F_GET_TIPO_LOCAL_VENTA(cCodGrupoCia_in IN PBL_LOCAL.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in    IN PBL_LOCAL.COD_LOCAL%TYPE,
                                  cIpPc_in        IN VTA_IMPR_IP.IP%TYPE)
  RETURN FARMACURSOR;

  PROCEDURE P_ACT_IP_TIPO_LOCAL_VTA(cCodGrupoCia_in       IN PBL_LOCAL.COD_GRUPO_CIA%TYPE,
                                    cCodLocal_in          IN PBL_LOCAL.COD_LOCAL%TYPE,
                                    cIpPc_in              IN VTA_IMPR_IP.IP%TYPE,
                                    cCodTipoLocalVenta_in IN MAE_TIP_LOCAL_VENTA.COD_TIP_LOCAL_VENTA%TYPE,
                                    cUsuMod_in            IN VARCHAR2);
                                      
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_ADMIN_IMP" AS
 /* ******************************************************************************************************* */
FUNCTION IMP_LISTA_IMPRESORAS(cCodGrupoCia_in  IN CHAR,
  		   			          cCodLocal_in	   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT TO_CHAR(IL.SEC_IMPR_LOCAL)  || 'Ã' ||
             NVL(IL.DESC_IMPR_LOCAL,' ') || 'Ã' ||
             NVL(TC.DESC_COMP,' ')       || 'Ã' ||
             NVL(IL.NUM_SERIE_LOCAL,' ') || 'Ã' ||
             NVL(IL.NUM_COMP,' ')        || 'Ã' ||
             NVL(IL.RUTA_IMPR,' ')       || 'Ã' ||
             CASE WHEN IL.EST_IMPR_LOCAL=ESTADO_ACTIVO
                  THEN 'Activo'
                  ELSE 'Inactivo'
             END   || 'Ã' ||
             NVL(IL.TIP_COMP,' ')        || 'Ã' ||
             NVL(IL.SERIE_IMP,' ')
      FROM VTA_IMPR_LOCAL  IL
      INNER JOIN VTA_SERIE_LOCAL SL on IL.COD_GRUPO_CIA   = SL.COD_GRUPO_CIA AND
                                       IL.COD_LOCAL       = SL.COD_LOCAL AND
                                       IL.NUM_SERIE_LOCAL = SL.NUM_SERIE_LOCAL AND
                                       IL.TIP_COMP        = SL.TIP_COMP
      INNER JOIN VTA_TIP_COMP    TC on SL.COD_GRUPO_CIA   = TC.COD_GRUPO_CIA AND
                                       SL.TIP_COMP        = TC.TIP_COMP
      WHERE IL.COD_GRUPO_CIA   = cCodGrupoCia_in
        AND IL.COD_LOCAL       = cCodLocal_in;
      RETURN curVta;
  END;
   /* ******************************************************************************************************* */

  FUNCTION IMP_LISTA_ASIGNACION_CAJA_IMPR(cCodGrupoCia_in  IN CHAR,
  		   				                          cCodLocal_in	   IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT CP.DESC_CAJA_PAGO || 'Ã' ||
             IL.DESC_IMPR_LOCAL || 'Ã' ||
             TC.DESC_COMP || 'Ã' ||
             CASE WHEN CI.EST_CAJA_IMPR = ESTADO_ACTIVO
                THEN 'Activo'
                ELSE 'Inactivo'
             END
      FROM  VTA_CAJA_PAGO CP
      INNER JOIN VTA_CAJA_IMPR CI on CP.COD_GRUPO_CIA  = CI.COD_GRUPO_CIA  AND
                                     CP.COD_LOCAL      = CI.COD_LOCAL 	   AND
                                     CP.NUM_CAJA_PAGO  = CI.NUM_CAJA_PAGO
      INNER JOIN VTA_IMPR_LOCAL IL on CI.COD_GRUPO_CIA = IL.COD_GRUPO_CIA  AND
                                      CI.COD_LOCAL	   = IL.COD_LOCAL 	   AND
                                      CI.SEC_IMPR_LOCAL= IL.SEC_IMPR_LOCAL
      INNER JOIN VTA_TIP_COMP TC on IL.COD_GRUPO_CIA   = TC.COD_GRUPO_CIA  AND
                                    IL.TIP_COMP		 = TC.TIP_COMP
      WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in AND
            CP.COD_LOCAL		 = cCodLocal_in;

      RETURN curVta;
  END;

 /* ******************************************************************************************************* */
  PROCEDURE IMP_INGRESA_IMPRESORA(cCodGrupoCia_in   IN CHAR,
                                  cCodLocal_in      IN CHAR,
                             	    cNumSerieLocal_in IN CHAR,
                             	    cTipComp_in       IN CHAR,
                             	    cDescImprLocal_in IN CHAR,
                             	    cNumComp_in       IN CHAR,
                             	    cRutaImp_in       IN CHAR,
                                  vModelo_in        IN VARCHAR,
								                  vSerieImpr_in     IN VARCHAR,
                             	    cCodUsu_in        IN CHAR)
  IS
      v_nNeoCod  NUMBER;
      v_cNumComp CHAR(7);

  BEGIN

      v_nNeoCod:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                  cCodLocal_in,
                                                  COD_NUMERA_IMPR);
      v_cNumComp:=Farma_Utility.COMPLETAR_CON_SIMBOLO(cNumComp_in,
                                                      7,
                                                      '0',
                                                      POS_INICIO);

      INSERT INTO VTA_IMPR_LOCAL (COD_GRUPO_CIA,
                                  COD_LOCAL,
                                  SEC_IMPR_LOCAL,
                                  NUM_SERIE_LOCAL,
                                  TIP_COMP,
                                  DESC_IMPR_LOCAL,
                                  NUM_COMP,
                                  RUTA_IMPR,
                                  EST_IMPR_LOCAL,
                                  FEC_CREA_IMPR_LOCAL,
                                  USU_CREA_IMPR_LOCAL,
                                  FEC_MOD_IMPR_LOCAL,
                                  USU_MOD_IMPR_LOCAL,
                                  MODELO_IMPRESORA,
											            SERIE_IMP)
      VALUES(cCodGrupoCia_in,
             cCodLocal_in,
             v_nNeoCod,
             cNumSerieLocal_in,
             cTipComp_in,
             cDescImprLocal_in,
             v_cNumComp ,
             cRutaImp_in,
             ESTADO_ACTIVO,
             SYSDATE,
             cCodUsu_in,
             NULL,
             NULL,
             vModelo_in,
						 vSerieImpr_in);
  END;

  /* ******************************************************************************************************* */
  PROCEDURE IMP_MODIFICA_IMPRESORA(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                             	     nSecImprLocal_in  IN NUMBER,
                             	     cNumSerieLocal_in IN CHAR,
                             	     cTipComp_in       IN CHAR,
                             	     cDescImprLocal_in IN CHAR,
                             	     cNumComp_in       IN CHAR,
                             	     cRutaImp_in       IN CHAR,
                                   vModelo_in        IN VARCHAR,
                                   vSerieImpr_in     IN VARCHAR,
                             	     cCodUsu_in        IN CHAR)
  IS
      v_cNumComp CHAR(7);
      /*v_cTipComp  VTA_IMPR_LOCAL.TIP_COMP%TYPE;
      v_vNumSerieLocal VTA_IMPR_LOCAL.NUM_SERIE_LOCAL%TYPE;*/
  BEGIN
      --ERIOS 10.02.2014 Se actualiza el maestro de ticketeras
	    v_cNumComp:=Farma_Utility.COMPLETAR_CON_SIMBOLO(cNumComp_in,7,'0',POS_INICIO );

      UPDATE VTA_IMPR_LOCAL
      SET FEC_MOD_IMPR_LOCAL = SYSDATE,USU_MOD_IMPR_LOCAL = cCodUsu_in,
		      --NUM_SERIE_LOCAL    = cNumSerieLocal_in,
          --TIP_COMP		   = cTipComp_in,
          --no se modificara la descripcion
          --DUBILLUZ 04.06.2009
          --DESC_IMPR_LOCAL    = cDescImprLocal_in,
          NUM_COMP		   = v_cNumComp,
          RUTA_IMPR		   = cRutaImp_in,
          MODELO_IMPRESORA = vModelo_in,
				  SERIE_IMP = vSerieImpr_in
      WHERE	COD_GRUPO_CIA  = cCodGrupoCia_in   AND
            COD_LOCAL		   = cCodLocal_in      AND
            SEC_IMPR_LOCAL = nSecImprLocal_in;

      /*SELECT TIP_COMP,NUM_SERIE_LOCAL
        INTO v_cTipComp, v_vNumSerieLocal
      FROM VTA_IMPR_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND SEC_IMPR_LOCAL = nSecImprLocal_in;

      IF v_cTipComp = '05' THEN --05: TICKET BOLETA
        UPDATE LGT_MAE_TICKET
        SET SERIE_TICKET = vSerieImpr_in,
        usu_mod_mae_ticket = cCodUsu_in,
        fec_mod_mae_ticket = SYSDATE
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND SERIE_SISTEMA = TO_NUMBER(v_vNumSerieLocal)
        AND COD_LOCAL = cCodLocal_in;
      END IF;*/

  END;

  /* ******************************************************************************************************* */
  PROCEDURE IMP_CAMBIA_ESTADO_IMPRESORA(cCodGrupoCia_in  IN CHAR,
		  					                        cCodLocal_in     IN CHAR,
							   		                    nSecImprLocal_in IN NUMBER,
									                      cCodUsu_in        IN CHAR)
  IS
      v_est CHAR(1);
  BEGIN
      SELECT EST_IMPR_LOCAL
	    INTO   v_est
	    FROM   VTA_IMPR_LOCAL
      WHERE  COD_GRUPO_CIA  = cCodGrupoCia_in AND
             COD_LOCAL      = cCodLocal_in    AND
             SEC_IMPR_LOCAL = nSecImprLocal_in;

       IF   v_est = ESTADO_ACTIVO THEN
            v_est:= ESTADO_INACTIVO;
       ELSE v_est:= ESTADO_ACTIVO;
       END IF;

       UPDATE VTA_IMPR_LOCAL
       SET FEC_MOD_IMPR_LOCAL = SYSDATE,
           USU_MOD_IMPR_LOCAL =  cCodUsu_in,
	   		   EST_IMPR_LOCAL = v_est
       WHERE COD_GRUPO_CIA  = cCodGrupoCia_in  AND
             COD_LOCAL      = cCodLocal_in     AND
             SEC_IMPR_LOCAL = nSecImprLocal_in;
  END;

  /* ******************************************************************************************************* */
  FUNCTION IMP_LISTA_TIPOS_COMPROBANTE(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
		  SELECT TIP_COMP    || 'Ã' ||
             DESC_COMP
      FROM   VTA_TIP_COMP
      WHERE  COD_GRUPO_CIA     = cCodGrupoCia_in AND
			       IND_NECESITA_IMPR = INDICADOR_SI;
      RETURN curVta;
  END;

  /******************************************************************************************************** */
  FUNCTION IMP_LISTA_SERIES_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cTipComp_in     IN CHAR,
                                        cNumSerie_in IN CHAR DEFAULT NULL)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      IF cNumSerie_in IS NOT NULL THEN
          OPEN curVta FOR
          SELECT TRIM(cNumSerie_in)    || 'Ã' ||
                 TRIM(cNumSerie_in)
          FROM DUAL;
      ELSE
          OPEN curVta FOR
		      SELECT TRIM(NUM_SERIE_LOCAL)    || 'Ã' ||
                 TRIM(NUM_SERIE_LOCAL)
          FROM   VTA_SERIE_LOCAL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
		             COD_LOCAL  	 = cCodLocal_in    AND
			           TIP_COMP LIKE (cTipComp_in)     AND
                 NUM_SERIE_LOCAL NOT IN (SELECT DISTINCT NUM_SERIE_LOCAL --ERIOS 18/04/2006
                                         FROM VTA_IMPR_LOCAL
                                         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND COD_LOCAL = cCodLocal_in
                                           AND TIP_COMP LIKE (cTipComp_in));
    END IF;
    RETURN curVta;
  END;

  /********************************************************************************/
  FUNCTION IMP_GET_RUTA_EXIST(pCodCia       IN CHAR,
                               pCodLocal     IN CHAR,
                               pTipoComp     IN CHAR,
                               pRuta         IN CHAR,
                               SecImpr       IN NUMBER)
  RETURN VARCHAR2
  IS
      VALOR  CHAR(1);
      CANT    NUMBER;
  BEGIN

      SELECT COUNT(*) INTO CANT
      FROM VTA_IMPR_LOCAL X
      WHERE X.COD_GRUPO_CIA=pCodCia
      AND X.COD_LOCAL=pCodLocal
      AND X.TIP_COMP=pTipoComp
      AND X.RUTA_IMPR LIKE pRuta||'%'
      AND X.SEC_IMPR_LOCAL<>SecImpr;

      IF(CANT>0)THEN
          VALOR:='S';
      ELSE
          VALOR:='N';
      END IF;
      RETURN VALOR;
  END;

  /********************************************************************************/

  FUNCTION IMP_LISTA_IP(cCodGrupoCia_in IN CHAR,
                        pCodLocal     IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN

	--ERIOS 2.4.5 Cambios proyecto Conveniencia
      OPEN curVta FOR
      SELECT A.SEC_IP || 'Ã' ||
             NVL(A.IP,' ') || 'Ã' ||
             NVL(BOL.DESC_IMPR_LOCAL,'Sin asignacion') || 'Ã' ||
             NVL(VTC_B.DESC_COMP,' ') || 'Ã' ||
             NVL(FAC.DESC_IMPR_LOCAL,'Sin asignacion') || 'Ã' ||
             NVL(VTC_F.DESC_COMP,' ') || 'Ã' ||
             NVL(C.DESC_IMPR_LOCAL_TERM,' ')
      FROM VTA_IMPR_IP A
      LEFT JOIN VTA_IMPR_LOCAL BOL on (BOL.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                                      AND BOL.COD_LOCAL = A.COD_LOCAL
                                      AND BOL.SEC_IMPR_LOCAL = A.SEC_IMPR_LOCAL)
      LEFT JOIN VTA_IMPR_LOCAL FAC on (FAC.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                                      AND FAC.COD_LOCAL = A.COD_LOCAL
                                      AND FAC.SEC_IMPR_LOCAL = A.SEC_IMPR_LOCAL_FAC)
      LEFT JOIN VTA_IMPR_LOCAL_TERMICA C  on
                                      (C.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                                      AND C.COD_LOCAL = A.COD_LOCAL
                                      AND C.SEC_IMPR_LOC_TERM = A.SEC_IMPR_LOC_TERM)
      LEFT JOIN VTA_TIP_COMP VTC_B on
                                    (VTC_B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                                    AND VTC_B.TIP_COMP = A.TIP_COMP)
      LEFT JOIN VTA_TIP_COMP VTC_F on
                                    (VTC_F.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                                    AND VTC_F.TIP_COMP = A.TIP_COMP_FAC)
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_LOCAL = pCodLocal
      ORDER BY A.SEC_IP;

      RETURN curVta;
  END;


  /* ******************************************************************************************************* */
  PROCEDURE IMP_INGRESA_IP(cCodGrupoCia_in   IN CHAR,
                           cCodLocal_in      IN CHAR,
                           cIdUsu_in         IN CHAR,
                           cIP_in            IN CHAR)
  IS
      nSecIP  NUMBER;
      cant    NUMBER;
  BEGIN

      SELECT NVL(TO_NUMBER(MAX(X.SEC_IP))+1,1)
      INTO nSecIP
      FROM VTA_IMPR_IP X
      WHERE  X.COD_GRUPO_CIA = cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in;

      SELECT COUNT(*) INTO CANT
      FROM VTA_IMPR_IP Y
      WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
        AND Y.COD_LOCAL=cCodLocal_in
        AND TRIM(Y.IP)=TRIM(cIP_in);

      IF( CANT >0)THEN
          RAISE_APPLICATION_ERROR(-20001,'El ip ingresado ya existe. Verifique!!!');
      ELSE
          INSERT INTO VTA_IMPR_IP (COD_GRUPO_CIA,
                                   COD_LOCAL,
                                   SEC_IP,
                                   IP,
                                   SEC_IMPR_LOCAL,
                                   NUM_SERIE_LOCAL,
                                   TIP_COMP,
                                   USU_CREA_IP,
                                   FEC_MOD_IP,
                                   USU_MOD_IP)
          VALUES (cCodGrupoCia_in,
                  cCodLocal_in,
                  nSecIP,
                  cIP_in,
                  null,
                  null,
                  null,
                  cIdUsu_in,
                  NULL,
                  NULL);
      END IF;
  END;


  /* ******************************************************************************************************* */
  PROCEDURE IMP_ELIMINA_IP(cCodGrupoCia_in   IN CHAR,
                           cCodLocal_in      IN CHAR,
                           cIdUsu_in         IN CHAR,
                           cSecIP_in         IN CHAR,
                           cIP_in            IN CHAR)
  IS
  BEGIN
      DELETE FROM VTA_IMPR_IP A
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=cCodLocal_in
        AND A.IP LIKE cIP_in
        AND A.SEC_IP=TO_NUMBER(cSecIP_in);
  END;

  /********************************************************************************/
  FUNCTION IMP_LISTA_IMP(cCodGrupoCia_in IN CHAR,
                         pCodLocal       IN CHAR,
                         SecIP           IN CHAR,
                         cTipoComp       IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN

      IF(cTipoComp = 'B') THEN
          OPEN curVta FOR
          SELECT A.SEC_IMPR_LOCAL            || 'Ã' ||
                 NVL(A.NUM_SERIE_LOCAL,' ')  || 'Ã' ||
                 NVL(A.DESC_IMPR_LOCAL,' ')  || 'Ã' ||
                 NVL(A.RUTA_IMPR,' ')        || 'Ã' ||
                 NVL(VTC.DESC_COMP,' ')      || 'Ã' || --DECODE(A.TIP_COMP,01,'BOLETA',02,'FACTURA',03,'GUIA',04,'NOTA DE CREDITO',05,'TICKET')|| 'Ã' ||
                 NVL(A.TIP_COMP,' ')
          FROM VTA_IMPR_LOCAL A
          INNER JOIN VTA_TIP_COMP VTC on A.TIP_COMP = VTC.TIP_COMP
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_LOCAL=pCodLocal
          AND A.TIP_COMP IN ('05','01')
          AND A.SEC_IMPR_LOCAL NOT IN (SELECT NVL(X.SEC_IMPR_LOCAL,'0')
                                       FROM VTA_IMPR_IP X
                                       WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
                                       AND X.COD_LOCAL=pCodLocal
                                       AND X.SEC_IP=TO_NUMBER(SecIP));
        --  AND A.SEC_IMPR_LOCAL IN (SELECT DISTINCT X.SEC_IMPR_LOCAL FROM VTA_IMPR_IP X WHERE  X.SEC_IMPR_LOCAL IS NOT NULL);
    /*      AND A.SEC_IMPR_LOCAL NOT IN (SELECT DISTINCT NVL(B.SEC_IMPR_LOCAL,'00')
                                      FROM VTA_IMPR_IP B
                                      WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
                                      AND B.COD_LOCAL=pCodLocal);*/
      ELSIF(cTipoComp = 'F') THEN
          OPEN curVta FOR
          SELECT A.SEC_IMPR_LOCAL            || 'Ã' ||
                 NVL(A.NUM_SERIE_LOCAL,' ')  || 'Ã' ||
                 NVL(A.DESC_IMPR_LOCAL,' ')  || 'Ã' ||
                 NVL(A.RUTA_IMPR,' ')        || 'Ã' ||
                 NVL(VTC.DESC_COMP,' ')      || 'Ã' || --DECODE(A.TIP_COMP,01,'BOLETA',02,'FACTURA',03,'GUIA',04,'NOTA DE CREDITO',05,'TICKET')|| 'Ã' ||
                 NVL(A.TIP_COMP,' ')
          FROM VTA_IMPR_LOCAL A
          INNER JOIN VTA_TIP_COMP VTC on A.TIP_COMP = VTC.TIP_COMP
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_LOCAL=pCodLocal
          AND A.TIP_COMP IN ('06','02')
          AND A.SEC_IMPR_LOCAL NOT IN (SELECT NVL(X.SEC_IMPR_LOCAL,'0')
                                       FROM VTA_IMPR_IP X
                                       WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
                                       AND X.COD_LOCAL=pCodLocal
                                       AND X.SEC_IP=TO_NUMBER(SecIP));
        --  AND A.SEC_IMPR_LOCAL IN (SELECT DISTINCT X.SEC_IMPR_LOCAL FROM VTA_IMPR_IP X WHERE  X.SEC_IMPR_LOCAL IS NOT NULL);
    /*      AND A.SEC_IMPR_LOCAL NOT IN (SELECT DISTINCT NVL(B.SEC_IMPR_LOCAL,'00')
                                      FROM VTA_IMPR_IP B
                                      WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
                                      AND B.COD_LOCAL=pCodLocal);*/
      END IF;
      RETURN curVta;
    END;

  /* ******************************************************************************************************* */
  PROCEDURE IMP_ACTUALIZA_IP(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cIdUsu_in         IN CHAR,
                             cSecIP_in         IN CHAR,
                             cSecImpr_in       IN CHAR,
                             cNumSer_in        IN CHAR,
                             cTipComp          IN CHAR,
                             cIndTipComp       IN CHAR)
  IS
      nSecIP  NUMBER;
  BEGIN
      IF(cIndTipComp='B') THEN

          UPDATE VTA_IMPR_IP X
          SET (X.DESC_IMPR,
               X.TIP_COMP,
               X.SEC_IMPR_LOCAL,
               X.NUM_SERIE_LOCAL,
               X.USU_MOD_IP,
               X.FEC_MOD_IP)=
              (SELECT Y.DESC_IMPR_LOCAL,
                      Y.TIP_COMP,
                      Y.SEC_IMPR_LOCAL,
                      Y.NUM_SERIE_LOCAL,
                      cIdUsu_in,
                      SYSDATE
               FROM VTA_IMPR_LOCAL Y
               WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
               AND Y.COD_LOCAL=cCodLocal_in
               AND Y.SEC_IMPR_LOCAL=TO_NUMBER(cSecImpr_in)
               AND Y.NUM_SERIE_LOCAL=cNumSer_in
               AND Y.TIP_COMP=cTipComp)
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
            AND X.COD_LOCAL=cCodLocal_in
            AND X.SEC_IP=TO_NUMBER(cSecIP_in);

      ELSIF(cIndTipComp='F') THEN

          UPDATE VTA_IMPR_IP X
          SET (X.DESC_IMPR,
               X.TIP_COMP_FAC,
               X.SEC_IMPR_LOCAL_FAC,
               X.NUM_SERIE_LOCAL_FAC,
               X.USU_MOD_IP,
               X.FEC_MOD_IP)=
              (SELECT Y.DESC_IMPR_LOCAL,
                      Y.TIP_COMP,
                      Y.SEC_IMPR_LOCAL,
                      Y.NUM_SERIE_LOCAL,
                      cIdUsu_in,
                      SYSDATE
               FROM VTA_IMPR_LOCAL Y
               WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
               AND Y.COD_LOCAL=cCodLocal_in
               AND Y.SEC_IMPR_LOCAL=TO_NUMBER(cSecImpr_in)
               AND Y.NUM_SERIE_LOCAL=cNumSer_in
               AND Y.TIP_COMP=cTipComp)
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
            AND X.COD_LOCAL=cCodLocal_in
            AND X.SEC_IP=TO_NUMBER(cSecIP_in);
      END IF;
  END;

	--Descripcion: Se actualiza la relacion maquina - impresora
  --Fecha       Usuario		Comentario
  --08/06/2009  JCORTEZ    Creación
  PROCEDURE IMP_QUITAR_IMPR(cCodGrupoCia_in   IN CHAR,
                            cCodLocal_in      IN CHAR,
                            cIdUsu_in         IN CHAR,
                            cSecIP_in         IN CHAR,
                            cIndTipComp       IN CHAR)
  IS
      nSecIP  NUMBER;
  BEGIN
      --Boletas
      IF(cIndTipComp='B') THEN

          UPDATE VTA_IMPR_IP X
          SET --X.DESC_IMPR=NULL,
              X.SEC_IMPR_LOCAL=NULL,
              X.NUM_SERIE_LOCAL=NULL,
              X.TIP_COMP=NULL
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
          AND X.COD_LOCAL=cCodLocal_in
          AND X.SEC_IP=TO_NUMBER(cSecIP_in);

      --Factura
      ELSIF(cIndTipComp='F') THEN

          UPDATE VTA_IMPR_IP X
          SET --X.DESC_IMPR=NULL,
              X.SEC_IMPR_LOCAL_FAC=NULL,
              X.NUM_SERIE_LOCAL_FAC=NULL,
              X.TIP_COMP_FAC=NULL
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
          AND X.COD_LOCAL=cCodLocal_in
          AND X.SEC_IP=TO_NUMBER(cSecIP_in);

      --Termicas
      ELSIF(cIndTipComp='T') THEN

          UPDATE VTA_IMPR_IP X
          SET --X.DESC_IMPR=NULL,
              X.SEC_IMPR_LOC_TERM=NULL--,
--              X.NUM_SERIE_LOCAL_FAC=NULL,
--              X.TIP_COMP_FAC=NULL
          WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
          AND X.COD_LOCAL=cCodLocal_in
          AND X.SEC_IP=TO_NUMBER(cSecIP_in);

      END IF;
  END;

  /********************************************************************************/
  FUNCTION IMP_VALIDA_IP(pCodCia       IN CHAR,
                         pCodLocal     IN CHAR,
                         pIP           IN CHAR,
                         pTipoComp     IN CHAR)
  RETURN VARCHAR2
  IS
      VALOR  CHAR(1);
      CANT    NUMBER;
  BEGIN
       SELECT COUNT(*) INTO CANT
       FROM VTA_IMPR_IP X
       WHERE X.COD_GRUPO_CIA=pCodCia
       AND X.COD_LOCAL=pCodLocal
       AND X.TIP_COMP=pTipoComp
       AND TRIM(X.IP)=TRIM(pIP);

       IF(CANT>0)THEN
           VALOR:='S';
       ELSE
           VALOR:='N';
       END IF;

       RETURN VALOR;
  END;


  /********************************************************************************/
  FUNCTION IMP_GET_TIPCOMP_IP(pCodCia       IN CHAR,
                              pCodLocal     IN CHAR,
                              pIP           IN CHAR,
                              pTipoComp     IN CHAR)
  RETURN VARCHAR2
  IS
      TIPCOMP CHAR(2);
      CANT    NUMBER;
  BEGIN
      SELECT COUNT(*) INTO CANT
      FROM VTA_IMPR_IP Y
      WHERE Y.COD_GRUPO_CIA=pCodCia
        AND Y.COD_LOCAL=pCodLocal
        AND Y.SEC_IMPR_LOCAL IS NOT NULL
        AND TRIM(Y.IP)=TRIM(pIP);

      IF (pTipoComp='01' or pTipoComp='05') THEN
          IF(CANT>0)THEN
              SELECT nvl(X.TIP_COMP,'N') INTO TIPCOMP
              FROM VTA_IMPR_IP X
              WHERE X.COD_GRUPO_CIA=pCodCia
                AND X.COD_LOCAL=pCodLocal
                AND TRIM(X.IP)=TRIM(pIP);
          ELSE
              TIPCOMP:='N';
          END IF;
      ELSIF (pTipoComp='02' or pTipoComp='06') THEN

          IF(CANT>0)THEN
              SELECT nvl(X.TIP_COMP_FAC,'N') INTO TIPCOMP
              FROM VTA_IMPR_IP X
              WHERE X.COD_GRUPO_CIA=pCodCia
                AND X.COD_LOCAL=pCodLocal
                AND TRIM(X.IP)=TRIM(pIP);
          ELSE
              TIPCOMP:='N';
          END IF;
      END IF;

      RETURN TIPCOMP;
  END;


  /********************************************************************************/
  FUNCTION IMP_GET_SECIMPR_IP(pCodGrupoCia_in     IN CHAR,
                              pCodLocal_in        IN CHAR,
                              pIP_in              IN CHAR,
                              -- dubilluz 14.10.2014
                              pTipComp_in         IN CHAR,
                              pIndPedManual_in    IN CHAR)
  RETURN VARCHAR2
  IS
      SecImp  VARCHAR2(4);
      CANT    NUMBER;
  BEGIN
    if pIndPedManual_in = 'N' then
        SELECT COUNT(*) INTO CANT
        FROM VTA_IMPR_IP A
        WHERE A.COD_GRUPO_CIA=pCodGrupoCia_in
          AND A.COD_LOCAL=pCodLocal_in
          AND A.SEC_IMPR_LOCAL IS NOT NULL
          AND TRIM(A.IP)=TRIM(pIP_in);

        IF(CANT>0)THEN
            SELECT TO_CHAR(X.SEC_IMPR_LOCAL) INTO SecImp
            FROM VTA_IMPR_IP X
            WHERE X.COD_GRUPO_CIA=pCodGrupoCia_in
              AND X.COD_LOCAL=pCodLocal_in
              AND TRIM(X.IP)=TRIM(pIP_in);
        ELSE
            SecImp:='N';
        END IF;
    else
      -- Si es regularizacion de COMPROBANTE MANUAL solo se deja BOLETA y FACTURA
      if pTipComp_in = '01' OR  pTipComp_in = '02' then
        
            /*SELECT TO_CHAR(X.SEC_IMPR_LOCAL) INTO SecImp
            FROM   VTA_IMPR_LOCAL X
            WHERE X.COD_GRUPO_CIA=pCodCia
              AND X.COD_LOCAL=pCodLocal
              AND X.TIP_COMP  =pTipComp;*/
        -- KMONCADA 25.05.2016
        SecImp := PTOVENTA_CAJ.F_GET_SECUENCIAL_IMPRESORA(cCodGrupoCia_in => pCodGrupoCia_in,
                                                          cCodLocal_in => pCodLocal_in,
                                                          cTipoCP_in => pTipComp_in,
                                                          cIpPC_in => pIP_in);
        end if;
    end if;


      RETURN SecImp;
  END;


  /******************************************************************************/
  FUNCTION IMP_GET_SECIMPR_FAC_IP(pCodCia       IN CHAR,
                                   pCodLocal     IN CHAR,
                                   pIP           IN CHAR)
  RETURN VARCHAR2
  IS
      SecImp  VARCHAR2(4);
      CANT    NUMBER;
  BEGIN
      SELECT COUNT(*) INTO CANT
      FROM VTA_IMPR_IP A
      WHERE A.COD_GRUPO_CIA=pCodCia
        AND A.COD_LOCAL=pCodLocal
        AND A.SEC_IMPR_LOCAL_FAC IS NOT NULL
        AND TRIM(A.IP)=TRIM(pIP);

      IF(CANT>0)THEN
          SELECT TO_CHAR(X.SEC_IMPR_LOCAL_FAC) INTO SecImp
          FROM VTA_IMPR_IP X
          WHERE X.COD_GRUPO_CIA=pCodCia
            AND X.COD_LOCAL=pCodLocal
            AND TRIM(X.IP)=TRIM(pIP);
      ELSE
          SecImp:='N';
      END IF;
      RETURN SecImp;
  END;

  /********************************************************************************/
  FUNCTION IMP_GET_SECIMPR_ORIGEN(pCodCia       IN CHAR,
                                  pCodLocal     IN CHAR,
                                  pTipComp      IN CHAR,
                                  pNumPedVta    IN CHAR,
                                  pIP           IN CHAR)
  RETURN VARCHAR2
  IS
      SecImp  VARCHAR(2);
      CANT    NUMBER;
  BEGIN
      SELECT COUNT(*) INTO CANT
      FROM VTA_IMPR_LOCAL B
      WHERE B.NUM_SERIE_LOCAL IN (SELECT Substr(TRIM(A.NUM_COMP_PAGO),0, 3)
                                  FROM VTA_COMP_PAGO A
                                  WHERE A.TIP_COMP_PAGO=pTipComp
                                    AND A.NUM_PED_VTA=pNumPedVta);

      IF(CANT>0)THEN
          SELECT B.SEC_IMPR_LOCAL
          INTO SecImp
          FROM VTA_IMPR_LOCAL B
          WHERE B.TIP_COMP=pTipComp
            AND B.NUM_SERIE_LOCAL IN (SELECT Substr(TRIM(A.NUM_COMP_PAGO),0, 3)
                                      FROM VTA_COMP_PAGO A
                                      WHERE A.TIP_COMP_PAGO=pTipComp
                                        AND A.NUM_PED_VTA=pNumPedVta);
      ELSE
          SELECT A.SEC_IMPR_LOCAL
          INTO SecImp
          FROM VTA_IMPR_LOCAL A
          WHERE A.TIP_COMP=pTipComp
            AND A.NUM_SERIE_LOCAL IN (SELECT X.NUM_SERIE_LOCAL
                                      FROM VTA_IMPR_IP X
                                      WHERE X.TIP_COMP=pTipComp
                                      AND TRIM(X.IP)=TRIM(pIP));
      END IF;
      RETURN SecImp;
  END;


  /********************************************************************************/
  FUNCTION IMP_EXIST_SECIMPR(pCodCia        IN CHAR,
                             pCodLocal     IN CHAR,
                             pSecImpr      IN CHAR,
                             pIP           IN CHAR)
  RETURN VARCHAR2
  IS
      results  VARCHAR(1);
      CANT    NUMBER;
      CANT2   NUMBER;
      CANT3   NUMBER;
      SecDis   VARCHAR(1);
  BEGIN

      --verifica imp origen
      SELECT COUNT(*) INTO CANT
      FROM VTA_IMPR_LOCAL A
      WHERE A.COD_GRUPO_CIA=pCodCia
        AND A.COD_LOCAL=pCodLocal
        AND A.EST_IMPR_LOCAL='A'
        AND A.SEC_IMPR_LOCAL=TRIM(pSecImpr);

      --valida asignada
      SELECT COUNT(*) INTO CANT2
      FROM VTA_IMPR_IP B
      WHERE B.COD_GRUPO_CIA=pCodCia
        AND B.COD_LOCAL=pCodLocal
        AND B.EST_IP='A'
        AND B.SEC_IMPR_LOCAL IS NOT NULL
        AND TRIM(B.IP)=TRIM(pIP);

       --valida disponible
      SELECT COUNT(*) INTO CANT3
      FROM VTA_IMPR_LOCAL B
      WHERE B.COD_GRUPO_CIA=pCodCia
        AND B.COD_LOCAL=pCodLocal
        AND B.EST_IMPR_LOCAL='A'
        AND B.TIP_COMP IN ('05')
        AND B.SERIE_IMP IS NOT NULL;


      IF(CANT>0)THEN
          results:='1';
      ELSE
          IF(CANT2>0)THEN
              results:='2';
          ELSE
              IF(CANT3>0)THEN
                  SELECT B.SEC_IMPR_LOCAL INTO SecDis
                  FROM VTA_IMPR_LOCAL B
                  WHERE B.COD_GRUPO_CIA=pCodCia
                    AND B.COD_LOCAL=pCodLocal
                    AND B.EST_IMPR_LOCAL='A'
                    AND B.TIP_COMP IN ('05')
                    AND B.SERIE_IMP IS NOT NULL;

                  results:=SecDis;

              ELSE
                  results:='X';
              END IF;
          END IF;
      END IF;

      RETURN results;
  END;


  /********************************************************************************/
  FUNCTION IMP_GET_DESC(pCodCia       IN CHAR,
                         pCodLocal     IN CHAR,
                         pSecImpr      IN CHAR)
  RETURN VARCHAR2
  IS
      descrip  VARCHAR(20);
      CANT  NUMBER;
  BEGIN
      SELECT COUNT(*) INTO CANT
      FROM VTA_IMPR_LOCAL X
      WHERE X.COD_GRUPO_CIA=pCodCia
        AND X.COD_LOCAL=pCodLocal
        AND X.SEC_IMPR_LOCAL=TRIM(pSecImpr);

      IF(CANT>0)THEN
          SELECT X.DESC_IMPR_LOCAL INTO descrip
          FROM VTA_IMPR_LOCAL X
          WHERE X.COD_GRUPO_CIA=pCodCia
            AND X.COD_LOCAL=pCodLocal
            AND X.SEC_IMPR_LOCAL=TRIM(pSecImpr);
      ELSE
          descrip:=' ';
      END IF;
      RETURN descrip;
  END;

  /* ****************************************************************************** */
  --FUNCTION IMP_LISTA_IMP_TERMICA(cCodGrupoCia_in IN CHAR,
  FUNCTION IMP_F_CUR_LISTA_IMP_TERMICA(cCodGrupoCia_in IN CHAR,
                                       pCodLocal       IN CHAR,
                                       SecIP           IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT A.SEC_IMPR_LOC_TERM|| 'Ã' ||
             A.Desc_Impr_Local_Term || 'Ã' ||
             DECODE(A.TIPO_IMPR_TERMICA,01,'EPSON',02,'STAR') || 'Ã' ||
             A.TIPO_IMPR_TERMICA
      FROM VTA_IMPR_LOCAL_TERMICA A
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=pCodLocal
        AND A.TIPO_IMPR_TERMICA IN ('01','02')
        AND A.EST_IMPR_LOCAL='A'
        AND A.SEC_IMPR_LOC_TERM NOT IN (SELECT NVL(X.SEC_IMPR_LOC_TERM,'0')
                                        FROM VTA_IMPR_IP X
                                        WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
                                          AND X.COD_LOCAL=pCodLocal
                                          AND X.SEC_IP=TO_NUMBER(to_number(SecIP)));
      RETURN curVta;
  END;

  /* ******************************************************************************************************* */
  --PROCEDURE IMP_ACTUALIZA_IP_IMPTER(cCodGrupoCia_in   IN CHAR,
  PROCEDURE IMP_P_UPDATE_IP_IMP_TERMICA( cCodGrupoCia_in   IN CHAR,
                                         cCodLocal_in      IN CHAR,
                                         vIdUsu_in         IN VARCHAR,
                                         cSecIP_in         IN CHAR,
                                         cSecImpTerm_in    IN NUMBER)
  IS
  BEGIN
      UPDATE VTA_IMPR_IP A
      SET A.SEC_IMPR_LOC_TERM=cSecImpTerm_in,
          A.FEC_MOD_IP=SYSDATE,
          A.USU_MOD_IP=vIdUsu_in
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=cCodLocal_in
        AND A.SEC_IP=TO_NUMBER(cSecIP_in);
  END;

  /* ******************************************************************************************************* */
  -- JMIRANDA 26/06/2009
  --FUNCTION IMP_LISTA_IMPRESORAS_TERMICAS(cCodGrupoCia_in  IN CHAR,
  FUNCTION IMP_F_CUR_LISTA_IMP_TERMICAS(cCodGrupoCia_in  IN CHAR,
  		   			                          cCodLocal_in	   IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT TO_CHAR(ILT.SEC_IMPR_LOC_TERM) || 'Ã' ||
              ILT.DESC_IMPR_LOCAL_TERM || 'Ã' || 
              TB.DESC_CORTA || 'Ã' ||
             -- ilt.est_impr_local
              CASE
                WHEN ILT.EST_IMPR_LOCAL = ESTADO_ACTIVO THEN
                 'Activo'
                ELSE
                 'Inactivo'
              END || 'Ã' || 
              ilt.TIPO_IMPR_TERMICA || 'Ã' ||
              -- KMONCADA 12.05.2016 IMPRESION DE COMANDA DE DESPACHO
              NVL(ILT.PISO_DESPACHO||'',' ')
        FROM VTA_IMPR_LOCAL_TERMICA ILT
             LEFT JOIN PBL_TAB_GRAL TB ON ILT.TIPO_IMPR_TERMICA = TB.LLAVE_TAB_GRAL
       WHERE ILT.COD_GRUPO_CIA = cCodGrupoCia_in
         AND ILT.COD_LOCAL = cCodLocal_in
         AND TB.COD_TAB_GRAL = 'MODELO_IMP_TERMICA'
         AND COD_APL = 'PTO_VENTA';
    RETURN curVta;
  END;

  /* ******************************************************************************************************* */
  --JMIRANDA 26/06/2009
  --PROCEDURE IMP_INGRESA_IMPRESORA_TERMICA( cCodGrupoCia_in   IN CHAR,
  --KMONCADA 13.05.2016 CAMBIO PARA QUE SE REGISTRE EL PISO DE DESPACHO EN QUE SE ENCUENTRA LA IMPRESORA
  PROCEDURE IMP_P_INSERT_IMP_TERMICA( cCodGrupoCia_in       IN CHAR,
                                      cCodLocal_in          IN CHAR,
                                      cDescImprLocalTerm_in IN CHAR,
                                      cTipoImprTermica_in   IN Char,
                                      cEstImprLocal_in      IN Char,
                             	        cCodUsu_in            IN Char,
                                      cNroPiso_in           IN CHAR DEFAULT NULL)--KMONCADA
  IS
      v_nNeoCod  NUMBER;
  BEGIN
      v_nNeoCod:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_IMPR_TERM);

        INSERT INTO VTA_IMPR_LOCAL_TERMICA (COD_GRUPO_CIA,
                                            COD_LOCAL,
                                            sec_impr_loc_term,
                                            desc_impr_local_term,
                                            tipo_impr_termica,
                                            est_impr_local,
                                            fec_crea_impr_local,
                                            usu_crea_impr_local,
                                            fec_mod_impr_local,
                                            usu_mod_impr_local,
                                            PISO_DESPACHO)
                       VALUES(cCodGrupoCia_in,
                              cCodLocal_in ,
                              v_nNeoCod,
                        		  cDescImprLocalTerm_in,
                              cTipoImprTermica_in,
                              cEstImprLocal_in,
                        		  SYSDATE,
                        		  cCodUsu_in,
                        		  Null,
                        		  Null,
                              CASE 
                               WHEN cNroPiso_in IS NOT NULL THEN
                                 TO_NUMBER(cNroPiso_in,'9990')
                               ELSE
                                 NULL
                             END);
  END;
  /* ******************************************************************************************************* */
  --JMIRANDA
  -- PROCEDURE IMP_MODIFICA_IMPRESORA_TERMICA( cCodGrupoCia_in   IN CHAR,
  --KMONCADA 13.05.2016 CAMBIO PARA QUE SE REGISTRE EL PISO DE DESPACHO EN QUE SE ENCUENTRA LA IMPRESORA
  PROCEDURE IMP_P_UPDATE_IMP_TERMICA(cCodGrupoCia_in       IN CHAR,
                                     cCodLocal_in          IN CHAR,
                                     nSecImprLocTerm_in    IN NUMBER,
                             	       cDescImprLocalTerm_in IN CHAR,
                                     cTipoImprTermica_in   IN Char,
                                     cEstImprLocalTerm_in  IN Char,
                             	       cCodUsu_in            IN CHAR, --Umodifica
                                     cNroPiso_in           IN CHAR DEFAULT NULL)--KMONCADA
  IS
  BEGIN
      UPDATE VTA_IMPR_LOCAL_TERMICA SET fec_mod_impr_local = SYSDATE,
               usu_mod_impr_local = cCodUsu_in,
               desc_impr_local_term = cDescImprLocalTerm_in,
               tipo_impr_termica = cTipoImprTermica_in,
               est_impr_local = cEstImprLocalTerm_in,
               PISO_DESPACHO = CASE 
                                 WHEN cNroPiso_in IS NOT NULL THEN
                                   TO_NUMBER(cNroPiso_in,'9990')
                                 ELSE
                                   NULL
                               END
         WHERE	COD_GRUPO_CIA  	  = cCodGrupoCia_in   AND
                COD_LOCAL		      = cCodLocal_in      AND
                SEC_IMPR_LOC_TERM  = nSecImprLocTerm_in;

END;
/********************************************************************************/
-- JMIRANDA    30/06/2009
--   FUNCTION IMP_TERMICA_EXIST(pCodCia       IN CHAR,
   FUNCTION IMP_F_VAR2_EXIST_IMP_TERMICA(pCodCia       IN CHAR,
                               pCodLocal     IN CHAR,
                               pDescripcion  IN Char)
  RETURN VARCHAR2
  IS
  VALOR  CHAR(1);
  CANT    NUMBER;
  BEGIN


      SELECT COUNT(*) INTO CANT
      FROM VTA_IMPR_LOCAL_TERMICA X
      WHERE X.COD_GRUPO_CIA=pCodCia
      AND X.COD_LOCAL=pCodLocal
      AND X.desc_impr_local_term Like pDescripcion;--||'%';

      IF(CANT>0)THEN
         VALOR:='S';
      ELSE
         VALOR:='N';
      END IF;

      RETURN VALOR;
  END;

   FUNCTION IMP_GET_MODELO(cCodGrupoCia_in       IN CHAR,
                          cCodCia_in     IN CHAR,
                          cCodLocal_in           IN CHAR,
                          nSecImpTerm_in    IN NUMBER)
   RETURN VARCHAR2
   IS
    v_vModelo VTA_IMPR_LOCAL.MODELO_IMPRESORA%TYPE;
   BEGIN

        SELECT NVL(MODELO_IMPRESORA,'DEFAULT')
             INTO v_vModelo
        FROM VTA_IMPR_LOCAL
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_CIA = cCodCia_in
            AND COD_LOCAL = cCodLocal_in
            AND SEC_IMPR_LOCAL = nSecImpTerm_in;
        RETURN v_vModelo;

   EXCEPTION
     WHEN OTHERS THEN
        RETURN '';
   END;

   FUNCTION IMP_LISTA_MODELOS(cCodGrupoCia_in       IN CHAR,
                          cCodCia_in     IN CHAR)
   RETURN FarmaCursor
   IS
    curListado FarmaCursor;
   BEGIN
    OPEN curListado FOR
    SELECT LLAVE_TAB_GRAL|| 'Ã' ||
            DESC_CORTA
    FROM PBL_TAB_GRAL
    WHERE COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'MODELO_IMP_TICKET';

    RETURN curListado;
   END;

   FUNCTION IMP_IMPRESORAS_IP(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              nIp_in          IN CHAR)
     RETURN FarmaCursor
   IS
     curListado FarmaCursor;
   BEGIN
      OPEN curListado FOR
      SELECT NVL(BOL.DESC_IMPR_LOCAL,'Sin asignacion') ||
             DECODE(BOL.RUTA_IMPR,NULL,'',' ('||BOL.RUTA_IMPR||')') || 'Ã' ||
             NVL(FAC.DESC_IMPR_LOCAL,'Sin asignacion') ||
             DECODE(FAC.RUTA_IMPR,NULL,'',' ('||FAC.RUTA_IMPR||')') || 'Ã' ||
             NVL(C.DESC_IMPR_LOCAL_TERM,'Sin asignacion') || 'Ã' ||
             -- KMONCADA 26.04.2016 CENTRO MEDICO
             NVL(D.DESC_TIP_LOCAL_VENTA, 'Sin asignación')
      FROM VTA_IMPR_IP A
      LEFT JOIN VTA_IMPR_LOCAL BOL on NVL(A.SEC_IMPR_LOCAL,0)=BOL.SEC_IMPR_LOCAL
      LEFT JOIN VTA_IMPR_LOCAL FAC on NVL(A.SEC_IMPR_LOCAL_FAC,0)=FAC.SEC_IMPR_LOCAL
      LEFT JOIN VTA_IMPR_LOCAL_TERMICA C  on NVL(A.SEC_IMPR_LOC_TERM,0)=C.SEC_IMPR_LOC_TERM   --JCG 26.06.2009
      -- KMONCADA 26.04.2016 ARCANGEL-CENTRO MEDICO
      LEFT JOIN MAE_TIP_LOCAL_VENTA D ON A.COD_TIP_LOCAL_VENTA = D.COD_TIP_LOCAL_VENTA
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_LOCAL = cCodLocal_in
        AND A.IP = nIp_in;

      RETURN curListado;
   END;

   --Author:Cesar Huanes
   --Descripción:Obtiene el secuencial de guias y de boletas
   --Fecha:15-04-2014
   FUNCTION GET_SEC_IMPR_GUIA_BOLETA(pCodGrupoCia  IN CHAR,
                                  pCodLocal     IN CHAR,
                                  pTipComp      IN CHAR,
                                  pIP           IN CHAR)

   RETURN VARCHAR2
   IS
     SEC_IMP VARCHAR(2);
     BEGIN

    IF  ( pTipComp='01' OR pTipComp='03' )  THEN
      SEC_IMP := PTOVENTA_CAJ.F_GET_SECUENCIAL_IMPRESORA(cCodGrupoCia_in => pCodGrupoCia,
                                                         cCodLocal_in => pCodLocal,
                                                         cTipoCP_in => pTipComp,
                                                         cIpPC_in => pIP);/*
    SELECT A.SEC_IMPR_LOCAL INTO SEC_IMP  FROM VTA_IMPR_LOCAL A, VTA_IMPR_IP B
    WHERE A.COD_GRUPO_CIA=B.COD_GRUPO_CIA AND
    A.COD_LOCAL=B.COD_LOCAL
    AND A.COD_GRUPO_CIA=pCodGrupoCia
    AND A.COD_LOCAL=pCodLocal
    AND A.TIP_COMP=pTipComp
    AND TRIM(B.IP)=TRIM(pIP);*/
    END IF;
    RETURN SEC_IMP;
    END;

  FUNCTION IMP_LISTA_TIPOS_COMP_MANUAL(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
		  SELECT TIP_COMP    || 'Ã' ||
             DESC_COMP
      FROM   VTA_TIP_COMP
      WHERE  COD_GRUPO_CIA     = cCodGrupoCia_in AND
			       tip_comp in ('01','02');
      RETURN curVta;
  END;

FUNCTION IMP_F_VAR_MAX_COMP(cCodGrupoCia_in   IN CHAR,
                            cCodLocal_in      IN CHAR,
                            nSecImprLocal_in  IN NUMBER) RETURN VARCHAR2
  AS
        vMaxComp number;
      vCurImpr vta_impr_local%rowtype;        
  begin
    
  SELECT CP.*
  INTO   vCurImpr
  FROM   VTA_IMPR_LOCAL CP
  WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    CP.COD_LOCAL = cCodLocal_in
  AND    CP.SEC_IMPR_LOCAL = nSecImprLocal_in;
      
          select max(Substr(cp.num_comp_pago, 4))
          into   vMaxComp
          from   vta_comp_pago cp
          where  cp.cod_grupo_cia = cCodGrupoCia_in
          and    cp.cod_local = cCodLocal_in
          and    cp.tip_comp_pago = vCurImpr.Tip_Comp
          and    cp.fec_crea_comp_pago >= to_date('01/11/2014','dd/mm/yyyy')
          and    cp.cod_tip_proc_pago is null; 
          
     return lpad(vMaxComp,'7','0');     
end;  
  FUNCTION IMP_P_VALIDA_CAMBIO_COMP(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                             	     nSecImprLocal_in  IN NUMBER,
                             	     cNumComp_in       IN NUMBER,
                             	     cCodUsu_in        IN CHAR) RETURN VARCHAR2
  AS
      v_cNumComp CHAR(7);
      
      V_ROL_VENDEDOR    VARCHAR2(10) := '009';
      V_ROL_JEFE_LOCAL  VARCHAR2(10) := '011';      
      V_ROL_OPERADOR    VARCHAR2(10) := '000'; 
      
      vNumVendedor number;     
      vJefeLocal number;
      vOperador number;            
      
      vRowImpr vta_impr_local%rowtype;
      
      vResultado varchar2(30000);
      
      vMaxComp number;
      dif number;
      
      vDifCompOperador   number;
      vDifCompVendedor   number;
      vDifCompJefeLocal  number;            

      vPermite boolean := false;
      
      vCurImpr vta_impr_local%rowtype;
      
      vMsjOperador varchar2(4000);
      vMsjQF varchar2(4000);
            
  BEGIN
    
  select t.desc_larga
  into   vMsjOperador
  from  pbl_tab_gral t
  where  t.id_tab_gral = 673;
  
  select t.desc_larga
  into   vMsjQF
  from  pbl_tab_gral t
  where  t.id_tab_gral = 674;  
    
  SELECT CP.*
  INTO   vCurImpr
  FROM   VTA_IMPR_LOCAL CP
  WHERE  CP.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    CP.COD_LOCAL = cCodLocal_in
  AND    CP.SEC_IMPR_LOCAL = nSecImprLocal_in;

  if vCurImpr.Num_Comp_Min is not null then
    if  cNumComp_in < vCurImpr.Num_Comp_Min then
      vResultado := 'I';
      return vResultado;
    end if;  
  end if;

  
  select COUNT(1)
  into   vNumVendedor
  from   pbl_rol_usu r
  where  r.COD_GRUPO_CIA = cCodGrupoCia_in
  and    r.COD_LOCAL = cCodLocal_in
  and    r.SEC_USU_LOCAL = cCodUsu_in
  AND    R.COD_ROL = V_ROL_VENDEDOR;

  select COUNT(1)
  into   vJefeLocal
  from   pbl_rol_usu r
  where  r.COD_GRUPO_CIA = cCodGrupoCia_in
  and    r.COD_LOCAL = cCodLocal_in
  and    r.SEC_USU_LOCAL = cCodUsu_in
  AND    R.COD_ROL = V_ROL_JEFE_LOCAL;
  
  select COUNT(1)
  into   vOperador  
  from   pbl_rol_usu r
  where  r.COD_GRUPO_CIA = cCodGrupoCia_in
  and    r.COD_LOCAL = cCodLocal_in
  and    r.SEC_USU_LOCAL = cCodUsu_in
  AND    R.COD_ROL = V_ROL_OPERADOR;    
  
  BEGIN  
  SELECT TO_NUMBER(TRIM(R.LLAVE_TAB_GRAL),'9999990') INTO vDifCompOperador 
  FROM PBL_TAB_GRAL R WHERE R.ID_TAB_GRAL = 658;
  EXCEPTION 
  WHEN OTHERS THEN 
    vDifCompOperador := 9999999999;
  END;

  BEGIN  
  SELECT TO_NUMBER(TRIM(R.LLAVE_TAB_GRAL),'9999990') INTO vDifCompJefeLocal 
  FROM PBL_TAB_GRAL R WHERE R.ID_TAB_GRAL = 659;
  EXCEPTION 
  WHEN OTHERS THEN 
    vDifCompJefeLocal := 9999999999;
  END;
  
  BEGIN  
  SELECT TO_NUMBER(TRIM(R.LLAVE_TAB_GRAL),'9999990') INTO vDifCompVendedor 
  FROM PBL_TAB_GRAL R WHERE R.ID_TAB_GRAL = 660;
  EXCEPTION 
  WHEN OTHERS THEN 
    vDifCompVendedor := 9999999999;
  END;    
  

  
  
	    v_cNumComp:=Farma_Utility.COMPLETAR_CON_SIMBOLO(cNumComp_in,7,'0',POS_INICIO );
      
      select r.*
      into   vRowImpr
      from   VTA_IMPR_LOCAL r
      WHERE	COD_GRUPO_CIA  = cCodGrupoCia_in   
      AND   COD_LOCAL		   = cCodLocal_in      
      AND   SEC_IMPR_LOCAL = nSecImprLocal_in;

	  --ERIOS 13.05.2015 No se validan las guias
      if vRowImpr.Tip_Comp = '01' or
         vRowImpr.Tip_Comp = '02' --or vRowImpr.Tip_Comp = '03' 
		 then

          select max(Substr(cp.num_comp_pago, 4))
          into   vMaxComp
          from   vta_comp_pago cp
          where  cp.cod_grupo_cia = cCodGrupoCia_in
          and    cp.cod_local = cCodLocal_in
          and    cp.tip_comp_pago = vRowImpr.Tip_Comp
          and    cp.fec_crea_comp_pago >= to_date('01/11/2014','dd/mm/yyyy')
          and    cp.cod_tip_proc_pago is null;
          
          dif := abs(vMaxComp - cNumComp_in);
          
          vPermite := false;
          dbms_output.put_line('dif '||dif);
          IF vOperador > 0 THEN
             if dif > vDifCompOperador then
               vPermite := false;
             else  
               vPermite := true;
             end if;
          ELSE
            IF vJefeLocal > 0 THEN  
               if dif > vDifCompJefeLocal then
                 vPermite := false;
               else  
                 vPermite := true;
               end if;              
              ELSE
                IF vNumVendedor > 0 THEN
                 if dif > vDifCompVendedor then
                   vPermite := false;
                 else  
                   vPermite := true;
                 end if;                  
                ELSE
                  vPermite := false;
                END IF;
            END IF;
          END IF;
          
          if vPermite = true then
            vResultado := 'N';
          else
            -- valida que ROL SOLICITARA PARA A EL CAMBIO
            vResultado := 'N';

                  
            if dif <= vDifCompVendedor  then
              vResultado := V_ROL_VENDEDOR ||'@'||'<html>
 <table border="0" style="width:100%">
  <tr>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td><b><center><FONT FACE="arial" SIZE=6 COLOR="red">El Comprobante que intenta modificar excede el valor máximo permitido.</font></center></b></td>
    <td></td>
  </tr>
<tr>
    <td></td>
    <td><center><FONT FACE="arial" SIZE=5 COLOR="red">Comuniquese con el Vendedor para autorizar el cambio.</font></center></td>
    <td></td>
  </tr>  
  <tr>
    <td>	</td>
    <td></td>
    <td></td>
  </tr>
</table> 
</html>';
            else
               if dif <= vDifCompJefeLocal then
                 vResultado := V_ROL_JEFE_LOCAL||'@'||vMsjQF;
               else  
               if dif <= vDifCompOperador then                 
                 vResultado := V_ROL_OPERADOR||'@'||vMsjOperador;
                else
                  vResultado := V_ROL_OPERADOR||'@'||vMsjOperador;
                  /*'<html>
 <table border="0" style="width:100%">
  <tr>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td><b><center><FONT FACE="arial" SIZE=6 COLOR="red">El Comprobante que intenta modificar excede el valor máximo permitido.</font></center></b></td>
    <td></td>
  </tr>
<tr>
    <td></td>
    <td><center><FONT FACE="arial" SIZE=5 COLOR="red">Comuniquese con el jefe del local para autorizar el cambio.</font></center></td>
    <td></td>
  </tr>  
  <tr>
    <td>	</td>
    <td></td>
    <td></td>
  </tr>
</table> 
</html>';*/
                  end if; 
               end if;
            end if;
          end if;
          
      else
        vResultado := 'N';
      end if;
      
      return vResultado;
  END;
 /* *************************************************************************** */  
FUNCTION F_VALIDA_APERTURA_CAJA(cCodGrupoCia_in   IN CHAR,
                                cCodLocal_in      IN CHAR,
                             	  nSecUsu_in        IN char) RETURN VARCHAR2
  AS
  vResultado varchar2(2):='N';
  vValor number;
BEGIN
    
    select to_number(t.llave_tab_gral,'999990')
    into   vValor
    from   pbl_tab_gral t
    where  t.id_tab_gral = 676;

    select decode(count(1),0,'S','N')
    into   vResultado
    from   ce_mov_caja m
    where  m.cod_grupo_cia = cCodGrupoCia_in
    and    m.cod_local = cCodLocal_in
    and    m.sec_usu_local = nSecUsu_in
    and    m.tip_mov_caja = 'C'
	and    m.fec_dia_vta >= to_date('01/05/2015','dd/mm/yyyy')
    and    m.fec_dia_vta< trunc(sysdate-vValor)-1/24/60/60
    and    m.ind_vb_cajero = 'N';
  
 return vResultado;
END;      
  
FUNCTION F_CUR_CAJA_SIN_VB(cCodGrupoCia_in   IN CHAR,
                           cCodLocal_in      IN CHAR,
                           nSecUsu_in        IN char) RETURN FarmaCursor
  AS
  vResultado varchar2(2):='N';
  vValor number;
  curVta FarmaCursor;
  BEGIN

    
    select to_number(t.llave_tab_gral,'999990')
    into   vValor
    from   pbl_tab_gral t
    where  t.id_tab_gral = 676;

      OPEN curVta FOR
          select to_char(m.fec_dia_vta,'dd/mm/yyyy')  || 'Ã' ||
                 m.num_turno_caja|| 'Ã' ||
                 'Sin Visto Bueno Cajero'
          into   vResultado
          from   ce_mov_caja m
          where  m.cod_grupo_cia = cCodGrupoCia_in
          and    m.cod_local = cCodLocal_in
          and    m.sec_usu_local = nSecUsu_in
          and    m.tip_mov_caja = 'C'
		  and    m.fec_dia_vta >= to_date('01/05/2015','dd/mm/yyyy')
          and    m.fec_dia_vta< trunc(sysdate-vValor)-1/24/60/60
          and    m.ind_vb_cajero = 'N'
          order by m.fec_dia_vta asc;
  
    return curVta;
END; 

FUNCTION IMP_LISTA_SERIE_COMP(cCodGrupoCia_in IN CHAR,cCodLocal IN CHAR,cTipComp IN CHAR )
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
		  SELECT SER.NUM_SERIE_LOCAL || 'Ã' ||
             SER.NUM_SERIE_LOCAL
      FROM   VTA_SERIE_LOCAL SER
      WHERE  SER.COD_GRUPO_CIA = cCodGrupoCia_in 
             AND  SER.COD_LOCAL=cCodLocal
             AND  SER.TIP_COMP LIKE(cTipComp)     
           /*  AND  SER.NUM_SERIE_LOCAL NOT IN (SELECT DISTINCT NUM_SERIE_LOCAL 
                                         FROM VTA_IMPR_LOCAL
                                         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND COD_LOCAL = cCodLocal
                                           AND TIP_COMP LIKE( cTipComp))*/
             AND  SER.EST_SERIE_LOCAL='A';
             
             
			     
      RETURN curVta;
  END;     
  
  FUNCTION F_GET_TIPO_LOCAL_VENTA(cCodGrupoCia_in IN PBL_LOCAL.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in    IN PBL_LOCAL.COD_LOCAL%TYPE,
                                  cIpPc_in        IN VTA_IMPR_IP.IP%TYPE)
    RETURN FARMACURSOR IS
    curImpr FarmaCursor;
    vEditado CHAR(3);
    vTLVLocal PBL_LOCAL.COD_TIP_LOCAL_VENTA%TYPE;
    vTLVAuxLocal PBL_LOCAL.COD_TIP_LOCAL_VENTA%TYPE;
    vOrdLocal NUMBER;
    vOrdAuxLocal NUMBER;
  BEGIN
    
    SELECT A.COD_TIP_LOCAL_VENTA
    INTO vEditado
    FROM VTA_IMPR_IP A
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND IP = cIpPc_in;
    
    SELECT L.COD_TIP_LOCAL_VENTA, 
           A.COD_TIP_LOCAL_VENTA,
           CASE 
             WHEN vEditado = L.COD_TIP_LOCAL_VENTA OR vEditado IS NULL THEN 1 
             ELSE 2 
           END,
           CASE 
             WHEN vEditado = A.COD_TIP_LOCAL_VENTA  THEN 1
             ELSE 2 
           END
    INTO vTLVLocal, 
         vTLVAuxLocal,
         vOrdLocal,
         vOrdAuxLocal
    FROM PBL_LOCAL L,
         AUX_PBL_LOCAL_TIP_VENTA A
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
    AND L.COD_LOCAL = cCodLocal_in
    AND L.COD_GRUPO_CIA = A.COD_GRUPO_CIA(+)
    AND L.COD_LOCAL = A.COD_LOCAL(+);
    
    OPEN curImpr FOR
      SELECT M.COD_TIP_LOCAL_VENTA || 'Ã' ||
             M.DESC_TIP_LOCAL_VENTA
      FROM (
      SELECT COD_TIP_LOCAL_VENTA 
        FROM PBL_LOCAL
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
      UNION ALL
      SELECT COD_TIP_LOCAL_VENTA
        FROM AUX_PBL_LOCAL_TIP_VENTA A
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
      ) W,
      MAE_TIP_LOCAL_VENTA M
      WHERE M.COD_TIP_LOCAL_VENTA = W.COD_TIP_LOCAL_VENTA
      ORDER BY DECODE(W.COD_TIP_LOCAL_VENTA, vTLVLocal , vOrdLocal, vTLVAuxLocal, vOrdAuxLocal);
    RETURN curImpr;
  END;

  PROCEDURE P_ACT_IP_TIPO_LOCAL_VTA(cCodGrupoCia_in       IN PBL_LOCAL.COD_GRUPO_CIA%TYPE,
                                    cCodLocal_in          IN PBL_LOCAL.COD_LOCAL%TYPE,
                                    cIpPc_in              IN VTA_IMPR_IP.IP%TYPE,
                                    cCodTipoLocalVenta_in IN MAE_TIP_LOCAL_VENTA.COD_TIP_LOCAL_VENTA%TYPE,
                                    cUsuMod_in            IN VARCHAR2)
  IS
  BEGIN
    UPDATE VTA_IMPR_IP P
    SET P.COD_TIP_LOCAL_VENTA = cCodTipoLocalVenta_in,
        P.USU_MOD_IP =  cUsuMod_in,
        P.FEC_MOD_IP = SYSDATE
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
    AND P.COD_LOCAL = cCodLocal_in
    AND P.IP = cIpPc_in;
  END;
END;
/
