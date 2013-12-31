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
  --Obtiene los productos de la orden de compra seleccionada. 
  --Fecha        Usuario		    Comentario
  --15/06/2013   LRUIZ          Creación
  
  FUNCTION DETALLE_ORDEN_COMPRA(vCodOrdenComp_in IN VARCHAR2)
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
                                cNumer_Docm_in            IN CHAR);
                             
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
  
                                            
END PTOVENTA_MDIRECTA;

/
