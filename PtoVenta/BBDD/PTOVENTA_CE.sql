--------------------------------------------------------
--  DDL for Package PTOVENTA_CE
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CE" AS
TYPE FarmaCursor IS REF CURSOR;

C_INICIO_MSG_2 VARCHAR2(20000) := ' <html>' ||
                                    ' <head>' ||
                                    ' <style type="text/css">' ||
                                    ' .style3 {font-family: Arial, Helvetica, sans-serif}' ||
                                    ' .style8 {font-size: 24; }' ||
                                    ' .style9 {font-size: larger}' ||
                                    ' .style12 {' ||
                                    ' font-family: Arial, Helvetica, sans-serif;' ||
                                    ' font-size: larger;' ||
                                    ' font-weight: bold;' ||
                                    ' }' ||
                                    ' </style>' ||
                                    ' </head>' ||
                                    ' <body>' ||
                                    ' <table width="510"border="0">' ||
                                    ' <tr>' ||
                                    ' <td width="487" align="center" valign="top"><h1>REMITO</h1></td>' ||
                                    ' </tr>' ||
                                    ' </table>' ||
                                    ' <table width="504" border="1">' ||
                                    ' <tr>' ||
                                    ' <td height="43" colspan="3"><h2>Deposito En Soles (CTA. CTE. N&deg;)</h2></td>' ||
                                    ' <td colspan="3"><h2>Deposito En Dolares (CTA. CTE. N&deg;)</h2> </td>' ||
                                    ' </tr>' ||
                                    ' <tr>' ||
                                    ' <td width="78" height="61"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES </strong></td>' ||
                                    ' <td width="110"><strong>MONTO  S/.</strong></td>' ||
                                    ' <td width="78"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES</strong></td>' ||
                                    ' <td width="110"><strong>MONTO  US$</strong></td>' ||
                                    ' </tr>';
    C_FIN_MSG VARCHAR2(2000) := '</td>' ||
                                  '</tr>' ||
                                  '</table>' ||
                                  '</body>' ||
                                  '</html>';
    C_INI_NODEBENIR VARCHAR2(2000) := '<TR><TD COLSPAN=6 ALIGN=CENTER><h3>Días con sobres que no se deben añadir a este remito</h3></TD></TR>';

    C_DEPOSITO_SD VARCHAR2(2000):=' <tr>' ||
                                    ' <td height="43" colspan="3"><h2>Deposito En Soles (CTA. CTE. N&deg;)</h2></td>' ||
                                    ' <td colspan="3"><h2>Deposito En Dolares (CTA. CTE. N&deg;)</h2> </td>' ||
                                    ' </tr>';


  C_C_ANUL_PENDIENTE CE_CUADRATURA.COD_CUADRATURA%TYPE :='001';
  C_C_REG_ANUL_PENDIENTE CE_CUADRATURA.COD_CUADRATURA%TYPE :='002';
  C_C_DEL_PENDIENTES CE_CUADRATURA.COD_CUADRATURA%TYPE :='003';
  C_C_COB_DEL_PENDIENTE CE_CUADRATURA.COD_CUADRATURA%TYPE :='004';
  C_C_ANUL_DEL_PENDIENTE CE_CUADRATURA.COD_CUADRATURA%TYPE :='005';
  C_C_INGRESO_COMP_MANUAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='006';
  C_C_REG_COMP_MANUAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='007';
  C_C_IND_DINERO_FALSO CE_CUADRATURA.COD_CUADRATURA%TYPE :='008';
  C_C_IND_ROBO CE_CUADRATURA.COD_CUADRATURA%TYPE :='009';
  C_C_DEFICIT_ASUMIDO_CAJERO CE_CUADRATURA.COD_CUADRATURA%TYPE :='010';

  C_C_DEPOSITO_VENTA CE_CUADRATURA.COD_CUADRATURA%TYPE :='011';
  C_C_SERVICIOS_BASICOS CE_CUADRATURA.COD_CUADRATURA%TYPE :='012';
  C_C_REEMBOLSO_C_CH CE_CUADRATURA.COD_CUADRATURA%TYPE :='013';
  C_C_PAGO_PLANILLA CE_CUADRATURA.COD_CUADRATURA%TYPE :='014';
  C_C_COTIZA_COMP CE_CUADRATURA.COD_CUADRATURA%TYPE :='015';
  C_C_ENTREGAS_RENDIR CE_CUADRATURA.COD_CUADRATURA%TYPE :='016';
  C_C_ROBO_ASALTO CE_CUADRATURA.COD_CUADRATURA%TYPE :='017';
  C_C_DINERO_FALSO CE_CUADRATURA.COD_CUADRATURA%TYPE :='018';
  C_C_OTROS_DESEMBOLSOS CE_CUADRATURA.COD_CUADRATURA%TYPE :='019';
  C_C_FONDO_SENCILLO CE_CUADRATURA.COD_CUADRATURA%TYPE :='020';
  C_C_DSCT_PERSONAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='021';
  C_C_ADELANTO CE_CUADRATURA.COD_CUADRATURA%TYPE :='024';
  C_C_DELIVERY_PERDIDO CE_CUADRATURA.COD_CUADRATURA%TYPE :='023';
  C_C_GRATIFICACION CE_CUADRATURA.COD_CUADRATURA%TYPE :='025';
  C_C_TIPO_GUIA_COMPETENCIA LGT_NOTA_ES_CAB.TIP_ORIGEN_NOTA_ES%TYPE :='04';
  C_C_TIPO_INGRESO LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE :='01';
  C_C_IND_TIP_COMP_PAGO_BOL VTA_COMP_PAGO.tip_comp_pago%TYPE := '01';
  C_C_IND_TIP_COMP_PAGO_FACT VTA_COMP_PAGO.tip_comp_pago%TYPE := '02';
  C_C_IND_TIP_COMP_PAGO_TICKET VTA_COMP_PAGO.tip_comp_pago%TYPE := '05';
  C_C_IND_TIP_COMP_PAGO_NC VTA_COMP_PAGO.tip_comp_pago%TYPE := '04';
  C_C_IND_TIP_COMP_PAGO_GUIA VTA_COMP_PAGO.tip_comp_pago%TYPE := '03';


  C_C_IND_COTCOMP_TURNO CE_CUADRATURA.COD_CUADRATURA%TYPE :='032'; --ASOSA, 12.08.2010

  ESTADO_ACTIVO		     CHAR(1):='A';
	ESTADO_INACTIVO		   CHAR(1):='I';
	INDICADOR_SI		     CHAR(1):='S';
	INDICADOR_NO		     CHAR(1):='N';
	TIP_MOV_APERTURA	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='A';
	TIP_MOV_CIERRE  	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='C';
	TIP_MOV_ARQUEO  	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='R';

  TIP_MONEDA_SOLES CHAR(2) := '01';
  TIP_MONEDA_DOLARES CHAR(2) := '02';
  TIP_DINERO_BILLETE CHAR(2) := '01';
  TIP_DINERO_MONEDA CHAR(2) := '02';

  EST_PED_COBRADO  	      VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE := 'C';
  FORMA_PAGO_EFEC_SOLES	  VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE := '00001';
	FORMA_PAGO_EFEC_DOLARES VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE := '00002';

  COD_TIP_COMP_BOLETA  VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='01';
	COD_TIP_COMP_FACTURA VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='02';
 	COD_TIP_COMP_TICKET    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='05';

  COD_CUADRATURA_DEL_PERDIDO     CE_CUADRATURA.COD_CUADRATURA%TYPE:='023';

  --Descripcion: Ingresa las formas de pago entrega
  --Fecha       Usuario		Comentario
  --02/08/2006  Paulo     Creación
  FUNCTION CE_GRABA_FORMA_PAGO_ENTREGA(cCodGrupoCia_in	    IN CHAR,
  		   						                    cCodLocal_in	      IN CHAR,
                                        cSecMovCaja_in      IN CHAR,
                                        cCodFormaPago_in    IN CHAR,
                                        cCantVoucher_in     IN NUMBER,
                                        cTipMoneda_in       IN CHAR,
                                        cMonEntrega_in      IN NUMBER,
                                        cMonEntregaTotal_in IN NUMBER,
                                        cUsuCreaEntrega_in  IN CHAR,
                                        cNumLote_in         IN CHAR)
                                         RETURN VARCHAR2;

/*  Descripcion : Elimina una forma de pago entrega que ya fue grabada
    Fecha       Usuario   Comentario
    03/08/2006  Paulo     Creacion
    03/02/2009  jcallo    modificacion
*/
  PROCEDURE CE_ELIMINA_FORMA_PAGO(cCodGrupoCia_in	 IN CHAR,
  		   						              cCodLocal_in	   IN CHAR,
                                  cSecMovCaja_in   IN CHAR,
                                  cCodFormaPago_in IN CHAR,
                                  cTipMoneda_in    IN CHAR,
                                  cNumLote_in      IN CHAR,
                                  cSecFPEntrega_in IN CHAR,
                                  cCodUsuLocal_in  IN CHAR);
  /**
  modificacion : jcallo 03/02/2009
  **/
  PROCEDURE CE_ELIMINA_FORMA_PAGO_SL(cCodGrupoCia_in	 IN CHAR,
  		   						                 cCodLocal_in	   IN CHAR,
                                     cSecMovCaja_in   IN CHAR,
                                     cCodFormaPago_in IN CHAR,
                                     cTipMoneda_in    IN CHAR,
                                     cSecFPEntrega_in IN CHAR,
                                     cCodUsuLocal_in  IN CHAR);

/*  Descripcion : Lista las formas de pago entrega ya ingresadas en un primer momento
    Fecha       Usuario   Comentario
    04/08/2006  Paulo     Creacion
*/
  FUNCTION CE_LISTA_DETALLE_FORMAS_PAGO(cCodGrupoCia_in	IN CHAR,
  		   						                    cCodLocal_in	  IN CHAR,
                                        cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor ;

/*  Descripcion : Lista la cuadratura de anulados pendientes
    Fecha       Usuario   Comentario
    07/08/2006  Paulo     Creacion
*/
  FUNCTION CE_LISTA_ANUL_PENDIENTES(cCodGrupoCia_in	  IN CHAR,
  		   						                cCodLocal_in	    IN CHAR,
                                    cSecMovCaja_in    IN CHAR,
                                    cCodCuadratura_in IN CHAR)
  RETURN FarmaCursor;

/*  Descripcion : Agrega a la tabla ce_cuadratura_caja del listado
    Fecha       Usuario   Comentario
    07/08/2006  Paulo     Creacion
*/
  PROCEDURE CE_INSERTA_LISTA_CUADRA_ING(cCodGrupoCia_in     IN CHAR,
  		   						                    cCodLocal_in	      IN CHAR,
                                        cSecMovCaja_in      IN CHAR,
                                        cCodCuadratura_in   IN CHAR,
                                        cNumSerieLocal_in   IN CHAR,
                                        cTipComp_in         IN CHAR,
                                        cNumCompPago_in     IN CHAR,
                                        cMonMonedaOrigen_in IN NUMBER,
                                        cMonTotal_in        IN NUMBER,
                                        cMonVuelto_in       IN NUMBER,
                                        cUsuCrea_in         IN CHAR);

/*  Descripcion : Agrega cuadratura en cursos al cerrar caja
    Fecha       Usuario   Comentario
    09/08/2006  Paulo     Creacion
*/
  PROCEDURE CE_GRABA_CUADRATURA_CIERRE(cCodGrupoCia_in	IN CHAR,
  		   						                   cCodLocal_in	    IN CHAR,
                                       cSecMovCaja_in   IN CHAR,
                                       cUsuCrea_in      IN CHAR);

/*  Descripcion : Lista cuadraturas para eliminacion
    Fecha       Usuario   Comentario
    10/08/2006  Paulo     Creacion
*/
  FUNCTION CE_LISTA_ELIMINA_CUADRATURA(cCodGrupoCia_in	 IN CHAR,
  		   						                   cCodLocal_in	     IN CHAR,
                                       cSecMovCaja_in    IN CHAR,
                                       cCodCuadratura_in IN CHAR)
  RETURN FarmaCursor;

/*  Descripcion : Validacion para la eliminacion de cuadratura
    Fecha       Usuario   Comentario
    10/08/2006  Paulo     Creacion
*/
  FUNCTION CE_VALIDA_ELIMINACION(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cSecMovCaja_in    IN CHAR,
                                 cSecCuadratura_in IN CHAR,
                                 cCodCuadratura_in IN CHAR)
  RETURN NUMBER;

/*  Descripcion : Elimina una cuadratura
    Fecha       Usuario   Comentario
    10/08/2006  Paulo     Creacion
*/
  PROCEDURE CE_ELIMINA_CUADRATURA(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cCodCuadratura_in IN CHAR,
                                   cSecMovcaja_in    IN CHAR,
                                   cSecCuadratura_in IN CHAR);

/*  Descripcion : OBTIENE LOS COMPROBANTES PARA ESE SECUENCIAL DE CAJA
    Fecha       Usuario   Comentario
    16/08/2006  Paulo     Creacion
*/
  FUNCTION CE_OBTIENE_RANGO_COMP(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cSecMovcaja_in  IN CHAR)
  RETURN FarmaCursor;

/*  Descripcion : Lista las Cuadraturas para el Cierre de Turno
    Fecha       Usuario   Comentario
    23/08/2006  Paulo     Creacion
*/
  FUNCTION CE_LISTA_CUADRATURAS(cCodGrupoCia_in	IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cFecCierre_in   IN CHAR)
  RETURN FarmaCursor;

/*  Descripcion : Lista la cuadratura de cotizacion de competencia para cierre de dia
    Fecha       Usuario   Comentario
    23/08/2006  Paulo     Creacion
*/
  FUNCTION CE_LISTA_COT_COMPETENCIA(cCodGrupoCia_in	IN CHAR,
                                    cCodLocal_in	  IN CHAR,
                                    cFechaCierreDia IN CHAR)
  RETURN FarmaCursor;

/*  Descripcion : lista la informacion de la cuadratura para poder eliminarlas
    Fecha       Usuario   Comentario
    24/08/2006  Paulo     Creacion
*/
  FUNCTION CE_LISTA_ANUL_CUADRATURA_CD(cCodGrupoCia_in   IN CHAR,
                                       cCodlLocal_in     IN CHAR,
                                       cCodCuadratura_in IN CHAR,
                                       cFechaCD_in       IN CHAR)
  RETURN FarmaCursor;

/*  Descripcion : OBTIENE EL NUMERO DE SECUIENCIA POR UN DIA DE CIERRE DE DIA
    Fecha       Usuario   Comentario
    24/08/2006  Paulo     Creacion
*/
  FUNCTION GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cFechaCD_in     IN CHAR)
  RETURN NUMBER;

/*  Descripcion : Inserta la cuadratura de cotizacion de competencia
    Fecha       Usuario   Comentario
    24/08/2006  Paulo     Creacion
*/
  PROCEDURE CE_AGREGA_COT_COMPETENCIA(cCodGrupoCia_in    IN CHAR,
                                      cCodLocal_in       IN CHAR,
                                      cFechaCierreDV_in  IN CHAR,
                                      cCodCuadratura_in  IN CHAR,
                                      cMontoTotal_in     IN NUMBER,
                                      cNumSec_in         IN CHAR,
                                      cUsuCrea_in        IN CHAR,
                                      cGlosa_in          IN CHAR);
/*  Descripcion : Elimina una cuadratura de Cierre de Dia
    Fecha       Usuario   Comentario
    28/08/2006  Paulo     Creacion
*/
  PROCEDURE CE_ELIMINA_CUADRATURA_CD(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cFechaCierreDV_in IN CHAR,
                                     cCodCuadratura_in IN CHAR,
                                     cSecCuadratura_in IN CHAR);

/*  Descripcion : Obtiene los comprobantes de todo el dia de Venta
    Fecha       Usuario   Comentario
    28/08/2006  Paulo     Creacion
*/
  FUNCTION CE_OBTIENE_RANGO_COMP_CD(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cFechaCierreDV  IN CHAR)
  RETURN FarmaCursor;

/*  Descripcion : Actualiza el campo de fecha de caja electronica cuando es una cuadratura
                  de tipo cotizacion de competencia
    Fecha       Usuario   Comentario
    28/08/2006  Paulo     Creacion
*/
  PROCEDURE CE_ACTUALIZA_GUIA_COT_COMP(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNotaes_in   IN CHAR,
                                       cFechaCD_in     IN CHAR,
                                       cUsuModNota_in  IN CHAR);

/*  Descripcion : Carga combo con nombre de Proveedor
    Fecha       Usuario   Comentario
    20/09/2006  Paulo     Creacion
*/
  FUNCTION CE_OBTIENE_PROVEEDOR(cTipoServicio IN CHAR)
  RETURN FarmaCursor;

/*  Descripcion : lista el detalle de las cuadraturas para cierre de dia
    Fecha       Usuario   Comentario
    04/10/2006  Paulo     Creacion
*/
  FUNCTION CE_LISTA_DETTALLE_CUADRATURA(cCodGrupoCia_in	  IN CHAR,
  		   						                    cCodLocal_in	    IN CHAR,
                                        cFecCierreDia_in  IN CHAR,
                                        cCodCuadratura_in IN CHAR)
  RETURN FarmaCursor ;

/*  Descripcion : lista el detalle de las formas de pago para el cierre de dia
    Fecha       Usuario   Comentario
    04/10/2006  Paulo     Creacion
*/
  FUNCTION CE_LISTA_DETTALLE_FORMAS_PAGO(cCodGrupoCia_in	IN CHAR,
  		   						                     cCodLocal_in	    IN CHAR,
                                         cFecCierreDia_in IN CHAR,
                                         cCodFormaPago_in IN CHAR)
  RETURN FarmaCursor ;

  /*  Descripcion : graba las formas de pago que son automaticas al momento de
                    cerrar la caja
      Fecha       Usuario   Comentario
      30/10/2006  LMESIA    Creacion
  */
  PROCEDURE CE_GRABA_FORMA_PAGO_CIERRE(cCodGrupoCia_in IN CHAR,
  		   						                   cCodLocal_in	   IN CHAR,
                                       cSecMovCaja_in  IN CHAR,
                                       cUsuCrea_in     IN CHAR);

  /*  Descripcion : ASIGNA EL VB CONTABLE
      Fecha       Usuario   Comentario
      05/12/2006  PAULO    Creacion
  */
  PROCEDURE CE_ASIGNA_VB_CONTABLE(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
                                  cCierreDia_in       IN CHAR,
                                  cUsuModCierreDia_in IN CHAR);

  /*  Descripcion : OBTIENE EL VB DE QUIMICO
      Fecha       Usuario   Comentario
      05/12/2006  PAULO    Creacion
  */
  FUNCTION CE_OBTIENE_VB_QF(cCodGrupoCia_in     IN CHAR,
                            cCodLocal_in        IN CHAR,
                            cCierreDia_in       IN CHAR)
  RETURN CHAR;

  /*  Descripcion : ELIMINA EL VB DE CONTABILIDAD
      Fecha       Usuario   Comentario
      06/12/2006  PAULO    Creacion
  */
  PROCEDURE CE_ELIMINA_VB_CONTABLE(cCodGrupoCia_in     IN CHAR,
                                   cCodLocal_in        IN CHAR,
                                   cCierreDia_in       IN CHAR);


  /*  Descripcion : ACTUALIZA LOS VB DE MATRIZ EN LOS LOCALES
      Fecha       Usuario   Comentario
      06/12/2006  PAULO    Creacion
  */
  PROCEDURE CE_ACTUALIZA_VB_LOCALES(cCodGrupoCia_in     IN CHAR);

  /*  Descripcion : ACTUALIZA EL INDICADOR DE ENVIO
      Fecha       Usuario   Comentario
      06/12/2006  PAULO    Creacion
  */
  PROCEDURE CE_ACTUALIZA_IND_ENVIO(cCodGrupoCia_in IN CHAR,
                                   cCierreDia_in   IN CHAR,
                                   cCodLocal_in IN CHAR);


  /*  Descripcion : Obtiene la cantidad de comprobantes a reclamar a Navsat en un dia de venta
      Fecha       Usuario   Comentario
      26/01/2007  LMESIA    Creacion
  */
  FUNCTION CE_OBT_CANT_RECLAMOS_NAVSAT(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cCierreDia_in   IN CHAR)
    RETURN NUMBER;

  /*  Descripcion : Obtiene la lista de comprobantes a reclamar a un proveedor
      Fecha       Usuario   Comentario
      26/01/2007  LMESIA    Creacion
  */
  FUNCTION CE_LISTA_COMP_RECLAMOS_NAVSAT(cCodGrupoCia_in IN CHAR,
  		   						                     cCodLocal_in	   IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor;

  /*  Descripcion : Obtiene el detalle de un comprobante a reclamar a un provvedor
      Fecha       Usuario   Comentario
      26/01/2007  LMESIA    Creacion
  */
  FUNCTION CE_LISTA_DET_RECLAMOS_NAVSAT(cCodGrupoCia_in IN CHAR,
  		   						                    cCod_Local_in   IN CHAR,
								                        cSecCompPago_in IN CHAR)
    RETURN FarmaCursor;

/******************************************************************************/

  /*  Descripcion : Obtiene las formas de pago
      Fecha       Usuario   Comentario
      20/03/2007  LREQUE    Creacion
  */
  FUNCTION CE_OBTIENE_FORMAS_PAGO(cCodGrupoCia_in  IN CHAR,
  		   						               cCodLocal_in    	IN CHAR)
  RETURN FarmaCursor;



/*************************************************************************/
   /* Descripcion : Obtiene el REsumen de productos virtuales por dia
      Fecha       Usuario   Comentario
      11/07/2007  JCORTEZ    Creacion
  */
  FUNCTION CE_OBTIENE_RESUMEN_VIRTUALES(cCodGrupoCia_in  IN CHAR,
  		   						                    cCodLocal_in   IN CHAR,
                                        cFecha_in IN CHAR)
  RETURN FarmaCursor;


/*************************************************************************/
   /* Descripcion : INGRESA LAS COTIZACIONES DE COMPETENCIA EN FORMA AUTOMATICA.
      Fecha       Usuario   Comentario
      03/08/2007  PAMEGHINO    Creacion
  */
  PROCEDURE CE_AGREGA_COTIZ_COMP_AUTO(cCodGrupoCia_in	IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cFechaCierreDia_in IN CHAR,
                                      cUsuCrea_in IN CHAR) ;

   /* Descripcion : agrega a la tabla temporal los dias que van actualizar el VB contable
      Fecha       Usuario   Comentario
      05/09/2007  PAMEGHINO    Creacion
  */
  PROCEDURE CE_INSERTA_TMP_VB_LOCALES(cCodGrupoCia_in IN CHAR);

   /* Descripcion : ACTUALIZA LA FECHA DE ENVIO DE LA TABLA TEMPORAL PARA LOS VB CONTABLES
      Fecha       Usuario   Comentario
      05/09/2007  PAMEGHINO    Creacion
  */
  PROCEDURE CE_ACTUALIZA_FEC_PROCESO_ENV(cCodGrupoCia_in IN CHAR,
                                         cCierreDia_in   IN CHAR,
                                         cCodLocal_in IN CHAR);

   /* Descripcion : ACTUALIZA EL INDICADOR DE VB CONTABLE EN LA TABLA DE CIERRE DE DIA EN BASE AL TEMPORAL EN EL LOCAL
      Fecha       Usuario   Comentario
      05/09/2007  PAMEGHINO    Creacion
  */
  PROCEDURE CE_UPLOAD_VB_LOCALES(cCodGrupoCia_in IN CHAR)   ;


   /* Descripcion : ENVIA LA INFORMACION DEL TEMPORAL DE MATRIZ A LOS LOCALES
      Fecha       Usuario   Comentario
      05/09/2007  PAMEGHINO    Creacion
  */

  PROCEDURE CE_ENVIA_VB_LOCALES (cCodGrupoCia_in IN CHAR);

   /* Descripcion : reune los procedimientos que bajan la informacion al temporal y envia al local
      Fecha       Usuario   Comentario
      06/09/2007  PAMEGHINO    Creacion
  */
  PROCEDURE CE_EJECUTA_VB_CONTABLE(cCodGrupoCia_in IN CHAR);

  FUNCTION CE_F_NUM_MONTO_SOBRES(cCodGrupoCia_in  IN CHAR,
  		   						             cCodLocal_in   IN CHAR,
                                 cFecha_in IN CHAR)
  RETURN NUMBER;

  --Descripcion: Se verifica si existe guias pendientes
  --Fecha       Usuario		Comentario
  --27/10/2009  JCORTEZ   Creación
  FUNCTION CE_EXIST_GUIAS_PEND(cCodGrupoCia_in     IN CHAR,
                               cCodLocal_in        IN CHAR)
  RETURN CHAR;


  --Descripcion: Se verifica si existe guias pendientes ORIGEN OTRO LOCAL
  --Fecha       Usuario		 Comentario
  --14/12/2009  JMIRANDA   Creación
  FUNCTION CE_EXIST_GUIAS_PEND_LOCAL(cCodGrupoCia_in     IN CHAR,
                               cCodLocal_in        IN CHAR)
  RETURN CHAR;

  /*********************************CAMBIO DE FORMA DE PAGO*************************************************************/

  --Descripcion: Se lista el registro de ventas para el posible cambio de forma de pago
  --Fecha       Usuario		 Comentario
  --26/02/2010  JCORTEZ   Creación
  FUNCTION CE_REGISTRO_VENTAS (cCodGrupoCia_in	IN CHAR,
                               cCodLocal_in	  	IN CHAR,
                               cFechaInicio     IN CHAR,
                               cFechaFin        IN CHAR,
                               cMovCaja         IN CHAR,
                               cNumPedVta_in    IN CHAR DEFAULT '%',
                               nMontoVta_in     IN NUMBER DEFAULT 0,
                               cTipoPago        IN CHAR DEFAULT '%')
  RETURN FarmaCursor;

  --Descripcion: Se lista formas de pago por pedido
  --Fecha       Usuario		 Comentario
  --26/02/2010  JCORTEZ   Creación
  FUNCTION CE_DETALLE_FORMAS_PAGO(cCodGrupoCia_in	IN CHAR,
  		   					                cCodLocal_in	  IN CHAR,
   							                  cNumPedVta      IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se crea backup de la antigua forma de pago pedido
  --Fecha       Usuario		 Comentario
  --26/02/2010  JCORTEZ   Creación
  PROCEDURE CE_FORMA_PAGO_PEDIDO_BK(cCodGrupoCia_in 	 	     IN CHAR,
                                  cCodLocal_in    	 	     IN CHAR,
                                  cCodFormaPago_in   	     IN CHAR,
                                  cNumPedVta_in   	 	     IN CHAR,
                                  cUsuCreaFormaPagoPed_in  IN CHAR);

  --Descripcion: Se crea la nueva forma de pago efectivo del pedido
  --Fecha       Usuario		 Comentario
  --26/02/2010  JCORTEZ   Creación
  PROCEDURE CE_NEW_FORMA_PAGO_PEDIDO(cCodGrupoCia_in 	 	     IN CHAR,
                                    cCodLocal_in    	 	     IN CHAR,
                                    cCodFormaPago_in   	     IN CHAR,
                                    cNumPedVta_in   	 	     IN CHAR,
                                    nImPago_in		 		       IN NUMBER,
                                    cTipMoneda_in			       IN CHAR,
                                    nValTipCambio_in 	 	     IN NUMBER,
                                    nValVuelto_in  	 	       IN NUMBER,
                                    nImTotalPago_in 		     IN NUMBER,
                                    cNumTarj_in  		 	       IN CHAR,
                                    cFecVencTarj_in  		     IN CHAR,
                                    cNomTarj_in  	 		       IN CHAR,
                                    cDni_in                  IN CHAR,
                                    cCodVou_in               IN CHAR,
                                    cCodLote_in              IN CHAR,
                                    cCanCupon_in  	 		     IN NUMBER,
                                    cUsuCreaFormaPagoPed_in  IN CHAR);

  --Descripcion: Se obtiene datos del tipo de tarjeta ingresada (funcion temporal)
  --Fecha       Usuario		 Comentario
  --26/02/2010  JCORTEZ   Creación
  --12/Sep/2013 LLEIVA    Modificacion
  FUNCTION CE_F_OBTENER_TARJETA(cCodCia_in     IN CHAR,
                                cCodTarj_in    IN VARCHAR,
                                cTipOrigen_in  IN VARCHAR)
  RETURN FarmaCursor;

  --Descripcion: Se compara los montos de efectivo y tarje con los comprobantes
  --Fecha       Usuario		 Comentario
  --26/02/2010  JCORTEZ   Creación
  PROCEDURE CE_VAL_MONTO_CIERRE_TURNO (cCodGrupoCia_in 	 	     IN CHAR,
                                        cCodLocal_in    	 	     IN CHAR,
                                        cMovCaja_in              IN CHAR,
                                        cFechaCierre_in          IN CHAR,
                                        cMontoCuadratura_in      IN NUMBER);

  FUNCTION CE_VALIDA_CAMBIO_FORM_PAGO
  RETURN CHAR;


   /*********************************CAMBIO DE INGRESO DE SOBRES*************************************************************/

  --Descripcion: Se obtiene estado del sobre temporal
  --Fecha       Usuario		 Comentario
  --29/03/2010  JCORTEZ   Creación
   FUNCTION CE_VALIDA_APROBACION(cCodGrupoCia_in	   IN CHAR,
 						                     cCodLocal_in	       IN CHAR,
                                 cCodForma_in        IN CHAR,
                                 cCodSobre_in        IN CHAR,
                                 cFechaVta_in        IN CHAR)
  RETURN VARCHAR2;



--Descripcion: Se obtiene el indicador que determina si el local tiene o no la opcion de prosegur activa
--Fecha       Usuario		 Comentario
--06/04/2010  ASOSA      Creación
FUNCTION CE_F_GET_IND_PROSEGUR(cCodCia_in IN CHAR,
                               cCodLoc_in IN CHAR)
RETURN CHAR;

--Descripcion: Lista los sobres de este local siempre y cuando se hallan aprobado y se halla hecho el cierre de dia por el QF
--Fecha       Usuario		 Comentario
--07/04/2010  ASOSA      Creación
FUNCTION CE_F_LISTA_SOBRE_AS(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR)
RETURN FarmaCursor;

--Descripcion: Agrega remitos actualizando con su codigo los sobres que contiene
--Fecha       Usuario		 Comentario
--07/04/2010  ASOSA      Creación
PROCEDURE CE_P_AGREGA_REMITO(cCodGrupoCia_in IN CHAR,
                                cCodLocal       IN CHAR,
                                cIdUsu_in       IN CHAR,
                                cNumRemito      IN CHAR,
                                cFecha          IN CHAR);

--Descripcion: Retorna el html que se imprimira en el voucher del remito
--Fecha       Usuario		 Comentario
--07/04/2010  ASOSA      Creación
--09/04/2010  ASOSA      Modificacion
FUNCTION CE_F_HTML_VOUCHER_REMITO(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodRemito      IN CHAR,
                                        cIpServ_in      IN CHAR)
RETURN VARCHAR2;

--Descripcion: Retorna cursor de sobres en dolares
--Fecha       Usuario		 Comentario
--07/04/2010  ASOSA      Creación
FUNCTION SEG_F_CUR_GET_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR)
RETURN FarmaCursor;

--Descripcion: Retorna cursor de sobres en soles
--Fecha       Usuario		 Comentario
--07/04/2010  ASOSA      Creación
FUNCTION SEG_F_CUR_GET_DATA3(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR)
RETURN FarmaCursor;

--Descripcion: Lista consolidado de sobres que no deben ir en el remito
--Fecha       Usuario		 Comentario
--09/04/2010  ASOSA      Creación
FUNCTION CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR)
RETURN FarmaCursor;

--Descripcion: Lista consolidado de sobres que no deben ir en el remito
--Fecha       Usuario		 Comentario
--09/04/2010  ASOSA      Creación
FUNCTION CE_F_LISTA_TOTALES_SD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                vUsu_in IN VARCHAR2)
RETURN FarmaCursor;

--Descripcion: Guardar historico de remito generado
--Fecha       Usuario		 Comentario
--21/04/2010  ASOSA      Creación
PROCEDURE CE_P_SAVE_HIST_REMI(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cCodRemito      IN CHAR,
                             vUsu_in IN VARCHAR2);

/*COPIA DEL PAQUETE PTOVENTA_CE DEBIDO A QUE HICE UN PQUEÑO CAMBIO Y MEJOR SE DUPLICO EL METODO
  Descripcion : lista la informacion de la cuadratura para poder eliminarlas
    Fecha       Usuario   Comentario
    23/04/2010  ASOSA     Implementacion
*/
FUNCTION CE_LISTA_ANUL_CUADRATURA_CD_AS(cCodGrupoCia_in   IN CHAR,
                                       cCodlLocal_in     IN CHAR,
                                       cCodCuadratura_in IN CHAR,
                                       cFechaCD_in       IN CHAR)
RETURN FarmaCursor;

--COPIA DEL PAQUETE PTOVENTA_CE_LMR DEBIDO A QUE HICE UN PQUEÑO CAMBIO Y MEJOR SE DUPLICO EL METODO
  --Descripcion: Lista efectivo rendido de cierre de dia
  --Fecha       Usuario	  Comentario
  --23/04/2010  ASOSA    Implementacion
FUNCTION CE_CONSO_EFEC_RENDIDO_CIERRE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in	  IN CHAR,
                                        cCierreDia_in   IN CHAR)
RETURN FarmaCursor;

--Descripcion: valida el monto en soles y dolares de lo recaudado contra lo rendido
--Fecha       Usuario		 Comentario
--23/04/2010  ASOSA      Creación
FUNCTION CE_F_VALIDAR_MONTO_SD(cCodCia_in IN CHAR,
                               cCodLocal_in IN CHAR,
                               cCierreDia_in IN CHAR)
RETURN CHAR;

--Descripcion: lISTA LAS CUADRATURAS CON SU MONTO DECLARADO CORRESPONDIENTE
--Fecha       Usuario		 Comentario
--29/04/2010  ASOSA      Creación
FUNCTION CE_LISTA_CUADRATURAS_AS(cCodGrupoCia_in	IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cFecCierre_in   IN CHAR)
RETURN FarmaCursor;

--Descripcion: OBTENER LA SUMA DE TODOS LOS SOBRES CUANDO ES PROSEGUR EL LOCAL
--Fecha       Usuario		 Comentario
--29/04/2010  ASOSA      Creación
FUNCTION CE_F_NUM_MONTO_SOBRES_AS(cCodGrupoCia_in  IN CHAR,
  		   						             cCodLocal_in   IN CHAR,
                                 cFecha_in IN CHAR)
RETURN NUMBER;


--Descripcion: CAMBIAR LA FORMA DE PAGO EN EL CIERRE DE DIA
--Fecha       Usuario		 Comentario
--03/06/2010  JQUISPE      Creación
FUNCTION CE_CAMBIO_FORMA_PAGO(cCodGrupoCia_in  IN CHAR,
  		   						               cCodLocal_in    	IN CHAR,
								                   cNumPedDiario_in IN CHAR,
								                   cFecPedVta_in	  IN CHAR)
RETURN FarmaCursor;

--Descripcion: LISTA DE VENTAS DE CIERRE DE CAJA X CAJERO
--Fecha       Usuario		 Comentario
--04/06/2010  JQUISPE      Creación
FUNCTION CE_LIST_DET_VENTAS (cCodGrupoCia_in	IN CHAR,
                               cCodLocal_in	  	IN CHAR,
                               cMovCaja         IN CHAR,
                               cNumPedVta_in    IN CHAR DEFAULT '%',
                               nMontoVta_in     IN NUMBER DEFAULT 0,
                               cTipoPago        IN CHAR DEFAULT '%')
 RETURN FarmaCursor;


--Descripcion: LISTA DE VENTAS DE CIERRE DE CAJA X CAJERO
--Fecha       Usuario		 Comentario
--04/06/2010  JQUISPE      Creación
FUNCTION CE_P_VERIFICAR_LONGITUD (  cCodGrupoCia_in  IN CHAR,
  		   						                 cCodLocal_in    	IN CHAR,
								                     cNumTarjeta IN CHAR
								                     )
  RETURN CHAR;

--  Descripcion : Obtiene las formas de pago, COPIA DEL ORIGINAL
--  Fecha       Usuario   Comentario
--  17/06/2010  ASOSA     Creacion
  FUNCTION CE_OBTIENE_FORMAS_PAGO_AS(cCodGrupoCia_in  IN CHAR,
  		   						               cCodLocal_in    	IN CHAR)
  RETURN FarmaCursor;

--Descripcion: GRABA LOS SOBRES PARCIALES Y LOS APRUEBA(GRABA EN FORMA PAGO ENTREGA Y EN CE_SOBRE
--Fecha       Usuario		 Comentario
--03/06/2010  ASOSA      Creación
PROCEDURE CE_P_INS_SOBRES_AUTOMATICO(cCodCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecMovCaja_in IN CHAR,
                                     cIdUsu_in IN CHAR);
--Descripcion: lISTO TODOS LOS SOBRES PARCIALES QUE NO HAN SIDO GRABADOS
--Fecha       Usuario		 Comentario
--04/06/2010  ASOSA      Creación
FUNCTION CE_F_LISTA_SOBRES_PARCIALES(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        secUsu_in       IN CHAR)
  RETURN FarmaCursor;

--Descripcion: Lista formas sobres para la lista de forma de pago entrega
--Fecha       Usuario		 Comentario
--04/06/2010  ASOSA      Creación
FUNCTION CAJ_F_CUR_SOBRES_ENTREGA_AS(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecMovCaja_in  IN CHAR)
  RETURN FarmaCursor;

--Descripcion: Lista documentos necesarios para cotizacion competencia en cierre turno
--Fecha       Usuario		 Comentario
--12/08/2010  ASOSA      Creación
FUNCTION CE_LISTA_COT_COMPET_TURNO(cCodGrupoCia_in	IN CHAR,
                                cCodLocal_in	  IN CHAR,
                                codCuadra_in IN VARCHAR)
RETURN FarmaCursor;

--Descripcion: Obtiene el indicador de Adm local peuda dar VB Cajero y VB QF.
--Fecha       Usuario		 Comentario
--20/12/2010  JQUISPE      Creación
FUNCTION GET_IND_CIERRE_CAJ_QF(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR)
RETURN CHAR;

--Descripcion: Obtiene el indicador de Adm local peuda dar VB Cajero y VB QF.
--Fecha       Usuario		 Comentario
--20/12/2010  JQUISPE      Creación
FUNCTION IS_ADMIN_CAJ_QF(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                           cSecUsu_in       IN CHAR)
RETURN CHAR;

--Descripcion: Lista los registros de resumen de forma de pago por primera vez
--Fecha       Usuario		          Comentario
--12/03/2013  Luigy Terrazos      Creación
FUNCTION CE_LISTA_REG_RES_FORM_FIRST(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                           cSecMovCaja_in       IN CHAR)
RETURN FarmaCursor;

--Descripcion: Se envia el Movimiento de Cierre y regresa el de Apertura
--Fecha       Usuario		          Comentario
--12/03/2013  Luigy Terrazos      Creación
FUNCTION CE_OBT_APER(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                           cSecMovCaja_in IN CHAR)
RETURN CHAR;

--Descripcion: GRABA LAS FORMAS DE PAGO QUE TIENE COMO ESTADO N EN EL CAMPO DE ACTUALIZACIONES AUTOMATICAS EN CE
--Fecha       Usuario		 Comentario
--03/06/2010  ASOSA      Creación
FUNCTION CE_FOR_PAG_NO_AUTO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecMovCaja_in IN CHAR,
                                     cIdUsu_in IN CHAR)

RETURN VARCHAR2;

--Descripcion: GRABA LAS FORMAS DE PAGO QUE TIENE COMO ESTADO N EN EL CAMPO DE ACTUALIZACIONES AUTOMATICAS EN CE
--Fecha       Usuario		 Comentario
--03/06/2010  ASOSA      Creación
FUNCTION CE_VALIDAR_MONT_TARJ(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecMovCaja_in IN CHAR,
                                     cIdUsu_in IN CHAR,
                                     cCodFomPag_in IN CHAR,
                                     cCodLote_in IN CHAR,
                                     cTipMon_in IN CHAR,
                                     cCantidad_in IN varchar2,
                                     cMont_in IN varchar2,
                                     cMontTol_in IN varchar2)

RETURN VARCHAR2;

--Descripcion: Lista las formas de pago con tarjeta que no fueron registradas
--Fecha       Usuario		          Comentario
--16/04/2013  Luigy Terrazos      Creación
FUNCTION CE_LISTA_FORM_PAGO_TRJ(cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cSecMovCaja_in       IN CHAR)
RETURN FarmaCursor;

/*  Descripción : Graba un nuevo registro en la tabla CE_FONDO_SENCILLO_ETV
    Fecha         Usuario       Comentario
    11/09/2013    wvillagomez   Creación
*/
FUNCTION CE_F_GRABA_REC_PAGO_SENCILLO(cCodGrupoCia_in         IN  CHAR,
                                        cCodCia_in              IN  CHAR,
                                        cCodLocal_in            IN  CHAR,
                                        cFolio_in               IN  CHAR,
                                        cTotal_in               IN  NUMBER,
                                        cMonto_in               IN  NUMBER,
                                        cDiferencia_in          IN  NUMBER,
                                        cTipFondoSencillo_in    IN  CHAR,
                                        cCodETV_in              IN  CHAR,
                                        cIdUsu_in               IN  VARCHAR2)
RETURN INT;

/*  Descripción : verifica si se puede generar un nuevo recibo o pago de sencillo
    Fecha         Usuario       Comentario
    11/09/2013    wvillagomez   Creación
*/

FUNCTION CE_F_VERIFICA_REC_SENCILLO (cCodGrupoCia_in   IN  CHAR,
                                      cCodCia_in        IN  CHAR,
                                      cCodLocal_in      IN  CHAR,
                                      cCodETV_in        IN  CHAR,
                                      cTotal_out        OUT NUMBER)
RETURN CHAR;

/*  Descripción : obtiene la Empresa de Traslado de Valores - ETV de un Local
    Fecha         Usuario       Comentario
    11/09/2013    wvillagomez   Creación
*/
FUNCTION CE_F_GET_ETV ( cCodGrupoCia_in   IN  CHAR,
                      cCodCia_in        IN  CHAR,
                      cCodLocal_in      IN  CHAR)
RETURN CHAR;

/*  Descripción : obtiene texto html para impresión de vale de recibo/pago de sencillo
    Fecha         Usuario       Comentario
    11/09/2013    wvillagomez   Creación
*/
FUNCTION CE_F_IMP_FONDO_SENCILLO( cCodFonSencillo IN NUMBER)
RETURN VARCHAR2;

  /**************************************************************************************************************************/
  --Descripcion: GUARDAR EL RESULTADO DEL PROCESO DE CONCILIACION
  --Fecha        Usuario		    Comentario
  --25/09/2013   LLEIVA         CREACION
  PROCEDURE VTA_GUARDA_LOG_CONCILIACION(cCodGrupoCia_in   IN CHAR,
                                       cCodCia_in        IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cCodProceso_in    IN CHAR,
                                       cDescConcepto_in  IN CHAR,
                                       cEstConciliacion_in  IN CHAR,
                                       cUsuCreador_in    IN CHAR);

  /**************************************************************************************************************************/
  --Descripcion: GUARDAR EL RESULTADO DEL PROCESO DE CONCILIACION 2
  --Fecha        Usuario		    Comentario
  --25/09/2013   LLEIVA         CREACION
  PROCEDURE VTA_GUARDA_LOG_CONCILIACION_2(cCodGrupoCia_in   IN CHAR,
                                       cCodCia_in           IN CHAR,
                                       cCodLocal_in         IN CHAR,
                                       cPidVendedor_in      IN CHAR,
                                       cFechaVenta_in       IN CHAR,
                                       cMontoVenta_in       IN CHAR,
                                       cNumCuotas_in        IN CHAR,
                                       cCodAutoriz_in       IN CHAR,
                                       cTrack2_in           IN CHAR,
                                       cCodAutorizPre_in    IN CHAR,
                                       cValorPorCuota_in    IN CHAR,
                                       cCaja_in             IN CHAR,
                                       cTipoTrans_in        IN CHAR,
                                       cCodServ_in          IN CHAR,
                                       cNumObjPago_in       IN CHAR,
                                       cNomCliente_in       IN CHAR,
                                       cCodVoucher_in       IN CHAR,
                                       cNumCompAnu_in       IN CHAR,
                                       cFechCompAnu_in      IN CHAR,
                                       cCodAutorizOrig_in   IN CHAR,
                                       cTipoCambio_in       IN CHAR,
                                       cNumTrace_in         IN CHAR,
                                       cCodAlianza_in       IN CHAR,
                                       cCodMonedaTrx_in     IN CHAR,
                                       cMonEstPago_in       IN CHAR,
                                       cDescConcepto_in     IN CHAR,
                                       cEstConciliacion_in  IN CHAR,
                                       cUsuCreador_in       IN CHAR,
                                       cCodLocalMigra_in IN CHAR);

  /*  Descripción : obtiene una lista del log de conciliacion para reintentar enviarlas
      Fecha         Usuario       Comentario
      17/Oct/2013   LLEIVA        Creación
  */
  FUNCTION VTA_LISTA_CONCILIACION_NOK(cCodGrupoCia_in   IN  CHAR,
                                      cCodCia_in        IN  CHAR,
                                      cCodLocal_in      IN  CHAR)
  RETURN FarmaCursor;

  /*  Descripción : actualiza el estado del registro de log de conciliacion indicado
                    si el estado del reintento de conciliacion es OK
      Fecha         Usuario       Comentario
      17/Oct/2013   LLEIVA        Creación
  */
  PROCEDURE VTA_ACT_LOG_CONCILIACION(cCodLogConc_in  IN CHAR);

  /*  Descripción : Obtiene el listado de ETVs presentes en el sistema
      Fecha         Usuario       Comentario
      27/Ene/2014   LLEIVA        Creación
  */
  FUNCTION CE_F_GET_LISTA_ETV RETURN FarmaCursor;
END;

/
