CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CE" AS
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
  -- KMONCADA 04.06.2015
  COD_FORMA_PAGO_REDONDEO_PTOS   VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE := '00092';

  COD_CIA_MARKET_01 CHAR(3):=  '004';                                     --ASOSA - 14/08/2014

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
  
  /*
      Descripción : Listar pedidos y documentos anulados
      Fecha                 Usuario       Comentario
      29/01/2015    ASOSA         Creación
  */
    FUNCTION CE_LISTA_DOC_ANUL(cCodGrupoCia_in	IN CHAR,
                                    cCodCia_in        IN CHAR,
                                    cCodLocal_in	  IN CHAR,
                                    cFechaCierreDia IN varchar2)
  RETURN FarmaCursor;
  
  /**********************************************************************************************/
  --Descripcion: CALCULA EL MONTO DE REDONDEO POR PUNTOS REDIMIDOS X TURNO
  --Fecha       Usuario		Comentario
  --04.06.2015  KMONCADA  Creación
  --22.06.2016  KMONCADA  Modificacion [CONV FAMISALUD] VA A CALCULAR POR COD DE FORMA DE PAGO PARA CONSIDERAR PUNTOS Y FAMISALUD
  FUNCTION GET_MONTO_TURNO_PTOS_REDONDEO(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cSecMovCaja_in  IN CHAR,
                                         cCodFormaPago_in IN VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE DEFAULT COD_FORMA_PAGO_REDONDEO_PTOS)
    RETURN NUMBER;
    
  /**********************************************************************************************/
  --Descripcion: CALCULA EL MONTO DE REDONDEO POR PUNTOS REDIMIDOS DEL DIA
  --Fecha       Usuario		Comentario
  --04.06.2015  KMONCADA  Creación
  --22.06.2016  KMONCADA  Modificacion [CONV FAMISALUD] VA A CALCULAR POR COD DE FORMA DE PAGO PARA CONSIDERAR PUNTOS Y FAMISALUD
  FUNCTION GET_MONTO_DIA_PTOS_REDONDEO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCierreDia_in    IN CHAR,
                                       cCodFormaPago_in IN VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE DEFAULT COD_FORMA_PAGO_REDONDEO_PTOS)
    RETURN NUMBER;
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CE" AS
 /****************************************************************************/
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
  RETURN VARCHAR2
  IS
   v_numSec NUMBER;
   cantidad NUMBER(3);
  BEGIN
       SELECT NVL(MAX(sec_forma_pago_entrega),0) + 1
       INTO   v_numSec
       FROM   Ce_Forma_Pago_Entrega
       WHERE  cod_grupo_cia = ccodgrupocia_in
       AND    cod_local = ccodlocal_in
       AND    sec_mov_caja = Csecmovcaja_In;

       SELECT COUNT(*) INTO cantidad --INI ASOSA, 11.06.2010
       FROM ce_forma_pago_entrega a
       WHERE a.cod_grupo_cia=cCodGrupoCia_in
       AND a.cod_local=cCodLocal_in
       AND a.sec_mov_caja=cSecMovCaja_in
       AND a.sec_forma_pago_entrega=v_numSec;

       IF cantidad>0 THEN
          RAISE_APPLICATION_ERROR(-20023,'La forma de pago entrega ya esta registrada.');
       ELSE
           INSERT INTO ce_forma_pago_entrega (cod_grupo_cia,cod_local,sec_forma_pago_entrega,sec_mov_caja,cod_forma_pago,cant_voucher,
                                          tip_moneda,mon_entrega,mon_entrega_total,usu_crea_forma_pago_ent,num_lote, est_forma_pago_ent)
           VALUES (Ccodgrupocia_In,ccodlocal_in,v_numsec,Csecmovcaja_In,Ccodformapago_In,ccantvoucher_in,ctipmoneda_in,
                                          cmonentrega_in,cmonentregatotal_in,Cusucreaentrega_In,cNumLote_in,'A');
       END IF;--FIN ASOSA, 11.06.2010

       RETURN ''||v_numSec;
  END;
  /****************************************************************************/
  PROCEDURE CE_ELIMINA_FORMA_PAGO(cCodGrupoCia_in	 IN CHAR,
  		   						              cCodLocal_in	   IN CHAR,
                                  cSecMovCaja_in   IN CHAR,
                                  cCodFormaPago_in IN CHAR,
                                  cTipMoneda_in    IN CHAR,
                                  cNumLote_in      IN CHAR,
                                  cSecFPEntrega_in IN CHAR,
                                  cCodUsuLocal_in  IN CHAR)
  IS
  BEGIN
--       DELETE FROM ce_forma_pago_entrega
       UPDATE CE_FORMA_PAGO_ENTREGA F
       SET F.EST_FORMA_PAGO_ENT = 'I',
           F.FEC_MOD_FORMA_PAGO_ENT = SYSDATE,
           F.USU_MOD_FORMA_PAGO_ENT = cCodUsuLocal_in
       WHERE  cod_grupo_cia = ccodgrupocia_in
       AND    cod_local = ccodlocal_in
       AND    sec_mov_caja = Csecmovcaja_In
       AND    cod_forma_pago = Ccodformapago_In
       AND    tip_moneda = ctipmoneda_in
       AND    num_lote = cNumLote_in--num_lote = cNumLote_in;
       AND    SEC_FORMA_PAGO_ENTREGA = cSecFPEntrega_in;
  END ;
  /****************************************************************************/
  -- ENVIAR EMAIL ALERTA AL ELIMINAR FORMA PAGO JCT 16FEB12
  PROCEDURE CE_ENVIA_EMAIL_ELIM_FPAGO(cCodGrupoCia_in	 IN CHAR,
  		   						                 cCodLocal_in	   IN CHAR,
                                     cSecMovCaja_in   IN CHAR,
                                     cCodFormaPago_in IN CHAR,
                                     cTipMoneda_in    IN CHAR,
                                     cSecFPEntrega_in IN CHAR,
                                     cCodUsuLocal_in  IN CHAR)
   IS
   PRAGMA AUTONOMOUS_TRANSACTION;
   -- declare local vars
   sMsg varchar2(255);
  BEGIN
     sMsg:='SecMovCaja: '  ||cSecMovCaja_in    ||', '||
               'CodFormaPago: '||cCodFormaPago_in  ||', '||
               'TipMoneda: '   ||cTipMoneda_in     ||', '||
               'SecFPEntrega: '||cSecFPEntrega_in  ||', '||
               'CodUsuLocal: ' ||cCodUsuLocal_in;


         FARMA_UTILITY.ENVIA_CORREO(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    'joliva@mifarma.com.pe,operador@mifarma.com.pe',
                                    'Eliminacion de Forma de Pago',
                                    'Elimina Forma Pago Crédito',
                                    sMsg,
                                    'tcanches@mifarma.com.pe'
                                  );
         commit;
        EXCEPTION
  		   WHEN OTHERS THEN
		   		ROLLBACK;

  END;
  /****************************************************************************/
  PROCEDURE CE_ELIMINA_FORMA_PAGO_SL(cCodGrupoCia_in	 IN CHAR,
  		   						                 cCodLocal_in	   IN CHAR,
                                     cSecMovCaja_in   IN CHAR,
                                     cCodFormaPago_in IN CHAR,
                                     cTipMoneda_in    IN CHAR,
                                     cSecFPEntrega_in IN CHAR,
                                     cCodUsuLocal_in  IN CHAR)
  IS

   VC_COD_FORMA_PAGO  VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE :='*****';
   VC_DESC_CORTA_FORMA_PAGO  VTA_FORMA_PAGO.DESC_CORTA_FORMA_PAGO%TYPE :='*****';

  BEGIN
       --DELETE FROM ce_forma_pago_entrega
       UPDATE CE_FORMA_PAGO_ENTREGA F
       SET F.EST_FORMA_PAGO_ENT = 'I',
           F.FEC_MOD_FORMA_PAGO_ENT = SYSDATE,
           F.USU_MOD_FORMA_PAGO_ENT = cCodUsuLocal_in
       WHERE  cod_grupo_cia = ccodgrupocia_in
       AND    cod_local = ccodlocal_in
       AND    sec_mov_caja = Csecmovcaja_In
       AND    cod_forma_pago = Ccodformapago_In
       AND    tip_moneda = ctipmoneda_in
       AND    SEC_FORMA_PAGO_ENTREGA=cSecFPEntrega_in;

       --- Enviar correo de alerta al eliminar forma pago credito jct 16Feb12

        --- Averigua si esxiste en forma pago credito
        BEGIN
         SELECT COD_FORMA_PAGO
                ,DESC_CORTA_FORMA_PAGO
         INTO VC_COD_FORMA_PAGO
           ,VC_DESC_CORTA_FORMA_PAGO
         FROM VTA_FORMA_PAGO
         WHERE  EST_FORMA_PAGO = 'A'
          AND IND_TARJ = 'N'
          AND IND_FORMA_PAGO_EFECTIVO = 'N'
          AND IND_FORMA_PAGO_CUPON = 'N'
          AND COD_FORMA_PAGO=cCodFormaPago_in
         ;

        EXCEPTION
        WHEN OTHERS THEN
                VC_COD_FORMA_PAGO:= '*****';

        END;

       -- si las vars. regresan CON DEFAULT esa forma de pago no es credito
       IF VC_COD_FORMA_PAGO<> '*****' AND VC_DESC_CORTA_FORMA_PAGO<> '*****' THEN
               CE_ENVIA_EMAIL_ELIM_FPAGO(cCodGrupoCia_in,
  		   						                 cCodLocal_in,
                                     cSecMovCaja_in,
                                     cCodFormaPago_in,
                                     cTipMoneda_in,
                                     cSecFPEntrega_in,
                                     cCodUsuLocal_in
                                   );
        END IF;
       --- jct
  END ;
  /****************************************************************************/

  FUNCTION CE_LISTA_DETALLE_FORMAS_PAGO(cCodGrupoCia_in	IN CHAR,
  		   						                    cCodLocal_in	  IN CHAR,
                                        cSecMovCaja_in  IN CHAR)
    RETURN FarmaCursor
  IS
    curCe FarmaCursor;
  BEGIN
    OPEN curCe FOR
         SELECT fpe.cod_forma_pago       || 'Ã' ||
                fp.desc_corta_forma_pago || 'Ã' ||
                fpe.cant_voucher         || 'Ã' ||
                decode(fpe.tip_moneda,TIP_MONEDA_SOLES,'SOLES','DOLARES') || 'Ã' ||
                to_char(fpe.mon_entrega,'999,990.00')          || 'Ã' ||
                to_char(fpe.mon_entrega_total,'999,990.00')    || 'Ã' ||
                nvl(fpe.num_lote,' ')                          || 'Ã' ||

                nvl((select decode(count(1),1,'S','N')
                    from   ce_sobre s
                    where  s.cod_grupo_cia = fpe.cod_grupo_cia
                    and    s.cod_local     = fpe.cod_local
                    and    s.sec_mov_caja  = fpe.sec_mov_caja
                    and    s.sec_forma_pago_entrega = fpe.sec_forma_pago_entrega),'N') || 'Ã' ||

                nvl((select s.cod_sobre
                    from   ce_sobre s
                    where  s.cod_grupo_cia = fpe.cod_grupo_cia
                    and    s.cod_local     = fpe.cod_local
                    and    s.sec_mov_caja  = fpe.sec_mov_caja
                    and    s.sec_forma_pago_entrega = fpe.sec_forma_pago_entrega),' ')   || 'Ã' ||--
                fpe.sec_mov_caja                              || 'Ã' ||
                fpe.tip_moneda                                || 'Ã' ||
                INDICADOR_SI                                  || 'Ã' ||
                FP.IND_FORMA_PAGO_AUTOMATICO_CE               || 'Ã' ||
                fpe.sec_forma_pago_entrega /* || 'Ã' || --

                nvl((select decode(count(1),1,'S','N')
                    from   ce_sobre s
                    where  s.cod_grupo_cia = fpe.cod_grupo_cia
                    and    s.cod_local     = fpe.cod_local
                    and    s.sec_mov_caja  = fpe.sec_mov_caja
                    and    s.sec_forma_pago_entrega = fpe.sec_forma_pago_entrega),'N')  --*/

         FROM   ce_forma_pago_entrega fpe,
                vta_forma_pago fp,
                vta_forma_pago_local fpl
         WHERE  fpe.cod_grupo_cia = cCodGrupoCia_in
         AND    fpe.cod_local = cCodLocal_in
         AND    fpe.sec_mov_caja = cSecMovCaja_in
         AND    fpe.cod_grupo_cia = fpl.cod_grupo_cia
         AND    fpe.cod_local = fpl.cod_local
         AND    fpe.cod_forma_pago = fpl.cod_forma_pago
         AND    fpe.est_forma_pago_ent = 'A'
         AND    fpl.cod_grupo_cia = fp.cod_grupo_cia
         AND    fpl.cod_forma_pago = fp.cod_forma_pago;
    RETURN curce ;
  END;
  /****************************************************************************/
  FUNCTION CE_LISTA_ANUL_PENDIENTES(cCodGrupoCia_in	  IN CHAR,
  		   						                cCodLocal_in	    IN CHAR,
                                    cSecMovCaja_in    IN CHAR,
                                    cCodCuadratura_in IN CHAR)
    RETURN FarmaCursor
  IS
    curCe FarmaCursor;
  BEGIN
      IF(Ccodcuadratura_In = C_C_REG_ANUL_PENDIENTE) THEN --Regularización de anulados pendientes
        OPEN curCe FOR
        SELECT decode(C.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET',C_C_IND_TIP_COMP_PAGO_GUIA,'GUIA')|| 'Ã' ||
               c.num_serie_local||c.num_comp_pago|| 'Ã' ||
               TO_CHAR(cp.fec_crea_comp_pago,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
               to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
               to_char(c.mon_total,'999,990.00')|| 'Ã' ||
               to_char(c.mon_vuelto,'999,990.00')|| 'Ã' ||
               c.tip_comp|| 'Ã' ||
               c.num_serie_local|| 'Ã' ||
               c.num_comp_pago
        FROM   ce_cuadratura_caja c,
               vta_comp_pago cp
        WHERE  
               c.cod_grupo_cia = cp.cod_grupo_cia
        AND    c.cod_local = cp.cod_local        
        AND    c.cod_grupo_cia = ccodgrupocia_in
        AND    c.cod_cuadratura = C_C_ANUL_PENDIENTE
        AND    c.cod_local = ccodlocal_in
        AND    c.num_serie_local||c.num_comp_pago = 
               (CASE NVL(CP.COD_TIP_PROC_PAGO,'0')
                     WHEN '1' THEN CP.NUM_COMP_PAGO_E
                     WHEN '0' THEN CP.NUM_COMP_PAGO
                  END
               )
            --FAC-ELECTRONICA :09.10.2014
        AND    cp.sec_mov_caja_anul  IN (SELECT sec_mov_caja_origen
                                         FROM   ce_mov_caja c
                                         WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                         AND    c.cod_local = ccodlocal_in
                                         AND    c.sec_mov_caja = cSecMovCaja_in)
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND NOT EXISTS (SELECT 1
                                FROM ce_cuadratura_caja w
                                WHERE
                                     w.cod_grupo_cia = ccodgrupocia_in
                                 AND w.cod_cuadratura = ccodcuadratura_in
                                 AND w.cod_local = ccodlocal_in
                                 AND w.num_serie_local = c.num_serie_local
                                 AND w.num_comp_pago = c.num_comp_pago
                                 AND to_char(w.mon_moneda_origen, '999,990.00') = to_char(c.mon_moneda_origen, '999,990.00')
                                 AND to_char(w.mon_total, '999,990.00')= to_char(c.mon_total, '999,990.00')
                                 AND w.tip_comp =  c.tip_comp
                                 AND w.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                                 )                                    
        UNION
        SELECT decode(C.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET',C_C_IND_TIP_COMP_PAGO_GUIA,'GUIA')|| 'Ã' ||
               c.num_serie_local||c.num_comp_pago|| 'Ã' ||
               TO_CHAR(cp.fec_crea_comp_pago,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
               to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
               to_char(c.mon_total,'999,990.00')|| 'Ã' ||
               to_char(c.mon_vuelto,'999,990.00')|| 'Ã' ||
               c.tip_comp|| 'Ã' ||
               c.num_serie_local|| 'Ã' ||
               c.num_comp_pago
        FROM   ce_cuadratura_caja c,
               vta_comp_pago cp
        WHERE  
               c.cod_grupo_cia = cp.cod_grupo_cia
        AND    c.cod_local = cp.cod_local               
        AND    c.cod_grupo_cia = ccodgrupocia_in
        AND    c.cod_cuadratura = C_C_ANUL_PENDIENTE
        AND    c.cod_local = ccodlocal_in
        AND    c.num_serie_local||c.num_comp_pago = 
               (CASE NVL(CP.COD_TIP_PROC_PAGO,'0')
                     WHEN '1' THEN CP.NUM_COMP_PAGO_E
                     WHEN '0' THEN CP.NUM_COMP_PAGO
                  END
               )
            --FAC-ELECTRONICA :09.10.2014
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    (c.num_serie_local||c.num_comp_pago,
               c.tip_comp)
               IN     (SELECT --NUM_COMP_PAGO,
                             FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO),
            --FAC-ELECTRONICA :09.10.2014
                             TIP_COMP_PAGO
                      FROM   VTA_COMP_PAGO COM
                      WHERE  COM.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                      AND    COM.COD_LOCAL     = C.COD_LOCAL       
                      AND    COM.NUM_PED_VTA IN (SELECT F.NUM_PED_VTA_ORIGEN FROM VTA_PEDIDO_VTA_CAB F
                                                 WHERE 
                                                        F.COD_GRUPO_CIA = COM.COD_GRUPO_CIA
                                                 AND    F.COD_LOCAL     = COM.COD_LOCAL       
                                                 AND    F.NUM_PED_VTA IN (SELECT DISTINCT CP.NUM_PED_VTA
                                                                         FROM  vta_comp_pago cp
                                                                         WHERE cp.cod_grupo_cia = F.COD_GRUPO_CIA
                                                                         AND   cp.cod_local = F.COD_LOCAL
                                                                         AND   cp.tip_comp_pago = C_C_IND_TIP_COMP_PAGO_NC
                                                                         AND   cp.sec_mov_caja  IN (SELECT sec_mov_caja_origen
                                                                                                    FROM   ce_mov_caja c
                                                                                                    WHERE  c.cod_grupo_cia = CP.COD_GRUPO_CIA
                                                                                                    AND    c.cod_local = CP.COD_LOCAL
                                                                                                    AND    c.sec_mov_caja = cSecMovCaja_in))))                                             
        
        
        AND NOT EXISTS (SELECT 1
                        FROM ce_cuadratura_caja w
                        WHERE
                             w.cod_grupo_cia = ccodgrupocia_in
                         AND w.cod_cuadratura = ccodcuadratura_in
                         AND w.cod_local = ccodlocal_in
                         AND w.num_serie_local = c.num_serie_local
                         AND w.num_comp_pago = c.num_comp_pago
                         AND to_char(w.mon_moneda_origen, '999,990.00') = to_char(c.mon_moneda_origen, '999,990.00')
                         AND to_char(w.mon_total, '999,990.00')= to_char(c.mon_total, '999,990.00')
                         AND w.tip_comp =  c.tip_comp
                         AND w.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                         );        
         ----- anterior Query.
        /*SELECT decode(C.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET',C_C_IND_TIP_COMP_PAGO_GUIA,'GUIA')|| 'Ã' ||
               c.num_serie_local||c.num_comp_pago|| 'Ã' ||
               TO_CHAR(cp.fec_crea_comp_pago,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
               to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
               to_char(c.mon_total,'999,990.00')|| 'Ã' ||
               to_char(c.mon_vuelto,'999,990.00')|| 'Ã' ||
               c.tip_comp|| 'Ã' ||
               c.num_serie_local|| 'Ã' ||
               c.num_comp_pago
        FROM   ce_cuadratura_caja c,
               vta_comp_pago cp
        WHERE  c.cod_grupo_cia = ccodgrupocia_in
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    c.cod_local = ccodlocal_in
        AND    c.cod_cuadratura = C_C_ANUL_PENDIENTE
        AND    cp.sec_mov_caja_anul  IN (SELECT sec_mov_caja_origen
                                         FROM   ce_mov_caja c
                                         WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                         AND    c.cod_local = ccodlocal_in
                                         AND    c.sec_mov_caja = cSecMovCaja_in)
        AND    c.cod_grupo_cia = cp.cod_grupo_cia
        AND    c.cod_local = cp.cod_local
        AND    c.num_comp_pago = --substr(cp.num_comp_pago,4,10)
        FARMA_UTILITY.GET_T_CORR_COMPROBA(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)

            --FAC-ELECTRONICA :09.10.2014
        AND    (c.num_serie_local,
                c.num_comp_pago,
                to_char(c.mon_moneda_origen,'999,990.00'),
                to_char(c.mon_total,'999,990.00'),
                c.tip_comp) NOT IN (SELECT c.num_serie_local,
                                           c.num_comp_pago,
                                           to_char(c.mon_moneda_origen,'999,990.00'),
                                           to_char(c.mon_total,'999,990.00'),
                                           c.tip_comp
                                    FROM   ce_cuadratura_caja c
                                    WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                    AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                                    AND    c.cod_local = ccodlocal_in
                                    AND    c.cod_cuadratura = ccodcuadratura_in)
        UNION
        SELECT decode(C.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET',C_C_IND_TIP_COMP_PAGO_GUIA,'GUIA')|| 'Ã' ||
               c.num_serie_local||c.num_comp_pago|| 'Ã' ||
               TO_CHAR(cp.fec_crea_comp_pago,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
               to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
               to_char(c.mon_total,'999,990.00')|| 'Ã' ||
               to_char(c.mon_vuelto,'999,990.00')|| 'Ã' ||
               c.tip_comp|| 'Ã' ||
               c.num_serie_local|| 'Ã' ||
               c.num_comp_pago
        FROM   ce_cuadratura_caja c,
               vta_comp_pago cp
        WHERE  c.cod_grupo_cia = ccodgrupocia_in
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    c.cod_local = ccodlocal_in
        AND    c.cod_cuadratura = C_C_ANUL_PENDIENTE
        AND    (c.num_serie_local||c.num_comp_pago,
               c.tip_comp)
               IN     (SELECT --NUM_COMP_PAGO,
                             FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO),
            --FAC-ELECTRONICA :09.10.2014
                             TIP_COMP_PAGO
                      FROM   VTA_COMP_PAGO COM
                      WHERE  COM.NUM_PED_VTA IN (SELECT F.NUM_PED_VTA_ORIGEN FROM VTA_PEDIDO_VTA_CAB F
                                                 WHERE F.NUM_PED_VTA IN (SELECT DISTINCT CP.NUM_PED_VTA
                                                                         FROM  vta_comp_pago cp
                                                                         WHERE cp.cod_grupo_cia = cCodGrupoCia_in
                                                                         AND   cp.cod_local = cCodLocal_in
                                                                         AND   cp.tip_comp_pago = C_C_IND_TIP_COMP_PAGO_NC
                                                                         AND   cp.sec_mov_caja  IN (SELECT sec_mov_caja_origen
                                                                                                    FROM   ce_mov_caja c
                                                                                                    WHERE  c.cod_grupo_cia = cCodGrupoCia_in
                                                                                                    AND    c.cod_local = cCodLocal_in
                                                                                                    AND    c.sec_mov_caja = cSecMovCaja_in))))                                             AND    c.cod_grupo_cia = cp.cod_grupo_cia
        AND    c.cod_grupo_cia = cp.cod_grupo_cia
        AND    c.cod_local = cp.cod_local
        AND    c.num_comp_pago = --substr(cp.num_comp_pago,4,10)
               FARMA_UTILITY.GET_T_CORR_COMPROBA(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
            --FAC-ELECTRONICA :09.10.2014
        AND    (c.num_serie_local,
                c.num_comp_pago,
                to_char(c.mon_moneda_origen,'999,990.00'),
                to_char(c.mon_total,'999,990.00'),
                c.tip_comp) NOT IN (SELECT c.num_serie_local,
                                           c.num_comp_pago,
                                           to_char(c.mon_moneda_origen,'999,990.00'),
                                           to_char(c.mon_total,'999,990.00'),
                                           c.tip_comp
                                    FROM   ce_cuadratura_caja c
                                    WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                    AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                                    AND    c.cod_local = ccodlocal_in
                                    AND    c.cod_cuadratura = ccodcuadratura_in);*/

      ELSIF(Ccodcuadratura_In = C_C_ANUL_DEL_PENDIENTE) THEN --Anulacion Delivery Pendiente
        OPEN curCe FOR
        SELECT decode(C.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET',C_C_IND_TIP_COMP_PAGO_GUIA,'GUIA')|| 'Ã' ||
               c.num_serie_local||c.num_comp_pago|| 'Ã' ||
               TO_CHAR(cp.fec_crea_comp_pago,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
               to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
               to_char(c.mon_total,'999,990.00')|| 'Ã' ||
               to_char(c.mon_vuelto,'999,990.00')|| 'Ã' ||
               c.tip_comp|| 'Ã' ||
               c.num_serie_local|| 'Ã' ||
               c.num_comp_pago
        FROM   ce_cuadratura_caja c,
               vta_comp_pago cp
        WHERE  c.cod_grupo_cia = ccodgrupocia_in
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    c.cod_local = ccodlocal_in
        AND    c.cod_cuadratura = C_C_DEL_PENDIENTES
        AND    cp.sec_mov_caja_anul  IN (SELECT sec_mov_caja_origen
                                         FROM   ce_mov_caja c
                                         WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                         AND    c.cod_local = ccodlocal_in
                                         AND    c.sec_mov_caja = cSecMovCaja_in)
        AND    c.cod_grupo_cia = cp.cod_grupo_cia
        AND    c.cod_local = cp.cod_local
        AND    c.num_comp_pago = --substr(cp.num_comp_pago,4,10)
         FARMA_UTILITY.GET_T_CORR_COMPROBA(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
            --FAC-ELECTRONICA :09.10.2014
        AND    cp.ind_comp_anul = INDICADOR_SI
        AND    CP.NUM_PEDIDO_ANUL IS NOT NULL
        AND    (c.num_serie_local,
                c.num_comp_pago,
                to_char(c.mon_moneda_origen,'999,990.00'),
                --to_char(c.mon_vuelto,'99,990.00'),
                c.tip_comp) NOT IN (SELECT c.num_serie_local,
                                           c.num_comp_pago,
                                           to_char(c.mon_moneda_origen,'999,990.00'),
                                           --to_char(c.mon_vuelto,'99,990.00'),
                                           c.tip_comp
                                    FROM   ce_cuadratura_caja c
                                    WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                    AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                                    AND    c.cod_local = ccodlocal_in
                                    AND    c.cod_cuadratura = Ccodcuadratura_In
                                    OR     c.cod_cuadratura = C_C_COB_DEL_PENDIENTE)

     --- NOTAS DE CREDITO
     --- DUBILLUZ     04/11/2008
      union
      SELECT decode(CD.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET',C_C_IND_TIP_COMP_PAGO_GUIA,'GUIA')|| 'Ã' ||
                     CD.num_serie_local||CD.num_comp_pago|| 'Ã' ||
                     TO_CHAR(cp.fec_crea_comp_pago,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
                     to_char(CD.mon_moneda_origen,'999,990.00')|| 'Ã' ||
                     to_char(CD.mon_total,'999,990.00')|| 'Ã' ||
                     to_char(CD.mon_vuelto,'999,990.00')|| 'Ã' ||
                     CD.tip_comp|| 'Ã' ||
                     CD.num_serie_local|| 'Ã' ||
                     CD.num_comp_pago
      FROM   (
              SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,C.NUM_PED_VTA_ORIGEN,C.SEC_MOV_CAJA,C.FEC_PED_VTA
              FROM   VTA_PEDIDO_VTA_CAB C
              WHERE  C.COD_GRUPO_CIA = ccodgrupocia_in
              AND    C.COD_LOCAL = ccodlocal_in
              AND    C.TIP_COMP_PAGO = '04' --NOTA DE CREDITO
              AND    C.EST_PED_VTA = 'C'
              AND    C.SEC_MOV_CAJA IN (SELECT sec_mov_caja_origen
                                       FROM   ce_mov_caja c
                                       WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                       AND    c.cod_local = ccodlocal_in
                                       AND    c.sec_mov_caja = cSecMovCaja_in)

              ) V,
              VTA_PEDIDO_VTA_CAB C1,
              VTA_COMP_PAGO CP,
              CE_CUADRATURA_CAJA CD
      WHERE   C1.COD_GRUPO_CIA = ccodgrupocia_in
      AND     C1.COD_LOCAL = ccodlocal_in
      AND     C1.COD_GRUPO_CIA = CD.COD_GRUPO_CIA
      AND     C1.COD_LOCAL = CD.COD_LOCAL
      AND     CD.COD_CUADRATURA = C_C_DEL_PENDIENTES
      AND     C1.COD_GRUPO_CIA = V.COD_GRUPO_CIA
      AND     C1.COD_LOCAL = V.COD_LOCAL
      AND     C1.NUM_PED_VTA = V.NUM_PED_VTA_ORIGEN
      AND     C1.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
      AND     C1.COD_LOCAL = CP.COD_LOCAL
      AND     C1.NUM_PED_VTA = CP.NUM_PED_VTA
      AND     CD.cod_grupo_cia = CP.cod_grupo_cia
      AND     CD.cod_local     = CP.cod_local
      AND     CD.num_comp_pago = --substr(CP.num_comp_pago,4,10)
      FARMA_UTILITY.GET_T_CORR_COMPROBA(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
            --FAC-ELECTRONICA :09.10.2014
      AND     CD.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO

      AND    (CD.num_serie_local,
              CD.num_comp_pago,
              to_char(CD.mon_moneda_origen,'999,990.00'),
                      CD.tip_comp) NOT IN (SELECT c.num_serie_local,
                                                 c.num_comp_pago,
                                                 to_char(c.mon_moneda_origen,'999,990.00'),
                                                 c.tip_comp
                                          FROM   ce_cuadratura_caja c
                                          WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                          AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                                          AND    c.cod_local = ccodlocal_in
                                          AND    c.cod_cuadratura = Ccodcuadratura_In
                                          OR     c.cod_cuadratura = C_C_COB_DEL_PENDIENTE);

      ELSIF(Ccodcuadratura_In = C_C_REG_COMP_MANUAL) THEN --Regularizacion de Comprobante Manual
        OPEN curCe FOR
        SELECT decode(C.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET',C_C_IND_TIP_COMP_PAGO_GUIA,'GUIA')|| 'Ã' ||
               c.num_serie_local||c.num_comp_pago|| 'Ã' ||
               TO_CHAR(c.fec_crea_cuadratura_caja,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
               to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
               to_char(c.mon_total,'999,990.00')|| 'Ã' ||
               to_char(c.mon_vuelto,'999,990.00')|| 'Ã' ||
               c.tip_comp|| 'Ã' ||
               c.num_serie_local|| 'Ã' ||
               c.num_comp_pago
        FROM   ce_cuadratura_caja c,
               vta_comp_pago cp
        WHERE  c.cod_grupo_cia = ccodgrupocia_in
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    c.cod_local = ccodlocal_in
        AND    c.cod_cuadratura = C_C_INGRESO_COMP_MANUAL
        AND    cp.sec_mov_caja  IN (SELECT c.sec_mov_caja_origen
                                    FROM ce_mov_caja c
                                    WHERE c.cod_grupo_cia = ccodgrupocia_in
                                    AND   c.cod_local = ccodlocal_in
                                    AND   c.sec_mov_caja = csecmovcaja_in)
        AND    (c.num_serie_local,
                c.num_comp_pago,
                to_char(c.mon_moneda_origen,'999,990.00'),
                to_char(c.mon_total,'999,990.00'),
                c.tip_comp) NOT IN (SELECT c.num_serie_local,
                                           c.num_comp_pago,
                                           to_char(c.mon_moneda_origen,'999,990.00'),
                                           to_char(c.mon_total,'999,990.00'),
                                           c.tip_comp
                                   FROM    ce_cuadratura_caja c
                                   WHERE   c.cod_grupo_cia = ccodgrupocia_in
                                   AND     C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                                   AND     c.cod_local = ccodlocal_in
                                   AND     c.cod_cuadratura = ccodcuadratura_in)
        AND    c.cod_grupo_cia = cp.cod_grupo_cia
        AND    c.cod_local = cp.cod_local
        AND    c.num_comp_pago =-- substr(cp.num_comp_pago,4,10);
        FARMA_UTILITY.GET_T_CORR_COMPROBA(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
        ;
                    --FAC-ELECTRONICA :09.10.2014
      END IF;
    RETURN curce;
  END;
  /****************************************************************************/
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
                                        cUsuCrea_in         IN CHAR)
  IS
    v_numSec NUMBER;
  BEGIN
       SELECT NVL(MAX(SEC_CUADRATURA_CAJA),0) + 1
       INTO   v_numSec
       FROM   Ce_Cuadratura_Caja
       WHERE  cod_grupo_cia = ccodgrupocia_in
       AND    cod_local = ccodlocal_in
       AND    sec_mov_caja = Csecmovcaja_In
       --AND    EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                -- SE OBVIA, PORQUE ESTE QUERY OBTIENE EL CORRELATIVO
       ;

       INSERT INTO CE_CUADRATURA_CAJA (COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA,SEC_CUADRATURA_CAJA,COD_CUADRATURA,
                                       NUM_SERIE_LOCAL,TIP_COMP,NUM_COMP_PAGO,MON_MONEDA_ORIGEN,MON_VUELTO,
                                       MON_TOTAL,USU_CREA_CUADRATURA_CAJA)
                                VALUES(ccodgrupocia_in,ccodlocal_in,csecmovcaja_in,V_NUMSEC, ccodcuadratura_in,
                                       cnumserielocal_in,ctipcomp_in,cnumcomppago_in,cmonmonedaorigen_in,cMonVuelto_in,
                                       cmontotal_in,cusucrea_in);

  END;
  /****************************************************************************/
 PROCEDURE CE_GRABA_CUADRATURA_CIERRE(cCodGrupoCia_in	IN CHAR,
  		   						                  cCodLocal_in	  IN CHAR,
                                      cSecMovCaja_in  IN CHAR,
                                      cUsuCrea_in     IN CHAR)
 IS
   v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
   
   --ERIOS 17.11.2014 Cambios de JLUNA
   CURSOR curAnulPen IS --anulados pendientes
     SELECT  c.mon_moneda_origen,
             c.mon_total,
             c.tip_comp,
             c.num_serie_local,
             c.num_comp_pago
     FROM    ce_cuadratura_caja c
     WHERE   c.cod_grupo_cia = ccodgrupocia_in
     AND     C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
     AND     c.cod_local = ccodlocal_in
     AND     c.cod_cuadratura = C_C_ANUL_PENDIENTE
     AND     (c.num_serie_local,
              c.num_comp_pago,
              c.mon_moneda_origen,
              c.mon_total,
              c.tip_comp)
     NOT IN  (SELECT c.num_serie_local,
                     c.num_comp_pago,
                     c.mon_moneda_origen,
                     c.mon_total,
                     c.tip_comp
              FROM   ce_cuadratura_caja c
              WHERE  c.cod_grupo_cia = ccodgrupocia_in
              AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
              AND    c.cod_local = ccodlocal_in
              AND    c.cod_cuadratura = C_C_REG_ANUL_PENDIENTE)
              AND    (c.num_serie_local||c.num_comp_pago,
                      c.tip_comp)
              IN     (SELECT --cp.num_comp_pago,
                     FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO),
                    --FAC-ELECTRONICA :09.10.2014
                             cp.tip_comp_pago
                       FROM  vta_comp_pago cp
                       WHERE cp.cod_grupo_cia = ccodgrupocia_in
                       AND   cp.cod_local = ccodlocal_in
                       AND   cp.Ind_Comp_Anul = INDICADOR_SI
                       AND   cp.sec_mov_caja_anul  IN (SELECT sec_mov_caja_origen
                                                       FROM   ce_mov_caja c
                                                       WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                                       AND    c.cod_local = ccodlocal_in
                                                       AND    c.sec_mov_caja = cSecMovCaja_in))
     UNION
     SELECT  c.mon_moneda_origen,
             c.mon_total,
             c.tip_comp,
             c.num_serie_local,
             c.num_comp_pago
     FROM    ce_cuadratura_caja c
     WHERE   c.cod_grupo_cia = ccodgrupocia_in
     AND     C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
     AND     c.cod_local = ccodlocal_in
     AND     c.cod_cuadratura = C_C_ANUL_PENDIENTE
     AND NOT exists 
       (SELECT 1
              FROM   ce_cuadratura_caja Cc
              WHERE  Cc.cod_grupo_cia = ccodgrupocia_in
              AND    Cc.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
              AND    Cc.cod_local = ccodlocal_in
              AND    Cc.cod_cuadratura = C_C_REG_ANUL_PENDIENTE
               and Cc.NUM_SERIE_LOCAL=C.NUM_SERIE_LOCAL 
               and Cc.NUM_COMP_PAGO=C.NUM_COMP_PAGO
               and Cc.MON_MONEDA_ORIGEN=C.MON_MONEDA_ORIGEN
               and Cc.MON_TOTAL=C.MON_TOTAL
               and Cc.TIP_COMP=C.TIP_COMP)
              and exists (
                    select 1
                      FROM   VTA_COMP_PAGO COM
                      WHERE  
                      FARMA_UTILITY.GET_T_COMPROBANTE(COM.COD_TIP_PROC_PAGO,COM.NUM_COMP_PAGO_E,COM.NUM_COMP_PAGO)=C.NUM_SERIE_LOCAL || C.NUM_COMP_PAGO               and 
                     C.TIP_COMP=com.TIP_COMP_PAGO and
                     com.cod_grupo_cia=c.cod_grupo_cia and com.cod_local=c.cod_local and  --agregar
                      COM.NUM_PED_VTA IN (SELECT F.NUM_PED_VTA_ORIGEN FROM VTA_PEDIDO_VTA_CAB F
                                                 WHERE 
                                                 f.cod_grupo_cia=com.cod_grupo_cia and f.cod_local=com.cod_local and --agregar
                                                 F.NUM_PED_VTA IN (SELECT DISTINCT CP.NUM_PED_VTA
                                                                         FROM  vta_comp_pago cp
                                                                         WHERE cp.cod_grupo_cia = cCodGrupoCia_in
                                                                         AND   cp.cod_local = cCodLocal_in
                                                                         AND   cp.tip_comp_pago = C_C_IND_TIP_COMP_PAGO_NC
                                                                         AND   cp.sec_mov_caja  IN (SELECT sec_mov_caja_origen
                                                                                                    FROM   ce_mov_caja c
                                                                                                    WHERE  c.cod_grupo_cia = cCodGrupoCia_in
                                                                                                    AND    c.cod_local = cCodLocal_in
                                                                                                    AND    c.sec_mov_caja = cSecMovCaja_in))));


   v_rCurAnulPend curAnulPen%ROWTYPE;

   CURSOR curCompManu IS --comprobantes manuales
     SELECT c.mon_moneda_origen,
            c.mon_total,
            c.tip_comp,
            c.num_serie_local,
            c.num_comp_pago
     FROM   ce_cuadratura_caja c
     WHERE  c.cod_grupo_cia = ccodgrupocia_in
     AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
     AND    c.cod_local = ccodlocal_in
     AND    c.cod_cuadratura = C_C_INGRESO_COMP_MANUAL
     AND    (c.num_serie_local,
             c.num_comp_pago,
             c.mon_moneda_origen,
             c.mon_total,
             c.tip_comp)
     NOT IN (SELECT c.num_serie_local,
                    c.num_comp_pago,
                    c.mon_moneda_origen,
                    c.mon_total,
                    c.tip_comp
             FROM   ce_cuadratura_caja c
             WHERE  c.cod_grupo_cia = ccodgrupocia_in
             AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
             AND    c.cod_local = ccodlocal_in
             AND    c.cod_cuadratura = C_C_REG_COMP_MANUAL)
             AND    (c.num_serie_local||c.num_comp_pago,c.tip_comp)
             IN     (SELECT --cp.num_comp_pago,
             FARMA_UTILITY.GET_T_COMPROBANTE(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO),
                    --FAC-ELECTRONICA :09.10.2014
                            cp.tip_comp_pago
                     FROM  vta_comp_pago cp
                     WHERE cp.cod_grupo_cia = ccodgrupocia_in
                     AND   cp.cod_local = ccodlocal_in
                     AND   cp.sec_mov_caja  IN (SELECT sec_mov_caja_origen
                                                FROM   ce_mov_caja c
                                                WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                                AND    c.cod_local = ccodlocal_in
                                                AND    c.sec_mov_caja = cSecMovCaja_in));
      v_rCurCompManu curCompManu%ROWTYPE;

       --JCORTEZ 25.02.10 ingreso de una anulacion de delivery pendiente
     CURSOR curAnulDelPen IS --anulados pendientes
     SELECT   c.mon_moneda_origen,
               c.mon_total,
               c.tip_comp,
               c.num_serie_local,
               c.num_comp_pago
        FROM   ce_cuadratura_caja c,
               vta_comp_pago cp
        WHERE  c.cod_grupo_cia = cCodGrupoCia_in
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    c.cod_local = cCodLocal_in
        AND    c.cod_cuadratura = C_C_DEL_PENDIENTES
        AND    cp.sec_mov_caja_anul  IN (SELECT sec_mov_caja_origen
                                         FROM   ce_mov_caja c
                                         WHERE  c.cod_grupo_cia = cCodGrupoCia_in
                                         AND    c.cod_local = cCodLocal_in
                                         AND    c.sec_mov_caja = cSecMovCaja_in)
        AND    c.cod_grupo_cia = cp.cod_grupo_cia
        AND    c.cod_local = cp.cod_local
        AND    c.num_comp_pago = --substr(cp.num_comp_pago,4,10)
               FARMA_UTILITY.GET_T_CORR_COMPROBA(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                    --FAC-ELECTRONICA :09.10.2014
        AND    cp.ind_comp_anul = 'S'
        AND    CP.NUM_PEDIDO_ANUL IS NOT NULL
        AND    (c.num_serie_local,
                c.num_comp_pago,
                to_char(c.mon_moneda_origen,'999,990.00'),
                c.tip_comp) NOT IN (SELECT c.num_serie_local,
                                           c.num_comp_pago,
                                           to_char(c.mon_moneda_origen,'999,990.00'),
                                           c.tip_comp
                                    FROM   ce_cuadratura_caja c
                                    WHERE  c.cod_grupo_cia = cCodGrupoCia_in
                                    AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                                    AND    c.cod_local = cCodLocal_in
                                    AND    c.cod_cuadratura = C_C_ANUL_DEL_PENDIENTE
                                    OR     c.cod_cuadratura = C_C_COB_DEL_PENDIENTE);

   v_rCurAnulDelPend curAnulDelPen%ROWTYPE;

   BEGIN
     FOR v_rCurAnulPend IN curAnulPen
     LOOP
          BEGIN
          v_nSecCuadratura := PTOVENTA_CE_ERN.GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);
          EXIT WHEN Curanulpen%NOTFOUND;
          INSERT INTO CE_CUADRATURA_CAJA (COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA,SEC_CUADRATURA_CAJA,COD_CUADRATURA,
                                          NUM_SERIE_LOCAL,TIP_COMP,NUM_COMP_PAGO,MON_MONEDA_ORIGEN,
                                          MON_TOTAL,
                                          USU_CREA_CUADRATURA_CAJA)
                                 VALUES  (ccodgrupocia_in,ccodlocal_in,csecmovcaja_in,v_nseccuadratura,C_C_REG_ANUL_PENDIENTE,
                                          v_rcuranulpend.num_serie_local,v_rcuranulpend.tip_comp,v_rcuranulpend.num_comp_pago,v_rcuranulpend.mon_moneda_origen,
                                          v_rcuranulpend.mon_total,
                                          Cusucrea_In);
           --COMMIT;
          END;
    END LOOP;

     FOR v_rCurCompManu IN curCompManu
     LOOP
          BEGIN
          v_nSecCuadratura := PTOVENTA_CE_ERN.GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);
          EXIT WHEN curCompManu%NOTFOUND;
          INSERT INTO CE_CUADRATURA_CAJA (COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA,SEC_CUADRATURA_CAJA,COD_CUADRATURA,
                                          NUM_SERIE_LOCAL,TIP_COMP,NUM_COMP_PAGO,MON_MONEDA_ORIGEN,
                                          MON_TOTAL,
                                          USU_CREA_CUADRATURA_CAJA)
                                 VALUES  (ccodgrupocia_in,ccodlocal_in,csecmovcaja_in,v_nseccuadratura,C_C_REG_COMP_MANUAL,
                                          v_rCurCompManu.num_serie_local,v_rCurCompManu.tip_comp,v_rCurCompManu.num_comp_pago,v_rCurCompManu.mon_moneda_origen,
                                          v_rCurCompManu.mon_total,
                                          Cusucrea_In);
           --COMMIT;
          END;
    END LOOP;

       FOR v_rCurAnulDelPend IN curAnulDelPen
     LOOP
          BEGIN
          v_nSecCuadratura := PTOVENTA_CE_ERN.GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);
          EXIT WHEN curAnulDelPen%NOTFOUND;
          INSERT INTO CE_CUADRATURA_CAJA (COD_GRUPO_CIA,COD_LOCAL,SEC_MOV_CAJA,SEC_CUADRATURA_CAJA,COD_CUADRATURA,
                                          NUM_SERIE_LOCAL,TIP_COMP,NUM_COMP_PAGO,MON_MONEDA_ORIGEN,
                                          MON_TOTAL,
                                          USU_CREA_CUADRATURA_CAJA)
                                 VALUES  (ccodgrupocia_in,ccodlocal_in,csecmovcaja_in,v_nseccuadratura,C_C_ANUL_DEL_PENDIENTE,
                                          v_rCurAnulDelPend.num_serie_local,v_rCurAnulDelPend.tip_comp,v_rCurAnulDelPend.num_comp_pago,v_rCurAnulDelPend.mon_moneda_origen,
                                          v_rCurAnulDelPend.mon_total,
                                          Cusucrea_In);
           --COMMIT;
          END;
    END LOOP;
  END;
   /****************************************************************************/
  FUNCTION CE_LISTA_ELIMINA_CUADRATURA(cCodGrupoCia_in	 IN CHAR,
  		   						                   cCodLocal_in	     IN CHAR,
                                       cSecMovCaja_in    IN CHAR,
                                       cCodCuadratura_in IN CHAR)
  RETURN FarmaCursor
  IS
  curCe FarmaCursor;
   BEGIN
      IF(Ccodcuadratura_In = C_C_IND_DINERO_FALSO) THEN --DINERO FALSO
        OPEN curCe FOR
             SELECT DEcode(C.tip_dinero,TIP_DINERO_BILLETE,'BILLETE',TIP_DINERO_MONEDA,'MONEDA')|| 'Ã' ||
                    DECODE(C.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')|| 'Ã' ||
                    to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
                    NVL(c.serie_billete,' ')|| 'Ã' ||
                    ' ' ||'Ã'||
                    ' ' ||'Ã'||
                    ' ' ||'Ã'||
                    c.sec_mov_caja || 'Ã' ||
                    mv.ind_vb_cajero|| 'Ã' ||
                    c.sec_cuadratura_caja
             FROM   ce_cuadratura_caja c,
                    ce_mov_caja mv
             WHERE  c.cod_grupo_cia = ccodgrupocia_in
             AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
             AND    c.cod_local = ccodlocal_in
             AND    c.cod_cuadratura = ccodcuadratura_in
             AND    c.sec_mov_caja = csecmovcaja_in
             AND    c.cod_grupo_cia = mv.cod_grupo_cia
             AND    c.cod_local = mv.cod_local
             AND    c.sec_mov_caja = mv.sec_mov_caja;
      ELSIF (Ccodcuadratura_In = C_C_IND_ROBO) THEN -- ROBO
        OPEN curCe FOR
             SELECT c.cod_forma_pago|| 'Ã' ||
                    f.desc_corta_forma_pago|| 'Ã' ||
                    to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
                    to_char(c.mon_total,'999,990.00')|| 'Ã' ||
                    ' ' ||'Ã'||
                    ' ' ||'Ã'||
                    ' ' ||'Ã'||
                    c.sec_mov_caja|| 'Ã' ||
                    mv.ind_vb_cajero|| 'Ã' ||
                    c.sec_cuadratura_caja
             FROM   ce_cuadratura_caja c,
                    vta_forma_pago f,
                    ce_mov_caja mv
             WHERE  c.cod_grupo_cia = ccodgrupocia_in
             AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
             AND    c.cod_local = ccodlocal_in
             AND    c.cod_cuadratura = ccodcuadratura_in
             AND    c.sec_mov_caja = csecmovcaja_in
             AND    c.cod_grupo_cia = f.cod_grupo_cia
             AND    c.cod_forma_pago = f.cod_forma_pago
             AND    c.cod_grupo_cia = mv.cod_grupo_cia
             AND    c.cod_local = mv.cod_local
             AND    c.sec_mov_caja = mv.sec_mov_caja;

      ELSIF (cCodCuadratura_in = C_C_IND_COTCOMP_TURNO) THEN --COTIZACION COMPETENCIA EN TURNO - ASOSA, 12.08.2010
            OPEN curCe FOR
            SELECT NVL(EC.NUM_DOC,' ') ||'Ã'||
                   DECODE(NVL(EC.tip_doc,' '),C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',C_C_IND_TIP_COMP_PAGO_FACT,'FACTURA','03','GUIA','04','NOTA DE CREDITO','05','TICKET')||'Ã'||
                   to_char(EC.FEC_NOTA_ES_CAB,'dd/MM/yyyy') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   cd.sec_mov_caja ||'Ã'||
                   cc.ind_vb_cajero ||'Ã'||
                   cd.sec_cuadratura_caja ||'Ã'||
                   ec.num_nota_es ||'Ã'||
                   CD.SEC_CUADRATURA_CAJA ||'Ã'||
                   TO_CHAR(CC.FEC_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CAJA CD ,
                   LGT_NOTA_ES_CAB EC,
                   CE_MOV_CAJA CC
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = cCodLocal_in
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.COD_GRUPO_CIA = EC.COD_GRUPO_CIA
            AND    CD.COD_LOCAL = EC.COD_LOCAL
            AND    CD.COT_NUM_NOTA_ES = EC.NUM_NOTA_ES
            AND    CC.COD_GRUPO_CIA=CD.COD_GRUPO_CIA
            AND    CC.COD_LOCAL=CD.COD_LOCAL
            AND    CC.SEC_MOV_CAJA=CD.SEC_MOV_CAJA
            AND    cc.sec_mov_caja = cSecMovCaja_in;
      ELSE
        OPEN curCe FOR
             SELECT decode(C.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',C_C_IND_TIP_COMP_PAGO_FACT,'FACTURA',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET',C_C_IND_TIP_COMP_PAGO_GUIA,'GUIA')|| 'Ã' ||
                    c.num_serie_local|| 'Ã' ||
                    c.num_comp_pago|| 'Ã' ||
                    to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
                    to_char(c.mon_total,'999,990.00')|| 'Ã' ||
                    to_char(c.mon_vuelto,'999,990.00')|| 'Ã' ||
                    c.tip_comp|| 'Ã' ||
                    c.sec_mov_caja|| 'Ã' ||
                    mv.ind_vb_cajero|| 'Ã' ||
                    c.sec_cuadratura_caja
             FROM   ce_cuadratura_caja c,
                    ce_mov_caja mv
             WHERE  c.cod_grupo_cia = ccodgrupocia_in
             AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
             AND    c.cod_local = ccodlocal_in
             AND    c.cod_cuadratura = ccodcuadratura_in
             AND    c.sec_mov_caja = csecmovcaja_in
             AND    c.cod_grupo_cia = mv.cod_grupo_cia
             AND    c.cod_local = mv.cod_local
             AND    c.sec_mov_caja = mv.sec_mov_caja;
      END IF;
   RETURN curce;
  END;
   /****************************************************************************/
  FUNCTION CE_VALIDA_ELIMINACION(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cSecMovCaja_in    IN CHAR,
                                 cSecCuadratura_in IN CHAR,
                                 cCodCuadratura_in IN CHAR)
  RETURN NUMBER
  IS
  v_nCantidad NUMBER;
   BEGIN
      IF(ccodcuadratura_in = C_C_ANUL_PENDIENTE) THEN
        SELECT COUNT(1)
        INTO   v_nCantidad
        FROM   ce_cuadratura_caja c
        WHERE  c.cod_grupo_cia = ccodgrupocia_in
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    c.cod_local = ccodlocal_in
        AND    c.cod_cuadratura IN (C_C_REG_ANUL_PENDIENTE)
        AND    (C.NUM_SERIE_LOCAL,C.TIP_COMP,C.NUM_COMP_PAGO) IN (SELECT A.NUM_SERIE_LOCAL, A.TIP_COMP, A.NUM_COMP_PAGO
                                                                  FROM   CE_CUADRATURA_CAJA A
                                                                  WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                                                  AND    A.COD_LOCAL = ccodlocal_in
                                                                  AND    A.SEC_MOV_CAJA = csecmovcaja_in
                                                                  AND    A.SEC_CUADRATURA_CAJA = Cseccuadratura_In);
      ELSIF (ccodcuadratura_in = C_C_DEL_PENDIENTES) THEN
        SELECT COUNT(1)
        INTO   v_nCantidad
        FROM   ce_cuadratura_caja c
        WHERE  c.cod_grupo_cia = ccodgrupocia_in
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    c.cod_local = ccodlocal_in
        AND    c.cod_cuadratura IN (C_C_COB_DEL_PENDIENTE,C_C_ANUL_DEL_PENDIENTE)
        AND    (C.NUM_SERIE_LOCAL,C.TIP_COMP,C.NUM_COMP_PAGO) IN (SELECT A.NUM_SERIE_LOCAL, A.TIP_COMP, A.NUM_COMP_PAGO
                                                                  FROM   CE_CUADRATURA_CAJA A
                                                                  WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                                                  AND    A.COD_LOCAL = ccodlocal_in
                                                                  AND    A.SEC_MOV_CAJA = csecmovcaja_in
                                                                  AND    A.SEC_CUADRATURA_CAJA = Cseccuadratura_In);
      ELSIF (ccodcuadratura_in =  C_C_INGRESO_COMP_MANUAL) THEN
        SELECT COUNT(1)
        INTO   v_nCantidad
        FROM   ce_cuadratura_caja c
        WHERE  c.cod_grupo_cia = ccodgrupocia_in
        AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    c.cod_local = ccodlocal_in
        AND    c.cod_cuadratura IN (C_C_REG_COMP_MANUAL)
        AND    (C.NUM_SERIE_LOCAL,C.TIP_COMP,C.NUM_COMP_PAGO) IN (SELECT A.NUM_SERIE_LOCAL, A.TIP_COMP, A.NUM_COMP_PAGO
                                                                  FROM   CE_CUADRATURA_CAJA A
                                                                  WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                                                  AND    A.COD_LOCAL = ccodlocal_in
                                                                  AND    A.SEC_MOV_CAJA = csecmovcaja_in
                                                                  AND    A.SEC_CUADRATURA_CAJA = Cseccuadratura_In);
        IF( v_nCantidad = 0 ) THEN
            SELECT COUNT(1)
            INTO   v_nCantidad
            FROM   ce_cuadratura_caja c
            WHERE  c.cod_grupo_cia = ccodgrupocia_in
            AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    c.cod_local = ccodlocal_in
            AND    c.cod_cuadratura IN (C_C_DEL_PENDIENTES)
            AND    (C.NUM_SERIE_LOCAL,C.TIP_COMP,C.NUM_COMP_PAGO) IN (SELECT A.NUM_SERIE_LOCAL, A.TIP_COMP, A.NUM_COMP_PAGO
                                                                      FROM   CE_CUADRATURA_CAJA A
                                                                      WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                                                      AND    A.COD_LOCAL = ccodlocal_in
                                                                      AND    A.SEC_MOV_CAJA = csecmovcaja_in
                                                                      AND    A.SEC_CUADRATURA_CAJA = Cseccuadratura_In);
        END IF;
      ELSE
        SELECT 0
        INTO   v_nCantidad
        FROM   DUAL;
      END IF;
    RETURN v_nCantidad;
   END;
    /****************************************************************************/
   PROCEDURE CE_ELIMINA_CUADRATURA(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cCodCuadratura_in IN CHAR,
                                   cSecMovcaja_in    IN CHAR,
                                   cSecCuadratura_in IN CHAR)
   IS
   BEGIN
     --RHERRERA: ACTUALIZA LA CUADRATURA DE ESTADO A (activo) --- >   I (inactivo)
             UPDATE CE_CUADRATURA_CAJA C  SET
             --USU_MOD_CUADRATURA_CAJA= vIdUsu_in ,
             FEC_MOD_CUADRATURA_CAJA = SYSDATE,
             EST_CUADRATURA_CAJA = 'I'
             WHERE  c.cod_grupo_cia = ccodgrupocia_in
              AND    c.cod_local = ccodlocal_in
              AND    c.sec_mov_caja = cSecMovCaja_in
              AND    c.cod_cuadratura = ccodcuadratura_in
              AND    c.sec_cuadratura_caja = cSecCuadratura_in;

   /*

     DELETE FROM CE_CUADRATURA_CAJA c
     WHERE  c.cod_grupo_cia = ccodgrupocia_in
     AND    c.cod_local = ccodlocal_in
     AND    c.sec_mov_caja = cSecMovCaja_in
     AND    c.cod_cuadratura = ccodcuadratura_in
     AND    c.sec_cuadratura_caja = cSecCuadratura_in;*/

   END;
    /****************************************************************************/
  FUNCTION CE_OBTIENE_RANGO_COMP(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cSecMovcaja_in  IN CHAR)
  RETURN FarmaCursor
  IS
  curCe FarmaCursor;
   BEGIN
        OPEN curce FOR
             SELECT nvl(v2.mib,' ')|| 'Ã' ||
                    nvl(v2.mxb,' ')|| 'Ã' ||
                    nvl(v1.mif,' ')|| 'Ã' ||
                    nvl(v1.mxf,' ')
               FROM (SELECT MIN(num_comp_pago)mif,
                            MAX(num_comp_pago)mxf
                     FROM   vta_comp_pago cp
                     WHERE  cp.cod_grupo_cia = cCodGrupoCia_in
                     AND    cp.cod_local = cCodLocal_in
                     AND    cp.tip_comp_pago = C_C_IND_TIP_COMP_PAGO_FACT
                     AND    substr(cp.num_comp_pago,0,3)= cCodLocal_in    --facturas
                     AND    cp.sec_mov_caja IN (SELECT sec_mov_caja_origen FROM ce_mov_caja WHERE sec_mov_caja = cSecMovcaja_in))v1,
                    (SELECT MIN(num_comp_pago)mib,
                             MAX(num_comp_pago)mxb
                     FROM   vta_comp_pago cp
                     WHERE  cp.cod_grupo_cia = cCodGrupoCia_in
                     AND    cp.cod_local = cCodLocal_in
                     AND    cp.tip_comp_pago = C_C_IND_TIP_COMP_PAGO_BOL
                     AND    substr(cp.num_comp_pago,0,3)= cCodLocal_in --boletas
                     AND    cp.sec_mov_caja IN (SELECT sec_mov_caja_origen FROM ce_mov_caja WHERE sec_mov_caja = cSecMovcaja_in))v2;
   RETURN CURCE;
   END;
    /****************************************************************************/
  FUNCTION CE_LISTA_CUADRATURAS(cCodGrupoCia_in	IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cFecCierre_in   IN CHAR)
  RETURN FarmaCursor
  AS
    curCe FarmaCursor;

    nMontoPorSobres number;
  BEGIN

    nMontoPorSobres := CE_F_NUM_MONTO_SOBRES(cCodGrupoCia_in,
    		   						             cCodLocal_in ,
                                   cFecCierre_in);

    dbms_output.put_line(''||nMontoPorSobres);
    OPEN curCe FOR
         SELECT COD_CUADRATURA|| 'Ã' ||
                DESC_CUADRATURA|| 'Ã' ||
                --nMontoPorSobres|| 'Ã' ||
                TO_CHAR(decode(TRIM(COD_CUADRATURA),C_C_DEPOSITO_VENTA,NVL(V1.SUMA,0.00)+nMontoPorSobres,NVL(V1.SUMA,0.00)) ,'999,999,990.00')|| 'Ã' ||
                TIP_CUADRATURA
         FROM   CE_CUADRATURA,
                (SELECT CUADRATURA_CIERRE_DIA.COD_CUADRATURA CODIGO,
                        NVL(CUADRATURA.DESC_CUADRATURA,' ') DESCRIPCION,
                        SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL) )SUMA
                 FROM   CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
                        CE_CUADRATURA CUADRATURA
                 WHERE  CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = ccodgrupocia_in
                 AND    CUADRATURA_CIERRE_DIA.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                 AND    CUADRATURA_CIERRE_DIA.COD_LOCAL = ccodlocal_in
                 AND    CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA = TO_DATE(cFecCierre_in,'dd/MM/yyyy')
                 AND    CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
                 AND    CUADRATURA_CIERRE_DIA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
                 GROUP BY CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                          CUADRATURA.DESC_CUADRATURA)V1
         WHERE  COD_GRUPO_CIA = ccodgrupocia_in
         AND EST_CUADRATURA = ESTADO_ACTIVO
         AND TIP_CUADRATURA IN ('03','04')
         AND COD_CUADRATURA = V1.CODIGO(+);
    RETURN curCe;
  END;
  /****************************************************************************/
  FUNCTION CE_LISTA_COT_COMPETENCIA(cCodGrupoCia_in	IN CHAR,
                                    cCodLocal_in	  IN CHAR,
                                    cFechaCierreDia IN CHAR)
  RETURN FarmaCursor
  AS
    curCe FarmaCursor;
  BEGIN
    OPEN curCe FOR
		--ERIOS 2.4.6 Se muestra tipo documento
         SELECT --DECODE(C.tip_doc,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',C_C_IND_TIP_COMP_PAGO_FACT,'FACTURA','03','GUIA','04','NOTA DE CREDITO',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET')||'Ã'||
                T.DESC_COMP||'Ã'||
                c.num_doc||'Ã'||
                to_char(c.val_total_nota_es_cab,'999,990.00')||'Ã'||
                c.num_nota_es ||'Ã'||
                to_char(c.fec_nota_es_cab,'dd/MM/yyyy')||'Ã'||
                nvl(c.desc_empresa,' ') ||'Ã'||
                c.tip_doc
         FROM   lgt_nota_es_cab c JOIN VTA_TIP_COMP T ON (T.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                          AND T.TIP_COMP = C.TIP_DOC)
         WHERE  c.cod_grupo_cia = ccodgrupocia_in
         AND    c.cod_local = ccodlocal_in
         AND    c.tip_origen_nota_es = C_C_TIPO_GUIA_COMPETENCIA
         AND    c.tip_nota_es = C_C_TIPO_INGRESO
         AND    c.fec_proceso_ce IS NULL
         AND    C.EST_NOTA_ES_CAB = ESTADO_ACTIVO
         AND    (c.num_nota_es,
                 to_char(c.val_total_nota_es_cab,'999,990.00'))
         NOT IN  (SELECT D.Num_Nota_Es,
                         to_char(d.mon_moneda_origen,'999,990.00')
                  FROM   CE_CUADRATURA_CIERRE_DIA D
                  WHERE  d.cod_grupo_cia = Ccodgrupocia_In
                  AND    D.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                  AND    d.cod_local = Ccodlocal_In
                  AND    d.cod_cuadratura = C_C_COTIZA_COMP);
    RETURN curCe;
  END;
   /****************************************************************************/

  FUNCTION CE_LISTA_ANUL_CUADRATURA_CD(cCodGrupoCia_in   IN CHAR,
                                       cCodlLocal_in     IN CHAR,
                                       cCodCuadratura_in IN CHAR,
                                       cFechaCD_in       IN CHAR)
  RETURN FarmaCursor
  AS
     curCe FarmaCursor;
  BEGIN
       IF(ccodcuadratura_in = C_C_DEPOSITO_VENTA) THEN -- deposito por venta
          OPEN  curCe FOR
          SELECT vta.desc_corta_forma_pago ||'Ã'||
                   NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   EF.NOM_ENTIDAD_FINANCIERA ||'Ã'||
                   NVL(TO_CHAR(CD.FEC_OPERACION,'dd/MM/yyyy HH24:MI:SS'),' ')||'Ã'||
                   nvl(cd.num_operacion,' ') ||'Ã'||
                   FC.NUM_CUENTA_BANCO ||'Ã'||
                   nvl(CD.NOM_AGENCIA,' ') ||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' '||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_ENTIDAD_FINANCIERA EF,
                   CE_ENTIDAD_FINANCIERA_CUENTA FC,
                   CE_CUADRATURA_CIERRE_DIA CD,
                   vta_forma_pago vta
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.SEC_ENT_FINAN_CUENTA = FC.SEC_ENT_FINAN_CUENTA
            AND    FC.COD_ENTIDAD_FINANCIERA = EF.COD_ENTIDAD_FINANCIERA
            AND    vta.cod_grupo_cia = cd.cod_grupo_cia
            AND    vta.cod_forma_pago = cd.cod_forma_pago
            UNION
            SELECT F.DESC_CORTA_FORMA_PAGO ||'Ã'||
                   NVL(DECODE(CF.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ')||'Ã'||
                   TO_CHAR(CF.MON_ENTREGA,'999,990.00') ||'Ã'||
                   TO_CHAR(CF.MON_ENTREGA_TOTAL,'999,990.00')||'Ã'||
                   'PROSEGUR' ||'Ã'||
                   NVL(TO_CHAR(S.FEC_CREA_SOBRE,'dd/MM/yyyy HH24:MI:SS'),' ') ||'Ã'||
                   --' ' ||'Ã'||
                   S.COD_SOBRE  ||'Ã'||
                   '0000000' ||'Ã'||
                   ' ' ||'Ã'||
                   NVL((SELECT TRIM('Recibido por QF - '||U.NOM_USU||' '||U.APE_PAT||' '||U.APE_MAT) as quimico FROM
                    PBL_USU_LOCAL U,
                    CE_CIERRE_DIA_VENTA CD
                    WHERE CD.COD_GRUPO_CIA = U.COD_GRUPO_CIA
                    AND   CD.COD_LOCAL     = U.COD_LOCAL
                    AND   CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
                    AND   CD.COD_LOCAL     = CCODLLOCAL_IN
                    AND   CD.FEC_CIERRE_DIA_VTA = TO_DATE(CFECHACD_IN,'DD/MM/YYYY')
                    AND   CD.IND_VB_CIERRE_DIA = 'S'
                    AND   CD.SEC_USU_LOCAL_VB(+)  = U.SEC_USU_LOCAL ),' ')||'Ã'||
                   ' '||'Ã'||
                   ' '||'Ã'||
                   TO_CHAR(S.FEC_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_SOBRE S,
                   CE_FORMA_PAGO_ENTREGA CF,
                   VTA_FORMA_PAGO F
            WHERE  CF.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CF.COD_LOCAL     = CCODLLOCAL_IN
            AND    S.FEC_DIA_VTA    = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    S.COD_GRUPO_CIA = CF.COD_GRUPO_CIA
            AND    S.COD_LOCAL = CF.COD_LOCAL
            AND    S.SEC_MOV_CAJA = CF.SEC_MOV_CAJA
            AND    S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA
            AND    CF.COD_GRUPO_CIA = F.COD_GRUPO_CIA
            AND    CF.COD_FORMA_PAGO = F.COD_FORMA_PAGO
            AND    CF.EST_FORMA_PAGO_ENT = 'A';

       ELSIF(ccodcuadratura_in = C_C_SERVICIOS_BASICOS) THEN -- servicios basicos
          OPEN  curCe FOR
            SELECT S.DESC_SERVICIO ||'Ã'||
                   p.desc_corta_proveedor ||'Ã'||
                   NVL(CD.Num_Documento,' ') ||'Ã'||
                   NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   NVL(TO_CHAR(CD.FEC_EMISION,'dd/MM/yyyy'),' ')||'Ã'||
                   NVL(TO_CHAR(CD.FEC_OPERACION,'dd/MM/yyyy HH24:MI:SS'),' ')||'Ã'||
                   NVL(TO_CHAR(Cd.Fec_Vencimiento,'dd/MM/yyyy'),' ')||'Ã'||
                   NVL(CD.NOM_TITULAR_SERVICIO,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   CE_SERVICIO S,
                   ce_proveedor p,
                   ce_servicio_proveedor sp
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_SERVICIO = Sp.COD_SERVICIO
            AND    cd.cod_proveedor = sp.cod_proveedor
            AND    sp.cod_servicio = s.cod_servicio
            AND    sp.cod_proveedor = p.cod_proveedor;
       ELSIF(ccodcuadratura_in = C_C_REEMBOLSO_C_CH) THEN -- REEMBOLSO DE CAJA CHICA
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy');
       ELSIF(ccodcuadratura_in = C_C_PAGO_PLANILLA) THEN -- PAGO PLANILLA
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   MT.NOM_TRAB ||' '|| MT.APE_PAT_TRAB ||' '|| NVL(MT.APE_MAT_TRAB,0)||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_MAE_TRAB MT,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_TRAB = MT.COD_TRAB
            AND    cd.cod_cia = mt.cod_cia;
       ELSIF(ccodcuadratura_in = C_C_COTIZA_COMP) THEN -- COTIZACION COMPETENCIA
          OPEN  curCe FOR
            SELECT NVL(EC.NUM_DOC,' ') ||'Ã'||
                   DECODE(NVL(EC.tip_doc,' '),C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',C_C_IND_TIP_COMP_PAGO_FACT,'FACTURA','03','GUIA','04','NOTA DE CREDITO','05','TICKET')||'Ã'||
                   to_char(EC.FEC_NOTA_ES_CAB,'dd/MM/yyyy') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ec.num_nota_es ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA ||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD ,
                   LGT_NOTA_ES_CAB EC
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.COD_GRUPO_CIA = EC.COD_GRUPO_CIA
            AND    CD.COD_LOCAL = EC.COD_LOCAL
            AND    CD.NUM_NOTA_ES = EC.NUM_NOTA_ES
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy');
       ELSIF(ccodcuadratura_in = C_C_ENTREGAS_RENDIR) THEN -- ENTREGAS A RENDIR
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   to_char(CD.Fec_Operacion,'dd/MM/yyyy HH24:MI:SS') ||'Ã'||
                   nvl(MT.NOM_TRAB ||' '|| MT.APE_PAT_TRAB ||' '|| MT.APE_MAT_TRAB,' ')||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(CD.DESC_MOTIVO,' ')||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_MAE_TRAB MT,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_TRAB = MT.COD_TRAB
            AND    cd.cod_cia = mt.cod_cia;
       ELSIF(ccodcuadratura_in = C_C_ROBO_ASALTO) THEN -- ROBO POR ASALTO
          OPEN  curCe FOR
            SELECT FP.DESC_CORTA_FORMA_PAGO||'Ã'||
                   DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(CD.DESC_MOTIVO,' ')||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   VTA_FORMA_PAGO FP,
                   VTA_FORMA_PAGO_LOCAL PL
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    FP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
            AND    FP.COD_FORMA_PAGO = PL.COD_FORMA_PAGO
            AND    PL.COD_GRUPO_CIA = CD.COD_GRUPO_CIA
            AND    PL.COD_LOCAL = CD.COD_LOCAL
            AND    PL.COD_FORMA_PAGO = CD.COD_FORMA_PAGO;
       ELSIF(ccodcuadratura_in = C_C_DINERO_FALSO) THEN -- DINERO FALSO
          OPEN  curCe FOR
            SELECT DECODE(NVL(CD.TIP_DINERO,' '),TIP_DINERO_BILLETE,'BILLETE',TIP_DINERO_MONEDA,'MONEDA')||'Ã'||
                   DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(CD.SERIE_BILLETE,' ') ||'Ã'||
                   TO_CHAR(CD.MON_PARCIAL,'999,990.00')||'Ã'||
                   NVL(UL.NOM_USU ||' '|| UL.APE_PAT ||' '|| UL.APE_MAT,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   cd.tip_moneda||cd.tip_dinero||cd.mon_moneda_origen||cd.serie_billete||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   PBL_USU_LOCAL UL,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    UL.COD_GRUPO_CIA = CD.COD_GRUPO_CIA
            AND    UL.COD_LOCAL = CD.COD_LOCAL
            AND    UL.COD_TRAB = CD.COD_TRAB;
       ELSIF(ccodcuadratura_in = C_C_OTROS_DESEMBOLSOS) THEN -- OTROS DESEMBOLSOS
          OPEN  curCe FOR
            SELECT DECODE(CD.TIP_DOCUMENTO,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA','05','TICKET')||'Ã'||
                   NVL(cd.num_serie_documento ||' - '|| CD.Num_Documento,' ')||'Ã'||
                   DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(to_char(CD.FEC_EMISION,'dd/MM/yyyy'),' ')||'Ã'||
                   nvl(to_char(CD.FEC_OPERACION,'dd/MM/yyyy HH24:MI:SS'),' ')||'Ã'||
                   NVL(CD.DESC_RUC,' ')||'Ã'||
                   NVL(CD.DESC_RAZON_SOCIAL,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy');
       ELSIF(ccodcuadratura_in = C_C_FONDO_SENCILLO) THEN -- FONDO SENCILLO LOCAL NUEVO
          OPEN  curCe FOR
            SELECT DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   L.DESC_CORTA_LOCAL||'Ã'||
                   CD.COD_AUTORIZACION||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   PBL_LOCAL L
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND    CD.COD_LOCAL_NUEVO = L.COD_LOCAL;
       ELSIF(ccodcuadratura_in = C_C_DSCT_PERSONAL) THEN -- DESCEUNTO PERSONAL
          OPEN  curCe FOR
            SELECT DECODE(NVL(CD.TIP_COMP,' '),C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA','05','TICKET')||'Ã'||
                   --NVL(cd.num_serie_local||CD.NUM_COMP_PAGO,' ')||'Ã'||
                   NVL(CD.NUM_PED_VTA,' ')||'Ã'||
                   DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   TO_CHAR(CD.MON_PARCIAL,'999,990.00')||'Ã'||
                   NVL(UL.NOM_USU ||' '|| UL.APE_PAT ||' '|| UL.APE_MAT,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   NVL(CD.TIP_COMP,' ')||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   PBL_USU_LOCAL UL,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            --AND    UL.EST_USU  = ESTADO_ACTIVO
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    UL.COD_GRUPO_CIA = CD.COD_GRUPO_CIA
            AND    UL.COD_LOCAL = CD.COD_LOCAL
            AND    UL.COD_TRAB = CD.COD_TRAB;
       ELSIF(ccodcuadratura_in = C_C_ADELANTO) THEN -- ADELANTO
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   MT.NOM_TRAB ||' '|| MT.APE_PAT_TRAB ||' '|| NVL(MT.APE_MAT_TRAB,0)||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_MAE_TRAB MT,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_TRAB = MT.COD_TRAB
            AND    cd.cod_cia = mt.cod_cia;
       ELSIF(ccodcuadratura_in = C_C_GRATIFICACION) THEN -- gratificacion
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   MT.NOM_TRAB ||' '|| MT.APE_PAT_TRAB ||' '|| NVL(MT.APE_MAT_TRAB,0)||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_MAE_TRAB MT,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_TRAB = MT.COD_TRAB
            AND    cd.cod_cia = mt.cod_cia;
       ELSIF(ccodcuadratura_in = C_C_DELIVERY_PERDIDO) THEN -- DELIVERY PERDIDO
          OPEN  curCe FOR
            SELECT DECODE(NVL(CD.TIP_COMP,' '),C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA','05','TICKET')||'Ã'||
                   NVL(cd.num_serie_local||CD.NUM_COMP_PAGO,' ')||'Ã'||
                   NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.MON_TOTAL,'999,990.00')||'Ã'||
                   TO_CHAR(CD.MON_PERDIDO_TOTAL ,'999,990.00')||'Ã'||
                   p.desc_corta_proveedor ||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   ce_proveedor p
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_PROVEEDOR = P.COD_PROVEEDOR;
       END IF;
    RETURN curce;
  END;

   /****************************************************************************/
   PROCEDURE CE_AGREGA_COT_COMPETENCIA(cCodGrupoCia_in   IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cFechaCierreDV_in IN CHAR,
                                       cCodCuadratura_in IN CHAR,
                                       cMontoTotal_in    IN NUMBER,
                                       cNumSec_in        IN CHAR,
                                       cUsuCrea_in       IN CHAR,
                                       cGlosa_in         IN CHAR)
   IS
     v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
     v_ValorSunaT ce_cuadratura.valor_sunat%TYPE;
     V_REFERENCIA CE_CUADRATURA_CIERRE_DIA.NUM_REFERENCIA%TYPE;
   BEGIN

    SELECT VALOR_SUNAT INTO V_VALORSUNAT
    FROM  CE_CUADRATURA
    WHERE COD_GRUPO_CIA = CCODGRUPOCIA_IN
    AND   COD_CUADRATURA = CCODCUADRATURA_IN;

    V_REFERENCIA := V_VALORSUNAT||'-'||CCODLOCAL_IN||'-'||TO_CHAR(to_date(cFechaCierreDV_in,'dd/MM/yyyy HH24:MI:SS'),'ddMMyyyy');

    v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cfechacierredv_in);
      INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA,COD_LOCAL,FEC_CIERRE_DIA_VTA,
                                           SEC_CUADRATURA_CIERRE_DIA,COD_CUADRATURA,
                                           TIP_MONEDA,MON_MONEDA_ORIGEN,MON_TOTAL,NUM_NOTA_ES,
                                           Mon_Parcial,USU_CREA_CUADRA_CIERRE_DIA,Desc_Motivo,
                                           MES_PERIODO,
                                           ANO_EJERCICIO,NUM_REFERENCIA)
                                  VALUES   (ccodgrupocia_in,ccodlocal_in,cfechacierredv_in,
                                            V_NSECCUADRATURA,ccodcuadratura_in,'01',cmontototal_in,
                                            cmontototal_in,Cnumsec_In,cmontototal_in,
                                            cusucrea_in,cglosa_in,To_char(TO_date(cFechaCierreDV_in,'dd/MM/yyyy HH24:MI:SS'),'MM'),
                                            to_char(TO_date(cFechaCierreDV_in,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),v_referencia);
    END;


   /****************************************************************************/
   PROCEDURE CE_ELIMINA_CUADRATURA_CD(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cFechaCierreDV_in IN CHAR,
                                      cCodCuadratura_in IN CHAR,
                                      cSecCuadratura_in IN CHAR)
   IS
   BEGIN

        UPDATE CE_CUADRATURA_CIERRE_DIA CD  SET
             --usu_mod_cuadra_cierre_dia= vIdUsu_in ,
             fec_mod_cuadra_cierre_dia = SYSDATE,
             est_cuadrat_c_dia = 'I'
                WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
        AND    CD.COD_LOCAL = CCODLOCAL_IN
        AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACIERREDV_IN,'dd/MM/yyyy')
        AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
        AND    CD.SEC_CUADRATURA_CIERRE_DIA = CSECCUADRATURA_IN;


        /*



        DELETE FROM CE_CUADRATURA_CIERRE_DIA CD
        WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
        AND    CD.COD_LOCAL = CCODLOCAL_IN
        AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACIERREDV_IN,'dd/MM/yyyy')
        AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
        AND    CD.SEC_CUADRATURA_CIERRE_DIA = CSECCUADRATURA_IN;*/



   END;

   /****************************************************************************/
  FUNCTION GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cFechaCD_in     IN CHAR)
  RETURN NUMBER
  IS
    v_nSec CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
  BEGIN
      SELECT NVL(MAX(SEC_CUADRATURA_CIERRE_DIA),0) + 1 INTO v_nSec
      FROM  CE_CUADRATURA_CIERRE_DIA
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND   COD_LOCAL = cCodLocal_in
      AND   FEC_CIERRE_DIA_VTA = TO_DATE(cFechaCD_in,'dd/MM/yyyy');
    RETURN v_nSec;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_RANGO_COMP_CD(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cFechaCierreDV  IN CHAR)
  RETURN FarmaCursor
  IS
  curCe FarmaCursor;
   BEGIN
        OPEN curce FOR
          SELECT nvl(v2.mib,' ')|| 'Ã' ||
                 nvl(v2.mxb,' ')|| 'Ã' ||
                 nvl(v1.mif,' ')|| 'Ã' ||
                 nvl(v1.mxf,' ')
          FROM  (SELECT MIN(num_comp_pago)mif,
                        MAX(num_comp_pago)mxf
                 FROM   vta_comp_pago cp
                 WHERE  cp.cod_grupo_cia = cCodGrupoCia_in
                 AND    cp.cod_local = cCodLocal_in
                 AND    cp.tip_comp_pago = C_C_IND_TIP_COMP_PAGO_FACT
                 AND    substr(cp.num_comp_pago,0,3)= cCodLocal_in    --facturas
                 AND    to_char(cp.fec_crea_comp_pago,'dd/MM/yyyy') IN (SELECT fec_dia_vta FROM ce_mov_caja WHERE fec_dia_vta = '' || Cfechacierredv || '')
                 )v1,
                (SELECT MIN(num_comp_pago)mib,
                        MAX(num_comp_pago)mxb
                 FROM   vta_comp_pago cp
                 WHERE  cp.cod_grupo_cia = cCodGrupoCia_in
                 AND    cp.cod_local = cCodLocal_in
                 AND    cp.tip_comp_pago =C_C_IND_TIP_COMP_PAGO_BOL
                 AND    substr(cp.num_comp_pago,0,3)= cCodLocal_in   --boletas
                 AND    to_char(cp.fec_crea_comp_pago,'dd/MM/yyyy') IN (SELECT fec_dia_vta FROM ce_mov_caja WHERE fec_dia_vta = '' || Cfechacierredv || '')
                 )v2;
        RETURN CURCE;
   END;
  /****************************************************************************/
  PROCEDURE CE_ACTUALIZA_GUIA_COT_COMP(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNotaes_in   IN CHAR,
                                       cFechaCD_in     IN CHAR,
                                       cUsuModNota_in  IN CHAR)
  IS
    v_est DATE;
  BEGIN
       SELECT C.FEC_PROCESO_CE INTO V_EST
       FROM   LGT_NOTA_ES_CAB C
       WHERE  C.COD_GRUPO_CIA = CCODGRUPOCIA_IN
       AND    C.COD_LOCAL = CCODLOCAL_IN
       AND    C.NUM_NOTA_ES = Cnumnotaes_In;

       IF V_EST IS NULL THEN
          V_EST := cFechaCD_in;
       ELSE v_est := NULL;
       END IF;

       UPDATE LGT_NOTA_ES_CAB C
       SET    C.FEC_PROCESO_CE = V_EST,
              C.FEC_MOD_NOTA_ES_CAB = SYSDATE,
              C.Usu_Mod_Nota_Es_Cab = Cusumodnota_In
       WHERE  C.COD_GRUPO_CIA = CCODGRUPOCIA_IN
       AND    C.COD_LOCAL = CCODLOCAL_IN
       AND    C.NUM_NOTA_ES = Cnumnotaes_In;

  END;
/****************************************************************************/
  FUNCTION CE_OBTIENE_PROVEEDOR(cTipoServicio IN CHAR)
  RETURN FarmaCursor
  IS
    curCe FarmaCursor;
  BEGIN
    OPEN curCe FOR
      SELECT pr.cod_proveedor|| 'Ã' ||pr.desc_corta_proveedor
      FROM   ce_proveedor pr,
             ce_servicio_proveedor sp
      WHERE  pr.cod_proveedor = sp.cod_proveedor
      AND    sp.cod_servicio = Ctiposervicio;

    RETURN curCe;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_DETTALLE_CUADRATURA(cCodGrupoCia_in	  IN CHAR,
  		   						                    cCodLocal_in	    IN CHAR,
                                        cFecCierreDia_in  IN CHAR,
                                        cCodCuadratura_in IN CHAR)
  RETURN FarmaCursor
  IS
  curCe FarmaCursor;
   BEGIN
      IF(Ccodcuadratura_In = C_C_IND_DINERO_FALSO) THEN --DINERO FALSO
        OPEN curCe FOR
            SELECT nvl(mc.sec_usu_local,' ')|| 'Ã' ||
                   nvl(c.usu_crea_cuadratura_caja,' ')|| 'Ã' ||
                   nvl(mc.num_caja_pago,0)|| 'Ã' ||
                   nvl(mc.num_turno_caja,0)|| 'Ã' ||
                   DEcode(C.tip_dinero,TIP_DINERO_BILLETE,'BILLETE',TIP_DINERO_MONEDA,'MONEDA')|| 'Ã' ||
                   DECODE(C.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')|| 'Ã' ||
                   to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
                   to_char(c.Mon_Total,'999,990.00')|| 'Ã' ||
                   NVL(c.serie_billete,' ')
            FROM   ce_cuadratura_caja c,
                   ce_mov_caja mc
            WHERE  c.cod_grupo_cia = ccodgrupocia_in
            AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    c.cod_local = ccodlocal_in
            AND    C.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                      FROM   CE_MOV_CAJA A
                                      WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                      AND    A.COD_LOCAL = ccodlocal_in
                                      AND    A.FEC_DIA_VTA = TO_DATE(cfeccierredia_in,'dd/MM/yyyy')
                                      AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
            AND    c.cod_grupo_cia = mc.cod_grupo_cia
            AND    c.cod_local = mc.cod_local
            AND    c.sec_mov_caja = mc.sec_mov_caja
            AND    c.cod_cuadratura = ccodcuadratura_in;
      ELSIF (Ccodcuadratura_In = C_C_DEFICIT_ASUMIDO_CAJERO) THEN -- DEFICIT ASUMIDO
        OPEN curCe FOR
            SELECT nvl(mc.sec_usu_local,' ')|| 'Ã' ||
                   nvl(mc.USU_CREA_MOV_CAJA,' ')|| 'Ã' ||
                   nvl(mc.num_caja_pago,0)|| 'Ã' ||
                   nvl(mc.num_turno_caja,0)|| 'Ã' ||
                   to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
                   to_char(c.mon_total,'999,990.00')
            FROM   ce_cuadratura_caja c,
                   ce_mov_caja mc
            WHERE  c.cod_grupo_cia = ccodgrupocia_in
            AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    c.cod_local = ccodlocal_in
            AND    C.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                      FROM   CE_MOV_CAJA A
                                      WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                      AND    A.COD_LOCAL = ccodlocal_in
                                      AND    A.FEC_DIA_VTA = TO_DATE(cfeccierredia_in,'dd/MM/yyyy')
                                      AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
            AND    c.cod_grupo_cia = mc.cod_grupo_cia
            AND    c.cod_local = mc.cod_local
            AND    c.sec_mov_caja = mc.sec_mov_caja
            AND    c.cod_cuadratura = ccodcuadratura_in;
      ELSIF (Ccodcuadratura_In = C_C_IND_ROBO) THEN -- ROBO
        OPEN curCe FOR
            SELECT nvl(mc.sec_usu_local,' ')|| 'Ã' ||
                   nvl(c.usu_crea_cuadratura_caja,' ')|| 'Ã' ||
                   nvl(mc.num_caja_pago,0)|| 'Ã' ||
                   nvl(mc.num_turno_caja,0)|| 'Ã' ||
                   f.desc_corta_forma_pago|| 'Ã' ||
                   to_char(c.mon_moneda_origen,'999,990.00')|| 'Ã' ||
                   to_char(c.mon_total,'999,990.00')
            FROM   ce_cuadratura_caja c,
                   vta_forma_pago f,
                   ce_mov_caja mc
            WHERE  c.cod_grupo_cia = ccodgrupocia_in
            AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    c.cod_local = ccodlocal_in
            AND    C.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                      FROM   CE_MOV_CAJA A
                                      WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                      AND    A.COD_LOCAL = ccodlocal_in
                                      AND    A.FEC_DIA_VTA = TO_DATE(cfeccierredia_in,'dd/MM/yyyy')
                                      AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
            AND    c.cod_grupo_cia = mc.cod_grupo_cia
            AND    c.cod_local = mc.cod_local
            AND    c.sec_mov_caja = mc.sec_mov_caja
            AND    c.cod_grupo_cia = f.cod_grupo_cia
            AND    c.cod_forma_pago = f.cod_forma_pago
            AND    c.cod_cuadratura = ccodcuadratura_in;
      ELSE
        OPEN curCe FOR
            SELECT nvl(mc.sec_usu_local,' ')|| 'Ã' ||
                   nvl(c.usu_crea_cuadratura_caja,' ')|| 'Ã' ||
                   nvl(mc.num_caja_pago,0)|| 'Ã' ||
                   nvl(mc.num_turno_caja,0)|| 'Ã' ||
                   decode(C.tip_comp,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',COD_TIP_COMP_TICKET,'TICKET',' ')|| 'Ã' ||
                   nvl(c.num_serie_local,'0')|| 'Ã' ||
                   nvl(c.num_comp_pago,'0')|| 'Ã' ||
                   nvl(to_char(c.mon_moneda_origen,'999,990.00'),'0')|| 'Ã' ||
                   to_char(nvl(c.mon_total,'0'),'999,990.00')|| 'Ã' ||
                   to_char(nvl(c.mon_vuelto,'0'),'999,990.00')
            FROM   ce_cuadratura_caja c,
                   ce_mov_caja mc
            WHERE  c.cod_grupo_cia = ccodgrupocia_in
            AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    c.cod_local = ccodlocal_in
            AND    C.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                      FROM   CE_MOV_CAJA A
                                      WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                      AND    A.COD_LOCAL = ccodlocal_in
                                      AND    A.FEC_DIA_VTA = TO_DATE(cfeccierredia_in,'dd/MM/yyyy')
                                      AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
            AND    c.cod_grupo_cia = mc.cod_grupo_cia
            AND    c.cod_local = mc.cod_local
            AND    c.sec_mov_caja = mc.sec_mov_caja
            AND    c.cod_cuadratura = ccodcuadratura_in;
      END IF;
   RETURN curce;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_DETTALLE_FORMAS_PAGO(cCodGrupoCia_in	IN CHAR,
  		   						                     cCodLocal_in	    IN CHAR,
                                         cFecCierreDia_in IN CHAR,
                                         cCodFormaPago_in IN CHAR)
  RETURN FarmaCursor
  IS
  curCe FarmaCursor;
   BEGIN
     -- KMONCADA 04.06.2015 PARA EL CASO DE FORMA DE PAGO REDONDEO X PUNTOS REDIMIDOS
     -- NO SE REGISTRAN EN CE_FORMA_PAGO_ENTREGA
    --IF COD_FORMA_PAGO_REDONDEO_PTOS != cCodFormaPago_in THEN 
    -- KMONCADA 22.06.2016 [ARCANGEL] SE CAMBIA POR QUE HAY MAS DE UNA FP POR REDONDEO ASUMIDO POR MIFARMA
    IF FARMA_UTILITY.F_IS_FPAGO_GRATUITO_REDONDEO(ccodgrupocia_in, cCodFormaPago_in) = 'N' THEN
        OPEN curce FOR
            SELECT nvl(mc.sec_usu_local,' ')|| 'Ã' ||
                   nvl(cf.usu_crea_forma_pago_ent,' ')|| 'Ã' ||
                   nvl(mc.num_caja_pago,0)|| 'Ã' ||
                   nvl(mc.num_turno_caja,0)|| 'Ã' ||
                   nvl(cf.cant_voucher,0)|| 'Ã' ||
                   to_char(cf.mon_entrega,'999,990.00')|| 'Ã' ||
                   to_char(cf.mon_entrega_total,'999,990.00')
            FROM   ce_forma_pago_entrega cf,
                   ce_mov_caja mc
            WHERE  cf.cod_grupo_cia = ccodgrupocia_in
            AND    cf.cod_local = ccodlocal_in
            AND    Cf.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                                      FROM   CE_MOV_CAJA A
                                      WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                                      AND    A.COD_LOCAL = ccodlocal_in
                                      AND    A.FEC_DIA_VTA = TO_DATE(cfeccierredia_in,'dd/MM/yyyy')
                                      AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
            AND   Cf.Cod_Grupo_Cia = mc.cod_grupo_cia
            AND   cf.cod_local = mc.cod_local
            AND   cf.sec_mov_caja = mc.sec_mov_caja
            AND   cf.cod_forma_pago = ccodformapago_in
            AND   cf.est_forma_pago_ent = 'A';
            
    ELSE
      OPEN curce FOR
        SELECT NVL(CE.SEC_USU_LOCAL,' ') || 'Ã' ||
               NVL(CE.USU_CREA_MOV_CAJA,' ')|| 'Ã' ||
               NVL(CE.NUM_CAJA_PAGO,0) || 'Ã' ||
               NVL(CE.NUM_TURNO_CAJA,0) || 'Ã' ||
               0 || 'Ã' ||
               TO_CHAR(FPP.IM_TOTAL_PAGO,'999,990.00') || 'Ã' ||
               TO_CHAR(FPP.IM_TOTAL_PAGO,'999,990.00')
        FROM VTA_FORMA_PAGO FP,
             VTA_FORMA_PAGO_PEDIDO FPP,
             VTA_PEDIDO_VTA_CAB CAB,
             CE_MOV_CAJA CE
        WHERE CE.COD_GRUPO_CIA = ccodgrupocia_in
        AND CE.COD_LOCAL = ccodlocal_in
        AND CE.FEC_DIA_VTA = TO_DATE(cfeccierredia_in,'dd/MM/yyyy')
        AND CE.TIP_MOV_CAJA = TIP_MOV_CIERRE
        AND FP.COD_GRUPO_CIA = CE.COD_GRUPO_CIA
        AND FP.COD_FORMA_PAGO = cCodFormaPago_in--COD_FORMA_PAGO_REDONDEO_PTOS
        AND FPP.COD_GRUPO_CIA  = FP.COD_GRUPO_CIA
        AND FPP.COD_LOCAL = CE.COD_LOCAL
        AND FPP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
        AND CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA
        AND CAB.COD_LOCAL = FPP.COD_LOCAL
        AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA
        AND CAB.EST_PED_VTA = 'C'
        AND CAB.SEC_MOV_CAJA=CE.SEC_MOV_CAJA_ORIGEN
        ORDER BY CE.FEC_CREA_MOV_CAJA DESC;
    END IF;
  RETURN curce;
 END;

  /****************************************************************************/
  PROCEDURE CE_GRABA_FORMA_PAGO_CIERRE(cCodGrupoCia_in IN CHAR,
  		   						                   cCodLocal_in	   IN CHAR,
                                       cSecMovCaja_in  IN CHAR,
                                       cUsuCrea_in     IN CHAR)
  IS
  CURSOR curFormaPago IS --formas de pago
         SELECT FP.COD_FORMA_PAGO CODIGO,
                TO_CHAR(SUM(FPP.IM_TOTAL_PAGO - FPP.VAL_VUELTO),'999,990.00') MONTO,
                SUM(NVL(FPP.CANT_CUPON,0)) CANTIDAD
         FROM   VTA_FORMA_PAGO_PEDIDO FPP,
                VTA_FORMA_PAGO FP
         WHERE  FPP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    FPP.COD_LOCAL = cCodLocal_in
         AND    FPP.NUM_PED_VTA IN (SELECT NUM_PED_VTA
                                    FROM   VTA_PEDIDO_VTA_CAB CAB
                                    WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CAB.COD_LOCAL = cCodLocal_in
                                    AND    CAB.SEC_MOV_CAJA IN (SELECT C.SEC_MOV_CAJA_ORIGEN
                                                                FROM   CE_MOV_CAJA C
                                                                WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
                                                                AND    C.COD_LOCAL = cCodLocal_in
                                                                AND    C.SEC_MOV_CAJA = cSecMovCaja_in)
                                    AND    CAB.EST_PED_VTA = EST_PED_COBRADO)
                                    --AND    CAB.IND_PEDIDO_ANUL = INDICADOR_NO)
         AND    (FP.IND_FORMA_PAGO_AUTOMATICO_CE = INDICADOR_SI AND  FP.IND_TARJ = INDICADOR_NO)
         --AND    FP.IND_TARJ = INDICADOR_NO
         --AND    FP.COD_FORMA_PAGO NOT IN (FORMA_PAGO_EFEC_SOLES, FORMA_PAGO_EFEC_DOLARES)
         AND    FPP.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
         AND    FPP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
         GROUP BY FP.COD_FORMA_PAGO;


       --JCORTEZ 18.03.10 Se agregaran formas de pago tarjetas
       --ERIOS 14.10.2013 Cantidad
        CURSOR curFormaPagoTarj IS
        SELECT   FP.COD_FORMA_PAGO CODIGO,FPP.COD_LOTE,
                TO_CHAR(SUM(FPP.IM_TOTAL_PAGO - FPP.VAL_VUELTO),'999,990.00') MONTO,
                COUNT(FPP.CANT_CUPON) CANTIDAD
         FROM   VTA_FORMA_PAGO_PEDIDO FPP,
                VTA_FORMA_PAGO FP
         WHERE  FPP.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    FPP.COD_LOCAL =cCodLocal_in
         AND    FPP.NUM_PED_VTA IN (SELECT NUM_PED_VTA
                                    FROM   VTA_PEDIDO_VTA_CAB CAB
                                    WHERE  CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND    CAB.COD_LOCAL = cCodLocal_in
                                    AND    CAB.SEC_MOV_CAJA IN (SELECT C.SEC_MOV_CAJA_ORIGEN
                                                                FROM   CE_MOV_CAJA C
                                                                WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
                                                                AND    C.COD_LOCAL = cCodLocal_in
                                                                AND    C.SEC_MOV_CAJA = cSecMovCaja_in)
                                    AND    CAB.EST_PED_VTA = 'C')
         AND    (FP.IND_FORMA_PAGO_AUTOMATICO_CE = 'S' AND  FP.IND_TARJ = INDICADOR_SI)--tipo tarjeta
         AND    FPP.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
         AND    FPP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
         GROUP BY  FP.COD_FORMA_PAGO,FPP.COD_LOTE;

    vSecFPago varchar2(200);
    indNewCobro VARCHAR2(500); --ASOSA, 16.07.2010
   BEGIN
     FOR v_rCurFormaPago IN curFormaPago
     LOOP
          vSecFPago:=CE_GRABA_FORMA_PAGO_ENTREGA(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                      v_rCurFormaPago.CODIGO, v_rCurFormaPago.CANTIDAD, TIP_MONEDA_SOLES,
                                      TO_NUMBER(v_rCurFormaPago.MONTO,'999,990.00'),
                                      TO_NUMBER(v_rCurFormaPago.MONTO,'999,990.00'),
                                      cUsuCrea_in,'');
     END LOOP;

     SELECT nvl(a.llave_tab_gral,'N')    --INI - ASOSA, 16.07.2010
     INTO indNewCobro
     FROM pbl_tab_gral a
     WHERE a.id_tab_gral='332';   --FIN - ASOSA, 16.07.2010

     --tipo tarjeta
      FOR v_rCurFormaPagoTarj IN curFormaPagoTarj
     LOOP
         IF indNewCobro='S' THEN --ASOSA, 16.07.2010
          vSecFPago:=CE_GRABA_FORMA_PAGO_ENTREGA(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                      v_rCurFormaPagoTarj.CODIGO, v_rCurFormaPagoTarj.CANTIDAD, TIP_MONEDA_SOLES,
                                      TO_NUMBER(v_rCurFormaPagoTarj.MONTO,'999,990.00'),
                                      TO_NUMBER(v_rCurFormaPagoTarj.MONTO,'999,990.00'),
                                      cUsuCrea_in,v_rCurFormaPagoTarj.Cod_Lote);
         END IF;
     END LOOP;
  END;

  /****************************************************************************/

  PROCEDURE CE_ASIGNA_VB_CONTABLE(cCodGrupoCia_in     IN CHAR,
                                  cCodLocal_in        IN CHAR,
                                  cCierreDia_in       IN CHAR,
                                  cUsuModCierreDia_in IN CHAR)
  IS
  BEGIN
        UPDATE CE_CIERRE_DIA_VENTA C SET C.USU_MOD_CIERRE_DIA = Cusumodcierredia_In, C.FEC_MOD_CIERRE_DIA = SYSDATE,
                                         C.IND_VB_CONTABLE = INDICADOR_SI, C.FEC_VB_CONTABLE = SYSDATE
        WHERE  C.COD_GRUPO_CIA = CCODGRUPOCIA_IN
        AND    C.COD_LOCAL = CCODLOCAL_IN
   		  AND	   C.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
  END;

  /****************************************************************************/

  FUNCTION CE_OBTIENE_VB_QF(cCodGrupoCia_in     IN CHAR,
                            cCodLocal_in        IN CHAR,
                            cCierreDia_in       IN CHAR)
  RETURN CHAR
  IS
    v_indVB CHAR(1);
  BEGIN
       SELECT C.IND_VB_CIERRE_DIA INTO V_INDVB
       FROM   CE_CIERRE_DIA_VENTA C
       WHERE  C.COD_GRUPO_CIA = CCODGRUPOCIA_IN
       AND    C.COD_LOCAL = CCODLOCAL_IN
  		 AND	  C.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
    RETURN V_INDVB ;
  END;

  /****************************************************************************/

  PROCEDURE CE_ELIMINA_VB_CONTABLE(cCodGrupoCia_in     IN CHAR,
                                   cCodLocal_in        IN CHAR,
                                   cCierreDia_in       IN CHAR)
  IS

  BEGIN
       UPDATE CE_CIERRE_DIA_VENTA C
       SET C.IND_VB_CONTABLE = INDICADOR_NO,
           C.IND_ENVIO_LOCAL = INDICADOR_NO
       WHERE  C.COD_GRUPO_CIA  = CCODGRUPOCIA_IN
       AND    C.COD_LOCAL = CCODLOCAL_IN
       AND    C.FEC_CIERRE_DIA_VTA = cCierreDia_in;

       EXECUTE IMMEDIATE
       ' UPDATE CE_CIERRE_DIA_VENTA@XE_'||CCODLOCAL_IN||
       ' SET    IND_VB_CONTABLE = ''' || INDICADOR_NO ||''',' ||
       '        IND_ENVIO_LOCAL = ''' || INDICADOR_NO ||'''' ||
       ' WHERE  COD_GRUPO_CIA  = ' || CCODGRUPOCIA_IN ||
       ' AND    COD_LOCAL = ' || CCODLOCAL_IN ||
       ' AND    FEC_CIERRE_DIA_VTA =  TO_DATE('||''''|| CCIERREDIA_IN ||''''||',' ||'''dd/MM/yyyy'')';
  END;

    /****************************************************************************/
  PROCEDURE CE_ACTUALIZA_VB_LOCALES(cCodGrupoCia_in IN CHAR)
  IS
    CURSOR curPendiEnvio IS -- pendiendtes de actualizar
    SELECT C.COD_LOCAL,
           C.FEC_CIERRE_DIA_VTA,
           C.IND_VB_CONTABLE,
           C.FEC_VB_CONTABLE
    FROM   CE_CIERRE_DIA_VENTA C
    WHERE  C.COD_GRUPO_CIA = CCODGRUPOCIA_IN
    AND    C.IND_VB_CONTABLE = INDICADOR_SI
    AND    C.IND_ENVIO_LOCAL = INDICADOR_NO
    ORDER BY C.COD_LOCAL;
    v_rPendiEnvio curPendiEnvio%ROWTYPE;
   BEGIN
     FOR v_rPendiEnvio IN curPendiEnvio
     LOOP
          BEGIN
            EXIT WHEN curPendiEnvio%NOTFOUND;
            EXECUTE IMMEDIATE
            ' UPDATE CE_CIERRE_DIA_VENTA@XE_'||V_RPENDiEnvio.COD_LOCAL ||
            ' SET IND_VB_CONTABLE = :1 , ' ||
            '     FEC_VB_CONTABLE = :2 ' || --'' || V_RPENDiEnvio.IND_VB_CONTABLE || '''' ||
            ' WHERE FEC_CIERRE_DIA_VTA = :3' || --'' || V_RPENDiEnvio.FEC_CIERRE_DIA_VTA || '''' ||
            ' AND   COD_GRUPO_CIA = :4'  USING V_RPENDiEnvio.IND_VB_CONTABLE,V_RPENDiEnvio.FEC_VB_CONTABLE ,V_RPENDiEnvio.FEC_CIERRE_DIA_VTA,CCODGRUPOCIA_IN; --|| CCODGRUPOCIA_IN ;
            CE_ACTUALIZA_IND_ENVIO(CCODGRUPOCIA_IN,V_RPENDiEnvio.FEC_CIERRE_DIA_VTA,V_RPENDiEnvio.COD_LOCAL);
            COMMIT;
           EXCEPTION
              WHEN OTHERS THEN
              ROLLBACK;
              dbms_output.put_line(SQLERRM);
         end;
    END LOOP;
  END;

  /****************************************************************************/
  PROCEDURE CE_INSERTA_TMP_VB_LOCALES(cCodGrupoCia_in IN CHAR)
  IS
    CURSOR curPendiEnvio IS -- pendiendtes de actualizar
    SELECT C.COD_LOCAL,
           C.FEC_CIERRE_DIA_VTA,
           C.IND_VB_CONTABLE,
           C.FEC_VB_CONTABLE
    FROM   CE_CIERRE_DIA_VENTA C
    WHERE  C.COD_GRUPO_CIA = CCODGRUPOCIA_IN
    AND    C.IND_VB_CONTABLE = INDICADOR_SI
    AND    C.IND_ENVIO_LOCAL = INDICADOR_NO
    AND    C.FEC_PROCESO  IS NULL
    AND    C.FEC_ARCHIVO IS NULL
    ORDER BY C.COD_LOCAL, TO_CHAR(C.FEC_CIERRE_DIA_VTA,'yyyy/MM/dd');
    v_rPendiEnvio curPendiEnvio%ROWTYPE;
   BEGIN
     FOR v_rPendiEnvio IN curPendiEnvio
     LOOP
          BEGIN
            EXIT WHEN curPendiEnvio%NOTFOUND;
            INSERT INTO TMP_CE_CIERRE_DIA_VENTA (COD_GRUPO_CIA, COD_LOCAL,FEC_CIERRE_DIA_VTA,IND_VB_CIERRE_DIA,FEC_VB_CIERRE_DIA,
                                                 DESC_OBSV_CIERRE_DIA,TIP_CAMBIO_CIERRE_DIA,USU_CREA_CIERRE_DIA,
                                                 FEC_CREA_CIERRE_DIA,USU_MOD_CIERRE_DIA,FEC_MOD_CIERRE_DIA,
                                                 SEC_USU_LOCAL_CREA,SEC_USU_LOCAL_VB,NUM_BOLETA_INICIAL,
                                                 NUM_BOLETA_FINAL,NUM_FACTURA_INICIAL,NUM_FACTURA_FINAL,
                                                 IND_COMP_VALIDOS,FEC_PROCESO,IND_VB_CONTABLE,IND_ENVIO_LOCAL,
                                                 FEC_ARCHIVO,FEC_VB_CONTABLE)
             SELECT COD_GRUPO_CIA,COD_LOCAL,FEC_CIERRE_DIA_VTA,IND_VB_CIERRE_DIA,FEC_VB_CIERRE_DIA,
                    DESC_OBSV_CIERRE_DIA,TIP_CAMBIO_CIERRE_DIA,USU_CREA_CIERRE_DIA,
                    FEC_CREA_CIERRE_DIA,USU_MOD_CIERRE_DIA,FEC_MOD_CIERRE_DIA,
                    SEC_USU_LOCAL_CREA,SEC_USU_LOCAL_VB,NUM_BOLETA_INICIAL,
                    NUM_BOLETA_FINAL,NUM_FACTURA_INICIAL,NUM_FACTURA_FINAL,
                    IND_COMP_VALIDOS,FEC_PROCESO,IND_VB_CONTABLE,IND_ENVIO_LOCAL,
                    FEC_ARCHIVO,FEC_VB_CONTABLE
             FROM   CE_CIERRE_DIA_VENTA
             WHERE  CE_CIERRE_DIA_VENTA.COD_GRUPO_CIA = cCodGrupoCia_in
             AND    CE_CIERRE_DIA_VENTA.COD_LOCAL = v_rPendiEnvio.COD_LOCAL
             AND    CE_CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = v_rPendiEnvio.FEC_CIERRE_DIA_VTA
             AND    CE_CIERRE_DIA_VENTA.IND_VB_CONTABLE = INDICADOR_SI
             AND    CE_CIERRE_DIA_VENTA.IND_ENVIO_LOCAL = INDICADOR_NO
             AND    CE_CIERRE_DIA_VENTA.FEC_PROCESO IS NULL
             AND    CE_CIERRE_DIA_VENTA.FEC_ARCHIVO IS NULL;
             CE_ACTUALIZA_IND_ENVIO(CCODGRUPOCIA_IN,V_RPENDiEnvio.FEC_CIERRE_DIA_VTA,V_RPENDiEnvio.COD_LOCAL);
             COMMIT;
           EXCEPTION
              WHEN OTHERS THEN
              ROLLBACK;
              dbms_output.put_line(SQLERRM);
         end;
    END LOOP;
   END;

  /****************************************************************************/
  PROCEDURE CE_ENVIA_VB_LOCALES (cCodGrupoCia_in IN CHAR)
  IS
    CURSOR curEnvioLocales IS -- pendiendtes de actualizar
    SELECT C.COD_GRUPO_CIA,
           C.COD_LOCAL,
           C.FEC_CIERRE_DIA_VTA
    FROM   TMP_CE_CIERRE_DIA_VENTA C
    WHERE  C.COD_GRUPO_CIA = CCODGRUPOCIA_IN
    AND    C.FEC_PROCESO_ENVIO IS NULL
    ORDER BY C.COD_LOCAL, TO_CHAR(C.FEC_CIERRE_DIA_VTA,'yyyy/MM/dd');
    v_rCurEnvioLocales curEnvioLocales%ROWTYPE;

    BEGIN
     FOR v_rCurEnvioLocales IN curEnvioLocales
     LOOP
          BEGIN
            EXIT WHEN curEnvioLocales%NOTFOUND;
            EXECUTE IMMEDIATE
                    ' INSERT INTO TMP_CE_CIERRE_DIA_VENTA@XE_'||v_rCurEnvioLocales.COD_LOCAL ||
                    ' (COD_GRUPO_CIA, COD_LOCAL,FEC_CIERRE_DIA_VTA,IND_VB_CIERRE_DIA,FEC_VB_CIERRE_DIA, ' ||
                    ' DESC_OBSV_CIERRE_DIA,TIP_CAMBIO_CIERRE_DIA,USU_CREA_CIERRE_DIA,' ||
                    ' FEC_CREA_CIERRE_DIA,USU_MOD_CIERRE_DIA,FEC_MOD_CIERRE_DIA,' ||
                    ' SEC_USU_LOCAL_CREA,SEC_USU_LOCAL_VB,NUM_BOLETA_INICIAL,' ||
                    ' NUM_BOLETA_FINAL,NUM_FACTURA_INICIAL,NUM_FACTURA_FINAL,' ||
                    ' IND_COMP_VALIDOS,FEC_PROCESO,IND_VB_CONTABLE,IND_ENVIO_LOCAL,' ||
                    ' FEC_ARCHIVO,FEC_VB_CONTABLE)' ||
                    ' SELECT COD_GRUPO_CIA, COD_LOCAL,FEC_CIERRE_DIA_VTA,IND_VB_CIERRE_DIA,FEC_VB_CIERRE_DIA,' ||
                    '        DESC_OBSV_CIERRE_DIA,TIP_CAMBIO_CIERRE_DIA,USU_CREA_CIERRE_DIA,' ||
                    '        FEC_CREA_CIERRE_DIA,USU_MOD_CIERRE_DIA,FEC_MOD_CIERRE_DIA,' ||
                    '        SEC_USU_LOCAL_CREA,SEC_USU_LOCAL_VB,NUM_BOLETA_INICIAL,' ||
                    '        NUM_BOLETA_FINAL,NUM_FACTURA_INICIAL,NUM_FACTURA_FINAL,' ||
                    '        IND_COMP_VALIDOS,FEC_PROCESO,IND_VB_CONTABLE,IND_ENVIO_LOCAL,' ||
                    '        FEC_ARCHIVO,FEC_VB_CONTABLE' ||
                    ' FROM   TMP_CE_CIERRE_DIA_VENTA ' ||
                    ' WHERE  TMP_CE_CIERRE_DIA_VENTA.COD_GRUPO_CIA = :1 ' ||
                    ' AND    TMP_CE_CIERRE_DIA_VENTA.COD_LOCAL = :2 ' ||
                    ' AND    TMP_CE_CIERRE_DIA_VENTA.FEC_CIERRE_DIA_VTA = :3 ' ||
                    ' AND TMP_CE_CIERRE_DIA_VENTA.FEC_PROCESO_ENVIO IS NULL '
                     USING cCodGrupoCia_in,v_rCurEnvioLocales.COD_LOCAL,v_rCurEnvioLocales.FEC_CIERRE_DIA_VTA ;

             CE_ACTUALIZA_FEC_PROCESO_ENV(cCodGrupoCia_in,v_rCurEnvioLocales.FEC_CIERRE_DIA_VTA,v_rCurEnvioLocales.COD_LOCAL);
             COMMIT;
           EXCEPTION
              WHEN OTHERS THEN
              ROLLBACK;
              dbms_output.put_line(SQLERRM);
         end;
    END LOOP;
   END;
  /****************************************************************************/

  PROCEDURE CE_EJECUTA_VB_CONTABLE(cCodGrupoCia_in IN CHAR)
  IS
    BEGIN
     CE_INSERTA_TMP_VB_LOCALES(cCodGrupoCia_in);
     CE_ENVIA_VB_LOCALES (cCodGrupoCia_in);
    END;

  /****************************************************************************/

  PROCEDURE CE_UPLOAD_VB_LOCALES(cCodGrupoCia_in IN CHAR)
  IS
    CURSOR curVBLocales IS -- pendiendtes de actualizar
    SELECT C.COD_GRUPO_CIA,
           C.COD_LOCAL,
           C.FEC_CIERRE_DIA_VTA,
           C.IND_VB_CONTABLE,
           C.FEC_VB_CONTABLE,
           C.USU_MOD_CIERRE_DIA
    FROM   TMP_CE_CIERRE_DIA_VENTA C
    WHERE  C.COD_GRUPO_CIA = CCODGRUPOCIA_IN
    AND    C.FEC_PROCESO_ENVIO IS NULL
    ORDER BY C.COD_LOCAL, TO_CHAR(C.FEC_CIERRE_DIA_VTA,'yyyy/MM/dd');
    v_rCurVBLocales curVBLocales%ROWTYPE;

    BEGIN
     FOR v_rCurVBLocales IN curVBLocales
     LOOP
          BEGIN
            EXIT WHEN curVBLocales%NOTFOUND;
            UPDATE CE_CIERRE_DIA_VENTA C
            SET    C.FEC_MOD_CIERRE_DIA = SYSDATE,
                   C.USU_MOD_CIERRE_DIA = v_rCurVBLocales.USU_MOD_CIERRE_DIA,
                   C.IND_VB_CONTABLE = v_rCurVBLocales.IND_VB_CONTABLE,
                   C.FEC_VB_CONTABLE = v_rCurVBLocales.FEC_VB_CONTABLE
            WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    C.COD_LOCAL  = v_rCurVBLocales.COD_LOCAL
            AND    C.FEC_CIERRE_DIA_VTA = v_rCurVBLocales.FEC_CIERRE_DIA_VTA;
            CE_ACTUALIZA_FEC_PROCESO_ENV(cCodGrupoCia_in,v_rCurVBLocales.FEC_CIERRE_DIA_VTA,v_rCurVBLocales.COD_LOCAL);
            COMMIT;
           EXCEPTION
              WHEN OTHERS THEN
              ROLLBACK;
              dbms_output.put_line(SQLERRM);
         end;
    END LOOP;
   END;

  /****************************************************************************/
  PROCEDURE CE_ACTUALIZA_IND_ENVIO(cCodGrupoCia_in IN CHAR,
                                   cCierreDia_in   IN CHAR,
                                   cCodLocal_in IN CHAR)
  IS
  BEGIN
       UPDATE ce_cierre_dia_venta c
       SET    C.IND_ENVIO_LOCAL = INDICADOR_SI,
              c.fec_envio_local = SYSDATE
       WHERE  c.cod_grupo_cia = ccodgrupocia_in
       AND    c.fec_cierre_dia_vta = ccierredia_in
       AND    c.cod_local = cCodLocal_in;
  END;

  /****************************************************************************/
  PROCEDURE CE_ACTUALIZA_FEC_PROCESO_ENV(cCodGrupoCia_in IN CHAR,
                                         cCierreDia_in   IN CHAR,
                                         cCodLocal_in IN CHAR)
  IS
  BEGIN
       UPDATE TMP_CE_CIERRE_DIA_VENTA T
       SET    T.FEC_PROCESO_ENVIO = SYSDATE,
              T.IND_ENVIO_LOCAL = 'S'
       WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    T.COD_LOCAL = cCodLocal_in
       AND    T.FEC_CIERRE_DIA_VTA = cCierreDia_in;
  END;

  /****************************************************************************/


  FUNCTION CE_OBT_CANT_RECLAMOS_NAVSAT(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cCierreDia_in   IN CHAR)
    RETURN NUMBER
  IS
    v_nCant NUMBER;
  BEGIN
       SELECT COUNT(1)
       INTO   v_nCant
       FROM   CE_MOV_CAJA MOV,
              CE_CIERRE_DIA_VENTA CDIA,
              VTA_COMP_PAGO COMP
       WHERE  CDIA.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    CDIA.COD_LOCAL = cCodLocal_in
       AND    CDIA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
       AND    MOV.TIP_MOV_CAJA = TIP_MOV_APERTURA
       AND    COMP.IND_RECLAMO_NAVSAT = INDICADOR_SI
       AND    CDIA.COD_GRUPO_CIA = MOV.COD_GRUPO_CIA
       AND    CDIA.COD_LOCAL = MOV.COD_LOCAL
       AND    CDIA.FEC_CIERRE_DIA_VTA = MOV.FEC_DIA_VTA
       AND    MOV.COD_GRUPO_CIA = COMP.COD_GRUPO_CIA
       AND    MOV.COD_LOCAL = COMP.COD_LOCAL
       AND    MOV.SEC_MOV_CAJA = COMP.SEC_MOV_CAJA;
    RETURN v_nCant;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_COMP_RECLAMOS_NAVSAT(cCodGrupoCia_in IN CHAR,
  		   						                     cCodLocal_in	   IN CHAR,
                                         cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  IS
    curCe FarmaCursor;
  BEGIN
       OPEN curce FOR
            SELECT COMP.NUM_PED_VTA || 'Ã' ||
                   DECODE(COMP.TIP_COMP_PAGO,COD_TIP_COMP_BOLETA,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA','05','TICKET') || 'Ã' ||
                   FARMA_UTILITY.GET_T_COMPROBANTE(COMP.COD_TIP_PROC_PAGO,COMP.NUM_COMP_PAGO_E,COMP.NUM_COMP_PAGO)
                   --COMP.NUM_COMP_PAGO
                   || 'Ã' ||
                   TO_CHAR(COMP.FEC_CREA_COMP_PAGO,'dd/MM/yyyy HH24:mi:ss') || 'Ã' ||
                   TO_CHAR(COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO,'999,990.00') || 'Ã' ||
                   'NAVSAT' || 'Ã' ||
                   NVL(COMP.SEC_COMP_PAGO,' ') || 'Ã' ||
                   TO_CHAR(COMP.FEC_CREA_COMP_PAGO,'yyyyMMdd HH24MISS')
            FROM   CE_MOV_CAJA MOV,
                   CE_CIERRE_DIA_VENTA CDIA,
                   VTA_COMP_PAGO COMP
            WHERE  CDIA.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    CDIA.COD_LOCAL = cCodLocal_in
            AND    CDIA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
            AND    MOV.TIP_MOV_CAJA = TIP_MOV_APERTURA
            AND    COMP.IND_RECLAMO_NAVSAT = INDICADOR_SI
            AND    CDIA.COD_GRUPO_CIA = MOV.COD_GRUPO_CIA
            AND    CDIA.COD_LOCAL = MOV.COD_LOCAL
            AND    CDIA.FEC_CIERRE_DIA_VTA = MOV.FEC_DIA_VTA
            AND    MOV.COD_GRUPO_CIA = COMP.COD_GRUPO_CIA
            AND    MOV.COD_LOCAL = COMP.COD_LOCAL
            AND    MOV.SEC_MOV_CAJA = COMP.SEC_MOV_CAJA;
    RETURN curce;
  END;

  /**************************************************************************************************/

  FUNCTION CE_LISTA_DET_RECLAMOS_NAVSAT(cCodGrupoCia_in IN CHAR,
  		   						                    cCod_Local_in   IN CHAR,
								                        cSecCompPago_in IN CHAR)
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
            AND    DET.SEC_COMP_PAGO = cSecCompPago_in
            AND    P.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
            AND    P.COD_PROD = DET.COD_PROD
            AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA
            AND    DET.COD_PROD = VIR.COD_PROD;
    RETURN curDet;
  END;

/**********************************************/

  FUNCTION CE_OBTIENE_FORMAS_PAGO(cCodGrupoCia_in  IN CHAR,
  		   						               cCodLocal_in    	IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    vIndActFondo CHAR(1) := 'N';
  BEGIN
   BEGIN
    SELECT LLAVE_TAB_GRAL INTO vIndActFondo FROM PBL_TAB_GRAL
     WHERE ID_TAB_GRAL = 341;
   EXCEPTION
    WHEN no_data_found THEN
     vIndActFondo := 'N';
   END;
   IF(vIndActFondo = 'N') THEN
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
			AND	   FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
			AND	   FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
			AND	   FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
			AND	   FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      order by 1 desc;

    ELSE
      OPEN curCaj FOR
		 	SELECT FORMA_PAGO.COD_FORMA_PAGO         || 'Ã' ||
				     FORMA_PAGO.DESC_CORTA_FORMA_PAGO  || 'Ã' ||
				     NVL(FORMA_PAGO.COD_OPE_TARJ,' ')  || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')      || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N') || 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
			FROM   VTA_FORMA_PAGO FORMA_PAGO,
				     VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
			WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
			AND	   FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
			AND	   FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
			AND	   FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
			AND	   FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
UNION
      SELECT       FORMA_PAGO.COD_FORMA_PAGO    || 'Ã' ||
				     FORMA_PAGO.DESC_CORTA_FORMA_PAGO  || 'Ã' ||
				     NVL(FORMA_PAGO.COD_OPE_TARJ,' ')  || 'Ã' ||
             NVL(FORMA_PAGO.IND_TARJ,'N')      || 'Ã' ||
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N') || 'Ã' ||
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ')
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
				     VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
			WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
			AND	   FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
--			AND	   FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = 'A'              --ESTADO INACTIVO DE FONDO SENCILLO
			AND	   FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
			AND	   FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      AND    FORMA_PAGO.COD_FORMA_PAGO = '00060';
    END IF;

      RETURN curCaj;
  END CE_OBTIENE_FORMAS_PAGO;



  /*************************************************************************************/

    FUNCTION CE_OBTIENE_RESUMEN_VIRTUALES(cCodGrupoCia_in  IN CHAR,
  		   						                    cCodLocal_in   IN CHAR,
                                        cFecha_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
        SELECT DECODE(A.TIP_PROD_VIRTUAL,'T','TARJETA VIRTUAL','RECARGA VIRTUAL')|| 'Ã' ||
                TO_CHAR(SUM(c.cant_atendida),'999,990.00')|| 'Ã' ||
                TO_CHAR(SUM(c.val_prec_total),'999,990.00')
        FROM   VTA_PEDIDO_VTA_CAB B,
               LGT_PROD_VIRTUAL A,
               VTA_PEDIDO_VTA_DET C
        WHERE  B.COD_GRUPO_CIA=cCodGrupoCia_in
        AND    B.COD_LOCAL=cCodLocal_in
        AND    B.EST_PED_VTA=EST_PED_COBRADO
        --AND    B.IND_PEDIDO_ANUL=INDICADOR_NO
        AND    b.fec_ped_vta BETWEEN TO_DATE(cFecha_in||' 00:00:00','dd/MM/yyyy HH24:mi:ss')
        AND    TO_DATE(cFecha_in||' 23:59:59','dd/MM/yyyy HH24:mi:ss')
        AND    B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND    B.COD_LOCAL = C.COD_LOCAL
        AND    B.NUM_PED_VTA = C.NUM_PED_VTA
        AND    C.COD_GRUPO_CIA = A.COD_GRUPO_CIA
        AND    C.COD_PROD = A.COD_PROD
        GROUP BY DECODE(A.TIP_PROD_VIRTUAL,'T','TARJETA VIRTUAL','RECARGA VIRTUAL');

            RETURN curCaj;
  END CE_OBTIENE_RESUMEN_VIRTUALES;

  /****************************************************************************/
  PROCEDURE CE_AGREGA_COTIZ_COMP_AUTO(cCodGrupoCia_in	IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cFechaCierreDia_in IN CHAR,
                                      cUsuCrea_in IN CHAR)
  IS
    CURSOR curCotComp IS

    SELECT c.cod_grupo_cia COD_GRUPO_CIA,
           c.cod_local COD_LOCAL,
           to_char(c.fec_crea_nota_es_cab,'dd/MM/yyyy') FECHA_CREA,
           C_C_COTIZA_COMP COTIZACION,
           c.val_total_nota_es_cab TOTAL,
           c.num_nota_es NUM_NOTA,
           nvl(c.desc_empresa,' ') GLOSA
    FROM   lgt_nota_es_cab c
    WHERE  c.cod_grupo_cia = cCodGrupoCia_in
    AND    c.cod_local = cCodLocal_in
    AND    c.tip_origen_nota_es = C_C_TIPO_GUIA_COMPETENCIA
    AND    c.tip_nota_es = C_C_TIPO_INGRESO
    AND    c.fec_proceso_ce IS NULL
    AND    C.EST_NOTA_ES_CAB = ESTADO_ACTIVO
    AND    to_char(c.fec_crea_nota_es_cab,'dd/MM/yyyy') = cFechaCierreDia_in
    AND    (c.num_nota_es,
           to_char(c.val_total_nota_es_cab,'999,990.00'))
    NOT IN  (SELECT D.Num_Nota_Es,
                   to_char(d.mon_moneda_origen,'999,990.00')
            FROM   CE_CUADRATURA_CIERRE_DIA D
            WHERE  d.cod_grupo_cia = cCodGrupoCia_in
            AND    D.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    d.cod_local = cCodLocal_in
            AND    d.cod_cuadratura = C_C_COTIZA_COMP);

    v_rCurCotComp curCotComp%ROWTYPE;

   BEGIN

--   null;

     FOR v_rCurCotComp IN curCotComp
     LOOP
          BEGIN

          CE_AGREGA_COT_COMPETENCIA(v_rCurCotComp.Cod_Grupo_Cia,v_rCurCotComp.Cod_Local,v_rCurCotComp.Fecha_Crea,
                                    v_rCurCotComp.Cotizacion,v_rCurCotComp.Total,v_rCurCotComp.Num_Nota,cUsuCrea_in,
                                    v_rCurCotComp.Glosa);
          CE_ACTUALIZA_GUIA_COT_COMP(v_rCurCotComp.Cod_Grupo_Cia,v_rCurCotComp.Cod_Local,v_rCurCotComp.Num_Nota,
                                     v_rCurCotComp.Fecha_Crea,cUsuCrea_in);

          END;
    END LOOP;

  END;
   /****************************************************************************/

  FUNCTION CE_F_NUM_MONTO_SOBRES(cCodGrupoCia_in  IN CHAR,
  		   						             cCodLocal_in   IN CHAR,
                                 cFecha_in IN CHAR)
  RETURN NUMBER
  IS
    nMonto number := 0;
  BEGIN
    SELECT nvl(sum(CF.MON_ENTREGA_TOTAL),0)
    into   nMonto
    FROM   CE_SOBRE S,
           CE_FORMA_PAGO_ENTREGA CF
    WHERE  CF.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CF.COD_LOCAL     = cCodLocal_in
    AND    CF.EST_FORMA_PAGO_ENT = 'A'
    AND    S.FEC_DIA_VTA    = to_date(cFecha_in,'dd/MM/yyyy')
    AND    S.COD_GRUPO_CIA  = CF.COD_GRUPO_CIA
    AND    S.COD_LOCAL      = CF.COD_LOCAL
    AND    S.SEC_MOV_CAJA   = CF.SEC_MOV_CAJA
    AND    S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA;

    RETURN nMonto;

  END CE_F_NUM_MONTO_SOBRES;

/****************************************************************************/
  FUNCTION CE_EXIST_GUIAS_PEND(cCodGrupoCia_in     IN CHAR,
                               cCodLocal_in        IN CHAR)
  RETURN CHAR
  IS
    CANT NUMBER;
    IND CHAR(1):='N';
  BEGIN
      SELECT COUNT(*) INTO CANT
      FROM LGT_NOTA_ES_CAB X
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
        AND X.COD_LOCAL=cCodLocal_in
        AND X.EST_NOTA_ES_CAB IN ('P')--Por confirmar
        AND X.TIP_DOC IN ('03')
        --AND X.TIP_ORIGEN_NOTA_ES IN ('02')--MATIRZ
        AND X.COD_DESTINO_NOTA_ES IN ('009')--hacia almacen
        AND X.IND_NOTA_IMPRESA IN ('S');--solo impresas

        IF(CANT>0) THEN
         IND:='S';
        END IF;

    RETURN IND ;
  END;

/****************************************************************************/
  --JMIRANDA 14.12.09
  FUNCTION CE_EXIST_GUIAS_PEND_LOCAL(cCodGrupoCia_in     IN CHAR,
                               cCodLocal_in        IN CHAR)
  RETURN CHAR
  IS
    CANT NUMBER;
    IND CHAR(1):='N';
  BEGIN

  	SELECT COUNT(*) INTO CANT
      FROM T_LGT_NOTA_ES_CAB C, T_LGT_GUIA_REM g
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
	   AND C.FEC_NOTA_ES_CAB > TO_DATE('31/12/2009 23:59:59','DD/MM/YYYY HH24:MI:SS')
           AND C.COD_DESTINO_NOTA_ES = cCodLocal_in
           AND C.EST_NOTA_ES_CAB = 'L'
           AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
           AND C.COD_LOCAL = G.COD_LOCAL
           AND C.NUM_NOTA_ES = G.NUM_NOTA_ES;

        IF(CANT>0) THEN
         IND:='S';
        END IF;

    RETURN IND ;
  END;

  /*********************************************************************************************************************************/
  FUNCTION CE_REGISTRO_VENTAS (cCodGrupoCia_in	IN CHAR,
                               cCodLocal_in	  	IN CHAR,
                               cFechaInicio     IN CHAR,
                               cFechaFin        IN CHAR,
                               cMovCaja         IN CHAR,
                               cNumPedVta_in    IN CHAR DEFAULT '%',
                               nMontoVta_in     IN NUMBER DEFAULT 0,
                               cTipoPago        IN CHAR DEFAULT '%')
 RETURN FarmaCursor
 IS
 curRep FarmaCursor;
 BEGIN
 	OPEN curRep FOR
 	SELECT VPC.num_ped_vta || 'Ã' ||
		DECODE(VPC.tip_comp_pago,01,' ',02,'F',03,'G',04,'N',05,'T') || 'Ã' ||
	  	NVL(--SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7)
      Farma_Utility.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
                                  --FAC-ELECTRONICA :09.10.2014
                                        ,' ') || 'Ã' ||
                TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
	 	NVL(VPC.NOM_CLI_PED_VTA,' ') || 'Ã' ||
		DECODE(VPC.IND_PEDIDO_ANUL,'S','ANUL.ORIG.',' ') || 'Ã' ||
		TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,990.00')|| 'Ã' ||
                TRIM(VPC.NUM_PED_DIARIO)
        FROM  VTA_PEDIDO_VTA_CAB VPC,
              VTA_COMP_PAGO CP,
              VTA_FORMA_PAGO_PEDIDO C
        WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
              AND   VPC.COD_LOCAL = cCodLocal_in
              AND  VPC.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN
                                        FROM CE_MOV_CAJA A
                                        WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                        AND A.COD_LOCAL=cCodLocal_in
                                        AND A.SEC_MOV_CAJA=cMovCaja
                                        AND A.IND_VB_CAJERO='N')--solo movimientos sin VB Cajero
              AND  C.COD_FORMA_PAGO IN (SELECT A.COD_FORMA_PAGO
                                        FROM VTA_FORMA_PAGO A
                                        WHERE A.IND_TARJ LIKE cTipoPago
                                        AND A.EST_FORMA_PAGO='A')
              AND  C.COD_FORMA_PAGO NOT IN (SELECT A.COD_FORMA_PAGO
                                        FROM VTA_FORMA_PAGO A
                                        WHERE A.IND_TARJ='N'
                                        AND A.EST_FORMA_PAGO='A'
                                        AND A.COD_FORMA_PAGO NOT IN ('00001','00002'))
            AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                    AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
            AND   VPC.EST_PED_VTA = 'C'
            AND   vpc.cod_grupo_cia = cp.cod_grupo_cia
            AND   vpc.COD_LOCAL = cp.cod_local
            AND   VPC.NUM_PED_VTA=CP.NUM_PED_VTA
            AND  VPC.COD_GRUPO_CIA=C.COD_GRUPO_CIA
            AND  VPC.COD_LOCAL=C.COD_LOCAL
            AND  VPC.NUM_PED_VTA=C.NUM_PED_VTA
            AND  VPC.TIP_PED_VTA='01'
            --Permite cambiar la forma de pago de la parte que fue pagada con efectivo o con tarjeta, Luigy Terrazos
            --AND  VPC.IND_PED_CONVENIO='N'
            AND  VPC.IND_DELIV_AUTOMATICO='N'
        UNION
        SELECT  VPC.num_ped_vta || 'Ã' ||
                DECODE(VPC.tip_comp_pago,01,' ',02,'F',03,'G',04,'N',05,'T') || 'Ã' ||
                NVL(--SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7)
                Farma_Utility.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
					                                    --FAC-ELECTRONICA :09.10.2014
                                              ,' ') || 'Ã' ||
                TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                NVL(VPC.NOM_CLI_PED_VTA,' ') || 'Ã' ||
                DECODE(VPC.IND_PEDIDO_ANUL,'N','ANULADO',' ')	|| 'Ã' ||
                TO_CHAR((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO) * -1,'999,990.00')|| 'Ã' ||
                TRIM(VPC.NUM_PED_DIARIO)
        FROM  VTA_PEDIDO_VTA_CAB VPC,
            VTA_COMP_PAGO CP,
            VTA_FORMA_PAGO_PEDIDO C
        WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   VPC.COD_LOCAL = cCodLocal_in
                  AND  VPC.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN
                                            FROM CE_MOV_CAJA A
                                            WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                            AND A.COD_LOCAL=cCodLocal_in
                                            AND A.SEC_MOV_CAJA=cMovCaja
                                            AND A.IND_VB_CAJERO='N')--solo movimientos si VB Cajero
                  AND  C.COD_FORMA_PAGO IN (SELECT A.COD_FORMA_PAGO
                                             FROM VTA_FORMA_PAGO A
                                             WHERE A.IND_TARJ LIKE cTipoPago
                                             AND A.EST_FORMA_PAGO='A')
                  AND  C.COD_FORMA_PAGO NOT IN (SELECT A.COD_FORMA_PAGO
                                            FROM VTA_FORMA_PAGO A
                                            WHERE A.IND_TARJ='N'
                                            AND A.EST_FORMA_PAGO='A'
                                            AND A.COD_FORMA_PAGO NOT IN ('00001','00002'))
                AND	  VPC.EST_PED_VTA= 'C'
                AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                        AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
                AND   VPC.NUM_PED_VTA=CP.NUM_PEDIDO_ANUL
                AND	  vpc.cod_grupo_cia = cp.cod_grupo_cia
                AND	  vpc.COD_LOCAL = cp.cod_local
                AND  VPC.COD_GRUPO_CIA=C.COD_GRUPO_CIA
                AND  VPC.COD_LOCAL=C.COD_LOCAL
                AND  VPC.NUM_PED_VTA=C.NUM_PED_VTA
                AND  VPC.TIP_PED_VTA='01'
                AND  VPC.IND_PED_CONVENIO='N'
                AND  VPC.IND_DELIV_AUTOMATICO='N';

 RETURN curRep;
 END;
  /***********************************************************************************************/
  FUNCTION CE_DETALLE_FORMAS_PAGO(cCodGrupoCia_in	IN CHAR,
    		   					              cCodLocal_in	  IN CHAR,
   							                  cNumPedVta      IN CHAR)
  RETURN FarmaCursor
  IS
  curlist FarmaCursor;
  BEGIN
 	  OPEN curlist FOR
	  	   SELECT A.COD_FORMA_PAGO  || 'Ã' ||
         b.desc_forma_pago  || 'Ã' ||
         TO_CHAR(A.im_pago,'999,990.000') || 'Ã' ||
         to_char(case when a.tip_moneda='01' then 'SOLES' when tip_moneda='02' then 'DOLARES' end)  || 'Ã' ||
         TO_CHAR(A.val_vuelto,'999,990.000') || 'Ã' ||
         TO_CHAR(A.im_total_pago,'999,990.000') || 'Ã' ||
         A.usu_crea_forma_pago_ped|| 'Ã' ||
         TO_CHAR(A.VAL_TIP_CAMBIO,'999,990.000')|| 'Ã' ||
         B.IND_TARJ
         FROM vta_forma_pago_pedido A,
              vta_forma_pago b
         WHERE a.cod_forma_pago=b.cod_forma_pago
           AND     a.COD_GRUPO_CIA = cCodGrupoCia_in
	         AND	   a.cod_local = cCodLocal_in
	         AND	   a.num_ped_vta=cNumPedVta;

	   RETURN curlist;

   END;

/* ************************************************************************ */

 PROCEDURE CE_FORMA_PAGO_PEDIDO_BK(cCodGrupoCia_in 	 	     IN CHAR,
                                  cCodLocal_in    	 	     IN CHAR,
                                  cCodFormaPago_in   	     IN CHAR,
                                  cNumPedVta_in   	 	     IN CHAR,
                                  cUsuCreaFormaPagoPed_in  IN CHAR)
  IS
  BEGIN

     INSERT INTO VTA_FORMA_PAGO_PEDIDO_BK (COD_GRUPO_CIA,
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
                                            FEC_CREA_FORMA_PAGO_PED,
                                            USU_CREA_FORMA_PAGO_PED,
                                            FEC_MOD_FORMA_PAGO_PED,
                                            USU_MOD_FORMA_PAGO_PED,
                                            CANT_CUPON,
                                            TIPO_AUTORIZACION,
                                            COD_LOTE,
                                            COD_AUTORIZACION,
                                            DNI_CLI_TARJ,
                                            FEC_NEW_CREA,
                                            USU_NEW_CREA)
     SELECT COD_GRUPO_CIA,
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
            FEC_CREA_FORMA_PAGO_PED,
            USU_CREA_FORMA_PAGO_PED,
            FEC_MOD_FORMA_PAGO_PED,
            USU_MOD_FORMA_PAGO_PED,
            CANT_CUPON,
            TIPO_AUTORIZACION,
            COD_LOTE,
            COD_AUTORIZACION,
            DNI_CLI_TARJ,
            SYSDATE,
            cUsuCreaFormaPagoPed_in
          FROM VTA_FORMA_PAGO_PEDIDO  A
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_LOCAL=cCodLocal_in
          AND A.NUM_PED_VTA=cNumPedVta_in
          AND A.COD_FORMA_PAGO=cCodFormaPago_in;

          DELETE FROM VTA_FORMA_PAGO_PEDIDO B
          WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
          AND B.COD_LOCAL=cCodLocal_in
          AND B.NUM_PED_VTA=cNumPedVta_in
          AND B.COD_FORMA_PAGO=cCodFormaPago_in;

 END;

  /***************************************************************************************************************/
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
                                    cUsuCreaFormaPagoPed_in  IN CHAR)
  IS
  BEGIN



    INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA, COD_LOCAL, COD_FORMA_PAGO, NUM_PED_VTA,
	   							  	                IM_PAGO, TIP_MONEDA, VAL_TIP_CAMBIO, VAL_VUELTO,
								  	                  IM_TOTAL_PAGO, NUM_TARJ, FEC_VENC_TARJ, NOM_TARJ,
								  	                  USU_CREA_FORMA_PAGO_PED, CANT_CUPON,COD_AUTORIZACION,COD_LOTE,DNI_CLI_TARJ)
                              VALUES (cCodGrupoCia_in, cCodLocal_in, cCodFormaPago_in, cNumPedVta_in,
									                    nImPago_in, cTipMoneda_in, nValTipCambio_in, nValVuelto_in,
									                    nImTotalPago_in, cNumTarj_in, cFecVencTarj_in, cNomTarj_in,
									                    cUsuCreaFormaPagoPed_in, cCanCupon_in,cCodVou_in,cCodLote_in,cDni_in);
  END;

   /*************************************************************************************************************/

  FUNCTION CE_F_OBTENER_TARJETA(cCodCia_in     IN CHAR,
                                cCodTarj_in    IN VARCHAR,
                                cTipOrigen_in  IN VARCHAR)
  RETURN FarmaCursor
  IS
  cur FarmaCursor;
  BEGIN
       OPEN cur FOR
            SELECT a.bin ||
                   a.desc_prod ||
                   a.cod_forma_pago ||
                   b.desc_corta_forma_pago
            FROM vta_fpago_tarj a
            inner join vta_forma_pago b on a.cod_grupo_cia=b.cod_grupo_cia
            WHERE a.cod_grupo_cia=cCodCia_in
            AND a.cod_forma_pago=b.cod_forma_pago
            AND cCodTarj_in LIKE trim(a.bin)||'%'
            AND TIP_ORIGEN_PAGO = cTipOrigen_in;
       RETURN cur;
   END;

   /*************************************************************************************************************/

     PROCEDURE CE_VAL_MONTO_CIERRE_TURNO (cCodGrupoCia_in 	 	     IN CHAR,
                                          cCodLocal_in    	 	     IN CHAR,
                                          cMovCaja_in              IN CHAR,
                                          cFechaCierre_in          IN CHAR,
                                          cMontoCuadratura_in      IN NUMBER)

   IS

    sMovCajaOrig VTA_PEDIDO_VTA_CAB.SEC_MOV_CAJA%TYPE;
        sMovCajaOrig2 VTA_PEDIDO_VTA_CAB.SEC_MOV_CAJA%TYPE;
    --efectivo
    TotalFormapsE VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
    TotalFormapdE VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
    --tarjeta
    TotalFormapsT VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
    TotalFormapdT VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;

    --Se agrupa por formas de pago existentes (asumiendo nuevo proceso de cobro)
    CURSOR curLote IS
    --SELECT 	DISTINCT TRIM(A.COD_LOTE) AS Cod_Lote
    SELECT 	DISTINCT TRIM(A.COD_FORMA_PAGO) AS COD_FORMA_PAGO
    FROM VTA_FORMA_PAGO_PEDIDO A,
         VTA_PEDIDO_VTA_CAB B,
         VTA_FORMA_PAGO C
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.COD_FORMA_PAGO NOT IN ('00001','00002')
    AND B.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN FROM CE_MOV_CAJA A WHERE A.SEC_MOV_CAJA=cMovCaja_in)
    AND B.FEC_PED_VTA BETWEEN TO_DATE(cFechaCierre_in||' 00:00:00','dd/MM/yyyy HH24:MI:SS')
    AND   				   		   TO_DATE(cFechaCierre_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    AND B.EST_PED_VTA    =  'C'
    AND C.IND_TARJ='S'
    --AND A.COD_LOTE IS NOT NULL
    AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
    AND A.COD_LOCAL=B.COD_LOCAL
    AND A.NUM_PED_VTA=B.NUM_PED_VTA
    AND A.COD_GRUPO_CIA=C.COD_GRUPO_CIA
    AND A.COD_FORMA_PAGO=C.COD_FORMA_PAGO;

     v_rCurLote curLote%ROWTYPE;
     vIndCambioFormaPago VARCHAR2(1):='N';
     vDescFormaPago VARCHAR2(100);
  BEGIN
      --efectivo
    TotalFormapsE:=0;
    TotalFormapdE:=0;
    --tarjeta
    TotalFormapsT:=0;
    TotalFormapdT:=0;

    BEGIN
    --Monto forma pago segun sistema
    SELECT 	nvl(B.SEC_MOV_CAJA,''),nvl(SUM(A.IM_TOTAL_PAGO - A.VAL_VUELTO),0) INTO sMovCajaOrig,TotalFormapsE
    FROM VTA_FORMA_PAGO_PEDIDO A,
         VTA_PEDIDO_VTA_CAB B
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.COD_FORMA_PAGO IN (FORMA_PAGO_EFEC_SOLES,FORMA_PAGO_EFEC_DOLARES)
    AND B.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN FROM CE_MOV_CAJA A WHERE A.SEC_MOV_CAJA=cMovCaja_in)
    AND   B.FEC_PED_VTA BETWEEN TO_DATE(cFechaCierre_in||' 00:00:00','dd/MM/yyyy HH24:MI:SS')
    AND   				   		   TO_DATE(cFechaCierre_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    AND   B.EST_PED_VTA    =  'C'
    --AND   B.IND_PEDIDO_ANUL    = 'N'
    AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
    AND A.COD_LOCAL=B.COD_LOCAL
    AND A.NUM_PED_VTA=B.NUM_PED_VTA
    GROUP BY B.SEC_MOV_CAJA;

   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          sMovCajaOrig := '';--NO SE ENCONTRO EL REGISTRO
                    TotalFormapsE := 0;--NO SE ENCONTRO EL REGISTRO
    END;

    BEGIN
    --Monto forma pago declarado
    SELECT NVL(SUM(A.MON_ENTREGA_TOTAL),0) INTO TotalFormapdE
    FROM CE_FORMA_PAGO_ENTREGA A
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.EST_FORMA_PAGO_ENT='A'
    AND A.COD_LOCAL=cCodLocal_in
    AND A.SEC_MOV_CAJA=cMovCaja_in
    AND A.COD_FORMA_PAGO IN (FORMA_PAGO_EFEC_SOLES,FORMA_PAGO_EFEC_DOLARES);

    EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          TotalFormapdE := 0;--NO SE ENCONTRO EL REGISTRO
    END;

    dbms_output.put_line('TotalFormapdE :'||(TotalFormapdE+cMontoCuadratura_in));
    dbms_output.put_line('TotalFormapsE :'||TotalFormapsE);

    SELECT A.LLAVE_TAB_GRAL INTO vIndCambioFormaPago
    FROM pbl_tab_gral A
    WHERE A.ID_TAB_GRAL=357;

    IF(nvl(TotalFormapdE+cMontoCuadratura_in,0)<nvl(TotalFormapsE,0)) THEN
      IF (vIndCambioFormaPago='S')THEN
       RAISE_APPLICATION_ERROR(-20100,'El MONTO EN EFECTIVO DECLARADO(SOLES/DOLARES) S/. '||TO_CHAR(nvl(TotalFormapdE+cMontoCuadratura_in,0),'999,990.000')||'  :NO ES EL MISMO EN COMPROBANTES EXISTENTES   S/. '||TO_CHAR(nvl(TotalFormapsE,0),'999,990.000')||' ');
      END IF;
    END IF;


    ----------------------------
      FOR v_rCurLote IN curLote
       LOOP

         BEGIN
         EXIT WHEN curLote%NOTFOUND;

                dbms_output.put_line('v_rCurLote.Cod_Lote :'||v_rCurLote.COD_FORMA_PAGO);

          --Monto tarjeta  segun sistema
          SELECT 	nvl(B.SEC_MOV_CAJA,''),nvl(SUM(A.IM_TOTAL_PAGO - A.VAL_VUELTO),0) INTO sMovCajaOrig2,TotalFormapsT
          FROM VTA_FORMA_PAGO_PEDIDO A,
               VTA_PEDIDO_VTA_CAB B,
               VTA_FORMA_PAGO C
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_LOCAL=cCodLocal_in
          AND A.COD_FORMA_PAGO NOT IN (FORMA_PAGO_EFEC_SOLES,FORMA_PAGO_EFEC_DOLARES)
          AND B.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN FROM CE_MOV_CAJA A WHERE A.SEC_MOV_CAJA=cMovCaja_in)
          AND B.FEC_PED_VTA BETWEEN TO_DATE(cFechaCierre_in||' 00:00:00','dd/MM/yyyy HH24:MI:SS')
          AND   				   		   TO_DATE(cFechaCierre_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND B.EST_PED_VTA    =  'C'
          AND C.IND_TARJ='S'
          AND A.COD_FORMA_PAGO=v_rCurLote.COD_FORMA_PAGO
          --AND A.COD_LOTE=TRIM(v_rCurLote.Cod_Lote)
          --AND   B.IND_PEDIDO_ANUL    = 'N'
          AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
          AND A.COD_LOCAL=B.COD_LOCAL
          AND A.NUM_PED_VTA=B.NUM_PED_VTA
          AND A.COD_GRUPO_CIA=C.COD_GRUPO_CIA
          AND A.COD_FORMA_PAGO=C.COD_FORMA_PAGO
          GROUP BY B.SEC_MOV_CAJA;

          --Monto forma pago tarjeta declarado
          SELECT nvl(SUM(A.MON_ENTREGA_TOTAL),0) INTO TotalFormapdT
          FROM CE_FORMA_PAGO_ENTREGA A,
               VTA_FORMA_PAGO B
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.EST_FORMA_PAGO_ENT='A'
          AND A.COD_LOCAL=cCodLocal_in
          AND A.SEC_MOV_CAJA=cMovCaja_in
          --AND TRIM(A.NUM_LOTE)=TRIM(v_rCurLote.Cod_Lote)
          AND A.COD_FORMA_PAGO=v_rCurLote.COD_FORMA_PAGO
          AND B.IND_TARJ='S'
          AND A.COD_FORMA_PAGO NOT IN (FORMA_PAGO_EFEC_SOLES,FORMA_PAGO_EFEC_DOLARES)
          AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
          AND A.COD_FORMA_PAGO=B.COD_FORMA_PAGO;

          dbms_output.put_line('TotalFormapdT :'||TotalFormapdT);
          dbms_output.put_line('TotalFormapsT :'||TotalFormapsT);

          SELECT A.DESC_CORTA_FORMA_PAGO INTO vDescFormaPago
          FROM VTA_FORMA_PAGO A
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_FORMA_PAGO=v_rCurLote.COD_FORMA_PAGO;

          IF(nvl(TotalFormapdT+cMontoCuadratura_in,0)<>nvl(TotalFormapsT,0)) THEN
            IF (vIndCambioFormaPago='S')THEN
             RAISE_APPLICATION_ERROR(-20101,'El MONTO DECLARADO EN TARJETAS (FP : '||vDescFormaPago||') : S/.'||TO_CHAR(nvl(TotalFormapdT+cMontoCuadratura_in,0),'999,990.000') ||'      :NO ES EL MISMO EN COMPROBANTES EXISTENTES : S/. '||TO_CHAR(nvl(TotalFormapsT,0),'999,990.000')||'     ');
             END IF;
          END IF;

         END;

       END
      LOOP;

  END;

  /****************************************************************************/
  FUNCTION CE_VALIDA_CAMBIO_FORM_PAGO

  RETURN CHAR
  IS
    v_cIndCambioFP CHAR(1);
    v_cIndNewCobro char(1);
  BEGIN

     v_cIndNewCobro := PTOVENTA_GRAL.GET_IND_NUEVO_COBRO;

     IF v_cIndNewCobro='S' THEN
      	SELECT  LLAVE_TAB_GRAL INTO v_cIndCambioFP
        FROM pbl_tab_gral a
         WHERE
         --a.id_tab_gral='342' AND
         a.cod_apl='PTO_VENTA'
         AND a.cod_tab_gral = 'CAMBIO_FORMA_PAGO'
         AND a.est_tab_gral='A';
     else
         v_cIndCambioFP := 'N';

     end if;

    RETURN v_cIndCambioFP ;
  END;

    /**************************************************************************************/
   FUNCTION CE_VALIDA_APROBACION(cCodGrupoCia_in	   IN CHAR,
 						                     cCodLocal_in	       IN CHAR,
                                 cCodForma_in        IN CHAR,
                                 cCodSobre_in        IN CHAR,
                                 cFechaVta_in        IN CHAR)
  RETURN VARCHAR2
  IS
   cEstado CHAR(1);
   CANT NUMBER;
   CANT2 NUMBER;
   cIndicador VARCHAR2(1);
  BEGIN

      SELECT COUNT(*) INTO CANT
      FROM CE_SOBRE_TMP  A
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCodLocal_in
      AND A.COD_FORMA_PAGO=cCodForma_in
      AND A.COD_SOBRE=cCodSobre_in
      AND A.FEC_DIA_VTA=cFechaVta_in;

      SELECT COUNT(*) INTO CANT2
      FROM CE_SOBRE  A
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCodLocal_in
      AND A.COD_SOBRE=cCodSobre_in
      AND A.FEC_DIA_VTA=cFechaVta_in;

    IF(CANT>0) THEN

       SELECT A.ESTADO INTO cEstado
       FROM CE_SOBRE_TMP A
       WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
       AND A.COD_LOCAL=cCodLocal_in
       AND A.COD_FORMA_PAGO=cCodForma_in
       AND A.COD_SOBRE=cCodSobre_in;

    ELSIF (CANT2>0)THEN

       SELECT A.ESTADO INTO cEstado
       FROM CE_SOBRE A
       WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
       AND A.COD_LOCAL=cCodLocal_in
       AND A.COD_SOBRE=cCodSobre_in;

    END IF;


       --SELECT TRIM(NVL(A.DESC_CORTA,' ')) INTO cIndicador --valida indicador de nueva funcionalidad
       SELECT TRIM(NVL(A.Llave_Tab_Gral,'N')) INTO cIndicador --ASOSA, 02.06.2010
       FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL=317;

       IF(cIndicador='N')THEN
             cEstado:='X'; --no se validara para que se muestre en color rojo durante el ingreso de formas pago entrega
       END IF;


       RETURN cEstado;
  END;

   /*************************************************************************************************************/
 /*  PROCEDURE CE_VAL_MONTO_CIERRE_TURNO (cCodGrupoCia_in 	 	   IN CHAR,
                                          cCodLocal_in    	 	     IN CHAR,
                                          cFechaCierre_in          IN CHAR,
                                          cMontoCuadratura_in      IN NUMBER)

   IS

   nTotalSSobres CE_SOBRE_TMP.MON_ENTREGA_TOTAL%TYPE;
   nEfectSoles CE_CUADRATURA_CIERRE_DIA.MON_TOTAL%TYPE;
   nTotalDSobres CE_SOBRE_TMP.MON_ENTREGA_TOTAL%TYPE;
   nEfectDolares CE_CUADRATURA_CIERRE_DIA.MON_TOTAL%TYPE;


   nMontoDifeS CE_CUADRATURA_CIERRE_DIA.MON_TOTAL%TYPE;
   nMontoDifeD CE_CUADRATURA_CIERRE_DIA.MON_TOTAL%TYPE;
   cIndicador VARCHAR2(1);

   BEGIN



       SELECT TRIM(NVL(A.DESC_CORTA,' ')) INTO cIndicador --valida indicador de nueva funcionalidad
       FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL=317;

       IF(cIndicador='S') THEN


            --Se obtiene total de sobres soles del dia
            SELECT TO_CHAR(NVL(SUM(C.MON_ENTREGA_TOTAL),0),'999,990.000') INTO nTotalSSobres
                 --TO_CHAR(A.FEC_DIA_VTA,'DD/MM/YYYY')
                 INTO nTotalSoles
            FROM CE_SOBRE A,
               CE_MOV_CAJA B,
               CE_FORMA_PAGO_ENTREGA C,
               CE_CIERRE_DIA_VENTA  D
            WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
            AND A.COD_LOCAL=cCodLocal_in
            AND C.TIP_MONEDA=TIP_MONEDA_SOLES
            AND A.FEC_DIA_VTA=cFechaCierre_in
            --AND D.IND_VB_CIERRE_DIA='S'
            AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
            AND A.COD_LOCAL=B.COD_LOCAL
            AND A.SEC_MOV_CAJA=B.SEC_MOV_CAJA
            AND A.FEC_DIA_VTA=B.FEC_DIA_VTA
            AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
            AND B.COD_LOCAL=C.COD_LOCAL
            AND B.SEC_MOV_CAJA=C.SEC_MOV_CAJA
            AND A.SEC_FORMA_PAGO_ENTREGA=C.SEC_FORMA_PAGO_ENTREGA
            AND B.COD_GRUPO_CIA=D.COD_GRUPO_CIA(+)
            AND B.COD_LOCAL=D.COD_LOCAL(+)
            AND B.FEC_DIA_VTA=D.FEC_CIERRE_DIA_VTA(+)
            GROUP BY TO_CHAR(A.FEC_DIA_VTA,'DD/MM/YYYY');

            --efectivo rendido soles de todas las cuadraturas
            SELECT NVL(SUM(A.MON_TOTAL),0) into nEfectSoles
            FROM CE_CUADRATURA_CIERRE_DIA A
            WHERE  A.COD_GRUPO_CIA=cCodGrupoCia_in
            AND A.COD_LOCAL=cCodLocal_in
            AND A.TIP_MONEDA=TIP_MONEDA_SOLES
            AND A.FEC_CIERRE_DIA_VTA=cFechaCierre_in;

            nEfectSoles:=nEfectSoles-nTotalSSobres;
             IF(nEfectSoles<>0)THEN
                RAISE_APPLICATION_ERROR(-20110,'El MONTO DECLARADO EN SOBRES (soles) : S/.'||TO_CHAR(nvl(nTotalSSobres,0),'999,990.000') ||'         :NO ES EL MISMO DE EFECTIVO RENDIDO : S/. '||TO_CHAR(nvl(nEfectSoles,0),'999,990.000')||'     ');
           END IF;


          --Se obtiene total de sobres dolares del dia
          SELECT TO_CHAR(NVL(SUM(C.MON_ENTREGA_TOTAL),0),'999,990.000') INTO nTotalDSobres
                 --TO_CHAR(A.FEC_DIA_VTA,'DD/MM/YYYY')
                 INTO nTotalSoles
          FROM CE_SOBRE A,
               CE_MOV_CAJA B,
               CE_FORMA_PAGO_ENTREGA C,
               CE_CIERRE_DIA_VENTA  D
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_LOCAL=cCodLocal_in
          AND C.TIP_MONEDA=TIP_MONEDA_DOLARES
          AND A.FEC_DIA_VTA=cFechaCierre_in
          --AND D.IND_VB_CIERRE_DIA='S'
          AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
          AND A.COD_LOCAL=B.COD_LOCAL
          AND A.SEC_MOV_CAJA=B.SEC_MOV_CAJA
          AND A.FEC_DIA_VTA=B.FEC_DIA_VTA
          AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
          AND B.COD_LOCAL=C.COD_LOCAL
          AND B.SEC_MOV_CAJA=C.SEC_MOV_CAJA
          AND A.SEC_FORMA_PAGO_ENTREGA=C.SEC_FORMA_PAGO_ENTREGA
          AND B.COD_GRUPO_CIA=D.COD_GRUPO_CIA(+)
          AND B.COD_LOCAL=D.COD_LOCAL(+)
          AND B.FEC_DIA_VTA=D.FEC_CIERRE_DIA_VTA(+)
          GROUP BY TO_CHAR(A.FEC_DIA_VTA,'DD/MM/YYYY');

          --efectivo rendido dolares de todas las cuadraturas
          SELECT NVL(SUM(A.MON_TOTAL),0) into nEfectDolares
          FROM CE_CUADRATURA_CIERRE_DIA A
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_LOCAL=cCodLocal_in
          AND A.TIP_MONEDA=TIP_MONEDA_DOLARES
          AND A.FEC_CIERRE_DIA_VTA=cFechaCierre_in;


          nMontoDifeD:=nEfectDolares-nTotalDSobres;

         IF(nMontoDifeD<>0)THEN
            RAISE_APPLICATION_ERROR(-20120,'El MONTO DECLARADO EN SOBRES (dolares) : S/.'||TO_CHAR(nvl(nTotalDSobres,0),'999,990.000') ||'         :NO ES EL MISMO DE EFECTIVO RENDIDO : S/. '||TO_CHAR(nvl(nEfectDolares,0),'999,990.000')||'     ');
         END IF;

   END IF;


   END;
   */

/**************************************************HITO 04*******************************************************************************/

FUNCTION CE_F_GET_IND_PROSEGUR(cCodCia_in IN CHAR,
                               cCodLoc_in IN CHAR)
RETURN CHAR
IS
indPros CHAR(1);
BEGIN
  SELECT nvl(a.ind_prosegur,' ') INTO indPros
  FROM pbl_local a
  WHERE a.cod_grupo_cia=cCodCia_in
  AND a.cod_local=cCodLoc_in;
  RETURN indPros;
END;

/*****************************************************************************************************************************************/
-- dubilluz 27.07.2010
FUNCTION CE_F_LISTA_SOBRE_AS(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR)
RETURN FarmaCursor
IS
    curDet FarmaCursor;
    clocal_tico CHAR(3):= PTOVENTA_CE_REMITO.GET_LOCAL_TICO(cCodLocal_in); -- local tico 28.10.2014    
  BEGIN
    /*OPEN curDet FOR
      SELECT to_char(E.FEC_DIA_VTA,'dd/mm/yyyy') || 'Ã' ||
             A.COD_SOBRE || 'Ã' ||
             DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
             TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00') || 'Ã' ||
             NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
             NVL(B.USU_CREA_FORMA_PAGO_ENT,' ')|| 'Ã' ||
             NVL(D.USU_CREA_CIERRE_DIA,' ')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '01' THEN 1 END,0)|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '02' THEN 1 END,0)
        FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, VTA_FORMA_PAGO C,CE_CIERRE_DIA_VENTA D,
             CE_DIA_REMITO E
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
          AND E.COD_REMITO IS NULL
                 and a.estado = 'A'
         --AND A.FEC_DIA_VTA = to_date(cFecha,'dd/mm/yyyy')
         AND d.ind_vb_cierre_dia='S'
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_LOCAL=D.COD_LOCAL --ASOSA, 15.06.2010
         AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
         AND A.FEC_DIA_VTA=TRUNC(D.FEC_CIERRE_DIA_VTA)
         AND A.COD_GRUPO_CIA=E.COD_GRUPO_CIA
         AND A.COD_LOCAL=E.COD_LOCAL--(+)-- se quita porq no podria haber sobres sin dia
         AND A.FEC_DIA_VTA=E.FEC_DIA_VTA;--(+);*/
OPEN curDet FOR
 SELECT to_char(E.FEC_DIA_VTA,'dd/mm/yyyy') || 'Ã' ||
             A.COD_SOBRE || 'Ã' ||
             DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
             TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00') || 'Ã' ||
             NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
             NVL(B.USU_CREA_FORMA_PAGO_ENT,' ')|| 'Ã' ||
             --A.USU_MOD_SOBRE|| 'Ã' ||
             nvl(u.login_usu,'-') || 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '01' THEN 1 END,0)|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '02' THEN 1 END,0)
        FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, VTA_FORMA_PAGO C,CE_DIA_REMITO E,
             pbl_usu_local u,
             pbl_local loc
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
          AND A.COD_REMITO IS NULL
                 and a.estado = 'A'
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_GRUPO_CIA=E.COD_GRUPO_CIA
         AND A.COD_LOCAL=E.COD_LOCAL
         AND A.FEC_DIA_VTA=E.FEC_DIA_VTA
         AND a.cod_grupo_cia = u.cod_grupo_cia
         and a.cod_local = u.cod_local
         and a.sec_usu_qf = u.sec_usu_local
         and a.cod_local=loc.cod_local
         AND NVL(a.ind_etv,loc.ind_prosegur)='S' -- kmoncada 19.05.2014 indicador de tipo de sobre portavalor o deposito
         union -- selecciona sobres de REMITO.
         SELECT to_char(A.FEC_DIA_VTA,'dd/mm/yyyy') || 'Ã' ||
                       A.COD_SOBRE || 'Ã' ||
                       DECODE(A.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
                       TO_CHAR(NVL(CASE
                                     WHEN A.TIP_MONEDA = '01' THEN
                                      A.MON_ENTREGA_TOTAL
                                     WHEN A.TIP_MONEDA = '02' THEN
                                      A.MON_ENTREGA
                                   END,
                                   0),
                               '999,999,990.00') || 'Ã' ||
                       NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
                       NVL(A.Usu_Crea_Sobre,' ')|| 'Ã' ||
                       --A.USU_MOD_SOBRE|| 'Ã' ||
                       nvl(u.login_usu,'-') || 'Ã' ||
                              TO_CHAR(NVL(CASE
                                     WHEN A.TIP_MONEDA = '01' THEN
                                      A.MON_ENTREGA_TOTAL
                                   END,
                                   0),
                                   '999,999,990.00')|| 'Ã' ||
                              TO_CHAR(NVL(CASE
                                     WHEN A.TIP_MONEDA = '02' THEN
                                      A.MON_ENTREGA
                                   END,
                                   0),
                                   '999,999,990.00')|| 'Ã' ||
                                   NVL(CASE WHEN A.TIP_MONEDA = '01' THEN 1 END,0)|| 'Ã' ||
                                   NVL(CASE WHEN A.TIP_MONEDA = '02' THEN 1 END,0)
                  FROM CE_SOBRE_TMP A, VTA_FORMA_PAGO C,
                       pbl_usu_local u
                       ,pbl_local loc
                 WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND A.COD_LOCAL = cCodLocal_in
                    AND A.COD_REMITO IS NULL
                           and a.estado = 'A'
                   AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                   AND A.COD_FORMA_PAGO = C.COD_FORMA_PAGO
                   AND a.cod_grupo_cia = u.cod_grupo_cia
                   and a.cod_local = u.cod_local
                   and a.sec_usu_qf = u.sec_usu_local
                   and a.cod_local=loc.cod_local
                   AND NVL(a.ind_etv,loc.ind_prosegur)='S' -- kmoncada 19.05.2014 indicador de tipo de sobre portavalor o deposito
                   and not exists (
                                   select 1
                                   from   ce_sobre sobre
                                   where  sobre.cod_sobre = a.cod_sobre
                                   )
             UNION --- UNION TABLA SOBRES TICOS rherrera 27.08.2014 -- remito
              SELECT TI.FEC_VTA            || 'Ã' ||
                        TI.COD_SOBRE       || 'Ã' ||
                        TI.MONEDA          || 'Ã' ||
                        TO_CHAR(TI.MON_ENTREGA_TOTAL,'999,999,990.00') || 'Ã' ||
                        TI.DESC_FORMA_PAGO                             || 'Ã' ||
                        TI.USU_CREA_SOBRE                              || 'Ã' ||
                        TI.USU_LOGIN                                   || 'Ã' ||
                        TO_CHAR(TI.MON_ENTRE_SOL,'999,999,990.00')     || 'Ã' ||
                        TO_CHAR(TI.MON_ENTRE_DOL,'999,999,990.00')     || 'Ã' ||
                        TI.TIP_MOND_SOL                                || 'Ã' ||
                        TI.TIP_MOND_DOL
                 FROM CE_SOBRE_TICO TI
                 WHERE  TI.COD_REMITO IS NULL
                 AND    TI.IND_LOCAL = INDICADOR_NO
                 AND    TI.EST_SOBRE = ESTADO_ACTIVO
                 AND    TI.COD_LOCAL = clocal_tico -- local tico
                                   ;

    RETURN curDet;
  END;

/*****************************************************************************************************************************************/

  PROCEDURE CE_P_AGREGA_REMITO(cCodGrupoCia_in IN CHAR,
                                cCodLocal       IN CHAR,
                                cIdUsu_in       IN CHAR,
                                cNumRemito      IN CHAR,
                                cFecha          IN CHAR)

   IS
   CURSOR cur IS
      SELECT A.COD_SOBRE
      FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, VTA_FORMA_PAGO C,CE_CIERRE_DIA_VENTA D,
             CE_DIA_REMITO E
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal
         AND E.COD_REMITO IS NULL
         AND a.estado = 'A'
         AND d.ind_vb_cierre_dia='S'
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
         AND A.FEC_DIA_VTA=TRUNC(D.FEC_CIERRE_DIA_VTA)
         AND A.COD_GRUPO_CIA=E.COD_GRUPO_CIA
         AND A.COD_LOCAL=E.COD_LOCAL--(+) PORQUE NO DEBERIA HABER SOBRE SIN DIA REMITO
         AND A.FEC_DIA_VTA=E.FEC_DIA_VTA;--(+);
    fila cur%ROWTYPE;
    v_nCant  NUMBER;
    v_nCant2 NUMBER;

  BEGIN

    SELECT COUNT(*)
      INTO v_nCant
      FROM CE_DIA_REMITO A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal
       AND TRUNC(A.FEC_DIA_VTA) = TRIM(cFecha);

    SELECT COUNT(*)
      INTO v_nCant2
      FROM CE_REMITO C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal
       AND TRIM(C.COD_REMITO) = TRIM(cNumRemito);

    IF (v_nCant2 = 0) THEN
      INSERT INTO CE_REMITO
        (COD_REMITO,
         COD_GRUPO_CIA,
         COD_LOCAL,
         USU_CREA_REMITO,
         USU_MOD_REMITO,
         FEC_MOD_REMITO,
         FEC_PROCESO_ARCHIVO,
         FEC_PROCESO_INT_CE
         )
      VALUES
        (cNumRemito,
         cCodGrupoCia_in,
         cCodLocal,
         cIdUsu_in,
         NULL,
         NULL,
         NULL,
         NULL);

       OPEN cur;
       LOOP
       FETCH cur INTO fila;
       EXIT WHEN cur%NOTFOUND;
            BEGIN
            UPDATE CE_SOBRE SO SET SO.COD_REMITO = cNumRemito
            WHERE SO.COD_SOBRE = fila.COD_SOBRE
            AND SO.COD_GRUPO_CIA = cCodGrupoCia_in
            AND SO.COD_LOCAL = cCodLocal;
            COMMIT;
            END;
       END LOOP;
       CLOSE cur;
    END IF;

    IF v_nCant > 0 THEN
      UPDATE CE_DIA_REMITO B
         SET B.COD_REMITO         = cNumRemito,
             B.USU_MOD_DIA_REMITO = cIdUsu_in,
             B.FEC_MOD_DIA_REMITO = SYSDATE
       WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
         AND B.COD_LOCAL = cCodLocal
         AND TRUNC(B.FEC_DIA_VTA) = TRIM(cFecha)
         AND B.COD_REMITO IS NULL;
    END IF;

  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
      RAISE_APPLICATION_ERROR(-20002, 'Ya existe asignacion de remito');

  END;

/*****************************************************************************************************************************************/

  FUNCTION CE_F_HTML_VOUCHER_REMITO(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodRemito      IN CHAR,
                                        cIpServ_in      IN CHAR)
    RETURN VARCHAR2 IS
    vMsg_out varchar2(32767) := '';
    vFila_2  VARCHAR2(32767) := '';
    vFila_3  VARCHAR2(32767) := '';
    vFila_4  VARCHAR2(32767) := '';
    vFila_41 VARCHAR2(32767) := '';

    vFila_3_AS  VARCHAR2(32767) := '';
    vFila_4_AS  VARCHAR2(32767) := '';
    vFila_41_AS VARCHAR2(32767) := '';

    vFecha      varchar2(200);
    vNumSobresS varchar2(200);
    vMontoS     varchar2(200);
    vNumSobresD varchar2(200);
    vMontoD     varchar2(200);

    vMontotTotalS varchar2(200);
    vMontotTotalD varchar2(200);
    vUsuRem       varchar2(200);

    i NUMBER(7) := 0;
    /*cursor3 FarmaCursor := SEG_F_CUR_GET_DATA3(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cCodRemito);
    cursor4 FarmaCursor := SEG_F_CUR_GET_DATA4(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cCodRemito);
    curSobSol FarmaCursor := CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in,cCodLocal_in);
    curSobDol FarmaCursor := CE_F_LISTA_TOTALES_SD(cCodGrupoCia_in,cCodLocal_in);*/
    CURSOR cursor3 IS SELECT xx.usuario,
                             to_char(xx.total_s,'999,999,990.00') total_s,
                             to_char(xx.Total_d,'999,999,990.00') Total_d
                      FROM CE_TOTS_REMI_SD xx
                      WHERE xx.cod_grupo_cia=cCodGrupoCia_in
                      AND xx.cod_local=cCodLocal_in
                      AND xx.cod_remito=cCodRemito
                      AND xx.Ind_Remito='S';
    CURSOR cursor4 IS SELECT to_char(yy.fec_dia_vta_rem,'dd/MM/yyyy') fec_dia_vta_rem,
                             yy.cant_s,
                             to_char(yy.mont_s,'999,999,990.00') mont_s,
                             yy.cant_d,
                             to_char(yy.mont_d,'999,999,990.00') mont_d
                      FROM CE_SOBRE_REMI_SD yy
                      WHERE yy.cod_grupo_cia=cCodGrupoCia_in
                      AND yy.cod_local=cCodLocal_in
                      AND yy.cod_remito=cCodRemito
                      AND yy.Ind_Remito='S';
    CURSOR curSobSol IS SELECT to_char(zz.fec_dia_vta_rem,'dd/MM/yyyy') fec_dia_vta_rem,
                               zz.cant_s,
                               to_char(zz.mont_s,'999,999,990.00') mont_s,
                               zz.cant_d,
                               to_char(zz.mont_d,'999,999,990.00') mont_d
                        FROM CE_SOBRE_REMI_SD zz
                        WHERE zz.cod_grupo_cia=cCodGrupoCia_in
                        AND zz.cod_local=cCodLocal_in
                        AND zz.cod_remito=cCodRemito
                        AND zz.Ind_Remito='N';
    CURSOR curSobDol IS SELECT ww.usuario,
                               to_char(ww.total_s,'999,999,990.00') total_s,
                               to_char(ww.Total_d,'999,999,990.00') Total_d
                        FROM CE_TOTS_REMI_SD ww
                        WHERE ww.cod_grupo_cia=cCodGrupoCia_in
                        AND ww.cod_local=cCodLocal_in
                        AND ww.cod_remito=cCodRemito
                        AND ww.Ind_Remito='N';
    fila4 cursor4%ROWTYPE;
    fila3 cursor3%ROWTYPE;
    filasol curSobSol%ROWTYPE;
    filadol curSobDol%ROWTYPE;
  BEGIN
    OPEN cursor4;
    LOOP
      FETCH cursor4
        INTO fila4;--vFecha, vNumSobresS, vMontoS, vNumSobresD, vMontoD;
      EXIT WHEN cursor4%NOTFOUND;
      IF (LENGTH(vFila_4) >= 32767 - 20) THEN
        i := i + 1;
        IF (i = 1) THEN
          vFila_41 := vFila_41 || vFila_4;
        END IF;
        vFila_41 := vFila_41 || ' <tr> ' || ' <td>' || fila4.fec_dia_vta_rem || '</td> ' ||
                    ' <td>' || fila4.cant_s || '</td> ' || ' <td><p>S/' ||
                    fila4.mont_s || '</p></td> ' || ' <td>' || fila4.fec_dia_vta_rem ||
                    '</td> ' || ' <td>' || fila4.cant_d || '</td> ' ||
                    ' <td><p>US$' || fila4.mont_d || '</p></td> ' || ' </tr> ';
      ELSE
        vFila_4 := vFila_4 || ' <tr> ' || ' <td>' || fila4.fec_dia_vta_rem || '</td> ' ||
                   ' <td>' || fila4.cant_s || '</td> ' || ' <td><p>S/' ||
                   fila4.mont_s || '</p></td> ' || ' <td>' || fila4.fec_dia_vta_rem || '</td> ' ||
                   ' <td>' || fila4.cant_d || '</td> ' || ' <td><p>US$' ||
                   fila4.mont_d || '</p></td> ' || ' </tr> ';

      END IF;
    END LOOP;
    CLOSE cursor4;
    -------------------------------------------------------------------------------
    OPEN cursor3;
    LOOP
      FETCH cursor3
        INTO fila3; --vUsuRem, vMontotTotalS, vMontotTotalD;
      EXIT WHEN cursor3%NOTFOUND;
      vUsuRem:=fila3.usuario;
      vFila_3 := vFila_3 || ' <tr>  ' ||
                 ' <td><strong>TOTAL</strong></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>S/.' ||
                 fila3.total_s || '</strong></p></td>  ' ||
                 ' <td><p><strong>TOTAL</strong></p></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>US$' ||
                 fila3.total_d || '</strong></p></td> ' || ' </tr>';

    END LOOP;
    CLOSE cursor3;
    dbms_output.put_line('vFila_3: '||vFila_3);
dbms_output.put_line('hito05');
    -----------------------------------------------------
    vFila_2 := vFila_2 || '   <tr> ' ||
               ' <td height="68" colspan="3"><p>REMITO N&deg; :  <strong>' ||
               cCodRemito || '</strong></p> ' || ' <p><strong>' || vUsuRem ||
               '</strong></p></td> ' || ' <td colspan="3"><center>' ||
               cCodLocal_in || '  -  ' || TRUNC(SYSDATE) ||
               '</center></td> ' || ' </tr>';
               dbms_output.put_line('COPIA');
               dbms_output.put_line(C_INICIO_MSG_2);
               dbms_output.put_line(vFila_4);
               dbms_output.put_line(vFila_41);
               dbms_output.put_line(vFila_3);
               dbms_output.put_line(vFila_2);
               dbms_output.put_line('</td></tr>');
               dbms_output.put_line(C_INI_NODEBENIR);
               dbms_output.put_line(C_DEPOSITO_SD);
    vMsg_out := C_INICIO_MSG_2 || vFila_4 || vFila_41 ||
                vFila_3 || vFila_2 || '</td></tr>' || C_INI_NODEBENIR || C_DEPOSITO_SD;--C_FIN_MSG;
    ---------------------------------------------------------------------------------------------------------------
    OPEN curSobSol;
    LOOP
      FETCH curSobSol
        INTO filasol;--vFecha, vNumSobresS, vMontoS, vNumSobresD, vMontoD;
      EXIT WHEN curSobSol%NOTFOUND;
      IF (LENGTH(vFila_4_AS) >= 32767 - 20) THEN
        i := i + 1;
        IF (i = 1) THEN
          vFila_41_AS := vFila_41_AS || vFila_4_AS;
        END IF;
        vFila_41_AS := vFila_41_AS || ' <tr> ' || ' <td>' || filasol.fec_dia_vta_rem || '</td> ' ||
                    ' <td>' || filasol.cant_s || '</td> ' || ' <td><p>S/' ||
                    filasol.mont_s || '</p></td> ' || ' <td>' || filasol.fec_dia_vta_rem ||
                    '</td> ' || ' <td>' || filasol.cant_d || '</td> ' ||
                    ' <td><p>US$' || filasol.mont_d || '</p></td> ' || ' </tr> ';
      ELSE
        vFila_4_AS := vFila_4_AS || ' <tr> ' || ' <td>' || filasol.fec_dia_vta_rem || '</td> ' ||
                   ' <td>' || filasol.cant_s || '</td> ' || ' <td><p>S/' ||
                   filasol.mont_s || '</p></td> ' || ' <td>' || filasol.fec_dia_vta_rem || '</td> ' ||
                   ' <td>' || filasol.cant_d || '</td> ' || ' <td><p>US$' ||
                   filasol.mont_d || '</p></td> ' || ' </tr> ';

      END IF;
    END LOOP;
    CLOSE curSobSol;
    ----------------------------------------------------------------------------------------------------------------
    OPEN curSobDol;
    LOOP
      FETCH curSobDol
        INTO  filadol;--vMontotTotalS, vMontotTotalD;
      EXIT WHEN curSobDol%NOTFOUND;
      vFila_3_AS := vFila_3_AS || ' <tr>  ' ||
                 ' <td><strong>TOTAL</strong></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>S/.' ||
                 filadol.total_s || '</strong></p></td>  ' ||
                 ' <td><p><strong>TOTAL</strong></p></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>US$' ||
                 filadol.Total_d || '</strong></p></td> ' || ' </tr>';

    END LOOP;
    CLOSE curSobDol;
    ----------------------------------------------------------------------------------------------------------------
        dbms_output.put_line(vFila_4_AS);
        dbms_output.put_line(vFila_41_AS);
        dbms_output.put_line(vFila_3_AS);
        dbms_output.put_line(C_FIN_MSG);
        dbms_output.put_line('FIN COPIA');
    vMsg_out := vMsg_out || vFila_4_AS || vFila_41_AS ||
                vFila_3_AS || C_FIN_MSG;

                dbms_output.put_line('hito07');
                dbms_output.put_line(vMsg_out);
    RETURN vMsg_out;

  END;


/*****************************************************************************************************************************************/


    FUNCTION SEG_F_CUR_GET_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor IS
    curVta FarmaCursor;
    --int_total number;
    --vCod_local_origen char(3);
  BEGIN
    OPEN curVta FOR
      SELECT V2.FECHA,
             V2.CANT_S,
             TO_CHAR(V2.SOLES, '999,999,990.00'),
             V2.CANT_D,
             TO_CHAR(V2.DOLARES, '999,999,990.00')
        FROM (SELECT TO_CHAR(A.FEC_DIA_VTA, 'DD/MM/YYYY') FECHA,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                C.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                1
                             END),
                         0) CANT_S,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                C.MON_ENTREGA
                             END),
                         0) DOLARES,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                1
                             END),
                         0) CANT_D,
                     V1.CANT CANT
                FROM CE_DIA_REMITO A,
                     CE_SOBRE B,
                     CE_FORMA_PAGO_ENTREGA C,
                     (SELECT COUNT(*) CANT,
                             TRUNC(X.FEC_DIA_VTA) FEC,
                             X.COD_GRUPO_CIA,
                             X.COD_LOCAL
                        FROM CE_SOBRE X
                        where x.estado = 'A'
                       GROUP BY TRUNC(X.FEC_DIA_VTA),
                                X.COD_GRUPO_CIA,
                                X.COD_LOCAL) V1,
                     CE_CIERRE_DIA_VENTA Y
               WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND A.COD_LOCAL = cCodLocal_in
                 AND A.COD_REMITO = TRIM(cCodRemito) --
                 AND Y.IND_VB_CIERRE_DIA = 'S'
                        and b.estado = 'A'
                 AND A.COD_REMITO IS NOT NULL
                 AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND A.FEC_DIA_VTA = B.FEC_DIA_VTA
                 AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND B.COD_LOCAL = C.COD_LOCAL
                 AND B.SEC_MOV_CAJA = C.SEC_MOV_CAJA
                 AND B.SEC_FORMA_PAGO_ENTREGA = C.SEC_FORMA_PAGO_ENTREGA --
                 AND TRUNC(A.FEC_DIA_VTA) = V1.FEC
                 AND A.COD_GRUPO_CIA = V1.COD_GRUPO_CIA
                 AND A.COD_LOCAL = V1.COD_LOCAL
                 AND A.COD_GRUPO_CIA = Y.COD_GRUPO_CIA
                 AND A.COD_LOCAL = Y.COD_LOCAL
                 AND A.FEC_DIA_VTA = Y.FEC_CIERRE_DIA_VTA
               GROUP BY TO_CHAR(A.FEC_DIA_VTA, 'DD/MM/YYYY'), V1.CANT
               ORDER BY 1 ASC) V2;
    RETURN curVta;
  END;

/*****************************************************************************************************************************************/
  FUNCTION SEG_F_CUR_GET_DATA3(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      SELECT V1.USU,
             TO_CHAR(V1.SOLES, '999,999,990.00'),
             TO_CHAR(V1.DOLARES, '999,999,990.00')
        FROM (SELECT B.USU_CREA_REMITO USU,
                     NVL(SUM(CASE
                               WHEN D.TIP_MONEDA = '01' THEN
                                D.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN D.TIP_MONEDA = '02' THEN
                                D.MON_ENTREGA
                             END),
                         0) DOLARES
                FROM CE_DIA_REMITO         A,
                     CE_REMITO             B,
                     CE_SOBRE              C,
                     CE_FORMA_PAGO_ENTREGA D,
                     CE_CIERRE_DIA_VENTA Y
               WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND A.COD_LOCAL = cCodLocal_in
                 AND A.COD_REMITO = TRIM(cCodRemito)
                 AND c.estado = 'A'
                 AND Y.IND_VB_CIERRE_DIA = 'S'
                 AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND A.COD_REMITO = B.COD_REMITO
                 AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND TRUNC(A.FEC_DIA_VTA) = TRUNC(C.FEC_DIA_VTA)
                 AND A.COD_LOCAL = C.COD_LOCAL
                 AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                 AND C.COD_LOCAL = D.COD_LOCAL
                 AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                 AND C.SEC_FORMA_PAGO_ENTREGA = D.SEC_FORMA_PAGO_ENTREGA
                 AND A.COD_GRUPO_CIA = Y.COD_GRUPO_CIA
                 AND A.COD_LOCAL = Y.COD_LOCAL
                 AND A.FEC_DIA_VTA = Y.FEC_CIERRE_DIA_VTA
               GROUP BY B.USU_CREA_REMITO) V1;

    RETURN curDet;
  END;

/*****************************************************************************************************************************************/

FUNCTION CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR) RETURN FarmaCursor IS
    curSobresCajero FarmaCursor;
  BEGIN
    OPEN curSobresCajero FOR
      SELECT TO_CHAR(D.Fec_Dia_Vta,'dd/MM/yyyy') FECHA,
             (SELECT COUNT(*)
              FROM CE_FORMA_PAGO_ENTREGA G, Ce_Sobre K
              WHERE G.COD_FORMA_PAGO='00001' AND G.COD_GRUPO_CIA=K.COD_GRUPO_CIA
              AND G.COD_LOCAL=K.COD_LOCAL AND G.SEC_MOV_CAJA=K.SEC_MOV_CAJA
              AND G.SEC_FORMA_PAGO_ENTREGA=K.SEC_FORMA_PAGO_ENTREGA
              AND K.FEC_DIA_VTA = D.FEC_DIA_VTA
              AND G.EST_FORMA_PAGO_ENT='A') CANT_S,
             TO_CHAR((SELECT NVL(SUM(G.MON_ENTREGA_TOTAL),0)  --ASOSA, 15.06.2010
              FROM CE_FORMA_PAGO_ENTREGA G, Ce_Sobre K
              WHERE G.COD_FORMA_PAGO='00001' AND G.COD_GRUPO_CIA=K.COD_GRUPO_CIA
              AND G.COD_LOCAL=K.COD_LOCAL AND G.SEC_MOV_CAJA=K.SEC_MOV_CAJA
              AND G.SEC_FORMA_PAGO_ENTREGA=K.SEC_FORMA_PAGO_ENTREGA
              AND K.FEC_DIA_VTA = D.FEC_DIA_VTA
              AND G.EST_FORMA_PAGO_ENT='A'),'999,999,990.00') MONT_S,
              (SELECT COUNT(*)
              FROM CE_FORMA_PAGO_ENTREGA G, Ce_Sobre K
              WHERE G.COD_FORMA_PAGO='00002' AND G.COD_GRUPO_CIA=K.COD_GRUPO_CIA
              AND G.COD_LOCAL=K.COD_LOCAL AND G.SEC_MOV_CAJA=K.SEC_MOV_CAJA
              AND G.SEC_FORMA_PAGO_ENTREGA=K.SEC_FORMA_PAGO_ENTREGA
              AND K.FEC_DIA_VTA = D.FEC_DIA_VTA
              AND G.EST_FORMA_PAGO_ENT='A') CANT_D,
             TO_CHAR((SELECT NVL(SUM(G.MON_ENTREGA_TOTAL),0) --ASOSA, 15.06.2010
              FROM CE_FORMA_PAGO_ENTREGA G, Ce_Sobre K
              WHERE G.COD_FORMA_PAGO='00002' AND G.COD_GRUPO_CIA=K.COD_GRUPO_CIA
              AND G.COD_LOCAL=K.COD_LOCAL AND G.SEC_MOV_CAJA=K.SEC_MOV_CAJA
              AND G.SEC_FORMA_PAGO_ENTREGA=K.SEC_FORMA_PAGO_ENTREGA
              AND K.FEC_DIA_VTA = D.FEC_DIA_VTA
              AND G.EST_FORMA_PAGO_ENT='A'),'999,999,990.00') MONT_D
             --nvl(SUM(CASE WHEN b.cod_forma_pago='00001' THEN 1 END),0) CANT_SOBRE_S,
             --NVL(SUM(CASE WHEN B.COD_FORMA_PAGO='00002' THEN B.MON_ENTREGA_TOTAL END),0) MONTO_S
      FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, CE_CIERRE_DIA_VENTA C,
             CE_DIA_REMITO D
      WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
      AND B.COD_LOCAL=cCodLocal_in
      AND B.COD_GRUPO_CIA=A.COD_GRUPO_CIA
      AND B.COD_LOCAL=A.COD_LOCAL
      AND B.SEC_MOV_CAJA=A.SEC_MOV_CAJA
      AND B.SEC_FORMA_PAGO_ENTREGA=A.SEC_FORMA_PAGO_ENTREGA
      AND B.EST_FORMA_PAGO_ENT='A'
      AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
      AND A.COD_LOCAL=D.COD_LOCAL
      AND A.FEC_DIA_VTA=D.FEC_DIA_VTA
      AND D.FEC_DIA_VTA=C.FEC_CIERRE_DIA_VTA
      AND c.ind_vb_cierre_dia='N'
      GROUP BY D.Fec_Dia_Vta;
    return curSobresCajero;
  end;

/*****************************************************************************************************************************************/

FUNCTION CE_F_LISTA_TOTALES_SD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                vUsu_in IN VARCHAR2) RETURN FarmaCursor IS
    curSobresCajero FarmaCursor;
    cursor02 FarmaCursor:=CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in,cCodLocal_in);
    montoSoles NUMBER(9,2):=0;
    montoDolares NUMBER(9,2):=0;
    cants VARCHAR2(200);
    cantd VARCHAR2(200);
    monts VARCHAR(200);
    montd VARCHAR(200);
    fecha VARCHAR(200);
  BEGIN
    LOOP
      FETCH cursor02
        INTO fecha, cants, monts, cantd, montd;
      EXIT WHEN cursor02%NOTFOUND;
      montoSoles:=montoSoles+to_number(monts,'999,999,990.00');
      montoDolares:=montoDolares+to_number(montd,'999,999,990.00');
    END LOOP;
    OPEN curSobresCajero FOR
    SELECT vUsu_in, to_char(montoSoles,'999,999,990.00') MONTO_S, to_char(montoDolares,'999,999,990.00') MONTO_D
    FROM dual;
    return curSobresCajero;
  end;

/*****************************************************************************************************************************************/

PROCEDURE CE_P_SAVE_HIST_REMI(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cCodRemito      IN CHAR,
                             vUsu_in IN VARCHAR2)
AS
cursor01 FarmaCursor := SEG_F_CUR_GET_DATA4(cCodGrupoCia_in,
                                            cCodLocal_in,
                                            cCodRemito);
cursor02 FarmaCursor := SEG_F_CUR_GET_DATA3(cCodGrupoCia_in,
                                            cCodLocal_in,
                                            cCodRemito);
cursor03 FarmaCursor := CE_F_LIST_SOBRE_NOREMI_SD(cCodGrupoCia_in,
                                                  cCodLocal_in);
cursor04 FarmaCursor := CE_F_LISTA_TOTALES_SD(cCodGrupoCia_in,
                                              cCodLocal_in,
                                              vUsu_in);

fecha VARCHAR2(200);
cants VARCHAR2(200);
montos VARCHAR2(200);
cantd VARCHAR2(200);
montod VARCHAR2(200);
usuario VARCHAR2(200);
montototals VARCHAR2(200);
montototald VARCHAR(200);
BEGIN
     LOOP --cursor01
     FETCH cursor01 INTO fecha, cants, montos, cantd, montod;
     EXIT WHEN cursor01%NOTFOUND;
          INSERT INTO CE_SOBRE_REMI_SD(COD_GRUPO_CIA,COD_LOCAL,COD_REMITO,IND_REMITO,FEC_DIA_VTA_REM,CANT_S,MONT_S,
                                       CANT_D,MONT_D,FEC_CREA,USU_CREA,FEC_MOD,USU_MOD)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodRemito,'S',TO_DATE(fecha,'dd/MM/yyyy'),
                 to_number(cants,'999,999,990'),to_number(montos,'999,999,990.00'),
                 to_number(cantd,'999,999,990'),to_number(montod,'999,999,990.00'),SYSDATE,vUsu_in,NULL,NULL);
     END LOOP;
     LOOP --cursor02
     FETCH cursor02 INTO usuario, montototals, montototald;
     EXIT WHEN cursor02%NOTFOUND;
          INSERT INTO CE_TOTS_REMI_SD(COD_GRUPO_CIA,COD_LOCAL,COD_REMITO,IND_REMITO,USUARIO,TOTAL_S,TOTAL_D,
                                      USU_CREA,FEC_CREA,USU_MOD,FEC_MOD)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodRemito,'S',usuario,
                 to_number(montototals,'999,999,990.00'),to_number(montototald,'999,999,990.00'),vUsu_in,SYSDATE,NULL,NULL);
     END LOOP;
     LOOP --cursor03
     FETCH cursor03 INTO fecha, cants, montos, cantd, montod;
     EXIT WHEN cursor03%NOTFOUND;
          INSERT INTO CE_SOBRE_REMI_SD(COD_GRUPO_CIA,COD_LOCAL,COD_REMITO,IND_REMITO,FEC_DIA_VTA_REM,CANT_S,MONT_S,
                                       CANT_D,MONT_D,FEC_CREA,USU_CREA,FEC_MOD,USU_MOD)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodRemito,'N',TO_DATE(fecha,'dd/MM/yyyy'),
                 to_number(cants,'999,999,990'),to_number(montos,'999,999,990.00'),
                 to_number(cantd,'999,999,990'),to_number(montod,'999,999,990.00'),SYSDATE,vUsu_in,NULL,NULL);
     END LOOP;
     LOOP --cursor04
     FETCH cursor04 INTO usuario, montototals, montototald;
     EXIT WHEN cursor04%NOTFOUND;
          INSERT INTO CE_TOTS_REMI_SD(COD_GRUPO_CIA,COD_LOCAL,COD_REMITO,IND_REMITO,USUARIO,TOTAL_S,TOTAL_D,
                                      USU_CREA,FEC_CREA,USU_MOD,FEC_MOD)
          VALUES(cCodGrupoCia_in,cCodLocal_in,cCodRemito,'N',usuario,
                 to_number(montototals,'999,999,990.00'),to_number(montototald,'999,999,990.00'),vUsu_in,SYSDATE,NULL,NULL);
     END LOOP;
END;

/*************************************************HITO 05**********************************************************************/

FUNCTION CE_LISTA_ANUL_CUADRATURA_CD_AS(cCodGrupoCia_in   IN CHAR,
                                       cCodlLocal_in     IN CHAR,
                                       cCodCuadratura_in IN CHAR,
                                       cFechaCD_in       IN CHAR)
  RETURN FarmaCursor
  AS
     curCe FarmaCursor;
  BEGIN
       IF(ccodcuadratura_in = C_C_DEPOSITO_VENTA) THEN -- deposito por venta
          OPEN  curCe FOR
          SELECT vta.desc_corta_forma_pago ||'Ã'||
                   NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   EF.NOM_ENTIDAD_FINANCIERA ||'Ã'||
                   NVL(TO_CHAR(CD.FEC_OPERACION,'dd/MM/yyyy HH24:MI:SS'),' ')||'Ã'||
                   nvl(cd.num_operacion,' ') ||'Ã'||
                   FC.NUM_CUENTA_BANCO ||'Ã'||
                   nvl(CD.NOM_AGENCIA,' ') ||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' '||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_ENTIDAD_FINANCIERA EF,
                   CE_ENTIDAD_FINANCIERA_CUENTA FC,
                   CE_CUADRATURA_CIERRE_DIA CD,
                   vta_forma_pago vta
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.SEC_ENT_FINAN_CUENTA = FC.SEC_ENT_FINAN_CUENTA
            AND    FC.COD_ENTIDAD_FINANCIERA = EF.COD_ENTIDAD_FINANCIERA
            AND    vta.cod_grupo_cia = cd.cod_grupo_cia
            AND    vta.cod_forma_pago = cd.cod_forma_pago
            UNION
            SELECT F.DESC_CORTA_FORMA_PAGO ||'Ã'||
                   NVL(DECODE(CF.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ')||'Ã'||
                   TO_CHAR(CF.MON_ENTREGA,'999,990.00') ||'Ã'||
                   TO_CHAR(CF.MON_ENTREGA_TOTAL,'999,990.00')||'Ã'||
                   'PROSEGUR' ||'Ã'||
                   NVL(TO_CHAR(S.FEC_CREA_SOBRE,'dd/MM/yyyy HH24:MI:SS'),' ') ||'Ã'||
                   --' ' ||'Ã'||
                   S.COD_SOBRE  ||'Ã'||
                   '0000000' ||'Ã'||
                   ' ' ||'Ã'||
                   NVL((SELECT TRIM('Recibido por QF - '||U.NOM_USU||' '||U.APE_PAT||' '||U.APE_MAT) as quimico FROM
                    PBL_USU_LOCAL U,
                    CE_CIERRE_DIA_VENTA CD
                    WHERE CD.COD_GRUPO_CIA = U.COD_GRUPO_CIA
                    AND   CD.COD_LOCAL     = U.COD_LOCAL
                    AND   CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
                    AND   CD.COD_LOCAL     = CCODLLOCAL_IN
                    AND   CD.FEC_CIERRE_DIA_VTA = TO_DATE(CFECHACD_IN,'DD/MM/YYYY')
                    AND   CD.IND_VB_CIERRE_DIA = 'S'
                    AND   CD.SEC_USU_LOCAL_VB(+)  = U.SEC_USU_LOCAL ),' ')||'Ã'||
                   ' '||'Ã'||
                   ' '||'Ã'||
                   TO_CHAR(S.FEC_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_SOBRE S,
                   CE_FORMA_PAGO_ENTREGA CF,
                   VTA_FORMA_PAGO F,
                   PBL_LOCAL LOC
            WHERE  CF.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CF.COD_LOCAL     = CCODLLOCAL_IN
            AND    S.FEC_DIA_VTA    = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    S.COD_GRUPO_CIA = CF.COD_GRUPO_CIA
            AND    S.COD_LOCAL = CF.COD_LOCAL
            AND    S.SEC_MOV_CAJA = CF.SEC_MOV_CAJA
            AND    S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA
            AND    CF.COD_GRUPO_CIA = F.COD_GRUPO_CIA
            AND    CF.COD_FORMA_PAGO = F.COD_FORMA_PAGO
            AND    CF.EST_FORMA_PAGO_ENT = 'A'
            AND    LOC.COD_LOCAL=S.COD_LOCAL
--            AND    LOC.IND_PROSEGUR='S';
            AND NVL(S.Ind_Etv,LOC.IND_PROSEGUR)='S';

       ELSIF(ccodcuadratura_in = C_C_SERVICIOS_BASICOS) THEN -- servicios basicos
          OPEN  curCe FOR
            SELECT S.DESC_SERVICIO ||'Ã'||
                   p.desc_corta_proveedor ||'Ã'||
                   NVL(CD.Num_Documento,' ') ||'Ã'||
                   NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   NVL(TO_CHAR(CD.FEC_EMISION,'dd/MM/yyyy'),' ')||'Ã'||
                   NVL(TO_CHAR(CD.FEC_OPERACION,'dd/MM/yyyy HH24:MI:SS'),' ')||'Ã'||
                   NVL(TO_CHAR(Cd.Fec_Vencimiento,'dd/MM/yyyy'),' ')||'Ã'||
                   NVL(CD.NOM_TITULAR_SERVICIO,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   CE_SERVICIO S,
                   ce_proveedor p,
                   ce_servicio_proveedor sp
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_SERVICIO = Sp.COD_SERVICIO
            AND    cd.cod_proveedor = sp.cod_proveedor
            AND    sp.cod_servicio = s.cod_servicio
            AND    sp.cod_proveedor = p.cod_proveedor;
       ELSIF(ccodcuadratura_in = C_C_REEMBOLSO_C_CH) THEN -- REEMBOLSO DE CAJA CHICA
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy');
       ELSIF(ccodcuadratura_in = C_C_PAGO_PLANILLA) THEN -- PAGO PLANILLA
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   MT.NOM_TRAB ||' '|| MT.APE_PAT_TRAB ||' '|| NVL(MT.APE_MAT_TRAB,0)||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_MAE_TRAB MT,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_TRAB = MT.COD_TRAB
            AND    cd.cod_cia = mt.cod_cia;
       ELSIF(ccodcuadratura_in = C_C_COTIZA_COMP) THEN -- COTIZACION COMPETENCIA
          OPEN  curCe FOR
            SELECT NVL(EC.NUM_DOC,' ') ||'Ã'||
                   DECODE(NVL(EC.tip_doc,' '),C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',C_C_IND_TIP_COMP_PAGO_FACT,'FACTURA','03','GUIA','04','NOTA DE CREDITO','05','TICKET')||'Ã'||
                   to_char(EC.FEC_NOTA_ES_CAB,'dd/MM/yyyy') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ec.num_nota_es ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA ||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD ,
                   LGT_NOTA_ES_CAB EC
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.COD_GRUPO_CIA = EC.COD_GRUPO_CIA
            AND    CD.COD_LOCAL = EC.COD_LOCAL
            AND    CD.NUM_NOTA_ES = EC.NUM_NOTA_ES
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy');
       ELSIF(ccodcuadratura_in = C_C_ENTREGAS_RENDIR) THEN -- ENTREGAS A RENDIR
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   to_char(CD.Fec_Operacion,'dd/MM/yyyy HH24:MI:SS') ||'Ã'||
                   nvl(MT.NOM_TRAB ||' '|| MT.APE_PAT_TRAB ||' '|| MT.APE_MAT_TRAB,' ')||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(CD.DESC_MOTIVO,' ')||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_MAE_TRAB MT,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_TRAB = MT.COD_TRAB
            AND    cd.cod_cia = mt.cod_cia;
       ELSIF(ccodcuadratura_in = C_C_ROBO_ASALTO) THEN -- ROBO POR ASALTO
          OPEN  curCe FOR
            SELECT FP.DESC_CORTA_FORMA_PAGO||'Ã'||
                   DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(CD.DESC_MOTIVO,' ')||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   VTA_FORMA_PAGO FP,
                   VTA_FORMA_PAGO_LOCAL PL
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    FP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
            AND    FP.COD_FORMA_PAGO = PL.COD_FORMA_PAGO
            AND    PL.COD_GRUPO_CIA = CD.COD_GRUPO_CIA
            AND    PL.COD_LOCAL = CD.COD_LOCAL
            AND    PL.COD_FORMA_PAGO = CD.COD_FORMA_PAGO;
       ELSIF(ccodcuadratura_in = C_C_DINERO_FALSO) THEN -- DINERO FALSO
          OPEN  curCe FOR
            SELECT DECODE(NVL(CD.TIP_DINERO,' '),TIP_DINERO_BILLETE,'BILLETE',TIP_DINERO_MONEDA,'MONEDA')||'Ã'||
                   DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(CD.SERIE_BILLETE,' ') ||'Ã'||
                   TO_CHAR(CD.MON_PARCIAL,'999,990.00')||'Ã'||
                   NVL(UL.NOM_USU ||' '|| UL.APE_PAT ||' '|| UL.APE_MAT,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   cd.tip_moneda||cd.tip_dinero||cd.mon_moneda_origen||cd.serie_billete||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   PBL_USU_LOCAL UL,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    UL.COD_GRUPO_CIA = CD.COD_GRUPO_CIA
            AND    UL.COD_LOCAL = CD.COD_LOCAL
            AND    UL.COD_TRAB = CD.COD_TRAB;
       ELSIF(ccodcuadratura_in = C_C_OTROS_DESEMBOLSOS) THEN -- OTROS DESEMBOLSOS
          OPEN  curCe FOR
            SELECT DECODE(CD.TIP_DOCUMENTO,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA','05','TICKET')||'Ã'||
                   NVL(cd.num_serie_documento ||' - '|| CD.Num_Documento,' ')||'Ã'||
                   DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   nvl(to_char(CD.FEC_EMISION,'dd/MM/yyyy'),' ')||'Ã'||
                   nvl(to_char(CD.FEC_OPERACION,'dd/MM/yyyy HH24:MI:SS'),' ')||'Ã'||
                   NVL(CD.DESC_RUC,' ')||'Ã'||
                   NVL(CD.DESC_RAZON_SOCIAL,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy');
       ELSIF(ccodcuadratura_in = C_C_FONDO_SENCILLO) THEN -- FONDO SENCILLO LOCAL NUEVO
          OPEN  curCe FOR
            SELECT DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   L.DESC_CORTA_LOCAL||'Ã'||
                   CD.COD_AUTORIZACION||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   PBL_LOCAL L
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND    CD.COD_LOCAL_NUEVO = L.COD_LOCAL;
       ELSIF(ccodcuadratura_in = C_C_DSCT_PERSONAL) THEN -- DESCEUNTO PERSONAL
          OPEN  curCe FOR
            SELECT DECODE(NVL(CD.TIP_COMP,' '),C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA','05','TICKET')||'Ã'||
                   --NVL(cd.num_serie_local||CD.NUM_COMP_PAGO,' ')||'Ã'||
                   NVL(CD.NUM_PED_VTA,' ')||'Ã'||
                   DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES',TIP_MONEDA_DOLARES,'DOLARES')||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   TO_CHAR(CD.MON_PARCIAL,'999,990.00')||'Ã'||
                   NVL(UL.NOM_USU ||' '|| UL.APE_PAT ||' '|| UL.APE_MAT,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   NVL(CD.TIP_COMP,' ')||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   PBL_USU_LOCAL UL,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            --AND    UL.EST_USU  = ESTADO_ACTIVO
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    UL.COD_GRUPO_CIA = CD.COD_GRUPO_CIA
            AND    UL.COD_LOCAL = CD.COD_LOCAL
            AND    UL.COD_TRAB = CD.COD_TRAB;
       ELSIF(ccodcuadratura_in = C_C_ADELANTO) THEN -- ADELANTO
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   MT.NOM_TRAB ||' '|| MT.APE_PAT_TRAB ||' '|| NVL(MT.APE_MAT_TRAB,0)||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_MAE_TRAB MT,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_TRAB = MT.COD_TRAB
            AND    cd.cod_cia = mt.cod_cia;
       ELSIF(ccodcuadratura_in = C_C_GRATIFICACION) THEN -- gratificacion
          OPEN  curCe FOR
            SELECT NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.Mon_Total,'999,990.00')||'Ã'||
                   MT.NOM_TRAB ||' '|| MT.APE_PAT_TRAB ||' '|| NVL(MT.APE_MAT_TRAB,0)||'Ã'||
                   nvl(CD.COD_AUTORIZACION,' ')||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_MAE_TRAB MT,
                   CE_CUADRATURA_CIERRE_DIA CD
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_TRAB = MT.COD_TRAB
            AND    cd.cod_cia = mt.cod_cia;
       ELSIF(ccodcuadratura_in = C_C_DELIVERY_PERDIDO) THEN -- DELIVERY PERDIDO
          OPEN  curCe FOR
            SELECT DECODE(NVL(CD.TIP_COMP,' '),C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA','05','TICKET')||'Ã'||
                   NVL(cd.num_serie_local||CD.NUM_COMP_PAGO,' ')||'Ã'||
                   NVL(DECODE(CD.TIP_MONEDA,TIP_MONEDA_SOLES,'SOLES','DOLARES'),' ') ||'Ã'||
                   TO_CHAR(CD.MON_MONEDA_ORIGEN,'999,990.00')||'Ã'||
                   TO_CHAR(CD.MON_TOTAL,'999,990.00')||'Ã'||
                   TO_CHAR(CD.MON_PERDIDO_TOTAL ,'999,990.00')||'Ã'||
                   p.desc_corta_proveedor ||'Ã'||
                   nvl(cd.desc_motivo,' ') ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   ' ' ||'Ã'||
                   CD.SEC_CUADRATURA_CIERRE_DIA||'Ã'||
                   TO_CHAR(CD.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy')
            FROM   CE_CUADRATURA_CIERRE_DIA CD,
                   ce_proveedor p
            WHERE  CD.COD_GRUPO_CIA = CCODGRUPOCIA_IN
            AND    CD.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND    CD.COD_LOCAL = CCODLLOCAL_IN
            AND    CD.COD_CUADRATURA = CCODCUADRATURA_IN
            AND    CD.FEC_CIERRE_DIA_VTA = to_date(CFECHACD_IN,'dd/MM/yyyy')
            AND    CD.COD_PROVEEDOR = P.COD_PROVEEDOR;
       END IF;
    RETURN curce;
  END;

/*****************************************************************************************************************************************/

FUNCTION CE_CONSO_EFEC_RENDIDO_CIERRE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in	  IN CHAR,
                                        cCierreDia_in   IN CHAR)
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
  BEGIN
    OPEN curCE FOR
        SELECT CUADRATURA_CIERRE_DIA.COD_CUADRATURA || 'Ã' ||
               NVL(CUADRATURA.DESC_CUADRATURA,' ') || 'Ã' ||
               --TO_CHAR(SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL) ),'999,999,990.00')
               TO_CHAR(DECODE(CUADRATURA.COD_CUADRATURA,COD_CUADRATURA_DEL_PERDIDO,SUM(CUADRATURA_CIERRE_DIA.MON_PERDIDO_TOTAL),SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL * DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)))),'999,999,990.00')
               --TO_CHAR(SUM( DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL) * DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,TIP_MONEDA_DOLARES,DV.TIP_CAMBIO_CIERRE_DIA,1)),'999,999,990.00')
        FROM   CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
               CE_CUADRATURA CUADRATURA,
               CE_CIERRE_DIA_VENTA DV
        WHERE  CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CUADRATURA_CIERRE_DIA.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
        AND    CUADRATURA_CIERRE_DIA.COD_LOCAL = cCodLocal_in
        AND    CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy')
        AND    CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
        AND    CUADRATURA_CIERRE_DIA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
        AND    DV.COD_GRUPO_CIA = CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA
        AND    DV.COD_LOCAL = CUADRATURA_CIERRE_DIA.COD_LOCAL
        AND    DV.FEC_CIERRE_DIA_VTA = CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA
        GROUP BY CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                 CUADRATURA.DESC_CUADRATURA,
                 CUADRATURA.COD_CUADRATURA
        union
        --SELECT V.COD_ETV ||'Ã'||
        --       TRIM(V.NOMBRE_ETV) ||'Ã'||
        --       TRIM(TO_CHAR(V.MONT_SOBRES,'999,999,990.00'))
        --FROM   DUAL,
        --       (
        --       SELECT E.COD_ETV,
        --              E.NOMBRE_ETV,
        --              nvl(sum(CF.MON_ENTREGA_TOTAL),0) MONT_SOBRES
        --       FROM   CE_SOBRE S
        --        INNER JOIN CE_FORMA_PAGO_ENTREGA CF ON S.COD_GRUPO_CIA = CF.COD_GRUPO_CIA AND
        --                                               S.COD_LOCAL = CF.COD_LOCAL AND
        --                                               S.SEC_MOV_CAJA = CF.SEC_MOV_CAJA AND
        --                                               S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA
        --        left JOIN CE_REMITO R ON S.COD_REMITO = R.COD_REMITO
        --        left JOIN PBL_ETV E ON R.COD_PBL_ETV = E.COD_ETV
        --        WHERE  CF.COD_GRUPO_CIA = '001'
        --        AND    CF.COD_LOCAL     = '071'
        --        AND    CF.EST_FORMA_PAGO_ENT = 'A'
        --        AND    S.FEC_DIA_VTA    = to_date('22/01/2014','dd/MM/yyyy')
        --        group by E.COD_ETV, E.NOMBRE_ETV
        --        ) V
        --WHERE   V.MONT_SOBRES > 0;
        SELECT '011' ||'Ã'||
               'SOBRES A PORTAVALOR'||'Ã'||
               TO_CHAR(V.MONT_SOBRES,'999,999,990.00')
        FROM   DUAL,
               (
               SELECT nvl(sum(CF.MON_ENTREGA_TOTAL),0) MONT_SOBRES
                FROM   CE_SOBRE S,
                       CE_FORMA_PAGO_ENTREGA CF,
                       PBL_LOCAL LOC
                WHERE  CF.COD_GRUPO_CIA = cCodGrupoCia_in
                AND    CF.COD_LOCAL     = cCodLocal_in
                AND    CF.EST_FORMA_PAGO_ENT = 'A'
                AND    S.FEC_DIA_VTA    = to_date(cCierreDia_in,'dd/MM/yyyy')
                AND    S.COD_GRUPO_CIA = CF.COD_GRUPO_CIA
                AND    S.COD_LOCAL = CF.COD_LOCAL
                AND    S.SEC_MOV_CAJA = CF.SEC_MOV_CAJA
                AND    S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA
                AND    S.COD_LOCAL=LOC.COD_LOCAL
                --AND    LOC.IND_PROSEGUR='S'
                AND NVL(S.IND_ETV,LOC.IND_PROSEGUR)='S'
                ) V
        WHERE   V.MONT_SOBRES > 0;



    RETURN curCE;
  END;

/*****************************************************************************************************************************************/

FUNCTION CE_F_VALIDAR_MONTO_SD(cCodCia_in IN CHAR,
                               cCodLocal_in IN CHAR,
                               cCierreDia_in IN CHAR)
RETURN CHAR
IS
montoRec NUMBER(9,2):=0;
montoRen NUMBER(9,2):=0;
/*montoRec VARCHAR2(30):='';
montoRen VARCHAR2(30):='';*/
diferencia NUMBER(9,2):=0;
INDICADOR CHAR(1); --1 MAS RENDIDO, 2 MAS RECAUDADO, 3 IGUALES
MONTO_PROSEGUR NUMBER(9,2):=0; --ASOSA, 14.06.2010
IND_PROSEGUR CHAR(1):='N'; --ASOSA, 14.06.2010
CURSOR CUR01 IS
       SELECT NVL(SUM(C3.MONTO), 0) PLATA
        FROM
             (SELECT CUADRATURA_CIERRE_DIA.COD_CUADRATURA CODIGO,
                     DECODE(CUADRATURA.COD_CUADRATURA,
                            '023',
                            SUM(CUADRATURA_CIERRE_DIA.MON_PERDIDO_TOTAL),
                            SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL,
                                       CUADRATURA_CIERRE_DIA.MON_TOTAL,
                                       CUADRATURA_CIERRE_DIA.MON_TOTAL,
                                       CUADRATURA_CIERRE_DIA.MON_PARCIAL *
                                       DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,
                                              '02',
                                              DV.TIP_CAMBIO_CIERRE_DIA,
                                              1)))) MONTO
                FROM CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
                     CE_CUADRATURA            CUADRATURA,
                     CE_CIERRE_DIA_VENTA      DV
               WHERE CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = '001'
                 AND CUADRATURA_CIERRE_DIA.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                 AND CUADRATURA_CIERRE_DIA.COD_LOCAL = cCodLocal_in
                 /*AND CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA =
                     TO_DATE('11/06/2010', 'dd/MM/yyyy')*/
                 AND DV.Fec_Cierre_Dia_Vta =              --ASOSA, 14.06.2010
                     TO_DATE(cCierreDia_in, 'dd/MM/yyyy')
                 AND CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA =
                     CUADRATURA.COD_GRUPO_CIA
                 AND CUADRATURA_CIERRE_DIA.COD_CUADRATURA =
                     CUADRATURA.COD_CUADRATURA
                 AND DV.COD_GRUPO_CIA = CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA
                 AND DV.COD_LOCAL = CUADRATURA_CIERRE_DIA.COD_LOCAL
                 AND DV.FEC_CIERRE_DIA_VTA =
                     CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA
                 AND CUADRATURA_CIERRE_DIA.COD_CUADRATURA IN (SELECT A.COD_CUADRATURA FROM CE_CAMPOS_CUADRATURA A WHERE A.COD_CAMPO='001')
                 AND CUADRATURA_CIERRE_DIA.COD_FORMA_PAGO IN ('00001','00002')
               GROUP BY CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                        CUADRATURA.DESC_CUADRATURA,
                        CUADRATURA.COD_CUADRATURA) C3
      UNION ALL
      SELECT NVL(SUM(C4.MONTO), 0) PLATA
        FROM (SELECT CUADRATURA_CIERRE_DIA.COD_CUADRATURA CODIGO,
                     DECODE(CUADRATURA.COD_CUADRATURA,
                            '023',
                            SUM(CUADRATURA_CIERRE_DIA.MON_PERDIDO_TOTAL),
                            SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL,
                                       CUADRATURA_CIERRE_DIA.MON_TOTAL,
                                       CUADRATURA_CIERRE_DIA.MON_TOTAL,
                                       CUADRATURA_CIERRE_DIA.MON_PARCIAL *
                                       DECODE(CUADRATURA_CIERRE_DIA.TIP_MONEDA,
                                              '02',
                                              DV.TIP_CAMBIO_CIERRE_DIA,
                                              1)))) MONTO
                FROM CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
                     CE_CUADRATURA            CUADRATURA,
                     CE_CIERRE_DIA_VENTA      DV
               WHERE CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = '001'
                 AND CUADRATURA_CIERRE_DIA.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                 AND CUADRATURA_CIERRE_DIA.COD_LOCAL = cCodLocal_in
                 /*AND CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA =
                     TO_DATE('11/06/2010', 'dd/MM/yyyy')*/
                 AND DV.Fec_Cierre_Dia_Vta =              --ASOSA, 14.06.2010
                     TO_DATE(cCierreDia_in, 'dd/MM/yyyy')
                 AND CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA =
                     CUADRATURA.COD_GRUPO_CIA
                 AND CUADRATURA_CIERRE_DIA.COD_CUADRATURA =
                     CUADRATURA.COD_CUADRATURA
                 AND DV.COD_GRUPO_CIA = CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA
                 AND DV.COD_LOCAL = CUADRATURA_CIERRE_DIA.COD_LOCAL
                 AND DV.FEC_CIERRE_DIA_VTA =
                     CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA
                 AND CUADRATURA_CIERRE_DIA.COD_CUADRATURA NOT IN (SELECT A.COD_CUADRATURA FROM CE_CAMPOS_CUADRATURA A WHERE A.COD_CAMPO='001')
               GROUP BY CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                        CUADRATURA.DESC_CUADRATURA,
                        CUADRATURA.COD_CUADRATURA) C4;
FILA CUR01%ROWTYPE;

BEGIN
     SELECT SUM(C1.MON_ENTREGA_TOTAL) INTO montoRec
     FROM CE_FORMA_PAGO_ENTREGA C1,
          VTA_FORMA_PAGO C2
     WHERE C1.COD_GRUPO_CIA=cCodCia_in
     AND C1.COD_LOCAL=cCodLocal_in
     AND C1.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA
                             FROM CE_MOV_CAJA A
                             WHERE A.COD_GRUPO_CIA=cCodCia_in
                             AND A.COD_LOCAL=cCodLocal_in
                             AND A.FEC_DIA_VTA=TO_DATE(cCierreDia_in,'dd/MM/yyyy')
                             AND A.TIP_MOV_CAJA=TIP_MOV_CIERRE)
     AND C1.COD_GRUPO_CIA=C2.COD_GRUPO_CIA
     AND C1.COD_FORMA_PAGO=C2.COD_FORMA_PAGO
     AND C1.EST_FORMA_PAGO_ENT=ESTADO_ACTIVO
     AND C2.IND_FORMA_PAGO_EFECTIVO=INDICADOR_SI
     AND C1.COD_FORMA_PAGO IN ('00001','00002');

     OPEN CUR01;
     LOOP
     FETCH CUR01 INTO FILA;
     EXIT WHEN CUR01%NOTFOUND;
          montoRen:=montoRen+FILA.PLATA;
     END LOOP;
     CLOSE CUR01;

     SELECT NVL(PP.IND_PROSEGUR,'N') INTO IND_PROSEGUR --INI - ASOSA, 14.06.2010
     FROM PBL_LOCAL PP
     WHERE PP.COD_LOCAL=cCodLocal_in
     AND PP.COD_GRUPO_CIA=cCodCia_in;
     IF IND_PROSEGUR='S' THEN
        SELECT V.MONT_SOBRES INTO MONTO_PROSEGUR
        FROM ( SELECT nvl(sum(CF.MON_ENTREGA_TOTAL),0) MONT_SOBRES
                FROM   CE_SOBRE S,
                       CE_FORMA_PAGO_ENTREGA CF,
                       PBL_LOCAL LOC
                WHERE  CF.COD_GRUPO_CIA = cCodCia_in
                AND    CF.COD_LOCAL     = cCodLocal_in
                AND    CF.EST_FORMA_PAGO_ENT = 'A'
                AND    S.FEC_DIA_VTA    = to_date(cCierreDia_in,'dd/MM/yyyy')
                AND    S.COD_GRUPO_CIA = CF.COD_GRUPO_CIA
                AND    S.COD_LOCAL = CF.COD_LOCAL
                AND    S.SEC_MOV_CAJA = CF.SEC_MOV_CAJA
                AND    S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA
                AND    S.COD_LOCAL=LOC.COD_LOCAL
                AND    NVL(S.Ind_Etv,LOC.IND_PROSEGUR)='S' --
                ) V
        WHERE   V.MONT_SOBRES > 0;
     END IF;
     --ESTO ES PORQUE EN CASO EL LOCAL SEA PROSEGUR SE VISUALIZA AUNQUE EN REALIDAD NO DEBENRIA, FUE UN REQUERIMIENTO
     montoRen:=montoRen+MONTO_PROSEGUR; --FIN - ASOSA, 14.06.2010

     diferencia:=montoRen-montoRec;
     dbms_output.put_line('recaudado: '||montoRec);
     dbms_output.put_line('rendido: '||montoRen);
     dbms_output.put_line('diferencia: '||diferencia);
     IF diferencia>0 THEN
        INDICADOR:='1';
     ELSIF diferencia<0 THEN
        INDICADOR:='2';
     ELSIF diferencia=0 THEN
        INDICADOR:='3';
     END IF;

     --Se anula la validacion xq falta aun estar
     --bien definida y no tiene caso estar
     --daubilluz 30/07/2010
     INDICADOR:='3';

RETURN INDICADOR;
END;

/*****************************************************************************************************************************************/

FUNCTION CE_LISTA_CUADRATURAS_AS(cCodGrupoCia_in	IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cFecCierre_in   IN CHAR)
  RETURN FarmaCursor
  AS
    curCe FarmaCursor;
    nMontoPorSobres number;
  BEGIN
    nMontoPorSobres := CE_F_NUM_MONTO_SOBRES_AS(cCodGrupoCia_in,
    		   						             cCodLocal_in ,
                                   cFecCierre_in);
    dbms_output.put_line(''||nMontoPorSobres);
    OPEN curCe FOR
         SELECT COD_CUADRATURA|| 'Ã' ||
                DESC_CUADRATURA|| 'Ã' ||
                TO_CHAR(decode(TRIM(COD_CUADRATURA),C_C_DEPOSITO_VENTA,NVL(V1.SUMA,0.00)+nMontoPorSobres,NVL(V1.SUMA,0.00)) ,'999,999,990.00')|| 'Ã' ||
                TIP_CUADRATURA
         FROM   CE_CUADRATURA,
                (SELECT CUADRATURA_CIERRE_DIA.COD_CUADRATURA CODIGO,
                        NVL(CUADRATURA.DESC_CUADRATURA,' ') DESCRIPCION,
                        SUM(DECODE(CUADRATURA_CIERRE_DIA.MON_PARCIAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_TOTAL, CUADRATURA_CIERRE_DIA.MON_PARCIAL) )SUMA
                 FROM   CE_CUADRATURA_CIERRE_DIA CUADRATURA_CIERRE_DIA,
                        CE_CUADRATURA CUADRATURA
                 WHERE  CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = ccodgrupocia_in
                 AND    CUADRATURA_CIERRE_DIA.EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                 AND    CUADRATURA_CIERRE_DIA.COD_LOCAL = ccodlocal_in
                 AND    CUADRATURA_CIERRE_DIA.FEC_CIERRE_DIA_VTA = TO_DATE(cFecCierre_in,'dd/MM/yyyy')
                 AND    CUADRATURA_CIERRE_DIA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
                 AND    CUADRATURA_CIERRE_DIA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
                 GROUP BY CUADRATURA_CIERRE_DIA.COD_CUADRATURA,
                          CUADRATURA.DESC_CUADRATURA)V1
         WHERE  COD_GRUPO_CIA = ccodgrupocia_in
         AND EST_CUADRATURA = ESTADO_ACTIVO
         AND TIP_CUADRATURA IN ('03','04')
         AND COD_CUADRATURA = V1.CODIGO(+);
    RETURN curCe;
  END;

/*****************************************************************************************************************************************/

FUNCTION CE_F_NUM_MONTO_SOBRES_AS(cCodGrupoCia_in  IN CHAR,
  		   						             cCodLocal_in   IN CHAR,
                                 cFecha_in IN CHAR)
  RETURN NUMBER
  IS
    nMonto number := 0;
  BEGIN
    SELECT nvl(sum(CF.MON_ENTREGA_TOTAL),0) INTO nMonto
    FROM   CE_SOBRE S,
           CE_FORMA_PAGO_ENTREGA CF,
           PBL_LOCAL A
    WHERE  CF.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CF.COD_LOCAL     = cCodLocal_in
    AND    CF.EST_FORMA_PAGO_ENT = 'A'
--    AND    A.IND_PROSEGUR='S'
    AND    NVL(S.IND_ETV,A.IND_PROSEGUR)='S'
    AND    S.FEC_DIA_VTA    = to_date(cFecha_in,'dd/MM/yyyy')
    AND    CF.COD_GRUPO_CIA=A.COD_GRUPO_CIA
    AND    CF.COD_LOCAL=A.COD_LOCAL
    AND    S.COD_GRUPO_CIA  = CF.COD_GRUPO_CIA
    AND    S.COD_LOCAL      = CF.COD_LOCAL
    AND    S.SEC_MOV_CAJA   = CF.SEC_MOV_CAJA
    AND    S.SEC_FORMA_PAGO_ENTREGA = CF.SEC_FORMA_PAGO_ENTREGA;

    RETURN nMonto;

  END CE_F_NUM_MONTO_SOBRES_AS;

/*****************************************************************************************************************************************/

/*****************************************************************************************************************************************/
/*****JQUISPE 03.06.2010 SE MDIFICO PARA EL TEMA DE CAMBIO DE FORMA DE PAGO EN EL CIERRE DE DIA*****************************************/
   FUNCTION CE_CAMBIO_FORMA_PAGO(cCodGrupoCia_in  IN CHAR,
  		   						               cCodLocal_in    	IN CHAR,
								                   cNumPedDiario_in IN CHAR,
								                   cFecPedVta_in	  IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
  		 	SELECT VTA_CAB.NUM_PED_VTA || 'Ã' ||
    				   TO_CHAR(VTA_CAB.VAL_NETO_PED_VTA,'999,990.00') || 'Ã' ||
    				   TO_CHAR((VTA_CAB.VAL_NETO_PED_VTA / VTA_CAB.VAL_TIP_CAMBIO_PED_VTA) + DECODE(VTA_CAB.IND_DISTR_GRATUITA,'N',DECODE(VTA_CAB.VAL_NETO_PED_VTA,0,0,0.05),0.00),'999,990.00') || 'Ã' ||
    				   TO_CHAR(VTA_CAB.VAL_TIP_CAMBIO_PED_VTA,'990.00') || 'Ã' ||
    				   VTA_CAB.TIP_COMP_PAGO || 'Ã' ||
               DECODE(VTA_CAB.TIP_COMP_PAGO,COD_TIP_COMP_BOLETA,'BOLETA',COD_TIP_COMP_FACTURA,'FACTURA',COD_TIP_COMP_TICKET,'TICKET') || 'Ã' ||
    				   NVL(VTA_CAB.NOM_CLI_PED_VTA,' ') || 'Ã' ||
    				   NVL(VTA_CAB.RUC_CLI_PED_VTA,' ') || 'Ã' ||
    				   NVL(VTA_CAB.DIR_CLI_PED_VTA,' ') || 'Ã' ||
    				   VTA_CAB.TIP_PED_VTA || 'Ã' ||
    				   TO_CHAR(VTA_CAB.FEC_PED_VTA,'dd/MM/yyyy')	|| 'Ã' ||
    				   VTA_CAB.IND_DISTR_GRATUITA || 'Ã' ||
               VTA_CAB.IND_DELIV_AUTOMATICO || 'Ã' ||
               VTA_CAB.CANT_ITEMS_PED_VTA || 'Ã' ||
               VTA_CAB.IND_PED_CONVENIO || 'Ã' ||
               NVL(VC.COD_CONVENIO,' ') || 'Ã' ||
               nvl(VC.COD_CLI,' ') || 'Ã' ||
               (CASE
                  WHEN VTA_CAB.PCT_BENEFICIARIO IS NULL THEN -1
                  ELSE VTA_CAB.PCT_BENEFICIARIO END
               )
  			FROM   VTA_PEDIDO_VTA_CAB VTA_CAB,
               CON_PED_VTA_CLI VC
  			WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
  			AND	   VTA_CAB.COD_LOCAL = cCodLocal_in
        -------------CAMBIO DEL PEDIDO DIARIO POR PEDIDO VENTA
  			AND	   VTA_CAB.NUM_PED_VTA = cNumPedDiario_in
  			--AND	   TO_CHAR(VTA_CAB.FEC_PED_VTA,'dd/MM/yyyy') = DECODE(cFecPedVta_in,NULL,TO_CHAR(SYSDATE,'dd/MM/yyyy'),TO_CHAR(TRUNC(TO_DATE(cFecPedVta_in,'dd/MM/yyyy')),'dd/MM/yyyy'))
        AND   TRUNC(VTA_CAB.FEC_PED_VTA) = TRUNC(TO_DATE(NVL(cFecPedVta_in,SYSDATE),'dd/MM/YYYY hh24:mi:ss'))
        ---  	 AND	   VTA_CAB.EST_PED_VTA IN (EST_PED_PENDIENTE,EST_PED_COB_NO_IMPR)
        AND    VTA_CAB.COD_GRUPO_CIA = VC.COD_GRUPO_CIA(+)
        AND    VTA_CAB.COD_LOCAL = VC.COD_LOCAL(+)
        AND    VTA_CAB.NUM_PED_VTA = VC.NUM_PED_VTA(+);
    RETURN curCaj;
  END;


/*********************************************************************************************************************************/
  FUNCTION CE_LIST_DET_VENTAS (cCodGrupoCia_in	IN CHAR,
                               cCodLocal_in	  	IN CHAR,
                               cMovCaja         IN CHAR,
                               cNumPedVta_in    IN CHAR DEFAULT '%',
                               nMontoVta_in     IN NUMBER DEFAULT 0,
                               cTipoPago        IN CHAR DEFAULT '%')
 RETURN FarmaCursor
 IS
 curRep FarmaCursor;
 vMontoDif NUMBER;
 BEGIN
 	  dbms_output.put_line('cNumPedVta_in'||cNumPedVta_in);
    dbms_output.put_line('nMontoVta_in'||nMontoVta_in);

    IF (cNumPedVta_in IS NOT NULL) AND (nMontoVta_in IS NULL) THEN
    dbms_output.put_line('1');
    OPEN curRep FOR
 	  SELECT VPC.num_ped_vta || 'Ã' ||
		  	 	 DECODE(VPC.tip_comp_pago,01,' ',02,'F',03,'G',04,'N',05,'T') || 'Ã' ||
	  			 NVL(--SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7)
           Farma_Utility.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
					--FAC-ELECTRONICA :09.10.2014
                            ,' ') || 'Ã' ||
           TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
	 			   NVL(VPC.NOM_CLI_PED_VTA,' ') || 'Ã' ||
				   DECODE(VPC.IND_PEDIDO_ANUL,'S','ANUL.ORIG.',' ') || 'Ã' ||
				   TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,990.00')|| 'Ã' ||
           TRIM(VPC.NUM_PED_DIARIO)
		 FROM  VTA_PEDIDO_VTA_CAB VPC,
			 	    VTA_COMP_PAGO CP,
            VTA_FORMA_PAGO_PEDIDO C
			WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND   VPC.COD_LOCAL = cCodLocal_in
      AND  VPC.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN
                                FROM CE_MOV_CAJA A
                                WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                AND A.COD_LOCAL=cCodLocal_in
                                AND A.SEC_MOV_CAJA=cMovCaja
                                AND A.IND_VB_CAJERO='N')--solo movimientos si VB Cajero
      AND  C.COD_FORMA_PAGO IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ LIKE cTipoPago
                                AND A.EST_FORMA_PAGO='A')
      AND  C.COD_FORMA_PAGO NOT IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ='N'
                                AND A.EST_FORMA_PAGO='A'
                                AND A.COD_FORMA_PAGO NOT IN ('00001','00002'))
			--AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			--AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			AND   VPC.EST_PED_VTA = 'C'
			AND   vpc.cod_grupo_cia = cp.cod_grupo_cia
			AND   vpc.COD_LOCAL = cp.cod_local
			AND   VPC.NUM_PED_VTA=CP.NUM_PED_VTA
      AND  VPC.COD_GRUPO_CIA=C.COD_GRUPO_CIA
      AND  VPC.COD_LOCAL=C.COD_LOCAL
      AND  VPC.NUM_PED_VTA=C.NUM_PED_VTA
      AND  VPC.NUM_PED_VTA= cNumPedVta_in
      AND  VPC.TIP_PED_VTA='01'
      AND  VPC.IND_PED_CONVENIO='N'
      AND  VPC.IND_DELIV_AUTOMATICO='N'
			UNION
		SELECT  VPC.num_ped_vta || 'Ã' ||
			 	    DECODE(VPC.tip_comp_pago,01,' ',02,'F',03,'G',04,'N',05,'T') || 'Ã' ||
			 	    NVL(--SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7)
            Farma_Utility.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
					--FAC-ELECTRONICA :09.10.2014
                                          ,' ') || 'Ã' ||
			 	    TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
            NVL(VPC.NOM_CLI_PED_VTA,' ') || 'Ã' ||
			 	    DECODE(VPC.IND_PEDIDO_ANUL,'N','ANULADO',' ')	|| 'Ã' ||
				    TO_CHAR((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO) * -1,'999,990.00')|| 'Ã' ||
           TRIM(VPC.NUM_PED_DIARIO)
			FROM  VTA_PEDIDO_VTA_CAB VPC,
			 	    VTA_COMP_PAGO CP,
            VTA_FORMA_PAGO_PEDIDO C
			WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND   VPC.COD_LOCAL = cCodLocal_in
      AND  VPC.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN
                                FROM CE_MOV_CAJA A
                                WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                AND A.COD_LOCAL=cCodLocal_in
                                AND A.SEC_MOV_CAJA=cMovCaja
                                AND A.IND_VB_CAJERO='N')--solo movimientos si VB Cajero
      AND  C.COD_FORMA_PAGO IN (SELECT A.COD_FORMA_PAGO
                                 FROM VTA_FORMA_PAGO A
                                 WHERE A.IND_TARJ LIKE cTipoPago
                                 AND A.EST_FORMA_PAGO='A')
      AND  C.COD_FORMA_PAGO NOT IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ='N'
                                AND A.EST_FORMA_PAGO='A'
                                AND A.COD_FORMA_PAGO NOT IN ('00001','00002'))
      AND	  VPC.EST_PED_VTA= 'C'
			--AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			--AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			AND   VPC.NUM_PED_VTA=CP.NUM_PEDIDO_ANUL
     	AND	  vpc.cod_grupo_cia = cp.cod_grupo_cia
 			AND	  vpc.COD_LOCAL = cp.cod_local
      AND  VPC.COD_GRUPO_CIA=C.COD_GRUPO_CIA
      AND  VPC.COD_LOCAL=C.COD_LOCAL
      AND  VPC.NUM_PED_VTA=C.NUM_PED_VTA
      AND  VPC.NUM_PED_VTA= cNumPedVta_in
      AND  VPC.TIP_PED_VTA='01'
      AND  VPC.IND_PED_CONVENIO='N'
      AND  VPC.IND_DELIV_AUTOMATICO='N';

      END IF;

     IF (cNumPedVta_in IS NULL) AND (nMontoVta_in IS NOT NULL) THEN

     dbms_output.put_line('2');
     SELECT TO_NUMBER(T.llave_tab_gral)
     into vMontoDif
     from
     pbl_tab_gral t
     where
     t.id_tab_gral='367';

     OPEN curRep FOR
 	   SELECT VPC.num_ped_vta || 'Ã' ||
		  	 	 DECODE(VPC.tip_comp_pago,01,' ',02,'F',03,'G',04,'N',05,'T') || 'Ã' ||
	  			 NVL(--SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7)
           Farma_Utility.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
           					--FAC-ELECTRONICA :09.10.2014
                                         ,' ') || 'Ã' ||
           TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
	 			   NVL(VPC.NOM_CLI_PED_VTA,' ') || 'Ã' ||
				   DECODE(VPC.IND_PEDIDO_ANUL,'S','ANUL.ORIG.',' ') || 'Ã' ||
				   TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,990.00')|| 'Ã' ||
           TRIM(VPC.NUM_PED_DIARIO)
		 FROM  VTA_PEDIDO_VTA_CAB VPC,
			 	    VTA_COMP_PAGO CP,
            VTA_FORMA_PAGO_PEDIDO C
			WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND   VPC.COD_LOCAL = cCodLocal_in
      AND  VPC.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN
                                FROM CE_MOV_CAJA A
                                WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                AND A.COD_LOCAL=cCodLocal_in
                                AND A.SEC_MOV_CAJA=cMovCaja
                                AND A.IND_VB_CAJERO='N')--solo movimientos si VB Cajero
      AND  C.COD_FORMA_PAGO IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ LIKE cTipoPago
                                AND A.EST_FORMA_PAGO='A')
      AND  C.COD_FORMA_PAGO NOT IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ='N'
                                AND A.EST_FORMA_PAGO='A'
                                AND A.COD_FORMA_PAGO NOT IN ('00001','00002'))
			--AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			--AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			AND   VPC.EST_PED_VTA = 'C'
			AND   vpc.cod_grupo_cia = cp.cod_grupo_cia
			AND   vpc.COD_LOCAL = cp.cod_local
			AND   VPC.NUM_PED_VTA=CP.NUM_PED_VTA
      AND  VPC.COD_GRUPO_CIA=C.COD_GRUPO_CIA
      AND  VPC.COD_LOCAL=C.COD_LOCAL
      AND  VPC.NUM_PED_VTA=C.NUM_PED_VTA
      AND  VPC.TIP_PED_VTA='01'
      AND  VPC.IND_PED_CONVENIO='N'
      AND  VPC.IND_DELIV_AUTOMATICO='N'
      AND  VPC.VAL_NETO_PED_VTA BETWEEN TO_NUMBER(nMontoVta_in)-vMontoDif
      AND TO_NUMBER(nMontoVta_in)+vMontoDif
			UNION
		SELECT  VPC.num_ped_vta || 'Ã' ||
			 	    DECODE(VPC.tip_comp_pago,01,' ',02,'F',03,'G',04,'N',05,'T') || 'Ã' ||
			 	    NVL(--SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7)
            Farma_Utility.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
					--FAC-ELECTRONICA :09.10.2014
                      ,' ') || 'Ã' ||
			 	    TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
            NVL(VPC.NOM_CLI_PED_VTA,' ') || 'Ã' ||
			 	    DECODE(VPC.IND_PEDIDO_ANUL,'N','ANULADO',' ')	|| 'Ã' ||
				    TO_CHAR((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO) * -1,'999,990.00')|| 'Ã' ||
           TRIM(VPC.NUM_PED_DIARIO)
			FROM  VTA_PEDIDO_VTA_CAB VPC,
			 	    VTA_COMP_PAGO CP,
            VTA_FORMA_PAGO_PEDIDO C
			WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND   VPC.COD_LOCAL = cCodLocal_in
      AND  VPC.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN
                                FROM CE_MOV_CAJA A
                                WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                AND A.COD_LOCAL=cCodLocal_in
                                AND A.SEC_MOV_CAJA=cMovCaja
                                AND A.IND_VB_CAJERO='N')--solo movimientos si VB Cajero
      AND  C.COD_FORMA_PAGO IN (SELECT A.COD_FORMA_PAGO
                                 FROM VTA_FORMA_PAGO A
                                 WHERE A.IND_TARJ LIKE cTipoPago
                                 AND A.EST_FORMA_PAGO='A')
      AND  C.COD_FORMA_PAGO NOT IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ='N'
                                AND A.EST_FORMA_PAGO='A'
                                AND A.COD_FORMA_PAGO NOT IN ('00001','00002'))
      AND	  VPC.EST_PED_VTA= 'C'
			--AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			--AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			AND   VPC.NUM_PED_VTA=CP.NUM_PEDIDO_ANUL
     	AND	  vpc.cod_grupo_cia = cp.cod_grupo_cia
 			AND	  vpc.COD_LOCAL = cp.cod_local
      AND  VPC.COD_GRUPO_CIA=C.COD_GRUPO_CIA
      AND  VPC.COD_LOCAL=C.COD_LOCAL
      AND  VPC.NUM_PED_VTA=C.NUM_PED_VTA
      AND  VPC.TIP_PED_VTA='01'
      AND  VPC.IND_PED_CONVENIO='N'
      AND  VPC.IND_DELIV_AUTOMATICO='N'
      AND  VPC.VAL_NETO_PED_VTA BETWEEN TO_NUMBER(nMontoVta_in)-vMontoDif
      AND TO_NUMBER(nMontoVta_in)+vMontoDif;


      END IF;


     IF (cNumPedVta_in IS NOT NULL) AND (nMontoVta_in IS NOT NULL) THEN
         dbms_output.put_line('3');
     OPEN curRep FOR
 	   SELECT VPC.num_ped_vta || 'Ã' ||
		  	 	 DECODE(VPC.tip_comp_pago,01,' ',02,'F',03,'G',04,'N',05,'T') || 'Ã' ||
	  			 NVL(--SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7)
           Farma_Utility.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
					--FAC-ELECTRONICA :09.10.2014
                                         ,' ') || 'Ã' ||
           TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
	 			   NVL(VPC.NOM_CLI_PED_VTA,' ') || 'Ã' ||
				   DECODE(VPC.IND_PEDIDO_ANUL,'S','ANUL.ORIG.',' ') || 'Ã' ||
				   TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,990.00')|| 'Ã' ||
           TRIM(VPC.NUM_PED_DIARIO)
		 FROM  VTA_PEDIDO_VTA_CAB VPC,
			 	    VTA_COMP_PAGO CP,
            VTA_FORMA_PAGO_PEDIDO C
			WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND   VPC.COD_LOCAL = cCodLocal_in
      AND  VPC.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN
                                FROM CE_MOV_CAJA A
                                WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                AND A.COD_LOCAL=cCodLocal_in
                                AND A.SEC_MOV_CAJA=cMovCaja
                                AND A.IND_VB_CAJERO='N')--solo movimientos si VB Cajero
      AND  C.COD_FORMA_PAGO IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ LIKE cTipoPago
                                AND A.EST_FORMA_PAGO='A')
      AND  C.COD_FORMA_PAGO NOT IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ='N'
                                AND A.EST_FORMA_PAGO='A'
                                AND A.COD_FORMA_PAGO NOT IN ('00001','00002'))
			--AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			--AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			AND   VPC.EST_PED_VTA = 'C'
			AND   vpc.cod_grupo_cia = cp.cod_grupo_cia
			AND   vpc.COD_LOCAL = cp.cod_local
			AND   VPC.NUM_PED_VTA=CP.NUM_PED_VTA
      AND  VPC.COD_GRUPO_CIA=C.COD_GRUPO_CIA
      AND  VPC.COD_LOCAL=C.COD_LOCAL
      AND  VPC.NUM_PED_VTA=C.NUM_PED_VTA
      AND  VPC.TIP_PED_VTA='01'
      AND  VPC.IND_PED_CONVENIO='N'
      AND  VPC.IND_DELIV_AUTOMATICO='N'
      AND  VPC.NUM_PED_VTA=cNumPedVta_in
      AND  VPC.VAL_NETO_PED_VTA = TO_NUMBER(nMontoVta_in)

			UNION
		SELECT  VPC.num_ped_vta || 'Ã' ||
			 	    DECODE(VPC.tip_comp_pago,01,' ',02,'F',03,'G',04,'N',05,'T') || 'Ã' ||
			 	    NVL(--SUBSTR(CP.NUM_COMP_PAGO,1,3)||'-'||SUBSTR(CP.NUM_COMP_PAGO,-7)
            Farma_Utility.GET_T_COMPROBANTE_2(CP.COD_TIP_PROC_PAGO,CP.NUM_COMP_PAGO_E,CP.NUM_COMP_PAGO)
					--FAC-ELECTRONICA :09.10.2014
                                          ,' ') || 'Ã' ||
			 	    TO_CHAR(VPC.FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
            NVL(VPC.NOM_CLI_PED_VTA,' ') || 'Ã' ||
			 	    DECODE(VPC.IND_PEDIDO_ANUL,'N','ANULADO',' ')	|| 'Ã' ||
				    TO_CHAR((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO) * -1,'999,990.00')|| 'Ã' ||
           TRIM(VPC.NUM_PED_DIARIO)
			FROM  VTA_PEDIDO_VTA_CAB VPC,
			 	    VTA_COMP_PAGO CP,
            VTA_FORMA_PAGO_PEDIDO C
			WHERE VPC.COD_GRUPO_CIA = cCodGrupoCia_in
			AND   VPC.COD_LOCAL = cCodLocal_in
      AND  VPC.SEC_MOV_CAJA IN (SELECT A.SEC_MOV_CAJA_ORIGEN
                                FROM CE_MOV_CAJA A
                                WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                AND A.COD_LOCAL=cCodLocal_in
                                AND A.SEC_MOV_CAJA=cMovCaja
                                AND A.IND_VB_CAJERO='N')--solo movimientos si VB Cajero
      AND  C.COD_FORMA_PAGO IN (SELECT A.COD_FORMA_PAGO
                                 FROM VTA_FORMA_PAGO A
                                 WHERE A.IND_TARJ LIKE cTipoPago
                                 AND A.EST_FORMA_PAGO='A')
      AND  C.COD_FORMA_PAGO NOT IN (SELECT A.COD_FORMA_PAGO
                                FROM VTA_FORMA_PAGO A
                                WHERE A.IND_TARJ='N'
                                AND A.EST_FORMA_PAGO='A'
                                AND A.COD_FORMA_PAGO NOT IN ('00001','00002'))
      AND	  VPC.EST_PED_VTA= 'C'
			--AND   VPC.FEC_PED_VTA BETWEEN TO_DATE(cFechaInicio || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
			--AND   TO_DATE(cFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
			AND   VPC.NUM_PED_VTA=CP.NUM_PEDIDO_ANUL
     	AND	  vpc.cod_grupo_cia = cp.cod_grupo_cia
 			AND	  vpc.COD_LOCAL = cp.cod_local
      AND  VPC.COD_GRUPO_CIA=C.COD_GRUPO_CIA
      AND  VPC.COD_LOCAL=C.COD_LOCAL
      AND  VPC.NUM_PED_VTA=C.NUM_PED_VTA
      AND  VPC.TIP_PED_VTA='01'
      AND  VPC.IND_PED_CONVENIO='N'
      AND  VPC.IND_DELIV_AUTOMATICO='N'
      AND  VPC.NUM_PED_VTA=cNumPedVta_in
      AND  VPC.VAL_NETO_PED_VTA = TO_NUMBER(nMontoVta_in);
      END IF;

 RETURN curRep;
 END;

 FUNCTION CE_P_VERIFICAR_LONGITUD (  cCodGrupoCia_in  IN CHAR,
  		   						                 cCodLocal_in    	IN CHAR,
								                     cNumTarjeta      IN CHAR
								                     )
  RETURN CHAR
  IS
    v_minTarj NUMBER;
    v_maxTarj NUMBER;
    v_retorno CHAR := 'N';
    v_cont number;
  BEGIN

    select to_number(t1.llave_tab_gral) into v_minTarj
    from pbl_tab_gral t1 where t1.id_tab_gral  = '368';

    select to_number(t2.llave_tab_gral) into v_maxTarj
    from pbl_tab_gral t2 where t2.id_tab_gral  = '369';

    select count(1) into v_cont
    from  dual
    where length(cNumTarjeta)
    between v_minTarj and v_maxTarj;

    if v_cont > 0 then
       v_retorno := 'S';
    end if;

    return v_retorno;
  END;
/* *********************************************************************************** */

FUNCTION CE_OBTIENE_FORMAS_PAGO_AS(cCodGrupoCia_in  IN CHAR,
  		   						               cCodLocal_in    	IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
    vIndActFondo CHAR(1) := 'N';
  BEGIN
   BEGIN
    SELECT LLAVE_TAB_GRAL INTO vIndActFondo FROM PBL_TAB_GRAL
     WHERE ID_TAB_GRAL = 341;
   EXCEPTION
    WHEN no_data_found THEN
     vIndActFondo := 'N';
   END;
   --ERIOS 13.11.2015 Se agrega filtro por EST_FORMA_PAGO
   IF(vIndActFondo = 'N') THEN
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
			AND	   FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
			AND	   FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
			AND	   FORMA_PAGO.EST_FORMA_PAGO = ESTADO_ACTIVO
			AND	   FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
			AND	   FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      order by 1 desc;

    ELSE
      OPEN curCaj FOR
      SELECT COD_FORMA_PAGO || 'Ã' ||
             DESC_CORTA_FORMA_PAGO || 'Ã' ||
             COD_OPE_TARJ || 'Ã' ||
             IND_TARJ || 'Ã' ||
             IND_FORMA_PAGO_CUPON || 'Ã' ||
             Cod_Tip_Deposito
      FROM
      (
      SELECT FORMA_PAGO.COD_FORMA_PAGO,
				     FORMA_PAGO.DESC_CORTA_FORMA_PAGO,
				     NVL(FORMA_PAGO.COD_OPE_TARJ,' ') COD_OPE_TARJ,
             NVL(FORMA_PAGO.IND_TARJ,'N') IND_TARJ,
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N') IND_FORMA_PAGO_CUPON,
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ') Cod_Tip_Deposito
			FROM   VTA_FORMA_PAGO FORMA_PAGO,
				     VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
			WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
			AND	   FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
			AND	   FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = ESTADO_ACTIVO
			AND	   FORMA_PAGO.EST_FORMA_PAGO = ESTADO_ACTIVO
			AND	   FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
			AND	   FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      UNION
      SELECT FORMA_PAGO.COD_FORMA_PAGO,
				     FORMA_PAGO.DESC_CORTA_FORMA_PAGO,
				     NVL(FORMA_PAGO.COD_OPE_TARJ,' ') COD_OPE_TARJ,
             NVL(FORMA_PAGO.IND_TARJ,'N') IND_TARJ,
             NVL(FORMA_PAGO.IND_FORMA_PAGO_CUPON,'N') IND_FORMA_PAGO_CUPON,
             NVL(FORMA_PAGO.Cod_Tip_Deposito,' ') Cod_Tip_Deposito
      FROM   VTA_FORMA_PAGO FORMA_PAGO,
				     VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
			WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
			AND	   FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
			AND	   FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
			AND	   FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
      AND    FORMA_PAGO.COD_FORMA_PAGO = '00060'
      ) V1
      ORDER BY decode(V1.COD_FORMA_PAGO,'00060','00002A',V1.COD_FORMA_PAGO);
    END IF;

      RETURN curCaj;
  END CE_OBTIENE_FORMAS_PAGO_AS;
  /* ***************************************************************************** */
PROCEDURE CE_P_INS_SOBRES_AUTOMATICO(cCodCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecMovCaja_in IN CHAR,
                                     cIdUsu_in IN CHAR)
AS
cursor01 FarmaCursor := CAJ_F_CUR_SOBRES_ENTREGA_AS(cCodCia_in,
                                            cCodLocal_in,
                                            cSecMovCaja_in);
--FILA LIST%ROWTYPE;
secFormPagoEntrega VARCHAR2(200);
COD_FORMA_PAGO CHAR(5);
DESC_CORTA_FORMA_PAGO VARCHAR2(120);
CANT_VOUCHER NUMBER(3);
MONEDA VARCHAR2(20);
MON_ENTREGA VARCHAR2(12);--NUMBER(9,3);
MON_ENTREGA_TOTAL varchar2(12);--NUMBER(9,3);
NUM_LOTE CHAR(10);
IND_SOBRE CHAR(1);
COD_SOBRE VARCHAR2(20);
SEC_MOV_CAJA CHAR(10);
TIP_MONEDA CHAR(2);
IND_SI CHAR(1);
IND_FORMA_PAGO_AUTOMATICO_CE CHAR(1);
SEC NUMBER(10);
seccc CHAR(10);
BEGIN
dbms_output.put_line('aaaaaaaaaaaaaaaaaaaaaaaaa');

SELECT a.sec_mov_caja INTO seccc --ini ASOSA, 11.06.2010
FROM ce_mov_caja a
WHERE a.cod_grupo_cia=cCodCia_in
AND a.cod_local=cCodLocal_in
AND a.sec_mov_caja=cSecMovCaja_in FOR UPDATE; --fin ASOSA, 11.06.2010

  LOOP
       FETCH cursor01 INTO COD_FORMA_PAGO,
                           DESC_CORTA_FORMA_PAGO,
                           CANT_VOUCHER,
                           MONEDA,
                           MON_ENTREGA,
                           MON_ENTREGA_TOTAL,
                           NUM_LOTE,
                           IND_SOBRE,
                           COD_SOBRE,
                           SEC_MOV_CAJA,
                           TIP_MONEDA,
                           IND_SI,
                           IND_FORMA_PAGO_AUTOMATICO_CE,
                           SEC;
       EXIT WHEN cursor01%NOTFOUND;
       dbms_output.put_line('MON_ENTREGA_TOTAL: '||MON_ENTREGA_TOTAL);
           secFormPagoEntrega := PTOVENTA_CE.CE_GRABA_FORMA_PAGO_ENTREGA(cCodCia_in,
                    		   						                    cCodLocal_in,
                                                          SEC_MOV_CAJA,
                                                          COD_FORMA_PAGO,
                                                          CANT_VOUCHER,
                                                          TIP_MONEDA,
                                                          to_number(MON_ENTREGA,'999,990.00'),
                                                          to_number(MON_ENTREGA_TOTAL,'999,990.00'),
                                                          cIdUsu_in,
                                                          NUM_LOTE);
         dbms_output.put_line('secFormPagoEntrega; '||secFormPagoEntrega);
         PTOVENTA_CE_SEGURIDAD.SEG_P_INSERT_SOBRE(cCodCia_in,
                                                 cCodLocal_in,
                                                 SEC_MOV_CAJA,
                                                 secFormPagoEntrega,
                                                 cIdUsu_in,
                                                 COD_SOBRE);
  END LOOP;
END;
/* ****************************************************************************** */
FUNCTION CE_F_LISTA_SOBRES_PARCIALES(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        secUsu_in       IN CHAR)
  RETURN FarmaCursor IS
  lista FarmaCursor;
  cantC NUMBER(3);
  cantA NUMBER(3);
  cantO NUMBER(3);
  BEGIN
    SELECT COUNT(*) INTO cantC
    FROM pbl_rol_usu q
    WHERE q.sec_usu_local=secUsu_in
    AND q.cod_rol='009'
    AND q.cod_grupo_cia=cCodGrupoCia_in
    AND q.cod_local=cCodLocal_in;

    SELECT COUNT(*) INTO cantA
    FROM pbl_rol_usu q
    WHERE q.sec_usu_local=secUsu_in
    AND q.cod_rol='011'
    AND q.cod_grupo_cia=cCodGrupoCia_in
    AND q.cod_local=cCodLocal_in;

    SELECT COUNT(*) INTO cantO
    FROM pbl_rol_usu q
    WHERE q.sec_usu_local=secUsu_in
    AND q.cod_rol='000'
    AND q.cod_grupo_cia=cCodGrupoCia_in
    AND q.cod_local=cCodLocal_in;

    IF cantA>0 OR cantO>0 THEN
    OPEN lista FOR
         SELECT to_char(a.fec_crea_sobre,'dd/MM/yyyy') || 'Ã' ||
                to_char(a.fec_crea_sobre,'HH24:MI:SS') || 'Ã' ||
                c.nom_usu || 'Ã' ||
                a.cod_sobre || 'Ã' ||
                b.num_caja_pago || 'Ã' ||
                b.num_turno_caja || 'Ã' ||
                nvl2(a.tip_moneda,decode(a.tip_moneda,'01','SOLES','02','DOLARES'),'NO HAY') || 'Ã' ||
                to_char(a.mon_entrega,'999,990.00') || 'Ã' ||
                to_char(a.mon_entrega_total,'999,990.00') || 'Ã' ||
                a.sec || 'Ã' ||
                a.sec_mov_caja || 'Ã' ||
                a.estado
         FROM ce_sobre_tmp a,
              ce_mov_caja b,
              pbl_usu_local c
         WHERE a.sec_mov_caja=b.sec_mov_caja
         AND a.cod_grupo_cia=b.cod_grupo_cia
         AND a.cod_local=b.cod_local
         AND b.cod_grupo_cia=c.cod_grupo_cia
         AND b.cod_local=c.cod_local
         AND b.sec_usu_local=c.sec_usu_local
         AND c.login_usu=a.usu_crea_sobre
         AND a.cod_grupo_cia=cCodGrupoCia_in
         AND a.cod_local=cCodLocal_in
         --and a.cod_remito is not null
         --AND b.sec_usu_local=secUsu_in
         AND NOT EXISTS(SELECT 1
                        FROM ce_sobre x
                        WHERE x.cod_sobre=a.cod_sobre
                        and  x.cod_grupo_cia = a.cod_grupo_cia
                        and  x.cod_local = a.cod_local)
         AND a.estado <> 'I'
         ORDER BY a.fec_crea_sobre DESC;
    ELSIF cantC >0 THEN
    OPEN lista FOR
         SELECT to_char(a.fec_crea_sobre,'dd/MM/yyyy') || 'Ã' ||
                to_char(a.fec_crea_sobre,'HH24:MI:SS') || 'Ã' ||
                c.nom_usu || 'Ã' ||
                a.cod_sobre || 'Ã' ||
                b.num_caja_pago || 'Ã' ||
                b.num_turno_caja || 'Ã' ||
                nvl2(a.tip_moneda,decode(a.tip_moneda,'01','SOLES','02','DOLARES'),'NO HAY') || 'Ã' ||
                to_char(a.mon_entrega,'999,990.00') || 'Ã' ||
                to_char(a.mon_entrega_total,'999,990.00') || 'Ã' ||
                a.sec || 'Ã' ||
                a.sec_mov_caja || 'Ã' ||
                a.estado
         FROM ce_sobre_tmp a,
              ce_mov_caja b,
              pbl_usu_local c
         WHERE a.sec_mov_caja=b.sec_mov_caja
         AND a.cod_grupo_cia=b.cod_grupo_cia
         AND a.cod_local=b.cod_local
         AND b.cod_grupo_cia=c.cod_grupo_cia
         AND b.cod_local=c.cod_local
         AND b.sec_usu_local=c.sec_usu_local
         AND c.login_usu=a.usu_crea_sobre
         AND a.cod_grupo_cia=cCodGrupoCia_in
         AND a.cod_local=cCodLocal_in
         AND b.sec_usu_local=secUsu_in
        -- and a.cod_remito is not null
         AND NOT EXISTS(SELECT 1
                        FROM ce_sobre x
                        WHERE x.cod_sobre=a.cod_sobre
                        and   x.cod_grupo_cia = a.cod_grupo_cia
                        and   x.cod_local = a.cod_local)
         AND a.estado <> 'I'
         ORDER BY a.fec_crea_sobre DESC;
    END IF;
    return lista;
    CLOSE lista;
  end;
/* ********************************************************************** */
FUNCTION CAJ_F_CUR_SOBRES_ENTREGA_AS(cCodGrupoCia_in IN CHAR,
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
      SELECT A.COD_FORMA_PAGO COD_FORMA_PAGO,
             Y.DESC_CORTA_FORMA_PAGO DESC_CORTA_FORMA_PAGO,
             0 CANT_VOUCHER,
             CASE WHEN A.TIP_MONEDA= '01' THEN 'SOLES'
                    WHEN A.TIP_MONEDA= '02' THEN 'DOLARES' END MONEDA,
                    TO_char(A.MON_ENTREGA,'999,990.00') MON_ENTREGA,
                    TO_char(A.MON_ENTREGA_TOTAL,'999,990.00') MON_ENTREGA_TOTAL,
                    ' ' NUM_LOTE,
                    'S' IND_SOBRE,
                    A.COD_SOBRE COD_SOBRE,
                    cSecMovCaja_in SEC_MOV_CAJA,
                    A.TIP_MONEDA TIP_MONEDA,
                    'N' IND_SI,
                    'N' IND_FORMA_PAGO_AUTOMATICO_CE,
                    A.SEC
      FROM CE_SOBRE_TMP A,
           VTA_FORMA_PAGO Y
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCodLocal_in
      AND A.SEC_MOV_CAJA=SecMovCaja--apertura con el que se registraron los sobres
      AND A.ESTADO IN ('P','A')
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

  /************************************************************************************************/

  FUNCTION CE_LISTA_COT_COMPET_TURNO(cCodGrupoCia_in	IN CHAR,
                                    cCodLocal_in	  IN CHAR,
                                    codCuadra_in IN VARCHAR)
  RETURN FarmaCursor
  AS
    curCe FarmaCursor;
  BEGIN
    OPEN curCe FOR
		--ERIOS 2.4.6 Se muestra tipo documento
         SELECT --DECODE(C.tip_doc,C_C_IND_TIP_COMP_PAGO_BOL,'BOLETA',C_C_IND_TIP_COMP_PAGO_FACT,'FACTURA','03','GUIA','04','NOTA DE CREDITO',C_C_IND_TIP_COMP_PAGO_TICKET,'TICKET')||'Ã'||
                T.DESC_COMP||'Ã'||
                c.num_doc||'Ã'||
                to_char(c.val_total_nota_es_cab,'999,990.00')||'Ã'||
                c.num_nota_es ||'Ã'||
                to_char(c.fec_nota_es_cab,'dd/MM/yyyy')||'Ã'||
                nvl(c.desc_empresa,' ') ||'Ã'||
                c.tip_doc
         FROM   lgt_nota_es_cab c JOIN VTA_TIP_COMP T ON (T.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                                          AND T.TIP_COMP = C.TIP_DOC)
         WHERE  c.cod_grupo_cia = ccodgrupocia_in
         AND    c.cod_local = ccodlocal_in
         AND    c.tip_origen_nota_es = C_C_TIPO_GUIA_COMPETENCIA
         AND    c.tip_nota_es = C_C_TIPO_INGRESO
         AND    c.fec_proceso_ce IS NULL
         AND    C.EST_NOTA_ES_CAB = ESTADO_ACTIVO
         AND    (c.num_nota_es,
                 to_char(c.val_total_nota_es_cab,'999,990.00'))
         NOT IN  (SELECT D.COT_NUM_NOTA_ES,
                         to_char(d.mon_moneda_origen,'999,990.00')
                  FROM   Ce_Cuadratura_Caja D
                  WHERE  d.cod_grupo_cia = Ccodgrupocia_In
                  AND    d.cod_local = Ccodlocal_In
                  AND    D.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
                  AND    d.cod_cuadratura = '032');
    RETURN curCe;
  END;

FUNCTION GET_IND_CIERRE_CAJ_QF(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR)
  RETURN CHAR
  IS
  Indicador CHAR(1);
  BEGIN

      select nvl(l.ind_adm_vb_qf,'N')
      into Indicador
        from pbl_local l
       where l.cod_grupo_cia = cCodGrupoCia_in
         and l.cod_local = cCodLocal_in;

  return Indicador;
  END;



  FUNCTION IS_ADMIN_CAJ_QF(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                           cSecUsu_in       IN CHAR)
  RETURN CHAR
  IS
  vCount NUMBER;
  Indicador CHAR(1);
  BEGIN

      SELECT COUNT(*)
      INTO   vCount
      FROM PBL_ROL_USU U
      WHERE U.SEC_USU_LOCAL = cSecUsu_in
      AND   U.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   U.COD_LOCAL = cCodLocal_in
      AND   U.COD_ROL ='011';

      IF vCount > 0 THEN
           Indicador := 'S';
      ELSE
           Indicador := 'N';
      END IF;

  return Indicador;
  END;

--====================================================================================================

  FUNCTION CE_LISTA_REG_RES_FORM_FIRST(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                           cSecMovCaja_in       IN CHAR)
  RETURN FarmaCursor
  IS
    curCe FarmaCursor;
    V_Mov_Aper CHAR(10) := CE_OBT_APER(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);
  BEGIN
    OPEN curCe FOR
        SELECT
        FOP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
        DECODE(TIP_MONEDA,'02','DOLARES','SOLES') || 'Ã' ||
        TO_CHAR((SUM(FPP.IM_TOTAL_PAGO-FPP.VAL_VUELTO) - nvl((
                                                SELECT
                                                SUM(FPP.VAL_VUELTO)
                                                FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP
                                                ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA
                                                AND CAB.COD_LOCAL = FPP.COD_LOCAL
                                                AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
                                                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND CAB.COD_LOCAL = cCodLocal_in
                                                AND CAB.EST_PED_VTA = 'C'
                                                AND CAB.SEC_MOV_CAJA = V_Mov_Aper
                                                AND FPP.COD_FORMA_PAGO = '00002'
                                                ),0) - nvl(
                                                (select
                                                sum(mon_entrega_total)
                                                from ce_sobre_tmp
                                                where sec_mov_caja=V_Mov_Aper
                                                and tip_moneda='01')
                                                ,0) ),'999,990.00') || 'Ã' ||
        TO_CHAR((SUM(FPP.IM_TOTAL_PAGO-FPP.VAL_VUELTO) - nvl((
                                                SELECT
                                                SUM(FPP.VAL_VUELTO)
                                                FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP
                                                ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA
                                                AND CAB.COD_LOCAL = FPP.COD_LOCAL
                                                AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
                                                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND CAB.COD_LOCAL = cCodLocal_in
                                                AND CAB.EST_PED_VTA = 'C'
                                                AND CAB.SEC_MOV_CAJA = V_Mov_Aper
                                                AND FPP.COD_FORMA_PAGO = '00002'
                                                ),0) - nvl(
                                                (select
                                                sum(mon_entrega_total)
                                                from ce_sobre_tmp
                                                where sec_mov_caja=V_Mov_Aper
                                                and tip_moneda='01')
                                                ,0) ),'999,990.00') || 'Ã' ||
        FOP.COD_FORMA_PAGO
        FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND
        CAB.COD_LOCAL = FPP.COD_LOCAL AND
        CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
        JOIN VTA_FORMA_PAGO FOP ON (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND
        FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.EST_PED_VTA = 'C'
        AND CAB.SEC_MOV_CAJA = V_Mov_Aper
        AND FPP.COD_FORMA_PAGO = '00001'
        GROUP BY FOP.DESC_CORTA_FORMA_PAGO, FOP.COD_FORMA_PAGO, FPP.TIP_MONEDA, 0

        UNION

        SELECT
        FOP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
        DECODE(TIP_MONEDA,'02','DOLARES','SOLES') || 'Ã' ||
        (SUM(FPP.IM_PAGO) - (select nvl(sum(mon_entrega_total),0) from ce_sobre_tmp where sec_mov_caja=V_Mov_Aper and tip_moneda='02')) || 'Ã' ||
        (SUM(FPP.IM_TOTAL_PAGO) - (select nvl(sum(mon_entrega_total),0) from ce_sobre_tmp where sec_mov_caja=V_Mov_Aper and tip_moneda='02')) || 'Ã' ||
        FOP.COD_FORMA_PAGO
        FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND
        CAB.COD_LOCAL = FPP.COD_LOCAL AND
        CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
        JOIN VTA_FORMA_PAGO FOP ON (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND
        FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.EST_PED_VTA = 'C'
        AND CAB.SEC_MOV_CAJA = V_Mov_Aper
        AND FPP.COD_FORMA_PAGO = '00002'
        GROUP BY FOP.DESC_CORTA_FORMA_PAGO, FOP.COD_FORMA_PAGO, FPP.TIP_MONEDA

        UNION

        SELECT
        FOP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
        DECODE(TIP_MONEDA,'02','DOLARES','SOLES') || 'Ã' ||
        sum(FPP.IM_PAGO) || 'Ã' ||
        sum(FPP.IM_TOTAL_PAGO) || 'Ã' ||
        FOP.COD_FORMA_PAGO
        FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND
        CAB.COD_LOCAL = FPP.COD_LOCAL AND
        CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
        JOIN VTA_FORMA_PAGO FOP ON (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND
        FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.EST_PED_VTA = 'C'
        AND CAB.SEC_MOV_CAJA = V_Mov_Aper
        AND FPP.COD_FORMA_PAGO NOT IN ('00001','00002')
        AND FOP.ind_forma_pago_automatico_ce = 'N'
        GROUP BY FOP.DESC_CORTA_FORMA_PAGO, FOP.COD_FORMA_PAGO, FPP.TIP_MONEDA , FPP.COD_LOTE;

    RETURN curce;
    END;

--====================================================================================================

    FUNCTION CE_OBT_APER(cCodGrupoCia_in  IN CHAR,
                           cCodLocal_in     IN CHAR,
                           cSecMovCaja_in IN CHAR)
    RETURN CHAR
    IS
    V_Mov_Aper CHAR(10);
    BEGIN
          SELECT sec_mov_caja_origen
          INTO   V_Mov_Aper
          FROM   ce_mov_caja c
          WHERE  c.cod_grupo_cia = ccodgrupocia_in
          AND    c.cod_local = ccodlocal_in
          AND    c.sec_mov_caja = cSecMovCaja_in;
    RETURN V_Mov_Aper;
    END;

--====================================================================================================

    FUNCTION CE_FOR_PAG_NO_AUTO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecMovCaja_in IN CHAR,
                                     cIdUsu_in IN CHAR)
    RETURN VARCHAR2
    IS
    VSEC varchar2(200);
    Cad_Impr varchar2(200);
    V_Mov_Aper CHAR(10) := CE_OBT_APER(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);
    V_Prosegur char(1);
    nSecSobre     number;
    cFecDiaVta_in date;
    vCodigoSobre  varchar2(20);
    cantidadSobr NUMBER(3);
    vIndProsegur char(1); --

    CURSOR curFormaPago IS
        --Cuando son tarjetas o no actualizables en automatico
        SELECT
        FOP.COD_FORMA_PAGO AS CODIGO,
        COUNT(*) AS CANTIDAD,
        TIP_MONEDA AS TIP_MONEDA_SOLES,
        TO_CHAR(SUM(FPP.IM_PAGO),'999,990.00') AS MONTO,
        TO_CHAR(SUM(FPP.IM_TOTAL_PAGO),'999,990.00') AS MONTO_TOT,
        cIdUsu_in,
        FPP.COD_LOTE AS Cod_Lote
        FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND
        CAB.COD_LOCAL = FPP.COD_LOCAL AND
        CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
        JOIN VTA_FORMA_PAGO FOP ON (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND
        FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.EST_PED_VTA = 'C'
        AND CAB.SEC_MOV_CAJA = V_Mov_Aper
        AND FPP.COD_FORMA_PAGO NOT IN ('00001','00002')
        AND (FOP.ind_forma_pago_automatico_ce = 'N' or (FOP.ind_forma_pago_automatico_ce = 'S' and fop.ind_tarj = 'S'))
        GROUP BY CAB.COD_GRUPO_CIA, CAB.COD_LOCAL, CAB.SEC_MOV_CAJA, FOP.COD_FORMA_PAGO,
        FPP.COD_LOTE, TIP_MONEDA, cIdUsu_in, FPP.COD_LOTE

        union
        --Dolares
        select
        FOP.COD_FORMA_PAGO AS CODIGO,
        0 AS CANTIDAD,
        TIP_MONEDA AS TIP_MONEDA_SOLES,
        TO_CHAR((((SUM(FPP.IM_PAGO))-(select nvl(sum(mon_entrega),0) from ce_sobre_tmp where sec_mov_caja='0000010682' and tip_moneda='02' and ESTADO='A'))),'999,990.00') AS MONTO,
        TO_CHAR((((SUM(FPP.IM_PAGO))-(select nvl(sum(mon_entrega),0) from ce_sobre_tmp where sec_mov_caja='0000010682' and tip_moneda='02' and ESTADO='A'))*(cab.val_tip_cambio_ped_vta)),'999,990.00') AS MONTO_TOT,
        cIdUsu_in,
        NULL AS Cod_Lote
        FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND
        CAB.COD_LOCAL = FPP.COD_LOCAL AND
        CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
        JOIN VTA_FORMA_PAGO FOP ON (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND
        FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.EST_PED_VTA = 'C'
        AND CAB.SEC_MOV_CAJA = V_Mov_Aper
        AND FPP.COD_FORMA_PAGO = '00002'
        GROUP BY CAB.COD_GRUPO_CIA, CAB.COD_LOCAL, CAB.SEC_MOV_CAJA, FOP.COD_FORMA_PAGO,
        TIP_MONEDA,cab.val_tip_cambio_ped_vta

        union
        --Soles
        SELECT
        FOP.COD_FORMA_PAGO AS CODIGO,
        0 AS CANTIDAD,
        TIP_MONEDA AS TIP_MONEDA_SOLES,
        TO_CHAR((SUM(FPP.IM_TOTAL_PAGO-FPP.VAL_VUELTO) - nvl((
                                                SELECT
                                                SUM(FPP.VAL_VUELTO)
                                                FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP
                                                ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA
                                                AND CAB.COD_LOCAL = FPP.COD_LOCAL
                                                AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
                                                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND CAB.COD_LOCAL = cCodLocal_in
                                                AND CAB.EST_PED_VTA = 'C'
                                                AND CAB.SEC_MOV_CAJA = V_Mov_Aper
                                                AND FPP.COD_FORMA_PAGO = '00002'
                                                ),0) - nvl(
                                                (select
                                                sum(mon_entrega_total)
                                                from ce_sobre_tmp
                                                where sec_mov_caja = V_Mov_Aper
                                                and tip_moneda='01'
                                                and ESTADO='A')
                                                ,0) ),'999,990.00') AS MONTO,
        TO_CHAR((SUM(FPP.IM_TOTAL_PAGO-FPP.VAL_VUELTO) - nvl((
                                                SELECT
                                                SUM(FPP.VAL_VUELTO)
                                                FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP
                                                ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA
                                                AND CAB.COD_LOCAL = FPP.COD_LOCAL
                                                AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
                                                WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND CAB.COD_LOCAL = cCodLocal_in
                                                AND CAB.EST_PED_VTA = 'C'
                                                AND CAB.SEC_MOV_CAJA = V_Mov_Aper
                                                AND FPP.COD_FORMA_PAGO = '00002'
                                                ),0) - nvl(
                                                (select
                                                sum(mon_entrega_total)
                                                from ce_sobre_tmp
                                                where sec_mov_caja = V_Mov_Aper
                                                and tip_moneda='01'
                                                and ESTADO='A')
                                                ,0) ),'999,990.00') AS MONTO_TOT,
        cIdUsu_in,
        null AS Cod_Lote
        FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND
        CAB.COD_LOCAL = FPP.COD_LOCAL AND
        CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
        JOIN VTA_FORMA_PAGO FOP ON (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND
        FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.EST_PED_VTA = 'C'
        AND CAB.SEC_MOV_CAJA = V_Mov_Aper
        AND FPP.COD_FORMA_PAGO = '00001'
        GROUP BY CAB.COD_GRUPO_CIA, CAB.COD_LOCAL, CAB.SEC_MOV_CAJA, FOP.COD_FORMA_PAGO,
        TIP_MONEDA,cab.val_tip_cambio_ped_vta;

    BEGIN
    select m.fec_dia_vta
      into cFecDiaVta_in
      from ce_mov_caja m
     where m.cod_grupo_cia = cCodGrupoCia_in
       and m.cod_local = cCodLocal_in
       and m.sec_mov_caja = cSecMovCaja_in;

    Cad_Impr := '';

      FOR v_rCurFormaPago IN curFormaPago
      LOOP
                VSEC :=CE_GRABA_FORMA_PAGO_ENTREGA(
                                      cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                      v_rCurFormaPago.CODIGO,
                                      v_rCurFormaPago.CANTIDAD,
                                      v_rCurFormaPago.TIP_MONEDA_SOLES,
                                      TO_NUMBER(v_rCurFormaPago.MONTO,'999,990.00'),
                                      TO_NUMBER(v_rCurFormaPago.MONTO_TOT,'999,990.00'),
                                      cIdUsu_in,
                                      v_rCurFormaPago.Cod_Lote);

                V_Prosegur := PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_IND_SEGUR_LOCAL(cCodGrupoCia_in,cCodLocal_in,v_rCurFormaPago.CODIGO);

                IF(V_Prosegur = 'S') THEN

                nSecSobre:=PTOVENTA_CAJ.CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in,cFecDiaVta_in);
                vCodigoSobre := to_char(cFecDiaVta_in, 'ddmmyyyy') || '-' || Farma_Utility.COMPLETAR_CON_SIMBOLO(nSecSobre,3,0,'I');

                SELECT COUNT(*) INTO cantidadSobr
                FROM ce_sobre x
                WHERE x.cod_grupo_cia=cCodGrupoCia_in
                AND x.cod_local=cCodLocal_in
                AND x.cod_sobre=vCodigoSobre
                AND x.estado IN ('P','A');

                select a.ind_prosegur into vIndProsegur
                from pbl_local a
                where a.cod_grupo_cia=cCodGrupoCia_in
                and   a.cod_local=cCodLocal_in;

                    IF(cantidadSobr = 0) THEN
                                INSERT INTO CE_SOBRE
                                (COD_SOBRE,
                                COD_GRUPO_CIA,
                                COD_LOCAL,
                                SEC_MOV_CAJA,
                                SEC_FORMA_PAGO_ENTREGA,
                                FEC_DIA_VTA,
                                ESTADO,
                                USU_CREA_SOBRE,COD_FORMA_PAGO, IND_ETV)
                                VALUES
                                (vCodigoSobre,
                                cCodGrupoCia_in,
                                cCodLocal_in,
                                cSecMovCaja_in,
                                VSEC,
                                cFecDiaVta_in,
                                'A',
                                cIdUsu_in,
                                v_rCurFormaPago.CODIGO, vIndProsegur);
                                Cad_Impr := vCodigoSobre || 'Ã' || Cad_Impr;
                    END IF;

                END IF;

      END LOOP;
      RETURN Cad_Impr;
    END;
    --============================================================

    FUNCTION CE_VALIDAR_MONT_TARJ(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecMovCaja_in IN CHAR,
                                     cIdUsu_in IN CHAR, --inv
                                     cCodFomPag_in IN CHAR,
                                     cCodLote_in IN CHAR,
                                     cTipMon_in IN CHAR,
                                     cCantidad_in IN varchar2,
                                     cMont_in IN varchar2,
                                     cMontTol_in IN varchar2)
    RETURN VARCHAR2
    IS
    v_cant varchar2(3) :='';
    v_mont varchar2(20) :='';
    v_montTot varchar2(20) :='';

    vMont_in varchar2(20) := TO_CHAR((TO_NUMBER(cMont_in,'999,990.00')),'999,990.00');
    vMontTol_in varchar2(20) := TO_CHAR((TO_NUMBER(cMontTol_in,'999,990.00')),'999,990.00');

    V_VALIDA varchar2(100);
    V_Mov_Aper CHAR(10) := CE_OBT_APER(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);
    BEGIN

        SELECT
        COUNT(*) ,
        TO_CHAR((SUM(FPP.IM_PAGO)),'999,990.00'),
        TO_CHAR((SUM(FPP.IM_TOTAL_PAGO)),'999,990.00')
        INTO v_cant,v_mont,v_montTot
        FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPP ON (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND
        CAB.COD_LOCAL = FPP.COD_LOCAL AND
        CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
        JOIN VTA_FORMA_PAGO FOP ON (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND
        FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
        --Existentes
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.SEC_MOV_CAJA = V_Mov_Aper
        --Nuevos
        AND FOP.COD_FORMA_PAGO = cCodFomPag_in
        AND FPP.COD_LOTE = cCodLote_in
        AND TIP_MONEDA = cTipMon_in
        --Condiciones
        AND CAB.EST_PED_VTA = 'C'
        AND FPP.COD_FORMA_PAGO NOT IN ('00001','00002')
        AND (FOP.ind_forma_pago_automatico_ce = 'N' or (FOP.ind_forma_pago_automatico_ce = 'S' and fop.ind_tarj = 'S'))
        GROUP BY CAB.COD_GRUPO_CIA, CAB.COD_LOCAL, CAB.SEC_MOV_CAJA, FOP.COD_FORMA_PAGO,
        TIP_MONEDA, FPP.COD_LOTE;

    IF(cCantidad_in = v_cant) AND (v_mont = vMont_in) AND (v_montTot = vMontTol_in) THEN
        V_VALIDA := 'OK';
    END IF;

    IF(cCantidad_in <> v_cant) THEN
        V_VALIDA := 'Cantidades de voucher diferentes en : '||abs(to_number(cCantidad_in) - to_number(v_cant));
    END IF;

    IF(v_mont <> vMont_in) THEN
        V_VALIDA := 'Montos sub totales diferentes.';
    END IF;

    IF(v_montTot <> vMontTol_in) THEN
        V_VALIDA := 'Montos totales diferentes.';
    END IF;

    RETURN V_VALIDA;
    END;

--====================================================
    FUNCTION CE_LISTA_FORM_PAGO_TRJ(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cSecMovCaja_in   IN CHAR)
    RETURN FarmaCursor
    IS
      curCe FarmaCursor;
      V_Mov_Aper CHAR(10) := CE_OBT_APER(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);
      v_Cont NUMBER(5);
    BEGIN
          --Aqui muestra las formas de pago que se registraron sin ser emitidas
          OPEN curCe FOR
                  SELECT    FPE.COD_FORMA_PAGO || 'Ã' ||
                            FP.DESC_FORMA_PAGO || 'Ã' ||
                            '01' || 'Ã' ||' '|| 'Ã' ||' '|| 'Ã' ||' '|| 'Ã' ||' '
                  FROM      CE_FORMA_PAGO_ENTREGA FPE JOIN VTA_FORMA_PAGO FP
                            ON (FPE.COD_GRUPO_CIA = FP.COD_GRUPO_CIA AND FPE.COD_FORMA_PAGO = FP.COD_FORMA_PAGO)
                  WHERE     FPE.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND       FPE.COD_LOCAL = cCodLocal_in
                  AND       FPE.SEC_MOV_CAJA = cSecMovCaja_in
                  AND       FPE.EST_FORMA_PAGO_ENT = 'A'
                  AND       FPE.COD_FORMA_PAGO NOT IN ('00001','00002')
                  AND       FPE.COD_FORMA_PAGO NOT IN (
                                  SELECT    FPP.COD_FORMA_PAGO
                                  FROM      VTA_PEDIDO_VTA_CAB CAB
                                            JOIN VTA_FORMA_PAGO_PEDIDO FPP ON
                                            (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND CAB.COD_LOCAL = FPP.COD_LOCAL AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
                                            JOIN VTA_FORMA_PAGO FOP ON
                                            (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
                                  WHERE     FPP.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND       FPP.COD_LOCAL = cCodLocal_in
                                  AND       SEC_MOV_CAJA = V_Mov_Aper
                                  AND       CAB.EST_PED_VTA = 'C'
                                  AND       FPP.COD_FORMA_PAGO NOT IN ('00001','00002')
                                  AND       (FOP.IND_FORMA_PAGO_AUTOMATICO_CE = 'N' OR (FOP.IND_FORMA_PAGO_AUTOMATICO_CE = 'S' AND FOP.IND_TARJ = 'S'))
                                  GROUP BY  FPP.COD_FORMA_PAGO
                                  )
                  AND       (FP.IND_FORMA_PAGO_AUTOMATICO_CE = 'N' OR (FP.IND_FORMA_PAGO_AUTOMATICO_CE = 'S' AND FP.IND_TARJ = 'S'))
                  GROUP BY  FPE.COD_GRUPO_CIA, FPE.COD_LOCAL, FPE.SEC_MOV_CAJA, FPE.COD_FORMA_PAGO, FP.DESC_FORMA_PAGO, FPE.TIP_MONEDA

                  UNION

                  --Aqui muestra las formas de pago que no han sido registradas
                  SELECT    FOP.COD_FORMA_PAGO || 'Ã' ||
                            FOP.DESC_FORMA_PAGO || 'Ã' ||
                            '02' || 'Ã' ||' '|| 'Ã' ||' '|| 'Ã' ||' '|| 'Ã' ||' '
                  FROM      VTA_PEDIDO_VTA_CAB CAB
                            JOIN VTA_FORMA_PAGO_PEDIDO FPP ON
                            (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND CAB.COD_LOCAL = FPP.COD_LOCAL AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
                            JOIN VTA_FORMA_PAGO FOP ON
                            (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
                  WHERE     CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND       CAB.COD_LOCAL = cCodLocal_in
                  AND       CAB.SEC_MOV_CAJA = V_Mov_Aper
                  AND       CAB.EST_PED_VTA = 'C'
                  AND       FPP.COD_FORMA_PAGO NOT IN ('00001','00002')
                  AND       FPP.COD_FORMA_PAGO NOT IN (
                                  SELECT    FPE.COD_FORMA_PAGO
                                  FROM      CE_FORMA_PAGO_ENTREGA FPE,
                                            VTA_FORMA_PAGO FP
                                  WHERE     FPE.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND       FPE.COD_LOCAL = cCodLocal_in
                                  AND       FPE.SEC_MOV_CAJA = cSecMovCaja_in
                                  AND       FPE.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
                                  AND       FPE.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
                                  AND       FPE.EST_FORMA_PAGO_ENT = 'A'
                                  GROUP BY  FP.DESC_CORTA_FORMA_PAGO,FPE.TIP_MONEDA,FPE.COD_FORMA_PAGO
                                  )
                  AND       (FOP.ind_forma_pago_automatico_ce = 'N' or (FOP.ind_forma_pago_automatico_ce = 'S' and fop.ind_tarj = 'S'))
                  GROUP BY  CAB.COD_GRUPO_CIA, CAB.COD_LOCAL, CAB.SEC_MOV_CAJA, FOP.COD_FORMA_PAGO, FOP.DESC_FORMA_PAGO, TIP_MONEDA

                  UNION

                  --Aqui comprueba diferencia entra cantidades y monton
                  SELECT    FOP.COD_FORMA_PAGO|| 'Ã' ||FOP.DESC_FORMA_PAGO|| 'Ã' ||'03'|| 'Ã' ||FPP.COD_LOTE|| 'Ã' ||FPP.TIP_MONEDA|| 'Ã' ||
                            ABS(count(*) - (
                                  SELECT    FPE.CANT_VOUCHER
                                  FROM      CE_FORMA_PAGO_ENTREGA FPE JOIN VTA_FORMA_PAGO FP
                                            ON (FPE.COD_GRUPO_CIA = FP.COD_GRUPO_CIA AND FPE.COD_FORMA_PAGO = FP.COD_FORMA_PAGO)
                                  WHERE     FPE.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                                  AND       FPE.COD_LOCAL = CAB.COD_LOCAL
                                  AND       FPE.SEC_MOV_CAJA = cSecMovCaja_in
                                  AND       FP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO
                                  AND       TRIM(FPE.NUM_LOTE) = TRIM(FPP.COD_LOTE)
                                  AND       FPE.EST_FORMA_PAGO_ENT = 'A'
                                  AND       FPE.COD_FORMA_PAGO NOT IN ('00001','00002')
                                  AND       (FP.ind_forma_pago_automatico_ce = 'N' or (FP.ind_forma_pago_automatico_ce = 'S' and FP.ind_tarj = 'S')))) || 'Ã' ||
                            ABS(SUM(FPP.IM_TOTAL_PAGO) - (
                                  SELECT    FPE.MON_ENTREGA_TOTAL
                                  FROM      CE_FORMA_PAGO_ENTREGA FPE JOIN VTA_FORMA_PAGO FP
                                            ON (FPE.COD_GRUPO_CIA = FP.COD_GRUPO_CIA AND FPE.COD_FORMA_PAGO = FP.COD_FORMA_PAGO)
                                  WHERE     FPE.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                                  AND       FPE.COD_LOCAL = CAB.COD_LOCAL
                                  AND       FPE.SEC_MOV_CAJA = cSecMovCaja_in
                                  AND       FP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO
                                  AND       TRIM(FPE.NUM_LOTE) = TRIM(FPP.COD_LOTE)
                                  AND       FPE.EST_FORMA_PAGO_ENT = 'A'
                                  AND       FPE.COD_FORMA_PAGO NOT IN ('00001','00002')
                                  AND       (FP.ind_forma_pago_automatico_ce = 'N' or (FP.ind_forma_pago_automatico_ce = 'S' and FP.ind_tarj = 'S'))))
                  FROM      VTA_PEDIDO_VTA_CAB CAB
                            JOIN VTA_FORMA_PAGO_PEDIDO FPP ON
                            (CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA AND CAB.COD_LOCAL = FPP.COD_LOCAL AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA)
                            JOIN VTA_FORMA_PAGO FOP ON
                            (FPP.COD_GRUPO_CIA = FOP.COD_GRUPO_CIA AND FPP.COD_FORMA_PAGO = FOP.COD_FORMA_PAGO)
                  WHERE     CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND       CAB.COD_LOCAL = cCodLocal_in
                  AND       CAB.SEC_MOV_CAJA = V_Mov_Aper
                  AND       CAB.EST_PED_VTA = 'C'
                  AND       FPP.COD_FORMA_PAGO NOT IN ('00001','00002')
                  AND       (FOP.IND_FORMA_PAGO_AUTOMATICO_CE = 'N' OR (FOP.IND_FORMA_PAGO_AUTOMATICO_CE = 'S' AND FOP.IND_TARJ = 'S'))
                  GROUP BY  CAB.COD_GRUPO_CIA, CAB.COD_CIA,CAB.COD_LOCAL, FOP.COD_FORMA_PAGO, FOP.DESC_FORMA_PAGO, FPP.COD_LOTE, FPP.TIP_MONEDA;
          RETURN curCe;
    END;

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
    RETURN INT
    IS
        vCodFonSencillo INT;
    BEGIN
        --wvillagomez 11.09.2013
        SELECT SEQ_FONDO_SENCILLO_ETV.NEXTVAL INTO vCodFonSencillo FROM DUAL;

        INSERT INTO PTOVENTA.CE_FONDO_SENCILLO_ETV
        (COD_GRUPO_CIA, COD_CIA, COD_LOCAL, COD_FON_SENCILLO , NRO_FOLIO, VAL_TOTAL, VAL_RECIBIDO, VAL_DIFERENCIA,
         TIP_FON_SENCILLO, COD_ETV, USU_CREA)
        VALUES( cCodGrupoCia_in, cCodCia_in, cCodLocal_in, vCodFonSencillo, cFolio_in, cTotal_in, cMonto_in,
                cDiferencia_in, cTipFondoSencillo_in, cCodETV_in, cIdUsu_in);
        RETURN vCodFonSencillo;
    EXCEPTION
    WHEN OTHERS THEN
      vCodFonSencillo := 0;
      RETURN vCodFonSencillo;

    END;

    FUNCTION CE_F_VERIFICA_REC_SENCILLO (cCodGrupoCia_in   IN  CHAR,
                                          cCodCia_in        IN  CHAR,
                                          cCodLocal_in      IN  CHAR,
                                          cCodETV_in        IN  CHAR,
                                          cTotal_out        OUT NUMBER)
    RETURN CHAR
    IS
        vAbrir        CHAR(1);
        vTipFondoSenc CHAR(1);
        vEstado       CHAR(1);
        vFecCrea      DATE;
        vTitulo       CHAR(45);
    BEGIN
      BEGIN
        --wvillagomez 11.09.2013
        vAbrir  := 'N';
        vEstado := 'A';

        SELECT TIP_FON_SENCILLO,  FEC_CREA,  VAL_TOTAL
          INTO vTipFondoSenc,     vFecCrea,  cTotal_out
        FROM ( SELECT *
          FROM CE_FONDO_SENCILLO_ETV
         WHERE COD_GRUPO_CIA    = cCodGrupoCia_in
           AND COD_CIA          = cCodCia_in
           AND COD_LOCAL        = cCodLocal_in
           AND COD_ETV          = cCodETV_in
           AND EST_FON_SENCILLO = vEstado
        ORDER BY COD_FON_SENCILLO DESC
        ) WHERE ROWNUM = 1
        ;

        IF vFecCrea IS NULL THEN
            vAbrir := 'S';
        ELSE
            IF vTipFondoSenc = '1' THEN
                vAbrir := 'N';
            ELSE
                vAbrir := 'S';
            END IF;
        END IF;

        EXCEPTION
        WHEN NO_DATA_FOUND THEN
          cTotal_out  := 0;
          vAbrir      := 'S';
        WHEN OTHERS THEN
          cTotal_out  := 0;
          vAbrir      := 'X';
      END;
      RETURN vAbrir;
    END;

    FUNCTION CE_F_GET_ETV ( cCodGrupoCia_in   IN  CHAR,
                      cCodCia_in        IN  CHAR,
                      cCodLocal_in      IN  CHAR)
    RETURN CHAR
    IS
        vCodETV_out CHAR(1);
        vCodETV     NUMBER;
        vEstado     CHAR(1);
    BEGIN
        vEstado := 'A';

        SELECT TO_NUMBER(COD_ETV)
          INTO vCodETV
          FROM PBL_ETV_LOCAL
         WHERE COD_GRUPO_CIA    = cCodGrupoCia_in
           AND COD_CIA          = cCodCia_in
           AND COD_LOCAL        = cCodLocal_in
           AND EST_ETV_LOCAL    = vEstado;

        vCodETV     := vCodETV - 1;
        vCodETV_out := vCodETV || '';

      RETURN vCodETV_out;

    END;

   FUNCTION CE_F_IMP_FONDO_SENCILLO( cCodFonSencillo IN NUMBER)
  RETURN VARCHAR2
    IS
       C_INICIO_MSG  VARCHAR2(2000) := '<html>
                                        <head><style type="text/css"> body { font-family:Arial, Helvetica, sans-serif;} </style></head>
                                        <body><table width="340" border="0">
                                              <tr><td><table border="0">';

       C_FILA_VACIA  VARCHAR2(2000) :='<tr><td height="13" colspan="3"></td></tr> ';

       C_FIN_MSG     VARCHAR2(2000) := '</table></td></tr></table></body></html>';

        vMsg_out            VARCHAR2(32767) := '';
        vMensajeLocal       VARCHAR2(1000)  := '';
        vLocal              VARCHAR2(50)    := '';
        vMensajeRecaudacion VARCHAR2(1000)  := '';

        vCodGrupoCia      CHAR(3);
        vCodCia           CHAR(3);
        vCodLocal         CHAR(3);
        vCorrelativo      NUMBER(10,0);
        vFecha            CHAR(10);
        vHora             CHAR(8);
        vFolioETV         CHAR(10);
        vTotal            NUMBER(9,3);
        vMonto            NUMBER(9,3);
        vDiferencia       NUMBER(9,3);
        vTipFonSencillo   CHAR(1);
        vTitulo           CHAR(50);
        vTextoMarca varchar2(50) := '';  --ASOSA - 14/08/2014
        cCodMarca char(3) := ''; --ASOSA - 17/09/2014
    BEGIN
          --wvillagomez 13.09.2013
         SELECT COD_GRUPO_CIA,
                COD_CIA,
                COD_LOCAL,
                COD_FON_SENCILLO                 CORRELATIVO,
                TO_CHAR(FEC_CREA, 'dd/MM/yyyy')  FECHA,
                TO_CHAR(FEC_CREA, 'HH24:mi:ss')  HORA,
                NRO_FOLIO                        FOLIO_ETV,
                VAL_TOTAL                        TOTAL,
                VAL_RECIBIDO                     MONTO,
                VAL_DIFERENCIA                   DIFERENCIA,
                TIP_FON_SENCILLO
           INTO vCodGrupoCia,
                vCodCia,
                vCodLocal,
                vCorrelativo,
                vFecha,
                vHora,
                vFolioETV,
                vTotal,
                vMonto,
                vDiferencia,
                vTipFonSencillo
           FROM CE_FONDO_SENCILLO_ETV
          WHERE COD_FON_SENCILLO = cCodFonSencillo;

          --INI ASOSA - 14/08/2014
          SELECT PL.COD_MARCA
          INTO cCodMarca
          FROM PBL_LOCAL PL
          WHERE PL.COD_GRUPO_CIA = vCodGrupoCia
          AND PL.COD_CIA = vCodCia
          AND PL.COD_LOCAL = vCodLocal;

          IF cCodMarca <> COD_CIA_MARKET_01 THEN
                     vTextoMarca := 'BOTICAS ';
          END IF;
          --FIN ASOSA - 14/08/2014

          SELECT
              --'BOTICAS '||MG.NOM_MARCA ||
              vTextoMarca||MG.NOM_MARCA ||   --ASOSA - 14/08/2014
              '<br>'|| PC.RAZ_SOC_CIA || ' RUC: '||PC.NUM_RUC_CIA ||
              '<br>'|| PC.DIR_CIA
              ,PL.COD_LOCAL
          INTO  vMensajeLocal,
                vlocal
          FROM PBL_LOCAL PL
              JOIN PBL_MARCA_CIA MC       ON (PL.COD_GRUPO_CIA  = MC.COD_GRUPO_CIA AND
                                              PL.COD_MARCA      = MC.COD_MARCA     AND
                                              PL.COD_CIA        = MC.COD_CIA)
              JOIN PBL_MARCA_GRUPO_CIA MG ON (MG.COD_GRUPO_CIA  = MC.COD_GRUPO_CIA AND
                                              MG.COD_MARCA      = MC.COD_MARCA)
              JOIN PBL_CIA PC             ON (PC.COD_CIA        = PL.COD_CIA)
          WHERE PL.COD_GRUPO_CIA  = vCodGrupoCia
            AND PL.COD_LOCAL      = vCodLocal;


        vMensajeRecaudacion :=
            '<table width="100%" border="0"><tr><td>FECHA    :'||vFecha||'</td><td align="right">HORA :' || vHora ||'</td></tr>'||
            '<tr><td>'||'LOCAL N:'|| vlocal ||'</td></tr></table>' ||
                    'Correlativo      :'|| vCorrelativo ||
            '<br>'||'Folio ETV        :'|| vFolioETV ||
            '<br>'||'Fecha Recepción  :'|| vFecha ||
            '<br>'||'Hora Recepción   :'|| vHora ;
            IF vTipFonSencillo = '1' THEN
                vMensajeRecaudacion := vMensajeRecaudacion ||
                '<br>'||'Total Recibido   :S/.'|| TO_CHAR(vTotal,'999,990.00') ||
                '<br>'||
                '<br>'||
                '<br>'||
                '<br>'||
                '<br>'||
                '<br>'||
                '<br>';
                vTitulo := 'VALE DE RECIBO DE SENCILLO';
            ELSE
                vMensajeRecaudacion := vMensajeRecaudacion ||
                '<br>'||'Fecha que se paga:'|| vFecha ||
                '<table width="100%" border="0"><tr><td>Monto Diferencia :S/.</td><td align="right">'|| TO_CHAR(vDiferencia,'999,990.00') ||'</td></tr>' ||
                '<tr><td>Monto Pago       :S/.</td><td align="right">'|| TO_CHAR(vMonto,'999,990.00') ||'</td></tr>' ||
                '<tr><td>Total Pago       :S/.</td><td align="right">'|| TO_CHAR(vTotal,'999,990.00') ||'</td></tr></table>'||
                '<br></br>';
                vTitulo := 'VALE PAGO PARCIAL DE SENCILLO EN CONTINGENCIA';
                IF vDiferencia = 0 THEN
                  vTitulo := 'VALE PAGO TOTAL DE SENCILLO';
                END IF;
            END IF;
            vMensajeRecaudacion := vMensajeRecaudacion ||
            '<table width="100%" border="0"><tr><td align="center">-------------------------</td><td align="center">-------------------------------</td></tr>' ||
            '<tr><td align="center">Administrador</td><td align="center">Transportista</td></tr></table>';


        vMsg_out := C_INICIO_MSG ||
                   '<tr><td height="21" colspan="3" align="center">'||
                    vMensajeLocal||
                    '</td></tr>'||
                    '<tr><td height="21" colspan="3" align="center"
                            style="font-weight:bold;">' ||
                            vTitulo||
                    '</td></tr>'||
                    '<tr><td height="21" colspan="3" align="left">'||
                    vMensajeRecaudacion||
                    '</td></tr>'||
                    C_FIN_MSG ;

        RETURN vMsg_out;

    END;

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
                                       cUsuCreador_in    IN CHAR)
  IS
  BEGIN
       INSERT INTO VTA_LOG_CONCILIACIONES (COD_GRUPO_CIA,
                                           COD_CIA,
                                           COD_LOCAL,
                                           COD_OPERACION,
                                           DSC_CONCEPTO,
                                           EST_CONCILIACION,
                                           FEC_CREA,
                                           USU_CREA)
        VALUES (cCodGrupoCia_in,
                cCodCia_in,
                cCodLocal_in,
                cCodProceso_in,
                cDescConcepto_in,
                cEstConciliacion_in,
                SYSDATE,
                cUsuCreador_in);
        COMMIT;
  END;

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
                                       cCodLocalMigra_in IN CHAR)
   IS
   BEGIN
      --ERIOS 2.2.8 Conciliacion offline
      IF cTipoTrans_in = '1' OR cTipoTrans_in = '3' THEN
        RETURN;
      END IF;

        insert into vta_log_conciliacion(id,
                                         cod_grupo_cia,
                                         cod_cia,
                                         cod_local,
                                         pid_vendedor,
                                         pfecha_venta,
                                         pmonto_venta,
                                         pnum_cuotas,
                                         pcod_autorizacion,
                                         ptrack2,
                                         pcod_autorizacion_pre,
                                         pfpa_valorxcuota,
                                         pcaja,
                                         ptipo_transaccion,
                                         pcodiserv,
                                         pfpa_num_obj_pago,
                                         pnombclie,
                                         pvoucher,
                                         pnro_comp_anu,
                                         pfech_comp_anu,
                                         pcodiautoorig,
                                         pfpa_tipocambio,
                                         pfpa_nrotrace,
                                         pcod_alianza,
                                         pcod_moneda_trx,
                                         pmon_estpago,
                                         dsc_concepto,
                                         est_conciliacion,
                                         fec_crea,
                                         usu_crea,
                                         COD_LOCAL_MIGRA)
        values(sq_vta_log_conciliacion.nextval,
               cCodGrupoCia_in,
               cCodCia_in,
               cCodLocal_in,
               cPidVendedor_in,
               cFechaVenta_in,
               cMontoVenta_in,
               cNumCuotas_in,
               cCodAutoriz_in,
               cTrack2_in,
               cCodAutorizPre_in,
               cValorPorCuota_in,
               cCaja_in,
               cTipoTrans_in,
               cCodServ_in,
               cNumObjPago_in,
               cNomCliente_in,
               cCodVoucher_in,
               cNumCompAnu_in,
               cFechCompAnu_in,
               cCodAutorizOrig_in,
               cTipoCambio_in,
               cNumTrace_in,
               cCodAlianza_in,
               cCodMonedaTrx_in,
               cMonEstPago_in,
               cDescConcepto_in,
               cEstConciliacion_in,
               SYSDATE,
               cUsuCreador_in,
               cCodLocalMigra_in);
        COMMIT;
   END;

   /*  Descripción : obtiene la Empresa de Traslado de Valores - ETV de un Local
      Fecha         Usuario       Comentario
      17/Oct/2013   LLEIVA        Creación
  */
  FUNCTION VTA_LISTA_CONCILIACION_NOK(cCodGrupoCia_in   IN  CHAR,
                                      cCodCia_in        IN  CHAR,
                                      cCodLocal_in      IN  CHAR)
  RETURN FarmaCursor
  IS
    cur FarmaCursor;
  BEGIN
      OPEN cur for
      select id || 'Ã' ||
             DECODE(cod_local,NULL,' ',cod_local) || 'Ã' ||
             DECODE(pid_vendedor,NULL,' ',pid_vendedor) || 'Ã' ||
             DECODE(pfecha_venta,NULL,' ',pfecha_venta) || 'Ã' ||
             DECODE(pmonto_venta,NULL,' ',pmonto_venta) || 'Ã' ||
             DECODE(pnum_cuotas,NULL,' ',pnum_cuotas) || 'Ã' ||
             DECODE(pcod_autorizacion,NULL,' ',pcod_autorizacion) || 'Ã' ||
             DECODE(ptrack2,NULL,' ',ptrack2) || 'Ã' ||
             DECODE(pcod_autorizacion_pre,NULL,' ',pcod_autorizacion_pre) || 'Ã' ||
             DECODE(pfpa_valorxcuota,NULL,' ',pfpa_valorxcuota) || 'Ã' ||
             DECODE(pcaja,NULL,' ',pcaja) || 'Ã' ||
             DECODE(ptipo_transaccion,NULL,' ',ptipo_transaccion) || 'Ã' ||
             DECODE(pcodiserv,NULL,' ',pcodiserv) || 'Ã' ||
             DECODE(pfpa_num_obj_pago,NULL,' ',pfpa_num_obj_pago) || 'Ã' ||
             DECODE(pnombclie,NULL,' ',pnombclie) || 'Ã' ||
             DECODE(pvoucher,NULL,' ',pvoucher) || 'Ã' ||
             DECODE(pnro_comp_anu,NULL,' ',pnro_comp_anu) || 'Ã' ||
             DECODE(pfech_comp_anu,NULL,' ',pfech_comp_anu) || 'Ã' ||
             DECODE(pcodiautoorig,NULL,' ',pcodiautoorig) || 'Ã' ||
             DECODE(pfpa_tipocambio,NULL,' ',pfpa_tipocambio) || 'Ã' ||
             DECODE(pfpa_nrotrace,NULL,' ',pfpa_nrotrace) || 'Ã' ||
             DECODE(pcod_alianza,NULL,' ',pcod_alianza) || 'Ã' ||
             DECODE(pcod_moneda_trx,NULL,' ',pcod_moneda_trx) || 'Ã' ||
             DECODE(pmon_estpago,NULL,' ',pmon_estpago) as resultado
      from vta_log_conciliacion
      where est_conciliacion != 'OK';
      RETURN cur;
  END;

  /*  Descripción : actualiza el estado del registro de log de conciliacion indicado
                    si el estado del reintento de conciliacion es OK
      Fecha         Usuario       Comentario
      17/Oct/2013   LLEIVA        Creación
  */
  PROCEDURE VTA_ACT_LOG_CONCILIACION(cCodLogConc_in  IN CHAR)
  IS
  BEGIN
        UPDATE vta_log_conciliacion
        SET est_conciliacion = 'OK'
        WHERE ID = cCodLogConc_in;
        COMMIT;
  EXCEPTION
  WHEN OTHERS THEN
       ROLLBACK;
  END;

  /*  Descripción : Obtiene el listado de ETVs presentes en el sistema
      Fecha         Usuario       Comentario
      27/Ene/2014   LLEIVA        Creación
  */
  FUNCTION CE_F_GET_LISTA_ETV
    RETURN FarmaCursor
    IS
         cur FarmaCursor;
    BEGIN
         OPEN cur for
         select T.COD_ETV || 'Ã' ||
                T.NOMBRE_ETV
                as resultado
         from pbl_etv t;

         RETURN cur;
    END;
    
      /****************************************************************************/
  FUNCTION CE_LISTA_DOC_ANUL(cCodGrupoCia_in	IN CHAR,
                                    cCodCia_in        IN CHAR,
                                    cCodLocal_in	  IN CHAR,
                                    cFechaCierreDia IN varchar2)
  RETURN FarmaCursor
  AS
    curCe FarmaCursor;
    tipo_nota_credito char(2) := '04';
    ind_si char(1) := 'S';
  BEGIN
    OPEN curCe FOR
            
            SELECT A.NUM_PED_VTA || 'Ã' ||
                                 A.FEC_PED_VTA || 'Ã' ||
                                 A.VAL_NETO_PED_VTA || 'Ã' ||
                                 NVL(B.NUM_COMP_PAGO_E, B.NUM_COMP_PAGO)  || 'Ã' ||
                                 C.DESC_COMP
            FROM VTA_PEDIDO_VTA_CAB A,
                                    VTA_COMP_PAGO B,
                                    VTA_TIP_COMP C
            WHERE A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
            AND A.COD_LOCAL = B.COD_LOCAL
            AND A.NUM_PED_VTA = B.NUM_PED_VTA
            AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
            AND C.TIP_COMP = B.TIP_COMP_PAGO
            AND A.COD_GRUPO_CIA = cCodGrupoCia_in
            AND A.COD_CIA = cCodCia_in
            AND TRUNC(A.FEC_PED_VTA) = trunc(to_DATE(cFechaCierreDia,'dd/MM/yyyy'))
            --AND A.IND_PEDIDO_ANUL <> ind_si
            AND B.TIP_COMP_PAGO <> tipo_nota_credito           
            AND A.NUM_PED_VTA IN (
                                                                  SELECT DISTINCT(R.NUM_PED_VTA_ORIGEN)
                                                                  FROM VTA_PEDIDO_VTA_CAB R
                                                                  WHERE TRUNC(R.FEC_PED_VTA) = to_DATE(cFechaCierreDia,'dd/MM/yyyy')
                                                                  AND R.TIP_COMP_PAGO = tipo_nota_credito
                                                                  )
            ORDER BY A.FEC_PED_VTA, NUM_COMP_PAGO ASC;

    RETURN curCe;
  END;
   /****************************************************************************/
  -- KMONCADA 22.06.2016 [CONV FAMISALUD] VA A CALCULAR POR COD DE FORMA DE PAGO PARA CONSIDERAR PUNTOS Y FAMISALUD
  FUNCTION GET_MONTO_TURNO_PTOS_REDONDEO(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cSecMovCaja_in   IN CHAR,
                                         cCodFormaPago_in IN VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE DEFAULT COD_FORMA_PAGO_REDONDEO_PTOS)
    RETURN NUMBER IS
    vTotal VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
  BEGIN
    
    SELECT NVL(SUM(FPP.IM_TOTAL_PAGO),0)
    INTO vTotal
    FROM VTA_FORMA_PAGO        FP,
         VTA_FORMA_PAGO_PEDIDO FPP,
         VTA_PEDIDO_VTA_CAB    CAB,
         CE_MOV_CAJA           CE
    WHERE CE.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CE.COD_LOCAL = cCodLocal_in
    AND CE.SEC_MOV_CAJA = cSecMovCaja_in
    AND FP.COD_GRUPO_CIA = CE.COD_GRUPO_CIA
    AND FP.COD_FORMA_PAGO = cCodFormaPago_in--COD_FORMA_PAGO_REDONDEO_PTOS
    AND FPP.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
    AND FPP.COD_LOCAL = CE.COD_LOCAL
    AND FPP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
    AND CAB.COD_GRUPO_CIA = FPP.COD_GRUPO_CIA
    AND CAB.COD_LOCAL = FPP.COD_LOCAL
    AND CAB.NUM_PED_VTA = FPP.NUM_PED_VTA
    AND CAB.EST_PED_VTA = 'C'
    AND CAB.SEC_MOV_CAJA = CE.SEC_MOV_CAJA_ORIGEN;
    
    RETURN vTotal;
  END;
  
  /****************************************************************************/
  -- KMONCADA 22.06.2016 [CONV FAMISALUD] VA A CALCULAR POR COD DE FORMA DE PAGO PARA CONSIDERAR PUNTOS Y FAMISALUD
  FUNCTION GET_MONTO_DIA_PTOS_REDONDEO(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cCierreDia_in    IN CHAR,
                                       cCodFormaPago_in IN VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE DEFAULT COD_FORMA_PAGO_REDONDEO_PTOS)
    RETURN NUMBER IS
    vTotal VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
  BEGIN
    
    SELECT NVL(SUM(GET_MONTO_TURNO_PTOS_REDONDEO(cCodGrupoCia_in =>  CE.COD_GRUPO_CIA, 
                                                 cCodLocal_in => CE.COD_LOCAL, 
                                                 cSecMovCaja_in => CE.SEC_MOV_CAJA,
                                                 cCodFormaPago_in => cCodFormaPago_in
           )),0)
      INTO vTotal
      FROM CE_MOV_CAJA CE
     WHERE CE.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CE.COD_LOCAL = cCodLocal_in
       AND CE.TIP_MOV_CAJA = 'C'
       AND CE.FEC_DIA_VTA = TO_DATE(cCierreDia_in,'dd/MM/yyyy');
    RETURN vTotal;
  END;
  
  
  
END;
/
