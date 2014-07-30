--------------------------------------------------------
--  DDL for Package PTOVENTA_FIDELIZACION
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_FIDELIZACION" AS

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
