--------------------------------------------------------
--  DDL for Package PTOVENTA_REPORTE
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_REPORTE" AS

  TYPE FarmaCursor IS REF CURSOR;

  EST_PED_COBRADO  	  VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE:='C';

  C_C_EST_PED_VTA_COBRADO VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE := 'C';
  C_C_TIP_COMP_PAGO_FACTURA  VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := 'F';
  C_C_TIP_COMP_PAGO_BOLETA  VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := 'B';
  C_C_TIP_COMP_PAGO_GUIA  VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := 'G';
  C_C_TIP_COMP_PAGO_NOTA_CREDITO  VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := 'N';
  C_C_TIP_COMP_PAGO_TICKET  VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := 'T';
  C_C_INDICADOR_SI VTA_PEDIDO_VTA_CAB.IND_PEDIDO_ANUL%TYPE := 'S';
  C_C_INDICADOR_NO VTA_PEDIDO_VTA_CAB.IND_PEDIDO_ANUL%TYPE := 'N';
  C_C_TIP_PED_VTA_NORMAL VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE := '01';
  C_C_TIP_PED_VTA_DELIVERY VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE := '02';
  C_C_IND_TIP_COMP_PAGO_BOL VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := '01';
  C_C_IND_TIP_COMP_PAGO_FACT VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := '02';
  C_C_IND_TIP_COMP_PAGO_NC VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := '04';
  C_C_IND_TIP_COMP_PAGO_TICKET VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := '05';
  C_C_IND_TIP_COMP_PAGO_TIC_FAC VTA_PEDIDO_VTA_CAB.tip_comp_pago%TYPE := '06';

  C_C_TIP_PED_VTA_INSTITUCIONAL VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE := '03';
    g_nTipoFiltroPrincAct NUMBER(1) := 1;
  g_nTipoFiltroAccTerap NUMBER(1) := 2;
  g_nTipoFiltroLab NUMBER(1) := 3;

    C_CID_TAB_GRAL_NUM_DIAS    PBL_TAB_GRAL.ID_TAB_GRAL%TYPE := '109';


   ---Descripcion :Variables para tipo de Venta
   --Fecha       Usuario Comentario
   --28/11/2008  asolis  Creación

     C_C_TIPO_VENTA_MEZON         CHAR(2):='01';
     C_C_TIPO_VENTA_DELIVERY      CHAR(2):='02';
     C_C_TIPO_VENTA_INSTITUCIONAL CHAR(2):='03';

     VN_MAX_DIAS_REP      NUMBER := 40; --- 24-JUN-14 TCT ADD CONST


  --Descripcion: Lista el Registro de Ventas para el reporte de Registro de Ventas
  --Fecha       Usuario		Comentario
  --08/03/2006  Paulo     Creación
  FUNCTION REPORTE_REGISTRO_VENTA(cCodGrupoCia_in	IN CHAR,
  		   						  cCodLocal_in	  	IN CHAR,
  		   						  cFechaInicio      IN CHAR,
 		  						  cFechaFin         IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista el Detalle Registro de Ventas para el reporte de Registro de Ventas
  --Fecha       Usuario		Comentario
  --09/03/2006  Paulo     Creación
  FUNCTION REPORTE_DETALLE_REGISTRO_VENTA (cCodGrupoCia_in	IN CHAR,
  		   						  		   cCodLocal_in	  	IN CHAR,
  		   						  		   cCodNumeroPed IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista los Comprobantes de una venta para el reporte de Registro de Ventas
  --Fecha       Usuario		Comentario
  --10/03/2006  Paulo     Creación
  --15/07/2014  Rherrera  Modificación: Modificar el query del reporte.
  FUNCTION REPORTE_COMPROBANTES_VENTA(cCodGrupoCia_in	IN CHAR,
    		   						  cCodLocal_in	  	IN CHAR,
  		   							  cCodNumeroPed IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista los Detalles de los Comprobantes de una venta para el reporte de Registro de Ventas
  --Fecha       Usuario		Comentario
  --10/03/2006  Paulo     Creación
  --05/05/2014  RHERRERA  Mofidicación: muestra el detalle del comprabante asignado
  FUNCTION REPORTE_DETALLE_COMPROBANTE(cCodGrupoCia_in	IN CHAR,
  		   						  	  cCodLocal_in	  	IN CHAR,
									  cNumCompPago IN CHAR,
                    cNumPedVta IN CHAR DEFAULT NULL,
                    cTipComPago IN VARCHAR2
                    )
  RETURN FarmaCursor;
  --Descripcion: Obtiene los valores para el resumen del pedido
  --Fecha       Usuario		Comentario
  --13/03/2006  Paulo     Creación
  FUNCTION REPORTE_RESUMEN_VENTA (cCodGrupoCia_in	IN CHAR,
  		   						  cCodLocal_in	  	IN CHAR,
 		  						  cFechaInicio 		IN CHAR,
 		  						  cFechaFin 		IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: obtiene los valores para el resumen de las notas de credito
  --Fecha       Usuario		Comentario
  --07/06/2006  Paulo     Creación
  FUNCTION REPORTE_RESUMEN_VTA_NOT_CREDIT (cCodGrupoCia_in	IN CHAR,
  		   						  			 cCodLocal_in	  	IN CHAR,
 		  						  			 cFechaInicio 		IN CHAR,
 		  						  			 cFechaFin 			IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista la Relacion de formas de pago por local
  --Fecha       Usuario		Comentario
  --14/03/2006  Paulo     Creación
  FUNCTION REPORTE_FORMAS_DE_PAGO(cCodGrupoCia_in	IN CHAR,
  		   						  cCodLocal_in	  	IN CHAR,
 		  						  cFechaInicio 		IN CHAR,
 		  						  cFechaFin 		IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista la Relacion de productos vendidos por local
  --Fecha       Usuario		Comentario
  --15/03/2006  Paulo     Creación

  FUNCTION REPORTE_DETALLE_VENTAS  (cCodGrupoCia_in	IN CHAR,
    		   						cCodLocal_in	IN CHAR,
 			   						cFechaInicio 	IN CHAR,
 		  						    cFechaFin 		IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista el Resumen de productos vendidos por local
  --Fecha       Usuario		Comentario
  --15/03/2006  Paulo     Creación
  FUNCTION REPORTE_RESUMEN_PRODUCTOS_VEND (cCodGrupoCia_in	IN CHAR,
  		       		   					   cCodLocal_in	  	IN CHAR,
 					   					   cFechaInicio 	IN CHAR,
 		  						       	   cFechaFin 		IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista la relacion de productos vendidos por un filtro
  --Fecha       Usuario		Comentario
  --15/03/2006  Paulo     Creación
  FUNCTION REPORTE_FILTRO_PRODUCTOS_VEND(cCodGrupoCia_in	IN CHAR,
    		   						   	 cCodLocal_in	  	IN CHAR,
 			   						   	 cFechaInicio 		IN CHAR,
 		  						       	 cFechaFin 			IN CHAR,
									   	 cTipoFiltro      	IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista la relacion de ventas por vendedor
  --Fecha       Usuario		Comentario
  --17/03/2006  Paulo     Creación

  FUNCTION REPORTE_VENTAS_POR_VENDEDOR(cCodGrupoCia_in	IN CHAR,
        		   					   cCodLocal_in     IN CHAR,
 			   						   cFechaInicio 	IN CHAR,
 		  						       cFechaFin 		IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista la relacion de productos vendidos por vendedor
  --Fecha       Usuario		Comentario
  --20/03/2006  Paulo     Creación

  FUNCTION REPORTE_DETALLE_VENTAS_VEND(cCodGrupoCia_in	IN CHAR,
       		   					   	   cCodLocal_in     IN CHAR,
									   cFechaInicio 	IN CHAR,
 		  						       cFechaFin 		IN CHAR,
									   cUsuario_in      IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Lista la relacion de ventas por producto
  --Fecha       Usuario		Comentario
  --20/03/2006  MHUAYTA     Creación
   FUNCTION REPORTE_VENTAS_POR_PRODUCTO(cCodGrupoCia_in	IN CHAR,
        		   					   	cCodLocal_in    IN CHAR,
 			   						   	cFechaInicio 	IN CHAR,
										cFechaFin 		IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Lista la relacion de vendedores por producto
  --Fecha       Usuario		Comentario
  --20/03/2006  MHUAYTA     Creación
  FUNCTION REPORTE_VENDEDORES_PRODUCTO(cCodGrupoCia_in	IN CHAR,
        		   					   cCodLocal_in     IN CHAR,
									   cCodProd_in		IN CHAR,
 			   						   cFechaInicio 	IN CHAR,
 		  						       cFechaFin 		IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista la relacion de ventas por vendedor filtrado
  --Fecha       Usuario		Comentario
  --24/03/2006  MHUAYTA     Creación


  FUNCTION REPORTE_VENTAS_POR_PRODUCTO_F(cCodGrupoCia_in	    IN CHAR,
        		   					   	cCodLocal_in     	IN CHAR,
 			   						   	cFechaInicio 		IN CHAR,
 		  						       	cFechaFin 			IN CHAR,
										cTipoFiltro_in  	IN CHAR,
  		   						 		cCodFiltro_in 	 	IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista las relacion de ventas por dia
  --Fecha       Usuario		Comentario
  --27/03/2006  PAULO     Creación

  FUNCTION REPORTE_VETAS_POR_DIA(cCodGrupoCia_in IN CHAR,
        		   				 cCodLocal_in    IN CHAR,
 			   					 cFechaInicio 	 IN CHAR,
 		  						 cFechaFin 		 IN CHAR)

  RETURN FarmaCursor;

  --Descripcion: Lista el resumen detallado del resumen de venta
  --Fecha       Usuario		Comentario
  --09/06/2006  PAULO     Creación
  FUNCTION REPORTE_DETALLADO_RESUMEN_VTA (cCodGrupoCia_in	IN CHAR,
  		   						  			              cCodLocal_in	  	IN CHAR,
                                          cFechaInicio 	    IN CHAR,
 		  						  			                cFechaFin 		    IN CHAR,
                                          cCodProd_in 	    IN CHAr)
  RETURN FarmaCursor;

  --Descripcion: Lista el resumen de laboratorios con sus ventas y la cantridad vendida
  --Fecha       Usuario		Comentario
  --10/07/2006  PAULO     Creación
  FUNCTION REPORTE_VTA_PRODUCTO_LAB(cCodGrupoCia_in  	IN CHAR,
  		   						  			        cCodLocal_in	  	IN CHAR,
                                    cFechaInicio 	    IN CHAR,
 		  						  			          cFechaFin 		    IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Muestra un consolidado completo de la venta por producto de todos los vendedores
  --Fecha       Usuario		Comentario
  --07/09/2006  PAULO     Creación
  FUNCTION REPORTE_CONSOLIDADO_VTA_PROD(cCodGrupoCia_in  	IN CHAR,
   		   						  			              cCodLocal_in	  	IN CHAR,
                                          cFechaInicio_in 	IN CHAR,
   		  						  			              cFechaFin_in 		  IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: MUESTRA LAS VENTAS POR PRODUCTO  TARJETA VIRTUALO
  --Fecha       Usuario		Comentario
  --15/12/2006  PAULO     Creación
  FUNCTION REPORTE_VTA_PRODUCTO_VIRTUAL(cCodGrupoCia_in	 IN CHAR,
        		   					   	            cCodLocal_in     IN CHAR,
 			   						   	                cFechaInicio 	   IN CHAR,
 		  						       	              cFechaFin 		   IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Elimina un registro de la tabla LGT_PROD_LOCAL_FALTA_STK
  --Fecha       Usuario		Comentario
  --30/03/2007  Luis Reque     Creación

  PROCEDURE REPORTE_BORRAR_DET_FALTA_CERO(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cCodProd_in     IN CHAR,
                                          cFecha_in       IN CHAR,
                                          cSecUsuLocal_in IN CHAR);

  --Descripcion: Obtienes los pedidos que han sido anulados y no cobrados
  --Fecha       Usuario		     Comentario
  --23/04/2007  Luis Reque     Creación
  FUNCTION REPORTE_PEDIDOS_ANUL_NO_COB(cCodGrupoCia_in	IN CHAR,
  		   						                   cCodLocal_in	  	IN CHAR,
  		   						                   cFechaInicio     IN CHAR,
 		  						                     cFechaFin        IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene las unidades vendidas del local (filtro)
  --Fecha       Usuario		     Comentario
  --02/05/2007  Luis Reque     Creación
  FUNCTION REPORTE_UNID_VTA_LOCAL_FILTRO(cTipoFiltro_in  IN CHAR,
		   						                       cCodFiltro_in 	 IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene las unidades vendidas del local
  --Fecha       Usuario		     Comentario
  --02/05/2007  Luis Reque     Creación
  FUNCTION REPORTE_UNID_VTA_LOCAL RETURN FarmaCursor;

  --Descripcion: Obtiene los productos sin venta en N dias
  --Fecha       Usuario		     Comentario
  --02/05/2007  Luis Reque     Creación
  FUNCTION REPORTE_PROD_SIN_VTA_N_DIAS(cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene los productos sin venta en N dias (filtro)
  --Fecha       Usuario		     Comentario
  --02/05/2007  Luis Reque     Creación
  FUNCTION REPORTE_PROD_SIN_VTA_NDIAS_FIL(cCodLocal_in    IN CHAR,
                                          cTipoFiltro_in  IN CHAR,
		   						                        cCodFiltro_in 	IN CHAR) RETURN FarmaCursor;

  --Descripcion: Listado de lasd formas de pago por pedido
  --Fecha       Usuario		     Comentario
  --05/08/2007  jorge Cortez     Creación
  FUNCTION REPORTE_DETALLE_FORMAS_PAGO(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in	  	IN CHAR,
  		   							                 cNumPedVta IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene el Numero de Dias sin Venta para el reporte
  --Fecha       Usuario		     Comentario
  --21/08/2007  DUBILLUZ    Creación
  FUNCTION NUMERO_DIAS_SIN_VENTAS
  RETURN CHAR ;

/*----------------------------------------------------------------------------------------------------------------------------------------
Goal : Carga Auxliar de Ventas x Rango de Fechas (Maximo 40 Dias)
History : 23-JUN-14    TCT  Procesa por Rango de Fechas
-----------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_LOAD_VENTAS_VEN_LOC(cCodGrupoCia_in   IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cFecha_Ini_in   IN CHAR,
                                    cFecha_Fin_in   IN CHAR DEFAULT
                                    TO_CHAR(SYSDATE - 1,'dd/mm/yyyy'));


  --Descripcion: Lista para impresion
  --Fecha         Usuario		Comentario
  --11/06/2008   DUBILLUZ   creacion

  FUNCTION REPORTE_VENTAS_VENDEDOR_IMP(cCodGrupoCia_in	IN CHAR,
       		   					   	             cCodLocal_in     IN CHAR,
			                       					 cFechaInicio 	  IN CHAR,
 		  						                     cFechaFin 		    IN CHAR
                                       )
  RETURN FarmaCursor;



  --Descripcion: Actualiza la tabla de resumen de tipo de ventas por vendedor
  --Fecha         Usuario		Comentario
  --26/11/2008   ASOLIS   creacion

 /*---------------------------------------------------------------------------------------------------------------------------------------
 GOAL : Carga Temporales de Ventas
 History : 24-JUN-14   TCT   Agrega Tablas Temporales y Rango de Fechas
----------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE ACT_RES_VENTAS_VENDEDOR_TIPO(
                                        cCodGrupoCia_in     IN CHAR,
                                        cCodLocal_in        IN CHAR,
                                        cFecha_Ini_in       IN CHAR,
                                        cFecha_Fin_in       IN CHAR,
                                        cTipoVenta          IN CHAR
                                       );

  --Descripcion: Lista la relacion de tipo de ventas por vendedor
  --Fecha         Usuario		Comentario
  --26/11/2008   ASOLIS   creacion

  FUNCTION REPORTE_VENTAS_POR_VEND_TIPO(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in     IN CHAR,
 			   						                   cFechaInicio 	IN CHAR,
 		  						                     cFechaFin 		IN CHAR,
                                       cTipoVenta IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista la relacion de productos vendidos por vendedor
  --Fecha       Usuario		Comentario
  --26/11/2008   ASOLIS   creacion

  FUNCTION REPORTE_DET_VENTAS_VEND_TIPO(cCodGrupoCia_in	 IN CHAR,
       		   					   	              cCodLocal_in     IN CHAR,
									                      cFechaInicio 	 IN CHAR,
 		  						                      cFechaFin 	 IN CHAR,
									                      cUsuario_in      IN CHAR,
                                        cTipoVenta      IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista un reporte de guias presentes en el sistema
  --Fecha       Usuario		Comentario
  --28/MAR/2014 LLEIVA    creacion
  FUNCTION REPORTE_GUIAS(cCodGrupoCia_in	 IN CHAR,
       		   					   cCodLocal_in      IN CHAR,
									       cFecInicial_in 	 IN CHAR,
 		  						       cFecFinal_in 	   IN CHAR,
									       cNumGuia_in       IN CHAR,
                         cTipoGuia_in      IN CHAR)
  RETURN FarmaCursor;

/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : Generar el Reporte de Ventas x Vendedor para un rango de fecha (Maximo 40 Dias)
History : 23-JUN-14  TCT  Agrega proceso para carga de temporal
-------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION REP_VENTAS_POR_VEND_TEST(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in     IN CHAR,
 			   						                   cFechaInicio 	IN CHAR,
 		  						                     cFechaFin 		IN CHAR)
  RETURN CHAR;

/*******************************************************************************************************************************************/


  -- Descripcion: Lista reporte de venta delivery
  -- Fecha       Usuario		Comentario
  -- 17.07.2014  KMONCADA   creacion
  FUNCTION REPORTER_REG_VTA_DELIVERY (cCodGrupoCia_in	 IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cFecInicial_in 	 IN CHAR,
                                     cFecFinal_in 	   IN CHAR,
                                     cWhere_in         IN VARCHAR2) 
  RETURN FarmaCursor;
  
/*******************************************************************************************************************************************/  
  -- Descripcion: OBTIENE LOS CAMPOS DE UNA VISTA PARA REALIZAR FILTROS
  -- Fecha       Usuario		Comentario
  -- 17.07.2014  KMONCADA   creacion
  FUNCTION GET_CAMPOS_REPORTE(cTabla_in IN CHAR)
    RETURN FarmaCursor;


/*******************************************************************************************************************************************/    
  -- Descripcion: OBTIENE EL TIPO DE DATO DE LA COLUMNA
  -- Fecha       Usuario		Comentario
  -- 17.07.2014  KMONCADA   creacion
  FUNCTION GET_TIPO_DATO_COLUMN(cTabla_in IN CHAR,
                                cColumna IN CHAR)
    RETURN VARCHAR2;

/*******************************************************************************************************************************************/
  -- Descripcion: OBTIENE LA FORMA DE PAGO DE UN PEDIDO EN FORMA RESUMIDA
  -- Fecha       Usuario		Comentario
  -- 17.07.2014  KMONCADA   creacion
  FUNCTION REPORTE_FORMAS_PAGO_RESUMEN(cCodGrupoCia_in	IN CHAR,
        		   					               cCodLocal_in	  	IN CHAR,
  		   							                 cNumPedVta IN CHAR)
  RETURN FarmaCursor;
  
  -- Descripcion: Lista reporte de venta convenio
  -- Fecha       Usuario		Comentario
  -- 17.07.2014  KMONCADA   creacion
  FUNCTION REPORTER_REG_VTA_CONVENIO (cCodGrupoCia_in	 IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cFecInicial_in 	 IN CHAR,
                                      cFecFinal_in 	   IN CHAR,
                                      cCodConvenios_in IN varCHAR2) 
  RETURN FarmaCursor;

END;

/
