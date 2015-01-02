--------------------------------------------------------
--  DDL for Package PTOVENTA_IMP_CONSEJOS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_IMP_CONSEJOS" AS

  TYPE FarmaCursor IS REF CURSOR;
  C_INDICADOR_NO CHAR(1) := 'N';
  C_INDICADOR_SI CHAR(1) := 'S';
  C_ESTADO_ACTIVO CHAR(1) := 'A';
  C_TITULAR CHAR(1) := 'T';

  C_SIZE_CONSEJO   VARCHAR2(200) := '18';
  C_SIZE_MSG_FINAL VARCHAR2(200) := '10';

/*
  C_INICIO_MSG  VARCHAR2(2000) := '<html>
                                <head>
                                <style type="text/css">
                                <!--
                                .style2 {font-size: '||C_SIZE_MSG_FINAL||'; }
                                .style5 {font-size: '||C_SIZE_CONSEJO||'; }
                                .style3 {font-family: Arial, Helvetica, sans-serif}
                                .style8 {font-size: 24; }
                                .style9 {font-size: larger}
                                .style12 {
                                 font-family: Arial, Helvetica, sans-serif;
                                 font-size: larger;
                                 font-weight: bold;
                                }
                                </style>
                                </head>
                                <body>

                                <table width="200" border="0">
                                  <tr>
                                    <td>&nbsp;&nbsp;</td>
                                    <td><table width="300" height="841" border="1">
                                ';*/

  --Modificado por fernando Veliz La Rosa 02.09.08
  C_INICIO_MSG  VARCHAR2(2000) := '<html>
                                  <head>
                                  <style type="text/css">
                                  .style2 {font-size: '||C_SIZE_MSG_FINAL||'; }
                                  .style5 {font-size: '||C_SIZE_CONSEJO||'; }

                                  .style3 {font-family: Arial, Helvetica, sans-serif}
                                  .style8 {font-size: 24; }
                                  .style9 {font-size: 14}
                                  .style12 {
                                   font-family: Arial, Helvetica, sans-serif;
                                   font-size: larger;
                                   font-weight: bold;
                                  }
                                  </style>
                                  </head>
                                  <body>

                                  <table width="200" border="0">
                                  <tr>
                                    <td>&nbsp;&nbsp;</td>
                                    <td>
                                    <table width="300" height="841" border="1">';

  C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                  '<td height="2" colspan="3"></td> '||
                                  ' </tr> ';

  C_FIN_MSG     VARCHAR2(2000) := ' </table></td>
                                      </tr>
                                    </table>
                                    <p><br>
                                      <br>
                                    </p>
                                    </body>
                                    </html> ';

  FUNCTION IMP_GET_SEPARADOR
  RETURN VARCHAR2;

  --Descripcion: Obtiene mensje 01 de consejo
  --Fecha       Usuario		Comentario
  --09/05/2008  DUBILLUZ  Creación
  FUNCTION IMP_GET_MSG_01_CONSEJO
  RETURN VARCHAR2;

  --Descripcion: Obtiene nombre del cliente
  --Fecha       Usuario		Comentario
  --09/05/2008  DUBILLUZ  Creación
  FUNCTION IMP_GET_NAME_CLI(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2;

 FUNCTION IMP_GET_PIE_PAGINA(cCodGrupoCia_in 	IN CHAR,
                             cCodLocal_in    	IN CHAR,
                						 cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene consejos
  --Fecha       Usuario		Comentario
  --09/05/2008  DUBILLUZ  Creación
  FUNCTION IMP_GET_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR,
                            cLoginUsu_in     IN CHAR)
  RETURN VARCHAR2;

  FUNCTION IMP_PROCESA_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR,
                                cLoginUsu_in     IN CHAR,
                                --Se agrega cod_cia para obtener la ruta de la imagen
                                --Autor Luigy Terrazos    Fecha 04/03/2013
                                cCodCia_in in char
                							 )
  RETURN VARCHAR2;

  FUNCTION IMP_CONTIENE_CONSEJOS_PED(cCodGrupoCia_in 	IN CHAR,
                                     cCodLocal_in    	IN CHAR,
                        						 cNumPedVta_in   	IN CHAR)
  RETURN CHAR;

    FUNCTION IMP_CREA_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR)
    RETURN CHAR;

  --Descripcion: Obtiene nombre del cliente
  FUNCTION IMP_GET_MAX_PROD_X_PED
  RETURN INTEGER;

  --Descripcion: Obtiene nombre del cliente
  FUNCTION IMP_GET_MAX_CONS_X_PROD
  RETURN INTEGER;

  --Descripcion: Obtiene nombre del cliente
  FUNCTION IMP_GET_IND_IMP_CUPON(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR)
  RETURN VARCHAR2;

  FUNCTION IMP_GET_NAME_IMP_CONSEJO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN VARCHAR2 ;

FUNCTION IMP_GET_NAME_IMP_STICKER(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN VARCHAR2;
END;

/
