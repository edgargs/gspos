--------------------------------------------------------
--  DDL for Package PTOVENTA_REPORTE_PACK_DU
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_REPORTE_PACK_DU" AS

  -- Author  : JCALLO
  -- Created : 28/10/2008
  -- Purpose :

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion:
  --Fecha       Usuario		Comentario
  --28/10/2008  JCALLO    CREACION
  PROCEDURE CARGA_DETALLE_CABECERA( fecInicio in char, fecFin    in char);

  PROCEDURE CARGA_PEDIDO_X_PACK ( fecInicio in char, fecFin    in char);

 PROCEDURE CARGA_REPORTE_PACK ( fecInicio in char, fecFin    in char);


  PROCEDURE DROP_TABLAS;

  PROCEDURE DELETE_TABLAS (fecInicio in char, fecFin    in char);

  PROCEDURE CREA_RESUMEN(
                          fecInicio in char,
                          fecFin    in char
                         );


END PTOVENTA_REPORTE_PACK_DU;

/
