--------------------------------------------------------
--  DDL for Package PTOVENTA_CAJ_COBRO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CAJ_COBRO" IS

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
