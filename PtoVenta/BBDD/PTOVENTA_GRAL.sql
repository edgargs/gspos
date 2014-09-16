--------------------------------------------------------
--  DDL for Package PTOVENTA_GRAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_GRAL" AS

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Obtiene el registro de un listado de maestros.
  --Fecha       Usuario	  Comentario
  --15/02/2006  ERIOS     Creacion
  --06/03/2013  ERIOS     Se agrega el parametro cCodCia_in
  FUNCTION BUSCA_REGISTRO_LISTA_MAESTROS(cTipoMaestro_in IN CHAR, cCodBusqueda_in IN CHAR, cCodGrupoCia IN CHAR, cCodCia_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de maestros.
  --Fecha       Usuario	  Comentario
  --15/02/2006  ERIOS     Creacion
  --06/03/2013  ERIOS     Se agrega el parametro cCodCia_in
  FUNCTION LISTA_MAESTROS(cTipoMaestro_in IN CHAR, cCodGrupoCia IN CHAR, cCodCia_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de filtros.
  --Fecha       Usuario		Comentario
  --22/02/2006  LMESIA     Creación
  FUNCTION LISTA_FILTROS(cTipoFiltro_in IN CHAR,
  		   				 cTipoProd_in	IN CHAR)
  		   RETURN FarmaCursor;
  --Descripcion: Valor para el tipo A
  --Fecha       Usuario		Comentario
  --11/10/2006  ERIOS     Creacion
  FUNCTION GET_ABC_VALOR_A RETURN INTEGER;

  --Descripcion: Valor para el tipo B
  --Fecha       Usuario		Comentario
  --11/10/2006  ERIOS     Creacion
  FUNCTION GET_ABC_VALOR_B RETURN INTEGER;

  --Descripcion: Obtiene la direccion Domicilio Fiscal
  --Fecha       Usuario	    Comentario
  --06/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECCION_FISCAL(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene la indicador de direccion local
  --Fecha       Usuario	    Comentario
  --12/09/2013  ERIOS       Creacion
  FUNCTION VTA_F_CHAR_IND_OBT_DIR_LOCAL
  RETURN CHAR;

  --Descripcion: Se obtiene mensaje de ticket delivery
  --Fecha       Usuario	    Comentario
  --12/09/2013  ERIOS       Creacion
  FUNCTION VTA_F_GET_MENS_TICKET(cCodCia_in    IN CHAR,
                                cCod_local_in  IN CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene la indicador de registro de venta restringida
  --Fecha       Usuario	    Comentario
  --12/09/2013  ERIOS       Creacion
  FUNCTION GET_IND_REG_VTA_RESTRIG
  RETURN CHAR;

  --Descripcion: Ruta del icono
  --Fecha       Usuario		         Comentario
  --28/02/2013  Luigy Terrazos     Creacion
  FUNCTION GET_RUTA_IMAGEN_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR) RETURN char;
  --Descripcion: Razon Social del CIA
  --Fecha       Usuario		         Comentario
  --28/02/2013  Luigy Terrazos     Creacion
  FUNCTION GET_RAZ_SOC_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR) RETURN char;
  --Descripcion: Direc Fiscal CIA
  --Fecha       Usuario		         Comentario
  --28/02/2013  Luigy Terrazos     Creacion
  FUNCTION GET_DIR_FIS_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR) RETURN varchar2;

  --Descripcion: Telefono CIA
  --Fecha       Usuario		         Comentario
  --28/02/2013  Luigy Terrazos     Creacion
  FUNCTION GET_TELF_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR) RETURN char;

  --Descripcion: Marca CIA
  --Fecha       Usuario     Comentario
  --06/03/2013  ERIOS       Creacion
  FUNCTION GET_NOMBRE_MARCA_CIA(cCodGrupoCia_in in char, cCodLocal_in in CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene indicador de Nuevo Cobro
  --Fecha       Usuario     Comentario
  --01/04/2013  ERIOS       Creacion
  FUNCTION GET_IND_NUEVO_COBRO RETURN CHAR;

  --Descripcion: Obtener la informacion de la tarjeta ingresada y la forma de pago a la que pertenece
  --Fecha       Usuario   Comentario
  --24/02/2010  ASOSA     Creacion
  --12/Sep/2013 LLEIVA    Modificacion
  --20/12/2013  ERIOS     Se agrega parametros cCodCia_in,cCodLocal_in
  FUNCTION PVTA_F_OBTENER_TARJETA(cCodGrupoCia_in IN CHAR,
								  cCodCia_in IN CHAR,
								  cCodLocal_in IN CHAR,
                                  cCodTarj_in     IN VARCHAR,
                                  cTipOrigen_in   IN VARCHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene Directorio raiz
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECTORIO_RAIZ RETURN CHAR;

  --Descripcion: Obtiene Directorio de imagenes
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECTORIO_IMAGENES RETURN CHAR;

  --Descripcion: Obtiene Imagen cabecera consejos
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_RUTA_IMG_CABECERA_1 RETURN CHAR;

  --Descripcion: Obtiene Imagen cabecera consejos
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_RUTA_IMG_CABECERA_2 RETURN CHAR;

  --Descripcion: Obtiene Imagen Digemid
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_IMG_LIST_DIGEMID RETURN CHAR;

  --Descripcion: Obtiene Directorio de impresion
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECTORIO_IMPRESION RETURN CHAR;

  --Descripcion: Obtiene Directorio de logss
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECTORIO_LOG RETURN CHAR;

  --Descripcion: Obtiene indicador de servicios FarmaSix
  --Fecha       Usuario     Comentario
  --16/07/2013  ERIOS       Creacion
  FUNCTION GET_IND_FARMASIX(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLoca_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene indicador de Pinpad
  --Fecha       Usuario     Comentario
  --16/08/2013  ERIOS       Creacion
  FUNCTION GET_IND_PINPAD(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLoca_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtener los menús que serán bloqueados en la aplicación
  --Fecha       Usuario   Comentario
  --18/10/2013  CVILCA    Creación
  FUNCTION GET_OPCION_BLOQUEADA(cCodGrupoCia_in IN CHAR,
                                                cCodCia_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Logo Marca
  --Fecha       Usuario     Comentario
  --28/10/2013  ERIOS       Creacion
  FUNCTION GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in in char, cCodCia_in in CHAR, cCodLocal_in in CHAR)
  RETURN char;

  --Descripcion: Indicador impresion url web
  --Fecha       Usuario     Comentario
  --28/10/2013  ERIOS       Creacion
  --07.08.2014  ERIOS       Se agrega parametros de local
  FUNCTION GET_IND_IMPR_WEB(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN CHAR;

  --Descripcion: Indicador conciliacion online
  --Fecha       Usuario     Comentario
  --29/11/2013  ERIOS       Creacion
  FUNCTION GET_IND_CONCILIAC_ONLINE RETURN CHAR;

  --Descripcion: Busca Todos los datos del proveedor
  --Fecha       Usuario     Comentario
  --10/12/2013  CHUANES       Creacion
    FUNCTION BUSCA_DATOS_PROVEEDOR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProveedor_in IN CHAR)

  Return FarmaCursor ;

  --Descripcion: Indicador recaudacion centralizada
  --Fecha       Usuario     Comentario
  --28/05/2014  ERIOS       Creacion  
  FUNCTION GET_IND_RECAUDAC_CENTRA RETURN NUMBER;

  --Descripcion: Margen impresion comprobantes
  --Fecha       Usuario     Comentario
  --17/06/2014  ERIOS       Creacion    
  FUNCTION GET_MARGEN_IMP_COMP RETURN NUMBER;
  
 END;

/
