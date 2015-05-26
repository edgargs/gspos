CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CAJ" AS
TYPE FarmaCursor IS REF CURSOR;

	COD_NUM_SEC_MOV_CAJ   PBL_NUMERA.COD_NUMERA%TYPE := '010';
	ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';
	POS_INICIO		      CHAR(1):='I';
	TIP_MOV_APERTURA	  CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='A';
	TIP_MOV_CIERRE  	  CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='C';
	TIP_MOV_ARQUEO  	  CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='R';
	EST_PED_PENDIENTE  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='P';
	EST_PED_ANULADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='N';
	EST_PED_COBRADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='C';
	EST_PED_COB_NO_IMPR  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='S';
	COD_TIP_COMP_BOLETA    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='01';
	COD_TIP_COMP_FACTURA   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='02';
	COD_TIP_COMP_GUIA      VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='03';
	COD_TIP_COMP_NOTA_CRED VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='04';
 	COD_TIP_COMP_TICKET    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='05';

  COD_NUM_SEC_SOBRE PBL_NUMERA.COD_NUMERA%TYPE := '062';

	TIPO_COMPROBANTE_99		  CHAR(2):='99';

  C_CARACTER_INIC CHAR(1) := '$';
  C_ESTADO_ACTIVO CHAR(1) := 'A';
  C_ESTADO_EMITIDO CHAR(1) := 'E';

  C_ESTADO_ANULADO CHAR(1) := 'N';
  C_ESTADO_USADO CHAR(1) := 'U';

  C_C_COD_EAN_CUPON PBL_NUMERA.COD_NUMERA%TYPE := '045';

  C_C_SERIE_VTA_INSTITUCIONAL NUMBER := 500;
  C_C_COD_NUMERA_TRACE CHAR(3) := '028';
  COD_TIP_MON_SOLES    CHAR(2) := '01';
	COD_TIP_MON_DOLARES  CHAR(2) := '02';
  DESC_TIP_MON_SOLES   VARCHAR2(10) := 'SOLES';
	DESC_TIP_MON_DOLARES VARCHAR2(10) := 'DOLARES';

  COD_EXITO CHAR(2) := '00';        --ASOSA - 14/08/2014
  -- KMONCADA 01.09.2014
  TIPO_VTA_INSTITUCIONAL CHAR(2) := '03';
  TIPO_VTA_DELIVERY CHAR(2) := '02';
  TIPO_VTA_MESON CHAR(2) := '01';

  C_INICIO_MSG_1 VARCHAR2(1000) := '<html><head><style type="text/css">' ||
                                   '.titulo {font-size: 10;font-family:sans-serif;font-style: italic;}' ||
                                   '.cajero {font-size: 20;font-family: Arial, Helvetica, sans-serif;border-style: solid;} ' ||
                                   '.histcab {font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.historico{font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.msgfinal {font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.tip{font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.fila{border-style: solid;}' ||
                                   '</style>' || '</head>' || '<body>' ||
                                   '<table width="200" border="0">' ||
                                   '<tr>' || '<td>&nbsp;&nbsp;</td>' ||
                                   '<td>' ||
                                   '<table width="300"  border="1" cellspacing="0" cellpadding="5">';

/*********************************************************************************************************************************/
    --Descripcion: Obtiene la(s) cajas disponibles de un usuario
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
    FUNCTION CAJ_OBTIENE_CAJAS_DISP_USUARIO(cCodGrupoCia_in IN CHAR,
			 					  cCod_Local_in   IN CHAR,
								  cSecUsu_in 	  IN CHAR)
	RETURN CHAR;

  --Descripcion: Registra un movimiento de caja
  --Fecha        Usuario		Comentario
  --22/02/2006   MHUAYTA    Creación
  --17/07/2006   LMESIA     Modificacion
  PROCEDURE CAJ_REGISTRA_MOVIMIENTO_APER(cCodGrupoCia_in IN CHAR,
							  			                   cCod_Local_in   IN CHAR,
							  			                   nNumCaj_in 	   IN NUMBER,
							  			                   cSecUsu_in 	   IN CHAR,
							  			                   cCodUsu_in 	   IN CHAR,
                                         cIpMovCaja      IN CHAR);

  --Descripcion: Obtiene el secuencial del movimiento de apertura de una caja
  --Fecha        Usuario		Comentario
  --22/02/2006   MHUAYTA    Creación
  --17/07/2006   LMESIA     Modificacion
  FUNCTION CAJ_OBTENER_SEC_MOV_APERTURA(cCodGrupoCia_in IN CHAR,
	 		  							                  cCod_Local_in   IN CHAR,
										                    nNumCaj_in      IN NUMBER)
    RETURN CHAR;

	--Descripcion: Obtiene el nuevo turno de una caja
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
    FUNCTION CAJ_OBTENER_NUEVO_TURNO_CAJA(cCodGrupoCia_in IN CHAR,
			 							 cCod_Local_in 	 IN CHAR,
										 nNumCaj_in 	 IN NUMBER)
    RETURN NUMBER;

    --Descripcion: Obtiene el turno actual de una caja
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
    --11/02/2011  RHERRERA      Modificacion, obtenermos ultima fechas de Apertura y Utimo mov caja
	FUNCTION CAJ_OBTENER_TURNO_ACTUAL_CAJA(cCodGrupoCia_in IN CHAR,
   									  cCod_Local_in   IN CHAR,
									  nNumCaj_in      IN NUMBER)
    RETURN NUMBER;

	--Descripcion: Obtiene la fecha de apertura de caja
    --Fecha       Usuario		Comentario
    --20/04/2006  Paulo     Creación
	FUNCTION CAJ_OBTENER_FECHA_APERTURA(cCodGrupoCia_in IN CHAR,
   									  cCod_Local_in   IN CHAR,
									  nNumCaj_in      IN NUMBER)
    RETURN CHAR;

	--Descripcion: Obtiene el indicador de caja abierta
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
	FUNCTION CAJ_OBTENER_IND_CAJA_ABIERTA(cCodGrupoCia_in IN CHAR,
			 							 cCod_Local_in 	 IN CHAR,
										 nNumCaj_in 	 IN NUMBER)
    RETURN CHAR;

	--Descripcion: Valida que el usuario que va a hacer una operacion apertura/cierre
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
	PROCEDURE CAJ_VALIDA_OPERADOR_CAJA(cCodGrupoCia_in IN CHAR,
			  							cCod_Local_in 	IN CHAR,
										cSecUsu_in 		IN CHAR,
										cTipOp_in 		IN CHAR);

	--Descripcion: Obtiene la lista de las impresoras de las cajas de un usuario
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
	FUNCTION CAJ_LISTA_IMPRESORAS_CAJAS_USU(cCodGrupoCia_in IN CHAR,
			 						        cCod_Local_in 	IN CHAR,
									        cSecUsu_in 		IN CHAR)
    RETURN FarmaCursor;

	--Descripcion: Obtiene la lista de las impresoras de las cajas de un local
    --Fecha       Usuario		Comentario
    --02/06/2006  MHUAYTA     Creación
	 FUNCTION CAJ_LISTA_IMPRESORAS_CAJAS_LOC(cCodGrupoCia_in IN CHAR,
  		   						          cCod_Local_in   IN CHAR
								  		    )

    RETURN FarmaCursor;

	--Descripcion: Cambia los valores de serie y numero de comprobante de una impresora
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
	PROCEDURE CAJ_RECONFIG_IMPRESORA(cCodGrupoCia_in   IN CHAR,
			  				  cCod_Local_in    	IN CHAR,
							  nSecImprLocal_in 	IN NUMBER,
							  cNumSerieLocal_in IN CHAR,
							  cNumComp_in 		IN CHAR,
							  cCodUsu_in 	    IN CHAR);

	--Descripcion: Obtiene la lista de series disponibles para una caja
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
	--02/06/2006  MHUAYTA     Modificacion: Soporte para Administradores, Operadores Sistemas
	FUNCTION CAJ_LISTA_SERIES_IMPRESORA_CAJ(cCodGrupoCia_in IN CHAR,
			 							 cCod_Local_in 	 IN CHAR,
										 cSecUsu_in 	 IN CHAR,
										 cTipComp_in 	 IN CHAR)
    RETURN FarmaCursor;

	  --Descripcion: Obtiene los pedidos pendientes
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
    --17/03/2008  JCORTEZ    MODIFICACION
    --22/05/2008  DUBILLUZ    MODIFICACION
  FUNCTION CAJ_LISTA_CAB_PEDIDOS_PENDIENT(cCodGrupoCia_in IN CHAR,
  		   							      cCod_Local_in   IN CHAR,
                            cSec_Usu_in    IN CHAR)
    RETURN FarmaCursor;

	--Descripcion: Obtiene el detalle de un pedido pendiente
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
	FUNCTION CAJ_LISTA_DET_PEDIDOS_PENDIENT(cCodGrupoCia_in IN CHAR,
			 					   cCod_Local_in   IN CHAR,
								   cNum_Ped_Vta_in IN CHAR)
    RETURN FarmaCursor;

	--Descripcion: Cambia el estado de un pedido
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
	PROCEDURE CAJ_CAMBIA_ESTADO_PEDIDO(cCodGrupoCia_in IN CHAR,
			  					 cCod_Local_in 	 IN CHAR,
								 cNum_Ped_Vta_in IN CHAR,
								 cEst_in 		 IN CHAR,
								 cCodUsu_in 	  	 IN CHAR);

	--Descripcion: Obtiene la relacion de pedidos-comprobantes
    --Fecha       Usuario		Comentario
    --22/02/2006  MHUAYTA     Creación
	 FUNCTION CAJ_LISTA_RELACION_PEDIDO_COMP(cCodGrupoCia_in IN CHAR,
  		   					     cCod_Local_in   IN CHAR,
							     cSecMovCaja_in  IN CHAR,
                            cMostrar_in IN CHAR DEFAULT 'S')
     RETURN FarmaCursor;

  --Descripcion: Obtiene la informacion de la cabecera del pedido a traves del pedido diario en la fecha actual
  --Fecha       Usuario		Comentario
  --01/03/2006  LMESIA     	Creación
  --21/04/2014  RHERRERA    MODIFICACION: se obtiene la infor de la tabla cabecera
                                        -- y se quita los parametro no requeridos.
  FUNCTION CAJ_OBTIENE_INFO_PEDIDO(cCodGrupoCia_in  IN CHAR,
  		   						               cCodLocal_in    	IN CHAR,
								                   cNumPedDiario_in IN CHAR,
								                   cFecPedVta_in	IN CHAR,
                                   nValorSelCopago_in number default -1)
  	RETURN FarmaCursor;

 /* --Descripcion: Obtiene las formas de pago aceptadas por el local para el cobro del pedido
  --Fecha       Usuario		Comentario
  --01/03/2006  LMESIA     	Creación
  --06/09/2007  DUBILLUZ    Modificacion
  FUNCTION CAJ_OBTIENE_FORMAS_PAGO(cCodGrupoCia_in  IN CHAR,
  		   						   cCodLocal_in    	IN CHAR,
                       cIndPedConvenio  IN CHAR,
                       cCodConvenio     IN CHAR,
                       cCodCli_in       IN CHAR,
                       cNumPed_in       IN CHAR default '')
  RETURN FarmaCursor;
*/

  --Descripcion: Obtiene las aperturas del dia
  --Fecha       Usuario		Comentario
  --01/03/2006  MHUAYTA    	Creación
 FUNCTION CAJ_LISTA_APERTURAS_DIA(cCodGrupoCia_in IN CHAR,
  		   					   cCod_Local_in   IN CHAR
							   )
  RETURN FarmaCursor;

  --Descripcion: Obtiene los movimientos de una caja
  --Fecha       Usuario		Comentario
  --01/03/2006  MHUAYTA    	Creación
  --20/12/2007  DUBILLUZ    MODIFICACION
 FUNCTION CAJ_LISTA_MOVIMIENTOS_CAJA(cCodGrupoCia_in IN CHAR,
  		   					     cCod_Local_in   IN CHAR,
								 cFecDiaVta_in   IN CHAR)

  RETURN FarmaCursor;

  --Descripcion: Obtiene los Valores de un arqueo de caja
  --Fecha       Usuario		Comentario
  --01/03/2006  MHUAYTA     	Creación

   FUNCTION CAJ_OBTIENE_VALORES_ARQUEO(cCodGrupoCia_in IN CHAR,
  		   					   cCod_Local_in   IN CHAR,
							   cSecMovCaja_in  IN CHAR
							   )
  RETURN FarmaCursor;


  --Descripcion: Obtiene los Valores de formas de pago de un arqueo de caja
  --Fecha       Usuario		Comentario
  --01/03/2006  MHUAYTA     	Creación
  FUNCTION CAJ_OBTIENE_FORMAS_PAGO_ARQUEO(cCodGrupoCia_in IN CHAR,
  		   					   cCod_Local_in   	  IN CHAR,
							   cSecMovCaja_in 	  IN CHAR,
							   cTipOp_in 		  IN CHAR
							   )
  RETURN FarmaCursor;

  --Descripcion: Graba el comprobante de un pedido
  --Fecha       Usuario		Comentario
  --03/03/2006  LMESIA     	Creación
  PROCEDURE CAJ_GRABAR_COMPROBANTE_PAGO(pCodGrupoCia_in         IN CHAR,
                             	 	  	pCodLocal_in            IN CHAR,
  							 	 	  	cNumPedVta_in	        IN CHAR,
								 	  	cSecCompPago_in         IN CHAR,
									  	cTipCompPago_in         IN CHAR,
									  	cNumCompPago_in         IN CHAR,
									  	cSecMovCaja_in          IN CHAR,
									  	nCantItemsComPago_in	IN NUMBER,
									  	cCodCliLocal_in         IN CHAR,
									  	nValBrutoCompPago_in    IN NUMBER,
									  	nValNetoCompPago_in     IN NUMBER,
									  	nValDctoCompPago_in     IN NUMBER,
									  	nValAfectoCompPago_in   IN NUMBER,
									  	nValIgvCompPago_in      IN NUMBER,
									  	nValRedondeoCompPago_in IN NUMBER,
								 	  	cUsuCreaCompPago_in     IN CHAR);


  --Descripcion: Graba un arqueo o cierre de caja
  --Fecha        Usuario		Comentario
  --03/03/2006   MHUAYTA    Creación
  --08/06/2006   MHUAYTA    Modificación: Se añadió campos de notas de crédito
  --17/07/2006   LMESIA     Modificacion
  --11-Feb-2014  LLEIVA     Modificación
   FUNCTION CAJ_REGISTRA_ARQUEO_CAJA(cTipMov_in       IN CHAR,
                                     cCodGrupoCia_in  IN CHAR,
                                     cCod_Local_in    IN CHAR,
                                     nNumCaj_in       IN NUMBER,
                                     cSecUsu_in       IN CHAR,
                                     cIdUsu_in        IN CHAR,
                                     nCantBolEmi_in   IN NUMBER,
                                     nMonBolEmi_in    IN NUMBER,
                                     nCantFacEmi_in   IN NUMBER,
                                     nMontFacEmi_in   IN NUMBER,
                                     nCantGuiaEmi_in  IN NUMBER,
                                     nMonGuiaEmi_in   IN NUMBER,
                                     nCantBolAnu_in   IN NUMBER,
                                     nMonBolAnu_in    IN NUMBER,
                                     nCantFacAnu_in   IN NUMBER,
                                     nMonFacAnu_in    IN NUMBER,
                                     nCantGuiaAnu_in  IN NUMBER,
                                     nMonGuiaAnu_in   IN NUMBER,
                                     nCantBolTot_in   IN NUMBER,
                                     nMonBolTot_in    IN NUMBER,
                                     nCantFactTot_in  IN NUMBER,
                                     nMonFactTot_in   IN NUMBER,
                                     nCantGuiaTot_in  IN NUMBER,
                                     nMonGuiaTot_in   IN NUMBER,
                                     nMonTotGen_in    IN NUMBER,
                                     nMonTotAnu_in    IN NUMBER,
                                     nMonTot_in       IN NUMBER,
                                     nCantNCBol_in    IN NUMBER,
                                     nMonNCBol_in     IN NUMBER,
                                     nCantNCFact_in   IN NUMBER,
                                     nMonNCFact_in    IN NUMBER,
                                     nMonNCTot_in     IN NUMBER,
                                     cIpMovCaja       IN CHAR
                                     --LLEIVA 11-Feb-2014 Se añaden los campos de Ticket-Factura
                                     ,nCantTickFacEmi  IN NUMBER,
                                     nMonTickFacEmi   IN NUMBER,
                                     nCantTickFacAnul IN NUMBER,
                                     nMonTickFacAnul  IN NUMBER,
                                     nCantNCTickFac   IN NUMBER,
                                     nMonNCTickFac    IN NUMBER,
                                     nCantTickFacTot  IN NUMBER,
                                     nMonTickFacTot   IN NUMBER
                                     )
    RETURN CHAR;

  --Descripcion: Verifica q exista relacion entre caja e impresoras por tipo de comprobante
  --Fecha       Usuario		Comentario
  --06/03/2006  LMESIA     	Creación
  FUNCTION CAJ_VERIFICA_CAJA_IMPRESORAS(cCodGrupoCia_in IN CHAR,
						   	   		 	cCodLocal_in   	IN CHAR,
										cNumCajaPago_in IN NUMBER)
  	RETURN CHAR;

  --Descripcion: Verifica que la caja del usuario se encuentre abierta
  --Fecha       Usuario		Comentario
  --06/03/2006  LMESIA     	Creación
  FUNCTION CAJ_VERIFICA_CAJA_ABIERTA(cCodGrupoCia_in IN CHAR,
  		   						   	 cCodLocal_in    IN CHAR,
									 cSecUsuLocal_in IN CHAR)
  	RETURN NUMBER;

  --Descripcion: Verifica que la caja este relacionada al usuario
  --Fecha       Usuario		Comentario
  --06/03/2006  LMESIA     	Creación
  FUNCTION CAJ_OBTIENE_CAJA_USUARIO(cCodGrupoCia_in IN CHAR,
  		   						   	cCodLocal_in    IN CHAR,
									cSecUsuLocal_in IN CHAR)
  	RETURN NUMBER;

  --Descripcion: Obtiene el numero de secuencia de las impresoras de venta
  --Fecha       Usuario		Comentario
  --06/03/2006  LMESIA     	Creación
  FUNCTION CAJ_SECUENCIA_IMPRESORAS_VENTA(cCodGrupoCia_in IN CHAR,
  		   						   		  cCodLocal_in    IN CHAR,
										  cNumCajaPago_in IN NUMBER)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene la ruta de una impresora
  --Fecha       Usuario		Comentario
  --06/03/2006  LMESIA     	Creación
  FUNCTION CAJ_OBTIENE_RUTA_IMPRESORA(cCodGrupoCia_in  IN CHAR,
  		   						   	  cCodLocal_in     IN CHAR,
									  cSecImprLocal_in IN NUMBER)
  	RETURN CHAR;

	--Descripcion: Obtiene la relacion de comprobantes emitidos en un rango de fecha
  --Fecha       Usuario		Comentario
  --06/03/2006  MHUAYTA     	Creación

	FUNCTION CAJ_LISTA_COMPROBANT_RANGO_FEC(cCodGrupoCia_in IN CHAR,
  		   					    cCod_Local_in   IN CHAR,
								cFecIni_in 		IN CHAR,
								cFecFin_in 		IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Agrupa el detalle del pedido para la impresion de comprobantes
  --Fecha       Usuario		Comentario
  --06/03/2006  LMESIA     	Creación
  FUNCTION CAJ_AGRUPA_IMPRESION_DETALLE(cCodGrupoCia_in IN CHAR,
						   	   		 	cCodLocal_in    IN CHAR,
										cNumPedVta_in 	IN CHAR,
										nCantMaxImpr_in IN NUMBER,
                    cUsuModImpr_in  IN CHAR
										)
  	RETURN NUMBER;

	--Descripcion: Verifica si el pedido existe y se puede anular.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  PROCEDURE CAJ_VERIFICA_PEDIDO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,nMontoVta_in IN NUMBER);

  --Descripcion: Obtiene el listado de caomprobantes por pedido.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION CAJ_LISTA_CABECERA_PEDIDO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el nombre del cajero que cobro un pedido.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION INV_GET_CAJERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cSecMovCaja_in IN CHAR) RETURN VARCHAR2;

  PROCEDURE CAJ_CORRIGE_COMPROBANTES(cCodGrupoCia_in  	IN CHAR,
  								 codLocal_in     		IN CHAR,
								 cSecIni_in 	 		IN CHAR,
								 cSecFin_in  	 		IN CHAR,
								 cTipComp_in			IN CHAR,
								 nCant_in	 			IN NUMBER,
							 	 nIndDir				IN NUMBER,
								 cCodUsu_in				IN CHAR);

  --Descripcion: Obtiene el listado de caomprobantes por pedido.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION CAJ_LISTA_DETALLE_PEDIDO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cTipComp_in IN CHAR, cNumComp_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Verifica si el comprobante existe y se puede anular.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION CAJ_VERIFICA_COMPROBANTE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipComp_in IN CHAR, cNumComp_in IN CHAR,nMontoVta_in IN NUMBER) RETURN VARCHAR2;


    --Descripcion: Verifica si los comprobantes a correjir existen en la BD
    --Fecha       Usuario		Comentario
    --07/03/2006  MHUAYTA    	Creación
  FUNCTION CAJ_VERIFICAR_COMPROBANTES(cCodGrupoCia_in  IN CHAR,
  								  cCodLocal_in     IN CHAR,
								  cSecIni_in 	   IN CHAR,
								  cSecFin_in  	   IN CHAR,
								  cTipComp_in	   IN CHAR)
	RETURN NUMBER;

	--Descripcion: Verifica si los comprobantes a modificar como correccion existen en el sistema
    --Fecha       Usuario		Comentario
    --07/03/2006  MHUAYTA    	Creación
	 FUNCTION CAJ_VERIFICAR_CORRECCION_COMP(cCodGrupoCia_in IN CHAR,
  								        cCodLocal_in    IN CHAR,
								 	 	cSecIni_in     	IN CHAR,
								 	 	cSecFin_in     	IN CHAR,
									 	cTipComp_in	   	IN CHAR,
								 	 	nCant_in	 	IN NUMBER,
							 	 	 	cIndDir_in	    IN CHAR)
	RETURN NUMBER;

	--Descripcion: Efectua la correccion de series de comprobantes
    --Fecha       Usuario		Comentario
    --07/03/2006  MHUAYTA    	Creación

	PROCEDURE CAJ_CORRIGE_SERIES( cCodGrupoCia_in  IN CHAR,
  						      cCodLocal_in     IN CHAR,
						   	  cSecIniA_in 	   IN CHAR,
						   	  cSecFinA_in 	   IN CHAR,
						   	  cSecIniB_in 	   IN CHAR,
						   	  cSecFinB_in 	   IN CHAR,
						   	  cTipComp_in	   IN CHAR,
							  cCodUsu_in	   IN CHAR);

  --Descripcion: Obtiene estado del pedido
  --Fecha       Usuario		Comentario
  --08/03/2006  LMESIA     	Creación
  FUNCTION CAJ_OBTIENE_ESTADO_PEDIDO(cCodGrupoCia_in IN CHAR,
  		   						   	 cCodLocal_in    IN CHAR,
									 cNumPedVta_in 	 IN CHAR)
  	RETURN CHAR;

  --Descripcion: Obtiene el indicador de caja abierta y bloquea el registro
  --Fecha       Usuario		Comentario
  --08/03/2006  LMESIA     	Creación
  FUNCTION CAJ_IND_CAJA_ABIERTA_FORUPDATE(cCodGrupoCia_in IN CHAR,
  		   						   	 	  cCodLocal_in    IN CHAR,
									 	  cSecUsuLocal_in IN CHAR,
										  cSecMovCaja_in  IN CHAR)
  	RETURN CHAR;

  --Descripcion: Obtiene la secuencia de movimiento de caja
  --Fecha       Usuario		Comentario
  --08/03/2006  LMESIA     	Creación
  FUNCTION CAJ_OBTIENE_SEC_MOV_CAJA(cCodGrupoCia_in IN CHAR,
  		   						   	cCodLocal_in    IN CHAR,
									nNumCajaPago_in IN NUMBER)
  	RETURN CHAR;

  --Descripcion: Obtiene la fecha de modificacion de la caja (movimiento de caja)
  --Fecha       Usuario		Comentario
  --08/03/2006  LMESIA     	Creación
  FUNCTION CAJ_OBTIENE_FECHA_MOV_CAJA(cCodGrupoCia_in IN CHAR,
  		   						   	  cCodLocal_in    IN CHAR,
									  nNumCajaPago_in IN NUMBER)
  	RETURN CHAR;

  --Descripcion: Cobra el pedido generando los comprobantes de pago necesarios
  --Fecha       Usuario		Comentario
  --08/03/2006  LMESIA     	Creación
  --19/04/2007  LREQUE     	Modificación
  --14/08/2013  LLEIVA      Modificación
  FUNCTION CAJ_COBRA_PEDIDO(cCodGrupoCia_in 	   IN CHAR,
						   	             cCodLocal_in    	   IN CHAR,
							               cNumPedVta_in   	   IN CHAR,
							               cSecMovCaja_in    	 IN CHAR,
							               cCodNumera_in 	 	   IN CHAR,
							               cTipCompPago_in	   IN CHAR,
							               cCodMotKardex_in    IN CHAR,
						   	             cTipDocKardex_in    IN CHAR,
							               cCodNumeraKardex_in IN CHAR,
							               cUsuCreaCompPago_in IN CHAR,
                             cDescDetalleForPago_in IN CHAR DEFAULT ' ',
                             cPermiteCampana     IN CHAR DEFAULT 'N',
                             cDni_in             IN CHAR  DEFAULT NULL) RETURN CHAR;

  --Descripcion: Actualiza el stock del producto cuando se cobre un pedido Y GRABA KARDEX
  --Fecha       Usuario		Comentario
  --09/03/2006  LMESIA    CreaciÃ³n
  --21/11/2007  dubilluz   modificado
  PROCEDURE CAJ_ACTUALIZA_STK_PROD_DETALLE(cCodGrupoCia_in 	   IN CHAR,
						   	 			   cCodLocal_in    	   IN CHAR,
							 			   cNumPedVta_in   	   IN CHAR,
										   cCodMotKardex_in    IN CHAR,
						   	  			   cTipDocKardex_in    IN CHAR,
										   cCodNumeraKardex_in IN CHAR,
							 			   cUsuModProdLocal_in IN CHAR);

  --Descripcion: Inserta una forma de pago por pedido
  --Fecha       Usuario		Comentario
  --09/03/2006  LMESIA     	Creación
  PROCEDURE CAJ_GRABAR_FORMA_PAGO_PEDIDO(cCodGrupoCia_in 	 	     IN CHAR,
	                                   	   cCodLocal_in    	 	     IN CHAR,
										                     cCodFormaPago_in   	   IN CHAR,
									   	                   cNumPedVta_in   	 	     IN CHAR,
									   	                   nImPago_in		 		       IN NUMBER,
										                     cTipMoneda_in			     IN CHAR,
									  	                   nValTipCambio_in 	 	   IN NUMBER,
								   	  	                 nValVuelto_in  	 	     IN NUMBER,
								   	  	                 nImTotalPago_in 		     IN NUMBER,
									  	                   cNumTarj_in  		 	     IN CHAR,
									  	                   cFecVencTarj_in  		   IN CHAR,
									  	                   cNomTarj_in  	 		     IN CHAR,
                                         cCanCupon_in  	 		     IN NUMBER,
									  	                   cUsuCreaFormaPagoPed_in IN CHAR);

	--Descripcion: Almacena los valores de forma de pago para un movimiento de caja
  --Fecha        Usuario		Comentario
  --22/02/2006   MHUAYTA    Creación
  --17/07/2006   LMESIA     Modificacion
  PROCEDURE CAJ_ALMACENAR_VALORES_COMP(cCodGrupooCia_in IN CHAR,
   			 							                 cCodLocal_in     IN CHAR,
										                   cSecMovCaja_in   IN CHAR,
										                   cTipOp_in        IN CHAR);

  --Descripcion: Obtiene Informacion de la agrupacion de comprobantes
  --Fecha       Usuario		Comentario
  --10/03/2006  LMESIA     	Creación
  FUNCTION CAJ_INFO_DETALLE_AGRUPACION(cCodGrupoCia_in IN CHAR,
  		   						   	   cCodLocal_in    IN CHAR,
									   cNumPedVta_in   IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene Informacion (detalle del pedido) para la impresion del comprobante
  --Fecha       Usuario		Comentario
  --10/03/2006  LMESIA     	Creación
  --10/01/2007  LREQUE		Modificación
  FUNCTION CAJ_INFO_DETALLE_IMPRESION(cCodGrupoCia_in  IN CHAR,
  		   						   	  cCodLocal_in     IN CHAR,
									  cNumPedVta_in    IN CHAR,
									  cSecGrupoImpr_in IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene Informacion de los totales por comprobante
  --Fecha       Usuario		Comentario
  --10/03/2006  LMESIA     	Creación
  FUNCTION CAJ_INFO_TOTALES_COMPROBANTE(cCodGrupoCia_in IN CHAR,
  		   						   	   	cCodLocal_in    IN CHAR,
									   	cNumPedVta_in   IN CHAR,
										cSecCompPago_in	IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Actualiza la secuencia de comprobante de pago con el tipo y numero de comprobante impreso
  --Fecha       Usuario		Comentario
  --10/03/2006  LMESIA     	Creación
  -- 19.08.2014 LTAVARA   MODIFICACION
  PROCEDURE CAJ_ACTUALIZA_COMPROBANTE_IMPR(cCodGrupoCia_in 	  IN CHAR,
  		   						   	   	   cCodLocal_in    	  IN CHAR,
									   	   cNumPedVta_in   	  IN CHAR,
										   cSecCompPago_in 	  IN CHAR,
										   cTipCompPago_in 	  IN CHAR,
										   cNumCompPago_in 	  IN CHAR,
										   cUsuModCompPago_in IN CHAR
                       );

  --Descripcion: Obtiene el numero  de comprobante donde se va a imprimir el detalle del pedido
  --Fecha       Usuario		Comentario
  --10/03/2006  LMESIA     	Creación
  FUNCTION CAJ_OBTIENE_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in  IN CHAR,
  		   						   	   	  cCodLocal_in     IN CHAR,
									   	  cSecImprLocal_in IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Actualiza el numero de comprobante en la impresora local
  --Fecha       Usuario		Comentario
  --10/03/2006  LMESIA     	Creación
  PROCEDURE CAJ_ACTUALIZA_IMPR_NUM_COMP(cCodGrupoCia_in 	IN CHAR,
  		   						   	   	cCodLocal_in    	IN CHAR,
										cSecImprLocal_in 	IN CHAR,
										cUsuModImprLocal_in IN CHAR);

  --Descripcion: Actualiza el estado de la cabecera del pedido
  --Fecha       Usuario		Comentario
  --14/03/2006  LMESIA     	Creación
  PROCEDURE CAJ_ACTUALIZA_ESTADO_PEDIDO(cCodGrupoCia_in 	IN CHAR,
  		   						   	   	cCodLocal_in    	IN CHAR,
										cNumPedVta_in 		IN CHAR,
										cEstPedVta_in		IN CHAR,
										cUsuModPedVtaCab_in IN CHAR);

  --Descripcion: Lista la cabecera de los pedidos con estado S (pedidos cobrados Sin impresion de comprobante)
  --Fecha       Usuario		Comentario
  --14/03/2006  LMESIA     	Creación
  FUNCTION CAJ_LISTA_CAB_PEDIDOS_ESTADO_S(cCodGrupoCia_in IN CHAR,
  		   						   	  	  cCodLocal_in    IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Lista el detalle de un pedido con estado S (pedido cobrado Sin impresion de comprobante)
  --Fecha       Usuario		Comentario
  --14/03/2006  LMESIA     	Creación
  FUNCTION CAJ_LISTA_DET_PEDIDO_ESTADO_S(cCodGrupoCia_in IN CHAR,
  		   						   	  	 cCodLocal_in    IN CHAR,
										 cNumPedVta_in	 IN CHAR)
  	RETURN FarmaCursor;

 --Descripcion: Obtiene los datos de un movimientos de caja y de su cajero
  --Fecha       Usuario		Comentario
  --20/03/2006  MHUAYTA     	Creación
  FUNCTION CAJ_OBTIENE_INFO_CAJERO(cCodGrupoCia_in IN CHAR,
  		   						   cCodLocal_in    IN CHAR,
								   cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene los Valores de Comprobantes (arqueo) para consulta
  --Fecha       Usuario		Comentario
  --22/03/2006  MHUAYTA     	Creación
  --08/06/2006  MHUAYTA     	Modificacion: Se añadieron campos de Notas de Credito
  FUNCTION CAJ_OBTIENE_VAL_ARQUEO_CONSULT(cCodGrupoCia_in IN CHAR,
  		   					           cCod_Local_in   IN CHAR,
							           cSecMovCaja_in  IN CHAR
							            )
  RETURN FarmaCursor;

  --Descripcion: Obtiene los Valores de formas de pago (arqueo) para consulta
  --Fecha       Usuario		Comentario
  --22/03/2006  MHUAYTA     	Creación
  FUNCTION CAJ_DETALLES_FORM_PAGO_CONSULT(cCodGrupoCia_in  IN CHAR,
  		   					     		   cCod_Local_in   IN CHAR,
							     		   cSecMovCaja_in  IN CHAR
								 		   )
  RETURN FarmaCursor;

  --Descripcion: Actualiza los valores del cliente en la cabecera del pedido
  --Fecha       Usuario		Comentario
  --22/03/2006  LMESIA     	Creación
  PROCEDURE CAJ_ACTUALIZA_CLI_PEDIDO(cCodGrupoCia_in 	 IN CHAR,
  		   						   	 cCodLocal_in    	 IN CHAR,
									 cNumPedVta_in		 IN CHAR,
									 cCodCliLocal_in 	 IN CHAR,
									 cNomCliPed_in 	 	 IN CHAR,
									 cDirCliLocal_in 	 IN CHAR,
									 cRucCliPed_in 	 	 IN CHAR,
									 cUsuModPedVtaCab_in IN CHAR);

  --Descripcion: Obtiene los datos de cabecera del reporte Comprobante-Pedidos
  --Fecha       Usuario		Comentario
  --22/03/2006  MHUAYTA     	Creación

	 FUNCTION CAJ_LISTA_PEDIDOS_COMPROB_CAB(cCodGrupoCia_in    IN CHAR,
  		   					  	     cCod_Local_in      IN CHAR,
							         cSecMovCaja_in     IN CHAR)
     RETURN FarmaCursor;

  --Descripcion: Verifica si existen pedidos pendientes despues que X minutos
  --Fecha       Usuario		Comentario
  --22/03/2006  LMESIA     	Creación
  FUNCTION CAJ_VERIFICA_PED_PEND_ANUL(cCodGrupoCia_in IN CHAR,
  		   						   	  cCodLocal_in    IN CHAR,
									  nMinutos_in	  IN NUMBER)
    RETURN NUMBER;

  --Descripcion: Anula los pedidos pendientes despues de X minutos
  --Fecha       Usuario		Comentario
  --22/03/2006  LMESIA     	Creación
  PROCEDURE CAJ_ANULA_PED_PEND_MASIVO(cCodGrupoCia_in 	  IN CHAR,
  		   						   	  cCodLocal_in    	  IN CHAR,
									  nMinutos_in	  	  IN NUMBER,
									  cUsuModPedVtaCab_in IN CHAR);

  --Descripcion: Determina si una IP esta habilitada para efectuar configuacion de impresoras
  --Fecha       Usuario		Comentario
  --04/04/2006  MHUAYTA    	Creación
	FUNCTION CAJ_VERIFICA_IP_VALIDA(cCodGrupoCia_in     IN CHAR,
  		   					  	     cCod_Local_in      IN CHAR,
							         cDirIP				IN CHAR)
     RETURN NUMBER;

  FUNCTION CAJ_VERIFICA_NUM_COMP(cCodGrupoCia_in IN CHAR,
  		   						 cCodLocal_in IN CHAR,
								 cSecImpLocal_in  IN CHAR,
								 nCantComprobantes_in IN NUMBER,
                 cNumPedVta_in IN CHAR DEFAULT NULL)
    RETURN CHAR;


  --Descripcion: Obtiene los datos de los vendedores de un pedido
  --Fecha       Usuario		Comentario
  --03/05/2006  MHUAYTA    	Creación
	FUNCTION CAJ_OBTIENE_INFO_VENDEDOR(cCodGrupoCia_in IN CHAR,
  		   						       cCodLocal_in    IN CHAR,
								       cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor;

  PROCEDURE CAJ_ACT_KARDEX_COMPROBANTE(cCodGrupoCia_in 	  IN CHAR,
	   						   	   	   cCodLocal_in    	  IN CHAR,
								   	   cNumPedVta_in   	  IN CHAR,
									   cSecCompPago_in 	  IN CHAR,
									   cTipCompPago_in 	  IN CHAR,
									   cNumCompPago_in 	  IN CHAR,
									   cUsuModKardex_in   IN CHAR);

  FUNCTION CAJ_PEDIDO_DEL_CAJ(cCodGrupoCia_in IN CHAR,
  		   					  	         cCod_Local_in   IN CHAR,
							                 cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: verifica si se puede abrir una caja
  --Fecha       Usuario		Comentario
  --04/09/2006  paulo    	Creación
  PROCEDURE CE_INVALIDA_APER_CAJ(cCodGrupoCia_in	IN CHAR,
  		   						              cCodLocal_in	  IN CHAR);

  --Descripcion: obtiene el valor maximo q puede ingresar para la configuracio de comprobantes
  --Fecha       Usuario		Comentario
  --14/09/2006   paulo    	Creación
  --17/12/2007   dubilluz   Modificacion
  FUNCTION  CAJ_VALOR_MAX_CONFIG_COMP(cCodGrupoCia_in IN CHAR,
  		   					  	                cCodLocal_in   IN CHAR,
                                      cTipoDocumento_in IN CHAR)
  RETURN CHAR;

  --Descripcion: Valida la forma de pago cupon y si devuelve monto 0.00 no se hace
  --             el cobro; caso contrario contrario, se cobra con esa forma de pago
  --Fecha       Usuario		Comentario
  --14/09/2006  paulo    	Creación
  FUNCTION CAJ_VALIDA_FORMA_PAGO_CUPON(cCodGrupoCia_in  IN CHAR,
						   	                       cCodLocal_in     IN CHAR,
							                         cNumPedVta_in    IN CHAR,
							                         cCodFormaPago_in IN CHAR,
                                       nCantCupon_in    IN NUMBER)
    RETURN NUMBER;

  --Descripcion: Obtiene secuencia de impresora de venta institucional por tipo de comprobante
  --Fecha       Usuario		Comentario
  --13/12/2006  paulo    	Creación
  FUNCTION CAJ_SECUENCIA_IMPR_INSTI(cCodGrupoCia_in IN CHAR,
  		   						   		          cCodLocal_in    IN CHAR,
										                cNumCajaPago_in IN NUMBER,
                                    cTipComp_in     IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Verifica el total de la cabecera contra el total de formas de pago
  --Fecha       Usuario	  Comentario
  --31/01/2007  LMesia    Creación
  --24/04/2007  LReque	  Modifación
  --27/12/2007  dubilluz  modificacion
  FUNCTION CAJ_VERIFICA_TOTAL_PED_FOR_PAG(cCodGrupoCia_in IN CHAR,
					  cCodLocal_in    IN CHAR,
					  cNumPedVta_in   IN CHAR,
                                          cDescDetalleForPago_in IN CHAR DEFAULT ' ') RETURN CHAR;

  --Descripcion: Verifica existencia de productos virtuales dentro del pedido
  --Fecha       Usuario		Comentario
  --10/01/2007  LMesia    Creación
  FUNCTION CAJ_VERIFICA_PROD_VIRTUALES(cCodGrupoCia_in IN CHAR,
  		   						   	               cCodLocal_in    IN CHAR,
									                     cNumPedVta_in   IN CHAR)
    RETURN NUMBER;

  --Descripcion: Obtiene la info del detalle de venta de los productos virtuales
  --Fecha       Usuario		Comentario
  --12/01/2007  LMesia    Creación
  FUNCTION CAJ_OBT_INFO_PROD_VIRTUAL(cCodGrupoCia_in IN CHAR,
  		   						   		           cCodLocal_in    IN CHAR,
										                 cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene el numero de trace de la tabla numera
  --             Es un proceso autonomo
  --Fecha       Usuario		Comentario
  --15/01/2007  LMesia    Creación
  FUNCTION OBTENER_NUMERACION_TRACE(cCodGrupoCia_in	IN CHAR,
							                      cCodLocal_in  	IN CHAR)
    RETURN NUMBER;


  --Descripcion: Actualiza la info del detalle del pedido virtual
  --Fecha       Usuario		Comentario
  --16/01/2007  LMesia    Creacion
  --27/09/2007  ERIOS     Se agregar campos para Bprepaid
  PROCEDURE CAJ_ACT_INFO_DET_PED_VIRTUAL(cCodGrupoCia_in IN CHAR,
							                           cCodLocal_in  	 IN CHAR,
                                         cNumPedVta_in   IN CHAR,
                                         cCodProd_in     IN CHAR,
							                           cNumTrace_in  	 IN CHAR,
                                         cCodAproba_in   IN CHAR,
                                         cNumTarjVir_in	 IN CHAR,
                                         cNumPin_in      IN CHAR,
                                         cUsuMod_in      IN CHAR,
                                         cFechaTX_in IN CHAR,
                                         cHoraTX_in IN CHAR,
                                         cDatosImprimir_in IN VARCHAR2);

  --Descripcion: Obtiene Valores de Proveedor NAVSAT para Recarga
  --Fecha       Usuario		Comentario
  --17/01/2007  LMesia    Creación
  FUNCTION CAJ_OBTIENE_VALORES_RECARGA
    RETURN FarmaCursor;

  --Descripcion: Obtiene Valores de Proveedor NAVSAT para Tarjeta
  --Fecha       Usuario		Comentario
  --17/01/2007  LMesia    Creación
  FUNCTION CAJ_OBTIENE_VALORES_TARJETA
    RETURN FarmaCursor;

  --Descripcion: Actualiza la info del detalle del pedido ANULADO virtual
  --Fecha       Usuario		Comentario
  --22/01/2007  LMesia    Creación
  PROCEDURE CAJ_ACT_INFO_DET_PED_VIR_ANUL(cCodGrupoCia_in IN CHAR,
							                            cCodLocal_in  	IN CHAR,
                                          cNumPedVtaOrigen_in IN CHAR,
							                            cNumTrace_in  	IN CHAR,
                                          cCodAproba_in   IN CHAR,
                                          cUsuMod_in      IN CHAR);

  --Descripcion: Lista la cabecera de los pedidos virtuales
  --Fecha       Usuario		Comentario
  --23/01/2007  LMesia    Creación
  FUNCTION CAJ_LISTA_CAB_PED_VIRTUALES(cCodGrupoCia_in IN CHAR,
  		   						                   cCod_Local_in   IN CHAR,
								                       cSecMovCaja_in  IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista el detalle de un pedido virtual
  --Fecha       Usuario		Comentario
  --23/01/2007  LMesia    Creación
  FUNCTION CAJ_LISTA_DET_PED_VIRTUALES(cCodGrupoCia_in IN CHAR,
  		   						                   cCod_Local_in   IN CHAR,
								                       cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene el tipo de producto virtual de un pedido
  --Fecha       Usuario		Comentario
  --25/01/2007  LMesia    Creación
  FUNCTION CAJ_OBTIENE_TIPO_PROD_VIR_PED(cCodGrupoCia_in IN CHAR,
  		   						   	                 cCodLocal_in    IN CHAR,
									                       cNumPedVta_in   IN CHAR)
    RETURN CHAR;

  --Descripcion: OBTIENE LA FORMA DE PAGO DEL PEDIDO CONVENIO
  --Fecha       Usuario		Comentario
  --20/03/2007  PAULO    Creación
  FUNCTION CAJ_LISTA_FORMA_PAGO_CONV(cCodGrupoCia_in IN CHAR,
  		   						   	             cCodLocal_in    IN CHAR,
									                   cNumPedVta_in 	IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: OBTIENE LA FORMA DE PAGO DEL PEDIDO CONVENIO
  --Fecha       Usuario		Comentario
  --26/07/2007  DUBILLUZ      Creación
  FUNCTION OBTIENE_COD_FORMA_PAGO(cCodGrupoCia_in IN CHAR,
                                  cCodConv_in   CHAR) RETURN CHAR ;

  --Descripcion: VALIDA SI EXISTE CREDITO DISPONIBLE DEL CLIENTE
  --Fecha       Usuario		Comentario
  --09/09/2007  DUBILLUZ 	CREACION
  FUNCTION CAJ_VERIFICA_CREDITO_CONVENIO(cCodGrupoCia_in  IN CHAR,
  		   						   cCodLocal_in    	IN CHAR,
                       vIdConvenio      IN VARCHAR2)
    RETURN CHAR;

  --Descripcion: retorna las formas de Pago de acuerdo al Convenio
  --Fecha       Usuario		Comentario
  --06/09/2007  DUBILLUZ 	Creación
  --25/09/2007  DUBILLUZ  MODIFICACION : cambio por execute
  --23/01/2008  DUBILLUZ  MODIFICACION
  --12/03/2008 JCORTEZ    MODIFICACI0N :Solo forma de pago por covenio
  /*FUNCTION CAJ_GET_FORMAS_PAGO_CONVENIO(cCodGrupoCia_in  IN CHAR,
                    		   						   cCodLocal_in    	IN CHAR,
                                         cIndPedConvenio  IN CHAR,
                                         cCodConvenio     IN CHAR,
                                         cCodCli_in       IN CHAR) RETURN FarmaCursor ;
*/
  --Descripcion: VALIDA SI EXISTE CREDITO DISPONIBLE DEL CLIENTE
  --Fecha       Usuario		Comentario
  --10/09/2007  DUBILLUZ 	CREACION
  --24/09/2007  DUBILLUZ  MODIFICACION
  --25/09/2007  DUBILLUZ  MODIFICACION : POR EXECUTE
  FUNCTION CAJ_VALIDA_SALDO_CREDITO(cCodGrupoCia_in IN CHAR,
                                  cCodConvenio   CHAR,
                                  cCodCli_in     CHAR)
    RETURN varchar2;

  --Descripcion: Retorna el codForma de Pago Dolares
  --Fecha       Usuario		Comentario
  --13/10/2007  DUBILLUZ   Creacion
  FUNCTION CAJ_OBTIENE_FP_DOLARES(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
    RETURN CHAR;

  --Descripcion: Retorna los valores de conexion con Bprepaid
  --Fecha       Usuario		Comentario
  --27/09/2007  ERIOS     Creacion
  FUNCTION CAJ_OBTIENE_VALORES_BPREPAID(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Retorna el codForma de Pago Dolares
  --Fecha       Usuario		Comentario
  --13/10/2007  DUBILLUZ   Creacion
  FUNCTION CAJ_GET_NUMERO_RECARGA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                 cNumPed_in       IN CHAR)
  RETURN CHAR;

  PROCEDURE CAJ_GRABA_RESPTA_RECARGA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cNumPed_in      IN CHAR,
                                     cCodRespta_in   IN CHAR);

 --Descripcion: Se valida los cupones al cobrar
  FUNCTION CAJ_VALIDA_CANT_CUPON_CAMP(cCodGrupoCia_in  IN CHAR,
				   	                          cCodLocal_in     IN CHAR,
					                            cNumPedVta_in    IN CHAR,
					                            cCodFormaPago_in IN CHAR,
                                      nCantCupon_in    IN NUMBER)
  RETURN FarmaCursor;
  --Descripcion: obtiene indicador de cobro deacuerdo al error
  --Fecha       Usuario		Comentario
  --27/06/2008  JCORTEZ   Creacion
  FUNCTION IMP_GET_IND_IMP_RECARGA(cCodGrupoCia_in 	IN CHAR,
                                   cCodError    	IN CHAR)
  RETURN VARCHAR2;
  --Descripcion: Se valida los cupones al cobrar
  --Fecha       Usuario		Comentario
  --10/07/2008  ERIOS     Creacion
/*  PROCEDURE VALIDA_USO_CUPONES(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR,vIdUsu_in IN VARCHAR2);*/

  --Descripcion: Se verifica la conexion con matriz
  --Fecha       Usuario		Comentario
  --10/07/2008  ERIOS     Creacion
/*  FUNCTION VERIFICA_CONN_MATRIZ
  RETURN CHAR;
*/
  --Descripcion: ACtualiza los cupones en matriz
  --Fecha       Usuario		Comentario
  --10/07/2008  ERIOS     Creacion
/*  PROCEDURE ACTUALIZA_CUPONES_MATRIZ(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR,vIdUsu_in IN VARCHAR2);*/


    --Descripcion: verifica si la campaña esta asociada a una forma de pago
  --Fecha       Usuario		Comentario
  --02/07/2008  JCORTEZ   Creacion
  FUNCTION GET_VERIFICA_CAMP_FP(cCodGrupoCia_in IN CHAR,
                                cCodLocal    	  IN CHAR,
                                cNumPedVta      IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se genera el cupon referente a la campaña
  --Fecha       Usuario		Comentario
  --02/07/2008  JCORTEZ   Creacion
  PROCEDURE CAJ_GENERA_CUPON(pCodGrupoCia_in         IN CHAR,
                            cCodLocal_in             IN CHAR,
        							 	 	  cNumPedVta_in	           IN CHAR,
      								 	  	cIdUsu_in                IN CHAR,
                            cDni_in               IN CHAR);

  --Descripcion: Se genera el secuencial del cupon
  --Fecha       Usuario		Comentario
  --02/07/2008  JCORTEZ   Creacion
  FUNCTION OBTENER_NUMERACION(cCodGrupoCia_in	 IN CHAR,
              							  cCodLocal_in  	   IN CHAR,
                              cCodCamp_in  	   IN CHAR)
  RETURN NUMBER;

  --Descripcion: Se actualiza el indicador de impresion del cupon por pedido
  --Fecha       Usuario		Comentario
  --02/07/2008  JCORTEZ   Creacion
  PROCEDURE CAJ_UPDATE_IND_IMP(pCodGrupoCia_in        IN CHAR,
                              cCodLocal_in            IN CHAR,
                              cCodCamp                IN CHAR,
          							 	 	  cNumPedVta_in	          IN CHAR,
        								 	  	cIndImpre_in            IN CHAR,
                              cIndTodos_in            IN CHAR);

  --Descripcion: Se verifica que el pedido contenga productos de campaña
  --Fecha       Usuario		Comentario
  --03/07/2008  JCORTEZ   Creacion
  FUNCTION GET_VERIFICA_PED_CAMP(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in     IN CHAR,
            							 	 	   cNumPedVta_in	  IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Se obtiene forma pago pedido cupon
  --Fecha       Usuario		Comentario
  --07/07/2008  JCORTEZ   Creacion
  FUNCTION GET_FORMA_PAGO_PED_CUPON(cCodGrupoCia_in IN CHAR,
                                    cCodLocal    	  IN CHAR,
                                    cNumPedVta      IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se activa la impresion para las camapañas validas
  --Fecha       Usuario		Comentario
  --07/07/2008  JCORTEZ   Creacion
  PROCEDURE CAJ_UPDATE_IND_IMP_SIN_FP(pCodGrupoCia_in       IN CHAR,
                                     cCodLocal_in           IN CHAR,
                  							 	 	 cNumPedVta_in	        IN CHAR);

  --Descripcion: Obtiene el numero de lineas de detalle en la boleta segun si es o no convenio
  --Fecha           Usuario		Comentario
  --11/10/2008  DVELIZ   Creacion
  FUNCTION CAJ_F_VAR_LINEA_DOC(cIdeTabGral  IN VARCHAR2 )
  RETURN VARCHAR2;

  --Descripcion: Obtiene el indicador de convenio
  --Fecha           Usuario		Comentario
  --17/10/2008  DVELIZ   Creacion
  FUNCTION CAJ_F_VAR_IND_PED_CONVENIO(pCodGrupoCia_in       IN CHAR,
                                     cCodLocal_in           IN CHAR,
                  							 	 	 cNumPedVta_in	        IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: VALIDA LA CONSISTENCIA DE MONTOS DE CABECERA DETALLE Y FORMA DE PAGO
  --Fecha           Usuario		Comentario
  --05/11/2008      JCALLO   Creacion
  FUNCTION  CAJ_F_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in IN CHAR,
					                                   cCodLocal_in    IN CHAR,
					                                   cNumPedVta_in   IN CHAR)
  RETURN CHAR;


  --Descripcion: VALIDA SI EXISTEN COMPROBANTES YA PROCESADAS EN SAP
  --Fecha           Usuario		Comentario
  --25/11/2008      DUBILLUZ   Creacion
  FUNCTION  CAJ_F_IS_COMP_PROCESO_SAP(cCodGrupoCia_in  IN CHAR,
              								     codLocal_in     	IN CHAR,
                  						     cSecIni_in 	 	  IN CHAR,
                  						     cSecFin_in  	 	  IN CHAR,
                                   cTipComp_in	    IN CHAR)
  RETURN CHAR;


   --Descripcion: Obtiene las formas de pago por convenio aceptadas por el local para el cobro del pedido
  --Fecha       Usuario		Comentario
  --11/12/2008 ASOLIS    	Creación

 FUNCTION CAJ_OBTIENE_FORMAS_PAG_CONV(cCodGrupoCia_in  IN CHAR,
  		   						   cCodLocal_in    	IN CHAR,
                       cIndPedConvenio  IN CHAR,
                       cCodConvenio     IN CHAR,
                       cCodCli_in       IN CHAR,
                       cNumPed_in       IN CHAR default '',
                       cValorCredito IN CHAR)

 RETURN FarmaCursor;

  --Descripcion: Obtiene las formas de pago sin convenio aceptadas por el local para el cobro del pedido
  --Fecha       Usuario		Comentario
  --11/12/2008  ASOLIS    	Creación

  FUNCTION CAJ_OBTIENE_FORMAS_PAG_SINCONV(cCodGrupoCia_in  IN CHAR,
  		   						   cCodLocal_in    	IN CHAR,
                       cIndPedConvenio  IN CHAR,
                       cCodConvenio     IN CHAR,
                       cCodCli_in       IN CHAR,
                       cNumPed_in       IN CHAR default NULL)
  RETURN FarmaCursor;

  --Descripcion: Obtiene las formas de pago
  --Fecha       Usuario		Comentario
  --15/12/2008  ASOLIS    	Creación
  FUNCTION CAJ_GET_FORMAS_PAGO_CONVENIO(cCodGrupoCia_in  IN CHAR,
                    		   						   cCodLocal_in    	IN CHAR,
                                         cIndPedConvenio  IN CHAR,
                                         cCodConvenio     IN CHAR,
                                         cCodCli_in       IN CHAR,
                                         cValorCredito IN CHAR) RETURN FarmaCursor ;





  --Descripcion: Procesa  Valores de un arqueo de caja
  --Fecha       Usuario		Comentario
  --05/12/2009  ASOLIS     	Creación


   FUNCTION CAJ_F_PROCESA_VALORES_ARQUEO(cCodGrupoCia_in     IN CHAR,
                          		   					    cCod_Local_in   IN CHAR,
                                              cTipMov_in      IN CHAR,
                                              nNumCaj_in 		  IN NUMBER,
              							  		            cSecUsu_in 		  IN CHAR,
              							  		            cIdUsu_in		    IN CHAR,
                        							   cSecMovCaja_in       IN CHAR,
                                            cIpMovCaja_in     IN CHAR,
                                            cTipOp_in         IN CHAR)
  RETURN CHAR;



--Descripcion : VALIDACION DIARIA PARA CAJA ELECTRONICA
--Fecha         Usuario Comentario
--08/01/2009    ASOLIS   Creacion

  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL);


  --Descripcion: Obtener valor de Nro Boleta y Factura
  --Fecha       Usuario		Comentario
  --01/02/2009  ASOLIS     	Creación


   FUNCTION CAJ_F_VALOR_COMPROBANTES(cCodGrupoCia_in IN CHAR,
                          		   		 cCod_Local_in    IN CHAR)
   RETURN FarmaCursor;


   --Descripción : Obtener valor de máxima diferencia permitido para comprobantes
   --Fecha       Usuario		Comentario
   --02/02/2009  ASOLIS     	Creación

   FUNCTION CAJ_F_VALOR_MAXIMA_DIFERENCIA

   RETURN CHAR;

   --Descripción : Obtener la serie de Boleta
   --Fecha       Usuario		Comentario
   --06/02/2009  ASOLIS     	Creación

   FUNCTION CAJ_LISTA_SERIES_BOLETA_CAJ(cCodGrupoCia_in IN CHAR,
			 							 cCod_Local_in 	 IN CHAR)
    RETURN FarmaCursor;


    --Descripción : Obtener la serie de Factura
   --Fecha       Usuario		Comentario
   --06/02/2009  ASOLIS     	Creación

   FUNCTION CAJ_LISTA_SERIES_FACTURA_CAJ(cCodGrupoCia_in IN CHAR,
			 							 cCod_Local_in 	 IN CHAR)
    RETURN FarmaCursor;

    --Descripción : Obtener VALOR COMPROBANTE BOLETA
   --Fecha       Usuario		Comentario
   --06/02/2009  ASOLIS     	Creación

    FUNCTION CAJ_F_VALOR_COMPROBANTE_BOLETA(cCodGrupoCia_in IN CHAR,
                          		   		        cCod_Local_in   IN CHAR,
                                            cNum_SerieLocal_in IN CHAR)

    RETURN FarmaCursor;

     --Descripción : Obtener VALOR COMPROBANTE FACTURA
   --Fecha       Usuario		Comentario
   --06/02/2009  ASOLIS     	Creación


    FUNCTION CAJ_F_VALOR_COMP_FACTURA(cCodGrupoCia_in IN CHAR,
                          		   		        cCod_Local_in   IN CHAR,
                                            cNum_SerieLocal_in IN CHAR)

    RETURN FarmaCursor;

    --Descripción : OBTENER DNI DEL CLIENTE FIDELIZADO SI SE TRATA DE UN PEDIDO FIDELIZADO
    --Fecha       Usuario		Comentario
    --16/02/2009  JCALLO     	Creación
    FUNCTION CAJ_F_CHAR_DNI_CLIENTE(cCodGrupoCia_in    IN CHAR,
                          		   	cCod_Local_in      IN CHAR,
                                  cNum_Pedido_in     IN CHAR)
    RETURN CHAR;

    --Descripción : OBTENER TODAS LAS CAMPAÑAS AUTOMATICAS USADOS EN EL PEDIDO
    --Fecha       Usuario		Comentario
    --16/02/2009  JCALLO     	Creación
    FUNCTION CAJ_F_LISTA_CAMP_AUTOMATIC(cCodGrupoCia_in    IN CHAR,
                          		   		 cCod_Local_in      IN CHAR,
                                     cNum_pedido_in     IN CHAR)

    RETURN FarmaCursor;

    --Descripción : REGISTRAR O ACTUALIZAR LA CANTIDAD DE VECES DE USO DE CAMPANIA
    --Fecha       Usuario		Comentario
    --16/02/2009  JCALLO     	Creación
    PROCEDURE CAJ_REG_CAMP_LIM_CLIENTE(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     vCodCampCupon_in  IN CHAR,
                                     vDniCliente_in    IN CHAR,
                                     vIdUsuario_in     IN CHAR);

    --Descripción : Bloquear el cierre de caja
    --Fecha       Usuario		Comentario
    --04/03/2009  DUBILLUZ  	Creación
    PROCEDURE CAJ_P_FOR_UPDATE_MOV_CAJA(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in      IN CHAR,
                                        cSecCaja_in       IN CHAR );

    --Descripción : Se obtiene el tipo de comprobante impresora actvio por caja (boleta / ticket)
    --Fecha       Usuario		Comentario
    --25/03/2009  JCORTEZ  	Creación
    FUNCTION CAJ_GET_TIPO_COMPR(cCodGrupoCia_in    IN CHAR,
                      		   	cCod_Local_in      IN CHAR,
                              cNumCaja           IN CHAR,
                              cTipComp           IN CHAR)
    RETURN CHAR;

    FUNCTION CAJ_F_VERIFICA_TIPO_COMP(cCodGrupoCia_in 	IN CHAR,
                                     cCodLocal_in 	IN CHAR,
                                     cSecIni_in   	IN CHAR,
                                     cSecFin_in   	IN CHAR )
    RETURN NUMBER;

    --Descripción : --si es de tipo ticket no se validara rango de comprobantes boleta y factura
    --Fecha       Usuario		Comentario
    --15/04/2009  JCORTEZ  	Creación
    FUNCTION CAJ_GET_TIPO_COMPR2(cCodGrupoCia_in    IN CHAR,
                          		   	cCod_Local_in      IN CHAR,
                                  cNumCaja           IN CHAR)
    RETURN CHAR;

    --Descripción : --si es de tipo ticket no se validara rango de comprobantes boleta y factura
    --Fecha       Usuario		Comentario
    --15/04/2009  JCORTEZ  	Creación
    FUNCTION CAJ_OBTIENE_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in   IN CHAR,
                                            cCodLocal_in     IN CHAR,
                                            cSecImprLocal_in IN CHAR,
                                            cNumPed_in     IN CHAR)
    RETURN FarmaCursor;


    --Descripción :
    --Fecha       Usuario		Comentario
    --19/05/2009  JCORTEZ  	Creación
    FUNCTION CAJ_VALIDA_CAJA_APERTURA(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        nNumCaj_in      IN NUMBER)
      RETURN CHAR;

    --Descripción : Se actualiza campo fecha anulacion por comprobante
    --Fecha       Usuario		Comentario
    --17/07/2009  JCORTEZ  	Creación
  PROCEDURE CAJ_P_ACT_COMP_ANUL(cCodGrupoCia_in 	  IN CHAR,
                                  cCodLocal_in    	  IN CHAR,
                                  cNumPedVta_in   	  IN CHAR,
                                  cNumCompPago_in 	  IN CHAR,
                                  cUsuModCompPago_in  IN CHAR,
                                  cInd                IN CHAR
                                                      );

  --Descripción : Valida el ingreso de sobre en local
  --Fecha       Usuario		Comentario
  --03/11/2009  JCORTEZ  	Creación
  FUNCTION CAJ_F_PERMITE_INGRESO_SOBRE(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecMovCaja 	 IN CHAR)
  RETURN CHAR;

  --Descripción : Se graba los sobres temporalmente
  --Fecha       Usuario		Comentario
  --03/11/2009  JCORTEZ  	Creación
  FUNCTION CAJ_F_GRABA_SOBRE(cCodGrupoCia_in	    IN CHAR,
  		   						                    cCodLocal_in	      IN CHAR,
                                        cSecMovCaja_in      IN CHAR,
                                        cCodFormaPago_in    IN CHAR,
                                        cTipMoneda_in       IN CHAR,
                                        cMonEntrega_in      IN NUMBER,
                                        cMonEntregaTotal_in IN NUMBER,
                                        cUsuCreaEntrega_in  IN CHAR)
  RETURN VARCHAR2;

  --Descripción : Se elimina el sobre creado temporalmente
  --Fecha       Usuario		Comentario
  --03/11/2009  JCORTEZ  	Creación
  PROCEDURE CAJ_P_ELIMINA_SOBRE(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cSecMovCaja_in  IN CHAR,
                                cCodForm_in     IN CHAR,
                                cTipMon_in      IN CHAR,
                                cSec            IN CHAR,
                                cUsuCrea        IN CHAR);

  --Descripción : Se lista los sobres creados
  --Fecha       Usuario		Comentario
  --03/11/2009  JCORTEZ  	Creación
  FUNCTION CAJ_F_CUR_SOBRES(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor;

  --Descripción : Se lista los sobres entrega para el cierre de turno
  --Fecha       Usuario		Comentario
  --03/11/2009  JCORTEZ  	Creación
  FUNCTION CAJ_F_CUR_SOBRES_ENTREGA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor;

  --Descripción : Se lista los sobres entrega para el cierre de turno
  --Fecha       Usuario		Comentario
  --03/11/2009  JCORTEZ  	Creación
  FUNCTION CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in IN CHAR,
                                    cCod_Local_in   IN CHAR,
                                    cSecMovCaja_in  IN CHAR,
                                    cFecDiaVta_in   IN DATE)
  RETURN NUMBER;

  --Descripción : Se lista los sobres entrega para el cierre de turno
  --Fecha       Usuario		Comentario
  --03/11/2009  JCORTEZ  	Creación
  --16/06/2010  ASOSA     Modificacion, para q el monto sea solo del efectivo soles y dolares
  FUNCTION CAJ_F_GET_MONTOVENTAS(cCodGrupoCia_in   IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR)
  RETURN CHAR;

  --Descripción : Se lista los sobres entrega para el cierre de turno
  --Fecha       Usuario		Comentario
  --03/11/2009  JCORTEZ  	Creación
  PROCEDURE CAJ_P_ENVIA_SOBRE(cCodGrupoCia  IN CHAR,
                            cCodLocal     IN CHAR,
                            cUsuCrea      IN CHAR,
                            cCodSobre     IN CHAR);

  --Descripción : Se validar si se permite ingreso de mas de un sobres
  --Fecha       Usuario		Comentario
  --28/03/2010  JCORTEZ  	Creación
  FUNCTION CAJ_F_PERMITE_MAS_SOBRES(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN CHAR;

  --Descripción : Se valida la opcion de activar control sobres
  --Fecha       Usuario		Comentario
  --09/04/2010  JCORTEZ  	Creación
  --04/06/2010  ASOSA  	Modificacion
  FUNCTION CAJ_F_PERMTE_CONTROL_SOBRE(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR)
  RETURN CHAR;


  --Descripción : Se valida la opcion de activar Prosegur
  --Fecha       Usuario		Comentario
  --09/04/2010  JCORTEZ  	Creación
  FUNCTION CAJ_F_PERMTE_CONTROL_PROSEGUR(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR)
  RETURN CHAR;

  FUNCTION CAJ_F_IS_PED_CONV_MF_BTL(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in IN CHAR
                                   )
  RETURN CHAR;

  --Descripcion: Inserta una forma de pago por pedido con informacion de la tarjeta, parte de la correccion de CAJ_GRABAR_FORMA_PAGO_PEDIDO
  --Fecha       Usuario		      Comentario
  --20/03/2013  Luigy Terrazos  Creación
  PROCEDURE CAJ_GRAB_NEW_FORM_PAGO_PEDIDO(cCodGrupoCia_in           IN CHAR,
                                          cCodLocal_in              IN CHAR,
                                         cCodFormaPago_in        IN CHAR,
                                          cNumPedVta_in             IN CHAR,
                                          nImPago_in                IN NUMBER,
                                         cTipMoneda_in           IN CHAR,
                                         nValTipCambio_in         IN NUMBER,
                                          nValVuelto_in            IN NUMBER,
                                          nImTotalPago_in          IN NUMBER,
                                         cNumTarj_in              IN CHAR,
                                         cFecVencTarj_in         IN CHAR,
                                         cNomTarj_in              IN CHAR,
                                         cCanCupon_in              IN NUMBER,
                                         cUsuCreaFormaPagoPed_in IN CHAR,
                                         cDNI_in              IN CHAR,
                                         cCodAtori_in         IN CHAR,
                                         cLote_in             IN CHAR);

  --Descripcion: Guarda el historial de los cambios de forma de pago
  --Fecha       Usuario		      Comentario
  --26/03/2013  Luigy Terrazos  Creación
  PROCEDURE SAVE_HIST_FORM_PAGO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNum_Ped_Vta_in IN CHAR,
                                cUsuModFormaPagoPed_in IN CHAR);

  --Descripcion: Elimina los registros que seran modificados
  --Fecha       Usuario		      Comentario
  --26/03/2013  Luigy Terrazos  Creación
  PROCEDURE DEL_FORM_PAGO_PED(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNum_Ped_Vta_in IN CHAR);

  --Descripcion: Obtener las formas de pago de un pedido, para abrir la gabeta en caso sea efectivo
  --Fecha       Usuario		      Comentario
  --27/12/2013  GFonseca        Creación
  FUNCTION GET_FPAGO_PEDIDO(cNum_Pedido_in IN CHAR)
  RETURN FarmaCursor;

  --Fecha       Usuario    Comentario
  --11/02/2014  RHERRERA      Creación

  FUNCTION CAJ_OBTENER_FECHAMAX_APERTURA(cCodGrupoCia_in IN CHAR,
                                         cCod_Local_in   IN CHAR,
                                         nNumCaj_in      IN NUMBER)
  RETURN DATE;


  --Descripcion: Cambia Forma de Pago
  --Fecha       Usuario		      Comentario
  --17/03/2014   RHERRERA        Creación
   PROCEDURE CAJ_CAM_FORM_PAGO_TARJ (
                                           cGrupCia_in      IN CHAR,
                                           cLocal_in        IN CHAR,
                                           cUsuaario_in     IN VARCHAR2,
                                           cmonto_in        IN VARCHAR2,
                                           cIdVoucher_in    IN VARCHAR2,--AP
                                           cFechaVoucher_in IN VARCHAR2,
                                           cHoraVoucher_in  IN VARCHAR2,
                                           cNumPedNew_in    IN VARCHAR2,
                                           cFormaPagNew_in  IN VARCHAR2,
                                           cTipOrigen_in    IN VARCHAR2

                                            );

--Descripcion:       Funcion que determina si el pedido contiene una recarga virtual
--Autor:             ASOSA
--Fecha:                  08/07/2014
  FUNCTION CAJ_VERIFICA_REC_VIRTUAL(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                       cNumPedVta_in   IN CHAR)
RETURN NUMBER;


  -- DESCRIPCION: FUNCION QUE DEVUELVE LOS NUMEROS DE COMPROBANTES DE PAGO ASIGNADOS
  --              AL PEDIDO (REIMPRESION)
  -- AUTOR      : KMONCADA
  -- FECHA      : 09.07.2014

  FUNCTION CAJ_NUM_COMP_PAGO_IMPR_PEDIDO(cCodGrupoCia_in   IN CHAR,
                                                 cCodLocal_in     IN CHAR,
                                                 cNumPed_in      IN CHAR)
  RETURN FarmaCursor;

    -- Author  : LTAVARA
    -- Created : 25/08/2014 04:50:35 p.m.
    -- Purpose : FUNCION LISTA DE COMPROBANTE DE PAGO POR PEDIDO

   FUNCTION CAJ_F_CUR_LISTA_COMP_PAGO(cCodGrupoCia_in  IN VARCHAR2,
                                         cCodLocal_in     IN VARCHAR2,
                                         cNumPed_in       IN VARCHAR2
                                         )
        RETURN FarmaCursor;


    -- Author  : LTAVARA
    -- Created : 25/07/2014 06:50:35 p.m.
    -- Purpose : Modifica el tipo del documento del comprobante

    FUNCTION CAJ_MODIFICAR_TIP_COMP_PAGO(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  vTipCompPaqo VARCHAR2

                                  )
        RETURN NUMBER;

    -- Author  :
    -- Created : 25/07/2014 06:50:35 p.m.
    -- Purpose : Modifica el tipo del documento del comprobante
  FUNCTION GET_NOMBRE_IMPRES_TERMICA(cCodGrupoCia_in  IN CHAR,
                            cCodLocal_in     IN CHAR,
                     cIp_in IN String )
  RETURN VARCHAR2;

    -- Author  : CHUANES
    -- Created : 01/08/2014 07:10 PM
    -- Purpose : Obtiene el modelo de impresora.
  /***********************************************************/

FUNCTION GET_MODELO_IMP_TERMICA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR, cIp_In IN String  )
  RETURN VTA_IMPR_LOCAL_TERMICA.TIPO_IMPR_TERMICA%TYPE ;

  /***********************************************************/
    -- AUTOR         : KMONCADA
    -- FECHA         : 24/09/2014
    -- DESCRIPCION   : VERIFICA SI CONVENIO MIXTO
  FUNCTION IS_CONVENIO_MIXTO(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                           cNumPedidoVta VARCHAR2)
  RETURN CHAR;

  FUNCTION IND_GET_PED_ING_MANUAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR) RETURN VARCHAR2;
  FUNCTION IND_GET_CADENA_MANUAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR) RETURN VARCHAR2;

  FUNCTION IND_VALIDA_COMP_MANUAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cTipComp_in   IN CHAR,
                                  cSerieComp_in   IN CHAR,
                                  cNumComp_in   IN CHAR
                                 ) RETURN VARCHAR2 ;

  /***********************************************************/
    -- AUTOR         : KMONCADA
    -- FECHA         : 20.10.2014
    -- DESCRIPCION   : VERIFICA QUE EL NUM DE COMPROB.ELECTRONICO NO HALLA SIDO ASIGNADO
  FUNCTION FN_EXISTE_COMPROBANTE_E(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cTipComp_in   IN CHAR,
                                   cNumComp_in   IN CHAR
                                  )RETURN INTEGER;
                                  
  PROCEDURE P_ACT_FCH_IMPRESION(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta      IN CHAR,
                                   cTipComp_in   IN CHAR,
                                   cSecComPago   IN CHAR,
                                   cIdUsu        IN VARCHAR2
                                  );  
  /***********************************************************/
    -- AUTOR         : RHERRERA
    -- FECHA         : 18.11.2014
    -- DESCRIPCION   : OBTIENE TIPO DE COMPROBANTE DEL PEDIDO                                  
   FUNCTION CAJ_F_CUR_LISTA_COMP_PAGO_GUI(cCodGrupoCia_in  IN VARCHAR2,
                                         cCodLocal_in     IN VARCHAR2,
                                         cNumPed_in       IN VARCHAR2
                                         )
   RETURN FarmaCursor;                                       
                              
   /***********************************************************/
    -- AUTOR         : KMONCADA
    -- FECHA         : 02.12.2014
    -- DESCRIPCION   : NOMBRE DEL COMPROBANTE ELECTRONICO PARA GRABAR COMO ARCHIVO
   FUNCTION GET_NOMBRE_FILE_COMP_ELEC(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cNumPed_in       IN CHAR,
                                     cSecCompPago_in  IN CHAR)
  RETURN VARCHAR2;
  
   /***********************************************************/  
  FUNCTION CAJ_LISTA_COMPROBANT_COMPROBAR(cCodGrupoCia_in IN CHAR,
                                            cCod_Local_in   IN CHAR,
                                            cFecIni_in      IN CHAR,
                                            cFecFin_in      IN CHAR)
  RETURN FarmaCursor;                                             
  
  /**
  * METODO QUE OBTIENE IMPRESORA TERMICA 
  *  AUTOR                FECHA            ACCION
     KMONCADA             12.01.2015       CREACION
  */
  FUNCTION GET_IMPRESORA_TERMICA(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in     IN CHAR, 
                                 cIp_In           IN String)
  RETURN FarmaCursor;
  
  
  /*********************************************/
  FUNCTION GET_NRO_COMPROBANTE_PAGO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cSecCompPago_in IN CHAR)
  RETURN VARCHAR2;
  
  FUNCTION F_CHAR_IND_COMP_ELECTRONICO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cNumPedidoVta_in IN CHAR,
                                       cSecComPago_in   IN CHAR)
  RETURN CHAR;
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CAJ" AS


 FUNCTION CAJ_OBTIENE_CAJAS_DISP_USUARIO(cCodGrupoCia_in IN CHAR,
                              cCod_Local_in   IN CHAR,
                        cSecUsu_in      IN CHAR)
  RETURN CHAR
  IS
    vCaja NUMBER;
    vRpta CHAR(4);
    vCant NUMBER;
  BEGIN
       vRpta:='0';
     vCaja:=0;

    SELECT TO_NUMBER(COUNT(*)) INTO vCant
    FROM VTA_CAJA_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
          COD_LOCAL     = cCod_Local_in   AND
        SEC_USU_LOCAL = cSecUsu_in      AND
        EST_CAJA_PAGO = ESTADO_ACTIVO    AND
      ROWNUM    = 1;

      IF vCant=0 THEN
         BEGIN
           vCaja:=0;
         END;
      ELSE
          BEGIN
            SELECT NVL(NUM_CAJA_PAGO,0) INTO vCaja
             FROM VTA_CAJA_PAGO
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
                    COD_LOCAL     = cCod_Local_in   AND
                  SEC_USU_LOCAL = cSecUsu_in      AND
                  EST_CAJA_PAGO = ESTADO_ACTIVO   AND
              ROWNUM=1;
          END;
      END IF;

        IF vCaja=0 THEN
             vRpta:='0';
        ELSE vRpta:=TO_CHAR(vCaja);
        END IF;

  RETURN vRpta;
  END;

    /**************************************************************************************************/

  PROCEDURE CAJ_REGISTRA_MOVIMIENTO_APER(cCodGrupoCia_in IN CHAR,
                                         cCod_Local_in   IN CHAR,
                                         nNumCaj_in      IN NUMBER,
                                         cSecUsu_in      IN CHAR,
                                         cCodUsu_in      IN CHAR,
                                         cIpMovCaja      IN CHAR) IS
  vCant        NUMBER;
  v_nNeoCod    CHAR(10);
  v_nNeoTur    NUMBER(4);
  v_IndCajAb   CHAR(1);
  BEGIN
      --Selecciona para bloquear el registro
      SELECT IND_CAJA_ABIERTA
      INTO   v_IndCajAb
      FROM   VTA_CAJA_PAGO
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
             COD_LOCAL     = cCod_Local_in   AND
             NUM_CAJA_PAGO = nNumCaj_in FOR UPDATE;

       --Inserta Movimiento de Caja
       v_nNeoCod:=Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.Obtener_Numeracion(cCodGrupoCia_in,cCod_Local_in,COD_NUM_SEC_MOV_CAJ),10,'0',POS_INICIO );

       IF v_IndCajAb = INDICADOR_SI THEN
           RAISE_APPLICATION_ERROR(-20009,'No se puede aperturar una caja cuando ya se encuentra abierta.');
       END IF;

       v_nNeoTur:= CAJ_OBTENER_NUEVO_TURNO_CAJA(cCodGrupoCia_in,cCod_Local_in,nNumCaj_in);

       INSERT INTO CE_MOV_CAJA (COD_GRUPO_CIA,
                                 COD_LOCAL,
                                 SEC_MOV_CAJA,
                                 NUM_CAJA_PAGO,
                                 SEC_USU_LOCAL,
                                 TIP_MOV_CAJA,
                                 FEC_DIA_VTA,
                                 NUM_TURNO_CAJA,
                                 USU_CREA_MOV_CAJA,
                                Ip_Mov_Caja)
                        VALUES (cCodGrupoCia_in,
                                cCod_Local_in,
                                 v_nNeoCod,
                                nNumCaj_in,
                                cSecUsu_in,
                                TIP_MOV_APERTURA,
                                TO_DATE(TO_CHAR(SYSDATE,'dd/MM/yyyy'),'dd/MM/yyyy'),
                                v_nNeoTur,
                                cCodUsu_in,
                                cIpmovcaja);

       UPDATE VTA_CAJA_PAGO SET FEC_MOD_CAJA_PAGO = SYSDATE, USU_MOD_CAJA_PAGO = cCodUsu_in,
                                SEC_MOV_CAJA      = v_nNeoCod,
                                IND_CAJA_ABIERTA  = INDICADOR_SI
                          WHERE COD_GRUPO_CIA     = cCodGrupoCia_in AND
                                 COD_LOCAL         = cCod_Local_in   AND
                                NUM_CAJA_PAGO     = nNumCaj_in;

   END;

FUNCTION CAJ_OBTENER_SEC_MOV_APERTURA(cCodGrupoCia_in IN CHAR,
                                         cCod_Local_in   IN CHAR,
                                        nNumCaj_in      IN NUMBER)
    RETURN CHAR
  IS
    v_cMovOrig CHAR(10);
    v_dMaxFec  DATE;
  BEGIN
       SELECT MAX(FEC_DIA_VTA)
       INTO   v_dMaxFec
       FROM   CE_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
              COD_LOCAL     = cCod_Local_in   AND
              NUM_CAJA_PAGO = nNumCaj_in      AND
              TIP_MOV_CAJA  = TIP_MOV_APERTURA;

       SELECT NVL(MAX(SEC_MOV_CAJA),'0000000001')
       INTO   v_cMovOrig
       FROM   CE_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in  AND
              COD_LOCAL     = cCod_Local_in    AND
              NUM_CAJA_PAGO = nNumCaj_in       AND
              TIP_MOV_CAJA  = TIP_MOV_APERTURA AND
              FEC_DIA_VTA   = v_dMaxFec;

  RETURN v_cMovOrig;
  END;
  /*****************************************************************************************************************/
     FUNCTION CAJ_OBTENER_SEC_MOV_APERTURA(cCodGrupoCia_in IN CHAR,
                          cCod_Local_in   IN CHAR,
                       cFecDiaVenta_in IN CHAR,
                       nNumCaj_in      IN NUMBER)
     RETURN CHAR
     IS
       v_cMovOrig CHAR(10);
     vMaxFec DATE;
     BEGIN

   SELECT MAX(FEC_DIA_VTA) INTO vMaxFec
   FROM CE_MOV_CAJA
     WHERE    COD_GRUPO_CIA = cCodGrupoCia_in AND
              COD_LOCAL     = cCod_Local_in   AND
            NUM_CAJA_PAGO = nNumCaj_in      AND
              TIP_MOV_CAJA  = ESTADO_ACTIVO;

      SELECT NVL(MAX(SEC_MOV_CAJA),'0000000001') INTO v_cMovOrig
      FROM CE_MOV_CAJA
      WHERE   COD_GRUPO_CIA = cCodGrupoCia_in AND
              COD_LOCAL     = cCod_Local_in   AND
            NUM_CAJA_PAGO = nNumCaj_in      AND
              TIP_MOV_CAJA  = TIP_MOV_APERTURA
       AND TO_CHAR(FEC_DIA_VTA,'dd/MM/yyyy')=TRIM(TO_CHAR(vMaxFec,'dd/MM/yyyy'));
     RETURN v_cMovOrig;
    END;
    /**************************************************************************************************/
  FUNCTION CAJ_OBTENER_NUEVO_TURNO_CAJA(cCodGrupoCia_in IN CHAR,
                           cCod_Local_in   IN CHAR,
                      nNumCaj_in      IN NUMBER)
    RETURN NUMBER
    IS
     vnTurno NUMBER;
    BEGIN
    SELECT NVL(MAX(NUM_TURNO_CAJA),0)+1 INTO vnTurno
    FROM   CE_MOV_CAJA
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
           COD_LOCAL     = cCod_Local_in   AND
         NUM_CAJA_PAGO = nNumCaj_in      AND
           TO_CHAR(SYSDATE,'dd/MM/yyyy')=TO_CHAR(FEC_DIA_VTA,'dd/MM/yyyy');

  RETURN vnTurno;
  END;
    /**************************************************************************************************/

  FUNCTION CAJ_OBTENER_IND_CAJA_ABIERTA(cCodGrupoCia_in IN CHAR,
                           cCod_Local_in   IN CHAR,
                      nNumCaj_in      IN NUMBER)
    RETURN CHAR
    IS
     vInd CHAR(1);
  BEGIN
    SELECT IND_CAJA_ABIERTA INTO vInd
    FROM VTA_CAJA_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
          COD_LOCAL     = cCod_Local_in   AND
        NUM_CAJA_PAGO = nNumCaj_in ;
  RETURN vInd;
  END;
  /**************************************************************************************************/


  FUNCTION CAJ_OBTENER_TURNO_ACTUAL_CAJA(cCodGrupoCia_in IN CHAR,
                           cCod_Local_in   IN CHAR,
                        nNumCaj_in      IN NUMBER)
    RETURN NUMBER
    IS

  vnFecAper             DATE;  -- variable para obtener utlima fecha apertura
  vnTurno               NUMBER(3);
  vCantMov              NUMBER;
  cSecMovCaja           CHAR(10);

  BEGIN

            -- Almacenamos Ultima fecha de Apertura --
     vnFecAper:=CAJ_OBTENER_FECHAMAX_APERTURA(cCodGrupoCia_in,cCod_Local_in,nNumCaj_in);
               -- Insertar Ultimo Movimiento de Caja Apertura --
    cSecMovCaja := CAJ_OBTENER_SEC_MOV_APERTURA(cCodGrupoCia_in,cCod_Local_in,nNumCaj_in);


    SELECT COUNT(*) INTO vCantMov
           FROM CE_MOV_CAJA
           WHERE    COD_GRUPO_CIA = cCodGrupoCia_in  AND
                    COD_LOCAL     = cCod_Local_in   AND
                    NUM_CAJA_PAGO = nNumCaj_in    AND
                    fec_dia_vta   = vnFecAper AND   -- validamos registros de la fecha ultima apertura
                    TIP_MOV_CAJA  = TIP_MOV_APERTURA;

        IF vCantMov > 0 THEN

         BEGIN

              SELECT NVL(NUM_TURNO_CAJA,0) INTO vnTurno
              FROM   CE_MOV_CAJA
              WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                     COD_LOCAL     = cCod_Local_in   AND
                     NUM_CAJA_PAGO = nNumCaj_in      AND
           --         fec_dia_vta   = vnFecAper AND
                      SEC_MOV_CAJA= cSecMovCaja;
        END;

        ELSE
           BEGIN
           vnTurno:='0';
         END;
        END IF;

  RETURN vnTurno;
  END;


   /**************************************************************************************************/
    FUNCTION CAJ_OBTENER_FECHA_APERTURA(cCodGrupoCia_in IN CHAR,
                           cCod_Local_in   IN CHAR,
                        nNumCaj_in      IN NUMBER)
    RETURN CHAR
    IS
  VFECHA CHAR(10);
  cSecMovOrigen CHAR(10);
   vCantMov NUMBER;
  BEGIN

  SELECT COUNT(*) INTO vCantMov
  FROM CE_MOV_CAJA
     WHERE    COD_GRUPO_CIA = cCodGrupoCia_in  AND
              COD_LOCAL     = cCod_Local_in   AND
            NUM_CAJA_PAGO = nNumCaj_in    AND
        TIP_MOV_CAJA  = TIP_MOV_APERTURA;

        IF vCantMov > 0 THEN
           BEGIN
              SELECT NVL(MAX(SEC_MOV_CAJA),' ')
              INTO cSecMovOrigen
              FROM CE_MOV_CAJA
                WHERE   COD_GRUPO_CIA = cCodGrupoCia_in  AND
                            COD_LOCAL     = cCod_Local_in   AND
                       NUM_CAJA_PAGO = nNumCaj_in    AND
                   TIP_MOV_CAJA  = TIP_MOV_APERTURA;

              SELECT TO_CHAR(FEC_DIA_VTA,'dd/MM/yyyy') INTO VFECHA
              FROM   CE_MOV_CAJA
                WHERE    COD_GRUPO_CIA = cCodGrupoCia_in AND
                           COD_LOCAL     = cCod_Local_in   AND
                      NUM_CAJA_PAGO = nNumCaj_in      AND
                  SEC_MOV_CAJA= cSecMovOrigen;

         END;
        ELSE
            BEGIN
          VFECHA:=TO_CHAR(SYSDATE,'dd/MM/yyyy');

          END;

        END IF;

   RETURN VFECHA;
   END;
   /**************************************************************************************************/


   PROCEDURE CAJ_VALIDA_OPERADOR_CAJA( cCodGrupoCia_in IN CHAR,
                         cCod_Local_in   IN CHAR,
                     cSecUsu_in      IN CHAR,
                     cTipOp_in       IN CHAR)
   IS
   vNumCaja NUMBER;
   vIdAbierto CHAR(1);
   BEGIN
        --Valida para Configuracion de Caja y para Movimiento
       vNumCaja:=TO_NUMBER(NVL(CAJ_OBTIENE_CAJAS_DISP_USUARIO(cCodGrupoCia_in,cCod_Local_in,cSecUsu_in),'0'));
    IF vNumCaja=0 THEN
         RAISE_APPLICATION_ERROR(-20011,'El usuario no posee ninguna caja activa asociada.');
    END IF;
    vIdAbierto:=CAJ_OBTENER_IND_CAJA_ABIERTA(cCodGrupoCia_in,cCod_Local_in,vNumCaja);

    IF cTipOp_in='MA' THEN
      BEGIN
          --Valida para Movimiento
          IF vIdAbierto = INDICADOR_SI THEN
            RAISE_APPLICATION_ERROR(-20012,'La caja del usuario ya se encuentra aperturada');
          END IF;
      END;
    END IF;

    IF cTipOp_in='MC' THEN
      BEGIN
          --Valida para Movimiento
          IF vIdAbierto = INDICADOR_NO THEN
            RAISE_APPLICATION_ERROR(-20013,'La caja del usuario ya se encuentra cerrada');
          END IF;
      END;
    END IF;
  END;

    /**************************************************************************************************/

  FUNCTION CAJ_LISTA_IMPRESORAS_CAJAS_USU(cCodGrupoCia_in IN CHAR,
                               cCod_Local_in   IN CHAR,
                        cSecUsu_in      IN CHAR)
  RETURN FarmaCursor
  IS
    curImpr FarmaCursor;
  BEGIN
    OPEN curImpr FOR

  SELECT IL.SEC_IMPR_LOCAL               || 'Ã' ||
       IL.DESC_IMPR_LOCAL               || 'Ã' ||
         TC.DESC_COMP                     || 'Ã' ||
         IL.NUM_SERIE_LOCAL               || 'Ã' ||
         IL.NUM_COMP                      || 'Ã' ||
         IL.RUTA_IMPR                     || 'Ã' ||
         CASE WHEN IL.EST_IMPR_LOCAL=ESTADO_ACTIVO
           THEN 'Activo'
             ELSE 'Inactivo'
       END                         || 'Ã' ||
  IL.TIP_COMP

  FROM VTA_IMPR_LOCAL  IL,
        VTA_SERIE_LOCAL SL,
        VTA_TIP_COMP    TC,
      VTA_CAJA_IMPR   CI,
      VTA_CAJA_PAGO   CP
   WHERE
                CP.COD_GRUPO_CIA   = cCodGrupoCia_in
      AND   CP.COD_LOCAL     = cCod_Local_in
      AND   CP.SEC_USU_LOCAL   = cSecUsu_in
      AND   CI.COD_GRUPO_CIA   = CP.COD_GRUPO_CIA
      AND   CI.COD_LOCAL     = CP.COD_LOCAL
      AND   CI.NUM_CAJA_PAGO   = CP.NUM_CAJA_PAGO
      AND   IL.COD_GRUPO_CIA   = CI.COD_GRUPO_CIA
      AND   IL.COD_LOCAL     = CI.COD_LOCAL
      AND   IL.SEC_IMPR_LOCAL   = CI.SEC_IMPR_LOCAL
      AND   IL.COD_GRUPO_CIA      = SL.COD_GRUPO_CIA
      AND   IL.COD_LOCAL          = SL.COD_LOCAL
      AND   IL.NUM_SERIE_LOCAL    = SL.NUM_SERIE_LOCAL
      AND   IL.TIP_COMP        = SL.TIP_COMP
      AND   SL.COD_GRUPO_CIA   = TC.COD_GRUPO_CIA
      AND   SL.TIP_COMP        = TC.TIP_COMP;
    RETURN curImpr;
  END;

  /**************************************************************************************************/

  FUNCTION CAJ_LISTA_IMPRESORAS_CAJAS_LOC(cCodGrupoCia_in IN CHAR,
                               cCod_Local_in   IN CHAR
                          )
  RETURN FarmaCursor
  IS
    curImpr FarmaCursor;
  BEGIN
    OPEN curImpr FOR

  SELECT IL.SEC_IMPR_LOCAL               || 'Ã' ||
       IL.DESC_IMPR_LOCAL               || 'Ã' ||
         TC.DESC_COMP                     || 'Ã' ||
         IL.NUM_SERIE_LOCAL               || 'Ã' ||
         IL.NUM_COMP                      || 'Ã' ||
         IL.RUTA_IMPR                     || 'Ã' ||
         CASE WHEN IL.EST_IMPR_LOCAL=ESTADO_ACTIVO
           THEN 'Activo'
             ELSE 'Inactivo'
       END                         || 'Ã' ||
  IL.TIP_COMP

  FROM VTA_IMPR_LOCAL  IL,
        VTA_SERIE_LOCAL SL,
        VTA_TIP_COMP    TC
      WHERE   IL.COD_GRUPO_CIA   = cCodGrupoCia_in
      AND   IL.COD_LOCAL     = cCod_Local_in
      AND   IL.COD_GRUPO_CIA      = SL.COD_GRUPO_CIA
      AND   IL.COD_LOCAL          = SL.COD_LOCAL
      AND   IL.NUM_SERIE_LOCAL    = SL.NUM_SERIE_LOCAL
      AND   IL.TIP_COMP        = SL.TIP_COMP
      AND   SL.COD_GRUPO_CIA   = TC.COD_GRUPO_CIA
      AND   SL.TIP_COMP        = TC.TIP_COMP;
    RETURN curImpr;
  END;

/**********************************************************************************/


   FUNCTION CAJ_LISTA_SERIES_IMPRESORA_CAJ(cCodGrupoCia_in IN CHAR,
                            cCod_Local_in   IN CHAR,
                       cSecUsu_in      IN CHAR,
                       cTipComp_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curImpr FarmaCursor;
  BEGIN
    OPEN curImpr FOR

    SELECT  DISTINCT TRIM(NUM_SERIE_LOCAL) || 'Ã' ||
                 TRIM(NUM_SERIE_LOCAL) "1"

        FROM VTA_IMPR_LOCAL IL,
          VTA_CAJA_IMPR  CI,
        VTA_CAJA_PAGO  CP
    WHERE
                 CP.COD_GRUPO_CIA  = cCodGrupoCia_in
       AND   CP.COD_LOCAL      = cCod_Local_in
       AND   IL.TIP_COMP       = cTipComp_in
       AND   CI.COD_GRUPO_CIA  = CP.COD_GRUPO_CIA
       AND   CI.COD_LOCAL      = CP.COD_LOCAL
       AND   CI.NUM_CAJA_PAGO  = CP.NUM_CAJA_PAGO
       AND   IL.COD_GRUPO_CIA  = CI.COD_GRUPO_CIA
       AND   IL.COD_LOCAL      = CI.COD_LOCAL
       AND   IL.SEC_IMPR_LOCAL = CI.SEC_IMPR_LOCAL;
     RETURN curImpr;
  END;

  /**************************************************************************************************/

  PROCEDURE CAJ_RECONFIG_IMPRESORA(cCodGrupoCia_in   IN CHAR,
                       cCod_Local_in     IN CHAR,
                   nSecImprLocal_in  IN NUMBER,
                   cNumSerieLocal_in IN CHAR,
                   cNumComp_in       IN CHAR,
                   cCodUsu_in        IN CHAR)
  IS
  BEGIN
       UPDATE VTA_IMPR_LOCAL SET FEC_MOD_IMPR_LOCAL = SYSDATE,USU_MOD_IMPR_LOCAL = cCodUsu_in,
              NUM_SERIE_LOCAL= cNumSerieLocal_in,
              NUM_COMP       = cNumComp_in
     WHERE
        COD_GRUPO_CIA   = cCodGrupoCia_in   AND
          COD_LOCAL       = cCod_Local_in     AND
        SEC_IMPR_LOCAL  = nSecImprLocal_in;
  END;

  /**************************************************************************************************/

  FUNCTION CAJ_LISTA_CAB_PEDIDOS_PENDIENT(cCodGrupoCia_in IN CHAR,
                             cCod_Local_in   IN CHAR,
                            cSec_Usu_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curImpr FarmaCursor;
    indTipoLocal char(1);
    indAdministrador char(1):='N';
  BEGIN
    select TRIM(NVL(l.tip_caja,''))
    into   indTipoLocal
    from   pbl_local l
    where  l.cod_grupo_cia = cCodGrupoCia_in
    and    l.cod_local = cCod_Local_in;
  --ERIOS v2.4.5 No se muestran pedidos Venta Empresa
   if trim(indTipoLocal) = 'M'then

   dbms_output.put_line('1');
        select decode(count(1),1,'S','N')
        into   indAdministrador
        from   pbl_rol_usu r
        where  r.cod_grupo_cia = cCodGrupoCia_in
        and    r.cod_local = cCod_Local_in
        and    r.cod_rol = '011'
        and    r.sec_usu_local = cSec_Usu_in;

    if indAdministrador = 'S' then
       dbms_output.put_line('2');
    OPEN curImpr FOR
        SELECT NVL(INITCAP(LPAD(NVL(NOM_CLI_PED_VTA || ' ',' '),INSTR(NOM_CLI_PED_VTA || ' ', ' '))),' ')  || 'Ã' ||
               NVL(VC.NUM_PED_DIARIO,' ')   || 'Ã' ||
               NVL(TO_CHAR(VC.NUM_PED_VTA),' ') || 'Ã' ||
                NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ') || 'Ã' ||
               TRIM(TO_CHAR(NVL(VC.VAL_NETO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_IGV_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                 TRIM(TO_CHAR(NVL(VC.VAL_NETO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_BRUTO_PED_VTA,0),'999,999,990.00'))|| 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_DCTO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_REDONDEO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                NVL(TO_CHAR(VC.RUC_CLI_PED_VTA),' ')     || 'Ã' ||
             NVL(VC.NUM_PED_DIARIO,' ') || 'Ã' ||
             NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy'),' ')|| 'Ã' ||
             NVL(TO_CHAR(VC.FEC_PED_VTA,'yyyyMMdd' ),' ')||' '||NVL(VC.NUM_PED_DIARIO,' ')|| 'Ã' ||
             -- JCORTEZ 17/03/2008
             NVL(VC.IND_PED_CONVENIO ,' ') || 'Ã' ||
             NVL(VC.COD_CONVENIO,' ') || 'Ã' ||
             NVL(PC.COD_CLI,' ')
            FROM   VTA_PEDIDO_VTA_CAB VC,
                     VTA_TIP_COMP TP,
                   CON_PED_VTA_CLI  PC
          WHERE  VC.COD_GRUPO_CIA =  cCodGrupoCia_in
          AND VC.COD_LOCAL     =  cCod_Local_in
          AND VC.EST_PED_VTA   =  'P'
      AND VC.TIP_PED_VTA <> '03'
          AND TP.TIP_COMP      =  VC.TIP_COMP_PAGO
          AND TP.COD_GRUPO_CIA =  VC.COD_GRUPO_CIA
          AND VC.NUM_PED_VTA=PC.NUM_PED_VTA(+); -- JCORTEZ 17/03/2008
    else
    dbms_output.put_line('3');
    OPEN curImpr FOR
        SELECT distinct NVL(INITCAP(LPAD(NVL(NOM_CLI_PED_VTA || ' ',' '),INSTR(NOM_CLI_PED_VTA || ' ', ' '))),' ')  || 'Ã' ||
               NVL(VC.NUM_PED_DIARIO,' ')   || 'Ã' ||
               NVL(TO_CHAR(VC.NUM_PED_VTA),' ') || 'Ã' ||
                NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ') || 'Ã' ||
               TRIM(TO_CHAR(NVL(VC.VAL_NETO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_IGV_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                 TRIM(TO_CHAR(NVL(VC.VAL_NETO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_BRUTO_PED_VTA,0),'999,999,990.00'))|| 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_DCTO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_REDONDEO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                NVL(TO_CHAR(VC.RUC_CLI_PED_VTA),' ')     || 'Ã' ||
             NVL(VC.NUM_PED_DIARIO,' ') || 'Ã' ||
             NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy'),' ')|| 'Ã' ||
             NVL(TO_CHAR(VC.FEC_PED_VTA,'yyyyMMdd' ),' ')||' '||NVL(VC.NUM_PED_DIARIO,' ')|| 'Ã' ||
             -- JCORTEZ 17/03/2008
             NVL(VC.IND_PED_CONVENIO ,' ') || 'Ã' ||
             NVL(VC.COD_CONVENIO,' ') || 'Ã' ||
             NVL(PC.COD_CLI,' ')
            FROM   VTA_PEDIDO_VTA_CAB VC,
                   VTA_PEDIDO_VTA_DET VD,
                     VTA_TIP_COMP TP,
                   CON_PED_VTA_CLI  PC
          WHERE  VC.COD_GRUPO_CIA =  cCodGrupoCia_in
          AND VC.COD_LOCAL     =  cCod_Local_in
          AND VC.EST_PED_VTA   =  'P'
      AND VC.TIP_PED_VTA <> '03'
          AND VC.COD_GRUPO_CIA = VD.COD_GRUPO_CIA
          AND VC.COD_LOCAL = VD.COD_LOCAL
          AND VC.NUM_PED_VTA = VD.NUM_PED_VTA
          AND VD.SEC_USU_LOCAL = cSec_Usu_in
          AND TP.TIP_COMP      =  VC.TIP_COMP_PAGO
          AND TP.COD_GRUPO_CIA =  VC.COD_GRUPO_CIA
          AND VC.NUM_PED_VTA=PC.NUM_PED_VTA(+); -- JCORTEZ 17/03/2008
    end if;
       else

          dbms_output.put_line('4');
        OPEN curImpr FOR

        SELECT NVL(INITCAP(LPAD(NVL(NOM_CLI_PED_VTA || ' ',' '),INSTR(NOM_CLI_PED_VTA || ' ', ' '))),' ')  || 'Ã' ||
               NVL(VC.NUM_PED_DIARIO,' ')   || 'Ã' ||
               NVL(TO_CHAR(VC.NUM_PED_VTA),' ') || 'Ã' ||
                NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ') || 'Ã' ||
               TRIM(TO_CHAR(NVL(VC.VAL_NETO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_IGV_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                 TRIM(TO_CHAR(NVL(VC.VAL_NETO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_BRUTO_PED_VTA,0),'999,999,990.00'))|| 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_DCTO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_REDONDEO_PED_VTA,0),'999,999,990.00'))  || 'Ã' ||
                NVL(TO_CHAR(VC.RUC_CLI_PED_VTA),' ')     || 'Ã' ||
             NVL(VC.NUM_PED_DIARIO,' ') || 'Ã' ||
             NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy'),' ')|| 'Ã' ||
             NVL(TO_CHAR(VC.FEC_PED_VTA,'yyyyMMdd' ),' ')||' '||NVL(VC.NUM_PED_DIARIO,' ')|| 'Ã' ||
             -- JCORTEZ 17/03/2008
             NVL(VC.IND_PED_CONVENIO ,' ') || 'Ã' ||
             NVL(VC.COD_CONVENIO,' ') || 'Ã' ||
             NVL(PC.COD_CLI,' ')
            FROM   VTA_PEDIDO_VTA_CAB VC,
                     VTA_TIP_COMP TP,
                   CON_PED_VTA_CLI  PC
          WHERE  VC.COD_GRUPO_CIA =  cCodGrupoCia_in
          AND VC.COD_LOCAL     =  cCod_Local_in
          AND VC.EST_PED_VTA   =  'P'
      AND VC.TIP_PED_VTA <> '03'
          AND TP.TIP_COMP      =  VC.TIP_COMP_PAGO
          AND TP.COD_GRUPO_CIA =  VC.COD_GRUPO_CIA
          AND VC.NUM_PED_VTA=PC.NUM_PED_VTA(+); -- JCORTEZ 17/03/2008
       end if;
    RETURN curImpr;
  END;

    /**************************************************************************************************/

  FUNCTION CAJ_LISTA_DET_PEDIDOS_PENDIENT(cCodGrupoCia_in IN CHAR,
                               cCod_Local_in   IN CHAR,
                          cNum_Ped_Vta_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR

    SELECT NVL(TO_CHAR(DET.COD_PROD),' ')        || 'Ã' ||
              NVL(TO_CHAR(P.DESC_PROD),' ')         || 'Ã' ||
           NVL(TO_CHAR(DET.UNID_VTA),' ')        || 'Ã' ||
            --NVL(TO_CHAR(DET.VAL_PREC_VTA),' ')    || 'Ã' ||
            --NVL(TO_CHAR(DET.PORC_DCTO_TOTAL),' ') || 'Ã' ||
            TO_CHAR(NVL(DET.VAL_PREC_VTA,0),'999,999,990.000')  || 'Ã' ||
            TO_CHAR(NVL(DET.CANT_ATENDIDA,0),'999,999,990.00')   || 'Ã' ||
            TO_CHAR( ( NVL(DET.VAL_PREC_TOTAL,0) ),'999,999,990.00')|| 'Ã' ||
           nvl(U.LOGIN_USU,' ')
      FROM  VTA_PEDIDO_VTA_DET DET,
           LGT_PROD P,
          pbl_usu_local u
      WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in   AND
        DET.COD_LOCAL     = cCod_Local_in     AND
        DET.NUM_PED_VTA   = cNum_Ped_Vta_in  AND
        P.COD_GRUPO_CIA   = DET.COD_GRUPO_CIA AND
        P.COD_PROD        = DET.COD_PROD      and
        DET.COD_GRUPO_CIA = U.COD_GRUPO_CIA AND
        DET.COD_LOCAL = U.COD_LOCAL AND
        DET.SEC_USU_LOCAL = U.SEC_USU_LOCAL  ;


    RETURN curDet;
  END;

   /**************************************************************************************************/

  PROCEDURE CAJ_CAMBIA_ESTADO_PEDIDO(cCodGrupoCia_in IN CHAR,
                         cCod_Local_in   IN CHAR,
                       cNum_Ped_Vta_in IN CHAR,
                       cEst_in         IN CHAR,
                   cCodUsu_in    IN CHAR)
  IS

  BEGIN
       UPDATE VTA_PEDIDO_VTA_CAB SET FEC_MOD_PED_VTA_CAB = SYSDATE, USU_MOD_PED_VTA_CAB = cCodUsu_in,
       EST_PED_VTA=cEst_in,
                                   -- DUBILLUZ 03.10.2014
                            TIP_COMP_PAGO = (CASE
                                                   WHEN TIP_COMP_PAGO = '05' THEN '01'
                                                   WHEN TIP_COMP_PAGO = '06' THEN '02'
                                                   ELSE TIP_COMP_PAGO
                                                  END
                                                  )
     WHERE
        COD_GRUPO_CIA = cCodGrupoCia_in AND
          COD_LOCAL     = cCod_Local_in   AND
        NUM_PED_VTA   = cNum_Ped_Vta_in;


  END;

   /**************************************************************************************************/


  FUNCTION CAJ_LISTA_RELACION_PEDIDO_COMP(cCodGrupoCia_in IN CHAR,
                                 cCod_Local_in   IN CHAR,
                            cSecMovCaja_in  IN CHAR,
                            cMostrar_in IN CHAR DEFAULT 'S')
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
  DBMS_OUTPUT.PUT_LINE('  cMostrar_in: '||cMostrar_in);
  --JMIRANDA 12.01.2011 CAMBIO PARA MOSTRAR * EN EL MONTO DE COMPROBANTE
  IF cMostrar_in = 'N' THEN
   --NO MOSTRAR
       OPEN curDet FOR

    SELECT NVL(TO_CHAR(VC.NUM_PED_VTA),' ')                                           || 'Ã' ||
         NVL(TO_CHAR(TP.DESC_COMP),' ')                                             || 'Ã' ||
         -- INICIO
         --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
            --NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')                                       || 'Ã' ||
            -- FIN
            NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ')                 || 'Ã' ||
         --NVL(TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,999,990.00'),' ')|| 'Ã' ||
         '****' || 'Ã' ||
         'COBRADO' || 'Ã' ||
         ' '   || 'Ã' ||
         NVL(TO_CHAR(TP.TIP_COMP),' ')|| --NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')           || 'Ã' ||
                  --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
         NVL(TO_CHAR(TP.TIP_COMP),' ') || NVL(TO_CHAR(CP.NUM_PED_VTA),' ')
    FROM   VTA_PEDIDO_VTA_CAB VC,
          VTA_TIP_COMP TP,
         VTA_COMP_PAGO CP
    WHERE  VC.COD_GRUPO_CIA = cCodGrupoCia_in  AND
         VC.COD_LOCAL     = cCod_Local_in    AND
         VC.EST_PED_VTA = EST_PED_COBRADO     AND
         VC.SEC_MOV_CAJA=cSecMovCaja_in     AND
         TP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_LOCAL     = VC.COD_LOCAL     AND
         CP.NUM_PED_VTA   = VC.NUM_PED_VTA   AND
         TP.TIP_COMP=CP.TIP_COMP_PAGO
    UNION
    SELECT NVL(TO_CHAR(VC.NUM_PED_VTA),' ')                                           || 'Ã' ||
         NVL(TO_CHAR(TP.DESC_COMP),' ')                                             || 'Ã' ||
-- INICIO
         --RH: 06.10.2014 FAC-ELECTRONICA
         --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
            --NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')                                       || 'Ã' ||
-- FIN
         NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ')                 || 'Ã' ||
  -- 2009-10-30 JOLIVA: SE CORRIGE EL SIGNO
         --NVL(TO_CHAR((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO)*-1,'999,999,990.00'),' ')    || 'Ã' ||
         '****' || 'Ã' ||
         'ANULADO' || 'Ã' ||
         ' '  || 'Ã' ||
         NVL(TO_CHAR(TP.TIP_COMP),' ')|| --NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')             || 'Ã' ||
                  --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
         NVL(TO_CHAR(TP.TIP_COMP),' ') || NVL(TO_CHAR(CP.NUM_PED_VTA),' ')
    FROM   VTA_PEDIDO_VTA_CAB VC,
         VTA_TIP_COMP TP,
         VTA_COMP_PAGO CP
    WHERE  VC.COD_GRUPO_CIA = cCodGrupoCia_in  AND
         VC.COD_LOCAL     = cCod_Local_in    AND
         VC.EST_PED_VTA =EST_PED_COBRADO     AND
         VC.SEC_MOV_CAJA=cSecMovCaja_in     AND
         TP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_LOCAL     = VC.COD_LOCAL     AND
         CP.NUM_PEDIDO_ANUL   = VC.NUM_PED_VTA   AND
         TP.TIP_COMP=CP.TIP_COMP_PAGO;

      --FIN NO MOSTRAR
   ELSE

   OPEN curDet FOR

    SELECT NVL(TO_CHAR(VC.NUM_PED_VTA),' ')                                           || 'Ã' ||
         NVL(TO_CHAR(TP.DESC_COMP),' ')                                             || 'Ã' ||
-- INICIO
         --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
            --NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')                                       || 'Ã' ||
-- FIN
            NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ')                 || 'Ã' ||
         NVL(TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,999,990.00'),' ')|| 'Ã' ||
         'COBRADO' || 'Ã' ||
         ' '   || 'Ã' ||
         NVL(TO_CHAR(TP.TIP_COMP),' ')||-- NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')           || 'Ã' ||
                  --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
         NVL(TO_CHAR(TP.TIP_COMP),' ') || NVL(TO_CHAR(CP.NUM_PED_VTA),' ')
    FROM   VTA_PEDIDO_VTA_CAB VC,
          VTA_TIP_COMP TP,
         VTA_COMP_PAGO CP
    WHERE  VC.COD_GRUPO_CIA = cCodGrupoCia_in  AND
         VC.COD_LOCAL     = cCod_Local_in    AND
         VC.EST_PED_VTA = EST_PED_COBRADO     AND
         VC.SEC_MOV_CAJA=cSecMovCaja_in     AND
         TP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_LOCAL     = VC.COD_LOCAL     AND
         CP.NUM_PED_VTA   = VC.NUM_PED_VTA   AND
         TP.TIP_COMP=CP.TIP_COMP_PAGO
    UNION
    SELECT NVL(TO_CHAR(VC.NUM_PED_VTA),' ')                                           || 'Ã' ||
         NVL(TO_CHAR(TP.DESC_COMP),' ')                                             || 'Ã' ||
-- INICIO
         --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
            --NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')                                       || 'Ã' ||
-- FIN
         NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ')                 || 'Ã' ||
         --NVL(TO_CHAR(VC.VAL_NETO_PED_VTA - VC.VAL_REDONDEO_PED_VTA,'999,999,990.00'),' ')    || 'Ã' ||
         --NVL(TO_CHAR(CP.VAL_NETO_COMP_PAGO - CP.VAL_REDONDEO_COMP_PAGO,'999,999,990.00'),' ')    || 'Ã' || --JMIRANDA
--         NVL(TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,999,990.00'),' ')    || 'Ã' ||
-- 2009-10-30 JOLIVA: SE CORRIGE EL SIGNO
         NVL(TO_CHAR((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO)*-1,'999,999,990.00'),' ')    || 'Ã' ||
         'ANULADO' || 'Ã' ||
         ' '  || 'Ã' ||
         NVL(TO_CHAR(TP.TIP_COMP),' ')|| --NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')             || 'Ã' ||
                  --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
         NVL(TO_CHAR(TP.TIP_COMP),' ') || NVL(TO_CHAR(CP.NUM_PED_VTA),' ')
    FROM   VTA_PEDIDO_VTA_CAB VC,
         VTA_TIP_COMP TP,
         VTA_COMP_PAGO CP
    WHERE  VC.COD_GRUPO_CIA = cCodGrupoCia_in  AND
         VC.COD_LOCAL     = cCod_Local_in    AND
         VC.EST_PED_VTA =EST_PED_COBRADO     AND
         VC.SEC_MOV_CAJA=cSecMovCaja_in     AND
         TP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_LOCAL     = VC.COD_LOCAL     AND
         CP.NUM_PEDIDO_ANUL   = VC.NUM_PED_VTA   AND
         TP.TIP_COMP=CP.TIP_COMP_PAGO;
/*
     SELECT   NVL(TO_CHAR(VC.NUM_PED_VTA),' ')                                           || 'Ã' ||
                NVL(TO_CHAR(TP.DESC_COMP),' ')                                             || 'Ã' ||
                 NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')                                       || 'Ã' ||
                 NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ')                 || 'Ã' ||
                NVL(TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,999,990.00'),' ') || 'Ã' ||
                CASE VC.EST_PED_VTA
               WHEN EST_PED_PENDIENTE THEN 'Pendiente'
                 WHEN EST_PED_ANULADO   THEN 'Anulado'
             WHEN EST_PED_COBRADO   THEN 'Cobrado'
             ELSE ' '
          END
                                                                     || 'Ã' ||
        ' '                                               || 'Ã' ||
        ' '
        FROM   VTA_PEDIDO_VTA_CAB VC,
           VTA_TIP_COMP TP,
         VTA_COMP_PAGO CP
      WHERE  VC.COD_GRUPO_CIA = cCodGrupoCia_in  AND
         VC.COD_LOCAL     = cCod_Local_in    AND
         VC.EST_PED_VTA =EST_PED_COBRADO     AND
         VC.SEC_MOV_CAJA=cSecMovCaja_in     AND
             TP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_LOCAL     = VC.COD_LOCAL     AND
         CP.NUM_PED_VTA   = VC.NUM_PED_VTA   AND
         TP.TIP_COMP=VC.TIP_COMP_PAGO
        UNION
        SELECT   NVL(TO_CHAR(VC.NUM_PED_VTA),' ')                                           || 'Ã' ||
                NVL(TO_CHAR(TP.DESC_COMP),' ')                                             || 'Ã' ||
                 NVL(TO_CHAR(CP.NUM_COMP_PAGO),' ')                                       || 'Ã' ||
                 NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ')                 || 'Ã' ||
                NVL(TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,999,990.00'),' ') || 'Ã' ||
                CASE VC.EST_PED_VTA
               WHEN EST_PED_PENDIENTE THEN 'Pendiente'
                 WHEN EST_PED_ANULADO   THEN 'Anulado'
             WHEN EST_PED_COBRADO   THEN 'Cobrado'
             ELSE ' '
          END
                                                                     || 'Ã' ||
        ' '                                               || 'Ã' ||
        ' '
        FROM   VTA_PEDIDO_VTA_CAB VC,
           VTA_TIP_COMP TP,
         VTA_COMP_PAGO CP
      WHERE  VC.COD_GRUPO_CIA = cCodGrupoCia_in  AND
         VC.COD_LOCAL     = cCod_Local_in    AND
         VC.EST_PED_VTA =EST_PED_COBRADO     AND
         VC.SEC_MOV_CAJA=cSecMovCaja_in     AND
             TP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_LOCAL     = VC.COD_LOCAL     AND
         CP.NUM_PEDIDO_ANUL   = VC.NUM_PED_VTA   AND
         TP.TIP_COMP=VC.TIP_COMP_PAGO;
     */

     END IF;

   RETURN curDet;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_INFO_PEDIDO(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in      IN CHAR,
                                   cNumPedDiario_in IN CHAR,
                                   cFecPedVta_in    IN CHAR,
                                   nValorSelCopago_in number default -1)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
         SELECT VTA_CAB.NUM_PED_VTA || 'Ã' ||
               TO_CHAR(VTA_CAB.VAL_NETO_PED_VTA,'999,990.00') || 'Ã' ||
               TO_CHAR((VTA_CAB.VAL_NETO_PED_VTA / VTA_CAB.VAL_TIP_CAMBIO_PED_VTA) + DECODE(VTA_CAB.IND_DISTR_GRATUITA,'N',DECODE(VTA_CAB.VAL_NETO_PED_VTA,0,0,0.05),0.00),'999,990.00') || 'Ã' ||
               TO_CHAR(VTA_CAB.VAL_TIP_CAMBIO_PED_VTA,'990.00') || 'Ã' ||
               NVL(VTA_CAB.TIP_COMP_PAGO,' ') || 'Ã' ||
               -- KMONCADA 01.09.2014 EN CASO DE CONVENIO MUESTRE LOS DOCUMENTOS A IMPRIMIR
               CASE
                 WHEN VTA_CAB.COD_CONVENIO IS NOT NULL AND VTA_CAB.TIP_PED_VTA <> TIPO_VTA_INSTITUCIONAL THEN
                   PTOVENTA_CONV_BTLMF.BTLMF_F_VARCHAR_MSG_COMP(cCodGrupoCia_in,VTA_CAB.COD_CONVENIO,VTA_CAB.VAL_NETO_PED_VTA,nValorSelCopago_in)
                 ELSE
                   DECODE(VTA_CAB.TIP_COMP_PAGO,COD_TIP_COMP_BOLETA,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',COD_TIP_COMP_TICKET,'TICKET','OTRO') END || 'Ã' ||
               NVL(VTA_CAB.NOM_CLI_PED_VTA,' ') || 'Ã' ||
               NVL(VTA_CAB.RUC_CLI_PED_VTA,' ') || 'Ã' ||
               NVL(VTA_CAB.DIR_CLI_PED_VTA,' ') || 'Ã' ||
               NVL(VTA_CAB.TIP_PED_VTA,' ') || 'Ã' ||
               TO_CHAR(VTA_CAB.FEC_PED_VTA,'dd/MM/yyyy')  || 'Ã' ||
               NVL(VTA_CAB.IND_DISTR_GRATUITA,' ') || 'Ã' ||
               NVL(VTA_CAB.IND_DELIV_AUTOMATICO,' ') || 'Ã' ||
               VTA_CAB.CANT_ITEMS_PED_VTA || 'Ã' ||
               NVL(VTA_CAB.IND_PED_CONVENIO,' ') || 'Ã' ||
               NVL(VTA_CAB.COD_CONVENIO,' ')|| 'Ã'||
               NVL(VTA_CAB.COD_CLI_CONV,' ') -- SE GUARDA EN TABAL CABECERA PEDIDO
    --RHERRERA: SE OBTIENE INFORMACION DEL PEDIDO DE LA CABECERA DEL PEDIDO
           --  NVL(VC.COD_CONVENIO,' ') || 'Ã' ||
             --  NVL(VC.COD_CLI,' ')
             /*AGREGADO PARA VENTA EMPRESA*/
               || 'Ã'||
               NVL(VTA_CAB.PUNTO_LLEGADA,' ' )    || 'Ã'||
               NVL(VTA_CAB_EMP.NUM_OC,' ')|| 'Ã'||
               -- KMONCADA 30.06.2014 MENSAJE DE POLIZA EN EL CASO DE VENTA EMPRESA
               (CASE
                  WHEN VTA_CAB_EMP.COD_POLIZA IS NOT NULL
                        AND LENGTH(VTA_CAB_EMP.COD_POLIZA)> 1
                    THEN 'ATENCION BOTIQUIN '||VTA_CAB_EMP.NOMBRE_CLIENTE_POLIZA||' POLIZA N° '||VTA_CAB_EMP.COD_POLIZA||'.'
                  ELSE ' ' END)|| 'Ã'||
               (
               CASE
                 -- dubilluz 15.09.2014
                  WHEN VTA_CAB.PCT_BENEFICIARIO IS NULL THEN '-1'
                  ELSE to_char(VTA_CAB.PCT_BENEFICIARIO,'99999990.00') END
               )
        FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
              -- ,CON_PED_VTA_CLI VC -- RHERRERA: USO EN CONVENIO ANTIGUO
              -- KMONCADA
              LEFT JOIN VTA_PEDIDO_VTA_CAB_EMP VTA_CAB_EMP
              ON VTA_CAB.COD_GRUPO_CIA=VTA_CAB_EMP.COD_GRUPO_CIA AND VTA_CAB.COD_LOCAL=VTA_CAB_EMP.COD_LOCAL
              AND VTA_CAB.NUM_PED_VTA=VTA_CAB_EMP.NUM_PED_VTA
        WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    VTA_CAB.COD_LOCAL = cCodLocal_in
        AND     VTA_CAB.NUM_PED_DIARIO = cNumPedDiario_in
        AND    TO_CHAR(VTA_CAB.FEC_PED_VTA,'dd/MM/yyyy') = DECODE(cFecPedVta_in,NULL,TO_CHAR(SYSDATE,'dd/MM/yyyy'),TO_CHAR(TO_DATE(cFecPedVta_in,'dd/MM/yyyy'),'dd/MM/yyyy'))
        AND    VTA_CAB.EST_PED_VTA IN (EST_PED_PENDIENTE,EST_PED_COB_NO_IMPR)
        --AND    VTA_CAB.COD_GRUPO_CIA = VC.COD_GRUPO_CIA(+)
        --AND    VTA_CAB.COD_LOCAL = VC.COD_LOCAL(+)
        --AND    VTA_CAB.NUM_PED_VTA = VC.NUM_PED_VTA(+)
        ;
    RETURN curCaj;
  END;


  /* ********************************************************************************************** */
  --  06/09/2007  DUBILLUZ  MODIFICACION
 /* FUNCTION CAJ_OBTIENE_FORMAS_PAGO(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in      IN CHAR,
                       cIndPedConvenio  IN CHAR,
                       cCodConvenio     IN CHAR,
                       cCodCli_in       IN CHAR,
                       cNumPed_in       IN CHAR default '')
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
-- IF cIndPedConvenio = 'N' THEN
--    OPEN curCaj FOR
\*       SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;
*\
   IF cIndPedConvenio = 'N' THEN
    OPEN curCaj FOR
       SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;
  ELSIF cIndPedConvenio = 'S' THEN
   --RETORNA LAS FORMA DE PAGO DEL CONVENIO
     curCaj := CAJ_GET_FORMAS_PAGO_CONVENIO(cCodGrupoCia_in ,cCodLocal_in,cIndPedConvenio,cCodConvenio,cCodCli_in)  ;
   END IF;

   RETURN curCaj;
  END;
  */


  /* ********************************************************************************************** */
  --  11/12/2008  ASOLIS  CREACION
  FUNCTION CAJ_OBTIENE_FORMAS_PAG_CONV(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in      IN CHAR,
                       cIndPedConvenio  IN CHAR,
                       cCodConvenio     IN CHAR,
                       cCodCli_in       IN CHAR,
                       cNumPed_in       IN CHAR default '',
                       cValorCredito   IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN


   --RETORNA LAS FORMA DE PAGO DEL CONVENIO
     curCaj := CAJ_GET_FORMAS_PAGO_CONVENIO(cCodGrupoCia_in ,cCodLocal_in,cIndPedConvenio,cCodConvenio,cCodCli_in,cValorCredito)  ;


   RETURN curCaj;
  END;

  /* ********************************************************************************************** */
  --  11/12/2008  ASOLIS  CREACION
  FUNCTION CAJ_OBTIENE_FORMAS_PAG_SINCONV(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in      IN CHAR,
                       cIndPedConvenio  IN CHAR,
                       cCodConvenio     IN CHAR,
                       cCodCli_in       IN CHAR,
                       cNumPed_in       IN CHAR default NULL)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    -- dubilluz 09.06.2011
    cIndEfectivo varchar2(20) := 'N';
    cIndTarjeta varchar2(20) := 'N';
    cCodFormaPago varchar2(20) := 'N';
  BEGIN
  --aqui SE COLOCARÁ LA LOGICA DE FORMAS DE PAGO para FIDELIZADO.
  -- DADO QUE FIDELIZADO ES Y SERA SIN CONVENIO
  -- dubilluz 09.06.2011
    --ERIOS 2.4.5 Se corrige la venta fidelizado
  IF cNumPed_in IS NOT NULL THEN
    select nvl(IND_FP_FID_EFECTIVO,'N'),nvl(IND_FP_FID_TARJETA,'N'),
       nvl(COD_FP_FID_TARJETA,'N')
    into   cIndEfectivo,cIndTarjeta,cCodFormaPago
    from   vta_pedido_vta_cab c
    where  c.cod_grupo_cia = cCodGrupoCia_in
    and    c.cod_local  = cCodLocal_in
    and    c.num_ped_vta = cNumPed_in;
  END IF;

  if cIndEfectivo || cIndTarjeta || cCodFormaPago = 'NNN' then
  -- LISTA TODAS LAS FORMAS DE PAGO
    OPEN curCaj FOR
       SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      -- NO DEBE DE LISTAR FORMA DE PAGO CONVENIO CREDITO BTL MF
      AND    FORMA_PAGO.COD_FORMA_PAGO != '00080'
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;

  else
      if cIndEfectivo = 'S' and cIndTarjeta = 'N' and cCodFormaPago = 'N' then
      -- SOLO EFECTIVO
          OPEN curCaj FOR
           SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
                 FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                 NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
                 NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
                 NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
                 NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
          FROM   VTA_FORMA_PAGO FORMA_PAGO,
                 VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
          WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
          AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
          AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
          AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
          AND    FORMA_PAGO.IND_FORMA_PAGO_EFECTIVO = 'S'
          AND    FORMA_PAGO.IND_TARJ = 'N'
      -- NO DEBE DE LISTAR FORMA DE PAGO CONVENIO CREDITO BTL MF
      AND    FORMA_PAGO.COD_FORMA_PAGO != '00080'
          AND    FORMA_PAGO.COD_FORMA_PAGO IN ('00001','00002')
          AND    FORMA_PAGO.COD_CONVENIO IS NULL
          AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
          AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;
      ELSE
          IF cIndEfectivo = 'N' and cIndTarjeta = 'S' and cCodFormaPago != 'N' THEN
             -- SOLO TARJETA
             --ERIOS 17.10.2013 Se muestra las formas de pago hijo
              OPEN curCaj FOR
               SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
                     FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                     NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
                     NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
                     NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
                     NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
              FROM   VTA_FORMA_PAGO FORMA_PAGO,
                     VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
                     VTA_REL_FORMA_PAGO RFP
              WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
              AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
              AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = 'A'
              AND    FORMA_PAGO.EST_FORMA_PAGO             = 'A'
              AND    FORMA_PAGO.IND_TARJ = 'S'
              AND    FORMA_PAGO.IND_FORMA_PAGO_EFECTIVO = 'N'
              AND    ('T0000' = cCodFormaPago OR FORMA_PAGO.COD_FORMA_PAGO = cCodFormaPago OR rfp.cod_forma_PAGO = cCodFormaPago)
              AND    FORMA_PAGO.COD_CONVENIO IS NULL
              AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
              AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
              AND RFP.COD_GRUPO_CIA(+) = FORMA_PAGO.COD_GRUPO_CIA
              AND rfp.cod_forma_hijo(+) = FORMA_PAGO.COD_FORMA_PAGO;


          ELSE
           -- COMO SIEMPRE TODAS
                     -- LISTA TODAS LAS FORMAS DE PAGO
              OPEN curCaj FOR
                 SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
                       FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                       NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
                       NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
                       NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
                       NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
                FROM   VTA_FORMA_PAGO FORMA_PAGO,
                       VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
                WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
                AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
                AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
                AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
                AND    FORMA_PAGO.COD_CONVENIO IS NULL
                AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
                AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;

          END IF;

      end if;

  end if;


   RETURN curCaj;
  END;

/* ********************************************************************************************** */

 FUNCTION CAJ_LISTA_APERTURAS_DIA(cCodGrupoCia_in IN CHAR,
                         cCod_Local_in   IN CHAR
                 )
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT     CP.SEC_MOV_CAJA                                     || 'Ã' ||
           TO_CHAR(MC.FEC_DIA_VTA,'dd/MM/yyyy')           || 'Ã' ||
           NVL(TO_CHAR(MC.NUM_CAJA_PAGO),' ')             || 'Ã' ||
           NVL(TO_CHAR(Mc.NUM_TURNO_CAJA),' ')             || 'Ã' ||
                 CASE MC.TIP_MOV_CAJA
               WHEN TIP_MOV_APERTURA
             THEN 'Apertura'
             ELSE 'Cierre'
           END                                || 'Ã' ||
               UL.NOM_USU ||' '|| UL.APE_PAT                   || 'Ã' ||
           TO_CHAR(FEC_CREA_MOV_CAJA,'dd/MM/yyyy HH24:mi:ss')  || 'Ã' ||
           TO_CHAR(FEC_CREA_MOV_CAJA,'dd/MM/yyyy HH24:mi:ss')  || 'Ã' ||
           MC.TIP_MOV_CAJA                      || 'Ã' ||
           MC.NUM_CAJA_PAGO                     || 'Ã' ||
           NVL(TO_CHAR(MC.NUM_TURNO_CAJA),' ')           || 'Ã' ||
           NVL(MC.SEC_MOV_CAJA_ORIGEN,' ')             || 'Ã' ||
           NVL(LPAD(TO_CHAR(MC.NUM_CAJA_PAGO),2,'0'),' ') || NVL(LPAD(TO_CHAR(Mc.NUM_TURNO_CAJA),2,'0'),' ')
     FROM CE_MOV_CAJA MC,PBL_USU_LOCAL UL, VTA_CAJA_PAGO CP
     WHERE MC.FEC_DIA_VTA = TO_CHAR(SYSDATE,'dd/MM/yyyy')AND
          CP.COD_GRUPO_CIA = cCodGrupoCia_in       AND
         CP.COD_LOCAL     = cCod_Local_in        AND
         UL.COD_GRUPO_CIA = CP.COD_GRUPO_CIA       AND
          UL.COD_LOCAL     = CP.COD_LOCAL           AND
         UL.SEC_USU_LOCAL = CP.SEC_USU_LOCAL       AND
         CP.COD_GRUPO_CIA = MC.COD_GRUPO_CIA       AND
         CP.COD_LOCAL     = MC.COD_LOCAL           AND
         CP.SEC_MOV_CAJA  = MC.SEC_MOV_CAJA       ;

    RETURN curDet;
  END;

  /*******************************************************************************************************/

  /*******************************************************************************************************/

 FUNCTION CAJ_LISTA_MOVIMIENTOS_CAJA(cCodGrupoCia_in IN CHAR,
                            cCod_Local_in   IN CHAR,
                     cFecDiaVta_in   IN CHAR)

  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR

     SELECT SEC_MOV_CAJA                                      || 'Ã' ||
         TO_CHAR(FEC_DIA_VTA,'dd/MM/yyyy')          || 'Ã' ||
         NVL(TO_CHAR(NUM_CAJA_PAGO),' ')                 || 'Ã' ||
         NVL(TO_CHAR(NUM_TURNO_CAJA),' ')              || 'Ã' ||
               CASE TIP_MOV_CAJA
               WHEN TIP_MOV_APERTURA THEN 'Apertura'
               WHEN TIP_MOV_CIERRE   THEN 'Cierre'
               WHEN TIP_MOV_ARQUEO   THEN 'Arqueo'
         END                                  || 'Ã' ||
         UL.NOM_USU ||' '|| UL.APE_PAT               || 'Ã' ||
         --20/12/2007 DUBILLUZ MODIFICACION
         DECODE(TIP_MOV_CAJA,TIP_MOV_CIERRE,
                DECODE(MC.IND_VB_CAJERO,INDICADOR_SI,NVL(TO_CHAR(MC.FEC_CIERRE_TURNO_CAJA,'dd/MM/yyyy HH24:mi:ss'),' '),' '),' ') || 'Ã' ||
         TO_CHAR(FEC_CREA_MOV_CAJA,'dd/MM/yyyy HH24:mi:ss') || 'Ã' ||
         MC.TIP_MOV_CAJA                     || 'Ã' ||
         NVL(TO_CHAR(MC.NUM_CAJA_PAGO),' ')          || 'Ã' ||
         NVL(TO_CHAR(MC.NUM_TURNO_CAJA),' ')          || 'Ã' ||
         NVL(MC.SEC_MOV_CAJA_ORIGEN,' ')            || 'Ã' ||
         NVL(LPAD(TO_CHAR(MC.NUM_CAJA_PAGO),2,'0'),' ') || NVL(LPAD(TO_CHAR(Mc.NUM_TURNO_CAJA),2,'0'),' ') || SEC_MOV_CAJA
     FROM CE_MOV_CAJA MC,PBL_USU_LOCAL UL
     WHERE MC.COD_GRUPO_CIA = cCodGrupoCia_in             AND
          MC.COD_LOCAL     = cCod_Local_in             AND
         TO_CHAR(MC.FEC_DIA_VTA,'dd/MM/yyyy') = cFecDiaVta_in  AND
         MC.COD_GRUPO_CIA = UL.COD_GRUPO_CIA                  AND
          MC.COD_LOCAL     = UL.COD_LOCAL                AND
         MC.SEC_USU_LOCAL = UL.SEC_USU_LOCAL         ;

    RETURN curDet;
  END;


 /*********************************************************************************************************/

   FUNCTION CAJ_OBTIENE_VALORES_ARQUEO(cCodGrupoCia_in IN CHAR,
                              cCod_Local_in   IN CHAR,
                         cSecMovCaja_in IN CHAR
                 )
  RETURN FarmaCursor
    IS
       curDet FarmaCursor;
    BEGIN
     OPEN curDet FOR
        SELECT 'N' || 'Ã' || VP.TIP_COMP_PAGO || 'Ã' || COUNT(VP.SEC_COMP_PAGO) || 'Ã' ||
               TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),
                            '999,990.00'))
          FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
         WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VP.COD_LOCAL = cCod_Local_in
           AND VPC.SEC_MOV_CAJA = cSecMovCaja_in
           AND VPC.EST_PED_VTA = EST_PED_COBRADO
           AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
           AND vpc.cod_local = vp.cod_local
           AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
         GROUP BY 'N', VP.TIP_COMP_PAGO

        UNION

        SELECT 'S' || 'Ã' || VP.TIP_COMP_PAGO || 'Ã' || COUNT(VP.SEC_COMP_PAGO) || 'Ã' ||
               TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VAL_REDONDEO_COMP_PAGO),
                            '999,990.00'))
          FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
         WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VP.COD_LOCAL = cCod_Local_in
           AND VPC.SEC_MOV_CAJA = cSecMovCaja_in
           AND vpc.EST_PED_VTA = EST_PED_COBRADO
           AND VP.IND_COMP_ANUL = INDICADOR_SI
           AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
           AND vpc.cod_local = vp.cod_local
           AND VPC.NUM_PED_VTA = VP.NUM_PEDIDO_ANUL
         GROUP BY 'S', VP.TIP_COMP_PAGO

        UNION

        SELECT 'NC' || 'Ã' || VPC2.TIP_COMP_PAGO || 'Ã' || COUNT(VPC.NUM_PED_VTA) || 'Ã' ||
               TO_CHAR(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),
                       '999,990.00')
          FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
         WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VPC.COD_LOCAL = cCod_Local_in
           AND VPC.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED
           AND VPC.EST_PED_VTA = EST_PED_COBRADO
           AND VPC2.TIP_COMP_PAGO = COD_TIP_COMP_BOLETA
           AND VPC.SEC_MOV_CAJA = cSecMovCaja_in
           AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
           AND VPC.COD_LOCAL = VPC2.COD_LOCAL
           AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
           AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
           AND VCP.COD_LOCAL = VPC.COD_LOCAL
           AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA

         GROUP BY VPC2.TIP_COMP_PAGO

        UNION

        SELECT 'NC' || 'Ã' || VPC2.TIP_COMP_PAGO || 'Ã' || COUNT(VPC.NUM_PED_VTA) || 'Ã' ||
               TO_CHAR(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),
                       '999,990.00')
          FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
         WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VPC.COD_LOCAL = cCod_Local_in
           AND VPC.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED
           AND VPC.EST_PED_VTA = EST_PED_COBRADO
           AND VPC2.TIP_COMP_PAGO = COD_TIP_COMP_FACTURA
           AND VPC.SEC_MOV_CAJA = cSecMovCaja_in
           AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
           AND VPC.COD_LOCAL = VPC2.COD_LOCAL
           AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
           AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
           AND VCP.COD_LOCAL = VPC.COD_LOCAL
           AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA

         GROUP BY VPC2.TIP_COMP_PAGO;



   RETURN curDet;
  END;

  /************************************************************************************************************/

 FUNCTION CAJ_OBTIENE_FORMAS_PAGO_ARQUEO(cCodGrupoCia_in IN CHAR,
                                cCod_Local_in   IN CHAR,
                           cSecMovCaja_in  IN CHAR,
                         cTipOp_in      IN CHAR
                          )
  RETURN FarmaCursor
   IS
    vcSecMov CHAR(10);
    curDet FarmaCursor;
   BEGIN

   IF cTipOp_in=TIP_MOV_ARQUEO THEN
      vcSecMov:=cSecMovCaja_in;
   ELSE
      SELECT SEC_MOV_CAJA_ORIGEN INTO vcSecMov
    FROM CE_MOV_CAJA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in  AND
        COD_LOCAL     = cCod_Local_in   AND
      SEC_MOV_CAJA  = cSecMovCaja_in   AND
      SEC_MOV_CAJA_ORIGEN IS NOT NULL ;
   END IF;

    OPEN curDet FOR
     SELECT FPP.COD_FORMA_PAGO      || 'Ã' ||
         FP.DESC_CORTA_FORMA_PAGO    || 'Ã' ||
         (SELECT INITCAP(DESC_CORTA)
          FROM PBL_TAB_GRAL
        WHERE COD_TAB_GRAL='MONEDA' AND
            LLAVE_TAB_GRAL=TIP_MONEDA)                      || 'Ã' ||
         TO_CHAR( SUM(FPP.IM_PAGO) ,'999,990.00')                || 'Ã' ||
         TO_CHAR( SUM(FPP.IM_TOTAL_PAGO /* - FPP.VAL_VUELTO*/ ),'999,990.00')  || 'Ã' ||
         TIP_MONEDA
      FROM
         VTA_FORMA_PAGO_PEDIDO FPP,
         VTA_FORMA_PAGO FP,
         VTA_FORMA_PAGO_LOCAL FPL,
         VTA_PEDIDO_VTA_CAB CAB
      WHERE CAB.COD_GRUPO_CIA  = cCodGrupoCia_in    AND
        CAB.COD_LOCAL      = cCod_Local_in      AND
        CAB.SEC_MOV_CAJA   = vcSecMov         AND
        CAB.EST_PED_VTA    = EST_PED_COBRADO   AND
        --CAB.IND_PEDIDO_ANUL    = 'N'         AND
        FP.COD_GRUPO_CIA   = FPL.COD_GRUPO_CIA  AND
        FP.COD_FORMA_PAGO  = FPL.COD_FORMA_PAGO AND
        fpl.cod_grupo_cia  = fpp.cod_grupo_cia  AND
        fpl.cod_local      = fpp.COD_LOCAL      AND
        FPL.COD_FORMA_PAGO = FPP.COD_FORMA_PAGO AND
        CAB.COD_GRUPO_CIA  = FPP.COD_GRUPO_CIA  AND
        CAB.COD_LOCAL      = FPP.COD_LOCAL      AND
        CAB.NUM_PED_VTA    = FPP.NUM_PED_VTA
       GROUP BY  FPP.COD_FORMA_PAGO,DESC_CORTA_FORMA_PAGO,TIP_MONEDA
       UNION
       SELECT '00000'      || 'Ã' ||
           'VUELTO'    || 'Ã' ||
           'SOLES'                      || 'Ã' ||
           TO_CHAR(NVL(SUM(FPP.VAL_VUELTO * -1),0),'999,990.00')  || 'Ã' ||
           TO_CHAR(NVL(SUM(FPP.VAL_VUELTO * -1),0),'999,990.00')  || 'Ã' ||
           '01'
        FROM
           VTA_FORMA_PAGO_PEDIDO FPP,
           VTA_FORMA_PAGO FP,
           VTA_FORMA_PAGO_LOCAL FPL,
           VTA_PEDIDO_VTA_CAB CAB
        WHERE CAB.COD_GRUPO_CIA  = cCodGrupoCia_in    AND
          CAB.COD_LOCAL      = cCod_Local_in      AND
          CAB.SEC_MOV_CAJA   = vcSecMov         AND
          CAB.EST_PED_VTA    = EST_PED_COBRADO   AND
          --CAB.IND_PEDIDO_ANUL    = 'N'         AND
          FP.COD_GRUPO_CIA   = FPL.COD_GRUPO_CIA  AND
          FP.COD_FORMA_PAGO  = FPL.COD_FORMA_PAGO AND
          fpl.cod_grupo_cia  = fpp.cod_grupo_cia  AND
          fpl.cod_local      = fpp.COD_LOCAL      AND
          FPL.COD_FORMA_PAGO = FPP.COD_FORMA_PAGO AND
          CAB.COD_GRUPO_CIA  = FPP.COD_GRUPO_CIA  AND
          CAB.COD_LOCAL      = FPP.COD_LOCAL      AND
          CAB.NUM_PED_VTA    = FPP.NUM_PED_VTA;

   RETURN curDet;
 END;

  /* ********************************************************************************************** */

  PROCEDURE CAJ_GRABAR_COMPROBANTE_PAGO(pCodGrupoCia_in         IN CHAR,
                                      pCodLocal_in            IN CHAR,
                          cNumPedVta_in          IN CHAR,
                       cSecCompPago_in         IN CHAR,
                      cTipCompPago_in         IN CHAR,
                      cNumCompPago_in         IN CHAR,
                      cSecMovCaja_in          IN CHAR,
                      nCantItemsComPago_in  IN NUMBER,
                      cCodCliLocal_in         IN CHAR,
                      nValBrutoCompPago_in    IN NUMBER,
                      nValNetoCompPago_in     IN NUMBER,
                      nValDctoCompPago_in     IN NUMBER,
                      nValAfectoCompPago_in   IN NUMBER,
                      nValIgvCompPago_in      IN NUMBER,
                      nValRedondeoCompPago_in IN NUMBER,
                       cUsuCreaCompPago_in     IN CHAR)
  IS
  v_rVtaPedidoVtaCab VTA_PEDIDO_VTA_CAB%ROWTYPE;
  BEGIN
  -- obteniendo registro de pedido
  SELECT *
  INTO   v_rVtaPedidoVtaCab
  FROM   VTA_PEDIDO_VTA_CAB
  WHERE  COD_GRUPO_CIA = pCodGrupoCia_in
  AND    COD_LOCAL     = pCodLocal_in
  AND    NUM_PED_VTA   = cNumPedVta_in;

    INSERT INTO VTA_COMP_PAGO(COD_GRUPO_CIA,
                   COD_LOCAL,
                NUM_PED_VTA,
                SEC_COMP_PAGO,
                TIP_COMP_PAGO,
                NUM_COMP_PAGO,
                SEC_MOV_CAJA,
                CANT_ITEM,
                COD_CLI_LOCAL,
                NOM_IMPR_COMP,
                DIREC_IMPR_COMP,
                NUM_DOC_IMPR,
                VAL_BRUTO_COMP_PAGO,
                VAL_NETO_COMP_PAGO,
                VAL_DCTO_COMP_PAGO,
                VAL_AFECTO_COMP_PAGO,
                VAL_IGV_COMP_PAGO,
                VAL_REDONDEO_COMP_PAGO,
                USU_CREA_COMP_PAGO)
            VALUES (pCodGrupoCia_in,
                       pCodLocal_in,
                   cNumPedVta_in,
                   cSecCompPago_in,
                TO_NUMBER(SUBSTR(cSecCompPago_in,-2))+10,
                   --cTipCompPago_in,
                cNumPedVta_in,
                --cNumCompPago_in,
                   cSecMovCaja_in,
                   nCantItemsComPago_in,
                   cCodCliLocal_in,
                   v_rVtaPedidoVtaCab.NOM_CLI_PED_VTA,
                   v_rVtaPedidoVtaCab.DIR_CLI_PED_VTA,
                   v_rVtaPedidoVtaCab.RUC_CLI_PED_VTA,
                   nValBrutoCompPago_in,
                nValNetoCompPago_in,
                nValDctoCompPago_in,
                nValAfectoCompPago_in,
                nValIgvCompPago_in,
                nValRedondeoCompPago_in,
                cUsuCreaCompPago_in);
  END;


    /***************************************************************************************************************/
   FUNCTION CAJ_REGISTRA_ARQUEO_CAJA(cTipMov_in       IN CHAR,
                                     cCodGrupoCia_in  IN CHAR,
                                     cCod_Local_in    IN CHAR,
                                     nNumCaj_in       IN NUMBER,
                                     cSecUsu_in       IN CHAR,
                                     cIdUsu_in        IN CHAR,
                                     nCantBolEmi_in   IN NUMBER,
                                     nMonBolEmi_in    IN NUMBER,
                                     nCantFacEmi_in   IN NUMBER,
                                     nMontFacEmi_in   IN NUMBER,
                                     nCantGuiaEmi_in  IN NUMBER,
                                     nMonGuiaEmi_in   IN NUMBER,
                                     nCantBolAnu_in   IN NUMBER,
                                     nMonBolAnu_in    IN NUMBER,
                                     nCantFacAnu_in   IN NUMBER,
                                     nMonFacAnu_in    IN NUMBER,
                                     nCantGuiaAnu_in  IN NUMBER,
                                     nMonGuiaAnu_in   IN NUMBER,
                                     nCantBolTot_in   IN NUMBER,
                                     nMonBolTot_in    IN NUMBER,
                                     nCantFactTot_in  IN NUMBER,
                                     nMonFactTot_in   IN NUMBER,
                                     nCantGuiaTot_in  IN NUMBER,
                                     nMonGuiaTot_in   IN NUMBER,
                                     nMonTotGen_in    IN NUMBER,
                                     nMonTotAnu_in    IN NUMBER,
                                     nMonTot_in       IN NUMBER,
                                     nCantNCBol_in    IN NUMBER,
                                     nMonNCBol_in     IN NUMBER,
                                     nCantNCFact_in   IN NUMBER,
                                     nMonNCFact_in    IN NUMBER,
                                     nMonNCTot_in     IN NUMBER,
                                     cIpMovCaja       IN CHAR
                                     --LLEIVA 11-Feb-2014 Se añaden los campos de Ticket-Factura
                                     ,nCantTickFacEmi  IN NUMBER,
                                     nMonTickFacEmi   IN NUMBER,
                                     nCantTickFacAnul IN NUMBER,
                                     nMonTickFacAnul  IN NUMBER,
                                     nCantNCTickFac   IN NUMBER,
                                     nMonNCTickFac    IN NUMBER,
                                     nCantTickFacTot  IN NUMBER,
                                     nMonTickFacTot   IN NUMBER
                                     )
    RETURN CHAR
  IS
    v_nNeoCod  CHAR(10);
    v_nNeoTur NUMBER;
    v_IndCajAb CHAR(1);
    v_nActTur  NUMBER;
    v_cSecOrigen CHAR(10);
    vCantPedInc NUMBER;
    vFecDiaVta DATE;
    TOTAL_CAJA_TURNO NUMBER;
    v_sec_caja_origen char(10);
    vCantPedInc_reca NUMBER;
  BEGIN
       --Selecciona para bloquear el registro
       SELECT IND_CAJA_ABIERTA
       INTO   v_IndCajAb
       FROM   VTA_CAJA_PAGO
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
              COD_LOCAL     = cCod_Local_in   AND
              NUM_CAJA_PAGO = nNumCaj_in FOR UPDATE;

       --Inserta Movimiento de Caja
       v_nNeoCod := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCod_Local_in,COD_NUM_SEC_MOV_CAJ),10,'0',POS_INICIO );
       v_cSecOrigen := CAJ_OBTENER_SEC_MOV_APERTURA(cCodGrupoCia_in,cCod_Local_in,nNumCaj_in);

       --Obtiene la FecDiaVta del movimiento de apertura

       SELECT FEC_DIA_VTA
       INTO   vFecDiaVta
       FROM   CE_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
              COD_LOCAL     = cCod_Local_in AND
              SEC_MOV_CAJA  = v_cSecOrigen;

       IF cTipMov_in=TIP_MOV_CIERRE THEN
          IF v_IndCajAb=INDICADOR_NO THEN
             RAISE_APPLICATION_ERROR(-20010,'No se puede cerrar una caja cuando ya se encuentra cerrada.');
          END IF;

          SELECT COUNT(*)
          INTO   vCantPedInc
          FROM   VTA_PEDIDO_VTA_CAB
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                  COD_LOCAL     = cCod_Local_in AND
                 EST_PED_VTA   = EST_PED_COB_NO_IMPR AND
                 SEC_MOV_CAJA  = (SELECT SEC_MOV_CAJA
                                   FROM   VTA_CAJA_PAGO
                                  WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                                           COD_LOCAL      = cCod_Local_in AND
                                          NUM_CAJA_PAGO = nNumCaj_in);

----- CAMBIA DE FECHA LOS PEDIDOS ---
           UPDATE VTA_PEDIDO_VTA_CAB CA
           SET    CA.FEC_PED_VTA = trunc(SYSDATE)+1/24/60,
                  CA.NUM_PED_DIARIO =
                               TRIM(TO_CHAR(9000 + TO_NUMBER(CA.NUM_PED_DIARIO,'0000'),
                                       '0000'))
           WHERE  (CA.COD_GRUPO_CIA,CA.COD_LOCAL,CA.NUM_PED_VTA)  IN
           (
           SELECT DISTINCT D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_PED_VTA
            FROM vta_pedido_vta_det d, lgt_prod_virtual p
           WHERE d.cod_grupo_cia = cCodGrupoCia_in
           AND   d.cod_local  = cCod_Local_in
             AND d.num_ped_vta IN
                 (SELECT VTA_CAB.NUM_PED_VTA
                    FROM VTA_PEDIDO_VTA_CAB VTA_CAB
                   WHERE VTA_CAB.cod_grupo_cia = cCodGrupoCia_in
                   AND   VTA_CAB.cod_local = cCod_Local_in
                     AND VTA_CAB.FEC_PED_VTA between
                                             trunc(vFecDiaVta)+1-1/24 and
                                             trunc(vFecDiaVta)+ 1-1/24/60/60
                     AND VTA_CAB.EST_PED_VTA = 'P')
             and d.cod_grupo_cia = p.cod_grupo_cia
             and d.cod_prod = p.cod_prod
             and p.tip_prod_virtual = 'R'
             AND D.SEC_USU_LOCAL = cSecUsu_in
            );

            SELECT count(D.NUM_PED_VTA) into vCantPedInc_reca
              FROM vta_pedido_vta_det d, lgt_prod_virtual p
             WHERE d.cod_grupo_cia = cCodGrupoCia_in
             AND   d.cod_local  = cCod_Local_in
               AND d.num_ped_vta IN
                   (SELECT VTA_CAB.NUM_PED_VTA
                      FROM VTA_PEDIDO_VTA_CAB VTA_CAB
                     WHERE VTA_CAB.cod_grupo_cia = cCodGrupoCia_in
                     AND   VTA_CAB.cod_local = cCod_Local_in
                       AND VTA_CAB.FEC_PED_VTA between
                                               trunc(vFecDiaVta) and
                                               trunc(vFecDiaVta)  + 1-1/24/60/60
                       AND VTA_CAB.EST_PED_VTA = 'P')
               and d.cod_grupo_cia = p.cod_grupo_cia
               and d.cod_prod = p.cod_prod
               and p.tip_prod_virtual = 'R'
               AND D.SEC_USU_LOCAL = cSecUsu_in;

          IF vCantPedInc+vCantPedInc_reca>0 THEN
             --RAISE_APPLICATION_ERROR(-20011,' No se puede cerrar caja si existen pedidos cobrados SIN comprobante emitido para el movimiento de caja actual.');
             RAISE_APPLICATION_ERROR(-20011,'No se puede cerrar caja ya que existen pedidos pendientes o en proceso de cobro. Vuelva a intentar!!!');
          END IF;

          v_nActTur:=CAJ_OBTENER_TURNO_ACTUAL_CAJA(cCodGrupoCia_in,cCod_Local_in,nNumCaj_in);

          INSERT INTO CE_MOV_CAJA(COD_GRUPO_CIA,         COD_LOCAL,
                                  SEC_MOV_CAJA,          NUM_CAJA_PAGO,
                                  SEC_USU_LOCAL,         SEC_MOV_CAJA_ORIGEN,
                                  TIP_MOV_CAJA,          FEC_DIA_VTA,
                                  NUM_TURNO_CAJA,        FEC_CREA_MOV_CAJA,
                                  USU_CREA_MOV_CAJA,     CANT_BOL_EMI,
                                  MON_BOL_EMI,           CANT_FACT_EMI,
                                  MON_FACT_EMI,          CANT_GUIA_EMI,
                                  MON_GUIA_EMI,          CANT_BOL_ANU,
                                  MON_BOL_ANU,           CANT_FACT_ANU,
                                  MON_FACT_ANU,          CANT_GUIA_ANU,
                                  MON_GUIA_ANU,          CANT_BOL_TOT,
                                  MON_BOL_TOT,           CANT_FACT_TOT,
                                  MON_FACT_TOT,          CANT_GUIA_TOT,
                                  MON_GUIA_TOT,          MON_TOT_GEN,
                                  MON_TOT_ANU,           MON_TOT,
                                  CANT_NC_BOLETAS,       MON_NC_BOLETAS,
                                  CANT_NC_FACT,          MON_NC_FACT,
                                  MON_TOT_NC,            Ip_Mov_Caja
                                  --LLEIVA 11-Feb-2014 Se añaden los campos de Ticket-Factura
                                  ,CANT_TICK_FAC_EMI,    MON_TICK_FAC_EMI,
                                  CANT_TICK_FAC_ANUL,    MON_TICK_FACK_ANUL,
                                  CANT_NC_TICKETS_FAC,   MON_NC_TICKETS_FAC,
                                  CANT_TICK_FAC_TOT,     MON_TICK_FAC_TOT
                                  )
                          VALUES (cCodGrupoCia_in,       cCod_Local_in,
                                  v_nNeoCod,             TO_NUMBER(nNumCaj_in,'990'),
                                  cSecUsu_in,            v_cSecOrigen,
                                  cTipMov_in,            vFecDiaVta,
                                  v_nActTur,             SYSDATE,
                                  cIdUsu_in,             nCantBolEmi_in,
                                  nMonBolEmi_in,         nCantFacEmi_in,
                                  nMontFacEmi_in,        nCantGuiaEmi_in,
                                  nMonGuiaEmi_in,        nCantBolAnu_in,
                                  nMonBolAnu_in,         nCantFacAnu_in,
                                  nMonFacAnu_in,         nCantGuiaAnu_in,
                                  nMonGuiaAnu_in,        nCantBolTot_in,
                                  nMonBolTot_in,         nCantFactTot_in,
                                  nMonFactTot_in,        nCantGuiaTot_in,
                                  nMonGuiaTot_in,        nMonTotGen_in,
                                  nMonTotAnu_in,         nMonTot_in,
                                  ABS(nCantNCBol_in),    ABS(nMonNCBol_in),
                                  ABS(nCantNCFact_in),   ABS(nMonNCFact_in),
                                  ABS(nMonNCTot_in),     cIpMovCaja
                                  --LLEIVA 11-Feb-2014 Se añaden los campos de Ticket-Factura
                                  ,nCantTickFacEmi,       nMonTickFacEmi,
                                  nCantTickFacAnul,      nMonTickFacAnul,
                                  nCantNCTickFac,        nMonNCTickFac,
                                  nCantTickFacTot,       nMonTickFacTot
                                  );

          SELECT SEC_MOV_CAJA
          into   v_sec_caja_origen
          FROM   VTA_CAJA_PAGO
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
          COD_LOCAL      = cCod_Local_in AND
          NUM_CAJA_PAGO = nNumCaj_in;

          SELECT SUM(C.VAL_NETO_PED_VTA) MONTO_CJA
          INTO  TOTAL_CAJA_TURNO
          FROM  VTA_PEDIDO_VTA_CAB C
          WHERE C.EST_PED_VTA = 'C'
          AND   C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND   C.COD_LOCAL = cCod_Local_in
          AND   C.SEC_MOV_CAJA = v_sec_caja_origen;


          UPDATE CE_MOV_CAJA C
          SET    MON_TOT = TOTAL_CAJA_TURNO
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL = cCod_Local_in
          AND    SEC_MOV_CAJA = v_nNeoCod;

          UPDATE VTA_CAJA_PAGO SET FEC_MOD_CAJA_PAGO = SYSDATE, USU_MOD_CAJA_PAGO = cIdUsu_in,
                                   SEC_MOV_CAJA      = NULL,
                                   IND_CAJA_ABIERTA  = INDICADOR_NO
                             WHERE COD_GRUPO_CIA   = cCodGrupoCia_in AND
                                    COD_LOCAL       = cCod_Local_in   AND
                                   NUM_CAJA_PAGO   = nNumCaj_in;

          --JCORTEZ 13.05.09 Se valida que no exista pedidos sin cobrar y sin secuencial de movimiento de caja.
           SELECT COUNT(*)
          INTO   vCantPedInc
          FROM   VTA_PEDIDO_VTA_CAB
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                  COD_LOCAL     = cCod_Local_in AND
                 EST_PED_VTA   = EST_PED_COB_NO_IMPR AND
                 SEC_MOV_CAJA  = (SELECT SEC_MOV_CAJA
                                   FROM   VTA_CAJA_PAGO
                                  WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                                           COD_LOCAL      = cCod_Local_in AND
                                          NUM_CAJA_PAGO = nNumCaj_in);
          IF vCantPedInc>0 THEN
             RAISE_APPLICATION_ERROR(-20011,'No se puede cerrar caja ya que existen pedidos pendientes o en proceso de cobro. Vuelva a intentar!!!');
          END IF;

          --inicio adicion paulo para cuadraturas
          ptoventa_Ce.Ce_Graba_Cuadratura_Cierre(ccodgrupocia_in,ccod_local_in,v_nNeoCod,cidusu_in);
          --fin adicion paulo para cuadraturas

          --inicio adicion LMESIA para cuadraturas
          ptoventa_Ce.Ce_Graba_Forma_Pago_Cierre(ccodgrupocia_in,ccod_local_in,v_nNeoCod,cidusu_in);
          --fin adicion LMESIA para cuadraturas

       END IF;
       IF cTipMov_in=TIP_MOV_ARQUEO THEN
           v_nActTur:=CAJ_OBTENER_TURNO_ACTUAL_CAJA(cCodGrupoCia_in,cCod_Local_in,nNumCaj_in);

           INSERT INTO CE_MOV_CAJA (COD_GRUPO_CIA,       COD_LOCAL,
                                    SEC_MOV_CAJA,        NUM_CAJA_PAGO,
                                    SEC_USU_LOCAL,       SEC_MOV_CAJA_ORIGEN,
                                    TIP_MOV_CAJA,        FEC_DIA_VTA,
                                    NUM_TURNO_CAJA,      FEC_CREA_MOV_CAJA,
                                    USU_CREA_MOV_CAJA,   CANT_BOL_EMI,
                                    MON_BOL_EMI,         CANT_FACT_EMI,
                                    MON_FACT_EMI,        CANT_GUIA_EMI,
                                    MON_GUIA_EMI,        CANT_BOL_ANU,
                                    MON_BOL_ANU,         CANT_FACT_ANU,
                                    MON_FACT_ANU,        CANT_GUIA_ANU,
                                    MON_GUIA_ANU,        CANT_BOL_TOT,
                                    MON_BOL_TOT,         CANT_FACT_TOT,
                                    MON_FACT_TOT,        CANT_GUIA_TOT,
                                    MON_GUIA_TOT,        MON_TOT_GEN,
                                    MON_TOT_ANU,         MON_TOT,
                                    CANT_NC_BOLETAS,     MON_NC_BOLETAS,
                                    CANT_NC_FACT,        MON_NC_FACT,
                                    MON_TOT_NC,          Ip_Mov_Caja
                                    --LLEIVA 11-Feb-2014 Se añaden los campos de Ticket-Factura
                                    ,CANT_TICK_FAC_EMI,  MON_TICK_FAC_EMI,
                                    CANT_TICK_FAC_ANUL,  MON_TICK_FACK_ANUL,
                                    CANT_NC_TICKETS_FAC, MON_NC_TICKETS_FAC,
                                    CANT_TICK_FAC_TOT,   MON_TICK_FAC_TOT
                                    )
                            VALUES (cCodGrupoCia_in,     cCod_Local_in,
                                    v_nNeoCod,           nNumCaj_in,
                                    cSecUsu_in,          v_cSecOrigen,
                                    cTipMov_in,          vFecDiaVta,
                                    v_nActTur,           SYSDATE,
                                    cIdUsu_in,           nCantBolEmi_in,
                                    nMonBolEmi_in,       nCantFacEmi_in,
                                    nMontFacEmi_in,      nCantGuiaEmi_in,
                                    nMonGuiaEmi_in,      nCantBolAnu_in,
                                    nMonBolAnu_in,       nCantFacAnu_in,
                                    nMonFacAnu_in,       nCantGuiaAnu_in,
                                    nMonGuiaAnu_in,      nCantBolTot_in,
                                    nMonBolTot_in,       nCantFactTot_in,
                                    nMonFactTot_in,      nCantGuiaTot_in,
                                    nMonGuiaTot_in,      nMonTotGen_in,
                                    nMonTotAnu_in,       nMonTot_in,
                                    ABS(nCantNCBol_in),  ABS(nMonNCBol_in),
                                    ABS(nCantNCFact_in), ABS(nMonNCFact_in),
                                    ABS(nMonNCTot_in),   cIpMovCaja
                                    --LLEIVA 11-Feb-2014 Se añaden los campos de Ticket-Factura
                                    ,nCantTickFacEmi,     nMonTickFacEmi,
                                    nCantTickFacAnul,    nMonTickFacAnul,
                                    nCantNCTickFac,      nMonNCTickFac,
                                    nCantTickFacTot,     nMonTickFacTot
                                    );
       END IF;
  RETURN v_nNeoCod;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_VERIFICA_CAJA_IMPRESORAS(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in     IN CHAR,
                    cNumCajaPago_in IN NUMBER)
  RETURN CHAR IS
    v_cTipComp VTA_TIP_COMP.TIP_COMP%TYPE;
  v_nCant    NUMBER;
    CURSOR curCaj IS
             SELECT TIP_COMP
             FROM   VTA_TIP_COMP TIP_COMP
             WHERE  TIP_COMP.COD_GRUPO_CIA     = cCodGrupoCia_in
             AND   TIP_COMP.IND_NECESITA_IMPR = INDICADOR_SI;
  BEGIN
       v_cTipComp := '00';
    v_nCant := 0;
     FOR tipComp_rec IN curCaj
    LOOP
        v_cTipComp := tipComp_rec.TIP_COMP;
      SELECT COUNT(*)
      INTO   v_nCant
      FROM   VTA_IMPR_LOCAL IMPR_LOCAL,
            VTA_CAJA_IMPR CAJA_IMPR
      WHERE  CAJA_IMPR.COD_GRUPO_CIA   = cCodGrupoCia_in
      AND   CAJA_IMPR.COD_LOCAL       = cCodLocal_in
      AND   CAJA_IMPR.NUM_CAJA_PAGO   = cNumCajaPago_in
      AND   IMPR_LOCAL.TIP_COMP       = v_cTipComp
      AND   IMPR_LOCAL.COD_GRUPO_CIA  = CAJA_IMPR.COD_GRUPO_CIA
      AND   IMPR_LOCAL.COD_LOCAL      = CAJA_IMPR.COD_LOCAL
      AND   IMPR_LOCAL.SEC_IMPR_LOCAL = CAJA_IMPR.SEC_IMPR_LOCAL;

      IF(v_nCant = 0) THEN
        EXIT;
      END IF;
      v_cTipComp := '00';--SIGNIFICA Q SI EXISTEN LAS IMPRESORAS PARA LAS CAJAS POR TIPO DE COMPROBANTE
    END LOOP;
    RETURN v_cTipComp;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_VERIFICA_CAJA_ABIERTA(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                   cSecUsuLocal_in IN CHAR)
  RETURN NUMBER
  IS
    v_nCant    NUMBER;
  BEGIN
     SELECT COUNT(*)
    INTO   v_nCant
    FROM   VTA_CAJA_PAGO CAJA_PAGO
    WHERE  CAJA_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     CAJA_PAGO.COD_LOCAL = cCodLocal_in
    AND     CAJA_PAGO.SEC_USU_LOCAL = cSecUsuLocal_in
    AND     CAJA_PAGO.IND_CAJA_ABIERTA = INDICADOR_SI
    AND     CAJA_PAGO.SEC_MOV_CAJA IS NOT NULL;
  RETURN v_nCant;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_CAJA_USUARIO(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR,
                  cSecUsuLocal_in IN CHAR)
  RETURN NUMBER
  IS
    v_nCajaPago    NUMBER;
  BEGIN
        SELECT CAJA_PAGO.NUM_CAJA_PAGO
    INTO   v_nCajaPago
    FROM   VTA_CAJA_PAGO CAJA_PAGO
    WHERE  CAJA_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     CAJA_PAGO.COD_LOCAL     = cCodLocal_in
    AND     CAJA_PAGO.SEC_USU_LOCAL = cSecUsuLocal_in;
  RETURN v_nCajaPago;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
      RETURN 0;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_SECUENCIA_IMPRESORAS_VENTA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                      cNumCajaPago_in IN NUMBER)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    /*SELECT NVL(V1.BOL_SEC,0) || 'Ã' ||
         NVL(V2.FAC_SEC,0) || 'Ã' ||
         NVL(V3.GUIA_SEC,0)*/
    SELECT NVL(/*(SELECT IMPR_LOCAL.SEC_IMPR_LOCAL BOL_SEC
            FROM   VTA_IMPR_LOCAL IMPR_LOCAL,
                 VTA_CAJA_IMPR CAJA_IMPR
            WHERE  CAJA_IMPR.COD_GRUPO_CIA   = cCodGrupoCia_in
            AND     CAJA_IMPR.COD_LOCAL       = cCodLocal_in
            AND     CAJA_IMPR.NUM_CAJA_PAGO   = cNumCajaPago_in
            AND    IMPR_LOCAL.NUM_SERIE_LOCAL < C_C_SERIE_VTA_INSTITUCIONAL
            AND     IMPR_LOCAL.COD_GRUPO_CIA  = CAJA_IMPR.COD_GRUPO_CIA
            AND     IMPR_LOCAL.COD_LOCAL      = CAJA_IMPR.COD_LOCAL
            AND     IMPR_LOCAL.SEC_IMPR_LOCAL = CAJA_IMPR.SEC_IMPR_LOCAL
            AND     IMPR_LOCAL.TIP_COMP       = COD_TIP_COMP_BOLETA)*/
          (SELECT X.SEC_IMPR_LOCAL
            FROM VTA_IMPR_IP X
            WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
            AND X.COD_LOCAL=cCodLocal_in
            AND X.TIP_COMP=COD_TIP_COMP_BOLETA
            AND TRIM(X.IP)= (SELECT TRIM(SYS_CONTEXT('USERENV','IP_ADDRESS')) FROM DUAL)),0)|| 'Ã' || --BOLETAS
           NVL((SELECT IMPR_LOCAL.SEC_IMPR_LOCAL FAC_SEC
            FROM   VTA_IMPR_LOCAL IMPR_LOCAL,
                 VTA_CAJA_IMPR CAJA_IMPR
            WHERE  CAJA_IMPR.COD_GRUPO_CIA   = cCodGrupoCia_in
            AND     CAJA_IMPR.COD_LOCAL       = cCodLocal_in
            AND     CAJA_IMPR.NUM_CAJA_PAGO   = cNumCajaPago_in
            --   DUBILLUZ 09.04.2014
            --AND    IMPR_LOCAL.NUM_SERIE_LOCAL < C_C_SERIE_VTA_INSTITUCIONAL
            AND    DESC_IMPR_LOCAL NOT LIKE '%INSTITUCIONAL%'
            --   DUBILLUZ 09.04.2014
            AND     IMPR_LOCAL.COD_GRUPO_CIA  = CAJA_IMPR.COD_GRUPO_CIA
            AND     IMPR_LOCAL.COD_LOCAL      = CAJA_IMPR.COD_LOCAL
            AND     IMPR_LOCAL.SEC_IMPR_LOCAL = CAJA_IMPR.SEC_IMPR_LOCAL
            AND     IMPR_LOCAL.TIP_COMP = COD_TIP_COMP_FACTURA),0)|| 'Ã' || --FACTURAS
           NVL((SELECT IMPR_LOCAL.SEC_IMPR_LOCAL GUIA_SEC
            FROM   VTA_IMPR_LOCAL IMPR_LOCAL,
                 VTA_CAJA_IMPR CAJA_IMPR
            WHERE  CAJA_IMPR.COD_GRUPO_CIA   = cCodGrupoCia_in
            AND     CAJA_IMPR.COD_LOCAL       = cCodLocal_in
            AND     CAJA_IMPR.NUM_CAJA_PAGO   = cNumCajaPago_in
            --   DUBILLUZ 09.04.2014
            --AND    IMPR_LOCAL.NUM_SERIE_LOCAL < C_C_SERIE_VTA_INSTITUCIONAL
            AND    DESC_IMPR_LOCAL NOT LIKE '%INSTITUCIONAL%'
            --   DUBILLUZ 09.04.2014
            AND     IMPR_LOCAL.COD_GRUPO_CIA  = CAJA_IMPR.COD_GRUPO_CIA
            AND     IMPR_LOCAL.COD_LOCAL      = CAJA_IMPR.COD_LOCAL
            AND     IMPR_LOCAL.SEC_IMPR_LOCAL = CAJA_IMPR.SEC_IMPR_LOCAL
            AND     IMPR_LOCAL.TIP_COMP       = COD_TIP_COMP_GUIA),0)|| 'Ã' ||--GUIAS
          NVL(/*(SELECT IMPR_LOCAL.SEC_IMPR_LOCAL TICKET_SEC
            FROM   VTA_IMPR_LOCAL IMPR_LOCAL,
                 VTA_CAJA_IMPR CAJA_IMPR
            WHERE  CAJA_IMPR.COD_GRUPO_CIA   = cCodGrupoCia_in
            AND     CAJA_IMPR.COD_LOCAL       = cCodLocal_in
            AND     CAJA_IMPR.NUM_CAJA_PAGO   = cNumCajaPago_in
            AND    IMPR_LOCAL.NUM_SERIE_LOCAL < C_C_SERIE_VTA_INSTITUCIONAL
            AND     IMPR_LOCAL.COD_GRUPO_CIA  = CAJA_IMPR.COD_GRUPO_CIA
            AND     IMPR_LOCAL.COD_LOCAL      = CAJA_IMPR.COD_LOCAL
            AND     IMPR_LOCAL.SEC_IMPR_LOCAL = CAJA_IMPR.SEC_IMPR_LOCAL
            AND     IMPR_LOCAL.TIP_COMP       = COD_TIP_COMP_TICKET)*/
            (SELECT X.SEC_IMPR_LOCAL
            FROM VTA_IMPR_IP X
            WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
            AND X.COD_LOCAL=cCodLocal_in
            AND X.TIP_COMP=COD_TIP_COMP_TICKET
            AND TRIM(X.IP)= (SELECT TRIM(SYS_CONTEXT('USERENV','IP_ADDRESS')) FROM DUAL)),0)|| 'Ã' ||--TICKET
          NVL(/*(SELECT IMPR_LOCAL.SERIE_IMP TICKET_SER
            FROM   VTA_IMPR_LOCAL IMPR_LOCAL,
                 VTA_CAJA_IMPR CAJA_IMPR
            WHERE  CAJA_IMPR.COD_GRUPO_CIA   = cCodGrupoCia_in
            AND     CAJA_IMPR.COD_LOCAL       = cCodLocal_in
            AND     CAJA_IMPR.NUM_CAJA_PAGO   = cNumCajaPago_in
            AND    IMPR_LOCAL.NUM_SERIE_LOCAL < C_C_SERIE_VTA_INSTITUCIONAL
            AND     IMPR_LOCAL.COD_GRUPO_CIA  = CAJA_IMPR.COD_GRUPO_CIA
            AND     IMPR_LOCAL.COD_LOCAL      = CAJA_IMPR.COD_LOCAL
            AND     IMPR_LOCAL.SEC_IMPR_LOCAL = CAJA_IMPR.SEC_IMPR_LOCAL
            AND     IMPR_LOCAL.TIP_COMP       = COD_TIP_COMP_TICKET)*/
            (SELECT Y.SERIE_IMP
            FROM VTA_IMPR_IP X,
                 VTA_IMPR_LOCAL Y
            WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
            AND X.COD_LOCAL=cCodLocal_in
            AND X.SEC_IMPR_LOCAL IS NOT NULL
            AND X.TIP_COMP=COD_TIP_COMP_TICKET
            AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
            AND X.COD_LOCAL=Y.COD_LOCAL
            AND X.SEC_IMPR_LOCAL=Y.SEC_IMPR_LOCAL
            AND TRIM(X.IP)= (SELECT TRIM(SYS_CONTEXT('USERENV','IP_ADDRESS')) FROM DUAL)),0)--SERIE TICKET


          FROM DUAL  ;
    RETURN curCaj;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_RUTA_IMPRESORA(cCodGrupoCia_in  IN CHAR,
                            cCodLocal_in     IN CHAR,
                    cSecImprLocal_in IN NUMBER)
  RETURN CHAR
  IS
    v_cRutaImpr VTA_IMPR_LOCAL.RUTA_IMPR%TYPE;
  BEGIN
    SELECT NVL(IMPR_LOCAL.RUTA_IMPR,' ')
    INTO   v_cRutaImpr
    FROM   VTA_IMPR_LOCAL IMPR_LOCAL
    WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND     IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
    AND     IMPR_LOCAL.SEC_IMPR_LOCAL = cSecImprLocal_in;
    RETURN v_cRutaImpr;
  END;

  /********************************************************************************************************/

  FUNCTION CAJ_LISTA_COMPROBANT_RANGO_FEC(cCodGrupoCia_in   IN CHAR,
                                 cCod_Local_in     IN CHAR,
                          cFecIni_in     IN CHAR,
                          cFecFin_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR

     SELECT NVL(TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss'),' ')                      || 'Ã' ||
          NVL(TO_CHAR(TP.DESC_COMP),' ')                                           || 'Ã' ||
        --@@ RH:03.10.2014 FAC-ELECTRONICA
        --        NVL(SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'|| SUBSTR(CP.NUM_COMP_PAGO,-7),' ')    || 'Ã' ||
         --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
        --@@
          NVL(TO_CHAR(VC.NUM_PED_VTA),' ')                                        || 'Ã' ||
             NVL(TRIM(TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,999,990.00')),' ')    || 'Ã' ||
        ' '                                 || 'Ã' ||
            CASE VC.EST_PED_VTA WHEN EST_PED_PENDIENTE THEN 'Pendiente'
                             WHEN EST_PED_ANULADO   THEN 'Anulado'
                   WHEN EST_PED_COBRADO   THEN 'Cobrado'
                   END                                          || 'Ã' ||
        CP.TIP_COMP_PAGO ||  --CP.NUM_COMP_PAGO                         || 'Ã' ||
                 --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
        NVL(TO_CHAR(CP.SEC_COMP_PAGO),' ')                             || 'Ã' ||
        CP.TIP_COMP_PAGO                                  || 'Ã' ||
        --NVL(CP.NUM_COMP_PAGO,' ')                            || 'Ã' ||
                 --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                   || 'Ã' ||
        CP.TIP_COMP_PAGO ||  NVL(TO_CHAR(VC.NUM_PED_VTA),' ')

     FROM   VTA_PEDIDO_VTA_CAB VC,
            VTA_TIP_COMP TP,
          VTA_COMP_PAGO CP

       WHERE VC.COD_GRUPO_CIA = cCodGrupoCia_in  AND
         VC.COD_LOCAL     = cCod_Local_in    AND
         VC.EST_PED_VTA = EST_PED_COBRADO     AND
         FEC_CREA_COMP_PAGO BETWEEN TO_DATE(cFecIni_in||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                AND TO_DATE(cFecFin_in||' 23:59:59','dd/MM/yyyy HH24:mi:ss')  AND
         TP.TIP_COMP     = CP.TIP_COMP_PAGO  AND
             TP.COD_GRUPO_CIA = CP.COD_GRUPO_CIA AND
         CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA AND
         CP.COD_LOCAL     = VC.COD_LOCAL     AND
         CP.NUM_PED_VTA   = VC.NUM_PED_VTA   AND
         -- KMONCADA 05.01.2015 
         CP.NUM_COMP_PAGO_E IS NOT NULL      AND
         NVL(CP.COD_TIP_PROC_PAGO, '0') != '1'
         ;


    RETURN curDet;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_AGRUPA_IMPRESION_DETALLE(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        nCantMaxImpr_in IN NUMBER,
                                        cUsuModImpr_in  IN CHAR
                    )
  RETURN NUMBER
  IS
    v_nSecGrupoImpr NUMBER;
  v_nContador      NUMBER;
  v_cIndicador  CHAR(1);
  v_nCantidad    NUMBER;
  v_cCodProd    VTA_PEDIDO_VTA_DET.COD_PROD%TYPE;
  --v_nCantDetalleImpr NUMBER;
    CURSOR indicadores IS
           SELECT COUNT(*) CANTIDAD,
             VTA_DET.IND_EXONERADO_IGV INDICADOR
        FROM   VTA_PEDIDO_VTA_DET VTA_DET
        WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     VTA_DET.COD_LOCAL = cCodLocal_in
        AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
        GROUP BY VTA_DET.IND_EXONERADO_IGV;
  CURSOR productos(p_indicador CHAR) IS
           SELECT VTA_DET.COD_PROD PRODUCTO
        FROM   VTA_PEDIDO_VTA_DET VTA_DET
        WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     VTA_DET.COD_LOCAL = cCodLocal_in
        AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
        AND     VTA_DET.IND_EXONERADO_IGV = p_indicador;

  BEGIN
    v_nSecGrupoImpr := 0;

    FOR secGrupoImpr_rec IN indicadores
    LOOP
        v_nCantidad := secGrupoImpr_rec.CANTIDAD;
        v_cIndicador := secGrupoImpr_rec.INDICADOR;
      v_nContador := 0;
      --IF(v_nSecGrupoImpr = 0) THEN
      v_nSecGrupoImpr := v_nSecGrupoImpr + 1;
      --END IF;
      FOR producto_rec IN productos(v_cIndicador)
        LOOP
          IF (v_nContador = nCantMaxImpr_in) THEN
           v_nSecGrupoImpr := v_nSecGrupoImpr + 1 ;
         v_nContador := 0;
        END IF;
        v_nContador := v_nContador + 1 ;
          v_cCodProd := producto_rec.PRODUCTO;
        UPDATE VTA_PEDIDO_VTA_DET SET FEC_MOD_PED_VTA_DET = SYSDATE, USU_MOD_PED_VTA_DET = cUsuModImpr_in,
               SEC_GRUPO_IMPR = v_nSecGrupoImpr
            WHERE  COD_GRUPO_CIA  = cCodGrupoCia_in
            AND  COD_LOCAL      = cCodLocal_in
            AND  NUM_PED_VTA    = cNumPedVta_in
            AND  COD_PROD       = v_cCodProd;
      END LOOP;
    END LOOP;
  RETURN v_nSecGrupoImpr;
  END;

  /* ********************************************************************************************** */
  PROCEDURE CAJ_VERIFICA_PEDIDO(cCodGrupoCia_in IN CHAR,
                  cCodLocal_in    IN CHAR,
                cNumPedVta_in   IN CHAR,
                nMontoVta_in    IN NUMBER)
  AS
    v_cEstPedido VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
  BEGIN
    SELECT EST_PED_VTA,EST_PED_VTA INTO v_cEstPedido,v_cIndAnulado
    FROM VTA_PEDIDO_VTA_CAB
    WHERE COD_GRUPO_CIA        = cCodGrupoCia_in
          AND COD_LOCAL        = cCodLocal_in
          AND NUM_PED_VTA      = cNumPedVta_in
          AND VAL_NETO_PED_VTA = nMontoVta_in;

    IF v_cEstPedido <> EST_PED_COBRADO THEN
      RAISE_APPLICATION_ERROR(-20002,'El Pedido no ha sido cobrado. ¡No puede Anular este Pedido!');
    END IF;

    IF v_cIndAnulado = INDICADOR_SI THEN
      RAISE_APPLICATION_ERROR(-20003,'El Pedido ya está anulado. ¡No puede Anular este Pedido!');
    END IF;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20001,'No se encuentra ningun Pedido con estos datos. ¡Verifique!');
  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_LISTA_CABECERA_PEDIDO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCab FarmaCursor;
  BEGIN
    OPEN curCab FOR
    SELECT NUM_PED_VTA                              || 'Ã' ||
           TO_CHAR(FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS')           || 'Ã' ||
           TO_CHAR(VAL_NETO_PED_VTA,'999,990.000')               || 'Ã' ||
           TO_CHAR(VAL_NETO_PED_VTA/VAL_TIP_CAMBIO_PED_VTA,'999,990.000') || 'Ã' ||
           NVL(RUC_CLI_PED_VTA,' ')                     || 'Ã' ||
           NVL(NOM_CLI_PED_VTA,' ')                     || 'Ã' ||
           DECODE(SEC_MOV_CAJA,NULL,' ',INV_GET_CAJERO(cCodGrupoCia_in,cCodLocal_in,SEC_MOV_CAJA)) || 'Ã' ||
           'CONVENIO'
    FROM VTA_PEDIDO_VTA_CAB
    WHERE COD_GRUPO_CIA   = cCodGrupoCia_in
          AND COD_LOCAL   = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;

    RETURN curCab;
  END;
  /* ********************************************************************************************** */
  FUNCTION INV_GET_CAJERO(cCodGrupoCia_in IN CHAR,
                   cCodLocal_in    IN CHAR,
              cSecMovCaja_in  IN CHAR)
  RETURN VARCHAR2
  IS
    v_vNom VARCHAR2(150);
  BEGIN
    SELECT U.NOM_USU || ' ' || U.APE_PAT INTO v_vNom
    FROM PBL_USU_LOCAL U, CE_MOV_CAJA C
    WHERE C.COD_GRUPO_CIA     = cCodGrupoCia_in
          AND C.COD_LOCAL     = cCodLocal_in
          AND SEC_MOV_CAJA    = cSecMovCaja_in
          AND U.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND U.COD_LOCAL     = C.COD_LOCAL
          AND U.SEC_USU_LOCAL = C.SEC_USU_LOCAL;
    RETURN v_vNom;
  END;

  /***********************************************************************************************************/

    PROCEDURE CAJ_CORRIGE_COMPROBANTES(cCodGrupoCia_in    IN CHAR,
                       codLocal_in       IN CHAR,
                     cSecIni_in      IN CHAR,
                     cSecFin_in       IN CHAR,
                     cTipComp_in      IN CHAR,
                     nCant_in         IN NUMBER,
                      nIndDir          IN NUMBER,
                   cCodUsu_in      IN CHAR)
   IS

  comprobanteinicial     NUMBER(10) := TO_NUMBER(cSecIni_in);
  comprobantefinal      NUMBER(10) := TO_NUMBER(cSecFin_in);
  numeropedido       CHAR(10);
  numeropedidoanul     CHAR(10);
  -----
  cSecMax    varchar2(20);
  cSecMin    varchar2(20);


  BEGIN
    IF ( nIndDir = 1 ) THEN  --Se corregira ascendentemente
          LOOP
        BEGIN

           dbms_output.put_line('1: ' || TO_CHAR(comprobantefinal) );

           UPDATE VTA_COMP_PAGO SET FEC_MOD_COMP_PAGO = SYSDATE, USU_MOD_COMP_PAGO = cCodUsu_in,
                  NUM_COMP_PAGO = TRIM(TO_CHAR(comprobantefinal + nCant_in,'0000000000')),
                   TIP_COMP_PAGO = cTipComp_in
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL     = codLocal_in
             AND   TIP_COMP_PAGO = cTipComp_in
             AND   NUM_COMP_PAGO = TRIM(TO_CHAR(comprobantefinal,'0000000000'));


          --Se obtienen el maximo y minimo secuencial de cada Comprobante
          --para luego todo el Kardex Actualizarlo con el Nuevo
            SELECT TRIM(MIN(SEC_KARDEX)),TRIM(MAX(SEC_KARDEX))
            INTO   cSecMin , cSecMax
            FROM   LGT_KARDEX
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND    COD_LOCAL     = codLocal_in
            AND    TIP_COMP_PAGO = cTipComp_in
            AND    NUM_COMP_PAGO = TRIM(TO_CHAR(comprobantefinal,'0000000000'));

            update lgt_kardex k
            set    k.num_comp_pago  = TRIM(TO_CHAR(comprobantefinal + nCant_in,'0000000000')),
                   k.fec_mod_kardex = sysdate,
                   k.usu_mod_kardex = cCodUsu_in
            where  k.cod_grupo_cia  =  cCodGrupoCia_in
            and    k.cod_local      =  codLocal_in
            and    k.tip_comp_pago  = cTipComp_in
            AND    k.sec_kardex between cSecMin and cSecMax;


           dbms_output.put_line('2: ' || TO_CHAR(comprobantefinal + nCant_in) );
           COMMIT;
           dbms_output.put_line('3: ' || TO_CHAR(comprobantefinal + nCant_in) );
          comprobantefinal := comprobantefinal - 1;
           dbms_output.put_line('4: ' || comprobantefinal );
              EXIT WHEN comprobantefinal<comprobanteinicial;
        EXCEPTION WHEN OTHERS THEN
          dbms_output.put_line('ERROR: ' || SQLERRM );
            ROLLBACK;
          END;
          END LOOP;
    ELSIF ( nIndDir = 2 ) THEN  --Se corregira descendentemente
      LOOP
        BEGIN
           UPDATE VTA_COMP_PAGO SET FEC_MOD_COMP_PAGO = SYSDATE, USU_MOD_COMP_PAGO = cCodUsu_in,
                  NUM_COMP_PAGO     = TRIM(TO_CHAR(comprobanteinicial - nCant_in,'0000000000')),
                   TIP_COMP_PAGO     = cTipComp_in
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL     = codLocal_in
             AND   TIP_COMP_PAGO = cTipComp_in
             AND   NUM_COMP_PAGO = TRIM(TO_CHAR(comprobanteinicial,'0000000000'));

          --Se obtienen el maximo y minimo secuencial de cada Comprobante
          --para luego todo el Kardex Actualizarlo con el Nuevo
            SELECT TRIM(MIN(SEC_KARDEX)),TRIM(MAX(SEC_KARDEX))
            INTO   cSecMin , cSecMax
            FROM   LGT_KARDEX
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND    COD_LOCAL     = codLocal_in
            AND    TIP_COMP_PAGO = cTipComp_in
            AND    NUM_COMP_PAGO = TRIM(TO_CHAR(comprobanteinicial,'0000000000'));

            update lgt_kardex k
            set    k.num_comp_pago  = TRIM(TO_CHAR(comprobanteinicial - nCant_in,'0000000000')),
                   k.fec_mod_kardex = sysdate,
                   k.usu_mod_kardex = cCodUsu_in
            where  k.cod_grupo_cia  =  cCodGrupoCia_in
            and    k.cod_local      =  codLocal_in
            and    k.tip_comp_pago  = cTipComp_in
            AND    k.sec_kardex between cSecMin and cSecMax;

           dbms_output.put_line('2: ' || TO_CHAR(comprobanteinicial - nCant_in) );
           COMMIT;
           dbms_output.put_line('3: ' || TO_CHAR(comprobanteinicial - nCant_in) );
          comprobanteinicial := comprobanteinicial + 1;
           dbms_output.put_line('4: ' || comprobanteinicial );
           EXIT WHEN comprobanteinicial>comprobantefinal;
           EXCEPTION WHEN OTHERS THEN
           dbms_output.put_line('ERROR: ' || SQLERRM );
           ROLLBACK;
          END;
          END LOOP;
    END IF;
  END;


  /* ********************************************************************************************** */
  FUNCTION CAJ_LISTA_DETALLE_PEDIDO(cCodGrupoCia_in IN CHAR,
                       cCodLocal_in    IN CHAR,
                  cNumPedVta_in   IN CHAR,
                  cTipComp_in     IN CHAR,
                  cNumComp_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT D.COD_PROD                  || 'Ã' ||
           P.DESC_PROD                 || 'Ã' ||
           D.UNID_VTA                 || 'Ã' ||
           TO_CHAR(D.VAL_PREC_LISTA,'999,990.000')   || 'Ã' ||
           D.PORC_DCTO_TOTAL             || 'Ã' ||
           TO_CHAR(D.VAL_PREC_VTA,'999,990.000')   || 'Ã' ||
           D.CANT_ATENDIDA               || 'Ã' ||
           TO_CHAR(D.VAL_PREC_TOTAL,'999,990.000')
    FROM VTA_PEDIDO_VTA_DET D, VTA_COMP_PAGO C, LGT_PROD_LOCAL PL,
     LGT_PROD P
    WHERE D.COD_GRUPO_CIA     = cCodGrupoCia_in
          AND D.COD_LOCAL     = cCodLocal_in
          AND D.NUM_PED_VTA   = cNumPedVta_in
      AND C.TIP_COMP_PAGO LIKE cTipComp_in
          AND --C.NUM_COMP_PAGO
                       --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(C.COD_TIP_PROC_PAGO,C.NUM_COMP_PAGO_E,C.NUM_COMP_PAGO)
                                LIKE cNumComp_in
          AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND D.COD_LOCAL     = C.COD_LOCAL
          AND D.NUM_PED_VTA   = C.NUM_PED_VTA
          AND D.SEC_COMP_PAGO = C.SEC_COMP_PAGO
          AND D.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
          AND D.COD_LOCAL     = PL.COD_LOCAL
          AND D.COD_PROD      = PL.COD_PROD
      AND PL.COD_GRUPO_CIA= P.COD_GRUPO_CIA
      AND PL.COD_PROD    = P.COD_PROD;
    RETURN curDet;
  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_VERIFICA_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                       cCodLocal_in    IN CHAR,
                  cTipComp_in     IN CHAR,
                  cNumComp_in     IN CHAR,
                  nMontoVta_in    IN NUMBER)
  RETURN VARCHAR2
  IS
    v_cNumPed VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    v_cEstPedido VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
  BEGIN
    SELECT NUM_PED_VTA INTO v_cNumPed
    FROM VTA_COMP_PAGO
    WHERE TIP_COMP_PAGO          = cTipComp_in
          AND --NUM_COMP_PAGO
                   --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(COD_TIP_PROC_PAGO,NUM_COMP_PAGO_E,NUM_COMP_PAGO)
                = cNumComp_in
          AND VAL_NETO_COMP_PAGO = nMontoVta_in;

    SELECT EST_PED_VTA,EST_PED_VTA INTO v_cEstPedido,v_cIndAnulado
    FROM VTA_PEDIDO_VTA_CAB
    WHERE COD_GRUPO_CIA    = cCodGrupoCia_in
          AND COD_LOCAL    = cCodLocal_in
          AND NUM_PED_VTA  = v_cNumPed;

    IF v_cEstPedido <> EST_PED_COBRADO THEN
      RAISE_APPLICATION_ERROR(-20002,'El Pedido no ha sido cobrado. ¡No puede Anular este Pedido!');
    END IF;

    IF v_cIndAnulado = INDICADOR_SI THEN
      RAISE_APPLICATION_ERROR(-20003,'El Pedido ya está anulado. ¡No puede Anular este Pedido!');
    END IF;

    RETURN v_cNumPed;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20001,'No se encuentra ningun Pedido con estos datos. ¡Verifique!');
  END;

  /*************************************************************************************************************/
  FUNCTION CAJ_VERIFICAR_COMPROBANTES(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in     IN CHAR,
                      cSecIni_in       IN CHAR,
                      cSecFin_in       IN CHAR,
                      cTipComp_in     IN CHAR)
  RETURN NUMBER IS
         numerocomprobante_out   NUMBER;
       comprobanteinicial      NUMBER := TO_NUMBER(cSecIni_in);
       comprobantefinal          NUMBER := TO_NUMBER(cSecFin_in);
       numerocomprobante     CHAR(10);
  BEGIN
         numerocomprobante_out := 1; --SI EXISTE Y NO OCURRIRA ERROR
    LOOP
      BEGIN
         SELECT --COM.NUM_COMP_PAGO
                         --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO)
                INTO   numerocomprobante
        FROM   VTA_COMP_PAGO COM
        WHERE  COM.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     COM.COD_LOCAL     = cCodLocal_in
        AND     --COM.NUM_COMP_PAGO =
         FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO)
                =TRIM(TO_CHAR(comprobanteinicial,'0000000000'))
        AND     COM.TIP_COMP_PAGO = cTipComp_in
        AND ROWNUM=1;
        numerocomprobante_out := 1;
        comprobanteinicial := comprobanteinicial + 1;
        EXIT WHEN comprobanteinicial>comprobantefinal;

      EXCEPTION WHEN NO_DATA_FOUND THEN
          numerocomprobante_out := 2; --NO EXISTE Y SI OCURRIRA ERROR
          dbms_output.put_line('NO EXISTE Y SI OCURRIRA ERROR' );
              EXIT;
      END;
    END LOOP;
     RETURN numerocomprobante_out;
  END;

  /*************************************************************************************************************/

FUNCTION CAJ_F_VERIFICA_TIPO_COMP(cCodGrupoCia_in   IN CHAR,
                                                                                  cCodLocal_in   IN CHAR,  cSecIni_in     IN CHAR,  cSecFin_in     IN CHAR )
  RETURN NUMBER IS

   flag_comprobante   NUMBER:=0;

   CURSOR curTipoComp IS
      SELECT *
      FROM   VTA_COMP_PAGO
      WHERE COD_GRUPO_CIA=cCodGrupoCia_in
                      AND COD_LOCAL=cCodLocal_in
                      AND TO_NUMBER(SEC_COMP_PAGO) between TO_NUMBER( cSecIni_in)   AND  TO_NUMBER(cSecFin_in) ;

  BEGIN

      FOR curTipoComp_Aux  IN   curTipoComp
      LOOP

          IF  curTipoComp_Aux.Tip_Comp_Pago='05'  THEN

                 flag_comprobante:=1 ;

          END IF;

      END LOOP;

     RETURN flag_comprobante;

  END;

 /* ************************************************************************ */


  FUNCTION CAJ_VERIFICAR_CORRECCION_COMP(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                        cSecIni_in       IN CHAR,
                        cSecFin_in       IN CHAR,
                       cTipComp_in    IN CHAR,
                        nCant_in       IN NUMBER,
                         cIndDir_in        IN CHAR)
  RETURN NUMBER
  IS
    numerocomprobante_out   NUMBER;
  comprobanteinicial      NUMBER := TO_NUMBER(cSecIni_in);
  comprobantefinal      NUMBER := TO_NUMBER(cSecFin_in);
  numerocomprobante     CHAR(10);
  contador         NUMBER := 0;

  BEGIN
         numerocomprobante_out := 1; --SI EXISTE Y OCURRIRA UN ERROR DE DUPLICACION
    IF ( cIndDir_in = 1 ) THEN  --Se corregira ascendentemente
        LOOP
           BEGIN
              SELECT --COM.NUM_COMP_PAGO
              --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO)
               INTO   numerocomprobante
           FROM   VTA_COMP_PAGO COM
           WHERE  COM.COD_GRUPO_CIA = cCodGrupoCia_in
           AND     COM.COD_LOCAL     = cCodLocal_in
           AND     --COM.NUM_COMP_PAGO
           --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO)
                   = TRIM(TO_CHAR(comprobantefinal + nCant_in,'0000000000'))
           AND     COM.TIP_COMP_PAGO = cTipComp_in;
           numerocomprobante_out := 1;
           EXIT;

         EXCEPTION WHEN NO_DATA_FOUND THEN
              numerocomprobante_out := 2; --NO EXISTE Y NO OCURRIRA UN ERROR DE DUPLICACION
             dbms_output.put_line('NO EXISTE Y NO OCURRIRA UN ERROR DE DUPLICACION' );
             contador := contador + 1;
            comprobantefinal := comprobantefinal - 1;

          IF ( contador=nCant_in OR comprobantefinal<comprobanteinicial) THEN
              EXIT;
          END IF;

         END;
      END LOOP;
    ELSIF ( cIndDir_in = 2 ) THEN  --Se corregira descendentemente
      LOOP
        BEGIN
           SELECT --COM.NUM_COMP_PAGO
           --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO)
                  INTO   numerocomprobante
          FROM   VTA_COMP_PAGO COM
          WHERE  COM.COD_GRUPO_CIA = cCodGrupoCia_in
          AND     COM.COD_LOCAL     = cCodLocal_in
          AND     --COM.NUM_COMP_PAGO
           --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO)
                 = TRIM(TO_CHAR(comprobanteinicial - nCant_in,'0000000000'))
          AND     COM.TIP_COMP_PAGO = cTipComp_in;

          numerocomprobante_out := 1;
          EXIT;

        EXCEPTION WHEN NO_DATA_FOUND THEN
            numerocomprobante_out := 2; --NO EXISTE Y NO OCURRIRA UN ERROR DE DUPLICACION
            dbms_output.put_line('NO EXISTE Y NO OCURRIRA UN ERROR DE DUPLICACION' );
            contador := contador + 1;
            comprobanteinicial := comprobanteinicial + 1;

              dbms_output.put_line('4: ' || comprobanteinicial );

            IF ( contador=nCant_in OR comprobanteinicial>comprobantefinal) THEN
               EXIT;
            END IF;

        END;
      END LOOP;
    END IF;
     RETURN numerocomprobante_out;
  END;
/****************************************************************************************************************/

 PROCEDURE CAJ_CORRIGE_SERIES( cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in     IN CHAR,
                   cSecIniA_in      IN CHAR,
                   cSecFinA_in      IN CHAR,
                   cSecIniB_in      IN CHAR,
                   cSecFinB_in      IN CHAR,
                   cTipComp_in      IN CHAR,
                 cCodUsu_in    IN CHAR)

 IS
  comprobanteinicialA     NUMBER(10) := TO_NUMBER(cSecIniA_in);
  comprobantefinalA      NUMBER(10) := TO_NUMBER(cSecFinA_in);
  comprobanteinicialB     NUMBER(10) := TO_NUMBER(cSecIniB_in);
  comprobantefinalB      NUMBER(10) := TO_NUMBER(cSecFinB_in);
  numeropedido       CHAR(10);
  numeropedidoanul     CHAR(10);
  secCompPago          CHAR(10);

  BEGIN
    LOOP
      BEGIN
        SELECT TRIM(NVL(NUM_PED_VTA,' ')),
             TRIM(NVL(NUM_PEDIDO_ANUL,' ')),
             TRIM(NVL(SEC_COMP_PAGO,' '))
        INTO   numeropedido,
             numeropedidoanul,
             secCompPago
        FROM   VTA_COMP_PAGO COM
        WHERE  COM.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     COM.COD_LOCAL     = cCodLocal_in
        AND     COM.TIP_COMP_PAGO = cTipComp_in
        AND     --COM.NUM_COMP_PAGO
                   --RH: 21.10.2014 FAC-ELECTRONICA
         FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO)
                = TRIM(TO_CHAR(comprobanteinicialA,'0000000000'))
        AND ROWNUM=1;
         UPDATE VTA_COMP_PAGO SET FEC_MOD_COMP_PAGO = SYSDATE, USU_MOD_COMP_PAGO = cCodUsu_in,
                NUM_COMP_PAGO     = TRIM(TO_CHAR(comprobanteinicialB,'0000000000')),
                 TIP_COMP_PAGO     = cTipComp_in
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND   COD_LOCAL     = cCodLocal_in
           AND   NUM_PED_VTA   = numeropedido
           AND   SEC_COMP_PAGO = secCompPago
           --AND   TIP_COMP_PAGO = cTipComp_in
           --AND   NUM_COMP_PAGO = TRIM(TO_CHAR(comprobanteinicialA,'0000000000'))
           AND ROWNUM=1;

        comprobanteinicialA := comprobanteinicialA + 1;
        comprobanteinicialB := comprobanteinicialB + 1;

            EXIT WHEN comprobanteinicialA>comprobantefinalA OR comprobanteinicialB>comprobantefinalB;
      EXCEPTION WHEN OTHERS THEN
            dbms_output.put_line('ERROR: ' || SQLERRM );
          ROLLBACK;
      END;
      END LOOP;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_ESTADO_PEDIDO(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                   cNumPedVta_in    IN CHAR)
  RETURN CHAR
  IS
    v_cEstPedido VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
  BEGIN
  SELECT VTA_CAB.EST_PED_VTA
  INTO   v_cEstPedido
  FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
  WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
  AND     VTA_CAB.COD_LOCAL     = cCodLocal_in
  AND     VTA_CAB.NUM_PED_VTA   = cNumPedVta_in
  FOR UPDATE;
    RETURN v_cEstPedido;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
     v_cEstPedido := EST_PED_ANULADO; --SE ENVIA "N" PARA SIMULAR Q EL PEDIDO NO SE ENCUENTRA PENDIENTE
     RETURN v_cEstPedido;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_IND_CAJA_ABIERTA_FORUPDATE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                       cSecUsuLocal_in IN CHAR,
                      cSecMovCaja_in  IN CHAR)
  RETURN CHAR
  IS
    v_cIndCajaAbierta VTA_CAJA_PAGO.IND_CAJA_ABIERTA%TYPE;
  BEGIN
    SELECT CAJA_PAGO.IND_CAJA_ABIERTA
  INTO   v_cIndCajaAbierta
  FROM   VTA_CAJA_PAGO CAJA_PAGO
  WHERE  CAJA_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
  AND     CAJA_PAGO.COD_LOCAL     = cCodLocal_in
  AND     CAJA_PAGO.SEC_USU_LOCAL = cSecUsuLocal_in
  AND     CAJA_PAGO.SEC_MOV_CAJA  = cSecMovCaja_in FOR UPDATE;
    RETURN v_cIndCajaAbierta;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
     v_cIndCajaAbierta := INDICADOR_NO; --SE ENVIA "N" PARA SIMULAR Q LA CAJA ESTA CERRADA
     RETURN v_cIndCajaAbierta;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_SEC_MOV_CAJA(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR,
                  nNumCajaPago_in IN NUMBER)
  RETURN CHAR
  IS
    v_cSecMovCaja VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;
  BEGIN
        SELECT CAJA_PAGO.SEC_MOV_CAJA
    INTO   v_cSecMovCaja
    FROM   VTA_CAJA_PAGO CAJA_PAGO
    WHERE  CAJA_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     CAJA_PAGO.COD_LOCAL     = cCodLocal_in
    AND     CAJA_PAGO.NUM_CAJA_PAGO = nNumCajaPago_in;
  RETURN v_cSecMovCaja;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
      RETURN '0';
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_FECHA_MOV_CAJA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                    nNumCajaPago_in IN NUMBER)
  RETURN CHAR
  IS
    v_cFecModCajaPago    CHAR(10);
  BEGIN
        v_cFecModCajaPago := ' ';
        SELECT TO_CHAR(CAJA_PAGO.FEC_MOD_CAJA_PAGO,'dd/MM/yyyy')
    INTO   v_cFecModCajaPago
    FROM   VTA_CAJA_PAGO CAJA_PAGO
    WHERE  CAJA_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     CAJA_PAGO.COD_LOCAL     = cCodLocal_in
    AND     CAJA_PAGO.NUM_CAJA_PAGO = nNumCajaPago_in;
  RETURN v_cFecModCajaPago;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
      v_cFecModCajaPago := ' ';
      RETURN v_cFecModCajaPago;
  END;

/*--------------------------------------------------------------------------------------------------------------------
GOAL : Procesar el Cobro de Pedido
History : 17-JUL-14  TCT Modifica la generacion de codigos de Barra para dejar de usar el codigo del Local
----------------------------------------------------------------------------------------------------------------------*/
 FUNCTION CAJ_COBRA_PEDIDO(cCodGrupoCia_in        IN CHAR,
                           cCodLocal_in           IN CHAR,
                           cNumPedVta_in          IN CHAR,
                           cSecMovCaja_in         IN CHAR,
                           cCodNumera_in          IN CHAR,
                           cTipCompPago_in        IN CHAR,
                           cCodMotKardex_in       IN CHAR,
                           cTipDocKardex_in       IN CHAR,
                           cCodNumeraKardex_in    IN CHAR,
                           cUsuCreaCompPago_in    IN CHAR,
                           cDescDetalleForPago_in IN CHAR DEFAULT ' ',
                           cPermiteCampana        IN CHAR DEFAULT 'N',
                           cDni_in                IN CHAR DEFAULT NULL) RETURN CHAR
  IS
    v_nSecCompPago VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_cIndGraboComp    CHAR(1);
    v_cCodCliLocal    VTA_PEDIDO_VTA_CAB.COD_CLI_LOCAL%TYPE;
    v_cNomCliPedVta    VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE;
    v_cRucCliPedVta    VTA_PEDIDO_VTA_CAB.RUC_CLI_PED_VTA%TYPE;
    v_cDirCliPedVta    VTA_PEDIDO_VTA_CAB.DIR_CLI_PED_VTA%TYPE;
    v_nValRedondeo    VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_cIndDistrGratuita    VTA_PEDIDO_VTA_CAB.IND_DISTR_GRATUITA%TYPE;
    v_cIndDelivAutomatico VTA_PEDIDO_VTA_CAB.IND_DELIV_AUTOMATICO%TYPE;
    v_nContador      NUMBER;
    v_Resultado     CHAR(7);
    --Variable de Indicador para Cobro
    --24/08/2007   dubilluz  creacion
    v_Indicador_Cobro     CHAR(1);
    v_Indicador_Linea     Varchar2(3453);

    --JCORTEZ 15.05.09
    TipComp CHAR(2);
    TipCompAux CHAR(2);
    SecUso CHAR(3);
    NumCaja NUMBER(4);


    vAhorroPedido number;

    --JCORTEZ 10.06.09
    --COMENTADO LLEIVA: ahora se obtiene la IP del parametro cIp_in
    cIP VARCHAR(20);


    CURSOR totalesComprobante IS
           SELECT VTA_DET.SEC_GRUPO_IMPR "SECUENCIA",
                 COUNT(*) "ITEMS",
                 TO_CHAR(SUM(VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA),'999,990.00') "VALOR_BRUTO",
                 TO_CHAR(SUM(VTA_DET.VAL_PREC_TOTAL),'999,990.00') "VALOR_NETO",
                 TO_CHAR((SUM(VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA) - SUM(VTA_DET.VAL_PREC_TOTAL)),'999,990.00') "VALOR_DESCUENTO",
                 TO_CHAR(SUM(VTA_DET.VAL_PREC_TOTAL / (1 + (VTA_DET.VAL_IGV/100))),'999,990.00') "VALOR_AFECTO",
                 TO_CHAR(SUM(VTA_DET.VAL_PREC_TOTAL - (VTA_DET.VAL_PREC_TOTAL / (1 + (VTA_DET.VAL_IGV/100)))),'999,990.00') "VALOR_IGV",
                 TO_CHAR(VTA_DET.VAL_IGV,'990.00') "PORC_IGV"
          FROM   VTA_PEDIDO_VTA_DET VTA_DET
          WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
          AND     VTA_DET.COD_LOCAL = cCodLocal_in
          AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
          AND     VTA_DET.SEC_GRUPO_IMPR <> 0
          GROUP BY SEC_GRUPO_IMPR, VAL_IGV
          ORDER BY VTA_DET.SEC_GRUPO_IMPR;

       CURSOR curComprobantes is
      select *
      from   vta_comp_pago g
      where  g.cod_grupo_cia = cCodGrupoCia_in
      and    g.cod_local = cCodLocal_in
      and    g.num_ped_vta = cNumPedVta_in;
      fSC curComprobantes%rowtype;

      pIndComManual vta_pedido_vta_cab.ind_comp_manual%type;

  BEGIN

        IF PTOVENTA_FIDELIZACION.FID_F_VALIDA_DNI_PEDIDO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in) = 'N' THEN
        -- INICIO dubilluz 25.07.2011
           RAISE_APPLICATION_ERROR(-20050,
           'NO SE PUEDE COBRAR.Porque el DNI no tiene el formato correcto para usar la campaña.');
           v_Resultado := 'ERROR';
           return v_Resultado;
        -- FIN dubilluz 25.07.2011
        END IF;
        -- NO SE APLICA EN VERSION 2.4.0
        -- Ya que lo validara en el Nuevo Cobro al final antes del COmmit
        /*
        IF PTOVENTA_FIDELIZACION.FID_F_VALIDA_COBRO_PEDIDO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in) = 'N' THEN
        -- INICIO dubilluz 25.07.2011
           RAISE_APPLICATION_ERROR(-20040,
           'NO SE PUEDE COBRAR.Porque el descuento usado no cumple con la forma de Pago.');
           v_Resultado := 'ERROR';
           return v_Resultado;

        END IF;
        */
        -- FIN dubilluz 25.07.2011

        --Si el tipo de comprobante del usuario que cobra es distinto al que genero el pedido no permite cobrar
        -- dubilluz 14.10.2014
        SELECT X.TIP_COMP_PAGO,nvl(x.ind_comp_manual,'N') INTO TipComp,pIndComManual
        FROM VTA_PEDIDO_VTA_CAB X
        WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
        AND X.COD_LOCAL=cCodLocal_in
        AND X.NUM_PED_VTA=cNumPedVta_in;

        --JCORTEZ 15.05.09 Se valida el tipo de comprobante
        --JCORTEZ 10.06.09 NO se valida relacion caja impresora cuando es ticket o boleta
        if pIndComManual = 'N'  then
            IF  (TipComp = COD_TIP_COMP_TICKET or TipComp = COD_TIP_COMP_BOLETA) THEN
               /* SELECT A.SEC_USU_LOCAL INTO SecUso
                  FROM PBL_USU_LOCAL A
                  WHERE TRIM(A.LOGIN_USU)=TRIM(cUsuCreaCompPago_in)
                  AND A.COD_GRUPO_CIA=cCodGrupoCia_in
                  AND A.COD_LOCAL=cCodLocal_in;

                  SELECT B.NUM_CAJA_PAGO INTO NumCaja
                  FROM VTA_CAJA_PAGO B
                  WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
                  AND B.COD_LOCAL=cCodLocal_in
                  AND B.SEC_USU_LOCAL=TRIM(SecUso);

                  SELECT D.TIP_COMP INTO TipCompAux
                  FROM VTA_CAJA_IMPR C,
                       VTA_IMPR_LOCAL D
                  WHERE C.COD_GRUPO_CIA=cCodGrupoCia_in
                  AND C.COD_LOCAL=cCodLocal_in
                  AND C.NUM_CAJA_PAGO=NumCaja
                  AND D.TIP_COMP IN (COD_TIP_COMP_TICKET,COD_TIP_COMP_BOLETA)
                  AND C.SEC_IMPR_LOCAL=D.SEC_IMPR_LOCAL
                  AND C.COD_GRUPO_CIA=D.COD_GRUPO_CIA
                  AND C.COD_LOCAL=D.COD_LOCAL;*/


                  --Se valida el tipo de impresora que tiene asiganda la ip
                  --COMENTADO LLEIVA: ahora se obtiene la IP del parametro cIp_in
                  SELECT SYS_CONTEXT('USERENV','IP_ADDRESS') INTO cIP
                  FROM DUAL;

                  SELECT B.TIP_COMP INTO TipCompAux
                  FROM VTA_IMPR_LOCAL B
                  WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
                  AND B.COD_LOCAL=cCodLocal_in
                  AND B.SEC_IMPR_LOCAL IN (SELECT A.SEC_IMPR_LOCAL
                                              FROM VTA_IMPR_IP A
                                              WHERE A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
                                              AND A.COD_LOCAL=B.COD_LOCAL
                                              AND TRIM(A.IP)=TRIM(cIP));
                                              --AND TRIM(A.IP)=TRIM(cIp_in));

                  --Si el tipo de comprobante del usuario que cobra es distinto al que genero el pedido no permite cobrar
                  IF(TipCompAux<>TipComp)THEN
                     RAISE_APPLICATION_ERROR(-20021,'El tipo de comprobante relacionado a la caja actual es diferente al que genero el pedido.');
                  END IF;
            END IF;
        end if;
        v_Resultado := 'ERROR';
        v_cIndGraboComp := INDICADOR_NO;
        v_nContador := 0;
        --dbms_output.put_line('v_cIndGraboComp: ' || v_cIndGraboComp );
        SELECT VTA_CAB.VAL_REDONDEO_PED_VTA,
               VTA_CAB.COD_CLI_LOCAL,
               NVL(VTA_CAB.NOM_CLI_PED_VTA,' '),
               NVL(VTA_CAB.RUC_CLI_PED_VTA,' '),
               NVL(VTA_CAB.DIR_CLI_PED_VTA,' '),
               VTA_CAB.IND_DISTR_GRATUITA,
               VTA_CAB.IND_DELIV_AUTOMATICO
        INTO v_nValRedondeo,
             v_cCodCliLocal,
             v_cNomCliPedVta,
             v_cRucCliPedVta,
             v_cDirCliPedVta,
             v_cIndDistrGratuita,
             v_cIndDelivAutomatico
        FROM VTA_PEDIDO_VTA_CAB VTA_CAB
        WHERE VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   VTA_CAB.COD_LOCAL = cCodLocal_in
        AND   VTA_CAB.NUM_PED_VTA = cNumPedVta_in;

        FOR totalesComprobante_rec IN totalesComprobante
        LOOP
            v_nContador := v_nContador + 1;
            IF(v_nContador <> 1) THEN
                v_nValRedondeo := 0.00;
            END IF;
            IF(v_cIndDistrGratuita = INDICADOR_SI) THEN
                totalesComprobante_rec.VALOR_BRUTO := 0.00;
                totalesComprobante_rec.VALOR_DESCUENTO := 0.00;
            END IF;
            v_nSecCompPago := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in);
            v_nSecCompPago := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nSecCompPago, 10, 0, POS_INICIO);

            --v_nSecCompPago := totalesComprobante_rec.SECUENCIA;
            /*dbms_output.put_line('v_nSecCompPago: ' || v_nSecCompPago );
            dbms_output.put_line('1: ' || totalesComprobante_rec.ITEMS );
            dbms_output.put_line('2: ' || totalesComprobante_rec.VALOR_BRUTO );
            dbms_output.put_line('3: ' || totalesComprobante_rec.VALOR_NETO );
            dbms_output.put_line('4: ' || totalesComprobante_rec.VALOR_DESCUENTO );
            dbms_output.put_line('5: ' || totalesComprobante_rec.VALOR_AFECTO );
            dbms_output.put_line('6: ' || totalesComprobante_rec.VALOR_IGV );*/

            INSERT INTO VTA_COMP_PAGO(COD_GRUPO_CIA,
                                      COD_LOCAL,
                                      NUM_PED_VTA,
                                      SEC_COMP_PAGO,
                                      TIP_COMP_PAGO,
                                      NUM_COMP_PAGO,
                                      SEC_MOV_CAJA,
                                      CANT_ITEM,
                                      COD_CLI_LOCAL,
                                      NOM_IMPR_COMP,
                                      DIREC_IMPR_COMP,
                                      NUM_DOC_IMPR,
                                      VAL_BRUTO_COMP_PAGO,
                                      VAL_NETO_COMP_PAGO,
                                      VAL_DCTO_COMP_PAGO,
                                      VAL_AFECTO_COMP_PAGO,
                                      VAL_IGV_COMP_PAGO,
                                      VAL_REDONDEO_COMP_PAGO,
                                      USU_CREA_COMP_PAGO,
                                      PORC_IGV_COMP_PAGO
                                    )
            --FECHA_COBRO)--jcortez 06.07.09 se guarda fecha de cobro
            VALUES(cCodGrupoCia_in,
                   cCodLocal_in,
                   cNumPedVta_in,
                   v_nSecCompPago,
                   TIPO_COMPROBANTE_99,
                   v_nSecCompPago,
                   cSecMovCaja_in,
                   TO_NUMBER(totalesComprobante_rec.ITEMS,'9,990'),
                   v_cCodCliLocal,
                   v_cNomCliPedVta,
                   v_cDirCliPedVta,
                   v_cRucCliPedVta,
                   TO_NUMBER(totalesComprobante_rec.VALOR_BRUTO,'999,990.00'),
                   TO_NUMBER(totalesComprobante_rec.VALOR_NETO,'999,990.00'),
                   TO_NUMBER(totalesComprobante_rec.VALOR_DESCUENTO,'999,990.00'),
                   TO_NUMBER(totalesComprobante_rec.VALOR_AFECTO,'999,990.00'),
                   TO_NUMBER(totalesComprobante_rec.VALOR_IGV,'999,990.00'),
                   v_nValRedondeo,
                   cUsuCreaCompPago_in,
                   TO_NUMBER(totalesComprobante_rec.PORC_IGV,'990.00')
                   );
                   --,SYSDATE);

            --ACTUALIZA DETALLE PEDIDO
            UPDATE VTA_PEDIDO_VTA_DET
            SET USU_MOD_PED_VTA_DET = cUsuCreaCompPago_in,
                FEC_MOD_PED_VTA_DET = SYSDATE,
                SEC_COMP_PAGO = v_nSecCompPago
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND   COD_LOCAL = cCodLocal_in
            AND   NUM_PED_VTA = cNumPedVta_in
            AND   SEC_GRUPO_IMPR = totalesComprobante_rec.SECUENCIA;

            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                       cCodLocal_in,
                                                       cCodNumera_in,
                                                       cUsuCreaCompPago_in);
            v_cIndGraboComp := INDICADOR_SI;

        END LOOP;


       open curComprobantes;
       LOOP
       FETCH curComprobantes INTO fSC;
       EXIT WHEN curComprobantes%NOTFOUND;
                   /* ****************************************************************** */
                  --Descripcion: Actulizar los campos nuevo de la tabla VTA_COMP_PAGO
                  --Fecha       Usuario        Comentario
                  --22/07/2014  LTAVARA         Creacion
                  --07/10/2014  DUBILLUZ        Modificacion
                  FARMA_EPOS.sp_upd_comp_pago_e(fSC.Cod_Grupo_Cia,
                                                      fSC.Cod_Local,
                                                      fSC.Num_Ped_Vta,
                                                      fSC.Sec_Comp_Pago,
                                                      null);
       END LOOP;
       close curComprobantes;



        -- graba el ajuste de pedice_hist_forma_pago_entregado 19.11.2013
        -- dubilluz
        pkg_sol_stock.sp_aprueba_sol(cCodGrupoCia_in,
                                     cCodLocal_in,
                                     cNumPedVta_in);
        --- dubilluz
        IF(v_cIndGraboComp = INDICADOR_SI) THEN
            --ACTUALIZA STK PRODUCTO
            /*CAJ_ACTUALIZA_STK_PROD_DETALLE(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, --ASOSA, 11.07.2010, comentado por mientras para pruebas
                                             cCodMotKardex_in, cTipDocKardex_in, cCodNumeraKardex_in,
                                             cUsuCreaCompPago_in);*/

            PTOVTA_RESPALDO_STK.CAJ_UPD_STK_PROD_DETALLE(cCodGrupoCia_in,
                                                         cCodLocal_in,
                                                         cNumPedVta_in, --ASOSA, 11.07.2010
                                                         cCodMotKardex_in,
                                                         cTipDocKardex_in,
                                                         cCodNumeraKardex_in,
                                                         cUsuCreaCompPago_in);

    --INI ASOSA - 07/10/2014 - PANHD
          PTOVTA_RESPALDO_STK.CAJ_UPD_STK_PROD_COMP(cCodGrupoCia_in,
                                                         cCodLocal_in,
                                                         cNumPedVta_in,
                                                         cCodMotKardex_in,    --ASOSA - 17/10/2014
                                                         cTipDocKardex_in,
                                                         cCodNumeraKardex_in,
                                                         cUsuCreaCompPago_in);
          --FIN ASOSA - 07/10/2014 - PANHD

            --dbms_output.put_line('cSecMovCaja_in=' || cSecMovCaja_in);
            --dbms_output.put_line('cCodGrupoCia_in=' || cCodGrupoCia_in);
            --dbms_output.put_line('cCodLocal_in=' || cCodLocal_in);
            --dbms_output.put_line('cNumPedVta_in=' || cNumPedVta_in);

            --ACTUALIZA CABECERA PEDIDO
            UPDATE VTA_PEDIDO_VTA_CAB
            SET USU_MOD_PED_VTA_CAB = cUsuCreaCompPago_in,
                FEC_MOD_PED_VTA_CAB = SYSDATE,
                SEC_MOV_CAJA = cSecMovCaja_in,
                EST_PED_VTA = INDICADOR_SI, --PEDIDO COBRADO SIN COMPROBANTE IMPRESO
                TIP_COMP_PAGO = cTipCompPago_in,
                FEC_PED_VTA = SYSDATE
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND   COD_LOCAL = cCodLocal_in
            AND   NUM_PED_VTA = cNumPedVta_in;

            --PARA PROBAR
            /*UPDATE VTA_PEDIDO_VTA_CAB SET VAL_NETO_PED_VTA = VAL_NETO_PED_VTA+2
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND     COD_LOCAL = cCodLocal_in
            AND     NUM_PED_VTA = cNumPedVta_in;
            */
            /**VERIFICA SI EL TOTAL DE CABECERA COINCIDE CON EL TOTAL DE LAS FORMAS DE PAGO DEL PEDIDO**/

            --COMENTADO POR JCALLO 05.11.2008
            --v_Resultado := CAJ_VERIFICA_TOTAL_PED_FOR_PAG(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, cDescDetalleForPago_in);

            /*
            --TODO SE VALIDARA EN EL JAVA
            --dubilluz 25.04.2014
            --ERIOS 18.10.2013 Valida nuevo modelo de cobro
            IF PTOVENTA_GRAL.GET_IND_NUEVO_COBRO = 'S' THEN
                v_Resultado := 'EXITO';
            ELSE
                v_Resultado := CAJ_F_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in);
            END IF;
            */
            --TODO SE VALIDARA EN EL JAVA
            --dubilluz 25.04.2014

            --v_Resultado := 'ERROR';
            --------------------------------------------------
            ----Agregado Para el Cobro  DUBILLUZ  27/08/2007
            --------------------------------------------------
            --COMENTADO en la funcion CAJ_F_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in);
            -- ya valida el flag de que permita o no cobrar si ocurre inconsistencia de montos entre
            -- cabecera , detalle y forma de pago.
            v_Resultado := 'EXITO';

            IF v_Resultado = 'ERROR' THEN
                --Atualizando el Indicador de Linea con el Local
                FARMA_GRAL.INV_ACTUALIZA_IND_LINEA(cUsuCreaCompPago_in,cCodLocal_in,cCodGrupoCia_in);

                --Obtenemos el Indicador Actualizado
                v_Indicador_Linea :=FARMA_GRAL.INV_OBTIENE_IND_LINEA(cCodLocal_in,cCodGrupoCia_in);
                IF v_Indicador_Linea = 'FALSE' THEN
                     -- SI NO HAY LINEA TONCES SE PERMITIRA COBRAR MAS ALLA SI EL PARAMETRO DE COBRO ESTE O NO EN N
                     v_Resultado := 'EXITO';

                      /*--NO HAY CONEXION CON EL LOCAL
                      --ACTUALIZA EL INDICADOR A 'S'
                      --  UPDATE COBRO_PEDIDO SET IND_COBRO = 'S';
                      --SE PERMITE COBRAR
                        v_Resultado := 'EXITO';
                      ELSE
                        IF v_Indicador_Linea = 'TRUE' THEN
                      --SI HAY CONEXION CON EL LOCAL
                      --CONSULTA DEL INDICADOR PARA PERMITIR EL COBRO
                        Select IND_COBRO into v_Indicador_Cobro
                        from COBRO_PEDIDO ;
                         -------------
                         IF v_Indicador_Cobro = 'S' THEN
                           v_Resultado := 'EXITO';
                         ELSE
                           IF v_Indicador_Cobro = 'N' THEN
                             v_Resultado := 'ERROR';
                             END IF;
                         END IF;
                         -------------
                      END IF;*/
                END IF;
            end if;
            ---------------------------------------------------
            IF(v_cIndDelivAutomatico = INDICADOR_SI) THEN
                UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB
                SET TMP_CAB.USU_MOD_PED_VTA_CAB = cUsuCreaCompPago_in,
                    TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
                    TMP_CAB.EST_PED_VTA = EST_PED_COBRADO
                WHERE TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
                AND   TMP_CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
            END IF;

            --ERIOS 28/07/2008 Se graba el monto ahorrado en la ultimo comprobante
            UPDATE VTA_COMP_PAGO
            SET VAL_DCTO_COMP = (SELECT DECODE(X.IND_DELIV_AUTOMATICO,'S',0,'N',VAL_DCTO_PED_VTA)
                                 FROM VTA_PEDIDO_VTA_CAB X
                                 WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND X.COD_LOCAL = cCodLocal_in
                                 AND X.NUM_PED_VTA = cNumPedVta_in)
            WHERE (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO) =
                  (SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,MAX(SEC_COMP_PAGO)
                   FROM VTA_COMP_PAGO
                   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                     AND COD_LOCAL = cCodLocal_in
                     AND NUM_PED_VTA = cNumPedVta_in
                   GROUP BY COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA);
            IF v_Resultado='EXITO' THEN
                IF cPermiteCampana='S' THEN
                   --- 16-jul-14 tct DEBUG
                   --SP_GRABA_LOG('EN PROC. CAJ_COBRA_PEDIDO');
                   --- 100.- Invoca la Generacion de los Cupones para el Pedido
                    CAJ_GENERA_CUPON(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cUsuCreaCompPago_in,cDni_in);
                END IF;

                -- 20.08.2008 dubilluz se realizara en Java
                -- VALIDA_USO_CUPONES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cUsuCreaCompPago_in );
                --Inactiva Campana GOLD
                --DUBILLUZ 06.04.2009
                ptoventa_tareas.p_inactiva_camp_primera_compra(ccodgrupocia_in,ccodlocal_in,cnumpedvta_in);

                --Inserta en la tabla de ahorro x DNI para validar el maximo Ahorro en el dia o Semana
                --dubilluz 28.05.2009
                select sum(d.ahorro)
                into   vAhorroPedido
                from   vta_pedido_vta_det d
                where  d.cod_grupo_cia = cCodGrupoCia_in
                and    d.cod_local = cCodLocal_in
                and    d.num_ped_vta = cNumPedVta_in;

                if vAhorroPedido > 0 then
                    insert into vta_ped_dcto_cli_aux (COD_GRUPO_CIA,
                                                      COD_LOCAL,
                                                      NUM_PED_VTA,
                                                      VAL_DCTO_VTA,
                                                      DNI_CLIENTE,
                                                      FEC_CREA_PED_VTA_CAB)
                    select c.cod_grupo_cia,
                           c.cod_local,
                           c.num_ped_vta,
                           sum(d.ahorro),
                           t.dni_cli,
                           c.fec_ped_vta
                    from   vta_pedido_vta_det d,
                           vta_pedido_vta_cab c,
                           fid_tarjeta_pedido t
                    where  c.cod_grupo_cia = cCodGrupoCia_in
                    and    c.cod_local = cCodLocal_in
                    and    c.num_ped_vta =  cNumPedVta_in
                    and    c.cod_grupo_cia = d.cod_grupo_cia
                    and    c.cod_local = d.cod_local
                    and    c.num_ped_vta = d.num_ped_vta
                    and    c.cod_grupo_cia = t.cod_grupo_cia
                    and    c.cod_local = t.cod_local
                    and    c.num_ped_vta = t.num_pedido
                    group by c.cod_grupo_cia,c.cod_local,c.num_ped_vta,t.dni_cli,c.fec_ped_vta;
            end if;
         END IF;
      END IF;
      RETURN v_Resultado;
  END;

/* ********************************************************************************************** */


  /* ********************************************************************************************** */
  --21/11/2007 dubilluz modificado
  PROCEDURE CAJ_ACTUALIZA_STK_PROD_DETALLE(cCodGrupoCia_in      IN CHAR,
                                             cCodLocal_in         IN CHAR,
                                            cNumPedVta_in        IN CHAR,
                                           cCodMotKardex_in    IN CHAR,
                                            cTipDocKardex_in    IN CHAR,
                                           cCodNumeraKardex_in IN CHAR,
                                            cUsuModProdLocal_in IN CHAR)
  IS
    --v_cIndActStk CHAR(1);
    v_cIndProdVirtual CHAR(1);
    mesg_body VARCHAR2(4000);
  CURSOR productos_Kardex IS
         SELECT VTA_DET.COD_PROD,
                 --SUM(VTA_DET.CANT_ATENDIDA) CANT_ATENDIDA,
                --SUM((VTA_DET.CANT_ATENDIDA*PROD_LOCAL.VAL_FRAC_LOCAL)/VTA_DET.VAL_FRAC) CANT_ATENDIDA,
                SUM(VTA_DET.CANT_FRAC_LOCAL) CANT_ATENDIDA, --ERIOS 29/05/2008 Cantidad calculada
                PROD_LOCAL.STK_FISICO,
                 PROD_LOCAL.VAL_FRAC_LOCAL,
                 PROD_LOCAL.UNID_VTA,
                DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR
         FROM   VTA_PEDIDO_VTA_DET VTA_DET,
                LGT_PROD_LOCAL PROD_LOCAL,
                LGT_PROD_VIRTUAL VIR
         WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    VTA_DET.COD_LOCAL     = cCodLocal_in
         AND    VTA_DET.NUM_PED_VTA   = cNumPedVta_in
         AND    VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
         AND    VTA_DET.COD_LOCAL     = PROD_LOCAL.COD_LOCAL
         AND    VTA_DET.COD_PROD      = PROD_LOCAL.COD_PROD
         AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
         AND    PROD_LOCAL.COD_PROD = VIR.COD_PROD(+)
         GROUP BY VTA_DET.COD_PROD,
                   PROD_LOCAL.VAL_FRAC_LOCAL,
                   PROD_LOCAL.UNID_VTA,
                  PROD_LOCAL.STK_FISICO,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI);
  --21/11/2007 dubilluz modificado
  CURSOR productos_Respaldo IS
         SELECT VTA_DET.COD_PROD,
                --SUM(VTA_DET.CANT_ATENDIDA) CANT_ATENDIDA,
                VTA_DET.CANT_ATENDIDA CANT_ATENDIDA,
                VTA_DET.VAL_FRAC AS VAL_FRAC_VTA,
                PROD_LOCAL.STK_FISICO,
                PROD_LOCAL.VAL_FRAC_LOCAL,
                PROD_LOCAL.UNID_VTA,
                DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR
         FROM   VTA_PEDIDO_VTA_DET VTA_DET,
                LGT_PROD_LOCAL PROD_LOCAL,
                LGT_PROD_VIRTUAL VIR
         WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    VTA_DET.COD_LOCAL = cCodLocal_in
         AND    VTA_DET.NUM_PED_VTA = cNumPedVta_in
         AND    VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
         AND    VTA_DET.COD_LOCAL = PROD_LOCAL.COD_LOCAL
         AND    VTA_DET.COD_PROD = PROD_LOCAL.COD_PROD
         AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
         AND    PROD_LOCAL.COD_PROD = VIR.COD_PROD(+)
         ORDER  BY VTA_DET.CANT_ATENDIDA DESC;
         /*GROUP BY VTA_DET.COD_PROD,
                  PROD_LOCAL.STK_FISICO,
                  PROD_LOCAL.VAL_FRAC_LOCAL,
                  PROD_LOCAL.UNID_VTA,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI);
                  */
    v_nCantAtendida VTA_PEDIDO_VTA_DET.CANT_ATENDIDA%TYPE;
  BEGIN
    --v_cIndActStk := INDICADOR_NO;
     FOR productos_K IN productos_Kardex
    LOOP
        --GRABAR KARDEX
        v_cIndProdVirtual := productos_K.IND_PROD_VIR;
        IF v_cIndProdVirtual = INDICADOR_SI THEN
          Ptoventa_Inv.INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in,
                                                 cCodLocal_in,
                                                 productos_K.COD_PROD,
                                                 cCodMotKardex_in,
                                                 cTipDocKardex_in,
                                                 cNumPedVta_in,
                                                 (productos_k.CANT_ATENDIDA * -1),
                                                 productos_K.VAL_FRAC_LOCAL,
                                                 productos_K.UNID_VTA,
                                                 cUsuModProdLocal_in,
                                                 cCodNumeraKardex_in);
        ELSE
          Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
                                         cCodLocal_in,
                                         productos_K.COD_PROD,
                                         cCodMotKardex_in,
                                         cTipDocKardex_in,
                                         cNumPedVta_in,
                                         productos_K.STK_FISICO,
                                         (productos_k.CANT_ATENDIDA * -1),
                                         productos_K.VAL_FRAC_LOCAL,
                                         productos_K.UNID_VTA,
                                         cUsuModProdLocal_in,
                                         cCodNumeraKardex_in);
        END IF;
        --v_cIndActStk := INDICADOR_SI;
    END LOOP;
    FOR productos_R IN productos_Respaldo
    LOOP
        v_cIndProdVirtual := productos_R.IND_PROD_VIR;
        IF v_cIndProdVirtual = INDICADOR_NO THEN
          --ACTUALIZA STK PRODUCTO
          --ERIOS 29/05/2008 Calcula la cantidad atendida a la fraccion del local.
          v_nCantAtendida := (productos_R.CANT_ATENDIDA*productos_R.VAL_FRAC_LOCAL)/productos_R.VAL_FRAC_VTA;
          UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = cUsuModProdLocal_in, FEC_MOD_PROD_LOCAL = SYSDATE,
                 STK_FISICO = STK_FISICO - v_nCantAtendida
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND     COD_LOCAL = cCodLocal_in
          AND     COD_PROD = productos_R.COD_PROD;

        END IF;
    END LOOP;


  EXCEPTION
    WHEN OTHERS THEN
         mesg_body := 'ERROR AL COBRAR PEDIDO No ' || cNumPedVta_in || '. ' || SQLERRM;
         FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    'joliva@mifarma.com.pe',
                                    'ERROR AL COBRAR PEDIDO',
                                    'ERROR',
                                    mesg_body,
                                    '');
         RAISE;
  END;

  /* ************************************************************************ */

  PROCEDURE CAJ_GRABAR_FORMA_PAGO_PEDIDO(cCodGrupoCia_in           IN CHAR,
                                          cCodLocal_in              IN CHAR,
                                         cCodFormaPago_in        IN CHAR,
                                          cNumPedVta_in             IN CHAR,
                                          nImPago_in                IN NUMBER,
                                         cTipMoneda_in           IN CHAR,
                                         nValTipCambio_in         IN NUMBER,
                                          nValVuelto_in            IN NUMBER,
                                          nImTotalPago_in          IN NUMBER,
                                         cNumTarj_in              IN CHAR,
                                         cFecVencTarj_in         IN CHAR,
                                         cNomTarj_in              IN CHAR,
                                         cCanCupon_in              IN NUMBER,
                                         cUsuCreaFormaPagoPed_in IN CHAR) IS
  BEGIN
    INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,
                                      COD_LOCAL,
                                      COD_FORMA_PAGO,
                                      NUM_PED_VTA,
                                      IM_PAGO,
                                      TIP_MONEDA,
                                      VAL_TIP_CAMBIO,
                                      VAL_VUELTO,
                                      IM_TOTAL_PAGO,
                                      NUM_TARJ,
                                      FEC_VENC_TARJ,
                                      NOM_TARJ,
                                      USU_CREA_FORMA_PAGO_PED,
                                      CANT_CUPON)
                              VALUES (cCodGrupoCia_in,
                                      cCodLocal_in,
                                      cCodFormaPago_in,
                                      cNumPedVta_in,
                                      nImPago_in,
                                      cTipMoneda_in,
                                      nValTipCambio_in,
                                      nValVuelto_in,
                                      nImTotalPago_in,
                                      cNumTarj_in,
                                      cFecVencTarj_in,
                                      cNomTarj_in,
                                      cUsuCreaFormaPagoPed_in,
                                      cCanCupon_in);
  END;

  /* ********************************************************************************************** */

  PROCEDURE CAJ_ALMACENAR_VALORES_COMP(cCodGrupooCia_in IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                       cSecMovCaja_in   IN CHAR,
                                       cTipOp_in        IN CHAR)
  IS
  CURSOR curLabs IS
         SELECT FPP.COD_FORMA_PAGO ,
                FP.DESC_CORTA_FORMA_PAGO ,
                DECODE(TIP_MONEDA,'01','Soles','Dolares') ,
                SUM(FPP.IM_PAGO) "IM_PAGO" ,
                SUM(FPP.IM_TOTAL_PAGO) "IM_TOTAL_PAGO",
                TIP_MONEDA
         FROM   VTA_FORMA_PAGO_PEDIDO FPP,
                VTA_FORMA_PAGO FP,
                VTA_FORMA_PAGO_LOCAL FPL,
                VTA_PEDIDO_VTA_CAB CAB
         WHERE  CAB.COD_GRUPO_CIA  = cCodGrupooCia_in    AND
                CAB.COD_LOCAL      = cCodLocal_in        AND
                CAB.SEC_MOV_CAJA   = (SELECT SEC_MOV_CAJA_ORIGEN
                                      FROM   CE_MOV_CAJA
                                      WHERE  COD_GRUPO_CIA = cCodGrupooCia_in AND
                                      COD_LOCAL     = cCodLocal_in AND
                                      SEC_MOV_CAJA  = cSecMovCaja_in) AND
                CAB.EST_PED_VTA    = EST_PED_COBRADO            AND
                FP.COD_GRUPO_CIA   = FPL.COD_GRUPO_CIA  AND
                FP.COD_FORMA_PAGO  = FPL.COD_FORMA_PAGO AND
                fpl.cod_grupo_cia  = fpp.cod_grupo_cia  AND
                fpl.cod_local      = fpp.COD_LOCAL      AND
                FPL.COD_FORMA_PAGO = FPP.COD_FORMA_PAGO AND
                CAB.COD_GRUPO_CIA  = FPP.COD_GRUPO_CIA  AND
                CAB.COD_LOCAL      = FPP.COD_LOCAL    AND
                CAB.NUM_PED_VTA    = FPP.NUM_PED_VTA
         GROUP BY FPP.COD_FORMA_PAGO,
                  DESC_CORTA_FORMA_PAGO,
                  TIP_MONEDA,
                  DECODE(TIP_MONEDA,'01','Soles','Dolares')
         UNION
         SELECT '00000' "COD_FORMA_PAGO",
                'VUELTO' "DESC_CORTA_FORMA_PAGO",
                'Soles' "DESC_CORTA",
                SUM(FPP.VAL_VUELTO * -1) "IM_PAGO",
                SUM(FPP.VAL_VUELTO * -1) "IM_TOTAL_PAGO",
                '01' "TIP_MONEDA"
          FROM  VTA_FORMA_PAGO_PEDIDO FPP,
                VTA_PEDIDO_VTA_CAB CAB
          WHERE CAB.COD_GRUPO_CIA  = cCodGrupooCia_in    AND
                CAB.COD_LOCAL      = cCodLocal_in      AND
                CAB.SEC_MOV_CAJA   = (SELECT SEC_MOV_CAJA_ORIGEN
                                      FROM   CE_MOV_CAJA
                                      WHERE  COD_GRUPO_CIA = cCodGrupooCia_in AND
                                             COD_LOCAL     = cCodLocal_in AND
                                             SEC_MOV_CAJA  = cSecMovCaja_in) AND
                CAB.EST_PED_VTA    = EST_PED_COBRADO AND
                CAB.COD_GRUPO_CIA  = FPP.COD_GRUPO_CIA AND
                CAB.COD_LOCAL      = FPP.COD_LOCAl AND
                CAB.NUM_PED_VTA    = FPP.NUM_PED_VTA;

  regLab curLabs%ROWTYPE;
  BEGIN
       OPEN curLabs;
       LOOP
       FETCH curLabs INTO regLab;
           EXIT WHEN curLabs%NOTFOUND;

           INSERT INTO CE_MOV_CAJA_FORMA_PAGO_SIST(COD_GRUPO_CIA,
                                                    COD_LOCAL,
                                                   SEC_MOV_CAJA,
                                                   COD_FORMA_PAGO,
                                                   TIP_MONEDA,
                                                   IM_PAGO,
                                                   IM_TOTAL)
                                            VALUES(cCodGrupooCia_in,
                                                   cCodLocal_in,
                                                   cSecMovCaja_in,
                                                   regLab.COD_FORMA_PAGO,
                                                   regLab.TIP_MONEDA,
                                                   NVL(regLab.IM_PAGO,0),
                                                   NVL(regLab.IM_TOTAL_PAGO,0));

       END LOOP;
       CLOSE curLabs;
   END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_INFO_DETALLE_AGRUPACION(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                     cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    indConvenio CHAR(1):= 'N';
    vTipoDocumento VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE;
  BEGIN
    -- KMONCADA 09.12.2014 INDICADOR DE PEDIDO CONVENIO
    -- KMONCADA 2015.03.24 TIPO DE DOCUMENTO DEL PEDIDO
    BEGIN   
      SELECT DECODE(NVL(TRIM(CAB.COD_CONVENIO),'0'),'0','N','S'), CAB.TIP_COMP_PAGO
      INTO   indConvenio, vTipoDocumento
      FROM  VTA_PEDIDO_VTA_CAB CAB
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   CAB.COD_LOCAL     = cCodLocal_in
      AND   CAB.NUM_PED_VTA   = cNumPedVta_in;
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        indConvenio := 'N';
    END;
    -- KMONCADA 09.12.2014 LOS DOCUMENTOS PENDIENTE EN CASO DE CONVENIOS SE VERIFICARAN 
    -- SEGUN SEC_COMP_PAGO_BENEFI Y EMPRESA
    -- KMONCADA 2015.03.24 PARA EL CASO DE NOTAS DE CREDITO POR CONVENIO SE UTILIZARA EL QUERY DE
    -- AGRUPACION NORMAL
    IF indConvenio='S' and vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN
      OPEN curCaj FOR
      
      SELECT ((select count(*)
                FROM   VTA_COMP_PAGO COMP_PAGO
                WHERE COMP_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
                AND COMP_PAGO.COD_LOCAL = cCodLocal_in
                AND NUM_PED_VTA = cNumPedVta_in)
                || 'Ã' ||
                NVL(VTA_DET.SEC_COMP_PAGO_BENEF,' '))AS RESULTADO
      FROM   VTA_PEDIDO_VTA_DET VTA_DET, 
             VTA_COMP_PAGO P
      WHERE  P.COD_GRUPO_CIA = VTA_DET.COD_GRUPO_CIA
      AND    P.COD_LOCAL     = VTA_DET.COD_LOCAL
      AND    P.NUM_PED_VTA   = VTA_DET.NUM_PED_VTA
      AND    P.SEC_COMP_PAGO = CASE 
                                  WHEN P.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
                                    VTA_DET.SEC_COMP_PAGO
                                  ELSE
                                    VTA_DET.SEC_COMP_PAGO_BENEF
                               END
      AND    P.FECH_IMP_COBRO  IS NULL
      AND     VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     VTA_DET.COD_LOCAL = cCodLocal_in
      AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in

      UNION

      SELECT ((select count(*)
                FROM   VTA_COMP_PAGO COMP_PAGO
                WHERE COMP_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
                AND COMP_PAGO.COD_LOCAL = cCodLocal_in
                AND NUM_PED_VTA = cNumPedVta_in)
                || 'Ã' ||
                NVL(VTA_DET.SEC_COMP_PAGO_EMPRE,' '))AS RESULTADO
      FROM   VTA_PEDIDO_VTA_DET VTA_DET 
             , VTA_COMP_PAGO P
      WHERE  P.COD_GRUPO_CIA = VTA_DET.COD_GRUPO_CIA
      AND    P.COD_LOCAL     = VTA_DET.COD_LOCAL
      AND    P.NUM_PED_VTA   = VTA_DET.NUM_PED_VTA
      AND    P.SEC_COMP_PAGO = CASE 
                                  WHEN P.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
                                    VTA_DET.SEC_COMP_PAGO
                                  ELSE
                                    VTA_DET.SEC_COMP_PAGO_EMPRE
                               END
      AND    P.FECH_IMP_COBRO  IS NULL
      AND     VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     VTA_DET.COD_LOCAL = cCodLocal_in
      AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
      ;
    ELSE
      OPEN curCaj FOR
/*
-- LLEIVA 14-Mar-2014 Ahora se busca la cantidad de comprobantes de pago
--    SELECT NVL(VTA_DET.SEC_GRUPO_IMPR,0) || 'Ã' ||
      SELECT ((select count(*)
              FROM   VTA_COMP_PAGO COMP_PAGO
              WHERE COMP_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
              AND COMP_PAGO.COD_LOCAL = cCodLocal_in
              AND NUM_PED_VTA = cNumPedVta_in)
-- FIN LLEIVA
              || 'Ã' ||
              NVL(VTA_DET.SEC_COMP_PAGO,' '))AS RESULTADO
    FROM   VTA_PEDIDO_VTA_DET VTA_DET
    WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     VTA_DET.COD_LOCAL = cCodLocal_in
    AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
    --AND     VTA_DET.SEC_GRUPO_IMPR <> 0
    GROUP BY VTA_DET.SEC_GRUPO_IMPR, VTA_DET.SEC_COMP_PAGO
    ORDER BY VTA_DET.SEC_GRUPO_IMPR;*/
        
      SELECT ((select count(*)
                FROM   VTA_COMP_PAGO COMP_PAGO
                WHERE COMP_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
                AND COMP_PAGO.COD_LOCAL = cCodLocal_in
                AND NUM_PED_VTA = cNumPedVta_in)
  -- FIN LLEIVA
                || 'Ã' ||
                NVL(VTA_DET.SEC_COMP_PAGO,' '))AS RESULTADO
      FROM   VTA_PEDIDO_VTA_DET VTA_DET 
             , VTA_COMP_PAGO P
      --RHERRERA 10.11.2014
      WHERE  P.COD_GRUPO_CIA = VTA_DET.COD_GRUPO_CIA
      AND    P.COD_LOCAL     = VTA_DET.COD_LOCAL
      AND    P.NUM_PED_VTA   = VTA_DET.NUM_PED_VTA
      AND    P.SEC_COMP_PAGO = VTA_DET.SEC_COMP_PAGO
      AND    P.FECH_IMP_COBRO  IS NULL
      ------------- impresion solo sin fecha impresion
      AND     VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     VTA_DET.COD_LOCAL = cCodLocal_in
      AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
      --AND     VTA_DET.SEC_GRUPO_IMPR <> 0
      GROUP BY VTA_DET.SEC_GRUPO_IMPR, VTA_DET.SEC_COMP_PAGO
      ORDER BY VTA_DET.SEC_GRUPO_IMPR;
      
    END IF;
    
    RETURN curCaj;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_INFO_DETALLE_IMPRESION(cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                      cNumPedVta_in    IN CHAR,
                                      cSecGrupoImpr_in IN CHAR)
   RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    v_cTipVta  VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE;
    v_cIndConvEnteros VTA_PEDIDO_VTA_CAB.IND_CONV_ENTEROS%TYPE;
    v_promocion  CHAR(9):='';
  BEGIN
    SELECT TIP_PED_VTA, IND_CONV_ENTEROS
           INTO v_cTipVta, v_cIndConvEnteros
    FROM VTA_PEDIDO_VTA_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;

    SELECT LLAVE_TAB_GRAL INTO v_promocion
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL='188'
    AND COD_APL='PTO_VENTA';

    --SE varia el precio a mostrar en venta institucional.
    --07.05.2009 dubilluz
    IF v_cTipVta = '03'  THEN
    OPEN curCaj FOR
      SELECT VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC)|| 'Ã' ||
                     CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
                     THEN  SUBSTR(v_promocion,1,9) ||' '|| PROD.DESC_PROD
                     ELSE  PROD.DESC_PROD
                     END   || 'Ã' ||
                     VTA_DET.UNID_VTA                                      || 'Ã' ||
                     LAB.NOM_LAB                                           || 'Ã' ||
                     TO_CHAR((VTA_DET.Val_Prec_Total + nvl(vta_det.ahorro,0))/(vta_det.cant_atendida * prod_local.VAL_FRAC_LOCAL / vta_det.val_frac),'999,990.000')           || 'Ã' ||

                     TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')          || 'Ã' ||
                     VTA_DET.COD_PROD                                      || 'Ã' ||
                     NVL(VTA_DET.NUM_LOTE_PROD,' ')                        || 'Ã' ||
                     DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) || 'Ã' ||
                     NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
                     NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
                     NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
                     NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ')                  || 'Ã' ||
                     PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA || 'Ã' ||
                     TO_CHAR(VTA_DET.AHORRO,'999,990.000')  || 'Ã' ||

                      -- precio nuevo de institucional
                       -- 07.04.2009 ******* dubilluz
                       case
                        when pp.val_prec_vta is null
                        then
                             TO_CHAR(
                                     (VTA_DET.Val_Prec_Total + nvl(vta_det.ahorro,0))/
                                     (vta_det.cant_atendida * prod_local.VAL_FRAC_LOCAL / vta_det.val_frac),
                                     '999,990.000')
                        else
                             case
                              when  (
                                     (VTA_DET.Val_Prec_Total + nvl(vta_det.ahorro,0))/
                                     (vta_det.cant_atendida * prod_local.VAL_FRAC_LOCAL / vta_det.val_frac)
                                    ) >= (pp.val_prec_vta/vta_det.val_frac)
                              then
                                    TO_CHAR(
                                     (VTA_DET.Val_Prec_Total + nvl(vta_det.ahorro,0))/
                                     (vta_det.cant_atendida * prod_local.VAL_FRAC_LOCAL / vta_det.val_frac),
                                     '999,990.000')
                              else
                                    TO_CHAR(
                                     (pp.val_prec_vta/vta_det.val_frac),
                                     '999,990.000')
                             end
                       end   || 'Ã' ||
                       ---***
                       case
                        when pp.val_prec_vta is null
                        then
                             TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')
                        else
                             case
                              when  (
                                     (VTA_DET.Val_Prec_Total + nvl(vta_det.ahorro,0))/
                                     (vta_det.cant_atendida * prod_local.VAL_FRAC_LOCAL / vta_det.val_frac)
                                    ) >= (pp.val_prec_vta/vta_det.val_frac)
                              then
                                    TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')
                              else
                                    TO_CHAR(
                                     (pp.val_prec_vta/vta_det.val_frac)*vta_det.cant_atendida,
                                     '999,990.00')
                             end
                       end
                       /*jquispe 23.07.2010 cambio para calcular el precio total*/
              || 'Ã' ||  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA*VTA_DET.CANT_ATENDIDA,'999,990.00')

              FROM   VTA_PEDIDO_VTA_DET VTA_DET,
                     LGT_PROD_LOCAL PROD_LOCAL,
                     LGT_PROD PROD,
                     LGT_LAB LAB,
                     LGT_PROD_VIRTUAL VIR,
                     --VTA_PROMOCION PROM --JCORTEZ 16/01/2008
                     (
                       select l.cod_grupo_cia,l.cod_prod,l.val_prec_vta
                       from   LGT_PRECIO_PROD_VI l
                       where  l.estado = 'A'
                       and    sysdate between l.fec_ini_vig and l.fec_fin_vig
                     ) pp
              WHERE  VTA_DET.COD_GRUPO_CIA    = cCodGrupoCia_in
              AND     VTA_DET.COD_LOCAL        = cCodLocal_in
              AND     VTA_DET.NUM_PED_VTA      = cNumPedVta_in
              AND     VTA_DET.SEC_GRUPO_IMPR   = cSecGrupoImpr_in
              -----
              AND    VTA_DET.COD_GRUPO_CIA = pp.COD_GRUPO_CIA(+)
              AND    VTA_DET.COD_PROD = PP.COD_PROD(+)
              -----
              --AND    PROM.ESTADO='A'
              AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
              AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
              AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
              AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
              AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
              AND     PROD.COD_LAB             = LAB.COD_LAB
              AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
              AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
              /*AND  VTA_DET.COD_GRUPO_CIA    = PROM.COD_GRUPO_CIA(+) --JCORTEZ 16/01/2008
              AND    VTA_DET.COD_PROM         = PROM.COD_PROM(+) */
              ORDER BY VTA_DET.SEC_PED_VTA_DET;

    ELSE
    --08/03/2007 ERIOS
    --SI ES UNA VENTA DELIVERY Y TIENE EL INDICADOR DE IMPRESION DE ENTEROS
    IF v_cTipVta = '02' AND v_cIndConvEnteros = INDICADOR_SI THEN
    OPEN curCaj FOR
        SELECT DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)||'',
                                                          VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC))|| 'Ã' ||
              --JCORTEZ 24/03/2008
             CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
             --THEN 'PROM - '||SUBSTR(PROM.COD_PROM,4,6)|| PROD.DESC_PROD
             THEN  SUBSTR(v_promocion,1,9) ||' '|| PROD.DESC_PROD
             ELSE  PROD.DESC_PROD
             END   || 'Ã' ||
             --PROD.DESC_PROD  || 'Ã' ||
             --JCORTEZ 16/01/2008
             /*CASE  WHEN PROM.COD_PROM IS NOT NULL
             THEN 'PROM - '||SUBSTR(PROM.COD_PROM,4,6)|| PROD.DESC_PROD
             ELSE  PROD.DESC_PROD
             END                                                         || 'Ã' ||*/
               DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_UNID_PRESENT,
                                                                  VTA_DET.UNID_VTA                                      )|| 'Ã' ||
               LAB.NOM_LAB                                           || 'Ã' ||
               DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,
                          TO_CHAR((VTA_DET.VAL_PREC_TOTAL+ nvl(vta_det.ahorro,0) )/(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC),'999,990.000'),
                          TO_CHAR(VTA_DET.VAL_PREC_VTA + nvl(vta_det.ahorro,0),'999,990.000') )|| 'Ã' ||
               TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')          || 'Ã' ||

               VTA_DET.COD_PROD                                      || 'Ã' ||
               NVL(VTA_DET.NUM_LOTE_PROD,' ')                        || 'Ã' ||
               DECODE(NVL(VIR.COD_PROD,'N'),'N','N','S') || 'Ã' ||
               NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
               NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
               NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
               NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
               NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
               NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ')                  || 'Ã' ||
               DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_PROD || ' / ' || PROD.DESC_UNID_PRESENT,
                                                                 PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA)  || 'Ã' ||

                --Agregado por DVELIZ 10.10.08

                TO_CHAR(VTA_DET.AHORRO,'999,990.000')
               /*jquispe 23.07.2010 cambio para calcular el precio total*/
              || 'Ã' ||  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA*VTA_DET.CANT_ATENDIDA,'999,990.00')
        FROM   VTA_PEDIDO_VTA_DET VTA_DET,
               LGT_PROD_LOCAL PROD_LOCAL,
               LGT_PROD PROD,
               LGT_LAB LAB,
               LGT_PROD_VIRTUAL VIR
               --VTA_PROMOCION PROM --JCORTEZ 16/01/2008
        WHERE  VTA_DET.COD_GRUPO_CIA    = cCodGrupoCia_in
        AND     VTA_DET.COD_LOCAL        = cCodLocal_in
        AND     VTA_DET.NUM_PED_VTA      = cNumPedVta_in
        AND     VTA_DET.SEC_GRUPO_IMPR   = cSecGrupoImpr_in
       -- AND    PROM.ESTADO='A'
        AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
        AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
        AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
        AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
        AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
        AND     PROD.COD_LAB             = LAB.COD_LAB
        AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
        AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
        /*AND  VTA_DET.COD_GRUPO_CIA    = PROM.COD_GRUPO_CIA --JCORTEZ 16/01/2008
        AND    VTA_DET.COD_PROM         = PROM.COD_PROM(+) */
        ORDER BY VTA_DET.SEC_PED_VTA_DET;

    ELSE --IMPRESION NORMAL
    OPEN curCaj FOR
        SELECT VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC)|| 'Ã' ||
                 --JCORTEZ 16/01/2008
               /* CASE  WHEN PROM.COD_PROM IS NOT NULL
                 THEN 'PROM - '||SUBSTR(PROM.COD_PROM,4,6)|| PROD.DESC_PROD
                 ELSE  PROD.DESC_PROD
                 END                                                 || 'Ã' ||*/
               --JCORTEZ 24/03/2008
               CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
               --THEN 'PROM - '||SUBSTR(PROM.COD_PROM,4,6)|| PROD.DESC_PROD
               THEN  SUBSTR(v_promocion,1,9) ||' '|| PROD.DESC_PROD
               ELSE  PROD.DESC_PROD
               END   || 'Ã' ||
               VTA_DET.UNID_VTA                                      || 'Ã' ||
               LAB.NOM_LAB                                           || 'Ã' ||
               --TO_CHAR(VTA_DET.VAL_PREC_VTA,'999,990.000')           || 'Ã' ||
               -- Se colocara el precio de venta normal
               TO_CHAR((VTA_DET.Val_Prec_Total + nvl(vta_det.ahorro,0))/(vta_det.cant_atendida * prod_local.VAL_FRAC_LOCAL / vta_det.val_frac),'999,990.000')           || 'Ã' ||

               TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')          || 'Ã' ||
               VTA_DET.COD_PROD                                      || 'Ã' ||
               NVL(VTA_DET.NUM_LOTE_PROD,' ')                        || 'Ã' ||
               DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) || 'Ã' ||
               NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
               NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
               NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
               NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
               NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
               NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ')                  || 'Ã' ||
               PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA || 'Ã' ||
               --Agregado por DVELIZ 10.10.08
               --  ROUND(VTA_DET.AHORRO,2)
               TO_CHAR(VTA_DET.AHORRO,'999,990.000')
               /*jquispe 23.07.2010 cambio para calcular el precio total*/
              || 'Ã' ||  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA*VTA_DET.CANT_ATENDIDA,'999,990.00')
        FROM   VTA_PEDIDO_VTA_DET VTA_DET,
               LGT_PROD_LOCAL PROD_LOCAL,
               LGT_PROD PROD,
               LGT_LAB LAB,
               LGT_PROD_VIRTUAL VIR
               --VTA_PROMOCION PROM --JCORTEZ 16/01/2008
        WHERE  VTA_DET.COD_GRUPO_CIA    = cCodGrupoCia_in
        AND     VTA_DET.COD_LOCAL        = cCodLocal_in
        AND     VTA_DET.NUM_PED_VTA      = cNumPedVta_in
        AND     VTA_DET.SEC_GRUPO_IMPR   = cSecGrupoImpr_in
        --AND    PROM.ESTADO='A'
        AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
        AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
        AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
        AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
        AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
        AND     PROD.COD_LAB             = LAB.COD_LAB
        AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
        AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
        /*AND  VTA_DET.COD_GRUPO_CIA    = PROM.COD_GRUPO_CIA(+) --JCORTEZ 16/01/2008
        AND    VTA_DET.COD_PROM         = PROM.COD_PROM(+) */
        ORDER BY VTA_DET.SEC_PED_VTA_DET;
    END IF;
    END IF;
    RETURN curCaj;
  END CAJ_INFO_DETALLE_IMPRESION;
  /* ********************************************************************************************** */

  FUNCTION CAJ_INFO_TOTALES_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                       cNumPedVta_in   IN CHAR,
                    cSecCompPago_in  IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    SELECT TO_CHAR(COMP_PAGO.VAL_BRUTO_COMP_PAGO,'999,990.00') || 'Ã' ||
         TO_CHAR(COMP_PAGO.VAL_NETO_COMP_PAGO + COMP_PAGO.VAL_REDONDEO_COMP_PAGO,'999,990.00') || 'Ã' ||
         TO_CHAR(COMP_PAGO.VAL_DCTO_COMP_PAGO,'999,990.00') || 'Ã' ||
         TO_CHAR(COMP_PAGO.VAL_IGV_COMP_PAGO,'999,990.00') || 'Ã' ||
         TO_CHAR(COMP_PAGO.VAL_AFECTO_COMP_PAGO,'999,990.00') || 'Ã' ||
         TO_CHAR(COMP_PAGO.VAL_REDONDEO_COMP_PAGO,'999,990.00') || 'Ã' ||
         TO_CHAR(COMP_PAGO.PORC_IGV_COMP_PAGO,'990.00') || 'Ã' ||
         NVL(COMP_PAGO.NOM_IMPR_COMP,' ') || 'Ã' ||
         NVL(COMP_PAGO.NUM_DOC_IMPR,' ') || 'Ã' ||
         NVL(COMP_PAGO.DIREC_IMPR_COMP,' ') || 'Ã' ||
         TO_CHAR(COMP_PAGO.FEC_CREA_COMP_PAGO,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
         TO_CHAR(COMP_PAGO.VAL_DCTO_COMP,'999,990.00')
    FROM   VTA_COMP_PAGO COMP_PAGO
    WHERE  COMP_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     COMP_PAGO.COD_LOCAL     = cCodLocal_in
    AND     COMP_PAGO.NUM_PED_VTA   = cNumPedVta_in
    AND     COMP_PAGO.SEC_COMP_PAGO = cSecCompPago_in;
    RETURN curCaj;
  END;

  /* ********************************************************************************************** */

  PROCEDURE CAJ_ACTUALIZA_COMPROBANTE_IMPR(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
                       cNumPedVta_in       IN CHAR,
                       cSecCompPago_in     IN CHAR,
                       cTipCompPago_in     IN CHAR,
                       cNumCompPago_in     IN CHAR,
                       cUsuModCompPago_in IN CHAR
                       )
  IS

  nCantidad  NUMBER;
  nNumComPago CHAR(3);
  nElectronico int;

  BEGIN

--OBTENER SI EL COMPROBANTE ES ELECTRONICO O NO
  select count(1)
  into   nElectronico
  from   VTA_COMP_PAGO  c
    WHERE  COD_GRUPO_CIA     = cCodGrupoCia_in 
    AND     COD_LOCAL         = cCodLocal_in
    AND     NUM_PED_VTA       = cNumPedVta_in
    AND     SEC_COMP_PAGO     = cSecCompPago_in
    and    c.cod_tip_proc_pago = 1;
           
           

 IF nElectronico = 0 OR cTipCompPago_in= '03' THEN --LTAVARA 28.08.14 SOLO CUANDO NO ES ELECTRONICO O EL TIPO DE DOCUMENTO ES UNA GUIA

    SELECT Substr(TRIM(cNumCompPago_in), 0, 3)
    INTO   nNumComPago
    FROM   DUAL;

    select count(1)
    into   nCantidad
    from   vta_serie_local s
    where  s.cod_grupo_cia = cCodGrupoCia_in
    and    s.cod_local = cCodLocal_in
    and    s.num_serie_local = nNumComPago;

    IF nCantidad  = 0 THEN
       RAISE_APPLICATION_ERROR(-20018,'Serie de Comprobante Incorrecto:  '||nNumComPago);
    END IF;

END IF;


  UPDATE VTA_COMP_PAGO SET USU_MOD_COMP_PAGO = cUsuModCompPago_in, FEC_MOD_COMP_PAGO = SYSDATE,
         TIP_COMP_PAGO     = cTipCompPago_in,
          NUM_COMP_PAGO     = cNumCompPago_in          
    WHERE  COD_GRUPO_CIA     = cCodGrupoCia_in
    AND     COD_LOCAL         = cCodLocal_in
    AND     NUM_PED_VTA       = cNumPedVta_in
    AND     SEC_COMP_PAGO     = cSecCompPago_in;

  if nElectronico > 0 then 
  -- DUBILLUZ 14.10.2014
   UPDATE VTA_PEDIDO_VTA_CAB C
   SET   C.USU_MOD_PED_VTA_CAB = cUsuModCompPago_in,
         C.FEC_MOD_PED_VTA_CAB = SYSDATE,
         C.TIP_COMP_PAGO = (CASE
                           WHEN cTipCompPago_in = '05' THEN '01'
                           WHEN cTipCompPago_in = '06' THEN '02'
                             ELSE cTipCompPago_in
                           END)
    WHERE  COD_GRUPO_CIA     = cCodGrupoCia_in
    AND     COD_LOCAL         = cCodLocal_in
    AND     NUM_PED_VTA       = cNumPedVta_in;

    UPDATE VTA_COMP_PAGO SET USU_MOD_COMP_PAGO = cUsuModCompPago_in, FEC_MOD_COMP_PAGO = SYSDATE,
         TIP_COMP_PAGO = (CASE
                           WHEN cTipCompPago_in = '05' THEN '01'
                           WHEN cTipCompPago_in = '06' THEN '02'
                             ELSE cTipCompPago_in
                           END)                                    
    WHERE  COD_GRUPO_CIA     = cCodGrupoCia_in
    AND     COD_LOCAL         = cCodLocal_in
    AND     NUM_PED_VTA       = cNumPedVta_in
    AND     SEC_COMP_PAGO     = cSecCompPago_in;    
  end if;
  
  
    CAJ_ACT_KARDEX_COMPROBANTE(cCodGrupoCia_in,
                        cCodLocal_in,
                         cNumPedVta_in,
                   cSecCompPago_in,
                   cTipCompPago_in,
                   cNumCompPago_in,
                   cUsuModCompPago_in);
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in     IN CHAR,
                         cSecImprLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    SELECT IMPR_LOCAL.NUM_SERIE_LOCAL || 'Ã' ||
         IMPR_LOCAL.NUM_COMP
    FROM   VTA_IMPR_LOCAL IMPR_LOCAL
    WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND     IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
    AND     IMPR_LOCAL.SEC_IMPR_LOCAL = cSecImprLocal_in FOR UPDATE;
    RETURN curCaj;
  END;

  /* ********************************************************************************************** */

  PROCEDURE CAJ_ACTUALIZA_IMPR_NUM_COMP(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                    cSecImprLocal_in   IN CHAR,
                    cUsuModImprLocal_in IN CHAR)
  IS
  BEGIN
    UPDATE VTA_IMPR_LOCAL SET FEC_MOD_IMPR_LOCAL = SYSDATE, USU_MOD_IMPR_LOCAL = cUsuModImprLocal_in,
           NUM_COMP = TRIM(TO_CHAR((TO_NUMBER(NUM_COMP) + 1),'0000000'))
   WHERE COD_GRUPO_CIA      = cCodGrupoCia_in
   AND   COD_LOCAL          = cCodLocal_in
   AND   SEC_IMPR_LOCAL     = cSecImprLocal_in;
  END;

  /* ********************************************************************************************** */

  PROCEDURE CAJ_ACTUALIZA_ESTADO_PEDIDO(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                    cNumPedVta_in     IN CHAR,
                    cEstPedVta_in    IN CHAR,
                    cUsuModPedVtaCab_in IN CHAR)
  IS
    vCantComprobante INTEGER;
    vCantComprobanteImp INTEGER;
  BEGIN
    --ACTUALIZA CABECERA PEDIDO
    UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = cUsuModPedVtaCab_in, FEC_MOD_PED_VTA_CAB = SYSDATE
           /*EST_PED_VTA         = cEstPedVta_in*/
      WHERE COD_GRUPO_CIA       = cCodGrupoCia_in
      AND  COD_LOCAL           = cCodLocal_in
      AND  NUM_PED_VTA         = cNumPedVta_in;
      
    -- ACTUALIZA ESTADO DE COBRADO
    IF cEstPedVta_in = EST_PED_COBRADO THEN
      SELECT COUNT(1) 
      INTO   vCantComprobante
      FROM VTA_COMP_PAGO COMP
      WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   COMP.COD_LOCAL     = cCodLocal_in
      AND   COMP.NUM_PED_VTA   = cNumPedVta_in;
      
      SELECT COUNT(1) 
      INTO   vCantComprobanteImp
      FROM VTA_COMP_PAGO COMP
      WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   COMP.COD_LOCAL     = cCodLocal_in
      AND   COMP.NUM_PED_VTA   = cNumPedVta_in
      AND   COMP.FECH_IMP_COBRO IS NOT NULL;
      
      IF vCantComprobante = vCantComprobanteImp THEN
        UPDATE VTA_PEDIDO_VTA_CAB 
        SET    EST_PED_VTA         = cEstPedVta_in
        WHERE  COD_GRUPO_CIA       = cCodGrupoCia_in
        AND    COD_LOCAL           = cCodLocal_in
        AND    NUM_PED_VTA         = cNumPedVta_in;
      END IF;
      
    ELSE
      UPDATE VTA_PEDIDO_VTA_CAB 
      SET    EST_PED_VTA         = cEstPedVta_in
      WHERE  COD_GRUPO_CIA       = cCodGrupoCia_in
      AND    COD_LOCAL           = cCodLocal_in
      AND    NUM_PED_VTA         = cNumPedVta_in;
    END IF;
    --ERIOS 2.4.8 Si el pedido se cobra, graba respaldo
    IF cEstPedVta_in = EST_PED_COBRADO THEN
      PTOVENTA_REPLICANTE.REGISTRA_PEDIDO_RESPALDO(cCodGrupoCia_in,NULL,cCodLocal_in,cNumPedVta_in,cUsuModPedVtaCab_in);
    END IF;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_LISTA_CAB_PEDIDOS_ESTADO_S(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    SELECT 
    -- NVL(TIP_COMP.DESC_COMP,' ')                    
    -- KMONCADA 02.12.2014 TIPO POR CADA COMPROBANTE
      CASE
        WHEN NVL(COMP.TIP_COMP_PAGO,TIPO_COMPROBANTE_99) != TIPO_COMPROBANTE_99 THEN
          (SELECT 
            CASE 
              WHEN NVL(COMP.COD_TIP_PROC_PAGO,'0')='1' AND TIP_COMP.TIP_COMP IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN
                'BOLETA ELECTRONICA'
              WHEN NVL(COMP.COD_TIP_PROC_PAGO,'0')='1' AND TIP_COMP.TIP_COMP IN (COD_TIP_COMP_FACTURA) THEN
                'FACTURA ELECTRONICA'
              WHEN NVL(COMP.COD_TIP_PROC_PAGO,'0')='1' AND TIP_COMP.TIP_COMP = COD_TIP_COMP_NOTA_CRED THEN  
                'NOTA CRED.ELECT.'
              ELSE
                TIP_COMP.DESC_COMP
            END
           FROM  VTA_TIP_COMP TIP_COMP
           WHERE COMP.COD_GRUPO_CIA = TIP_COMP.COD_GRUPO_CIA
          AND    COMP.TIP_COMP_PAGO = TIP_COMP.TIP_COMP)
      ELSE
         (SELECT TIP_COMP.DESC_COMP
         FROM  VTA_TIP_COMP TIP_COMP
           WHERE VTA_CAB.COD_GRUPO_CIA = TIP_COMP.COD_GRUPO_CIA
          AND    VTA_CAB.TIP_COMP_PAGO = TIP_COMP.TIP_COMP )
      END                                                  || 'Ã' || -- 0
      --LTAVARA 25.09.2014 OBTIENE NUMERO ELECTRONICO
      NVL(COMP.NUM_COMP_PAGO_E,'0')                        || 'Ã' || -- 1
      NVL(VTA_CAB.NUM_PED_DIARIO,' ')                      || 'Ã' || -- 2
      NVL(VTA_CAB.NUM_PED_VTA,' ')                         || 'Ã' || -- 3
      TO_CHAR(VTA_CAB.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' || -- 4
      TO_CHAR(VTA_CAB.VAL_NETO_PED_VTA,'999,990.00')       || 'Ã' || -- 5
      NVL(VTA_CAB.RUC_CLI_PED_VTA,' ')                     || 'Ã' || -- 6
      NVL(
--         (SELECT MAX(VTA_DET.SEC_GRUPO_IMPR)
--           FROM   VTA_PEDIDO_VTA_DET VTA_DET
--          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
--          AND     COD_LOCAL = cCodLocal_in
--          AND     NUM_PED_VTA = VTA_CAB.NUM_PED_VTA)

            (select count(*)
            FROM   VTA_COMP_PAGO COMP_PAGO
            WHERE COMP_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
            AND   COMP_PAGO.COD_LOCAL = cCodLocal_in
            AND   NUM_PED_VTA = VTA_CAB.NUM_PED_VTA
            AND   FECH_IMP_COBRO IS NULL)
       ,0)                                                 || 'Ã' || -- 7
       NVL(VTA_CAB.SEC_MOV_CAJA,' ')                       || 'Ã' || -- 8
       NVL(VTA_CAB.TIP_COMP_PAGO,' ')                      || 'Ã' || -- 9
       VTA_CAB.VAL_TIP_CAMBIO_PED_VTA                      || 'Ã' || -- 10
       VTA_CAB.USU_CREA_PED_VTA_CAB                        || 'Ã' || -- 11 ' ' JMIRANDA 17/09/2009
       NVL(VTA_CAB.COD_CONVENIO,' ')                       || 'Ã' || -- 12 --RHERRERA: 11/04/2014 envia codigo de convenio
       --NVL(CONV.DES_CONVENIO,' ') || ' Ã ' || --CHUANES 24/04/2014
       -- KMONCADA 02.12.2014 
       CASE 
         WHEN NVL(VTA_CAB.COD_CONVENIO,'0')!='0' THEN
           (SELECT DES_CONVENIO FROM MAE_CONVENIO WHERE COD_CONVENIO=VTA_CAB.COD_CONVENIO)
         ELSE
           ' '
       END                                                 || 'Ã' || -- 13
       -- KMONCADA  07.07.2014 OBTIENE DATOS EN CASO DE VENTA EMPRESA
       NVL(VTA_CAB.NOM_CLI_PED_VTA,' ')                    || 'Ã' || -- 14
       NVL(VTA_CAB.DIR_CLI_PED_VTA,' ')                    || 'Ã' || -- 15
       NVL(VTA_CAB.RUC_CLI_PED_VTA,' ')                    || 'Ã' || -- 16
       NVL((SELECT V_EMP.NUM_OC
             FROM VTA_PEDIDO_VTA_CAB_EMP V_EMP
             WHERE V_EMP.COD_GRUPO_CIA=VTA_CAB.COD_GRUPO_CIA
             AND V_EMP.COD_LOCAL=VTA_CAB.COD_LOCAL
             AND V_EMP.NUM_PED_VTA=VTA_CAB.NUM_PED_VTA
            ),' ')                                         || 'Ã' || -- 17
       NVL(VTA_CAB.PUNTO_LLEGADA,' ' )                     || 'Ã' || -- 18
       NVL((SELECT 
              CASE
                WHEN V_EMP.COD_POLIZA IS NOT NULL AND LENGTH(V_EMP.COD_POLIZA)> 1 THEN 
                  'ATENCION BOTIQUIN '||V_EMP.NOMBRE_CLIENTE_POLIZA||' POLIZA N° '||V_EMP.COD_POLIZA||'.'
                ELSE ' ' 
              END
            FROM VTA_PEDIDO_VTA_CAB_EMP V_EMP
            WHERE V_EMP.COD_GRUPO_CIA=VTA_CAB.COD_GRUPO_CIA
            AND V_EMP.COD_LOCAL=VTA_CAB.COD_LOCAL
            AND V_EMP.NUM_PED_VTA=VTA_CAB.NUM_PED_VTA
            ),' ')                                         || 'Ã' || -- 19
       VTA_CAB.IND_DELIV_AUTOMATICO                        || 'Ã' || -- 20
       --CHUANES 08.09.2014 OBTIENE DATOS NECESARIOS PARA LA REIMPRESION DEL DOC. ELECTRONICO
       --inicio
       NVL(COMP.SEC_COMP_PAGO,' ')                        || 'Ã' || -- 21
       NVL(COMP.COD_TIP_PROC_PAGO,'0')                    || 'Ã' || -- 22
       NVL(COMP.TIP_CLIEN_CONVENIO,' ')                   || 'Ã' || -- 23
       NVL(COMP.NUM_COMP_PAGO,' ')                        || 'Ã' || -- 24
       --fin
        
       -- KMONCADA 02.12.2014 INDICADOR SI COMPROBANTE YA TIENE ASIGNADO NRO
       CASE 
         WHEN NVL(COMP.NUM_COMP_PAGO_E,'0')='0' THEN
           CASE 
             WHEN COMP.SEC_COMP_PAGO != COMP.NUM_COMP_PAGO THEN
               'S'
             ELSE
               'N'
           END
         ELSE
           CASE 
             WHEN NVL(COMP.NUM_COMP_PAGO_E,'0')!='0' THEN
               'S'
             ELSE
               'N'
           END
       END                                                          -- 25
    FROM   VTA_PEDIDO_VTA_CAB VTA_CAB,
            --VTA_TIP_COMP TIP_COMP ,
            --MAE_CONVENIO CONV,
            VTA_COMP_PAGO COMP
    WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    VTA_CAB.COD_LOCAL     = cCodLocal_in
    AND    VTA_CAB.COD_GRUPO_CIA=COMP.COD_GRUPO_CIA
    AND    VTA_CAB.COD_LOCAL=COMP.COD_LOCAL
    AND    VTA_CAB.NUM_PED_VTA=COMP.NUM_PED_VTA
    AND    VTA_CAB.EST_PED_VTA   = EST_PED_COB_NO_IMPR
    AND    COMP.FECH_IMP_COBRO IS NULL -- rherrera indicador impreso

--    AND    VTA_CAB.COD_GRUPO_CIA = TIP_COMP.COD_GRUPO_CIA
--    AND     VTA_CAB.TIP_COMP_PAGO = TIP_COMP.TIP_COMP
--    AND VTA_CAB.COD_CONVENIO=CONV.COD_CONVENIO(+)
      ;
    
    RETURN curCaj;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_LISTA_DET_PEDIDO_ESTADO_S(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                     cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    SELECT NVL(VTA_DET.SEC_COMP_PAGO,' ')          || 'Ã' ||
         NVL(VTA_DET.COD_PROD,' ')             || 'Ã' ||
          NVL(PROD.DESC_PROD,' ')             || 'Ã' ||
           NVL(VTA_DET.UNID_VTA,' ')           || 'Ã' ||
            TO_CHAR(VTA_DET.VAL_PREC_VTA,'999,990.000')   || 'Ã' ||
         TO_CHAR(VTA_DET.CANT_ATENDIDA,'9,990')     || 'Ã' ||
            TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')
    FROM   VTA_PEDIDO_VTA_DET VTA_DET,
         LGT_PROD_LOCAL PROD_LOCAL,
         LGT_PROD PROD
    WHERE  VTA_DET.COD_GRUPO_CIA    = cCodGrupoCia_in
    AND     VTA_DET.COD_LOCAL        = cCodLocal_in
    AND     VTA_DET.NUM_PED_VTA     = cNumPedVta_in
    AND     VTA_DET.COD_GRUPO_CIA   = PROD_LOCAL.COD_GRUPO_CIA
    AND     VTA_DET.COD_LOCAL     = PROD_LOCAL.COD_LOCAL
    AND     VTA_DET.COD_PROD     = PROD_LOCAL.COD_PROD
    AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
    AND     PROD_LOCAL.COD_PROD     = PROD.COD_PROD;
    RETURN curCaj;
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_INFO_CAJERO(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in    IN CHAR,
                   cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    SELECT NVL(MOV_CAJA.NUM_CAJA_PAGO,0)    || 'Ã' ||
         NVL(MOV_CAJA.NUM_TURNO_CAJA,0)   || 'Ã' ||
         NVL(USU_LOCAL.NOM_USU,' ')     || 'Ã' ||
         NVL(USU_LOCAL.APE_PAT,' ')
    FROM   CE_MOV_CAJA MOV_CAJA,
         PBL_USU_LOCAL USU_LOCAL
    WHERE  MOV_CAJA.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     MOV_CAJA.COD_LOCAL     = cCodLocal_in
    AND     MOV_CAJA.SEC_MOV_CAJA  = cSecMovCaja_in
    AND     MOV_CAJA.COD_GRUPO_CIA = USU_LOCAL.COD_GRUPO_CIA
    AND     MOV_CAJA.COD_LOCAL     = USU_LOCAL.COD_LOCAL
    AND     MOV_CAJA.SEC_USU_LOCAL = USU_LOCAL.SEC_USU_LOCAL;
    RETURN curCaj;
  END;


  /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_INFO_VENDEDOR(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR,
                     cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curRep FarmaCursor;
  BEGIN
    OPEN curRep FOR
     SELECT DISTINCT NVL(NOM_USU,' ') || 'Ã' ||
                 NVL(APE_PAT,' ')
    FROM PBL_USU_LOCAL UL,
       VTA_PEDIDO_VTA_DET DET
     WHERE DET.COD_GRUPO_CIA=cCodGrupoCia_in  AND
          DET.COD_LOCAL=cCodLocal_in        AND
          DET.NUM_PED_VTA=cNumPedVta_in    AND
          DET.COD_GRUPO_CIA=UL.COD_GRUPO_CIA AND
          DET.COD_LOCAL=UL.COD_LOCAL      AND
          DET.SEC_USU_LOCAL=UL.SEC_USU_LOCAL;

    RETURN curRep;
  END;


  /*****************************************************************************************************/
  FUNCTION CAJ_OBTIENE_VAL_ARQUEO_CONSULT(cCodGrupoCia_in IN CHAR,
                                 cCod_Local_in   IN CHAR,
                            cSecMovCaja_in  IN CHAR
                          )
  RETURN FarmaCursor
  IS
   curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
   SELECT NVL(CANT_BOL_EMI,0)                            || 'Ã' ||
          TO_CHAR(NVL(MON_BOL_EMI,0),'999,990.00')       || 'Ã' ||
          NVL(CANT_FACT_EMI,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_FACT_EMI,0),'999,990.00')      || 'Ã' ||
          NVL(CANT_GUIA_EMI,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_GUIA_EMI,0),'999,990.00')      || 'Ã' ||
          NVL(CANT_BOL_ANU,0)                            || 'Ã' ||
          TO_CHAR(NVL(MON_BOL_ANU,0),'999,990.00')       || 'Ã' ||
          NVL(CANT_FACT_ANU,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_FACT_ANU,0),'999,990.00')      || 'Ã' ||
          NVL(CANT_GUIA_ANU,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_GUIA_ANU,0),'999,990.00')      || 'Ã' ||
          NVL(CANT_BOL_TOT,0)                            || 'Ã' ||
          TO_CHAR(NVL(MON_BOL_TOT,0),'999,990.00')       || 'Ã' ||
          NVL(CANT_FACT_TOT,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_FACT_TOT,0),'999,990.00')      || 'Ã' ||
          NVL(CANT_GUIA_TOT,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_GUIA_TOT,0),'999,990.00')      || 'Ã' ||
          TO_CHAR(NVL(MON_TOT_GEN,0),'999,990.00')       || 'Ã' ||
          TO_CHAR(NVL(MON_TOT_ANU,0),'999,990.00')       || 'Ã' ||
          TO_CHAR(NVL(MON_TOT,0),'999,990.00')           || 'Ã' ||
          NVL(CANT_NC_BOLETAS,0)                         || 'Ã' ||
          TO_CHAR(NVL(MON_NC_BOLETAS,0)*-1,'999,990.00') || 'Ã' ||
          NVL(CANT_NC_FACT,0)                            || 'Ã' ||
          TO_CHAR(NVL(MON_NC_FACT,0)*-1,'999,990.00')    || 'Ã' ||
          TO_CHAR(NVL(MON_TOT_NC,0)*-1,'999,990.00')     || 'Ã' ||
          NVL(CANT_TICK_EMI,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_TICK_EMI,0),'999,990.00')      || 'Ã' ||
          NVL(CANT_TICK_ANU,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_TICK_ANU,0),'999,990.00')      || 'Ã' ||
          NVL(CANT_NC_TICKETS,0)                         || 'Ã' ||
          TO_CHAR(NVL(MON_NC_TICKETS,0)*-1,'999,990.00') || 'Ã' ||
          NVL(CANT_TICK_TOT,0)                           || 'Ã' ||
          TO_CHAR(NVL(MON_TICK_TOT,0),'999,990.00')      || 'Ã' ||
          NVL(CANT_TICK_FAC_EMI,0)                       || 'Ã' ||
          TO_CHAR(NVL(MON_TICK_FAC_EMI,0),'999,990.00')  || 'Ã' ||
          NVL(CANT_TICK_FAC_ANUL,0)                      || 'Ã' ||
          TO_CHAR(NVL(MON_TICK_FACK_ANUL,0),'999,990.00')|| 'Ã' ||
          NVL(CANT_NC_TICKETS_FAC,0)                     || 'Ã' ||
          TO_CHAR(NVL(MON_NC_TICKETS_FAC,0),'999,990.00')|| 'Ã' ||
          NVL(CANT_TICK_FAC_TOT,0)                       || 'Ã' ||
          TO_CHAR(NVL(MON_TICK_FAC_TOT,0),'999,990.00')
   FROM CE_MOV_CAJA
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
         COD_LOCAL     = cCod_Local_in   AND
         SEC_MOV_CAJA  = cSecMovCaja_in;
   RETURN curDet;
  END;

 /**********************************************************************************************************/

  FUNCTION CAJ_DETALLES_FORM_PAGO_CONSULT( cCodGrupoCia_in IN CHAR,
                               cCod_Local_in   IN CHAR,
                          cSecMovCaja_in  IN CHAR
                        )
  RETURN FarmaCursor
   IS
   curDet FarmaCursor;
  BEGIN

    OPEN curDet FOR
    SELECT MCFP.COD_FORMA_PAGO          || 'Ã' ||
     FP.DESC_CORTA_FORMA_PAGO          || 'Ã' ||
     (SELECT INITCAP(DESC_CORTA)
          FROM PBL_TAB_GRAL
        WHERE COD_TAB_GRAL='MONEDA' AND
            LLAVE_TAB_GRAL=TIP_MONEDA) || 'Ã' ||
      TO_CHAR(IM_PAGO,'999,990.00')        || 'Ã' ||
    TO_CHAR(IM_TOTAL,'999,990.00')        || 'Ã' ||
    TIP_MONEDA
FROM  CE_MOV_CAJA_FORMA_PAGO_SIST MCFP,
    VTA_FORMA_PAGO FP
WHERE MCFP.COD_GRUPO_CIA  = cCodGrupoCia_in   AND
    MCFP.COD_LOCAL      = cCod_Local_in    AND
    MCFP.SEC_MOV_CAJA   = cSecMovCaja_in    AND
    MCFP.COD_GRUPO_CIA  = FP.COD_GRUPO_CIA  AND
    MCFP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO;
   RETURN curDet;
 END;

  /* ********************************************************************************************** */

  PROCEDURE CAJ_ACTUALIZA_CLI_PEDIDO(cCodGrupoCia_in    IN CHAR,
                           cCodLocal_in       IN CHAR,
                   cNumPedVta_in     IN CHAR,
                   cCodCliLocal_in    IN CHAR,
                   cNomCliPed_in       IN CHAR,
                   cDirCliLocal_in    IN CHAR,
                   cRucCliPed_in       IN CHAR,
                   cUsuModPedVtaCab_in IN CHAR)
  IS
  BEGIN
    UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = cUsuModPedVtaCab_in, FEC_MOD_PED_VTA_CAB = SYSDATE,
           COD_CLI_LOCAL   = cCodCliLocal_in,
           NOM_CLI_PED_VTA = cNomCliPed_in,
           DIR_CLI_PED_VTA = cDirCliLocal_in,
           RUC_CLI_PED_VTA = cRucCliPed_in
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND  COD_LOCAL = cCodLocal_in
      AND  NUM_PED_VTA = cNumPedVta_in;
  END;
  /**************************************************************************************************/

  FUNCTION CAJ_LISTA_PEDIDOS_COMPROB_CAB(cCodGrupoCia_in IN CHAR,
                                cCod_Local_in   IN CHAR,
                           cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor
  IS
      curDet FarmaCursor;
    vNumCajaPago NUMBER(4);
      v_nTurCaja    NUMBER;
    vSecUsuLocal CHAR(3);
  BEGIN

  SELECT NUM_CAJA_PAGO INTO vNumCajaPago
  FROM CE_MOV_CAJA
  WHERE
     COD_GRUPO_CIA = cCodGrupoCia_in AND
     COD_LOCAL     = cCod_Local_in   AND
     SEC_MOV_CAJA  = cSecMovCaja_in;
  SELECT SEC_USU_LOCAL INTO vSecUsuLocal
  FROM CE_MOV_CAJA
  WHERE
     COD_GRUPO_CIA = cCodGrupoCia_in AND
     COD_LOCAL     = cCod_Local_in   AND
     SEC_MOV_CAJA  = cSecMovCaja_in;
  SELECT NUM_TURNO_CAJA INTO v_nTurCaja
  FROM CE_MOV_CAJA
  WHERE
     COD_GRUPO_CIA = cCodGrupoCia_in AND
     COD_LOCAL     = cCod_Local_in   AND
     SEC_MOV_CAJA  = cSecMovCaja_in;
  OPEN curDet FOR
     SELECT 'USU' || 'Ã' ||
         APE_PAT || ' ' || APE_MAT || ', ' || NOM_USU
     FROM PBL_USU_LOCAL
     WHERE   COD_GRUPO_CIA  = cCodGrupoCia_in AND
         COD_LOCAL      = cCod_Local_in   AND
         SEC_USU_LOCAL  = vSecUsuLocal
     UNION
     SELECT 'CAJ' || 'Ã' ||
         TO_CHAR(vNumCajaPago)
     FROM dual
     UNION
     SELECT 'TUR' || 'Ã' ||
         TO_CHAR(v_nTurCaja)
     FROM dual;
   RETURN curDet;
  END;

  /* ********************************************************************************************** */

  --01/10/2007  DUBILLUZ  Modificacion
  FUNCTION CAJ_VERIFICA_PED_PEND_ANUL(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                    nMinutos_in    IN NUMBER)
  RETURN NUMBER
  IS
    v_nCantPedPend NUMBER;
  BEGIN
	--ERIOS 2.4.7 Se agrega limite (JLUNA)
    SELECT COUNT(VTA_CAB.NUM_PED_VTA)
  INTO   v_nCantPedPend
  FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
  WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
  AND     VTA_CAB.COD_LOCAL = cCodLocal_in
  AND     VTA_CAB.FEC_PED_VTA < (SYSDATE - (nMinutos_in/24/60))
  AND     VTA_CAB.FEC_PED_VTA >= trunc(SYSDATE-3)
  ---
  AND    VTA_CAB.IND_DELIV_AUTOMATICO = INDICADOR_NO
  ---
  AND     VTA_CAB.EST_PED_VTA = EST_PED_PENDIENTE;
  RETURN v_nCantPedPend;
  END;
  /* ********************************************************************************************** */

  PROCEDURE CAJ_ANULA_PED_PEND_MASIVO(cCodGrupoCia_in     IN CHAR,
                            cCodLocal_in        IN CHAR,
                    nMinutos_in        IN NUMBER,
                    cUsuModPedVtaCab_in IN CHAR)
  IS
  CURSOR pedidos IS
          SELECT VTA_CAB.NUM_PED_VTA NUMERO
        FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
        WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     VTA_CAB.COD_LOCAL = cCodLocal_in
        --27/09/2007 DUBILLUZ  MODIFICADO
        AND     VTA_CAB.IND_DELIV_AUTOMATICO <> 'S'
        AND     VTA_CAB.FEC_PED_VTA < (SYSDATE - (nMinutos_in/24/60))
        AND     VTA_CAB.EST_PED_VTA = EST_PED_PENDIENTE;
  nIsPedVirtual number;
  BEGIN
    FOR pedidos_rec IN pedidos
    LOOP
        select count(1)
        into  nIsPedVirtual
        from  vta_pedido_vta_det d,
              lgt_prod_virtual p
        where d.cod_grupo_cia = cCodGrupoCia_in
        and   d.cod_local     = cCodLocal_in
        and   d.num_ped_vta   = pedidos_rec.NUMERO
        and   d.cod_grupo_cia = p.cod_grupo_cia
        and   d.cod_prod      = p.cod_prod
        and   p.tip_prod_virtual = 'R';

        if nIsPedVirtual = 0 then
              PTOVENTA_CAJ_ANUL.CAJ_ANULAR_PEDIDO_PENDIENTE(cCodGrupoCia_in,
                                      cCodLocal_in,
                              pedidos_rec.NUMERO,
                              cUsuModPedVtaCab_in);
        end if;
    END LOOP;
  END;

  FUNCTION CAJ_VERIFICA_IP_VALIDA(cCodGrupoCia_in IN CHAR,
                         cCod_Local_in   IN CHAR,
                    cDirIP        IN CHAR)
  RETURN NUMBER
  IS
    vnRpta NUMBER;
  vnCant NUMBER;
  BEGIN
    SELECT COUNT(*) INTO  vnCant
  FROM PBL_LOCAL L
  WHERE L.COD_GRUPO_CIA=cCodGrupoCia_in AND
      L.COD_LOCAL=cCod_Local_in      AND
      cDirIP IN (L.IP_CONFIG_COMP_1,L.IP_CONFIG_COMP_2);
  IF vnCant=0 THEN
     vnRpta:=0;
  ELSE
     vnRpta:=1;
  END IF;
    RETURN vnRpta;
  END;
  FUNCTION CAJ_VERIFICA_NUM_COMP(cCodGrupoCia_in IN CHAR,
                      cCodLocal_in IN CHAR,
                 cSecImpLocal_in  IN CHAR,
                 nCantComprobantes_in IN NUMBER,
                 cNumPedVta_in IN CHAR DEFAULT NULL)
  RETURN CHAR
  IS
    v_cTipComp  CHAR(2);
  v_cNumCom   CHAR(10);
  v_nContador NUMBER;
  v_nCount    NUMBER;
  BEGIN
    SELECT IMPR_LOCAL.TIP_COMP,
          IMPR_LOCAL.NUM_SERIE_LOCAL ||
       IMPR_LOCAL.NUM_COMP
    INTO    v_cTipComp,
       v_cNumCom
    FROM   VTA_IMPR_LOCAL IMPR_LOCAL
    WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND   IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
    AND   IMPR_LOCAL.SEC_IMPR_LOCAL = cSecImpLocal_in FOR UPDATE;
    v_nContador := 0;
    LOOP
        SELECT COUNT(*)
      INTO    v_nCount
      FROM   VTA_COMP_PAGO COMP_PAGO
      WHERE   COMP_PAGO.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   COMP_PAGO.COD_LOCAL = cCodLocal_in
      -- KMONCADA 2015.03.18 NO CONSIDERA EL NUMERO DE CP DEL MISMO PEDIDO
      AND   COMP_PAGO.NUM_PED_VTA <> cNumPedVta_in
      AND   COMP_PAGO.TIP_COMP_PAGO = v_cTipComp
      -- descomentar para reimpresión de tickets JMIRANDA
      /* //REIMPRESION DE TICKET
      and  (COMP_PAGO.COD_GRUPO_CIA,COMP_PAGO.COD_LOCAL,COMP_PAGO.NUM_PED_VTA)
           not in
           (
             select t.cod_grupo_cia,t.cod_local,t.num_ped_vta
             from   tmp_reimpresion_ticket t
           )
      */
      --
      AND   COMP_PAGO.NUM_COMP_PAGO = TRIM(TO_CHAR(v_cNumCom + v_nContador,'0000000000'));
      --DBMS_OUTPUT.PUT_LINE('v_cNumCom + v_nContador '||TRIM(TO_CHAR(v_cNumCom + v_nContador,'0000000000')));
      --DBMS_OUTPUT.PUT_LINE('v_nCount '||v_nCount);
      IF v_nCount > 0 THEN
         RETURN 'FALSE';
      END IF;
      v_nContador := v_nContador + 1 ;
      EXIT WHEN v_nContador = nCantComprobantes_in;
    END LOOP;
    RETURN 'TRUE';
  END;
  /* ********************************************************************************************** */

  PROCEDURE CE_INVALIDA_APER_CAJ(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in    IN CHAR)
  IS
  v_AperturaCaja ce_cierre_dia_venta%ROWTYPE;
  BEGIN
       SELECT * INTO v_AperturaCaja
       FROM   Ce_Cierre_Dia_Venta CD
       WHERE  cd.cod_grupo_cia = cCodGrupoCia_in
       AND    cd.cod_local = cCodLocal_in
       --AND    cd.Ind_Vb_Cierre_Dia = INDICADOR_SI
       AND    to_char(cd.fec_cierre_dia_vta,'dd/MM/yyyy') = TO_CHAR(SYSDATE,'dd/MM/yyyy');
     --EXCEPTION
     --   WHEN too_many_rows THEN
     RAISE_APPLICATION_ERROR(-20020,'No puede abrir una caja cuando existe una fecha de cierre de dia de Venta');
       EXCEPTION
        WHEN no_data_found THEN
             dbms_output.put_line('exception no encontro');
  END;

  /* ********************************************************************************************** */

  PROCEDURE CAJ_ACT_KARDEX_COMPROBANTE(cCodGrupoCia_in     IN CHAR,
                              cCodLocal_in        IN CHAR,
                        cNumPedVta_in       IN CHAR,
                     cSecCompPago_in     IN CHAR,
                     cTipCompPago_in     IN CHAR,
                     cNumCompPago_in     IN CHAR,
                     cUsuModKardex_in   IN CHAR)
  IS
  CURSOR productos IS
           SELECT VTA_DET.COD_PROD
        FROM   VTA_PEDIDO_VTA_DET VTA_DET
        WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     VTA_DET.COD_LOCAL = cCodLocal_in
        AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
        AND     VTA_DET.SEC_COMP_PAGO = cSecCompPago_in;
  BEGIN
      FOR productos_rec IN productos
    LOOP
        UPDATE LGT_KARDEX SET FEC_MOD_KARDEX = SYSDATE, USU_MOD_KARDEX = cUsuModKardex_in,
               TIP_COMP_PAGO = cTipCompPago_in,
                NUM_COMP_PAGO = cNumCompPago_in
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND   COD_LOCAL = cCodLocal_in
         AND   NUM_TIP_DOC = cNumPedVta_in
         AND   COD_PROD = productos_rec.COD_PROD;
    END LOOP;
  END;

  FUNCTION CAJ_PEDIDO_DEL_CAJ(cCodGrupoCia_in IN CHAR,
                                cCod_Local_in   IN CHAR,
                               cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor
  IS
   curRep FarmaCursor;
  BEGIN
    OPEN curRep FOR

    SELECT 'N' || 'Ã' ||
            'D' || 'Ã' ||
            COUNT(VP.SEC_COMP_PAGO)|| 'Ã' ||
            TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),'999,990.00'))
          FROM
             VTA_COMP_PAGO VP ,
             VTA_PEDIDO_VTA_CAB VPC
          WHERE  VP.COD_GRUPO_CIA = Ccodgrupocia_In
        AND     VP.COD_LOCAL  = ccod_local_in
        AND    VPC.SEC_MOV_CAJA  = csecmovcaja_in
        AND     vpc.EST_PED_VTA    = 'C'
        AND     vpc.tip_ped_vta = '02'
        AND     vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
        AND     vpc.cod_local = vp.cod_local
        AND     VP.NUM_PED_VTA = VPC.NUM_PED_VTA
        GROUP BY 'N','D'
        UNION
        SELECT 'S'|| 'Ã' ||
                'D' || 'Ã' ||
                 COUNT(VP.SEC_COMP_PAGO)|| 'Ã' ||
                 TRIM(TO_CHAR(SUM(VP.VAL_NETO_COMP_PAGO + VAL_REDONDEO_COMP_PAGO),'999,990.00'))
          FROM
             VTA_COMP_PAGO VP ,
             VTA_PEDIDO_VTA_CAB VPC
          WHERE  VP.COD_GRUPO_CIA = ccodgrupocia_in
        AND     VP.COD_LOCAL  = ccod_local_in
        AND    VPC.SEC_MOV_CAJA  = csecmovcaja_in
        AND     vpc.tip_ped_vta = '02'
        AND     vpc.EST_PED_VTA    = 'C'
        AND     VP.IND_COMP_ANUL  = 'S'
        AND     vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
        AND     vpc.cod_local = vp.cod_local
        AND    VPC.NUM_PED_VTA = VP.NUM_PEDIDO_ANUL
        GROUP BY 'S','D';

     RETURN curRep;
  END;

  FUNCTION  CAJ_VALOR_MAX_CONFIG_COMP(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                      cTipoDocumento_in IN CHAR)
  RETURN CHAR
  IS
  v_valormax CHAR(7);
  BEGIN
  IF ctipodocumento_in = COD_TIP_COMP_BOLETA THEN
        SELECT MAX(substr(c.num_comp_pago,4,10))+l.cant_max_config INTO v_valormax
        FROM vta_comp_pago c,
             pbl_local l
        WHERE c.cod_grupo_cia = ccodgrupocia_in
        AND   c.cod_local = ccodlocal_in
        AND   c.tip_comp_pago = COD_TIP_COMP_BOLETA
        AND   c.cod_local = l.cod_local
        GROUP BY l.cant_max_config ;
   ELSIF ctipodocumento_in = COD_TIP_COMP_FACTURA THEN
        SELECT MAX(substr(c.num_comp_pago,4,10))+l.cant_max_config INTO v_valormax
        FROM vta_comp_pago c,
             pbl_local l
        WHERE c.cod_grupo_cia = ccodgrupocia_in
        AND   c.cod_local = ccodlocal_in
        AND   c.tip_comp_pago = COD_TIP_COMP_FACTURA
        AND   c.cod_local = l.cod_local
        GROUP BY l.cant_max_config ;
   ELSIF ctipodocumento_in = COD_TIP_COMP_GUIA THEN
         SELECT MAX(substr(r.num_guia_rem,4,10)) + l.cant_max_config INTO v_valormax
         FROM lgt_nota_es_cab c ,
              lgt_guia_rem r,
              pbl_local l
         WHERE c.tip_nota_es = '02'
         AND   c.cod_grupo_cia = ccodgrupocia_in
         AND   c.cod_local = ccodlocal_in
         AND   c.cod_grupo_cia = r.cod_grupo_cia
         AND   c.cod_local = r.cod_local
         --Se añadio el indicador de impresion
         --17/12/2007  dubilluz  modificacion
         AND   r.ind_guia_impresa = INDICADOR_SI
         AND   c.num_nota_es = r.num_nota_es
         AND   c.cod_local = l.cod_local
         GROUP BY l.cant_max_config;
    END IF;
    RETURN v_valormax;
  END;

  /*****************************/

  FUNCTION CAJ_VALIDA_FORMA_PAGO_CUPON(cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                       cNumPedVta_in    IN CHAR,
                                       cCodFormaPago_in IN CHAR,
                                       nCantCupon_in    IN NUMBER)
    RETURN NUMBER
  IS
    v_nMontoCupon   NUMBER;
    v_nCantcupon   NUMBER;
    v_nCantHabil   NUMBER;
    v_nCantDetalle NUMBER;
    i              NUMBER;
  CURSOR prodHabilesCupon IS
          SELECT PROM.COD_PROD,
                PROM.CANT_MAX_PROD_DSCTO CANTIDAD,
                TO_CHAR((PROD_LOC.VAL_PREC_VTA * (PROM.VAL_DSCTO_PROM/100)),'999,990.00') MONTO_DSCTO
         FROM   VTA_PROMOCION_PROD PROM,
                LGT_PROD_LOCAL PROD_LOC
         WHERE  PROM.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    PROM.COD_LOCAL = cCodLocal_in
         AND    PROM.COD_FORMA_PAGO = cCodFormaPago_in
         AND    PROM.COD_PROD IN (SELECT VTA_DET.COD_PROD
                                  FROM   VTA_PEDIDO_VTA_DET VTA_DET
                                  WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND     VTA_DET.COD_LOCAL = cCodLocal_in
                                  AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
                                  GROUP BY VTA_DET.COD_PROD)
         AND    PROM.COD_GRUPO_CIA = PROD_LOC.COD_GRUPO_CIA
         AND    PROM.COD_LOCAL = PROD_LOC.COD_LOCAL
         AND    PROM.COD_PROD = PROD_LOC.COD_PROD
         ORDER BY 3 DESC;

  CURSOR detalleProducto(p_codProd CHAR) IS
         SELECT VTA_DET.COD_PROD PRODUCTO,
               SUM(VTA_DET.CANT_ATENDIDA) CANTIDAD
        FROM   VTA_PEDIDO_VTA_DET VTA_DET
        WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     VTA_DET.COD_LOCAL = cCodLocal_in
        AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
        AND     VTA_DET.COD_PROD = p_codProd
        GROUP BY VTA_DET.COD_PROD;

  BEGIN
      v_nMontoCupon  := 0.00;
      v_nCantcupon  := nCantCupon_in;
      i := 0;
      FOR prodHabilesCupon_rec IN prodHabilesCupon
      LOOP
          v_nCantHabil := prodHabilesCupon_rec.CANTIDAD;
          v_nCantDetalle := 0;
          EXIT WHEN v_nCantcupon = 0;
          FOR detalleProducto_rec IN detalleProducto(prodHabilesCupon_rec.COD_PROD)
          LOOP
              v_nCantDetalle := detalleProducto_rec.CANTIDAD;
              LOOP
                  IF( (i = v_nCantDetalle) OR (v_nCantcupon = 0) ) THEN
                     EXIT;
                  END IF;
                  v_nMontoCupon := v_nMontoCupon + TO_NUMBER(prodHabilesCupon_rec.MONTO_DSCTO,'999,990.00');
                  i := i + 1;
                  /*
                  dbms_output.put_line('PRODUCTO: ' || prodHabilesCupon_rec.COD_PROD);
                  dbms_output.put_line('v_nMontoCupon: ' || v_nMontoCupon);
                  dbms_output.put_line('i: ' || i);
                  dbms_output.put_line('v_nCantHabil: ' || v_nCantHabil);
                  dbms_output.put_line('v_nCantDetalle: ' || v_nCantDetalle);
                  dbms_output.put_line('v_nCantcupon: ' || v_nCantcupon);
                  dbms_output.put_line('************');
                  */
                  IF(MOD(i,v_nCantHabil) = 0) THEN
                     v_nCantcupon := v_nCantcupon - 1;
                     v_nCantDetalle := v_nCantDetalle - i;
                     i := 0;
                  END IF;
              END LOOP;
          END LOOP;
      END LOOP;
    RETURN v_nMontoCupon;
  END;

  FUNCTION CAJ_SECUENCIA_IMPR_INSTI(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                    cNumCajaPago_in IN NUMBER,
                                    cTipComp_in     IN CHAR)
    RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
       OPEN curCaj FOR
            SELECT IMPR_LOCAL.SEC_IMPR_LOCAL FAC_SEC
            FROM   VTA_IMPR_LOCAL IMPR_LOCAL,
                   VTA_CAJA_IMPR CAJA_IMPR
            WHERE  CAJA_IMPR.COD_GRUPO_CIA = cCodGrupoCia_in
            AND     CAJA_IMPR.COD_LOCAL = cCodLocal_in
            AND     CAJA_IMPR.NUM_CAJA_PAGO = cNumCajaPago_in
            --AND    IMPR_LOCAL.NUM_SERIE_LOCAL = C_C_SERIE_VTA_INSTITUCIONAL + TO_NUMBER(cCodLocal_in)
            AND     IMPR_LOCAL.COD_GRUPO_CIA = CAJA_IMPR.COD_GRUPO_CIA
            AND     IMPR_LOCAL.COD_LOCAL = CAJA_IMPR.COD_LOCAL
            AND     IMPR_LOCAL.SEC_IMPR_LOCAL = CAJA_IMPR.SEC_IMPR_LOCAL
            AND     IMPR_LOCAL.TIP_COMP = cTipComp_in;
       RETURN curCaj;
  END;

FUNCTION  CAJ_VERIFICA_TOTAL_PED_FOR_PAG(cCodGrupoCia_in IN CHAR,
          cCodLocal_in    IN CHAR,
          cNumPedVta_in   IN CHAR,
                                           cDescDetalleForPago_in IN CHAR DEFAULT ' ')
  RETURN CHAR
  IS
    v_nTotalCab NUMBER;
    v_nTotalFP  NUMBER;
    mesg_body   VARCHAR2(4000);
  CURSOR totales IS
         SELECT DISTINCT F.NUM_PED_VTA,
                SEC_MOV_CAJA,
                TO_CHAR(F.VAL_NETO_PED_VTA,'999,990.00') TOTAL_CAB,
                TO_CHAR(V1.TOTAL_FP,'999,990.00') TOTAL_FP
         FROM   VTA_PEDIDO_VTA_CAB F,
                VTA_FORMA_PAGO_PEDIDO P,
                (SELECT SUM(IM_TOTAL_PAGO - VAL_VUELTO) TOTAL_FP
                 FROM   VTA_FORMA_PAGO_PEDIDO
                 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    COD_LOCAL = cCodLocal_in
                 AND    NUM_PED_VTA = cNumPedVta_in) V1
         WHERE  F.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    F.COD_LOCAL = cCodLocal_in
         AND    F.NUM_PED_VTA = cNumPedVta_in
         AND    F.COD_LOCAL = P.COD_LOCAL
         AND    F.NUM_PED_VTA = P.NUM_PED_VTA
         AND    F.VAL_NETO_PED_VTA <> V1.TOTAL_FP;
  BEGIN
     FOR totales_K IN totales
    LOOP
        mesg_body := 'ERROR AL COBRAR PEDIDO No ' || cNumPedVta_in || '<BR>El total de la cabecera es ' || totales_K.TOTAL_CAB || ' y el total de formas de pago es ' || totales_K.TOTAL_FP || '.<BR>' || cDescDetalleForPago_in;
        FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                   cCodLocal_in,
                                   'joliva@mifarma.com.pe',--'lmesia@mifarma.com.pe;;joliva@mifarma.com.pe',
                                   'ERROR AL COBRAR PEDIDO - DIFERENCIAS EN TOTALES - LOCAL ',
                                   'ERROR',
                                   mesg_body,
                                   '');
        RETURN 'ERROR';--NUEVO
        EXIT;
    END LOOP;
    RETURN 'EXITO';--NUEVO
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_VERIFICA_PROD_VIRTUALES(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                       cNumPedVta_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nCant    NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nCant
       FROM   LGT_PROD_VIRTUAL VIR
       WHERE  VIR.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    VIR.TIP_PROD_VIRTUAL IN ('T','R') -- kmoncada
       AND    VIR.COD_PROD IN (SELECT COD_PROD
                               FROM   VTA_PEDIDO_VTA_DET DET
                               WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                               AND    DET.COD_LOCAL = cCodLocal_in
                               AND    DET.NUM_PED_VTA = cNumPedVta_in
                               );
    RETURN v_nCant;
  END;

  /*******************************************************/

  FUNCTION CAJ_OBT_INFO_PROD_VIRTUAL(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                     cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
       OPEN curCaj FOR
            SELECT DET.COD_PROD || 'Ã' ||
                   VIR.TIP_PROD_VIRTUAL || 'Ã' ||
                   TO_CHAR(DET.VAL_PREC_TOTAL,'999,990.00') || 'Ã' ||
                   NVL(DET.DESC_NUM_TEL_REC,' ') || 'Ã' ||
                   NVL(VIR.COD_PROV_TEL,' ') || 'Ã' ||
                   NVL(DET.VAL_NUM_TRACE,' ') || 'Ã' ||
                   NVL(DET.VAL_COD_APROBACION,' ') || 'Ã' ||
                   NVL(VIR.COD_PROD_PROV,'00000000') || 'Ã' ||
                   NVL(DET.FECHA_TX,' ') || 'Ã' ||
                   NVL(DET.HORA_TX,' ') || 'Ã' ||
                   VIR.TIP_RCD || 'Ã' || --ASOSA - 14/07/2014
                   VIR.COD_TIPO_PROD --ASOSA - 14/07/2014
            FROM   LGT_PROD_VIRTUAL VIR,
                   VTA_PEDIDO_VTA_DET DET
            WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    DET.COD_LOCAL = cCodLocal_in
            AND    DET.NUM_PED_VTA = cNumPedVta_in
            AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA
            AND    DET.COD_PROD = VIR.COD_PROD;
       RETURN curCaj;
  END;

  /************************************************************/

  FUNCTION OBTENER_NUMERACION_TRACE(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in    IN CHAR)
    RETURN NUMBER
  IS PRAGMA AUTONOMOUS_TRANSACTION;
    v_nNumero  NUMBER;
  BEGIN
       SELECT NUM.VAL_NUMERA
      INTO   v_nNumero
      FROM   PBL_NUMERA NUM
      WHERE  NUM.COD_GRUPO_CIA = cCodGrupoCia_in
      AND     NUM.COD_LOCAL = cCodLocal_in
      AND    NUM.COD_NUMERA = C_C_COD_NUMERA_TRACE FOR UPDATE;
      IF ( v_nNumero IS NULL OR v_nNumero=0 ) THEN
        v_nNumero := 1;
      END IF;
      FARMA_UTILITY.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                 cCodLocal_in,
                                                 C_C_COD_NUMERA_TRACE,
                                                 'PCK_NUM_TRACE');
      --27/09/2007 ERIOS Para Brightstar, el trace se compone por XXXNNN
      --                 donde XXX es el codigo de local y
      --                 NNN secuencial
      --IF ( v_nNumero = 9999 ) THEN
      IF ( v_nNumero = 999 ) THEN
        FARMA_UTILITY.INICIALIZA_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                                   cCodLocal_in,
                                                   C_C_COD_NUMERA_TRACE,
                                                   'PCK_NUM_TRACE');
      END IF;
      COMMIT;
  RETURN v_nNumero;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
           ROLLBACK;
           RETURN 0;
  END;
  /************************************************************/

  PROCEDURE CAJ_ACT_INFO_DET_PED_VIRTUAL(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumPedVta_in   IN CHAR,
                                         cCodProd_in     IN CHAR,
                                         cNumTrace_in     IN CHAR,
                                         cCodAproba_in   IN CHAR,
                                         cNumTarjVir_in   IN CHAR,
                                         cNumPin_in      IN CHAR,
                                         cUsuMod_in      IN CHAR,
                                         cFechaTX_in IN CHAR,
                                         cHoraTX_in IN CHAR,
                                         cDatosImprimir_in IN VARCHAR2)
  IS
  BEGIN
       UPDATE VTA_PEDIDO_VTA_DET
       SET    USU_MOD_PED_VTA_DET = cUsuMod_in, FEC_MOD_PED_VTA_DET = SYSDATE,
              VAL_NUM_TRACE = cNumTrace_in, VAL_COD_APROBACION = cCodAproba_in,
              DESC_NUM_TARJ_VIRTUAL = cNumTarjVir_in, VAL_NUM_PIN = cNumPin_in,
              FECHA_TX = cFechaTX_in,
              HORA_TX = cHoraTX_in,
              DATOS_IMP_VIRTUAL = cDatosImprimir_in
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    NUM_PED_VTA = cNumPedVta_in
       AND    COD_PROD = cCodProd_in;
  END ;

  /*******************************************************/

  FUNCTION CAJ_OBTIENE_VALORES_RECARGA
    RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
       OPEN curCaj FOR
            SELECT GRAL.DESC_CORTA
            FROM   PBL_TAB_GRAL GRAL
            WHERE  GRAL.COD_APL = 'PTO_VENTA'
            AND     GRAL.COD_TAB_GRAL IN ('CONST_NAVSAT','CONST_NAVSAT_RECARGA')
            ORDER BY GRAL.COD_TAB_GRAL, GRAL.LLAVE_TAB_GRAL;
       RETURN curCaj;
  END;

  /*******************************************************/

  FUNCTION CAJ_OBTIENE_VALORES_TARJETA
    RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
       OPEN curCaj FOR
            SELECT GRAL.DESC_CORTA
            FROM   PBL_TAB_GRAL GRAL
            WHERE  GRAL.COD_APL = 'PTO_VENTA'
            AND     GRAL.COD_TAB_GRAL IN ('CONST_NAVSAT','CONST_NAVSAT_TARJETA')
            ORDER BY GRAL.COD_TAB_GRAL, GRAL.LLAVE_TAB_GRAL;
       RETURN curCaj;
  END;

  /************************************************************/

  PROCEDURE CAJ_ACT_INFO_DET_PED_VIR_ANUL(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cNumPedVtaOrigen_in IN CHAR,
                                          cNumTrace_in    IN CHAR,
                                          cCodAproba_in   IN CHAR,
                                          cUsuMod_in      IN CHAR)
  IS
  BEGIN
       UPDATE VTA_PEDIDO_VTA_DET
       SET    USU_MOD_PED_VTA_DET = cUsuMod_in, FEC_MOD_PED_VTA_DET = SYSDATE,
              VAL_NUM_TRACE = cNumTrace_in, VAL_COD_APROBACION = cCodAproba_in
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    NUM_PED_VTA IN (SELECT CAB.NUM_PED_VTA
                              FROM   VTA_PEDIDO_VTA_CAB CAB
                              WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND    CAB.COD_LOCAL = cCodLocal_in
                              AND    CAB.NUM_PED_VTA_ORIGEN = cNumPedVtaOrigen_in);
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_LISTA_CAB_PED_VIRTUALES(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in   IN CHAR,
                                       cSecMovCaja_in  IN CHAR)
    RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
       OPEN curDet FOR
            SELECT VC.NUM_PED_VTA || 'Ã' ||
                   NVL(TP.DESC_COMP,' ') || 'Ã' ||
         --RH: 21.10.2014 FAC-ELECTRONICA
      FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                    --CP.NUM_COMP_PAGO
                    || 'Ã' ||
                    TO_CHAR(VC.FEC_PED_VTA,'dd/MM/yyyy HH24:mi:ss') || 'Ã' ||
                   TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,990.00') || 'Ã' ||
                   DECODE(VC.EST_PED_VTA,EST_PED_COB_NO_IMPR,'NO IMPRESO','COBRADO') || 'Ã' ||
                   TO_CHAR(VC.FEC_PED_VTA,'yyyyMMdd HH24MISS')
            FROM   VTA_PEDIDO_VTA_CAB VC,
                   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD_VIRTUAL VIR,
                    VTA_TIP_COMP TP,
                   VTA_COMP_PAGO CP
            WHERE  VC.COD_GRUPO_CIA = cCodGrupoCia_in
            AND     VC.COD_LOCAL = cCod_Local_in
            AND     VC.EST_PED_VTA IN (EST_PED_COBRADO, EST_PED_COB_NO_IMPR)
            AND     VC.SEC_MOV_CAJA = cSecMovCaja_in
            AND     TP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA
            AND     CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA
            AND     CP.COD_LOCAL = VC.COD_LOCAL
            AND     CP.NUM_PED_VTA = VC.NUM_PED_VTA
            AND     TP.TIP_COMP = VC.TIP_COMP_PAGO
            AND    VC.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
            AND    VC.COD_LOCAL = DET.COD_LOCAL
            AND    VC.NUM_PED_VTA = DET.NUM_PED_VTA
            AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA
            AND    DET.COD_PROD = VIR.COD_PROD;
    RETURN curDet;
  END;

  /**************************************************************************************************/

  FUNCTION CAJ_LISTA_DET_PED_VIRTUALES(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in   IN CHAR,
                                       cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
       OPEN curDet FOR
            SELECT DET.COD_PROD || 'Ã' ||
                    NVL(P.DESC_PROD,' ') || 'Ã' ||
                   DECODE(VIR.TIP_PROD_VIRTUAL,'T','TARJETA','RECARGA') || 'Ã' ||
                   TO_CHAR(DET.VAL_PREC_TOTAL,'999,990.00') || 'Ã' ||
                   NVL(DET.VAL_COD_APROBACION,' ') || 'Ã' ||
                   NVL(DECODE(VIR.TIP_PROD_VIRTUAL,'T',DET.DESC_NUM_TARJ_VIRTUAL,DET.DESC_NUM_TEL_REC),' ') || 'Ã' ||
                   --NVL(DET.DESC_NUM_TARJ_VIRTUAL,' ') || 'Ã' ||
                   NVL(DET.VAL_NUM_PIN,' ')
            FROM   VTA_PEDIDO_VTA_DET DET,
                   LGT_PROD P,
                   LGT_PROD_VIRTUAL VIR
            WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    DET.COD_LOCAL = cCod_Local_in
            AND    DET.NUM_PED_VTA = cNumPedVta_in
            AND    P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
            AND    P.COD_PROD = DET.COD_PROD
            AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA
            AND    DET.COD_PROD = VIR.COD_PROD;
    RETURN curDet;
  END;

  FUNCTION CAJ_OBTIENE_TIPO_PROD_VIR_PED(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                         cNumPedVta_in   IN CHAR)
    RETURN CHAR
  IS
    v_cTipoProd  CHAR(1);
  BEGIN
       SELECT NVL(VIR.TIP_PROD_VIRTUAL,' ')
       INTO   v_cTipoProd
       FROM   LGT_PROD_VIRTUAL VIR
       WHERE  VIR.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    VIR.COD_PROD IN (SELECT COD_PROD
                               FROM   VTA_PEDIDO_VTA_DET DET
                               WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                               AND    DET.COD_LOCAL = cCodLocal_in
                               AND    DET.NUM_PED_VTA = cNumPedVta_in
                               );
    RETURN v_cTipoProd;
  END;

  FUNCTION CAJ_LISTA_FORMA_PAGO_CONV(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                     cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vIndPedLocal  CHAR(1);
  BEGIN
  ---SE CONSULTA SI EL PEDIDO ES DE DELIVEY_AUTOMATICO O ES LOCAL
  SELECT C.IND_DELIV_AUTOMATICO INTO vIndPedLocal
  FROM VTA_PEDIDO_VTA_CAB C
  WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
  AND   C.COD_LOCAL     = cCodLocal_in
  AND   C.NUM_PED_VTA   = cNumPedVta_in;

  IF vIndPedLocal = 'N' THEN
  -----BUSCARA EN EL TMP_VTA_FPAGO_PED_CON_LOCAL_CON SI ES PEDIDO LOCAL
         OPEN curVta FOR
            SELECT TFPP.COD_FORMA_PAGO || 'Ã' ||
                   FP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                   '0' || 'Ã' ||
                   DECODE(TFPP.TIP_MONEDA,COD_TIP_MON_SOLES,DESC_TIP_MON_SOLES,DESC_TIP_MON_DOLARES) || 'Ã' ||
                   TO_CHAR(TFPP.IM_PAGO,'999,990.00') || 'Ã' ||
                   TO_CHAR(TFPP.IM_TOTAL_PAGO,'999,990.00') || 'Ã' ||
                   TFPP.TIP_MONEDA || 'Ã' ||
                   TFPP.VAL_VUELTO || 'Ã' ||
                   ' ' || 'Ã' ||
                   ' ' || 'Ã' ||
                   ' ' || 'Ã' ||
                   FP.COD_CONVENIO
                   --Se agrega estos 3 campos, que sufren alteracion al agregar el pago con tarjeta. Autor Luigy Terrazos   25/03/2013
                        || 'Ã' ||
                   ' '  || 'Ã' ||
                   ' '  || 'Ã' ||
                   ' '
            FROM   TMP_VTA_FPAGO_PED_CON_LOCAL TFPP,
                   VTA_FORMA_PAGO FP
            WHERE  TFPP.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    TFPP.COD_LOCAL = cCodLocal_in
            AND    TFPP.NUM_PED_VTA = cNumPedVta_in
            AND    TFPP.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
            AND    TFPP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO;

  ELSE
  -----------BUSCARA EN EL TMP_VTA_FORMA_PAGO_PEDIDO_CON SI ES PEDIDO DE DELIVERY
       OPEN curVta FOR
            SELECT TFPP.COD_FORMA_PAGO || 'Ã' ||
                   FP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                   '0' || 'Ã' ||
                   DECODE(TFPP.TIP_MONEDA,COD_TIP_MON_SOLES,DESC_TIP_MON_SOLES,DESC_TIP_MON_DOLARES) || 'Ã' ||
                   TO_CHAR(TFPP.IM_PAGO,'999,990.00') || 'Ã' ||
                   TO_CHAR(TFPP.IM_TOTAL_PAGO,'999,990.00') || 'Ã' ||
                   TFPP.TIP_MONEDA || 'Ã' ||
                   TFPP.VAL_VUELTO || 'Ã' ||
                   ' ' || 'Ã' ||
                   ' ' || 'Ã' ||
                   ' ' || 'Ã' ||
                   FP.COD_CONVENIO
                   --Se agrega estos 3 campos, que sufren alteracion al agregar el pago con tarjeta. Autor Luigy Terrazos   03.04.2013
                        || 'Ã' ||
                   ' '  || 'Ã' ||
                   ' '  || 'Ã' ||
                   ' '
            FROM   TMP_VTA_FORMA_PAGO_PEDIDO_CON TFPP,
                   VTA_FORMA_PAGO FP
            WHERE  TFPP.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    TFPP.COD_LOCAL = cCodLocal_in
            AND    TFPP.NUM_PED_VTA = cNumPedVta_in
            AND    TFPP.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
            AND    TFPP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO;
    ---------
    END IF;

    RETURN curVta;
  END;

  FUNCTION OBTIENE_COD_FORMA_PAGO(cCodGrupoCia_in IN CHAR,
                                  cCodConv_in   CHAR
                                 ) RETURN CHAR IS
      V_COD_FORMA  CHAR(10):='N';
  BEGIN

  SELECT v.cod_forma_pago into V_COD_FORMA
  from   con_mae_convenio c ,
         vta_forma_pago v
  WHERE  c.cod_convenio  = cCodConv_in AND
         c.porc_copago_conv > 0 and
         v.cod_convenio  = c.cod_convenio and
         v.cod_grupo_cia = cCodGrupoCia_in;
   RETURN V_COD_FORMA;
     EXCEPTION
     WHEN NO_DATA_FOUND THEN
       RETURN V_COD_FORMA;

  END OBTIENE_COD_FORMA_PAGO;

  --Descripcion: Obtiene el codigo de Forma de Pago con el Convenio si tiene Credito
  --Fecha       Usuario    Comentario
  --08/09/2007  DUBILLUZ   CREACION
  FUNCTION CAJ_VERIFICA_CREDITO_CONVENIO(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in      IN CHAR,
                       vIdConvenio      IN VARCHAR2)
  RETURN CHAR  IS
    V_CANTIDAD NUMBER;
  BEGIN

          SELECT COUNT(1)
          INTO  V_CANTIDAD
          FROM  VTA_FORMA_PAGO FORMA_PAGO,
                VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
                CON_MAE_CONVENIO  CV

          WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
          AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
          AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
          AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
          AND    FORMA_PAGO.COD_CONVENIO = vIdConvenio
          AND    CV.PORC_COPAGO_CONV > 0
          AND    FORMA_PAGO.COD_CONVENIO = CV.COD_CONVENIO
          AND     FORMA_PAGO.COD_GRUPO_CIA     = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
          AND     FORMA_PAGO.COD_FORMA_PAGO    = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;


   if V_CANTIDAD > 0  then
    return 'S';
   elsif V_CANTIDAD = 0 then
    RETURN 'N';
  end if;

  END CAJ_VERIFICA_CREDITO_CONVENIO;


/*******************************************************************************/
/*  FUNCTION CAJ_GET_FORMAS_PAGO_CONVENIO(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in      IN CHAR,
                       cIndPedConvenio  IN CHAR,
                       cCodConvenio     IN CHAR,
                       cCodCli_in       IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    vIndTarjeta  char(1);
    vIndEfectivo char(1);
    vTomaCondicion  varchar2(10);
    vIndComparar  char(1);
    vCadena       varchar2(10);
    X             NUMBER;
    vIndCredito   char(1);
    vSaldoDisponible number(8,2);
    vClienteExiste  number;
    CANT NUMBER; --JCORTEZ

  BEGIN
   IF cIndPedConvenio = 'S' THEN

   dbms_output.put_line('1');
 -- if cCodConvenio = '0000000001' or cCodConvenio = '0000000004' OR cCodConvenio = '0000000007' then
   --CONSULTARA SI POSEE CREDITO EL CLIENTE PARA PODER CARGAR LA FORMA DE PAGO CREDITO
    dbms_output.put_line('2');
  EXECUTE IMMEDIATE
  ' SELECT count(*)  ' ||
  ' FROM   CON_CLI_CONV@XE_000   ' ||
  ' WHERE  COD_CONVENIO    = :1 ' ||
  ' AND    COD_CLI         = :2  '
    INTO vClienteExiste USING cCodConvenio, cCodCli_in;

  ROLLBACK;

  if vClienteExiste > 0 then
   EXECUTE IMMEDIATE
  ' SELECT  VAL_CREDITO_MAX  - VAL_CREDITO_UTIL  ' ||
  '  FROM   CON_CLI_CONV@XE_000    '||
  '  WHERE  COD_CONVENIO    = :1 '||
  '  AND    COD_CLI         = :2 '
  INTO vSaldoDisponible USING   cCodConvenio , cCodCli_in;

  end if;


  if vSaldoDisponible > 0 then
     vIndCredito := 'S';
  else
     vIndCredito := 'N';
  end if;

 else
     vIndCredito := 'N';
 end if;

   --FIN
   ----CONSULTARA SI EL VALOR DE  IND_TARJETA O IND_EFECTIVO ESTAN EN "S"
   SELECT IND_TARJETA,IND_EFECTIVO INTO vIndTarjeta,vIndEfectivo
   FROM CON_MAE_CONVENIO  WHERE  cod_convenio = cCodConvenio;
   vCadena :=CONCAT(vIndTarjeta,vIndEfectivo);
   dbms_output.put_line(vCadena);

   dbms_output.put_line('vIndTarjeta : '||vIndTarjeta);
   dbms_output.put_line('vIndEfectivo : '||vIndEfectivo);

   IF vIndTarjeta <> 'N' OR vIndEfectivo <> 'N' THEN
     ------
     vIndComparar :=  (case  when vCadena = 'SN' then 'S'
                             when vCadena = 'NS' then 'N'
                             when vCadena = 'SS' then 'F'
                        end  );
     if vIndComparar = 'F' then
       vTomaCondicion := 'FALSE';
     else
       vTomaCondicion := 'TRUE';
     end if;
     vIndComparar :=  TRIM(vIndComparar);
    dbms_output.put_line(vTomaCondicion || '  >>>   '||vIndComparar);
     ------

    dbms_output.put_line('CONVENIO'||cCodConvenio);
    dbms_output.put_line('IND_CREDITO'||vIndCredito);

     OPEN curCaj FOR
       SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')    || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND   (  vTomaCondicion ='FALSE'  or FORMA_PAGO.IND_TARJ = vIndComparar )
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO--;
    ----
    union
    ----
    ---CARGA LA FORMA DE PAGO DE CREDITO SI
    --EL CONVENIO DA CREDITO
         SELECT FORMA_PAGO.COD_FORMA_PAGO || 'Ã' ||
         FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
         NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
         NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
         NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
         NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM  VTA_FORMA_PAGO FORMA_PAGO,
            VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
            CON_MAE_CONVENIO  CV
      WHERE
             vIndCredito = 'S'
      AND    FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO = cCodConvenio
      AND    CV.PORC_COPAGO_CONV > 0
      AND    FORMA_PAGO.COD_CONVENIO = CV.COD_CONVENIO
      AND     FORMA_PAGO.COD_GRUPO_CIA     = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO    = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;

--
    ELSIF   vIndTarjeta = 'N' and vIndEfectivo = 'N' THEN

       SELECT COUNT(1) INTO X
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
             con_convenio_x_forma_pago    c
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      AND    FORMA_PAGO.COD_GRUPO_CIA = C.COD_GRUPO_CIA
      AND    FORMA_PAGO.COD_FORMA_PAGO = C.COD_FORMA_PAGO
      AND    C.COD_CONVENIO  = cCodConvenio;

    IF X >0 THEN

    OPEN curCaj FOR
       SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
             con_convenio_x_forma_pago    c
      WHERE
             FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      AND    FORMA_PAGO.COD_GRUPO_CIA = C.COD_GRUPO_CIA
      AND    FORMA_PAGO.COD_FORMA_PAGO = C.COD_FORMA_PAGO
      AND    C.COD_CONVENIO  = cCodConvenio--;
    union
    ---CARGA LA FORMA DE PAGO DE CREDITO SI
    --EL CONVENIO DA CREDITO
         SELECT FORMA_PAGO.COD_FORMA_PAGO || 'Ã' ||
         FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
         NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
         NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
         NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
         NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM  VTA_FORMA_PAGO FORMA_PAGO,
            VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
            CON_MAE_CONVENIO  CV
      WHERE
             vIndCredito = 'S'
      AND    FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO = cCodConvenio
      AND    CV.PORC_COPAGO_CONV > 0
      AND    FORMA_PAGO.COD_CONVENIO = CV.COD_CONVENIO
      AND     FORMA_PAGO.COD_GRUPO_CIA     = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO    = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;

    ELSE
    --JCORTEZ 12/03/2008 Solo forma de pago por covenio
    SELECT COUNT(*) INTO CANT
    FROM con_convenio_x_forma_pago
    WHERE COD_CONVENIO=cCodConvenio;

    IF (CANT>0)THEN

     OPEN curCaj FOR
      SELECT FORMA_PAGO.COD_FORMA_PAGO      || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO  || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ')  || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')      || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO =cCodConvenio
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;
    ELSE
        OPEN curCaj FOR
                  SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO--;
    union
    ---CARGA LA FORMA DE PAGO DE CREDITO SI
    --EL CONVENIO DA CREDITO
         SELECT FORMA_PAGO.COD_FORMA_PAGO || 'Ã' ||
         FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
         NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
         NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
         NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
         NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM  VTA_FORMA_PAGO FORMA_PAGO,
            VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
            CON_MAE_CONVENIO  CV
      WHERE
             vIndCredito = 'S'
      AND    FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO = cCodConvenio
      AND    CV.PORC_COPAGO_CONV > 0
      AND    FORMA_PAGO.COD_CONVENIO = CV.COD_CONVENIO
      AND     FORMA_PAGO.COD_GRUPO_CIA     = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO    = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;
    END IF;

     END IF;
    END IF;
    --
    RETURN curCaj;

  END;*/

 /* ********************************************************************************************** */

 /* ********************************************************************************************** */
  --Fecha       Usuario    Comentario
  --26/07/2007  DUBILLUZ      Creación
  FUNCTION CAJ_VALIDA_SALDO_CREDITO(cCodGrupoCia_in IN CHAR,
                                  cCodConvenio   CHAR,
                                  cCodCli_in     CHAR) RETURN varchar2 IS

  vClienteExiste   NUMBER;
  vSaldoDisponible NUMBER(8,2) := 0;
  vIndCredito      CHAR(1);
  BEGIN


   --CONSULTARA SI POSEE CREDITO EL CLIENTE PARA PODER CARGAR LA FORMA DE PAGO CREDITO
  EXECUTE IMMEDIATE
  ' SELECT count(1)  ' ||
  ' FROM   CON_CLI_CONV@XE_000   CLI_CONV ' ||
  ' WHERE  CLI_CONV.COD_CONVENIO    = :1 ' ||
  ' AND    CLI_CONV.COD_CLI         = :2  '
    INTO vClienteExiste USING cCodConvenio, cCodCli_in;

  if vClienteExiste > 0 then
   EXECUTE IMMEDIATE
  ' SELECT  CLI_CONV.VAL_CREDITO_MAX  - CLI_CONV.VAL_CREDITO_UTIL  ' ||
  '  FROM   CON_CLI_CONV@XE_000    CLI_CONV '||
  '  WHERE  CLI_CONV.COD_CONVENIO    = :1 '||
  '  AND    CLI_CONV.COD_CLI         = :2 '
  INTO vSaldoDisponible USING   cCodConvenio , cCodCli_in;
  end if;
  if vSaldoDisponible > 0 then
     vIndCredito := 'S';
  else
     vIndCredito := 'N';
  end if;

     RETURN  vSaldoDisponible;
  END CAJ_VALIDA_SALDO_CREDITO;

 /* ********************************************************************************************** */

  FUNCTION CAJ_OBTIENE_FP_DOLARES(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
                                                          RETURN CHAR
  IS
  v_CodFPDolares CHAR(5);
  v_estado       CHAR(1);
  BEGIN

      SELECT G.LLAVE_TAB_GRAL INTO v_CodFPDolares
      FROM   PBL_TAB_GRAL  G
      WHERE  G.ID_TAB_GRAL  = '145'
      AND    G.COD_APL      = 'PTO_VENTA'
      AND    G.COD_TAB_GRAL = 'FORMA_PAGO'
      AND    G.DESC_CORTA   = 'DOLARES';

      SELECT L.EST_FORMA_PAGO_LOCAL INTO v_estado
      FROM   VTA_FORMA_PAGO_LOCAL L , VTA_FORMA_PAGO T
      WHERE  L.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    L.COD_LOCAL      = cCodLocal_in
      AND    L.COD_FORMA_PAGO = v_CodFPDolares
      AND    L.COD_GRUPO_CIA  = T.COD_GRUPO_CIA
      AND    L.COD_FORMA_PAGO = T.COD_FORMA_PAGO
      AND    L.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    T.EST_FORMA_PAGO =   ESTADO_ACTIVO;

      IF v_estado = ESTADO_ACTIVO THEN
         RETURN v_CodFPDolares;
      ELSE
         RETURN INDICADOR_NO;
      END IF;
  END;

  /***************************************************************************/
  FUNCTION CAJ_OBTIENE_VALORES_BPREPAID(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
    RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    SELECT DESC_CORTA
    FROM (
    SELECT GRAL.LLAVE_TAB_GRAL,GRAL.DESC_CORTA
    FROM   PBL_TAB_GRAL GRAL
    WHERE  GRAL.COD_APL = 'PTO_VENTA'
           AND GRAL.COD_TAB_GRAL IN ('CONST_BPREPAID')
    UNION
    SELECT '04' AS LLAVE_TAB_GRAL,DECODE(L.IND_LOCAL_PROV,'S','PRV','LIM') AS DESC_CORTA
    FROM PBL_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
     )
     ORDER BY LLAVE_TAB_GRAL;

    RETURN curCaj;
  END;

  /***************************************************************************/
  FUNCTION CAJ_GET_NUMERO_RECARGA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                 cNumPed_in       IN CHAR)
  RETURN CHAR
  IS
  numeroTelefono  VARCHAR2(20);
  BEGIN
        SELECT D.desc_num_tel_rec INTO numeroTelefono
        FROM   VTA_PEDIDO_VTA_DET D
        WHERE  D.COD_GRUPO_CIA =  cCodGrupoCia_in
        AND    D.COD_LOCAL     =  cCodLocal_in
        AND    D.NUM_PED_VTA   =  cNumPed_in;
      RETURN numeroTelefono;
  END;
 /* ************************************************************************ */
  PROCEDURE CAJ_GRABA_RESPTA_RECARGA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cNumPed_in      IN CHAR,
                                     cCodRespta_in   IN CHAR)
 IS
 BEGIN
    if cCodRespta_in = '000' then
      -- Este estado es porque SE FORSARA A COBRAR para que Verifique el Estado
      UPDATE VTA_PEDIDO_VTA_CAB C
      SET    C.COD_RPTA_RECARGA = 'XX'
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL     = cCodLocal_in
      AND    C.NUM_PED_VTA   = cNumPed_in;
    else
      UPDATE VTA_PEDIDO_VTA_CAB C
      SET    C.COD_RPTA_RECARGA = cCodRespta_in
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL     = cCodLocal_in
      AND    C.NUM_PED_VTA   = cNumPed_in;

   end if;
 END;



 /*********************************************************************************************************/
FUNCTION CAJ_VALIDA_CANT_CUPON_CAMP(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                      cNumPedVta_in    IN CHAR,
                                      cCodFormaPago_in IN CHAR,
                                      nCantCupon_in    IN NUMBER)
    RETURN FarmaCursor
  IS
    v_nMontoCupon   NUMBER;
    v_nCantcupon   NUMBER(8,3);
    v_nCantcuponIng NUMBER;
    v_nCantHabil   NUMBER;
    v_nCantDetalle NUMBER;
    v_nCantMin     NUMBER;
    v_nMonto  NUMBER:=0;

  curCaj FarmaCursor;
  CURSOR prodHabilesCupon IS
  SELECT C.COD_PROD,
                B.MONT_MIN,
                B.VALOR_CUPON
         FROM VTA_FORMA_PAGO A,
              VTA_CAMPANA_CUPON B,
              VTA_CAMPANA_PROD_USO C
         WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
         AND A.COD_FORMA_PAGO=cCodFormaPago_in
         AND A.COD_GRUPO_CIA =B.COD_GRUPO_CIA
         AND A.COD_CAMP_CUPON=B.COD_CAMP_CUPON
         AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
         AND B.COD_CAMP_CUPON=C.COD_CAMP_CUPON
         AND C.COD_PROD  IN (SELECT VTA_DET.COD_PROD
                             FROM   VTA_PEDIDO_VTA_DET VTA_DET
                             WHERE  VTA_DET.COD_GRUPO_CIA =cCodGrupoCia_in
                             AND     VTA_DET.COD_LOCAL = cCodLocal_in
                             AND     VTA_DET.NUM_PED_VTA =cNumPedVta_in
                             GROUP BY VTA_DET.COD_PROD)
         ORDER BY 2 DESC;

  CURSOR detalleProducto(p_codProd CHAR) IS
        SELECT VTA_DET.COD_PROD PRODUCTO,
               VTA_DET.VAL_PREC_TOTAL TOTAL,
               SUM(VTA_DET.CANT_ATENDIDA) CANTIDAD
        FROM   VTA_PEDIDO_VTA_DET VTA_DET
        WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND     VTA_DET.COD_LOCAL = cCodLocal_in
        AND     VTA_DET.NUM_PED_VTA = cNumPedVta_in
        AND     VTA_DET.COD_PROD = p_codProd
        GROUP BY VTA_DET.COD_PROD,VTA_DET.VAL_PREC_TOTAL;

  BEGIN
      v_nMontoCupon  := 0.00;
      v_nCantcupon  := 0.00;
      v_nCantcuponIng := nCantCupon_in;
      FOR prodHabilesCupon_rec IN prodHabilesCupon
      LOOP
          v_nCantMin:=prodHabilesCupon_rec.MONT_MIN;
          v_nMonto:=prodHabilesCupon_rec.VALOR_CUPON;
          dbms_output.put_line('prodHabilesCupon_rec.Cod_Prod : ' || prodHabilesCupon_rec.Cod_Prod);
          EXIT WHEN v_nCantcuponIng = 0;
          FOR detalleProducto_rec IN detalleProducto(prodHabilesCupon_rec.COD_PROD)
          LOOP
              v_nCantDetalle := detalleProducto_rec.CANTIDAD;
              v_nMontoCupon := v_nMontoCupon + detalleProducto_rec.TOTAL;
          END
          LOOP;
       END
       LOOP;

       v_nCantcupon:= v_nCantcupon + TRUNC(v_nMontoCupon/v_nMonto);
        dbms_output.put_line('v_nCantcupon: ' || v_nCantcupon);
        dbms_output.put_line('v_nMontoCupon: ' || v_nMontoCupon);
       OPEN curCaj FOR
       SELECT v_nCantcupon|| 'Ã' ||
              v_nMonto
       FROM DUAL;
    RETURN curCaj;
  END;

  /* ************************************************************************ */
  FUNCTION IMP_GET_IND_IMP_RECARGA(cCodGrupoCia_in   IN CHAR,
                                   cCodError      IN CHAR)
  RETURN VARCHAR2
 IS
  vResultado varchar2(14000):= '';
  cant number:=0;
  BEGIN

   if cCodError is not null  then
     if (cCodError  = '00' or cCodError = '000' )
     then
        vResultado := 'S';

     else

        vResultado := 'N';

     end if;
  else
    vResultado := 'S';
  end if;

     /*
      SELECT COUNT(*)
      INTO cant
      FROM   PBL_COD_ERROR_RECARGA L
      WHERE  L.COD_ERROR = cCodError
      AND    L.IND_COBRO = 'N';

      IF(cant=1)THEN
       vResultado:='N';
      ELSE
       vResultado:='S';
      END IF;*/
   RETURN vResultado;

   END;
/***************************************************************************/
 /* PROCEDURE VALIDA_USO_CUPONES(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR,vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curCupones IS
    \*SELECT DISTINCT TRIM(D.COD_CUPON) AS COD_CUPON
    FROM VTA_PEDIDO_CUPON_DET D
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND D.ESTADO = 'A'
    UNION*\
    SELECT COD_CUPON
    FROM VTA_CAMP_PEDIDO_CUPON
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in
          AND ESTADO = 'S'
          AND IND_USO = 'S';

    v_cIndLinea CHAR(1);

    v_cEstado VTA_CUPON.ESTADO%TYPE;
    v_cEstado2 VTA_CUPON.ESTADO%TYPE := 'A';
    v_nCant INTEGER;
  BEGIN
    SELECT COUNT(COD_CUPON) INTO v_nCant
    FROM (
      \*SELECT DISTINCT D.COD_CUPON
      FROM VTA_PEDIDO_CUPON_DET D
      WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
            AND D.COD_LOCAL = cCodLocal_in
            AND D.NUM_PED_VTA = cNumPedVta_in
            AND D.ESTADO = 'A'
      UNION*\
      SELECT COD_CUPON
      FROM VTA_CAMP_PEDIDO_CUPON
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_VTA = cNumPedVta_in
            AND ESTADO = 'S'
            AND IND_USO = 'S'
          );

    IF v_nCant > 0 THEN
      v_cIndLinea := VERIFICA_CONN_MATRIZ;

      FOR cupon IN curCupones
      LOOP
        DBMS_OUTPUT.PUT_LINE(cupon.COD_CUPON);
        SELECT ESTADO
            INTO v_cEstado
          FROM VTA_CUPON
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_CUPON = TRIM(cupon.COD_CUPON)
                FOR UPDATE;
        DBMS_OUTPUT.PUT_LINE('v_cEstado: '||v_cEstado);
        IF v_cIndLinea = 'S' THEN
          EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON@XE_000(:1,:2,:3); END;'
            USING cCodGrupoCia_in,cupon.COD_CUPON, IN OUT v_cEstado2;
        END IF;

        IF v_cEstado <> 'A' THEN
          RAISE_APPLICATION_ERROR(-20021,'Cupon no valido');
        ELSIF v_cEstado2 <> 'A' THEN
          RAISE_APPLICATION_ERROR(-20022,'Cupon no valido');
        ELSE --ELSIF v_cEstado = 'A' THEN
          UPDATE VTA_CUPON
          SET ESTADO = 'U',
              FEC_PROCESA_MATRIZ = NULL,
              USU_PROCESA_MATRIZ = NULL,
            FEC_MOD_CUP_CAB = SYSDATE,
            USU_MOD_CUP_CAB = vIdUsu_in
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_CUPON = TRIM(cupon.COD_CUPON);
        END IF;

      END LOOP;
    END IF;
  END;*/
  /***************************************************************************/
 /* FUNCTION VERIFICA_CONN_MATRIZ
  RETURN CHAR
  IS
    v_cIndLinea CHAR(1);

    V_TIME_ESTIMADO CHAR(20);
    v_time_1 TIMESTAMP;
    v_time_2 TIMESTAMP;
    V_RESULT INTERVAL DAY TO SECOND;
  BEGIN
       v_cIndLinea := 'N';
\*
    BEGIN
      SELECT LLAVE_TAB_GRAL INTO V_TIME_ESTIMADO
          FROM   PBL_TAB_GRAL
          WHERE  ID_TAB_GRAL = 76
          AND    COD_APL = 'PTO_VENTA'
          AND    COD_TAB_GRAL = 'REPOSICION';

      SELECT CURRENT_TIMESTAMP INTO v_time_1 FROM dual ;
      EXECUTE IMMEDIATE ' SELECT COD_CLI ' ||
                        ' FROM CON_CLI_CONV@XE_000' ||
                        ' WHERE ROWNUM < 2 ';
      SELECT CURRENT_TIMESTAMP INTO v_time_2 FROM dual ;

      V_RESULT := v_time_2 - v_time_1 ;

      IF(TO_CHAR(V_RESULT) > TRIM(V_TIME_ESTIMADO)) THEN
        v_cIndLinea  := 'N';
      ELSE
        v_cIndLinea  := 'S';
      END IF;
    EXCEPTION
      WHEN OTHERS THEN
      v_cIndLinea := 'N';
    END;
*\
    RETURN v_cIndLinea;
  END;*/
  /***************************************************************************/
 /* PROCEDURE ACTUALIZA_CUPONES_MATRIZ(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cNumPedVta_in IN CHAR,vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curCupones IS
    \*SELECT DISTINCT D.COD_CUPON,'S' AS ESTADO
    FROM VTA_PEDIDO_CUPON_DET D
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND D.ESTADO = 'A'
    UNION*\
    \*SELECT COD_CUPON,ESTADO
    FROM VTA_CAMP_PEDIDO_CUPON,
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in
          --AND ESTADO = 'E'
          AND IND_USO = 'S' --Los emitidos se graban por defecto con 'S'
          ;*\
    SELECT A.COD_CUPON,A.ESTADO,B.FEC_INI,B.FEC_FIN
    FROM VTA_CAMP_PEDIDO_CUPON A,
         VTA_CUPON B
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_LOCAL = cCodLocal_in
          AND A.NUM_PED_VTA = cNumPedVta_in
          --AND ESTADO = 'E'
          AND A.IND_USO = 'S' --Los emitidos se graban por defecto con 'S'
          AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
          AND A.COD_CAMP_CUPON=B.COD_CAMPANA
          AND A.COD_CUPON=B.COD_CUPON;

    v_cIndLinea CHAR(1);
    v_nCant INTEGER;
    vRetorno CHAR(1) := 'X';
    v_cEstado VTA_CUPON.ESTADO%TYPE;

    mesg_body VARCHAR2(4000);
  BEGIN
    SELECT SUM(CANT) INTO v_nCant
    FROM (
      \*SELECT COUNT(DISTINCT D.COD_CUPON) AS CANT
      FROM VTA_PEDIDO_CUPON_DET D
      WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
            AND D.COD_LOCAL = cCodLocal_in
            AND D.NUM_PED_VTA = cNumPedVta_in
            AND D.ESTADO = 'A'
      UNION*\
      SELECT COUNT(COD_CUPON) AS CANT
      FROM VTA_CAMP_PEDIDO_CUPON
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_VTA = cNumPedVta_in
            --AND ESTADO = 'E'
            AND IND_USO = 'S'
    );

    IF v_nCant > 0 THEN
      v_cIndLinea := VERIFICA_CONN_MATRIZ;

      IF v_cIndLinea = 'S' THEN
        FOR cupon IN curCupones
        LOOP
          SELECT ESTADO
            INTO v_cEstado
          FROM VTA_CUPON
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_CUPON = TRIM(cupon.COD_CUPON)
                FOR UPDATE;
          DBMS_OUTPUT.PUT_LINE('cupon.COD_CUPON:'||cupon.COD_CUPON);
          IF cupon.ESTADO = 'E' THEN
            EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.GRABAR_CUPON@XE_000(:1,:2,:3,:4,:5,:6); END;'
            --USING cCodGrupoCia_in,TRIM(cupon.COD_CUPON),vIdUsu_in,IN OUT vRetorno;
            USING cCodGrupoCia_in,TRIM(cupon.COD_CUPON),vIdUsu_in,TRIM(cupon.FEC_INI),TRIM(cupon.FEC_FIN),IN OUT vRetorno;--JCORTEZ 30/07/08
            DBMS_OUTPUT.PUT_LINE('vRetorno:'||vRetorno);
            IF vRetorno = 'S' THEN
              UPDATE VTA_CUPON
              SET FEC_PROCESA_MATRIZ = SYSDATE,
                  USU_PROCESA_MATRIZ = vIdUsu_in
              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                    AND COD_CUPON = TRIM(cupon.COD_CUPON);
              COMMIT;
            ELSE
              ROLLBACK;
            END IF;
          ELSE
            EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.ACT_ESTADO_CUPON@XE_000(:1,:2,:3,:4,:5); END;'
            USING cCodGrupoCia_in,TRIM(cupon.COD_CUPON),v_cEstado,vIdUsu_in,IN OUT vRetorno;
            DBMS_OUTPUT.PUT_LINE('vRetorno:'||vRetorno);
            IF vRetorno = 'S' THEN
              UPDATE VTA_CUPON
              SET FEC_PROCESA_MATRIZ = SYSDATE,
                  USU_PROCESA_MATRIZ = vIdUsu_in
              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                    AND COD_CUPON = TRIM(cupon.COD_CUPON);
              COMMIT;
            ELSE
              ROLLBACK;
              --Verifique el estado del cupon usado
              mesg_body := 'ERROR AL ACTUALIZAR CUPON <BR>'||
                           'PEDIDO No ' || cNumPedVta_in || '<BR>'||
                           'CUPON No '||cupon.COD_CUPON;
              FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                         cCodLocal_in,
                                         'erios@mifarma.com.pe',--'lmesia@mifarma.com.pe;;joliva@mifarma.com.pe',
                                         'ERROR AL ACTUALIZAR CUPON MATRIZ',
                                         'VERIFIQUE',
                                         mesg_body,
                                         '');
            END IF;
          END IF;
        END LOOP;
      END IF;
    END IF;
  END;*/
  /***************************************************************************/


     /* ************************************************************************ */
  FUNCTION GET_VERIFICA_CAMP_FP(cCodGrupoCia_in IN CHAR,
                                cCodLocal        IN CHAR,
                                cNumPedVta      IN CHAR)
  RETURN FarmaCursor
  IS
  --vResultado varchar2(14000):= '';
  cur FarmaCursor;
  vCodFormaPago CHAR(5):= '';
  BEGIN
      OPEN cur FOR
       SELECT B.NUM_PED_VTA|| 'Ã' ||
              D.COD_FORMA_PAGO|| 'Ã' ||
              TO_CHAR(SUM(C.VAL_PREC_TOTAL),'999,990.000')|| 'Ã' ||
              A.DESC_CUPON|| 'Ã' ||
              ' '|| 'Ã' ||
              NVL(A.COD_CAMP_CUPON,' ')
      FROM VTA_CAMPANA_CUPON A,
           VTA_PEDIDO_CUPON B,
           VTA_PEDIDO_VTA_DET C,
           VTA_FORMA_PAGO_CAMP D,
           VTA_FORMA_PAGO E
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND B.COD_LOCAL=cCodLocal
      AND C.NUM_PED_VTA=cNumPedVta
      AND E.EST_FORMA_PAGO='A'
      AND A.TIP_CAMPANA='C'
      AND C.COD_PROD IN (SELECT D.COD_PROD
                         FROM VTA_CAMPANA_PROD D
                         WHERE D.COD_GRUPO_CIA=cCodGrupoCia_in
                         AND D.COD_CAMP_CUPON=B.COD_CAMP_CUPON)
      AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
      AND A.COD_CAMP_CUPON=B.COD_CAMP_CUPON
      AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
      AND B.COD_LOCAL=C.COD_LOCAL
      AND B.NUM_PED_VTA=C.NUM_PED_VTA
      --AND A.COD_FORMA_PAGO_CAMP=D.COD_FORMA_PAGO_CAMP
      AND A.COD_CAMP_CUPON=D.COD_CAMP_CUPON
      AND D.COD_FORMA_PAGO=E.COD_FORMA_PAGO
      GROUP BY B.NUM_PED_VTA, D.COD_FORMA_PAGO ,A.DESC_CUPON,A.COD_CAMP_CUPON;

    /*  exception
        when no_data_found then
          vCodFormaPago := 'N';*/

   RETURN cur;
   END;

/*----------------------------------------------------------------------------------------------------------------
GOAL : Cargar los Cupones EAN 13 en Tablas
Pre Cond : La tabla  VTA_PEDIDO_CUPON contine la cantidad de cupones x campaña
Out      : Carga Tabla VTA_CAMP_PEDIDO_CUPON, VTA_CUPON
History  : 16-JUL-14  TCT Modifica para no usar codigo local en la barra y aumentar la longitud del correlativo
           a 7 digitos
-----------------------------------------------------------------------------------------------------------------*/
 PROCEDURE CAJ_GENERA_CUPON(pCodGrupoCia_in         IN CHAR,
                            cCodLocal_in            IN CHAR,
                            cNumPedVta_in            IN CHAR,
                            cIdUsu_in             IN CHAR,
                            cDni_in               IN CHAR)
  IS
  v_rVtaPedidoVtaCab VTA_PEDIDO_VTA_CAB%ROWTYPE;
  v_SecEan  NUMBER;
  c_SecEan  CHAR(8);
  v_CodCupon   VARCHAR2(20);
  n_cant    NUMBER(8);
  n_canteExist NUMBER;
  i number:=0;
  FEC_INI_AUX DATE;
  FEC_FIN_AUX DATE;
  v_ip        VARCHAR2(20);

  cod_cupon VARCHAR2(20);

  CURSOR CURCAMP IS
 SELECT  DISTINCT X.COD_GRUPO_CIA,
          X.COD_LOCAL,
          X.COD_CAMP_CUPON,
          X.NUM_PED_VTA,
          X.CANTIDAD,
          NVL(Z.NUM_DIAS_INI,0) AS NUM_DIAS_INI,
          NVL(Z.NUM_DIAS_VIG,0) AS NUM_DIAS_VIG,
          Z.FECH_INICIO_USO,
          Z.FECH_FIN_USO,
          Z.IND_MULTIUSO,
          Z.COD_CAMP_IMP_CUP  --- 18-JUL-14 TCT ADD
  FROM VTA_PEDIDO_CUPON X,
       VTA_PEDIDO_VTA_CAB Y,
       VTA_CAMPANA_CUPON Z,
       VTA_CAMPANA_PROD_USO P,
       LGT_PROD_LOCAL L
  WHERE X.COD_GRUPO_CIA=pCodGrupoCia_in
  AND X.COD_LOCAL=cCodLocal_in
  AND Y.NUM_PED_VTA=cNumPedVta_in
  AND X.IND_IMPRESION='S'
  AND Z.TIP_CAMPANA='C'
  AND L.COD_GRUPO_CIA = pCodGrupoCia_in
  AND L.COD_LOCAL = cCodLocal_in
  AND L.STK_FISICO> 0
  AND L.COD_GRUPO_CIA = X.COD_GRUPO_CIA
  AND L.COD_LOCAL = X.COD_LOCAL
  AND P.COD_GRUPO_CIA = Z.COD_GRUPO_CIA
  AND P.COD_CAMP_CUPON = Z.COD_CAMP_CUPON
  AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
  AND P.COD_PROD = L.COD_PROD
  --AND Y.EST_PED_VTA NOT IN ('N','C')
  AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
  AND X.COD_LOCAL=Y.COD_LOCAL
  AND X.NUM_PED_VTA=Y.NUM_PED_VTA
  AND X.COD_GRUPO_CIA=Z.COD_GRUPO_CIA
  AND X.COD_CAMP_CUPON=Z.COD_CAMP_CUPON;


  ROWCURCAMP CURCAMP%ROWTYPE;



  BEGIN
    --- TCT DEBUG
    --- sp_graba_log(ac_descrip => 'PASO POR :CAJ_GENERA_CUPON');
    ---

    FOR ROWCURCAMP IN CURCAMP
    LOOP
       BEGIN

         IF(ROWCURCAMP.IND_MULTIUSO='N')THEN

          n_cant:=ROWCURCAMP.CANTIDAD;
          dbms_output.put_line('INGRESO CURSOR -->'||n_cant);
          FOR i IN 1..n_cant
          LOOP
           --v_SecEan := Farma_Utility.OBTENER_NUMERACION(pCodGrupoCia_in, cCodLocal_in,C_C_COD_EAN_CUPON);
           v_SecEan := PTOVENTA_CAJ.OBTENER_NUMERACION(pCodGrupoCia_in, cCodLocal_in,ROWCURCAMP.COD_CAMP_CUPON);

          --- < 16-JUL-14  TCT Cambio los Cupones ya no deben Imprimir el Codigo de Local >
           IF v_SecEan <= 999999 THEN  -- tenia 4 pos, se aumento a 6
             --RAISE_APPLICATION_ERROR(-20022,'Se ha superado el límite de cupones a imprimir.');

             --- 100.-  Veirfica Formato de Imprimir Cupones
             IF ROWCURCAMP.COD_CAMP_IMP_CUP IS  NULL THEN
               -- Antiguo modo de imprimir cupones
               c_SecEan := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_SecEan),4,'0','I');
               v_CodCupon:=TRIM(ROWCURCAMP.COD_CAMP_CUPON)||TRIM(ROWCURCAMP.COD_LOCAL||TRIM(c_SecEan));

             ELSIF ROWCURCAMP.COD_CAMP_IMP_CUP IS NOT NULL THEN
               -- Nuevo modo de imprimir cupones
               c_SecEan := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_SecEan),6,'0','I');
               v_CodCupon:=TRIM(ROWCURCAMP.COD_CAMP_IMP_CUP)||TRIM(ROWCURCAMP.COD_LOCAL||TRIM(c_SecEan));

             END IF;

             --v_CodCupon:=TRIM(C_CARACTER_INIC)||TRIM(ROWCURCAMP.COD_CAMP_CUPON)||TRIM(ROWCURCAMP.COD_LOCAL||TRIM(c_SecEan));
             --v_CodCupon:=TRIM(ROWCURCAMP.COD_CAMP_CUPON)||TRIM(ROWCURCAMP.COD_LOCAL||TRIM(c_SecEan));
             --v_CodCupon:=TRIM(ROWCURCAMP.COD_CAMP_IMP_CUP)||TRIM(ROWCURCAMP.COD_LOCAL||TRIM(c_SecEan));
          --- < /16-JUL-14  TCT Cambio los Cupones ya no deben Imprimir el Codigo de Local >

             dbms_output.put_line('CUPO GENERADO--> '||v_CodCupon);

             v_CodCupon := PTOVENTA_IMP_CUPON.GENERA_EAN13(v_CodCupon);

             --Se asume que la vigenci del cupon debe estar entre las fechas de uso de la campaña
             IF(ROWCURCAMP.NUM_DIAS_INI=0)THEN
                 FEC_INI_AUX:=TRUNC(SYSDATE);
                 IF(FEC_INI_AUX<ROWCURCAMP.FECH_INICIO_USO)THEN
                   FEC_INI_AUX:=ROWCURCAMP.FECH_INICIO_USO;
                 END IF;
             ELSIF (ROWCURCAMP.NUM_DIAS_INI>0) THEN
                 FEC_INI_AUX:=TRUNC(SYSDATE+ROWCURCAMP.NUM_DIAS_INI);
                 IF(FEC_INI_AUX<ROWCURCAMP.FECH_INICIO_USO)THEN
                   FEC_INI_AUX:=ROWCURCAMP.FECH_INICIO_USO;
                 END IF;
             END IF;

             IF(ROWCURCAMP.NUM_DIAS_VIG=0)THEN
                 FEC_FIN_AUX:=ROWCURCAMP.FECH_FIN_USO;
             ELSIF (ROWCURCAMP.NUM_DIAS_VIG>0)THEN
                 FEC_FIN_AUX:= TRUNC(FEC_INI_AUX + ROWCURCAMP.NUM_DIAS_VIG);
                 IF(FEC_FIN_AUX>ROWCURCAMP.FECH_FIN_USO)THEN
                   FEC_FIN_AUX:=ROWCURCAMP.FECH_FIN_USO;
                 END IF;
             END IF;


        SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
            FROM DUAL;

             INSERT INTO VTA_CUPON(COD_GRUPO_CIA
                                  ,COD_LOCAL
                                  ,COD_CUPON
                                  ,ESTADO
                                  ,USU_CREA_CUP_CAB
                                  ,USU_MOD_CUP_CAB
                                  ,FEC_MOD_CUP_CAB
                                  ,COD_CAMPANA
                                  ,SEC_CUPON
                                  ,FEC_INI
                                  ,FEC_FIN,
                                  IP,NUM_DOC_IDENT)--JCORTEZ 17.08.09 nuevos campos
             VALUES (ROWCURCAMP.COD_GRUPO_CIA,
                    ROWCURCAMP.COD_LOCAL,
                    v_CodCupon,
                    C_ESTADO_ACTIVO,
                    cIdUsu_in,
                    NULL,NULL,ROWCURCAMP.COD_CAMP_CUPON,c_SecEan,FEC_INI_AUX,FEC_FIN_AUX,v_ip,cDni_in);
                    --DECODE(ROWCURCAMP.NUM_DIAS_INI,0,ROWCURCAMP.FECH_INICIO_USO,TRUNC(SYSDATE+ROWCURCAMP.NUM_DIAS_INI)),
                    --DECODE(ROWCURCAMP.NUM_DIAS_VIG,0,ROWCURCAMP.FECH_FIN_USO,TRUNC((SYSDATE+ROWCURCAMP.NUM_DIAS_INI)+ROWCURCAMP.NUM_DIAS_VIG)));

            INSERT INTO VTA_CAMP_PEDIDO_CUPON(COD_GRUPO_CIA
                                        ,COD_LOCAL
                                        ,COD_CUPON
                                        ,NUM_PED_VTA
                                        ,ESTADO
                                        ,USU_CREA_CUPON_PED
                                        ,USU_MOD_CUPON_PED
                                        ,FEC_MOD_CUPON_PED
                                        ,COD_CAMP_CUPON)
             VALUES(ROWCURCAMP.COD_GRUPO_CIA,
                    ROWCURCAMP.COD_LOCAL,
                    v_CodCupon,ROWCURCAMP.NUM_PED_VTA,C_ESTADO_EMITIDO,cIdUsu_in,NULL,NULL,ROWCURCAMP.COD_CAMP_CUPON);

              --Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(pCodGrupoCia_in,cCodLocal_in,C_C_COD_EAN_CUPON,cIdUsu_in);

              UPDATE VTA_NUMERA_CUPON X
              SET X.SEC_CUPON=v_SecEan
              WHERE X.COD_GRUPO_CIA=ROWCURCAMP.COD_GRUPO_CIA
              AND X.COD_LOCAL=ROWCURCAMP.COD_LOCAL
              AND X.COD_CAMP_CUPON=ROWCURCAMP.COD_CAMP_CUPON;
            END IF;
          END
          LOOP;
      /*EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;  */

          ELSE --JCORTEZ 15/08/2008 se genera pedido cupon multiuso
           n_cant:=ROWCURCAMP.CANTIDAD;

          /*FOR i IN 1..n_cant
          LOOP*/

          SELECT A.COD_CUPON INTO cod_cupon
          FROM VTA_CUPON A
          WHERE A.COD_GRUPO_CIA=ROWCURCAMP.COD_GRUPO_CIA
          AND A.COD_CAMPANA=ROWCURCAMP.COD_CAMP_CUPON;

           INSERT INTO VTA_CAMP_PEDIDO_CUPON(COD_GRUPO_CIA
                                        ,COD_LOCAL
                                        ,COD_CUPON
                                        ,NUM_PED_VTA
                                        ,ESTADO
                                        ,USU_CREA_CUPON_PED
                                        ,USU_MOD_CUPON_PED
                                        ,FEC_MOD_CUPON_PED
                                        ,COD_CAMP_CUPON)
             VALUES(ROWCURCAMP.COD_GRUPO_CIA,
                    ROWCURCAMP.COD_LOCAL,
                    cod_cupon,ROWCURCAMP.NUM_PED_VTA,C_ESTADO_EMITIDO,cIdUsu_in,NULL,NULL,ROWCURCAMP.COD_CAMP_CUPON);
         /*  END
           LOOP;*/

          END IF;
       END;
    END
    LOOP;
  END;

  /****************************************************************************/
   FUNCTION OBTENER_NUMERACION(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in       IN CHAR,
                               cCodCamp_in       IN CHAR)
  RETURN NUMBER
  IS
    v_nNumero  NUMBER;
  BEGIN

    SELECT MAX(NUM.SEC_CUPON)
    INTO   v_nNumero
    FROM   VTA_NUMERA_CUPON NUM
    WHERE  NUM.Cod_Grupo_Cia =cCodGrupoCia_in
    AND     NUM.Cod_Local = cCodLocal_in
    AND    NUM.COD_CAMP_CUPON=cCodCamp_in;--for update

    IF ( v_nNumero IS NULL OR v_nNumero=0 ) THEN
      v_nNumero := 1;
    ELSE
      v_nNumero := v_nNumero+1;
    END IF;
  RETURN v_nNumero;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
    RETURN 0;
  END;

  /*******************************************************************************************************/
  PROCEDURE CAJ_UPDATE_IND_IMP(pCodGrupoCia_in        IN CHAR,
                              cCodLocal_in            IN CHAR,
                              cCodCamp                IN CHAR,
                                cNumPedVta_in            IN CHAR,
                               cIndImpre_in            IN CHAR,
                              cIndTodos_in            IN CHAR)
  IS
  CANT  NUMBER:=0;
  BEGIN

    IF(cIndTodos_in='S')THEN
      UPDATE VTA_PEDIDO_CUPON X
      SET X.IND_IMPRESION=cIndImpre_in
      WHERE X.COD_GRUPO_CIA=pCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.NUM_PED_VTA=cNumPedVta_in;
    ELSIF (cIndTodos_in='N')THEN
      UPDATE VTA_PEDIDO_CUPON X
      --SET X.IND_IMPRESION=cIndImpre_in
      SET X.IND_IMPRESION=cIndImpre_in
      WHERE X.COD_GRUPO_CIA=pCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.COD_CAMP_CUPON=cCodCamp
      AND X.NUM_PED_VTA=cNumPedVta_in;
    end if;

  END;

  /* ************************************************************************ */
  FUNCTION GET_VERIFICA_PED_CAMP(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                   cNumPedVta_in    IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(14000):= '';
  cant number:=0;
  cant2 number:=0;
  BEGIN

    SELECT COUNT(*) INTO cant
    FROM VTA_PEDIDO_CUPON A
         --VTA_CAMPANA_CUPON B
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.NUM_PED_VTA=cNumPedVta_in;

     IF(cant>0)THEN
       vResultado:='S';
     ELSE
       vResultado:='N';
     END IF;

   RETURN vResultado;
   END;

  /* ************************************************************************ */
  FUNCTION GET_FORMA_PAGO_PED_CUPON(cCodGrupoCia_in IN CHAR,
                                    cCodLocal        IN CHAR,
                                    cNumPedVta      IN CHAR)
  RETURN FarmaCursor
  IS
  --vResultado varchar2(14000):= '';
  cur FarmaCursor;
  vCodFormaPago CHAR(5):= '';
  BEGIN
      OPEN cur FOR
      SELECT A.COD_FORMA_PAGO || 'Ã' ||
            B.DESC_CORTA_FORMA_PAGO|| 'Ã' ||
            DECODE(A.CANT_CUPON,NULL,0)|| 'Ã' ||
            CASE A.TIP_MONEDA
            WHEN '01' THEN 'Soles'
            WHEN '02' THEN 'Dolares'
            END|| 'Ã' ||
            TO_CHAR(A.IM_PAGO,'999,990.000')|| 'Ã' ||
            TO_CHAR(A.IM_TOTAL_PAGO,'999,990.000')|| 'Ã' ||
            nvl(A.TIP_MONEDA,' ')|| 'Ã' ||
            '0' || 'Ã' ||
            NVL(A.NUM_TARJ,' ')|| 'Ã' ||
            DECODE(A.FEC_VENC_TARJ,NULL,' ')|| 'Ã' ||
            NVL(A.NOM_TARJ,' ')|| 'Ã' ||
            ' '
      FROM TMP_FORMA_PAGO_PED_CUPON A,
           VTA_FORMA_PAGO B
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCodLocal
      AND A.NUM_PED_VTA=cNumPedVta
      AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
      AND A.COD_FORMA_PAGO=B.COD_FORMA_PAGO;

   RETURN cur;
   END;

   /*************************************************************************************************************/
 PROCEDURE CAJ_UPDATE_IND_IMP_SIN_FP(pCodGrupoCia_in        IN CHAR,
                                     cCodLocal_in           IN CHAR,
                                       cNumPedVta_in          IN CHAR)
  IS
  CANT  NUMBER:=0;
    CURSOR cupongenera IS
      SELECT A.COD_CAMP_CUPON
      FROM VTA_PEDIDO_CUPON A,
           VTA_CAMPANA_CUPON B
      WHERE A.NUM_PED_VTA=cNumPedVta_in
      AND B.TIP_CAMPANA='C'
      AND A.COD_CAMP_CUPON NOT IN (SELECT DISTINCT COD_CAMP_CUPON FROM VTA_FORMA_PAGO_CAMP)
      AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
      AND A.COD_CAMP_CUPON=B.COD_CAMP_CUPON;

  BEGIN

  FOR cupongenera_cur IN cupongenera
  LOOP
      UPDATE VTA_PEDIDO_CUPON X
      SET X.IND_IMPRESION='S'
      WHERE X.COD_GRUPO_CIA=pCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.NUM_PED_VTA=cNumPedVta_in
      AND X.COD_CAMP_CUPON=cupongenera_cur.COD_CAMP_CUPON;
  END
  LOOP;

 END;


 /***********************************************************************************************************/
 FUNCTION CAJ_F_VAR_LINEA_DOC(cIdeTabGral  IN VARCHAR2 )
RETURN VARCHAR2
IS
vCantLineas   VARCHAR2(10);
BEGIN
    SELECT llave_tab_gral INTO vCantLineas
    FROM pbl_tab_gral
    WHERE id_tab_gral = cIdeTabGral
    AND est_tab_gral = 'A' ;
    RETURN vCantLineas;
END CAJ_F_VAR_LINEA_DOC;

 /*****************************************************************************/

 FUNCTION CAJ_F_VAR_IND_PED_CONVENIO(pCodGrupoCia_in       IN CHAR,
                                     cCodLocal_in           IN CHAR,
                                       cNumPedVta_in          IN CHAR)
  RETURN VARCHAR2
  IS
  vIndPedConvenio   VARCHAR2(1);
  BEGIN
      SELECT IND_PED_CONVENIO INTO vIndPedConvenio
      FROM VTA_PEDIDO_VTA_CAB
      WHERE COD_GRUPO_CIA = pCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND NUM_PED_VTA = cNumPedVta_in;
    RETURN vIndPedConvenio;
  END CAJ_F_VAR_IND_PED_CONVENIO;

  /*****************************************************************************/
  FUNCTION  CAJ_F_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in IN CHAR,
                                             cCodLocal_in    IN CHAR,
                                             cNumPedVta_in   IN CHAR)
  RETURN CHAR
  IS

    v_cIndValidarMonto    PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='N';
    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_nValNetoPedVta      VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nValRedondeo        VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_nSumaTotDet       NUMBER:=0;
    v_nSumaValorDet     NUMBER:=0;
    v_nSumaFormaPago       NUMBER:=0;

    v_vDescLocal VARCHAR2(120);

    mesg_body   VARCHAR2(4000);

  BEGIN

        SELECT TRIM(G.LLAVE_TAB_GRAL) INTO v_cIndValidarMonto
        FROM PBL_TAB_GRAL G
        WHERE G.ID_TAB_GRAL = 238;

        SELECT G.LLAVE_TAB_GRAL INTO v_cEmailErrorPtoVenta
        FROM PBL_TAB_GRAL  G
        WHERE G.ID_TAB_GRAL = 241;

        SELECT C.VAL_NETO_PED_VTA, C.VAL_REDONDEO_PED_VTA
        INTO v_nValNetoPedVta, v_nValRedondeo
        FROM  VTA_PEDIDO_VTA_CAB C
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   C.COD_LOCAL     = cCodLocal_in
        AND   C.NUM_PED_VTA   = cNumPedVta_in;

        SELECT SUM(D.VAL_PREC_TOTAL) into v_nSumaValorDet
        FROM VTA_PEDIDO_VTA_DET D
        WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   D.COD_LOCAL     = cCodLocal_in
        AND   D.NUM_PED_VTA   = cNumPedVta_in;

        --dubilluz 14.10.2011
        SELECT nvl(SUM(IM_TOTAL_PAGO - VAL_VUELTO),0) INTO v_nSumaFormaPago
        FROM   VTA_FORMA_PAGO_PEDIDO
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in
        AND    NUM_PED_VTA = cNumPedVta_in;

        v_nSumaTotDet:= v_nSumaValorDet+v_nValRedondeo;

        IF (v_nSumaFormaPago = v_nSumaTotDet) AND (v_nSumaFormaPago = v_nValNetoPedVta ) THEN
            RETURN 'EXITO';
        ELSE

            --DESCRIPCION DE LOCAL
            SELECT COD_LOCAL ||' - '|| DESC_LOCAL
            INTO   v_vDescLocal
            FROM   PBL_LOCAL
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND    COD_LOCAL = cCodLocal_in;

            --GENERANDO EL CONTENIDO DEL CORREO A ENVIAR
            mesg_body := '<H1>ERROR AL COBRAR PEDIDO DE VENTA</H1><BR>'||
                         '<i>inconsistencia de montos entre cabecera , detalle de pedido y suma de forma de pago</i><BR>'||
                         '<br>CABECERA PEDIDO &nbsp; &nbsp; : <b>'||to_char(v_nValNetoPedVta,'999,990.00')||
                         '</b> &nbsp;&nbsp;&nbsp;=====>&nbsp; VAL_NETO_PED_VTA '||
                         '<br>DETALLE &nbsp; PEDIDO &nbsp; &nbsp; &nbsp;: <b>'||to_char(v_nSumaTotDet,'999,990.00')||
                         '</b> &nbsp;&nbsp;&nbsp;=====>&nbsp; SUM(D.VAL_PREC_TOTAL):<b>'||
                         to_char(v_nSumaValorDet,'999,990.00')||'</b> + VAL_REDONDEO_PED_VTA: <B>'||
                         to_char(v_nValRedondeo,'999,990.00')||'</B> )'||
                         '<br>TOTAL FORMA PAGO : <b>'||to_char(v_nSumaFormaPago,'999,990.00')||
                         '</b> &nbsp;&nbsp;&nbsp;=====>&nbsp; SUM(IM_TOTAL_PAGO - VAL_VUELTO):<b>'||
                         '<BR><br> NUM_PEDIDO : <B>'||cNumPedVta_in||'</B>'||
                         '<BR> LOCAL : <B>'||v_vDescLocal||'</B>'||
                         '<BR><BR> FECHA : <B>'||to_char(SYSDATE,'dd/MM/yyyy HH24:MI:SS')||'</B>';

            --ENVIANDO EL CORREO
            FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                           v_cEmailErrorPtoVenta,
                           'ERROR AL COBRAR PEDIDO: DIFER. TOTAL CABECERA , DETALLE, FORMA PAGO : '||v_vDescLocal,
                           'ALERTA',
                           mesg_body,
                           '',
                           FARMA_EMAIL.GET_EMAIL_SERVER,
                           TRUE);

            dbms_output.put_line('v_cIndValidarMonto--> '||v_cIndValidarMonto);

            IF v_cIndValidarMonto = 'S' THEN
               RETURN 'ERROR';
            ELSE
               RETURN 'EXITO';
            END IF;

            /*||'ERROR AL COBRAR PEDIDO No ' || cNumPedVta_in || '<BR>El total de la cabecera es ' ||
            totales_K.TOTAL_CAB || ' y el total de formas de pago es ' || totales_K.TOTAL_FP || '.<BR>' ||
            cDescDetalleForPago_in;

            FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                   cCodLocal_in,
                                   'joliva@mifarma.com.pe',--'lmesia@mifarma.com.pe;;joliva@mifarma.com.pe',
                                   'ERROR AL COBRAR PEDIDO - DIFERENCIAS EN TOTALES - LOCAL ',
                                   'ERROR',
                                   mesg_body,
                                   '');*/


        END IF;


    /*FOR totales_K IN totales
    LOOP
        mesg_body := 'ERROR AL COBRAR PEDIDO No ' || cNumPedVta_in || '<BR>El total de la cabecera es ' || totales_K.TOTAL_CAB || ' y el total de formas de pago es ' || totales_K.TOTAL_FP || '.<BR>' || cDescDetalleForPago_in;
        FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                   cCodLocal_in,
                                   'joliva@mifarma.com.pe',--'lmesia@mifarma.com.pe;;joliva@mifarma.com.pe',
                                   'ERROR AL COBRAR PEDIDO - DIFERENCIAS EN TOTALES - LOCAL ',
                                   'ERROR',
                                   mesg_body,
                                   '');
        RETURN 'ERROR';--NUEVO
        EXIT;
    END LOOP;*/


  END;

  /* *************************************************************************************************** */
/*
PROCEDURE CAJ_CORRIGE_COMPROBANTES(
                                   cCodGrupoCia_in  IN CHAR,
                                   codLocal_in       IN CHAR,
                                   cSecIni_in        IN CHAR,
                                   cSecFin_in         IN CHAR,
                                   cTipComp_in      IN CHAR,
                                   nCant_in           IN NUMBER,
                                    nIndDir          IN NUMBER,
                                   cCodUsu_in        IN CHAR
                                  )
   IS
*/

  FUNCTION  CAJ_F_IS_COMP_PROCESO_SAP(cCodGrupoCia_in  IN CHAR,
                                   codLocal_in       IN CHAR,
                                   cSecIni_in        IN CHAR,
                                   cSecFin_in         IN CHAR,
                                   cTipComp_in      IN CHAR)
  RETURN CHAR
  IS
  nCant number;

  comprobanteinicial  NUMBER(10) := TO_NUMBER(cSecIni_in);
  comprobantefinal     NUMBER(10) := TO_NUMBER(cSecFin_in);

  BEGIN

      SELECT count(1)
        INTO nCant
        FROM VTA_COMP_PAGO CP
       WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND CP.COD_LOCAL = codLocal_in
         AND CP.FEC_PROCESO_SAP IS NOT NULL
         AND CP.TIP_COMP_PAGO = cTipComp_in
         AND --CP.NUM_COMP_PAGO
         --RH: 21.10.2014 FAC-ELECTRONICA
      FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
             between
             TRIM(TO_CHAR(comprobanteinicial, '0000000000')) and
             TRIM(TO_CHAR(comprobantefinal, '0000000000'));

    IF nCant > 0 THEN
        RETURN 'S';
    ELSE
        RETURN 'N';
    END IF;


  END CAJ_F_IS_COMP_PROCESO_SAP;
/*--------------------------------------------------------------------*/
 --  11/12/2008  ASOLIS  CREACION
  FUNCTION CAJ_GET_FORMAS_PAGO_CONVENIO(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in      IN CHAR,
                       cIndPedConvenio  IN CHAR,
                       cCodConvenio     IN CHAR,
                       cCodCli_in       IN CHAR,
                       cValorCredito    IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    vIndTarjeta  char(1);
    vIndEfectivo char(1);
    vTomaCondicion  varchar2(10);
    vIndComparar  char(1);
    vCadena       varchar2(10);
    X             NUMBER;
    vIndCredito   char(1);
    vSaldoDisponible number(8,2);
    vClienteExiste  number;
    CANT NUMBER; --JCORTEZ

  BEGIN
   IF cIndPedConvenio = 'S' THEN

   -- if cCodConvenio = '0000000001' or cCodConvenio = '0000000004' OR cCodConvenio = '0000000007' then
   --CONSULTARA SI POSEE CREDITO EL CLIENTE PARA PODER CARGAR LA FORMA DE PAGO CREDITO

  if cValorCredito = 'S' then
     vIndCredito := 'S';
  else
     vIndCredito := 'N';
  end if;



 else
     vIndCredito := 'N';
 end if;

   --FIN
   ----CONSULTARA SI EL VALOR DE  IND_TARJETA O IND_EFECTIVO ESTAN EN "S"
   SELECT IND_TARJETA,IND_EFECTIVO INTO vIndTarjeta,vIndEfectivo
   FROM CON_MAE_CONVENIO  WHERE  cod_convenio = cCodConvenio;
   vCadena :=CONCAT(vIndTarjeta,vIndEfectivo);
   dbms_output.put_line(vCadena);

   dbms_output.put_line('vIndTarjeta : '||vIndTarjeta);
   dbms_output.put_line('vIndEfectivo : '||vIndEfectivo);

   IF vIndTarjeta <> 'N' OR vIndEfectivo <> 'N' THEN
     ------
     vIndComparar :=  (case  when vCadena = 'SN' then 'S'
                             when vCadena = 'NS' then 'N'
                             when vCadena = 'SS' then 'F'
                        end  );
     if vIndComparar = 'F' then
       vTomaCondicion := 'FALSE';
     else
       vTomaCondicion := 'TRUE';
     end if;
     vIndComparar :=  TRIM(vIndComparar);
    dbms_output.put_line(vTomaCondicion || '  >>>   '||vIndComparar);
     ------

    dbms_output.put_line('CONVENIO'||cCodConvenio);
    dbms_output.put_line('IND_CREDITO'||vIndCredito);

     OPEN curCaj FOR
       SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')    || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND   (  vTomaCondicion ='FALSE'  or FORMA_PAGO.IND_TARJ = vIndComparar )
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO--;
    ----
    union
    ----
    ---CARGA LA FORMA DE PAGO DE CREDITO SI
    --EL CONVENIO DA CREDITO
         SELECT FORMA_PAGO.COD_FORMA_PAGO || 'Ã' ||
         FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
         NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
         NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
         NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
         NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM  VTA_FORMA_PAGO FORMA_PAGO,
            VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
            CON_MAE_CONVENIO  CV
      WHERE
             vIndCredito = 'S'
      AND    FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO = cCodConvenio
      AND    CV.PORC_COPAGO_CONV > 0
      AND    FORMA_PAGO.COD_CONVENIO = CV.COD_CONVENIO
      AND     FORMA_PAGO.COD_GRUPO_CIA     = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO    = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;

--
    ELSIF   vIndTarjeta = 'N' and vIndEfectivo = 'N' THEN

       SELECT COUNT(1) INTO X
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
             con_convenio_x_forma_pago    c
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      AND    FORMA_PAGO.COD_GRUPO_CIA = C.COD_GRUPO_CIA
      AND    FORMA_PAGO.COD_FORMA_PAGO = C.COD_FORMA_PAGO
      AND    C.COD_CONVENIO  = cCodConvenio;

    IF X >0 THEN

    OPEN curCaj FOR
       SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
             con_convenio_x_forma_pago    c
      WHERE
             FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      AND    FORMA_PAGO.COD_GRUPO_CIA = C.COD_GRUPO_CIA
      AND    FORMA_PAGO.COD_FORMA_PAGO = C.COD_FORMA_PAGO
      AND    C.COD_CONVENIO  = cCodConvenio--;
    union
    ---CARGA LA FORMA DE PAGO DE CREDITO SI
    --EL CONVENIO DA CREDITO
         SELECT FORMA_PAGO.COD_FORMA_PAGO || 'Ã' ||
         FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
         NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
         NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
         NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
         NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM  VTA_FORMA_PAGO FORMA_PAGO,
            VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
            CON_MAE_CONVENIO  CV
      WHERE
             vIndCredito = 'S'
      AND    FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO = cCodConvenio
      AND    CV.PORC_COPAGO_CONV > 0
      AND    FORMA_PAGO.COD_CONVENIO = CV.COD_CONVENIO
      AND     FORMA_PAGO.COD_GRUPO_CIA     = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO    = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;

    ELSE
    --JCORTEZ 12/03/2008 Solo forma de pago por covenio
    SELECT COUNT(*) INTO CANT
    FROM con_convenio_x_forma_pago
    WHERE COD_CONVENIO=cCodConvenio;

    IF (CANT>0)THEN

     OPEN curCaj FOR
      SELECT FORMA_PAGO.COD_FORMA_PAGO      || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO  || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ')  || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')      || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO =cCodConvenio
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;
    ELSE
        OPEN curCaj FOR
                  SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
             FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
             VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
      WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO IS NULL
      AND     FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO--;
    union
    ---CARGA LA FORMA DE PAGO DE CREDITO SI
    --EL CONVENIO DA CREDITO
         SELECT FORMA_PAGO.COD_FORMA_PAGO || 'Ã' ||
         FORMA_PAGO.DESC_CORTA_FORMA_PAGO || 'Ã' ||
         NVL(FORMA_PAGO.COD_OPE_TARJ,' ') || 'Ã' ||
         NVL(FORMA_PAGO.IND_TARJ,'N')     || 'Ã' ||
         NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N')|| 'Ã' ||
         NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM  VTA_FORMA_PAGO FORMA_PAGO,
            VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL,
            CON_MAE_CONVENIO  CV
      WHERE
             vIndCredito = 'S'
      AND    FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
      AND     FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
      AND     FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
      AND    FORMA_PAGO.EST_FORMA_PAGO             = ESTADO_ACTIVO
      AND    FORMA_PAGO.COD_CONVENIO = cCodConvenio
      AND    CV.PORC_COPAGO_CONV > 0
      AND    FORMA_PAGO.COD_CONVENIO = CV.COD_CONVENIO
      AND     FORMA_PAGO.COD_GRUPO_CIA     = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
      AND     FORMA_PAGO.COD_FORMA_PAGO    = FORMA_PAGO_LOCAL.COD_FORMA_PAGO;
    END IF;

     END IF;
    END IF;
    --
    RETURN curCaj;

  END;



 /*********************************************************************************************************/
   FUNCTION CAJ_F_PROCESA_VALORES_ARQUEO(     cCodGrupoCia_in      IN CHAR,
                                               cCod_Local_in        IN CHAR,
                                              cTipMov_in           IN CHAR,
                                              nNumCaj_in            IN NUMBER,
                                              cSecUsu_in            IN CHAR,
                                              cIdUsu_in             IN CHAR,
                                              cSecMovCaja_in       IN CHAR,
                                              cIpMovCaja_in        IN CHAR,
                                              cTipOp_in            IN CHAR)

  RETURN CHAR
    IS
      flag    CHAR(2);
      tipComp CHAR(2);


      codigoGenerado_SecMovCaja  CHAR(10);
      v_codigoGenerado_SecMovCaja  CHAR(10);
      cantBoletasGen  NUMBER ;
      boletasGen      NUMBER (8,2);
      cantFacturasGen NUMBER :=0;
      facturasGen     NUMBER (8,2):=0.0;
      cantGuiasGen    NUMBER ;
      guiasGen        NUMBER (8,2):=0.0;
      cantBoletasAnu  NUMBER :=0;
      boletasAnu      NUMBER (8,2):=0.0;
      cantFacturasAnu NUMBER ;
      facturasAnu     NUMBER (8,2):=0.0;
      cantGuiasAnu    NUMBER :=0;
      guiasAnu        NUMBER (8,2):=0.0;
      cantNCBoletas   NUMBER :=0;
      cantNCFacturas  NUMBER :=0;
      ncBoletas       NUMBER (8,2):=0.0;
      ncFacturas      NUMBER (8,2):=0.0;

      ---  totales
      totGenerados    NUMBER (8,2):=0.0;
      totAnulados     NUMBER (8,2):=0.0;
      totNCredito     NUMBER (8,2):=0.0;
      totCompras      NUMBER (8,2):=0.0;
      cantBoletasTot  NUMBER;
      cantFacturasTot NUMBER;
      cantGuiasTot    NUMBER;
      boletasTotal    NUMBER;
      facturasTotal   NUMBER;
      guiasTotal      NUMBER;
      TOTAL_CAJA_TURNO NUMBER (8,2):=0.0;
      TOTAL_MONTO_SISTEMA NUMBER (8,2):=0.0;

      -- mensajes
      cMensaje   varchar (550);
      cVerifique varchar (600);
      v_vDescLocal varchar (150);
      v_vNombreCajero varchar (150);

     v_vFecha varchar(100);


/*    CURSOR curDet IS

        SELECT 'N' FLAG , VP.TIP_COMP_PAGO , COUNT(VP.SEC_COMP_PAGO) CANTIDAD,
               SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO) TOTAL_NETO_REDONDEO
          FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
         WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VP.COD_LOCAL = cCod_Local_in
           AND VPC.SEC_MOV_CAJA = cSecMovCaja_in
           AND VPC.EST_PED_VTA = EST_PED_COBRADO
           AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
           AND vpc.cod_local = vp.cod_local
           AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
         GROUP BY 'N', VP.TIP_COMP_PAGO

        UNION

        SELECT 'S' FLAG, VP.TIP_COMP_PAGO , COUNT(VP.SEC_COMP_PAGO) CANTIDAD,
               SUM(VP.VAL_NETO_COMP_PAGO + VAL_REDONDEO_COMP_PAGO) TOTAL_NETO_REDONDEO
          FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
         WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VP.COD_LOCAL = cCod_Local_in
           AND VPC.SEC_MOV_CAJA = cSecMovCaja_in
           AND vpc.EST_PED_VTA = EST_PED_COBRADO
           AND VP.IND_COMP_ANUL = INDICADOR_SI
           AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
           AND vpc.cod_local = vp.cod_local
           AND VPC.NUM_PED_VTA = VP.NUM_PEDIDO_ANUL
         GROUP BY 'S', VP.TIP_COMP_PAGO

        UNION

        SELECT 'NC' FLAG , VPC2.TIP_COMP_PAGO , COUNT(VPC.NUM_PED_VTA) CANTIDAD,
               SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO) TOTAL_NETO_REDONDEO
          FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
         WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VPC.COD_LOCAL = cCod_Local_in
           AND VPC.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED
           AND VPC.EST_PED_VTA = EST_PED_COBRADO
           AND VPC2.TIP_COMP_PAGO = COD_TIP_COMP_BOLETA
           AND VPC.SEC_MOV_CAJA = cSecMovCaja_in
           AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
           AND VPC.COD_LOCAL = VPC2.COD_LOCAL
           AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
           AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
           AND VCP.COD_LOCAL = VPC.COD_LOCAL
           AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA

         GROUP BY VPC2.TIP_COMP_PAGO

        UNION

        SELECT 'NC' FLAG,VPC2.TIP_COMP_PAGO , COUNT(VPC.NUM_PED_VTA) CANTIDAD,
               SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO) TOTAL_NETO_REDONDEO
          FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
         WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VPC.COD_LOCAL = cCod_Local_in
           AND VPC.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED
           AND VPC.EST_PED_VTA = EST_PED_COBRADO
           AND VPC2.TIP_COMP_PAGO = COD_TIP_COMP_FACTURA
           AND VPC.SEC_MOV_CAJA = cSecMovCaja_in
           AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
           AND VPC.COD_LOCAL = VPC2.COD_LOCAL
           AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
           AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
           AND VCP.COD_LOCAL = VPC.COD_LOCAL
           AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA

         GROUP BY VPC2.TIP_COMP_PAGO;
*/
        BEGIN


/*          FOR v_rCurDetm IN curDet
          LOOP
          BEGIN
          flag :=  v_rCurDetm.Flag;
          tipComp := v_rCurDetm.Tip_Comp_Pago;



                    IF flag = 'N' AND   tipComp ='01' THEN --BOLETA

                    cantBoletasGen := v_rCurDetm.Cantidad ;
                    boletasGen := v_rCurDetm.Total_Neto_Redondeo;



                    ELSIF flag = 'N' AND   tipComp  ='02' THEN --FACTURA

                    cantFacturasGen :=  v_rCurDetm.Cantidad;
                    facturasGen := v_rCurDetm.Total_Neto_Redondeo;

                   ELSIF flag = 'N' AND  tipComp = '03' THEN --GUIA

                    cantGuiasGen :=  v_rCurDetm.Cantidad;
                    guiasGen :=      v_rCurDetm.Total_Neto_Redondeo;
                    END IF;


                  IF  flag ='NC' AND tipComp = '01' THEN --BOLETA

                    cantNCBoletas:= v_rCurDetm.Cantidad ;
                    ncBoletas := v_rCurDetm.Total_Neto_Redondeo;

                 ELSIF flag ='NC' AND tipComp = '02' THEN --FACTURA

                    cantNCFacturas :=  v_rCurDetm.Cantidad;
                    ncFacturas := v_rCurDetm.Total_Neto_Redondeo;

                END IF;



             IF  flag ='S' AND tipComp = '01' THEN --BOLETA

                    cantBoletasAnu:= v_rCurDetm.Cantidad ;
                    boletasAnu := v_rCurDetm.Total_Neto_Redondeo;

             ELSIF flag ='S' AND tipComp = '02' THEN --FACTURA

                    cantFacturasAnu :=  v_rCurDetm.Cantidad;
                    facturasAnu := v_rCurDetm.Total_Neto_Redondeo;

             ELSIF flag ='S'AND tipComp = '03' THEN --GUIA

                    cantGuiasAnu :=  v_rCurDetm.Cantidad;
                    guiasAnu :=      v_rCurDetm.Total_Neto_Redondeo;

              END IF;


          END;
          END LOOP;

     --  DBMS_OUTPUT.put_line('boletasGen:'||boletasGen);
     --calculando totales
      totGenerados := totGenerados +  boletasGen;
      totGenerados := totGenerados +  facturasGen;
      totGenerados := totGenerados +  guiasGen;

      totAnulados := totAnulados  + boletasAnu;
      totAnulados := totAnulados  + facturasAnu;
      totAnulados := totAnulados + guiasAnu;

      totNCredito := totNCredito  + ncBoletas;
      totNCredito := totNCredito  + ncFacturas;


     cantBoletasTot  := cantBoletasGen - cantBoletasAnu;
     cantFacturasTot := cantFacturasGen - cantFacturasAnu;
      cantGuiasTot  := cantGuiasGen - cantGuiasAnu;
      boletasTotal  := boletasGen - boletasAnu ;
      facturasTotal := facturasGen - facturasAnu;
      guiasTotal := guiasGen - guiasAnu;

    totCompras := (totGenerados - totAnulados) + totNCredito;
    --  totCompras := 500;
*/

        SELECT to_char(fec_dia_vta, 'dd/mm/yyyy')
          into v_vFecha
          FROM CE_MOV_CAJA C
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND COD_LOCAL = cCod_Local_in
           AND SEC_MOV_CAJA = cSecMovCaja_in
           AND TIP_MOV_CAJA = 'A';


      IF cTipMov_in = 'C' THEN--TIPO DE CAJA CIERRE

           codigoGenerado_SecMovCaja := CAJ_REGISTRA_ARQUEO_CAJA(cTipMov_in,
                                                                 cCodGrupoCia_in ,
                                                                 cCod_Local_in,
                                                                 nNumCaj_in ,
                                                                 cSecUsu_in ,
                                                                 cIdUsu_in  ,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 0,
                                                                 cIpMovCaja_in,
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null);


           UPDATE CE_MOV_CAJA MM
           SET (MM.CANT_BOL_EMI, MM.MON_BOL_EMI) =
                    (
                     /*SELECT COUNT(*) CANTIDAD,
                              SUM(C.VAL_NETO_PED_VTA) TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NULL
                         AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND C.TIP_COMP_PAGO = '01'*/

                      SELECT COUNT(*) CANTIDAD,
                             NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                      FROM VTA_COMP_PAGO CP
                      WHERE TIP_COMP_PAGO='01'
                        AND EXISTS
                            (SELECT 1
                             FROM VTA_PEDIDO_VTA_CAB C
                             WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                               AND C.COD_LOCAL = cCod_Local_in
                               AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                         TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                               AND C.EST_PED_VTA = 'C'
                               AND C.NUM_PED_VTA_ORIGEN IS NULL
                               AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                               AND C.SEC_MOV_CAJA=CP.SEC_MOV_CAJA)
                    ),
               (MM.CANT_TICK_EMI, MM.MON_TICK_EMI) =
                    (
                     /*SELECT COUNT(*) CANTIDAD,
                              SUM(C.VAL_NETO_PED_VTA) TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NULL
                         AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND C.TIP_COMP_PAGO = '05'*/

                       SELECT COUNT(*) CANTIDAD,
                              NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE TIP_COMP_PAGO='05'
                         AND EXISTS
                             (SELECT 1
                              FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.SEC_MOV_CAJA=CP.SEC_MOV_CAJA)
                    ),
               --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
               (MM.CANT_TICK_FAC_EMI, MM.MON_TICK_FAC_EMI) =
                    (
                       SELECT COUNT(*) CANTIDAD,
                              NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE TIP_COMP_PAGO='06'
                         AND EXISTS
                             (SELECT 1
                              FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.SEC_MOV_CAJA=CP.SEC_MOV_CAJA)
                    ),
               --FIN LLEIVA
               (MM.CANT_FACT_EMI, MM.MON_FACT_EMI) =
                    (
                     /*SELECT COUNT(*) CANTIDAD,
                              NVL(SUM(C.VAL_NETO_PED_VTA),0) TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NULL
                         AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND C.TIP_COMP_PAGO = '02'*/

                       SELECT COUNT(*) CANTIDAD,
                              NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE TIP_COMP_PAGO='02'
                         AND EXISTS
                             (SELECT 1
                              FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.SEC_MOV_CAJA=CP.SEC_MOV_CAJA)
                    ),
               (MM.CANT_GUIA_EMI, MM.MON_GUIA_EMI) =
                    (
                     /*SELECT COUNT(*) CANTIDAD,
                              NVL(SUM(C.VAL_NETO_PED_VTA),0) TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NULL
                         AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND C.TIP_COMP_PAGO = '03'*/

                       SELECT COUNT(*) CANTIDAD,
                              NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE TIP_COMP_PAGO='03'
                         AND EXISTS
                             (SELECT 1
                              FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.SEC_MOV_CAJA=CP.SEC_MOV_CAJA)
                    ),
               (MM.CANT_BOL_ANU ) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD
                       FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '01'
                    ),
               (MM.MON_BOL_ANU) =
                    (
                     /*SELECT NVL(SUM(C.VAL_NETO_PED_VTA),0)*-1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS not NULL
                         AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND C.TIP_COMP_PAGO = '01'*/

                       SELECT NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE TIP_COMP_PAGO='01'
                         AND EXISTS
                             (SELECT 1
                              FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.COD_GRUPO_CIA=CP.COD_GRUPO_CIA
                                AND C.COD_LOCAL=CP.COD_LOCAL
                                AND C.NUM_PED_VTA_ORIGEN=CP.NUM_PED_VTA)
                         AND CP.IND_COMP_ANUL='S'
                    ),
               (MM.CANT_TICK_ANU) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD
                       FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '05'
                    ),
               (MM.MON_TICK_ANU) =
                    (
                     /*SELECT NVL(SUM(C.VAL_NETO_PED_VTA),0)*-1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS not NULL
                         AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND C.TIP_COMP_PAGO = '05'*/

                       SELECT NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE TIP_COMP_PAGO='05'
                         AND EXISTS
                             (SELECT 1
                              FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.COD_GRUPO_CIA=CP.COD_GRUPO_CIA
                                AND C.COD_LOCAL=CP.COD_LOCAL
                                AND C.NUM_PED_VTA_ORIGEN=CP.NUM_PED_VTA)
                         AND CP.IND_COMP_ANUL='S'
                    ),
               --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
               (MM.CANT_TICK_FAC_ANUL) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD
                       FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '06'
                    ),
               (MM.MON_TICK_FACK_ANUL) =
                    (
                       SELECT NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE TIP_COMP_PAGO='06'
                         AND EXISTS
                             (SELECT 1 FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.COD_GRUPO_CIA=CP.COD_GRUPO_CIA
                                AND C.COD_LOCAL=CP.COD_LOCAL
                                AND C.NUM_PED_VTA_ORIGEN=CP.NUM_PED_VTA)
                                AND CP.IND_COMP_ANUL='S'
                    ),
               --FIN LLEIVA
               (MM.CANT_FACT_ANU) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD
                       FROM VTA_COMP_PAGO VP,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '02'
                    ),
               (MM.MON_FACT_ANU) =
                    (
                     /*SELECT NVL(SUM(C.VAL_NETO_PED_VTA),0)*-1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS not NULL
                         AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND C.TIP_COMP_PAGO = '02'*/

                       SELECT NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE TIP_COMP_PAGO='02'
                         AND EXISTS
                             (SELECT 1
                              FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.COD_GRUPO_CIA=CP.COD_GRUPO_CIA
                                AND C.COD_LOCAL=CP.COD_LOCAL
                                AND C.NUM_PED_VTA_ORIGEN=CP.NUM_PED_VTA)
                         AND CP.IND_COMP_ANUL='S'
                    ),
               (MM.CANT_GUIA_ANU) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD
                       FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '03'
                    ),
               (MM.MON_GUIA_ANU) =
                    (
                       SELECT NVL(SUM(CP.VAL_NETO_COMP_PAGO+CP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO CP
                       WHERE  TIP_COMP_PAGO='03'
                         AND EXISTS
                             (SELECT 1
                              FROM VTA_PEDIDO_VTA_CAB C
                              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND C.COD_LOCAL = cCod_Local_in
                                AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                          TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                                AND C.EST_PED_VTA = 'C'
                                AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                                AND C.SEC_MOV_CAJA=MM.SEC_MOV_CAJA_ORIGEN
                                AND C.COD_GRUPO_CIA=CP.COD_GRUPO_CIA
                                AND C.COD_LOCAL=CP.COD_LOCAL
                                AND C.NUM_PED_VTA_ORIGEN=CP.NUM_PED_VTA)
                         AND CP.IND_COMP_ANUL='S'
                    ),
               (MM.CANT_NC_BOLETAS, MM.MON_NC_BOLETAS) =
                    (  --COMENTADO DEBIDO A QUE NO ABARCA PARA NC DE CONEVENIO 
                       -- CON 2 COMPROBANTES QUE MUEVEN EFECTIVO
                       ---CIERRE DE TURNO
                       /*SELECT COUNT(VPC.NUM_PED_VTA) CANTIDAD,
                               NVL(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),0) * -1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
                       WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VPC.COD_LOCAL = cCod_Local_in
                         AND VPC.TIP_COMP_PAGO = '04'
                         AND VPC.EST_PED_VTA = 'C'
                         AND VPC2.TIP_COMP_PAGO = '01'
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
                         AND VPC.COD_LOCAL = VPC2.COD_LOCAL
                         AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
                         AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
                         AND VCP.COD_LOCAL = VPC.COD_LOCAL
                         AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA*/
                --RHERRERA 21.11.2014 
                -- utizamos el secunecia de comprobante origen que generaron las notas
                -- de credito
                SELECT COUNT(VPC.NUM_PED_VTA) CANTIDAD,
                       NVL(SUM(VPC.VAL_NETO_COMP_PAGO + VPC.VAL_REDONDEO_COMP_PAGO), 0) * -1 TOTAL_NETO_REDONDEO
                  FROM VTA_COMP_PAGO VPC, VTA_PEDIDO_VTA_CAB C
                 WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND VPC.COD_LOCAL = cCod_Local_in
                   AND C.TIP_COMP_PAGO = '04'
                   AND VPC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                   AND VPC.COD_LOCAL = C.COD_LOCAL
                   AND VPC.NUM_PED_VTA = C.NUM_PED_VTA
                   AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                   AND EXISTS (SELECT 1 
                          FROM VTA_COMP_PAGO P
                         WHERE P.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                           AND P.COD_LOCAL = C.COD_LOCAL
                           AND P.TIP_COMP_PAGO = '01'
                           AND P.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                           AND P.SEC_COMP_PAGO = C.SEC_COMP_PAGO)
                    ),
               (MM.CANT_NC_TICKETS, MM.MON_NC_TICKETS) =
                    (
                       --ERIOS 2.4.3 Se calcula el valor para NC de tickets y guias
            SELECT COUNT(1) CANTIDAD,NVL(SUM(VCP2.VAL_NETO_COMP_PAGO+VCP2.val_redondeo_comp_pago),0) TOTAL_NETO_REDONDEO--,VCP2.TIP_COMP_PAGO
            FROM  (
            SELECT DISTINCT VPC.COD_GRUPO_CIA,VPC.COD_LOCAL,VPC.NUM_PED_VTA_ORIGEN,VPC.sec_mov_caja
            FROM VTA_COMP_PAGO VCP JOIN VTA_PEDIDO_VTA_CAB VPC ON (VCP.COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND VCP.COD_LOCAL = cCod_Local_in
                                        AND VCP.TIP_COMP_PAGO = '04'
                                        AND VPC.COD_GRUPO_CIA = VCP.COD_GRUPO_CIA
                                        AND VPC.COD_LOCAL = VCP.COD_LOCAL
                                        AND VPC.NUM_PED_VTA = VCP.NUM_PED_VTA
                                        --AND VPC.sec_mov_caja = MM.SEC_MOV_CAJA_ORIGEN
                                        )
            ) VPC1
            JOIN  VTA_COMP_PAGO VCP2     ON (VCP2.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND VCP2.COD_LOCAL = cCod_Local_in
                              AND VCP2.TIP_COMP_PAGO IN ('05')
                              AND VCP2.COD_GRUPO_CIA = VPC1.COD_GRUPO_CIA
                              AND VCP2.COD_LOCAL = VPC1.COD_LOCAL
                              AND VCP2.NUM_PED_VTA = VPC1.NUM_PED_VTA_ORIGEN
                              )
                              WHERE VPC1.sec_mov_caja = MM.SEC_MOV_CAJA_ORIGEN
            --GROUP BY VCP2.TIP_COMP_PAGO
                    ),
               --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
               (MM.CANT_NC_TICKETS_FAC, MM.MON_NC_TICKETS_FAC) =
                    (
                       SELECT COUNT(VPC.NUM_PED_VTA) CANTIDAD,
                              NVL(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),0) * -1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
                       WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VPC.COD_LOCAL = cCod_Local_in
                         AND VPC.TIP_COMP_PAGO = '04'
                         AND VPC.EST_PED_VTA = 'C'
                         AND VPC2.TIP_COMP_PAGO = '06'
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA_ORIGEN
                         AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
                         AND VPC.COD_LOCAL = VPC2.COD_LOCAL
                         AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
                         AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
                         AND VCP.COD_LOCAL = VPC.COD_LOCAL
                         AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA
                    ),
               --FIN LLEIVA
               (MM.CANT_NC_FACT, MM.MON_NC_FACT) =
                    (
            --ERIOS 2.4.6 Consulta por factura
                       SELECT COUNT(1) CANTIDAD, NVL(SUM(VCP2.VAL_NETO_COMP_PAGO+VCP2.val_redondeo_comp_pago),0) TOTAL_NETO_REDONDEO
            FROM  (
            SELECT DISTINCT VPC.COD_GRUPO_CIA,VPC.COD_LOCAL,VPC.NUM_PED_VTA_ORIGEN,VPC.sec_mov_caja
            FROM VTA_COMP_PAGO VCP JOIN VTA_PEDIDO_VTA_CAB VPC ON (VCP.COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND VCP.COD_LOCAL = cCod_Local_in
                                        AND VCP.TIP_COMP_PAGO = '04'
                                        AND VPC.COD_GRUPO_CIA = VCP.COD_GRUPO_CIA
                                        AND VPC.COD_LOCAL = VCP.COD_LOCAL
                                        AND VPC.NUM_PED_VTA = VCP.NUM_PED_VTA
                                        )
            ) VPC1
            JOIN  VTA_COMP_PAGO VCP2     ON (VCP2.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND VCP2.COD_LOCAL = cCod_Local_in
                              AND VCP2.TIP_COMP_PAGO IN ('02')
                              AND VCP2.COD_GRUPO_CIA = VPC1.COD_GRUPO_CIA
                              AND VCP2.COD_LOCAL = VPC1.COD_LOCAL
                              AND VCP2.NUM_PED_VTA = VPC1.NUM_PED_VTA_ORIGEN
                              )
                              WHERE VPC1.sec_mov_caja = MM.SEC_MOV_CAJA_ORIGEN
                    )
               WHERE MM.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND MM.COD_LOCAL = cCod_Local_in
                 AND MM.SEC_MOV_CAJA = codigoGenerado_SecMovCaja;

           UPDATE CE_MOV_CAJA MM
           SET MM.CANT_BOL_TOT      = nvl(MM.CANT_BOL_EMI,0) - nvl(MM.CANT_BOL_ANU,0) ,
               MM.MON_BOL_TOT       = nvl(MM.MON_BOL_EMI,0) - nvl(MM.MON_BOL_ANU,0) ,
               MM.CANT_TICK_TOT     = nvl(MM.CANT_TICK_EMI,0) - nvl(MM.CANT_TICK_ANU,0) ,
               MM.MON_TICK_TOT      = nvl(MM.MON_TICK_EMI,0) - nvl(MM.MON_TICK_ANU,0) ,
               --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
               MM.CANT_TICK_FAC_TOT = nvl(MM.CANT_TICK_FAC_EMI,0) - nvl(MM.CANT_TICK_FAC_ANUL,0),
               MM.MON_TICK_FAC_TOT  = nvl(MM.MON_TICK_FAC_EMI,0) - nvl(MM.MON_TICK_FACK_ANUL,0),
               --FIN LLEIVA
               MM.CANT_FACT_TOT     = nvl(MM.CANT_FACT_EMI,0) - nvl(MM.CANT_FACT_ANU,0),
               MM.MON_FACT_TOT      = nvl(MM.MON_FACT_EMI,0) - nvl(MM.MON_FACT_ANU,0),
               MM.CANT_GUIA_TOT     = nvl(MM.CANT_GUIA_EMI,0) - nvl(MM.CANT_GUIA_ANU,0) ,
               MM.MON_GUIA_TOT      = nvl(MM.MON_GUIA_EMI,0) - nvl(MM.MON_GUIA_ANU,0) ,
               MM.MON_TOT_NC        = nvl(MM.MON_NC_BOLETAS,0) + nvl(MM.MON_NC_FACT,0) + nvl(MM.Mon_Nc_Tickets,0)
           WHERE MM.COD_GRUPO_CIA = cCodGrupoCia_in
             AND MM.COD_LOCAL = cCod_Local_in
             AND MM.SEC_MOV_CAJA =codigoGenerado_SecMovCaja;

           UPDATE CE_MOV_CAJA MM
           SET MM.MON_TOT_GEN = nvl(MM.MON_BOL_EMI,0) +
                                nvl(MM.MON_FACT_EMI,0) +
                                nvl(MM.MON_TICK_EMI,0)+
                                nvl(MM.MON_GUIA_EMI,0)
                                --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
                                + nvl(MM.MON_TICK_FAC_EMI,0)
                                ,
               MM.MON_TOT_ANU = nvl(MM.MON_BOL_ANU,0) +
                                nvl(MM.MON_FACT_ANU,0) +
                                nvl(MM.MON_TICK_ANU,0)+
                                nvl(MM.MON_GUIA_ANU,0)
                                --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
                                + nvl(MM.MON_TICK_FACK_ANUL,0)
           WHERE MM.COD_GRUPO_CIA = cCodGrupoCia_in
             AND MM.COD_LOCAL = cCod_Local_in
             AND MM.SEC_MOV_CAJA = codigoGenerado_SecMovCaja;


           UPDATE CE_MOV_CAJA MM
           SET MM.MON_TOT = nvl(MM.MON_TOT_GEN,0) -
                            nvl(MM.MON_TOT_ANU,0) -
                            nvl(MM.MON_TOT_NC,0),
               MM.FEC_MOD_MOV_CAJA = SYSDATE
           WHERE MM.COD_GRUPO_CIA = cCodGrupoCia_in
             AND MM.COD_LOCAL = cCod_Local_in
             AND MM.SEC_MOV_CAJA = codigoGenerado_SecMovCaja;

          IF codigoGenerado_SecMovCaja IS NOT NULL THEN
               CAJ_ALMACENAR_VALORES_COMP(cCodGrupoCia_in,cCod_Local_in,codigoGenerado_SecMovCaja,cTipOp_in);
          END IF;

          ----CALCULOS PARA VERIFICAR MONTOS
          -- Suma de todos los pedidos cobrados por el cajero
          SELECT SUM(C.VAL_NETO_PED_VTA) MONTO_CJA
                 INTO  TOTAL_CAJA_TURNO
          FROM  VTA_PEDIDO_VTA_CAB C
          WHERE C.EST_PED_VTA = EST_PED_COBRADO
            AND C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCod_Local_in
            AND C.SEC_MOV_CAJA = codigoGenerado_SecMovCaja; --APERTURA,ORIGEN

          --Suma de los montos de la forma de pago que el sistema calculó
          SELECT SUM(C.IM_TOTAL) MONTO_SISTEMA
                 INTO TOTAL_MONTO_SISTEMA
          FROM  CE_MOV_CAJA_FORMA_PAGO_SIST C
          WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCod_Local_in
            AND C.SEC_MOV_CAJA = codigoGenerado_SecMovCaja;--CIERRE

          --ASIGNANDO VALOR  A VARIABLE DE CIERRE DE CAJA
          v_codigoGenerado_SecMovCaja := codigoGenerado_SecMovCaja;

      ELSIF cTipMov_in = 'U' THEN --ACTUALIZAR CIERRE DE CAJA

           --PENDIENTE DE CAMBIO
           UPDATE CE_MOV_CAJA MM
           SET (MM.CANT_BOL_EMI, MM.MON_BOL_EMI) =
                    (
                       SELECT COUNT(*) CANTIDAD,
                              SUM(C.VAL_NETO_PED_VTA) TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NULL
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND C.TIP_COMP_PAGO = '01'
                    ),
               (MM.CANT_TICK_EMI, MM.MON_TICK_EMI) =
                    (
                       SELECT COUNT(*) CANTIDAD,
                              SUM(C.VAL_NETO_PED_VTA) TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NULL
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND C.TIP_COMP_PAGO = '05'
                    ),
               --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
               (MM.CANT_TICK_FAC_EMI, MM.MON_TICK_FAC_EMI) =
                    (
                       SELECT COUNT(*) CANTIDAD,
                              SUM(C.VAL_NETO_PED_VTA) TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NULL
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND C.TIP_COMP_PAGO = '06'
                    ),
               --FIN LLEIVA
               (MM.CANT_FACT_EMI, MM.MON_FACT_EMI) =
                    (
                       SELECT COUNT(*) CANTIDAD,
                              NVL(SUM(C.VAL_NETO_PED_VTA),0) TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C ,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NULL
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND C.TIP_COMP_PAGO = '02'
                    ),
               (MM.CANT_BOL_ANU) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD--,
                              --NVL(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO VP,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '01'
                    ),
               (MM.MON_BOL_ANU) =
                    (
                       SELECT NVL(SUM(C.VAL_NETO_PED_VTA),0)*-1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND C.TIP_COMP_PAGO = '01'
                    ),
               (MM.CANT_TICK_ANU) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD--,
                              --NVL(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO VP,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '05'
                    ),
               (MM.MON_TICK_ANU) =
                    (
                       SELECT --COUNT(*) CANTIDAD,
                              NVL(SUM(C.VAL_NETO_PED_VTA),0)*-1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND C.TIP_COMP_PAGO = '05'
                    ),
               --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
               (MM.CANT_TICK_FAC_ANUL) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD--,
                              --NVL(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO VP,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '06'
                    ),
               (MM.MON_TICK_FACK_ANUL) =
                    (
                       SELECT --COUNT(*) CANTIDAD,
                              NVL(SUM(C.VAL_NETO_PED_VTA),0)*-1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND C.TIP_COMP_PAGO = '06'
                    ),
               --FIN LLEIVA
               (MM.CANT_FACT_ANU) =
                    (
                       SELECT COUNT(VP.SEC_COMP_PAGO) CANTIDAD--,
                              --NVL(SUM(VP.VAL_NETO_COMP_PAGO + VP.VAL_REDONDEO_COMP_PAGO),0) TOTAL_NETO_REDONDEO
                       FROM VTA_COMP_PAGO VP, VTA_PEDIDO_VTA_CAB VPC
                       WHERE VP.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VP.COD_LOCAL = cCod_Local_in
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND VPC.EST_PED_VTA = 'C'
                         AND VP.IND_COMP_ANUL = 'S'
                         AND vpc.cod_grupo_cia = vp.COD_GRUPO_CIA
                         AND vpc.cod_local = vp.cod_local
                         AND VP.NUM_PED_VTA = VPC.NUM_PED_VTA
                         AND VP.TIP_COMP_PAGO = '02'
                    ),
               (MM.MON_FACT_ANU) =
                    (
                       SELECT --COUNT(*) CANTIDAD,
                              NVL(SUM(C.VAL_NETO_PED_VTA),0)*-1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB C,
                            VTA_PEDIDO_VTA_CAB VPC
                       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND C.COD_LOCAL = cCod_Local_in
                         AND C.FEC_PED_VTA BETWEEN TO_DATE(v_vFecha || ' 00:00:00','DD/MM/YYYY HH24:MI:SS') AND
                                                   TO_DATE(v_vFecha || ' 23:59:59','DD/MM/YYYY HH24:MI:SS')
                         AND C.EST_PED_VTA = 'C'
                         AND C.NUM_PED_VTA_ORIGEN IS NOT NULL
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND C.TIP_COMP_PAGO = '02'
                    ),
               (MM.CANT_NC_BOLETAS, MM.MON_NC_BOLETAS) =
                    (
                       /*SELECT COUNT(VPC.NUM_PED_VTA) CANTIDAD,
                              NVL(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),0) * -1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
                       WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VPC.COD_LOCAL = cCod_Local_in
                         AND VPC.TIP_COMP_PAGO = '04'
                         AND VPC.EST_PED_VTA = 'C'
                         AND VPC2.TIP_COMP_PAGO = '01'
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
                         AND VPC.COD_LOCAL = VPC2.COD_LOCAL
                         AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
                         AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
                         AND VCP.COD_LOCAL = VPC.COD_LOCAL
                         AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA*/
                       SELECT COUNT(VPC.NUM_PED_VTA) CANTIDAD,
                              NVL(SUM(VPC.VAL_NETO_COMP_PAGO +
                                      VPC.VAL_REDONDEO_COMP_PAGO),
                                  0) * -1 TOTAL_NETO_REDONDEO
                         FROM VTA_COMP_PAGO VPC, VTA_PEDIDO_VTA_CAB C
                        WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND VPC.COD_LOCAL = cCod_Local_in
                          AND C.TIP_COMP_PAGO = '04'
                          AND VPC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                          AND VPC.COD_LOCAL = C.COD_LOCAL
                          AND VPC.NUM_PED_VTA = C.NUM_PED_VTA
                          AND C.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                          AND EXISTS
                        (SELECT 1
                                 FROM VTA_COMP_PAGO P
                                WHERE P.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                  AND P.COD_LOCAL = C.COD_LOCAL
                                  AND P.TIP_COMP_PAGO = '01'
                                  AND P.NUM_PED_VTA = C.NUM_PED_VTA_ORIGEN
                                  AND P.SEC_COMP_PAGO = C.SEC_COMP_PAGO)
                    ),
               (MM.CANT_NC_TICKETS, MM.MON_NC_TICKETS) =
                    (
                       SELECT COUNT(VPC.NUM_PED_VTA) CANTIDAD,
                              NVL(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),0) * -1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
                       WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VPC.COD_LOCAL = cCod_Local_in
                         AND VPC.TIP_COMP_PAGO = '04'
                         AND VPC.EST_PED_VTA = 'C'
                         AND VPC2.TIP_COMP_PAGO = '05'
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
                         AND VPC.COD_LOCAL = VPC2.COD_LOCAL
                         AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
                         AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
                         AND VCP.COD_LOCAL = VPC.COD_LOCAL
                         AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA
                    ),
               --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
               (MM.CANT_NC_TICKETS_FAC, MM.MON_NC_TICKETS_FAC) =
                    (
                       SELECT COUNT(VPC.NUM_PED_VTA) CANTIDAD,
                              NVL(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),0) * -1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB VPC,
                            VTA_PEDIDO_VTA_CAB VPC2,
                            VTA_COMP_PAGO VCP
                       WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VPC.COD_LOCAL = cCod_Local_in
                         AND VPC.TIP_COMP_PAGO = '04'
                         AND VPC.EST_PED_VTA = 'C'
                         AND VPC2.TIP_COMP_PAGO = '06'
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
                         AND VPC.COD_LOCAL = VPC2.COD_LOCAL
                         AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
                         AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
                         AND VCP.COD_LOCAL = VPC.COD_LOCAL
                         AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA
                    ),
               --FIN LLEIVA
               (MM.CANT_NC_FACT, MM.MON_NC_FACT) =
                    (
                       SELECT COUNT(VPC.NUM_PED_VTA) CANTIDAD,
                              NVL(SUM(VCP.VAL_NETO_COMP_PAGO + VCP.VAL_REDONDEO_COMP_PAGO),0) * -1 TOTAL_NETO_REDONDEO
                       FROM VTA_PEDIDO_VTA_CAB VPC, VTA_PEDIDO_VTA_CAB VPC2, VTA_COMP_PAGO VCP
                       WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                         AND VPC.COD_LOCAL = cCod_Local_in
                         AND VPC.TIP_COMP_PAGO = '04'
                         AND VPC.EST_PED_VTA = 'C'
                         AND VPC2.TIP_COMP_PAGO = '02'
                         AND VPC.SEC_MOV_CAJA = MM.SEC_MOV_CAJA
                         AND VPC.COD_GRUPO_CIA = VPC2.COD_GRUPO_CIA
                         AND VPC.COD_LOCAL = VPC2.COD_LOCAL
                         AND VPC.NUM_PED_VTA_ORIGEN = VPC2.NUM_PED_VTA
                         AND VCP.COD_GRUPO_CIA = VPC.COD_GRUPO_CIA
                         AND VCP.COD_LOCAL = VPC.COD_LOCAL
                         AND VCP.NUM_PED_VTA = VPC.NUM_PED_VTA
                    )
           WHERE MM.COD_GRUPO_CIA = cCodGrupoCia_in
             AND MM.COD_LOCAL = cCod_Local_in
             AND MM.SEC_MOV_CAJA = cSecMovCaja_in;

           UPDATE CE_MOV_CAJA MM
           SET MM.CANT_BOL_TOT = nvl(MM.CANT_BOL_EMI,0) - nvl(MM.CANT_BOL_ANU,0) ,
               MM.MON_BOL_TOT  = nvl(MM.MON_BOL_EMI,0) - nvl(MM.MON_BOL_ANU,0) ,
               MM.CANT_TICK_TOT = nvl(MM.CANT_TICK_EMI,0) - nvl(MM.CANT_TICK_ANU,0) ,
               MM.MON_TICK_TOT  = nvl(MM.MON_TICK_EMI,0) - nvl(MM.MON_TICK_ANU,0) ,
               --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
               MM.CANT_TICK_FAC_TOT = nvl(MM.CANT_TICK_FAC_EMI,0) - nvl(MM.CANT_TICK_FAC_ANUL,0),
               MM.MON_TICK_FAC_TOT  = nvl(MM.MON_TICK_FAC_EMI,0) - nvl(MM.MON_TICK_FACK_ANUL,0),
               --FIN LLEIVA
               MM.CANT_FACT_TOT = nvl(MM.CANT_FACT_EMI,0) - nvl(MM.CANT_FACT_ANU,0),
               MM.MON_FACT_TOT  = nvl(MM.MON_FACT_EMI,0) - nvl(MM.MON_FACT_ANU,0),
               MM.MON_TOT_NC    = nvl(MM.MON_NC_BOLETAS,0) + nvl(MM.MON_NC_FACT,0)
           WHERE MM.COD_GRUPO_CIA = cCodGrupoCia_in
             AND MM.COD_LOCAL = cCod_Local_in
             AND MM.SEC_MOV_CAJA = cSecMovCaja_in;

           UPDATE CE_MOV_CAJA MM
           SET MM.MON_TOT_GEN = nvl(MM.MON_BOL_EMI,0) +
                                nvl(MM.MON_FACT_EMI,0) +
                                nvl(MM.MON_TICK_EMI,0)
                                --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
                                + nvl(MM.MON_TICK_FAC_EMI,0)
                                ,
               MM.MON_TOT_ANU = nvl(MM.MON_BOL_ANU,0) +
                                nvl(MM.MON_FACT_ANU,0) +
                                nvl(MM.MON_TICK_ANU,0)
                                --LLEIVA 07-Feb-2014 Se añadio la actualizacion para Ticket Fact.
                                + nvl(MM.MON_TICK_FACK_ANUL ,0)
           WHERE MM.COD_GRUPO_CIA = cCodGrupoCia_in
             AND MM.COD_LOCAL = cCod_Local_in
             AND MM.SEC_MOV_CAJA = cSecMovCaja_in;

           UPDATE CE_MOV_CAJA MM
           SET MM.MON_TOT = MM.MON_TOT_GEN -
                            MM.MON_TOT_ANU -
                            MM.MON_TOT_NC,
               MM.FEC_MOD_MOV_CAJA = SYSDATE
           WHERE MM.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND MM.COD_LOCAL = cCod_Local_in
                 AND MM.SEC_MOV_CAJA = cSecMovCaja_in;

           ----CALCULOS PARA VERIFICAR MONTOS
           -- Suma de todos los pedidos cobrados por el cajero
           SELECT SUM(C.VAL_NETO_PED_VTA) MONTO_CJA
                  INTO  TOTAL_CAJA_TURNO
           FROM  VTA_PEDIDO_VTA_CAB C
           WHERE C.EST_PED_VTA = EST_PED_COBRADO --'C'
             AND C.COD_GRUPO_CIA = cCodGrupoCia_in
             AND C.COD_LOCAL = cCod_Local_in
             AND C.SEC_MOV_CAJA = cSecMovCaja_in; --APERTURA,ORIGEN

           --Suma de los montos de la forma de pago que el sistema calculó
           SELECT SUM(C.IM_TOTAL) MONTO_SISTEMA
                  INTO TOTAL_MONTO_SISTEMA
           FROM CE_MOV_CAJA_FORMA_PAGO_SIST C
           WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
             AND C.COD_LOCAL = cCod_Local_in
             AND C.SEC_MOV_CAJA = (SELECT SEC_MOV_CAJA
                                   FROM  CE_MOV_CAJA
                                   WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
                                         COD_LOCAL = cCod_Local_in AND
                                         SEC_MOV_CAJA_origen = cSecMovCaja_in);

           --ASIGNANDO VALOR  A VARIABLE DE CIERRE DE CAJA
           SELECT SEC_MOV_CAJA INTO v_codigoGenerado_SecMovCaja
           FROM CE_MOV_CAJA
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
                 COD_LOCAL = cCod_Local_in AND
                 SEC_MOV_CAJA_origen = cSecMovCaja_in ;

      END IF;

      -------------------------ENVIAR EMAIL------------------
      IF totCompras <> TOTAL_CAJA_TURNO or
           totCompras <> TOTAL_MONTO_SISTEMA or
           TOTAL_CAJA_TURNO <> TOTAL_MONTO_SISTEMA
      THEN
           --DESCRIPCION DE LOCAL
           SELECT COD_LOCAL ||' - '|| DESC_LOCAL
                  INTO   v_vDescLocal
           FROM   PBL_LOCAL
           WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
                  COD_LOCAL = cCod_Local_in;

           --NOMBRES Y APELLIDOS DEL CALJERO
           SELECT nom_usu || ' ' ||
                  ape_pat || ' ' ||
                  ape_mat  INTO v_vNombreCajero
           FROM   pbl_usu_local
           WHERE cod_grupo_cia = cCodGrupoCia_in --JCHAVEZ 25092009.n
             and cod_local = cCod_Local_in  --JCHAVEZ 25092009.n
             and sec_usu_local=cSecUsu_in;

           --FECHA DE VENTA
           SELECT to_char(fec_dia_vta,'dd/mm/yyyy') into v_vFecha
           FROM CE_MOV_CAJA
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND COD_LOCAL = cCod_Local_in
             AND sec_mov_caja_origen= cSecMovCaja_in
             AND TIP_MOV_CAJA = 'C'
             AND ROWNUM<2;

           cMensaje := '<BR><B>  Local : </B>'  ||  v_vDescLocal   || '  ' || '<B> Fecha :</B>' || v_vFecha ||'<BR>' ||
                       '<B>Cajero :</B>' ||  cSecUsu_in || '-' ||  ' ' ||  v_vNombreCajero ||
                       '<BR><B>SEC_MOV_CAJA (APERTURA): </B>' ||  cSecMovCaja_in   ||
                       '<BR><B> SEC_MOV_CAJA (CIERRE): </B>'   ||  v_codigoGenerado_SecMovCaja || '<BR>' ;

           cVerifique := '<TABLE BORDER=1>' ||
                         '<TR align="center" valign="BASELINE">
                          <TD align="center">' || 'TOTAL VENTAS  (S/.)' || '</TD>' ||
                         '<TD align="center">' ||'TOTAL CIERRE TURNO  (S/.)' || '</TD>' ||
                         '<TD align="center" >' || 'TOTAL POR FORMA PAGO SIST. (S/.)' || '</TD>
                          </TR><TR align="center" valign="BASELINE">
                          <TD align="center" >' || totCompras || '</TD>' ||
                         '<TD align="center" >' || TOTAL_CAJA_TURNO || '</TD>' ||
                         '<TD align="center" >' || TOTAL_MONTO_SISTEMA || '</TD></TR>'
                         ||'</TABLE>';

                           /*   '<BR><B> MONTO TOTAL : ' || '</B>'  ||  totCompras   ||  '<BR>' ||
                              '<BR><B>  TOTAL CAJA TURNO : ' || '</B>'   ||  TOTAL_CAJA_TURNO   ||  '<BR>' ||
                              '<BR><B> MONTO TOTAL SISTEMA : ' || '</B>' ||  TOTAL_MONTO_SISTEMA   ||  '<BR>';
                           */

           INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCod_Local_in,
                                         'ALERTA DIF. CIERRE TURNO CAJA :',
                                         'ALERTA',
                                         'EXISTEN DIFERENCIAS EN LOS MONTOS DEL CIERRE DE TURNO : '||
                                         '<BR>' ||'</B>' || cMensaje ||
                                         '<BR> <I>Verifique Diferencias:</I> <BR>'|| cVerifique || '<B>');
      END IF;
      RETURN 'S';
  END;


   /* ***************************************************************** */

  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        cCopiaCorreo     IN CHAR DEFAULT NULL)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTER_CE;
   -- CCReceiverAddress VARCHAR2(120) := 'joliva, operador';
   CCReceiverAddress VARCHAR2(120);

    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

     select  llave_tab_gral
     into   ReceiverAddress
     from  pbl_tab_gral
     where id_tab_gral=253;

  /* select mail_local
   into   ReceiverAddress
   from   pbl_local
   where  cod_grupo_cia = cCodGrupoCia_in
   and    cod_local = cCodLocal_in;*/

    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             NVL(cCopiaCorreo,CCReceiverAddress),
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;
  /*---------------------------------------------------------------------------------------*/

   FUNCTION CAJ_LISTA_SERIES_BOLETA_CAJ(cCodGrupoCia_in IN CHAR,
                      cCod_Local_in    IN CHAR)
    RETURN FarmaCursor
  IS
    curImpr FarmaCursor;
  BEGIN
    OPEN curImpr FOR

    /*SELECT  DISTINCT TRIM(NUM_SERIE_LOCAL) || 'Ã' ||
                 TRIM(NUM_SERIE_LOCAL) "1"

        FROM VTA_IMPR_LOCAL IL,
          VTA_CAJA_IMPR  CI,
        VTA_CAJA_PAGO  CP
    WHERE
                 CP.COD_GRUPO_CIA  = cCodGrupoCia_in
       AND   CP.COD_LOCAL      = cCod_Local_in
       AND   IL.TIP_COMP       = '01'
       AND   CI.COD_GRUPO_CIA  = CP.COD_GRUPO_CIA
       AND   CI.COD_LOCAL      = CP.COD_LOCAL
       AND   CI.NUM_CAJA_PAGO  = CP.NUM_CAJA_PAGO
       AND   IL.COD_GRUPO_CIA  = CI.COD_GRUPO_CIA
       AND   IL.COD_LOCAL      = CI.COD_LOCAL
       AND   IL.SEC_IMPR_LOCAL = CI.SEC_IMPR_LOCAL;*/

        SELECT  DISTINCT TRIM(IL.NUM_SERIE_LOCAL) || 'Ã' ||
                 TRIM(IL.NUM_SERIE_LOCAL) "1"
        FROM VTA_IMPR_LOCAL IL
        WHERE IL.COD_GRUPO_CIA=cCodGrupoCia_in
            AND IL.COD_LOCAL=cCod_Local_in
            AND IL.TIP_COMP = COD_TIP_COMP_BOLETA;

     RETURN curImpr;
  END;

 /**********************************************************************************/

    FUNCTION CAJ_LISTA_SERIES_FACTURA_CAJ(cCodGrupoCia_in IN CHAR,
                      cCod_Local_in    IN CHAR)
    RETURN FarmaCursor
  IS
    curImpr FarmaCursor;
  BEGIN
    OPEN curImpr FOR

  /*  SELECT  DISTINCT TRIM(NUM_SERIE_LOCAL) || 'Ã' ||
                 TRIM(NUM_SERIE_LOCAL) "1"

        FROM VTA_IMPR_LOCAL IL,
          VTA_CAJA_IMPR  CI,
        VTA_CAJA_PAGO  CP
    WHERE
                 CP.COD_GRUPO_CIA  = cCodGrupoCia_in
       AND   CP.COD_LOCAL      = cCod_Local_in
       AND   IL.TIP_COMP       = '02'
       AND   CI.COD_GRUPO_CIA  = CP.COD_GRUPO_CIA
       AND   CI.COD_LOCAL      = CP.COD_LOCAL
       AND   CI.NUM_CAJA_PAGO  = CP.NUM_CAJA_PAGO
       AND   IL.COD_GRUPO_CIA  = CI.COD_GRUPO_CIA
       AND   IL.COD_LOCAL      = CI.COD_LOCAL
       AND   IL.SEC_IMPR_LOCAL = CI.SEC_IMPR_LOCAL;*/


        SELECT  DISTINCT TRIM(IL.NUM_SERIE_LOCAL) || 'Ã' ||
                 TRIM(IL.NUM_SERIE_LOCAL) "1"
        FROM VTA_IMPR_LOCAL IL
        WHERE IL.COD_GRUPO_CIA=cCodGrupoCia_in
            AND IL.COD_LOCAL=cCod_Local_in
            AND IL.TIP_COMP = COD_TIP_COMP_FACTURA;


     RETURN curImpr;
  END;
  /**************************************************************************************************/


  --ASOLIS 01/02/2009
    FUNCTION CAJ_F_VALOR_COMPROBANTES(cCodGrupoCia_in IN CHAR,
                                       cCod_Local_in   IN CHAR)

    RETURN FarmaCursor

    IS
    cur FarmaCursor;

    BEGIN

         OPEN cur FOR

         SELECT
           --IL.DESC_IMPR_LOCAL     || 'Ã' ||
           TC.DESC_COMP           || 'Ã' ||
           IL.NUM_SERIE_LOCAL     || 'Ã' ||
           IL.NUM_COMP            || 'Ã' ||
           IL.TIP_COMP

    FROM VTA_IMPR_LOCAL  IL ,
          VTA_SERIE_LOCAL SL ,
          VTA_TIP_COMP    TC
    WHERE IL.COD_GRUPO_CIA   = cCodGrupoCia_in
    AND   IL.COD_LOCAL       = cCod_Local_in
    AND   IL.COD_GRUPO_CIA   = SL.COD_GRUPO_CIA
    AND   IL.COD_LOCAL       = SL.COD_LOCAL
    AND   IL.NUM_SERIE_LOCAL = SL.NUM_SERIE_LOCAL
    AND   IL.TIP_COMP        = SL.TIP_COMP
    AND   SL.COD_GRUPO_CIA   = TC.COD_GRUPO_CIA
    AND   SL.TIP_COMP        = TC.TIP_COMP
    AND   IL.TIP_COMP IN ('01','02') --BOLETA ,FACTURA
    AND   SL.NUM_SERIE_LOCAL = cCod_Local_in
    ORDER BY IL.TIP_COMP;

    RETURN cur;

    END;

    /*------------------------------------------------------------------------*/
      --ASOLIS 06/02/2009
    FUNCTION CAJ_F_VALOR_COMPROBANTE_BOLETA(cCodGrupoCia_in IN CHAR,
                                             cCod_Local_in   IN CHAR,
                                            cNum_SerieLocal_in IN CHAR)
    RETURN FarmaCursor
    IS
        cur FarmaCursor;
    BEGIN

         OPEN cur FOR
         SELECT --IL.DESC_IMPR_LOCAL     || 'Ã' ||
                TC.DESC_COMP           || 'Ã' ||
                IL.NUM_SERIE_LOCAL     || 'Ã' ||
                IL.NUM_COMP            || 'Ã' ||
                IL.TIP_COMP
         FROM VTA_IMPR_LOCAL  IL,
              VTA_SERIE_LOCAL SL,
              VTA_TIP_COMP    TC
         WHERE IL.COD_GRUPO_CIA   = cCodGrupoCia_in
         AND   IL.COD_LOCAL       = cCod_Local_in
         AND   IL.COD_GRUPO_CIA   = SL.COD_GRUPO_CIA
         AND   IL.COD_LOCAL       = SL.COD_LOCAL
         AND   IL.NUM_SERIE_LOCAL = SL.NUM_SERIE_LOCAL
         AND   IL.TIP_COMP        = SL.TIP_COMP
         AND   SL.COD_GRUPO_CIA   = TC.COD_GRUPO_CIA
         AND   SL.TIP_COMP        = TC.TIP_COMP
         AND   IL.TIP_COMP        =  '01' --BOLETA
         AND   SL.NUM_SERIE_LOCAL = cNum_SerieLocal_in
         ORDER BY IL.TIP_COMP;

    RETURN cur;

    END;

    ----------------------------------------------------------------------------------------

          --ASOLIS 06/02/2009
    FUNCTION CAJ_F_VALOR_COMP_FACTURA(cCodGrupoCia_in IN CHAR,
                                             cCod_Local_in   IN CHAR,
                                            cNum_SerieLocal_in IN CHAR)

    RETURN FarmaCursor

    IS
    cur FarmaCursor;

    BEGIN

         OPEN cur FOR

         SELECT
           --IL.DESC_IMPR_LOCAL     || 'Ã' ||
           TC.DESC_COMP           || 'Ã' ||
           IL.NUM_SERIE_LOCAL     || 'Ã' ||
           IL.NUM_COMP            || 'Ã' ||
           IL.TIP_COMP

    FROM VTA_IMPR_LOCAL  IL ,
          VTA_SERIE_LOCAL SL ,
          VTA_TIP_COMP    TC
    WHERE IL.COD_GRUPO_CIA   = cCodGrupoCia_in
    AND   IL.COD_LOCAL       = cCod_Local_in
    AND   IL.COD_GRUPO_CIA   = SL.COD_GRUPO_CIA
    AND   IL.COD_LOCAL       = SL.COD_LOCAL
    AND   IL.NUM_SERIE_LOCAL = SL.NUM_SERIE_LOCAL
    AND   IL.TIP_COMP        = SL.TIP_COMP
    AND   SL.COD_GRUPO_CIA   = TC.COD_GRUPO_CIA
    AND   SL.TIP_COMP        = TC.TIP_COMP
    AND   IL.TIP_COMP        =  '02' --FACTURA
    AND   SL.NUM_SERIE_LOCAL = cNum_SerieLocal_in
    ORDER BY IL.TIP_COMP;

    RETURN cur;

    END;


  ---------------------------------------------------------------------------------------------

   FUNCTION CAJ_F_VALOR_MAXIMA_DIFERENCIA

   RETURN CHAR

   IS

   v_ValorMaximo char(1);

   BEGIN

     SELECT  LLAVE_TAB_GRAL INTO v_ValorMaximo
     FROM    pbl_tab_gral
     WHERE   ID_TAB_GRAL=256;

     RETURN v_ValorMaximo;

   END;


  -----------------------------------------------------------------------------------------------

  /*FUNCTION CAJ_F_CHAR_DNI_CLIENTE(cCodGrupoCia_in    IN CHAR,
                                   cCod_Local_in      IN CHAR,
                                  cNum_Pedido_in     IN CHAR)

   RETURN CHAR

   IS

   v_DniClienteFid char(20):='';

   BEGIN

     SELECT NVL(T.DNI_CLI,' ')
     INTO   v_DniClienteFid
     FROM   FID_TARJETA_PEDIDO T
     WHERE  T.NUM_PEDIDO = cNum_Pedido_in;

     RETURN TRIM(v_DniClienteFid);

   END;
*/
-----------------------------------------------------------------------------------------------

  FUNCTION CAJ_F_CHAR_DNI_CLIENTE(cCodGrupoCia_in    IN CHAR,
                                   cCod_Local_in      IN CHAR,
                                  cNum_Pedido_in     IN CHAR)

   RETURN CHAR

   IS

   v_DniClienteFid char(20):=' ';

   BEGIN

     SELECT NVL(T.DNI_CLI,' ')
     INTO   v_DniClienteFid
     FROM   FID_TARJETA_PEDIDO T
     WHERE  T.NUM_PEDIDO = cNum_Pedido_in;

     RETURN v_DniClienteFid;--EL TRIM SE QUITO YA QUE EN JAVA LO HACE
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         v_DniClienteFid:=' ';
         RETURN v_DniClienteFid;--EL TRIM SE QUITO YA QUE EN JAVA LO HACE

   END;



   /******************************************************************/
   FUNCTION CAJ_F_LISTA_CAMP_AUTOMATIC(cCodGrupoCia_in    IN CHAR,
                                      cCod_Local_in      IN CHAR,
                                     cNum_pedido_in     IN CHAR)

   RETURN FarmaCursor

   IS
   cur FarmaCursor;

   BEGIN

        OPEN cur FOR
             select distinct trim(d.cod_camp_cupon) as cod_camp_cupon
             from vta_pedido_vta_cab c, vta_pedido_vta_det d
             where c.cod_grupo_cia = d.cod_grupo_cia
             and   c.cod_local     = d.cod_local
             and   c.num_ped_vta   = d.num_ped_vta
             and   c.ind_fid       = 'S'
             and   d.num_ped_vta   = cNum_pedido_in;

        RETURN cur;

   END;
   /******************************************************************/

  PROCEDURE CAJ_REG_CAMP_LIM_CLIENTE(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     vCodCampCupon_in  IN CHAR,
                                     vDniCliente_in    IN CHAR,
                                     vIdUsuario_in     IN CHAR)
  AS
    vCant number:=0;
    vNroMaxUsos number:=0;
    vNroUsos number:=0;
  BEGIN

    SELECT nvl(count(1),0) INTO vCant
    FROM CL_CLI_CAMP CL
    WHERE CL.DNI_CLI        = vDniCliente_in
    AND   CL.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND   CL.COD_CAMP_CUPON = vCodCampCupon_in;

    --
    IF vCant = 0 THEN--QUIERE DECIR QUE NO EXISTE EL REGISTRO AUN DE DICHA CAMPAÑA PARA DICHO CLIENTE
       SELECT NVL(C.CL_MAX_USOS,0)
       INTO   vNroMaxUsos
       FROM   VTA_CAMPANA_CUPON C
       WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
       AND    C.COD_CAMP_CUPON = vCodCampCupon_in;

       INSERT INTO CL_CLI_CAMP (DNI_CLI,COD_GRUPO_CIA,COD_CAMP_CUPON,ESTADO,MAX_USOS,
                                NRO_USOS,USU_CREA_CL_CLI_CAMP,FEC_CREA_CL_CLI_CAMP)
       VALUES (vDniCliente_in, cCodGrupoCia_in, vCodCampCupon_in, 'A', vNroMaxUsos,1,vIdUsuario_in, SYSDATE);
    ELSE -- QUIERE DECIR QUE YA EXISTIA LA CAMPALA CON EL CLIENTE

        SELECT nvl(CL.MAX_USOS,0), nvl(CL.Nro_Usos,0) INTO vNroMaxUsos, vNroUsos
        FROM  CL_CLI_CAMP CL
        WHERE CL.DNI_CLI        = vDniCliente_in
        AND   CL.COD_GRUPO_CIA  = cCodGrupoCia_in
        AND   CL.COD_CAMP_CUPON = vCodCampCupon_in;

        --DBMS_OUTPUT.put_line('HABER :vNroMaxUsos: '||vNroMaxUsos||' , vNroUsos:'||vNroUsos);

        IF vNroMaxUsos >  vNroUsos THEN
          UPDATE CL_CLI_CAMP C SET NRO_USOS = NRO_USOS +1,
                                   USU_MOD_CL_CLI_CAMP = vIdUsuario_in,
                                   FEC_MOD_CL_CLI_CAMP = SYSDATE
          WHERE  C.DNI_CLI        = vDniCliente_in
          AND    C.COD_GRUPO_CIA  = cCodGrupoCia_in
          AND    C.COD_CAMP_CUPON = vCodCampCupon_in;

        ELSE
            RAISE_APPLICATION_ERROR(-20999,'Error al cobrar pedido !\nEl descuento de la campaña ya fue usado por el cliente !');
        END IF;
    END IF;

  END;
 /* ******************************************************************************* */
   PROCEDURE CAJ_P_FOR_UPDATE_MOV_CAJA(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cSecCaja_in       IN CHAR)
    as
    codLocal_in char(3);
    begin

    SELECT M.COD_LOCAL
    INTO   codLocal_in
    FROM   CE_MOV_CAJA M
    WHERE  M.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    M.COD_LOCAL     = cCodLocal_in
    AND    M.TIP_MOV_CAJA  = 'A'
    AND    M.SEC_MOV_CAJA  = cSecCaja_in for update;

    end;

  /*****************************************************************************/
  FUNCTION CAJ_GET_TIPO_COMPR(cCodGrupoCia_in    IN CHAR,
                               cCod_Local_in      IN CHAR,
                              cNumCaja           IN CHAR,
                              cTipComp           IN CHAR)
   RETURN CHAR
   IS
     TIP_COMP CHAR(2);
   BEGIN

    IF(cTipComp=COD_TIP_COMP_BOLETA)THEN
      SELECT TRIM(B.TIP_COMP) INTO TIP_COMP
      FROM VTA_CAJA_IMPR A,
           VTA_IMPR_LOCAL B
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCod_Local_in
      AND NUM_CAJA_PAGO=cNumCaja
      AND B.TIP_COMP IN (COD_TIP_COMP_TICKET,COD_TIP_COMP_BOLETA)
      AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
      AND A.COD_LOCAL=B.COD_LOCAL
      AND A.SEC_IMPR_LOCAL=B.SEC_IMPR_LOCAL;
    ELSE
     TIP_COMP:=COD_TIP_COMP_FACTURA;
    END IF;

     RETURN TIP_COMP;

   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         TIP_COMP:='01';
         RETURN TIP_COMP;
   END;

   /************************************************************************/
  FUNCTION CAJ_GET_TIPO_COMPR2(cCodGrupoCia_in    IN CHAR,
                               cCod_Local_in      IN CHAR,
                              cNumCaja           IN CHAR)
   RETURN CHAR
   IS
     TIPO_COMP CHAR(2);
     VALOR CHAR(1);
   BEGIN

    SELECT B.TIP_COMP INTO TIPO_COMP
    FROM VTA_CAJA_IMPR A,
         VTA_IMPR_LOCAL B
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCod_Local_in
    AND A.NUM_CAJA_PAGO=TO_NUMBER(cNumCaja)
    AND B.TIP_COMP IN (COD_TIP_COMP_TICKET,COD_TIP_COMP_BOLETA)
    AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
    AND A.COD_LOCAL=B.COD_LOCAL
    AND A.SEC_IMPR_LOCAL=B.SEC_IMPR_LOCAL;

    IF (TIPO_COMP=COD_TIP_COMP_TICKET) THEN
       VALOR:='N';
    ELSE
       VALOR:='S';
    END IF;

     RETURN VALOR;
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         VALOR:='S';
         RETURN VALOR;
   END;

   /**********************************************************************/
     FUNCTION CAJ_OBTIENE_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in   IN CHAR,
                                              cCodLocal_in     IN CHAR,
                                              cSecImprLocal_in IN CHAR,
                                              cNumPed_in      IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    TipComp CHAR(2);
  BEGIN

  SELECT X.TIP_COMP_PAGO INTO TipComp
  FROM VTA_PEDIDO_VTA_CAB X
  WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
  AND X.COD_LOCAL=cCodLocal_in
  AND X.NUM_PED_VTA=cNumPed_in;

    OPEN curCaj FOR
    SELECT IMPR_LOCAL.NUM_SERIE_LOCAL || 'Ã' ||
         IMPR_LOCAL.NUM_COMP
    FROM   VTA_IMPR_LOCAL IMPR_LOCAL
    WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND     IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
    AND    IMPR_LOCAL.TIP_COMP=TipComp
    AND ROWNUM=1 FOR UPDATE; --Ya que habra mas de una ticketera de tipo 5
    RETURN curCaj;
  END;

   /**********************************************************************/
     FUNCTION CAJ_VALIDA_CAJA_APERTURA(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        nNumCaj_in      IN NUMBER)
      RETURN CHAR
      IS
         v_IndCajAb CHAR(1);
      BEGIN
             SELECT IND_CAJA_ABIERTA
             INTO   v_IndCajAb
             FROM   VTA_CAJA_PAGO
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND COD_LOCAL     = cCodLocal_in
             AND NUM_CAJA_PAGO = nNumCaj_in FOR UPDATE;

         RETURN   v_IndCajAb;
      END;
 /** **************************************************************** */
  PROCEDURE CAJ_P_ACT_COMP_ANUL(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
                                  cNumPedVta_in       IN CHAR,
                                  cNumCompPago_in     IN CHAR,
                                  cUsuModCompPago_in  IN CHAR,
                                  cInd                IN CHAR
                                  )
  IS

  nCantidad  NUMBER;
  nNumComPago CHAR(3);

  BEGIN

  SELECT COUNT(1)
  INTO   nCantidad
  FROM   VTA_COMP_PAGO A
  WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
  AND A.COD_LOCAL=cCodLocal_in
  AND A.NUM_PED_VTA=cNumPedVta_in
  --AND A.NUM_COMP_PAGO=cNumCompPago_in;--08.09.2014
  AND
  --DECODE(A.COD_TIP_PROC_PAGO,'1',A.NUM_COMP_PAGO_E,A.NUM_COMP_PAGO)
  --RH: 21.10.2014 FAC-ELECTRONICA
      FARMA_UTILITY.GET_T_COMPROBANTE(A.COD_TIP_PROC_PAGO,A.NUM_COMP_PAGO_E,A.NUM_COMP_PAGO)
        =cNumCompPago_in ;--LTAVARA 08.09.2014 proceso electronico


  IF nCantidad  = 0 THEN
     RAISE_APPLICATION_ERROR(-20020,'Comprobante Incorrecto:  '||nNumComPago);
  END IF;

  IF cInd='A' THEN

  UPDATE VTA_COMP_PAGO X
  SET  X.FECH_IMP_ANUL=SYSTIMESTAMP
    WHERE  X.COD_GRUPO_CIA     = cCodGrupoCia_in
    AND     X.COD_LOCAL         = cCodLocal_in
    AND     X.NUM_PED_VTA       = cNumPedVta_in
    AND    -- X.NUM_COMP_PAGO=cNumCompPago_in;--08.09.2014
    --DECODE(X.COD_TIP_PROC_PAGO,'1',X.NUM_COMP_PAGO_E,X.NUM_COMP_PAGO)
    --RH: 21.10.2014 FAC-ELECTRONICA
      FARMA_UTILITY.GET_T_COMPROBANTE(X.COD_TIP_PROC_PAGO,X.NUM_COMP_PAGO_E,X.NUM_COMP_PAGO)
                     =cNumCompPago_in ;--LTAVARA 08.09.2014 proceso electronico


  ELSIF cInd='C' THEN

    UPDATE VTA_COMP_PAGO X
  SET  X.FECH_IMP_COBRO=SYSTIMESTAMP
    WHERE  X.COD_GRUPO_CIA     = cCodGrupoCia_in
    AND     X.COD_LOCAL         = cCodLocal_in
    AND     X.NUM_PED_VTA       = cNumPedVta_in
    AND --   X.NUM_COMP_PAGO=cNumCompPago_in;--08.09.2014
    --DECODE(X.COD_TIP_PROC_PAGO,'1',X.NUM_COMP_PAGO_E,X.NUM_COMP_PAGO)
    --RH: 21.10.2014 FAC-ELECTRONICA
      FARMA_UTILITY.GET_T_COMPROBANTE(X.COD_TIP_PROC_PAGO,X.NUM_COMP_PAGO_E,X.NUM_COMP_PAGO)
          =cNumCompPago_in ;--LTAVARA 08.09.2014 proceso electronico

  END IF;
    --commit realizarlo en java
    --COMMIT;

   END;


     /* ********************************************************************************************** */

  FUNCTION CAJ_F_PERMITE_INGRESO_SOBRE(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecMovCaja    IN CHAR)
  RETURN CHAR
  IS
    v_ValTotal VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_ValTotalSobre VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    indPerm    PBL_TAB_GRAL.Desc_Corta%TYPE;
    montoPerm  PBL_TAB_GRAL.Llave_Tab_Gral%TYPE;
    valor CHAR(1):='N';
    indProsegur   CHAR(1);
  BEGIN
  v_ValTotalSobre:=0.1;

       SELECT C.IND_PROSEGUR INTO indProsegur
       FROM PBL_LOCAL C
       WHERE C.COD_GRUPO_CIA=cCodGrupoCia_in
       AND C.COD_LOCAL=cCodLocal_in;

       SELECT TRIM(A.LLAVE_TAB_GRAL),TRIM(A.DESC_CORTA)
       INTO montoPerm,indPerm
       FROM PBL_TAB_GRAL A
       --WHERE A.ID_TAB_GRAL=317; ANTES, ASOSA, 31.05.2010
       WHERE A.ID_TAB_GRAL=366; --ASOSA, 31.05.2010
       dbms_output.put_line('v_ValTotalSobre: ');

      /*SELECT SUM(X.VAL_NETO_PED_VTA)INTO v_ValTotal --INI - ASOSA, 03.06.2010
      FROM VTA_PEDIDO_VTA_CAB X
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.EST_PED_VTA=EST_PED_COBRADO
      --AND X.IND_PEDIDO_ANUL='N'
      AND X.SEC_MOV_CAJA=cSecMovCaja;*/ --SE COMENTO PORQUE EL DINERO PARA SOBRES PARCIALES DEBE SER EFECTIVO

      SELECT nvl(SUM(X.IM_TOTAL_PAGO - x.val_vuelto),0) INTO v_ValTotal
      FROM VTA_FORMA_PAGO_PEDIDO X
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.NUM_PED_VTA IN (SELECT Q.NUM_PED_VTA FROM VTA_PEDIDO_VTA_CAB Q
                            WHERE Q.COD_GRUPO_CIA=cCodGrupoCia_in
                            AND Q.COD_LOCAL=cCodLocal_in
                            AND Q.EST_PED_VTA=EST_PED_COBRADO
                            AND Q.SEC_MOV_CAJA=cSecMovCaja)
      AND X.COD_FORMA_PAGO IN ('00001','00002');           --FIN - ASOSA, 03.06.2010

      SELECT nvl(SUM(Q.MON_ENTREGA_TOTAL),0)
      INTO   v_ValTotalSobre
      FROM   CE_SOBRE_TMP Q
      WHERE  Q.COD_GRUPO_CIA=cCodGrupoCia_in
      AND Q.COD_LOCAL=cCodLocal_in
      AND Q.SEC_MOV_CAJA=cSecMovCaja
      AND Q.ESTADO = 'A';

      /*
      SELECT SUM(X.IM_TOTAL_PAGO) INTO v_ValTotal
      FROM VTA_FORMA_PAGO_PEDIDO X
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.NUM_PED_VTA IN (SELECT Q.NUM_PED_VTA FROM VTA_PEDIDO_VTA_CAB Q
                            WHERE Q.COD_GRUPO_CIA=cCodGrupoCia_in
                            AND Q.COD_LOCAL=cCodLocal_in
                            AND Q.EST_PED_VTA=EST_PED_COBRADO
                            AND Q.SEC_MOV_CAJA=cSecMovCaja)
      AND X.COD_FORMA_PAGO IN ('00001','00002');           --FIN - ASOSA, 03.06.2010
      SELECT nvl(SUM(Y.MON_ENTREGA_TOTAL) ,0) INTO v_ValTotalSobre
      FROM CE_SOBRE_TMP Y
      WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
      AND Y.COD_LOCAL=cCodLocal_in
      AND Y.SEC_MOV_CAJA=cSecMovCaja;
      */
      dbms_output.put_line('v_ValTotalSobre: '||v_ValTotalSobre);
      dbms_output.put_line('v_ValTotal: '||v_ValTotal);
      dbms_output.put_line('v_ValTotal - TotalSobre: '||(v_ValTotal-v_ValTotalSobre));
      dbms_output.put_line('montoPerm: '||montoPerm);

      --IF(indProsegur='S')THEN
        IF(indPerm='S')THEN
          IF(v_ValTotal-v_ValTotalSobre>=TO_NUMBER(TRIM(montoPerm)))THEN
          valor:='S';
          END IF;
        END IF;
     -- END IF;

    RETURN valor;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
     valor:='N';
     RETURN valor;
  END;

   /****************************************************************************/
  FUNCTION CAJ_F_GRABA_SOBRE(cCodGrupoCia_in      IN CHAR,
                                         cCodLocal_in        IN CHAR,
                                        cSecMovCaja_in      IN CHAR,
                                        cCodFormaPago_in    IN CHAR,
                                        cTipMoneda_in       IN CHAR,
                                        cMonEntrega_in      IN NUMBER,
                                        cMonEntregaTotal_in IN NUMBER,
                                        cUsuCreaEntrega_in  IN CHAR)
  RETURN VARCHAR2
  IS
   v_numSec NUMBER;
   cFecDiaVta_in CE_MOV_CAJA.FEC_DIA_VTA%TYPE;
   vCodigoSobre  varchar2(20);
    nSecSobre     number;
    cEstadoCreado VARCHAR2(1);
    cIndProsegur CE_SOBRE_TMP.IND_ETV%TYPE; -- INDICADOR DE SOBRE PORTAVALOR O DEPOSITO
  BEGIN


    select m.fec_dia_vta
      into cFecDiaVta_in
      from ce_mov_caja m
     where m.cod_grupo_cia = cCodGrupoCia_in
       and m.cod_local = cCodLocal_in
       and m.sec_mov_caja = cSecMovCaja_in;

    --nSecSobre := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, COD_NUM_SEC_SOBRE);

    nSecSobre:=PTOVENTA_CAJ.CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in,cFecDiaVta_in);



    vCodigoSobre :=  --cCodLocal_in ||'-'||
     to_char(cFecDiaVta_in, 'ddmmyyyy') || '-' ||
                    Farma_Utility.COMPLETAR_CON_SIMBOLO(nSecSobre,
                                                        3,
                                                        0,
                                                        'I');
       SELECT NVL(MAX(A.SEC),0) + 1
       INTO   v_numSec
       FROM   CE_SOBRE_TMP A
       WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
       AND    A.COD_LOCAL = ccodlocal_in
       AND    A.SEC_MOV_CAJA = Csecmovcaja_In;

       --ASOSA, 02.06.2010
       cEstadoCreado:='P'; --ASOSA, 10.06.2010
       /*SELECT A.DESC_LARGA INTO cEstadoCreado
       FROM PBL_TAB_GRAL A
       --WHERE A.ID_TAB_GRAL=317; antes
       WHERE A.ID_TAB_GRAL=366; --ASOSA, 01.06.2010
       */

       -- KMONCADA, 19/05/14 SE OBTIENE TIPO DE SOBRE SEGUN INDICE DEL LOCAL
       SELECT a.ind_prosegur
       INTO cIndProsegur
       FROM   pbl_local a
       where  a.cod_grupo_cia=cCodGrupoCia_in
       and    a.cod_local=cCodLocal_in;

--     SE AGREGA CAMPO DE TIPO DE SOBRE PORTAVALOR O DEPOSITO
       INSERT INTO CE_SOBRE_TMP (COD_SOBRE,COD_GRUPO_CIA,COD_LOCAL,FEC_DIA_VTA,SEC_MOV_CAJA,SEC,COD_FORMA_PAGO
       ,TIP_MONEDA,MON_ENTREGA,MON_ENTREGA_TOTAL,USU_CREA_SOBRE,USU_MOD_SOBRE,FEC_MOD_SOBRE,ESTADO, IND_ETV)
        VALUES (vCodigoSobre,cCodGrupoCia_in,cCodLocal_in,TRUNC(SYSDATE),cSecMovCaja_in,v_numSec,cCodFormaPago_in,
        cTipMoneda_in,cMonEntrega_in,cMonEntregaTotal_in,cUsuCreaEntrega_in,NULL,NULL,cEstadoCreado, cIndProsegur);
/*
            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               COD_NUM_SEC_SOBRE,
                                               cUsuCreaEntrega_in);*/

       RETURN ''||vCodigoSobre;
  END;

    /******************************************************************************/
  PROCEDURE CAJ_P_ELIMINA_SOBRE(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cSecMovCaja_in  IN CHAR,
                                cCodForm_in     IN CHAR,
                                cTipMon_in      IN CHAR,
                                cSec            IN CHAR,
                                cUsuCrea        IN CHAR)
 IS

    dFecDia    date;
    cantSobre  number;
    cCodRemito VARCHAR2(20);

    cCodFormPago_in CHAR(5);
    cSecCaja_in     CHAR(10);
    nEstadoSobre char(1);
  BEGIN

  SELECT COUNT(*) INTO cantSobre
  FROM CE_SOBRE_TMP A
  WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
  AND A.COD_LOCAL=cCodLocal_in
  AND A.COD_FORMA_PAGO=cCodForm_in
  AND A.TIP_MONEDA=cTipMon_in
  AND A.SEC_MOV_CAJA=cSecMovCaja_in
  AND A.COD_SOBRE=cSec;



    IF cantSobre > 0 THEN

       SELECT A.ESTADO INTO nEstadoSobre
        FROM CE_SOBRE_TMP A
        WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=cCodLocal_in
        AND A.COD_FORMA_PAGO=cCodForm_in
        AND A.TIP_MONEDA=cTipMon_in
        AND A.SEC_MOV_CAJA=cSecMovCaja_in
        AND A.COD_SOBRE=cSec;

      if nEstadoSobre = 'P' then
        DELETE CE_SOBRE_TMP A
        WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=cCodLocal_in
        AND A.COD_FORMA_PAGO=cCodForm_in
        AND A.TIP_MONEDA=cTipMon_in
        AND A.SEC_MOV_CAJA=cSecMovCaja_in
        AND A.COD_SOBRE=cSec;
      else
          RAISE_APPLICATION_ERROR(-20005,'No se puede eliminar el sobre porque no esta pendiente.');
      end if;
    ELSE
      RAISE_APPLICATION_ERROR(-20101,'EL SOBRE YA FUE ELIMINADO. VERIFIQUE!!!');
    END IF;

  END;

  /*********************************************************************************************** */
  FUNCTION CAJ_F_CUR_SOBRES(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cSecMovCaja_in  IN CHAR)
RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN

      OPEN curDet FOR
      SELECT X.COD_FORMA_PAGO || 'Ã' ||
             Y.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             CASE WHEN X.TIP_MONEDA= '01' THEN 'SOLES'
                    WHEN X.TIP_MONEDA= '02' THEN 'DOLARES' END || 'Ã' ||
                    TO_CHAR(X.MON_ENTREGA,'999,990.00') || 'Ã' ||
                    TO_CHAR(X.MON_ENTREGA_TOTAL,'999,990.00') || 'Ã' ||
                    'S' || 'Ã' ||
                    X.COD_SOBRE || 'Ã' ||
                    X.SEC_MOV_CAJA || 'Ã' ||
                    X.TIP_MONEDA || 'Ã' ||
                    X.SEC
      FROM CE_SOBRE_TMP X,
           VTA_FORMA_PAGO Y
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.SEC_MOV_CAJA=cSecMovCaja_in
      AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
      AND X.COD_FORMA_PAGO=Y.COD_FORMA_PAGO;

    RETURN curDet;
  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_F_CUR_SOBRES_ENTREGA(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor IS
    curDet FarmaCursor;
    SecMovCaja CE_MOV_CAJA.SEC_MOV_CAJA%TYPE;
    NumCaja CE_MOV_CAJA.NUM_CAJA_PAGO%TYPE;
    SecUsu CE_MOV_CAJA.SEC_USU_LOCAL%TYPE;
    FecDia CE_MOV_CAJA.FEC_DIA_VTA%TYPE;
        SecMovCajaOrig CE_MOV_CAJA.SEC_MOV_CAJA_ORIGEN%TYPE;
  BEGIN


     SELECT A.SEC_MOV_CAJA_ORIGEN INTO SecMovCajaOrig
     FROM CE_MOV_CAJA A
     WHERE  A.COD_GRUPO_CIA=cCodGrupoCia_in
     AND A.COD_LOCAL=cCodLocal_in
     AND A.SEC_MOV_CAJA=TRIM(cSecMovCaja_in);

    SELECT A.SEC_MOV_CAJA,A.NUM_CAJA_PAGO,A.SEC_USU_LOCAL,A.FEC_DIA_VTA
           INTO SecMovCaja,NumCaja,SecUsu,FecDia
    FROM CE_MOV_CAJA A
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.SEC_MOV_CAJA=TRIM(SecMovCajaOrig);--obtenermos apertura del turno

      OPEN curDet FOR
      SELECT A.COD_FORMA_PAGO || 'Ã' ||
             Y.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             '0'|| 'Ã' ||
             CASE WHEN A.TIP_MONEDA= '01' THEN 'SOLES'
                    WHEN A.TIP_MONEDA= '02' THEN 'DOLARES' END || 'Ã' ||
                    TO_CHAR(A.MON_ENTREGA,'999,990.00') || 'Ã' ||
                    TO_CHAR(A.MON_ENTREGA_TOTAL,'999,990.00') || 'Ã' ||
                    ' ' || 'Ã' ||
                    'S' || 'Ã' ||
                    A.COD_SOBRE || 'Ã' ||
                    cSecMovCaja_in || 'Ã' ||
                    A.TIP_MONEDA || 'Ã' ||
                    'N' || 'Ã' ||
                    'N' || 'Ã' ||
                    A.SEC
      FROM CE_SOBRE_TMP A,
           VTA_FORMA_PAGO Y
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCodLocal_in
      AND A.SEC_MOV_CAJA=SecMovCaja--apertura con el que se registraron los sobres
      AND A.ESTADO IN ('P','A') --ASOSA, 14.06.2010
      AND A.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
      AND A.COD_FORMA_PAGO=Y.COD_FORMA_PAGO
      AND A.COD_SOBRE NOT IN (SELECT COD_SOBRE
                              FROM CE_SOBRE A
                              WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                              AND A.COD_LOCAL=cCodLocal_in
                              AND A.FEC_DIA_VTA=FecDia)
      ORDER BY A.COD_FORMA_PAGO;

    RETURN curDet;
  END;


 /**************************************************************************************************/
   FUNCTION CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in   IN CHAR,
                                        cSecMovCaja_in  IN CHAR,
                                        cFecDiaVta_in   IN DATE)
    RETURN NUMBER
    IS
  Sec CHAR(10);
  cSecMovOrigen CHAR(10);
  nSecSobre1 NUMBER;
  nSecSobre2 NUMBER;
  BEGIN



    select to_number(nvl(max(Substr(B.cod_sobre,10)),'0'),'999')+ 1
    into nSecSobre1
    from CE_SOBRE_TMP B
    where B.cod_grupo_cia = cCodGrupoCia_in
       and B.cod_local = cCod_Local_in
       and B.FEC_DIA_VTA = cFecDiaVta_in;


     select to_number(nvl(max(Substr(A.cod_sobre,10)),'0'),'999')+ 1
    into nSecSobre2
    from CE_SOBRE A
    where A.cod_grupo_cia = cCodGrupoCia_in
       and A.cod_local = cCod_Local_in
      and A.FEC_DIA_VTA = cFecDiaVta_in;



    IF(nSecSobre1>nSecSobre2)THEN
        Sec:=nSecSobre1;
    ELSE
        Sec:=nSecSobre2;
    END IF;


   RETURN Sec;
   END;

   /**********************************************************************/
   FUNCTION CAJ_F_GET_MONTOVENTAS(cCodGrupoCia_in   IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR)
    RETURN CHAR
    IS
       cValTotal VARCHAR2(20);
        v_ValTotal VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    BEGIN

      SELECT nvl(SUM(y.im_total_pago),0) INTO v_ValTotal
      FROM VTA_PEDIDO_VTA_CAB X,
           VTA_FORMA_PAGO_PEDIDO Y
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.EST_PED_VTA=EST_PED_COBRADO
      --AND X.IND_PEDIDO_ANUL='N'
      AND X.SEC_MOV_CAJA=cSecMovCaja_in
      AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
      AND X.COD_LOCAL=Y.COD_LOCAL
      AND X.NUM_PED_VTA=Y.NUM_PED_VTA
      AND Y.COD_FORMA_PAGO IN ('00001','00002');

       RETURN  TO_CHAR(v_ValTotal,'999,990.00');
    END;


   /*********************************************************************************/
    PROCEDURE CAJ_P_ENVIA_SOBRE(cCodGrupoCia  IN CHAR,
                                cCodLocal     IN CHAR,
                                cUsuCrea      IN CHAR,
                                cCodSobre     IN CHAR)
    IS

    vDirec VARCHAR2(100);
    mesg_body VARCHAR2(4000);
     v_ip VARCHAR2(20);

    BEGIN

        SELECT TRIM(A.LLAVE_TAB_GRAL)  INTO vDirec
        FROM PBL_TAB_GRAL A
        WHERE A.ID_TAB_GRAL=319;

        SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
        FROM DUAL;

         mesg_body := mesg_body||'<BR>'||'SE GENERO UN NUEVO SOBRE EN LOCAL : '||cCodSobre|| '.<BR>' ||
                   'IP :'||v_ip;

              FARMA_UTILITY.envia_correo(cCodGrupoCia,cCodLocal,
                                   vDirec,
                                   'CONFIRMACION DE CREACION DE SOBRE ',
                                   'CONFIRMACION',
                                   mesg_body,
                                   '');

    END;


   /*********************************************************************************/

  FUNCTION CAJ_F_PERMITE_MAS_SOBRES(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN CHAR
  IS
    valor CHAR(1):='N';
  BEGIN

       SELECT TRIM(A.LLAVE_TAB_GRAL)INTO valor
       FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL=349;


    RETURN valor;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
     valor:='N';
     RETURN valor;
  END;

 /****************************************************************************************************************************/
  FUNCTION CAJ_F_PERMTE_CONTROL_SOBRE(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR)
  RETURN CHAR
    IS
    vIndControlSobre  varchar2(1);
    vIndSobreParcial  CHAR(1);--asosa
    ind CHAR(1);
   BEGIN

     BEGIN
      --SELECT NVL(TRIM(P.DESC_CORTA),' ') INTO vIndControlSobre
      SELECT NVL(TRIM(P.Llave_Tab_Gral),'N') INTO vIndControlSobre--ASOSA, 02.06.2010
      FROM PBL_TAB_GRAL P
      WHERE P.ID_TAB_GRAL=317;

      SELECT NVL(TRIM(P.Desc_Corta),'N') INTO vIndSobreParcial--ASOSA, 04.06.2010
      FROM PBL_TAB_GRAL P
      WHERE P.ID_TAB_GRAL=366;

      IF(vIndControlSobre = 'S' AND vIndSobreParcial = 'S')THEN
         ind := 'S';
      ELSE
          ind:= 'N';
      END IF;

     EXCEPTION WHEN OTHERS THEN
       ind :='N';
       RETURN ind;
     END;
     RETURN ind;
   END;

 /****************************************************************************************************************************/
  FUNCTION CAJ_F_PERMTE_CONTROL_PROSEGUR(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR)
  RETURN CHAR
    IS
    vIndProsegur  varchar2(1);
   BEGIN

     --LLEIVA 14-Ene-2014 Se modifico para que el indicador sea si existe alguna enpresa para realizar el portavalor
     BEGIN
      select DECODE(COD_ETV,null,'N',' ','N','S')
      INTO vIndProsegur
      from PBL_ETV_LOCAL
      WHERE COD_GRUPO_CIA=cCodGrupoCia_in
      AND COD_LOCAL=cCodLocal_in
    AND EST_ETV_LOCAL = 'A';

     EXCEPTION WHEN OTHERS THEN
       vIndProsegur :='N';
     END;
     RETURN vIndProsegur;
   END;
   /* **********************************************************************************/
  FUNCTION CAJ_F_IS_PED_CONV_MF_BTL(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in IN CHAR
                                   )
  RETURN CHAR
    IS
    vInd  varchar2(1);
   BEGIN

     BEGIN
        SELECT case
               when  nvl(CAB.IND_CONV_BTL_MF,'N') = 'S' and CAB.COD_CONVENIO is not null then 'S'
               else 'N'
               end is_ped_conv_mf_btl
        into   vInd
          FROM VTA_PEDIDO_VTA_CAB CAB
         WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
         and   cab.cod_local = cCodLocal_in
         and   cab.num_ped_vta = cNumPedVta_in;

     EXCEPTION WHEN OTHERS THEN
       vInd :='N';
     END;
     RETURN vInd;
   END;

  /* ************************************************************************ */
  PROCEDURE CAJ_GRAB_NEW_FORM_PAGO_PEDIDO(cCodGrupoCia_in           IN CHAR,
                                          cCodLocal_in              IN CHAR,
                                         cCodFormaPago_in        IN CHAR,
                                          cNumPedVta_in             IN CHAR,
                                          nImPago_in                IN NUMBER,
                                         cTipMoneda_in           IN CHAR,
                                         nValTipCambio_in         IN NUMBER,
                                          nValVuelto_in            IN NUMBER,
                                          nImTotalPago_in          IN NUMBER,
                                         cNumTarj_in              IN CHAR,
                                         cFecVencTarj_in         IN CHAR,
                                         cNomTarj_in              IN CHAR,
                                         cCanCupon_in              IN NUMBER,
                                         cUsuCreaFormaPagoPed_in IN CHAR,
                                         cDNI_in              IN CHAR,
                                         cCodAtori_in         IN CHAR,
                                         cLote_in             IN CHAR) IS
  BEGIN
    INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,
                                      COD_LOCAL,
                                      COD_FORMA_PAGO,
                                      NUM_PED_VTA,
                                      IM_PAGO,
                                      TIP_MONEDA,
                                      VAL_TIP_CAMBIO,
                                      VAL_VUELTO,
                                      IM_TOTAL_PAGO,
                                      NUM_TARJ,
                                      FEC_VENC_TARJ,
                                      NOM_TARJ,
                                      USU_CREA_FORMA_PAGO_PED,
                                      CANT_CUPON,
                                      DNI_CLI_TARJ,
                                      COD_AUTORIZACION,
                                      COD_LOTE)
                              VALUES (cCodGrupoCia_in,
                                      cCodLocal_in,
                                      cCodFormaPago_in,
                                      cNumPedVta_in,
                                      nImPago_in,
                                      cTipMoneda_in,
                                      nValTipCambio_in,
                                      nValVuelto_in,
                                      nImTotalPago_in,
                                      cNumTarj_in,
                                      TO_DATE(cFecVencTarj_in,'dd/MM/yyyy'),
                                      cNomTarj_in,
                                      cUsuCreaFormaPagoPed_in,
                                      cCanCupon_in,
                                      cDNI_in,
                                      cCodAtori_in,
                                      cLote_in);
END;
  /* ************************************************************************ */
  PROCEDURE SAVE_HIST_FORM_PAGO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNum_Ped_Vta_in IN CHAR,
                                cUsuModFormaPagoPed_in IN CHAR)
  IS
  vSec  NUMBER;
  BEGIN

  select ((nvl(max(sec_hist),0))+1)
  INTO    vSec
  from VTA_FORMA_PAGO_PEDIDO_HIST
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND   cod_local = cCodLocal_in
      AND   num_ped_vta = cNum_Ped_Vta_in;

  INSERT INTO VTA_FORMA_PAGO_PEDIDO_HIST (COD_GRUPO_CIA, COD_LOCAL, COD_FORMA_PAGO, NUM_PED_VTA, IM_PAGO, TIP_MONEDA, VAL_TIP_CAMBIO, VAL_VUELTO, IM_TOTAL_PAGO, NUM_TARJ,
  FEC_VENC_TARJ,  NOM_TARJ, FEC_CREA_FORMA_PAGO_PED, USU_CREA_FORMA_PAGO_PED, FEC_MOD_FORMA_PAGO_PED, USU_MOD_FORMA_PAGO_PED,
  CANT_CUPON, TIPO_AUTORIZACION, COD_LOTE, COD_AUTORIZACION, DNI_CLI_TARJ, COD_CIA, USU_HIST_FORMA_PAGO_PED, FEC_HIST_FORMA_PAGO_PED,
  SEC_HIST)
        SELECT COD_GRUPO_CIA, COD_LOCAL, COD_FORMA_PAGO, NUM_PED_VTA, IM_PAGO, TIP_MONEDA, VAL_TIP_CAMBIO, VAL_VUELTO, IM_TOTAL_PAGO,
            NUM_TARJ, FEC_VENC_TARJ, NOM_TARJ, FEC_CREA_FORMA_PAGO_PED, USU_CREA_FORMA_PAGO_PED, FEC_MOD_FORMA_PAGO_PED,
            USU_MOD_FORMA_PAGO_PED, CANT_CUPON, TIPO_AUTORIZACION, COD_LOTE, COD_AUTORIZACION, DNI_CLI_TARJ, COD_CIA,
            cUsuModFormaPagoPed_in , SYSDATE,vSec
        FROM VTA_FORMA_PAGO_PEDIDO
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND   cod_local = cCodLocal_in
        AND   num_ped_vta = cNum_Ped_Vta_in;

  END;

  /* ************************************************************************ */
  PROCEDURE DEL_FORM_PAGO_PED(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNum_Ped_Vta_in IN CHAR)
  IS
  BEGIN

  DELETE FROM VTA_FORMA_PAGO_PEDIDO
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND   cod_local = cCodLocal_in
        AND   num_ped_vta = cNum_Ped_Vta_in;

  END;


  /* ************************************************************************ */


 FUNCTION GET_FPAGO_PEDIDO(cNum_Pedido_in IN CHAR)
  RETURN FarmaCursor IS
  curFpagoPed FarmaCursor;
  BEGIN
  OPEN curFpagoPed FOR
      SELECT ( fpago.cod_forma_pago ) AS resultado
      FROM VTA_FORMA_PAGO_PEDIDO fpago_ped
      JOIN VTA_FORMA_PAGO fpago
      ON (fpago_ped.cod_forma_pago = fpago.cod_forma_pago)
      WHERE fpago_ped.NUM_PED_VTA = cNum_Pedido_in;
   RETURN curFpagoPed;
 END;

  /* ************************************************************************ */



  FUNCTION CAJ_OBTENER_FECHAMAX_APERTURA(cCodGrupoCia_in IN CHAR,
                                         cCod_Local_in   IN CHAR,
                                        nNumCaj_in      IN NUMBER)
    RETURN DATE
  IS
    v_dMaxFec  DATE;
  BEGIN
       SELECT MAX(FEC_DIA_VTA)
       INTO   v_dMaxFec
       FROM   CE_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
              COD_LOCAL     = cCod_Local_in   AND
              NUM_CAJA_PAGO = nNumCaj_in      AND
              TIP_MOV_CAJA  = TIP_MOV_APERTURA;



  RETURN v_dMaxFec;
  END;
 /*      ******************************************************************** */

 PROCEDURE CAJ_CAM_FORM_PAGO_TARJ (
                                           cGrupCia_in      IN CHAR,
                                           cLocal_in        IN CHAR,
                                           cUsuaario_in     IN VARCHAR2,
                                           cmonto_in        IN VARCHAR2,
                                           cIdVoucher_in    IN VARCHAR2,--AP
                                           cFechaVoucher_in IN VARCHAR2,
                                           cHoraVoucher_in  IN VARCHAR2,
                                           cNumPedNew_in    IN VARCHAR2,
                                           cFormaPagNew_in  IN VARCHAR2,
                                           cTipOrigen_in    IN VARCHAR2
                                            )

IS

 v_NUM_PED_V vta_forma_pago_pedido.num_ped_vta%TYPE;
 v_COD_FP_v  vta_forma_pago_pedido.cod_forma_pago%TYPE;
 v_NUM_TARJ  vta_forma_pago_pedido.num_tarj%TYPE;
 --v_COD_AP    vta_forma_pago_pedido.cod_autorizacion%TYPE;
 vcant       NUMBER(2);
 --curNumPiD FarmaCursor;
 v_est_ped vta_pedido_vta_cab.est_ped_vta%TYPE;
 v_ind_anu vta_pedido_vta_cab.ind_pedido_anul%TYPE;


BEGIN


           /*SELECT  DISTINCT pp.NUM_PED_VTA,pp.Num_Tarj_Original --, COUNT(*)
                   INTO v_NUM_PED_V,v_NUM_TARJ
                        -- , vcant
            FROM    vta_proceso_pinpad_visa pp
            WHERE   pp.dsc_msj_fin_ope_trama like '%'||cIdVoucher_in||'%'  AND
            pp.dsc_msj_fin_ope_trama like '%'||cHoraVoucher_in||'%' AND
            pp.dsc_msj_fin_ope_trama like '%'||cFechaVoucher_in||'%'
            GROUP BY pp.NUM_PED_VTA;

            SELECT est_ped_vta, ind_pedido_anul
                   INTO
                   v_est_ped, v_ind_anu
            FROM VTA_PEDIDO_VTA_CAB
            WHERE num_ped_vta = v_NUM_PED_V;

            SELECT DISTINCT cod_forma_pago INTO v_COD_FP_v
            FROM vta_fpago_tarj a
            WHERE  v_NUM_TARJ LIKE trim(a.bin)||'%'
                   AND TIP_ORIGEN_PAGO = cTipOrigen_in;


        IF v_NUM_PED_V IS NOT NULL then

             IF (
              (v_est_ped='N' AND v_ind_anu='S') OR
              (v_est_ped='N' AND v_ind_anu='N') ) THEN

                update vta_forma_pago_pedido
                set cod_forma_pago =  v_COD_FP_v,   --00083 visa, 00084 mc --CMR 0024 -- SOLES: 00001
                num_tarj = v_NUM_TARJ   ,
                cod_autorizacion = cIdVoucher_in , -- codigo AP
                usu_mod_forma_pago_ped = cUsuaario_in , --- USUARIO
                fec_mod_forma_pago_ped = sysdate
                where num_ped_vta = cNumPedNew_in     -- NUEVO PEDIDO (DATOS DE LA INTERFAZ)
                and cod_forma_pago = cFormaPagNew_in; --FORMA DE PAGO  (NUEVO PEDIDO)-(DATOS DE LA INTERFAZ)

             ELSE
                    IF( (v_est_ped='C' AND v_ind_anu='S') OR
                      (v_est_ped='C' AND v_ind_anu='N') ) THEN

                      update vta_forma_pago_pedido
                      set cod_forma_pago =  v_COD_FP_v,   --00083 visa, 00084 mc --CMR 0024 -- SOLES: 00001
                      num_tarj = v_NUM_TARJ   ,
                      cod_autorizacion = cIdVoucher_in ,
                      usu_mod_forma_pago_ped = cUsuaario_in , --- USUARIO
                      fec_mod_forma_pago_ped = sysdate
                      where num_ped_vta = cNumPedNew_in     -- NUEVO PEDIDO (DATOS DE LA INTERFAZ)
                      and cod_forma_pago = cFormaPagNew_in;

                      update vta_forma_pago_pedido
                      set cod_forma_pago = cFormaPagNew_in ,--00083 visa, 00084 mc --CMR 0024 -- SOLES: 00001
                      num_tarj = null ,
                      cod_autorizacion = null,
                      usu_mod_forma_pago_ped = cUsuaario_in ,
                      fec_mod_forma_pago_ped = sysdate
                      where num_ped_vta = v_NUM_PED_V -- PVoucher
                      and cod_forma_pago = v_COD_FP_v; --Forma de Pago Condicion

                    END IF;
              END IF;

        ELSE

        vcant:=2;


        END IF;*/

       COMMIT;
END;

  /*******************************************************/

  FUNCTION CAJ_VERIFICA_REC_VIRTUAL(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                       cNumPedVta_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nCant    NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nCant
       FROM   LGT_PROD_VIRTUAL VIR
       WHERE  VIR.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    VIR.TIP_PROD_VIRTUAL IN ('R') --solo recargas virtuales
       AND    VIR.COD_PROD IN (SELECT COD_PROD
                                                           FROM   VTA_PEDIDO_VTA_DET DET,
                                                                           VTA_PEDIDO_VTA_CAB CAB
                                                           WHERE  CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                                                           AND CAB.COD_LOCAL = DET.COD_LOCAL
                                                           AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                                                           AND    DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                                           AND    DET.COD_LOCAL = cCodLocal_in
                                                           AND    DET.NUM_PED_VTA = cNumPedVta_in
                                                           --ASOSA  - 03/12/2014 - RECAR  AND    CAB.COD_RPTA_RECARGA = COD_EXITO
                               );
    RETURN v_nCant;
  END;

  /*******************************************************/


  FUNCTION CAJ_NUM_COMP_PAGO_IMPR_PEDIDO(cCodGrupoCia_in   IN CHAR,
                                                 cCodLocal_in     IN CHAR,
                                                 cNumPed_in      IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    TipComp CHAR(2);
  BEGIN
-- KMONCADA 04.11.2014 OBTENDRA LOS NUMEROS DE COMPROBANTES SEGUN ELECTRONICO O NO
    OPEN curCaj FOR
    -- CONSULTAR CON RICARDO SI EL CAMBIO ES NECESARIO PARA OTRAS FUNCIONES
    -- 21.10.2014 DUBILLUz
--    SELECT SUBSTR(A.NUM_COMP_PAGO,0,3)|| 'Ã' ||SUBSTR(A.NUM_COMP_PAGO,4)
      SELECT DECODE(A.COD_TIP_PROC_PAGO,'1',' ',SUBSTR(A.NUM_COMP_PAGO,0,3))|| 'Ã' ||
             DECODE(A.COD_TIP_PROC_PAGO, '1', A.NUM_COMP_PAGO_E,SUBSTR(A.NUM_COMP_PAGO,4))
    --RH: 21.10.2014 FAC-ELECTRONICA
      --FARMA_UTILITY.GET_T_SERIE_COMPROBA(A.COD_TIP_PROC_PAGO,A.NUM_COMP_PAGO_E,A.NUM_COMP_PAGO)
      || 'Ã' ||
      FARMA_UTILITY.GET_T_CORR_COMPROBA(A.COD_TIP_PROC_PAGO,A.NUM_COMP_PAGO_E,A.NUM_COMP_PAGO)
    FROM   VTA_COMP_PAGO A
    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   A.COD_LOCAL = cCodLocal_in
    AND   A.NUM_PED_VTA = cNumPed_in
--    AND   A.SEC_COMP_PAGO<>A.NUM_COMP_PAGO ;
   AND ((NVL(A.COD_TIP_PROC_PAGO,'0') = '0' AND A.SEC_COMP_PAGO <> A.NUM_COMP_PAGO) OR
           (A.COD_TIP_PROC_PAGO = '1' AND A.NUM_COMP_PAGO_E IS NOT NULL)
          )
--RHERRERA 10.11.2014  comprantes sin con fecha de impresion NULL
    AND    A.FECH_IMP_COBRO IS NULL          
          ;
          --RH: 21.10.2014 FAC-ELECTRONICA
      --FARMA_UTILITY.GET_T_COMPROBANTE(A.COD_TIP_PROC_PAGO,A.NUM_COMP_PAGO_E,A.NUM_COMP_PAGO);
    RETURN curCaj;
  END;

    -- Author  : LTAVARA
    -- Created : 25/08/2014 04:50:35 p.m.
    -- Purpose : FUNCION LISTA DE COMPROBANTE DE PAGO POR PEDIDO
   FUNCTION CAJ_F_CUR_LISTA_COMP_PAGO(cCodGrupoCia_in  IN VARCHAR2,
                                         cCodLocal_in     IN VARCHAR2,
                                         cNumPed_in       IN VARCHAR2
                                         )
        RETURN FarmaCursor IS
        curComp FarmaCursor;

      BEGIN

        OPEN curComp FOR
          SELECT P.NUM_COMP_PAGO || 'Ã' ||
                 P.SEC_COMP_PAGO || 'Ã' ||
                 P.TIP_COMP_PAGO || 'Ã' ||
                 NVL(P.TIP_CLIEN_CONVENIO,0) || 'Ã' ||
                 NVL(P.NUM_COMP_PAGO_E,0)
            FROM VTA_COMP_PAGO P
           WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
           AND   P.COD_LOCAL = cCodLocal_in
           AND   P.NUM_PED_VTA = cNumPed_in
           AND   P.TIP_COMP_PAGO != '03'--LISTA TODOS LOS DOCUMENTOS MENOS LAS GUIAS
            ORDER BY  P.NUM_COMP_PAGO ASC ;

        RETURN curComp;
    END;


   -- Author  : LTAVARA
    -- Created : 25/07/2014 06:50:35 p.m.
    -- Purpose : Modifica el tipo de documento del comprobante
   FUNCTION CAJ_MODIFICAR_TIP_COMP_PAGO(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                   vTipCompPaqo VARCHAR2
                                  )
        RETURN NUMBER AS

        v_vRespuesta number := 1;--exito

    BEGIN
        BEGIN
            UPDATE VTA_COMP_PAGO COMP
        SET COMP.TIP_COMP_PAGO = vTipCompPaqo
        WHERE
             COMP.COD_GRUPO_CIA=cGrupoCia
        AND
             COMP.COD_LOCAL=cCodLocal
        AND
             COMP.NUM_PED_VTA=cNumPedidoVta
        AND
             COMP.SEC_COMP_PAGO=cSecCompPago;
            EXCEPTION
            WHEN OTHERS THEN
                v_vRespuesta:= 0;
        END;
        RETURN v_vRespuesta;
    END CAJ_MODIFICAR_TIP_COMP_PAGO;

    -- Author  : CHUANES
    -- Created : 01/08/2014 07:10 PM
    -- Purpose : Obtiene la ruta de la impresora termica asignada.
    /*************************************************************/

   FUNCTION GET_NOMBRE_IMPRES_TERMICA(cCodGrupoCia_in  IN CHAR,
                            cCodLocal_in     IN CHAR,
                     cIp_in IN String )
  RETURN VARCHAR2 AS


    vNombre VARCHAR2(200):=NULL;
  BEGIN
   SELECT  DESC_IMPR_LOCAL_TERM
    INTO  vNombre
    from  VTA_IMPR_LOCAL_TERMICA TERM , VTA_IMPR_IP IMP
      WHERE
      IMP.COD_GRUPO_CIA=TERM.COD_GRUPO_CIA AND
      IMP.COD_LOCAL=TERM.COD_LOCAL AND
      IMP.SEC_IMPR_LOC_TERM =TERM.SEC_IMPR_LOC_TERM
      AND IMP.COD_GRUPO_CIA=cCodGrupoCia_in
      AND IMP.COD_LOCAL=cCodLocal_in
      AND IMP.IP=cIp_in;

        RETURN vNombre;
        END;
    -- Author  : CHUANES
    -- Created : 01/08/2014 07:10 PM
    -- Purpose : Obtiene el modelo de impresora.
  /***********************************************************/


  FUNCTION GET_MODELO_IMP_TERMICA(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR, 
                                  cIp_In IN String)
  RETURN VTA_IMPR_LOCAL_TERMICA.TIPO_IMPR_TERMICA%TYPE AS
    vModelo VTA_IMPR_LOCAL_TERMICA.TIPO_IMPR_TERMICA%TYPE;-- VARCHAR2(200):=NULL;
  BEGIN
           /*SELECT  TB.DESC_CORTA  INTO vModelo
     FROM  VTA_IMPR_IP IMP  , VTA_IMPR_LOCAL_TERMICA  ILT,PBL_TAB_GRAL TB WHERE
    IMP.COD_GRUPO_CIA=ILT.COD_GRUPO_CIA AND
    IMP.COD_LOCAL=ILT.COD_LOCAL AND
    IMP.SEC_IMPR_LOC_TERM=ILT.SEC_IMPR_LOC_TERM AND
    ILT.TIPO_IMPR_TERMICA=TB.LLAVE_TAB_GRAL AND
    TB.COD_TAB_GRAL   = 'MODELO_IMP_TERMICA'  AND
    COD_APL           = 'PTO_VENTA' AND
    IMP.IP=cIp_In;*/
    -- KMONCADA 31.10.2014 DEVUELVE CODIGO DE TIPO DE IMPRESION TERMICA
    SELECT ilt.tipo_impr_termica
    INTO vModelo
    FROM VTA_IMPR_IP IMP, VTA_IMPR_LOCAL_TERMICA ILT
    WHERE IMP.COD_GRUPO_CIA = ILT.COD_GRUPO_CIA
    AND IMP.COD_LOCAL = ILT.COD_LOCAL
    AND IMP.SEC_IMPR_LOC_TERM = ILT.SEC_IMPR_LOC_TERM
    AND IMP.IP = cIp_In;
    
    RETURN    vModelo;
  END;

  /***********************************************************/
  FUNCTION IS_CONVENIO_MIXTO(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                           cNumPedidoVta VARCHAR2)
  RETURN CHAR AS
    vFlag CHAR(1);
    vCantidad INTEGER;
    vCodConvenio MAE_CONVENIO.COD_CONVENIO%TYPE;
  BEGIN
    
    SELECT NVL(CAB.COD_CONVENIO,'*')
    INTO   vCodConvenio
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   CAB.COD_LOCAL     = cCodLocal_in
    AND   CAB.NUM_PED_VTA   = cNumPedidoVta;

    IF vCodConvenio!='*' THEN
      SELECT NVL(CONV.FLG_CONV_MIXTO,'0')
      INTO vFlag
      FROM MAE_CONVENIO CONV
      WHERE CONV.COD_CONVENIO = vCodConvenio;
    ELSE
      vFlag := 0;
    END IF;
    /*
    vFlag := '1';

    SELECT COUNT(1)
    INTO vCantidad
    FROM   REL_FORMA_PAGO_CONV FP,
           MAE_CONVENIO CONV,
           VTA_PEDIDO_VTA_CAB CAB
    WHERE  FP.COD_FORMA_PAGO = '00050'
    AND    FP.COD_CONVENIO   = CONV.COD_CONVENIO
    AND    CONV.FLG_ACTIVO   = '1'
    AND    CONV.COD_CONVENIO = CAB.COD_CONVENIO
    AND    CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CAB.COD_LOCAL     = cCodLocal_in
    AND    CAB.NUM_PED_VTA   = cNumPedidoVta;

    IF vCantidad=0 THEN
      vFlag := '0';
    END IF;*/

    RETURN vFlag;
  END;


  FUNCTION IND_GET_PED_ING_MANUAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR) RETURN VARCHAR2 IS
    v_vNom VARCHAR2(150);
  BEGIN
    SELECT nvl(c.ind_comp_manual, 'N')
      into v_vNom
      FROM vta_pedido_vta_cab C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal_in
       AND c.num_ped_vta = cNumPedVta_in;
    RETURN v_vNom;
  END;

  FUNCTION IND_GET_CADENA_MANUAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR) RETURN VARCHAR2 IS
    v_vNom VARCHAR2(150);
  BEGIN
    SELECT  tip_comp_manual||'@'||
            serie_comp_manual||'@'||
            num_comp_manual
      into v_vNom
      FROM vta_pedido_vta_cab C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal_in
       AND c.num_ped_vta = cNumPedVta_in;
    RETURN v_vNom;
  END;

  FUNCTION IND_VALIDA_COMP_MANUAL(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cTipComp_in   IN CHAR,
                                  cSerieComp_in   IN CHAR,
                                  cNumComp_in   IN CHAR
                                 ) RETURN VARCHAR2 is
    nExisteSerie number;
    nResultado varchar2(2);
    nExisteComp  number;
  BEGIN

     SELECT count(1)
     into   nExisteSerie
     FROM   VTA_SERIE_LOCAL S
     WHERE  S.COD_GRUPO_CIA = cCodGrupoCia_in
     AND    S.COD_LOCAL = cCodLocal_in
     AND    S.TIP_COMP = cTipComp_in
     and    s.num_serie_local = cSerieComp_in;

     if nExisteSerie > 0 then

         SELECT count(1)
         into   nExisteComp
         FROM   vta_comp_pago c
         WHERE  c.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    c.COD_LOCAL = cCodLocal_in
         AND    c.tip_comp_pago = cTipComp_in
         and    c.num_comp_pago = cSerieComp_in||cNumComp_in;

         if nExisteComp > 0 then
           nResultado := 'N';
         else
           nResultado := 'S';
         end if;

     else
         nResultado := 'N';
     end if;

     return nResultado;
  END;

  FUNCTION FN_EXISTE_COMPROBANTE_E(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cTipComp_in   IN CHAR,
                                   cNumComp_in   IN CHAR
                                  )RETURN INTEGER IS
    nCantComprobante INTEGER;
    vTipoComprobante CHAR(2);
  BEGIN
    vTipoComprobante := cTipComp_in;
    IF vTipoComprobante = '05' THEN
      vTipoComprobante := '01';
    END IF;
    IF vTipoComprobante = '06' THEN
      vTipoComprobante := '02';
    END IF;

    SELECT COUNT(1)
    INTO nCantComprobante
    FROM VTA_COMP_PAGO
    WHERE COD_LOCAL = cCodLocal_in
    AND   COD_GRUPO_CIA = cCodGrupoCia_in
    AND   TIP_COMP_PAGO = vTipoComprobante
    AND   NUM_COMP_PAGO_E = cNumComp_in;

    RETURN nCantComprobante;
  END;
  
  PROCEDURE P_ACT_FCH_IMPRESION(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta      IN CHAR,
                                   cTipComp_in   IN CHAR,
                                   cSecComPago   IN CHAR,
                                   cIdUsu        IN VARCHAR2
                                  )
  IS
    
  BEGIN
    UPDATE VTA_COMP_PAGO X
    SET    X.FECH_IMP_COBRO = SYSTIMESTAMP ,
           X.USU_MOD_COMP_PAGO = cIdUsu,
           X.FEC_MOD_COMP_PAGO = SYSDATE    
    WHERE  X.COD_GRUPO_CIA     = cCodGrupoCia_in
    AND    X.COD_LOCAL         = cCodLocal_in
    AND    X.NUM_PED_VTA       = cNumPedVta
    -- KMONCADA 09.04.2015 LA ACTUALIZACION SE REALIZARA POR SEC_COMP_PAGO
--    AND    X.TIP_COMP_PAGO     = cTipComp_in
    AND    X.SEC_COMP_PAGO     = cSecComPago;
  END;

    -- Author  : RHERRERA
    -- Created : 18/11/2014 
    -- Purpose : FUNCION LISTA DE CABECERA DE PEDIDO
   FUNCTION CAJ_F_CUR_LISTA_COMP_PAGO_GUI(cCodGrupoCia_in  IN VARCHAR2,
                                         cCodLocal_in     IN VARCHAR2,
                                         cNumPed_in       IN VARCHAR2
                                         )
        RETURN FarmaCursor IS
        curComp FarmaCursor;

      BEGIN

        OPEN curComp FOR
          SELECT P.TIP_COMP_PAGO
            FROM VTA_PEDIDO_VTA_CAB P
           WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
           AND   P.COD_LOCAL = cCodLocal_in
           AND   P.NUM_PED_VTA = cNumPed_in
           AND   P.TIP_COMP_PAGO = '03'--LISTA TODAS LAS GUIAS
           ;

        RETURN curComp;
    END;
  
  FUNCTION GET_NOMBRE_FILE_COMP_ELEC(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cNumPed_in       IN CHAR,
                                     cSecCompPago_in  IN CHAR)
  RETURN VARCHAR2 IS
    vResultado VARCHAR2(50);
  BEGIN
    SELECT TO_CHAR((SYSDATE),'YYYYMMDD')||'_'||SUBSTR(DESC_COMP,0,1)||'_E_'||CP.NUM_PED_VTA||'_'||CP.NUM_COMP_PAGO_E||'.TXT'
    INTO vResultado
    FROM VTA_COMP_PAGO CP,
         VTA_TIP_COMP TCP
    WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CP.COD_LOCAL       = cCodLocal_in
    AND CP.NUM_PED_VTA     = cNumPed_in
    AND CP.SEC_COMP_PAGO   = cSecCompPago_in
    AND CP.COD_GRUPO_CIA   = TCP.COD_GRUPO_CIA
    AND CP.TIP_COMP_PAGO   = TCP.TIP_COMP;
    
    RETURN vResultado;
  END;
  /************************************************************************************/  
  FUNCTION CAJ_LISTA_COMPROBANT_COMPROBAR(cCodGrupoCia_in   IN CHAR,
                                 cCod_Local_in     IN CHAR,
                          cFecIni_in     IN CHAR,
                          cFecFin_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR

        SELECT  NVL(TO_CHAR(VC.FEC_PED_VTA, 'dd/MM/yyyy HH24:mi:ss'), ' ') || 'Ã' ||
                NVL(TO_CHAR(TP.DESC_COMP), ' ') || 'Ã' ||
                FARMA_UTILITY.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,
                                                  CP.NUM_COMP_PAGO_E,
                                                  CP.NUM_COMP_PAGO) || 'Ã' ||
                NVL(TO_CHAR(VC.NUM_PED_VTA), ' ') || 'Ã' ||
                NVL(TRIM(TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,
                                 '999,999,990.00')),' ') || 'Ã' || 
                NVL(CP.COD_TIP_PROC_PAGO, 0) || 'Ã' || --TIPO DE COMPROBANTE
                CASE NVL(CP.COD_TIP_PROC_PAGO, '0')
                  WHEN '0' THEN SUBSTR(CP.NUM_COMP_PAGO,1,3)
                  WHEN '1' THEN SUBSTR(CP.NUM_COMP_PAGO_E,1,4)
                END || 'Ã' || 
                CP.TIP_COMP_PAGO ||
                FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,
                                                CP.NUM_COMP_PAGO_E,
                                                CP.NUM_COMP_PAGO) || 'Ã' ||
                NVL(TO_CHAR(CP.SEC_COMP_PAGO), ' ') || 'Ã' || 
                CP.TIP_COMP_PAGO || 'Ã' ||
                FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,
                                                CP.NUM_COMP_PAGO_E,
                                                CP.NUM_COMP_PAGO) || 'Ã' ||
                CP.TIP_COMP_PAGO || NVL(TO_CHAR(VC.NUM_PED_VTA), ' ')|| 'Ã' ||
                CASE NVL(CP.COD_TIP_PROC_PAGO, '0')
                  WHEN '0' THEN
                       SUBSTR(CP.NUM_COMP_PAGO,-7)
                  WHEN '1' THEN
                       SUBSTR(CP.NUM_COMP_PAGO_E,-8)
                END
          FROM VTA_PEDIDO_VTA_CAB VC, VTA_TIP_COMP TP, VTA_COMP_PAGO CP

         WHERE VC.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VC.COD_LOCAL = cCod_Local_in
           AND VC.EST_PED_VTA = EST_PED_COBRADO
           AND FEC_CREA_COMP_PAGO BETWEEN
               TO_DATE(cFecIni_in || ' 00:00:00', 'dd/MM/yyyy HH24:mi:ss') AND
               TO_DATE(cFecIni_in || ' 23:59:59', 'dd/MM/yyyy HH24:mi:ss')
           AND TP.TIP_COMP = CP.TIP_COMP_PAGO
           AND TP.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
           AND CP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA
           AND CP.COD_LOCAL = VC.COD_LOCAL
           AND CP.NUM_PED_VTA = VC.NUM_PED_VTA;

    RETURN curDet;
  END;
  
  
  FUNCTION GET_IMPRESORA_TERMICA(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in     IN CHAR, 
                                 cIp_In           IN String)
  RETURN FarmaCursor
  IS
    curImpresora FarmaCursor;
  BEGIN
    OPEN curImpresora FOR
      SELECT TERM.tipo_impr_termica TIPO,
         TERM.DESC_IMPR_LOCAL_TERM IMPRESORA
    FROM VTA_IMPR_LOCAL_TERMICA TERM, 
         VTA_IMPR_IP IMP
   WHERE IMP.COD_GRUPO_CIA = TERM.COD_GRUPO_CIA
     AND IMP.COD_LOCAL = TERM.COD_LOCAL
     AND IMP.SEC_IMPR_LOC_TERM = TERM.SEC_IMPR_LOC_TERM
     AND IMP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND IMP.COD_LOCAL = cCodLocal_in
     AND IMP.IP = cIp_In;
     
     RETURN curImpresora;
  END;
  
  FUNCTION GET_NRO_COMPROBANTE_PAGO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cSecCompPago_in IN CHAR)
  RETURN VARCHAR2
  IS
    vNroComprobante VTA_COMP_PAGO.NUM_COMP_PAGO_E%TYPE;                                   
  BEGIN
    BEGIN 
    SELECT CASE
             WHEN NVL(CP.COD_TIP_PROC_PAGO, '0') = '0' THEN -- PREIMPRESO
              CASE
                WHEN CP.SEC_COMP_PAGO <> CP.NUM_COMP_PAGO THEN
                 CP.NUM_COMP_PAGO
                ELSE
                 ' '
              END
             WHEN NVL(CP.COD_TIP_PROC_PAGO, '0') = '1' THEN -- ELECTRONICO
              CASE
                WHEN CP.NUM_COMP_PAGO_E IS NOT NULL THEN
                 CP.NUM_COMP_PAGO_E
                ELSE
                 ' '
              END
           END
      INTO vNroComprobante
      FROM VTA_COMP_PAGO CP
     WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CP.COD_LOCAL = cCodLocal_in
       AND CP.NUM_PED_VTA = cNumPedVta_in
       AND CP.SEC_COMP_PAGO = cSecCompPago_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20101,'ERROR AL OBTENER EL NUMERO DE COMPROBANTE DE PAGO, VERIFIQUE!!!');
    END;
    RETURN vNroComprobante;
    
   END;  
  FUNCTION F_CHAR_IND_COMP_ELECTRONICO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cNumPedidoVta_in IN CHAR,
                                       cSecComPago_in   IN CHAR)
  RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    BEGIN 
      SELECT NVL(X.COD_TIP_PROC_PAGO, '0')
        INTO vIndicador
        FROM VTA_COMP_PAGO X
       WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
         AND X.COD_LOCAL = cCodLocal_in
         AND X.NUM_PED_VTA = cNumPedidoVta_in
         AND X.SEC_COMP_PAGO = cSecComPago_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20101,'ERROR AL OBTENER INDICADOR DE COMP.ELECTRONICO , VERIFIQUE!!!');
    END;
    RETURN vIndicador;
 END;
 
 
END;
/
