CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_INV" AS

  TYPE FarmaCursor IS REF CURSOR;

  COD_NUMERA_SEC_MOV_AJUSTE_KARD            PBL_NUMERA.COD_NUMERA%TYPE := '017';
  COD_NUMERA_SEC_KARDEX                       PBL_NUMERA.COD_NUMERA%TYPE := '016';
  COD_GRU_REP_INSUMO CHAR(3) :='010' ;  --RHERRERA 06.08.2014
  MOT_CONSUMO_INTERNO   CHAR(3)     :='522' ; --RHERRERA 06.08.2014
  MOT_ANU_SALID_INTERNO   CHAR(3)     :='523' ; --RHERRERA 12.08.2014
  VTA_EMPRESA CHAR(02):='03' ;        --RHERRERA 13.08.2014
  USU_CONSUMO_AUTO      VARCHAR2(15):='CONSUMO_AUTO'; --RHERRERA 29.09.2014 USUARIO CONSUMO AUTOMATICO


  g_cTipoNotaEntrada LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '01';
  g_cTipoNotaSalida LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '02';
  g_cTipoNotaRecepcion LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '03';

  g_cTipCompGuia LGT_KARDEX.Tip_Comp_Pago%TYPE := '03';
  g_cTipDocKdxGuiaES LGT_KARDEX.Tip_Comp_Pago%TYPE := '02';
  g_cTipCompNumEntrega LGT_KARDEX.Tip_Comp_Pago%TYPE := '05';

  g_cTipoOrigenLocal CHAR(2):= '01';
  g_cTipoOrigenMatriz CHAR(2):= '02';
  g_cTipoOrigenProveedor CHAR(2):= '03';
  g_cTipoOrigenCompetencia CHAR(2):= '04';
  g_cTipoOrigenRecetario CHAR(2):= '05';
  g_cTipoOrigenGuiaSalida CHAR(2):= '06';

  g_cNumNotaEs PBL_NUMERA.COD_NUMERA%TYPE := '011';
  g_cNumPedRep PBL_NUMERA.COD_NUMERA%TYPE := '014';
  g_cNumExcProd PBL_NUMERA.COD_NUMERA%TYPE := '023';

  g_cTipoLocalVenta PBL_LOCAL.TIP_LOCAL%TYPE := 'V';
  g_cTipoLocalMatriz PBL_LOCAL.TIP_LOCAL%TYPE := 'A';

  g_cTipoImpresoraGuia VTA_IMPR_LOCAL.TIP_COMP%TYPE := '03';


  g_nTipoFiltroPrincAct NUMBER(1) := 1;
  g_nTipoFiltroAccTerap NUMBER(1) := 2;
  g_nTipoFiltroLab NUMBER(1) := 3;

  g_cTipoMotKardexTransfInterna LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '205';

  g_vMotivoAjuste PBL_TAB_GRAL.COD_TAB_GRAL%TYPE := 'MOTIVOS DE AJUSTE';
  g_cMotivoAjuste LGT_MOT_KARDEX.TIP_MOV_KARDEX%TYPE := '4';
  g_vCodPtoVenta PBL_TAB_GRAL.COD_APL%TYPE := 'PTOVENTA';
  g_vCodPtoVenta2 PBL_TAB_GRAL.COD_APL%TYPE := 'PTO_VENTA';
  g_vGralCompetencia PBL_TAB_GRAL.COD_TAB_GRAL%TYPE := 'COMPETENCIA';
  g_vGralProveedor PBL_TAB_GRAL.COD_TAB_GRAL%TYPE := 'PROVEEDOR';
  g_vGralMotivoSalida PBL_TAB_GRAL.COD_TAB_GRAL%TYPE := 'MOT_NOTA_SALIDA';
  g_vGralOrigenNotaEs PBL_TAB_GRAL.COD_TAB_GRAL%TYPE := 'TIPO_ORIGEN_NOTA_ES';
  g_vGralDestinoNotaEs PBL_TAB_GRAL.COD_TAB_GRAL%TYPE := 'TIPO_DESTINO_NOTA_ES';

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';
  g_cCodDelivery PBL_LOCAL.COD_LOCAL%TYPE := '999';
  g_cCodDeliveryInst PBL_LOCAL.COD_LOCAL%TYPE := '998';

  g_cTipoMotKardexAjusteGuia LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '008';
  g_cMotKardexIngMatriz LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '101';

  TIPO_MOV_SOBRANTE CHAR(3) := '112';                      --ASOSA - 15/08/2014

  INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';

  --LISTA GUIA INGRESOS

  --Descripcion: Obtiene el listado de las guias de ingreso de un local.
  --Fecha       Usuario		Comentario
  --14/02/2006  ERIOS     Creación
  --19/07/2006  ERIOS     Modificación: Rango y Filtro
  --31/01/2007  LMESIA    Modificación
  FUNCTION INV_LISTA_GUIA_INGRESO(cGrupoCia_in IN CHAR,
                                  cCia_in      IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFechaIni_in IN VARCHAR2,
                                  vFechaFin_in IN VARCHAR2,
                                  vFiltro_in   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene el origen de una guia de ingreso de un local.
  --Fecha       Usuario		Comentario
  --14/02/2006  ERIOS     Creación
  FUNCTION INV_GET_ORIGEN_NOTA_E(cTipOrigen_in IN CHAR, cCodOrigen_in IN CHAR, cGrupoCia_in IN CHAR, cCia_in IN CHAR, cNumNotaEs_in IN CHAR DEFAULT NULL) RETURN VARCHAR2;

  --Descripcion: Obtiene el motivo de una guia de ingreso de un local.
  --Fecha       Usuario		Comentario
  --14/02/2006  ERIOS     Creación
  FUNCTION INV_GET_MOTIVO_NOTA_E(cCodMotivo_in IN CHAR) RETURN VARCHAR2;

  --LISTA PRODUCTOS

  --Descripcion: Obtiene el listado de las productos de un local.
  --Fecha       Usuario		Comentario
  --14/02/2006  ERIOS     Creación
  FUNCTION INV_LISTA_PROD_GUIA_INGRESO(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene los tipos de origenes de guia de ingreso.
  --Fecha       Usuario		Comentario
  --15/02/2006  ERIOS     Creación
  --28/06/2006  ERIOS     Modificación
  FUNCTION INV_GET_TIPO_ORIGEN_GUIA_E RETURN FarmaCursor;

  --Descripcion: Obtiene los tipos de Documentos.
  --Fecha       Usuario		Comentario
  --15/02/2006  ERIOS     Creacion
  --25/04/2014  ERIOS     Se agrega el parametro vTipo_in
  FUNCTION INV_GET_TIPO_DOCUMENTO(cGrupoCia_in IN CHAR, vTipo_in IN VARCHAR2 DEFAULT 'INV') RETURN FarmaCursor;

  --AGREGAR GUIA INGRESO

  --Descripcion: Agrega la cabecera de una guia de ingreso.
  --Fecha       Usuario	  Comentario
  --16/02/2006  ERIOS     Creación
  FUNCTION INV_AGREGA_CAB_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                        vFechaGuia_in IN VARCHAR2,cTipDoc_in IN CHAR,cNumDoc_in IN CHAR,cTipOrigen_in IN CHAR, vCodOrigen_in IN VARCHAR2,nCantItems_in IN NUMBER,nValTotal_in IN NUMBER,
                                        vNombreTienda_in IN VARCHAR2, vCiudadTienda_in IN VARCHAR2, vRucTienda_in IN VARCHAR2,
                                        vUsu_in IN VARCHAR2) RETURN CHAR;

  --Descripcion: Agrega el detalle de una guia de ingreso.
  --Fecha       Usuario	  Comentario
  --20/02/2006  ERIOS     Creación
  --23/06/2006  ERIOS     Modificación: cTipOrigen_in
  PROCEDURE INV_AGREGA_DET_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cTipOrigen_in IN CHAR,
                                        cCodProd_in IN CHAR, nValPrecUnit_in IN NUMBER, nValPrecTotal_in IN NUMBER, nCantMov_in IN NUMBER, vFecNota_in IN VARCHAR2, vFecVecProd_in IN VARCHAR2, vNumLote_in IN VARCHAR2,
                                        cCodMotKardex_in IN CHAR,cTipDocKardex_in IN CHAR,
                                        vValFrac_in IN NUMBER,vUsu_in IN VARCHAR2,
                                        nCantSolicitado varchar2 default null,--ASOSA - 15/08/2014
                                        nOrdenCompra char default null --ASOSA - 24/09/2014
                                        );

  --Descripcion: Obtiene el detalle de una guia de ingreso.
  --Fecha       Usuario		Comentario
  --20/02/2006  ERIOS     Creación
  FUNCTION INV_GET_DET_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cNumDoc_in IN CHAR,cTipoNota_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la cabecera de una guia de ingreso.
  --Fecha       Usuario		Comentario
  --20/02/2006  ERIOS     Creación
  FUNCTION INV_GET_CAB_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cNumDoc_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la descripcion de un origen.
  --Fecha       Usuario		Comentario
  --20/02/2006  ERIOS     Creación
  FUNCTION GET_DESC_ORIGEN(cCodGrupoCia IN CHAR, cTipoMaestro_in IN CHAR,cCodBusqueda_in IN CHAR,cNumNotaEs_in IN CHAR DEFAULT NULL) RETURN VARCHAR2;

  --LISTA TRANSFERENCIAS

  --Descripcion: Obtiene el listado de las transferencias de un local.
  --Fecha       Usuario		Comentario
  --21/02/2006  ERIOS     Creación
  --31/01/2007  LMESIA    Modificacion
  FUNCTION INV_LISTA_TRANSFERENCIA(cGrupoCia_in IN CHAR,
                                   cCia_in      IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   vFiltro_in   IN CHAR)
    RETURN FarmaCursor;



  --Descripcion: Obtiene el destino de una transferencia de un local.
  --Fecha       Usuario		Comentario
  --21/02/2006  ERIOS     Creación
  FUNCTION INV_GET_DESTINO_TRANSFERENCIA(cTipOrigen_in IN CHAR, cCodOrigen_in IN CHAR, cGrupoCia_in IN CHAR, cCia_in IN CHAR) RETURN VARCHAR2;

  --LISTA PRODUCTOS TRANSFERENCIAS

  --Descripcion: Obtiene el listado de las productos de un local.
  --Fecha       Usuario		Comentario
  --22/02/2006  ERIOS     Creación
  FUNCTION INV_LISTA_PROD_TRANSFERENCIA(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --CABECERA TRANSPORTE

  --Descripcion: Obtiene los tipos de transferencias.
  --Fecha       Usuario		Comentario
  --22/02/2006  ERIOS     Creación
  --28/06/2006  ERIOS     Modificación
  FUNCTION INV_GET_TIPO_TRANSFERENCIA RETURN FarmaCursor;

  --Descripcion: Obtiene los motivos de transferencias.
  --Fecha       Usuario		Comentario
  --22/02/2006  ERIOS     Creación
  FUNCTION INV_GET_MOTIVO_TRANSFERENCIA RETURN FarmaCursor;

  --Descripcion: Obtiene las guias de recepción
  --Fecha       Usuario		Comentario
  --22/02/2006  MHUAYTA     Creación
   FUNCTION INV_LISTA_GUIA_RECEP(cGrupoCia_in IN CHAR,
   								 cCodLocal_in IN CHAR)
   RETURN FarmaCursor;

    --Descripcion: Obtiene las guias de recepción
  --Fecha       Usuario		Comentario
  --22/02/2006  MHUAYTA     Creación
   FUNCTION INV_LISTA_DET_GUIA_RECEP(cGrupoCia_in IN CHAR,
   									 cCodLocal_in IN CHAR,
									 cNumNota_in IN CHAR,
                                                                         cNumGuia_in IN CHAR)
   RETURN FarmaCursor;

   --AGREGAR TRANSFERENCIA

  --Descripcion: Agrega la cabecera de una guia de ingreso.
  --Fecha       Usuario		Comentario
  --16/02/2006  ERIOS     Creación
  FUNCTION INV_AGREGA_CAB_TRANSFERENCIA(cCodGrupoCia_in IN CHAR
                                        ,cCodLocal_in IN CHAR
                                        ,vTipDestino_in IN CHAR
                                        ,cCodDestino_in IN VARCHAR2
                                        ,cTipMotivo_in IN CHAR
                                        ,vDesEmp_in IN VARCHAR2
                                        ,vRucEmp_in IN VARCHAR2
                                        ,vDirEmp_in IN VARCHAR2
                                        ,vDesTran_in IN VARCHAR2
                                        ,vRucTran_in IN VARCHAR2
                                        ,vDirTran_in IN VARCHAR2
                                        ,vPlacaTran_in IN VARCHAR2
                                        ,nCantItems_in IN NUMBER
                                        ,nValTotal_in IN NUMBER
                                        ,vUsu_in IN VARCHAR2
                                        ,cCodMotTransInterno_in CHAR
                                        ,codOc IN VARCHAR2 DEFAULT NULL--ASOSA - 18/09/2014
                                        ) RETURN CHAR;

  --Descripcion: Agrega el detalle de una guia de ingreso.
  --Fecha       Usuario		Comentario
  --20/02/2006  ERIOS     Creación
  /*
  PROCEDURE INV_AGREGA_DET_TRANSFERENCIA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumNota_in      IN CHAR,
                                         cCodProd_in      IN CHAR,
                                         nValPrecUnit_in  IN NUMBER,
                                         nValPrecTotal_in IN NUMBER,
                                         nCantMov_in      IN NUMBER,
                                         vFecVecProd_in   IN VARCHAR2,
                                         vNumLote_in      IN VARCHAR2,
                                         cCodMotKardex_in IN CHAR,
                                         cTipDocKardex_in IN CHAR,
                                         vValFrac_in      IN NUMBER,
                                         vUsu_in          IN VARCHAR2,
                                         vTipDestino_in   IN CHAR,
                                         cCodDestino_in   IN CHAR);
  */
  PROCEDURE INV_AGREGA_DET_TRANSFERENCIA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumNota_in      IN CHAR,
                                         cCodProd_in      IN CHAR,
                                         nValPrecUnit_in  IN NUMBER,
                                         nValPrecTotal_in IN NUMBER,
                                         nCantMov_in      IN NUMBER,
                                         vFecVecProd_in   IN VARCHAR2,
                                         vNumLote_in      IN VARCHAR2,
                                         cCodMotKardex_in IN CHAR,
                                         cTipDocKardex_in IN CHAR,
                                         vValFrac_in      IN NUMBER,
                                         vUsu_in          IN VARCHAR2,
                                         vTipDestino_in   IN CHAR,
                                         cCodDestino_in   IN CHAR,
                                         vIndFrac_in      IN CHAR DEFAULT 'N');


  --Descripcion: Actualiza el estado de recepcion de una cabecera de guia de recepcion
  --Fecha       Usuario		Comentario
  --23/02/2006  MHUAYTA     Creación
	PROCEDURE INV_ACT_CAB_GUIA_RECEP(cGrupoCia_in IN CHAR,
			  						 cCodLocal_in IN CHAR,
									 cNumNota_in  IN CHAR,
									 cIdUsu_in    IN CHAR);

  --Descripcion: Obtiene el estado de recepcion de una guía
  --Fecha       Usuario		Comentario
  --23/02/2006  MHUAYTA     Creación
	FUNCTION INV_OBTIENE_EST_RECEP_GUIA(cGrupoCia_in IN CHAR,
			 							cCodLocal_in IN CHAR,
										cNumNota_in  IN CHAR)
    RETURN CHAR;

  --Descripcion: Actualiza un registro unico de una guia de recepcion
  --Fecha       Usuario		Comentario
  --23/02/2006  MHUAYTA     Creación
	PROCEDURE INV_ACT_REG_GUIA_RECEP(cGrupoCia_in 	IN CHAR,
			  						 cCodLocal_in 	IN CHAR,
									 cNumNota_in 	IN CHAR,
									 cSecDetNota_in IN CHAR,
                   cNumPag_in IN CHAR,
									 cIdUsu_in 		IN CHAR);

  --Descripcion: Actualiza una pagina de una guia de recepcion
  --Fecha       Usuario		Comentario
  --23/02/2006  MHUAYTA     Creación
    PROCEDURE INV_ACT_PAG_GUIA_RECEP(cGrupoCia_in IN CHAR,
			  						 cCodLocal_in IN CHAR,
									 cNumNota_in  IN CHAR,
									 cNumPag_in   IN CHAR,
									 cIdUsu_in 	  IN CHAR);

  --Descripcion: Actualiza una guia de recepcion completa
  --Fecha       Usuario		Comentario
  --23/02/2006  MHUAYTA     Creación
   /*PROCEDURE INV_ACT_GUIA_RECEP(cGrupoCia_in IN CHAR,
   			 					cCodLocal_in IN CHAR,
								cNumNota_in  IN CHAR,
								cIdUsu_in 	 IN CHAR);*/

  --Descripcion: Actualiza el estado de recepcion de una guia
  --Fecha       Usuario		Comentario
  --23/02/2006  MHUAYTA     Creación
    PROCEDURE INV_ACT_EST_GUIA_RECEP(cGrupoCia_in IN CHAR,
			  						 cCodLocal_in IN CHAR,
									 cNumNota_in  IN CHAR,
									 cIdUsu_in 	  IN CHAR);

  --Descripcion: Obtiene las paginas habiles de una guia
  --Fecha       Usuario		Comentario
  --23/02/2006  MHUAYTA     Creación
 	FUNCTION INV_ONTIENE_PAGINAS_GUIA(cGrupoCia_in IN CHAR,
			 						  cCodLocal_in IN CHAR,
									  cNumNota_in  IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Genera las Guias de Remision para una Transferencia
  --Fecha       Usuario		Comentario
  --24/02/2006  ERIOS     Creación
  PROCEDURE INV_GENERA_GUIA_TRANSFERENCIA(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNota_in IN CHAR, nCantMAxDet_in IN NUMBER, nCantItems_in IN NUMBER,cIdUsu_in IN CHAR);

  --VER  TRANSFERENCIA
  --Descripcion: Obtiene la cabecera de una transferencia.
  --Fecha       Usuario		Comentario
  --24/02/2006  ERIOS     Creación
  FUNCTION INV_GET_CAB_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de una transferencia.
  --Fecha       Usuario		Comentario
  --24/02/2006  ERIOS     Creación
  FUNCTION INV_GET_DET_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Anula de una transferencia.
  --Fecha       Usuario		Comentario
  --24/02/2006  ERIOS     Creación
  PROCEDURE INV_ANULA_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                    cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                    vIdUsu_in IN VARCHAR2);

  --INVENTARIO GET STOCK

  --Descripcion: Anula de una transferencia.
  --Fecha       Usuario		Comentario
  --27/02/2006  ERIOS     Creación
  FUNCTION INV_GET_STK_PROD_FORUPDATE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene Info Producto.
  --Fecha       Usuario		Comentario
  --27/02/2006  ERIOS     Creación
  FUNCTION INV_GET_INFO_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in	 IN CHAR,cCodProd_in	 IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene Datos de Destino y Transportista.
  --Fecha       Usuario		Comentario
  --28/02/2006  ERIOS     Creación
  FUNCTION INV_GET_DATOS_TRANSPORTISTA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodTransportista_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene Datos de Transportista del Local.
  --Fecha       Usuario		Comentario
  --28/02/2006  ERIOS     Creación
  FUNCTION INV_GET_TRANSP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Anula de una Guia de Ingreso.
  --Fecha       Usuario		Comentario
  --01/03/2006  ERIOS     Creación
  PROCEDURE INV_ANULA_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                    cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                    vIdUsu_in IN VARCHAR2);

  --Descripcion: Obtiene el Stock Disponible de un Producto en un local.
  --Fecha       Usuario		Comentario
  --01/03/2006  ERIOS     Creación
  FUNCTION INV_GET_STK_DISP_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN VARCHAR2;

  /*PEDIDO REPOSION*/
  --LISTA PEDIDOS REPOSICION

  --Descripcion: Obtiene el listado de Pedidos de Reposion de un local.
  --Fecha       Usuario		Comentario
  --02/03/2006  ERIOS     Creación
  FUNCTION INV_LISTA_PEDIDO_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --CALCULAR MAX Y MIN

  --Descripcion: Obtiene el numero de dias maximo de reposicion de un producto en un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  --FUNCTION INV_GET_NUM_DIAS_MAX_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN NUMBER;

  --Descripcion: Obtiene el numero de dias maximo de reposicion de un producto en un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_STK_TRANS_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN NUMBER;

  --Descripcion: Obtiene el listado de productos para reposicion.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  --19/04/2007  LREQUE    Modifcación
  FUNCTION INV_LISTA_PROD_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)RETURN FarmaCursor;

  --Descripcion: Obtiene la cabecera de reposicion de un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_CAB_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)RETURN FarmaCursor;

  --Descripcion: Obtiene el ultimo pedido de reposicion de un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_ULT_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de producto de reposicion de un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_DET_PROD_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene la cantidad anterior de un pedido de reposicion de un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_CANT_ANT_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene la rotacion de 30,60,90,120 dias de un producto en un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_ROT_PROD_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Guarda la cantidad a solicitar en un pedido de reposicion.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  PROCEDURE INV_SET_CANT_PEDREP_TMP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR,nCantTmp_in IN NUMBER,cIdUsu_in IN VARCHAR2);

  --Descripcion: Obtiene el listado de productos para reposicion filtrado.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  --30/01/2007  LREQUE		Modificación: se le ha colocado formato a los valores numéricos.
  FUNCTION INV_LISTA_PROD_REP_FILTRO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipoFiltro_in IN CHAR, cCodFiltro_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle del pedido actual de Reposicion.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  FUNCTION INV_GET_PED_ACT_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de productos para reposicion a enviar a Matriz.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  --20/04/2007	 LREQUE	   Modificación
  FUNCTION INV_LISTA_PROD_REP_VER(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;


  --Descripcion: Obtiene el listado de productos para reposicion a enviar a Matriz filtrado.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  --30/01/2007  LREQUE		Modificación: se le ha colocado formato a los valores numéricos.
  FUNCTION INV_LISTA_PROD_REP_VER_FILTRO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipoFiltro_in IN CHAR, cCodFiltro_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de un Pedido de Reposicion.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  --05/07/2006  ERIOS     Modificación
  FUNCTION INV_GET_DET_REP_VER(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNroPedido_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Graba registro de Kardex.
  --Fecha       Usuario		Comentario
  --09/03/2006  LMESIA     	Creación
  --23/06/2006  ERIOS           Modificación: cTipDoc_in, cNumDoc_in
  PROCEDURE INV_GRABAR_KARDEX(cCodGrupoCia_in 	   IN CHAR,
                              cCodLocal_in    	   IN CHAR,
							  cCodProd_in		   IN CHAR,
							  cCodMotKardex_in 	   IN CHAR,
						   	  cTipDocKardex_in     IN CHAR,
						   	  cNumTipDoc_in  	   IN CHAR,
							  nStkAnteriorProd_in  IN NUMBER,
							  nCantMovProd_in  	   IN NUMBER,
							  nValFrac_in		   IN NUMBER,
							  cDescUnidVta_in	   IN CHAR,
							  cUsuCreaKardex_in	   IN CHAR,
							  cCodNumera_in	   	   IN CHAR,
							  cGlosa_in			   IN CHAR DEFAULT NULL,
                                                          cTipDoc_in IN CHAR DEFAULT NULL,
                                                          cNumDoc_in IN CHAR DEFAULT NULL);

  --Descripcion: Obtiene el indicador de Pedido de Reposicion.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS     	Creación
  FUNCTION INV_GET_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN CHAR;


  --Descripcion: Obtiene el indicador de Pedido de Reposicion.
  --Fecha       Usuario		  Comentario
  --05/08/2014  rherrera    Modificacion : Se agrega la condición para productos insumos
    /*
   FUNCTION INV_LISTA_ITEMS_AK(cCodGrupoCia_in IN CHAR ,
  		   					  cCodLocal_in    IN CHAR,
                    cTipo_rep_insumo   IN VARCHAR2
							  )
   RETURN FarmaCursor;*/

    FUNCTION INV_LISTA_ITEMS_AK(cCodGrupoCia_in IN CHAR ,
  		   					              cCodLocal_in    IN CHAR  )
    RETURN FarmaCursor;


  --Descripcion:Muestra la lista de movimientos de Kardex
  --Fecha       Usuario		Comentario
  --13/03/2006  MHUAYTA     	Creación
  FUNCTION INV_LISTA_MOVS_KARDEX(cCodGrupoCia_in IN CHAR,
  		   					               cCodLocal_in    IN CHAR,
								                 cCod_Prod_in	   IN CHAR,
								                 cFecIni_in	     IN CHAR,
								                 cFecFin_in 	   IN CHAR)
    RETURN FarmaCursor;

  --Descripcion:Muestra la lista de Filtros para productos
  --Fecha       Usuario		Comentario
  --13/03/2006  MHUYATA     	Creación

 FUNCTION INV_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
  		   				  		 cCodLocal_in	 IN CHAR,
								 cTipoFiltro_in  IN CHAR,
  		   						 cCodFiltro_in 	 IN CHAR)
   RETURN FarmaCursor;

    --Descripcion:Muestra la lista de tipos de motivo de movimiento de kardex
  --Fecha       Usuario		Comentario
  --14/03/2006  MHUYATA     	Creación

   FUNCTION INV_LISTA_MOT_AJUST_KRDX(cCodGrupoCia_in IN CHAR)
   RETURN FarmaCursor;


 /*  FUNCTION INV_LISTA_MOT_AJUST_KRDX(cCodGrupoCia_in IN CHAR,
                                    cTipo_rep   IN CHAR  )
   RETURN FarmaCursor;
*/

   PROCEDURE INV_INGRESA_AJUSTE_KARDEX(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cCodProd_in       IN CHAR,
                                      cCodMotKardex_in  IN CHAR,
                                      cNeoCant_in       IN CHAR,
                                      cGlosa_in		  IN CHAR,
                                      cTipDoc_in		  IN CHAR,
                                      cUsu_in           IN CHAR,
                                      cIndCorreoAjuste IN CHAR DEFAULT 'S');

  --Descripcion: Obtiene la lista de lotes por producto.
  --Fecha       Usuario		Comentario
  --22/03/2006  ERIOS     Creación
  FUNCTION LISTA_LOTE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de una guia de remision.
  --Fecha       Usuario   Comentario
  --23/03/2006  ERIOS     Creación
  FUNCTION INV_GET_DET_GUIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,nSecGuia_in IN NUMBER) RETURN FarmaCursor;

  --Descripcion: Actualiza el transferencia como documento impreso.
  --Fecha       Usuario   Comentario
  --23/03/2006  ERIOS     Creación
  PROCEDURE INV_SET_IND_IMPRESO_GUIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,nSecGuia_in IN NUMBER,cNumGuia_in IN CHAR,cIdUsu_in IN CHAR);

  --Descripcion: Obtiene los secuenciales de las guias de remision.
  --Fecha       Usuario   Comentario
  --23/03/2006  ERIOS     Creación
  --20/06/2006  ERIOS     Modificación: cSecImprLocal_in
  FUNCTION INV_GET_SEC_GUIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cSecImprLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene los motivos de transferencia.
  --Fecha       Usuario   Comentario
  --24/03/2006  ERIOS     Creación
  FUNCTION INV_GET_MOTIVO_TRANSF(cCodGrupoCia_in IN CHAR,cCodTipo_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la lista de impresoras paa emitir una guia
  --Fecha       Usuario   Comentario
  --07/04/2006  MHUAYTA     Creación
  FUNCTION INV_LISTA_IMPRESORAS(cGrupoCia_in IN CHAR,
  		   					    cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Ejecuta la actualizacion de stock de producto y genera un mov de kardex producto de una recepcion de productos
  --Fecha       Usuario   Comentario
  --11/04/2006  MHUAYTA   Creación
  --13/06/2006  ERIOS     Modificación: nSecGuia_in
  PROCEDURE INV_OPERAC_ANEXAS_PROD(cCodGrupoCia_in  IN CHAR,
  								   cCodLocal_in 	IN CHAR,
								   cNumNota_in 		IN CHAR,
                                                                   nSecGuia_in IN NUMBER,
								   cCodProd_in 		IN CHAR,
								   nCantMov_in 		IN NUMBER,
								   cIdUsu_in 		IN CHAR,
                                                                   nTotalGuia_in IN NUMBER DEFAULT NULL,
                                                                   nDiferencia_in IN NUMBER DEFAULT NULL);

  --Descripcion: Obtiene el numero  de comprobante donde se va a imprimir el detalle del pedido
  --Fecha       Usuario		Comentario
  --19/04/2006  MHUAYTA     	Creación

  FUNCTION INV_OBTIENE_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in  IN CHAR,
  		   						   	   	  cCodLocal_in     IN CHAR,
									   	  cSecImprLocal_in IN CHAR)
  RETURN FarmaCursor;

   --Descripcion: Actualiza el numero de comprobante en la impresora local
  --Fecha       Usuario		Comentario
  --19/04/2006  MHUAYTA     	Creación
 PROCEDURE INV_ACTUALIZA_IMPR_NUM_COMP(cCodGrupoCia_in 	   IN CHAR,
  		   						   	   cCodLocal_in    	   IN CHAR,
									   cSecImprLocal_in    IN CHAR,
									   cUsuModImprLocal_in IN CHAR);

	--Descripcion: Cambia los valores de serie y numero de comprobante de una impresora
    --Fecha       Usuario		Comentario
    --19/04/2006  MHUAYTA     Creación
	PROCEDURE INV_RECONFIG_IMPRESORA(cCodGrupoCia_in    IN CHAR,
			  				         cCod_Local_in    	IN CHAR,
							  		 nSecImprLocal_in 	IN NUMBER,
							  		 cNumComp_in 		IN CHAR);
  --Descripcion: Obtiene la cantidad de historico de lote de un producto.
  --Fecha       Usuario   Comentario

  FUNCTION INV_GET_HISTORICO_LOTE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                                  cCodProd_in IN CHAR,cNumPed IN CHAR)
  RETURN INTEGER;

  --Descripcion: Obtiene la cantidad de historico de lote de un producto.
  --Fecha       Usuario   Comentario
  --19/04/2006  ERIOS     Creación
  FUNCTION INV_GET_HISTORICO_LOTE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN INTEGER;

  --Descripcion: Obtiene las guias de recepción
  --Fecha       Usuario	  Comentario
  --03/05/2006  ERIOS     Creación
   FUNCTION INV_LISTA_GUIA_RECEP(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNota_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Actualiza la guia
  --Fecha       Usuario	  Comentario
  --03/05/2006  ERIOS     Creación
  /*PROCEDURE INV_ACT_GUIA_RECEP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in  IN CHAR,cNumPag_in   IN CHAR,cIdUsu_in    IN CHAR);*/

  --Descripcion: Determina si exste o no el Numero de Entrega
  --Fecha       Usuario	  Comentario
  --12/05/2006  ERIOS     Creación
  FUNCTION INV_GET_NUM_ENTREGA(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumEntrega_in IN CHAR) RETURN NUMBER;

  --Descripcion: Agrega un exceso de producto
  --Fecha       Usuario	  Comentario
  --12/05/2006  ERIOS     Creación
  PROCEDURE INV_AGREGA_EXCESO_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumEntrega_in IN CHAR, cNumLote_in IN CHAR,vFecVenc_in IN VARCHAR2, cCodProd_in IN CHAR,nCantExec_in IN NUMBER,cIdUsu_in IN CHAR);

  --Descripcion: Obtiene el listado de excesos
  --Fecha       Usuario	  Comentario
  --12/05/2006  ERIOS     Creación
  FUNCTION INV_LISTA_EXCESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Modifica un exceso de producto
  --Fecha       Usuario	  Comentario
  --12/05/2006  ERIOS     Creación
  PROCEDURE INV_MODIFICA_EXCESO_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cSecExceso_in IN CHAR,cNumEntrega_in IN CHAR, cNumLote_in IN CHAR,vFecVenc_in IN VARCHAR2, cCodProd_in IN CHAR,nCantExec_in IN NUMBER,cIdUsu_in IN CHAR);

  --Descripcion: Graba el stock actual de los productso en el local
  --Fecha       Usuario	  Comentario
  --24/05/2006  ERIOS     Creación
  PROCEDURE INV_GRABA_STOCK_ACTUAL_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsu_in IN CHAR);

  --Descripcion: Actualiza el estado de la guia de recepcion
  --Fecha       Usuario	  Comentario
  --29/05/2006  ERIOS     Creación
  PROCEDURE INV_ACT_EST_GUIA(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cNumPag_in IN CHAR,cIdUsu_in IN CHAR);

  --Descripcion: Obtiene el estado de la guia de recepcion
  --Fecha       Usuario	  Comentario
  --29/05/2006  ERIOS     Creación
  FUNCTION INV_OBTIENE_EST_GUIA(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cNumPag_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene los motivos de ajuste de kardex
  --Fecha       Usuario	  Comentario
  --05/06/2006  Paulo     Creación
  FUNCTION INV_LISTA_MOTIVOS_KRDX(cCodGrupoCia_in IN CHAR)RETURN Farmacursor;


  --Descripcion: Filtra por descripcion de kardex
  --Fecha       Usuario	  Comentario
  --05/06/2006  Paulo     Creación
  FUNCTION INV_FILTRO_LISTA_MOVS_KARDEX(cCodGrupoCia_in IN CHAR ,
  		   					     	   cCodLocal_in    IN CHAR ,
								       cCod_Prod_in	 IN CHAR,
								       cFecIni_in		 IN CHAR,
								       cFecFin_in 	 IN CHAR,
									   cFiltro_in	 IN CHAR)

 RETURN FarmaCursor;

 --Descripcion: Setea el valor del registro
 --Fecha       Usuario	  Comentario
 --06/06/2006  ERIOS     Creación
 PROCEDURE INV_SETEAR_VALORES(cCodGrupoCia_in IN CHAR ,cCodLocal_in IN CHAR,
                              cNumNota_in IN CHAR, cSecDetNota_in IN CHAR,
                              cCantMov_in IN CHAR, cIdUsu_in 	IN CHAR);

  --Descripcion: Actualiza el kardex con el numero de guia fisica de guia de recepcion
  --Fecha       Usuario	  Comentario
  --12/06/2006  LMESIA    Creación
  PROCEDURE INV_ACT_KARDEX_GUIA_REC(cGrupoCia_in   IN CHAR,
                                    cCodLocal_in 	 IN CHAR,
                                    cNumNota_in  	 IN CHAR,
                                    cSecDetNota_in IN CHAR,
                                    cCodProd_in 	 IN CHAR,
                                    cIdUsu_in 		 IN CHAR,
                                    cTipOrigen_in IN CHAR DEFAULT NULL);

  --Descripcion: Verifica el estado de proceso interface Sap.
  --Fecha       Usuario	 Comentario
  --19/06/2006  ERIOS     Creación
  FUNCTION INV_GET_ESTADO_PROC_SAP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cTipoNota_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene los datos de cabecera de una guía de transferencia.
  --Fecha       Usuario	 Comentario
  --20/06/2006  ERIOS     Creación
  FUNCTION INV_GET_CABECERA_TRANSF(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Valida si existe espacio disponible para imprimir las guias de una transferencia.
  --Fecha       Usuario	 Comentario
  --20/06/2006  ERIOS     Creación
  PROCEDURE VALIDA_GUIAS_DISPONIBLE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,nNumGuias_in IN NUMBER,cSecImprLocal_in IN CHAR);

  --Descripcion: Obtiene el listado de Guías Impresas.
  --Fecha       Usuario	 Comentario
  --26/06/2006  ERIOS     Creación
  FUNCTION INV_LISTA_GUIAS_REM(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,vFechaIni_in IN VARCHAR2,vFechaFin_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Verifica si existen las guias necesarias.
  --Fecha       Usuario	 Comentario
  --26/06/2006  ERIOS     Creación
  FUNCTION INV_VERIFICAR_GUIAS(cCodGrupoCia_in  IN CHAR,
                              cCodLocal_in     IN CHAR,
                              cSecIni_in       IN CHAR,
                              cSecFin_in       IN CHAR,
                              cTipoNota_in IN CHAR) RETURN NUMBER;

  --Descripcion: Verifica si no existen las guias necesarias.
  --Fecha       Usuario	 Comentario
  --26/06/2006  ERIOS     Creación
  FUNCTION INV_VERIFICAR_CORRECCION_GUIA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cSecIni_in       IN CHAR,
                                         cSecFin_in       IN CHAR,
                                         nCant_in	  IN NUMBER,
                                         cIndDir_in	  IN CHAR,
                                         cTipoNota_in IN CHAR) RETURN NUMBER;

  --Descripcion: Corrige guias de remision.
  --Fecha       Usuario	 Comentario
  --26/06/2006  ERIOS     Creación
  PROCEDURE INV_CORRIGE_GUIAS(cCodGrupoCia_in  	IN CHAR,
                                   cCodLocal_in     	IN CHAR,
                                   cSecIni_in 	 	IN CHAR,
                                   cSecFin_in  	 	IN CHAR,
                                   nCant_in	 	    IN NUMBER,
                                   nIndDir	        IN NUMBER,
                                   cCodUsu_in			IN CHAR,
                                   cTipoNota_in IN CHAR);

  --Descripcion: Corrige guias de remision.
  --Fecha       Usuario	 Comentario
  --26/06/2006  ERIOS     Creación
  PROCEDURE INV_CORREGIR_NUM_GUIA(cCodGrupoCia_in  	IN CHAR,
                                   cCodLocal_in     	IN CHAR,
                                   cNumNotaEs_in IN CHAR,
                                   nSecGuiaRem_in IN NUMBER,
                                   cNumGuiaAnt_in IN CHAR,
                                   cTipoNota_in IN CHAR,
                                   cNumGuiaNuevo_in IN CHAR,
                                   cIdUsu_in IN VARCHAR2);

  --Descripcion: Actualiza Kardex de una Guía corregida.
  --Fecha       Usuario	 Comentario
  --26/06/2006  ERIOS     Creación
  --04/07/2006  ERIOS     Modificación
  PROCEDURE INV_CORRIGE_KARDEX_GUIA(cGrupoCia_in   IN CHAR,
                                    cCodLocal_in 	 IN CHAR,
                                    cNumNota_in  	 IN CHAR,
                                    cSecDetNota_in IN CHAR,
                                    cCodProd_in 	 IN CHAR,
                                    cIdUsu_in 		 IN CHAR,
                                    cTipOrigen_in IN CHAR DEFAULT NULL);

  --Descripcion: Lista productos en Stock Cero.
  --Fecha       Usuario	 Comentario
  --30/06/2006  ERIOS     Creación
  --30/01/2007  LREQUE		Modificación: se le ha colocado formato a los valores numéricos.
  FUNCTION INV_LISTA_PROD_REP_STK_CERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Lista las transferencias pendientes de procesar.
  --Fecha       Usuario	 Comentario
  --17/07/2006  ERIOS     Creación
  FUNCTION INV_LISTA_TRANSF_CONFIRMAR(cGrupoCia_in IN CHAR, cCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Confirma una transferencia.
  --Fecha       Usuario	 Comentario
  --17/07/2006  ERIOS     Creación
  PROCEDURE INV_CONFIRMAR_TRANSF(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumNotaEs_in IN CHAR, vIdUsu_in IN VARCHAR2);

  --Descripcion: Lista los ajuestes de kardex q se ha realizado
   --Fecha       Usuario	 Comentario
   --17/07/2006  PAULO     Creación
   FUNCTION INV_LISTA_AJUSTES_KARDEX(cCodGrupoCia_in IN CHAR ,
   		   					                  cCodLocal_in    IN CHAR ,
 								                    cFecIni_in		 IN CHAR,
 								                    cFecFin_in 	 IN CHAR)
 RETURN FarmaCursor;

  --Descripcion: Obtiene la cantidad máxima para reponer.
  --Fecha       Usuario	 Comentario
  --17/07/2006  ERIOS     Creación
  /*FUNCTION INV_GET_CANT_MAX(nCantSug_in IN NUMBER,nPorcAdic_in IN NUMBER,
  nCantMax_in IN NUMBER,nValFrac_in IN NUMBER) RETURN NUMBER;*/

  --Descripcion: Obtiene la dirección del local.
  --Fecha       Usuario	 Comentario
  --31/08/2006  ERIOS     Creación
  FUNCTION GET_DIREC_ORIGEN_LOCAL(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Verifica el estado de proceso cierre día.
  --Fecha       Usuario	 Comentario
  --01/09/2006  ERIOS     Creación
  FUNCTION INV_GET_EST_PROC_CIERRE_DIA(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cTipoNota_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene el último pedido reposición del día.
  --Fecha       Usuario	 Comentario
  --04/09/2006  ERIOS     Creación
  FUNCTION INV_GET_ULTIMO_PED_REP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN CHAR;

  --Descripcion: Guarda la cantidad a solicitar en un pedido de reposicion.
  --Fecha       Usuario		Comentario
  --04/09/2006  ERIOS     Creación
  PROCEDURE INV_SET_CANT_PEDREP_MATRIZ(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedRep_in IN CHAR,cCodProd_in IN CHAR,nCant_in IN NUMBER,cIdUsu_in IN VARCHAR2);

  --Descripcion: Envia correo de alerta.
  --Fecha       Usuario	  Comentario
  --07/09/2006  ERIOS     Creación
  PROCEDURE INT_ENVIA_CORREO_AJUSTE(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        cSecKardex IN CHAR);

  --Descripcion: Obtiene el último pedido reposición del día.
  --Fecha       Usuario	 Comentario
  --14/09/2006  ERIOS     Creación
  FUNCTION GET_EST_PROCESO_SAP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedRep_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene los motivos para filtrar las transferencias.
  --Fecha       Usuario	 Comentario
  --11/10/2006  ERIOS     Creación
  FUNCTION INV_GET_FILTRO_TRANSF(cCodGrupoCia_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Lista datos del transportista seleccionado.
  --Fecha       Usuario	 Comentario
  --31/10/2006  ERIOS     Creación
  FUNCTION INV_GET_DATOS_TRANSPORTE(cCodGrupoCia_in IN CHAR,cCodLocal_in	 IN CHAR,cCodDestino_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene cantidades de la guia de recepcion.
  --Fecha       Usuario	 Comentario
  --02/11/2006  ERIOS     Creación
  FUNCTION INV_GET_CANT_RECEP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR, cNumeroEntrega_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Graba el kardex por ajuste de diferencias.
  --Fecha       Usuario	 Comentario
  --03/11/2006  ERIOS     Creación
  PROCEDURE INV_INGRESA_AJUSTE_DIFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR,cCodMotKardex_in IN CHAR,nCantAjuste_in IN NUMBER,
  cTipDoc_in IN CHAR,cNumeroEntrega_in IN CHAR,cNumNotaEs_in IN CHAR,
  nNumPos_in IN NUMBER,cUsu_in IN CHAR);

  --Descripcion: realiza el filtro de pedido de reposicion por laboratorio
  --Fecha       Usuario	 Comentario
  --24/11/2006  PAULO     Creación
  FUNCTION INV_GET_DET_REP_FILTRO(cCodGrupoCia_in IN CHAR,
  		   					                cCodLocal_in    IN CHAR,
							                    cNroPedido_in   IN CHAR,
                                  cCodFiltro_in   IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: OBTIENE EL STOCK DE LOS LOCALES MAS CERCANOS
  --Fecha       Usuario	 Comentario
  --12/12/2006  PAULO     Creación
  FUNCTION INV_STOCK_LOCALES_PREFERIDOS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        cCodProd_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: OBTIENE EL STOCK DE LOS LOCALES MENOS CERCANOS
  --Fecha       Usuario	 Comentario
  --13/12/2006  PAULO     Creación
  /*FUNCTION INV_STOCK_LOCAL_NO_PREFERIDOS(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cCodProd_in IN CHAR)
  RETURN FarmaCursor;*/

  --Descripcion: Realiza la consulta en linea al local para sacar el stock
  --Fecha       Usuario	 Comentario
  --13/12/2006  PAULO     Creación
  FUNCTION INV_OBTIENE_STOCK_EN_lINEA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cCodProd_in IN CHAR/*,
                                      STOCK in OUT CHAR,
                                      UNIDAD_VENTA in OUT CHAR*/)
  RETURN VARCHAR2;

  --Descripcion: Realiza la consulta a matriz
  --Fecha       Usuario	 Comentario
  --13/12/2006  PAULO     Creación
  FUNCTION INV_CONECTA_MATRIZ(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in IN CHAR,
                              cCodProd_in IN CHAR/*,
                              STOCK in OUT CHAR,
                              UNIDAD_VENTA in OUT CHAR*/)
    RETURN VARCHAR2 ;

  --Descripcion: Graba Kardex de un producto Virtual; es decir, no tiene ni stk anterior ni final
  --Fecha       Usuario	 Comentario
  --16/01/2007  LMESIA   Creación
  PROCEDURE INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in 	  IN CHAR,
                                      cCodLocal_in    	  IN CHAR,
        							                cCodProd_in		      IN CHAR,
        							                cCodMotKardex_in 	  IN CHAR,
        						   	              cTipDocKardex_in    IN CHAR,
        						   	              cNumTipDoc_in  	    IN CHAR,
        							                nCantMovProd_in  	  IN NUMBER,
        							                nValFrac_in		      IN NUMBER,
        							                cDescUnidVta_in	    IN CHAR,
        							                cUsuCreaKardex_in	  IN CHAR,
        							                cCodNumera_in	   	  IN CHAR,
        							                cGlosa_in			      IN CHAR DEFAULT NULL,
                                      cTipDoc_in          IN CHAR DEFAULT NULL,
                                      cNumDoc_in          IN CHAR DEFAULT NULL);

  --Descripcion: obtiene la fecha de calculo de maximos y minimos
  --Fecha       Usuario	 Comentario
  --30/03/2007  paulo   Creación

  FUNCTION  INV_OBTIENE_FECHA_REPOSICION(cCodGrupoCia_in 	  IN CHAR,
                                         cCodLocal_in    	  IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: obtiene los indicadores de stock
  --Fecha       Usuario	 Comentario
  --20/04/2007  paulo   Creación

  FUNCTION INV_OBTIENE_IND_STOCK(cCodProd_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Establece una cantidad adicional a un producto
  --Fecha       Usuario	     Comentario
  --20/04/2007  Luis Reque   Creación
  PROCEDURE INV_SET_CANT_ADIC_TMP(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR,
                                  nCantTmp_in     IN NUMBER,
                                  cIdUsu_in       IN VARCHAR2);

  --Descripcion: Obtiene el listado de productos para reposicion a enviar a Matriz,
  --             los caules, tiene solicitud de cantidad adicional
  --Fecha       Usuario		Comentario
  --25/04/2007  Luis Reque   Creación
  FUNCTION INV_LISTA_PROD_REP_VER_ADIC(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle del pedido actual de Reposicion (cantidades adicionales).
  --Fecha       Usuario		Comentario
  --25/04/2006  LREQUE    Creación
  FUNCTION INV_GET_PED_ACT_REP_ADIC(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR) RETURN FarmaCursor;

  FUNCTION INV_GET_MOTIVO_TRANS_INTERNO
  RETURN FARMACURSOR;


  PROCEDURE  INV_ACTUALIZA_IND_LINEA (cIdUsuario_in IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cCodGrupoCia_in IN CHAR);

   FUNCTION INV_OBTIENE_IND_LINEA(cCodLocal_in  IN CHAR,
                                  cCodGrupoCia_in IN CHAR)
   RETURN VARCHAR2;

  --Descripcion: ACTUALIZA EL INDICADOR DE MAXIMOS Y MINIMOS EN EL DETALLE
  --             PARA NO CONSIDERLO EN EL CALCULO DE MAXIMOS Y MINIMOS
  --             EN EL PEDIDO DE REPOSICION
  --Fecha       Usuario		    Comentario
  --26/07/2007  PAMEGHINO     Creación
  PROCEDURE INV_ACTUALIZA_IND_CAL_MAX_MIN(cCodGrupoCia_in  IN CHAR,
                                          cCodLocal_in     IN CHAR,
                                          cIdUSuMod_in     IN CHAR,
                                          cCantAtendida_in IN CHAR,
                                          cNumPedido_in    IN CHAR,
                                          cCodProd_in      IN CHAR);

  --Descripcion: OBTIENE LA FECHA LIMIETE DE COTIZACION DE COMPETENCIA Y
  --             VALIDA QUE LA FECHA INGRESADA SEA MENOR A ELLA.
  --Fecha       Usuario		    Comentario
  --02/08/2007  PAMEGHINO     Creación
  FUNCTION INV_VALIDA_FECHA_COT_COMP(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cFechaIngreso_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: oBTIENE TIEM STAM
  --Fecha       Usuario		    Comentario
  --12/09/2007  DUBILLUZ      Creación
  FUNCTION OBTIENE_TIME_STAMP(cCodGrupoCia_in  IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: OBTIENE EL IND DONDE SE ESTABLECE SI SE HABILITA EL
  --TEXT DE FRACCION PARA LA TRANSFERENCIA
  --Fecha       Usuario		    Comentario
  --15/09/2007  DUBILLUZ      Creación
  FUNCTION  INV_GET_IND_TXTFRACC_MOTIVO(cCodGrupoCia_in IN CHAR,
                                        cCodTipo_in     IN CHAR,
                                        cCodMotivo      IN CHAR)
  RETURN CHAR;


  --Descripcion: Obtiene las listas de competencias
  --Fecha        Usuario	   Comentario
  --12/11/2007   DUBILLUZ     Creación
  FUNCTION INV_LISTA_COMPETENCIAS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Validara si existe el comprobante
  --Fecha       Usuario		    Comentario
  --12/11/2007  DUBILLUZ      Creación
  FUNCTION  INV_VALIDA_NUM_COMPETENCIA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumDoc_in      IN CHAR,
                                       cTipDoc_in      IN CHAR,
                                       cRucEmpresa     IN CHAR)
  RETURN CHAR;

  --Descripcion: Busca la empresa por RUC
  --Fecha        Usuario	   Comentario
  --12/11/2007   DUBILLUZ     Creación
  FUNCTION INV_BUSCA_EMPRESA(cCodGrupoCia_in IN CHAR,
                             cRUC_in         IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Retorna los datos de la cotizacion
  --Fecha        Usuario	   Comentario
  --26/11/2007   DUBILLUZ     Creación
  FUNCTION  REP_GET_DATOS_COTIZACION(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cNumDoc_in      IN CHAR,
                                     cTipDoc_in      IN CHAR,
                                     cRucEmpresa     IN CHAR)
  RETURN FarmaCursor;

  FUNCTION  INV_F_GET_VAL_FRAC_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cCodProd_in IN CHAR)
  RETURN NUMBER;

   --Descripcion: Se valida guias
  --Fecha        Usuario	   Comentario
  --29/10/2009   JCORTEZ     Creación
  FUNCTION INV_F_EXISTS_GUIAS_TRANSF(cGrupoCia_in IN CHAR,
                                       cCodLocal_in IN CHAR,
                                       cNumNota_in  IN CHAR)
  RETURN CHAR;

  --Descripcion: OBTENIENDO DESCRIPCION LARGA
  --Fecha        Usuario	   Comentario
  --10/12/2009   JMIRANDA     Creación
  FUNCTION TRANSF_F_DESC_LARGA_TRANS(cLlaveTabGral_in IN CHAR,
                                     vDescCorta_in IN VARCHAR2)
  RETURN VARCHAR2;

    PROCEDURE INV_P_REG_TMP_INIFIN_CREATRANS(cCodGrupoCia_in IN CHAR,
							  			                   cCod_Local_in   IN CHAR,
                                         cNotaEstCab_in     IN CHAR,
							  			                   cTmpTipo_in 	   IN CHAR);
  PROCEDURE INV_P_REG_TMP_INIFIN_CONFTRANS(cCodGrupoCia_in IN CHAR,
							  			                   cCod_Local_in   IN CHAR,
                                         cNotaEstCab_in     IN CHAR,
                                         cTipoConfirmacion_in  IN CHAR,
							  			                   cTmpTipo_in 	   IN CHAR);
     PROCEDURE INV_P_REG_TMP_INIFIN_GUIATRANS(cCodGrupoCia_in IN CHAR,
							  			                   cCod_Local_in   IN CHAR,
                                         cNotaEstCab_in     IN CHAR,
							  			                   cTmpTipo_in 	   IN CHAR);
     PROCEDURE INV_P_REG_TMP_INIFIN_ANUTRANS(cCodGrupoCia_in IN CHAR,
							  			                   cCod_Local_in   IN CHAR,
                                         cNotaEstCab_in     IN CHAR,
							  			                   cTmpTipo_in 	   IN CHAR);

  --Descripcion: OBTENIENDO DESCRIPCION LARGA
  --Fecha        Usuario	   Comentario
  --19/01/2010   ASOSA       Modificacion
FUNCTION INV_F_VALIDA_ASIST_AUDIT(ccodcia_in in char,
                         ccodloc_in in char,
                         csecusuloc_in in char)
RETURN CHAR;

  --Descripcion: ASOCIA ENTREGAS POR ALMACEN A LGT_RECEP_ENTREGA
  --Fecha        Usuario	   Comentario
  --09/02/2010   JMIRANDA       CREACIÓN
PROCEDURE INV_P_ASOCIA_ENT_RECEP (cCodGrupoCia_in IN CHAR,
							  			            cCod_Local_in   IN CHAR,
                                  cNro_Recepcion_in IN CHAR,
                                  cNum_Entrega_in IN CHAR,
                                  cUsuario_in IN VARCHAR2);

  --Descripcion: INSERTA CABECERA
  --Fecha        Usuario	   Comentario
  --09/02/2010   JMIRANDA       CREACIÓN
FUNCTION INV_F_INS_RECEP_CAB (cCodGrupoCia_in IN CHAR,
							  			            cCod_Local_in   IN CHAR,
                                  cIdUsu_in      IN CHAR,
                                  cNombTransp    IN CHAR,
                                  cHoraTransp    IN CHAR,
                                  cPlaca         IN CHAR,
                                  nCantBultos    IN NUMBER,
                                  nCantPrecintos IN NUMBER,
                                  cGlosa         IN VARCHAR2 DEFAULT '')
RETURN CHAR;

  --Descripcion: ACTUALIZA CANTIDAD DE GUIAS EN CABECERA
  --Fecha        Usuario	   Comentario
  --09/02/2010   JMIRANDA       CREACIÓN
PROCEDURE INV_P_ACT_CANT_GUIAS (cCodGrupoCia_in IN CHAR,
							  			            cCod_Local_in   IN CHAR,
                                  cNro_Recepcion_in IN CHAR,
                                  cIdUsu_in      IN CHAR,
                                  cCantGuias_in IN NUMBER);

 FUNCTION INV_GET_IND_NUEVA_TRANSF
 RETURN CHAR;

  --Descripcion: VERIFICA SI DEBE PEDIR LOTE PARA TRANSFERENCIA
  --Fecha        Usuario	   Comentario
  --14/05/2010   JMIRANDA       CREACIÓN
 FUNCTION INV_GET_PIDE_LOTE_TRANSF(cCodGrupoCia_in CHAR)
 RETURN CHAR;

  --Descripcion: VERIFICA SI DEBE PEDIR FECHA VENCIMIENTO PARA TRANSFERENCIA
  --Fecha        Usuario	   Comentario
  --14/05/2010   JMIRANDA       CREACIÓN
 FUNCTION INV_GET_PID_FEC_VTO_TRANSF(cCodGrupoCia_in CHAR)
 RETURN CHAR;

 --Descripcion: BUSCA SI EL PRODUCTO ESTA ACTIVO
  --Fecha        Usuario	   Comentario
  --05/05/2010   JQUISPE       CREACIÓN

FUNCTION INV_F_ACTIVO_PROD(cCodGrupoCia_in IN CHAR,
							  			       cCod_Local_in   IN CHAR,
                             cCodProd_in     IN VARCHAR2)
RETURN CHAR;

FUNCTION INV_F_CANTIDAD_ITEMS_GUIA(cCodGrupoCia_in IN CHAR,cCod_Local_in IN CHAR, cCodProd_in IN CHAR)
RETURN CHAR;

FUNCTION INV_F_GET_FECHAVTO_LOTE (cCodGrupoCia_in    IN CHAR,
							  			            cCodLocal_in       IN CHAR,
                                  cCodProd_in        IN CHAR,
                                  cNumlote_in        IN CHAR)
RETURN varchar2;


 --Descripcion: Busca los datos Ruc y Direccion de la tabla  LGT_TRANSP_CIEGA
  --Fecha       Usuario		Comentario
  --27/11/2013  CHUANES    Creación
 FUNCTION INV_GET_DATOS_TRANSP_CIEGA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodTransp_in IN CHAR)

  Return FarmaCursor;

  --Descripcion: Lista datos de nombre del local y  direccion del local.
  --Fecha       Usuario		Comentario
  --16/12/2013  CHUANES     Creación
   FUNCTION INV_GET_DATOS_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)

   Return  FarmaCursor;
   --PROCEDIMIENTO QUE GENERA GUIAS DE REMISION
  --Fecha        Usuario		    Comentario
  --16/12/2013   CHUANES        Creación

  PROCEDURE INV_GENERA_GUIA_REMISION(cGrupoCia_in   IN CHAR, cCodLocal_in IN CHAR,cIdUsu_in IN CHAR,cNumNota_in IN CHAR);

 --PROCEDIMIENTO ACTUALIZA  TEXTO_IMPR
  --Fecha        Usuario		    Comentario
  --16/12/2013   CHUANES        Creación
  PROCEDURE ACTUALIZA_TEXTO_IMPR(cGrupoCia_in   IN CHAR, cCodLocal_in IN CHAR ,cNumNota IN CHAR ,cTexto_Impr IN VARCHAR);

  --Descripcion: Obtiene el texto a imprimir
  --Fecha        Usuario			Comentario
  --16/12/2013   ERIOS        		Creacion
	FUNCTION GET_TEXTO_IMPR (cCodGrupoCia_in    IN CHAR,
									  cCodLocal_in       IN CHAR,
									  cNumNota_in        IN CHAR)
	RETURN varchar2;

  --PROCEDIMIENTO LISTA GUIA QUE NO MUEVEN STOCK
  --Fecha        Usuario		    Comentario
  --19/12/2013   CHUANES        Creación
  FUNCTION LISTA_GUIA_NO_MUEVE_STOCK(cCodGrupoCia_in  IN CHAR,cCodLocal_in   IN CHAR)

  Return FarmaCursor;

  -- MUESTRA DATOS DE TRANSPORTISTA PARA TRANSFERENCIAS DELIVERY
  -- Fecha        Usuario		    Comentario
  -- 22.08.2014   KMONCADA      Creación
  FUNCTION INV_GET_DATOS_TRANS_DELIVERY(cCodGrupoCia_in IN CHAR,cCodLocal_in	 IN CHAR,cCodDestino_in IN CHAR)
  RETURN FarmaCursor;

  --     LISTA DE PRODUCTOS CON LABORATORIO SUMINISTROS (COD_GRUPO_REP = '010')
  -- Fecha        Usuario		    Comentario
  -- 19.09.2014   RHERRERA      Creación
 FUNCTION INV_LISTA_INSUMOS (cCodGrupoCia_in IN CHAR ,
  		   					            cCodLocal_in    IN CHAR  )
 RETURN FarmaCursor;
  --  OBTIENE MOTIVO DE INSUMOS
  -- Fecha        Usuario		    Comentario
  -- 19.09.2014   RHERRERA      Creación
 FUNCTION F_OBTIENE_MOT_INSUMO(cCodMotKardex_in CHAR)
          RETURN CHAR;

  end;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_INV" AS

  FUNCTION INV_LISTA_GUIA_INGRESO(cGrupoCia_in IN CHAR,
                                  cCia_in      IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFechaIni_in IN VARCHAR2,
                                  vFechaFin_in IN VARCHAR2,
                                  vFiltro_in   IN CHAR)
    RETURN FarmaCursor
  IS
  	curGuias FarmaCursor;
  BEGIN
  	   OPEN curGuias FOR
  	        SELECT C.NUM_NOTA_ES || 'Ã' ||
                   NVL((SELECT T.DESC_COMP
                        FROM   VTA_TIP_COMP T
                        WHERE  T.COD_GRUPO_CIA = cGrupoCia_in
                        AND    T.TIP_COMP = C.TIP_DOC),' ') || 'Ã' ||
                   NVL(G.NUM_GUIA_REM,C.NUM_DOC) || 'Ã' ||--NVL(NUM_DOC,' ') || 'Ã' ||
  	               NVL(INV_GET_ORIGEN_NOTA_E(C.TIP_ORIGEN_NOTA_ES,C.COD_ORIGEN_NOTA_ES,cGrupoCia_in,cCia_in,C.NUM_NOTA_ES),' ') || 'Ã' ||
                   TO_CHAR(C.FEC_NOTA_ES_CAB,'dd/MM/yyyy') || 'Ã' ||
                   TO_CHAR(C.FEC_CREA_NOTA_ES_CAB,'dd/MM/yyyy') || 'Ã' ||
  	               C.CANT_ITEMS || 'Ã' ||
                   DECODE(C.EST_NOTA_ES_CAB,'A','ACTIVO','N','ANULADO',C.EST_NOTA_ES_CAB) || 'Ã' ||
                   ' ' || 'Ã' ||
                   C.TIP_NOTA_ES || 'Ã' ||
                   NVL(C.TIP_ORIGEN_NOTA_ES,' ')
  	        FROM   LGT_NOTA_ES_CAB C, LGT_GUIA_REM G
  	        WHERE  C.COD_GRUPO_CIA = cGrupoCia_in
  	        AND    C.COD_LOCAL = cCodLocal_in
  	        AND    C.TIP_NOTA_ES = g_cTipoNotaEntrada
            AND    C.FEC_NOTA_ES_CAB BETWEEN TO_DATE(vFechaIni_in,'dd/MM/yyyy')
                                     AND     TO_DATE(vFechaFin_in,'dd/MM/yyyy')+0.99999
            AND    C.TIP_ORIGEN_NOTA_ES LIKE vFiltro_in
            AND    C.COD_GRUPO_CIA = G.COD_GRUPO_CIA(+)
            AND    C.COD_LOCAL = G.COD_LOCAL(+)
            AND    C.NUM_NOTA_ES = G.NUM_NOTA_ES(+)
            UNION --GUIAS DE RECEPCION
            SELECT C.NUM_NOTA_ES || 'Ã' ||
                   NVL((SELECT T.DESC_COMP
                        FROM   VTA_TIP_COMP T
                        WHERE  T.COD_GRUPO_CIA = cGrupoCia_in
                        AND    T.TIP_COMP = TIP_DOC),' ') || 'Ã' ||
                   NVL(G.NUM_GUIA_REM,' ') || 'Ã' ||
  	               NVL(INV_GET_ORIGEN_NOTA_E(TIP_ORIGEN_NOTA_ES,COD_ORIGEN_NOTA_ES,cGrupoCia_in,cCia_in),' ') || 'Ã' ||
                   TO_CHAR(FEC_NOTA_ES_CAB,'dd/MM/yyyy') || 'Ã' ||
                   TO_CHAR(G.FEC_MOD_GUIA_REM,'dd/MM/yyyy') || 'Ã' ||
  	               (SELECT COUNT(*)
                    FROM   LGT_NOTA_ES_DET
                    WHERE  COD_GRUPO_CIA = C.COD_GRUPO_CIA
                    AND    COD_LOCAL = C.COD_LOCAL
                    AND    NUM_NOTA_ES = C.NUM_NOTA_ES
                    AND    SEC_GUIA_REM = G.SEC_GUIA_REM)  || 'Ã' ||
                   DECODE(G.EST_GUIA_REM,'A','ACTIVO','N','ANULADO',G.EST_GUIA_REM)|| 'Ã' ||
                   NVL(G.NUM_ENTREGA,' ') || 'Ã' ||
                   C.TIP_NOTA_ES || 'Ã' ||
                   NVL(C.TIP_ORIGEN_NOTA_ES,' ')
  	        FROM   LGT_NOTA_ES_CAB C, LGT_GUIA_REM G
  	        WHERE  C.COD_GRUPO_CIA = cGrupoCia_in
  	        AND    C.COD_LOCAL = cCodLocal_in
  	        AND    C.TIP_NOTA_ES = g_cTipoNotaRecepcion
            AND    G.IND_GUIA_CERRADA = 'S'
            AND    FEC_NOTA_ES_CAB BETWEEN TO_DATE(vFechaIni_in,'dd/MM/yyyy')
                                   AND     TO_DATE(vFechaFin_in,'dd/MM/yyyy')+0.99999
            AND    TIP_ORIGEN_NOTA_ES LIKE vFiltro_in
            AND    C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND    C.COD_LOCAL = G.COD_LOCAL
            AND    C.NUM_NOTA_ES = G.NUM_NOTA_ES;

	  RETURN curGuias;
  END;



	/****************************************************************************************************/

  FUNCTION INV_GET_ORIGEN_NOTA_E(cTipOrigen_in IN CHAR, cCodOrigen_in IN CHAR, cGrupoCia_in IN CHAR, cCia_in IN CHAR, cNumNotaEs_in IN CHAR DEFAULT NULL)
  RETURN VARCHAR2
  IS
  	v_vDes VARCHAR2(30);
  BEGIN
  	IF cTipOrigen_in = g_cTipoOrigenLocal THEN --LOCAL
  	  SELECT DESC_CORTA_LOCAL INTO v_vDes
  	  FROM PBL_LOCAL
  	  WHERE COD_GRUPO_CIA = cGrupoCia_in
  		  AND COD_CIA = cCia_in
  		  AND COD_LOCAL = cCodOrigen_in
  		  AND TIP_LOCAL = g_cTipoLocalVenta;
        ELSIF cTipOrigen_in = g_cTipoOrigenMatriz THEN --MATRIZ
  	  SELECT DESC_CORTA_LOCAL INTO v_vDes
  	  FROM PBL_LOCAL
  	  WHERE COD_GRUPO_CIA = cGrupoCia_in
  		AND COD_CIA = cCia_in
  		AND COD_LOCAL = cCodOrigen_in
    		AND TIP_LOCAL = g_cTipoLocalMatriz;
  	ELSIF cTipOrigen_in = g_cTipoOrigenProveedor THEN --PROVEEDOR
  	  SELECT DESC_CORTA INTO v_vDes
  	  FROM PBL_TAB_GRAL
  	  WHERE COD_APL = g_vCodPtoVenta2
  		  AND COD_TAB_GRAL = g_vGralProveedor
  		  AND LLAVE_TAB_GRAL = cCodOrigen_in;
  	ELSIF(cTipOrigen_in = g_cTipoOrigenCompetencia) THEN --COMPETENCIA
  	  /*SELECT DESC_CORTA INTO v_vDes
  	  FROM PBL_TAB_GRAL
  	  WHERE COD_APL = g_vCodPtoVenta2
  		  AND COD_TAB_GRAL = g_vGralCompetencia
  		  AND LLAVE_TAB_GRAL = cCodOrigen_in;*/
          SELECT DESC_EMPRESA INTO v_vDes
          FROM LGT_NOTA_ES_CAB
          WHERE COD_GRUPO_CIA = cGrupoCia_in
                AND COD_LOCAL = cCodOrigen_in
                AND NUM_NOTA_ES = cNumNotaEs_in;
        END IF;

  	RETURN NVL(v_vDes,' ');
  END;

  FUNCTION INV_GET_MOTIVO_NOTA_E(cCodMotivo_in IN CHAR)
  RETURN VARCHAR2
  IS
  	v_vDes VARCHAR2(30);
  BEGIN
  	SELECT DESC_CORTA INTO v_vDes
  	  FROM PBL_TAB_GRAL
  	  WHERE COD_APL = g_vCodPtoVenta2
    		AND COD_TAB_GRAL = g_vGralMotivoSalida
    		AND LLAVE_TAB_GRAL = cCodMotivo_in;

	  RETURN v_vDes;
  END;

	/****************************************************************************************************/

  --LISTA PRODUCTOS GUIA INGRESO

  FUNCTION INV_LISTA_PROD_GUIA_INGRESO(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
  	curProductos FarmaCursor;
  BEGIN
  	OPEN curProductos FOR
  	SELECT P.COD_PROD || 'Ã' ||
  	      P.DESC_PROD || 'Ã' ||
  	      DECODE(P.IND_PROD_FRACCIONABLE,'S',L.UNID_VTA,P.DESC_UNID_PRESENT) || 'Ã' ||
  	      B.NOM_LAB || 'Ã' ||
  	      TO_CHAR(L.STK_FISICO,'999,990.00')
  	FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B
  	WHERE P.COD_GRUPO_CIA = cGrupoCia_in
              AND L.COD_LOCAL = cCodLocal_in
  	      AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
  	      AND P.COD_PROD = L.COD_PROD
  	      AND B.COD_LAB = P.COD_LAB;

	  RETURN curProductos;
  END;

	/****************************************************************************************************/

  FUNCTION INV_GET_TIPO_ORIGEN_GUIA_E
  RETURN FarmaCursor
  IS
  	curOrigen FarmaCursor;
  BEGIN
  	OPEN curOrigen FOR
  	SELECT LLAVE_TAB_GRAL || 'Ã' ||
  		DESC_CORTA
  	FROM PBL_TAB_GRAL
  	WHERE COD_APL = g_vCodPtoVenta2
  	      AND COD_TAB_GRAL = g_vGralOrigenNotaEs
              AND EST_TAB_GRAL = 'A';
  	RETURN curOrigen;
  END;

  FUNCTION INV_GET_TIPO_DOCUMENTO(cGrupoCia_in IN CHAR, vTipo_in IN VARCHAR2 DEFAULT 'INV')
  RETURN FarmaCursor
  IS
  	curDocs FarmaCursor;
  BEGIN
  	OPEN curDocs FOR
  	SELECT TIP_COMP || 'Ã' ||
  		DESC_COMP
  	FROM VTA_TIP_COMP
  	WHERE COD_GRUPO_CIA = cGrupoCia_in
		AND ((vTipo_in = 'MD'
				 AND TIP_COMP IN(02,03) )
			  OR (vTipo_in = 'INV'
				 AND 1=1 ) )
	 ;

	  RETURN curDocs;
  END;

	/****************************************************************************************************/

  --AGREGA CABECERA GUIA INGRESO

  FUNCTION INV_AGREGA_CAB_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  				    vFechaGuia_in IN VARCHAR2,cTipDoc_in IN CHAR,cNumDoc_in IN CHAR,cTipOrigen_in IN CHAR, vCodOrigen_in IN VARCHAR2,nCantItems_in IN NUMBER,nValTotal_in IN NUMBER,
  				    vNombreTienda_in IN VARCHAR2, vCiudadTienda_in IN VARCHAR2, vRucTienda_in IN VARCHAR2,
                                    vUsu_in IN VARCHAR2)
  RETURN CHAR
  IS
  	v_nNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
  BEGIN
  	v_nNumNota := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumNotaEs),10,'0','I' );

	  INSERT INTO LGT_NOTA_ES_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES, FEC_NOTA_ES_CAB,TIP_DOC,NUM_DOC,TIP_ORIGEN_NOTA_ES,COD_ORIGEN_NOTA_ES,CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB, TIP_NOTA_ES,USU_CREA_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,
                                        DESC_EMPRESA,
                                        RUC_EMPRESA,
                                        DIR_EMPRESA)
	  VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumNota, TO_DATE(vFechaGuia_in,'dd/MM/yyyy'),cTipDoc_in,TRIM(cNumDoc_in),cTipOrigen_in,vCodOrigen_in,nCantItems_in,nValTotal_in, g_cTipoNotaEntrada,vUsu_in,cCodLocal_in,
                  vNombreTienda_in,
                  vRucTienda_in,
                  vCiudadTienda_in);

          --AGREGADO 13/06/2006 ERIOS
          --MODIFICADO 23/06/2006 ERIOS
          IF (cTipOrigen_in = g_cTipoOrigenLocal) OR (cTipOrigen_in = g_cTipoOrigenMatriz) THEN
          --AGREGA GUIA
            INSERT INTO LGT_GUIA_REM(COD_GRUPO_CIA,
                                     COD_LOCAL,
                                     NUM_NOTA_ES,
                                     SEC_GUIA_REM,
                                     NUM_GUIA_REM,
                                     USU_CREA_GUIA_REM,
                                     FEC_CREA_GUIA_REM)
            VALUES (cCodGrupoCia_in,cCodLocal_in,v_nNumNota,1,TRIM(cNumDoc_in),vUsu_in,SYSDATE);
          END IF;
  	Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumNotaEs, vUsu_in);

  	RETURN v_nNumNota;
  END;

   /****************************************************************************************************/

  --AGREGA DETALLE GUIA INGRESO

  PROCEDURE INV_AGREGA_DET_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cTipOrigen_in IN CHAR,
  				    cCodProd_in IN CHAR, nValPrecUnit_in IN NUMBER, nValPrecTotal_in IN NUMBER, nCantMov_in IN NUMBER, vFecNota_in IN VARCHAR2, vFecVecProd_in IN VARCHAR2, vNumLote_in IN VARCHAR2,
                                    cCodMotKardex_in IN CHAR,cTipDocKardex_in IN CHAR,
  				    vValFrac_in IN NUMBER,vUsu_in IN VARCHAR2,
              nCantSolicitado varchar2 default null,     --ASOSA - 15/08/2014
              nOrdenCompra char default null   --ASOSA - 24/09/2014
              )
  AS
    nSec LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;
    v_nValFrac LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
    v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
    v_nStkFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    vAmountReceivedUntilNow number(5) := 0;   --ASOSA - 24/09/2014


     vCantExceso number(9,3) := 0;
     /*
     TO_NUMBER(nCantMov_in, '999,999.999') -     --ASOSA - 15/08/2014
                                                          TO_NUMBER(nvl(nCantSolicitado,nCantMov_in), '999,999.999')
                                                          ;
                                                          */

    v_cIndProdVirtual CHAR(1);
  BEGIN

  --INI ASOSA - 24/09/2014
  IF nCantSolicitado IS NOT NULL AND nOrdenCompra IS NOT NULL THEN --ASOSA - 03/10/2014
      SELECT NVL(FF.CANT_RECEP,0)
      INTO vAmountReceivedUntilNow
      FROM LGT_OC_DET FF
      WHERE FF.COD_GRUPO_CIA = cCodGrupoCia_in
      AND FF.COD_LOCAL = cCodLocal_in
      AND FF.COD_OC = nOrdenCompra
      AND FF.COD_PROD = cCodProd_in;

      vCantExceso := vAmountReceivedUntilNow -
                                             TO_NUMBER(nCantSolicitado, '999,999.999') ;
      IF vCantExceso < 0 THEN
                     vCantExceso := 0;
      END IF;
    END IF;
      --FIN ASOSA - 24/09/2014

  	SELECT COUNT(SEC_DET_NOTA_ES)+1 INTO nSec
  	FROM LGT_NOTA_ES_DET
  	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
  	      AND COD_LOCAL = cCodLocal_in
  	      AND NUM_NOTA_ES = cNumNota_in;

      SELECT VAL_FRAC_LOCAL,NVL(TRIM(UNID_VTA),(SELECT DESC_UNID_PRESENT
                                                FROM LGT_PROD
                                                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND COD_PROD = cCodProd_in )),STK_FISICO
      INTO v_nValFrac,v_vDescUnidVta, v_nStkFisico
  	FROM LGT_PROD_LOCAL
  	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
  	      AND COD_LOCAL = cCodLocal_in
  	      AND COD_PROD = cCodProd_in;

    --VALIDA FRACCION
    IF v_nValFrac <> vValFrac_in THEN
       RAISE_APPLICATION_ERROR(-20083,'LA FRACCION HA CAMBIADO PARA EL PRODUCTO:'||cCodProd_in||'. VUELVA A INGRESAR EL PRODUCTO.');
    END IF;

  	INSERT INTO LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAl,NUM_NOTA_ES,SEC_DET_NOTA_ES,COD_PROD,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,FEC_NOTA_ES_DET,FEC_VCTO_PROD,NUM_LOTE_PROD, USU_CREA_NOTA_ES_DET, VAL_FRAC,DESC_UNID_VTA,
                CANT_EXCESO --ASOSA - 15/08/2014
    )
  	VALUES(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,nSec, cCodProd_in,nValPrecUnit_in,nValPrecTotal_in,
                nCantMov_in, TO_DATE(vFecNota_in,'dd/MM/yyyy'),TO_DATE(vFecVecProd_in,'dd/MM/yyyy'),vNumLote_in, vUsu_in, v_nValFrac,v_vDescUnidVta,
                vCantExceso     --ASOSA - 15/08/2014
                );

  	SELECT DECODE(COUNT(*),0,INDICADOR_NO,INDICADOR_SI)
      INTO v_cIndProdVirtual
    FROM LGT_PROD_VIRTUAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_PROD = cCodProd_in;

        --AGREGADO 23/06/2006 ERIOS
        IF (cTipOrigen_in = g_cTipoOrigenLocal) OR (cTipOrigen_in = g_cTipoOrigenMatriz) THEN
          --ACTUALIZA GUIA
          UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = vUsu_in, FEC_MOD_NOTA_ES_DET = SYSDATE,
                SEC_GUIA_REM = 1
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_NOTA_ES = cNumNota_in
                AND SEC_DET_NOTA_ES = nSec;
        END IF;

  	--INSERTAR KARDEX
    IF v_cIndProdVirtual = INDICADOR_SI THEN
      INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in ,
                                    cCodLocal_in,
                                    cCodProd_in,
                                    cCodMotKardex_in,
                                    cTipDocKardex_in,
                                    cNumNota_in,
                                    --nCantMov_in,
                                    (nCantMov_in - vCantExceso),  --ASOSA - 25/09/2014
                                    --nCantSolicitado, --ASOSA - 15/08/2014
                                    v_nValFrac,
                                    v_vDescUnidVta,
                                    vUsu_in,
                                    COD_NUMERA_SEC_KARDEX,'');

                                    v_nStkFisico := v_nStkFisico + nCantMov_in - vCantExceso;     --ASOSA - 25/09/2014

                                    --INI ASOSA - 15/08/2014
                                    IF vCantExceso IS NOT NULL AND vCantExceso > 0 THEN
                                        INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in ,
                                        cCodLocal_in,
                                        cCodProd_in,
                                        TIPO_MOV_SOBRANTE,
                                        cTipDocKardex_in,
                                        cNumNota_in,
                                        vCantExceso,
                                        v_nValFrac,
                                        v_vDescUnidVta,
                                        vUsu_in,
                                        COD_NUMERA_SEC_KARDEX,'');
                                    END IF;
                                    --FIN ASOSA - 15/08/2014

    ELSE
        INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                                    cCodLocal_in,
                                    cCodProd_in,
                                    cCodMotKardex_in,
                                    cTipDocKardex_in,
                                    cNumNota_in,
                                    v_nStkFisico,
                                    --nCantMov_in,
                                    (nCantMov_in - vCantExceso),  --ASOSA - 25/09/2014
                                    --nCantSolicitado, --ASOSA - 15/08/2014
                                    v_nValFrac,
                                    v_vDescUnidVta,
                                    vUsu_in,
                                    COD_NUMERA_SEC_KARDEX,'');

                                    v_nStkFisico := v_nStkFisico + nCantMov_in - vCantExceso;     --ASOSA - 25/09/2014

                                    --INI ASOSA - 15/08/2014
                                    IF vCantExceso IS NOT NULL AND vCantExceso > 0 THEN
                                          INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                                          cCodLocal_in,
                                          cCodProd_in,
                                          TIPO_MOV_SOBRANTE,
                                          cTipDocKardex_in,
                                          cNumNota_in,
                                          v_nStkFisico,
                                          vCantExceso,
                                          v_nValFrac,
                                          v_vDescUnidVta,
                                          vUsu_in,
                                          COD_NUMERA_SEC_KARDEX,'');
                                    END IF;
                                    --FIN ASOSA - 15/08/2014

       UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
                STK_FISICO = STK_FISICO + nCantMov_in
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;
     END IF;
        --AGREGADO 13/06/2006 ERIOS
        --MODIFICADO 23/06/2006 ERIOS
        INV_ACT_KARDEX_GUIA_REC(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,1,
                                cCodProd_in,
                                vUsu_in,cTipOrigen_in);

         INV_ACT_KARDEX_GUIA_REC(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,1,
                                cCodProd_in,
                                vUsu_in,cTipOrigen_in);
  END;

   /****************************************************************************************************/

  FUNCTION INV_GET_DET_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cNumDoc_in IN CHAR, cTipoNota_in IN CHAR)
  RETURN FarmaCursor
  IS
  	curDet FarmaCursor;
  BEGIN
    IF cTipoNota_in = g_cTipoNotaEntrada THEN
      OPEN curDet FOR
  	SELECT D.COD_PROD || 'Ã' ||
  	      P.DESC_PROD || 'Ã' ||
--  	      DECODE(P.IND_PROD_FRACCIONABLE,'S',L.UNID_VTA,P.DESC_UNID_PRESENT) || 'Ã' ||
  	      DESC_UNID_VTA || 'Ã' ||
  	      B.NOM_LAB || 'Ã' ||
  	      D.CANT_MOV || 'Ã' ||
  	      TRIM(TO_CHAR(D.VAL_PREC_UNIT,'999,990.000')) || 'Ã' ||
  	      TRIM(TO_CHAR(D.VAL_PREC_TOTAL,'999,990.000')) || 'Ã' ||
  	      ' ' || 'Ã' ||
  	      ' ' || 'Ã' ||
          D.VAL_FRAC
  	FROM LGT_NOTA_ES_DET D, LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B
  	WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
  	      AND D.COD_LOCAL = cCodLocal_in
  	      AND D.NUM_NOTA_ES = cNumNota_in
              AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
  	      AND D.COD_LOCAL = L.COD_LOCAL
  	      AND D.COD_PROD = L.COD_PROD
  	      AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
  	      AND L.COD_PROD = P.COD_PROD
  	      AND P.COD_LAB = B.COD_LAB;
    ELSIF cTipoNota_in = g_cTipoNotaRecepcion THEN
      OPEN curDet FOR
  	SELECT D.COD_PROD || 'Ã' ||
  	      P.DESC_PROD || 'Ã' ||
--  	      DECODE(P.IND_PROD_FRACCIONABLE,'S',L.UNID_VTA,P.DESC_UNID_PRESENT) || 'Ã' ||
  	      DESC_UNID_VTA || 'Ã' ||
  	      B.NOM_LAB || 'Ã' ||
  	      D.CANT_MOV || 'Ã' ||
  	      TRIM(TO_CHAR(D.VAL_PREC_UNIT,'999,990.000')) || 'Ã' ||
  	      TRIM(TO_CHAR(D.VAL_PREC_TOTAL,'999,990.000')) || 'Ã' ||
  	      ' ' || 'Ã' ||
  	      ' ' || 'Ã' ||
          D.VAL_FRAC
  	FROM LGT_NOTA_ES_DET D, LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B, LGT_GUIA_REM G
  	WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
  	      AND D.COD_LOCAL = cCodLocal_in
  	      AND D.NUM_NOTA_ES = cNumNota_in
              AND G.NUM_GUIA_REM = cNumDoc_in
  	      AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
  	      AND D.COD_LOCAL = L.COD_LOCAL
  	      AND D.COD_PROD = L.COD_PROD
  	      AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
  	      AND L.COD_PROD = P.COD_PROD
  	      AND P.COD_LAB = B.COD_LAB
              AND D.COD_GRUPO_CIA = G.COD_GRUPO_CIA
              AND D.COD_LOCAL = G.COD_LOCAL
              AND D.NUM_NOTA_ES = G.NUM_NOTA_ES
              AND D.SEC_GUIA_REM = G.SEC_GUIA_REM;
    END IF;

    RETURN curDet;
  END;

	/****************************************************************************************************/

 FUNCTION INV_GET_CAB_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR, cNumDoc_in IN CHAR)
  RETURN FarmaCursor
  IS
  	curCab FarmaCursor;
  BEGIN
  	OPEN curCab FOR
  	SELECT TO_CHAR(C.FEC_CREA_NOTA_ES_CAB,'dd/MM/yyyy') || 'Ã' ||
                D.DESC_COMP  || 'Ã' ||
                cNumDoc_in || 'Ã' ||
                NVL(O.DESC_CORTA,' ') || 'Ã' ||
                NVL(GET_DESC_ORIGEN(C.COD_GRUPO_CIA,C.TIP_ORIGEN_NOTA_ES,C.COD_ORIGEN_NOTA_ES,C.NUM_NOTA_ES),' ') || 'Ã' ||
                C.EST_NOTA_ES_CAB
  	FROM LGT_NOTA_ES_CAB C, VTA_TIP_COMP D, PBL_TAB_GRAL O
  	WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
  	      AND C.COD_LOCAL = cCodLocal_in
  	      AND C.NUM_NOTA_ES = cNumNota_in
  	      AND O.COD_APL = g_vCodPtoVenta2
  	      AND O.COD_TAB_GRAL = g_vGralOrigenNotaEs
  	      AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
  	      AND D.TIP_COMP = C.TIP_DOC
  	      AND O.LLAVE_TAB_GRAL = C.TIP_ORIGEN_NOTA_ES;

	  RETURN curCab;
  END;


	/****************************************************************************************************/

 FUNCTION GET_DESC_ORIGEN(cCodGrupoCia IN CHAR, cTipoMaestro_in IN CHAR,cCodBusqueda_in IN CHAR,cNumNotaEs_in IN CHAR DEFAULT NULL)
  RETURN VARCHAR2
  IS
  	v_vDesc VARCHAR2(30);
  BEGIN
  	IF(cTipoMaestro_in = g_cTipoOrigenLocal) THEN --LOCAL
  		SELECT NVL(DESC_CORTA_LOCAL,' ') INTO v_vDesc
  			    FROM  PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia
  		  AND TIP_LOCAL = g_cTipoLocalVenta
  		  AND COD_LOCAL = cCodBusqueda_in;
    ELSIF(cTipoMaestro_in = g_cTipoOrigenMatriz) THEN --MATRIZ
      SELECT NVL(DESC_CORTA_LOCAL,' ') INTO v_vDesc
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia
  		  AND TIP_LOCAL = g_cTipoLocalMatriz
  		  AND COD_LOCAL = cCodBusqueda_in;
    ELSIF(cTipoMaestro_in = g_cTipoOrigenProveedor) THEN --PROVEEDOR
      SELECT NVL(DESC_CORTA,' ') INTO v_vDesc
      FROM  PBL_TAB_GRAL
	    WHERE COD_APL = g_vCodPtoVenta2
  		  AND COD_TAB_GRAL = g_vGralProveedor
  		  AND LLAVE_TAB_GRAL = cCodBusqueda_in;
    ELSIF(cTipoMaestro_in = g_cTipoOrigenCompetencia) THEN --COMPETENCIA
      /*SELECT NVL(DESC_CORTA,' ') INTO v_vDesc
      FROM  PBL_TAB_GRAL
	    WHERE COD_APL = g_vCodPtoVenta2
  		  AND COD_TAB_GRAL = g_vGralCompetencia
  		  AND LLAVE_TAB_GRAL = cCodBusqueda_in;*/
      SELECT NVL(DESC_EMPRESA,' ') INTO v_vDesc
      FROM LGT_NOTA_ES_CAB
      WHERE COD_GRUPO_CIA = cCodGrupoCia
            AND COD_LOCAL = cCodBusqueda_in
            AND NUM_NOTA_ES = cNumNotaEs_in;
    END IF;

	  RETURN v_vDesc;

  END;

	/****************************************************************************************************/

  FUNCTION INV_LISTA_TRANSFERENCIA(cGrupoCia_in IN CHAR,
                                   cCia_in      IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   vFiltro_in   IN CHAR)
    RETURN FarmaCursor
  IS
  	curGuias FarmaCursor;
  BEGIN
       OPEN curGuias FOR
            SELECT NC.NUM_NOTA_ES || 'Ã' ||
                   NVL(INV_GET_DESTINO_TRANSFERENCIA(NC.TIP_ORIGEN_NOTA_ES,NC.COD_DESTINO_NOTA_ES,cGrupoCia_in,cCia_in),
                   ' ') || 'Ã' ||
                   TO_CHAR(NC.FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                   NC.CANT_ITEMS || 'Ã' ||
                   DECODE(NC.EST_NOTA_ES_CAB,'N','ANULADO'
                                             ,'C','CONFIRMADO'
                                             ,'M','MATRIZ'
                                             ,'L','LOCAL'
                                             ,'X','ERROR'
                                             ,'R','RECHAZADO'
                                             ,'A','ACEPTADO'
                                             ,'P','POR CONFIRMAR'
                                             ,' ') || 'Ã' ||
                   NVL(GR.NUM_GUIA_REM,' ')  || 'Ã' ||
                   DECODE(GR.IND_GUIA_IMPRESA ,'S','SI'
                                              ,'N',' ') || 'Ã' ||
                   K.DESC_CORTA_MOT_KARDEX || 'Ã' ||
                   DECODE(NC.FEC_PROCESO_SAP,NULL,' ','SI') || 'Ã' ||
                   NVL(TIP_NOTA_ES,' ') || 'Ã' ||
                   NVL(TIP_ORIGEN_NOTA_ES,' ')
            FROM   LGT_NOTA_ES_CAB NC,
                   LGT_GUIA_REM  GR,
                   LGT_MOT_KARDEX K
  	        WHERE  NC.COD_GRUPO_CIA = cGrupoCia_in
  	        AND    NC.COD_LOCAL = cCodLocal_in
  	        AND    NC.TIP_NOTA_ES = g_cTipoNotaSalida
			AND    NC.TIP_ORIGEN_NOTA_ES IN (g_cTipoOrigenLocal,g_cTipoOrigenMatriz)
            AND    K.COD_MOT_KARDEX LIKE vFiltro_in
            AND    NC.COD_GRUPO_CIA = GR.COD_GRUPO_CIA
            AND    NC.COD_LOCAL = GR.COD_LOCAL
            AND    NC.NUM_NOTA_ES = GR.NUM_NOTA_ES
            AND    NC.COD_GRUPO_CIA = K.COD_GRUPO_CIA
            AND    NC.TIP_MOT_NOTA_ES = K.COD_MOT_KARDEX;
  	RETURN curGuias;
  END;



	/****************************************************************************************************/

  FUNCTION INV_GET_DESTINO_TRANSFERENCIA(cTipOrigen_in IN CHAR, cCodOrigen_in IN CHAR, cGrupoCia_in IN CHAR, cCia_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vDes VARCHAR2(30);
  BEGIN
    IF cTipOrigen_in = g_cTipoOrigenLocal THEN --LOCAL
      SELECT DESC_CORTA_LOCAL INTO v_vDes
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            AND COD_CIA = cCia_in
            AND COD_LOCAL = cCodOrigen_in
            AND TIP_LOCAL = g_cTipoLocalVenta;
    ELSIF cTipOrigen_in = g_cTipoOrigenMatriz THEN --MATRIZ
      SELECT DESC_CORTA_LOCAL INTO v_vDes
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            --AND COD_CIA = cCia_in
            AND COD_LOCAL = cCodOrigen_in
            AND TIP_LOCAL = g_cTipoLocalMatriz;
    ELSIF cTipOrigen_in = g_cTipoOrigenProveedor THEN --PROVEEDOR
      SELECT NVL(NOM_PROV,' ') INTO v_vDes
      FROM  LGT_PROV
	    WHERE COD_PROV = cCodOrigen_in;
    END IF;

    RETURN v_vDes;
  END;

   /****************************************************************************************************/

  --LISTA PRODUCTOS TRANSFERENCIA

  FUNCTION INV_LISTA_PROD_TRANSFERENCIA(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curProductos FarmaCursor;
  BEGIN
    OPEN curProductos FOR
    SELECT P.COD_PROD || 'Ã' ||
          P.DESC_PROD || 'Ã' ||
          DECODE(P.IND_PROD_FRACCIONABLE,'S',L.UNID_VTA,P.DESC_UNID_PRESENT) || 'Ã' ||
          B.NOM_LAB || 'Ã' ||
          TO_CHAR(L.STK_FISICO ,'999,990.00') || 'Ã' ||
          L.VAL_FRAC_LOCAL || 'Ã' ||
          --TO_CHAR(L.VAL_PREC_VTA,'999,990.00') || 'Ã' ||
          L.VAL_PREC_VTA || 'Ã' ||
          TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
          ' '
    FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B
    WHERE P.COD_GRUPO_CIA = cGrupoCia_in
          AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND P.COD_PROD = L.COD_PROD
          AND L.COD_LOCAL = cCodLocal_in
          AND B.COD_LAB = P.COD_LAB;

    RETURN curProductos;

  END;

   /****************************************************************************************************/

  FUNCTION INV_GET_TIPO_TRANSFERENCIA
  RETURN FarmaCursor
  IS
  	curDest FarmaCursor;
  BEGIN
  	OPEN curDest FOR
  	SELECT LLAVE_TAB_GRAL || 'Ã' ||
  		DESC_CORTA
  	FROM PBL_TAB_GRAL
  	WHERE COD_APL = g_vCodPtoVenta
  	      AND COD_TAB_GRAL = g_vGralDestinoNotaEs
              AND EST_TAB_GRAL = 'A';
  	RETURN curDest;
  END;

  FUNCTION INV_GET_MOTIVO_TRANSFERENCIA
  RETURN FarmaCursor
  IS
  	curMotivo FarmaCursor;
  BEGIN
  	OPEN curMotivo FOR
  	SELECT LLAVE_TAB_GRAL || 'Ã' ||
  		DESC_CORTA
  	FROM PBL_TAB_GRAL
  	WHERE COD_APL = g_vCodPtoVenta2
  	      AND COD_TAB_GRAL = g_vGralMotivoSalida;
  	RETURN curMotivo;
  END;

 /****************************************************************************************************/

  FUNCTION INV_LISTA_GUIA_RECEP(cGrupoCia_in IN CHAR,
  		   					    cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
  	curGuias FarmaCursor;
  BEGIN
  	OPEN curGuias FOR
  	SELECT NUM_NOTA_ES     					 		         || 'Ã' ||
  	       TO_CHAR(FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS')  || 'Ã' ||
	       EST_NOTA_ES_CAB     			  			   		 || 'Ã' ||
	       CANT_ITEMS  		   						   		 || 'Ã' ||
	       TIP_ORIGEN_NOTA_ES  						   		 || 'Ã' ||
	       CASE NVL(EST_RECEPCION,'E')
              WHEN 'P' THEN 'En proceso'
	          WHEN 'E'  THEN 'Emitido'
						WHEN 'C' THEN 'Cerrado'
						WHEN 'N' THEN 'Anulado'
			END  || 'Ã' ||
	        NVL(EST_RECEPCION,'E')
  	FROM LGT_NOTA_ES_CAB
  	WHERE COD_GRUPO_CIA = cGrupoCia_in
  	      AND COD_LOCAL = cCodLocal_in
  	      AND TIP_NOTA_ES = g_cTipoNotaRecepcion
    	  AND EST_RECEPCION IN ('E','P') ;
    RETURN curGuias;
  END;

/****************************************************************************************************/

  --AGREGA CABECERA TRANSFERENCIA

  FUNCTION INV_AGREGA_CAB_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                    vTipDestino_in IN CHAR,cCodDestino_in IN VARCHAR2,cTipMotivo_in IN CHAR,vDesEmp_in IN VARCHAR2, vRucEmp_in IN VARCHAR2, vDirEmp_in IN VARCHAR2,vDesTran_in IN VARCHAR2, vRucTran_in IN VARCHAR2, vDirTran_in IN VARCHAR2,vPlacaTran_in IN VARCHAR2,nCantItems_in IN NUMBER,nValTotal_in IN NUMBER,
                                      vUsu_in IN VARCHAR2,cCodMotTransInterno_in CHAR,
                                      codOc IN VARCHAR2 DEFAULT NULL--ASOSA - 18/09/2014
                                      )
  RETURN CHAR
  IS
  	v_nNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
  BEGIN
  	v_nNumNota := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumNotaEs),10,'0','I' );

	  INSERT INTO LGT_NOTA_ES_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES, FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB, COD_ORIGEN_NOTA_ES,TIP_ORIGEN_NOTA_ES,COD_DESTINO_NOTA_ES,TIP_MOT_NOTA_ES,DESC_EMPRESA,RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS, CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB, TIP_NOTA_ES,USU_CREA_NOTA_ES_CAB,TIP_DOC,COD_MOTIVO_INTERNO_TRANS)
	  VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumNota, SYSDATE,'P', cCodLocal_in,vTipDestino_in,cCodDestino_in,cTipMotivo_in,vDesEmp_in, vRucEmp_in , vDirEmp_in,vDesTran_in, vRucTran_in, vDirTran_in,vPlacaTran_in, nCantItems_in,nValTotal_in, g_cTipoNotaSalida,vUsu_in,g_cTipCompGuia,cCodMotTransInterno_in);

  	Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumNotaEs, vUsu_in);
    --INI ASOSA - 18/09/2014
    IF codOc IS NOT NULL THEN
        UPDATE LGT_OC_CAB_RECEP
            SET PROC_OC_CAB = 'T'
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL       = cCodLocal_in
            AND COD_OC          = codOc
            AND EST_OC_CAB      = 'A';

            UPDATE LGT_OC_CAB
            SET PROC_OC_CAB = 'T'
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL       = cCodLocal_in
            AND COD_OC          = codOc
            AND EST_OC_CAB      = 'A';
      END IF;

    --INI ASOSA - 18/09/2014

  	RETURN v_nNumNota;

  END;
-- transferencia desde local
  PROCEDURE INV_AGREGA_DET_TRANSFERENCIA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumNota_in      IN CHAR,
                                         cCodProd_in      IN CHAR,
                                         nValPrecUnit_in  IN NUMBER,
                                         nValPrecTotal_in IN NUMBER,

                                         nCantMov_in      IN NUMBER,
                                         vFecVecProd_in   IN VARCHAR2,
                                         vNumLote_in      IN VARCHAR2,
                                         cCodMotKardex_in IN CHAR,
                                         cTipDocKardex_in IN CHAR,
                                         vValFrac_in      IN NUMBER,
                                         vUsu_in          IN VARCHAR2,
                                         vTipDestino_in   IN CHAR,
                                         cCodDestino_in   IN CHAR,
                                         vIndFrac_in      IN CHAR DEFAULT 'N')
    AS
      v_dFechaNota DATE;
      nSec LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;
    	v_nValFrac LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
    	v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
          v_nStkFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
      v_cInd CHAR(1);
    BEGIN
      SELECT FEC_NOTA_ES_CAB INTO v_dFechaNota
      FROM LGT_NOTA_ES_CAB
      WHERE COD_GRUPO_CIA =  cCodGrupoCia_in
            AND COD_LOCAL =  cCodLocal_in
            AND NUM_NOTA_ES = cNumNota_in;

      SELECT COUNT(SEC_DET_NOTA_ES)+1 INTO nSec
    	FROM LGT_NOTA_ES_DET
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND NUM_NOTA_ES = cNumNota_in;

      SELECT VAL_FRAC_LOCAL,NVL(TRIM(UNID_VTA),(SELECT DESC_UNID_PRESENT
                                                FROM LGT_PROD
                                                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND COD_PROD = cCodProd_in )),STK_FISICO INTO

  v_nValFrac,v_vDescUnidVta,v_nStkFisico
    	FROM LGT_PROD_LOCAL
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;

      IF v_nValFrac <> vValFrac_in THEN
         RAISE_APPLICATION_ERROR(-20083,'LA FRACCION HA CAMBIADO PARA EL PRODUCTO:'||cCodProd_in||'. VUELVA A INGRESAR EL

  PRODUCTO.');
      END IF;
      --AGREGADO 07/09/2006 ERIOS
      --VALIDA QUE LA FRACCION DEL LOCAL DESTINO, PERMITA ACEPTAR LA TRANSFERENCIA
      --SI HAY CONEXION CON MATRIZ
      IF vTipDestino_in = g_cTipoOrigenLocal THEN
   /*     EXECUTE IMMEDIATE 'BEGIN  PTOVENTA_TRANSF.GET_FRACCION_LOCAL@XE_000(:1,:2,:3,:4,:5,:6); END;'
                          USING cCodGrupoCia_in,cCodDestino_in,cCodProd_in,nCantMov_in,v_nValFrac,IN OUT v_cInd;
        --v_cInd := PTOVENTA_TRANSF.GET_FRACCION_LOCAL(cCodGrupoCia_in,cCodDestino_in,cCodProd_in,nCantMov_in,v_nValFrac);
   */
        IF vIndFrac_in != 'V' THEN
          RAISE_APPLICATION_ERROR(-20081,'ALGUNOS PRODUCTOS NO PUEDEN SER TRANSFERIDOS, DEBIDO A LA FRACCION ACTUAL DEL LOCAL

  DESTINO.');
        END IF;
      END IF;

    	INSERT INTO LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAl,NUM_NOTA_ES,SEC_DET_NOTA_ES,

  COD_PROD,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,FEC_NOTA_ES_DET,FEC_VCTO_PROD,NUM_LOTE_PROD, USU_CREA_NOTA_ES_DET,

  VAL_FRAC,DESC_UNID_VTA)
    	VALUES(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,nSec,

  cCodProd_in,nValPrecUnit_in,nValPrecTotal_in,nCantMov_in,v_dFechaNota,TO_DATE(vFecVecProd_in,'dd/MM/yyyy'),vNumLote_in,

  vUsu_in, v_nValFrac,v_vDescUnidVta);

      --INSERTAR KARDEX
      Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                                      cCodLocal_in,
                                      cCodProd_in,
                                      cCodMotKardex_in,
                                      cTipDocKardex_in,
                                      cNumNota_in,
                                      v_nStkFisico,
                                      nCantMov_in*-1,
                                      v_nValFrac,
                                      v_vDescUnidVta,
                                      vUsu_in,
                                      COD_NUMERA_SEC_KARDEX,'');

      --DESCONTAR STOCK FISICO,STOCK COMPROMETIDO
      UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
                STK_FISICO = STK_FISICO - nCantMov_in
    	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    	      AND COD_LOCAL = cCodLocal_in
    	      AND COD_PROD = cCodProd_in;
      --BORRAR REGISTRO RESPALDO

    END;

   /****************************************************************************************************/

  FUNCTION INV_LISTA_DET_GUIA_RECEP(cGrupoCia_in IN CHAR,
  		   							cCodLocal_in IN CHAR,
									cNumNota_in  IN CHAR,
                                                                        cNumGuia_in IN CHAR)
  RETURN FarmaCursor
  IS
  curDet FarmaCursor;
  BEGIN
  OPEN curDet FOR

       SELECT NVL(TO_CHAR(DET.COD_PROD),' ')	        	|| 'Ã' ||
	   NVL(TO_CHAR(PROD.DESC_PROD),' ')			   	    || 'Ã' ||
	   NVL(TO_CHAR(DESC_UNID_VTA),' ')						|| 'Ã' ||
	   NVL(TO_CHAR(LAB.NOM_LAB),' ')						|| 'Ã' ||
          CANT_ENVIADA_MATR                       || 'Ã' ||
          DECODE(FEC_MOD_NOTA_ES_DET,NULL,' ',TO_CHAR(CANT_MOV))                                || 'Ã' ||
          DECODE(FEC_MOD_NOTA_ES_DET,NULL,' ',TO_CHAR(CANT_ENVIADA_MATR - CANT_MOV))	|| 'Ã' ||
	  NVL(TO_CHAR(IND_PROD_AFEC),' ')						|| 'Ã' ||
           (SELECT NUM_GUIA_REM
            FROM LGT_GUIA_REM
            WHERE COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                  AND COD_LOCAL = DET.COD_LOCAL
                  AND NUM_NOTA_ES = DET.NUM_NOTA_ES
                  AND SEC_GUIA_REM = DET.SEC_GUIA_REM
           )
           || 'Ã' ||
	   NVL(TO_CHAR(VAL_FRAC),' ')							|| 'Ã' ||
	   NVL(TO_CHAR(STK_FISICO),' ')							|| 'Ã' ||
	   NVL(TO_CHAR(SEC_DET_NOTA_ES),' ')					|| 'Ã' ||
	   TO_CHAR( TO_CHAR(NVL(NUM_PAG_RECEP,0),g_cCodMatriz) || NVL(PROD.DESC_PROD,' ') )|| 'Ã' ||
            NVL(TO_CHAR(NUM_PAG_RECEP),' ')
FROM  LGT_NOTA_ES_DET DET,
  	  LGT_LAB LAB,
	  LGT_PROD_LOCAL PRODLOC,
	  LGT_PROD PROD,
          LGT_GUIA_REM G
WHERE
	  DET.COD_GRUPO_CIA=cGrupoCia_in 		   AND
	  DET.COD_LOCAL=cCodLocal_in     		   AND
	  DET.NUM_NOTA_ES=cNumNota_in			   AND
          G.NUM_GUIA_REM = cNumGuia_in AND
	  DET.COD_GRUPO_CIA=PRODLOC.COD_GRUPO_CIA  AND
	  DET.COD_LOCAL=PRODLOC.COD_LOCAL          AND
	  DET.COD_PROD=PRODLOC.COD_PROD			   AND
	  PRODLOC.COD_GRUPO_CIA=PROD.COD_GRUPO_CIA AND
	  PRODLOC.COD_PROD=PROD.COD_PROD		   AND
	  PROD.COD_LAB=LAB.COD_LAB
          AND DET.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND DET.COD_LOCAL = G.COD_LOCAL
          AND DET.NUM_NOTA_ES = G.NUM_NOTA_ES
          AND DET.SEC_GUIA_REM = G.SEC_GUIA_REM;

/*ORDER BY NVL(TO_CHAR(NUM_PAG_RECEP),' '),
	  	 NVL(TO_CHAR(PROD.DESC_PROD),' ');*/


  RETURN curDet;
  END;

   /****************************************************************************************************/

  PROCEDURE INV_ACT_CAB_GUIA_RECEP(cGrupoCia_in IN CHAR,
  								   cCodLocal_in IN CHAR,
								   cNumNota_in  IN CHAR,
								   cIdUsu_in    IN CHAR)
  IS
  vEstRecep CHAR(1);
  BEGIN
    vEstRecep :=INV_OBTIENE_EST_RECEP_GUIA(cGrupoCia_in, cCodLocal_in ,cNumNota_in);
   UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB=cIdUsu_in,FEC_MOD_NOTA_ES_CAB=SYSDATE,
          EST_RECEPCION=vEstRecep
   WHERE COD_GRUPO_CIA=cGrupoCia_in AND
  		COD_LOCAL=cCodLocal_in	   AND
		NUM_NOTA_ES=cNumNota_in;
   END;

  /****************************************************************************************************/

  FUNCTION INV_OBTIENE_EST_RECEP_GUIA(cGrupoCia_in IN CHAR,
  		   							  cCodLocal_in IN CHAR,
									  cNumNota_in  IN CHAR)
  RETURN CHAR
  IS
	  vCantTotal NUMBER;
	  vCantAfectados NUMBER;
	  vEstNeo CHAR(1);
	  vEstOrig CHAR(1);
  BEGIN

  SELECT COUNT(*)
  INTO vCantAfectados
  FROM   LGT_NOTA_ES_DET
  WHERE  COD_GRUPO_CIA = cGrupoCia_in   AND
  		 COD_LOCAL	   = cCodLocal_in	AND
		 NUM_NOTA_ES   = cNumNota_in    AND
		 IND_PROD_AFEC = 'S';

  SELECT COUNT(*)
  INTO vCantTotal
  FROM LGT_NOTA_ES_DET
  WHERE COD_GRUPO_CIA = cGrupoCia_in AND
  		COD_LOCAL	  = cCodLocal_in AND
		NUM_NOTA_ES	  = cNumNota_in;

  SELECT EST_RECEPCION
  INTO vEstOrig
  FROM LGT_NOTA_ES_CAB
  WHERE COD_GRUPO_CIA=cGrupoCia_in AND
  		COD_LOCAL=cCodLocal_in	   AND
		NUM_NOTA_ES=cNumNota_in;

	IF 	vEstOrig<>'N' THEN
		BEGIN
			 IF vCantTotal=vCantAfectados THEN
  	   		 	vEstNeo:='C';--CERRADA
			 END IF;

			 IF vCantAfectados=0 THEN
  	   		    vEstNeo:='E';
	   		 END IF;

			 IF vCantTotal>vCantAfectados THEN
  	   		    vEstNeo:='P';
	   		 END IF;
		END;
	ELSE
		 BEGIN
		 vEstNeo:='N';
		 END;
	END IF;

	RETURN vEstNeo;

  END;

  /****************************************************************************************************/

   PROCEDURE INV_ACT_REG_GUIA_RECEP(cGrupoCia_in    IN CHAR,
   			 						cCodLocal_in 	IN CHAR,
									cNumNota_in  	IN CHAR,
									cSecDetNota_in  IN CHAR,
                  cNumPag_in IN CHAR,
									cIdUsu_in 		IN CHAR)
  IS
    v_cEstDet LGT_NOTA_ES_DET.EST_NOTA_ES_DET%TYPE;
    v_nSecGuia LGT_NOTA_ES_DET.SEC_GUIA_REM%TYPE;
    v_cCodProd LGT_NOTA_ES_DET.COD_PROD%TYPE;
    v_nCantMov LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_nCantMatr LGT_NOTA_ES_DET.CANT_ENVIADA_MATR%TYPE;
    v_nCantDif LGT_NOTA_ES_DET.CANT_MOV%TYPE;
  BEGIN
    SELECT EST_NOTA_ES_DET,SEC_GUIA_REM,COD_PROD,CANT_MOV,CANT_ENVIADA_MATR,(CANT_ENVIADA_MATR - CANT_MOV)
      INTO v_cEstDet,v_nSecGuia,v_cCodProd, v_nCantMov, v_nCantMatr, v_nCantDif
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in
          AND SEC_DET_NOTA_ES = cSecDetNota_in
          AND IND_PROD_AFEC = 'N' FOR UPDATE;

     IF v_cEstDet = 'T' THEN
       INV_OPERAC_ANEXAS_PROD(cGrupoCia_in,cCodLocal_in,cNumNota_in,v_nSecGuia,
								   v_cCodProd,v_nCantMov,
								   cIdUsu_in,
                   v_nCantMatr,v_nCantDif);
        UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET=cIdUsu_in,FEC_MOD_NOTA_ES_DET=SYSDATE,
          IND_PROD_AFEC='S',
          EST_NOTA_ES_DET = 'A'
        WHERE COD_GRUPO_CIA=cGrupoCia_in 	    AND
        		COD_LOCAL=cCodLocal_in	      	AND
      		NUM_NOTA_ES=cNumNota_in    	  	AND
      		SEC_DET_NOTA_ES=cSecDetNota_in 	AND
      		IND_PROD_AFEC<>'S'
          ;
     ELSIF v_cEstDet = 'P' THEN
       INV_OPERAC_ANEXAS_PROD(cGrupoCia_in,cCodLocal_in,cNumNota_in,v_nSecGuia,
								   v_cCodProd,v_nCantMatr,
								   cIdUsu_in,
                   v_nCantMatr,0);
       UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET=cIdUsu_in,FEC_MOD_NOTA_ES_DET=SYSDATE,
          CANT_MOV = v_nCantMatr,
          IND_PROD_AFEC='S',
          EST_NOTA_ES_DET = 'A'
        WHERE COD_GRUPO_CIA=cGrupoCia_in 	    AND
        		COD_LOCAL=cCodLocal_in	      	AND
      		NUM_NOTA_ES=cNumNota_in    	  	AND
      		SEC_DET_NOTA_ES=cSecDetNota_in 	AND
      		IND_PROD_AFEC<>'S'
          ;
     END IF;

    INV_ACT_EST_GUIA_RECEP(cGrupoCia_in,cCodLocal_in,cNumNota_in,cIdUsu_in);

    --AGREGADO 29/04/2006 - ERIOS
    INV_ACT_EST_GUIA(cGrupoCia_in,cCodLocal_in,cNumNota_in,cNumPag_in,cIdUsu_in);

    END;

	 /****************************************************************************************************/

  PROCEDURE INV_ACT_PAG_GUIA_RECEP(cGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cNumNota_in  IN CHAR,
                                   cNumPag_in   IN CHAR,
                                   cIdUsu_in    IN CHAR)
  IS
    CURSOR curProd IS
      SELECT SEC_DET_NOTA_ES
        FROM LGT_NOTA_ES_DET
       WHERE COD_GRUPO_CIA = cGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND NUM_NOTA_ES = cNumNota_in
         AND NUM_PAG_RECEP = cNumPag_in
         AND IND_PROD_AFEC <> 'S';
    v_rCurProd curProd%ROWTYPE;
  BEGIN
    FOR v_rCurProd IN curProd
    LOOP
      INV_ACT_REG_GUIA_RECEP(cGrupoCia_in,
                             cCodLocal_in,
                             cNumNota_in,
                             v_rCurProd.SEC_DET_NOTA_ES,
                             cNumPag_in,
                             cIdUsu_in);
    END LOOP;
  END;

	 /****************************************************************************************************/
   --ERIOS 11/12/2006: DESACTUALIZADO
   /*PROCEDURE INV_ACT_GUIA_RECEP(cGrupoCia_in IN CHAR,
   			 					cCodLocal_in IN CHAR,
								cNumNota_in  IN CHAR,
								cIdUsu_in    IN CHAR)
  IS
    CURSOR curProd IS
    SELECT COD_PROD, CANT_ENVIADA_MATR, NUM_PAG_RECEP
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA=cGrupoCia_in AND
  		COD_LOCAL=cCodLocal_in	   AND
		NUM_NOTA_ES=cNumNota_in    AND
		IND_PROD_AFEC<>'S';
    v_rCurProd curProd%ROWTYPE;
  BEGIN
    FOR v_rCurProd IN curProd
    LOOP
      INV_OPERAC_ANEXAS_PROD(cGrupoCia_in,cCodLocal_in,cNumNota_in,v_rCurProd.NUM_PAG_RECEP,
								   v_rCurProd.COD_PROD,
								   v_rCurProd.CANT_ENVIADA_MATR,
								   '101',
								   '02',
								   cIdUsu_in);

      --AGREGADO 29/04/2006 - ERIOS
      INV_ACT_EST_GUIA(cGrupoCia_in,cCodLocal_in,cNumNota_in,v_rCurProd.NUM_PAG_RECEP,cIdUsu_in);
    END LOOP;

  UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET=cIdUsu_in,FEC_MOD_NOTA_ES_DET=SYSDATE,
        CANT_MOV=CANT_ENVIADA_MATR,
  	IND_PROD_AFEC='S'
  WHERE COD_GRUPO_CIA=cGrupoCia_in AND
  		COD_LOCAL=cCodLocal_in	   AND
		NUM_NOTA_ES=cNumNota_in    AND
		IND_PROD_AFEC<>'S';
    INV_ACT_EST_GUIA_RECEP(cGrupoCia_in,cCodLocal_in,cNumNota_in,cIdUsu_in);

    END;*/

	 /****************************************************************************************************/

   PROCEDURE INV_ACT_EST_GUIA_RECEP(cGrupoCia_in IN CHAR,
   			 					    cCodLocal_in IN CHAR,
									cNumNota_in  IN CHAR,
									cIdUsu_in    IN CHAR)
  IS
  vEst CHAR(1);
  BEGIN
  vEst:=INV_OBTIENE_EST_RECEP_GUIA(cGrupoCia_in, cCodLocal_in,cNumNota_in);

  UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB=cIdUsu_in,FEC_MOD_NOTA_ES_CAB=SYSDATE,
        EST_RECEPCION=vEst
  WHERE COD_GRUPO_CIA=cGrupoCia_in AND
  		COD_LOCAL=cCodLocal_in	   AND
		NUM_NOTA_ES=cNumNota_in;
    END;

	 /****************************************************************************************************/

   FUNCTION INV_ONTIENE_PAGINAS_GUIA(cGrupoCia_in IN CHAR,
   									 cCodLocal_in IN CHAR,
									 cNumNota_in  IN CHAR)
  RETURN FarmaCursor
  IS
  curDet FarmaCursor;
  BEGIN
  OPEN curDet FOR
  SELECT DISTINCT TO_CHAR(NUM_PAG_RECEP,'00000000')     || 'Ã' ||
  		 		  NUM_PAG_RECEP "pag"
  FROM LGT_NOTA_ES_DET
  WHERE COD_GRUPO_CIA=cGrupoCia_in AND
  		COD_LOCAL=cCodLocal_in	   AND
		NUM_NOTA_ES=cNumNota_in
	    AND IND_PROD_AFEC<>'S'
  ORDER BY "pag";
  RETURN curDet;
  END;

  PROCEDURE INV_GENERA_GUIA_TRANSFERENCIA(cGrupoCia_in   IN CHAR,
  										  cCodLocal_in   IN CHAR,
										  cNumNota_in    IN CHAR,
										  nCantMAxDet_in IN NUMBER,
										  nCantItems_in  IN NUMBER,
										  cIdUsu_in      IN CHAR)
  AS
    v_nSecDet LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;
    j INTEGER:=1;
    CURSOR curDet IS
    SELECT D.SEC_DET_NOTA_ES--,L.NOM_LAB,P.DESC_PROD,P.DESC_UNID_PRESENT
    FROM LGT_NOTA_ES_DET D, LGT_PROD P, LGT_LAB L
    WHERE D.COD_GRUPO_CIA = cGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.NUM_NOTA_ES = cNumNota_in
          AND D.SEC_GUIA_REM IS NULL
          AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND D.COD_PROD = P.COD_PROD
          AND P.COD_LAB = L.COD_LAB
    ORDER BY L.NOM_LAB,P.DESC_PROD,P.DESC_UNID_PRESENT;
  BEGIN
    FOR i IN 1..CEIL(nCantItems_in/nCantMAxDet_in)
    LOOP
      INSERT INTO LGT_GUIA_REM(COD_GRUPO_CIA,
	  		 	  			   COD_LOCAL,
							   NUM_NOTA_ES,
							   SEC_GUIA_REM,
                                                           NUM_GUIA_REM,
							   USU_CREA_GUIA_REM,
							   FEC_CREA_GUIA_REM)
      VALUES (cGrupoCia_in,cCodLocal_in,cNumNota_in,i,SUBSTR(cNumNota_in,-5)||'-'||i,cIdUsu_in,SYSDATE);
      Dbms_Output.PUT_LINE('CABECERA' || i );
      /*WHILE j <= nCantMAxDet_in*i  AND j <= nCantItems_in
      LOOP
        UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = cIdUsu_in,FEC_MOD_NOTA_ES_DET = SYSDATE,
              SEC_GUIA_REM = i
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = cNumNota_in
              AND SEC_DET_NOTA_ES = j;
        Dbms_Output.PUT_LINE('CABECERA' || i || ' DET' || j);
        j:=j+1;
      END LOOP;*/
      OPEN curDet;
      LOOP
        FETCH curDet INTO v_nSecDet;
        EXIT WHEN curDet%NOTFOUND;
        UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = cIdUsu_in,FEC_MOD_NOTA_ES_DET = SYSDATE,
              SEC_GUIA_REM = i
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = cNumNota_in
              AND SEC_DET_NOTA_ES = v_nSecDet;
        j:=j+1;
        EXIT WHEN j > nCantMAxDet_in*i;
      END LOOP;
      CLOSE curDet;
    END LOOP;
  END;

  FUNCTION INV_GET_CAB_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR)
  RETURN FarmaCursor
  IS
  	curCab FarmaCursor;
  BEGIN
  	OPEN curCab FOR
  	SELECT TO_CHAR(C.FEC_NOTA_ES_CAB,'dd/MM/yyyy') || 'Ã' ||
  	  O.DESC_CORTA || 'Ã' ||
      INV_GET_DESTINO_TRANSFERENCIA(C.TIP_ORIGEN_NOTA_ES,C.COD_DESTINO_NOTA_ES,C.COD_GRUPO_CIA,cCia_in) || 'Ã' ||
      --INV_GET_MOTIVO_NOTA_E(C.TIP_MOT_NOTA_ES) || 'Ã' ||
      C.EST_NOTA_ES_CAB
  	FROM LGT_NOTA_ES_CAB C, PBL_TAB_GRAL O
  	WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
  	      AND C.COD_LOCAL = cCodLocal_in
  	      AND C.NUM_NOTA_ES = cNumNota_in
  	      AND O.COD_APL = g_vCodPtoVenta
  	      AND O.COD_TAB_GRAL = g_vGralDestinoNotaEs
  	      AND O.LLAVE_TAB_GRAL = C.TIP_ORIGEN_NOTA_ES;
   	RETURN curCab;
  END;

  FUNCTION INV_GET_DET_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR)
  RETURN FarmaCursor
  IS
  	curDet FarmaCursor;
  BEGIN
  	OPEN curDet FOR
  	SELECT ( D.COD_PROD || 'Ã' ||
  	      P.DESC_PROD || 'Ã' ||
		  NVL(D.DESC_UNID_VTA,' ')	 || 'Ã' ||
--  	      DECODE(P.IND_PROD_FRACCIONABLE,'S',L.UNID_VTA,P.DESC_UNID_PRESENT) || 'Ã' ||
  	      B.NOM_LAB || 'Ã' ||
  	      D.CANT_MOV || 'Ã' ||
  	      TO_CHAR(D.VAL_PREC_UNIT,'999,990.000') || 'Ã' ||
  	      NVL(TO_CHAR(D.FEC_VCTO_PROD,'dd/MM/yyyy'),' ') ) RESULTADO
  	FROM LGT_NOTA_ES_DET D, LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B
  	WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
  	      AND D.COD_LOCAL = cCodLocal_in
  	      AND D.NUM_NOTA_ES = cNumNota_in
  	      AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
  	      AND D.COD_LOCAL = L.COD_LOCAL
  	      AND D.COD_PROD = L.COD_PROD
  	      AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
	        AND P.COD_LAB = B.COD_LAB;

  	RETURN curDet;
  END;

   PROCEDURE INV_ANULA_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                    cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                    vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curDet IS
    SELECT SEC_DET_NOTA_ES,COD_PROD,CANT_MOV,SEC_GUIA_REM,VAL_FRAC
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;
--
    v_nFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nComprometido LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFrac LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_vDescUnidVta LGT_PROD_LOCAL.UNID_VTA%TYPE;
--
    det curDet%ROWTYPE;

    v_nCant LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_cProdCong LGT_PROD_LOCAL.IND_PROD_CONG%TYPE;
  BEGIN
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in,FEC_MOD_NOTA_ES_CAB = SYSDATE,
          EST_NOTA_ES_CAB = 'N'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

    FOR det IN curDet
    LOOP
      SELECT STK_FISICO,STK_FISICO,VAL_FRAC_LOCAL,UNID_VTA,L.IND_PROD_CONG
        INTO v_nFisico,v_nComprometido,v_nValFrac, v_vDescUnidVta, v_cProdCong
      FROM LGT_PROD_LOCAL L
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = det.COD_PROD;
      --VERIFICA PRODUCTO CONGELADO
      IF v_cProdCong = 'S' THEN
        RAISE_APPLICATION_ERROR(-20003,'ERROR AL ANULAR. PROD:'||det.COD_PROD||' CONGELADO');
      END IF;
      --VERIFICA FRACCION ACTUAL
      IF MOD(det.CANT_MOV*v_nValFrac,det.VAL_FRAC) = 0 THEN
        v_nCant := ((det.CANT_MOV*v_nValFrac)/det.VAL_FRAC);
      ELSE
        RAISE_APPLICATION_ERROR(-20002,'Error al anular. Prod:'||det.COD_PROD||',Cant:'||det.CANT_MOV||' ,Frac:'||det.VAL_FRAC||' ,Frac. Act:'||v_nValFrac);
      END IF;

      UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vIdUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
            STK_FISICO = STK_FISICO + v_nCant
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = det.COD_PROD;

      UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = vIdUsu_in,FEC_MOD_NOTA_ES_DET = SYSDATE,
            EST_NOTA_ES_DET = 'N'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in
          AND SEC_DET_NOTA_ES = det.SEC_DET_NOTA_ES;

      --KARDEX
      PTOVENTA_INV.INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                          cCodLocal_in,
                          det.COD_PROD,
                          cCodMotKardex_in,
                          cTipDocKardex_in,
                          cNumNota_in,
                          v_nFisico,
                          v_nCant,
                          v_nValFrac,
                          v_vDescUnidVta,
                          vIdUsu_in,
                          COD_NUMERA_SEC_KARDEX,'');

        --AGREGADO 13/06/2006 ERIOS
        INV_ACT_KARDEX_GUIA_REC(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,det.SEC_GUIA_REM,
                                det.COD_PROD,
                                vIdUsu_in);
    END LOOP;

    --JCHAVEZ 29122009 inactivando la transferencia por recepcion ciega
    UPDATE LGT_RECEP_PROD_TRANSF
    SET   EST_TRANSF = 'I'
    WHERE COD_GRUPO_CIA   = cCodGrupoCia_in
    AND   COD_LOCAL       = cCodLocal_in
    AND   NUM_NOTA_ES     = cNumNota_in;
  END;


  /*****************************************************************************/
  FUNCTION INV_GET_STK_PROD_FORUPDATE(cCodGrupoCia_in IN CHAR,
  		   				  		 	   	  cCodLocal_in	  IN CHAR,
								 	   	  cCodProd_in	  IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
		SELECT (PROD_LOCAL.STK_FISICO)
		FROM   LGT_PROD_LOCAL PROD_LOCAL
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
		AND	   PROD_LOCAL.COD_PROD = cCodProd_in FOR UPDATE;
    RETURN curVta;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_INFO_PROD(cCodGrupoCia_in IN CHAR,
  		   				  		 cCodLocal_in	 IN CHAR,
								 cCodProd_in	 IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
		SELECT (STK_FISICO )  || 'Ã' ||
			   IND_PROD_CONG  || 'Ã' ||
         TO_CHAR(SYSDATE,'dd/MM/yyyy HH24:MI:SS')
		FROM LGT_PROD_LOCAL
		WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
		      AND	COD_LOCAL = cCodLocal_in
		      AND	COD_PROD = cCodProd_in;
    RETURN curProd;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_DATOS_TRANSPORTISTA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodTransportista_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTransp FarmaCursor;
  BEGIN
    OPEN curTransp FOR
    SELECT NVL(COD_TRANSP,' ') || 'Ã' || NVL(RUC_TRANSP,' ') || 'Ã' || NVL(NOM_TRANSP,' ') || 'Ã' || NVL(DIREC_TRANSP,' ')
    FROM LGT_TRANSP_CIEGA
    WHERE COD_TRANSP = cCodTransportista_in
    AND ESTADO='A';

    RETURN curTransp;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_TRANSP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vTransp VARCHAR2(150);
    v_cCodTransp CHAR(3);
  BEGIN
    SELECT COD_TRANSPORTISTA INTO v_cCodTransp
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    IF v_cCodTransp IS NULL THEN
      SELECT ' '  || 'Ã' || ' '  || 'Ã' || ' ' || 'Ã' || ' '
      INTO v_vTransp
      FROM DUAL;
    ELSE
      SELECT NVL(NOM_TRANSPORTISTA,' ')  || 'Ã' || NVL(RUC_TRANSPORTISTA,' ')  || 'Ã' || NVL(DIREC_TRANSPORTISTA,' ') || 'Ã' || NVL(NUM_PLACA,' ')
      INTO v_vTransp
      FROM LGT_TRANSPORTISTA
      WHERE COD_TRANSPORTISTA = v_cCodTransp;
    END IF;

    RETURN v_vTransp;
  END;

  /*****************************************************************************/
  PROCEDURE INV_ANULA_GUIA_INGRESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                    cCodMotKardex_in IN CHAR, cTipDocKardex_in IN CHAR,
                                    vIdUsu_in IN VARCHAR2)
  AS
    CURSOR curDet IS
    SELECT SEC_DET_NOTA_ES,COD_PROD,CANT_MOV,VAL_FRAC
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

    det curDet%ROWTYPE;

    v_nFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nComprometido LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFrac LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_vDescUnidVta LGT_PROD_LOCAL.UNID_VTA%TYPE;

    v_cTipOrigen LGT_NOTA_ES_CAB.TIP_ORIGEN_NOTA_ES%TYPE;

    v_nCant LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_cProdCong LGT_PROD_LOCAL.IND_PROD_CONG%TYPE;
  BEGIN
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in,FEC_MOD_NOTA_ES_CAB = SYSDATE,
          EST_NOTA_ES_CAB = 'N'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

    FOR det IN curDet
    LOOP
      SELECT STK_FISICO,STK_FISICO,VAL_FRAC_LOCAL,UNID_VTA,L.IND_PROD_CONG
        INTO v_nFisico,v_nComprometido,v_nValFrac, v_vDescUnidVta, v_cProdCong
      FROM LGT_PROD_LOCAL L
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = det.COD_PROD FOR UPDATE;
      --VERIFICA PRODUCTO CONGELADO
      IF v_cProdCong = 'S' THEN
        RAISE_APPLICATION_ERROR(-20003,'ERROR AL ANULAR. PROD:'||det.COD_PROD||' CONGELADO');
      END IF;

      IF MOD(det.CANT_MOV*v_nValFrac,det.VAL_FRAC) = 0 THEN
        v_nCant := ((det.CANT_MOV*v_nValFrac)/det.VAL_FRAC);
      ELSE
        RAISE_APPLICATION_ERROR(-20002,'Error al anular. Prod:'||det.COD_PROD||',Cant:'||det.CANT_MOV||' ,Frac:'||det.VAL_FRAC||' ,Frac. Act:'||v_nValFrac);
      END IF;

      IF (v_nComprometido - v_nCant) >= 0 THEN
        UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = vIdUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
              STK_FISICO = STK_FISICO - v_nCant
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = det.COD_PROD;

        UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET = vIdUsu_in,FEC_MOD_NOTA_ES_DET = SYSDATE,
              EST_NOTA_ES_DET = 'N'
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_NOTA_ES = cNumNota_in
            AND SEC_DET_NOTA_ES = det.SEC_DET_NOTA_ES;

        --INSERTAR KARDEX
        INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                          cCodLocal_in,
                          det.COD_PROD,
                          cCodMotKardex_in,
                          cTipDocKardex_in,
                          cNumNota_in,
                          v_nFisico,
                          v_nCant*-1,
                          v_nValFrac,
                          v_vDescUnidVta,
                          vIdUsu_in,
                          COD_NUMERA_SEC_KARDEX,'');
        --AGREGADO 13/06/2006 ERIOS
        --MODIFICADO 23/06/2006 ERIOS
        SELECT TIP_ORIGEN_NOTA_ES
          INTO v_cTipOrigen
        FROM LGT_NOTA_ES_CAB
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = cNumNota_in;
        INV_ACT_KARDEX_GUIA_REC(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,1,
                                det.COD_PROD,
                                vIdUsu_in,v_cTipOrigen);
      ELSE
        RAISE_APPLICATION_ERROR(-20001,'No se puede Anular esta Guia. Stk:'||v_nComprometido||' Cant:'||v_nCant);
      END IF;

    END LOOP;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_STK_DISP_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_nStkDis LGT_PROD_LOCAL.STK_FISICO%TYPE;
  BEGIN
    SELECT STK_FISICO INTO v_nStkDis
    FROM LGT_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND COD_PROD = cCodProd_in;
    RETURN v_nStkDis || '';
  END;

  /*****************************************************************************/
  FUNCTION INV_LISTA_PEDIDO_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPedRep FarmaCursor;
  BEGIN
    OPEN curPedRep FOR
    SELECT NUM_PED_REP || 'Ã' ||
           TO_CHAR(FEC_CREA_PED_REP_CAB,'dd/MM/yyyy') || 'Ã' ||
           CANT_ITEMS || 'Ã' ||
           R.NUM_MIN_DIAS_REP || 'Ã' ||
           R.NUM_MAX_DIAS_REP || 'Ã' ||
           R.NUM_DIAS_ROT
    FROM LGT_PED_REP_CAB R, PBL_LOCAL L
    WHERE R.COD_GRUPO_CIA = cCodGrupoCia_in
          AND R.COD_LOCAL = cCodLocal_in
          AND R.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND R.COD_LOCAL = L.COD_LOCAL;

       RETURN curPedRep;
  END;




  /*****************************************************************************/
  /*FUNCTION INV_GET_NUM_DIAS_MAX_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN NUMBER
  IS
    v_nMax NUMBER(3);
  BEGIN
    SELECT NVL(R.NUM_MAX_DIAS_REP,0) INTO v_nMax
    FROM LGT_PROD P, LGT_GRUPO_REP_LOCAL R
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.COD_PROD = cCodProd_in
          AND P.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND R.COD_LOCAL = cCodLocal_in
          AND R.COD_GRUPO_REP = P.COD_GRUPO_REP;
    IF v_nMax=0 THEN
      SELECT NUM_MAX_DIAS_REP INTO v_nMax
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;
    END IF;
    RETURN v_nMax;
  END;*/
  /*****************************************************************************/
  FUNCTION INV_GET_STK_TRANS_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN NUMBER
  IS
    v_nTrans LGT_PROD_LOCAL_REP.CANT_TRANSITO%TYPE;
  BEGIN
    SELECT NVL(SUM(CANT_ENVIADA_MATR),0) INTO v_nTrans
    FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
          AND D.COD_PROD = cCodProd_in
          AND C.TIP_NOTA_ES = g_cTipoNotaRecepcion
          AND D.IND_PROD_AFEC = 'N';
    RETURN v_nTrans;
  END;

  /*****************************************************************************/
  FUNCTION INV_LISTA_PROD_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
    RETURN FarmaCursor
    IS
      curProd FarmaCursor;
      --v_cPorcAdic PBL_LOCAL.PORC_ADIC_REP%TYPE;
      --v_nCantMax PBL_LOCAL.CANT_UNIDAD_MAX%TYPE;
    BEGIN
      /*SELECT PORC_ADIC_REP,CANT_UNIDAD_MAX
        INTO v_cPorcAdic,v_nCantMax
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;*/
     /* OPEN curProd FOR
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             INV_GET_DET_PROD_REP(cCodGrupoCia_in,cCodLocal_in,P.COD_PROD) || 'Ã' ||
             INV_GET_ROT_PROD_REP(cCodGrupoCia_in,cCodLocal_in,P.COD_PROD) || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC --INV_GET_CANT_MAX(T.CANT_SUG,v_cPorcAdic,v_nCantMax,L.VAL_FRAC_LOCAL)
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD;*/
      --30/03/2007 ERIOS Se modificó la consulta.
      OPEN curProd FOR
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;

      RETURN curProd;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_CAB_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCab FarmaCursor;
  BEGIN
    OPEN curCab FOR
    SELECT NUM_DIAS_ROT || 'Ã' ||
           NUM_MIN_DIAS_REP || 'Ã' ||
           NUM_MAX_DIAS_REP
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
    RETURN curCab;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_ULT_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPed FarmaCursor;
  BEGIN
    OPEN curPed FOR
    SELECT NVL(CANT_ITEMS,0) || 'Ã' ||
           NVL(CANT_PROD,0)
    FROM LGT_PED_REP_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_REP = (SELECT MAX(NUM_PED_REP)FROM LGT_PED_REP_CAB
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL = cCodLocal_in);
    RETURN curPed;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_DET_PROD_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN VARCHAR2
  IS
   v_vDet VARCHAR2(200);
  BEGIN
    SELECT L.NOM_LAB || 'Ã' ||
           O.CANT_EXHIB || 'Ã' ||
           T.CANT_TRANSITO || 'Ã' ||
           DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
           DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
           DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
           TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
           INV_GET_CANT_ANT_PED_REP(cCodGrupoCia_in,cCodLocal_in,cCodProd_in)
           INTO v_vDet
    FROM LGT_PROD P, LGT_LAB L, LGT_PROD_LOCAL O, LGT_PROD_LOCAL_REP T,LGT_GRUPO_REP_LOCAL G
    WHERE O.COD_GRUPO_CIA = cCodGrupoCia_in
          AND O.COD_LOCAL = cCodLocal_in
          AND O.COD_PROD = cCodProd_in
          AND O.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND O.COD_PROD = P.COD_PROD
          AND P.COD_LAB = L.COD_LAB
          AND O.COD_GRUPO_CIA = T.COD_GRUPO_CIA
          AND O.COD_LOCAL = T.COD_LOCAL
          AND O.COD_PROD = T.COD_PROD
          AND P.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND G.COD_LOCAL = O.COD_LOCAL
          AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;
    RETURN v_vDet;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_CANT_ANT_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vCant NUMBER(6):=0;
  BEGIN
    BEGIN
      /*SELECT D.CANT_SOLICITADA INTO v_vCant
      FROM LGT_PED_REP_CAB C, LGT_PED_REP_DET D
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND D.COD_PROD = cCodProd_in
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_PED_REP = D.NUM_PED_REP
            AND ROWNUM = 1
      ORDER BY D.NUM_PED_REP DESC;*/
      --30/03/2007 ERIOS
      SELECT D.CANT_SOLICITADA INTO v_vCant
                FROM LGT_PED_REP_DET D
                WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND D.COD_LOCAL = cCodLocal_in
                      AND D.COD_PROD = cCodProd_in
                      AND (D.NUM_PED_REP) =
                      (SELECT MAX(D.NUM_PED_REP)
                      FROM LGT_PED_REP_DET D
                      WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND D.COD_LOCAL = cCodLocal_in
                            AND D.COD_PROD = cCodProd_in
                       GROUP BY D.COD_GRUPO_CIA,D.COD_LOCAL,D.COD_PROD
                            );
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_vCant:=0;
    END;
    RETURN v_vCant || '';
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_ROT_PROD_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vRot VARCHAR2(100):='';

  BEGIN
    /*FOR i IN 1..4 --hasta el cuarto mes anterior
    LOOP
      IF i=1 THEN
        v_vRot:=TO_CHAR(PTOVENTA_REP.INV_GET_CANT_VTA_ULT_DIAS(cCodGrupoCia_in,cCodLocal_in,cCodProd_in,30*i,1,SYSDATE),'999,990.000');
      ELSE
        v_vRot:=v_vRot|| 'Ã' || TO_CHAR(PTOVENTA_REP.INV_GET_CANT_VTA_ULT_DIAS(cCodGrupoCia_in,cCodLocal_in,cCodProd_in,30,1,SYSDATE-(30*(i-1))),'999,990.000');
      END IF;
    END LOOP;*/
      SELECT TO_CHAR(CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(CANT_VTA_PER_3, '999,990.000')
        INTO v_vRot
        FROM LGT_PROD_LOCAL_REP
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in
         AND COD_PROD = cCodProd_in;
    RETURN v_vRot;
  END;

  /*****************************************************************************/
  PROCEDURE INV_SET_CANT_PEDREP_TMP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR,nCantTmp_in IN NUMBER,cIdUsu_in IN VARCHAR2)
  AS
  BEGIN
    UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = cIdUsu_in,FEC_MOD_PROD_LOCAL_REP = SYSDATE,
        CANT_SOL = nCantTmp_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;

    --COMMIT;
  END;

  /*****************************************************************************/
  FUNCTION INV_LISTA_PROD_REP_FILTRO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipoFiltro_in IN CHAR, cCodFiltro_in IN CHAR)
    RETURN FarmaCursor
    IS
      curProd FarmaCursor;
    BEGIN
      --RECUPERA LOS PRODUCTOS
      IF(cTipoFiltro_in = g_nTipoFiltroPrincAct) THEN --principio activo
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B, LGT_GRUPO_REP_LOCAL G,
           LGT_PRINC_ACT_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_PRINC_ACT = cCodFiltro_in
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND A.COD_PROD = L.COD_PROD;
      ELSIF(cTipoFiltro_in = g_nTipoFiltroAccTerap) THEN --accion terapeutica
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B, LGT_GRUPO_REP_LOCAL G,
           LGT_ACC_TERAP_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_ACC_TERAP = cCodFiltro_in
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND A.COD_PROD = L.COD_PROD;
      ELSIF(cTipoFiltro_in = g_nTipoFiltroLab) THEN --laboratorio
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B, LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND B.COD_LAB = cCodFiltro_in
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;
      END IF;
      RETURN curProd;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_PED_ACT_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPed FarmaCursor;
  BEGIN
    OPEN curPed FOR
    SELECT COUNT(*) || 'Ã' || NVL(SUM(CANT_SOL),0)
    FROM LGT_PROD_LOCAL_REP T, LGT_PROD_LOCAL L, LGT_PROD P
    WHERE CANT_SOL IS NOT NULL
          AND CANT_SOL > 0
          AND T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T.COD_LOCAL = cCodLocal_in
          AND L.IND_REPONER = 'S'
          AND P.EST_PROD = 'A'
          AND T.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND T.COD_LOCAL = L.COD_LOCAL
          AND T.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
    UNION ALL
    SELECT COUNT(*) || 'Ã' || NVL(SUM(CANT_SUG),0)
    FROM LGT_PROD_LOCAL_REP T, LGT_PROD_LOCAL L, LGT_PROD P
    WHERE CANT_SOL IS NULL
          AND CANT_SUG > 0
          AND T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T.COD_LOCAL = cCodLocal_in
          AND L.IND_REPONER = 'S'
          AND P.EST_PROD = 'A'
          AND T.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND T.COD_LOCAL = L.COD_LOCAL
          AND T.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD;
    RETURN curPed;
  END;

  /*****************************************************************************/
  FUNCTION INV_LISTA_PROD_REP_VER(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN

    OPEN curProd FOR
      /*SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND T.CANT_SOL IS NOT NULL
            --AND T.CANT_SOL > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
      UNION ALL
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND T.CANT_SOL IS NULL
            AND T.CANT_SUG > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;
      */
        SELECT P.COD_PROD                               || 'Ã' ||
               P.DESC_PROD                              || 'Ã' ||
               P.DESC_UNID_PRESENT                      || 'Ã' ||
               TO_CHAR(T.CANT_MIN_STK,'999,990')        || 'Ã' ||
               TO_CHAR(T.CANT_MAX_STK,'999,990')        || 'Ã' ||
               TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
               DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
               TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
               DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))            || 'Ã' ||--SOL
               DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))          || 'Ã' ||--ADIC NUVA COLUMNA!
               B.NOM_LAB                                                            || 'Ã' ||
               L.CANT_EXHIB                                                         || 'Ã' ||
               T.CANT_TRANSITO                                                      || 'Ã' ||
               DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP)         || 'Ã' ||
               DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP)         || 'Ã' ||
               DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT)                 || 'Ã' ||
               TO_CHAR(T.CANT_ROT,'99,990.000')                                     || 'Ã' ||
               0                                                                    || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_0, '999,990.000')                             || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_1, '999,990.000')                             || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_2, '999,990.000')                             || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_3, '999,990.000')                             || 'Ã' ||
               L.VAL_FRAC_LOCAL                                                     || 'Ã' ||
               T.CANT_MAX_ADIC                                                      || 'Ã' ||
               P.IND_TIPO_PROD
        FROM   LGT_PROD P,
               LGT_PROD_LOCAL L,
               LGT_PROD_LOCAL_REP T,
               LGT_LAB B,
               LGT_GRUPO_REP_LOCAL G
        WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    L.COD_LOCAL     = cCodLocal_in
        AND    T.CANT_SOL      IS NOT NULL
        AND    L.IND_REPONER   = 'S'
        AND    P.EST_PROD      = 'A'
        AND    L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    L.COD_PROD      = P.COD_PROD
        AND    L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
        AND    L.COD_LOCAL     = T.COD_LOCAL
        AND    L.COD_PROD      = T.COD_PROD
        AND    P.COD_LAB       = B.COD_LAB
        AND    L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
        AND    L.COD_LOCAL     = G.COD_LOCAL
        AND    G.COD_GRUPO_REP = P.COD_GRUPO_REP
        UNION ALL
        SELECT P.COD_PROD                                     || 'Ã' ||
               P.DESC_PROD                                    || 'Ã' ||
               P.DESC_UNID_PRESENT                            || 'Ã' ||
               TO_CHAR(T.CANT_MIN_STK,'999,990')              || 'Ã' ||
               TO_CHAR(T.CANT_MAX_STK,'999,990')              || 'Ã' ||
               TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
               DECODE(L.IND_REPONER,'N','NO REPONER',' ')                                || 'Ã' ||
               TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990')      || 'Ã' ||
               DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
               DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
               B.NOM_LAB                                                                 || 'Ã' ||
               L.CANT_EXHIB                                                              || 'Ã' ||
               T.CANT_TRANSITO                                                           || 'Ã' ||
               DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP)              || 'Ã' ||
               DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP)              || 'Ã' ||
               DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT)                      || 'Ã' ||
               TO_CHAR(T.CANT_ROT,'99,990.000')                                          || 'Ã' ||
               0                                                                         || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_0, '999,990.000')                                  || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_1, '999,990.000')                                  || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_2, '999,990.000')                                  || 'Ã' ||
               TO_CHAR(T.CANT_VTA_PER_3, '999,990.000')                                  || 'Ã' ||
               L.VAL_FRAC_LOCAL                                                          || 'Ã' ||
               T.CANT_MAX_ADIC                                                           || 'Ã' ||
               P.IND_TIPO_PROD
        FROM   LGT_PROD P,
               LGT_PROD_LOCAL L,
               LGT_PROD_LOCAL_REP T,
               LGT_LAB B,
               LGT_GRUPO_REP_LOCAL G
        WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   L.COD_LOCAL     = cCodLocal_in
        AND   T.CANT_SOL      IS NULL
        AND   T.CANT_SUG      > 0
        AND   L.IND_REPONER   = 'S'
        AND   P.EST_PROD      = 'A'
        AND   L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND   L.COD_PROD      = P.COD_PROD
        AND   L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
        AND   L.COD_LOCAL     = T.COD_LOCAL
        AND   L.COD_PROD      = T.COD_PROD
        AND   P.COD_LAB       = B.COD_LAB
        AND   L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
        AND   L.COD_LOCAL     = G.COD_LOCAL
        AND   G.COD_GRUPO_REP = P.COD_GRUPO_REP;
    RETURN curProd;
  END;
  /*****************************************************************************/
  FUNCTION INV_LISTA_PROD_REP_VER_FILTRO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipoFiltro_in IN CHAR, cCodFiltro_in IN CHAR)
    RETURN FarmaCursor
    IS
      curProd FarmaCursor;
    BEGIN

      IF(cTipoFiltro_in = g_nTipoFiltroPrincAct) THEN --principio activo
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G,
           LGT_PRINC_ACT_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_PRINC_ACT = cCodFiltro_in
            AND T.CANT_SOL IS NOT NULL
            --AND T.CANT_SOL > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND A.COD_PROD = P.COD_PROD
      UNION ALL
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G,
           LGT_PRINC_ACT_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_PRINC_ACT = cCodFiltro_in
            AND T.CANT_SOL IS NULL
            AND T.CANT_SUG > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND A.COD_PROD = P.COD_PROD;

      ELSIF(cTipoFiltro_in = g_nTipoFiltroAccTerap) THEN --accion terapeutica
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G,
           LGT_ACC_TERAP_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_ACC_TERAP = cCodFiltro_in
            AND T.CANT_SOL IS NOT NULL
            --AND T.CANT_SOL > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND A.COD_PROD = P.COD_PROD
      UNION ALL
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G,
           LGT_ACC_TERAP_PROD A
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND A.COD_ACC_TERAP = cCodFiltro_in
            AND T.CANT_SOL IS NULL
            AND T.CANT_SUG > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND A.COD_PROD = P.COD_PROD;

      ELSIF(cTipoFiltro_in = g_nTipoFiltroLab) THEN --laboratorio
        OPEN curProd FOR
        SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND B.COD_LAB = cCodFiltro_in
            AND T.CANT_SOL IS NOT NULL
            --AND T.CANT_SOL > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
      UNION ALL
      SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))                 || 'Ã' ||--SOL
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))               || 'Ã' ||--ADIC NUVA COLUMNA!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND B.COD_LAB = cCodFiltro_in
            AND T.CANT_SOL IS NULL
            AND T.CANT_SUG > 0
            AND L.IND_REPONER = 'S'
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP;

      END IF;
      RETURN curProd;
    END;
  /*****************************************************************************/
FUNCTION INV_GET_DET_REP_VER(cCodGrupoCia_in IN CHAR,
                      cCodLocal_in    IN CHAR,
                 cNroPedido_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
    SELECT D.COD_PROD        || 'Ã' ||
           P.DESC_PROD       || 'Ã' ||
           P.DESC_UNID_PRESENT   || 'Ã' ||
           TO_CHAR(D.CANT_SOLICITADA,'99,990') || 'Ã' ||
           TO_CHAR(D.CANT_SUGERIDA,'99,990') || 'Ã' ||
           TO_CHAR(D.STK_DISPONIBLE/D.VAL_FRAC_PROD ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_DIA_ROT*D.VAL_ROT_PROD ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_MIN_STK ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_MAX_STK ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_DIA_ROT ,'999,990')  || 'Ã' ||
           TO_CHAR(D.NUM_DIAS ,'999,990')  || 'Ã' ||
           TO_CHAR(D.CANT_EXHIB ,'999,990')  || 'Ã' ||
           A.NOM_LAB         || 'Ã' ||
           TO_CHAR(D.STK_TRANSITO ,'999,990')   || 'Ã' ||
           TO_CHAR(D.VAL_ROT_PROD,'999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
           TO_CHAR(D.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
           P.IND_TIPO_PROD
    FROM LGT_PED_REP_DET D,
     LGT_PED_REP_CAB C,
     LGT_PROD_LOCAL L,
     LGT_PROD P,
     LGT_LAB  A
    WHERE C.COD_GRUPO_CIA     = cCodGrupoCia_in
          AND C.COD_LOCAL     = cCodLocal_in
          AND C.NUM_PED_REP   = cNroPedido_in
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL     = D.COD_LOCAL
          AND C.NUM_PED_REP   = D.NUM_PED_REP
          AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND D.COD_LOCAL     = L.COD_LOCAL
          AND D.COD_PROD      = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD      = P.COD_PROD
          AND P.COD_LAB       = A.COD_LAB;

    RETURN curProd;
  END;

  /* ************************************************************************ */

  PROCEDURE INV_GRABAR_KARDEX(cCodGrupoCia_in      IN CHAR,
                              cCodLocal_in         IN CHAR,
                cCodProd_in       IN CHAR,
                cCodMotKardex_in      IN CHAR,
                   cTipDocKardex_in     IN CHAR,
                   cNumTipDoc_in       IN CHAR,
                nStkAnteriorProd_in  IN NUMBER,
                nCantMovProd_in       IN NUMBER,
                nValFrac_in       IN NUMBER,
                cDescUnidVta_in     IN CHAR,
                cUsuCreaKardex_in     IN CHAR,
                cCodNumera_in          IN CHAR,
                cGlosa_in         IN CHAR DEFAULT NULL,
                                                          cTipDoc_in IN CHAR DEFAULT NULL,
                                                          cNumDoc_in IN CHAR DEFAULT NULL)
  IS
  v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;

  BEGIN
    v_cSecKardex :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in),10,'0','I' );

    INSERT INTO LGT_KARDEX(COD_GRUPO_CIA, COD_LOCAL, SEC_KARDEX, COD_PROD, COD_MOT_KARDEX,
                                     TIP_DOC_KARDEX, NUM_TIP_DOC, STK_ANTERIOR_PROD, CANT_MOV_PROD,
                                     STK_FINAL_PROD, VAL_FRACC_PROD, DESC_UNID_VTA, USU_CREA_KARDEX,DESC_GLOSA_AJUSTE,
                                     TIP_COMP_PAGO,NUM_COMP_PAGO)
     VALUES (cCodGrupoCia_in,  cCodLocal_in, v_cSecKardex, cCodProd_in, cCodMotKardex_in,
                                     cTipDocKardex_in, cNumTipDoc_in, nStkAnteriorProd_in, nCantMovProd_in,
                                     (nStkAnteriorProd_in + nCantMovProd_in), nValFrac_in, cDescUnidVta_in, cUsuCreaKardex_in,cGlosa_in,
                                     cTipDoc_in,cNumDoc_in);

  Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in, cUsuCreaKardex_in);
  END;

  /* ************************************************************************ */
  FUNCTION INV_GET_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN CHAR
  IS
    v_cInd CHAR(1);
    v_cDiaGenera CHAR(2);
    v_cDiaHoy CHAR(2);
  BEGIN
    SELECT NVL(TO_CHAR(FEC_GENERA_MAX_MIN,'dd'),'00'),TO_CHAR(SYSDATE,'dd')
      INTO v_cDiaGenera, v_cDiaHoy
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    IF v_cDiaGenera <> v_cDiaHoy THEN
      UPDATE PBL_LOCAL SET USU_MOD_LOCAL = SUBSTR(sys_context('USERENV','TERMINAL'),1,15), FEC_MOD_LOCAL = SYSDATE,
            IND_PED_REP = 'N'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
    END IF;

    SELECT IND_PED_REP INTO v_cInd
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    RETURN v_cInd;
  END;

  /********************************************************************************/
/* FUNCTION INV_LISTA_ITEMS_AK(cCodGrupoCia_in IN CHAR ,
                               cCodLocal_in    IN CHAR  )
 RETURN FarmaCursor

 IS
 BEGIN
   RETURN  INV_LISTA_ITEMS_AK(cCodGrupoCia_in, cCodLocal_in, null);
 END;*/

  /********************************************************************************/


 FUNCTION INV_LISTA_ITEMS_AK(cCodGrupoCia_in IN CHAR ,
                              cCodLocal_in    IN CHAR
                                    )
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN

         OPEN curLabs FOR
         SELECT PL.COD_PROD               || 'Ã' ||
                NVL(P.DESC_PROD,' ')        || 'Ã' ||
                NVL(P.DESC_UNID_PRESENT,' ')         || 'Ã' ||
                NVL(L.NOM_LAB,' ')               || 'Ã' ||
                DECODE(TRIM(P.DESC_UNID_PRESENT),TRIM(PL.UNID_VTA),' ',NVL(TRIM(PL.UNID_VTA),' ')) || 'Ã' ||
                nvl(trunc(stk_fisico/pl.val_frac_local),0)|| 'Ã' ||
                nvl(MOD(stk_fisico,pl.val_frac_local),0) || 'Ã' ||
                pl.val_frac_local || 'Ã' ||
                DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S')|| 'Ã' ||
                P.EST_PROD --JCORTEZ 30.03.2010 solicitud de RCASTRO
         FROM   LGT_PROD_LOCAL PL,
                LGT_PROD P,
                LGT_LAB L,
                LGT_PROD_VIRTUAL PR_VRT
         WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    PL.COD_LOCAL     = cCodLocal_in
         AND    PL.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND    PL.COD_PROD      = P.COD_PROD
         AND    P.COD_LAB       = L.COD_LAB
         AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
         AND    P.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
         AND    P.COD_PROD = PR_VRT.COD_PROD(+);
         --AND    P.COD_GRUPO_REP <> COD_GRU_REP_INSUMO;-- 05.08.2014


   RETURN curLabs;
 END;
  /********************************************************************************/

   FUNCTION INV_LISTA_MOVS_KARDEX(cCodGrupoCia_in IN CHAR ,
                        cCodLocal_in    IN CHAR ,
                 cCod_Prod_in   IN CHAR,
                 cFecIni_in     IN CHAR,
                 cFecFin_in    IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
  OPEN curLabs FOR
     SELECT DISTINCT
           TO_CHAR(K.FEC_KARDEX,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
           NVL(MK.DESC_CORTA_MOT_KARDEX,' ')         || 'Ã' ||
           --NVL(K.COD_MOT_KARDEX,' ')           || 'Ã' ||
           --NVL(K.TIP_COMP_PAGO,' ')           || 'Ã' ||
           --NVL(K.NUM_COMP_PAGO,' ')           || 'Ã' ||
          CASE k.tip_comp_pago
          WHEN '01' THEN 'BOLETA'
          WHEN '02' THEN 'FACTURA'
          WHEN '03' THEN 'GUIA'
          WHEN '04' THEN 'NOTA CREDITO'
          WHEN '05' THEN DECODE(K.TIP_DOC_KARDEX,'01','TICKET','ENTREGA')
          ELSE NVL(DECODE(K.TIP_DOC_KARDEX,'01','VENTA','02','GUIA ENTRADA/SALIDA','03','AJUSTE DE INVENTARIO'),' ')
          END       || 'Ã' ||
--           DECODE(K.NUM_COMP_PAGO,NULL,NVL(K.NUM_TIP_DOC,' '),K.NUM_COMP_PAGO)|| 'Ã' ||
           DECODE(K.NUM_COMP_PAGO,NULL,NVL(K.NUM_TIP_DOC,' '),
--INICIO---- Se igualo el numero comprabnte de kardes y vta_comp_pago para obtener el
             -- num electronico.
          --RH: 27.10.2014 FAC-ELECTRONICA
                                                           --K.NUM_COMP_PAGO)|| 'Ã' ||
            NVL((select 
            Farma_Utility.GET_T_COMPROBANTE_2(P.COD_TIP_PROC_PAGO,P.NUM_COMP_PAGO_E,P.NUM_COMP_PAGO)
					--FAC-ELECTRONICA :27.10.2014 
               from vta_comp_pago p 
               where p.num_comp_pago= k.num_comp_pago 
               and   K.COD_LOCAL      = cCodLocal_in 
               AND   P.FEC_CREA_COMP_PAGO BETWEEN 
                                          TRUNC(K.FEC_KARDEX) AND
                                          TRUNC(K.FEC_KARDEX)  + 1 -24/60/60
                            ), K.NUM_COMP_PAGO))       || 'Ã' || 
--FIN-----------  


           NVL(K.STK_ANTERIOR_PROD,0)            || 'Ã' ||
           NVL(K.CANT_MOV_PROD,0)              || 'Ã' ||
           NVL(K.STK_FINAL_PROD,0)              || 'Ã' ||
           NVL(K.VAL_FRACC_PROD,0)              || 'Ã' ||
          --k.USU_CREA_KARDEX || 'Ã' ||
          nvl(decode(K.TIP_DOC_KARDEX,'01',nvl(vtas.usuario,' ')),' ') || 'Ã' ||
          NVL(k.DESC_GLOSA_AJUSTE,' ')|| 'Ã' ||
          k.sec_kardex || 'Ã' ||
          NVL(K.CANT_MOV_PROD,0) * NVL(K.VAL_FRACC_PROD,0)|| 'Ã' ||
          K.NUM_TIP_DOC || 'Ã' ||
          nvl(VTAS.IND,' ') || 'Ã' ||
          k.cod_mot_kardex
    FROM LGT_KARDEX K,
         LGT_MOT_KARDEX MK,
         (SELECT PVD.COD_PROD codigo,
                  pvd.cod_local LOCAL,
                  PVD.USU_CREA_PED_VTA_DET usuario,
                  pvd.num_ped_vta venta,
                  PVD.IND_CALCULO_MAX_MIN IND
           FROM   Lgt_kardex K,
                  vTa_pedido_vta_det pvd
           WHERE  K.COD_GRUPO_CIA = ccodgrupocia_in AND
                  K.COD_LOCAL = ccodlocal_in AND
                  k.cod_prod = ccod_prod_in AND
                  K.COD_GRUPO_CIA = PVD.COD_GRUPO_CIA AND
                  K.COD_LOCAL = PVD.COD_LOCAL AND
                  K.COD_PROD = PVD.COD_PROD AND
                  k.num_tip_doc(+) = pvd.num_ped_vta)vtas
    WHERE K.COD_GRUPO_CIA  = cCodGrupoCia_in    AND
          K.COD_LOCAL      = cCodLocal_in      AND
          K.COD_PROD     = cCod_Prod_in      AND
          K.FEC_KARDEX BETWEEN TO_DATE(cFecIni_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') AND TO_DATE(cFecFin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') AND
          K.COD_GRUPO_CIA  = MK.COD_GRUPO_CIA    AND
          K.COD_MOT_KARDEX = MK.COD_MOT_KARDEX AND
          k.num_tip_doc = vtas.venta(+) ;

   RETURN curLabs;
 END;

 /*****************************************************************************************************/

  /* ************************************************************************ */

  FUNCTION INV_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in   IN CHAR,
                 cTipoFiltro_in  IN CHAR,
                      cCodFiltro_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
     IF(cTipoFiltro_in = g_nTipoFiltroPrincAct) THEN --principio activo
       OPEN curVta FOR
       SELECT PROD.COD_PROD                      || 'Ã' ||
               NVL(PROD.DESC_PROD,' ')        || 'Ã' ||
               PROD.DESC_UNID_PRESENT          || 'Ã' ||
               LAB.NOM_LAB              || 'Ã' ||
              DECODE(TRIM(PROD.DESC_UNID_PRESENT),TRIM(PROD_LOCAL.UNID_VTA),' ',NVL(TRIM(PROD_LOCAL.UNID_VTA),' ')) || 'Ã' ||
              nvl(trunc(stk_fisico/PROD_LOCAL.val_frac_local),0)|| 'Ã' ||
              nvl(MOD(stk_fisico,PROD_LOCAL.val_frac_local),0) || 'Ã' ||
              PROD_LOCAL.val_frac_local || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S')
/*
             FLOOR((STK_FISICO/VAL_FRAC_LOCAL)) || 'Ã' ||
             MOD(STK_FISICO, VAL_FRAC_LOCAL)
*/
       FROM   LGT_PROD PROD,
               LGT_PROD_LOCAL PROD_LOCAL,
               LGT_LAB LAB,
               PBL_IGV IGV,
              LGT_PRINC_ACT_PROD PRINC_ACT_PROD,
              LGT_PROD_VIRTUAL PR_VRT
       WHERE  PROD_LOCAL.COD_GRUPO_CIA     = cCodGrupoCia_in
       AND    PROD_LOCAL.COD_LOCAL      = cCodLocal_in
       AND    PRINC_ACT_PROD.COD_PRINC_ACT = cCodFiltro_in
       AND    PROD.COD_PROD          = PRINC_ACT_PROD.COD_PROD
       AND    PROD.COD_GRUPO_CIA        = PROD_LOCAL.COD_GRUPO_CIA
       AND    PROD.COD_PROD          = PROD_LOCAL.COD_PROD
       AND    PROD.COD_LAB          = LAB.COD_LAB
       AND    PROD.COD_IGV          = IGV.COD_IGV
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+);
  ELSIF(cTipoFiltro_in = g_nTipoFiltroAccTerap) THEN --accion terapeutica
    OPEN curVta FOR
       SELECT PROD.COD_PROD                       || 'Ã' ||
               NVL(PROD.DESC_PROD,' ')       || 'Ã' ||
               PROD.DESC_UNID_PRESENT         || 'Ã' ||
               LAB.NOM_LAB             || 'Ã' ||
              DECODE(TRIM(PROD.DESC_UNID_PRESENT),TRIM(PROD_LOCAL.UNID_VTA),' ',NVL(TRIM(PROD_LOCAL.UNID_VTA),' ')) || 'Ã' ||
              nvl(trunc(stk_fisico/PROD_LOCAL.val_frac_local),0)|| 'Ã' ||
              nvl(MOD(stk_fisico,PROD_LOCAL.val_frac_local),0) || 'Ã' ||
              PROD_LOCAL.val_frac_local  || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S')
/*
             FLOOR((STK_FISICO/VAL_FRAC_LOCAL)) || 'Ã' ||
             MOD(STK_FISICO, VAL_FRAC_LOCAL)
*/
       FROM   LGT_PROD        PROD,
               LGT_PROD_LOCAL     PROD_LOCAL,
               LGT_LAB        LAB,
              PBL_IGV        IGV,
              LGT_ACC_TERAP      ACC_TER,
              LGT_ACC_TERAP_PROD ACC_TERAP_PROD ,
              LGT_PROD_VIRTUAL PR_VRT
       WHERE  PROD_LOCAL.COD_GRUPO_CIA     = cCodGrupoCia_in
       AND    PROD_LOCAL.COD_LOCAL         = cCodLocal_in
       AND    ACC_TER.COD_ACC_TERAP        = cCodFiltro_in
       AND    PROD.COD_GRUPO_CIA           = ACC_TERAP_PROD.COD_GRUPO_CIA
       AND    PROD.COD_PROD                = ACC_TERAP_PROD.COD_PROD
       AND    ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP
       AND    PROD.COD_GRUPO_CIA           = PROD_LOCAL.COD_GRUPO_CIA
       AND    PROD.COD_PROD                = PROD_LOCAL.COD_PROD
       AND    PROD.COD_LAB                 = LAB.COD_LAB
       AND    PROD.COD_IGV                 = IGV.COD_IGV
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+);
  ELSIF(cTipoFiltro_in = g_nTipoFiltroLab) THEN --laboratorio
    OPEN curVta FOR
       SELECT PROD.COD_PROD                  || 'Ã' ||
               NVL(PROD.DESC_PROD,' ')        || 'Ã' ||
               PROD.DESC_UNID_PRESENT          || 'Ã' ||
               LAB.NOM_LAB              || 'Ã' ||
              DECODE(TRIM(PROD.DESC_UNID_PRESENT),TRIM(PROD_LOCAL.UNID_VTA),' ',NVL(TRIM(PROD_LOCAL.UNID_VTA),' ')) || 'Ã' ||
              nvl(trunc(stk_fisico/PROD_LOCAL.val_frac_local),0)|| 'Ã' ||
              nvl(MOD(stk_fisico,PROD_LOCAL.val_frac_local),0) || 'Ã' ||
              PROD_LOCAL.val_frac_local || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S')
/*
             FLOOR((STK_FISICO/VAL_FRAC_LOCAL)) || 'Ã' ||
             MOD(STK_FISICO, VAL_FRAC_LOCAL)
*/
       FROM   LGT_PROD PROD,
               LGT_PROD_LOCAL PROD_LOCAL,
               LGT_LAB LAB,
               PBL_IGV IGV ,
              LGT_PROD_VIRTUAL PR_VRT
       WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    PROD_LOCAL.COD_LOCAL     = cCodLocal_in
       AND    LAB.COD_LAB        = cCodFiltro_in
       AND    PROD.COD_GRUPO_CIA      = PROD_LOCAL.COD_GRUPO_CIA
       AND    PROD.COD_PROD        = PROD_LOCAL.COD_PROD
       AND    PROD.COD_LAB        = LAB.COD_LAB
       AND    PROD.COD_IGV        = IGV.COD_IGV
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+);

    END IF;
  RETURN curVta;
  END;

  /* ************************************************************************ */
  FUNCTION INV_LISTA_MOT_AJUST_KRDX(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
         curLabs FarmaCursor;
  BEGIN

  OPEN curLabs FOR
      -- RHERRERA 24.09.2014
          SELECT COD_MOT_KARDEX || 'Ã' || DESC_CORTA_MOT_KARDEX
            FROM LGT_MOT_KARDEX X
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND TIP_MOV_KARDEX = g_cMotivoAjuste
             AND COD_MOT_KARDEX NOT IN  ('522','523')
             AND EST_MOT_KARDEX = 'A'--; --JCORTEZ 12.08.09 Se considera estado para motivos
             ORDER BY DESC_CORTA_MOT_KARDEX ASC;

     RETURN curLabs;--INV_LISTA_MOT_AJUST_KRDX(cCodGrupoCia_in,null);
  END;
   /* ************************************************************************ */
 /* FUNCTION INV_LISTA_MOT_AJUST_KRDX(cCodGrupoCia_in IN CHAR,
                                    cTipo_rep   IN CHAR  )
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN

   IF  cTipo_rep = COD_GRU_REP_INSUMO THEN

  OPEN curLabs FOR
\*
      SELECT LLAVE_TAB_GRAL || 'Ã' ||
        DESC_CORTA
      FROM PBL_TAB_GRAL
      WHERE COD_APL = g_vCodPtoVenta
            AND COD_TAB_GRAL = g_vMotivoAjuste;
*\
        SELECT COD_MOT_KARDEX || 'Ã' || DESC_CORTA_MOT_KARDEX
          FROM LGT_MOT_KARDEX
         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND TIP_MOV_KARDEX = g_cMotivoAjuste
           AND EST_MOT_KARDEX = 'A' --;--JCORTEZ 12.08.09 Se considera estado para motivos
           AND COD_MOT_KARDEX IN ( MOT_CONSUMO_INTERNO -- 06.08.2014 - rherrera - motivo: uso interno
                                 ,MOT_ANU_SALID_INTERNO); -- 12.08.2014 - rherrera - motivo: AJUSTE INSUMOS
   ELSE

      OPEN curLabs FOR

          SELECT COD_MOT_KARDEX || 'Ã' || DESC_CORTA_MOT_KARDEX
            FROM LGT_MOT_KARDEX
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND TIP_MOV_KARDEX = g_cMotivoAjuste
             AND EST_MOT_KARDEX = 'A'--; --JCORTEZ 12.08.09 Se considera estado para motivos
             ORDER BY DESC_CORTA_MOT_KARDEX ASC;

   END IF;

   RETURN curLabs;
 END;
*/
 /********************************************************************************/

PROCEDURE INV_INGRESA_AJUSTE_KARDEX(cCodGrupoCia_in  IN CHAR,
                                    cCodLocal_in      IN CHAR,
                                    cCodProd_in       IN CHAR,
                                    cCodMotKardex_in  IN CHAR,
                                    cNeoCant_in       IN CHAR,
                                    cGlosa_in      IN CHAR,
                                    cTipDoc_in      IN CHAR,
                                    cUsu_in           IN CHAR,
                                    cIndCorreoAjuste IN CHAR DEFAULT 'S')
  IS
   v_nStkFisico   NUMBER;
   v_nValFrac     NUMBER;
   v_vDescUnidVta VARCHAR2(30);
   vCantMov_in    NUMBER;
   v_nNeoCod    CHAR(10);
    v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;
    v_cCod_Mot   CHAR(3);--rherrera 19.09.2014 motivo
  BEGIN

    -- rherrera 19.09.2014 obtiene motivo insumo
    v_cCod_Mot:= F_OBTIENE_MOT_INSUMO(cCodMotKardex_in);

    --Obtener STK Actual
    SELECT STK_FISICO,VAL_FRAC_LOCAL,UNID_VTA
      INTO   v_nStkFisico,v_nValFrac,v_vDescUnidVta
    FROM   LGT_PROD_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in  AND
             COD_LOCAL     = cCodLocal_in    AND
             COD_PROD     = cCodProd_in;

    vCantMov_in:= cNeoCant_in - v_nStkFisico;
    v_nNeoCod:=Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_MOV_AJUSTE_KARD),10,'0','I' );
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_MOV_AJUSTE_KARD, cUsu_in);

   --Actualizar Stock de Prod Local
    UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = cUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
          STK_FISICO       = TO_NUMBER(cNeoCant_in)
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in  AND
                   COD_LOCAL     = cCodLocal_in    AND
                   COD_PROD     = cCodProd_in;

   --INSERTAR KARDEX
    v_cSecKardex :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_KARDEX),10,'0','I' );
    INSERT INTO LGT_KARDEX(COD_GRUPO_CIA, COD_LOCAL, SEC_KARDEX, COD_PROD, COD_MOT_KARDEX,
                                     TIP_DOC_KARDEX, NUM_TIP_DOC, STK_ANTERIOR_PROD, CANT_MOV_PROD,
                                     STK_FINAL_PROD, VAL_FRACC_PROD, DESC_UNID_VTA, USU_CREA_KARDEX,DESC_GLOSA_AJUSTE)
     VALUES (cCodGrupoCia_in,  cCodLocal_in, v_cSecKardex, cCodProd_in, /*rherrera*/v_cCod_Mot,--cCodMotKardex_in,
                                     cTipDoc_in, v_nNeoCod, v_nStkFisico, vCantMov_in,
                                     (v_nStkFisico + vCantMov_in), v_nValFrac, v_vDescUnidVta, cUsu_in,cGlosa_in);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, COD_NUMERA_SEC_KARDEX, cUsu_in);
    --FIN INSERTAR KARDEX

          -- RHERRERA 25.09.2014
          -- Modificado: Cuando sea un ajuste SALIDA DE INSUMOS: Reinicia el ajuste automático.
          if v_cCod_Mot = MOT_CONSUMO_INTERNO AND
             cUsu_in <> USU_CONSUMO_AUTO
            then
             PTOVENTA_CONSUMO_AUTOMATICO.P_LIBERAR_AJUSTE(cCodLocal_in,cCodProd_in);
          end if;


    --AGREGADO 07/09/2006 ERIOS
    --IF cIndCorreoAjuste='S' THEN
    IF cIndCorreoAjuste='S' AND
        v_cCod_Mot <> MOT_CONSUMO_INTERNO  THEN --modificado por RHERRERA 25.09.2014
      --ENVIAR MAIL DE AJUSTE
      INT_ENVIA_CORREO_AJUSTE(cCodGrupoCia_in,cCodLocal_in,
                                          'AJUSTE DE PRODUCTO: ',
                                          'AJUSTE',
                                          v_cSecKardex);
    END IF;
 END;

  /****************************************************************************/
  FUNCTION LISTA_LOTE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLote FarmaCursor;
  BEGIN
    OPEN curLote FOR
    SELECT NUM_LOTE_PROD || 'Ã' || DECODE(FEC_VENC_LOTE,NULL,' ',TO_CHAR(FEC_VENC_LOTE,'dd/MM/yyyy'))
    FROM LGT_MAE_LOTE_PROD
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          --AND COD_LOCAL = cCodLocal_in
          AND (TRIM(NUM_LOTE_PROD) IS NOT NULL OR TRIM(FEC_VENC_LOTE) IS NOT NULL)
          AND COD_PROD = cCodProd_in
          -- dubilluz 21.04.2014
          and nvl(FEC_VENC_LOTE,SYSDATE) >= Add_months(sysdate, -6)
          order by nvl(FEC_VENC_LOTE,SYSDATE) asc;
          -- dubilluz 21.04.2014
    /*SELECT DISTINCT NVL(D.NUM_LOTE_PROD,' ') || 'Ã' || DECODE(D.FEC_VCTO_PROD, NULL,' ', D.FEC_VCTO_PROD)
    FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.TIP_NOTA_ES = g_cTipoNotaRecepcion --DATOS QUE ENVIA MATRIZ EN RECEPCION
          AND D.COD_PROD = cCodProd_in
          AND (TRIM(D.NUM_LOTE_PROD) IS NOT NULL OR TRIM(D.FEC_VCTO_PROD) IS NOT NULL)
          --AND D.NUM_LOTE_PROD IS NOT NULL
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES;*/
    RETURN curLote;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_DET_GUIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,nSecGuia_in IN NUMBER)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
    v_cTipNotaEs LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE;
    v_cTipOrigen LGT_NOTA_ES_CAB.TIP_ORIGEN_NOTA_ES%TYPE;
  BEGIN
    SELECT TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES
      INTO v_cTipNotaEs,v_cTipOrigen
    FROM LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;

    IF v_cTipNotaEs=g_cTipoNotaSalida AND v_cTipOrigen=g_cTipoOrigenMatriz THEN --MATRIZ
      OPEN curDet FOR
      SELECT D.COD_PROD || 'Ã' ||
              P.DESC_PROD    || ' UNID:' || P.DESC_UNID_PRESENT || ' ' ||
              DECODE(TRIM(NUM_LOTE_PROD),NULL,' ','LT:'||NUM_LOTE_PROD)|| ' ' ||
              DECODE(TRIM(FEC_VCTO_PROD),NULL,' ','FV:'||TO_CHAR(FEC_VCTO_PROD,'dd/MM/yyyy'))|| 'Ã' ||
              -- 2010-02-17 JOLIVA: Se agrega la política de canje en el nombre del laboratorio
--              B.NOM_LAB       || 'Ã' ||
              CASE WHEN PC.MESES IS NOT NULL THEN '(' || PC.MESES || ' m)' ELSE '(S/P)' END || ' ' || B.NOM_LAB || 'Ã' ||
              --D.CANT_MOV/D.VAL_FRAC
              --16/10/2007 DUBILLUZ  MODIFICADO
              TRUNC(D.CANT_MOV/D.VAL_FRAC)|| '/' || MOD(D.CANT_MOV,D.VAL_FRAC)
        FROM LGT_NOTA_ES_DET D,
                 LGT_PROD P,
                 LGT_PROD_LOCAL L,
                 LGT_LAB B,
                 AUX_POLITICA_CANJE_LAB PC
        WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
              AND D.COD_LOCAL = cCodLocal_in
              AND D.NUM_NOTA_ES = cNumNotaEs_in
              AND D.SEC_GUIA_REM  = nSecGuia_in
              AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
              AND D.COD_LOCAL = L.COD_LOCAL
              AND D.COD_PROD = L.COD_PROD
              AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
              AND L.COD_PROD = P.COD_PROD
              AND P.COD_LAB = B.COD_LAB
              AND PC.COD_LAB(+) = B.COD_LAB
        --ORDER BY B.NOM_LAB,P.DESC_PROD,P.DESC_UNID_PRESENT;
        --cAMBIO DE ORDEN SOLICITADO POR ROLANDO
        --DUBILLUZ 27.05.2009
        ORDER BY B.NOM_LAB,P.COD_PROD;
    ELSE --LOCALES
      OPEN curDet FOR
      SELECT D.COD_PROD || 'Ã' ||
              P.DESC_PROD    || ' UNID:' || DESC_UNID_VTA || ' ' ||
              DECODE(TRIM(NUM_LOTE_PROD),NULL,' ','LT:'||NUM_LOTE_PROD)|| ' ' ||
              DECODE(TRIM(FEC_VCTO_PROD),NULL,' ','FV:'||TO_CHAR(FEC_VCTO_PROD,'dd/MM/yyyy'))|| 'Ã' ||
              B.NOM_LAB       || 'Ã' ||
              D.CANT_MOV
        FROM LGT_NOTA_ES_DET D,
                 LGT_PROD P,
                 LGT_PROD_LOCAL L,
                 LGT_LAB B
        WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
              AND D.COD_LOCAL = cCodLocal_in
              AND D.NUM_NOTA_ES = cNumNotaEs_in
              AND D.SEC_GUIA_REM  = nSecGuia_in
              AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
              AND D.COD_LOCAL = L.COD_LOCAL
              AND D.COD_PROD = L.COD_PROD
              AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
              AND L.COD_PROD = P.COD_PROD
              AND P.COD_LAB = B.COD_LAB;
    END IF;

    RETURN curDet;
  END;

  /*****************************************************************************/
  PROCEDURE INV_SET_IND_IMPRESO_GUIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,nSecGuia_in IN NUMBER,cNumGuia_in IN CHAR,cIdUsu_in IN CHAR)
  AS
    v_nCant INTEGER;
  BEGIN

    UPDATE LGT_GUIA_REM SET USU_MOD_GUIA_REM = cIdUsu_in, FEC_MOD_GUIA_REM = SYSDATE,
          NUM_GUIA_REM = cNumGuia_in,
          IND_GUIA_IMPRESA = 'S'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in
          AND SEC_GUIA_REM = nSecGuia_in;

    --ACTUALIZA LA CABECERA CUANDO TODAS LAS GUIAS HAN SIDO IMPRESAS
    SELECT COUNT(*)
      INTO v_nCant
    FROM LGT_GUIA_REM
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in
          --AND NUM_GUIA_REM IS NULL
          AND IND_GUIA_IMPRESA = 'N';

    IF v_nCant = 0 THEN
      UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = cIdUsu_in, FEC_MOD_NOTA_ES_CAB = SYSDATE,
            IND_NOTA_IMPRESA = 'S'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_NOTA_ES = cNumNotaEs_in;
    END IF;
  END;

  /*****************************************************************************/
  FUNCTION INV_GET_SEC_GUIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cSecImprLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curSec FarmaCursor;
    v_nCant INTEGER;
  BEGIN
    OPEN curSec FOR
    SELECT SEC_GUIA_REM
    FROM LGT_GUIA_REM G
    WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
          AND G.COD_LOCAL = cCodLocal_in
          AND G.NUM_NOTA_ES = cNumNotaEs_in
          --AND G.NUM_GUIA_REM IS NULL
          AND G.IND_GUIA_IMPRESA = 'N'
    ORDER BY 1;
    --VALIDA GUIAS DISPONIBLES PARA IMPRESION
    --AGREGADO 20/06/2006 ERIOS
    IF cSecImprLocal_in IS NOT NULL THEN
      SELECT COUNT(SEC_GUIA_REM)
        INTO v_nCant
      FROM LGT_GUIA_REM G
      WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
            AND G.COD_LOCAL = cCodLocal_in
            AND G.NUM_NOTA_ES = cNumNotaEs_in
            --AND G.NUM_GUIA_REM IS NULL
            AND G.IND_GUIA_IMPRESA = 'N';
      IF v_nCant > 0 THEN
        VALIDA_GUIAS_DISPONIBLE(cCodGrupoCia_in,cCodLocal_in,v_nCant,cSecImprLocal_in);
      END IF;
    END IF;

    RETURN curSec;
  END;

  /******************************************************************************/
  FUNCTION INV_GET_MOTIVO_TRANSF(cCodGrupoCia_in IN CHAR,cCodTipo_in IN CHAR)
  RETURN FarmaCursor
  IS
    curMot FarmaCursor;
  BEGIN
    IF cCodTipo_in = g_cTipoOrigenLocal THEN --LOCAL
      OPEN curMot FOR
      SELECT COD_MOT_KARDEX || 'Ã' ||
           DESC_CORTA_MOT_KARDEX
      FROM LGT_MOT_KARDEX
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_MOT_KARDEX = g_cTipoMotKardexTransfInterna; --
    ELSIF cCodTipo_in = g_cTipoOrigenMatriz THEN --MATRIZ
      OPEN curMot FOR
      SELECT LLAVE_TAB_GRAL || 'Ã' ||
                  DESC_CORTA
          FROM PBL_TAB_GRAL
          WHERE COD_APL = g_vCodPtoVenta
                AND COD_TAB_GRAL = g_vMotivoAjuste;
--  JMIRANDA 11.12.09
--  COMENTAR ESTO EN EL FUTURO NO EXISTE TRANSF PROVEEDOR
    ELSIF cCodTipo_in = g_cTipoOrigenProveedor THEN --PROVEEDOR
      OPEN curMot FOR
      SELECT LLAVE_TAB_GRAL || 'Ã' ||
                  DESC_CORTA
          FROM PBL_TAB_GRAL
          WHERE COD_APL = g_vCodPtoVenta
                AND COD_TAB_GRAL = g_vMotivoAjuste;
    END IF;
    RETURN curMot;
  END;

  /**********************************************************************************************************/

  FUNCTION INV_LISTA_IMPRESORAS(cGrupoCia_in IN CHAR,
                       cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curRep FarmaCursor;
  BEGIN
    OPEN curRep FOR
    SELECT SEC_IMPR_LOCAL   || 'Ã' ||
           DESC_IMPR_LOCAL  || 'Ã' ||
         NUM_SERIE_LOCAL||'-'||NUM_COMP
    FROM VTA_IMPR_LOCAL
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND EST_IMPR_LOCAL = 'A'
      AND TIP_COMP=g_cTipoImpresoraGuia;

    RETURN curRep;
  END;
  /**************************************************************************************************************/

  PROCEDURE INV_OPERAC_ANEXAS_PROD(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in   IN CHAR,
                   cNumNota_in     IN CHAR,
                   nSecGuia_in IN NUMBER,
                   cCodProd_in     IN CHAR,
                   nCantMov_in     IN NUMBER,
                   cIdUsu_in     IN CHAR,
                   nTotalGuia_in IN NUMBER DEFAULT NULL,
                   nDiferencia_in IN NUMBER DEFAULT NULL)
  AS
    v_nValFrac LGT_NOTA_ES_DET.VAL_FRAC%TYPE;
    v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
    v_nStkFisico LGT_PROD_LOCAL.STK_FISICO%TYPE;
          v_nNumGuia LGT_GUIA_REM.NUM_GUIA_REM%TYPE;
  BEGIN
    SELECT DECODE(VAL_FRAC_LOCAL,NULL,1,0,1,VAL_FRAC_LOCAL),UNID_VTA,STK_FISICO
    INTO v_nValFrac,v_vDescUnidVta, v_nStkFisico
    FROM LGT_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
        COD_LOCAL     = cCodLocal_in    AND
      COD_PROD     = cCodProd_in;

      --ACTUALIZA PROD LOCAL
      UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = cIdUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
            STK_FISICO = STK_FISICO + nCantMov_in* v_nValFrac
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
         COD_LOCAL     = cCodLocal_in    AND
       COD_PROD      = cCodProd_in;

      --AGREGADO 23/06/2006 ERIOS
      SELECT NUM_ENTREGA
        INTO v_nNumGuia
      FROM LGT_GUIA_REM
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_NOTA_ES = cNumNota_in
            AND SEC_GUIA_REM = nSecGuia_in;
        --INSERTAR KARDEX
        IF (nTotalGuia_in IS NOT NULL) AND (nDiferencia_in IS NOT NULL)
            AND (nDiferencia_in <> 0) THEN
          INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                                    cCodLocal_in,
                                    cCodProd_in,
                                    g_cMotKardexIngMatriz,
                                    g_cTipDocKdxGuiaES,
                                    cNumNota_in,
                                    v_nStkFisico,
                                    nTotalGuia_in* v_nValFrac,
                                    v_nValFrac,
                                    v_vDescUnidVta,
                                    cIdUsu_in,
                                    COD_NUMERA_SEC_KARDEX,'',g_cTipCompNumEntrega,v_nNumGuia);
          INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                                    cCodLocal_in,
                                    cCodProd_in,
                                    g_cTipoMotKardexAjusteGuia,
                                    g_cTipDocKdxGuiaES,
                                    cNumNota_in,
                                    v_nStkFisico+(nTotalGuia_in* v_nValFrac),
                                    (ABS(nDiferencia_in)*-1)* v_nValFrac,
                                    v_nValFrac,
                                    v_vDescUnidVta,
                                    cIdUsu_in,
                                    COD_NUMERA_SEC_KARDEX,'',g_cTipCompNumEntrega,v_nNumGuia);
        ELSE
          INV_GRABAR_KARDEX(cCodGrupoCia_in ,
                                    cCodLocal_in,
                                    cCodProd_in,
                                    g_cMotKardexIngMatriz,
                                    g_cTipDocKdxGuiaES,
                                    cNumNota_in,
                                    v_nStkFisico,
                                    nCantMov_in* v_nValFrac,
                                    v_nValFrac,
                                    v_vDescUnidVta,
                                    cIdUsu_in,
                                    COD_NUMERA_SEC_KARDEX,'',g_cTipCompNumEntrega,v_nNumGuia);
        END IF;

      --AGREGADO 13/06/2006 ERIOS
        /*INV_ACT_KARDEX_GUIA_REC(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,nSecGuia_in,
                                cCodProd_in,
                                cIdUsu_in);*/
  END;

   FUNCTION INV_OBTIENE_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in  IN CHAR,
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


  PROCEDURE INV_ACTUALIZA_IMPR_NUM_COMP(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                    cSecImprLocal_in   IN CHAR,
                    cUsuModImprLocal_in IN CHAR)
  IS
  BEGIN
    UPDATE VTA_IMPR_LOCAL SET FEC_MOD_IMPR_LOCAL = SYSDATE,USU_MOD_IMPR_LOCAL = cUsuModImprLocal_in,
            NUM_COMP = TRIM(TO_CHAR((TO_NUMBER(NUM_COMP) + 1),'0000000'))
   WHERE COD_GRUPO_CIA      = cCodGrupoCia_in
   AND   COD_LOCAL          = cCodLocal_in
   AND   SEC_IMPR_LOCAL     = cSecImprLocal_in;
  END;



PROCEDURE INV_RECONFIG_IMPRESORA(cCodGrupoCia_in   IN CHAR,
                       cCod_Local_in     IN CHAR,
                   nSecImprLocal_in  IN NUMBER,
                   cNumComp_in       IN CHAR)
  IS
  BEGIN
       UPDATE VTA_IMPR_LOCAL SET USU_MOD_IMPR_LOCAL = SUBSTR(sys_context('USERENV','TERMINAL'),1,15) ,FEC_MOD_IMPR_LOCAL = SYSDATE,
                  NUM_COMP       = cNumComp_in
     WHERE
        COD_GRUPO_CIA   = cCodGrupoCia_in   AND
          COD_LOCAL       = cCod_Local_in     AND
        SEC_IMPR_LOCAL  = nSecImprLocal_in;
  END;

  /****************************************************************************/
  FUNCTION INV_GET_HISTORICO_LOTE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                                  cCodProd_in IN CHAR,cNumPed IN CHAR)
  RETURN INTEGER
  IS
    cant INTEGER; --fijarse en el campo de control de lote
    vTipVta CHAR(2);
  BEGIN

        BEGIN
         SELECT   C.TIP_PED_VTA
         into     vTipVta
         FROM     VTA_PEDIDO_VTA_CAB C
         WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    C.COD_LOCAL   = cCodLocal_in
         AND    C.NUM_PED_VTA   = cNumPed;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            vTipVta:='01';
        END;



      if (vTipVta = VTA_EMPRESA) then --venta mayorista
          SELECT DECODE(P.IND_CONTROL_LOTE,'N',0,1) INTO CANT
          FROM   LGT_PROD P
          WHERE  P.COD_PROD = cCodProd_in;
          --cant:=0;
      else
          SELECT DECODE(P.IND_CONTROL_LOTE,'N',0,1) INTO CANT
          FROM   LGT_PROD P
          WHERE  P.COD_PROD = cCodProd_in;
      end if;

      RETURN cant;
  END;

  FUNCTION INV_GET_HISTORICO_LOTE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cCodProd_in IN CHAR)
  RETURN INTEGER
  IS
    cant INTEGER; --fijarse en el campo de control de lote
  BEGIN

      RETURN INV_GET_HISTORICO_LOTE(cCodGrupoCia_in, cCodLocal_in,cCodProd_in,null);
  END;

  /****************************************************************************/
  FUNCTION INV_LISTA_GUIA_RECEP(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNota_in IN CHAR)
  RETURN FarmaCursor
  IS
    curGuias FarmaCursor;
  BEGIN
    OPEN curGuias FOR
    SELECT G.NUM_GUIA_REM|| 'Ã' || D.NUM_PAG_RECEP || 'Ã' ||
                    COUNT(NUM_PAG_RECEP)|| 'Ã' || SUM(CANT_ENVIADA_MATR)|| 'Ã' ||
                    G.NUM_ENTREGA || 'Ã' ||
                    TO_CHAR(C.FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                    -- NVL(G.TIPO_PED_REP,' ') || 'Ã' || -- JMIRANDA 14.12.09 PARA VISUALIZAR EL ZREG
                    DECODE(G.IND_GUIA_LIBERADA,'S','UB-L',NVL(G.TIPO_PED_REP,' ')) || 'Ã' || -- JMIRANDA 01.03.2010
                    C.NUM_NOTA_ES
    FROM LGT_NOTA_ES_CAB C,LGT_NOTA_ES_DET D, LGT_GUIA_REM G
    WHERE C.COD_GRUPO_CIA = cGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.NUM_NOTA_ES LIKE cNumNota_in
          AND C.TIP_NOTA_ES = '03'
          AND D.IND_PROD_AFEC = 'N'
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
          AND D.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND D.COD_LOCAL = G.COD_LOCAL
          AND D.NUM_NOTA_ES = G.NUM_NOTA_ES
          AND D.SEC_GUIA_REM = G.SEC_GUIA_REM
    --GROUP BY G.NUM_GUIA_REM,D.NUM_PAG_RECEP,G.NUM_ENTREGA,TO_CHAR(C.FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS'),C.NUM_NOTA_ES;
     --   GROUP BY G.NUM_GUIA_REM,D.NUM_PAG_RECEP,G.NUM_ENTREGA,TO_CHAR(C.FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS'), G.TIPO_PED_REP, C.NUM_NOTA_ES;
        GROUP BY G.NUM_GUIA_REM,D.NUM_PAG_RECEP,G.NUM_ENTREGA,TO_CHAR(C.FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS'),
                DECODE(G.IND_GUIA_LIBERADA,'S','UB-L',NVL(G.TIPO_PED_REP,' ')), C.NUM_NOTA_ES;

    RETURN curGuias;
  END;

  /****************************************************************************/

  /****************************************************************************/
  FUNCTION INV_GET_NUM_ENTREGA(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumEntrega_in IN CHAR)
  RETURN NUMBER
  IS
    v_nCant INTEGER;
  BEGIN
    SELECT COUNT(NUM_ENTREGA)
      INTO v_nCant
    FROM LGT_GUIA_REM
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_ENTREGA = cNumEntrega_in;
    RETURN v_nCant;
  END;

  /****************************************************************************/
  PROCEDURE INV_AGREGA_EXCESO_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumEntrega_in IN CHAR, cNumLote_in IN CHAR,vFecVenc_in IN VARCHAR2, cCodProd_in IN CHAR,nCantExec_in IN NUMBER,cIdUsu_in IN CHAR)
  AS
    v_nNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
  BEGIN
    v_nNumNota := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumExcProd),10,'0','I' );

    INSERT INTO LGT_DIF_ENTREGA(COD_GRUPO_CIA,
                                COD_LOCAL,
                                SEC_DIF_ENTREGA,
                                COD_PROD,
                                NUM_ENTREGA,
                                NUM_LOTE,
                                FEC_VENC,
                                CANT_DIF_ENTREGA,
                                USU_CREA_DIF_ENTREGA  )
    VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumNota,
            cCodProd_in,
            cNumEntrega_in,
            cNumLote_in,
            TO_DATE(vFecVenc_in,'dd/MM/yyyy'),
            nCantExec_in,
            cIdUsu_in
            );

    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumExcProd, cIdUsu_in);
  END;

  /****************************************************************************/
  FUNCTION INV_LISTA_EXCESO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curListado FarmaCursor;
  BEGIN
    OPEN curListado FOR
    SELECT SEC_DIF_ENTREGA|| 'Ã' ||
          D.COD_PROD|| 'Ã' ||
          DESC_PROD|| 'Ã' ||
          DESC_UNID_PRESENT|| 'Ã' ||
          CANT_DIF_ENTREGA|| 'Ã' ||
          TO_CHAR(FEC_CREA_DIF_ENTREGA,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
          NVL(NUM_ENTREGA,' ')|| 'Ã' ||
          NVL(NUM_LOTE,' ')|| 'Ã' ||
          NVL(TRIM(TO_CHAR(FEC_VENC,'dd/MM/yyyy')),' ')|| 'Ã' ||
          L.NOM_LAB
    FROM LGT_DIF_ENTREGA D, LGT_PROD P, LGT_LAB L
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND D.COD_PROD = P.COD_PROD
          AND P.COD_LAB = L.COD_LAB;
    RETURN curListado;
  END;

  /****************************************************************************/
  PROCEDURE INV_MODIFICA_EXCESO_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cSecExceso_in IN CHAR,cNumEntrega_in IN CHAR, cNumLote_in IN CHAR,vFecVenc_in IN VARCHAR2, cCodProd_in IN CHAR,nCantExec_in IN NUMBER,cIdUsu_in IN CHAR)
  AS
  BEGIN
    UPDATE LGT_DIF_ENTREGA SET FEC_MOD_DIF_ENTREGA = SYSDATE,USU_MOD_DIF_ENTREGA =  cIdUsu_in,
        NUM_ENTREGA = cNumEntrega_in,
        NUM_LOTE = cNumLote_in,
        FEC_VENC = vFecVenc_in,
        CANT_DIF_ENTREGA = nCantExec_in
    WHERE COD_GRUPO_CIA  = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND SEC_DIF_ENTREGA = cSecExceso_in
          AND COD_PROD = cCodProd_in;
  END;

  /****************************************************************************/
  PROCEDURE INV_GRABA_STOCK_ACTUAL_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsu_in IN CHAR)
  AS
  BEGIN
    BEGIN
      INSERT INTO LGT_HIST_STK_LOCAL(COD_GRUPO_CIA,
                                      COD_LOCAL,
                                      COD_PROD,
                                      FEC_STK,
                                      CANT_STK,
                                      VAL_FRAC_PROD_LOCAL,
                                      USU_CREA_HIST_STK_LOCAL,
                                      STK_TRANSITO)
      SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD,TO_CHAR(SYSDATE-1,'dd/MM/yyyy') AS FECHA, STK_FISICO,VAL_FRAC_LOCAL,cIdUsu_in,
            INV_GET_STK_TRANS_PROD(COD_GRUPO_CIA,COD_LOCAL,COD_PROD)
      FROM LGT_PROD_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND STK_FISICO > 0;
    EXCEPTION
      WHEN DUP_VAL_ON_INDEX THEN
        NULL;
    END;

    COMMIT;
    EXCEPTION

    WHEN OTHERS THEN
      ROLLBACK;
      RAISE;
  END;

  /****************************************************************************/
  PROCEDURE INV_ACT_EST_GUIA(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cNumPag_in IN CHAR,cIdUsu_in IN CHAR)
  AS
    vEst CHAR(1);
  BEGIN
    vEst:=INV_OBTIENE_EST_GUIA(cGrupoCia_in, cCodLocal_in,cNumNota_in,cNumPag_in);

    UPDATE LGT_GUIA_REM SET USU_MOD_GUIA_REM=cIdUsu_in,FEC_MOD_GUIA_REM=SYSDATE,
          IND_GUIA_CERRADA=vEst
    WHERE COD_GRUPO_CIA=cGrupoCia_in AND
                  COD_LOCAL=cCodLocal_in     AND
                  NUM_NOTA_ES=cNumNota_in
                  AND SEC_GUIA_REM = cNumPag_in;
  END;
  /****************************************************************************/
  FUNCTION INV_OBTIENE_EST_GUIA(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cNumPag_in IN CHAR)
  RETURN CHAR
  IS
    vCantTotal NUMBER;
    vCantAfectados NUMBER;
    vEstNeo CHAR(1);
    vEstOrig CHAR(1);
  BEGIN

      SELECT COUNT(*)
      INTO vCantAfectados
      FROM   LGT_NOTA_ES_DET
      WHERE  COD_GRUPO_CIA = cGrupoCia_in   AND
                     COD_LOCAL     = cCodLocal_in  AND
                     NUM_NOTA_ES   = cNumNota_in    AND
                     NUM_PAG_RECEP = cNumPag_in
                     AND IND_PROD_AFEC = 'S';

      SELECT COUNT(*)
      INTO vCantTotal
      FROM LGT_NOTA_ES_DET
      WHERE COD_GRUPO_CIA = cGrupoCia_in AND
                    COD_LOCAL    = cCodLocal_in AND
                    NUM_NOTA_ES    = cNumNota_in
                    AND NUM_PAG_RECEP = cNumPag_in;

      IF vCantTotal=vCantAfectados THEN
     vEstNeo:='S';--CERRADA
      ELSE
        vEstNeo:='N';
      END IF;

    RETURN vEstNeo;
  END;
 /**********************************************************************************/
  FUNCTION INV_LISTA_MOTIVOS_KRDX(cCodGrupoCia_in IN CHAR)
 RETURN FarmaCursor
 IS
   curKrdx FarmaCursor;
 BEGIN
  OPEN curKrdx FOR
      SELECT COD_MOT_KARDEX        || 'Ã' ||
         DESC_CORTA_MOT_KARDEX
    FROM  LGT_MOT_KARDEX
    WHERE COD_GRUPO_CIA  = cCodGrupoCia_in
        ORDER BY DESC_CORTA_MOT_KARDEX;
        --AND TIP_MOV_KARDEX = g_cMotivoAjuste;

   RETURN curKrdx;
 END;

 FUNCTION INV_FILTRO_LISTA_MOVS_KARDEX(cCodGrupoCia_in IN CHAR ,
                             cCodLocal_in    IN CHAR ,
                       cCod_Prod_in   IN CHAR,
                       cFecIni_in     IN CHAR,
                       cFecFin_in    IN CHAR,
                     cFiltro_in   IN CHAR)

 RETURN FarmaCursor
 IS
   curKrdx FarmaCursor;
 BEGIN
  OPEN curKrdx FOR
     SELECT
           TO_CHAR(K.FEC_KARDEX,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
           NVL(MK.DESC_CORTA_MOT_KARDEX,' ')         || 'Ã' ||
           --NVL(K.COD_MOT_KARDEX,' ')           || 'Ã' ||
           --NVL(K.TIP_COMP_PAGO,' ')           || 'Ã' ||
           --NVL(K.NUM_COMP_PAGO,' ')           || 'Ã' ||
          NVL(DECODE(K.TIP_DOC_KARDEX,'01','VENTA','02','GUIA ENTRADA/SALIDA','03','AJUSTE DE INVENTARIO'),' ')       || 'Ã' ||
           NVL(K.NUM_TIP_DOC,' ')             || 'Ã' ||
           NVL(K.STK_ANTERIOR_PROD,0)            || 'Ã' ||
           NVL(K.CANT_MOV_PROD,0)              || 'Ã' ||
           NVL(K.STK_FINAL_PROD,0)              || 'Ã' ||
           NVL(K.VAL_FRACC_PROD,0)              || 'Ã' ||
          k.USU_CREA_KARDEX || 'Ã' ||
          NVL(k.DESC_GLOSA_AJUSTE,' ')|| 'Ã' ||
          k.sec_kardex || 'Ã' ||
          NVL(K.CANT_MOV_PROD,0) * NVL(K.VAL_FRACC_PROD,0) || 'Ã' ||
          K.NUM_TIP_DOC  || 'Ã' ||
          nvl(VTAS.IND,' ') || 'Ã' ||
          k.cod_mot_kardex
    FROM LGT_KARDEX K,
         LGT_MOT_KARDEX MK,
         (SELECT PVD.COD_PROD codigo,
                  pvd.cod_local LOCAL,
                  PVD.USU_CREA_PED_VTA_DET usuario,
                  pvd.num_ped_vta venta,
                  PVD.IND_CALCULO_MAX_MIN IND
           FROM   Lgt_kardex K,
                  vTa_pedido_vta_det pvd
           WHERE  K.COD_GRUPO_CIA = ccodgrupocia_in AND
                  K.COD_LOCAL = ccodlocal_in AND
                  k.cod_prod = ccod_prod_in AND
                  K.COD_GRUPO_CIA = PVD.COD_GRUPO_CIA AND
                  K.COD_LOCAL = PVD.COD_LOCAL AND
                  K.COD_PROD = PVD.COD_PROD AND
                  k.num_tip_doc(+) = pvd.num_ped_vta)vtas
   WHERE K.COD_GRUPO_CIA  = cCodGrupoCia_in    AND
          K.COD_LOCAL      = cCodLocal_in      AND
          K.COD_PROD     = cCod_Prod_in      AND
          mk.cod_mot_kardex = cFiltro_in AND
          K.FEC_KARDEX BETWEEN TO_DATE(cFecIni_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') AND TO_DATE(cFecFin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') AND
          K.COD_GRUPO_CIA  = MK.COD_GRUPO_CIA    AND
          K.COD_MOT_KARDEX = MK.COD_MOT_KARDEX AND
          k.num_tip_doc = vtas.venta(+);

   RETURN curKrdx;
 END;

  /****************************************************************************/
  PROCEDURE INV_SETEAR_VALORES(cCodGrupoCia_in IN CHAR ,cCodLocal_in IN CHAR,
                              cNumNota_in IN CHAR, cSecDetNota_in IN CHAR,
                              cCantMov_in IN CHAR, cIdUsu_in   IN CHAR)
  AS
  BEGIN
    UPDATE LGT_NOTA_ES_DET SET USU_MOD_NOTA_ES_DET=cIdUsu_in,FEC_MOD_NOTA_ES_DET=SYSDATE,
            CANT_MOV=cCantMov_in,
            EST_NOTA_ES_DET = 'T' --TRABAJADO
    WHERE COD_GRUPO_CIA=cCodGrupoCia_in       AND
      COD_LOCAL=cCodLocal_in          AND
    NUM_NOTA_ES=cNumNota_in          AND
    SEC_DET_NOTA_ES=cSecDetNota_in   ;
  END;

  /****************************************************************************************************/
PROCEDURE INV_ACT_KARDEX_GUIA_REC(cGrupoCia_in   IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumNota_in     IN CHAR,
                                    cSecDetNota_in IN CHAR,
                                    cCodProd_in    IN CHAR,
                                    cIdUsu_in      IN CHAR,
                                    cTipOrigen_in IN CHAR DEFAULT NULL)
  IS
    v_cNumGuiaRem VARCHAR2(20);--LGT_GUIA_REM.Num_Guia_Rem%TYPE;
    v_cTipDoc LGT_NOTA_ES_CAB.TIP_DOC%TYPE;
    v_nCant LGT_KARDEX.CANT_MOV_PROD%TYPE;
  BEGIN
    --MODIFICADO 23/06/2006 ERIOS
    IF (cTipOrigen_in = g_cTipoOrigenLocal) OR (cTipOrigen_in = g_cTipoOrigenMatriz) THEN
      SELECT g_cTipCompGuia,A.NUM_GUIA_REM,SUM(D.CANT_MOV)
        INTO   v_cTipDoc,v_cNumGuiaRem,v_nCant
      FROM   LGT_GUIA_REM A, LGT_NOTA_ES_DET D
      WHERE  A.COD_GRUPO_CIA = cGrupoCia_in
            AND A.COD_LOCAL = cCodLocal_in
            AND A.NUM_NOTA_ES = cNumNota_in
            AND A.SEC_GUIA_REM = cSecDetNota_in
            AND D.COD_PROD = cCodProd_in
            AND A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND A.COD_LOCAL = D.COD_LOCAL
            AND A.NUM_NOTA_ES = D.NUM_NOTA_ES
             AND A.SEC_GUIA_REM = D.SEC_GUIA_REM
       GROUP BY  g_cTipCompGuia,A.NUM_GUIA_REM
             ;
    ELSIF (cTipOrigen_in = g_cTipoOrigenProveedor) OR (cTipOrigen_in = g_cTipoOrigenCompetencia) THEN
      SELECT NVL(C.TIP_DOC,g_cTipCompGuia),C.NUM_DOC,SUM(D.CANT_MOV) --JCHAVEZ agregue el SUM 29122009
        INTO   v_cTipDoc,v_cNumGuiaRem,v_nCant
      FROM   LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
      WHERE  C.COD_GRUPO_CIA = cGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.NUM_NOTA_ES = cNumNota_in
            AND D.COD_PROD = cCodProd_in
            AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND C.COD_LOCAL = D.COD_LOCAL
            AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
            GROUP BY  NVL(C.TIP_DOC,g_cTipCompGuia),C.NUM_DOC;
    ELSIF cTipOrigen_in IS NULL THEN
      --SELECT NVL(C.TIP_DOC,g_cTipCompGuia),A.NUM_GUIA_REM
      /*SELECT g_cTipCompGuia,A.NUM_GUIA_REM
        INTO   v_cTipDoc,v_cNumGuiaRem
      FROM   LGT_GUIA_REM A, LGT_NOTA_ES_CAB C
      WHERE  A.COD_GRUPO_CIA = cGrupoCia_in
            AND A.COD_LOCAL = cCodLocal_in
            AND A.NUM_NOTA_ES = cNumNota_in
            AND A.SEC_GUIA_REM = cSecDetNota_in
            AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
            AND A.COD_LOCAL = C.COD_LOCAL
            AND A.NUM_NOTA_ES = C.NUM_NOTA_ES;*/
      SELECT g_cTipCompGuia,A.NUM_GUIA_REM,SUM(D.CANT_MOV)--JCHAVEZ agregue el SUM 29122009
        INTO   v_cTipDoc,v_cNumGuiaRem,v_nCant
      FROM   LGT_GUIA_REM A, LGT_NOTA_ES_DET D
      WHERE  A.COD_GRUPO_CIA = cGrupoCia_in
            AND A.COD_LOCAL = cCodLocal_in
            AND A.NUM_NOTA_ES = cNumNota_in
            AND A.SEC_GUIA_REM = cSecDetNota_in
            AND D.COD_PROD = cCodProd_in
            AND A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND A.COD_LOCAL = D.COD_LOCAL
            AND A.NUM_NOTA_ES = D.NUM_NOTA_ES
             AND A.SEC_GUIA_REM = D.SEC_GUIA_REM
             GROUP BY  g_cTipCompGuia,A.NUM_GUIA_REM;
    END IF;
    DBMS_OUTPUT.PUT_LINE('ACTUALIZA:'||v_cNumGuiaRem);
    UPDATE LGT_KARDEX K SET K.USU_MOD_KARDEX = cIdUsu_in, K.FEC_MOD_KARDEX = SYSDATE,
          K.TIP_COMP_PAGO = v_cTipDoc,
          K.NUM_COMP_PAGO = v_cNumGuiaRem
     WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND   COD_PROD = cCodProd_in
    AND   NUM_TIP_DOC = cNumNota_in
          --AND CANT_MOV_PROD = v_nCant
          AND TIP_COMP_PAGO IS NULL
          AND NUM_COMP_PAGO IS NULL
          AND ROWNUM = 1;

  END;

  /****************************************************************************/
  FUNCTION INV_GET_ESTADO_PROC_SAP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cTipoNota_in IN CHAR)
  RETURN CHAR
  IS
    v_cEstado CHAR(1);
  BEGIN
    IF cTipoNota_in = g_cTipoNotaSalida THEN
      SELECT NVL2(FEC_PROCESO_SAP,'S','N')
        INTO v_cEstado
      FROM LGT_NOTA_ES_CAB
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_NOTA_ES = cNumNota_in
            ;
    ELSIF cTipoNota_in = g_cTipoNotaEntrada THEN
      SELECT DECODE(COUNT(*),0,'S','N')
        INTO v_cEstado
      FROM LGT_KARDEX
      WHERE COD_GRUPO_CIA = cGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND TIP_DOC_KARDEX = '02'
            AND NUM_TIP_DOC = cNumNota_in
            AND FEC_PROCESO_SAP IS NULL
            ;
    END IF;

    RETURN v_cEstado;
  END;

  /****************************************************************************/
  FUNCTION INV_GET_CABECERA_TRANSF(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCabecera FarmaCursor;
  BEGIN
    OPEN curCabecera FOR
    SELECT NVL(DESC_EMPRESA,' ') || 'Ã' ||
          RUC_EMPRESA || 'Ã' ||
          DIR_EMPRESA || 'Ã' ||
          DESC_TRANS || 'Ã' ||
          RUC_TRANS || 'Ã' ||
          COD_DESTINO_NOTA_ES
    FROM LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

    RETURN curCabecera;
  END;

  /****************************************************************************/
  PROCEDURE VALIDA_GUIAS_DISPONIBLE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                    nNumGuias_in IN NUMBER,cSecImprLocal_in IN CHAR)
  AS
    v_cSerieGuia VTA_IMPR_LOCAL.NUM_SERIE_LOCAL%TYPE;
    v_cNumGuia VTA_IMPR_LOCAL.NUM_COMP%TYPE;
    v_cNumGuiaAux VTA_IMPR_LOCAL.NUM_COMP%TYPE;
    v_nCant INTEGER;
    i INTEGER;
  BEGIN
    SELECT IMPR_LOCAL.NUM_SERIE_LOCAL,IMPR_LOCAL.NUM_COMP
        INTO v_cSerieGuia,v_cNumGuia
    FROM   VTA_IMPR_LOCAL IMPR_LOCAL
    WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
          AND     IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
          AND     IMPR_LOCAL.SEC_IMPR_LOCAL = cSecImprLocal_in;

    FOR i IN 1..nNumGuias_in
    LOOP
      v_cNumGuiaAux := TRIM(TO_CHAR((TO_NUMBER(v_cNumGuia) + (i-1)),'0000000'));
      SELECT COUNT(*)
        INTO v_nCant
      FROM LGT_GUIA_REM
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_GUIA_REM = v_cSerieGuia||v_cNumGuiaAux;
      IF v_nCant > 0 THEN
        RAISE_APPLICATION_ERROR(-20061,'NO TIENE ESPACIO DISPONIBLE PARA IMPRIMIR TODAS LAS GUIAS. VERIFIQUE Y CORRIGA EL NUMERO DE GUIA');
      END IF;
    END LOOP;

  END;

  /****************************************************************************/
  FUNCTION INV_LISTA_GUIAS_REM(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,vFechaIni_in IN VARCHAR2,vFechaFin_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
    curGuias FarmaCursor;
  BEGIN
    OPEN curGuias FOR
    SELECT G.NUM_NOTA_ES|| 'Ã' ||
            G.SEC_GUIA_REM|| 'Ã' ||
            DECODE(C.TIP_NOTA_ES,'01','GUIA INGRESO','02','TRANSFERENCIA','GUIA')|| 'Ã' ||
            NVL(G.NUM_GUIA_REM,' ')|| 'Ã' ||
            TO_CHAR(G.FEC_CREA_GUIA_REM,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
            DECODE(C.EST_NOTA_ES_CAB,'A',' ','N','ANULADO','ESTADO')|| 'Ã' ||
            C.TIP_NOTA_ES||G.NUM_GUIA_REM|| 'Ã' ||
            C.TIP_NOTA_ES
    FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
    WHERE G.COD_GRUPO_CIA = cGrupoCia_in
          AND G.COD_LOCAL = cCodLocal_in
          AND C.TIP_NOTA_ES = g_cTipoNotaSalida
          AND C.TIP_ORIGEN_NOTA_ES IN (g_cTipoOrigenLocal,g_cTipoOrigenMatriz)
          AND G.IND_GUIA_IMPRESA = 'S'
          AND G.FEC_CREA_GUIA_REM BETWEEN TO_DATE(vFechaIni_in,'dd/MM/yyyy') AND TO_DATE(vFechaFin_in,'dd/MM/yyyy')+0.99999
          AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND G.COD_LOCAL = C.COD_LOCAL
          AND G.NUM_NOTA_ES = C.NUM_NOTA_ES
    UNION
    SELECT G.NUM_NOTA_ES|| 'Ã' ||
            G.SEC_GUIA_REM|| 'Ã' ||
            DECODE(C.TIP_NOTA_ES,'01','GUIA INGRESO','02','TRANSFERENCIA','GUIA')|| 'Ã' ||
            NVL(G.NUM_GUIA_REM,' ')|| 'Ã' ||
            TO_CHAR(G.FEC_CREA_GUIA_REM,'dd/MM/yyyy HH24:MI:SS')|| 'Ã' ||
            DECODE(C.EST_NOTA_ES_CAB,'A',' ','N','ANULADO','ESTADO')|| 'Ã' ||
            C.TIP_NOTA_ES||G.NUM_GUIA_REM|| 'Ã' ||
            C.TIP_NOTA_ES
    FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
    WHERE G.COD_GRUPO_CIA = cGrupoCia_in
          AND G.COD_LOCAL = cCodLocal_in
          AND C.TIP_NOTA_ES = g_cTipoNotaEntrada
          AND C.TIP_ORIGEN_NOTA_ES IN (g_cTipoOrigenLocal)
          AND G.FEC_CREA_GUIA_REM BETWEEN TO_DATE(vFechaIni_in,'dd/MM/yyyy') AND TO_DATE(vFechaFin_in,'dd/MM/yyyy')+0.99999
          AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND G.COD_LOCAL = C.COD_LOCAL
          AND G.NUM_NOTA_ES = C.NUM_NOTA_ES
    ;

    RETURN curGuias;
  END;

  /****************************************************************************/
  FUNCTION INV_VERIFICAR_GUIAS(cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cSecIni_in       IN CHAR,
                                cSecFin_in       IN CHAR,
                                cTipoNota_in IN CHAR)
  RETURN NUMBER IS
    numerocomprobante_out   NUMBER;
    comprobanteinicial      NUMBER := TO_NUMBER(cSecIni_in);
    comprobantefinal      NUMBER := TO_NUMBER(cSecFin_in);
    numerocomprobante     CHAR(10);
  BEGIN
    numerocomprobante_out := 1; --SI EXISTE Y NO OCURRIRA ERROR
    LOOP
      BEGIN
        SELECT G.NUM_GUIA_REM
          INTO numerocomprobante
        FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
        WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
              AND G.COD_LOCAL = cCodLocal_in
              AND C.TIP_NOTA_ES = cTipoNota_in
              AND C.TIP_ORIGEN_NOTA_ES IN (g_cTipoOrigenLocal,g_cTipoOrigenMatriz)
              AND G.NUM_GUIA_REM = TRIM(TO_CHAR(comprobanteinicial,'0000000000'))
              AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
              AND G.COD_LOCAL = C.COD_LOCAL
              AND G.NUM_NOTA_ES = C.NUM_NOTA_ES
              AND ROWNUM=1;

        numerocomprobante_out := 1;
        comprobanteinicial := comprobanteinicial + 1;

        EXIT WHEN comprobanteinicial>comprobantefinal;

        EXCEPTION WHEN NO_DATA_FOUND THEN
          numerocomprobante_out := 2; --NO EXISTE Y SI OCURRIRA ERROR
          EXIT;
        END;
    END LOOP;

    RETURN numerocomprobante_out;
  END;

  /****************************************************************************/
  FUNCTION INV_VERIFICAR_CORRECCION_GUIA(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cSecIni_in       IN CHAR,
                                         cSecFin_in       IN CHAR,
                                         nCant_in       IN NUMBER,
                                         cIndDir_in        IN CHAR,
                                         cTipoNota_in IN CHAR)
  RETURN NUMBER
  IS
    numerocomprobante_out   NUMBER;
    comprobanteinicial      NUMBER := TO_NUMBER(cSecIni_in);
    comprobantefinal      NUMBER := TO_NUMBER(cSecFin_in);
    numerocomprobante     CHAR(10);
    contador       NUMBER := 0;

  BEGIN
    numerocomprobante_out := 1; --SI EXISTE Y OCURRIRA UN ERROR DE DUPLICACION
    IF ( cIndDir_in = 1 ) THEN  --Se corregira ascendentemente
      LOOP
        BEGIN
          SELECT G.NUM_GUIA_REM
          INTO numerocomprobante
        FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
        WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
              AND G.COD_LOCAL = cCodLocal_in
              AND C.TIP_NOTA_ES = cTipoNota_in
              AND C.TIP_ORIGEN_NOTA_ES IN (g_cTipoOrigenLocal,g_cTipoOrigenMatriz)
              AND G.NUM_GUIA_REM = TRIM(TO_CHAR(comprobantefinal + nCant_in,'0000000000'))
              AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
              AND G.COD_LOCAL = C.COD_LOCAL
              AND G.NUM_NOTA_ES = C.NUM_NOTA_ES;

                numerocomprobante_out := 1;
                EXIT;

        EXCEPTION WHEN NO_DATA_FOUND THEN
            numerocomprobante_out := 2; --NO EXISTE Y NO OCURRIRA UN ERROR DE DUPLICACION
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
            SELECT G.NUM_GUIA_REM
          INTO numerocomprobante
        FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
        WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
              AND G.COD_LOCAL = cCodLocal_in
              AND C.TIP_NOTA_ES = cTipoNota_in
              AND C.TIP_ORIGEN_NOTA_ES IN (g_cTipoOrigenLocal,g_cTipoOrigenMatriz)
              AND G.NUM_GUIA_REM = TRIM(TO_CHAR(comprobanteinicial - nCant_in,'0000000000'))
              AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
              AND G.COD_LOCAL = C.COD_LOCAL
              AND G.NUM_NOTA_ES = C.NUM_NOTA_ES;

                  numerocomprobante_out := 1;
                  EXIT;

          EXCEPTION WHEN NO_DATA_FOUND THEN
            numerocomprobante_out := 2; --NO EXISTE Y NO OCURRIRA UN ERROR DE DUPLICACION
            contador := contador + 1;
            comprobanteinicial := comprobanteinicial + 1;

            IF ( contador=nCant_in OR comprobanteinicial>comprobantefinal) THEN
               EXIT;
            END IF;

          END;
        END LOOP;
    END IF;
    RETURN numerocomprobante_out;
  END;

  /****************************************************************************/
  PROCEDURE INV_CORRIGE_GUIAS(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in       IN CHAR,
                                   cSecIni_in      IN CHAR,
                                   cSecFin_in       IN CHAR,
                                   nCant_in         IN NUMBER,
                                   nIndDir          IN NUMBER,
                                   cCodUsu_in      IN CHAR,
                                   cTipoNota_in IN CHAR)
  IS
    comprobanteinicial    NUMBER(10) := TO_NUMBER(cSecIni_in);
    comprobantefinal     NUMBER(10) := TO_NUMBER(cSecFin_in);
    numeropedido    CHAR(10);
    numeropedidoanul    CHAR(10);

    CURSOR curDet(cNumGuia_in IN CHAR) IS
    SELECT C.NUM_NOTA_ES,G.SEC_GUIA_REM,D.COD_PROD
    FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
    WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
          AND G.COD_LOCAL = cCodLocal_in
          AND C.TIP_NOTA_ES = cTipoNota_in
          AND G.NUM_GUIA_REM = cNumGuia_in
          AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND G.COD_LOCAL = C.COD_LOCAL
          AND G.NUM_NOTA_ES = C.NUM_NOTA_ES
          AND G.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND G.COD_LOCAL = D.COD_LOCAL
          AND G.NUM_NOTA_ES = D.NUM_NOTA_ES
          AND G.SEC_GUIA_REM = D.SEC_GUIA_REM;
    v_rCurDet curDet%ROWTYPE;
  BEGIN
    IF ( nIndDir = 1 ) THEN  --Se corregira ascendentemente
      LOOP
        BEGIN
        --
          UPDATE LGT_GUIA_REM SET FEC_MOD_GUIA_REM = SYSDATE, USU_MOD_GUIA_REM = cCodUsu_in,
            NUM_GUIA_REM = TRIM(TO_CHAR(comprobantefinal + nCant_in,'0000000000'))
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_GUIA_REM = TRIM(TO_CHAR(comprobantefinal,'0000000000'))
                AND NUM_NOTA_ES IN (SELECT C.NUM_NOTA_ES
                                    FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
                                    WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
                                          AND G.COD_LOCAL = cCodLocal_in
                                          AND C.TIP_NOTA_ES = cTipoNota_in
                                          AND C.TIP_ORIGEN_NOTA_ES IN (g_cTipoOrigenLocal,g_cTipoOrigenMatriz)
                                          AND G.NUM_GUIA_REM = TRIM(TO_CHAR(comprobantefinal,'0000000000'))
                                          AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                          AND G.COD_LOCAL = C.COD_LOCAL
                                          AND G.NUM_NOTA_ES = C.NUM_NOTA_ES);
          --ACTUALIZA EL KARDEX
          FOR v_rCurDet IN curDet(TRIM(TO_CHAR(comprobantefinal + nCant_in,'0000000000')))
          LOOP
            INV_CORRIGE_KARDEX_GUIA(cCodGrupoCia_in,cCodLocal_in,
                                    v_rCurDet.NUM_NOTA_ES,v_rCurDet.SEC_GUIA_REM,v_rCurDet.COD_PROD,
                                    cCodUsu_in);
          END LOOP;
          --COMMIT;
          comprobantefinal := comprobantefinal - 1;
        EXIT WHEN comprobantefinal<comprobanteinicial;
          /*EXCEPTION WHEN OTHERS THEN
                  dbms_output.put_line('ERROR: ' || SQLERRM );
                  ROLLBACK;*/
        END;
      END LOOP;
    ELSIF ( nIndDir = 2 ) THEN  --Se corregira descendentemente
      LOOP
        BEGIN
          UPDATE LGT_GUIA_REM SET FEC_MOD_GUIA_REM = SYSDATE, USU_MOD_GUIA_REM = cCodUsu_in,
            NUM_GUIA_REM = TRIM(TO_CHAR(comprobanteinicial - nCant_in,'0000000000'))
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_GUIA_REM = TRIM(TO_CHAR(comprobanteinicial,'0000000000'))
                AND NUM_NOTA_ES IN (SELECT C.NUM_NOTA_ES
                                      FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C
                                      WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND G.COD_LOCAL = cCodLocal_in
                                            AND C.TIP_NOTA_ES = cTipoNota_in
                                            AND C.TIP_ORIGEN_NOTA_ES IN (g_cTipoOrigenLocal,g_cTipoOrigenMatriz)
                                            AND G.NUM_GUIA_REM = TRIM(TO_CHAR(comprobanteinicial,'0000000000'))
                                            AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                            AND G.COD_LOCAL = C.COD_LOCAL
                                            AND G.NUM_NOTA_ES = C.NUM_NOTA_ES);
            --ACTUALIZA EL KARDEX
            FOR v_rCurDet IN curDet(TRIM(TO_CHAR(comprobanteinicial - nCant_in,'0000000000')))
            LOOP
              INV_CORRIGE_KARDEX_GUIA(cCodGrupoCia_in,cCodLocal_in,
                                      v_rCurDet.NUM_NOTA_ES,v_rCurDet.SEC_GUIA_REM,v_rCurDet.COD_PROD,
                                      cCodUsu_in);
            END LOOP;
             --COMMIT;
            comprobanteinicial := comprobanteinicial + 1;
         EXIT WHEN comprobanteinicial>comprobantefinal;
              /* EXCEPTION WHEN OTHERS THEN
                     dbms_output.put_line('ERROR: ' || SQLERRM );
             ROLLBACK;*/
        END;
      END LOOP;
    END IF;
  END;

  /****************************************************************************/
  PROCEDURE INV_CORREGIR_NUM_GUIA(cCodGrupoCia_in    IN CHAR,
                                   cCodLocal_in       IN CHAR,
                                   cNumNotaEs_in IN CHAR,
                                   nSecGuiaRem_in IN NUMBER,
                                   cNumGuiaAnt_in IN CHAR,
                                   cTipoNota_in IN CHAR,
                                   cNumGuiaNuevo_in IN CHAR,
                                   cIdUsu_in IN VARCHAR2)
  AS
    CURSOR curDet(cNumGuia_in IN CHAR) IS
    SELECT C.NUM_NOTA_ES,G.SEC_GUIA_REM,D.COD_PROD
    FROM LGT_GUIA_REM G, LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D
    WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
          AND G.COD_LOCAL = cCodLocal_in
          AND C.TIP_NOTA_ES = cTipoNota_in
          AND G.NUM_GUIA_REM = cNumGuia_in
          AND C.NUM_NOTA_ES = cNumNotaEs_in
          AND G.SEC_GUIA_REM = nSecGuiaRem_in
          AND G.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND G.COD_LOCAL = C.COD_LOCAL
          AND G.NUM_NOTA_ES = C.NUM_NOTA_ES
          AND G.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND G.COD_LOCAL = D.COD_LOCAL
          AND G.NUM_NOTA_ES = D.NUM_NOTA_ES
          AND G.SEC_GUIA_REM = D.SEC_GUIA_REM;
    v_rCurDet curDet%ROWTYPE;
  BEGIN
    UPDATE LGT_GUIA_REM SET FEC_MOD_GUIA_REM = SYSDATE, USU_MOD_GUIA_REM = cIdUsu_in,
          NUM_GUIA_REM = cNumGuiaNuevo_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in
          AND SEC_GUIA_REM = nSecGuiaRem_in;

    --ACTUALIZA CABECERA SOLO PARA LAS GUIAS DE INGRESO.
    UPDATE LGT_NOTA_ES_CAB SET FEC_MOD_NOTA_ES_CAB = SYSDATE, USU_MOD_NOTA_ES_CAB = cIdUsu_in,
          NUM_DOC = TRIM(cNumGuiaNuevo_in)
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in
          --AND NUM_DOC IS NOT NULL
          AND TIP_NOTA_ES = g_cTipoNotaEntrada
          AND TIP_ORIGEN_NOTA_ES = g_cTipoOrigenLocal;

     --ACTUALIZA EL KARDEX
      FOR v_rCurDet IN curDet(cNumGuiaNuevo_in)
      LOOP
        DBMS_OUTPUT.PUT_LINE('PROD:'||v_rCurDet.COD_PROD);
        INV_CORRIGE_KARDEX_GUIA(cCodGrupoCia_in,cCodLocal_in,
                                v_rCurDet.NUM_NOTA_ES,v_rCurDet.SEC_GUIA_REM,v_rCurDet.COD_PROD,
                                cIdUsu_in);
      END LOOP;
  END;

  /****************************************************************************/
  PROCEDURE INV_CORRIGE_KARDEX_GUIA(cGrupoCia_in   IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumNota_in     IN CHAR,
                                    cSecDetNota_in IN CHAR,
                                    cCodProd_in    IN CHAR,
                                    cIdUsu_in      IN CHAR,
                                    cTipOrigen_in IN CHAR DEFAULT NULL)
  IS
    v_cNumGuiaRem LGT_GUIA_REM.Num_Guia_Rem%TYPE;
    v_cTipDoc LGT_NOTA_ES_CAB.TIP_DOC%TYPE;
    v_nCant LGT_KARDEX.CANT_MOV_PROD%TYPE;
  BEGIN

      SELECT g_cTipCompGuia,A.NUM_GUIA_REM,D.CANT_MOV
        INTO   v_cTipDoc,v_cNumGuiaRem,v_nCant
      FROM   LGT_GUIA_REM A, LGT_NOTA_ES_DET D
      WHERE  A.COD_GRUPO_CIA = cGrupoCia_in
            AND A.COD_LOCAL = cCodLocal_in
            AND A.NUM_NOTA_ES = cNumNota_in
            AND A.SEC_GUIA_REM = cSecDetNota_in
            AND D.COD_PROD = cCodProd_in
            AND A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
            AND A.COD_LOCAL = D.COD_LOCAL
            AND A.NUM_NOTA_ES = D.NUM_NOTA_ES
             AND A.SEC_GUIA_REM = D.SEC_GUIA_REM;

    DBMS_OUTPUT.PUT_LINE('ACTUALIZA:'||v_cNumGuiaRem);
    UPDATE LGT_KARDEX K SET K.USU_MOD_KARDEX = cIdUsu_in, K.FEC_MOD_KARDEX = SYSDATE,
          K.TIP_COMP_PAGO = v_cTipDoc,
          K.NUM_COMP_PAGO = v_cNumGuiaRem
     WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND   COD_LOCAL = cCodLocal_in
          AND   COD_PROD = cCodProd_in
    AND   NUM_TIP_DOC = cNumNota_in
          --AND CANT_MOV_PROD = v_nCant
          --AND ROWNUM = 1 --MODIFICADO 04/07/2006 ERIOS: UNA GUIA INGRESO TIENE UN ITEM POR PRODUCTO
                                                          --PERO EN KARDEX, PUEDE TENER HASTA DOS.
                                                          --DEBIDO A UNA ANULACION.
          ;

  END;

  /****************************************************************************/
  FUNCTION INV_LISTA_PROD_REP_STK_CERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    --RECUPERA LOS PRODUCTOS
    OPEN curProd FOR
    SELECT P.COD_PROD || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             P.DESC_UNID_PRESENT || 'Ã' ||
             --TO_CHAR(T.CANT_MIN_STK,'999,990') || 'Ã' ||
             --TO_CHAR(T.CANT_MAX_STK,'999,990') || 'Ã' ||
             TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
             DECODE(L.IND_REPONER,'N','NO REPONER',' ') || 'Ã' ||
             TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
             DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))  || 'Ã' ||
             DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))  || 'Ã' ||--NUEVA COLUMNA!!
             B.NOM_LAB || 'Ã' ||
             L.CANT_EXHIB || 'Ã' ||
             T.CANT_TRANSITO || 'Ã' ||
             DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP) || 'Ã' ||
             DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT) || 'Ã' ||
             TO_CHAR(T.CANT_ROT,'99,990.000') || 'Ã' ||
             0  || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_0, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_1, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_2, '999,990.000') || 'Ã' ||
             TO_CHAR(T.CANT_VTA_PER_3, '999,990.000') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             T.CANT_MAX_ADIC || 'Ã' ||
             P.IND_TIPO_PROD
      FROM LGT_PROD P, LGT_PROD_LOCAL L, LGT_PROD_LOCAL_REP T,
           LGT_LAB B,LGT_GRUPO_REP_LOCAL G
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND P.EST_PROD = 'A'
            AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND L.COD_PROD = P.COD_PROD
            AND L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
            AND L.COD_LOCAL = T.COD_LOCAL
            AND L.COD_PROD = T.COD_PROD
            AND P.COD_LAB = B.COD_LAB
            AND L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
            AND L.COD_LOCAL = G.COD_LOCAL
            AND G.COD_GRUPO_REP = P.COD_GRUPO_REP
            AND P.COD_PROD IN (SELECT DISTINCT COD_PROD
                              FROM LGT_PROD_LOCAL_FALTA_STK
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND COD_LOCAL = cCodLocal_in
                                  AND FEC_GENERA_PED_REP IS NULL)
          ;
    RETURN curProd;
  END;
  /****************************************************************************/
  FUNCTION INV_LISTA_TRANSF_CONFIRMAR(cGrupoCia_in IN CHAR, cCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curGuias FarmaCursor;
  BEGIN
    OPEN curGuias FOR
    SELECT NC.NUM_NOTA_ES || 'Ã' ||--0
           NVL(INV_GET_DESTINO_TRANSFERENCIA(TIP_ORIGEN_NOTA_ES,COD_DESTINO_NOTA_ES,cGrupoCia_in,cCia_in),' ') || 'Ã' ||--1
           GR.NUM_GUIA_REM|| 'Ã' ||--2
           TO_CHAR(NC.FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||--3
           NC.CANT_ITEMS || 'Ã' ||--4
           DECODE(NC.EST_NOTA_ES_CAB,'N','ANULADO',DECODE(IND_NOTA_IMPRESA,'S','IMPRESO',' '))|| 'Ã' ||--5
           NC.TIP_ORIGEN_NOTA_ES|| 'Ã' ||--6
           NC.COD_DESTINO_NOTA_ES--7
    FROM LGT_NOTA_ES_CAB NC,
              LGT_GUIA_REM  GR
    WHERE NC.COD_GRUPO_CIA = cGrupoCia_in
          AND NC.COD_LOCAL = cCodLocal_in
          AND NC.TIP_NOTA_ES = g_cTipoNotaSalida
              AND EST_NOTA_ES_CAB = 'P'
              AND IND_NOTA_IMPRESA = 'S'
              AND NC.COD_GRUPO_CIA = GR.COD_GRUPO_CIA
              AND NC.COD_LOCAL = GR.COD_LOCAL
              AND NC.NUM_NOTA_ES = GR.NUM_NOTA_ES
              AND (NC.COD_GRUPO_CIA,NC.COD_LOCAL,NC.NUM_NOTA_ES) NOT IN
              (SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES FROM T_LGT_NOTA_ES_CAB);
    RETURN curGuias;
  END;

  /****************************************************************************/
  PROCEDURE INV_CONFIRMAR_TRANSF(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumNotaEs_in IN CHAR, vIdUsu_in IN VARCHAR2)
  AS
    v_cTipOrigen        LGT_NOTA_ES_CAB.TIP_ORIGEN_NOTA_ES%TYPE;
    v_cIND_NOTA_IMPRESA LGT_NOTA_ES_CAB.IND_NOTA_IMPRESA%TYPE;
  BEGIN
    SELECT TIP_ORIGEN_NOTA_ES,IND_NOTA_IMPRESA
      INTO v_cTipOrigen,v_cIND_NOTA_IMPRESA
    FROM LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_NOTA_ES = g_cTipoNotaSalida
          AND COD_ORIGEN_NOTA_ES = cCodLocal_in
          AND COD_DESTINO_NOTA_ES <> cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in
          ;
    IF (v_cIND_NOTA_IMPRESA='S') THEN
      IF v_cTipOrigen = g_cTipoOrigenMatriz THEN
        UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in, FEC_MOD_NOTA_ES_CAB = SYSDATE,
              EST_NOTA_ES_CAB = 'A'
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND TIP_NOTA_ES = g_cTipoNotaSalida
              AND EST_NOTA_ES_CAB = 'P'
              AND IND_NOTA_IMPRESA = 'S'
              AND NUM_NOTA_ES = cNumNotaEs_in;
      ELSIF v_cTipOrigen = g_cTipoOrigenLocal THEN
        --ACTUALIZA CABECERA
        UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in, FEC_MOD_NOTA_ES_CAB = SYSDATE,
              EST_NOTA_ES_CAB = 'C'
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND TIP_NOTA_ES = g_cTipoNotaSalida
              AND EST_NOTA_ES_CAB = 'P'
              AND IND_NOTA_IMPRESA = 'S'
              AND NUM_NOTA_ES = cNumNotaEs_in;

        --CREAR TEMPORALES

        INSERT INTO T_LGT_NOTA_ES_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
        FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB,TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
        CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
        RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS,
        TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
        USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB  ,
        IND_NOTA_IMPRESA,FEC_PROCESO_SAP)
        SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
        FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB,TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
        CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
        RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS,
        TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
        USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB  ,
        IND_NOTA_IMPRESA,FEC_PROCESO_SAP
        FROM LGT_NOTA_ES_CAB
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND TIP_NOTA_ES = g_cTipoNotaSalida
              AND EST_NOTA_ES_CAB = 'C'
              AND IND_NOTA_IMPRESA = 'S'
              AND NUM_NOTA_ES = cNumNotaEs_in;

        INSERT INTO T_LGT_GUIA_REM(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
        NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM,
        EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA)
        SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
        NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM,
        EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA
        FROM LGT_GUIA_REM
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = cNumNotaEs_in;


        INSERT INTO T_LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
        COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET,
        FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR,
        NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET,
        FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA)
        SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
        COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET,
        FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR,
        NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET,
        FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA
        FROM LGT_NOTA_ES_DET
        WHERE COD_GRUPO_CIA = cGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = cNumNotaEs_in;


      END IF;
    END IF;
  END;

  FUNCTION INV_LISTA_AJUSTES_KARDEX(cCodGrupoCia_in IN CHAR ,
                                     cCodLocal_in    IN CHAR ,
                                    cFecIni_in     IN CHAR,
                                    cFecFin_in    IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
  OPEN curLabs FOR
   SELECT lp.cod_prod || 'Ã' ||
                lp.desc_prod || 'Ã' ||
                TO_CHAR(K.FEC_KARDEX,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
       NVL(MK.DESC_CORTA_MOT_KARDEX,' ')  || 'Ã' ||
       NVL(K.STK_ANTERIOR_PROD,0) || 'Ã' ||
       NVL(K.CANT_MOV_PROD,0) || 'Ã' ||
       NVL(K.STK_FINAL_PROD,0) || 'Ã' ||
       NVL(K.VAL_FRACC_PROD,0) || 'Ã' ||
    TO_CHAR(K.FEC_KARDEX,'yyyyMMddHH24miss')|| 'Ã' ||
                lb.nom_lab || 'Ã' ||
                k.usu_crea_kardex || 'Ã' ||
    NVL(k.DESC_GLOSA_AJUSTE,' ')
    FROM LGT_KARDEX K,
               LGT_MOT_KARDEX MK,
               lgt_prod lp,
               lgt_lab lb
    WHERE K.COD_GRUPO_CIA  = cCodGrupoCia_in    AND
                K.COD_LOCAL      = cCodLocal_in      AND
                k.tip_doc_kardex = 03 AND
    K.FEC_KARDEX BETWEEN TO_DATE(cFecIni_in || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') AND TO_DATE(cFecFin_in || ' 23:59:59','dd/MM/yyyy HH24:mi:ss') AND
    K.COD_GRUPO_CIA  = MK.COD_GRUPO_CIA    AND
      K.COD_MOT_KARDEX = MK.COD_MOT_KARDEX AND
                k.cod_grupo_cia = lp.cod_grupo_cia AND
                k.cod_prod = lp.cod_prod AND
                lp.cod_lab = lb.cod_lab
                ;

   RETURN curLabs;
 END;

  /****************************************************************************/
  /*FUNCTION INV_GET_CANT_MAX(nCantSug_in IN NUMBER,nPorcAdic_in IN NUMBER,
  nCantMax_in IN NUMBER,nValFrac_in IN NUMBER)
  RETURN NUMBER
  IS
    v_nCantMax LGT_PROD_LOCAL_REP.CANT_SUG%TYPE;
    v_nSugAux LGT_PROD_LOCAL_REP.CANT_SUG%TYPE;
    v_nMaxAux LGT_PROD_LOCAL_REP.CANT_SUG%TYPE;
  BEGIN
    IF nCantSug_in < 1 THEN
      v_nSugAux := 1;
      v_nMaxAux := nCantMax_in;
    ELSE
      v_nSugAux := CEIL(nCantSug_in*((100+nPorcAdic_in)/100));
      v_nMaxAux := nCantSug_in + nCantMax_in;
    END IF;
    --ERIOS 19/12/2006: SI LA FRAC >= 20, EL CANT MAXIMA ES LA CANT SUGERIDA.
    IF v_nSugAux > v_nMaxAux OR nValFrac_in >= 20 THEN
      v_nCantMax := v_nSugAux;
    ELSE
      v_nCantMax := v_nMaxAux;
    END IF;
    RETURN v_nCantMax;
  END;*/
  /****************************************************************************/
  FUNCTION GET_DIREC_ORIGEN_LOCAL(cGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vDireccion PBL_LOCAL.DIREC_LOCAL%TYPE;
  BEGIN
    SELECT DIREC_LOCAL
      INTO v_vDireccion
    FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;
    RETURN v_vDireccion;
  END;

  /****************************************************************************/
  FUNCTION INV_GET_EST_PROC_CIERRE_DIA(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cTipoNota_in IN CHAR)
  RETURN CHAR
  IS
    v_cEstado CHAR(1);
  BEGIN
    SELECT NVL2(FEC_PROCESO_CE,'S','N')
      INTO v_cEstado
    FROM LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in
          ;

    RETURN v_cEstado;
  END;

  /****************************************************************************/
  FUNCTION INV_GET_ULTIMO_PED_REP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN CHAR
  IS
    v_cNumPed LGT_PED_REP_CAB.NUM_PED_REP%TYPE;
  BEGIN
    SELECT NVL(MAX(NUM_PED_REP) ,'X')
      INTO v_cNumPed
    FROM LGT_PED_REP_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_CREA_PED_REP_CAB BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE)+0.9999;
    RETURN v_cNumPed;
  END;

  /****************************************************************************/

  PROCEDURE INV_SET_CANT_PEDREP_MATRIZ(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedRep_in IN CHAR,cCodProd_in IN CHAR,nCant_in IN NUMBER,cIdUsu_in IN VARCHAR2)
  AS
  BEGIN
    UPDATE LGT_PED_REP_DET SET USU_MOD_PED_REP_DET = cIdUsu_in,FEC_MOD_PED_REP_DET = SYSDATE,
        CANT_SOLICITADA = nCant_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_REP = cNumPedRep_in
          AND COD_PROD = cCodProd_in;
  END;

  /****************************************************************************/

  PROCEDURE INT_ENVIA_CORREO_AJUSTE(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        cSecKardex IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_AJUSTES;
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(32767);

    v_vDescLocal VARCHAR2(120);

    CURSOR curLog IS
    SELECT K.COD_PROD||'-'||P.DESC_PROD AS PROD,
          P.DESC_UNID_PRESENT AS UNID_PRESENT,
          NVL(TRIM(L.UNID_VTA),P.DESC_UNID_PRESENT ) AS UNID_VENTA,
          TO_CHAR(FEC_CREA_KARDEX,'dd/MM/yyyy HH24:MI:SS') AS FECHA,
          M.COD_MOT_KARDEX||'-'||M.DESC_CORTA_MOT_KARDEX AS MOT,
          STK_ANTERIOR_PROD,CANT_MOV_PROD,STK_FINAL_PROD,VAL_FRACC_PROD,
          K.USU_CREA_KARDEX,DESC_GLOSA_AJUSTE
    FROM LGT_KARDEX K, LGT_PROD P, LGT_PROD_LOCAL L, LGT_MOT_KARDEX M
    WHERE K.COD_GRUPO_CIA = cCodGrupoCia_in
          AND K.COD_LOCAL = cCodLocal_in
          AND K.SEC_KARDEX = cSecKardex
          AND K.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND K.COD_PROD = P.COD_PROD
          AND K.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND K.COD_LOCAL = L.COD_LOCAL
          AND K.COD_PROD = L.COD_PROD
          AND K.COD_GRUPO_CIA = M.COD_GRUPO_CIA
          AND K.COD_MOT_KARDEX = M.COD_MOT_KARDEX;

    v_rCurLog curLog%ROWTYPE;
  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    --CREAR CUERPO MENSAJE;
    FOR v_rCurLog IN curLog
    LOOP
      --PRODUCTO
      mesg_body := mesg_body||'<table style="text-align: left; width: 97%;" border="1"';
      mesg_body := mesg_body||' cellpadding="2" cellspacing="1">';
      mesg_body := mesg_body||'  <tbody>';
      mesg_body := mesg_body||'    <tr>';
      mesg_body := mesg_body||'      <th><small>LOCAL</small></th>';
      mesg_body := mesg_body||'      <th><small>PRODUCTO</small></th>';
      mesg_body := mesg_body||'      <th><small>UNID PRESENT</small></th>';
      mesg_body := mesg_body||'      <th><small>UNID VENTA</small></th>';
      mesg_body := mesg_body||'    </tr>';

      mesg_body := mesg_body||'   <tr>'||
                              '      <td><small>'||v_vDescLocal||'</small></td>'||
                              '      <td><small>'||v_rCurLog.PROD||'</small></td>'||
                              '      <td><small>'||v_rCurLog.UNID_PRESENT||'</small></td>'||
                              '      <td><small>'||v_rCurLog.UNID_VENTA||'</small></td>'||
                              '   </tr>';

      mesg_body := mesg_body||'  </tbody>';
      mesg_body := mesg_body||'</table>';
      mesg_body := mesg_body||'<br>';

      --AJUSTE
      mesg_body := mesg_body||'<table style="text-align: left; width: 97%;" border="1"';
      mesg_body := mesg_body||' cellpadding="2" cellspacing="1">';
      mesg_body := mesg_body||'  <tbody>';
      mesg_body := mesg_body||'    <tr>';
      mesg_body := mesg_body||'      <th><small>FECHA</small></th>';
      mesg_body := mesg_body||'      <th><small>MOTIVO</small></th>';
      mesg_body := mesg_body||'      <th><small>STK ANT</small></th>';
      mesg_body := mesg_body||'      <th><small>CANT MOV</small></th>';
      mesg_body := mesg_body||'      <th><small>STK FINAL</small></th>';
      mesg_body := mesg_body||'      <th><small>FRACCION</small></th>';
      mesg_body := mesg_body||'      <th><small>USUARIO</small></th>';
      mesg_body := mesg_body||'      <th><small>GLOSA</small></th>';
      mesg_body := mesg_body||'    </tr>';

      mesg_body := mesg_body||'   <tr>'||
                              '      <td><small>'||v_rCurLog.FECHA||'</small></td>'||
                              '      <td><small>'||v_rCurLog.MOT||'</small></td>'||
                              '      <td align = center><small>'||v_rCurLog.STK_ANTERIOR_PROD||'</small></td>'||
                              '      <td align = center><small>'||v_rCurLog.CANT_MOV_PROD||'</small></td>'||
                              '      <td align = center><small>'||v_rCurLog.STK_FINAL_PROD||'</small></td>'||
                              '      <td align = center><small>'||v_rCurLog.VAL_FRACC_PROD||'</small></td>'||
                              '      <td><small>'||v_rCurLog.USU_CREA_KARDEX||'</small></td>'||
                              '      <td><small>'||v_rCurLog.DESC_GLOSA_AJUSTE||'</small></td>'||
                              '   </tr>';

      mesg_body := mesg_body||'  </tbody>';
      mesg_body := mesg_body||'</table>';
      mesg_body := mesg_body||'<br>';

    END LOOP;
    --FIN HTML

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                          ReceiverAddress,
                          vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                          vTitulo_in,--'EXITO',
                          mesg_body,
                          CCReceiverAddress,
                          FARMA_EMAIL.GET_EMAIL_SERVER,
                          true);

  END;

  /************************************************/
  FUNCTION GET_EST_PROCESO_SAP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cTipoNota_in IN CHAR)
  RETURN CHAR
  IS
    v_cEstado CHAR(1);
  BEGIN
    SELECT NVL2(FEC_PROCESO_CE,'S','N')
      INTO v_cEstado
    FROM LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in
          ;

    RETURN v_cEstado;
  END;

  /****************************************************************************/
  FUNCTION INV_GET_FILTRO_TRANSF(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
    curMot FarmaCursor;
  BEGIN
      OPEN curMot FOR
      SELECT COD_MOT_KARDEX || 'Ã' ||
            DESC_CORTA_MOT_KARDEX
      FROM LGT_MOT_KARDEX
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_MOT_KARDEX = g_cTipoMotKardexTransfInterna
      UNION
      SELECT LLAVE_TAB_GRAL || 'Ã' ||
                  DESC_CORTA
          FROM PBL_TAB_GRAL
          WHERE COD_APL = g_vCodPtoVenta
                AND COD_TAB_GRAL = g_vMotivoAjuste;

    RETURN curMot;
  END;

  FUNCTION INV_GET_DATOS_TRANSPORTE(cCodGrupoCia_in IN CHAR,cCodLocal_in   IN CHAR,cCodDestino_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTransp FarmaCursor;
  BEGIN
    OPEN curTransp FOR
    SELECT C.NOM_CIA || 'Ã' ||
        C.NUM_RUC_CIA || 'Ã' ||
        NVL(D.DIREC_LOCAL,' ')  || 'Ã' ||
        INV_GET_TRANSP(cCodGrupoCia_in,cCodLocal_in)
    FROM PBL_LOCAL D, PBL_CIA C
    WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodDestino_in
          AND C.COD_CIA = D.COD_CIA;

    RETURN curTransp;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_CANT_RECEP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR, cNumeroEntrega_in IN CHAR)
  RETURN FarmaCursor
  IS
    curRecep FarmaCursor;
  BEGIN
    OPEN curRecep FOR
    SELECT C.EST_RECEPCION|| 'Ã' ||D.CANT_ENVIADA_MATR|| 'Ã' ||D.CANT_MOV|| 'Ã' ||
        C.NUM_NOTA_ES|| 'Ã' ||D.POSICION|| 'Ã' ||D.IND_AJUSTE_DIF|| 'Ã' ||
        G.NUM_GUIA_REM|| 'Ã' ||D.NUM_ENTREGA
    FROM LGT_NOTA_ES_DET D, LGT_NOTA_ES_CAB C, LGT_GUIA_REM G
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodLocal_in
          AND D.COD_PROD = cCodProd_in
          AND D.NUM_ENTREGA = cNumeroEntrega_in
          AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND D.COD_LOCAL = C.COD_LOCAL
          AND D.NUM_NOTA_ES = C.NUM_NOTA_ES
          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND C.COD_LOCAL = G.COD_LOCAL
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES
          AND D.SEC_GUIA_REM = G.SEC_GUIA_REM
    ;
    RETURN curRecep;
  END;
  /*****************************************************************************/
  PROCEDURE INV_INGRESA_AJUSTE_DIFERENCIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR,cCodMotKardex_in IN CHAR,nCantAjuste_in IN NUMBER,
  cTipDoc_in IN CHAR,cNumeroEntrega_in IN CHAR,cNumNotaEs_in IN CHAR,
  nNumPos_in IN NUMBER,cUsu_in IN CHAR)
  AS
    v_nStkFisico   LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nValFrac     LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_vDescUnidVta LGT_PROD_LOCAL.UNID_VTA%TYPE;
    vCantMov_in    NUMBER;
    v_nNeoCod    CHAR(10);
    v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;
    v_cIndAjuste LGT_NOTA_ES_DET.IND_AJUSTE_DIF%TYPE;
  BEGIN
     --Obtener STK Actual
    SELECT STK_FISICO,VAL_FRAC_LOCAL,UNID_VTA
      INTO   v_nStkFisico,v_nValFrac,v_vDescUnidVta
    FROM   LGT_PROD_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in  AND
             COD_LOCAL     = cCodLocal_in    AND
             COD_PROD     = cCodProd_in FOR UPDATE;

    vCantMov_in:= nCantAjuste_in * v_nValFrac;
    v_nNeoCod:=Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_MOV_AJUSTE_KARD),10,'0','I' );
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_MOV_AJUSTE_KARD, cUsu_in);

   --Actualizar Stock de Prod Local
    UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = cUsu_in,FEC_MOD_PROD_LOCAL = SYSDATE,
          STK_FISICO       = v_nStkFisico + vCantMov_in
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in  AND
                   COD_LOCAL     = cCodLocal_in    AND
                   COD_PROD     = cCodProd_in;

   --INSERTAR KARDEX
    v_cSecKardex :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_SEC_KARDEX),10,'0','I' );
    INSERT INTO LGT_KARDEX(COD_GRUPO_CIA, COD_LOCAL, SEC_KARDEX, COD_PROD, COD_MOT_KARDEX,
                                     TIP_DOC_KARDEX, NUM_TIP_DOC, STK_ANTERIOR_PROD, CANT_MOV_PROD,
                                     STK_FINAL_PROD, VAL_FRACC_PROD, DESC_UNID_VTA, USU_CREA_KARDEX,
                                     TIP_COMP_PAGO, NUM_COMP_PAGO)
     VALUES (cCodGrupoCia_in,  cCodLocal_in, v_cSecKardex, cCodProd_in, cCodMotKardex_in,
                                     cTipDoc_in, v_nNeoCod, v_nStkFisico, vCantMov_in,
                                     (v_nStkFisico + vCantMov_in), v_nValFrac, v_vDescUnidVta, cUsu_in,
                                     g_cTipCompNumEntrega,cNumeroEntrega_in);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, COD_NUMERA_SEC_KARDEX, cUsu_in);
    --ACTUALIZAR DETALLE
    SELECT IND_AJUSTE_DIF
      INTO v_cIndAjuste
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in
          AND COD_PROD = cCodProd_in
          AND NUM_ENTREGA = cNumeroEntrega_in
          AND POSICION = nNumPos_in FOR UPDATE;
    IF v_cIndAjuste = 'S' THEN
      RAISE_APPLICATION_ERROR(-20082,'EL DETALLE YA FUE AJUSTADO.');
    ELSE
      UPDATE LGT_NOTA_ES_DET
      SET FEC_MOD_NOTA_ES_DET = SYSDATE, USU_MOD_NOTA_ES_DET = cUsu_in,
          IND_AJUSTE_DIF = 'S'
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_NOTA_ES = cNumNotaEs_in
            AND COD_PROD = cCodProd_in
            AND NUM_ENTREGA = cNumeroEntrega_in
            AND POSICION = nNumPos_in;
    END IF;
    --ENVIAR MAIL DE AJUSTE
    INT_ENVIA_CORREO_AJUSTE(cCodGrupoCia_in,cCodLocal_in,
                                        'AJUSTE POR DIFERENCIAS: ',
                                        'AJUSTE',
                                        v_cSecKardex);
  END;
  /*****************************************************************************/
  FUNCTION INV_STOCK_LOCALES_PREFERIDOS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        cCodProd_in IN CHAR)
  RETURN FarmaCursor
  IS
    curInv FarmaCursor;
  BEGIN
       DELETE FROM VTA_STK_LOCALES;

        EXECUTE IMMEDIATE
        ' INSERT INTO VTA_STK_LOCALES ' ||
        ' SELECT PROD_LOCAL.cod_local , ' ||
        '        LOCAL.DESC_CORTA_LOCAL , '||
        '        to_char(PROD_LOCAL.STK_FISICO,''999,990''),' ||
        '        decode(PROD_LOCAL.Ind_Prod_Fraccionado,''S'',PROD_LOCAL.unid_vta,'' '') ' ||
        ' FROM   LGT_PROD_LOCAL@xe_del_999 PROD_LOCAL, '||
        '        PBL_LOCAL LOCAL ' ||
        ' WHERE  PROD_LOCAL.COD_GRUPO_CIA = ''' ||  CCODGRUPOCIA_IN  || ''' ' ||
        ' AND    PROD_LOCAL.COD_LOCAL  IN (SELECT COD_LOCAL_PREFERIDO ' ||
        '                                  FROM PBL_LOCAL_PREFERIDO ' ||
        '                                 WHERE COD_LOCAL = ''' || ccodlocal_in ||''')' ||
        ' AND    LOCAL.Tip_Local = ''' ||  g_cTipoLocalVenta  || ''' ' ||
        ' AND    PROD_LOCAL.cod_prod = ''' || ccodprod_in || ''' ' ||
        ' AND    LOCAL.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA ' ||
        ' AND    LOCAL.COD_LOCAL = PROD_LOCAL.COD_LOCAL' ;

--        EXECUTE IMMEDIATE 'ALTER SESSION CLOSE DATABASE LINK XE_DEL_999';

    OPEN curInv FOR
        SELECT SL.COD_LOCAL || 'Ã' ||
               SL.DESC_LOCAL || 'Ã' ||
               SL.STCK_LOCAL || 'Ã' ||
               SL.UNIDAD_VTA_LOCAL
        FROM   VTA_STK_LOCALES SL ;

    RETURN CURINV;

  END;
  /*****************************************************************************/
  FUNCTION INV_OBTIENE_STOCK_EN_lINEA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cCodProd_in IN CHAR/*,
                                      STOCK in OUT CHAR,
                                      UNIDAD_VENTA in OUT CHAR*/)
  RETURN VARCHAR2
  IS
   v_vSentencia VARCHAR2(32767);
  BEGIN
        EXECUTE IMMEDIATE ' SELECT to_char(stk_fisico) || ''Ã'' || '  ||
                      '        NVL(unid_vta, '' '') '  ||
                      ' FROM   lgt_prod_local@XE_'||cCodLocal_in  ||
                      ' WHERE  cod_grupo_cia = '''|| cCodGrupoCia_in ||''' ' ||
                      ' AND    cod_local = '''|| cCodLocal_in ||''' ' ||
                      ' AND    cod_prod  = '''|| cCodProd_in ||''' ' INTO v_vsentencia;

    --OPEN RETORNO  FOR v_Vsentencia;
    --     FETCH curInv ;
    --     CLOSE curInv ;

   RETURN V_VSENTENCIA ;
   END;

  /*****************************************************************************/
  FUNCTION  INV_CONECTA_MATRIZ(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in IN CHAR,
                              cCodProd_in IN CHAR/*,
                              STOCK in OUT CHAR,
                              UNIDAD_VENTA in OUT CHAR*/)
  RETURN VARCHAR2
  IS
  RETORNO  VARCHAR2(32767);
  BEGIN
   EXECUTE IMMEDIATE ' SELECT PTOVENTA_INV.INV_OBTIENE_STOCK_EN_LINEA@XE_000(:1,:2,:3) FROM DUAL '
                     INTO RETORNO USING CCODGRUPOCIA_IN,CCODLOCAL_IN,CCODPROD_IN;
--  PTOVENTA_INV.INV_OBTIENE_STOCK_EN_LINEA@XE_000(CCODGRUPOCIA_IN,CCODLOCAL_IN,CCODPROD_IN,STOCK,UNIDAD_VENTA  );
  RETURN RETORNO ;
  END;

  /* ************************************************************************ */

  PROCEDURE INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in     IN CHAR,
                                      cCodLocal_in        IN CHAR,
                                      cCodProd_in          IN CHAR,
                                      cCodMotKardex_in     IN CHAR,
                                       cTipDocKardex_in    IN CHAR,
                                       cNumTipDoc_in        IN CHAR,
                                      nCantMovProd_in      IN NUMBER,
                                      nValFrac_in          IN NUMBER,
                                      cDescUnidVta_in      IN CHAR,
                                      cUsuCreaKardex_in    IN CHAR,
                                      cCodNumera_in         IN CHAR,
                                      cGlosa_in            IN CHAR DEFAULT NULL,
                                      cTipDoc_in          IN CHAR DEFAULT NULL,
                                      cNumDoc_in          IN CHAR DEFAULT NULL)
  IS
    v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;
  BEGIN
       v_cSecKardex :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in),10,'0','I' );
       INSERT INTO LGT_KARDEX(COD_GRUPO_CIA, COD_LOCAL, SEC_KARDEX, COD_PROD, COD_MOT_KARDEX,
                              TIP_DOC_KARDEX, NUM_TIP_DOC, STK_ANTERIOR_PROD, CANT_MOV_PROD,
                              STK_FINAL_PROD, VAL_FRACC_PROD, DESC_UNID_VTA, USU_CREA_KARDEX,DESC_GLOSA_AJUSTE,
                              TIP_COMP_PAGO,NUM_COMP_PAGO)
                      VALUES (cCodGrupoCia_in,  cCodLocal_in, v_cSecKardex, cCodProd_in, cCodMotKardex_in,
                              cTipDocKardex_in, cNumTipDoc_in, 0, nCantMovProd_in,
                              0, nValFrac_in, cDescUnidVta_in, cUsuCreaKardex_in,cGlosa_in,
                              cTipDoc_in,cNumDoc_in);
       Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in, cUsuCreaKardex_in);
  END;

    FUNCTION INV_GET_DET_REP_FILTRO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                  cNroPedido_in   IN CHAR,
                                  cCodFiltro_in   IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
    SELECT D.COD_PROD        || 'Ã' ||
           P.DESC_PROD       || 'Ã' ||
           P.DESC_UNID_PRESENT   || 'Ã' ||
           TO_CHAR(D.CANT_SOLICITADA,'99,990') || 'Ã' ||
           TO_CHAR(D.CANT_SUGERIDA,'99,990') || 'Ã' ||
           TO_CHAR(D.STK_DISPONIBLE/D.VAL_FRAC_PROD ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_DIA_ROT*D.VAL_ROT_PROD ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_MIN_STK ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_MAX_STK ,'999,990')   || 'Ã' ||
           TO_CHAR(D.CANT_DIA_ROT ,'999,990')  || 'Ã' ||
           TO_CHAR(D.NUM_DIAS ,'999,990')  || 'Ã' ||
           TO_CHAR(D.CANT_EXHIB ,'999,990')  || 'Ã' ||
           A.NOM_LAB         || 'Ã' ||
           TO_CHAR(D.STK_TRANSITO ,'999,990')   || 'Ã' ||
           TO_CHAR(D.VAL_ROT_PROD,'999,990.000')
    FROM   LGT_PED_REP_DET D,
           LGT_PED_REP_CAB C,
           LGT_PROD_LOCAL L,
           LGT_PROD P,
           LGT_LAB  A
    WHERE  C.COD_GRUPO_CIA     = cCodGrupoCia_in
    AND    C.COD_LOCAL     = cCodLocal_in
    AND    C.NUM_PED_REP   = cNroPedido_in
    AND    A.COD_LAB    = ccodfiltro_in
    AND    C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    C.COD_LOCAL     = D.COD_LOCAL
    AND    C.NUM_PED_REP   = D.NUM_PED_REP
    AND    D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
    AND    D.COD_LOCAL     = L.COD_LOCAL
    AND    D.COD_PROD      = L.COD_PROD
    AND    L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
    AND    L.COD_PROD      = P.COD_PROD
    AND    P.COD_LAB       = A.COD_LAB;
    RETURN curProd;
  END;

  /* ************************************************************************ */

  FUNCTION  INV_OBTIENE_FECHA_REPOSICION(cCodGrupoCia_in     IN CHAR,
                                         cCodLocal_in        IN CHAR)
  RETURN VARCHAR2
  IS
  RETORNO VARCHAR2(30);
  BEGIN
       SELECT TO_CHAR(LOCAL.FEC_GENERA_MAX_MIN,'dd/MM/yyyy HH24:MI:SS') INTO RETORNO
       FROM   PBL_LOCAL LOCAL
       WHERE  LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    LOCAL.COD_LOCAL = cCodLocal_in;

    RETURN RETORNO ;
  END;

  /* ************************************************************************ */

  FUNCTION INV_OBTIENE_IND_STOCK(cCodProd_in IN CHAR)
  RETURN VARCHAR2
  IS
   v_vSentencia VARCHAR2(32767);
  BEGIN
        -- PRIMERO ES DE LOCALES, SEGUNDO ES DE ALMACEN
        EXECUTE IMMEDIATE ' SELECT CASE WHEN SUM(CASE WHEN COD_LOCAL = ''009'' THEN 1 ELSE 0 END) > 0 THEN ''S'' ELSE ''N'' END|| ''Ã'' ||' ||
                          '        CASE WHEN SUM(CASE WHEN COD_LOCAL = ''009'' THEN 0 ELSE 1 END) > 0 THEN ''S'' ELSE ''N'' END' ||
                          ' FROM LGT_PROD_LOCAL@XE_DEL_999 ' ||
                          ' WHERE COD_PROD = '''|| cCodProd_in ||''' ' ||
                          ' AND STK_FISICO > 0 ' INTO v_vSentencia ;

   dbms_output.put_line(v_vSentencia);

   RETURN V_VSENTENCIA ;
   END;

 /******************************************************************************/

   PROCEDURE INV_SET_CANT_ADIC_TMP(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   nCantTmp_in     IN NUMBER,
                                   cIdUsu_in       IN VARCHAR2)
  AS
  BEGIN
    UPDATE LGT_PROD_LOCAL_REP SET USU_MOD_PROD_LOCAL_REP = cIdUsu_in,FEC_MOD_PROD_LOCAL_REP = SYSDATE,
           CANT_ADIC = nCantTmp_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND COD_PROD = cCodProd_in;

  END;

 /******************************************************************************/

  FUNCTION INV_LISTA_PROD_REP_VER_ADIC(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
       OPEN curProd FOR
          SELECT P.COD_PROD                            || 'Ã' ||
                 P.DESC_PROD                           || 'Ã' ||
                 P.DESC_UNID_PRESENT                   || 'Ã' ||
                 TO_CHAR(T.CANT_MIN_STK,'999,990')     || 'Ã' ||
                 TO_CHAR(T.CANT_MAX_STK,'999,990')     || 'Ã' ||
                 TO_CHAR(TRUNC(L.STK_FISICO/L.VAL_FRAC_LOCAL),'999,990') ||' / '|| DECODE(L.VAL_FRAC_LOCAL,1,' ',MOD(L.STK_FISICO,L.VAL_FRAC_LOCAL)) || 'Ã' ||
                 DECODE(L.IND_REPONER,'N','NO REPONER',' ')                           || 'Ã' ||
                 TO_CHAR(DECODE(T.CANT_SUG+ABS(T.CANT_SUG),0,0,T.CANT_SUG),'999,990') || 'Ã' ||
                 DECODE(T.CANT_SOL,NULL,' ',TO_CHAR(T.CANT_SOL,'999,990'))            || 'Ã' ||--SOL
                 DECODE(T.CANT_ADIC,NULL,' ',TO_CHAR(T.CANT_ADIC,'999,990'))          || 'Ã' ||--ADIC
                 B.NOM_LAB                                                            || 'Ã' ||
                 L.CANT_EXHIB                                                         || 'Ã' ||
                 T.CANT_TRANSITO                                                      || 'Ã' ||
                 DECODE(G.NUM_MIN_DIAS_REP,0,' ',NULL,' ',G.NUM_MIN_DIAS_REP)         || 'Ã' ||
                 DECODE(G.NUM_MAX_DIAS_REP,0,' ',NULL,' ',G.NUM_MAX_DIAS_REP)         || 'Ã' ||
                 DECODE(G.NUM_DIAS_ROT,0,' ',NULL,' ',G.NUM_DIAS_ROT)                 || 'Ã' ||
                 TO_CHAR(T.CANT_ROT,'99,990.000')                                     || 'Ã' ||
                 0                                                                    || 'Ã' ||
                 TO_CHAR(T.CANT_VTA_PER_0, '999,990.000')                             || 'Ã' ||
                 TO_CHAR(T.CANT_VTA_PER_1, '999,990.000')                             || 'Ã' ||
                 TO_CHAR(T.CANT_VTA_PER_2, '999,990.000')                             || 'Ã' ||
                 TO_CHAR(T.CANT_VTA_PER_3, '999,990.000')                             || 'Ã' ||
                 L.VAL_FRAC_LOCAL                                                     || 'Ã' ||
                 T.CANT_MAX_ADIC                                                      || 'Ã' ||
                 P.IND_TIPO_PROD
          FROM   LGT_PROD P,
                 LGT_PROD_LOCAL L,
                 LGT_PROD_LOCAL_REP T,
                 LGT_LAB B,
                 LGT_GRUPO_REP_LOCAL G
          WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    L.COD_LOCAL     = cCodLocal_in
          AND    T.CANT_ADIC     IS NOT NULL
          AND    L.IND_REPONER   = 'S'
          AND    P.EST_PROD      = 'A'
          AND    L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND    L.COD_PROD      = P.COD_PROD
          AND    L.COD_GRUPO_CIA = T.COD_GRUPO_CIA
          AND    L.COD_LOCAL     = T.COD_LOCAL
          AND    L.COD_PROD      = T.COD_PROD
          AND    P.COD_LAB       = B.COD_LAB
          AND    L.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND    L.COD_LOCAL     = G.COD_LOCAL
          AND    G.COD_GRUPO_REP = P.COD_GRUPO_REP;
       RETURN curProd;
  END INV_LISTA_PROD_REP_VER_ADIC;

  /*****************************************************************************/

  FUNCTION INV_GET_PED_ACT_REP_ADIC(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curPed FarmaCursor;
  BEGIN
    OPEN curPed FOR
        SELECT COUNT(*)                 || 'Ã' ||
               NVL(SUM(CANT_ADIC),0)
        FROM   LGT_PROD_LOCAL_REP T,
               LGT_PROD_LOCAL L, LGT_PROD P
        WHERE  CANT_ADIC         IS NOT NULL
        AND    CANT_ADIC         > 0
        AND    T.COD_GRUPO_CIA   = cCodGrupoCia_in
        AND    T.COD_LOCAL       = cCodLocal_in
        AND    L.IND_REPONER     = 'S'
        AND    P.EST_PROD        = 'A'
        AND    T.COD_GRUPO_CIA   = L.COD_GRUPO_CIA
        AND    T.COD_LOCAL       = L.COD_LOCAL
        AND    T.COD_PROD        = L.COD_PROD
        AND    L.COD_GRUPO_CIA   = P.COD_GRUPO_CIA
        AND    L.COD_PROD        = P.COD_PROD;
    RETURN curPed;
  END;
  /*****************************************************************************/
  FUNCTION INV_GET_MOTIVO_TRANS_INTERNO
  RETURN FARMACURSOR
       IS CurMot FarmaCursor;

  BEGIN
       OPEN curMot FOR
            SELECT ptg.llave_tab_gral || 'Ã' ||
                   ptg.desc_corta
            FROM   pbl_tab_gral ptg
            WHERE  ptg.cod_apl = 'PTO_VENTA'
            AND    PTG.COD_TAB_GRAL = 'MOTIVO_TRANSFERENCIA'
            AND    ptg.est_tab_gral = 'A';
  RETURN CURMOT ;

 END;

  /*****************************************************************************/
  PROCEDURE  INV_ACTUALIZA_IND_LINEA (cIdUsuario_in IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cCodGrupoCia_in IN CHAR)
--  RETURN VARCHAR2
  IS
   v_valor VARCHAR2(3);
  BEGIN
        EXECUTE IMMEDIATE ' SELECT COD_LOCAL ' ||
                          ' FROM LGT_PROD_LOCAL@XE_DEL_999 ' ||
                          ' WHERE ROWNUM < 2 ' INTO v_valor;
        IF(v_valor IS NOT NULL) THEN
           UPDATE PBL_LOCAL SET FEC_MOD_LOCAL = SYSDATE, USU_MOD_LOCAL = cIdUsuario_in,
                                IND_EN_LINEA = 'S'
           WHERE   COD_LOCAL = cCodLocal_in
           AND     COD_GRUPO_CIA = cCodGrupoCia_in;
        END IF ;

        EXCEPTION
          WHEN OTHERS THEN
             dbms_output.put_line('se cayo');
           UPDATE PBL_LOCAL SET FEC_MOD_LOCAL = SYSDATE, USU_MOD_LOCAL = cIdUsuario_in,
                                IND_EN_LINEA = 'N'
           WHERE   COD_LOCAL = cCodLocal_in
           AND     COD_GRUPO_CIA = cCodGrupoCia_in ;
   END;

  /*****************************************************************************/

   FUNCTION INV_OBTIENE_IND_LINEA(cCodLocal_in  IN CHAR,
                                  cCodGrupoCia_in IN CHAR)
   RETURN VARCHAR2
   IS
     v_Retorno VARCHAR2(10);
     v_IndLinea CHAR(1);
     BEGIN
          SELECT IND_EN_LINEA INTO v_IndLinea
          FROM PBL_LOCAL
          WHERE COD_LOCAL = cCodLocal_in
          AND   COD_GRUPO_CIA = cCodGrupoCia_in ;

          IF(v_IndLinea = 'S') THEN
           v_Retorno := 'TRUE' ;
          ELSIF (v_IndLinea = 'N') THEN
           v_retorno := 'FALSE' ;
          END IF ;

    RETURN V_RETORNO;
   END;

  /*****************************************************************************/

  PROCEDURE INV_ACTUALIZA_IND_CAL_MAX_MIN(cCodGrupoCia_in  IN CHAR,
                                          cCodLocal_in     IN CHAR,
                                          cIdUSuMod_in     IN CHAR,
                                          cCantAtendida_in IN CHAR,
                                          cNumPedido_in    IN CHAR,
                                          cCodProd_in      IN CHAR)
  IS
    BEGIN
         UPDATE VTA_PEDIDO_VTA_DET D SET D.FEC_MOD_PED_VTA_DET = SYSDATE, D.USU_MOD_PED_VTA_DET = cIdUSuMod_in,
                                         D.IND_CALCULO_MAX_MIN = 'N', D.FEC_EXCLUSION = SYSDATE
         WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    D.COD_LOCAL =  cCodLocal_in
         AND    D.COD_PROD = cCodProd_in
--         AND    D.CANT_ATENDIDA = cCantAtendida_in
-- 2008-09-19 JOLIVA - Se modifica para que funcione para productos con venta sugerida
         -- AND    D.CANT_FRAC_LOCAL = cCantAtendida_in
         AND    D.NUM_PED_VTA = cNumPedido_in;
  END;

  /*****************************************************************************/

  FUNCTION INV_VALIDA_FECHA_COT_COMP(cCodGrupoCia_in  IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cFechaIngreso_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_LlaveTabGral pbl_tab_gral.llave_tab_gral%TYPE;
    v_FechaLimite  DATE;
    v_retorno      VARCHAR2(5);
  BEGIN
      SELECT ptg.llave_tab_gral INTO v_LlaveTabGral
      FROM   pbl_tab_gral ptg
      WHERE  PTG.Id_Tab_Gral = 108
      AND    ptg.cod_apl = 'PTO_VENTA'
      AND    PTG.COD_TAB_GRAL = 'INVENTARIO'
      AND    ptg.est_tab_gral = 'A';

      SELECT TO_CHAR(SYSDATE-v_LlaveTabGral,'DD/MM/YYYY') INTO v_FechaLimite FROM DUAL;

      IF(cFechaIngreso_in < v_FechaLimite) THEN
       v_retorno:= 'TRUE';
      ELSE
       v_retorno:= 'FALSE';
      END IF;

  RETURN V_RETORNO;

  END;

  --Descripcion: oBTIENE TIEM STAM
  --Fecha       Usuario        Comentario
  --12/09/2007  DUBILLUZ      Creación
  FUNCTION OBTIENE_TIME_STAMP(cCodGrupoCia_in  IN CHAR)
                                                   RETURN VARCHAR2
   IS
    v_time TIMESTAMP;
   BEGIN
    SELECT CURRENT_TIMESTAMP into v_time  FROM dual ;
   RETURN ''||v_time;
   END;

  FUNCTION GET_EST_PROCESO_SAP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumPedRep_in IN CHAR)
  RETURN CHAR
  IS
    v_cInd CHAR(1);
  BEGIN
    SELECT NVL2(FEC_PROCESO_SAP,'S','N')
      INTO v_cInd
    FROM LGT_PED_REP_CAB
    WHERE COD_GRUPO_CIA = cGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_PED_REP = cNumPedRep_in
          ;
    RETURN v_cInd;
  END;

/* ***************************************************************************/
  FUNCTION  INV_GET_IND_TXTFRACC_MOTIVO(cCodGrupoCia_in IN CHAR,
                                        cCodTipo_in     IN CHAR,
                                        cCodMotivo      IN CHAR)
  RETURN CHAR
  IS
  vDescMotivo  VARCHAR2(100);
  vIndTextFraccion   CHAR(1);
  BEGIN
   IF cCodTipo_in <> g_cTipoOrigenLocal THEN

    SELECT DESC_CORTA   INTO vDescMotivo
    FROM   PBL_TAB_GRAL T
    WHERE  T.COD_APL = 'PTOVENTA'
    AND    T.LLAVE_TAB_GRAL = cCodMotivo
    AND    T.COD_TAB_GRAL = 'MOTIVOS DE AJUSTE';

    SELECT LLAVE_TAB_GRAL INTO vIndTextFraccion
    FROM   PBL_TAB_GRAL F
    WHERE  COD_APL = 'PTO_VENTA'
    AND    DESC_CORTA  = vDescMotivo
    --AND    DESC_LARGA  = vDescMotivo;--  JMIRANDA 10.12.09
    AND    COD_TAB_GRAL LIKE 'TRANS_FRACCION_%';

   ELSE
    vIndTextFraccion := 'N';
   END IF;

    RETURN vIndTextFraccion ;

    EXCEPTION
    WHEN OTHERS THEN
       dbms_output.put_line('OCURRIO UN ERROR');
  END;

  /*****************************************************************************/

  FUNCTION INV_LISTA_COMPETENCIAS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curcomp FarmaCursor;
  BEGIN
    OPEN curcomp FOR
        SELECT C.RUC_COMPETENCIA || 'Ã' ||
               C.DESC_CORTA_COMP
        FROM   LGT_MAE_COMPETENCIA C
        WHERE  C.EST_COMPETENCIA = 'A';
    RETURN curcomp;
  END;

/* ***************************************************************************/
  FUNCTION  INV_VALIDA_NUM_COMPETENCIA(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumDoc_in      IN CHAR,
                                       cTipDoc_in      IN CHAR,
                                       cRucEmpresa     IN CHAR)
  RETURN CHAR
  IS
    cRetorno CHAR(1);
    cNum     NUMBER;
  BEGIN
        SELECT COUNT(1)
        INTO   cNum
        FROM   LGT_NOTA_ES_CAB N
        WHERE  N.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    N.COD_LOCAL     = cCodLocal_in
        AND    N.EST_NOTA_ES_CAB = 'A'
        AND    N.NUM_DOC       = TRIM(cNumDoc_in)
        AND    N.TIP_DOC       = cTipDoc_in
        AND    N.RUC_EMPRESA   = cRucEmpresa;

    IF cNum > 0 THEN
      cRetorno:= 'S';
    ELSE
      cRetorno:= 'N';
    END IF;

    RETURN cRetorno;
  END;

/* ***************************************************************************/
  FUNCTION INV_BUSCA_EMPRESA(cCodGrupoCia_in IN CHAR,
                             cRUC_in         IN CHAR)
  RETURN FarmaCursor
  IS
    curcomp FarmaCursor;
  BEGIN
    OPEN curcomp FOR
        SELECT C.DESC_CORTA_COMP || 'Ã' ||
               C.RUC_COMPETENCIA
        FROM   LGT_MAE_COMPETENCIA C
        WHERE  C.EST_COMPETENCIA = 'A'
        AND    C.RUC_COMPETENCIA  = cRUC_in;
    RETURN curcomp;
  END;

/* ***************************************************************************/

  FUNCTION  REP_GET_DATOS_COTIZACION(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cNumDoc_in      IN CHAR,
                                     cTipDoc_in      IN CHAR,
                                     cRucEmpresa     IN CHAR)
  RETURN FarmaCursor
  IS
    cRetorno CHAR(1);
    cNum     NUMBER;
    curcomp FarmaCursor;
  BEGIN

    OPEN curcomp FOR

        SELECT N.NUM_NOTA_ES  || 'Ã' ||
               TO_CHAR(N.FEC_NOTA_ES_CAB,'dd/MM/yyyy')  || 'Ã' ||
               N.NUM_DOC  || 'Ã' ||
               'S/.'||trim(TO_CHAR(N.Val_Total_Nota_Es_Cab,'999,990.00'))   || 'Ã' ||

               N.RUC_EMPRESA  || 'Ã' ||
               N.DESC_EMPRESA
        FROM   LGT_NOTA_ES_CAB N
        WHERE  N.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    N.COD_LOCAL     = cCodLocal_in
        AND    N.EST_NOTA_ES_CAB = 'A'
        AND    N.NUM_DOC       = TRIM(cNumDoc_in)
        AND    N.TIP_DOC       = cTipDoc_in
        AND    N.RUC_EMPRESA   = cRucEmpresa;

    RETURN curcomp;
  END;

/* ***************************************************************************/


 FUNCTION  INV_F_GET_VAL_FRAC_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cCodProd_in IN CHAR)
 RETURN NUMBER
 IS
   v_nValFrac LGT_PROD_LOCAL.Val_Frac_Local%TYPE:=0;
 BEGIN
     SELECT VAL_FRAC_LOCAL INTO  v_nValFrac
     FROM LGT_PROD_LOCAL
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
     AND COD_LOCAL = cCodLocal_in
     AND COD_PROD = cCodProd_in;
     RETURN v_nValFrac;
 END;
/* ********************************************************************** */
FUNCTION INV_F_EXISTS_GUIAS_TRANSF(cGrupoCia_in IN CHAR,
                                       cCodLocal_in IN CHAR,
                                       cNumNota_in  IN CHAR)
  RETURN CHAR
  IS
    cind VARCHAR(20):='N';
    CANT1     NUMBER;
    CANT2     NUMBER;
    codLocOrigen CHAR(3);
  BEGIN

    SELECT COUNT(*) INTO CANT1
    FROM LGT_GUIA_REM X
    WHERE X.COD_GRUPO_CIA = cGrupoCia_in
          AND X.COD_LOCAL = cCodLocal_in
          AND X.NUM_NOTA_ES = cNumNota_in;

    SELECT COUNT(*) INTO CANT2
    FROM LGT_GUIA_REM Y
    WHERE Y.COD_GRUPO_CIA = cGrupoCia_in
          AND Y.COD_LOCAL = cCodLocal_in
          AND Y.NUM_NOTA_ES = cNumNota_in
          AND LENGTH(TRIM(Y.NUM_GUIA_REM))=10
          AND Y.IND_GUIA_IMPRESA='S';

  IF(CANT1>CANT2) THEN--mismas generadas e impresas
    RAISE_APPLICATION_ERROR(-20101,'NO SE PUEDE CONFIRMAR LA TRANSFERENCIA. EXISTEN GUIAS SIN IMPRIMIR. VERIFIQUE!!!');
  ELSIF (CANT1=0) THEN--no generadas
    RAISE_APPLICATION_ERROR(-20102,'LA TRANSFERENCIA NO HA GENERADO GUIAS. VERIFIQUE!!!');
  ELSE

    SELECT A.COD_ORIGEN_NOTA_ES INTO codLocOrigen
    FROM LGT_NOTA_ES_CAB A
    WHERE A.COD_GRUPO_CIA=cGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.NUM_NOTA_ES=cNumNota_in;

    cind:='S' || 'Ã' ||codLocOrigen;
  END IF;

    RETURN cind;
  END;

/* ********************************************************************** */
  FUNCTION TRANSF_F_DESC_LARGA_TRANS(cLlaveTabGral_in IN CHAR,
                                     vDescCorta_in IN VARCHAR2  )
  RETURN VARCHAR2
  IS
   vDescLarga VARCHAR2(500);
  BEGIN
   BEGIN
           SELECT DESC_LARGA INTO vDescLarga
             FROM PBL_TAB_GRAL
            WHERE LLAVE_TAB_GRAL = cLlaveTabGral_in
              AND DESC_CORTA = vDescCorta_in;


     EXCEPTION
      WHEN NO_DATA_FOUND THEN
       vDescLarga := 'N';
    END;
  RETURN vDescLarga;
  END;
/* ********************************************************************** */
 /****************************************************************************************/
   PROCEDURE INV_P_REG_TMP_INIFIN_CREATRANS(cCodGrupoCia_in IN CHAR,
                                         cCod_Local_in   IN CHAR,
                                         cNotaEstCab_in     IN CHAR,
                                         cTmpTipo_in      IN CHAR)
   AS
  BEGIN

      IF cTmpTipo_in='I' THEN
         UPDATE LGT_NOTA_ES_CAB
         SET FEC_INI_CREACION=SYSTIMESTAMP
         WHERE
         COD_GRUPO_CIA=cCodGrupoCia_in AND
         COD_LOCAL=cCod_Local_in AND
         NUM_NOTA_ES=cNotaEstCab_in;

      ELSIF cTmpTipo_in='F' THEN
         UPDATE LGT_NOTA_ES_CAB
         SET FEC_FIN_CREACION=SYSTIMESTAMP
         WHERE
         COD_GRUPO_CIA=cCodGrupoCia_in AND
         COD_LOCAL=cCod_Local_in AND
         NUM_NOTA_ES=cNotaEstCab_in;
      END IF;
  END;
  /****************************************************************************************/
  PROCEDURE INV_P_REG_TMP_INIFIN_CONFTRANS(cCodGrupoCia_in IN CHAR,
                                         cCod_Local_in   IN CHAR,
                                         cNotaEstCab_in     IN CHAR,
                                         cTipoConfirmacion_in  IN CHAR,
                                         cTmpTipo_in      IN CHAR)
   AS
  BEGIN

      IF cTmpTipo_in='I' THEN
         IF cTipoConfirmacion_in = 'N' THEN --SI LA CONFIRMACION ES DE LOCAL ORIGEN A MATRIZ
            UPDATE LGT_NOTA_ES_CAB
            SET FEC_INI_CONFIRMACION=SYSTIMESTAMP
            WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND
            COD_LOCAL=cCod_Local_in AND
            NUM_NOTA_ES=cNotaEstCab_in;
         ELSIF cTipoConfirmacion_in = 'S' THEN --SI LA CONFIRMACION ES DE MATRIZ A LOCAL ORIGEN
            UPDATE LGT_NOTA_ES_CAB
            SET FEC_INI_CONFIRMACION_LINEA=SYSTIMESTAMP
            WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND
            COD_LOCAL=cCod_Local_in AND
            NUM_NOTA_ES=cNotaEstCab_in;
         END IF;

      ELSIF cTmpTipo_in='F' THEN
          IF cTipoConfirmacion_in = 'N' THEN --SI LA CONFIRMACION ES DE LOCAL ORIGEN A MATRIZ
            UPDATE LGT_NOTA_ES_CAB
            SET FEC_FIN_CONFIRMACION=SYSTIMESTAMP
            WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND
            COD_LOCAL=cCod_Local_in AND
            NUM_NOTA_ES=cNotaEstCab_in;
         ELSIF cTipoConfirmacion_in = 'S' THEN --SI LA CONFIRMACION ES DE MATRIZ A LOCAL ORIGEN
            UPDATE LGT_NOTA_ES_CAB
            SET FEC_FIN_CONFIRMACION_LINEA=SYSTIMESTAMP
            WHERE COD_GRUPO_CIA=cCodGrupoCia_in AND
            COD_LOCAL=cCod_Local_in AND
            NUM_NOTA_ES=cNotaEstCab_in;
         END IF;
      END IF;
  END;

  /******************************************************************************************************************/
   PROCEDURE INV_P_REG_TMP_INIFIN_GUIATRANS(cCodGrupoCia_in IN CHAR,
                                         cCod_Local_in   IN CHAR,
                                         cNotaEstCab_in     IN CHAR,
                                         cTmpTipo_in      IN CHAR)
   AS
  BEGIN

      IF cTmpTipo_in='I' THEN
         UPDATE LGT_NOTA_ES_CAB
         SET FEC_INI_CREA_IMPR_GUIAS=SYSTIMESTAMP
         WHERE
         COD_GRUPO_CIA=cCodGrupoCia_in AND
         COD_LOCAL=cCod_Local_in AND
         NUM_NOTA_ES=cNotaEstCab_in;

      ELSIF cTmpTipo_in='F' THEN
         UPDATE LGT_NOTA_ES_CAB
         SET FEC_FIN_CREA_IMPR_GUIAS=SYSTIMESTAMP
         WHERE
         COD_GRUPO_CIA=cCodGrupoCia_in AND
         COD_LOCAL=cCod_Local_in AND
         NUM_NOTA_ES=cNotaEstCab_in;
      END IF;
  END;

    /******************************************************************************************************************/
   PROCEDURE INV_P_REG_TMP_INIFIN_ANUTRANS(cCodGrupoCia_in IN CHAR,
                                         cCod_Local_in   IN CHAR,
                                         cNotaEstCab_in     IN CHAR,
                                         cTmpTipo_in      IN CHAR)
   AS
  BEGIN

      IF cTmpTipo_in='I' THEN
         UPDATE LGT_NOTA_ES_CAB
         SET FEC_INI_ANULACION=SYSTIMESTAMP
         WHERE
         COD_GRUPO_CIA=cCodGrupoCia_in AND
         COD_LOCAL=cCod_Local_in AND
         NUM_NOTA_ES=cNotaEstCab_in;

      ELSIF cTmpTipo_in='F' THEN
         UPDATE LGT_NOTA_ES_CAB
         SET FEC_FIN_ANULACION=SYSTIMESTAMP
         WHERE
         COD_GRUPO_CIA=cCodGrupoCia_in AND
         COD_LOCAL=cCod_Local_in AND
         NUM_NOTA_ES=cNotaEstCab_in;
      END IF;
  END;



/*********************************************************************************************/
FUNCTION INV_F_VALIDA_ASIST_AUDIT(ccodcia_in in char,
                         ccodloc_in in char,
                         csecusuloc_in in char)
RETURN CHAR
IS
CANTIDAD NUMBER;
flag char(1);
BEGIN
  select count(*) into CANTIDAD from pbl_rol_usu a
  where a.cod_grupo_cia=ccodcia_in
  and a.cod_local=ccodloc_in
  and a.sec_usu_local=csecusuloc_in
  and a.cod_rol='036';
  IF CANTIDAD>0 THEN
     flag:='S';
  ELSE
      flag:='N';
  END IF;
  RETURN flag;
END;
/*********************************************************************************************/
PROCEDURE INV_P_ASOCIA_ENT_RECEP (cCodGrupoCia_in IN CHAR,
                                  cCod_Local_in   IN CHAR,
                                  cNro_Recepcion_in IN CHAR,
                                  cNum_Entrega_in IN CHAR,
                                  cUsuario_in IN VARCHAR2)
IS
 v_Num_Nota_Es lgt_guia_rem.num_nota_es%TYPE;
 v_Num_Guia_Rem lgt_guia_rem.num_guia_rem%TYPE;
 v_Sec_Guia_Rem lgt_guia_rem.sec_guia_rem%TYPE;
 v_FechaRecepcion date;--fecha recepcion
 v_FechaCreaEntrega date;--fecha creacion entrega

BEGIN
 SELECT g.num_nota_es, g.num_guia_rem, g.sec_guia_rem
   INTO v_Num_Nota_Es, v_Num_Guia_Rem, v_Sec_Guia_Rem
   FROM lgt_guia_rem g
  WHERE G.NUM_ENTREGA = cNum_Entrega_in;

  --FECHA_RECEPCION
  select M.FEC_RECEP
  into v_FechaRecepcion
  from LGT_RECEP_MERCADERIA M
  where M.NRO_RECEP=cNro_Recepcion_in
  and M.COD_LOCAL=cCod_Local_in
  and M.COD_GRUPO_CIA=cCodGrupoCia_in;

  --FCHA CREACION ENTREGA
  select G.FEC_CREA_GUIA_REM
  into v_FechaCreaEntrega
  from LGT_GUIA_REM G
  where G.NUM_GUIA_REM=v_Num_Guia_Rem
  and G.COD_LOCAL=cCod_Local_in
  and G.COD_GRUPO_CIA=cCodGrupoCia_in;

  DBMS_OUTPUT.put_line('FECHA ENTREGA '||v_FechaCreaEntrega);
  DBMS_OUTPUT.put_line('FECHA RECEPCION '||v_FechaRecepcion);

 IF(v_FechaRecepcion >= v_FechaCreaEntrega) THEN
  INSERT INTO LGT_RECEP_ENTREGA
  (cod_grupo_cia, cod_local, nro_recep, num_nota_es , num_guia_rem ,
   num_entrega , sec_guia_rem, usu_crea_rec_ent, fec_crea_rec_ent)
  VALUES
  (cCodGrupoCia_in, cCod_Local_in, cNro_Recepcion_in, v_Num_Nota_Es, v_Num_Guia_Rem,
   cNum_Entrega_in, v_Sec_Guia_Rem, cUsuario_in, SYSDATE);
 ELSE
   RAISE_APPLICATION_ERROR(-20010,'NO SE PUEDE ASOCIAR LA ENTREGA '|| cNum_Entrega_in ||' PORQUE ' ||chr(10)||'LA FECHA DE RECEPCION :    '||to_char(v_FechaRecepcion,'dd/mm/yyyy hh24:mi:ss')||'     ES MENOR A '||chr(10)||'LA FECHA DE ENTREGA     :    '||to_char(v_FechaCreaEntrega,'dd/mm/yyyy hh24:mi:ss'));
 END IF;

END;
/*********************************************************************************************/
--CREA CABECERA EN LA RECEPCION DE ALMACEN
FUNCTION INV_F_INS_RECEP_CAB (cCodGrupoCia_in IN CHAR,
                                  cCod_Local_in   IN CHAR,
                                  cIdUsu_in      IN CHAR,
                                  cNombTransp    IN CHAR,
                                  cHoraTransp    IN CHAR,
                                  cPlaca         IN CHAR,
                                  nCantBultos    IN NUMBER,
                                  nCantPrecintos IN NUMBER,
                                  cGlosa         IN VARCHAR2 DEFAULT '')

RETURN CHAR
IS
vRecep VARCHAR2(15);
BEGIN

vRecep := PTOVENTA_RECEP_CIEGA_JC.RECEP_P_NEW_RECEPCION(cCodGrupoCia_in,
                                    cCod_Local_in,
                                    cIdUsu_in,
                                    0,
                                    cNombTransp ,
                                    cHoraTransp ,
                                    cPlaca ,
                                    nCantBultos ,
                                    nCantPrecintos,
                                    cGlosa );


  UPDATE LGT_RECEP_MERCADERIA M
     SET M.IND_AFEC_RECEP_CIEGA = 'N'
   WHERE M.COD_GRUPO_CIA = cCodGrupoCia_in
     AND M.COD_LOCAL = cCod_Local_in
     AND M.NRO_RECEP = vRecep;

RETURN vRecep;
 EXCEPTION
  WHEN OTHERS THEN
   RAISE_APPLICATION_ERROR(-20020,'¡Error al grabar Cabecera Recepción!');

END;

/*********************************************************************************************/
PROCEDURE INV_P_ACT_CANT_GUIAS (cCodGrupoCia_in IN CHAR,
                                  cCod_Local_in   IN CHAR,
                                  cNro_Recepcion_in IN CHAR,
                                  cIdUsu_in      IN CHAR,
                                  cCantGuias_in IN NUMBER)
IS
 v_ip VARCHAR2(20);
 BEGIN
 SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
        FROM DUAL;

  UPDATE LGT_RECEP_MERCADERIA G
  SET G.CANT_GUIAS = cCantGuias_in, G.USU_MOD_RECEP = cIdUsu_in, g.fec_mod_recep = SYSDATE,
      G.IP_MOD_RECEP =  v_ip
  WHERE G.COD_GRUPO_CIA = cCodGrupoCia_in
  AND G.COD_LOCAL = cCod_Local_in
  AND G.NRO_RECEP = cNro_Recepcion_in;

  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20021,'Error No existe la recepción.');
  WHEN OTHERS THEN
    raise_application_error(-20022,'Error');
 END;

/*********************************************************************************************/

 FUNCTION INV_GET_IND_NUEVA_TRANSF
 RETURN CHAR
 IS
  vRpta CHAR(1) := 'N';
 BEGIN
  BEGIN
    SELECT l.llave_tab_gral INTO vRpta
      FROM pbl_tab_gral l
     WHERE l.id_tab_gral = 348;
  EXCEPTION
  WHEN no_data_found THEN
   vRpta := 'N';
  END;
  RETURN vRpta;
 END;

/*********************************************************************************************/

 FUNCTION INV_GET_PIDE_LOTE_TRANSF(cCodGrupoCia_in CHAR)
 RETURN CHAR
 IS
  vRpta CHAR(1) := 'N';
 BEGIN
  BEGIN
    SELECT l.llave_tab_gral INTO vRpta
      FROM pbl_tab_gral l
     WHERE l.id_tab_gral = 362;
       -- AND l.cod_grupo_cia = cCodGrupoCia_in;
  EXCEPTION
  WHEN no_data_found THEN
   vRpta := 'E';
  END;
  RETURN vRpta;
 END;

/*********************************************************************************************/

 FUNCTION INV_GET_PID_FEC_VTO_TRANSF(cCodGrupoCia_in CHAR)
 RETURN CHAR
 IS
  vRpta CHAR(1) := 'N';
 BEGIN
  BEGIN
    SELECT l.llave_tab_gral INTO vRpta
      FROM pbl_tab_gral l
     WHERE l.id_tab_gral = 363;
       --AND l.cod_grupo_cia = cCodGrupoCia_in;
  EXCEPTION
  WHEN no_data_found THEN
   vRpta := 'E';
  END;
  RETURN vRpta;
 END;

 /* *********************************************************************************** */
FUNCTION INV_F_ACTIVO_PROD(cCodGrupoCia_in IN CHAR,
                             cCod_Local_in   IN CHAR,
                             cCodProd_in     IN VARCHAR2)
RETURN CHAR
IS
vActivo char(1) := 'S';
BEGIN

    select
    (   case
        when count(*)>0 then 'A'
        else 'I'
        end)
    into vActivo
    from lgt_prod p ,lgt_prod_local l
    where l.cod_prod = p.cod_prod
    and l.cod_prod=cCodProd_in
    and l.cod_grupo_cia=cCodGrupoCia_in
    and l.cod_local=cCod_Local_in
    and l.est_prod_loc='A'
    and p.est_prod='A';

RETURN vActivo;
END;

/*******************************************************************************************/

FUNCTION INV_F_CANTIDAD_ITEMS_GUIA(cCodGrupoCia_in IN CHAR,cCod_Local_in IN CHAR, cCodProd_in IN CHAR)
RETURN CHAR
IS
vActivo char(1) := 'S';
BEGIN

    select
    (   case
        when count(*)>0 then 'A'
        else 'I'
        end)
    into vActivo
    from lgt_prod p ,lgt_prod_local l
    where l.cod_prod = p.cod_prod
    and l.cod_prod=cCodProd_in
    and l.cod_grupo_cia=cCodGrupoCia_in
    and l.cod_local=cCod_Local_in
    and l.est_prod_loc='A'
    and p.est_prod='A';
RETURN vActivo;
END;

/**********************************************************************************************/

FUNCTION INV_F_GET_FECHAVTO_LOTE (cCodGrupoCia_in    IN CHAR,
                                  cCodLocal_in       IN CHAR,
                                  cCodProd_in        IN CHAR,
                                  cNumlote_in        IN CHAR)
RETURN varchar2
IS
  vFechaVto varchar2(20);
BEGIN

   vFechaVto:='';
BEGIN
   SELECT TO_CHAR(FEC_VENC_LOTE,'dd/MM/yyyy') INTO vFechaVto
   FROM LGT_MAE_LOTE_PROD L
   WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in AND
         L.COD_PROD = cCodProd_in AND
         L.NUM_LOTE_PROD=cNumlote_in;

EXCEPTION
  WHEN no_data_found THEN
       vFechaVto:='';
  END;

   RETURN vFechaVto;
END;



/******************************************************************************/
/*Cesar Huanes-Busca el Ruc y la Direccion de la tabla LGT_TRANSP_CIEGA*/
FUNCTION INV_GET_DATOS_TRANSP_CIEGA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodTransp_in IN CHAR)

  Return FarmaCursor
  IS
  curTranspRecep  FarmaCursor;
  BEGIN
  OPEN curTranspRecep  FOR
  SELECT  NVL(COD_TRANSP,' ') ||'Ã'|| NVL(RUC_TRANSP,' ') ||'Ã'|| NVL(NOM_TRANSP,' ') ||'Ã'|| NVL(DIREC_TRANSP,' ')
  FROM LGT_TRANSP_CIEGA
  WHERE COD_TRANSP=cCodTransp_in
  AND ESTADO='A';

  RETURN curTranspRecep;

  END;

   --Descripcion: Lista datos de nombre del local y  direccion del local.
  --Fecha       Usuario    Comentario
  --16/12/2013  CHUANES     Creación
   FUNCTION INV_GET_DATOS_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)

  Return FarmaCursor
  IS
  curLocal FarmaCursor;
  BEGIN
  OPEN curLocal  FOR
  SELECT NVL(DESC_CORTA_LOCAL,' ' )|| 'Ã' ||
         NVL(DESC_LOCAL,' ') || 'Ã' ||
         NVL(DIREC_LOCAL,' ')
          FROM  PBL_LOCAL  L
          WHERE L.COD_GRUPO_CIA=cCodGrupoCia_in AND L.COD_LOCAL=cCodLocal_in;

  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20021,'Error No existe El Local.');
  WHEN OTHERS THEN
    raise_application_error(-20022,'Error');

  RETURN curLocal;

  END;

  /*Generar Guia de Remision--Cesar Huanes*/
 PROCEDURE INV_GENERA_GUIA_REMISION(cGrupoCia_in   IN CHAR, cCodLocal_in IN CHAR,cIdUsu_in IN CHAR,cNumNota_in IN CHAR)
 AS

 v_NumNota LGT_GUIA_REM.NUM_NOTA_ES%TYPE;
 v_NumGuia LGT_GUIA_REM.NUM_GUIA_REM%TYPE;
 v_secGuia LGT_GUIA_REM.SEC_GUIA_REM%TYPE;

BEGIN

    INSERT INTO LGT_GUIA_REM(cod_grupo_cia,
                        cod_local,
                        num_nota_es,
                        sec_guia_rem,
                        num_guia_rem,
                        fec_crea_guia_rem,
                        usu_crea_guia_rem)
    VALUES(cGrupoCia_in,cCodLocal_in,cNumNota_in,1,SUBSTR(cNumNota_in,-5)||'-'||1,SYSDATE, cIdUsu_in);


  END;
 /*Actualiza texto de Impresion--Cesar Huanes*/

 PROCEDURE ACTUALIZA_TEXTO_IMPR(cGrupoCia_in   IN CHAR, cCodLocal_in IN CHAR ,cNumNota IN CHAR ,cTexto_Impr IN VARCHAR)
 AS
 BEGIN
 UPDATE LGT_NOTA_ES_CAB
 SET TEXTO_IMP=cTexto_Impr
 WHERE NUM_NOTA_ES=cNumNota
 AND   COD_GRUPO_CIA=cGrupoCia_in
 AND COD_LOCAL=cCodLocal_in;

 EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20021,'Error No existe la recepción.');
  WHEN OTHERS THEN
    raise_application_error(-20022,'Error');


 END;


  FUNCTION GET_TEXTO_IMPR (cCodGrupoCia_in    IN CHAR,
                    cCodLocal_in       IN CHAR,
                    cNumNota_in        IN CHAR)
  RETURN varchar2
  IS
    vTextoImpr lgt_nota_es_Cab.TEXTO_IMP%TYPE;
    vchar VARCHAR2(3000);
    tvchar VARCHAR2(5000);
    cant number := 100;
    cantM number;
    co number :=0;
    cLinea VARCHAR2(3000);
  BEGIN
    
    SELECT trim(REPLACE(TEXTO_IMP, CHR(10), ' '))
      INTO cLinea
      FROM lgt_nota_es_Cab
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND NUM_NOTA_ES = cNumNota_in;
  
    vchar := cLinea;
    IF LENGTH(cLinea)>cant THEN
      vchar := TRIM(VCHAR);
      cantM := trunc((length(vchar)/cant));
      while (co<cantM) loop
            tvchar := tvchar || substr(vchar,((co*(cant))+1),cant)||'Ã';
            co := co+1;
      end loop;
      tvchar := tvchar || substr(vchar,((co*cant)+1))||'Ã';
      RETURN tvchar;
    ELSE
      RETURN vchar;
    END IF;
/*
    SELECT REPLACE(TEXTO_IMP,CHR(10),'Ã')
      INTO vTextoImpr
    FROM lgt_nota_es_Cab
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND NUM_NOTA_ES = cNumNota_in;

    RETURN vTextoImpr;*/
  END;

 /*Lista Guias de Remision que no mueven stock--Cesar Huanes*/
 FUNCTION LISTA_GUIA_NO_MUEVE_STOCK(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in IN CHAR)

RETURN FarmaCursor
  IS
    curGuia FarmaCursor;
BEGIN
OPEN curGuia FOR
SELECT
    ( NVL(A.NUM_NOTA_ES,' ') || 'Ã' ||
     NVL(A.NUM_GUIA_REM,' ') || 'Ã' ||
     TO_CHAR(A.FEC_CREA_GUIA_REM,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
     NVL(A.USU_CREA_GUIA_REM,' ') || 'Ã' ||
   DECODE(B.EST_NOTA_ES_CAB,'N','ANULADO'
                                             ,'C','CONFIRMADO'
                                             ,'M','MATRIZ'
                                             ,'L','LOCAL'
                                             ,'X','ERROR'
                                             ,'R','RECHAZADO'
                                             ,'A','ACEPTADO'
                                             ,'P','POR IMPRIMIR'
                                             ,' ') || 'Ã' ||
  B.IND_NOTA_IMPRESA
     ) RESULTADO
FROM
      LGT_GUIA_REM A,
      LGT_NOTA_ES_CAB B
WHERE
      A.NUM_NOTA_ES=B.NUM_NOTA_ES
AND
      A.COD_GRUPO_CIA=cCodGrupoCia_in
AND
      A.COD_LOCAL= cCodLocal_in
AND
      --B.TEXTO_IMP IS NOT NULL
    B.TIP_NOTA_ES = g_cTipoNotaSalida
    AND B.TIP_ORIGEN_NOTA_ES = g_cTipoOrigenGuiaSalida

    ;

    --ORDER BY A.FEC_CREA_GUIA_REM DESC;

RETURN   curGuia;

END;

-- DATOS DE TRANSPORTISTA PARA TRANSFERENCIAS DELIVERY
-- KMONCADA
  FUNCTION INV_GET_DATOS_TRANS_DELIVERY(cCodGrupoCia_in IN CHAR,cCodLocal_in   IN CHAR,cCodDestino_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTransp FarmaCursor;
    COD_TRANSPORTE LGT_TRANSPORTISTA.COD_TRANSPORTISTA%TYPE;
    v_vTransp VARCHAR2(550);
  BEGIN

    BEGIN
    SELECT A.LLAVE_TAB_GRAL
    INTO COD_TRANSPORTE
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL=521;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        COD_TRANSPORTE := NULL;
    END;

    IF COD_TRANSPORTE IS NOT NULL THEN
      SELECT  NVL(A.NOM_TRANSPORTISTA, ' ') || 'Ã' || NVL(A.RUC_TRANSPORTISTA, ' ') || 'Ã' ||
       NVL(A.DIREC_TRANSPORTISTA, ' ') || 'Ã' || NVL(A.NUM_PLACA, ' ')
       INTO v_vTransp
       FROM LGT_TRANSPORTISTA A
       WHERE A.COD_TRANSPORTISTA = COD_TRANSPORTE;
    ELSE
      SELECT ' '  || 'Ã' || ' '  || 'Ã' || ' ' || 'Ã' || ' '
      INTO v_vTransp
      FROM DUAL;
    END IF;

    OPEN curTransp FOR
    SELECT C.NOM_CIA || 'Ã' ||
        C.NUM_RUC_CIA || 'Ã' ||
        NVL(D.DIREC_LOCAL,' ')  || 'Ã' ||
        v_vTransp
    FROM PBL_LOCAL D, PBL_CIA C
    WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
          AND D.COD_LOCAL = cCodDestino_in
          AND C.COD_CIA = D.COD_CIA;

    RETURN curTransp;
  END;

  --     LISTA DE PRODUCTOS CON LABORATORIO SUMINISTROS (COD_GRUPO_REP = '010')
  -- Fecha        Usuario        Comentario
  -- 19.09.2014   RHERRERA      Creación
 FUNCTION INV_LISTA_INSUMOS (cCodGrupoCia_in IN CHAR ,
                               cCodLocal_in    IN CHAR  )
 RETURN FarmaCursor

 IS
 curLabs FarmaCursor;
 BEGIN
    OPEN curLabs FOR

              SELECT PL.COD_PROD               || 'Ã' ||
                NVL(P.DESC_PROD,' ')        || 'Ã' ||
                NVL(P.DESC_UNID_PRESENT,' ')         || 'Ã' ||
                NVL(L.NOM_LAB,' ')               || 'Ã' ||
                DECODE(TRIM(P.DESC_UNID_PRESENT),TRIM(PL.UNID_VTA),' ',NVL(TRIM(PL.UNID_VTA),' ')) || 'Ã' ||
                nvl(trunc(stk_fisico/pl.val_frac_local),0)|| 'Ã' ||
                nvl(MOD(stk_fisico,pl.val_frac_local),0) || 'Ã' ||
                pl.val_frac_local || 'Ã' ||
                DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S')|| 'Ã' ||
                P.EST_PROD --JCORTEZ 30.03.2010 solicitud de RCASTRO
                || 'Ã' ||
                NVL((SELECT TO_CHAR(MAX(X.FEC_CREA_KARDEX),'DD/MM/YYYY')
                            FROM LGT_KARDEX X
                            WHERE X.COD_PROD = P.COD_PROD
                            AND   X.USU_CREA_KARDEX IN (
                                  SELECT L.LOGIN_USU
                                  FROM   PBL_USU_LOCAL L
                                  WHERE  L.EST_USU = 'A'
                            )
                            ),' ')
         FROM   LGT_PROD_LOCAL PL,
                LGT_PROD P,
                LGT_LAB L,
                LGT_PROD_VIRTUAL PR_VRT
         WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    PL.COD_LOCAL     = cCodLocal_in
         AND    PL.COD_GRUPO_CIA = P.COD_GRUPO_CIA
         AND    PL.COD_PROD      = P.COD_PROD
         AND    P.COD_LAB       = L.COD_LAB
         AND    P.EST_PROD      = 'A'
         AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
         AND    P.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
         AND    P.COD_PROD = PR_VRT.COD_PROD(+)--;
         AND    (P.COD_GRUPO_REP = COD_GRU_REP_INSUMO 
                -- AGREGADO PARA TICOS 27.10.2014
                OR 
                (P.COD_GRUPO_REP = '009' AND P.IND_TIPO_CONSUMO = 'C')
                )
         
         ; -- 05.08.2014


    RETURN curLabs;
 END;

 FUNCTION F_OBTIENE_MOT_INSUMO(cCodMotKardex_in CHAR)
          RETURN CHAR

          IS
          cmotivoInsumo CHAR(3);
   BEGIN

          IF cCodMotKardex_in = '001' THEN
              cmotivoInsumo:='522';
              ELSE
              cmotivoInsumo:=cCodMotKardex_in;
            END IF;


          RETURN cmotivoInsumo;

   END;

END;
/
