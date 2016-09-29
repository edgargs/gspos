CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CAJ_ANUL" AS

  TYPE FarmaCursor IS REF CURSOR;

  g_cNumPed PBL_NUMERA.COD_NUMERA%TYPE := '007';
  g_cNumPedDia PBL_NUMERA.COD_NUMERA%TYPE := '009';
  g_cCodMotKardex LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '106';
  g_cFormPagoEfectivo VTA_FORMA_PAGO_PEDIDO.COD_FORMA_PAGO%TYPE := '00001';
  g_cFormPagoDolares VTA_FORMA_PAGO_PEDIDO.COD_FORMA_PAGO%TYPE := '00002';

  g_nDiasPermitidosIntercambio INTEGER := 1;

  TIPO_COMPROBANTE_99		  CHAR(2):='99';

  INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';

  TIPO_PROD_VIRTUAL_TARJETA CHAR(1):='T';
  TIPO_PROD_VIRTUAL_RECARGA CHAR(1):='R';

  EST_PED_PENDIENTE  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='P';
	EST_PED_ANULADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='N';
	EST_PED_COBRADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='C';
	EST_PED_COB_NO_IMPR  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='S';

  --INI ASOSA - 13/10/2014 - PANHD
    TIPO_PROD_FINAL CONSTANT CHAR(1) := 'F';
    BAJA_ANUL_VTA_PROD CONSTANT CHAR(3) := '114';
    DEVOLUCION_VENTA CONSTANT CHAR(3) := '106';
  --INI ASOSA - 13/10/2014 - PANHD
  
  -- KMONCADA 13.04.2015 ANULACION DE PEDIDOS CON CUPONES USADOS
  TAB_IND_ANULA_CON_CUPON_USADOS INTEGER := 680;
  -- KMONCADA 27.10.2015 INDICADOR DE TIEMPO MAXIMO DE ANULACION DE PEDIDO EN DIAS
  TAB_IND_TIEMPO_MAX_ANULA INTEGER := 571;
  TAB_IND_ACTIVA_MAX_DIAS_ANULA INTEGER := 572;

  --Descripcion: Verifica si el pedido existe y se puede anular.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  --22/01/2007  LMESIA    Modificacion
  --31/03/2008  DUBILLUZ  Modificacion
    PROCEDURE CAJ_VERIFICA_PEDIDO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                nMontoVta_in    IN NUMBER,
                                nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                                cIndAnulaTodoPedido_in IN CHAR DEFAULT 'S',
                                cValMints_in         IN CHAR DEFAULT 'S');
  --Descripcion: Obtiene el listado de caomprobantes por pedido.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION CAJ_LISTA_CABECERA_PEDIDO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cNumPedVta_in IN CHAR,
                                     cNumCompPag IN CHAR,
                                     cFlagTipProcPago IN CHAR DEFAULT '0'--LTAVARA 03.09.14 12:00 FLAG PROCESO ELECTRONICO
                                     ) RETURN FarmaCursor;

  --Descripcion: Obtiene el nombre del cajero que cobro un pedido.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION CAJ_GET_CAJERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cSecMovCaja_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene el listado de caomprobantes por pedido.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION CAJ_LISTA_DETALLE_PEDIDO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cTipComp_in IN CHAR, cNumComp_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Verifica si el comprobante existe y se puede anular.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION CAJ_VERIFICA_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cTipComp_in     IN CHAR,
                                    cNumComp_in     IN CHAR,
                                    nMontoVta_in    IN NUMBER,
                                    nIndReclamoNavsat_in IN CHAR DEFAULT 'N'

                                    )
    RETURN VARCHAR2;

  --Descripcion: Obtiene la lista de cajas abiertas en el local.
  --Fecha       Usuario		Comentario
  --07/03/2006  ERIOS    	Creación
  FUNCTION CAJ_LISTA_CAJA_USUARIO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Anula Pedido y/o Comprobante.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS    	Creación
  PROCEDURE CAJ_ANULAR_PEDIDO(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   IN CHAR,
                              cTipComp_in     IN CHAR,
                              cNumComp_in     IN CHAR,
                              nMontoVta_in    IN NUMBER,
                              nNumCajaPago_in IN NUMBER,
                              vIdUsu_in       IN VARCHAR2,
                              nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                              cMotivoAnulacion_in  IN VARCHAR2,
                              cValMints_in      IN CHAR DEFAULT 'S');

  --Descripcion: Anula la cabecera del pedido y genera un pedido negativo.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS    	Creación
  FUNCTION CAJ_GET_PEDIDO_NEGATIVO_CAB(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cSecMovCaja_in IN CHAR,vIdUsu_in IN VARCHAR2) RETURN CHAR;

  --Descripcion: Anula los comprobantes de un pedido o solo el comprobante indicado.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS    	Creación
  PROCEDURE CAJ_ANULAR_COMPROBANTES(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cTipComp_in     IN CHAR,
                                    cNumComp_in     IN CHAR,
                                    cNumPedNeg_in   IN CHAR,
                                    cSecMovCaja_in  IN CHAR,
                                    vIdUsu_in       IN VARCHAR2,
                                    nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                                    cMotivoAnulacion_in IN VARCHAR2);

  --Descripcion: Anula el comprobante indicado, crea el detalle de pedido negativo.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS    	Creación
  --16/01/2007  LMESIA    Modificacion
  PROCEDURE CAJ_ANULAR_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR,
                                   cNumPedNeg_in   IN CHAR,
                                   cSecCompPago_in IN CHAR,
                                   cSecMovCaja_in  IN CHAR,
                                   vIdUsu_in       IN CHAR,
                                   nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                                   cMotivoAnulacion_in IN VARCHAR2);

  --Descripcion: Anula la cabecera del pedido.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS    	Creación
  PROCEDURE CAJ_ANULAR_CABECERA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,vIdUsu_in IN VARCHAR2,
                                    cMotivoAnulacion_in IN VARCHAR2);

  --Descripcion: Actualiza los montos del pedido negativo en la cabecera.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS    	Creación
  PROCEDURE CAJ_ACTUALIZAR_MONTOS_CABECERA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedNeg_in IN CHAR,cNumPed_in IN CHAR,cSecCompPago_in IN CHAR,vIdUsu_in IN VARCHAR2);

  --Descripcion: Anula las formas de pago.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS    	Creación
  PROCEDURE CAJ_ANULAR_FORMA_PAGO(cCodGrupoCia_in IN CHAR,
            cCodLocal_in IN CHAR,
            cNumPedVta_in IN CHAR,
            cTipComp_in IN CHAR,
            cNumComp_in IN CHAR,
            cNumPedNeg_in IN CHAR,
            vIdUsu_in IN VARCHAR2);

  --Descripcion: Anula Pedido Pendiente.
  --Fecha       Usuario		Comentario
  --13/03/2006  ERIOS    	Creación
  --07/07/2006  ERIOS    	Modificación:Corrección al borrar Respaldo Stock
  --16/01/2007  LMESIA    Modificación
  --21/11/2007 dubilluz  modificacion
  PROCEDURE CAJ_ANULAR_PEDIDO_PENDIENTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN CHAR,
                                        cModulo_in      IN CHAR DEFAULT 'V');

  --Descripcion: Busca un pedido pendiente, segun el numero y fecha.
  --Fecha       Usuario		Comentario
  --13/03/2006  ERIOS    	Creación
  FUNCTION CAJ_BUSCAR_PEDIDO_UNIR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedDia_in IN CHAR,vFecha_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Muestra el detalle de un pedido pendiente.
  --Fecha       Usuario		Comentario
  --13/03/2006  ERIOS    	Creación
  FUNCTION CAJ_GET_DETALLE_PEDIDO_UNIR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene un nuevo pedido para unir otros comprobantes.
  --Fecha       Usuario		Comentario
  --13/03/2006  ERIOS    	Creación
  --25/05/2006  MHUAYTA     Agrego campos tipo_comprobantes y tip_pedido
  FUNCTION CAJ_GET_NUMERO_PEDIDO_UNIR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,nMontoVta_in IN NUMBER,nTipoCam_in IN NUMBER,nNumCajaPago_in IN NUMBER,cTipComp_in IN CHAR, cTipPed_in IN CHAR, vIdUsu_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Unir los comprobantes en un nuevo pedido.
  --Fecha       Usuario		Comentario
  --13/03/2006  ERIOS    	Creación
  --18/01/2007  LMESIA   	Creación
  --21/11/2007 dubilluz  modificacion
  PROCEDURE CAJ_GET_UNIR_COMPROBANTE_UNIR(cCodGrupoCia_in    IN CHAR,
                                          cCodLocal_in       IN CHAR,
                                          cNumPedNuevoVta_in IN CHAR,
                                          cNumPedVta_in      IN CHAR,
                                          vIdUsu_in          IN CHAR,
                                          cModulo_in         IN CHAR DEFAULT 'V');

  --Descripcion: Obtiene el listado de productos para una nota de credito.
  --Fecha       Usuario		Comentario
  --14/03/2006  ERIOS    	Creación
  FUNCTION CAJ_LISTA_DETALLE_NOTA_CREDITO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cTipComp_in IN CHAR, cNumComp_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Agrega cabcera de un pedido para Nota Credito.
  --Fecha       Usuario		Comentario
  --15/03/2006  ERIOS    	Creación
  FUNCTION CAJ_AGREGAR_CAB_NOTA_CREDITO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumVtaAnt_in IN CHAR,nTipoCam_in IN NUMBER,vIdUsu_in IN VARCHAR2,nNumCajaPago_in IN NUMBER,cMotivoAnulacion IN VARCHAR2) RETURN CHAR;

  --Descripcion: Agrega detalle de un pedido para Nota Credito.
  --Fecha       Usuario		Comentario
  --15/03/2006  ERIOS    	Creacion
  --05/06/2008  ERIOS    	Modificacion: nSecDetPed_in
  --22.10.2014  KMONCADA  modificacion: se convierte en funcion para devolver numero de nc generada
  --                      devolvera N si presenta error, G cuando se trate de una guia y el nro de nc en los demas casos
   FUNCTION CAJ_AGREGAR_DET_NC(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in IN CHAR,
                                       cNumVtaAnt_in IN CHAR,
                                       cNumVta_in IN CHAR,
                                       -- estos valores no se van a usar
                                       cCodProd_in IN CHAR,nCantProd_in IN NUMBER,nTotal_in IN NUMBER,vIdUsu_in IN VARCHAR2,nSecDetPed_in IN NUMBER
                                       ,
                                       nNumCajaPago_in IN NUMBER,
                                       cFlagElectronico in CHAR DEFAULT '0',--LTAVARA 03.09.14 20:20 FLAG PROCESO ELECTRONICO
                                       cCodTipMotivoNotaE in CHAR DEFAULT '6'--LTAVARA 15.10.2014 TIPO MOTIVO NC
                                       )RETURN VARCHAR2
   ;

  PROCEDURE CAJ_COBRA_NOTA_CREDITO(cCodGrupoCia_in 	 IN CHAR,
                                   cCodLocal_in    	 IN CHAR,
                                   cNumPedVta_in   	 IN CHAR,
                                   cNumVtaAnt_in IN CHAR,
                                   cSecMovCaja_in    	 IN CHAR,
                                   cCodNumera_in 	 	 IN CHAR,
                                   cTipCompPago_in	 IN CHAR,
                                   cCodMotKardex_in    IN CHAR,
                                   cTipDocKardex_in    IN CHAR,
                                   cCodNumeraKardex_in IN CHAR,
                                   cUsuCreaCompPago_in IN CHAR,
                                   nNumCajaPago_in IN NUMBER,
                                   cTipCompAnul_in IN CHAR,
                                   cNumCompAnul_in IN CHAR);

  --Descripcion:
  --Fecha       Usuario		Comentario
  --19/04/2006  ERIOS    	Creación
  --16/01/2007  LMESIA    Modificacion
  PROCEDURE CAJ_ACTUALIZA_STK_PROD_DETALLE(cCodGrupoCia_in 	   IN CHAR,
                                           cCodLocal_in    	   IN CHAR,
                                           cNumPedVta_in   	   IN CHAR,
                                           cNumVtaAnt_in       IN CHAR,
                                           cCodMotKardex_in    IN CHAR,
                                           cTipDocKardex_in    IN CHAR,
                                           cCodNumeraKardex_in IN CHAR,
                                           cUsuModProdLocal_in IN CHAR);

  --Descripcion: Obtiene las series asignadas al local por tipo de documento.
  --Fecha       Usuario		Comentario
  --19/04/2006  ERIOS    	Creación
  FUNCTION CAJ_GET_SERIE_ANUL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipDoc_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Determina si existe aun productos para generar Nota de Credito.
  --Fecha       Usuario		Comentario
  --04/05/2006  ERIOS    	Creación
  FUNCTION CAJ_GET_PRODS_REST(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedido_in IN CHAR,cNumComp_in IN CHAR, cTipDoc_in IN CHAR) RETURN NUMBER;

  --Descripcion: Intercambia la numeracion entre dos comprobantes.
  --Fecha       Usuario		Comentario
  --17/05/2006  ERIOS    	Creación
  --28/08/2007  DUBILLUZ  MODIFICACION
  --27/09/2007  DUBILLUZ  MODIFICACION
  PROCEDURE CAJ_INTERCAMBIO_COMPROBANTE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                      cNumDocA_in IN CHAR, nMonDocA_in IN NUMBER,
                                      cNumDocB_in IN CHAR, nMonDocB_in IN NUMBER,
                                      cTipComp_in IN CHAR,cIdUsu_in IN CHAR);
  --Descripcion: Intercambia la numeracion entre dos comprobantes.
  --Fecha       Usuario		Comentario
  --19/06/2006  ERIOS    	Creación
  PROCEDURE CAJ_ACT_CABECERA_NC(cCodGrupoCia_in 	   IN CHAR,
                               cCodLocal_in    	   IN CHAR,
                               cNumPedVta_in   	   IN CHAR,
                               nTotalBruto_in IN NUMBER,
                               nTotalNeto_in IN NUMBER,
                               nTotalIgv_in IN NUMBER,
                               nTotalDscto_in IN NUMBER,
                               vIdUsu_in IN VARCHAR2);

  --Descripcion: Obtiene las impresoras activas en el local.
  --Fecha       Usuario		Comentario
  --11/07/2006  ERIOS    	CreaciÃ³n
  FUNCTION CAJ_GET_IMPRESORAS(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)RETURN FarmaCursor;

  --Descripcion: Obtiene el tiempo maximo tiempo para anulacion de pedido.
  --Fecha       Usuario    Comentario
  --09/11/2007  DUBILLUZ    Creacion
  --31/03/2008  DUBILLUZ    moficacion
  FUNCTION CAJ_GET_TIEMPO_MAX_ANULACION(cGrupoCia_in   IN CHAR,
                                        cCodLocal_in   IN CHAR,
                                        cNumPedVta_in  IN CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene el Numero de Pedido
  --Fecha       Usuario    Comentario
  --31/03/2008  DUBILLUZ    Creacion
  FUNCTION CAJ_GET_NUMERO_PEDIDO(cGrupoCia_in   IN CHAR,
                                 cCodLocal_in   IN CHAR,
                                 cNumComp_in  IN CHAR)
  RETURN VARCHAR2;

 /* --Descripcion: Anula los cupones en matriz
  --Fecha       Usuario    Comentario
  --30/07/2008  ERIOS      Creacion
  PROCEDURE CUPONES_ANULADOS_PEDIDO(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   IN CHAR,
                              vIdUsu_in IN CHAR );

  --Descripcion: Verifica cupones anulados
  --Fecha       Usuario    Comentario
  --30/07/2008  ERIOS      Creacion
  FUNCTION CUPONES_ANULADOS(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR)
  RETURN NUMBER;*/

PROCEDURE CAJ_P_ANULA_PED_SIN_RESPALDO(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN CHAR,
                                        cModulo_in      IN CHAR DEFAULT 'V');
--Descripcion: Verifica si existe productos canje en el pedido
  --Fecha       Usuario		Comentario
  --18/12/2008  JCORTEZ    	Creación
  FUNCTION CAJ_EXISTE_PROD_CANJE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR)
  RETURN INTEGER;

  --Descripcion: Verifica si existe productos canje y regalo en el pedido
  --Fecha       Usuario		Comentario
  --18/12/2008  JCORTEZ    	Creación
  FUNCTION CAJ_EXIST_PROD_CANJ_REG(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cDni IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Anula el pedido Fidelizado
  --Fecha       Usuario		Comentario
  --18/12/2008  JCORTEZ    	Creación
  PROCEDURE CAJ_ANULA_PED_FIDELIZADO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    vIdUsu_in       IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cDni_in         IN CHAR,
                                    cIndLocal_in    IN CHAR);

  --Descripcion: Se obtiene tipo de pedido Fidelizado
  --Fecha       Usuario		Comentario
  --18/12/2008  JCORTEZ    	Creación
  FUNCTION CAJ_TIPO_PED_FIDELIZADO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in   IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Se anula tipo canje
  --Fecha       Usuario		Comentario
  --18/12/2008  JCORTEZ    	Creación
  PROCEDURE CAJ_ANUL_CANJE(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR,
                          vIdUsu_in       IN CHAR,
                          cNumPedVta_in   IN CHAR,
                          cDni_in         IN CHAR,
                          cIndLocal_in    IN CHAR);

  --Descripcion: Verifica si el pedido existe y se puede anular.
  --Fecha       Usuario		Comentario
  --06/07/2009  JCHAVEZ 	Creación
  PROCEDURE CAJ_VERIFICA_PEDIDO_ANU(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                nMontoVta_in    IN NUMBER,
                                cTipoCompPago IN CHAR DEFAULT '%'
                                );
  --Descripcion: Obtiene el listado de comprobantes por pedido.
  --Fecha       Usuario		Comentario
  --06/07/2009  JCHAVEZ   Creación
  FUNCTION CAJ_LISTA_DETALLE_PEDIDO_ANU(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cTipComp_in IN CHAR, cNumComp_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la caja y el turno del ticket anulado.
  --Fecha       Usuario		Comentario
  --06/07/2009  JCHAVEZ   Creación
  FUNCTION CAJA_TURNO_PEDIDO_ANULADO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene el NroConvenio y codCliente de Convenio.
  --Fecha       Usuario	  	Comentario
  --08/09/2009  JMIRANDA    Creación
  FUNCTION CAJ_F_OBT_DAT_CONV_ANUL (cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cNumPedVta_in IN CHAR,
                                   vIdUsu_in IN VARCHAR2)
  RETURN VARCHAR2;

   PROCEDURE CAJ_ANULAR_PEDIDO_BTL_MF(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   IN CHAR,
                              cTipComp_in     IN CHAR,
                              cNumComp_in     IN CHAR,
                              nMontoVta_in    IN NUMBER,
                              nNumCajaPago_in IN NUMBER,
                              vIdUsu_in       IN VARCHAR2,
                              nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                              cMotivoAnulacion_in IN VARCHAR2,
                              cValMints_in  IN CHAR DEFAULT 'S' );
  --Descripcion: Obtiene motivos para la NC.
  --Fecha       Usuario	  	Comentario
  --09/09/2009  LTAVARA    Creación

  FUNCTION CAJ_LISTA_MOTIVO_NC(cCodMaestro_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Metodo para que registre el movimiento de kardex de  los productos perecederos
  --Fecha            Usuario		Comentario
  --13/10/2014  ASOSA    	Creación
  PROCEDURE CAJ_ACTUALIZA_STK_PROD_COMP(cCodGrupoCia_in 	   IN CHAR,
                                           cCodLocal_in    	   IN CHAR,
                                           cNumPedVta_in   	   IN CHAR,
                                           cNumVtaAnt_in       IN CHAR,
                                           cTipDocKardex_in    IN CHAR,
                                           cCodNumeraKardex_in IN CHAR,
                                           cUsuModProdLocal_in IN CHAR);

  --Descripcion: Metodo para que registre el movimiento de kardex de  los productos perecederos
  --Fecha            Usuario		Comentario
  --13/10/2014  ASOSA    	Creación
  PROCEDURE CAJ_ANULAR_COMPR_TICO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR,
                                   cNumPedNeg_in   IN CHAR,
                                   cSecCompPago_in IN CHAR,
                                   cSecMovCaja_in  IN CHAR,
                                   vIdUsu_in       IN CHAR,
                                   nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                                   cMotivoAnulacion_in IN VARCHAR2);
  
  PROCEDURE VERIFICAR_DET_NC(cCodGrupoCia_in CHAR,
                            cCodLocal_in    CHAR,
                            cNumPedVta_in   CHAR);
  
  -- DESCRIPCION: METODO QUE ANULA CUPONES EMITIDOS POR PEDIDO, SE COPIA PROCEDIMIENTO DE ANULACION ANTIGUO
  -- FECHA        USUARIO    COMENTARIO
  -- 12.04.2015   KMONCADA   CREACION.
  PROCEDURE CAJ_ANULA_CUPONES(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                              cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                              cNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                              vIdUsu_in       IN VARCHAR2);
  
  FUNCTION F_IND_ANULA_CON_CUPONES_USADOS
    RETURN CHAR;

  --Descripcion: Obtiene serie auxiliar
  --Fecha       Usuario		Comentario
  --11/05/2016  ERIOS    	Creacion	
  FUNCTION F_GET_SERIE_AUX_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN CHAR;
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CAJ_ANUL" AS


  /* ********************************************************************************************** */
  PROCEDURE CAJ_VERIFICA_PEDIDO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                nMontoVta_in    IN NUMBER,
                                nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                                cIndAnulaTodoPedido_in IN CHAR DEFAULT 'S',
                                cValMints_in         IN CHAR DEFAULT 'S')
  AS
    v_cEstPedido  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_nCantCompAnul INTEGER;

    CURSOR curProdVirtual IS
           SELECT DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                  NVL(VIR.TIP_PROD_VIRTUAL,' ') TIPO,
                  SYSDATE - CAB.FEC_PED_VTA FECHA
           FROM   VTA_PEDIDO_VTA_DET DET,
                  VTA_PEDIDO_VTA_CAB CAB,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
           AND    DET.COD_LOCAL = CAB.COD_LOCAL
           AND    DET.NUM_PED_VTA = CAB.NUM_PED_VTA
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+);

    --VARIABLE PARA COLOCAR EL TIEMPO LIMITE DE ANULACION
    --09/11/2007 DUBILLUZ CREACION
    TIEMPO_MAXIMO  VARCHAR2(30);

    v_nCantCuponU INTEGER;
    vIndValidaDiasAnula CHAR(1);
    vMaxDiasAnula NUMBER;
    vPermiteAnular CHAR(1);
  BEGIN
    -- KMONCADA 27.10.2015 VALIDACION DE PLAZO MAXIMO EN DIAS PARA ANULAR PEDIDOS.
    BEGIN
      SELECT NVL(GRAL.LLAVE_TAB_GRAL,'N')
      INTO vIndValidaDiasAnula
      FROM PBL_TAB_GRAL GRAL
      WHERE GRAL.ID_TAB_GRAL = TAB_IND_ACTIVA_MAX_DIAS_ANULA; 
    EXCEPTION 
      WHEN OTHERS THEN
        vIndValidaDiasAnula := 'N';
    END;
    
    IF vIndValidaDiasAnula = 'S' THEN 
      BEGIN
        SELECT TO_NUMBER(NVL(GRAL.LLAVE_TAB_GRAL,'0'),'9999990.00')
        INTO vMaxDiasAnula
        FROM PBL_TAB_GRAL GRAL
        WHERE GRAL.ID_TAB_GRAL = TAB_IND_TIEMPO_MAX_ANULA; 
      EXCEPTION
        WHEN OTHERS THEN
          vMaxDiasAnula := 7;
      END;
      
      SELECT 
        CASE 
          WHEN (TRUNC(SYSDATE) - TRUNC(CAB.FEC_PED_VTA))<= vMaxDiasAnula THEN
            'S'
          ELSE
            'N'
        END
      INTO vPermiteAnular
      FROM VTA_PEDIDO_VTA_CAB CAB
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA = cNumPedVta_in;
      
      IF vPermiteAnular = 'N' THEN
        RAISE_APPLICATION_ERROR(-20030,'¡No puede Anular este Pedido!'||chr(13)||
                                       'Plazo maximo que permite anular superado.');
      END IF;
      
    END IF;
    
    --se obtiene el tiempo maximo para anular un pedido recarga virtual
    --en minutos
    -- se modifico el modo de obtener el tie,pop
    TIEMPO_MAXIMO :=
      Farma_Gral.GET_TIEMPO_MAX_ANULACION(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in);


    dbms_output.put_line('tiempo maximo ' || TIEMPO_MAXIMO);

       SELECT EST_PED_VTA,
              IND_PEDIDO_ANUL
       INTO   v_cEstPedido,
              v_cIndAnulado
       FROM   VTA_PEDIDO_VTA_CAB
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    NUM_PED_VTA = cNumPedVta_in
       AND    VAL_NETO_PED_VTA = nMontoVta_in;

       IF v_cEstPedido <> 'C' THEN
          RAISE_APPLICATION_ERROR(-20002,'El Pedido no ha sido cobrado. ¡No puede Anular este Pedido!');
       END IF;

       IF v_cIndAnulado = 'S' THEN
          RAISE_APPLICATION_ERROR(-20003,'El Pedido ya está anulado. ¡No puede Anular este Pedido!');
       END IF;
       -- 21/11/2007 JOLIVA
       -- Si se va a anular todo el pedido, no pueden existir comprobantes anulados del mismo
       IF cIndAnulaTodoPedido_in = 'S' THEN
           --15/08/2007  ERIOS  Si se ha anulado una parte del pedido, no puede anular el pedido.
           SELECT COUNT(*)
             INTO v_nCantCompAnul
           FROM VTA_COMP_PAGO
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND COD_LOCAL = cCodLocal_in
                 AND NUM_PED_VTA = cNumPedVta_in
                 AND IND_COMP_ANUL = 'S';
            IF v_nCantCompAnul > 0 THEN
              RAISE_APPLICATION_ERROR(-20035,'Debe anular cada comprobante, porque ya anulado parte del pedido.');
            END IF;

       END IF;

       IF nIndReclamoNavsat_in = INDICADOR_NO THEN
         FOR v_rCurProdVirtual IN curProdVirtual
         LOOP
             IF v_rCurProdVirtual.IND_PROD_VIR = INDICADOR_SI THEN
                IF v_rCurProdVirtual.TIPO = TIPO_PROD_VIRTUAL_TARJETA THEN
                   RAISE_APPLICATION_ERROR(-20004,'El pedido cuenta con un producto del tipo Tarjeta Virtual. No se puede anular.');
                ELSIF v_rCurProdVirtual.TIPO = TIPO_PROD_VIRTUAL_RECARGA THEN
--                   IF v_rCurProdVirtual.FECHA > 0.007 THEN --0.007 INDICA 10 MINUTOS
                   IF cValMints_in='S' AND v_rCurProdVirtual.FECHA > (TO_NUMBER(TIEMPO_MAXIMO)/24/60) THEN --0.007 INDICA 10 MINUTOS
                      RAISE_APPLICATION_ERROR(-20005,'Este pedido fue cobrado hace mas de ' || TIEMPO_MAXIMO || ' minutos. No se puede anular.');
                   END IF;
                END IF;
             END IF;
         END LOOP;
       END IF;

    --30/07/2008 ERIOS Verifica si no ha usados cupones
    SELECT COUNT(*) INTO v_nCantCuponU
      FROM VTA_CAMP_PEDIDO_CUPON C,
           VTA_CUPON O
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_PED_VTA = cNumPedVta_in
            AND C.ESTADO = 'E'
            AND C.IND_IMPR = 'S'
            AND C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
            AND C.COD_CUPON = O.COD_CUPON
            AND O.ESTADO = 'U';
    /*IF v_nCantCuponU > 0 THEN
      RAISE_APPLICATION_ERROR(-20015,'El pedido generó cupones y ya fueron usados. No puede anular el comprobante.');
    END IF;*/

  EXCEPTION
       WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20001,'No se encuentra ningun Pedido con estos datos. ¡Verifique!');
  END;

  /* ********************************************************************************************** */

  FUNCTION CAJ_LISTA_CABECERA_PEDIDO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                                        cNumPedVta_in IN CHAR ,
                                                        cNumCompPag IN CHAR,
                                                       cFlagTipProcPago IN CHAR DEFAULT '0'--LTAVARA 03.09.14 12:00 FLAG PROCESO ELECTRONICO
                                                        )
  RETURN FarmaCursor
  IS
    curCab FarmaCursor;
  BEGIN
    OPEN curCab FOR
    SELECT A.NUM_PED_VTA || 'Ã' ||
           TO_CHAR(FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
           TO_CHAR(VAL_NETO_PED_VTA,'999,990.000') || 'Ã' ||
           NVL(RUC_CLI_PED_VTA,' ') || 'Ã' ||
           NVL(NOM_CLI_PED_VTA,' ') || 'Ã' ||
           DECODE(A.SEC_MOV_CAJA,NULL,' ',CAJ_GET_CAJERO(cCodGrupoCia_in,cCodLocal_in,A.SEC_MOV_CAJA)) || 'Ã' ||
           NVL(B.DESC_CORTA_CONV,' ') || 'Ã' ||
           C.TIP_COMP_PAGO || 'Ã' ||
           TIP_PED_VTA || 'Ã' ||
           IND_DELIV_AUTOMATICO  || 'Ã' ||
           NVL(IND_CAMP_ACUMULADA,' ') || 'Ã' || --JCORTEZ 18.12.2008
           NVL(DNI_CLI,' ')

    FROM VTA_PEDIDO_VTA_CAB A,
         CON_MAE_CONVENIO  B,
         VTA_COMP_PAGO C
    WHERE A.COD_GRUPO_CIA=C.COD_GRUPO_CIA
          AND A.COD_LOCAL=C.COD_LOCAL
          AND A.NUM_PED_VTA=C.NUM_PED_VTA
          AND A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_LOCAL = cCodLocal_in
          AND A.NUM_PED_VTA = cNumPedVta_in
          AND
               DECODE(cFlagTipProcPago,'1',C.NUM_COMP_PAGO_E,C.NUM_COMP_PAGO)=cNumCompPag--LTAVARA, valida si el documento es electronico
          AND A.COD_CONVENIO=B.COD_CONVENIO(+);


    RETURN curCab;
  END;
  /* ********************************************************************************************** */
  FUNCTION CAJ_GET_CAJERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cSecMovCaja_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vNom VARCHAR2(150);
  BEGIN
    SELECT U.NOM_USU || ' ' || U.APE_PAT INTO v_vNom
    FROM PBL_USU_LOCAL U, CE_MOV_CAJA C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND SEC_MOV_CAJA = cSecMovCaja_in
          AND U.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND U.COD_LOCAL = C.COD_LOCAL
          AND U.SEC_USU_LOCAL = C.SEC_USU_LOCAL;
    RETURN v_vNom;
  END;


  /* ********************************************************************************************** */
  FUNCTION CAJ_LISTA_DETALLE_PEDIDO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cTipComp_in IN CHAR, cNumComp_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT D.COD_PROD || 'Ã' ||
           R.DESC_PROD || 'Ã' ||
           D.UNID_VTA || 'Ã' ||
           TO_CHAR(D.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
           D.CANT_ATENDIDA || 'Ã' ||
           TO_CHAR(D.VAL_PREC_TOTAL,'999,990.000')
    FROM VTA_PEDIDO_VTA_DET D, VTA_COMP_PAGO C, LGT_PROD_LOCAL P, LGT_PROD R
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND D.EST_PED_VTA_DET = 'A'
          AND C.TIP_COMP_PAGO LIKE cTipComp_in
          AND C.NUM_COMP_PAGO LIKE cNumComp_in
          AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND D.COD_LOCAL = C.COD_LOCAL
          AND D.NUM_PED_VTA = C.NUM_PED_VTA
          AND D.SEC_COMP_PAGO = C.SEC_COMP_PAGO
          AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND D.COD_LOCAL = P.COD_LOCAL
          AND D.COD_PROD = P.COD_PROD
          AND P.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND P.COD_PROD = R.COD_PROD;

    RETURN curDet;
  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_VERIFICA_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cTipComp_in     IN CHAR,
                                    cNumComp_in     IN CHAR,
                                    nMontoVta_in    IN NUMBER,
                                    nIndReclamoNavsat_in IN CHAR DEFAULT 'N')
    RETURN VARCHAR2
  IS
    v_cNumPed VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    v_nMontoPed VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_cIndPedConvenio VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE;

    v_nCantCupon INTEGER;
    v_nCantComp INTEGER;
    v_nCantCuponU INTEGER;
    nCantProdVirtual INTEGER;
  BEGIN
       SELECT C.NUM_PED_VTA,
              (SELECT VAL_NETO_PED_VTA
               FROM VTA_PEDIDO_VTA_CAB
               WHERE NUM_PED_VTA = C.NUM_PED_VTA AND COD_GRUPO_CIA =cCodGrupoCia_in AND COD_LOCAL=cCodLocal_in)
       INTO   v_cNumPed,
              v_nMontoPed
       FROM   VTA_COMP_PAGO C
       WHERE  C.TIP_COMP_PAGO = cTipComp_in
       AND    C.NUM_COMP_PAGO = cNumComp_in
       AND    C.VAL_NETO_COMP_PAGO + C.VAL_REDONDEO_COMP_PAGO = nMontoVta_in
       AND    C.IND_COMP_ANUL = 'N'
       AND    C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    C.COD_LOCAL = cCodLocal_in;

    --31/05/2007 ERIOS Se verifica que el pedido no sea del tipo convenio.
    SELECT C.IND_PED_CONVENIO INTO v_cIndPedConvenio
    FROM VTA_PEDIDO_VTA_CAB C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.NUM_PED_VTA = v_cNumPed;
    IF v_cIndPedConvenio = 'S' THEN
      RAISE_APPLICATION_ERROR(-20012,'Este es un pedido convenio, debe anularse por pedido.');
    END IF;

    --07/01/2008 JCALLO Se verifica que el pedido no sea del tipo VIRTUAL.
    SELECT nvl(COUNT(1),0)
    INTO   nCantProdVirtual
    FROM   LGT_PROD_VIRTUAL VIR
    WHERE  VIR.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    VIR.COD_PROD IN (SELECT COD_PROD
                            FROM   VTA_PEDIDO_VTA_DET DET
                            WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND    DET.COD_LOCAL = cCodLocal_in
                            AND    DET.NUM_PED_VTA = v_cNumPed);
    IF nCantProdVirtual > 0 THEN
      RAISE_APPLICATION_ERROR(-20099,'Este es un pedido con producto virtual, debe anularse por pedido completo.');
    END IF;

    --30/07/2008 ERIOS Verifica que no tenga cupones
    SELECT COUNT(*) INTO v_nCantComp
    FROM VTA_COMP_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = v_cNumPed;

    SELECT COUNT(*) INTO v_nCantCupon
    FROM VTA_CAMP_PEDIDO_CUPON
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = v_cNumPed
          AND ESTADO = 'E'
          AND IND_IMPR = 'S';

    IF v_nCantComp > 1 AND v_nCantCupon > 0 THEN
      SELECT COUNT(*) INTO v_nCantCuponU
      FROM VTA_CAMP_PEDIDO_CUPON C,
           VTA_CUPON O
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_PED_VTA = v_cNumPed
            AND C.ESTADO = 'E'
            AND C.IND_IMPR = 'S'
            AND C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
            AND C.COD_CUPON = O.COD_CUPON
            AND O.ESTADO = 'U';
      /*IF v_nCantCuponU > 0 THEN
        RAISE_APPLICATION_ERROR(-20013,'El pedido generó cupones y ya fueron usados. No puede anular el comprobante.');
      ELSE      */
        RAISE_APPLICATION_ERROR(-20014,'Este comprobante ha generado cupones, debe anularse por pedido.');
      --END IF;
    END IF;

    -- 21/11/2007 JOLIVA
    -- Verifica algunos datos del pedido
       CAJ_VERIFICA_PEDIDO(cCodGrupoCia_in,cCodLocal_in,v_cNumPed,v_nMontoPed, nIndReclamoNavsat_in,'N');

    RETURN v_cNumPed;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20011,'No se encuentra ningun Comprobante con estos datos. ¡Verifique!');
      RETURN v_cNumPed;
  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_LISTA_CAJA_USUARIO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    SELECT C.SEC_USU_LOCAL || 'Ã' ||
            CAJ_GET_CAJERO(cCodGrupoCia_in, cCodLocal_in, C.SEC_MOV_CAJA) || 'Ã' ||
            C.NUM_CAJA_PAGO || 'Ã' ||
            M.NUM_TURNO_CAJA
    FROM VTA_CAJA_PAGO C, CE_MOV_CAJA M
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.IND_CAJA_ABIERTA = 'S'
          AND TO_CHAR(M.FEC_DIA_VTA,'dd/MM/yyyy') = TO_CHAR(SYSDATE,'dd/MM/yyyy')
          AND C.COD_GRUPO_CIA = M.COD_GRUPO_CIA
          AND C.COD_LOCAL = M.COD_LOCAL
          AND C.SEC_MOV_CAJA = M.SEC_MOV_CAJA;
    RETURN curCaj;
  END;

  /* ********************************************************************************************** */
  PROCEDURE CAJ_ANULAR_PEDIDO(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   IN CHAR,
                              cTipComp_in     IN CHAR,
                              cNumComp_in     IN CHAR,
                              nMontoVta_in    IN NUMBER,
                              nNumCajaPago_in IN NUMBER,
                              vIdUsu_in       IN VARCHAR2,
                              nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                              cMotivoAnulacion_in IN VARCHAR2,
                              cValMints_in  IN CHAR DEFAULT 'S' )
  AS
    v_cNumPedAux VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;

    v_cCajaAbierta VTA_CAJA_PAGO.IND_CAJA_ABIERTA%TYPE;
    v_cSecUsuLocal VTA_CAJA_PAGO.SEC_USU_LOCAL%TYPE;
    v_cSecMovCaja_in VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;

    v_nNumPedNegativo VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    --agregado para validar la fecha y el secuencial de movimiento de caja diario
    v_cIndCorrecto CHAR(1):='N';
    v_dFecPedVta VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;

    v_nValBruto VTA_PEDIDO_VTA_CAB.VAL_BRUTO_PED_VTA%TYPE;
    v_nValNeto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nValRedondeo VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_nValIgv VTA_PEDIDO_VTA_CAB.VAL_IGV_PED_VTA%TYPE;
    v_nValDscto VTA_PEDIDO_VTA_CAB.VAL_DCTO_PED_VTA%TYPE;
    v_nCantItems VTA_PEDIDO_VTA_CAB.CANT_ITEMS_PED_VTA %TYPE;
    
    --DUBILLUZ 28.05.2009
    v_dFecPedVtaOrigen VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
    vNumPedOrigen VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    vAhorroPedido number;
  BEGIN
    --VERIFICA SI EL PEDIDO EXISTE Y NO HAYA SIDO ANULADO
    IF cTipComp_in = '%' AND cNumComp_in = '%' THEN
      CAJ_VERIFICA_PEDIDO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,nMontoVta_in, nIndReclamoNavsat_in, 'S',cValMints_in);
    ELSE
      v_cNumPedAux:=CAJ_VERIFICA_COMPROBANTE(cCodGrupoCia_in,cCodLocal_in,cTipComp_in, cNumComp_in,nMontoVta_in, nIndReclamoNavsat_in);
    END IF;
    -- VERIFICA SI LA CAJA NO  HA SIDO CERRADA Y AUN PERMANECE ABIERTA
    SELECT SEC_USU_LOCAL, SEC_MOV_CAJA INTO v_cSecUsuLocal,v_cSecMovCaja_in
    FROM VTA_CAJA_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_CAJA_PAGO = nNumCajaPago_in;
    v_cCajaAbierta:= Ptoventa_Caj.CAJ_IND_CAJA_ABIERTA_FORUPDATE(cCodGrupoCia_in,cCodLocal_in, v_cSecUsuLocal, v_cSecMovCaja_in);
    IF v_cCajaAbierta='N' THEN
      RAISE_APPLICATION_ERROR(-20021,'LA CAJA NO SE ENCUENTRA ABIERTA.');
    ELSIF v_cCajaAbierta='S' THEN
      --OBTENER EL PEDIDO NEGATIVO
      v_nNumPedNegativo:=CAJ_GET_PEDIDO_NEGATIVO_CAB(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,v_cSecMovCaja_in,vIdUsu_in);
      --ANULAR LOS COMPROBANTES Y ASIGNARLES EL PEDIDO NEGATIVO
      CAJ_ANULAR_COMPROBANTES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cTipComp_in, cNumComp_in, v_nNumPedNegativo ,v_cSecMovCaja_in,vIdUsu_in,nIndReclamoNavsat_in,cMotivoAnulacion_in);
      --ANULAR CABECERA
      CAJ_ANULAR_CABECERA(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,vIdUsu_in,cMotivoAnulacion_in);
      --ANULAR LA FORMA DE PAGO CON MONTOS NEGATIVOS
      CAJ_ANULAR_FORMA_PAGO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cTipComp_in, cNumComp_in,v_nNumPedNegativo,vIdUsu_in);

		--ERIOS 30.10.2014 Se actualiza los montos del pedido original
      SELECT VAL_BRUTO_PED_VTA*-1,VAL_NETO_PED_VTA*-1,VAL_REDONDEO_PED_VTA*-1,
      VAL_IGV_PED_VTA*-1,VAL_DCTO_PED_VTA*-1,CANT_ITEMS_PED_VTA
      INTO v_nValBruto,v_nValNeto,v_nValRedondeo,
      v_nValIgv,v_nValDscto,v_nCantItems
      FROM VTA_PEDIDO_VTA_CAB
      WHERE cod_grupo_cia = cCodGrupoCia_in
      AND   cod_local     = cCodLocal_in
      AND   NUM_PED_VTA   = cNumPedVta_in;


      UPDATE VTA_PEDIDO_VTA_CAB cab
      SET USU_MOD_PED_VTA_CAB = vIdUsu_in,
          FEC_MOD_PED_VTA_CAB = SYSDATE,
          VAL_BRUTO_PED_VTA =  v_nValBruto,
          VAL_NETO_PED_VTA =  v_nValNeto,
          VAL_REDONDEO_PED_VTA =  v_nValRedondeo,
          VAL_IGV_PED_VTA  =  v_nValIgv,
          VAL_DCTO_PED_VTA =  v_nValDscto,
          CANT_ITEMS_PED_VTA = v_nCantItems
      WHERE cab.cod_grupo_cia = cCodGrupoCia_in
      AND   cab.cod_local     = cCodLocal_in
      AND   CAB.NUM_PED_VTA   = v_nNumPedNegativo;		
      
      /**OBTENIENDO LA FECHA DEL REGISTRO DEL PEDIDO NEGATIVO**/
      SELECT cab.fec_ped_vta,cab.num_ped_vta_origen
      into   v_dFecPedVta,vNumPedOrigen
      FROM  VTA_PEDIDO_VTA_CAB cab
      WHERE cab.cod_grupo_cia = cCodGrupoCia_in
      AND   cab.cod_local     = cCodLocal_in
      AND   CAB.NUM_PED_VTA   = v_nNumPedNegativo;

      /**VERIFICAR SI EL PEDIDO GENERADO CORRESPONDIENTE ESTA CON LA FECHA DE APERTURA **/
      BEGIN
        SELECT 'S' INTO v_cIndCorrecto
        FROM CE_MOV_CAJA C
        WHERE TRUNC(C.Fec_Dia_Vta) = TRUNC(v_dFecPedVta)
        AND   C.SEC_MOV_CAJA       = v_cSecMovCaja_in;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_cIndCorrecto := 'N';
        RAISE_APPLICATION_ERROR(-20028,'FECHA DE ANULACION INVALIDA YA PASO LAS 24:00 HORAS.');
      END;

        --Inserta en la tabla de ahorro x DNI para validar el maximo Ahorro en el dia o Semana
        --dubilluz 28.05.2009
      SELECT cab.fec_ped_vta into v_dFecPedVtaOrigen
      FROM  VTA_PEDIDO_VTA_CAB cab
      WHERE cab.cod_grupo_cia = cCodGrupoCia_in
      AND   cab.cod_local     = cCodLocal_in
      AND   CAB.NUM_PED_VTA   = vNumPedOrigen;
       if trunc(v_dFecPedVtaOrigen) = trunc(sysdate) then
			-- KMONCADA 16.04.2015 SE CONSIDERA SOLO LAS CAMPAÑAS QUE SUMEN EN AHORRO DE FIDELIZADO
            select NVL(SUM(d.AHORRO),0)
            into   vAhorroPedido
            from   vta_pedido_vta_det d,
                   vta_campana_cupon cup
            where  cup.cod_grupo_cia = d.cod_grupo_cia
            and    cup.cod_camp_cupon = d.cod_camp_cupon
            and d.cod_grupo_cia = cCodGrupoCia_in
            and    d.cod_local = cCodLocal_in
            and    d.num_ped_vta = vNumPedOrigen
            and NVL(cup.flg_acumula_ahorro_dni,'S') = 'S';

        if vAhorroPedido > 0 then
             insert into vta_ped_dcto_cli_aux
            (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,VAL_DCTO_VTA,DNI_CLIENTE,FEC_CREA_PED_VTA_CAB)
            select c.cod_grupo_cia,c.cod_local,v_nNumPedNegativo,sum(d.ahorro)*-1,t.dni_cli,v_dFecPedVta
            from   vta_pedido_vta_det d,
                   vta_pedido_vta_cab c,
                   fid_tarjeta_pedido t,
                   vta_campana_cupon cup -- KMONCADA 16.04.2015 SE CONSIDERA SOLO LAS CAMPAÑAS QUE SUMEN EN AHORRO DE FIDELIZADO
            where  c.cod_grupo_cia = cCodGrupoCia_in
            and    c.cod_local = cCodLocal_in
            and    c.num_ped_vta =  vNumPedOrigen
            and    c.cod_grupo_cia = d.cod_grupo_cia
            and    c.cod_local = d.cod_local
            and    c.num_ped_vta = d.num_ped_vta
            and    c.cod_grupo_cia = t.cod_grupo_cia
            and    c.cod_local = t.cod_local
            and    c.num_ped_vta = t.num_pedido
            AND    cup.cod_grupo_cia = d.cod_grupo_cia
            and    cup.cod_camp_cupon = d.cod_camp_cupon
            and NVL(cup.flg_acumula_ahorro_dni,'S') = 'S'
            group by c.cod_grupo_cia,c.cod_local,v_nNumPedNegativo,t.dni_cli,v_dFecPedVta;

            ---- 2012.10.15 CAMBIO PARA GUARDAR EL DSCTO ANULADO
  end if;
  --NO GRABA EL PEDIDO NC POR EL if vAhorroPedido > 0 then, SE RETIRO DEL IF
            insert into fid_tarjeta_pedido
            (
            cod_grupo_cia,
            cod_local,
            num_pedido,
            dni_cli,
            cant_dcto,
            usu_crea_tarjeta_pedido,
            fec_crea_tarjeta_pedido,
            cod_tarjeta
            )
            select
            c.cod_grupo_cia,
            c.cod_local,
            v_nNumPedNegativo,
            t.dni_cli,
            sum(d.ahorro)*-1,
            'SISTEMAS',
            sysdate,
            t.cod_tarjeta
            from   vta_pedido_vta_det d,
                   vta_pedido_vta_cab c,
                   fid_tarjeta_pedido t
            where  c.cod_grupo_cia = cCodGrupoCia_in
            and    c.cod_local = cCodLocal_in
            and    c.num_ped_vta =  vNumPedOrigen
            and    c.cod_grupo_cia = d.cod_grupo_cia
            and    c.cod_local = d.cod_local
            and    c.num_ped_vta = d.num_ped_vta
            and    c.cod_grupo_cia = t.cod_grupo_cia
            and    c.cod_local = t.cod_local
            and    c.num_ped_vta = t.num_pedido
            group by c.cod_grupo_cia,
            c.cod_local,v_nNumPedNegativo,t.dni_cli,t.cod_tarjeta;

        end if;

      /*** FIN VALIDACION DE FECHA DE PEDIDO ELIMINADO***/

      --- hace update de cabecera igual a suma de detalle ---
      --  dubilluz 17.07.2015
          update VTA_PEDIDO_VTA_CAB c
             set (ahorro_camp,
                  ahorro_puntos,
                  AHORRO_PACK,
                  PTOS_AHORRO_PACK,
                  ahorro_total,
                  ptos_ahorro_camp) =
                 (select sum(nvl(d.ahorro_camp, 0)),
                         sum(nvl(d.ahorro_puntos, 0)),
                         sum(nvl(d.ahorro_pack, 0)),
                         sum(nvl(d.ptos_ahorro_pack, 0)),
                         sum(nvl(d.ahorro, 0)),
                         --sum(nvl(d.ahorro_camp, 0)) + sum(nvl(d.ahorro_puntos, 0)),
                         sum(nvl(d.ahorro_camp * 100, 0))
                    from vta_pedido_vta_det d
                   where d.cod_grupo_cia = c.cod_grupo_cia
                     and d.cod_local = c.cod_local
                     and d.num_ped_vta = c.num_ped_vta)
           where c.cod_grupo_cia = cCodGrupoCia_in
             and c.cod_local = cCodLocal_in
             and c.num_ped_vta = v_nNumPedNegativo;
      --  dubilluz 17.07.2015      
      --- --- 
      
      
    ELSE
      RAISE_APPLICATION_ERROR(-20022,'HA OCURRIDO UN ERROR DESCONOCIDO.');
    END IF;
  END;
  /* ********************************************************************************************** */
  FUNCTION CAJ_GET_PEDIDO_NEGATIVO_CAB(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cSecMovCaja_in IN CHAR,vIdUsu_in IN VARCHAR2)
  RETURN CHAR
  IS
    v_nNumPedNegativo VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    v_nNumPedDiario VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO %TYPE;
  BEGIN
    --GENERA PEDIDO NEGATIVO
    v_nNumPedNegativo:= Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPed),10,'0','I');
    v_nNumPedDiario:= Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPedDia),4,'0','I');
    dbms_output.put_line('CAJ_GET_PEDIDO_NEGATIVO_CAB: INSERT VTA_PEDIDO_VTA_CAB:');
    INSERT INTO VTA_PEDIDO_VTA_CAB(COD_GRUPO_CIA,
                                    COD_LOCAL,
                                    NUM_PED_VTA,
                                    COD_CLI_LOCAL,
                                    SEC_MOV_CAJA,
                                    TIP_PED_VTA,
                                    VAL_TIP_CAMBIO_PED_VTA,
                                    NUM_PED_DIARIO,
                                    CANT_ITEMS_PED_VTA,
                                    EST_PED_VTA,
                                    TIP_COMP_PAGO,
                                    NOM_CLI_PED_VTA,
                                    DIR_CLI_PED_VTA,
                                    RUC_CLI_PED_VTA,
                                    USU_CREA_PED_VTA_CAB,
                                    NUM_PED_VTA_ORIGEN,
                                    IND_PED_CONVENIO,
                                    COD_CONVENIO,
                                    IND_FID,
                                    DNI_CLI,
                                    NUM_PEDIDO_DELIVERY,                                    
                                    ind_conv_btl_mf,
                                    COD_CLI_CONV,
                                    ip_cob_ped,
                                    name_pc_cob_ped,
                                    dni_usu_local,
                                    fecha_proceso_nc_rac,
                                    fecha_proceso_anula_rac,
                                    fec_proceso_rac,
                                    NUM_TARJ_PUNTOS,
                                    PT_INICIAL,
                                    PT_ACUMULADO,
                                    PT_REDIMIDO,
                                    PT_TOTAL,
                                    EST_TRX_ORBIS
                                    )
    SELECT COD_GRUPO_CIA,
          COD_LOCAL,
          v_nNumPedNegativo,
          COD_CLI_LOCAL,
          cSecMovCaja_in,
          TIP_PED_VTA,
          VAL_TIP_CAMBIO_PED_VTA,
          v_nNumPedDiario,
          1,
          'C',
          TIP_COMP_PAGO,
          NOM_CLI_PED_VTA,
          DIR_CLI_PED_VTA,
          RUC_CLI_PED_VTA,
          vIdUsu_in,
          cNumPedVta_in,
          IND_PED_CONVENIO,
          COD_CONVENIO,
          IND_FID,
          DNI_CLI,
          NUM_PEDIDO_DELIVERY,
          ind_conv_btl_mf,
          COD_CLI_CONV,
          ip_cob_ped,
          name_pc_cob_ped,
          dni_usu_local,
          fecha_proceso_nc_rac,
          fecha_proceso_anula_rac,
          fec_proceso_rac,
          --KMONCADA 14.09.2015 SE GRABARA EL NRO DE TARJETA EN CASO DE PTOS
          NVL(NUM_TARJ_PUNTOS,''),
          (PT_INICIAL * (-1)) PT_INICIAL,
          (PT_ACUMULADO * (-1)) PT_ACUMULADO,
          (PT_REDIMIDO * (-1)) PT_REDIMIDO,
          (PT_TOTAL * (-1)) PT_TOTAL,
          --KMONCADA 14.09.2015 SE GRABARA EL NRO DE TARJETA EN CASO DE PTOS
          CASE WHEN EST_TRX_ORBIS IN ('E','P') THEN 'P' ELSE NULL END EST_TRX_ORBIS
    FROM VTA_PEDIDO_VTA_CAB
    WHERE COD_GRUPO_CIA  = cCodGrupoCia_in
          AND COD_LOCAL  = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;

    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumPedDia,vIdUsu_in);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumPed,vIdUsu_in);

    RETURN v_nNumPedNegativo;
  END;
  /* ********************************************************************************************** */
  --24/08/2007  DUBILLUZ  MODIFICACION
  PROCEDURE CAJ_ANULAR_COMPROBANTES(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cTipComp_in     IN CHAR,
                                    cNumComp_in     IN CHAR,
                                    cNumPedNeg_in   IN CHAR,
                                    cSecMovCaja_in  IN CHAR,
                                    vIdUsu_in       IN VARCHAR2,
                                    nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                                    cMotivoAnulacion_in IN VARCHAR2)
  AS
    v_cIndDelivAutomatico VTA_PEDIDO_VTA_CAB.IND_DELIV_AUTOMATICO%TYPE;
    v_tip_ped_vta         VTA_PEDIDO_VTA_CAB.Tip_Ped_Vta %TYPE;
    CURSOR curComp IS
    SELECT SEC_COMP_PAGO, IND_AFECTA_KARDEX
    FROM VTA_COMP_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in
          AND IND_COMP_ANUL = 'N';

    v_rCurComp curComp%ROWTYPE;

    v_cSecCompPago VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;

    --Agregado Por FRAMIREZ 12.06.2012
    vEsConvenioBTLMF CHAR(1);

  BEGIN
       --Agregado Por FRAMIREZ 12.06.2012
       SELECT CAB.IND_CONV_BTL_MF
         INTO vEsConvenioBTLMF
         FROM VTA_PEDIDO_VTA_CAB CAB
        WHERE CAB.COD_GRUPO_CIA =  cCodGrupoCia_in  AND
              CAB.COD_LOCAL = cCodLocal_in AND
              CAB.NUM_PED_VTA = cNumPedVta_in;

       SELECT VTA_CAB.IND_DELIV_AUTOMATICO,
              VTA_CAB.TIP_PED_VTA
       INTO    v_cIndDelivAutomatico,
               v_tip_ped_vta     --12.08.2014
       FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
       WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    VTA_CAB.COD_LOCAL = cCodLocal_in
       AND    VTA_CAB.NUM_PED_VTA = cNumPedVta_in;
    --DETERMINA SI SE VA ANULAR UN PEDIDO O UN COMPROBANTE
    IF cTipComp_in = '%' AND cNumComp_in = '%' THEN
      --ANULAR LOS COMPROBANTES DE UN PEDIDO
      FOR v_rCurComp IN curComp
      LOOP

              IF vEsConvenioBTLMF = 'S' THEN
                   IF v_rCurComp.Ind_Afecta_Kardex = 'S' THEN
                      CAJ_ANULAR_COMPROBANTE(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cNumPedNeg_in,v_rCurComp.SEC_COMP_PAGO,cSecMovCaja_in,vIdUsu_in,nIndReclamoNavsat_in,cMotivoAnulacion_in);

                      --INI ASOSA - 14/10/2014 - PANHD
                      CAJ_ANULAR_COMPR_TICO(cCodGrupoCia_in,
                                                            cCodLocal_in,
                                                            cNumPedVta_in,
                                                            cNumPedNeg_in,
                                                            v_rCurComp.SEC_COMP_PAGO,
                                                            cSecMovCaja_in,
                                                            vIdUsu_in,
                                                            nIndReclamoNavsat_in,
                                                            cMotivoAnulacion_in);
                      --FIN ASOSA - 14/10/2014 - PANHD


                      --ANULAR COMPROBANTE
                        UPDATE VTA_COMP_PAGO SET USU_MOD_COMP_PAGO = vIdUsu_in,  FEC_MOD_COMP_PAGO = SYSDATE,
                               IND_COMP_ANUL = 'S',
                               FEC_ANUL_COMP_PAGO = SYSDATE,
                               NUM_PEDIDO_ANUL    = cNumPedNeg_in,
                               SEC_MOV_CAJA_ANUL  = cSecMovCaja_in,
                               IND_RECLAMO_NAVSAT = nIndReclamoNavsat_in,
                               MOTIVO_ANULACION   = cMotivoAnulacion_in
                               --FECHA_ANULACION=SYSDATE --JCORTEZ 06.07.09 Fecha de generacion del archivo de anulacion
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL     = cCodLocal_in
                        AND    NUM_PED_VTA   = cNumPedVta_in;
				   ELSIF v_rCurComp.Ind_Afecta_Kardex is null THEN
						--ERIOS 2.4.6 Validacion de campo nulo
						RAISE_APPLICATION_ERROR(-20008,'El campo IND_AFECTA_KARDEX es nulo.');
                   END IF;
              ELSE
        CAJ_ANULAR_COMPROBANTE(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cNumPedNeg_in,v_rCurComp.SEC_COMP_PAGO,cSecMovCaja_in,vIdUsu_in,nIndReclamoNavsat_in,cMotivoAnulacion_in);

         --INI ASOSA - 17/10/2014 - PANHD
                      CAJ_ANULAR_COMPR_TICO(cCodGrupoCia_in,
                                                            cCodLocal_in,
                                                            cNumPedVta_in,
                                                            cNumPedNeg_in,
                                                            v_rCurComp.SEC_COMP_PAGO,
                                                            cSecMovCaja_in,
                                                            vIdUsu_in,
                                                            nIndReclamoNavsat_in,
                                                            cMotivoAnulacion_in);
                      --FIN ASOSA - 17/10/2014 - PANHD

              END IF;

      END LOOP;
    ELSE
      --ANULAR EL COMPROBANTE ESPECIFICADO
      SELECT SEC_COMP_PAGO INTO v_cSecCompPago
      FROM VTA_COMP_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_VTA = cNumPedVta_in
            AND TIP_COMP_PAGO = cTipComp_in
            AND NUM_COMP_PAGO = cNumComp_in;
      CAJ_ANULAR_COMPROBANTE(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cNumPedNeg_in,v_cSecCompPago,cSecMovCaja_in,vIdUsu_in,nIndReclamoNavsat_in,cMotivoAnulacion_in);
    END IF;
	-- KMONCADA 10.02.2016 TAMBIEN REALIZARA ANULACION DE LA PROFORMA EN LOCAL M
    IF(v_cIndDelivAutomatico = INDICADOR_SI OR FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in) = 'S') THEN
       UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB SET TMP_CAB.USU_MOD_PED_VTA_CAB = vIdUsu_in, TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
              --MODIFICADO DUBILLUZ  12.08.2014
              TMP_CAB.EST_PED_VTA = -- RHERRERA
              (
              CASE
                WHEN v_tip_ped_vta = '03' THEN
                EST_PED_PENDIENTE ---PENDIENTE
              ELSE
                -- dubilluz 31.07.2014
                EST_PED_ANULADO --ANULADO
              END),
              -- dubilluz 31.07.2014
              --TMP_CAB.EST_PED_VTA = EST_PED_ANULADO,
              TMP_CAB.NUM_PED_VTA_ORIGEN = 
              CASE
                WHEN FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in) = 'S' THEN
                  TMP_CAB.NUM_PED_VTA_ORIGEN
                ELSE
                  ''
              END,
              TMP_CAB.Ind_Pedido_Anul = INDICADOR_SI --dubilluz 24/08/2007
       WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
       AND    TMP_CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
    END IF;

  END;
  /* ********************************************************************************************** */

  PROCEDURE CAJ_ANULAR_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR,
                                   cNumPedNeg_in   IN CHAR,
                                   cSecCompPago_in IN CHAR,
                                   cSecMovCaja_in  IN CHAR,
                                   vIdUsu_in       IN CHAR,
                                   nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                                   cMotivoAnulacion_in IN VARCHAR2)
  AS
    v_cIndProdVirtual CHAR(1);
    CURSOR curProd IS
           SELECT DET.SEC_PED_VTA_DET,
                  DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.VAL_PREC_VTA,
                  DET.VAL_PREC_TOTAL,
                  DET.PORC_DCTO_1,
                  DET.PORC_DCTO_2,
                  DET.PORC_DCTO_3,
                  DET.PORC_DCTO_TOTAL,
                  DET.EST_PED_VTA_DET,
                  DET.VAL_TOTAL_BONO,
                  DET.VAL_FRAC,
                  DET.SEC_USU_LOCAL,
                  DET.VAL_PREC_LISTA,
                  DET.VAL_IGV,
                  DET.UNID_VTA,
                  DET.IND_EXONERADO_IGV,
                  DET.SEC_GRUPO_IMPR,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                  IND_ORIGEN_PROD,
                  DET.PORC_ZAN,    -- 2009-11-09 JOLIVA
                  --JCORTEZ 11.11.09
                   NVL(DET.COD_GRUPO_REP,' ') COD_GRUPO_REP,
                   NVL(DET.COD_GRUPO_REP_EDMUNDO,' ') COD_GRUPO_REP_EDMUNDO,
                   DET.COD_PROM,         --ASOSA, 21.05.2010
                  DET.COD_CAMP_CUPON,    --ASOSA, 21.05.2010
                  --Agregado FRAMIREZ  11.06.2012  ConvBTLMF
                  DET.ahorro_conv,
                  DET.num_comp_pago,
                  DET.sec_comp_pago_benef,
                  DET.sec_comp_pago_empre,
                  --KMONCADA 10.09.2015 REGISTRO DE ANULACION EN CASO DE PTOS
                  (DET.AHORRO * (-1)) AHORRO,
                  DET.IND_PROD_MAS_1,
                  DET.COD_PROD_PUNTOS,
                  DET.IND_BONIFICADO,
                  DET.FACTOR_PUNTOS,
                  (DET.CTD_PUNTOS_ACUM * (-1)) CTD_PUNTOS_ACUM,
                  (DET.AHORRO_PUNTOS * (-1)) AHORRO_PUNTOS,
                  (DET.AHORRO_CAMP * (-1)) AHORRO_CAMP,
                  (DET.PTOS_AHORRO * (-1)) PTOS_AHORRO,
                  (DET.AHORRO_PACK * (-1)) AHORRO_PACK,
                  (DET.PTOS_AHORRO_PACK * (-1)) PTOS_AHORRO_PACK,
                  -- KMONCADA 10.02.2016 SE CARGARAN LOS DATOS DE CONTROL DE LOTE
                  DET.LOTE,
                  DET.FECHA_VENCIMIENTO_LOTE

           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in--FALTA FILTAR POR DOCUMENTO
           AND    DET.SEC_COMP_PAGO = cSecCompPago_in
           --INI ASOSA - 07/10/2014 - PANHD
             AND  NOT EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND DET.COD_PROD = PP.COD_PROD
                                                                    AND DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
             --FIN ASOSA - 07/10/2014 - PANHD
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+);
    v_rCurProd curProd%ROWTYPE;

    i INTEGER:=0;

    v_nFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nComprometido LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFrac LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_vDescUnidVta LGT_PROD_LOCAL.UNID_VTA%TYPE;
    v_nCant LGT_NOTA_ES_DET.CANT_MOV%TYPE;

    --JCORTEZ 19.10.09
    CCOD_GRUPO_REP CHAR(3);
    CCOD_GRUPO_REP_EDMUNDO CHAR(3);
  BEGIN

    DBMS_OUTPUT.PUT_LINE('There are 0 ' || cNumPedNeg_in);
    --ANULAR COMPROBANTE
    UPDATE VTA_COMP_PAGO SET USU_MOD_COMP_PAGO = vIdUsu_in,  FEC_MOD_COMP_PAGO = SYSDATE,
           IND_COMP_ANUL = 'S',
           FEC_ANUL_COMP_PAGO = SYSDATE,
           NUM_PEDIDO_ANUL   = cNumPedNeg_in,
           SEC_MOV_CAJA_ANUL = cSecMovCaja_in,
           IND_RECLAMO_NAVSAT = nIndReclamoNavsat_in,
           MOTIVO_ANULACION= cMotivoAnulacion_in
           --FECHA_ANULACION=SYSDATE --JCORTEZ 06.07.09 Fecha de generacion del archivo de anulacion
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    NUM_PED_VTA = cNumPedVta_in
    AND    SEC_COMP_PAGO = cSecCompPago_in;

    SELECT COUNT(*)
    INTO   i
    FROM   VTA_PEDIDO_VTA_DET
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    NUM_PEd_VTA = cNumPedNeg_in;

    FOR v_rCurProd IN curProd
    LOOP
        i:=i+1;
        --VERIFICA SI LA FRACCION PERMITE LA ANULACION
        SELECT STK_FISICO,STK_FISICO,VAL_FRAC_LOCAL,UNID_VTA
        INTO   v_nFisico,v_nComprometido,v_nValFrac, v_vDescUnidVta
        FROM   LGT_PROD_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in
        AND    COD_PROD = v_rCurProd.COD_PROD FOR UPDATE;

        IF MOD(v_rCurProd.CANT_ATENDIDA*v_nValFrac,v_rCurProd.VAL_FRAC) = 0 THEN
          v_nCant := ((v_rCurProd.CANT_ATENDIDA*v_nValFrac)/v_rCurProd.VAL_FRAC);
        ELSE
          RAISE_APPLICATION_ERROR(-20002,'Error al anular. Prod:'||v_rCurProd.COD_PROD||',Cant:'||v_rCurProd.CANT_ATENDIDA||' ,Frac:'||v_rCurProd.VAL_FRAC||' ,Frac. Act:'||v_nValFrac);
        END IF;

        --ANULAR EL DET DE PEDIDO, SEGUN EL COMPROBANTE
        UPDATE VTA_PEDIDO_VTA_DET SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
               EST_PED_VTA_DET = 'N'
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in
        AND    NUM_PED_VTA = cNumPedVta_in
        AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET
        AND    SEC_COMP_PAGO = cSecCompPago_in;

/*
        --JCORTEZ 19.10.09
        SELECT NVL(X.COD_GRUPO_REP,' '),NVL(X.COD_GRUPO_REP_EDMUNDO,' ')
        INTO CCOD_GRUPO_REP,CCOD_GRUPO_REP
        FROM LGT_PROD X
        WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
        AND X.COD_PROD=v_rCurProd.COD_PROD;*/

        --INSERT DETALLE NEGATIVO
        dbms_output.put_line('CAJ_ANULAR_COMPROBANTE: INSERT VTA_PEDIDO_VTA_DET:');
        INSERT INTO VTA_PEDIDO_VTA_DET(COD_GRUPO_CIA,
                                       COD_LOCAL,
                                       NUM_PED_VTA,
                                       SEC_PED_VTA_DET,
                                       COD_PROD,
                                       CANT_ATENDIDA,
                                       VAL_PREC_VTA,
                                       VAL_PREC_TOTAL,
                                       PORC_DCTO_1,
                                       PORC_DCTO_2,
                                       PORC_DCTO_3,
                                       PORC_DCTO_TOTAL,
                                       EST_PED_VTA_DET, --
                                       VAL_TOTAL_BONO,
                                       VAL_FRAC,
                                       SEC_USU_LOCAL,
                                       USU_CREA_PED_VTA_DET,
                                       VAL_PREC_LISTA,
                                       VAL_IGV,
                                       UNID_VTA,
                                       IND_EXONERADO_IGV,
                                       IND_ORIGEN_PROD,
                                       VAL_FRAC_LOCAL,
                                       CANT_FRAC_LOCAL,
                                       COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09
                                       PORC_ZAN,       -- 2009-11-09 JOLIVA
                                       COD_PROM,       -- ASOSA, 21.05.2010
                                       COD_CAMP_CUPON,  -- ASOSA, 21.05.2010
                                       --Agregado FRAMIREZ  11.06.2012  ConvBTLMF
                                       ahorro_conv,
                                       num_comp_pago,
                                       sec_comp_pago_benef,
                                       sec_comp_pago_empre,
                                       --KMONCADA 10.09.2015 REGISTRO DE ANULACION EN CASO DE PTOS
                                       AHORRO,
                                       IND_PROD_MAS_1,
                                       COD_PROD_PUNTOS,
                                       IND_BONIFICADO,
                                       FACTOR_PUNTOS,
                                       CTD_PUNTOS_ACUM,
                                       AHORRO_PUNTOS,
                                       AHORRO_CAMP,
                                       PTOS_AHORRO,
                                       AHORRO_PACK,
                                       PTOS_AHORRO_PACK,
                                       LOTE,
                                       FECHA_VENCIMIENTO_LOTE
                                       )
                                VALUES(cCodGrupoCia_in,
                                       cCodLocal_in,
                                       cNumPedNeg_in,
                                       i,
                                       v_rCurProd.COD_PROD,
                                       v_rCurProd.CANT_ATENDIDA*-1,
                                       v_rCurProd.VAL_PREC_VTA,
                                       v_rCurProd.VAL_PREC_TOTAL*-1,
                                       v_rCurProd.PORC_DCTO_1,
                                       v_rCurProd.PORC_DCTO_2,
                                       v_rCurProd.PORC_DCTO_3,
                                       v_rCurProd.PORC_DCTO_TOTAL,
                                       'N',
                                       v_rCurProd.VAL_TOTAL_BONO*-1,
                                       v_rCurProd.VAL_FRAC,
                                       v_rCurProd.SEC_USU_LOCAL,
                                       vIdUsu_in,
                                       v_rCurProd.VAL_PREC_LISTA,
                                       v_rCurProd.VAL_IGV,
                                       v_rCurProd.UNID_VTA,
                                       v_rCurProd.IND_EXONERADO_IGV,
                                       v_rCurProd.IND_ORIGEN_PROD,
                                       v_nValFrac,
                                       v_nCant*-1,
                                       v_rCurProd.COD_GRUPO_REP,v_rCurProd.COD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09
                                       v_rCurProd.PORC_ZAN,        -- 2009-11-09 JOLIVA
                                       v_rCurProd.Cod_Prom,        --ASOSA, 21.05.2010
                                       v_rCurProd.Cod_Camp_Cupon,   --ASOSA, 21.05.2010
                                       --Agregado FRAMIREZ  11.06.2012  ConvBTLMF
                                       v_rCurProd.ahorro_conv,
                                       v_rCurProd.num_comp_pago,
                                       v_rCurProd.sec_comp_pago_benef,
                                       v_rCurProd.sec_comp_pago_empre,
                                       --KMONCADA 10.09.2015 REGISTRO DE ANULACION EN CASO DE PTOS
                                       v_rCurProd.AHORRO,
                                       v_rCurProd.IND_PROD_MAS_1,
                                       v_rCurProd.COD_PROD_PUNTOS,
                                       v_rCurProd.IND_BONIFICADO,
                                       v_rCurProd.FACTOR_PUNTOS,
                                       v_rCurProd.CTD_PUNTOS_ACUM,
                                       v_rCurProd.AHORRO_PUNTOS,
                                       v_rCurProd.AHORRO_CAMP,
                                       v_rCurProd.PTOS_AHORRO,
                                       v_rCurProd.AHORRO_PACK,
                                       v_rCurProd.PTOS_AHORRO_PACK,
                                       v_rCurProd.LOTE,
                                       v_rCurProd.FECHA_VENCIMIENTO_LOTE
                                       );

        v_cIndProdVirtual := v_rCurProd.IND_PROD_VIR;
        IF v_cIndProdVirtual = INDICADOR_SI THEN
           --INSERTAR KARDEX VIRTUAL
           Ptoventa_Inv.INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in,
                                                  cCodLocal_in,
                                                  v_rCurProd.COD_PROD,
                                                  g_cCodMotKardex,
                                                  '01',
                                                  cNumPedNeg_in,
                                                  v_nCant,
                                                  v_nValFrac,
                                                  v_vDescUnidVta,
                                                  vIdUsu_in,
                                                  '016');
        ELSE
           --ACTUALIZAR STOCK
		   -- KMONCADA 10.02.2016 REALIZARA LA ACTUALIZACION DEL STOCK LUEGO DE INSERTAR KARDEX
           /*UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vIdUsu_in, FEC_MOD_PROD_LOCAL = SYSDATE,
                  STK_FISICO = v_nFisico + v_nCant
           WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
           AND    COD_LOCAL = cCodLocal_in
           AND    COD_PROD = v_rCurProd.COD_PROD;*/
           --INSERTAR KARDEX
           Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          v_rCurProd.COD_PROD,
                                          g_cCodMotKardex,
                                          '01',
                                          cNumPedNeg_in,
                                          v_nFisico,
                                          v_nCant,
                                          v_nValFrac,
                                          v_vDescUnidVta,
                                          vIdUsu_in,
                                          '016',
                                          NULL, NULL, NULL,
										  -- KMONCADA 10.02.2016 [LOCAL M]
                                          to_char(v_rCurProd.FECHA_VENCIMIENTO_LOTE,'dd/mm/yyyy'),
                                          v_rCurProd.LOTE
                                          );
          --ACTUALIZAR STOCK
          PTOVTA_RESPALDO_STK.P_ACT_STOCK_PRODUCTO(cCodGrupoCia_in,
								                                   cCodLocal_in,
								                                   v_rCurProd.COD_PROD,
								                                   v_nCant,
								                                   v_nValFrac,
								                                   v_nValFrac,
								                                   vIdUsu_in,
								                                   v_rCurProd.LOTE);
        END IF;
    END LOOP;

    --ACTUALIZAR MONTOS EN LA CABECERA
    CAJ_ACTUALIZAR_MONTOS_CABECERA(cCodGrupoCia_in,cCodLocal_in,cNumPedNeg_in,cNumPedVta_in,cSecCompPago_in,vIdUsu_in);

  END;
  /* ********************************************************************************************** */
  PROCEDURE CAJ_ANULAR_CABECERA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,vIdUsu_in IN VARCHAR2,cMotivoAnulacion_in IN VARCHAR2)
  AS
    v_nIndCompAnul INTEGER;

    CURSOR curCupones IS
    SELECT C.COD_GRUPO_CIA,TRIM(C.COD_CUPON) AS COD_CUPON
      FROM VTA_CAMP_PEDIDO_CUPON C,
           VTA_CAMPANA_CUPON CAMP,
           VTA_CUPON O
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_PED_VTA = cNumPedVta_in
            AND C.ESTADO = 'E'
            AND C.IND_IMPR = 'S'
            AND C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
            AND C.COD_CUPON = O.COD_CUPON
            AND O.ESTADO = 'A'
            AND CAMP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
            AND CAMP.COD_CAMP_CUPON = C.COD_CAMP_CUPON
            AND CAMP.IND_MULTIUSO = 'N';--NO SE ANULAN LOS CUPONES MULTIUSO

    v_cIndLinea CHAR(1);
    v_cEstado2 VTA_CUPON.ESTADO%TYPE := 'A';
  BEGIN
     SELECT COUNT(*) INTO v_nIndCompAnul
    FROM VTA_COMP_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in
          AND IND_COMP_ANUL = 'N';
    --ANULAR LA CABECERA
    IF v_nIndCompAnul=0 THEN
      UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in,FEC_MOD_PED_VTA_CAB = SYSDATE,
         IND_PEDIDO_ANUL = 'S',
        MOTIVO_ANULACION= cMotivoAnulacion_in
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_VTA = cNumPedVta_in;

      --30/07/2008 ERIOS Se asume que se anulo el pedido completo
     -- v_cIndLinea := PTOVENTA_CAJ.VERIFICA_CONN_MATRIZ;
      FOR cupon IN curCupones
      LOOP
        --Verifica conn matriz
/*        IF v_cIndLinea = 'S' THEN
          EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON@XE_000(:1,:2,:3); END;'
            USING cCodGrupoCia_in,TRIM(cupon.COD_CUPON), IN OUT v_cEstado2;
          \*IF v_cEstado2 <> 'A' THEN
            RAISE_APPLICATION_ERROR(-20016,'Cupon usado: '||TRIM(cupon.COD_CUPON));
          END IF;  *\
        END IF;*/

        UPDATE VTA_CUPON
        SET ESTADO = 'N',
            FEC_PROCESA_MATRIZ = NULL,
            USU_PROCESA_MATRIZ = NULL,
          FEC_MOD_CUP_CAB = SYSDATE,
          USU_MOD_CUP_CAB = vIdUsu_in
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CUPON = TRIM(cupon.COD_CUPON);
      END LOOP;

      --Procesa cupones
      --ACTUALIZA_CUPONES_MATRIZ
    END IF;
  END;
  /* ********************************************************************************************** */
  PROCEDURE CAJ_ACTUALIZAR_MONTOS_CABECERA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedNeg_in IN CHAR,cNumPed_in IN CHAR,cSecCompPago_in IN CHAR,vIdUsu_in IN VARCHAR2)
  AS
    v_nValBruto VTA_PEDIDO_VTA_CAB.VAL_BRUTO_PED_VTA%TYPE;
    v_nValNeto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nValRedondeo VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_nValIgv VTA_PEDIDO_VTA_CAB.VAL_IGV_PED_VTA%TYPE;
    v_nValDscto VTA_PEDIDO_VTA_CAB.VAL_DCTO_PED_VTA%TYPE;
    v_nCantItems VTA_PEDIDO_VTA_CAB.CANT_ITEMS_PED_VTA %TYPE;

  BEGIN
	--ERIOS 30.10.2014 La actualizacion es por pedido, ya no por comprobantes.
	goto fin_transaccion;
	
    SELECT SUM(CANT_ATENDIDA*VAL_PREC_LISTA),SUM(VAL_PREC_TOTAL),SUM(VAL_PREC_TOTAL-(VAL_PREC_TOTAL/((100+VAL_IGV)/100))),COUNT(*)
          INTO v_nValBruto, v_nValNeto, v_nValIgv,v_nCantItems
    FROM VTA_PEDIDO_VTA_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedNeg_in;

    --DATOS DEL COMPROBANTE
    SELECT VAL_BRUTO_COMP_PAGO*-1,
            (VAL_NETO_COMP_PAGO+VAL_REDONDEO_COMP_PAGO)*-1,
            VAL_REDONDEO_COMP_PAGO*-1,
            VAL_IGV_COMP_PAGO*-1,
            VAL_DCTO_COMP_PAGO*-1
      INTO v_nValBruto,
            v_nValNeto,
            v_nValRedondeo,
            v_nValIgv,
            v_nValDscto
    FROM VTA_COMP_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPed_in
          AND SEC_COMP_PAGO = cSecCompPago_in;

    --ACTUALIZAR MONTOS EN CABECERA
    IF v_nCantItems > 0 THEN       --ASOSA - 14/10/2014 - PANHD
          UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in, FEC_MOD_PED_VTA_CAB = SYSDATE,
              VAL_BRUTO_PED_VTA = VAL_BRUTO_PED_VTA + v_nValBruto,
              VAL_NETO_PED_VTA = VAL_NETO_PED_VTA + v_nValNeto,
              VAL_REDONDEO_PED_VTA = VAL_REDONDEO_PED_VTA + v_nValRedondeo,
              VAL_IGV_PED_VTA  = VAL_IGV_PED_VTA + v_nValIgv,
              VAL_DCTO_PED_VTA = VAL_DCTO_PED_VTA + v_nValDscto,
              CANT_ITEMS_PED_VTA = v_nCantItems
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_PED_VTA = cNumPedNeg_in;
   END IF;
   
   <<fin_transaccion>>
   NULL;
  END;
  /* ********************************************************************************************** */
  PROCEDURE CAJ_ANULAR_FORMA_PAGO(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cNumPedVta_in IN CHAR,
                                  cTipComp_in IN CHAR,
                                  cNumComp_in IN CHAR,
                                  cNumPedNeg_in IN CHAR,
                                  vIdUsu_in IN VARCHAR2)
  AS
    v_nMonto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_dFechaPedido VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
    v_cIndPedConvenio VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE;
    v_nValNeto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_cCodConvenio CON_PED_VTA_CLI.COD_CONVENIO%TYPE := 'X';
    v_cCodCli CON_PED_VTA_CLI.COD_CLI%TYPE;

    v_dSysdate DATE;
    v_dMes DATE;
    v_nMonCred VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
    v_nMonAcum VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE := 0;

    --ERIOS 27.01.2014 Se agrega el lote para las pagos con tarjeta
    CURSOR curFPago IS
    SELECT  P.COD_FORMA_PAGO,
            P.IM_PAGO,
            P.TIP_MONEDA,
            P.VAL_TIP_CAMBIO,
            P.VAL_VUELTO,
            P.IM_TOTAL_PAGO,
            P.NUM_TARJ,
            P.FEC_VENC_TARJ,
            P.NOM_TARJ,
            F.COD_CONVENIO,
            F.Ind_Tarj,
            P.COD_LOTE
    FROM VTA_FORMA_PAGO_PEDIDO P,
         VTA_FORMA_PAGO F
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.COD_LOCAL = cCodLocal_in
          AND P.NUM_PED_VTA = cNumPedVta_in
          AND P.COD_GRUPO_CIA = F.COD_GRUPO_CIA
          AND P.COD_FORMA_PAGO = F.COD_FORMA_PAGO;

    CURSOR curFPagoNT IS
    SELECT  P.COD_FORMA_PAGO,
            P.IM_PAGO,
            P.TIP_MONEDA,
            P.VAL_TIP_CAMBIO,
            P.VAL_VUELTO,
            P.IM_TOTAL_PAGO,
            P.NUM_TARJ,
            P.FEC_VENC_TARJ,
            P.NOM_TARJ,
            F.COD_CONVENIO,
            F.Ind_Tarj
    FROM VTA_FORMA_PAGO_PEDIDO P,
         VTA_FORMA_PAGO F
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.COD_LOCAL = cCodLocal_in
          AND P.NUM_PED_VTA = cNumPedVta_in
          AND F.IND_TARJ = 'N'
          AND F.COD_FORMA_PAGO NOT IN (g_cFormPagoEfectivo)
          AND P.COD_GRUPO_CIA = F.COD_GRUPO_CIA
          AND P.COD_FORMA_PAGO = F.COD_FORMA_PAGO;


    --JCORTEZ 21.07.09 pedidos con tarjeta
    CURSOR curFPagoST IS
    SELECT  P.COD_FORMA_PAGO,
            P.IM_PAGO,
            P.TIP_MONEDA,
            P.VAL_TIP_CAMBIO,
            P.VAL_VUELTO,
            P.IM_TOTAL_PAGO,
            P.NUM_TARJ,
            P.FEC_VENC_TARJ,
            P.NOM_TARJ,
            F.COD_CONVENIO,
            F.Ind_Tarj
    FROM VTA_FORMA_PAGO_PEDIDO P,
         VTA_FORMA_PAGO F
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.COD_LOCAL = cCodLocal_in
          AND P.NUM_PED_VTA = cNumPedVta_in
          AND F.IND_TARJ = 'S'
          AND F.COD_FORMA_PAGO NOT IN (g_cFormPagoEfectivo)
          AND P.COD_GRUPO_CIA = F.COD_GRUPO_CIA
          AND P.COD_FORMA_PAGO = F.COD_FORMA_PAGO;

    v_nCantComp INTEGER;
    v_nComps INTEGER;

    v_nCantFP INTEGER;
    v_nValComp VTA_COMP_PAGO.VAL_NETO_COMP_PAGO%TYPE;


    vIndTarje CHAR(1);
    vIndConvBTLMF CHAR(1);
    vIndAnulTransCerr CHAR(1);
    vTempCodFormaPago VARCHAR2(6);
    --KMONCADA 25.04.2016 [PERCEPCION]
    vMontoPercepcion VTA_PEDIDO_VTA_CAB.VAL_PERCEPCION_PED_VTA%TYPE;
    vRedondeoPercepcion VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PERCEPCION%TYPE;
    vCodFPDsctPercepcion VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE;
  BEGIN
    -- INDICADOR CONVENIO BTLMF
   SELECT CAB.IND_CONV_BTL_MF
     INTO vIndConvBTLMF
     FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA = cNumPedVta_in;

    --DETERMINA SI SE VA ANULAR UN PEDIDO O UN COMPROBANTE
    SELECT COUNT(*) INTO v_nCantComp
    FROM VTA_COMP_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;

    SELECT COUNT(*) INTO v_nComps
    FROM VTA_COMP_PAGO CP
    WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
          AND CP.COD_LOCAL = cCodLocal_in
          AND CP.NUM_PED_VTA = cNumPedVta_in
          AND CP.TIP_COMP_PAGO LIKE cTipComp_in
          AND CP.NUM_COMP_PAGO LIKE cNumComp_in;
    
    --IF cTipComp_in = '%' AND cNumComp_in = '%' THEN
    IF v_nCantComp = v_nComps THEN --SE ANULA EL PEDIDO

      --31/05/2007 ERIOS Obtiene la fecha,indicador convenio del pedido.
      SELECT TRUNC(C.FEC_PED_VTA),C.IND_PED_CONVENIO,C.VAL_NETO_PED_VTA
      INTO v_dFechaPedido,v_cIndPedConvenio,v_nValNeto
      FROM VTA_PEDIDO_VTA_CAB C
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_PED_VTA = cNumPedVta_in;

      v_dSysdate := TRUNC(SYSDATE);
      v_dMes := TRUNC(SYSDATE,'MM');
      v_nMonCred := 0;

      --Verifica Pedido Convenio

      IF vIndConvBTLMF = 'S' THEN
         vIndConvBTLMF := 'S';
      ELSE
      IF v_cIndPedConvenio = 'S' THEN

        SELECT P.COD_CONVENIO,P.COD_CLI
        INTO v_cCodConvenio,v_cCodCli
        FROM CON_PED_VTA_CLI P
        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
              AND P.COD_LOCAL = cCodLocal_in
              AND P.NUM_PED_VTA = cNumPedVta_in;
      END IF;
      END IF;
      --Verifica si es pedido con Tarjeta   JCORTEZ
        /*SELECT F.Ind_Tarj INTO vIndTarje
    FROM VTA_FORMA_PAGO_PEDIDO P,
         VTA_FORMA_PAGO F
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.COD_LOCAL = cCodLocal_in
          AND P.NUM_PED_VTA = cNumPedVta_in
          AND F.COD_FORMA_PAGO NOT IN (g_cFormPagoEfectivo)
          AND P.COD_GRUPO_CIA = F.COD_GRUPO_CIA
          AND P.COD_FORMA_PAGO = F.COD_FORMA_PAGO;
          */

      IF v_dSysdate = v_dFechaPedido THEN
        --Si es el mismo dia, se anula las mismas formas de pago del pedido.
        FOR rowFPago IN curFPago
        LOOP
          /*  JMIRANDA 08/09/2009 se comento para actualizar con remoto y no DBLINK
          IF v_cIndPedConvenio = 'S' AND v_cCodConvenio = rowFPago.Cod_Convenio THEN
            --Actualiza Monto credito.
            v_nMonCred := rowFPago.IM_TOTAL_PAGO*-1;
            PTOVENTA_CONV .CON_ACTUALIZA_CONSUMO_CLI(v_cCodConvenio,v_cCodCli,v_nMonCred);
          END IF;
          */
          --LLEIVA 11-Dic-2013 Se consultan las formas de pago para saber si fueron pagadas con tarjeta
          --LLEIVA 11-Dic-2013 Si fueron pagadas con tarjeta, se verifica si la anulación se pudo realizar
          vIndAnulTransCerr := 'N';
          BEGIN
               select IND_ANUL_TRANS_CERR
               into vIndAnulTransCerr
               from VTA_PROCESO_PINPAD_MC
               where NUM_PED_VTA = cNumPedVta_in
                     and COD_FORMA_PAGO = rowFPago.COD_FORMA_PAGO
                     and TIP_REGISTRO = 'COM';
          EXCEPTION
          WHEN OTHERS THEN
               --aqui debe hacerse la validacion de visa, si aplicaria el caso, por ahora se indica 'N'
               BEGIN
                    select IND_ANUL_TRANS_CERR
                    into vIndAnulTransCerr
                    from VTA_PROCESO_PINPAD_VISA
                    where NUM_PED_VTA = cNumPedVta_in
                          and COD_FORMA_PAGO = rowFPago.COD_FORMA_PAGO
                          and TIP_REGISTRO = 'COM';
               EXCEPTION
               WHEN OTHERS THEN
                    vIndAnulTransCerr := 'N';
               END;
          END;

          IF (vIndAnulTransCerr = 'S') THEN
               --LLEIVA 11-Dic-2013 Si no se realizo la anulacion, se anula la forma de pago en soles
               vTempCodFormaPago := g_cFormPagoEfectivo;
          ELSE
               --LLEIVA 11-Dic-2013 Si se realizo la anulacion, se anula la forma de pago de la tarj correspondiente
               vTempCodFormaPago := rowFPago.COD_FORMA_PAGO;
          END IF;


          DBMS_OUTPUT.put_line('CAJ_ANULAR_FORMA_PAGO : VTA_FORMA_PAGO_PEDIDO');
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
                                                IND_ANUL_LOTE_CERRADO,
                                                COD_LOTE)
              VALUES(cCodGrupoCia_in,
                     cCodLocal_in,
                     vTempCodFormaPago,
                     cNumPedNeg_in,
                     rowFPago.IM_PAGO*-1,
                     rowFPago.TIP_MONEDA,
                     rowFPago.VAL_TIP_CAMBIO,
                     rowFPago.VAL_VUELTO*-1,
                     rowFPago.IM_TOTAL_PAGO*-1,
                     rowFPago.NUM_TARJ,
                     rowFPago.FEC_VENC_TARJ,
                     rowFPago.NOM_TARJ,
                     vIdUsu_in,
                     vIndAnulTransCerr,
                     rowFPago.COD_LOTE);
          EXCEPTION
          WHEN DUP_VAL_ON_INDEX THEN
              UPDATE VTA_FORMA_PAGO_PEDIDO
              SET IM_PAGO = (IM_PAGO + rowFPago.IM_PAGO*-1),
                  IM_TOTAL_PAGO = (IM_TOTAL_PAGO + rowFPago.IM_TOTAL_PAGO*-1),
                  VAL_VUELTO = (VAL_VUELTO + rowFPago.VAL_VUELTO*-1),
                  USU_MOD_FORMA_PAGO_PED = vIdUsu_in,
                  FEC_MOD_FORMA_PAGO_PED = SYSDATE,
                  IND_ANUL_LOTE_CERRADO = DECODE(IND_ANUL_LOTE_CERRADO,'S','S',vIndAnulTransCerr)
              where COD_GRUPO_CIA = cCodGrupoCia_in AND
                    COD_LOCAL = cCodLocal_in AND
                    COD_FORMA_PAGO = vTempCodFormaPago AND
                    NUM_PED_VTA = cNumPedNeg_in AND
                    TIP_MONEDA = rowFPago.TIP_MONEDA;
          END;
        END LOOP;

      --ELSIF v_dFechaPedido BETWEEN v_dMes AND v_dSysdate THEN
      ELSIF v_dFechaPedido <> v_dSysdate THEN
        --18/07/2007  ERIOS  Si esta dentro del mes, se anulan las formas de pago que no son tarjetas.
        FOR rowFPagoNT IN curFPagoNT
        LOOP
          v_nMonAcum := v_nMonAcum + rowFPagoNT.IM_TOTAL_PAGO*-1;
          /*  JMIRANDA 08/09/2009 se comento para actualizar con remoto y no DBLINK
          IF v_cIndPedConvenio = 'S' AND v_cCodConvenio = rowFPagoNT.Cod_Convenio THEN
            --Actualiza Monto credito.
            v_nMonCred := rowFPagoNT.IM_TOTAL_PAGO*-1;
            PTOVENTA_CONV.CON_ACTUALIZA_CONSUMO_CLI(v_cCodConvenio,v_cCodCli,v_nMonCred);
          END IF;
          */
          DBMS_OUTPUT.put_line('CAJ_ANULAR_FORMA_PAGO : VTA_FORMA_PAGO_PEDIDO');
          INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,COD_LOCAL,
          COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,
          VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,
          NUM_TARJ,FEC_VENC_TARJ,NOM_TARJ,USU_CREA_FORMA_PAGO_PED)
          VALUES(cCodGrupoCia_in,cCodLocal_in,
          rowFPagoNT.COD_FORMA_PAGO,cNumPedNeg_in,rowFPagoNT.IM_PAGO*-1,rowFPagoNT.TIP_MONEDA,
          rowFPagoNT.VAL_TIP_CAMBIO,rowFPagoNT.VAL_VUELTO*-1,rowFPagoNT.IM_TOTAL_PAGO*-1,
          rowFPagoNT.NUM_TARJ,rowFPagoNT.FEC_VENC_TARJ,rowFPagoNT.NOM_TARJ,vIdUsu_in);

        END LOOP;


        IF v_nValNeto > ABS(v_nMonAcum) THEN

        --El resto en soles.
          DBMS_OUTPUT.put_line('CAJ_ANULAR_FORMA_PAGO : VTA_FORMA_PAGO_PEDIDO');
          INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,COD_LOCAL,
          COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,
          VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,
          USU_CREA_FORMA_PAGO_PED  )
          VALUES(cCodGrupoCia_in,cCodLocal_in,
          g_cFormPagoEfectivo,cNumPedNeg_in,(v_nValNeto+v_nMonAcum)*-1,'01',
          0,0,(v_nValNeto+v_nMonAcum)*-1,
          vIdUsu_in);

        /*   --JCORTEZ 21.07.09 Cada pedido se anulara con su forma de pago, ya no solo con g_cFormPagoEfectivo (SOLES)
          FOR rowFPagST IN curFPagoST
          LOOP

            DBMS_OUTPUT.put_line('CAJ_ANULAR_FORMA_PAGO : VTA_FORMA_PAGO_PEDIDO CON TARJETA');
            INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,COD_LOCAL,
            COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,
            VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,
            NUM_TARJ,FEC_VENC_TARJ,NOM_TARJ,USU_CREA_FORMA_PAGO_PED)
            VALUES(cCodGrupoCia_in,cCodLocal_in,
            rowFPagST.COD_FORMA_PAGO,cNumPedNeg_in,rowFPagST.IM_PAGO*-1,rowFPagST.TIP_MONEDA,
            rowFPagST.VAL_TIP_CAMBIO,rowFPagST.VAL_VUELTO*-1,rowFPagST.IM_TOTAL_PAGO*-1,
            rowFPagST.NUM_TARJ,rowFPagST.FEC_VENC_TARJ,rowFPagST.NOM_TARJ,vIdUsu_in);

          END
          LOOP;*/

          END IF;


      ELSE
        RAISE_APPLICATION_ERROR(-20034,'VERIFIQUE LA FECHA DEL PEDIDO.');
      END IF;

    ELSE --Pedido Normal
      --ERIOS 07/04/2008
      SELECT  COUNT(*) INTO v_nCantFP
      FROM VTA_FORMA_PAGO_PEDIDO P,
           VTA_FORMA_PAGO F
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND P.COD_LOCAL = cCodLocal_in
            AND P.NUM_PED_VTA = cNumPedVta_in
            AND P.COD_GRUPO_CIA = F.COD_GRUPO_CIA
            AND P.COD_FORMA_PAGO = F.COD_FORMA_PAGO;

      SELECT CP.VAL_NETO_COMP_PAGO INTO v_nValComp
      FROM VTA_COMP_PAGO CP
      WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
            AND CP.COD_LOCAL = cCodLocal_in
            AND CP.NUM_PED_VTA = cNumPedVta_in
            AND CP.TIP_COMP_PAGO LIKE cTipComp_in
            AND CP.NUM_COMP_PAGO LIKE cNumComp_in;

      --Si el pedido tien una sola forma de pago
      IF v_nCantFP = 1 THEN
        FOR rowFPago IN curFPago
        LOOP
          IF NOT (rowFPago.COD_FORMA_PAGO = g_cFormPagoDolares OR rowFPago.Ind_Tarj = 'S') THEN
            /* JMIRANDA 08/09/2009 se comento para actualizar con remoto y no DBLINK
            IF v_cIndPedConvenio = 'S' AND v_cCodConvenio = rowFPago.Cod_Convenio THEN
              --Actualiza Monto credito.
              --v_nMonCred := rowFPago.IM_TOTAL_PAGO*-1;
              v_nMonCred := v_nValComp*-1;
              PTOVENTA_CONV.CON_ACTUALIZA_CONSUMO_CLI(v_cCodConvenio,v_cCodCli,v_nMonCred);
            END IF;
            */
            DBMS_OUTPUT.put_line('CAJ_ANULAR_FORMA_PAGO : VTA_FORMA_PAGO_PEDIDO');
            INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,COD_LOCAL,
            COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,
            VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,USU_CREA_FORMA_PAGO_PED,COD_LOTE)
            VALUES(cCodGrupoCia_in,cCodLocal_in,
            rowFPago.COD_FORMA_PAGO,cNumPedNeg_in,v_nValComp*-1,rowFPago.TIP_MONEDA,
            0,0,v_nValComp*-1,vIdUsu_in,rowFPago.COD_LOTE);
          ELSE
            RAISE_APPLICATION_ERROR(-20037,'EL PEDIDO NO SE PAGO CON SOLES O CREDITO. DEBE ANULAR EL PEDIDO COMPLETO.');
          END IF;
        END LOOP;
      ELSE
        RAISE_APPLICATION_ERROR(-20036,'EL PEDIDO NO TIENE UNA SOLA FORMA DE PAGO. DEBE ANULAR EL PEDIDO COMPLETO.');
      END IF;
      /*SELECT VAL_NETO_PED_VTA INTO v_nMonto
      FROM VTA_PEDIDO_VTA_CAB
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_VTA = cNumPedNeg_in;

      INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,COD_LOCAL,
      COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,
      VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,
      USU_CREA_FORMA_PAGO_PED  )
      VALUES(cCodGrupoCia_in,cCodLocal_in,
      g_cFormPagoEfectivo,cNumPedNeg_in,v_nMonto,'01',--SOLES
      0,0,v_nMonto,vIdUsu_in);*/
    END IF;
    
    -- KMONCADA 20.04.2016 PERCEPCION
    SELECT NVL(CAB.VAL_PERCEPCION_PED_VTA,0) - NVL(CAB.VAL_REDONDEO_PERCEPCION,0)
    INTO vMontoPercepcion
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    IF vMontoPercepcion != 0 THEN
      BEGIN
        SELECT P.COD_FORMA_PAGO
        INTO vCodFPDsctPercepcion
        FROM VTA_FORMA_PAGO_PEDIDO P
        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
        AND P.COD_LOCAL = cCodLocal_in
        AND P.COD_FORMA_PAGO = g_cFormPagoEfectivo
        AND P.NUM_PED_VTA = cNumPedNeg_in;
      EXCEPTION 
        WHEN NO_DATA_FOUND THEN
          BEGIN
            SELECT P.COD_FORMA_PAGO
            INTO vCodFPDsctPercepcion
            FROM VTA_FORMA_PAGO_PEDIDO P
            WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND P.COD_LOCAL = cCodLocal_in
            AND P.COD_FORMA_PAGO = g_cFormPagoDolares
            AND P.NUM_PED_VTA = cNumPedNeg_in;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              vCodFPDsctPercepcion := NULL;
              /*RAISE_APPLICATION_ERROR(-20100,'ERROR AL REGISTRAR LA FORMA DE PAGO'||CHR(13)||
                                             'PEDIDO CON PERCEPCION');*/
          END;
      END;
      /*RAISE_APPLICATION_ERROR(-21000,'PRUEBA'||CHR(13)||
                                     'COD_FORMA_PAGO --> '||vCodFPDsctPercepcion||CHR(13)||
                                     'MONTO PERCEPCION --> '||vMontoPercepcion);*/
      UPDATE VTA_FORMA_PAGO_PEDIDO P
      SET P.IM_TOTAL_PAGO = P.IM_TOTAL_PAGO + vMontoPercepcion
      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
      AND P.COD_LOCAL = cCodLocal_in
      AND P.COD_FORMA_PAGO = vCodFPDsctPercepcion
      AND P.NUM_PED_VTA = cNumPedNeg_in;
    END IF;
   END;
  /* ********************************************************************************************** */
    --21/11/2007 dubilluz  modificacion
  PROCEDURE CAJ_ANULAR_PEDIDO_PENDIENTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN CHAR,
                                        cModulo_in      IN CHAR DEFAULT 'V')
  AS
    mesg_body VARCHAR2(4000);
    v_cIndProdVirtual CHAR(1);
    v_cIndDelivAutomatico VTA_PEDIDO_VTA_CAB.IND_DELIV_AUTOMATICO%TYPE;
    v_tip_ped_vta         VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE;
    CURSOR curProd IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,--ERIOS 05/06/2008 Se obtiene el cantida en el frac. del local.
                  DET.SEC_PED_VTA_DET,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                  SEC_RESPALDO_STK --ASOSA, 20.09.2010
           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           ORDER BY DET.CANT_ATENDIDA DESC ;
    v_rCurProd curProd%ROWTYPE;

    v_cEstPedido  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;

           /*
           CURSOR curListaProdRegaloAutomatico IS
                 SELECT COD_PROD,
                       CANT_MOV,
                       VAL_FRAC_LOCAL,
                       SEC_RESPALDO_STK --ASOSA, 20.09.2010
                FROM    PBL_RESPALDO_STK
                WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    COD_LOCAL     = cCodLocal_in
                AND    NUM_PED_VTA   = cNumPedVta_in
                AND    IND_REGALO    = 'A';
                */
    --SE AÑADIO UN CURSOR PARA AGRUPAR LOS PRODUCTOS DEL DETALLE Y ASI ELIMINAR EN LA
    --TABLA DE RESPALDO
    --18/10/2007  DUBILLUZ  MODIFICACION
    --21/11/2007 dubilluz modificado
    /* CURSOR curProdDel IS
           SELECT DET.COD_PROD,
                  SUM(DET.CANT_ATENDIDA) CANT_ATENDIDA
           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           GROUP BY DET.COD_PROD;

    v_rCurProdDel curProdDel%ROWTYPE;*/
    stkComp001 lgt_prod_local.Stk_Fisico%TYPE;-- stk_comprometido%TYPE; --ASOSA, 20.09.2010
    vCantPedInc_reca NUMBER;
  BEGIN
/*
SELECT count(D.NUM_PED_VTA) into vCantPedInc_reca
              FROM vta_pedido_vta_det d, lgt_prod_virtual p
             WHERE d.cod_grupo_cia = cCodGrupoCia_in
             AND   d.cod_local  = cCodLocal_in
             AND   D.NUM_PED_VTA = cNumPedVta_in
               AND d.num_ped_vta IN
                   (SELECT VTA_CAB.NUM_PED_VTA
                      FROM VTA_PEDIDO_VTA_CAB VTA_CAB
                     WHERE VTA_CAB.cod_grupo_cia = cCodGrupoCia_in
                     AND   VTA_CAB.cod_local = cCodLocal_in
                       AND VTA_CAB.NUM_PED_VTA >= cNumPedVta_in
                       AND VTA_CAB.EST_PED_VTA = 'P')
               and d.cod_grupo_cia = p.cod_grupo_cia
               and d.cod_prod = p.cod_prod
               and p.tip_prod_virtual = 'R';
     IF vCantPedInc_reca > 0 THEN
 RAISE_APPLICATION_ERROR(-20222,'¡No puede Anular este Pedido DE RECARGA! ');
     END IF;*/
       --JCORTEZ 09/01/2007
       --SE VALIDA EL ESTADO DEL PEDIDO ANTES DE ANULAR
       SELECT EST_PED_VTA,
              IND_PEDIDO_ANUL
       INTO   v_cEstPedido,
              v_cIndAnulado
       FROM   VTA_PEDIDO_VTA_CAB
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    NUM_PED_VTA = cNumPedVta_in  FOR UPDATE;
      -- AND    VAL_NETO_PED_VTA = nMontoVta_in

       if v_cEstPedido = 'N' THEN
             RAISE_APPLICATION_ERROR(-20002,'El Pedido esta anulado.');
          elsif v_cEstPedido = 'C' THEN
                RAISE_APPLICATION_ERROR(-20003,'El Pedido fue cobrado. ¡No puede Anular este Pedido!');
       END IF;

       IF v_cEstPedido <> 'P' THEN
          RAISE_APPLICATION_ERROR(-20001,'El Pedido no esta pendiente. ¡No puede Anular este Pedido!');
       END IF;

       SELECT VTA_CAB.IND_DELIV_AUTOMATICO,
              VTA_CAB.TIP_PED_VTA
       INTO    v_cIndDelivAutomatico,
               v_tip_ped_vta --12.08.2014
       FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
       WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    VTA_CAB.COD_LOCAL = cCodLocal_in
       AND    VTA_CAB.NUM_PED_VTA = cNumPedVta_in;

       UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in,  FEC_MOD_PED_VTA_CAB = SYSDATE,
              EST_PED_VTA = EST_PED_ANULADO,
              IND_PEDIDO_ANUL = INDICADOR_SI
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL     = cCodLocal_in
       AND    NUM_PED_VTA   = cNumPedVta_in;

       IF(v_cIndDelivAutomatico = INDICADOR_SI) THEN
          UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB SET TMP_CAB.USU_MOD_PED_VTA_CAB = vIdUsu_in, TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
                 -- KMONCADA 19.01.2015 PEDIDOS DELIVERY PASA A PENDIENTE CUANDO SE CANCELA COBRO
                 TMP_CAB.EST_PED_VTA = EST_PED_PENDIENTE
                  /*
                  --MODIFICADO UBILLUZ   12.08.2014
                  TMP_CAB.EST_PED_VTA =(
                  CASE
                    WHEN v_tip_ped_vta = '03' THEN
                    EST_PED_PENDIENTE ---PENDIENTE
                  ELSE
                    -- dubilluz 31.07.2014
                    EST_PED_ANULADO --ANULADO
                  END)*/,
                 --TMP_CAB.EST_PED_VTA = EST_PED_ANULADO,
                 TMP_CAB.Num_Ped_Vta_Origen = '' ,
                 -- TMP_CAB.IND_PEDIDO_ANUL = INDICADOR_SI --DUBILLUZ  24/08/2007
                 TMP_CAB.IND_PEDIDO_ANUL = INDICADOR_NO --DUBILLUZ  24/08/2007
          WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
          AND    TMP_CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
          -- KMONCADA 22.03.2016 LAS PROFORMAS LOCAL M SE VUELVEN A PONER PENDIENTE DE ENTREGA.
       ELSIF FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in) = 'S' THEN
          UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB 
          SET    TMP_CAB.USU_MOD_PED_VTA_CAB = vIdUsu_in, 
                 TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
                 TMP_CAB.EST_PED_VTA = 'S',
                 TMP_CAB.Num_Ped_Vta_Origen = NULL,
                 TMP_CAB.IND_PEDIDO_ANUL = INDICADOR_NO
          WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
          AND    TMP_CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
       END IF;

       FOR v_rCurProd IN curProd
       LOOP
           v_cIndProdVirtual := v_rCurProd.IND_PROD_VIR;
           IF v_cIndProdVirtual = INDICADOR_SI THEN
             UPDATE VTA_PEDIDO_VTA_DET  SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
                    EST_PED_VTA_DET = 'N'
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
             AND    NUM_PED_VTA   = cNumPedVta_in
             AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
           ELSE
                    --INI ASOSA, 20.09.2010
             /*SELECT STK_COMPROMETIDO
             INTO stkComp001
             FROM lgt_prod_local
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL = cCodLocal_in
             AND   COD_PROD = v_rCurProd.COD_PROD FOR UPDATE;
             IF stkComp001 - v_rCurProd.CANT_FRAC_LOCAL >= 0 THEN
                    --FIN ASOSA, 20.09.2010
                 UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vIdUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
                         --STK_COMPROMETIDO = STK_COMPROMETIDO - v_rCurProd.CANT_ATENDIDA
                        STK_COMPROMETIDO = STK_COMPROMETIDO - v_rCurProd.CANT_FRAC_LOCAL
                 WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND   COD_LOCAL = cCodLocal_in
                 AND   COD_PROD = v_rCurProd.COD_PROD;

                 UPDATE VTA_PEDIDO_VTA_DET  SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
                        EST_PED_VTA_DET = 'N'
                 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    COD_LOCAL     = cCodLocal_in
                 AND    NUM_PED_VTA   = cNumPedVta_in
                 AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
             END IF;
             */
             UPDATE VTA_PEDIDO_VTA_DET  SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
                        EST_PED_VTA_DET = 'N'
                 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    COD_LOCAL     = cCodLocal_in
                 AND    NUM_PED_VTA   = cNumPedVta_in
                 AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
                 --SE COMENTO EL DELETE PARA REALIZAR LUEGO CON EL CURSOR NUEVO
                 --18/10/2007  DUBILLUZ MODIFICACION

                 --ELIMINA RESPALDO STOCK
                /* DELETE PBL_RESPALDO_STK
                 WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                 AND     COD_LOCAL     = cCodLocal_in
                 AND    COD_PROD = v_rCurProd.COD_PROD
                 AND    NUM_PED_VTA   = cNumPedVta_in
                 AND    CANT_MOV = v_rCurProd.CANT_ATENDIDA
                 AND    MODULO = cModulo_in
                 AND    ROWNUM = 1;*/
              --21/11/2007 dubilluz modificado
               /*  DELETE PBL_RESPALDO_STK --ASOSA, 20.09.2010
                 WHERE  sec_respaldo_stk = v_rCurProd.Sec_Respaldo_Stk;*/
                /*UPDATE PBL_RESPALDO_STK
                SET    CANT_MOV = CANT_MOV -  v_rCurProd.CANT_ATENDIDA
                WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                AND    COD_PROD = v_rCurProd.COD_PROD
                AND     NUM_PED_VTA = cNumPedVta_in
                AND    CANT_MOV >= v_rCurProd.CANT_ATENDIDA
                AND    MODULO = cModulo_in
                AND    ROWNUM = 1;*/ --antes

           END IF;
      END LOOP;

          --ahora elimina los respaldo
          --21/11/2007 dubilluz  modificado
          /*DELETE PBL_RESPALDO_STK
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
           AND    COD_LOCAL = cCodLocal_in
          AND     NUM_PED_VTA = cNumPedVta_in
          AND    CANT_MOV = 0
          AND    MODULO = 'V';*/ --antes

          -------------------------
          --Inicio de añadir
          --Añadido para Eliminar los productos automaticos
          --dubilluz 18.02.2009
          /*FOR respaldoStk_rec IN curListaProdRegaloAutomatico LOOP
            UPDATE LGT_PROD_LOCAL l
               SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = vIdUsu_in,
                   STK_COMPROMETIDO = NVL(STK_COMPROMETIDO,0) - respaldoStk_rec.CANT_MOV/NVL(respaldoStk_rec.VAL_FRAC_LOCAL,l.val_frac_local)*l.val_frac_local --MODIFICACION JLUNA 20090216
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND    COD_LOCAL = cCodLocal_in
             AND    COD_PROD  = respaldoStk_rec.COD_PROD;

            DELETE PBL_RESPALDO_STK
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
            AND    NUM_PED_VTA   = cNumPedVta_in
            AND    IND_REGALO    = 'A'
            AND    COD_PROD      = respaldoStk_rec.COD_PROD
            AND    VAL_FRAC_LOCAL = respaldoStk_rec.VAL_FRAC_LOCAL
            AND    CANT_MOV = respaldoStk_rec.CANT_MOV
            --AND    ROWNUM = 1; antes
            AND    SEC_RESPALDO_STK = respaldoStk_rec.Sec_Respaldo_Stk; --ASOSA, 20.09.2010
          END LOOP;
          */
      --SE PROCEDERA A ABRIR EL CURSOR Y ELIMINAR LOS REGISTROS DE LA TABLA RESPALDO
      --18/10/2007 DUBILLUZ MODIFICACION
      /* FOR v_rCurProdDel IN curProdDel
       LOOP
      --SE ELIMINARAN REGISTROS DE RESPALDO STOCK
             --ELIMINA RESPALDO STOCK
             DELETE PBL_RESPALDO_STK
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND     COD_LOCAL     = cCodLocal_in
             AND    COD_PROD = v_rCurProdDel.COD_PROD
             AND    NUM_PED_VTA   = cNumPedVta_in
             AND    CANT_MOV = v_rCurProdDel.CANT_ATENDIDA
             AND    MODULO = cModulo_in
             AND    ROWNUM = 1;

      END LOOP;*/

      update vta_camp_pedido_cupon p
      set    p.estado = 'N'
      where  p.cod_grupo_cia = cCodGrupoCia_in
      and    p.cod_local = cCodLocal_in
      and    p.num_ped_vta = cNumPedVta_in
      and    p.estado = 'S';


  EXCEPTION
    WHEN OTHERS THEN
         mesg_body := 'ERROR AL ANULAR PEDIDO PENDIENTE No ' || cNumPedVta_in || '. ' || SQLERRM;
         FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    --'joliva@mifarma.com.pe, operador',
                                    'joliva@mifarma.com.pe, operador, asosa', --ASOSA, 20.09.2010
                                    'ERROR AL ANULAR PEDIDO PENDIENTE',
                                    'ERROR',
                                    mesg_body,
                                    '');
         RAISE;
  END;


  /* ********************************************************************************************** */
  FUNCTION CAJ_BUSCAR_PEDIDO_UNIR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedDia_in IN CHAR,vFecha_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
    curPed FarmaCursor;
  BEGIN
    OPEN curPed FOR
    SELECT NUM_PED_DIARIO || 'Ã' ||
            NUM_PED_VTA || 'Ã' ||
            TO_CHAR(FEC_PED_VTA,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
            TRIM(TO_CHAR(VAL_NETO_PED_VTA,'999,990.000')) || 'Ã' ||
            NVL(RUC_CLI_PED_VTA,' ') || 'Ã' ||
            NVL(NOM_CLI_PED_VTA,' ') || 'Ã' ||
            (SELECT DISTINCT LOGIN_USU
              FROM PBL_USU_LOCAL U, VTA_PEDIDO_VTA_DET D
              WHERE U.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                    AND U.COD_LOCAL = C.COD_LOCAL
                    AND D.NUM_PED_VTA = C.NUM_PED_VTA
                    AND U.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                    AND U.COD_LOCAL = D.COD_LOCAL
                    AND U.SEC_USU_LOCAL = D.SEC_USU_LOCAL )|| 'Ã' ||
            TIP_COMP_PAGO                     || 'Ã' ||
      TIP_PED_VTA || 'Ã' ||
      C.IND_PED_CONVENIO
    FROM VTA_PEDIDO_VTA_CAB C
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL     = cCodLocal_in
          AND NUM_PED_DIARIO   = cNumPedDia_in
          AND EST_PED_VTA = 'P'
          AND IND_PEDIDO_ANUL = 'N'
          AND TO_CHAR(FEC_PED_VTA,'dd/MM/yyyy') = vFecha_in;

    RETURN curPed;
  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_GET_DETALLE_PEDIDO_UNIR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT D.COD_PROD || 'Ã' ||
            P.DESC_PROD || 'Ã' ||
            D.UNID_VTA || 'Ã' ||
            TO_CHAR(D.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
            D.CANT_ATENDIDA || 'Ã' ||
            TO_CHAR(D.VAL_PREC_TOTAL,'999,990.000')
    FROM VTA_PEDIDO_VTA_DET D, LGT_PROD_LOCAL L, LGT_PROD P
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL     = cCodLocal_in
          AND D.NUM_PED_VTA   = cNumPedVta_in
          AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND D.COD_LOCAL     = L.COD_LOCAL
          AND D.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD;
    RETURN curDet;
  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_GET_NUMERO_PEDIDO_UNIR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,nMontoVta_in IN NUMBER,nTipoCam_in IN NUMBER,nNumCajaPago_in IN NUMBER,cTipComp_in IN CHAR, cTipPed_in IN CHAR, vIdUsu_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
    v_nNumPed VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    v_nNumPedDiario VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;

    v_cSecMovCaja_in VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;

    curPed FarmaCursor;
  BEGIN
    v_nNumPed:= Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPed),10,'0','I');
    v_nNumPedDiario:= Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPedDia),4,'0','I');

    SELECT SEC_MOV_CAJA INTO v_cSecMovCaja_in
    FROM VTA_CAJA_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_CAJA_PAGO = nNumCajaPago_in;
    DBMS_OUTPUT.put_line('CAJ_GET_NUMERO_PEDIDO_UNIR : VTA_PEDIDO_VTA_CAB');
    INSERT INTO VTA_PEDIDO_VTA_CAB(COD_GRUPO_CIA,
                                    COD_LOCAL,
                                    NUM_PED_VTA,
                                    COD_CLI_LOCAL,
                                    SEC_MOV_CAJA,
                                    TIP_PED_VTA,
                                    VAL_TIP_CAMBIO_PED_VTA,
                                    NUM_PED_DIARIO,
                                    CANT_ITEMS_PED_VTA,
                                    EST_PED_VTA,
                                    TIP_COMP_PAGO,
                                    NOM_CLI_PED_VTA,
                                    DIR_CLI_PED_VTA,
                                    RUC_CLI_PED_VTA,
                                    USU_CREA_PED_VTA_CAB,
                                    NUM_PED_VTA_ORIGEN)
    VALUES( cCodGrupoCia_in,
            cCodLocal_in,
            v_nNumPed,
            NULL,
            v_cSecMovCaja_in,
            cTipPed_in,
            nTipoCam_in,
            v_nNumPedDiario,
            1,
            'P',
            cTipComp_in,
            NULL,
            NULL,
            NULL,
            vIdUsu_in,
            NULL);

    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumPedDia,vIdUsu_in);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumPed,vIdUsu_in);

    OPEN curPed FOR
    SELECT v_nNumPed  || 'Ã' ||
          v_nNumPedDiario
    FROM DUAL;

    RETURN curPed;
  END;
  /* ********************************************************************************************** */
  PROCEDURE CAJ_GET_UNIR_COMPROBANTE_UNIR(cCodGrupoCia_in    IN CHAR,
                                          cCodLocal_in       IN CHAR,
                                          cNumPedNuevoVta_in IN CHAR,
                                          cNumPedVta_in      IN CHAR,
                                          vIdUsu_in          IN CHAR,
                                          cModulo_in         IN CHAR DEFAULT 'V')
  AS
    v_cEstado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cAnulado VTA_PEDIDO_VTA_CAB.IND_PEDIDO_ANUL%TYPE;
    v_cIndProdVirtual CHAR(1);
    CURSOR curProd IS
           SELECT DET.COD_GRUPO_CIA,
                  DET.COD_LOCAL,
                  DET.SEC_PED_VTA_DET,
                  DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.VAL_PREC_VTA,
                  DET.VAL_PREC_TOTAL,
                  DET.PORC_DCTO_1,
                  DET.PORC_DCTO_2,
                  DET.PORC_DCTO_3,
                  DET.PORC_DCTO_TOTAL,
                  DET.EST_PED_VTA_DET,
                  DET.VAL_TOTAL_BONO,
                  DET.VAL_FRAC,
                  DET.SEC_COMP_PAGO,
                  DET.SEC_USU_LOCAL,
                  DET.USU_CREA_PED_VTA_DET,
                  DET.VAL_PREC_LISTA,
                  DET.VAL_IGV,
                  DET.UNID_VTA,
                  DET.IND_EXONERADO_IGV,
                  DET.SEC_GRUPO_IMPR,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                  DET.CANT_USADA_NC,
                  DET.SEC_COMP_PAGO_ORIGEN,
                  DET.NUM_LOTE_PROD,
                  DET.FEC_PROCESO_GUIA_RD,
                  DET.DESC_NUM_TEL_REC,
                  DET.VAL_NUM_TRACE,
                  DET.VAL_COD_APROBACION,
                  DET.DESC_NUM_TARJ_VIRTUAL,
                  DET.VAL_NUM_PIN,
                  DET.FEC_VENCIMIENTO_LOTE,
                  DET.VAL_PREC_PUBLIC,
                  DET.IND_CALCULO_MAX_MIN,
                  DET.FEC_EXCLUSION,
                  DET.FECHA_TX,
                  DET.HORA_TX,
                  DET.COD_PROM,
                  DET.IND_ORIGEN_PROD,
                  DET.VAL_FRAC_LOCAL,
                  DET.CANT_FRAC_LOCAL,
                  DET.CANT_XDIA_TRA,
                  DET.CANT_DIAS_TRA,
                  DET.PORC_ZAN,      -- 2009-11-09 JOLIVA
                  NVL(DET.COD_GRUPO_REP,' ') COD_GRUPO_REP,
                  NVL(DET.COD_GRUPO_REP_EDMUNDO,' ') COD_GRUPO_REP_EDMUNDO,
                  DET.COD_CAMP_CUPON  --ASOSA, 21.05.2010
           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+);
    v_rCurProd curProd%ROWTYPE;

    v_nSec INTEGER;

    CURSOR curCab IS
    SELECT COD_CLI_LOCAL,
          VAL_BRUTO_PED_VTA,
          VAL_NETO_PED_VTA,
          VAL_REDONDEO_PED_VTA,
          VAL_IGV_PED_VTA,
          VAL_DCTO_PED_VTA,
          CANT_ITEMS_PED_VTA,
          TIP_COMP_PAGO,
          NOM_CLI_PED_VTA,
          DIR_CLI_PED_VTA,
          RUC_CLI_PED_VTA
    FROM VTA_PEDIDO_VTA_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;

    v_rCurCab curCab%ROWTYPE;
    --se creo un nuevo cursor para actualizar en el respaldo
    --21/11/2007 dubilluz  modificacion
    CURSOR curProdRespaldo IS
           SELECT DET.COD_GRUPO_CIA,
                  DET.COD_LOCAL,
                  DET.COD_PROD,
                  SUM(DET.CANT_ATENDIDA) CANT_ATENDIDA ,
                  DET.VAL_FRAC
           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           GROUP  BY DET.COD_GRUPO_CIA,
                  DET.COD_LOCAL,DET.COD_PROD, DET.VAL_FRAC;
    v_rCurProdRespaldo curProdRespaldo%ROWTYPE;

    --JCORTEZ 19.10.09
    CCOD_GRUPO_REP CHAR(3);
    CCOD_GRUPO_REP_EDMUNDO CHAR(3);
  BEGIN
    --COMPROBAR PEDIDO A UNIR
    SELECT EST_PED_VTA, IND_PEDIDO_ANUL INTO v_cEstado,v_cAnulado
    FROM VTA_PEDIDO_VTA_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in FOR UPDATE;
    IF v_cEstado = 'P' AND v_cAnulado = 'N' THEN
      SELECT COUNT(*) INTO v_nSec
      FROM VTA_PEDIDO_VTA_DET
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_VTA = cNumPedNuevoVta_in;
      FOR v_rCurProd IN curProd
      LOOP
        v_cIndProdVirtual := v_rCurProd.IND_PROD_VIR;
        IF v_cIndProdVirtual = INDICADOR_SI THEN
           RAISE_APPLICATION_ERROR(-20032,'No puede unir este pedido porque contiene productos virtuales. Verifique!!!');
        END IF;
        v_nSec:=v_nSec+1;

/*        --JCORTEZ 19.10.09
        SELECT NVL(X.COD_GRUPO_REP,' '),NVL(X.COD_GRUPO_REP_EDMUNDO,' ')
        INTO CCOD_GRUPO_REP,CCOD_GRUPO_REP_EDMUNDO
        FROM LGT_PROD X
        WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
        AND X.COD_PROD=v_rCurProd.COD_PROD;*/

        --UNIR PRODUCTOS
        DBMS_OUTPUT.put_line('CAJ_GET_UNIR_COMPROBANTE_UNIR : VTA_PEDIDO_VTA_DET');
        INSERT INTO VTA_PEDIDO_VTA_DET(COD_GRUPO_CIA,
                                        COD_LOCAL,
                                        NUM_PED_VTA,
                                        SEC_PED_VTA_DET,
                                        COD_PROD,
                                        CANT_ATENDIDA,
                                        VAL_PREC_VTA,
                                        VAL_PREC_TOTAL,
                                        PORC_DCTO_1,
                                        PORC_DCTO_2,
                                        PORC_DCTO_3,
                                        PORC_DCTO_TOTAL,
                                        EST_PED_VTA_DET,
                                        VAL_TOTAL_BONO,
                                        VAL_FRAC,
                                        SEC_COMP_PAGO,
                                        SEC_USU_LOCAL,
                                        USU_CREA_PED_VTA_DET,
                                        VAL_PREC_LISTA,
                                        VAL_IGV,
                                        UNID_VTA,
                                        IND_EXONERADO_IGV,
                                        SEC_GRUPO_IMPR,
                                        CANT_USADA_NC,
                                        SEC_COMP_PAGO_ORIGEN,
                                        NUM_LOTE_PROD,
                                        FEC_PROCESO_GUIA_RD,
                                        DESC_NUM_TEL_REC,
                                        VAL_NUM_TRACE,
                                        VAL_COD_APROBACION,
                                        DESC_NUM_TARJ_VIRTUAL,
                                        VAL_NUM_PIN,
                                        FEC_VENCIMIENTO_LOTE,
                                        VAL_PREC_PUBLIC,
                                        IND_CALCULO_MAX_MIN,
                                        FEC_EXCLUSION,
                                        FECHA_TX,
                                        HORA_TX,
                                        COD_PROM,
                                        IND_ORIGEN_PROD,
                                        VAL_FRAC_LOCAL,
                                        CANT_FRAC_LOCAL,
                                        CANT_XDIA_TRA,
                                        CANT_DIAS_TRA,
                                        COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO, --JCORTEZ 19.10.09
                                        PORC_ZAN,      -- 2009-11-09
                                        COD_CAMP_CUPON --ASOSA, 21.05.2010
                                        )
        VALUES(v_rCurProd.COD_GRUPO_CIA,
              v_rCurProd.COD_LOCAL,
              cNumPedNuevoVta_in,
              v_nSec,
              v_rCurProd.COD_PROD,
              v_rCurProd.CANT_ATENDIDA,
              v_rCurProd.VAL_PREC_VTA,
              v_rCurProd.VAL_PREC_TOTAL,
              v_rCurProd.PORC_DCTO_1,
              v_rCurProd.PORC_DCTO_2,
              v_rCurProd.PORC_DCTO_3,
              v_rCurProd.PORC_DCTO_TOTAL,
              v_rCurProd.EST_PED_VTA_DET,
              v_rCurProd.VAL_TOTAL_BONO,
              v_rCurProd.VAL_FRAC,
              v_rCurProd.SEC_COMP_PAGO,
              v_rCurProd.SEC_USU_LOCAL,
              v_rCurProd.USU_CREA_PED_VTA_DET,
              v_rCurProd.VAL_PREC_LISTA,
              v_rCurProd.VAL_IGV,
              v_rCurProd.UNID_VTA,
              v_rCurProd.IND_EXONERADO_IGV,
              v_rCurProd.SEC_GRUPO_IMPR,
              v_rCurProd.CANT_USADA_NC,
              v_rCurProd.SEC_COMP_PAGO_ORIGEN,
              v_rCurProd.NUM_LOTE_PROD,
              v_rCurProd.FEC_PROCESO_GUIA_RD,
              v_rCurProd.DESC_NUM_TEL_REC,
              v_rCurProd.VAL_NUM_TRACE,
              v_rCurProd.VAL_COD_APROBACION,
              v_rCurProd.DESC_NUM_TARJ_VIRTUAL,
              v_rCurProd.VAL_NUM_PIN,
              v_rCurProd.FEC_VENCIMIENTO_LOTE,
              v_rCurProd.VAL_PREC_PUBLIC,
              v_rCurProd.IND_CALCULO_MAX_MIN,
              v_rCurProd.FEC_EXCLUSION,
              v_rCurProd.FECHA_TX,
              v_rCurProd.HORA_TX,
              v_rCurProd.COD_PROM,
              v_rCurProd.IND_ORIGEN_PROD,
              v_rCurProd.VAL_FRAC_LOCAL,
              v_rCurProd.CANT_FRAC_LOCAL,
              v_rCurProd.CANT_XDIA_TRA,
              v_rCurProd.CANT_DIAS_TRA,
              v_rCurProd.COD_GRUPO_REP,v_rCurProd.COD_GRUPO_REP_EDMUNDO, --JCORTEZ 19.10.09
              v_rCurProd.PORC_ZAN,      -- 2009-11-09 JOLIVA
              v_rCurProd.Cod_Camp_Cupon -- ASOSA, 21.05.2010
              );
        --ACTUALIZAR RESPALDO STOCK
        --24/05/2006  ERIOS
/*    UPDATE PBL_RESPALDO_STK SET FEC_MOD_RESPALDO_STK=SYSDATE ,USU_MOD_RESPALDO_STK =vIdUsu_in,
             NUM_PED_VTA  = cNumPedNuevoVta_in
        WHERE COD_GRUPO_CIA = v_rCurProd.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCurProd.COD_LOCAL
              AND COD_PROD = v_rCurProd.COD_PROD
              AND NUM_PED_VTA = cNumPedVta_in
              AND CANT_MOV = v_rCurProd.CANT_ATENDIDA
              AND MODULO = cModulo_in
              AND VAL_FRAC_LOCAL = v_rCurProd.VAL_FRAC;*/
      END LOOP;
      --ACTUALIZARA EL NUMERO DE PEDIDO NUEVO EN LA TABLA RESPALDO STOCK
      /*FOR v_rCurProdRespaldo IN curProdRespaldo
      LOOP
          UPDATE PBL_RESPALDO_STK SET FEC_MOD_RESPALDO_STK=SYSDATE ,USU_MOD_RESPALDO_STK =vIdUsu_in,
             NUM_PED_VTA  = cNumPedNuevoVta_in
        WHERE COD_GRUPO_CIA = v_rCurProdRespaldo.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCurProdRespaldo.COD_LOCAL
              AND COD_PROD = v_rCurProdRespaldo.COD_PROD
              AND NUM_PED_VTA = cNumPedVta_in
              AND CANT_MOV = v_rCurProdRespaldo.CANT_ATENDIDA
              AND MODULO = cModulo_in
              AND VAL_FRAC_LOCAL = v_rCurProdRespaldo.VAL_FRAC;
      END LOOP;*/
      ---FIN

      --ANULAR DETALLE
        UPDATE VTA_PEDIDO_VTA_DET SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
             EST_PED_VTA_DET='N'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_PED_VTA = cNumPedVta_in;

      FOR v_rCurCab IN curCab
      LOOP
        --SUMAR CABECERA
        UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in,FEC_MOD_PED_VTA_CAB = SYSDATE,
          COD_CLI_LOCAL = DECODE(v_rCurCab.COD_CLI_LOCAL,NULL,COD_CLI_LOCAL,v_rCurCab.COD_CLI_LOCAL),
          VAL_BRUTO_PED_VTA = VAL_BRUTO_PED_VTA+v_rCurCab.VAL_BRUTO_PED_VTA,
          VAL_NETO_PED_VTA = VAL_NETO_PED_VTA+v_rCurCab.VAL_NETO_PED_VTA,
          VAL_REDONDEO_PED_VTA = VAL_REDONDEO_PED_VTA+v_rCurCab.VAL_REDONDEO_PED_VTA,
          VAL_IGV_PED_VTA=VAL_IGV_PED_VTA+v_rCurCab.VAL_IGV_PED_VTA,
          VAL_DCTO_PED_VTA = VAL_DCTO_PED_VTA+v_rCurCab.VAL_DCTO_PED_VTA,
          --CANT_ITEMS_PED_VTA = CANT_ITEMS_PED_VTA+v_rCurCab.CANT_ITEMS_PED_VTA,
          CANT_ITEMS_PED_VTA = v_nSec,
          TIP_COMP_PAGO = v_rCurCab.TIP_COMP_PAGO,
          NOM_CLI_PED_VTA = v_rCurCab.NOM_CLI_PED_VTA,
          DIR_CLI_PED_VTA = v_rCurCab.DIR_CLI_PED_VTA,
          RUC_CLI_PED_VTA = v_rCurCab.RUC_CLI_PED_VTA
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_PED_VTA = cNumPedNuevoVta_in;
        --ANULAR CABECERA
        UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in,FEC_MOD_PED_VTA_CAB = SYSDATE,
             EST_PED_VTA = 'N',
               IND_PEDIDO_ANUL = 'S'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_PED_VTA = cNumPedVta_in;
      END LOOP;


    ELSE
      RAISE_APPLICATION_ERROR(-20031,'No puede unir este pedido porque ha sido anulado o cobrado. Verifiqe:'||cNumPedVta_in);
    END IF;

  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_LISTA_DETALLE_NOTA_CREDITO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cTipComp_in IN CHAR, cNumComp_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT D.COD_PROD || 'Ã' ||
           R.DESC_PROD || 'Ã' ||
           D.UNID_VTA || 'Ã' ||
           L.NOM_LAB || 'Ã' ||
           TO_CHAR(D.CANT_ATENDIDA - D.CANT_USADA_NC) || 'Ã' ||
           TO_CHAR(D.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
           --'0' || 'Ã' ||
           TO_CHAR(D.CANT_ATENDIDA - D.CANT_USADA_NC) || 'Ã' ||
           D.VAL_FRAC || 'Ã' ||
           TO_CHAR(D.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
           D.VAL_IGV || 'Ã' ||
           D.SEC_PED_VTA_DET
    FROM VTA_PEDIDO_VTA_DET D, VTA_COMP_PAGO C, LGT_PROD_LOCAL P, LGT_PROD R, LGT_LAB L
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND D.CANT_ATENDIDA-D.CANT_USADA_NC > 0
          AND D.EST_PED_VTA_DET = 'A'
          AND C.TIP_COMP_PAGO LIKE cTipComp_in
          AND C.NUM_COMP_PAGO LIKE cNumComp_in
          AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND D.COD_LOCAL = C.COD_LOCAL
          AND D.NUM_PED_VTA = C.NUM_PED_VTA
          AND D.SEC_COMP_PAGO = C.SEC_COMP_PAGO
          AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND D.COD_LOCAL = P.COD_LOCAL
          AND D.COD_PROD = P.COD_PROD
          AND P.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND P.COD_PROD = R.COD_PROD
          AND R.COD_LAB = L.COD_LAB;

    RETURN curDet;
  END;

  /* ********************************************************************************************** */
  FUNCTION CAJ_AGREGAR_CAB_NOTA_CREDITO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumVtaAnt_in IN CHAR,nTipoCam_in IN NUMBER,
                                      vIdUsu_in IN VARCHAR2,nNumCajaPago_in IN NUMBER,cMotivoAnulacion IN VARCHAR2)
  RETURN CHAR
  IS
    --v_nNumPed VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    v_nNumPed varchar2(30000);
    v_nNumPedDiario VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;

    CURSOR curCab IS
      SELECT  CA.COD_CLI_LOCAL,
              CA.TIP_PED_VTA,
              CA.NOM_CLI_PED_VTA,
              CA.DIR_CLI_PED_VTA,
              CA.RUC_CLI_PED_VTA,
              CA.IND_PED_CONVENIO,
              CA.COD_CONVENIO,
              CA.IND_FID,
              CA.DNI_CLI,
              CA.ind_conv_btl_mf,
              CA.ip_cob_ped,
              CA.name_pc_cob_ped,
              CA.dni_usu_local,
              CA.fecha_proceso_nc_rac,
              CA.fecha_proceso_anula_rac,
              CA.fec_proceso_rac,
              -- DUBILLUZ 27.09.2013
              CP.SEC_COMP_PAGO,
              CP.TIP_CLIEN_CONVENIO,
              cp.VAL_BRUTO_COMP_PAGO,
              -- KMONCADA 20.04.2016 [PERCEPCION] NO CONCIDERA LA PERCEPCION EN TOTAL DE CABECERA PEDIDO
              --cp.VAL_NETO_COMP_PAGO, 
              (CP.VAL_NETO_COMP_PAGO - NVL(CP.VAL_PERCEP_COMP_PAGO,0)) VAL_NETO_COMP_PAGO,
              cp.VAL_DCTO_COMP_PAGO,
              cp.VAL_AFECTO_COMP_PAGO,
              cp.VAL_IGV_COMP_PAGO,
              cp.VAL_REDONDEO_COMP_PAGO,
              CA.NUM_PEDIDO_DELIVERY, --LTAVARA 16.09.2014 PARA GENERA UNA NC DE UN PEDIDO DELIVERY QUE ESTA EN LOS TEMPORALES
              CASE
                 WHEN CP.TIP_COMP_PAGO ='03' THEN '03'
                 ELSE '04' END TIP_COMP_PAGO,
              -- CP.TIP_COMP_PAGO -- kmoncada 26.06.2014 para identificar tipo documento en venta institucional
              CA.COD_CLI_CONV,     -- KMONCADA 29.10.2014 GRABARA CODIGO CLIENTE CONVENIO (CONVENIO MIXTO)

              (CA.PT_INICIAL * (-1)) PT_INICIAL,
              (CA.PT_ACUMULADO * (-1)) PT_ACUMULADO,
              (CA.PT_REDIMIDO * (-1)) PT_REDIMIDO,
              (CA.PT_TOTAL * (-1)) PT_TOTAL,
              --KMONCADA 14.09.2015 SE GRABARA EL NRO DE TARJETA EN CASO DE PTOS
              NVL(CA.NUM_TARJ_PUNTOS,'') NUM_TARJ_PUNTOS,
              CASE WHEN CA.EST_TRX_ORBIS IN ('E','P') THEN 'P' ELSE NULL END EST_TRX_ORBIS,
              NVL(CP.VAL_PERCEP_COMP_PAGO,0) VAL_PERCEP_COMP_PAGO,
              NVL(CP.VAL_REDONDEO_PERCEPCION,0) VAL_REDONDEO_PERCEPCION,
              NVL(CP.VAL_PCT_PERCEPCION,0) VAL_PCT_PERCEPCION
    FROM    VTA_PEDIDO_VTA_CAB CA,
            VTA_COMP_PAGO CP
    WHERE CA.COD_GRUPO_CIA = cCodGrupoCia_in
          AND CA.COD_LOCAL = cCodLocal_in
          AND CA.NUM_PED_VTA = cNumVtaAnt_in
          AND CA.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
          AND CA.COD_LOCAL = CP.COD_LOCAL
          AND CA.NUM_PED_VTA = CP.NUM_PED_VTA FOR UPDATE;


    v_rCurCab curcab%ROWTYPE;

    v_cSecMovCaja_in VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;

    -- dubilluz 27.09.2013
    vSeparador char(1) := '@';
    -- cantidad de comprobantes
    vCtdComprobantes number;

    vRetornaNumePedidos varchar2(30000) := '';

    vSaltarGuiaInstitucional char(1) :='N'; --kmoncada 26.06.2014 variable que indica si es guia en caso de venta institucional
    
    -- KMONCADA 03.02.2015 
    vCantAnulado INTEGER := '0';
    
    fechaSecMovCaja CE_MOV_CAJA.FEC_DIA_VTA%TYPE;
  BEGIN
    
    -- KMONCADA 03.02.2015 VALIDA SI PEDIDO YA FUE ANULADO
    SELECT COUNT(1)
      INTO vCantAnulado
      FROM VTA_PEDIDO_VTA_CAB CAB
     WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND CAB.COD_LOCAL = cCodLocal_in
       AND CAB.NUM_PED_VTA_ORIGEN = cNumVtaAnt_in;
    
    IF vCantAnulado > 0 THEN
      RAISE_APPLICATION_ERROR(-20301,'El Pedido ya está anulado. ¡No puede Anular este Pedido!');
    END IF;
    /*
    Se generar cantidad de NOTAS DE PEDIDOS NOTA DE CREDITO
    SI ESTE PEDIDO TIENE N COMPROBANTES SE GENERARA TODO
    -- dubilluz 27.09.2013
    v_nNumPed:= Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPed),10,'0','I');
    v_nNumPedDiario:= Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPedDia),4,'0','I');
    */

    select count(1)
      into vCtdComprobantes
      from VTA_COMP_PAGO CP
     WHERE Cp.COD_GRUPO_CIA = cCodGrupoCia_in
       AND Cp.COD_LOCAL = cCodLocal_in
       AND Cp.NUM_PED_VTA = cNumVtaAnt_in;

    SELECT SEC_MOV_CAJA
      INTO v_cSecMovCaja_in
      FROM VTA_CAJA_PAGO
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_CAJA_PAGO = nNumCajaPago_in;

      UPDATE VTA_PEDIDO_VTA_CAB
         SET USU_MOD_PED_VTA_CAB = vIdUsu_in,
             FEC_MOD_PED_VTA_CAB = SYSDATE,
             MOTIVO_ANULACION    = REPLACE(REPLACE(REPLACE(cMotivoAnulacion,CHR(10) || CHR(13),' '), CHR(9),' '), CHR(10), ' ')
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND NUM_PED_VTA = cNumVtaAnt_in;

    /*OPEN curCab;
    FETCH curCab INTO v_rCurCab;*/
      OPEN curCab;
       LOOP
        FETCH curCab INTO v_rCurCab;
        EXIT WHEN curCab%NOTFOUND;

        -- kmoncada 26.06.2014 no grabara la nota de credito por la guia de remision
        -- en el caso de venta empresa.
        IF v_rCurCab.TIP_PED_VTA = '03' AND v_rCurCab.TIP_COMP_PAGO = '03' THEN
           vSaltarGuiaInstitucional :='S';
        END IF;

        IF vSaltarGuiaInstitucional='N' THEN
           DBMS_OUTPUT.put_line('CAJ_AGREGAR_CAB_NOTA_CREDITO : VTA_PEDIDO_VTA_CAB');

            -- dubilluz 27.09.2013
            v_nNumPed:= Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPed),10,'0','I');
            v_nNumPedDiario:= Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumPedDia),4,'0','I');
            -- dubilluz 27.09.2013
            if vCtdComprobantes > 1 then
             if trim(vRetornaNumePedidos) is null then
               vRetornaNumePedidos := v_nNumPed;
             else
               vRetornaNumePedidos := vRetornaNumePedidos || '@' ||v_nNumPed;
             end if;
            else
             if trim(vRetornaNumePedidos) is null then
               vRetornaNumePedidos := v_nNumPed;
             else
               vRetornaNumePedidos := vRetornaNumePedidos || '@' ||v_nNumPed;
             end if;
            end if;

    INSERT INTO VTA_PEDIDO_VTA_CAB(COD_GRUPO_CIA,
                                    COD_LOCAL,
                                    NUM_PED_VTA,
                                    COD_CLI_LOCAL,
                                    SEC_MOV_CAJA,
                                    TIP_PED_VTA,
                                    VAL_TIP_CAMBIO_PED_VTA,
                                    NUM_PED_DIARIO,
                                    CANT_ITEMS_PED_VTA,
                                    EST_PED_VTA,
                                    TIP_COMP_PAGO,
                                    NOM_CLI_PED_VTA,
                                    DIR_CLI_PED_VTA,
                                    RUC_CLI_PED_VTA,
                                    USU_CREA_PED_VTA_CAB,
                                    NUM_PED_VTA_ORIGEN,
                                    IND_PED_CONVENIO,
                                    COD_CONVENIO,
                                    IND_FID,
                                    DNI_CLI,
                                    ind_conv_btl_mf,
                                    ip_cob_ped,
                                    name_pc_cob_ped,
                                    dni_usu_local,
                                    fecha_proceso_nc_rac,
                                    fecha_proceso_anula_rac,
                                    fec_proceso_rac,
                                    SEC_COMP_PAGO,TIP_CLIEN_CONVENIO,
                                    VAL_BRUTO_PED_VTA,
                                    VAL_NETO_PED_VTA,
                                    VAL_REDONDEO_PED_VTA,
                                    VAL_IGV_PED_VTA,
                                    VAL_DCTO_PED_VTA,
                                    MOTIVO_ANULACION, -- LTAVARA 03.09.2014 17:27, PARA ENVIAR EN EL COMPROBANTE ELECTRONICO
                                    NUM_PEDIDO_DELIVERY,-- LTAVARA 16.09.2014 10:27
                                    COD_CLI_CONV, -- KMONCADA 29.10.2014 GRABARA CODIGO CLIENTE CONVENIO (CONVENIO MIXTO)
                                    PT_INICIAL,
                                    PT_ACUMULADO,
                                    PT_REDIMIDO,
                                    PT_TOTAL,
                                    --KMONCADA 14.09.2015 SE GRABARA EL NRO DE TARJETA EN CASO DE PTOS
                                    NUM_TARJ_PUNTOS,
                                    EST_TRX_ORBIS/*,
                                    VAL_PERCEPCION_PED_VTA,
                                    VAL_REDONDEO_PERCEPCION*/
                                    )
    VALUES( cCodGrupoCia_in,
            cCodLocal_in,
            v_nNumPed,
            v_rCurCab.COD_CLI_LOCAL,
            v_cSecMovCaja_in,
            v_rCurCab.TIP_PED_VTA,
            nTipoCam_in,
            v_nNumPedDiario,
            1,
            'P',
            v_rCurCab.TIP_COMP_PAGO, -- '04',--NOTA CREDITO
            v_rCurCab.NOM_CLI_PED_VTA,
            v_rCurCab.DIR_CLI_PED_VTA,
            v_rCurCab.RUC_CLI_PED_VTA,
            vIdUsu_in,
            cNumVtaAnt_in,
            v_rCurCab.IND_PED_CONVENIO,
            v_rCurCab.COD_CONVENIO,
            v_rCurCab.Ind_Fid,
            v_rCurCab.DNI_CLI,
            v_rCurCab.ind_conv_btl_mf,
            v_rCurCab.ip_cob_ped,
            v_rCurCab.name_pc_cob_ped,
            v_rCurCab.dni_usu_local,
            v_rCurCab.fecha_proceso_nc_rac,
            v_rCurCab.fecha_proceso_anula_rac,
            v_rCurCab.fec_proceso_rac,
            v_rCurCab.SEC_COMP_PAGO,
            v_rCurCab.TIP_CLIEN_CONVENIO,
            v_rCurCab.VAL_BRUTO_COMP_PAGO*-1,
            (v_rCurCab.VAL_NETO_COMP_PAGO+v_rCurCab.VAL_REDONDEO_COMP_PAGO)*(-1),
            v_rCurCab.VAL_REDONDEO_COMP_PAGO*-1,
            v_rCurCab.VAL_IGV_COMP_PAGO*-1,
            v_rCurCab.VAL_DCTO_COMP_PAGO*-1,
            REPLACE(REPLACE(REPLACE(cMotivoAnulacion,CHR(10)||CHR(13),' '),CHR(9),' '),CHR(10),' '), -- LTAVARA 03.09.2014 17:27, PARA ENVIAR EN EL COMPROBANTE ELECTRONICO
            v_rCurCab.NUM_PEDIDO_DELIVERY,  -- LTAVARA 16.09.2014 10:27
            v_rCurCab.COD_CLI_CONV, -- KMONCADA 29.10.2014 GRABARA CODIGO CLIENTE CONVENIO (CONVENIO MIXTO)
            v_rCurCab.PT_INICIAL,
            v_rCurCab.PT_ACUMULADO,
            v_rCurCab.PT_REDIMIDO,
            v_rCurCab.PT_TOTAL,
            --KMONCADA 14.09.2015 SE GRABARA EL NRO DE TARJETA EN CASO DE PTOS
            v_rCurCab.NUM_TARJ_PUNTOS,
            v_rCurCab.EST_TRX_ORBIS/*,
            -- KMONCADA 25.04.2016 [PERCEPCION]
            v_rCurCab.Val_Percep_Comp_Pago * (-1),
            (v_rCurCab.Val_Redondeo_Percepcion) * (-1)*/
            );
-- dubilluz 27.09.2013
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumPedDia,vIdUsu_in);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumPed,vIdUsu_in);
-- dubilluz 27.09.2013
        vCtdComprobantes := vCtdComprobantes - 1;

        ELSE
           vSaltarGuiaInstitucional :='N';
        END IF;
      end loop;

      close curCab;

    --KMONCADA 12.04.2015 ANULA CUPONES
      CAJ_ANULA_CUPONES(cCodGrupoCia_in => cCodGrupoCia_in,
                        cCodLocal_in => cCodLocal_in,
                        cNumPedVta_in => cNumVtaAnt_in,
                        vIdUsu_in => vIdUsu_in);
      -- anula ahorro diario acumulado
      insert into PBL_UNID_DCTO_X_NUM_DOC
      (cod_grupo_cia, cod_local, num_ped_vta, num_doc_id, dia_vta, 
       cod_prod, cant_atendida, val_frac, usu_crea)
      select cod_grupo_cia, cod_local, v_nNumPed, num_doc_id, dia_vta, 
             cod_prod, abs(cant_atendida)*-1, abs(val_frac), usu_crea
      from   PBL_UNID_DCTO_X_NUM_DOC
      where  cod_grupo_cia = cCodGrupoCia_in
      and    cod_local = cCodLocal_in
      and    num_ped_vta = cNumVtaAnt_in;
      
    -- KMONCADA 21.08.2015 VALIDAR FECHAS DE MOVIMIENTO DE CAJA CON FECHA DE PEDIDO
    BEGIN
      SELECT FEC_DIA_VTA
      INTO fechaSecMovCaja
      FROM CE_MOV_CAJA 
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in 
      AND COD_LOCAL = cCodLocal_in 
      AND SEC_MOV_CAJA = v_cSecMovCaja_in;
      
      UPDATE VTA_PEDIDO_VTA_CAB CAB
      SET CAB.FEC_PED_VTA = CASE
                              WHEN TRUNC(SYSDATE) > TRUNC(fechaSecMovCaja) THEN
                                fechaSecMovCaja + 23/24 + 59/24/60 + 55/24/60/60
                              ELSE
                                SYSDATE
                            END
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA = v_nNumPed;
    EXCEPTION 
      WHEN OTHERS THEN
        NULL;
    END;               
    -- RETORNA NUMERO DE PEDIDOS QUE VA GENERAR EL COMPROBANTE
    -- separado por @

    RETURN vRetornaNumePedidos;
  END;
  /* *********************************************************************** */
   FUNCTION CAJ_AGREGAR_DET_NC(cCodGrupoCia_in     IN CHAR,
                               cCodLocal_in        IN CHAR,
                               cNumVtaAnt_in       IN CHAR,
                               cNumVta_in          IN CHAR,
                               -- estos valores no se van a usar
                               cCodProd_in         IN CHAR,
                               nCantProd_in        IN NUMBER,
                               nTotal_in           IN NUMBER,
                               vIdUsu_in           IN VARCHAR2,
                               nSecDetPed_in       IN NUMBER,
                               nNumCajaPago_in     IN NUMBER,
                               cFlagElectronico    in CHAR DEFAULT '0', --LTAVARA 03.09.14 20:20 FLAG PROCESO ELECTRONICO
                               cCodTipMotivoNotaE  in CHAR DEFAULT '6' --LTAVARA 15.10.2014 TIPO MOTIVO NC
                               )
     -- KMONCADA 22.10.2014 PARA QUE DEVUELVA EL NUMERO DE NC GENERADO
     RETURN VARCHAR2 IS
     V_NUM_COMP_PAGO_NC VARCHAR2(100);
  --AS
    CURSOR curProd IS
    SELECT D.COD_GRUPO_CIA,
            D.COD_LOCAL,
            D.SEC_PED_VTA_DET,
            D.COD_PROD,
            D.CANT_ATENDIDA,
            D.VAL_PREC_VTA,
            D.VAL_PREC_TOTAL,
            D.PORC_DCTO_1,
            D.PORC_DCTO_2,
            D.PORC_DCTO_3,
            D.PORC_DCTO_TOTAL,
            D.EST_PED_VTA_DET,
            D.VAL_TOTAL_BONO,
            D.VAL_FRAC,
            D.SEC_COMP_PAGO,
            D.SEC_USU_LOCAL,
            D.USU_CREA_PED_VTA_DET,
            D.VAL_PREC_LISTA,
            D.VAL_IGV,
            D.UNID_VTA,
            D.IND_EXONERADO_IGV,
            D.SEC_GRUPO_IMPR,
            D.IND_ORIGEN_PROD,
            L.VAL_FRAC_LOCAL,
            D.PORC_ZAN,
            NVL(D.COD_GRUPO_REP,' ') COD_GRUPO_REP,
            NVL(D.COD_GRUPO_REP_EDMUNDO,' ') COD_GRUPO_REP_EDMUNDO,
            D.COD_PROM,
            D.COD_CAMP_CUPON,
            D.AHORRO_CONV,
            D.NUM_COMP_PAGO,
            D.SEC_COMP_PAGO_BENEF,
            D.SEC_COMP_PAGO_EMPRE,
            NVL(D.VAL_PREC_TOTAL_EMPRE,d.val_prec_total) VAL_PREC_TOTAL_EMPRE,
            case
              --si es nulo es porque es un pedido descuento o solo imprime
              --comprobante al beneficiario al 100%
              when trim(D.SEC_COMP_PAGO_EMPRE) is null then d.val_prec_total
              else D.VAL_PREC_TOTAL_BENEF--,
            end  as "VAL_PREC_TOTAL_BENEF"
            --D.sec_comp_pago
             /* INICIO LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014  */
              ,
              D.COD_TIP_AFEC_IGV_E,
              D.COD_TIP_PREC_VTA_E,
              D.VAL_PREC_VTA_UNIT_E,
              D.VAL_VTA_UNIT_ITEM_E,
              D.CANT_UNID_VDD_E,
              D.VAL_VTA_ITEM_E,
              D.VAL_TOTAL_IGV_ITEM_E,
              D.VAL_TOTAL_DESC_ITEM_E,
               /* FIN LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014  */
              (D.AHORRO * (-1)) AHORRO, --LTAVARA 05.11.2014

              D.IND_PROD_MAS_1,
              
              D.COD_PROD_PUNTOS,
              D.IND_BONIFICADO,
              D.FACTOR_PUNTOS,
              (D.CTD_PUNTOS_ACUM * (-1)) CTD_PUNTOS_ACUM,
              (D.AHORRO_PUNTOS * (-1)) AHORRO_PUNTOS,
              (D.AHORRO_CAMP * (-1)) AHORRO_CAMP,
              (D.PTOS_AHORRO * (-1)) PTOS_AHORRO,
              (D.AHORRO_PACK * (-1)) AHORRO_PACK,
              (D.PTOS_AHORRO_PACK * (-1)) PTOS_AHORRO_PACK,
			  -- KMONCADA 10.02.2016 SE CARGARA LOS DATOS DE CONTROL LOTE [LOCAL M]
              D.FECHA_VENCIMIENTO_LOTE,
              D.LOTE,
              D.NUM_LOTE_PROD,
			  D.FEC_VENCIMIENTO_LOTE,
              -- KMONCADA 20.04.2016 PERCEPCION
              D.VAL_PCT_PERCEPCION,
              (D.VAL_MONTO_PERCEPCION * (-1)) VAL_MONTO_PERCEPCION
              
    FROM VTA_PEDIDO_VTA_DET D,
         LGT_PROD_LOCAL L
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumVtaAnt_in
          AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND D.COD_LOCAL = L.COD_LOCAL
          AND D.COD_PROD = L.COD_PROD;

    v_rCurProd curProd%ROWTYPE;

    v_nSec INTEGER;
  
    --JCORTEZ 19.10.09
    CCOD_GRUPO_REP CHAR(3);
    CCOD_GRUPO_REP_EDMUNDO CHAR(3);

    vSecCompPago_in vta_pedido_vta_cab.sec_comp_pago%type;
    vTipClieConv_in  vta_pedido_vta_cab.tip_clien_convenio%type;

  DOC_BENEFICIARIO char(1) := '1';
  DOC_EMPRESA char(1) := '2';

  v_nSecCompPago VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
   v_nNumCompPago VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;--LTAVARA 01.10.2014
  vCodNumera_SecCompPAgo_in varchar2(5) := '015';
  v_cSecMovCaja_in VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;
  vIndAfectaKardex_in char(1);
  vNetoPedido_in vta_pedido_vta_cab.val_neto_ped_vta%type;

  vNumCompPago_in vta_comp_pago.num_comp_pago%type;
  -- dubilluz 23.04.2014
  isConvenio char(1) := 'N';
  vImptotalCredito vta_forma_pago_pedido.im_total_pago%type;
  vImptotalSoles   vta_forma_pago_pedido.im_total_pago%type;
  vImptotalVtaCredito vta_forma_pago_pedido.im_total_pago%type;

  vTipoPedido vta_pedido_vta_cab.tip_ped_vta%type;

  nEx_pedNC_DOBLE number;

  -- kmoncada 08.07.14
  vTipoComprobantePago VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE;
  
  --KMONCADA 21.11.2014 GENERACION DE NOTAS DE CREDITO
  vTipCompPagoAnula    VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE;
  vGrabaFP             CHAR(1);
  vMontoNC             VTA_FORMA_PAGO_PEDIDO.IM_PAGO%TYPE;
  vCantNotasCredito    INTEGER;
  
  v_dFecPedVtaOrigen VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE; --LTAVARA 22.05.2015
  vAhorroPedido number; --LTAVARA 22.05.2015
  v_dFecPedVtaNegativo VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE; --LTAVARA 22.05.2015
  vIndLocalM           CHAR(1);
  
    CURSOR curComps(cSecCompPago_in IN CHAR, nSecGrupoImpr_in IN NUMBER) IS
	WITH 
	COMP_PAGO_ORIGINAL AS (SELECT cp.COD_CLI_LOCAL, NOM_IMPR_COMP,
              DIREC_IMPR_COMP, NUM_DOC_IMPR, 			  
              VAL_DCTO_COMP_PAGO*-1 VAL_DCTO_COMP_PAGO,				
			  VAL_REDONDEO_COMP_PAGO*-1 VAL_REDONDEO_COMP_PAGO, 
			  PORC_IGV_COMP_PAGO,
              IND_AFECTA_KARDEX,
                        cp.COD_TIP_PROC_PAGO,
                       cp.COD_TIP_IDENT_RECEP_E,
                       (CASE
                         WHEN  cp.cod_tip_proc_pago ='1'    THEN
                          --NUM COMP PAGO ELECTRONICO CON EL FORMATO 0000-00000000
                               FARMA_UTILITY.GET_T_COMPROBANTE_2(cp.COD_TIP_PROC_PAGO,cp.NUM_COMP_PAGO_E,cp.NUM_COMP_PAGO)

                         ELSE--NUM COMP PAGO CONTINGENCIA CON EL FORMATO 0000-00000000
                                   TRIM(TO_CHAR(SUBSTR(cp.NUM_COMP_PAGO,1,3),'0000'))||'-'||
                                   TRIM(TO_CHAR(SUBSTR(cp.NUM_COMP_PAGO,4,7),'00000000'))
                         END) NUM_COMP_PAGO_EREF,
                        DECODE(cp.TIP_COMP_PAGO,
                                      '02','1',
                                      '06','1',
                                      '01','3',
                                      '05','3') COD_TIP_COMP_PAGO_EREF,                        
                        cp.COD_TIP_MONEDA,
                        cp.val_copago_e,-- 23/12/2014 ltavara
                        cp.val_copago_comp_pago,-- 23/12/2014 ltavara
                        cp.tip_clien_convenio,-- 23/12/2014 ltavara
                        cp.pct_beneficiario,  -- 23/12/2014 ltavara    
                        cp.pct_empresa-- 23/12/2014 ltavara
     from
      vta_comp_pago cp
     where
             cp.cod_grupo_cia = cCodGrupoCia_in
      and     cp.cod_local = cCodLocal_in
      and     cp.num_ped_vta = cNumVtaAnt_in
      and     cp.sec_comp_pago = cSecCompPago_in)
	select  
			CANT_ITEM,
			 VAL_BRUTO_COMP_PAGO*-1 VAL_BRUTO_COMP_PAGO,
              VAL_NETO_COMP_PAGO*-1 VAL_NETO_COMP_PAGO, 			  
              VAL_AFECTO_COMP_PAGO*-1 VAL_AFECTO_COMP_PAGO, 
			  VAL_IGV_COMP_PAGO*-1 VAL_IGV_COMP_PAGO,
              cp.VAL_TOTAL_E,
                        cp.TOTAL_GRAV_E,
                        cp.TOTAL_INAF_E,
                        cp.TOTAL_GRATU_E,
                        cp.TOTAL_EXON_E,
                        cp.TOTAL_DESC_E,
                        cp.TOTAL_IGV_E,
			cpo.COD_CLI_LOCAL,
			cpo.NOM_IMPR_COMP,
            cpo.DIREC_IMPR_COMP, 
			cpo.NUM_DOC_IMPR,
			cpo.VAL_DCTO_COMP_PAGO,
			cpo.VAL_REDONDEO_COMP_PAGO, 
			cpo.PORC_IGV_COMP_PAGO,
            cpo.IND_AFECTA_KARDEX,
            cpo.COD_TIP_PROC_PAGO,
            cpo.COD_TIP_IDENT_RECEP_E,
			cpo.NUM_COMP_PAGO_EREF,
			cpo.COD_TIP_COMP_PAGO_EREF,                        
            cpo.COD_TIP_MONEDA,
            cpo.val_copago_e,
            cpo.val_copago_comp_pago,
            cpo.tip_clien_convenio,
            cpo.pct_beneficiario, 
            cpo.pct_empresa
		FROM vta_comp_pago cp,
		     COMP_PAGO_ORIGINAL cpo
	    WHERE cp.cod_grupo_cia = cCodGrupoCia_in
      and     cp.cod_local = cCodLocal_in
      and     cp.num_ped_vta = cNumVtaAnt_in
      and     cp.sec_comp_pago = cSecCompPago_in
	  AND vIndLocalM = 'N'
	  UNION
	  SELECT SCI.CANT_ITEM,
			 SCI.VAL_BRUTO_COMP_PAGO,
             SCI.VAL_NETO_COMP_PAGO, 			  
             SCI.VAL_AFECTO_COMP_PAGO, 
			 SCI.VAL_IGV_COMP_PAGO,
             SCI.VAL_TOTAL_E,
             SCI.TOTAL_GRAV_E,
             SCI.TOTAL_INAF_E,
             SCI.TOTAL_GRATU_E,
             SCI.TOTAL_EXON_E,
             SCI.TOTAL_DESC_E,
             SCI.TOTAL_IGV_E,
			cpo.COD_CLI_LOCAL,
			cpo.NOM_IMPR_COMP,
            cpo.DIREC_IMPR_COMP, 
			cpo.NUM_DOC_IMPR,
			cpo.VAL_DCTO_COMP_PAGO,
			cpo.VAL_REDONDEO_COMP_PAGO, 
			cpo.PORC_IGV_COMP_PAGO,
            cpo.IND_AFECTA_KARDEX,
            cpo.COD_TIP_PROC_PAGO,
            cpo.COD_TIP_IDENT_RECEP_E,
			cpo.NUM_COMP_PAGO_EREF,
			cpo.COD_TIP_COMP_PAGO_EREF,                        
            cpo.COD_TIP_MONEDA,
            cpo.val_copago_e,
            cpo.val_copago_comp_pago,
            cpo.tip_clien_convenio,
            cpo.pct_beneficiario, 
            cpo.pct_empresa
	  FROM (
			  SELECT 
				COUNT(1) CANT_ITEM,
				SUM(VAL_PREC_VTA*CANT_ATENDIDA) VAL_BRUTO_COMP_PAGO,
				SUM(VAL_PREC_TOTAL) VAL_NETO_COMP_PAGO,
				SUM(VAL_PREC_TOTAL/(1+VAL_IGV/100)) VAL_AFECTO_COMP_PAGO,
				SUM(VAL_PREC_TOTAL-(VAL_PREC_TOTAL/(1+VAL_IGV/100))) VAL_IGV_COMP_PAGO,
				SUM(VAL_VTA_ITEM_E) VAL_TOTAL_E,
				SUM(VAL_VTA_ITEM_E) TOTAL_GRAV_E,
				0 TOTAL_INAF_E,
				0 TOTAL_GRATU_E,
				0 TOTAL_EXON_E,
				SUM(VAL_TOTAL_DESC_ITEM_E) TOTAL_DESC_E,
				SUM(VAL_TOTAL_IGV_ITEM_E) TOTAL_IGV_E
			  FROM PTOVENTA.VTA_PEDIDO_VTA_DET 
			  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
				AND COD_LOCAL = cCodLocal_in
				AND NUM_PED_VTA = cNumVta_in
				AND SEC_GRUPO_IMPR = nSecGrupoImpr_in ) SCI,
		     COMP_PAGO_ORIGINAL cpo
		WHERE 1=1
		AND vIndLocalM = 'S';
	  
	  v_nCantGrupoImpr INTEGER;
	  v_nValRedondeo    VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
  BEGIN
    vIndLocalM := FARMA_UTILITY.F_IS_LOCAL_TIPO_VTA_M(cCodGrupoCia_in, cCodLocal_in);
    BEGIN
    -- dubilluz 23.04.2014
    select case
           when count(1) > 0 then 'S'
           else 'N'
           end
    into   isConvenio
    from   vta_pedido_vta_cab c
    where  c.ind_ped_convenio = 'S'
    and    c.ind_conv_btl_mf = 'S'
    and    c.cod_convenio is not null
    and    c.cod_grupo_cia =cCodGrupoCia_in
    and    c.cod_local = cCodLocal_in
    and    c.num_ped_vta = cNumVtaAnt_in;
    -- dubilluz 23.04.2014


    SELECT COUNT(*) INTO v_nSec
    FROM VTA_PEDIDO_VTA_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumVta_in;

    --- OBTENER LOS DATOS QUE VAN HACER REFERENCIA AL PEDIDO
    --- PARA SABER SI ES CONVENIO O NO
    --- Y SI ES CONVENIO SABER SI SACARA DE LA EMPRESA O BENEFICIARIO
    SELECT CN.SEC_COMP_PAGO,CN.TIP_CLIEN_CONVENIO
    INTO   vSecCompPago_in,vTipClieConv_in
    FROM   VTA_PEDIDO_VTA_CAB CN
    WHERE  CN.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CN.COD_LOCAL = cCodLocal_in
    AND    CN.NUM_PED_VTA = cNumVta_in;
    -------------------


    -- recorre la cantidad de comprobantes
  OPEN curProd;
   LOOP
    FETCH curProd INTO v_rCurProd;
    EXIT WHEN curProd%NOTFOUND;

      -- no es un pedido convenio
      -- y solo debe generar para el sec comp de referencia
      if trim(vTipClieConv_in)  is null then
        if vSecCompPago_in = v_rCurProd.Sec_Comp_Pago then
            v_nSec:=v_nSec+1;
            --
            DBMS_OUTPUT.put_line('CAJ_AGREGAR_DET_NOTA_CREDITO : VTA_PEDIDO_VTA_DET');
            INSERT INTO VTA_PEDIDO_VTA_DET(COD_GRUPO_CIA,
                                            COD_LOCAL,
                                            NUM_PED_VTA,
                                            SEC_PED_VTA_DET,
                                            COD_PROD,
                                            CANT_ATENDIDA,
                                            VAL_PREC_VTA,
                                            VAL_PREC_TOTAL,
                                            PORC_DCTO_1,
                                            PORC_DCTO_2,
                                            PORC_DCTO_3,
                                            PORC_DCTO_TOTAL,
                                            EST_PED_VTA_DET,
                                            VAL_TOTAL_BONO,
                                            VAL_FRAC,
                                            SEC_COMP_PAGO,
                                            SEC_USU_LOCAL,
                                            USU_CREA_PED_VTA_DET,
                                            VAL_PREC_LISTA,
                                            VAL_IGV,
                                            UNID_VTA,
                                            IND_EXONERADO_IGV,
                                            SEC_GRUPO_IMPR,
                                            IND_ORIGEN_PROD,
                                            VAL_FRAC_LOCAL,
                                            CANT_FRAC_LOCAL,
                                            COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09
                                            PORC_ZAN,       -- 2009-11-09 JOLIVA
                                            COD_PROM,       --ASOSA, 21.05.2010
                                            COD_CAMP_CUPON,  --ASOSA, 21.05.2010
                                             --Agregado Por FRAMIREZ 12.06.2012
                                            AHORRO_CONV,
                                            NUM_COMP_PAGO,
                                            SEC_COMP_PAGO_BENEF,
                                            SEC_COMP_PAGO_EMPRE,
                                            sec_comp_pago_origen,TIP_CLIEN_CONVENIO
                                            /* INICIO LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                                            ,
                                            COD_TIP_AFEC_IGV_E,
                                            COD_TIP_PREC_VTA_E,
                                            VAL_PREC_VTA_UNIT_E,
                                            VAL_VTA_UNIT_ITEM_E,
                                            CANT_UNID_VDD_E,
                                            VAL_VTA_ITEM_E,
                                            VAL_TOTAL_IGV_ITEM_E,
                                            VAL_TOTAL_DESC_ITEM_E,
                                             /* FIN LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                                            AHORRO, --LTAVARA 05.11.2014
                                            IND_PROD_MAS_1,
                                            CTD_PUNTOS_ACUM,
                                            COD_PROD_PUNTOS,
                                            IND_BONIFICADO,
                                            FACTOR_PUNTOS,
                                            AHORRO_PUNTOS,
                                            AHORRO_CAMP,
                                            PTOS_AHORRO,
                                            AHORRO_PACK,
                                            PTOS_AHORRO_PACK,
                                            FECHA_VENCIMIENTO_LOTE,
                                            LOTE,
											NUM_LOTE_PROD,
											FEC_VENCIMIENTO_LOTE
                                            )
                VALUES(v_rCurProd.COD_GRUPO_CIA,
                      v_rCurProd.COD_LOCAL,
                      cNumVta_in,
                      v_nSec,
                      v_rCurProd.COD_PROD,
                      v_rCurProd.Cant_Atendida*-1,
                      -- v_rCurProd.VAL_PREC_VTA,
                      round(v_rCurProd.Val_Prec_Total/v_rCurProd.Cant_Atendida,3),
                      --nCantProd_in*-1*v_rCurProd.VAL_PREC_VTA,
                      v_rCurProd.Val_Prec_Total*-1,
                      v_rCurProd.PORC_DCTO_1,
                      v_rCurProd.PORC_DCTO_2,
                      v_rCurProd.PORC_DCTO_3,
                      v_rCurProd.PORC_DCTO_TOTAL,
                      v_rCurProd.EST_PED_VTA_DET,
                      (v_rCurProd.VAL_TOTAL_BONO*v_rCurProd.Cant_Atendida*-1)/v_rCurProd.CANT_ATENDIDA,
                      v_rCurProd.VAL_FRAC,
                      NULL,
                      v_rCurProd.SEC_USU_LOCAL,
                      vIdUsu_in,
                      v_rCurProd.VAL_PREC_LISTA,
                      v_rCurProd.VAL_IGV,
                      v_rCurProd.UNID_VTA,
                      v_rCurProd.IND_EXONERADO_IGV,
                      v_rCurProd.SEC_GRUPO_IMPR,
                      v_rCurProd.IND_ORIGEN_PROD,
                      v_rCurProd.VAL_FRAC_LOCAL, --ERIOS 05/06/2008
                      ( (v_rCurProd.Cant_Atendida*v_rCurProd.VAL_FRAC_LOCAL)/v_rCurProd.VAL_FRAC )*-1,
                      v_rCurProd.COD_GRUPO_REP,v_rCurProd.COD_GRUPO_REP_EDMUNDO, --JCORTEZ 19.10.09
                      v_rCurProd.PORC_ZAN,      -- 2009-11-09 JOLIVA
                      v_rCurProd.Cod_Prom,      --ASOSA, 21.05.2010
                      v_rCurProd.Cod_Camp_Cupon, --ASOSA, 21.05.2010
                       --Agregado Por FRAMIREZ 12.06.2012
                      v_rCurProd.AHORRO_CONV,
                      v_rCurProd.NUM_COMP_PAGO,
                      v_rCurProd.SEC_COMP_PAGO_BENEF,
                      v_rCurProd.SEC_COMP_PAGO_EMPRE,
                      vSecCompPago_in,vTipClieConv_in
                      /* INICIO LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                        ,
                     v_rCurProd.COD_TIP_AFEC_IGV_E,
                     v_rCurProd.COD_TIP_PREC_VTA_E,
                     v_rCurProd. VAL_PREC_VTA_UNIT_E,
                     v_rCurProd.VAL_VTA_UNIT_ITEM_E,
                     v_rCurProd.CANT_UNID_VDD_E,
                     v_rCurProd.VAL_VTA_ITEM_E,
                     v_rCurProd.VAL_TOTAL_IGV_ITEM_E,
                     v_rCurProd.VAL_TOTAL_DESC_ITEM_E,
                      /* FIN LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                      v_rCurProd.AHORRO,--LTAVARA 05.11.2014
                     v_rCurProd.IND_PROD_MAS_1,
                     v_rCurProd.CTD_PUNTOS_ACUM,
                     v_rCurProd.COD_PROD_PUNTOS,
                     v_rCurProd.IND_BONIFICADO,
                     v_rCurProd.FACTOR_PUNTOS,
                     v_rCurProd.AHORRO_PUNTOS,
                     v_rCurProd.AHORRO_CAMP,
                     v_rCurProd.PTOS_AHORRO,
                     v_rCurProd.AHORRO_PACK,
                     v_rCurProd.PTOS_AHORRO_PACK,
                     v_rCurProd.FECHA_VENCIMIENTO_LOTE,
                     v_rCurProd.LOTE,
					 v_rCurProd.NUM_LOTE_PROD,
					 v_rCurProd.FEC_VENCIMIENTO_LOTE
                  );
            end if;
      -- fin de generacion detalle si NO ES CONVENIO
      else
        if trim(vTipClieConv_in)  is not null then
        -- es un pedido convenio berificar si es cliente o beneficiario
        -- DOC_BENEFICIARIO char(1) := '1';
        -- DOC_EMPRESA char(1) := '2';
          if trim(vTipClieConv_in)  = DOC_BENEFICIARIO then
            if vSecCompPago_in = v_rCurProd.Sec_Comp_Pago_Benef then
                v_nSec:=v_nSec+1;
                DBMS_OUTPUT.put_line('CAJ_AGREGAR_DET_NOTA_CREDITO : VTA_PEDIDO_VTA_DET');
                INSERT INTO VTA_PEDIDO_VTA_DET(COD_GRUPO_CIA,
                                                COD_LOCAL,
                                                NUM_PED_VTA,
                                                SEC_PED_VTA_DET,
                                                COD_PROD,
                                                CANT_ATENDIDA,
                                                VAL_PREC_VTA,
                                                VAL_PREC_TOTAL,
                                                PORC_DCTO_1,
                                                PORC_DCTO_2,
                                                PORC_DCTO_3,
                                                PORC_DCTO_TOTAL,
                                                EST_PED_VTA_DET,
                                                VAL_TOTAL_BONO,
                                                VAL_FRAC,
                                                SEC_COMP_PAGO,
                                                SEC_USU_LOCAL,
                                                USU_CREA_PED_VTA_DET,
                                                VAL_PREC_LISTA,
                                                VAL_IGV,
                                                UNID_VTA,
                                                IND_EXONERADO_IGV,
                                                SEC_GRUPO_IMPR,
                                                IND_ORIGEN_PROD,
                                                VAL_FRAC_LOCAL,
                                                CANT_FRAC_LOCAL,
                                                COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09
                                                PORC_ZAN,       -- 2009-11-09 JOLIVA
                                                COD_PROM,       --ASOSA, 21.05.2010
                                                COD_CAMP_CUPON,  --ASOSA, 21.05.2010
                                                 --Agregado Por FRAMIREZ 12.06.2012
                                                AHORRO_CONV,
                                                NUM_COMP_PAGO,
                                                SEC_COMP_PAGO_BENEF,
                                                SEC_COMP_PAGO_EMPRE,
                                                sec_comp_pago_origen,TIP_CLIEN_CONVENIO
                                             /* INICIO LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                                                ,
                                                COD_TIP_AFEC_IGV_E,
                                                COD_TIP_PREC_VTA_E,
                                                VAL_PREC_VTA_UNIT_E,
                                                VAL_VTA_UNIT_ITEM_E,
                                                CANT_UNID_VDD_E,
                                                VAL_VTA_ITEM_E,
                                                VAL_TOTAL_IGV_ITEM_E,
                                                VAL_TOTAL_DESC_ITEM_E,
                                             /* FIN LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                                                AHORRO,--LTAVARA 05.11.2014
                                                IND_PROD_MAS_1,
                                                CTD_PUNTOS_ACUM,
                                                COD_PROD_PUNTOS,
                                                IND_BONIFICADO,
                                                FACTOR_PUNTOS,
                                                AHORRO_PUNTOS,
                                                AHORRO_CAMP,
                                                PTOS_AHORRO,
                                                AHORRO_PACK,
                                                PTOS_AHORRO_PACK,
                                                FECHA_VENCIMIENTO_LOTE,
                                                LOTE,
												NUM_LOTE_PROD,
												FEC_VENCIMIENTO_LOTE
                                                )
                VALUES(v_rCurProd.COD_GRUPO_CIA,
                      v_rCurProd.COD_LOCAL,
                      cNumVta_in,
                      v_nSec,
                      v_rCurProd.COD_PROD,
                      v_rCurProd.Cant_Atendida*-1,
                      -- v_rCurProd.VAL_PREC_VTA,
                      round(v_rCurProd.Val_Prec_Total_Benef/v_rCurProd.Cant_Atendida,3),
                      --nCantProd_in*-1*v_rCurProd.VAL_PREC_VTA,
                      v_rCurProd.Val_Prec_Total_Benef*-1,
                      v_rCurProd.PORC_DCTO_1,
                      v_rCurProd.PORC_DCTO_2,
                      v_rCurProd.PORC_DCTO_3,
                      v_rCurProd.PORC_DCTO_TOTAL,
                      v_rCurProd.EST_PED_VTA_DET,
                      (v_rCurProd.VAL_TOTAL_BONO*v_rCurProd.Cant_Atendida*-1)/v_rCurProd.CANT_ATENDIDA,
                      v_rCurProd.VAL_FRAC,
                      NULL,
                      v_rCurProd.SEC_USU_LOCAL,
                      vIdUsu_in,
                      v_rCurProd.VAL_PREC_LISTA,
                      v_rCurProd.VAL_IGV,
                      v_rCurProd.UNID_VTA,
                      v_rCurProd.IND_EXONERADO_IGV,
                      v_rCurProd.SEC_GRUPO_IMPR,
                      v_rCurProd.IND_ORIGEN_PROD,
                      v_rCurProd.VAL_FRAC_LOCAL, --ERIOS 05/06/2008
                      ( (v_rCurProd.Cant_Atendida*v_rCurProd.VAL_FRAC_LOCAL)/v_rCurProd.VAL_FRAC )*-1,
                      v_rCurProd.COD_GRUPO_REP,v_rCurProd.COD_GRUPO_REP_EDMUNDO, --JCORTEZ 19.10.09
                      v_rCurProd.PORC_ZAN,      -- 2009-11-09 JOLIVA
                      v_rCurProd.Cod_Prom,      --ASOSA, 21.05.2010
                      v_rCurProd.Cod_Camp_Cupon, --ASOSA, 21.05.2010
                       --Agregado Por FRAMIREZ 12.06.2012
                      v_rCurProd.AHORRO_CONV,
                      v_rCurProd.NUM_COMP_PAGO,
                      v_rCurProd.SEC_COMP_PAGO_BENEF,
                      v_rCurProd.SEC_COMP_PAGO_EMPRE,
                      vSecCompPago_in,vTipClieConv_in
                      /* INICIO LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                        ,
                     v_rCurProd.COD_TIP_AFEC_IGV_E,
                     v_rCurProd.COD_TIP_PREC_VTA_E,
                     v_rCurProd. VAL_PREC_VTA_UNIT_E,
                     v_rCurProd.VAL_VTA_UNIT_ITEM_E,
                     v_rCurProd.CANT_UNID_VDD_E,
                     v_rCurProd.VAL_VTA_ITEM_E,
                     v_rCurProd.VAL_TOTAL_IGV_ITEM_E,
                     v_rCurProd.VAL_TOTAL_DESC_ITEM_E,
                      /* FIN LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                      v_rCurProd.AHORRO,--LTAVARA 05.11.2014
                     v_rCurProd.IND_PROD_MAS_1,
                     v_rCurProd.CTD_PUNTOS_ACUM,
                     v_rCurProd.COD_PROD_PUNTOS,
                     v_rCurProd.IND_BONIFICADO,
                     v_rCurProd.FACTOR_PUNTOS,
                     v_rCurProd.AHORRO_PUNTOS,
                     v_rCurProd.AHORRO_CAMP,
                     v_rCurProd.PTOS_AHORRO,
                     v_rCurProd.AHORRO_PACK,
                     v_rCurProd.PTOS_AHORRO_PACK,
                     v_rCurProd.FECHA_VENCIMIENTO_LOTE,
                     v_rCurProd.LOTE,
					 v_rCurProd.NUM_LOTE_PROD,
					 v_rCurProd.FEC_VENCIMIENTO_LOTE
                      );
              end if;
          else
            --es de empresa
             if vSecCompPago_in = v_rCurProd.Sec_Comp_Pago_Empre then
                v_nSec:=v_nSec+1;
                DBMS_OUTPUT.put_line('CAJ_AGREGAR_DET_NOTA_CREDITO : VTA_PEDIDO_VTA_DET');
                INSERT INTO VTA_PEDIDO_VTA_DET(COD_GRUPO_CIA,
                                                COD_LOCAL,
                                                NUM_PED_VTA,
                                                SEC_PED_VTA_DET,
                                                COD_PROD,
                                                CANT_ATENDIDA,
                                                VAL_PREC_VTA,
                                                VAL_PREC_TOTAL,
                                                PORC_DCTO_1,
                                                PORC_DCTO_2,
                                                PORC_DCTO_3,
                                                PORC_DCTO_TOTAL,
                                                EST_PED_VTA_DET,
                                                VAL_TOTAL_BONO,
                                                VAL_FRAC,
                                                SEC_COMP_PAGO,
                                                SEC_USU_LOCAL,
                                                USU_CREA_PED_VTA_DET,
                                                VAL_PREC_LISTA,
                                                VAL_IGV,
                                                UNID_VTA,
                                                IND_EXONERADO_IGV,
                                                SEC_GRUPO_IMPR,
                                                IND_ORIGEN_PROD,
                                                VAL_FRAC_LOCAL,
                                                CANT_FRAC_LOCAL,
                                                COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09
                                                PORC_ZAN,       -- 2009-11-09 JOLIVA
                                                COD_PROM,       --ASOSA, 21.05.2010
                                                COD_CAMP_CUPON,  --ASOSA, 21.05.2010
                                                 --Agregado Por FRAMIREZ 12.06.2012
                                                AHORRO_CONV,
                                                NUM_COMP_PAGO,
                                                SEC_COMP_PAGO_BENEF,
                                                SEC_COMP_PAGO_EMPRE,
                                               sec_comp_pago_origen,TIP_CLIEN_CONVENIO
                                               /* INICIO LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                                                ,
                                                COD_TIP_AFEC_IGV_E,
                                                COD_TIP_PREC_VTA_E,
                                                VAL_PREC_VTA_UNIT_E,
                                                VAL_VTA_UNIT_ITEM_E,
                                                CANT_UNID_VDD_E,
                                                VAL_VTA_ITEM_E,
                                                VAL_TOTAL_IGV_ITEM_E,
                                                VAL_TOTAL_DESC_ITEM_E,
                                             /* FIN LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                                                AHORRO,--LTAVARA 05.11.2014
                                                IND_PROD_MAS_1,
                                                CTD_PUNTOS_ACUM,
                                                COD_PROD_PUNTOS,
                                                IND_BONIFICADO,
                                                FACTOR_PUNTOS,
                                                AHORRO_PUNTOS,
                                                AHORRO_CAMP,
                                                PTOS_AHORRO,
                                                AHORRO_PACK,
                                                PTOS_AHORRO_PACK,
                                                FECHA_VENCIMIENTO_LOTE,
                                                LOTE,
												NUM_LOTE_PROD,
												FEC_VENCIMIENTO_LOTE
                                                )
                VALUES(v_rCurProd.COD_GRUPO_CIA,
                      v_rCurProd.COD_LOCAL,
                      cNumVta_in,
                      v_nSec,
                      v_rCurProd.COD_PROD,
                      v_rCurProd.Cant_Atendida*-1,
                      -- v_rCurProd.VAL_PREC_VTA,
                      round(v_rCurProd.Val_Prec_Total_Empre/v_rCurProd.Cant_Atendida,3),
                      --nCantProd_in*-1*v_rCurProd.VAL_PREC_VTA,
                      v_rCurProd.Val_Prec_Total_Empre*-1,
                      v_rCurProd.PORC_DCTO_1,
                      v_rCurProd.PORC_DCTO_2,
                      v_rCurProd.PORC_DCTO_3,
                      v_rCurProd.PORC_DCTO_TOTAL,
                      v_rCurProd.EST_PED_VTA_DET,
                      (v_rCurProd.VAL_TOTAL_BONO*v_rCurProd.Cant_Atendida*-1)/v_rCurProd.CANT_ATENDIDA,
                      v_rCurProd.VAL_FRAC,
                      NULL,
                      v_rCurProd.SEC_USU_LOCAL,
                      vIdUsu_in,
                      v_rCurProd.VAL_PREC_LISTA,
                      v_rCurProd.VAL_IGV,
                      v_rCurProd.UNID_VTA,
                      v_rCurProd.IND_EXONERADO_IGV,
                      v_rCurProd.SEC_GRUPO_IMPR,
                      v_rCurProd.IND_ORIGEN_PROD,
                      v_rCurProd.VAL_FRAC_LOCAL, --ERIOS 05/06/2008
                      ( (v_rCurProd.Cant_Atendida*v_rCurProd.VAL_FRAC_LOCAL)/v_rCurProd.VAL_FRAC )*-1,
                      v_rCurProd.COD_GRUPO_REP,v_rCurProd.COD_GRUPO_REP_EDMUNDO, --JCORTEZ 19.10.09
                      v_rCurProd.PORC_ZAN,      -- 2009-11-09 JOLIVA
                      v_rCurProd.Cod_Prom,      --ASOSA, 21.05.2010
                      v_rCurProd.Cod_Camp_Cupon, --ASOSA, 21.05.2010
                       --Agregado Por FRAMIREZ 12.06.2012
                      v_rCurProd.AHORRO_CONV,
                      v_rCurProd.NUM_COMP_PAGO,
                      v_rCurProd.SEC_COMP_PAGO_BENEF,
                      v_rCurProd.SEC_COMP_PAGO_EMPRE,
                      vSecCompPago_in,vTipClieConv_in
                         /* INICIO LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                        ,
                     v_rCurProd.COD_TIP_AFEC_IGV_E,
                     v_rCurProd.COD_TIP_PREC_VTA_E,
                     v_rCurProd. VAL_PREC_VTA_UNIT_E,
                     v_rCurProd.VAL_VTA_UNIT_ITEM_E,
                     v_rCurProd.CANT_UNID_VDD_E,
                     v_rCurProd.VAL_VTA_ITEM_E,
                     v_rCurProd.VAL_TOTAL_IGV_ITEM_E,
                     v_rCurProd.VAL_TOTAL_DESC_ITEM_E,
                      /* FIN LTAVARA SE AGREGO PARA COMPROBANTE ELECTRONICO 30-07-2014   */
                     v_rCurProd.AHORRO,--LTAVARA 05.11.2014
                     v_rCurProd.IND_PROD_MAS_1,
                     v_rCurProd.CTD_PUNTOS_ACUM,
                     v_rCurProd.COD_PROD_PUNTOS,
                     v_rCurProd.IND_BONIFICADO,
                     v_rCurProd.FACTOR_PUNTOS,
                     v_rCurProd.AHORRO_PUNTOS,
                     v_rCurProd.AHORRO_CAMP,
                     v_rCurProd.PTOS_AHORRO,
                     v_rCurProd.AHORRO_PACK,
                     v_rCurProd.PTOS_AHORRO_PACK,
                     v_rCurProd.FECHA_VENCIMIENTO_LOTE,
                     v_rCurProd.LOTE,
					 v_rCurProd.NUM_LOTE_PROD,
					 v_rCurProd.FEC_VENCIMIENTO_LOTE
                      );
              end if;
          end if;
        end if;
      end if;
    end loop;


    SELECT  A.TIP_COMP_PAGO
    INTO    vTipoComprobantePago
    FROM    VTA_PEDIDO_VTA_CAB A
    WHERE   A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     A.COD_LOCAL = cCodLocal_in
    AND     A.NUM_PED_VTA = cNumVta_in;

    -- KMONCADA 09.07.14 NO GENERA NC DE GUIAS
    IF vTipoComprobantePago <> '03' THEN
	
	v_nCantGrupoImpr := 1;
	--ERIOS 15.02.2016 Agrupa notas de credito
	IF vIndLocalM = 'S' THEN
	  UPDATE VTA_PEDIDO_VTA_DET
	  SET SEC_GRUPO_IMPR = CEIL(SEC_PED_VTA_DET/10) --Cambiar por variable
	  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND  COD_LOCAL = cCodLocal_in
            AND  NUM_PED_VTA = cNumVta_in;
	  SELECT MAX(SEC_GRUPO_IMPR)
	    INTO v_nCantGrupoImpr
	  FROM VTA_PEDIDO_VTA_DET
	  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND  COD_LOCAL = cCodLocal_in
            AND  NUM_PED_VTA = cNumVta_in;
	END IF;

	SELECT SEC_MOV_CAJA INTO v_cSecMovCaja_in
      FROM VTA_CAJA_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_CAJA_PAGO = nNumCajaPago_in;
			
	FOR regGrupoImp IN 1.. v_nCantGrupoImpr
	LOOP
	FOR regComp in curComps(vSecCompPago_in, regGrupoImp)
	LOOP
      -- ahora genera el comprobante de pago de NC
      -- debe ser el mismo monto el mismo precio pero en NEGATIVO
      -- GENERA COMPROBANTE DE NOTA DE CREDITO
      
      v_nSecCompPago := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, vCodNumera_SecCompPAgo_in);
     -- ERIOS 11.05.2016 Se recupera la serie auxiliar
	 v_nNumCompPago := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nSecCompPago, 7, 0, 'I');
	 v_nNumCompPago := F_GET_SERIE_AUX_LOCAL(cCodGrupoCia_in, cCodLocal_in)||TRIM(v_nNumCompPago);
      v_nSecCompPago := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nSecCompPago, 10, 0, 'I');


      -- genera el comprobante de nota de credito
        IF cFlagElectronico='0'  THEN -- LTAVARA 03.09.2014 20:20 SI NO ESTA ACTIVO EL FLAG OBTENER NUM COMP PAGO CONTINGENCIA
          SELECT IMPR_LOCAL.NUM_SERIE_LOCAL||''||IMPR_LOCAL.NUM_COMP
          into   vNumCompPago_in
          FROM   VTA_IMPR_LOCAL IMPR_LOCAL
          WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
          AND     IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
          AND     IMPR_LOCAL.Tip_Comp = '04' FOR UPDATE;

        ELSE -- LTAVARA , SI ESTA ACTIVO EL NUM_COMP_PAGO ES EL SEC_COMP_PAGO
           -- vNumCompPago_in := v_nSecCompPago;
            vNumCompPago_in := v_nNumCompPago;--LTAVARA 01.10.2014
        END IF;

		IF(regGrupoImp <> 1) THEN
			v_nValRedondeo := 0.00;
		ELSE
		    v_nValRedondeo := regComp.VAL_REDONDEO_COMP_PAGO;
		END IF;
			
        INSERT INTO VTA_COMP_PAGO(COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SEC_COMP_PAGO,
                      TIP_COMP_PAGO, NUM_COMP_PAGO,
                      SEC_MOV_CAJA, CANT_ITEM, COD_CLI_LOCAL, NOM_IMPR_COMP,
                      DIREC_IMPR_COMP, NUM_DOC_IMPR, VAL_BRUTO_COMP_PAGO,
                      VAL_NETO_COMP_PAGO, VAL_DCTO_COMP_PAGO,
                      VAL_AFECTO_COMP_PAGO, VAL_IGV_COMP_PAGO,
                      VAL_REDONDEO_COMP_PAGO, USU_CREA_COMP_PAGO, PORC_IGV_COMP_PAGO,
                      IND_AFECTA_KARDEX,
                        COD_TIP_PROC_PAGO,
                        COD_TIP_IDENT_RECEP_E,
                        NUM_COMP_PAGO_EREF,
                        COD_TIP_COMP_PAGO_EREF,
                        COD_TIP_MOTIVO_NOTA_E,
                        VAL_TOTAL_E,
                        TOTAL_GRAV_E,
                        TOTAL_INAF_E,
                        TOTAL_GRATU_E,
                        TOTAL_EXON_E,
                        TOTAL_DESC_E,
                        TOTAL_IGV_E,
                        COD_TIP_MONEDA,
                        VAL_COPAGO_E,-- 23/12/2014 ltavara
                        VAL_COPAGO_COMP_PAGO,-- 23/12/2014 ltavara
                        TIP_CLIEN_CONVENIO,-- 23/12/2014 ltavara
                        PCT_BENEFICIARIO,  -- 23/12/2014 ltavara    
                        PCT_EMPRESA-- 23/12/2014 ltavara                         
                      )
      VALUES(cCodGrupoCia_in, cCodLocal_in, cNumVta_in, v_nSecCompPago,
              '04',vNumCompPago_in,v_cSecMovCaja_in,
			  regComp.CANT_ITEM, 
			  regComp.COD_CLI_LOCAL, 
			  regComp.NOM_IMPR_COMP,
              regComp.DIREC_IMPR_COMP, 
			  regComp.NUM_DOC_IMPR, 
			  regComp.VAL_BRUTO_COMP_PAGO,
              regComp.VAL_NETO_COMP_PAGO, 
			  regComp.VAL_DCTO_COMP_PAGO,
              regComp.VAL_AFECTO_COMP_PAGO, 
			  regComp.VAL_IGV_COMP_PAGO,
              v_nValRedondeo, 
			  vIdUsu_in, 
			  regComp.PORC_IGV_COMP_PAGO,
              regComp.IND_AFECTA_KARDEX,
              regComp.COD_TIP_PROC_PAGO,
              regComp.COD_TIP_IDENT_RECEP_E,
              regComp.NUM_COMP_PAGO_EREF,
              regComp.COD_TIP_COMP_PAGO_EREF,
                       cCodTipMotivoNotaE,--TIPO DE NC POR DEVOLUCION TOTAL
              regComp.VAL_TOTAL_E,
              regComp.TOTAL_GRAV_E,
              regComp.TOTAL_INAF_E,
              regComp.TOTAL_GRATU_E,
              regComp.TOTAL_EXON_E,
              regComp.TOTAL_DESC_E,
              regComp.TOTAL_IGV_E,
              regComp.COD_TIP_MONEDA,
              regComp.val_copago_e,-- 23/12/2014 ltavara
              regComp.val_copago_comp_pago,-- 23/12/2014 ltavara
              regComp.tip_clien_convenio,-- 23/12/2014 ltavara
              regComp.pct_beneficiario,  -- 23/12/2014 ltavara    
              regComp.pct_empresa-- 23/12/2014 ltavara
      );

               /* *********************** FIN ******************************************* */

      IF cFlagElectronico='0'  THEN --LTAVARA 04.09.2014 10:07 VALIDAR FLAG ELECTRONICO
        UPDATE VTA_IMPR_LOCAL
        SET    FEC_MOD_IMPR_LOCAL = SYSDATE,
               USU_MOD_IMPR_LOCAL = vIdUsu_in,
               NUM_COMP           = TRIM(TO_CHAR((TO_NUMBER(NUM_COMP) + 1),'0000000'))
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND   COD_LOCAL = cCodLocal_in
        AND   Tip_Comp = '04';

      END IF;

      UPDATE VTA_PEDIDO_VTA_DET
             SET USU_MOD_PED_VTA_DET = vIdUsu_in,
                 FEC_MOD_PED_VTA_DET = SYSDATE,
                 SEC_COMP_PAGO = v_nSecCompPago
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND  COD_LOCAL = cCodLocal_in
            AND  NUM_PED_VTA = cNumVta_in
			AND SEC_GRUPO_IMPR = regGrupoImp;

      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, vCodNumera_SecCompPAgo_in, vIdUsu_in);
      V_NUM_COMP_PAGO_NC := V_NUM_COMP_PAGO_NC||vNumCompPago_in||'@';
    END LOOP;
	END LOOP;
	ELSE
      V_NUM_COMP_PAGO_NC := 'G';
      -- KMONCADA 09.07.14 ANULA LA GUIA DE REMISION
      SELECT SEC_MOV_CAJA
      INTO   v_cSecMovCaja_in
      FROM   VTA_CAJA_PAGO
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL = cCodLocal_in
      AND    NUM_CAJA_PAGO = nNumCajaPago_in;
      
      UPDATE VTA_COMP_PAGO
      SET    IND_COMP_ANUL = 'S',
             FEC_ANUL_COMP_PAGO = SYSDATE,
             NUM_PEDIDO_ANUL   = cNumVta_in,
             SEC_MOV_CAJA_ANUL = v_cSecMovCaja_in
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL = cCodLocal_in
      AND    NUM_PED_VTA = cNumVtaAnt_in
      AND    SEC_COMP_PAGO = vSecCompPago_in;
    END IF;

      -- actualiza kardex x cada comprobante
      -- SOLO SI EL COMPROBANTE ORIGINAL FUE EL QUE MOVIO KARDEX
      select  nvl(IND_AFECTA_KARDEX,'S')
      into    vIndAfectaKardex_in
      from    vta_comp_pago cp
      where   cp.cod_grupo_cia = cCodGrupoCia_in
      and     cp.cod_local = cCodLocal_in
      and     cp.num_ped_vta = cNumVtaAnt_in
      and     cp.sec_comp_pago = vSecCompPago_in;

      if vIndAfectaKardex_in = 'S' then
        CAJ_ACTUALIZA_STK_PROD_DETALLE(cCodGrupoCia_in, cCodLocal_in, cNumVta_in,cNumVtaAnt_in,
                           '106',--cCodMotKardex_in,
                           '01',--cTipDocKardex_in,
                           '016',--cCodNumeraKardex_in,
                           vIdUsu_in);

           --INI ASOSA - 13/10/2014 - PANHD
          CAJ_ACTUALIZA_STK_PROD_COMP(cCodGrupoCia_in,
                                                                                   cCodLocal_in, cNumVta_in,
                                                                                   cNumVtaAnt_in,
                                                                                   '01',--cTipDocKardex_in,
                                                                                   '016',--cCodNumeraKardex_in,
                                                                                   vIdUsu_in);
          --FIN ASOSA - 13/10/2014 - PANHD

      end if;


        -- agrega forma de pago de PEDIDO
       select  ca.val_neto_ped_vta
       into    vNetoPedido_in
       from    vta_pedido_vta_cab ca
       where   ca.cod_grupo_cia = cCodGrupoCia_in
       and     ca.cod_local = cCodLocal_in
       and     ca.num_ped_vta = cNumVta_in;
       
       -- KMONCADA 21.11.2014 
       /*
       LA ANULACION DE UN PEDIDO CON MAS DE UN DOCUMENTO VERIFICARA 
       QUE LAS FORMAS DE PAGO NEGATIVAS NO SE MUESTREN LA GUIA EN 
       CASO DE EXISTIR.
       */
       -- OBTIENE EL TIPO DE COMPROBANTE DE LA ANULACION GRL: 03 OTROS CASOS:04
       SELECT CAB.TIP_COMP_PAGO
       INTO   vTipCompPagoAnula
       FROM VTA_PEDIDO_VTA_CAB CAB
       WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND   CAB.COD_LOCAL     = cCodLocal_in
       AND   CAB.NUM_PED_VTA   = cNumVta_in;
       
       
       /******/
       --
       SELECT COUNT(1)
       INTO   vCantNotasCredito
       FROM VTA_PEDIDO_VTA_CAB CAB
       WHERE CAB.COD_GRUPO_CIA      = cCodGrupoCia_in
       AND   CAB.COD_LOCAL          = cCodLocal_in
       AND   CAB.NUM_PED_VTA_ORIGEN = cNumVtaAnt_in
       AND   CAB.TIP_COMP_PAGO      = '04';
       
       -- INDICADOR DE REGISTRO DE FORMAS DE PAGO EN NOTA DE CREDITO
       vGrabaFP := '0';
       
       -- NO REALIZARA VERIFICACION EN CASO DE ANULACION DE GUIA DE REMISION
       -- GRABARA PARA GRL FP VALOR 0 (CERO)
       IF vTipCompPagoAnula != '03' OR vCantNotasCredito=0 THEN 
         -- VERIFICARA SI YA SE REGISTRARON FORMAS DE PAGO NEGATIVAS
         SELECT NVL(SUM(FP.IM_PAGO),0)
         INTO   vMontoNC
         FROM VTA_PEDIDO_VTA_CAB CAB,
              VTA_PEDIDO_VTA_CAB CABA,
              VTA_FORMA_PAGO_PEDIDO FP
         WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
         AND   CAB.COD_LOCAL     = cCodLocal_in
         AND   CAB.NUM_PED_VTA = cNumVtaAnt_in
         AND   CAB.COD_GRUPO_CIA = CABA.COD_GRUPO_CIA
         AND   CAB.COD_LOCAL     = CABA.COD_LOCAL
         AND   CAB.NUM_PED_VTA   = CABA.NUM_PED_VTA_ORIGEN
         AND   CABA.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
         AND   CABA.COD_LOCAL     = FP.COD_LOCAL
         AND   CABA.NUM_PED_VTA   = FP.NUM_PED_VTA;
         
         -- EN CASO DE EXISTIR FP NEGATIVAS HABILITARA EL INDICADOR PARA GRABARLAS
         IF vMontoNC = 0 THEN
           vGrabaFP := '1';
         END IF;
       END IF;
       
--       if nSecDetPed_in = 0  then
       IF vGrabaFP = '1' THEN
         CAJ_ANULAR_FORMA_PAGO(cCodGrupoCia_in,
                             cCodLocal_in,
                             cNumVtaAnt_in,--cNumPedVta_in,
                             '%',--cTipComp_in,
                             '%',--cNumComp_in,
                             cNumVta_in,--cNumPedNeg_in,
                             vIdUsu_in);
       ELSE
         IF isConvenio = 'S' THEN
           INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,COD_LOCAL,
                COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,
                VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,
                USU_CREA_FORMA_PAGO_PED  )
                VALUES(cCodGrupoCia_in,cCodLocal_in,
                '00080',cNumVta_in,0,'01',--SOLES
                0,0,0,vIdUsu_in);
         END IF;
       END IF;

      --SUMAR CABECERA
      
      UPDATE VTA_PEDIDO_VTA_CAB t
         SET USU_MOD_PED_VTA_CAB = vIdUsu_in,
             FEC_MOD_PED_VTA_CAB = SYSDATE,
             CANT_ITEMS_PED_VTA  = v_nSec,
             --t.est_ped_vta = 'C' -- 15.09.2014
             -- t.est_ped_vta = 'S' --LTAVARA 15.09.2014
             -- KMONCADA 04.11.2014 EN CASO DE SER NOTA CREDITO NO ELECTRONICA GRABA ESTADO "C"

             -- KMONCADA 21.11.2014 LAS ANULACIONES DE GUIAS 
             -- SE GRABARAN COMO COBRADAS YA QUE NO SE VAN A IMPRIMIR
             -- T.EST_PED_VTA = DECODE(TIP_COMP_PAGO,'03','C', DECODE(cFlagElectronico,'1','S','C'))
             -- KMONCADA 10.02.2016 PARA EL CASO DE LOCAL M SE IMPRIMIRA NOTAS DE CREDITO
             T.EST_PED_VTA = CASE
                               WHEN T.TIP_COMP_PAGO = '03' THEN 'C'
                               WHEN cFlagElectronico = '1' OR vIndLocalM  = 'S' THEN 'S'
                               ELSE 'C'
                             END
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND NUM_PED_VTA = cNumVta_in;

    CLOSE curProd;
    -- KMONCADA 10.02.2016 SE MARCARA LA PROFORMA COMO ANULADA
    IF vIndLocalM = 'S' THEN
      UPDATE TMP_VTA_PEDIDO_VTA_CAB C
      SET C.USU_MOD_PED_VTA_CAB = vIdUsu_in,
          C.FEC_MOD_PED_VTA_CAB = SYSDATE,
          C.EST_PED_VTA = 'N'
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND C.COD_LOCAL = cCodLocal_in
      AND C.NUM_PED_VTA_ORIGEN = cNumVtaAnt_in;
    END IF;
    -- KMONCADA 16.12.2014 VERIFICAR LOS SEC_COMP_PAGO_ORIGEN DE LAS NOTAS DE CREDITO
    VERIFICAR_DET_NC(cCodGrupoCia_in, cCodLocal_in, cNumVta_in);

    --***INICIO ***LTAVARA - 22.05.2015 INSERTAR EL PEDIDO DE NC EN EL FID_TARJETA_PEDIDO
      --Inserta en la tabla de ahorro x DNI para validar el maximo Ahorro en el dia o Semana
        --dubilluz 28.05.2009 
        SELECT cab.fec_ped_vta into v_dFecPedVtaOrigen
        FROM  VTA_PEDIDO_VTA_CAB cab
        WHERE cab.cod_grupo_cia = cCodGrupoCia_in
        AND   cab.cod_local     = cCodLocal_in
        AND   CAB.NUM_PED_VTA   = cNumVtaAnt_in;
        
         --LTAVARA 22.05.2015 Obtiene la fecha de la NC
        SELECT cab.fec_ped_vta into v_dFecPedVtaNegativo
        FROM  VTA_PEDIDO_VTA_CAB cab
        WHERE cab.cod_grupo_cia = cCodGrupoCia_in
        AND   cab.cod_local     = cCodLocal_in
        AND   CAB.NUM_PED_VTA   = cNumVta_in;
   
        
          if trunc(v_dFecPedVtaOrigen) = trunc(sysdate) then
			     -- KMONCADA 16.04.2015 SE CONSIDERA SOLO LAS CAMPAÑAS QUE SUMEN EN AHORRO DE FIDELIZADO
            --select NVL(SUM(d.AHORRO),0)-- SOLO POR LAS PROMOCIONES NO INCLUYE LO REDIMIDO
            select NVL(SUM(D.AHORRO_CAMP),0)
            into   vAhorroPedido
            from   vta_pedido_vta_det d,
                   vta_campana_cupon cup
            where  cup.cod_grupo_cia = d.cod_grupo_cia
            and    cup.cod_camp_cupon = d.cod_camp_cupon
            and d.cod_grupo_cia = cCodGrupoCia_in
            and    d.cod_local = cCodLocal_in
            -- KMONCADA 21.04.2016 SOLO SUMA EL AHORRO DE LA NC
            --and    d.num_ped_vta = cNumVtaAnt_in
            AND D.NUM_PED_VTA = cNumVta_in
            and NVL(cup.flg_acumula_ahorro_dni,'S') = 'S';

             if vAhorroPedido > 0 then
               insert into vta_ped_dcto_cli_aux
              (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,VAL_DCTO_VTA,DNI_CLIENTE,FEC_CREA_PED_VTA_CAB)
              --select c.cod_grupo_cia,c.cod_local,cNumVta_in,sum(d.ahorro)*-1,t.dni_cli,v_dFecPedVtaNegativo
              select c.cod_grupo_cia,c.cod_local,cNumVta_in,sum(NVL(d.AHORRO_CAMP,0)),t.dni_cli,v_dFecPedVtaNegativo
              from   vta_pedido_vta_det d,
                     vta_pedido_vta_cab c,
                     fid_tarjeta_pedido t,
                     vta_campana_cupon cup -- KMONCADA 16.04.2015 SE CONSIDERA SOLO LAS CAMPAÑAS QUE SUMEN EN AHORRO DE FIDELIZADO
              where  c.cod_grupo_cia = cCodGrupoCia_in
              and    c.cod_local = cCodLocal_in
              -- KMONCADA 21.04.2016 SOLO SUMA EL AHORRO DE LA NC
              --and    c.num_ped_vta =  cNumVtaAnt_in
              and    c.num_ped_vta =  cNumVta_in
              and    c.cod_grupo_cia = d.cod_grupo_cia
              and    c.cod_local = d.cod_local
              and    c.num_ped_vta = d.num_ped_vta
              and    c.cod_grupo_cia = t.cod_grupo_cia
              and    c.cod_local = t.cod_local
              and    c.num_ped_vta = t.num_pedido
              AND    cup.cod_grupo_cia = d.cod_grupo_cia
              and    cup.cod_camp_cupon = d.cod_camp_cupon
              and NVL(cup.flg_acumula_ahorro_dni,'S') = 'S'
              group by c.cod_grupo_cia,c.cod_local,cNumVta_in,t.dni_cli,v_dFecPedVtaNegativo;

              ---- 2012.10.15 CAMBIO PARA GUARDAR EL DSCTO ANULADO
              end if;
            end if;
            -- Registra el pedido NC solo fidelizado
            insert into fid_tarjeta_pedido
            (
            cod_grupo_cia,
            cod_local,
            num_pedido,
            dni_cli,
            cant_dcto,
            usu_crea_tarjeta_pedido,
            fec_crea_tarjeta_pedido,
            cod_tarjeta
            )
            select
            c.cod_grupo_cia,
            c.cod_local,
            cNumVta_in,
            t.dni_cli,
            sum(d.ahorro)*-1,
            'SISTEMAS',
            sysdate,
            t.cod_tarjeta
            from   vta_pedido_vta_det d,
                   vta_pedido_vta_cab c,
                   fid_tarjeta_pedido t
            where  c.cod_grupo_cia = cCodGrupoCia_in
            and    c.cod_local = cCodLocal_in
            and    c.num_ped_vta =  cNumVtaAnt_in
            and    c.cod_grupo_cia = d.cod_grupo_cia
            and    c.cod_local = d.cod_local
            and    c.num_ped_vta = d.num_ped_vta
            and    c.cod_grupo_cia = t.cod_grupo_cia
            and    c.cod_local = t.cod_local
            and    c.num_ped_vta = t.num_pedido
            group by c.cod_grupo_cia,
            c.cod_local,cNumVta_in,t.dni_cli,t.cod_tarjeta;
            
        --***FIN ***LTAVARA - 22.05.2015 INSERTAR EL PEDIDO DE NC EN EL FID_TARJETA_PEDIDO

--- hace update de cabecera igual a suma de detalle ---
      --  dubilluz 17.07.2015
          update VTA_PEDIDO_VTA_CAB c
             set (ahorro_camp,
                  ahorro_puntos,
                  AHORRO_PACK,
                  PTOS_AHORRO_PACK,
                  ahorro_total,
                  ptos_ahorro_camp) =
                 (select sum(nvl(d.ahorro_camp, 0)),
                         sum(nvl(d.ahorro_puntos, 0)),
                         sum(nvl(d.ahorro_pack, 0)),
                         sum(nvl(d.ptos_ahorro_pack, 0)),
                         sum(nvl(d.ahorro, 0)),
                         --sum(nvl(d.ahorro_camp, 0)) + sum(nvl(d.ahorro_puntos, 0)),
                         sum(nvl(d.ahorro_camp * 100, 0))
                    from vta_pedido_vta_det d
                   where d.cod_grupo_cia = c.cod_grupo_cia
                     and d.cod_local = c.cod_local
                     and d.num_ped_vta = c.num_ped_vta)
           where c.cod_grupo_cia = cCodGrupoCia_in
             and c.cod_local = cCodLocal_in
             and c.num_ped_vta = cNumVta_in;
             

         update VTA_PEDIDO_VTA_CAB c
             set (ahorro_camp,
                  ahorro_puntos,
                  AHORRO_PACK,
                  PTOS_AHORRO_PACK,
                  ahorro_total,
                  ptos_ahorro_camp) =
                 (select sum(nvl(d.ahorro_camp, 0)),
                         sum(nvl(d.ahorro_puntos, 0)),
                         sum(nvl(d.ahorro_pack, 0)),
                         sum(nvl(d.ptos_ahorro_pack, 0)),
                         sum(nvl(d.ahorro, 0)),
                         --sum(nvl(d.ahorro_camp, 0)) + sum(nvl(d.ahorro_puntos, 0)),
                         sum(nvl(d.ahorro_camp * 100, 0))
                    from vta_pedido_vta_det d
                   where d.cod_grupo_cia = c.cod_grupo_cia
                     and d.cod_local = c.cod_local
                     and d.num_ped_vta = c.num_ped_vta)
           where c.cod_grupo_cia = cCodGrupoCia_in
             and c.cod_local = cCodLocal_in
             and c.num_ped_vta = cNumVtaAnt_in;
             
         
         
             
      --  dubilluz 17.07.2015   
        
    EXCEPTION
      WHEN OTHERS THEN
        -- KMONCADA 2015.03.18 MUESTRA MSJ DE ERROR EN CASO SUCEDA
        RAISE_APPLICATION_ERROR(-20850,SQLERRM);
        V_NUM_COMP_PAGO_NC := 'N';
    END;
    RETURN V_NUM_COMP_PAGO_NC;
  END;
  
  /*****************************************************************************/
  PROCEDURE CAJ_ACT_CABECERA_NC(cCodGrupoCia_in      IN CHAR,
                               cCodLocal_in         IN CHAR,
                               cNumPedVta_in        IN CHAR,
                               nTotalBruto_in IN NUMBER,
                               nTotalNeto_in IN NUMBER,
                               nTotalIgv_in IN NUMBER,
                               nTotalDscto_in IN NUMBER,
                               vIdUsu_in IN VARCHAR2)
  AS
  BEGIN
    UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in, FEC_MOD_PED_VTA_CAB = SYSDATE,
      VAL_BRUTO_PED_VTA = nTotalBruto_in*-1,
        VAL_NETO_PED_VTA = nTotalNeto_in*-1,
        VAL_DCTO_PED_VTA = nTotalDscto_in*-1,
        VAL_IGV_PED_VTA = nTotalIgv_in*-1
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in;
  END;
  /*****************************************************************************/
    PROCEDURE CAJ_COBRA_NOTA_CREDITO(cCodGrupoCia_in    IN CHAR,
                  cCodLocal_in       IN CHAR,
               cNumPedVta_in      IN CHAR,
               cNumVtaAnt_in IN CHAR,
               cSecMovCaja_in       IN CHAR,
               cCodNumera_in       IN CHAR,
               cTipCompPago_in   IN CHAR,
               cCodMotKardex_in    IN CHAR,
                  cTipDocKardex_in    IN CHAR,
               cCodNumeraKardex_in IN CHAR,
               cUsuCreaCompPago_in IN CHAR,
               nNumCajaPago_in IN NUMBER,
               cTipCompAnul_in IN CHAR,
               cNumCompAnul_in IN CHAR)
  IS
    v_nSecCompPago VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_nNumCompPago VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
    v_cIndGraboComp    CHAR(1);
    v_cCodCliLocal    VTA_PEDIDO_VTA_CAB.COD_CLI_LOCAL%TYPE;
    v_cNomCliPedVta    VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE;
    v_cRucCliPedVta    VTA_PEDIDO_VTA_CAB.RUC_CLI_PED_VTA%TYPE;
    v_cDirCliPedVta    VTA_PEDIDO_VTA_CAB.DIR_CLI_PED_VTA%TYPE;
    v_nValRedondeo    VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_cIndDistrGratuita  VTA_PEDIDO_VTA_CAB.IND_DISTR_GRATUITA%TYPE;
    v_nContador      NUMBER;
    v_nMonto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nMonto2 VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;--JCORTEZ
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

    v_cSecMovCaja_in VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;
    v_nCant INTEGER;

    v_cIndDelivAutomatico char(1);
    v_tip_ped_vta         VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE;
    v_cCodConvenio CON_PED_VTA_CLI.COD_CONVENIO%TYPE := 'X';
    v_cCodCli CON_PED_VTA_CLI.COD_CLI%TYPE;
    v_cIndPedConvenio VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE;
    v_nValNeto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nMonCred VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;

    -- YA NO SE REALIZARA LA ANULACION EN ESTE METODO SINO DESPUES EN
    -- EL APLICATIVO
    -- DUBILLUZ  22.08.2008
    CURSOR curCupones IS
    SELECT C.COD_GRUPO_CIA,TRIM(C.COD_CUPON) AS COD_CUPON
      FROM VTA_CAMP_PEDIDO_CUPON C,
           VTA_CUPON O,
           VTA_CAMPANA_CUPON CAMP
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_PED_VTA = cNumVtaAnt_in
            AND C.ESTADO = 'E'
            AND C.IND_IMPR = 'S'
            AND C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
            AND C.COD_CUPON = O.COD_CUPON
            AND O.ESTADO = 'A'
            AND CAMP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
            AND CAMP.COD_CAMP_CUPON = C.COD_CAMP_CUPON
            AND CAMP.IND_MULTIUSO = 'N';--NO SE ANULAN LOS CUPONES MULTIUSO

    v_cIndLinea CHAR(1);
    v_cEstado2 VTA_CUPON.ESTADO%TYPE := 'A';
    v_IndConvenioBTLMF CHAR(1);

  BEGIN

   --Agregado por FRAMIREZ 13.06.2012
   SELECT CAB.IND_CONV_BTL_MF
     INTO v_IndConvenioBTLMF
   FROM VTA_PEDIDO_VTA_CAB CAB
   WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in AND
         CAB.COD_LOCAL     = cCodLocal_in AND
         CAB.NUM_PED_VTA   = cNumVtaAnt_in;


      v_cIndGraboComp := 'N';
    v_nContador := 0;
    --dbms_output.put_line('v_cIndGraboComp: ' || v_cIndGraboComp );
      SELECT VTA_CAB.VAL_REDONDEO_PED_VTA,
         VTA_CAB.COD_CLI_LOCAL,
          NVL(VTA_CAB.NOM_CLI_PED_VTA,' '),
          NVL(VTA_CAB.RUC_CLI_PED_VTA,' '),
          NVL(VTA_CAB.DIR_CLI_PED_VTA,' '),
       VTA_CAB.IND_DISTR_GRATUITA
    INTO   v_nValRedondeo,
         v_cCodCliLocal,
       v_cNomCliPedVta,
       v_cRucCliPedVta,
       v_cDirCliPedVta,
       v_cIndDistrGratuita
    FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
    WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   VTA_CAB.COD_LOCAL = cCodLocal_in
                AND   VTA_CAB.NUM_PED_VTA = cNumPedVta_in;
       FOR totalesComprobante_rec IN totalesComprobante
    LOOP
        v_nContador := v_nContador + 1;
      IF(v_nContador <> 1) THEN
        v_nValRedondeo := 0.00;
      END IF;
      IF(v_cIndDistrGratuita = 'S') THEN
        totalesComprobante_rec.VALOR_BRUTO := 0.00;
      totalesComprobante_rec.VALOR_DESCUENTO := 0.00;
      END IF;
        v_nSecCompPago := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in);
     -- ERIOS 11.05.2016 Se recupera la serie auxiliar
	 v_nNumCompPago := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nSecCompPago, 7, 0, 'I');
	 v_nNumCompPago := F_GET_SERIE_AUX_LOCAL(cCodGrupoCia_in, cCodLocal_in)||TRIM(v_nNumCompPago);	  
      v_nSecCompPago := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nSecCompPago, 10, 0, 'I' );
      /*dbms_output.put_line('v_nSecCompPago: ' || v_nSecCompPago );
      dbms_output.put_line('1: ' || totalesComprobante_rec.ITEMS );
      dbms_output.put_line('2: ' || totalesComprobante_rec.VALOR_BRUTO );
      dbms_output.put_line('3: ' || totalesComprobante_rec.VALOR_NETO );
      dbms_output.put_line('4: ' || totalesComprobante_rec.VALOR_DESCUENTO );
      dbms_output.put_line('5: ' || totalesComprobante_rec.VALOR_AFECTO );
      dbms_output.put_line('6: ' || totalesComprobante_rec.VALOR_IGV );*/
      SELECT SEC_MOV_CAJA INTO v_cSecMovCaja_in
      FROM VTA_CAJA_PAGO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_CAJA_PAGO = nNumCajaPago_in;
      DBMS_OUTPUT.put_line('CAJ_COBRA_NOTA_CREDITO : VTA_COMP_PAGO');
      INSERT INTO VTA_COMP_PAGO(COD_GRUPO_CIA, COD_LOCAL, NUM_PED_VTA, SEC_COMP_PAGO,
                      TIP_COMP_PAGO, NUM_COMP_PAGO,
                      SEC_MOV_CAJA, CANT_ITEM, COD_CLI_LOCAL, NOM_IMPR_COMP,
                      DIREC_IMPR_COMP, NUM_DOC_IMPR, VAL_BRUTO_COMP_PAGO,
                      VAL_NETO_COMP_PAGO, VAL_DCTO_COMP_PAGO,
                      VAL_AFECTO_COMP_PAGO, VAL_IGV_COMP_PAGO,
                      VAL_REDONDEO_COMP_PAGO, USU_CREA_COMP_PAGO, PORC_IGV_COMP_PAGO)
       VALUES(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, v_nSecCompPago,
                      TIPO_COMPROBANTE_99,v_nNumCompPago,--v_nSecCompPago,--LTAVARA 01.10.2014
                      v_cSecMovCaja_in, TO_NUMBER(totalesComprobante_rec.ITEMS,'9,990'), v_cCodCliLocal, v_cNomCliPedVta,
                      v_cDirCliPedVta, v_cRucCliPedVta, TO_NUMBER(totalesComprobante_rec.VALOR_BRUTO,'999,990.00'),
                      TO_NUMBER(totalesComprobante_rec.VALOR_NETO,'999,990.00'), TO_NUMBER(totalesComprobante_rec.VALOR_DESCUENTO,'999,990.00'),
                      TO_NUMBER(totalesComprobante_rec.VALOR_AFECTO,'999,990.00'), TO_NUMBER(totalesComprobante_rec.VALOR_IGV,'999,990.00'),
                      v_nValRedondeo, cUsuCreaCompPago_in, TO_NUMBER(totalesComprobante_rec.PORC_IGV,'990.00'));

                  --
                 /* UPDATE VTA_PEDIDO_VTA_CAB
                  SET SEC_MOV_CAJA = v_cSecMovCaja_in
                  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                        AND  COD_LOCAL = cCodLocal_in
                        AND  NUM_PED_VTA = cNumPedVta_in;*/
                  --ACTUALIZA DETALLE PEDIDO
      UPDATE VTA_PEDIDO_VTA_DET SET USU_MOD_PED_VTA_DET = cUsuCreaCompPago_in, FEC_MOD_PED_VTA_DET = SYSDATE,
              SEC_COMP_PAGO = v_nSecCompPago
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND  COD_LOCAL = cCodLocal_in
            AND  NUM_PED_VTA = cNumPedVta_in
            AND  SEC_GRUPO_IMPR = totalesComprobante_rec.SECUENCIA;

      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in, cUsuCreaCompPago_in);
      v_cIndGraboComp := 'S';
    END LOOP;

    IF(v_cIndGraboComp = 'S') THEN
        --ACTUALIZA STK PRODUCTO
      CAJ_ACTUALIZA_STK_PROD_DETALLE(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in,cNumVtaAnt_in,
                       cCodMotKardex_in, cTipDocKardex_in, cCodNumeraKardex_in,
                     cUsuCreaCompPago_in);

    dbms_output.put_line('cSecMovCaja_in=' || cSecMovCaja_in);
    dbms_output.put_line('cCodGrupoCia_in=' || cCodGrupoCia_in);
    dbms_output.put_line('cCodLocal_in=' || cCodLocal_in);
    dbms_output.put_line('cNumPedVta_in=' || cNumPedVta_in);

        --ACTUALIZA CABECERA PEDIDO
      UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = cUsuCreaCompPago_in, FEC_MOD_PED_VTA_CAB = SYSDATE,
                 EST_PED_VTA = 'S', --PEDIDO COBRADO SIN COMPROBANTE IMPRESO
                 TIP_COMP_PAGO = cTipCompPago_in,
                 VAL_NETO_PED_VTA = ROUND(VAL_NETO_PED_VTA,2)
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND  COD_LOCAL = cCodLocal_in
          AND  NUM_PED_VTA = cNumPedVta_in;
      --SI ES ULTIMO COMPROBANTE DEL PEDIDO
      v_nCant := CAJ_GET_PRODS_REST(cCodGrupoCia_in,cCodLocal_in,cNumVtaAnt_in,cNumCompAnul_in, cTipCompAnul_in);
      IF v_nCant = 0 THEN
        --ACTUALIZAR PED CON REDONDEO
        SELECT VAL_REDONDEO_PED_VTA
          INTO v_nValRedondeo
        FROM VTA_PEDIDO_VTA_CAB
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND  COD_LOCAL = cCodLocal_in
              AND  NUM_PED_VTA = cNumVtaAnt_in;
        /*--31/05/2007 ERIOS El redondeo se obtiene de los comprobantes.
        SELECT SUM(C.VAL_REDONDEO_COMP_PAGO)
          INTO v_nValRedondeo
        FROM VTA_COMP_PAGO C
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
              AND C.COD_LOCAL = cCodLocal_in
              AND C.NUM_PED_VTA = cNumVtaAnt_in
              AND C.NUM_COMP_PAGO LIKE cNumCompAnul_in
              AND C.TIP_COMP_PAGO = cTipCompAnul_in;*/

        --Actualizar el pedido con el redondeo.
        UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = cUsuCreaCompPago_in, FEC_MOD_PED_VTA_CAB = SYSDATE,
              VAL_NETO_PED_VTA = VAL_NETO_PED_VTA-v_nValRedondeo,
              VAL_REDONDEO_PED_VTA = ABS(v_nValRedondeo)
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND  COD_LOCAL = cCodLocal_in
              AND  NUM_PED_VTA = cNumPedVta_in;

        --ACTUALIZAR COMP CON REDONDEO
        UPDATE VTA_COMP_PAGO SET USU_MOD_COMP_PAGO = cUsuCreaCompPago_in,FEC_MOD_COMP_PAGO = SYSDATE,
             VAL_REDONDEO_COMP_PAGO = ABS(v_nValRedondeo)
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_PED_VTA = cNumPedVta_in
                AND SEC_COMP_PAGO = v_nSecCompPago;

      END IF;
      --GRABA FORMA PAGO
      --CAJ_ANULAR_FORMA_PAGO(cCodGrupoCia_in,cCodLocal_in,'MOD','MOD', 'MOD',cNumPedVta_in,cUsuCreaCompPago_in);
      --31/05/2007 ERIOS Se graba la forma de pago en soles.

      SELECT VAL_NETO_PED_VTA INTO v_nMonto
      FROM VTA_PEDIDO_VTA_CAB
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_VTA = cNumPedVta_in;

     SELECT VAL_NETO_PED_VTA INTO v_nMonto2
        FROM VTA_PEDIDO_VTA_CAB
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND  COD_LOCAL = cCodLocal_in
              AND  NUM_PED_VTA = cNumVtaAnt_in;

      DBMS_OUTPUT.put_line('CAJ_COBRA_NOTA_CREDITO : VTA_FORMA_PAGO_PEDIDO');

      --JCORTEZ 23.07.09 SE GENERAR LA nota de creddito con las mismas formas de pago que el pedido a anular.

      --RAISE_APPLICATION_ERROR(-20040,'numero pedido--> '||cNumVtaAnt_in||'/ '||cNumPedVta_in||'   monto--> '||v_nMonto2||'/ '||v_nMonto*-1);
      --Si el total de nota de credito es menor que el pedido total, se genera efectivo soles
      IF((v_nMonto*-1)<v_nMonto2 AND (v_nMonto*-1)>0) THEN

        INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,COD_LOCAL,
        COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,
        VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,
        USU_CREA_FORMA_PAGO_PED  )
        VALUES(cCodGrupoCia_in,cCodLocal_in,
        g_cFormPagoEfectivo,cNumPedVta_in,v_nMonto,'01',--SOLES
        0,0,v_nMonto,cUsuCreaCompPago_in);

      ELSE --motos iguales se genera mismas formas de pago

        --001,071,0000049682,02,0000049683,%,'jcortez'
        --ANULAR LA FORMA DE PAGO CON MONTOS NEGATIVOS
        CAJ_ANULAR_FORMA_PAGO(cCodGrupoCia_in,cCodLocal_in,cNumVtaAnt_in,cTipCompAnul_in, cNumCompAnul_in,cNumPedVta_in,cUsuCreaCompPago_in);

      END IF;

      -- añadido dubilluz 04/06/2008
       SELECT VTA_CAB.IND_DELIV_AUTOMATICO,
              VTA_CAB.TIP_PED_VTA
       INTO    v_cIndDelivAutomatico,
               v_tip_ped_vta --12.08.2014
       FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
       WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    VTA_CAB.COD_LOCAL = cCodLocal_in
       AND    VTA_CAB.NUM_PED_VTA = cNumVtaAnt_in;


       IF(v_cIndDelivAutomatico = INDICADOR_SI) THEN
          UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB
          SET
                 TMP_CAB.USU_MOD_PED_VTA_CAB = cUsuCreaCompPago_in,
                 TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
                 --rherrera 12.08.2014
                 TMP_CAB.EST_PED_VTA =
                  (
                  CASE
                    WHEN v_tip_ped_vta = '03' THEN
                    EST_PED_PENDIENTE ---PENDIENTE
                  ELSE
                    -- dubilluz 31.07.2014
                    EST_PED_ANULADO --ANULADO
                  END),
                 --TMP_CAB.EST_PED_VTA = EST_PED_ANULADO,
                 TMP_CAB.Num_Ped_Vta_Origen = '' ,
                 TMP_CAB.IND_PEDIDO_ANUL = 'S'
          WHERE  TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
          AND    TMP_CAB.NUM_PED_VTA_ORIGEN = cNumVtaAnt_in;
       END IF;


      SELECT C.IND_PED_CONVENIO,C.VAL_NETO_PED_VTA
      INTO   v_cIndPedConvenio,v_nValNeto
      FROM   VTA_PEDIDO_VTA_CAB C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL = cCodLocal_in
      AND    C.NUM_PED_VTA = cNumVtaAnt_in;

      --Verifica Pedido Convenio
      --Agregado  por Framirez CONVENIO BTLMF
      IF   v_IndConvenioBTLMF = 'S' THEN

            v_IndConvenioBTLMF := 'S';
      ELSE

      IF v_cIndPedConvenio = 'S' THEN
        SELECT P.COD_CONVENIO,P.COD_CLI
        INTO v_cCodConvenio,v_cCodCli
        FROM CON_PED_VTA_CLI P
        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
              AND P.COD_LOCAL = cCodLocal_in
              AND P.NUM_PED_VTA = cNumVtaAnt_in;
      END IF;
       END IF;

        IF v_cIndPedConvenio = 'S'  THEN
          --Actualiza Monto credito.
          v_nMonCred :=v_nValNeto*-1;
          PTOVENTA_CONV.CON_ACTUALIZA_CONSUMO_CLI(v_cCodConvenio,v_cCodCli,v_nMonCred);
        END IF;

      --30/07/2008 ERIOS Se asume que se anulo el pedido completo
      --21/08/2008 DUBILLUZ ya no se verificara si hay linea en el
      --local ya que se anulara en el local y el JOB lo actualizara en el Local
      --v_cIndLinea := PTOVENTA_CAJ.VERIFICA_CONN_MATRIZ;

     -- Ya no se anulara el
      FOR cupon IN curCupones
      LOOP

/*        --Verifica conn matriz
        IF v_cIndLinea = 'S' THEN
          EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON@XE_000(:1,:2,:3); END;'
            USING cCodGrupoCia_in,TRIM(cupon.COD_CUPON), IN OUT v_cEstado2;
          \*IF v_cEstado2 <> 'A' THEN
            RAISE_APPLICATION_ERROR(-20016,'Cupon usado: '||TRIM(cupon.COD_CUPON));
          END IF;  *\
        END IF;
*/
        UPDATE VTA_CUPON
        SET ESTADO = 'N',
            FEC_PROCESA_MATRIZ = NULL,
            USU_PROCESA_MATRIZ = NULL,
          FEC_MOD_CUP_CAB = SYSDATE,
          USU_MOD_CUP_CAB = cUsuCreaCompPago_in
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_CUPON = TRIM(cupon.COD_CUPON);
      END LOOP;

      --Procesa cupones
      --ACTUALIZA_CUPONES_MATRIZ
    END IF;
  END;
  /*****************************************************************************/
  PROCEDURE CAJ_ACTUALIZA_STK_PROD_DETALLE(cCodGrupoCia_in      IN CHAR,
                                           cCodLocal_in         IN CHAR,
                                           cNumPedVta_in        IN CHAR,
                                           cNumVtaAnt_in       IN CHAR,
                                           cCodMotKardex_in    IN CHAR,
                                           cTipDocKardex_in    IN CHAR,
                                           cCodNumeraKardex_in IN CHAR,
                                           cUsuModProdLocal_in IN CHAR)
  IS
    v_cIndActStk CHAR(1);
    v_cIndProdVirtual CHAR(1);

  CURSOR productos IS
         SELECT VTA_DET.COD_PROD,
                --VTA_DET.CANT_ATENDIDA,--ERIOS 05/06/20058
                VTA_DET.CANT_FRAC_LOCAL AS CANT_ATENDIDA,
                PROD_LOCAL.STK_FISICO,
                --PROD_LOCAL.VAL_FRAC_LOCAL,
                VTA_DET.VAL_FRAC_LOCAL,
                PROD_LOCAL.UNID_VTA,
                -- KMONCADA 26.05.2016 ANULACION NO DEBE DE REGRESAR STOCK DE LAS CONSULTAS
                CASE 
                  WHEN PTOVENTA_VTA.F_IS_PROD_CENTRO_MEDICO(VTA_DET.COD_GRUPO_CIA, VTA_DET.COD_LOCAL, VTA_DET.COD_PROD) = 'S' THEN 'S'
                  ELSE DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI)  
                END IND_PROD_VIR,
                --DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR,
                VTA_DET.SEC_PED_VTA_DET,
                VTA_DET.FECHA_VENCIMIENTO_LOTE,
                VTA_DET.LOTE,
                VTA_DET.VAL_FRAC
         FROM   VTA_PEDIDO_VTA_DET VTA_DET,
                LGT_PROD_LOCAL PROD_LOCAL,
                LGT_PROD_VIRTUAL VIR
         WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    VTA_DET.COD_LOCAL = cCodLocal_in
         AND    VTA_DET.NUM_PED_VTA = cNumPedVta_in
         --INI ASOSA - 07/10/2014 - PANHD
             AND  NOT EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND VTA_DET.COD_PROD = PP.COD_PROD
                                                                    AND VTA_DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
             --FIN ASOSA - 07/10/2014 - PANHD
         AND    VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
         AND    VTA_DET.COD_LOCAL = PROD_LOCAL.COD_LOCAL
         AND    VTA_DET.COD_PROD = PROD_LOCAL.COD_PROD
         AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
         AND    PROD_LOCAL.COD_PROD = VIR.COD_PROD(+);
  BEGIN
       v_cIndActStk := 'N';
        FOR productos_rec IN productos
       LOOP
           v_cIndProdVirtual := productos_rec.IND_PROD_VIR;
           IF v_cIndProdVirtual = INDICADOR_SI THEN
             --GRABAR KARDEX VIRTUAL
             Ptoventa_Inv.INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in,
                                                    cCodLocal_in,
                                                    productos_rec.COD_PROD,
                                                    cCodMotKardex_in,
                                                    cTipDocKardex_in,
                                                    cNumPedVta_in,
                                                    (productos_rec.CANT_ATENDIDA * -1),
                                                    productos_rec.VAL_FRAC_LOCAL,
                                                    productos_rec.UNID_VTA,
                                                    cUsuModProdLocal_in,
                                                    cCodNumeraKardex_in);
             --ACTUALIZA
             UPDATE VTA_PEDIDO_VTA_DET SET FEC_MOD_PED_VTA_DET = SYSDATE,
                    SEC_COMP_PAGO_ORIGEN = (SELECT C.SEC_COMP_PAGO
                                            FROM   VTA_PEDIDO_VTA_DET C
                                            WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND    C.COD_LOCAL = cCodLocal_in
                                            AND    C.NUM_PED_VTA = cNumVtaAnt_in
                                            AND    C.COD_PROD = productos_rec.COD_PROD)
                                            --USU_MOD_PED_VTA_DET = vIdUsu_in, NO SE LE PASA EL USUARIO
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL = cCodLocal_in
             AND   NUM_PED_VTA = cNumPedVta_in
             AND   COD_PROD = productos_rec.COD_PROD;
           ELSE
             --GRABAR KARDEX
             Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
                                             cCodLocal_in,
                                             productos_rec.COD_PROD,
                                             cCodMotKardex_in,
                                             cTipDocKardex_in,
                                             cNumPedVta_in,
                                             productos_rec.STK_FISICO,
                                             (productos_rec.CANT_ATENDIDA * -1),
                                             productos_rec.VAL_FRAC_LOCAL,
                                             productos_rec.UNID_VTA,
                                             cUsuModProdLocal_in,
                                             cCodNumeraKardex_in,
                                             NULL, NULL, NULL,
                                             to_char(productos_rec.FECHA_VENCIMIENTO_LOTE,'dd/mm/yyyy'),
                                             productos_rec.LOTE);
             --ACTUALIZA STK PRODUCTO
             v_cIndActStk := 'S';
             PTOVTA_RESPALDO_STK.P_ACT_STOCK_PRODUCTO(cCodGrupoCia_in,
                                                      cCodLocal_in,
                                                      productos_rec.COD_PROD,
                                                      (productos_rec.CANT_ATENDIDA * -1),
                                                      productos_rec.VAL_FRAC_LOCAL,
                                                      productos_rec.VAL_FRAC,
                                                      cUsuModProdLocal_in,
                                                      productos_rec.LOTE);
             /*UPDATE LGT_PROD_LOCAL SET  USU_MOD_PROD_LOCAL = cUsuModProdLocal_in, FEC_MOD_PROD_LOCAL = SYSDATE,
                    STK_FISICO = STK_FISICO + (productos_rec.CANT_ATENDIDA * -1)
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL = cCodLocal_in
             AND   COD_PROD = productos_rec.COD_PROD;*/

             --ACTUALIZA
             UPDATE VTA_PEDIDO_VTA_DET SET FEC_MOD_PED_VTA_DET = SYSDATE,
                    SEC_COMP_PAGO_ORIGEN = (SELECT C.SEC_COMP_PAGO
                                            FROM   VTA_PEDIDO_VTA_DET C
                                            WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND    C.COD_LOCAL = cCodLocal_in
                                            AND    C.NUM_PED_VTA = cNumVtaAnt_in
                                            AND    C.COD_PROD = productos_rec.COD_PROD
                                            AND    C.SEC_PED_VTA_DET = productos_rec.Sec_Ped_Vta_Det)
                      --USU_MOD_PED_VTA_DET = vIdUsu_in, NO SE LE PASA EL USUARIO
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL = cCodLocal_in
             AND   NUM_PED_VTA = cNumPedVta_in
             AND   COD_PROD = productos_rec.COD_PROD
             AND   Sec_Ped_Vta_Det = productos_rec.Sec_Ped_Vta_Det;


           END IF;
       END LOOP;

  END;

  /****************************************************************************/
  FUNCTION CAJ_GET_SERIE_ANUL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipDoc_in IN CHAR)
  RETURN FarmaCursor
  IS
    curSerie Farmacursor;
  BEGIN
    OPEN curSerie FOR
    SELECT NUM_SERIE_LOCAL|| 'Ã' ||
          NUM_SERIE_LOCAL
    FROM  VTA_SERIE_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_COMP = cTipDoc_in;

    RETURN curSerie;
  END;

  /****************************************************************************/
  FUNCTION CAJ_GET_PRODS_REST(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cNumPedido_in IN CHAR,cNumComp_in IN CHAR, cTipDoc_in IN CHAR)
  RETURN NUMBER
  IS
    v_nCant INTEGER:=0;
  BEGIN
    BEGIN
      SELECT SUM(CANT_ATENDIDA)- SUM(CANT_USADA_NC)
        INTO v_nCant
      FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_DET D
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            --AND C.NUM_COMP_PAGO LIKE cNumComp_in
            --AND C.TIP_COMP_PAGO = cTipDoc_in
            AND D.NUM_PED_VTA = cNumPedido_in
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_PED_VTA = D.NUM_PED_VTA
            AND C.SEC_COMP_PAGO = D.SEC_COMP_PAGO;
		--ERIOS 22.10.2013 Valida uso en notas de creditos
		IF v_nCant > 0 THEN
			WITH DNC AS
			(SELECT DET.COD_PROD,SUM(DET.CANT_ATENDIDA) CANT_USADA_NC
				from VTA_PEDIDO_VTA_CAB CAB JOIN VTA_PEDIDO_VTA_DET DET ON (
				CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
				AND CAB.COD_LOCAL = DET.COD_LOCAL
				AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA)
				WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
				AND CAB.COD_LOCAL = cCodLocal_in
				AND NUM_PED_VTA_ORIGEN = cNumPedido_in
				GROUP BY DET.COD_PROD)
			SELECT SUM(D.CANT_ATENDIDA+NVL(DNC.CANT_USADA_NC,0))
					INTO v_nCant
				  FROM VTA_PEDIDO_VTA_DET D, DNC
				  WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
						AND D.COD_LOCAL = cCodLocal_in
						AND D.NUM_PED_VTA = cNumPedido_in
						AND D.COD_PROD = DNC.COD_PROD(+);
			IF v_nCant < 0 THEN
				v_nCant := 0;
			END IF;
		END IF;
    EXCEPTION
      WHEN OTHERS THEN
        NULL;
    END;
    RETURN v_nCant;
  END;

  /****************************************************************************/
  --MODIFICACION 28/08/2007
  PROCEDURE CAJ_INTERCAMBIO_COMPROBANTE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                      cNumDocA_in IN CHAR, nMonDocA_in IN NUMBER,
                                      cNumDocB_in IN CHAR, nMonDocB_in IN NUMBER,
                                      cTipComp_in IN CHAR,cIdUsu_in IN CHAR)
  AS
    v_dFechaDocA DATE;
    v_cNumPedA VTA_COMP_PAGO.NUM_PED_VTA%TYPE;
    v_cSecCompA VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;

    v_dFechaDocB DATE;
    v_cNumPedB VTA_COMP_PAGO.NUM_PED_VTA%TYPE;
    v_cSecCompB VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;

    v_dFechaPermitida DATE;

    --Se declaran Valores para Saber cuales son los limites de secKardex de cada Comprobante
    cNumComPagoA varchar2(10) := trim(cNumDocA_in);
    cSecMaxA    varchar2(10);
    cSecMinA    varchar2(10);

    cNumComPagoB varchar2(10) := trim(cNumDocB_in);
    cSecMaxB    varchar2(10);
    cSecMinB    varchar2(10);

   BEGIN
    --VERIFICAR DOC A
    BEGIN
      SELECT C.NUM_PED_VTA,C.SEC_COMP_PAGO,(SELECT FEC_PED_VTA
              FROM VTA_PEDIDO_VTA_CAB
              WHERE NUM_PED_VTA = C.NUM_PED_VTA
                    AND COD_GRUPO_CIA =cCodGrupoCia_in
                    AND COD_LOCAL=cCodLocal_in)
            INTO v_cNumPedA,v_cSecCompA,v_dFechaDocA
      FROM VTA_COMP_PAGO C
      WHERE C.TIP_COMP_PAGO = cTipComp_in
            AND C.NUM_COMP_PAGO = cNumDocA_in
            AND C.VAL_NETO_COMP_PAGO + C.VAL_REDONDEO_COMP_PAGO = nMonDocA_in
            --AND C.IND_COMP_ANUL = 'N'
            AND C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in FOR UPDATE;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20031,'No se encuentra el Documento A. Verifique!');
    END;

    IF PTOVENTA_CAJ.CAJ_F_IS_PED_CONV_MF_BTL(cCodGrupoCia_in,cCodLocal_in,v_cNumPedA) = 'S' THEN
       RAISE_APPLICATION_ERROR(-20043,'No se puede intercambiar un pedido Convenio. Verifique!');
    END IF ;


    --VERIFICAR DOC B
    BEGIN
      SELECT C.NUM_PED_VTA,C.SEC_COMP_PAGO,(SELECT FEC_PED_VTA
              FROM VTA_PEDIDO_VTA_CAB
              WHERE NUM_PED_VTA = C.NUM_PED_VTA
                    AND COD_GRUPO_CIA =cCodGrupoCia_in
                    AND COD_LOCAL=cCodLocal_in)
            INTO v_cNumPedB,v_cSecCompB,v_dFechaDocB
      FROM VTA_COMP_PAGO C
      WHERE C.TIP_COMP_PAGO = trim(cTipComp_in)
            AND C.NUM_COMP_PAGO = trim(cNumDocB_in)
            AND C.VAL_NETO_COMP_PAGO + C.VAL_REDONDEO_COMP_PAGO = nMonDocB_in
            --AND C.IND_COMP_ANUL = 'N'
            AND C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in FOR UPDATE;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20032,'No se encuentra el Documento B. Verifique!');
    END;


    IF PTOVENTA_CAJ.CAJ_F_IS_PED_CONV_MF_BTL(cCodGrupoCia_in,cCodLocal_in,v_cNumPedB) = 'S' THEN
       RAISE_APPLICATION_ERROR(-20044,'No se puede intercambiar un pedido Convenio. Verifique!');
    END IF ;

    --VERIFICAR MISMA FECHA
    --VERIFICAR FECHA PERMITIDA
    v_dFechaPermitida := TO_DATE(TO_CHAR(SYSDATE - g_nDiasPermitidosIntercambio,'dd/MM/yyyy'),'dd/MM/yyyy');

    IF (v_dFechaDocA < v_dFechaPermitida OR v_dFechaDocB < v_dFechaPermitida ) THEN
      RAISE_APPLICATION_ERROR(-20033,'. Verifique!');
    END IF;

    --INTERCAMBIAR
    --ANULAR COMPA
    UPDATE VTA_COMP_PAGO SET FEC_MOD_COMP_PAGO=SYSDATE,
         NUM_COMP_PAGO = v_cSecCompA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = v_cNumPedA
          AND SEC_COMP_PAGO = v_cSecCompA;
    --CAMBIAR COMPB
    UPDATE VTA_COMP_PAGO SET FEC_MOD_COMP_PAGO=SYSDATE,
         NUM_COMP_PAGO = trim(cNumDocA_in)
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = v_cNumPedB
          AND SEC_COMP_PAGO = v_cSecCompB;
    --CAMBIAR COMPA
    UPDATE VTA_COMP_PAGO SET FEC_MOD_COMP_PAGO=SYSDATE,
          NUM_COMP_PAGO = trim(cNumDocB_in)
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = v_cNumPedA
          AND SEC_COMP_PAGO = v_cSecCompA;

   --Añadido
   --28/08/2007  DUBILLUZ creacion
   --24/10/2007  dubilluz modificacion

      SELECT TRIM(MIN(SEC_KARDEX)),TRIM(MAX(SEC_KARDEX))
      INTO   cSecMinB , cSecMaxB
      FROM   LGT_KARDEX
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    TIP_COMP_PAGO = cTipComp_in
      AND    NUM_COMP_PAGO = cNumComPagoB ;

      SELECT TRIM(MIN(SEC_KARDEX)),TRIM(MAX(SEC_KARDEX))
      INTO   cSecMinA , cSecMaxA
      FROM   LGT_KARDEX
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
      AND    COD_LOCAL     = cCodLocal_in
      AND    TIP_COMP_PAGO = cTipComp_in
      AND    NUM_COMP_PAGO = cNumComPagoA ;


      update lgt_kardex k
      set    k.num_comp_pago  = cNumComPagoA,
             k.fec_mod_kardex = sysdate,
             k.usu_mod_kardex = cIdUsu_in
      where  k.cod_grupo_cia  =  cCodGrupoCia_in
      and    k.cod_local      =  cCodLocal_in
      and    k.tip_comp_pago  = cTipComp_in
      AND    k.sec_kardex between cSecMinB and cSecMaxB;

      update lgt_kardex k
      set    k.num_comp_pago  = cNumComPagoB,
             k.fec_mod_kardex = sysdate,
             k.usu_mod_kardex = cIdUsu_in
      where  k.cod_grupo_cia  =  cCodGrupoCia_in
      and    k.cod_local      =  cCodLocal_in
      and    k.tip_comp_pago  = cTipComp_in
      AND    k.sec_kardex between cSecMinA and cSecMaxA;

  END;

  FUNCTION CAJ_GET_IMPRESORAS(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    cur FarmaCursor;
  BEGIN
    OPEN cur FOR
    SELECT RUTA_IMPR|| 'Ã' ||DESC_IMPR_LOCAL||' - '||NUM_SERIE_LOCAL
    FROM VTA_IMPR_LOCAL
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_COMP IN ('01','02')--BOLETAS Y FACTURAS
          AND EST_IMPR_LOCAL = 'A';

    RETURN cur;
  END;

  /* ******************************************************************************************** */

  FUNCTION CAJ_GET_TIEMPO_MAX_ANULACION(cGrupoCia_in   IN CHAR,
                                        cCodLocal_in   IN CHAR,
                                        cNumPedVta_in  IN CHAR)
  RETURN CHAR
  IS
  TIEMPO  VARCHAR2(20);
  vTipo_prov varchar2(2);
  BEGIN
/*      SELECT G.LLAVE_TAB_GRAL INTO TIEMPO
        FROM   PBL_TAB_GRAL G
        WHERE  G.ID_TAB_GRAL = '163'
        AND    G.COD_APL     = 'PTO_VENTA'
        AND    G.COD_TAB_GRAL = 'TIEMPO_ANULACION';*/
        BEGIN
        SELECT V.COD_PROV_TEL
        INTO   vTipo_prov
        FROM   LGT_PROD_VIRTUAL V
        WHERE  V.COD_GRUPO_CIA = cGrupoCia_in
        AND    V.TIP_PROD_VIRTUAL = 'R'
        AND    V.COD_PROD IN (
                              SELECT D.COD_PROD
                              FROM   VTA_PEDIDO_VTA_DET D
                              WHERE  D.COD_GRUPO_CIA = cGrupoCia_in
                              AND    D.COD_LOCAL = cCodLocal_in
                              AND    D.NUM_PED_VTA = cNumPedVta_in
                              );
        EXCEPTION
        WHEN OTHERS THEN
             vTipo_prov := 'N';
        END;

        BEGIN
        DBMS_OUTPUT.put_line('vTipo_prov ' || vTipo_prov);
        SELECT G.LLAVE_TAB_GRAL
        INTO TIEMPO
        FROM   PBL_TAB_GRAL G
        WHERE  G.ID_TAB_GRAL IN (191,192)
        AND    G.COD_APL     = 'PTO_VENTA'
        AND    G.COD_TAB_GRAL = TRIM(vTipo_prov);

        EXCEPTION
        WHEN NO_DATA_FOUND THEN
          TIEMPO := '0';
        END;

      RETURN TIEMPO;
  END;

  FUNCTION CAJ_GET_NUMERO_PEDIDO(cGrupoCia_in   IN CHAR,
                                 cCodLocal_in   IN CHAR,
                                 cNumComp_in  IN CHAR
                                 )
  RETURN VARCHAR2
  is
    vNum_ped varchar2(20);
  BEGIN
        begin

        SELECT c.num_ped_vta
        into   vNum_ped
        FROM   VTA_COMP_PAGO C
        WHERE  C.COD_GRUPO_CIA = cGrupoCia_in
        AND    C.COD_LOCAL = cCodLocal_in
        AND    C.Num_Comp_Pago = cNumComp_in;

        exception
        when no_data_found then
          vNum_ped := ' ';
        end;

  return vNum_ped;
  END;
  /***************************************************************************/
  /*PROCEDURE CUPONES_ANULADOS_PEDIDO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                vIdUsu_in IN CHAR )
  AS
    v_cIndLinea CHAR(1);
    v_nCant INTEGER;

    CURSOR curCupones IS
    SELECT TRIM(O.COD_CUPON) AS COD_CUPON
    FROM VTA_CAMP_PEDIDO_CUPON C,
         VTA_CUPON O
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.NUM_PED_VTA = cNumPedVta_in
          AND C.ESTADO = 'E'
          AND C.IND_IMPR = 'S'
          AND C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
          AND C.COD_CUPON = O.COD_CUPON
          AND O.ESTADO = 'N'
          AND O.FEC_PROCESA_MATRIZ IS NULL;

    vRetorno CHAR(1) := 'X';
  BEGIN
    --Verifica pendientes
    SELECT COUNT(*) INTO v_nCant
    FROM VTA_CAMP_PEDIDO_CUPON C,
         VTA_CUPON O
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.NUM_PED_VTA = cNumPedVta_in
          AND C.ESTADO = 'E'
          AND C.IND_IMPR = 'S'
          AND C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
          AND C.COD_CUPON = O.COD_CUPON
          AND O.ESTADO = 'N'
          AND O.FEC_PROCESA_MATRIZ IS NULL;
    IF v_nCant > 0 THEN
      --Verifica conn matriz
      v_cIndLinea := PTOVENTA_CAJ.VERIFICA_CONN_MATRIZ;
      IF v_cIndLinea = 'S' THEN
        FOR cupon IN curCupones
        LOOP
          DBMS_OUTPUT.PUT_LINE('CUPON:'||cupon.COD_CUPON);
          EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON@XE_000(:1,:2,:3); END;'
          USING cCodGrupoCia_in,cupon.COD_CUPON,IN OUT vRetorno;
          DBMS_OUTPUT.PUT_LINE('vRetorno:'||vRetorno);
          IF vRetorno = '0' THEN
            EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.GRABAR_CUPON@XE_000(:1,:2,:3,:4); END;'
            USING cCodGrupoCia_in,cupon.COD_CUPON,vIdUsu_in,IN OUT vRetorno;
            COMMIT;
          END IF;

          EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.ACT_ESTADO_CUPON@XE_000(:1,:2,:3,:4,:5); END;'
          USING cCodGrupoCia_in,cupon.COD_CUPON,'N',vIdUsu_in,IN OUT vRetorno;
          DBMS_OUTPUT.PUT_LINE('vRetorno:'||vRetorno);
          IF vRetorno = 'S' THEN
            UPDATE VTA_CUPON
            SET FEC_PROCESA_MATRIZ = SYSDATE,
                USU_PROCESA_MATRIZ = vIdUsu_in
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                  AND COD_CUPON = cupon.COD_CUPON;
            COMMIT;
          END IF;
        END LOOP;
      END IF;
    END IF;
  END;
  \***************************************************************************\
  FUNCTION CUPONES_ANULADOS(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR)
  RETURN NUMBER
  IS
    v_nCant NUMBER;
    v_nCantCuponU NUMBER;

    CURSOR curCupones IS
    SELECT COD_GRUPO_CIA,COD_CUPON
    FROM VTA_CAMP_PEDIDO_CUPON
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_VTA = cNumPedVta_in
          AND ESTADO = 'E'
          AND IND_IMPR = 'S'
    ;
    v_cIndLinea CHAR(1);
    v_cEstado2 VTA_CUPON.ESTADO%TYPE := 'A';
  BEGIN
    SELECT COUNT(*) INTO v_nCant
      FROM VTA_CAMP_PEDIDO_CUPON C,
           VTA_CUPON O
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_PED_VTA = cNumPedVta_in
            AND C.ESTADO = 'E'
            AND C.IND_IMPR = 'S'
            AND C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
            AND C.COD_CUPON = O.COD_CUPON
            AND O.ESTADO = 'U';

    IF v_nCant = 0 THEN
      v_cIndLinea := PTOVENTA_CAJ.VERIFICA_CONN_MATRIZ;
      FOR cupon IN curCupones
      LOOP
        IF v_cIndLinea = 'S' THEN
          EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON@XE_000(:1,:2,:3); END;'
            USING cCodGrupoCia_in,TRIM(cupon.COD_CUPON), IN OUT v_cEstado2;
          IF v_cEstado2 <> 'A' THEN
            --RAISE_APPLICATION_ERROR(-20016,'Cupon usado: '||TRIM(cupon.COD_CUPON));
            v_nCant := v_nCant + 1;
          END IF;
        END IF;
      END LOOP;

    END IF;

    RETURN v_nCant;
  END;*/
  /***************************************************************************/

PROCEDURE CAJ_P_ANULA_PED_SIN_RESPALDO(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        vIdUsu_in       IN CHAR,
                                        cModulo_in      IN CHAR DEFAULT 'V')
  AS
    mesg_body VARCHAR2(4000);
    v_cIndProdVirtual CHAR(1);
    v_cIndDelivAutomatico VTA_PEDIDO_VTA_CAB.IND_DELIV_AUTOMATICO%TYPE;
    CURSOR curProd IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,
                  DET.SEC_PED_VTA_DET,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) IND_PROD_VIR
           FROM   VTA_PEDIDO_VTA_DET DET,
                  LGT_PROD_VIRTUAL VIR
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
           AND    DET.COD_PROD = VIR.COD_PROD(+)
           ORDER BY DET.CANT_ATENDIDA DESC ;
    v_rCurProd curProd%ROWTYPE;

    CURSOR curProdRegalo IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,
                  DET.SEC_PED_VTA_DET
           FROM   VTA_PEDIDO_VTA_DET DET,
                  CA_CANJ_CLI_PED  CANJ
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
           AND    DET.COD_GRUPO_CIA = CANJ.COD_GRUPO_CIA
           AND    DET.COD_LOCAL = CANJ.COD_LOCAL
           AND    DET.NUM_PED_VTA = CANJ.NUM_PED_VTA
           AND    DET.SEC_PED_VTA_DET = CANJ.SEC_PED_VTA
           ORDER BY DET.CANT_ATENDIDA DESC;
/*
           CURSOR curListaProdRegaloAutomatico IS
                 SELECT COD_PROD,
                       CANT_MOV,
                       VAL_FRAC_LOCAL
                FROM    PBL_RESPALDO_STK
                WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                 AND    COD_LOCAL     = cCodLocal_in
                AND    NUM_PED_VTA   = cNumPedVta_in
                AND    IND_REGALO    = 'A';*/

    v_cEstPedido  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;


  BEGIN

       SELECT EST_PED_VTA,
              IND_PEDIDO_ANUL
       INTO   v_cEstPedido,
              v_cIndAnulado
       FROM   VTA_PEDIDO_VTA_CAB
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    NUM_PED_VTA = cNumPedVta_in  FOR UPDATE;

       if v_cEstPedido = 'N' THEN
             RAISE_APPLICATION_ERROR(-20002,'El Pedido esta anulado.');
          elsif v_cEstPedido = 'C' THEN
                RAISE_APPLICATION_ERROR(-20003,'El Pedido fue cobrado. ¡No puede Anular este Pedido!');
       END IF;

       IF v_cEstPedido <> 'P' THEN
          RAISE_APPLICATION_ERROR(-20001,'El Pedido no esta pendiente. ¡No puede Anular este Pedido!');
       END IF;



       UPDATE VTA_PEDIDO_VTA_CAB SET USU_MOD_PED_VTA_CAB = vIdUsu_in,  FEC_MOD_PED_VTA_CAB = SYSDATE,
              EST_PED_VTA = EST_PED_ANULADO,
              IND_PEDIDO_ANUL = INDICADOR_SI
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL     = cCodLocal_in
       AND    NUM_PED_VTA   = cNumPedVta_in;


       FOR v_rCurProd IN curProd
       LOOP
           v_cIndProdVirtual := v_rCurProd.IND_PROD_VIR;
           IF v_cIndProdVirtual = INDICADOR_SI THEN
             UPDATE VTA_PEDIDO_VTA_DET  SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
                    EST_PED_VTA_DET = 'N'
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
             AND    NUM_PED_VTA   = cNumPedVta_in
             AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
           ELSE

             UPDATE VTA_PEDIDO_VTA_DET  SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
                    EST_PED_VTA_DET = 'N'
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
             AND    NUM_PED_VTA   = cNumPedVta_in
             AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;



           END IF;
      END LOOP;


            ----------------------------------.
            --SE AGREGO PARA CAMPAÑAS ACUMULA TU COMPRA Y GANA
             FOR v_rCurProd IN curProdRegalo
                                 LOOP


/*
             UPDATE LGT_PROD_LOCAL
             SET    USU_MOD_PROD_LOCAL = vIdUsu_in,
                    FEC_MOD_PROD_LOCAL = SYSDATE,
                    STK_COMPROMETIDO = STK_COMPROMETIDO - v_rCurProd.CANT_FRAC_LOCAL
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL     = cCodLocal_in
             AND   COD_PROD      = v_rCurProd.COD_PROD;
*/
             UPDATE VTA_PEDIDO_VTA_DET
             SET    USU_MOD_PED_VTA_DET = vIdUsu_in,
                    FEC_MOD_PED_VTA_DET = SYSDATE,
                    EST_PED_VTA_DET = 'N'
             WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
             AND    NUM_PED_VTA   = cNumPedVta_in
             AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET;
/*
            UPDATE PBL_RESPALDO_STK
            SET    CANT_MOV = CANT_MOV -  v_rCurProd.CANT_ATENDIDA
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND    COD_PROD = v_rCurProd.COD_PROD
            AND     NUM_PED_VTA = cNumPedVta_in
            AND    CANT_MOV >= v_rCurProd.CANT_ATENDIDA
            AND    MODULO = 'V'
            AND    ROWNUM = 1;


            DELETE PBL_RESPALDO_STK_AUX
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND    COD_PROD      = v_rCurProd.COD_PROD
            AND    COD_LOCAL     = cCodLocal_in
            AND     NUM_PED_VTA   = cNumPedVta_in
            AND    IND_REGALO = 'S';

            DELETE PBL_RESPALDO_STK
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND    COD_PROD      = v_rCurProd.COD_PROD
            AND    COD_LOCAL     = cCodLocal_in
            AND     NUM_PED_VTA   = cNumPedVta_in
            AND    IND_REGALO = 'S';
*/
            END LOOP;
          -------------------------
          --Inicio de añadir
          --Añadido para Eliminar los productos automaticos
          --dubilluz 18.02.2009
          /*FOR respaldoStk_rec IN curListaProdRegaloAutomatico LOOP
            UPDATE LGT_PROD_LOCAL l
               SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = vIdUsu_in,
                   STK_COMPROMETIDO = NVL(STK_COMPROMETIDO,0) - respaldoStk_rec.CANT_MOV/NVL(respaldoStk_rec.VAL_FRAC_LOCAL,l.val_frac_local)*l.val_frac_local --MODIFICACION JLUNA 20090216
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND    COD_LOCAL = cCodLocal_in
             AND    COD_PROD  = respaldoStk_rec.COD_PROD;

            DELETE PBL_RESPALDO_STK
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
             AND    COD_LOCAL     = cCodLocal_in
            AND    NUM_PED_VTA   = cNumPedVta_in
            AND    IND_REGALO    = 'A'
            AND    COD_PROD      = respaldoStk_rec.COD_PROD
            AND    VAL_FRAC_LOCAL = respaldoStk_rec.VAL_FRAC_LOCAL
            AND    CANT_MOV = respaldoStk_rec.CANT_MOV
            AND    ROWNUM = 1;
          END LOOP;*/

         /* -------------------------
          UPDATE PBL_RESPALDO_STK C
          SET    NUM_PED_VTA = NULL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
           AND    COD_LOCAL = cCodLocal_in
          AND     NUM_PED_VTA = cNumPedVta_in
          AND    MODULO = 'V';*/

      update vta_camp_pedido_cupon p
      set    p.estado = 'N'
      where  p.cod_grupo_cia = cCodGrupoCia_in
      and    p.cod_local = cCodLocal_in
      and    p.num_ped_vta = cNumPedVta_in
      and    p.estado = 'S';



  EXCEPTION
    WHEN OTHERS THEN
         mesg_body := 'ERROR AL ANULAR PEDIDO PENDIENTE No ' || cNumPedVta_in || '. ' || SQLERRM;
         FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    'joliva@mifarma.com.pe, operador',
                                    'ERROR AL ANULAR PEDIDO PENDIENTE',
                                    'ERROR',
                                    mesg_body,
                                    '');
         RAISE;
  END;

  /*******************************/



  /****************************************************************************************************/

    /****************************************************************************************************/

  FUNCTION CAJ_EXISTE_PROD_CANJE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR)
  RETURN INTEGER
  IS
    v_nRetorno INTEGER:=0;
  BEGIN

     SELECT  COUNT(*)  INTO v_nRetorno --prod canje
     FROM VTA_CAMPANA_PROD B
     WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
     AND B.COD_CAMP_CUPON IN (SELECT DISTINCT A.COD_CAMP_CUPON
                              FROM VTA_PEDIDO_VTA_DET A
                              WHERE A.EST_PED_VTA_DET='C'
                              AND A.COD_GRUPO_CIA=cCodGrupoCia_in
                              AND A.COD_LOCAL=cCodLocal_in
                              AND A.NUM_PED_VTA=cNumPedVta_in);

    RETURN v_nRetorno;
  END;
  /***************************************************************************************************/
  FUNCTION CAJ_EXIST_PROD_CANJ_REG(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cDni IN CHAR)
  RETURN VARCHAR2
  IS
  Cant NUMBER;
  vExist CHAR(1);
  vExistC CHAR(1);
  BEGIN

  vExistC:=CAJ_EXISTE_PROD_CANJE(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);

  --Verifica si existen productos regalo en Detalle
  SELECT COUNT(*) INTO Cant
  FROM VTA_PEDIDO_VTA_DET X
  WHERE X.EST_PED_VTA_DET='C'
  AND X.COD_GRUPO_CIA=cCodGrupoCia_in
  AND X.COD_LOCAL=cCodLocal_in
  AND X.NUM_PED_VTA=cNumPedVta_in
  AND X.COD_PROD IN (SELECT C.CA_COD_PROD --prod regalo
                     FROM VTA_CAMPANA_CUPON C
                     WHERE C.COD_GRUPO_CIA=cCodGrupoCia_in
                     AND C.TIP_CAMPANA='A'
                     AND C.COD_CAMP_CUPON IN (SELECT DISTINCT A.COD_CAMP_CUPON
                                              FROM VTA_PEDIDO_VTA_DET A
                                              WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
                                              AND A.COD_LOCAL=cCodLocal_in
                                              AND A.NUM_PED_VTA=cNumPedVta_in));
    IF(Cant>0 OR vExistC>0)THEN
     vExist:='S';
    ELSE
     vExist:='N';
    END IF;

  RETURN vExist;
  END;


 /*******************************************************************************************/
  PROCEDURE CAJ_ANULA_PED_FIDELIZADO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    vIdUsu_in       IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cDni_in         IN CHAR,
                                    cIndLocal_in    IN CHAR)
  AS

  ExistCanjeAct NUMBER;
  ExistCanje     NUMBER;
  ExistHist  NUMBER;
  TIPO  CHAR(1);

  cNumPedOri CA_PED_ORIGEN_CANJ.NUM_PED_ORIGEN%TYPE;
  cCodLocalOri CA_PED_ORIGEN_CANJ.COD_LOCAL_ORIGEN%TYPE;
  cSecPedOri CA_PED_ORIGEN_CANJ.SEC_PED_ORIGEN%TYPE;
  cCodProdOri CA_PED_ORIGEN_CANJ.Cod_Prod_Origen%TYPE;

  BEGIN

  --si el pedido tiene canje en local
  SELECT COUNT(*) INTO ExistCanje
  FROM CA_PED_ORIGEN_CANJ A
  WHERE  A.ESTADO='A'--30/12/2008
  AND A.DNI_CLI=cDni_in
  AND A.COD_GRUPO_CIA=cCodGrupoCia_in
  AND A.COD_LOCAL_ORIGEN=cCodLocal_in
  AND A.NUM_PED_ORIGEN=cNumPedVta_in;

  SELECT COUNT(*) INTO ExistCanjeAct
  FROM CA_PED_ORIGEN_CANJ B
  WHERE B.ESTADO='A'
  AND B.DNI_CLI=cDni_in
  AND B.COD_GRUPO_CIA=cCodGrupoCia_in
  AND B.COD_LOCAL_ORIGEN=cCodLocal_in
  AND B.NUM_PED_ORIGEN=cNumPedVta_in;

   TIPO:= PTOVENTA_CAJ_ANUL.CAJ_TIPO_PED_FIDELIZADO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);

    IF (TIPO='A') THEN  --si es acumulado
        --IF(ExistCanje=0)THEN
        IF(ExistCanjeAct=0)THEN
           IF(cIndLocal_in='S')THEN
            --dubilluz 31.11.2009
            null;
            --antes no permitia anular
            --porque se anulaba en Matriz y si no existian datos en el local
            --no se podia tomar esta decision de anular.
            --RAISE_APPLICATION_ERROR(-20001,'No se puede anular el pedido, ya que no hay canjes relacionados.'); --en local
           END IF;
       -- ELSE
           -- RAISE_APPLICATION_ERROR(-20002,'No se puede anular el pedido, ya que hay canjes relacionados');
        END IF;

        IF(ExistCanje>0)THEN
          RAISE_APPLICATION_ERROR(-20007,'No se puede anular el pedido, ya que es parte de un canje.');
        END IF;
               -- Se anula el acumulado
               DBMS_OUTPUT.put_line('CAJ_ANULA_PED_FIDELIZADO : CA_HIS_CLI_PED');
               UPDATE CA_HIS_CLI_PED C
               SET C.ESTADO='N',
                   C.FEC_MOD_CA_HIS_CLI_PED=SYSDATE,
                   C.USU_MOD_CA_HIS_CLI_PED=vIdUsu_in,
                   C.IND_PROC_MATRIZ=DECODE(cIndLocal_in,'S','N',NULL)
               WHERE C.ESTADO='A'
               AND C.DNI_CLI=cDni_in
               AND C.COD_GRUPO_CIA=cCodGrupoCia_in
               AND C.COD_LOCAL=cCodLocal_in
               AND C.NUM_PED_VTA=cNumPedVta_in;

    ELSIF (TIPO='C') THEN --Si es canje
     PTOVENTA_CAJ_ANUL.CAJ_ANUL_CANJE(cCodGrupoCia_in,cCodLocal_in,vIdUsu_in,cNumPedVta_in,cDni_in,cIndLocal_in);
    --ELSE
    -- RAISE_APPLICATION_ERROR(-20004,'Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.');
    END IF;

 END;

   /********************************************************************************************/
  FUNCTION CAJ_TIPO_PED_FIDELIZADO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in   IN CHAR)
  RETURN VARCHAR2
  IS
    CantCanje NUMBER;
    CantAcum  NUMBER;
        CantCanjeDet  NUMBER;
    tipo CHAR(1);
  BEGIN

     SELECT COUNT(*) INTO CantAcum
     FROM CA_HIS_CLI_PED Y
     WHERE Y.ESTADO='A'
     AND Y.COD_GRUPO_CIA=cCodGrupoCia_in
     AND Y.COD_LOCAL=cCodLocal_in
     AND Y.NUM_PED_VTA=cNumPedVta_in;

     SELECT COUNT(*) INTO CantCanje
     FROM CA_CANJ_CLI_PED X
     WHERE X.ESTADO='A'
     AND  X.COD_GRUPO_CIA=cCodGrupoCia_in
     AND X.COD_LOCAL=cCodLocal_in
     AND X.NUM_PED_VTA= cNumPedVta_in;

       IF(CantAcum>0 AND CantCanje=0)THEN
          tipo:= 'A';   -- acumulado
       ELSIF(CantAcum=0 AND CantCanje=0) THEN
           tipo:='E';
       ELSE
          tipo:= 'C';  --Canje o ambos
       END IF;

  RETURN tipo;

  END;

  /**************************************************************************************/
 PROCEDURE CAJ_ANUL_CANJE(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR,
                          vIdUsu_in       IN CHAR,
                          cNumPedVta_in   IN CHAR,
                          cDni_in         IN CHAR,
                          cIndLocal_in    IN CHAR)
  AS

  ExistHist  NUMBER;
  ExistCanj  NUMBER;
  cantHist NUMBER;
  ExistCanj2 NUMBER;
  codCia    CA_PED_ORIGEN_CANJ.COD_GRUPO_CIA%TYPE;
  codLocal    CA_PED_ORIGEN_CANJ.COD_LOCAL_CANJ%TYPE;
  numPed    CA_PED_ORIGEN_CANJ.NUM_PED_CANJ%TYPE;
  dniCli    CA_PED_ORIGEN_CANJ.DNI_CLI%TYPE;


     CURSOR PEDCANJE IS
     SELECT G.COD_GRUPO_CIA,G.COD_LOCAL_CANJ,G.NUM_PED_CANJ,G.DNI_CLI
          FROM CA_PED_ORIGEN_CANJ G
          WHERE G.ESTADO='A'
          AND G.COD_GRUPO_CIA=cCodGrupoCia_in
          AND G.COD_LOCAL_ORIGEN=cCodLocal_in
          AND G.NUM_PED_ORIGEN=cNumPedVta_in
          AND G.DNI_CLI=cDni_in;
  BEGIN

      --Verifica que no existe historico generado por el canje
      SELECT COUNT(*) INTO ExistHist
      FROM CA_HIS_CLI_PED F
      WHERE F.ESTADO='A'
      AND EXISTS (SELECT 1
                  FROM CA_CANJ_CLI_PED I
                  WHERE I.COD_GRUPO_CIA=cCodGrupoCia_in
                  AND I.COD_LOCAL=cCodLocal_in
                  AND I.NUM_PED_VTA=cNumPedVta_in
                  AND I.COD_GRUPO_CIA=F.COD_GRUPO_CIA
                  AND I.COD_LOCAL=F.COD_LOCAL
                  AND I.NUM_PED_VTA=F.NUM_PED_VTA);

     /* SELECT G.COD_GRUPO_CIA,G.COD_LOCAL_CANJ,G.NUM_PED_CANJ,G.DNI_CLI
          INTO codCia,codLocal,numPed,dniCli*/
     SELECT COUNT(*) INTO  ExistCanj
          FROM CA_PED_ORIGEN_CANJ G
          WHERE G.ESTADO='A'
          AND G.COD_GRUPO_CIA=cCodGrupoCia_in
          AND G.COD_LOCAL_ORIGEN=cCodLocal_in
          AND G.NUM_PED_ORIGEN=cNumPedVta_in
          AND G.DNI_CLI=cDni_in;


    IF(ExistHist>0)THEN
      FOR ROWNUM IN PEDCANJE
      LOOP
        IF(ROWNUM.NUM_PED_CANJ<>cNumPedVta_in)THEN
         --RAISE_APPLICATION_ERROR(-20005,'No se puede anular el pedido, ya que es parte de su propio canje');
        --ELSE */
          /*SELECT COUNT(*) INTO cantHist
          FROM CA_HIS_CLI_PED X
          WHERE X.ESTADO='A'
          AND (X.COD_GRUPO_CIA,
                 X.COD_LOCAL,
                 X.NUM_PED_VTA,
                 X.DNI_CLI) = (SELECT Y.COD_GRUPO_CIA,Y.COD_LOCAL,Y.NUM_PED_VTA,Y.DNI_CLI
                              FROM CA_CANJ_CLI_PED Y
                              WHERE Y.COD_GRUPO_CIA=ROWNUM.COD_GRUPO_CIA
                              AND Y.COD_LOCAL=ROWNUM.COD_LOCAL_CANJ
                              AND Y.NUM_PED_VTA=ROWNUM.NUM_PED_CANJ
                              AND Y.DNI_CLI=ROWNUM.DNI_CLI);*/

            --IF(cantHist>0)THEN
                 RAISE_APPLICATION_ERROR(-20006,'No se puede anular, ya que hay canjes asociados');
           -- END IF;
        END IF;
          --recursividad para validar acumulado y canje
          --PTOVENTA_CAJ_ANUL_JC.CAJ_ANUL_CANJE(codCia,codLocal,vIdUsu_in,numPed,dniCli,cIndLocal_in);
      END
      LOOP;

      END IF;

      DBMS_OUTPUT.PUT_LINE('Actualizando pedido '||cNumPedVta_in);
            DBMS_OUTPUT.PUT_LINE('Indicador Local '||cIndLocal_in);
        --Anula Canje
        DBMS_OUTPUT.put_line('CAJ_ANUL_CANJE : CA_CANJ_CLI_PED');
        UPDATE CA_CANJ_CLI_PED D
        SET D.ESTADO='N',
            D.USU_MOD_CA_CANJ_CLI=vIdUsu_in,
            D.FEC_MOD_CANJ_CLI=SYSDATE,
            D.IND_PROC_MATRIZ=DECODE(cIndLocal_in,'S','N',NULL)
        WHERE D.DNI_CLI=cDni_in
        AND D.COD_GRUPO_CIA=cCodGrupoCia_in
        AND D.COD_LOCAL=cCodLocal_in
        AND D.NUM_PED_VTA=cNumPedVta_in;

        --Anula Detalle del Canje
        DBMS_OUTPUT.put_line('CAJ_ANUL_CANJE : CA_PED_ORIGEN_CANJ');
        UPDATE CA_PED_ORIGEN_CANJ E
        SET E.ESTADO='N',
            E.FEC_MOD_CA_PED_ORIG=SYSDATE,
            E.USU_MOD_CA_PED_ORIG=vIdUsu_in,
            E.IND_PROC_MATRIZ=DECODE(cIndLocal_in,'S','N',NULL)
        WHERE E.DNI_CLI=cDni_in
        AND E.COD_GRUPO_CIA=cCodGrupoCia_in
        AND E.COD_LOCAL_CANJ=cCodLocal_in
        AND E.NUM_PED_CANJ=cNumPedVta_in;


         -- Se anula el acumulado cuando es generado por el mismo pedudo
         DBMS_OUTPUT.put_line('CAJ_ANUL_CANJE : CA_HIS_CLI_PED');
               UPDATE CA_HIS_CLI_PED C
               SET C.ESTADO='N'
               WHERE C.ESTADO='A'
               AND C.DNI_CLI=cDni_in
               AND C.COD_GRUPO_CIA=cCodGrupoCia_in
               AND C.COD_LOCAL=cCodLocal_in
               AND C.NUM_PED_VTA=cNumPedVta_in;

          --Se devueve cantidad restante
          DBMS_OUTPUT.put_line('CAJ_ANUL_CANJE : CA_HIS_CLI_PED');
          UPDATE CA_HIS_CLI_PED H
            SET    (
                   H.IND_PROC_MATRIZ,
                   H.CANT_RESTANTE,
                   H.USU_MOD_CA_HIS_CLI_PED,
                   H.FEC_MOD_CA_HIS_CLI_PED,
                   H.IND_OPERA_CANJE ,
                   H.CANT_USO_OPER_MATRIZ -- AGREGADO DUBILLUZ - 15.01.2010
                   ) = (
                        SELECT DECODE(cIndLocal_in,'S','N',NULL),H.CANT_RESTANTE + O.CANT_USO,vIdUsu_in,SYSDATE,
                               'N',0 -- AGREGADO DUBILLUZ - 15.01.2010
                        FROM   CA_PED_ORIGEN_CANJ O
                        WHERE  O.DNI_CLI = cDni_in
                        AND    O.COD_GRUPO_CIA  = cCodGrupoCia_in
                        AND    O.COD_LOCAL_CANJ = cCodLocal_in
                        AND    O.NUM_PED_CANJ = cNumPedVta_in
                        AND    O.DNI_CLI = H.DNI_CLI
                        AND    O.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                        AND    O.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                        AND    O.COD_LOCAL_ORIGEN = H.COD_LOCAL
                        AND    O.NUM_PED_ORIGEN = H.NUM_PED_VTA
                        AND    O.SEC_PED_ORIGEN = H.SEC_PED_VTA
                        AND    O.COD_PROD_ORIGEN = H.COD_PROD
                       )
            WHERE /* H.DNI_CLI        = cDni_in
            AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
            AND    H.COD_LOCAL      = cCodLocal_in
            AND    H.NUM_PED_VTA    = cNumPedVta_in
            AND   */ EXISTS (
                            SELECT 1
                            FROM   CA_PED_ORIGEN_CANJ W
                            WHERE  W.DNI_CLI = cDni_in
                            AND    W.COD_GRUPO_CIA  = cCodGrupoCia_in
                            AND    W.COD_LOCAL_CANJ = cCodLocal_in
                            AND    W.NUM_PED_CANJ = cNumPedVta_in--
                            AND    W.DNI_CLI = H.DNI_CLI
                            AND    W.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                            AND    W.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                            AND    W.COD_LOCAL_ORIGEN = H.COD_LOCAL
                            AND    W.NUM_PED_ORIGEN = H.NUM_PED_VTA
                            AND    W.SEC_PED_ORIGEN = H.SEC_PED_VTA
                            AND    W.COD_PROD_ORIGEN = H.COD_PROD
                          );

   EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20007,'Se genero un error en la anulacion del pedido Fidelizado en Local:'||cNumPedVta_in);
     END;

   /* ********************************************************************************************** */
  PROCEDURE CAJ_VERIFICA_PEDIDO_ANU(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                nMontoVta_in    IN NUMBER,
                               cTipoCompPago IN CHAR DEFAULT '%'
)
  AS
    v_cEstPedido  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    v_cIndAnulado VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE;
    nCantidadNotaCredito int:=0;
  BEGIN
       SELECT EST_PED_VTA,
              IND_PEDIDO_ANUL
       INTO   v_cEstPedido,
              v_cIndAnulado
       FROM   VTA_PEDIDO_VTA_CAB
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    NUM_PED_VTA = cNumPedVta_in
       AND    VAL_NETO_PED_VTA = nMontoVta_in
       AND TIP_COMP_PAGO = cTipoCompPago;

       IF v_cEstPedido <> 'C' THEN
          RAISE_APPLICATION_ERROR(-20002,'El Pedido no ha sido cobrado. ¡No puede Reimprimir el ticket!');
       END IF;

       IF v_cIndAnulado = 'N' THEN
          SELECT count(1)
           INTO   nCantidadNotaCredito
           FROM   VTA_PEDIDO_VTA_CAB c
           WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
           AND    COD_LOCAL = cCodLocal_in
           AND    C.NUM_PED_VTA_ORIGEN = cNumPedVta_in
           AND    C.TIP_COMP_PAGO = '04';--NOTA DE CREDITO

          if nCantidadNotaCredito = 0 then
           RAISE_APPLICATION_ERROR(-20003,'El Pedido no ha sido anulado. ¡No puede Reimprimir el ticket!');
          end if;
       END IF;
  EXCEPTION
       WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20001,'No se encuentra ningun Pedido con estos datos. ¡Verifique!');
  END;

 /* ********************************************************************************************** */
 FUNCTION CAJ_LISTA_DETALLE_PEDIDO_ANU (cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cTipComp_in IN CHAR, cNumComp_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT D.COD_PROD || 'Ã' ||
           R.DESC_PROD || 'Ã' ||
           D.UNID_VTA || 'Ã' ||
           TO_CHAR(D.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
           D.CANT_ATENDIDA || 'Ã' ||
           TO_CHAR(D.VAL_PREC_TOTAL,'999,990.000')
    FROM VTA_PEDIDO_VTA_DET D, VTA_COMP_PAGO C, LGT_PROD_LOCAL P, LGT_PROD R
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_PED_VTA = cNumPedVta_in
          AND C.TIP_COMP_PAGO LIKE cTipComp_in
          AND C.NUM_COMP_PAGO LIKE cNumComp_in
          AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND D.COD_LOCAL = C.COD_LOCAL
          AND D.NUM_PED_VTA = C.NUM_PED_VTA
          AND D.SEC_COMP_PAGO = C.SEC_COMP_PAGO
          AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND D.COD_LOCAL = P.COD_LOCAL
          AND D.COD_PROD = P.COD_PROD
          AND P.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND P.COD_PROD = R.COD_PROD;

    RETURN curDet;
  END;

  /* ********************************************************************************************** */
 FUNCTION CAJA_TURNO_PEDIDO_ANULADO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR)
  RETURN VARCHAR2
  IS
    vCajaTurno VARCHAR2(20);
  BEGIN

    SELECT A.num_caja_pago || 'Ã' || A.num_turno_caja INTO vCajaTurno
    FROM   ce_mov_caja A
    WHERE  A.cod_grupo_cia = cCodGrupoCia_in
    AND    A.cod_local = cCodLocal_in
    AND    A.sec_mov_caja = (
                              SELECT DISTINCT B.sec_mov_caja
                              FROM   vta_pedido_vta_cab B
                              WHERE  B.cod_grupo_cia = cCodGrupoCia_in
                              AND    B.cod_local =cCodLocal_in
                              AND    B.num_ped_vta_origen = cNumPedVta_in
                            );
   RETURN vCajaTurno;
  END;

  /* ********************************************************************************************** */
 FUNCTION CAJ_F_OBT_DAT_CONV_ANUL (cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cNumPedVta_in IN CHAR,
                                   vIdUsu_in IN VARCHAR2)
  RETURN VARCHAR2
  AS
    v_nMonto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_dFechaPedido VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
    v_cIndPedConvenio VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE;
    v_nValNeto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_cCodConvenio CON_PED_VTA_CLI.COD_CONVENIO%TYPE := 'X';
    v_cCodCli CON_PED_VTA_CLI.COD_CLI%TYPE;
    v_cNumNegPedVta VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;

    v_nMonCred VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;

    CURSOR curFPago IS
    SELECT  P.COD_FORMA_PAGO,
            P.IM_PAGO,
            P.TIP_MONEDA,
            P.VAL_TIP_CAMBIO,
            P.VAL_VUELTO,
            P.IM_TOTAL_PAGO,
            P.NUM_TARJ,
            P.FEC_VENC_TARJ,
            P.NOM_TARJ,
            F.COD_CONVENIO,
            F.Ind_Tarj
    FROM VTA_FORMA_PAGO_PEDIDO P,
         VTA_FORMA_PAGO F
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.COD_LOCAL = cCodLocal_in
          AND P.NUM_PED_VTA = cNumPedVta_in
          AND P.COD_GRUPO_CIA = F.COD_GRUPO_CIA
          AND P.COD_FORMA_PAGO = F.COD_FORMA_PAGO;

    v_MensajeObt VARCHAR2(200);
  BEGIN

      SELECT TRUNC(C.FEC_PED_VTA),C.IND_PED_CONVENIO,C.VAL_NETO_PED_VTA
      INTO v_dFechaPedido,v_cIndPedConvenio,v_nValNeto
      FROM VTA_PEDIDO_VTA_CAB C
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_PED_VTA = cNumPedVta_in;

      v_nMonCred := 0;

      --Verifica Pedido Convenio
      IF v_cIndPedConvenio = 'S' THEN
        SELECT P.COD_CONVENIO,P.COD_CLI
        INTO v_cCodConvenio,v_cCodCli
        FROM CON_PED_VTA_CLI P
        WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
              AND P.COD_LOCAL = cCodLocal_in
              AND P.NUM_PED_VTA = cNumPedVta_in;

        SELECT C.NUM_PED_VTA
          INTO v_cNumNegPedVta
          FROM VTA_PEDIDO_VTA_CAB C
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.COD_LOCAL = cCodLocal_in
           AND C.NUM_PED_VTA_ORIGEN = cNumPedVta_in;

      END IF;

        FOR rowFPago IN curFPago
        LOOP
        --  JMIRANDA 08/09/2009 Retorna Datos para llamar desde java conexion remota a matriz
          IF v_cIndPedConvenio = 'S' AND v_cCodConvenio = rowFPago.Cod_Convenio THEN
            v_nMonCred := rowFPago.IM_TOTAL_PAGO*-1;
            v_MensajeObt := v_cCodConvenio || 'Ã' || v_cCodCli || 'Ã' || trim(To_char(v_nMonCred,'999,990.000'))
                            || 'Ã' || cCodLocal_in || 'Ã' || v_cNumNegPedVta
                            || 'Ã' || vIdUsu_in;
            RETURN v_MensajeObt;
          END IF;

        END LOOP;
      RETURN 'N';
 END;

 /*******************************************************************************************************************/
 PROCEDURE CAJ_ANULA_VAL_FIDELIZADO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    vIdUsu_in       IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cDni_in         IN CHAR,
                                    cIndLocal_in    IN CHAR)
  AS

    ExistCanjeAct NUMBER;
    ExistCanje     NUMBER;
    ExistHist  NUMBER;
    TIPO  CHAR(1);

    cNumPedOri CA_PED_ORIGEN_CANJ.NUM_PED_ORIGEN%TYPE;
    cCodLocalOri CA_PED_ORIGEN_CANJ.COD_LOCAL_ORIGEN%TYPE;
    cSecPedOri CA_PED_ORIGEN_CANJ.SEC_PED_ORIGEN%TYPE;
    cCodProdOri CA_PED_ORIGEN_CANJ.Cod_Prod_Origen%TYPE;

   CURSOR PEDCANJE IS
     SELECT G.COD_GRUPO_CIA,G.COD_LOCAL_CANJ,G.NUM_PED_CANJ,G.DNI_CLI
          FROM CA_PED_ORIGEN_CANJ G
          WHERE G.ESTADO='A'
          AND G.COD_GRUPO_CIA=cCodGrupoCia_in
          AND G.COD_LOCAL_ORIGEN=cCodLocal_in
          AND G.NUM_PED_ORIGEN=cNumPedVta_in
          AND G.DNI_CLI=cDni_in;
    BEGIN

    --si el pedido tiene canje en local
    SELECT COUNT(*) INTO ExistCanje
    FROM CA_PED_ORIGEN_CANJ A
    WHERE  A.ESTADO='A'
    AND A.DNI_CLI=cDni_in
    AND A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL_ORIGEN=cCodLocal_in
    AND A.NUM_PED_ORIGEN=cNumPedVta_in;


      --Verifica que no existe historico generado por el canje
      SELECT COUNT(*) INTO ExistHist
      FROM CA_HIS_CLI_PED F
      WHERE F.ESTADO='A'
      AND EXISTS (SELECT 1
                  FROM CA_CANJ_CLI_PED I
                  WHERE I.COD_GRUPO_CIA=cCodGrupoCia_in
                  AND I.COD_LOCAL=cCodLocal_in
                  AND I.NUM_PED_VTA=cNumPedVta_in
                  AND I.COD_GRUPO_CIA=F.COD_GRUPO_CIA
                  AND I.COD_LOCAL=F.COD_LOCAL
                  AND I.NUM_PED_VTA=F.NUM_PED_VTA);


     TIPO:= PTOVENTA_CAJ_ANUL.CAJ_TIPO_PED_FIDELIZADO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);

      IF (TIPO='A') THEN  --si es acumulado
          IF(ExistCanje>0)THEN
            RAISE_APPLICATION_ERROR(-20007,'No se puede anular el pedido, ya que es parte de un canje.');
          END IF;
      ELSIF (TIPO='C') THEN --Si es canje
       --PTOVENTA_CAJ_ANUL.CAJ_ANUL_CANJE(cCodGrupoCia_in,cCodLocal_in,vIdUsu_in,cNumPedVta_in,cDni_in,cIndLocal_in);
        IF(ExistHist>0)THEN
          FOR ROWNUM IN PEDCANJE
          LOOP
            IF(ROWNUM.NUM_PED_CANJ<>cNumPedVta_in)THEN
                     RAISE_APPLICATION_ERROR(-20006,'No se puede anular, ya que hay canjes asociados');
            END IF;
          END
          LOOP;
      END IF;

      ELSE
       RAISE_APPLICATION_ERROR(-20004,'Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.');
      END IF;


  END;

   PROCEDURE CAJ_ANULAR_PEDIDO_BTL_MF(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   IN CHAR,
                              cTipComp_in     IN CHAR,
                              cNumComp_in     IN CHAR,
                              nMontoVta_in    IN NUMBER,
                              nNumCajaPago_in IN NUMBER,
                              vIdUsu_in       IN VARCHAR2,
                              nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                              cMotivoAnulacion_in IN VARCHAR2,
                              cValMints_in  IN CHAR DEFAULT 'S' )
  AS
    v_cNumPedAux VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;

    v_cCajaAbierta VTA_CAJA_PAGO.IND_CAJA_ABIERTA%TYPE;
    v_cSecUsuLocal VTA_CAJA_PAGO.SEC_USU_LOCAL%TYPE;
    v_cSecMovCaja_in VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;

    v_nNumPedNegativo VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    --agregado para validar la fecha y el secuencial de movimiento de caja diario
    v_cIndCorrecto CHAR(1):='N';
    v_dFecPedVta VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;

    v_nValBruto VTA_PEDIDO_VTA_CAB.VAL_BRUTO_PED_VTA%TYPE;
    v_nValNeto VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nValRedondeo VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_nValIgv VTA_PEDIDO_VTA_CAB.VAL_IGV_PED_VTA%TYPE;
    v_nValDscto VTA_PEDIDO_VTA_CAB.VAL_DCTO_PED_VTA%TYPE;
    v_nCantItems VTA_PEDIDO_VTA_CAB.CANT_ITEMS_PED_VTA %TYPE;


    --DUBILLUZ 28.05.2009
    v_dFecPedVtaOrigen VTA_PEDIDO_VTA_CAB.FEC_PED_VTA%TYPE;
    vNumPedOrigen VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
    vAhorroPedido number;
  BEGIN
    --VERIFICA SI EL PEDIDO EXISTE Y NO HAYA SIDO ANULADO
    IF cTipComp_in = '%' AND cNumComp_in = '%' THEN
      CAJ_VERIFICA_PEDIDO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,nMontoVta_in, nIndReclamoNavsat_in, 'S',cValMints_in);
    ELSE
      v_cNumPedAux:=CAJ_VERIFICA_COMPROBANTE(cCodGrupoCia_in,cCodLocal_in,cTipComp_in, cNumComp_in,nMontoVta_in, nIndReclamoNavsat_in);
    END IF;
    -- VERIFICA SI LA CAJA NO  HA SIDO CERRADA Y AUN PERMANECE ABIERTA
    SELECT SEC_USU_LOCAL, SEC_MOV_CAJA INTO v_cSecUsuLocal,v_cSecMovCaja_in
    FROM VTA_CAJA_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_CAJA_PAGO = nNumCajaPago_in;
    v_cCajaAbierta:= Ptoventa_Caj.CAJ_IND_CAJA_ABIERTA_FORUPDATE(cCodGrupoCia_in,cCodLocal_in, v_cSecUsuLocal, v_cSecMovCaja_in);
    IF v_cCajaAbierta='N' THEN
      RAISE_APPLICATION_ERROR(-20021,'LA CAJA NO SE ENCUENTRA ABIERTA.');
    ELSIF v_cCajaAbierta='S' THEN
      --OBTENER EL PEDIDO NEGATIVO
      v_nNumPedNegativo:=CAJ_GET_PEDIDO_NEGATIVO_CAB(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,v_cSecMovCaja_in,vIdUsu_in);
      --ANULAR LOS COMPROBANTES Y ASIGNARLES EL PEDIDO NEGATIVO
      CAJ_ANULAR_COMPROBANTES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cTipComp_in, cNumComp_in, v_nNumPedNegativo ,v_cSecMovCaja_in,vIdUsu_in,nIndReclamoNavsat_in,cMotivoAnulacion_in);
      --ANULAR CABECERA
      CAJ_ANULAR_CABECERA(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,vIdUsu_in,cMotivoAnulacion_in);
      --ANULAR LA FORMA DE PAGO CON MONTOS NEGATIVOS
      CAJ_ANULAR_FORMA_PAGO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cTipComp_in, cNumComp_in,v_nNumPedNegativo,vIdUsu_in);

      SELECT VAL_BRUTO_PED_VTA*-1,VAL_NETO_PED_VTA*-1,VAL_REDONDEO_PED_VTA*-1,
      VAL_IGV_PED_VTA*-1,VAL_DCTO_PED_VTA*-1,CANT_ITEMS_PED_VTA
      INTO v_nValBruto,v_nValNeto,v_nValRedondeo,
      v_nValIgv,v_nValDscto,v_nCantItems
      FROM VTA_PEDIDO_VTA_CAB
      WHERE cod_grupo_cia = cCodGrupoCia_in
      AND   cod_local     = cCodLocal_in
      AND   NUM_PED_VTA   = cNumPedVta_in;


      UPDATE VTA_PEDIDO_VTA_CAB cab
      SET USU_MOD_PED_VTA_CAB = vIdUsu_in,
          FEC_MOD_PED_VTA_CAB = SYSDATE,
          VAL_BRUTO_PED_VTA =  v_nValBruto,
          VAL_NETO_PED_VTA =  v_nValNeto,
          VAL_REDONDEO_PED_VTA =  v_nValRedondeo,
          VAL_IGV_PED_VTA  =  v_nValIgv,
          VAL_DCTO_PED_VTA =  v_nValDscto,
          CANT_ITEMS_PED_VTA = v_nCantItems
      WHERE cab.cod_grupo_cia = cCodGrupoCia_in
      AND   cab.cod_local     = cCodLocal_in
      AND   CAB.NUM_PED_VTA   = v_nNumPedNegativo;


      /**OBTENIENDO LA FECHA DEL REGISTRO DEL PEDIDO NEGATIVO**/
      SELECT cab.fec_ped_vta,cab.num_ped_vta_origen
      into   v_dFecPedVta,vNumPedOrigen
      FROM  VTA_PEDIDO_VTA_CAB cab
      WHERE cab.cod_grupo_cia = cCodGrupoCia_in
      AND   cab.cod_local     = cCodLocal_in
      AND   CAB.NUM_PED_VTA   = v_nNumPedNegativo;

      /**VERIFICAR SI EL PEDIDO GENERADO CORRESPONDIENTE ESTA CON LA FECHA DE APERTURA **/
      BEGIN
        SELECT 'S' INTO v_cIndCorrecto
        FROM CE_MOV_CAJA C
        WHERE TRUNC(C.Fec_Dia_Vta) = TRUNC(v_dFecPedVta)
        AND   C.SEC_MOV_CAJA       = v_cSecMovCaja_in;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_cIndCorrecto := 'N';
        RAISE_APPLICATION_ERROR(-20028,'FECHA DE ANULACION INVALIDA YA PASO LAS 24:00 HORAS.');
      END;

        --Inserta en la tabla de ahorro x DNI para validar el maximo Ahorro en el dia o Semana
        --dubilluz 28.05.2009
      SELECT cab.fec_ped_vta into v_dFecPedVtaOrigen
      FROM  VTA_PEDIDO_VTA_CAB cab
      WHERE cab.cod_grupo_cia = cCodGrupoCia_in
      AND   cab.cod_local     = cCodLocal_in
      AND   CAB.NUM_PED_VTA   = vNumPedOrigen;
       if trunc(v_dFecPedVtaOrigen) = trunc(sysdate) then

         select NVL(SUM(d.AHORRO),0)
         into   vAhorroPedido
         from   vta_pedido_vta_det d,
                vta_campana_cupon cup -- KMONCADA 16.04.2015 SE CONSIDERA SOLO LAS CAMPAÑAS QUE SUMEN EN AHORRO DE FIDELIZADO
         where  cup.cod_grupo_cia = d.cod_grupo_cia
         and    cup.cod_camp_cupon = d.cod_camp_cupon
         and    d.cod_grupo_cia = cCodGrupoCia_in
         and    d.cod_local = cCodLocal_in
         and    d.num_ped_vta = vNumPedOrigen
         and    NVL(cup.flg_acumula_ahorro_dni,'S') = 'S';

        if vAhorroPedido > 0 then
             insert into vta_ped_dcto_cli_aux
            (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,VAL_DCTO_VTA,DNI_CLIENTE,FEC_CREA_PED_VTA_CAB)
            select c.cod_grupo_cia,c.cod_local,v_nNumPedNegativo,sum(d.ahorro)*-1,t.dni_cli,v_dFecPedVta
            from   vta_pedido_vta_det d,
                   vta_pedido_vta_cab c,
                   fid_tarjeta_pedido t,
                   vta_campana_cupon cup -- KMONCADA 16.04.2015 SE CONSIDERA SOLO LAS CAMPAÑAS QUE SUMEN EN AHORRO DE FIDELIZADO
            where  c.cod_grupo_cia = cCodGrupoCia_in
            and    c.cod_local = cCodLocal_in
            and    c.num_ped_vta =  vNumPedOrigen
            and    c.cod_grupo_cia = d.cod_grupo_cia
            and    c.cod_local = d.cod_local
            and    c.num_ped_vta = d.num_ped_vta
            and    c.cod_grupo_cia = t.cod_grupo_cia
            and    c.cod_local = t.cod_local
            and    c.num_ped_vta = t.num_pedido
            AND    cup.cod_grupo_cia = d.cod_grupo_cia
            and    cup.cod_camp_cupon = d.cod_camp_cupon
            and    NVL(cup.flg_acumula_ahorro_dni,'S') = 'S'
            group by c.cod_grupo_cia,c.cod_local,v_nNumPedNegativo,t.dni_cli,v_dFecPedVta;
        end if;

        end if;

      /*** FIN VALIDACION DE FECHA DE PEDIDO ELIMINADO***/
      
      --- hace update de cabecera igual a suma de detalle ---
      --  dubilluz 17.07.2015
          update VTA_PEDIDO_VTA_CAB c
             set (ahorro_camp,
                  ahorro_puntos,
                  AHORRO_PACK,
                  PTOS_AHORRO_PACK,
                  ahorro_total,
                  ptos_ahorro_camp) =
                 (select sum(nvl(d.ahorro_camp, 0)),
                         sum(nvl(d.ahorro_puntos, 0)),
                         sum(nvl(d.ahorro_pack, 0)),
                         sum(nvl(d.ptos_ahorro_pack, 0)),
                         sum(nvl(d.ahorro, 0)),
                         --sum(nvl(d.ahorro_camp, 0)) + sum(nvl(d.ahorro_puntos, 0)),
                         sum(nvl(d.ahorro_camp * 100, 0))
                    from vta_pedido_vta_det d
                   where d.cod_grupo_cia = c.cod_grupo_cia
                     and d.cod_local = c.cod_local
                     and d.num_ped_vta = c.num_ped_vta)
           where c.cod_grupo_cia = cCodGrupoCia_in
             and c.cod_local = cCodLocal_in
             and c.num_ped_vta = v_nNumPedNegativo;
      --  dubilluz 17.07.2015      
      --- --- 
      
    ELSE
      RAISE_APPLICATION_ERROR(-20022,'HA OCURRIDO UN ERROR DESCONOCIDO.');
    END IF;
  END;
  /* ********************************************************************************************** */

FUNCTION CAJ_LISTA_MOTIVO_NC(cCodMaestro_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT '0Ã--SELECCIONAR--' -- LTAVARA 15.10.2014
      FROM DUAL
      UNION
		  SELECT VALOR1    || 'Ã' ||
             DESCRIPCION
      FROM   MAESTRO_DETALLE
      WHERE  COD_MAESTRO   = cCodMaestro_in ;
      RETURN curVta;
  END;

  /****************************************************************************************************/

  PROCEDURE CAJ_ACTUALIZA_STK_PROD_COMP(cCodGrupoCia_in      IN CHAR,
                                           cCodLocal_in         IN CHAR,
                                           cNumPedVta_in        IN CHAR,
                                           cNumVtaAnt_in       IN CHAR,
                                           cTipDocKardex_in    IN CHAR,
                                           cCodNumeraKardex_in IN CHAR,
                                           cUsuModProdLocal_in IN CHAR)
  IS

--CURSOR PRODUCTOS FINALES
         	CURSOR prod_final_Kardex IS
         SELECT VTA_DET.COD_PROD,
                SUM(VTA_DET.CANT_FRAC_LOCAL) CANT_ATENDIDA,
                PROD_LOCAL.STK_FISICO,
				 	      PROD_LOCAL.VAL_FRAC_LOCAL,
				 	      PROD_LOCAL.UNID_VTA,
                 VTA_DET.SEC_PED_VTA_DET
				 FROM   VTA_PEDIDO_VTA_DET VTA_DET,
					      LGT_PROD_LOCAL PROD_LOCAL
			   WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
				 AND    VTA_DET.COD_LOCAL     = cCodLocal_in
				 AND	  VTA_DET.NUM_PED_VTA   = cNumPedVta_in
				 AND	  VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
				 AND	  VTA_DET.COD_LOCAL     = PROD_LOCAL.COD_LOCAL
				 AND	  VTA_DET.COD_PROD      = PROD_LOCAL.COD_PROD
          AND  EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND VTA_DET.COD_PROD = PP.COD_PROD
                                                                    AND VTA_DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
				 GROUP BY VTA_DET.COD_PROD,
					   	    PROD_LOCAL.VAL_FRAC_LOCAL,
					   	    PROD_LOCAL.UNID_VTA,
						      PROD_LOCAL.STK_FISICO,
                  VTA_DET.SEC_PED_VTA_DET;


  BEGIN

       --INSERTA KARDEX ALTA Y BAJA PRODUCTOS FINALES
        FOR productos_rec IN prod_final_Kardex
       LOOP

            --GRABAR KARDEX   DEVOLUCION_VENTA
             Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
                                             cCodLocal_in,
                                             productos_rec.COD_PROD,
                                             DEVOLUCION_VENTA,
                                             cTipDocKardex_in,
                                             cNumPedVta_in,
                                             productos_rec.STK_FISICO,
                                            (productos_rec.CANT_ATENDIDA * -1),--ACA LA CANTIDAD VIENE NEGATIVA ASI Q * -1 SE VUELVE POSITIVA
                                             productos_rec.VAL_FRAC_LOCAL,
                                             productos_rec.UNID_VTA,
                                             cUsuModProdLocal_in,
                                             cCodNumeraKardex_in);

       --GRABAR KARDEX  	BAJA_ANUL_VTA_PROD
             Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
                                             cCodLocal_in,
                                             productos_rec.COD_PROD,
                                             BAJA_ANUL_VTA_PROD,
                                             cTipDocKardex_in,
                                             cNumPedVta_in,
                                             (productos_rec.STK_FISICO +  (productos_rec.CANT_ATENDIDA * -1)),  --PARA QUE NO INGRESE EL STOCK EN CERO
                                              productos_rec.CANT_ATENDIDA,
                                             productos_rec.VAL_FRAC_LOCAL,
                                             productos_rec.UNID_VTA,
                                             cUsuModProdLocal_in,
                                             cCodNumeraKardex_in);


             --ACTUALIZA
             UPDATE VTA_PEDIDO_VTA_DET SET FEC_MOD_PED_VTA_DET = SYSDATE,
                    SEC_COMP_PAGO_ORIGEN = (SELECT C.SEC_COMP_PAGO
                                            FROM   VTA_PEDIDO_VTA_DET C
                                            WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND    C.COD_LOCAL = cCodLocal_in
                                            AND    C.NUM_PED_VTA = cNumVtaAnt_in
                                            AND    C.COD_PROD = productos_rec.COD_PROD
                                            AND    C.SEC_PED_VTA_DET = productos_rec.Sec_Ped_Vta_Det)
             WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND   COD_LOCAL = cCodLocal_in
             AND   NUM_PED_VTA = cNumPedVta_in
             AND   COD_PROD = productos_rec.COD_PROD
             AND   Sec_Ped_Vta_Det = productos_rec.Sec_Ped_Vta_Det;

       END LOOP;
  END;

  /************************************************************************************************/

  PROCEDURE CAJ_ANULAR_COMPR_TICO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR,
                                   cNumPedNeg_in   IN CHAR,
                                   cSecCompPago_in IN CHAR,
                                   cSecMovCaja_in  IN CHAR,
                                   vIdUsu_in       IN CHAR,
                                   nIndReclamoNavsat_in IN CHAR DEFAULT 'N',
                                   cMotivoAnulacion_in IN VARCHAR2)
  AS

    CURSOR curProd IS
           SELECT DET.SEC_PED_VTA_DET,
                  DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.VAL_PREC_VTA,
                  DET.VAL_PREC_TOTAL,
                  DET.PORC_DCTO_1,
                  DET.PORC_DCTO_2,
                  DET.PORC_DCTO_3,
                  DET.PORC_DCTO_TOTAL,
                  DET.EST_PED_VTA_DET,
                  DET.VAL_TOTAL_BONO,
                  DET.VAL_FRAC,
                  DET.SEC_USU_LOCAL,
                  DET.VAL_PREC_LISTA,
                  DET.VAL_IGV,
                  DET.UNID_VTA,
                  DET.IND_EXONERADO_IGV,
                  DET.SEC_GRUPO_IMPR,
                  IND_ORIGEN_PROD,
                  DET.PORC_ZAN,
                   NVL(DET.COD_GRUPO_REP,' ') COD_GRUPO_REP,
                   NVL(DET.COD_GRUPO_REP_EDMUNDO,' ') COD_GRUPO_REP_EDMUNDO,
                   DET.COD_PROM,
                  DET.COD_CAMP_CUPON,
                  DET.ahorro_conv,
                  DET.num_comp_pago,
                  DET.sec_comp_pago_benef,
                  DET.sec_comp_pago_empre,
                  --KMONCADA 10.09.2015 REGISTRO DE ANULACION EN CASO DE PTOS
                  (DET.AHORRO * (-1)) AHORRO,
                  DET.IND_PROD_MAS_1,
                  DET.COD_PROD_PUNTOS,
                  DET.IND_BONIFICADO,
                  DET.FACTOR_PUNTOS,
                  (DET.CTD_PUNTOS_ACUM * (-1)) CTD_PUNTOS_ACUM,
                  (DET.AHORRO_PUNTOS * (-1)) AHORRO_PUNTOS,
                  (DET.AHORRO_CAMP * (-1)) AHORRO_CAMP,
                  (DET.PTOS_AHORRO * (-1)) PTOS_AHORRO,
                  (DET.AHORRO_PACK * (-1)) AHORRO_PACK,
                  (DET.PTOS_AHORRO_PACK * (-1)) PTOS_AHORRO_PACK                  

           FROM   VTA_PEDIDO_VTA_DET DET
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPedVta_in
             AND EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND PP.COD_PROD = DET.COD_PROD
                                                                    AND DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
           AND    DET.SEC_COMP_PAGO = cSecCompPago_in;
    v_rCurProd curProd%ROWTYPE;

    i INTEGER:=0;

    v_nFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nComprometido LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFrac LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_vDescUnidVta LGT_PROD_LOCAL.UNID_VTA%TYPE;
    v_nCant LGT_NOTA_ES_DET.CANT_MOV%TYPE;

  BEGIN

    DBMS_OUTPUT.PUT_LINE('There are 0 ' || cNumPedNeg_in);
    --ANULAR COMPROBANTE
    UPDATE VTA_COMP_PAGO SET USU_MOD_COMP_PAGO = vIdUsu_in,  FEC_MOD_COMP_PAGO = SYSDATE,
           IND_COMP_ANUL = 'S',
           FEC_ANUL_COMP_PAGO = SYSDATE,
           NUM_PEDIDO_ANUL   = cNumPedNeg_in,
           SEC_MOV_CAJA_ANUL = cSecMovCaja_in,
           IND_RECLAMO_NAVSAT = nIndReclamoNavsat_in,
           MOTIVO_ANULACION= cMotivoAnulacion_in
           --FECHA_ANULACION=SYSDATE --JCORTEZ 06.07.09 Fecha de generacion del archivo de anulacion
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    NUM_PED_VTA = cNumPedVta_in
    AND    SEC_COMP_PAGO = cSecCompPago_in;

    SELECT COUNT(*)
    INTO   i
    FROM   VTA_PEDIDO_VTA_DET
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    NUM_PEd_VTA = cNumPedNeg_in;

    FOR v_rCurProd IN curProd
    LOOP
        i:=i+1;
        --VERIFICA SI LA FRACCION PERMITE LA ANULACION
        SELECT STK_FISICO,STK_FISICO,VAL_FRAC_LOCAL,UNID_VTA
        INTO   v_nFisico,v_nComprometido,v_nValFrac, v_vDescUnidVta
        FROM   LGT_PROD_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in
        AND    COD_PROD = v_rCurProd.COD_PROD FOR UPDATE;

        IF MOD(v_rCurProd.CANT_ATENDIDA*v_nValFrac,v_rCurProd.VAL_FRAC) = 0 THEN
          v_nCant := ((v_rCurProd.CANT_ATENDIDA*v_nValFrac)/v_rCurProd.VAL_FRAC);
        ELSE
          RAISE_APPLICATION_ERROR(-20002,'Error al anular. Prod:'||v_rCurProd.COD_PROD||',Cant:'||v_rCurProd.CANT_ATENDIDA||' ,Frac:'||v_rCurProd.VAL_FRAC||' ,Frac. Act:'||v_nValFrac);
        END IF;

        --ANULAR EL DET DE PEDIDO, SEGUN EL COMPROBANTE
        UPDATE VTA_PEDIDO_VTA_DET SET USU_MOD_PED_VTA_DET = vIdUsu_in, FEC_MOD_PED_VTA_DET = SYSDATE,
               EST_PED_VTA_DET = 'N'
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in
        AND    NUM_PED_VTA = cNumPedVta_in
        AND    SEC_PED_VTA_DET = v_rCurProd.SEC_PED_VTA_DET
        AND    SEC_COMP_PAGO = cSecCompPago_in;

        --INSERT DETALLE NEGATIVO
        dbms_output.put_line('CAJ_ANULAR_COMPROBANTE: INSERT VTA_PEDIDO_VTA_DET:');
        INSERT INTO VTA_PEDIDO_VTA_DET(COD_GRUPO_CIA,
                                       COD_LOCAL,
                                       NUM_PED_VTA,
                                       SEC_PED_VTA_DET,
                                       COD_PROD,
                                       CANT_ATENDIDA,
                                       VAL_PREC_VTA,
                                       VAL_PREC_TOTAL,
                                       PORC_DCTO_1,
                                       PORC_DCTO_2,
                                       PORC_DCTO_3,
                                       PORC_DCTO_TOTAL,
                                       EST_PED_VTA_DET, --
                                       VAL_TOTAL_BONO,
                                       VAL_FRAC,
                                       SEC_USU_LOCAL,
                                       USU_CREA_PED_VTA_DET,
                                       VAL_PREC_LISTA,
                                       VAL_IGV,
                                       UNID_VTA,
                                       IND_EXONERADO_IGV,
                                       IND_ORIGEN_PROD,
                                       VAL_FRAC_LOCAL,
                                       CANT_FRAC_LOCAL,
                                       COD_GRUPO_REP,COD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09
                                       PORC_ZAN,       -- 2009-11-09 JOLIVA
                                       COD_PROM,       -- ASOSA, 21.05.2010
                                       COD_CAMP_CUPON,  -- ASOSA, 21.05.2010
                                       ahorro_conv,
                                       num_comp_pago,
                                       sec_comp_pago_benef,
                                       sec_comp_pago_empre,
                                       --KMONCADA 10.09.2015 REGISTRO DE ANULACION EN CASO DE PTOS
                                       AHORRO,
                                       IND_PROD_MAS_1,
                                       COD_PROD_PUNTOS,
                                       IND_BONIFICADO,
                                       FACTOR_PUNTOS,
                                       CTD_PUNTOS_ACUM,
                                       AHORRO_PUNTOS,
                                       AHORRO_CAMP,
                                       PTOS_AHORRO,
                                       AHORRO_PACK,
                                       PTOS_AHORRO_PACK
                                       )
                                VALUES(cCodGrupoCia_in,
                                       cCodLocal_in,
                                       cNumPedNeg_in,
                                       i,
                                       v_rCurProd.COD_PROD,
                                       v_rCurProd.CANT_ATENDIDA*-1,
                                       v_rCurProd.VAL_PREC_VTA,
                                       v_rCurProd.VAL_PREC_TOTAL*-1,
                                       v_rCurProd.PORC_DCTO_1,
                                       v_rCurProd.PORC_DCTO_2,
                                       v_rCurProd.PORC_DCTO_3,
                                       v_rCurProd.PORC_DCTO_TOTAL,
                                       'N',
                                       v_rCurProd.VAL_TOTAL_BONO*-1,
                                       v_rCurProd.VAL_FRAC,
                                       v_rCurProd.SEC_USU_LOCAL,
                                       vIdUsu_in,
                                       v_rCurProd.VAL_PREC_LISTA,
                                       v_rCurProd.VAL_IGV,
                                       v_rCurProd.UNID_VTA,
                                       v_rCurProd.IND_EXONERADO_IGV,
                                       v_rCurProd.IND_ORIGEN_PROD,
                                       v_nValFrac,
                                       v_nCant*-1,
                                       v_rCurProd.COD_GRUPO_REP,v_rCurProd.COD_GRUPO_REP_EDMUNDO,--JCORTEZ 19.10.09
                                       v_rCurProd.PORC_ZAN,        -- 2009-11-09 JOLIVA
                                       v_rCurProd.Cod_Prom,        --ASOSA, 21.05.2010
                                       v_rCurProd.Cod_Camp_Cupon,   --ASOSA, 21.05.2010
                                       v_rCurProd.ahorro_conv,
                                       v_rCurProd.num_comp_pago,
                                       v_rCurProd.sec_comp_pago_benef,
                                       v_rCurProd.sec_comp_pago_empre,
                                       --KMONCADA 10.09.2015 REGISTRO DE ANULACION EN CASO DE PTOS
                                       v_rCurProd.AHORRO,
                                       v_rCurProd.IND_PROD_MAS_1,
                                       v_rCurProd.COD_PROD_PUNTOS,
                                       v_rCurProd.IND_BONIFICADO,
                                       v_rCurProd.FACTOR_PUNTOS,
                                       v_rCurProd.CTD_PUNTOS_ACUM,
                                       v_rCurProd.AHORRO_PUNTOS,
                                       v_rCurProd.AHORRO_CAMP,
                                       v_rCurProd.PTOS_AHORRO,
                                       v_rCurProd.AHORRO_PACK,
                                       v_rCurProd.PTOS_AHORRO_PACK
                                       );


              --INSERTAR KARDEX DEVOLUCION_VENTA
      Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          v_rCurProd.COD_PROD,
                                          g_cCodMotKardex,
                                          '01',
                                          cNumPedNeg_in,
                                          v_nFisico,
                                          v_nCant,
                                          v_nValFrac,
                                          v_vDescUnidVta,
                                          vIdUsu_in,
                                          '016');

                 --INSERTAR KARDEX BAJA_ANUL_VTA_PROD
           Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          v_rCurProd.COD_PROD,
                                          BAJA_ANUL_VTA_PROD, --ASOSA - 17/10/2014 - PANHD
                                          '01',
                                          cNumPedNeg_in,
                                          (v_nFisico + v_nCant),
                                          (v_nCant * -1),
                                          v_nValFrac,
                                          v_vDescUnidVta,
                                          vIdUsu_in,
                                          '016');

    END LOOP;

    --ACTUALIZAR MONTOS EN LA CABECERA
    CAJ_ACTUALIZAR_MONTOS_CABECERA(cCodGrupoCia_in,cCodLocal_in,cNumPedNeg_in,cNumPedVta_in,cSecCompPago_in,vIdUsu_in);

  END;
  
  PROCEDURE VERIFICAR_DET_NC(cCodGrupoCia_in CHAR,
                            cCodLocal_in    CHAR,
                            cNumPedVta_in   CHAR)
  AS
  BEGIN

    for lista in (
        select d.cod_grupo_cia,d.cod_local,d.num_ped_vta,d.sec_ped_vta_det,
               d.sec_comp_pago,d.sec_comp_pago_origen,
               do.sec_comp_pago SEC_ORIGEN_nuevo/*, p.* */
        from   vta_pedido_vta_cab c,
               vta_comp_pago p,
               vta_pedido_vta_det d,
               vta_comp_pago po,
               vta_pedido_vta_det do
        where  c.cod_grupo_cia  = cCodGrupoCia_in
        and    c.cod_local      = cCodLocal_in
        and    c.num_ped_vta    = cNumPedVta_in
        and    p.tip_comp_pago  = '04'
        and    d.sec_comp_pago_origen is null
        and    c.cod_grupo_cia  = p.cod_grupo_cia
        and    c.cod_local      = p.cod_local
        and    c.num_ped_vta    = p.num_ped_vta
        and    c.cod_grupo_cia  = d.cod_grupo_cia
        and    c.cod_local      = d.cod_local
        and    c.num_ped_vta    = d.num_ped_vta
        and    po.cod_grupo_cia = c.cod_grupo_cia
        and    po.cod_local     = c.cod_local
        and    po.num_ped_vta   = c.num_ped_vta_origen
        and    do.cod_grupo_cia = po.cod_grupo_cia
        and    do.cod_local     = po.cod_local
        and    do.num_ped_vta   = po.num_ped_vta
        and    abs(p.val_neto_comp_pago+p.val_redondeo_comp_pago) = abs(po.val_neto_comp_pago+po.val_redondeo_comp_pago)
        and    d.cod_prod = do.cod_prod) loop
          
        update vta_pedido_vta_det d
        set    d.sec_comp_pago_origen = lista.sec_origen_nuevo
        where  d.cod_grupo_cia = lista.cod_grupo_cia
        and    d.cod_local = lista.cod_local
        and    d.num_ped_vta = lista.num_ped_vta
        and    d.sec_ped_vta_det = lista.sec_ped_vta_det;
    end loop;

  EXCEPTION 
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005,'Error actualizando el detale de las notas de credito'||sqlerrm);
  END;           
  
  PROCEDURE CAJ_ANULA_CUPONES(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                              cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                              cNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                              vIdUsu_in       IN VARCHAR2)
  AS
    CURSOR curCupones IS
    SELECT C.COD_GRUPO_CIA,TRIM(C.COD_CUPON) AS COD_CUPON
      FROM VTA_CAMP_PEDIDO_CUPON C,
           VTA_CAMPANA_CUPON CAMP,
           VTA_CUPON O
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_PED_VTA = cNumPedVta_in
            AND C.ESTADO = 'E'
            AND C.IND_IMPR = 'S'
            AND C.COD_GRUPO_CIA = O.COD_GRUPO_CIA
            AND C.COD_CUPON = O.COD_CUPON
            AND O.ESTADO = 'A'
            AND CAMP.COD_GRUPO_CIA = C.COD_GRUPO_CIA
            AND CAMP.COD_CAMP_CUPON = C.COD_CAMP_CUPON
            AND CAMP.IND_MULTIUSO = 'N';--NO SE ANULAN LOS CUPONES MULTIUSO
  BEGIN
    --30/07/2008 ERIOS Se asume que se anulo el pedido completo
    -- v_cIndLinea := PTOVENTA_CAJ.VERIFICA_CONN_MATRIZ;
    FOR cupon IN curCupones
      LOOP
        --Verifica conn matriz
/*        IF v_cIndLinea = 'S' THEN
          EXECUTE IMMEDIATE 'BEGIN PTOVENTA.PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON@XE_000(:1,:2,:3); END;'
            USING cCodGrupoCia_in,TRIM(cupon.COD_CUPON), IN OUT v_cEstado2;
          \*IF v_cEstado2 <> 'A' THEN
            RAISE_APPLICATION_ERROR(-20016,'Cupon usado: '||TRIM(cupon.COD_CUPON));
          END IF;  *\
        END IF;*/

      UPDATE VTA_CUPON
      SET ESTADO = 'N',
          FEC_PROCESA_MATRIZ = NULL,
          USU_PROCESA_MATRIZ = NULL,
          FEC_MOD_CUP_CAB = SYSDATE,
          USU_MOD_CUP_CAB = vIdUsu_in
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_CUPON = TRIM(cupon.COD_CUPON);
    END LOOP;
  END;
  
  FUNCTION F_IND_ANULA_CON_CUPONES_USADOS
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    SELECT TRIM(A.LLAVE_TAB_GRAL) 
    INTO   vIndicador
    FROM   PBL_TAB_GRAL A 
    WHERE  A.ID_TAB_GRAL = TAB_IND_ANULA_CON_CUPON_USADOS;
    RETURN vIndicador;
  EXCEPTION 
    WHEN OTHERS THEN
      RETURN 'S';
  END;

  FUNCTION F_GET_SERIE_AUX_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN CHAR IS
    v_cSerieAux CHAR(3);
  BEGIN
    SELECT NVL(NUM_SERIE_AUX,COD_LOCAL)
	  INTO v_cSerieAux
	FROM PTOVENTA.AUX_MAE_LOCAL
	WHERE COD_LOCAL_SAP = cCodLocal_in;
	
    RETURN v_cSerieAux;
  END;
END;
/
