CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_TOMA_INV" AS

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

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_TOMA_INV" AS

 FUNCTION TI_LISTA_LABS
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
  OPEN curLabs FOR
     SELECT COD_LAB || 'Ã' ||
 	 		NOM_LAB || 'Ã' ||
			TIP_LAB
     FROM   LGT_LAB
     WHERE  IND_LAB_CONG = INDICADOR_NO
     AND    COD_LAB IN (SELECT DISTINCT P.COD_LAB
                        FROM   LGT_PROD P
                        WHERE P.EST_PROD = 'A');
	 RETURN curLabs;
 END;
 /*********************************************************************************************/
 FUNCTION TI_GUARDAR_CAB_TI(cCodGrupoCia_in IN CHAR ,
 		  					cCodLocal_in    IN CHAR ,
							cCantLab_in     IN CHAR ,
							cTipTomaInv_in  IN CHAR ,
							cIdUsu_in       IN CHAR)
 RETURN CHAR
 IS
  	v_nNumToma LGT_TOMA_INV_CAB.SEC_TOMA_INV%TYPE;
  BEGIN
     	v_nNumToma := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV),8,'0',POS_INICIO );
	  INSERT INTO LGT_TOMA_INV_CAB(COD_GRUPO_CIA,
	  		 	  				   COD_LOCAL,
								   SEC_TOMA_INV,
								   TIP_TOMA_INV,
								   CANT_LAB_TOMA,
								   EST_TOMA_INV,
								   FEC_CREA_TOMA_INV,
								   USU_CREA_TOMA_INV,
								   FEC_MOD_TOMA_INV,
								   USU_MOD_TOMA_INV)
	  VALUES(cCodGrupoCia_in,
	  		 cCodLocal_in,
			 v_nNumToma,
			 cTipTomaInv_in,
			 cCantLab_in,
			 EST_TOMA_INV_EMITIDO,
			 SYSDATE,
			 cIdUsu_in,
			 NULL,NULL);
	  	Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV, cIdUsu_in);
  	RETURN v_nNumToma;
  END;
    /*********************************************************************************************/
  PROCEDURE TI_GUARDAR_DET_TOT_TI(cCodGrupoCia_in IN CHAR ,
  								  cCodLocal_in    IN CHAR ,
								  cSecToma_in     IN CHAR ,
								  cIdUsu_in       IN CHAR)
  IS
   vCantProdComp NUMBER;
   vCodLab       CHAR(5);
   vCantProd     NUMBER;
  CURSOR curLabs
  		 IS
 	/*	 SELECT COD_LAB
		 FROM   LGT_LAB
		 WHERE  IND_LAB_CONG = INDICADOR_NO;*/
     SELECT COD_LAB
     FROM   LGT_LAB
     WHERE  IND_LAB_CONG = INDICADOR_NO
     AND    COD_LAB IN (SELECT DISTINCT P.COD_LAB
                        FROM   LGT_PROD P
                        WHERE P.EST_PROD = 'A');

  regLab curLabs%ROWTYPE;
    BEGIN

   vCantProdComp := 0;

   IF vCantProdComp >0 THEN
 	RAISE_APPLICATION_ERROR(-20017,'No se puede generar una toma de inventario total mientras exista stock comprometido.');
 END IF;
    OPEN curLabs;
     LOOP
	   FETCH curLabs INTO vCodLab;
	   EXIT WHEN curLabs%NOTFOUND;

	   SELECT COUNT(*) INTO vCantProd FROM
            LGT_PROD_LOCAL PL,
            LGT_PROD P
 	   WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	          PL.COD_LOCAL     = cCodLocal_in	 AND
			      P.COD_LAB        = vCodLab			 AND
            P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A') AND
			      P.COD_GRUPO_CIA  = PL.COD_GRUPO_CIA AND
	          P.COD_PROD       = PL.COD_PROD     ;

	  	  INSERT INTO LGT_TOMA_INV_LAB(COD_GRUPO_CIA,
	  		 	  				   COD_LOCAL,
								   SEC_TOMA_INV,
								   COD_LAB,
								   EST_TOMA_INV_LAB,
								   CANT_PROD_LAB,
								   FEC_CREA_TOMA_INV_LAB,
								   USU_CREA_TOMA_INV_LAB,
								   FEC_MOD_TOMA_INV_LAB,
								   USU_MOD_TOMA_INV_LAB)
	       VALUES(cCodGrupoCia_in,
	  		 cCodLocal_in,
			 cSecToma_in,
			 vCodLab,
			 EST_TOMA_INV_EMITIDO,
			 vCantProd,
			 SYSDATE,
			 cIdUsu_in,
			 NULL,NULL);
			  TI_GUADAR_PROD_TOMA_INV(cCodGrupoCia_in, cCodLocal_in  , cSecToma_in  ,vCodLab ,cIdUsu_in );
			  TI_ACT_EST_CONG_LAB(vCodLab ,INDICADOR_SI);
	    END LOOP;
	     CLOSE curLabs;

      -- Eliminando el rol de vendedor sin tomar ne cuenta los quimicos
      -- 23/01/2008 DUBILLUZ MODIFICACION
      DELETE FROM PBL_ROL_USU
           WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
           AND    COD_LOCAL     = cCodLocal_in
           AND    COD_ROL  = TIPO_ROL_VENDEDOR
           AND    SEC_USU_LOCAL < '900'
           AND    SEC_USU_LOCAL NOT IN (
                               SELECT U.SEC_USU_LOCAL
                               FROM   PBL_ROL_USU U
                               WHERE  U.COD_GRUPO_CIA = cCodGrupoCia_in
                               AND    U.COD_LOCAL = cCodLocal_in
                               AND    U.COD_ROL = TIPO_ROL_ADMINISTRADOR_LOCAL
                              );



  END;
    /*********************************************************************************************/
  PROCEDURE TI_GUARDAR_DET_TI(cCodGrupoCia_in IN CHAR ,
  							  cCodLocal_in    IN CHAR ,
							  cSecToma_in     IN CHAR ,
							  cCodLab_in      IN CHAR,
							  cIdUsu_in       IN CHAR)
  IS
     vCantProdComp NUMBER;
     vCantProd NUMBER;
  BEGIN
  vCantProdComp:=0;
   IF vCantProdComp >0 THEN
 	  RAISE_APPLICATION_ERROR(-20017,'No se puede generar una toma de inventario de un laboratorio mientras exista stock comprometido.');
   END IF;
    SELECT TO_NUMBER(COUNT(*)) INTO vCantProd FROM
	         LGT_PROD_LOCAL PL,
	         LGT_PROD P
 	  WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	         PL.COD_LOCAL     = cCodLocal_in	 AND
			     P.COD_LAB        = cCodLab_in		 AND
           P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A') AND
			     P.COD_GRUPO_CIA  = PL.COD_GRUPO_CIA AND
	         P.COD_PROD       = PL.COD_PROD 	 ;

	  INSERT INTO LGT_TOMA_INV_LAB(COD_GRUPO_CIA,
	  		 	  				   COD_LOCAL,
								   SEC_TOMA_INV,
								   COD_LAB,
								   EST_TOMA_INV_LAB,
								   CANT_PROD_LAB,
								   FEC_CREA_TOMA_INV_LAB,
								   USU_CREA_TOMA_INV_LAB,
								   FEC_MOD_TOMA_INV_LAB,
								   USU_MOD_TOMA_INV_LAB)
	       VALUES(cCodGrupoCia_in,
	  		      cCodLocal_in,
			 	  cSecToma_in,
			 	  cCodLab_in,
			 	  EST_TOMA_INV_EMITIDO,
			 	  vCantProd,
			 	  SYSDATE,
			 	  cIdUsu_in,
			 	  NULL,NULL);

   TI_GUADAR_PROD_TOMA_INV(cCodGrupoCia_in, cCodLocal_in  , cSecToma_in  ,cCodLab_in ,cIdUsu_in );
   TI_ACT_EST_CONG_LAB(cCodLab_in ,INDICADOR_SI);
  END;
   /*********************************************************************************************/
 PROCEDURE TI_GUADAR_PROD_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		   						   cCodLocal_in    IN CHAR ,
								   cSecToma_in     IN CHAR ,
								   cCodLab_in      IN CHAR ,
								   cIdUsu_in       IN CHAR)
 IS
  vCantProd NUMBER;
  CURSOR curProds
  		 IS
 		  SELECT PL.COD_PROD,
		  		   PL.UNID_VTA,
				     PL.VAL_FRAC_LOCAL,
				     PL.STK_FISICO,
             P.VAL_PREC_PROM
		  FROM   LGT_PROD_LOCAL PL,
		  		   LGT_PROD P
 	    WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	           PL.COD_LOCAL     = cCodLocal_in	 AND
             P.EST_PROD = ESTADO_ACTIVO AND
             P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A') AND
			       P.COD_LAB        = cCodLab_in 		 AND
			       P.COD_GRUPO_CIA  = PL.COD_GRUPO_CIA AND
	           P.COD_PROD       = PL.COD_PROD ;

   regLab curProds%ROWTYPE;
  BEGIN
   OPEN curProds;
     LOOP
	   FETCH curProds INTO regLab;
	   EXIT WHEN curProds%NOTFOUND;
         INSERT INTO LGT_TOMA_INV_LAB_PROD(COD_GRUPO_CIA,
	  		 	  				                       COD_LOCAL,
								                           SEC_TOMA_INV,
								                           COD_LAB,
								                           COD_PROD,
								                           DESC_UNID_VTA,
								                           VAL_FRAC,
								                           CANT_TOMA_INV_PROD,
								                           STK_ANTERIOR,
								                           EST_TOMA_INV_LAB_PROD,
								                           FEC_CREA_TOMA_INV_LAB_PROD,
								                           USU_CREA_TOMA_INV_LAB_PROD,
								                           FEC_MOD_TOMA_INV_LAB_PROD,
								                           USU_MOD_TOMA_INV_LAB_PROD,
                                           Val_Prec_Prom)

	       VALUES(cCodGrupoCia_in,
	  		        cCodLocal_in,
			          cSecToma_in,
			          cCodLab_in,
			          regLab.COD_PROD,
			          regLab.UNID_VTA,
			          regLab.VAL_FRAC_LOCAL,
			          NULL,
			          regLab.STK_FISICO,
			          EST_TOMA_INV_EMITIDO,
        			  SYSDATE,
        			  cIdUsu_in,
        			  NULL,NULL,
                regLab.Val_Prec_Prom);
		TI_ACT_EST_COG_PROD_LOCAL(cCodGrupoCia_in , cCodLocal_in , regLab.COD_PROD  , INDICADOR_SI,cIdUsu_in);
    END LOOP;
     CLOSE curProds;
END;
     /*********************************************************************************************/
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
    /*********************************************************************************************/
PROCEDURE TI_ACT_EST_CONG_LAB(cCodLab_in  IN CHAR ,
		  					  cIndCong_in IN CHAR)
IS
BEGIN
  UPDATE LGT_LAB SET FEC_MOD_LAB = SYSDATE,
  		 IND_LAB_CONG = cIndCong_in
      --USU_MOD_LAB = ,NO HAY USUARIO
  WHERE COD_LAB    = cCodLab_in;
END;
/*********************************************************************************************/
 FUNCTION TI_LISTA_HIST_TOMAS_INV(cCodGrupoCia_in IN CHAR ,
		  						  cCodLocal_in    IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
	OPEN curLabs FOR
     SELECT SEC_TOMA_INV                                               || 'Ã' ||
            DECODE(IND_CICLICO,INDICADOR_SI,'CICLICO','TRADICIONAL')   || 'Ã' ||
	  		    CASE TIP_TOMA_INV
            WHEN 'P' THEN 'Parcial'
				 		ELSE 'Total'
			      END                                                        || 'Ã' ||
			      TO_CHAR(FEC_CREA_TOMA_INV,'dd/MM/yyyy HH24:mi:ss')         || 'Ã' ||
			      NVL(TO_CHAR(FEC_MOD_TOMA_INV,'dd/MM/yyyy HH24:mi:ss'),' ') || 'Ã' ||
			      CASE NVL(EST_TOMA_INV,EST_TOMA_INV_EMITIDO)
			      WHEN EST_TOMA_INV_PROCESO THEN 'En proceso'
	          WHEN EST_TOMA_INV_EMITIDO THEN 'Emitido'
				    WHEN EST_TOMA_INV_CARGADO THEN 'Cerrado'
				    WHEN EST_TOMA_INV_ANULADO THEN 'Anulado'
			      END
     FROM   LGT_TOMA_INV_CAB
     WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
  		      COD_LOCAL     = cCodLocal_in	   AND
		        EST_TOMA_INV  IN (EST_TOMA_INV_CARGADO,EST_TOMA_INV_ANULADO)
            AND IND_CICLICO IN(INDICADOR_NO,INDICADOR_SI);--ERIOS 27/10/2006
	 RETURN curLabs;
 END;
 /*********************************************************************************************/
 FUNCTION TI_LISTA_TOMAS_INV(cCodGrupoCia_in IN CHAR ,
 		  					             cCodLocal_in    IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
	OPEN curLabs FOR
     SELECT SEC_TOMA_INV  || 'Ã' ||
        	  CASE   TIP_TOMA_INV
        		WHEN   'P' THEN 'Parcial'
        		ELSE 'Total'
        		END	  || 'Ã' ||
			      TO_CHAR(FEC_CREA_TOMA_INV,'dd/MM/yyyy HH24:mi:ss') || 'Ã' ||
		        CASE NVL(EST_TOMA_INV,EST_TOMA_INV_EMITIDO)
				    WHEN EST_TOMA_INV_PROCESO THEN 'En proceso'
	          WHEN EST_TOMA_INV_EMITIDO THEN 'Emitido'
				    WHEN EST_TOMA_INV_CARGADO THEN 'Cargado'
			 	    WHEN EST_TOMA_INV_ANULADO THEN 'Anulado'
		        END|| 'Ã' ||
            NVL(EST_TOMA_INV,' ')|| 'Ã' ||
            --MODIFICADO
            --DUBILLUZ CREACION
           USU_CREA_TOMA_INV
     FROM  LGT_TOMA_INV_CAB
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
  		     COD_LOCAL     = cCodLocal_in	   AND
		       EST_TOMA_INV IN (EST_TOMA_INV_PROCESO,EST_TOMA_INV_EMITIDO)
                       AND IND_CICLICO = INDICADOR_NO--ERIOS 27/10/2006
                       AND (TIP_TOMA_INV = 'T' OR TIP_TOMA_INV = 'P');--MFAJARDO 30.06.09
                                                                      --MFAJARDO 20.07.09
	 RETURN curLabs;
 END;
 /*********************************************************************************************/
 --07/01/2008 DUBILLUZ MODIFICACION
 FUNCTION TI_LISTA_LABS_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  						 cCodLocal_in    IN CHAR ,
								 cSecToma_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN

	OPEN curLabs FOR
   SELECT TIL.COD_LAB  || 'Ã' ||
	   L.NOM_LAB    || 'Ã' ||
	   CASE NVL(EST_TOMA_INV_LAB,EST_LAB_TOMA_EMITIDO)
	    WHEN EST_LAB_TOMA_PROCESO THEN 'En proceso'
	    WHEN EST_LAB_TOMA_EMITIDO THEN 'Emitido'
		  WHEN EST_LAB_TOMA_TERMINADO THEN 'Terminado'
      ELSE ' '
	   END
 FROM LGT_TOMA_INV_LAB TIL,
 	  LGT_LAB L
 WHERE TIL.COD_GRUPO_CIA = cCodGrupoCia_in AND
  	   TIL.COD_LOCAL     = cCodLocal_in	   AND
	   TIL.SEC_TOMA_INV  = cSecToma_in 	   AND
	   TIL.COD_LAB       = L.COD_LAB       ;

	 RETURN curLabs;
 END;
 /*********************************************************************************************/
 FUNCTION TI_LISTA_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  							 cCodLocal_in    IN CHAR ,
									 cSecToma_in     IN CHAR ,
									 cCodLab_in      IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
	OPEN curLabs FOR
  SELECT TILP.COD_PROD      || 'Ã' ||
  		   NVL(P.DESC_PROD,' ')  	    || 'Ã' ||
         NVL(P.DESC_UNID_PRESENT,' ') || 'Ã' ||
         DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(Trunc(CANT_TOMA_INV_PROD/pl.val_frac_local),'99,990')) || 'Ã' ||
		     --NVL(Trunc(CANT_TOMA_INV_PROD/pl.val_frac_local),0) || 'Ã' ||
         DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(MOD(CANT_TOMA_INV_PROD,pl.val_frac_local),'99,990')) || 'Ã' ||
         --NVL(MOD(CANT_TOMA_INV_PROD,pl.val_frac_local),0) || 'Ã' ||
         PL.VAL_FRAC_LOCAL || 'Ã' ||
         NVL(PL.UNID_VTA,' ')
  FROM LGT_TOMA_INV_LAB_PROD TILP,
       LGT_PROD_LOCAL PL,
	     LGT_PROD P
  WHERE
  	   TILP.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	   TILP.COD_LOCAL     = cCodLocal_in     AND
	   TILP.SEC_TOMA_INV  = cSecToma_in      AND
	   TILP.COD_LAB       = cCodLab_in 		 AND
       P.EST_PROD = ESTADO_ACTIVO AND
       PL.EST_PROD_LOC = ESTADO_ACTIVO AND
       TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
	   TILP.COD_LOCAL     = PL.COD_LOCAL     AND
	   TILP.COD_PROD      = PL.COD_PROD      AND
	   P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA    AND
	   P.COD_PROD = PL.COD_PROD      ;

	 RETURN curLabs;

 END;

  /*********************************************************************************************/
  PROCEDURE TI_INGRESA_CANT_PROD_TI(cCodGrupoCia_in IN CHAR ,
  									cCodLocal_in    IN CHAR ,
									cSecToma_in     IN CHAR ,
									cCodLab_in      IN CHAR ,
									cCodProd_in     IN CHAR ,
									cCantToma_in    IN CHAR)
  IS
  BEGIN
  UPDATE  LGT_TOMA_INV_LAB_PROD SET  FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,
  		  CANT_TOMA_INV_PROD    = cCantToma_in,
  	  	  EST_TOMA_INV_LAB_PROD = EST_TOMA_INV_PROCESO
          --USU_MOD_TOMA_INV_LAB_PROD = ,NO HAY USUARIO
  WHERE
  		COD_GRUPO_CIA = cCodGrupoCia_in AND
		COD_LOCAL     = cCodLocal_in    AND
		SEC_TOMA_INV  = cSecToma_in 	AND
		COD_LAB       = cCodLab_in 		AND
		COD_PROD      = cCodProd_in;
  --ACTUALIZA EL ESTADO DE LA TOMA
  --07/01/2007 DUBILLUZ MODIFICACION
  TI_ACT_ESTADO_TOMA(cCodGrupoCia_in,cCodLocal_in,
                     cSecToma_in,cCodLab_in);
    END;
   /*********************************************************************************************/
 FUNCTION TI_LISTA_DIF_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  								 cCodLocal_in    IN CHAR ,
										 cSecToma_in     IN CHAR ,
										 cCodLab_in      IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN

	OPEN curLabs FOR
  SELECT  TILP.COD_PROD                            || 'Ã' ||
  		    NVL(P.DESC_PROD,' ') 					  || 'Ã' ||
		      p.desc_unid_present 	  			      || 'Ã' ||
		      TRUNC(STK_ANTERIOR/PL.VAL_FRAC_LOCAL) ||' / '|| DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD(STK_ANTERIOR,PL.VAL_FRAC_LOCAL))|| 'Ã' ||
		      TRUNC((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR)/PL.VAL_FRAC_LOCAL) ||' / '||  DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR),PL.VAL_FRAC_LOCAL))|| 'Ã' ||
--		      TO_CHAR(P.VAL_PREC_PROM/PL.VAL_FRAC_LOCAL,'999,990.00')
		      TO_CHAR(NVL(TILP.VAL_PREC_PROM,P.VAL_PREC_PROM)/PL.VAL_FRAC_LOCAL,'999,990.00')
  FROM  LGT_TOMA_INV_LAB_PROD TILP,
        LGT_PROD_LOCAL PL,
		    LGT_PROD p
  WHERE TILP.CANT_TOMA_INV_PROD IS NOT NULL		 	   AND
  	   	TILP.COD_GRUPO_CIA 		    = cCodGrupoCia_in  AND
	   	TILP.COD_LOCAL     		    = cCodLocal_in     AND
	   	TILP.SEC_TOMA_INV  		    = cSecToma_in      AND
	   	TILP.COD_LAB       		    = cCodLab_in       AND
	   	TILP.EST_TOMA_INV_LAB_PROD IN(EST_TOMA_INV_PROCESO,EST_TOMA_INV_EMITIDO) AND
 	   	(NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) <> 0  AND
	   	TILP.COD_GRUPO_CIA          = PL.COD_GRUPO_CIA AND
	   	TILP.COD_LOCAL     		    = PL.COD_LOCAL     AND
	   	TILP.COD_PROD      		    = PL.COD_PROD      AND
		  P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA   		   AND
		  P.COD_PROD = PL.COD_PROD;

  RETURN curLabs;
 END;

 /*****************************************************************************************************/
 FUNCTION TI_LISTA_DIF_PROD_LAB_TOMA_I_H(cCodGrupoCia_in IN CHAR ,
 		  								 cCodLocal_in    IN CHAR ,
										 cSecToma_in     IN CHAR ,
										 cCodLab_in      IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN

	OPEN curLabs FOR
  SELECT  TILP.COD_PROD                            || 'Ã' ||
  		    NVL(P.DESC_PROD,' ') 					  || 'Ã' ||
		      p.desc_unid_present 	  			      || 'Ã' ||
		      TRUNC(STK_ANTERIOR/PL.VAL_FRAC_LOCAL) ||' / '|| DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD(STK_ANTERIOR,PL.VAL_FRAC_LOCAL))|| 'Ã' ||
		      TRUNC((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR)/PL.VAL_FRAC_LOCAL) ||' / '||  DECODE(PL.VAL_FRAC_LOCAL,1,' ',MOD((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR),PL.VAL_FRAC_LOCAL)) || 'Ã' ||
--		      TO_CHAR(P.VAL_PREC_PROM/PL.VAL_FRAC_LOCAL,'999,990.00')
		      TO_CHAR(NVL(TILP.VAL_PREC_PROM,P.VAL_PREC_PROM)/PL.VAL_FRAC_LOCAL,'999,990.00')
  FROM   LGT_TOMA_INV_LAB_PROD TILP,
         LGT_PROD_LOCAL PL,
		     LGT_PROD p
  WHERE  TILP.CANT_TOMA_INV_PROD IS NOT NULL		 	 AND
  	   	 TILP.COD_GRUPO_CIA 		  = cCodGrupoCia_in  AND
	   	 TILP.COD_LOCAL     		  = cCodLocal_in     AND
	   	 TILP.SEC_TOMA_INV  		  = cSecToma_in      AND
	   	 TILP.COD_LAB       		  = cCodLab_in       AND
	   	 TILP.EST_TOMA_INV_LAB_PROD IN (EST_TOMA_INV_CARGADO,EST_TOMA_INV_ANULADO)       AND
 	   	 (NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) <> 0 AND
	   	 TILP.COD_GRUPO_CIA           = PL.COD_GRUPO_CIA AND
	   	 TILP.COD_LOCAL     		  = PL.COD_LOCAL     AND
	   	 TILP.COD_PROD      		  = PL.COD_PROD
		 AND	  P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
		   AND	  P.COD_PROD = PL.COD_PROD      ;
  RETURN curLabs;
 END;
    /*************************************************************************************************/
  PROCEDURE TI_RELLENA_CERO_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
  										 cCodLocal_in    IN CHAR ,
										 cSecToma_in     IN CHAR ,
										 cCodLab_in      IN CHAR)
  IS
  BEGIN
   UPDATE LGT_TOMA_INV_LAB_PROD SET FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,
   		  CANT_TOMA_INV_PROD=0
        --USU_MOD_TOMA_INV_LAB_PRO = ,NO HAY USUARIO
   WHERE
      COD_GRUPO_CIA = cCodGrupoCia_in AND
	  COD_LOCAL     = cCodLocal_in 	  AND
	  SEC_TOMA_INV  = cSecToma_in 	  AND
	  COD_LAB       = cCodLab_in 	  AND
	  CANT_TOMA_INV_PROD IS NULL;

  --ACTUALIZA EL ESTADO DE LA TOMA
  --07/01/2007 DUBILLUZ MODIFICACION
  TI_ACT_ESTADO_TOMA(cCodGrupoCia_in,cCodLocal_in,
                     cSecToma_in,cCodLab_in);

  END;
  /*************************************************************************************************/
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

   /*************************************************************************************************/
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
   PROCEDURE TI_CARGA_TOMA_INV(cCodGrupoCia_in IN CHAR ,
   			 				   cCodLocal_in    IN CHAR ,
							   cSecToma_in     IN CHAR ,
							   cIdUsu_in       IN CHAR)
   IS
   CURSOR curProds
   IS
   	   SELECT TILP.COD_GRUPO_CIA,
	   		  TILP.COD_LOCAL,
			  TILP.COD_PROD,
			  TILP.CANT_TOMA_INV_PROD,
			  PL.STK_FISICO,
			  PL.VAL_FRAC_LOCAL,
			  PL.UNID_VTA ,
        P.VAL_PREC_PROM
	   FROM   LGT_TOMA_INV_LAB_PROD TILP,
	   		  LGT_PROD_LOCAL PL ,
          LGT_PROD       P
 	   WHERE TILP.COD_GRUPO_CIA = cCodGrupoCia_in   AND
	         TILP.COD_LOCAL     = cCodLocal_in		AND
			 TILP.SEC_TOMA_INV  = cSecToma_in		AND
			 CANT_TOMA_INV_PROD <>STK_ANTERIOR		AND
			 TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA  AND
	   		 TILP.COD_LOCAL     = PL.COD_LOCAL 		AND
	         TILP.COD_PROD      = PL.COD_PROD  	AND
           TILP.COD_GRUPO_CIA  = P.COD_GRUPO_CIA AND
           TILP.COD_PROD      = P.COD_PROD ;


  regLab curProds%ROWTYPE;
  vCantMovProd NUMBER;
  vCodMotKardex CHAR(3);
  BEGIN
   OPEN curProds;
     LOOP
	   FETCH curProds INTO regLab;
	   EXIT WHEN curProds%NOTFOUND;
	--Actualiza el stock actual de cada producto
	UPDATE LGT_PROD_LOCAL SET FEC_MOD_PROD_LOCAL = SYSDATE, USU_MOD_PROD_LOCAL = cIdUsu_in,
		   STK_FISICO    = regLab.CANT_TOMA_INV_PROD
	WHERE  COD_GRUPO_CIA = regLab.COD_GRUPO_CIA      AND
		   COD_LOCAL     = regLab.COD_LOCAL          AND
		   COD_PROD      = regLab.COD_PROD;

  -- Actualiza el VAL_PREC_PROM
  -- Usuario     fecha    Comentario
  -- dubilluz  09/07/207   CREACION
  UPDATE LGT_TOMA_INV_LAB_PROD P
  SET    P.VAL_PREC_PROM =regLab.Val_Prec_Prom
   WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    P.COD_LOCAL     = cCodLocal_in
      AND    P.SEC_TOMA_INV  = P.SEC_TOMA_INV
      AND    P.COD_PROD      = regLab.Cod_Prod ;
 --Guarda un registro del movimiento en el Kardex
  vCantMovProd:=regLab.CANT_TOMA_INV_PROD-regLab.STK_FISICO;

  IF vCantMovProd<>0 THEN
  	 BEGIN
	 	  IF vCantMovProd > 0 THEN
  	 	  	 vCodMotKardex:=COD_MOT_KARDEX_AJUSTE_POS;
  		  END IF;

  		  IF vCantMovProd < 0 THEN
  	 	  	 vCodMotKardex:=COD_MOT_KARDEX_AJUSTE_NEG;
  		  END IF;

  		  Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
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
    END LOOP;
	CLOSE curProds;
	--Descongela globalmente
	TI_DESCONG_GLOB_TOMA_INV(cCodGrupoCia_in,
							 cCodLocal_in ,
							 cSecToma_in,
							 cIdUsu_in);
	--Actualiza el estado de la toma (las 3 tablas)
  TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in ,
							  cCodLocal_in ,
							  cSecToma_in ,
							  EST_TOMA_INV_CARGADO,
							  cIdUsu_in);


	END;
   /**********************************************************************************************************/
   PROCEDURE TI_ANULA_TOMA_INV(cCodGrupoCia_in IN CHAR ,
                               cCodLocal_in    IN CHAR ,
							   cSecToma_in     IN CHAR ,
							   cIdUsu_in       IN CHAR)
   IS
   BEGIN
    --Descongela globalmente
	  TI_DESCONG_GLOB_TOMA_INV(cCodGrupoCia_in,
	  						   cCodLocal_in ,
							   cSecToma_in,
							   cIdUsu_in);
     --Actualiza el estado de la toma (las 3 tablas)
     TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in ,
	 						   cCodLocal_in ,
							   cSecToma_in ,
							   EST_TOMA_INV_ANULADO,
							   cIdUsu_in);
   END;
   /**********************************************************************************************************/
   FUNCTION TI_OBTIENE_IND_TOMA_INCOMPLETA(cCodGrupoCia_in IN CHAR ,
   										   cCodLocal_in    IN CHAR,
										   cSecToma_in     IN CHAR)
   RETURN CHAR
   IS
   vCant NUMBER;
   vInd CHAR(1);
   BEGIN
      SELECT TO_NUMBER(COUNT(*))
	  INTO   vCant
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
     /**********************************************************************************************************/
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
			TILP.COD_LAB       = L.COD_LAB 		 ;

	 RETURN curLabs;
 END;
  /******************************************************************************************************************/
  FUNCTION TI_LISTA_LABS_ITEMS_LAB
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
	OPEN curLabs FOR
     SELECT COD_LAB             || 'Ã' ||
 	 		NVL(NOM_LAB,' ')    || 'Ã' ||
			NVL(DIRECC_LAB,' ') || 'Ã' ||
 	 		NVL(TELEF_LAB,' ')
     FROM   LGT_LAB;
	 RETURN curLabs;
 END;
 /******************************************************************************************************************/
  FUNCTION TI_LISTA_ITEMS_LAB(cCodGrupoCia_in IN CHAR ,
  		   					  cCodLocal_in    IN CHAR ,
							  cCodLab_in      IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
	OPEN curLabs FOR
   SELECT PL.COD_PROD  						 || 'Ã' ||
   		  NVL(P.DESC_PROD,' ') 			 || 'Ã' ||
	   	  P.DESC_UNID_PRESENT 				 || 'Ã' ||
	   	  PL.UNID_VTA 		  	 	   	 	 || 'Ã' ||
		    '0'								 || 'Ã' ||
	   	  FLOOR((STK_FISICO/VAL_FRAC_LOCAL)) || 'Ã' ||
	   	  MOD(STK_FISICO, VAL_FRAC_LOCAL) 	 || 'Ã' ||
        ' '
-- 31/10/2007 Joliva
-- No se debe mostrar el Costo Promedio en locales
--	   	  TRIM(TRIM(TO_CHAR(p.val_prec_prom/VAL_FRAC_LOCAL,'99,999,990.99')))
FROM    LGT_PROD_LOCAL PL,LGT_PROD P
WHERE   PL.COD_GRUPO_CIA = cCodGrupoCia_in   AND
	  PL.COD_LOCAL     = cCodLocal_in 	   AND
	  P.COD_LAB        = cCodLab_in		   AND
    P.COD_PROD  NOT IN (SELECT COD_PROD FROM LGT_PROD_VIRTUAL WHERE EST_PROD_VIRTUAL = 'A') AND
	  PL.COD_GRUPO_CIA = P.COD_GRUPO_CIA   AND
	  PL.COD_PROD      = P.COD_PROD		 ;

	 RETURN curLabs;
 END;

  /******************************************************************************************************************/

  FUNCTION TI_LISTA_COD_LABORATORIOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLabs FarmaCursor;
  BEGIN
	--ERIOS 2.4.5 Cambios proyecto Conveniencia
  OPEN curLabs FOR
       SELECT DISTINCT L.COD_LAB || 'Ã' ||
              L.NOM_LAB
       FROM LGT_PROD P,
            LGT_LAB L,
           (
             SELECT DISTINCT COD_PROD
             FROM LGT_KARDEX K
             WHERE K.COD_LOCAL = Ccodlocal_In
                   AND K.COD_MOT_KARDEX != '100'
           ) V2
       WHERE p.cod_grupo_cia = Ccodgrupocia_In
       AND P.COD_PROD = V2.COD_PROD
       AND P.COD_LAB = L.COD_LAB  ;
   RETURN curLabs;
  END ;

 /******************************************************************************************************************/
 FUNCTION TI_LISTA_PROD_IMPRESION(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  cCodLab_in IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN
	--ERIOS 2.4.5 Cambios proyecto Conveniencia
 OPEN curLabs FOR
      SELECT DISTINCT P.COD_PROD  || 'Ã' ||
             p.desc_prod || 'Ã' ||
             p.desc_unid_present || 'Ã' ||
             pl.unid_vta
      FROM LGT_PROD P,
           LGT_LAB L,
           lgt_prod_local pl,
           (
             SELECT DISTINCT COD_PROD
             FROM LGT_KARDEX K
             WHERE K.COD_GRUPO_CIA=Ccodgrupocia_In
                   AND K.COD_LOCAL = Ccodlocal_In
                   AND K.COD_MOT_KARDEX != '100'
           ) V2
      WHERE PL.cod_grupo_cia =  Ccodgrupocia_In
      AND PL.COD_LOCAL = Ccodlocal_In
      AND P.COD_LAB = cCodLab_in
      AND PL.EST_PROD_LOC = ESTADO_ACTIVO
      AND P.EST_PROD = ESTADO_ACTIVO
      AND PL.COD_PROD = V2.COD_PROD
      AND P.COD_LAB = L.COD_LAB
      AND	PL.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND	PL.COD_PROD      = P.COD_PROD;
   RETURN curLabs;
  END ;

  /******************************************************************************************************************/
  FUNCTION TI_TOTAL_ITEMS_TOMA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in IN CHAR,
                               cSecToma_in CHAR)
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
/******************************************************************************************************************/
  FUNCTION TI_INFORMACION_VALORIZADA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecToma_in CHAR)
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
--                         (((LP.STK_ANTERIOR-LP.CANT_TOMA_INV_PROD) / NVL(LP.VAL_FRAC,1)) * PROD.VAL_PREC_PROM) FALTA
                         (((LP.STK_ANTERIOR-LP.CANT_TOMA_INV_PROD) / NVL(LP.VAL_FRAC,1)) * NVL(LP.VAL_PREC_PROM,PROD.VAL_PREC_PROM)) FALTA
                  FROM   LGT_TOMA_INV_LAB_PROD LP,
                         lgt_prod prod
                  WHERE  LP.COD_GRUPO_CIA = ccodgrupocia_in
                  AND    LP.COD_LOCAL = ccodlocal_in
                  AND    LP.sec_toma_inv = csectoma_in
                  AND    LP.CANT_TOMA_INV_PROD < LP.STK_ANTERIOR
                  AND    LP.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                  AND    LP.COD_PROD = PROD.COD_PROD)V2, --FALTANTE
                 (SELECT LP.COD_PROD COD,
--                         (((LP.CANT_TOMA_INV_PROD-LP.STK_ANTERIOR) / NVL(LP.VAL_FRAC,1)) * PROD.VAL_PREC_PROM) SOBRA
                         (((LP.CANT_TOMA_INV_PROD-LP.STK_ANTERIOR) / NVL(LP.VAL_FRAC,1)) * NVL(LP.VAL_PREC_PROM,PROD.VAL_PREC_PROM)) SOBRA
                  FROM   LGT_TOMA_INV_LAB_PROD LP,
                         LGT_PROD PROD
                  WHERE  LP.COD_GRUPO_CIA = ccodgrupocia_in
                  AND    LP.COD_LOCAL = ccodlocal_in
                  AND    LP.sec_toma_inv = csectoma_in
                  AND    LP.CANT_TOMA_INV_PROD > LP.STK_ANTERIOR
                  AND    LP.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                  AND    LP.COD_PROD = PROD.COD_PROD)V1-- SOBRANTE)
          WHERE  LP.COD_GRUPO_CIA = ccodgrupocia_in
          AND    LP.COD_LOCAL = ccodlocal_in
          AND    LP.sec_toma_inv =csectoma_in
          AND    LP.COD_PROD = V1.COD(+)
          AND    LP.COD_PROD = V2.COD(+)
          AND    LP.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND    LP.COD_PROD = P.COD_PROD;
    RETURN curTi;
  END;
  /******************************************************************************************************************/
  FUNCTION TI_DIFERENCIAS_CONSOLIDADO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cSecTomaInv_in IN CHAR)
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
--          	      TO_CHAR(((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) * P.VAL_PREC_PROM) / PL.VAL_FRAC_LOCAL,'999,990.00') || 'Ã' ||
          	      TO_CHAR(((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) * NVL(TILP.VAL_PREC_PROM,P.VAL_PREC_PROM)) / PL.VAL_FRAC_LOCAL,'999,990.00') || 'Ã' ||
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
/******************************************************************************************************************/
  FUNCTION TI_DIF_CONSOLIDADO_FILTRO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecTomaInv_in IN CHAR,
                                     cCodLab_in IN CHAR)
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
--          	      TO_CHAR((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) * P.VAL_PREC_PROM / PL.VAL_FRAC_LOCAL,'999,990.00') || 'Ã' ||
          	      TO_CHAR((NVL(CANT_TOMA_INV_PROD,0)-STK_ANTERIOR) * NVL(TILP.VAL_PREC_PROM,P.VAL_PREC_PROM) / PL.VAL_FRAC_LOCAL,'999,990.00') || 'Ã' ||
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

  FUNCTION TI_OBTIENE_IND_FOR_UPDATE(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecTomaInv_in IN CHAR,
                                     cIndProceso IN CHAR)
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
          EST_TOMA_INV = cindproceso FOR UPDATE ;
    RETURN curTi;
  END;

  FUNCTION TI_OBTIENE_PROD_MOVIMIENTO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      cCodLab_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTi FarmaCursor;
    meses_mov NUMBER ;
    fecha_anterior DATE ;

  BEGIN
       SELECT t.llave_tab_gral INTO meses_mov
       FROM   pbl_tab_gral t
       WHERE  t.id_tab_gral = '46';

       DBMS_OUTPUT.PUT_LINE (MESES_MOV);

      SELECT add_months(sysdate, -1 * meses_mov ) INTO fecha_anterior from dual ;

      DBMS_OUTPUT.PUT_LINE (FECHA_ANTERIOR);

		--ASOSA, 20.01.2011 - cambio de query hecho por RCASTRO
		--ERIOS 06.11.2014 Mejora de JLUNA
       OPEN curTi FOR
        SELECT PL.COD_PROD || 'Ã' ||    
              NVL(P.DESC_PROD,' ') || 'Ã' ||
              P.DESC_UNID_PRESENT || 'Ã' ||
              PL.UNID_VTA || 'Ã' ||
               '0'        || 'Ã' ||
              FLOOR((STK_FISICO/VAL_FRAC_LOCAL)) || 'Ã' ||
              MOD(STK_FISICO, VAL_FRAC_LOCAL) || 'Ã' ||
              TRIM(TRIM(TO_CHAR(p.val_prec_prom/VAL_FRAC_LOCAL,'99,999,990.99')))
        FROM   LGT_PROD_LOCAL PL,
               LGT_PROD P
        WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in
        AND  PL.COD_LOCAL     = cCodLocal_in
        AND  P.COD_LAB        = cCodLab_in
        AND  exists
          (SELECT 1
           FROM LGT_PROD_LOCAL
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND  COD_LOCAL     = cCodLocal_in
           AND  STK_FISICO > 0
		   and cod_prod=P.COD_PROD 
		   UNION all
		   SELECT 1
           FROM LGT_KARDEX
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
           AND  COD_LOCAL     = cCodLocal_in
           AND  FEC_KARDEX BETWEEN fecha_anterior AND SYSDATE
		   and cod_prod=P.COD_PROD 
          )
        AND    PL.COD_GRUPO_CIA = P.COD_GRUPO_CIA
        AND    PL.COD_PROD      = P.COD_PROD;


      RETURN curti;
   END;
/* *********************************************************** */
PROCEDURE TI_ACT_ESTADO_TOMA(cCodGrupoCia_in IN CHAR ,
            							   cCodLocal_in    IN CHAR ,
          									 cSecToma_in     IN CHAR ,
                             cCodLab_in      IN CHAR )
IS
 CANT_CEROS NUMBER(10);
 CANT_MAYOR_CERO NUMBER(10);
 CANT_PRODUCTOS NUMBER(10);
 CANT_NULOS NUMBER(10);
 ESTADO_NUEVO CHAR(1);

BEGIN
    SELECT  COUNT(1)
    INTO   CANT_PRODUCTOS
    FROM   LGT_TOMA_INV_LAB_PROD P
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    SEC_TOMA_INV = cSecToma_in
    AND    COD_LAB  = cCodLab_in;

    SELECT COUNT(1)
    INTO   CANT_CEROS
    FROM   LGT_TOMA_INV_LAB_PROD P
    WHERE  CANT_TOMA_INV_PROD = 0
    AND    COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    SEC_TOMA_INV = cSecToma_in
    AND    COD_LAB  = cCodLab_in;


    SELECT COUNT(1)
    INTO   CANT_MAYOR_CERO
    FROM   LGT_TOMA_INV_LAB_PROD P
    WHERE  CANT_TOMA_INV_PROD > 0
    AND    COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    SEC_TOMA_INV = cSecToma_in
    AND    COD_LAB  = cCodLab_in;

    SELECT COUNT(1)
    INTO   CANT_NULOS
    FROM   LGT_TOMA_INV_LAB_PROD P
    WHERE  CANT_TOMA_INV_PROD IS NULL
    AND    COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    SEC_TOMA_INV = cSecToma_in
    AND    COD_LAB  = cCodLab_in;


    IF CANT_NULOS = CANT_PRODUCTOS THEN
        ESTADO_NUEVO := EST_LAB_TOMA_EMITIDO;
                       dbms_output.put_line('sd 1');
    ELSE
        IF CANT_CEROS = CANT_PRODUCTOS OR
           CANT_MAYOR_CERO = CANT_PRODUCTOS OR
           (CANT_MAYOR_CERO + CANT_CEROS) = CANT_PRODUCTOS  THEN
              ESTADO_NUEVO := EST_LAB_TOMA_TERMINADO;
               dbms_output.put_line('sd 2');
        ELSE
              ESTADO_NUEVO := EST_LAB_TOMA_PROCESO;
               dbms_output.put_line('sd 3');
        END IF;
    END IF;
    dbms_output.put_line('sd '||ESTADO_NUEVO );
    UPDATE LGT_TOMA_INV_LAB E
    SET    E.EST_TOMA_INV_LAB = ESTADO_NUEVO
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in
    AND    SEC_TOMA_INV = cSecToma_in
    AND    COD_LAB  = cCodLab_in;


END;
/* ************************************************************* */
FUNCTION INV_GET_ESTADOS_LAB_TINV(cCodGrupoCia_in IN CHAR ,
            							        cCodLocal_in    IN CHAR ,
          									      cSecToma_in     IN CHAR )

  RETURN FarmaCursor
  IS
   curestado FarmaCursor;
   BEGIN
      OPEN curestado FOR
      SELECT *
      FROM (
      SELECT 'X'|| 'Ã' ||
             'TODOS LOS ESTADOS'
             || 'Ã' ||
             '1'
             FROM DUAL
      UNION
      SELECT
           DISTINCT(nvl(l.est_toma_inv_lab ,'E')) || 'Ã' ||
           CASE  nvl(l.est_toma_inv_lab ,EST_LAB_TOMA_EMITIDO)
				       WHEN EST_LAB_TOMA_EMITIDO   THEN 'Emitido'
			         WHEN EST_LAB_TOMA_PROCESO     THEN 'Proceso'
					     WHEN EST_LAB_TOMA_TERMINADO  THEN 'Terminado'
					   ELSE ' '
				    END
            || 'Ã' ||
             '2'
    		FROM   LGT_TOMA_INV_LAB l
    		WHERE  l.cod_grupo_cia = cCodGrupoCia_in
        AND    L.COD_LOCAL    =cCodLocal_in
        AND    L.SEC_TOMA_INV =  cSecToma_in
        )
      ORDER BY 1 DESC;
      RETURN curestado ;

  END  INV_GET_ESTADOS_LAB_TINV;

/* ************************************************************* */

FUNCTION TI_LISTA_LABS_TOMA_FILTRO(cCodGrupoCia_in IN CHAR ,
                 		  						 cCodLocal_in    IN CHAR ,
								                   cSecToma_in     IN CHAR,
                                   cEstado_in      IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN

 IF cEstado_in <> 'X' THEN
	OPEN curLabs FOR
   SELECT TIL.COD_LAB  || 'Ã' ||
       	  L.NOM_LAB    || 'Ã' ||
      	   CASE NVL(EST_TOMA_INV_LAB,EST_LAB_TOMA_EMITIDO)
      	    WHEN EST_LAB_TOMA_PROCESO THEN 'En proceso'
      	    WHEN EST_LAB_TOMA_EMITIDO THEN 'Emitido'
      		  WHEN EST_LAB_TOMA_TERMINADO THEN 'Terminado'
            ELSE ' '
      	   END
     FROM LGT_TOMA_INV_LAB TIL,
       	  LGT_LAB L
     WHERE TIL.COD_GRUPO_CIA = cCodGrupoCia_in
     AND   TIL.COD_LOCAL     = cCodLocal_in
     AND   TIL.SEC_TOMA_INV  = cSecToma_in
     AND   TIL.COD_LAB       = L.COD_LAB
     AND   TIL.EST_TOMA_INV_LAB =   cEstado_in     ;
ELSE
	OPEN curLabs FOR
   SELECT TIL.COD_LAB  || 'Ã' ||
       	  L.NOM_LAB    || 'Ã' ||
      	   CASE NVL(EST_TOMA_INV_LAB,EST_LAB_TOMA_EMITIDO)
      	    WHEN EST_LAB_TOMA_PROCESO THEN 'En proceso'
      	    WHEN EST_LAB_TOMA_EMITIDO THEN 'Emitido'
      		  WHEN EST_LAB_TOMA_TERMINADO THEN 'Terminado'
            ELSE ' '
      	   END
     FROM LGT_TOMA_INV_LAB TIL,
       	  LGT_LAB L
     WHERE TIL.COD_GRUPO_CIA = cCodGrupoCia_in
     AND   TIL.COD_LOCAL     = cCodLocal_in
     AND   TIL.SEC_TOMA_INV  = cSecToma_in
     AND   TIL.COD_LAB       = L.COD_LAB  ;
END IF;
	 RETURN curLabs;
 END;

/* *********************************************************************** */
FUNCTION TI_F_VAR_DATOS_PROD(
                             cCodGrupoCia_in IN CHAR ,
                  		  		 cCodLocal_in    IN CHAR ,
                             cCodProd_in     IN CHAR
                             )
 RETURN FarmaCursor
 IS
   curDatos FarmaCursor;
BEGIN
    OPEN curDatos FOR
    SELECT p.COD_PROD                   || 'Ã' ||
    		   NVL(P.DESC_PROD,' ')  	      || 'Ã' ||
           NVL(P.DESC_UNID_PRESENT,' ') || 'Ã' ||
           PL.VAL_FRAC_LOCAL            || 'Ã' ||
           NVL(PL.UNID_VTA,' ')      || 'Ã' ||
           lab.nom_lab
    FROM   LGT_PROD_LOCAL PL,
           LGT_PROD P,
           lgt_lab lab
    WHERE  p.cod_grupo_cia  = pl.cod_grupo_cia
    and    p.cod_prod       = pl.cod_prod
    and    pl.cod_grupo_cia = cCodGrupoCia_in
    and    pl.cod_local = cCodLocal_in
    and    pl.cod_prod  = cCodProd_in
    and    p.cod_lab = lab.cod_lab;

    RETURN curDatos;

END;
/* *********************************************************************** */
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
                                   )
IS
 vValFracLocal  lgt_prod_local.val_frac_local%type;
 vCodLab        lgt_prod.cod_lab%type;
 cCantToma_in  number;
 SecAuxConteo NUMBER(8);
BEGIN

--AGREGO
   SecAuxConteo := TI_F_GET_SEC_AUX_CONTEO(cCodGrupoCia_in,
                                     cCodLocal_in,
                                     cSecTomaInv_in
                                     );
    dbms_output.put_line('Error2: '||Sqlerrm);
    dbms_output.put_line('SecAuxConteo: '||SecAuxConteo);
 INSERT INTO AUX_LGT_PROD_TOMA_CONTEO
  (COD_GRUPO_CIA, COD_LOCAL, SEC_TOMA_INV, COD_PROD, COD_BARRA, CANTIDAD_ENTERO,
   CANTIDAD_FRACCION, USU_CREA_CONTEO, FEC_CREA_CONTEO, IP_CREA_CONTEO,
   IND_NO_FOUND, SEC_AUX_CONTEO)
   VALUES
   (cCodGrupoCia_in, cCodLocal_in, cSecTomaInv_in, cCodProd_in, cCodBarra_in, nCantEntero_in,
   nCantFraccion_in,  cUsuConteo_in, SYSDATE, vIp_in,
   --vIndNoFound_in, nSecAuxConteo_in );
   vIndNoFound_in,SecAuxConteo );

  ----------------------------------
  --------------------------------------------------------------
  ---dubilluz - 28.12.2009
  --inicio
  select p.val_frac_local,mp.cod_lab
  into   vValFracLocal,vCodLab
  from   lgt_prod_local p,
         lgt_prod mp
  where  p.cod_grupo_cia = cCodGrupoCia_in
  and    p.cod_local     = cCodLocal_in
  and    p.cod_prod      = cCodProd_in
  and    p.cod_grupo_cia = mp.cod_grupo_cia
  and    p.cod_prod = mp.cod_prod;

  cCantToma_in := nCantEntero_in*vValFracLocal +  nCantFraccion_in;

  UPDATE LGT_TOMA_INV_LAB_PROD
     SET FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,
         CANT_TOMA_INV_PROD        = nvl(CANT_TOMA_INV_PROD,0) + cCantToma_in,
         EST_TOMA_INV_LAB_PROD     = EST_TOMA_INV_PROCESO,
         USU_MOD_TOMA_INV_LAB_PROD = cUsuConteo_in
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
     AND COD_LOCAL = cCodLocal_in
     AND SEC_TOMA_INV = cSecTomaInv_in
     AND COD_LAB = vCodLab
     AND COD_PROD = cCodProd_in;

  --ACTUALIZA ESTADO
  TI_ACT_ESTADO_TOMA(cCodGrupoCia_in, cCodLocal_in, cSecTomaInv_in, vCodLab);
  -------
   ---dubilluz - 28.12.2009
  --FIN
END;
/* *********************************************************************** */
FUNCTION TI_F_GET_SEC_AUX_CONTEO(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cSecTomaInv_in IN CHAR
                                     )
RETURN NUMBER
IS
 vSecAuxConteo NUMBER(8);
 vSec NUMBER(8);
/*
 CURSOR c1 IS

    SELECT  T.Sec_Toma_Inv
     FROM AUX_LGT_PROD_TOMA_CONTEO T
    WHERE T.cod_grupo_cia = cCodGrupoCia_in
      AND T.cod_local = cCodLocal_in
      AND T.sec_toma_inv = cSecTomaInv_in
      FOR UPDATE;*/

BEGIN
 BEGIN

/*   select c.sec_toma_inv into vSec
     from LGT_TOMA_INV_CAB c
    WHERE c.cod_grupo_cia = '&cia'
      AND c.cod_local = '&local'
      AND c.sec_toma_inv = '&sec_toma_inv'
      FOR UPDATE;*/
   UPDATE LGT_TOMA_INV_CAB C
      SET C.SEC_AUX_CONTEO = NVL(C.SEC_AUX_CONTEO,0) + 1
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND C.COD_LOCAL = cCodLocal_in
      AND C.SEC_TOMA_INV = cSecTomaInv_in;

   --AQUI MODIFICAR EL UPDATE DE LA CABECERA DE TOMA_TRADICIONAL
/*     SELECT NVL(MAX(TO_NUMBER(tc.sec_aux_conteo)), 0)
      INTO vSecAuxConteo
      FROM AUX_LGT_PROD_TOMA_CONTEO tc
     WHERE tc.cod_grupo_cia = cCodGrupoCia_in
       AND tc.cod_local = cCodLocal_in
       AND tc.sec_toma_inv = cSecTomaInv_in;
       --AND tc.Ind_Proceso_Toma = 'N';  --Procesado
*/
     SELECT IC.SEC_AUX_CONTEO
       INTO vSecAuxConteo
       FROM LGT_TOMA_INV_CAB IC
      WHERE IC.COD_GRUPO_CIA = cCodGrupoCia_in
        AND IC.COD_LOCAL = cCodLocal_in
        AND IC.SEC_TOMA_INV = cSecTomaInv_in;
       dbms_output.put_line('cSecTomaInv_in1: '||cSecTomaInv_in);
/*     IF vSecAuxConteo = 0 THEN
        vSecAuxConteo := 1;
     ELSE
        vSecAuxConteo := vSecAuxConteo+1;
     END IF;
*/
     EXCEPTION
      WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('Error1: '||Sqlerrm);
       vSecAuxConteo := 1;
      WHEN OTHERS THEN
        dbms_output.put_line('Error2: '||Sqlerrm);
       ROLLBACK;
 END;
  dbms_output.put_line('vSecAuxConteo: '||vSecAuxConteo);
  RETURN vSecAuxConteo;
END;

/* *********************************************************************** */
FUNCTION TI_F_CUR_LIS_CONTEO_TOMA(cCodGrupoCia_in IN CHAR,
                    		   			  cCodLocal_in	 IN CHAR,
                                  cSecTomaInv_in IN CHAR,
                                  cUsuConteo_in IN CHAR)
  RETURN FarmaCursor
  IS
    curConteoToma FarmaCursor;
    v_Ip VARCHAR2(20);

  BEGIN

    SELECT sys_context( 'USERENV', 'IP_ADDRESS')
      INTO v_Ip
      FROM dual;

    OPEN curConteoToma FOR
    /*select q.datos
    from   (
            select v.datos,rownum orden
            from   (
             SELECT NVL(TC.COD_PROD,'') || 'Ã' ||
                    NVL(P.DESC_PROD,'') || 'Ã' ||
                    NVL(P.DESC_UNID_PRESENT,'') || 'Ã' ||
                    NVL(TC.CANTIDAD_ENTERO,'') || 'Ã' ||
                    NVL(TC.CANTIDAD_FRACCION,'') || 'Ã' ||
                    nvl(lab.nom_lab,'')|| 'Ã' ||
                    to_char(tc.fec_crea_conteo,'hh24:MM:SS') || 'Ã' ||
                    NVL(TC.SEC_AUX_CONTEO,'') || 'Ã' ||
                    NVL(TC.COD_BARRA,'')datos
             FROM  AUX_LGT_PROD_TOMA_CONTEO TC,
                   LGT_PROD P,
                   lgt_lab lab
             WHERE TC.COD_GRUPO_CIA = cCodGrupoCia_in
               AND TC.COD_LOCAL = cCodLocal_in
               AND TC.SEC_TOMA_INV = cSecTomaInv_in
               AND TC.COD_PROD = P.COD_PROD
               AND TC.IND_PROD_BORRA = 'N'
               AND TC.USU_CREA_CONTEO = cUsuConteo_in
               AND P.COD_LAB = LAB.COD_LAB
             ORDER BY TC.SEC_AUX_CONTEO desc
             ) v
           )q
     where q.orden <= 100;*/
     select NVL(TC.COD_PROD,'') || 'Ã' ||
                    NVL(P.DESC_PROD,'') || 'Ã' ||
                    NVL(P.DESC_UNID_PRESENT,'') || 'Ã' ||
                    NVL(TC.CANTIDAD_ENTERO,'') || 'Ã' ||
                    NVL(TC.CANTIDAD_FRACCION,'') || 'Ã' ||
                    nvl(lab.nom_lab,'')|| 'Ã' ||
                    to_char(tc.fec_crea_conteo,'hh24:Mi:SS') || 'Ã' ||
                    NVL(TC.SEC_AUX_CONTEO,'') || 'Ã' ||
                    NVL(TC.COD_BARRA,'')datos
    from   (
             SELECT TC.COD_GRUPO_CIA,TC.COD_PROD,TC.CANTIDAD_ENTERO,TC.CANTIDAD_FRACCION,TC.FEC_CREA_CONTEO,TC.SEC_AUX_CONTEO,TC.COD_BARRA,
                   RANK() OVER ( order by SEC_AUX_CONTEO desc) orden
             FROM  PTOVENTA.AUX_LGT_PROD_TOMA_CONTEO TC
             WHERE TC.COD_GRUPO_CIA = cCodGrupoCia_in
               AND TC.COD_LOCAL = cCodLocal_in
               AND TC.SEC_TOMA_INV = cSecTomaInv_in
               AND TC.IND_PROD_BORRA = 'N'
               AND TC.USU_CREA_CONTEO = cUsuConteo_in
               AND TC.IP_CREA_CONTEO = v_Ip
           ) TC,
           LGT_PROD P,
           lgt_lab lab
    where TC.orden <= 100
    AND TC.COD_GRUPO_CIA=P.COD_GRUPO_CIA
    AND TC.COD_PROD = P.COD_PROD
    AND P.COD_LAB = LAB.COD_LAB
    ORDER BY TC.SEC_AUX_CONTEO DESC; --JMIRANDA 17.01.10

    RETURN curConteoToma;
  END;

/* *********************************************************************** */
PROCEDURE TI_P_DEL_CONTEO_TOMA(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	 IN CHAR,
                                        cSecTomaInv_in IN CHAR,
                                        cAuxSecConteo_in  IN NUMBER,
--                                        cCantEntMod_in      IN CHAR,
  --                                      cCantFraccionMod_in IN CHAR,
                                        cUsuMod_in IN CHAR,
                                        vIp_in IN VARCHAR,
                                        cIndEliminado IN CHAR DEFAULT 'S'
                               )
IS
  Cant_Ent_Mod CHAR := '0';
  Cant_Fraccion_Mod CHAR := '0';
BEGIN
 --elimina
  TI_P_UPD_CONTEO_TOMA(cCodGrupoCia_in,
                       cCodLocal_in,
                       cSecTomaInv_in,
                       cAuxSecConteo_in,
                       Cant_Ent_Mod,
                       Cant_Fraccion_Mod,
                       cUsuMod_in,
                       vIp_in,
                       cIndEliminado);

END;

/* *********************************************************************** */
 PROCEDURE TI_P_UPD_CONTEO_TOMA(cCodGrupoCia_in IN CHAR,
                          		   			  cCodLocal_in	   IN CHAR,
                                        cSecTomaInv_in   IN CHAR,
                                        cAuxSecConteo_in    IN NUMBER,
                                        cCantEntMod_in      IN CHAR,
                                        cCantFraccionMod_in IN CHAR,
                                        cUsuMod_in IN CHAR,
                                        vIp_in IN VARCHAR,
                                        cIndEliminado IN CHAR DEFAULT 'N'
                                   )
 IS
  --vCodLab CHAR;
  vCantEntAnt CHAR;
  vCantFraccionAnt CHAR;

 vValFracLocal  lgt_prod_local.val_frac_local%type;
 vCodLab        lgt_prod.cod_lab%type;
 cCodProd_in    lgt_prod_local.cod_prod%type;
 cCantTomaNew_in  number;
 cCantTomaOld_in  number;

 BEGIN



  --------------------------------------------------------------
  ---dubilluz - 28.12.2009
  --inicio
  select COD_PROD
  into   cCodProd_in
  from   AUX_LGT_PROD_TOMA_CONTEO tc
   WHERE TC.COD_GRUPO_CIA = cCodGrupoCia_in
     AND TC.COD_LOCAL = cCodLocal_in
     AND TC.SEC_TOMA_INV = cSecTomaInv_in
     AND TC.SEC_AUX_CONTEO = cAuxSecConteo_in
     AND TC.IP_CREA_CONTEO = vIp_in;

  select p.val_frac_local,mp.cod_lab
  into   vValFracLocal,vCodLab
  from   lgt_prod_local p,
         lgt_prod mp
  where  p.cod_grupo_cia = cCodGrupoCia_in
  and    p.cod_local     = cCodLocal_in
  and    p.cod_prod      = cCodProd_in
  and    p.cod_grupo_cia = mp.cod_grupo_cia
  and    p.cod_prod = mp.cod_prod;

  cCantTomaNew_in := cCantEntMod_in*vValFracLocal +  cCantFraccionMod_in;

  select TC.CANTIDAD_ENTERO*vValFracLocal +  TC.CANTIDAD_FRACCION
  into   cCantTomaOld_in
  from   AUX_LGT_PROD_TOMA_CONTEO tc
   WHERE TC.COD_GRUPO_CIA = cCodGrupoCia_in
     AND TC.COD_LOCAL = cCodLocal_in
     AND TC.SEC_TOMA_INV = cSecTomaInv_in
     AND TC.SEC_AUX_CONTEO = cAuxSecConteo_in
     AND TC.IP_CREA_CONTEO = vIp_in;

  UPDATE LGT_TOMA_INV_LAB_PROD
     SET FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,
         CANT_TOMA_INV_PROD        = CANT_TOMA_INV_PROD + (cCantTomaNew_in - cCantTomaOld_in),
         EST_TOMA_INV_LAB_PROD     = EST_TOMA_INV_PROCESO,
         USU_MOD_TOMA_INV_LAB_PROD = cUsuMod_in
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
     AND COD_LOCAL = cCodLocal_in
     AND SEC_TOMA_INV = cSecTomaInv_in
     AND COD_LAB = vCodLab
     AND COD_PROD = cCodProd_in;

  --ACTUALIZA ESTADO
  TI_ACT_ESTADO_TOMA(cCodGrupoCia_in, cCodLocal_in, cSecTomaInv_in, vCodLab);
  ---dubilluz - 28.12.2009
  --FIN

   UPDATE AUX_LGT_PROD_TOMA_CONTEO TC

   SET TC.CANTIDAD_ENTERO = cCantEntMod_in,
       TC.CANTIDAD_FRACCION = cCantFraccionMod_in,
       TC.USU_MOD_CONTEO = cUsuMod_in,
       TC.FEC_MOD_CONTEO = SYSDATE,
       TC.IP_MOD_CONTEO = vIp_in,
       TC.IND_PROD_BORRA = cIndEliminado

   WHERE TC.COD_GRUPO_CIA = cCodGrupoCia_in
     AND TC.COD_LOCAL = cCodLocal_in
     AND TC.SEC_TOMA_INV = cSecTomaInv_in
     AND TC.SEC_AUX_CONTEO = cAuxSecConteo_in
          AND TC.IP_CREA_CONTEO = vIp_in;

 END;

/* *********************************************************************** */

 PROCEDURE TI_RELLENA_CERO_TOMA_INV(cCodGrupoCia_in IN CHAR ,
  										 cCodLocal_in    IN CHAR ,
										 cSecToma_in     IN CHAR )
  IS
  BEGIN

   for uu in
            (
            select t.cod_lab
            from   lgt_toma_inv_lab t
            where  COD_GRUPO_CIA = cCodGrupoCia_in
            and    COD_LOCAL = cCodLocal_in
            and    SEC_TOMA_INV = cSecToma_in
            )loop

    UPDATE LGT_TOMA_INV_LAB_PROD
       SET FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,
           CANT_TOMA_INV_PROD        = 0
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL     = cCodLocal_in
       AND SEC_TOMA_INV  = cSecToma_in
       AND COD_LAB       = uu.cod_lab
       AND CANT_TOMA_INV_PROD IS NULL;

    --ACTUALIZA EL ESTADO DE LA TOMA
    --07/01/2007 DUBILLUZ MODIFICACION
    TI_ACT_ESTADO_TOMA(cCodGrupoCia_in,
                       cCodLocal_in,
                       cSecToma_in,
                       uu.cod_lab);

    end loop;
  END;
/* ********************************************************************** */
 FUNCTION TI_LISTA_DIF_TOMA_INV(cCodGrupoCia_in IN CHAR ,
           		  								cCodLocal_in    IN CHAR ,
          										  cSecToma_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN

	OPEN curLabs FOR
  SELECT
         lab.nom_lab|| 'Ã' ||
         TILP.COD_PROD || 'Ã' || NVL(P.DESC_PROD, ' ') || 'Ã' ||
         p.desc_unid_present || 'Ã' ||
         TRUNC(STK_ANTERIOR / PL.VAL_FRAC_LOCAL) || ' / ' ||
         DECODE(PL.VAL_FRAC_LOCAL,
                1,
                ' ',
                MOD(STK_ANTERIOR, PL.VAL_FRAC_LOCAL)) || 'Ã' ||
         TRUNC((NVL(CANT_TOMA_INV_PROD, 0) - STK_ANTERIOR) /
               PL.VAL_FRAC_LOCAL) || ' / ' ||
         DECODE(PL.VAL_FRAC_LOCAL,
                1,
                ' ',
                MOD((NVL(CANT_TOMA_INV_PROD, 0) - STK_ANTERIOR),
                    PL.VAL_FRAC_LOCAL)) || 'Ã' ||
         TO_CHAR(NVL(TILP.VAL_PREC_PROM, P.VAL_PREC_PROM) /
                 PL.VAL_FRAC_LOCAL,
                 '999,990.00')|| 'Ã' ||
                 lab.cod_lab  || 'Ã' ||
                 lab.nom_lab|| NVL(P.DESC_PROD, ' ') ||p.desc_unid_present
    FROM LGT_TOMA_INV_LAB_PROD TILP, LGT_PROD_LOCAL PL, LGT_PROD p,
         lgt_lab lab
   WHERE TILP.CANT_TOMA_INV_PROD IS NOT NULL
     AND TILP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND TILP.COD_LOCAL = cCodLocal_in
     AND TILP.SEC_TOMA_INV = cSecToma_in
     AND TILP.EST_TOMA_INV_LAB_PROD IN
         (EST_TOMA_INV_PROCESO, EST_TOMA_INV_EMITIDO)
     AND (NVL(CANT_TOMA_INV_PROD, 0) - STK_ANTERIOR) <> 0
     AND TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
     AND TILP.COD_LOCAL = PL.COD_LOCAL
     AND TILP.COD_PROD = PL.COD_PROD
     AND P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
     AND P.COD_PROD = PL.COD_PROD
     and p.cod_lab = lab.cod_lab;

  RETURN curLabs;
 END;

 /* ***************************************************************** */
 FUNCTION TI_lista_LAB_TOMA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cSecTomaInv_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLabs FarmaCursor;
  BEGIN
       OPEN curLabs FOR
       /*
       SELECT T.COD_LAB|| 'Ã' ||lab.nom_lab
       FROM   LGT_TOMA_INV_LAB T,
              lgt_lab lab
       WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
       AND   T.COD_LOCAL     = cCodLocal_in
       AND   T.SEC_TOMA_INV  = cSecTomaInv_in
       and   t.cod_lab = lab.cod_lab;
*/

   SELECT distinct lab.cod_lab  || 'Ã' ||lab.nom_lab
    FROM LGT_TOMA_INV_LAB_PROD TILP, LGT_PROD_LOCAL PL, LGT_PROD p,
         lgt_lab lab
   WHERE TILP.CANT_TOMA_INV_PROD IS NOT NULL
     AND TILP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND TILP.COD_LOCAL = cCodLocal_in
     AND TILP.SEC_TOMA_INV = cSecTomaInv_in
     AND TILP.EST_TOMA_INV_LAB_PROD IN
         (EST_TOMA_INV_PROCESO, EST_TOMA_INV_EMITIDO)
     AND (NVL(CANT_TOMA_INV_PROD, 0) - STK_ANTERIOR) <> 0
     AND TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
     AND TILP.COD_LOCAL = PL.COD_LOCAL
     AND TILP.COD_PROD = PL.COD_PROD
     AND P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
     AND P.COD_PROD = PL.COD_PROD
     and p.cod_lab = lab.cod_lab
     order by lab.nom_lab asc;

       RETURN curLabs;

  end;
  /* *************************************************************** */
 FUNCTION TI_LISTA_DIF_TOMA_LAB_INV(cCodGrupoCia_in IN CHAR ,
           		  								cCodLocal_in    IN CHAR ,
          										  cSecToma_in     IN CHAR,
                                cCodLab_in      IN CHAR)
RETURN FarmaCursor
 IS
   curLabs FarmaCursor;
 BEGIN

	OPEN curLabs FOR
  SELECT
         lab.nom_lab|| 'Ã' ||
         TILP.COD_PROD || 'Ã' || NVL(P.DESC_PROD, ' ') || 'Ã' ||
         p.desc_unid_present || 'Ã' ||
         TRUNC(STK_ANTERIOR / PL.VAL_FRAC_LOCAL) || ' / ' ||
         DECODE(PL.VAL_FRAC_LOCAL,
                1,
                ' ',
                MOD(STK_ANTERIOR, PL.VAL_FRAC_LOCAL)) || 'Ã' ||
         TRUNC((NVL(CANT_TOMA_INV_PROD, 0) - STK_ANTERIOR) /
               PL.VAL_FRAC_LOCAL) || ' / ' ||
         DECODE(PL.VAL_FRAC_LOCAL,
                1,
                ' ',
                MOD((NVL(CANT_TOMA_INV_PROD, 0) - STK_ANTERIOR),
                    PL.VAL_FRAC_LOCAL)) || 'Ã' ||
         TO_CHAR(NVL(TILP.VAL_PREC_PROM, P.VAL_PREC_PROM) /
                 PL.VAL_FRAC_LOCAL,
                 '999,990.00')|| 'Ã' ||
                 lab.cod_lab  || 'Ã' ||
                 lab.nom_lab|| NVL(P.DESC_PROD, ' ') ||p.desc_unid_present
    FROM LGT_TOMA_INV_LAB_PROD TILP, LGT_PROD_LOCAL PL, LGT_PROD p,
         lgt_lab lab
   WHERE TILP.CANT_TOMA_INV_PROD IS NOT NULL
     AND TILP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND TILP.COD_LOCAL     = cCodLocal_in
     AND TILP.SEC_TOMA_INV  = cSecToma_in
     AND TILP.COD_LAB       = cCodLab_in
     AND TILP.EST_TOMA_INV_LAB_PROD IN
         (EST_TOMA_INV_PROCESO, EST_TOMA_INV_EMITIDO)
     AND (NVL(CANT_TOMA_INV_PROD, 0) - STK_ANTERIOR) <> 0
     AND TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
     AND TILP.COD_LOCAL = PL.COD_LOCAL
     AND TILP.COD_PROD = PL.COD_PROD
     AND P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
     AND P.COD_PROD = PL.COD_PROD
     and p.cod_lab = lab.cod_lab
     ORDER BY lab.nom_lab|| NVL(P.DESC_PROD, ' ') ||p.desc_unid_present  ASC;

  RETURN curLabs;
 END;

   /***********************************************************************************************************************/
    PROCEDURE TI_P_ENVIA_EMAIL_NO_FOUND(cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR,
                                        cSecTomaInv_in      IN CHAR,
                                        cCodBarra_in        IN CHAR,
                                        cUsu_in             IN CHAR,
                                        cGlosaProd_in       IN VARCHAR2 default 'N'
                                        )
    IS

    vDirecEnvio VARCHAR2(500);
    mesg_body VARCHAR2(4000);
	  v_Asunto VARCHAR2(500);
    v_Titulo VARCHAR2(200);
    v_ip VARCHAR2(20);

    BEGIN

        SELECT TRIM(A.LLAVE_TAB_GRAL)  INTO vDirecEnvio
        FROM PBL_TAB_GRAL A
        WHERE A.ID_TAB_GRAL=327;

        SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
        FROM DUAL;

        v_Asunto := 'TOMA DE INVENTARIO TRADICIONAL - CODIGO DE BARRA NO EXISTE EN EL LOCAL ';
        v_Titulo := 'TOMA DE INVENTARIO TRADICIONAL.';

         mesg_body := mesg_body||
                  '<BR>'||'CODIGO DE BARRA NO EXISTE EN EL LOCAL. '||'<BR>'||
                  '<BR>'||'SECUENCIAL DE TOMA: '||cSecTomaInv_in||
                  '<BR>'||'CODIGO DE BARRA: '||cCodBarra_in||
                  '<BR>'||'GLOSA: '||cGlosaProd_in||
                  '<BR>'||'USUARIO: '||cUsu_in||
                  '<BR>'||'IP :'||v_ip||
                   '';

              FARMA_UTILITY.envia_correo(cCodGrupoCia_in,cCodLocal_in,
                                   vDirecEnvio, --address
                                   v_Asunto, --asunto
                                   v_Titulo, --titulo
                                   mesg_body, --cuerpo
                                   ''); --CC

             TI_P_INS_C_B_NO_FOUND(cCodGrupoCia_in, cCodLocal_in, cSecTomaInv_in,
                                   cCodBarra_in, cUsu_in, v_ip,cGlosaProd_in);

    END;

   /***********************************************************************************************************************/

   PROCEDURE TI_P_INS_C_B_NO_FOUND (cCodGrupoCia_in     IN CHAR,
                                    cCodLocal_in        IN CHAR,
                                    cSecTomaInv_in      IN CHAR,
                                    cCodBarra_in        IN CHAR,
                                    cUsu_in             IN CHAR,
                                    cIP_in              IN VARCHAR2,
                                    cGlosa_in           IN VARCHAR2 default 'N'
                                   )
   IS

   BEGIN
      INSERT INTO LGT_TOMA_COD_BARRA_NO_FOUND
      (COD_GRUPO_CIA, COD_LOCAL, SEC_TOMA_INV, COD_BARRA,
      USU_CREA_CONTEO, FEC_CREA_CONTEO, IP_CREA_CONTEO,GLOSA)
      VALUES
      (cCodGrupoCia_in, cCodLocal_in, cSecTomaInv_in, cCodBarra_in,
      cUsu_in,SYSDATE,cIP_in,cGlosa_in);

   END;

   /* ******************************************************************* */
   procedure ti_p_actualiza_indices is
     cursor cr1 is
       select a.index_name,
              'alter index PTOVENTA.' || A.index_name ||
              ' REBUILD TABLESPACE TS_PTOVENTA_IDX storage (initial 64K minextents 1 maxextents unlimited)' comando
         from all_indexes a
        where a.owner = 'PTOVENTA'
          and a.table_name in
              ('LGT_TOMA_INV_CAB', 'LGT_TOMA_INV_LAB',
               'LGT_TOMA_INV_LAB_PROD', 'AUX_LGT_PROD_TOMA_CONTEO',
               'LGT_TOMA_COD_BARRA_NO_FOUND');
     x cr1%rowtype;
   begin
     open cr1;
     loop
       fetch cr1
         into x;
       exit when cr1%notfound;
       execute immediate x.comando;
     end loop;
     close cr1;
   end;

END;
/

