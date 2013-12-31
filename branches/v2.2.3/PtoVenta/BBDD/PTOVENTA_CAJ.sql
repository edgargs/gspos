--------------------------------------------------------
--  DDL for Package PTOVENTA_CAJ
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CAJ" AS
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
  FUNCTION CAJ_OBTIENE_INFO_PEDIDO(cCodGrupoCia_in  IN CHAR,
  		   						               cCodLocal_in    	IN CHAR,
								                   cNumPedDiario_in IN CHAR,
								                   cFecPedVta_in	IN CHAR)
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
  FUNCTION CAJ_REGISTRA_ARQUEO_CAJA(cTipMov_in      IN CHAR,
  							                    cCodGrupoCia_in IN CHAR,
               		   					  		cCod_Local_in 	IN CHAR,
              							  		  nNumCaj_in 		  IN NUMBER,
              							  		  cSecUsu_in 		  IN CHAR,
              							  		  cIdUsu_in		    IN CHAR,
              							  		  nCantBolEmi_in  IN NUMBER,
              							  		  nMonBolEmi_in 	IN NUMBER,
              							  		  nCantFacEmi_in 	IN NUMBER,
              							  		  nMontFacEmi_in 	IN NUMBER,
              							  		  nCantGuiaEmi_in IN NUMBER,
              							  		  nMonGuiaEmi_in 	IN NUMBER,
              							  		  nCantBolAnu_in 	IN NUMBER,
              							  		  nMonBolAnu_in 	IN NUMBER,
              							  		  nCantFacAnu_in 	IN NUMBER,
              							  		  nMonFacAnu_in 	IN NUMBER,
              							  		  nCantGuiaAnu_in IN NUMBER,
              							  		  nMonGuiaAnu_in 	IN NUMBER,
              							  		  nCantBolTot_in 	IN NUMBER,
              							  		  nMonBolTot_in 	IN NUMBER,
              							  		  nCantFactTot_in IN NUMBER,
              							  		  nMonFactTot_in 	IN NUMBER,
              							  		  nCantGuiaTot_in IN NUMBER,
              							  		  nMonGuiaTot_in 	IN NUMBER,
              							  		  nMonTotGen_in	  IN NUMBER,
              							  		  nMonTotAnu_in 	IN NUMBER,
              							  		  nMonTot_in 		  IN NUMBER,
              									    nCantNCBol_in 	IN NUMBER,
              							  		  nMonNCBol_in	  IN NUMBER,
              							  		  nCantNCFact_in 	IN NUMBER,
              							  		  nMonNCFact_in 	IN NUMBER,
              									    nMonNCTot_in	  IN NUMBER,
                                    cIpMovCaja      IN CHAR)
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
  PROCEDURE CAJ_ACTUALIZA_COMPROBANTE_IMPR(cCodGrupoCia_in 	  IN CHAR,
  		   						   	   	   cCodLocal_in    	  IN CHAR,
									   	   cNumPedVta_in   	  IN CHAR,
										   cSecCompPago_in 	  IN CHAR,
										   cTipCompPago_in 	  IN CHAR,
										   cNumCompPago_in 	  IN CHAR,
										   cUsuModCompPago_in IN CHAR);

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
								 nCantComprobantes_in IN NUMBER)
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
                       cNumPed_in       IN CHAR default '')
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
                                  cInd                IN CHAR);

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
END;

/
