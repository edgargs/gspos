--------------------------------------------------------
--  DDL for Package PTOVENTA_TOMA_CIC
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TOMA_CIC" AS

  COD_NUMERA_TOMA_INV           PBL_NUMERA.COD_NUMERA%TYPE 		 := '013';

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Inicializa la toma de inventario ciclico.
  --Fecha       Usuario   Comentario
  --23/10/2006  ERIOS     Creación
  PROCEDURE CIC_INICIALIZA_TOMA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Lista los productos de la toma de inventario ciclico.
  --Fecha       Usuario   Comentario
  --24/10/2006  ERIOS     Creación
  FUNCTION GET_LISTA_PROD_TOMA_CIC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista los productos restantes para el inventario.
  --Fecha       Usuario   Comentario
  --24/10/2006  ERIOS     Creación
  FUNCTION GET_LISTA_PRODS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Inserta un producto en la lista de inventario ciclico.
  --Fecha       Usuario   Comentario
  --24/10/2006  ERIOS     Creación
  PROCEDURE CIC_INSERTA_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR, nUnidVends_in IN NUMBER, nMontoTotal_in IN NUMBER,
  cCodLab_in IN CHAR);

  --Descripcion: Borra un producto en la lista de inventario ciclico.
  --Fecha       Usuario   Comentario
  --24/10/2006  ERIOS     Creación
  PROCEDURE CIC_BORRA_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR);

  --Descripcion: Graba un nuevo de inventario ciclico.
  --Fecha       Usuario   Comentario
  --27/10/2006  ERIOS     Creación
  PROCEDURE CIC_GRABA_TOM_INV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cMatriz_in IN CHAR, vIdUsu_in IN VARCHAR2);

	ESTADO_ACTIVO		  CHAR(1):='A';
  LOCAL_MATRIZ      CHAR(3):= '010' ;
	EST_TOMA_INV_PROCESO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'P';
	EST_TOMA_INV_EMITIDO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'E';
	EST_TOMA_INV_CARGADO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'C';
	EST_TOMA_INV_ANULADO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'N';
  EST_CICLICO                      LGT_TOMA_INV_CAB.IND_CICLICO%TYPE := 'S';

  --Descripcion: Obtiene la lista de tomas de inventario
  --Fecha       Usuario		Comentario
  --23/10/2006  paulo     Creación
 FUNCTION TI_LISTA_TOMAS_INV(cCodGrupoCia_in IN CHAR ,
                             cCodLocal_in    IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Obtiene la lista de laboratorios de una toma de inventario
  --Fecha       Usuario		Comentario
  --23/10/2006  paulo     Creación
 FUNCTION TI_LISTA_LABS_TOMA_INV(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Obtiene la lista de productos de un laboratorio en una toma de inventario
 --Fecha       Usuario		Comentario
 --24/10/2006  PAULO     Creación
 FUNCTION TI_LISTA_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR,
                                     cCodLab_in      IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Ingresa la cantidad de un producto inventariado
  --Fecha       Usuario		Comentario
  --24/10/2006  paulo     Creación
 PROCEDURE TI_INGRESA_CANT_PROD_TI(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR,
                                   cCodLab_in      IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   cCantToma_in    IN CHAR);
/****************************************************************************/

  --Descripcion: LISTA LOS MOVIMIENTOS DE KARDEX DEL DIA
  --Fecha       Usuario		Comentario
  --24/10/2006  paulo     Creación
 FUNCTION TI_LISTA_MOVS_KARDEX(cCodGrupoCia_in IN CHAR ,
  		   					             cCodLocal_in    IN CHAR ,
								               cCod_Prod_in	   IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Obtiene el total de items y el total tomado
 --Fecha       Usuario		Comentario
 --25/10/2006  Paulo     Creación
 FUNCTION TI_TOTAL_ITEMS_TOMA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Obtiene la informacion valorizada en precios de todo lo tomado
 --Fecha       Usuario		Comentario
 --25/10/2006  Paulo     Creación
 FUNCTION TI_INFORMACION_VALORIZADA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Lista la diferencia de los productos en consolidado
 --Fecha       Usuario		Comentario
 --25/10/2006  Paulo     Creación
 FUNCTION TI_DIFERENCIAS_CONSOLIDADO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in   IN CHAR,
                                      cSecTomaInv_in IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Lista la diferencia de los productos en consolidado por filtro
 --Fecha       Usuario		Comentario
 --25/10/2006  Paulo     Creación
 FUNCTION TI_DIF_CONSOLIDADO_FILTro(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecTomaInv_in  IN CHAR,
                                    cCodLab_in      IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Actualiza el estado de la estructura de toma de inventario
  --Fecha       Usuario		Comentario
  --25/10/2006  paulo     Creación
 PROCEDURE TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR,
                                     cEstToma_in     IN CHAR,
                                     cIdUsu_in       IN CHAR);
/****************************************************************************/

 --Descripcion: Obtiene un indicador para determinar si existe data incompleta en una toma de inventario
 --Fecha       Usuario		Comentario
 --25/10/2006  MHUAYTA     Creación
 FUNCTION TI_OBTIENE_IND_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR ,
                                         cCodLocal_in    IN CHAR,
                                         cSecToma_in     IN CHAR)
 RETURN CHAR;
/****************************************************************************/

 --Descripcion: Obtiene indicador para cargar la toma
 --Fecha       Usuario		Comentario
 --25/10/2006  Paulo     Creación
 FUNCTION TI_OBTIENE_IND_FOR_UPDATE(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecTomaInv_in  IN CHAR,
                                    cIndProceso     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

 --Descripcion: Obtiene un indicla lista de laboratorios con data incompleta en una toma de inventario
 --Fecha       Usuario		Comentario
 --25/10/2006  PAULO     Creación
 FUNCTION TI_LISTA_LABS_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR ,
                                        cCodLocal_in    IN CHAR,
                                        cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Carga Una toma de inventario: Actualiza estados, estados de congelamiento,stocks en local y genera kardex
  --Fecha       Usuario		Comentario
  --26/10/2006  paulo     Creación
   PROCEDURE TI_CARGA_TOMA_INV(cCodGrupoCia_in IN CHAR ,
                               cCodLocal_in    IN CHAR,
                               cSecToma_in     IN CHAR,
                               cIdUsu_in       IN CHAR);
/****************************************************************************/

  --Descripcion: Anula una toma de inventario: Actualiza estados y estados de congelamiento
  --Fecha       Usuario		Comentario
  --26/10/2006  paulo     Creación
  PROCEDURE TI_ANULA_TOMA_INV(cCodGrupoCia_in IN CHAR ,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR,
                              cIdUsu_in       IN CHAR);
/****************************************************************************/

 --Descripcion: Rellena con ceros las cantidades no ingresadas de los productos a inventariar en un laboratorio
 --Fecha       Usuario		Comentario
 --26/10/2006  paulo     Creación
 PROCEDURE TI_RELLENA_CERO_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
                                        cCodLocal_in    IN CHAR,
                                        cSecToma_in     IN CHAR,
                                        cCodLab_in      IN CHAR);
/****************************************************************************/

  --Descripcion: Muestra las diferencias entre la cantidad ingresada y el stock del producto en una toma ciclica
  --Fecha       Usuario		Comentario
  --27/10/2006  LMESIA    Creación
  FUNCTION TI_LISTA_DIF_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  								                    cCodLocal_in    IN CHAR ,
										                      cSecToma_in     IN CHAR ,
										                      cCodLab_in      IN CHAR)
  RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Obtiene todos los codigos de laboratorio de la toma
  --Fecha       Usuario		Comentario
  --30/10/2006  Paulo    Creación
  FUNCTION TI_LISTA_COD_LABORATORIOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR)
  RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Obtiene todos los codigos de productos por laboratorio
  --Fecha       Usuario		Comentario
  --30/10/2006  Paulo    Creación
 FUNCTION TI_LISTA_PROD_IMPRESION(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cCodLab_in      IN CHAR,
                                  cSecToma_in     IN CHAR)
 RETURN FarmaCursor;
/****************************************************************************/

  --Descripcion: Inserta en la tabla de cabecera de toma inventario en el local
  --Fecha       Usuario		Comentario
  --02/11/2006  Paulo    Creación
 PROCEDURE TI_INSERT_TOMA_CAB(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR);
/****************************************************************************/

  --Descripcion: Ontiene un numero de indicador si la toma ya existe en el local
  --Fecha       Usuario		Comentario
  --02/11/2006  Paulo    Creación
 FUNCTION TI_OBTIENE_CANT_TOMA_CAB(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR)
 RETURN FarmaCursor ;
/****************************************************************************/

  --Descripcion: Ontiene un numero de indicador si la toma ya existe en el local
  --Fecha       Usuario		Comentario
  --02/11/2006  Paulo    Creación
 FUNCTION TI_OBTIENE_CANT_TOMA_LAB(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR)
 RETURN FarmaCursor ;
/****************************************************************************/

  --Descripcion: Ontiene un numero de indicador si la toma ya existe en el local
  --Fecha       Usuario		Comentario
  --02/11/2006  Paulo    Creación
 FUNCTION TI_OBTIENE_CANT_TOMA_LAB_PROD(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cSecToma_in     IN CHAR)
 RETURN FarmaCursor ;
/****************************************************************************/


  --Descripcion: Inserta en la tabla de laboratorios de toma inventario en el local
  --Fecha       Usuario		Comentario
  --02/11/2006  Paulo    Creación
 PROCEDURE TI_INSERT_TOMA_LAB(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR);
/****************************************************************************/

  --Descripcion: Inserta en la tabla de productos por laboratorio para la toma de inventario
  --Fecha       Usuario		Comentario
  --02/11/2006  Paulo    Creación
 PROCEDURE TI_INSERT_TOMA_LAB_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR) ;
/****************************************************************************/

  --Descripcion: Inserta en todas las tablas necesarias para realizar la toma
  --Fecha       Usuario		Comentario
  --02/11/2006  Paulo    Creación
 PROCEDURE TI_ENVIA_TOMA_LOCAL(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cSecToma_in     IN CHAR);


END;

/
