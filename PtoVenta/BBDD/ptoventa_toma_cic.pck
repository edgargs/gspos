CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_TOMA_CIC" AS

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

  --Descripción: Agregar el campo lote en el detalle en ambos casos sea mayorista y el otro caso
  --Por: Rafael Bullon Mucha
  --Fecha: 16/02/2016
  
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
                                     cCodLab_in      IN CHAR,
                                     cTipoVentaMayorista IN INTEGER DEFAULT NULL)
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
--Descripcion: Muestra el consolidado de diferencias en por lote
--Agregado: por Rafael Bullon Mucha
--Fecha: 15/02/2016

  FUNCTION TI_DIFERENCIAS_CONSOL_LOTE(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecTomaInv_in  IN CHAR) 
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
 --Descripcion: Lista la diferencia de los productos en consolidado por filtro agregado el campo lote
 --Usuario: Rafael Bullon Mucha
 --Fecha: 18/02/2016
 FUNCTION TI_DIF_CONSOLIDADO_FILTRO_LT(cCodGrupoCia_in IN CHAR,
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

  --Descripcion: Muestra las diferencias entre la cantidad ingresada y el stock del producto en una toma ciclica por lote
  --Usuario:	Rafael Bullon Mucha
  --Fecha: 15/02/2016  
  FUNCTION TI_LISTA_DIF_PROD_INV_LOTE(cCodGrupoCia_in IN CHAR ,
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



/****************************************************************************/
  --Descripcion: Obtiene la lista de productos por lote en una toma de inventario
  --Creado por Rafael Bullon
  --Fecha. 28/01/2016

 FUNCTION TI_LISTA_PROD_LOTE_TOMA_INV(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cSecToma_in     IN CHAR,
                                     cCodLab_in      IN CHAR,
                                     cCodProd_in     IN CHAR)
 RETURN FarmaCursor;
 
 /****************************************************************************/
  --Descripcion: Obtiene la lista de productos por lote en una toma de inventario
  --Creado por Rafael Bullon
  --Fecha. 29/01/2016

 FUNCTION TI_F_GET_TIENE_MASTERPACK(cCodGrupoCia_in IN CHAR,
                                     cCodProd_in     IN CHAR)
 RETURN CHAR; 
 
 /****************************************************************************/
  --Descripcion: Valida si existe lote en la tabla 
  --Creado por Rafael Bullon
  --Fecha. 01/02/2016 
 FUNCTION TI_VALIDA_LOTE(cCodGrupoCia_in IN CHAR ,
 		  							                 cCodLocal_in    IN CHAR ,
	                                   cSecToma_in     IN CHAR ,
                                     cCodLab_in      IN CHAR,
                                     cCodProd_in     IN CHAR,
                                     cLote_in        IN CHAR                                   
                                     )
 RETURN CHAR; 
 
 /****************************************************************************/
  --Descripcion: Valida si existe lote en la tabla 
  --Creado por Rafael Bullon
  --Fecha. 03/02/2016  
  FUNCTION CIC_GRABA_LOTE_PROD(      cCodGrupoCia_in  IN CHAR,
 		  							                 cCodLocal_in    IN CHAR,	                                                                        
                                     cCodProd_in     IN CHAR,
                                     v_CodLab        IN CHAR,
                                     v_NumToma       IN CHAR,                                     
                                     v_lote          IN CHAR,
                                     v_cantidad      IN INTEGER,
                                     vIdUsu_in       IN CHAR
                                     )
RETURN INTEGER;

/****************************************************************************/
  --Descripcion: Valida si existe lote en la tabla 
  --Creado por Rafael Bullon
  --Fecha. 03/02/2016  
 FUNCTION CIC_MODIFICA_LOTE_PROD(    cCodGrupoCia_in  IN CHAR,
 		  							                 cCodLocal_in    IN CHAR,	                                                                        
                                     cCodProd_in     IN CHAR,
                                     v_CodLab        IN CHAR,
                                     v_NumToma       IN CHAR,                                     
                                     v_lote          IN CHAR,
                                     v_cantidad      IN INTEGER,
                                     vIdUsu_in       IN CHAR                                     
                                     )

  RETURN INTEGER;
 
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_TOMA_CIC" AS
  /****************************************************************************/
  PROCEDURE CIC_INICIALIZA_TOMA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    v_nCantPrioridad NUMBER;
    v_vFechaIni CHAR(10);
    v_vFechaFin CHAR(10);
    v_nCant NUMBER;
  BEGIN

  --BORRA LA TABLA INICIAL PARA EL LOCAL
    DELETE FROM LGT_AUX_INV_CIC
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    SELECT TO_NUMBER(LLAVE_TAB_GRAL) INTO v_nCant
    FROM   PBL_TAB_GRAL
    WHERE  ID_TAB_GRAL = 77
    AND    COD_APL = 'PTO_VENTA'
    AND    COD_TAB_GRAL = 'TOMA INVENTARIO CIC';

    v_vFechaIni := TO_CHAR(SYSDATE-30,'dd/MM/yyyy');
    v_vFechaFin := TO_CHAR(SYSDATE,'dd/MM/yyyy');

    INSERT INTO LGT_AUX_INV_CIC(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,COD_LAB,CANT_UNID_VTA,MON_TOT_VTA,IND_ORIGEN)
    SELECT DISTINCT v1.cia,
           v1.LOCAL,
           v1.cod_prod,
           v1.lab,
           v1.vta,
           v1.tot_vta,
           v1.origen
    FROM
    (
    SELECT C.COD_GRUPO_CIA CIA,
           cCodLocal_in LOCAL,
           C.COD_PROD COD_PROD,
           P.COD_LAB LAB,
           0 VTA,
           0 TOT_VTA,
           'P' ORIGEN,--PRIORIDAD,
           SUM(C.CANT_UNID_VTA)
    FROM   VTA_RES_VTA_ACUM_LOCAL C,
           LGT_PROD P,
           LGT_PROD_LOCAL PL
    WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    C.COD_LOCAL = cCodLocal_in
    AND    PL.STK_FISICO > 0
    AND    C.FEC_DIA_VTA BETWEEN TO_DATE(v_vFechaIni || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
    AND    TO_DATE(v_vFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    AND    C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
    AND    C.COD_PROD = P.COD_PROD
    AND    P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
    AND    P.COD_PROD = PL.COD_PROD
    GROUP BY C.COD_GRUPO_CIA ,
             cCodLocal_in,
             C.COD_PROD ,
             P.COD_LAB ,
             0 ,
             0 ,
             'P' --PRIORIDAD,
    ORDER BY 8 DESC)V1
    WHERE   ROWNUM <= (v_Ncant/2);

    SELECT COUNT(*)INTO v_nCantPrioridad
    FROM   LGT_AUX_INV_CIC
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND   COD_LOCAL = cCodLocal_in;

    INSERT INTO LGT_AUX_INV_CIC(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,COD_LAB,CANT_UNID_VTA,MON_TOT_VTA,IND_ORIGEN)
    SELECT v1.cia,
           v1.LOCAL,
           v1.cod_prod,
           v1.lab,
           v1.vta,
           v1.tot_vta,
           v1.origen
    FROM
    (
    SELECT PL.COD_GRUPO_CIA cia,
           PL.COD_LOCAL LOCAL,
           PL.COD_PROD cod_prod,
           L.COD_LAB lab,
           0 vta,
           0 tot_vta,
           'T' origen,
           decode(PL.VAL_PREC_VTA * PL.VAL_FRAC_LOCAL,0,0,PL.VAL_PREC_VTA * PL.VAL_FRAC_LOCAL)  prec
    FROM   LGT_PROD_LOCAL PL,
           LGT_PROD P,
           LGT_LAB L
    WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    PL.COD_LOCAL = cCodLocal_in
    AND    PL.EST_PROD_LOC = 'A'
    AND    PL.STK_FISICO > 0
    AND    P.COD_PROD NOT IN  (SELECT COD_PROD FROM LGT_AUX_INV_CIC)
    AND    PL.COD_GRUPO_CIA = P.COD_GRUPO_CIA
    AND    PL.COD_PROD = P.COD_PROD
    AND    P.COD_LAB = L.COD_LAB
    ORDER BY 8 DESC)v1
    WHERE   ROWNUM <= (v_Ncant - v_nCantPrioridad);

/*    --BORRA LA TABLA INICIAL PARA EL LOCAL
    DELETE FROM LGT_AUX_INV_CIC
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    --INSERTA LOS PRODUCTOS DEFINIDOS POR AUDITORIA
    INSERT INTO LGT_AUX_INV_CIC(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,COD_LAB,
    CANT_UNID_VTA,MON_TOT_VTA,IND_ORIGEN)
    SELECT C.COD_GRUPO_CIA,cCodLocal_in,C.COD_PROD,P.COD_LAB,0,0,'P'--PRIORIDAD
    FROM LGT_AUX_PROD_CIC C, LGT_PROD P
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND C.COD_PROD = P.COD_PROD;

    SELECT COUNT(*)
      INTO v_nCantPrioridad
    FROM LGT_AUX_INV_CIC
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in;

    --INSERTA PRODUCTOS DEL TIPO "A"
    v_vFechaIni := TO_CHAR(SYSDATE-30,'dd/MM/yyyy');
    v_vFechaFin := TO_CHAR(SYSDATE,'dd/MM/yyyy');
    TMP_REP_ERN.REP_DETERMINAR_TIPO(cCodGrupoCia_in, cCodLocal_in,
                                    v_vFechaIni,v_vFechaFin);

    INSERT INTO LGT_AUX_INV_CIC(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,COD_LAB,
    CANT_UNID_VTA,MON_TOT_VTA,IND_ORIGEN)
    SELECT V.COD_GRUPO_CIA,V.COD_LOCAL,V.COD_PROD,P.COD_LAB,V.CANT_UNID_VTA,V.MON_TOT_VTA,'T'--TIPO 'A'
    FROM
    ( SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
              NVL(SUM(CANT_UNID_VTA),0) AS CANT_UNID_VTA,
              NVL(SUM(MON_TOT_VTA),0) AS MON_TOT_VTA
      FROM VTA_RES_VTA_REP_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_DIA_VTA BETWEEN TO_DATE(v_vFechaIni || ' 00:00:00','dd/MM/yyyy HH24:MI:SS')
                                 AND TO_DATE(v_vFechaFin || ' 23:59:59','dd/MM/yyyy HH24:MI:SS')
            AND COD_PROD IN (SELECT COD_PROD FROM LGT_PROD_LOCAL_REP
                            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND COD_LOCAL = cCodLocal_in
                                  AND TIPO = 'A'
                                  AND COD_PROD NOT IN (SELECT COD_PROD
                                                        FROM LGT_AUX_PROD_CIC
                                                        WHERE COD_GRUPO_CIA = cCodGrupoCia_in)
                              )
       GROUP BY COD_GRUPO_CIA,COD_LOCAL,COD_PROD
       ORDER BY MON_TOT_VTA DESC ) V, LGT_PROD P
     WHERE V.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND V.COD_PROD = P.COD_PROD
          AND ROWNUM <= (1000-v_nCantPrioridad);
*/  END;
  /****************************************************************************/

  FUNCTION GET_LISTA_PROD_TOMA_CIC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
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
           --TO_CHAR(A.CANT_UNID_VTA,'9,999')|| 'Ã' ||
           --TO_CHAR(A.MON_TOT_VTA,'99,990.00')|| 'Ã' ||
           --DECODE(A.IND_ORIGEN,'T','A',' ')
           NVL(LR.TIPO,' ') || 'Ã' ||
           DECODE(C.IND_PROD_FRACCIONADO,'S',C.UNID_VTA,P.DESC_UNID_PRESENT)
    FROM   LGT_AUX_INV_CIC A, LGT_PROD P, LGT_LAB L,
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
    /*
  /Descripcion: Cambio del IN por el EXISTS
  /POR: Rafael Bullon Mucha
  /Fecha: 22/02/2016
  */
  FUNCTION GET_LISTA_PRODS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curLista FarmaCursor;
  BEGIN
    OPEN curLista FOR
    SELECT C.COD_PROD|| 'Ã' ||
           P.DESC_PROD|| 'Ã' ||
           P.DESC_UNID_PRESENT|| 'Ã' ||
           C.UNID_VTA|| 'Ã' ||
           to_char(C.STK_FISICO,'999,990.00') || 'Ã' ||
           --NVL(V.CANT_UNID_VTA,0)|| 'Ã' ||
           --NVL(V.MON_TOT_VTA,0)|| 'Ã' ||
           P.COD_LAB
    FROM LGT_PROD_LOCAL C,LGT_PROD P
         /*(SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
                 NVL(SUM(CANT_UNID_VTA),0) AS CANT_UNID_VTA,
                 NVL(SUM(MON_TOT_VTA),0) AS MON_TOT_VTA
          FROM   VTA_RES_VTA_REP_LOCAL
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL = cCodLocal_in
          AND    FEC_DIA_VTA BETWEEN TRUNC(SYSDATE-30)
          AND    TRUNC(SYSDATE)+0.99999
          GROUP BY COD_GRUPO_CIA,COD_LOCAL,COD_PROD) V    */
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND C.COD_PROD = P.COD_PROD
/*          AND C.COD_GRUPO_CIA = V.COD_GRUPO_CIA(+)
          AND C.COD_LOCAL = V.COD_LOCAL(+)
          AND C.COD_PROD = V.COD_PROD(+)
*/        /*  AND C.COD_PROD NOT IN (SELECT COD_PROD FROM LGT_AUX_INV_CIC
                                 WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND COD_LOCAL = cCodLocal_in)*/
          AND NOT EXISTS (SELECT 1 FROM LGT_AUX_INV_CIC AA
                                 WHERE AA.COD_GRUPO_CIA = cCodGrupoCia_in
                                 AND AA.COD_LOCAL = cCodLocal_in
                                 AND AA.COD_PROD = C.COD_PROD 
                          )
          ;   
    RETURN curLista;
  END;
  /****************************************************************************/
  PROCEDURE CIC_INSERTA_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR, nUnidVends_in IN NUMBER, nMontoTotal_in IN NUMBER,
  cCodLab_in IN CHAR)
  AS
  BEGIN
    INSERT INTO LGT_AUX_INV_CIC(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,COD_LAB,
    CANT_UNID_VTA,MON_TOT_VTA,IND_ORIGEN)
    VALUES( cCodGrupoCia_in,cCodLocal_in,cCodProd_in,cCodLab_in,
    nUnidVends_in,nMontoTotal_in,'M');--MANUAL
  END;
  /****************************************************************************/
  PROCEDURE CIC_BORRA_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cCodProd_in IN CHAR)
  AS
  BEGIN
    DELETE FROM LGT_AUX_INV_CIC
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;
  END;
  /****************************************************************************/
    --Descripción: Agregar el campo lote en el detalle en ambos casos sea mayorista y el otro caso
  --Por: Rafael Bullon Mucha
  --Fecha: 16/02/2016
  
    PROCEDURE CIC_GRABA_TOM_INV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  cMatriz_in IN CHAR, vIdUsu_in IN VARCHAR2)
  AS
    v_nNumToma LGT_TOMA_INV_CAB.SEC_TOMA_INV%TYPE;
    n_vNumera NUMBER;
    CURSOR curLabs IS
    SELECT COD_LAB,COUNT(COD_PROD) AS CANT_PROD
    FROM LGT_AUX_INV_CIC
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
      --CIC_GET_NUMERA_CIC(cCodGrupoCia_in,cCodLocal_in,vIdUsu_in,v_nNumToma);
      v_nNumToma := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV),8,'0','I' );
      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,COD_NUMERA_TOMA_INV, vIdUsu_in);
    END IF;

    INSERT INTO LGT_TOMA_INV_CAB(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,
                                 TIP_TOMA_INV,CANT_LAB_TOMA,EST_TOMA_INV,
                                 FEC_CREA_TOMA_INV,USU_CREA_TOMA_INV,
                                 IND_CICLICO)
    VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumToma,
                   'P',1,'E',
                   SYSDATE,vIdUsu_in,
                   'S');

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
      IF (Farma_utility.f_is_local_tipo_vta_m(cCodGrupoCia_in,cCodLocal_in)='S') THEN
              INSERT INTO LGT_TOMA_INV_LAB_PROD(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,COD_LAB,
                                               COD_PROD,DESC_UNID_VTA,VAL_FRAC,
                                               STK_ANTERIOR,
                                               EST_TOMA_INV_LAB_PROD,
                                               FEC_CREA_TOMA_INV_LAB_PROD,USU_CREA_TOMA_INV_LAB_PROD,LOTE)
              SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,v_nNumToma,C.COD_LAB,
                      C.COD_PROD,PL.UNID_VTA,PL.VAL_FRAC_LOCAL,
                      PL.STK_FISICO,
                      'E',
                      SYSDATE,vIdUsu_in, PLL.LOTE
              FROM LGT_AUX_INV_CIC C, LGT_PROD_LOCAL PL, LGT_PROD_LOCAL_LOTE PLL
              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND C.COD_LOCAL = cCodLocal_in
                    AND C.COD_LAB = v_rCurLab.COD_LAB
                    AND C.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                    AND C.COD_LOCAL = PL.COD_LOCAL
                    AND C.COD_PROD = PL.COD_PROD
                    AND C.COD_GRUPO_CIA =PLL.COD_GRUPO_CIA
                    AND C.COD_LOCAL = PLL.COD_LOCAL
                    AND C.COD_PROD = PLL.COD_PROD;
      ELSE 
              INSERT INTO LGT_TOMA_INV_LAB_PROD(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,COD_LAB,
                                               COD_PROD,DESC_UNID_VTA,VAL_FRAC,
                                               STK_ANTERIOR,
                                               EST_TOMA_INV_LAB_PROD,
                                               FEC_CREA_TOMA_INV_LAB_PROD,USU_CREA_TOMA_INV_LAB_PROD,LOTE)
              SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,v_nNumToma,C.COD_LAB,
                      C.COD_PROD,PL.UNID_VTA,PL.VAL_FRAC_LOCAL,
                      PL.STK_FISICO,
                      'E',
                      SYSDATE,vIdUsu_in,PTOVENTA_TOMA_INV.GET_SIN_LOTE
              FROM LGT_AUX_INV_CIC C, LGT_PROD_LOCAL PL
              WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND C.COD_LOCAL = cCodLocal_in
                    AND C.COD_LAB = v_rCurLab.COD_LAB
                    AND C.COD_GRUPO_CIA = PL.COD_GRUPO_CIA
                    AND C.COD_LOCAL = PL.COD_LOCAL
                    AND C.COD_PROD = PL.COD_PROD;
      END IF;


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
    --DBMS_OUTPUT.PUT_LINE('INVENTARIO CICLICO NUEVO: '||v_nNumToma);
  END;
  /****************************************************************************/
 FUNCTION TI_LISTA_TOMAS_INV(cCodGrupoCia_in IN CHAR,
 		  					             cCodLocal_in    IN CHAR)
 RETURN FarmaCursor
 IS
   curCic FarmaCursor;
 BEGIN
	OPEN curCic FOR
     SELECT SEC_TOMA_INV  || 'Ã' ||
        	  CASE   TIP_TOMA_INV
        		WHEN   'P' THEN 'PARCIAL'
        		ELSE 'TOTAL'
        		END	  || 'Ã' ||
			      TO_CHAR(FEC_CREA_TOMA_INV,'dd/MM/yyyy HH24:mi:ss') || 'Ã' ||
		        CASE NVL(EST_TOMA_INV,EST_TOMA_INV_EMITIDO)
				    WHEN EST_TOMA_INV_PROCESO THEN 'EN PROCESO'
	          WHEN EST_TOMA_INV_EMITIDO THEN 'EMITIDO'
				    WHEN EST_TOMA_INV_CARGADO THEN 'CARGADO'
			 	    WHEN EST_TOMA_INV_ANULADO THEN 'ANULADO'
		        END|| 'Ã' ||
            NVL(EST_TOMA_INV,' ')
     FROM  LGT_TOMA_INV_CAB
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
  		     COD_LOCAL     = cCodLocal_in	   AND
		       EST_TOMA_INV IN (EST_TOMA_INV_PROCESO,EST_TOMA_INV_EMITIDO)AND
           IND_CICLICO = EST_CICLICO;
	 RETURN curCic;
 END;
/****************************************************************************/

 FUNCTION TI_LISTA_LABS_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  						               cCodLocal_in    IN CHAR ,
                                 cSecToma_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curCic FarmaCursor;
 BEGIN
	OPEN curCic FOR
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
	 RETURN curCic;
 END;
/****************************************************************************/
    -- Modificado por: Rafael Bullo Mucha
    -- Fecha: 11/02/2016
 FUNCTION TI_LISTA_PROD_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  							                 cCodLocal_in    IN CHAR ,
	                                   cSecToma_in     IN CHAR ,
                                     cCodLab_in      IN CHAR ,
                                     cTipoVentaMayorista IN INTEGER DEFAULT NULL)
 RETURN FarmaCursor
 IS
   curCic FarmaCursor;
 BEGIN
	
  
  IF (cTipoVentaMayorista = 0) THEN  
     OPEN curCic FOR
        SELECT Distinct(TILP.COD_PROD)      || 'Ã' ||
             NVL(P.DESC_PROD,' ')  	    || 'Ã' ||
             NVL(P.DESC_UNID_PRESENT,' ') || 'Ã' ||
             DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(Trunc(CANT_TOMA_INV_PROD/pl.val_frac_local),'99,990')) || 'Ã' ||
             DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(MOD(CANT_TOMA_INV_PROD,pl.val_frac_local),'99,990')) || 'Ã' ||
             PL.VAL_FRAC_LOCAL || 'Ã' ||
             NVL(PL.UNID_VTA,' ')|| 'Ã' ||
             P.IND_LOTE_MAYORISTA --Agregar Campo Lote Mayorista por: Rafael Bullon 27/01/2016
      FROM LGT_TOMA_INV_LAB_PROD TILP,
           LGT_PROD_LOCAL PL,
           LGT_PROD P
      WHERE
           TILP.COD_GRUPO_CIA = cCodGrupoCia_in  AND
           TILP.COD_LOCAL     = cCodLocal_in     AND
           TILP.SEC_TOMA_INV  = cSecToma_in      AND
           TILP.COD_LAB       = cCodLab_in 		 AND
           PL.EST_PROD_LOC = ESTADO_ACTIVO AND
           TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
           TILP.COD_LOCAL     = PL.COD_LOCAL     AND
           TILP.COD_PROD      = PL.COD_PROD      AND
           P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA    AND
           P.COD_PROD = PL.COD_PROD      ;
     
       
   ELSIF(cTipoVentaMayorista=1) THEN
     OPEN curCic FOR
              SELECT COD_PROD  || 'Ã' ||
                 DESC_PROD || 'Ã' ||
                 DESC_UNID_PRESENT || 'Ã' ||
                 DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(Trunc(CANT_TOMA_INV_PROD/val_frac_local),'99,990')) || 'Ã' ||
                 DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(MOD(CANT_TOMA_INV_PROD,val_frac_local),'99,990')) || 'Ã' ||
                 VAL_FRAC_LOCAL || 'Ã' ||
                 NVL(UNID_VTA,' ') || 'Ã' ||
                 IND_LOTE_MAYORISTA         
            FROM (      
                             SELECT TILP.COD_PROD AS "COD_PROD",
                                     NVL(P.DESC_PROD,' ')  AS "DESC_PROD",
                                     NVL(P.DESC_UNID_PRESENT,' ') AS "DESC_UNID_PRESENT",
                                     SUM(CANT_TOMA_INV_PROD) AS "CANT_TOMA_INV_PROD",
                                     PL.VAL_FRAC_LOCAL AS "VAL_FRAC_LOCAL",
                                     NVL(PL.UNID_VTA,' ') AS "UNID_VTA",
                                     P.IND_LOTE_MAYORISTA AS "IND_LOTE_MAYORISTA"--Agregar Campo Lote Mayorista por: Rafael Bullon 27/01/2016
                              FROM LGT_TOMA_INV_LAB_PROD TILP,
                                   LGT_PROD_LOCAL PL,
                                   LGT_PROD P
                              WHERE
                                   TILP.COD_GRUPO_CIA = cCodGrupoCia_in  AND
                                   TILP.COD_LOCAL     = cCodLocal_in     AND
                                   TILP.SEC_TOMA_INV  = cSecToma_in      AND
                                   TILP.COD_LAB       = cCodLab_in 		 AND
                                   PL.EST_PROD_LOC = ESTADO_ACTIVO AND
                                   TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
                                   TILP.COD_LOCAL     = PL.COD_LOCAL     AND
                                   TILP.COD_PROD      = PL.COD_PROD      AND
                                   P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA    AND
                                   P.COD_PROD = PL.COD_PROD 
                             GROUP BY TILP.COD_PROD,P.DESC_PROD,P.DESC_UNID_PRESENT, PL.val_frac_local,PL.UNID_VTA,P.IND_LOTE_MAYORISTA
                    ); 
          
    END IF;
   

	 RETURN curCic;

 END;
/****************************************************************************/

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
    WHERE   COD_GRUPO_CIA = cCodGrupoCia_in AND
  		      COD_LOCAL     = cCodLocal_in    AND
  		      SEC_TOMA_INV  = cSecToma_in 	AND
  		      COD_LAB       = cCodLab_in 		AND
  		      COD_PROD      = cCodProd_in;
  END;
/****************************************************************************/

 FUNCTION TI_LISTA_MOVS_KARDEX(cCodGrupoCia_in IN CHAR ,
  		   					             cCodLocal_in    IN CHAR ,
								               cCod_Prod_in	   IN CHAR)
 RETURN FarmaCursor
 IS
   curCic FarmaCursor;
 BEGIN
	OPEN curCic FOR
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
   RETURN curcic;
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
/*
Muestra el consolidado de diferencias en por lote
Agregado: por Rafael Bullon Mucha
Fecha: 15/02/2016
*/

  FUNCTION TI_DIFERENCIAS_CONSOL_LOTE(cCodGrupoCia_in IN CHAR,
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
                  LOTE  || 'Ã' ||
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
 --Descripcion: Lista la diferencia de los productos en consolidado por filtro agregado el campo lote
 --Usuario: Rafael Bullon Mucha
 --Fecha: 18/02/2016
  FUNCTION TI_DIF_CONSOLIDADO_FILTRO_LT(cCodGrupoCia_in IN CHAR,
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
                  LOTE || 'Ã' ||
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
   BEGIN
	--Actualiza el estado de la toma (las 3 tablas)
  	TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in ,
							                cCodLocal_in ,
							                cSecToma_in ,
                              EST_TOMA_INV_CARGADO,
							                cIdUsu_in);
	END;
/****************************************************************************/

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
          IND_CICLICO = EST_CICLICO AND
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
   --Actualiza el estado de la toma (las 3 tablas)
   TI_ACTUALIZA_EST_TOMA_INV(cCodGrupoCia_in ,
						                 cCodLocal_in ,
					                   cSecToma_in ,
					                   EST_TOMA_INV_ANULADO,
					                   cIdUsu_in);
 END;
/****************************************************************************/

  PROCEDURE TI_RELLENA_CERO_LAB_TOMA_INV(cCodGrupoCia_in IN CHAR ,
  										                   cCodLocal_in    IN CHAR ,
                                         cSecToma_in     IN CHAR ,
                                         cCodLab_in      IN CHAR)
  IS
  BEGIN
   UPDATE LGT_TOMA_INV_LAB_PROD SET FEC_MOD_TOMA_INV_LAB_PROD = SYSDATE,
   		                              CANT_TOMA_INV_PROD=0
   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
          COD_LOCAL     = cCodLocal_in 	  AND
	        SEC_TOMA_INV  = cSecToma_in 	  AND
	        COD_LAB       = cCodLab_in 	  AND
	        CANT_TOMA_INV_PROD IS NULL;
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
  --Descripcion: Muestra las diferencias entre la cantidad ingresada y el stock del producto en una toma ciclica por lote
  --Usuario:	Rafael Bullon Mucha
  --Fecha: 15/02/2016  
  
  
  FUNCTION TI_LISTA_DIF_PROD_INV_LOTE(cCodGrupoCia_in IN CHAR ,
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
                    LOTE || 'Ã' ||
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
 v_count farmacursor ;
 x CHAR(8);
   BEGIN
     /*v_count := TI_OBTIENE_CANT_TOMA_CAB(ccodgrupocia_in,ccodlocal_in,csectoma_in);

     FETCH v_count INTO x;
         IF v_count%NOTFOUND  THEN
         dbms_output.put_line('entro');*/
            EXECUTE IMMEDIATE
            ' INSERT INTO lgt_toma_inv_cab@XE_'||ccodlocal_in ||
            ' (SELECT * ' ||
            ' FROM   lgt_toma_inv_cab ' ||
            ' WHERE  cod_grupo_cia = ' || ccodgrupocia_in ||
            ' AND    cod_local = ' || ccodlocal_in ||
            ' AND    sec_toma_inv = ' || csectoma_in || ')' ;
        /* ELSE
           ROLLBACK;
           RAISE_APPLICATION_ERROR(-20100,'No se pudo realizar el envio al local.\n La toma ya existe en el local o no existe coneccion');
           dbms_output.put_line('se cayo');
         END IF ;
         dbms_output.put_line('salio');     */
  END ;
/****************************************************************************/

 PROCEDURE TI_INSERT_TOMA_LAB(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cSecToma_in     IN CHAR)
 IS
 v_count farmacursor ;
 x CHAR(8);
   BEGIN
     /*v_count := TI_OBTIENE_CANT_TOMA_LAB(ccodgrupocia_in,ccodlocal_in,csectoma_in);

     FETCH v_count INTO x;
        IF v_count%NOTFOUND  THEN
        dbms_output.put_line('entro');*/
            EXECUTE IMMEDIATE
            ' INSERT INTO lgt_toma_inv_lab@XE_'||ccodlocal_in ||
            ' (SELECT * ' ||
            ' FROM   lgt_toma_inv_lab ' ||
            ' WHERE  cod_grupo_cia = ' || ccodgrupocia_in ||
            ' AND    cod_local = ' || ccodlocal_in ||
            ' AND    sec_toma_inv = ' || csectoma_in || ')' ;
         /*ELSE
           ROLLBACK;
           RAISE_APPLICATION_ERROR(-20100,'No se pudo realizar el envio al local.\n La toma ya existe en el local o no existe coneccion');
           dbms_output.put_line('se cayo');

         END IF ;
              dbms_output.put_line('salio');*/
  END ;
/****************************************************************************/

 PROCEDURE TI_INSERT_TOMA_LAB_PROD(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecToma_in     IN CHAR)
 IS
 v_count farmacursor ;
 x CHAR(8);
   BEGIN
    /* v_count := TI_OBTIENE_CANT_TOMA_LAB_PROD(ccodgrupocia_in,ccodlocal_in,csectoma_in);

     FETCH v_count INTO x;
         IF v_count%NOTFOUND  THEN
         dbms_output.put_line('entro');*/
            EXECUTE IMMEDIATE
            ' INSERT INTO lgt_toma_inv_lab_prod@XE_'||ccodlocal_in ||
            ' (SELECT * ' ||
            ' FROM   lgt_toma_inv_lab_prod ' ||
            ' WHERE  cod_grupo_cia = ' || ccodgrupocia_in ||
            ' AND    cod_local = ' || ccodlocal_in ||
            ' AND    sec_toma_inv = ' || csectoma_in || ')' ;
        /* ELSE
           ROLLBACK;
           RAISE_APPLICATION_ERROR(-20100,'No se pudo realizar el envio al local.\n La toma ya existe en el local o no existe coneccion');
           dbms_output.put_line('se cayo');


         END IF ;
        dbms_output.put_line('salio');*/
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
    -- Agregado por: Rafael Bullo Mucha
    -- Fecha: 28/01/2016

 FUNCTION TI_LISTA_PROD_LOTE_TOMA_INV(cCodGrupoCia_in IN CHAR ,
 		  							                 cCodLocal_in    IN CHAR ,
	                                   cSecToma_in     IN CHAR ,
                                     cCodLab_in      IN CHAR,
                                     cCodProd_in     IN CHAR)
 RETURN FarmaCursor
 IS
   curCicP FarmaCursor;
 BEGIN
	OPEN curCicP FOR
  SELECT TILP.COD_PROD      || 'Ã' ||
  		   NVL(P.DESC_PROD,' ')  	    || 'Ã' ||
         NVL(P.DESC_UNID_PRESENT,' ') || 'Ã' ||
         NVL(TILP.LOTE,' ')|| 'Ã' ||
         NVL(TO_CHAR(PLL.FECHA_VENCIMIENTO_LOTE,'DD/MM/YYYY'),' ')|| 'Ã' ||
         DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(Trunc(CANT_TOMA_INV_PROD/pl.val_frac_local),'99,990')) || 'Ã' ||
         DECODE(CANT_TOMA_INV_PROD,NULL,' ',TO_CHAR(MOD(CANT_TOMA_INV_PROD,pl.val_frac_local),'99,990'))
  FROM LGT_TOMA_INV_LAB_PROD TILP,
       LGT_PROD_LOCAL PL,
	     LGT_PROD P,
       LGT_PROD_LOCAL_LOTE  PLL
  WHERE
  	   TILP.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	     TILP.COD_LOCAL     = cCodLocal_in     AND
	     TILP.SEC_TOMA_INV  = cSecToma_in      AND
	     TILP.COD_LAB       = cCodLab_in 		 AND
       TILP.COD_PROD      = cCodProd_in   AND
       PL.EST_PROD_LOC = ESTADO_ACTIVO AND
       TILP.COD_GRUPO_CIA = PL.COD_GRUPO_CIA AND
	     TILP.COD_LOCAL     = PL.COD_LOCAL     AND
	     TILP.COD_PROD      = PL.COD_PROD      AND
	     P.COD_GRUPO_CIA = PL.COD_GRUPO_CIA    AND
	     P.COD_PROD = PL.COD_PROD              AND
       TILP.COD_GRUPO_CIA =PLL.COD_GRUPO_CIA AND
       TILP.COD_LOCAL     = PLL.COD_LOCAL    AND
       TILP.COD_PROD     = PLL.COD_PROD      AND
       TILP.LOTE     = PLL.LOTE      ;

	 RETURN curCicP;

 END;
 /****************************************************************************/
     -- Agregado por: Rafael Bullo Mucha
    -- Fecha: 29/01/2016
 FUNCTION TI_F_GET_TIENE_MASTERPACK(cCodGrupoCia_in IN CHAR,                                
                                     cCodProd_in IN CHAR
                                   
                                     )
RETURN CHAR
IS

 INDICADOR CHAR(1):= 'N';
BEGIN
 BEGIN
 
  SELECT DECODE(count(*),0,'N','S') 
  INTO INDICADOR
  FROM MAE_PROD_MASTER_PACK
  WHERE COD_GRUPO_CIA= cCodGrupoCia_in
  AND COD_PROD=cCodProd_in AND CANT_PACK  > 0 ;


     EXCEPTION
      WHEN OTHERS THEN
        INDICADOR:='N'
        ;
 END;
  RETURN INDICADOR;
END;
 /****************************************************************************/ 
      -- Agregado por: Rafael Bullo Mucha
    -- Fecha: 01/02/2016
 FUNCTION TI_VALIDA_LOTE(cCodGrupoCia_in IN CHAR ,
 		  							                 cCodLocal_in    IN CHAR ,
	                                   cSecToma_in     IN CHAR ,
                                     cCodLab_in      IN CHAR,
                                     cCodProd_in     IN CHAR,
                                     cLote_in        IN CHAR                                   
                                     )
RETURN CHAR
IS

 INDICADOR CHAR(1):= 'N';
BEGIN
 BEGIN
 
  SELECT DECODE(count(*),0,'N','S') 
  INTO INDICADOR
  FROM LGT_TOMA_INV_LAB_PROD TILP   
  WHERE
  	   TILP.COD_GRUPO_CIA = cCodGrupoCia_in  AND
	     TILP.COD_LOCAL     = cCodLocal_in     AND
	     TILP.SEC_TOMA_INV  = cSecToma_in     AND
	     TILP.COD_LAB       = cCodLab_in 		 AND
       TILP.COD_PROD      = cCodProd_in  AND
       TILP.LOTE          = UPPER(TRIM(cLote_in)) ;

     EXCEPTION
      WHEN OTHERS THEN
        INDICADOR:='N'
        ;
 END;
  RETURN INDICADOR;
END;

 /****************************************************************************/ 
      -- Agregado por: Rafael Bullo Mucha
    -- Fecha: 03/02/2016
 FUNCTION CIC_GRABA_LOTE_PROD(       cCodGrupoCia_in  IN CHAR,
 		  							                 cCodLocal_in    IN CHAR,	                                                                        
                                     cCodProd_in     IN CHAR,
                                     v_CodLab        IN CHAR,
                                     v_NumToma       IN CHAR,                                     
                                     v_lote          IN CHAR,
                                     v_cantidad      IN INTEGER,
                                     vIdUsu_in       IN CHAR
                                     
                                     )

RETURN INTEGER
IS

 INDICADOR INTEGER:= 0;
BEGIN
 BEGIN
 
        INSERT INTO LGT_TOMA_INV_LAB_PROD(COD_GRUPO_CIA,COD_LOCAL,SEC_TOMA_INV,COD_LAB,
                                       COD_PROD,DESC_UNID_VTA,VAL_FRAC,
                                       STK_ANTERIOR,
                                       EST_TOMA_INV_LAB_PROD,
                                       FEC_CREA_TOMA_INV_LAB_PROD,USU_CREA_TOMA_INV_LAB_PROD,LOTE,CANT_TOMA_INV_PROD)
                                       
        SELECT    PL.COD_GRUPO_CIA ,PL.COD_LOCAL, v_NumToma,v_CodLab,PL.COD_PROD,
              PL.UNID_VTA,PL.VAL_FRAC_LOCAL,
              0,
              'P',
              SYSDATE,vIdUsu_in,UPPER(v_lote),v_cantidad              
        FROM LGT_PROD_LOCAL PL 
        WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
              AND PL.COD_LOCAL = cCodLocal_in
              AND PL.COD_PROD= cCodProd_in;
              
       INDICADOR:=1;

     EXCEPTION
      WHEN OTHERS THEN
        INDICADOR:=0;
 END;
  RETURN INDICADOR;
END;
 /****************************************************************************/ 
  /****************************************************************************/ 
      -- Agregado por: Rafael Bullo Mucha
    -- Fecha: 03/02/2016
 FUNCTION CIC_MODIFICA_LOTE_PROD(    cCodGrupoCia_in  IN CHAR,
 		  							                 cCodLocal_in    IN CHAR,	                                                                        
                                     cCodProd_in     IN CHAR,
                                     v_CodLab        IN CHAR,
                                     v_NumToma       IN CHAR,                                     
                                     v_lote          IN CHAR,
                                     v_cantidad      IN INTEGER,
                                     vIdUsu_in       IN CHAR                                     
                                     )

RETURN INTEGER
IS

 INDICADOR INTEGER:= 0;
BEGIN
 BEGIN
 
        UPDATE LGT_TOMA_INV_LAB_PROD
        SET USU_MOD_TOMA_INV_LAB_PROD=vIdUsu_in,
            FEC_MOD_TOMA_INV_LAB_PROD=SYSDATE,
            CANT_TOMA_INV_PROD=v_cantidad
        WHERE
            COD_GRUPO_CIA = cCodGrupoCia_in AND
            COD_LOCAL = cCodLocal_in        AND
            COD_LAB       = v_CodLab	      AND
            SEC_TOMA_INV = v_NumToma       AND
            COD_PROD= cCodProd_in           AND
            LOTE=v_lote;
              
       INDICADOR:=1;

     EXCEPTION
      WHEN OTHERS THEN
        INDICADOR:=0;
 END;
  RETURN INDICADOR;
END;
 /****************************************************************************/ 
 
END;
/
