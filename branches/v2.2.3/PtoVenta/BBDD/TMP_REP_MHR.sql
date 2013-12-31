--------------------------------------------------------
--  DDL for Package TMP_REP_MHR
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."TMP_REP_MHR" AS
 TYPE FarmaCursor IS REF CURSOR;

 --Descripcion: Obtiene el reporte de dias-mes
  --Fecha       Usuario		Comentario
  --29/03/2006  MHUAYTA     Creación
 FUNCTION REPORTE_VENTAS_DIA_MES(cCodGrupoCia_in IN CHAR,
        		   			     cCodLocal_in    IN CHAR,
 			   					 cFechaInicio 	 IN CHAR,
 		  						 cFechaFin 		 IN CHAR,
								 cCodFiltro		 IN CHAR
										   )
  RETURN FarmaCursor;
END;

/
