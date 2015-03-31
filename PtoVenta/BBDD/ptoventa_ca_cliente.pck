CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CA_CLIENTE" AS

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
  --07.08.2014  ERIOS       Se agregan parametros de local
  FUNCTION CA_F_CUR_DATOS_EXISTE_DNI(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,cDNI_in IN CHAR)
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

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CA_CLIENTE" AS

  /******************************************************************************/
  /**
  *metodo encargado de listas las campanias activas
  *author : jcallo
  *fecha  : 15.12.2008
  **/
  FUNCTION CA_F_CUR_LISTA_CAMPANIAS( cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCodProd_in     IN CHAR)
  RETURN FarmaCursor
  IS
    curCampAcumulado FarmaCursor;

    nNumDia CHAR(1);

  BEGIN

    nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);

    OPEN curCampAcumulado FOR
    --QUERY DE DUBILLUZ--SEGUN EL RECONTRA OPTIMO
    SELECT DISTINCT C.DESC_CUPON                  || 'Ã' ||--0+1 = 1--por el flag del check
           TO_CHAR(C.FECH_INICIO,'DD/MM/YYYY')    || 'Ã' ||--1+1 = 2
           TO_CHAR(C.FECH_FIN,'DD/MM/YYYY')       || 'Ã' ||--2+1 = 3
           C.COD_CAMP_CUPON                       || 'Ã' ||--3+1 = 4
           Replace(C.CA_MENSAJE_CAMP,'<br>',' ')||chr(10)||chr(13)||chr(10)||chr(13)||'VIGENCIA: '||TO_CHAR(C.FECH_INICIO,'DD/MM/YYYY')||' AL '||TO_CHAR(C.FECH_FIN,'DD/MM/YYYY')||
           chr(10)||chr(13)||'RESTRICCIONES: MAXIMO '||TRIM(TO_CHAR(NVL(C.CA_MAX_CANJE,0),'999999999'))||DECODE(C.CA_MAX_CANJE,1,' PREMIO',' PREMIOS')||
           DECODE(NVL(C.CA_NUM_CANJE_X_PER,0),1, ' por ',' por cada ')||
           TRIM( DECODE( NVL(C.CA_NUM_CANJE_X_PER,0) ,
                           1, ' ',
                           TO_CHAR(NVL(C.CA_NUM_CANJE_X_PER,0),'999999999')
                       )
               )||' '||DECODE(CA_NUM_CANJE_X_PER,1,DECODE(C.CA_PER_MAX_CANJE,'S','SEMANA','M','MES'),DECODE(C.CA_PER_MAX_CANJE,'S','SEMANAS','M','MESES')) || 'Ã' ||
           NVL(C.CA_NUM_CANJE_X_PER,0)            || 'Ã' ||--5 +1 = 6
           NVL(C.VALOR_CUPON,0.00)                || 'Ã' ||--6 +1 = 7
           NVL(C.CA_PER_MAX_CANJE,' ')                     --7 +1 = 8
    FROM   VTA_CAMPANA_CUPON  C
    WHERE  TRUNC(SYSDATE) BETWEEN C.FECH_INICIO AND  C.FECH_FIN
           AND    C.TIP_CAMPANA = 'A'--TIPO CAMPAÑA DE ACUMULACION
           AND    C.ESTADO      = 'A'--ESTADO ACTIVO
           AND    C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    EXISTS (
                          SELECT 1 FROM VTA_CAMPANA_PROD VCP
                          WHERE  C.COD_GRUPO_CIA  = VCP.COD_GRUPO_CIA
                          AND    C.COD_CAMP_CUPON = VCP.COD_CAMP_CUPON
                          AND    ( cCodProd_in = 'T' OR VCP.COD_PROD = cCodProd_in)
                          )--cCodProd_in = 'T' pàra mostrar todos

           AND    C.COD_CAMP_CUPON IN (
                                     /* SELECT *
                                      FROM   (
                                              SELECT *
                                              FROM
                                                  (
                                                  SELECT COD_CAMP_CUPON
                                                  FROM   VTA_CAMPANA_CUPON
                                                  WHERE    TIP_CAMPANA='A'
                                                  MINUS
                                                  SELECT CL.COD_CAMP_CUPON
                                                  FROM   VTA_CAMP_X_LOCAL CL
                                                  )
                                              UNION
                                              SELECT COD_CAMP_CUPON
                                              FROM   VTA_CAMP_X_LOCAL
                                              WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND    COD_LOCAL = cCodLocal_in
                                              AND    ESTADO = 'A')*/
                                     --JCORTEZ 19.10.09 cambio de logica
                                      SELECT *
                                        FROM   (
                                               SELECT X.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON X
                                               WHERE X.COD_GRUPO_CIA='001'
                                               AND X.TIP_CAMPANA='A'
                                               AND X.ESTADO='A'
                                               AND X.IND_CADENA='S'
                                               UNION
                                               SELECT Y.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON Y
                                               WHERE Y.COD_GRUPO_CIA='001'
                                               AND Y.TIP_CAMPANA='A'
                                               AND Y.ESTADO='A'
                                               AND Y.IND_CADENA='N'
                                               AND Y.COD_CAMP_CUPON IN (SELECT COD_CAMP_CUPON
                                                                        FROM   VTA_CAMP_X_LOCAL Z
                                                                        WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                                                        AND    Z.COD_LOCAL = cCodLocal_in
                                                                        AND    Z.ESTADO = 'A')

                                               )
                                      )
           AND  C.COD_CAMP_CUPON IN (
                                    SELECT *
                                    FROM
                                        (
                                          SELECT *
                                          FROM
                                          (
                                            SELECT COD_CAMP_CUPON
                                            FROM   VTA_CAMPANA_CUPON
                                            MINUS
                                            SELECT H.COD_CAMP_CUPON
                                            FROM   VTA_CAMP_HORA H
                                          )
                                          UNION
                                          SELECT H.COD_CAMP_CUPON
                                          FROM   VTA_CAMP_HORA H
                                          WHERE  TRIM(TO_CHAR(SYSDATE,'HH24')) BETWEEN H.HORA_INICIO  AND H.HORA_FIN
                                        ))
           AND  DECODE(C.DIA_SEMANA,NULL,'S',
                        DECODE(C.DIA_SEMANA,REGEXP_REPLACE(C.DIA_SEMANA,nNumDia,'S'),'N','S')
                        ) = 'S'; --FIN DEL QUERY DE DUBILLUZ DE LA CAMPAÑAS ACTIVAS




       /* SELECT CMP.NUM_REG                                                        || 'Ã' ||
               CMP.DNI_CLI                                                        || 'Ã' ||
               CLI.NOM_CLI|| ' ' ||CLI.APE_PAT_CLI|| ' ' ||CLI.APE_MAT_CLI        || 'Ã' ||
               CMP.MAX_SISTOLICA                                                  || 'Ã' ||
               CMP.MIN_DIASTOLICA                                                 || 'Ã' ||
               TO_CHAR(CMP.FEC_CREACION,'dd/MM/yyyy HH24:MI')                  || 'Ã' ||
               NVL(CLI.SEXO_CLI,'N')                                              || 'Ã' ||
               NVL( TO_CHAR(CLI.FEC_NAC_CLI , 'dd/MM/yyyy' ),' ')

               'aaa'|| 'Ã' ||
               'bbb'|| 'Ã' ||
               'ccc'|| 'Ã' ||
               'ddd'|| 'Ã' ||
               'eee'|| 'Ã' ||
               'fff'|| 'Ã' ||
               'ggg'|| 'Ã' ||
               TO_CHAR(sysdate , 'dd/MM/yyyy' )

    FROM DUAL;*/
    --PBL_CLI_MED_PRESION CMP, PBL_CLIENTE CLI
    /*WHERE
          CMP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND  CMP.COD_LOCAL = cCodLocal_in
     AND  CMP.DNI_CLI = CLI.DNI_CLI
     AND  CMP.EST_REG = 'A'
     AND  CLI.IND_ESTADO = 'A'
     AND  CMP.FEC_CREACION BETWEEN TO_DATE(cFecIni_in||' 00:00:00','dd/MM/yyyy HH24:MI:SS')  AND TO_DATE(cFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS');*/

    RETURN curCampAcumulado;
  END;

  /**
  *metodo encargado de listas las campanias activas
  *author : jcallo
  *fecha  : 15.12.2008
  **/
  FUNCTION CA_F_CUR_LISTA_DATOS_CLIENTE
  RETURN FarmaCursor  IS

    curCamp FarmaCursor;
  BEGIN

    OPEN curCamp FOR
         SELECT NOM_CAMPO|| 'Ã' ||
                 '***'     || 'Ã' ||
                 CF.COD_CAMPO|| 'Ã' ||
                 IND_TIP_DATO|| 'Ã' ||
                 IND_SOLO_LECTURA || 'Ã' ||
                 IND_OBLIGATORIO || 'Ã' ||
                 0
          FROM   FID_CAMPOS_FORMULARIO CF,
                 FID_CAMPOS_FIDELIZACION CO
          WHERE  CF.COD_CAMPO = CO.COD_CAMPO
          AND    CO.IND_MOD = CC_MOD_CA_CLI
          order by CO.SEC_CAMPO asc;
    RETURN curCamp;
  END CA_F_CUR_LISTA_DATOS_CLIENTE;



  /* **** */




  FUNCTION CA_F_CUR_DATOS_EXISTE_DNI(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,cDNI_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCamp FarmaCursor;
    vDNI varchar2(20);
    nCant number;
  BEGIN

	ptoventa_fid_reniec.p_genera_tarjeta_dni(cCodGrupoCia_in,
                                             cCodLocal_in,
                                             trim(cDNI_in));

    OPEN curCamp FOR

        SELECT
          A.DNI_CLI                                    || 'Ã' ||
          nvl(A.NOM_CLI,'N')                           || 'Ã' ||
          nvl(A.APE_PAT_CLI,'N')                       || 'Ã' ||
          nvl(A.APE_MAT_CLI,'N')                       || 'Ã' ||
          nvl(A.SEXO_CLI,'N')                          || 'Ã' ||
          nvl(TO_CHAR(A.FEC_NAC_CLI,'dd/MM/yyyy'),'N') || 'Ã' ||
          nvl(A.DIR_CLI,'N')                           || 'Ã' ||
          nvl(''||A.FONO_CLI,'N')                      || 'Ã' ||
          nvl(''||A.CELL_CLI,'N')                      || 'Ã' ||
          nvl(''||A.Email,'N')
        FROM PBL_CLIENTE A
        WHERE A.DNI_CLI = cDNI_in;
    --end if;

    RETURN curCamp;

  END CA_F_CUR_DATOS_EXISTE_DNI;

  /***********************************/
  --CAMPOS DATO DEL CLIENTE
  FUNCTION CA_F_CUR_CAMPOS_CLIENTE
    RETURN FarmaCursor
    IS
    curLista FarmaCursor;
    BEGIN

      OPEN curLista FOR
      SELECT C.COD_CAMPO
      FROM   FID_CAMPOS_FIDELIZACION C
      WHERE  C.IND_MOD = CC_MOD_CA_CLI
      ORDER BY C.SEC_CAMPO;

      RETURN curLista;
    END;

  /******************************************************************/

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
                                 pIndEstado  IN CHAR)
   AS

    --PRAGMA AUTONOMOUS_TRANSACTION;

    vCount NUMBER;
          vCantCampo1 number;
          vCantCampo2 number;
  BEGIN

    SELECT COUNT(1)
      INTO vCount
      FROM PBL_CLIENTE
     WHERE DNI_CLI = vDni_cli
       AND IND_ESTADO = 'A';

    IF trim(vDni_cli) is not NULL THEN

      --dbms_output.put_line('vCount ' || vCount);

      IF (vCount = 0) THEN

        INSERT INTO PBL_CLIENTE
          (DNI_CLI,
           NOM_CLI,
           APE_PAT_CLI,
           APE_MAT_CLI,
           SEXO_CLI,
           FEC_NAC_CLI,
           DIR_CLI,
           FONO_CLI,
           CELL_CLI,
           EMAIL,
           FEC_CREA_CLIENTE,
           USU_CREA_CLIENTE,
           FEC_MOD_CLIENTE,
           USU_MOD_CLIENTE,
           IND_ESTADO)
        VALUES
          (vDni_cli,
           DECODE(vNom_cli, 'N', NULL, null, null, vNom_cli),
           DECODE(vApat_cli, 'N', NULL, null, null, vApat_cli),
           DECODE(vAmat_cli, 'N', NULL, null, null, vAmat_cli),
           DECODE(vSexo_cli, 'N', NULL, null, null, vSexo_cli),
           DECODE(vFecNac_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_DATE(vFecNac_cli, 'DD/MM/YYYY')),
           DECODE(vDir_cli, 'N', NULL, null, null, vDir_cli),
           decode(vFono_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_NUMBER(vFono_cli, '9999999999')),
           decode(vCell_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_NUMBER(vCell_cli, '9999999999')),
           DECODE(vEmail_cli, 'N', NULL, null, null, vEmail_cli),
           SYSDATE,
           pUser,
           NULL,
           NULL,
           pIndEstado);
      ELSE

        --solo si se  falta campos.

        -- se obtiene el numero de campos ingresados por el cliente
        vCantCampo1 := CA_F_NUM_CAMPOS_CLIENTE(vDni_cli);

        SELECT COUNT(*) INTO vCantCampo2
        FROM FID_CAMPOS_FIDELIZACION
        WHERE IND_MOD = CC_MOD_CA_CLI;

        IF(vCantCampo2 >= vCantCampo1)THEN

        UPDATE PBL_CLIENTE L
           SET NOM_CLI = DECODE(vNom_cli, 'N', NULL, vNom_cli),
               APE_PAT_CLI = DECODE(vApat_cli, 'N', NULL, vApat_cli),
               APE_MAT_CLI = DECODE(vAmat_cli, 'N', NULL, vAmat_cli),
               SEXO_CLI = DECODE(vSexo_cli, 'N', NULL, vSexo_cli),
               FEC_NAC_CLI = DECODE(vFecNac_cli,'N',NULL,TO_DATE(vFecNac_cli, 'DD/MM/YYYY')),
               DIR_CLI = DECODE(vDir_cli, 'N', NULL, vDir_cli),
               FONO_CLI = decode(vFono_cli,'N',NULL,TO_NUMBER(vFono_cli, '9999999999')),
               CELL_CLI = decode(vCell_cli,'N',NULL,TO_NUMBER(vCell_cli, '9999999999')),
               EMAIL = DECODE(vEmail_cli, 'N', NULL, vEmail_cli),
               USU_MOD_CLIENTE = pUser,
               FEC_MOD_CLIENTE = SYSDATE
         WHERE L.DNI_CLI = vDni_cli;
        end if;
      END IF;


    END IF;

  EXCEPTION

    WHEN OTHERS THEN

      --dbms_output.put_line('error');
      RAISE_APPLICATION_ERROR(-20007,'ERROR AL REGISTRAR DATOS DEL CLIENTE:'||SQLERRM);


      --rollback;

  END;


  /* ***************************************************************** */
  FUNCTION CA_F_NUM_CAMPOS_CLIENTE(cDni   IN CHAR)
  RETURN number
  is
   cCant number;
  begin
      select decode(DNI_CLI, null, 0, 1) + decode(NOM_CLI, null, 0, 1) +
             decode(APE_PAT_CLI, null, 0, 1) + decode(APE_MAT_CLI, null, 0, 1) +
             decode(SEXO_CLI, null,0, 1) + decode(FEC_NAC_CLI, null, 0, 1)+
             decode(DIR_CLI, null, 0, 1) + decode(FONO_CLI, null, 0, 1) +
             decode(CELL_CLI, null, 0, 1) +decode(l.email, null, 0, 1)
      into cCant
      from pbl_cliente l
      where l.dni_cli = trim(cDni);
       --AND l.ind_estado = 'A';

       return cCant;
  end;


  --numero de las tarjetas asociada a cada cliente.
  -- jcallo    17.12.2008
  FUNCTION CA_F_CUR_TARJETAS_CLI( cDniCliente_in IN CHAR )
  RETURN FarmaCursor  IS
         curFarma FarmaCursor;
  BEGIN

    OPEN curFarma FOR
         SELECT FT.COD_TARJETA
         FROM   FID_TARJETA FT
         WHERE  FT.DNI_CLI = cDniCliente_in;
    RETURN curFarma;
  END CA_F_CUR_TARJETAS_CLI;


  PROCEDURE CA_P_UPD_TARJETA_CLIENTE( cTarjeta_in    IN CHAR,
                                      cDni_cli_in    IN CHAR,
                                      cCodLocal_in   IN CHAR,
                                      cUsuario_in    IN CHAR)
   AS

  BEGIN

       UPDATE FID_TARJETA
       SET DNI_CLI         = cDni_cli_in,
           USU_MOD_TARJETA = cUsuario_in,
           FEC_MOD_TARJETA = SYSDATE,
           COD_LOCAL       = cCodLocal_in
       WHERE COD_TARJETA   = cTarjeta_in
       --cambio para no actualizar tarjetas que NO SEA NECESARIO
       --30.10.2009 dubilluz
       and   nvl(dni_cli,' ') != nvl(cDni_cli_in,' ');

  EXCEPTION

    WHEN OTHERS THEN

      --dbms_output.put_line('error');
      RAISE_APPLICATION_ERROR(-20007,'ERROR AL AFILIAR CLIENTE CON TARJETA DE FIDELIZACION:'||SQLERRM);


      --rollback;

  END;


  PROCEDURE CA_P_INSERT_CLI_CAMPANIA( cCodGrupoCia_in IN CHAR,
                                      cCodCampCupon_in  IN CHAR,
                                      cDni_cli_in       IN CHAR,
                                      cUsuario_in       IN CHAR)
  AS
  nExisteReg   number:=0;
  BEGIN

       SELECT count(*) into nExisteReg
       FROM CA_CLI_CAMP
       WHERE DNI_CLI = cDni_cli_in
       AND   COD_GRUPO_CIA = cCodGrupoCia_in
       AND   COD_CAMP_CUPON = cCodCampCupon_in;

       IF nExisteReg = 0 THEN -- quiere decir que no esta aun el cliente asociado a la campaña

         INSERT INTO CA_CLI_CAMP (COD_GRUPO_CIA,
                                  COD_CAMP_CUPON,
                                  DNI_CLI,
                                  ESTADO,
                                  USU_CREA_CA_CLI_CAMP,
                                  FEC_CREA_CA_CLI_CAMP)
         VALUES (cCodGrupoCia_in,
                 cCodCampCupon_in,
                 cDni_cli_in,
                 'A',
                 cUsuario_in,
                 SYSDATE);

       END IF;

  EXCEPTION

    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20015,'ERROR AL AFILIAR CLIENTE CON CAMPAÑA DE ACUMULACION DE VENTAS:'||SQLERRM);
  END;

  --CAMPANIAS DE ACUMULACION POR CLIENTE
  --Fecha       Usuario		Comentario
  --18.12.2008  jcallo    Creacion
  FUNCTION CA_F_CUR_CAMP_CLIENTE( cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cDni_cli_in     IN CHAR)
  RETURN FarmaCursor IS

     curFarma FarmaCursor;


     nNumDia CHAR(1);

  BEGIN

      nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);

      OPEN curFarma FOR
      SELECT DISTINCT C.DESC_CUPON                  || 'Ã' ||--0+1 = 1--por el flag del check
           TO_CHAR(C.FECH_INICIO,'DD/MM/YYYY')    || 'Ã' ||--1+1 = 2
           TO_CHAR(C.FECH_FIN,'DD/MM/YYYY')       || 'Ã' ||--2+1 = 3
           C.COD_CAMP_CUPON                       || 'Ã' ||--3+1 = 4
           C.CA_MENSAJE_CAMP||chr(10)||chr(13)||chr(10)||chr(13)||'VIGENCIA: '||TO_CHAR(C.FECH_INICIO,'DD/MM/YYYY')||' AL '||TO_CHAR(C.FECH_FIN,'DD/MM/YYYY')||
           chr(10)||chr(13)||'RESTRICCIONES: MAXIMO '||TRIM(TO_CHAR(NVL(C.CA_MAX_CANJE,0),'99999'))||DECODE(C.CA_MAX_CANJE,1,' PREMIO',' PREMIOS')||
           DECODE(NVL(C.CA_NUM_CANJE_X_PER,0),1, ' por ',' por cada ')||
           TRIM( DECODE( NVL(C.CA_NUM_CANJE_X_PER,0) ,
                           1, ' ',
                           TO_CHAR(NVL(C.CA_NUM_CANJE_X_PER,0),'999999999')
                       )
               )||' '|| DECODE(CA_NUM_CANJE_X_PER,1,DECODE(C.CA_PER_MAX_CANJE,'S','SEMANA','M','MES'),DECODE(C.CA_PER_MAX_CANJE,'S','SEMANAS','M','MESES')) || 'Ã' ||
           NVL(C.CA_NUM_CANJE_X_PER,0)            || 'Ã' ||--5 +1 = 6
           NVL(C.VALOR_CUPON,0.00)                || 'Ã' ||--6 +1 = 7
           NVL(C.CA_PER_MAX_CANJE,' ')                     --7 +1 = 8
      FROM   VTA_CAMPANA_CUPON  C, CA_CLI_CAMP CCC
      WHERE  TRUNC(SYSDATE) BETWEEN C.FECH_INICIO AND  C.FECH_FIN
           AND    C.TIP_CAMPANA = 'A'--TIPO CAMPAÑA DE ACUMULACION
--           AND    C.ESTADO      = 'A'--ESTADO ACTIVO
           AND    C.COD_GRUPO_CIA = cCodGrupoCia_in

           AND    CCC.DNI_CLI      = cDni_cli_in
           AND    C.COD_GRUPO_CIA  = CCC.COD_GRUPO_CIA
           AND    C.COD_CAMP_CUPON = CCC.COD_CAMP_CUPON

           AND    C.COD_CAMP_CUPON IN (
                                     /* SELECT *
                                      FROM   (
                                              SELECT *
                                              FROM
                                                  (
                                                  SELECT COD_CAMP_CUPON
                                                  FROM   VTA_CAMPANA_CUPON
                                                  WHERE    TIP_CAMPANA='A'
                                                  MINUS
                                                  SELECT CL.COD_CAMP_CUPON
                                                  FROM   VTA_CAMP_X_LOCAL CL
                                                  )
                                              UNION
                                              SELECT COD_CAMP_CUPON
                                              FROM   VTA_CAMP_X_LOCAL
                                              WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND    COD_LOCAL = cCodLocal_in
                                              AND    ESTADO = 'A')*/
                                     --JCORTEZ 19.10.09 cambio de logica
                                      SELECT *
                                        FROM   (
                                               SELECT X.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON X
                                               WHERE X.COD_GRUPO_CIA='001'
                                               AND X.TIP_CAMPANA='A'
                                               AND X.ESTADO='A'
                                               AND X.IND_CADENA='S'
                                               UNION
                                               SELECT Y.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON Y
                                               WHERE Y.COD_GRUPO_CIA='001'
                                               AND Y.TIP_CAMPANA='A'
                                               AND Y.ESTADO='A'
                                               AND Y.IND_CADENA='N'
                                               AND Y.COD_CAMP_CUPON IN (SELECT COD_CAMP_CUPON
                                                                        FROM   VTA_CAMP_X_LOCAL Z
                                                                        WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                                                        AND    Z.COD_LOCAL = cCodLocal_in
                                                                        AND    Z.ESTADO = 'A')

                                               )
                                      )
           AND  C.COD_CAMP_CUPON IN (
                                    SELECT *
                                    FROM
                                        (
                                          SELECT *
                                          FROM
                                          (
                                            SELECT COD_CAMP_CUPON
                                            FROM   VTA_CAMPANA_CUPON
                                            MINUS
                                            SELECT H.COD_CAMP_CUPON
                                            FROM   VTA_CAMP_HORA H
                                          )
                                          UNION
                                          SELECT H.COD_CAMP_CUPON
                                          FROM   VTA_CAMP_HORA H
                                          WHERE  TRIM(TO_CHAR(SYSDATE,'HH24')) BETWEEN H.HORA_INICIO  AND H.HORA_FIN
                                        ))
           AND  DECODE(C.DIA_SEMANA,NULL,'S',
                        DECODE(C.DIA_SEMANA,REGEXP_REPLACE(C.DIA_SEMANA,nNumDia,'S'),'N','S')
                        ) = 'S';


       /*SELECT TRIM(COD_CAMP_CUPON)
       FROM CA_CLI_CAMP
       WHERE DNI_CLI = cDni_cli_in
       AND   COD_GRUPO_CIA = cCodGrupoCia_in;*/

       RETURN curFarma;
  END;


  --Obtiene DNI si se trata de un pedido que que acumula ventas
  --la ultima validacion que pidio diego
  FUNCTION CA_F_CHAR_GET_DNI_IMPRIMIR (
                                  cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR
                                 )
                                 RETURN char
  is
    cDniPed VARCHAR2(20);
    nCant  number:=0;
  begin

      BEGIN
      SELECT NVL(C.DNI_CLI,'')
      INTO   cDniPed
      FROM   VTA_PEDIDO_VTA_CAB C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL     = cCodLocal_in
      AND    C.NUM_PED_VTA   =  cNumPedVta_in
      AND    C.IND_CAMP_ACUMULADA  = 'S'
      AND    C.DNI_CLI IS NOT NULL;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      cDniPed:= ' ';
      END;

      /**validacion que pidio diego*/
      select nvl(count(1),0) into nCant
      from   vta_pedido_vta_det d,
             ca_cli_camp ca,
             vta_campana_cupon cc,
             vta_campana_prod  cp
      where  d.cod_grupo_cia = cCodGrupoCia_in
      and    d.cod_local  = cCodLocal_in
      and    d.num_ped_vta = cNumPedVta_in--'0000339711'
      and    ca.dni_cli = cDniPed
      and    cc.tip_campana = 'A'
      and    cc.estado = 'A'
      and    ca.cod_grupo_cia = cc.cod_grupo_cia
      and    ca.cod_camp_cupon = cc.cod_camp_cupon
      and    cc.cod_grupo_cia = cp.cod_grupo_cia
      and    cc.cod_camp_cupon = cp.cod_camp_cupon
      and    cp.cod_grupo_cia = d.cod_grupo_cia
      and    (cp.cod_prod = d.cod_prod or cc.ca_cod_prod = d.cod_prod);

      if nCant = 0 then
         cDniPed:= ' ';
      end if;

   return cDniPed;
  end;


  FUNCTION CA_F_VAR_IMP_CAB_HTML(cIpServ_in        IN CHAR,
                                 cDniCliente_in    IN CHAR,
								 cCodGrupoCia_in IN CHAR,
								 cCodCia_in IN CHAR,
								 cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
    msgHTML VARCHAR2(3000):='';
    vCliente VARCHAR2(300):='';

    vFila_IMG_Cabecera_MF varchar2(800):= '';
    v_vCabecera2 VARCHAR2(500);
  BEGIN

       SELECT CLI.NOM_CLI||' '||CLI.APE_PAT_CLI||' '||CLI.APE_MAT_CLI into vCliente
       FROM PBL_CLIENTE CLI
       WHERE CLI.DNI_CLI = cDniCliente_in;

    v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);
       vFila_IMG_Cabecera_MF:= '<tr> <td>'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="300" height="90"></td>'||
                         '</tr> ';

       msgHTML:= C_INICIO_MSG ||
                 '<tr><td align="center" class="titulo">'||
		             'ACUMULA TUS COMPRAS Y GANA !</td></tr> '||vFila_IMG_Cabecera_MF||
                 '<tr><td class="cliente"><strong>DNI :</strong>&nbsp;&nbsp;'||TRIM(cDniCliente_in)||'<BR>'||
                 '<strong>CLIENTE :</strong>&nbsp;&nbsp;'||TRIM(vCliente)||'</td></tr>';
       return msgHTML;
  END;

  /* ******************************************************************** */



  FUNCTION CA_F_VAR_MSG_IMP
  RETURN VARCHAR2
  IS
  vResultado PBL_TAB_GRAL.DESC_LARGA%TYPE := ' ';
  BEGIN
      BEGIN
      SELECT TRIM(T.DESC_LARGA)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'MSG_MED_PRESION'
      AND    T.ID_TAB_GRAL = 235;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := ' ';
      END;

   RETURN vResultado;
  END;


  FUNCTION CA_F_VAR_MAX_ITEMS_IMP
  RETURN VARCHAR2
  IS
  vResultado PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE := ' ';
  BEGIN
      BEGIN
      SELECT TRIM(T.LLAVE_TAB_GRAL)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'MAX_ITEM_IMP_MP'
      AND    T.ID_TAB_GRAL = 236;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := ' ';
      END;

   RETURN vResultado;
  END;

  FUNCTION CA_F_VAR_MAX_MIN_VAL_MP
  RETURN VARCHAR2
  IS
  vResultado PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE := ' ';
  BEGIN
      BEGIN
      SELECT TRIM(T.LLAVE_TAB_GRAL)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'MAX_MIN_MED_PRESION'
      AND    T.ID_TAB_GRAL = 237;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := ' ';
      END;

   RETURN vResultado;
  END;
  /* ********************************************************* */
  FUNCTION CA_F_CUR_CAMP_PREMIO(cDNI_in         IN CHAR,
                                cCodGrupoCia_in IN CHAR,
                                cNumPed_in      IN CHAR,
                                cCodLocal_in    IN CHAR
                               )
  RETURN FarmaCursor IS

     curFarma FarmaCursor;

  BEGIN
      BEGIN
      OPEN curFarma FOR

        SELECT CAMP.COD_CAMP_CUPON
        FROM   CA_CANJ_CLI_PED CA, VTA_CAMPANA_CUPON CAMP
        WHERE  CA.COD_GRUPO_CIA  = CAMP.COD_GRUPO_CIA
        AND    CA.COD_CAMP_CUPON = CAMP.COD_CAMP_CUPON
        AND    CA.DNI_CLI        = cDNI_in
        AND    CA.COD_GRUPO_CIA  = cCodGrupoCia_in
        AND    CA.NUM_PED_VTA    = cNumPed_in
        AND    CA.COD_LOCAL      = cCodLocal_in;

      EXCEPTION
      WHEN NO_DATA_FOUND THEN
           DBMS_OUTPUT.put_line('ERROR:'||SQLERRM);
      END;

   RETURN curFarma;
  END;

  -- Cantidad de unidades acumuladas por pedido
  -- y/o promoion
  -- jcallo    22.12.2008
  FUNCTION CA_F_CUR_CAMP_SUM_LOCAL_PED(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cDni_in         IN CHAR,
                                       cNumPed_in      IN CHAR)
  RETURN FarmaCursor IS


    curFarma  FarmaCursor;

  BEGIN
      OPEN curFarma FOR
      SELECT CH.COD_CAMP_CUPON             || 'Ã' ||--0
             CH.VAL_FRAC_MIN               || 'Ã' ||--1
             SUM(CH.CANT_RESTANTE)         || 'Ã' ||--2
             CAMP.CA_CANT_CANJE            || 'Ã' ||--3
             CAMP.CA_VAL_FRAC_CANJE        || 'Ã' ||--4
             CAMP.CA_MENSAJE_CAMP          || 'Ã' ||--5DECODE(C.CA_MAX_CANJE,1,' PREMIO',' PREMIOS')
             CAMP.CA_UNIDAD_CANJ           || 'Ã' ||--6
             '<b>'||CAMP.CA_MENSAJE_CAMP||'.</b><br><b>Vigencia:</b> '||TO_CHAR(CAMP.FECH_INICIO,'DD/MM/YYYY')||' al '||TO_CHAR(CAMP.FECH_FIN,'DD/MM/YYYY')||
             '.<br><b>Restricciones:&nbsp; </b> M&aacute;ximo '||TRIM(TO_CHAR(NVL(CAMP.CA_MAX_CANJE,0),'99999'))||DECODE(CAMP.CA_MAX_CANJE,1,' premio',' premios')||
             DECODE(NVL(CAMP.CA_NUM_CANJE_X_PER,0),1, ' por ',' por cada ')||
             TRIM( DECODE( NVL(CAMP.CA_NUM_CANJE_X_PER,0) ,
                           1, ' ',
                           TO_CHAR(NVL(CAMP.CA_NUM_CANJE_X_PER,0),'999999999')
                       )
               )||' '||DECODE(CAMP.CA_NUM_CANJE_X_PER,1,DECODE(CAMP.CA_PER_MAX_CANJE,'S','semana','M','mes'),DECODE(CAMP.CA_PER_MAX_CANJE,'S','semanas','M','meses'))
                                        || 'Ã' ||--7
             to_char( ( ( (CAMP.Ca_Cant_Canje/CAMP.Ca_Val_Frac_Canje)*CH.Val_Frac_Min )/CH.Val_Frac_Min )*CAMP.Ca_Val_Frac_Canje,'999')
                                        || 'Ã' ||--8
             to_char( ( SUM(CH.CANT_RESTANTE)/CH.Val_Frac_Min )*CAMP.Ca_Val_Frac_Canje,'999')
                                        || 'Ã' ||--9
             to_char( ( ( (CAMP.Ca_Cant_Canje/CAMP.Ca_Val_Frac_Canje)*CH.Val_Frac_Min - SUM(CH.CANT_RESTANTE) )/CH.Val_Frac_Min )*CAMP.Ca_Val_Frac_Canje,'999')
                                        --10
      FROM CA_CLI_CAMP CCC,  VTA_CAMPANA_CUPON CAMP, CA_HIS_CLI_PED CH
      WHERE CCC.COD_GRUPO_CIA = cCodGrupoCia_in
         AND CCC.ESTADO        = 'A'
         AND CCC.DNI_CLI       = cDni_in
         AND CAMP.TIP_CAMPANA  = 'A'
         AND CAMP.ESTADO       = 'A'

         AND CCC.DNI_CLI        = CH.DNI_CLI
         AND CCC.COD_GRUPO_CIA  = CH.COD_GRUPO_CIA
         AND CCC.COD_CAMP_CUPON = CH.COD_CAMP_CUPON
         AND CH.NUM_PED_VTA     = cNumPed_in
         AND CH.ESTADO = 'A'
         AND CCC.COD_GRUPO_CIA  = CAMP.COD_GRUPO_CIA
         AND CCC.COD_CAMP_CUPON = CAMP.COD_CAMP_CUPON
         AND CCC.COD_CAMP_CUPON IN (
                                     /*   SELECT *
                                        FROM   (
                                                SELECT *
                                                FROM
                                                    (
                                                    SELECT COD_CAMP_CUPON
                                                    FROM   VTA_CAMPANA_CUPON
                                                    WHERE    TIP_CAMPANA='A'
                                                    MINUS
                                                    SELECT CL.COD_CAMP_CUPON
                                                    FROM   VTA_CAMP_X_LOCAL CL
                                                    )
                                                UNION
                                                SELECT COD_CAMP_CUPON
                                                FROM   VTA_CAMP_X_LOCAL
                                                WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                                AND    COD_LOCAL = cCodLocal_in
                                                AND    ESTADO = 'A')*/
                                    --JCORTEZ 19.10.09 cambio de logica
                                      SELECT *
                                        FROM   (
                                               SELECT X.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON X
                                               WHERE X.COD_GRUPO_CIA='001'
                                               AND X.TIP_CAMPANA='A'
                                               AND X.ESTADO='A'
                                               AND X.IND_CADENA='S'
                                               UNION
                                               SELECT Y.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON Y
                                               WHERE Y.COD_GRUPO_CIA='001'
                                               AND Y.TIP_CAMPANA='A'
                                               AND Y.ESTADO='A'
                                               AND Y.IND_CADENA='N'
                                               AND Y.COD_CAMP_CUPON IN (SELECT COD_CAMP_CUPON
                                                                        FROM   VTA_CAMP_X_LOCAL Z
                                                                        WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                                                        AND    Z.COD_LOCAL = cCodLocal_in
                                                                        AND    Z.ESTADO = 'A')

                                               )

                                    )
         GROUP BY CH.COD_CAMP_CUPON           ,
           CH.VAL_FRAC_MIN              ,
           CAMP.CA_CANT_CANJE           ,
           CAMP.CA_VAL_FRAC_CANJE       ,
           CAMP.CA_MENSAJE_CAMP         ,
           CAMP.CA_UNIDAD_CANJ   ,
           CAMP.FECH_INICIO,
           CAMP.FECH_FIN,
           CAMP.CA_MAX_CANJE,
           CAMP.CA_NUM_CANJE_X_PER,
           CAMP.CA_PER_MAX_CANJE;

    RETURN curFarma;

  END;

  FUNCTION CA_F_VAR_GET_PIE_HTML(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR,
                						     cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2 IS
  vResultado varchar2(23000):= '';
  vCod_trab varchar2(100):= '';
  vFecha    varchar2(100):= '';
  vlocal    varchar2(100):= '';
  BEGIN
      BEGIN
          SELECT --NVL(/*U.COD_TRAB_RRHH*/u.sec_usu_local,U.COD_TRAB),
                 trim(u.sec_usu_local),
                 --TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY HH24:MI:SS'),
                 trim(TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY')),
                 L.COD_LOCAL --|| '-'||L.DESC_ABREV
          INTO   vCod_trab,vFecha,vlocal
          FROM   VTA_PEDIDO_VTA_CAB C,
                 CE_MOV_CAJA M       ,
                 PBL_USU_LOCAL U,
                 PBL_LOCAL L
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL = cCodLocal_in
          AND    C.NUM_PED_VTA = cNumPedVta_in
          AND    C.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND    C.COD_LOCAL = L.COD_LOCAL
          AND    C.COD_GRUPO_CIA = M.COD_GRUPO_CIA
          AND    C.COD_LOCAL = M.COD_LOCAL
          AND    C.SEC_MOV_CAJA = M.SEC_MOV_CAJA
          AND    M.COD_GRUPO_CIA = U.COD_GRUPO_CIA
          AND    M.COD_LOCAL = U.COD_LOCAL
          AND    M.SEC_USU_LOCAL = U.SEC_USU_LOCAL;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
       vCod_trab:='';
       vFecha:= '';
       vlocal := '';
      END;

      vResultado := '<table width="310" border="0">
                      <tr><td height="38" align="center" class="style3 style9">'|| vCod_trab ||'</td>
                          <td align="center" class="style3 style9">'||vFecha||'</td>
                          <td align="center" class="style3 style9">'||cNumPedVta_in||'</td>
                          <td align="center" class="style3 style9">'||vlocal||'</td>
                      </tr>
                    </table>';
   RETURN vResultado;

  END;

PROCEDURE CA_P_ACUMULA_UNIDADES (
                                   cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cNumPed_in       IN CHAR,
                                   cDni_in          IN CHAR,
                                   cUsuCrea_in      IN CHAR
                                   )
  AS
    CURSOR curProdPedAcumulados(cNumDia_in IN NUMBER) IS
      SELECT CAMP.COD_CAMP_CUPON,DET.COD_PROD,DET.CANT_ATENDIDA,DET.VAL_FRAC,CAMP.CA_UNID_MIN,DET.SEC_PED_VTA_DET
      FROM   CA_CLI_CAMP C,
             VTA_CAMPANA_CUPON  CAMP,
             VTA_CAMPANA_PROD   CPROD,
             VTA_PEDIDO_VTA_DET DET,
             (

                    SELECT VR.COD_PROD,MIN(VC.COD_CAMP_CUPON) COD_CAMP
                    FROM   (
                          SELECT V.COD_PROD,MIN(V.CANT) CANT
                          FROM   (
                                  SELECT CP.COD_CAMP_CUPON,D.COD_PROD,COUNT(CP1.COD_PROD) CANT
                                  FROM   CA_CLI_CAMP CLI,
                                         VTA_CAMPANA_PROD CP,
                                         VTA_CAMPANA_PROD CP1,
                                         VTA_PEDIDO_VTA_DET D
                                  WHERE  CLI.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                                  AND    CLI.COD_CAMP_CUPON = CP.COD_CAMP_CUPON
                                  AND    CP.COD_GRUPO_CIA  = CP1.COD_GRUPO_CIA
                                  AND    CP.COD_CAMP_CUPON = CP1.COD_CAMP_CUPON
                                  AND    CP.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                  AND    CP.COD_PROD = D.COD_PROD
                                  AND    CLI.DNI_CLI = cDni_in
                                  AND    D.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND    D.COD_LOCAL = cCodLocal_in
                                  AND    D.NUM_PED_VTA = cNumPed_in
                                  GROUP BY CP.COD_CAMP_CUPON,D.COD_PROD
                                 ) V
                          GROUP BY V.COD_PROD
                          )VR,
                          (
                                  SELECT CP.COD_CAMP_CUPON,COUNT(DISTINCT CP1.COD_PROD) CANT
                                  FROM   CA_CLI_CAMP CLI,
                                         VTA_CAMPANA_PROD CP,
                                         VTA_CAMPANA_PROD CP1,
                                         VTA_PEDIDO_VTA_DET D
                                  WHERE  CLI.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                                  AND    CLI.COD_CAMP_CUPON = CP.COD_CAMP_CUPON
                                  AND    CP.COD_GRUPO_CIA  = CP1.COD_GRUPO_CIA
                                  AND    CP.COD_CAMP_CUPON = CP1.COD_CAMP_CUPON
                                  AND    CP.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                  AND    CP.COD_PROD = D.COD_PROD
                                  AND    CLI.DNI_CLI = cDni_in
                                  AND    D.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND    D.COD_LOCAL = cCodLocal_in
                                  AND    D.NUM_PED_VTA = cNumPed_in
                                  AND    CP.COD_CAMP_CUPON IN
                                                             (
                                                             SELECT W.COD_CAMP_CUPON
                                                             FROM   VTA_CAMPANA_CUPON W
                                                             WHERE  W.TIP_CAMPANA = 'A'
                                                             AND    W.ESTADO = 'A'
                                                             AND    TRUNC(SYSDATE) BETWEEN W.FECH_INICIO AND  W.FECH_FIN
                                                             )
                                  GROUP BY CP.COD_CAMP_CUPON
                           ) VC

                    WHERE VC.CANT = VR.CANT
                    AND   EXISTS    (SELECT 1
                                     FROM  VTA_CAMPANA_PROD T
                                     WHERE T.COD_CAMP_CUPON = VC.COD_CAMP_CUPON
                                     AND   T.COD_PROD = VR.COD_PROD)
                    GROUP BY VR.COD_PROD

             )VCAMP
      WHERE  C.DNI_CLI = cDni_in
      AND    C.COD_GRUPO_CIA = CAMP.COD_GRUPO_CIA
      AND    C.COD_CAMP_CUPON = CAMP.COD_CAMP_CUPON
      AND    C.ESTADO = 'A'
      AND    CAMP.ESTADO = 'A'
      AND    CAMP.TIP_CAMPANA = C_TIP_CAMP_ACUMULADAS---CAMPAÑAS DEL TIPO A SON CAMPAÑAS ACUMULADAS
      AND    CPROD.COD_CAMP_CUPON = VCAMP.COD_CAMP
      AND    CPROD.COD_PROD = VCAMP.COD_PROD
      AND    TRUNC(SYSDATE) BETWEEN CAMP.FECH_INICIO AND  CAMP.FECH_FIN
      AND    CAMP.COD_CAMP_CUPON IN (
                                  /*SELECT *
                                  FROM   (
                                          SELECT *
                                          FROM
                                              (
                                              SELECT T.COD_CAMP_CUPON
                                              FROM   VTA_CAMPANA_CUPON T
                                              WHERE  T.TIP_CAMPANA = C_TIP_CAMP_ACUMULADAS
                                              MINUS
                                              SELECT CL.COD_CAMP_CUPON
                                              FROM   VTA_CAMP_X_LOCAL CL
                                              )
                                          UNION
                                          SELECT COD_CAMP_CUPON
                                          FROM   VTA_CAMP_X_LOCAL
                                          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                                          AND    COD_LOCAL = cCodLocal_in
                                          AND    ESTADO = 'A')*/
                                     --JCORTEZ 19.10.09 cambio de logica
                                      SELECT *
                                        FROM   (
                                               SELECT X.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON X
                                               WHERE X.COD_GRUPO_CIA='001'
                                               AND X.TIP_CAMPANA=C_TIP_CAMP_ACUMULADAS
                                               AND X.ESTADO='A'
                                               AND X.IND_CADENA='S'
                                               UNION
                                               SELECT Y.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON Y
                                               WHERE Y.COD_GRUPO_CIA='001'
                                               AND Y.TIP_CAMPANA=C_TIP_CAMP_ACUMULADAS
                                               AND Y.ESTADO='A'
                                               AND Y.IND_CADENA='N'
                                               AND Y.COD_CAMP_CUPON IN (SELECT COD_CAMP_CUPON
                                                                        FROM   VTA_CAMP_X_LOCAL Z
                                                                        WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                                                        AND    Z.COD_LOCAL = cCodLocal_in
                                                                        AND    Z.ESTADO = 'A')

                                               )
                                   )
      AND  CAMP.COD_CAMP_CUPON IN (
                                SELECT *
                                FROM
                                    (
                                      SELECT *
                                      FROM
                                      (
                                        SELECT T.COD_CAMP_CUPON
                                        FROM   VTA_CAMPANA_CUPON T
                                        WHERE  T.TIP_CAMPANA = C_TIP_CAMP_ACUMULADAS
                                        MINUS
                                        SELECT H.COD_CAMP_CUPON
                                        FROM   VTA_CAMP_HORA H
                                      )
                                      UNION
                                      SELECT H.COD_CAMP_CUPON
                                      FROM   VTA_CAMP_HORA H
                                      WHERE  TRIM(TO_CHAR(SYSDATE,'HH24')) BETWEEN H.HORA_INICIO  AND H.HORA_FIN
                                    )
                                )
      AND  DECODE(CAMP.DIA_SEMANA,NULL,'S',
                    DECODE(CAMP.DIA_SEMANA,REGEXP_REPLACE(CAMP.DIA_SEMANA,cNumDia_in,'S'),'N','S')
                    ) = 'S'
      AND  CAMP.COD_GRUPO_CIA = CPROD.COD_GRUPO_CIA
      AND  CAMP.COD_CAMP_CUPON = CPROD.COD_CAMP_CUPON
      AND  DET.COD_GRUPO_CIA = CAMP.COD_GRUPO_CIA
      AND  DET.COD_LOCAL = cCodLocal_in
      AND  DET.NUM_PED_VTA = cNumPed_in
      AND  DET.COD_GRUPO_CIA = CPROD.COD_GRUPO_CIA
      AND  DET.COD_PROD = CPROD.COD_PROD;

      --Dato de producto
      nValFracMax       number;
      --Cantidad de unidades minima de compra del pedido
      nCantUndMinCompra number;
      nNumDia    VARCHAR2(2);
      nVecesAcum  number;

      nCantAcum number;

  BEGIN
     nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);
        --DBMS_OUTPUT.put_line('nNumDia:'||nNumDia);
    FOR cCampProd IN curProdPedAcumulados(nNumDia)
    LOOP

        DBMS_OUTPUT.put_line('.CAMP:'||cCampProd.COD_CAMP_CUPON);

        SELECT P.VAL_MAX_FRAC
        INTO   nValFracMax
        FROM   LGT_PROD P
        WHERE  P.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    P.COD_PROD = cCampProd.COD_PROD;

        nCantUndMinCompra := (cCampProd.CANT_ATENDIDA/cCampProd.VAL_FRAC)*nValFracMax;
        --CANTIDAD QUE SE VA AGRUPAR PARA VER CUANTAS UNIDADES MINIMAS
        --cCampProd.CA_UNID_MIN
        select trunc(nCantUndMinCompra/cCampProd.CA_UNID_MIN)
        into   nVecesAcum
        from dual;
          -- DBMS_OUTPUT.put_line('nCantUndMinCompra:'||nCantUndMinCompra);
          -- DBMS_OUTPUT.put_line('cCampProd.CA_UNID_MIN:'||cCampProd.CA_UNID_MIN);
        if nVecesAcum > 0 then
           --Est
           DBMS_OUTPUT.put_line('...ACUMULA');
           nCantAcum := nVecesAcum*cCampProd.CA_UNID_MIN;
           CA_P_INSERT_HIS_PED_CLI (
                                   cCodGrupoCia_in,
                                   cCodLocal_in,
                                   cNumPed_in,
                                   cDni_in,
                                   cCampProd.COD_CAMP_CUPON  ,
                                   cCampProd.SEC_PED_VTA_DET,
                                   cCampProd.COD_PROD,
                                   cCampProd.CANT_ATENDIDA,
                                   cCampProd.VAL_FRAC,
                                   C_TIPO_HIST_PEDIDO_PENDIENTE,--cEstado_in IN CHAR,
                                   nCantAcum,
                                   nValFracMax,
                                   cUsuCrea_in
                                   );


        end if;

    END LOOP;
  END;
  /* ************************************************************************************ */
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
                                   )
  is

  dFechaPed date;

  BEGIN
  select c.fec_ped_vta
  into   dFechaPed
  from   vta_pedido_vta_cab c
  where  c.cod_grupo_cia = cCodGrupoCia_in
  and    c.cod_local = cCodLocal_in
  and    c.num_ped_vta = cNumPed_in;

                        INSERT INTO CA_HIS_CLI_PED
                        (DNI_CLI,
                        COD_GRUPO_CIA,
                        COD_CAMP_CUPON,
                        COD_LOCAL,
                        NUM_PED_VTA,
                        FEC_PED_VTA,
                        SEC_PED_VTA,
                        COD_PROD,
                        CANT_ATENDIDA,
                        VAL_FRAC,

                        ESTADO,
                        CANT_RESTANTE,
                        VAL_FRAC_MIN,
                        USU_CREA_CA_HIS_CLI_PED,
                        FEC_CREA_CA_HIS_CLI_PED,
                        IND_PROC_MATRIZ,
                        FEC_PROC_MATRIZ
                        )
                        VALUES
                        (cDni_in,
                        cCodGrupoCia_in,
                        cCodCamp_in,
                        cCodLocal_in,
                        cNumPed_in,
                        dFechaPed ,
                        nSecPedVta_in,
                        cCodProd_in,
                        nCantPedido_in,
                        nValFracPedido_in,

                        cEstado_in,
                        nCantRest_in,
                        nValFracMin_in,
                        cUsuCrea_in,
                        SYSDATE         ,
                        'N',
                        null
                        );
  END;

  /* ************************************************************************************ */
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
                                 )
  IS

  dFechaPed date;

  BEGIN
          select c.fec_ped_vta
          into   dFechaPed
          from   vta_pedido_vta_cab c
          where  c.cod_grupo_cia = cCodGrupoCia_in
          and    c.cod_local     = cCodLocal_in
          and    c.num_ped_vta   = cNumPed_in;

          INSERT INTO CA_CANJ_CLI_PED
          (
            DNI_CLI,
            COD_GRUPO_CIA,
            COD_CAMP_CUPON,
            COD_LOCAL,
            NUM_PED_VTA,
            FEC_PED_VTA,
            SEC_PED_VTA,
            COD_PROD,
            CANT_ATENDIDA,
            VAL_FRAC,
            ESTADO,
            USU_CREA_CA_CANJ_CLI,
            FEC_CREA_CA_CANJ_CLI
           )
          VALUES
          (cDni_in,
           cCodGrupoCia_in,
           cCodCamp_in,
           cCodLocal_in,
           cNumPed_in,
           dFechaPed,
           nSecPedVta_in,
           cCodProd_in,
           nCantPedido_in,
           nValFracPedido_in,
           cEstado_in,
           cUsuCrea_in,
           sysdate
           );

  END;
  /* ************************************************************************************ */
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
                                   )
  IS

  dFechaPedCanj date;
  nSecPedRegaloCanj number;
  cCodProdCanj      char(6);


  BEGIN

  select c.fec_ped_vta,d.sec_ped_vta_det,d.cod_prod
  into   dFechaPedCanj,nSecPedRegaloCanj,cCodProdCanj
  from   vta_pedido_vta_det d,
         vta_pedido_vta_cab c
  where  c.cod_grupo_cia = cCodGrupoCia_in
  and    c.cod_local     = cCodLocalCanj_in
  and    c.num_ped_vta   = cNumPedCanj_in
  and    c.cod_grupo_cia = d.cod_grupo_cia
  and    c.cod_local     = d.cod_local
  and    c.num_ped_vta   = d.num_ped_vta
  and    d.cod_camp_cupon = cCodCamp_in;

  /*
  COD_CAMP_CUPON   || 'Ã' ||
           COD_LOCAL_ORIGEN || 'Ã' ||
           NUM_PED_ORIGEN   || 'Ã' ||
           SEC_PED_ORIGEN   || 'Ã' ||
           COD_PROD_ORIGEN  || 'Ã' ||
           CANT_USO         || 'Ã' ||
           VAL_FRAC_MIN
  */
       INSERT INTO CA_PED_ORIGEN_CANJ
       (
        DNI_CLI,
        COD_GRUPO_CIA,
        COD_CAMP_CUPON,

        COD_LOCAL_CANJ,
        NUM_PED_CANJ,
        FEC_PED_VTA_CANJ,
        SEC_PED_CANJ,
        COD_PROD_CANJ,

        COD_LOCAL_ORIGEN,
        NUM_PED_ORIGEN,
        SEC_PED_ORIGEN,
        COD_PROD_ORIGEN,

        ESTADO,
        CANT_USO,
        VAL_FRAC_MIN,
        USU_CREA_CA_PED_ORIG,
        FEC_CREA_CA_PED_ORIG
       )
       VALUES
       (
        cDni_in,
        cCodGrupoCia_in,
        cCodCamp_in,

        cCodLocalCanj_in,
        cNumPedCanj_in,
        dFechaPedCanj ,
        nSecPedRegaloCanj ,
        cCodProdCanj  ,

        cCodLocalOrigen_in   ,
        cNumPedOrigen_in     ,
        nSecPedVtaOrigen_in  ,
        cCodProdOrigen_in    ,
        'P' ,--estado siempre estara pendiente porque aun no se ha cobrado el pedido
        nCantUsoOrigen_in    ,
        nValFracMinOrigen_in ,
        cUsuCrea_in,
        sysdate
       );
  END;
  /* ************************************************************************************ */
  PROCEDURE CA_P_OPERA_BENEFICIO_CAMPANA(
                                         cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         cNumPed_in       IN CHAR,
                                         cDni_in          IN CHAR,
                                         cUsuCrea_in      IN CHAR
                                        )
 is
 begin
 null;
 end;

 /* ************************************************************************************** */
 FUNCTION CA_F_CUR_UNID_ACUMULADAS(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumPedVta_in   IN CHAR,
                                    cDni_in         IN CHAR
                                    )
   RETURN FarmaCursor
   IS
    cur FarmaCursor;

  BEGIN
  /*
                        cDni_in,
                        cCodGrupoCia_in,
                        cCodCamp_in,
                        cCodLocal_in,
                        cNumPed_in,
                        dFechaPed ,
                        nSecPedVta_in,
                        cCodProd_in,
                        nCantPedido_in,
                        nValFracPedido_in,
                        cEstado_in,
                        --nCantRest_in,
                        nValFracMin_in,
                        cUsuCrea_in,
  */
        OPEN cur FOR
            select  DNI_CLI || 'Ã' ||
                    COD_GRUPO_CIA || 'Ã' ||
                    COD_CAMP_CUPON || 'Ã' ||
                    COD_LOCAL || 'Ã' ||
                    NUM_PED_VTA || 'Ã' ||
                    FEC_PED_VTA || 'Ã' ||
                    SEC_PED_VTA || 'Ã' ||
                    COD_PROD || 'Ã' ||
                    CANT_ATENDIDA || 'Ã' ||
                    VAL_FRAC || 'Ã' ||
                    ESTADO || 'Ã' ||
                    VAL_FRAC_MIN || 'Ã' ||
                    USU_CREA_CA_HIS_CLI_PED || 'Ã' ||
                    HC.CANT_RESTANTE
            from   CA_HIS_CLI_PED HC
            where  HC.DNI_CLI = cDni_in
            AND    HC.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    HC.ESTADO = 'P'
            AND    HC.COD_LOCAL = cCodLocal_in
            AND    HC.NUM_PED_VTA = cNumPedVta_in;

    return cur;

  END ;

  /* ********************************************************************** */
  PROCEDURE CA_P_ADD_PROD_REGALO_CAMP(
                                      cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cNumPed_in       IN CHAR,
                                      cDni_in          IN CHAR,
                                      cCodCamp_in      IN CHAR,
                                      cSecUsu_in       IN CHAR,
                                      cIpPc_in         IN CHAR,
                                      cUsuCrea_in      IN CHAR
                                     )
  is

  nCantItemPed number;

  vRestoCanjeFraccionLocal number;
  nCantidadRegalo           number;
  vCodProdRegalo char(6);
  vFracLocal number;

  nStockProd   number;

 begin

      SELECT MAX(D.SEC_PED_VTA_DET)
      into   nCantItemPed
      FROM   VTA_PEDIDO_VTA_DET D
      WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL     = cCodLocal_in
      AND    D.NUM_PED_VTA   = cNumPed_in;

     SELECT MOD((CA_CANT_PROD * P.VAL_FRAC_LOCAL), CA_VAL_FRAC) CA,
            (CAMP.CA_CANT_PROD / CAMP.CA_VAL_FRAC) * P.VAL_FRAC_LOCAL cCantRegalo, --esta en fraccion del local
            CAMP.CA_COD_PROD,
            P.VAL_FRAC_LOCAL
       INTO vRestoCanjeFraccionLocal, nCantidadRegalo,vCodProdRegalo,vFracLocal
       FROM VTA_CAMPANA_CUPON CAMP, LGT_PROD_LOCAL P, LGT_PROD MP
      WHERE CAMP.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAMP.COD_CAMP_CUPON = cCodCamp_in
        AND P.COD_LOCAL = cCodLocal_in
        AND P.COD_GRUPO_CIA = CAMP.COD_GRUPO_CIA
        AND P.COD_PROD = CAMP.CA_COD_PROD
        AND P.COD_GRUPO_CIA = MP.COD_GRUPO_CIA
        AND P.COD_PROD = MP.COD_PROD;

       if vRestoCanjeFraccionLocal = 0  then

           DBMS_OUTPUT.put_line('cProdRega '||vCodProdRegalo ||' cantidad'|| nCantidadRegalo  || ' fracLocal:' ||vFracLocal);



           SELECT PL.STK_FISICO
           INTO   nStockProd
           FROM   LGT_PROD_LOCAL PL
           WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    PL.COD_LOCAL     = cCodLocal_in
           AND    PL.COD_PROD      = vCodProdRegalo;

           --Solo si hay stock podra dar el regalo

            --DBMS_OUTPUT.put_line('nStockProd:'||nStockProd);
            --DBMS_OUTPUT.put_line('nCantidadRegalo:'||nCantidadRegalo);


           if nStockProd >= nCantidadRegalo then

              nCantItemPed := nCantItemPed + 1;

                   UPDATE LGT_PROD_LOCAL
                   SET    USU_MOD_PROD_LOCAL = cUsuCrea_in,
                          FEC_MOD_PROD_LOCAL = SYSDATE
        	         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        	         AND    COD_LOCAL     = cCodLocal_in
        	         AND    COD_PROD      = vCodProdRegalo;

                     -- insertando los productos regalo que tiene el paquete 2 de la promocion
					 -- 07.01.2015 ERIOS Garantizado por local
                            INSERT INTO VTA_PEDIDO_VTA_DET
                            (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,
                            VAL_PREC_VTA,VAL_PREC_TOTAL,PORC_DCTO_1,PORC_DCTO_2,PORC_DCTO_3,
                            PORC_DCTO_TOTAL,VAL_TOTAL_BONO,VAL_FRAC,
                            SEC_USU_LOCAL,USU_CREA_PED_VTA_DET,VAL_PREC_LISTA,
                            VAL_IGV,UNID_VTA,IND_EXONERADO_IGV,
                            VAL_PREC_PUBLIC,IND_ORIGEN_PROD,
                            VAL_FRAC_LOCAL,
                            CANT_FRAC_LOCAL,
                            COD_CAMP_CUPON,
                            PORC_ZAN       -- 2009-11-09 JOLIVA
                            )
                            SELECT   cCodGrupoCia_in,cCodLocal_in,cNumPed_in,nCantItemPed,
                                     vCodProdRegalo,nCantidadRegalo,
                                     0,0,
                                     PROD_LOCAL.PORC_DCTO_1,
                                     PROD_LOCAL.PORC_DCTO_2,
                                     PROD_LOCAL.PORC_DCTO_3,
                                     PROD_LOCAL.PORC_DCTO_1+ PROD_LOCAL.PORC_DCTO_2 +
                                     PROD_LOCAL.PORC_DCTO_3,
                                     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.VAL_BONO_VIG,(PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL)),
                            			   PROD_LOCAL.VAL_FRAC_LOCAL,
                                     cSecUsu_in,cUsuCrea_in,
                                     0,
                                     IGV.PORC_IGV,prod_local.unid_vta,DECODE(IGV.PORC_IGV,0,'S','N'),
                                     PROD_LOCAL.VAL_PREC_VTA,
                                     '8',-- ORIGEN DE REGALO DE CAMPAÑAS ACUMULADAS
                                     PROD_LOCAL.VAL_FRAC_LOCAL,
                                     nCantidadRegalo,
                                     cCodCamp_in,
                                     PROD_LOCAL.PORC_ZAN
                            FROM     LGT_PROD PROD,
                            	       LGT_PROD_LOCAL PROD_LOCAL,
                                     pbl_igv igv
                            WHERE    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND	     PROD_LOCAL.COD_LOCAL     = cCodLocal_in
                            AND	     PROD_LOCAL.COD_PROD      = vCodProdRegalo
                            AND	     PROD.COD_GRUPO_CIA       = PROD_LOCAL.COD_GRUPO_CIA
                            AND	     PROD.COD_PROD = PROD_LOCAL.COD_PROD
                            AND      PROD.COD_IGV  = IGV.COD_IGV;



                            CA_P_INSERT_CANJ_CLI (cCodGrupoCia_in,cCodLocal_in,
                                   cNumPed_in,
                                   cDni_in,
                                   cCodCamp_in,
                                   nCantItemPed,
                                   vCodProdRegalo,
                                   nCantidadRegalo,
                                   vFracLocal,
                                   'P',--todo estare P pendiente porque aunn no es cobrado el pedido
                                   cUsuCrea_in
                                 );


           end if;


       end if;

 end;

 /* ******************************************************************************** */
  PROCEDURE CA_P_ANALIZA_CANJE (
                                cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cNumPed_in       IN CHAR,
                                cUsuMod_in       IN CHAR,
                                cAccion_in       IN CHAR,
                                cIndQuitaRespaldo_in       IN CHAR
                                )
  is

  nIsPedCampAcumulada number;
  cDni_in varchar2(20);
  cCodEncarte varchar2(20);
  cCodRegalo varchar2(20);

  CURSOR curProdRegalo IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,
                  DET.SEC_PED_VTA_DET
           FROM   VTA_PEDIDO_VTA_DET DET,
                  CA_CANJ_CLI_PED  CANJ
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPed_in
           AND    DET.COD_GRUPO_CIA = CANJ.COD_GRUPO_CIA
           AND    DET.COD_LOCAL = CANJ.COD_LOCAL
           AND    DET.NUM_PED_VTA = CANJ.NUM_PED_VTA
           AND    DET.SEC_PED_VTA_DET = CANJ.SEC_PED_VTA
           ORDER BY DET.CANT_ATENDIDA DESC;

 CURSOR curProdRegaloGeneral IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,
                  DET.SEC_PED_VTA_DET
           FROM   VTA_PEDIDO_VTA_DET DET
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPed_in
           ORDER BY DET.CANT_ATENDIDA DESC;

  begin

  SELECT COUNT(1)
  INTO   nIsPedCampAcumulada
  FROM   VTA_PEDIDO_VTA_CAB C
  WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    C.COD_LOCAL     = cCodLocal_in
  AND    C.NUM_PED_VTA   =  cNumPed_in
  AND    C.IND_CAMP_ACUMULADA  = 'S'
  AND    C.DNI_CLI IS NOT NULL;

  --RAISE_APPLICATION_ERROR(-20018,'nIsPedCampAcumulada:'||nIsPedCampAcumulada);

  IF nIsPedCampAcumulada > 0 then

     cDni_in := CA_F_CHAR_GET_DNI_PED(cCodGrupoCia_in,cCodLocal_in,cNumPed_in);

        if cAccion_in = 'C' then -- Se esta cobrando el pedido
            --ACTUALIZA EL CANJE SI HUBO
            UPDATE CA_CANJ_CLI_PED CANJ
            SET    CANJ.ESTADO = 'A',
                   CANJ.USU_MOD_CA_CANJ_CLI = cUsuMod_in,
                   CANJ.FEC_MOD_CANJ_CLI    = SYSDATE
            WHERE  CANJ.DNI_CLI       = cDni_in
            AND    CANJ.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    CANJ.COD_LOCAL     = cCodLocal_in
            AND    CANJ.NUM_PED_VTA   = cNumPed_in;

            --ACTUALIZA LOS PRODUCTOS ORIGEN
            UPDATE CA_PED_ORIGEN_CANJ PO
            SET    PO.ESTADO = 'A',
                   PO.USU_MOD_CA_PED_ORIG = cUsuMod_in,
                   PO.FEC_MOD_CA_PED_ORIG = SYSDATE
            WHERE  PO.DNI_CLI        = cDni_in
            AND    PO.COD_GRUPO_CIA  = cCodGrupoCia_in
            AND    PO.COD_LOCAL_CANJ = cCodLocal_in
            AND    PO.NUM_PED_CANJ   = cNumPed_in;

            --actualiza acumulaciones para que se operen
            UPDATE CA_HIS_CLI_PED H
            SET    H.ESTADO = 'A',
                   H.USU_MOD_CA_HIS_CLI_PED = cUsuMod_in,
                   H.FEC_MOD_CA_HIS_CLI_PED =SYSDATE
            WHERE  H.DNI_CLI        = cDni_in
            AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
            AND    H.COD_LOCAL      = cCodLocal_in
            AND    H.NUM_PED_VTA    = cNumPed_in;

            --actualiza el uso de algunas de las actualizaciones en el local
            UPDATE CA_HIS_CLI_PED H
            SET    (
                   H.ESTADO,
                   H.CANT_RESTANTE,
                   H.USU_MOD_CA_HIS_CLI_PED,
                   H.FEC_MOD_CA_HIS_CLI_PED
                   ) = (
                        SELECT 'A',H.CANT_RESTANTE - O.CANT_USO,cUsuMod_in,SYSDATE
                        FROM   CA_PED_ORIGEN_CANJ O
                        WHERE  O.DNI_CLI = cDni_in
                        AND    O.COD_GRUPO_CIA  = cCodGrupoCia_in
                        AND    O.COD_LOCAL_CANJ = cCodLocal_in
                        --AND    O.NUM_PED_ORIGEN = cNumPed_in
                        AND    O.Num_Ped_Canj = cNumPed_in
                        AND    O.DNI_CLI = H.DNI_CLI
                        AND    O.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                        AND    O.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                        AND    O.COD_LOCAL_ORIGEN = H.COD_LOCAL
                        AND    O.NUM_PED_ORIGEN = H.NUM_PED_VTA
                        AND    O.SEC_PED_ORIGEN = H.SEC_PED_VTA
                        AND    O.COD_PROD_ORIGEN = H.COD_PROD
                       )

            WHERE  H.DNI_CLI        = cDni_in
            AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
            AND    H.COD_LOCAL      = cCodLocal_in
            --AND    H.NUM_PED_VTA    = cNumPed_in
            AND    EXISTS (
                            SELECT 1
                            FROM   CA_PED_ORIGEN_CANJ W
                            WHERE  W.DNI_CLI = cDni_in
                            AND    W.COD_GRUPO_CIA  = cCodGrupoCia_in
                            AND    W.COD_LOCAL_CANJ = cCodLocal_in
                            --AND    W.NUM_PED_ORIGEN = cNumPed_in
                            AND    W.Num_Ped_Canj = cNumPed_in
                            AND    W.DNI_CLI = H.DNI_CLI
                            AND    W.COD_GRUPO_CIA = H.COD_GRUPO_CIA
                            AND    W.COD_CAMP_CUPON = H.COD_CAMP_CUPON
                            AND    W.COD_LOCAL_ORIGEN = H.COD_LOCAL
                            AND    W.NUM_PED_ORIGEN = H.NUM_PED_VTA
                            AND    W.SEC_PED_ORIGEN = H.SEC_PED_VTA
                            AND    W.COD_PROD_ORIGEN = H.COD_PROD
                          );



        elsif cAccion_in = 'N' then -- Se anula el pedido pedido cobrado anulado

            --quita respaldo de stock si tiene regalo
            if cIndQuitaRespaldo_in = 'S' then



            NULL;

           END IF;

            ---elimina los productos regalo q existan
            delete vta_pedido_vta_det d
            where  d.cod_grupo_cia = cCodGrupoCia_in
            and    d.cod_local = cCodLocal_in
            and    d.num_ped_vta = cNumPed_in
            and    (d.sec_ped_vta_det,d.cod_prod) in (
                                                      select ca.sec_ped_vta,ca.cod_prod
                                                      from   ca_canj_cli_ped ca
                                                      where  ca.dni_cli = cDni_in
                                                      and    ca.cod_grupo_cia = cCodGrupoCia_in
                                                      and    ca.num_ped_vta = cNumPed_in
                                                     );
            delete CA_PED_ORIGEN_CANJ PO
            WHERE  PO.DNI_CLI        = cDni_in
            AND    PO.COD_GRUPO_CIA  = cCodGrupoCia_in
            AND    PO.COD_LOCAL_CANJ = cCodLocal_in
            AND    PO.NUM_PED_CANJ   = cNumPed_in;

            delete CA_CANJ_CLI_PED CANJ
            WHERE  CANJ.DNI_CLI       = cDni_in
            AND    CANJ.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    CANJ.COD_LOCAL     = cCodLocal_in
            AND    CANJ.NUM_PED_VTA   = cNumPed_in;

            delete CA_HIS_CLI_PED H
            WHERE  H.DNI_CLI        = cDni_in
            AND    H.COD_GRUPO_CIA  = cCodGrupoCia_in
            AND    H.COD_LOCAL      = cCodLocal_in
            AND    H.NUM_PED_VTA    = cNumPed_in;



     END IF;

  end if;

  end;
 /* ********************************************************************************* */
  FUNCTION CA_F_CHAR_IS_PEDIDO_CA(
                                  cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR
                                 )
                                 RETURN char
  is
  nIsPedCampAcumulada number;
  begin

  SELECT COUNT(1)
  INTO   nIsPedCampAcumulada
  FROM   VTA_PEDIDO_VTA_CAB C
  WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    C.COD_LOCAL     = cCodLocal_in
  AND    C.NUM_PED_VTA   =  cNumPedVta_in
  AND    C.IND_CAMP_ACUMULADA  = 'S'
  AND    C.DNI_CLI IS NOT NULL;

   if nIsPedCampAcumulada > 0 then
      return 'S';
   end if;


   return 'N';
  end;

 /* ********************************************************************************* */

  FUNCTION CA_F_CHAR_GET_DNI_PED (
                                  cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumPedVta_in   IN CHAR
                                 )
                                 RETURN char
  is
    cDniPed VARCHAR2(20);
  begin

      BEGIN
      SELECT NVL(C.DNI_CLI,'')
      INTO   cDniPed
      FROM   VTA_PEDIDO_VTA_CAB C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL     = cCodLocal_in
      AND    C.NUM_PED_VTA   =  cNumPedVta_in
      AND    C.IND_CAMP_ACUMULADA  = 'S'
      AND    C.DNI_CLI IS NOT NULL;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      cDniPed:= ' ';
      END;


   return cDniPed;
  end;

 /* ******************************************************************************** */

  FUNCTION CA_F_CHAR_EXIST_REGALO (
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumPedVta_in   IN CHAR,
                                   cDni_in         IN CHAR
                                  )
                                   RETURN char
  is

    nCant   number;
    cRespt  char(1) := 'N';

  begin


      SELECT count(1)
      INTO   nCant
      FROM   CA_CANJ_CLI_PED C
      WHERE  C.DNI_CLI        = cDni_in
      AND    C.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    C.COD_LOCAL      = cCodLocal_in
      AND    C.NUM_PED_VTA    = cNumPedVta_in;

      if nCant > 0 then

         cRespt := 'S';

      end if;

      return cRespt;

  end;
 /* ******************************************************************************** */
   PROCEDURE CA_P_UPDATE_DATA_PED_CAB(
                                      cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cDni_in          IN CHAR,
                                      cNumPed_in       IN CHAR
                                     )
   IS

   nHistAcum number;
   nCanjePed number;
   nPedOrig  number;

   nAplicaCamp number;
   BEGIN

        SELECT COUNT(1)
        into   nHistAcum
        FROM   CA_HIS_CLI_PED H
        WHERE  H.DNI_CLI = cDni_in
        AND    H.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    H.COD_LOCAL   = cCodLocal_in
        AND    H.NUM_PED_VTA = cNumPed_in;

        SELECT count(1)
        into   nCanjePed
        FROM   CA_CANJ_CLI_PED CP
        WHERE  CP.DNI_CLI = cDni_in
        AND    CP.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    CP.COD_LOCAL   = cCodLocal_in
        AND    CP.NUM_PED_VTA = cNumPed_in ;

        SELECT count(1)
        into   nPedOrig
        FROM   CA_PED_ORIGEN_CANJ P
        WHERE  P.DNI_CLI        = cDni_in
        AND    P.COD_GRUPO_CIA  = cCodGrupoCia_in
        AND    P.COD_LOCAL_CANJ = cCodLocal_in
        AND    P.NUM_PED_CANJ   = cNumPed_in;


        select count(1)
        into   nAplicaCamp
        from   vta_pedido_vta_det d,
               ca_cli_camp ca,
               vta_campana_cupon cc,
               vta_campana_prod  cp
        where  d.cod_grupo_cia = cCodGrupoCia_in
        and    d.cod_local     = cCodLocal_in
        and    d.num_ped_vta   = cNumPed_in
        and    ca.dni_cli      = cDni_in
        and    cc.tip_campana = 'A'
        and    cc.estado = 'A'
        and    ca.cod_grupo_cia = cc.cod_grupo_cia
        and    ca.cod_camp_cupon = cc.cod_camp_cupon
        and    cc.cod_grupo_cia = cp.cod_grupo_cia
        and    cc.cod_camp_cupon = cp.cod_camp_cupon
        and    cp.cod_grupo_cia = d.cod_grupo_cia
        and    (cp.cod_prod = d.cod_prod or cc.ca_cod_prod = d.cod_prod);


       /*
       dbms_output.put_line('cant:'||nHistAcum);
       dbms_output.put_line('cant:'||nCanjePed);
       dbms_output.put_line('cant:'||nPedOrig);

       RAISE_APPLICATION_ERROR(-20018,'nHistAcum:'||nHistAcum);
       */

       if nAplicaCamp > 0 then

           if (nHistAcum+nCanjePed+nPedOrig) > 0  then

               UPDATE  VTA_PEDIDO_VTA_CAB C
               SET     C.IND_CAMP_ACUMULADA = 'S',
                       C.DNI_CLI = cDni_in,
                       C.IND_FID = 'S'
               WHERE   C.COD_GRUPO_CIA = cCodGrupoCia_in
               AND     C.COD_LOCAL     = cCodLocal_in
               AND     C.NUM_PED_VTA   = cNumPed_in;

           end if;

       end if;

   END;
 /* ******************************************************************************** */
FUNCTION CA_F_CHAR_EXIST_PROD_CAMP (
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cCodProd_in     IN CHAR,
                                   cDni_in IN CHAR
                                  )
                                   RETURN char
  is

    nCant   number;
    cRespt  char(1) := 'N';
    nNumDia CHAR(1);
  begin
         nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);

    --ERIOS 17.11.2014 Cambios de JLUNA
    SELECT count(1)
    INTO   nCant
    FROM   VTA_CAMPANA_CUPON  C
    WHERE  TRUNC(SYSDATE) BETWEEN C.FECH_INICIO AND  C.FECH_FIN
           AND    C.TIP_CAMPANA = 'A'--TIPO CAMPAÑA DE ACUMULACION
           AND    C.ESTADO      = 'A'--ESTADO ACTIVO
           AND    C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    EXISTS (
                          SELECT 1 FROM VTA_CAMPANA_PROD VCP
                          WHERE  C.COD_GRUPO_CIA  = VCP.COD_GRUPO_CIA
                          AND    C.COD_CAMP_CUPON = VCP.COD_CAMP_CUPON
                          AND    ( cCodProd_in = 'T' OR VCP.COD_PROD = cCodProd_in)
                          )--cCodProd_in = 'T' pàra mostrar todos

           AND    EXISTS (                                      
                          SELECT 1
                          FROM VTA_CAMPANA_CUPON X
                           WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
                           AND X.TIP_CAMPANA='A'
                           AND X.ESTADO='A'
                           AND X.IND_CADENA='S'
                           and x.cod_camp_cupon=c.cod_camp_cupon
                           UNION ALL
                           SELECT 1
                           FROM VTA_CAMPANA_CUPON Y
                           WHERE Y.COD_GRUPO_CIA=cCodGrupoCia_in
                           AND Y.TIP_CAMPANA='A'
                           AND Y.ESTADO='A'
                           AND Y.IND_CADENA='N'
                           and y.cod_camp_cupon=c.cod_camp_cupon
                           AND EXISTS (SELECT 1
                                        FROM   VTA_CAMP_X_LOCAL Z
                                        WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                        AND    Z.COD_LOCAL = cCodLocal_in
                                        and z.cod_camp_cupon=Y.COD_CAMP_CUPON
                                        AND    Z.ESTADO = 'A')
                          )
           AND  EXISTS (
                        (
                        SELECT 1
                        FROM   VTA_CAMPANA_CUPON
                        where COD_CAMP_CUPON=c.cod_camp_cupon
                        MINUS
                        SELECT 1
                        FROM   VTA_CAMP_HORA H
                        where h.cod_camp_cupon=c.cod_camp_cupon
                        )
                        UNION ALL
                        SELECT 1
                        FROM   VTA_CAMP_HORA H
                        WHERE  TRIM(TO_CHAR(SYSDATE,'HH24')) BETWEEN H.HORA_INICIO  AND H.HORA_FIN
                        and   h.cod_camp_cupon=c.cod_camp_cupon
                      )
           AND  DECODE(C.DIA_SEMANA,NULL,'S',
                        DECODE(C.DIA_SEMANA,REGEXP_REPLACE(C.DIA_SEMANA,1,'S'),'N','S')
                        ) = 'S';

      if nCant > 0 then

         select count(1)
         into   nCant
         from   ca_cli_camp c,
                vta_campana_cupon ca,
                vta_campana_prod cp
         where  c.dni_cli = cDni_in
         and    ca.estado = 'A'
         and    ca.tip_campana = 'A'
         and    c.cod_grupo_cia = ca.cod_grupo_cia
         and    c.cod_camp_cupon = ca.cod_camp_cupon
         and    ca.cod_grupo_cia = cp.cod_grupo_cia
         and    ca.cod_camp_cupon = cp.cod_camp_cupon
         and    cp.cod_prod  = cCodProd_in;

         if nCant > 0 then
         cRespt := 'E';
         else
         cRespt := 'S';
         end if;






      end if;

      return cRespt;

  end;

  /* ***************************************************************************** */
  FUNCTION CA_F_CUR_CANJ_PEDIDO(
                                cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPed_in      IN CHAR,
                                cDNI_in         IN CHAR
                               )
  RETURN FarmaCursor IS

     curFarma FarmaCursor;

  BEGIN
      BEGIN
      OPEN curFarma FOR

        SELECT COD_CAMP_CUPON  || 'Ã' ||
               to_char(CA.FEC_PED_VTA,'dd/mm/yyyy')  || 'Ã' ||
               SEC_PED_VTA     || 'Ã' ||
               COD_PROD        || 'Ã' ||
               CANT_ATENDIDA   || 'Ã' ||
               VAL_FRAC
        FROM   CA_CANJ_CLI_PED CA
        WHERE  CA.DNI_CLI        = cDNI_in
        AND    CA.COD_GRUPO_CIA  = cCodGrupoCia_in
        AND    CA.NUM_PED_VTA    = cNumPed_in
        AND    CA.COD_LOCAL      = cCodLocal_in;

      EXCEPTION
      WHEN NO_DATA_FOUND THEN
           DBMS_OUTPUT.put_line('ERROR:'||SQLERRM);
      END;

   RETURN curFarma;
  END;

  /* ***************************************************************************** */
  FUNCTION CA_F_CUR_ORIG_PEDIDO(
                                cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPed_in      IN CHAR,
                                cDNI_in         IN CHAR
                               )
  RETURN FarmaCursor IS

     curFarma FarmaCursor;

  BEGIN
      BEGIN
      OPEN curFarma FOR

        SELECT COD_CAMP_CUPON  || 'Ã' ||
               to_char(CA.FEC_PED_VTA_CANJ,'dd/mm/yyyy') || 'Ã' ||
               SEC_PED_CANJ     || 'Ã' ||
               COD_PROD_CANJ    || 'Ã' ||

               COD_LOCAL_ORIGEN  || 'Ã' ||
               NUM_PED_ORIGEN    || 'Ã' ||
               SEC_PED_ORIGEN    || 'Ã' ||
               COD_PROD_ORIGEN   || 'Ã' ||

               CANT_USO          || 'Ã' ||
               VAL_FRAC_MIN

        FROM   CA_PED_ORIGEN_CANJ CA
        WHERE  CA.DNI_CLI        = cDNI_in
        AND    CA.COD_GRUPO_CIA  = cCodGrupoCia_in
        AND    CA.NUM_PED_CANJ   = cNumPed_in
        AND    CA.COD_LOCAL_CANJ = cCodLocal_in;

      EXCEPTION
      WHEN NO_DATA_FOUND THEN
           DBMS_OUTPUT.put_line('ERROR:'||SQLERRM);
      END;

   RETURN curFarma;
  END;

  /* ********************************************************************************* */
  PROCEDURE CA_P_UPDATE_PROCESO_MATRIZ_HIS(
                                cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cNumPed_in      IN CHAR,
                                cDNI_in         IN CHAR,
                                cIndEnviaMatriz IN CHAR
                               )
 IS

 BEGIN

      if cIndEnviaMatriz = 'S' then
         update ca_his_cli_ped h
         set    h.ind_proc_matriz = 'S',
                h.fec_proc_matriz = sysdate
         where  h.dni_cli = cDNI_in
         and    h.cod_grupo_cia = cCodGrupoCia_in
         and    h.cod_local = cCodLocal_in
         and    h.num_ped_vta = cNumPed_in;

      elsif cIndEnviaMatriz = 'N' then
         update ca_his_cli_ped h
         set    h.ind_proc_matriz = 'N',
                h.fec_proc_matriz = null
         where  h.dni_cli = cDNI_in
         and    h.cod_grupo_cia = cCodGrupoCia_in
         and    h.cod_local = cCodLocal_in
         and    h.num_ped_vta = cNumPed_in;

      end if;

 END;


 /*****************************************************************************/

  PROCEDURE CA_P_UPDATE_ELIMINA_REGALO(cCodGrupoCia_in       IN CHAR,
                                    cCodLocal_in          IN CHAR,
                                    cUsuMod_in            IN CHAR,
                                    cNumPed_in            IN CHAR,
                                    cAccion_in            IN CHAR,
                                    cIndQuitaRespaldo_in  IN CHAR)
 IS

   cCodEncarte varchar2(20);
  cCodRegalo varchar2(20);

 CURSOR curProdRegaloGeneral IS
           SELECT DET.COD_PROD,
                  DET.CANT_ATENDIDA,
                  DET.CANT_FRAC_LOCAL,
                  DET.SEC_PED_VTA_DET
           FROM   VTA_PEDIDO_VTA_DET DET
           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    DET.COD_LOCAL = cCodLocal_in
           AND    DET.NUM_PED_VTA = cNumPed_in
           ORDER BY DET.CANT_ATENDIDA DESC;

  CURSOR curProdEncartes IS
    SELECT X.COD_ENCARTE,X.COD_PROD_REGALO
    FROM VTA_ENCARTE X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.ESTADO='A'
    AND TRUNC(SYSDATE) BETWEEN TRUNC(X.FECH_INICIO) AND TRUNC(X.FECH_FIN);

 BEGIN

          IF cAccion_in = 'N' THEN -- Se anula el pedido pedido cobrado anulado
            IF cIndQuitaRespaldo_in = 'S' THEN --quita respaldo de stock si tiene regalo
              FOR v_rCurProd1 IN curProdRegaloGeneral
              LOOP

               FOR v_rCurEncartes IN curProdEncartes
                LOOP
                      --INTO cCodEncarte,cCodRegalo
                      IF(v_rCurEncartes.COD_PROD_REGALO =v_rCurProd1.COD_PROD) THEN

                        UPDATE LGT_PROD_LOCAL
                        SET    USU_MOD_PROD_LOCAL = cUsuMod_in,
                        FEC_MOD_PROD_LOCAL = SYSDATE
                        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                        AND   COD_LOCAL     = cCodLocal_in
                        AND   COD_PROD      = v_rCurProd1.COD_PROD;

                        UPDATE VTA_PEDIDO_VTA_DET
                        SET    USU_MOD_PED_VTA_DET = cUsuMod_in,
                               FEC_MOD_PED_VTA_DET = SYSDATE,
                               EST_PED_VTA_DET = 'N'
                        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                        AND    COD_LOCAL     = cCodLocal_in
                        AND    NUM_PED_VTA   = cNumPed_in
                        AND    SEC_PED_VTA_DET = v_rCurProd1.SEC_PED_VTA_DET;

                       -- COMMIT;
                      END IF;
                 END
                LOOP;

              END
              LOOP;

            END IF;

          END IF;

 END;

END;
/

