--------------------------------------------------------
--  DDL for Package PTOVENTA_REP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_REP" AS

  g_cNumPedRep PBL_NUMERA.COD_NUMERA%TYPE := '014';

  g_nCantDiasPeriodoMes NUMBER := '28';

  g_cTipoNotaEntrada LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '01';
  g_cTipoNotaSalida LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '02';
  g_cTipoNotaRecepcion LGT_NOTA_ES_CAB.TIP_NOTA_ES%TYPE := '03';

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';

  TYPE FarmaCursor IS REF CURSOR;

  g_nTipoFiltroPrincAct NUMBER(1) := 1;
  g_nTipoFiltroAccTerap NUMBER(1) := 2;
  g_nTipoFiltroLab NUMBER(1) := 3;


  PROCEDURE INV_CALCULA_MAX_MIN(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cIndAutomatico_in IN CHAR DEFAULT 'N');

  --Descripcion: Setea los campos de la tabla TMP_PROD_LOCAL.
  --Fecha       Usuario   Comentario
  --02/03/2006  ERIOS     Creación
  PROCEDURE INV_LIMPIA_TMP_PROD(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR);

  --Descripcion: Calcula los totales de prods vendidos en un determinado dia.
  --Fecha       Usuario   Comentario
  --02/03/2006  ERIOS     Creación
  PROCEDURE INV_CALCULA_RESUMEN_VENTAS(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       vFecha_in       IN VARCHAR2);

  --Descripcion: Calcula la rotacion de los productos de un local.
  --Fecha       Usuario   Comentario
  --03/03/2006  ERIOS     Creación
  PROCEDURE INV_CALCULA_ROTACION_PRODS(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       nDiasVenta_in IN NUMBER);

  --Descripcion: Obtiene el factor de baja rotacion de un local.
  --Fecha       Usuario   Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_FAC_BAJA_ROT(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR) RETURN NUMBER;

  --Descripcion: Obtiene la rotacion de un producto en un local.
  --Fecha       Usuario   Comentario
  --03/03/2006  ERIOS     Creación
  --FUNCTION INV_GET_ROTACION_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN NUMBER;

  --Descripcion: Obtiene la ventas de los ultimos N dias de un podructo en un local.
  --Fecha       Usuario   Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_CANT_VTA_ULT_DIAS(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cCodProd_in       IN CHAR,
                                     nRotProd_in       IN NUMBER,
                                     nCantVeces_in     IN NUMBER,
                                     dFecha_in         IN DATE,
                                     nPeriodo_out      OUT CHAR)
    RETURN NUMBER;

  --Descripcion: Genera el Pedido Reposicion.
  --Fecha       Usuario   Comentario
  --06/03/2006  ERIOS     Creación
  --05/07/2006  ERIOS     Modificación: Actualiza LGT_PROD_LOCAL_FALTA_STK, FEC_GENERA_MAX_MIN
  --17/08/2007  DUBILLUZ  MODIFICACION
  PROCEDURE INV_GENERAR_PED_REP(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                nCantItems_in   IN NUMBER,
                                nCantProds_in   IN NUMBER,
                                cIdUsu_in       IN CHAR);

  --Descripcion: Calcula las unidades vendidas de los productos de un local en los ultimos 4 meses.
  --Fecha       Usuario   Comentario
  --04/12/2006  ERIOS     Creación
  PROCEDURE INV_CALCULA_UNID_VEND_PRODS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR);

  --Descripcion: Mueve el stock de un producto antiguo al nuevo codigo.
  --Fecha       Usuario   Comentario
  --05/12/2006  ERIOS     Creación
  PROCEDURE REP_MOVER_STK_CAMBIO_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProdAnt_in IN CHAR, cCodProdNue_in IN CHAR, dFecha_in IN DATE);

  --Descripcion: Obtiene la cantidad de dias con stock o con ventas efectivos en un periodo
  --Fecha       Usuario   Comentario
  --15/12/2006  LMESIA    Creación
  FUNCTION INV_GET_CANT_DIAS_EFECTIVO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cCodProd_in IN CHAR,
                                      cPeriodo_in IN CHAR,
                                      nRotProd_in IN NUMBER)
    RETURN NUMBER;

  /*--Descripcion: Calcula los dias de venta efectivos
  --Fecha       Usuario   Comentario
  --27/12/2006  ERIOS     Creación
  PROCEDURE REP_CALCULA_DIAS_EFECTIVO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR);  */

  --Descripcion:
  --Fecha       Usuario   Comentario
  --27/12/2006  ERIOS     Creación
  PROCEDURE INV_LIMPIA_TMP_PROD_DIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  vFecha_in IN VARCHAR2);

  --Descripcion: Obtiene la cantidad máxima para reponer.
  --Fecha       Usuario	 Comentario
  --17/07/2006  ERIOS     Creación
  FUNCTION INV_GET_CANT_MAX(nCantSug_in IN NUMBER,nPorcAdic_in IN NUMBER,
  nCantMax_in IN NUMBER,nValFrac_in IN NUMBER,nStock_in IN NUMBER) RETURN NUMBER;

  --Descripcion: Genera el Pedido Reposicion.
  --Fecha       Usuario   Comentario
  --19/03/2007  ERIOS     Creacion
  PROCEDURE INV_GENERA_PED_AUTO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cIdUsu_in       IN CHAR);

  --Descripcion: Obtiene el numero de dias maximo de reposicion de un producto en un local.
  --Fecha       Usuario		Comentario
  --14/05/2007  ERIOS     Creación
  FUNCTION INV_GET_STK_TRANS_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN NUMBER;

  --Descripcion: Calcula los totales de prods vendidos en un determinado dia, en matriz.
  --Fecha       Usuario   Comentario
  --14/05/2007  ERIOS     Creación
  PROCEDURE INV_CALCULA_RESUMEN_VENTAS_M(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       vFecha_in       IN VARCHAR2);

  --Descripcion: Obtiene la cantidad de dias con stock o con ventas efectivos en un periodo en matriz.
  --Fecha       Usuario   Comentario
  --14/05/2007  ERIOS    Creacion
  FUNCTION INV_GET_CANT_DIAS_EFECTIVO_M(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cCodProd_in IN CHAR,
                                      cPeriodo_in IN CHAR,
                                      nRotProd_in IN NUMBER) RETURN NUMBER;

  --Descripcion: Establece una cantidad adicional a un producto
  --Fecha       Usuario	     Comentario
  --20/04/2007  Luis Reque   Creación
  PROCEDURE INV_SET_CANT_ADIC_TMP(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodProd_in     IN CHAR,
                                  nCantTmp_in     IN NUMBER,
                                  cIdUsu_in       IN VARCHAR2);

  --Descripcion: Obtiene el detalle de un Pedido de Reposicion.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  --05/07/2006  ERIOS     Modificación
  FUNCTION INV_GET_DET_REP_VER(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNroPedido_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: realiza el filtro de pedido de reposicion por laboratorio
  --Fecha       Usuario	 Comentario
  --24/11/2006  PAULO     Creación
  FUNCTION INV_GET_DET_REP_FILTRO(cCodGrupoCia_in IN CHAR,
  		   					                cCodLocal_in    IN CHAR,
							                    cNroPedido_in   IN CHAR,
                                  cCodFiltro_in   IN CHAR)
  RETURN FarmaCursor;
  --Descripcion: Obtiene el último pedido reposición del día.
  --Fecha       Usuario	 Comentario
  --04/09/2006  ERIOS     Creación
  FUNCTION INV_GET_ULTIMO_PED_REP(cGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN CHAR;

  --Descripcion: Guarda la cantidad a solicitar en un pedido de reposicion.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  PROCEDURE INV_SET_CANT_PEDREP_TMP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR,nCantTmp_in IN NUMBER,cIdUsu_in IN VARCHAR2);

  --Descripcion: Obtiene la cabecera de reposicion de un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_CAB_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)RETURN FarmaCursor;

  --Descripcion: Obtiene el ultimo pedido de reposicion de un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_ULT_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)RETURN FarmaCursor;

  --Descripcion: Obtiene la cantidad anterior de un pedido de reposicion de un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  FUNCTION INV_GET_CANT_ANT_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene el indicador de Pedido de Reposicion.
  --Fecha       Usuario		Comentario
  --09/03/2006  ERIOS     	Creación
  FUNCTION INV_GET_PED_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN CHAR;

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

  --Descripcion: Obtiene el listado de Pedidos de Reposion de un local.
  --Fecha       Usuario		Comentario
  --02/03/2006  ERIOS     Creación
  --16/10/2008  JCORTEZ   Modificacion
  FUNCTION INV_LISTA_PEDIDO_REPOSICION(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cFecInicio_in   IN CHAR,
                                       cFecFin_in      IN CHAR,
                                       cTipoFiltro_in  IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de productos para reposicion a enviar a Matriz.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  --20/04/2007	 LREQUE	   Modificación
  FUNCTION INV_LISTA_PROD_REP_VER(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle del pedido actual de Reposicion.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  FUNCTION INV_GET_PED_ACT_REP(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de productos para reposicion a enviar a Matriz filtrado.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  FUNCTION INV_LISTA_PROD_REP_VER_FILTRO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipoFiltro_in IN CHAR, cCodFiltro_in IN CHAR) RETURN FarmaCursor;

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

  --Descripcion: Lista productos en Stock Cero.
  --Fecha       Usuario	 Comentario
  --30/06/2006  ERIOS     Creación
  FUNCTION INV_LISTA_PROD_REP_STK_CERO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de productos para reposicion.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creación
  --19/04/2007  LREQUE    Modifcación
  FUNCTION INV_LISTA_PROD_REPOSICION(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR)RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de productos para reposicion filtrado.
  --Fecha       Usuario		Comentario
  --06/03/2006  ERIOS     Creación
  FUNCTION INV_LISTA_PROD_REP_FILTRO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cTipoFiltro_in IN CHAR, cCodFiltro_in IN CHAR) RETURN FarmaCursor;


  PROCEDURE  INV_ACTUALIZA_IND_LINEA (cIdUsuario_in IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cCodGrupoCia_in IN CHAR);

   FUNCTION INV_OBTIENE_IND_LINEA(cCodLocal_in  IN CHAR,
                                  cCodGrupoCia_in IN CHAR)
   RETURN VARCHAR2;

   --Descripcion: Calcula el stock de la cadena (lcoales y almacen).
   --Fecha        Usuario Comentario
   --17/07/2007   ERIOS   Creacion
   PROCEDURE REP_CALCULA_STOCK_M(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in  IN CHAR);

   --Descripcion: Calcula la cantidad exhid de la cadena.
   --Fecha        Usuario Comentario
   --17/07/2007   ERIOS   Creacion
   PROCEDURE REP_CALCULA_EXHIB_M(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in  IN CHAR);

  --Descripcion: Calcula lel historico de stock.
  --Fecha        Usuario Comentario
  --18/07/2007   ERIOS   Creacion
  PROCEDURE REP_CALCULA_HIST_STK_M(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    vFecha_in IN VARCHAR2);

  --Descripcion: Calcula el stock en transito del local o matriz.
  --Fecha        Usuario Comentario
  --19/07/2007   ERIOS   Creacion
  PROCEDURE REP_CALCULA_TRANSITO(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

  --Descripcion: Inserta en una tabla temporal la informacion de los stocks de los productos
  --Fecha        Usuario Comentario
  --08/08/2007   PAMEGHINO   Creacion
  PROCEDURE REP_GRABA_INDC_STOCK(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsurio_in IN CHAR);

  --Descripcion: ACTUALIZA A NULOS LOS CAMPOS DE INDICADORES DE STOCK
  --Fecha        Usuario Comentario
  --08/08/2007   PAMEGHINO   Creacion
  PROCEDURE REP_ACTUALIZA_INDS_STOCK_NULL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsurio_in IN CHAR)  ;

  --Descripcion: ACTUALIZA AL VALOR DE LA TABLA TEMPORAL TODOS LOS REGISTROS EN LA TABLA LGT_PROD_LOCAL_REP
  --Fecha        Usuario Comentario
  --08/08/2007   PAMEGHINO   Creacion
  PROCEDURE REP_ACTUALIZA_INDS_STOCK(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsurio_in IN CHAR);

  --Descripcion: REALIZA TODO EL PROCESO DE ACTUALIZACION DE LOS INDICADORES DE STOCK PARA EL PEDIDO REPOSICION
  --Fecha        Usuario Comentario
  --08/08/2007   PAMEGHINO   Creacion
  PROCEDURE REP_PROCESO_IND_STOCK(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsurio_in IN CHAR);

  --Descripcion: Actualiza el resumen por mes y local, con la logica de cambio codigo.
  --Fecha        Usuario Comentario
  --28/08/2007   ERIOS   Creacion
  PROCEDURE REP_MOVER_STK_MES_LOC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProdAnt_in IN CHAR, cCodProdNue_in IN CHAR, dFecha_in IN DATE);

  --Descripcion: elimina e inserta las ventas acumuladas contemplando los marcados como excluidos para el calculo de reposicion
  --Fecha        Usuario Comentario
  --28/08/2007   pameghino   Creacion
  PROCEDURE REP_ELIMINA_FECHA_RESUMEN(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cIndAutomatico_in IN CHAR);

  --Descripcion: Actualiza el Ind
  --Fecha       Usuario		    Comentario
  --17/09/2007  dubilluz       creacion
  PROCEDURE  INV_ACTUALIZA_IND_LINEA(cIdUsuario_in IN CHAR,
                                      cCodLocal_in  IN CHAR,
                                      cCodGrupoCia_in IN CHAR,
                                      cIndActualizar_in IN CHAR,
                                     cTiempoConexion_in  IN CHAR);

  --Descripcion: Obtiene el Tiempo Estimado de Consulta
  --Fecha       Usuario		    Comentario
  --13/09/2007  DUBILLUZ      Creación
  FUNCTION REP_GET_TIEMPOESTIMADO(cCodGrupoCia_in  IN CHAR)
                                           RETURN varchar2;

 --Descripcion: oBTIENE EL IND LINEA ACTUAL
 --Fecha       Usuario		    Comentario
 --24/09/2007  dubilluz       creacion
 FUNCTION  REP_GET_INDLINEA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in  IN CHAR) RETURN CHAR;

  --Fecha       Usuario		Comentario
  --25/09/2007  dubilluz   creacion
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);

  --Descripcion: Se obtiene la verificacion de la revision del RDM
  --Fecha       Usuario		Comentario
  --15/10/2007  JCORTEZ   Creacion
   FUNCTION DIST_OBTIENE_PROD_REVISION(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNroPedido_in   IN CHAR,
                                       cCodProd_in     IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene la cantidad maxima para el producto
  --Fecha       Usuario		Comentario
  --26/08/2008  DUBILLUZ   Creacion
   FUNCTION  REP_GET_CANT_MAX_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   cIndActualiza   IN CHAR DEFAULT 'S') RETURN NUMBER;

  --Descripcion: Recalcula ventas por mes, producto y local
  --Fecha       Usuario		Comentario
  --04/09/2008  JLUNA   Creacion
   PROCEDURE INI_VTA_RES_VTA_EFECTIVA_LOCAL(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR);

  --Descripcion: Obtiene la cantidad maxima para el producto
  --Fecha       Usuario		Comentario
  --23/10/2008  JOLIVA   Creacion
   FUNCTION  REP_GET_CANT_REP_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   cCantMeses_in   IN NUMBER) RETURN NUMBER;

--Descripcion: Actualiza la cantidad sugerida del pedido de reposicion por
   --agrupacion de productos
   --Fecha       Usuario		Comentario
   --01/10/2008  DVELIZ     Creacion
   --04/06/2009  JOLIVA     SE AGREGA GRUPO (NO OBLIGATORIO)
   PROCEDURE REP_ACTUALIZA_CANT_SUG(vCodGrupoCia IN CHAR, vCodLocal IN CHAR, vCodGrupo IN CHAR DEFAULT NULL);

   --Descripcion: Ejecuta los procesos respectivos para el pedido de reposicion
   --Fecha       Usuario		Comentario
   --01/10/2008  DVELIZ     Creacion
  PROCEDURE  REP_EJECUTA_PROCESO_REPOSICION;

END;

/
