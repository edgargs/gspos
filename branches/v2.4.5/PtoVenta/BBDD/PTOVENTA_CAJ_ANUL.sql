--------------------------------------------------------
--  DDL for Package PTOVENTA_CAJ_ANUL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CAJ_ANUL" AS

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
  FUNCTION CAJ_LISTA_CABECERA_PEDIDO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedVta_in IN CHAR,cNumCompPag IN CHAR) RETURN FarmaCursor;

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
                                    nIndReclamoNavsat_in IN CHAR DEFAULT 'N')
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
PROCEDURE CAJ_AGREGAR_DET_NC(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in IN CHAR,
                                       cNumVtaAnt_in IN CHAR,
                                       cNumVta_in IN CHAR,
                                       -- estos valores no se van a usar
                                       cCodProd_in IN CHAR,nCantProd_in IN NUMBER,nTotal_in IN NUMBER,vIdUsu_in IN VARCHAR2,nSecDetPed_in IN NUMBER
                                       ,
                                       nNumCajaPago_in IN NUMBER
                                       );

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

END;

/
