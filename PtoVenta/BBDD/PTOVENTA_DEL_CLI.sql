--------------------------------------------------------
--  DDL for Package PTOVENTA_DEL_CLI
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_DEL_CLI" AS

TYPE FarmaCursor IS REF CURSOR;
  TIPO_CALLE_AVENIDA  	  VTA_MAE_DIR.TIP_CALLE%TYPE:='01';
  TIPO_CALLE_JIRON  	  VTA_MAE_DIR.TIP_CALLE%TYPE:='02';
  TIPO_CALLE_CALLE  	  VTA_MAE_DIR.TIP_CALLE%TYPE:='03';
  TIPO_CALLE_PASAJE 	  VTA_MAE_DIR.TIP_CALLE%TYPE:='04';

  LOCAL_DELIVERY        TMP_VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE:='999';
  LOCAL_INSTITUCIONAL   TMP_VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE:='998';

  EST_PED_PENDIENTE  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='P';
	EST_PED_ANULADO  	    VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='N';
	EST_PED_COBRADO  	    VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='C';

  ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';

  INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';

  POS_INICIO		      CHAR(1):='I';

  COD_TIP_COMP_BOLETA    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='01';
	COD_TIP_COMP_FACTURA   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='02';

  TIP_PEDIDO_DELIVERY      VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE:='02';
	TIP_PEDIDO_INSTITUCIONAL VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE:='03';

  NUMERA_PEDIDO_VTA  	   PBL_NUMERA.COD_NUMERA%TYPE:='007';
	NUMERA_PEDIDO_DIARIO   PBL_NUMERA.COD_NUMERA%TYPE:='009';

  COD_TIP_MON_SOLES    CHAR(2) := '01';
	COD_TIP_MON_DOLARES  CHAR(2) := '02';
  DESC_TIP_MON_SOLES   VARCHAR2(10) := 'SOLES';
	DESC_TIP_MON_DOLARES VARCHAR2(10) := 'DOLARES';--570

C_INICIO_MSG_L  VARCHAR2(500):= '<html>'||
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
                                      '<td height="56" colspan="5" align="center" class="style5">DELIVERY</td>'||
                                    '</tr>'||
                                    '<tr>'||
                                      '<td valign="top">';


 C_FORMA_PAGO_L  VARCHAR2(500) := '<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                                  '<tr>'||
                                  '  <td width="50%" class="style4">FORMA </td>'||
                                  '  <td width="12%" class="style4">MONEDA </td>'||
                                  '  <td width="12%" class="style4">MONTO </td>'||
                                  '</tr>';


  C_FIN_FORMA_PAGO_L  VARCHAR2(100) :='<tr>'||
                                     '<td height="2" colspan="5"></td>'||
                                     '</tr></table>';


  C_FIN_MSG_L    VARCHAR2(500) := '</td>'||
                                  '</tr>'||
                                  '</table>'||
                                  '</body>'||
                                  '</html>';

  /*C_INICIO_MSG  VARCHAR2(2000) := '<html><head><style type="text/css">.style3 {font-family: Arial, Helvetica, sans-serif}'||
                                '.style8 {font-size: 24; }.style20 {font-size: 8; }.style9 {font-size: larger}.style12 {font-family: Arial, Helvetica, sans-serif;'||
                                'font-size: larger;font-weight: bold;}</style></head><body>'||
                                '<table width="570" border="0">'||
                                '<tr>'||
                                 '<td width="570" align="center" valign="top"><h1>DELIVERY</h1></td></tr>';

  C_MSG_MED  VARCHAR2(2000):=   '<td></td>'||
                                 '</tr>'||
                                 '<tr>'||
                                 '<td></td>'||
                                 '</tr>'||
                                '</tr>'||
                                '</table>'||
                                '<table width="570" height="450" border="0">'||
                                  '<tr>'||
                                    '<td width="4" >&nbsp;</td>'||
                                    '<td width="4" >&nbsp;</td>'||
                                    '<td>';

  C_FORMA_PAGO  VARCHAR2(2000) := '<table width="570" border="0">'||
                                  '<tr>'||
                                  '  <td width="350">Forma : </td>'||
                                  '  <td width="70" align="left">Moneda : </td>'||
                                  '  <td width="90" align="left">Monto : </td>'||
                                  '  <td width="50" align="left">Lote : </td>'|| --Agregado por DVELIZ 15.12.2008
                                  '  <td width="100" align="left">Autorizacion : </td>'|| --Agregado por DVELIZ 15.12.2008
                                  '</tr>';

  C_FIN_FORMA_PAGO  VARCHAR2(2000) :='</table>';

  C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                  '<td height="2" colspan="3"></td> '||
                                  ' </tr> ';

  C_FIN_MSG     VARCHAR2(2000) := '</td>'||
                                  '</tr>'||
                                  '</table>'||
                                  '</body>'||
                                  '</html>';*/
/*C_INICIO_MSG  VARCHAR2(2000) := '<html>
                                <head>
                                <style type="text/css">
                                <!--
                                .style3 {font-family: Arial, Helvetica, sans-serif}
                                .style8 {font-size: 24; }
                                .style9 {font-size: larger}
                                .style12 {font-family: Arial, Helvetica, sans-serif; font-size: larger; font-weight: bold;}
                                .style20 {font-family: Arial, Helvetica, sans-serif; font-size: 10; font-weight: bold;}
                                </style>
                                </head>
                                <body>
                                <table width="700" border="0">
                                <tr>
                                 <td width="700" align="center" valign="top"><h1>DELIVERY</h1></td>
                                 <tr align="center" >';*/
 /*C_INICIO_MSG  VARCHAR2(2000) := '<html><head>'||
                                /*<style type="text/css">.style3 {font-family: Arial, Helvetica, sans-serif}'||
                                '.style8 {font-size: 24; }.style20 {font-size: 8; }.style9 {font-size: larger}.style12 {font-family: Arial, Helvetica, sans-serif;'||
                                'font-size: larger;font-weight: bold;}</style>

                                '</head><body>'||
                                '<table width="800" border="0">'||
                                '<tr>'||
                                 '<td width="800" align="center" valign="top"><h1>DELIVERY</h1></td></tr>';*/

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
                                      '<td height="56" colspan="5" align="center" class="style5">DELIVERY</td>'||
                                    '</tr>'||
                                    '<tr>'||
                                      '<td valign="top">';
 /* C_MSG_MED  VARCHAR2(32767):=   '<td></td>
                                 </tr>
                                 <tr>
                                 <td></td>
                                 </tr>
                                </tr>
                                </table>
                                <table width="700" height="450" border="0">
                                  <tr>
                                    <td width="4" >&nbsp;</td>
                                    <td width="4" >&nbsp;</td>
                                    <td>
                                ';*/

  C_MSG_MED  VARCHAR2(200):=   '<td></td>'||
                                 '</tr>'||
                                 '<tr>'||
                                 '<td></td>'||
                                 '</tr>'||
                                '</tr>'||
                                '</table>'||
                                '<table width="800" height="450" border="0">'||
                                  '<tr>'||
                                    '<td width="4" >&nbsp;</td>'||
                                    '<td width="4" >&nbsp;</td>'||
                                    '<td>';

  /*C_FORMA_PAGO  VARCHAR2(32767) := '<table width="700" border="1">'||
                                  '<tr>'||
                                  '  <td width="370"><FONT FACE="ARIAL" SIZE="4">Forma :</FONT> </td>'||
                                  '  <td width="100" align="left"><FONT FACE="ARIAL" SIZE="4">Moneda : </FONT></td>'||
                                  '  <td width="80" align="left"><FONT FACE="ARIAL" SIZE="4">Monto :</FONT> </td>'||
                                  '  <td width="50" align="left"><FONT FACE="ARIAL" SIZE="4">Lote :</FONT> </td>'|| --Agregado por DVELIZ 15.12.2008
                                  '  <td width="110" align="left"><FONT FACE="ARIAL" SIZE="4">Autorizacion :</FONT> </td>'|| --Agregado por DVELIZ 15.12.2008
                                  '</tr>';*/
  C_FORMA_PAGO  VARCHAR2(500) := '<table width="100%" border="0" cellpadding="0" cellspacing="0">'||
                                  '<tr>'||
                                  '  <td width="34%" class="style4">FORMA </td>'||
                                  '  <td width="12%" class="style4">MONEDA </td>'||
                                  '  <td width="14%" class="style4">DOLARES </td>'||
                                  '  <td width="14%" class="style4">MONTO</td>'||
                                  '  <td width="8%" class="style4">LOTE </td>'||
                                  '  <td width="18%" class="style4">AUTORIZACION </td>'||
                                  '</tr>';


  C_FIN_FORMA_PAGO  VARCHAR2(100) :='<tr>'||
                                     '<td height="2" colspan="6"></td>'||
                                     '</tr></table>';

  C_FILA_VACIA  VARCHAR2(100) :='<tr> '||
                                  '<td height="2" colspan="3"></td> '||
                                  ' </tr> ';

  C_FIN_MSG     VARCHAR2(500) := '</td>'||
                                  '</tr>'||
                                  '</table>'||
                                  '</body>'||
                                  '</html>';


  --VARIABLE DE ESTADO "G" indica que un pedido de Delivery Automatico fue Generado en el Local
  --Fecha       Usuario		Comentario
  --24/08/2007  Dubilluz   Creación
  ESTADO_GENERADO		  CHAR(1):='G';

  --Descripcion: Lista los Clientes que tienen asociado un numero telefonico
  --Fecha       Usuario		Comentario
  --21/04/2006  Paulo     Creación

  FUNCTION CLI_OBTIENE_CLI_NOMB_DNI(cCodGrupoCia_in	  IN CHAR,
  		   						  	cCodLocal_in	  IN CHAR,
  		   						    cTelefono_in      IN CHAR,
									c_TipoTelefono_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Muestra la direccion y el telefono de o
  --los clientes asociados al mismo telefono
  --Fecha       Usuario		Comentario
  --21/04/2006  Paulo     Creación
  FUNCTION CLI_OBTIENE_CLI_TELF_DIR(cCodGrupoCia_in	  IN CHAR,
  		   						  	cCodLocal_in	  IN CHAR,
  		   						    cTelefono_in      IN CHAR,
									c_TipoTelefono_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Busca a un cliente por Dni y Apellido
  --Fecha       Usuario		Comentario
  --24/04/2006  Paulo     Creación
  FUNCTION CLI_BUSCA_DNI_APELLIDO_CLI(cCodGrupoCia_in  IN CHAR,
  		   						 	  cCodLocal_in	   IN CHAR,
  		   						 	  cDniApellido_in  IN CHAR,
									  cTipoBusqueda_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Agrega el detalle del cliente asociado a  una direccion con un telefono
  --Fecha       Usuario		Comentario
  --24/04/2006  Paulo     Creación
  PROCEDURE CLI_AGREGA_DETALLE_DIRECCION(cCodLocal_in IN CHAR,
  		   							    cCodDir_in IN CHAR,
  		   								cCodGrupoCia_in IN CHAR,
										cCodCli_in IN CHAR);

  --Descripcion: Agrega un nuevo cliente
  --Fecha       Usuario		Comentario
  --25/04/2006  Paulo     Creación
  FUNCTION CLI_AGREGA_CLIENTE(cCodGrupoCia_in CHAR,
  		   					  cCodLocal_in CHAR,
							  cCodNumera_in CHAR,
							  cNomCli_in CHAR,
							  cApePatCli_in CHAR,
							  cApeMatCli_in CHAR,
							  cTipDocIdent_in CHAR,
							  cNumDocIdent_in CHAR,
							  cIndCliJur_in CHAR,
							  cUsuCreaCli_in CHAR)
  RETURN CHAR;

  --Descripcion: Agrega una nueva direccion al maestro de direcciones
  --Fecha       Usuario		Comentario
  --25/04/2006  Paulo     Creación
  FUNCTION CLI_AGREGA_DIRECCION_CLIENTE(cCodGrupoCia_in CHAR,
  		   							   cCodLocal_in	   CHAR,
									   cCodNumera_in		CHAR,
									   cTipCalle_in		CHAR,
									   cNomCalle_in		CHAR,
									   cNumCalle_in		CHAR,
									   cNomUrb_in	CHAR,
									   cNomDistrito_in		CHAR,
									   cNumInt_in			CHAR,
									   cRefDirec_in		CHAR,
									   cUsuCreaDir_in		CHAR)
  RETURN CHAR;


  --Descripcion: Agrega un nuevo telefono al maestro de telefonos
  --Fecha       Usuario		Comentario
  --25/04/2006  Paulo     Creación
  FUNCTION CLI_AGREGA_TELEFONO_CLIENTE(cCodGrupoCia_in CHAR,
  		   							   cCodLocal_in	   CHAR,
									   cCodDir_in	   CHAR,
									   ccodNumera_in	   CHAR,
									   cNumTelefono_in	   CHAR,
									   cTipTelefono_in	   CHAR,
									   cUsuCreaTel_in	   CHAR)
  RETURN CHAR;

  --Descripcion: Modifica a un cliente
  --Fecha       Usuario		Comentario
  --25/04/2006  Paulo     Creación
  FUNCTION cli_actualiza_cliente(cCodGrupoCia_in CHAR,
 		  						cCodLocal_in CHAR,
								cCodCliLocal_in CHAR,
								cNomCli_in CHAR,
								cApePatCli_in CHAR,
								cApeMatCli_in CHAR,
								cNumDocCli_in	  CHAR,
								cUsuModCli_in	  CHAR)
 RETURN CHAR;

 --Descripcion: Modifica una Direccion
  --Fecha       Usuario		Comentario
  --25/04/2006  Paulo     Creación
 FUNCTION cli_actualiza_direccion (cCodGrupoCia_in CHAR,
 		  						  cCodLocal_in CHAR,
								  cCodDir_in CHAR,
								  cTipCalle_in	CHAR,
								  cNomCalle_in	CHAR,
								  cNumCalle_in	CHAR,
								  cNomUrb_in	CHAR,
								  cNomDistrito_in	CHAR,
								  cNumInt_in	CHAR,
								  cRefDirec_in	CHAR,
								  cUsuModDir_in	CHAR)
  RETURN CHAR;

  --Descripcion: Agrega todos los datos del cliente creados en el esquema delivery
  --Fecha       Usuario		Comentario
  --22/06/2006  Paulo     Creación
  PROCEDURE cli_graba_datos_cli(cCodGrupoCia_in CHAR,
  		   							         cCodLocal_in	   CHAR,
									             --cCodDir_in	   CHAR,
									             cCodNumeraCli_in	   CHAR,
                               cCodNumeraDir_in	   CHAR,
                               cCodNumeraTel_in    CHAR,
									             cNumTelefono_in	   CHAR,
									             cTipTelefono_in	   CHAR,
									             cUsuCreaTel_in	   CHAR,
                               cTipCalle_in		CHAR,
                               cNomCalle_in		CHAR,
									             cNumCalle_in		CHAR,
									             cNomUrb_in	CHAR,
									             cNomDistrito_in		CHAR,
									             cNumInt_in			CHAR,
									             cRefDirec_in		CHAR,
									             cUsuCreaDir_in		CHAR,
                               cNomCli_in CHAR,
							                 cApePatCli_in CHAR,
							                 cApeMatCli_in CHAR,
							                 cTipDocIdent_in CHAR,
							                 cNumDocIdent_in CHAR,
							                 cIndCliJur_in CHAR,
							                 cUsuCreaCli_in CHAR);
                						   --cCodCli_in IN CHAR);
  --Descripcion: Valida si el numero de Dni o el cliente ya existe
  --Fecha       Usuario		Comentario
  --22/06/2006  Paulo     Creación
  FUNCTION cli_valida_cli_repetido ( cCodGrupoCia_in  CHAR,
  		   							                cCodLocal_in	   CHAR,
                                      cNumDocIdent_in  CHAR,
                                      cNomCli_in       CHAR,
                                      cApePatCli_in    CHAR,
                                      cApeMatCli_in    CHAR)
   RETURN CHAR;

   --Descripcion: Valida si el numero de Telefono ya existe
  --Fecha       Usuario		Comentario
  --22/06/2006  Paulo     Creación
   FUNCTION cli_valida_num_tel(cCodGrupoCia_in  CHAR,
  		   							         cCodLocal_in	   CHAR,
                               cNumTelf_in  CHAR)
   RETURN CHAR;

   --Descripcion: Valida si la direccion existe
  --Fecha       Usuario		Comentario
  --22/06/2006  Paulo     Creación
   FUNCTION cli_valida_direccion(cCodGrupoCia_in CHAR,
                                 cCodLocal_in    CHAR,
                                 cNombCalle_in   CHAR,
                                 cNumCalle_in    CHAR,
                                 cNomDist_in     CHAR,
                                 cNumTelf_in     CHAR)
   RETURN CHAR;


  --Descripcion: LISTA LOS PEDIDOS DELIVERYS PENDIENTES
  --Fecha       Usuario		Comentario
  --12/10/2006  Paulo     Creación
  FUNCTION CLI_LISTA_CAB_PEDIDOS(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: LISTA EL DETALLE DE LOS PEDIDOS DELIVERYS PENDIENTES
  --Fecha       Usuario		Comentario
  --12/10/2006  Paulo     Creación
  FUNCTION CLI_LISTA_DETALLE_PEDIDOS(cCodGrupoCia_in     IN CHAR,
                                    cCodLocal_in         IN CHAR,
                                    cCodLocalAtencion_in IN CHAR,
                                    cNumPedido_in        IN CHAR)
    RETURN FarmaCursor;

  /* ********************************************************************************************** */

  --17/04/2008  DUBILLUZ     MODIFICACION
  --03/04/2009  JCORTEZ     MODIFICACION
  PROCEDURE CLI_GENERA_PEDIDO_DA(cCodGrupoCia_in 	    IN CHAR,
						   	                 cCodLocal_in    	    IN CHAR,
                                 cCodLocalAtencion_in IN CHAR,
							                   cNumPedVtaDel_in 	  IN CHAR,
                                 cNuevoNumPedVta_in   IN CHAR,
                                 cNumPedDiario_in 	  IN CHAR,
                                 cIPPedido_in         IN CHAR,
                                 cSecUsuVen_in        IN CHAR,
							                   cUsuCreaPedVta_in    IN CHAR,
                                 cNumCaja_in          IN CHAR);

  FUNCTION CLI_OBTIENE_ESTADO_PEDIDO(cCodGrupoCia_in IN CHAR,
  		   						   	             cCodLocal_in    IN CHAR,
									                   cNumPedVta_in 	 IN CHAR)
    RETURN CHAR;

  FUNCTION CLI_LISTA_FORMA_PAGO_PED(cCodGrupoCia_in IN CHAR,
  		   						   	            cCodLocal_in    IN CHAR,
									                  cNumPedVta_in 	IN CHAR)
    RETURN FarmaCursor;

  PROCEDURE CLI_ANULA_DELIVERY_AUTOMATICO(cCodGrupoCia_in  IN CHAR,
  		   						   	   	              cCodLocal_in     IN CHAR,
									   	                    cNumPedVta_in    IN CHAR,
										                      cUsuModPedido_in IN CHAR);

  --Descripcion: obtiene el codigo del local de origen del pedido
  --Fecha       Usuario		Comentario
  --27/11/2006  Paulo     Creación
  FUNCTION CLI_OBTIENE_LOCAL_ORIGEN(cCodGrupoCia_in  IN CHAR,
  		   						   	   	        cCodLocal_in     IN CHAR,
									   	              cNumPedVta_in    IN CHAR)
  RETURN CHAR;

  --Descripcion: lista el detalle de pedido en multiseleccion
  --Fecha       Usuario		Comentario
  --27/11/2006  Paulo     Creación
  FUNCTION CLI_LISTA_DETALLE_PEDIDOS_INST(cCodGrupoCia_in      IN CHAR,
                                          cCodLocal_in         IN CHAR,
                                          cCodLocalAtencion_in IN CHAR,
                                          cNumPedido_in        IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: verifica la existencia de un lote para un producto dado
  --Fecha       Usuario		Comentario
  --28/11/2006  LMESIA    Creación
  FUNCTION CLI_VERIFICA_LOTE_PROD(cCodGrupoCia_in IN CHAR,
									   	            cCodProd_in     IN CHAR,
                                  cNumLote_in     IN CHAR)
    RETURN CHAR;

  --Descripcion: agrega el detalle de una venta institucional tmp, producto con lote
  --Fecha       Usuario		Comentario
  --28/11/2006  LMESIA    Creación
  PROCEDURE CLI_AGREGA_VTA_INSTI_DET(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
  		   								             cNumPedido_in   IN CHAR,
										                 cCodProd_in     IN CHAR,
                                     cNumLote_in     IN CHAR,
                                     cCant_in        IN NUMBER,
                                     cUsuCrea_in     IN CHAR,
                                     cFechaVencimiento_in IN CHAR);

  --Descripcion: elimina el detalle de una venta institucional tmp por pedido y producto
  --Fecha       Usuario		Comentario
  --28/11/2006  LMESIA    Creación
  PROCEDURE CLI_ELIMINA_VTA_INSTI_DET(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
  		   								              cNumPedido_in   IN CHAR,
										                  cCodProd_in     IN CHAR);

  --Descripcion: Lista el detalle de una venta institucional tmp por pedido y producto
  --Fecha       Usuario		Comentario
  --28/11/2006  LMESIA    Creación
  FUNCTION CLI_LISTA_INST_DET_PROD_LOTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
  		   								                cNumPedido_in   IN CHAR,
										                    cCodProd_in     IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: elimina el detalle de una venta institucional tmp por pedido ,producto y lote
  --Fecha       Usuario		Comentario
  --28/11/2006  LMESIA    Creación
  PROCEDURE CLI_ELIMINA_PED_INST_PROD_LOTE(cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
  		   								                   cNumPedido_in   IN CHAR,
										                       cCodProd_in     IN CHAR,
                                           cNumLote_in     IN CHAR);

  --Descripcion: genera el pedido(cabecera y detalle) de venta institucional automatico
  --Fecha       Usuario		Comentario
  --29/11/2006  LMESIA    Creación
  --17/04/2008  DUBILLUZ     MODIFICACION
  PROCEDURE CLI_GENERA_PEDIDO_INST_A(cCodGrupoCia_in 	   IN CHAR,
						   	                     cCodLocal_in    	   IN CHAR,
                                     cCodLocalAtencion_in IN CHAR,
							                       cNumPedVtaDel_in 	   IN CHAR,
                                     cNuevoNumPedVta_in   IN CHAR,
                                     cNumPedDiario_in 	   IN CHAR,
                                     cIPPedido_in         IN CHAR,
                                     cSecUsuVen_in        IN CHAR,
							                       cUsuCreaPedVta_in    IN CHAR);

  --Descripcion: Lista los productos con cantidades con lote ya seleccionadas en su totalidad
  --Fecha       Usuario		Comentario
  --30/11/2006  LMESIA    Creación
  FUNCTION CLI_LISTA_PROD_LOTE_SEL(cCodGrupoCia_in      IN CHAR,
                                   cCodLocal_in         IN CHAR,
  		   								           cNumPedido_in        IN CHAR,
										               cCodLocalAtencion_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Reinicializa un pedido y lo pone como pendiente de generacion de delivery automatico
  --Fecha       Usuario		Comentario
  --14/12/2006  LMESIA    Creación
  PROCEDURE CLI_REINICIALIZA_PEDIDO_AUTO(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
  		   								                 cNumPedido_in   IN CHAR);

  --Descripcion: OBTIENE SI LOS MONTOS SON DIFERENTES
  --Fecha       Usuario		Comentario
  --09/01/2007  PAULO    Creación
  FUNCTION CLI_OBTIENE_IND_MONTOS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
  		   								          cNumPedido_in   IN CHAR)
  RETURN CHAR ;


  --Descripcion: ACTUALIZA LOS VALORES DEL PEDIDO DE LA CABECERA
  --Fecha       Usuario		Comentario
  --09/01/2007  PAULO    Creación
  PROCEDURE  CLI_ACTUALIZA_VALORES_PD(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
    		   		  						          cNumPedido_in   IN CHAR);

  --Descripcion: OBTIENE LA FECHA DE VENCIMIENTO DEL NUMERO DE LOTE
  --Fecha       Usuario		Comentario
  --23/01/2007  PAULO    Creación
  FUNCTION CLI_OBTIENE_FECHA_VENCIMIENTO(cCodGrupoCia_in IN CHAR,
									   	                    cCodProd_in     IN CHAR,
                                          cNumLote_in     IN CHAR)
  RETURN CHAR;

  --Descripcion: Actualiza el numero de pedido en la tabla TMP_VTA_FORMA_PAGO_PEDIDO_CON
  --Fecha       Usuario		Comentario
  --28/03/2007  LREQUE    	Creación

  PROCEDURE CON_ACTUALIZA_NUM_PED(cCodGrupoCia_in 	IN CHAR,
  	                          cCodLocal_in    	IN CHAR,
                                  cNumPedVta_in         IN CHAR,
                                  cNumPedVtaDel_in      IN CHAR);

  --Descripcion: Agrega los cupones por pedido
  --Fecha       Usuario		Comentario
  --17/04/2007  DUBILLUZ  	Creación
  PROCEDURE CLI_AGREGA_CUPON_PED(cCodGrupoCia_in 	IN CHAR,
     	                           cCodLocal_in    	IN CHAR,
                                 cNumPedVta_in    IN CHAR,
                                 cCodLocalAtencion_in IN CHAR,
                                 cNuevoNumPedVta_in  IN CHAR
                                 );

  --Descripcion: se procesa el formato de impresion
  --Fecha       Usuario		Comentario
  --13/06/2008  JCORTEZ  	Creación
  FUNCTION IMP_DATOS_DELIVERY(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Se obtiene datos de comanda
  --Fecha       Usuario		Comentario
  --13/06/2008  JCORTEZ  	Creación
  FUNCTION VTA_OBTENER_DATA1(cCodGrupoCia_in IN CHAR,
  		   				             cCodLocal_in	   IN CHAR,
							               cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se obtiene datos de comanda
  --Fecha       Usuario		Comentario
  --13/06/2008  JCORTEZ  	Creación
  FUNCTION VTA_OBTENER_DATA2(cCodGrupoCia_in IN CHAR,
        		   				        cCodLocal_in	  IN CHAR,
      							          cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se obtiene datos de comanda
  --Fecha       Usuario		Comentario
  --13/06/2008  JCORTEZ  	Creación
  FUNCTION VTA_OBTENER_DATA3(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in	   IN CHAR,
                              cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se obtiene datos de comanda
  --Fecha       Usuario		Comentario
  --13/06/2008  JCORTEZ  	Creación
  FUNCTION VTA_OBTENER_DATA4(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in	   IN CHAR,
                             cNumPedVta_in   IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se obtiene numero del pedido delivery
  --Fecha       Usuario		Comentario
  --16/07/2008  JCORTEZ  	Creación
  FUNCTION GET_NUM_PED_DELIVERY(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                                cNumPed_in        IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Se obtiene datos de comanda
  --Fecha       Usuario		Comentario
  --13/06/2008  JCORTEZ  	Creación
  FUNCTION OBTENER_FORMATO_PALABRA(palabra   IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Retorna los datos de pedido de delivery automatico
  --Fecha       Usuario		Comentario
  --26/11/2008  DUBILLUZ  	Creación
  FUNCTION CLI_F_GET_DATOS_PED_DELIVERY(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
  		   								                cNumPedido_in   IN CHAR)
  RETURN VARCHAR2;

  PROCEDURE CLI_P_ENVIA_ALERTA_DELIVERY(cCodGrupoCia_del_in IN CHAR,
                                        cCodLocal_del_in    IN CHAR,
  		   								                cNumPedido_del_in   IN CHAR,
                                        cCodLocal_in        IN CHAR);

  PROCEDURE CLI_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_del_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR,
                                        vEnviarOper_in   IN CHAR DEFAULT 'N');

  --Descripcion: Este metodo arma el cuerpo del mail de anulacion de pedidos de delivery
  --Autor:  DVELIZ
  --Fecha:  11.12.2008
  --Estado: Creado
  FUNCTION CLI_F_VAR_MENSAJE_ANULACION(cCodGrupoCia_del_in IN CHAR,
                                       cCodLocal_del_in    IN CHAR,
  		   								               cNumPedido_del_in   IN CHAR,
                                       cCodLocal_in        IN CHAR,
                                       cDescLocal_in       IN CHAR,
                                       cNumPedVta_in       IN CHAR,
                                       cFechaPedDelivery   IN CHAR,
                                       cMontoPedDelivery   IN CHAR,
                                       cTipoCompPed        IN CHAR,
                                       cNroCompPed         IN CHAR)
  RETURN VARCHAR2;



  /**************************************************************************/

    --Descripcion: Se obtiene formato de impresion comanda delivery local
  --Fecha       Usuario		Comentario
  --07/08/2009  JCORTEZ  	Creación
    FUNCTION IMP_DATOS_DELIVERY_L(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR)
    RETURN VARCHAR2;


    --Descripcion: Se actualiza datos del pedido delivery local
  --Fecha       Usuario		Comentario
  --07/08/2009  JCORTEZ  	Creación
    PROCEDURE CAJ_P_UP_DATOS_DELIVERY(cCodGrupoCia_in 	IN CHAR,
                                    cCodLocal_in    	IN CHAR,
                                    cNumPedVta_in 		IN CHAR,
                                    cEstPedVta_in		  IN CHAR,
                                    cUsuModPedVtaCab_in IN CHAR,
                                    cCodcli             IN CHAR,
                                    cNomCli             IN CHAR,
                                    cTelCli             IN CHAR,
                                    cDirCli             IN CHAR,
                                    cNroCli             IN CHAR);

      --Descripcion: Datos para la comanda local
  --Fecha       Usuario		Comentario
  --07/08/2009  JCORTEZ  	Creación
    FUNCTION VTA_OBTENER_DATA1_L(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in	   IN CHAR,
                              cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor;

      --Descripcion: Datos para la comanda local
  --Fecha       Usuario		Comentario
  --07/08/2009  JCORTEZ  	Creación
    FUNCTION VTA_OBTENER_DATA2_L(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in	  IN CHAR,
                              cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor;

     --Descripcion: Datos para la comanda local
  --Fecha       Usuario		Comentario
  --07/08/2009  JCORTEZ  	Creación
    FUNCTION VTA_OBTENER_DATA4_L(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in	   IN CHAR,
                           cNumPedVta_in   IN CHAR)
    RETURN FarmaCursor;

  FUNCTION CLI_F_VAR_PARTIDA_LLEGADA(
                                     cCodGrupoCia_del_in IN CHAR,
                                     cCodLocal_del_in    IN CHAR,
  		   								             cNumPedido_del_in   IN CHAR
                                     )
  RETURN VARCHAR2;

  --Descripcion: Valida si tiene los lotes ingresados el pedido
  --Fecha       Usuario		Comentario
  --16/12/2009  DUBILLUZ 	Creación
  FUNCTION CLI_F_VAR_EXIST_LOTE_PED(
                                     cCodGrupoCia_in 	   IN CHAR,
						   	                     cCodLocal_in    	   IN CHAR,
                                     cCodLocalAtencion_in IN CHAR,
							                       cNumPedVtaDel_in 	   IN CHAR
                                     )
  RETURN VARCHAR2;

END;

/
