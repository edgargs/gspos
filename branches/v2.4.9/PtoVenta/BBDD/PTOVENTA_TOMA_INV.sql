--------------------------------------------------------
--  DDL for Package PTOVENTA_TOMA_INV
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TOMA_INV" AS

TYPE FarmaCursor IS REF CURSOR;

	COD_MOT_KARDEX_AJUSTE_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '107';
	COD_MOT_KARDEX_AJUSTE_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '009';
	TIP_DOC_KARDEX_TOMA_INV	      LGT_KARDEX.TIP_DOC_KARDEX%TYPE 	 := '03';
	COD_NUMERA_KARDEX             PBL_NUMERA.COD_NUMERA%TYPE 		 := '016';
	COD_NUMERA_TOMA_INV           PBL_NUMERA.COD_NUMERA%TYPE 		 := '013';
  ID_TAB_GRAL                   PBL_TAB_GRAL.ID_TAB_GRAL%TYPE  := '46';
	ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';
	POS_INICIO		      CHAR(1):='I';

	EST_TOMA_INV_PROCESO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'P';
	EST_TOMA_INV_EMITIDO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'E';
	EST_TOMA_INV_CARGADO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'C';
	EST_TOMA_INV_ANULADO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'N';

  --ESTADOS DE LAB_TOMA
  --07/01/2008  DUBILLUZ  CREACION
	EST_LAB_TOMA_EMITIDO               LGT_TOMA_INV_LAB.EST_TOMA_INV_LAB%TYPE := 'E';
  EST_LAB_TOMA_PROCESO               LGT_TOMA_INV_LAB.EST_TOMA_INV_LAB%TYPE := 'P';
	EST_LAB_TOMA_TERMINADO             LGT_TOMA_INV_LAB.EST_TOMA_INV_LAB%TYPE := 'T';

  --23/01/2008 DUBILLUZ CREACION
  TIPO_ROL_ADMINISTRADOR_LOCAL  CHAR(3):='011';
  TIPO_ROL_VENDEDOR             CHAR(3):='010';
  --LISTA LABORATORIOS

  --Descripcion: Obtiene el listado de Laboratorios para inventariar en un Local
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación
  --22/03/2006	PAULO		Modificacion para implementar la funcionalidad de seleccion de tipo de laboratorio
  FUNCTION TI_LISTA_LABS RETURN FarmaCursor;

  /*********************************************************************************************/

  --Descripcion: Guarda la cabecera de una toma de inventario
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación

 FUNCTION TI_GUARDAR_CAB_TI(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR , cCantLab_in IN CHAR , cTipTomaInv_in IN CHAR, cIdUsu_in IN CHAR)
 RETURN CHAR;-- retorna el secTomaInv Generado

 /*********************************************************************************************/

   --Descripcion: Guarda detalles de todos los labs para un inventario
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación
  --23/01/2008  dubilluz   modificacion
PROCEDURE TI_GUARDAR_DET_TOT_TI(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR , cSecToma_in IN CHAR , cIdUsu_in IN CHAR);

/*********************************************************************************************/

   --Descripcion: Guarda el detalle de una toma de inventario
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación
PROCEDURE TI_GUARDAR_DET_TI(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR , cSecToma_in IN CHAR ,cCodLab_in IN CHAR,cIdUsu_in IN CHAR);

/*********************************************************************************************/

   --Descripcion: Guarda los productos de una toma de inventario
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación

PROCEDURE TI_GUADAR_PROD_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR , cSecToma_in IN CHAR ,cCodLab_in IN CHAR,cIdUsu_in IN CHAR);

/*********************************************************************************************/

 --Descripcion: Actualiza el estado de congelamiento un producto
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación


PROCEDURE TI_ACT_EST_COG_PROD_LOCAL(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR, cCodProd_in IN CHAR , cIndCong_in IN CHAR,cIdUsu_in IN CHAR);

/*********************************************************************************************/

 --Descripcion: Actualiza el estado de inventariado de un lab
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación

PROCEDURE TI_ACT_EST_CONG_LAB(cCodLab_in IN CHAR , cIndCong_in IN CHAR);


/*********************************************************************************************/

 --Descripcion: Obtiene la lista de tomas de inventario historicas
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación
FUNCTION TI_LISTA_HIST_TOMAS_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR)
 RETURN FarmaCursor;

 /*********************************************************************************************/

 --Descripcion: Obtiene la lista de tomas de inventario
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación
 FUNCTION TI_LISTA_TOMAS_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR)
 RETURN FarmaCursor;

  /*********************************************************************************************/

 --Descripcion: Obtiene la lista de laboratorios de una toma de inventario
  --Fecha       Usuario		Comentario
  --27/02/2006  MHUAYTA     Creación
  --07/01/2008  dubilluz   modificacion
 FUNCTION TI_LISTA_LABS_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR)
 RETURN FarmaCursor;

 /*********************************************************************************************/

 --Descripcion: Obtiene la lista de productos de un laboratorio en una toma de inventario
  --Fecha       Usuario		Comentario
  --27/02/2006  MHUAYTA     Creación
 FUNCTION TI_LISTA_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR,cCodLab_in IN CHAR)
 RETURN FarmaCursor;

 /*********************************************************************************************/

 --Descripcion: Ingresa la cantidad de un producto inventariado
  --Fecha       Usuario		Comentario
  --27/02/2006  MHUAYTA     Creación
  --07/01/2008  dubilluz   modificacion
 PROCEDURE TI_INGRESA_CANT_PROD_TI(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR,cCodLab_in IN CHAR,cCodProd_in IN CHAR,cCantToma_in IN CHAR);

  /*********************************************************************************************/

 --Descripcion: Obtiene la lista de productos con diferencias en las cantidades de inventariado
  --Fecha       Usuario		Comentario
  --27/02/2006  MHUAYTA     Creación
FUNCTION TI_LISTA_DIF_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR,cCodLab_in IN CHAR)
 RETURN FarmaCursor;

 /*********************************************************************************************/

 --Descripcion: Obtiene la lista de productos con diferencias en las cantidades de inventariado historicamente
  --Fecha       Usuario		Comentario
  --27/02/2006  MHUAYTA     Creación
FUNCTION TI_LISTA_DIF_PROD_LAB_TOMA_I_H(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR,cCodLab_in IN CHAR)
 RETURN FarmaCursor;

  /*********************************************************************************************/

 --Descripcion: Rellena con ceros las cantidades no ingresadas de los productos a inventariar en un laboratorio
  --Fecha       Usuario		Comentario
  --27/02/2006  MHUAYTA     Creación
  --07/01/2008  DUBILLUZ    Modificación
 PROCEDURE TI_RELLENA_CERO_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR,cCodLab_in IN CHAR);

  /*********************************************************************************************/

 --Descripcion: Actualiza el estado de la estructura de toma de inventario
  --Fecha       Usuario		Comentario
  --28/02/2006  MHUAYTA     Creación
  --06/07/2007  DUBILLUZ    Modificación
  PROCEDURE TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR, cEstToma_in IN CHAR,cIdUsu_in IN CHAR);

  /*********************************************************************************************/
  --Descripcion: Descongela laboratorios y sus productos correspondientes
  --Fecha       Usuario		Comentario
  --28/02/2006  MHUAYTA     Creación
   PROCEDURE TI_DESCONG_GLOB_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR,cIdUsu_in IN CHAR);

    /*********************************************************************************************/
  --Descripcion: Carga Una toma de inventario: Actualiza estados, estados de congelamiento,stocks en local y genera kardex
  --Fecha       Usuario		Comentario
  --28/02/2006  MHUAYTA     Creación
   PROCEDURE TI_CARGA_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR,cIdUsu_in IN CHAR);

    /*********************************************************************************************/
   --Descripcion: Anula una toma de inventario: Actualiza estados y estados de congelamiento
   --Fecha       Usuario		Comentario
   --28/02/2006  MHUAYTA     Creación
    PROCEDURE TI_ANULA_TOMA_INV(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR,cIdUsu_in IN CHAR);

	  /**********************************************************************************************************/
    --Descripcion: Obtiene un indicador para determinar si existe data incompleta en una toma de inventario
   --Fecha       Usuario		Comentario
   --28/02/2006  MHUAYTA     Creación
   FUNCTION TI_OBTIENE_IND_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR)
   RETURN CHAR;

     /**********************************************************************************************************/
    --Descripcion: Obtiene un indicla lista de laboratorios con data incompleta en una toma de inventario
   --Fecha       Usuario		Comentario
   --28/02/2006  MHUAYTA     Creación

    FUNCTION TI_LISTA_LABS_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cSecToma_in IN CHAR)
 	RETURN FarmaCursor;
	 /**********************************************************************************************************/

  --Descripcion: Obtiene el listado de Laboratorios para revisar sus items
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación
  FUNCTION TI_LISTA_LABS_ITEMS_LAB RETURN FarmaCursor;
  /**********************************************************************************************************/

  --Descripcion: Obtiene el listado de Items de un laboratorio
  --Fecha       Usuario		Comentario
  --24/02/2006  MHUAYTA     Creación
  FUNCTION TI_LISTA_ITEMS_LAB(cCodGrupoCia_in IN CHAR , cCodLocal_in IN CHAR,cCodLab_in IN CHAR)
 RETURN FarmaCursor;

  --Descripcion: Lista los codigos de laboratios para realizar impresion en bucle
  --Fecha       Usuario		Comentario
  --21/08/2006  Paulo     Creación
   FUNCTION TI_LISTA_COD_LABORATORIOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR)


  RETURN FarmaCursor;

  --Descripcion: Lista los productos para la impreison
  --Fecha       Usuario		Comentario
  --21/08/2006  Paulo     Creación
 FUNCTION TI_LISTA_PROD_IMPRESION(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cCodLab_in IN CHAR)
 RETURN FarmaCursor;

  --Descripcion: Obtiene el total de items y el total tomado
  --Fecha       Usuario		Comentario
  --31/08/2006  Paulo     Creación
 FUNCTION TI_TOTAL_ITEMS_TOMA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in IN CHAR,
                               cSecToma_in CHAR)
 RETURN FarmaCursor;

  --Descripcion: Obtiene la informacion valorizada en precios de todo lo tomado
  --Fecha       Usuario		Comentario
  --31/08/2006  Paulo     Creación
 FUNCTION TI_INFORMACION_VALORIZADA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecToma_in CHAR)
 RETURN FarmaCursor;

 --Descripcion: Lista la diferencia de los productos en consolidado
  --Fecha       Usuario		Comentario
  --12/09/2006  Paulo     Creación
 FUNCTION TI_DIFERENCIAS_CONSOLIDADO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cSecTomaInv_in IN CHAR)
 RETURN FarmaCursor;

  --Descripcion: Lista la diferencia de los productos en consolidado por filtro
  --Fecha       Usuario		Comentario
  --12/09/2006  Paulo     Creación
 FUNCTION TI_DIF_CONSOLIDADO_FILTro(cCodGrupoCia_in IN CHAR,
                                             cCodLocal_in IN CHAR,
                                             cSecTomaInv_in IN CHAR,
                                             cCodLab_in IN CHAR)
 RETURN FarmaCursor;

  --Descripcion: Obtiene indicador para validacion de cargar la toma
  --Fecha       Usuario		Comentario
  --27/09/2006  Paulo     Creación
  FUNCTION TI_OBTIENE_IND_FOR_UPDATE(cCodGrupoCia_in IN CHAR,
  			      cCodLocal_in IN CHAR,
  			      cSecTomaInv_in IN CHAR,
  			      cIndProceso IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: lista los productos com mov kardex
  --Fecha       Usuario		Comentario
  --15/12/2006  Paulo     Creación
   FUNCTION TI_OBTIENE_PROD_MOVIMIENTO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cCodLab_in IN CHAR)
   RETURN FarmaCursor;

  --Descripcion: Actualiza el estado de la toma de inventario
  --Fecha       Usuario		Comentario
  --07/01/2008  dubilluz     Creación
    PROCEDURE TI_ACT_ESTADO_TOMA(cCodGrupoCia_in IN CHAR ,
                							   cCodLocal_in    IN CHAR ,
              									 cSecToma_in     IN CHAR ,
                                 cCodLab_in      IN CHAR );

  --Descripcion: Actualiza el estado de la toma de inventario
  --Fecha       Usuario		Comentario
  --08/01/2008  dubilluz     Creación
    FUNCTION INV_GET_ESTADOS_LAB_TINV(cCodGrupoCia_in IN CHAR ,
                							        cCodLocal_in    IN CHAR ,
              									      cSecToma_in     IN CHAR )

                                     RETURN FarmaCursor;
  --Descripcion: Actualiza el estado de la toma de inventario
  --Fecha       Usuario		Comentario
  --08/01/2008  dubilluz     Creación
    FUNCTION TI_LISTA_LABS_TOMA_FILTRO(cCodGrupoCia_in IN CHAR ,
                     		  						 cCodLocal_in    IN CHAR ,
    								                   cSecToma_in     IN CHAR,
                                       cEstado_in      IN CHAR)
                                       RETURN FarmaCursor;

  --Descripcion: Obtiene Datos de Producto
  --Fecha       Usuario		Comentario
  --21/12/2008  dubilluz     Creación
  FUNCTION TI_F_VAR_DATOS_PROD(
                           cCodGrupoCia_in IN CHAR ,
                		  		 cCodLocal_in    IN CHAR ,
                           cCodProd_in     IN CHAR
                           )
  RETURN FarmaCursor;


  --Descripcion: Obtiene Datos de Producto
  --Fecha       Usuario		Comentario
  --22/12/2009  JMIRANDA    Creación
  PROCEDURE TI_P_INS_AUX_CONTEO_TOMA(   cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cSecTomaInv_in IN CHAR,
                                      cCodProd_in IN CHAR,
                                      cCodBarra_in IN VARCHAR2,
                                      nCantEntero_in IN NUMBER,
                                      nCantFraccion_in IN NUMBER,
                                      cUsuConteo_in IN CHAR,
                                      vIp_in IN VARCHAR,
                                      vIndNoFound_in IN CHAR DEFAULT 'N',
                                      nSecAuxConteo_in IN NUMBER
                                   );

  --Descripcion: Obtiene Secuencial de Producto a insertar
  --Fecha       Usuario		Comentario
  --23/12/2009  JMIRANDA    Creación
  FUNCTION TI_F_GET_SEC_AUX_CONTEO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecTomaInv_in IN CHAR
                                     )
  RETURN NUMBER;

  --Descripcion: Obtiene Lista de Productos
  --Fecha       Usuario		Comentario
  --23/12/2009  JMIRANDA    Creación
  FUNCTION TI_F_CUR_LIS_CONTEO_TOMA(cCodGrupoCia_in IN CHAR,
                    		   			  cCodLocal_in	 IN CHAR,
                                  cSecTomaInv_in IN CHAR,
                                  cUsuConteo_in IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Elimina producto seleccionado
  --Fecha       Usuario		Comentario
  --28/12/2009  JMIRANDA    Creación
  PROCEDURE TI_P_DEL_CONTEO_TOMA(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	 IN CHAR,
                                        cSecTomaInv_in IN CHAR,
                                        cAuxSecConteo_in  IN NUMBER,
        --                                cCantEntMod_in      IN CHAR,
          --                              cCantFraccionMod_in IN CHAR,
                                        cUsuMod_in IN CHAR,
                                        vIp_in IN VARCHAR,
                                        cIndEliminado IN CHAR DEFAULT 'S'
                               );

  --Descripcion: Actualiza producto seleccionado
  --Fecha       Usuario		Comentario
  --28/12/2009  JMIRANDA    Creación

 PROCEDURE TI_P_UPD_CONTEO_TOMA(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	   IN CHAR,
                                        cSecTomaInv_in   IN CHAR,
                                        cAuxSecConteo_in    IN NUMBER,
                                        cCantEntMod_in      IN CHAR,
                                        cCantFraccionMod_in IN CHAR,
                                        cUsuMod_in IN CHAR,
                                        vIp_in IN VARCHAR,
                                        cIndEliminado IN CHAR DEFAULT 'N'
                                   );

  --Descripcion: Actualiza producto seleccionado
  --Fecha       Usuario		Comentario
  --28/12/2009  DUBILLUZ    Creación
 PROCEDURE TI_RELLENA_CERO_TOMA_INV(cCodGrupoCia_in IN CHAR ,
  										 cCodLocal_in    IN CHAR ,
										 cSecToma_in     IN CHAR );


 FUNCTION TI_LISTA_DIF_TOMA_INV(cCodGrupoCia_in IN CHAR ,
           		  								cCodLocal_in    IN CHAR ,
          										  cSecToma_in     IN CHAR)
 RETURN FarmaCursor;

  FUNCTION TI_lista_LAB_TOMA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cSecTomaInv_in IN CHAR)
  RETURN FarmaCursor;

 FUNCTION TI_LISTA_DIF_TOMA_LAB_INV(cCodGrupoCia_in IN CHAR ,
           		  								cCodLocal_in    IN CHAR ,
          										  cSecToma_in     IN CHAR,
                                cCodLab_in      IN CHAR)
 RETURN FarmaCursor;

  --Descripcion: Actualiza producto seleccionado
  --Fecha       Usuario		Comentario
  --30/12/2009  JMIRANDA    Creación

    PROCEDURE TI_P_ENVIA_EMAIL_NO_FOUND(cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR,
                                        cSecTomaInv_in      IN CHAR,
                                        cCodBarra_in        IN CHAR,
                                        cUsu_in             IN CHAR,
                                        cGlosaProd_in       IN VARCHAR2 default 'N'
                                        );

   PROCEDURE TI_P_INS_C_B_NO_FOUND (cCodGrupoCia_in     IN CHAR,
                                    cCodLocal_in        IN CHAR,
                                    cSecTomaInv_in      IN CHAR,
                                    cCodBarra_in        IN CHAR,
                                    cUsu_in             IN CHAR,
                                    cIP_in              IN VARCHAR2,
                                    cGlosa_in           IN VARCHAR2 default 'N'
                                   );

   procedure ti_p_actualiza_indices;

END;

/
