--------------------------------------------------------
--  DDL for Package PTOVENTA_VTA_LISTA_AS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_VTA_LISTA_AS" is

  TYPE FarmaCursor IS REF CURSOR;

  IND_ORIGEN_PROD CONSTANT CHAR(1) := '1';
  IND_ORIGEN_SUST CONSTANT CHAR(1) := '2';
  IND_ORIGEN_ALTE CONSTANT CHAR(1) := '3';
  IND_ORIGEN_COMP CONSTANT CHAR(1) := '4';
  IND_ORIGEN_OFER CONSTANT CHAR(1) := '5';
  IND_ORIGEN_REGA CONSTANT CHAR(1) := '6';


  C_INICIO_MSG  VARCHAR2(2000) := '<html>'||
                                  '<head>'||
                                  '</head>'||
                                  '<body>'||
                                  '<table width="100%" border="0">';

  C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                  ' </tr> ';

  C_FIN_MSG     VARCHAR2(2000) := '</table>'||
                                    '</body>'||
                                    '</html> ';

  COD_CIA_PERU CHAR(3) := '001';
  COD_CIA_BOL CHAR(3) := '002';


  --Descripcion: Listado de productos en venta para cuando el cliente solicite verificar el precio que se le esta cobrando
  --Fecha       Usuario		    Comentario
  --02/02/2010  ASOSA         Creacion
  FUNCTION VTA_LISTA_PROD_02_NEW(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             vCadena_in IN VARCHAR2,
                             cTipo_in IN VARCHAR2)
  RETURN FarmaCursor;

  --Descripcion: DEVUELVE EL PRECIO DESCONTADO SEGUN EL PORCENTAJE DE LA CAMPAÑA Y EL MAXIMO DESCUENTO PERMITIDO
  --Fecha       Usuario		    Comentario
  --02/02/2010  ASOSA         Creacion
  FUNCTION VTA_CALCULAR_DSCTO(nPrecio_in IN NUMBER,
                              nPorc_in IN NUMBER,
                              nMax_in IN NUMBER,
                              nCodProd_in IN varchar2,
                              nCodCamp_in IN varchar2,
                              nValCostProm in char,
                              nValPrecProm in number,
                              nIgv number)
  RETURN NUMBER;

  --Descripcion: DEVUELVE MENSAJE PARA LISTA DE PRECIOS PARA EL CLIENTE
  --Fecha       Usuario		    Comentario
  --03/02/2010  ASOSA         Creacion
  --Se agrega dos parametros para retornar la ruta de la imagen    Autor Luigy Terrazos   Fecha 04/03/2013
  FUNCTION VTA_RETORNAR_MENSAJE(vIP_LOCAL_in IN VARCHAR,cCodGrupoCia_in in char, cCodCia_in in char, cCodLocal_in IN CHAR)
  RETURN VARCHAR2;


    FUNCTION IMP_GET_NUM_CARACTERES
  RETURN VARCHAR2;

    FUNCTION VTA_REDONDEO_MAS(nPrecio_in IN NUMBER)
  RETURN NUMBER;

   --Descripcion: Devuelve la lista de productos con precio de campaña por filtro
  --Fecha       Usuario		    Comentario
  --10/05/2010  ASOSA         Creacion
  FUNCTION VTA_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
  		   				  		           cCodLocal_in	   IN CHAR,
								                 cTipoFiltro_in  IN CHAR,
  		   						             cCodFiltro_in 	 IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista los productos con precio de campaña
  --Fecha       Usuario		    Comentario
  --10/05/2010  ASOSA         Creacion
    FUNCTION VTA_LISTA_PROD(cCodGrupoCia_in IN CHAR,
  		   				          cCodLocal_in	  IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Devuelve precio de
  --Fecha       Usuario		    Comentario
  --10/05/2010  ASOSA         Creacion
    FUNCTION VTA_F_GET_PREC_CAMP(CCODCIA_IN   IN CHAR,
                             CCODLOCAL_IN IN CHAR,
                             CCODPROD_IN  IN CHAR)
    RETURN NUMBER;

  --Descripcion: Devuelve el indicador de la lista de productos con precio de campaña para saber si la muestro o no
  --Fecha       Usuario		    Comentario
  --10/05/2010  ASOSA         Creacion
    FUNCTION VTA_F_IND_LIST_PREC_CAMP(cCodCia_in IN CHAR DEFAULT '001')
    RETURN CHAR;


  --Descripcion: Lista los productos sustitutos con precio de campaña
  --Fecha       Usuario		    Comentario
  --10/05/2010  ASOSA         Creacion
    FUNCTION VTA_LISTA_PROD_SUSTITUTOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)

    RETURN FarmaCursor;

  --Descripcion: Devolver el codigo de la campaña a utilizar y su nombre a mostrar en la columna
  --Fecha       Usuario		    Comentario
  --11/05/2010  ASOSA         Creacion
    FUNCTION VTA_F_GET_COD_DESC_CAMP(cCodCia_in IN CHAR DEFAULT '001')
    RETURN VARCHAR2;

  --Descripcion: DEVUELVE EL PRINCIPIO ACTIVO DEL PRODUCTO
  --Fecha       Usuario		    Comentario
  --28/09/2010  ASOSA         Creacion
  FUNCTION VTA_F_GET_PRINC_ACT(cCodGrupoCia_in in char default '001',
                               cCodProd_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Devuelve Listado con Productos Sustitutos
  --Fecha       Usuario		    Comentario
  --02/10/2010  JMIRANDA         Creacion
  FUNCTION VTA_LISTA_PROD_DCI(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)

   RETURN FarmaCursor;

  --Descripcion: Listado de productos en venta para cuando el cliente solicite verificar el precio que se le esta cobrando
  --Fecha       Usuario		    Comentario
  --02/02/2010  ASOSA         Creacion
  FUNCTION VTA_LISTA_PROD_02(cCodCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             vCadena_in IN VARCHAR2)
  RETURN FarmaCursor;

  --Descripcion: Devuelve el indicador si el producto es Farma
  --Fecha       Usuario		    Comentario
  --05/10/2010  JMIRANDA         Creacion
  FUNCTION VTA_F_IND_PROD_FARMA(cCodGrupoCia_in IN CHAR,
                              cCodProd_in IN CHAR)
                              RETURN CHAR;

  --Descripcion: Devuelve lista de principios activos por comas :)
  --Fecha       Usuario		    Comentario
  --05/10/2010  JMIRANDA         Creacion
  FUNCTION VTA_F_GET_PRINC_ACT_PROD(cCodGrupoCia_in IN CHAR,
                                    cCodProd_in IN CHAR)
                                    RETURN VARCHAR2;
end;

/
