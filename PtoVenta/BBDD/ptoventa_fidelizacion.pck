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
  
--INI ASOSA - 23/02/2015 - PTOSYAYAYAYA
    COD_MAE_TIPO_LUGAR number(10) := 15;
        COD_MAE_TIPO_DIREC number(10) := 16;
        EST_PENDIENTE CHAR(1) := 'P';
        EST_ENVIADO CHAR(1) := 'E';
--FIN ASOSA - 23/02/2015 - PTOSYAYAYAYA

--INI ASOSA - 18/03/2015 - PTOSYAYAYAYA
     V_SEPARADOR varchar2(10) := 'ä';
     V_BOLD_I varchar2(10) := 'ÃiÃ';
     V_BOLD_F varchar2(10) := 'ÃfÃ';     
--FIN ASOSA - 18/03/2015 - PTOSYAYAYAYA

--INI ASOSA - 23/03/2015 - PTOSYAYAYAYA
     V_SEPARADOR_2 varchar2(10) := 'ë';
     V_BOLD_I_2 varchar2(10) := 'ËiË';
     V_BOLD_F_2 varchar2(10) := 'ËfË';     
--FIN ASOSA - 23/03/2015 - PTOSYAYAYAYA

  
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
  --FUNCTION FID_F_CUR_LISTA_FIDELIZACION RETURN FarmaCursor;

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
                                 cUserValida IN CHAR default 'N',
                                 --INI ASOSA - 06/02/2015 - PTOSYAYAYAYA
                                 cIndNuevoProceso in CHAR DEFAULT 'N',
                                 cIndLineaOrbis in CHAR  DEFAULT 'N',
                                 vCelular in varchar2  DEFAULT ' ',
                                 vDepartamento in varchar2  DEFAULT ' ',
                                 vProvincia in varchar2  DEFAULT ' ',
                                 vDistrito in varchar2  DEFAULT ' ',
                                 vTipoDireccion in varchar2  DEFAULT ' ',
                                 vTipoLugar in varchar2  DEFAULT ' ',
                                 vReferencias in varchar2 DEFAULT ' '
                                 --FIN ASOSA - 06/02/2015 - PTOSYAYAYAYA
                                 );

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
  -- 09.02.2015 KMONCADA    MODIFICACION: RECIBE NUEVA TARJ DE LEALTAD
  FUNCTION FID_F_CUR_TARJETA_X_DNI(cDni_in               IN CHAR, 
                                   cCodLocal_in          IN CHAR, 
                                   cNroTarjetaLealtad_in IN CHAR DEFAULT NULL)
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
    
    -- KMONCADA 11.02.2015 TARJETA DE PROGRAMA DE PUNTOS
  **/
  FUNCTION FID_F_CHAR_CREA_NEW_TARJ_FID(vCodGrupoCia_in    IN VARCHAR2,
                                        vCodLocal_in       IN VARCHAR2,
                                        vConcatenado_in    IN VARCHAR2,
                                        vUsuID_in          IN VARCHAR2,
                                        vNroTarjetaLealtad IN CHAR DEFAULT NULL)
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
                             
  ---
  FUNCTION GET_VAR_DNI_CLIENTE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNroTarjeta_in  IN CHAR) 
  RETURN VARCHAR2;
  
  FUNCTION GET_F_DATOS_CLIENTE(cDniCliente_in PBL_CLIENTE.DNI_CLI%TYPE) 
  RETURN FARMACURSOR;
  
  FUNCTION F_VAR_ACTUALIZAR_REGISTRO(cNroTarjetaPuntos IN FID_TARJETA.COD_TARJETA%TYPE,
                                     cNroDocumento_in IN PBL_CLIENTE.DNI_CLI%TYPE,
                                     cIndEnvioOrbis  IN CHAR,
                                     cNroTarjetFidelizacion IN CHAR)
    RETURN CHAR;
   
   
/*********************************************************/


  -- Author  : ASOSA
  -- Created : 09/02/2015
  -- Descripcion : Listar los campos a mostrar
  FUNCTION FID_F_CUR_LISTA_FIDELIZACION 
  RETURN FarmaCursor;
  
  -- Author  : ASOSA
  -- Created : 09/02/2015
  -- Descripcion : Calcular si un campo debe ser visible
  FUNCTION FID_F_GET_IND_VISIBLE(cCodCampo_in in char)
    RETURN char;

  -- Author  : ASOSA
  -- Created : 09/02/2015
  -- Descripcion : Obtener lista de maestro detalle por codigo de maestro
    FUNCTION GET_MAE_DETALLE(codMaestro in number)
 RETURN FarmaCursor;
 
   -- Author  : ASOSA
  -- Created : 09/02/2015
  -- Descripcion : Insert/Update cliente en pbl_cliente
     PROCEDURE FID_P_INSERT_CLIENTE_02(vDni_cli    IN CHAR,
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
                                 cTipDoc     IN CHAR,
                                 --
                                 cIndLineaOrbis in CHAR,
                                 vCelular in varchar2,
                                 vDepartamento in varchar2,
                                 vProvincia in varchar2,
                                 vDistrito in varchar2,
                                 vTipoDireccion in varchar2,
                                 vTipoLugar in varchar2,
                                 vReferencias in varchar2
                                 --
                                 );
                                 
  -- Author  : ASOSA
  -- Created : 09/02/2015
  -- Descripcion : Devolver cursor para imprimir voucher con datos de puntos fuera del comprobante.
  FUNCTION F_IMPR_VOU_INFO_PTOS(cCodGrupoCia_in       IN CHAR,
                                cCodCia_in            IN CHAR,
                                cCodLocal_in          IN CHAR,
                                vNumPedVta_in         IN CHAR,
                                valorAhorro_in        IN NUMBER,
                                cDocTarjetaPtos_in    IN VARCHAR2 DEFAULT NULL
                                 )
  RETURN FARMACURSOR;

/*  -- Author  : ASOSA
  -- Created : 09/02/2015
  -- Descripcion : Insertar en tabla temporal los datos de puntos
  PROCEDURE INS_IMP_PTOS(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in in char,
                               cSecCompPago_in in char,
                               cIndVarios_in in char DEFAULT 'N',
                               valorAhorro_in in number
                               );*/

  -- Author  : ASOSA
  -- Created : 09/02/2015
  -- Descripcion : Determinar si la informacion de puntos debe salir en la impresion de un pedido con varios comprobantes.
    FUNCTION FID_F_GET_IND_VARIOS_COMP2(cCodGrupoCia_in   IN CHAR,
                                                                                     cCodLocal_in      IN CHAR,
                                                                                     vNumPedVta_in in char)
    RETURN char; 

  -- Author  : ASOSA
  -- Created : 18/02/2015
  -- Descripcion : Determinar si la informacion de puntos debe salir en la impresion de un pedido con un comprobante.
     FUNCTION FID_F_GET_IND_UN_COMP2(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 vNumPedVta_in in char)
    RETURN char;
    
  -- Author  : ASOSA
  -- Created : 20/02/2015
  -- Descripcion : obtener parte del nombre completo de una persona
    function FN_NOMBRE_CAMPO(A_CADENA VARCHAR2, 
                                      TIPO INTEGER DEFAULT 0) 
      RETURN VARCHAR;

  -- Author  : ASOSA
  -- Created : 20/02/2015
  -- Descripcion : Obtener apelidos de un nombre.
       FUNCTION FID_F_GET_APELLIDOS(vNombre_in in varchar2)
    RETURN varchar2;
  -- Author  : ASOSA
  -- Created : 27/02/2015
  -- Descripcion : Insertar determinados procesos del fv para puntos.
  PROCEDURE FID_P_INS_PROC_PTOS(cCodGrupoCia_in     CHAR,
                                cCodCia_in          CHAR,
                                cCodLocal_in        CHAR,
                                vTipoOperacion_in   VARCHAR2,
                                vOperacion_in       VARCHAR2,
                                vInput_in           VARCHAR2,
                                vEmpleado_id_in     VARCHAR2,
                                vTransaccionId_in   VARCHAR2,
                                vNumAutorizacion_in VARCHAR2,
                                vNumTarjeta_in      VARCHAR2,
                                vDocIdentidad_in    VARCHAR2,
                                vEstado             VARCHAR2,
                                vOutput             VARCHAR2,
                                vFecha              VARCHAR2);
  -- Author  : ASOSA
  -- Created : 06/03/2015
  -- Descripcion : Retorna numero formateado.                   
 FUNCTION FID_F_GET_NUM_FORMATED(nNumber_in in number)
    RETURN varchar2;

  -- Author  : ASOSA
  -- Created : 23/03/2015
  -- Descripcion : Retorna numero formateado con decimales.                       
    FUNCTION FID_F_GET_NUM_FORMATED_02(nNumber_in in number)
    RETURN varchar2;

  -- Author  : ASOSA
  -- Created : 10/03/2015
  -- Descripcion : Determina si se imprimira o no el mensajaso de experto de ahorro
    FUNCTION FID_F_GET_IND_IMPR(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in in char,
                               cSecCompPago_in in char,
                               nAhorroTotal_in in number)
    RETURN char;

  -- Author  : ASOSA
  -- Created : 10/03/2015
  -- Descripcion : Determinar texto para mostrar al mensajaso de xperto de ahorro
    FUNCTION FID_F_GET_TEXT_EXPERT(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in in char,
                               cSecCompPago_in in char,
                               valorAhorro_in in number)
    RETURN FARMACURSOR;

  -- Author  : ASOSA
  -- Created : 16/04/2015
  -- Descripcion : Obtener ahorro si es que lo hay. Este ahorro se muestra en el texto mas abajo tambien,
  --             solo que quieren que aca tambien se vea, por eso esta frankenstain.
    FUNCTION FID_F_GET_AHORRO_F(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 vNumPedVta_in in char,
                                 cSecCompPago_in   in char)
    RETURN VARCHAR2;

  -- Author  : ASOSA
  -- Created : 20/04/2015
  -- Descripcion : Listar campos para el mnto de fidelizados
  FUNCTION FID_F_CUR_LISTA_FIDELIZACION02 
    RETURN FarmaCursor;
    
  FUNCTION F_IMPR_AFILIACION_PTOS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumDoc_in      IN CHAR,
                                  cSecUsu_in      IN CHAR)
    RETURN FarmaCursor;
  
  PROCEDURE IMP_VOUCHER_PTOS(cCodGrupoCia_in      IN CHAR,
                             cCodLocal_in         IN CHAR,
                             cNumPedVta_in        IN CHAR,
                             cSecCompPago_in      IN CHAR,
                             cIndVarios_in        IN CHAR DEFAULT 'N',
                             valorAhorro_in       IN NUMBER,
                             vIdDoc_in            IN VARCHAR2,
                             vIpPc_in             IN VARCHAR2,
                             cDocTarjetaPtos_in   IN VARCHAR2 DEFAULT NULL);
                             
  FUNCTION FID_F_GET_IND_UN_COMP(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      vNumPedVta_in     IN CHAR)
  RETURN CHAR;
  
  FUNCTION FID_F_GET_IND_VARIOS_COMP(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in        IN CHAR,
                                     vNumPedVta_in       IN CHAR)
  RETURN CHAR;
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
         WHERE IND_MOD = CC_MOD_TAR_FID
         AND   IND_OBLIGATORIO = 'S'
         AND   FID_F_GET_IND_VISIBLE(COD_CAMPO) = 'S';

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
	--ERIOS 24.03.2015 Se comenta porque la informacion llega por Viajero
    /*if (vCount = 0) then
        FID_P_INSERTA_TARJETA_FID(pCodTarjeta,
                                  pCodLocal,
                                  'FARMAVENTA');
        vCount := 1;
    END IF;*/

    RETURN TO_CHAR(vCount);

  END FID_F_VAR_VALIDA_TARJETA;

  /**************************************************************************/
  /*
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
  */

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
       WHERE A.IND_ESTADO = 'A'
         AND A.DNI_CLI = ( 
             SELECT B.DNI_CLI
             FROM FID_TARJETA B
             WHERE B.COD_TARJETA = pCodTarjeta
             );
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
	iy INTEGER:=0;
  BEGIN

--22.04.2015 Cambios de AESCATE
select count(1)
into iy
from PTOVENTA.vta_rango_tarjeta
WHERE DESDE<= substr(pCodTarjeta,1,12)
AND HASTA>= substr(pCodTarjeta,1,12)
/*WHERE DESDE<= pCodTarjeta
AND HASTA>= pCodTarjeta*/
AND COD_TIPO_TARJETA='TC';
if iy<>0 then
  vDni_cli_2 := 'D'||vDni_cli||'C';
ELSE
--
    vDni_cli_2 := vDni_cli;
    --dubilluz 25.07.2011
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);
end if;

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
                                 cUserValida IN CHAR default 'N',
                                 --INI ASOSA - 06/02/2015 - PTOSYAYAYAYA
                                 cIndNuevoProceso in CHAR DEFAULT 'N',
                                 cIndLineaOrbis in CHAR DEFAULT 'N',
                                 vCelular in varchar2  DEFAULT ' ',
                                 vDepartamento in varchar2  DEFAULT ' ',
                                 vProvincia in varchar2  DEFAULT ' ',
                                 vDistrito in varchar2  DEFAULT ' ',
                                 vTipoDireccion in varchar2  DEFAULT ' ',
                                 vTipoLugar in varchar2  DEFAULT ' ',
                                 vReferencias in varchar2 DEFAULT ' '
                                 --FIN ASOSA - 06/02/2015 - PTOSYAYAYAYA
                                 ) is

    PRAGMA AUTONOMOUS_TRANSACTION;

    vCount      NUMBER;
    vCantCampo1 number;
    vCantCampo2 number;

    vCantidad    number;
    vCodTrabRRHH VARCHAR2(20);
    vDni_cli_2   varchar2(30);
    vCount02      NUMBER;
	iy INTEGER:=0;	
  BEGIN

--22.04.2015 Cambios de AESCATE
select count(1)
into iy
from PTOVENTA.vta_rango_tarjeta
WHERE DESDE<= substr(pCodTarjeta,1,12)
AND HASTA>= substr(pCodTarjeta,1,12)
/*WHERE DESDE<= pCodTarjeta
AND HASTA>= pCodTarjeta*/
AND COD_TIPO_TARJETA='TC';
if iy<>0 then
  vDni_cli_2 := 'D'||vDni_cli||'C';
ELSE
--
    vDni_cli_2 := vDni_cli;
    --dubilluz 25.07.2011
    vDni_cli_2 := fid_valida_dni(vDni_cli_2);
end if;
  
--INI ASOSA - 06/02/2015 - PTOSYAYAYAYA
IF cIndNuevoProceso = 'S' THEN
                    FID_P_INSERT_CLIENTE_02(vDni_cli_2 ,
                                                                                 vNom_cli ,
                                                                                 vApat_cli ,
                                                                                 vAmat_cli ,
                                                                                 vEmail_cli ,
                                                                                 vFono_cli ,
                                                                                 vSexo_cli ,
                                                                                 vDir_cli  ,
                                                                                 vFecNac_cli,
                                                                                 pCodTarjeta ,
                                                                                 pCodLocal  ,
                                                                                 pUser  ,
                                                                                 pIndEstado,
                                                                                 cTipDoc  ,
                                                                                 cIndLineaOrbis ,
                                                                                 vCelular ,
                                                                                 vDepartamento ,
                                                                                 vProvincia ,
                                                                                 vDistrito,
                                                                                 vTipoDireccion,
                                                                                 vTipoLugar ,
                                                                                 vReferencias);

ELSE
--FIN ASOSA - 06/02/2015 - PTOSYAYAYAYA


    SELECT COUNT(*)
      INTO vCount
      FROM PBL_CLIENTE
    --WHERE TRIM(DNI_CLI) = vDni_cli
     WHERE DNI_CLI = vDni_cli_2;
       --AND IND_ESTADO = 'A';   //ASOSA - 06/04/2015 - PTOSYAYAYAYA - SE COMENTA PORQUE AHORA RESULTA QUE PUEDE QUE HALLA UN CLIENTE POR ACTUALIZAR INACTIVO

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
           IND_ESTADO,
           --INI ASOSA - 06/04/2015 - PTOSYAYAYAYA
           IND_ENVIADO_ORBIS,
           CELL_CLI,
           DEPARTAMENTO,
           PROVINCIA,
           DISTRITO,
           TIPO_DIRECCION,
           TIPO_LUGAR,
           REFERENCIAS
           --FIN ASOSA - 06/04/2015 - PTOSYAYAYAYA
           )
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
                  --TO_NUMBER(vFono_cli, '9999999.000')),
                  vFono_cli),
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
           pIndEstado,
           --INI ASOSA - 06/04/2015 - PTOSYAYAYAYA
           cIndLineaOrbis,
           vCelular,
           vDepartamento,
           vProvincia,
           vDistrito,
           vTipoDireccion,
           vTipoLugar,
           vReferencias
           --FIN ASOSA - 06/04/2015 - PTOSYAYAYAYA
           );
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
                                      --TO_NUMBER(vFono_cli, '9999999.000')),     
                                      vFono_cli),
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
                 L.USU_MOD_CLIENTE  = pUser,
                 --INI ASOSA - 06/04/2015 - PTOSYAYAYAYA
                 L.IND_ENVIADO_ORBIS = cIndNuevoProceso,
                 L.CELL_CLI = vCelular ,
                 L.DEPARTAMENTO = vDepartamento ,
                 L.PROVINCIA = vProvincia ,
                 L.DISTRITO = vDistrito ,
                 L.TIPO_DIRECCION = vTipoDireccion ,
                 L.TIPO_LUGAR = vTipoLugar ,
                 L.REFERENCIAS = vReferencias ,
                 L.EMAIL = vEmail_cli
                 --FIN ASOSA - 06/04/2015 - PTOSYAYAYAYA
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
                                        FROM DUAL),
               --INI ASOSA - 06/04/2015 - PTOSYAYAYAYA
               X.IND_ENVIADO_ORBIS = cIndNuevoProceso,
               X.CELL_CLI = vCelular ,
               X.DEPARTAMENTO = vDepartamento ,
               X.PROVINCIA = vProvincia ,
               X.DISTRITO = vDistrito ,
               X.TIPO_DIRECCION = vTipoDireccion ,
               X.TIPO_LUGAR = vTipoLugar ,
               X.REFERENCIAS = vReferencias ,
               X.EMAIL = vEmail_cli
                 --FIN ASOSA - 06/04/2015 - PTOSYAYAYAYA
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

      

    END IF;
END IF;
COMMIT;
  /*EXCEPTION

    WHEN OTHERS THEN

      dbms_output.put_line('error'||sqlerrm);

      rollback;*/

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
  FUNCTION FID_F_CUR_TARJETA_X_DNI(cDni_in               IN CHAR,
                                   cCodLocal_in          IN CHAR,
                                   cNroTarjetaLealtad_in IN CHAR DEFAULT NULL)
    RETURN FarmaCursor
  
   IS
    curLista FarmaCursor;
    vDni VARCHAR2(20);
    iy INTEGER :=0;
  BEGIN
    -- KMONCADA 24.09.2014 VERIFICA LONGITUD DE DNI.
    IF (LENGTH(TRIM(cDni_in)) = 8) THEN
      ptoventa_fid_reniec.p_genera_tarjeta_dni('001', cCodLocal_in, trim(cDni_in), cNroTarjetaLealtad_in);
    END IF;
    
    vDni := cDni_in;
    IF cNroTarjetaLealtad_in IS NOT NULL THEN 
    select count(1)
      into iy
      from PTOVENTA.vta_rango_tarjeta
      WHERE DESDE<= substr(cNroTarjetaLealtad_in,1,12)
      AND HASTA>= substr(cNroTarjetaLealtad_in,1,12)
      /*WHERE DESDE<= pCodTarjeta
      AND HASTA>= pCodTarjeta*/
      AND COD_TIPO_TARJETA='TC';
      
      if iy<>0 then
        vDni := 'D'||cDni_in||'C';
      end if;
      
    END IF;
    
    
    IF cNroTarjetaLealtad_in IS NULL THEN
    
      OPEN curLista FOR
        SELECT COD_TARJETA
          FROM FID_TARJETA
         WHERE DNI_CLI = vDni
         AND   NVL(IND_PUNTOS,'N') = 'N';
    ELSE
      IF FARMA_PUNTOS.F_VAR_TARJ_VALIDA('001',cCodLocal_in,cNroTarjetaLealtad_in) = 'N' THEN
        OPEN curLista FOR
         SELECT COD_TARJETA
           FROM FID_TARJETA
          WHERE DNI_CLI = vDni
            AND NVL(IND_PUNTOS, 'N') = 'N';
      ELSE
         OPEN curLista FOR
           SELECT COD_TARJETA
            FROM FID_TARJETA
           WHERE DNI_CLI = vDni
             AND COD_TARJETA = cNroTarjetaLealtad_in;
      END IF;
      
    END IF;
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
                                  
--INI ASOSA - 26/02/2015 - PTOSYAYAYAYA

               decode(v.cod_campo,

                      '012',

                      decode(v.pidedato, 0, 0, decode(l.Departamento, null, 0, 1)),

                      0) +

               decode(v.cod_campo,

                      '013',

                      decode(v.pidedato, 0, 0, decode(l.Provincia, null, 0, 1)),

                      0) +

              decode(v.cod_campo,

                      '014',

                      decode(v.pidedato, 0, 0, decode(l.Distrito, null, 0, 1)),

                      0) +

               decode(v.cod_campo,

                      '015',

                      decode(v.pidedato, 0, 0, decode(l.Tipo_Direccion, null, 0, 1)),

                      0) +

               decode(v.cod_campo,

                      '016',

                      decode(v.pidedato, 0, 0, decode(l.Tipo_Direccion, null, 0, 1)),

                      0) +

               decode(v.cod_campo,

                      '017',

                      decode(v.pidedato, 0, 0, decode(l.Tipo_Lugar, null, 0, 1)),

                      0) +                      

                      

               --FIN ASOSA - 26/02/2015 - PTOSYAYAYAYA


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
                  nvl('' || A.Email, 'N')|| 'Ã' ||                  
                  nvl('' || A.Cell_Cli, 'N')|| 'Ã' ||
                  nvl('' || A.Departamento, 'N')|| 'Ã' ||
                  nvl('' || A.Provincia, 'N')|| 'Ã' ||
                  nvl('' || A.Distrito, 'N')|| 'Ã' ||
                  nvl('' || A.Tipo_Direccion, 'N')|| 'Ã' ||
                  nvl('' || A.Tipo_Lugar, 'N')|| 'Ã' ||
                  nvl('' || A.Referencias, 'N')
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
                    nvl(cliente.correo, 'N')|| 'Ã' ||                  
                  nvl('' || cliente.Cell_Cli, 'N')|| 'Ã' ||
                  nvl('' || cliente.Departamento, 'N')|| 'Ã' ||
                  nvl('' || cliente.Provincia, 'N')|| 'Ã' ||
                  nvl('' || cliente.Distrito, 'N')|| 'Ã' ||
                  nvl('' || cliente.Tipo_Direccion, 'N')|| 'Ã' ||
                  nvl('' || cliente.Tipo_Lugar, 'N')|| 'Ã' ||
                  nvl('' || cliente.Referencias, 'N')
              from (select A.DNI_CLI dni,
                           A.NOM_CLI nombre,
                           to_char(A.FEC_NAC_CLI, 'dd/mm/yyyy') fecha,

                           A.APE_PAT_CLI ape_pat,
                           A.APE_MAT_CLI ape_mat,
                           A.SEXO_CLI    sexo,
                           A.DIR_CLI     dir,
                           A.FONO_CLI    fono,
                           A.Email       correo,
                          A.Cell_Cli,
                          A.Departamento,
                          A.Provincia,
                          A.Distrito,
                          A.Tipo_Direccion,
                          A.Tipo_Lugar,
                          A.Referencias
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
                               null correo,
                               NULL Cell_Cli,
                               NULL Departamento,
                               NULL Provincia,
                               NULL Distrito,
                               NULL Tipo_Direccion,
                               NULL Tipo_Lugar,
                               NULL Referencias
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
                      nvl(cliente.correo, 'N')|| 'Ã' ||                  
                      nvl('' || cliente.Cell_Cli, 'N')|| 'Ã' ||
                      nvl('' || cliente.Departamento, 'N')|| 'Ã' ||
                      nvl('' || cliente.Provincia, 'N')|| 'Ã' ||
                      nvl('' || cliente.Distrito, 'N')|| 'Ã' ||
                      nvl('' || cliente.Tipo_Direccion, 'N')|| 'Ã' ||
                      nvl('' || cliente.Tipo_Lugar, 'N')|| 'Ã' ||
                      nvl('' || cliente.Referencias, 'N')
                from (select A.DNI_CLI dni,
                             A.NOM_CLI nombre,
                             to_char(A.FEC_NAC_CLI, 'dd/mm/yyyy') fecha,

                             A.APE_PAT_CLI ape_pat,
                             A.APE_MAT_CLI ape_mat,
                             A.SEXO_CLI    sexo,
                             A.DIR_CLI     dir,
                             A.FONO_CLI    fono,
                             A.Email       correo,
                            A.Cell_Cli,
                            A.Departamento,
                            A.Provincia,
                            A.Distrito,
                            A.Tipo_Direccion,
                            A.Tipo_Lugar,
                            A.Referencias
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
                               null correo,
                               NULL Cell_Cli,
                               NULL Departamento,
                               NULL Provincia,
                               NULL Distrito,
                               NULL Tipo_Direccion,
                               NULL Tipo_Lugar,
                               NULL Referencias
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
            COMISION_VTA = cIndComision_in,
           -----------------------
           -- KMONCADA 13.04.2015 EN CASO DE PEDIDO FIDELIZADO Y SE IMPRIMIRA DNI CLIENTE EN CASO NO HAYA
           -- INGRESADO NRO DE DOCUMENTO A MOSTRAR EN EL COMPROBANTE
            RUC_CLI_PED_VTA = CASE 
                                WHEN TIP_COMP_PAGO = '01' AND TRIM(RUC_CLI_PED_VTA) IS NULL THEN
                                  vDni_cli_2
                                ELSE
                                  RUC_CLI_PED_VTA
                              END
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

  FUNCTION FID_F_CHAR_CREA_NEW_TARJ_FID(vCodGrupoCia_in    IN VARCHAR2,
                                        vCodLocal_in       IN VARCHAR2,
                                        vConcatenado_in    IN VARCHAR2,
                                        vUsuID_in          IN VARCHAR2,
                                        vNroTarjetaLealtad IN CHAR DEFAULT NULL)
    RETURN CHAR AS
    vSecuencialNumera         CHAR(6);
    vConcatenado              CHAR(12);
    vNuevaTarjetaFidelizacion CHAR(13);
  BEGIN
    IF vNroTarjetaLealtad IS NULL THEN 
      vSecuencialNumera         := TRIM(Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(vCodGrupoCia_in, vCodLocal_in, CC_COD_NUMERA), 6, '0', 'I'));
      vConcatenado              := vConcatenado_in || vSecuencialNumera;
      vNuevaTarjetaFidelizacion := GENERA_EAN13(vConcatenado);
      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(vCodGrupoCia_in, vCodLocal_in, CC_COD_NUMERA, vUsuID_in);
    ELSE
      IF FARMA_PUNTOS.F_VAR_TARJ_VALIDA(vCodGrupoCia_in, vCodLocal_in, vNroTarjetaLealtad) = 'S' THEN
        vNuevaTarjetaFidelizacion := vNroTarjetaLealtad;
      ELSE 
        vNuevaTarjetaFidelizacion := NULL;
      END IF;
      
    END IF;
    
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
      select sum(round(NVL(D.AHORRO_CAMP,d.ahorro), 10))
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

  FUNCTION GET_VAR_DNI_CLIENTE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNroTarjeta_in  IN CHAR) 
    RETURN VARCHAR2 IS
    cDniCliente FID_TARJETA.DNI_CLI%TYPE;
  BEGIN
    BEGIN
      SELECT FID.DNI_CLI
        INTO cDniCliente
        FROM FID_TARJETA FID
       WHERE FID.COD_TARJETA = cNroTarjeta_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        cDniCliente := ' ';
    END;
    
    RETURN cDniCliente;
  END;
  
  FUNCTION GET_F_DATOS_CLIENTE(cDniCliente_in PBL_CLIENTE.DNI_CLI%TYPE) 
    RETURN FARMACURSOR IS
    vCliente FARMACURSOR;
  BEGIN
    
    OPEN vCliente FOR
      SELECT NVL(A.DNI_CLI, ' ') DNI,
             NVL(A.NOM_CLI, ' ') NOMBRE,
             NVL(A.APE_PAT_CLI, ' ') APE_PATERNO,
             NVL(A.APE_MAT_CLI, ' ') APE_MATERNO,
             CASE 
               WHEN A.FEC_NAC_CLI IS NULL THEN 
                 ' '
               ELSE
                 TO_CHAR(A.FEC_NAC_CLI,'DD/MM/YYYY') 
             END FECHA_NAC,
             NVL(A.SEXO_CLI, ' ') SEXO,
             NVL(A.DIR_CLI, ' ') DIRECCION,
             NVL(A.FONO_CLI||'', ' ') TELEFONO,
             NVL(A.EMAIL, ' ') CORREO,
             NVL(A.CELL_CLI||'', ' ') CELULAR,
             NVL(A.DEPARTAMENTO, ' ') DEPARTAMENTO,
             NVL(A.PROVINCIA, ' ') PROVINCIA,
             NVL(A.DISTRITO, ' ') DISTRITO,
             NVL(A.TIPO_DIRECCION, ' ') TIPO_DIRECCION,
             NVL(A.REFERENCIAS, ' ') REFERENCIA,
             NVL(A.TIPO_LUGAR, ' ') TIPO_LUGAR
      FROM PBL_CLIENTE A
      WHERE A.DNI_CLI = cDniCliente_in;
    
    RETURN vCliente;
  END;
  
  FUNCTION F_VAR_ACTUALIZAR_REGISTRO(cNroTarjetaPuntos IN FID_TARJETA.COD_TARJETA%TYPE,
                                     cNroDocumento_in IN PBL_CLIENTE.DNI_CLI%TYPE,
                                     cIndEnvioOrbis  IN CHAR,
                                     cNroTarjetFidelizacion IN CHAR)
    RETURN CHAR IS
    PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
    
    UPDATE PBL_CLIENTE A
    SET A.IND_ENVIADO_ORBIS = cIndEnvioOrbis
    WHERE A.DNI_CLI = cNroDocumento_in;
    
    UPDATE FID_TARJETA A
       SET A.IND_ENVIADO_ORBIS = cIndEnvioOrbis
     WHERE A.COD_TARJETA = cNroTarjetFidelizacion
       AND A.DNI_CLI = cNroDocumento_in;
    COMMIT;
    RETURN 'S';
  END;
  
    /**************************************************************************/
  FUNCTION FID_F_CUR_LISTA_FIDELIZACION RETURN FarmaCursor IS

    curCamp FarmaCursor;
    siObligatorio char(1) := 'S';
    noObligatorio char(1) := 'N';
  BEGIN

    OPEN curCamp FOR
      SELECT *
      FROM
      (SELECT decode(IND_OBLIGATORIO,'S','(*) ','') || trim(NOM_CAMPO)  || 'Ã' || 
                       ' ' || 'Ã' || 
                       CF.COD_CAMPO || 'Ã' ||
                       IND_TIP_DATO || 'Ã' || 
                       IND_SOLO_LECTURA || 'Ã' ||
                       IND_OBLIGATORIO || 'Ã' || 
                       FID_F_GET_IND_VISIBLE(CF.COD_CAMPO)
        FROM FID_CAMPOS_FORMULARIO CF, FID_CAMPOS_FIDELIZACION CO
       WHERE CF.COD_CAMPO = CO.COD_CAMPO
         AND CO.IND_MOD = CC_MOD_TAR_FID
         AND FID_F_GET_IND_VISIBLE(CF.COD_CAMPO) = 'S'
         AND IND_OBLIGATORIO = siObligatorio
         ORDER BY CO.SEC_CAMPO ASC)
       UNION ALL
       SELECT *
      FROM
       (SELECT decode(IND_OBLIGATORIO,'S','(*) ','') || trim(NOM_CAMPO)  || 'Ã' || 
                       ' ' || 'Ã' || 
                       CF.COD_CAMPO || 'Ã' ||
                       IND_TIP_DATO || 'Ã' || 
                       IND_SOLO_LECTURA || 'Ã' ||
                       IND_OBLIGATORIO || 'Ã' || 
                       FID_F_GET_IND_VISIBLE(CF.COD_CAMPO)
        FROM FID_CAMPOS_FORMULARIO CF, FID_CAMPOS_FIDELIZACION CO
       WHERE CF.COD_CAMPO = CO.COD_CAMPO
         AND CO.IND_MOD = CC_MOD_TAR_FID
         AND FID_F_GET_IND_VISIBLE(CF.COD_CAMPO) = 'S'
         AND IND_OBLIGATORIO = noObligatorio
         ORDER BY CO.SEC_CAMPO ASC);

    RETURN curCamp;
  END FID_F_CUR_LISTA_FIDELIZACION;

  /***************************************************************************/   
  
    FUNCTION FID_F_GET_IND_VISIBLE(cCodCampo_in in char)
    RETURN char IS

    ind char := 'N';
    horaIni date;
    horaFin date;
    indVisibleDefault char(1);
  BEGIN

      SELECT TO_DATE(TO_CHAR(SYSDATE,'dd/MM/yyyy') || ' ' || TO_CHAR(CO.HORA_INI,'HH:MI:SS A.M.'),'dd/MM/yyyy HH:MI:SS A.M.'),
                     TO_DATE(TO_CHAR(SYSDATE,'dd/MM/yyyy') || ' ' || TO_CHAR(CO.HORA_FIN,'HH:MI:SS A.M.'),'dd/MM/yyyy HH:MI:SS A.M.'),
                        CO.IND_VISIBLE_DEFAULT
        INTO horaIni,
                     horaFin,
                     indVisibleDefault
        FROM FID_CAMPOS_FIDELIZACION CO
       WHERE CO.IND_MOD = CC_MOD_TAR_FID
       AND CO.COD_CAMPO = cCodCampo_in;
       
       IF SYSDATE <= horaFin AND SYSDATE >= horaIni THEN
                         ind := 'S';
       ELSE
                         IF indVisibleDefault = 'S' THEN
                                ind := 'S';
                         END IF;
       END IF;

    RETURN ind;
  END;
  
    /* ******************************************************** */
    
FUNCTION GET_MAE_DETALLE(codMaestro in number)
 RETURN FarmaCursor
IS
    curProvs FarmaCursor;
BEGIN
     OPEN curProvs FOR
     SELECT A.VALOR1 || 'Ã' || 
                     A.DESCRIPCION
     FROM MAESTRO_DETALLE A
     WHERE A.COD_MAESTRO = codMaestro
     AND A.ESTADO = 1;

     RETURN curProvs;
END;

    /* ******************************************************** */
    
    PROCEDURE FID_P_INSERT_CLIENTE_02(vDni_cli    IN CHAR,
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
                                 cTipDoc     IN CHAR,
                                 cIndLineaOrbis in CHAR,
                                 vCelular in varchar2,
                                 vDepartamento in varchar2,
                                 vProvincia in varchar2,
                                 vDistrito in varchar2,
                                 vTipoDireccion in varchar2,
                                 vTipoLugar in varchar2,
                                 vReferencias in varchar2
                                 ) AS
    vCount      NUMBER;
    pTipoDoc char(3) := '';

  BEGIN

    SELECT COUNT(*)
      INTO vCount
      FROM PBL_CLIENTE
     WHERE DNI_CLI = vDni_cli;
     
     IF LENGTH(vDni_cli) = 8 THEN
           pTipoDoc := '01 ';
       ELSIF LENGTH(vDni_cli) = 9 THEN
             pTipoDoc := '02 ';
         ELSE
             pTipoDoc := '03 ';
           END IF;
        

       IF vCount > 0 THEN
       
                  UPDATE PBL_CLIENTE
                  SET dni_cli = vDni_cli,   
                      nom_cli = vNom_cli, 
                         ape_pat_cli = vApat_cli,
                        ape_mat_cli = vAmat_cli,
                        fono_cli = vFono_cli,
                        sexo_cli = vSexo_cli,
                        dir_cli = vDir_cli,
                        fec_nac_cli = vFecNac_cli,
                        fec_MOD_cliente = SYSDATE,
                        usu_MOD_cliente = pUser,
                        ind_estado = pIndEstado,
                        email = vEmail_cli, 
                        cod_local_origen = pCodLocal, 
                        cell_cli =vCelular ,                                                                  
                        departamento = vDepartamento, 
                        provincia = vProvincia, 
                        distrito = vDistrito , 
                        tipo_direccion = vTipoDireccion, 
                        referencias = vReferencias,                                                                     
                        tipo_lugar = vTipoLugar,
                        ind_enviado_orbis = cIndLineaOrbis,
                        COD_TIP_DOCUMENTO = pTipoDoc
                  WHERE DNI_CLI = vDni_cli;
                                            
                                            UPDATE FID_TARJETA
                                         SET DNI_CLI         = vDni_cli,
                                             USU_MOD_TARJETA = pUser,
                                             FEC_MOD_TARJETA = SYSDATE,
                                             cod_local       = pCodLocal,
                                             IND_ENVIADO_ORBIS = cIndLineaOrbis     --ASOSA - 17/02/2015 - PTOSYAYAYAYA
                                       WHERE COD_TARJETA = pCodTarjeta;
                                            
       ELSE
                 INSERT INTO PBL_CLIENTE(dni_cli, 
                                                                    nom_cli, 
                                                                    ape_pat_cli, 
                                                                    ape_mat_cli, 
                                                                    fono_cli, 
                                                                    sexo_cli, 
                                                                    dir_cli, 
                                                                    fec_nac_cli, 
                                                                    fec_crea_cliente, 
                                                                    usu_crea_cliente, 
                                                                    ind_estado, 
                                                                    email, 
                                                                    cod_local_origen, 
                                                                    cell_cli,                                                                  
                                                                    departamento, 
                                                                    provincia, 
                                                                    distrito, 
                                                                    tipo_direccion, 
                                                                    referencias,                                                                     
                                                                    tipo_lugar,
                                                                    ind_enviado_orbis,
                                                                    COD_TIP_DOCUMENTO
                                                                    )
                                VALUES(
                                vDni_cli,
                                vNom_cli,
                                vApat_cli,
                                vAmat_cli,
                                vFono_cli,
                                vSexo_cli,
                                vDir_cli,
                                TO_DATE(TRIM(vFecNac_cli),'dd/mm/YYYY'),
                                SYSDATE,
                                pUser,
                                pIndEstado,
                                vEmail_cli,
                                pCodLocal,
                                vCelular,
                                vDepartamento,
                                vProvincia,
                                vDistrito,
                                vTipoDireccion,
                                vReferencias,
                                vTipoLugar,
                                cIndLineaOrbis,
                                pTipoDoc
                                );
                
                                             UPDATE FID_TARJETA
                                         SET DNI_CLI         = vDni_cli,
                                             USU_MOD_TARJETA = pUser,
                                             FEC_MOD_TARJETA = SYSDATE,
                                             cod_local       = pCodLocal,
                                             IND_ENVIADO_ORBIS = cIndLineaOrbis       --ASOSA - 17/02/2015 - PTOSYAYAYAYA
                                       WHERE COD_TARJETA = pCodTarjeta;
                                            
       END IF;
 

  END;
  
  	/****************************************************************************************************/
  
  FUNCTION F_IMPR_VOU_INFO_PTOS(cCodGrupoCia_in       IN CHAR,
                                cCodCia_in            IN CHAR,
                                cCodLocal_in          IN CHAR,
                                vNumPedVta_in         IN CHAR,
                                valorAhorro_in        IN NUMBER,
                                -- KMONCADA 23.06.2015 NUMERO DE DOCUMENTO DE LA TARJETA DE PUNTOS
                                cDocTarjetaPtos_in    IN VARCHAR2 DEFAULT NULL
                                 )
  RETURN FARMACURSOR
  IS
  
    curTicket FARMACURSOR;
    vIdDoc VARCHAR2(100);
    vIpPc  VARCHAR2(100);
  BEGIN
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc := FARMA_PRINTER.F_GET_IP_SESS;
    -- DELETE TMP_DOCUMENTO_ELECTRONICOS;
    IMP_VOUCHER_PTOS(cCodGrupoCia_in => cCodGrupoCia_in,
                     cCodLocal_in => cCodLocal_in,
                     cNumPedVta_in => vNumPedVta_in,
                     cSecCompPago_in => null,
                     cIndVarios_in => 'S',
                     valorAhorro_in => valorAhorro_in,
                     vIdDoc_in => vIdDoc,
                     vIpPc_in => vIpPc,
                     cDocTarjetaPtos_in => cDocTarjetaPtos_in);
    /*PTOVENTA_FIDELIZACION.INS_IMP_PTOS(cCodGrupoCia_in,
                 cCodLocal_in,
                 vNumPedVta_in,
                 NULL,
                 'S',
                 valorAhorro_in);*/
                                 
    
    /*OPEN curTicket FOR
        SELECT A.VALOR, A.TAMANIO, A.ALINEACION, A.BOLD, A.AJUSTE
        FROM TMP_DOCUMENTO_ELECTRONICOS A;
        */
    curTicket := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
    RETURN curTicket;
  

  END;  

    /* ******************************************************** */
    
/*PROCEDURE INS_IMP_PTOS(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in in char,
                               cSecCompPago_in in char,
                               cIndVarios_in in char DEFAULT 'N',
                               valorAhorro_in in number
                               )
IS  
   tarjeta varchar2(25) := '';
   nombreCliente varchar2(100) := '';
   docIdentidad varchar2(20) := '';
   vPtoAcumulado number(12,2) := 0;
   vPtoRedimido number(12,2) := 0;
   vPtoTotal number(12,2) := 0;
        codTabGralGlosa number(3) := 458;
      
   ptosAhorro number(6,2) := 0;
     
   vTextoAhorro varchar2(100) := '';
   vTextoAcum varchar2(100) := '';   
   vTextoRedim varchar2(100) := '';
   vTextoAcumHist varchar2(100) := '';
   vTextoTotal varchar2(100) := '';
   
   codTabTextoAhorro number(3) := 471;
   codTabTextoAcum number(3) := 469;
   codTabTextoRedim number(3) := 470;
   codTabTextoAcumHist number(3) := 478;
   codTabTextoTotal number(3) := 485;
   
   vTextoAhorro2 varchar2(100) := '';
   vTextoAcum2 varchar2(100) := '';   
   vTextoRedim2 varchar2(100) := '';
   vTextoAcumHist2 varchar2(100) := '';
   vTextoTotal2 varchar2(100) := '';
   
   codTabTextoAhorro2 number(3) := 487;
   codTabTextoAcum2 number(3) := 477;
   codTabTextoRedim2 number(3) := 477;
   codTabTextoAcumHist2 number(3) := 479;
   codTabTextoTotal2 number(3) := 486;
   
   codTabTextoCuponAcum number(3) := 498;
   vTextoCuponAcum varchar2(100) := '';
   
   codTabTextoInfo number(3) := 661;
   
   codIndVerImagen number(3) := 495;
   
   cantAgregar01 number(3) := 30;
   cantAgregar02 number(3) := 15;
   cantAgregar03 number(3) := 20;
   textoAgregar varchar2(10) := ' '; 
   
   cCantidadCupon       INTEGER DEFAULT 0;
   textoCantCupon varchar2(10) := '';
   
   codTab12MesesAbajo number(3) := 499;
   textoTab12MesesAbajo varchar2(50) := '';
   
   codTabGastando number(3) := 500;
   textoTabGastando varchar2(50) := '';
   
    codTab491 number(3) := '491';
    valorTab491 varchar2(10) := '';
    vValorAhorro     NUMBER(12,3);
    valorImpr varchar2(200) := '';
   
BEGIN
           
        IF cIndVarios_in != 'S' THEN     
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('***********************************************', '0','I','N','N');
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        END IF;
        
      --OBTENGO DATOS DE CLIENTE
         select (select substr(b.num_tarj_puntos,1,4) || 
                                           replace(rpad(' ',(length(b.num_tarj_puntos)-7),'*'),' ','') || 
                                           substr(b.num_tarj_puntos,length(b.num_tarj_puntos)-3,4)
                             from dual),
                                  b.dni_cli
                                  
        into tarjeta,
                     docIdentidad
         from vta_pedido_vta_cab b
         where b.cod_grupo_cia = cCodGrupoCia_in
         and b.cod_local = cCodLocal_in
         and b.num_ped_vta = cNumPedVta_in;
         
         select cli.nom_cli || ' ' || 
                cli.ape_pat_cli || ' ' || 
                cli.ape_mat_cli
         into nombreCliente
         from pbl_cliente cli
         where cli.dni_cli = docIdentidad;

        --INSERTO IMAGEN DE PUNTOS
        IF PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(codIndVerImagen) = 'S' THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( 'IMAGEN DE PUNTOS','9','X','S','N');
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( ' ','9','D','S','N');
        END IF;
        
        --INSERTO DATOS DE CLIENTE
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
        SELECT nombreCliente ,'0','C','S','N' FROM DUAL;

        tarjeta := '****' || SUBSTR(tarjeta,5,9);

        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
        SELECT 'Tarjeta : ' || tarjeta || '    DNI/CE : ' || docIdentidad,
               '0','C','S','N' FROM DUAL; 
               
        --INI TODOS LOS TEXTOS
        SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
        INTO vTextoAhorro
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoAhorro;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
        INTO vTextoAcum
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoAcum;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
        INTO vTextoRedim
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoRedim;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO vTextoAcumHist
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoAcumHist;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
        INTO vTextoTotal
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoTotal;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
        INTO vTextoCuponAcum
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoCuponAcum;
        
        --
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO vTextoAhorro2
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoAhorro2;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO vTextoAcum2
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoAcum2;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO vTextoRedim2
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoRedim2;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO vTextoAcumHist2
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoAcumHist2;
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO vTextoTotal2
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabTextoTotal2;
        
        --
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO textoTab12MesesAbajo
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTab12MesesAbajo;   
        
        SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO textoTabGastando
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabGastando;     
        
        --FIN TODOS LOS TEXTOS  
        
        -- CALCULO DE UN MONTON DE CONCEPTOS DE PUNTOS
        IF cIndVarios_in = 'S' THEN
            SELECT SUM(NVL(DET.AHORRO, 0))
            INTO ptosAhorro
            FROM VTA_PEDIDO_VTA_DET DET
            WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
            AND DET.COD_LOCAL = cCodLocal_in
            AND DET.NUM_PED_VTA = cNumPedVta_in;     
        ELSE
            SELECT SUM(NVL(DET.AHORRO, 0))
            INTO ptosAhorro
            FROM VTA_PEDIDO_VTA_DET DET
            WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
            AND DET.COD_LOCAL = cCodLocal_in
            AND DET.NUM_PED_VTA = cNumPedVta_in
            AND DET.SEC_COMP_PAGO = cSecCompPago_in;               
        END IF;
        
          SELECT NVL(CAB.PT_ACUMULADO, 0),
                 NVL(CAB.PT_REDIMIDO, 0),
                 CASE
                   WHEN NVL(CAB.PT_TOTAL, 0) < 0 THEN
                    0
                   ELSE
                    NVL(CAB.PT_TOTAL, 0)
                 END
            INTO vPtoAcumulado, vPtoRedimido, vPtoTotal
            FROM VTA_PEDIDO_VTA_CAB CAB
           WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
             AND CAB.COD_LOCAL = cCodLocal_in
             AND CAB.NUM_PED_VTA = cNumPedVta_in;
            
           --CANTIDAD DE CUPONES 
          SELECT COUNT(1)
          INTO cCantidadCupon
          FROM VTA_CAMP_PEDIDO_CUPON CUPON
          WHERE CUPON.COD_GRUPO_CIA = cCodGrupoCia_in
          AND   CUPON.COD_LOCAL     = cCodLocal_in 
          AND   CUPON.NUM_PED_VTA   = cNumPedVta_in
          AND   CUPON.ESTADO        = 'E' 
          AND   CUPON.IND_IMPR      = 'S'; 
          
          IF cCantidadCupon < 10 THEN
             textoCantCupon := '0' || cCantidadCupon;
          ELSE
             textoCantCupon := '' || cCantidadCupon;
          END IF;
          
          --CALCULO DEL BENDITO AHORRASTE EN 12 MESES
          
          SELECT NVL(A.LLAVE_TAB_GRAL,'A')
         INTO valorTab491
         FROM PBL_TAB_GRAL A
         WHERE A.ID_TAB_GRAL = codTab491;
          
          if valorTab491 = 'A' then
              SELECT  (valorAhorro_in * TO_NUMBER(TAB.DESC_CORTA,'99990.00')) / TO_NUMBER(TAB.LLAVE_TAB_GRAL,'99990.00')
              INTO vValorAhorro
              FROM PBL_TAB_GRAL TAB
              WHERE ID_TAB_GRAL = 482;
          end if;
          
          valorImpr := FID_F_GET_NUM_FORMATED_02(nvl(vValorAhorro,0));
           
                              
              IF ptosAhorro > 0.5 THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
                SELECT --V_BOLD_I || 
                    RPAD(vTextoAhorro,43,textoAgregar) ||
                    --V_BOLD_F || 
                    V_SEPARADOR || 
                    V_BOLD_I ||
                    'S/.' || LPAD(PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED_02(ptosAhorro),12,textoAgregar) || 
                    V_BOLD_F
                    ,'9','I','N','N' 
                 FROM DUAL;
              END IF;
              
              IF vPtoRedimido > 0 THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
                SELECT --V_BOLD_I || 
                    RPAD(vTextoRedim,cantAgregar01,textoAgregar) ||
                    --V_BOLD_F || 
                    V_SEPARADOR || 
                    V_BOLD_I|| 
                    LPAD(PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(vPtoRedimido),cantAgregar02,textoAgregar) || 
                    V_BOLD_F || 
                    V_SEPARADOR || ' ' || 
                    RPAD(vTextoRedim2,cantAgregar03,textoAgregar)
                    ,'9','I','N','N' 
                 FROM DUAL;
              END IF;
                              
              IF cCantidadCupon > 0 THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
                SELECT --V_BOLD_I || 
                    RPAD(vTextoCuponAcum,cantAgregar01,textoAgregar) ||
                    --V_BOLD_F || 
                    V_SEPARADOR || 
                    V_BOLD_I|| 
                    LPAD(textoCantCupon,
                       cantAgregar02,textoAgregar) || 
                    V_BOLD_F || 
                    V_SEPARADOR || ' ' || 
                    RPAD('Cupon dscto',cantAgregar03,textoAgregar)
                    ,'9','I','N','N' 
                 FROM DUAL;
              END IF;
              
              IF vPtoAcumulado > 0 THEN  
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
                SELECT --V_BOLD_I || 
                    RPAD(vTextoAcum,cantAgregar01,textoAgregar) ||
                    --V_BOLD_F || 
                    V_SEPARADOR || 
                    V_BOLD_I|| 
                    LPAD(PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(vPtoAcumulado),cantAgregar02,textoAgregar) || 
                    V_BOLD_F || 
                    V_SEPARADOR || ' ' || 
                    RPAD(vTextoAcum2,cantAgregar03,textoAgregar)
                    ,'9','I','N','N' 
                 FROM DUAL;
                 
              END IF;
                              
              IF vPtoTotal > 0 THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
                SELECT  --V_BOLD_I || 
                    RPAD(vTextoTotal,cantAgregar01,textoAgregar) ||
                    --V_BOLD_F || 
                    V_SEPARADOR || 
                    V_BOLD_I || 
                    LPAD(PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(vPtoTotal),cantAgregar02,textoAgregar) || 
                    V_BOLD_F || 
                    V_SEPARADOR || ' ' || 
                    RPAD(vTextoTotal2,cantAgregar03,textoAgregar)
                    ,'9','I','N','N'
                FROM DUAL;
              END IF;
              
              IF valorImpr <> '0.00' THEN
                  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
                    SELECT  --V_BOLD_I || 
                        RPAD(textoTab12MesesAbajo,43,textoAgregar) ||
                        --V_BOLD_F || 
                        V_SEPARADOR || 
                        V_BOLD_I || 
                        'S/.' || LPAD(valorImpr,12,textoAgregar) || 
                        V_BOLD_F
                        ,'9','I','N','N'
                    FROM DUAL;
              END IF;
                
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
                
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
                SELECT textoTabGastando,'0','I','S','N'
                FROM DUAL;
                
              IF cIndVarios_in != 'S' THEN              
                  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
                  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('***********************************************', '0','I','N','N');
              END IF;
                        
              --TEXTO LEGAL Y MAS DE ESAS COSAS      
              INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
              SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'9','I','N','N'
               FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
                    ' '||'@'||
                    (SELECT P.LLAVE_TAB_GRAL
                     FROM PBL_TAB_GRAL P
                     WHERE P.ID_TAB_GRAL = codTabTextoInfo)
                    ||'@'||' '||'@'||
                    (SELECT P.LLAVE_TAB_GRAL
                     FROM PBL_TAB_GRAL P
                     WHERE P.ID_TAB_GRAL = codTabGralGlosa)
                    ||'@'||' '
               ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;

END;*/

    /* ******************************************************** */    
    
        FUNCTION FID_F_GET_IND_VARIOS_COMP2(cCodGrupoCia_in   IN CHAR,
                                           cCodLocal_in      IN CHAR,
                                           vNumPedVta_in in char)
    RETURN char IS

    ind char := 'N';
    cantCompPedido number(3) := 0;
    cantPedAnulado number(3) := 0;
    cantPedConCliente number(3) := 0;
  BEGIN

      SELECT COUNT(1)
     INTO cantCompPedido
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = vNumPedVta_in
     AND B.TIP_COMP_PAGO NOT IN ('03', '04');             --MENOS GUIAS Y NOTAS DE CREDITO
     --SI EL PEDIDO SOLO EMITE GUIA ES XQ ES CONVENIO AL 100% Y X LO TANTO NO DEBE TENER PUNTOS
     
      SELECT COUNT(1)
     INTO cantPedAnulado
     FROM Vta_Pedido_Vta_Cab C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
     AND C.COD_LOCAL = cCodLocal_in
     AND C.NUM_PED_VTA = vNumPedVta_in
     AND C.NUM_PED_VTA_ORIGEN IS NOT NULL;
     
     SELECT COUNT(1)
     INTO cantPedConCliente
     FROM VTA_PEDIDO_VTA_CAB F
     WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
     AND F.COD_LOCAL = cCodLocal_in
     AND F.NUM_PED_VTA = vNumPedVta_in
     AND F.NUM_PED_VTA_ORIGEN IS NULL
     AND f.est_trx_orbis IN (EST_PENDIENTE, EST_ENVIADO);
     
     if cantCompPedido > 1 
       AND cantPedAnulado = 0 
       AND cantPedConCliente = 1 then
            ind := 'S';
     end if;
     

    RETURN ind;
  END;
  
    /* ******************************************************** */
  FUNCTION FID_F_GET_IND_VARIOS_COMP(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     vNumPedVta_in in char)
    RETURN char IS

    cantCompPedido number(3) := 0;
    cantFacturas NUMBER := 0;
    indNuevoPtos CHAR(1);
    vPedidoPuntos CHAR(1);
    vUnComprobante CHAR(1);
  BEGIN
    
     -- QUE ESTA ACTIVO PTOS
     SELECT NVL(A.IND_PUNTOS,'N')
     into indNuevoPtos
     FROM PBL_LOCAL A
     WHERE A.COD_LOCAL = cCodLocal_in;
     
     IF indNuevoPtos = 'N' THEN
       RETURN 'N';
     END IF;

     -- PROCESADO POR PROGRAMA PUNTOS Y NO ES PEDIDO DE ANULACION
     SELECT DECODE(COUNT(1), 1, 'S', 'N')
     INTO vPedidoPuntos
     FROM VTA_PEDIDO_VTA_CAB F
     WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
     AND F.COD_LOCAL = cCodLocal_in
     AND F.NUM_PED_VTA = vNumPedVta_in
     AND F.NUM_PED_VTA_ORIGEN IS NULL
     AND f.est_trx_orbis IN (EST_PENDIENTE, EST_ENVIADO);
     
     IF vPedidoPuntos = 'N' THEN
       RETURN 'N';
     END IF;
     
     SELECT COUNT(1)
     INTO cantCompPedido
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = vNumPedVta_in
     AND B.TIP_COMP_PAGO NOT IN ('03', '04');             --MENOS GUIAS Y NOTAS DE CREDITO
--SI EL PEDIDO SOLO EMITE GUIA ES XQ ES CONVENIO AL 100% Y X LO TANTO NO DEBE TENER PUNTOS
     
     SELECT COUNT(1)
     INTO cantFacturas
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = vNumPedVta_in
     AND B.TIP_COMP_PAGO = '02';

     if cantCompPedido > 1 OR cantFacturas > 0 then
       RETURN 'S';
     ELSE
       RETURN 'N';
     end if;
  END;
    
      
  FUNCTION FID_F_GET_IND_UN_COMP(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      vNumPedVta_in     IN CHAR)
    RETURN char IS

     ind char := 'N';
     indNuevoPtos CHAR(1) := '';   
     cantCompPedido number(3) := 0;
     cantCompFact number(3) := 0;
     cantPedConCliente number(3) := 0;
     vPedidoPuntos CHAR(1);
     
  BEGIN
     
     -- QUE ESTA ACTIVO PTOS
     SELECT NVL(A.IND_PUNTOS,'N')
     into indNuevoPtos
     FROM PBL_LOCAL A
     WHERE A.COD_LOCAL = cCodLocal_in;
     
     IF indNuevoPtos = 'N' THEN
       RETURN 'N';
     END IF;
     
     -- PROCESADO POR PROGRAMA PUNTOS Y NO ES PEDIDO DE ANULACION
     SELECT DECODE(COUNT(1), 1, 'S', 'N')
     INTO vPedidoPuntos
     FROM VTA_PEDIDO_VTA_CAB F
     WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
     AND F.COD_LOCAL = cCodLocal_in
     AND F.NUM_PED_VTA = vNumPedVta_in
     AND F.NUM_PED_VTA_ORIGEN IS NULL
     AND f.est_trx_orbis IN (EST_PENDIENTE, EST_ENVIADO);
     
     IF vPedidoPuntos = 'N' THEN
       RETURN 'N';
     END IF;

     -- CANTIDAD DE COMPROBANTES QUE GENERA EL PEDIDO
     SELECT COUNT(1)
     INTO cantCompPedido
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = vNumPedVta_in
     AND B.TIP_COMP_PAGO NOT IN ('03', '04');             --MENOS GUIAS Y NOTAS DE CREDITO
--SI EL PEDIDO SOLO EMITE GUIA ES XQ ES CONVENIO AL 100% Y X LO TANTO NO DEBE TENER PUNTOS
     

     -- CANTIDAD DE FACTURAS QUE EMITE EL PEDIDO
     SELECT COUNT(1)
     INTO cantCompFact
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = vNumPedVta_in
     AND B.TIP_COMP_PAGO = '02';

     IF cantCompPedido = 1 AND cantCompFact = 0 THEN
       RETURN 'S';
     ELSE
       RETURN 'N';
     end if;
  END;

 
 FUNCTION FID_F_GET_IND_UN_COMP2(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 vNumPedVta_in in char)
    RETURN char IS

     ind char := 'N';
     indNuevoPtos CHAR(1) := '';   
     cantCompPedido number(3) := 0;
     cantPedAnulado number(3) := 0;
     cantPedConCliente number(3) := 0;
  BEGIN

     SELECT NVL(A.IND_PUNTOS,'N')
             into indNuevoPtos
                                   FROM PBL_LOCAL A
                                   WHERE A.COD_LOCAL = cCodLocal_in; 
                                   
     SELECT COUNT(1)
     INTO cantCompPedido
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = vNumPedVta_in
     AND B.TIP_COMP_PAGO NOT IN ('03', '04');             --MENOS GUIAS Y NOTAS DE CREDITO
--SI EL PEDIDO SOLO EMITE GUIA ES XQ ES CONVENIO AL 100% Y X LO TANTO NO DEBE TENER PUNTOS
     
      SELECT COUNT(1)
     INTO cantPedAnulado
     FROM VTA_PEDIDO_VTA_CAB C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
     AND C.COD_LOCAL = cCodLocal_in
     AND C.NUM_PED_VTA = vNumPedVta_in
     AND C.NUM_PED_VTA_ORIGEN IS NOT NULL;
     
     SELECT COUNT(1)
     INTO cantPedConCliente
     FROM VTA_PEDIDO_VTA_CAB F
     WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
     AND F.COD_LOCAL = cCodLocal_in
     AND F.NUM_PED_VTA = vNumPedVta_in
     AND F.NUM_PED_VTA_ORIGEN IS NULL
     AND f.est_trx_orbis IN (EST_PENDIENTE, EST_ENVIADO);
     
     IF indNuevoPtos = 'S' 
         AND cantCompPedido = 1 
         AND cantPedAnulado = 0  
         AND cantPedConCliente = 1 THEN
            ind := 'S';
     end if;
     

    RETURN ind;
  END;
  
    /* ******************************************************** */
    
    function FN_NOMBRE_CAMPO(A_CADENA VARCHAR2, TIPO INTEGER DEFAULT 0) RETURN VARCHAR
IS
C_CADENA VARCHAR2(2000) := A_CADENA;--TRIM(UPPER(FN_REDUCE(A_CADENA => A_CADENA)));
A_APE_PAT VARCHAR2(2000);
A_APE_MAT VARCHAR2(2000);
A_NOMBRES VARCHAR2(2000);
--C_CADENA
P_POS_I NUMBER := 1;
P_POS_F NUMBER;
P_POS NUMBER := 1;
BEGIN
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
IF P_POS_F = 0 THEN
P_POS_F := LENGTH(C_CADENA);
END IF;
A_APE_PAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F));

IF A_APE_PAT IN ( 'DEL', 'LA', 'DE') THEN
P_POS := P_POS + 1;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
A_APE_PAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F));
END IF;

IF A_APE_PAT = 'DE LA' THEN
P_POS := P_POS + 1;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
A_APE_PAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F));
END IF;

----
P_POS := P_POS + 1;
P_POS_I := P_POS_F;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS );
IF P_POS_F = 0 THEN
P_POS_F := LENGTH(C_CADENA);
END IF;
A_APE_MAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F - LENGTH(A_APE_PAT)));

----------------------------------------------------------------------------------
IF A_APE_MAT IN ( 'DEL', 'LA', 'DE') THEN
P_POS := P_POS + 1;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
A_APE_MAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F - LENGTH(A_APE_PAT)));
END IF;

IF A_APE_MAT = 'DE LA' THEN
P_POS := P_POS + 1;
P_POS_F := INSTR(C_CADENA, ' ', 1, P_POS);
A_APE_MAT := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F - LENGTH(A_APE_PAT)));
END IF;
----------------------------------------------------------------------------------
P_POS_I := P_POS_F;
P_POS_F := LENGTH(C_CADENA);
IF P_POS_I != P_POS_F THEN
A_NOMBRES := TRIM(SUBSTR(C_CADENA, P_POS_I, P_POS_F));
END IF;

A_APE_PAT := SUBSTR(A_APE_PAT, 1, 20);
A_APE_MAT := SUBSTR(A_APE_MAT, 1, 20);
A_NOMBRES := SUBSTR(A_NOMBRES, 1, 20);
IF TIPO = 0 THEN
   RETURN A_APE_PAT;
END IF;
IF TIPO = 1 THEN
   RETURN A_APE_MAT ;
END IF;
IF TIPO = 2 THEN
   RETURN A_NOMBRES ;
END IF;
EXCEPTION
WHEN OTHERS THEN
NULL;
END;

    /* ******************************************************** */

  FUNCTION FID_F_GET_APELLIDOS(vNombre_in in varchar2)
    RETURN varchar2 IS

    apellidos varchar2(100) := '';
     apePaterno varchar2(50) := '';
     apeMaterno varchar2(50) := '';
  BEGIN

     apePaterno := nvl(FN_NOMBRE_CAMPO(nvl(vNombre_in,' '), 0),'N');
     apeMaterno := nvl(FN_NOMBRE_CAMPO(nvl(vNombre_in,' '), 1),'N');

     apellidos := trim(apePaterno || ' ' || apeMaterno);

    RETURN apellidos;
  END;

    /* ******************************************************** */

  PROCEDURE FID_P_INS_PROC_PTOS(cCodGrupoCia_in     CHAR,
                                cCodCia_in          CHAR,
                                cCodLocal_in        CHAR,
                                vTipoOperacion_in   VARCHAR2,
                                vOperacion_in       VARCHAR2,
                                vInput_in           VARCHAR2,
                                vEmpleado_id_in     VARCHAR2,
                                vTransaccionId_in   VARCHAR2,
                                vNumAutorizacion_in VARCHAR2,
                                vNumTarjeta_in      VARCHAR2,
                                vDocIdentidad_in    VARCHAR2,
                                vEstado             VARCHAR2,
                                vOutput             VARCHAR2,
                                vFecha              VARCHAR2) 
  AS
    codOperacion varchar2(10) := '';
       PRAGMA AUTONOMOUS_TRANSACTION;

  BEGIN

    codOperacion :=FARMA_UTILITY.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,'077');
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, '077', 'ASOSA');
   
    INSERT INTO VTA_OPERACION_LOG
      (cod_grupo_cia,
       cod_cia,
       cod_local,
       cod_operacion,
       tipo_operacion,
       operacion,
       input,
       empleado_id,
       transaccion_id,
       numero_autorizacion,
       numero_tarjeta,
       doc_identidad,
       estado,
       output,
       fecha)
    VALUES
      (cCodGrupoCia_in,
       cCodCia_in,
       cCodLocal_in,
       codOperacion,
       vTipoOperacion_in,
       vOperacion_in,
       vInput_in,
       vEmpleado_id_in,
       vTransaccionId_in,
       vNumAutorizacion_in,
       vNumTarjeta_in,
       vDocIdentidad_in,
       vEstado,
       vOutput,
       sysdate);
    COMMIT;

 EXCEPTION
  WHEN OTHERS THEN
     ROLLBACK;

  END;
 
  
   /* ******************************************************** */
  
  FUNCTION FID_F_GET_NUM_FORMATED(nNumber_in in number)
    RETURN varchar2 
    IS

    numFormated varchar2(100) := '';
    COD NUMBER(3) := 473;
    VALOR VARCHAR2(10) := '';
  BEGIN
    
    SELECT TRIM(NVL(A.LLAVE_TAB_GRAL,'0'))
    INTO VALOR
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = COD;
  
    IF VALOR = '0' THEN
  
         if nNumber_in = 0 then
           numFormated := '0.00';
         else
           if nNumber_in < 1 then
             numFormated := '0' || trim(TO_CHAR(ROUND(nNumber_in,2),'999,999.99'));
           else
             numFormated := trim(TO_CHAR(ROUND(nNumber_in,2),'999,999.99'));
           end if;       
         end if;
     
     ELSE 
       
         if nNumber_in < 1 then
           numFormated := '0';
         else
           numFormated := TRUNC(nNumber_in);
         end if;
       
     END IF;
     

    RETURN numFormated;
  END;
  
     /* ******************************************************** */
  
  FUNCTION FID_F_GET_NUM_FORMATED_02(nNumber_in in number)
    RETURN varchar2 
    IS

    numFormated varchar2(100) := '';   
  BEGIN
  
       if nNumber_in = 0 then
         numFormated := '0.00';
       else
         if nNumber_in < 1 then
           numFormated := '0' || trim(TO_CHAR(ROUND(nNumber_in,2),'999,999.99'));
         else
           numFormated := trim(TO_CHAR(ROUND(nNumber_in,2),'999,999.99'));
         end if;       
       end if;

    RETURN numFormated;
  END;
    
    /* ******************************************************** */
    
  FUNCTION FID_F_GET_IND_IMPR(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in in char,
                               cSecCompPago_in in char,
                               nAhorroTotal_in in number)
    RETURN char 
    IS
    
    ind char := 'N';
     indNuevoPtos CHAR(1) := '';   
     cantCompPedido number(3) := 0;
     cantPedAnulado number(3) := 0;
     cantPedConCliente number(3) := 0;
     dscto number(12,2) := 0;
     codTabMinimo number(3) := 474;
     cantidad number(12,2) := 0;
     
     valIndTotal varchar2(10) := '';
     indTotal number(3) := 491;
  BEGIN
    
     SELECT NVL(T.LLAVE_TAB_GRAL,' ')
        INTO valIndTotal
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = indTotal;
    
     SELECT SUM(B.TOTAL_DESC_E)
     INTO dscto
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = cNumPedVta_in
     and B.SEC_COMP_PAGO = cSecCompPago_in;
     
     if valIndTotal = 'A' then
       dscto := nAhorroTotal_in;
     end if;
         
     
     SELECT to_number(NVL(T.LLAVE_TAB_GRAL,'0'),'999,999.99')
        INTO cantidad
        FROM PBL_TAB_GRAL T
        WHERE T.ID_TAB_GRAL = codTabMinimo;
     
     SELECT NVL(A.IND_PUNTOS,'N')
             into indNuevoPtos
     FROM PBL_LOCAL A
     WHERE A.COD_LOCAL = cCodLocal_in; 
                                   
     SELECT COUNT(1)
     INTO cantCompPedido
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = cNumPedVta_in
     AND B.TIP_COMP_PAGO NOT IN ('03', '04');             --MENOS GUIAS Y NOTAS DE CREDITO
--SI EL PEDIDO SOLO EMITE GUIA ES XQ ES CONVENIO AL 100% Y X LO TANTO NO DEBE TENER PUNTOS
     
      SELECT COUNT(1)
     INTO cantPedAnulado
     FROM VTA_PEDIDO_VTA_CAB C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
     AND C.COD_LOCAL = cCodLocal_in
     AND C.NUM_PED_VTA = cNumPedVta_in
     AND C.NUM_PED_VTA_ORIGEN IS NOT NULL;
     
     SELECT COUNT(1)
     INTO cantPedConCliente
     FROM VTA_PEDIDO_VTA_CAB F
     WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
     AND F.COD_LOCAL = cCodLocal_in
     AND F.NUM_PED_VTA = cNumPedVta_in
     AND F.NUM_PED_VTA_ORIGEN IS NULL
     AND f.est_trx_orbis IN (EST_PENDIENTE, EST_ENVIADO);
     
     IF indNuevoPtos = 'S' 
         AND cantCompPedido > 0
         AND cantPedAnulado = 0  
         AND cantPedConCliente = 1 
         AND dscto > cantidad THEN
            ind := 'S';
     end if;
     

    RETURN ind;
  END;
    
    /* ******************************************************** */
    
    FUNCTION FID_F_GET_TEXT_EXPERT(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in in char,
                               cSecCompPago_in in char,
                               valorAhorro_in in number)
    RETURN FARMACURSOR 
    IS

    dscto number(12,2) := 0;
    nombreCliente varchar2(100) := '';
    dni varchar2(20) := '';
    sexo char(1) := 'M';
    
    codTab491 number(3) := '491';  --tab 491 en java
    valorTab491 varchar2(10) := '';      --cod en java
    --vValorAhorro     NUMBER(12,3);
    
    expertWord varchar2(50) := 'Experta del Ahorro';
    valorImpr varchar2(200) := '';      --valor en java
    
    cursorExperto    FARMACURSOR;
    vIdDoc VARCHAR2(30);
    vIpPC VARCHAR2(30);
  BEGIN
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc := FARMA_PRINTER.F_GET_IP_SESS;
    
--    delete TMP_EXPERTO;
    
  --VALORES "A-AHORRO EN 12 MESES" O "T-TOTAL COMPROBANTE"
     SELECT NVL(A.LLAVE_TAB_GRAL,'A')
     INTO valorTab491
     FROM PBL_TAB_GRAL A
     WHERE A.ID_TAB_GRAL = codTab491;
    
  --TOTAL DEL COMPROBANTE
     -- SELECT SUM(B.TOTAL_DESC_E)
     SELECT NVL(B.VAL_DCTO_COMP,0)
     INTO dscto
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = cNumPedVta_in
     and B.SEC_COMP_PAGO = cSecCompPago_in;
     
     dscto := round((dscto * 1.18),2);
     
         select b.dni_cli
         into dni
         from vta_pedido_vta_cab b
         where b.cod_grupo_cia = cCodGrupoCia_in
         and b.cod_local = cCodLocal_in
         and b.num_ped_vta = cNumPedVta_in;
         
         select cli.nom_cli
         into nombreCliente
         from pbl_cliente cli
         where cli.dni_cli = dni;
         
         --nombreCliente := FN_NOMBRE_CAMPO(nombreCliente, 0) || ' ' || FN_NOMBRE_CAMPO(nombreCliente, 1);
         
         if  trim(dni) = '' then
             sexo := 'M';
         else
             select nvl(b.sexo_cli,'M')
             into sexo
             from pbl_cliente b
             where b.dni_cli = dni;
         end if;
         
         /*
         texto := nombreCliente || 'Ã' || 
               'Mira has ahorrado en esta compra:S/.' || FID_F_GET_NUM_FORMATED(dscto) || 'Ã' || 
               sexo;
               */
               
        --dependiendo del sexo se muestra expert@ 
        IF sexo = 'M' then
           expertWord := 'Experto del Ahorro';
        end if;
        
        if valorTab491 = 'A' then
          /*SELECT  (valorAhorro_in * TO_NUMBER(TAB.DESC_CORTA,'99990.00')) / TO_NUMBER(TAB.LLAVE_TAB_GRAL,'99990.00')
          INTO vValorAhorro
          FROM PBL_TAB_GRAL TAB
          WHERE ID_TAB_GRAL = 482;*/

           valorImpr := FID_F_GET_NUM_FORMATED_02(nvl(valorAhorro_in,0));
           -- KMONCADA 21.04.2015 CAMBIO QUE REQUIRIO ARTURO QUITAR LA PALABRA SOLES
           valorImpr := 'Ahorraste S/.' || valorImpr || ' en los ultimos 12 meses';
        else
           valorImpr := 'Mira has ahorrado en esta compra: S/.' || FID_F_GET_NUM_FORMATED(dscto);
        end if;
        

      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPC, 
                                        vValor_in => nombreCliente, 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_2, 
                                        vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                        vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPC, 
                                        vValor_in => expertWord, 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_2, 
                                        vAlineado_in => FARMA_PRINTER.ALING_CEN);
                                        
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPC, 
                                        vValor_in => valorImpr, 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                        vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                        vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPC);
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPC);
      
      cursorExperto := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc, vIpPc_in => vIpPC);
      RETURN cursorExperto;
      
     /* INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT  nombreCliente,'1','C','S','N' FROM DUAL;
      
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT  expertWord,'1','C','N','N' FROM DUAL;
      
      \* pidieron que ya no en dos lineas, porsiakso lo comento
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT  'Ahorro','1','C','N','N' FROM DUAL;
      *\
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT  valorImpr,'0','C','S','N' FROM DUAL;

INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','9','D','N','N');
      \* AS0SA - 12/05/2015 - PTOSYAYAYAYA - PIDIERON QUE ESTE TEXTO YA NO APARESCA
                     TAL VEZ LO PIDAN PONER EN OTRO MOMENTO
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT  'Monedero de ahorro','0','C','S','N' FROM DUAL;
      *\
      
INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','9','D','N','N'); 
     */
      /*
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT  ' ','1','C','N','N' FROM DUAL;
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT  ' ','9','C','N','N' FROM DUAL;
*/

/*    OPEN cursorExperto FOR
        SELECT A.VALOR, A.TAMANIO, A.ALINEACION, A.BOLD, A.AJUSTE
        FROM TMP_EXPERTO A;*/
      RETURN cursorExperto;
  END;
  
  /* COMENTADO POR SI SE ARREPIENTEN Y CAMBIAN DE OPINION DE NUEVO
      FUNCTION FID_F_GET_TEXT_EXPERT(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in in char,
                               cSecCompPago_in in char)
    RETURN FARMACURSOR 
    IS

    texto varchar2(500) := '';
    dscto number(12,2) := 0;
    nombreCliente varchar2(100) := '';
    dni varchar2(20) := '';
    sexo char(1) := 'M';
    
  BEGIN
    
    
  --TOTAL DEL COMPROBANTE
     SELECT SUM(B.TOTAL_DESC_E)
     INTO dscto
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = cNumPedVta_in
     and B.SEC_COMP_PAGO = cSecCompPago_in;
     
     dscto := round((dscto * 1.18),2);
     
     select b.nom_cli_ped_vta, b.dni_cli
        into nombreCliente, dni
         from vta_pedido_vta_cab b
         where b.cod_grupo_cia = cCodGrupoCia_in
         and b.cod_local = cCodLocal_in
         and b.num_ped_vta = cNumPedVta_in;
         
         nombreCliente := FN_NOMBRE_CAMPO(nombreCliente, 0) || ' ' || FN_NOMBRE_CAMPO(nombreCliente, 1);
         
         if  trim(dni) = '' then
             sexo := 'M';
         else
             select nvl(b.sexo_cli,'M')
             into sexo
             from pbl_cliente b
             where b.dni_cli = dni;
         end if;
         
         texto := nombreCliente || 'Ã' || 
               'Mira has ahorrado en esta compra:S/.' || FID_F_GET_NUM_FORMATED(dscto) || 'Ã' || 
               sexo;

    RETURN texto;
  END;
  */
    
    /* ******************************************************** */
    
     FUNCTION FID_F_GET_AHORRO_F(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 vNumPedVta_in     in char,
                                 cSecCompPago_in   in char)
    RETURN VARCHAR2 IS

     --ind char := 'N';
     indNuevoPtos CHAR(1) := '';   
     cantCompPedido number(3) := 0;
     cantPedAnulado number(3) := 0;
     cantPedConCliente number(3) := 0;
     
     ptosInmed NUMBER(6,2) := 0;
     AHORRO VARCHAR2(20) := 'N';
  BEGIN

     SELECT NVL(A.IND_PUNTOS,'N')
             into indNuevoPtos
                                   FROM PBL_LOCAL A
                                   WHERE A.COD_LOCAL = cCodLocal_in; 
                                   
     SELECT COUNT(1)
     INTO cantCompPedido
     FROM VTA_COMP_PAGO B
     WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
     AND B.COD_LOCAL = cCodLocal_in
     AND B.NUM_PED_VTA = vNumPedVta_in
     AND B.TIP_COMP_PAGO NOT IN ('03', '04');             --MENOS GUIAS Y NOTAS DE CREDITO
--SI EL PEDIDO SOLO EMITE GUIA ES XQ ES CONVENIO AL 100% Y X LO TANTO NO DEBE TENER PUNTOS
     
      SELECT COUNT(1)
     INTO cantPedAnulado
     FROM VTA_PEDIDO_VTA_CAB C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
     AND C.COD_LOCAL = cCodLocal_in
     AND C.NUM_PED_VTA = vNumPedVta_in
     AND C.NUM_PED_VTA_ORIGEN IS NOT NULL;
     
     SELECT COUNT(1)
     INTO cantPedConCliente
     FROM VTA_PEDIDO_VTA_CAB F
     WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
     AND F.COD_LOCAL = cCodLocal_in
     AND F.NUM_PED_VTA = vNumPedVta_in
     AND F.NUM_PED_VTA_ORIGEN IS NULL
     AND f.est_trx_orbis IN (EST_PENDIENTE, EST_ENVIADO);
     
      select SUM(NVL(D.TOTAL_DESC_E,0))
      INTO ptosInmed
      FROM VTA_COMP_PAGO D
      WHERE D.cod_grupo_cia = cCodGrupoCia_in
      and D.cod_local = cCodLocal_in
      and D.num_ped_vta = vNumPedVta_in
      and D.SEC_COMP_PAGO = cSecCompPago_in;
         
      ptosInmed := round((ptosInmed * 1.18),2);
     
     AHORRO := FID_F_GET_NUM_FORMATED_02(ptosInmed);
     
     IF /*indNuevoPtos = 'S' 
         AND cantCompPedido > 0 
         AND cantPedAnulado = 0  
         AND cantPedConCliente = 1 
         AND*/ AHORRO <> '0.00' THEN
            AHORRO := AHORRO;
     ELSE
       AHORRO := 'N';
     end if;
     

    RETURN AHORRO;
  END;
  
    /* ******************************************************** */
    
      FUNCTION FID_F_CUR_LISTA_FIDELIZACION02 RETURN FarmaCursor IS

    curCamp FarmaCursor;
    siObligatorio char(1) := 'S';
    noObligatorio char(1) := 'N';
  BEGIN

    OPEN curCamp FOR
      SELECT *
      FROM
      (SELECT decode(IND_OBLIGATORIO,'S','(*) ','') || trim(NOM_CAMPO)  || 'Ã' || 
                       ' ' || 'Ã' || 
                       CF.COD_CAMPO || 'Ã' ||
                       IND_TIP_DATO || 'Ã' || 
                       IND_SOLO_LECTURA || 'Ã' ||
                       IND_OBLIGATORIO || 'Ã' || 
                       NVL(CO.IND_VISIBLE_MNTO,'N')
        FROM FID_CAMPOS_FORMULARIO CF, FID_CAMPOS_FIDELIZACION CO
       WHERE CF.COD_CAMPO = CO.COD_CAMPO
         AND CO.IND_MOD = CC_MOD_TAR_FID
         AND IND_VISIBLE_MNTO = 'S'
         AND IND_OBLIGATORIO = siObligatorio
         ORDER BY CO.SEC_CAMPO ASC)
       UNION ALL
       SELECT *
      FROM
       (SELECT decode(IND_OBLIGATORIO,'S','(*) ','') || trim(NOM_CAMPO)  || 'Ã' || 
                       ' ' || 'Ã' || 
                       CF.COD_CAMPO || 'Ã' ||
                       IND_TIP_DATO || 'Ã' || 
                       IND_SOLO_LECTURA || 'Ã' ||
                       IND_OBLIGATORIO || 'Ã' || 
                       NVL(CO.IND_VISIBLE_MNTO,'N')
        FROM FID_CAMPOS_FORMULARIO CF, FID_CAMPOS_FIDELIZACION CO
       WHERE CF.COD_CAMPO = CO.COD_CAMPO
         AND CO.IND_MOD = CC_MOD_TAR_FID
         AND IND_VISIBLE_MNTO = 'S'
         AND IND_OBLIGATORIO = noObligatorio
         ORDER BY CO.SEC_CAMPO ASC);

    RETURN curCamp;
  END FID_F_CUR_LISTA_FIDELIZACION02;

  /***************************************************************************/   
  
  FUNCTION F_IMPR_AFILIACION_PTOS(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cNumDoc_in      IN CHAR,
                                  cSecUsu_in      IN CHAR)
    RETURN FarmaCursor
    IS
    cursorComprobante    FarmaCursor;

    vIdDoc               VARCHAR2(30);
    vIpPc                VARCHAR2(30);
    vValor               VARCHAR2(5000);
    vDocumento           VARCHAR2(20);
    vNombreCliente       VARCHAR2(200);
    vFechaNacimiento     VARCHAR2(30);
    vSexoCliente         VARCHAR2(5);
    vEmailCliente        VARCHAR2(200);
    vCelularCliente      VARCHAR2(50);
    vTelfFijoCliente     VARCHAR2(50);
    vDireccion1Cliente   VARCHAR2(500);
    vDireccion2Cliente   VARCHAR2(5000);
  BEGIN
    
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
    
    SELECT PC.RAZ_SOC_CIA || ' - RUC: ' || PC.NUM_RUC_CIA
    INTO vValor
    FROM PBL_CIA PC, PBL_LOCAL LOC
    WHERE LOC.COD_GRUPO_CIA = cCodGrupoCia_in
    AND LOC.COD_LOCAL = cCodLocal_in
    AND LOC.COD_CIA = PC.COD_CIA;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vValor_in =>vValor,
                                      vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
    
    SELECT  
       A.DNI_CLI ,
       NVL(A.NOM_CLI || ' ' || A.APE_PAT_CLI || ' ' || A.APE_MAT_CLI, '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') ,
       RPAD(NVL(TO_CHAR(A.FEC_NAC_CLI, 'dd/MM/yyyy'), '_ _ _ _ _ '), 14) ,
       NVL(A.SEXO_CLI, '_ _ ') ,
       NVL(A.EMAIL, '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') ,
       NVL(A.CELL_CLI, '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') ,
       NVL(A.FONO_CLI, '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') ,
       NVL(a.dir_cli, '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _') ,
       CASE
         WHEN a.dir_cli IS NULL THEN
          RPAD(' ', 12) || '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _'
         ELSE
          '' 
       END
    INTO vDocumento,
         vNombreCliente,
         vFechaNacimiento,
         vSexoCliente,
         vEmailCliente,
         vCelularCliente,
         vTelfFijoCliente,
         vDireccion1Cliente,
         vDireccion2Cliente
    FROM PBL_CLIENTE A
    WHERE A.DNI_CLI = cNumDoc_in;
    
    -- NRO DE DOCUMENTO
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('DNI/CE:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vDocumento,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
    -- NOMBRE
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Nombre:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vNombreCliente,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);

    -- FN Y SEXO
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('FN:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => vFechaNacimiento,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Sexo:',8),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vSexoCliente,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);                                          
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);  
    
                                               
    -- CORREO
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Email:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vEmailCliente,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
   
    -- CELULAR
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Celular:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vCelularCliente,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);    

    -- TELF FIJO
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Telf.Fijo:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vTelfFijoCliente,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);

    -- DIRECCION
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Dirección:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vDireccion1Cliente,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                               vIpPc_in => vIpPc);
    IF vDireccion2Cliente IS NULL THEN
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                               vIpPc_in => vIpPc);
      
    ELSE
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                        vIpPc_in => vIpPc,
                                        vValor_in => vDireccion2Cliente,
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    END IF;
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
                                             
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);

    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                             
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);                                                                              

    -- FIRMA
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Firma:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
                                             
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc);
                                        
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => RPAD(' ',12)||'___________________________________',
                                      --vValor_in => '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _',
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);

       
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
    SELECT A.DESC_LARGA
    INTO vValor
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = 687;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vValor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                      vAlineado_in => FARMA_PRINTER.ALING_JUSTI);

    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_BARCODE_CODE39(vIdDoc_in => vIdDoc,
                                               vIpPc_in => vIpPc,
                                               vValor_in => cNumDoc_in);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
    SELECT A.DESC_LARGA
    INTO vValor
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = 506;
    
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vValor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                      vAlineado_in => FARMA_PRINTER.ALING_JUSTI);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'CodV: ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => cSecUsu_in,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1);
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => ' - CodL: ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => cSecUsu_in,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, 
                                             vIpPc_in => vIpPc);
    
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'Fecha Actual: ',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => to_char(sysdate,'dd/MM/yyyy HH12:MI:SS A.M.'),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    
    cursorComprobante := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                                       vIpPc_in => vIpPc);
    RETURN cursorComprobante;
  END;   
  
  PROCEDURE IMP_VOUCHER_PTOS(cCodGrupoCia_in       IN CHAR,
                             cCodLocal_in          IN CHAR,
                             cNumPedVta_in         IN CHAR,
                             cSecCompPago_in       IN CHAR,
                             cIndVarios_in         IN CHAR DEFAULT 'N',
                             valorAhorro_in        IN NUMBER,
                             vIdDoc_in             IN VARCHAR2,
                             vIpPc_in              IN VARCHAR2,
                             -- KMONCADA 23.06.2015 NUMERO DE DOCUMENTO DE LA TARJETA DE PUNTOS
                             cDocTarjetaPtos_in    IN VARCHAR2 DEFAULT NULL)
  IS  
     tarjeta varchar2(25) := '';
     vPtoAcumulado number(12,2) := 0;
     vPtoRedimido number(12,2) := 0;
     vPtoTotal number(12,2) := 0;
          codTabGralGlosa number(3) := 458;
              
     ptosAhorro number(6,2) := 0;
             
     vTextoAhorro varchar2(100) := '';
     vTextoAcum varchar2(100) := '';   
     vTextoRedim varchar2(100) := '';
     vTextoAcumHist varchar2(100) := '';
     vTextoTotal varchar2(100) := '';
           
     codTabTextoAhorro number(3) := 471;
     codTabTextoAcum number(3) := 469;
     codTabTextoRedim number(3) := 470;
     codTabTextoAcumHist number(3) := 478;
     codTabTextoTotal number(3) := 485;
           
     vTextoAhorro2 varchar2(100) := '';
     vTextoAcum2 varchar2(100) := '';   
     vTextoRedim2 varchar2(100) := '';
     vTextoAcumHist2 varchar2(100) := '';
     vTextoTotal2 varchar2(100) := '';
           
     codTabTextoAhorro2 number(3) := 487;
     codTabTextoAcum2 number(3) := 477;
     codTabTextoRedim2 number(3) := 477;
     codTabTextoAcumHist2 number(3) := 479;
     codTabTextoTotal2 number(3) := 486;
           
     codTabTextoCuponAcum number(3) := 498;
     vTextoCuponAcum varchar2(100) := '';
           
     codTabTextoInfo number(3) := 661;
           
     codIndVerImagen number(3) := 495;
           
          
     cCantidadCupon       INTEGER DEFAULT 0;
     textoCantCupon varchar2(10) := '';
           
     codTab12MesesAbajo number(3) := 499;
     textoTab12MesesAbajo varchar2(50) := '';
           
     codTabGastando number(3) := 500;
     textoTabGastando varchar2(50) := '';
           
      codTab491 number(3) := '491';
      valorTab491 varchar2(10) := '';
      vValorAhorro     NUMBER(12,3);
      valorImpr varchar2(200) := '';
      
    CURSOR curCabecera IS
      SELECT 
        CLI.NOM_CLI || ' ' || CLI.APE_PAT_CLI || ' ' || CLI.APE_MAT_CLI CLIENTE,
        'Tarjeta : ' || (SELECT '****' || SUBSTR((SUBSTR(CAB.NUM_TARJ_PUNTOS,1,4) || 
                     REPLACE(RPAD(' ',(LENGTH(CAB.NUM_TARJ_PUNTOS)-7),'*'),' ','') || 
                     SUBSTR(CAB.NUM_TARJ_PUNTOS,LENGTH(CAB.NUM_TARJ_PUNTOS)-3,4)),5,9)
              FROM dual)  || 
        '    DNI/CE : ' || NVL(cDocTarjetaPtos_in, CLI.DNI_CLI) TARJETA
      FROM VTA_PEDIDO_VTA_CAB CAB,
           PBL_CLIENTE CLI
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA= cNumPedVta_in
      AND CAB.DNI_CLI = CLI.DNI_CLI;
    
    filaCurCabecera curCabecera%ROWTYPE;
    
    vValor VARCHAR2(3000);
    vMontoAhorroPedido VTA_PEDIDO_VTA_DET.AHORRO%TYPE;
  BEGIN
    IF cIndVarios_in != 'S' THEN     
      FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc_in, vIpPc_in => vIpPc_in, vCaracter => '*');
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc_in, vIpPc_in => vIpPc_in);
      --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('***********************************************', '0','I','N','N');
      --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
    END IF;
    
    IF PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(codIndVerImagen) = 'S' THEN
      FARMA_PRINTER.P_AGREGA_IMAGEN(vIdDoc_in => vIdDoc_in,
                                         vIpPc_in => vIpPc_in,
                                         vRutaImagen_in => 'C:\\farmaventa\\imagenes\\imgPunto.jpg');
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc_in,
                                               vIpPc_in => vIpPc_in);
      /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( 'IMAGEN DE PUNTOS','9','X','S','N');
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( ' ','9','D','S','N');*/
    END IF;
    
    OPEN curCabecera;
      LOOP
        FETCH curCabecera INTO filaCurCabecera;
        EXIT WHEN curCabecera%NOTFOUND;
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                            vIpPc_in => vIpPc_in,
                                            vValor_in => filaCurCabecera.CLIENTE,
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                            vNegrita_in => FARMA_PRINTER.BOLD_ACT);
                                            
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                            vIpPc_in => vIpPc_in,
                                            vValor_in => filaCurCabecera.TARJETA,
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                            vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      END LOOP;
    CLOSE curCabecera;
                
        /*--OBTENGO DATOS DE CLIENTE
           select (select substr(b.num_tarj_puntos,1,4) || 
                                             replace(rpad(' ',(length(b.num_tarj_puntos)-7),'*'),' ','') || 
                                             substr(b.num_tarj_puntos,length(b.num_tarj_puntos)-3,4)
                               from dual),
                                    b.dni_cli
                                          
          into tarjeta,
                       docIdentidad
           from vta_pedido_vta_cab b
           where b.cod_grupo_cia = cCodGrupoCia_in
           and b.cod_local = cCodLocal_in
           and b.num_ped_vta = cNumPedVta_in;
                 
           select cli.nom_cli || ' ' || 
                  cli.ape_pat_cli || ' ' || 
                  cli.ape_mat_cli
           into nombreCliente
           from pbl_cliente cli
           where cli.dni_cli = docIdentidad;

          --INSERTO IMAGEN DE PUNTOS
          IF PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(codIndVerImagen) = 'S' THEN
                  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( 'IMAGEN DE PUNTOS','9','X','S','N');
                  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( ' ','9','D','S','N');
          END IF;
                
          --INSERTO DATOS DE CLIENTE
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
          SELECT nombreCliente ,'0','C','S','N' FROM DUAL;

          tarjeta := '****' || SUBSTR(tarjeta,5,9);

          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
          SELECT 'Tarjeta : ' || tarjeta || '    DNI/CE : ' || docIdentidad,
                 '0','C','S','N' FROM DUAL; */
                       
    --INI TODOS LOS TEXTOS
    SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
    INTO vTextoAhorro
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoAhorro;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
    INTO vTextoAcum
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoAcum;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
    INTO vTextoRedim
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoRedim;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ')
    INTO vTextoAcumHist
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoAcumHist;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
    INTO vTextoTotal
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoTotal;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ') 
    INTO vTextoCuponAcum
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoCuponAcum;
                
    --
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ')
    INTO vTextoAhorro2
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoAhorro2;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ')
    INTO vTextoAcum2
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoAcum2;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ')
    INTO vTextoRedim2
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoRedim2;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ')
    INTO vTextoAcumHist2
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoAcumHist2;
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ')
    INTO vTextoTotal2
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabTextoTotal2;
                
    --
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ')
    INTO textoTab12MesesAbajo
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTab12MesesAbajo;   
                
    SELECT NVL(T.LLAVE_TAB_GRAL,' ')
    INTO textoTabGastando
    FROM PBL_TAB_GRAL T
    WHERE T.ID_TAB_GRAL = codTabGastando;     
                
    --FIN TODOS LOS TEXTOS  
                
    -- CALCULO DE UN MONTON DE CONCEPTOS DE PUNTOS
    IF cIndVarios_in = 'S' THEN
        SELECT SUM(NVL(DET.AHORRO, 0))
        INTO ptosAhorro
        FROM VTA_PEDIDO_VTA_DET DET
        WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND DET.COD_LOCAL = cCodLocal_in
        AND DET.NUM_PED_VTA = cNumPedVta_in;     
    ELSE
        SELECT SUM(NVL(DET.AHORRO, 0))
        INTO ptosAhorro
        FROM VTA_PEDIDO_VTA_DET DET
        WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
        AND DET.COD_LOCAL = cCodLocal_in
        AND DET.NUM_PED_VTA = cNumPedVta_in
        AND DET.SEC_COMP_PAGO = cSecCompPago_in;               
    END IF;
    
    IF ptosAhorro > 0.5 THEN
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                                vIpPc_in => vIpPc_in,
                                                vValor_in => RPAD(vTextoAhorro, 43,' '),
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0);
                                                
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                        vIpPc_in => vIpPc_in,
                                        vValor_in => 'S/.' || LPAD(FID_F_GET_NUM_FORMATED_02(ptosAhorro),12,' '),
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      /*                                  
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT --V_BOLD_I || 
          RPAD(vTextoAhorro,43,textoAgregar) ||
          --V_BOLD_F || 
          V_SEPARADOR || 
          V_BOLD_I ||
          'S/.' || LPAD(PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED_02(ptosAhorro),12,textoAgregar) || 
          V_BOLD_F
          ,'9','I','N','N' 
       FROM DUAL;
       */
    END IF;
                
    SELECT NVL(CAB.PT_ACUMULADO, 0),
           NVL(CAB.PT_REDIMIDO, 0),
           CASE
             WHEN NVL(CAB.PT_TOTAL, 0) < 0 THEN
              0
             ELSE
              NVL(CAB.PT_TOTAL, 0)
           END
    INTO vPtoAcumulado, vPtoRedimido, vPtoTotal
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in;
    
    IF vPtoRedimido > 0 THEN
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                                vIpPc_in => vIpPc_in,
                                                vValor_in => RPAD(vTextoRedim,30,' '),
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0);
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                                vIpPc_in => vIpPc_in,
                                                vValor_in => LPAD(FID_F_GET_NUM_FORMATED(vPtoRedimido),14,' ')||' ',
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                        vIpPc_in => vIpPc_in,
                                        vValor_in => RPAD(vTextoRedim2,19,' '),
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vNegrita_in => FARMA_PRINTER.BOLD_DESACT);
                                        
                                                                                  
     /* INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT --V_BOLD_I || 
          RPAD(vTextoRedim,cantAgregar01,textoAgregar) ||
          --V_BOLD_F || 
          V_SEPARADOR || 
          V_BOLD_I|| 
          LPAD(PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(vPtoRedimido),cantAgregar02,textoAgregar) || 
          V_BOLD_F || 
          V_SEPARADOR || ' ' || 
          RPAD(vTextoRedim2,cantAgregar03,textoAgregar)
          ,'9','I','N','N' 
       FROM DUAL;*/
    END IF;
                    
    --CANTIDAD DE CUPONES 
    SELECT COUNT(1)
    INTO cCantidadCupon
    FROM VTA_CAMP_PEDIDO_CUPON CUPON
    WHERE CUPON.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   CUPON.COD_LOCAL     = cCodLocal_in 
    AND   CUPON.NUM_PED_VTA   = cNumPedVta_in
    AND   CUPON.ESTADO        = 'E' 
    AND   CUPON.IND_IMPR      = 'S'; 
                  
    IF cCantidadCupon < 10 THEN
       textoCantCupon := '0' || cCantidadCupon;
    ELSE
       textoCantCupon := '' || cCantidadCupon;
    END IF;
    
    IF cCantidadCupon > 0 THEN
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                                vIpPc_in => vIpPc_in,
                                                vValor_in => RPAD(vTextoCuponAcum,30,' '),
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0);
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                                vIpPc_in => vIpPc_in,
                                                vValor_in => LPAD(textoCantCupon,14,' ')||' ',
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                        vIpPc_in => vIpPc_in,
                                        vValor_in => RPAD('Cupon dscto',19,' '),
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vNegrita_in => FARMA_PRINTER.BOLD_DESACT);
      /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT --V_BOLD_I || 
          RPAD(vTextoCuponAcum,cantAgregar01,textoAgregar) ||
          --V_BOLD_F || 
          V_SEPARADOR || 
          V_BOLD_I|| 
          LPAD(textoCantCupon,
             cantAgregar02,textoAgregar) || 
          V_BOLD_F || 
          V_SEPARADOR || ' ' || 
          RPAD('Cupon dscto',cantAgregar03,textoAgregar)
          ,'9','I','N','N' 
       FROM DUAL;*/
    END IF;
    
    IF vPtoAcumulado > 0 THEN
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                                vIpPc_in => vIpPc_in,
                                                vValor_in => RPAD(vTextoAcum,30,' '),
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0);
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                                vIpPc_in => vIpPc_in,
                                                vValor_in => LPAD(FID_F_GET_NUM_FORMATED(vPtoAcumulado),14,' ')||' ',
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                        vIpPc_in => vIpPc_in,
                                        vValor_in => RPAD(vTextoAcum2,19,' '),
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vNegrita_in => FARMA_PRINTER.BOLD_DESACT);
        /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
        SELECT --V_BOLD_I || 
            RPAD(vTextoAcum,cantAgregar01,textoAgregar) ||
            --V_BOLD_F || 
            V_SEPARADOR || 
            V_BOLD_I|| 
            LPAD(PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(vPtoAcumulado),cantAgregar02,textoAgregar) || 
            V_BOLD_F || 
            V_SEPARADOR || ' ' || 
            RPAD(vTextoAcum2,cantAgregar03,textoAgregar)
            ,'9','I','N','N' 
         FROM DUAL;*/
                         
    END IF;
      
    IF vPtoTotal > 0 THEN
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                              vIpPc_in => vIpPc_in,
                                              vValor_in => RPAD(vTextoTotal,30,' '),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0);
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                                vIpPc_in => vIpPc_in,
                                                vValor_in => LPAD(FID_F_GET_NUM_FORMATED(vPtoTotal),14,' ')||' ',
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                vNegrita_in => FARMA_PRINTER.BOLD_ACT);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                        vIpPc_in => vIpPc_in,
                                        vValor_in => RPAD(vTextoTotal2,19,' '),
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vNegrita_in => FARMA_PRINTER.BOLD_DESACT);
                                        
      /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
      SELECT  --V_BOLD_I || 
          RPAD(vTextoTotal,cantAgregar01,textoAgregar) ||
          --V_BOLD_F || 
          V_SEPARADOR || 
          V_BOLD_I || 
          LPAD(PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(vPtoTotal),cantAgregar02,textoAgregar) || 
          V_BOLD_F || 
          V_SEPARADOR || ' ' || 
          RPAD(vTextoTotal2,cantAgregar03,textoAgregar)
          ,'9','I','N','N'
      FROM DUAL;*/
    END IF;
                  
    --CALCULO DEL BENDITO AHORRASTE EN 12 MESES
    SELECT NVL(A.LLAVE_TAB_GRAL,'A')
    INTO valorTab491
    FROM PBL_TAB_GRAL A
    WHERE A.ID_TAB_GRAL = codTab491;
                  
    IF valorTab491 = 'A' THEN
      SELECT  (valorAhorro_in * TO_NUMBER(TAB.DESC_CORTA,'99990.00')) / TO_NUMBER(TAB.LLAVE_TAB_GRAL,'99990.00')
      INTO vValorAhorro
      FROM PBL_TAB_GRAL TAB
      WHERE ID_TAB_GRAL = 482;
      
      SELECT SUM(NVL(DET.AHORRO,0))
      INTO vMontoAhorroPedido
      FROM VTA_PEDIDO_VTA_DET DET
      WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND DET.COD_LOCAL = cCodLocal_in
      AND DET.NUM_PED_VTA = cNumPedVta_in;
      
      vValorAhorro := NVL(vValorAhorro,0) + vMontoAhorroPedido;
    END IF;
                  
    valorImpr := FID_F_GET_NUM_FORMATED_02(nvl(vValorAhorro,0));
    
    IF valorImpr <> '0.00' THEN
      FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc_in,
                                              vIpPc_in => vIpPc_in,
                                              vValor_in => RPAD(textoTab12MesesAbajo,43,' '),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                        vIpPc_in => vIpPc_in,
                                        vValor_in => 'S/.' || LPAD(valorImpr,12,' '),
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vNegrita_in => FARMA_PRINTER.BOLD_ACT);
                                        
      /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
        SELECT  --V_BOLD_I || 
            RPAD(textoTab12MesesAbajo,43,textoAgregar) ||
            --V_BOLD_F || 
            V_SEPARADOR || 
            V_BOLD_I || 
            'S/.' || LPAD(valorImpr,12,textoAgregar) || 
            V_BOLD_F
            ,'9','I','N','N'
        FROM DUAL;*/
    END IF;
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc_in,vIpPc_in => vIpPc_in);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                      vIpPc_in => vIpPc_in,
                                      vValor_in => textoTabGastando,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);

    /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
                        
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS        
    SELECT textoTabGastando,'0','I','S','N'
    FROM DUAL;*/
                        
    IF cIndVarios_in != 'S' THEN     
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc_in,vIpPc_in => vIpPc_in);
      FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc_in,
                                                        vIpPc_in => vIpPc_in, 
                                                        vCaracter => '*');
        --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('***********************************************', '0','I','N','N');
    END IF;
    
    SELECT P.LLAVE_TAB_GRAL
    INTO vValor
    FROM PBL_TAB_GRAL P
    WHERE P.ID_TAB_GRAL = codTabTextoInfo;
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc_in,vIpPc_in => vIpPc_in);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                      vIpPc_in => vIpPc_in,
                                      vValor_in => vValor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    
    SELECT P.LLAVE_TAB_GRAL
    INTO vValor
    FROM PBL_TAB_GRAL P
    WHERE P.ID_TAB_GRAL = codTabGralGlosa;
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc_in,vIpPc_in => vIpPc_in);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc_in,
                                      vIpPc_in => vIpPc_in,
                                      vValor_in => vValor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
                                                                  
               /* --TEXTO LEGAL Y MAS DE ESAS COSAS      
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
                SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'9','I','N','N'
                 FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
                      ' '||'@'||
                      (SELECT P.LLAVE_TAB_GRAL
                       FROM PBL_TAB_GRAL P
                       WHERE P.ID_TAB_GRAL = codTabTextoInfo)
                      ||'@'||' '||'@'||
                      (SELECT P.LLAVE_TAB_GRAL
                       FROM PBL_TAB_GRAL P
                       WHERE P.ID_TAB_GRAL = codTabGralGlosa)
                      ||'@'||' '
                 ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;*/

  END;
  
END PTOVENTA_FIDELIZACION;
/
