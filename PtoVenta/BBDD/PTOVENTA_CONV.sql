--------------------------------------------------------
--  DDL for Package PTOVENTA_CONV
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CONV" AS

  TYPE FarmaCursor IS REF CURSOR;
  C_INDICADOR_NO CHAR(1) := 'N';
  C_INDICADOR_SI CHAR(1) := 'S';
  C_ESTADO_ACTIVO CHAR(1) := 'A';
  C_TITULAR CHAR(1) := 'T';

  C_INICIO_MSG  VARCHAR2(500):= '<html>'||
                                  '<head>'||
                                  '<style type="text/css">'||
                                  '<!--'||
                                  '.style1 {font-size: 8px}'||
                                  '.style3 {'||
                                  '  font-size: 10px;'||
                                   ' font-weight: bold;}'||
                                  '.style4 {font-size: 8px; font-weight: bold; }'||
                                  '.style5 {font-size: 18px;font-weight: bold;}'||
                                  '-->'||
                                  '</style>'||
                                  '</head>'||
                                  '<body>'||
                                  '<table width="436" height="254" border="0" cellpadding="0" cellspacing="0">'||
                                    '<tr>'||
                                      '<td height="56" colspan="5" align="center" class="style5">CONVENIO</td>'||
                                    '</tr>'||
                                    '<tr>'||
                                      '<td valign="top">';


  C_FORMA_PAGO  VARCHAR2(500) := '<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                                  '<tr>'||
                                  '  <td width="50%" class="style4">FORMA </td>'||
                                 -- '  <td width="12%" class="style4">MONEDA </td>'||
                                  '  <td width="12%" class="style4">MONTO </td>'||
                                 -- '  <td width="8%" class="style4">LOTE </td>'||
                                --  '  <td width="18%" class="style4">AUTORIZACION </td>'||
                                  '</tr>';

  C_FIN_FORMA_PAGO  VARCHAR2(100) :='<tr>'||
                                  '<td height="2" colspan="5"></td>'||
                                  '</tr></table>';


  C_FILA_VACIA  VARCHAR2(100) :='<tr> '||
                                  '<td height="2" colspan="3"></td> '||
                                  ' </tr> ';

  C_FIN_MSG     VARCHAR2(500) := '</td>'||
                                  '</tr>'||
                                  '</table>'||
                                  '</body>'||
                                  '</html>';
  --Ptoventa_Conv
  --Descripcion: Lista el maestro de Convenios
  --Fecha       Usuario		Comentario
  --16/03/2006  Paulo     Creación
  --Modificado 22/08/2007 DUBILLUZ
  --Modificado 31/08/2007 DUBILLUZ
 FUNCTION CONV_LISTA_CONVENIOS (cCodGrupoCia_in CHAR,
                                cCodLocal_in CHAR,
                                 cSecUsuLocal_in CHAR
                                )
  RETURN FarmaCursor;

  --Descripcion: Lista los campos a ingresar en cada convenio
  --Fecha       Usuario		Comentario
  --16/03/2006  Paulo     Creación
  --14/03/2008  JCORTEZ  listando campos dependientes
 FUNCTION CONV_LISTA_CONVENIO_CAMPOS(cCodConvenio_in IN CHAR)
  RETURN FarmaCursor ;

  --Descripcion: Lista los Clientes por convenio
  --Fecha       Usuario		Comentario
  --16/03/2006  Paulo     Creación
  --05/02/2008  DUBILLUZ  MODIFICACION
 FUNCTION CONV_LISTA_CLI_CONVENIO(cCodConvenio_in IN CHAR)
  RETURN FarmaCursor ;

  --Descripcion: lISTA LOS CLIENTES DEPENDIENTES DEL CLIENTE POR CONVENIO
  --Fecha       Usuario		Comentario
  --04/02/2008  DUBILLUZ   CREACION
 FUNCTION CONV_LISTA_CLI_DEP_CONVENIO(cCodConvenio_in IN CHAR,
                                  cCodCli_in      IN CHAR)
  RETURN FarmaCursor ;

/**************************************************************/

  --Descripcion: Obtiene el indicador de control de precio del producto
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  FUNCTION CON_OBTIENE_IND_PRECIO(cCodGrupoCia_in CHAR,
                                  cCodProd_in     CHAR) RETURN CHAR;

  --Descripcion: Obtiene el nuevo precio efectuando el descuento del convenio
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  --08/05/2007  LREQUE      Modificación pendiente
  FUNCTION CON_OBTIENE_NVO_PRECIO(cCodGrupoCia_in CHAR,
                                  cCodLocal_in    CHAR,
                                  cCodConv_in     CHAR,
                                  cCodProd_in     CHAR,
                                  nValPrecVta_in  NUMBER) RETURN CHAR;

  --Descripcion: Valida si, el cliente, ha superado o no el limite de crédito asignado
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  FUNCTION CON_OBTIENE_DIF_CREDITO(cCodConv_in     CHAR,
                                   cCodCliente_in  CHAR,
                                   nMonto_in      NUMBER) RETURN CHAR;

  --Descripcion: Agrega
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  --05/02/2008  DUBILLUZ  	Modificacion
  PROCEDURE CON_AGREGA_PEDIDO_CONVENIO(cCodGrupoCia_in   CHAR,
                                         cCodLocal_in      CHAR,
                                         cNumPedVta_in     CHAR,
                                         cCodConvenio_in   CHAR,
                                         cCodCli_in        CHAR,
                                         cUsuCreaPed_in    CHAR,
                                         cNumDocIden_in    CHAR,
                                         cCodTrab_in       CHAR,
                                         cApePatTit_in     CHAR,
                                         cApeMatTit_in     CHAR,
                                         cFecNacTit_in     CHAR,
                                         cCodSol_in        CHAR,
                                         nValPorcDcto_in   NUMBER,
                                         nValPorcCoPago_in NUMBER,
                                         cNumTelefCli_in   CHAR,
                                         cDirecCli_in      CHAR,
                                         cNomDistrito_in   CHAR,
                                         cValCopago_in     NUMBER,
                                         cCodInterno_in    CHAR,
                                         cnomCompleto_in   CHAR ,
                                         cCodCliDep        CHAR,
                                         cCodTrabDep       CHAR);

  --Descripcion: Actualiza el consumo del cliente
  --Fecha       Usuario		Comentario
  --16/03/2007  LREQUE    	Creación
  /**
  JCALLO 09/01/2008
  este procedimiento falta eliminar, no se hice ya que este procedimiento esta
  siendo invocado desde otro procedimiento del paquete ptoventa_caj_anul
  **/
  PROCEDURE CON_ACTUALIZA_CONSUMO_CLI(cCodConvenio_in   CHAR,
                                      cCodCli_in        CHAR,
                                      nMonto_in         NUMBER);

  --Descripcion: Obtiene el valor del copago según un convenio
  --Fecha       Usuario		Comentario
  --20/03/2007  LREQUE    	Creación
  FUNCTION  CON_OBTIENE_COPAGO(cCodConvenio   CHAR,
                               cCodCliente    CHAR,
                               nMonto         NUMBER) RETURN CHAR;

  --Descripcion: Obtiene la forma de pago del convenio
  --Fecha       Usuario		Comentario
  --20/03/2007  LREQUE    	Creación
  FUNCTION CON_OBTIENE_FORMA_PAGO_CONV(cCodGrupoCia   CHAR,
                                       cCodConvenio   CHAR) RETURN CHAR;

  --Descripcion: Graba la forma de pago en una tabla temporal
  --Fecha       Usuario		Comentario
  --20/03/2007  LREQUE    	Creación
  PROCEDURE CON_GRABAR_FORMA_PAGO_PED_CONV(cCodGrupoCia_in 	   IN CHAR,
  	                                       cCodLocal_in    	   IN CHAR,
  					                               cCodFormaPago_in    IN CHAR,
  					                               cNumPedVta_in   	   IN CHAR,
  					                               nImPago_in		       IN NUMBER,
  					                               cTipMoneda_in	     IN CHAR,
  				                                 nValTipCambio_in 	 IN NUMBER,
  					                               nValVuelto_in  	   IN NUMBER,
  					                               nImTotalPago_in 	   IN NUMBER,
  					                               cNumTarj_in  	     IN CHAR,
  					                               cFecVencTarj_in  	 IN CHAR,
  					                               cNomTarj_in  	     IN CHAR,
                                           cCanCupon_in  	     IN NUMBER,
  					                               cUsuCreaFormaPagoPed_in IN CHAR);

  --Descripcion: Graba la forma de pago en una tabla temporal
  --Fecha       Usuario		Comentario
  --21/03/2007  LREQUE    	Creación
FUNCTION CON_OBTIENE_INFO_CONV_PED(cCodGrupoCia_in 	IN CHAR,
  	                               cCodLocal_in    	IN CHAR,
                                   cNumPedVta_in    IN CHAR,
                                   nMontoPedido_in  IN CHAR) RETURN FarmaCursor;

  --Descripcion: Actualiza el pedido
  --Fecha       Usuario		Comentario
  --28/03/2007  LREQUE    	Creación
 PROCEDURE CON_ACTUALIZA_NUM_PED(cCodGrupoCia_in 	 	    IN CHAR,
  	                              cCodLocal_in    	 	    IN CHAR,
                                  cNumPedVta_in           IN CHAR,
                                  cNumPedVtaDel_in        IN CHAR);

 FUNCTION TMP_CON_OBTIENE_INFO_CONV_PED(cCodGrupoCia_in 	 	     IN CHAR,
  	                                     cCodLocal_in    	 	     IN CHAR,
                                         cNumPedVta_in           IN CHAR,
                                         nMontoPedido_in         IN NUMBER) RETURN FarmaCursor;

  --Descripcion: Inserta, a la lista de PRODUCTOS EXCLUIDOS, los productos con PRECIO CONTROLADO
  --Fecha       Usuario		Comentario
  --07/05/2007  LREQUE    	Creación
  PROCEDURE CON_LLENA_LISTA_EXCLUIDOS(cCodGrupoCia_in 	 	    IN CHAR);

  FUNCTION CON_OBTIENE_CREDITO(cCodConv_in     CHAR,
                              cCodCliente_in  CHAR) RETURN CHAR;

  FUNCTION CON_OBTIENE_CREDITO_UTIL(cCodConv_in     CHAR,
                                    cCodCliente_in  CHAR) RETURN CHAR;



-------Ptoventa_Conv
  --Descripcion: Graba la forma de pago en una tabla temporal LOCAL
  --Fecha       Usuario		Comentario
  --20/09/2007   DUBILLUZ   CREACION
  PROCEDURE CON_GRABAR_FP_PED_CONV_LOCAL(cCodGrupoCia_in 	 	     IN CHAR,
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

  --Descripcion: Actualiza el credito disponible del cliente.
  --Fecha       Usuario		Comentario
  --06/03/2008  ERIOS     Creacion
  PROCEDURE CONV_ACTUALIZA_CRED_DISP(cCodConvenio_in   IN CHAR,
                               cCodCliente_in          IN CHAR,
                               cCodGrupoCia_in 	 	  IN CHAR,
  	                           cCodLocal_in    	 	  IN CHAR,
  									   	       cNumPedVta_in   	 	  IN CHAR,
                               nMonto_in            IN NUMBER);

  --Descripcion: Retorna el nombre del cliente.
  --Fecha       Usuario		Comentario
  --13/06/2008  ERIOS     Creacion
  FUNCTION GET_NOMBRE_CLIENTE_NUMDOC(cCodConvenio_in IN CHAR,cNumDoc_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Retorna el valor porcentaje convenio.
  --Fecha       Usuario           Comentario
  --15/12/2008  asolis     Creacion
  FUNCTION CON_PORC_COPAGO_CONV(cCodConvenio_in   IN CHAR)
  RETURN CHAR ;


  --Descripcion: Se obtiene formato de voucher convenio
  --Fecha       Usuario   Comentario
  --15/12/20  JCORTEZ     Creacion
  FUNCTION IMP_DATOS_CONVENIO(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cCodConvenio_in   IN CHAR,
                                cCodCli_in        IN CHAR,
                                cIpServ_in        IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Se obtiene cabecera de voucher convenio
  --Fecha       Usuario   Comentario
  --15/12/20  JCORTEZ     Creacion
  FUNCTION VTA_OBTENER_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in	   IN CHAR,
                               cNumPedVta_in   IN CHAR,
                               cCodConvenio_in IN CHAR,
                               cCodCli_in      IN CHAR)
  RETURN FarmaCursor;

 FUNCTION VTA_OBTENER_DATA1(cCodGrupoCia_in IN CHAR,
  		   				             cCodLocal_in	   IN CHAR,
							               cNumPedVta_in   IN CHAR,
                             cCodConvenio_in IN CHAR,
                             cCodCli_in      IN CHAR)
  RETURN FarmaCursor;

  FUNCTION VTA_OBTENER_DATA2(cCodGrupoCia_in  IN CHAR,
   				                   cCodLocal_in	    IN CHAR,
  	                         cNumPedVta_in    IN CHAR,
                             cCodConvenio_in  IN CHAR,
                             cCodCli_in       IN CHAR)
  RETURN FarmaCursor;
  
  --Descripcion: Obtiene el porcentaje de la forma de pago segun su codigo
  --Fecha       Usuario         Comentario
  --26.03.2013  Luigy Terrazos  Creacion
  FUNCTION CONSUL_PORC_FORM_PAG(cCodConvenio_in   IN CHAR)
  RETURN CHAR ;
END;

/
