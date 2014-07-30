--------------------------------------------------------
--  DDL for Package PTOVENTA_RCM
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RCM" AS

    TYPE FarmaCursor IS REF CURSOR;

    CONS_ACTIVO CHAR(1) := 'A';

    --Descripcion: Lista las formas farmaceuticas
    --Fecha       Usuario   Comentario
    --15/04/2013  ERIOS     Creacion
    FUNCTION GET_FORMA_FARMAC
    RETURN FarmaCursor;

    --Descripcion: Lista los insumos
    --Fecha       Usuario   Comentario
    --26/04/2013  Luigy T.  Creacion
    FUNCTION LISTA_INSUMOS
    RETURN FarmaCursor;

    --Descripcion: Obtiene informacion del insumo
    --Fecha       Usuario   Comentario
    --29/04/2013  Luigy T.  Creacion

    FUNCTION GET_INF_INSUMO (cCod_Insumo_in            IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Retorna correlativo del recetario
    --Fecha        Usuario		    Comentario
    --30/04/2013   Luigy Terrazos Creación
    --FUNCTION RCD_CORRE_NUM_RECETARIO(cCodGrupoCia_in          IN CHAR,
        --                             cCod_Cia_in              IN CHAR,
      --                               cCod_Local_in            IN CHAR)
    --RETURN CHAR;

    --Descripcion: Guarda la cabecera del recetario.
    --Fecha        Usuario		    Comentario
    --30/04/2013   Luigy Terrazos Creación
    FUNCTION RCM_GRAB_CAB(cCodGrupoCia_in          IN CHAR,
                          cCod_Cia_in              IN CHAR,
                          cCod_Local_in            IN CHAR,
                          cInd_Esteril_in          IN CHAR,
                          cCod_Forma_Farmac_in     IN CHAR,
                          nCant_Contenido_in       IN NUMBER,
                          nCant_Preparados_in      IN NUMBER,
                          cFec_Recetario_in        IN CHAR,
                          cCod_Paciente_in         IN VARCHAR2,
                          cCod_Medico_in           IN CHAR,
                          cFec_Entrega_in          IN CHAR,
                          cHora_Entrega_in         IN VARCHAR2,
                          cCod_Local_Entrega_in    IN CHAR,
                          cObs_Recetario_in        IN VARCHAR2,
                          nVal_Bruto_Recetario_in  IN NUMBER,
                          cEst_Pedido_Cab_in       IN CHAR,
                          cUsu_Crea_in             IN VARCHAR2,
                          cFec_Crea_in             IN CHAR,
                          cNum_Telefono_in         IN CHAR,
                          cNum_Guia_in             IN CHAR,
                          nValIgvRecetario_in      IN NUMBER,
                          nValVentaRecetario_in    IN NUMBER,
                          vValVentaRecSinRed_in    IN NUMBER)
    RETURN CHAR;

    --Descripcion: Guarda el detalle del recetario.
    --Fecha        Usuario		    Comentario
    --30/04/2013   Luigy Terrazos Creación
    PROCEDURE RCM_GRAB_DET(cCodGrupoCia_in          IN CHAR,
                           cCod_Cia_in              IN CHAR,
                           cCod_Local_in            IN CHAR,
                           cNum_Recetario_in        IN CHAR,
                           nCod_Insumo_in           IN CHAR,
                           nCant_Atendida_in        IN NUMBER,
                           cVal_Prec_Rec_in         IN NUMBER,
                           cVal_Prec_Total_in       IN NUMBER,
                           cUsu_Crea_in             IN VARCHAR2,
                           cFec_Crea_in             IN CHAR,
                           cCodUnidadVenta_in       IN CHAR,
                           cCant_Porcentaje_in      IN NUMBER);

    --Descripcion: Asigna un codigo de pedido a un recetario magistral
    --Fecha        Usuario  Comentario
    --22/05/2013   LLEIVA   Creacion
    PROCEDURE RCM_ASIGNA_PEDIDO_RCM(cCodGrupoCia_in          IN CHAR,
                                    cCod_Cia_in              IN CHAR,
                                    cCod_Local_in            IN CHAR,
                                    cNum_Recetario           IN CHAR,
                                    cNum_Pedido              IN CHAR);

    --Descripcion: Lista las guias que se van a enlazar con los recetarios
    --             magistrales
    --Fecha        Usuario  Comentario
    --23/05/2013   LLEIVA   Creacion
    FUNCTION RCM_LISTAR_GUIAS_RCM
    RETURN FarmaCursor;

    --Descripcion: Graba una cabecera de recetario magistral adjunto a una
    --             guia previamente seleccionada
    --Fecha        Usuario  Comentario
    --24/05/2013   LLEIVA   Creacion
    FUNCTION RCM_GUIA_GRAB_CAB(cCodGrupoCia_in              IN CHAR,
                                cCod_Cia_in                 IN CHAR,
                                cCod_Local_in               IN CHAR,
                                cNum_Ord_Prep_in            IN CHAR,
                                cUsu_Crea_in                IN CHAR)
    RETURN CHAR;

    --Descripcion: Obtiene el porcentaje del Igv
    --Fecha       Usuario   Comentario
    --28/05/2013  ERIOS     Creacion
    FUNCTION GET_PORC_IGV(cCodIgv_in IN CHAR) RETURN NUMBER;

    --Descripcion: Obtiene listado de productos virtuales
    --Fecha       Usuario   Comentario
    --29/05/2013  LLEIVA    Creacion
    FUNCTION LISTA_PROD_VIRTUALES
    RETURN FarmaCursor;

    --Descripcion: Obtiene unidades relacionadas a una unidad particular
    --             y que posea un factor de conversión
    --Fecha       Usuario   Comentario
    --04/06/2013  LLEIVA    Creacion
    FUNCTION LISTA_UNID_MEDIDA_REL(cCodUnidMed_in IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Obtiene el estado de un recetario indicado
    --Fecha       Usuario   Comentario
    --13/06/2013  LLEIVA    Creacion
    FUNCTION GET_EST_RECETARIO_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Pedido_in      IN CHAR)
    RETURN VARCHAR2;

    --Descripcion: Anula un recetario magistral que pertenesca a un numero de pedido
    --Fecha       Usuario   Comentario
    --13/06/2013  LLEIVA    Creacion
    PROCEDURE ANULA_RECETARIO_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Pedido_in      IN CHAR);

    --Descripcion: Actualiza un recetario magistral que pertenesca a un numero de pedido
    --             indicando que este ha sido pagado
    --Fecha       Usuario   Comentario
    --13/06/2013  LLEIVA    Creacion
    PROCEDURE ACTUALIZA_RECET_COBRADO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Recetario      IN CHAR);

    --Descripcion: Retorna el factor de conversion de unidades de un producto con una unidad
    --             de venta
    --Fecha       Usuario   Comentario
    --18/06/2013  LLEIVA    Creacion
    FUNCTION GET_FACTOR_CONVERSION(cCod_Producto       IN CHAR,
                                   cCod_UnidadVenta    IN CHAR)
    RETURN VARCHAR2;

	    --Descripcion: Generacion trama recetario.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    FUNCTION GET_NUMERO_RECETARIO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedido_in IN CHAR) RETURN VARCHAR2;

    --Descripcion: Generacion trama recetario.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    FUNCTION GET_TRAMA_RECETARIO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumRecetario_in IN CHAR) RETURN VARCHAR2;

    --Descripcion: Actualiza estado de recetario.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    PROCEDURE ACTUALIZA_ESTADO_RECETARIO(cCodGrupoCia_in IN CHAR,
                                         cCodCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumRecetario_in IN CHAR,
                                         cEstado_in IN CHAR,
                                         vUsuario_in IN VARCHAR2);

    --Descripcion: Imprime Contrasenia Recetario Magistral
    --Fecha        Usuario  Comentario
    --21/05/2013   ERIOS    Creacion
    FUNCTION IMP_HTML_CONTRASENIA(cCodGrupoCia_in         IN CHAR,
                          cCod_Cia_in              IN CHAR,
                          cCod_Local_in            IN CHAR,
                          cNum_Recetario_in        IN CHAR)
    RETURN VARCHAR2;

    --Descripcion: Generacion trama actualización recetario en FASA.
    --Fecha       Usuario	Comentario
    --28/06/2013  ERIOS   Creacion
    FUNCTION GET_TRAMA_ACTUALIZA_RECETARIO(cCodGrupoCia_in IN CHAR,
                                           cCodCia_in IN CHAR,
                                           cCodLocal_in IN CHAR,
                                           cNumPedido_in IN CHAR,
                                           cAccion_in IN CHAR)
    RETURN VARCHAR2;

    --Descripción: Procedimiento Actualiza estado Pedido RCM anulado (N)
    --Fecha        Usuario   Comentario
    --21/05/2013   RUDY LL.  Creacion
    --===============================================================================================================
   PROCEDURE VTA_UPDATE_PEDIDO_CAB_RCM(cCodGrupoCia_in    IN CHAR,
                                              cCodCia_in 	 	     IN CHAR,
                                   	          cCodLocal_in    	 IN CHAR,
                								   	          cNumPedidoAnul_in  IN VARCHAR2,
                                              cEstPedCab_in      IN CHAR);


    --Descripción:   Función Valida si un pedido es del tipo RCM y devuelve estado actual del pedido.
    --Fecha          Usuario  Comentario
    --21/05/2013     RUDY LL. Creacion
--===============================================================================================================
   FUNCTION VTA_GET_VALIDA_RCM(cCodGrupoCia_in IN CHAR,
                                        --cCodCia_in 	 	     IN CHAR,
                                        cCod_Local_in      IN CHAR,
                                        cNum_Comp_Pago_in  IN CHAR)

   RETURN varchar;

--===============================================================================================================
  PROCEDURE VTA_RCM_VALIDA_RCM(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cNum_Comp_Pago_in IN CHAR);
 -- RETURN BOOLEAN;
--===============================================================================================================

    --Descripcion: Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION REPORTE_RECETARIO_MAGISTRAL(cCodGrupoCia_in   IN VARCHAR2,
                                         cCodCia_in        IN VARCHAR2,
                                         cCodLocal_in      IN VARCHAR2,
                                         cFecInicial_in    IN VARCHAR2,
                                         cFecFinal_in      IN VARCHAR2,
                                         cPaciente_in      IN VARCHAR2,
                                         cNumRecetario_in  IN VARCHAR2,
                                         cNumPedido_in     IN VARCHAR2)
    RETURN FarmaCursor;

    --Descripcion: Listado de Detalle de Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION LISTADO_DETALLE_RCM(cCodGrupoCia_in   IN CHAR,
                                       cCodCia_in        IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumRecetario_in  IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Detalle de Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION DETALLE_RCM(cCodGrupoCia_in   IN CHAR,
                         cCodCia_in        IN CHAR,
                         cCodLocal_in      IN CHAR,
                         cNumRecetario_in  IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Retorna listado de Ordenes de Preparado a recepcionar como productos
    --Fecha       Usuario	Comentario
    --25/10/2013  LLEIVA  Creacion
    FUNCTION LISTADO_RECEPCION_RCM(cCodLocal_in      IN CHAR,
                                   cFecha_in         IN CHAR,
                                   cNumOrdenPrep_in  IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Retorna listado de detalles de Ordenes de Preparado a recepcionar como productos
    --Fecha       Usuario	Comentario
    --25/10/2013  LLEIVA  Creacion
    FUNCTION LISTADO_RECEPCION_DETALLE_RCM(cNumOrdenPrep_in  IN CHAR)
    RETURN FarmaCursor;

END PTOVENTA_RCM;

/
