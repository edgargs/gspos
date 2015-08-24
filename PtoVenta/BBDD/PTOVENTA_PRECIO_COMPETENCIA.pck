create or replace package PTOVENTA_PRECIO_COMPETENCIA AS

  -- Author  : CLARICO
  -- Created : 05/01/2015 09:15:23 a.m.
  -- Purpose : manejo de informacion para modulo gestion de precios como contingencia

  TYPE FarmaCursor IS REF CURSOR;
   
PROCEDURE PREC_REGISTRA_COTIZACION(cCodProd IN VARCHAR2, 
                                  cCodLocal IN VARCHAR2, 
                                  cCodUsuario IN VARCHAR2, 
                                  cCodTipoCotizacion IN NUMBER,
                                  cValFrac IN NUMBER,
                                  cCantidad IN NUMBER,
                                  cPrecioUnitario IN NUMBER,
                                  cCompetidor IN VARCHAR2,
                                  cSustento IN NUMBER,
                                  cNumDoc IN VARCHAR2,
                                  cFechaDocumento IN VARCHAR2,
                                  cArchivoSustento IN VARCHAR2,
                                  cCondicion IN VARCHAR2,
                                  cNumNota IN VARCHAR2,
                                  cMotivoNoImagen IN VARCHAR2); 

FUNCTION PREC_LISTA_COT_PENDIENTES RETURN FarmaCursor;

PROCEDURE PREC_BORRA_COT_PENDIENTES(cSecuencial IN NUMBER);

end ;
/
create or replace package body PTOVENTA_PRECIO_COMPETENCIA as

     
                                  
                                  
PROCEDURE PREC_REGISTRA_COTIZACION(cCodProd IN VARCHAR2, 
                                  cCodLocal IN VARCHAR2, 
                                  cCodUsuario IN VARCHAR2, 
                                  cCodTipoCotizacion IN NUMBER,
                                  cValFrac IN NUMBER,
                                  cCantidad IN NUMBER,
                                  cPrecioUnitario IN NUMBER,
                                  cCompetidor IN VARCHAR2,
                                  cSustento IN NUMBER,
                                  cNumDoc IN VARCHAR2,
                                  cFechaDocumento IN VARCHAR2,
                                  cArchivoSustento IN VARCHAR2,
                                  cCondicion IN VARCHAR2,
                                  cNumNota IN VARCHAR2,
                                  cMotivoNoImagen IN VARCHAR2) AS
v_vsecuencial NUMBER;                                  
BEGIN
     
     BEGIN 
       SELECT NVL(MAX(SECUENCIAL),0)+1 INTO v_vsecuencial
       FROM AUX_SET_COTIZACION;
     END;


     INSERT INTO AUX_SET_COTIZACION(
                       SECUENCIAL     ,
                       COD_PROD       ,
                       COD_LOCAL      ,
                       USUARIO        ,
                       TIPO_COTIZACION,
                       VAL_FRAC       ,
                       CANTIDAD       ,
                       PREC_UNIT      ,
                       COMPETIDOR     ,
                       SUSTENTO       ,
                       NUM_DOC        ,
                       FEC_DOC        ,
                       ARCHIVO_SUSTENTO,
                       CONDICION      ,
                       NUM_NOTA_ES    ,
                       MOT_NO_IMAGEN
                       )
                       VALUES(
                       v_vsecuencial  ,
                       cCodProd       , 
                       cCodLocal      , 
                       cCodUsuario    , 
                       cCodTipoCotizacion ,
                       cValFrac       ,
                       cCantidad      ,
                       cPrecioUnitario,
                       cCompetidor    ,
                       cSustento      ,
                       cNumDoc        ,
                       cFechaDocumento,
                       cArchivoSustento,
                       cCondicion     ,
                       cNumNota       ,
                       cMotivoNoImagen 
                       );
END;   


FUNCTION PREC_LISTA_COT_PENDIENTES
  RETURN FarmaCursor
  IS
  	curOrigen FarmaCursor;
  BEGIN
  	OPEN curOrigen FOR
  	SELECT S.SECUENCIAL || 'Ã' ||
           S.COD_PROD   || 'Ã' ||
           S.COD_LOCAL  || 'Ã' ||
           S.USUARIO    || 'Ã' ||
           S.TIPO_COTIZACION || 'Ã' ||
           S.VAL_FRAC   || 'Ã' ||
           S.CANTIDAD   || 'Ã' ||
           S.PREC_UNIT  || 'Ã' ||
           S.COMPETIDOR || 'Ã' ||
           S.SUSTENTO   || 'Ã' ||
           NVL(S.NUM_DOC,' ')    || 'Ã' ||
           NVL(TO_CHAR(S.FEC_DOC,'DD/MM/YYYY'),' ')  || 'Ã' ||
           NVL(S.ARCHIVO_SUSTENTO,' ') || 'Ã' ||
           S.CONDICION        || 'Ã' ||
           NVL(S.NUM_NOTA_ES,' ')      || 'Ã' ||
           NVL(S.MOT_NO_IMAGEN,' ')
  	FROM AUX_SET_COTIZACION S;
  	RETURN curOrigen;
  END;


PROCEDURE PREC_BORRA_COT_PENDIENTES(cSecuencial IN NUMBER) AS
BEGIN
     DELETE FROM AUX_SET_COTIZACION WHERE SECUENCIAL = cSecuencial;
     COMMIT;
END;                               
       
end ;
/
