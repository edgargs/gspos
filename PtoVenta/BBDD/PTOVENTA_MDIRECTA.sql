--------------------------------------------------------
--  DDL for Package PTOVENTA_MDIRECTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_MDIRECTA" IS

  -- Author  : LRUIZ
  -- Created : 15/06/2013 12:29:20 p.m.
  -- Purpose : Administrar las interacciones entre el sistema PTOVENTA y Recepción de mercadería directa

  --Descripcion: Guarda la información sobre la recepción de mercaderia directa y su devolución
  --Fecha        Usuario		    Comentario
  --10/06/2013   LRUIZ          Creación

  TYPE FarmaCursor IS REF CURSOR;

  g_cTipoNotaEntrada LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE;
  g_cTipoNotaSalida LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE;

  g_vCodPtoVenta    PBL_TAB_GRAL.COD_APL%TYPE;
  g_vMotivoAjuste   PBL_TAB_GRAL.COD_TAB_GRAL%TYPE;


  --==============================================================================================
  --Lista las ordenes de compra segun fecha de registro.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación

  FUNCTION LISTA_ORDEN_COMPRA_CAB(cCodGrupoCia_in IN CHAR,
                                  cCodCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFechaIni_in IN VARCHAR2,vFechaFin_in IN VARCHAR2)
    RETURN FarmaCursor;

  --==============================================================================================
  --Obtiene la cabera de la orden de compra.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación

  FUNCTION LISTA_CAB_ORDEN_COMPRA(vCod_OC_in IN VARCHAR2)
    RETURN FarmaCursor;

  --==============================================================================================
  --lISTA PRODUCTOS POR CODIGO DE ORDEN DE COMPRA,GRUPO CIA,COD CIA, Y COD LOCAL
  --Fecha        Usuario		    Comentario
  --19.02.2014   CHUANES         Modificacion

  FUNCTION DETALLE_ORDEN_COMPRA(vCodGrupoCia IN CHAR,vCodCia IN CHAR,vCodLocal IN CHAR ,vCodOrdenComp_in IN VARCHAR2)
    RETURN FarmaCursor;

  --==============================================================================================
  --Lista los productos por ordenes de compra.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación

  FUNCTION LISTAR_DETALLE_ORDEN_COMPRA(vCodProducto_in IN VARCHAR2)
    RETURN FarmaCursor;

  --==============================================================================================
  --Lista los productos por ordenes de compra.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación

  FUNCTION DETALLE_ORDEN_COMPRA_RECEP(vOrdenComp_in IN VARCHAR2)
    RETURN FarmaCursor;

  --==============================================================================================
  --Lista los documentos recepcionados por ordenes de compra.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación
  --16.12.2013   ERIOS              Se agrega parametro nSecCab_in
  FUNCTION LISTA_CAB_ORDEN_COMPRA_RECEP(cCodGrupoCia_in IN CHAR,
                                cCodCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
								cCodOC_in IN CHAR,
								nSecCab_in IN NUMBER)
    RETURN FarmaCursor;

  --==============================================================================================
  --Guarda la cabecera del docuemnto ingresado en la tabla lgt_oc_cab_recep, segun orden de compra.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación

  FUNCTION MDIR_GRABA_OC_CAB_RECEP(cCodGrupoCia_in  IN CHAR,
            cCod_Cia_in   IN CHAR, cCod_Local_in   IN CHAR, vId_User_in   IN VARCHAR2,
            cNumer_Ord_Comp_in  IN CHAR, cfecha_in  IN DATE, cId_Docum_in  IN CHAR,
            cSerie_Docm_in  IN CHAR, cNumer_Docm_in  IN CHAR, nCant_Item_in  IN CHAR,
            cCod_Prov_in   IN CHAR, nImport_Total_in  IN CHAR, nImport_Parc_in  IN CHAR,
            nRedondeo_in  IN CHAR)
  RETURN CHAR;

  --==============================================================================================
  --Guarda los productos ingresado en la tabla lgt_oc_det_recep, segun orden de compra.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación

  PROCEDURE MDIR_GRABA_OC_DET_RECEP(cCodGrupoCia_in       IN CHAR,
                                cCod_Cia_in              IN CHAR,
                                cCod_Local_in            IN CHAR,
                                vId_User_in              IN VARCHAR2,
                                cNumer_Ord_Comp_in       IN CHAR,
                                cCod_Prod_in             IN CHAR,
                                nCant_Solict_in          IN CHAR,
                                nCant_Recep_in           IN CHAR,
                                nPrecio_Unit_in          IN CHAR,
                                nIGV_in                  IN CHAR,
                                nCant_Recep_Total_in     IN CHAR,
                                nSec_Det_in              IN CHAR,
                                cId_Docum_in              IN CHAR,
                                cSerie_Docm_in            IN CHAR,
                                cNumer_Docm_in            IN CHAR,
                                nSecOCRcep                IN NUMBER
                                );

  --==============================================================================================
  --Lsita los productos de la orden de compra para su ingreso de recepción.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación
  --16.12.2013   ERIOS              Se agrega el parametro nSecCab_in
  FUNCTION LISTAR_DET_ORDEN_COMPRA_RECEP (cCodGrupoCia_in IN CHAR,
										  cCodCia_in   IN CHAR,
										  cCodLocal_in   IN CHAR,
										  cCod_OC_in   IN CHAR,
                                          nSecCab_in IN NUMBER)
  RETURN FarmaCursor;

  --==============================================================================================
  --Verifica si el documento se encuentra ANULADO(N) o activo(A) retorna una cadena('TRUE' o 'FALSE').
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación

  FUNCTION LISTAR_NUM_GUIA_ANULAR_RECEP (vCodGrupoCia_in    IN CHAR,
                                         vCodLocal_in       IN CHAR,
                                         vNumerGuia_in      IN CHAR,
                                         vIdeDocumento_in   IN CHAR,
                                         vNumeroDocument_in IN CHAR)
  RETURN CHAR;

  --==============================================================================================
  --Canbia el estado(A) de LGT_OC_CAB_RECEP, LGT_OC_DET_RECEP, LGT_OC_DET a (N) y resta las cantidades anuladas.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación
  --16.12.2013   ERIOS              Se agrega el parametro nSecCab_in
  PROCEDURE ANULAR_INGRESO_RECEPCION(cCodGrupoCia_in IN CHAR,
                                cCodCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
								cCodOC_in IN CHAR,
								nSecCab_in IN NUMBER);

  --==============================================================================================
  --Lista los documentos recepcionados por ordenes de compra.
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación

  FUNCTION LISTAR_DOCUMEN_RECEP(cCodGrupoCia_in IN CHAR, cCodCia_in in CHAR, cCodLocal_in IN CHAR, cCodOrdComp_in IN CHAR, cCodProv_in IN CHAR)
  RETURN FarmaCursor;

  --==============================================================================================
  --Verifica que la cantida de productos recepcionados, sea igual a la cantidad de productos solicitados.
  --Fecha        Usuario		    Comentario
  --17/07/2013   LRUIZ          Creación

  FUNCTION CIERRE_ORD_COMPRA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumerGuia_in IN CHAR)
  RETURN CHAR;

  FUNCTION INV_LISTA_DEVOLUCION(cGrupoCia_in IN CHAR, cCia_in IN CHAR,cCodLocal_in IN CHAR,vFiltro_in   IN CHAR,cTipoOrigen  IN CHAR)
  RETURN FarmaCursor;

  FUNCTION INV_GET_MOTIVO_DEVOLUCION
  RETURN FarmaCursor;

  FUNCTION INV_GET_ORDEN_COMPRA(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cProv_in     IN CHAR)
  RETURN FarmaCursor;

  FUNCTION OBTENER_DETALLE_PRODUCTOS_OC(cCodOrdenCompr IN CHAR,cBuscar IN VARCHAR2)
  RETURN FarmaCursor;

  PROCEDURE INV_CONFIRMAR_DEVOL(cGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumNotaEs_in IN CHAR, vIdUsu_in IN VARCHAR2);

 --==============================================================================================
  --INVOCA AL PROCEDIMIENTO DE ACTUALIZACIÓN DE LA ORDEN DE COMPRA EN COMIS
  --Fecha        Usuario		    Comentario
  --26/10/2013   CVILCA         Creación

  FUNCTION OBTENER_PRODUCTOS_POR_NOTA(cCodGrupoCia_in IN CHAR,
                                      cCodCia_in      IN CHAR,
                                      cCod_Local_in   IN CHAR,
                                      cNumOrdCom_in   IN VARCHAR2,
                                      vNumNota_in     IN VARCHAR2)
   RETURN FarmaCursor;

  --==============================================================================================
  --FUNCION QUE RETORNA EL VALOR INNERPACK DE UN PRODUCTO (INDICA EL NUMERO DE UNIDADES POR PAQUETE)
  --Fecha        Usuario		    Comentario
  --10/12/2013   GFONSECA         Creación
  FUNCTION GET_PROD_INNER_PACK(cCodGrupoCia_in IN CHAR,
                               cCodProd_in      IN CHAR)
  RETURN NUMBER;

  --==============================================================================================
  --Lista el Maestro de productos  en la devolucíon de productos
  --Fecha        Usuario		    Comentario
  --23/12/2013   CHUANES        Creación

  FUNCTION LISTA_MAESTRO_PRODUCTOS(cCodGrupoCia_in IN CHAR,cCod_Local_in IN CHAR)
  Return FarmaCursor;
   --==============================================================================================
  --Busca un producto por descripcíon
  --Fecha        Usuario		    Comentario
  --23/12/2013   CHUANES        Creación

  FUNCTION BUSCA_PRODUCTO_DESC(cCodGrupoCia_in   IN CHAR,cCod_Local_in IN CHAR, vDescripcion  IN VARCHAR2)
  RETURN FarmaCursor;

   --==============================================================================================
  --Busca  un producto por codigo de barra
  --Fecha        Usuario		    Comentario
  --23/12/2013   CHUANES        Creación

  FUNCTION F_GET_PRODUC_COD_BARRA(cCodGrupoCia IN CHAR, cCodLocal IN CHAR,  cCodBarra IN CHAR )

  RETURN FarmaCursor;

  --==============================================================================================
  --Graba la tabla LGT_OC_CAB_RECEP cuando se realiza una devolucíon
  --Fecha        Usuario		    Comentario
  --30/12/2013   CHUANES         Creación

  PROCEDURE INV_GRABA_DEVOLUCION(cGrupoCia_in   IN CHAR,  cCod_Cia_in  IN CHAR, cCod_Local_in
                                 IN CHAR,cCodOC_in IN CHAR ,cCod_Prov_in IN CHAR,cCant_Item_in IN CHAR,cIdUsu_in IN CHAR,cNum_Nota_in IN CHAR);

  --==============================================================================================
  --Determina las guias que fueron enviadas y/o grabadas en el lecacy
  --Fecha        Usuario		    Comentario
  --02/01/2014   CHUANES         Creación

  FUNCTION GET_GUIA_NOENVIO_LEGACY(cGrupoCia_in   IN CHAR,  cCod_Cia_in  IN CHAR, cCod_Local_in IN CHAR,cNum_Nota IN CHAR)
  RETURN FarmaCursor;

  --==============================================================================================
  -- Lista el detalle de una guía de remisíon segun logica requerida en java
  --Fecha        Usuario		    Comentario
  --02/01/2014   CHUANES         Creación

  FUNCTION GET_DET_GUIA_LEGACY(cGrupoCia_in   IN CHAR,  cCod_Cia_in  IN CHAR, cCod_Local_in IN CHAR,cNum_Nota IN CHAR,cSec_Guia IN CHAR)
  RETURN FarmaCursor;

  --==============================================================================================
  -- ACTUALIZA LGT_GUIA_REM MEDIANTE UN INDICADOR SI GRABO EN EL LEGACY
  --Fecha        Usuario		    Comentario
  --02/01/2014   CHUANES         Creación
   PROCEDURE ACTUALIZAR_INDICADOR(cGrupoCia_in   IN CHAR,  cCod_Cia_in  IN CHAR, cCod_Local_in IN CHAR,cNum_nota IN CHAR,cSec_Guia IN CHAR);
  --==============================================================================================
  -- ACTUALIZA LGT_OC_CAB_RECEP EN EL CAMPO NUM_NOTA_ES
  --Fecha        Usuario		    Comentario
  --24/01/2014   CHUANES         Creación

   PROCEDURE ACTUALIZA_NUMNOTA(cCodGrupoCia IN CHAR, cCodCia_in IN CHAR,cCodLocal  IN CHAR,cNumNota IN CHAR,cCodOC IN CHAR,nSecOCRecep IN NUMBER);
   --==============================================================================================
  -- VALIDA EL STOCK DISPONIBLE CUANDO SE REALIZA UNA TRANSFERENCIA O UNA DEVOLUCIÓN
  --Fecha        Usuario		    Comentario
  --05/02/2014   CHUANES         Creación

   FUNCTION GET_VALIDA_STOCK_PRODUCTO(cCodGrupoCia_in IN CHAR, cCod_Local_in IN CHAR,cCodProd_in IN CHAR,cCantTrans IN CHAR)
   RETURN CHAR;

   FUNCTION VALIDA_NUM_AJUSTE(cCodGrupoCia  IN CHAR,cCodLocal IN CHAR,cCodProd IN CHAR,cNumComPago IN CHAR,cCodMotkardex IN CHAR,cTipDockardex IN CHAR)
  RETURN CHAR;
     --==============================================================================================
  -- Obtenemos el Indicador de la OC comparando las cantidades Pedidas y Recepcionadas
  --Fecha        Usuario		    Comentario
  --03/03/2014   RHERRERA         Creación
  FUNCTION IND_CIERRE_ORD_COMPRA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumerOC_in IN CHAR)
  RETURN CHAR  ;
  
  
  -- OBTENEMOS LA CANTIDAD EXCEDENTE PERMITIDA DE INGRESAR EN UNA OC, CONSIDERANDO EL VALOR INNER
  -- PACK DEL PRODUCTO
  -- Fecha        Usuario      Comentario
  -- 15.07.2014   kmoncada     Creacion
  FUNCTION GET_CANT_ADIC_PROD_X_PROVEEDOR(cCodProveedor_in IN CHAR, 
                                          cCodGrupoCia_in IN CHAR,
                                          cCodProducto_in IN CHAR)
  RETURN NUMBER;
  
  -- Listar detalle de orden de compra de la cual se espera devolver algunos productos por haber ingresado previamente sobrantes.
  -- Fecha        Usuario      Comentario
  -- 19/08/2014   ASOSA     Creacion
   FUNCTION GET_DET_OC_SOBRANTE(vCodGrupoCia IN CHAR,
                                                                               vCodCia IN CHAR,
                                                                               vCodLocal IN CHAR ,
                                                                               vCodOrdenComp_in IN VARCHAR2)
      RETURN FarmaCursor;

  -- Obtiene el codigo de proveedor de una ordern de compra.
  -- Fecha        Usuario      Comentario
  -- 25/08/2014   ASOSA     Creacion
  function  GET_PROV_OC(vCodGrupoCia IN CHAR,
                                                     vCodCia IN CHAR,
                                                     vCodLocal IN CHAR ,
                                                     vCodOrdenComp_in IN VARCHAR2) 
  return varchar2;
  
    -- Obtiene parametro general string
  -- Fecha        Usuario      Comentario
  -- 25/08/2014   ASOSA     Creacion
  function  GET_VAL_GEN_STRING(nCodigo in number)
  return varchar2;
  
      -- Obtiene parametro general string (dos valores string)
  -- Fecha        Usuario      Comentario
  -- 25/08/2014   ASOSA     Creacion
  function  GET_VAL_GEN_TWO_STRG(nCodigo in number)
  return varchar2;
  
      -- Obtiene parametro general integer
  -- Fecha        Usuario      Comentario
  -- 25/08/2014   ASOSA     Creacion
  function  GET_VAL_GEN_INTEGER(nCodigo in number)
  return varchar2;
  
  -- Obtiene las ordenes de compra pendientes de regularizar por exceso de mercaderia.
  -- Fecha        Usuario      Comentario
  -- 26/08/2014   ASOSA     Creacion
   FUNCTION GET_ORD_COMP_PEND_REG(vCodGrupoCia IN CHAR,
                                                                               vCodCia IN CHAR,
                                                                               vCodLocal IN CHAR)
      RETURN FarmaCursor;
      
        -- Determina si una OC ya fue regularizada..
  -- Fecha        Usuario      Comentario
  -- 26/08/2014   ASOSA     Creacion
      function  GET_IND_OC_REGULARIZADO(vCodGrupoCia IN CHAR,
                                                     vCodCia IN CHAR,
                                                     vCodLocal IN CHAR ,
                                                     vCodOrdenComp_in IN VARCHAR2) 
  return varchar2;

   END PTOVENTA_MDIRECTA;

/
