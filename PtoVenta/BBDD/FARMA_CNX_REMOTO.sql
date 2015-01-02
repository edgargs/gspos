--------------------------------------------------------
--  DDL for Package FARMA_CNX_REMOTO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."FARMA_CNX_REMOTO" AS


      ID_CONEXION_MARKET INTEGER := 651 ;

  FUNCTION F_CNX_RECARGAS(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene usuario de conexion al RAC por local
  --Fecha       Usuario		Comentario
  --10/04/2014  ERIOS       Creacion						  
  FUNCTION F_CNX_RAC_LOCAL(cCodGrupoCia_in IN CHAR,
							cCodCia_in IN CHAR,
                          cCodLocal_in    IN CHAR) RETURN VARCHAR2;

 --Descripcion: Obtiene usuario de conexion al RAC por local
  --Fecha       Usuario		Comentario
  --16/09/2014  RHERRERA       Creacion		
  FUNCTION F_CNX_LOCAL_MARKET RETURN VARCHAR2;					  


END FARMA_CNX_REMOTO;

/
