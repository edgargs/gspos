CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CLI_MED_PRESION" AS

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

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CLI_MED_PRESION" AS

  /******************************************************************************/

  FUNCTION MP_F_CUR_LIST_REGISTROS( cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        cFecIni_in IN CHAR,
                                        cFecFin_in IN CHAR)
  RETURN FarmaCursor
  IS
    curMedPressionReg FarmaCursor;
  BEGIN
    OPEN curMedPressionReg FOR
        SELECT CMP.NUM_REG                                                        || 'Ã' ||
               CMP.DNI_CLI                                                        || 'Ã' ||
               CLI.NOM_CLI|| ' ' ||CLI.APE_PAT_CLI|| ' ' ||CLI.APE_MAT_CLI        || 'Ã' ||
               CMP.MAX_SISTOLICA                                                  || 'Ã' ||
               CMP.MIN_DIASTOLICA                                                 || 'Ã' ||
               TO_CHAR(CMP.FEC_CREACION,'dd/MM/yyyy HH24:MI')                     || 'Ã' ||
               NVL(CLI.SEXO_CLI,'N')                                              || 'Ã' ||
               NVL( TO_CHAR(CLI.FEC_NAC_CLI , 'dd/MM/yyyy' ),' ')
    FROM PBL_CLI_MED_PRESION CMP, PBL_CLIENTE CLI
    WHERE
          CMP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND  CMP.COD_LOCAL = cCodLocal_in
     AND  CMP.DNI_CLI = CLI.DNI_CLI
     AND  CMP.EST_REG = 'A'
     AND  CLI.IND_ESTADO = 'A'
     AND  CMP.FEC_CREACION BETWEEN TO_DATE(cFecIni_in||' 00:00:00','dd/MM/yyyy HH24:MI:SS')  AND TO_DATE(cFecFin_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS');

    RETURN curMedPressionReg;
  END;

    /*****/
  FUNCTION MP_F_CUR_HIST_MP(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in   IN CHAR,
                            cDniCliente_in IN CHAR)
  RETURN FarmaCursor  IS

    curFarma FarmaCursor;
  BEGIN

    OPEN curFarma FOR
         SELECT MPC.COD_LOCAL||'-'||L.DESC_ABREV       || 'Ã' ||
                MPC.MAX_SISTOLICA                      || 'Ã' ||
                MPC.MIN_DIASTOLICA                     || 'Ã' ||
                TO_CHAR(MPC.FEC_CREACION,'dd/MM/yyyy HH24:MI')     || 'Ã' ||
                TO_CHAR(MPC.FEC_CREACION,'yyyyMMdd HH24:MI:SS')
          FROM   PBL_CLI_MED_PRESION MPC,
                 PBL_LOCAL L
          WHERE  MPC.DNI_CLI     = cDniCliente_in
          AND    MPC.COD_LOCAL   = cCodLocal_in
          AND    MPC.EST_REG     = 'A'
          AND    L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    L.COD_LOCAL     = MPC.COD_LOCAL;
          --order by MPC.FEC_CREACION desc;
    RETURN curFarma;
  END MP_F_CUR_HIST_MP;

  /* **** */
  FUNCTION MP_F_CUR_LISTA_DATOS_CLIENTE
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
          AND    CO.IND_MOD = CC_MOD_MED_PRE
          order by cf.COD_CAMPO asc;
    RETURN curCamp;
  END MP_F_CUR_LISTA_DATOS_CLIENTE;


   FUNCTION MP_F_CUR_DATOS_EXISTE_DNI(cDNI_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCamp FarmaCursor;
    vDNI varchar2(20);
    nCant number;
    nCant2 number;

        vValidaReniec varchar2(2);
    -- dubilluz 16.05.2012
    vDatosDNI varchar2(30000);
  VAL_DNI varchar2(20);
  VAL_NOMBRE varchar2(10000);
  VAL_APE_PAT varchar2(10000);
  VAL_APE_MAT varchar2(10000);
  VAL_SEXO varchar2(20);
  VAL_FECHA_NAC DATE;
    -- dubilluz  16.05.2012
  BEGIN

    SELECT count(1)
    INTO   nCant
    FROM   PBL_CLIENTE f
    WHERE  TRIM(f.dni_cli) = cDNI_in;

    vValidaReniec := PTOVENTA_FID_RENIEC.F_VAR2_GET_IND_VALIDA_RENIEC;

    if vValidaReniec = 'N' then
      IF nCant = 0 THEN
       OPEN curCamp FOR
          SELECT '$' from dual where 1=2;
      ELSE

       OPEN curCamp FOR
          SELECT
              A.DNI_CLI           || 'Ã ' ||
              nvl(A.APE_PAT_CLI,'N')       || 'Ã' ||
              nvl(A.APE_MAT_CLI,'N')       || 'Ã' ||
              nvl(A.NOM_CLI,'N')           || 'Ã' ||
              nvl(''||A.FEC_NAC_CLI,'N')       || 'Ã' ||
              nvl(A.SEXO_CLI,'N')          || 'Ã' ||
              nvl(A.DIR_CLI,'N')           || 'Ã' ||
              nvl(''||A.FONO_CLI,'N')            || 'Ã' ||
              nvl(''||A.Email,'N')
            FROM PBL_CLIENTE A
            WHERE A.DNI_CLI = cDNI_in;
      end if;
    else

    -- dubilluz 16.05.2012
    /*
    SELECT COUNT(1)
    INTO nCant2
    FROM @PBL_DNI_RED@ A
    WHERE A.LE=cDNI_in;
    */
    vDatosDNI := utility_dni_reniec.aux_datos_existe_dni('001','000',cDNI_in);
    if vDatosDNI = 'N' then
       nCant2 := 0;
    else
      nCant2 := 1;
      VAL_DNI     := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_DNI,'@'));
      VAL_NOMBRE  := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_NOMBRE,'@'));
      VAL_APE_PAT := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_APE_PAT,'@'));
      VAL_APE_MAT := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_APE_MAT,'@'));
      VAL_SEXO    := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_SEXO,'@'));
      VAL_FECHA_NAC := TO_DATE(TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_FN,'@')),'yyyymmdd');
    end if;

    -- dubilluz 16.05.2012

    IF nCant = 0 AND nCant2=0 THEN
       OPEN curCamp FOR
          SELECT '$' from dual where 1=2;
    ELSE

          if nCant > 0 then
              OPEN curCamp FOR
                /*
                SELECT
                    A.DNI_CLI           || 'Ã ' ||
                    nvl(A.APE_PAT_CLI,'N')       || 'Ã' ||
                    nvl(A.APE_MAT_CLI,'N')       || 'Ã' ||
                    nvl(A.NOM_CLI,'N')           || 'Ã' ||
                    nvl(''||A.FEC_NAC_CLI,'N')       || 'Ã' ||
                    nvl(A.SEXO_CLI,'N')          || 'Ã' ||
                    nvl(A.DIR_CLI,'N')           || 'Ã' ||
                    nvl(''||A.FONO_CLI,'N')            || 'Ã' ||
                    nvl(''||A.Email,'N')
                  FROM PBL_CLIENTE A
                  WHERE A.DNI_CLI = cDNI_in*/

                    select nvl(reniec.dni,cliente.dni)  || 'Ã' ||
                           ------------------------------------
                           nvl(cliente.ape_pat,'N') || 'Ã' ||
                           nvl(cliente.ape_mat,'N') || 'Ã' ||
                           --nvl(cliente.nombre,'N') || 'Ã' ||
                           --nvl(cliente.nombre,reniec.nombre) || 'Ã' ||
                           nvl(nvl('&'||cliente.nombre,'@'||reniec.nombre),'N') || 'Ã' ||
                           nvl(reniec.fecha,cliente.fecha)   || 'Ã' ||
                           nvl(cliente.sexo,'N') || 'Ã' ||
                           nvl(cliente.dir,'N')  || 'Ã' ||
                           nvl(cliente.fono||'','N') || 'Ã' ||
                           nvl(cliente.correo,'N')
                    from
                    (
                    select A.DNI_CLI dni,
                           A.NOM_CLI nombre,
                           to_char(A.FEC_NAC_CLI,'dd/mm/yyyy') fecha,

                           A.APE_PAT_CLI ape_pat,
                           A.APE_MAT_CLI ape_mat,
                           A.SEXO_CLI sexo,
                           A.DIR_CLI dir,
                           A.FONO_CLI fono,
                           A.Email correo
                    from   pbl_cliente a
                    where  dni_cli = cDNI_in
                    ) cliente,
                    (
/*                    select r.le dni,
                           r.nombre_completo nombre,
                           to_char(to_date(r.fec_nac,'yyyymmdd'),'dd/mm/yyyy') fecha,

                           null ape_pat,
                           null ape_mat,
                           null sexo,
                           null dir,
                           null fono,
                           null correo
                    from   @pbl_dni_red@ r
                    where  r.le LIKE  cDNI_in || '%'*/
                        SELECT VAL_DNI dni,VAL_NOMBRE nombre,TO_CHAR(VAL_FECHA_NAC,'DD/MM/YYYY') fecha,
                               null ape_pat,
                               null ape_mat,
                               null sexo,
                               null dir,
                               null fono,
                               null correo
                        FROM   DUAL

                    ) reniec
                    where  cliente.dni = reniec.dni(+);

            else
               if nCant2 > 0 then
               OPEN curCamp FOR
                        select nvl(reniec.dni,cliente.dni)  || 'Ã' ||
                               ------------------------------------
                               nvl(cliente.ape_pat,'N') || 'Ã' ||
                               nvl(cliente.ape_mat,'N') || 'Ã' ||
                               --nvl(cliente.nombre,'N') || 'Ã' ||
                               nvl(cliente.nombre,reniec.nombre) || 'Ã' ||
                               nvl(nvl('@'||reniec.nombre,'&'||cliente.nombre),'N') || 'Ã' ||
                               nvl(reniec.fecha,cliente.fecha)   || 'Ã' ||
                               nvl(cliente.sexo,'N') || 'Ã' ||
                               nvl(cliente.dir,'N')  || 'Ã' ||
                               nvl(cliente.fono||'','N') || 'Ã' ||
                               nvl(cliente.correo,'N')
                        from
                        (
                        select A.DNI_CLI dni,
                               A.NOM_CLI nombre,
                               to_char(A.FEC_NAC_CLI,'dd/mm/yyyy') fecha,

                               A.APE_PAT_CLI ape_pat,
                               A.APE_MAT_CLI ape_mat,
                               A.SEXO_CLI sexo,
                               A.DIR_CLI dir,
                               A.FONO_CLI fono,
                               A.Email correo
                        from   pbl_cliente a
                        where  dni_cli = cDNI_in
                        ) cliente,
                        (
/*                        select r.le dni,
                               r.nombre_completo nombre,
                               to_char(to_date(r.fec_nac,'yyyymmdd'),'dd/mm/yyyy') fecha,

                               null ape_pat,
                               null ape_mat,
                               null sexo,
                               null dir,
                               null fono,
                               null correo
                        from   @pbl_dni_red@ r
                        where  r.le LIKE  cDNI_in || '%'*/
                        SELECT VAL_DNI dni,VAL_NOMBRE nombre,TO_CHAR(VAL_FECHA_NAC,'DD/MM/YYYY') fecha,
                               null ape_pat,
                               null ape_mat,
                               null sexo,
                               null dir,
                               null fono,
                               null correo
                        FROM   DUAL
                        ) reniec
                        where  cliente.dni(+) = reniec.dni;
                   end if;

            end if;

          END IF;
    end if;
    RETURN curCamp;


  END MP_F_CUR_DATOS_EXISTE_DNI;
  /***********************************/

  FUNCTION MP_F_CUR_CAMPOS_CLIENTE
    RETURN FarmaCursor
    IS
    curLista FarmaCursor;
    BEGIN
      OPEN curLista FOR
      SELECT C.COD_CAMPO
      FROM   FID_CAMPOS_FIDELIZACION C
      WHERE  C.IND_MOD = CC_MOD_MED_PRE;

      RETURN curLista;
    END;

  /******************************************************************/

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
                                 )

   AS

    --PRAGMA AUTONOMOUS_TRANSACTION;

    vCount NUMBER;
          vCantCampo1 number;
          vCantCampo2 number;
  BEGIN

    SELECT COUNT(1)
      INTO vCount
      FROM PBL_CLIENTE
     WHERE DNI_CLI = vDni_cli;

    IF trim(vDni_cli) is not NULL THEN

      --dbms_output.put_line('vCount ' || vCount);

      IF (vCount = 0) THEN

        INSERT INTO PBL_CLIENTE
          (DNI_CLI,
           NOM_CLI,
           APE_PAT_CLI,
           APE_MAT_CLI,
           EMAIL,
           FONO_CLI,
           SEXO_CLI,
           DIR_CLI,
           FEC_NAC_CLI,
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
           DECODE(vEmail_cli, 'N', NULL, null, null, vEmail_cli),
           decode(vFono_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_NUMBER(vFono_cli, '9999999.000')),
           DECODE(vSexo_cli, 'N', NULL, null, null, vSexo_cli),
           DECODE(vDir_cli, 'N', NULL, null, null, vDir_cli),
           DECODE(vFecNac_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_DATE(vFecNac_cli, 'DD/MM/YYYY')),
           SYSDATE,
           pUser,
           NULL,
           NULL,
           pIndEstado);
      ELSE

        --solo si se  falta campos.

        -- se obtiene el numero de campos ingresados por el cliente
        vCantCampo1 := MP_F_NUM_CAMPOS_CLIENTE(vDni_cli);

        SELECT COUNT(*) INTO vCantCampo2
        FROM FID_CAMPOS_FIDELIZACION
        WHERE IND_MOD = CC_MOD_MED_PRE;

        IF(vCantCampo2 >= vCantCampo1)THEN

        UPDATE PBL_CLIENTE L
           SET NOM_CLI = DECODE(vNom_cli, 'N', NULL, vNom_cli),
               APE_PAT_CLI = DECODE(vApat_cli, 'N', NULL, vApat_cli),
               APE_MAT_CLI = DECODE(vAmat_cli, 'N', NULL, vAmat_cli),
               FONO_CLI = decode(vFono_cli,                                 'N',
                                 NULL,
                                 TO_NUMBER(vFono_cli, '9999999.000')),

               SEXO_CLI = DECODE(vSexo_cli, 'N', NULL, vSexo_cli),
               DIR_CLI = DECODE(vDir_cli, 'N', NULL, vDir_cli),
               FEC_NAC_CLI = DECODE(vFecNac_cli,
                                    'N',
                                    NULL,
                                    TO_DATE(vFecNac_cli, 'DD/MM/YYYY')),
               USU_MOD_CLIENTE = pUser,
               FEC_MOD_CLIENTE = SYSDATE

         WHERE L.DNI_CLI = vDni_cli;
        end if;
      END IF;

       IF cTipDoc != 'N' and cUserValida != 'N' THEN
        --q se valido el DNI
        --y se ingreso usuario y clave
        ---
         UPDATE PBL_CLIENTE X
              SET X.ID_USU_CONFIR=cUserValida                                                                                             ,
                  X.USU_MOD_CLIENTE=cUserValida,
                  X.FEC_MOD_CLIENTE=SYSDATE,
                  x.cod_tip_documento = cTipDoc
              WHERE TRIM(X.DNI_CLI)= vDni_cli;

       END IF;
    END IF;

  EXCEPTION

    WHEN OTHERS THEN

      --dbms_output.put_line('error');
      RAISE_APPLICATION_ERROR(-20007,'ERROR AL REGISTRAR DATOS DEL CLIENTE');


      --rollback;

  END;

  /*** INSERTAR MEDIDA DE PRESION ***/
--  MP_P_INSERT_MED_PRESION

  FUNCTION MP_F_VAR_INSERT_MED_PRESION(    cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in  IN CHAR,
                                      cDniCli_in IN CHAR,
                                      nMaxSist_in IN NUMBER,
                                      nMinDiast_in IN NUMBER,
                                      vUsuario_in IN CHAR)
  RETURN VARCHAR2
  AS
   v_nNumRegistro  NUMBER;
   v_cNumRegistro  CHAR(10);
  BEGIN

        v_nNumRegistro := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in,CC_NUM_MED_PRE);
        --dbms_output.put_line (v_nCodPaquete);
        v_cNumRegistro := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_nNumRegistro),10,'0','I');--completar 0 a la izquierda
        --dbms_output.put_line(Length(v_cCodPaquete));

        INSERT INTO PBL_CLI_MED_PRESION( COD_GRUPO_CIA,
                                         COD_LOCAL,
                                         NUM_REG,
                                         DNI_CLI,
                                         MAX_SISTOLICA,
                                         MIN_DIASTOLICA,
                                         EST_REG,
                                         USU_CREACION,
                                         FEC_CREACION)
                     VALUES             (cCodGrupoCia_in,
                                         cCodLocal_in,
                                         v_cNumRegistro,
                                         cDniCli_in,
                                         nMaxSist_in,
                                         nMinDiast_in,
                                         'A',
                                         vUsuario_in,
                                         SYSDATE);

        /** actualizando secuenciador**/
        Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,CC_NUM_MED_PRE,vUsuario_in);

        return v_cNumRegistro;

  EXCEPTION
    WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20007,'ERROR AL REGISTRAR MEDIDA DE PRESION');
  END;

  /********/

  PROCEDURE MP_P_UPDATE_MED_PRESION(  cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in  IN CHAR,
                                      cNumReg  IN CHAR,
                                      cDniCli_in IN CHAR,
                                      nMaxSist_in IN NUMBER,
                                      nMinDiast_in IN NUMBER,
                                      vUsuario_in IN CHAR)
   AS
  BEGIN

        UPDATE PBL_CLI_MED_PRESION  SET  MAX_SISTOLICA = nMaxSist_in,
                                         MIN_DIASTOLICA = nMinDiast_in,
                                         USU_MODIF = vUsuario_in,
                                         FEC_MODIF = SYSDATE
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND   COD_LOCAL     = cCodLocal_in
        AND   NUM_REG       = cNumReg
        AND   DNI_CLI       = cDniCli_in;

  EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20007,'ERROR AL ACTUALIZAR DATOS DE LA MEDIDA DE PRESION');
  END;

  /********/

  PROCEDURE MP_P_INACTIVAR_MED_PRESION(  cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in  IN CHAR,
                                      cNumReg  IN CHAR,
                                      cDniCli_in IN CHAR,
                                      vUsuario_in IN CHAR)
   AS
  BEGIN

        UPDATE PBL_CLI_MED_PRESION  SET  EST_REG='I',
                                         USU_MODIF = vUsuario_in,
                                         FEC_MODIF = SYSDATE
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND   COD_LOCAL     = cCodLocal_in
        AND   NUM_REG       = cNumReg
        AND   DNI_CLI       = cDniCli_in;

  EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20007,'ERROR AL ELIMINAR MEDIDA DE PRESION');
  END;

  /************************************************************************/

  /*PROCEDURE MP_P_ACTUALIZA_HIST_MP (cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                    cNumReg_in      IN CHAR,
                                    vDni_cli_in     IN CHAR,
                                    nMaxSist_in     IN NUMBER,
                                    nMinDiast_in    IN NUMBER,
                                    cEstado_in      IN CHAR,
                                    vUsuario_in     IN CHAR,
                                    cFecCrea_in     IN CHAR,
                                    )
   AS

  BEGIN

        INSERT INTO PBL_CLIENTE
          (DNI_CLI,
           NOM_CLI,
           APE_PAT_CLI,
           APE_MAT_CLI,
           EMAIL,
           FONO_CLI,
           SEXO_CLI,
           DIR_CLI,
           FEC_NAC_CLI,
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
           DECODE(vEmail_cli, 'N', NULL, null, null, vEmail_cli),
           decode(vFono_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_NUMBER(vFono_cli, '9999999.000')),
           DECODE(vSexo_cli, 'N', NULL, null, null, vSexo_cli),
           DECODE(vDir_cli, 'N', NULL, null, null, vDir_cli),
           DECODE(vFecNac_cli,
                  'N',
                  NULL,
                  null,
                  null,
                  TO_DATE(vFecNac_cli, 'DD/MM/YYYY')),
           SYSDATE,
           pUser,
           NULL,
           NULL,
           pIndEstado);

    END IF;

  EXCEPTION

    WHEN OTHERS THEN

      --dbms_output.put_line('error');
      RAISE_APPLICATION_ERROR(-20007,'ERROR AL REGISTRAR DATOS DEL CLIENTE');


      --rollback;

  END;*/


  /* ***************************************************************** */
  FUNCTION MP_F_NUM_CAMPOS_CLIENTE(cDni   IN CHAR)
  RETURN number
  is
   cCant number;
  begin
      select decode(DNI_CLI, null, 0, 1) + decode(NOM_CLI, null, 0, 1) +
             decode(APE_PAT_CLI, null, 0, 1) + decode(APE_MAT_CLI, null, 0, 1) +
             decode(FONO_CLI, null, 0, 1) + decode(SEXO_CLI, null,0, 1) +
             decode(DIR_CLI, null, 0, 1) + decode(FEC_NAC_CLI, null, 0, 1)+
              decode(l.email, null, 0, 1)
        into cCant
        from pbl_cliente l
       where l.dni_cli = trim(cDni);
       --AND l.ind_estado = 'A';

       return cCant;
  end;

  /* ***************************************************************** */


  FUNCTION MP_F_VAR_IMP_CAB_HIST(cIpServ_in        IN CHAR,
                                 cDniCliente_in    IN CHAR,
                                 cNomCliente_in    IN CHAR,
								 cCodGrupoCia_in IN CHAR,
								 cCodCia_in IN CHAR,
								 cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
    msgHTML VARCHAR2(3000):='';

    vFila_IMG_Cabecera_MF varchar2(800):= '';
    v_vCabecera2 VARCHAR2(500);
  BEGIN

    v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);
       vFila_IMG_Cabecera_MF:= '<tr> <td colspan="4">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="300" height="90"></td>'||
                         '</tr> ';

       msgHTML:= C_INICIO_MSG ||vFila_IMG_Cabecera_MF||
                 '<tr><td colspan="4" align="center" class="titulo">'||
                 'HIST&Oacute;RICO MEDIDA DE PRESI&Oacute;N</td></tr> '||
                 '<tr><td colspan="4" class="cliente"><strong>DNI :</strong>&nbsp;&nbsp;'||TRIM(cDniCliente_in)||'<BR>'||
                 '<strong>CLIENTE :</strong>&nbsp;&nbsp;'||TRIM(cNomCliente_in)||'</td></tr>'||
                 '<tr><td colspan="4"><table width="100%" border="0" cellspacing="0" cellspading="0">'||
                 '<tr class="histcab">'||
                 '<td width="21%">Local</TD>'||
                 '<td width="22%" align="right">Max. Sist&oacute;lica</TD>'||
                 '<td width="22%" align="right">Min. Diast&oacute;lica</TD>'||
                 '<td width="35%">Fecha Registro</TD>'||
                 '</tr>';
       return msgHTML;
  END;

  FUNCTION MP_F_VAR_IMP_PIE_HIST
  RETURN VARCHAR2
  IS
    msgHTML VARCHAR2(1000):='';

    vFila_IMG_Cabecera_MF varchar2(200):= '';
  BEGIN
       msgHTML:= '</table></td></tr>'||
                 '<tr><td colspan="4" align="center" class="msgfinal">'||MP_F_VAR_MSG_IMP||'</td></tr>'||
                 '<tr><td class="tip" colspan="4">'||C_TIP_MEDIDA_PRESION||'</td></tr>'||
                 '</table></td></tr></table><p><br><br></p></body></html>';

       return msgHTML;
  END;





  FUNCTION MP_F_VAR_MSG_IMP
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


  FUNCTION MP_F_VAR_MAX_ITEMS_IMP
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

  FUNCTION MP_F_VAR_MAX_MIN_VAL_MP
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


END;
/

