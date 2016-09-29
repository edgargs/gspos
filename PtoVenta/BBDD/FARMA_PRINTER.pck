CREATE OR REPLACE PACKAGE FARMA_PRINTER is
  
  TYPE FarmaCursor IS REF CURSOR;
  TYPE ArrayList IS TABLE OF VARCHAR2(1000 CHAR) INDEX BY BINARY_INTEGER ;
  
  TD_TEXTO           INTEGER := 1; 
  TD_PDF417          INTEGER := 2; 
  TD_IMAGEN          INTEGER := 3;
  TD_BARCODE_EAN13   INTEGER := 4;
  TD_BARCODE_CODE39  INTEGER := 5;
  TD_QR              INTEGER := 6;
  TD_BARCODE_CODE128 INTEGER := 7;
   
  TAMANIO_0          INTEGER := 0;
  TAMANIO_1          INTEGER := 1;
  TAMANIO_2          INTEGER := 2;
  TAMANIO_3          INTEGER := 3;
  TAMANIO_4          INTEGER := 4;
  TAMANIO_5          INTEGER := 5;
  TAMANIO_6          INTEGER := 6;

  ALING_IZQ          CHAR(1) := 'I';
  ALING_CEN          CHAR(1) := 'C';
  ALING_DER          CHAR(1) := 'D';
  ALING_JUSTI        CHAR(1) := 'J';
  
  BOLD_ACT            CHAR(1) := 'S';
  BOLD_DESACT         CHAR(1) := 'N';
  
  SUBRAY_ACT          CHAR(1) := 'S';
  SUBRAY_DESACT       CHAR(1) := 'N';
  
  LINEADO_SIMPLE      CHAR(1) := '1';
  LINEADO_DOBLE       CHAR(1) := '2';
  
  INVERSO_ACT         CHAR(1) := 'S';
  INVERSO_DESACT      CHAR(1) := 'N';
  
  SALTO_LINEA_ACT     CHAR(1) := 'S';
  SALTO_LINEA_DESACT  CHAR(1) := 'N';
  
  JUSTIFICA_NO        CHAR(1) := 'N';

  /*** CCASTILLO 27/05/2016 ***/
  G_FLAGIMPRESORA        BOOLEAN:=FALSE;
  G_TIPOIMPRESORA        VTA_IMPR_LOCAL_TERMICA.TIPO_IMPR_TERMICA%TYPE:='01';
  /*** CCASTILLO 27/05/2016 ***/

/* *************************************************************** */

  FUNCTION F_GENERA_ID_DOC  
    RETURN VARCHAR2;

/* *************************************************************** */  

  FUNCTION F_GET_IP_SESS  
    RETURN VARCHAR2;
  
/* *************************************************************** */

  FUNCTION F_INT_LINEA_DOC (vIdDoc_in IN VARCHAR2,
                            vIpPc_in  IN VARCHAR2)
    RETURN INTEGER;
 
/* *************************************************************** */

  PROCEDURE P_GRABA_LINEA_DOC( vIdDoc_in        IN VARCHAR2,
                               vIpPc_in         IN VARCHAR2,
                               vValor_in        IN VARCHAR2,
                               vTipoDato_in     IN INTEGER,
                               vTamanio_in      IN INTEGER,
                               vAlineado_in     IN CHAR,
                               vNegrita_in      IN CHAR,
                               vSubrayado_in    IN CHAR,
                               vInterlineado_in IN CHAR,
                               vColorInv_in     IN CHAR,
                               vSaltoLinea_in   IN CHAR);

/* *************************************************************** */  

  FUNCTION CANTIDAD_CARACTERES_TERMICO(cTamanioTexto_in IN INTEGER)
    RETURN INTEGER;

/* *************************************************************** */
    
  FUNCTION APLICA_JUSTIFICA(vLinea_in              IN VARCHAR2,
                            vCantidadCaracteres_in IN INTEGER)
    RETURN VARCHAR2;

/* *************************************************************** */
    
  FUNCTION JUSTIFICA_TEXTO(vValor_in        IN VARCHAR2,
                           cTamanioTexto_in IN INTEGER)
    RETURN ArrayList;

/* *************************************************************** */  

  PROCEDURE P_AGREGA_TEXTO(vIdDoc_in        IN VARCHAR2,
                           vIpPc_in         IN VARCHAR2,
                           vValor_in        IN VARCHAR2,
                           vTamanio_in      IN INTEGER,
                           vAlineado_in     IN CHAR,
                           vNegrita_in      IN CHAR DEFAULT BOLD_DESACT,
                           vSubrayado_in    IN CHAR DEFAULT SUBRAY_DESACT,
                           vInterlineado_in IN CHAR DEFAULT LINEADO_SIMPLE,
                           vColorInv_in     IN CHAR DEFAULT INVERSO_DESACT,
                           vSaltoLinea_in   IN CHAR DEFAULT SALTO_LINEA_ACT,
                           vJustifica_in    IN CHAR DEFAULT 'S');

/* *************************************************************** */

  PROCEDURE P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in  IN VARCHAR2,
                                    vIpPc_in          IN VARCHAR2,
                                    vCaracter         IN CHAR,
                                    vTamanio_in       IN INTEGER DEFAULT TAMANIO_0);
  
/* *************************************************************** */

  PROCEDURE P_AGREGA_LINEA_BLANCO(vIdDoc_in  IN VARCHAR2,
                                  vIpPc_in   IN VARCHAR2);

/* *************************************************************** */

  FUNCTION F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in IN VARCHAR2,
                                      vIpPc_in  IN VARCHAR2)
  RETURN FarmaCursor;
  
/* *************************************************************** */

  PROCEDURE P_AGREGA_LOGO_MARCA(vIdDoc_in      IN VARCHAR2,
                         vIpPc_in       IN VARCHAR2,
                         vCodGrupoCia   IN VARCHAR2,
                         vCodLocal_in   IN VARCHAR2);

/* *************************************************************** */
                         
  PROCEDURE P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in        IN VARCHAR2,
                                   vIpPc_in         IN VARCHAR2,
                                   vValor_in        IN VARCHAR2,
                                   vTamanio_in      IN INTEGER,
                                   vNegrita_in      IN CHAR DEFAULT BOLD_DESACT,
                                   vAlineado_in     IN CHAR DEFAULT ALING_IZQ,
                                   vSubrayado_in    IN CHAR DEFAULT SUBRAY_DESACT,
                                   vInterlineado_in IN CHAR DEFAULT LINEADO_SIMPLE,
                                   vColorInv_in     IN CHAR DEFAULT INVERSO_DESACT);

/* *************************************************************** */
                                   
  PROCEDURE P_AGREGA_BARCODE_CODE39(vIdDoc_in      IN VARCHAR2,
                                    vIpPc_in       IN VARCHAR2,
                                    vValor_in      IN VARCHAR2,
                                    vAlineado_in   IN CHAR DEFAULT ALING_CEN);
                                    
/* *************************************************************** */
  
  PROCEDURE P_AGREGA_BARCODE_CODE128(vIdDoc_in      IN VARCHAR2,
                                     vIpPc_in       IN VARCHAR2,
                                     vValor_in      IN VARCHAR2,
                                     vAlineado_in   IN CHAR DEFAULT ALING_CEN);
                                     
  PROCEDURE P_AGREGA_BARCODE_EAN13(vIdDoc_in      IN VARCHAR2,
                                   vIpPc_in       IN VARCHAR2,
                                   vValor_in      IN VARCHAR2,
                                   vAlineado_in   IN CHAR DEFAULT ALING_CEN);
                                   
  PROCEDURE P_AGREGA_IMAGEN(vIdDoc_in      IN VARCHAR2,
                            vIpPc_in       IN VARCHAR2,
                            vRutaImagen_in IN VARCHAR2);
                            
  PROCEDURE P_AGREGA_PDF417(vIdDoc_in        IN VARCHAR2,
                            vIpPc_in         IN VARCHAR2,
                            vValor_in        IN VARCHAR2);

  /* *************************************************************** */
  
  PROCEDURE P_GRABA_LINEA_DOC(vIdDoc_in        IN VARCHAR2,
                              vIpPc_in         IN VARCHAR2,
                              vValor_in        IN VARCHAR2);

  /* *************************************************************** */

  FUNCTION F_GET_TEXTO_MATRICIAL(pTexto_in          IN VARCHAR2,
                                 pLongitud_in       IN NUMBER,
                                 pAlineacion_in     IN CHAR DEFAULT 'I',
                                 pCaracCompleta_in  IN CHAR DEFAULT ' ') 
  RETURN VARCHAR2;

    /*** CCASTILLO 30/05/2016 ***/                       
    FUNCTION F_CALCULA_PAPEL_TERMICO(iTipoDato_in         IN INTEGER,
                                       iTamanio_in          IN INTEGER,
                                       iLenCadena_in        IN INTEGER,
                                       vTipoImpresora_in    IN VARCHAR2)
                                        
    RETURN NUMBER;
    /*** CCASTILLO 30/05/2016 ***/
  
                                 
END FARMA_PRINTER;
/
CREATE OR REPLACE PACKAGE BODY FARMA_PRINTER is

  FUNCTION F_GENERA_ID_DOC  
    RETURN VARCHAR2  IS
    vCampo VARCHAR2(4000);
  BEGIN
    SELECT TRIM(TO_char(systimestamp, 'ddmmyyyyHH24MISSFF'))
    INTO   vCampo
    FROM   dual;
    RETURN vCampo ;
  END;

/* *************************************************************** */

  FUNCTION F_GET_IP_SESS
    RETURN VARCHAR2  IS
  BEGIN
   RETURN SYS_CONTEXT('USERENV','IP_ADDRESS');
  END;
  
/* *************************************************************** */
 
  FUNCTION F_INT_LINEA_DOC(vIdDoc_in IN VARCHAR2,
                           vIpPc_in  IN VARCHAR2)
    RETURN INTEGER IS
    nValPos INTEGER;        
  BEGIN
   
   SELECT COUNT(1)+1
   INTO   nValPos
   FROM   IMPRESION_TERMICA D  
   WHERE  D.IP_PC  = vIpPc_in
   AND    D.ID_DOC = vIdDoc_in;
  
   RETURN nValPos;
  END;
  
/* *************************************************************** */

  PROCEDURE P_GRABA_LINEA_DOC(vIdDoc_in        IN VARCHAR2,
                              vIpPc_in         IN VARCHAR2,
                              vValor_in        IN VARCHAR2,
                              vTipoDato_in     IN INTEGER,
                              vTamanio_in      IN INTEGER,
                              vAlineado_in     IN CHAR,
                              vNegrita_in      IN CHAR,
                              vSubrayado_in    IN CHAR,
                              vInterlineado_in IN CHAR,
                              vColorInv_in     IN CHAR,
                              vSaltoLinea_in   IN CHAR) IS
                             
    
  /*** CCASTILLO 30/05/2016 ***/
   v_lon_ptermico number:=0;
  /*** CCASTILLO 30/05/2016 ***/ 
                              
  BEGIN
  
  /*** CCASTILLO 30/05/2016 ***/
    BEGIN
      IF(G_FLAGIMPRESORA=FALSE) then
        SELECT DISTINCT FARMA_GRAL.GET_TIPO_IMPR_CONSEJO_X_IP(a.cod_grupo_cia,a.cod_local) INTO G_TIPOIMPRESORA FROM VTA_IMPR_LOCAL a;
        G_FLAGIMPRESORA:=TRUE;
      END IF;
      
      v_lon_ptermico:=F_CALCULA_PAPEL_TERMICO(vTipoDato_in,vTamanio_in,NVL(LENGTH(vValor_in),0),G_TIPOIMPRESORA);
      
      IF(v_lon_ptermico IS NULL) THEN
        v_lon_ptermico:=0;               
      END IF;
    
    EXCEPTION
     WHEN OTHERS THEN   
        v_lon_ptermico:=0;
    END;
    /*** CCASTILLO 30/05/2016 ***/  
     
  
    INSERT INTO IMPRESION_TERMICA(IP_PC, 
                                  ID_DOC, 
                                  ORDEN, 
                                  VALOR, 
                                  TIPO_DATO, 
                                  TAMANIO, 
                                  ALINEACION,
                                  NEGRITA, 
                                  SUBRAYADO, 
                                  INTERLINEADO, 
                                  COLOR_INVERSO, 
                                  /*** CCASTILLO 30/05/2016 ***/ 
                                  --SALTO_LINEA)
                                  SALTO_LINEA,
                                  LON_PTERMICO)
                                  /*** CCASTILLO 30/05/2016 ***/
    VALUES (vIpPc_in, 
            vIdDoc_in, 
            F_INT_LINEA_DOC(vIdDoc_in,vIpPc_in), 
            vValor_in,
            vTipoDato_in,
            vTamanio_in,
            vAlineado_in,
            vNegrita_in,
            vSubrayado_in,
            vInterlineado_in,
            vColorInv_in,
            /*** CCASTILLO 30/05/2016 ***/ 
            --vSaltoLinea_in);
            vSaltoLinea_in,
            v_lon_ptermico);
            /*** CCASTILLO 30/05/2016 ***/ 
            
  END;  

/* *************************************************************** */

  PROCEDURE P_AGREGA_LOGO_MARCA(vIdDoc_in      IN VARCHAR2,
                                vIpPc_in       IN VARCHAR2,
                                vCodGrupoCia   IN VARCHAR2,
                                vCodLocal_in   IN VARCHAR2) IS
    
    vNombreImagen VARCHAR2(100);
  begin
	--ERIOS 18.02.2016 Se obtiene el log por marca.
	vNombreImagen := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\\'||
					 PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\\'||
					 PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||
					 PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(vCodGrupoCia,'',vCodLocal_in);
    
    P_AGREGA_IMAGEN(vIdDoc_in => vIdDoc_in,vIpPc_in => vIpPc_in,vRutaImagen_in => vNombreImagen);

  end; 
  
  PROCEDURE P_AGREGA_IMAGEN(vIdDoc_in      IN VARCHAR2,
                            vIpPc_in       IN VARCHAR2,
                            vRutaImagen_in IN VARCHAR2) IS
    
    
  begin

    P_GRABA_LINEA_DOC( vIdDoc_in => vIdDoc_in,
                       vIpPc_in => vIpPc_in,
                       vValor_in => vRutaImagen_in,
                       vTipoDato_in => TD_IMAGEN, 
                       vTamanio_in => TAMANIO_1, 
                       vAlineado_in => ALING_CEN, 
                       vNegrita_in =>  BOLD_DESACT,
                       vSubrayado_in =>  SUBRAY_DESACT,
                       vInterlineado_in => LINEADO_SIMPLE,
                       vColorInv_in => INVERSO_DESACT,
                       vSaltoLinea_in => SALTO_LINEA_ACT);
  end; 

/* *************************************************************** */

  PROCEDURE P_AGREGA_BARCODE_CODE39(vIdDoc_in      IN VARCHAR2,
                                    vIpPc_in       IN VARCHAR2,
                                    vValor_in      IN VARCHAR2,
                                    vAlineado_in   IN CHAR DEFAULT ALING_CEN) IS
  BEGIN
    P_GRABA_LINEA_DOC( vIdDoc_in => vIdDoc_in,
                       vIpPc_in => vIpPc_in,
                       vValor_in => vValor_in,
                       vTipoDato_in => TD_BARCODE_CODE39, 
                       vTamanio_in => TAMANIO_1, 
                       vAlineado_in => vAlineado_in, 
                       vNegrita_in =>  BOLD_DESACT,
                       vSubrayado_in =>  SUBRAY_DESACT,
                       vInterlineado_in => LINEADO_SIMPLE,
                       vColorInv_in => INVERSO_DESACT,
                       vSaltoLinea_in => SALTO_LINEA_ACT);
  END;
  
/* *************************************************************** */

  PROCEDURE P_AGREGA_BARCODE_CODE128(vIdDoc_in      IN VARCHAR2,
                                     vIpPc_in       IN VARCHAR2,
                                     vValor_in      IN VARCHAR2,
                                     vAlineado_in   IN CHAR DEFAULT ALING_CEN) IS
  BEGIN
    P_GRABA_LINEA_DOC( vIdDoc_in => vIdDoc_in,
                       vIpPc_in => vIpPc_in,
                       vValor_in => vValor_in,
                       vTipoDato_in => TD_BARCODE_CODE128, 
                       vTamanio_in => TAMANIO_1, 
                       vAlineado_in => vAlineado_in, 
                       vNegrita_in =>  BOLD_DESACT,
                       vSubrayado_in =>  SUBRAY_DESACT,
                       vInterlineado_in => LINEADO_SIMPLE,
                       vColorInv_in => INVERSO_DESACT,
                       vSaltoLinea_in => SALTO_LINEA_ACT);
  END;
  
/* *************************************************************** */

  PROCEDURE P_AGREGA_BARCODE_EAN13(vIdDoc_in      IN VARCHAR2,
                                   vIpPc_in       IN VARCHAR2,
                                   vValor_in      IN VARCHAR2,
                                   vAlineado_in   IN CHAR DEFAULT ALING_CEN) IS
  BEGIN
    P_GRABA_LINEA_DOC( vIdDoc_in => vIdDoc_in,
                       vIpPc_in => vIpPc_in,
                       vValor_in => vValor_in,
                       vTipoDato_in => TD_BARCODE_EAN13, 
                       vTamanio_in => TAMANIO_1, 
                       vAlineado_in => vAlineado_in, 
                       vNegrita_in =>  BOLD_DESACT,
                       vSubrayado_in =>  SUBRAY_DESACT,
                       vInterlineado_in => LINEADO_SIMPLE,
                       vColorInv_in => INVERSO_DESACT,
                       vSaltoLinea_in => SALTO_LINEA_ACT);
  END;
  
/* *************************************************************** */
  PROCEDURE P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in        IN VARCHAR2,
                                   vIpPc_in         IN VARCHAR2,
                                   vValor_in        IN VARCHAR2,
                                   vTamanio_in      IN INTEGER,
                                   vNegrita_in      IN CHAR DEFAULT BOLD_DESACT,
                                   vAlineado_in     IN CHAR DEFAULT ALING_IZQ,
                                   vSubrayado_in    IN CHAR DEFAULT SUBRAY_DESACT,
                                   vInterlineado_in IN CHAR DEFAULT LINEADO_SIMPLE,
                                   vColorInv_in     IN CHAR DEFAULT INVERSO_DESACT) IS
  
  BEGIN
    P_GRABA_LINEA_DOC( vIdDoc_in => vIdDoc_in,
                       vIpPc_in => vIpPc_in,
                       vValor_in => vValor_in,
                       vTipoDato_in => TD_TEXTO,
                       vTamanio_in => vTamanio_in,
                       vAlineado_in => NVL(vAlineado_in, ALING_IZQ),
                       vNegrita_in => NVL(vNegrita_in, BOLD_DESACT),
                       vSubrayado_in => NVL(vSubrayado_in, SUBRAY_DESACT),
                       vInterlineado_in => NVL(vInterlineado_in, LINEADO_SIMPLE) ,
                       vColorInv_in => NVL(vColorInv_in, INVERSO_DESACT),
                       vSaltoLinea_in => SALTO_LINEA_DESACT);
  END;

/* *************************************************************** */

  PROCEDURE P_AGREGA_TEXTO(vIdDoc_in        IN VARCHAR2,
                           vIpPc_in         IN VARCHAR2,
                           vValor_in        IN VARCHAR2,
                           vTamanio_in      IN INTEGER,
                           vAlineado_in     IN CHAR,
                           vNegrita_in      IN CHAR DEFAULT BOLD_DESACT,
                           vSubrayado_in    IN CHAR DEFAULT SUBRAY_DESACT,
                           vInterlineado_in IN CHAR DEFAULT LINEADO_SIMPLE,
                           vColorInv_in     IN CHAR DEFAULT INVERSO_DESACT,
                           vSaltoLinea_in   IN CHAR DEFAULT SALTO_LINEA_ACT,
                           vJustifica_in    IN CHAR DEFAULT 'S') IS
    
    vCantCaracteresLinea INTEGER;
    lstValor             ArrayList;
    vTotal               INTEGER;
  BEGIN
    
    vCantCaracteresLinea := CANTIDAD_CARACTERES_TERMICO(cTamanioTexto_in => vTamanio_in);
      
    IF vAlineado_in = ALING_JUSTI THEN
      lstValor := JUSTIFICA_TEXTO(vValor_in => vValor_in, 
                                  cTamanioTexto_in => vTamanio_in);
    ELSE
      IF NVL(LENGTH(vValor_in),0) > vCantCaracteresLinea AND vJustifica_in = 'S' THEN
        lstValor := JUSTIFICA_TEXTO(vValor_in => vValor_in, 
                                    cTamanioTexto_in => vTamanio_in);
      ELSE
        lstValor(1) := vValor_in;
      END IF;
    END IF;
    
    vTotal := lstValor.count;
    FOR i in 1 .. vTotal LOOP
      P_GRABA_LINEA_DOC( vIdDoc_in => vIdDoc_in,
                         vIpPc_in => vIpPc_in,
                         vValor_in => lstValor(i),
                         vTipoDato_in => TD_TEXTO,
                         vTamanio_in => NVL(vTamanio_in, TAMANIO_1),
                         vAlineado_in => NVL(vAlineado_in, ALING_IZQ),
                         vNegrita_in => NVL(vNegrita_in, BOLD_DESACT),
                         vSubrayado_in => NVL(vSubrayado_in, SUBRAY_DESACT),
                         vInterlineado_in => NVL(vInterlineado_in, LINEADO_SIMPLE) ,
                         vColorInv_in => NVL(vColorInv_in, INVERSO_DESACT),
                         vSaltoLinea_in => NVL(vSaltoLinea_in, SALTO_LINEA_ACT));
    END LOOP;
    
    
 
                    
  END;  

/* *************************************************************** */
  
  PROCEDURE P_AGREGA_LINEA_BLANCO(vIdDoc_in  IN VARCHAR2,
                                  vIpPc_in   IN VARCHAR2) IS
  BEGIN
    P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc_in,
                                   vIpPc_in => vIpPc_in, 
                                   vCaracter => ' ');
  END; 

/* *************************************************************** */

  PROCEDURE P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in  IN VARCHAR2,
                                    vIpPc_in          IN VARCHAR2,
                                    vCaracter         IN CHAR,
                                    vTamanio_in       IN INTEGER DEFAULT TAMANIO_0) IS
    vCantCaracteresLinea INTEGER;
    vCadena VARCHAR2(100);
  BEGIN
    vCantCaracteresLinea := CANTIDAD_CARACTERES_TERMICO(cTamanioTexto_in => vTamanio_in);
    vCadena := RPAD(vCaracter, vCantCaracteresLinea-1, vCaracter);
    P_AGREGA_TEXTO( vIdDoc_in => vIdDoc_in, 
                    vIpPc_in => vIpPc_in, 
                    vValor_in => vCadena, 
                    vTamanio_in => TAMANIO_0, 
                    vAlineado_in => ALING_CEN);
    
  END;
 
/* *************************************************************** */

  FUNCTION JUSTIFICA_TEXTO(vValor_in         IN VARCHAR2,
                           cTamanioTexto_in  IN INTEGER)
    RETURN ArrayList IS
    
    vAux VARCHAR(5000);
    vCantCaracteresLinea INTEGER;
    vLinea VARCHAR2(100 CHAR) := '';
    
    vEspacioBlanco INTEGER;
    vPalabra VARCHAR2(100 CHAR);
    
    
    lstValor ArrayList;
    vPosArray INTEGER := 1;
    
  BEGIN
    
    vCantCaracteresLinea := CANTIDAD_CARACTERES_TERMICO(cTamanioTexto_in => cTamanioTexto_in);
    
    IF LENGTH(vValor_in) > vCantCaracteresLinea THEN
      vAux := vValor_in;
      vLinea := '';
      vPalabra := '';
      WHILE LENGTH(vAux)!=0
      LOOP
        vEspacioBlanco := INSTR(vAux, ' ', 1, 1);
        IF vEspacioBlanco != 0 THEN
          vPalabra := SUBSTR(vAux,0,vEspacioBlanco-1);
          
          IF NVL(LENGTH(vLinea),0) + LENGTH(vPalabra) < vCantCaracteresLinea THEN
            IF NVL(LENGTH(vLinea),0) = 0 THEN
              vLinea := vPalabra;
            ELSE
              vLinea := vLinea || ' ' || vPalabra;
            END IF;
          ELSE
            IF INSTR(vLinea, ' ', 1, 1) != 0 THEN
              lstValor(vPosArray) := APLICA_JUSTIFICA(vLinea, vCantCaracteresLinea);
              vPosArray := vPosArray + 1;
              vLinea := vPalabra;
            ELSE
              lstValor(vPosArray) := vLinea;
              vPosArray := vPosArray + 1;
              vLinea := vPalabra;
            END IF;
          END IF;
          
          vAux := TRIM(SUBSTR(vAux, vEspacioBlanco+1));
        ELSE
          
          IF NVL(LENGTH(vLinea),0) + LENGTH(vAux) < vCantCaracteresLinea THEN
            IF NVL(LENGTH(vLinea),0) = 0 THEN
              vLinea := vAux;
            ELSE
              vLinea := vLinea || ' ' || vAux;
            END IF;
            vAux := '';
          ELSE
            NULL;
          END IF;
          
          IF INSTR(vLinea, ' ', 1, 1) != 0 THEN
            lstValor(vPosArray) := APLICA_JUSTIFICA(vLinea, vCantCaracteresLinea);
            vPosArray := vPosArray + 1;
            --vLinea := vAux;
          ELSE
            lstValor(vPosArray) := vLinea;
            vPosArray := vPosArray + 1;
          END IF;
          
          
          -- vLinea := vLinea ||' '||vAux;
          IF NVL(LENGTH(vAux),0) != 0 THEN 
            lstValor(vPosArray) := vAux;
            vPosArray := vPosArray + 1;
            vAux := '';
          END IF;
        END IF;
      END LOOP;
    ELSE
      lstValor(vPosArray) := APLICA_JUSTIFICA(vValor_in, vCantCaracteresLinea);
      vPosArray := vPosArray + 1;
    END IF;
    
    RETURN lstValor;
  END;
/* *************************************************************** */  
  FUNCTION F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in IN VARCHAR2,
                                      vIpPc_in  IN VARCHAR2)
  RETURN FarmaCursor
  IS
    curData FarmaCursor;
  BEGIN
    OPEN curData FOR
      SELECT T.*
      FROM   IMPRESION_TERMICA T
      WHERE  T.IP_PC = vIpPc_in
      AND T.ID_DOC = vIdDoc_in
      ORDER BY T.ORDEN ASC;
    RETURN curData;
  END;
  
/* *************************************************************** */  

  FUNCTION APLICA_JUSTIFICA(vLinea_in              IN VARCHAR2,
                            vCantidadCaracteres_in IN INTEGER)
    RETURN VARCHAR2 IS
    CURSOR curLineas is
    SELECT X.CADENA
    FROM (
       SELECT 
         REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')), 'Ã', '&')), 'Ë', '<') cadena
       FROM TABLE(XMLSEQUENCE(
                       EXTRACT(
                          XMLTYPE('<coll><e>' ||REPLACE(REPLACE((REPLACE((vLinea_in),'&', 'Ã')), '<', 'Ë'),' ','</e><e>') ||'</e></coll>'),
                          '/coll/e'))) xt
            
      ) X
    WHERE TRIM(X.cadena) IS NOT NULL;
    
    lineCursor curLineas%rowtype; 

    vDiferencia INTEGER;
    vLetra VARCHAR2(4000);
    vPosFinal INTEGER := 0;
    vEspaciosAnt VARCHAR2(20);
    vEspacios VARCHAR2(20);
    vCont INTEGER;
    vCantPalabras INTEGER;
    vMinJustifica INTEGER;
  BEGIN
    vLetra := vLinea_in;
    vMinJustifica := vCantidadCaracteres_in * 0.8;
    IF LENGTH(vLetra) <= vCantidadCaracteres_in AND LENGTH(vLetra) >= vMinJustifica THEN
      vDiferencia := vCantidadCaracteres_in - LENGTH(TRIM(vLetra));
      vEspaciosAnt := '';
      vEspacios := ' ';
      SELECT COUNT(1)
        INTO vCantPalabras
        FROM (SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),
                                      'Ã',
                                      '&')),
                             'Ë',
                             '<') cadena
                FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                       REPLACE(REPLACE((REPLACE((vLinea_in),
                                                                                '&',
                                                                                'Ã')),
                                                                       '<',
                                                                       'Ë'),
                                                               ' ',
                                                               '</e><e>') ||
                                                       '</e></coll>'),
                                               '/coll/e'))) xt
              
              ) x
       WHERE TRIM(x.cadena) IS NOT NULL;
      IF vCantPalabras > 1 THEN
        IF vDiferencia > 0 THEN
         
          WHILE vDiferencia > 0
          LOOP
            vLetra := '';
            vPosFinal := 0;
            vEspaciosAnt := vEspaciosAnt || ' ';
            vEspacios := vEspacios || ' ';
            vCont := 0;
            OPEN curLineas;
            LOOP
              FETCH curLineas INTO lineCursor;
              EXIT WHEN curLineas%NOTFOUND;
                IF vCont = 0 THEN
                  vLetra := lineCursor.Cadena;
                ELSE
                  vLetra := vLetra || vEspacios || lineCursor.Cadena;
                  vDiferencia := vDiferencia - 1;
                  IF vDiferencia < 1 THEN
                    EXIT;
                  END IF;
                END IF;
                vPosFinal := vCont;
                vCont := vCont + 1;
              
            END LOOP;
            CLOSE curLineas;
          END LOOP;
          
          IF vPosFinal + 1 !=  vCantPalabras THEN
            OPEN curLineas;
            LOOP
              FETCH curLineas INTO lineCursor;
              EXIT WHEN curLineas%NOTFOUND;
                IF (curLineas%ROWCOUNT > (vPosFinal + 2)) THEN
                  vLetra := vLetra || vEspaciosAnt || lineCursor.Cadena;
                END IF;
            END LOOP;
            CLOSE curLineas;
          END IF;
        END IF;
      END IF;
    END IF;
--    DBMS_OUTPUT.PUT_LINE('PALABRA INICIAL --> |'||vLinea_in||'|'||LENGTH(vLinea_in));
    DBMS_OUTPUT.PUT_LINE('PALABRA FINAL --> |'||vLetra||'|'||LENGTH(vLetra));
    RETURN vLetra;
  END;
  
/* *************************************************************** */

  FUNCTION CANTIDAD_CARACTERES_TERMICO(cTamanioTexto_in IN INTEGER)
    RETURN INTEGER IS
    cValor INTEGER;
  BEGIN
    IF cTamanioTexto_in = 0 THEN
      cValor := 64;
    ELSIF cTamanioTexto_in = 1 THEN
      cValor := 48;
    ELSIF cTamanioTexto_in = 2 THEN
      cValor := 24;
    ELSIF cTamanioTexto_in = 3 THEN
      cValor := 16;
    ELSIF cTamanioTexto_in = 4 THEN
      cValor := 12;
    ELSIF cTamanioTexto_in = 5 THEN
      cValor := 9;
    ELSIF cTamanioTexto_in = 6 THEN
      cValor := 8;
    END IF;
    RETURN cValor;
  END;
  
  PROCEDURE P_AGREGA_PDF417(vIdDoc_in        IN VARCHAR2,
                            vIpPc_in         IN VARCHAR2,
                            vValor_in        IN VARCHAR2) IS
    
    vCantCaracteresLinea INTEGER;
    lstValor             ArrayList;
    vTotal               INTEGER;
  BEGIN
    
    
    P_GRABA_LINEA_DOC( vIdDoc_in => vIdDoc_in,
                       vIpPc_in => vIpPc_in,
                       vValor_in => vValor_in,
                       vTipoDato_in => TD_PDF417,
                       vTamanio_in => TAMANIO_0,
                       vAlineado_in => ALING_CEN,
                       vNegrita_in => BOLD_DESACT,
                       vSubrayado_in => SUBRAY_DESACT,
                       vInterlineado_in => LINEADO_SIMPLE,
                       vColorInv_in => INVERSO_DESACT,
                       vSaltoLinea_in => SALTO_LINEA_ACT);

    
    
 
                    
  END;
  
  /* *************************************************************** */
  
  PROCEDURE P_GRABA_LINEA_DOC(vIdDoc_in        IN VARCHAR2,
                              vIpPc_in         IN VARCHAR2,
                              vValor_in        IN VARCHAR2)
  IS
  BEGIN
    P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                   vIpPc_in => vIpPc_in, 
                   vValor_in => vValor_in,
                   vTamanio_in => TAMANIO_0,
                   vAlineado_in => ALING_IZQ,
                   vJustifica_in => 'N');
  END; 

  /* *************************************************************** */
    
  FUNCTION F_GET_TEXTO_MATRICIAL(pTexto_in          IN VARCHAR2,
                                 pLongitud_in       IN NUMBER,
                                 pAlineacion_in     IN CHAR DEFAULT 'I',
                                 pCaracCompleta_in  IN CHAR DEFAULT ' ') 
  RETURN VARCHAR2 IS
    vCadena VARCHAR2(200);
    vLongCadena NUMBER;
    vResto NUMBER;
  BEGIN
    IF TRIM(pTexto_in) IS NULL THEN
      vCadena := RPAD(' ', pLongitud_in, pCaracCompleta_in);
    ELSE
      vCadena := TRIM(pTexto_in);
      vLongCadena := LENGTH(vCadena);
      IF vLongCadena >= pLongitud_in then
        vCadena := SUBSTR(vCadena,0,pLongitud_in);
      ELSE
        IF pAlineacion_in = 'C' THEN
          vLongCadena := pLongitud_in - vLongCadena;
          vResto := MOD(vLongCadena, 2);
          vLongCadena := TRUNC(vLongCadena/2);
          vCadena := LPAD(vCadena,(vLongCadena + LENGTH(vCadena)),pCaracCompleta_in);
          vCadena := RPAD(vCadena,(vLongCadena+vResto + LENGTH(vCadena)),pCaracCompleta_in);
        ELSIF pAlineacion_in = 'D' THEN
          vCadena := LPAD(vCadena,pLongitud_in,pCaracCompleta_in);
        ELSE -- IZQUIERDA
          vCadena := RPAD(vCadena,pLongitud_in,pCaracCompleta_in);
        END IF;
      END IF;
    END IF;
    RETURN vCadena;
  END;

  /*************************************************************************************************/
  
  FUNCTION F_CALCULA_PAPEL_TERMICO(iTipoDato_in         IN INTEGER,
                                   iTamanio_in          IN INTEGER,
                                   iLenCadena_in        IN INTEGER,
                                   vTipoImpresora_in    IN VARCHAR2)
                                        
    RETURN NUMBER IS   
     
  	v_longitud	number:=0;
  	v_CantCaracteresLinea integer:=0;
  	v_TipoLocal PBL_LOCAL.COD_TIP_LOCAL_VENTA%TYPE;
    v_TipoImpresora VTA_IMPR_LOCAL_TERMICA.TIPO_IMPR_TERMICA%TYPE;

  BEGIN
  
  v_TipoImpresora:=TRIM(NVL(vTipoImpresora_in,'01'));
  
       IF(iTipoDato_in=1) THEN -- Texto
      				IF (v_TipoImpresora='01') THEN ---EN EPSON
      	
      					CASE iTamanio_in
      					  WHEN 0 THEN v_longitud:=2.5; --milimetros
      					  WHEN 1 THEN v_longitud:=3;
      					  WHEN 2 THEN v_longitud:=6;
      					  WHEN 3 THEN v_longitud:=9;
      					  WHEN 4 THEN v_longitud:=12;
      					  WHEN 5 THEN v_longitud:=15;
      					  WHEN 6 THEN v_longitud:=18;
      					  ELSE v_longitud:=2.5;	  
      					END CASE;
      					
      				ELSIF(v_TipoImpresora='02') THEN ---EN STAR
      				
      					CASE iTamanio_in
      					  WHEN 0 THEN v_longitud:=3; --milimetros
      					  WHEN 1 THEN v_longitud:=3.5;
      					  WHEN 2 THEN v_longitud:=6.5;
      					  WHEN 3 THEN v_longitud:=9.5;
      					  WHEN 4 THEN v_longitud:=12.5;
      					  WHEN 5 THEN v_longitud:=15.5;
      					  WHEN 6 THEN v_longitud:=18.5;
      					  ELSE v_longitud:=3;	  
      					END CASE;
      				
      				END IF;
      				
      	  
      					v_CantCaracteresLinea := FARMA_PRINTER.CANTIDAD_CARACTERES_TERMICO(iTamanio_in); -- Función de impresión
      	  
      					if not(iLenCadena_in > v_CantCaracteresLinea) then
      						 v_longitud:=v_longitud;
      					else
      						 v_longitud:=v_longitud*2; -- 2 lineas para los productos
      					end if;
      
      
      
       ELSIF(iTipoDato_in=2) THEN --codigo pdf
                  
      				IF (v_TipoImpresora='01') THEN ---EN EPSON
      					CASE iTamanio_in
      						WHEN 0 THEN v_longitud:=21.5;
      					    ELSE v_longitud:=21.5;	  
      					END CASE;
      				
      				ELSIF(v_TipoImpresora='02') THEN ---EN STAR
      					CASE iTamanio_in
      						WHEN 0 THEN v_longitud:=26;
      					    ELSE v_longitud:=26;	  
      					END CASE;
      					
      				END IF;
      					
      			
       ELSIF(iTipoDato_in=3) THEN --Logo
      
      				IF (v_TipoImpresora='01') THEN ---EN EPSON
      					CASE iTamanio_in
      						WHEN 1 THEN v_longitud:=9;
      					    ELSE v_longitud:=9;	  
      					END CASE;
      				
      				ELSIF(v_TipoImpresora='02') THEN ---EN STAR
      					CASE iTamanio_in
      						WHEN 1 THEN v_longitud:=11;
      					    ELSE v_longitud:=11;	  
      					END CASE;
      					
      				END IF;
      			
      				SELECT TRIM(NVL(L.COD_TIP_LOCAL_VENTA , '001'))  INTO v_TipoLocal -- Obtiene el tipo de local
      				FROM PBL_LOCAL L, PBL_CIA C,(SELECT cod_grupo_cia,cod_local FROM VTA_IMPR_LOCAL WHERE ROWNUM<=1) X
      			    WHERE L.COD_CIA = C.COD_CIA
      				AND L.COD_GRUPO_CIA = X.COD_GRUPO_CIA
      				AND L.COD_LOCAL = X.COD_LOCAL;
      		
      				IF(v_TipoLocal='002') THEN -- solo para ticos
      					v_longitud:=v_longitud+4; -- +-3 milimetros de error
      				END IF;
      			
       ELSIF(iTipoDato_in=5) THEN -- codigo barras
      			
      				IF (v_TipoImpresora='01') THEN ---EN EPSON
      					CASE iTamanio_in
      						WHEN 1 THEN v_longitud:=15;
      					    ELSE v_longitud:=15;	  
      					END CASE;
      				
      				ELSIF(v_TipoImpresora='02') THEN ---EN STAR
      					CASE iTamanio_in
      						WHEN 1 THEN v_longitud:=17;
      					    ELSE v_longitud:=17;	  
      					END CASE;
      					
      				END IF;
      			
       ELSE
      				v_longitud:=2.5;
       END IF;
        
       RETURN v_longitud;
        
    EXCEPTION 
    WHEN OTHERS THEN
      v_longitud:=0;
      RETURN v_longitud;
              
  END;
   
  -----
  
END FARMA_PRINTER;
/
