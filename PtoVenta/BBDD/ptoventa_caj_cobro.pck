CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CAJ_COBRO" IS

  -- AUTHOR  : DESARROLLO4-RHERRERA
  -- CREATED : 27/03/2014 12:17:37
  -- PURPOSE :

  -- VARIABLES CURSOR
  TYPE FARMACURSOR IS REF CURSOR;

  TYPE V_SEC_COM_PAGO_T IS TABLE OF VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE INDEX BY BINARY_INTEGER;

  --TYPE VARCHAR2_TABLE_4 IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

  ID_TBL_GENERAL_CONV_BTLMF VARCHAR2(5) := '391';



/**********************CONSTANTES PARA LOS TIPOS DE COMPROBANTES DE PAGO*******************/

  COD_TIP_COMP_BOLETA    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '01';
  COD_TIP_COMP_FACTURA   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '02';
  COD_TIP_COMP_GUIA      VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '03';
  COD_TIP_COMP_NOTA_CRED VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '04';
  COD_TIP_COMP_TICKET    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '05';

  COD_FORMATO_GUIA_GRANDE      PBL_TAB_GRAL.ID_TAB_GRAL%TYPE:='279';
  COD_FORMATO_GUIA_CHICA      PBL_TAB_GRAL.ID_TAB_GRAL%TYPE:='280';


  -- tipos de convenio
  FLG_TIP_CONV_COPAGO    MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE := 1;
  FLG_TIP_CONV_CONTADO   MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE := 2;
  FLG_TIP_CONV_CREDITO   MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE := 3;

  -- cual comprobante mueve kardex
  MUEVE_KARDEX_EMPRESA      MAE_CONVENIO.NUM_DOC_FLG_KDX%TYPE := 1;
  MUEVE_KARDEX_BENEFICIARIO MAE_CONVENIO.NUM_DOC_FLG_KDX%TYPE := 2;

  -- CONSTANTE AUXILIAR PARA REFERIRSE AL COMPROBANTE DE LA EMPRESA Y BEFENICIARIO
  COMP_EMPRESA      CHAR(1) := 'E';
  COMP_BENEFICIARIO CHAR(1) := 'B';

  COMP_BENEF_CAMPO CHAR(1) := '1';
  COMP_EMPRE_CAMPO      CHAR(1) := '2';


/***************INDICADORES****************/


  INDICADOR_SI        CHAR(1) := 'S';
  INDICADOR_NO        CHAR(1) := 'N';
  POS_INICIO          CHAR(1) := 'I';
  TIPO_COMPROBANTE_99 CHAR(2) := '99';
  EST_PED_COBRADO     VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE := 'C';


    --Descripcion: Flujo del General del Cobro
    --Fecha       Usuario		Comentario
    --01/04/2014  RHERRERA     Creación
    FUNCTION CAJ_PROC_COBRO_PED(

        ----------------------- DATOS DE ERROR -----------------------
        V_ERROR_MENSAJE_OUT OUT VARCHAR2,
        V_NUC_SEC_OUT       OUT NUMBER,


        A_CODFORMAPAGO  IN VARCHAR2_TABLE,
        A_MONTO         IN VARCHAR2_TABLE,
        A_CODMONEDA     IN VARCHAR2_TABLE,
        A_XXX           IN VARCHAR2_TABLE,
        A_IMPTOTAL      IN VARCHAR2_TABLE,
        A_NUMTARJETA    IN VARCHAR2_TABLE,
        A_FECVECTARJETA IN VARCHAR2_TABLE,
        A_NOMCLITARJETA IN VARCHAR2_TABLE,
        A_CANTCUPON     IN VARCHAR2_TABLE,
        A_DNITARJETA    IN VARCHAR2_TABLE,
        A_CODBOUCH      IN VARCHAR2_TABLE,
        A_CODLOTE       IN VARCHAR2_TABLE,

        -- V_SEC_COM_PAGO_OUT           OUT V_SEC_COM_PAGO_T,
        -- V_SEC_COM_PAGO_OUT           OUT SYS_REFCURSOR,
        -- C_SEC_COM_PAGO               IN OUT SYS_REFCURSOR,
        -- V_CUR_DET_PAGO_OUT           IN OUT V_CUR_DET_PAGO,

        ----------------------- DATOS DE PEDIDO ------------------------
        C_COD_GRUPOCIA_IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
        C_COD_LOCAL_IN    VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
        C_NUM_PTO_VTA_IN  VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
        C_TIP_COM_PED_IN  VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE,
        --           C_ESTADO_PED_IN               VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE,
        C_USU_CAJA VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE,

        ------------------------VARIABLES DE ENTRADA------------------------
        --           VNUMCAJA                      CE_MOV_CAJA.NUM_CAJA_PAGO%TYPE,
        VINDPEDIDOSELECCIONADO IN CHAR,
        --  VINDTOTALPEDIDOCUBIERTO       IN BOOLEAN,
        VNUSECUSU CE_MOV_CAJA.SEC_USU_LOCAL%TYPE,
        ------------------------DATOS CLIENTE------------------------
        VRUC_CLI_PED      VTA_PEDIDO_VTA_CAB.RUC_CLI_PED_VTA%TYPE,
        VCOD_CLI_LOCAL    VTA_PEDIDO_VTA_CAB.COD_CLI_LOCAL%TYPE,
        VNOM_CLI_PED      VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE,
        VDIR_CLI_PED      VTA_PEDIDO_VTA_CAB.DIR_CLI_PED_VTA%TYPE,
        VDNI_FID          VTA_PEDIDO_VTA_CAB.DNI_CLI%TYPE,
        VNUMTARJETA_FIDEL FID_TARJETA.COD_TARJETA%TYPE,
        ------------------------------------------------
        PTIPCONSULTA IN VARCHAR2,
        ------------------------VARIABLES DE PROCESO COBRO------------------------
        CCODNUMERA_IN          IN CHAR,
        CCODMOTKARDEX_IN       IN CHAR,
        CTIPDOCKARDEX_IN       IN CHAR,
        CCODNUMERAKARDEX_IN    IN CHAR,
        CDESCDETALLEFORPAGO_IN IN CHAR,
        CPERMITECAMPANA        IN CHAR,

        ----------------------- CONEXION Y CONVENIO-------------------------------

        --VINDCONEXIONRAC                IN VARCHAR2,
        --VVALTOTALPAGAR                 VTA_FORMA_PAGO_PEDIDO.VAL_VUELTO%TYPE,
        --VMONTOSALDO                    VTA_FORMA_PAGO_PEDIDO.VAL_VUELTO%TYPE,
        --VHAYDATOSINGRBTLMF             IN BOOLEAN,
        --VFLGVALIDALINCREBENEF          IN VARCHAR2,

        ------------------------VARIABLES DE PROCESO COBRO------------------------

        CVALVUELTOPEDIDO VTA_FORMA_PAGO_PEDIDO.VAL_VUELTO%TYPE,
        CTIPOCAMBIO      VTA_FORMA_PAGO_PEDIDO.VAL_TIP_CAMBIO%TYPE,

        ------------------------VARIABLES CONVENIO ------------------------
        C_ESPEDIDOCONVENIO  OUT VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE,
        C_PORCOPAGO         MAE_CONVENIO.PCT_BENEFICIARIO%TYPE

        ) RETURN NUMBER;


    --Descripcion: Obtiene el Secuencial de Impresión
    --Fecha       Usuario		Comentario
    --03/04/2014  RHERRERA     Creación
    FUNCTION CAJ_SEC_IMPRE_DOC(C_COD_GRUPOCIA_IN  VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                             C_COD_LOCAL_IN     VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                             C_NUM_PTO_VTA_IN   VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                             C_TIP_COM_PED_IN   VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE,
                             C_USU_CAJA         VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE,
                             C_ESPEDIDOCONVENIO VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE

                             ) RETURN NUMBER;


    --Descripcion:
    --Fecha       Usuario		Comentario
    --03/04/2014  RHERRERA     Creación
    FUNCTION GET_FORMA_PAGO_PED_2(C_COD_GRUPOCIA_IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                                C_COD_LOCAL_IN    VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                                C_NUM_PTO_VTA_IN  VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE)

    RETURN FARMACURSOR;

    --Descripcion: Funcion para separar Segmentos
    --Fecha       Usuario		Comentario
    --07/04/2014  RHERRERA     Creación

    FUNCTION F_SEPARAR_SEGEMENTO(A_CADENA VARCHAR2,
                               A_POS    INT

                               ) RETURN VARCHAR2;

    --Descripcion: Obtiene el Mayor secuencial de impresion
    --Fecha       Usuario		Comentario
    --12/04/2014  RHERRERA     Creación
    FUNCTION F_NUM_SEC_IMPR(CCODGRUPOCIA_IN IN CHAR,
                          CCODLOCAL_IN    IN CHAR,
                          CNUMPEDVTA_IN   IN CHAR) RETURN NUMBER;



    --Descripcion: Obtiene indicador de nuevo cobro
    --Fecha       Usuario		Comentario

    FUNCTION F_IND_NVO_COBRO(C_COD_GRUPOCIA_IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                           C_COD_LOCAL_IN    VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE) RETURN VARCHAR2;


    --Descripcion: FLujo del Cobro Convenio
    --Fecha       Usuario		Comentario
    --20/04/2014  RHERRERA     Creación

    FUNCTION F_COBRO_CONV(  V_ERROR_MENSAJE_OUT     OUT VARCHAR2,
                           C_COD_GRUPOCIA_IN       VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                           C_COD_LOCAL_IN          VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                           C_NUM_PTO_VTA_IN  VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                           ---GRAB-PEDIDO---
                           VNUSECUSU               CE_MOV_CAJA.SEC_USU_LOCAL%TYPE,
                           V_SEC_MOV_CAJ           CE_MOV_CAJA.SEC_MOV_CAJA%TYPE,
                           CCODNUMERA_IN           IN CHAR,
                           CCODMOTKARDEX_IN        IN CHAR,
                           CTIPDOCKARDEX_IN        IN CHAR,
                           CCODNUMERAKARDEX_IN     IN CHAR,
                           C_USU_CAJA              VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE,
                           C_PORCOPAGO             IN CHAR,-- porcentaje,
                           CDESCDETALLEFORPAGO_IN  IN CHAR,
                           -- forma de pago --
                           A_CODFORMAPAGO          IN VARCHAR2_TABLE,
                           A_MONTO                 IN VARCHAR2_TABLE,
                           A_CODMONEDA             IN VARCHAR2_TABLE,
                           CTIPOCAMBIO             VTA_FORMA_PAGO_PEDIDO.VAL_TIP_CAMBIO%TYPE,
                           A_XXX                   IN VARCHAR2_TABLE,
                           A_IMPTOTAL              IN VARCHAR2_TABLE,
                           A_NUMTARJETA            IN VARCHAR2_TABLE,
                           A_FECVECTARJETA         IN VARCHAR2_TABLE,
                           A_NOMCLITARJETA         IN VARCHAR2_TABLE,
                           A_CANTCUPON             IN VARCHAR2_TABLE,
                           A_DNITARJETA            IN VARCHAR2_TABLE,
                           A_CODBOUCH              IN VARCHAR2_TABLE,
                           A_CODLOTE               IN VARCHAR2_TABLE
                          )


    RETURN NUMBER;

    --Descripcion: Cambio Forma de Pagp
    --Fecha       Usuario		Comentario
    --03/04/2014  RHERRERA     Creación
    --En Proceso......

    PROCEDURE P_CAMBIO_FP( C_MENSAJE          OUT VARCHAR2,
                         C_COD_GRUPOCIA_IN  VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                         C_COD_LOCAL_IN     VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                         C_TICK_COMP        VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE,
                         C_MONTO_TARJ       VTA_FORMA_PAGO_PEDIDO.IM_PAGO%TYPE,
                         V_COD_TARJ          VTA_FORMA_PAGO_PEDIDO.COD_FORMA_PAGO%TYPE,
                         V_NUM_TARJE         VTA_FORMA_PAGO_PEDIDO.NUM_TARJ%TYPE,
                         V_COD_AP            VTA_FORMA_PAGO_PEDIDO.COD_AUTORIZACION%TYPE
                         )
       ;

    --Descripcion: Grabar Forma de Pago del Pedido
    --Fecha       Usuario		Comentario
    --20/04/2014  RHERRERA     Creación
    FUNCTION F_GRAB_FORMA_PAGO(
                            V_MSG                     OUT VARCHAR2,
                            C_COD_GRUPOCIA_IN         VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                            C_COD_LOCAL_IN            VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                            A_CODFORMAPAGO            IN VARCHAR2_TABLE,
                            C_NUM_PTO_VTA_IN          VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                            A_MONTO                   IN VARCHAR2_TABLE,
                            A_CODMONEDA               IN VARCHAR2_TABLE,
                            CTIPOCAMBIO               VTA_FORMA_PAGO_PEDIDO.VAL_TIP_CAMBIO%TYPE,
                            A_XXX                     IN VARCHAR2_TABLE,
                            A_IMPTOTAL                IN VARCHAR2_TABLE,
                            A_NUMTARJETA              IN VARCHAR2_TABLE,
                            A_FECVECTARJETA           IN VARCHAR2_TABLE,
                            A_NOMCLITARJETA           IN VARCHAR2_TABLE,
                            A_CANTCUPON               IN VARCHAR2_TABLE,
                            C_USU_CAJA                VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE,
                            A_DNITARJETA              IN VARCHAR2_TABLE,
                            A_CODBOUCH                IN VARCHAR2_TABLE,
                            A_CODLOTE                 IN VARCHAR2_TABLE
                           )
                    RETURN NUMBER;


    --Descripcion: Cobra Convenio
    --Fecha       Usuario		Comentario
    --25/04/2014  RHERRERA     Modificación
    FUNCTION BTLMF_F_COBRA_PEDIDO_CONV_2(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cNuSecUsu_in IN CHAR,
                                   cNumPedVta_in IN CHAR,
                                   cSecMovCaja_in IN CHAR,
                                   cCodNumera_in IN CHAR,
                                   cCodMotKardex_in IN CHAR,
                                   cTipDocKardex_in IN CHAR,
                                   cCodNumeraKardex_in IN CHAR,
                                   cUsuCreaCompPago_in IN CHAR,
                                   cPorCopago IN char,
                                   cDescDetalleForPago_in IN CHAR DEFAULT ' ')
      RETURN CHAR;


    --Descripcion: Obtiene Cantidad de comprobantes a imprimir
    --Fecha       Usuario		Comentario
    --30/04/2014  RHERRERA     Creación
    FUNCTION BTLMF_F_LINEA_COMPROBANTE
    (   V_NUM_LINEA_B         IN NUMBER,
        V_NUM_LINEA_E       IN NUMBER,
        v_TipDocBenificiarioMF  IN NUMBER,
        v_TipDocClienteMF       IN NUMBER
    )
      RETURN NUMBER   ;



END PTOVENTA_CAJ_COBRO;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CAJ_COBRO" AS


---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---

  FUNCTION CAJ_PROC_COBRO_PED(

        ----------------------- DATOS DE ERROR -----------------------
        V_ERROR_MENSAJE_OUT OUT VARCHAR2,
        V_NUC_SEC_OUT       OUT NUMBER,


        A_CODFORMAPAGO  IN VARCHAR2_TABLE,
        A_MONTO         IN VARCHAR2_TABLE,
        A_CODMONEDA     IN VARCHAR2_TABLE,
        A_XXX           IN VARCHAR2_TABLE,
        A_IMPTOTAL      IN VARCHAR2_TABLE,
        A_NUMTARJETA    IN VARCHAR2_TABLE,
        A_FECVECTARJETA IN VARCHAR2_TABLE,
        A_NOMCLITARJETA IN VARCHAR2_TABLE,
        A_CANTCUPON     IN VARCHAR2_TABLE,
        A_DNITARJETA    IN VARCHAR2_TABLE,
        A_CODBOUCH      IN VARCHAR2_TABLE,
        A_CODLOTE       IN VARCHAR2_TABLE,

        -- V_SEC_COM_PAGO_OUT           OUT V_SEC_COM_PAGO_T,
        -- V_SEC_COM_PAGO_OUT           OUT SYS_REFCURSOR,
        -- C_SEC_COM_PAGO               IN OUT SYS_REFCURSOR,
        -- V_CUR_DET_PAGO_OUT           IN OUT V_CUR_DET_PAGO,

        ----------------------- DATOS DE PEDIDO ------------------------
        C_COD_GRUPOCIA_IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
        C_COD_LOCAL_IN    VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
        C_NUM_PTO_VTA_IN  VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
        C_TIP_COM_PED_IN  VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE,
        --           C_ESTADO_PED_IN               VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE,
        C_USU_CAJA VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE,

        ------------------------VARIABLES DE ENTRADA------------------------
        --           VNUMCAJA                      CE_MOV_CAJA.NUM_CAJA_PAGO%TYPE,
        VINDPEDIDOSELECCIONADO IN CHAR,
        --  VINDTOTALPEDIDOCUBIERTO       IN BOOLEAN,
        VNUSECUSU CE_MOV_CAJA.SEC_USU_LOCAL%TYPE,
        ------------------------DATOS CLIENTE------------------------
        VRUC_CLI_PED      VTA_PEDIDO_VTA_CAB.RUC_CLI_PED_VTA%TYPE,
        VCOD_CLI_LOCAL    VTA_PEDIDO_VTA_CAB.COD_CLI_LOCAL%TYPE,
        VNOM_CLI_PED      VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE,
        VDIR_CLI_PED      VTA_PEDIDO_VTA_CAB.DIR_CLI_PED_VTA%TYPE,
        VDNI_FID          VTA_PEDIDO_VTA_CAB.DNI_CLI%TYPE,
        VNUMTARJETA_FIDEL FID_TARJETA.COD_TARJETA%TYPE,
        ------------------------------------------------
        PTIPCONSULTA IN VARCHAR2,
        ------------------------VARIABLES DE PROCESO COBRO------------------------
        CCODNUMERA_IN          IN CHAR,
        CCODMOTKARDEX_IN       IN CHAR,
        CTIPDOCKARDEX_IN       IN CHAR,
        CCODNUMERAKARDEX_IN    IN CHAR,
        CDESCDETALLEFORPAGO_IN IN CHAR,
        CPERMITECAMPANA        IN CHAR,

        ----------------------- CONEXION Y CONVENIO-------------------------------

        --VINDCONEXIONRAC                IN VARCHAR2,
        --VVALTOTALPAGAR                 VTA_FORMA_PAGO_PEDIDO.VAL_VUELTO%TYPE,
        --VMONTOSALDO                    VTA_FORMA_PAGO_PEDIDO.VAL_VUELTO%TYPE,
        --VHAYDATOSINGRBTLMF             IN BOOLEAN,
        --VFLGVALIDALINCREBENEF          IN VARCHAR2,

        ------------------------VARIABLES DE PROCESO COBRO------------------------

        CVALVUELTOPEDIDO VTA_FORMA_PAGO_PEDIDO.VAL_VUELTO%TYPE,
        CTIPOCAMBIO      VTA_FORMA_PAGO_PEDIDO.VAL_TIP_CAMBIO%TYPE,

        ------------------------VARIABLES CONVENIO ------------------------
        C_ESPEDIDOCONVENIO  OUT VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE,
        C_PORCOPAGO         MAE_CONVENIO.PCT_BENEFICIARIO%TYPE

        ) RETURN NUMBER AS

        V_FLAG_CRED_BENF                   CHAR;
    --##########################  DECLARACION DE VARIABLES ##########################--
    V_CAJA_USUS       CE_MOV_CAJA.NUM_CAJA_PAGO%TYPE;
    V_CAJA_OPEN       NUMBER;
    V_SEC_MOV_CAJ     CE_MOV_CAJA.SEC_MOV_CAJA%TYPE;
    V_IP_LINEA        CHAR;
    V_VALIDA_MATRIZ_F VARCHAR2(10);
    V_RESP            CHAR;
    V_IND_EST_CAJA    CHAR;
    --#####             CUPON        #####--
    C_CUPON      FARMACURSOR;
    V_ESTADO_CUP VTA_CUPON.ESTADO%TYPE;
    V_COD_CUPON  VTA_CUPON.COD_CUPON%TYPE;
    V_COD_CUPON2 VTA_CUPON.COD_CUPON%TYPE;
    V_COD_CUPON3 VTA_CUPON.COD_CUPON%TYPE;
    --      V_FEC_INI                      VTA_CUPON.FEC_INI%TYPE;
    --      V_FEC_FIN                      VTA_CUPON.FEC_FIN%TYPE;
    IND_USO        CHAR;
    V_EXITO        VARCHAR(50);
    V_SEC_COM_PAGO VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    V_CADENA       VARCHAR2(10000);
    --###################################--

    V_NUC_SEC_IMPRE_COMPRO NUMBER;

    --#################### VARIABLES FORMA DE PAGO  ####################--

    C_DETALLE_FP               FARMACURSOR;
    VAL_CUPON                  FARMACURSOR;
    LISTACAMPAUTOMATICASPEDIDO FARMACURSOR;
    LISTACAMPLIMITTERMINADOS   FARMACURSOR;
    C_SEC_COM_PAGO             FARMACURSOR;
    V_CONT                     NUMBER(5) := 0;
    V_MSG                      VARCHAR2(10000);
    V_SEC_COM_T                V_SEC_COM_PAGO_T;

    V_GRAB_FP                  NUMBER(1);

    --####################VARIABLES FORMA DE PAGO CONVENIO ####################--
    V_CONV                     NUMBER(1);


 BEGIN



  BEGIN
    --#################### VALIDACIONES ####################--
    IF PTOVTA_RESPALDO_STK.F_EXISTE_STOCK_PEDIDO(C_COD_GRUPOCIA_IN,
                                                 C_COD_LOCAL_IN,
                                                 C_NUM_PTO_VTA_IN) = 'N' THEN
      V_MSG := 'NO PUEDE COBRAR EL PEDIDO' || CHR(13) ||
               'PORQUE NO HAY STOCK SUFICIENTE PARA PODER GENERARLO Ó ' ||
               CHR(13) || 'EXISTE UN PROBLEMA EN LA FRACCIÓN DE PRODUCTOS.';

      V_ERROR_MENSAJE_OUT := V_MSG;
      -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014

      RETURN 0;

    END IF;

  -- 2. ###  VALIDACIONES COBRAR PEDIDO ### --
    -- 2.1 VINDPEDIDOSELECCIONADO = TRUE
    IF VINDPEDIDOSELECCIONADO <> 'S' THEN
      V_MSG               := 'NO TIENE PEDIDOS SELECCIONADOS';
      V_ERROR_MENSAJE_OUT := V_MSG;
      -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
      RETURN 0;
    END IF;

    -- 2.3 PENDIO ESTADO PENDIENTE  'P'
    IF PTOVENTA_CAJ.CAJ_OBTIENE_ESTADO_PEDIDO(C_COD_GRUPOCIA_IN,
                                              C_COD_LOCAL_IN,
                                              C_NUM_PTO_VTA_IN) <> 'P' THEN
      V_MSG               := 'EL PEDIDO NO SE ENCUENTRA PENDIENTE DE COBRO.VERIFIQUE!!!';
      V_ERROR_MENSAJE_OUT := V_MSG;
      -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
      RETURN 0;
    END IF;

      -- 2.4 VALIDA EXISTECAJAUSUARIOIMPRESORA --
    BEGIN
      --//--
      V_CAJA_USUS := PTOVENTA_CAJ.CAJ_OBTIENE_CAJA_USUARIO(C_COD_GRUPOCIA_IN,
                                                           C_COD_LOCAL_IN,
                                                           VNUSECUSU);

      IF (V_CAJA_USUS = 0 OR V_CAJA_USUS IS NULL) THEN
        V_MSG               := 'NO SE CUENTA CON CAJA RELACIONADA... VERIFICAR !!!';
        V_ERROR_MENSAJE_OUT := V_MSG;
        -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
        RETURN 0;
      ELSE
        V_CAJA_OPEN := PTOVENTA_CAJ.CAJ_VERIFICA_CAJA_ABIERTA(C_COD_GRUPOCIA_IN,
                                                              C_COD_LOCAL_IN,
                                                              VNUSECUSU);

        IF (V_CAJA_OPEN = 0 OR V_CAJA_OPEN IS NULL) THEN
          V_MSG               := 'LA CAJA RELACIONADA AL USUARIO NO HA SIDO APERTURADA.' ||
                                 CHR(13) || 'VERIFIQUE !!!';
          V_ERROR_MENSAJE_OUT := V_MSG;
          -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
          RETURN 0;
        ELSE
          V_SEC_MOV_CAJ := PTOVENTA_CAJ.CAJ_OBTIENE_SEC_MOV_CAJA(C_COD_GRUPOCIA_IN,
                                                                 C_COD_LOCAL_IN,
                                                                 V_CAJA_USUS);

          IF (V_SEC_MOV_CAJ IS NULL OR V_SEC_MOV_CAJ = '') THEN
            V_MSG               := 'NO SE PUDO DETERMINAR EL MOVIMIENTO DE CAJA. VERIFIQUE !!!';
            V_ERROR_MENSAJE_OUT := V_MSG;
            -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
            RETURN 0;

          END IF;

        END IF;

      END IF;

    EXCEPTION
      WHEN OTHERS THEN
        V_MSG               := 'ERROR AL VALIDAR CAJA'||
                            CHR(13)||SQLERRM;
        V_ERROR_MENSAJE_OUT := V_MSG;
        -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
        RETURN 0;
    END;

    -- 2.6 VALIDA RUC SI ES DOCUMENTO FACTURA --
    IF (C_TIP_COM_PED_IN = '02' AND
       (VRUC_CLI_PED IS NULL OR VRUC_CLI_PED = '')) THEN
      V_MSG               := 'DEBE INGRESAR NUMERO DE RUC...';
      V_ERROR_MENSAJE_OUT := V_MSG;
      -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
      RETURN 0;
    END IF;

    -- 2.8 VALIDA SI PEDIDO ES FIDELIZADO --
    BEGIN
      -- VALIDA LOCALMENTE --
      V_RESP := PTOVENTA_FIDELIZACION.FID_F_CHAR_VALIDA_PED_FID(C_COD_GRUPOCIA_IN,
                                                                C_COD_LOCAL_IN,
                                                                C_NUM_PTO_VTA_IN,
                                                                VRUC_CLI_PED,
                                                                VNUMTARJETA_FIDEL);
      -- CONDICIONES PARA LA VARIABLE : V_RESP --
      IF TRIM(V_RESP) <> 'S' THEN
        IF TRIM(V_RESP) = 'N_RUC' THEN
          V_MSG               := 'LOS DESCUENTOS SON PARA VENTA CON ' ||
                                 CHR(13) ||
                                 'BOLETA DE VENTA Y PARA CONSUMO ' ||
                                 CHR(13) ||
                                 'PERSONAL. EL RUC INGRESADO QUEDA ' ||
                                 CHR(13) ||
                                 'FUERA DE LA PROMOCIÓN DE DESCUENTO.';
          V_ERROR_MENSAJE_OUT := V_MSG;
        ELSE
          IF TRIM(V_RESP) = 'N_DNI' THEN
            V_MSG               := 'DNI EXCEDIÓ EL MÁXIMO DE DESCUENTO PERMITIDO.' ||
                                   CHR(13) || 'DEBE RECALCULAR LA VENTA.';
            V_ERROR_MENSAJE_OUT := V_MSG;
          ELSE
            IF TRIM(V_RESP) = 'N_DCTO' THEN
              V_MSG := 'DESCUENTO EXCEDIÓ' || CHR(13) ||
                       'DE DESCUENTO PERMITIDO.' || CHR(13) ||
                       'DEBE RECALCULAR LA VENTA.';
            END IF;
          END IF;
        END IF;

        -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
        RETURN 0;
      END IF;

    EXCEPTION
      WHEN OTHERS THEN
        V_MSG := 'ERROR AL VALIDAR PEDIDO FIDELIZADO'
                 ||CHR(13)||SQLERRM;
        -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
        V_ERROR_MENSAJE_OUT := V_MSG;
    END;

  -- 3.    VALIDAR CAJA ABIERTA --
    BEGIN
      V_IND_EST_CAJA := PTOVENTA_CAJ.CAJ_VALIDA_CAJA_APERTURA(C_COD_GRUPOCIA_IN,
                                                              C_COD_LOCAL_IN,
                                                              V_CAJA_USUS); -- NUMCAJA
      IF V_IND_EST_CAJA <> 'S' THEN
        V_MSG               := 'LA CAJA NO SE ENCUNTRA APERTURADA...VERIFIQUE';
        V_ERROR_MENSAJE_OUT := V_MSG;
        -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
        RETURN 0;
      END IF;

    EXCEPTION
      WHEN OTHERS THEN
        V_MSG               := 'ERROR AL OBTENER INDICADOR DE CAJA ABIERTA..'
                               ||CHR(13)||SQLERRM;
        V_ERROR_MENSAJE_OUT := V_MSG;
        -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
        RETURN 0;
    END;

  -- 4.    ACTUALIZA CLIENTE PEDIDO --
    IF (LENGTH(VCOD_CLI_LOCAL) > 0 OR VCOD_CLI_LOCAL IS NOT NULL) THEN
      BEGIN
        PTOVENTA_CAJ.CAJ_ACTUALIZA_CLI_PEDIDO(C_COD_GRUPOCIA_IN,
                                              C_COD_LOCAL_IN,
                                              C_NUM_PTO_VTA_IN,
                                              VCOD_CLI_LOCAL,
                                              VNOM_CLI_PED,
                                              VDIR_CLI_PED,
                                              VRUC_CLI_PED,
                                              C_USU_CAJA);
      EXCEPTION
        WHEN OTHERS THEN
          V_MSG               := 'ERROR AL ACTUALIZAR CLIENTE'
                                 ||CHR(13)||SQLERRM;
          V_ERROR_MENSAJE_OUT := V_MSG;
          -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
          RETURN 0;
      END;
    END IF;


  EXCEPTION
    WHEN OTHERS THEN
      V_ERROR_MENSAJE_OUT := 'ERROR EN LAS VALIDACION DEL PEDIDO'
                             ||CHR(13)||SQLERRM;
  END;
    --#################### FIN VALIDACIONES ####################--




    --#################### ############# ####################--
    --#################### ############# ####################--
    --#################### ############# ####################--
    --#################### PROCESO COBRO ####################--
    --#################### ############# ####################--
    --#################### ############# ####################--
    --#################### ############# ####################--


     -- #.  SI IND_PED_CONVENIO = 'S' EN CABECERA DE PEDIDO
     BEGIN
     C_ESPEDIDOCONVENIO := PTOVENTA_CAJ.CAJ_F_VAR_IND_PED_CONVENIO(C_COD_GRUPOCIA_IN,
                                                                  C_COD_LOCAL_IN,
                                                                  C_NUM_PTO_VTA_IN);

      EXCEPTION
      WHEN OTHERS THEN
      V_ERROR_MENSAJE_OUT := 'ERROR NO SE PUEDO EDINTIFICAR EL TIPO DE PEDIDO'
                             ||CHR(13)||'CONVENIO O MEZON'||
                             CHR(13)||SQLERRM;
     END;

IF C_ESPEDIDOCONVENIO = 'S' THEN

      /*  ###############  CONVENIO ############### */
      /*  ###############  CONVENIO ############### */
      /*  ###############  CONVENIO ############### */
           BEGIN
               V_CONV := F_COBRO_CONV( V_MSG,
                                       C_COD_GRUPOCIA_IN,
                                       C_COD_LOCAL_IN,
                                       C_NUM_PTO_VTA_IN,
                                       ---GRAB-PEDIDO---
                                       VNUSECUSU,
                                       V_SEC_MOV_CAJ,
                                       CCODNUMERA_IN,
                                       CCODMOTKARDEX_IN,
                                       CTIPDOCKARDEX_IN,
                                       CCODNUMERAKARDEX_IN,
                                       C_USU_CAJA,
                                       C_PORCOPAGO,-- % Beneficiario
                                       CDESCDETALLEFORPAGO_IN,
                                       -- forma de pago --
                                       A_CODFORMAPAGO,
                                       A_MONTO,
                                       A_CODMONEDA,
                                       CTIPOCAMBIO,
                                       A_XXX,
                                       A_IMPTOTAL,
                                       A_NUMTARJETA,
                                       A_FECVECTARJETA,
                                       A_NOMCLITARJETA,
                                       A_CANTCUPON,
                                       A_DNITARJETA,
                                       A_CODBOUCH,
                                       A_CODLOTE
                                       );

                  IF V_CONV = 0 THEN
                    V_ERROR_MENSAJE_OUT := V_MSG;
                    RETURN 0;

                  ELSE

                    V_ERROR_MENSAJE_OUT := V_MSG;
                    RETURN 1;

 /*  ###############  CONVENIO ############### */
                    END IF; --- FIN CONVENIO
             EXCEPTION
             WHEN OTHERS THEN
             V_ERROR_MENSAJE_OUT := 'ERROR AL COBRAR PEDIDO CONVENIO'
                             ||CHR(13)||SQLERRM;

             END;



ELSE






    /*  ###############  SIN CONVENIO ############### */
    /*  ###############  SIN CONVENIO ############### */

    -- #.   OBTENER CUPONES USADOS EN EL PEDIDO
    BEGIN
      C_CUPON := PTOVENTA_CUPON.CUP_F_CUR_CUP_PED(C_COD_GRUPOCIA_IN,
                                                  C_COD_LOCAL_IN,
                                                  C_NUM_PTO_VTA_IN,
                                                  'N',
                                                  PTIPCONSULTA);
      LOOP
        FETCH C_CUPON
          INTO V_CADENA;

        V_COD_CUPON := F_SEPARAR_SEGEMENTO(V_CADENA, 1);

        IF V_COD_CUPON IS NULL THEN
          EXIT WHEN C_CUPON%NOTFOUND;
        END IF;

        EXIT WHEN C_CUPON%NOTFOUND;

        IND_USO := PTOVENTA_CUPON.CUP_F_CHAR_IND_MULTIPLO_CUP(C_COD_GRUPOCIA_IN,
                                                              V_COD_CUPON);

        VAL_CUPON := PTOVENTA_CUPON.CUP_F_CUR_VALIDA_CUPON(C_COD_GRUPOCIA_IN,
                                                           C_COD_LOCAL_IN,
                                                           V_COD_CUPON,
                                                           IND_USO,
                                                           VDNI_FID);

        V_ESTADO_CUP := PTOVENTA_CUPON.CUP_F_CHAR_BLOQ_EST(C_COD_GRUPOCIA_IN,
                                                           V_COD_CUPON);
        --END IF;

        --IF V_ESTADO_CUP IS NULL THEN
          -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
        --END IF;

      END LOOP;

    EXCEPTION
      WHEN OTHERS THEN
        V_MSG               := 'ERROR AL VALIDAR EL CUPON'
                               ||CHR(13)||SQLERRM;
        V_ERROR_MENSAJE_OUT := V_MSG;
    END;


    -- #.   OBTENER DNI SI CUENTA CON VENTA FIDELIZADO
    BEGIN

    IF (LENGTH(VDNI_FID) > 0 AND VDNI_FID IS NOT NULL) THEN

      LISTACAMPAUTOMATICASPEDIDO := PTOVENTA_CAJ.CAJ_F_LISTA_CAMP_AUTOMATIC(C_COD_GRUPOCIA_IN,
                                                                            C_COD_LOCAL_IN,
                                                                            C_NUM_PTO_VTA_IN);

      LISTACAMPLIMITTERMINADOS := PTOVENTA_FIDELIZACION.FID_F_LISTA_CAMPCL_USADOS(C_COD_GRUPOCIA_IN,
                                                                                  C_COD_LOCAL_IN,
                                                                                  VDNI_FID);

      LOOP
        FETCH LISTACAMPLIMITTERMINADOS
          INTO V_COD_CUPON2;
        EXIT WHEN LISTACAMPLIMITTERMINADOS%NOTFOUND;

        LOOP
          FETCH LISTACAMPAUTOMATICASPEDIDO
            INTO V_COD_CUPON3;
          EXIT WHEN LISTACAMPAUTOMATICASPEDIDO%NOTFOUND;

          IF V_COD_CUPON2 = V_COD_CUPON3 THEN
            V_MSG               := 'ERROR AL COBRAR PEDIDO' || CHR(13) ||
                                   'EL DESCUENTO DE LA CAMPAÑA YA FUE USADO POR EL CLIENTE !';
            V_ERROR_MENSAJE_OUT := V_MSG;
            -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
            RETURN 0;
          END IF;

        END LOOP;

      END LOOP;

    END IF;

    EXCEPTION
    WHEN OTHERS THEN
      V_ERROR_MENSAJE_OUT := 'ERROR EN LAS VALIDACION DEL PEDIDO'||
                             CHR(13)||'CLIENTE FIDELIZADO'
                             ||CHR(13)||SQLERRM;
      RETURN 0;

    END;


    -- #.  OBTENEMOS EL GRUPO DE IMPRESION ---
    BEGIN
   V_NUC_SEC_IMPRE_COMPRO := CAJ_SEC_IMPRE_DOC(C_COD_GRUPOCIA_IN,
                                                C_COD_LOCAL_IN,
                                                C_NUM_PTO_VTA_IN,
                                                C_TIP_COM_PED_IN,
                                                C_USU_CAJA,
                                                C_ESPEDIDOCONVENIO);
    V_NUC_SEC_OUT := V_NUC_SEC_IMPRE_COMPRO;

    EXCEPTION
    WHEN OTHERS THEN
      V_ERROR_MENSAJE_OUT := 'ERROR EN OBTENER GRUPO DE IMPRESION'||
                             CHR(13)||'VERIFICAR.....!!'
                             ||CHR(13)||SQLERRM;
      RETURN 0;

    END;


        -- ################################## --
        -- ########## COBRAR PEDIDO #########
        -- ################################## --

    BEGIN
      V_EXITO := PTOVENTA_CAJ.CAJ_COBRA_PEDIDO(C_COD_GRUPOCIA_IN,
                                               C_COD_LOCAL_IN,
                                               C_NUM_PTO_VTA_IN,
                                               V_SEC_MOV_CAJ,
                                               CCODNUMERA_IN,
                                               C_TIP_COM_PED_IN,
                                               CCODMOTKARDEX_IN,
                                               CTIPDOCKARDEX_IN,
                                               CCODNUMERAKARDEX_IN,
                                               C_USU_CAJA,
                                               CDESCDETALLEFORPAGO_IN,
                                               CPERMITECAMPANA, -- NO PERMITE CAMPANA 'N'
                                               VDNI_FID);

    EXCEPTION
      WHEN OTHERS THEN
        V_MSG               := 'FALLO....' || V_EXITO ||' AL COBRAR EL PEDIDO'||
                                CHR(13) || SQLERRM;
        V_ERROR_MENSAJE_OUT := V_MSG;
        -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
        RETURN 0;
    END;

    --- 8. ENTRAMOS AL REALIZAR EL C0BRO --

    IF TRIM(V_EXITO) = 'EXITO' THEN

      --### 8.1  VALIDA AGRUPACION DE COMPROBANTES ---
      C_SEC_COM_PAGO := PTOVENTA_CAJ.CAJ_INFO_DETALLE_AGRUPACION(C_COD_GRUPOCIA_IN,
                                                                 C_COD_LOCAL_IN,
                                                                 C_NUM_PTO_VTA_IN);
      LOOP
        FETCH C_SEC_COM_PAGO
          INTO V_CADENA;
        EXIT WHEN C_SEC_COM_PAGO%NOTFOUND;

        V_CONT         := V_CONT + 1;
        V_SEC_COM_PAGO := F_SEPARAR_SEGEMENTO(V_CADENA, 2);

        IF (V_SEC_COM_PAGO = '' OR V_SEC_COM_PAGO IS NULL) THEN
          -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
          V_MSG               := 'HUBO ERROR AL OBTENER EL SECUENCIAL DEL COMPROBANTE!';
          V_ERROR_MENSAJE_OUT := V_MSG;
          RETURN 0;
        END IF;

        V_SEC_COM_T(V_CONT) := V_SEC_COM_PAGO;
        --V_SEC_COM_PAGO_OUT := V_SEC_COM_T;
        --V_SEC_COM_PAGO_OUT := C_SEC_COM_PAGO;
      END LOOP;

      --### 8.2  VENTA CON FIDELIZADO ###--
      IF (VDNI_FID IS NOT NULL AND LENGTH(VDNI_FID) > 0) THEN
        BEGIN
          IF PTOVENTA_CA_CLIENTE.CA_F_CHAR_EXIST_REGALO(C_COD_GRUPOCIA_IN,
                                                        C_COD_LOCAL_IN,
                                                        C_NUM_PTO_VTA_IN,
                                                        VDNI_FID) = 'S' THEN
            DBMS_OUTPUT.PUT_LINE('EXISTE REGALO');
          END IF;

          PTOVENTA_CA_CLIENTE.CA_P_ANALIZA_CANJE(C_COD_GRUPOCIA_IN,
                                                 C_COD_LOCAL_IN,
                                                 C_NUM_PTO_VTA_IN,
                                                 C_USU_CAJA,
                                                 'C', --ACCION COBRO, NUNCA CAMBIA
                                                 'N' -- VARIABLE POR DEFECTO
                                                 );

          PTOVENTA_MATRIZ_CA_CLI.CA_P_ANALIZA_CANJE(C_COD_GRUPOCIA_IN,
                                                    C_COD_LOCAL_IN,
                                                    C_NUM_PTO_VTA_IN,
                                                    VDNI_FID,
                                                    C_USU_CAJA,
                                                    'C');

        EXCEPTION
          WHEN OTHERS THEN
            V_MSG := 'EL PEDIDO NO PUEDE SER COBRADO.' || CHR(13) ||
                     'PRESENTA UN PRODUCTO REGALO DE CAMPAÑA QUE NO SE PUEDE VALIDAR CON MATRIZ. ' ||
                     CHR(13) ||
                     'INTÉNTELO NUEVAMENTE DE LO CONTRARIO ANULE EL PEDIDO Y GENÉRELO NUEVAMENTE.' ||
                     CHR(13) || 'GRACIAS.'
                     ||CHR(13)||SQLERRM;

            V_ERROR_MENSAJE_OUT := V_MSG;
            -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014

            RETURN 0;
        END;

      END IF;


      --- 9.  DETALLE FORMA DE PAGO  --
      -- #### GRABAS FORMAS DE PAGO ##### --
    /*
      BEGIN

        FOR I IN 1 .. A_CODFORMAPAGO.COUNT LOOP
          PTOVENTA_CAJ.CAJ_GRAB_NEW_FORM_PAGO_PEDIDO(C_COD_GRUPOCIA_IN,
                                                     C_COD_LOCAL_IN,
                                                     A_CODFORMAPAGO(I),
                                                     C_NUM_PTO_VTA_IN,
                                                     TO_NUMBER(A_MONTO(I),
                                                               '999,990.000'),
                                                     A_CODMONEDA(I),
                                                     CTIPOCAMBIO,
                                                     TO_NUMBER(A_XXX(I),
                                                               '999,990.000'),
                                                     TO_NUMBER(A_IMPTOTAL(I),
                                                               '999,990.000'),
                                                     A_NUMTARJETA(I),
                                                     A_FECVECTARJETA(I),
                                                     A_NOMCLITARJETA(I),
                                                     TO_NUMBER(A_CANTCUPON(I)),
                                                     C_USU_CAJA,
                                                     A_DNITARJETA(I),
                                                     A_CODBOUCH(I),
                                                     A_CODLOTE(I));
        END LOOP;

        IF PTOVENTA_CAJ.CAJ_F_VERIFICA_PED_FOR_PAG(C_COD_GRUPOCIA_IN,
                                                   C_COD_LOCAL_IN,
                                                   C_NUM_PTO_VTA_IN) =
           'ERROR' THEN

          V_MSG := 'ERROR *****' || 'EL PEDIDO NO PUEDE SER COBRADO.' ||
                   CHR(13) ||'LOS TOTALES DE FORMAS DE PAGO Y CABECERA NO COINCIDEN.' ||
                   CHR(13) ||'COMUNÍQUESE CON EL OPERADOR DE SISTEMAS INMEDIATAMENTE.' ||
                   CHR(13) ||'NO CIERRE LA VENTANA.';

          -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
          V_ERROR_MENSAJE_OUT := V_MSG;
          RETURN 0;
        END IF;

      EXCEPTION
        WHEN OTHERS THEN
          -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
          V_MSG               := 'ERROR. NO SE PUDO REGISTRAR UNA DE LAS FORMAS DE PAGOS..' ||
                                 CHR(13) || SQLERRM;
          V_ERROR_MENSAJE_OUT := V_MSG;
          RETURN 0;
      END;

      -- #### FIN FORMAS DE PAGO ##### --
    */

       BEGIN

       V_GRAB_FP:= F_GRAB_FORMA_PAGO(V_MSG,
                                     C_COD_GRUPOCIA_IN,
                                     C_COD_LOCAL_IN,
                                     A_CODFORMAPAGO,
                                     C_NUM_PTO_VTA_IN,
                                     A_MONTO,
                                     A_CODMONEDA,
                                     CTIPOCAMBIO,
                                     A_XXX,
                                     A_IMPTOTAL,
                                     A_NUMTARJETA,
                                     A_FECVECTARJETA,
                                     A_NOMCLITARJETA,
                                     A_CANTCUPON,
                                     C_USU_CAJA,
                                     A_DNITARJETA,
                                     A_CODBOUCH,
                                     A_CODLOTE
                                     );

       IF V_GRAB_FP = 0 THEN

          V_ERROR_MENSAJE_OUT := V_MSG;
          RETURN 0;

       ELSE
          begin
            Farma_Gral.CAJ_REGISTRA_TMP_INI_FIN_COBRO(C_COD_GRUPOCIA_IN,
                                                      C_COD_LOCAL_IN,
                                                      C_NUM_PTO_VTA_IN,
                                                      'F');

          exception
              when others then
              V_MSG := 'Error al grabar FECHA DE FIN COBRO'||
                                   CHR(13) ||sqlerrm;
                                   RETURN 0;
          end;

            --V_MSG               := 'ACEPTE PARA FINALIZAR EL COBRO';
            V_ERROR_MENSAJE_OUT := V_MSG;
            --COMMIT;
            -- DUBILLUZ 16.04.2014
          RETURN 1;

       END IF;
       EXCEPTION
         WHEN OTHERS THEN
           V_ERROR_MENSAJE_OUT := 'ERROR AL GRABAR LA FORMA DE PAGO'||
                                  CHR(13)||'MAL ENVIO DE DATOS'||
                                  CHR(13)||SQLERRM;
          RETURN 0;

       END;

    ELSE

       begin
            Farma_Gral.CAJ_REGISTRA_TMP_INI_FIN_COBRO(C_COD_GRUPOCIA_IN,
                                                      C_COD_LOCAL_IN,
                                                      C_NUM_PTO_VTA_IN,
                                                      'F');

          exception
              when others then
              V_MSG := 'Error al grabar FECHA DE FIN COBRO'||
                                   CHR(13) ||sqlerrm;
                                   RETURN 0;
          end;

       V_ERROR_MENSAJE_OUT := CHR(13)||'EL PEDIDO NO PUEDE SER COBRADO'||CHR(13);

       V_ERROR_MENSAJE_OUT := V_MSG;
      -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
      RETURN 0;

    END IF;




  /*  ###############  SIN CONVENIO ############### */

END IF;--- FINNNNN

END;




---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---

  FUNCTION CAJ_SEC_IMPRE_DOC(C_COD_GRUPOCIA_IN  VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                             C_COD_LOCAL_IN     VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                             C_NUM_PTO_VTA_IN   VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                             C_TIP_COM_PED_IN   VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE,
                             C_USU_CAJA         VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE,
                             C_ESPEDIDOCONVENIO VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE

                             ) RETURN NUMBER IS
    V_ID_LINEA        PBL_TAB_GRAL.ID_TAB_GRAL%TYPE;
    V_LINEA_X_DOC     PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;
    V_NUM_SEC_GRU_IMP NUMBER;

  BEGIN

    IF C_ESPEDIDOCONVENIO = 'S' THEN

      IF C_TIP_COM_PED_IN = '01' THEN
        V_ID_LINEA := 231;
      ELSE
        IF C_TIP_COM_PED_IN = '02' THEN
          V_ID_LINEA := 233;
        ELSE
          IF C_TIP_COM_PED_IN = '05' THEN
            V_ID_LINEA := 269;
          ELSE
            IF C_TIP_COM_PED_IN = '06' THEN
              V_ID_LINEA := 269;
            ELSE
              RAISE_APPLICATION_ERROR(-20000,
                                      CHR(13) ||
                                      'ERROR.. AL SELECCIONAR TIPO DE COMPROBANTE');
            END IF;
          END IF;
        END IF;
      END IF;
    ELSE

      IF C_TIP_COM_PED_IN = '01' THEN
        V_ID_LINEA := 230;
      ELSE
        IF C_TIP_COM_PED_IN = '02' THEN
          V_ID_LINEA := 232;
        ELSE
          IF C_TIP_COM_PED_IN = '05' THEN
            V_ID_LINEA := 269;
          ELSE
            IF C_TIP_COM_PED_IN = '06' THEN
              V_ID_LINEA := 269;
            ELSE
              RAISE_APPLICATION_ERROR(-20000,
                                      CHR(13) ||
                                      'ERROR.. AL SELECCIONAR TIPO DE COMPROBANTE');
            END IF;
          END IF;
        END IF;
      END IF;
    END IF;

    V_LINEA_X_DOC := PTOVENTA_CAJ.CAJ_F_VAR_LINEA_DOC(V_ID_LINEA);

    V_NUM_SEC_GRU_IMP := PTOVENTA_CAJ.CAJ_AGRUPA_IMPRESION_DETALLE(C_COD_GRUPOCIA_IN,
                                                                   C_COD_LOCAL_IN,
                                                                   C_NUM_PTO_VTA_IN,
                                                                   V_LINEA_X_DOC,
                                                                   C_USU_CAJA,
                                                                   C_TIP_COM_PED_IN);
  --raise_application_error(-20001, V_NUM_SEC_GRU_IMP);
    RETURN V_NUM_SEC_GRU_IMP;

  END;



---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---

  FUNCTION GET_FORMA_PAGO_PED_2(C_COD_GRUPOCIA_IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                                C_COD_LOCAL_IN    VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                                C_NUM_PTO_VTA_IN  VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE)
    RETURN FARMACURSOR IS
    --VRESULTADO VARCHAR2(14000):= '';
    CUR FARMACURSOR;
  BEGIN
    OPEN CUR FOR
      SELECT A.COD_FORMA_PAGO,
             B.DESC_CORTA_FORMA_PAGO,
             DECODE(A.CANT_CUPON, NULL, 0),
             CASE A.TIP_MONEDA
               WHEN '01' THEN
                'SOLES'
               WHEN '02' THEN
                'DOLARES'
             END,
             TO_CHAR(A.IM_PAGO, '999,990.000'),
             TO_CHAR(A.IM_TOTAL_PAGO, '999,990.000'),
             NVL(A.TIP_MONEDA, ' '),
             '0',
             NVL(A.NUM_TARJ, ' '),
             DECODE(A.FEC_VENC_TARJ, NULL, ' '),
             NVL(A.NOM_TARJ, ' '),
             ' '
        FROM TMP_FORMA_PAGO_PED_CUPON A, VTA_FORMA_PAGO B
       WHERE A.COD_GRUPO_CIA = C_COD_GRUPOCIA_IN
         AND A.COD_LOCAL = C_COD_LOCAL_IN
         AND A.NUM_PED_VTA = C_NUM_PTO_VTA_IN
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_FORMA_PAGO = B.COD_FORMA_PAGO;

    RETURN CUR;
  END;


---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---


  FUNCTION F_SEPARAR_SEGEMENTO(A_CADENA VARCHAR2,
                               A_POS    INT

                               ) RETURN VARCHAR2 AS
    W_POSICION_INI INT;
    W_POSICION_FIN INT;
    A_SEPARADOR    VARCHAR2(10) := 'Ã';
  BEGIN
    IF A_POS = 1 THEN
      W_POSICION_INI := 1;
      W_POSICION_FIN := INSTR(A_CADENA, A_SEPARADOR, 1, 1) - 1;
      IF W_POSICION_FIN = -1 THEN
        W_POSICION_FIN := 1;
      END IF;
    ELSE
      W_POSICION_INI := INSTR(A_CADENA, A_SEPARADOR, 1, A_POS - 1) + 1;
      IF W_POSICION_INI = 1 THEN
        RETURN '';
      END IF;
      W_POSICION_FIN := INSTR(A_CADENA, A_SEPARADOR, 1, A_POS) - 1;
      IF W_POSICION_FIN = -1 THEN
        W_POSICION_FIN := LENGTH(A_CADENA);
      END IF;
    END IF;
    RETURN SUBSTR(A_CADENA,
                  W_POSICION_INI,
                  W_POSICION_FIN - W_POSICION_INI + 1);
  END;


---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---

  FUNCTION F_NUM_SEC_IMPR(CCODGRUPOCIA_IN IN CHAR,
                          CCODLOCAL_IN    IN CHAR,
                          CNUMPEDVTA_IN   IN CHAR) RETURN NUMBER

   AS
    V_CANTCOMPROBANTES NUMBER;

  BEGIN

    SELECT DISTINCT MAX(SEC_GRUPO_IMPR)
      INTO V_CANTCOMPROBANTES
      FROM VTA_PEDIDO_VTA_DET
     WHERE NUM_PED_VTA = CNUMPEDVTA_IN
       AND COD_GRUPO_CIA = CCODGRUPOCIA_IN
       AND COD_LOCAL = CCODLOCAL_IN;

    RETURN V_CANTCOMPROBANTES;
  END;


---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---

  FUNCTION F_IND_NVO_COBRO(C_COD_GRUPOCIA_IN VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                           C_COD_LOCAL_IN    VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE)
                            RETURN VARCHAR2 AS

    vInd    VARCHAR2(10) ;
  BEGIN

    begin
    SELECT NVL(T.LLAVE_TAB_GRAL,'N')
    into   vInd
    FROM   PBL_TAB_GRAL T
    WHERE  T.ID_TAB_GRAL = 515;
    EXCEPTION
    WHEN OTHERS THEN
    vInd := 'N';
    END;

    RETURN vInd;
  END;


---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---

FUNCTION F_COBRO_CONV(     V_ERROR_MENSAJE_OUT     OUT VARCHAR2,
                           C_COD_GRUPOCIA_IN       VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                           C_COD_LOCAL_IN          VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                           C_NUM_PTO_VTA_IN  VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                           ---GRAB-PEDIDO---
                           VNUSECUSU               CE_MOV_CAJA.SEC_USU_LOCAL%TYPE,
                           V_SEC_MOV_CAJ           CE_MOV_CAJA.SEC_MOV_CAJA%TYPE,
                           CCODNUMERA_IN           IN CHAR,
                           CCODMOTKARDEX_IN        IN CHAR,
                           CTIPDOCKARDEX_IN        IN CHAR,
                           CCODNUMERAKARDEX_IN     IN CHAR,
                           C_USU_CAJA              VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE,
                           C_PORCOPAGO             IN CHAR, -- % copago
                           CDESCDETALLEFORPAGO_IN  IN CHAR,
                           -- forma de pago --
                           A_CODFORMAPAGO          IN VARCHAR2_TABLE,
                           A_MONTO                 IN VARCHAR2_TABLE,
                           A_CODMONEDA             IN VARCHAR2_TABLE,
                           CTIPOCAMBIO             VTA_FORMA_PAGO_PEDIDO.VAL_TIP_CAMBIO%TYPE,
                           A_XXX                   IN VARCHAR2_TABLE,
                           A_IMPTOTAL              IN VARCHAR2_TABLE,
                           A_NUMTARJETA            IN VARCHAR2_TABLE,
                           A_FECVECTARJETA         IN VARCHAR2_TABLE,
                           A_NOMCLITARJETA         IN VARCHAR2_TABLE,
                           A_CANTCUPON             IN VARCHAR2_TABLE,
                           A_DNITARJETA            IN VARCHAR2_TABLE,
                           A_CODBOUCH              IN VARCHAR2_TABLE,
                           A_CODLOTE               IN VARCHAR2_TABLE
                          )

RETURN NUMBER AS


   V_EXITO                VARCHAR2(10);
   V_GRAB_FP              NUMBER(1);
   V_MSG_C                VARCHAR2(10000);

   BEGIN

        /*  ###############  CONVENIO ############### */
        /*  ###############  CONVENIO ############### */
       BEGIN
           V_EXITO:=         BTLMF_F_COBRA_PEDIDO_CONV_2
                                                 (   C_COD_GRUPOCIA_IN,
                                                     C_COD_LOCAL_IN,
                                                     VNUSECUSU,
                                                     C_NUM_PTO_VTA_IN,
                                                     V_SEC_MOV_CAJ,
                                                     CCODNUMERA_IN,
                                                     CCODMOTKARDEX_IN,
                                                     CTIPDOCKARDEX_IN,
                                                     CCODNUMERAKARDEX_IN,
                                                     C_USU_CAJA,
                                                     C_PORCOPAGO,
                                                     CDESCDETALLEFORPAGO_IN
                                                 );

       EXCEPTION
          WHEN OTHERS THEN
            V_ERROR_MENSAJE_OUT := 'FALLO....' || V_EXITO ||' AL COBRAR EL PEDIDO CONVENIO'||
                                    CHR(13) || SQLERRM;

            RETURN 0;
        END;

          IF (TRIM(V_EXITO) = 'EXITO') THEN

            BEGIN

            V_GRAB_FP := F_GRAB_FORMA_PAGO(
                              V_MSG_C,
                              C_COD_GRUPOCIA_IN,
                              C_COD_LOCAL_IN,
                              A_CODFORMAPAGO,
                              C_NUM_PTO_VTA_IN,
                              A_MONTO,
                              A_CODMONEDA,
                              CTIPOCAMBIO,
                              A_XXX,
                              A_IMPTOTAL,
                              A_NUMTARJETA,
                              A_FECVECTARJETA,
                              A_NOMCLITARJETA,
                              A_CANTCUPON,
                              C_USU_CAJA,
                              A_DNITARJETA,
                              A_CODBOUCH,
                              A_CODLOTE);

            IF V_GRAB_FP = 0 THEN

              V_ERROR_MENSAJE_OUT := V_MSG_C||'- CONVENIO';
              RETURN 0;

            ELSE
            begin
              Farma_Gral.CAJ_REGISTRA_TMP_INI_FIN_COBRO(C_COD_GRUPOCIA_IN,
                                               C_COD_LOCAL_IN,
                                               C_NUM_PTO_VTA_IN,
                                               'F');

              exception
              when others then
              V_ERROR_MENSAJE_OUT := 'Error al grabar FECHA DE FIN COBRO - CONVENIO' || CHR(13) ||
                sqlerrm;
              RETURN 0;
            end;

              V_ERROR_MENSAJE_OUT := V_MSG_C||'- CONVENIO';
              RETURN 1;

            END IF;

            EXCEPTION
            WHEN OTHERS THEN
            V_ERROR_MENSAJE_OUT := 'ERROR AL GRABAR LA FORMA DE PAGO' ||
                        CHR(13) || 'MAL ENVIO DE DATOS' ||
                        CHR(13) || SQLERRM;
            RETURN 0;

            END;


          ELSE

          V_ERROR_MENSAJE_OUT:=CHR(13)||'EL PEDIDO CONVENIO NO PUEDE SER COBRADO'||CHR(13) ;
          RETURN 0;

       END IF;

  END;


---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---


PROCEDURE P_CAMBIO_FP( C_MENSAJE          OUT VARCHAR2,
                         C_COD_GRUPOCIA_IN  VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                         C_COD_LOCAL_IN     VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                         C_TICK_COMP        VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE,
                         C_MONTO_TARJ       VTA_FORMA_PAGO_PEDIDO.IM_PAGO%TYPE,
                         V_COD_TARJ          VTA_FORMA_PAGO_PEDIDO.COD_FORMA_PAGO%TYPE,
                         V_NUM_TARJE         VTA_FORMA_PAGO_PEDIDO.NUM_TARJ%TYPE,
                         V_COD_AP            VTA_FORMA_PAGO_PEDIDO.COD_AUTORIZACION%TYPE
                         )
  IS


  V_NUM_PED_VTA       VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE ;
  V_COD_FORM_PAG      VTA_FORMA_PAGO_PEDIDO.NUM_TARJ%TYPE;
  V_TL_PAGO           VTA_FORMA_PAGO_PEDIDO.IM_PAGO%TYPE;


  V_VUELTO            VTA_FORMA_PAGO_PEDIDO.VAL_VUELTO%TYPE;
  V_CANT_PED          NUMBER(2);
  V_SUM_T             VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
  --**--CUR--**--
  V_SEC_MOV_C         CE_FORMA_PAGO_ENTREGA.SEC_MOV_CAJA%TYPE;
  V_SEC_FP_EN         CE_FORMA_PAGO_ENTREGA.SEC_FORMA_PAGO_ENTREGA%TYPE;
  V_CANT_ENT          CE_FORMA_PAGO_ENTREGA.CANT_VOUCHER%TYPE;
  V_MONT_ENT          CE_FORMA_PAGO_ENTREGA.MON_ENTREGA%TYPE;

  V_SEC_FPE           CE_FORMA_PAGO_ENTREGA.SEC_FORMA_PAGO_ENTREGA%TYPE;
  V_MONT_FIN          CE_FORMA_PAGO_ENTREGA.MON_ENTREGA%TYPE;
  V_FEC_PROCESO       VARCHAR2(20);

  CURSOR C1 ( V_NUM_PED_VTA_1 VTA_FORMA_PAGO_PEDIDO.NUM_TARJ%TYPE,
              V_COD_TARJ_1    VTA_FORMA_PAGO_PEDIDO.COD_FORMA_PAGO%TYPE
            )
  IS
              SELECT SEC_MOV_CAJA,sec_forma_pago_entrega
                     ,MON_ENTREGA
              FROM CE_FORMA_PAGO_ENTREGA c
              WHERE c.SEC_MOV_CAJA = (SELECT SEC_MOV_CAJA FROM CE_MOV_CAJA
              WHERE SEC_MOV_CAJA_ORIGEN = (select sec_mov_caja
                                           from vta_pedido_vta_cab
                                           where num_ped_vta = V_NUM_PED_VTA_1)
              AND TIP_MOV_CAJA = 'C')
              AND c.COD_FORMA_PAGO = V_COD_TARJ_1;

   cepm_rec C1%ROWTYPE;

BEGIN

      SELECT NUM_PED_VTA,TO_CHAr(fec_crea_comp_pago,'DD/mm/yyyy')
      INTO V_NUM_PED_VTA,V_FEC_PROCESO
      FROM VTA_COMP_PAGO
      WHERE NUM_COMP_PAGO = C_TICK_COMP ;


      SELECT COD_FORMA_PAGO,SUM(IM_PAGO-val_vuelto),val_vuelto
             INTO V_COD_FORM_PAG,V_TL_PAGO,V_VUELTO
      FROM   VTA_FORMA_PAGO_PEDIDO
      WHERE  NUM_PED_VTA = V_NUM_PED_VTA
      AND    COD_LOCAL = C_COD_LOCAL_IN
      GROUP BY COD_FORMA_PAGO,val_vuelto;

IF( V_COD_FORM_PAG <> V_COD_TARJ) THEN

      IF(V_TL_PAGO > C_MONTO_TARJ)THEN

             INSERT INTO VTA_FORMA_PAGO_PEDIDO(COD_GRUPO_CIA,
												COD_LOCAL,
												COD_FORMA_PAGO,
												NUM_PED_VTA,
												IM_PAGO,
												TIP_MONEDA,
												VAL_TIP_CAMBIO,
												VAL_VUELTO,
												IM_TOTAL_PAGO,
												NUM_TARJ,
												FEC_VENC_TARJ,
												NOM_TARJ,
												FEC_CREA_FORMA_PAGO_PED,
												USU_CREA_FORMA_PAGO_PED,
												FEC_MOD_FORMA_PAGO_PED,
												USU_MOD_FORMA_PAGO_PED,
												CANT_CUPON,
												TIPO_AUTORIZACION,
												COD_LOTE,
												COD_AUTORIZACION,
												DNI_CLI_TARJ,
												COD_CIA,
												IND_ANUL_LOTE_CERRADO)
                VALUES( C_COD_GRUPOCIA_IN,
                        C_COD_LOCAL_IN, --LOCAL
                        V_COD_TARJ,--TARJETA
                        V_NUM_PED_VTA,
                        C_MONTO_TARJ, --MONTO
                        '01',
                        2.75,
                        0.0,
                        C_MONTO_TARJ,
                        V_NUM_TARJE,
                        '',
                        '',
                        SYSDATE,
                        'RHERRERA',
                        '',
                        '',
                        0,
                        '',
                        '',
                        V_COD_AP,
                        '',
                        '002',
                        'N');

              UPDATE vta_forma_pago_pedido
              SET
              im_pago = (V_TL_PAGO-C_MONTO_TARJ+V_VUELTO),
              im_total_pago = (V_TL_PAGO-C_MONTO_TARJ+V_VUELTO),
              val_vuelto = V_VUELTO,
              usu_mod_forma_pago_ped = 'RHERRERA',
              fec_mod_forma_pago_ped = sysdate
              WHERE num_ped_vta = V_NUM_PED_VTA
              AND cod_forma_pago = V_COD_FORM_PAG;


             ELSE

              UPDATE vta_forma_pago_pedido
              SET cod_forma_pago = V_COD_TARJ,--00083 visa, 00084 mc, 00024 cmr, 00087 din, 00088 amex
              num_tarj = V_NUM_TARJE,
              cod_autorizacion = V_COD_AP, --000000
              usu_mod_forma_pago_ped = 'RHERRERA',
              fec_mod_forma_pago_ped = SYSDATE
              WHERE num_ped_vta = V_NUM_PED_VTA
              AND cod_forma_pago = V_COD_FORM_PAG;

                  IF(V_VUELTO > 0)THEN

                    UPDATE vta_forma_pago_pedido
                    SET
                    val_vuelto = 0,
                    usu_mod_forma_pago_ped = 'RHERRERA',
                    fec_mod_forma_pago_ped = sysdate
                    WHERE num_ped_vta = V_NUM_PED_VTA
                    AND cod_forma_pago = V_COD_FORM_PAG;

                  END IF;

             END IF;

          --############# PASO 2 ##########--
              SELECT COUNT(1),SUM(IM_PAGO-val_vuelto)
                     INTO V_CANT_PED,V_SUM_T
              FROM VTA_FORMA_PAGO_PEDIDO
              WHERE NUM_PED_VTA IN (
              SELECT NUM_PED_VTA
              FROM VTA_PEDIDO_VTA_CAB
              WHERE SEC_MOV_CAJA = (select sec_mov_caja
                                    from vta_pedido_vta_cab
                                    where num_ped_vta = V_NUM_PED_VTA)
              )
              AND COD_FORMA_PAGO = V_COD_TARJ
              GROUP BY COD_FORMA_PAGO;



    OPEN C1 (V_NUM_PED_VTA,V_COD_TARJ);
    LOOP
      FETCH C1 INTO cepm_rec ;
    --     FOR R IN C1 (V_NUM_PED_VTA,V_COD_TARJ)  LOOP

          IF C1%NOTFOUND THEN
                --- SECUENCIAL ---
                SELECT NVL(MAX(sec_forma_pago_entrega),0)
                       INTO V_SEC_FPE
                FROM CE_FORMA_PAGO_ENTREGA
                WHERE SEC_MOV_CAJA = (SELECT SEC_MOV_CAJA FROM CE_MOV_CAJA
                WHERE SEC_MOV_CAJA_ORIGEN = (select sec_mov_caja
                                             from vta_pedido_vta_cab
                                             where num_ped_vta = V_NUM_PED_VTA)
                AND TIP_MOV_CAJA = 'C');

                ----SEC MOV CAJA---
                SELECT SEC_MOV_CAJA INTO V_SEC_MOV_C
                FROM CE_MOV_CAJA
                WHERE SEC_MOV_CAJA_ORIGEN = (
                select sec_mov_caja
                from vta_pedido_vta_cab where num_ped_vta = V_NUM_PED_VTA)
                AND TIP_MOV_CAJA = 'C';



                INSERT INTO CE_FORMA_PAGO_ENTREGA
                VALUES(
                        C_COD_GRUPOCIA_IN,
                        C_COD_LOCAL_IN,	 -- LOCAL
                        V_SEC_MOV_C,	 -- CAJA
                        V_SEC_FPE+1,	 -- SEC_FORMA_PAGO
                        V_COD_TARJ, -- FORMA PAGO
                        '01',	--MONEDA SOLES
                        V_CANT_PED,	--CANT VOUCH
                        C_MONTO_TARJ,	--MONTO
                        C_MONTO_TARJ, 	--MONTO TOTAL
                        'RHERRERA',	--USU
                        sysdate,	--FECC
                        '',
                        '',
                        '',
                        'A'
                        );

          ELSE

                IF((V_SUM_T - cepm_rec.mon_entrega )= C_MONTO_TARJ )THEN
                    UPDATE CE_FORMA_PAGO_ENTREGA
                    SET
                    cant_voucher = V_CANT_PED ,--cant_voucher+1,
                    mon_entrega = V_SUM_T ,
                    mon_entrega_total =V_SUM_T ,
                    usu_mod_forma_pago_ent = 'RHERRERA' ,
                    fec_mod_forma_pago_ent = SYSDATE
                    WHERE SEC_MOV_CAJA = cepm_rec.Sec_Mov_Caja
                    AND COD_FORMA_PAGO = V_COD_TARJ
                    AND SEC_FORMA_PAGO_ENTREGA = cepm_rec.Sec_Forma_Pago_Entrega;

                END IF;


          END IF;


      END LOOP;
     CLOSE C1 ;
                  SELECT MON_ENTREGA, CANT_VOUCHER
                  INTO   V_MONT_FIN , V_CANT_ENT
                  FROM CE_FORMA_PAGO_ENTREGA
                  WHERE SEC_MOV_CAJA = (SELECT SEC_MOV_CAJA FROM CE_MOV_CAJA
                  WHERE SEC_MOV_CAJA_ORIGEN =
                  (select sec_mov_caja from vta_pedido_vta_cab where num_ped_vta = V_NUM_PED_VTA)
                  AND TIP_MOV_CAJA = 'C');

          IF ( (V_MONT_ENT = V_SUM_T )AND (V_CANT_PED = V_CANT_ENT )) THEN


                C_MENSAJE:='OK.';


          END IF;



      ELSE

      C_MENSAJE:='ERROR.';

END IF;


EXCEPTION
    WHEN OTHERS THEN
      C_MENSAJE:='ERROR.'||SQLERRM;



END;


---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---


FUNCTION F_GRAB_FORMA_PAGO(
                            V_MSG                     OUT VARCHAR2,
                            C_COD_GRUPOCIA_IN         VTA_PEDIDO_VTA_DET.COD_GRUPO_CIA%TYPE,
                            C_COD_LOCAL_IN            VTA_PEDIDO_VTA_DET.COD_LOCAL%TYPE,
                            A_CODFORMAPAGO            IN VARCHAR2_TABLE,
                            C_NUM_PTO_VTA_IN          VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                            A_MONTO                   IN VARCHAR2_TABLE,
                            A_CODMONEDA               IN VARCHAR2_TABLE,
                            CTIPOCAMBIO               VTA_FORMA_PAGO_PEDIDO.VAL_TIP_CAMBIO%TYPE,
                            A_XXX                     IN VARCHAR2_TABLE,
                            A_IMPTOTAL                IN VARCHAR2_TABLE,
                            A_NUMTARJETA              IN VARCHAR2_TABLE,
                            A_FECVECTARJETA           IN VARCHAR2_TABLE,
                            A_NOMCLITARJETA           IN VARCHAR2_TABLE,
                            A_CANTCUPON               IN VARCHAR2_TABLE,
                            C_USU_CAJA                VTA_PEDIDO_VTA_DET.USU_CREA_PED_VTA_DET%TYPE,
                            A_DNITARJETA              IN VARCHAR2_TABLE,
                            A_CODBOUCH                IN VARCHAR2_TABLE,
                            A_CODLOTE                 IN VARCHAR2_TABLE
                           )RETURN NUMBER

AS
BEGIN

        FOR I IN 1 .. A_CODFORMAPAGO.COUNT LOOP
          PTOVENTA_CAJ.CAJ_GRAB_NEW_FORM_PAGO_PEDIDO(
                 C_COD_GRUPOCIA_IN,
                 C_COD_LOCAL_IN,
                 A_CODFORMAPAGO(I),
                 C_NUM_PTO_VTA_IN,
                 TO_NUMBER(A_MONTO(I),
                           '999,990.000'),
                 A_CODMONEDA(I),
                 CTIPOCAMBIO,
                 TO_NUMBER(A_XXX(I),
                           '999,990.000'),
                 TO_NUMBER(A_IMPTOTAL(I),
                           '999,990.000'),
                 A_NUMTARJETA(I),
                 A_FECVECTARJETA(I),
                 A_NOMCLITARJETA(I),
                 TO_NUMBER(A_CANTCUPON(I)),
                 C_USU_CAJA,
                 A_DNITARJETA(I),
                 A_CODBOUCH(I),
                 A_CODLOTE(I)
                                           );
        END LOOP;

        IF PTOVENTA_CAJ.CAJ_F_VERIFICA_PED_FOR_PAG(C_COD_GRUPOCIA_IN,
                                                   C_COD_LOCAL_IN,
                                                   C_NUM_PTO_VTA_IN) =
           'ERROR' THEN

          V_MSG := 'ERROR *****' || 'EL PEDIDO NO PUEDE SER COBRADO.' ||
                   CHR(13) ||' LOS TOTALES DE FORMAS DE PAGO Y CABECERA NO COINCIDEN.' ||
                   CHR(13) ||' COMUNÍQUESE CON EL OPERADOR DE SISTEMAS INMEDIATAMENTE.' ||
                   CHR(13) ||' NO CIERRE LA VENTANA.';

          -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014

          RETURN 0;

          ELSE
            V_MSG := 'GRABO COBRO Y FORMAS DE PAGO.... OKK'||
                     CHR(13)||' RICCGOGO ESTUVO AQUÍ';
            RETURN 1;
        END IF;

      EXCEPTION
        WHEN OTHERS THEN
          -- farma_utility.liberar_transaccion; DUBILLUZ 16.04.2014
          V_MSG               := 'ERROR. NO SE PUDO REGISTRAR UNA DE LAS FORMAS DE PAGOS..' ||
                                 CHR(13) || SQLERRM;

          RETURN 0;
      END;


---#####################################################################---
---#####################################################################---
---#####################################################################---
---#####################################################################---


FUNCTION BTLMF_F_COBRA_PEDIDO_CONV_2(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cNuSecUsu_in IN CHAR,
                                   cNumPedVta_in IN CHAR,
                                   cSecMovCaja_in IN CHAR,
                                   cCodNumera_in IN CHAR,
                                   cCodMotKardex_in IN CHAR,
                                   cTipDocKardex_in IN CHAR,
                                   cCodNumeraKardex_in IN CHAR,
                                   cUsuCreaCompPago_in IN CHAR,
                                   cPorCopago IN char,
                                   cDescDetalleForPago_in IN CHAR DEFAULT ' ')

RETURN CHAR IS

 v_nSecCompPago VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
 v_cIndGraboComp CHAR(1);
 v_cCodCliLocal VTA_PEDIDO_VTA_CAB.COD_CLI_LOCAL%TYPE;
 v_cNomCliPedVta VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE;
 v_cRucCliPedVta VTA_PEDIDO_VTA_CAB.RUC_CLI_PED_VTA%TYPE;
 v_cDirCliPedVta VTA_PEDIDO_VTA_CAB.DIR_CLI_PED_VTA%TYPE;
 v_nValRedondeo VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
 v_cIndDistrGratuita VTA_PEDIDO_VTA_CAB.IND_DISTR_GRATUITA%TYPE;
 v_cIndDelivAutomatico VTA_PEDIDO_VTA_CAB.IND_DELIV_AUTOMATICO%TYPE;
 v_nContador NUMBER;
 v_nCont NUMBER;

 v_Resultado CHAR(7);
 --Variable de Indicador para Cobro
 --24/08/2007   dubilluz  creacion
 v_Indicador_Cobro CHAR(1);
 v_Indicador_Linea Varchar2(3453);

 --JCORTEZ 15.05.09
 TipComp CHAR(2);
 TipCompAux CHAR(2);
 SecUso CHAR(3);
 NumCaja NUMBER(4);

 v_Flg_Guardar_Comp CHAR(3) := 'N';

 vAhorroPedido number;

 --JCORTEZ 10.06.09
 cIP VARCHAR(20);

 --FRAMIREZ 17.01.2012
 v_TipDocBenificiario MAE_TIPO_COMP_PAGO_BTLMF.COD_TIPODOC%TYPE;
 v_TipDocCliente MAE_TIPO_COMP_PAGO_BTLMF.COD_TIPODOC%TYPE;
 v_TipDocBenificiarioMF MAE_TIPO_COMP_PAGO_BTLMF.TIP_COMP_PAGO%TYPE;
 v_TipDocClienteMF MAE_TIPO_COMP_PAGO_BTLMF.TIP_COMP_PAGO%TYPE;
 V_COD_CONVENIO MAE_CONVENIO.COD_CONVENIO%TYPE;
 V_NUM_PRODUCTOS MAE_COMP_CONV_LINEAS.NUM_PRODUCTOS%TYPE;
 --RHERRERA 29.04.2014 --
 V_NUM_LINEA_E MAE_COMP_CONV_LINEAS.NUM_PRODUCTOS%TYPE;
 V_NUM_LINEA_B MAE_COMP_CONV_LINEAS.NUM_PRODUCTOS%TYPE;



 v_NUM_PED_VTA VTA_COMP_PAGO.NUM_PED_VTA%TYPE;
 v_SEC_COMP_PAGO VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;

 v_TIP_COMP_PAGO     VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE;
 v_NUM_COMP_PAGO     VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
 v_NUM_COMP_PAGO_REF VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;

 v_SEC_MOV_CAJA VTA_COMP_PAGO.SEC_MOV_CAJA%TYPE;
 v_CANT_ITEM VTA_COMP_PAGO.CANT_ITEM%TYPE;
 v_COD_CLI_LOCAL VTA_COMP_PAGO.COD_CLI_LOCAL%TYPE;
 v_NOM_IMPR_COMP VTA_COMP_PAGO.NOM_IMPR_COMP%TYPE;
 v_DIREC_IMPR_COMP VTA_COMP_PAGO.DIREC_IMPR_COMP%TYPE;
 v_NUM_DOC_IMPR VTA_COMP_PAGO.NUM_DOC_IMPR%TYPE;
 v_VAL_REDONDEO_COMP_PAGO VTA_COMP_PAGO.VAL_REDONDEO_COMP_PAGO%TYPE;
 v_USU_CREA_COMP_PAGO VTA_COMP_PAGO.USU_CREA_COMP_PAGO%TYPE;
 v_PORC_IGV_COMP_PAGO VTA_COMP_PAGO.PORC_IGV_COMP_PAGO%TYPE;


 v_FlgKardex         MAE_CONVENIO.NUM_DOC_FLG_KDX%TYPE;
 v_Flg_Tipo_Convenio MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE;

 vNumeroProd NUMBER;
 vNumCompPago VTA_PEDIDO_VTA_DET.NUM_COMP_PAGO%TYPE;
 curDetallaPedido FarmaCursor;

 curCompPago  FarmaCursor;

 vIndAfectaKardex   VTA_COMP_PAGO.IND_AFECTA_KARDEX%TYPE;

 v_NOMBRE_PC VTA_PEDIDO_VTA_CAB.NAME_PC_COB_PED%TYPE;
 v_IP_PC VTA_PEDIDO_VTA_CAB.IP_COB_PED%TYPE;
 v_DNI_USU PBL_USU_LOCAL.DNI_USU%TYPE;

 V_PCT_BENEFICIARIO MAE_CONVENIO.PCT_BENEFICIARIO%TYPE;
 V_PCT_EMPRESA MAE_CONVENIO.PCT_EMPRESA%TYPE;
 V_FLG_POLITICA    MAE_CONVENIO.FLG_POLITICA%TYPE;

 nSecImpresora_TICKET vta_impr_local.sec_impr_local%type;

 vIndGeneraUnSoloComproBenif CHAR(1) := 'N';

  curCompPagoTemp  FarmaCursor;
  curFormPagoTemp  FarmaCursor;
  curPedidoDetTemp  FarmaCursor;

  --Datos Escala
  curEscala FarmaCursor;
  vCodConvenio         REL_CONVENIO_ESCALA.COD_CONVENIO%TYPE;
  vImp_minimo          REL_CONVENIO_ESCALA.IMP_MINIMO%TYPE;
  vImp_maximo          REL_CONVENIO_ESCALA.IMP_MAXIMO%TYPE;
  vFlg_porcentaje      REL_CONVENIO_ESCALA.FLG_PORCENTAJE%TYPE;
  vImp_monto           REL_CONVENIO_ESCALA.IMP_MONTO%TYPE;
  vMontoNeto           VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;

  vMontoCreditoEmpre FLOAT := 0;
  V_COD_FORMATO_GUIA   MAE_COMP_CONV_LINEAS.COD_FORMATO%TYPE;

    v_nCopago FLOAT;
    v_nPctBenef mae_convenio.pct_beneficiario%TYPE;
    v_nPctEmp      mae_convenio.pct_empresa%TYPE;

 BEGIN


  --0.OBTIENE DATOS PARA GUARDAR EN EL PEDIDO NECESARIOS PARA EL ENVIO AL RAC
      SELECT SYS_CONTEXT('USERENV', 'TERMINAL') INTO v_NOMBRE_PC FROM dual;
      SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO v_IP_PC FROM DUAL;

      SELECT U.DNI_USU
        INTO v_DNI_USU
        FROM PBL_USU_LOCAL U
       WHERE U.COD_GRUPO_CIA = cCodGrupoCia_in
         AND U.COD_LOCAL = cCodLocal_in
         AND U.SEC_USU_LOCAL = cNuSecUsu_in;


 --FIN PASO 0

 --1.Validaciones para el realizar el cobro pedido
  BEGIN
   SELECT VTA.COD_CONVENIO,VTA.VAL_NETO_PED_VTA
     INTO V_COD_CONVENIO,vMontoNeto
     FROM VTA_PEDIDO_VTA_CAB VTA
    WHERE VTA.COD_GRUPO_CIA = cCodGrupoCia_in
      AND VTA.COD_LOCAL = cCodLocal_in
      AND VTA.NUM_PED_VTA = cNumPedVta_in;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20015, 'El pedido no existe. ' || cNumPedVta_in);
   v_Resultado := 'ERROR';
   return v_Resultado;
  END;

  IF V_COD_CONVENIO IS NULL THEN
   RAISE_APPLICATION_ERROR(-20040, 'El codigo del convenio esta vacio para este pedido. ' || cNumPedVta_in);
   v_Resultado := 'ERROR';
   return v_Resultado;
  END IF;
 --FIN PASO 1

 --2. OBTIENE DATOS DEL CONVENIO PARA GENERAR LUEGO COMPROBANTES
  /*
  1 = COD_TIPDOC_CLIENTE ' Empresa (Nulo)
  2 = COD_TIPDOC_BENEFICIARIO' Beneficiario

  MUEVE_KARDEX_EMPRESA      number := 1;
  MUEVE_KARDEX_BENEFICIARIO number := 2;
  */
  BEGIN
   SELECT CONV.COD_TIPDOC_BENEFICIARIO,
          CONV.Cod_Tipdoc_Cliente,
          case
            when CONV.COD_TIPDOC_BENEFICIARIO is not null
             and CONV.Cod_Tipdoc_Cliente      is not null
            then nvl(CONV.NUM_DOC_FLG_KDX,MUEVE_KARDEX_EMPRESA)

            when CONV.COD_TIPDOC_BENEFICIARIO is null
             and CONV.Cod_Tipdoc_Cliente      is not null
            then MUEVE_KARDEX_EMPRESA

            when CONV.COD_TIPDOC_BENEFICIARIO is not null
             and CONV.Cod_Tipdoc_Cliente      is  null
            then MUEVE_KARDEX_BENEFICIARIO

          end,
          CONV.COD_TIPO_CONVENIO,
          CONV.PCT_BENEFICIARIO,
          CONV.PCT_EMPRESA,
          CONV.FLG_POLITICA
     INTO v_TipDocBenificiario,
          v_TipDocCliente,
          v_FlgKardex, -- CUAL MUEVE KARDEX EMPRESA o BENEFICIARIO
          v_Flg_Tipo_Convenio,
          V_PCT_BENEFICIARIO,
          V_PCT_EMPRESA,
          V_FLG_POLITICA
     FROM MAE_CONVENIO CONV
    WHERE CONV.COD_CONVENIO = V_COD_CONVENIO;
   EXCEPTION
   WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20000, 'No encontro los datos del convenio para este pedido. ' || cNumPedVta_in);
   v_Resultado := 'ERROR';
   return v_Resultado;
  END;

  IF  v_Flg_Tipo_Convenio IS NULL THEN
    RAISE_APPLICATION_ERROR(-20028, 'Error. El tipo convenio esta vacio. ' || cNumPedVta_in);
      v_Resultado := 'ERROR';
  END IF;


  IF v_TipDocBenificiario IS NULL AND v_TipDocCliente IS NULL THEN
    RAISE_APPLICATION_ERROR(-20022, 'No hay un tipo de comprobante asignado para este pedido. ' || cNumPedVta_in);
    v_Resultado := 'ERROR';
  END IF;

 --Valida el documentos permido para el benificiario

 dbms_output.put_line('v_TipDocBenificiario ');


  IF v_TipDocBenificiario IS   NOT NULL THEN
    BEGIN
      SELECT CP.TIP_COMP_PAGO
       INTO v_TipDocBenificiarioMF
       FROM MAE_TIPO_COMP_PAGO_BTLMF CP
      WHERE CP.COD_TIPODOC = v_TipDocBenificiario;
      EXCEPTION
      WHEN NO_DATA_FOUND  THEN
      RAISE_APPLICATION_ERROR(-20033, 'Es invalido el tipo de documento del Benificiario para este pedido. ' || v_TipDocBenificiario);
      v_Resultado := 'ERROR';
      return v_Resultado;
    END;
  END IF;

 --Valida el documentos permido para el cliente
  IF v_TipDocCliente IS  NOT NULL THEN
    BEGIN
     SELECT CP.TIP_COMP_PAGO
       INTO v_TipDocClienteMF
       FROM MAE_TIPO_COMP_PAGO_BTLMF CP
      WHERE CP.COD_TIPODOC = v_TipDocCliente;
     EXCEPTION
     WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20034, 'Es invalido el tipo de documento del Cliente para este pedido. ' || v_TipDocCliente);
     v_Resultado := 'ERROR';
     return v_Resultado;
    END;
  END IF;

 --VALIDACION SEGUN EL TIPO DE CONVENIO QUE COMPROBANTE DEBE DE EXITIR

  IF v_Flg_Tipo_Convenio = FLG_TIP_CONV_CONTADO THEN
      IF  v_TipDocBenificiario IS NULL THEN
         RAISE_APPLICATION_ERROR(-20055, 'No hay un comprobante definido del Benificiario  para este convenio ' || V_COD_CONVENIO);
      END IF;

      IF  v_TipDocBenificiario IS NOT NULL AND v_TipDocCliente IS NOT NULL THEN
         RAISE_APPLICATION_ERROR(-20066, 'Debe definirse un solo un comprobante Benificiario, si es un convenio al contado ' || V_COD_CONVENIO);
      END IF;

  ELSIF v_Flg_Tipo_Convenio = FLG_TIP_CONV_COPAGO THEN

      IF  v_TipDocBenificiario IS NOT NULL AND v_TipDocCliente IS NOT NULL THEN
          NULL;
      ELSE
         RAISE_APPLICATION_ERROR(-20066, 'Debe definirse un solo un comprobante Benificiario, si es un convenio al contado ' || V_COD_CONVENIO);

      END IF;

  END IF;

  nSecImpresora_TICKET := 0;

  if v_TipDocBenificiarioMF = COD_TIP_COMP_TICKET or v_TipDocClienteMF = COD_TIP_COMP_TICKET then
     -- validar que exista el sec impresora local de ticket y obtenerlo como parametro
       SELECT B.TIP_COMP
        INTO TipCompAux
        FROM VTA_IMPR_LOCAL B
       WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
         AND B.COD_LOCAL = cCodLocal_in
         AND B.SEC_IMPR_LOCAL
          IN (SELECT A.SEC_IMPR_LOCAL
                FROM VTA_IMPR_IP A
               WHERE A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND TRIM(A.IP) = TRIM(v_IP_PC));

      --Si el tipo de comprobante del usuario que cobra es distinto al lo que se definio en convenio.
      IF (TipCompAux <> COD_TIP_COMP_TICKET) then
         RAISE_APPLICATION_ERROR(-20021, 'El tipo de Comprobante que tiene el IP no tiene asociado una Ticketera.');
      END IF;

      ---------------------------------------
      SELECT B.SEC_IMPR_LOCAL
        INTO nSecImpresora_TICKET
        FROM VTA_IMPR_LOCAL B
       WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
         AND B.COD_LOCAL = cCodLocal_in
         AND B.SEC_IMPR_LOCAL IN
             (SELECT A.SEC_IMPR_LOCAL
                FROM VTA_IMPR_IP A
               WHERE A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND TRIM(A.IP) = TRIM(v_IP_PC));

  end if;


  SELECT VTA_CAB.VAL_REDONDEO_PED_VTA,
         VTA_CAB.COD_CLI_LOCAL,
         NVL(VTA_CAB.NOM_CLI_PED_VTA, ' '),
         NVL(VTA_CAB.RUC_CLI_PED_VTA, ' '),
         NVL(VTA_CAB.DIR_CLI_PED_VTA, ' '),
         VTA_CAB.IND_DISTR_GRATUITA,
         VTA_CAB.IND_DELIV_AUTOMATICO
    INTO v_nValRedondeo,
         v_cCodCliLocal,
         v_cNomCliPedVta,
         v_cRucCliPedVta,
         v_cDirCliPedVta,
         v_cIndDistrGratuita,
         v_cIndDelivAutomatico
    FROM VTA_PEDIDO_VTA_CAB VTA_CAB
   WHERE VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
     AND VTA_CAB.COD_LOCAL = cCodLocal_in
     AND VTA_CAB.NUM_PED_VTA = cNumPedVta_in;



/* ############################################################################### */
/* ############################  INICIO   ######################################## */
/* ############################################################################### */

--###############################################################--
  /*                 LINEAS BENEFICIARIO                       */
--###############################################################--
  IF v_TipDocBenificiarioMF IS NOT NULL THEN

       IF v_TipDocBenificiarioMF=COD_TIP_COMP_GUIA THEN
          BEGIN
		       --ERIOS 20.12.2013 2:Formato mediano (FASA)
               SELECT DECODE(ID_TAB_GRAL ,COD_FORMATO_GUIA_CHICA,'0',379,'2','1') INTO V_COD_FORMATO_GUIA
               FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL IN (279,280,379) AND EST_TAB_GRAL='A'
			   AND COD_CIA = (select cod_cia from pbl_local where cod_grupo_cia = cCodGrupoCia_in and cod_local = cCodLocal_in);
          EXCEPTION
                   WHEN NO_DATA_FOUND THEN
                        RAISE_APPLICATION_ERROR(-20021,'Falta Configurar formato de Guia');
                   WHEN OTHERS THEN
                        RAISE_APPLICATION_ERROR(-20021,'Error en la Configuracion de Tamaño de Guia');
          END;

          BEGIN
                SELECT T.NUM_PRODUCTOS
                       INTO V_NUM_LINEA_B
                FROM MAE_COMP_CONV_LINEAS T
                WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
                AND T.TIP_COMP      = v_TipDocBenificiarioMF
                AND T.COD_FORMATO=V_COD_FORMATO_GUIA;
           EXCEPTION
                     WHEN NO_DATA_FOUND THEN
                          RAISE_APPLICATION_ERROR(-20021,'Falta Configurar Tamaño de Productos que soporta el Documento');
           END;

       ELSE
           BEGIN
                SELECT T.NUM_PRODUCTOS
                       INTO V_NUM_LINEA_B
                FROM MAE_COMP_CONV_LINEAS T
                WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
                AND T.TIP_COMP      = v_TipDocBenificiarioMF;
           EXCEPTION
                     WHEN NO_DATA_FOUND THEN
                          RAISE_APPLICATION_ERROR(-20021,'Falta Configurar Tamaño de Productos que soporta el Documento:');
           END;
       END IF;
    END IF;
--###############################################################--
  /*                 LINEAS EMPRESA-CLIENTE                    */
--###############################################################--
   IF v_TipDocClienteMF IS NOT NULL THEN
         IF v_TipDocClienteMF=COD_TIP_COMP_GUIA THEN
          BEGIN
				--ERIOS 20.12.2013 2:Formato mediano (FASA)
               SELECT DECODE(ID_TAB_GRAL ,COD_FORMATO_GUIA_CHICA,'0',379,'2','1') INTO V_COD_FORMATO_GUIA
               FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL IN (279,280,379) AND EST_TAB_GRAL='A'
			   AND COD_CIA = (select cod_cia from pbl_local where cod_grupo_cia = cCodGrupoCia_in and cod_local = cCodLocal_in);
          EXCEPTION
                   WHEN NO_DATA_FOUND THEN
                        RAISE_APPLICATION_ERROR(-20021,'Falta Configurar formato de Guia');
                   WHEN OTHERS THEN
                        RAISE_APPLICATION_ERROR(-20021,'Error en la Configuracion de Tamaño de Guia');
          END;

          BEGIN
                SELECT T.NUM_PRODUCTOS
                       INTO V_NUM_LINEA_E
                FROM MAE_COMP_CONV_LINEAS T
                WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
                AND T.TIP_COMP      = v_TipDocClienteMF
                AND T.COD_FORMATO=V_COD_FORMATO_GUIA;
           EXCEPTION
                     WHEN NO_DATA_FOUND THEN
                          RAISE_APPLICATION_ERROR(-20021,'Falta Configurar Tamaño de Productos que soporta el Documento');
           END;

       ELSE
           BEGIN
                SELECT T.NUM_PRODUCTOS
                       INTO V_NUM_LINEA_E
                FROM MAE_COMP_CONV_LINEAS T
                WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
                AND T.TIP_COMP      = v_TipDocClienteMF;
           EXCEPTION
                     WHEN NO_DATA_FOUND THEN
                          RAISE_APPLICATION_ERROR(-20021,'Falta Configurar Tamaño de Productos que soporta el Documento');
           END;
       END IF;
    END IF;



    V_NUM_PRODUCTOS:=BTLMF_F_LINEA_COMPROBANTE(V_NUM_LINEA_B,
                                               V_NUM_LINEA_E,
                                               v_TipDocBenificiarioMF,
                                               v_TipDocClienteMF);




         /************************************************************/
         /*******************       BENEFICIARIO      ****************/
         /************************************************************/




   -- Obtenemos el Porcentaje del Benifiaciario y la empresa segun la escala.

       /*BEGIN
                SELECT distinct t.cod_convenio
                  INTO vCodConvenio
                  FROM REL_CONVENIO_ESCALA t
                 WHERE t.cod_convenio =  V_COD_CONVENIO;

              EXCEPTION
                   WHEN NO_DATA_FOUND THEN
                        vCodConvenio:=NULL;
       END;*/


       BEGIN


           /*IF vCodConvenio IS NOT NULL THEN
                  OPEN curEscala FOR
                  SELECT
                         t.imp_minimo,
                         t.imp_maximo,
                         t.flg_porcentaje,
                         t.imp_monto
                    FROM REL_CONVENIO_ESCALA t
                   WHERE t.cod_convenio =  vCodConvenio;
           END IF;*/

           IF  v_Flg_Tipo_Convenio = FLG_TIP_CONV_COPAGO THEN

             /*IF vCodConvenio IS NOT NULL THEN
               LOOP
                 FETCH curEscala
                 INTO vImp_minimo, vImp_maximo, vFlg_porcentaje, vImp_monto;
                 EXIT WHEN curEscala%NOTFOUND;
                 IF (vMontoNeto  > vImp_minimo or vMontoNeto = vImp_minimo)
                    AND (vMontoNeto < vImp_maximo)  THEN
                   -- 1:PORCENTAJE, 0;IMPORTE
                   IF vFlg_porcentaje = '1' THEN
                      V_PCT_BENEFICIARIO  := vImp_monto;
                      V_PCT_EMPRESA       := 100 - vImp_monto;
                   ELSE
                      vMontoCreditoEmpre :=  vMontoNeto - vImp_monto;
                      V_PCT_EMPRESA      :=  ROUND((vMontoCreditoEmpre * 100)/vMontoNeto,2);
                      V_PCT_BENEFICIARIO :=  ROUND((vImp_monto*100)/vMontoNeto,2);
                   END IF;
                   EXIT;
                 END IF;
                END LOOP;
                close curEscala;
            END IF;*/
			--ERIOS 2.4.6 Calculo por escala
			v_nCopago := PTOVENTA_CONV_BTLMF.FN_CALCULA_COPAGO(V_COD_CONVENIO,vMontoNeto,v_nPctBenef,v_nPctEmp);
			IF v_nCopago <> -1 THEN
				V_PCT_BENEFICIARIO := v_nPctBenef;
				V_PCT_EMPRESA := v_nPctEmp;
			END IF;
         END IF;

      END;

      IF V_FLG_POLITICA='0' THEN
         IF TO_NUMBER(cPorCopago,'99999.00')>0 THEN
             V_PCT_BENEFICIARIO:=TO_NUMBER(cPorCopago,'99999.00');
             V_PCT_EMPRESA     :=100-TO_NUMBER(cPorCopago,'99999.00');
         END IF;
      END IF;

  ----######## Grabamos el comprobante de pago de la BENEFICIARIO ########----
  IF v_TipDocBenificiarioMF IS NOT NULL THEN

   IF v_TipDocClienteMF IS NOT NULL THEN
     vIndGeneraUnSoloComproBenif := 'N';
   ELSE
     vIndGeneraUnSoloComproBenif := 'S';
   END IF;


       dbms_output.put_line('Grabamos el comprobante de pago del Benificiario');
       IF v_FlgKardex = MUEVE_KARDEX_BENEFICIARIO THEN
        vIndAfectaKardex := 'S';
       ELSE
        vIndAfectaKardex := 'N';
       END IF;

       PTOVENTA_CONV_BTLMF.BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
                                      cCodLocal_in,
                                      cUsuCreaCompPago_in,
                                      cNumPedVta_in,
                                      V_NUM_PRODUCTOS,
                                      v_TipDocBenificiarioMF,
                                      cSecMovCaja_in,
                                      cCodNumera_in,
                                      COMP_BENEFICIARIO,
                                       v_Flg_Tipo_Convenio,
                                       vIndAfectaKardex,
                                       V_PCT_BENEFICIARIO,
                                       V_PCT_EMPRESA,
                                       nSecImpresora_TICKET,
                                       'S', -- CON IGV
                                       vIndGeneraUnSoloComproBenif
                                      );


        PTOVENTA_CONV_BTLMF.BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
                                      cCodLocal_in,
                                      cUsuCreaCompPago_in,
                                      cNumPedVta_in,
                                      V_NUM_PRODUCTOS,
                                      v_TipDocBenificiarioMF,
                                      cSecMovCaja_in,
                                      cCodNumera_in,
                                      COMP_BENEFICIARIO,
                                       v_Flg_Tipo_Convenio,
                                       vIndAfectaKardex,
                                       V_PCT_BENEFICIARIO,
                                       V_PCT_EMPRESA,
                                       nSecImpresora_TICKET,
                                       'N', -- SIN IGV
                                       vIndGeneraUnSoloComproBenif
                                      );

        --Actualizamos Kardex
        IF vIndAfectaKardex = 'S' THEN

            OPEN curDetallaPedido FOR
               SELECT COUNT(D.COD_PROD),
                      D.Num_Comp_Pago
                 FROM VTA_PEDIDO_VTA_DET D
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                  AND  COD_LOCAL = cCodLocal_in
                  AND  NUM_PED_VTA = cNumPedVta_in
               GROUP BY D.Num_Comp_Pago;

            LOOP

               FETCH curDetallaPedido
                INTO vNumeroProd, vNumCompPago;
               EXIT WHEN curDetallaPedido%NOTFOUND;

               IF vNumCompPago IS NOT NULL  THEN
           PTOVENTA_CONV_BTLMF.BTLMF_P_ACTUALIZA_STK_PROD(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in,
                                          cCodMotKardex_in,
                                          cTipDocKardex_in,
                                          cCodNumeraKardex_in,
                                          cUsuCreaCompPago_in,
                                          v_TipDocBenificiarioMF,
                                          vNumCompPago
                                         );
               END IF;


             END LOOP;
             close curDetallaPedido;
               --ACTUALIZA CABECERA PEDIDO
               UPDATE VTA_PEDIDO_VTA_CAB
                 SET USU_MOD_PED_VTA_CAB = cUsuCreaCompPago_in,
                     FEC_MOD_PED_VTA_CAB = SYSDATE,
                     SEC_MOV_CAJA = cSecMovCaja_in,
                      EST_PED_VTA = INDICADOR_SI, --PEDIDO COBRADO SIN COMPROBANTE IMPRESO
                     TIP_COMP_PAGO = v_TipDocBenificiarioMF,
                     FEC_PED_VTA = SYSDATE
               WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND COD_LOCAL = cCodLocal_in
                 AND NUM_PED_VTA = cNumPedVta_in;

          END IF;

 END IF;




           /************************************************************/
           /*******************    EMPRESA-CLIENTE      ****************/
           /************************************************************/



    vNumeroProd  := 0;
    vNumCompPago := ' ';

  ----########Grabamos el comprobante de pago de la Empresa/Cliente########----
  IF v_TipDocClienteMF IS NOT NULL THEN

       dbms_output.put_line('Grabamos el comprobante de pago del Empresa/Cliente');



         IF  v_TipDocBenificiarioMF IS NOT NULL THEN
            IF v_FlgKardex = '1' THEN
              vIndAfectaKardex := 'S';
            ELSE
              IF v_FlgKardex = '2' THEN
                vIndAfectaKardex := 'N';
              ELSE
                vIndAfectaKardex := 'S';
              END IF;
            END IF;
        ELSE
            vIndAfectaKardex := 'S';
        END IF;


     PTOVENTA_CONV_BTLMF.BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
                                      cCodLocal_in,
                                      cUsuCreaCompPago_in,
                                      cNumPedVta_in,
                                      V_NUM_PRODUCTOS,
                                      v_TipDocClienteMF,
                                      cSecMovCaja_in,
                                      cCodNumera_in,
                                      COMP_EMPRESA,
                                      v_Flg_Tipo_Convenio,
                                      vIndAfectaKardex,
                                      V_PCT_BENEFICIARIO,
                                      V_PCT_EMPRESA,
                                      nSecImpresora_TICKET,
                                      'S', --CON IGV
                                      vIndGeneraUnSoloComproBenif
                                     );


      PTOVENTA_CONV_BTLMF.BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
                                      cCodLocal_in,
                                      cUsuCreaCompPago_in,
                                      cNumPedVta_in,
                                      V_NUM_PRODUCTOS,
                                      v_TipDocClienteMF,
                                      cSecMovCaja_in,
                                      cCodNumera_in,
                                      COMP_EMPRESA,
                                      v_Flg_Tipo_Convenio,
                                      vIndAfectaKardex,
                                      V_PCT_BENEFICIARIO,
                                      V_PCT_EMPRESA,
                                      nSecImpresora_TICKET,
                                      'N', --SIN IGV
                                      vIndGeneraUnSoloComproBenif
                                     );


       --  Actualizamos Kardex EMPRESA - CLIENTE
           IF v_FlgKardex = '1' OR v_FlgKardex IS NULL  THEN

            OPEN curDetallaPedido FOR
               SELECT COUNT(D.COD_PROD),
                      D.Num_Comp_Pago
                 FROM VTA_PEDIDO_VTA_DET D
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                  AND  COD_LOCAL = cCodLocal_in
                  AND  NUM_PED_VTA = cNumPedVta_in
               GROUP BY D.Num_Comp_Pago;

            LOOP

               FETCH curDetallaPedido
               INTO vNumeroProd, vNumCompPago;
               EXIT WHEN curDetallaPedido%NOTFOUND;
               IF vNumCompPago IS NOT NULL THEN

         PTOVENTA_CONV_BTLMF.BTLMF_P_ACTUALIZA_STK_PROD(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in,
                                          cCodMotKardex_in,
                                          cTipDocKardex_in,
                                          cCodNumeraKardex_in,
                                          cUsuCreaCompPago_in,
                                          v_TipDocClienteMF,
                                          vNumCompPago
                                         );
               END IF;
             END LOOP;
             close curDetallaPedido;



            --ACTUALIZA CABECERA PEDIDO
          UPDATE VTA_PEDIDO_VTA_CAB
             SET USU_MOD_PED_VTA_CAB = cUsuCreaCompPago_in,
                 FEC_MOD_PED_VTA_CAB = SYSDATE,
                 SEC_MOV_CAJA = cSecMovCaja_in,
                  EST_PED_VTA = INDICADOR_SI, --PEDIDO COBRADO SIN COMPROBANTE IMPRESO
                 TIP_COMP_PAGO = v_TipDocClienteMF,
                 FEC_PED_VTA = SYSDATE
           WHERE COD_GRUPO_CIA = cCodGrupoCia_in
             AND COD_LOCAL = cCodLocal_in
             AND NUM_PED_VTA = cNumPedVta_in;

          END IF;


          --Actualiza Referencia de comprobantes

            OPEN  curCompPago FOR
                  SELECT C.TIP_COMP_PAGO,
                         C.NUM_COMP_PAGO,
                         C.NUM_COMP_COPAGO_REF
                  FROM VTA_COMP_PAGO C
                  WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND  C.COD_LOCAL = cCodLocal_in
                    AND  C.NUM_PED_VTA = cNumPedVta_in
                    AND C.TIP_CLIEN_CONVENIO = '2';

            LOOP

               FETCH curCompPago
               INTO v_TIP_COMP_PAGO, v_NUM_COMP_PAGO,v_NUM_COMP_PAGO_REF;
               EXIT WHEN curCompPago%NOTFOUND;

          --Actualiza Cabecera del Pedido
               UPDATE VTA_COMP_PAGO COMP
                  SET COMP.TIP_COMP_PAGO_REF = v_TIP_COMP_PAGO,
                      COMP.NUM_COMP_COPAGO_REF = v_NUM_COMP_PAGO
                WHERE COMP.NUM_COMP_PAGO = v_NUM_COMP_PAGO_REF
                  AND COMP.TIP_CLIEN_CONVENIO = '1';

             END LOOP;

  END IF;

/* ############################################################################### */
/* ############################   FIN     ######################################## */
/* ############################################################################### */

   /*** Graba el ajuste de pedice_hist_forma_pago_entregado ***/
           pkg_sol_stock.sp_aprueba_sol(cCodGrupoCia_in,
                               cCodLocal_in,
                               cNumPedVta_in);
   -----------------------------------------------------------


		--ERIOS 18.10.2013 Valida nuevo modelo de cobro
        IF PTOVENTA_GRAL.GET_IND_NUEVO_COBRO = 'S' THEN
          v_Resultado := 'EXITO';
        ELSE
          v_Resultado := PTOVENTA_CAJ.CAJ_F_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in);
        END IF;


    IF v_Resultado = 'ERROR' THEN
            --Atualizando el Indicador de Linea con el Local
            FARMA_GRAL.INV_ACTUALIZA_IND_LINEA(cUsuCreaCompPago_in,cCodLocal_in,cCodGrupoCia_in);
            --Obtenemos el Indicador Actualizado
            v_Indicador_Linea :=FARMA_GRAL.INV_OBTIENE_IND_LINEA(cCodLocal_in,cCodGrupoCia_in);
            IF v_Indicador_Linea = 'FALSE' THEN
              -- SI NO HAY LINEA TONCES SE PERMITIRA COBRAR MAS ALLA SI EL PARAMETRO DE COBRO ESTE O NO EN N
              v_Resultado := 'EXITO';
            END IF;
    END IF;

             --ERIOS 28/07/2008 Se graba el monto ahorrado en la ultimo comprobante
               UPDATE VTA_COMP_PAGO
               SET VAL_DCTO_COMP = (SELECT DECODE(X.IND_DELIV_AUTOMATICO,'S',0,'N',VAL_DCTO_PED_VTA)
                                    FROM VTA_PEDIDO_VTA_CAB X
                                    WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND X.COD_LOCAL = cCodLocal_in
                                         AND X.NUM_PED_VTA = cNumPedVta_in)
               WHERE (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO) =
                     (SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,MAX(SEC_COMP_PAGO)
                     FROM VTA_COMP_PAGO
                     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                           AND COD_LOCAL = cCodLocal_in
                           AND NUM_PED_VTA = cNumPedVta_in
                      GROUP BY COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA);

              --SE ACTUALIZA EL MONTO DE REDONDEO EN UN SOLO COMPROBANTE

              UPDATE VTA_COMP_PAGO
              SET VAL_REDONDEO_COMP_PAGO = v_nValRedondeo
              WHERE (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO) =
              (SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,MAX(SEC_COMP_PAGO)
              FROM VTA_COMP_PAGO
              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                    AND COD_LOCAL = cCodLocal_in
                    AND NUM_PED_VTA = cNumPedVta_in
                    AND VAL_NETO_COMP_PAGO<>0
              GROUP BY COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA);

    IF v_Resultado = 'EXITO' THEN
            --Inserta en la tabla de ahorro x DNI para validar el maximo Ahorro en el dia o Semana
            --dubilluz 28.05.2009
            select sum(NVL(D.AHORRO_CAMP,d.ahorro))
            into   vAhorroPedido
            from   vta_pedido_vta_det d
            where  d.cod_grupo_cia = cCodGrupoCia_in
            and    d.cod_local = cCodLocal_in
            and    d.num_ped_vta = cNumPedVta_in;

            if vAhorroPedido > 0 then
               insert into vta_ped_dcto_cli_aux
              (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,VAL_DCTO_VTA,DNI_CLIENTE,FEC_CREA_PED_VTA_CAB)
              select c.cod_grupo_cia,c.cod_local,c.num_ped_vta,sum(NVL(D.AHORRO_CAMP,d.ahorro)),t.dni_cli,c.fec_ped_vta
              from   vta_pedido_vta_det d,
                     vta_pedido_vta_cab c,
                     fid_tarjeta_pedido t,
                     -- DUBILLUZ 08.11.2012
                     VTA_CAMPANA_PROD_USO P
              where  c.cod_grupo_cia = cCodGrupoCia_in
              and    c.cod_local = cCodLocal_in
              and    c.num_ped_vta =  cNumPedVta_in
              and    c.cod_grupo_cia = d.cod_grupo_cia
              and    c.cod_local = d.cod_local
              and    c.num_ped_vta = d.num_ped_vta
              and    c.cod_grupo_cia = t.cod_grupo_cia
              and    c.cod_local = t.cod_local
              and    c.num_ped_vta = t.num_pedido
              --------------------------------------------
              -- DUBILLUZ 08.11.2012
              AND    D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
              AND    D.COD_CAMP_CUPON = P.COD_CAMP_CUPON
              AND    D.COD_PROD = P.COD_PROD
              AND    P.IND_EXCLUYE_ACUM_AHORRO = 'N'
              -- DUBILLUZ 08.11.2012
              ---------------------------------------------
              group by c.cod_grupo_cia,c.cod_local,c.num_ped_vta,t.dni_cli,c.fec_ped_vta;

            end if;


          UPDATE VTA_PEDIDO_VTA_CAB P
          SET P.NAME_PC_COB_PED = v_NOMBRE_PC,
             P.IP_COB_PED      = v_IP_PC,
             P.DNI_USU_LOCAL   = v_DNI_USU
         WHERE P.NUM_PED_VTA = cNumPedVta_in;

           --Obtenemos data comp pago temporalmente.
             OPEN  curCompPagoTemp FOR
                SELECT  COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_COMP_PAGO,TIP_COMP_PAGO,
                        NUM_COMP_PAGO,SEC_MOV_CAJA,SEC_MOV_CAJA_ANUL,CANT_ITEM,
                        COD_CLI_LOCAL,NOM_IMPR_COMP,DIREC_IMPR_COMP,NUM_DOC_IMPR,
                        VAL_BRUTO_COMP_PAGO,VAL_NETO_COMP_PAGO,VAL_DCTO_COMP_PAGO,VAL_AFECTO_COMP_PAGO,
                        VAL_IGV_COMP_PAGO,VAL_REDONDEO_COMP_PAGO,PORC_IGV_COMP_PAGO,
                        USU_CREA_COMP_PAGO,FEC_CREA_COMP_PAGO,USU_MOD_COMP_PAGO,FEC_MOD_COMP_PAGO,
                        FEC_ANUL_COMP_PAGO,IND_COMP_ANUL,NUM_PEDIDO_ANUL,NUM_SEC_DOC_SAP,
                        FEC_PROCESO_SAP,NUM_SEC_DOC_SAP_ANUL,FEC_PROCESO_SAP_ANUL,IND_RECLAMO_NAVSAT,
                        VAL_DCTO_COMP,MOTIVO_ANULACION,FECHA_COBRO,FECHA_ANULACION,
                        FECH_IMP_COBRO,FECH_IMP_ANUL,TIP_CLIEN_CONVENIO,VAL_COPAGO_COMP_PAGO,
                        VAL_IGV_COMP_COPAGO,NUM_COMP_COPAGO_REF,IND_AFECTA_KARDEX,
                        PCT_BENEFICIARIO,PCT_EMPRESA,C.IND_COMP_CREDITO,C.TIP_COMP_PAGO_REF
                  FROM  VTA_COMP_PAGO C
                 WHERE  C.COD_GRUPO_CIA =  cCodGrupoCia_in
                   AND  C.COD_LOCAL =  cCodLocal_in
             AND  C.NUM_PED_VTA =  cNumPedVta_in;

          OPEN  curFormPagoTemp FOR
           SELECT COD_GRUPO_CIA,COD_LOCAL,COD_FORMA_PAGO,NUM_PED_VTA,IM_PAGO,TIP_MONEDA,
                    VAL_TIP_CAMBIO,VAL_VUELTO,IM_TOTAL_PAGO,NUM_TARJ,FEC_VENC_TARJ,
                    NOM_TARJ,FEC_CREA_FORMA_PAGO_PED,USU_CREA_FORMA_PAGO_PED,
                    FEC_MOD_FORMA_PAGO_PED,USU_MOD_FORMA_PAGO_PED,CANT_CUPON,
                    TIPO_AUTORIZACION,COD_LOTE,COD_AUTORIZACION,DNI_CLI_TARJ
               FROM VTA_FORMA_PAGO_PEDIDO C
              WHERE C.COD_GRUPO_CIA =  cCodGrupoCia_in
                AND C.COD_LOCAL =  cCodLocal_in
                AND C.NUM_PED_VTA =  cNumPedVta_in;

          OPEN curPedidoDetTemp FOR
                SELECT
                   cod_grupo_cia,cod_local,num_ped_vta,sec_ped_vta_det,cod_prod,cant_atendida,
                   val_prec_vta,val_prec_total,porc_dcto_1,porc_dcto_2,porc_dcto_3,porc_dcto_total,
                   est_ped_vta_det,val_total_bono,val_frac,sec_comp_pago,sec_usu_local,usu_crea_ped_vta_det,
                   fec_crea_ped_vta_det,usu_mod_ped_vta_det,fec_mod_ped_vta_det,val_prec_lista,val_igv,
                   unid_vta,ind_exonerado_igv,sec_grupo_impr,cant_usada_nc,sec_comp_pago_origen,num_lote_prod,
                   fec_proceso_guia_rd,desc_num_tel_rec,val_num_trace,val_cod_aprobacion,desc_num_tarj_virtual,
                   val_num_pin,fec_vencimiento_lote,val_prec_public,ind_calculo_max_min,fec_exclusion,
                   fecha_tx,hora_tx,cod_prom,ind_origen_prod,val_frac_local,cant_frac_local,cant_xdia_tra,
                   cant_dias_tra,ind_zan,val_prec_prom,datos_imp_virtual,cod_camp_cupon,ahorro,
                   porc_dcto_calc,porc_zan,ind_prom_automatico,ahorro_pack,porc_dcto_calc_pack,
                   cod_grupo_rep,cod_grupo_rep_edmundo,sec_respaldo_stk,num_comp_pago,sec_comp_pago_benef,
                   sec_comp_pago_empre
                FROM ptoventa.VTA_PEDIDO_VTA_DET D
                 WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND D.COD_LOCAL = cCodLocal_in
                   AND D.NUM_PED_VTA = cNumPedVta_in;


                   -- dubilluz 26.09.2013
                   ptoventa_conv_btlmf.btlmf_p_aux_precion_det_conv(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);



            -- GRABAMOS TABLAS TEMPORALES --
               v_Resultado := PTOVENTA_CONV_BTLMF.BTLMF_F_GRABA_DATOS_TMP(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,curCompPagoTemp,curFormPagoTemp,curPedidoDetTemp);
           IF  v_Resultado  = 'S' THEN
               v_Resultado := 'EXITO';
           ELSE
               v_Resultado := 'ERROR';
           END IF;
     END IF;


  RETURN v_Resultado;

 END;


FUNCTION BTLMF_F_LINEA_COMPROBANTE
  (   V_NUM_LINEA_B         IN NUMBER,
      V_NUM_LINEA_E       IN NUMBER,
      v_TipDocBenificiarioMF  IN NUMBER,
      v_TipDocClienteMF       IN NUMBER
  )
      RETURN NUMBER
  AS
  V_NUM_LINEA NUMBER(2);

BEGIN

IF ( V_NUM_LINEA_B IS NOT NULL and
     V_NUM_LINEA_E IS NOT NULL ) THEN


     IF (v_TipDocBenificiarioMF = 05 AND
         v_TipDocClienteMF <> 05 ) THEN

         V_NUM_LINEA:=V_NUM_LINEA_E;

     ELSE
         IF (v_TipDocBenificiarioMF <> 05 AND
             v_TipDocClienteMF = 05 ) THEN

             V_NUM_LINEA:=V_NUM_LINEA_B;

         ELSE
           IF (v_TipDocBenificiarioMF = 05 AND
               v_TipDocClienteMF = 05 ) THEN

               V_NUM_LINEA:=0;
           ELSE
               IF V_NUM_LINEA_B < V_NUM_LINEA_E THEN
                  V_NUM_LINEA:=V_NUM_LINEA_B;
               ELSE
                  V_NUM_LINEA:=V_NUM_LINEA_E;
               END IF;
           END IF;

         END IF;

      END IF;

ELSE
  IF ( V_NUM_LINEA_B IS NOT NULL AND
       V_NUM_LINEA_E IS NULL) THEN

       V_NUM_LINEA:=V_NUM_LINEA_B;

ELSE
       IF ( V_NUM_LINEA_B IS NULL AND
       V_NUM_LINEA_E IS NOT NULL ) THEN

       V_NUM_LINEA:=V_NUM_LINEA_E;

       END IF;

  END IF;
END IF;


RETURN V_NUM_LINEA;
END;



END PTOVENTA_CAJ_COBRO;
/
