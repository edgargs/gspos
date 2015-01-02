CREATE OR REPLACE PACKAGE PTOVENTA."FARMA_CNX_REMOTO" AS


      ID_CONEXION_MARKET INTEGER := 651 ;

  FUNCTION F_CNX_RECARGAS(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in    IN CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene usuario de conexion al RAC por local
  --Fecha       Usuario		Comentario
  --10/04/2014  ERIOS       Creacion
  FUNCTION F_CNX_RAC_LOCAL(cCodGrupoCia_in IN CHAR,
							cCodCia_in IN CHAR,
                          cCodLocal_in    IN CHAR
                           ) RETURN VARCHAR2;

 --Descripcion: Obtiene usuario de conexion al RAC por local
  --Fecha       Usuario		Comentario
  --16/09/2014  RHERRERA       Creacion
  FUNCTION F_CNX_LOCAL_MARKET RETURN VARCHAR2;

  --Descripcion: Obtiene datos para conexion con EPOS
  --Fecha       Usuario		Comentario
  --26/09/2014  LTAVARA       Creacion

  FUNCTION F_CNX_LOCAL_EPOS(cCodGrupoCia_in IN CHAR,
							            cCodCia_in IN CHAR,
                          cCodLocal_in    IN CHAR
                                                    ) RETURN VARCHAR2;
END FARMA_CNX_REMOTO;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."FARMA_CNX_REMOTO" IS

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
                          cCodLocal_in    IN CHAR
                          ) RETURN VARCHAR2 IS
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
   AND SERVIDOR='RAC';

    RETURN vResultado;
  END;

  --Descripcion: Obtiene usuario de conexion al RAC por local
  --Fecha       Usuario		Comentario
  --16/09/2014  RHERRERA       Creacion
  FUNCTION F_CNX_LOCAL_MARKET RETURN VARCHAR2 IS
    vResultado varchar2(10000);
  begin

    SELECT TRIM(IP_SERVIDOR)||'@'||    TRIM(USU_BD)||'@'||TRIM(CLAVE_BD)||'@'||
           TRIM(SID_BD)||'@'||TRIM(PUERTO_BD)||'@'||TRIM(TIME_OUT)
    into vResultado
      FROM PBL_CNX_REMOTO V
     WHERE V.ID_CONEXION= ID_CONEXION_MARKET
	 ;

    RETURN vResultado;
  END;

    --Descripcion: Obtiene datos para conexion con EPOS
  --Fecha       Usuario		Comentario
  --26/09/2014  LTAVARA       Creacion
  FUNCTION F_CNX_LOCAL_EPOS(cCodGrupoCia_in IN CHAR,
							            cCodCia_in IN CHAR,
                          cCodLocal_in    IN CHAR
                                                    ) RETURN VARCHAR2 IS
    vResultado varchar2(500);
  begin
    BEGIN 
      -- IP@PUERTO@MODO
      SELECT TRIM(IP_SERVIDOR)||'@'||TRIM(NVL(PUERTO_BD,0))||'@'||TRIM(TIME_OUT)
      into vResultado
      FROM PBL_CNX_REMOTO V
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
	    AND COD_CIA = cCodCia_in
	    AND COD_LOCAL = cCodLocal_in
      AND SERVIDOR='EPOS';
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vResultado := '@0@';
    END;
    RETURN vResultado;
  END;


END FARMA_CNX_REMOTO;
/
