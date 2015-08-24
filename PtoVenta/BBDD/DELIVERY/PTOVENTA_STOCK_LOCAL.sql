--------------------------------------------------------
--  DDL for Package PTOVENTA_STOCK_LOCAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "DELIVERY"."PTOVENTA_STOCK_LOCAL" AS

  TYPE FarmaCursor IS REF CURSOR;
  -- Author  : DUBILLUZ
  -- Created : 20/08/2007 12:56:14 p.m.
  g_cTipoLocalVenta PBL_LOCAL.TIP_LOCAL%TYPE := 'V';
  g_cIndLocalProvincia_S  PBL_LOCAL.IND_LOCAL_PROV%TYPE := 'S';
  g_cIndLocalProvincia_N  PBL_LOCAL.IND_LOCAL_PROV%TYPE := 'S';

  --Descripcion: OBTIENE EL STOCK DE LOS LOCALES MAS CERCANOS
  --Fecha       Usuario	 Comentario
  --20/08/2006  DUBILLUZ     Creacion
  FUNCTION INV_STOCK_LOCALES_PREFERIDOS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodProd_in     IN CHAR,
                                        cIndVerTodos_in IN CHAR DEFAULT 'N')
  RETURN FarmaCursor;

  --Descripcion: oBTIENE TIEM STAM
  --Fecha       Usuario		    Comentario
  --12/09/2007  DUBILLUZ      Creacion
  FUNCTION CONSULTA_LOCAL(cCodGrupoCia_in  IN CHAR)
                                              RETURN varchar2;

  FUNCTION INV_OBTIENE_IND_STOCK(cCodGrupoCia_in 	  IN CHAR,
                                 cCodProd_in IN CHAR)
  RETURN VARCHAR2;

end Ptoventa_STOCK_LOCAL;

/
