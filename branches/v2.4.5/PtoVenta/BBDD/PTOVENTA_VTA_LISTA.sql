--------------------------------------------------------
--  DDL for Package PTOVENTA_VTA_LISTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_VTA_LISTA" is

  TYPE FarmaCursor IS REF CURSOR;

  IND_ORIGEN_PROD CONSTANT CHAR(1) := '1';
  IND_ORIGEN_SUST CONSTANT CHAR(1) := '2';
  IND_ORIGEN_ALTE CONSTANT CHAR(1) := '3';
  IND_ORIGEN_COMP CONSTANT CHAR(1) := '4';
  IND_ORIGEN_OFER CONSTANT CHAR(1) := '5';
  IND_ORIGEN_REGA CONSTANT CHAR(1) := '6';

  --Descripcion: Obtiene el listado de los productos
  --Fecha       Usuario		Comentario
  --14/02/2006  LMESIA     Creación
  --21/11/2007 dubilluz modificacion
  FUNCTION VTA_LISTA_PROD(cCodGrupoCia_in IN CHAR,
  		   				  cCodLocal_in	  IN CHAR)
	RETURN FarmaCursor;
  --Descripcion: Obtiene el listado de los productos filtrado
  --Fecha       Usuario		Comentario
  --22/02/2006  LMESIA     Creación
  --21/11/2007 dubilluz modificacion
  FUNCTION VTA_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
  		   				  		 cCodLocal_in	 IN CHAR,
								 cTipoFiltro_in  IN CHAR,
  		   						 cCodFiltro_in 	 IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de los productos alternativos
  --Fecha       Usuario		Comentario
  --24/02/2006  LMESIA     Creación
  --04/12/2007  dubilluz   Modificacion
  FUNCTION VTA_LISTA_PROD_ALTERNATIVOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)
  	RETURN FarmaCursor;

  --Descripcion: Listado de productos sustitutos
  --Fecha       Usuario		Comentario
  --08/04/2008  ERIOS     Creacion
  FUNCTION VTA_LISTA_PROD_SUSTITUTOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se obtiene los productos complementarios de los que estan en el pedido
  --Fecha       Usuario		    Comentario
  --09/04/2008  JCORTEZ     Creación
  FUNCTION VTA_LISTA_PROD_COMP1(cCodGrupoCia_in IN CHAR,
								 	             cCodLocal_in	   IN CHAR,
                               cIP_in      IN CHAR,
                               cProdVentaPedido_in in varchar2
                               )
  RETURN FarmaCursor;

  --Descripcion: Se obtiene los productos en oferta
  --Fecha       Usuario		    Comentario
  --09/04/2008  JCORTEZ     Creación
  FUNCTION VTA_LISTA_PROD_OFERTA(cCodGrupoCia_in IN CHAR,
								 	             cCodLocal_in	   IN CHAR,
                               cIP_in      IN CHAR,
							   cProdVentaPedido_in in varchar2)
  RETURN FarmaCursor;

  --Descripcion: Se obtiene informacion de producto oferta
  --Fecha       Usuario		    Comentario
  --09/04/2008  JCORTEZ     Creación
  FUNCTION VTA_OBTIENE_INFO_PROD_OFER(cCodGrupoCia_in IN CHAR,
        		   				  		           cCodLocal_in	   IN CHAR,
      								                 cCodProd_in	   IN CHAR)
  RETURN FarmaCursor;

   --Descripcion: Se la descripcion del producto padre del complementario
  --Fecha       Usuario		    Comentario
  --15/04/2008  JCORTEZ     Creación
  FUNCTION VTA_OBTIENE_INFO_PROD_COMP(cCodGrupoCia_in IN CHAR,
                                       cCodProdComp_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se lista los tipo de filtro desde resumen de pedido (Encarte, Cupon)
  --Fecha       Usuario		    Comentario
  --18/04/2008  JCORTEZ     Creación
  FUNCTION VTA_LISTA_FILTRO_PROD(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Se realiza un filtro especializado
  --Fecha       Usuario		    Comentario
  --08/05/2008  DUBILLUZ       Creación
  FUNCTION VTA_LISTA_FILTRO_ESPECIALIZADO(cCodGrupoCia_in IN CHAR,
                  		   				          cCodLocal_in	  IN CHAR,
                                          cDescProd_in   IN VARCHAR2)
  RETURN FarmaCursor;

  --Descripcion: Listado de productos sustitos por CATEGORIA:IMS
  --Fecha       Usuario		    Comentario
  --11/06/2008  ERIOS         Creacion
  FUNCTION VTA_LISTA_PROD_SUST_IMS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Listado de productos sustitos por CATEGORIA:AGRUPACION
  --Fecha       Usuario		    Comentario
  --11/06/2008  ERIOS         Creacion
  FUNCTION VTA_LISTA_PROD_SUST_AGR(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Listado de productos sustitos por CATEGORIA:PRODUCTOS
  --Fecha       Usuario		    Comentario
  --11/06/2008  ERIOS         Creacion
  FUNCTION VTA_LISTA_PROD_SUST_PROD(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR,
                     cCodCatSust_in IN CHAR)
  RETURN FarmaCursor;



end;

/
