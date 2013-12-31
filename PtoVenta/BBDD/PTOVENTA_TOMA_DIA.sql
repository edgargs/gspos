--------------------------------------------------------
--  DDL for Package PTOVENTA_TOMA_DIA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TOMA_DIA" IS

  COD_NUMERA_TOMA_INV           PBL_NUMERA.COD_NUMERA%TYPE 		 := '013';

  COD_MOT_KARDEX_AJUSTE_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '107';
	COD_MOT_KARDEX_AJUSTE_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '009';

  COD_MOT_KARDEX_AJUSTE_POS1	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '510';
  COD_MOT_KARDEX_AJUSTE_NEG2	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '511';

	TIP_DOC_KARDEX_TOMA_INV	      LGT_KARDEX.TIP_DOC_KARDEX%TYPE 	 := '03';
	COD_NUMERA_KARDEX             PBL_NUMERA.COD_NUMERA%TYPE 		 := '016';
  	COD_NUMERA_AJUSTE             PBL_NUMERA.COD_NUMERA%TYPE 		 := '072';

  ID_TAB_GRAL                   PBL_TAB_GRAL.ID_TAB_GRAL%TYPE  := '46';
	ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';
	POS_INICIO		      CHAR(1):='I';

  --codigos de motivo de revertir
  --dubilluz  12.06.2009
  --- MAL CONTEO
  COD_MOT_KARDEX_MAL_CONTEO_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '107';
	COD_MOT_KARDEX_MAL_CONTEO_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '009';
  --- Reg.Por Almacen
  COD_MOT_KARDEX_POR_ALMACEN_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '107';
	COD_MOT_KARDEX_POR_ALMACEN_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '009';
  --- Error Operativo
  COD_MOT_KARDEX_ERR_OPERAT_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '107';
	COD_MOT_KARDEX_ERR_OPERAT_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '009';

  COD_MOT_KARDEX_VIRTUAL_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '510';
	COD_MOT_KARDEX_VIRTUAL_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '511';

  COD_MOT_KARDEX_CARGA_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '516';
	COD_MOT_KARDEX_CARGA_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '517';

	EST_TOMA_INV_REVERTIR             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'R';
  DESC_TIP_MON_SOLES   VARCHAR2(10) := 'SOLES';
	DESC_TIP_MON_DOLARES VARCHAR2(10) := 'DOLARES';--570

  COD_TIP_MON_SOLES    CHAR(2) := '01';
	COD_TIP_MON_DOLARES  CHAR(2) := '02';
	COD_TIP_COMP_BOLETA    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='01';
	COD_TIP_COMP_FACTURA   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='02';
	COD_TIP_COMP_GUIA      VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='03';
	COD_TIP_COMP_NOTA_CRED VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='04';
 	COD_TIP_COMP_TICKET    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE:='05';

  TYPE FarmaCursor IS REF CURSOR;


  --Descripcion: Lista los productos de la toma de inventario diario.
  --Fecha       Usuario   Comentario
  --25/05/2009  mfajardo    Creación
  FUNCTION GET_LISTA_PROD_TOMA_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista los productos restantes para el inventario.
  --Fecha       Usuario   Comentario
  --25/05/2009  mfajardo  Creación
  --09/10/2009  jchavez   Modificacion
  FUNCTION GET_LISTA_PRODS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cSecToma_in IN CHAR DEFAULT NULL)
  RETURN FarmaCursor;

  --Descripcion: Inserta un producto en la lista de inventario diario.
  --Fecha       Usuario   Comentario
  --25/05/2009  mfajardo    Creación
  PROCEDURE DIA_INSERTA_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR, cCodLab_in IN CHAR, cSecToma_in IN CHAR, cUnids_in IN CHAR, cStock_in IN CHAR);

  --Descripcion: Borra un producto en la lista de inventario diario.
  --Fecha       Usuario   Comentario
  --25/05/2009  mfajardo    Creación
  PROCEDURE DIA_BORRA_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR);

  --Descripcion: Graba un nuevo de inventario diario.
  --Fecha       Usuario   Comentario
  --25/05/2009  mfajardo    Creación
  PROCEDURE DIA_GRABA_TOM_INV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cMatriz_in IN CHAR, vIdUsu_in IN VARCHAR2);

  LOCAL_MATRIZ      CHAR(3):= '010' ;
	EST_TOMA_INV_PROCESO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'P';
	EST_TOMA_INV_EMITIDO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'E';
	EST_TOMA_INV_CARGADO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'C';
	EST_TOMA_INV_ANULADO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'N';
  TIPO_DIARIO                      LGT_TOMA_INV_CAB.IND_CICLICO%TYPE := 'D';

  --Descripcion: Obtiene la lista de tomas de inventario
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 FUNCTION TI_LISTA_TOMAS_INV(cCodGrupoCia_in IN CHAR ,
                             cCodLocal_in    IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Obtiene la lista de laboratorios de una toma de inventario
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 FUNCTION TI_LISTA_LABS_TOMA_INV(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Obtiene la lista de productos de un laboratorio en una toma de inventario
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_LISTA_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR
                                     )
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Ingresa la cantidad de un producto inventariado
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 PROCEDURE TI_INGRESA_CANT_PROD_TI(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR,
                                   cCodLab_in      IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   cCantToma_in    IN CHAR,
                                   cHora_in        IN CHAR);
/****************************************************************************/

  --Descripcion: LISTA LOS MOVIMIENTOS DE KARDEX DEL DIA
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 FUNCTION TI_LISTA_MOVS_KARDEX(cCodGrupoCia_in IN CHAR ,
  		   					             cCodLocal_in    IN CHAR ,
								               cCod_Prod_in	   IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Obtiene el total de items y el total tomado
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_TOTAL_ITEMS_TOMA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Obtiene la informacion valorizada en precios de todo lo tomado
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_INFORMACION_VALORIZADA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Lista la diferencia de los productos en consolidado
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_DIFERENCIAS_CONSOLIDADO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in   IN CHAR,
                                      cSecTomaInv_in IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Lista la diferencia de los productos en consolidado
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_DIFERENCIAS_HISTORICO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in   IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/


 --Descripcion: Lista la diferencia de los productos en consolidado por filtro
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_DIF_CONSOLIDADO_FILTro(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecTomaInv_in  IN CHAR,
                                    cCodLab_in      IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Actualiza el estado de la estructura de toma de inventario
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 PROCEDURE TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR,
                                     cEstToma_in     IN CHAR,
                                     cIdUsu_in       IN CHAR);
/****************************************************************************/

 --Descripcion: Obtiene un indicador para determinar si existe data incompleta en una toma de inventario
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_OBTIENE_IND_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR ,
                                         cCodLocal_in    IN CHAR,
                                         cSecToma_in     IN CHAR)
 RETURN CHAR;

/****************************************************************************/

 --Descripcion: Obtiene un indicador para determinar si el stock comprometido es mayor que el stock fisico
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación

 FUNCTION TI_OBTIENE_IND_STOCK_COMP(cCodGrupoCia_in IN CHAR,
   										                cCodLocal_in    IN CHAR,
										                  cProducto       IN CHAR,
                                      cCantidad       IN CHAR)
 RETURN CHAR;
/****************************************************************************/

 --Descripcion: Obtiene indicador para cargar la toma
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_OBTIENE_IND_FOR_UPDATE(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecTomaInv_in  IN CHAR,
                                    cIndProceso     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Obtiene un indicla lista de laboratorios con data incompleta en una toma de inventario
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 FUNCTION TI_LISTA_LABS_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR ,
                                        cCodLocal_in    IN CHAR,
                                        cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Carga Una toma de inventario: Actualiza estados, estados de congelamiento,stocks en local y genera kardex
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
   PROCEDURE TI_CARGA_TOMA_INV(cCodGrupoCia_in IN CHAR ,
                               cCodLocal_in    IN CHAR,
                               cSecToma_in     IN CHAR,
                               cIdUsu_in       IN CHAR);
/****************************************************************************/

  --Descripcion: Anula una toma de inventario: Actualiza estados y estados de congelamiento
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
  PROCEDURE TI_ANULA_TOMA_INV(cCodGrupoCia_in IN CHAR ,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR,
                              cIdUsu_in       IN CHAR);
/****************************************************************************/

 --Descripcion: Rellena con ceros las cantidades no ingresadas de los productos a inventariar en un laboratorio
 --Fecha       Usuario		Comentario
 --25/05/2009  mfajardo    Creación
 PROCEDURE TI_RELLENA_CERO_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
                                        cCodLocal_in    IN CHAR,
                                        cSecToma_in     IN CHAR
                                       );
/****************************************************************************/

  --Descripcion: Muestra las diferencias entre la cantidad ingresada y el stock del producto en una toma ciclica
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
  FUNCTION TI_LISTA_DIF_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  								                    cCodLocal_in    IN CHAR ,
										                      cSecToma_in     IN CHAR ,
										                      cCodLab_in      IN CHAR)
  RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Obtiene todos los codigos de laboratorio de la toma
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
  FUNCTION TI_LISTA_COD_LABORATORIOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR)
  RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Obtiene todos los codigos de productos por laboratorio
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 FUNCTION TI_LISTA_PROD_IMPRESION(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodLab_in      IN CHAR,
                                  cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Inserta en la tabla de cabecera de toma inventario en el local
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 PROCEDURE TI_INSERT_TOMA_CAB(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR);
/****************************************************************************/

  --Descripcion: Ontiene un numero de indicador si la toma ya existe en el local
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 FUNCTION TI_OBTIENE_CANT_TOMA_CAB(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR)
 RETURN FarmaCursor ;
/****************************************************************************/

  --Descripcion: Ontiene un numero de indicador si la toma ya existe en el local
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 FUNCTION TI_OBTIENE_CANT_TOMA_LAB(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR)
 RETURN FarmaCursor ;
/****************************************************************************/

  --Descripcion: Ontiene un numero de indicador si la toma ya existe en el local
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 FUNCTION TI_OBTIENE_CANT_TOMA_LAB_PROD(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cSecToma_in     IN CHAR)
 RETURN FarmaCursor ;
/****************************************************************************/


  --Descripcion: Inserta en la tabla de laboratorios de toma inventario en el local
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 PROCEDURE TI_INSERT_TOMA_LAB(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR);
/****************************************************************************/

  --Descripcion: Inserta en la tabla de productos por laboratorio para la toma de inventario
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 PROCEDURE TI_INSERT_TOMA_LAB_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR) ;
/****************************************************************************/

  --Descripcion: Inserta en todas las tablas necesarias para realizar la toma
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 PROCEDURE TI_ENVIA_TOMA_LOCAL(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cSecToma_in     IN CHAR);

/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación

 PROCEDURE TI_DESCONG_GLOB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
			  						   cCodLocal_in    IN CHAR ,
									   cSecToma_in     IN CHAR ,
									   cIdUsu_in       IN CHAR);

/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación

 PROCEDURE TI_ACT_EST_COG_PROD_LOCAL(cCodGrupoCia_in IN CHAR,
  									  cCodLocal_in    IN CHAR,
									  cCodProd_in     IN CHAR,
									  cIndCong_in     IN CHAR,
									  cIdUsu_in       IN CHAR)   ;


/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación

PROCEDURE DIA_INSERTA_PROD_CRUCE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR,cCodProdCruce_in IN CHAR, cCodLab_in IN CHAR,
  cUnidPres1_in IN CHAR,cUnidPres2_in IN CHAR,cValFrac1_in IN CHAR,  cValFrac2_in IN CHAR,
  cCant1_in IN CHAR,cCant2_in IN CHAR, cAccion_in IN CHAR,cSecToma_in IN CHAR);
  /****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación

 PROCEDURE CARGA_DIA_GRABA_TOM_INV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
   vIdUsu_in IN VARCHAR2);

/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación


 FUNCTION TI_DIFERENCIAS_CONTEO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecTomaInv_in  IN CHAR)
 RETURN FarmaCursor;

/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación


 FUNCTION TI_LISTA_PROD_LAB_TOMA_CONTEO(cCodGrupoCia_in IN CHAR ,
 		  							                 cCodLocal_in    IN CHAR ,
	                                   cSecToma_in     IN CHAR
                                     )
 RETURN FarmaCursor ;

 /****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación

 PROCEDURE TI_INGRESA_CANT_PROD_TI_CONTEO(cCodGrupoCia_in IN CHAR ,
  									                cCodLocal_in    IN CHAR ,
									                  cSecToma_in     IN CHAR ,
									                  cCodLab_in      IN CHAR ,
									                  cCodProd_in     IN CHAR ,
									                  cCantToma_in    IN CHAR ,
                                    cHora_in        IN CHAR ,
                                    cUsuarioMod     IN CHAR
                                    );

 /****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación
 FUNCTION TI_OBTIENE_STOCK_USADO_CRUCE(cCodGrupoCia_in   IN CHAR ,
   										                     cCodLocal_in    IN CHAR,
										                       cSecToma_in     IN CHAR,
                                           cCodProd_in     IN CHAR)
 RETURN CHAR ;

 /****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación

 PROCEDURE TI_CARGA_CRUCES_INV(cCodGrupoCia_in IN CHAR ,
   			 				               cCodLocal_in   IN CHAR ,
							                 cSecToma_in    IN CHAR ,
							                 cIdUsu_in      IN CHAR);

 /****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación

 PROCEDURE TI_RELLENA_CERO_CONTEO(cCodGrupoCia_in IN CHAR ,
  										                   cCodLocal_in    IN CHAR ,
                                         cSecToma_in     IN CHAR );


 /****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --25/05/2009  mfajardo    Creación

 PROCEDURE TI_ACT_EST_CARGA_PROD(cCodGrupoCia_in IN CHAR,
  									  cCodLocal_in    IN CHAR,
									  cSecToma_in     IN CHAR,
                    cCodProd_in     IN CHAR,
									  cIdUsu_in       IN CHAR)  ;

/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --15/06/2009  mfajardo    Creación

 PROCEDURE TI_ACT_CANT_PROD_CONTEO(cCodGrupoCia_in IN CHAR,
  									  cCodLocal_in    IN CHAR,
                      cSecToma_in     IN CHAR,
    								  cCodProd_in     IN CHAR,
		  							  cIdUsu_in       IN CHAR);

/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --17/06/2009  mfajardo    Creación

 FUNCTION TI_OBTIENE_IND_STOCK_COMP(cCodGrupoCia_in IN CHAR,
										                cProducto       IN CHAR)
 RETURN NUMBER;


/****************************************************************************/

  --Descripcion:
  --Fecha       Usuario		Comentario
  --17/06/2009  mfajardo    Creación

 FUNCTION TI_OBTIENE_TIEMPO_MOD_TOMA(cCodGrupoCia_in IN CHAR,
   										                cCodLocal_in    IN CHAR)
 RETURN CHAR;

  FUNCTION TI_F_CUR_DIFERENCIAS_DIARIO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR )
  RETURN FarmaCursor;

  --Descripcion:
  --Fecha       Usuario		Comentario
  --10/06/2009  dubilluz    Creación
 FUNCTION TI_F_CUR_LITA_MOTIVOS(cCodGrupoCia_in IN CHAR ,
  									             cCodLocal_in    IN CHAR ,
									               cTipoMotivo_in     IN CHAR
                                )
 return FarmaCursor;

 /****************************************************************************/
  --Descripcion:
  --Fecha       Usuario		Comentario
  --12/06/2009  dubilluz    Creación
 PROCEDURE TI_P_REVERTIR_PROD(
                              cCodGrupoCia_in IN CHAR ,
							                cCodLocal_in    IN CHAR ,
                              cSecUsu_in      IN CHAR ,
                              cSecToma_in     IN CHAR ,
                              cCodProd_in     IN CHAR ,
                              cCodMotivo_in    IN CHAR,
                              cSecKardex_in   IN CHAR,
                              cCantMov_in   IN CHAR,
                              cValFrac_in   IN CHAR
                             );
 /****************************************************************************/
 FUNCTION TI_F_CUR_LISTA_TRAB(
                              cCodCia_in      IN CHAR,
                              cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR
                              )
  RETURN FarmaCursor;
 /****************************************************************************/
 FUNCTION TI_F_VAR2_GET_TRABAJADOR(
                              cCodCia_in      IN CHAR,
                              cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cDNI_in    IN CHAR
                              )
  RETURN varchar2;

 --Descripcion: Se relizar el ajuste con nuevo motivo en Kardex
  --Fecha       Usuario		Comentario
  --15/06/2009  JCORTEZ    Creación
 PROCEDURE TI_AJUSTE(cCodGrupoCia_in IN CHAR ,
                      cCodLocal_in     IN CHAR ,
                      cSecUsu_in       IN CHAR ,
                      cSecToma_in      IN CHAR ,
                      cCodProd_in      IN CHAR ,
                      cCodMotivo_in    IN CHAR,
                      cSecKardex_in    IN CHAR,
                      cCantMov_in      IN CHAR,
                      cValFrac_in      IN CHAR,
                      cCodMotProdKar   IN CHAR,
                      cCodAjuste       IN CHAR);

  --Descripcion: Se graba en el kardex agregando secuencia origen al neutralizar registro de conteo.
  --Fecha       Usuario		Comentario
  --15/06/2009  JCORTEZ    Creación
 PROCEDURE GRABAR_KARDEX(cCodGrupoCia_in 	   IN CHAR,
                              cCodLocal_in    	   IN CHAR,
                              cCodProd_in		       IN CHAR,
                              cCodMotKardex_in 	   IN CHAR,
                              cTipDocKardex_in     IN CHAR,
                              cNumTipDoc_in  	     IN CHAR,
                              nStkAnteriorProd_in  IN NUMBER,
                              nCantMovProd_in  	   IN NUMBER,
                              nValFrac_in		       IN NUMBER,
                              cDescUnidVta_in	     IN CHAR,
                              cUsuCreaKardex_in	   IN CHAR,
                              cCodNumera_in	   	   IN CHAR,
                              cSecKardexOrigen     IN CHAR,
                              cCodAjuste           IN CHAR,
                              cGlosa_in			       IN CHAR DEFAULT NULL,
                              cTipDoc_in           IN CHAR DEFAULT NULL,
                              cNumDoc_in           IN CHAR DEFAULT NULL);

  --Descripcion: Se graba el ajuste generado
  --Fecha       Usuario		Comentario
  --17/06/2009  JCORTEZ    Creación
   PROCEDURE GRABAR_AJUSTE(cCodGrupoCia_in 	   IN CHAR,
                          cCodLocal_in    	   IN CHAR,
                          cIdUsu_in            IN CHAR,
                          cCodMotKardex_in     IN CHAR,
                          cCodAjust_in         IN CHAR);

  --Descripcion: Se graba productos por ajuste
  --Fecha       Usuario		Comentario
  --17/06/2009  JCORTEZ    Creación
   PROCEDURE GRABAR_AJUSTE_PROD(cCodGrupoCia_in 	   IN CHAR,
                                cCodLocal_in    	   IN CHAR,
                                cIdUsu_in            IN CHAR,
                                cCodAjuste_in        IN CHAR,
                                cCodToma_in          IN CHAR,
                                cCodProd_in          IN CHAR);

  --Descripcion: Se graba trabajadores por ajuste
  --Fecha       Usuario		Comentario
  --17/06/2009  JCORTEZ    Creación
   PROCEDURE GRABAR_AJUSTE_TRAB(cCodGrupoCia_in 	   IN CHAR,
                                cCodLocal_in    	   IN CHAR,
                                cIdUsu_in            IN CHAR,
                                cCodAjuste_in        IN CHAR,
                                cCodTrab             IN CHAR,
                                cCodTrabRRHH         IN CHAR,
                                cMontoDesc           IN NUMBER);



/****************************************************************************/
  FUNCTION TI_P_GRABA_PED_CAB(
                                    cCodGrupoCia_in 	   IN CHAR,
                                    cCodLocal_in    	   IN CHAR,
                                    cUsuCrea_in          IN CHAR
                                    )
      RETURN VARCHAR2;

 /****************************************************************************/
  PROCEDURE TI_P_GRABA_PED_DET(
                                  cCodGrupoCia_in 	   IN CHAR,
                                  cCodLocal_in    	   IN CHAR,
                                  cSecPedido    	   IN CHAR,
                                  cSecDet_in          IN CHAR,
                                  cCodProd_in         IN CHAR,
                                  cCant_in         IN CHAR,
                                  cValFrac_in      IN CHAR,
                                  cSecToma_in         IN CHAR,
                                  cSecKardex_in         IN CHAR,
                                  cCodMotivo_in      IN CHAR,
                                  cUsuCrea_in          IN CHAR
                                  );
 /****************************************************************************/

 /****************************************************************************/
 FUNCTION TI_F_CUR_DET_PED_TEMPORAL(
                                    cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedTemp_in  IN CHAR
                                    )
  RETURN FarmaCursor;
 /****************************************************************************/
 PROCEDURE TI_P_ELIMINA_DET_PED_TEMP(
                                    cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedTemp_in  IN CHAR,
                                    cCodProd_in  IN CHAR
                                    );
 /****************************************************************************/
 PROCEDURE TI_P_ELIMINA_PED_TEMP(
                                    cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedTemp_in  IN CHAR
                                    );

 /* *********************************************************************** */
 PROCEDURE TI_P_INSERT_TRAB_DCTO(
                                    cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedTemp_in  IN CHAR,
                                    cDNI_in  IN CHAR,
                                    cCodRRHH_in     IN CHAR,
                                    cCodTrab_in     IN CHAR,
                                    cMonto_in       IN CHAR,
                                    cUsu_in         IN CHAR
                                    );

 /* *********************************************************************** */
 PROCEDURE TI_P_REVIERTE_COMPROMETE_DET(
                                        cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedTemp_in  IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        cUsuCrea_in     IN CHAR,
                                        cIpPC_in        IN CHAR
                                        );
 /* *********************************************************************** */
  PROCEDURE TI_P_GRABA_PEDIDO(
                              cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedTemp_in  IN CHAR,
                              cNeto_in        IN CHAR,
                              cRedondeo_in    IN CHAR,
                              cUsu_in         IN CHAR,
                              cSecUsu_in      IN CHAR,
                              cIPPc_in        IN CHAR
                              );
 /* *********************************************************************** */
 FUNCTION TI_F_CUR_LISTA_PED_PENDIENTE(cCodGrupoCia_in IN CHAR,
  		   							      cCod_Local_in   IN CHAR,
                            cSec_Usu_in    IN CHAR)
  RETURN FarmaCursor;

 /* *********************************************************************** */
  FUNCTION TI_F_CUR_DET_PED_PENDIENTE(cCodGrupoCia_in IN CHAR,
  		   						          cCod_Local_in   IN CHAR,
								          cNum_Ped_Vta_in IN CHAR)
  RETURN FarmaCursor;

 /* *********************************************************************** */
  FUNCTION TI_P_CUR_FORMA_PAGO_PEDIDO(cCodGrupoCia_in IN CHAR,
  		   						   	            cCodLocal_in    IN CHAR,
									                  cNumPedVta_in 	IN CHAR)
  RETURN FarmaCursor;

 /* *********************************************************************** */
 FUNCTION TI_F_CUR_INFO_PEDIDO(cCodGrupoCia_in  IN CHAR,
  		   					             cCodLocal_in    	IN CHAR,
 							                 cNumPedVta_in    IN CHAR
                               )
  RETURN FarmaCursor;

/* **************************************************************************** */
FUNCTION TI_F_CUR_TRAB_DCTO_PEDIDO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecPedTmp_in   IN CHAR)
  RETURN FarmaCursor ;
/* ****************************************************************************** */
FUNCTION TI_F_VAR2_MONTO_PEDIDO(
                                cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cSecPedTmp_in   IN CHAR
                               )
  RETURN VARCHAR2;

PROCEDURE  TI_P_ACT_IND_SEG_CONTEO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cSecToma_in   IN CHAR,
                                cIdUsu_in       IN VARCHAR2);


END PTOVENTA_TOMA_DIA;

/
