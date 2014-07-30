--------------------------------------------------------
--  DDL for Package Body FARMA_CNX_REMOTO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."FARMA_CNX_REMOTO" IS

 /* *********************************************************************** */
  FUNCTION F_CNX_RECARGAS(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR) RETURN VARCHAR2 IS
    vResultado varchar2(10000);
  begin

    SELECT TRIM(IP_SERVIDOR)||'@'||    TRIM(USU_BD)||'@'||TRIM(CLAVE_BD)||'@'||
           TRIM(SID_BD)||'@'||TRIM(PUERTO_BD)||'@'||TRIM(TIME_OUT)
    into vResultado
      FROM PBL_CNX_REMOTO V
     WHERE V.ID_CONEXION = 1;

    RETURN vResultado;
  END;

 /* *********************************************************************** */
  --Descripcion: Obtiene usuario de conexion al RAC por local
  --Fecha       Usuario		Comentario
  --10/04/2014  ERIOS       Creacion 
  FUNCTION F_CNX_RAC_LOCAL(cCodGrupoCia_in IN CHAR,
							cCodCia_in IN CHAR,
                          cCodLocal_in    IN CHAR) RETURN VARCHAR2 IS
    vResultado varchar2(10000);
  begin

    SELECT TRIM(IP_SERVIDOR)||'@'||    TRIM(USU_BD)||'@'||TRIM(CLAVE_BD)||'@'||
           TRIM(SID_BD)||'@'||TRIM(PUERTO_BD)||'@'||TRIM(TIME_OUT)
    into vResultado
      FROM PBL_CNX_REMOTO V
     WHERE --V.ID_CONEXION = 1
	 COD_GRUPO_CIA = cCodGrupoCia_in
	 AND COD_CIA = cCodCia_in
	 AND COD_LOCAL = cCodLocal_in
	 ;

    RETURN vResultado;
  END;
  
END FARMA_CNX_REMOTO;

/
