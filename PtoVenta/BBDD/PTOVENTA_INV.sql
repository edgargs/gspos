--------------------------------------------------------
--  DDL for Package PTOVENTA_INV
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_INV" AS

  TYPE FarmaCursor IS REF CURSOR;

  COD_NUMERA_SEC_MOV_AJUSTE_KARD            PBL_NUMERA.COD_NUMERA%TYPE := '017';
  COD_NUMERA_SEC_KARDEX                       PBL_NUMERA.COD_NUMERA%TYPE := '016';

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
                                        vValFrac_in IN NUMBER,vUsu_in IN VARCHAR2);

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
                                        ,cCodMotTransInterno_in CHAR) RETURN CHAR;

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

   FUNCTION INV_LISTA_ITEMS_AK(cCodGrupoCia_in IN CHAR ,
  		   					  cCodLocal_in    IN CHAR
							  )
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
  end;

/
