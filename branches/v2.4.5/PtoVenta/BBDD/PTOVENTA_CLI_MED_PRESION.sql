--------------------------------------------------------
--  DDL for Package PTOVENTA_CLI_MED_PRESION
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CLI_MED_PRESION" AS

  C_C_ESTADO_ACTIVO VTA_CLI_LOCAL.EST_CLI_LOCAL%TYPE :='A';
  C_C_INDICADOR_NO  VTA_CLI_LOCAL.IND_CLI_JUR%TYPE :='N';

  TYPE FarmaCursor IS REF CURSOR;
  CC_MOD_MED_PRE   char(2):='MP';--MEDIDA DE PRESION
  CC_NUM_MED_PRE   PBL_NUMERA.COD_NUMERA%TYPE := '047';

  -- dubilluz 16.05.2012 --
  COL_ET_RENIEC_DNI integer := 1;
  COL_ET_RENIEC_NOMBRE integer := 2;
  COL_ET_RENIEC_APE_PAT integer := 3;
  COL_ET_RENIEC_APE_MAT integer := 4;
  COL_ET_RENIEC_SEXO    integer := 5;
  COL_ET_RENIEC_FN    integer := 6 ;
  -- dubilluz 16.05.2012 --

  /* USADOS PARA IMPRIMIR*/
  C_INICIO_MSG  VARCHAR2(3000) := '<html><head><style type="text/css">'||
                                  '.titulo {font-size: 14;font-weight: bold;font-family: Arial, Helvetica, sans-serif;}'||
                                  '.cliente {font-size: 12;font-family: Arial, Helvetica, sans-serif;} '||
                                  '.histcab {font-size: 12;font-family: Arial, Helvetica, sans-serif; font-weight: bold;}'||
                                  '.historico {font-size: 12;font-family: Arial, Helvetica, sans-serif; }'||
                                  '.msgfinal {font-size: 14;font-style: italicfont-family: Arial, Helvetica, sans-serif;}'||
                                  '.tip {font-size: 9;font-style: italic;font-family: Arial, Helvetica, sans-serif;}'||
                                  '</style>'||
                                  '</head>'||
                                  '<body>'||
                                  '<table width="200" border="0">'||
                                  '<tr>'||
                                  '<td>&nbsp;&nbsp;</td>'||
                                  '<td>'||
                                  '<table width="300"  border="1" cellspacing="0" cellpading="0">';
                                  --height="841"

  C_TIP_MEDIDA_PRESION VARCHAR2(500) := '<table border="0" '||
                                  'width="100%" cellspading="0" cellspacing="0"><tr><td colspan="4">'||
                                  '" La información contenida en este impreso es estrictamente personal '||
                                  'y meramente referencial. Tiene por objetivo ayudar a su Médico en el seguimiento'||
                                  ' de su estado de salud. Mifarma no se responsabiliza por el contenido o'||
                                  ' uso de este documento ".'||
                                  '</td></tr></table>';
  /* FIN DE USADOS PARA IMPRIMIR*/

  --Descripcion: Listado de las medidas de presion registradas en el local
  --Fecha       Usuario		Comentario
  --21/10/2008  JCALLO    Creación
  FUNCTION MP_F_CUR_LIST_REGISTROS( cCodGrupoCia_in IN CHAR,
                                                cCodLocal_in IN CHAR,
                                                cFecIni_in IN CHAR,
                                                cFecFin_in IN CHAR)
  RETURN FarmaCursor;



  FUNCTION MP_F_CUR_HIST_MP(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in   IN CHAR,
                            cDniCliente_in IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Listado de datos de formulario necesarios
  --Fecha       Usuario		Comentario
  --24/10/2008  JCALLO    Creación
  FUNCTION MP_F_CUR_LISTA_DATOS_CLIENTE
  RETURN FarmaCursor;

  FUNCTION MP_F_CUR_DATOS_EXISTE_DNI(cDNI_in IN CHAR)
  RETURN FarmaCursor;

  FUNCTION MP_F_CUR_CAMPOS_CLIENTE
    RETURN FarmaCursor;

  --Descripcion: PROCEDIMIENTO DE INSERSION DE DATOS DE CLIENTE
  --Fecha       Usuario		Comentario
  --24/10/2008  JCALLO    Creación
  PROCEDURE MP_P_INSERT_CLIENTE(vDni_cli IN CHAR,
                                 vNom_cli IN VARCHAR2,
                                 vApat_cli IN VARCHAR2,
                                 vAmat_cli IN VARCHAR2,
                                 vEmail_cli IN VARCHAR2,
                                 vFono_cli IN CHAR,
                                 vSexo_cli IN CHAR,
                                 vDir_cli IN VARCHAR2,
                                 vFecNac_cli IN CHAR,
                                 pCodLocal IN CHAR,
                                 pUser IN CHAR,
                                 pIndEstado IN CHAR,
                                  ---agregadas
                                 cTipDoc    IN CHAR default 'N',
                                 cUserValida IN CHAR default 'N'
                                 );



  FUNCTION MP_F_VAR_INSERT_MED_PRESION(    cCodGrupoCia_in IN CHAR,
            		   			   		        cCodLocal_in	IN CHAR,
                                      cDniCli_in IN CHAR,
                                      nMaxSist_in IN NUMBER,
                                      nMinDiast_in IN NUMBER,
                                      vUsuario_in IN CHAR)
  RETURN VARCHAR2;

  PROCEDURE MP_P_UPDATE_MED_PRESION(  cCodGrupoCia_in IN CHAR,
            		   			   		        cCodLocal_in	IN CHAR,
                                      cNumReg	IN CHAR,
                                      cDniCli_in IN CHAR,
                                      nMaxSist_in IN NUMBER,
                                      nMinDiast_in IN NUMBER,
                                      vUsuario_in IN CHAR);

  PROCEDURE MP_P_INACTIVAR_MED_PRESION(  cCodGrupoCia_in IN CHAR,
            		   			   		        cCodLocal_in	IN CHAR,
                                      cNumReg	IN CHAR,
                                      cDniCli_in IN CHAR,
                                      vUsuario_in IN CHAR);

  --Descripcion: OBTIENE LA CANTIDAD DE DATOS OBLIGATORIOS A REGISTRAR DEL CLIENTE
  --Fecha       Usuario    Comentario
  --24/10/2008  JCALLO    Creación
  FUNCTION MP_F_NUM_CAMPOS_CLIENTE(cDni   IN CHAR)
  RETURN number;

  /*** FUNCIONES PARA IMPRIMIR***/
  FUNCTION MP_F_VAR_IMP_CAB_HIST(cIpServ_in        IN CHAR,
                                 cDniCliente_in    IN CHAR,
                                 cNomCliente_in    IN CHAR,
								 cCodGrupoCia_in IN CHAR,
								 cCodCia_in IN CHAR,
								 cCodLocal_in IN CHAR)
  RETURN VARCHAR2;

  FUNCTION MP_F_VAR_IMP_PIE_HIST
  RETURN VARCHAR2;

  FUNCTION MP_F_VAR_MSG_IMP
  RETURN VARCHAR2;


  FUNCTION MP_F_VAR_MAX_ITEMS_IMP
  RETURN VARCHAR2;
  /** FIN DE FUNCIONES PARA IMPRIMIR***/

  FUNCTION MP_F_VAR_MAX_MIN_VAL_MP
  RETURN VARCHAR2;

  END;

/
