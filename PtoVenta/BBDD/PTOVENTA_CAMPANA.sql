--------------------------------------------------------
--  DDL for Package PTOVENTA_CAMPANA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CAMPANA" AS

 TYPE FarmaCursor IS REF CURSOR;
 C_INDICADOR_NO CHAR(1) := 'N';
 C_INDICADOR_SI CHAR(1) := 'S';
 C_ESTADO_ACTIVO CHAR(1) := 'A';
 C_TITULAR CHAR(1) := 'T';

  C_SIZE_CONSEJO   VARCHAR2(200) := '18';
  C_SIZE_MSG_FINAL VARCHAR2(200) := '10';


 /*C_INICIO_MSG  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>
                                  <table width="337" border="0">
                                  <tr>
                                   <td width="8">&nbsp;&nbsp;</td>
                                   <td width="319"><table width="316" height="293" border="0">
                                '; */

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
                                '<td height="13" colspan="3"></td> '||
                                ' </tr> ';

C_FIN_MSG     VARCHAR2(2000) := ' </table></td>
                                      </tr>
                                    </table>
                                    <p><br>
                                      <br>
                                    </p>
                                    </body>
                                    </html> ';


  /*C_FIN_MSG     VARCHAR2(2000) := '
                                 </table></td>
                                 </tr>
                                 </table>
                                 </body>
                                 </html>'; */

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_SEPARADOR
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 01 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MSG_01_CONSEJO
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MSG_02_CONSEJO
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_PIE_PAGINA(cCodGrupoCia_in 	IN CHAR,
                             cCodLocal_in    	IN CHAR,
                						 cNumPedVta_in   	IN CHAR)
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION CAMP_F_VAR_MSJ_CAMPANA(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
            								cNumPedVta_in   	IN VARCHAR2
              						 )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_CUPONES_PEDIDO(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR,
                             	  cNumPedVta_in   	IN CHAR)
 RETURN FARMACURSOR;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 PROCEDURE IMP_UPDATE_IND_IMP(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                   						cNumPedVta_in   IN CHAR,
                              cCodCupon       IN CHAR);


 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in 	IN CHAR,
                                   cCodCapana_in    IN CHAR
                							     )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in IN CHAR,
                                cCodCapana_in   IN CHAR
                							  )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje de la campaña
 --Fecha       Usuario		Comentario
 --15/07/2008  JCORTEZ  Creación
 FUNCTION IMP_GET_MSG_03_CONSEJO(cCodGrupoCia_in 	IN CHAR,
                                 CodCamp_in      IN CHAR)
 RETURN VARCHAR2;

 --Descripcion: obtiene fecha de vigencia del cupon
 --Fecha       Usuario		Comentario
 --30/07/2008  JCORTEZ  Creación
 FUNCTION IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in 	IN CHAR,
                                  CodCamp_in        IN CHAR,
                                  CodCupon_in       IN CHAR)
 RETURN VARCHAR2;

  --Descripcion: Calcula EAN13
  --Fecha       Usuario		Comentario
  --23/05/2008  DUBILLUZ  Creacion
  FUNCTION GENERA_EAN13(vCodigo_in IN VARCHAR2)
  RETURN CHAR;


 FUNCTION IMP_GET_TIME_CAN_READ RETURN VARCHAR2;

  --Descipción: obtiene descuento
 FUNCTION GET_NUM_DSCTO_PROD_USO_CAMP (cCod_Grupo_Cia_in IN CHAR,
                                   cCod_Local_in IN CHAR,
                                   cCod_Camp_Cupon_in IN CHAR,
                                   cCod_Prod IN CHAR)
 RETURN NUMBER;

END;

/
