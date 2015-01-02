CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_TOMA_DIA" IS

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

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_TOMA_DIA" IS

/****************************************************************************/

  FUNCTION GET_LISTA_PROD_TOMA_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLista FarmaCursor;
  BEGIN
    OPEN curLista FOR
    SELECT A.COD_PROD|| 'Ã' ||
           P.DESC_PROD|| 'Ã' ||
           L.NOM_LAB|| 'Ã' ||
           P.DESC_UNID_PRESENT|| 'Ã' ||
           NVL(C.UNID_VTA,' ')|| 'Ã' ||
           to_char(C.STK_FISICO,'999,990.00') || 'Ã' ||
           NVL(LR.TIPO,' ') || 'Ã' ||
           DECODE(C.IND_PROD_FRACCIONADO,'S',C.UNID_VTA,P.DESC_UNID_PRESENT)
    FROM   LGT_AUX_INV_DIA A, LGT_PROD P, LGT_LAB L,
           LGT_PROD_LOCAL C, LGT_PROD_LOCAL_REP LR
    WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_LOCAL = cCodLocal_in
          AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND A.COD_PROD = P.COD_PROD
          AND P.COD_LAB = L.COD_LAB
          AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND A.COD_LOCAL = C.COD_LOCAL
          AND A.COD_PROD = C.COD_PROD
          AND C.COD_GRUPO_CIA = LR.COD_GRUPO_CIA
          AND C.COD_LOCAL = LR.COD_LOCAL
          AND C.COD_PROD = LR.COD_PROD
    ;
    RETURN curLista;
  END;
  /****************************************************************************/
  FUNCTION GET_LISTA_PRODS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cSecToma_in IN CHAR DEFAULT NULL)
  RETURN FarmaCursor
  IS
    curLista FarmaCursor;
    v_nBloque NUMBER:=0;
  BEGIN

     select distinct a.bloque into v_nBloque
     from  lgt_toma_inv_cab a
     where a.cod_grupo_cia = cCodGrupoCia_in
     and a.cod_local = cCodLocal_in
     and a.sec_toma_inv = cSecToma_in
     ;


    OPEN curLista FOR

           SELECT C.COD_PROD|| 'Ã' ||
           P.DESC_PROD|| 'Ã' ||
           P.DESC_UNID_PRESENT|| 'Ã' ||
           C.UNID_VTA|| 'Ã' ||
           to_char(C.STK_FISICO,'9999990.00') || 'Ã' ||
           P.COD_LAB|| 'Ã' ||
           C.VAL_FRAC_LOCAL
           FROM LGT_PROD_LOCAL C,LGT_PROD P

           WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.COD_LOCAL = cCodLocal_in
           AND C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
           AND C.COD_PROD = P.COD_PROD
           AND C.EST_PROD_LOC='A'
           /*AND C.COD_PROD NOT IN (
                                  select d.cod_prod
                                  from   lgt_toma_inv_lab_prod d
                                  where  d.cod_grupo_cia = cCodGrupoCia_in and
                                         d.cod_local =  cCodLocal_in and
                                         d.hora is not null and
                                         d.sec_toma_inv in (
                                                             select c.sec_toma_inv
                                                             from   lgt_toma_inv_cab c
                                                             where  c.cod_grupo_cia = cCodGrupoCia_in and
                                                                    c.cod_local = cCodLocal_in and
                                                                    c.tip_toma_inv = 'D' and
                                                                    c.bloque = v_nBloque --agregado jchavez 09.10.09
                                                                    -- comentado jmiranda 04.11.09
                                                           )  and
                                         d.bloque = v_nBloque  --agregado jchavez 09.10.09
                                         --comentado jmiranda 04.11.09
                                 )*/
           AND C.COD_PROD IN ( SELECT K.COD_PROD
                               FROM LGT_KARDEX K
                               WHERE K.COD_GRUPO_CIA= cCodGrupoCia_in AND
                                    K.COD_LOCAL=cCodLocal_in
                             )
           AND P.EST_PROD = 'A' --agregado jchavez 09.10.09

          ;

    RETURN curLista;
  END;
  /****************************************************************************/
  PROCEDURE DIA_INSERTA_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR, cCodLab_in IN CHAR, cSecToma_in IN CHAR, cUnids_in IN CHAR, cStock_in IN CHAR)
  AS

  v_nCant NUMBER;
  v_nCantProd NUMBER;
  stk_fis NUMBER;
  v_nBloque NUMBER;
  BEGIN

    select count(*)
    into v_nCant
    from lgt_toma_inv_lab L
    where cod_grupo_cia = cCodGrupoCia_in and
          cod_local = cCodLocal_in and
          sec_toma_inv = cSecToma_in and
          cod_lab = cCodLab_in ;

    select count(*)
    into v_nCantProd
    from lgt_toma_inv_lab_prod
    where cod_grupo_cia = cCodGrupoCia_in and
          cod_local = cCodLocal_in and
          sec_toma_inv = cSecToma_in and
          cod_lab = cCodLab_in and
          cod_prod = cCodProd_in;

    select stk_fisico
    into stk_fis
    from lgt_prod_local
    where cod_grupo_cia = cCodGrupoCia_in and
          cod_local = cCodLocal_in and
          cod_prod = cCodProd_in;

    IF  v_nCant <= 0 THEN

     INSERT INTO lgt_toma_inv_lab( COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,COD_LAB,
                                       CANT_PROD_LAB,
                                       EST_TOMA_INV_LAB,
                                       FEC_CREA_TOMA_INV_LAB,USU_CREA_TOMA_INV_LAB)
     VALUES( cCodGrupoCia_in,cCodLocal_in,cSecToma_in,NVL(cCodLab_in,' '),0,'C',SYSDATE,'MFAJARDO');

    END IF;


    IF  v_nCantProd <= 0 THEN

    --JCHAVEZ 12102009.sn
     select distinct a.bloque into v_nBloque
     from  lgt_toma_inv_cab a
     where a.cod_grupo_cia = cCodGrupoCia_in
     and a.cod_local = cCodLocal_in
     and a.sec_toma_inv = cSecToma_in;
    --JCHAVEZ 12102009.en
    INSERT INTO LGT_TOMA_INV_LAB_PROD( COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,COD_LAB,
                                       COD_PROD,
                                       DESC_UNID_VTA,
                                       VAL_FRAC,
                                       CANT_TOMA_INV_PROD,
                                       STK_ANTERIOR,
                                       EST_TOMA_INV_LAB_PROD,
                                       FEC_CREA_TOMA_INV_LAB_PROD,USU_CREA_TOMA_INV_LAB_PROD, IND_DIFENCIA, HORA, HORA_CONTEO,BLOQUE )--JCHAVEZ 12102009 se agregó bloque

    VALUES( cCodGrupoCia_in,cCodLocal_in,cSecToma_in,cCodLab_in,cCodProd_in,
    NVL(cUnids_in,' '),1,stk_fis,stk_fis,'C',SYSDATE,'MFAJARDO','C', SYSDATE, SYSDATE,v_nBloque);

    END IF;

  END;

  /****************************************************************************/

  PROCEDURE DIA_INSERTA_PROD_CRUCE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR,cCodProdCruce_in IN CHAR, cCodLab_in IN CHAR,
  cUnidPres1_in IN CHAR,cUnidPres2_in IN CHAR,cValFrac1_in IN CHAR,  cValFrac2_in IN CHAR,
  cCant1_in IN CHAR,cCant2_in IN CHAR, cAccion_in IN CHAR, cSecToma_in IN CHAR)
  AS

   vAccion number;
   vAccionCruce number;
   vCant number;

  BEGIN

  IF cAccion_in='Suma' THEN
     vAccion:=-1;
     vAccionCruce:=1;
  ELSE
     vAccion:=1;
     vAccionCruce:=-1;
  END IF;

       SELECT COUNT(*)
       INTO vCant
       FROM LGT_AUX_INV_DIA_CRUCE C
       WHERE  C.SEC_TOMA_INV=cSecToma_in AND
              C.COD_PROD =  cCodProd_in AND
              C.COD_PROD_CRUCE = cCodProdCruce_in;

       IF vCant=0 THEN

          INSERT INTO LGT_AUX_INV_DIA_CRUCE(  COD_GRUPO_CIA   ,
                                        COD_LOCAL     ,
                                        SEC_TOMA_INV ,
                                        COD_PROD      ,
                                        COD_PROD_CRUCE ,
                                        UNID_PRES,
                                        UNID_PRES_PROD_CRUCE,
                                        VAL_FRAC,
                                        VAL_FRAC_PROD_CRUCE,
                                        CANTIDAD_PROD,
                                        CANTIDAD_PROD_CRUCE,
                                        ACCION,
                                        ACCION_CRUCE,
                                        --COD_LAB      ,
                                        FEC_CREA_TOMA_INV_DIA ,
                                        USU_CREA_TOMA_INV_DIA
                                        )
           VALUES( cCodGrupoCia_in,cCodLocal_in,cSecToma_in,cCodProd_in,cCodProdCruce_in,cUnidPres1_in,
                   cUnidPres2_in,cValFrac1_in,cValFrac2_in,cCant1_in,cCant2_in,vAccion,vAccionCruce,SYSDATE,'MFAJARDO');

       ELSE
           UPDATE LGT_AUX_INV_DIA_CRUCE
           SET CANTIDAD_PROD = cCant1_in , CANTIDAD_PROD_CRUCE=cCant2_in,ACCION = vAccion , ACCION_CRUCE =vAccionCruce
           WHERE  SEC_TOMA_INV=cSecToma_in AND
                  COD_PROD =  cCodProd_in AND
                  COD_PROD_CRUCE = cCodProdCruce_in;

       END IF;



  END;
  /****************************************************************************/


  PROCEDURE DIA_BORRA_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR)
  AS
  BEGIN
    DELETE FROM LGT_AUX_INV_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;
  END;
  /****************************************************************************/
  PROCEDURE DIA_GRABA_TOM_INV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cMatriz_in IN CHAR, vIdUsu_in IN VARCHAR2)
  AS
    v_nNumToma LGT_TOMA_INV_CAB.SEC_TOMA_INV%TYPE;
    n_vNumera NUMBER;
    CURSOR curLabs IS
    SELECT COD_LAB,COUNT(COD_PROD) AS CANT_PROD
    FROM LGT_AUX_INV_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
    GROUP BY COD_LAB
    ;
    v_rCurLab curLabs%ROWTYPE;
    v_nCantLabs NUMBER(5) := 0;
  BEGIN
    --GRABAR CABECERA
    IF cMatriz_in = '1' THEN
      EXECUTE IMMEDIATE 'SELECT VAL_NUMERA
                          FROM PBL_NUMERA@XE_'||cCodLocal_in||'
                          WHERE COD_GRUPO_CIA = :1
                                AND COD_LOCAL = :2
                                AND COD_NUMERA = :3 ' INTO n_vNumera
                        USING cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV;
      EXECUTE IMMEDIATE 'BEGIN FARMA_UTILITY.ACTUALIZAR_NUMERA_SIN_COMMIT@XE_'||cCodLocal_in||'(:1,:2,:3,:4); END;'
                        USING cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV, vIdUsu_in;
      v_nNumToma := Farma_Utility.COMPLETAR_CON_SIMBOLO(n_vNumera,8,'0','I' );
      IF v_nNumToma IS NULL THEN
        RAISE_APPLICATION_ERROR(-20041,'NUMERA EN BLANCO');
      END IF;
    ELSE
      v_nNumToma := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV),8,'0','I' );
      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV, vIdUsu_in);
    END IF;

    INSERT INTO LGT_TOMA_INV_CAB(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,
                                 TIP_TOMA_INV,CANT_LAB_TOMA,EST_TOMA_INV,
                                 FEC_CREA_TOMA_INV,USU_CREA_TOMA_INV,
                                 IND_CICLICO)
    VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumToma,
                   'D',1,'E',
                   SYSDATE,vIdUsu_in,
                   'N');

    --GRABAR LABORATORIOS
    FOR v_rCurLab IN curLabs
    LOOP
      v_nCantLabs := v_nCantLabs+1;
      INSERT INTO LGT_TOMA_INV_LAB(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,
                                   COD_LAB,EST_TOMA_INV_LAB,CANT_PROD_LAB,
                                   FEC_CREA_TOMA_INV_LAB,USU_CREA_TOMA_INV_LAB)
       VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumToma,
                          v_rCurLab.COD_LAB,'E',v_rCurLab.CANT_PROD,
                          SYSDATE,vIdUsu_in);
      --GRABAR DETALLE
      INSERT INTO LGT_TOMA_INV_LAB_PROD(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,COD_LAB,
                                       COD_PROD,DESC_UNID_VTA,VAL_FRAC,
                                       STK_ANTERIOR,
                                       EST_TOMA_INV_LAB_PROD,
                                       FEC_CREA_TOMA_INV_LAB_PROD,USU_CREA_TOMA_INV_LAB_PROD,BLOQUE)--jchavez 12102009 agrego bloque
      SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,v_nNumToma,C.COD_LAB,
              C.COD_PROD,PL.UNID_VTA,PL.VAL_FRAC_LOCAL,
              PL.STK_FISICO,
              'E',
              SYSDATE,vIdUsu_in,C.BLOQUE --jchavez 10122009 se agrego bloque
      FROM LGT_AUX_INV_DIA C, LGT_PROD_LOCAL PL
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.COD_LAB = v_rCurLab.COD_LAB
            AND C.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
            AND C.COD_LOCAL = PL.COD_LOCAL
            AND C.COD_PROD = PL.COD_PROD;

    END LOOP;
    IF v_nCantLabs = 0 THEN
      RAISE_APPLICATION_ERROR(-2040,'NO SE HAN GRABADO NINGUN LABORATORIO');
    ELSE
      UPDATE LGT_TOMA_INV_CAB
      SET FEC_MOD_TOMA_INV = SYSDATE, USU_MOD_TOMA_INV = vIdUsu_in, CANT_LAB_TOMA = v_nCantLabs
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND SEC_TOMA_INV = v_nNumToma;
    END IF;
    --DBMS_OUTPUT.PUT_LINE('INVENTARIO DIARIO NUEVO: '||v_nNumToma);
  END;
  /****************************************************************************/


 FUNCTION TI_LISTA_TOMAS_INV(cCodGrupoCia_in IN CHAR,
 		  					             cCodLocal_in    IN CHAR)
 RETURN FarmaCursor
 IS
   curDia FarmaCursor;
   vDias char(7);
   nNumDia NUMBER;
 BEGIN

  nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);

  SELECT LC.DIA_TOMA INTO vDias
  FROM   PBL_LOCAL LC
  WHERE  LC.COD_GRUPO_CIA = cCodGrupoCia_in AND
         LC.COD_LOCAL     = cCodLocal_in	;

	OPEN curDia FOR
     SELECT SEC_TOMA_INV  || 'Ã' ||
        	  CASE   TIP_TOMA_INV
        		WHEN   'P' THEN 'PARCIAL'
            WHEN   'D' THEN 'DIARIA'
        		ELSE 'TOTAL'
        		END	  || 'Ã' ||
			      TO_CHAR(FEC_CREA_TOMA_INV,'dd/MM/yyyy HH24:mi:ss') || 'Ã' ||
		        CASE NVL(EST_TOMA_INV,EST_TOMA_INV_EMITIDO)
				    WHEN EST_TOMA_INV_PROCESO THEN 'EN PROCESO'
	          WHEN EST_TOMA_INV_EMITIDO THEN 'EMITIDO'
				    WHEN EST_TOMA_INV_CARGADO THEN 'CARGADO'
			 	    WHEN EST_TOMA_INV_ANULADO THEN 'ANULADO'
		        END|| 'Ã' ||
            NVL(EST_TOMA_INV,' ')|| 'Ã' ||
            IND_SEG_CONTEO
     FROM  LGT_TOMA_INV_CAB
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
  		     COD_LOCAL     = cCodLocal_in	   AND
		       EST_TOMA_INV IN (EST_TOMA_INV_PROCESO,EST_TOMA_INV_EMITIDO)AND
           TIP_TOMA_INV = TIPO_DIARIO AND
           /*(TO_CHAR(FEC_CREA_TOMA_INV,'dd/MM/yyyy') = TO_CHAR(SYSDATE,'dd/MM/yyyy')  OR
           TO_CHAR(FEC_CREA_TOMA_INV,'dd/MM/yyyy') = TO_CHAR(SYSDATE-1,'dd/MM/yyyy'))  AND  --MFAJARDO 06/08/09
           */
           /*
           FEC_CREA_TOMA_INV BETWEEN
                                     TO_DATE(TO_CHAR(SYSDATE - 1 ,'dd/MM/yyyy')||
                                                     ' 00:00:00','DD/MM/YYYY HH24:MI:SS')
                                 AND TO_DATE(TO_CHAR(SYSDATE,'dd/MM/yyyy')
                                                     || ' 23:59:59','DD/MM/YYYY HH24:MI:SS') */

          FEC_CREA_TOMA_INV BETWEEN TRUNC(SYSDATE-1) AND TRUNC(SYSDATE+1)-1/24/60/60 AND



           DECODE(vDias,NULL,'S',DECODE(vDias,REGEXP_REPLACE(vDias,nNumDia, 'S'),'N','S')) = 'S' ;

	 RETURN curDia;
 END;
/****************************************************************************/

 FUNCTION TI_LISTA_LABS_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  						               cCodLocal_in    IN CHAR ,
                                 cSecToma_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curDia FarmaCursor;
 BEGIN
	OPEN curDia FOR
   SELECT TIL.COD_LAB  || 'Ã' ||
	        L.NOM_LAB    || 'Ã' ||
	        CASE NVL(EST_TOMA_INV_LAB,EST_TOMA_INV_EMITIDO)
	   		  WHEN EST_TOMA_INV_PROCESO THEN 'En proceso'
	        WHEN EST_TOMA_INV_EMITIDO THEN 'Emitido'
			    WHEN EST_TOMA_INV_CARGADO THEN 'Cargado'
			    WHEN EST_TOMA_INV_ANULADO THEN 'Anulado'
	        END
   FROM   LGT_TOMA_INV_LAB TIL,
 	        LGT_LAB L
   WHERE  TIL.COD_GRUPO_CIA = cCodGrupoCia_in AND
  	      TIL.COD_LOCAL     = cCodLocal_in	   AND
	        TIL.SEC_TOMA_INV  = cSecToma_in 	   AND
	        TIL.COD_LAB       = L.COD_LAB       ;
	 RETURN curDia;
 END;
/****************************************************************************/

 FUNCTION TI_LISTA_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  							                 cCodLocal_in    IN CHAR ,
	                                   cSecToma_in     IN CHAR
                                     )
 RETURN FarmaCursor
 IS
   curDia FarmaCursor;
 BEGIN
	OPEN curDia FOR
  SELECT TILP.COD_PROD      || 'Ã' ||
  		   NVL(P.DESC_PROD,' ')  	    || 'Ã' ||
         NVL(P.DESC_UNID_PRESENT,' ') || 'Ã' ||
         DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(Trunc(CANT_TOMA_INV_PROD/pl.val_frac_local),'99,990')) || 'Ã' ||
         DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(MOD(CANT_TOMA_INV_PROD,pl.val_frac_local),'99,990')) || 'Ã' ||
         L.NOM_LAB || 'Ã' ||
         PL.VAL_FRAC_LOCAL || 'Ã' ||
         NVL(PL.UNID_VTA,' ')
  FROM LGT_TOMA_INV_LAB_PROD TILP,
       LGT_PROD_LOCAL PL,
	     LGT_PROD P,
       LGT_LAB L
  WHERE
       L.COD_LAB=P.COD_LAB AND
  	   TILP.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	     TILP.COD_LOCAL     = cCodLocal_in     AND
	     TILP.SEC_TOMA_INV  = cSecToma_in      AND
       PL.EST_PROD_LOC = ESTADO_ACTIVO       AND
       TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
	     TILP.COD_LOCAL     = PL.COD_LOCAL     AND
	     TILP.COD_PROD      = PL.COD_PROD      AND
	     P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA    AND
	     P.COD_PROD = PL.COD_PROD
       ;

	 RETURN curDia;

 END;
/****************************************************************************/

 FUNCTION TI_LISTA_PROD_LAB_TOMA_CONTEO(cCodGrupoCia_in IN CHAR ,
 		  							                 cCodLocal_in    IN CHAR ,
	                                   cSecToma_in     IN CHAR
                                     )
 RETURN FarmaCursor
 IS
   curDia FarmaCursor;
 BEGIN
	OPEN curDia FOR
  SELECT TILP.COD_PROD      || 'Ã' ||
  		   NVL(P.DESC_PROD,' ')  	    || 'Ã' ||
         NVL(P.DESC_UNID_PRESENT,' ') || 'Ã' ||
         DECODE(CANT_TOMA_INV_PROD_CONTEO,NULL,' ',TO_CHAR(Trunc(CANT_TOMA_INV_PROD_CONTEO/pl.val_frac_local),'99,990')) || 'Ã' ||
         DECODE(CANT_TOMA_INV_PROD_CONTEO,NULL,' ',TO_CHAR(MOD(CANT_TOMA_INV_PROD_CONTEO,pl.val_frac_local),'99,990')) || 'Ã' ||
         L.NOM_LAB || 'Ã' ||
         PL.VAL_FRAC_LOCAL || 'Ã' ||
         NVL(PL.UNID_VTA,' ')
  FROM LGT_TOMA_INV_LAB_PROD TILP,
       LGT_PROD_LOCAL PL,
	     LGT_PROD P,
       LGT_LAB L
  WHERE
       L.COD_LAB=P.COD_LAB AND
  	   TILP.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	     TILP.COD_LOCAL     = cCodLocal_in     AND
	     TILP.SEC_TOMA_INV  = cSecToma_in      AND
       PL.EST_PROD_LOC = ESTADO_ACTIVO       AND
       TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
	     TILP.COD_LOCAL     = PL.COD_LOCAL     AND
	     TILP.COD_PROD      = PL.COD_PROD      AND
	     P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA    AND
	     P.COD_PROD = PL.COD_PROD              AND
       TILP.COD_PROD IN (
                            SELECT  TILP.COD_PROD
                            FROM
                                LGT_TOMA_INV_LAB_PROD TILP
                            WHERE   TILP.COD_GRUPO_CIA = cCodGrupoCia_in AND
                    	   	  TILP.COD_LOCAL     		  = cCodLocal_in     AND
                    	   	  TILP.SEC_TOMA_INV  		  = cSecToma_in   AND
                            TILP.CANT_TOMA_INV_PROD IS NOT NULL     AND
                    	   	  (NVL(TILP.CANT_TOMA_INV_PROD,0)-(

                                select stk_final_prod
                                from LGT_KARDEX LK
                                where LK.COD_GRUPO_CIA = cCodGrupoCia_in AND
                                      LK.COD_LOCAL = cCodLocal_in     AND
                                      LK.sec_kardex in (
                                      SELECT max(sec_kardex)
                                      FROM LGT_KARDEX K
                                      WHERE K.COD_GRUPO_CIA = cCodGrupoCia_in AND
                                            K.COD_LOCAL = cCodLocal_in     AND
                                            K.COD_PROD = TILP.COD_PROD     AND
                                            TILP.HORA > K.Fec_Crea_Kardex
                                      )
                            )

                            ) <> 0
                        )

       ;

	 RETURN curDia;

 END;
/****************************************************************************/

  PROCEDURE TI_INGRESA_CANT_PROD_TI(cCodGrupoCia_in IN CHAR ,
  									                cCodLocal_in    IN CHAR ,
									                  cSecToma_in     IN CHAR ,
									                  cCodLab_in      IN CHAR ,
									                  cCodProd_in     IN CHAR ,
									                  cCantToma_in    IN CHAR ,
                                    cHora_in    IN CHAR)
  IS
  BEGIN
    UPDATE  LGT_TOMA_INV_LAB_PROD SET  FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,
    		    CANT_TOMA_INV_PROD    = cCantToma_in,
    	  	  EST_TOMA_INV_LAB_PROD = EST_TOMA_INV_PROCESO,
            HORA= to_date((to_char(sysdate ,'dd/MM/yyyy')) || cHora_in ,'dd/MM/yyyy HH24:MI')
    WHERE   COD_GRUPO_CIA = cCodGrupoCia_in AND
  		      COD_LOCAL     = cCodLocal_in    AND
  		      SEC_TOMA_INV  = cSecToma_in 	AND
  		      --COD_LAB       = cCodLab_in 		AND
  		      COD_PROD      = cCodProd_in;
  END;
/****************************************************************************/

  PROCEDURE TI_INGRESA_CANT_PROD_TI_CONTEO(cCodGrupoCia_in IN CHAR ,
  									                cCodLocal_in    IN CHAR ,
									                  cSecToma_in     IN CHAR ,
									                  cCodLab_in      IN CHAR ,
									                  cCodProd_in     IN CHAR ,
									                  cCantToma_in    IN CHAR ,
                                    cHora_in        IN CHAR ,
                                    cUsuarioMod     IN CHAR
                                    )
  IS
  BEGIN
    UPDATE  LGT_TOMA_INV_LAB_PROD SET

            FEC_CONTEO_TOMA_INV_LAB_PROD = SYSDATE,
            USU_CONTEO_TOMA_INV_LAB_PROD = cUsuarioMod,
    		    CANT_TOMA_INV_PROD_CONTEO    = cCantToma_in,
            EST_TOMA_INV_LAB_PROD = EST_TOMA_INV_PROCESO,
            HORA_CONTEO= to_date((to_char(sysdate ,'dd/MM/yyyy')) || cHora_in ,'dd/MM/yyyy HH24:MI')

    WHERE   COD_GRUPO_CIA = cCodGrupoCia_in AND
  		      COD_LOCAL     = cCodLocal_in    AND
  		      SEC_TOMA_INV  = cSecToma_in 	AND
  		      --COD_LAB       = cCodLab_in 		AND
  		      COD_PROD      = cCodProd_in;
  END;
/****************************************************************************/


 FUNCTION TI_LISTA_MOVS_KARDEX(cCodGrupoCia_in IN CHAR ,
  		   					             cCodLocal_in    IN CHAR ,
								               cCod_Prod_in	   IN CHAR)
 RETURN FarmaCursor
 IS
   curDia FarmaCursor;
 BEGIN
	OPEN curDia FOR
       	 SELECT TO_CHAR(K.FEC_KARDEX,'dd/MM/yy HH24:mi:ss')|| 'Ã' ||
         	   		NVL(MK.DESC_CORTA_MOT_KARDEX,' ')	|| 'Ã' ||
                CASE k.tip_comp_pago
                WHEN '01' THEN 'BOLETA'
                WHEN '02' THEN 'FACTURA'
                WHEN '03' THEN 'GUIA'
                WHEN '04' THEN 'NOTA CREDITO'
                WHEN '05' THEN 'ENTREGA'
                ELSE NVL(DECODE(K.TIP_DOC_KARDEX,'01','VENTA','02','GUIA ENTRADA/SALIDA','03','AJUSTE DE INVENTARIO'),' ')
                END|| 'Ã' ||
	   		        DECODE(K.NUM_COMP_PAGO,NULL,NVL(K.NUM_TIP_DOC,' '),K.NUM_COMP_PAGO)|| 'Ã' ||
	   		        NVL(K.CANT_MOV_PROD,0)|| 'Ã' ||
	   		        NVL(K.VAL_FRACC_PROD,0)
	       FROM   LGT_KARDEX K,
	  	          LGT_MOT_KARDEX MK
	       WHERE  K.COD_GRUPO_CIA  = ccodgrupocia_in	  AND
	  		        K.COD_LOCAL      = ccodlocal_in		  AND
			          K.COD_PROD		 = ccod_prod_in		  AND
			          K.FEC_KARDEX BETWEEN TO_DATE(TO_CHAR(SYSDATE, 'dd/MM/yyyy') || '00:00:00','dd/MM/yyyy HH24:MI:SS') AND (SYSDATE) AND
			          K.COD_GRUPO_CIA  = MK.COD_GRUPO_CIA	  AND
	  		        K.COD_MOT_KARDEX = MK.COD_MOT_KARDEX ;
   RETURN curDia;
  END ;
/****************************************************************************/

  FUNCTION TI_TOTAL_ITEMS_TOMA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cSecToma_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
  BEGIN
        OPEN curTi FOR
          SELECT to_char(NVL(v1.total,0),'999,990') || 'Ã' ||
                 to_char(NVL(v2.tomados,0),'999,990')
          FROM   (SELECT   COUNT(*) total
                  FROM     lgt_toma_inv_lab_prod
                  WHERE    cod_grupo_cia = ccodgrupocia_in
                  AND      cod_local = ccodlocal_in
                  AND      sec_toma_inv = cSecToma_in )v1,
                 (SELECT   COUNT(*) tomados
                  FROM     lgt_toma_inv_lab_prod
                  WHERE    cod_grupo_cia = ccodgrupocia_in
                  AND      cod_local = ccodlocal_in
                  AND      sec_toma_inv = cSecToma_in
                  AND      CANT_TOMA_INV_PROD IS NOT NULL)v2;
  RETURN curTi;

  END;
/****************************************************************************/

  FUNCTION TI_INFORMACION_VALORIZADA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
  BEGIN
        OPEN curTi FOR
          SELECT TO_CHAR(SUM(NVL(V2.FALTA,0)),'999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(NVL(V1.SOBRA,0)),'999,999,990.00')
          FROM   LGT_TOMA_INV_LAB_PROD LP,
                 LGT_PROD P,
                 (SELECT LP.COD_PROD COD,
                         (((LP.STK_ANTERIOR-LP.CANT_TOMA_INV_PROD) / NVL(LP.VAL_FRAC,1)) * P.Val_Prec_Vta*lp.val_frac) FALTA
                  FROM   LGT_TOMA_INV_LAB_PROD LP,
                         LGT_PROD_local P
                  WHERE  LP.COD_GRUPO_CIA = ccodgrupocia_in
                  AND    LP.COD_LOCAL = ccodlocal_in
                  AND    LP.sec_toma_inv = csectoma_in
                  AND    LP.CANT_TOMA_INV_PROD < LP.STK_ANTERIOR
                  AND    LP.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                  AND    lp.cod_local = p.cod_local
                  AND    LP.COD_PROD = P.COD_PROD)V2, --FALTANTE
                 (SELECT LP.COD_PROD COD,
                         (((LP.CANT_TOMA_INV_PROD-LP.STK_ANTERIOR) / NVL(LP.VAL_FRAC,1)) * P.Val_Prec_Vta*lp.val_frac) SOBRA
                  FROM   LGT_TOMA_INV_LAB_PROD LP,
                         LGT_PROD_local P
                  WHERE  LP.COD_GRUPO_CIA = ccodgrupocia_in
                  AND    LP.COD_LOCAL = ccodlocal_in
                  AND    LP.sec_toma_inv = csectoma_in
                  AND    LP.CANT_TOMA_INV_PROD > LP.STK_ANTERIOR
                  AND    LP.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                  AND    lp.cod_local = p.cod_local
                  AND    LP.COD_PROD = P.COD_PROD)V1-- SOBRANTE)
          WHERE  LP.COD_GRUPO_CIA = ccodgrupocia_in
          AND    LP.COD_LOCAL = ccodlocal_in
          AND    LP.sec_toma_inv =csectoma_in
          AND    LP.COD_PROD = V1.COD(+)
          AND    LP.COD_PROD = V2.COD(+)
          AND    LP.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND    LP.COD_PROD = P.COD_PROD;
    RETURN curTi;
  END;
/****************************************************************************/

  FUNCTION TI_DIFERENCIAS_CONSOLIDADO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecTomaInv_in  IN CHAR)
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
  BEGIN

        OPEN curTi FOR

 SELECT           TILP.COD_PROD  || 'Ã' ||
           		    NVL(P.DESC_PROD,' ')  || 'Ã' ||
          	      p.desc_unid_present  || 'Ã' ||
          	      TRUNC(V.STOCK_ANTERIOR/PL.VAL_FRAC_LOCAL) ||' / '|| DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD(V.STOCK_ANTERIOR,PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          	      TRUNC((NVL(CANT_TOMA_INV_PROD_CONTEO,CANT_TOMA_INV_PROD)-V.STOCK_ANTERIOR)/PL.VAL_FRAC_LOCAL) ||' / '||  DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD((NVL(CANT_TOMA_INV_PROD_CONTEO,CANT_TOMA_INV_PROD)-V.STOCK_ANTERIOR),PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          	      TO_CHAR((NVL(CANT_TOMA_INV_PROD_CONTEO,CANT_TOMA_INV_PROD)-V.STOCK_ANTERIOR) * PL.VAL_PREC_VTA,'999,990.00') || 'Ã' ||
                  LAB.COD_LAB || '- ' || LAB.NOM_LAB || 'Ã' ||
                  PL.VAL_FRAC_LOCAL

          FROM    LGT_TOMA_INV_LAB_PROD TILP,
                  LGT_PROD_LOCAL PL,
          		    LGT_PROD p,
                  LGT_LAB LAB,

                  (
                    select K.stk_final_prod STOCK_ANTERIOR,T.COD_PROD COD_PROD,T.HORA_CONTEO
                    from   LGT_KARDEX K,
                           LGT_TOMA_INV_LAB_PROD T
                    where  T.COD_GRUPO_CIA 		  = cCodGrupoCia_in   AND
          	   	           T.COD_LOCAL     		  = cCodLocal_in      AND
          	   	           T.SEC_TOMA_INV  		  = cSecTomaInv_in    AND
                           K.COD_GRUPO_CIA      = T.COD_GRUPO_CIA   AND
                           K.COD_LOCAL          = T.COD_LOCAL       AND
                           t.cod_prod           = k.cod_prod       AND

                           K.sec_kardex = (
                            SELECT max(X.sec_kardex)
                            FROM LGT_KARDEX X
                            WHERE K.COD_GRUPO_CIA = cCodGrupoCia_in AND
                                  K.COD_LOCAL = cCodLocal_in        AND
                                  T.COD_PROD=X.COD_PROD             AND
                                  T.HORA_CONTEO > X.Fec_Crea_Kardex
                          )
                   ) V

          WHERE   TILP.COD_GRUPO_CIA 		  = cCodGrupoCia_in AND
          	   	  TILP.COD_LOCAL     		  = cCodLocal_in    AND
          	   	  TILP.SEC_TOMA_INV  		  = cSecTomaInv_in  AND
                  V.COD_PROD              = TILP.COD_PROD AND
                  nvl(TILP.CANT_TOMA_INV_PROD_CONTEO,TILP.CANT_TOMA_INV_PROD) IS NOT NULL        AND
          	   	  (

                   NVL(CANT_TOMA_INV_PROD_CONTEO,TILP.CANT_TOMA_INV_PROD)- V.STOCK_ANTERIOR

                  ) <> 0 AND
          	   	  TILP.COD_GRUPO_CIA           = PL.COD_GRUPO_CIA AND
          	   	  TILP.COD_LOCAL     		  = PL.COD_LOCAL     AND
          	   	  TILP.COD_PROD      		  = PL.COD_PROD AND
               	  P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                  P.COD_PROD = PL.COD_PROD AND
                  P.COD_LAB = LAB.COD_LAB ;




    RETURN curTi;
  END;
/****************************************************************************/


  FUNCTION TI_DIFERENCIAS_CONTEO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecTomaInv_in  IN CHAR)
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
  BEGIN
        OPEN curTi FOR
          SELECT  TILP.COD_PROD  || 'Ã' ||
           		    NVL(P.DESC_PROD,' ')  || 'Ã' ||
          	      p.desc_unid_present  || 'Ã' ||
          	      --TRUNC(STK_ANTERIOR/PL.VAL_FRAC_LOCAL) ||' / '|| DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD(STK_ANTERIOR,PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          	      --TRUNC((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR)/PL.VAL_FRAC_LOCAL) ||' / '||  DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR),PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          	      --TO_CHAR((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) * PL.VAL_PREC_VTA,'999,990.00') || 'Ã' ||
                  LAB.COD_LAB || '- ' || LAB.NOM_LAB
          FROM    LGT_TOMA_INV_LAB_PROD TILP,
                  LGT_PROD_LOCAL PL,
          		    LGT_PROD p,
                  LGT_LAB LAB
          WHERE   TILP.COD_GRUPO_CIA 		  = ccodgrupocia_in AND
          	   	  TILP.COD_LOCAL     		  = ccodlocal_in     AND
          	   	  TILP.SEC_TOMA_INV  		  = csectomainv_in   AND
                  TILP.CANT_TOMA_INV_PROD IS NOT NULL        AND
          	   	  (NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) <> 0 AND
          	   	  TILP.COD_GRUPO_CIA           = PL.COD_GRUPO_CIA AND
          	   	  TILP.COD_LOCAL     		  = PL.COD_LOCAL     AND
          	   	  TILP.COD_PROD      		  = PL.COD_PROD AND
               	  P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                  P.COD_PROD = PL.COD_PROD AND
                  P.COD_LAB = LAB.COD_LAB ;
    RETURN curTi;
  END;
/****************************************************************************/


  FUNCTION TI_DIFERENCIAS_HISTORICO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR )
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
  BEGIN
        OPEN curTi FOR
          SELECT  TILP.COD_PROD  || 'Ã' ||
           		    NVL(P.DESC_PROD,' ')  || 'Ã' ||
          	      p.desc_unid_present  || 'Ã' ||
          	      TRUNC(STK_ANTERIOR/PL.VAL_FRAC_LOCAL) ||' / '|| DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD(STK_ANTERIOR,PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          	      TRUNC((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR)/PL.VAL_FRAC_LOCAL) ||' / '||  DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR),PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          	      TO_CHAR((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) * PL.VAL_PREC_VTA,'999,990.00') || 'Ã' ||
                  LAB.COD_LAB || '- ' || LAB.NOM_LAB  || 'Ã' ||
                  C.FEC_CREA_TOMA_INV
          FROM    LGT_TOMA_INV_LAB_PROD TILP,
                  LGT_PROD_LOCAL PL,
          		    LGT_PROD p,
                  LGT_LAB LAB,
                  lgt_toma_inv_cab C
          WHERE   TILP.COD_GRUPO_CIA 		  = ccodgrupocia_in AND
          	   	  TILP.COD_LOCAL     		  = ccodlocal_in     AND
          	   	  --TILP.SEC_TOMA_INV  		  = csectomainv_in   AND
                  TILP.CANT_TOMA_INV_PROD IS NOT NULL        AND
          	   	  (NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) <> 0 AND
          	   	  TILP.COD_GRUPO_CIA           = PL.COD_GRUPO_CIA AND
          	   	  TILP.COD_LOCAL     		  = PL.COD_LOCAL     AND
          	   	  TILP.COD_PROD      		  = PL.COD_PROD AND
               	  P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                  P.COD_PROD = PL.COD_PROD AND
                  P.COD_LAB = LAB.COD_LAB AND
                  C.TIP_TOMA_INV='D' AND
                  C.EST_TOMA_INV='E' AND
                  C.SEC_TOMA_INV=TILP.SEC_TOMA_INV;
    RETURN curTi;
  END;
/****************************************************************************/


  FUNCTION TI_DIF_CONSOLIDADO_FILTRO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecTomaInv_in  IN CHAR,
                                     cCodLab_in      IN CHAR)
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
  BEGIN
        OPEN curTi FOR
          SELECT  TILP.COD_PROD  || 'Ã' ||
           		    NVL(P.DESC_PROD,' ')  || 'Ã' ||
          	      p.desc_unid_present  || 'Ã' ||
          	      TRUNC(STK_ANTERIOR/PL.VAL_FRAC_LOCAL) ||' / '|| DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD(STK_ANTERIOR,PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          	      TRUNC((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR)/PL.VAL_FRAC_LOCAL) ||' / '||  DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR),PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          	      TO_CHAR((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) * PL.VAL_PREC_VTA,'999,990.00') || 'Ã' ||
                  LAB.COD_LAB || '- ' || LAB.NOM_LAB
          FROM    LGT_TOMA_INV_LAB_PROD TILP,
                  LGT_PROD_LOCAL PL,
          		    LGT_PROD p,
                  LGT_LAB LAB
          WHERE   TILP.COD_GRUPO_CIA 		  = ccodgrupocia_in AND
          	   	  TILP.COD_LOCAL     		  = ccodlocal_in     AND
          	   	  TILP.SEC_TOMA_INV  		  = csectomainv_in   AND
                  TILP.COD_LAB       		  = cCodLab_in       AND
                  TILP.CANT_TOMA_INV_PROD IS NOT NULL        AND
          	   	  (NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) <> 0 AND
          	   	  TILP.COD_GRUPO_CIA           = PL.COD_GRUPO_CIA AND
          	   	  TILP.COD_LOCAL     		  = PL.COD_LOCAL     AND
          	   	  TILP.COD_PROD      		  = PL.COD_PROD AND
               	  P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                  P.COD_PROD = PL.COD_PROD AND
                  P.COD_LAB = LAB.COD_LAB ;
    RETURN curTi;
  END;
/****************************************************************************/

  PROCEDURE TI_CARGA_TOMA_INV(cCodGrupoCia_in IN CHAR ,
   			 				               cCodLocal_in   IN CHAR ,
							                 cSecToma_in    IN CHAR ,
							                 cIdUsu_in      IN CHAR)
   IS

      CURSOR curProds
   IS
   	    SELECT TILP.COD_GRUPO_CIA,
	   		      TILP.COD_LOCAL,
		      	  TILP.COD_PROD,
			        TILP.CANT_TOMA_INV_PROD_CONTEO-V.STOCK_ANTERIOR Cantidad_Prod,
			        V.STOCK_ANTERIOR,
              V.STOCK_final Stk_final,
			        PL.VAL_FRAC_LOCAL,
			        PL.UNID_VTA ,
              P.VAL_PREC_PROM
	     FROM   LGT_TOMA_INV_LAB_PROD TILP,
              LGT_PROD       P,
	   		      LGT_PROD_LOCAL PL ,
              (
                    select K.Stk_Anterior_Prod STOCK_ANTERIOR,K.Stk_Final_Prod STOCK_final,T.COD_PROD COD_PROD
                    from   LGT_KARDEX K,
                           LGT_TOMA_INV_LAB_PROD T
                    where  T.COD_GRUPO_CIA 		  = cCodGrupoCia_in     AND
          	   	           T.COD_LOCAL     		  = cCodLocal_in        AND
          	   	           T.SEC_TOMA_INV  		  = cSecToma_in         AND
                           T.COD_GRUPO_CIA      = K.COD_GRUPO_CIA     AND
                           T.COD_LOCAL          = K.COD_LOCAL         AND
                           t.cod_prod           =  k.cod_prod         AND

                           K.sec_kardex = (
                            SELECT max(X.sec_kardex)
                            FROM LGT_KARDEX X
                            WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in     AND
                                  X.COD_LOCAL     = cCodLocal_in        AND
                                  T.COD_PROD=X.COD_PROD AND
                                  T.HORA_CONTEO > X.Fec_Crea_Kardex
                          )
               ) V

 	     WHERE  TILP.COD_GRUPO_CIA = cCodGrupoCia_in   AND
	            TILP.COD_LOCAL     = cCodLocal_in      AND
			        TILP.SEC_TOMA_INV  = cSecToma_in		   AND
              TILP.IND_DIFENCIA  = 'S'		           AND
              V.COD_PROD              = TILP.COD_PROD AND
              TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA  AND
	   		      TILP.COD_LOCAL     = PL.COD_LOCAL 	   AND
	            TILP.COD_PROD      = PL.COD_PROD  	   AND
              TILP.COD_GRUPO_CIA  = P.COD_GRUPO_CIA  AND
              TILP.COD_PROD      = P.COD_PROD
        order by Cantidad_Prod desc;


  regLab curProds%ROWTYPE;
  vCantMovProd NUMBER;
  vCodMotKardex CHAR(3);


   BEGIN

    OPEN curProds;
     LOOP
	   FETCH curProds INTO regLab;
	   EXIT WHEN curProds%NOTFOUND;

	--Actualiza el stock de cada producto
  	UPDATE LGT_PROD_LOCAL SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = cIdUsu_in,
		       STK_FISICO    = STK_FISICO + regLab.Cantidad_Prod
	  WHERE  COD_GRUPO_CIA = regLab.COD_GRUPO_CIA      AND
		       COD_LOCAL     = regLab.COD_LOCAL          AND
		       COD_PROD      = regLab.COD_PROD;

  --Actualiza el VAL_PREC_PROM
     UPDATE LGT_TOMA_INV_LAB_PROD P
            SET    P.VAL_PREC_PROM =regLab.Val_Prec_Prom
     WHERE   P.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    P.COD_LOCAL     = cCodLocal_in
      AND    P.SEC_TOMA_INV  = P.SEC_TOMA_INV
      AND    P.COD_PROD      = regLab.Cod_Prod ;

 --Guarda un registro del movimiento en el Kardex
  vCantMovProd:=regLab.Cantidad_Prod;

  IF vCantMovProd<>0 THEN
  	 BEGIN
	 	    IF vCantMovProd > 0 THEN
  	 	  	 vCodMotKardex:=COD_MOT_KARDEX_VIRTUAL_POS;
  		  END IF;

  		  IF vCantMovProd < 0 THEN
  	 	  	 vCodMotKardex:=COD_MOT_KARDEX_VIRTUAL_NEG;
  		  END IF;

        --IF (regLab.Stk_final + vCantMovProd) > 0 THEN
  		  Ptoventa_Inv.INV_GRABAR_KARDEX(
                       cCodGrupoCia_in,
                       cCodLocal_in,
							     		 regLab.COD_PROD,
							     		 vCodMotKardex,
						   	     	 TIP_DOC_KARDEX_TOMA_INV,
						   	     	 cSecToma_in,
							     		 regLab.Stk_final,
							     		 vCantMovProd,
							     		 regLab.VAL_FRAC_LOCAL,
							     		 regLab.UNID_VTA,
							     		 cIdUsu_in,
							     		 COD_NUMERA_KARDEX);
        --END IF;

	  END;
  END IF;

  END LOOP;
	CLOSE curProds;

  TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in ,
							  cCodLocal_in ,
							  cSecToma_in ,
							  EST_TOMA_INV_CARGADO,
							  cIdUsu_in);

	END;
/****************************************************************************/

  PROCEDURE TI_CARGA_CRUCES_INV(cCodGrupoCia_in IN CHAR ,
   			 				               cCodLocal_in   IN CHAR ,
							                 cSecToma_in    IN CHAR ,
							                 cIdUsu_in      IN CHAR)
   IS

   CURSOR curProds
   IS

        --ajustes por diferencia

         	   SELECT TILP.COD_GRUPO_CIA,
      	   		      TILP.COD_LOCAL,
      		      	  TILP.COD_PROD,
      			        nvl(TILP.CANT_TOMA_INV_PROD_CONTEO,TILP.CANT_TOMA_INV_PROD)-V.STOCK_final Cantidad_Prod,
      			        --V.STOCK_ANTERIOR,
                    V.STOCK_final Stk_final,
      			        PL.VAL_FRAC_LOCAL,
      			        PL.UNID_VTA ,
                    P.VAL_PREC_PROM,
                    sign(nvl(TILP.CANT_TOMA_INV_PROD_CONTEO,TILP.CANT_TOMA_INV_PROD)-V.STOCK_final ) tipo_ajuste
                    ,'Diferencias' tipo_cruce_ajuste
      	     FROM   LGT_TOMA_INV_LAB_PROD TILP,
                    LGT_PROD       P,
      	   		      LGT_PROD_LOCAL PL ,
                    (

                         /*
                          select K.Stk_Anterior_Prod STOCK_ANTERIOR,K.Stk_Final_Prod STOCK_final,T.COD_PROD COD_PROD
                          from   LGT_KARDEX K,
                                 LGT_TOMA_INV_LAB_PROD T
                          where  T.COD_GRUPO_CIA 		  = cCodGrupoCia_in    AND
                	   	           T.COD_LOCAL     		  = cCodLocal_in       AND
                	   	           T.SEC_TOMA_INV  		  = cSecToma_in        AND
                                 T.COD_GRUPO_CIA      = K.COD_GRUPO_CIA    AND
                                 T.COD_LOCAL          = K.COD_LOCAL        AND
                                 t.cod_prod           =  k.cod_prod        AND

                                 K.sec_kardex = (
                                  SELECT max(X.sec_kardex)
                                  FROM LGT_KARDEX X
                                  WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in  AND
                                        X.COD_LOCAL = cCodLocal_in         AND
                                        T.COD_PROD=X.COD_PROD              AND
                                        T.HORA_CONTEO > X.Fec_Crea_Kardex
                                )
                         */

                        select cod_prod, stk_final STOCK_final
                          from (select cod_prod,
                                       stk_final,
                                       rank() over(partition by cod_prod order by fec_stock desc) rango
                                  from (select ttt.cod_prod,
                                               ttt.fec_crea_toma_inv_lab_prod fec_stock,
                                               ttt.stk_anterior * ttt.val_frac / ttt.val_frac stk_final
                                          from LGT_TOMA_INV_LAB_PROD ttt
                                         where Ttt.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND Ttt.COD_LOCAL = cCodLocal_in
                                           AND Ttt.SEC_TOMA_INV = cSecToma_in
                                           and ttt.ind_difencia = 'S'
                                        union
                                        select kk.cod_prod,
                                               kk.fec_kardex fec_stock,
                                               kk.stk_final_prod * ttt.val_frac / kk.val_fracc_prod
                                          from lgt_kardex kk, LGT_TOMA_INV_LAB_PROD ttt
                                         where ttt.cod_grupo_cia = kk.cod_grupo_cia
                                           and ttt.cod_local = kk.cod_local
                                           and ttt.cod_prod = kk.cod_prod
                                           and kk.fec_kardex > fec_crea_toma_inv_lab_prod
                                           and ttt.ind_difencia = 'S'
                                           and kk.fec_kardex < ttt.hora_conteo
                                           and Ttt.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND Ttt.COD_LOCAL = cCodLocal_in
                                           AND Ttt.SEC_TOMA_INV = cSecToma_in))
                         where rango = 1

                     ) V

       	     WHERE  TILP.COD_GRUPO_CIA = cCodGrupoCia_in   AND
      	            TILP.COD_LOCAL     = cCodLocal_in      AND
      			        TILP.SEC_TOMA_INV  = cSecToma_in		   AND
                    TILP.IND_DIFENCIA  = 'S'		           AND
                    V.COD_PROD              = TILP.COD_PROD AND
                    TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA  AND
      	   		      TILP.COD_LOCAL     = PL.COD_LOCAL 	   AND
      	            TILP.COD_PROD      = PL.COD_PROD  	   AND
                    TILP.COD_GRUPO_CIA  = P.COD_GRUPO_CIA  AND
                    TILP.COD_PROD      = P.COD_PROD

            union

            SELECT  TILPC.COD_GRUPO_CIA,
                    TILPC.COD_LOCAL,
                    TILPC.COD_PROD_CRUCE,
                    --TILPC.CANTIDAD_PROD_CRUCE*TILPC.ACCION_CRUCE  Cantidad_Prod,
                    TILPC.CANTIDAD_PROD_CRUCE*TILPC.ACCION_CRUCE*PL.VAL_FRAC_LOCAL/p.val_max_frac Cantidad_Prod,
                    --V.STOCK_ANTERIOR   Stk_Fisico,
                    V.STOCK_final Stk_final,
            			  PL.VAL_FRAC_LOCAL,
            			  PL.UNID_VTA ,
                    P.VAL_PREC_PROM,
                    sign(TILPC.CANTIDAD_PROD_CRUCE*TILPC.ACCION_CRUCE*PL.VAL_FRAC_LOCAL/p.val_max_frac) tipo_ajuste
                    ,'CRUCES' tipo_cruce_ajuste
            FROM   LGT_AUX_INV_DIA_CRUCE  TILPC,
                   LGT_PROD_LOCAL PL ,
                   LGT_PROD       P ,

                   (
                    select K.Stk_Anterior_Prod STOCK_ANTERIOR,K.Stk_Final_Prod STOCK_final,T.COD_PROD COD_PROD
                    from   LGT_KARDEX K,
                           LGT_TOMA_INV_LAB_PROD T
                    where  T.COD_GRUPO_CIA 		  = cCodGrupoCia_in AND
          	   	           T.COD_LOCAL     		  = cCodLocal_in    AND
          	   	           T.SEC_TOMA_INV  		  = cSecToma_in     AND
                           t.cod_prod           =  k.cod_prod     AND
                           K.COD_GRUPO_CIA = T.COD_GRUPO_CIA      AND
                           K.COD_LOCAL = T.COD_LOCAL              AND

                           K.sec_kardex = (
                            SELECT max(X.sec_kardex)
                            FROM LGT_KARDEX X
                            WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in AND
                                  X.COD_LOCAL     = cCodLocal_in    AND
                                  T.COD_PROD=X.COD_PROD --AND
                                  --T.HORA_CONTEO > X.Fec_Crea_Kardex
                          )
                   ) V

            WHERE  TILPC.COD_GRUPO_CIA = cCodGrupoCia_in   AND
            	     TILPC.COD_LOCAL     = cCodLocal_in     AND
            			 TILPC.SEC_TOMA_INV  = cSecToma_in		   AND
                   V.COD_PROD          = TILPC.COD_PROD   AND
                          TILPC.COD_GRUPO_CIA = PL.COD_GRUPO_CIA  AND
            	   		      TILPC.COD_LOCAL     = PL.COD_LOCAL 	   AND
            	            TILPC.Cod_Prod_Cruce      = PL.COD_PROD  	   AND
                          TILPC.COD_GRUPO_CIA  = P.COD_GRUPO_CIA  AND
                          TILPC.Cod_Prod_Cruce      = P.COD_PROD
           order by  tipo_ajuste DESC,tipo_cruce_ajuste ASC ,Cantidad_Prod desc ;

  regLab curProds%ROWTYPE;
  vCantMovProd NUMBER;
  vCantProd NUMBER;
  vCodMotKardex CHAR(3);
  vStock_Fisico CHAR(6);
  vStock_Comp NUMBER;

  BEGIN

   --VERIFICA PRODUCTOS DE LA TOMA CON STOCK COMPROMETIDO

        select count(*)
        into vStock_Comp
        from lgt_prod_local l, LGT_TOMA_INV_LAB_PROD D
        where D.COD_GRUPO_CIA=regLab.Cod_Grupo_Cia and
              D.COD_LOCAL = regLab.Cod_Local and
              D.SEC_TOMA_INV  = cSecToma_in and
              d.cod_prod=l.cod_prod;

     vStock_Comp := 0;
  IF vStock_Comp=0  THEN

    OPEN curProds;
     LOOP
	   FETCH curProds INTO regLab;
	   EXIT WHEN curProds%NOTFOUND;

     DBMS_OUTPUT.PUT_LINE('INGRESA A LOOP: ');

    vCantMovProd:=regLab.Cantidad_Prod;

    IF vCantMovProd<>0 THEN
  	 BEGIN

	  --Actualiza el stock de cada producto

     select l.stk_fisico
        into vStock_Fisico
        from lgt_prod_local l
        where l.cod_grupo_cia=regLab.Cod_Grupo_Cia and
              l.cod_local= regLab.Cod_Local and
              l.cod_prod= regLab.Cod_Prod;

    DBMS_OUTPUT.PUT_LINE('INVENTARIO DIARIO NUEVO: tipo...' ||regLab.tipo_cruce_ajuste);
    DBMS_OUTPUT.PUT_LINE('INVENTARIO DIARIO NUEVO:toma '||cSecToma_in);
    DBMS_OUTPUT.PUT_LINE('INVENTARIO DIARIO NUEVO:stock fisico '||vStock_Fisico);
    DBMS_OUTPUT.PUT_LINE('INVENTARIO DIARIO NUEVO:prod '||regLab.COD_PROD);
    DBMS_OUTPUT.PUT_LINE('INVENTARIO DIARIO NUEVO:cantidad a mover '||regLab.Cantidad_Prod);

  	UPDATE LGT_PROD_LOCAL SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = cIdUsu_in,
		       STK_FISICO    = STK_FISICO+regLab.Cantidad_Prod
	  WHERE  COD_GRUPO_CIA = regLab.COD_GRUPO_CIA      AND
		       COD_LOCAL     = regLab.COD_LOCAL          AND
		       COD_PROD      = regLab.COD_PROD;

    -- Actualiza el VAL_PREC_PROM
     UPDATE LGT_TOMA_INV_LAB_PROD P
            SET    P.VAL_PREC_PROM =regLab.Val_Prec_Prom
     WHERE   P.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    P.COD_LOCAL     = cCodLocal_in
      AND    P.SEC_TOMA_INV  = P.SEC_TOMA_INV
      AND    P.COD_PROD      = regLab.Cod_Prod ;

    -- Guarda la diferencia encontrada
     UPDATE LGT_TOMA_INV_LAB_PROD P
            SET    P.Cant_Prod_Diferencia = vCantMovProd
     WHERE   P.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    P.COD_LOCAL     = cCodLocal_in
      AND    P.SEC_TOMA_INV  = P.SEC_TOMA_INV
      AND    P.COD_PROD      = regLab.Cod_Prod ;

    -- Guarda la CANTIDAD FINAL DEL PRODUCTO encontrada
     UPDATE LGT_TOMA_INV_LAB_PROD P
            SET    P.CANT_PROD_STK_ANTERIOR = regLab.Stk_final
     WHERE   P.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    P.COD_LOCAL     = cCodLocal_in
      AND    P.SEC_TOMA_INV  = P.SEC_TOMA_INV
      AND    P.COD_PROD      = regLab.Cod_Prod ;

    --Guarda un registro del movimiento en el Kardex

    IF regLab.tipo_cruce_ajuste= 'CRUCES' THEN

        UPDATE LGT_TOMA_INV_LAB_PROD CR
            SET    CR.Cant_Prod_Diferencia =regLab.Cantidad_Prod,
                   CR.Cant_Prod_Stk_Anterior =vStock_Fisico--,
                   ---CR.Cant_Toma_Inv_Prod = regLab.Cantidad_Prod
        WHERE   CR.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CR.COD_LOCAL     = cCodLocal_in
        AND    CR.SEC_TOMA_INV  = CR.SEC_TOMA_INV
        AND    CR.COD_PROD      = regLab.Cod_Prod ;

        if regLab.Cantidad_Prod <0 then
        UPDATE LGT_TOMA_INV_LAB_PROD CR
            SET    CR.Cant_Toma_Inv_Prod = regLab.Cantidad_Prod * -1
        WHERE   CR.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CR.COD_LOCAL     = cCodLocal_in
        AND    CR.SEC_TOMA_INV  = CR.SEC_TOMA_INV
        AND    CR.COD_PROD      = regLab.Cod_Prod ;
        else

        UPDATE LGT_TOMA_INV_LAB_PROD CR
            SET    CR.Cant_Toma_Inv_Prod = regLab.Cantidad_Prod
        WHERE   CR.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CR.COD_LOCAL     = cCodLocal_in
        AND    CR.SEC_TOMA_INV  = CR.SEC_TOMA_INV
        AND    CR.COD_PROD      = regLab.Cod_Prod ;

        end if;


    /*    UPDATE LGT_TOMA_INV_LAB_PROD CR
            SET    CR.Cant_Prod_Stk_Anterior =vStock_Fisico
        WHERE   CR.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CR.COD_LOCAL     = cCodLocal_in
        AND    CR.SEC_TOMA_INV  = CR.SEC_TOMA_INV
        AND    CR.COD_PROD      = regLab.Cod_Prod ;
            */
        IF regLab.Cantidad_Prod >0 THEN
  	 	  	 vCodMotKardex:=COD_MOT_KARDEX_CARGA_NEG;
           --vCantMovProd:=vCantMovProd*(-1);
  		  END IF;

  		  IF regLab.Cantidad_Prod <0 THEN
  	 	  	 vCodMotKardex:=COD_MOT_KARDEX_CARGA_POS;
           --vCantMovProd:=vCantMovProd*(-1);
        END IF;

    ELSE

	 	    IF regLab.Cantidad_Prod >0 THEN
  	 	  	 vCodMotKardex:=COD_MOT_KARDEX_VIRTUAL_POS;
           vCantMovProd:=vCantMovProd;
  		  END IF;

  		  IF regLab.Cantidad_Prod <0 THEN
  	 	  	 vCodMotKardex:=COD_MOT_KARDEX_VIRTUAL_NEG;
           vCantMovProd:=vCantMovProd;
  		  END IF;

    END IF;

  		  Ptoventa_Inv.INV_GRABAR_KARDEX(
                       cCodGrupoCia_in,
                       cCodLocal_in,
							     		 regLab.COD_PROD,
							     		 vCodMotKardex,
						   	     	 TIP_DOC_KARDEX_TOMA_INV,
						   	     	 cSecToma_in,
							     		 vStock_Fisico,
							     		 vCantMovProd,
							     		 regLab.VAL_FRAC_LOCAL,
							     		 regLab.UNID_VTA,
							     		 cIdUsu_in,
							     		 COD_NUMERA_KARDEX);
	  END;
  END IF;

  END LOOP;
	CLOSE curProds;

  update lgt_kardex k
  set    IND_PROCESO_DIARIA = 'N'
  where  k.cod_grupo_cia = cCodGrupoCia_in
  and    k.cod_local = cCodLocal_in
  and    k.NUM_TIP_DOC = cSecToma_in;

  TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in ,
							  cCodLocal_in ,
							  cSecToma_in ,
							  EST_TOMA_INV_CARGADO,
							  cIdUsu_in);

  ELSE

    RAISE_APPLICATION_ERROR(-20009,'Error de stock comprometido');

  END IF;

	END;
/****************************************************************************/

PROCEDURE TI_DESCONG_GLOB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
			  						   cCodLocal_in    IN CHAR ,
									   cSecToma_in     IN CHAR ,
									   cIdUsu_in       IN CHAR)
   IS
   CURSOR curProds
   IS
 	   SELECT PL.COD_PROD
	   FROM   LGT_PROD_LOCAL PL,
	   		  LGT_PROD P
 	   WHERE P.COD_GRUPO_CIA  = PL.COD_GRUPO_CIA AND
	         P.COD_PROD       = PL.COD_PROD 	 AND
	   		 PL.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	         PL.COD_LOCAL     = cCodLocal_in     AND
			 P.COD_LAB IN (SELECT COD_LAB
			 		   	   FROM LGT_TOMA_INV_LAB
						   WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
						         COD_LOCAL     = cCodLocal_in 	 AND
								 SEC_TOMA_INV  = cSecToma_in);
		regLab curProds%ROWTYPE;
  BEGIN
   --Actualiza labs
  UPDATE LGT_LAB SET FEC_MOD_LAB = SYSDATE,USU_MOD_LAB = cIdUsu_in,
  		 IND_LAB_CONG = INDICADOR_NO
  WHERE COD_LAB IN (SELECT COD_LAB
			 		FROM   LGT_TOMA_INV_LAB
					WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
						   COD_LOCAL     = cCodLocal_in    AND
						   SEC_TOMA_INV  = cSecToma_in);
  --Actualiza productos
  OPEN curProds;
     LOOP
	   FETCH curProds INTO regLab;
	   EXIT WHEN curProds%NOTFOUND;
	  TI_ACT_EST_COG_PROD_LOCAL(cCodGrupoCia_in , cCodLocal_in , regLab.COD_PROD  , INDICADOR_NO,cIdUsu_in);
    END LOOP;
     CLOSE curProds;
   END;
   /**********************************************************************************************************/

  PROCEDURE TI_ACT_EST_COG_PROD_LOCAL(cCodGrupoCia_in IN CHAR,
  									  cCodLocal_in    IN CHAR,
									  cCodProd_in     IN CHAR,
									  cIndCong_in     IN CHAR,
									  cIdUsu_in       IN CHAR)
  IS
  BEGIN
  	   UPDATE LGT_PROD_LOCAL SET  FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = cIdUsu_in,
	   		  IND_PROD_CONG      = cIndCong_in
      WHERE  COD_GRUPO_CIA    = cCodGrupoCia_in AND
  			 COD_LOCAL        = cCodLocal_in	AND
			 COD_PROD         = cCodProd_in;
  END;

   /**********************************************************************************************************/

  PROCEDURE TI_ACT_EST_CARGA_PROD(cCodGrupoCia_in IN CHAR,
  									  cCodLocal_in    IN CHAR,
                      cSecToma_in     IN CHAR,
    								  cCodProd_in     IN CHAR,
		  							  cIdUsu_in       IN CHAR)
  IS
  BEGIN
  	   UPDATE LGT_TOMA_INV_LAB_PROD SET  FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE, USU_MOD_TOMA_INV_LAB_PROD = cIdUsu_in,
	   		      ind_difencia     = 'S'
       WHERE  COD_GRUPO_CIA    = cCodGrupoCia_in AND
  			      COD_LOCAL        = cCodLocal_in	AND
              SEC_TOMA_INV     = cSecToma_in AND
			        COD_PROD         = cCodProd_in;

       UPDATE LGT_TOMA_INV_LAB_PROD SET  FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE, USU_MOD_TOMA_INV_LAB_PROD = cIdUsu_in,
	   		      ind_difencia     = 'S'
       WHERE  COD_GRUPO_CIA    = cCodGrupoCia_in AND
  			      COD_LOCAL        = cCodLocal_in	AND
              SEC_TOMA_INV     = cSecToma_in AND
			        COD_PROD         = cCodProd_in;

  END;

  /**********************************************************************************************************/

  PROCEDURE TI_ACT_CANT_PROD_CONTEO(cCodGrupoCia_in IN CHAR,
  									  cCodLocal_in    IN CHAR,
                      cSecToma_in     IN CHAR,
    								  cCodProd_in     IN CHAR,
		  							  cIdUsu_in       IN CHAR)
  IS
  BEGIN
  	   UPDATE LGT_TOMA_INV_LAB_PROD SET  FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE, USU_MOD_TOMA_INV_LAB_PROD = cIdUsu_in,
              --CANT_TOMA_INV_PROD_CONTEO = 0,
              HORA_CONTEO = SYSDATE
       WHERE  COD_GRUPO_CIA    = cCodGrupoCia_in AND
  			      COD_LOCAL        = cCodLocal_in	AND
              SEC_TOMA_INV     = cSecToma_in AND
			        COD_PROD         = cCodProd_in;
  END;

    /*********************************************************************************************/

  PROCEDURE TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in IN CHAR ,
  									                  cCodLocal_in    IN CHAR ,
									                    cSecToma_in     IN CHAR ,
									                    cEstToma_in     IN CHAR ,
									                    cIdUsu_in       IN CHAR)
  IS
  BEGIN
     UPDATE LGT_TOMA_INV_LAB_PROD SET FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE, USU_MOD_TOMA_INV_LAB_PROD = cIdUsu_in,
	 		                                EST_TOMA_INV_LAB_PROD     = cEstToma_in
     WHERE  COD_GRUPO_CIA             = cCodGrupoCia_in  AND
            COD_LOCAL                 = cCodLocal_in 	 AND
            SEC_TOMA_INV              = cSecToma_in;

	 UPDATE LGT_TOMA_INV_LAB SET FEC_MOD_TOMA_INV_LAB = SYSDATE, USU_MOD_TOMA_INV_LAB = cIdUsu_in,
	 		                         EST_TOMA_INV_LAB     = cEstToma_in
	 WHERE  COD_GRUPO_CIA        = cCodGrupoCia_in  AND
      	  COD_LOCAL            = cCodLocal_in 	AND
      	  SEC_TOMA_INV         = cSecToma_in;

	 UPDATE LGT_TOMA_INV_CAB SET FEC_MOD_TOMA_INV = SYSDATE,USU_MOD_TOMA_INV = cIdUsu_in,
                           	 	 EST_TOMA_INV     = cEstToma_in
     WHERE  COD_GRUPO_CIA    = cCodGrupoCia_in   AND
		 	      COD_LOCAL        = cCodLocal_in 	 AND
		 	      SEC_TOMA_INV  	 = cSecToma_in;
 END;
/****************************************************************************/

   FUNCTION TI_OBTIENE_IND_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR ,
   										                     cCodLocal_in    IN CHAR,
										                       cSecToma_in     IN CHAR)
   RETURN CHAR
   IS
   vCant NUMBER;
   vInd CHAR(1);
   BEGIN
      SELECT TO_NUMBER(COUNT(*))INTO   vCant
      FROM   LGT_TOMA_INV_LAB_PROD
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
   	         COD_LOCAL     = cCodLocal_in    AND
   			     SEC_TOMA_INV  = cSecToma_in	 AND
   			     CANT_TOMA_INV_PROD IS NULL;
	   IF vCant=0 THEN
	   	  vInd:='0';
	   ELSE
	   	  vInd:='1';
	   END IF;
	 RETURN vInd;
   END;

/****************************************************************************/


   FUNCTION TI_OBTIENE_TIEMPO_MOD_TOMA(cCodGrupoCia_in IN CHAR,
   										                cCodLocal_in    IN CHAR)
   RETURN CHAR
   IS
   vInd CHAR(3);
   BEGIN
      SELECT G.LLAVE_TAB_GRAL
      INTO   vInd
      FROM   PBL_TAB_GRAL G
      WHERE  g.id_tab_gral=275 and
             g.est_tab_gral='A';
	 RETURN vInd;
   END;

/****************************************************************************/


   FUNCTION TI_OBTIENE_IND_STOCK_COMP(cCodGrupoCia_in IN CHAR,
   										                cCodLocal_in    IN CHAR,
										                  cProducto       IN CHAR,
                                      cCantidad       IN CHAR)
   RETURN CHAR
   IS
   vCant NUMBER;
   vInd CHAR(1);
   BEGIN

    vInd:='S';
	 RETURN vInd;
   END;

/****************************************************************************/

   FUNCTION TI_OBTIENE_IND_STOCK_COMP(cCodGrupoCia_in IN CHAR,
										                  cProducto       IN CHAR)
   RETURN NUMBER
   IS
   vValMaxFrac NUMBER(4);
   BEGIN

      select L.VAL_MAX_FRAC
      into   vValMaxFrac
      from   LGT_PROD L
      where  L.COD_GRUPO_CIA = cCodGrupoCia_in     AND
		         L.COD_PROD      = cProducto           ;

	 RETURN vValMaxFrac;
   END;


/****************************************************************************/

   FUNCTION TI_OBTIENE_STOCK_USADO_CRUCE(cCodGrupoCia_in   IN CHAR ,
   										                     cCodLocal_in    IN CHAR,
										                       cSecToma_in     IN CHAR,
                                           cCodProd_in     IN CHAR)
   RETURN CHAR
   IS

   vInd CHAR(5);

   BEGIN

      SELECT SUM(CANTIDAD_PROD_CRUCE)
      INTO vInd
      FROM LGT_AUX_INV_DIA_CRUCE
      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
   	         COD_LOCAL     = cCodLocal_in    AND
   			     SEC_TOMA_INV  = cSecToma_in	 AND
   			     COD_PROD_CRUCE = cCodProd_in AND
             ACCION_CRUCE=1  ;

       IF  vInd IS NULL THEN
         vInd:=0;
       END IF;

	 RETURN vInd;
   END;

/****************************************************************************/

  FUNCTION TI_OBTIENE_IND_FOR_UPDATE(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecTomaInv_in  IN CHAR,
                                     cIndProceso     IN CHAR)
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
  BEGIN
        OPEN curTi FOR
          SELECT SEC_TOMA_INV|| 'Ã' ||
                 EST_TOMA_INV
          FROM   LGT_TOMA_INV_CAB
          WHERE COD_GRUPO_CIA = ccodgrupocia_in AND
          COD_LOCAL     = ccodlocal_in	AND
          SEC_TOMA_INV = csectomainv_in  AND
          TIP_TOMA_INV = TIPO_DIARIO AND
          EST_TOMA_INV = cindproceso FOR UPDATE ;
    RETURN curTi;
  END;
/****************************************************************************/

  FUNCTION TI_LISTA_LABS_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR ,
    									                   cCodLocal_in    IN CHAR ,
										                     cSecToma_in     IN CHAR)
 	RETURN FarmaCursor
 	IS
   	  curLabs FarmaCursor;
 	BEGIN
	OPEN curLabs FOR
     SELECT DISTINCT TILP.COD_LAB || ' - ' ||
 	 				  L.NOM_LAB
	   FROM   LGT_TOMA_INV_LAB_PROD TILP,
	 		      LGT_LAB L
     WHERE  TILP.COD_GRUPO_CIA = cCodGrupoCia_in AND
   			    TILP.COD_LOCAL     = cCodLocal_in	 AND
   			    TILP.SEC_TOMA_INV  = cSecToma_in	 AND
   			    TILP.CANT_TOMA_INV_PROD IS NULL 	 AND
			      TILP.COD_LAB       = L.COD_LAB ;
	 RETURN curLabs;
 END;
/****************************************************************************/

 PROCEDURE TI_ANULA_TOMA_INV(cCodGrupoCia_in IN CHAR ,
                             cCodLocal_in    IN CHAR ,
					                   cSecToma_in     IN CHAR ,
					                   cIdUsu_in       IN CHAR)
 IS
 BEGIN
   TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in ,
						                 cCodLocal_in ,
					                   cSecToma_in ,
					                   EST_TOMA_INV_ANULADO,
					                   cIdUsu_in);
 END;
/****************************************************************************/

  PROCEDURE TI_RELLENA_CERO_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
  										                   cCodLocal_in    IN CHAR ,
                                         cSecToma_in     IN CHAR )
  IS
  BEGIN
   UPDATE LGT_TOMA_INV_LAB_PROD SET FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,HORA =SYSDATE,
   		                              CANT_TOMA_INV_PROD=0
   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
          COD_LOCAL     = cCodLocal_in 	  AND
	        SEC_TOMA_INV  = cSecToma_in 	  AND
	        --COD_LAB       = cCodLab_in 	  AND
	        CANT_TOMA_INV_PROD IS NULL;
  END;
/****************************************************************************/

 PROCEDURE TI_RELLENA_CERO_CONTEO(cCodGrupoCia_in IN CHAR ,
  										                   cCodLocal_in    IN CHAR ,
                                         cSecToma_in     IN CHAR )
  IS
  BEGIN
   UPDATE LGT_TOMA_INV_LAB_PROD SET FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,HORA =SYSDATE,
   		                              CANT_TOMA_INV_PROD_CONTEO=0
   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
          COD_LOCAL     = cCodLocal_in 	  AND
	        SEC_TOMA_INV  = cSecToma_in 	  AND
	        --COD_LAB       = cCodLab_in 	  AND
	        CANT_TOMA_INV_PROD_CONTEO IS NULL;
  END;
/****************************************************************************/

  FUNCTION TI_LISTA_DIF_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  								                    cCodLocal_in    IN CHAR ,
										                      cSecToma_in     IN CHAR ,
										                      cCodLab_in      IN CHAR)
    RETURN FarmaCursor
  IS
    curLabs FarmaCursor;
  BEGIN
	     OPEN curLabs FOR
            SELECT  TILP.COD_PROD || 'Ã' ||
            		    NVL(P.DESC_PROD,' ') || 'Ã' ||
          		      p.desc_unid_present || 'Ã' ||
          		      TRUNC(STK_ANTERIOR/PL.VAL_FRAC_LOCAL) ||' / '|| DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD(STK_ANTERIOR,PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          		      TRUNC((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR)/PL.VAL_FRAC_LOCAL) ||' / '||  DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR),PL.VAL_FRAC_LOCAL)) || 'Ã' ||
          		      TO_CHAR(PL.VAL_PREC_VTA,'999,990.00')
            FROM    LGT_TOMA_INV_LAB_PROD TILP,
                    LGT_PROD_LOCAL PL,
		                LGT_PROD p
            WHERE   TILP.CANT_TOMA_INV_PROD IS NOT NULL AND
  	   	            TILP.COD_GRUPO_CIA = cCodGrupoCia_in AND
	   	              TILP.COD_LOCAL = cCodLocal_in AND
	   	              TILP.SEC_TOMA_INV = cSecToma_in AND
	   	              TILP.COD_LAB = cCodLab_in AND
	   	              TILP.EST_TOMA_INV_LAB_PROD IN (EST_TOMA_INV_PROCESO,EST_TOMA_INV_EMITIDO) AND
 	   	              (NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) <> 0  AND
	   	              TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
	   	              TILP.COD_LOCAL = PL.COD_LOCAL AND
	   	              TILP.COD_PROD = PL.COD_PROD AND
		                P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
		                P.COD_PROD = PL.COD_PROD;

    RETURN curLabs;
  END;
/****************************************************************************/

  FUNCTION TI_LISTA_COD_LABORATORIOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curLabs FarmaCursor;
  BEGIN
    OPEN curLabs FOR
        SELECT Toma_lab.cod_lab|| 'Ã' ||
               lab.nom_lab
        FROM   lgt_toma_inv_lab toma_lab,
               lgt_lab lab
        WHERE  toma_lab.cod_grupo_cia = ccodgrupocia_in
        AND    toma_lab.cod_local = ccodlocal_in
        AND    toma_lab.sec_toma_inv = cSecToma_in
        AND    toma_lab.cod_lab = lab.cod_lab  ;
     RETURN curlabs;
  END;
/****************************************************************************/

 FUNCTION TI_LISTA_PROD_IMPRESION(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodLab_in      IN CHAR,
                                  cSecToma_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
    OPEN curLabs FOR
       SELECT Toma_prod.cod_prod|| 'Ã' ||
               prod.desc_prod|| 'Ã' ||
               prod.desc_unid_present|| 'Ã' ||
               toma_prod.desc_unid_vta
        FROM   lgt_toma_inv_lab_prod Toma_prod,
               lgt_prod prod
        WHERE  toma_prod.cod_grupo_cia = ccodgrupocia_in
        AND    toma_prod.cod_local = ccodlocal_in
        AND    toma_prod.cod_lab = ccodlab_in
        AND    toma_Prod.sec_toma_inv = Csectoma_In
        AND    Toma_prod.Cod_Grupo_Cia = prod.cod_grupo_cia
        AND    toma_prod.cod_prod = prod.cod_prod;
    RETURN Curlabs;
  END ;
/****************************************************************************/

 PROCEDURE TI_INSERT_TOMA_CAB(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR)
 IS

   BEGIN

            EXECUTE IMMEDIATE
            ' INSERT INTO lgt_toma_inv_cab@XE_'||ccodlocal_in ||
            ' (SELECT * ' ||
            ' FROM   lgt_toma_inv_cab ' ||
            ' WHERE  cod_grupo_cia = ' || ccodgrupocia_in ||
            ' AND    cod_local = ' || ccodlocal_in ||
            ' AND    sec_toma_inv = ' || csectoma_in || ')' ;

  END ;
/****************************************************************************/

 PROCEDURE TI_INSERT_TOMA_LAB(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR)
 IS

   BEGIN

            EXECUTE IMMEDIATE
            ' INSERT INTO lgt_toma_inv_lab@XE_'||ccodlocal_in ||
            ' (SELECT * ' ||
            ' FROM   lgt_toma_inv_lab ' ||
            ' WHERE  cod_grupo_cia = ' || ccodgrupocia_in ||
            ' AND    cod_local = ' || ccodlocal_in ||
            ' AND    sec_toma_inv = ' || csectoma_in || ')' ;

  END ;
/****************************************************************************/

 PROCEDURE TI_INSERT_TOMA_LAB_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR)
 IS

   BEGIN

            EXECUTE IMMEDIATE
            ' INSERT INTO lgt_toma_inv_lab_prod@XE_'||ccodlocal_in ||
            ' (SELECT * ' ||
            ' FROM   lgt_toma_inv_lab_prod ' ||
            ' WHERE  cod_grupo_cia = ' || ccodgrupocia_in ||
            ' AND    cod_local = ' || ccodlocal_in ||
            ' AND    sec_toma_inv = ' || csectoma_in || ')' ;

  END ;
/****************************************************************************/

 FUNCTION TI_OBTIENE_CANT_TOMA_CAB(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curti FarmaCursor;
   v_vSentencia VARCHAR2(32767);
 BEGIN
      v_vsentencia :=   ' SELECT sec_toma_inv ' ||
                        ' FROM   lgt_toma_inv_cab@XE_'||ccodlocal_in ||
                        ' WHERE  cod_grupo_cia = ' || cCodGrupoCia_in ||
                        ' AND    cod_local = ' || ccodlocal_in ||
                        ' AND    sec_toma_inv = ' || csectoma_in ;
   OPEN curti FOR v_Vsentencia;
   dbms_output.put_line(v_vsentencia);
   RETURN curti;
 END;
/****************************************************************************/
FUNCTION TI_OBTIENE_CANT_TOMA_LAB(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curti FarmaCursor;
   v_vSentencia VARCHAR2(32767);
 BEGIN
      v_vsentencia :=   ' SELECT sec_toma_inv ' ||
                        ' FROM   lgt_toma_inv_LAB@XE_'||ccodlocal_in ||
                        ' WHERE  cod_grupo_cia = ' || cCodGrupoCia_in ||
                        ' AND    cod_local = ' || ccodlocal_in ||
                        ' AND    sec_toma_inv = ' || csectoma_in ;
   OPEN curti FOR v_Vsentencia;
   dbms_output.put_line(v_vsentencia);
   RETURN curti;
 END;
/****************************************************************************/
FUNCTION TI_OBTIENE_CANT_TOMA_LAB_PROD(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cSecToma_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curti FarmaCursor;
   v_vSentencia VARCHAR2(32767);
 BEGIN
      v_vsentencia :=   ' SELECT sec_toma_inv ' ||
                        ' FROM   lgt_toma_inv_LAB_PROD@XE_'||ccodlocal_in ||
                        ' WHERE  cod_grupo_cia = ' || cCodGrupoCia_in ||
                        ' AND    cod_local = ' || ccodlocal_in ||
                        ' AND    sec_toma_inv = ' || csectoma_in ;
   OPEN curti FOR v_Vsentencia;
   dbms_output.put_line(v_vsentencia);
   RETURN curti;
 END;
/****************************************************************************/


 PROCEDURE TI_ENVIA_TOMA_LOCAL(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cSecToma_in     IN CHAR)
 IS
 v_count farmacursor ;
 x CHAR(8);

 BEGIN
  v_count := TI_OBTIENE_CANT_TOMA_CAB(ccodgrupocia_in,ccodlocal_in,csectoma_in);

     FETCH v_count INTO x;
         IF v_count%NOTFOUND  THEN
         dbms_output.put_line('entro al if');
            TI_INSERT_TOMA_CAB(ccodgrupocia_in,ccodlocal_in,csectoma_in);
            TI_INSERT_TOMA_LAB(ccodgrupocia_in,ccodlocal_in,csectoma_in);
            TI_INSERT_TOMA_LAB_PROD(ccodgrupocia_in,ccodlocal_in,csectoma_in);
         ELSE
         dbms_output.put_line('entro al else');
           ROLLBACK;
           RAISE_APPLICATION_ERROR(-20100,'No se concreto el envio de toma de inventario al local. Verifique, para luego volver a proceder');
           dbms_output.put_line('se cayo');
         END IF ;
 EXCEPTION
      WHEN OTHERS THEN
          ROLLBACK;
          RAISE_APPLICATION_ERROR(-20100,'No se concreto el envio de toma de inventario al local. Verifique, para luego volver a proceder');          RAISE_APPLICATION_ERROR(-20101,'No se pudo realizar el envio al local. La toma ya existe en el local o no existe coneccion');
          dbms_output.put_line('se cayo');
 END;
  /****************************************************************************/

  PROCEDURE CARGA_DIA_GRABA_TOM_INV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vIdUsu_in IN VARCHAR2)
  AS
    v_nNumToma LGT_TOMA_INV_CAB.SEC_TOMA_INV%TYPE;
    CURSOR curLabs IS
    SELECT D.COD_LAB,D.COD_LOCAL,D.COD_PROD,COUNT(D.COD_PROD) AS CANT_PROD,D.BLOQUE --agregado jchavez 09.10.09 se añadió bloque
    FROM LGT_AUX_INV_DIA D , LGT_PROD_LOCAL L
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in AND
          D.COD_LOCAL = cCodLocal_in AND
          D.COD_GRUPO_CIA = L.COD_GRUPO_CIA AND
          D.COD_PROD = L.COD_PROD  AND
          D.COD_LOCAL = L.COD_LOCAL
    GROUP BY D.COD_LAB,D.COD_LOCAL,D.COD_PROD,D.BLOQUE
    ;
    v_rCurLab curLabs%ROWTYPE;

    vCOD_LAB CHAR(5);
    vUNID_VTA VARCHAR2(30);
    vVAL_FRAC_LOCAL NUMBER;
    vSTK_FISICO NUMBER;
    vCANTLAB NUMBER;
    vCANTLABPROD NUMBER;

  BEGIN
   --GRABAR CABECERA

      v_nNumToma := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV),8,'0','I' );
      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV, vIdUsu_in);
      DBMS_OUTPUT.PUT_LINE('INVENTARIO DIARIO NUEVO: '||v_nNumToma);

    INSERT INTO LGT_TOMA_INV_CAB(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,
                                 TIP_TOMA_INV,CANT_LAB_TOMA,EST_TOMA_INV,
                                 FEC_CREA_TOMA_INV,USU_CREA_TOMA_INV,
                                 IND_CICLICO,BLOQUE) --agregado jchavez 09.10.09 se añade el campo BLOQUE
    VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumToma,
                   'D',1,'E',
                   SYSDATE,vIdUsu_in,
                   'N',
                   (SELECT DISTINCT A.BLOQUE
                    FROM LGT_AUX_INV_DIA A
                    WHERE A.COD_GRUPO_CIA= cCodGrupoCia_in
                   )--agregado jchavez 09.10.09 se añade el campo BLOQUE
           );


    FOR v_rCurLab IN curLabs
    LOOP

       --GRABAR LABORATORIOS
       SELECT P.COD_LAB INTO vCOD_LAB
       FROM   LGT_PROD P
       WHERE  P.COD_GRUPO_CIA=cCodGrupoCia_in AND
             P.COD_PROD=v_rCurLab.Cod_Prod;

       SELECT COUNT(*) INTO vCANTLAB
       FROM   LGT_TOMA_INV_LAB
       WHERE  COD_GRUPO_CIA=cCodGrupoCia_in AND
              COD_LAB=vCOD_LAB AND
              SEC_TOMA_INV=v_nNumToma;

       IF vCANTLAB<>1 THEN

       INSERT INTO LGT_TOMA_INV_LAB(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,
                                   COD_LAB,EST_TOMA_INV_LAB,CANT_PROD_LAB,
                                   FEC_CREA_TOMA_INV_LAB,USU_CREA_TOMA_INV_LAB)
       VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumToma,
              vCOD_LAB,'E',v_rCurLab.CANT_PROD,
                          SYSDATE,vIdUsu_in);
       END IF;

      --GRABAR DETALLE
      DBMS_OUTPUT.PUT_LINE('       PROD '||v_rCurLab.Cod_Prod);

      SELECT COUNT(*) INTO vCANTLABPROD
       FROM   LGT_TOMA_INV_LAB_PROD
       WHERE  COD_GRUPO_CIA=cCodGrupoCia_in AND
              COD_LAB=vCOD_LAB AND
              SEC_TOMA_INV=v_nNumToma AND
              COD_PROD=v_rCurLab.Cod_Prod;

      SELECT PL.UNID_VTA,PL.VAL_FRAC_LOCAL,PL.STK_FISICO
      INTO   vUNID_VTA,vVAL_FRAC_LOCAL,vSTK_FISICO
      FROM  LGT_PROD_LOCAL PL
      WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
            AND PL.COD_LOCAL = cCodLocal_in
            AND PL.COD_PROD = v_rCurLab.Cod_Prod;

       DBMS_OUTPUT.PUT_LINE(' INICIO... ');
       DBMS_OUTPUT.PUT_LINE('       GRUPO '||cCodGrupoCia_in);
       DBMS_OUTPUT.PUT_LINE('       LOCAL '||cCodLocal_in);
       DBMS_OUTPUT.PUT_LINE('       TOMA '||v_nNumToma);
       DBMS_OUTPUT.PUT_LINE('       LAB '||vCOD_LAB);
       DBMS_OUTPUT.PUT_LINE('       PROD '||v_rCurLab.Cod_Prod);
       DBMS_OUTPUT.PUT_LINE(' FIN... ');

      INSERT INTO LGT_TOMA_INV_LAB_PROD(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,COD_LAB,COD_PROD,
                                        DESC_UNID_VTA,
                                        VAL_FRAC,
                                        STK_ANTERIOR,
                                        EST_TOMA_INV_LAB_PROD,
                                        FEC_CREA_TOMA_INV_LAB_PROD,
                                        USU_CREA_TOMA_INV_LAB_PROD,
                                        BLOQUE)--agregado jchavez 09.10.09 se añadió en el campo BLOQUE
      VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumToma,vCOD_LAB,v_rCurLab.Cod_Prod,
             vUNID_VTA,vVAL_FRAC_LOCAL,vSTK_FISICO,'E',SYSDATE,vIdUsu_in,v_rCurLab.bloque) ;

    END LOOP;
    /*IF v_nCantLabs = 0 THEN
      RAISE_APPLICATION_ERROR(-2040,'NO SE HAN GRABADO NINGUN LABORATORIO');
    ELSE*/
      UPDATE LGT_TOMA_INV_CAB
      SET FEC_MOD_TOMA_INV = SYSDATE, USU_MOD_TOMA_INV = vIdUsu_in--, CANT_LAB_TOMA = v_nCantLabs
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND SEC_TOMA_INV = v_nNumToma;
    /*END IF;*/

  END;


  /****************************************************************************/
FUNCTION TI_F_CUR_DIFERENCIAS_DIARIO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR )
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
  BEGIN
        OPEN curTi FOR
          SELECT TILP.COD_PROD || 'Ã' ||  -- 0
                 NVL(P.DESC_PROD, ' ') || 'Ã' || -- 1
                 p.desc_unid_present || 'Ã' || -- 2

                 TRUNC(CANT_PROD_STK_ANTERIOR / PL.VAL_FRAC_LOCAL) || ' / ' ||
                       DECODE(PL.VAL_FRAC_LOCAL,1,' ',
                       MOD(CANT_PROD_STK_ANTERIOR, PL.VAL_FRAC_LOCAL)) || 'Ã' || -- 3

                 trunc(CANT_PROD_DIFERENCIA /PL.VAL_FRAC_LOCAL)    || ' / ' ||
                       DECODE(PL.VAL_FRAC_LOCAL,1,' ',
                       MOD(CANT_PROD_DIFERENCIA,PL.VAL_FRAC_LOCAL)) || 'Ã' || -- 4

                 TO_CHAR(CANT_PROD_DIFERENCIA *PL.VAL_PREC_VTA,'999,990.00') || 'Ã' ||  -- 5
                 LAB.COD_LAB || '- ' || LAB.NOM_LAB || 'Ã' ||  -- 6
                 C.FEC_CREA_TOMA_INV || 'Ã' || -- 7

                 trunc(PL.Stk_Fisico /PL.VAL_FRAC_LOCAL)    || ' / ' ||
                       DECODE(PL.VAL_FRAC_LOCAL,1,' ',
                       MOD(PL.Stk_Fisico,PL.VAL_FRAC_LOCAL)) || 'Ã' || -- 4

                 TILP.SEC_TOMA_INV   || 'Ã' ||  -- 8
                 VK.SEC_KARDEX       || 'Ã' ||  -- 9
                 VK.CANT_MOV_PROD    || 'Ã' ||  -- 10
                 VK.VAL_FRACC_PROD   || 'Ã' ||  -- 11
                 VK.COD_MOT_KARDEX


            FROM LGT_TOMA_INV_LAB_PROD TILP,
                 LGT_PROD_LOCAL        PL,
                 LGT_PROD              p,
                 LGT_LAB               LAB,
                 lgt_toma_inv_cab      C,
                 (
                  SELECT K.COD_GRUPO_CIA,K.COD_LOCAL,K.NUM_TIP_DOC,K.COD_PROD,K.SEC_KARDEX,K.COD_MOT_KARDEX,K.CANT_MOV_PROD,K.VAL_FRACC_PROD
                  FROM   LGT_KARDEX K
                  WHERE  K.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    K.COD_LOCAL = cCodLocal_in
--                  and    K.IND_DIF_TOMA_DIARIA = 'S'
                  AND    k.IND_PROCESO_DIARIA = 'N'
                  AND    K.COD_MOT_KARDEX IN ('510','511','516','517')
                  AND    K.SEC_KARDEX_ORIGEN IS NULL
                 ) VK
           WHERE TILP.COD_GRUPO_CIA = ccodgrupocia_in
             AND TILP.COD_LOCAL = ccodlocal_in
             AND TILP.CANT_TOMA_INV_PROD IS NOT NULL
             --AND (NVL(TILP.CANT_TOMA_INV_PROD_CONTEO, CANT_TOMA_INV_PROD) - CANT_PROD_STK_ANTERIOR) <> 0
             AND TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
             AND TILP.COD_LOCAL = PL.COD_LOCAL
             AND TILP.COD_PROD = PL.COD_PROD
             AND P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
             AND P.COD_PROD = PL.COD_PROD
             AND P.COD_LAB = LAB.COD_LAB
             AND TILP.COD_GRUPO_CIA = VK.COD_GRUPO_CIA
             AND TILP.COD_LOCAL = VK.COD_LOCAL
             AND TILP.SEC_TOMA_INV = VK.NUM_TIP_DOC
             AND TILP.COD_PROD = VK.COD_PROD
             AND (TILP.IND_DIFENCIA = 'S' OR TILP.IND_DIFENCIA = 'C')

             AND C.TIP_TOMA_INV = 'D'
             AND TILP.EST_TOMA_INV_LAB_PROD = 'C'
             AND C.EST_TOMA_INV = 'C'
             AND C.SEC_TOMA_INV = TILP.SEC_TOMA_INV;
    RETURN curTi;
  END;
/****************************************************************************/
 FUNCTION TI_F_CUR_LITA_MOTIVOS(cCodGrupoCia_in IN CHAR ,
  									             cCodLocal_in    IN CHAR ,
									               cTipoMotivo_in  IN CHAR
                                )
 RETURN FarmaCursor
 IS
   curMotivos FarmaCursor;
 BEGIN
    OPEN curMotivos FOR
       SELECT  m.sec_motivo ||'Ã'|| nvl(m.desc_motivo,' ')
        FROM   LGT_MOTIVO_TOMA_INV m
        WHERE  m.cod_grupo_cia = cCodGrupoCia_in
        and    m.tip_toma_inv  = 'D'
        and    m.tip_motivo = cTipoMotivo_in;
    RETURN curMotivos;
  END;
/****************************************************************************/
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
                             )
 is
 cCantToma number;
 cCodMotivoKardex char(3);
 cIdUsu_in varchar2(200);

 unid_vta_in VARCHAR2(30);
 stk_fisico_in NUMBER(6);
 begin

   SELECT TO_NUMBER(cCantMov_in)
   INTO   cCantToma
   FROM   DUAL;

   select l.unid_vta,l.stk_fisico
   into   unid_vta_in,stk_fisico_in
   from   lgt_prod_local l
   where  cod_grupo_cia  = cCodGrupoCia_in
   and    cod_local = cCodLocal_in
   and    cod_prod = cCodProd_in;
   --Actualiza el stock actual de cada producto
   UPDATE LGT_PROD_LOCAL
      SET FEC_MOD_PROD_LOCAL = SYSDATE,
          USU_MOD_PROD_LOCAL = cSecUsu_in,
          STK_FISICO         = STK_FISICO + (cCantToma*-1)
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND COD_PROD = cCodProd_in;

    SELECT m.cod_motivo_kardex
    into   cCodMotivoKardex
    FROM   LGT_MOTIVO_TOMA_INV M
    WHERE  M.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    M.SEC_MOTIVO = cCodMotivo_in;

    select u.login_usu
    into   cIdUsu_in
    from   pbl_usu_local u
    where  u.cod_grupo_cia = cCodGrupoCia_in
    and    u.cod_local = cCodLocal_in
    and    u.sec_usu_local = cSecUsu_in;

    update lgt_kardex k
    set    k.ind_proceso_diaria = 'S'
    where  k.cod_grupo_cia = cCodGrupoCia_in
    and    k.cod_local = cCodLocal_in
    and    k.sec_kardex = cSecKardex_in;

    GRABAR_KARDEX(cCodGrupoCia_in,
                         cCodLocal_in,
    					     		 cCodProd_in,
    					     		 cCodMotivoKardex,---
    				   	     	 TIP_DOC_KARDEX_TOMA_INV,
    				   	     	 cSecToma_in,
    					     		 stk_fisico_in,--
    					     		 cCantToma*-1,
    					     		 cValFrac_in,
    					     		 unid_vta_in,
    					     		 cIdUsu_in,
    					     		 COD_NUMERA_KARDEX,
                       cSecKardex_in,
                       null--COdigo de Ajuste
                       );
 end;

/****************************************************************************/
 FUNCTION TI_F_CUR_LISTA_TRAB(
                              cCodCia_in      IN CHAR,
                              cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR
                              )
  RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
      OPEN curGral FOR
      SELECT mae.num_doc_iden || 'Ã' ||
             APE_PAT_TRAB||' '||APE_MAT_TRAB||', '||NOM_TRAB || 'Ã' ||
             NVL(mae.COD_TRAB_RRHH,' ') ||'Ã'||
             MAE.COD_TRAB
      FROM CE_MAE_TRAB MAE, PBL_USU_LOCAL LOC
      WHERE MAE.COD_CIA = cCodCia_in
            AND LOC.COD_GRUPO_CIA = cCodGrupoCia_in
            AND LOC.COD_LOCAL = cCodLocal_in
            AND LOC.EST_USU = 'A'
            AND MAE.COD_TRAB = LOC.COD_TRAB;
    RETURN curGral;
  END;
/****************************************************************************/
 FUNCTION TI_F_VAR2_GET_TRABAJADOR(
                              cCodCia_in      IN CHAR,
                              cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cDNI_in    IN CHAR
                              )
  RETURN varchar2
  IS
    vDatos varchar2(3000);
  BEGIN
    begin
      SELECT mae.num_doc_iden || 'Ã' ||
             APE_PAT_TRAB||' '||APE_MAT_TRAB||', '||NOM_TRAB || 'Ã' ||
             NVL(mae.COD_TRAB_RRHH,' ') ||'Ã'||
             MAE.COD_TRAB
      into   vDatos
      FROM CE_MAE_TRAB MAE, PBL_USU_LOCAL LOC
      WHERE MAE.COD_CIA = cCodCia_in
            AND LOC.COD_GRUPO_CIA = cCodGrupoCia_in
            AND LOC.COD_LOCAL = cCodLocal_in
            AND mae.num_doc_iden = cDNI_in
            AND LOC.EST_USU = 'A'
            AND MAE.COD_TRAB = LOC.COD_TRAB;
     exception
     when no_data_found then
          vDatos := 'N';
     end;
    RETURN vDatos;
  END;
/****************************************************************************/
/****************************************************************************/
   PROCEDURE TI_AJUSTE(cCodGrupoCia_in IN CHAR ,
                      cCodLocal_in     IN CHAR ,
                      cSecUsu_in       IN CHAR ,
                      cSecToma_in      IN CHAR ,
                      cCodProd_in      IN CHAR ,
                      cCodMotivo_in    IN CHAR,--FINAL
                      cSecKardex_in    IN CHAR,
                      cCantMov_in      IN CHAR,
                      cValFrac_in      IN CHAR,
                      cCodMotProdKar   IN CHAR,--REVERT
                      cCodAjuste       IN CHAR)
   is
    cCantToma number;
    cCodMotivoKardex char(3);
    cCodMotivoKardex2 char(3);
    cIdUsu_in varchar2(200);

    unid_vta_in VARCHAR2(30);
     stk_fisico_in NUMBER(6);
    i  NUMBER(1);
   begin

     SELECT TO_NUMBER(cCantMov_in)
     INTO   cCantToma
     FROM   DUAL;

     select l.unid_vta,l.stk_fisico
     into   unid_vta_in,stk_fisico_in
     from   lgt_prod_local l
     where  cod_grupo_cia  = cCodGrupoCia_in
     and    cod_local = cCodLocal_in
     and    cod_prod = cCodProd_in;

      SELECT  u.login_usu
      INTO   cIdUsu_in
      FROM    pbl_usu_local u
      WHERE  u.cod_grupo_cia = cCodGrupoCia_in
      AND    u.cod_local = cCodLocal_in
      AND    u.sec_usu_local = cSecUsu_in;

      --Motivo final
      SELECT X.COD_MOT_KARDEX
      INTO   cCodMotivoKardex2
      FROM   LGT_MOT_KARDEX X
      WHERE  X.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    X.COD_MOT_KARDEX = cCodMotProdKar;

     --Por motivos de ajuste (aumentar stock - restar stock)
     IF(
       TRIM(cCodMotivoKardex2)=COD_MOT_KARDEX_AJUSTE_POS1 OR
       TRIM(cCodMotivoKardex2)=COD_MOT_KARDEX_AJUSTE_NEG2 OR
       TRIM(cCodMotivoKardex2)=COD_MOT_KARDEX_CARGA_POS OR
       TRIM(cCodMotivoKardex2)=COD_MOT_KARDEX_CARGA_NEG

       ) THEN

         /*IF  TRIM(cCodMotivoKardex2)=COD_MOT_KARDEX_AJUSTE_POS1 THEN i:=-1;
         ELSIF TRIM(cCodMotivoKardex2)=COD_MOT_KARDEX_AJUSTE_NEG2 THEN i:=1;
         END IF;      */



     select l.unid_vta,l.stk_fisico
     into   unid_vta_in,stk_fisico_in
     from   lgt_prod_local l
     where  cod_grupo_cia  = cCodGrupoCia_in
     and    cod_local = cCodLocal_in
     and    cod_prod = cCodProd_in;
     --Actualiza el stock actual de cada producto
     UPDATE LGT_PROD_LOCAL
        SET FEC_MOD_PROD_LOCAL = SYSDATE,
            USU_MOD_PROD_LOCAL = cIdUsu_in,
            STK_FISICO         = STK_FISICO + (cCantToma*(-1))
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL = cCodLocal_in
        AND COD_PROD = cCodProd_in;


      --Motivo final
      SELECT m.cod_motivo_kardex
      INTO   cCodMotivoKardex
      FROM   LGT_MOTIVO_TOMA_INV M
      WHERE  M.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    M.SEC_MOTIVO = cCodMotivo_in;


    GRABAR_KARDEX(cCodGrupoCia_in,
                  cCodLocal_in,
                  cCodProd_in,
                  cCodMotProdKar,--
                  TIP_DOC_KARDEX_TOMA_INV,
                  cSecToma_in,
                  stk_fisico_in,--
                  (cCantToma*-1),
                  cValFrac_in,
                  unid_vta_in,
                  cIdUsu_in,
                  COD_NUMERA_KARDEX,
                  cSecKardex_in,
                  cCodAjuste);

     select l.unid_vta,l.stk_fisico
     into   unid_vta_in,stk_fisico_in
     from   lgt_prod_local l
     where  cod_grupo_cia  = cCodGrupoCia_in
     and    cod_local = cCodLocal_in
     and    cod_prod = cCodProd_in;
     --Actualiza el stock actual de cada producto
     UPDATE LGT_PROD_LOCAL
        SET FEC_MOD_PROD_LOCAL = SYSDATE,
            USU_MOD_PROD_LOCAL = cIdUsu_in,
            STK_FISICO         = STK_FISICO + (cCantToma)
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_LOCAL = cCodLocal_in
        AND COD_PROD = cCodProd_in;
      NULL;

      --insertar registro fijo de ajuste
     /*  IF  TRIM(cCodMotivoKardex2)=COD_MOT_KARDEX_AJUSTE_POS1 THEN i:=1;
       ELSIF TRIM(cCodMotivoKardex2)=COD_MOT_KARDEX_AJUSTE_NEG2 THEN i:=-1;
       END IF;
       */

      GRABAR_KARDEX(cCodGrupoCia_in,
                         cCodLocal_in,
      					     		 cCodProd_in,
      					     		 cCodMotivoKardex,--
      				   	     	 TIP_DOC_KARDEX_TOMA_INV,
      				   	     	 cSecToma_in,
      					     		 stk_fisico_in,--
      					     		 (cCantToma),
      					     		 cValFrac_in,
      					     		 unid_vta_in,
      					     		 cIdUsu_in,
      					     		 COD_NUMERA_KARDEX,
                         cSecKardex_in,
                         cCodAjuste);

       UPDATE LGT_KARDEX X
       SET X.IND_PROCESO_DIARIA='S'
       WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
       AND X.COD_LOCAL=cCodLocal_in
       AND X.COD_PROD=cCodProd_in
       AND X.SEC_KARDEX=cSecKardex_in;

     ELSE

      RAISE_APPLICATION_ERROR(-20001,'MOTIVO DE AJUSTE INVALIDO');

     END IF;

   end;

  /*
    IF vCantMovProd<>0 THEN
    	 BEGIN
  	 	  IF vCantMovProd > 0 THEN
    	 	  	 vCodMotKardex:=COD_MOT_KARDEX_AJUSTE_POS;
    		  END IF;

    		  IF vCantMovProd < 0 THEN
    	 	  	 vCodMotKardex:=COD_MOT_KARDEX_AJUSTE_NEG;
    		  END IF;

    		  Ptoventa_Inv.INV_GRABAR_KARDEX(
                         cCodGrupoCia_in,
                         cCodLocal_in,
  							     		 regLab.COD_PROD,
  							     		 vCodMotKardex,
  						   	     	 TIP_DOC_KARDEX_TOMA_INV,
  						   	     	 cSecToma_in,
  							     		 regLab.STK_FISICO,
  							     		 vCantMovProd,
  							     		 regLab.VAL_FRAC_LOCAL,
  							     		 regLab.UNID_VTA,
  							     		 cIdUsu_in,
  							     		 COD_NUMERA_KARDEX);
  	  END;
    END IF;
  */
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
                              cSecKardexOrigen     IN CHAR,--solo para registro temporal
                              cCodAjuste           IN CHAR,
                              cGlosa_in			       IN CHAR DEFAULT NULL,
                              cTipDoc_in           IN CHAR DEFAULT NULL,
                              cNumDoc_in           IN CHAR DEFAULT NULL)
    IS
    v_cSecKardex LGT_KARDEX.SEC_KARDEX%TYPE;
    BEGIN
      v_cSecKardex :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,cCodNumera_in),10,'0','I' );
      INSERT INTO LGT_KARDEX(COD_GRUPO_CIA, COD_LOCAL, SEC_KARDEX, COD_PROD, COD_MOT_KARDEX,
                                       TIP_DOC_KARDEX, NUM_TIP_DOC, STK_ANTERIOR_PROD, CANT_MOV_PROD,
                                       STK_FINAL_PROD, VAL_FRACC_PROD, DESC_UNID_VTA, USU_CREA_KARDEX,DESC_GLOSA_AJUSTE,
                                       TIP_COMP_PAGO,NUM_COMP_PAGO,SEC_KARDEX_ORIGEN,COD_AJUSTE)
       VALUES (cCodGrupoCia_in,	cCodLocal_in, v_cSecKardex, cCodProd_in, cCodMotKardex_in,
                                       cTipDocKardex_in, cNumTipDoc_in, nStkAnteriorProd_in, nCantMovProd_in,
                                       (nStkAnteriorProd_in + nCantMovProd_in), nValFrac_in, cDescUnidVta_in, cUsuCreaKardex_in,cGlosa_in,
                                       cTipDoc_in,cNumDoc_in,cSecKardexOrigen,cCodAjuste);
  	Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in, cUsuCreaKardex_in);
    END;
/****************************************************************************/
PROCEDURE GRABAR_AJUSTE(cCodGrupoCia_in 	   IN CHAR,
                          cCodLocal_in    	   IN CHAR,
                          cIdUsu_in            IN CHAR,
                          cCodMotKardex_in     IN CHAR,
                          cCodAjust_in         IN CHAR)
    IS
    v_cCodAjuste LGT_AJUSTE_TOMA_INV_DIA_LOC.COD_AJUSTE%TYPE;
    cCodMot LGT_MOTIVO_TOMA_INV.COD_MOTIVO_KARDEX%TYPE;
    BEGIN

    SELECT X.COD_MOTIVO_KARDEX INTO cCodMot
    FROM LGT_MOTIVO_TOMA_INV X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.SEC_MOTIVO=TRIM(cCodMotKardex_in);

      INSERT INTO  LGT_AJUSTE_TOMA_INV_DIA_LOC (COD_GRUPO_CIA,COD_LOCAL,COD_AJUSTE,FEC_AJUSTE,COD_MOT_KARDEX,USU_CREA_AJUST,
             FEC_MOD_AJUST,USU_MOD_AJUST)
      VALUES (cCodGrupoCia_in,	cCodLocal_in,cCodAjust_in,TRUNC(SYSDATE),cCodMot,cIdUsu_in,NULL,NULL);

    END;

/*******************************************************************************************************/
   PROCEDURE GRABAR_AJUSTE_PROD(cCodGrupoCia_in 	   IN CHAR,
                                cCodLocal_in    	   IN CHAR,
                                cIdUsu_in            IN CHAR,
                                cCodAjuste_in        IN CHAR,
                                cCodToma_in          IN CHAR,
                                cCodProd_in          IN CHAR)
   IS
    BEGIN
      INSERT INTO  LGT_AJUSTE_PROD(COD_GRUPO_CIA,COD_LOCAL,COD_AJUSTE,SEC_TOMA_INV,COD_PROD,USU_CREA,FEC_MOD,USU_MOD)
       VALUES (cCodGrupoCia_in,	cCodLocal_in,cCodAjuste_in,cCodToma_in,cCodProd_in,cIdUsu_in,NULL,NULL);
    END;

  /*******************************************************************************************************/
  PROCEDURE GRABAR_AJUSTE_TRAB(cCodGrupoCia_in 	   IN CHAR,
                                cCodLocal_in    	   IN CHAR,
                                cIdUsu_in            IN CHAR,
                                cCodAjuste_in        IN CHAR,
                                cCodTrab             IN CHAR,
                                cCodTrabRRHH         IN CHAR,
                                cMontoDesc           IN NUMBER)
   IS
    BEGIN
      INSERT INTO  LGT_AJUSTE_TRAB(COD_GRUPO_CIA,COD_LOCAL,COD_AJUSTE,COD_TRAB,COD_TRAB_RRHH,MONT_DESCUENTO,USU_CREA,FEC_MOD,USU_MOD)
       VALUES (cCodGrupoCia_in,	cCodLocal_in,cCodAjuste_in,cCodTrab,cCodTrabRRHH,cMontoDesc,cIdUsu_in,NULL,NULL);
    END;

FUNCTION TI_P_GRABA_PED_CAB(
                                  cCodGrupoCia_in 	   IN CHAR,
                                  cCodLocal_in    	   IN CHAR,
                                  cUsuCrea_in          IN CHAR
                                  )
    RETURN VARCHAR2
    IS
    v_cSecPedido TMP_PED_CAB_INV_DIARIO.SEC_PEDIDO%TYPE;

    BEGIN
      v_cSecPedido :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,'071'),10,'0','I' );

      INSERT INTO TMP_PED_CAB_INV_DIARIO(COD_GRUPO_CIA,
                                        COD_LOCAL,
                                        SEC_PEDIDO,
                                        ESTADO,
                                        FEC_CREA_INV_PED_CAB,
                                        USU_CREA_INV_PED_CAB
                                        )
       VALUES (cCodGrupoCia_in,	cCodLocal_in, v_cSecPedido,'E',sysdate,cUsuCrea_in);

  	Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, '071', cUsuCrea_in);

    RETURN v_cSecPedido;

    END;
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
                                  )
    IS


    BEGIN
      INSERT INTO TMP_PED_DET_INV_DIARIO(COD_GRUPO_CIA,
                                        COD_LOCAL,SEC_PEDIDO,SEC_PED_DET,COD_PROD,CANT_ATENDIDA,VAL_FRAC,
                                          SEC_TOMA_INV,SEC_KARDEX,COD_MOT_KARDEX,
                                        FEC_CREA_INV_PED_DET,USU_CREA_INV_PED_DET)
       VALUES (cCodGrupoCia_in,	cCodLocal_in, cSecPedido,cSecDet_in,
              cCodProd_in,to_number(cCant_in,'9999999.000'),
              to_number(cValFrac_in,'999999.000'),
              cSecToma_in,cSecKardex_in,cCodMotivo_in,
              sysdate,cUsuCrea_in);

    END;

/****************************************************************************/

/****************************************************************************/
 FUNCTION TI_F_CUR_DET_PED_TEMPORAL(
                                    cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedTemp_in  IN CHAR
                                    )
  RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
      OPEN curGral FOR
        select T.COD_PROD || 'Ã' ||
               P.DESC_PROD || 'Ã' ||
               DECODE(L.IND_PROD_FRACCIONADO,'N',P.DESC_UNID_PRESENT,L.UNID_VTA) || 'Ã' ||
               TO_CHAR(L.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
               T.CANT_ATENDIDA*-1 || 'Ã' ||
               TO_CHAR(L.VAL_PREC_VTA*T.CANT_ATENDIDA*-1,'999,990.00') || 'Ã' ||
               T.VAL_FRAC
        from   TMP_PED_DET_INV_DIARIO T,
               LGT_PROD_LOCAL L,
               LGT_PROD P
        WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    T.COD_LOCAL = cCodLocal_in
        AND    T.SEC_PEDIDO = cNumPedTemp_in
        AND    T.COD_GRUPO_CIA = L.COD_GRUPO_CIA
        AND    T.COD_LOCAL = L.COD_LOCAL
        AND    T.COD_PROD = L.COD_PROD
        AND    L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    L.COD_PROD = P.COD_PROD;

    RETURN curGral;
  END;
/****************************************************************************/
 PROCEDURE TI_P_ELIMINA_DET_PED_TEMP(
                                    cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedTemp_in  IN CHAR,
                                    cCodProd_in  IN CHAR
                                    )
    IS

    BEGIN
      delete tmp_ped_det_inv_diario t
      where  t.cod_grupo_cia = cCodGrupoCia_in
      and    t.cod_local = cCodLocal_in
      and    t.sec_pedido =  cNumPedTemp_in
      and    t.cod_prod = cCodProd_in;

    END;
/****************************************************************************/
 PROCEDURE TI_P_ELIMINA_PED_TEMP(
                                    cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedTemp_in  IN CHAR
                                    )
 is
 begin

      delete tmp_ped_det_inv_diario t
      where  t.cod_grupo_cia = cCodGrupoCia_in
      and    t.cod_local = cCodLocal_in
      and    t.sec_pedido =  cNumPedTemp_in;

      delete tmp_ped_dcto_x_trab t
      where  t.cod_grupo_cia = cCodGrupoCia_in
      and    t.cod_local = cCodLocal_in
      and    t.sec_pedido =  cNumPedTemp_in;

      delete tmp_ped_cab_inv_diario t
      where  t.cod_grupo_cia = cCodGrupoCia_in
      and    t.cod_local = cCodLocal_in
      and    t.sec_pedido =  cNumPedTemp_in;

 end;
/****************************************************************************/
 PROCEDURE TI_P_INSERT_TRAB_DCTO(
                                    cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedTemp_in  IN CHAR,
                                    cDNI_in  IN CHAR,
                                    cCodRRHH_in     IN CHAR,
                                    cCodTrab_in     IN CHAR,
                                    cMonto_in       IN CHAR,
                                    cUsu_in         IN CHAR
                                    )
 is
 begin
 --RAISE_APPLICATION_ERROR(-20009,'nMontoDescuento:'||to_number(cMonto_in,'9999999999.00'));
      INSERT INTO TMP_PED_DCTO_X_TRAB
      (COD_GRUPO_CIA,COD_LOCAL,SEC_PEDIDO,COD_TRAB_RR_HH,
       COD_TRAB,MONTO,FEC_CREA_PED_DCTO,USU_CREA_PED_DCTO,DNI_USU)
      VALUES
      (cCodGrupoCia_in,cCodLocal_in,cNumPedTemp_in,cCodRRHH_in,
       --cCodTrab_in,to_number(cMonto_in,'9999999999.00'),sysdate,cUsu_in,cDNI_in);
       cCodTrab_in,to_number(cMonto_in,'9999999999.00'),sysdate,cUsu_in,cDNI_in);
 end;
 /* ************************************************************************ */
 PROCEDURE TI_P_REVIERTE_COMPROMETE_DET(
                                        cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cNumPedTemp_in  IN CHAR,
                                        cNumPedVta_in   IN CHAR,
                                        cUsuCrea_in     IN CHAR,
                                        cIpPC_in        IN CHAR
                                        )
 IS
  cCantToma number;
 cCodMotivoKardex char(3);
 cIdUsu_in varchar2(200);

 unid_vta_in VARCHAR2(30);
 stk_fisico_in NUMBER(6);

 cursor vCurDetalle is
 select d.cod_grupo_cia,d.cod_local,d.cod_prod,d.cant_atendida,
        d.sec_toma_inv,d.sec_kardex,d.cod_mot_kardex,d.val_frac
 from   tmp_ped_det_inv_diario d
 where  d.cod_grupo_cia = cCodGrupoCia_in
 and    d.cod_local = cCodLocal_in
 and    d.sec_pedido = cNumPedTemp_in;

 BEGIN


  FOR vCurProd IN vCurDetalle
  LOOP

 --Revierte la diferencia
 SELECT TO_NUMBER(vCurProd.Cant_Atendida)
   INTO   cCantToma
   FROM   DUAL;

   select l.unid_vta,l.stk_fisico
   into   unid_vta_in,stk_fisico_in
   from   lgt_prod_local l
   where  cod_grupo_cia  = vCurProd.Cod_Grupo_Cia
   and    cod_local = vCurProd.Cod_Local
   and    cod_prod = vCurProd.Cod_Prod;
   --Actualiza el stock actual de cada producto
   UPDATE LGT_PROD_LOCAL
      SET FEC_MOD_PROD_LOCAL = SYSDATE,
          USU_MOD_PROD_LOCAL = cUsuCrea_in,
          STK_FISICO         = STK_FISICO + (vCurProd.Cant_Atendida*-1)
   WHERE COD_GRUPO_CIA = vCurProd.Cod_Grupo_Cia
      AND COD_LOCAL = vCurProd.Cod_Local
      AND COD_PROD = vCurProd.Cod_Prod;
    /*
  COD_MOT_KARDEX_VIRTUAL_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '510';
	COD_MOT_KARDEX_VIRTUAL_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '511';

  COD_MOT_KARDEX_CARGA_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '516';
	COD_MOT_KARDEX_CARGA_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '517';

    */
    if vCurProd.Cod_Mot_Kardex = COD_MOT_KARDEX_VIRTUAL_POS then
       cCodMotivoKardex := COD_MOT_KARDEX_VIRTUAL_NEG;
    else
        if vCurProd.Cod_Mot_Kardex = COD_MOT_KARDEX_VIRTUAL_NEG then
           cCodMotivoKardex := COD_MOT_KARDEX_VIRTUAL_POS;
        else
            if vCurProd.Cod_Mot_Kardex = COD_MOT_KARDEX_CARGA_POS then
               cCodMotivoKardex := COD_MOT_KARDEX_CARGA_NEG;
           else
               if vCurProd.Cod_Mot_Kardex = COD_MOT_KARDEX_CARGA_NEG then
                  cCodMotivoKardex := COD_MOT_KARDEX_CARGA_POS;
               end if;
           end if;
        end if;
    end if;



    update lgt_kardex k
    set    k.ind_proceso_diaria = 'S'
    where  k.cod_grupo_cia = vCurProd.Cod_Grupo_Cia
    and    k.cod_local = vCurProd.Cod_Local
    and    k.sec_kardex = vCurProd.Sec_Kardex;

    GRABAR_KARDEX(vCurProd.Cod_Grupo_Cia,
                         vCurProd.Cod_Local,
    					     		 vCurProd.Cod_Prod,
    					     		 cCodMotivoKardex,---
    				   	     	 TIP_DOC_KARDEX_TOMA_INV,
    				   	     	 vCurProd.Sec_Toma_Inv,
    					     		 stk_fisico_in,--
    					     		 vCurProd.Cant_Atendida*-1,
    					     		 vCurProd.Val_Frac,
    					     		 unid_vta_in,
    					     		 cUsuCrea_in,
    					     		 COD_NUMERA_KARDEX,
                       vCurProd.Sec_Kardex,
                       null--codigo de ajuste
                       );




  end loop;
 END;
 /***************************************************************************/
  PROCEDURE TI_P_GRABA_PEDIDO(
                              cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedTemp_in  IN CHAR,
                              cNeto_in        IN CHAR,
                              cRedondeo_in    IN CHAR,
                              cUsu_in         IN CHAR,
                              cSecUsu_in      IN CHAR,
                              cIPPc_in        IN CHAR
                              )
 is
 numPedDiario      varchar2(4);
 numPedVenta       varchar2(10);
 feHoyDia date;
 nItems_in number;
 feModNumeracion date;

 nMontoDescuento number;
 nTipoCambio number;

 --cIpPC_in vta_pedido_vta_cab.ip_pc%type;

 vTipComPago_in vta_pedido_vta_cab.tip_comp_pago%type;
 begin

  select sum(w.monto)
  into   nMontoDescuento
  from   tmp_ped_dcto_x_trab w
  where  w.cod_grupo_cia = cCodGrupoCia_in
  and    w.cod_local  =  cCodLocal_in
  and    w.sec_pedido =  cNumPedTemp_in;

  --RAISE_APPLICATION_ERROR(-20008,'nMontoDescuento:'||to_char(nMontoDescuento,'99999999.00') ||'-'||to_number(cNeto_in,'99999999.00'));
  if nMontoDescuento < to_number(cNeto_in,'99999999.00') then
     RAISE_APPLICATION_ERROR(-20003,'El monto asignado a los Trabajadores \n es menor que el pedido.\n Debe de completar la diferencia de S/.'||(to_number(cNeto_in,'99999999.00')-nMontoDescuento) );
  end if;

  select count(distinct(d.cod_prod))
  into   nItems_in
  from   tmp_ped_det_inv_diario d
  where  d.cod_grupo_cia = cCodGrupoCia_in
  and    d.cod_local =  cCodLocal_in
  and    d.sec_pedido =  cNumPedTemp_in;

  numPedVenta :=FARMA_UTILITY.OBTENER_NUMERACION('001',cCodLocal_in,'007');

  SELECT SYSDATE
  into   feHoyDia
  FROM   DUAL;

  SELECT NVL(NUMERA.FEC_MOD_NUMERA,SYSDATE)
  INTO   feModNumeracion
  FROM   PBL_NUMERA NUMERA
  WHERE  NUMERA.COD_GRUPO_CIA = '001'
  AND	   NUMERA.COD_LOCAL     = cCodLocal_in
  AND	   NUMERA.COD_NUMERA    = '009' FOR UPDATE;

  if trunc(feHoyDia)!=trunc(feModNumeracion) then
    FARMA_UTILITY.INICIALIZA_NUMERA_SIN_COMMIT('001',cCodLocal_in,'009',cUsu_in);
    numPedDiario := '0001';
  elsif trunc(feHoyDia)=trunc(feModNumeracion) then
    numPedDiario := FARMA_UTILITY.OBTENER_NUMERACION('001',cCodLocal_in,'009');
    numPedDiario := Farma_Utility.COMPLETAR_CON_SIMBOLO(numPedDiario, 4, 0, 'I');
  end if;

  FARMA_UTILITY.ACTUALIZAR_NUMERA_SIN_COMMIT('001',cCodLocal_in,'009',cUsu_in);
  numPedVenta:=Farma_Utility.COMPLETAR_CON_SIMBOLO(numPedVenta, 10, 0, 'I');


  nTipoCambio := Farma_Utility.OBTIENE_TIPO_CAMBIO(cCodGrupoCia_in,null);
  --dbms_output.put_line('...grabando datos de cabecera...');

  vTipComPago_in := ptoventa_admin_imp.imp_get_tipcomp_ip(cCodGrupoCia_in,cCodLocal_in,
                                                   cIPPc_in,'01');
  if trim(vTipComPago_in) = 'N' then
     RAISE_APPLICATION_ERROR(-20008,'PC no tiene asignada Impresora');
  end if;
  INSERT INTO VTA_PEDIDO_VTA_CAB (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,NUM_PED_DIARIO,EST_PED_VTA,CANT_ITEMS_PED_VTA,
                                  COD_CLI_LOCAL,SEC_MOV_CAJA,
                    							VAL_BRUTO_PED_VTA,VAL_NETO_PED_VTA,VAL_REDONDEO_PED_VTA,
                                  VAL_IGV_PED_VTA,VAL_DCTO_PED_VTA,
                                  TIP_COMP_PAGO,
                                  TIP_PED_VTA,VAL_TIP_CAMBIO_PED_VTA,
                 									NOM_CLI_PED_VTA,DIR_CLI_PED_VTA,RUC_CLI_PED_VTA,
                                  USU_CREA_PED_VTA_CAB,IND_DISTR_GRATUITA,
                                  IND_PED_CONVENIO,COD_CONVENIO)
                              VALUES
                              (
                                cCodGrupoCia_in,cCodLocal_in,
                               numPedVenta
                                ,numPedDiario,'D',nItems_in,
                                null,null,
                                0,to_number(cNeto_in,'9999999999.00'),to_number(cRedondeo_in,'9999999999.00'),
                                0,0,
                                vTipComPago_in,
                                '01',nTipoCambio,
                                null,null,null,
                                cUsu_in,'N',
                                'N',null
                              );

  Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT('001', cCodLocal_in, '007', cUsu_in);


  --Aqui revierte cada detalle y luego compromete stock
/*  select c.ip_pc
  into    cIpPC_in
  from   vta_pedido_vta_cab c
  where  c.cod_grupo_cia = cCodGrupoCia_in
  and    c.cod_local = cCodLocal_in
  and    c.num_ped_vta = cNumPedTemp_in;
   */


         TI_P_REVIERTE_COMPROMETE_DET(cCodGrupoCia_in,
                                      cCodLocal_in,
                                      cNumPedTemp_in,
                                      numPedVenta,
                                      cUsu_in,
                                      cIpPC_in
                                      ) ;
  --Fin de Revertir y Comprometer


  ---inserta el detalle de Pedido
    INSERT INTO VTA_PEDIDO_VTA_DET D
    (
      COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,
      CANT_ATENDIDA,VAL_PREC_VTA,VAL_PREC_TOTAL,PORC_DCTO_1,PORC_DCTO_2,
      PORC_DCTO_3,PORC_DCTO_TOTAL,EST_PED_VTA_DET,VAL_TOTAL_BONO,
      VAL_FRAC,SEC_USU_LOCAL,USU_CREA_PED_VTA_DET,
      FEC_CREA_PED_VTA_DET,
      VAL_PREC_LISTA,VAL_IGV,UNID_VTA,IND_EXONERADO_IGV,
      VAL_PREC_PUBLIC,IND_CALCULO_MAX_MIN,
      IND_ORIGEN_PROD,
      VAL_FRAC_LOCAL,CANT_FRAC_LOCAL,
      IND_ZAN,VAL_PREC_PROM,
      PORC_ZAN  -- 2009-11-09 JOLIVA
    )
  SELECT   cCodGrupoCia_in,cCodLocal_in,trim(numPedVenta),ROWNUM SEC,d.cod_prod,
           d.cant_atendida*-1,l.val_prec_vta,
           L.VAL_PREC_VTA*d.CANT_ATENDIDA*-1,l.PORC_DCTO_1,l.PORC_DCTO_2,
           l.PORC_DCTO_3,0,'A',0,
           D.Val_Frac,cSecUsu_in,cUsu_in,
           sysdate,
           l.val_prec_lista,0,
           DECODE(L.IND_PROD_FRACCIONADO,'N',P.DESC_UNID_PRESENT,L.UNID_VTA),
           'S',l.val_prec_vta,'S',
           1,
           l.val_frac_local,d.cant_atendida*-1,
           l.ind_zan,p.val_prec_prom,
           P.PORC_ZAN -- 2009-11-09 JOLIVA
    FROM   TMP_PED_DET_INV_DIARIO d,
           LGT_PROD_LOCAL L,
           LGT_PROD P
    where  d.cod_grupo_cia = cCodGrupoCia_in
    and    d.cod_local =  cCodLocal_in
    and    d.sec_pedido =  cNumPedTemp_in
    and    d.cod_grupo_cia = l.cod_grupo_cia
    and    d.cod_local = l.cod_local
    and    d.cod_prod = l.cod_prod
    and    l.cod_grupo_cia = p.cod_grupo_cia
    and    l.cod_prod = p.cod_prod;

    -- Forma de pago descuento Personal 00019
    insert into vta_forma_pago_pedido
    (COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,VAL_TIP_CAMBIO,
    VAL_VUELTO,IM_TOTAL_PAGO,FEC_CREA_FORMA_PAGO_PED,
    USU_CREA_FORMA_PAGO_PED)
    values
    (cCodGrupoCia_in,cCodLocal_in,'00019',numPedVenta,to_number(cNeto_in,'9999999999.00'),'01',0,
     0,to_number(cNeto_in,'9999999999.00'),sysdate,cUsu_in
     );


    --SE ACTUALIZA EL PEDIDO
    UPDATE  TMP_PED_CAB_INV_DIARIO C
    SET     C.NUM_PED_VTA = numPedVenta,
            C.FEC_MOD_INV_PED_CAB = SYSDATE,
            C.ESTADO = 'P'
    WHERE   C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     C.COD_LOCAL = cCodLocal_in
    AND     C.SEC_PEDIDO = cNumPedTemp_in;

 end;
 /* ***************************************************************************** */
 FUNCTION TI_F_CUR_LISTA_PED_PENDIENTE(cCodGrupoCia_in IN CHAR,
  		   							      cCod_Local_in   IN CHAR,
                            cSec_Usu_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curImpr FarmaCursor;

  BEGIN

    OPEN curImpr FOR
        SELECT NVL(INITCAP(LPAD(NVL(NOM_CLI_PED_VTA || ' ', ' '),
                                 INSTR(NOM_CLI_PED_VTA || ' ', ' '))),
                    ' ') || 'Ã' || NVL(VC.NUM_PED_DIARIO, ' ') || 'Ã' ||
                NVL(TO_CHAR(VC.NUM_PED_VTA), ' ') || 'Ã' ||
                NVL(TO_CHAR(VC.FEC_PED_VTA, 'dd/MM/yyyy HH24:mi:ss'), ' ') || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_NETO_PED_VTA, 0), '999,999,990.00')) || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_IGV_PED_VTA, 0), '999,999,990.00')) || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_NETO_PED_VTA, 0), '999,999,990.00')) || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_BRUTO_PED_VTA, 0), '999,999,990.00')) || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_DCTO_PED_VTA, 0), '999,999,990.00')) || 'Ã' ||
                TRIM(TO_CHAR(NVL(VC.VAL_REDONDEO_PED_VTA, 0),
                             '999,999,990.00')) || 'Ã' ||
                NVL(TO_CHAR(VC.RUC_CLI_PED_VTA), ' ') || 'Ã' ||
                NVL(VC.NUM_PED_DIARIO, ' ') || 'Ã' ||
                NVL(TO_CHAR(VC.FEC_PED_VTA, 'dd/MM/yyyy'), ' ') || 'Ã' ||
                NVL(TO_CHAR(VC.FEC_PED_VTA, 'yyyyMMdd'), ' ') || ' ' ||
                NVL(VC.NUM_PED_DIARIO, ' ') || 'Ã' ||
                NVL(VC.IND_PED_CONVENIO, ' ') || 'Ã' ||
                NVL(VC.COD_CONVENIO, ' ') || 'Ã' || NVL(PC.COD_CLI, ' ')
          FROM VTA_PEDIDO_VTA_CAB VC,
               VTA_TIP_COMP TP,
               CON_PED_VTA_CLI PC,
               (SELECT C.COD_GRUPO_CIA, C.COD_LOCAL, C.NUM_PED_VTA
                  FROM TMP_PED_CAB_INV_DIARIO C
                 WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND C.COD_LOCAL = cCod_Local_in
                   AND C.ESTADO = 'P') VT
         WHERE VC.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VC.COD_LOCAL = cCod_Local_in
           AND VC.EST_PED_VTA = 'D'
           AND VC.COD_GRUPO_CIA = VT.COD_GRUPO_CIA
           AND VC.COD_LOCAL = VT.COD_LOCAL
           AND VC.NUM_PED_VTA = VT.NUM_PED_VTA
           AND TP.TIP_COMP = VC.TIP_COMP_PAGO
           AND TP.COD_GRUPO_CIA = VC.COD_GRUPO_CIA
           AND VC.NUM_PED_VTA = PC.NUM_PED_VTA(+);

    RETURN curImpr;
  END;

 /* ************************************************************************* */
  FUNCTION TI_F_CUR_DET_PED_PENDIENTE(cCodGrupoCia_in IN CHAR,
  		   						          cCod_Local_in   IN CHAR,
								          cNum_Ped_Vta_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR

		SELECT NVL(TO_CHAR(DET.COD_PROD),' ')        || 'Ã' ||
       		   NVL(TO_CHAR(P.DESC_PROD),' ')         || 'Ã' ||
	  		   NVL(TO_CHAR(DET.UNID_VTA),' ')        || 'Ã' ||
	   		   TO_CHAR(NVL(DET.VAL_PREC_VTA,0),'999,999,990.000')  || 'Ã' ||
	   		   TO_CHAR(NVL(DET.CANT_ATENDIDA,0),'999,999,990.00')   || 'Ã' ||
	   		   TO_CHAR( ( NVL(DET.VAL_PREC_TOTAL,0) ),'999,999,990.00')|| 'Ã' ||
           nvl(U.LOGIN_USU,' ')
	    FROM  VTA_PEDIDO_VTA_DET DET,
     		  LGT_PROD P,
          pbl_usu_local u
	    WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in   AND
			  DET.COD_LOCAL     = cCod_Local_in     AND
			  DET.NUM_PED_VTA   = cNum_Ped_Vta_in	AND
			  P.COD_GRUPO_CIA   = DET.COD_GRUPO_CIA AND
      	P.COD_PROD        = DET.COD_PROD      and
        DET.COD_GRUPO_CIA = U.COD_GRUPO_CIA AND
        DET.COD_LOCAL = U.COD_LOCAL AND
        DET.SEC_USU_LOCAL = U.SEC_USU_LOCAL  ;


    RETURN curDet;
  END;

  /* ******************************************************************* */
  FUNCTION TI_P_CUR_FORMA_PAGO_PEDIDO(cCodGrupoCia_in IN CHAR,
  		   						   	            cCodLocal_in    IN CHAR,
									                  cNumPedVta_in 	IN CHAR)
  RETURN FarmaCursor
  IS
    curCli FarmaCursor;
  BEGIN
       OPEN curCli FOR
            SELECT TFPP.COD_FORMA_PAGO || 'Ã' ||
                   FP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                   '0' || 'Ã' ||
                   DECODE(TFPP.TIP_MONEDA,COD_TIP_MON_SOLES,DESC_TIP_MON_SOLES,DESC_TIP_MON_DOLARES) || 'Ã' ||
                   TO_CHAR(TFPP.IM_PAGO,'999,990.00') || 'Ã' ||
                   TO_CHAR(TFPP.IM_TOTAL_PAGO,'999,990.00') || 'Ã' ||
                   TFPP.TIP_MONEDA || 'Ã' ||
                   TFPP.VAL_VUELTO || 'Ã' ||
                   ' ' || 'Ã' ||
                   ' ' || 'Ã' ||
                   ' '
            FROM   vta_forma_pago_pedido TFPP,
                   VTA_FORMA_PAGO FP
            WHERE  TFPP.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    TFPP.COD_LOCAL = cCodLocal_in
            AND    TFPP.NUM_PED_VTA = cNumPedVta_in
            AND    TFPP.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
            AND    TFPP.COD_FORMA_PAGO = FP.COD_FORMA_PAGO;
    RETURN CURCLI;
  END;

 /* *********************************************************************** */
 FUNCTION TI_F_CUR_INFO_PEDIDO(cCodGrupoCia_in  IN CHAR,
  		   					             cCodLocal_in    	IN CHAR,
 							                 cNumPedVta_in    IN CHAR
                               )
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
               nvl(VC.COD_CLI,' ')
  			FROM   VTA_PEDIDO_VTA_CAB VTA_CAB,
               CON_PED_VTA_CLI VC
  			WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
  			AND	   VTA_CAB.COD_LOCAL = cCodLocal_in
  			AND	   VTA_CAB.NUM_PED_VTA = cNumPedVta_in
  			AND	   VTA_CAB.EST_PED_VTA = 'D'
        AND    VTA_CAB.COD_GRUPO_CIA = VC.COD_GRUPO_CIA(+)
        AND    VTA_CAB.COD_LOCAL = VC.COD_LOCAL(+)
        AND    VTA_CAB.NUM_PED_VTA = VC.NUM_PED_VTA(+);
    RETURN curCaj;
  END;
/* **************************************************************************** */
FUNCTION TI_F_CUR_TRAB_DCTO_PEDIDO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecPedTmp_in   IN CHAR)
  RETURN FarmaCursor IS
  curTi FarmaCursor;
BEGIN
  OPEN curTi FOR
    SELECT D.COD_TRAB_RR_HH || 'Ã' ||
           NVL(M.APE_PAT_TRAB || ' ' || M.APE_MAT_TRAB || ',' || M.NOM_TRAB,
               ' ') || 'Ã' || NVL(D.MONTO, '0')
      FROM TMP_PED_DCTO_X_TRAB D ,CE_MAE_TRAB M
     WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
       AND D.COD_LOCAL = cCodLocal_in
       AND D.SEC_PEDIDO = cSecPedTmp_in
       AND D.COD_GRUPO_CIA = M.COD_CIA
       AND D.COD_TRAB_RR_HH = M.COD_TRAB_RRHH;

  RETURN curTi;
END;
/* ****************************************************************************** */
FUNCTION TI_F_VAR2_MONTO_PEDIDO(
                                cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cSecPedTmp_in   IN CHAR
                               )
  RETURN VARCHAR2
  IS

  vNeto varchar2(2000);

BEGIN

  select to_char(nvl(c.val_neto_ped_vta,0),'99999999.000')
  into   vNeto
  from   vta_pedido_vta_cab c
  where  c.cod_grupo_cia = cCodGrupoCia_in
  and    c.cod_local = cCodLocal_in
  and    c.num_ped_vta = (
                         select t.num_ped_vta
                         from   tmp_ped_cab_inv_diario t
                         where  t.cod_grupo_cia = cCodGrupoCia_in
                         and    t.cod_local = cCodLocal_in
                         and    t.sec_pedido = cSecPedTmp_in
                         );

  RETURN vNeto;
END;
/* ****************************************************************************** */
PROCEDURE  TI_P_ACT_IND_SEG_CONTEO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cSecToma_in   IN CHAR,
                                cIdUsu_in       IN VARCHAR2)
IS
BEGIN
   UPDATE LGT_TOMA_INV_CAB
     SET IND_SEG_CONTEO = 'Y', --INDICA QUE INGRESO AL 2DO CONTEO
         USU_MOD_TOMA_INV = cIdUsu_in,
         FEC_MOD_TOMA_INV = SYSDATE
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
         COD_LOCAL = cCodLocal_in AND
         SEC_TOMA_INV = cSecToma_in;
END;

END PTOVENTA_TOMA_DIA;
/

