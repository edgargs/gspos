CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_FIDELIZACION" AS

  -- Author  : DVELIZ
  -- Created : 26/09/2008 11:08:41 a.m.
  -- Purpose :

  ESTADO_ACTIVO   CHAR(1) := 'A';
  ESTADO_INACTIVO CHAR(1) := 'I';
  INDICADOR_SI    CHAR(1) := 'S';
  INDICADOR_NO    CHAR(1) := 'N';

  -- DUBILLUZ 16.05.2012
  COL_ET_RENIEC_DNI integer := 1;
  COL_ET_RENIEC_NOMBRE integer := 2;
  COL_ET_RENIEC_APE_PAT integer := 3;
  COL_ET_RENIEC_APE_MAT integer := 4;
  COL_ET_RENIEC_SEXO    integer := 5;
  COL_ET_RENIEC_FN    integer := 6;

  TYPE FarmaCursor IS REF CURSOR;

  CC_MOD_TAR_FID char(2) := 'TF'; --TARJETAS DE FIFELIZACION
  CC_COD_NUMERA  CHAR(3) := '070';
  --Descripcion: RETORNA UN INDICADOR SI LA TARJETA NO TIENE ASIGNADO CLIENTE EN LOCAL
  --Fecha       Usuario   Comentario
  --26/09/2008  DVELIZ    CREACION
  FUNCTION FID_F_VAR_VALIDA_CLIENTE(pCodTarjeta IN CHAR, pCodLocal IN CHAR)
    RETURN VARCHAR2;

  --Descripcion: RETORNA UN INDICADOR SI LA TARJETA EXISTE EN LOCAL
  --Fecha       Usuario   Comentario
  --26/09/2008  DVELIZ    CREACION
  FUNCTION FID_F_VAR_VALIDA_TARJETA(pCodTarjeta IN CHAR, pCodLocal IN CHAR)
    RETURN VARCHAR2;

  --Descripcion: LISTA LOS CAMPOS PARA LA FIDELIZACION
  --Fecha       Usuario   Comentario
  --26/09/2008  DVELIZ    CREACION
  FUNCTION FID_F_CUR_LISTA_FIDELIZACION RETURN FarmaCursor;

  --Descripcion: OBTIENE LOS DATOS DE UN CLIENTE MEDIANTE SU TARJETA
  --Fecha       Usuario   Comentario
  --26/09/2008  DVELIZ    CREACION
  --18/08/2009  JMIRANDA  MODIFICACION
  FUNCTION FID_F_CUR_DATOS_CLIENTE(cGrupocia_in IN CHAR default '001',
                                   pCodLocal    IN CHAR,
                                   pCodTarjeta  IN CHAR) RETURN FarmaCursor;

  --Descripcion: INSERTA LOS DATOS DEL CLIENTE EN EL MAESTRO Y ACTUALIZA LA TARJETA
  --Fecha       Usuario   Comentario
  --26/09/2008  DVELIZ    CREACION
  /*  PROCEDURE FID_P_INSERT_CLIENTE(vDni_cli     IN CHAR,
  pCodTarjeta  IN CHAR,
  pCodLocal    IN CHAR);*/
  PROCEDURE FID_P_INSERT_CLI_LOCAL(vDni_cli    IN CHAR,
                                   vNom_cli    IN VARCHAR2,
                                   vApat_cli   IN VARCHAR2,
                                   vAmat_cli   IN VARCHAR2,
                                   vEmail_cli  IN VARCHAR2,
                                   vFono_cli   IN CHAR,
                                   vSexo_cli   IN CHAR,
                                   vDir_cli    IN VARCHAR2,
                                   vFecNac_cli IN CHAR,
                                   pCodTarjeta IN CHAR,
                                   pCodLocal   IN CHAR,
                                   pUser       IN CHAR,
                                   pIndEstado  IN CHAR,
                                   ---agregadas
                                   cTipDoc     IN CHAR default 'N',
                                   cUserValida IN CHAR default 'N');

  PROCEDURE FID_P_INSERT_CLIENTE(vDni_cli    IN CHAR,
                                 vNom_cli    IN VARCHAR2,
                                 vApat_cli   IN VARCHAR2,
                                 vAmat_cli   IN VARCHAR2,
                                 vEmail_cli  IN VARCHAR2,
                                 vFono_cli   IN CHAR,
                                 vSexo_cli   IN CHAR,
                                 vDir_cli    IN VARCHAR2,
                                 vFecNac_cli IN CHAR,
                                 pCodTarjeta IN CHAR,
                                 pCodLocal   IN CHAR,
                                 pUser       IN CHAR,
                                 pIndEstado  IN CHAR,
                                 ---agregadas
                                 cTipDoc     IN CHAR default 'N',
                                 cUserValida IN CHAR default 'N');

  FUNCTION FID_F_CHAR_BUSCA_DNI(vDni_cli_in    IN CHAR,
                                pCodLocal_in   IN CHAR,
                                pCodTarjeta_in IN CHAR) RETURN CHAR;

  --Descripcion: RETORNA UN INDICADOR SI LA TARJETA NO TIENE ASIGNADO CLIENTE EN LOCAL
  --Fecha       Usuario   Comentario
  --26/09/2008  Dubilluz    CREACION
  FUNCTION FID_F_CUR_CAMP_X_TARJETA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCodTarjeta_in  IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: RETORNA UN LAS TARJETAS ASIGNADAS A UN CLIENTE
  --Fecha       Usuario   Comentario
  --30/09/2008  Dubilluz    CREACION
  FUNCTION FID_F_CUR_TARJETA_X_DNI(cDni_in IN CHAR, cCodLocal_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: VALIDA SI LE FALTA CAMPOS Y SI ES UN USUARIO YA REGISTRADO
  --Fecha       Usuario   Comentario
  --30/09/2008  Dubilluz    CREACION
  FUNCTION FID_F_CHAR_VALIDA_TARJETA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cTarjeta_in     IN CHAR) RETURN CHAR;

  FUNCTION FID_F_NUM_CANT_CAMPOS_CLIETE(cDni IN CHAR) RETURN number;

  FUNCTION FID_F_CUR_CAMPOS_FID RETURN FarmaCursor;

  FUNCTION FID_F_CUR_DATOS_DNI(pCodTarjeta IN CHAR) RETURN FarmaCursor;

  --Descripcion: INSERTA DATOS EN LA TABLA FID_TARJETA_PEDIDO
  --Fecha       Usuario   Comentario
  --02/10/2008  DVELIZ    CREACION
  PROCEDURE FID_P_INSERT_TARJ_PED(pCodCia   IN CHAR,
                                  pCodLocal IN CHAR,
                                  pNumPed   IN VARCHAR2,
                                  pNumTarj  IN VARCHAR2,
                                  pDniCli   IN VARCHAR2,
                                  pCantDcto IN NUMBER,
                                  pIduUsu   IN VARCHAR2,
                                  ----------------------------------------
                                  --- dubilluz 21.05.2012
                                  cIndComision_in IN CHAR DEFAULT 'S',
                                  cCMP_in IN varchar2 DEFAULT 'N',
                                  cNombre_in IN varchar2 DEFAULT 'N',
                                  cDesc_Colegio_in IN varchar2 DEFAULT 'N',
                                  cTipColegio_in IN varchar2 DEFAULT 'N',
                                  cCodMedico_in IN varchar2 DEFAULT 'N' );

  --Descripcion: OBTIENE LOS DATOS DE UN CLIENTE PARA LA IMPRESION
  --Fecha       Usuario   Comentario
  --02/10/2008  DVELIZ    CREACION
  FUNCTION FID_F_CUR_OBTIENE_CLI_IMPR(pCodCia   IN CHAR,
                                      pCodLocal IN CHAR,
                                      pNumPed   IN VARCHAR2) RETURN VARCHAR2;

  --Descripcion: Retorna el precio de descuento
  --Fecha       Usuario   Comentario
  --30/09/2008  Dubilluz    CREACION
  FUNCTION FID_F_VAR2_GET_PRECIO_PROD(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodCampana_in  IN CHAR,
                                      cCodProducto_in IN CHAR,
                                      cPrecioVenta    IN CHAR)
    return varchar2;

  FUNCTION FID_F_CUR_DATOS_EXISTE_DNI(cDNI_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Retorna cadena de validacion de doc indentificacion valido
  --Fecha       Usuario   Comentario
  --06/10/2008  JCALLO    CREACION
  FUNCTION FID_F_VAR_VAL_DOC_IDEN RETURN VARCHAR2;

  FUNCTION FID_F_VAR2_GET_PRECIO_NORMAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodProducto_in IN CHAR)
    return varchar2;

  --Descripcion: RETORNA TODAS LAS CAMPAÑAS LIMITADAS EN CANTIDAD DE USOS QUE YA NO PUEDEN VOLVER A USAR EL CLIENTE
  --Fecha       Usuario   Comentario
  --06/10/2008  JCALLO    CREACION
  FUNCTION FID_F_LISTA_CAMPCL_USADOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cDni_cliente_in IN CHAR)
    RETURN FarmaCursor;

  /**
    Genera nueva ean 13 para nueva tarjeta de fidelizacion
    author    dveliz
    since     13.02.2009
  **/
  FUNCTION GENERA_EAN13(vCodigo_in IN VARCHAR2) RETURN CHAR;

  /**
    Genera nueva tarjeta de fidelizacion
    author    dveliz
    since     13.02.2009
  **/
  FUNCTION FID_F_CHAR_CREA_NEW_TARJ_FID(vCodGrupoCia_in IN VARCHAR2,
                                        vCodLocal_in    IN VARCHAR2,
                                        vConcatenado_in IN VARCHAR2,
                                        vUsuID_in       IN VARCHAR2)
    RETURN CHAR;

  /**
    Inserta nueva tarjeta de fidelizacion a la tabla FID_TARJETA
    author    dveliz
    since     13.02.2009
  **/
  PROCEDURE FID_P_INSERTA_TARJETA_FID(vNuevaTarjetaFidelizacion_in IN VARCHAR2,
                                      vCodLocal_in                 IN VARCHAR2,
                                      vUsuID_in                    IN VARCHAR2);

  PROCEDURE FID_P_INSERTA_TARJETA_FID(vNuevaTarjetaFidelizacion_in IN VARCHAR2,
                                      vCodLocal_in                 IN VARCHAR2,
                                      vUsuID_in                    IN VARCHAR2,
                                      vDNI_in                      IN VARCHAR2);

  FUNCTION FID_F_CHAR_BUSCA_TARJETA(pCodTarjeta_in IN CHAR) RETURN CHAR;

  /*****/
  -- AUTOR: JCALLO
  -- FECHA: 03/03/2009
  --  DESCRIPCION : SE CREO UN NUEVO PRECEDIMIENTO PARA USAR UN NUEVO FRAMEWORK
  /*****/
  -- modificado dubilluz 09.06.2011
  FUNCTION FID_F_CUR_CAMP_X_TARJETA_NEW(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        cCodTarjeta_in       IN CHAR,
                                        cIndUsoEfectivo_in   IN CHAR,
                                        cIndUsoTarjeta_in    IN CHAR,
                                        cCodForma_Tarjeta_in IN CHAR)
    RETURN FarmaCursor;

  -- AUTOR: DUBILLUZ
  -- FECHA: 28/05/2009
  --  DESCRIPCION : DNI Anul o Lista NEGRA
  FUNCTION FID_F_CHAR_IS_DNI_ANULADO(vCodGrupoCia_in IN VARCHAR2,
                                     vCodLocal_in    IN VARCHAR2,
                                     vDNI_in         IN VARCHAR2) RETURN CHAR;

  FUNCTION FID_F_CHAR_IS_RUC_ANULADO(vCodGrupoCia_in IN VARCHAR2,
                                     vCodLocal_in    IN VARCHAR2,
                                     vRUC_in         IN VARCHAR2) RETURN CHAR;

  -- AUTOR: DUBILLUZ
  -- FECHA: 28/05/2009
  --  DESCRIPCION : Obtiene el ahorro total segun el periodo que se indique en el momento
  FUNCTION FID_F_NUM_AHR_PER_DNI(vCodGrupoCia_in IN VARCHAR2,
                                 vCodLocal_in    IN VARCHAR2,
                                 vDNI_in         IN VARCHAR2,
                                 vTarj_in        IN VARCHAR2 default 'X') RETURN number;

  -- AUTOR: DUBILLUZ
  -- FECHA: 28/05/2009
  --  DESCRIPCION : Monto maximo de ahorro por periodo por DNI
  FUNCTION FID_F_NUM_MAX_AHR_PER_DNI(vCodGrupoCia_in IN VARCHAR2,
                                     vCodLocal_in    IN VARCHAR2,
                                     vDNI_in         IN VARCHAR2 DEFAULT NULL)
    RETURN number;

  -- AUTOR: DUBILLUZ
  -- FECHA: 28/05/2009
  --  DESCRIPCION : Maximo de unidades de DCTO de Producto x Campaña
  FUNCTION FID_F_NUM_MAX_UNID_PROD_CAMP(vCodGrupoCia_in IN char,
                                        vCodLocal_in    IN char,
                                        vCodCampana_in  IN char,
                                        vCodProd_in     IN char)
    RETURN number;

  -- AUTOR: DUBILLUZ
  -- FECHA: 28/05/2009
  --  DESCRIPCION : Valida el pedido Fidelizado
  FUNCTION FID_F_CHAR_VALIDA_PED_FID(vCodGrupoCia_in IN CHAR,
                                     vCodLocal_in    IN CHAR,
                                     vNumPedVta_in   IN CHAR,
                                     vRUC_in         IN CHAR,
                                     vTarj_in IN CHAR default 'X') RETURN CHAR;

  --JCORTEZ
  FUNCTION FID_F_CUR_OBTIENE_TARJ_CLI(pCodLocal IN CHAR, pDNI IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Se obtiene cupones del cliente
  --Fecha       Usuario   Comentario
  --04/08/2009  JCORTEZ     Creacion
  FUNCTION VTA_F_CUPON_CLI(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                           cDni            IN CHAR) RETURN FarmaCursor;

  --Descripcion: Inserta Cliente a Campaña Acumulada
  --Fecha       Usuario      Comentario
  --18/08/2009  JMIRANDA     Creacion
  PROCEDURE FID_P_INSERT_CA_CAMP_CLI(cCodGrupoCia_in IN CHAR,
                                     cDni_in         IN CHAR);
  FUNCTION FID_F_TIP_DOC(cGrupoCia_in IN CHAR) RETURN FarmaCursor;

  --DUBILLUZ 09.06.2011
  --PARA CARGAR LAS FORMAS DE PAGO X CAMPANA
  FUNCTION FID_F_FPAGO_USO_X_CAMPANA(cGrupoCia_in          IN CHAR,
                                     cCodLocal_in          IN CHAR,
                                     cCodCampanCupon       IN CHAR,
                                     cCuponesIngresados_in in varchar2)
    RETURN FarmaCursor;

  FUNCTION FID_IS_FP_X_USO(cGrupoCia_in    IN CHAR,
                           cCodLocal_in    IN CHAR,
                           cCodCampanCupon IN CHAR) RETURN varchar2;

  FUNCTION FID_F_FORMA_PAGO_TARJETA(A_NUM_TARJETA CHAR /*, A_FLG_TIPO CHAR */)
    RETURN CHAR;
  FUNCTION FID_IS_TARJETA_APLICA_CAMPANA(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         A_NUM_TARJETA CHAR) RETURN CHAR;
  FUNCTION FID_F_DATO_TARJETA_INGRESADA(cGrupoCia_in  IN CHAR,
                                        cCodLocal_in  IN CHAR,
                                        A_NUM_TARJETA CHAR) RETURN varchar2;
  FUNCTION FN_LISTA_TJT_NUMEROM10(A_NUM_TARJETA CHAR, A_FLG_VALIDA CHAR)
    RETURN CHAR;

  FUNCTION FID_F_CAMP_X_TARJ_VISA(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  A_NUM_TARJETA CHAR) RETURN varchar2;

  function REVER_PALABRA(cadena_a_revertir VARCHAR2) return varchar2;

  /* ********************************************************************* */
  PROCEDURE FID_P_INSERT_TARJ_UNICA(cGrupoCia_in  IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    A_NUM_TARJETA IN CHAR,
                                    vDni_cli      IN CHAR);

  function FID_F_IS_VALIDA_MATRIZ(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNumPedido_in IN CHAR) return char;

  function fid_f_existe_dni_asociado(tarjetaUnica VARCHAR2) return varchar2;

  function FID_VALIDA_DNI(vDNI_in VARCHAR2) return varchar2;

  function FID_F_VALIDA_COBRO_PEDIDO(cCodGrupoCia_in in char,
                                     cCodLocal_in    in char,
                                     cNumPedVta_in   in char) return varchar2;

  function FID_F_VALIDA_DNI_PEDIDO(cCodGrupoCia_in in char,
                                   cCodLocal_in    in char,
                                   cNumPedVta_in   in char) return varchar2;

  function FID_F_GET_FID_USO(cCodGrupocia_in in char,
                             cCodCampania_in in char) return char;

  function FID_F_INI_NUEVO_DNI(cCodGrupocia_in in varchar2,
                               cCodLocal_in    in varchar2,
                               cCodCampania_in in varchar2,
                               cCodTarjeta_in  in varchar2) return VARCHAR2;

  -- DUBILLUZ 06.12.2011
  function FID_F_EXIST_CAMP_COLEGIO_MED(cCodGrupocia_in in varchar2,
                                        cCodLocal_in    in varchar2)
    return VARCHAR2;
  -- DUBILLUZ 06.12.2011
  function FID_F_EXIST_COD_MED(cCodGrupocia_in in varchar2,
                               cCodLocal_in    in varchar2,
                               cCodMed_in      in varchar2) return VARCHAR2;
  -- dubilluz 06.12.2011
  FUNCTION FID_F_CUR_CAMP_X_COD_MEDICO(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        cCodTarjeta_in       IN CHAR,
                                        cCodMedico_in   IN CHAR)
    RETURN FarmaCursor ;

-- INICIO  dubilluz 01.06.2012
FUNCTION FID_F_MAX_AHORRO_DIARIO(vCodGrupoCia_in IN VARCHAR2,
                                 vCodLocal_in    IN VARCHAR2,
                                 vDNI_in         IN VARCHAR2 DEFAULT NULL,
                                 vTarj_in        in varchar2
                                 )
                                RETURN NUMBER;

FUNCTION FID_F_CUR_CAMP_X_TARJ_ESPECIAL(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        cCodTarjeta_in       IN CHAR)
    RETURN FarmaCursor;
-- FIN    dubilluz 01.06.2012
FUNCTION GET_INDICADOR_COMISION(cCodGrupocia_in in char,
                             cCodLocal_in in char) return char;

END PTOVENTA_FIDELIZACION;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_FIDELIZACION" AS

  /****************************************************************************/
  FUNCTION FID_F_VAR_VALIDA_CLIENTE(pCodTarjeta IN CHAR, pCodLocal IN CHAR)
    RETURN VARCHAR2 AS
    vCount1     NUMBER(1);
    vCount2     NUMBER(1);
    vRetorno    VARCHAR2(1);
    vCantCampo1 NUMBER(2);
    vCantCampo2 NUMBER(2);
    vDni        VARCHAR2(20);
  BEGIN
    SELECT COUNT(*)
      INTO vCount1
      FROM FID_TARJETA
     WHERE COD_TARJETA = pCodTarjeta
       AND DNI_CLI IS NOT NULL;
    --AND COD_LOCAL = pCodLocal;

    IF (vCount1 = 1) THEN

      SELECT COUNT(*)
        INTO vCount2
        FROM PBL_CLIENTE
       WHERE DNI_CLI IN (SELECT DNI_CLI
                           FROM FID_TARJETA
                          WHERE COD_TARJETA = pCodTarjeta)
            -- AND COD_LOCAL = pCodLocal)
         AND IND_ESTADO = 'A';

      IF (vCount2 = 0) THEN
        vRetorno := '0';
      ELSE
        SELECT DNI_CLI
          INTO vDni
          FROM FID_TARJETA
         WHERE COD_TARJETA = pCodTarjeta;

        /*SELECT MAX(CANT_CAMPOS) INTO vCantCampo1
        FROM FID_CAMPOS_FIDELIZACION;*/

        -- se obtiene el numero de campos ingresados por el cliente
        vCantCampo1 := FID_F_NUM_CANT_CAMPOS_CLIETE(vDni);

        SELECT COUNT(*)
          INTO vCantCampo2
          FROM FID_CAMPOS_FIDELIZACION
         WHERE IND_MOD = CC_MOD_TAR_FID;

        IF (vCantCampo1 >= vCantCampo2) THEN
          vRetorno := '2';
        ELSE
          /*UPDATE FID_CAMPOS_FIDELIZACION
          SET CANT_CAMPOS = vCantCampo2;*/
          vRetorno := '1';
        END IF;
      END IF;
    ELSE
      vRetorno := '0';
    END IF;

    RETURN vRetorno;

  END FID_F_VAR_VALIDA_CLIENTE;

  /***************************************************************************/

  FUNCTION FID_F_VAR_VALIDA_TARJETA(pCodTarjeta IN CHAR, pCodLocal IN CHAR)
    RETURN VARCHAR2 AS
    vCount NUMBER(1);
  BEGIN
    SELECT COUNT(*)
      INTO vCount
      FROM FID_TARJETA
     WHERE COD_TARJETA = pCodTarjeta;
    --AND COD_LOCAL = pCodLocal;

    --LLEIVA 12-Mar-2014 Si no existe, se inserta el codigo de tarjeta
    if (vCount = 0) then
        FID_P_INSERTA_TARJETA_FID(pCodTarjeta,
                                  pCodLocal,
                                  'FARMAVENTA');
        vCount := 1;
    END IF;

    RETURN TO_CHAR(vCount);

  END FID_F_VAR_VALIDA_TARJETA;

  /**************************************************************************/
  FUNCTION FID_F_CUR_LISTA_FIDELIZACION RETURN FarmaCursor IS

    curCamp FarmaCursor;
  BEGIN

    OPEN curCamp FOR
      SELECT NOM_CAMPO || 'Ã' || ' ' || 'Ã' || CF.COD_CAMPO || 'Ã' ||
             IND_TIP_DATO || 'Ã' || IND_SOLO_LECTURA || 'Ã' ||
             IND_OBLIGATORIO || 'Ã' || 0
        FROM FID_CAMPOS_FORMULARIO CF, FID_CAMPOS_FIDELIZACION CO
       WHERE CF.COD_CAMPO = CO.COD_CAMPO
         AND CO.IND_MOD = CC_MOD_TAR_FID
       order by cf.COD_CAMPO asc;

    RETURN curCamp;
  END FID_F_CUR_LISTA_FIDELIZACION;

  /***************************************************************************/

  FUNCTION FID_F_CUR_DATOS_CLIENTE(cGrupocia_in IN CHAR default '001',
                                   pCodLocal    IN CHAR,
                                   pCodTarjeta  IN CHAR) RETURN FarmaCursor IS
    curCamp FarmaCursor;
    v_Dni   VARCHAR2(20);

  BEGIN
    --JMIRANDA 17/08/2009
    SELECT B.DNI_CLI
      INTO v_Dni
      FROM FID_TARJETA B
     WHERE B.COD_TARJETA = pCodTarjeta;

    FID_P_INSERT_CA_CAMP_CLI(cGrupocia_in, v_Dni);

    OPEN curCamp FOR

    /* SELECT
              'Ã Ã Ã Ã Ã  Ã Ã '
              from dual;*/
      SELECT nvl(A.DNI_CLI, ' ') || 'Ã' || nvl(A.APE_PAT_CLI, ' ') || 'Ã' ||
             nvl(A.APE_MAT_CLI, ' ') || 'Ã' || nvl(A.NOM_CLI, ' ') || 'Ã' ||
             nvl(' ' || A.FEC_NAC_CLI, ' ') || 'Ã' || nvl(A.SEXO_CLI, ' ') || 'Ã' ||
             nvl(A.DIR_CLI, ' ') || 'Ã' || nvl(' ' || A.FONO_CLI, ' ')
        FROM PBL_CLIENTE A
       INNER JOIN FID_TARJETA B ON (A.DNI_CLI = B.DNI_CLI)
       WHERE B.COD_TARJETA = pCodTarjeta
            --AND B.COD_LOCAL = pCodLocal
         AND A.IND_ESTADO = 'A';
    RETURN curCamp;
  END FID_F_CUR_DATOS_CLIENTE;

  /***************************************************************************/

  PROCEDURE FID_P_INSERT_CLI_LOCAL(vDni_cli    IN CHAR,
                                   vNom_cli    IN VARCHAR2,
                                   vApat_cli   IN VARCHAR2,
                                   vAmat_cli   IN VARCHAR2,
                                   vEmail_cli  IN VARCHAR2,
                                   vFono_cli   IN CHAR,
                                   vSexo_cli   IN CHAR,
                                   vDir_cli    IN VARCHAR2,
                                   vFecNac_cli IN CHAR,
                                   pCodTarjeta IN CHAR,
                                   pCodLocal   IN CHAR,
                                   pUser       IN CHAR,
                                   pIndEstado  IN CHAR,
                                   ---agregadas
                                   cTipDoc     IN CHAR default 'N',
                                   cUserValida IN CHAR default 'N')

   AS

    PRAGMA AUTONOMOUS_TRANSACTION;

    vCount      NUMBER;
    vCantCampo1 number;
    vCantCampo2 number;
    vDni_cli_2  varchar2(30);
  BEGIN

    vDni_cli_2 := vDni_cli;
    --dubilluz 25.07.2011
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    SELECT COUNT(*)
      INTO vCount
      FROM PBL_CLIENTE
     WHERE DNI_CLI = vDni_cli_2;

    IF trim(vDni_cli_2) is not NULL THEN

      dbms_output.put_line('vCount ' || vCount);

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
          (vDni_cli_2,
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

        /* UPDATE FID_TARJETA
          SET DNI_CLI = vDni_cli,
              USU_MOD_TARJETA = pUser,
              FEC_MOD_TARJETA = SYSDATE,
              cod_local = pCodLocal
        WHERE COD_TARJETA = pCodTarjeta;*/

      END IF;

      IF cTipDoc != 'N' and cUserValida != 'N' THEN
        --q se valido el DNI
        --y se ingreso usuario y clave
        ---
        UPDATE PBL_CLIENTE X
           SET X.ID_USU_CONFIR     = cUserValida,
               X.USU_MOD_CLIENTE   = cUserValida,
               X.FEC_MOD_CLIENTE   = SYSDATE,
               x.cod_tip_documento = cTipDoc
        --WHERE TRIM(X.DNI_CLI)= vDni_cli;
         WHERE X.DNI_CLI = vDni_cli;

      END IF;

      COMMIT;

    END IF;

  EXCEPTION

    WHEN OTHERS THEN

      dbms_output.put_line('error');

      rollback;

  END;
  /* **********************************************************************/
  PROCEDURE FID_P_INSERT_CLIENTE(vDni_cli    IN CHAR,
                                 vNom_cli    IN VARCHAR2,
                                 vApat_cli   IN VARCHAR2,
                                 vAmat_cli   IN VARCHAR2,
                                 vEmail_cli  IN VARCHAR2,
                                 vFono_cli   IN CHAR,
                                 vSexo_cli   IN CHAR,
                                 vDir_cli    IN VARCHAR2,
                                 vFecNac_cli IN CHAR,
                                 pCodTarjeta IN CHAR,
                                 pCodLocal   IN CHAR,
                                 pUser       IN CHAR,
                                 pIndEstado  IN CHAR,
                                 ---agregadas
                                 cTipDoc     IN CHAR default 'N',
                                 cUserValida IN CHAR default 'N') AS

    PRAGMA AUTONOMOUS_TRANSACTION;

    vCount      NUMBER;
    vCantCampo1 number;
    vCantCampo2 number;

    vCantidad    number;
    vCodTrabRRHH VARCHAR2(20);
    vDni_cli_2   varchar2(30);
  BEGIN

    vDni_cli_2 := vDni_cli;
    --dubilluz 25.07.2011
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    SELECT COUNT(*)
      INTO vCount
      FROM PBL_CLIENTE
    --WHERE TRIM(DNI_CLI) = vDni_cli
     WHERE DNI_CLI = vDni_cli_2
       AND IND_ESTADO = 'A';

    IF trim(vDni_cli_2) is not NULL THEN

      dbms_output.put_line('vCount ' || vCount);

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
          (vDni_cli_2,
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
           /*           DECODE(vFecNac_cli,
                             'N',
                             NULL,
                             null,
                             null,
                             TO_DATE(vFecNac_cli, 'DD/MM/YYYY')),*/
           to_date(DECODE(vFecNac_cli, 'N', NULL, null, null, vFecNac_cli),
                   'dd/mm/yyyy'),
           SYSDATE,
           pUser,
           NULL,
           NULL,
           pIndEstado);
      ELSE

        --solo si se  falta campos.

        -- se obtiene el numero de campos ingresados por el cliente
        vCantCampo1 := FID_F_NUM_CANT_CAMPOS_CLIETE(vDni_cli_2);

        SELECT COUNT(*)
          INTO vCantCampo2
          FROM FID_CAMPOS_FIDELIZACION
         WHERE IND_MOD = CC_MOD_TAR_FID;
        dbms_output.put_line('vCantCampo1 ' || vCantCampo1);
        dbms_output.put_line('vCantCampo2 ' || vCantCampo2);
        --IF(vCantCampo1 >= vCantCampo2)THEN
        IF (vCantCampo2 >= vCantCampo1) THEN

          UPDATE PBL_CLIENTE L
             SET NOM_CLI     = DECODE(vNom_cli, 'N', NULL, vNom_cli),
                 APE_PAT_CLI = DECODE(vApat_cli, 'N', NULL, vApat_cli),
                 APE_MAT_CLI = DECODE(vAmat_cli, 'N', NULL, vAmat_cli),
                 FONO_CLI    = decode(vFono_cli,
                                      'N',
                                      NULL,
                                      TO_NUMBER(vFono_cli, '9999999.000')),

                 SEXO_CLI    = DECODE(vSexo_cli, 'N', NULL, vSexo_cli),
                 DIR_CLI     = DECODE(vDir_cli, 'N', NULL, vDir_cli),
                 FEC_NAC_CLI = /*DECODE(vFecNac_cli,
                                                     'N',
                                                     NULL,
                                                     TO_DATE(vFecNac_cli, 'DD/MM/YYYY'))*/

                  to_date(decode(vFecNac_cli, 'N', null, vFecNac_cli),
                          'dd/mm/yyyy'),
                 --JCORTEZ 02.07.09 Se actualiza para saber que actualizar en matriz.
                 L.COD_LOCAL_ORIGEN = pCodLocal,
                 L.FEC_MOD_CLIENTE  = SYSDATE,
                 L.USU_MOD_CLIENTE  = pUser
           WHERE L.DNI_CLI = vDni_cli_2;
        end if;
      END IF;

      UPDATE FID_TARJETA
         SET DNI_CLI         = vDni_cli_2,
             USU_MOD_TARJETA = pUser,
             FEC_MOD_TARJETA = SYSDATE,
             cod_local       = pCodLocal
       WHERE COD_TARJETA = pCodTarjeta
            --cambio para no actualizar tarjetas que NO SEA NECESARIO
            --30.10.2009 dubilluz
            --and   dni_cli != vDni_cli;
         and nvl(dni_cli, ' ') != nvl(vDni_cli_2, ' ');
      --         AND COD_LOCAL = pCodLocal;

      IF cTipDoc != 'N' and cUserValida != 'N' THEN
        --q se valido el DNI
        --y se ingreso usuario y clave
        ---

        -- PBL_USU_LOCAL , RRHH   --JMIRANDA 23.10.2009
        SELECT NVL(COD_TRAB_RRHH, SEC_USU_LOCAL)
          INTO vCodTrabRRHH
          FROM PBL_USU_LOCAL L
         WHERE L.COD_GRUPO_CIA = '001'
           AND L.COD_LOCAL = pCodLocal
           AND L.SEC_USU_LOCAL = cUserValida;

        UPDATE PBL_CLIENTE X
           SET X.ID_USU_CONFIR     = vCodTrabRRHH,
               X.USU_MOD_CLIENTE   = cUserValida,
               X.FEC_MOD_CLIENTE   = SYSDATE,
               x.cod_tip_documento = cTipDoc,
               X.COD_LOCAL_CONFIR  = pCodLocal,
               X.Ip_Confir         = (SELECT SYS_CONTEXT('USERENV',
                                                         'IP_ADDRESS')
                                        FROM DUAL)
         WHERE X.DNI_CLI = vDni_cli_2;

        /*  UPDATE PBL_CLIENTE X
               SET X.ID_USU_CONFIR=cUserValida,
                   X.USU_MOD_CLIENTE=cUserValida,
                   X.FEC_MOD_CLIENTE=SYSDATE,
                   x.cod_tip_documento = cTipDoc
               --WHERE TRIM(X.DNI_CLI)= vDni_cli;
               WHERE X.DNI_CLI= vDni_cli;
        */

      END IF;

      COMMIT;

    END IF;

  EXCEPTION

    WHEN OTHERS THEN

      dbms_output.put_line('error');

      rollback;

  END;

  /* ********************************************* */

  FUNCTION FID_F_CHAR_BUSCA_DNI(vDni_cli_in    IN CHAR,
                                pCodLocal_in   IN CHAR,
                                pCodTarjeta_in IN CHAR) RETURN CHAR IS

    vRes  char(1);
    nCant number;
  BEGIN

    SELECT COUNT(1)
      into nCant
      FROM FID_TARJETA T, PBL_CLIENTE C
     WHERE T.COD_TARJETA = pCodTarjeta_in
       AND T.DNI_CLI = vDni_cli_in
          --AND    T.COD_LOCAL = pCodLocal_in
       AND T.DNI_CLI = C.DNI_CLI
       AND C.IND_ESTADO = 'A';

    if nCant > 0 then
      vRes := 'S';
    else
      vRes := 'N';

    end if;
    return vRes;
  END;

  /**************************************************************************/

  FUNCTION FID_F_CUR_CAMP_X_TARJETA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cCodTarjeta_in  IN CHAR)
    RETURN FarmaCursor IS

    curLista FarmaCursor;
    nNumDia  VARCHAR(2);
    cSexo    char(1);
    dFecNaci date;
  BEGIN

    nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);

    --OBTENIENDO EL SEXO Y LA FECHA DE NACIMIENTO DEL CLIENTE
    SELECT CL.SEXO_CLI, trunc(CL.FEC_NAC_CLI)
      INTO cSexo, dFecNaci
      FROM PBL_CLIENTE CL
     WHERE CL.DNI_CLI =
           (SELECT F.Dni_Cli
              FROM FID_TARJETA F
             WHERE F.COD_TARJETA = cCodTarjeta_in);

    --dbms_output.put_line('datos del cliente : sexo:'||cSexo||', fecha_nac:'||dFecNaci);
    -- LISTADO DE LAS CAMPAÑAS DE FIDELIZACION

    OPEN curLista FOR
      SELECT 'F' || C.COD_CAMP_CUPON || 'Ã' || C.COD_CAMP_CUPON || 'Ã' ||
             C.TIP_CUPON || 'Ã' || C.VALOR_CUPON || 'Ã' ||
             TRIM(to_char(NVL(C.MONT_MIN_USO, 0), '00000000.000')) || 'Ã' ||
             NVL(C.UNID_MIN_USO, 0) || 'Ã' || NVL(C.UNID_MAX_PROD, 0) || 'Ã' ||
             NVL(C.MONTO_MAX_DESCT, 0) || 'Ã' || 'N' || 'Ã' ||
             LPAD(C.PRIORIDAD, 8, '0') || LPAD(C.RANKING, 8, '0') ||
             TIP_CUPON || TO_CHAR(100000 - VALOR_CUPON, '00000.000')
        FROM VTA_CAMPANA_CUPON C --, FID_TARJETA T--, FID_CAMP_TARJETA CT
       WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
         AND C.ESTADO = 'A'
         AND C.IND_FID = 'S'
         AND C.TIP_CAMPANA = 'C'
         AND C.NUM_CUPON = 0
         -- dubilluz campañas que no son para colegio medido
         -- 06.12.2011
         and nvl(c.ind_tipo_colegio,'00') = '00'
         AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
            -- agregado el filtro de los campos de SEXO y EDAD
         AND (C.TIPO_SEXO_U IS NULL OR C.TIPO_SEXO_U = cSexo)
         AND (C.FEC_NAC_INICIO_U IS NULL OR C.FEC_NAC_INICIO_U <= dFecNaci)
         AND (C.FEC_NAC_FIN_U IS NULL OR C.FEC_NAC_FIN_U >= dFecNaci)
            --fin de filtro de los campos de Sexo y Edad
         AND C.COD_CAMP_CUPON IN
             ( /*SELECT *
                              FROM (SELECT *
                                      FROM (SELECT COD_CAMP_CUPON
                                              FROM VTA_CAMPANA_CUPON
                                            MINUS
                                            SELECT CL.COD_CAMP_CUPON
                                              FROM VTA_CAMP_X_LOCAL CL)
                                    UNION
                                    SELECT COD_CAMP_CUPON
                                      FROM VTA_CAMP_X_LOCAL
                                     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                       AND COD_LOCAL = cCodLocal_in
                                       AND ESTADO = 'A')*/
              --JCORTEZ 19.10.09 cambio de logica
              SELECT *
                FROM (SELECT X.COD_CAMP_CUPON
                         FROM VTA_CAMPANA_CUPON X
                        WHERE X.COD_GRUPO_CIA = '001'
                          AND X.TIP_CAMPANA = 'C'
                          AND X.ESTADO = 'A'
                          AND X.IND_CADENA = 'S'
                       UNION
                       SELECT Y.COD_CAMP_CUPON
                         FROM VTA_CAMPANA_CUPON Y
                        WHERE Y.COD_GRUPO_CIA = '001'
                          AND Y.TIP_CAMPANA = 'C'
                          AND Y.ESTADO = 'A'
                          AND Y.IND_CADENA = 'N'
                          AND Y.COD_CAMP_CUPON IN
                              (SELECT COD_CAMP_CUPON
                                 FROM VTA_CAMP_X_LOCAL Z
                                WHERE Z.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND Z.COD_LOCAL = cCodLocal_in
                                  AND Z.ESTADO = 'A')

                       ))
         AND C.COD_CAMP_CUPON IN
             (SELECT *
                FROM (SELECT *
                        FROM (SELECT COD_CAMP_CUPON
                                FROM VTA_CAMPANA_CUPON
                              MINUS
                              SELECT H.COD_CAMP_CUPON FROM VTA_CAMP_HORA H)
                      UNION
                      SELECT H.COD_CAMP_CUPON
                        FROM VTA_CAMP_HORA H
                       WHERE TRIM(TO_CHAR(SYSDATE, 'HH24')) BETWEEN
                             H.HORA_INICIO AND H.HORA_FIN))
         AND DECODE(C.DIA_SEMANA,
                    NULL,
                    'S',
                    DECODE(C.DIA_SEMANA,
                           REGEXP_REPLACE(C.DIA_SEMANA, nNumDia, 'S'),
                           'N',
                           'S')) = 'S'

            --QUITANDO LAS CAMPAÑAS LIMITADAS POR CANTIDAS DE USOS POR CLIENTE A LAS CAMPAÑAS AUTOMATICAS
            --JCALLO 13/02/2009
         AND C.COD_CAMP_CUPON NOT IN
             (SELECT COD_CAMP_CUPON
                FROM CL_CLI_CAMP
               WHERE DNI_CLI =
                     (SELECT F.Dni_Cli
                        FROM FID_TARJETA F
                       WHERE F.COD_TARJETA = cCodTarjeta_in)
                 AND MAX_USOS <= NRO_USOS);

    RETURN curLista;

  END FID_F_CUR_CAMP_X_TARJETA;

  /**************************************************************************/
  FUNCTION FID_F_CUR_TARJETA_X_DNI(cDni_in IN CHAR, cCodLocal_in IN CHAR)
    RETURN FarmaCursor

   IS
    curLista FarmaCursor;
  BEGIN
    -- KMONCADA 24.09.2014 VERIFICA LONGITUD DE DNI.
    IF (LENGTH(TRIM(cDni_in))=8) THEN
      ptoventa_fid_reniec.p_genera_tarjeta_dni('001',cCodLocal_in, trim(cDni_in));
    END IF;

    OPEN curLista FOR
      SELECT COD_TARJETA
        FROM FID_TARJETA
       WHERE /*COD_LOCAL = cCodLocal_in
              AND */
       DNI_CLI = cDni_in;
    RETURN curLista;
  END FID_F_CUR_TARJETA_X_DNI;

  /* ***************************************************************** */
  FUNCTION FID_F_CHAR_VALIDA_TARJETA(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cTarjeta_in     IN CHAR) RETURN CHAR IS

    vRes char(1) := 'N';

    nCant number;
  BEGIN

    SELECT COUNT(1)
      into nCant
      FROM FID_TARJETA T
     WHERE T.COD_TARJETA = T.COD_TARJETA
       AND T.DNI_CLI IS NULL;

    if nCant > 0 then
      -- tarjeta esta libre para asociar
      vRes := 'S';
    end if;

    return vRes;

  END;

  /* ***************************************************************** */
  FUNCTION FID_F_NUM_CANT_CAMPOS_CLIETE(cDni IN CHAR) RETURN number is
    cCant      number;
    vDni_cli_2 varchar(30);
  begin
    /*
          select decode(DNI_CLI, null, 0, 1) + decode(NOM_CLI, null, 0, 1) +
                 decode(APE_PAT_CLI, null, 0, 1) + decode(APE_MAT_CLI, null, 0, 1) +
                 decode(FONO_CLI, null, 0, 1) + decode(SEXO_CLI, null,0, 1) +
                 decode(DIR_CLI, null, 0, 1) + decode(FEC_NAC_CLI, null, 0, 1)+
                  decode(l.email, null, 0, 1)
    */
    /*select decode(DNI_CLI, null, 0, 1) + decode(NOM_CLI, null, 0, 1) +
                 decode(FEC_NAC_CLI, null, 0, 1) +  decode(SEXO_CLI, null,0, 1)
            into cCant
            from pbl_cliente l
           where l.dni_cli = trim(cDni)
           AND l.ind_estado = 'A';
    */

    --dubilluz 25.07.2011
    vDni_cli_2 := cDni;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);
    
    select sum(decode(v.cod_campo,
                      '003',
                      decode(v.pidedato, 0, 0, decode(l.NOM_CLI, null, 0, 1)),
                      0) +
               decode(v.cod_campo,
                      '002',
                      decode(v.pidedato, 0, 0, decode(l.DNI_CLI, null, 0, 1)),
                      0) + decode(v.cod_campo,
                                  '009',
                                  decode(v.pidedato,
                                         0,
                                         0,
                                         decode(l.FEC_NAC_CLI, null, 0, 1)),
                                  0) +
               decode(v.cod_campo,
                      '008',
                      decode(v.pidedato,
                             0,
                             0,
                             decode(l.SEXO_CLI, null, 0, 1)),
                      0) + decode(v.cod_campo,
                                  '006',
                                  decode(v.pidedato,
                                         0,
                                         0,
                                         decode(l.fono_cli, null, 0, 1)),
                                  0) +
               decode(v.cod_campo,
                      '007',
                      decode(v.pidedato, 0, 0, decode(l.dir_cli, null, 0, 1)),
                      0) + decode(v.cod_campo,
                                  '005',
                                  decode(v.pidedato,
                                         0,
                                         0,
                                         decode(l.ape_mat_cli, null, 0, 1)),
                                  0) +
               decode(v.cod_campo,
                      '010',
                      decode(v.pidedato, 0, 0, decode(l.email, null, 0, 1)),
                      0) + decode(v.cod_campo,
                                  '011',
                                  decode(v.pidedato,
                                         0,
                                         0,
                                         decode(l.cell_cli, null, 0, 1)),
                                  0) +
               decode(v.cod_campo,
                      '004',
                      decode(v.pidedato,
                             0,
                             0,
                             decode(l.ape_pat_cli, null, 0, 1)),
                      0))
      into cCant
      from pbl_cliente l,
           (select f.cod_campo,
                   f.nom_campo,
                   (select count(1)
                      from fid_campos_fidelizacion a
                     where a.cod_campo = f.cod_campo
                       and a.ind_mod = 'TF') pideDato
              from fid_campos_formulario f
             where est_campo = 'A') v
     where l.dni_cli = trim(vDni_cli_2)
       AND l.ind_estado = 'A';
   
    return cCant;
  end;

  /* ***************************************************************** */
  FUNCTION FID_F_CUR_CAMPOS_FID RETURN FarmaCursor IS
    curLista FarmaCursor;
  BEGIN
    OPEN curLista FOR
      SELECT C.COD_CAMPO
        FROM FID_CAMPOS_FIDELIZACION C
       WHERE C.IND_MOD = CC_MOD_TAR_FID;

    RETURN curLista;
  END;

  /* ******************************************************** */
  FUNCTION FID_F_CUR_DATOS_DNI(pCodTarjeta IN CHAR) RETURN FarmaCursor IS
    curCamp FarmaCursor;
    vDNI    varchar2(20);
  BEGIN

    select f.dni_cli
      into vDNI
      from fid_tarjeta f
     where f.cod_tarjeta = pCodTarjeta;

    OPEN curCamp FOR

      SELECT A.DNI_CLI || 'Ã ' || nvl(A.APE_PAT_CLI, 'N') || 'Ã' ||
             nvl(A.APE_MAT_CLI, 'N') || 'Ã' || nvl(A.NOM_CLI, 'N') || 'Ã' ||
             nvl(to_char(A.FEC_NAC_CLI, 'dd/mm/yyyy'), 'N') || 'Ã' || --dubilluz 05.04.2010
             nvl(A.SEXO_CLI, 'N') || 'Ã' || nvl(A.DIR_CLI, 'N') || 'Ã' ||
             nvl('' || A.FONO_CLI, 'N') || 'Ã' || nvl('' || A.Email, 'N')
        FROM PBL_CLIENTE A
       WHERE A.DNI_CLI = vDNI;
    RETURN curCamp;
  END FID_F_CUR_DATOS_DNI;

  /* ********************************** */
  FUNCTION FID_F_CUR_DATOS_EXISTE_DNI(cDNI_in IN CHAR) RETURN FarmaCursor IS
    curCamp FarmaCursor;
    vDNI    varchar2(20);
    nCant   number;
    nCant2  number;

    vValidaReniec varchar2(2);
    vDni_cli_2    varchar(30);

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

    --dubilluz 25.07.2011
    vDni_cli_2 := cDNI_in;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    SELECT count(1)
      INTO nCant
      FROM PBL_CLIENTE f
    --WHERE  TRIM(f.dni_cli) = cDNI_in;
     WHERE f.dni_cli = vDni_cli_2;

    vValidaReniec := PTOVENTA_FID_RENIEC.F_VAR2_GET_IND_VALIDA_RENIEC;

    if vValidaReniec = 'N' then

      IF nCant = 0 THEN
        OPEN curCamp FOR
          SELECT '$' from dual; --where 1=2;
      else
        OPEN curCamp FOR
          SELECT A.DNI_CLI || 'Ã ' || nvl(A.APE_PAT_CLI, 'N') || 'Ã' ||
                  nvl(A.APE_MAT_CLI, 'N') || 'Ã' ||
                  decode(nvl(A.NOM_CLI, 'N'),
                         'N',
                         decode(nvl(A.NOM_CLI, 'N'), 'N', '@' || A.NOM_CLI),
                         '&' || A.NOM_CLI) || 'Ã' ||
                 --                nvl('&'||A.NOM_CLI,'N')           || 'Ã' ||
                  nvl('' || A.FEC_NAC_CLI, 'N') || 'Ã' ||
                  nvl(A.SEXO_CLI, 'N') || 'Ã' || nvl(A.DIR_CLI, 'N') || 'Ã' ||
                  nvl('' || A.FONO_CLI, 'N') || 'Ã' ||
                  nvl('' || A.Email, 'N')
            FROM PBL_CLIENTE A
           WHERE A.DNI_CLI = vDni_cli_2;
      end if;

    else

    -- dubilluz 16.05.2012
    /*
    SELECT COUNT(1)
    INTO nCant2
    FROM @PBL_DNI_RED@ A
    WHERE A.LE=vDni_cli_2;
    */
    -- KMONCADA 24.09.2014 VERIFICA LONGITUD DE CADENA DNI
    IF LENGTH(TRIM(vDni_cli_2))=8 THEN
      vDatosDNI := utility_dni_reniec.aux_datos_existe_dni('001','000',vDni_cli_2);
    ELSE
      vDatosDNI := 'N';
    END IF;
    
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


      IF nCant = 0 AND nCant2 = 0 THEN
        OPEN curCamp FOR
          SELECT '$' from dual; --where 1=2;

      ELSE
        --------------------------------------
        if nCant > 0 THEN

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

            select nvl(reniec.dni, cliente.dni) || 'Ã' ||
                   ------------------------------------
                    nvl(cliente.ape_pat, 'N') || 'Ã' ||
                    nvl(cliente.ape_mat, 'N') || 'Ã' ||
                   --nvl(nvl('@'||reniec.nombre,'&'||cliente.nombre),'N') || 'Ã' ||
                   --nvl(nvl('&'||cliente.nombre,'@'||reniec.nombre),'N') || 'Ã' ||
                    decode(nvl(cliente.nombre, 'N'),
                           'N',
                           decode(nvl(reniec.nombre, 'N'),
                                  'N',
                                  '@' || reniec.nombre),
                           '&' || cliente.nombre) || 'Ã' ||
                   --nvl(cliente.nombre,'N') || 'Ã' ||
                    NVL(nvl(reniec.fecha, cliente.fecha), 'N') || 'Ã' ||
                    nvl(cliente.sexo, 'N') || 'Ã' || nvl(cliente.dir, 'N') || 'Ã' ||
                    nvl(cliente.fono || '', 'N') || 'Ã' ||
                    nvl(cliente.correo, 'N')
              from (select A.DNI_CLI dni,
                           A.NOM_CLI nombre,
                           to_char(A.FEC_NAC_CLI, 'dd/mm/yyyy') fecha,

                           A.APE_PAT_CLI ape_pat,
                           A.APE_MAT_CLI ape_mat,
                           A.SEXO_CLI    sexo,
                           A.DIR_CLI     dir,
                           A.FONO_CLI    fono,
                           A.Email       correo
                      from pbl_cliente a
                     where dni_cli = vDni_cli_2) cliente,
                   (
/*                    select r.le dni,
                           r.nombre_completo nombre,
                           to_char(to_date(r.fec_nac, 'yyyymmdd'),
                                   'dd/mm/yyyy') fecha,

                           null ape_pat,
                           null ape_mat,
                           null sexo,
                           null dir,
                           null fono,
                           null correo
                      from @pbl_dni_red@ r
                    --where  r.le LIKE cDNI_in||'%'
                     where r.le = vDni_cli_2
*/
                        SELECT VAL_DNI dni,
                               VAL_NOMBRE nombre,
                               TO_CHAR(VAL_FECHA_NAC,'DD/MM/YYYY') fecha,
                               null ape_pat,
                               null ape_mat,
                               null sexo,
                               null dir,
                               null fono,
                               null correo
                        FROM   DUAL

                   ) reniec
             where cliente.dni = reniec.dni(+);

        else
          if nCant2 > 0 THEN

            OPEN curCamp FOR
              select nvl(reniec.dni, cliente.dni) || 'Ã' ||
                     ------------------------------------
                      nvl(cliente.ape_pat, 'N') || 'Ã' ||
                      nvl(cliente.ape_mat, 'N') || 'Ã' ||
                      nvl(nvl('@' || reniec.nombre, '&' || cliente.nombre),
                          'N') || 'Ã' ||
                     --nvl(cliente.nombre,'N') || 'Ã' ||
                      nvl(reniec.fecha, cliente.fecha) || 'Ã' ||
                      nvl(cliente.sexo, 'N') || 'Ã' || nvl(cliente.dir, 'N') || 'Ã' ||
                      nvl(cliente.fono || '', 'N') || 'Ã' ||
                      nvl(cliente.correo, 'N')
                from (select A.DNI_CLI dni,
                             A.NOM_CLI nombre,
                             to_char(A.FEC_NAC_CLI, 'dd/mm/yyyy') fecha,

                             A.APE_PAT_CLI ape_pat,
                             A.APE_MAT_CLI ape_mat,
                             A.SEXO_CLI    sexo,
                             A.DIR_CLI     dir,
                             A.FONO_CLI    fono,
                             A.Email       correo
                        from pbl_cliente a
                       where dni_cli = vDni_cli_2) cliente,
                     (/*select r.le dni,
                             r.nombre_completo nombre,
                             to_char(to_date(r.fec_nac, 'yyyymmdd'),
                                     'dd/mm/yyyy') fecha,

                             null ape_pat,
                             null ape_mat,
                             NULL sexo,
                             null dir,
                             null fono,
                             null correo
                        from @pbl_dni_red@ r
                      --where  r.le LIKE cDNI_in||'%'
                       where r.le = vDni_cli_2*/
                        SELECT VAL_DNI dni,
                               VAL_NOMBRE nombre,
                               TO_CHAR(VAL_FECHA_NAC,'DD/MM/YYYY') fecha,
                               null ape_pat,
                               null ape_mat,
                               null sexo,
                               null dir,
                               null fono,
                               null correo
                        FROM   DUAL

                       ) reniec
               where cliente.dni(+) = reniec.dni;
          end if;

        end if;

      END IF;
    end if;

    RETURN curCamp;

  END FID_F_CUR_DATOS_EXISTE_DNI;

  /**************************************************************************/
  PROCEDURE FID_P_INSERT_TARJ_PED(pCodCia   IN CHAR,
                                  pCodLocal IN CHAR,
                                  pNumPed   IN VARCHAR2,
                                  pNumTarj  IN VARCHAR2,
                                  pDniCli   IN VARCHAR2,
                                  pCantDcto IN NUMBER,
                                  pIduUsu   IN VARCHAR2,

                                  ----------------------------------------
                                  --- dubilluz 21.05.2012
                                  cIndComision_in IN CHAR DEFAULT 'S',
                                  cCMP_in IN varchar2 DEFAULT 'N',
                                  cNombre_in IN varchar2 DEFAULT 'N',
                                  cDesc_Colegio_in IN varchar2 DEFAULT 'N',
                                  cTipColegio_in IN varchar2 DEFAULT 'N',
                                  cCodMedico_in IN varchar2 DEFAULT 'N'
                                  )

   AS
    --PRAGMA AUTONOMOUS_TRANSACTION;
    VCANT      number := 0;
    vDni_cli_2 varchar2(30);
  BEGIN

    --dubilluz 25.07.2011
    vDni_cli_2 := pDniCli;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    INSERT INTO FID_TARJETA_PEDIDO
      (COD_GRUPO_CIA,
       COD_LOCAL,
       NUM_PEDIDO,
       COD_TARJETA,
       DNI_CLI,
       CANT_DCTO,
       USU_CREA_TARJETA_PEDIDO,
       FEC_CREA_TARJETA_PEDIDO,
       USU_MOD_TARJETA_PEDIDO,
       FEC_MOD_TARJETA_PEDIDO)
    VALUES
      (pCodCia,
       pCodLocal,
       pNumPed,
       pNumTarj,
       vDni_cli_2,
       pCantDcto,
       pIduUsu,
       SYSDATE,
       NULL,
       NULL);

    --ACTUALIZANDO EL INDICADOR DE FIDELIZADO EN LA CABECERA DEL PEDIDO
    UPDATE VTA_PEDIDO_VTA_CAB C
       SET C.IND_FID = 'S', C.DNI_CLI = vDni_cli_2,
           -----------------------
            NUM_CMP  = cCMP_in,
            NOMBRE  = cNombre_in ,
            DESC_TIP_COLEGIO  = cDesc_Colegio_in,
            TIPO_COLEGIO  = cTipColegio_in,
            COD_MEDICO  = cCodMedico_in,
            COMISION_VTA = cIndComision_in
           -----------------------
     WHERE C.COD_GRUPO_CIA = pCodCia
       AND C.COD_LOCAL = pCodLocal
       AND C.NUM_PED_VTA = pNumPed;

    --- si NO COMISIONA --
    --- dubilluz 21.05.2012 --
    if cIndComision_in = 'N' then
        update vta_pedido_vta_det d
        set    d.PORC_ZAN  = 0
         WHERE d.COD_GRUPO_CIA = pCodCia
           AND d.COD_LOCAL = pCodLocal
           AND d.NUM_PED_VTA = pNumPed;
    end if;
    --- si NO COMISIONA --

    --  COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20999,
                              'ERROR AL INSERTAR FID_TARJETA_PEDIDO.' ||
                              SQLERRM);
      --       dbms_output.put_line('ERROR:'||SQLERRM);
    --ROLLBACK;
  END FID_P_INSERT_TARJ_PED;

  /*************************************************************************/
  FUNCTION FID_F_CUR_OBTIENE_CLI_IMPR(pCodCia   IN CHAR,
                                      pCodLocal IN CHAR,
                                      pNumPed   IN VARCHAR2) RETURN VARCHAR2 IS
    vNombre VARCHAR2(1000);
  BEGIN
    SELECT 'SR (A)' || ' ' || B.NOM_CLI || ' ' || B.APE_PAT_CLI || ' ' ||
           B.APE_MAT_CLI
      INTO vNombre
      FROM FID_TARJETA_PEDIDO A
     INNER JOIN PBL_CLIENTE B ON (A.DNI_CLI = B.DNI_CLI)
     WHERE B.IND_ESTADO = 'A'
       AND A.NUM_PEDIDO = pNumPed
       AND A.COD_LOCAL = pCodLocal
       AND A.COD_GRUPO_CIA = pCodCia;

    RETURN vNombre;
  END FID_F_CUR_OBTIENE_CLI_IMPR;

  /* ******************************************************************* */
  FUNCTION FID_F_VAR2_GET_PRECIO_PROD(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodCampana_in  IN CHAR,
                                      cCodProducto_in IN CHAR,
                                      cPrecioVenta    IN CHAR)
    return varchar2 is
    vResult varchar2(3000) := '10.2';
    nCant   number;

    nValorCupon      number;
    nMaxDscto        number;
    nPrecioActual    number;
    nNuevoPrecio_ini number;
    nCantExiteProd   number;

    nValorDescuento number;
  begin

    select count(1)
      into nCant
      from vta_campana_cupon c
     where c.cod_grupo_cia = cCodGrupoCia_in
       and c.ind_fid = 'S'
       and c.tip_campana = 'C'
       and c.tip_cupon = 'P'
          --JCORTEZ 19.10.09 cambio de logica, por IND_CADENA
       AND C.COD_CAMP_CUPON IN
           (SELECT *
              FROM (SELECT X.COD_CAMP_CUPON
                      FROM VTA_CAMPANA_CUPON X
                     WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND X.TIP_CAMPANA = 'C'
                       AND X.ESTADO = 'A'
                       AND X.IND_CADENA = 'S'
                    UNION
                    SELECT Y.COD_CAMP_CUPON
                      FROM VTA_CAMPANA_CUPON Y
                     WHERE Y.COD_GRUPO_CIA = cCodGrupoCia_in
                       AND Y.TIP_CAMPANA = 'C'
                       AND Y.ESTADO = 'A'
                       AND Y.IND_CADENA = 'N'
                       AND Y.COD_CAMP_CUPON IN
                           (SELECT COD_CAMP_CUPON
                              FROM VTA_CAMP_X_LOCAL Z
                             WHERE Z.COD_GRUPO_CIA = cCodGrupoCia_in
                               AND Z.COD_LOCAL = cCodLocal_in
                               AND Z.ESTADO = 'A')

                    ));

    if nCant > 0 then

      select count(1)
        into nCantExiteProd
        from vta_campana_prod_uso c
       where c.cod_grupo_cia = cCodGrupoCia_in
         and c.cod_camp_cupon = cCodCampana_in
         and c.cod_prod = cCodProducto_in;

      if nCantExiteProd > 0 then
        select c.valor_cupon, c.monto_max_desct
          into nValorCupon, nMaxDscto
          from vta_campana_cupon c
         where c.cod_grupo_cia = cCodGrupoCia_in
           and c.cod_camp_cupon = cCodCampana_in;

        select to_number(cPrecioVenta, '9999999999.00000')
          into nPrecioActual
          from dual;
        --MODIFICACION DUBILLUZ
        /* SELECT L.VAL_PREC_VTA
        INTO   nPrecioActual
        FROM   LGT_PROD_LOCAL L
        WHERE  L.COD_PROD = cCodProducto_in
        AND    L.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    L.COD_LOCAL = cCodLocal_in;*/

        nValorDescuento := nPrecioActual * (nValorCupon / 100);

        if nValorDescuento > nMaxDscto then
          nValorDescuento := nMaxDscto;
        end if;

        nNuevoPrecio_ini := nPrecioActual - nValorDescuento;

        select to_char(nNuevoPrecio_ini, '9999999990.00')
          into vResult
          from dual;

        vResult := trim(vResult);

      else
        vResult := 'N';
      end if;

    else
      vResult := 'N';
    end if;

    return vResult;
  end;

  /* ******************************************************************* */
  FUNCTION FID_F_VAR_VAL_DOC_IDEN return varchar2 is
    vIdTabGral number := 221;
    vDocVal    varchar2(500);

  begin
    BEGIN
      SELECT LLAVE_TAB_GRAL
        into vDocVal
        FROM PBL_TAB_GRAL TG
       WHERE TG.ID_TAB_GRAL = vIdTabGral;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vDocVal := '8';
    END;

    return vDocVal;

  end;

  /* ******************************************************************* */
  FUNCTION FID_F_VAR2_GET_PRECIO_NORMAL(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodProducto_in IN CHAR)
    return varchar2 is
    vResult varchar2(3000);

    nPrecioActual number;
  begin

    SELECT L.VAL_PREC_VTA
      INTO nPrecioActual
      FROM LGT_PROD_LOCAL L
     WHERE L.COD_PROD = cCodProducto_in
       AND L.COD_GRUPO_CIA = cCodGrupoCia_in
       AND L.COD_LOCAL = cCodLocal_in;

    select to_char(nPrecioActual, '9999999990.00') into vResult from dual;

    return vResult;
  end;

  /* ******************************************************************* */
  FUNCTION FID_F_LISTA_CAMPCL_USADOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cDni_cliente_in IN CHAR)
    RETURN FarmaCursor IS
    curCamp FarmaCursor;
  BEGIN

    OPEN curCamp FOR
      SELECT CL.COD_CAMP_CUPON
        FROM CL_CLI_CAMP CL
       WHERE CL.DNI_CLI = cDni_cliente_in
         AND CL.COD_GRUPO_CIA = cCodGrupoCia_in
         AND CL.MAX_USOS = CL.NRO_USOS;
    RETURN curCamp;

  END;
  /***************************************************************************/
  FUNCTION GENERA_EAN13(vCodigo_in IN VARCHAR2) RETURN CHAR IS
    cCodEan13 CHAR(13);
    vDig1     NUMBER(1);
    vDig2     NUMBER(1);
    vDig3     NUMBER(1);
    vDig4     NUMBER(1);
    vDig5     NUMBER(1);
    vDig6     NUMBER(1);
    vDig7     NUMBER(1);
    vDig8     NUMBER(1);
    vDig9     NUMBER(1);
    vDig10    NUMBER(1);
    vDig11    NUMBER(1);
    vDig12    NUMBER(1);
    vDig13    NUMBER(1);

    vSumImp NUMBER;
    vSumPar NUMBER;
    vAux    NUMBER;
    vAux2   NUMBER;

  BEGIN

    vDig1  := TO_NUMBER(SUBSTR(vCodigo_in, 1, 1));
    vDig2  := TO_NUMBER(SUBSTR(vCodigo_in, 2, 1));
    vDig3  := TO_NUMBER(SUBSTR(vCodigo_in, 3, 1));
    vDig4  := TO_NUMBER(SUBSTR(vCodigo_in, 4, 1));
    vDig5  := TO_NUMBER(SUBSTR(vCodigo_in, 5, 1));
    vDig6  := TO_NUMBER(SUBSTR(vCodigo_in, 6, 1));
    vDig7  := TO_NUMBER(SUBSTR(vCodigo_in, 7, 1));
    vDig8  := TO_NUMBER(SUBSTR(vCodigo_in, 8, 1));
    vDig9  := TO_NUMBER(SUBSTR(vCodigo_in, 9, 1));
    vDig10 := TO_NUMBER(SUBSTR(vCodigo_in, 10, 1));
    vDig11 := TO_NUMBER(SUBSTR(vCodigo_in, 11, 1));
    vDig12 := TO_NUMBER(SUBSTR(vCodigo_in, 12, 1));

    vSumImp := (vDig12 + vDig10 + vDig8 + vDig6 + vDig4 + vDig2) * 3;
    vSumPar := vDig11 + vDig9 + vDig7 + vDig5 + vDig3 + vDig1;

    vAux  := vSumImp + vSumPar;
    vAux2 := MOD(vAux, 10);
    --RAISE_APPLICATION_ERROR(-20005,vCodigo_in||'*'||vAux||'/'||vAux2);
    IF vAux2 = 0 THEN
      vDig13 := 0;
    ELSE
      vDig13 := 10 - vAux2;
    END IF;

    cCodEan13 := vCodigo_in || vDig13;

    RETURN cCodEan13;
  END;
  /***************************************************************************/

  FUNCTION FID_F_CHAR_CREA_NEW_TARJ_FID(vCodGrupoCia_in IN VARCHAR2,
                                        vCodLocal_in    IN VARCHAR2,
                                        vConcatenado_in IN VARCHAR2,
                                        vUsuID_in       IN VARCHAR2)
    RETURN CHAR AS
    vSecuencialNumera         CHAR(6);
    vConcatenado              CHAR(12);
    vNuevaTarjetaFidelizacion CHAR(13);
  BEGIN

    vSecuencialNumera         := TRIM(Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(vCodGrupoCia_in,
                                                                                                           vCodLocal_in,
                                                                                                           CC_COD_NUMERA),
                                                                          6,
                                                                          '0',
                                                                          'I'));
    vConcatenado              := vConcatenado_in || vSecuencialNumera;
    vNuevaTarjetaFidelizacion := GENERA_EAN13(vConcatenado);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(vCodGrupoCia_in,
                                               vCodLocal_in,
                                               CC_COD_NUMERA,
                                               vUsuID_in);

    FID_P_INSERTA_TARJETA_FID(vNuevaTarjetaFidelizacion,
                              vCodLocal_in,
                              vUsuID_in);

    RETURN vNuevaTarjetaFidelizacion;

  END;

  /***************************************************************************/

  PROCEDURE FID_P_INSERTA_TARJETA_FID(vNuevaTarjetaFidelizacion_in IN VARCHAR2,
                                      vCodLocal_in                 IN VARCHAR2,
                                      vUsuID_in                    IN VARCHAR2) AS
  PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
    INSERT INTO FID_TARJETA
      (COD_TARJETA, DNI_CLI, COD_LOCAL, USU_CREA_TARJETA, FEC_CREA_TARJETA)
    VALUES
      (vNuevaTarjetaFidelizacion_in,
       NULL,
       vCodLocal_in,
       vUsuID_in,
       SYSDATE);
    COMMIT;

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;

  END;
  /***************************************************************************/

  PROCEDURE FID_P_INSERTA_TARJETA_FID(vNuevaTarjetaFidelizacion_in IN VARCHAR2,
                                      vCodLocal_in                 IN VARCHAR2,
                                      vUsuID_in                    IN VARCHAR2,
                                      vDNI_in                      IN VARCHAR2) AS
    vDni_cli_2 varchar2(30);
  BEGIN

    --dubilluz 25.07.2011
    vDni_cli_2 := vDNI_in;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    INSERT INTO FID_TARJETA
      (COD_TARJETA, DNI_CLI, COD_LOCAL, USU_CREA_TARJETA, FEC_CREA_TARJETA)
    VALUES
      (vNuevaTarjetaFidelizacion_in,
       vDni_cli_2,
       vCodLocal_in,
       vUsuID_in,
       SYSDATE);
  END;
  /* ************************************************************************ */
  FUNCTION FID_F_CHAR_BUSCA_TARJETA(pCodTarjeta_in IN CHAR) RETURN CHAR IS
    vResultado char(1);
  BEGIN
    select decode(count(1), 0, 'N', 'S')
      into vResultado
      from fid_tarjeta t
     where t.cod_tarjeta = pCodTarjeta_in;

    return vResultado;

  END;
  /* ************************************************************************ */
  -- modificado dubilluz 09.06.2011
  FUNCTION FID_F_CUR_CAMP_X_TARJETA_NEW(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        cCodTarjeta_in       IN CHAR,
                                        cIndUsoEfectivo_in   IN CHAR,
                                        cIndUsoTarjeta_in    IN CHAR,
                                        cCodForma_Tarjeta_in IN CHAR)
    RETURN FarmaCursor IS

    curLista       FarmaCursor;
    nNumDia        VARCHAR(2);
    cSexo          char(1);
    dFecNaci       date;
    dDniCli        VARCHAR2(20);
    nListaNegraDNI number;
  BEGIN

    nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);

    IF cCodTarjeta_in IS NOT NULL THEN

      --OBTENIENDO EL SEXO Y LA FECHA DE NACIMIENTO DEL CLIENTE
      SELECT CL.SEXO_CLI, trunc(CL.FEC_NAC_CLI), CL.DNI_CLI
        INTO cSexo, dFecNaci, dDniCli
        FROM PBL_CLIENTE CL
       WHERE CL.DNI_CLI =
             (SELECT F.Dni_Cli
                FROM FID_TARJETA F
               WHERE F.COD_TARJETA = cCodTarjeta_in);
      select count(1)
        into nListaNegraDNI
        from fid_dni_nulos f
       where f.dni_cli = dDniCli
         and f.estado = 'A';
      -- dbms_output.put_line('datos del cliente : sexo:'||cSexo||', fecha_nac:'||dFecNaci);
      -- LISTADO DE LAS CAMPAÑAS DE FIDELIZACION

      OPEN curLista FOR
        SELECT C.COD_CAMP_CUPON AS COD_CAMP_CUPON,
               NVL(C.DESC_CUPON, ' ') AS DESC_CUPON,
               C.TIP_CUPON AS TIP_CUPON,
               --C.VALOR_CUPON AS VALOR_CUPON,
               -- CAMBIO de descuento de acuerdo a la forma de PAGO
               nvl(fpc.descuento_personalizado, c.valor_cupon) AS VALOR_CUPON, -- dubilluz 09.06.2011
               TRIM(to_char(NVL(C.MONT_MIN_USO, 0), '99999999.999')) AS MONT_MIN_USO,
               NVL(C.UNID_MIN_USO, 0) AS UNID_MIN_USO,
               NVL(C.UNID_MAX_PROD, 0) AS UNID_MAX_PROD,
               NVL(C.MONTO_MAX_DESCT, 0) AS MONTO_MAX_DESCT,
               'N' AS IND_MULTIUSO, --POR DEFECTO INDICADOR MULTIUSO COMO N
               C.IND_FID AS IND_FID,
               '' AS COD_CUPON,
               nvl(C.IND_VAL_COSTO_PROM, 'S') AS IND_VAL_COSTO_PROM,
               LPAD(C.PRIORIDAD, 8, '0') || LPAD(C.RANKING, 8, '0') ||
               TIP_CUPON || TO_CHAR(100000 - VALOR_CUPON, '00000.000') AS ORDEN
          FROM VTA_CAMPANA_CUPON C, --, FID_TARJETA T--, FID_CAMP_TARJETA CT
               (
                /*select distinct cf.cod_camp_cupon,cf.cod_grupo_cia,cf.porc_dcto descuento_personalizado
                                from   vta_camp_x_fpago_uso cf,
                                       vta_forma_pago f
                                where  f.cod_grupo_cia = cCodGrupoCia_in
                                and    f.est_forma_pago in ('A','X')
                                and    f.ind_forma_pago_efectivo = cIndUsoEfectivo_in
                                and    f.ind_tarj = cIndUsoTarjeta_in

                                and    ('NULL'=cCodForma_Tarjeta_in or ('T0000' = cCodForma_Tarjeta_in or f.cod_forma_pago = cCodForma_Tarjeta_in))
                                and    cf.cod_grupo_cia = f.cod_grupo_cia
                                and    cf.cod_forma_pago = f.cod_forma_pago
                                union
                                select a.cod_camp_cupon,a.cod_grupo_cia,a.valor_cupon descuento_personalizado
                                from   vta_campana_cupon a
                                where  not exists (select 1 from vta_camp_x_fpago_uso cx where cx.cod_grupo_cia = a.cod_grupo_cia
                                                                                         and   cx.cod_camp_cupon = a.cod_camp_cupon
                                                                                         and   cx.estado = 'A')*/
                select vh.cod_camp_cupon,
                        vh.cod_grupo_cia,
                        vh.descuento_personalizado
                  from (select distinct cf.cod_camp_cupon,
                                         cf.cod_grupo_cia,
                                         cf.porc_dcto descuento_personalizado,
                                         (case
                                           when 'T0000' = cCodForma_Tarjeta_in then
                                            case
                                           when IND_APLICA_TODAS_TARJETAS = 'S' then
                                            'S'
                                           else
                                            'N'
                                         end else 'S' end) ind
                           from vta_camp_x_fpago_uso cf, vta_forma_pago f
                          where f.cod_grupo_cia = cCodGrupoCia_in
                            and f.est_forma_pago in ('A', 'X')
                            and f.ind_forma_pago_efectivo = cIndUsoEfectivo_in
                            and f.ind_tarj = cIndUsoTarjeta_in
                            and ('NULL' = cCodForma_Tarjeta_in or
                                ('T0000' = cCodForma_Tarjeta_in or
                                f.cod_forma_pago = cCodForma_Tarjeta_in))
                            and cf.cod_grupo_cia = f.cod_grupo_cia
                            and cf.cod_forma_pago = f.cod_forma_pago) vh
                 where vh.ind = 'S'
                   and vh.descuento_personalizado > 0
                union
                select a.cod_camp_cupon,
                       a.cod_grupo_cia,
                       a.valor_cupon descuento_personalizado
                  from vta_campana_cupon a
                 where not exists (select 1
                          from vta_camp_x_fpago_uso cx
                         where cx.cod_grupo_cia = a.cod_grupo_cia
                           and cx.cod_camp_cupon = a.cod_camp_cupon
                           and cx.estado = 'A')) fpc
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.ESTADO = 'A'
           AND C.IND_FID IN ('S')
           AND C.TIP_CAMPANA = 'C'
           AND C.NUM_CUPON = 0
           AND nListaNegraDNI = 0
           -- dubilluz campañas que no son para colegio medido
           -- 06.12.2011
           and nvl(c.ind_tipo_colegio,'00') = '00'
           AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
              -- agregado el filtro de los campos de SEXO y EDAD
           AND (C.TIPO_SEXO_U IS NULL OR C.TIPO_SEXO_U = cSexo)
           AND (C.FEC_NAC_INICIO_U IS NULL OR
               C.FEC_NAC_INICIO_U <= dFecNaci)
           AND (C.FEC_NAC_FIN_U IS NULL OR C.FEC_NAC_FIN_U >= dFecNaci)
              --fin de filtro de los campos de Sexo y Edad
           AND C.COD_CAMP_CUPON IN
               ( /*SELECT *
                                FROM (SELECT *
                                        FROM (SELECT COD_CAMP_CUPON
                                                FROM VTA_CAMPANA_CUPON
                                              MINUS
                                              SELECT CL.COD_CAMP_CUPON
                                                FROM VTA_CAMP_X_LOCAL CL)
                                      UNION
                                      SELECT COD_CAMP_CUPON
                                        FROM VTA_CAMP_X_LOCAL
                                       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND COD_LOCAL = cCodLocal_in
                                         AND ESTADO = 'A')*/
                --JCORTEZ 19.10.09 cambio de logica
                SELECT *
                  FROM (SELECT X.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON X
                          WHERE X.COD_GRUPO_CIA = '001'
                            AND X.TIP_CAMPANA = 'C'
                            AND X.ESTADO = 'A'
                            AND X.IND_CADENA = 'S'
                         UNION
                         SELECT Y.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON Y
                          WHERE Y.COD_GRUPO_CIA = '001'
                            AND Y.TIP_CAMPANA = 'C'
                            AND Y.ESTADO = 'A'
                            AND Y.IND_CADENA = 'N'
                            AND Y.COD_CAMP_CUPON IN
                                (SELECT COD_CAMP_CUPON
                                   FROM VTA_CAMP_X_LOCAL Z
                                  WHERE Z.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND Z.COD_LOCAL = cCodLocal_in
                                    AND Z.ESTADO = 'A')

                         ))
           AND C.COD_CAMP_CUPON IN
               (SELECT *
                  FROM (SELECT *
                          FROM (SELECT COD_CAMP_CUPON
                                  FROM VTA_CAMPANA_CUPON
                                MINUS
                                SELECT H.COD_CAMP_CUPON FROM VTA_CAMP_HORA H)
                        UNION
                        SELECT H.COD_CAMP_CUPON
                          FROM VTA_CAMP_HORA H
                         WHERE TRIM(TO_CHAR(SYSDATE, 'HH24')) BETWEEN
                               H.HORA_INICIO AND H.HORA_FIN))
              --Quitando campañas que se encuentren en filtro para cierto tipo de tarjetas
              --dubilluz 31.03.2009
              /*AND C.COD_CAMP_CUPON IN
                                              (
                                                 (
                                                      SELECT COD_CAMP_CUPON
                                                      FROM   VTA_CAMPANA_CUPON d
                                                      where  d.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                                      MINUS
                                                      SELECT T.COD_CAMP_CUPON
                                                      FROM   VTA_CAMP_X_TARJETA T
                                                      where  t.cod_grupo_cia= cCodGrupoCia_in
                                                      and    t.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                                 )
                                                UNION
                                                select t.cod_camp_cupon
                                                from   vta_camp_x_tarjeta t,fid_tarjeta q
                                                where  q.dni_cli = dDniCli
                                                and    t.cod_camp_cupon= C.COD_CAMP_CUPON
                                                and    q.cod_tarjeta  between t.tarjeta_ini and t.tarjeta_fin
                                               )*/
           and ((exists (select 1
                           from vta_camp_x_tarjeta t, fid_tarjeta q
                          where q.dni_cli = dDniCli
                            and t.cod_grupo_cia = cCodGrupoCia_in
                            and t.cod_camp_cupon = C.COD_CAMP_CUPON
                            and q.cod_tarjeta between t.tarjeta_ini and
                                t.tarjeta_fin)

               ) or exists
                (SELECT 1
                   FROM VTA_CAMPANA_CUPON d
                  where d.cod_grupo_cia = cCodGrupoCia_in
                    and d.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                 MINUS
                 SELECT 1
                   FROM VTA_CAMP_X_TARJETA T
                  where t.cod_grupo_cia = cCodGrupoCia_in
                    and t.COD_CAMP_CUPON = C.COD_CAMP_CUPON))
           AND DECODE(C.DIA_SEMANA,
                      NULL,
                      'S',
                      DECODE(C.DIA_SEMANA,
                             REGEXP_REPLACE(C.DIA_SEMANA, nNumDia, 'S'),
                             'N',
                             'S')) = 'S'

              --QUITANTO LAS CAMPAÑAS LIMITADAS POR CANTIDAS DE USOS POR CLIENTE A LAS CAMPAÑAS AUTOMATICAS
              --JCALLO 13/02/2009
           AND C.COD_CAMP_CUPON NOT IN
               (SELECT COD_CAMP_CUPON
                  FROM CL_CLI_CAMP
                 WHERE DNI_CLI =
                       (SELECT F.Dni_Cli
                          FROM FID_TARJETA F
                         WHERE F.COD_TARJETA = cCodTarjeta_in)
                   AND MAX_USOS <= NRO_USOS)
              -- nueva condición para aquellos que colocaron las forma de pago USO.
              -- 09.06.2011  - DUBILLUZ
           and c.cod_grupo_cia = fpc.cod_grupo_cia
           and c.cod_camp_cupon = fpc.cod_camp_cupon
           --- dubilluz 24.05.2013
          /* AND C.COD_CAMP_CUPON  IN (
                                        select cab.cod_camp_cupon
                                        from vta_camp_valida_dni va,
                                           vta_campana_cupon cab
                                        where cab.cod_grupo_cia = va.cod_grupo_cia(+)
                                        and cab.cod_camp_cupon = va.cod_camp_cupon(+)
                                        AND REGEXP_LIKE(dDniCli, nvl(va.exp_regular, '^[0-9]+$'))
                                       )*/
           --- dubilluz 24.05.2013
        union
        -- jquispe 25.07.2011 cambio para agregar las campañas automaticas sin fidelizar
        SELECT C.COD_CAMP_CUPON AS COD_CAMP_CUPON,
               NVL(C.DESC_CUPON, ' ') AS DESC_CUPON,
               C.TIP_CUPON AS TIP_CUPON,
               --C.VALOR_CUPON AS VALOR_CUPON,
               -- CAMBIO de descuento de acuerdo a la forma de PAGO
               c.valor_cupon /*fpc.descuento_personalizado */ AS VALOR_CUPON, -- dubilluz 09.06.2011
               TRIM(to_char(NVL(C.MONT_MIN_USO, 0), '99999999.999')) AS MONT_MIN_USO,
               NVL(C.UNID_MIN_USO, 0) AS UNID_MIN_USO,
               NVL(C.UNID_MAX_PROD, 0) AS UNID_MAX_PROD,
               NVL(C.MONTO_MAX_DESCT, 0) AS MONTO_MAX_DESCT,
               'N' AS IND_MULTIUSO, --POR DEFECTO INDICADOR MULTIUSO COMO N
               C.IND_FID AS IND_FID,
               '' AS COD_CUPON,
               nvl(C.IND_VAL_COSTO_PROM, 'S') AS IND_VAL_COSTO_PROM,
               LPAD(C.PRIORIDAD, 8, '0') || LPAD(C.RANKING, 8, '0') ||
               TIP_CUPON || TO_CHAR(100000 - VALOR_CUPON, '00000.000') AS ORDEN
          FROM VTA_CAMPANA_CUPON C --, FID_TARJETA T--, FID_CAMP_TARJETA CT
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.ESTADO = 'A'
           AND C.IND_FID = 'N'
           AND C.TIP_CAMPANA = 'C'
           AND C.NUM_CUPON = 0
           -- dubilluz campañas que no son para colegio medido
           -- 06.12.2011
           and nvl(c.ind_tipo_colegio,'00') = '00'
              --AND nListaNegraDNI = 0
           AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
              -- agregado el filtro de los campos de SEXO y EDAD
              /*AND ( C.TIPO_SEXO_U IS NULL OR C.TIPO_SEXO_U = cSexo)
                       AND ( C.FEC_NAC_INICIO_U IS NULL OR C.FEC_NAC_INICIO_U <= dFecNaci )
                       AND ( C.FEC_NAC_FIN_U IS NULL OR C.FEC_NAC_FIN_U >= dFecNaci )*/
              --fin de filtro de los campos de Sexo y Edad
           AND C.COD_CAMP_CUPON IN
               ( /*SELECT *
                                FROM (SELECT *
                                        FROM (SELECT COD_CAMP_CUPON
                                                FROM VTA_CAMPANA_CUPON
                                              MINUS
                                              SELECT CL.COD_CAMP_CUPON
                                                FROM VTA_CAMP_X_LOCAL CL)
                                      UNION
                                      SELECT COD_CAMP_CUPON
                                        FROM VTA_CAMP_X_LOCAL
                                       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND COD_LOCAL = cCodLocal_in
                                         AND ESTADO = 'A')*/
                --JCORTEZ 19.10.09 cambio de logica
                SELECT *
                  FROM (SELECT X.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON X
                          WHERE X.COD_GRUPO_CIA = '001'
                            AND X.TIP_CAMPANA = 'C'
                            AND X.ESTADO = 'A'
                            AND X.IND_CADENA = 'S'
                         UNION
                         SELECT Y.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON Y
                          WHERE Y.COD_GRUPO_CIA = '001'
                            AND Y.TIP_CAMPANA = 'C'
                            AND Y.ESTADO = 'A'
                            AND Y.IND_CADENA = 'N'
                            AND Y.COD_CAMP_CUPON IN
                                (SELECT COD_CAMP_CUPON
                                   FROM VTA_CAMP_X_LOCAL Z
                                  WHERE Z.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND Z.COD_LOCAL = cCodLocal_in
                                    AND Z.ESTADO = 'A')

                         ))
           AND C.COD_CAMP_CUPON IN
               (SELECT *
                  FROM (SELECT *
                          FROM (SELECT COD_CAMP_CUPON
                                  FROM VTA_CAMPANA_CUPON
                                MINUS
                                SELECT H.COD_CAMP_CUPON FROM VTA_CAMP_HORA H)
                        UNION
                        SELECT H.COD_CAMP_CUPON
                          FROM VTA_CAMP_HORA H
                         WHERE TRIM(TO_CHAR(SYSDATE, 'HH24')) BETWEEN
                               H.HORA_INICIO AND H.HORA_FIN))
              --Quitando campañas que se encuentren en filtro para cierto tipo de tarjetas
              --dubilluz 31.03.2009
              /*AND C.COD_CAMP_CUPON IN
                                              (
                                                 (
                                                      SELECT COD_CAMP_CUPON
                                                      FROM   VTA_CAMPANA_CUPON d
                                                      where  d.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                                      MINUS
                                                      SELECT T.COD_CAMP_CUPON
                                                      FROM   VTA_CAMP_X_TARJETA T
                                                      where  t.cod_grupo_cia= cCodGrupoCia_in
                                                      and    t.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                                 )
                                                UNION
                                                select t.cod_camp_cupon
                                                from   vta_camp_x_tarjeta t,fid_tarjeta q
                                                where  q.dni_cli = dDniCli
                                                and    t.cod_camp_cupon= C.COD_CAMP_CUPON
                                                and    q.cod_tarjeta  between t.tarjeta_ini and t.tarjeta_fin
                                               )*/
              /*and ((exists (select 1
                                    from vta_camp_x_tarjeta t,fid_tarjeta q
                                    where  q.dni_cli =dDniCli
                                    and    t.cod_grupo_cia=cCodGrupoCia_in
                                    and    t.cod_camp_cupon= C.COD_CAMP_CUPON
                                    and    q.cod_tarjeta  between t.tarjeta_ini and t.tarjeta_fin)

                          )
                          or
                          exists (
                                                      SELECT 1
                                                      FROM   VTA_CAMPANA_CUPON d
                                                      where  d.cod_grupo_cia=cCodGrupoCia_in
                                                      and    d.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                                      MINUS
                                                      SELECT 1
                                                      FROM   VTA_CAMP_X_TARJETA T
                                                      where  t.cod_grupo_cia= cCodGrupoCia_in
                                                      and    t.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                          ) )*/
           AND DECODE(C.DIA_SEMANA,
                      NULL,
                      'S',
                      DECODE(C.DIA_SEMANA,
                             REGEXP_REPLACE(C.DIA_SEMANA, nNumDia, 'S'),
                             'N',
                             'S')) = 'S'
           --- dubilluz 24.05.2013
           /*AND C.COD_CAMP_CUPON       IN (
                                        select cab.cod_camp_cupon
                                        from vta_camp_valida_dni va,
                                           vta_campana_cupon cab
                                        where cab.cod_grupo_cia = va.cod_grupo_cia(+)
                                        and cab.cod_camp_cupon = va.cod_camp_cupon(+)
                                        AND REGEXP_LIKE(dDniCli, nvl(va.exp_regular, '^[0-9]+$'))
                                       )*/
           --- dubilluz 24.05.2013

        --QUITANTO LAS CAMPAÑAS LIMITADAS POR CANTIDAS DE USOS POR CLIENTE A LAS CAMPAÑAS AUTOMATICAS
        --JCALLO 13/02/2009
        /*AND C.COD_CAMP_CUPON NOT IN ( SELECT COD_CAMP_CUPON
                                               FROM CL_CLI_CAMP
                                               WHERE DNI_CLI       = ( SELECT F.Dni_Cli FROM FID_TARJETA F
                                                                       WHERE F.COD_TARJETA = cCodTarjeta_in )
                                               AND   MAX_USOS      <= NRO_USOS )*/
        -- nueva condición para aquellos que colocaron las forma de pago USO.

        -- 09.06.2011  - DUBILLUZ
        /*and  c.cod_grupo_cia = fpc.cod_grupo_cia
                 and  c.cod_camp_cupon = fpc.cod_camp_cupon*/

        ;

    ELSE

      OPEN curLista FOR
      -- jquispe 25.07.2011 cambio para agregar las campañas automaticas sin fidelizar
        SELECT C.COD_CAMP_CUPON AS COD_CAMP_CUPON,
               NVL(C.DESC_CUPON, ' ') AS DESC_CUPON,
               C.TIP_CUPON AS TIP_CUPON,
               --C.VALOR_CUPON AS VALOR_CUPON,
               -- CAMBIO de descuento de acuerdo a la forma de PAGO
               c.valor_cupon /*fpc.descuento_personalizado */ AS VALOR_CUPON, -- dubilluz 09.06.2011
               TRIM(to_char(NVL(C.MONT_MIN_USO, 0), '99999999.999')) AS MONT_MIN_USO,
               NVL(C.UNID_MIN_USO, 0) AS UNID_MIN_USO,
               NVL(C.UNID_MAX_PROD, 0) AS UNID_MAX_PROD,
               NVL(C.MONTO_MAX_DESCT, 0) AS MONTO_MAX_DESCT,
               'N' AS IND_MULTIUSO, --POR DEFECTO INDICADOR MULTIUSO COMO N
               C.IND_FID AS IND_FID,
               '' /*c.cod_camp_cupon*/ AS COD_CUPON,
               nvl(C.IND_VAL_COSTO_PROM, 'S') AS IND_VAL_COSTO_PROM,
               LPAD(C.PRIORIDAD, 8, '0') || LPAD(C.RANKING, 8, '0') ||
               TIP_CUPON || TO_CHAR(100000 - VALOR_CUPON, '00000.000') AS ORDEN
          FROM VTA_CAMPANA_CUPON C --, FID_TARJETA T--, FID_CAMP_TARJETA CT
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.ESTADO = 'A'
           AND C.IND_FID = 'N'
           AND C.TIP_CAMPANA = 'C'
           AND C.NUM_CUPON = 0
           -- dubilluz campañas que no son para colegio medido
           -- 06.12.2011
           and nvl(c.ind_tipo_colegio,'00') = '00'
              --AND nListaNegraDNI = 0
           AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
              -- agregado el filtro de los campos de SEXO y EDAD
              /*AND ( C.TIPO_SEXO_U IS NULL OR C.TIPO_SEXO_U = cSexo)
                       AND ( C.FEC_NAC_INICIO_U IS NULL OR C.FEC_NAC_INICIO_U <= dFecNaci )
                       AND ( C.FEC_NAC_FIN_U IS NULL OR C.FEC_NAC_FIN_U >= dFecNaci )*/
              --fin de filtro de los campos de Sexo y Edad
           AND C.COD_CAMP_CUPON IN
               ( /*SELECT *
                                FROM (SELECT *
                                        FROM (SELECT COD_CAMP_CUPON
                                                FROM VTA_CAMPANA_CUPON
                                              MINUS
                                              SELECT CL.COD_CAMP_CUPON
                                                FROM VTA_CAMP_X_LOCAL CL)
                                      UNION
                                      SELECT COD_CAMP_CUPON
                                        FROM VTA_CAMP_X_LOCAL
                                       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND COD_LOCAL = cCodLocal_in
                                         AND ESTADO = 'A')*/
                --JCORTEZ 19.10.09 cambio de logica
                SELECT *
                  FROM (SELECT X.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON X
                          WHERE X.COD_GRUPO_CIA = '001'
                            AND X.TIP_CAMPANA = 'C'
                            AND X.ESTADO = 'A'
                            AND X.IND_CADENA = 'S'
                         UNION
                         SELECT Y.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON Y
                          WHERE Y.COD_GRUPO_CIA = '001'
                            AND Y.TIP_CAMPANA = 'C'
                            AND Y.ESTADO = 'A'
                            AND Y.IND_CADENA = 'N'
                            AND Y.COD_CAMP_CUPON IN
                                (SELECT COD_CAMP_CUPON
                                   FROM VTA_CAMP_X_LOCAL Z
                                  WHERE Z.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND Z.COD_LOCAL = cCodLocal_in
                                    AND Z.ESTADO = 'A')

                         ))
           AND C.COD_CAMP_CUPON IN
               (SELECT *
                  FROM (SELECT *
                          FROM (SELECT COD_CAMP_CUPON
                                  FROM VTA_CAMPANA_CUPON
                                MINUS
                                SELECT H.COD_CAMP_CUPON FROM VTA_CAMP_HORA H)
                        UNION
                        SELECT H.COD_CAMP_CUPON
                          FROM VTA_CAMP_HORA H
                         WHERE TRIM(TO_CHAR(SYSDATE, 'HH24')) BETWEEN
                               H.HORA_INICIO AND H.HORA_FIN))
              --Quitando campañas que se encuentren en filtro para cierto tipo de tarjetas
              --dubilluz 31.03.2009
              /*AND C.COD_CAMP_CUPON IN
                                              (
                                                 (
                                                      SELECT COD_CAMP_CUPON
                                                      FROM   VTA_CAMPANA_CUPON d
                                                      where  d.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                                      MINUS
                                                      SELECT T.COD_CAMP_CUPON
                                                      FROM   VTA_CAMP_X_TARJETA T
                                                      where  t.cod_grupo_cia= cCodGrupoCia_in
                                                      and    t.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                                 )
                                                UNION
                                                select t.cod_camp_cupon
                                                from   vta_camp_x_tarjeta t,fid_tarjeta q
                                                where  q.dni_cli = dDniCli
                                                and    t.cod_camp_cupon= C.COD_CAMP_CUPON
                                                and    q.cod_tarjeta  between t.tarjeta_ini and t.tarjeta_fin
                                               )*/
              /*and ((exists (select 1
                                    from vta_camp_x_tarjeta t,fid_tarjeta q
                                    where  q.dni_cli =dDniCli
                                    and    t.cod_grupo_cia=cCodGrupoCia_in
                                    and    t.cod_camp_cupon= C.COD_CAMP_CUPON
                                    and    q.cod_tarjeta  between t.tarjeta_ini and t.tarjeta_fin)

                          )
                          or
                          exists (
                                                      SELECT 1
                                                      FROM   VTA_CAMPANA_CUPON d
                                                      where  d.cod_grupo_cia=cCodGrupoCia_in
                                                      and    d.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                                      MINUS
                                                      SELECT 1
                                                      FROM   VTA_CAMP_X_TARJETA T
                                                      where  t.cod_grupo_cia= cCodGrupoCia_in
                                                      and    t.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                          ) )*/
           AND DECODE(C.DIA_SEMANA,
                      NULL,
                      'S',
                      DECODE(C.DIA_SEMANA,
                             REGEXP_REPLACE(C.DIA_SEMANA, nNumDia, 'S'),
                             'N',
                             'S')) = 'S'

        --QUITANTO LAS CAMPAÑAS LIMITADAS POR CANTIDAS DE USOS POR CLIENTE A LAS CAMPAÑAS AUTOMATICAS
        --JCALLO 13/02/2009
        /*AND C.COD_CAMP_CUPON NOT IN ( SELECT COD_CAMP_CUPON
                                               FROM CL_CLI_CAMP
                                               WHERE DNI_CLI       = ( SELECT F.Dni_Cli FROM FID_TARJETA F
                                                                       WHERE F.COD_TARJETA = cCodTarjeta_in )
                                               AND   MAX_USOS      <= NRO_USOS )*/
        -- nueva condición para aquellos que colocaron las forma de pago USO.

        -- 09.06.2011  - DUBILLUZ
        /*and  c.cod_grupo_cia = fpc.cod_grupo_cia
                 and  c.cod_camp_cupon = fpc.cod_camp_cupon*/
        ;

    END IF;
    --fin de condicion
    RETURN curLista;

  END FID_F_CUR_CAMP_X_TARJETA_NEW;
  /***************************************************************************/
  FUNCTION FID_F_CHAR_IS_DNI_ANULADO(vCodGrupoCia_in IN VARCHAR2,
                                     vCodLocal_in    IN VARCHAR2,
                                     vDNI_in         IN VARCHAR2) RETURN CHAR AS
    vResultado CHAR(1);
    vDni_cli_2 varchar2(30);
  BEGIN

    --dubilluz 25.07.2011
    vDni_cli_2 := vDNI_in;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    select decode(count(1), 0, 'S', 'N')
      into vResultado -- 'S' DNI permite descuento
    -- 'N' DNI NO PERMITE
      from FID_DNI_NULOS d
     where d.dni_cli = vDni_cli_2
       and d.estado = 'A';

    RETURN vResultado;

  END;

  /***************************************************************************/
  FUNCTION FID_F_CHAR_IS_RUC_ANULADO(vCodGrupoCia_in IN VARCHAR2,
                                     vCodLocal_in    IN VARCHAR2,
                                     vRUC_in         IN VARCHAR2) RETURN CHAR AS
    vResultado CHAR(1);
  BEGIN

    select decode(count(1), 0, 'S', 'N')
      into vResultado -- 'S' DNI permite descuento
    -- 'N' DNI NO PERMITE
      from FID_RUC_NULOS d
     where d.RUC = vRUC_in
       and d.estado = 'A';

    RETURN vResultado;

  END;
  /***************************************************************************/
  /*FUNCTION FID_F_NUM_AHR_PER_DNI(vCodGrupoCia_in IN VARCHAR2,
                                 vCodLocal_in    IN VARCHAR2,
                                 vDNI_in         IN VARCHAR2,
                                 vTarj_in        IN VARCHAR2 default 'X') RETURN number AS
    vResultado number;
    vDni_cli_2 varchar2(30);
    pTarjEspecial number;
  BEGIN

    if vTarj_in != 'X' then

    SELECT COUNT(1)
    INTO   pTarjEspecial
    FROM   FID_TARJ_CAMP_ESPECIAL F
    WHERE  F.COD_GRUPO_CIA = vCodGrupoCia_in
    AND    F.COD_TARJETA = vCodLocal_in
    AND    F.ESTADO = vTarj_in
    and    f.estado = 'A';

    else
       pTarjEspecial := 0;
    end if;
    --dubilluz 25.07.2011
    vDni_cli_2 := vDNI_in;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    if pTarjEspecial = 0 then

        select nvl(sum(v.val_dcto_vta), 0)
          into vResultado
          from vta_ped_dcto_cli_aux v
         where v.dni_cliente = vDni_cli_2
           and v.fec_crea_ped_vta_cab between trunc(sysdate) and
               trunc(sysdate + 1) - 1 / 59000;
    else
          select nvl(sum(v.val_dcto_vta), 0)
          into vResultado
          from vta_ped_dcto_cli_aux v
         where v.fec_crea_ped_vta_cab between trunc(sysdate) and
               trunc(sysdate + 1) - 1 / 59000
         and   (v.cod_grupo_cia,v.cod_local,v.num_ped_vta) in
                                  (
                                  select ft.cod_grupo_cia,ft.cod_local,ft.num_pedido
                                  from   fid_tarjeta_pedido ft
                                  where  ft.cod_grupo_cia = vCodGrupoCia_in
                                  and    ft.cod_tarjeta = vTarj_in
                                  );


    end if;
    RETURN vResultado;

  END;*/

  FUNCTION FID_F_NUM_AHR_PER_DNI(vCodGrupoCia_in IN VARCHAR2,
                                 vCodLocal_in    IN VARCHAR2,
                                 vDNI_in         IN VARCHAR2,
                                 vTarj_in        IN VARCHAR2 default 'X') RETURN number AS
    vResultado number;
    vDni_cli_2 varchar2(30);
    vTarj_aplica_camp varchar2(1);
    pTarjEspecial number;
    vCodCampana varchar2(10);
    vDcto_unico_x_tarj_new varchar2(30);


    cCodFormaPago varchar2(200);

  BEGIN
    IF vTarj_in != 'X' THEN
       SELECT COUNT(1)
       INTO   pTarjEspecial
       FROM   FID_TARJ_CAMP_ESPECIAL F
       WHERE  F.COD_GRUPO_CIA = vCodGrupoCia_in
       AND    F.COD_TARJETA = vCodLocal_in
       AND    F.ESTADO = vTarj_in
       and    f.estado = 'A';
    ELSE
       pTarjEspecial := 0;
    END IF;

    vDni_cli_2 := vDNI_in;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    IF pTarjEspecial = 0 THEN

        vTarj_aplica_camp := FID_IS_TARJETA_APLICA_CAMPANA(vCodGrupoCia_in,
                                                           vCodLocal_in,
                                                           vTarj_in);

        IF(vTarj_aplica_camp = 'N') THEN
            select nvl(sum(v.val_dcto_vta), 0)
            into vResultado
            from vta_ped_dcto_cli_aux v
            where v.dni_cliente = vDni_cli_2
            and v.fec_crea_ped_vta_cab between trunc(sysdate) and
               trunc(sysdate + 1) - 1 / 59000
 -- DUBILLUZ 07.11.2012
                   and (v.cod_grupo_cia,v.cod_local,v.num_ped_vta) not in

                                          	(select va.COD_GRUPO_CIA,va.COD_LOCAL,va.num_pedido
                          r                  from   (
                                                    select t.cod_grupo_cia,t.cod_local,t.num_pedido,t.cod_tarjeta,t.dni_cli,
                                                           ptoventa_fidelizacion.fid_f_forma_pago_tarjeta(t.cod_tarjeta) V_FORMA_PAGO
                                                    from   fid_tarjeta_pedido t,
                                                           vta_pedido_vta_cab cab
                                                    where  t.dni_cli = vDni_cli_2
                                                    and    cab.cod_grupo_cia = vCodGrupoCia_in
                                                    and    cab.cod_local = vCodLocal_in
                                                    and    cab.fec_ped_vta  between trunc(sysdate) and trunc(sysdate + 1) - 1/24/60/60
                                                    and    t.cod_grupo_cia = cab.cod_grupo_cia
                                                    and    t.cod_local = cab.cod_local
                                                    and    t.num_pedido = cab.num_ped_vta
                                                   ) va,
                                                   vta_forma_pago f
                                            where  va.v_forma_pago != '*'
                                            and    f.ind_ahorro_x_fp = 'S'
                                            and    f.cod_grupo_cia = va.cod_grupo_cia
                                            and    va.v_forma_pago = f.cod_forma_pago
                                            );
        ELSE
              /*vCodCampana := FID_F_CAMP_X_TARJ_VISA(vCodGrupoCia_in,
                                                    vCodLocal_in,
                                                    vTarj_in);

                   select dcto_unico_x_tarj
                   into vDcto_unico_x_tarj_new
                   from vta_campana_cupon
                   where cod_grupo_cia= vCodGrupoCia_in
                   and cod_camp_cupon=vCodCampana;*/

              -- DUBILLUZ 07.11.2012
                cCodFormaPago :=  ptoventa_fidelizacion.fid_f_forma_pago_tarjeta(vTarj_in);
                select NVL(IND_AHORRO_X_FP,'N')
                into   vDcto_unico_x_tarj_new
                from   vta_forma_pago f
                where  f.cod_grupo_cia = vCodGrupoCia_in
                and    f.cod_forma_pago = cCodFormaPago ;
              -- DUBILLUZ 07.11.2012

              IF(vDcto_unico_x_tarj_new = 'N') THEN
                   select nvl(sum(v.val_dcto_vta), 0)
                   into vResultado
                   from vta_ped_dcto_cli_aux v
                   where v.dni_cliente = vDni_cli_2
                   and v.fec_crea_ped_vta_cab between trunc(sysdate)  and trunc(sysdate + 1) - 1 / 59000
                   -- DUBILLUZ 07.11.2012
                   and (v.cod_grupo_cia,v.cod_local,v.num_ped_vta) not in
                                          	(select va.COD_GRUPO_CIA,va.COD_LOCAL,va.num_pedido
                                            from   (
                                                    select t.cod_grupo_cia,t.cod_local,t.num_pedido,t.cod_tarjeta,t.dni_cli,
                                                           ptoventa_fidelizacion.fid_f_forma_pago_tarjeta(t.cod_tarjeta) V_FORMA_PAGO
                                                    from   fid_tarjeta_pedido t,
                                                           vta_pedido_vta_cab cab
                                                    where  t.dni_cli = vDni_cli_2
                                                    and    cab.cod_grupo_cia = vCodGrupoCia_in
                                                    and    cab.cod_local = vCodLocal_in
                                                    and    cab.fec_ped_vta  between trunc(sysdate) and trunc(sysdate + 1) - 1/24/60/60
                                                    and    t.cod_grupo_cia = cab.cod_grupo_cia
                                                    and    t.cod_local = cab.cod_local
                                                    and    t.num_pedido = cab.num_ped_vta
                                                   ) va,
                                                   vta_forma_pago f
                                            where  va.v_forma_pago != '*'
                                            and    f.ind_ahorro_x_fp = 'S'
                                            and    f.cod_grupo_cia = va.cod_grupo_cia
                                            and    va.v_forma_pago = f.cod_forma_pago
                                            );

              ELSE
                    select nvl(sum(v.val_dcto_vta), 0)
                    into vResultado
                    from vta_ped_dcto_cli_aux v
                    where v.fec_crea_ped_vta_cab between trunc(sysdate)
                    and trunc(sysdate + 1) - 1 / 59000
                    and (v.cod_grupo_cia,v.cod_local,v.num_ped_vta) in
                                  (select ft.cod_grupo_cia,ft.cod_local,ft.num_pedido
                                   from   fid_tarjeta_pedido ft
                                   where  ft.cod_grupo_cia = vCodGrupoCia_in
                                   and    ft.cod_tarjeta = vTarj_in);

               END IF;

        END IF;
    ELSE
            select nvl(sum(v.val_dcto_vta), 0)
            into vResultado
            from vta_ped_dcto_cli_aux v;
    END IF;

    RETURN vResultado;

  END;
  /***************************************************************************/
  FUNCTION FID_F_NUM_MAX_AHR_PER_DNI(vCodGrupoCia_in IN VARCHAR2,
                                     vCodLocal_in    IN VARCHAR2,
                                     vDNI_in         IN VARCHAR2 DEFAULT NULL)
    RETURN number AS
    vResultado  number;
    vMaxDctoDNI PBL_DCTO_X_DNI.MAX_DCTO%TYPE;
    vDni_cli_2  varchar2(30);
  BEGIN

    --dubilluz 25.07.2011
    vDni_cli_2 := vDNI_in;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    -- 2009-11-30 JOLIVA: Se verifica si el DNI tiene descuento personalizado
    vMaxDctoDNI := 0;
    begin
      SELECT MAX_DCTO
        INTO vMaxDctoDNI
        FROM PBL_DCTO_X_DNI
       WHERE DNI_CLI = vDni_cli_2;
    exception
      when no_data_found then
        vMaxDctoDNI := 0;
    end;

    --maximo de descuento x Dia de DNI
    --vResultado := 10;
    begin
      select t.llave_tab_gral
        into vResultado
        from pbl_tab_gral t
       where t.id_tab_gral = 274;
    exception
      when no_data_found then
        vResultado := 9999;
    end;

    IF vMaxDctoDNI > vResultado THEN
      vResultado := vMaxDctoDNI;
    END IF;

    RETURN vResultado;

  END;
  /***************************************************************************/
  FUNCTION FID_F_NUM_MAX_UNID_PROD_CAMP(vCodGrupoCia_in IN char,
                                        vCodLocal_in    IN char,
                                        vCodCampana_in  IN char,
                                        vCodProd_in     IN char)
    RETURN number AS
    vResultado number;
  BEGIN

    begin
      select MAX_UNID_DCTO
        into vResultado
        from vta_campana_prod_uso c
       where c.cod_grupo_cia = vCodGrupoCia_in
         and c.cod_camp_cupon = vCodCampana_in
         and c.cod_prod = vCodProd_in;

      if vResultado is null then
        select cc.unid_max_prod
          into vResultado
          from vta_campana_cupon cc
         where cc.cod_grupo_cia = vCodGrupoCia_in
           and cc.cod_camp_cupon = vCodCampana_in;
      end if;

    exception
      when no_data_found then

        select cc.unid_max_prod
          into vResultado
          from vta_campana_cupon cc
         where cc.cod_grupo_cia = vCodGrupoCia_in
           and cc.cod_camp_cupon = vCodCampana_in;
    end;

    RETURN vResultado;

  END;
  /***************************************************************************/
  FUNCTION FID_F_CHAR_VALIDA_PED_FID(vCodGrupoCia_in IN CHAR,
                                     vCodLocal_in    IN CHAR,
                                     vNumPedVta_in   IN CHAR,
                                     --vDNI_in         IN CHAR,
                                     vRUC_in IN CHAR,
                                     vTarj_in IN CHAR default 'X') RETURN CHAR AS
    vResultado    CHAR(100);
    vDni_in       VARCHAR2(10);
    vAhorroPedido number;
  BEGIN

    begin
      select t.dni_cli
        into vDni_in
        from fid_tarjeta_pedido t
       where t.cod_grupo_cia = vCodGrupoCia_in
         and t.cod_local = vCodLocal_in
         and t.num_pedido = vNumPedVta_in;
    exception
      when no_data_found then
        vDni_in := 'N';
    end;

    if vDni_in != 'N' then
      select sum(round(d.ahorro, 10))
      --select sum(d.ahorro)
        into vAhorroPedido
        from vta_pedido_vta_det d
       where d.cod_grupo_cia = vCodGrupoCia_in
         and d.cod_local = vCodLocal_in
         and d.num_ped_vta = vNumPedVta_in;
      IF vAhorroPedido > 0 THEN

        dbms_output.put_line('vDni_in:' || vDni_in);
        select decode(count(1), 0, 'S', 'N_DNI')
          into vResultado -- 'S' DNI permite descuento
        -- 'N' DNI NO PERMITE
          from FID_DNI_NULOS d
         where d.dni_cli = vDni_in
           and d.estado = 'A';
        dbms_output.put_line('vResultado:' || vResultado);
        if vResultado = 'S' then

          dbms_output.put_line('vAhorroPedido:' || vAhorroPedido);
          dbms_output.put_line('Uso:' ||
                               FID_F_NUM_AHR_PER_DNI(vCodGrupoCia_in,
                                                     vCodLocal_in,
                                                     vDni_in,
                                                     vTarj_in
                                                     ));
          --vAhorroPedido := trunc(vAhorroPedido);
          --vAhorroPedido:= vAhorroPedido + trunc(FID_F_NUM_AHR_PER_DNI(vCodGrupoCia_in,vCodLocal_in,vDni_in));
          vAhorroPedido := vAhorroPedido +
                           FID_F_NUM_AHR_PER_DNI(vCodGrupoCia_in,
                                                 vCodLocal_in,
                                                 vDni_in,
                                                 vTarj_in
                                                 );

          --if FID_F_NUM_MAX_AHR_PER_DNI(vCodGrupoCia_in,
          if FID_F_MAX_AHORRO_DIARIO(vCodGrupoCia_in,
                                       vCodLocal_in,
                                       vDni_in,
                                       vTarj_in
                                       ) >= vAhorroPedido then

            if Length(trim(vRUC_in)) > 0 then
              select decode(count(1), 0, 'S', 'N_RUC')
                into vResultado -- 'S' DNI permite descuento
              -- 'N' DNI NO PERMITE
                from FID_RUC_NULOS r
               where r.ruc = vRUC_in
                 and r.estado = 'A';
            else
              vResultado := 'S'; --Validacion Exitosa
            end if;

          else
            vResultado := 'N_DCTO';
          end if;

        end if;
      ELSE
        vResultado := 'S'; --Validacion Exitosa
      END IF;
    else
      vResultado := 'S'; --Validacion Exitosa
    end if;

    RETURN trim(vResultado);

  END;
  /***************************************************************************/

  /*************************************************************************/
  FUNCTION FID_F_CUR_OBTIENE_TARJ_CLI(pCodLocal IN CHAR, pDNI IN CHAR)
    RETURN FarmaCursor IS
    curCamp    FarmaCursor;
    cant       NUMBER;
    vDni_cli_2 varchar2(30);
  BEGIN

    /*SELECT  COUNT(*) INTO cant
    FROM  FID_TARJETA X
    WHERE TRIM(X.DNI_CLI)=TRIM(pDNI)
    AND ROWNUM<2--solo una tarjeta;
    ORDER BY X.FEC_CREA_TARJETA ASC;*/

    --dubilluz 25.07.2011
    vDni_cli_2 := pDNI;
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);

    SELECT COUNT(*)
      INTO cant
      FROM FID_TARJETA X
     WHERE X.DNI_CLI = TRIM(vDni_cli_2)
       AND ROWNUM < 2 --solo una tarjeta;
     ORDER BY X.FEC_CREA_TARJETA ASC;

    IF cant > 0 THEN
      OPEN curCamp FOR
        SELECT X.COD_TARJETA || 'Ã ' || X.DNI_CLI
        --|| 'Ã ' ||
          FROM FID_TARJETA X
        --WHERE TRIM(X.DNI_CLI)=TRIM(pDNI)
         WHERE X.DNI_CLI = TRIM(vDni_cli_2)
              --AND X.COD_LOCAL=pCodLocal
           AND ROWNUM < 2 --solo una tarjeta;
         ORDER BY X.FEC_CREA_TARJETA ASC;
    else
      OPEN curCamp FOR
        select 1 || 'Ã' || 2 from dual where 1 = 2;

    END IF;

    RETURN curCamp;
  END;
  /* ***************************************************************************** */
  FUNCTION VTA_F_CUPON_CLI(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                           cDni            IN CHAR) RETURN FarmaCursor AS
    curCupon     FarmaCursor;
    v_vDescLocal VARCHAR2(120);
  BEGIN

    OPEN curCupon FOR
      SELECT NULL FROM DUAL;
    /*SELECT  V1.DESC_CUPON || 'Ã' ||
            V1.VALOR_CUPON|| 'Ã' ||
            TO_CHAR(V1.FEC_EMI,'DD/MM/YYYY') || 'Ã' ||
            V1.COD_CUPON|| 'Ã' ||
            V1.TEXTO
    FROM (SELECT ROWNUM,A.DESC_CUPON DESC_CUPON,
            A.VALOR_CUPON ||''|| CASE WHEN A.TIP_CUPON='P' THEN '%' ELSE 's/.' END VALOR_CUPON,
            CP.FEC_CREA_CUP_CAB FEC_EMI,CP.COD_CUPON COD_CUPON,
            A.VALOR_CUPON||''|| CASE WHEN A.TIP_CUPON='P' THEN ' %' ELSE ' s/.' END
            ||' Descuento.'||CHR(13)|| CHR(10)||A.TEXTO_LARGO TEXTO
     FROM   VTA_CAMP_PEDIDO_CUPON C,
            FID_TARJETA_PEDIDO T,
            VTA_CUPON CP,
            VTA_CAMPANA_CUPON A
     WHERE  t.cod_grupo_cia = cCodGrupoCia_in
     and    t.cod_local = cCodLocal_in
     and    t.dni_cli = cDni
     and    c.estado = 'E'
     and    c.ind_impr = INDICADOR_SI
     and    cp.estado = ESTADO_ACTIVO
     and    cp.fec_crea_cup_cab BETWEEN TRUNC(SYSDATE - (SELECT LLAVE_TAB_GRAL FROM  PBL_TAB_GRAL WHERE ID_TAB_GRAL=285))
                                    AND TRUNC(SYSDATE+1)-1/24/60/60
     and    t.cod_grupo_cia = c.cod_grupo_cia
     and    t.cod_local = c.cod_local
     and    t.num_pedido = c.num_ped_vta
     AND    c.cod_grupo_cia = cp.cod_grupo_cia
     and    c.cod_cupon = cp.cod_cupon
     AND    CP.COD_GRUPO_CIA=A.COD_GRUPO_CIA
     AND    CP.COD_CAMPANA=A.COD_CAMP_CUPON
     AND    ROWNUM<2 --Se muestra solo uno por campaña
     ORDER BY A.VALOR_CUPON,A.PRIORIDAD DESC)V1;*/

    RETURN curCupon;
  END;

  /* ***************************************************************************** */
  PROCEDURE FID_P_INSERT_CA_CAMP_CLI(cCodGrupoCia_in IN CHAR,
                                     cDni_in         IN CHAR) AS
    PRAGMA AUTONOMOUS_TRANSACTION;
    vCount NUMBER;
  BEGIN

    IF trim(cDni_in) is not NULL THEN

      dbms_output.put_line('vCount ' || vCount);
      SELECT COUNT(*)
        INTO vCount
        FROM (SELECT C.COD_CAMP_CUPON
                FROM VTA_CAMPANA_CUPON C
               WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND C.TIP_CAMPANA = 'A'
                 AND C.ESTADO = 'A'
                 AND C.FECH_FIN >= SYSDATE
              MINUS
              SELECT CA.COD_CAMP_CUPON
                FROM CA_CLI_CAMP CA
               WHERE CA.DNI_CLI = cDni_in);

      IF (vCount > 0) THEN
        INSERT INTO CA_CLI_CAMP
          (DNI_CLI,
           COD_GRUPO_CIA,
           COD_CAMP_CUPON,
           ESTADO,
           USU_CREA_CA_CLI_CAMP,
           FEC_CREA_CA_CLI_CAMP)
          SELECT cDni_in,
                 cCodGrupoCia_in,
                 COD_CAMP_CUPON,
                 'A',
                 'SISTEMAS',
                 SYSDATE
            FROM (SELECT C.COD_CAMP_CUPON
                    FROM VTA_CAMPANA_CUPON C
                   WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                     AND C.TIP_CAMPANA = 'A'
                     AND C.ESTADO = 'A'
                     AND C.FECH_FIN >= SYSDATE
                  MINUS
                  SELECT CA.COD_CAMP_CUPON
                    FROM CA_CLI_CAMP CA
                   WHERE CA.DNI_CLI = cDni_in);

      END IF;

      COMMIT;

    END IF;

  EXCEPTION

    WHEN OTHERS THEN

      dbms_output.put_line('Error');

      rollback;

  END;

  /***********************************************************/
  FUNCTION FID_F_TIP_DOC(cGrupoCia_in IN CHAR) RETURN FarmaCursor IS
    curCamp FarmaCursor;
  BEGIN
    OPEN curCamp FOR
      SELECT A.COD_TIP_DOCUMENTO || 'Ã' || A.DESC_TIP_DOCUMENTO
        FROM PBL_TIP_DOCUMENTOS A;

    RETURN curCamp;

  END;

  /***********************************************************/
  FUNCTION FID_F_FPAGO_USO_X_CAMPANA(cGrupoCia_in          IN CHAR,
                                     cCodLocal_in          IN CHAR,
                                     cCodCampanCupon       IN CHAR,
                                     cCuponesIngresados_in in varchar2)
    RETURN FarmaCursor IS
    curCamp FarmaCursor;
  BEGIN
    OPEN curCamp FOR
    -- LISTA FORMAS DE PAGO x Campaña
      select distinct datos
        from (select v.datos
                from (SELECT distinct '0' || 'Ã' || FU.COD_FORMA_PAGO || 'Ã' ||
                                      FP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                                      FP.IND_TARJ || 'Ã' ||
                                      FP.IND_FORMA_PAGO_EFECTIVO datos,
                                      fp.cod_forma_pago
                        FROM VTA_CAMPANA_CUPON    C,
                             VTA_CAMP_X_FPAGO_USO FU,
                             VTA_FORMA_PAGO       FP
                       WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                         AND C.ESTADO = 'A'
                         AND C.IND_FID = 'S'
                         AND C.TIP_CAMPANA = 'C'
                         AND C.NUM_CUPON = 0
                         -- dubilluz campañas que no son para colegio medido
                         -- 06.12.2011
                         and nvl(c.ind_tipo_colegio,'00') = '00'
                         AND FU.IND_LISTA_PANTALLA = 'S'
                         AND FU.IND_LISTA_PANTALLA = 'S' --DUBILLUZ 20.07.2011
                         AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND
                             C.FECH_FIN_USO
                         AND DECODE(C.DIA_SEMANA,
                                    NULL,
                                    'S',
                                    DECODE(C.DIA_SEMANA,
                                           REGEXP_REPLACE(C.DIA_SEMANA,
                                                          FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE),
                                                          'S'),
                                           'N',
                                           'S')) = 'S'
                         AND (C.IND_CADENA = 'S' OR
                             (C.IND_CADENA = 'N' AND EXISTS
                              (SELECT 1
                                  FROM VTA_CAMP_X_LOCAL CL
                                 WHERE CL.COD_GRUPO_CIA = cGrupoCia_in
                                   AND CL.COD_LOCAL = cCodLocal_in
                                   AND CL.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                   AND CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                   AND CL.ESTADO = 'A')))
                         AND C.COD_GRUPO_CIA = FU.COD_GRUPO_CIA
                         AND C.COD_CAMP_CUPON = FU.COD_CAMP_CUPON
                         AND FU.ESTADO = 'A'
                         AND FU.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
                         AND FU.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
                         AND FP.EST_FORMA_PAGO in ('A', 'X')
                         AND EXISTS
                       (SELECT 1
                                FROM VTA_FORMA_PAGO_LOCAL FL
                               WHERE FL.COD_GRUPO_CIA = cGrupoCia_in
                                 AND FL.COD_LOCAL = cCodLocal_in
                                 AND FL.EST_FORMA_PAGO_LOCAL in ('A', 'X')
                                 AND FL.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
                                 AND FL.COD_FORMA_PAGO = FP.COD_FORMA_PAGO)
                      union -- forma de pago q tenga la campaña del cupon ingresado
                      -- LISTA FORMAS DE PAGO x Campaña
                      SELECT distinct '0' || 'Ã' || FU.COD_FORMA_PAGO || 'Ã' ||
                                      FP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
                                      FP.IND_TARJ || 'Ã' ||
                                      FP.IND_FORMA_PAGO_EFECTIVO datos,
                                      fp.cod_forma_pago
                        FROM VTA_CAMPANA_CUPON    C,
                             VTA_CAMP_X_FPAGO_USO FU,
                             VTA_FORMA_PAGO       FP
                       WHERE C.COD_GRUPO_CIA = cGrupoCia_in
                         AND C.ESTADO = 'A'
                         AND C.IND_FID = 'S'
                         AND C.TIP_CAMPANA = 'C'
                         AND C.NUM_CUPON <> 0
                         AND C.COD_CAMP_CUPON IN
                             (select cCodCampanCupon
                                from dual
                              union
                              SELECT EXTRACTVALUE(xt.column_value, 'e')
                                FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                                       REPLACE(cCuponesIngresados_in,
                                                                               '@',
                                                                               '</e><e>') ||
                                                                       '</e></coll>'),
                                                               '/coll/e'))) xt)
                         AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND
                             C.FECH_FIN_USO
                         AND DECODE(C.DIA_SEMANA,
                                    NULL,
                                    'S',
                                    DECODE(C.DIA_SEMANA,
                                           REGEXP_REPLACE(C.DIA_SEMANA,
                                                          FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE),
                                                          'S'),
                                           'N',
                                           'S')) = 'S'
                         AND (C.IND_CADENA = 'S' OR
                             (C.IND_CADENA = 'N' AND EXISTS
                              (SELECT 1
                                  FROM VTA_CAMP_X_LOCAL CL
                                 WHERE CL.COD_GRUPO_CIA = cGrupoCia_in
                                   AND CL.COD_LOCAL = cCodLocal_in
                                   AND CL.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                                   AND CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                                   AND CL.ESTADO = 'A')))
                         AND C.COD_GRUPO_CIA = FU.COD_GRUPO_CIA
                         AND C.COD_CAMP_CUPON = FU.COD_CAMP_CUPON
                         AND FU.ESTADO = 'A'
                         AND FU.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
                         AND FU.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
                         AND FP.EST_FORMA_PAGO in ('A', 'X')
                         AND FU.IND_LISTA_PANTALLA = 'S' --DUBILLUZ 20.07.2011
                         AND EXISTS
                       (SELECT 1
                                FROM VTA_FORMA_PAGO_LOCAL FL
                               WHERE FL.COD_GRUPO_CIA = cGrupoCia_in
                                 AND FL.COD_LOCAL = cCodLocal_in
                                 AND FL.EST_FORMA_PAGO_LOCAL in ('A', 'X')
                                 AND FL.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
                                 AND FL.COD_FORMA_PAGO = FP.COD_FORMA_PAGO)) v
               order by v.cod_forma_pago asc);

    RETURN curCamp;

  END;
/*-----------------------------------------------------------------------------------------------------------------------------------
GOAL : Validacion de Fidelizacion
History : 21-JUL-14  TCT Modifica lectura de codigo de Campaña
-------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FID_IS_FP_X_USO(cGrupoCia_in    IN CHAR,
                           cCodLocal_in    IN CHAR,
                           cCodCampanCupon IN CHAR) RETURN varchar2 IS
    cantidad varchar2(10);

    vCodCampana vta_campana_cupon.cod_camp_cupon%type;

  BEGIN

   if  cCodCampanCupon ='12719' then
      vCodCampana :='12723';
   else
      
      --- < 21 - JUL - 14  TCT New Mode codigo camp >
      --vCodCampana := cCodCampanCupon;
         vCodCampana := ptoventa_vta.fn_get_cod_campa(ccodgrupocia_in => cGrupoCia_in,
                                           ccodcupon_in => cCodCampanCupon);
     --- < /21 - JUL - 14  TCT New Mode codigo camp >                                      
     
   end if;

    SELECT decode(COUNT(1), 0, 'N', 'S')
      into cantidad
      FROM VTA_CAMPANA_CUPON C, VTA_CAMP_X_FPAGO_USO FU, VTA_FORMA_PAGO FP
     WHERE C.COD_GRUPO_CIA = cGrupoCia_in
       AND C.ESTADO = 'A'
       AND C.IND_FID = 'S'
       AND C.TIP_CAMPANA = 'C'
       AND C.NUM_CUPON <> 0
       AND C.COD_CAMP_CUPON = vCodCampana--cCodCampanCupon
       AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
       AND DECODE(C.DIA_SEMANA,
                  NULL,
                  'S',
                  DECODE(C.DIA_SEMANA,
                         REGEXP_REPLACE(C.DIA_SEMANA,
                                        FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE),
                                        'S'),
                         'N',
                         'S')) = 'S'
       AND (C.IND_CADENA = 'S' OR
           (C.IND_CADENA = 'N' AND EXISTS
            (SELECT 1
                FROM VTA_CAMP_X_LOCAL CL
               WHERE CL.COD_GRUPO_CIA = cGrupoCia_in
                 AND CL.COD_LOCAL = cCodLocal_in
                 AND CL.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                 AND CL.ESTADO = 'A')))
       AND C.COD_GRUPO_CIA = FU.COD_GRUPO_CIA
       AND C.COD_CAMP_CUPON = FU.COD_CAMP_CUPON
       AND FU.ESTADO = 'A'
       AND FU.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
       AND FU.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
       AND FP.EST_FORMA_PAGO in ('A', 'X')
       AND EXISTS
     (SELECT 1
              FROM VTA_FORMA_PAGO_LOCAL FL
             WHERE FL.COD_GRUPO_CIA = cGrupoCia_in
               AND FL.COD_LOCAL = cCodLocal_in
               AND FL.EST_FORMA_PAGO_LOCAL in ('A', 'X')
               AND FL.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
               AND FL.COD_FORMA_PAGO = FP.COD_FORMA_PAGO);

    RETURN cantidad;

  END;
  /* ************************************************************* */
  FUNCTION FID_F_FORMA_PAGO_TARJETA(A_NUM_TARJETA CHAR /*, A_FLG_TIPO CHAR */)
    RETURN CHAR is
    I                NUMBER := 0;
    X                NUMBER := 1;
    V_RESULT         NUMBER := 0;
    V_TOTAL          NUMBER := 0;
    V_VALIDA         NUMBER := 1;
    c_cod_forma_pago AUX_FORMA_PAGO.Cod_Forma_Pago%TYPE;
    C_COD_TARJETA    AUX_FORMA_PAGO.COD_HIJO%TYPE;
    C_DSC_TARJETA    AUX_FORMA_PAGO.DES_HIJO%TYPE;
    C_FLG_RETIRO_EFE AUX_FORMA_PAGO.FLG_RETIRO_EFE%TYPE;
    V_FLG_VALIDA_MOD AUX_FORMA_PAGO.FLG_VALIDA_MOD%TYPE;
    cCodFormaPago_MF vta_forma_pago.cod_forma_pago%type;
  BEGIN

    --primero detecta que tarjeta es solo en base al BIN
    if FN_LISTA_TJT_NUMEROM10(A_NUM_TARJETA, 1) = 0 then
      return '*';
    else
      if FN_LISTA_TJT_NUMEROM10(A_NUM_TARJETA, 1) = 1 then
        BEGIN
          SELECT COD_FORMA_PAGO,
                 COD_HIJO,
                 DES_HIJO,
                 FLG_RETIRO_EFE,
                 FLG_VALIDA_MOD
            INTO c_cod_forma_pago,
                 C_COD_TARJETA,
                 C_DSC_TARJETA,
                 C_FLG_RETIRO_EFE,
                 V_FLG_VALIDA_MOD
            FROM AUX_FORMA_PAGO
           WHERE --COD_BIN_TARJETA LIKE SUBSTR( A_NUM_TARJETA ,1,6)
           INSTR(COD_BIN_TARJETA, SUBSTR(A_NUM_TARJETA, 1, 6)) > 0
           AND COD_FORMA_PAGO = '002';
          --            RAISE_APPLICATION_ERROR(-20000||' '||C_COD_TARJETA||' '||C_DSC_TARJETA||' '||C_FLG_RETIRO_EFE||' '||V_FLG_VALIDA_MOD);
        EXCEPTION

          WHEN NO_DATA_FOUND THEN
            RETURN '*';
            ----------------PARA QUE NO BUSQUE MAS
            BEGIN
              --VISA ELECTRON
              SELECT COD_HIJO, DES_HIJO, FLG_RETIRO_EFE, FLG_VALIDA_MOD
                INTO C_COD_TARJETA,
                     C_DSC_TARJETA,
                     C_FLG_RETIRO_EFE,
                     V_FLG_VALIDA_MOD
                FROM AUX_FORMA_PAGO
               WHERE INSTR(COD_BIN_TARJETA, SUBSTR(A_NUM_TARJETA, 1, 4)) > 0
                 AND COD_FORMA_PAGO = '002';
              --                        RAISE_APPLICATION_ERROR(-20000,C_COD_TARJETA||' '||);
            EXCEPTION
              WHEN NO_DATA_FOUND THEN
                BEGIN
                  SELECT COD_HIJO, DES_HIJO, FLG_RETIRO_EFE, FLG_VALIDA_MOD
                    INTO C_COD_TARJETA,
                         C_DSC_TARJETA,
                         C_FLG_RETIRO_EFE,
                         V_FLG_VALIDA_MOD
                    FROM AUX_FORMA_PAGO
                  --WHERE COD_BIN_TARJETA LIKE SUBSTR( A_NUM_TARJETA ,1,2) ;
                   WHERE INSTR(COD_BIN_TARJETA, SUBSTR(A_NUM_TARJETA, 1, 2)) > 0
                     AND COD_FORMA_PAGO = '002';
                  --                                    raise_application_error(-20000, 'Aca no va llegar jamas');
                EXCEPTION
                  WHEN NO_DATA_FOUND THEN
                    BEGIN
                      SELECT COD_HIJO,
                             DES_HIJO,
                             FLG_RETIRO_EFE,
                             FLG_VALIDA_MOD
                        INTO C_COD_TARJETA,
                             C_DSC_TARJETA,
                             C_FLG_RETIRO_EFE,
                             V_FLG_VALIDA_MOD
                        FROM AUX_FORMA_PAGO
                       WHERE COD_BIN_TARJETA LIKE
                             SUBSTR(A_NUM_TARJETA, 1, 1)
                         AND COD_FORMA_PAGO = '002';
                      --                                                RAISE_APPLICATION_ERROR(-20000);
                    EXCEPTION
                      WHEN NO_DATA_FOUND THEN
                        C_COD_TARJETA := '*';
                        C_DSC_TARJETA := '*';
                    END;
                END;
            END;
        END;

        --validamos el mod10 si la tarjeta lo requiere
        IF C_COD_TARJETA <> '*' AND V_FLG_VALIDA_MOD = '1' THEN
          IF INSTR(A_NUM_TARJETA, '00000000') = 0 THEN
            --PARA CUANDO NO TIENEN LA TARJETA Y LO RELLENAN DE CEROS
            FOR I IN REVERSE 1 .. LENGTH(A_NUM_TARJETA) LOOP
              V_RESULT := SUBSTR(A_NUM_TARJETA, I, 1) * X;
              IF V_RESULT >= 10 THEN
                V_TOTAL := V_TOTAL + SUBSTR(V_RESULT, 1, 1) +
                           SUBSTR(V_RESULT, 2, 1);
              ELSE
                V_TOTAL := V_TOTAL + V_RESULT;
              END IF;
              IF X = 2 THEN
                X := 1;
              ELSE
                X := 2;
              END IF;
            END LOOP;
            --------------------------
            IF MOD(V_TOTAL, 10) = 0 THEN
              V_VALIDA := 1;
            ELSE
              V_VALIDA := 0; --tarjeta no valida
            END IF;
            --------------------------
          ELSE
            V_VALIDA := 1;
          END IF;
        END IF;

        IF V_VALIDA = 0 THEN
          RETURN '*';
        END IF;

        DBMS_OUTPUT.put_line('C_COD_TARJETA:' || C_COD_TARJETA);
        begin
          SELECT bm.cod_forma_pago_mf
            into cCodFormaPago_MF
            FROM aux_forma_pago a, vta_fp_tarj_btl_mf bm
           WHERE a.cod_forma_pago = c_cod_forma_pago
             AND a.cod_hijo = C_COD_TARJETA
             and a.cod_forma_pago = bm.cod_forma_pago_btl
             and a.cod_hijo = bm.cod_hijo;
        exception
          when no_data_found then
            cCodFormaPago_MF := '*';
        end;
      end if;
    end if;

    return cCodFormaPago_MF;
  END;
  /* ***************************************************************************** */
  --
  FUNCTION FID_IS_TARJETA_APLICA_CAMPANA(cGrupoCia_in  IN CHAR,
                                         cCodLocal_in  IN CHAR,
                                         A_NUM_TARJETA CHAR) RETURN CHAR IS
    vAplicaDescuentoTarjeta char(1) := 'N';
    vCodFormaPagoMF         vta_forma_pago.cod_forma_pago%type;
    cantidad_aplica_camp    number;
    nExisteListaNegra       number;
  BEGIN

    /*select count(1)
    into   nExisteListaNegra
    from   fid_tarjeta n
    where  n.cod_tarjeta = A_NUM_TARJETA;

    if nExisteListaNegra = 0 then*/

    vCodFormaPagoMF := FID_F_FORMA_PAGO_TARJETA(A_NUM_TARJETA);

    if trim(vCodFormaPagoMF) = '*' then
      vAplicaDescuentoTarjeta := 'N';
    else
      /* ****/
      SELECT count(1)
        INTO cantidad_aplica_camp
        FROM VTA_CAMPANA_CUPON    C,
             VTA_CAMP_X_FPAGO_USO FU,
             VTA_FORMA_PAGO       FP
       WHERE C.COD_GRUPO_CIA = cGrupoCia_in
         AND FU.COD_FORMA_PAGO = vCodFormaPagoMF
         AND C.ESTADO = 'A'
         AND C.IND_FID = 'S'
         AND C.TIP_CAMPANA = 'C'
         AND C.NUM_CUPON = 0
           -- dubilluz campañas que no son para colegio medido
           -- 06.12.2011
           and nvl(c.ind_tipo_colegio,'00') = '00'
         AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
         AND DECODE(C.DIA_SEMANA,
                    NULL,
                    'S',
                    DECODE(C.DIA_SEMANA,
                           REGEXP_REPLACE(C.DIA_SEMANA,
                                          FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE),
                                          'S'),
                           'N',
                           'S')) = 'S'
         AND (C.IND_CADENA = 'S' OR
             (C.IND_CADENA = 'N' AND EXISTS
              (SELECT 1
                  FROM VTA_CAMP_X_LOCAL CL
                 WHERE CL.COD_GRUPO_CIA = cGrupoCia_in
                   AND CL.COD_LOCAL = cCodLocal_in
                   AND CL.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                   AND CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                   AND CL.ESTADO = 'A')))
         AND C.COD_GRUPO_CIA = FU.COD_GRUPO_CIA
         AND C.COD_CAMP_CUPON = FU.COD_CAMP_CUPON
         AND FU.ESTADO = 'A'
         AND FU.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
         AND FU.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
         AND FP.EST_FORMA_PAGO in ('A', 'X')
         AND EXISTS
       (SELECT 1
                FROM VTA_FORMA_PAGO_LOCAL FL
               WHERE FL.COD_GRUPO_CIA = cGrupoCia_in
                 AND FL.COD_LOCAL = cCodLocal_in
                 AND FL.EST_FORMA_PAGO_LOCAL in ('A', 'X')
                 AND FL.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
                 AND FL.COD_FORMA_PAGO = FP.COD_FORMA_PAGO);
      /*****/
    end if;
    --------------------------------------
    if cantidad_aplica_camp > 0 then
      vAplicaDescuentoTarjeta := 'S';
    end if;
    --------------------------------------
    /*else
        vAplicaDescuentoTarjeta := 'N'; -- NO APLICA XQ ESTA EN LISTA NEGRA
    end if; */
    return vAplicaDescuentoTarjeta;

  END;

  /* ***************************************************************************** */
  FUNCTION FID_F_DATO_TARJETA_INGRESADA(cGrupoCia_in  IN CHAR,
                                        cCodLocal_in  IN CHAR,
                                        A_NUM_TARJETA CHAR) RETURN varchar2 IS
    vAplicaDescuentoTarjeta char(1) := 'N';
    vCodFormaPagoMF         vta_forma_pago.cod_forma_pago%type;
    datos_forma_pago        VARCHAR2(3000);
  BEGIN
    -- dubilluz 07.09.2012 inicio
    vCodFormaPagoMF := FID_F_FORMA_PAGO_TARJETA(A_NUM_TARJETA);
    -- dubilluz 07.09.2012 fin
    SELECT distinct FP.COD_FORMA_PAGO || '@' || FP.DESC_FORMA_PAGO
      INTO datos_forma_pago
      FROM VTA_CAMPANA_CUPON C, VTA_CAMP_X_FPAGO_USO FU, VTA_FORMA_PAGO FP
     WHERE C.COD_GRUPO_CIA = cGrupoCia_in
       AND FU.COD_FORMA_PAGO = vCodFormaPagoMF
       AND C.ESTADO = 'A'
       AND C.IND_FID = 'S'
       AND C.TIP_CAMPANA = 'C'
       AND C.NUM_CUPON = 0
           -- dubilluz campañas que no son para colegio medido
           -- 06.12.2011
           and nvl(c.ind_tipo_colegio,'00') = '00'
       AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
       AND DECODE(C.DIA_SEMANA,
                  NULL,
                  'S',
                  DECODE(C.DIA_SEMANA,
                         REGEXP_REPLACE(C.DIA_SEMANA,
                                        FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE),
                                        'S'),
                         'N',
                         'S')) = 'S'
       AND (C.IND_CADENA = 'S' OR
           (C.IND_CADENA = 'N' AND EXISTS
            (SELECT 1
                FROM VTA_CAMP_X_LOCAL CL
               WHERE CL.COD_GRUPO_CIA = cGrupoCia_in
                 AND CL.COD_LOCAL = cCodLocal_in
                 AND CL.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                 AND CL.ESTADO = 'A')))
       AND C.COD_GRUPO_CIA = FU.COD_GRUPO_CIA
       AND C.COD_CAMP_CUPON = FU.COD_CAMP_CUPON
       AND FU.ESTADO = 'A'
       AND FU.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
       AND FU.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
       AND FP.EST_FORMA_PAGO in ('A', 'X')
       AND EXISTS
     (SELECT 1
              FROM VTA_FORMA_PAGO_LOCAL FL
             WHERE FL.COD_GRUPO_CIA = cGrupoCia_in
               AND FL.COD_LOCAL = cCodLocal_in
               AND FL.EST_FORMA_PAGO_LOCAL in ('A', 'X')
               AND FL.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
               AND FL.COD_FORMA_PAGO = FP.COD_FORMA_PAGO);
    /*****/

    return datos_forma_pago;

  END;
  /* ***************************************************************************** */
  FUNCTION FN_LISTA_TJT_NUMEROM10(A_NUM_TARJETA CHAR, A_FLG_VALIDA CHAR)
    RETURN CHAR AS
    I        NUMBER := 0;
    X        NUMBER := 1;
    V_RESULT NUMBER := 0;
    V_TOTAL  NUMBER := 0;
    V_VALIDA NUMBER := 1;
  BEGIN
    --validamos el mod10 si la tarjeta lo requiere
    IF INSTR(A_NUM_TARJETA, '00000000') = 0 THEN
      --PARA CUANDO NO TIENEN LA TARJETA Y LO RELLENAN DE CEROS
      FOR I IN REVERSE 1 .. LENGTH(A_NUM_TARJETA) LOOP
        V_RESULT := SUBSTR(A_NUM_TARJETA, I, 1) * X;
        IF V_RESULT >= 10 THEN
          V_TOTAL := V_TOTAL + SUBSTR(V_RESULT, 1, 1) +
                     SUBSTR(V_RESULT, 2, 1);
        ELSE
          V_TOTAL := V_TOTAL + V_RESULT;
        END IF;
        IF X = 2 THEN
          X := 1;
        ELSE
          X := 2;
        END IF;
      END LOOP;
      --------------------------
      IF MOD(V_TOTAL, 10) = 0 THEN
        V_VALIDA := 1;
      ELSE
        V_VALIDA := 0; --tarjeta no valida
      END IF;
      --------------------------
    ELSE
      V_VALIDA := 1;
    END IF;

    IF V_VALIDA = 0 THEN
      RETURN '0';
    ELSE
      RETURN '1';
    END IF;
  END;
  /* ******************************************************************************  */
  --retorna la campña para esa tarjeta
  FUNCTION FID_F_CAMP_X_TARJ_VISA(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  A_NUM_TARJETA CHAR) RETURN varchar2 IS
    vAplicaDescuentoTarjeta char(1) := 'N';
    vCodFormaPagoMF         vta_forma_pago.cod_forma_pago%type;
    cCodCampana_in          VARCHAR2(10);
    cIndAhorro_x_FP_TARJ varchar2(10);
  BEGIN
    vCodFormaPagoMF := FID_F_FORMA_PAGO_TARJETA(A_NUM_TARJETA);

    select NVL(IND_AHORRO_X_FP,'N')
    into   cIndAhorro_x_FP_TARJ
    from   VTA_FORMA_PAGO
    where  COD_FORMA_PAGO = vCodFormaPagoMF;

    if cIndAhorro_x_FP_TARJ = 'N' then

    SELECT C.COD_CAMP_CUPON
      INTO cCodCampana_in
      FROM VTA_CAMPANA_CUPON C, VTA_CAMP_X_FPAGO_USO FU, VTA_FORMA_PAGO FP
     WHERE C.COD_GRUPO_CIA = cGrupoCia_in
       AND FU.COD_FORMA_PAGO = vCodFormaPagoMF
       AND C.ESTADO = 'A'
       AND C.IND_FID = 'S'
       AND C.TIP_CAMPANA = 'C'
       AND C.NUM_CUPON = 0
           -- dubilluz campañas que no son para colegio medido
           -- 06.12.2011
           and nvl(c.ind_tipo_colegio,'00') = '00'
       AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
       AND DECODE(C.DIA_SEMANA,
                  NULL,
                  'S',
                  DECODE(C.DIA_SEMANA,
                         REGEXP_REPLACE(C.DIA_SEMANA,
                                        FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE),
                                        'S'),
                         'N',
                         'S')) = 'S'
       AND (C.IND_CADENA = 'S' OR
           (C.IND_CADENA = 'N' AND EXISTS
            (SELECT 1
                FROM VTA_CAMP_X_LOCAL CL
               WHERE CL.COD_GRUPO_CIA = cGrupoCia_in
                 AND CL.COD_LOCAL = cCodLocal_in
                 AND CL.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                 AND CL.ESTADO = 'A')))
       AND C.COD_GRUPO_CIA = FU.COD_GRUPO_CIA
       AND C.COD_CAMP_CUPON = FU.COD_CAMP_CUPON
       AND FU.ESTADO = 'A'
       AND FU.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
       AND FU.COD_FORMA_PAGO = FP.COD_FORMA_PAGO
       AND FP.EST_FORMA_PAGO in ('A', 'X')
       AND EXISTS
     (SELECT 1
              FROM VTA_FORMA_PAGO_LOCAL FL
             WHERE FL.COD_GRUPO_CIA = cGrupoCia_in
               AND FL.COD_LOCAL = cCodLocal_in
               AND FL.EST_FORMA_PAGO_LOCAL in ('A', 'X')
               AND FL.COD_GRUPO_CIA = FP.COD_GRUPO_CIA
               AND FL.COD_FORMA_PAGO = FP.COD_FORMA_PAGO);
    /*****/
   else
     cCodCampana_in := 'X';
   end if;
    return cCodCampana_in;

  END;
  /* ********************************************************************* */
  PROCEDURE FID_P_INSERT_TARJ_UNICA(cGrupoCia_in  IN CHAR,
                                    cCodLocal_in  IN CHAR,
                                    A_NUM_TARJETA IN CHAR,
                                    vDni_cli      IN CHAR) is
    vCodCampana        char(5);
    vDni_Nuevo         VARCHAR2(20);
    vExisteCliente     number;
    vExisteTarjeta     number;
    VExisteCampTarjeta number;
    --dubilluz 17.10.2011
    vCadenaInvierteDNI vta_camp_x_fpago_uso.char_nuevo_dni%type;

  begin

    vCodCampana := ptoventa_fidelizacion.fid_f_camp_x_tarj_visa(cGrupoCia_in,
                                                                cCodLocal_in,
                                                                A_NUM_TARJETA);
    if vCodCampana != 'X' then
    --dubilluz 17.10.2011
    vCadenaInvierteDNI := FID_F_INI_NUEVO_DNI(cGrupoCia_in,
                                              cCodLocal_in,
                                              vCodCampana,
                                              A_NUM_TARJETA);
    else
      vCadenaInvierteDNI := 'N';
    end if;

    if vCadenaInvierteDNI != 'N' or vCadenaInvierteDNI is null then
      vDni_Nuevo := trim(vCadenaInvierteDNI) || REVER_PALABRA(vDni_cli);
      --dubilluz 25.07.2011
      vDni_Nuevo := fid_valida_dni(vDni_Nuevo);
    else
      vDni_Nuevo := vDni_cli;
    end if;
    -- fin dubilluz 17.10.2011

    select count(1)
      into vExisteCliente
      from pbl_cliente
     where dni_cli = vDni_Nuevo;
    select count(1)
      into vExisteTarjeta
      from FID_TARJETA
     where cod_tarjeta = A_NUM_TARJETA;
    select count(1)
      into VExisteCampTarjeta
      from vta_camp_x_tarjeta
     where TARJETA_INI = A_NUM_TARJETA;

    if vExisteCliente = 0 then
      insert into pbl_cliente
        (DNI_CLI,
         NOM_CLI,
         APE_PAT_CLI,
         APE_MAT_CLI,
         FONO_CLI,
         SEXO_CLI,
         DIR_CLI,
         FEC_NAC_CLI,
         FEC_CREA_CLIENTE,
         USU_CREA_CLIENTE,
         IND_ESTADO,
         EMAIL,
         COD_LOCAL_ORIGEN,
         CELL_CLI,
         COD_TIP_DOCUMENTO,
         ID_USU_CONFIR,
         COD_LOCAL_CONFIR,
         IP_CONFIR)
      /* values
       (vDni_Nuevo,'NOM','AP','AM',0,null,null,null,sysdate,'AUTO','A',null,cCodLocal_in,0,
       '01',null,null,null);*/
        select vDni_Nuevo,
               NOM_CLI,
               APE_PAT_CLI,
               APE_MAT_CLI,
               FONO_CLI,
               SEXO_CLI,
               DIR_CLI,
               FEC_NAC_CLI,
               FEC_CREA_CLIENTE,
               USU_CREA_CLIENTE,
               IND_ESTADO,
               EMAIL,
               COD_LOCAL_ORIGEN,
               CELL_CLI,
               COD_TIP_DOCUMENTO,
               ID_USU_CONFIR,
               COD_LOCAL_CONFIR,
               IP_CONFIR
          from pbl_cliente c
         where c.dni_cli = vDni_cli;
    end if;
    dbms_output.put_line(vExisteTarjeta);
    if vExisteTarjeta = 0 then

      INSERT INTO FID_TARJETA
        (COD_TARJETA,
         DNI_CLI,
         COD_LOCAL,
         USU_CREA_TARJETA,
         FEC_CREA_TARJETA)
      VALUES
        (A_NUM_TARJETA, vDni_Nuevo, cCodLocal_in, 'AUTO', SYSDATE);
        dbms_output.put_line(A_NUM_TARJETA);
    end if;
    dbms_output.put_line(VExisteCampTarjeta||'-'||vCodCampana);

    if VExisteCampTarjeta = 0 and vCodCampana != 'X' then
      insert into vta_camp_x_tarjeta
        (COD_GRUPO_CIA,
         COD_CAMP_CUPON,
         TARJETA_INI,
         TARJETA_FIN,
         USU_CREA_CAMP_X_TARJ,
         FEC_CREA_CAMP_X_TARJ)
      values
        (cGrupoCia_in,
         vCodCampana,
         A_NUM_TARJETA,
         A_NUM_TARJETA,
         'AUTO',
         sysdate);
    end if;


  end;
  /* ************************************************************************ */
  function fid_f_existe_dni_asociado(tarjetaUnica VARCHAR2) return varchar2 is
    v_cadena2        varchar2(888);
    v_longitud       number;
    cadena_revertida varchar2(1000);
    valor            char(1);
  begin

    select decode(count(1), 0, 'N', 'S')
      into valor
      from fid_tarjeta t
     where t.cod_tarjeta = tarjetaUnica;

    return valor;

  end;
  /*************************************************************************** */
  function REVER_PALABRA(cadena_a_revertir VARCHAR2) return varchar2 is
    v_cadena2        varchar2(888);
    v_longitud       number;
    cadena_revertida varchar2(1000);
  begin

    v_longitud := length(cadena_a_revertir);
    loop
      v_cadena2  := v_cadena2 || substr(cadena_a_revertir, v_longitud, 1);
      v_longitud := v_longitud - 1;
      if v_longitud = 0 then
        exit;
      end if;
    end loop;

    cadena_revertida := v_cadena2;

    return cadena_revertida;

  end;

  /* *********************************************************************** */
  function FID_F_IS_VALIDA_MATRIZ(cGrupoCia_in  IN CHAR,
                                  cCodLocal_in  IN CHAR,
                                  cNumPedido_in IN CHAR) return char is
    vCodCampana varchar2(10);
  begin
    begin
      select c.cod_camp_cupon
        into vCodCampana
        from vta_campana_cupon c,
             (select distinct d.cod_camp_cupon
                from vta_pedido_vta_det d
               where d.cod_grupo_cia = cGrupoCia_in
                 and d.cod_local = cCodLocal_in
                 and d.num_ped_vta = cNumPedido_in) cu
       where c.cod_grupo_cia = cGrupoCia_in
         and c.cod_camp_cupon = cu.cod_camp_cupon
         and c.ind_valida_matriz = 'S';
    exception
      when no_data_found then
        vCodCampana := 'N';
    end;
    return vCodCampana;
  end;
  /* *********************************************************************** */
  function FID_VALIDA_DNI(vDNI_in VARCHAR2) return varchar2 is
    v_cadena2        varchar2(888);
    v_longitud       number;
    cadena_revertida varchar2(1000);
    uCadena          varchar2(10);
    vDNI_CADENA      varchar2(10);
  begin
    /*
    DUBILLUZ  - 17.10.2011
    ESTE METODO ERA INICIALMENTE PARA CORREGIR UN ERROR EN EL SISTEMA
    Y YA NO SE VA PRESENTAR POR ESO QUE SE COMENTA TODO LO HECHO Y ENVIARA
    SIEMPRE EL MISMO DNI QUE RECIBE COMO PARAMETRO.

    IF length(vDNI_in) > 10 then
       --ultimo 2 caracteres
       uCadena := substr(vDNI_in,length(vDNI_in)+1-2,2);
       if uCadena = 'TU' or uCadena = 'UT' then
          --esta mal y a corregir
          vDNI_CADENA := substr(vDNI_in,3,8);
          cadena_revertida := REVER_PALABRA(vDNI_CADENA);
          cadena_revertida := 'TU'||cadena_revertida;
       end if;
    else
       cadena_revertida :=  vDNI_in;
    end if;
    */
    cadena_revertida := vDNI_in;

    return cadena_revertida;

  end;
  /* *************************************************************** */
  --FID_F_VALIDA_COBRO_PEDIDO(cCodGrupoCia_in,CodLocal_in,cNumPedVta_in)
  function FID_F_VALIDA_COBRO_PEDIDO(cCodGrupoCia_in in char,
                                     cCodLocal_in    in char,
                                     cNumPedVta_in   in char) return varchar2 is

    vResultado      char(1) := 'N';
    vCantCampanaUso number;
  begin

    select count(1)
      into vCantCampanaUso
      from vta_pedido_vta_det d, vta_camp_x_fpago_uso cp
     where d.cod_grupo_cia = cCodGrupoCia_in
       and d.cod_local = cCodLocal_in
       and d.num_ped_vta = cNumPedVta_in
       and cp.estado = 'A'
       and d.cod_grupo_cia = cp.cod_grupo_cia
       and d.cod_camp_cupon = cp.cod_camp_cupon
       and cp.cod_forma_pago not in ('E0000', 'T0000');

    if vCantCampanaUso > 0 then
  --ERIOS 17.10.2013 Nuevo cobro, no valida
      /*
      IF PTOVENTA_GRAL.GET_IND_NUEVO_COBRO = 'S' THEN
        vResultado := 'S';
      ELSE*/
      --ERIOS 17.10.2013 Relacion de forma pago
      -- LA VALIDACION DEBE DE HACERSE SEA CUAL COBRO SEA --
        select decode(count(1), 0, 'N', 'S')
          into vResultado
          from vta_pedido_vta_det    d,
               vta_camp_x_fpago_uso  cp,
               vta_forma_pago_pedido fp
         where d.cod_grupo_cia = cCodGrupoCia_in
           and d.cod_local = cCodLocal_in
           and d.num_ped_vta = cNumPedVta_in
           and cp.estado = 'A'
           and d.cod_grupo_cia = cp.cod_grupo_cia
           and d.cod_camp_cupon = cp.cod_camp_cupon
           and d.cod_grupo_cia = fp.cod_grupo_cia
           and d.cod_local = fp.cod_local
           and d.num_ped_vta = fp.num_ped_vta
           and cp.cod_grupo_cia = fp.cod_grupo_cia
           and (cp.cod_forma_pago = fp.cod_forma_pago
                or exists (select 1 from vta_rel_forma_pago
                            where cod_grupo_cia = fp.cod_grupo_cia
                                and cod_forma_hijo = fp.cod_forma_pago
                                and cod_forma_pago = cp.cod_forma_pago));
      -- END IF;
    else
      vResultado := 'S';
    end if;

    return vResultado;

  end;
  /* *************************************************************** */
  function FID_F_VALIDA_DNI_PEDIDO(cCodGrupoCia_in in char,
                                   cCodLocal_in    in char,
                                   cNumPedVta_in   in char) return varchar2 is

    vResultado      char(1) := 'N';
    vCantCampanaUso number;
    vDNI_PEDIDO     varchar2(30);
    vExpRegular     varchar2(100);
    --dubilluz 07.12.2011
    vUsoCampana_MEDICO number;
    vColegioMedico varchar2(10);
    nExiste_Medico number;
    --dubilluz 06.05.2013
    nCodTarjeta varchar2(50);
    vCodCampana_in varchar2(50);
    nValidaFormatoDNI_COBRO varchar2(5);
  begin

    -- dubilluz 06.05.2013
    begin
      SELECT T.COD_TARJETA
      into   nCodTarjeta
      FROM   FID_TARJETA_PEDIDO T
      WHERE  t.cod_grupo_cia = cCodGrupoCia_in
      and    t.cod_local = cCodLocal_in
      and    t.num_pedido = cNumPedVta_in;

      SELECT C.COD_CAMP_CUPON
      into   vCodCampana_in
      FROM   VTA_CAMP_X_TARJETA C
      WHERE  nCodTarjeta BETWEEN TARJETA_INI AND TARJETA_FIN;

      select nvl(ca.ind_val_dni_cobro,'N')
      into   nValidaFormatoDNI_COBRO
      from   vta_campana_cupon ca
      where  ca.cod_grupo_cia = cCodGrupoCia_in
      and    ca.cod_camp_cupon = vCodCampana_in;
    exception
    when others then
      nValidaFormatoDNI_COBRO := 'S';
    end;

    if nValidaFormatoDNI_COBRO = 'S' then
    -- dubilluz 06.05.2013
    select count(1)
      into vCantCampanaUso
      from vta_pedido_vta_det d
     where d.cod_grupo_cia = cCodGrupoCia_in
       and d.cod_local = cCodLocal_in
       and d.num_ped_vta = cNumPedVta_in
       and d.cod_camp_cupon is not null;

    if vCantCampanaUso > 0 then
      --ERIOS 17.11.2014 Cambios de JLUNA
      select count(1)
      into   vUsoCampana_MEDICO
      from   vta_campana_cupon s
      where  s.ind_tipo_colegio != '00'
      and    s.cod_grupo_cia = cCodGrupoCia_in
      and    s.cod_camp_cupon in (
                                  select trim(d.cod_camp_cupon)
                                    from vta_pedido_vta_det d
                                   where d.cod_grupo_cia = cCodGrupoCia_in
                                     and d.cod_local = cCodLocal_in
                                     and d.num_ped_vta = cNumPedVta_in
                                     and d.cod_camp_cupon is not null
                                 );

      select cc.dni_cli,nvl(cc.num_cmp,'0')
        into vDNI_PEDIDO,vColegioMedico
        from vta_pedido_vta_cab cc
       where cc.cod_grupo_cia = cCodGrupoCia_in
         and cc.cod_local = cCodLocal_in
         and cc.num_ped_vta = cNumPedVta_in;

      -- existe alguna campana que pida MEDICO
      if vUsoCampana_MEDICO > 0 then
         select count(1)
         into   nExiste_Medico
         from   mae_medico m
         where  m.flg_activo = 1
         and    m.num_cmp = vColegioMedico
         and    m.cod_tipo_colegio = '01';

         if nExiste_Medico > 0 then
            vResultado := 'S';
         else
            vResultado := 'N';
         end if;
      else
           vResultado := 'S';
      end if;

      if vResultado = 'S' then
            if vDNI_PEDIDO is not null then

              select decode(count(1), 0, 'N', 'S')
                into vResultado
                from (select distinct d.cod_grupo_cia,
                                      d.cod_local,
                                      d.num_ped_vta,
                                      nvl(vd.expresion, '^[0-9]+$') exp
                        from vta_pedido_vta_det d,
                             (select cab.cod_grupo_cia,
                                     cab.cod_camp_cupon,
                                     nvl(va.exp_regular, '^[0-9]+$') expresion
                                from vta_camp_valida_dni va, vta_campana_cupon cab
                               where cab.cod_grupo_cia = va.cod_grupo_cia(+)
                                 and cab.cod_camp_cupon = va.cod_camp_cupon(+)) vd
                       where d.cod_grupo_cia = cCodGrupoCia_in
                         and d.cod_local = cCodLocal_in
                         and d.num_ped_vta = cNumPedVta_in
                         and d.cod_camp_cupon is not null
                         and d.cod_grupo_cia = vd.cod_grupo_cia
                         and d.cod_camp_cupon = vd.cod_camp_cupon(+)) val,
                     vta_pedido_vta_cab ca
               where ca.cod_grupo_cia = val.cod_grupo_cia
                 and ca.cod_local = val.cod_local
                 and ca.num_ped_vta = val.num_ped_vta
                 and REGEXP_LIKE(ca.dni_cli, val.exp);

            else
              vResultado := 'S';
            end if;
      else
        vResultado := 'N';
      end if;
    else
      vResultado := 'S';
    end if;
    else
        vResultado := 'S';
    end if;

    return vResultado;

  end;
/*---------------------------------------------------------------------------------------------------------------------------------------
GOAL : Valida Fidelizado uso
History : 21-JUL-14  TCT Modifica la forma de procesar codigo camp cupon
-----------------------------------------------------------------------------------------------------------------------------------------*/
function FID_F_GET_FID_USO(cCodGrupocia_in in char,
                             cCodCampania_in in char) return char is
    vInd_Uso char(1);
    vCodCampana vta_campana_cupon.cod_camp_cupon%type;
  begin

      if cCodCampania_in = '12719' then
         vCodCampana := '12723';
      else
         
         --- < 21 - JUL - 14  TCT New Mode codigo camp >
            -- vCodCampana:=   cCodCampania_in;
               vCodCampana := ptoventa_vta.fn_get_cod_campa(ccodgrupocia_in => cCodGrupocia_in,
                                                 ccodcupon_in => cCodCampania_in);
           --- < /21 - JUL - 14  TCT New Mode codigo camp >                                      
      end if;



    select c.ind_fid --devuelvo el ind de no fidelizado para el uso
      into vInd_Uso
      from vta_campana_cupon c
     where c.cod_grupo_cia = cCodGrupocia_in
       and c.cod_camp_cupon = vCodCampana;

    return vInd_Uso;
  end;

  /* ***************************************************************** */
  function FID_F_INI_NUEVO_DNI(cCodGrupocia_in in varchar2,
                               cCodLocal_in    in varchar2,
                               cCodCampania_in in varchar2,
                               cCodTarjeta_in  in varchar2) return VARCHAR2 is

    vCadena       VTA_CAMP_X_FPAGO_USO.char_nuevo_dni%type;
    vCodCampana   varchar2(5);
    vcodFormaPago varchar2(10);
  begin

    if cCodTarjeta_in != 'N' or cCodTarjeta_in is not null then
      vCodCampana := fid_f_camp_x_tarj_visa(cCodGrupocia_in,
                                            cCodLocal_in,
                                            cCodTarjeta_in);
    else
      vCodCampana := cCodCampania_in;
    end if;

    vcodFormaPago := ptoventa_fidelizacion.fid_f_forma_pago_tarjeta(cCodTarjeta_in);
    --dbms_output.put_line('vcodFormaPago:'||vcodFormaPago);
    --dbms_output.put_line('vCodCampana:'||vCodCampana);
    BEGIN
      select NVL(c.char_nuevo_dni, 'N')
        into vCadena
        from VTA_CAMP_X_FPAGO_USO c
       where c.cod_grupo_cia = cCodGrupocia_in
         and c.cod_camp_cupon = trim(vCodCampana)
         and c.cod_forma_pago = trim(vcodFormaPago)
         AND C.IND_INVERTIR_DNI = 'S';
      --dbms_output.put_line('entro 1 ');
    eXCEPTION
      WHEN NO_DATA_FOUND THEN
        vCadena := 'N';
        --dbms_output.put_line('entro2');
    END;
    return vCadena;
  end;

  /* ***************************************************************** */
  function FID_F_EXIST_CAMP_COLEGIO_MED(cCodGrupocia_in in varchar2,
                                        cCodLocal_in    in varchar2)
    return VARCHAR2 is
    vInd char(1) := 'N';
  begin

    SELECT decode(count(1), 0, 'N', 'S')
      into vInd
      FROM VTA_CAMPANA_CUPON c
     WHERE c.COD_GRUPO_CIA = cCodGrupocia_in
       AND C.ESTADO = 'A'
       AND C.IND_FID IN ('S')
       AND C.TIP_CAMPANA = 'C'
       AND C.NUM_CUPON = 0
       AND NVL(C.IND_TIPO_COLEGIO,'00') != '00'
       AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO;

    return vInd;
  end;
  /* ****************************************************************** */
  function FID_F_EXIST_COD_MED(cCodGrupocia_in in varchar2,
                               cCodLocal_in    in varchar2,
                               cCodMed_in      in varchar2) return VARCHAR2 is
    vInd char(1) := 'N';
  begin

    SELECT decode(count(1), 0, 'N', 'S')
      into vInd
      from mae_medico a
     where a.flg_activo = 1
       AND (decode(a.cod_tipo_colegio,
                   '01',
                   'CMP',
                   '02',
                   'ODO',
                   '03',
                   'OBS',
                   'XX')) || (a.num_cmp) =
                                           -- SOLO POR AHORA FUNCIONARA PARA CMP tipo 01
                                           'CMP'||trim(cCodMed_in);

    return vInd;
  end;
  /* ***************************************************************** */
FUNCTION FID_F_CUR_CAMP_X_COD_MEDICO(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        cCodTarjeta_in       IN CHAR,
                                        cCodMedico_in   IN CHAR)
    RETURN FarmaCursor IS

    curLista       FarmaCursor;
    nNumDia        VARCHAR(2);
    cSexo          char(1);
    dFecNaci       date;
    dDniCli        VARCHAR2(20);
    nListaNegraDNI number;
    vTIPO_COLEGIO_MEDICO varchar2(10);
  BEGIN

      -- actualiza el valor de TIPO DE COLEGIO para la CAMPANA
      vTIPO_COLEGIO_MEDICO := trim(cCodMedico_in);
      nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);

    IF cCodTarjeta_in IS NOT NULL THEN

      --OBTENIENDO EL SEXO Y LA FECHA DE NACIMIENTO DEL CLIENTE
      SELECT CL.SEXO_CLI, trunc(CL.FEC_NAC_CLI), CL.DNI_CLI
        INTO cSexo, dFecNaci, dDniCli
        FROM PBL_CLIENTE CL
       WHERE CL.DNI_CLI =
             (SELECT F.Dni_Cli
                FROM FID_TARJETA F
               WHERE F.COD_TARJETA = cCodTarjeta_in);
      select count(1)
        into nListaNegraDNI
        from fid_dni_nulos f
       where f.dni_cli = dDniCli
         and f.estado = 'A';

      OPEN curLista FOR
        SELECT C.COD_CAMP_CUPON AS COD_CAMP_CUPON,
               NVL(C.DESC_CUPON, ' ') AS DESC_CUPON,
               C.TIP_CUPON AS TIP_CUPON,
               C.VALOR_CUPON AS VALOR_CUPON,
               TRIM(to_char(NVL(C.MONT_MIN_USO, 0), '99999999.999')) AS MONT_MIN_USO,
               NVL(C.UNID_MIN_USO, 0) AS UNID_MIN_USO,
               NVL(C.UNID_MAX_PROD, 0) AS UNID_MAX_PROD,
               NVL(C.MONTO_MAX_DESCT, 0) AS MONTO_MAX_DESCT,
               'N' AS IND_MULTIUSO, --POR DEFECTO INDICADOR MULTIUSO COMO N
               C.IND_FID AS IND_FID,
               '' AS COD_CUPON,
               nvl(C.IND_VAL_COSTO_PROM, 'S') AS IND_VAL_COSTO_PROM,
               LPAD(C.PRIORIDAD, 8, '0') || LPAD(C.RANKING, 8, '0') ||
               TIP_CUPON || TO_CHAR(100000 - VALOR_CUPON, '00000.000') AS ORDEN
          FROM VTA_CAMPANA_CUPON C
         WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
           AND C.ESTADO = 'A'
           AND C.IND_FID IN ('S')
           AND C.TIP_CAMPANA = 'C'
           AND C.NUM_CUPON = 0
           AND nListaNegraDNI = 0
           -- dubilluz campañas que no son para colegio medido
           -- 06.12.2011
           and (nvl(c.ind_tipo_colegio,'00') = '0T' or nvl(c.ind_tipo_colegio,'00') = vTIPO_COLEGIO_MEDICO)
           AND TRUNC(SYSDATE) BETWEEN C.FECH_INICIO_USO AND C.FECH_FIN_USO
              -- agregado el filtro de los campos de SEXO y EDAD
           AND (C.TIPO_SEXO_U IS NULL OR C.TIPO_SEXO_U = cSexo)
           AND (C.FEC_NAC_INICIO_U IS NULL OR
               C.FEC_NAC_INICIO_U <= dFecNaci)
           AND (C.FEC_NAC_FIN_U IS NULL OR C.FEC_NAC_FIN_U >= dFecNaci)
              --fin de filtro de los campos de Sexo y Edad
           AND C.COD_CAMP_CUPON IN
               (
                --JCORTEZ 19.10.09 cambio de logica
                SELECT *
                  FROM (SELECT X.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON X
                          WHERE X.COD_GRUPO_CIA = '001'
                            AND X.TIP_CAMPANA = 'C'
                            AND X.ESTADO = 'A'
                            AND X.IND_CADENA = 'S'
                         UNION
                         SELECT Y.COD_CAMP_CUPON
                           FROM VTA_CAMPANA_CUPON Y
                          WHERE Y.COD_GRUPO_CIA = '001'
                            AND Y.TIP_CAMPANA = 'C'
                            AND Y.ESTADO = 'A'
                            AND Y.IND_CADENA = 'N'
                            AND Y.COD_CAMP_CUPON IN
                                (SELECT COD_CAMP_CUPON
                                   FROM VTA_CAMP_X_LOCAL Z
                                  WHERE Z.COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND Z.COD_LOCAL = cCodLocal_in
                                    AND Z.ESTADO = 'A')

                         ))
           AND C.COD_CAMP_CUPON IN
               (SELECT *
                  FROM (SELECT *
                          FROM (SELECT COD_CAMP_CUPON
                                  FROM VTA_CAMPANA_CUPON
                                MINUS
                                SELECT H.COD_CAMP_CUPON FROM VTA_CAMP_HORA H)
                        UNION
                        SELECT H.COD_CAMP_CUPON
                          FROM VTA_CAMP_HORA H
                         WHERE TRIM(TO_CHAR(SYSDATE, 'HH24')) BETWEEN
                               H.HORA_INICIO AND H.HORA_FIN))
           and ((exists (select 1
                           from vta_camp_x_tarjeta t, fid_tarjeta q
                          where q.dni_cli = dDniCli
                            and t.cod_grupo_cia = cCodGrupoCia_in
                            and t.cod_camp_cupon = C.COD_CAMP_CUPON
                            and q.cod_tarjeta between t.tarjeta_ini and
                                t.tarjeta_fin)

               ) or exists
                (SELECT 1
                   FROM VTA_CAMPANA_CUPON d
                  where d.cod_grupo_cia = cCodGrupoCia_in
                    and d.COD_CAMP_CUPON = C.COD_CAMP_CUPON
                 MINUS
                 SELECT 1
                   FROM VTA_CAMP_X_TARJETA T
                  where t.cod_grupo_cia = cCodGrupoCia_in
                    and t.COD_CAMP_CUPON = C.COD_CAMP_CUPON))
           AND DECODE(C.DIA_SEMANA,
                      NULL,
                      'S',
                      DECODE(C.DIA_SEMANA,
                             REGEXP_REPLACE(C.DIA_SEMANA, nNumDia, 'S'),
                             'N',
                             'S')) = 'S'

              --QUITANTO LAS CAMPAÑAS LIMITADAS POR CANTIDAS DE USOS POR CLIENTE A LAS CAMPAÑAS AUTOMATICAS
              --JCALLO 13/02/2009
           AND C.COD_CAMP_CUPON NOT IN
               (SELECT COD_CAMP_CUPON
                  FROM CL_CLI_CAMP
                 WHERE DNI_CLI =
                       (SELECT F.Dni_Cli
                          FROM FID_TARJETA F
                         WHERE F.COD_TARJETA = cCodTarjeta_in)
                   AND MAX_USOS <= NRO_USOS);

    END IF;

    RETURN curLista;

  END FID_F_CUR_CAMP_X_COD_MEDICO;
/* ************************************************************************ */
FUNCTION FID_F_MAX_AHORRO_DIARIO(vCodGrupoCia_in IN VARCHAR2,
                                 vCodLocal_in    IN VARCHAR2,
                                 vDNI_in         IN VARCHAR2 DEFAULT NULL,
                                 vTarj_in        in varchar2
                                 )
                                RETURN NUMBER
IS
 nExisteAhorr_x_Tarj_Especial number;
 nMaxAhorro number;
BEGIN
   select count(1)
   into   nExisteAhorr_x_Tarj_Especial
   from   FID_TARJ_DCTO_DIARIO t
   where  t.cod_grupo_cia = vCodGrupoCia_in
   and    t.cod_tarjeta =  vTarj_in
   and    t.estado = 'A';

   if nExisteAhorr_x_Tarj_Especial = 0 then
      nMaxAhorro := FID_F_NUM_MAX_AHR_PER_DNI(vCodGrupoCia_in,vCodLocal_in,vDNI_in);
   else
     select t.AHORRO_MAX_DIA
     into   nMaxAhorro
     from   FID_TARJ_DCTO_DIARIO t
     where  t.cod_grupo_cia = vCodGrupoCia_in
     and    t.cod_tarjeta =  vTarj_in
     and    t.estado = 'A';

   end if;

   return nMaxAhorro;

END;
/* ************************************************************************** */
/*FUNCTION FID_F_CUR_CAMP_X_TARJ_ESPECIAL(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        cCodTarjeta_in       IN CHAR)
    RETURN FarmaCursor IS

    curLista       FarmaCursor;
    nCantidad number;
  BEGIN
  SELECT count(1)
  into   nCantidad
        from   FID_TARJ_CAMP_ESPECIAL a
        where  a.cod_grupo_cia = cCodGrupoCia_in
        and    a.cod_tarjeta = cCodTarjeta_in
        and    a.estado = 'A';
  if nCantidad > 0 then
      OPEN curLista FOR
        SELECT a.cod_camp_cupon
        from   FID_TARJ_CAMP_ESPECIAL a
        where  a.cod_grupo_cia = cCodGrupoCia_in
        and    a.cod_tarjeta = cCodTarjeta_in
        and    a.estado = 'A';
  else
      return null;
  end if;
        return curLista;
END;*/
FUNCTION FID_F_CUR_CAMP_X_TARJ_ESPECIAL(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        cCodTarjeta_in       IN CHAR)
    RETURN FarmaCursor IS

    curLista       FarmaCursor;
    nCantidad number;
  BEGIN
  --ERIOS 23.01.2014 Codigo innecesario
  /*SELECT count(1)
  into   nCantidad
        from   FID_TARJ_CAMP_ESPECIAL a
        where  a.cod_grupo_cia = cCodGrupoCia_in
        and    a.cod_tarjeta = cCodTarjeta_in
        and    a.estado = 'A';*/
  --if nCantidad > 0 then
      OPEN curLista FOR
        SELECT a.cod_camp_cupon
        from   FID_TARJ_CAMP_ESPECIAL a
        where  a.cod_grupo_cia = cCodGrupoCia_in
        and    a.cod_tarjeta = cCodTarjeta_in
        and    a.estado = 'A'
        AND    1=2;
  --else
  --    return null;
  --end if;
        return curLista;
END;

/* ************************************************************************** */
function GET_INDICADOR_COMISION(cCodGrupocia_in in char,
                             cCodLocal_in in char) return char is

    vValor pbl_tab_gral.llave_tab_gral%type;

  begin
   begin
      select p.llave_tab_gral --devuelvo el ind de no fidelizado para el uso
        into vValor
       from pbl_tab_gral p
      where p.id_tab_gral = 401;
    exception
       when others then
           vValor := 'N';
      end;

    return vValor;
  end;
/* ************************************************************************** */
END PTOVENTA_FIDELIZACION;
/

