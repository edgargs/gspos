--------------------------------------------------------
--  DDL for Package PTOVENTA_RECEP_CIEGA_AS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RECEP_CIEGA_AS" is

TYPE FarmaCursor IS REF CURSOR;
C_INICIO_MSG VARCHAR2(20000) := '<html>'  ||
                                      '<head>'  ||
                                      '<style type="text/css">'  ||
                                      '.style3 {font-family: Arial, Helvetica, sans-serif}'  ||
                                      '.style8 {font-size: 24; }'  ||
                                      '.style9 {font-size: larger}'  ||
                                      '.style12 {'  ||
                                      'font-family: Arial, Helvetica, sans-serif;'  ||
                                      'font-size: larger;'  ||
                                      'font-weight: bold;'  ||
                                      '}'  ||
                                      '</style>'  ||
                                      '</head>'  ||
                                      '<body>'  ||
                                      '<table width="510"border="0">'  ||
                                      '<tr>'  ||
                                      '<td width="500" align="center" valign="top"><h1>CONSTANCIA <BR> INGRESO&nbsp;DE&nbsp;TRANSPORTISTA</h1></td>'  ||
                                      '</tr>'  ||
                                      '</table>'  ||
                                      '<table width="504" border="0">';

  C_FIN_MSG VARCHAR2(2000) := '</table>' ||
                                  '</body>' ||
                                  '</html>';
                                  
   TIPO_PROVEEDOR CHAR(1) := 'P'; --ASOSA - 25/07/2014


--Descripcion: Obtiene listado de empresas de transporte para recepcion ciega
--Fecha       Usuario		Comentario
--05/04/2010  ASOSA     Creación
FUNCTION RECEP_F_LISTA_TRANSP
RETURN FarmaCursor;

--Descripcion: Inserta la recepcion con el codigo de empresa de ransporte para recepcion ciega
--Fecha       Usuario		Comentario
--06/04/2010  ASOSA     Creación
-- AAMPUERO 15.04.2014                      nCantPrecintos IN NUMBER,

FUNCTION RECEP_F_INS_TRANSPORTISTA(cCodGrupoCia_in IN CHAR,
                                      cCodLocal      IN CHAR,
                                      cCantGuias     IN NUMBER,
                                      cIdUsu_in      IN CHAR,
                                      cNombTransp    IN CHAR,
                                      cPlaca         IN CHAR,
                                      nCantBultos    IN NUMBER,
                                      nCantBandejas IN NUMBER,
                                      cGlosa IN VARCHAR2 DEFAULT '',
                                      cSecUsu_in     IN CHAR,
                                      cCodTransp IN  CHAR,
                                       cNroHojaRes IN VARCHAR2,    --ASOSA - 24/07/2014
                                       cCodValija IN varchar2, --ASOSA - 25/07/2014
                                       aBandejas IN VARCHAR2_TABLE--ASOSA - 25/07/2014                                       
                                      )
RETURN VARCHAR2;

--Descripcion: Diseña y devuelve el texto que se imprimira en la constancia de transportista
--Fecha       Usuario		Comentario
--06/04/2010  ASOSA     Creación
FUNCTION RECEP_F_VAR2_IMP_VOUCHER(cGrupoCia_in  IN CHAR,
                                              cCodLocal_in  IN CHAR,
                                              cNroRecep_in IN VARCHAR2)
RETURN VARCHAR2;

--Descripcion: Devuelve cantidad de tickets para imprimir.
--Fecha       Usuario		Comentario
--05/05/2010  JQUISPE     Creación
  FUNCTION  RECEP_F_GET_NUM_IMPRES
  RETURN CHAR;
  
--Descripcion: Devuelve el numero de DT mas la cantidad de bultos que contiene.
--Fecha       Usuario		Comentario
--18/07/2014  ASOSA    Creación
FUNCTION RECEP_F_GET_DOC_TRANSPORTE(vNroHoja_in IN varchar2)
RETURN FarmaCursor;

--Descripcion: Devuelve "S" si existe la bandeja asociada a la hoja de resumen (Documento de Transporte), o "N" si no.
--Fecha       Usuario		Comentario
--18/07/2014  ASOSA    Creación
FUNCTION RECEP_F_GET_BANDEJA_HR(vNroHoja_in IN varchar2,
                                                                      vNroBandeja_in IN varchar2)
RETURN CHAR;

--Descripcion: Devuelve "S" si existe grabado previamente el numero de hoja de resumen, o "N" si no.
--Fecha       Usuario		Comentario
--13/08/2014  ASOSA    Creación
FUNCTION RECEP_F_EXISTS_HOJA_RES(vNroHoja_in IN varchar2)
RETURN CHAR;

end PTOVENTA_RECEP_CIEGA_AS;

/
