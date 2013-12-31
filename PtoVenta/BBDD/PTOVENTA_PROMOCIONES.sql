--------------------------------------------------------
--  DDL for Package PTOVENTA_PROMOCIONES
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_PROMOCIONES" AS

	TYPE FarmaCursor IS REF CURSOR;
  C_INDICADOR_NO CHAR(1) := 'N';
  C_INDICADOR_SI CHAR(1) := 'S';
  C_ESTADO_ACTIVO CHAR(1) := 'A';



  --PROCEDURE PROMO_LISTA_PROMOCIONES;

  --Descripcion: Obtiene el listado de las promociones
  --Fecha       Usuario		       Comentario
  --13/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADO(cCodGrupoCia_in IN CHAR,
  		                         cCodLocal_in	   IN CHAR)
  RETURN FARMACURSOR;

 PROCEDURE PROMO_LISTA_VENDEDOR;
  --PROCEDURE PROMO_LISTA_PROMOCIONES;

  --Descripcion: Obtiene el listado de las promociones por producto
  --Fecha       Usuario		       Comentario
  --22/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADOPORPRODUCTO(cCodGrupoCia_in IN CHAR,
              		                        cCodLocal_in	  IN CHAR,
                                          vCodProd        IN CHAR)
   RETURN FARMACURSOR;

  --Descripcion: Obtiene el listado de las promociones
  --Fecha       Usuario		       Comentario
  --13/06/2007  Jorge Cortez     Creacion
  FUNCTION PROMOCIONES_DETALLE
  RETURN FARMACURSOR;

  --Descripcion: Obtiene el listado de las promociones del paquete 1
  --Fecha       Usuario		       Comentario
  --13/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADO_PAQUETE1(cCodGrupoCia_in IN CHAR,
  		   				                             cCodLocal_in	 IN CHAR,
                                             vCodProm IN CHAR)
  RETURN FARMACURSOR;

  --Descripcion: Obtiene el listado de las promociones del paquete 2
  --Fecha       Usuario		       Comentario
  --13/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADO_PAQUETE2(cCodGrupoCia_in IN CHAR,
  		   				                             cCodLocal_in	 IN CHAR,
                                             vCodProm IN CHAR)
  RETURN FARMACURSOR;

  --Descripcion: Obtiene el listado de ambos paquetes
  --Fecha       Usuario		       Comentario
  --18/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADO_PAQUETES(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProm IN CHAR)
  RETURN FARMACURSOR;

  --Descripcion: Verfica si se permite la venta de promocion en el local
  --Fecha       Usuario		  Comentario
  --27/02/2008  dubilluz     Creacion
  FUNCTION PROMOCIONES_PERMITE_EN_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				                        cCodLocal_in	  IN CHAR,
                                        vCodProm        IN CHAR)
  RETURN  CHAR;

  --Descripcion: Procesa los pack que dan productos regalo
  -- para añadirlo en el pedido
  --Fecha       Usuario		  Comentario
  --11/06/2008  dubilluz     Creacion
  PROCEDURE PROCESO_PROM_REGALO(cCodGrupoCia_in IN CHAR,
  		   				                cCodLocal_in	  IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                cSecUsu_in      IN CHAR,
                                cLogin_in       IN CHAR,
                                cIp_in          IN CHAR);



   PROCEDURE VTA_P_GRABA_PROM_NO_AUTOMAT(cCodGrupoCia_in 	 	  IN CHAR,
                                      cCodLocal_in    	 	  IN CHAR,
                            				  cNumPedVta_in   	 	  IN CHAR,
                                       cCodProm                	  IN CHAR,
                            				  nCantAtendida_in	 	  IN NUMBER,
                            				  cUsuCreaPedVtaDet_in	IN CHAR
                                      );
/*------------------------------------------------------------------------------------------------------------------
GOAL : Actualizar el Porcentaje de Descuento para Aquellos Regalos que aplica PRECIO FIJO x Producto
Ammedments:
When          Who      What
22-AGO-13     TCT      Create
--------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_UPD_DSCTO_REGA_PROD(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProd IN CHAR);                                      
/*------------------------------------------------------------------------------------------------------------------
GOAL : Actualizar el Porcentaje de Descuento para Aquellos Regalos que aplica PRECIO FIJO
Ammedments:
--------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_UPDATE_DSCTO_REGA_PROM(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProm IN CHAR);
/****************************************************************************************************************/                                                                               

END;

/
