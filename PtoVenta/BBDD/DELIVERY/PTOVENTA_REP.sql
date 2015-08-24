--------------------------------------------------------
--  DDL for Package PTOVENTA_REP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "DELIVERY"."PTOVENTA_REP" AS

  g_cIndLocalProvincia_S  PBL_LOCAL.IND_LOCAL_PROV%TYPE := 'S';
  g_cIndLocalProvincia_N  PBL_LOCAL.IND_LOCAL_PROV%TYPE := 'S';

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: obtiene los indicadores de stock
  --Fecha       Usuario	 Comentario
  --20/08/2007  DUBILLUZ   Creacion

  FUNCTION INV_OBTIENE_IND_STOCK(cCodGrupoCia_in 	  IN CHAR,
                                 cCodProd_in IN CHAR)
  RETURN VARCHAR2;




END;

/
