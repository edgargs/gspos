--------------------------------------------------------
--  DDL for Package TMP_REP_ERN
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."TMP_REP_ERN" AS

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Obtiene el reporte de ventas por Hora.
  --Fecha       Usuario		Comentario
  --27/03/2006  ERIOS    	Creación
  FUNCTION REP_VENTAS_POR_HORA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR,cDiaSem_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene las unidades vendidas por pedido.
  --Fecha       Usuario		Comentario
  --27/03/2006  ERIOS    	Creación
  FUNCTION REP_GET_UNIDADES_PEDIDO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR) RETURN FLOAT;

  --Descripcion: Obtiene el reporte de productos Falta Cero.
  --Fecha       Usuario		Comentario
  --06/07/2006  ERIOS    	Creación
  --30-03-2007  LREQUE    Modificación
  FUNCTION REP_PRODUCTOS_FALTA_CERO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR,cDiaSem_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de productos Falta Cero.
  --Fecha       Usuario		Comentario
  --06/07/2006  ERIOS    	Creación
  FUNCTION REP_DET_FALTA_CERO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR, vFechaIni_in IN CHAR, vFechaFin_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de productos ABC.
  --Fecha       Usuario		Comentario
  --17/07/2006  ERIOS    	Creación
  FUNCTION REP_PRODUCTO_ABC(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cFiltro_in IN CHAR,
                            cInd_in IN CHAR,
                            cFechaIni_in IN CHAR,
                            cFechaFin_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Determina el tipo de cada productos.
  --Fecha       Usuario		Comentario
  --17/07/2006  ERIOS    	Creacion
  --07/09/2007  ERIOS    	Modificacion
  PROCEDURE REP_DETERMINAR_TIPO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR DEFAULT 'S');

  --Descripcion: Determina el tipo de cada productos en la compañia
  --Fecha       Usuario		Comentario
  --28/07/2006  JOLIVA    	Creación
  /*PROCEDURE REP_DETERMINAR_TIPO(cCodGrupoCia_in IN CHAR, nDiasRot_in IN NUMBER);*/

  --Descripcion: Lista por filtro de tipo de producto
  --Fecha       Usuario		Comentario
  --29/09/2006  paulo    	Creación
  FUNCTION REP_FILTRO_PRODUCTO_ABC(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            cFiltro_in IN CHAR,
                            cInd_in IN CHAR,
                            cTipoProducto_in IN CHAR,
                            cFechaIni_in IN CHAR,
                            cFechaFin_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Determina el tipo de cada productos,
  --             segun la cantidad de unidades vendidas.
  --Fecha       Usuario		Comentario
  --13/03/2007  ERIOS    	Creación
  PROCEDURE REP_DETERMINAR_TIPO_UND(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR);

  --Descripcion: Determina el tipo por monto de cada productos.
  --Fecha       Usuario		Comentario
  --07/09/2007  ERIOS    	Creacion
  PROCEDURE REP_DETERMINAR_TIPO_MONTO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cFechaIni_in IN CHAR,cFechaFin_in IN CHAR,
                            cIndGeneraResumen IN CHAR);
END;

/
