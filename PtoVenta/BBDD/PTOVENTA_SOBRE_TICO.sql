--------------------------------------------------------
--  DDL for Package PTOVENTA_SOBRE_TICO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_SOBRE_TICO" AS

  TYPE FarmaCursor IS REF CURSOR;
  ESTADO_ACTIVO        CHAR := 'A';
  ESTADO_INACTIVO      CHAR := 'I';
  NO_ENVIADO           CHAR := 'N';
  ENVIADO              CHAR := 'S';
  ELIMINADO            CHAR := 'X';
  IND_LOCAL_MARKET     CHAR(3) :='002';
  IND_LOCAL_FARMA      CHAR(3) :='001';
  CodGrupoCia_in      CHAR(3) :='001';

  FUNCTION F_LISTAR_SOBRES(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
    RETURN FarmaCursor;

  PROCEDURE P_INSERT_SOBRE_TICO(cFecVta     CHAR,
                                cCodLocal   CHAR,
                                cCodSobre   VARCHAR2,
                                cMoneda     VARCHAR2,
                                cMontoTotal NUMBER,
                                cFormaPago  VARCHAR2,
                                cUsuCrea    VARCHAR2,
                                cUsuLogin   VARCHAR2,
                                cMonSol     NUMBER,
                                cMonDol     NUMBER,
                                cIndSol     NUMBER,
                                cIndDol     NUMBER);

  FUNCTION P_DELETE_SOBRE(cFecVta   CHAR,
                           cCodLocal CHAR,
                           cCodSobre VARCHAR2,
                           cUsuMod   VARCHAR2)
  RETURN CHAR;

  FUNCTION F_TICO_SOBRES_LOCAL(cCodLocal IN CHAR) RETURN FarmaCursor;

  PROCEDURE P_UPDATE_IND_PROCESO
                            (indProceso IN CHAR
                            );

  PROCEDURE P_UPDATE_REMITO(cCodSobre  IN VARCHAR2,
                            cCodLocal  IN CHAR,
                            cFecVta    IN CHAR,
                            cCodRemito IN VARCHAR2,
                            cPrecinto  IN VARCHAR2,
                            cUsuMod    IN VARCHAR2);

  FUNCTION F_LISTA_SOBRE_TICO(cCodLocal IN CHAR) RETURN FarmaCursor;

  FUNCTION F_OBTENER_FECVTA(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            cCodSobre_in    IN CHAR)
  
  RETURN CHAR;

  FUNCTION GET_IND_TICO (cCodLocal CHAR) RETURN CHAR ;

  FUNCTION GET_IP_PADRE(cCodLocal CHAR) RETURN VARCHAR2;
  
  FUNCTION GET_IND_SIN_MARKET(cCodLocal CHAR) RETURN CHAR;
  
  PROCEDURE  P_INSERT_CIERRE_DIA_MARKET (cFecCierreDia   IN CHAR ,
                                         cCodLocal       IN CHAR ,
                                         cUsuLogin       IN CHAR ,
                                         cUsuCrea        IN CHAR
                                        );
                                        
  FUNCTION P_IND_CIERRE_MARKET (cFecDiaCierre     IN CHAR,
                                cCodMarket   IN CHAR)
    RETURN CHAR;          

      FUNCTION GET_IPS_TICOS(cCodLocal CHAR) RETURN FarmaCursor;                                                                          

END; --FIN DEL PACKAGE

/
