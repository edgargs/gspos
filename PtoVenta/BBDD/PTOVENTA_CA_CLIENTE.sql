--------------------------------------------------------
--  DDL for Package PTOVENTA_CA_CLIENTE
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CA_CLIENTE" AS

  C_C_ESTADO_ACTIVO VTA_CLI_LOCAL.EST_CLI_LOCAL%TYPE :='A';
  C_C_INDICADOR_NO  VTA_CLI_LOCAL.IND_CLI_JUR%TYPE :='N';


  C_TIP_CAMP_ACUMULADAS CHAR(1) :='A';

  C_TIPO_HIST_PEDIDO_PENDIENTE CHAR(1) :='P';-- Estado para los pedidos acumuladas que se generaron pero no cobrados


  TYPE FarmaCursor IS REF CURSOR;
  CC_MOD_CA_CLI   char(2):='CA';--CAMPANIA ACUMULACION
  CC_NUM_MED_PRE   PBL_NUMERA.COD_NUMERA%TYPE := '047';

  /* USADOS PARA IMPRIMIR*/
  C_INICIO_MSG  VARCHAR2(3000) := '<html><head><style type="text/css">'||
                                  '.titulo {font-size: 16;font-weight: bold;font-family: sans-serif;font-style: italic;}'||--Arial, Helvetica, sans-serif;}'||
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

  --Descripcion: Listado CAMPANIAS de acumulacion
  --Fecha       Usuario		Comentario
  --15/12/2008  JCALLO    Creación
  FUNCTION CA_F_CUR_LISTA_CAMPANIAS( cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cCodProd_in     IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Listado de datos de formulario necesarios
  --Fecha       Usuario		Comentario
  --15/12/2008  JCALLO    Creación
  FUNCTION CA_F_CUR_LISTA_DATOS_CLIENTE
  RETURN FarmaCursor;

  --Descripcion: Listado de datos de formulario necesarios
  --Fecha       Usuario		Comentario
  --15/12/2008  JCALLO    Creación
  FUNCTION CA_F_CUR_DATOS_EXISTE_DNI(cDNI_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Listado de CAMPOS deL formulario
  --Fecha       Usuario		Comentario
  --15/12/2008  JCALLO    Creación
  FUNCTION CA_F_CUR_CAMPOS_CLIENTE
    RETURN FarmaCursor;

  --Descripcion: PROCEDIMIENTO DE INSERSION DE DATOS DE CLIENTE
  --Fecha       Usuario		Comentario
  --15/12/2008  JCALLO    Creación
  PROCEDURE CA_P_INSERT_CLIENTE( vDni_cli    IN CHAR,
                                 vNom_cli    IN VARCHAR2,
                                 vApat_cli   IN VARCHAR2,
                                 vAmat_cli   IN VARCHAR2,
                                 vSexo_cli   IN CHAR,
                                 vFecNac_cli IN CHAR,
                                 vDir_cli    IN VARCHAR2,
                                 vFono_cli   IN CHAR,
                                 vCell_cli   IN CHAR,
                                 vEmail_cli  IN VARCHAR2,
                                 pCodLocal   IN CHAR,
                                 pUser       IN CHAR,
                                 pIndEstado  IN CHAR);

  --Descripcion: OBTIENE LA CANTIDAD DE DATOS OBLIGATORIOS A REGISTRAR DEL CLIENTE
  --Fecha       Usuario		Comentario
  --16/12/2008  JCALLO    Creación
  FUNCTION CA_F_NUM_CAMPOS_CLIENTE(cDni   IN CHAR)
  RETURN number;


  --numero de las tarjetas asociada a cada cliente.
  --Fecha       Usuario		Comentario
  --17.12.2008  jcallo    Creacion
  FUNCTION CA_F_CUR_TARJETAS_CLI( cDniCliente_in IN CHAR )
  RETURN FarmaCursor;

  --ASGINAR EL NUMERO DNI CON LAS TARJETAS DE FIDELIZACION
  --Fecha       Usuario		Comentario
  --17.12.2008  jcallo    Creacion
  PROCEDURE CA_P_UPD_TARJETA_CLIENTE( cTarjeta_in    IN CHAR,
                                      cDni_cli_in    IN CHAR,
                                      cCodLocal_in   IN CHAR,
                                      cUsuario_in    IN CHAR);

  --ASOCIAR CLIENTE CON CAMPAÑA DE ACUMULACION
  --Fecha       Usuario		Comentario
  --17.12.2008  jcallo    Creacion
  PROCEDURE CA_P_INSERT_CLI_CAMPANIA( cCodGrupoCia_in IN CHAR,
                                      cCodCampCupon_in  IN CHAR,
                                      cDni_cli_in       IN CHAR,
                                      cUsuario_in       IN CHAR);

  --CAMPANIAS DE ACUMULACION POR CLIENTE
  --Fecha       Usuario		Comentario
  --18.12.2008  jcallo    Creacion
  FUNCTION CA_F_CUR_CAMP_CLIENTE( cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cDni_cli_in     IN CHAR)
  RETURN FarmaCursor;

  FUNCTION CA_F_CHAR_GET_DNI_IMPRIMIR (
                                  cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR
                                 )
  RETURN char;

  /*** FUNCIONES PARA IMPRIMIR***/
  FUNCTION CA_F_VAR_IMP_CAB_HTML(cIpServ_in        IN CHAR,
                                 cDniCliente_in    IN CHAR,
								 cCodGrupoCia_in IN CHAR,
								 cCodCia_in IN CHAR,
								 cCodLocal_in IN CHAR)
  RETURN VARCHAR2;

  FUNCTION CA_F_VAR_MSG_IMP
  RETURN VARCHAR2;


  FUNCTION CA_F_VAR_MAX_ITEMS_IMP
  RETURN VARCHAR2;
  /** FIN DE FUNCIONES PARA IMPRIMIR***/

  FUNCTION CA_F_VAR_MAX_MIN_VAL_MP
  RETURN VARCHAR2;


  -- obtiene la cantidad restante de compras de los productos de la campañas para obtener regalo
  -- y/o promoion
  -- jcallo    17.12.2008
  FUNCTION CA_F_CUR_CAMP_PREMIO(cDNI_in         IN CHAR,
                                cCodGrupoCia_in IN CHAR,
                                cNumPed_in      IN CHAR,
                                cCodLocal_in    IN CHAR
                               )
  RETURN FarmaCursor;

  -- Cantidad de unidades acumuladas por pedido
  -- y/o promoion
  -- jcallo    22.12.2008
  FUNCTION CA_F_CUR_CAMP_SUM_LOCAL_PED(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cDni_in         IN CHAR,
                                  cNumPed_in      IN CHAR)
  RETURN FarmaCursor;

  FUNCTION CA_F_VAR_GET_PIE_HTML(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR,
                						     cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2;


  --Descripcion: Acumula unidades de histotirco
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_ACUMULA_UNIDADES (
                                   cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cNumPed_in       IN CHAR,
                                   cDni_in          IN CHAR,
                                   cUsuCrea_in      IN CHAR
                                   );

  --Descripcion: Insert historico
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_INSERT_HIS_PED_CLI (
                                   cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cNumPed_in       IN CHAR,
                                   cDni_in       IN CHAR,
                                   cCodCamp_in   IN CHAR,
                                   nSecPedVta_in IN NUMBER,
                                   cCodProd_in   IN CHAR,
                                   nCantPedido_in   IN NUMBER,
                                   nValFracPedido_in IN NUMBER,
                                   cEstado_in IN CHAR,
                                   nCantRest_in   IN NUMBER,
                                   nValFracMin_in IN NUMBER,
                                   cUsuCrea_in IN CHAR
                                   );

  --Descripcion: Inserta canje
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_INSERT_CANJ_CLI (
                                   cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cNumPed_in        IN CHAR,
                                   cDni_in           IN CHAR,
                                   cCodCamp_in       IN CHAR,
                                   nSecPedVta_in     IN NUMBER,
                                   cCodProd_in       IN CHAR,
                                   nCantPedido_in    IN NUMBER,
                                   nValFracPedido_in IN NUMBER,
                                   cEstado_in        IN CHAR,
                                   cUsuCrea_in       IN CHAR
                                 );

  --Descripcion: Inserta origen
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_INSERT_PED_ORIGEN (
                                     cDni_in           IN CHAR,
                                     cCodGrupoCia_in   IN CHAR,
                                     cCodCamp_in       IN CHAR,
                                     cCodLocalCanj_in  IN CHAR,
                                     cNumPedCanj_in    IN CHAR,
                                     cCodLocalOrigen_in   IN CHAR,
                                     cNumPedOrigen_in     IN CHAR,
                                     nSecPedVtaOrigen_in  IN NUMBER,
                                     cCodProdOrigen_in    IN CHAR,
                                     nCantUsoOrigen_in    IN NUMBER,
                                     nValFracMinOrigen_in    IN NUMBER,
                                     cUsuCrea_in       IN CHAR
                                   );
  --Descripcion: Operacion de beneficio
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_OPERA_BENEFICIO_CAMPANA(
                                         cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumPed_in       IN CHAR,
                                         cDni_in          IN CHAR,
                                         cUsuCrea_in      IN CHAR
                                        );

  --Descripcion: Datos de unidades Acumuladas
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  FUNCTION CA_F_CUR_UNID_ACUMULADAS(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cDni_in         IN CHAR
                                    )
                                    RETURN FarmaCursor;

  --Descripcion: Añade prod Regalo
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_ADD_PROD_REGALO_CAMP(
                                      cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cNumPed_in       IN CHAR,
                                      cDni_in          IN CHAR,
                                      cCodCamp_in      IN CHAR,
                                      cSecUsu_in       IN CHAR,
                                      cIpPc_in         IN CHAR,
                                      cUsuCrea_in      IN CHAR
                                     );

  --Descripcion: Acumula unidades de histotirco
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  --VERIFICA SI SE COBRO O NO EL PEDIDO
  --SI SE COBRO SE CAMBIA EL ESTADO CASO CONTRARIO SE ELIMINAN LOS DATOS
  PROCEDURE CA_P_ANALIZA_CANJE (
                                cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cNumPed_in       IN CHAR,
                                cUsuMod_in       IN CHAR,
                                cAccion_in       IN CHAR ,
                                cIndQuitaRespaldo_in       IN CHAR
                                );

  --Descripcion: SI es un pedido por campaña acumulada
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  FUNCTION CA_F_CHAR_IS_PEDIDO_CA(
                                  cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR
                                 )
                                 RETURN char;

  --Descripcion: Obtiene DNI
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  FUNCTION CA_F_CHAR_GET_DNI_PED(
                                  cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR
                                 )RETURN CHAR;

  --Descripcion: Verifica si tiene DNI
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  FUNCTION CA_F_CHAR_EXIST_REGALO (
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR,
                                   cDni_in         IN CHAR
                                  )
                                   RETURN char;

  --Descripcion: Verifica informacion de Pedido Cabacera
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  PROCEDURE CA_P_UPDATE_DATA_PED_CAB(
                                      cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cDni_in          IN CHAR,
                                      cNumPed_in       IN CHAR

                                     );
  --Descripcion: Existe Prod Campaña
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  FUNCTION CA_F_CHAR_EXIST_PROD_CAMP (
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   cDni_in IN CHAR
                                  )  RETURN CHAR;

  --Descripcion: obtiene los canjes del pedido para enviarlos a matriz
  --Fecha       Usuario		Comentario
  --21/12/2008  DUBILLUZ   Creación
  FUNCTION CA_F_CUR_CANJ_PEDIDO(
                                cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPed_in      IN CHAR,
                                cDNI_in         IN CHAR
                               )
  RETURN FarmaCursor ;


  FUNCTION CA_F_CUR_ORIG_PEDIDO(
                                cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPed_in      IN CHAR,
                                cDNI_in         IN CHAR
                               )
  RETURN FarmaCursor ;


  PROCEDURE CA_P_UPDATE_PROCESO_MATRIZ_HIS(
                                cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPed_in      IN CHAR,
                                cDNI_in         IN CHAR,
                                cIndEnviaMatriz IN CHAR
                               );

  --Descripcion: Se descompromete stock de productos regalo por encarte (optimizar proceso)
  --Fecha       Usuario		Comentario
  --13/08/2008  JCORTEZ   Creación
  PROCEDURE CA_P_UPDATE_ELIMINA_REGALO(cCodGrupoCia_in       IN CHAR,
                                        cCodLocal_in          IN CHAR,
                                        cUsuMod_in            IN CHAR,
                                        cNumPed_in            IN CHAR,
                                        cAccion_in            IN CHAR,
                                        cIndQuitaRespaldo_in  IN CHAR);

	END;

/
