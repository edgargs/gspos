--------------------------------------------------------
--  DDL for Package PTOVENTA_IMP_CUPON
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_IMP_CUPON" AS

 TYPE FarmaCursor IS REF CURSOR;
 C_INDICADOR_NO CHAR(1) := 'N';
 C_INDICADOR_SI CHAR(1) := 'S';
 C_ESTADO_ACTIVO CHAR(1) := 'A';
 C_TITULAR CHAR(1) := 'T';

 C_INICIO_MSG  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>
                                  <table width="337" border="0">
                                  <tr>
                                   <td width="8">&nbsp;&nbsp;</td>
                                   <td width="319"><table width="316" height="293" border="0">
                                ';


 C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                '<td height="13" colspan="3"></td> '||
                                ' </tr> ';

 C_FIN_MSG     VARCHAR2(2000) := '
                                 </table></td>
                                 </tr>
                                 </table>
                                 </body>
                                 </html>';

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
 FUNCTION IMP_PROCESA_CUPON(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
            								cNumPedVta_in   	IN CHAR,
                            cIpServ_in        IN CHAR,
                            cCodCupon_in      IN CHAR
                            --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                            ,cCodCia_in in char
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

  --Descripcion: Obtiene formato html de cupon regalo
  --Fecha       Usuario		Comentario
  --18.08.09  JCORTEZ  Creacion
  FUNCTION IMP_PROCESA_CUPON_REGALO(cCodGrupoCia_in 	IN CHAR,
                                    cCodLocal_in    	IN CHAR,
                                    cIpServ_in        IN CHAR,
                                    cCodCupon_in      IN CHAR,
                                    cDni_in           IN CHAR)
  RETURN VARCHAR2;


  --Descripcion: Obtiene pie de pagina de cupon regalo
  --Fecha       Usuario		Comentario
  --18.08.09    JCORTEZ   Creacion
  FUNCTION IMP_GET_PIE_PAGINA2(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cDni              IN CHAR)
  RETURN VARCHAR2;
  
  PROCEDURE PROCESA_CUPON_SORTEO(cCodGrupoCia_in 	IN CHAR,
                               cCodLocal_in    	IN CHAR,
                    					 cNumPedVta_in   	IN CHAR);
  
  function  IMP_GET_CUPON_SORTEO(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR,
                      					 cCodCupon_in   	IN CHAR)
     return varchar2;


 FUNCTION IMP_PROCESA_CUPON_1(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
            								cNumPedVta_in   	IN CHAR,
                            cIpServ_in        IN CHAR,
                            cCodCupon_in      IN CHAR
                            --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                            ,cCodCia_in in char
              						 )
 RETURN VARCHAR2;

 FUNCTION IMP_PROCESA_CUPON_2(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
            								cNumPedVta_in   	IN CHAR,
                            cIpServ_in        IN CHAR,
                            cCodCupon_in      IN CHAR
                            --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                            ,cCodCia_in in char
              						 )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_PIE_PAGINA_2(cCodGrupoCia_in 	IN CHAR,
                             cCodLocal_in    	IN CHAR,
                						 cNumPedVta_in   	IN CHAR)
 RETURN VARCHAR2;

END;

/
