CREATE OR REPLACE PACKAGE "PTOVENTA_ENCUESTAS" IS

	TYPE FarmaCursor IS REF CURSOR;
	
	FUNCTION VERIFICA_ENCUESTA(cCodGrupoCia_in IN CHAR,
								cCodCia_in IN CHAR,
								cCodLocal_in IN CHAR,
								cCodProd_in IN CHAR) RETURN FarmaCursor;
								
	FUNCTION OBTENER_PREGUNTAS(nCodEncuesta_in IN NUMBER) RETURN FarmaCursor;

  PROCEDURE GRABA_DETALLE_ENCUESTA(  cCodGrupoCia_in  IN CHAR,
                                     cCodCia_in       IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cSecuencial      IN NUMBER,
                                     cCodProd_in      IN CHAR,
                                     cCodEncuesta_in  IN NUMBER,
                                     cSecPregunta_in  IN NUMBER,
                                     cRespuesta       IN VARCHAR2
                                     );             
                              
 FUNCTION GRABA_CABECERA_ENCUESTA( cCodGrupoCia_in  IN CHAR,
                                   cCodCia_in       IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cUsuId_in        IN CHAR,
                                   cCodProd_in      IN CHAR,
                                   cCodEncuesta_in  IN NUMBER) 
   RETURN NUMBER;
   
                                 
END PTOVENTA_ENCUESTAS;
/
CREATE OR REPLACE PACKAGE BODY "PTOVENTA_ENCUESTAS" IS

	FUNCTION VERIFICA_ENCUESTA(cCodGrupoCia_in IN CHAR,
								cCodCia_in IN CHAR,
								cCodLocal_in IN CHAR,
								cCodProd_in IN CHAR) RETURN FarmaCursor
	AS
		vReturn FarmaCursor;
	BEGIN
		OPEN vReturn FOR
		SELECT COD_ENCUESTA
		FROM PBL_ENCUESTA ENC JOIN PBL_PRODUCTO_ENCUESTA PEC USING (COD_ENCUESTA)
		WHERE TRUNC(SYSDATE) BETWEEN FEC_INI AND FEC_FIN+1
    AND ESTADO = 'A'
    AND COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_PROD = cCodProd_in;
    
    RETURN vReturn;
  END;
  
  FUNCTION OBTENER_PREGUNTAS(nCodEncuesta_in IN NUMBER) RETURN FarmaCursor
  AS
    vReturn FarmaCursor;
  BEGIN
    OPEN vReturn FOR
    SELECT COD_ENCUESTA,SEC_PREGUNTA,DESC_PREGUNTA,TIPO_PREGUNTA, TIPO_VALIDACION
    FROM PBL_PREGUNTA_ENCUESTA
    WHERE COD_ENCUESTA = nCodEncuesta_in
    ORDER BY SEC_PREGUNTA;
    
    RETURN vReturn;
  END;  
  
  PROCEDURE GRABA_DETALLE_ENCUESTA(  cCodGrupoCia_in  IN CHAR,
                                     cCodCia_in       IN CHAR,
                                     cCodLocal_in     IN CHAR,
                                     cSecuencial      IN NUMBER,
                                     cCodProd_in      IN CHAR,
                                     cCodEncuesta_in  IN NUMBER,
                                     cSecPregunta_in  IN NUMBER,
                                     cRespuesta       IN VARCHAR2
                                     ) IS
   
  
  
   BEGIN
     
     INSERT INTO PBL_REGISTRO_ENCUESTA_DET
                 (
                 COD_GRUPO_CIA      ,        
                 COD_CIA            ,
                 COD_LOCAL          ,
                 SEC_REG_ENCUESTA    ,  
                 COD_ENCUESTA        ,
                 SEC_PREGUNTA        ,
                 DESC_RESPUESTA      
                 )
     VALUES(
                 cCodGrupoCia_in       ,
                 cCodCia_in            ,
                 cCodLocal_in          ,
                 cSecuencial                   ,
                 cCodEncuesta_in       ,
                 cSecPregunta_in       ,
                 cRespuesta            
                 );
   
   END;                                             


 FUNCTION GRABA_CABECERA_ENCUESTA( cCodGrupoCia_in  IN CHAR,
                                   cCodCia_in       IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cUsuId_in        IN CHAR,
                                   cCodProd_in      IN CHAR,
                                   cCodEncuesta_in  IN NUMBER) 
   RETURN NUMBER
  AS
    vSecuencial NUMBER;
  BEGIN
    
    SELECT SEQ_REG_ENCUESTA.nextval 
           INTO vSecuencial
    FROM dual;
  
    INSERT INTO PBL_REGISTRO_ENCUESTA X
      (COD_GRUPO_CIA, --1
       COD_CIA, --2
       COD_LOCAL, --3
       SEC_REG_ENCUESTA, --4
       COD_ENCUESTA, --5  
       COD_PROD, --6
       ESTADO, --7
       USU_CREA, --8
       FEC_REGISTRO --9
       
       )
    VALUES
      (cCodGrupoCia_in,
       cCodCia_in,
       cCodLocal_in,
       vSecuencial,
       cCodEncuesta_in,
       cCodProd_in,
       'A',
       cUsuId_in,
       sysdate
       );
  
    RETURN vSecuencial;
  END;                                               
  
END PTOVENTA_ENCUESTAS;
/
