CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CONV_BTLMF" is

  -- Author  : FRAMIREZ
  -- Created : 02/08/2012 09:46:38 a.m.
  -- Purpose :

PANTALLA_ABA_PIXEL_ALTO  VARCHAR2(30) := 'PANT_ABA_PIX_ALTO';
  PANTALLA_ABA_PIXEL_ANCHO VARCHAR2(30) := 'PANT_ABA_PIX_ANCHO';
  PANTALLA_DER_PIXEL_ALTO  VARCHAR2(30) := 'PANT_DER_PIX_ALTO';
  PANTALLA_DER_PIXEL_ANCHO VARCHAR2(30) := 'PANT_DER_PIX_ANCHO';
  PANTALLA_IZQ_PIXEL_ALTO  VARCHAR2(30) := 'PANT_IZQ_PIX_ALTO';
  PANTALLA_IZQ_PIXEL_ANCHO VARCHAR2(30) := 'PANT_IZQ_PIX_ANCHO';

  COD_DATO_CONV_REPARTIDOR  VARCHAR2(10) := 'D_001';
  COD_DATO_CONV_MEDICO      VARCHAR2(10) := 'D_005';
  COD_DATO_CONV_ORIG_RECETA VARCHAR2(10) := 'D_002';

  COD_DATO_CONV_DIAGNOSTICO_UIE VARCHAR2(10) := 'D_004';
  COD_DATO_CONV_BENIFICIARIO    VARCHAR2(10) := 'D_000';

  OBJ_IN_TEXTO       VARCHAR2(30) := 'INGRESO_TEXTO';
  OBJ_LISTA_PANTALLA VARCHAR2(30) := 'LISTA_PANTALLA';
  OBJ_LISTA_COMBO    VARCHAR2(30) := 'LISTA_COMBO';

  CONS_NRO_RESOLUCION VARCHAR2(20) := '800x600';

    --INI ASOSA - 17/10/2014 - PANHD
  TIPO_PROD_FINAL CONSTANT CHAR(1) := 'F';
  	ALTA_VTA_PROD CONSTANT CHAR(3) := '113';
  --FIN ASOSA - 17/10/2014 - PANHD

  FLG_RETENCION CHAR(1) := '1';

  ---VARIABLES JQUISPE 23.12.2011
  C_ESTADO_ACTIVO CHAR(1) := 'A';

  TYPE TYP_ARR_VARCHAR is table of VARCHAR2(100) index by PLS_INTEGER;

  -----DECLARANDO UN ARRAY VACIO
  W_ARRAY_VACIO TYP_ARR_VARCHAR;


  -------------------------------------------------------------------------------------------
  /*************************CONSTANTES PARA LOS TIPOS DE DOCUMENTOS*************************/
  --CONS_TIP_DOC_NOTA              CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'NCR'   ;
  --CONS_TIP_DOC_BOL               CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'BOL'   ;
  --CONS_TIP_DOC_FAC               CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'FAC'   ;
  --CONS_TIP_DOC_DEBITO            CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'NDB'   ;
  ----------------
  -- TICKET(S) FAC/BOL --
  --CONS_TIP_DOC_TKT_BOL           CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'TKB'   ;
  --CONS_TIP_DOC_TKT_FAC           CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'TKF'   ;
  ----------------
  --CONS_TIP_DOC_BOL_ANT           CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := '0'   ;
  --CONS_TIP_DOC_FAC_ANT           CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := '1'   ;
  --       CONS_TIPO_DOC_REFER            CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := '1'   ;
  ----------------
  --CONS_TIP_DOC_GUIA_LOC          CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'GRL'   ;
  --CONS_TIP_DOC_PROF              CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'PRO'   ;
  --CONS_TIP_DOC_TRAN              CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'TRA'   ;
  --CONS_TIP_DOC_ESPVAL            CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'EVA'   ;
  --CONS_TIP_DOC_RECIBO            CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'REC'   ;
  --CONS_TIP_DOC_AJUSTE            CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'AJU'   ;
  --CONS_TIP_DOC_VOUCHER           CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'VOU'   ;
  --CONS_TIP_DOC_ORDEN_DEV         CMR.MAE_TIPO_DOCUMENTO.COD_TIPODOC%TYPE         := 'ODE'   ;

  /**********************CONSTANTES PARA LOS TIPOS DE COMPROBANTES DE PAGO*******************/

  COD_TIP_COMP_BOLETA    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '01';
  COD_TIP_COMP_FACTURA   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '02';
  COD_TIP_COMP_GUIA      VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '03';
  COD_TIP_COMP_NOTA_CRED VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '04';
  COD_TIP_COMP_TICKET    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '05';
  COD_TIP_COMP_TICKET_FA VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '06';

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


  /**********INDICADORES****************/
  INDICADOR_SI        CHAR(1) := 'S';
  INDICADOR_NO        CHAR(1) := 'N';
  POS_INICIO          CHAR(1) := 'I';
  TIPO_COMPROBANTE_99 CHAR(2) := '99';
  EST_PED_COBRADO     VTA_PEDIDO_VTA_CAB.EST_PED_VTA%TYPE := 'C';

  CONV_COMPETENCIA MAE_CONVENIO.COD_CONVENIO%TYPE := '0000000834';
  CONV_VENTA_EMPRESA MAE_CONVENIO.COD_CONVENIO%TYPE := '0000001131';

  TYPE FarmaCursor IS REF CURSOR;

  -- KMONCADA INDICADORES PARA FACTURACION ELECTRONICA
  FAC_ELEC_ACTIVO                    CHAR(1) := '1';
  FAC_ELEC_INACTIVO                  CHAR(1) := '0';
  COD_CANT_LINEAS_FE                 PBL_TAB_GRAL.ID_TAB_GRAL%TYPE := 617;

  --Descripcion: Lista de convenios BTL Y MF
  --Fecha        Usuario         Comentario
  --26/10/2011   Fredy Ramirez   Creación
  FUNCTION BTLMF_F_CUR_LISTA_CONVENIOS(cCodGrupoCia_in CHAR,
                                       cCodLocal_in    CHAR,
                                       cSecUsuLocal_in CHAR)
    RETURN FarmaCursor;

  --Descripcion: Obtiene un indicador si existe datos del convenio
  --Fecha        Usuario        Comentario
  --19/10/2011   Fredy Ramirez  Creación
  FUNCTION BTLMF_F_CHAR_PIDE_DATO_CONV(vCodGrupoCia_in IN VARCHAR2,
                                       vCodLocal_in    IN VARCHAR2,
                                       cSecUsuLocal_in IN VARCHAR2,
                                       vCodConvenio_in IN VARCHAR2

                                       ) RETURN CHAR;

  --Descripcion: Lista  Medicos,Repartidor y Origen de Receta
  --Fecha        Usuario        Comentario
  --19/10/2011   Fredy Ramirez  Creación
  FUNCTION BTLMF_F_CUR_LISTA_DATO(cCodGrupoCia_in  CHAR,
                                  cCodLocal_in     CHAR,
                                  cSecUsuLocal_in  CHAR,
                                  vCodConvenio_in  CHAR,
                                  vCodTipoCampo_in VARCHAR2,
                                  vDescripcion_in  VARCHAR2)
    RETURN FarmaCursor;

  --Descripcion: Cantidad Lista Beneficiario
  --Fecha        Usuario        Comentario
  --30/04/2014   ERIOS          Creacion
  FUNCTION GET_CANT_LISTA_BENEFICIARIO(CCODGRUPOCIA_IN  CHAR,
                                            CCODLOCAL_IN     CHAR,
                                            CSECUSULOCAL_IN  CHAR,
                                            VCODCONVENIO_IN  CHAR,
                                            VBENIFICIARIO_IN VARCHAR2)
	RETURN INTEGER;

  --Descripcion: Lista Benificiario
  --Fecha        Usuario        Comentario
  --19/10/2011   Fredy Ramirez  Creación
  FUNCTION BTLMF_F_CUR_LISTA_BENIFICIARIO(cCodGrupoCia_in  CHAR,
                                          cCodLocal_in     CHAR,
                                          cSecUsuLocal_in  CHAR,
                                          vCodConvenio_in  CHAR,
                                          vBenificiario_in VARCHAR2

                                          )

   RETURN FarmaCursor;

  FUNCTION BTLMF_F_CUR_OBT_BENIFICIARIO(cCodGrupoCia_in  CHAR,
                                          cCodLocal_in     CHAR,
                                          cSecUsuLocal_in  CHAR,
                                          vCodConvenio_in  CHAR,
                                          vCodBenif_in CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista repartidor de un convenio
  --Fecha        Usuario        Comentario
  --19/10/2011   Fredy Ramirez  Creación
  FUNCTION BTLMF_F_CUR_LISTA_REPARTIDOR(cCodGrupoCia_in CHAR,
                                        cCodLocal_in    CHAR,
                                        cSecUsuLocal_in CHAR,
                                        vCodConvenio_in CHAR,
                                        vClaseObjeto_in VARCHAR2)

   RETURN FarmaCursor;

  --Descripcion: Lista medico par un convenio
  --Fecha        Usuario        Comentario
  --19/10/2011   Fredy Ramirez  Creación
  FUNCTION BTLMF_F_CUR_LISTA_MEDICO(cCodGrupoCia_in CHAR,
                                    cCodLocal_in    CHAR,
                                    cSecUsuLocal_in CHAR,
                                    vCodConvenio_in CHAR,
                                    vClaseObjeto_in VARCHAR2)

   RETURN FarmaCursor;

  FUNCTION BTLMF_F_CUR_OBTIENE_MEDICO(cCodGrupoCia_in CHAR,
                                      cCodLocal_in    CHAR,
                                      cSecUsuLocal_in CHAR,
                                      vCodConvenio_in CHAR,
                                      vCodMedico_in   VARCHAR2)
    RETURN FarmaCursor;

  --Descripcion: Lista clinicas para un convenio
  --Fecha        Usuario        Comentario
  --19/10/2011   Fredy Ramirez  Creación
  FUNCTION BTLMF_F_CUR_LISTA_CLINICA(cCodGrupoCia_in CHAR,
                                     cCodLocal_in    CHAR,
                                     cSecUsuLocal_in CHAR,
                                     vCodConvenio_in CHAR,
                                     vClaseObjeto_in VARCHAR2)

   RETURN FarmaCursor;

  --Descripcion: Lista de convenios BTL Y MF
  --Fecha        Usuario         Comentario
  --26/10/2011   Fredy Ramirez   Creación
  FUNCTION BTLMF_F_CUR_LISTA_DIAGNOSTICO(cCodGrupoCia_in    CHAR,
                                         cCodLocal_in       CHAR,
                                         cSecUsuLocal_in    CHAR,
                                         vDesDiagnostico_in VARCHAR2)

   RETURN FarmaCursor;

  FUNCTION BTLMF_F_CUR_OBT_DIAGNOSTICO(cCodGrupoCia_in CHAR,
                                       cCodLocal_in    CHAR,
                                       cSecUsuLocal_in CHAR,
                                       vCodCIE10_in    VARCHAR2)

   RETURN FarmaCursor;

  --Descripcion: Lista datos adicionales
  --Fecha        Usuario        Comentario
  --19/10/2011   Fredy Ramirez  Creación
  FUNCTION BTLMF_F_CUR_LISTA_DATO_ADIC(cCodGrupoCia_in CHAR,
                                       cCodLocal_in    CHAR,
                                       cSecUsuLocal_in CHAR,
                                       vCodConvenio_in CHAR)

   RETURN FarmaCursor;

  FUNCTION BTLMF_F_CUR_LIST_DAT_ADIC_DET(vCodTipoCampo CHAR)
    RETURN FarmaCursor;

  --Descripcion: OBTIENE DOCUMENTOS DE VERIFICACION O RETENCION
  --Fecha        Usuario         Comentario
  --26/10/2011   Fredy Ramirez   Creación
  FUNCTION BTLMF_F_CHAR_OBTIENE_DOC_VERIF(vCodGrupoCia_in   IN CHAR,
                                          vCodLocal_in      IN CHAR,
                                          cSecUsuLocal_in   IN CHAR,
                                          vCodConvenio_in   IN VARCHAR2,
                                          vFlg_retencion_in IN CHAR,
                                          vNombreBenif_in   IN VARCHAR2)
    RETURN VARCHAR2;

  --Descripcion: Lista de documentos de verificacion o retencion
  --Fecha        Usuario         Comentario
  --26/10/2011   Fredy Ramirez   Creación
  FUNCTION BTLMF_F_CHAR_OBTIENE_DATO(vCodGrupoCia_in   IN CHAR,
                                     vCodLocal_in      IN CHAR,
                                     cSecUsuLocal_in   IN CHAR,
                                     vCodConvenio_in   IN VARCHAR2,
                                     vFlg_retencion_in IN CHAR)
    RETURN FarmaCursor;

  --Descripcion: Lista de datos de convenios
  --Fecha        Usuario         Comentario
  --26/10/2011   Fredy Ramirez   Creación
  FUNCTION BTLMF_F_CUR_LISTA_DATOS_CONV(cCodGrupoCia_in CHAR,
                                        cCodLocal_in    CHAR,
                                        cSecUsuLocal_in CHAR,
                                        cCodConvenio_in CHAR)
    RETURN FarmaCursor;

  --Descripcion: Cambia a mayuscula la primera letra de todas las palabras encadenados.
  --Fecha        Usuario         Comentario
  --26/10/2011   Fredy Ramirez   Creación
  FUNCTION BTLMF_F_CHAR_INI_CAP(vCadena IN VARCHAR2) RETURN varchar2;

  FUNCTION BTLMF_F_CUR_LISTA_PANTALLA_MSG(cCodGrupoCia_in   CHAR,
                                          cCodLocal_in      CHAR,
                                          cSecUsuLocal_in   CHAR,
                                          vNroResolucion_in VARCHAR2,
                                          vPosicion_in      CHAR)
    RETURN FarmaCursor;

  PROCEDURE BTLMF_P_INSERT_BENIFICIARIO(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in      IN CHAR,
                                        cSecUsuLocal_in   IN CHAR,
                                        pCod_convenio     IN VARCHAR2,
                                        pNum_documento_id IN VARCHAR2,
                                        pDes_nom_cliente  IN VARCHAR2,
                                        pDes_ape_cliente  IN VARCHAR2,
                                        pDes_email        IN VARCHAR2,
                                        pFch_nacimiento   IN VARCHAR2,
                                        PTelefono         IN VARCHAR2,
                                        pFlg_creacion     IN CHAR,
                                        PCodCliente       IN CHAR DEFAULT NULL);

  FUNCTION BTLMF_F_CUR_OBTIENE_BENIF(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cSecUsuLocal_in   IN CHAR,
                                     pCod_convenio     IN CHAR,
                                     pNum_documento_id IN VARCHAR2)
    RETURN FarmaCursor;

      FUNCTION BTLMF_F_EXISTE_BENIF(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cSecUsuLocal_in   IN CHAR,
                                     pCod_convenio     IN CHAR,
                                     pNum_documento_id IN VARCHAR2)
    RETURN CHAR;

  FUNCTION BTLMF_F_CUR_OBT_TARJ(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cSecUsuLocal_in IN CHAR,
                                pCodBarra       IN VARCHAR2)
    RETURN FarmaCursor;

  FUNCTION BTLMF_F_CUR_OBT_CLIENTE(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecUsuLocal_in IN CHAR,
                                   cCodConvenio    IN CHAR,
                                   pCodCliente     IN VARCHAR2)
    RETURN FarmaCursor;

  FUNCTION BTLMF_F_CUR_OBT_CONVENIO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecUsuLocal_in IN CHAR,
                                    pCodConvenio    IN CHAR)
    RETURN FarmaCursor;

  FUNCTION BTLMF_F_CHAR_IMPRIMIR(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cIpServ_in      IN CHAR,
                                 cCodConvenio_in IN CHAR,
                                 cDni            IN VARCHAR2) RETURN VARCHAR2;

  FUNCTION BTLMF_F_CHAR_IMPR_VOUCHER(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecUsuLocal_in IN CHAR,
                                    cIpServ_in      IN CHAR,
                                    cNroPedido_in   IN CHAR,
                                    vCodigoBarra_in IN VARCHAR2
                                    )
    RETURN FarmaCursor;

  PROCEDURE BTLMF_P_INSERT_PED_VTA(cCodGrupoCia_in      IN CHAR,
                                   cCodLocal_in         IN CHAR,
                                   pNumPedVta_in        IN CHAR,
                                   PCodCampo_in         IN CHAR,
                                   pCodConvenio_in      IN CHAR,
                                   pCodCliente_in       IN CHAR,
                                   pUsCreaPedVtaCli_in  IN VARCHAR2,
                                   PDescripcionCampo_in IN VARCHAR2,
                                   PNombreCampo_in      IN VARCHAR2,
                                   pCodVaor_In          IN CHAR
                                   );

  FUNCTION BTLMF_F_CUR_LISTA_PED_VTA(vCodGrupoCia_in IN CHAR,
                                     vCodLocal_in    IN CHAR,
                                     vNroPedVta_in   IN CHAR)
    RETURN FarmaCursor;

  FUNCTION BTLMF_F_OBTIENE_NVO_PRECIO(cCodGrupoCia_in CHAR,
                                      cCodLocal_in    CHAR,
                                      cCodConv_in     CHAR,
                                      cCodProd_in     CHAR,
                                      nValPrecVta_in  NUMBER DEFAULT NULL)
    RETURN CHAR;

  FUNCTION F_GET_IND_EXCLUIDO_CONV(cCodGrupoCia_in in char,
                                   cCodConvenio_in in char,
                                   cCodProd_in     in char) RETURN CHAR;

  FUNCTION BTLMF_F_CHAR_ES_ACTIVO_CONV(vCodigo_in IN varchar2) RETURN CHAR;

  FUNCTION BTLMF_F_GET_COPAGO_CONV(cCodConvenio_in IN CHAR) RETURN CHAR;

  FUNCTION BTLMF_F_CUR_LIST_FORM_PAG_CONV(cCodGrupoCia_in CHAR,
                                          cCodLocal_in    CHAR,
                                          cSecUsuLocal_in CHAR,
                                          vCodConvenio_in CHAR)
    RETURN FarmaCursor;

  FUNCTION BTLMF_F_CHAR_OBT_PRECIO_CONV( cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cCodConvenio_in IN CHAR,
                                         cCodProducto_in IN CHAR )
  RETURN char;

  --Descripcion : Retorna la lista de precio por convenios
  --Fecha         Usuario    Comentario
  --16.01.2012    Jquispe    Creacion

  FUNCTION BTLMF_F_CUR_LISTA_PRECIO_CONV(vCodGrupoCia_in in char,
                                         vCodConvenio_in in char,
                                         vCodLocal_in   in char)
    RETURN FarmaCursor;

FUNCTION BTLMF_F_CHAR_COBRA_PEDIDO(
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
                                   cDescDetalleForPago_in IN CHAR DEFAULT ' ',
                                   cFlagElectronico in CHAR DEFAULT '0'--LTAVARA 27.08.14 10:00 FLAG PROCESO ELECTRONICO
                                   )

RETURN CHAR ;

  FUNCTION BTLMF_F_CUR_OBT_CONV_PEDIDO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecUsuLocal_in IN CHAR,
                                      pNroPedido      IN VARCHAR2)
    RETURN FarmaCursor;

  FUNCTION BTLMF_F_CHAR_OBT_COD_BARRA_CON(vCodLocal IN CHAR,
                                          nTamanio_in IN INTEGER DEFAULT 18) RETURN VARCHAR2;

  PROCEDURE BTLMF_P_INSERT_COMP_PAGO(cCodGrupoCia_in         IN CHAR,
                                     cCodLocal_in            IN CHAR,
                                     pNUM_PED_VTA            VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                     pSEC_COMP_PAGO          VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE,
                                     pTIP_COMP_PAGO          VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE,
                                     pNUM_COMP_PAGO          VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE,
                                     pSEC_MOV_CAJA           VTA_COMP_PAGO.SEC_MOV_CAJA%TYPE,
                                     pCANT_ITEM              VTA_COMP_PAGO.CANT_ITEM%TYPE,
                                     pCOD_CLI_LOCAL          VTA_COMP_PAGO.COD_CLI_LOCAL%TYPE,
                                     pNOM_IMPR_COMP          VTA_COMP_PAGO.NOM_IMPR_COMP%TYPE,
                                     pDIREC_IMPR_COMP        VTA_COMP_PAGO.DIREC_IMPR_COMP%TYPE,
                                     pNUM_DOC_IMPR           VTA_COMP_PAGO.NUM_DOC_IMPR%TYPE,
                                     PVAL_BRUTO_COMP_PAGO    CHAR,
                                     pVAL_NETO_COMP_PAGO     CHAR,
                                     pVAL_DCTO_COMP_PAGO     CHAR,
                                     pVAL_AFECTO_COMP_PAGO   CHAR,
                                     pVAL_IGV_COMP_PAGO      CHAR,
                                     pVAL_REDONDEO_COMP_PAGO VTA_COMP_PAGO.VAL_REDONDEO_COMP_PAGO%TYPE,
                                     pUSU_CREA_COMP_PAGO     VTA_COMP_PAGO.USU_CREA_COMP_PAGO%TYPE,
                                     pPORC_IGV_COMP_PAGO     CHAR,
                                     PTipClienteConv         VTA_COMP_PAGO.Tip_Clien_Convenio%TYPE,
                                     pVAL_COPAGO_COMP_PAGO  VTA_COMP_PAGO.VAL_COPAGO_COMP_PAGO%TYPE,
                                     PVAL_NUM_COMP_COPAGO   VTA_COMP_PAGO.NUM_COMP_COPAGO_REF%TYPE,
                                     pVAL_IGV_COMP_COPAGO   CHAR,
                                     PIND_AFECTA_KARDEX CHAR,
                                     PPCT_BENEFICIARIO VTA_COMP_PAGO.PCT_BENEFICIARIO%TYPE,
                                     PPCT_EMPRESA VTA_COMP_PAGO.PCT_EMPRESA%TYPE,
                                     PIND_COMP_CREDITO  VTA_COMP_PAGO.IND_COMP_CREDITO%TYPE,
                                     pTIP_COMP_PAGO_REF VTA_COMP_PAGO.TIP_COMP_PAGO_REF%TYPE,
                                     pCOD_TIPO_CONVENIO VTA_COMP_PAGO.COD_TIPO_CONVENIO%TYPE,
                                     PIND_AFECTO_IGV    VTA_COMP_PAGO.IND_AFECTO_IGV%TYPE,
                                     -- KMONCADA 22.04.2016 PERCEPCION
                                     pValMontoPercepcion_in IN VTA_COMP_PAGO.VAL_PERCEP_COMP_PAGO%TYPE,
                                     pPctPercepcion_in IN VTA_COMP_PAGO.VAL_PCT_PERCEPCION%TYPE,
                                     pValRedondeoPercepcion_in IN VTA_COMP_PAGO.VAL_REDONDEO_PERCEPCION%TYPE

                                     );
  PROCEDURE BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in             IN CHAR,
                                           cCodLocal_in                IN CHAR,
                                           cUsuCreaCompPago_in         IN CHAR,
                                           cNumPedVta_in               VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                                           V_NUM_PRODUCTOS             MAE_COMP_CONV_LINEAS.NUM_PRODUCTOS%TYPE,
                                           pTIP_COMP_PAGO              VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE,
                                           pSEC_MOV_CAJA               VTA_COMP_PAGO.SEC_MOV_CAJA%TYPE,
                                           cCodNumera_in               IN CHAR,
                                           pTipoClienteConvenio        IN CHAR,
                                           pFlgTipoConvenio            IN MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE,
                                           pIndAfectaKardex            IN CHAR,
                                           vPCT_BENEFICIARIO           VTA_COMP_PAGO.PCT_BENEFICIARIO%TYPE,
                                           vPCT_EMPRESA                VTA_COMP_PAGO.PCT_EMPRESA%TYPE,
                                           vSecImprLocal_in            vta_impr_local.sec_impr_local%type,
                                           vIND_CON_IGV_in             char,
                                           vIndGeneraUnSoloComproBenif CHAR,
                                           cFlagElectronico            in CHAR DEFAULT '0',
                                           -- KMONCADA 22.04.2016 PERCEPCION
                                           cAplicaPercepcion_in        IN CHAR DEFAULT 'N',
                                           vContieneProdPercep_in      IN CHAR DEFAULT 'N');

  PROCEDURE BTLMF_P_ACT_ESTDO_PEDIDO( cCodGrupoCia_in 	IN CHAR,
  		   						   	   	          cCodLocal_in    	IN CHAR,
										                  cNumPedVta_in   	IN CHAR
                                     );
  FUNCTION BTLMF_OBT_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cSecImprLocal_in IN number,
                                        cTipComp         IN CHAR)
    RETURN VARCHAR2;

  --Descripcion : Retorna la lista de precio por convenios
  --Fecha         Usuario    Comentario
  --26.09.2014    ERIOS      Se agrega parametro vSecImprLocal_in
  PROCEDURE BTLMF_P_GENERA_IMPR_NUM_COMP(cCodGrupoCia_in     IN CHAR,
                                         cCodLocal_in        IN CHAR,
                                         cUsuModImprLocal_in IN CHAR,
                                         cTipComp            IN CHAR,
										 vSecImprLocal_in vta_impr_local.sec_impr_local%type);

  PROCEDURE BTLMF_P_ACTUALIZA_STK_PROD(cCodGrupoCia_in     IN CHAR,
                                       cCodLocal_in        IN CHAR,
                                       cNumPedVta_in       IN CHAR,
                                       cCodMotKardex_in    IN CHAR,
                                       cTipDocKardex_in    IN CHAR,
                                       cCodNumeraKardex_in IN CHAR,
                                       cUsuModProdLocal_in IN CHAR,
                                       cTipComp_in         IN CHAR,
                                       cNumComp_in         IN CHAR);


 FUNCTION BTLMF_FLOAT_OBT_MONTO_CREDITO(
                                        cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cMontoNeto_in    IN FLOAT,
                                        cNroPedido_in    IN CHAR,
                                        cCodConvenio     IN CHAR
                                       )
 RETURN FLOAT;

 FUNCTION BTLMF_FLOAT_OBT_MTO_CRED_COMP(
                                        cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in      IN CHAR,
                                        cMontoNeto_in     IN FLOAT,
                                        cSubMontoNeto_in  IN FLOAT,
                                        cNroPedido_in     IN CHAR,
                                        cPorcBeneficiario IN CHAR DEFAULT NULL
                                       )
  RETURN FLOAT;

 FUNCTION BTLMF_F_CHAR_OBT_FORM_PAGO(cCodGrupoCia_in CHAR,
                                            cCodLocal_in CHAR,
                                            cSecUsuLocal_in CHAR,
                                            cCodFormaPago CHAR)

 RETURN CHAR;

 FUNCTION BTLMF_F_CUR_LISTA_COMP_PAGO(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         pNroPedido       IN VARCHAR2,
                                         PTipoClienteConv IN CHAR
                                         )

 RETURN FarmaCursor;

 FUNCTION BTLMF_F_CUR_LIST_DET_COMP_PAGO(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                             pNroPedido       IN VARCHAR2,
                                             PSecCompPago     IN CHAR,
                                             pTipCompPago     IN CHAR,
                                             pTipClienteConv  IN CHAR
                                         )
 RETURN FarmaCursor;

 FUNCTION CAJ_OBTIENE_RUTA_IMPRESORA(cCodGrupoCia_in  IN CHAR,
  		   						   	              cCodLocal_in     IN CHAR,
									                    cTipCompPago_in IN CHAR)

 RETURN CHAR;

 FUNCTION BTLMF_F_CHAR_OBT_DNI_USUARIO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecUsuLocal_in IN CHAR
                                     )
 RETURN CHAR;

   PROCEDURE BTLMF_P_ACT_FECHA_PROC_RAC(CodGrupoCia_in IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNroPedido     IN CHAR
                                      );
   PROCEDURE BTLMF_P_AC_FEC_PROC_ANU_NC_RAC(CodGrupoCia_in IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNroPedido     IN CHAR
                                      );
/*   FUNCTION CON_AGREGA_DATOS_TMP(cCodGrupoCia_in CHAR,
                              cCodLocal_in    CHAR,
                              cNumPedVta_in   CHAR)
   RETURN CHAR;*/

   FUNCTION BTLMF_F_CHAR_ES_DIA_VIG_RECTA(fechaReceta_in IN varchar2)
   RETURN CHAR;

   --Descripcion:  Corta una cadena de palabras.
   --Fecha          Usuario        Comentario.
   --28/03/2012     Fredy Ramirez   Creación.
   FUNCTION BTLMF_F_EXISTE_PROD_CONV(cCodLocal_in IN CHAR,vCodConvenio CHAR)
   RETURN CHAR;

   FUNCTION BTLMF_F_CHAR_OBT_TIP_CONVENIO(cCodConvenio IN CHAR)
   RETURN CHAR;




  --Descripcion:  Corta una cadena de palabras.
  --Fecha         Usuario        Comentario.
  --26/10/2011    Fredy Ramirez   Creación.
  FUNCTION split(input_list   varchar2,
                 ret_this_one number,
                 delimiter    varchar2) RETURN varchar2;

  FUNCTION BTLMF_F_VARCHAR_MSG_COMP(cCodGrupoCia_in varchar2,cCodConvenio varchar2,  montoTotalPedido varchar2 ,nValorSelCopago_in number default -1)
  RETURN varchar2 ;

  FUNCTION getDetallePedido(cCodGrupoCia_in in varchar2,
                            cCodLocal_in    in varchar2,
                            cNumPedVta_in   in varchar2,
                            cIndConIGV_in   in varchar2,
                            -- KMONCADA 22.04.2016 PERCEPCION
                            cAplicaPercepcion_in        IN CHAR DEFAULT 'N',
                            vContieneProdPercep_in      IN CHAR DEFAULT 'N')
  RETURN FarmaCursor;


  FUNCTION BTLMF_F_CHAR_OBT_MSJ_COMP_IMPR(cCodGrupoCia_in in CHAR,
                                          cCodLocal_in    in CHAR,
                                          cTipoComp IN CHAR,
                                          cNroPedido IN CHAR
                                         )

  RETURN FarmaCursor;

FUNCTION BTLMF_F_GRABA_DATOS_TMP( cCodGrupoCia_in CHAR,
                                  cCodLocal_in    CHAR,
                                  cNumPedVta_in   CHAR,
                                  curComPagoTemp FarmaCursor,
                                  curFormPagoTemp FarmaCursor,
                                  curPedidoDetTemp FarmaCursor
                                 )
   RETURN CHAR;
 FUNCTION BTLMF_F_DEV_SALUDO RETURN VARCHAR2;

FUNCTION BTLMF_F_ES_COMP_CREDITO(cCodCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                                 cCodLocal_in  IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                                 cNroPedido_in IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                 cTipComp_in   IN VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE,
                                 cNumComp_in   IN VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE)
RETURN CHAR;


 PROCEDURE BTLMF_P_AC_FEC_PROC_ANU_RAC(CodGrupoCia_in IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNroPedido     IN CHAR
                                      );

FUNCTION CAMP_F_VAR_MSJ_ANULACION(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in 	IN CHAR,
                                   cajero_in    	IN CHAR,
                                   turno_in   	IN CHAR,
                                   numpedido_in IN CHAR,
                                   cod_igv_in IN CHAR ,
                                   cIndReimpresion_in in CHAR,
                                   numComprobante in CHAR)

  RETURN VARCHAR2;

    FUNCTION VTA_LISTA_FILTRO_COPAGO(cCodConvenio IN CHAR)
    RETURN FarmaCursor;


procedure BTLMF_P_AUX_PRECION_DET_CONV (cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                           cNumPedVta_in   IN CHAR
                                           );


 FUNCTION BTLMF_F_CHAR_PIDE_COPAGO_CONV(vCodConvenio_in IN VARCHAR2)  RETURN CHAR;

 --14/08/2014    ERIOS          Se agrega parametro vNumeroDoc.
  FUNCTION MIFARMA_IMPR_MSG_GRAL(cCodGrupoCia_in in CHAR,
                                     cCodLocal_in    in CHAR,
                                     cTipoComp IN CHAR,
                                     cNumComp IN CHAR,
									 vNumeroDoc IN VARCHAR2,
                   vNumPedVta_in in varchar2,
                   vIsReimpresion IN CHAR DEFAULT 'N'
                                     )
  RETURN FarmaCursor ;

  --Descripcion:  Valida si el numero de comprabantes es relacionad con un
  --              convenio que emite documentos de guia.
  --Fecha         Usuario        Comentario.
  --12/03/2014    RHERRERA          Creación.
  FUNCTION BTLMF_OBT_NU_COM_CONV_GUIA(         cCodGrupoCia_in   IN CHAR,
                                              cCodLocal_in      IN CHAR,
                                              cfecha_in         IN DATE,
                                              cTipComp_in       IN CHAR,
                                              cNumComPago_in    IN CHAR
                                     )

  RETURN CHAR;

  --Descripcion:  Retorna comprobantes del convenio
  --Fecha         Usuario        Comentario.
  --23/04/2014    ERIOS          Creacion
  FUNCTION GET_COMP_CONVENIO(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,cCodCovenio_in IN CHAR)
  RETURN VARCHAR2;

  --Descripcion:  Retorna indicador beneficiario en linea
  --Fecha         Usuario        Comentario.
  --30/04/2014    ERIOS          Creacion
  FUNCTION GET_INDICADOR_BENEF_LINEA(VCODCONVENIO_IN IN CHAR)
  RETURN VARCHAR2;

  --Descripcion:  Retorna Cabecera comprabante vta-empresa
  --Fecha         Usuario        Comentario.
  --26/06/2014    RHERRERA          Creacion
  FUNCTION OBT_DATO_VTA_CLIE(cGrupoCia in char,
                            cCodLocal in char,
                            cNumPedVta in char)
   RETURN FarmaCursor;

  --Descripcion:  Calcula precio tratamiento por convenio
  --Fecha         Usuario        Comentario.
  --18/07/2014    ERIOS          Creacion
  FUNCTION VTA_OBTIENE_PROD_SUG(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                cCodProd_in     IN CHAR,
                                cCantVta_in     IN NUMBER,
								cCodConvenio_in IN CHAR)
     RETURN FarmaCursor;

   -- Author  : LTAVARA
    -- Created : 25/07/2014 06:50:35 p.m.
    -- Purpose : Validar si el convenio puede emitir comprobante electrónico
   FUNCTION FN_VALIDAR_CONV_ELECT( cCodConvenio VARCHAR2)
        RETURN VARCHAR2;

--Autor : Juan Arturo Escate Espichan
--Fecha : 05/11/2008
--Proposito: Esto recalculo el copago variable segun las escalas
--22/08/2014 ERIOS Se agrega parametros
-------------------------------------------------------------------
    FUNCTION FN_CALCULA_COPAGO(
            A_COD_CONVENIO     MAE_CONVENIO.COD_CONVENIO%TYPE,
            A_IMP_TOTAL        FLOAT,
			vPct_beneficiario IN OUT MAE_CONVENIO.PCT_BENEFICIARIO%TYPE,
			vPct_empresa IN OUT MAE_CONVENIO.PCT_EMPRESA%TYPE
			) RETURN FLOAT;

   -- Descripcion:  Obtiene los comprobantes de pago al credito para ser validados
   --               en el rac, previo al cobro
   -- FECHA         USUARIO           COMENTARIO
   -- 02.10.2014    KMONCADA          CREACION.
   --16/09/2016    ERIOS          Se agrega campos para validacion de receta
   FUNCTION COMP_CREDITO_VALIDA_RAC( cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cNroPedido_in   IN CHAR
   ) RETURN FarmaCursor;

  --Descripcion:  LISTA LOS COMPROBANTES A IMPRIMIR O REIMPRIMIR
  --Fecha           Usuario        Comentario.
  --17.11.2014      KMONCADA       Creacion

   FUNCTION BTLMF_F_CUR_LST_COMP_PAGO_IMPR(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         pNroPedido       IN VARCHAR2,
                                         pTipoClienteConv IN CHAR
                                         )
   RETURN FarmaCursor;

   FUNCTION F_CHAR_EMITE_BENEFICIARIO(cCodConvenio_in  IN CHAR)
   RETURN CHAR;

   --Descripcion:  COMPLETA O GENERA SALTO DE LINEA
   --Fecha           Usuario        Comentario.
   --27.01.2015      KMONCADA       Creacion
   FUNCTION F_CHAR_SALTO_LINEAS(  vTexto_in            IN VARCHAR2,
                                  vCantCaractLinea1_in IN INTEGER,
                                  vCantCaractLinea2_in IN INTEGER,
                                  vSeparador_in        IN VARCHAR2)
   RETURN VARCHAR2;

   FUNCTION F_C_IND_PEDIDO_CONVENIO(pCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                   pCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                   pNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN CHAR;

  PROCEDURE BTLMF_P_INSERT_BENIF_NEW(pCod_convenio_in     IN VARCHAR2,
                                            pCodCliente_in IN CHAR,
                                            pDesCliente_in IN CHAR,
                                            pNomCliente_in IN CHAR,
                                            pFlagActivo_in IN CHAR,
                                            pNum_documentoId_in IN VARCHAR2,
                                            vImpLineaCredito_in IN MAE_CONVENIO.IMP_LINEA_CREDITO%TYPE,
                                            pCodBarra_in     IN REL_CONVENIO_BARRA.COD_BARRA%TYPE
                                        );

  --Descripcion: Imprime voucher de convenio
  --Fecha           Usuario        Comentario.
  --27/07/2015      ERIOS          Creacion
  FUNCTION F_IMPR_VOUCHER(cCodGrupoCia_in IN CHAR,
									cCodCia_in    IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNroPedido_in   IN CHAR,
									cIndCopiaGuia_in IN CHAR DEFAULT 'N',
									cSecCompPago_in IN CHAR DEFAULT NULL
                                    ) RETURN FarmaCursor;
  /*********************************************************************************/
  --Descripcion: VALIDA SI PEDIDO TIENE UN PRODUCTO DE VENTA PERMITIDA 0
  --Fecha           Usuario        Comentario.
  --23.06.2016      KMONCDA        Creacion
  FUNCTION F_VTA_CONVENIO_GRATUITA(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                   cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                   cNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                                   cCodProd_in     IN LGT_PROD.COD_PROD%TYPE,
                                   cCodConvenio_in IN MAE_CONVENIO.COD_CONVENIO%TYPE
                                   )
  RETURN VARCHAR2;
  
  /*********************************************************************************/
  --Descripcion: DEVUELVE LA FP DE VENTA GRATUITA EN CASO DE CONVENIO
  --Fecha           Usuario        Comentario.
  --23.06.2016      KMONCDA        Creacion  
  FUNCTION F_GET_FP_CONV_PROD_GRATUITO(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
  		   						   	               cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
									                     cNumPedVta_in 	 IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN FARMACURSOR;
end PTOVENTA_CONV_BTLMF;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CONV_BTLMF" is



  FUNCTION BTLMF_F_CUR_LISTA_CONVENIOS(cCodGrupoCia_in CHAR,
                                       cCodLocal_in    CHAR,
                                       cSecUsuLocal_in CHAR)
    RETURN FarmaCursor IS
    curConv FarmaCursor;
  BEGIN
    OPEN curConv FOR
    --ERIOS 16.12.2013 Convenio COMPETENCIA
    SELECT m.cod_convenio || 'Ã' ||
              case when m.cod_convenio = CONV_COMPETENCIA THEN ' '|| m.des_convenio
                      ELSE DECODE(m.cod_tipo_convenio,2,'(%)'|| m.des_convenio,m.des_convenio)
                END|| 'Ã' ||
                NVL(m.cod_convenio_rel, 'N') || 'Ã' ||
                NVL(m.flg_creacion_cliente, 'N')
         FROM MAE_CONVENIO m WHERE m.flg_activo='1'
         and (m.flg_periodo_validez = '0' OR
         (m.flg_periodo_validez = '1' and sysdate between m.fch_inicio and
         nvl(m.fch_fin, sysdate + 1)))
         and m.flg_atencion_local='1'
         and (m.flg_atencion_todos_locales='1'
         or (m.flg_atencion_todos_locales='0' and exists
         (select 1 from rel_local_conv where cod_convenio=m.cod_convenio
         and estado='A' and cod_grupo_cia=cCodGrupoCia_in and cod_local=cCodLocal_in))
         or m.COD_CONVENIO = CONV_COMPETENCIA);
    RETURN curConv;
  END;

  FUNCTION BTLMF_F_CHAR_PIDE_DATO_CONV(vCodGrupoCia_in IN VARCHAR2,
                                       vCodLocal_in    IN VARCHAR2,
                                       cSecUsuLocal_in IN VARCHAR2,
                                       vCodConvenio_in IN VARCHAR2)
    RETURN CHAR AS
    vResultado CHAR(1);
    vFlagTipoConvenio MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE;

  BEGIN

    SELECT decode(decode(m.flg_beneficiarios, '0', 0, 1) +
                  decode(m.flg_repartidor, '0', 0, 1) +
                  decode(m.flg_receta, '0', 0, 1) +
                  decode(m.flg_medico, '0', 0, 1) +
                  (SELECT count(1)
                     FROM rel_convenio_tipo_campo r
                    WHERE r.cod_convenio = m.cod_convenio),
                  0,
                  'N',
                  'S') ind_pide_datos_generales,
                  m.COD_TIPO_CONVENIO
      INTO vResultado,vFlagTipoConvenio
      FROM MAE_CONVENIO m
     WHERE (m.flg_activo = '1' -- estado activo
            OR m.cod_convenio = CONV_VENTA_EMPRESA)
       AND m.cod_convenio = vCodConvenio_in
       AND m.flg_atencion_local = '1'
          -- esta pendiente el filtro de aplica a Local o Aplica a TODOS.
          -- xq no se tiene los codigos de local MIFARMA en el sistema de BTL.
       AND (m.flg_periodo_validez = '0' or
           (m.flg_periodo_validez = '1' and sysdate between m.fch_inicio and
           nvl(m.fch_fin, sysdate + 1)));

       IF  vResultado = 'N' AND vFlagTipoConvenio = '1' THEN
            vResultado := 'P';
       ELSE
         IF vResultado = 'N' THEN
             vResultado := 'T';
         END IF;
       END IF;

    RETURN vResultado;

  END;

  FUNCTION BTLMF_F_CUR_LISTA_DATO(cCodGrupoCia_in  CHAR,
                                  cCodLocal_in     CHAR,
                                  cSecUsuLocal_in  CHAR,
                                  vCodConvenio_in  CHAR,
                                  vCodTipoCampo_in VARCHAR2,
                                  vDescripcion_in  VARCHAR2)
    RETURN FarmaCursor IS
    curDato     FarmaCursor;
    claseObjeto VARCHAR2(30);
    flgLista    INTEGER;

  BEGIN

    BEGIN
      SELECT C.CLASE_OBJETO
        INTO claseObjeto
        FROM CON_CAMPO_MENSAJE C
       WHERE TRIM(C.COD_CAMPO_MENSAJE) = vCodTipoCampo_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        claseObjeto := '';
    END;

    BEGIN
      SELECT NVL(t.Flg_Lista, 0)
        INTO flgLista
        FROM MAE_TIPO_CAMPO t
       WHERE t.cod_tipo_campo = vCodTipoCampo_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        flgLista := 0;
    END;

    IF vCodTipoCampo_in = COD_DATO_CONV_REPARTIDOR THEN

      curDato := BTLMF_F_CUR_LISTA_REPARTIDOR(cCodGrupoCia_in,
                                              cCodLocal_in,
                                              cSecUsuLocal_in,
                                              vCodConvenio_in,
                                              claseObjeto);
    END IF;

    IF vCodTipoCampo_in = COD_DATO_CONV_MEDICO THEN

      curDato := BTLMF_F_CUR_LISTA_MEDICO(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cSecUsuLocal_in,
                                          vCodConvenio_in,
                                          claseObjeto);
    END IF;

    IF vCodTipoCampo_in = COD_DATO_CONV_ORIG_RECETA THEN

      curDato := BTLMF_F_CUR_LISTA_CLINICA(cCodGrupoCia_in,
                                           cCodLocal_in,
                                           cSecUsuLocal_in,
                                           vCodConvenio_in,
                                           claseObjeto);

    END IF;

    IF vCodTipoCampo_in = COD_DATO_CONV_DIAGNOSTICO_UIE THEN

      curDato := BTLMF_F_CUR_LISTA_DIAGNOSTICO(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cSecUsuLocal_in,
                                               vDescripcion_in);

    END IF;

    IF vCodTipoCampo_in = COD_DATO_CONV_BENIFICIARIO THEN

      curDato := BTLMF_F_CUR_LISTA_BENIFICIARIO(cCodGrupoCia_in,
                                                cCodLocal_in,
                                                cSecUsuLocal_in,
                                                vCodConvenio_in,
                                                vDescripcion_in);

    END IF;

    IF flgLista = '1' THEN

      curDato := BTLMF_F_CUR_LIST_DAT_ADIC_DET(vCodTipoCampo_in);

    END IF;

    RETURN curDato;
  END;

    FUNCTION BTLMF_F_CUR_OBT_BENIFICIARIO(CCODGRUPOCIA_IN CHAR,
                                          CCODLOCAL_IN    CHAR,
                                          CSECUSULOCAL_IN CHAR,
                                          VCODCONVENIO_IN CHAR,
                                          VCODBENIF_IN    CHAR

                                          ) RETURN FARMACURSOR IS
      CURCONV        FARMACURSOR;
      FLGDATARIMAC   CHAR(1);
--      FLGBENEFONLINE CHAR(1);

    BEGIN

      SELECT C.FLG_DATA_RIMAC--, C.FLG_BENEF_ONLINE
        INTO FLGDATARIMAC --, FLGBENEFONLINE
        FROM MAE_CONVENIO C
       WHERE C.COD_CONVENIO = VCODCONVENIO_IN;

      IF FLGDATARIMAC = '1' THEN
         RAISE_APPLICATION_ERROR(-20000,
                              'NO BUSQUEDA DNI.');
        --EN CONSTRUCCION. LISTA DE BENIFICIARIOS DE RIMAC.
/*        OPEN CURCONV FOR
          SELECT BENIF.NOMBRES "DES_NOMBRE"
            FROM PTOVENTA.V_CON_BENEFIC_RIMAC BENIF
           WHERE BENIF.COD_CONVENIO = VCODCONVENIO_IN
             AND BENIF.COD_REFERENCIA = VCODBENIF_IN;*/
      ELSE
        OPEN CURCONV FOR
          SELECT B.DNI "DNI",
                 B.DES_CLIENTE "DES_NOMBRE"
            FROM PTOVENTA.CON_BENEFICIARIO B
           WHERE B.DNI * 1 > 1
             AND B.DNI IS NOT NULL
             AND B.COD_CONVENIO = VCODCONVENIO_IN
             AND TRIM(B.DNI) = VCODBENIF_IN;
      END IF;

      /* -- Si el convenio tiene el flag online activo busca en matriz -- */
      --IF CURCONV%ROWCOUNT = 0 AND FLGBENEFONLINE = 'S' then
      /*IF CURCONV%NOTFOUND AND FLGBENEFONLINE = 'S' then
        IF FLGDATARIMAC = '1' THEN
          OPEN CURCONV FOR
            SELECT BENIF.NOMBRES "DES_NOMBRE"
              FROM PTOVENTA.V_CON_BENEFIC_RIMAC@XE_000 BENIF
             WHERE BENIF.COD_CONVENIO = VCODCONVENIO_IN
               AND BENIF.COD_REFERENCIA = VCODBENIF_IN;
        ELSE
          OPEN CURCONV FOR
            SELECT B.DNI "DNI",
                   B.DES_CLIENTE || ' ' || '' "DES_NOMBRE"
              FROM PTOVENTA.V_CON_BENEFICIARIO@XE_000 B
             WHERE B.DNI * 1 > 1
               AND B.DNI IS NOT NULL
               AND B.COD_CONVENIO = VCODCONVENIO_IN
               AND B.DNI = VCODBENIF_IN;
        END IF;
      END IF;*/

      RETURN CURCONV;
    END;

  FUNCTION GET_CANT_LISTA_BENEFICIARIO(CCODGRUPOCIA_IN  CHAR,
                                            CCODLOCAL_IN     CHAR,
                                            CSECUSULOCAL_IN  CHAR,
                                            VCODCONVENIO_IN  CHAR,
                                            VBENIFICIARIO_IN VARCHAR2)
  RETURN INTEGER IS
    nCantLocal INTEGER;
    FLGDATARIMAC   CHAR(1);
    FLGBENEFONLINE CHAR(1);
  BEGIN
    SELECT C.FLG_DATA_RIMAC-- , C.FLG_BENEF_ONLINE
        INTO FLGDATARIMAC --, FLGBENEFONLINE
        FROM MAE_CONVENIO C
       WHERE C.COD_CONVENIO = VCODCONVENIO_IN;

     IF FLGDATARIMAC = '1' THEN
       nCantLocal:=0;
/*
      SELECT COUNT(1) INTO nCantLocal
      FROM PTOVENTA.V_CON_BENEFIC_RIMAC BENIF
             WHERE BENIF.COD_CONVENIO = VCODCONVENIO_IN
             AND BENIF.NOMBRES LIKE '%' || UPPER(TRIM(VBENIFICIARIO_IN)) || '%';
             */
    ELSE

      SELECT COUNT(1) INTO nCantLocal
      FROM CON_BENEFICIARIO P
        WHERE P.COD_CONVENIO = VCODCONVENIO_IN
         AND P.DES_CLIENTE LIKE '%' || UPPER(TRIM(VBENIFICIARIO_IN)) || '%';
    END IF;

    RETURN nCantLocal;
  END;

    FUNCTION BTLMF_F_CUR_LISTA_BENIFICIARIO(CCODGRUPOCIA_IN  CHAR,
                                            CCODLOCAL_IN     CHAR,
                                            CSECUSULOCAL_IN  CHAR,
                                            VCODCONVENIO_IN  CHAR,
                                            VBENIFICIARIO_IN VARCHAR2

                                            ) RETURN FARMACURSOR IS
      PRAGMA AUTONOMOUS_TRANSACTION;
    CURCONV        FARMACURSOR;
      REG            VARCHAR2(32000);
      FLGDATARIMAC   CHAR(1);
      FLGBENEFONLINE CHAR(1);
      FLGFOUND       CHAR(1) DEFAULT 'N';
    nCantLocal INTEGER;
    nCantRemoto INTEGER;
    BEGIN
/*
      SELECT C.FLG_DATA_RIMAC, C.FLG_BENEF_ONLINE
        INTO FLGDATARIMAC, FLGBENEFONLINE
        FROM MAE_CONVENIO C
       WHERE C.COD_CONVENIO = VCODCONVENIO_IN;
*/
    /*  IF FLGDATARIMAC = '1' THEN

      ELSE*/
        OPEN CURCONV FOR
          SELECT NVL(BENIF.DNI,'        ')          || 'Ã' ||
                 BENIF.DES_CLIENTE                  || ' ' ||
                 ''                                 || 'Ã' ||
                 TO_CHAR(NVL(BENIF.IMP_LINEA_CREDITO, '0'),'999,999.90')  || 'Ã' || -- KMONCADA 01.10.2014 FORMATO DE NUMERO DECIMAL
                 NVL(BENIF.ESTADO, 'A')             || 'Ã' ||
                 ' '                                || 'Ã' ||
                 ' '                                || 'Ã' ||
                 ' '                                || 'Ã' ||
                 ' '                                || 'Ã' ||
                 ' '                                || 'Ã' ||
                 ' '                                || 'Ã' ||
                 ' '                                || 'Ã' ||
                 ' '                                || 'Ã' ||
                 BENIF.COD_CLIENTE
            FROM CON_BENEFICIARIO BENIF
        WHERE BENIF.COD_CONVENIO = VCODCONVENIO_IN
         AND BENIF.FLG_ACTIVO = '1'
         AND BENIF.DES_CLIENTE LIKE '%' || UPPER(TRIM(VBENIFICIARIO_IN)) || '%';
    --  END IF;

    ROLLBACK;
      RETURN CURCONV;

    END;


  FUNCTION BTLMF_F_CUR_LISTA_REPARTIDOR(cCodGrupoCia_in CHAR,
                                        cCodLocal_in    CHAR,
                                        cSecUsuLocal_in CHAR,
                                        vCodConvenio_in CHAR,
                                        vClaseObjeto_in VARCHAR2)
    RETURN FarmaCursor IS
    curConv FarmaCursor;
  BEGIN

    IF trim(vClaseObjeto_in) = OBJ_LISTA_COMBO THEN
      OPEN curConv FOR
        SELECT v.codigo || 'Ã' || v.descripcion
          from (SELECT '0' codigo, '---SELECCIONE---' descripcion, 1 orden
                  FROM DUAL
                UNION
                SELECT rep.cod_repartidor codigo,
                       rep.DES_REPARTIDOR descripcion,
                       2                  orden
                  FROM Mae_Repartidor rep, rel_convenio_repartidor rel
                 WHERE rep.cod_repartidor = rel.cod_repartidor
                   AND rep.flg_activo = 1
                   AND rel.cod_convenio = vCodConvenio_in) v
         order by v.orden, v.descripcion;
    END IF;
    IF trim(vClaseObjeto_in) = OBJ_LISTA_PANTALLA THEN
      OPEN curConv FOR
        SELECT rep.cod_repartidor || 'Ã' || rep.DES_REPARTIDOR
          FROM Mae_Repartidor rep, rel_convenio_repartidor rel
         WHERE rep.cod_repartidor = rel.cod_repartidor
           AND rep.flg_activo = 1
           AND rel.cod_convenio = vCodConvenio_in

         ORDER BY rep.DES_REPARTIDOR;
    END IF;

    RETURN curConv;
  END;

  FUNCTION BTLMF_F_CUR_LISTA_MEDICO(cCodGrupoCia_in CHAR,
                                    cCodLocal_in    CHAR,
                                    cSecUsuLocal_in CHAR,
                                    vCodConvenio_in CHAR,
                                    vClaseObjeto_in VARCHAR2)
    RETURN FarmaCursor IS
    curConv FarmaCursor;
  BEGIN

    IF trim(vClaseObjeto_in) = OBJ_LISTA_COMBO THEN
      OPEN curConv FOR
        select v.codigo || 'Ã' || v.descripcion
          from (SELECT '0' codigo, '---SELECCIONE---' descripcion, 1 orden
                  FROM DUAL
                UNION
                SELECT med.cod_medico codigo,
                       med.des_nom_medico || ' ' || med.des_ape_medico descripcion,
                       2 orden
                  FROM mae_medico med, rel_convenio_medico rm
                 WHERE med.cod_medico = rm.cod_medico
                   AND med.flg_activo = 1
                   AND rm.cod_convenio = vCodConvenio_in) v
         order by v.orden, v.descripcion;

    END IF;
    IF trim(vClaseObjeto_in) = OBJ_LISTA_PANTALLA THEN
    --ERIOS 2.4.7 Se agrega codigo de medico
      OPEN curConv FOR
        SELECT med.num_cmp || 'Ã' ||
         med.des_nom_medico || ' ' || med.des_ape_medico || 'Ã' ||
         med.cod_medico
          FROM mae_medico med, rel_convenio_medico rm

         WHERE med.cod_medico = rm.cod_medico
           AND med.flg_activo = 1
           AND rm.cod_convenio = vCodConvenio_in
         ORDER BY med.des_nom_medico;

    END IF;

    RETURN curConv;
  END;

  FUNCTION BTLMF_F_CUR_OBTIENE_MEDICO(cCodGrupoCia_in CHAR,
                                      cCodLocal_in    CHAR,
                                      cSecUsuLocal_in CHAR,
                                      vCodConvenio_in CHAR,
                                      vCodMedico_in   VARCHAR2)
    RETURN FarmaCursor IS
    curMedico FarmaCursor;
  BEGIN
  --ERIOS 2.4.7 Se agrega campo codigo de medico
    OPEN curMedico FOR
      SELECT med.num_cmp, med.des_nom_medico, med.des_ape_medico,
       med.cod_medico
        FROM mae_medico med, rel_convenio_medico rm
       WHERE med.cod_medico = rm.cod_medico
         AND med.num_cmp = vCodMedico_in
         AND rm.cod_convenio = vCodConvenio_in
         AND med.flg_activo = 1
         AND rownum = 1;

    RETURN curMedico;
  END;

  FUNCTION BTLMF_F_CUR_LISTA_CLINICA(cCodGrupoCia_in CHAR,
                                     cCodLocal_in    CHAR,
                                     cSecUsuLocal_in CHAR,
                                     vCodConvenio_in CHAR,
                                     vClaseObjeto_in VARCHAR2)
    RETURN FarmaCursor IS
    curConv FarmaCursor;
  BEGIN

    IF trim(vClaseObjeto_in) = OBJ_LISTA_COMBO THEN

      OPEN curConv FOR
        SELECT v.codigo || 'Ã' || v.descripcion
          FROM (SELECT '0' codigo, '---SELECCIONE---' descripcion, 1 orden
                  FROM DUAL
                UNION
                SELECT l.cod_local, l.des_local, 2 orden
                  FROM MAE_LOCAL l, rel_convenio_local_receta rc
                 WHERE l.cod_local = rc.cod_local
                   AND rc.cod_convenio = vCodConvenio_in) v
         ORDER BY v.orden, v.descripcion;
    END IF;

    IF trim(vClaseObjeto_in) = OBJ_LISTA_PANTALLA THEN

      OPEN curConv FOR
        SELECT l.cod_local || 'Ã' || l.des_local
          FROM MAE_LOCAL l, rel_convenio_local_receta rc
         WHERE l.cod_local = rc.cod_local
           AND rc.cod_convenio = vCodConvenio_in

         ORDER BY l.des_local;
    END IF;

    RETURN curConv;
  END;

  FUNCTION BTLMF_F_CUR_LISTA_DIAGNOSTICO(cCodGrupoCia_in    CHAR,
                                         cCodLocal_in       CHAR,
                                         cSecUsuLocal_in    CHAR,
                                         vDesDiagnostico_in VARCHAR2)
    RETURN FarmaCursor IS
    curConv FarmaCursor;
  BEGIN
    OPEN curConv FOR

      SELECT

       d.COD_CIE_10 || 'Ã' || d.DES_DIAGNOSTICO || 'Ã' || d.cod_diagnostico
        FROM MAE_DIAGNOSTICO d
       WHERE d.flg_activo = '1';

    RETURN curConv;
  END;

  FUNCTION BTLMF_F_CUR_OBT_DIAGNOSTICO(cCodGrupoCia_in CHAR,
                                       cCodLocal_in    CHAR,
                                       cSecUsuLocal_in CHAR,
                                       vCodCIE10_in    VARCHAR2)
    RETURN FarmaCursor IS
    curDiagno FarmaCursor;
  BEGIN
    OPEN curDiagno FOR
      SELECT d.COD_CIE_10, d.DES_DIAGNOSTICO,d.COD_DIAGNOSTICO
        FROM MAE_DIAGNOSTICO d
       WHERE d.flg_activo = '1'
         AND UPPER(d.cod_cie_10) = UPPER(vCodCIE10_in);

    RETURN curDiagno;
  END;

  FUNCTION BTLMF_F_CUR_LISTA_DATO_ADIC(cCodGrupoCia_in CHAR,
                                       cCodLocal_in    CHAR,
                                       cSecUsuLocal_in CHAR,
                                       vCodConvenio_in CHAR)
    RETURN FarmaCursor IS
    curConv FarmaCursor;
  BEGIN
    OPEN curConv FOR
      SELECT d.des_tipo_campo || 'Ã' || r.ctd_long_max
        FROM mae_tipo_campo d, rel_convenio_tipo_campo r
       WHERE d.cod_tipo_campo = r.cod_tipo_campo
         AND d.flg_activo = '1'
         AND r.cod_convenio = vCodConvenio_in;

    RETURN curConv;
  END;

  FUNCTION BTLMF_F_CUR_LIST_DAT_ADIC_DET(vCodTipoCampo CHAR)
    RETURN FarmaCursor IS
    curConv FarmaCursor;
  BEGIN
    OPEN curConv FOR
      SELECT det.cod_eux_tipo_campo || 'Ã' || det.des_eux_tipo_campo
        FROM aux_datos_tipo_campo det
       WHERE det.cod_tipo_campo = vCodTipoCampo;

    RETURN curConv;
  END;

   FUNCTION BTLMF_F_CHAR_OBTIENE_DOC_VERIF(vCodGrupoCia_in   IN CHAR,
                                          vCodLocal_in      IN CHAR,
                                          cSecUsuLocal_in   IN CHAR,
                                          vCodConvenio_in   IN VARCHAR2,
                                          vFlg_retencion_in IN CHAR,
                                          vNombreBenif_in   IN VARCHAR2)
    RETURN VARCHAR2 IS
    vResultado VARCHAR2(32767);

    v_des_doc_verificacion VARCHAR2(100) := '';
    v_htmlIzquierdo        VARCHAR2(6000) := '';
    v_htmlDerecho          VARCHAR2(6000) := '';
    v_htmlAbajo            VARCHAR2(6000) := '';

    v_msg_convenio VARCHAR(6000) := '';

    curDoc FarmaCursor := BTLMF_F_CHAR_OBTIENE_DATO(vCodGrupoCia_in,
                                                    vCodLocal_in,
                                                    cSecUsuLocal_in,
                                                    vCodConvenio_in,
                                                    vflg_retencion_in);

  BEGIN

    IF vFlg_retencion_in = '1' THEN


      v_htmlIzquierdo := '<table width="' || PANTALLA_IZQ_PIXEL_ANCHO ||
                         '" border= "0"  align ="center">' || '<TR>' ||
                         '<TD><H1>LOCAL: ' || vNombreBenif_in || '</H1>' ||
                         '</TD>' || '</TR>' || '<TR>' || '<Td height = "' ||
                         PANTALLA_IZQ_PIXEL_ALTO || '">' ||
                         '  <table  width="' || PANTALLA_IZQ_PIXEL_ANCHO ||
                         '" border= "0"  align ="center"> ' || '  <tr> ' ||
                         '  <td valign="top" colspan ="2"> ' ||
                         '       <font color="#000000" size ="6" face="Arial"> ' ||
                         '       Tiene que retener estos documentos :</font> ' ||
                         '  <td> ' || '  </tr> ' || '  <tr> ' || '  <td> ' ||
                         '  </td> ' || '  </tr> ' || '  <tr> ' ||
                         '    <td height = "' || PANTALLA_IZQ_PIXEL_ALTO ||
                         /*'        <font color="#000000" size ="5" face="Arial"> ' ||*/
                         '" valign ="top"> ';
      LOOP
        FETCH curDoc
          INTO v_des_doc_verificacion;

        EXIT WHEN curDoc%NOTFOUND;

           v_htmlIzquierdo := v_htmlIzquierdo ||
                         '        <font color="#000000" size ="5" face="Arial"> ' ||
                         '          - ' ||
                         BTLMF_F_CHAR_INI_CAP(v_des_doc_verificacion) ||
                         '<br> ' || '        </font> ';
          /* v_htmlIzquierdo := v_htmlIzquierdo || '         &nbsp&nbsp - ' ||
                           BTLMF_F_CHAR_INI_CAP(v_des_doc_verificacion) ||
                           '<br> </font>';*/
      END LOOP;
      close curDoc;

      v_htmlIzquierdo := v_htmlIzquierdo || '</td>' || '   <td >' ||
                         '        <font color="#000000" size ="5" face="Arial"> ' ||
                         '           <br> ' ||
                         '           <br> ' ||
                         '           <br> ' ||
                         '           <br> ' ||
                         '           <br> ' ||
                         '           <br> ' ||
                         '           <br> ' || '      </font> ' ||
                         '    </td>' || '  </tr> ' || ' </table> ' ||
                         '</TD>' || '</TR>' || '</TABLE>';

      vResultado := v_htmlIzquierdo;

    END IF;

    IF vFlg_retencion_in = '0' THEN

      v_htmlDerecho := '  <table width="' ||
                       PANTALLA_DER_PIXEL_ANCHO ||
                       '" border= "0"  align ="center"> ' ||
                       '<TR>' || '<Td height = "' ||
                         PANTALLA_DER_PIXEL_ALTO || '">' ||
                         '  <table  width="' || PANTALLA_DER_PIXEL_ANCHO ||
                         '" border= "0"  align ="center"> ' ||
                        '  <tr> ' ||
                       '  <td colspan ="2" > ' ||
                       '       <font color="#000000" size ="6" face="Arial"> ' ||
                       '       Tiene que verificar estos documentos del cliente:</font> ' ||
                       '  <td> ' || '  </tr> ' || '  <tr> ' ||
                       '  <td colspan ="2"> ' || '  </td> ' ||
                       '  </tr> ' ||

                       '  <tr> ' || '    <td height valign ="top"> ';


      LOOP
        FETCH curDoc
          INTO v_des_doc_verificacion;

        EXIT WHEN curDoc%NOTFOUND;

         v_htmlDerecho := v_htmlDerecho ||
                         '        <font color="#000000" size ="5" face="Arial"> ' ||
                         '          - ' ||
                         BTLMF_F_CHAR_INI_CAP(v_des_doc_verificacion) ||
                         '<br> ' || '        </font> ';
      END LOOP;
      close curDoc;
     v_htmlDerecho := v_htmlDerecho || '   </td> ' || '    <td> ' ||
                       '        <font color="#000000" size ="5" face="Arial"> ' ||
                       '          <br> ' ||
                       '          <br> ' ||
                       '          <br> ' ||
                       '          <br> ' ||
                       '          <br> ' ||
                       '          <br> ' ||
                       '          <br> ' ||
                        '          <br>' || '      </font> ' ||
                       '    </td> ' || '  </tr> ' || ' </table> '||
                        '</TD>' || '</TR>' || '</TABLE>';

      vResultado := v_htmlDerecho;

    END IF;

    IF vFlg_retencion_in = '2' THEN

      SELECT c.msg_convenio
        INTO v_msg_convenio
        FROM MAE_CONVENIO c
       WHERE c.cod_convenio = vCodConvenio_in;

      v_htmlAbajo := '' || '  <table  width = "' ||
                     PANTALLA_ABA_PIXEL_ANCHO ||
                     '" border= "0"  align ="center" > ' || '  <tr> ' ||
                     '<td  align="center">' || '</td> ' ||
                     '  <tr> ' || '    <td  height = "' ||
                     PANTALLA_ABA_PIXEL_ALTO || '" width="' ||
                     PANTALLA_ABA_PIXEL_ANCHO || '" valign = "top">' ||
                     REPLACE(v_msg_convenio, ';', ' ') || '    </td> ' ||
                     '  </tr> ' || '  <tr>' ||
                     '<td align="center"><H3> Presione [Enter] para continuar.</H3> ' ||
                     '</td>' || '  </tr> ' || ' </table> ' || '';
      vResultado  := v_htmlAbajo;

    END IF;

    RETURN vResultado;

  END;



  FUNCTION BTLMF_F_CHAR_OBTIENE_DATO(vCodGrupoCia_in   IN CHAR,
                                     vCodLocal_in      IN CHAR,
                                     cSecUsuLocal_in   IN CHAR,
                                     vCodConvenio_in   IN VARCHAR2,
                                     vflg_retencion_in IN CHAR)
    RETURN FarmaCursor IS
    curDoc FarmaCursor;

  BEGIN
    OPEN curDoc FOR
      SELECT d.des_documento_verificacion
        FROM mae_documento_verificacion d, rel_convenio_docum_verif f
       WHERE d.cod_documento_verificacion = f.cod_documento_verificacion
         AND f.flg_activo = '1'
         AND f.cod_convenio = vCodConvenio_in
         AND f.flg_retencion = vflg_retencion_in;
    RETURN curDoc;
  END;

  FUNCTION BTLMF_F_CHAR_INI_CAP(vCadena IN VARCHAR2) RETURN VARCHAR2 IS
    vResultado VARCHAR2(300);
    vLongitud  NUMBER;

    vSubCadena VARCHAR(300);
    vCadenaTra VARCHAR(300) := '';

  BEGIN
    vLongitud := length(vCadena);

    FOR i in 1 .. vLongitud LOOP

      vSubCadena := PTOVENTA_CONV_BTLMF.split(vCadena, i, ' ');
      IF vSubCadena IS NOT NULL THEN

        vCadenaTra := vCadenaTra || ' ' || vSubCadena;

      END IF;

    END LOOP;

    vResultado := initcap(TRIM(vCadenaTra));

    RETURN vResultado;
  END;

  FUNCTION BTLMF_F_CUR_LISTA_DATOS_CONV(cCodGrupoCia_in CHAR,
                                        cCodLocal_in    CHAR,
                                        cSecUsuLocal_in CHAR,
                                        cCodConvenio_in CHAR)
    RETURN FarmaCursor IS
    curConv      FarmaCursor;
    flgDataRimac char(1);
  BEGIN

    BEGIN

      SELECT C.FLG_DATA_RIMAC
        INTO flgDataRimac
        FROM MAE_CONVENIO C
       WHERE C.COD_CONVENIO = cCodConvenio_in;

    END;

    OPEN curConv FOR
      SELECT

             VH.COD_TIPO_CAMPO,
             DECODE(VH.COD_TIPO_CAMPO,'D_003',PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_INI_CAP(VH.DES_TIPO_CAMPO)||' (DD/MM/YYYY)' ,PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_INI_CAP(VH.DES_TIPO_CAMPO)) DES_TIPO_CAMPO,
             VH.CTD_LONG_MAX,
             VH.CTD_LONG_MIN,
             VH.IND_CREA_OBJETO,
             VH.EDITABLE,
             VH.INVOCA_LISTA,
             VH.CLASE_OBJETO,
             VH.DESC_CAMPO_MENSAJE,
             VH.ORDEN,
             '' DATO_INGRESADO,
             rank() over(order by vh.ORDEN asc) POSICION,
             VH.FLG_LISTA
        FROM (SELECT r.COD_TIPO_CAMPO,
                     t.DES_TIPO_CAMPO,
                     r.CTD_LONG_MAX,
                     r.CTD_LONG_MIN,
                     decode(nvl(t.flg_editable, 0), '0', 'N', 'S') IND_CREA_OBJETO,
                     decode(nvl(t.flg_editable, 0), '0', 'N', 'S') EDITABLE,
                     decode(nvl(t.flg_lista, '0'), '0', 'N', 'S') INVOCA_LISTA,
                     case
                       when decode(nvl(t.flg_editable, 0), '0', 'N', 'S') = 'S' then
                        decode(nvl(t.flg_lista, '0'),
                               '0',
                               'INGRESO_TEXTO',
                               'LISTA_PANTALLA')
                       else
                        'N'
                     end CLASE_OBJETO,
                     case
                       when t.flg_lista = 1 then
                        'Presione [F12]'
                       else
                        'Ingrese Dato'
                     end "DESC_CAMPO_MENSAJE",
                     'Z' || R.COD_TIPO_CAMPO ORDEN,
                     t.flg_lista
                FROM rel_convenio_tipo_campo r, MAE_TIPO_CAMPO t
               WHERE cod_convenio = cCodConvenio_in --'0000000084'
                 AND T.FLG_ACTIVO = '1'
                 AND r.cod_tipo_campo = t.cod_tipo_campo
              UNION
              SELECT VD.COD_DATO_CONV AS "COD_TIPO_CAMPO",
                     CASE
                       WHEN VD.COD_DATO_CONV = 'D_000' THEN
                        'BENEFICIARIO'
                       WHEN VD.COD_DATO_CONV = 'D_001' THEN
                        'REPARTIDOR'
                       WHEN VD.COD_DATO_CONV = 'D_002' THEN
                        'ORIGEN RECETA'
                       WHEN VD.COD_DATO_CONV = 'D_003' THEN
                        'FECHA RECETA'
                       WHEN VD.COD_DATO_CONV = 'D_004' THEN
                        'DIAGNOSTICO CIE 10'
                       WHEN VD.COD_DATO_CONV = 'D_005' THEN
                        'MEDICO'
                     END AS "DES_TIPO_CAMPO",
                     0 as "CTD_LONG_MAX",
                     0 as "CTD_LONG_MIN",
                     'S' as "IND_CREA_OBJETO",
                     'S' as EDITABLE,
                     CASE
                       WHEN VD.COD_DATO_CONV = 'D_003' THEN
                        'S'
                       else
                        'N'
                     END AS "INVOCA_LISTA",
                     CM.CLASE_OBJETO AS "CLASE_OBJETO",
                     CASE
                       WHEN VD.COD_DATO_CONV = 'D_000' AND flgDataRimac = '1' THEN
                        'Presione F12'
                       ELSE
                        NVL(CM.DESC_CAMPO_MENSAJE, ' ')
                     END "DESC_CAMPO_MENSAJE",
                     VD.COD_DATO_CONV ORDEN,
                     0 flg_lista
                FROM (SELECT trim(EXTRACTVALUE(xt.column_value, 'e')) COD_DATO_CONV
                        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                               REPLACE((SELECT
                                                                             CASE WHEN m.flg_data_rimac = '1' THEN
                                                                                       'D_000'
                                                                                  ELSE
                                                                              decode(m.flg_beneficiarios,
                                                                                       '0',
                                                                                       '',
                                                                                       'D_000')
                                                                              END            || '@' ||
                                                                              decode(m.flg_repartidor,
                                                                                     '0',
                                                                                     '',
                                                                                     'D_001') || '@' ||
                                                                              decode(m.flg_receta,
                                                                                     '0',
                                                                                     '',
                                                                                     'D_002') || '@' ||
                                                                              decode(m.flg_receta,
                                                                                     '0',
                                                                                     '',
                                                                                     'D_003') || '@' ||
                                                                              decode(m.flg_receta,
                                                                                     '0',
                                                                                     '',
                                                                                     'D_004') || '@' ||
                                                                              decode(m.flg_medico,
                                                                                     '0',
                                                                                     '',
                                                                                     'D_005')
                                                                         FROM MAE_CONVENIO m
                                                                        WHERE m.flg_activo = '1' -- estado activo
                                                                          AND m.cod_convenio =
                                                                              cCodConvenio_in
                                                                             --'0000000084'
                                                                          AND m.flg_atencion_local = '1'
                                                                          AND (m.flg_periodo_validez = '0' or
                                                                              (m.flg_periodo_validez = '1' and
                                                                              sysdate between
                                                                              m.fch_inicio and
                                                                              nvl(m.fch_fin,
                                                                                    sysdate + 1)))),
                                                                       '@',
                                                                       '</e><e>') ||
                                                               '</e></coll>'),
                                                       '/coll/e'))) xt) VD,
                     CON_CAMPO_MENSAJE CM

               WHERE VD.COD_DATO_CONV IS NOT NULL
                 AND VD.COD_DATO_CONV = TRIM(CM.COD_CAMPO_MENSAJE(+))) VH

       order by POSICION asc;

    RETURN curConv;
  END;

  FUNCTION BTLMF_F_CUR_LISTA_PANTALLA_MSG(cCodGrupoCia_in   CHAR,
                                          cCodLocal_in      CHAR,
                                          cSecUsuLocal_in   CHAR,
                                          vNroResolucion_in VARCHAR2,
                                          vPosicion_in      CHAR)
    RETURN FarmaCursor IS
    curConv FarmaCursor;
    nExiste number;
  BEGIN
    SELECT count(1)
      into nExiste
      FROM CON_PANTALLA_MSJ m
     WHERE m.nro_resolucion = vNroResolucion_in
       AND m.posicion = vPosicion_in;

    if nExiste > 0 then
      OPEN curConv FOR
        SELECT m.factor_ancho, m.factor_altura
          FROM CON_PANTALLA_MSJ m
         WHERE m.nro_resolucion = vNroResolucion_in
           AND m.posicion = vPosicion_in;
    else
      dbms_output.put_line('cons_NRO_RESOLUCION:' || cons_NRO_RESOLUCION);
      dbms_output.put_line('vPosicion_in:' || vPosicion_in);
      OPEN curConv FOR
        SELECT m.factor_ancho, m.factor_altura
          FROM CON_PANTALLA_MSJ m
         WHERE m.nro_resolucion = CONS_NRO_RESOLUCION
           AND m.posicion = vPosicion_in;

    end if;

    RETURN curConv;
  END;

  PROCEDURE BTLMF_P_INSERT_BENIFICIARIO(cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in      IN CHAR,
                                        cSecUsuLocal_in   IN CHAR,
                                        pCod_convenio     IN VARCHAR2,
                                        pNum_documento_id IN VARCHAR2,
                                        pDes_nom_cliente  IN VARCHAR2,
                                        pDes_ape_cliente  IN VARCHAR2,
                                        pDes_email        IN VARCHAR2,
                                        pFch_nacimiento   IN VARCHAR2,
                                        PTelefono         IN VARCHAR2,
                                        pFlg_creacion     IN CHAR,
                                        PCodCliente IN CHAR DEFAULT NULL
                                        )

   AS

    vNumero     varchar2(1000);
    vCodCliente varchar2(10);
    mesg_body              VARCHAR2(32000);
    C_DES_INICIO               VARCHAR2(500):='<tr><th align="left" width="70px">';
    C_DES_MEDIO                VARCHAR2(500):='</th><td width="100%"><i>;';
    C_DES_FIN                  VARCHAR2(500):='</i></td></tr>';

    vNombreConvenio MAE_CONVENIO.DES_CONVENIO%TYPE;
    vDni CON_TMP_BENIFICIARIO.DNI%TYPE;
    vImpLineaCredito MAE_CONVENIO.IMP_LINEA_CREDITO%TYPE;

  BEGIN
    SELECT CONV.DES_CONVENIO,CONV.MTO_LINEA_CRED_BASE
      INTO vNombreConvenio,vImpLineaCredito
      FROM MAE_CONVENIO CONV
     WHERE CONV.COD_CONVENIO =  pCod_convenio;



   IF pFlg_creacion = 'S' THEN
      SELECT TO_CHAR(SEQ_CON_TMP_BENIFICIARIO.NEXTVAL)
        INTO vNumero
        FROM DUAL;

       SELECT lpad(pFlg_creacion || vNumero, 10, '0')
        INTO vCodCliente
        FROM DUAL;
   ELSE
       vCodCliente := PCodCliente;
   END IF;

    insert into CON_TMP_BENIFICIARIO
      (cia,
       cod_local,
       dni,
       cod_convenio,
       des_nom_cliente,
       des_ape_cliente,
       des_email,
       fch_nacimiento,
       telefono,
       flg_creacion,
       cod_cliente,
       fec_creacion,
       LINEA_CREDITO)
    values
      (cCodGrupoCia_in,
       cCodLocal_in,
       PNum_documento_id,
       pCod_convenio,
       PDes_nom_cliente,
       PDes_ape_cliente,
       PDes_email,
       PFch_nacimiento,
       PTelefono,
       PFlg_creacion,
       vCodCliente,
       SYSDATE,
       vImpLineaCredito);

       COMMIT;

       BEGIN
         SELECT DNI
           INTO vDni
         FROM
         CON_TMP_BENIFICIARIO
         WHERE DNI = PNum_documento_id ;
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
         vDni := NULL;
       END;


       IF PFlg_creacion = 'S' AND vDni IS NOT NULL THEN

           --CUERPO MENSAJE
           mesg_body := mesg_body||'<TABLE BORDER="0" width="500px">';
           mesg_body := mesg_body||'<TR><TD COLSPAN="2"><H2>'||BTLMF_F_DEV_SALUDO||' '||'Fredy Ramirez C'||'</H2></TD></TR>'||
                                    '<TR><TD COLSPAN="2"><i>'||' Se ha solicitado la creación de un nuevo cliente Beneficiario </i></TD></TR>'||
                                    C_DES_INICIO||'Local:'||C_DES_MEDIO||cCodLocal_in||C_DES_FIN||
                                    C_DES_INICIO||'Fecha: '||C_DES_MEDIO||TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:MI')||C_DES_FIN||
                                    C_DES_INICIO||'Solicitante: '||C_DES_MEDIO||'Karen Huaman Torres.'||C_DES_FIN;
            mesg_body := mesg_body||C_DES_INICIO||'Convenio: '||C_DES_MEDIO||pCod_convenio||'-'||vNombreConvenio||C_DES_FIN;
            --mesg_body := mesg_body||C_DES_INICIO||'C. Benef: '||C_DES_MEDIO||C_REG_SOLICITUD.COD_REFERENCIA_BENEFICIARIO||C_DES_FIN;
            mesg_body := mesg_body||C_DES_INICIO||'Apellidos'||C_DES_MEDIO||PDes_ape_cliente||C_DES_FIN;
            mesg_body := mesg_body||C_DES_INICIO||'Nombre: '||C_DES_MEDIO||PDes_nom_cliente||C_DES_FIN;
            --mesg_body := mesg_body||C_DES_INICIO||'Cargo: '||C_DES_MEDIO||C_REG_SOLICITUD.DES_CARGO||C_DES_FIN;
            mesg_body := mesg_body||C_DES_INICIO||'DNI: '||C_DES_MEDIO||PNum_documento_id||C_DES_FIN;
            mesg_body := mesg_body||C_DES_INICIO||'Fecha Nac.: '||C_DES_MEDIO||PFch_nacimiento||C_DES_FIN;
            mesg_body := mesg_body||C_DES_INICIO||'Telefono:: '||C_DES_MEDIO||PTelefono||C_DES_FIN;
           -- mesg_body := mesg_body||C_DES_INICIO||'Estado Civil: '||C_DES_MEDIO||C_REG_SOLICITUD.COD_ESTADO_CIVIL||'-'||FN_DES_ESTCIVIL(C_REG_SOLICITUD.COD_ESTADO_CIVIL)||C_DES_FIN;
            mesg_body := mesg_body||C_DES_INICIO||'E-Mail: '||C_DES_MEDIO||PDes_email||C_DES_FIN;
            mesg_body := mesg_body||'</TD></TR></TABLE>';

               --ENVIANDO EL CORREO
             --  FARMA_EMAIL.GET_SENDDOR_ADDRESS
           --ENVIO DE CORREO.
           FARMA_EMAIL.envia_correo('programacion@mifarma.com.pe',
                               'programacion@mifarma.com.pe',
                               'Solicitud de Creación del nuevo Beneficiario : ',
                               'ALERTA',
                               mesg_body,
                               '',
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               TRUE);

          --FARMA_EMAIL.GET_EMAIL_SERVER

        END IF;

  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20999,
                              'ERROR AL INSERTAR O ENVIAR CORREO DEL NUEVO BENEFICIARIO.' ||
                              SQLERRM);
      ROLLBACK;

  END BTLMF_P_INSERT_BENIFICIARIO;

  FUNCTION BTLMF_F_CUR_OBTIENE_BENIF(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cSecUsuLocal_in   IN CHAR,
                                     pCod_convenio     IN CHAR,
                                     pNum_documento_id IN VARCHAR2)
    RETURN FarmaCursor IS
    curBenif FarmaCursor;

    existeBenifTemp CON_TMP_BENIFICIARIO.DNI%TYPE;

  BEGIN

  /* BEGIN
        SELECT B.DNI
          INTO existeBenifTemp
          FROM CON_TMP_BENIFICIARIO B
       WHERE B.CIA = cCodGrupoCia_in
         AND B.COD_LOCAL = cCodLocal_in
         AND B.COD_CONVENIO = pCod_convenio
         AND B.DNI = pNum_documento_id
         AND B.FLG_CREACION = 'C'
         AND TO_CHAR(B.FEC_CREACION,'DD/MM/YYYY') = TO_CHAR(SYSDATE,'DD/MM/YYYY');

         EXCEPTION
          WHEN NO_DATA_FOUND THEN
          existeBenifTemp := NULL;

    END;

   IF existeBenifTemp IS NOT NULL THEN

   ELSE*/
         OPEN curBenif FOR
        SELECT BENIF.DNI,
               ' ' des_nom_cliente,
               BENIF.DES_CLIENTE AS DES_APE_CLIENTE,
               'N'  FLG_CREACION,
               NVL(BENIF.imp_linea_credito,0) LCREDITO,
               NVL(BENIF.estado,' ')   ESTADO,
               NVL(BENIF.num_poliza,' '),
               NVL(BENIF.num_plan,' ') ,
               NVL(BENIF.cod_asegurado,' ') ,
               NVL(BENIF.num_item,' ') ,
               NVL(BENIF.prt,' ') ,
               NVL(BENIF.num_contrato,' ') ,
               NVL(BENIF.tipo_seguro,' ') ,
               NVL(BENIF.COD_CLIENTE,' ') COD_CLIENTE
          FROM CON_BENEFICIARIO BENIF
         WHERE
              BENIF.FLG_ACTIVO = '1'
              AND BENIF.COD_CONVENIO = pCod_convenio
         AND TRIM(BENIF.DNI) = pNum_documento_id;

--   END IF;

    RETURN curBenif;
  END;

  FUNCTION BTLMF_F_EXISTE_BENIF(cCodGrupoCia_in   IN CHAR,
                                     cCodLocal_in      IN CHAR,
                                     cSecUsuLocal_in   IN CHAR,
                                     pCod_convenio     IN CHAR,
                                     pNum_documento_id IN VARCHAR2)
    RETURN CHAR IS

    existeBenifTemp char(1) := 'N';
--    tempDNI CON_TMP_BENIFICIARIO.DNI%TYPE;
--    DNI V_CON_BENEFICIARIO.DNI%TYPE;
      DNI CON_BENEFICIARIO.DNI%TYPE;


  BEGIN

    BEGIN

             SELECT BENIF.DNI
             INTO   DNI
             FROM   CON_BENEFICIARIO BENIF
            WHERE   BENIF.FLG_ACTIVO = '1'
              AND   BENIF.COD_CONVENIO = pCod_convenio
              AND   TRIM(BENIF.DNI) = pNum_documento_id;

      EXCEPTION
      WHEN NO_DATA_FOUND THEN
           DNI := NULL;
      END;

      IF DNI IS NOT NULL THEN
        existeBenifTemp := 'S';
      END IF;

     RETURN existeBenifTemp;
  END;

  FUNCTION BTLMF_F_CUR_OBT_TARJ(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cSecUsuLocal_in IN CHAR,
                                pCodBarra       IN VARCHAR2)
    RETURN FarmaCursor IS
    curBenif FarmaCursor;

  BEGIN

    OPEN curBenif FOR
      SELECT B.COD_BARRA, B.COD_CONVENIO, B.COD_CLIENTE
        FROM REL_CONVENIO_BARRA B

       WHERE B.COD_BARRA = pCodBarra;

    RETURN curBenif;
  END;

  FUNCTION BTLMF_F_CUR_OBT_CLIENTE(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cSecUsuLocal_in IN CHAR,
                                   cCodConvenio    IN CHAR,
                                   pCodCliente     IN VARCHAR2)
    RETURN FarmaCursor IS
    curBenif FarmaCursor;

  BEGIN

    OPEN curBenif FOR
      SELECT C.COD_CLIENTE,
             C.DNI AS DNI,
             C.DES_CLIENTE AS DES_NOM_CLIENTE,
             ' ' DES_APE_CLIENTE,
             NVL(C.imp_linea_credito,'0')  LCREDITO,
             NVL(C.estado,'A')  ESTADO,
             NVL(C.num_poliza,' '),
             NVL(C.num_plan,' ') ,
             NVL(C.cod_asegurado,' ') ,
             NVL(C.num_item,' ') ,
             NVL(C.prt,' ') ,
             NVL(C.num_contrato,' ') ,
             NVL(C.tipo_seguro,' ')
        FROM CON_BENEFICIARIO C
       WHERE C.COD_CLIENTE = pCodCliente
         AND C.COD_CONVENIO = cCodConvenio;

    RETURN curBenif;
  END;

  FUNCTION BTLMF_F_CUR_OBT_CONVENIO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecUsuLocal_in IN CHAR,
                                    pCodConvenio    IN CHAR)
    RETURN FarmaCursor IS
    curBenif FarmaCursor;

  BEGIN
    --ERIOS 16.12.2013 Convenio COMPETENCIA
    OPEN curBenif FOR
      SELECT m.cod_convenio ,
                m.des_convenio,
                NVL(m.cod_convenio_rel, '') AS cod_convenio_rel,
                NVL(m.flg_creacion_cliente, ' ') AS flg_creacion_cliente,
                m.COD_TIPO_CONVENIO,
                NVL(m.flg_valida_lincre_benef,'0') as FLG_VALIDA_LINCRE_BENEF,
                nvl(m.flg_data_rimac, ' ') flg_data_rimac,
                nvl(m.flg_imprime_importes, ' ') flg_imprime_importes,
                nvl(m.ind_vta_complementaria, ' ') ind_vta_complementaria,
                m.ruc,
                m.institucion,
                NVL(m.MTO_LINEA_CRED_BASE,0) MTO_LINEA_CRED_BASE,
                nvl(m.direccion,' ') DIRECCION
         FROM MAE_CONVENIO m
         WHERE m.flg_activo='1'
               and (m.flg_periodo_validez = '0' OR
               (m.flg_periodo_validez = '1' and sysdate between m.fch_inicio and
               nvl(m.fch_fin, sysdate + 1)))
               and m.flg_atencion_local='1'
               and (m.flg_atencion_todos_locales='1'
                   or (m.flg_atencion_todos_locales='0' and exists
                         (select 1 from rel_local_conv where cod_convenio=m.cod_convenio
                         and estado='A' and cod_grupo_cia=cCodGrupoCia_in and cod_local=cCodLocal_in))
                   or CONV_COMPETENCIA = pCodConvenio)
               and m.cod_convenio = pCodConvenio;


    RETURN curBenif;
  END;

  FUNCTION BTLMF_F_CHAR_IMPRIMIR(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cIpServ_in      IN CHAR,
                                 cCodConvenio_in IN CHAR,
                                 cDni            IN VARCHAR2) RETURN VARCHAR2 IS

    vMsg_out        varchar2(32767) := '';
    vMensajeLocal   varchar2(2200) := '';
    vFecha          varchar2(200) := '';
    vNombreConvenio varchar2(300);

  BEGIN

    SELECT CONV.DES_CONVENIO
      INTO vNombreConvenio
      FROM MAE_CONVENIO CONV
     WHERE CONV.COD_CONVENIO = cCodConvenio_in;

    select l.cod_local || '-' || l.desc_corta_local,
           to_char(sysdate, 'dd/mm/yyyy HH24:MI:SS')
      into vMensajeLocal, vFecha
      from pbl_local l
     where l.cod_grupo_cia = cCodGrupoCia_in
       and l.cod_local = cCodLocal_in;

    vMsg_out := '

     <table width="303" height="192" cellspacing="1" cellpadding="1" border="0" align="" summary="">
    <tbody>
        <tr>
            <td>
            <table width="293" height="128" cellspacing="1" cellpadding="1" border="0" align="" summary="">
                <tbody>
                    <tr>
                        <td align="center" style="font-family: Arial, Verdana,Sans-Serif" >
                        <h5><strong>' ||
                UPPER(vNombreConvenio) ||
                '</strong></h5>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-family: Arial, Verdana"><font size="1"><strong>Codigo
                        </strong></font></td>
                        <td style="font-family: Arial, Verdana"><font size="1">: ' ||
                cCodConvenio_in ||
                '</font></td>
                        <td style="font-family: Arial, Verdana"><font size="1"><strong>Fecha</strong></font></td>
                        <td style="font-family: Arial, Verdana"><font size="1">: ' ||
                vFecha ||
                '</font></td>
                    </tr>
                    <tr>
                        <td style="font-family: Arial,Verdana;"><font size="1"><strong>Local</strong></font></td>
                        <td colspan="3" style="font-family: Arial,Verdana;"><font size="1">: ' ||
                vMensajeLocal ||
                '</font></td>
                    </tr>
                    <tr>
                        <td style="font-family: Arial,Verdana;">;</td>
                        <td colspan="3" style="font-family: Arial,Verdana;">;</td>
                    </tr>
                    <tr>
                        <td colspan="4" style="font-family: Arial, Verdana"><h5>;;El benificiario con el Nro DNI ' || cDni ||
                ' no; existe.;</h5></td>
                    </tr>
                </tbody>
            </table>
            </td>
        </tr>
        <tr>
            <td>                          </td>
        </tr>
        <tr>
            <td>                          </td>
        </tr>
    </tbody>
</table>
     ';

    return vMsg_out;

  END;

  FUNCTION BTLMF_F_CHAR_IMPR_VOUCHER(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecUsuLocal_in IN CHAR,
                                    cIpServ_in      IN CHAR,
                                    cNroPedido_in   IN CHAR,
                                    vCodigoBarra_in IN VARCHAR2
                                    )
    RETURN FarmaCursor IS

    curvMsg_out FarmaCursor;

    vMensajeLocal   varchar2(2200) := '';
    vFecha          varchar2(2200) := '';
    vCodConvenio    VTA_PEDIDO_VTA_CAB.COD_CONVENIO%TYPE;
    vCodCliente     VTA_PEDIDO_VTA_CAB.COD_CLI_CONV%TYPE;
    vNombreConvenio MAE_CONVENIO.DES_CONVENIO%TYPE;
    vTipoCabecera   MAE_CONVENIO.TIPO_CABECERA%TYPE;
    vFlg_Rimac      MAE_CONVENIO.FLG_DATA_RIMAC%TYPE;

    vNombreBenif    varchar2(300) := '';
    vDni            varchar2(20) := '';

    vApellidoBenif         varchar2(300) := '';
    v_des_doc_verificacion varchar2(100) := '';
    curDoc                 FarmaCursor;
    curVta                 FarmaCursor;
    vListaDocumentos       varchar2(22767) := '';

    vMensaje_uno  varchar2(32767) := ' ';
    vMsj_vtas_voucher_uno  varchar2(32767) := ' ';
    vMsj_vtas_voucher_dos  varchar2(32767) := ' ';


    vMensaje_dos  varchar2(32767) := ' ';
    vMensaje_tres varchar2(32767) := ' ';
    vMensaje_cuatro varchar2(32767) := ' ';

    vNombCampo  CON_BTL_MF_PED_VTA.NOMBRE_CAMPO%TYPE;
    vDescCampo  CON_BTL_MF_PED_VTA.DESCRIPCION_CAMPO%TYPE;
    vFlgImprime CON_BTL_MF_PED_VTA.Flg_Imprime%TYPE;

    VAuxiliar varchar2(10) := ' ';
    vRuta varchar2(500);

    vTerminal varchar2(300);
    vVendedor varchar2(300);

    vDocBenif varchar(1000) := '';
    vDocBenifTemp varchar(1000) := '';

    vDocEmpre varchar(1000) := '';
    vDocEmpreTemp varchar(1000) := '';


    curDocBenif FarmaCursor;
    curDocEmpre FarmaCursor;

    vTipDocBenif MAE_CONVENIO.Cod_Tipdoc_Beneficiario%TYPE;
    vTipDocEmpre MAE_CONVENIO.COD_TIPDOC_CLIENTE%TYPE;
    vFechaEmisionBenif varchar2(400);
    vFechaEmisionEmpre varchar2(400);



    vFlgVoucherCredito   MAE_CONVENIO.FLG_VOUCHER_CREDITO%TYPE;
    vFlgVoucherCabecera  MAE_CONVENIO.FLG_VOUCHER_CABECERA%TYPE;
    vFlgVoucherDatoAseg  MAE_CONVENIO.FLG_VOUCHER_DATO_ASEG%TYPE;
    vFlgVoucherDatoRete  MAE_CONVENIO.FLG_VOUCHER_DATO_RETEN%TYPE;
    vFlgVoucherDatoFirma MAE_CONVENIO.FLG_VOUCHER_FIRMA%TYPE;
    vFlgCodigoBarra      MAE_CONVENIO.FLG_COD_BARRA%TYPE;

    vMontoTotalBenif     VTA_COMP_PAGO.VAL_NETO_COMP_PAGO%TYPE;
    vMontoTotalEmpre     VTA_COMP_PAGO.VAL_NETO_COMP_PAGO%TYPE;

    vMontoCreditoTotal   VTA_COMP_PAGO.VAL_NETO_COMP_PAGO%TYPE;

    vCodTipoConvenio     VTA_COMP_PAGO.Cod_Tipo_Convenio%TYPE;

    flaImprimeVoucher NUMBER;

    cCodCia_in pbl_local.cod_cia%type;
    v_vCabecera2 VARCHAR2(500);

    vFlgConvMixto MAE_CONVENIO.FLG_CONV_MIXTO%TYPE;
    vCantCopagoCredito   INTEGER := '0';

    -- KMONCADA 15.01.2015 VOUCHER PARA CONVENIOS COPAGO
    vCantidadCopago INTEGER :=0;
    vMensajePie VARCHAR2(100);
	v_cSeparador CHAR(1):= chr(92); --'\';
  BEGIN

    --ERIOS 18.10.2013 Imagenes
    select cod_cia into cCodCia_in
    from pbl_local
    where cod_grupo_cia = cCodGrupoCia_in
    and cod_local = cCodLocal_in;

     vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||v_cSeparador||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||v_cSeparador;
    v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||v_cSeparador||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||v_cSeparador||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);


    -- Obtenemos el codConvenio y codCliente

     BEGIN
        SELECT PED.COD_CONVENIO, PED.COD_CLI_CONV
          INTO vCodConvenio, vCodCliente
          FROM VTA_PEDIDO_VTA_CAB PED
         WHERE PED.cod_grupo_cia = cCodGrupoCia_in
           and PED.cod_local = cCodLocal_in
           and PED.NUM_PED_VTA = cNroPedido_in;

         EXCEPTION
          WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20000, 'No encontro datos del pedido. ' || cNroPedido_in);
     END;

   -- Obtenemos una lista de documentos a retener.
     curDoc := BTLMF_F_CHAR_OBTIENE_DATO(cCodGrupoCia_in,
                                        cCodLocal_in,
                                        '',
                                        vCodConvenio,
                                        FLG_RETENCION);


    -- Obtenemos el nombre del convenio
    BEGIN
      SELECT CONV.DES_CONVENIO,
             CONV.TIPO_CABECERA,
             CONV.FLG_DATA_RIMAC,
             Cod_Tipdoc_Beneficiario,
             COD_TIPDOC_CLIENTE,
             NVL(CONV.FLG_VOUCHER_CREDITO,'N'),
             NVL(CONV.FLG_VOUCHER_CABECERA,'N'),
             NVL(CONV.FLG_VOUCHER_DATO_ASEG,'N'),
             NVL(CONV.FLG_VOUCHER_DATO_RETEN,'N'),
             NVL(CONV.FLG_VOUCHER_FIRMA,'N'),
             NVL(CONV.FLG_COD_BARRA,'N'),
             NVL(CONV.FLG_CONV_MIXTO,'0')
        INTO vNombreConvenio, vTipoCabecera,vFlg_Rimac,vTipDocBenif,vTipDocEmpre,
             vFlgVoucherCredito,
             vFlgVoucherCabecera,
             vFlgVoucherDatoAseg,
             vFlgVoucherDatoRete,
             vFlgVoucherDatoFirma,
             vFlgCodigoBarra,
             vFlgConvMixto
        FROM MAE_CONVENIO CONV
       WHERE CONV.COD_CONVENIO = vCodConvenio;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20000, 'No encontro datos del convenio. ' || cNroPedido_in);
    END;

    -- Obtenemos datos del cliente normal.
    BEGIN
      select tt.descripcion_campo,tt.cod_valor_in
      into   vNombreBenif, vDni
      from   con_btl_mf_ped_vta tt
      where  cod_campo = 'D_000'
      and    tt.cod_grupo_cia = cCodGrupoCia_in
      and    tt.cod_local = cCodLocal_in
      and    tt.num_ped_vta = cNroPedido_in;
    EXCEPTION
       WHEN NO_DATA_FOUND THEN
        vNombreBenif := '';
                vDni := '';
          --RAISE_APPLICATION_ERROR(-20000, 'No encontro datos del benificiario. ' || cNroPedido_in);
    END;
   /*
   IF vFlg_Rimac = '0' OR vFlg_Rimac IS NULL THEN
    BEGIN
      SELECT CLI.DES_CLIENTE, CLI.DNI
        INTO vNombreBenif, vDni
        --FROM V_CON_BENEFICIARIO CLI
        from  con_beneficiario cli
       WHERE CLI.COD_CLIENTE = vCodCliente
         AND CLI.COD_CONVENIO = vCodConvenio;
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
        vNombreBenif := '';
                vDni := '';


          --RAISE_APPLICATION_ERROR(-20000, 'No encontro datos del benificiario. ' || cNroPedido_in);
    END;
   END IF;
   -- Obtenemos datos del cliente Rimac.

    IF vFlg_Rimac = '1' THEN
    BEGIN
      SELECT CLI.NOMBRES
        INTO vNombreBenif
        FROM V_CON_BENEFIC_RIMAC CLI
       WHERE CLI.COD_REFERENCIA = vCodCliente
         AND CLI.COD_CONVENIO   = vCodConvenio;
       EXCEPTION
       WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20000, 'No encontro datos del benificiario Rimac. ' || cNroPedido_in);
    END;
   END IF;*/

     -- Obtenemos el local y la fecha
     select l.cod_local || '-' || l.desc_corta_local || '',
            to_char(sysdate, 'dd/mm/yyyy HH24:MI:SS')
       into vMensajeLocal, vFecha
       from pbl_local l
      where l.cod_grupo_cia = cCodGrupoCia_in
        and l.cod_local = cCodLocal_in;


     --Obtenemos el tipo de convenio
         SELECT distinct P.COD_TIPO_CONVENIO
            INTO vCodTipoConvenio
            FROM VTA_COMP_PAGO P
           WHERE P.num_ped_vta = cNroPedido_in;


     -- Obtenemos los numeros de comprobantes del benificiario
     OPEN curDocBenif FOR
     -- LTAVARA 22.09.2014 MUESTRA COMPROBANTES ELECTRONICOS
         SELECT  T.COD_TIPODOC||' '|| DECODE(P.COD_TIP_PROC_PAGO,'1',FARMA_UTILITY.GET_T_COMPROBANTE_2(P.COD_TIP_PROC_PAGO,P.NUM_COMP_PAGO_E,P.NUM_COMP_PAGO),P.NUM_COMP_PAGO)|| ' S/.'||TO_CHAR(P.VAL_NETO_COMP_PAGO,'999,999,999.00') AS  DOCBENIF,
                 REPLACE(TO_CHAR(P.FEC_CREA_COMP_PAGO,'dd/MM/yyyy hh:mi'),' ','&nbsp;')  AS  FECEMISION
            FROM VTA_COMP_PAGO P,
                 MAE_TIPO_COMP_PAGO_BTLMF T
           WHERE P.num_ped_vta = cNroPedido_in
             AND T.TIP_COMP_PAGO = P.TIP_COMP_PAGO
             AND T.COD_TIPODOC = (CASE
                                   WHEN NVL(P.COD_TIP_PROC_PAGO,'0')='1' AND vTipDocBenif='TKB' THEN
                                     'BOL'
                                   ELSE vTipDocBenif
                                  END);
     LOOP
      FETCH curDocBenif
        INTO vDocBenifTemp, vFechaEmisionBenif;
        EXIT WHEN curDocBenif%NOTFOUND;
        vDocBenif := vDocBenifTemp||'<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'||vDocBenif;
     END LOOP;
     CLOSE curDocBenif;
     /*IF vDocBenif IS NOT NULL THEN
     vDocBenif := vTipDocBenif ||'-'|| vDocBenifTemp;
     END IF;*/

     -- Monto total del benificiario.
          SELECT SUM(P.VAL_NETO_COMP_PAGO)
            INTO vMontoTotalBenif
            FROM VTA_COMP_PAGO P
           WHERE P.num_ped_vta = cNroPedido_in
             AND P.TIP_CLIEN_CONVENIO = '1';

     -- Obtenemos los numeros de comprobantes de la empresa
     OPEN curDocEmpre FOR
     -- LTAVARA 22.09.2014 MUESTRA COMPROBANTES ELECTRONICOS
         SELECT  T.COD_TIPODOC||' '|| DECODE(P.COD_TIP_PROC_PAGO,'1',FARMA_UTILITY.GET_T_COMPROBANTE_2(P.COD_TIP_PROC_PAGO,P.NUM_COMP_PAGO_E,P.NUM_COMP_PAGO),P.NUM_COMP_PAGO)|| ' S/.'||TO_CHAR(P.VAL_NETO_COMP_PAGO,'999,999,999.00') AS  DOCBENIF,
                 REPLACE(TO_CHAR(SYSDATE,'dd/MM/yyyy hh:mi'),' ','&nbsp;')   AS  FECEMISION
            FROM VTA_COMP_PAGO P,
                 MAE_TIPO_COMP_PAGO_BTLMF T
           WHERE P.num_ped_vta = cNroPedido_in
             AND T.TIP_COMP_PAGO = P.TIP_COMP_PAGO
             AND T.COD_TIPODOC = (CASE
                                   WHEN NVL(P.COD_TIP_PROC_PAGO,'0')='1' AND vTipDocEmpre='TKB' THEN
                                     'BOL'
                                   ELSE vTipDocEmpre
                                  END);
     LOOP
           FETCH curDocEmpre
            INTO vDocEmpreTemp, vFechaEmisionEmpre;
            EXIT WHEN curDocEmpre%NOTFOUND;
            vDocEmpre := vDocEmpreTemp||'<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'||vDocEmpre;
     END LOOP;
     CLOSE curDocEmpre;
       /*IF vDocEmpre IS NOT NULL THEN
        vDocEmpre := vTipDocEmpre ||'-'|| vDocEmpreTemp;
       END IF;*/

        -- Monto total del empresa.
           SELECT SUM(P.VAL_NETO_COMP_PAGO)
            INTO vMontoTotalEmpre
            FROM VTA_COMP_PAGO P
           WHERE P.num_ped_vta = cNroPedido_in
             AND P.TIP_CLIEN_CONVENIO = '2';


     --Obtenemos el nombre de la PC
     SELECT SYS_CONTEXT('USERENV','TERMINAL')
       INTO vterminal
       FROM dual;

     -- KMONCADA 17.11.2014 SE MOSTRARA NOMBRE DEL VENDEDOR DEL PEDIDO
     --Obtenemos el nombre del vendedor.
     BEGIN
     SELECT USUARIO.NOM_USU||' '||USUARIO.APE_PAT||' '||USUARIO.APE_MAT
     INTO   vVendedor
     FROM VTA_PEDIDO_VTA_CAB CAB,
          PBL_USU_LOCAL USUARIO
     WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
     AND   CAB.COD_LOCAL     = cCodLocal_in
     AND   CAB.NUM_PED_VTA   = cNroPedido_in
     AND   CAB.COD_GRUPO_CIA = USUARIO.COD_GRUPO_CIA
     AND   CAB.COD_LOCAL     = USUARIO.COD_LOCAL
     AND   CAB.SEC_USU_LOCAL = USUARIO.SEC_USU_LOCAL;
     EXCEPTION
       WHEN NO_DATA_FOUND THEN
         SELECT USUARIO.NOM_USU || ' ' || USUARIO.APE_PAT || ' ' || USUARIO.APE_MAT
           INTO vVendedor
           FROM VTA_PEDIDO_VTA_CAB CAB, PBL_USU_LOCAL USUARIO
          WHERE CAB.COD_GRUPO_CIA        = cCodGrupoCia_in
            AND CAB.COD_LOCAL            = cCodLocal_in
            AND CAB.NUM_PED_VTA          = cNroPedido_in
            AND CAB.COD_GRUPO_CIA        = USUARIO.COD_GRUPO_CIA
            AND CAB.COD_LOCAL            = USUARIO.COD_LOCAL
            AND CAB.USU_CREA_PED_VTA_CAB = USUARIO.LOGIN_USU;
     END;

     SELECT count(1)
       into flaImprimeVoucher
       FROM MAE_CONVENIO MC
      WHERE MC.COD_TIPDOC_BENEFICIARIO = 'TKB'
        AND MC.COD_TIPDOC_CLIENTE IS NULL
        AND MC.PCT_BENEFICIARIO = 0
        AND MC.PCT_EMPRESA = 100
        AND MC.COD_CONVENIO = vCodConvenio;
    -- Creamos el formato del mensaje

    vMensajePie := 'Acepto pagar al emisor el importe <br> de la atencion anotado en este titulo';

    IF vFlgConvMixto = '1' AND flaImprimeVoucher = 0 THEN
       SELECT COUNT(1)
       INTO   vCantCopagoCredito
       FROM VTA_FORMA_PAGO_PEDIDO A
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND   A.COD_LOCAL     = cCodLocal_in
       AND   A.NUM_PED_VTA   = cNroPedido_in
       AND   A.COD_FORMA_PAGO = '00050';

       IF vCantCopagoCredito > 0 THEN
         flaImprimeVoucher := 1;
         BEGIN
           SELECT tt.descripcion_campo,' '
           INTO   vNombreBenif, vDni
           from   con_btl_mf_ped_vta tt
           where  cod_campo = '0000000011'
           and    tt.cod_grupo_cia = cCodGrupoCia_in
           and    tt.cod_local = cCodLocal_in
           and    tt.num_ped_vta = cNroPedido_in;
         EXCEPTION
           WHEN NO_DATA_FOUND THEN
             vNombreBenif := '';
             vDni := '';
         END;
       END IF;
    END IF;

    IF flaImprimeVoucher = 0 THEN
      SELECT COUNT(DISTINCT(A.TIP_COMP_PAGO))
      INTO vCantidadCopago
      FROM VTA_COMP_PAGO A
      WHERE A.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND A.COD_LOCAL        = cCodLocal_in
      AND A.NUM_PED_VTA      = cNroPedido_in
      AND A.TIP_COMP_PAGO   != COD_TIP_COMP_GUIA
      AND A.PCT_BENEFICIARIO > 0
      AND A.PCT_EMPRESA      > 0;

      IF vCantidadCopago > 1 THEN
        flaImprimeVoucher := 1;
        vMensajePie := 'Acepto haber recibido los productos<br> indicados en los comprobantes';
      END IF;

    END IF;

    IF flaImprimeVoucher > 0 THEN

        -- vMensaje_uno := '<table width="274" height="229" cellspacing="1" cellpadding="1" border="0" align="" summary="">';
        vMensaje_uno := '<html><head></head><body><table border="0" >';
        vMensaje_uno := vMensaje_uno || '<tbody>';
        vMensaje_uno := vMensaje_uno || ' <tr>';
        vMensaje_uno := vMensaje_uno || '  <td>';
        vMensaje_uno := vMensaje_uno ||
                        --'   <table width="281" height="45" cellspacing="1" cellpadding="1" border="0" align="" summary="">';
                        '   <table border="0" >';
        vMensaje_uno := vMensaje_uno || '     <tbody>';

        vMensaje_uno := vMensaje_uno  || '<tr>' ||
                        '<td height="50"  align="center">'||
                        '<div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="222" height="70" class="style3"></div></td>'||
                         '</tr>';
        vMensaje_uno := vMensaje_uno || '<tr>';
        vMensaje_uno := vMensaje_uno || ' <td  align="center"><h3><strong>VENTA&nbsp;&nbsp;CONVENIO</strong></h3></td>';
        vMensaje_uno := vMensaje_uno || '</tr>';

        vMensaje_dos := vMensaje_dos ||'<tr>';
        vMensaje_dos := vMensaje_dos ||'    <td style="font-family: Arial, Verdana" > ';
        vMensaje_dos := vMensaje_dos ||'        <font size="1"><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Terminal&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;'||vterminal|| '';
        vMensaje_dos := vMensaje_dos ||'                       <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vendedor&nbsp;&nbsp;&nbsp;:&nbsp;'||vVendedor|| '';
        vMensaje_dos := vMensaje_dos ||'                       <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Convenio&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;'||
                                                               REPLACE(
                                                               F_CHAR_SALTO_LINEAS( vTexto_in            => vNombreConvenio,
                                                                                    vCantCaractLinea1_in => 30,
                                                                                    vCantCaractLinea2_in => 40,
                                                                                    vSeparador_in        => '<br>'),
                                                                                    ' ','&nbsp;')
                                                               || '';
        vMensaje_dos := vMensaje_dos ||'                       <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;F.&nbsp;Emision&nbsp;&nbsp;:&nbsp;'||NVL(vFechaEmisionBenif,vFechaEmisionEmpre)|| '';
        vMensaje_dos := vMensaje_dos ||'                       <BR>'||'' || '</font>';
        vMensaje_dos := vMensaje_dos ||'    </td>';
        vMensaje_dos := vMensaje_dos ||'</tr>';

        --CODIGO BARRA
         vMensaje_cuatro := vMensaje_cuatro  || '<tr>' ||
                        '<td height="50" >'||
                        '<div align="center" class="style8">'||
                          '<<codigoBarra>></div></td>'||
                         '</tr>';

         SELECT SUM(COMP.VAL_NETO_COMP_PAGO)
           INTO vMontoCreditoTotal
           FROM VTA_COMP_PAGO COMP
          WHERE COMP.NUM_PED_VTA =cNroPedido_in
            AND COMP.Cod_Tipo_Convenio   = 3;

          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'<tr>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td align = "center" style="font-family: Arial, Verdana" ><font size="1"></font></td>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'</tr>';


          --MONTO CREDITO
           IF vCodTipoConvenio = '1' THEN
              vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'<tr>';
              vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td align = "left" style="font-family: Arial, Verdana" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="3"><strong>'||vDocBenif||/*'  S/.'||TO_CHAR(vMontoTotalBenif,'99,990.00')||*/'</strong></font></td>';
              vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'</tr>';
              --ERIOS 05.12.2014 Truco para que los convenios mixto, no imprima documentos de empresa.
              IF vFlgConvMixto = '0' THEN
              vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'<tr>';
              vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td align = "left" style="font-family: Arial, Verdana" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="3"><strong>'||vDocEmpre||/*'  S/.'||TO_CHAR(vMontoTotalEmpre,'99,990.00')||*/'</strong></font></td>';
              vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'</tr>';
              END IF;
           ELSE
                 IF vCodTipoConvenio = '3' THEN

                    IF vDocBenif IS NOT NULL THEN
                       vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'<tr>';
                       vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td align = "left" style="font-family: Arial, Verdana" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4"><strong>'||vDocBenif||/*'  S/.'||TO_CHAR(vMontoTotalBenif,'99,990.00')||*/'</strong></font></td>';
                       vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'</tr>';
                    END IF;
                    IF vDocEmpre IS NOT NULL THEN
                       vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'<tr>';
                       vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td align = "left" style="font-family: Arial, Verdana" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4"><strong>'||vDocEmpre||/*'  S/.'||TO_CHAR(vMontoTotalEmpre,'99,990.00')||*/'</strong></font></td>';
                       vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'</tr>';
                    END IF;

                END IF;
           END IF;

          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'<tr>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td align = "center" style="font-family: Arial, Verdana" ><font size="1"></font></td>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'</tr>';

          --FIRMA
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'<tr>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td style="font-family: Arial, Verdana" ><font size="1">';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'        <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Firma&nbsp;&nbsp;&nbsp;:&nbsp;'||'_____________________'||'';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'        <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nombre&nbsp;&nbsp;:&nbsp;'||vNombreBenif||'';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'        <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DNI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;'||vDni||'';
--          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'        <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Telefono:&nbsp;'||'1445454'|| '';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'        <BR>'||''|| '</font>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    </td>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'</tr>';

          --Documentos a retener
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '  <tr>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||
                           '   <td align = "center" style="font-family: Arial, Verdana"><strong><h5>*** Documentos Sin Valor Fiscal ***</h5></strong></td>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '  </tr>';

          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '      </tbody>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '    </table>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '   </td>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '  </tr>';

          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'<tr>';
--          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td align = "center" style="font-family: Arial, Verdana" ><h5>Acepto pagar al emisor el importe <br> de la atencion anotado en este titulo</h5></td>';
          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'    <td align = "center" style="font-family: Arial, Verdana" ><h5>'||vMensajePie||'</h5></td>';

          vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos ||'</tr>';

        vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '</tbody>';
        vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '</table>';
        vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || ' </td>';
        vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '</tr>';
        vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '</tbody>';
        vMsj_vtas_voucher_dos := vMsj_vtas_voucher_dos || '</table></body></html>';

  END IF;

    OPEN curvMsg_out FOR
      SELECT
             vMensaje_uno          MESAJEHTML_UNO,
             vMsj_vtas_voucher_uno MESAJEHTML_VTA_UNO,
             vMensaje_dos          MESAJEHTML_DOS,
             vMensaje_tres         MESAJEHTML_TRES,
             vMensaje_cuatro       MESAJEHTML_CUATRO,
             vMsj_vtas_voucher_dos MESAJEHTML_VTA_DOS
        FROM DUAL;

    RETURN curvMsg_out;
  END;

  PROCEDURE BTLMF_P_INSERT_PED_VTA(cCodGrupoCia_in      IN CHAR,
                                   cCodLocal_in         IN CHAR,
                                   pNumPedVta_in        IN CHAR,
                                   PCodCampo_in         IN CHAR,
                                   pCodConvenio_in      IN CHAR,
                                   pCodCliente_in       IN CHAR,
                                   pUsCreaPedVtaCli_in  IN VARCHAR2,
                                   PDescripcionCampo_in IN VARCHAR2,
                                   PNombreCampo_in      IN VARCHAR2,
                                   pCodVaor_In          IN CHAR
                                   )

  AS


  vFlagImprime rel_convenio_tipo_campo.flg_imprime%type;


  BEGIN


   BEGIN
       SELECT r.flg_imprime
         INTO vFlagImprime
         FROM rel_convenio_tipo_campo r
        WHERE r.cod_tipo_campo = PCodCampo_in
          AND r.cod_convenio   = pCodConvenio_in;

        EXCEPTION
        WHEN NO_DATA_FOUND THEN
              vFlagImprime := NULL;
    END;

    insert into CON_BTL_MF_PED_VTA
      (COD_GRUPO_CIA,
       COD_LOCAL,
       NUM_PED_VTA,
       COD_CAMPO,
       COD_CONVENIO,
       COD_CLIENTE,
       FEC_CREA_PED_VTA_CLI,
       USU_CREA_PED_VTA_CLI,
       DESCRIPCION_CAMPO,
       NOMBRE_CAMPO,
       FLG_IMPRIME,
       COD_VALOR_IN
       )
    values
      (cCodGrupoCia_in,
       cCodLocal_in,
       pNumPedVta_in,
       PCodCampo_in,
       pCodConvenio_in,
       pCodCliente_in,
       SYSDATE,
       pUsCreaPedVtaCli_in,
       PDescripcionCampo_in,
       PNombreCampo_in,
       vFlagImprime,
       pCodVaor_In
       );

    COMMIT;

  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20999,
                              'ERROR AL INSERTAR BTLMF_P_INSERT_PED_VTA.' ||
                              SQLERRM);
      ROLLBACK;

  END BTLMF_P_INSERT_PED_VTA;

  FUNCTION BTLMF_F_CUR_LISTA_PED_VTA(vCodGrupoCia_in IN CHAR,
                                     vCodLocal_in    IN CHAR,
                                     vNroPedVta_in   IN CHAR)
    RETURN FarmaCursor IS
    curVta FarmaCursor;

  BEGIN
  --ERIOS 2.4.5 Solo imprime datos adicioanles
    OPEN curVta FOR
      SELECT p.nombre_campo        NOMBRE_CAMPO,
             p.descripcion_campo   DESCRIPCION_CAMPO,
             --CASE WHEN LENGTH(P.COD_CAMPO) = 10 THEN
              nvl(p.flg_imprime,'0') FLG_IMPRIME,
             --ELSE '2'  END FLG_IMPRIME,
             p.cod_campo COD_CAMPO,
       p.COD_VALOR_IN
        FROM CON_BTL_MF_PED_VTA p
       WHERE p.cod_grupo_cia = vCodGrupoCia_in
         AND P.COD_LOCAL = vCodLocal_in
         AND p.num_ped_vta = vNroPedVta_in
     --AND p.flg_imprime = '1'
       order by p.cod_campo;


    RETURN curVta;

  END;

  FUNCTION BTLMF_F_OBTIENE_NVO_PRECIO(cCodGrupoCia_in CHAR,
                                      cCodLocal_in    CHAR,
                                      cCodConv_in     CHAR,
                                      cCodProd_in     CHAR,
                                      nValPrecVta_in  NUMBER DEFAULT NULL)
    RETURN CHAR IS
    V_NVO_PRECIO          number(8, 3);
    V_PORC_DCTO_CONV      NUMBER(5, 2);
    V_PORC_DCTO_CONV_LAB  NUMBER(5, 2);
    V_PORC_DCTO_CONV_PROD NUMBER(5, 2);
    v_count_prod          NUMBER;
    v_count_lab           NUMBER;

    vConvExistLocal NUMBER(1);
    --vIndTipoLista      CHAR(1);
    vContLstExcluye NUMBER(2);

    v_prec_vta NUMBER(8, 3);

    v_indComp CHAR(1);
    valComp   NUMBER(1) := 1;

    -- 2010-06-23 JOLIVA: Variable que indica si el convenio aplica a todo el maestro de productos
    v_indMaeProd CON_MAE_CONVENIO.IND_MAE_PROD%TYPE;

    -- 2011-12-23 JQUISPE: Variables para nuevos convenios de BTL
  BEGIN
    -------------------------
    --- Nueva Logica
    -------------------------
    --- Nuevo metodo que calcula el precio de venta de convenio
    --- No se usan tablas de conv. de mifarma
    --- Se verifica la tabla de precios por ccnvenios , V_CON_PREC_VTA_CONV
    --- Los estados son 3 posibles para obtener el prec. de esa tabla
    --- Si es 'E' se vende a precio publico,En Btl No se Vende.
    --- Si es 'I' se lee el campo de precio de conv. de la tabla de convenios.
    --- Si es 'N' se vende a precio publico

    --BEGIN

    V_NVO_PRECIO := 0;

    BEGIN

      V_NVO_PRECIO := pkg_producto.fn_precio('10',
                                             cCodLocal_in,
                                             cCodProd_in,
                                             '002',
                                             cCodConv_in);

    EXCEPTION
      WHEN OTHERS THEN

        SELECT L.VAL_PREC_VTA
          INTO V_NVO_PRECIO
          FROM LGT_PROD_LOCAL L
         WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
           AND L.COD_LOCAL = cCodLocal_in
           AND L.COD_PROD = cCodProd_in;

    END;

    RETURN TRIM(TO_CHAR(V_NVO_PRECIO, '99999.999'));

  END;

  FUNCTION F_GET_IND_EXCLUIDO_CONV(cCodGrupoCia_in in char,
                                   cCodConvenio_in in char,
                                   cCodProd_in     in char) RETURN CHAR IS
    IND_EXCLUIDO CHAR(1);
    NCOUNTE      NUMBER;
    NCOUNTI      NUMBER;

  BEGIN

      IND_EXCLUIDO := 'E';

      begin
        IND_EXCLUIDO := pkg_producto.FN_DEV_APTO_CONV(cCodProd_in,
                                                      cCodConvenio_in);
      exception
        when others then
          IND_EXCLUIDO := 'E';
      end;

      return IND_EXCLUIDO;

    END;

    FUNCTION BTLMF_F_CHAR_ES_ACTIVO_CONV(vCodigo_in IN varchar2)
      RETURN CHAR IS llaveTblGeneral char(1);

    BEGIN
      select t.llave_tab_gral
        into llaveTblGeneral
        from pbl_tab_gral t
       where t. id_tab_gral = vCodigo_in;

      RETURN llaveTblGeneral;
    END;

    FUNCTION BTLMF_F_GET_COPAGO_CONV(cCodConvenio_in IN CHAR)

      RETURN CHAR

    IS v_porc_copago number(8, 2);
    v_escredito char(1);
    BEGIN

      SELECT PORC_COPAGO_CONV
        INTO v_porc_copago
        FROM con_mae_convenio
       WHERE COD_CONVENIO = cCodConvenio_in;

      if v_porc_copago = 100 then

        v_escredito := 'S';

      else

        v_escredito := 'N';

      end if;

      RETURN v_escredito;
    END;

    FUNCTION BTLMF_F_CUR_LIST_FORM_PAG_CONV(cCodGrupoCia_in CHAR,
                                            cCodLocal_in CHAR,
                                            cSecUsuLocal_in CHAR,
                                            vCodConvenio_in CHAR)
      RETURN FarmaCursor IS curConv FarmaCursor;
    --flgDataRimac CHAR(1);
   -- V_TIPO_PAGO MAE_CONVENIO.FLG_TIPO_PAGO%TYPE;
     V_CONTEO INTEGER;

    BEGIN

    --SELECT CC.FLG_TIPO_PAGO INTO V_TIPO_PAGO FROM MAE_CONVENIO CC WHERE COD_CONVENIO =vCodConvenio_in;

     --IF V_TIPO_PAGO='0' THEN
     --     OPEN curConv FOR
     --     SELECT F.cod_forma_pago || 'Ã' || F.desc_corta_forma_pago || 'Ã' || ' ' ||'Ã' || f.ind_tarj
     --     FROM vta_forma_pago f
     --     WHERE f.est_forma_pago = 'A';
     --ELSE
         SELECT COUNT(1) INTO V_CONTEO FROM rel_forma_pago_conv WHERE cod_convenio  = vCodConvenio_in;

         IF V_CONTEO=0 THEN
            OPEN curConv FOR
            SELECT f.cod_forma_pago || 'Ã' || f.desc_corta_forma_pago || 'Ã' || ' ' ||'Ã' || f.ind_tarj
            ||'Ã' || '0'
            FROM vta_forma_pago f
            WHERE f.Est_Forma_Pago = 'A';
         ELSE
            OPEN curConv FOR
            SELECT fp.cod_forma_pago || 'Ã' || fp.desc_corta_forma_pago || 'Ã' || ' ' ||'Ã' || f.ind_tarj
             ||'Ã' || '0'
            FROM rel_forma_pago_conv fp,vta_forma_pago f
            WHERE fp.cod_convenio  = vCodConvenio_in
             AND f.cod_forma_pago = fp.cod_forma_pago
             AND fp.estado = 'A' AND f.Est_Forma_Pago = 'A';
         END IF;
     --END IF;




      RETURN curConv;
    END;

  FUNCTION BTLMF_F_CHAR_OBT_PRECIO_CONV( cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cCodConvenio_in IN CHAR,
                                         cCodProducto_in IN CHAR )
  RETURN CHAR
  IS
    v_precio lgt_prod_local.val_prec_vta%type;
  BEGIN
    select round((pkg_producto.fn_precio('10',
                               lp.cod_local,
                               lp.cod_prod,
                               '002',
                               cCodConvenio_in) /
           decode(lp.ind_prod_fraccionado,
                   'S',
                   (select p.val_frac_vta_sug
                      from lgt_prod p
                     where p.cod_grupo_cia = lp.cod_grupo_cia
                       and p.cod_prod = lp.cod_prod),
                   lp.val_frac_local)), 3)
      into v_precio
      from lgt_prod_local lp
     where lp.cod_grupo_cia = cCodGrupoCia_in
       and lp.cod_local = cCodLocal_in
       and lp.cod_prod = cCodProducto_in;

    --RETURN v_precio;
    RETURN TRIM(TO_CHAR(v_precio,'999,999.990'));
  EXCEPTION
    WHEN OTHERS THEN
         RETURN 0;
  END;


FUNCTION BTLMF_F_CUR_LISTA_PRECIO_CONV(vCodGrupoCia_in in char, vCodConvenio_in in char,vCodLocal_in in char) RETURN FarmaCursor AS

CURS_PRECIOS FarmaCursor;

BEGIN

OPEN CURS_PRECIOS FOR


-- DJARA 07.04.2014
select lp.cod_prod || 'Ã' ||
       to_char((pkg_producto.fn_precio('10',
                               lp.cod_local,
                               lp.cod_prod,
                               '002',
                               vCodConvenio_in) /
       decode(lp.ind_prod_fraccionado,
               'S',
               (select p.val_frac_vta_sug
                  from lgt_prod p
                 where p.cod_grupo_cia = lp.cod_grupo_cia
                   and p.cod_prod = lp.cod_prod),
               lp.val_frac_local)), '999999.99') || 'Ã' ||
       'I' estado
  from lgt_prod_local lp
 where lp.cod_grupo_cia = vCodGrupoCia_in
   and lp.cod_local = vCodLocal_in
   and lp.est_prod_loc = 'A'
   and pkg_producto.fn_dev_apto_conv(lp.cod_prod, vCodConvenio_in) = 'I'
   and exists (select 1
          from lgt_prod p
         where p.cod_grupo_cia = lp.cod_grupo_cia
           and p.cod_prod = lp.cod_prod
           and p.est_prod = 'A')
 order by lp.cod_prod asc;


RETURN CURS_PRECIOS;

END;


FUNCTION BTLMF_F_CHAR_COBRA_PEDIDO(
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
                                   cDescDetalleForPago_in IN CHAR DEFAULT ' ',
                                   cFlagElectronico in CHAR DEFAULT '0'--LTAVARA 27.08.14 10:00 FLAG PROCESO ELECTRONICO
                                   )

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

 -- rherrera 24.10.2014
 --- pago boleta
 v_tip_pago VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE;

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

  vTip_Ped_vta  CHAR(2);

    v_nCopago FLOAT;
    v_nPctBenef mae_convenio.pct_beneficiario%TYPE;
    v_nPctEmp      mae_convenio.pct_empresa%TYPE;

     ------------------
 nIsTipCompDOS   char(1);
 nExisteGuiaCERO number;
 ------------------
 -- KMONCADA 21.04.2016 PERCEPCION
 -- KMONCADA 14.06.2016 SE ASIGNA VALOR POR DEFECTO
  vAplicaPercepcion CHAR(1) := 'N';
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
          --CONV.NUM_DOC_FLG_KDX,
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

  SELECT VTA_CAB.TIP_PED_VTA
    INTO vTip_Ped_vta
    FROM VTA_PEDIDO_VTA_CAB VTA_CAB
   WHERE VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
     AND VTA_CAB.COD_LOCAL = cCodLocal_in
     AND VTA_CAB.NUM_PED_VTA = cNumPedVta_in;

  -- KMONCADA 17.11.2014 SOLO APLICA PARA VENTA EMPRESA
  IF vTip_Ped_vta = '03' THEN
     --rherrera:  asigna tipo boleta
    select c.tip_comp_pago
      into v_tip_pago
      from vta_pedido_vta_cab c

     where c.cod_grupo_cia = cCodGrupoCia_in
       and c.cod_local = cCodLocal_in
       and c.num_ped_vta = cNumPedVta_in;
    if v_tip_pago = '01' then
      v_TipDocClienteMF := v_tip_pago;
    end if;
  END IF;

  -- KMONCADA 24.10.2014 REALIZA LA ACTUALIZACION EN TKF --> FAC O TKB --> BOL (FACTURACION ELECTRONICA)

  IF cFlagElectronico = '1' THEN
    v_TipDocBenificiarioMF := CASE
                                 WHEN v_TipDocBenificiarioMF = COD_TIP_COMP_TICKET THEN COD_TIP_COMP_BOLETA
                                 WHEN v_TipDocBenificiarioMF = COD_TIP_COMP_TICKET_FA THEN COD_TIP_COMP_FACTURA
                                 ELSE v_TipDocBenificiarioMF END;
    v_TipDocClienteMF :=      CASE
                                 WHEN v_TipDocClienteMF = COD_TIP_COMP_TICKET THEN COD_TIP_COMP_BOLETA
                                 WHEN v_TipDocClienteMF = COD_TIP_COMP_TICKET_FA THEN COD_TIP_COMP_FACTURA
                                 ELSE v_TipDocClienteMF END;
   END IF;

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

  --valida relacion caja impresora cuando es ticket o boleta para el comprobante
  --del benfi
  /*
  if v_TipDocBenificiarioMF IS   NOT NULL AND (v_TipDocBenificiarioMF = COD_TIP_COMP_TICKET or v_TipDocBenificiarioMF = COD_TIP_COMP_BOLETA) then

    --Se valida el tipo de impresora que tiene asiganda la ip
    SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO cIP FROM DUAL;

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
               AND TRIM(A.IP) = TRIM(cIP));

    --Si el tipo de comprobante del usuario que cobra es distinto al lo que se definio en convenio.
    IF (TipCompAux <> v_TipDocBenificiarioMF)
     THEN RAISE_APPLICATION_ERROR(-20021, 'El tipo de comprobante relacionado a la caja actual es diferente a lo que se definio en convenio.');
    END IF;
  end if;*/

   --dbms_output.put_line('v_cIndGraboComp: ' || v_cIndGraboComp );
  SELECT VTA_CAB.VAL_REDONDEO_PED_VTA,
         VTA_CAB.COD_CLI_LOCAL,
         NVL(VTA_CAB.NOM_CLI_PED_VTA, ' '),
         NVL(VTA_CAB.RUC_CLI_PED_VTA, ' '),
         NVL(VTA_CAB.DIR_CLI_PED_VTA, ' '),
         VTA_CAB.IND_DISTR_GRATUITA,
         VTA_CAB.IND_DELIV_AUTOMATICO,
     VTA_CAB.TIP_PED_VTA
    INTO v_nValRedondeo,
         v_cCodCliLocal,
         v_cNomCliPedVta,
         v_cRucCliPedVta,
         v_cDirCliPedVta,
         v_cIndDistrGratuita,
         v_cIndDelivAutomatico,
     vTip_Ped_vta
    FROM VTA_PEDIDO_VTA_CAB VTA_CAB
   WHERE VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
     AND VTA_CAB.COD_LOCAL = cCodLocal_in
     AND VTA_CAB.NUM_PED_VTA = cNumPedVta_in;

   IF cFlagElectronico = FAC_ELEC_ACTIVO AND NOT(NVL(v_TipDocBenificiarioMF,'*') = COD_TIP_COMP_GUIA OR NVL(v_TipDocClienteMF,'*') = COD_TIP_COMP_GUIA) THEN
     SELECT LLAVE_TAB_GRAL
     INTO   V_NUM_PRODUCTOS
     FROM   PBL_TAB_GRAL
     WHERE  ID_TAB_GRAL = COD_CANT_LINEAS_FE;
   ELSE
     IF v_TipDocBenificiarioMF IS NOT NULL THEN

         IF v_TipDocBenificiarioMF=COD_TIP_COMP_GUIA THEN
            BEGIN
             --ERIOS 20.12.2013 2:Formato mediano (FASA)
                 SELECT DECODE(ID_TAB_GRAL ,COD_FORMATO_GUIA_CHICA,'0',379,'2','1') INTO V_COD_FORMATO_GUIA
                 FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL IN (279,280,379) AND EST_TAB_GRAL='A'
           --AND COD_CIA = (select cod_cia from pbl_local where cod_grupo_cia = cCodGrupoCia_in and cod_local = cCodLocal_in)
           ;
            EXCEPTION
                     WHEN NO_DATA_FOUND THEN
                          RAISE_APPLICATION_ERROR(-20021,'Falta Configurar formato de Guia');
                     WHEN OTHERS THEN
                          RAISE_APPLICATION_ERROR(-20021,'Error en la Configuracion de Tamaño de Guia');
            END;

            BEGIN
                  SELECT T.NUM_PRODUCTOS
                  INTO V_NUM_PRODUCTOS
                  FROM MAE_COMP_CONV_LINEAS T
                  WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND T.TIP_COMP      = v_TipDocBenificiarioMF
                  AND T.COD_FORMATO=V_COD_FORMATO_GUIA;
             EXCEPTION
                       WHEN NO_DATA_FOUND THEN
                            RAISE_APPLICATION_ERROR(-20021,'Falta Configurar Tamaño de Productos que soporta el Documento');
             END;
       ELSIF v_TipDocBenificiarioMF = COD_TIP_COMP_TICKET AND v_TipDocClienteMF IS NULL then
        --ERIOS 2.4.4 Si el convenio emite solo ticket, imprime un solo documeto.
        V_NUM_PRODUCTOS := 0;
         ELSE
             BEGIN
                  SELECT T.NUM_PRODUCTOS
                         INTO V_NUM_PRODUCTOS
                  FROM MAE_COMP_CONV_LINEAS T
                  WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND T.TIP_COMP      = v_TipDocBenificiarioMF;
             EXCEPTION
                       WHEN NO_DATA_FOUND THEN
                            RAISE_APPLICATION_ERROR(-20021,'Falta Configurar Tamaño de Productos que soporta el Documento:');
             END;
         END IF;
      END IF;
   END IF;
   IF vTip_Ped_vta = '03' THEN
     V_NUM_PRODUCTOS := TO_NUMBER(PTOVENTA_CAJ.CAJ_F_VAR_LINEA_DOC('232'));
   END IF;

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


           IF  v_Flg_Tipo_Convenio = FLG_TIP_CONV_COPAGO THEN


      --ERIOS 2.4.6 Calculo por escala
             v_nCopago := FN_CALCULA_COPAGO(V_COD_CONVENIO,vMontoNeto,v_nPctBenef,v_nPctEmp);
             IF v_nCopago <> -1 THEN
               V_PCT_BENEFICIARIO := v_nPctBenef;
               V_PCT_EMPRESA := v_nPctEmp;
               -- KMONCADA 26.12.2014 CUANDO DOCUMENTO EMPRESA MONTO IGUAL A 0 NO GENERAR DOCUMENTO
               IF V_PCT_EMPRESA = 0 AND v_TipDocClienteMF != COD_TIP_COMP_GUIA THEN
                 v_TipDocClienteMF := NULL;
                 v_FlgKardex := MUEVE_KARDEX_BENEFICIARIO;
               END IF;
             END IF;
         END IF;

      END;

      IF V_FLG_POLITICA='0' THEN
         IF TO_NUMBER(cPorCopago,'99999.00')>0 THEN
             V_PCT_BENEFICIARIO:=TO_NUMBER(cPorCopago,'99999.00');
             V_PCT_EMPRESA     :=100-TO_NUMBER(cPorCopago,'99999.00');
         END IF;
      END IF;



 -----------------------Grabamos el comprobante de pago del Benificiario-----------
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

      -- KMONCADA 22.04.2016 PERCEPCION
      IF FARMA_UTILITY.F_GET_LOCAL_APLICA_PERCEPCION(cCodGrupoCia_in, cCodLocal_in) = 'S' THEN
         SELECT DECODE(SUM(NVL(D.VAL_MONTO_PERCEPCION,0)),0,'N','S')
         INTO vAplicaPercepcion
         FROM VTA_PEDIDO_VTA_DET D
         WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
         AND D.COD_LOCAL = cCodLocal_in
         AND D.NUM_PED_VTA = cNumPedVta_in;
      END IF;

      BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
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
                                     'S',
                                     vIndGeneraUnSoloComproBenif,
                                     cFlagElectronico,--LTAVARA 27.08.14 10:00 FLAG PROCESO ELECTRONICO
                                     vAplicaPercepcion
                                     );
      -- KMONCADA 22.04.2016 PERCEPCION
      IF vAplicaPercepcion = 'S' THEN
        BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
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
                                     'S',
                                     vIndGeneraUnSoloComproBenif,
                                     cFlagElectronico,--LTAVARA 27.08.14 10:00 FLAG PROCESO ELECTRONICO
                                     vAplicaPercepcion,
                                     'S'
                                     );
      END IF;

      BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
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
                                     'N',
                                     vIndGeneraUnSoloComproBenif,
                                     cFlagElectronico--LTAVARA 27.08.14 10:00 FLAG PROCESO ELECTRONICO
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
               BTLMF_P_ACTUALIZA_STK_PROD(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in,
                                          cCodMotKardex_in,
                                          cTipDocKardex_in,
                                          cCodNumeraKardex_in,
                                          cUsuCreaCompPago_in,
                                          v_TipDocBenificiarioMF,
                                          vNumCompPago
                                         );
                      --INI ASOSA - 17/10/2014 - PANHD
                      PTOVTA_RESPALDO_STK.CAJ_UPD_STK_PROD_COMP(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in,
                                          cCodMotKardex_in,
                                          cTipDocKardex_in,
                                          cCodNumeraKardex_in,
                                          cUsuCreaCompPago_in,
                                          v_TipDocBenificiarioMF,
                                          vNumCompPago
                                         );
                      --FIN ASOSA - 17/10/2014 - PANHD

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

   IF cFlagElectronico = FAC_ELEC_ACTIVO AND NOT(NVL(v_TipDocBenificiarioMF,'*') = COD_TIP_COMP_GUIA OR NVL(v_TipDocClienteMF,'*') = COD_TIP_COMP_GUIA) THEN
     SELECT LLAVE_TAB_GRAL
     INTO   V_NUM_PRODUCTOS
     FROM   PBL_TAB_GRAL
     WHERE  ID_TAB_GRAL = COD_CANT_LINEAS_FE;
   ELSE
     IF v_TipDocClienteMF IS NOT NULL THEN
         IF v_TipDocClienteMF=COD_TIP_COMP_GUIA THEN
          BEGIN
        --ERIOS 20.12.2013 2:Formato mediano (FASA)
               SELECT DECODE(ID_TAB_GRAL ,COD_FORMATO_GUIA_CHICA,'0',379,'2','1') INTO V_COD_FORMATO_GUIA
               FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL IN (279,280,379) AND EST_TAB_GRAL='A'
         --AND COD_CIA = (select cod_cia from pbl_local where cod_grupo_cia = cCodGrupoCia_in and cod_local = cCodLocal_in)
         ;
          EXCEPTION
                   WHEN NO_DATA_FOUND THEN
                        RAISE_APPLICATION_ERROR(-20021,'Falta Configurar formato de Guia');
                   WHEN OTHERS THEN
                        RAISE_APPLICATION_ERROR(-20021,'Error en la Configuracion de Tamaño de Guia');
          END;

          BEGIN
                SELECT T.NUM_PRODUCTOS
                INTO V_NUM_PRODUCTOS
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
                INTO V_NUM_PRODUCTOS
                FROM MAE_COMP_CONV_LINEAS T
                WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
                AND T.TIP_COMP      = v_TipDocClienteMF;
           EXCEPTION
                     WHEN NO_DATA_FOUND THEN
                          RAISE_APPLICATION_ERROR(-20021,'Falta Configurar Tamaño de Productos que soporta el Documento');
           END;
       END IF;
     END IF;
   END IF;

  IF vTip_Ped_vta = '03' THEN
    V_NUM_PRODUCTOS := TO_NUMBER(PTOVENTA_CAJ.CAJ_F_VAR_LINEA_DOC('232'));
  END IF;

    vNumeroProd  := 0;
    vNumCompPago := ' ';
  -----------------------Grabamos el comprobante de pago de la Empresa/Cliente-----------
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

       -- KMONCADA 22.04.2016 PERCEPCION
      IF FARMA_UTILITY.F_GET_LOCAL_APLICA_PERCEPCION(cCodGrupoCia_in, cCodLocal_in) = 'S' THEN
         SELECT DECODE(SUM(NVL(D.VAL_MONTO_PERCEPCION,0)),0,'N','S')
         INTO vAplicaPercepcion
         FROM VTA_PEDIDO_VTA_DET D
         WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
         AND D.COD_LOCAL = cCodLocal_in
         AND D.NUM_PED_VTA = cNumPedVta_in;
      END IF;
      
       BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
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
                                      'S',
                                      vIndGeneraUnSoloComproBenif,
                                      cFlagElectronico, ----LTAVARA 27.08.14 10:00 FLAG PROCESO ELECTRONICO
                                      vAplicaPercepcion -- KMONCADA 22.04.2016 PERCEPCION
                                     );
       IF vAplicaPercepcion = 'S' THEN
         BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
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
                                      'S',
                                      vIndGeneraUnSoloComproBenif,
                                      cFlagElectronico, ----LTAVARA 27.08.14 10:00 FLAG PROCESO ELECTRONICO
                                      -- KMONCADA 22.04.2016 PERCEPCION
                                      vAplicaPercepcion,
                                     'S'
                                     );
       END IF;
       BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in,
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
                                      'N',
                                      vIndGeneraUnSoloComproBenif ,
                                      cFlagElectronico----LTAVARA 27.08.14 10:00 FLAG PROCESO ELECTRONICO
                                     );

      -- graba el ajuste de pedice_hist_forma_pago_entregado 19.11.2013
      -- dubilluz
      pkg_sol_stock.sp_aprueba_sol(cCodGrupoCia_in,
                               cCodLocal_in,
                               cNumPedVta_in);
      --- dubilluz

         --Actualizamos Kardex
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
               BTLMF_P_ACTUALIZA_STK_PROD(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in,
                                          cCodMotKardex_in,
                                          cTipDocKardex_in,
                                          cCodNumeraKardex_in,
                                          cUsuCreaCompPago_in,
                                          v_TipDocClienteMF,
                                          vNumCompPago
                                         );

                            --INI ASOSA -17/10/2014 - PANHD
                                         PTOVTA_RESPALDO_STK.CAJ_UPD_STK_PROD_COMP(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in,
                                          cCodMotKardex_in,
                                          cTipDocKardex_in,
                                          cCodNumeraKardex_in,
                                          cUsuCreaCompPago_in,
                                          v_TipDocBenificiarioMF,
                                          vNumCompPago
                                         );
                                       --FIN ASOSA -17/10/2014 - PANHD

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
               UPDATE VTA_COMP_PAGO COMP
                  SET COMP.TIP_COMP_PAGO_REF = v_TIP_COMP_PAGO,
                      COMP.NUM_COMP_COPAGO_REF = v_NUM_COMP_PAGO
                WHERE COMP.NUM_COMP_PAGO = v_NUM_COMP_PAGO_REF
                  AND COMP.TIP_CLIEN_CONVENIO = '1';

             END LOOP;

  END IF;


    --ERIOS 18.10.2013 Valida nuevo modelo de cobro
      /*  IF PTOVENTA_GRAL.GET_IND_NUEVO_COBRO = 'S' THEN
          v_Resultado := 'EXITO';
        ELSE
          v_Resultado := PTOVENTA_CAJ.CAJ_F_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in);
        END IF;*/

         v_Resultado := 'EXITO';

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
            ---------------------------------------------------
      --ERIOS 2.4.5 Se actualiza el pedido delivery
            IF(v_cIndDelivAutomatico = INDICADOR_SI) THEN
                UPDATE TMP_VTA_PEDIDO_VTA_CAB TMP_CAB
                SET TMP_CAB.USU_MOD_PED_VTA_CAB = cUsuCreaCompPago_in,
                    TMP_CAB.FEC_MOD_PED_VTA_CAB = SYSDATE,
                    TMP_CAB.EST_PED_VTA = EST_PED_COBRADO
                WHERE TMP_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND   TMP_CAB.COD_LOCAL_ATENCION = cCodLocal_in
                AND   TMP_CAB.NUM_PED_VTA_ORIGEN = cNumPedVta_in;
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

    ----------- DUBILLUZ 28.11.2014 ---------------
     ----------- DUBILLUZ 28.11.2014 ---------------
             select count(1)
             into   nExisteGuiaCERO
             from   vta_comp_pago cp
             where  cp.tip_comp_pago = '03' -- guia
             and    cp.cod_grupo_cia = cCodGrupoCia_in
             and    cp.cod_local = cCodLocal_in
             and    cp.num_ped_vta = cNumPedVta_in
             and    cp.val_neto_comp_pago = 0;

             if nExisteGuiaCERO > 0 then

               select decode(count(distinct cp.tip_comp_pago),2,'S','N')
               into   nIsTipCompDOS
               from   vta_comp_pago cp
               where  cp.cod_grupo_cia = cCodGrupoCia_in
               and    cp.cod_local = cCodLocal_in
               and    cp.num_ped_vta = cNumPedVta_in;

               if nIsTipCompDOS = 'S' then
                     update vta_comp_pago cp
                     set    cp.ind_afecta_kardex = (case
                                                     when cp.tip_comp_pago = '03'  then 'N'
                                                       else 'S'
                                                    end
                                                    ),
                            cp.fec_mod_comp_pago = sysdate
                     where  cp.cod_grupo_cia = cCodGrupoCia_in
                     and    cp.cod_local = cCodLocal_in
                     and    cp.num_ped_vta = cNumPedVta_in;

                     update lgt_kardex k
                     set    (k.TIP_COMP_PAGO,k.NUM_COMP_PAGO) = (
                                                                   select cp.tip_comp_pago_ref,cp.num_comp_copago_ref
                                                                   from   vta_comp_pago cp
                                                                   where  cp.tip_comp_pago = '03' -- guia
                                                                   and    cp.cod_grupo_cia = cCodGrupoCia_in
                                                                   and    cp.cod_local = cCodLocal_in
                                                                   and    cp.num_ped_vta = cNumPedVta_in
                                                                   and     cp.tip_comp_pago = k.tip_comp_pago
                                                                   and     cp.num_comp_pago = k.num_comp_pago
                                                                   and     cp.num_ped_vta = k.num_tip_doc
                                                                   and     cp.cod_local = k.cod_local
                                                                   and     cp.cod_grupo_cia = k.cod_grupo_cia
                                                                 )
                     where   k.cod_grupo_cia = cCodGrupoCia_in
                     and     k.cod_local = cCodLocal_in
                     and     k.num_tip_doc = cNumPedVta_in
                     and     k.fec_kardex >= trunc(sysdate-1)
                     and     exists (
                                     select cp.tip_comp_pago_ref,cp.num_comp_copago_ref
                                     from   vta_comp_pago cp
                                     where  cp.tip_comp_pago = '03' -- guia
                                     and    cp.cod_grupo_cia = cCodGrupoCia_in
                                     and    cp.cod_local = cCodLocal_in
                                     and    cp.num_ped_vta = cNumPedVta_in
                                     and    cp.tip_comp_pago = k.tip_comp_pago
                                     and    cp.num_comp_pago = k.num_comp_pago
                                     and     cp.num_ped_vta = k.num_tip_doc
                                     and     cp.cod_local = k.cod_local
                                     and     cp.cod_grupo_cia = k.cod_grupo_cia
                                   );
                     /*IF SQL%ROWCOUNT>0 THEN
                       RAISE_APPLICATION_ERROR(-20232,SQLERRM);
                     END IF;*/
               end if;
             end if;


             update VTA_PEDIDO_VTA_CAB k
                     set    (k.Ruc_Cli_Ped_Vta,K.NOM_CLI_PED_VTA) = (
                                                                   select DISTINCT CP.NUM_DOC_IMPR,CP.NOM_IMPR_COMP
                                                                   from   vta_comp_pago cp
                                                                   where  cp.tip_comp_pago = COD_TIP_COMP_FACTURA
                                                                   and    cp.cod_grupo_cia = cCodGrupoCia_in
                                                                   and    cp.cod_local = cCodLocal_in
                                                                   and    cp.num_ped_vta = cNumPedVta_in
                                                                   and     cp.num_ped_vta = k.num_ped_vta
                                                                   and     cp.cod_local = k.cod_local
                                                                   and     cp.cod_grupo_cia = k.cod_grupo_cia
                                                                 )
                     where   k.cod_grupo_cia = cCodGrupoCia_in
                     and     k.cod_local = cCodLocal_in
                     and     k.num_ped_vta = cNumPedVta_in
                     and     exists (
                                     select 1
                                     from   vta_comp_pago cp
                                     where  cp.tip_comp_pago = COD_TIP_COMP_FACTURA
                                     and    cp.cod_grupo_cia = cCodGrupoCia_in
                                     and    cp.cod_local = cCodLocal_in
                                     and    cp.num_ped_vta = cNumPedVta_in
                                     and     cp.num_ped_vta = k.num_ped_vta
                                     and     cp.cod_local = k.cod_local
                                     and     cp.cod_grupo_cia = k.cod_grupo_cia);

             ----------- DUBILLUZ 28.11.2014 ---------------
             ----------- DUBILLUZ 28.11.2014 ---------------

    IF v_Resultado = 'EXITO' THEN
            --Inserta en la tabla de ahorro x DNI para validar el maximo Ahorro en el dia o Semana
            --dubilluz 28.05.2009
            select sum(NVL(D.AHORRO_CAMP,d.ahorro))
            into   vAhorroPedido
            from   vta_pedido_vta_det d,
                   vta_campana_cupon cup -- KMONCADA 16.04.2015 SE CONSIDERA SOLO LAS CAMPAÑAS QUE SUMEN EN AHORRO DE FIDELIZADO
            where  cup.cod_grupo_cia = d.cod_grupo_cia
            and    cup.cod_camp_cupon = d.cod_camp_cupon
            and    d.cod_grupo_cia = cCodGrupoCia_in
            and    d.cod_local = cCodLocal_in
            and    d.num_ped_vta = cNumPedVta_in
            and    NVL(cup.flg_acumula_ahorro_dni,'S') = 'S';

            if vAhorroPedido > 0 then
               insert into vta_ped_dcto_cli_aux
              (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,VAL_DCTO_VTA,DNI_CLIENTE,FEC_CREA_PED_VTA_CAB)
              select c.cod_grupo_cia,c.cod_local,c.num_ped_vta,sum(NVL(D.AHORRO_CAMP,d.ahorro)),t.dni_cli,c.fec_ped_vta
              from   vta_pedido_vta_det d,
                     vta_pedido_vta_cab c,
                     fid_tarjeta_pedido t,
                     -- DUBILLUZ 08.11.2012
                     VTA_CAMPANA_PROD_USO P,
                     vta_campana_cupon cup -- KMONCADA 16.04.2015 SE CONSIDERA SOLO LAS CAMPAÑAS QUE SUMEN EN AHORRO DE FIDELIZADO
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
              AND    cup.cod_grupo_cia = d.cod_grupo_cia
              and    cup.cod_camp_cupon = d.cod_camp_cupon
              and    NVL(cup.flg_acumula_ahorro_dni,'S') = 'S'
              group by c.cod_grupo_cia,c.cod_local,c.num_ped_vta,t.dni_cli,c.fec_ped_vta;

            end if;


          UPDATE VTA_PEDIDO_VTA_CAB P
          SET P.NAME_PC_COB_PED = v_NOMBRE_PC,
             P.IP_COB_PED      = v_IP_PC,
             P.DNI_USU_LOCAL   = v_DNI_USU
         WHERE P.NUM_PED_VTA = cNumPedVta_in;

           --Obtenemos data comp pago temporalmente.
           -- KMONCADA 02.10.2014 SE AGREGA COMPOS PARA COMP.ELECTRONICOS
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
                        PCT_BENEFICIARIO,PCT_EMPRESA,C.IND_COMP_CREDITO,C.TIP_COMP_PAGO_REF,
                        C.NUM_COMP_PAGO_E, DECODE(C.COD_TIP_PROC_PAGO,'1','1','0')
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

			   -- dubilluz 26.09.2013
			   --ERIOS 28.08.2015 Primero se realiza el calculo.
			   ptoventa_conv_btlmf.btlmf_p_aux_precion_det_conv(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);

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
                   sec_comp_pago_empre, D.VAL_PREC_TOTAL_EMPRE, D.VAL_PREC_TOTAL_BENEF
                   -- KMONCADA 02.07.2015 SE AGREGA CAMPOS PARA GRABAR EN RAC
                FROM ptoventa.VTA_PEDIDO_VTA_DET D
                 WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND D.COD_LOCAL = cCodLocal_in
                   AND D.NUM_PED_VTA = cNumPedVta_in;

            -- GRABAMOS TABLAS TEMPORALES --
               v_Resultado :=  BTLMF_F_GRABA_DATOS_TMP(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,curCompPagoTemp,curFormPagoTemp,curPedidoDetTemp);

               --  dubilluz 17.07.2015
               update VTA_PEDIDO_VTA_CAB c
                 set (ahorro_camp,
                      ahorro_puntos,
                      AHORRO_PACK,
                      PTOS_AHORRO_PACK,
                      ahorro_total,
                      ptos_ahorro_camp) =
                     (select sum(nvl(d.ahorro_camp, 0)),
                             sum(nvl(d.ahorro_puntos, 0)),
                             sum(nvl(d.ahorro_pack, 0)),
                             sum(nvl(d.ptos_ahorro_pack, 0)),
                             sum(nvl(d.ahorro, 0)),
                             --sum(nvl(d.ahorro_camp, 0)) + sum(nvl(d.ahorro_puntos, 0)),
                             sum(nvl(d.ahorro_camp * 100, 0))
                        from vta_pedido_vta_det d
                       where d.cod_grupo_cia = c.cod_grupo_cia
                         and d.cod_local = c.cod_local
                         and d.num_ped_vta = c.num_ped_vta)
               where c.cod_grupo_cia = cCodGrupoCia_in
                 and c.cod_local = cCodLocal_in
                 and c.num_ped_vta = cNumPedVta_in;
           -- KMONCADA 09.05.2016 CUPONES PARA CONVENIOS
           FARMA_CUPON.NVO_ALGORITMO_CUPON(cCodGrupoCia_in => cCodGrupoCia_in,
                                           cCodLocal_in => cCodLocal_in,
                                           cNumPedVta_in => cNumPedVta_in);
           IF  v_Resultado  = 'S' THEN
               v_Resultado := 'EXITO';
           ELSE
               v_Resultado := 'ERROR';
           END IF;
     END IF;






  RETURN v_Resultado;

 END;

FUNCTION BTLMF_F_CUR_OBT_CONV_PEDIDO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecUsuLocal_in IN CHAR,
                                    pNroPedido    IN VARCHAR2)
    RETURN FarmaCursor IS
    curBenif FarmaCursor;

  BEGIN

    OPEN curBenif FOR
      SELECT
       vta.cod_convenio,
       conv.flg_cod_barra,
       NVL(vta.ind_conv_btl_mf,'N') IND_CONV_BTL_MF,
       NVL(conv.flg_valida_lincre_benef,'0') FLG_VALIDA_LINCRE_BENEF,
       conv.cod_tipo_convenio COD_TIPO_CONVENIO

      FROM
       PTOVENTA.vta_pedido_vta_cab vta,
       PTOVENTA.Mae_Convenio conv
      WHERE vta.num_ped_vta = pNroPedido
        AND conv.cod_convenio = vta.cod_convenio;

    RETURN curBenif;
  END;

  FUNCTION BTLMF_F_CHAR_OBT_COD_BARRA_CON(vCodLocal IN CHAR,
										  nTamanio_in IN INTEGER DEFAULT 18)
  RETURN VARCHAR2 IS
    vCodigoBarraConv VARCHAR2(300);


  BEGIN

   SELECT vCodLocal||to_char(systimestamp,'yymmdd')||substr(to_char(systimestamp,'HH24MISSff'),0,9)
     INTO vCodigoBarraConv
     FROM DUAL;

    RETURN SUBSTR(vCodigoBarraConv,0,nTamanio_in);
   END;

PROCEDURE BTLMF_P_INSERT_COMP_PAGO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   pNUM_PED_VTA VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                   pSEC_COMP_PAGO VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE,
                                   pTIP_COMP_PAGO VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE,
                                   pNUM_COMP_PAGO VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE,
                                   pSEC_MOV_CAJA VTA_COMP_PAGO.SEC_MOV_CAJA%TYPE,
                                   pCANT_ITEM VTA_COMP_PAGO.CANT_ITEM%TYPE,
                                   pCOD_CLI_LOCAL VTA_COMP_PAGO.COD_CLI_LOCAL%TYPE,
                                   pNOM_IMPR_COMP VTA_COMP_PAGO.NOM_IMPR_COMP%TYPE,
                                   pDIREC_IMPR_COMP VTA_COMP_PAGO.DIREC_IMPR_COMP%TYPE,
                                   pNUM_DOC_IMPR VTA_COMP_PAGO.NUM_DOC_IMPR%TYPE,
                                   pVAL_BRUTO_COMP_PAGO    CHAR,
                                   pVAL_NETO_COMP_PAGO     CHAR,
                                   pVAL_DCTO_COMP_PAGO     CHAR,
                                   pVAL_AFECTO_COMP_PAGO   CHAR,
                                   pVAL_IGV_COMP_PAGO      CHAR,
                                   pVAL_REDONDEO_COMP_PAGO VTA_COMP_PAGO.VAL_REDONDEO_COMP_PAGO%TYPE,
                                   pUSU_CREA_COMP_PAGO VTA_COMP_PAGO.USU_CREA_COMP_PAGO%TYPE,
                                   pPORC_IGV_COMP_PAGO CHAR,
                                   PTipClienteConv    VTA_COMP_PAGO.Tip_Clien_Convenio%TYPE,
                                   pVAL_COPAGO_COMP_PAGO  VTA_COMP_PAGO.VAL_COPAGO_COMP_PAGO%TYPE,
                                   PVAL_NUM_COMP_COPAGO   VTA_COMP_PAGO.NUM_COMP_COPAGO_REF%TYPE,
                                   pVAL_IGV_COMP_COPAGO   CHAR,
                                   PIND_AFECTA_KARDEX CHAR,
                                   PPCT_BENEFICIARIO VTA_COMP_PAGO.PCT_BENEFICIARIO%TYPE,
                                   PPCT_EMPRESA VTA_COMP_PAGO.PCT_EMPRESA%TYPE,
                                   PIND_COMP_CREDITO  VTA_COMP_PAGO.IND_COMP_CREDITO%TYPE,
                                   pTIP_COMP_PAGO_REF VTA_COMP_PAGO.TIP_COMP_PAGO_REF%TYPE,
                                   pCOD_TIPO_CONVENIO VTA_COMP_PAGO.COD_TIPO_CONVENIO%TYPE,
                                   PIND_AFECTO_IGV    VTA_COMP_PAGO.IND_AFECTO_IGV%TYPE,
                                   -- KMONCADA 22.04.2016 PERCEPCION
                                   pValMontoPercepcion_in IN VTA_COMP_PAGO.VAL_PERCEP_COMP_PAGO%TYPE,
                                   pPctPercepcion_in IN VTA_COMP_PAGO.VAL_PCT_PERCEPCION%TYPE,
                                   pValRedondeoPercepcion_in IN VTA_COMP_PAGO.VAL_REDONDEO_PERCEPCION%TYPE
                                 )
 AS

 BEGIN

 dbms_output.put_line('Ingresando datos Comprobantoe');

 dbms_output.put_line('cCodGrupoCia_in        = '||cCodGrupoCia_in);
 dbms_output.put_line('cCodLocal_in           = '||cCodLocal_in);
 dbms_output.put_line('pNUM_PED_VTA           = '||pNUM_PED_VTA);
 dbms_output.put_line('pSEC_COMP_PAGO         = '||pSEC_COMP_PAGO);
 dbms_output.put_line('pTIP_COMP_PAGO         = '||pTIP_COMP_PAGO);
 dbms_output.put_line('pNUM_COMP_PAGO         = '||pNUM_COMP_PAGO);
 dbms_output.put_line('pSEC_MOV_CAJA          = '||pSEC_MOV_CAJA);
 dbms_output.put_line('CANT_ITEM              = '||pCANT_ITEM);
 dbms_output.put_line('COD_CLI_LOCAL          = '||PCOD_CLI_LOCAL);
 dbms_output.put_line('NOM_IMPR_COMP          = '||PNOM_IMPR_COMP);
 dbms_output.put_line('DIREC_IMPR_COMP        = '||PDIREC_IMPR_COMP);
 dbms_output.put_line('NUM_DOC_IMPR           = '||PNUM_DOC_IMPR);
 dbms_output.put_line('VAL_BRUTO_COMP_PAGO    = '||PVAL_BRUTO_COMP_PAGO);
 dbms_output.put_line('VAL_NETO_COMP_PAGO     = '||PVAL_NETO_COMP_PAGO);
 dbms_output.put_line('VAL_DCTO_COMP_PAGO     = '||PVAL_DCTO_COMP_PAGO);
 dbms_output.put_line('VAL_AFECTO_COMP_PAGO   = '||PVAL_AFECTO_COMP_PAGO);
 dbms_output.put_line('VAL_IGV_COMP_PAGO      = '||PVAL_IGV_COMP_PAGO);
 dbms_output.put_line('VAL_REDONDEO_COMP_PAGO = '||PVAL_REDONDEO_COMP_PAGO);
 dbms_output.put_line('USU_CREA_COMP_PAGO     = '||PUSU_CREA_COMP_PAGO);
 dbms_output.put_line('PORC_IGV_COMP_PAGO     = '||PPORC_IGV_COMP_PAGO);






    INSERT INTO VTA_COMP_PAGO(COD_GRUPO_CIA,
                              COD_LOCAL,
                              NUM_PED_VTA,
                              SEC_COMP_PAGO,
                              TIP_COMP_PAGO,
                              NUM_COMP_PAGO,
                              SEC_MOV_CAJA,
                              CANT_ITEM,
                              COD_CLI_LOCAL,
                              NOM_IMPR_COMP,
                              DIREC_IMPR_COMP,
                              NUM_DOC_IMPR,
                              VAL_BRUTO_COMP_PAGO,
                              VAL_NETO_COMP_PAGO,
                              VAL_DCTO_COMP_PAGO,
                              VAL_AFECTO_COMP_PAGO,
                              VAL_IGV_COMP_PAGO,
                              VAL_REDONDEO_COMP_PAGO,
                              USU_CREA_COMP_PAGO,
                              PORC_IGV_COMP_PAGO,
                              Tip_Clien_Convenio,
                              VAL_COPAGO_COMP_PAGO,
                              NUM_COMP_COPAGO_REF,
                              VAL_IGV_COMP_COPAGO,
                              IND_AFECTA_KARDEX,
                              PCT_BENEFICIARIO,
                              PCT_EMPRESA,
                              IND_COMP_CREDITO,
                              TIP_COMP_PAGO_REF,
                              COD_TIPO_CONVENIO,
                              IND_AFECTO_IGV,
                              VAL_PERCEP_COMP_PAGO,
                              VAL_REDONDEO_PERCEPCION,
                              VAL_PCT_PERCEPCION
                              )
                        VALUES(
                                cCodGrupoCia_in,
                                cCodLocal_in ,
                                pNUM_PED_VTA ,
                                pSEC_COMP_PAGO ,
                                pTIP_COMP_PAGO ,
                                pNUM_COMP_PAGO ,
                                pSEC_MOV_CAJA ,
                                pCANT_ITEM ,
                                pCOD_CLI_LOCAL ,
                                pNOM_IMPR_COMP ,
                                pDIREC_IMPR_COMP ,
                                pNUM_DOC_IMPR ,
                                TO_NUMBER(pVAL_BRUTO_COMP_PAGO ,'999,990.00'),
                                TO_NUMBER(pVAL_NETO_COMP_PAGO ,'999,990.00') + NVL(pValMontoPercepcion_in,0),
                                TO_NUMBER(pVAL_DCTO_COMP_PAGO , '999,990.00'),
                                TO_NUMBER(pVAL_AFECTO_COMP_PAGO , '999,990.00'),
                                TO_NUMBER(pVAL_IGV_COMP_PAGO,'999,990.00'),
                                pVAL_REDONDEO_COMP_PAGO ,
                                pUSU_CREA_COMP_PAGO ,
                                TO_NUMBER(pPORC_IGV_COMP_PAGO, '990.00'),
                                decode(PTipClienteConv,'B',COMP_BENEF_CAMPO,COMP_EMPRE_CAMPO),
                                pVAL_COPAGO_COMP_PAGO,
                                PVAL_NUM_COMP_COPAGO,
                                TO_NUMBER(pVAL_IGV_COMP_COPAGO,'999,990.00'),
                                PIND_AFECTA_KARDEX,
                                PPCT_BENEFICIARIO,
                                PPCT_EMPRESA,
                                PIND_COMP_CREDITO,
                                PTIP_COMP_PAGO_REF,
                                PCOD_TIPO_CONVENIO,
                                PIND_AFECTO_IGV,
                                pValMontoPercepcion_in,
                                pValRedondeoPercepcion_in,
                                pPctPercepcion_in
                               );




          EXCEPTION
      WHEN DUP_VAL_ON_INDEX THEN
      RAISE_APPLICATION_ERROR(-20999,'ERROR AL INSERTAR COMPROBANTE DE PAGO: ' ||
                  'EL NUMERO '''||pNUM_COMP_PAGO||''' DE '||CASE WHEN pTIP_COMP_PAGO = '02' THEN 'FACTURA' WHEN pTIP_COMP_PAGO = '03' THEN 'GUIA' ELSE 'DOCUMENTO' END||' YA EXISTE.');
          WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20999,'ERROR AL INSERTAR COMPROBANTE DE PAGO: ' ||SUBSTR(SQLERRM,11));


 END BTLMF_P_INSERT_COMP_PAGO;

  PROCEDURE BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in             IN CHAR,
                                           cCodLocal_in                IN CHAR,
                                           cUsuCreaCompPago_in         IN CHAR,
                                           cNumPedVta_in               VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                                           V_NUM_PRODUCTOS             MAE_COMP_CONV_LINEAS.NUM_PRODUCTOS%TYPE,
                                           pTIP_COMP_PAGO              VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE,
                                           pSEC_MOV_CAJA               VTA_COMP_PAGO.SEC_MOV_CAJA%TYPE,
                                           cCodNumera_in               IN CHAR,
                                           pTipoClienteConvenio        IN CHAR,
                                           pFlgTipoConvenio            IN MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE,
                                           pIndAfectaKardex            CHAR,
                                           vPCT_BENEFICIARIO           VTA_COMP_PAGO.PCT_BENEFICIARIO%TYPE,
                                           vPCT_EMPRESA                VTA_COMP_PAGO.PCT_EMPRESA%TYPE,
                                           vSecImprLocal_in            vta_impr_local.sec_impr_local%type,
                                           vIND_CON_IGV_in             char,
                                           vIndGeneraUnSoloComproBenif CHAR,
                                           cFlagElectronico            IN CHAR DEFAULT '0',
                                           -- KMONCADA 22.04.2016 PERCEPCION
                                           cAplicaPercepcion_in        IN CHAR DEFAULT 'N',
                                           vContieneProdPercep_in      IN CHAR DEFAULT 'N')
  AS

  v_nContador        NUMBER;
  v_nCont            NUMBER;
  v_nContadorItem    NUMBER;

  v_nContComp        NUMBER;
  v_nCantMaxItem     NUMBER;
  v_nItem            NUMBER;
  v_nRestoItem       NUMBER;
  v_nCantidadItem    NUMBER;


  v_flg_numero_comp_uno char(1);
  v_flg_numero_comp_dos char(1);
  v_cIndGraboComp CHAR(1);
--  v_flg_activa CHAR(1);

  v_nSecCompPago  VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
  v_nNumCompPago  VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;-- LTAVARA 01.10.2014 usar con auxiliar
  v_SEC_COMP_PAGO VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
  v_NUM_COMP_PAGO VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
  v_CANT_ITEM     VTA_COMP_PAGO.CANT_ITEM%TYPE;

  v_PORC_IGV_COMP_PAGO VARCHAR2(30);


  v_VAL_BRUTO_COMP_PAGO  VTA_COMP_PAGO.VAL_BRUTO_COMP_PAGO%TYPE;
  v_VAL_NETO_COMP_PAGO   VTA_COMP_PAGO.VAL_NETO_COMP_PAGO%TYPE;
  v_VAL_COPAGO_COMP_PAGO VTA_COMP_PAGO.VAL_COPAGO_COMP_PAGO%TYPE;
  v_VAL_NUM_COMP_COPAGO  VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;


  v_VAL_DCTO_COMP_PAGO     VTA_COMP_PAGO.VAL_DCTO_COMP_PAGO%TYPE;
  v_VAL_AFECTO_COMP_PAGO   VTA_COMP_PAGO.VAL_AFECTO_COMP_PAGO%TYPE;
  v_VAL_IGV_COMP_PAGO      VTA_COMP_PAGO.VAL_IGV_COMP_PAGO%TYPE;
  v_VAL_IGV_COMP_COPAGO    VTA_COMP_PAGO.VAL_IGV_COMP_PAGO%TYPE;



  v_cCodCliLocal    VTA_PEDIDO_VTA_CAB.COD_CLI_LOCAL%TYPE;
  v_cNomCliPedVta    VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE;
  v_cRucCliPedVta    VTA_PEDIDO_VTA_CAB.RUC_CLI_PED_VTA%TYPE;
  v_cDirCliPedVta    VTA_PEDIDO_VTA_CAB.DIR_CLI_PED_VTA%TYPE;
  v_nValRedondeo    VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;

  v_Flg_Guardar_Comp CHAR(1) := 'P';
  v_Flg_Contar_Item CHAR(1) := 'N';

  vQuery varchar2(4000) := ' ';

  v_codigoProducto VARCHAR2(30000);

  v_CodigoProd_Pedido VARCHAR2(30000);
  v_conteo_benef integer;

  v_Coma varchar2(10);
  mesg_body VARCHAR2(4000);

  v_nro_comprobante NUMBER;

  v_MontoCredito FLOAT;
  v_MontoNeto VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL%TYPE;

  v_Ind_comp_credito VTA_COMP_PAGO.IND_COMP_CREDITO%TYPE;
  v_Tip_comp_pago_ref VTA_COMP_PAGO.TIP_COMP_PAGO_REF%TYPE;

   curDetPedido FarmaCursor;
   /******/
           SEC_PED_VTA_DET vta_impr_local.sec_impr_local%type;
           COD_PROD lgt_prod.cod_prod%type;
            VALOR_BRUTO number;
            VALOR_NETO number;
            VALOR_DESCUENTO number;
            VALOR_AFECTO number;
            VALOR_IGV number;
            PORC_IGV number;
            PCT_PERCEPCION VTA_PEDIDO_VTA_DET.VAL_PCT_PERCEPCION%TYPE;
            VALOR_PERCEPCION VTA_PEDIDO_VTA_DET.VAL_MONTO_PERCEPCION%TYPE;
   /******/
       CURSOR curComprobantes is
      select *
      from   vta_comp_pago g
      where  g.cod_grupo_cia = cCodGrupoCia_in
      and    g.cod_local = cCodLocal_in
      and    g.num_ped_vta = cNumPedVta_in;
      fSC curComprobantes%rowtype;

        --INI ASOSA - 24/07/2015 - IGVAZNIA
      v_tipoFact varchar2(10) := '02';
      v_cod_cia_amazonia char(3) := '004';
      v_cod_cia char(3) := '001';
      v_ind_exon varchar2(20) := '';
      --FIN ASOSA - 24/07/2015 - IGVAZNIA

      -- 22.04.2016 PERCEPCION
      vIndGraboPercepcion CHAR(1) := 'N';
      vValMontoPercepcion VTA_PEDIDO_VTA_DET.VAL_MONTO_PERCEPCION%TYPE;
      vValPCTPercepcion VTA_PEDIDO_VTA_DET.VAL_PCT_PERCEPCION%TYPE;
      vRedondeoPercepcion VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PERCEPCION%TYPE := NULL;
      vValCPRedondeoPercepcion VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PERCEPCION%TYPE := NULL;
 BEGIN

   dbms_output.put_line('Metodo:BTLMF_P_GRAB_COMP_PAGO_CON_IGV');
   v_flg_numero_comp_uno := 'S';
   v_flg_numero_comp_dos := 'S';

    -- KMONCADA 22.04.2016 PERCEPCION
    IF vIND_CON_IGV_in = 'S' AND cAplicaPercepcion_in = 'N' THEN
      SELECT COUNT(*),
             SUM(VTA_DET.VAL_PREC_TOTAL)
      INTO v_nCantidadItem,
           v_MontoNeto
      FROM VTA_PEDIDO_VTA_DET VTA_DET,
           LGT_PROD P,
           PBL_IGV  I
      WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND VTA_DET.COD_LOCAL = cCodLocal_in
      AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
      AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND VTA_DET.COD_PROD = P.COD_PROD
      AND P.COD_IGV = I.COD_IGV
      AND I.PORC_IGV > 0;
    ELSIF vIND_CON_IGV_in = 'S' AND cAplicaPercepcion_in = 'S' AND vContieneProdPercep_in = 'N' THEN
      SELECT COUNT(*),
             SUM(VTA_DET.VAL_PREC_TOTAL)
      INTO v_nCantidadItem,
           v_MontoNeto
      FROM VTA_PEDIDO_VTA_DET VTA_DET,
           LGT_PROD P,
           PBL_IGV  I
      WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND VTA_DET.COD_LOCAL = cCodLocal_in
      AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
      AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND VTA_DET.COD_PROD = P.COD_PROD
      AND P.COD_IGV = I.COD_IGV
      AND I.PORC_IGV > 0
      AND NVL(VTA_DET.VAL_MONTO_PERCEPCION,0) = 0;
    ELSIF vIND_CON_IGV_in = 'S' AND cAplicaPercepcion_in = 'S' AND vContieneProdPercep_in = 'S' THEN
      SELECT COUNT(*),
             SUM(VTA_DET.VAL_PREC_TOTAL)
      INTO v_nCantidadItem,
           v_MontoNeto
      FROM VTA_PEDIDO_VTA_DET VTA_DET,
           LGT_PROD P,
           PBL_IGV  I
      WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND VTA_DET.COD_LOCAL = cCodLocal_in
      AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
      AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND VTA_DET.COD_PROD = P.COD_PROD
      AND P.COD_IGV = I.COD_IGV
      AND I.PORC_IGV > 0
      AND NVL(VTA_DET.VAL_MONTO_PERCEPCION,0) != 0;
    ELSE
      SELECT COUNT(*),
             SUM(VTA_DET.VAL_PREC_TOTAL)
      INTO v_nCantidadItem,
           v_MontoNeto
      FROM VTA_PEDIDO_VTA_DET VTA_DET,
           LGT_PROD P,
           PBL_IGV  I
      WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND VTA_DET.COD_LOCAL = cCodLocal_in
      AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
      AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND VTA_DET.COD_PROD = P.COD_PROD
      AND P.COD_IGV = I.COD_IGV
      AND I.PORC_IGV = 0;
    END IF;

 -- COMP PAGO PARA BENEFICIARIO

   SELECT VTA_CAB.VAL_REDONDEO_PED_VTA,
          VTA_CAB.COD_CLI_LOCAL,
          NVL(VTA_CAB.NOM_CLI_PED_VTA,' '),
          NVL(VTA_CAB.RUC_CLI_PED_VTA,' '),
          NVL(VTA_CAB.DIR_CLI_PED_VTA,' '),
          VTA_CAB.VAL_REDONDEO_PERCEPCION
   INTO  v_nValRedondeo,
         v_cCodCliLocal,
         v_cNomCliPedVta,
         v_cRucCliPedVta,
         v_cDirCliPedVta,
         vRedondeoPercepcion
   FROM  VTA_PEDIDO_VTA_CAB VTA_CAB
   WHERE VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
   AND   VTA_CAB.COD_LOCAL = cCodLocal_in
   AND   VTA_CAB.NUM_PED_VTA = cNumPedVta_in;

   -- COMP PAGO PARA EMPRESA




  v_nItem := 0;
  v_nCont := 0;
  --v_nContComp := 0;
  v_nContadorItem := 0;

  v_VAL_BRUTO_COMP_PAGO  := 0;
  v_VAL_NETO_COMP_PAGO   := 0;
  v_VAL_DCTO_COMP_PAGO   := 0;
  v_VAL_AFECTO_COMP_PAGO := 0;
  v_VAL_IGV_COMP_PAGO    := 0;
  v_VAL_IGV_COMP_COPAGO  := 0;
  -- KMONCADA 25.04.2016 PERCEPCION
  vValPCTPercepcion := 0;
  vValMontoPercepcion := 0;
  vValCPRedondeoPercepcion := 0;
  v_codigoProducto := '';
  v_CodigoProd_Pedido := '';
  v_Coma := ',';

/*  IF vIND_CON_IGV_in = 'S' THEN
    SELECT
          SUM(VTA_DET.VAL_PREC_TOTAL)
     INTO v_MontoNeto
     FROM VTA_PEDIDO_VTA_DET VTA_DET,
          LGT_PROD P,
          PBL_IGV  I
    WHERE
           VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND VTA_DET.COD_LOCAL = cCodLocal_in
      AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
      AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND VTA_DET.COD_PROD = P.COD_PROD
      AND P.COD_IGV = I.COD_IGV
      AND I.PORC_IGV > 0;
  ELSE
   SELECT
          SUM(VTA_DET.VAL_PREC_TOTAL)
     INTO v_MontoNeto
     FROM VTA_PEDIDO_VTA_DET VTA_DET,
          LGT_PROD P,
          PBL_IGV  I
    WHERE
           VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND VTA_DET.COD_LOCAL = cCodLocal_in
      AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
      AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND VTA_DET.COD_PROD = P.COD_PROD
      AND P.COD_IGV = I.COD_IGV
      AND I.PORC_IGV = 0;
  END IF;*/

  v_nCantMaxItem := v_nCantidadItem;

  --INI ASOSA - 24/07/2015 - IGVAZNIA
    SELECT a.cod_cia
    into v_cod_cia
    FROM pbl_local a
    where a.cod_grupo_cia = cCodGrupoCia_in
    and a.cod_local = cCodLocal_in;
  --FIN ASOSA - 24/07/2015 - IGVAZNIA

      curDetPedido := getDetallePedido(cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, vIND_CON_IGV_in,
                                       cAplicaPercepcion_in, vContieneProdPercep_in);
      LOOP
      FETCH curDetPedido INTO
            SEC_PED_VTA_DET,
            COD_PROD,
            VALOR_BRUTO,
            VALOR_NETO,
            VALOR_DESCUENTO,
            VALOR_AFECTO,
            VALOR_IGV,
            PORC_IGV,
            PCT_PERCEPCION,
            VALOR_PERCEPCION;
      EXIT WHEN curDetPedido%NOTFOUND;

      v_nCont         := v_nCont + 1;
      v_nContadorItem := v_nContadorItem + 1;

      IF v_nCont = '1' THEN
         v_Coma := ' ';
      ELSE
         v_Coma := ',';
      END IF;

      IF V_NUM_PRODUCTOS=0 AND v_nContadorItem >= v_nCantMaxItem THEN
          v_nItem      :=  v_nCont;
          v_Flg_Guardar_Comp := 'S';
          v_nCont := 0;
      ELSE
          IF MOD(v_nContadorItem,V_NUM_PRODUCTOS) = 0 or  v_nContadorItem >= v_nCantMaxItem THEN
             v_nItem      :=  v_nCont;
             v_Flg_Guardar_Comp := 'S';
             v_nCont := 0;
          END IF;
      END IF;

      --INI ASOSA - 24/07/2015 - IGVAZNIA
      v_ind_exon := PTOVENTA_CAJ.F_GET_PARTIDA_ARANCELARIA(cCodGrupoCia_in,
                                                             cCodLocal_in,
                                                             v_cod_cia,
                                                             COD_PROD);
		--ERIOS 21.06.2016 Determina por CIA
        if (--v_cod_cia = v_cod_cia_amazonia and
           pTIP_COMP_PAGO = v_tipoFact and
           v_ind_exon is not null) then
           v_nContadorItem := v_nContadorItem + 1 ;
        end if;
      --FIN ASOSA - 24/07/2015 - IGVAZNIA


      v_VAL_BRUTO_COMP_PAGO  := v_VAL_BRUTO_COMP_PAGO +Valor_Bruto;
      v_VAL_NETO_COMP_PAGO   := v_VAL_NETO_COMP_PAGO  + Valor_Neto;
      v_VAL_DCTO_COMP_PAGO   := v_VAL_DCTO_COMP_PAGO + Valor_Descuento;
      v_VAL_AFECTO_COMP_PAGO := v_VAL_AFECTO_COMP_PAGO + Valor_Afecto;
      v_PORC_IGV_COMP_PAGO   := Porc_Igv;
      v_codigoProducto       := v_codigoProducto||v_Coma||Cod_Prod;
      v_CodigoProd_Pedido    := v_CodigoProd_Pedido||v_Coma||Cod_Prod||'|'||Sec_Ped_Vta_Det;
      --KMONCADA 22.04.2016 PERCEPCION
      vValMontoPercepcion := vValMontoPercepcion + VALOR_PERCEPCION;
      vValPCTPercepcion := CASE WHEN PCT_PERCEPCION > NVL(vValPCTPercepcion,0) THEN PCT_PERCEPCION
                                ELSE vValPCTPercepcion
                            END;

      IF v_Flg_Guardar_Comp = 'S' THEN
        -- 25.04.2016 PERCEPCION
        IF cAplicaPercepcion_in = 'S' AND vContieneProdPercep_in = 'S' THEN
          IF vIndGraboPercepcion = 'N' THEN
            IF pTipoClienteConvenio = COMP_BENEFICIARIO THEN
              IF vRedondeoPercepcion IS NOT NULL AND vRedondeoPercepcion != 0 THEN
                vValCPRedondeoPercepcion := vRedondeoPercepcion;
                vIndGraboPercepcion := 'S';
              ELSE
                vValPCTPercepcion := NULL;
                vValMontoPercepcion := NULL;
                vValCPRedondeoPercepcion := NULL;
              END IF;
            ELSE
              vValPCTPercepcion := NULL;
              vValMontoPercepcion := NULL;
              vValCPRedondeoPercepcion := NULL;
            END IF;
          ELSE
            NULL;
          END IF;
        END IF;
         IF pFlgTipoConvenio = FLG_TIP_CONV_COPAGO or pFlgTipoConvenio = FLG_TIP_CONV_CREDITO THEN
             v_NUM_COMP_PAGO  :=  BTLMF_OBT_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in,cCodLocal_in,vSecImprLocal_in,pTIP_COMP_PAGO);
                 IF vPCT_BENEFICIARIO = 0 AND  vPCT_EMPRESA = 100 THEN

                      IF pFlgTipoConvenio = FLG_TIP_CONV_COPAGO then
                         IF pTipoClienteConvenio = COMP_BENEFICIARIO THEN
                              v_Ind_comp_credito    := 'N';
                              v_VAL_COPAGO_COMP_PAGO := v_VAL_NETO_COMP_PAGO;
                              v_VAL_NETO_COMP_PAGO   := 0;
                              v_VAL_IGV_COMP_PAGO  := 0;
                         else
                             v_Ind_comp_credito    := 'S';
                             v_VAL_NETO_COMP_PAGO  :=  v_VAL_NETO_COMP_PAGO;
                              v_VAL_IGV_COMP_PAGO  := (v_VAL_NETO_COMP_PAGO -(v_VAL_NETO_COMP_PAGO / (1 + (VALOR_IGV/ 100))));

                                v_CodigoProd_Pedido := trim(v_CodigoProd_Pedido);
                                       dbms_output.put_line('-'||v_CodigoProd_Pedido||'-');

                                       ----------DUBILLUZ INICIO
                                        SELECT P.NUM_COMP_PAGO,P.VAL_NETO_COMP_PAGO,P.VAL_IGV_COMP_PAGO,P.TIP_COMP_PAGO
                                          INTO v_VAL_NUM_COMP_COPAGO,v_VAL_COPAGO_COMP_PAGO,v_VAL_IGV_COMP_COPAGO,v_Tip_comp_pago_ref
                                          FROM VTA_COMP_PAGO P
                                         WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND P.COD_LOCAL = cCodLocal_in
                                           AND P.NUM_PED_VTA = cNumPedVta_in
                                           AND P.SEC_COMP_PAGO =
                                               (SELECT DISTINCT D.SEC_COMP_PAGO_BENEF
                                                  FROM VTA_PEDIDO_VTA_DET D
                                                 WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                                                   AND D.COD_LOCAL = cCodLocal_in
                                                   AND D.NUM_PED_VTA = cNumPedVta_in
                                                   AND (D.COD_PROD, D.SEC_PED_VTA_DET) IN
                                                       (select Substr(ves.texto, 1, 6) cod_prod,
                                                               Substr(ves.texto, 8) * 1 sec_prod_det
                                                          from (SELECT EXTRACTVALUE(xt.column_value, 'e') texto
                                                                  FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                                                                         REPLACE(v_CodigoProd_Pedido,
                                                                                                                 ',',
                                                                                                                 '</e><e>') ||
                                                                                                         '</e></coll>'),
                                                                                                 '/coll/e'))) xt) ves));
                                       ----------DUBILLUZ FIN



                                       UPDATE VTA_COMP_PAGO P
                                        SET P.NUM_COMP_COPAGO_REF = v_NUM_COMP_PAGO,
                                          P.TIP_COMP_PAGO_REF   = v_Tip_comp_pago_ref,
                                          P.Val_Igv_Comp_Copago = TO_NUMBER(TO_CHAR(v_VAL_IGV_COMP_PAGO,'999,990.00'),'999,990.00')
                                        WHERE p.COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND p.COD_LOCAL = cCodLocal_in
                                        AND p.NUM_PED_VTA   = cNumPedVta_in
                                        AND P.TIP_CLIEN_CONVENIO = COMP_BENEF_CAMPO
                                        AND P.NUM_COMP_PAGO = v_VAL_NUM_COMP_COPAGO;

                         end if;
                      ---------------
                     ELSE
                      ---------------
                      -- ES CREDITO el CONVENIO
                         IF pTipoClienteConvenio = COMP_BENEFICIARIO THEN
                            IF vIndGeneraUnSoloComproBenif = 'N' THEN
                              v_VAL_COPAGO_COMP_PAGO:=v_VAL_NETO_COMP_PAGO;
                              v_VAL_NETO_COMP_PAGO   :=  0;
                              v_Ind_comp_credito     := 'N';
                            ELSE
                               v_Ind_comp_credito    := 'S';
                            END IF;
                            v_VAL_IGV_COMP_PAGO  := (v_VAL_NETO_COMP_PAGO -(v_VAL_NETO_COMP_PAGO / (1 + (VALOR_IGV/ 100))));
                         ELSE  IF pTipoClienteConvenio = COMP_EMPRESA THEN
                                     v_Ind_comp_credito    := 'S';
                                     v_VAL_IGV_COMP_PAGO  := (v_VAL_NETO_COMP_PAGO -(v_VAL_NETO_COMP_PAGO / (1 + (VALOR_IGV/ 100))));

                                     v_CodigoProd_Pedido := trim(v_CodigoProd_Pedido);
                                     dbms_output.put_line('-'||v_CodigoProd_Pedido||'-');

                                         SELECT COUNT(1) into v_conteo_benef FROM
                                         VTA_COMP_PAGO P
                                         WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND P.COD_LOCAL = cCodLocal_in
                                           AND P.NUM_PED_VTA = cNumPedVta_in
                                           AND P.SEC_COMP_PAGO =
                                               (SELECT DISTINCT D.SEC_COMP_PAGO_BENEF
                                                  FROM VTA_PEDIDO_VTA_DET D
                                                 WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                                                   AND D.COD_LOCAL = cCodLocal_in
                                                   AND D.NUM_PED_VTA = cNumPedVta_in
                                                   AND (D.COD_PROD, D.SEC_PED_VTA_DET) IN
                                                       (select Substr(ves.texto, 1, 6) cod_prod,
                                                               Substr(ves.texto, 8) * 1 sec_prod_det
                                                          from (SELECT EXTRACTVALUE(xt.column_value, 'e') texto
                                                                  FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                                                                         REPLACE(v_CodigoProd_Pedido,
                                                                                                                 ',',
                                                                                                                 '</e><e>') ||
                                                                                                         '</e></coll>'),
                                                                                                         '/coll/e'))) xt) ves));
                                        IF v_conteo_benef>0 then

                                            SELECT P.NUM_COMP_PAGO,P.VAL_NETO_COMP_PAGO,P.VAL_IGV_COMP_PAGO,P.TIP_COMP_PAGO
                                              INTO v_VAL_NUM_COMP_COPAGO,v_VAL_COPAGO_COMP_PAGO,v_VAL_IGV_COMP_COPAGO,v_Tip_comp_pago_ref
                                              FROM VTA_COMP_PAGO P
                                             WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                               AND P.COD_LOCAL = cCodLocal_in
                                               AND P.NUM_PED_VTA = cNumPedVta_in
                                               AND P.SEC_COMP_PAGO =
                                                   (SELECT DISTINCT D.SEC_COMP_PAGO_BENEF
                                                      FROM VTA_PEDIDO_VTA_DET D
                                                     WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                                                       AND D.COD_LOCAL = cCodLocal_in
                                                       AND D.NUM_PED_VTA = cNumPedVta_in
                                                       AND (D.COD_PROD, D.SEC_PED_VTA_DET) IN
                                                           (select Substr(ves.texto, 1, 6) cod_prod,
                                                                   Substr(ves.texto, 8) * 1 sec_prod_det
                                                              from (SELECT EXTRACTVALUE(xt.column_value, 'e') texto
                                                                      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                                                                             REPLACE(v_CodigoProd_Pedido,
                                                                                                                     ',',
                                                                                                                     '</e><e>') ||
                                                                                                             '</e></coll>'),
                                                                                                     '/coll/e'))) xt) ves));

                                            UPDATE VTA_COMP_PAGO P
                                            SET P.NUM_COMP_COPAGO_REF = v_NUM_COMP_PAGO,
                                              P.TIP_COMP_PAGO_REF   = v_Tip_comp_pago_ref,
                                              P.Val_Igv_Comp_Copago = TO_NUMBER(TO_CHAR(v_VAL_IGV_COMP_PAGO,'999,990.00'),'999,990.00')
                                            WHERE p.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND p.COD_LOCAL = cCodLocal_in
                                            AND p.NUM_PED_VTA   = cNumPedVta_in
                                            AND P.TIP_CLIEN_CONVENIO = COMP_BENEF_CAMPO
                                            AND P.NUM_COMP_PAGO = v_VAL_NUM_COMP_COPAGO;
                                        END IF;

                               END IF;
                         end if;
                      ---------------
                     END IF;
               ELSE
                   ----inicio
                           IF pTipoClienteConvenio = COMP_BENEFICIARIO THEN
                              BEGIN
                                SELECT DECODE(CAB.NUM_PED_VTA, NULL,'N','S') INTO v_Ind_comp_credito
                                FROM  VTA_PEDIDO_VTA_CAB CAB,MAE_CONVENIO CONV
                                WHERE CAB.COD_CONVENIO    = CONV.COD_CONVENIO
                                AND   CAB.COD_GRUPO_CIA   = cCodGrupoCia_in
                                AND   CAB.COD_LOCAL       = cCodLocal_in
                                AND   CAB.NUM_PED_VTA     = cNumPedVta_in
                                AND   CONV.FLG_CONV_MIXTO = '1'
                                AND   EXISTS (
                                           SELECT *
                                           FROM VTA_FORMA_PAGO_PEDIDO P
                                           WHERE P.NUM_PED_VTA=CAB.NUM_PED_VTA
                                           AND   P.COD_FORMA_PAGO='00050'
                                           );
                              EXCEPTION
                                WHEN NO_DATA_FOUND THEN
                                  v_Ind_comp_credito :='N';
                              END;

                              v_VAL_COPAGO_COMP_PAGO :=  BTLMF_FLOAT_OBT_MTO_CRED_COMP(cCodGrupoCia_in,cCodLocal_in,v_MontoNeto,v_VAL_NETO_COMP_PAGO,cNumPedVta_in,vPCT_BENEFICIARIO);
                              v_VAL_NETO_COMP_PAGO   :=  v_VAL_NETO_COMP_PAGO - v_VAL_COPAGO_COMP_PAGO;
                              v_VAL_IGV_COMP_PAGO  := (v_VAL_NETO_COMP_PAGO -(v_VAL_NETO_COMP_PAGO / (1 + (VALOR_IGV/ 100))));

                           ELSE
                               IF pTipoClienteConvenio = COMP_EMPRESA THEN
                                  dbms_output.put_line('----------EMPRESA-------');
                                  v_Ind_comp_credito    := 'S';
                                   v_VAL_NETO_COMP_PAGO := BTLMF_FLOAT_OBT_MTO_CRED_COMP(cCodGrupoCia_in,cCodLocal_in,v_MontoNeto,v_VAL_NETO_COMP_PAGO,cNumPedVta_in,vPCT_BENEFICIARIO);
                                   v_VAL_IGV_COMP_PAGO  := (v_VAL_NETO_COMP_PAGO -(v_VAL_NETO_COMP_PAGO / (1 + (VALOR_IGV/ 100))));
                                       v_CodigoProd_Pedido := trim(v_CodigoProd_Pedido);
                                       dbms_output.put_line('-'||v_CodigoProd_Pedido||'-');

                                       ----------DUBILLUZ INICIO
                                        SELECT P.NUM_COMP_PAGO,P.VAL_NETO_COMP_PAGO,P.VAL_IGV_COMP_PAGO,P.TIP_COMP_PAGO
                                          INTO v_VAL_NUM_COMP_COPAGO,v_VAL_COPAGO_COMP_PAGO,v_VAL_IGV_COMP_COPAGO,v_Tip_comp_pago_ref
                                          FROM VTA_COMP_PAGO P
                                         WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND P.COD_LOCAL = cCodLocal_in
                                           AND P.NUM_PED_VTA = cNumPedVta_in
                                           AND P.SEC_COMP_PAGO =
                                               (SELECT DISTINCT D.SEC_COMP_PAGO_BENEF
                                                  FROM VTA_PEDIDO_VTA_DET D
                                                 WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
                                                   AND D.COD_LOCAL = cCodLocal_in
                                                   AND D.NUM_PED_VTA = cNumPedVta_in
                                                   AND (D.COD_PROD, D.SEC_PED_VTA_DET) IN
                                                       (select Substr(ves.texto, 1, 6) cod_prod,
                                                               Substr(ves.texto, 8) * 1 sec_prod_det
                                                          from (SELECT EXTRACTVALUE(xt.column_value, 'e') texto
                                                                  FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                                                                                         REPLACE(v_CodigoProd_Pedido,
                                                                                                                 ',',
                                                                                                                 '</e><e>') ||
                                                                                                         '</e></coll>'),
                                                                                                 '/coll/e'))) xt) ves));
                                       ----------DUBILLUZ FIN



                                       UPDATE VTA_COMP_PAGO P
                                        SET
                                          P.NUM_COMP_COPAGO_REF = v_NUM_COMP_PAGO,
                                          P.TIP_COMP_PAGO_REF   = v_Tip_comp_pago_ref,
                                          P.Val_Igv_Comp_Copago = TO_NUMBER(TO_CHAR(v_VAL_IGV_COMP_PAGO,'999,990.00'),'999,990.00')
                                        WHERE p.COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND p.COD_LOCAL = cCodLocal_in
                                        AND p.NUM_PED_VTA   = cNumPedVta_in
                                        AND P.TIP_CLIEN_CONVENIO = COMP_BENEF_CAMPO
                                        AND P.NUM_COMP_PAGO = v_VAL_NUM_COMP_COPAGO;

                             END IF;
                           END IF;
                   ----fin
               END IF;

           ELSE
           --TIPO CONVENIO CONTADO
                   v_Ind_comp_credito   := 'N';
                   v_NUM_COMP_PAGO      :=  BTLMF_OBT_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in,cCodLocal_in,vSecImprLocal_in,pTIP_COMP_PAGO);
                   v_VAL_NETO_COMP_PAGO :=  v_VAL_NETO_COMP_PAGO;
                   v_VAL_IGV_COMP_PAGO  := (v_VAL_NETO_COMP_PAGO -(v_VAL_NETO_COMP_PAGO / (1 + (VALOR_IGV/ 100))));
           END IF;

            v_nSecCompPago := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in);
             -- ERIOS 11.05.2016 Se recupera la serie auxiliar
	 v_nNumCompPago := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nSecCompPago, 7, 0, 'I');
	 v_nNumCompPago := PTOVENTA_CAJ_ANUL.F_GET_SERIE_AUX_LOCAL(cCodGrupoCia_in, cCodLocal_in)||TRIM(v_nNumCompPago);	  
             v_nSecCompPago := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nSecCompPago, 10, 0, POS_INICIO);


            --Descripcion: Si el proceso es electronico, el numero de comprobante es el sec del comprobante
            --Fecha       Usuario        Comentario
            --27/07/2014  LTAVARA         Creacion

               IF  cFlagElectronico='1' AND pTIP_COMP_PAGO != '03' THEN
              -- v_NUM_COMP_PAGO:= v_nSecCompPago;
                 v_NUM_COMP_PAGO:= v_nNumCompPago; -- LTAVARA 01.10.2014
               END IF;




             BTLMF_P_INSERT_COMP_PAGO(cCodGrupoCia_in, cCodLocal_in,cNumPedVta_in,v_nSecCompPago,pTIP_COMP_PAGO ,
                                      v_NUM_COMP_PAGO, pSEC_MOV_CAJA, v_nItem, v_cCodCliLocal,
                                      v_cNomCliPedVta, v_cDirCliPedVta, v_cRucCliPedVta, TO_CHAR(v_VAL_BRUTO_COMP_PAGO,'999,990.00') ,
                                      TO_CHAR(v_VAL_NETO_COMP_PAGO,'999,990.00'), TO_CHAR(v_VAL_DCTO_COMP_PAGO,'999,990.00'), TO_CHAR(v_VAL_AFECTO_COMP_PAGO,'999,990.00'),
                                      TO_CHAR(v_VAL_IGV_COMP_PAGO,'999,990.00'), 0, cUsuCreaCompPago_in, TO_CHAR(v_PORC_IGV_COMP_PAGO,'990.00'),pTipoClienteConvenio,
                                      v_VAL_COPAGO_COMP_PAGO,v_VAL_NUM_COMP_COPAGO,TO_CHAR(v_VAL_IGV_COMP_COPAGO,'999,990.00'),pIndAfectaKardex,
                                      vPCT_BENEFICIARIO,vPCT_EMPRESA,v_Ind_comp_credito,v_Tip_comp_pago_ref,pFlgTipoConvenio,vIND_CON_IGV_in,
                                      -- KMONCADA 22.04.2016 PERCEPCION
                                      vValMontoPercepcion, vValPCTPercepcion, vValCPRedondeoPercepcion
                                     );

             --Descripcion: Si el proceso NO es electronico, incrementa el numero de comproante en 1
             -- Cuando el documento es una Guia y esta cativo el flag debe incrementar el numero
            --Fecha       Usuario        Comentario
            --27/07/2014  LTAVARA         Creacion

               IF  cFlagElectronico='0' OR  (cFlagElectronico='1' AND pTIP_COMP_PAGO = '03') THEN

                BTLMF_P_GENERA_IMPR_NUM_COMP(cCodGrupoCia_in, cCodLocal_in, cUsuCreaCompPago_in,pTIP_COMP_PAGO,vSecImprLocal_in);

               END IF;


             --ACTUALIZA DETALLE PEDIDO
             --SEC_COMP_PAGO = '||v_nSecCompPago||','||

             IF pIndAfectaKardex = 'S' THEN
                             DBMS_OUTPUT.put_line('-AAA-');
                  vQuery :=  'SEC_COMP_PAGO = '||chr(39)||v_nSecCompPago||chr(39)||',';
                 DBMS_OUTPUT.put_line('-'||vQuery||'-'||'NUM COMPAGO'||v_NUM_COMP_PAGO);
             END IF;
               DBMS_OUTPUT.put_line('-BBB-');

             -- kmoncada 23.07.2014
             v_CodigoProd_Pedido := TRIM(v_CodigoProd_Pedido);
             FOR LISTA IN (
               SELECT SUBSTR(ves.texto, 1, 6) COD_PROD,
                      SUBSTR(ves.texto, 8) * 1 SEC_PROD_DET
               FROM   (
                      SELECT EXTRACTVALUE(xt.column_value, 'e') TEXTO
                      FROM   TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE(v_CodigoProd_Pedido,',','</e><e>') ||
                                                               '</e></coll>'),
                                                       '/coll/e'))) xt
                      ) ves) LOOP

                       IF pTipoClienteConvenio = COMP_BENEFICIARIO THEN
                       DBMS_OUTPUT.put_line('-D'||pIndAfectaKardex||'D-');

                        --ERIOS 13.11.2014 Cambio de JLUNA
                          EXECUTE IMMEDIATE
                             ' UPDATE VTA_PEDIDO_VTA_DET
                                     SET
                                      USU_MOD_PED_VTA_DET = '||chr(39)||cUsuCreaCompPago_in||chr(39)||','||
                                     'FEC_MOD_PED_VTA_DET = SYSDATE'||','||vQuery||
                                     'SEC_COMP_PAGO_BENEF = '||chr(39)||v_nSecCompPago||chr(39)||','||
                                   'NUM_COMP_PAGO = '||chr(39)||v_NUM_COMP_PAGO||chr(39)||'
                             WHERE COD_GRUPO_CIA = '||chr(39)||cCodGrupoCia_in||chr(39)||'
                               AND COD_LOCAL = '||chr(39)||cCodLocal_in||chr(39)||'
                               AND NUM_PED_VTA = '||chr(39)||cNumPedVta_in||chr(39)||'
                               AND COD_PROD = '||chr(39)||LISTA.COD_PROD||chr(39)||'
                               AND SEC_PED_VTA_DET = '||chr(39)|| LISTA.SEC_PROD_DET||chr(39);

                       ELSE IF pTipoClienteConvenio = COMP_EMPRESA THEN
                          EXECUTE IMMEDIATE
                          --KMONCADA 14.04.2015 Cambio de JLUNA
                          ' UPDATE VTA_PEDIDO_VTA_DET
                               SET
                                    USU_MOD_PED_VTA_DET = '||chr(39)||cUsuCreaCompPago_in||chr(39)||','||
                                   'FEC_MOD_PED_VTA_DET = SYSDATE'||','||vQuery||
                                   'SEC_COMP_PAGO_EMPRE = '||chr(39)||v_nSecCompPago||chr(39)||','||
                                   'NUM_COMP_PAGO = '||chr(39)||v_NUM_COMP_PAGO||chr(39)||
                             ' WHERE COD_GRUPO_CIA = '||chr(39)||cCodGrupoCia_in||chr(39)||'
                               AND COD_LOCAL = '||chr(39)||cCodLocal_in||chr(39)||'
                               AND NUM_PED_VTA = '||chr(39)||cNumPedVta_in||chr(39)||'
                               AND COD_PROD = '||chr(39)||LISTA.COD_PROD||chr(39)||'
                               AND SEC_PED_VTA_DET = '||chr(39)|| LISTA.SEC_PROD_DET||chr(39);
                          END IF;
                       END IF;
             end loop;

                    v_codigoProducto := '';
                    v_CodigoProd_Pedido := '';
                    v_VAL_BRUTO_COMP_PAGO  := 0;
                    v_VAL_NETO_COMP_PAGO   := 0;
                    v_VAL_DCTO_COMP_PAGO   := 0;
                    v_VAL_AFECTO_COMP_PAGO := 0;
                    v_VAL_IGV_COMP_PAGO    := 0;
                    -- KMONCADA 25.04.2016 PERCEPCION
                    vValPCTPercepcion := 0;
                    vValMontoPercepcion := 0;
                    vValCPRedondeoPercepcion := 0;

              Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, cCodNumera_in, cUsuCreaCompPago_in);

             v_Flg_Guardar_Comp := 'N';
             v_nItem :=0;

      END IF;


    --Aqui decidimos generar el comprobante

          -- Comparamaos el limite de productos permitidos por el comprobante.
       /*   IF  v_nCantidadItem > V_NUM_PRODUCTOS THEN
                 v_nContador := 0;
                 v_nCantidadItem := v_nCantidadItem - V_NUM_PRODUCTOS;
                 v_nContador := v_nCantidadItem;
                 v_nItem := V_NUM_PRODUCTOS;
                 v_flg_activa :='N';
          --ELSE
            --     v_nItem := v_nCantidadItem;
                 --v_nCantidadItem := 0;
              --   v_flg_activa :='N';
          END IF;*/


       --END IF;




 END LOOP;
  close curDetPedido;

 open curComprobantes;
 LOOP
 FETCH curComprobantes INTO fSC;
 EXIT WHEN curComprobantes%NOTFOUND;
             /* ****************************************************************** */
            --Descripcion: Actulizar los campos nuevo de la tabla VTA_COMP_PAGO
            --Fecha       Usuario        Comentario
            --22/07/2014  LTAVARA         Creacion
            --07/10/2014  DUBILLUZ        Modificacion
            FARMA_EPOS.sp_upd_comp_pago_e(fSC.Cod_Grupo_Cia,
                                                fSC.Cod_Local,
                                                fSC.Num_Ped_Vta,
                                                fSC.Sec_Comp_Pago,
                                                fSC.Tip_Clien_Convenio);
 END LOOP;
 close curComprobantes;


 END BTLMF_P_GRAB_COMP_PAGO_CON_IGV;

PROCEDURE BTLMF_P_ACT_ESTDO_PEDIDO(cCodGrupoCia_in   IN CHAR,
                                         cCodLocal_in      IN CHAR,
                                       cNumPedVta_in   IN CHAR

                                      )
  IS
  BEGIN
            UPDATE VTA_PEDIDO_VTA_CAB CAB
               SET CAB.EST_PED_VTA = 'C'
             WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
               AND CAB.COD_LOCAL = cCodLocal_in
               AND CAB.NUM_PED_VTA =cNumPedVta_in;

END;

  FUNCTION BTLMF_OBT_NUM_COMP_PAGO_IMPR(cCodGrupoCia_in   IN CHAR,
                                              cCodLocal_in     IN CHAR,
                                              cSecImprLocal_in IN number,
                                              cTipComp      IN CHAR)
  RETURN VARCHAR2
  IS
    --curCaj FarmaCursor;
    NUM_COMP varchar2(300);

  BEGIN

   IF cTipComp = COD_TIP_COMP_TICKET THEN

    SELECT IMPR_LOCAL.NUM_SERIE_LOCAL||IMPR_LOCAL.NUM_COMP
      INTO NUM_COMP
      FROM VTA_IMPR_LOCAL IMPR_LOCAL
     WHERE IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
       AND IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
       AND IMPR_LOCAL.TIP_COMP=cTipComp
       AND IMPR_LOCAL.SEC_IMPR_LOCAL = cSecImprLocal_in/*  FOR UPDATE*/; --Ya que habra mas de una ticketera de tipo 5

   ELSE
    --ERIOS 20.10.2015 Se filtra documentos activos.
    -- KMONCADA 23.05.2016 DOS IMPRESORAS POR TIPO DE COMPROBANTE
    SELECT IMPR_LOCAL.NUM_SERIE_LOCAL||IMPR_LOCAL.NUM_COMP
      INTO NUM_COMP
      FROM VTA_IMPR_LOCAL IMPR_LOCAL
     WHERE IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
       AND IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
       AND IMPR_LOCAL.TIP_COMP       = cTipComp
	   AND IMPR_LOCAL.EST_IMPR_LOCAL = 'A'
     AND IMPR_LOCAL.SEC_IMPR_LOCAL = (
           -- KMONCADA 24.05.2016 PARA CASOS DE GUIAS SOLO CONSIDERARA LA REGISTRADA EN VTA_IMPR_LOCAL
           SELECT X.SEC_IMPR_LOCAL
           FROM VTA_IMPR_LOCAL X
           WHERE X.COD_GRUPO_CIA = IMPR_LOCAL.COD_GRUPO_CIA
           AND X.COD_LOCAL = IMPR_LOCAL.COD_LOCAL
           AND cTipComp = '03'
           AND X.EST_IMPR_LOCAL = 'A'
           AND X.TIP_COMP = '03'
                 
           UNION ALL
                 
           SELECT 
                CASE 
                  WHEN IMPR_LOCAL.TIP_COMP = '01' THEN
                    A.SEC_IMPR_LOCAL
                  WHEN IMPR_LOCAL.TIP_COMP = '02' THEN
                    A.SEC_IMPR_LOCAL_FAC
                  ELSE
                    A.SEC_IMPR_LOCAL
                END    
           FROM VTA_IMPR_IP A
           WHERE A.COD_GRUPO_CIA = IMPR_LOCAL.COD_GRUPO_CIA
           AND A.COD_LOCAL = IMPR_LOCAL.COD_LOCAL
           AND cTipComp != '03'
           AND TRIM(A.IP) = TRIM(SYS_CONTEXT('USERENV', 'IP_ADDRESS'))
     )/* FOR UPDATE*/;

   END IF;
    RETURN NUM_COMP;
  END;

  --Descripcion : Retorna la lista de precio por convenios
  --Fecha         Usuario    Comentario
  --26.09.2014    ERIOS      Se agrega parametro vSecImprLocal_in
 PROCEDURE BTLMF_P_GENERA_IMPR_NUM_COMP(cCodGrupoCia_in   IN CHAR,
                                            cCodLocal_in      IN CHAR,
                                         cUsuModImprLocal_in   IN CHAR,
                                         cTipComp IN CHAR,
                     vSecImprLocal_in vta_impr_local.sec_impr_local%type
                                         )
  IS
  BEGIN
    --ERIOS 2.4.6 Control por tipo de documento
    UPDATE VTA_IMPR_LOCAL
       SET FEC_MOD_IMPR_LOCAL = SYSDATE,
           USU_MOD_IMPR_LOCAL = cUsuModImprLocal_in,
           NUM_COMP = TRIM(TO_CHAR((TO_NUMBER(NUM_COMP) + 1),'0000000'))
     WHERE COD_GRUPO_CIA      = cCodGrupoCia_in
       AND COD_LOCAL          = cCodLocal_in
       AND TIP_COMP     = cTipComp
     AND ((cTipComp = COD_TIP_COMP_TICKET AND SEC_IMPR_LOCAL = vSecImprLocal_in)
          OR (cTipComp != COD_TIP_COMP_TICKET));
END;


PROCEDURE BTLMF_P_ACTUALIZA_STK_PROD(cCodGrupoCia_in      IN CHAR,
                                       cCodLocal_in         IN CHAR,
                                      cNumPedVta_in        IN CHAR,
                                     cCodMotKardex_in    IN CHAR,
                                      cTipDocKardex_in    IN CHAR,
                                     cCodNumeraKardex_in IN CHAR,
                                      cUsuModProdLocal_in IN CHAR,
                                     cTipComp_in         IN CHAR,
                                     cNumComp_in         IN CHAR
                                    )
  IS
    v_cIndProdVirtual CHAR(1);
    mesg_body VARCHAR2(4000);
  CURSOR productos_Kardex IS
         SELECT VTA_DET.COD_PROD,
                SUM(VTA_DET.CANT_FRAC_LOCAL) CANT_ATENDIDA, --ERIOS 29/05/2008 Cantidad calculada
                PROD_LOCAL.STK_FISICO,
                 PROD_LOCAL.VAL_FRAC_LOCAL,
                 PROD_LOCAL.UNID_VTA,
                CASE 
                  WHEN PTOVENTA_VTA.F_IS_PROD_CENTRO_MEDICO(VTA_DET.COD_GRUPO_CIA, VTA_DET.COD_LOCAL, VTA_DET.COD_PROD) = 'S' THEN 'S'
                  ELSE DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) 
                END IND_PROD_VIR
         FROM   VTA_PEDIDO_VTA_DET VTA_DET,
                LGT_PROD_LOCAL PROD_LOCAL,
                LGT_PROD_VIRTUAL VIR
         WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    VTA_DET.COD_LOCAL     = cCodLocal_in
         AND    VTA_DET.NUM_PED_VTA   = cNumPedVta_in
         AND    VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
         AND    VTA_DET.COD_LOCAL     = PROD_LOCAL.COD_LOCAL
         AND    VTA_DET.COD_PROD      = PROD_LOCAL.COD_PROD
         AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
         AND    PROD_LOCAL.COD_PROD = VIR.COD_PROD(+)
         AND    VTA_DET.Num_Comp_Pago = cNumComp_in
         --INI ASOSA - 17/10/2014 - PANHD
             AND  NOT EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND VTA_DET.COD_PROD = PP.COD_PROD
                                                                    AND VTA_DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
             --FIN ASOSA - 17/10/2014 - PANHD
         GROUP BY VTA_DET.COD_GRUPO_CIA, VTA_DET.COD_LOCAL,VTA_DET.COD_PROD,
                   PROD_LOCAL.VAL_FRAC_LOCAL,
                   PROD_LOCAL.UNID_VTA,
                  PROD_LOCAL.STK_FISICO,
                  DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI);
  --21/11/2007 dubilluz modificado
  CURSOR productos_Respaldo IS
         SELECT VTA_DET.COD_PROD,
                VTA_DET.CANT_ATENDIDA CANT_ATENDIDA,
                VTA_DET.VAL_FRAC AS VAL_FRAC_VTA,
                PROD_LOCAL.STK_FISICO,
                PROD_LOCAL.VAL_FRAC_LOCAL,
                PROD_LOCAL.UNID_VTA,
                CASE 
                  WHEN PTOVENTA_VTA.F_IS_PROD_CENTRO_MEDICO(VTA_DET.COD_GRUPO_CIA, VTA_DET.COD_LOCAL, VTA_DET.COD_PROD) = 'S' THEN 'S'
                  ELSE DECODE(NVL(VIR.COD_PROD,INDICADOR_NO),INDICADOR_NO,INDICADOR_NO,INDICADOR_SI) 
                END IND_PROD_VIR,
                VTA_DET.SEC_RESPALDO_STK --ASOSA, 11.07.2010
         FROM   VTA_PEDIDO_VTA_DET VTA_DET,
                LGT_PROD_LOCAL PROD_LOCAL,
                LGT_PROD_VIRTUAL VIR
         WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    VTA_DET.COD_LOCAL = cCodLocal_in
         AND    VTA_DET.NUM_PED_VTA = cNumPedVta_in
         AND    VTA_DET.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
         AND    VTA_DET.COD_LOCAL = PROD_LOCAL.COD_LOCAL
         AND    VTA_DET.COD_PROD = PROD_LOCAL.COD_PROD
         AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
         AND    PROD_LOCAL.COD_PROD = VIR.COD_PROD(+)
         AND    VTA_DET.Num_Comp_Pago = cNumComp_in
         --INI ASOSA - 17/10/2014 - PANHD
             AND  NOT EXISTS (
                                                                    SELECT 1
                                                                    FROM LGT_PROD PP
                                                                    WHERE PP.IND_TIPO_CONSUMO = TIPO_PROD_FINAL
                                                                    AND VTA_DET.COD_PROD = PP.COD_PROD
                                                                    AND VTA_DET.COD_GRUPO_CIA = PP.COD_GRUPO_CIA
                                                                    )
             --FIN ASOSA - 17/10/2014 - PANHD
         ORDER  BY VTA_DET.CANT_ATENDIDA DESC;

    v_nCantAtendida VTA_PEDIDO_VTA_DET.CANT_ATENDIDA%TYPE;

    stkComp lgt_prod_local.stk_fisico%TYPE;
            --lgt_prod_local.stk_comprometido%TYPE;
    stkFis  lgt_prod_local.stk_fisico%TYPE;
    fecMod  lgt_prod_local.fec_mod_prod_local%TYPE;
    usuMod  lgt_prod_local.usu_mod_prod_local%TYPE;
  BEGIN
    --v_cIndActStk := INDICADOR_NO;
     FOR productos_K IN productos_Kardex
    LOOP
        --GRABAR KARDEX
        v_cIndProdVirtual := productos_K.IND_PROD_VIR;
        IF v_cIndProdVirtual = INDICADOR_SI THEN
          Ptoventa_Inv.INV_GRABAR_KARDEX_VIRTUAL(cCodGrupoCia_in,
                                                 cCodLocal_in,
                                                 productos_K.COD_PROD,
                                                 cCodMotKardex_in,
                                                 cTipDocKardex_in,
                                                 cNumPedVta_in,
                                                 (productos_k.CANT_ATENDIDA * -1),
                                                 productos_K.VAL_FRAC_LOCAL,
                                                 productos_K.UNID_VTA,
                                                 cUsuModProdLocal_in,
                                                 cCodNumeraKardex_in,
                                                 '',
                                                 cTipComp_in,
                                                 cNumComp_in
                                                 );
        ELSE
          Ptoventa_Inv.INV_GRABAR_KARDEX(cCodGrupoCia_in,
                                         cCodLocal_in,
                                         productos_K.COD_PROD,
                                         cCodMotKardex_in,
                                         cTipDocKardex_in,
                                         cNumPedVta_in,
                                         productos_K.STK_FISICO,
                                         (productos_k.CANT_ATENDIDA * -1),
                                         productos_K.VAL_FRAC_LOCAL,
                                         productos_K.UNID_VTA,
                                         cUsuModProdLocal_in,
                                         cCodNumeraKardex_in,
                                         '',
                                         cTipComp_in,
                                         cNumComp_in
                                         );
        END IF;
        --v_cIndActStk := INDICADOR_SI;
    END LOOP;
    FOR productos_R IN productos_Respaldo
    LOOP
        v_cIndProdVirtual := productos_R.IND_PROD_VIR;
        IF v_cIndProdVirtual = INDICADOR_NO THEN

          SELECT USU_MOD_PROD_LOCAL, FEC_MOD_PROD_LOCAL, STK_FISICO, 0--STK_COMPROMETIDO --ASOSA, 11.07.2010
          INTO usuMod, fecMod, stkFis, stkComp
          FROM LGT_PROD_LOCAL
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND     COD_LOCAL = cCodLocal_in
          AND     COD_PROD = productos_R.COD_PROD FOR UPDATE;

          --ACTUALIZA STK PRODUCTO
          --ERIOS 29/05/2008 Calcula la cantidad atendida a la fraccion del local.
          v_nCantAtendida := (productos_R.CANT_ATENDIDA*productos_R.VAL_FRAC_LOCAL)/productos_R.VAL_FRAC_VTA;
          UPDATE LGT_PROD_LOCAL SET USU_MOD_PROD_LOCAL = cUsuModProdLocal_in, FEC_MOD_PROD_LOCAL = SYSDATE,
                 STK_FISICO = STK_FISICO - v_nCantAtendida--,
                 --STK_COMPROMETIDO = STK_COMPROMETIDO - v_nCantAtendida
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND     COD_LOCAL = cCodLocal_in
          AND     COD_PROD = productos_R.COD_PROD;

        END IF;
    END LOOP;

  EXCEPTION
    WHEN OTHERS THEN
         mesg_body := 'ERROR AL COBRAR PEDIDO No ' || cNumPedVta_in || '. ' || SQLERRM;
         FARMA_UTILITY.envia_correo(cCodGrupoCia_in,
                                    cCodLocal_in,
                                    'programacion@mifarma.com.pe',
                                    'ERROR AL COBRAR PEDIDO',
                                    'ERROR',
                                    mesg_body,
                                    '');
         RAISE;
  END;


 FUNCTION BTLMF_FLOAT_OBT_MTO_CRED_COMP(
                                        cCodGrupoCia_in   IN CHAR,
                                        cCodLocal_in      IN CHAR,
                                        cMontoNeto_in     IN FLOAT,
                                        cSubMontoNeto_in  IN FLOAT,
                                        cNroPedido_in     IN CHAR,
                                        cPorcBeneficiario IN CHAR DEFAULT NULL
                                       )
  RETURN FLOAT
  IS
    curEscala FarmaCursor;
    vMontoCreditoEmpre FLOAT := 0;
    vMontoRestoEmpre   FLOAT   := 0;
    vPorcentajeEmpre   FLOAT   := 0;




    vFlgTipoConvenio     MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE;
    vFlgTipoPago         MAE_CONVENIO.FLG_TIPO_PAGO%TYPE;
    vPctBenificiario     MAE_CONVENIO.PCT_BENEFICIARIO%TYPE;
    vPctEmpresa          MAE_CONVENIO.PCT_EMPRESA%TYPE;
    vCodConvenio         REL_CONVENIO_ESCALA.COD_CONVENIO%TYPE;
    vCodConvenioCab        VTA_PEDIDO_VTA_CAB.COD_CONVENIO%TYPE;

    vImp_minimo          REL_CONVENIO_ESCALA.IMP_MINIMO%TYPE;
    vImp_maximo          REL_CONVENIO_ESCALA.IMP_MAXIMO%TYPE;
    vFlg_porcentaje      REL_CONVENIO_ESCALA.FLG_PORCENTAJE%TYPE;
    vImp_monto           REL_CONVENIO_ESCALA.IMP_MONTO%TYPE;
    vFlgPolitica         MAE_CONVENIO.FLG_POLITICA%TYPE;

  v_nCopago FLOAT;
    v_nPctBenef mae_convenio.pct_beneficiario%TYPE;
    v_nPctEmp      mae_convenio.pct_empresa%TYPE;
  BEGIN

        SELECT C.COD_CONVENIO
          INTO vCodConvenioCab
          FROM VTA_PEDIDO_VTA_CAB C
         WHERE C.NUM_PED_VTA = cNroPedido_in;


         BEGIN

              SELECT
                     c.COD_TIPO_CONVENIO,
                     c.flg_tipo_pago,
                     c.pct_beneficiario,
                     c.pct_empresa,
                     c.flg_politica
                INTO vFlgTipoConvenio,
                     vFlgTipoPago,
                     vPctBenificiario,
                     vPctEmpresa,
                     vFlgPolitica
                FROM MAE_CONVENIO C
               WHERE
               --C.CIA = cCodGrupoCia_in
                 --AND
                  C.COD_CONVENIO = vCodConvenioCab;

           EXCEPTION
                WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20000, 'No encontro el convenio. ' || vCodConvenioCab);
         END;


         IF  vFlgTipoConvenio = FLG_TIP_CONV_COPAGO  THEN
             IF vFlgPolitica='0' AND cPorcBeneficiario>0 AND cPorcBeneficiario IS NOT NULL THEN
                 IF vFlgTipoPago = '0'  THEN
                         vMontoCreditoEmpre := (cSubMontoNeto_in*(100-cPorcBeneficiario))/100;
                 ELSE
                        -- vPorcentajeEmpre   := ROUND((100-cPorcBeneficiario)/cMontoNeto_in,2);
                         vPorcentajeEmpre   := ROUND((100-cPorcBeneficiario),2);---kmoncada 04.08.2014
                         vMontoCreditoEmpre := (cSubMontoNeto_in*vPorcentajeEmpre)/100;
                 END IF;
             ELSE
          --ERIOS 2.4.6 Calculo por escala
          v_nCopago := FN_CALCULA_COPAGO(vCodConvenioCab,cMontoNeto_in,v_nPctBenef,v_nPctEmp);
          IF v_nCopago <> -1 THEN
            vMontoCreditoEmpre := (cSubMontoNeto_in*v_nPctEmp)/100;
                   ELSE
                      IF vFlgTipoPago = '0'  THEN
                         vMontoCreditoEmpre := (cSubMontoNeto_in*vPctEmpresa)/100;
                      ELSE
                         vPorcentajeEmpre   := ROUND(vPctEmpresa/cMontoNeto_in,2);
                         vMontoCreditoEmpre := (cSubMontoNeto_in*vPorcentajeEmpre)/100;
                      END IF;
                  END IF;
             END IF;
         END IF;


     RETURN ROUND(vMontoCreditoEmpre,2);
  END;

  FUNCTION BTLMF_FLOAT_OBT_MONTO_CREDITO(
                                      cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cMontoNeto_in    IN FLOAT,
                                      cNroPedido_in    IN CHAR,
                                      cCodConvenio     IN CHAR
                                     )
  RETURN FLOAT
  IS
    curEscala FarmaCursor;
    vMontoCreditoEmpre FLOAT := 0;
    vFlgTipoConvenio     MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE;
    vFlgTipoPago         MAE_CONVENIO.FLG_TIPO_PAGO%TYPE;
    vPctBenificiario     MAE_CONVENIO.PCT_BENEFICIARIO%TYPE;
    vPctEmpresa          MAE_CONVENIO.PCT_EMPRESA%TYPE;
    vCodConvenio_Si_Escala REL_CONVENIO_ESCALA.COD_CONVENIO%TYPE;
    vCodConvenioCab      VTA_PEDIDO_VTA_CAB.COD_CONVENIO%TYPE;

    vImp_minimo          REL_CONVENIO_ESCALA.IMP_MINIMO%TYPE;
    vImp_maximo          REL_CONVENIO_ESCALA.IMP_MAXIMO%TYPE;
    vFlg_porcentaje      REL_CONVENIO_ESCALA.FLG_PORCENTAJE%TYPE;
    vImp_monto           REL_CONVENIO_ESCALA.IMP_MONTO%TYPE;

  v_nCopago FLOAT;
    v_nPctBenef mae_convenio.pct_beneficiario%TYPE;
    v_nPctEmp      mae_convenio.pct_empresa%TYPE;
  BEGIN
       IF cNroPedido_in IS NOT NULL THEN
          SELECT C.COD_CONVENIO
            INTO vCodConvenioCab
            FROM VTA_PEDIDO_VTA_CAB C
          WHERE C.NUM_PED_VTA = cNroPedido_in;
       ELSE
          vCodConvenioCab := cCodConvenio;
       END IF;

         BEGIN

              SELECT
                     c.COD_TIPO_CONVENIO,
                     c.flg_tipo_pago,
                     c.pct_beneficiario,
                     c.pct_empresa
                INTO vFlgTipoConvenio,
                     vFlgTipoPago,
                     vPctBenificiario,
                     vPctEmpresa
                FROM MAE_CONVENIO C
               WHERE
               --C.CIA = cCodGrupoCia_in
                 --AND
                  C.COD_CONVENIO = vCodConvenioCab;

           EXCEPTION
                WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20000, 'No encontro el convenio. ' || vCodConvenioCab);
         END;



         IF  vFlgTipoConvenio = FLG_TIP_CONV_COPAGO THEN


      --ERIOS 2.4.6 Calculo por escala
      v_nCopago := FN_CALCULA_COPAGO(vCodConvenioCab,cMontoNeto_in,v_nPctBenef,v_nPctEmp);
      IF v_nCopago <> -1 THEN
        vMontoCreditoEmpre := cMontoNeto_in - v_nCopago;
             ELSE
                IF vFlgTipoPago = '0'  THEN
                   vMontoCreditoEmpre := (cMontoNeto_in*vPctEmpresa)/100;
                ELSE
                   vMontoCreditoEmpre := vPctEmpresa;
                END IF;
            END IF;
         END IF;

         IF vFlgTipoConvenio = FLG_TIP_CONV_CREDITO THEN
            vMontoCreditoEmpre := (cMontoNeto_in*100)/100;
         END IF;


     RETURN ROUND(vMontoCreditoEmpre,2);
  END;




  FUNCTION BTLMF_F_CHAR_OBT_FORM_PAGO(cCodGrupoCia_in CHAR,
                                            cCodLocal_in CHAR,
                                            cSecUsuLocal_in CHAR,
                                            cCodFormaPago CHAR)
   RETURN CHAR
    IS
      vDesFormaPago VTA_FORMA_PAGO.DESC_FORMA_PAGO%TYPE;
    BEGIN
            SELECT
                   F.DESC_FORMA_PAGO
              INTO vDesFormaPago
              FROM VTA_FORMA_PAGO F
             WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
               AND F.COD_FORMA_PAGO = cCodFormaPago;

      RETURN vDesFormaPago;
    END;

    FUNCTION BTLMF_F_CUR_LISTA_COMP_PAGO(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         pNroPedido       IN VARCHAR2,
                                         pTipoClienteConv IN CHAR
                                         )
        RETURN FarmaCursor IS
        curComp FarmaCursor;
		v_vTipoDocCliente mae_convenio.cod_tipdoc_cliente%type;
		v_cFlgConvMixto mae_convenio.FLG_CONV_MIXTO%type;
      BEGIN
		--ERIOS 2.4.7 Se imprime datos adicionales en los ticket de Conv. Mixto.
		select NVL(cod_tipdoc_cliente,'TKB'),FLG_CONV_MIXTO
			INTO v_vTipoDocCliente,v_cFlgConvMixto
		from mae_convenio
		WHERE COD_CONVENIO =
		(SELECT COD_CONVENIO FROM VTA_PEDIDO_VTA_CAB
		WHERE NUM_PED_VTA = pNroPedido);

        --ERIOS 2.4.3 Se agrega el porcentaje
        OPEN curComp FOR
          SELECT P.NUM_COMP_PAGO || 'Ã' ||
                 P.SEC_COMP_PAGO || 'Ã' ||
                 P.TIP_COMP_PAGO || 'Ã' ||
                 NVL(TO_CHAR(P.VAL_IGV_COMP_PAGO,'999,990.00'),0)    || 'Ã' ||
                 NVL(TO_CHAR(P.VAL_NETO_COMP_PAGO,'999,990.00'),0)   || 'Ã' ||
                 NVL(TO_CHAR(P.VAL_COPAGO_COMP_PAGO,'999,990.00'),0) || 'Ã' ||
                 NVL(TO_CHAR(P.Val_Igv_Comp_Copago,'999,990.00'),0)  || 'Ã' ||
                 NVL(p.num_comp_copago_ref,' ') || 'Ã' ||
                 P.TIP_CLIEN_CONVENIO || 'Ã' ||
                 DECODE(v_cFlgConvMixto,'1','1',NVL(btl.flg_imp_dato_adic,'0'))|| 'Ã' ||
                 P.COD_TIPO_CONVENIO || 'Ã' ||
                 TO_CHAR(p.FEC_CREA_COMP_PAGO,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                 nvl(p.tip_comp_pago_ref,' ')|| 'Ã' ||
                 NVL(TO_CHAR(P.VAL_REDONDEO_COMP_PAGO,'999,990.00'),0) || 'Ã' || --VAL_REDONDEO_COMP_PAGO
                 TO_CHAR(P.PCT_BENEFICIARIO,'999,990.00') || 'Ã' ||
                 TO_CHAR(P.PCT_EMPRESA,'999,990.00') || 'Ã' ||
         decode(v_vTipoDocCliente,btl.cod_tipodoc,'1','0')|| 'Ã' ||
                 NVL(P.NUM_COMP_PAGO_E,0) -- LTAVARA 18.09.2014 SE ENVIA EL NUMERO DE COMPROBANTE ELECTRONICO
            FROM VTA_COMP_PAGO P,
                 mae_tipo_comp_pago_btlmf btl
           WHERE P.NUM_PED_VTA = pNroPedido
             AND btl.tip_comp_pago = p.tip_comp_pago
             -- KMONCADA 17.11.2014 SE CREA OTRO METODO PARA LISTA DE IMPRESION Y REIMPRESION
--             AND  P.FECH_IMP_COBRO IS NULL -- rherrera indicador impreso
            ORDER BY  P.TIP_CLIEN_CONVENIO,P.NUM_COMP_PAGO ASC ;
             --AND P.TIP_CLIEN_CONVENIO = pTipoClienteConv;
        RETURN curComp;
    END;

    FUNCTION BTLMF_F_CUR_LIST_DET_COMP_PAGO(cCodGrupoCia_in  IN CHAR,
                                             cCodLocal_in     IN CHAR,
                                             pNroPedido       IN VARCHAR2,
                                             PSecCompPago     IN CHAR,
                                             pTipCompPago     IN CHAR,
                                             pTipClienteConv  IN CHAR
                                           )
        RETURN FarmaCursor IS
        curCompDeta FarmaCursor;

        vIndAfectoIGV CHAR(1);
        vTip_Ped_vta  CHAR(2);
        vNumPedOrig   CHAR(10);
      BEGIN



         SELECT V.IND_AFECTO_IGV
           INTO vIndAfectoIGV
           FROM VTA_COMP_PAGO V
          WHERE V.COD_GRUPO_CIA  = cCodGrupoCia_in
           AND  V.COD_LOCAL      = cCodLocal_in
           AND  V.NUM_PED_VTA    = pNroPedido
           AND  V.SEC_COMP_PAGO  = PSecCompPago
           AND  V.TIP_COMP_PAGO  = pTipCompPago
           AND  V.TIP_CLIEN_CONVENIO = pTipClienteConv;
           /*rherrer: vta empresa , datos para busqueda*/
           SELECT TIP_PED_VTA
           INTO   vTip_Ped_vta
           FROM VTA_PEDIDO_VTA_CAB
           WHERE COD_GRUPO_CIA   =   cCodGrupoCia_in
           AND   COD_LOCAL       =   cCodLocal_in
           AND   NUM_PED_VTA     =   pNroPedido ;

           ---           ////         ---


IF vTip_Ped_vta = '03' AND --venta empresa rherrera 26062014
   (pTipCompPago = '03' OR pTipCompPago = '02')
   THEN

           SELECT NUM_PED_VTA
           INTO vNumPedOrig
           FROM TMP_VTA_PEDIDO_VTA_CAB
           WHERE COD_GRUPO_CIA        = cCodGrupoCia_in
           AND   COD_LOCAL            = cCodLocal_in
           AND   NUM_PED_VTA_ORIGEN   = pNroPedido;



   IF   pTipClienteConv = COMP_BENEF_CAMPO THEN

                OPEN curCompDeta FOR
                SELECT
                DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)||'',
                        --   VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC))
                           VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'',''))
                                                                       || 'Ã' ||
                 CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
                 THEN  SUBSTR(4,1,9) ||' '|| PROD.DESC_PROD
                 ELSE  PROD.DESC_PROD
                 END                                                   || 'Ã' ||
                 DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_UNID_PRESENT,
                                                                    VTA_DET.UNID_VTA                                      )
                 || 'Ã' ||
                 SUBSTR(NVL(LAB.NOM_LAB,'.'),1,5)
                 || 'Ã' ||
                 CASE  WHEN vIndAfectoIGV='N'
                  THEN  TO_CHAR(round(VTA_DET.val_prec_vta,3),'999,990.000')
                 ELSE  TO_CHAR(round(VTA_DET.val_prec_vta*100/(VTA_DET.VAL_IGV+100),3),'999,990.000')
                 END
                 || 'Ã' ||
                  CASE  WHEN vIndAfectoIGV='N'
                   THEN TO_CHAR(round(VTA_DET.val_prec_total,3),'999,990.000')
                  ELSE TO_CHAR(round(VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100),3),'999,990.000')
                  END
                 || 'Ã' ||
                 VTA_DET.COD_PROD                                       || 'Ã' ||
                 NVL(VTA_DET.NUM_LOTE_PROD,' ')                         || 'Ã' ||
                 DECODE(NVL(VIR.COD_PROD,'N'),'N','N','S')              || 'Ã' ||
                 NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
                 NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
                 NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
                 NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
                 NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
                 NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ') || 'Ã' ||
                 DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_PROD || ' / ' || PROD.DESC_UNID_PRESENT,
                 PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA)  || 'Ã' ||
                 TO_CHAR(VTA_DET.AHORRO,'999,990.000')         || 'Ã' ||
                 TO_CHAR(round(VTA_DET.val_prec_vta*100/(VTA_DET.VAL_IGV+100),3)*VTA_DET.CANT_ATENDIDA,'999,990.000') || 'Ã' ||
                  CASE  WHEN vIndAfectoIGV='N'
                   THEN   TO_CHAR(round(0,3),'999,990.000')
                  ELSE   TO_CHAR(round(VTA_DET.val_prec_total - VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100),3),'999,990.000')
                  END
                /*RHERRERA 26/06/2014: OBTENER LOTE DE PRODUCTO*/
                  || 'Ã' ||
                  NVL(VTA_DET.NUM_LOTE_PROD,' ') || 'Ã' ||
                  NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'DD/MM/YYYY'),' ')
           FROM  VTA_PEDIDO_VTA_DET VTA_DET,
                 LGT_PROD_LOCAL PROD_LOCAL,
                 LGT_PROD PROD,
                 LGT_LAB LAB,
                 LGT_PROD_VIRTUAL VIR
--                 , tmp_vta_institucional_det TMP --TABLA DE LOTES
          WHERE  VTA_DET.COD_GRUPO_CIA          = cCodGrupoCia_in
          AND     VTA_DET.COD_LOCAL              = cCodLocal_in
          AND     VTA_DET.NUM_PED_VTA            = pNroPedido
          AND    VTA_DET.SEC_COMP_PAGO_BENEF    = PSecCompPago
--          AND     TMP.NUM_PED_VTA               = vNumPedOrig
--          AND     VTA_DET.COD_PROD         = TMP.COD_PROD
          AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
          AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
          AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
          AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
          AND     PROD.COD_LAB             = LAB.COD_LAB
          AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
          AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
          ORDER BY VTA_DET.SEC_PED_VTA_DET;

          END IF;



          IF   pTipClienteConv = COMP_EMPRE_CAMPO THEN

                OPEN curCompDeta FOR
          SELECT

              DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)||'',
                        -- VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC))
                         VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'',''))
                                                                     || 'Ã' ||
               CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
               THEN  SUBSTR(4,1,9) ||' '|| PROD.DESC_PROD
               ELSE  PROD.DESC_PROD
               END                                                   || 'Ã' ||
               DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_UNID_PRESENT,
                                                                  VTA_DET.UNID_VTA                                      )
               || 'Ã' ||
               SUBSTR(NVL(LAB.NOM_LAB,'.'),1,5)

               || 'Ã' ||
               --CASE  WHEN vIndAfectoIGV='N'
               --THEN  TO_CHAR(round(VTA_DET.val_prec_vta,3),'999,990.000')
               --ELSE  TO_CHAR(round(VTA_DET.val_prec_vta*100/(VTA_DET.VAL_IGV+100),3),'999,990.000')
               --END   --rherrera 02.08.2014
                ------------------------------------------------------
                --CASE  WHEN vIndAfectoIGV='N'  THEN
                  --    (
                      CASE WHEN
                      MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC) = 0 THEN
                        TO_CHAR(round(VTA_DET.val_prec_vta,3)*VTA_DET.VAL_FRAC,'999,990.000')
                      ELSE
                        TO_CHAR(round(VTA_DET.val_prec_vta,3),'999,990.000')
                      END                                       --  )
               --ELSE
                  --   (CASE WHEN
                    --  MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC) = 0 THEN
                      --  TO_CHAR(round(VTA_DET.val_prec_vta*100/(VTA_DET.VAL_IGV+100),3)*VTA_DET.VAL_FRAC,'999,990.000') --se agrego *VTA_DET.VAL_FRAC
                     -- ELSE
                       -- TO_CHAR(round(VTA_DET.val_prec_vta*100/(VTA_DET.VAL_IGV+100),3),'999,990.000')
                     -- END    )
               --END----------------------------------
               || 'Ã' ||
               -- CASE  WHEN vIndAfectoIGV='N'
                 --THEN
                   TO_CHAR(round(VTA_DET.val_prec_total,2),'999,990.000')
                --ELSE TO_CHAR(round(VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100),3),'999,990.000')
                --END
               ---------------------------------------------------------------------------------
               || 'Ã' ||
               VTA_DET.COD_PROD                                       || 'Ã' ||
               NVL(VTA_DET.NUM_LOTE_PROD,' ')                         || 'Ã' ||
               DECODE(NVL(VIR.COD_PROD,'N'),'N','N','S')              || 'Ã' ||
               NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
               NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
               NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
               NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
               NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
               NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ') || 'Ã' ||
               DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_PROD || ' / ' || PROD.DESC_UNID_PRESENT,
               PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA)  || 'Ã' ||
               TO_CHAR(VTA_DET.AHORRO,'999,990.000')         || 'Ã' ||
               TO_CHAR(round(VTA_DET.val_prec_vta*100/(100 + FARMA_GRAL.F_GET_IGV_LOCAL),3)*VTA_DET.CANT_ATENDIDA,'999,990.000')|| 'Ã' ||--0.000  --ASOSA - 24/06/2015 - IGVAZNIA
               CASE  WHEN vIndAfectoIGV='N'
                 THEN   TO_CHAR(round(0,3),'999,990.000')
                ELSE    TO_CHAR(round(VTA_DET.val_prec_total - VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100),3),'999,990.000')
                END
         FROM  VTA_PEDIDO_VTA_DET VTA_DET,
               LGT_PROD_LOCAL PROD_LOCAL,
               LGT_PROD PROD,
               LGT_LAB LAB,
               LGT_PROD_VIRTUAL VIR
        WHERE  VTA_DET.COD_GRUPO_CIA          = cCodGrupoCia_in
        AND     VTA_DET.COD_LOCAL              = cCodLocal_in
        AND     VTA_DET.NUM_PED_VTA            = pNroPedido
        AND    VTA_DET.SEC_COMP_PAGO_EMPRE    = PSecCompPago
        AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
        AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
        AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
        AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
        AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
        AND     PROD.COD_LAB             = LAB.COD_LAB
        AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
        AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
        ORDER BY VTA_DET.SEC_PED_VTA_DET;

        END IF;


ELSE



       IF pTipCompPago = COD_TIP_COMP_TICKET THEN
           --CON IGV
               IF   pTipClienteConv = COMP_BENEF_CAMPO THEN

                    OPEN curCompDeta FOR
                     SELECT
                    DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)||'',
                               VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC))
                                                                           || 'Ã' ||
                     CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
                     THEN  SUBSTR(4,1,9) ||' '|| PROD.DESC_PROD
                     ELSE  PROD.DESC_PROD
                     END                                                   || 'Ã' ||
                     DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_UNID_PRESENT,
                                                                        VTA_DET.UNID_VTA                                      )|| 'Ã' ||

                     --LAB.NOM_LAB                                           || 'Ã' ||
                     ' '                                           || 'Ã' ||

                     (DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,
                                TO_CHAR(((VTA_DET.VAL_PREC_TOTAL+ nvl(vta_det.ahorro,0) )/(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)),'999,990.000'),
                                TO_CHAR((VTA_DET.VAL_PREC_VTA + nvl(vta_det.ahorro,0)),'999,990.000') ))--*(1-nvl(VTA_DET.Val_Igv,0)/100)
                                                                            || 'Ã' ||
                     TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')           || 'Ã' ||

                     VTA_DET.COD_PROD                                       || 'Ã' ||
                     NVL(VTA_DET.NUM_LOTE_PROD,' ')                         || 'Ã' ||
                     DECODE(NVL(VIR.COD_PROD,'N'),'N','N','S')              || 'Ã' ||
                     NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
                     NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
                     NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
                     NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ') || 'Ã' ||
                     DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_PROD || ' / ' || PROD.DESC_UNID_PRESENT,
                     PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA)  || 'Ã' ||
                     NVL(TO_CHAR(VTA_DET.AHORRO_CONV,'999,990.000'),'0')         || 'Ã' ||
                     TO_CHAR(PROD_LOCAL.VAL_PREC_VTA*VTA_DET.CANT_ATENDIDA,'999,990.00')
               FROM  VTA_PEDIDO_VTA_DET VTA_DET,
                     LGT_PROD_LOCAL PROD_LOCAL,
                     LGT_PROD PROD,
                     LGT_LAB LAB,
                     LGT_PROD_VIRTUAL VIR
              WHERE  VTA_DET.COD_GRUPO_CIA          = cCodGrupoCia_in
              AND     VTA_DET.COD_LOCAL              = cCodLocal_in
              AND     VTA_DET.NUM_PED_VTA            = pNroPedido
              AND    VTA_DET.SEC_COMP_PAGO_BENEF    = PSecCompPago
              AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
              AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
              AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
              AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
              AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
              AND     PROD.COD_LAB             = LAB.COD_LAB
              AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
              AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
              ORDER BY VTA_DET.SEC_PED_VTA_DET;

              END IF;
              IF   pTipClienteConv = COMP_EMPRE_CAMPO THEN

                        OPEN curCompDeta FOR
                        SELECT DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)||'',
                                                                VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC))
                                                                           || 'Ã' ||
                     CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
                     THEN  SUBSTR(4,1,9) ||' '|| PROD.DESC_PROD
                     ELSE  PROD.DESC_PROD
                     END                                                   || 'Ã' ||
                     DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_UNID_PRESENT,
                                                                        VTA_DET.UNID_VTA                                      )|| 'Ã' ||
                     --LAB.NOM_LAB                                           || 'Ã' ||
                     ' '                                           || 'Ã' ||

                     (DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,
                                TO_CHAR((VTA_DET.VAL_PREC_TOTAL+ nvl(vta_det.ahorro,0) )/(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC),'999,990.000'),
                                TO_CHAR(VTA_DET.VAL_PREC_VTA + nvl(vta_det.ahorro,0),'999,990.000') ))--*(1-nvl(VTA_DET.Val_Igv,0)/100)
                                                                            || 'Ã' ||
                     TO_CHAR(VTA_DET.VAL_PREC_TOTAL,'999,990.00')           || 'Ã' ||

                     VTA_DET.COD_PROD                                       || 'Ã' ||
                     NVL(VTA_DET.NUM_LOTE_PROD,' ')                         || 'Ã' ||
                     DECODE(NVL(VIR.COD_PROD,'N'),'N','N','S')              || 'Ã' ||
                     NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
                     NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
                     NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
                     NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ') || 'Ã' ||
                     DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_PROD || ' / ' || PROD.DESC_UNID_PRESENT,
                     PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA)  || 'Ã' ||
                     NVL(TO_CHAR(VTA_DET.AHORRO_CONV,'999,990.000'),'0')         || 'Ã' ||
                     TO_CHAR(PROD_LOCAL.VAL_PREC_VTA*VTA_DET.CANT_ATENDIDA,'999,990.00')


               FROM  VTA_PEDIDO_VTA_DET VTA_DET,
                     LGT_PROD_LOCAL PROD_LOCAL,
                     LGT_PROD PROD,
                     LGT_LAB LAB,
                     LGT_PROD_VIRTUAL VIR
              WHERE  VTA_DET.COD_GRUPO_CIA          = cCodGrupoCia_in
              AND     VTA_DET.COD_LOCAL              = cCodLocal_in
              AND     VTA_DET.NUM_PED_VTA            = pNroPedido
              AND    VTA_DET.SEC_COMP_PAGO_EMPRE    = PSecCompPago
              AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
              AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
              AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
              AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
              AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
              AND     PROD.COD_LAB             = LAB.COD_LAB
              AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
              AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
              ORDER BY VTA_DET.SEC_PED_VTA_DET;

              END IF;



          ELSE


          --SIN IGV

                           IF   pTipClienteConv = COMP_BENEF_CAMPO THEN

                    OPEN curCompDeta FOR
                    SELECT
                    DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)||'',
                               VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC))
                                                                           || 'Ã' ||
                     CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
                     THEN  SUBSTR(4,1,9) ||' '|| PROD.DESC_PROD
                     ELSE  PROD.DESC_PROD
                     END                                                   || 'Ã' ||
                     DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_UNID_PRESENT,
                                                                        VTA_DET.UNID_VTA                                      )
                     || 'Ã' ||
                     --LAB.NOM_LAB
                     ' '

                     || 'Ã' ||
                     CASE  WHEN vIndAfectoIGV='N'
                      THEN  TO_CHAR(round(VTA_DET.val_prec_vta,3),'999,990.00')
                     ELSE  TO_CHAR(round(VTA_DET.val_prec_vta*100/(VTA_DET.VAL_IGV+100),3),'999,990.00')
                     END
                     || 'Ã' ||
                      CASE  WHEN vIndAfectoIGV='N'
                       THEN TO_CHAR(round(VTA_DET.val_prec_total,3),'999,990.00')
                      ELSE TO_CHAR(round(VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100),3),'999,990.00')
                      END
                     || 'Ã' ||
                     VTA_DET.COD_PROD                                       || 'Ã' ||
                     NVL(VTA_DET.NUM_LOTE_PROD,' ')                         || 'Ã' ||
                     DECODE(NVL(VIR.COD_PROD,'N'),'N','N','S')              || 'Ã' ||
                     NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
                     NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
                     NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
                     NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ') || 'Ã' ||
                     DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_PROD || ' / ' || PROD.DESC_UNID_PRESENT,
                     PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA)  || 'Ã' ||
                     TO_CHAR(VTA_DET.AHORRO,'999,990.000')         || 'Ã' ||
                     TO_CHAR(round(VTA_DET.val_prec_vta*100/(VTA_DET.VAL_IGV+100),3)*VTA_DET.CANT_ATENDIDA,'999,990.00') || 'Ã' ||
                      CASE  WHEN vIndAfectoIGV='N'
                       THEN   TO_CHAR(round(0,3),'999,990.00')
                      ELSE   TO_CHAR(round(VTA_DET.val_prec_total - VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100),3),'999,990.00')
                      END
               FROM  VTA_PEDIDO_VTA_DET VTA_DET,
                     LGT_PROD_LOCAL PROD_LOCAL,
                     LGT_PROD PROD,
                     LGT_LAB LAB,
                     LGT_PROD_VIRTUAL VIR
              WHERE  VTA_DET.COD_GRUPO_CIA          = cCodGrupoCia_in
              AND     VTA_DET.COD_LOCAL              = cCodLocal_in
              AND     VTA_DET.NUM_PED_VTA            = pNroPedido
              AND    VTA_DET.SEC_COMP_PAGO_BENEF    = PSecCompPago
              AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
              AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
              AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
              AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
              AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
              AND     PROD.COD_LAB             = LAB.COD_LAB
              AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
              AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
              ORDER BY VTA_DET.SEC_PED_VTA_DET;

              END IF;
              IF   pTipClienteConv = COMP_EMPRE_CAMPO THEN

                        OPEN curCompDeta FOR
                        SELECT

                    DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)||'',
                               VTA_DET.CANT_ATENDIDA||DECODE(VTA_DET.VAL_FRAC,1,'','/'||VTA_DET.VAL_FRAC))
                                                                           || 'Ã' ||
                     CASE  WHEN VTA_DET.VAL_PREC_TOTAL=0
                     THEN  SUBSTR(4,1,9) ||' '|| PROD.DESC_PROD
                     ELSE  PROD.DESC_PROD
                     END                                                   || 'Ã' ||
                     DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_UNID_PRESENT,
                                                                        VTA_DET.UNID_VTA                                      )
                     || 'Ã' ||
                     --LAB.NOM_LAB
                     ' '
                     || 'Ã' ||
                      TO_CHAR( round(
                     CASE
                     WHEN MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC) = 0 THEN
                        (VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100))/(VTA_DET.CANT_ATENDIDA/VTA_DET.VAL_FRAC)
                     ELSE
                        VTA_DET.val_prec_vta*100/(VTA_DET.VAL_IGV+100)
                     END
                     ,3) ,'999,990.00')
                     || 'Ã' ||
                      CASE  WHEN vIndAfectoIGV='N'
                       THEN TO_CHAR(round(VTA_DET.val_prec_total,3),'999,990.00')
                      ELSE TO_CHAR(round(VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100),3),'999,990.00')
                      END
                     || 'Ã' ||
                     VTA_DET.COD_PROD                                       || 'Ã' ||
                     NVL(VTA_DET.NUM_LOTE_PROD,' ')                         || 'Ã' ||
                     DECODE(NVL(VIR.COD_PROD,'N'),'N','N','S')              || 'Ã' ||
                     NVL(VIR.TIP_PROD_VIRTUAL,' ')                          || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TEL_REC,' ')                      || 'Ã' ||
                     NVL(VTA_DET.DESC_NUM_TARJ_VIRTUAL,' ')                 || 'Ã' ||
                     NVL(VTA_DET.VAL_NUM_PIN,' ')                           || 'Ã' ||
                     NVL(VTA_DET.VAL_COD_APROBACION,' ')                    || 'Ã' ||
                     NVL(TO_CHAR(VTA_DET.FEC_VENCIMIENTO_LOTE,'dd/MM/yyyy'),' ') || 'Ã' ||
                     DECODE(MOD(VTA_DET.CANT_ATENDIDA,VTA_DET.VAL_FRAC),0,PROD.DESC_PROD || ' / ' || PROD.DESC_UNID_PRESENT,
                     PROD.DESC_PROD || ' / ' || VTA_DET.UNID_VTA)  || 'Ã' ||
                     TO_CHAR(VTA_DET.AHORRO,'999,990.000')         || 'Ã' ||
                     TO_CHAR(round(VTA_DET.val_prec_vta*100/(100 + FARMA_GRAL.F_GET_IGV_LOCAL),3)*VTA_DET.CANT_ATENDIDA,'999,990.00')|| 'Ã' ||    --ASOSA - 24/06/2015 - IGVAZNIA
                     CASE  WHEN vIndAfectoIGV='N'
                       THEN   TO_CHAR(round(0,3),'999,990.00')
                      ELSE    TO_CHAR(round(VTA_DET.val_prec_total - VTA_DET.val_prec_total*100/(VTA_DET.VAL_IGV+100),3),'999,990.00')
                      END
               FROM  VTA_PEDIDO_VTA_DET VTA_DET,
                     LGT_PROD_LOCAL PROD_LOCAL,
                     LGT_PROD PROD,
                     LGT_LAB LAB,
                     LGT_PROD_VIRTUAL VIR
              WHERE  VTA_DET.COD_GRUPO_CIA          = cCodGrupoCia_in
              AND     VTA_DET.COD_LOCAL              = cCodLocal_in
              AND     VTA_DET.NUM_PED_VTA            = pNroPedido
              AND    VTA_DET.SEC_COMP_PAGO_EMPRE    = PSecCompPago
              AND     VTA_DET.COD_GRUPO_CIA    = PROD_LOCAL.COD_GRUPO_CIA
              AND     VTA_DET.COD_LOCAL        = PROD_LOCAL.COD_LOCAL
              AND     VTA_DET.COD_PROD         = PROD_LOCAL.COD_PROD
              AND     PROD_LOCAL.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
              AND     PROD_LOCAL.COD_PROD      = PROD.COD_PROD
              AND     PROD.COD_LAB             = LAB.COD_LAB
              AND    PROD_LOCAL.COD_GRUPO_CIA = VIR.COD_GRUPO_CIA(+)
              AND    PROD_LOCAL.COD_PROD      = VIR.COD_PROD(+)
              ORDER BY VTA_DET.SEC_PED_VTA_DET;

              END IF;


          END IF;


END IF; --RHERRERA : FIN DE LA CONDICIÓN
    RETURN curCompDeta;

    END;



  FUNCTION CAJ_OBTIENE_RUTA_IMPRESORA(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cTipCompPago_in  IN CHAR)
  RETURN CHAR
  IS

    v_cRutaImpr VTA_IMPR_LOCAL.RUTA_IMPR%TYPE;
    cIP VARCHAR2(30);
    vSEC_IMPR_LOCAL VTA_IMPR_IP.SEC_IMPR_LOCAL%TYPE;

  BEGIN
    -- KMONCADA 26.05.2016 NUEVO METODO PARA DETERMINAR SECUENCIAL DE IMPRESORA
    SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') 
    INTO cIP 
    FROM DUAL;
    vSEC_IMPR_LOCAL := PTOVENTA_CAJ.F_GET_SECUENCIAL_IMPRESORA(cCodGrupoCia_in => cCodGrupoCia_in,
                                                               cCodLocal_in => cCodLocal_in,
                                                               cTipoCP_in => cTipCompPago_in,
                                                               cIpPC_in => cIP);
    
    
    IF vSEC_IMPR_LOCAL IS NULL THEN 
      RAISE_APPLICATION_ERROR(-20000, 'No encontro a la Ip para el comprobante '||
                                      FARMA_UTILITY.GET_DESC_COMPROBANTE(cCodGrupoCia_in, cTipCompPago_in)||'.');
    END IF;
    
    SELECT NVL(IMPR_LOCAL.RUTA_IMPR,' ')
    INTO   v_cRutaImpr
    FROM   VTA_IMPR_LOCAL IMPR_LOCAL
    WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND    IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
    AND    IMPR_LOCAL.SEC_IMPR_LOCAL = vSEC_IMPR_LOCAL;
      
    /*IF cTipCompPago_in = '05' THEN

     BEGIN

    --ERIOS 2.4.5 Cambios proyecto Conveniencia
        SELECT I.SEC_IMPR_LOCAL
          INTO vSEC_IMPR_LOCAL
          FROM VTA_IMPR_IP I
         WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
            AND I.COD_LOCAL = cCodLocal_in
            AND I.IP = cIP
           AND I.TIP_COMP = cTipCompPago_in;
      EXCEPTION
          WHEN NO_DATA_FOUND  THEN
          RAISE_APPLICATION_ERROR(-20000, 'No encontro el Ip para el comprobante Ticket. ');
      END;

       SELECT NVL(IMPR_LOCAL.RUTA_IMPR,' ')
          INTO v_cRutaImpr
         FROM VTA_IMPR_LOCAL IMPR_LOCAL
        WHERE IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
          AND IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
          AND IMPR_LOCAL.TIP_COMP = cTipCompPago_in
          AND IMPR_LOCAL.SEC_IMPR_LOCAL = vSEC_IMPR_LOCAL;


    ELSE
      SELECT NVL(IMPR_LOCAL.RUTA_IMPR,' ')
      INTO   v_cRutaImpr
      FROM   VTA_IMPR_LOCAL IMPR_LOCAL
      WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
      AND    IMPR_LOCAL.SEC_IMPR_LOCAL = vSEC_IMPR_LOCAL;
      --AND     IMPR_LOCAL.TIP_COMP = cTipCompPago_in;

    END IF;*/

    RETURN v_cRutaImpr;
  END;




  FUNCTION BTLMF_F_CHAR_OBT_DNI_USUARIO( cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cSecUsuLocal_in IN CHAR
                                   )
  RETURN CHAR
  IS
  vDni PBL_USU_LOCAL.Dni_Usu%TYPE;



  BEGIN
      SELECT
             U.DNI_USU
        INTO vDni
        FROM PBL_USU_LOCAL U
       WHERE U.COD_GRUPO_CIA = cCodGrupoCia_in
         AND U.COD_LOCAL     = cCodLocal_in
         AND U.SEC_USU_LOCAL = cSecUsuLocal_in;

    RETURN vDni;
  END;



  PROCEDURE BTLMF_P_ACT_FECHA_PROC_RAC(CodGrupoCia_in IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNroPedido     IN CHAR
                                      )

  IS
  BEGIN

     UPDATE VTA_PEDIDO_VTA_CAB VTA
        SET VTA.FEC_PROCESO_RAC = SYSDATE
      WHERE VTA.COD_GRUPO_CIA = CodGrupoCia_in
        AND VTA.COD_LOCAL     = cCodLocal_in
        AND VTA.NUM_PED_VTA   = cNroPedido;

  END;

  PROCEDURE BTLMF_P_AC_FEC_PROC_ANU_NC_RAC(CodGrupoCia_in IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNroPedido     IN CHAR
                                      )

  IS
  BEGIN

     UPDATE VTA_PEDIDO_VTA_CAB VTA
        SET VTA.Fecha_Proceso_NC_RAC = SYSDATE
      WHERE VTA.COD_GRUPO_CIA = CodGrupoCia_in
        AND VTA.COD_LOCAL     = cCodLocal_in
        AND VTA.NUM_PED_VTA   = cNroPedido;

  END;

  PROCEDURE BTLMF_P_AC_FEC_PROC_ANU_RAC(CodGrupoCia_in IN CHAR,
                                       cCodLocal_in   IN CHAR,
                                       cNroPedido     IN CHAR
                                      )

  IS
  BEGIN

     UPDATE VTA_PEDIDO_VTA_CAB VTA
        SET VTA.FECHA_PROCESO_ANULA_RAC = SYSDATE
      WHERE VTA.COD_GRUPO_CIA = CodGrupoCia_in
        AND VTA.COD_LOCAL     = cCodLocal_in
        AND VTA.NUM_PED_VTA   = cNroPedido;

  END;


FUNCTION BTLMF_F_CHAR_ES_DIA_VIG_RECTA(fechaReceta_in IN varchar2)
    RETURN CHAR IS

    vResp char(1) := 'N';
    vLimiteDiaVigencia INTEGER;
    vDiaVigencia INTEGER;

    BEGIN

      SELECT CAST(t.llave_tab_gral AS INTEGER)
        INTO vLimiteDiaVigencia
        FROM pbl_tab_gral t
       WHERE t.id_tab_gral = '393';

      SELECT TO_DATE(TO_CHAR(SYSDATE,'dd/MM/yyyy'),'dd/MM/yyyy') - TO_DATE(fechaReceta_in,'dd/MM/yyyy')
        INTO vDiaVigencia
        FROM DUAL;

  --ERIOS 2.4.3 Valida que la fecha de receta no sea posterior
  IF   vDiaVigencia < 0 THEN
      vResp := 'P' ;
    ELSIF  vDiaVigencia < vLimiteDiaVigencia or vDiaVigencia = vLimiteDiaVigencia THEN
        vResp := 'S' ;
    END IF;

    RETURN vResp;
END;




FUNCTION BTLMF_F_EXISTE_PROD_CONV(cCodLocal_in IN CHAR,vCodConvenio CHAR)
    RETURN CHAR IS

    vResp char(1) ;

    BEGIN

      SELECT decode(COUNT(*),'0','N','S')
        INTO vResp
        FROM lgt_prod_local l
       WHERE l.cod_grupo_cia = '001'
         and l.cod_local = cCodLocal_in
         and pkg_producto.FN_DEV_APTO_CONV(l.cod_prod, vCodConvenio) = 'I';

    RETURN vResp;
END;



  FUNCTION BTLMF_F_CHAR_OBT_TIP_CONVENIO(cCodConvenio IN CHAR)
  RETURN CHAR
  IS
  vPCT_BENEFICIARIO  MAE_CONVENIO.PCT_BENEFICIARIO%TYPE;
  vPCT_EMPRESA       MAE_CONVENIO.PCT_EMPRESA%TYPE;
  vFLG_TIPO_CONVENIO MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE;

  respTipoConvenio CHAR (1);




  BEGIN

      SELECT C.PCT_BENEFICIARIO,
             C.PCT_EMPRESA,
             C.COD_TIPO_CONVENIO
        INTO vPCT_BENEFICIARIO,
             vPCT_EMPRESA,
             vFLG_TIPO_CONVENIO
        FROM MAE_CONVENIO C
       WHERE C.COD_CONVENIO = cCodConvenio;
      respTipoConvenio :=  vFLG_TIPO_CONVENIO;



    RETURN respTipoConvenio;
  END;


function split(input_list varchar2, ret_this_one number, delimiter varchar2) return varchar2 is
  v_list varchar2(32767) := delimiter || input_list; start_position number; end_position number;
  begin
  start_position := instr(v_list, delimiter, 1, ret_this_one); if start_position > 0 then end_position := instr(v_list, delimiter, 1, ret_this_one + 1); if end_position = 0 then end_position := length(v_list) + 1;
  end if; return(substr(v_list, start_position + 1, end_position - start_position - 1)); else return NULL;
  end if;
end split;

FUNCTION BTLMF_F_VARCHAR_MSG_COMP(cCodGrupoCia_in varchar2,cCodConvenio varchar2,  montoTotalPedido varchar2 ,nValorSelCopago_in number default -1)
    RETURN varchar2 IS
    vResp varchar2(3000);
    vPct_beneficiario mae_convenio.pct_beneficiario%TYPE;
    vPct_empresa      mae_convenio.pct_empresa%TYPE;

   curEscala FarmaCursor;
   vMontoNeto           NUMBER(9,2);
   vMontoCreditoEmpre   VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
   v_Flg_Tipo_Convenio  MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE;

    v_nCopago FLOAT;
    v_nPctBenef mae_convenio.pct_beneficiario%TYPE;
    v_nPctEmp      mae_convenio.pct_empresa%TYPE;

    BEGIN


         select
                conv.pct_beneficiario,
                conv.pct_empresa,
                conv.cod_tipo_convenio
           into vPct_beneficiario,
                vPct_empresa,
                v_Flg_Tipo_Convenio
           from mae_convenio conv
          where conv.cod_convenio = cCodConvenio;

      --ERIOS 2.2.8 Calculo de copago variable
      --ERIOS 2.4.6 Calculo por escala
      IF nValorSelCopago_in != -1 THEN
        vPct_beneficiario := nValorSelCopago_in;
        vPct_empresa := 100-nValorSelCopago_in;
      ELSE
          vMontoNeto := TO_NUMBER(montoTotalPedido,'999999.99');
        v_nCopago := FN_CALCULA_COPAGO(cCodConvenio,vMontoNeto,v_nPctBenef,v_nPctEmp);
        dbms_output.put_line('v_nCopago:'||v_nCopago);
        IF v_nCopago > -1 THEN
          vPct_beneficiario := v_nPctBenef;
          vPct_empresa := v_nPctEmp;
        END IF;
      END IF;

          select ''||
                 case
                 when res.comp_benef = 'X' then ' '
                 else '(%'||vPct_beneficiario||')Benef: '|| res.comp_benef
                 end||
                 case
                 when res.comp_empresa = 'X' then ' '
                 else '  /  (%'||vPct_empresa||')Empre: '|| res.comp_empresa
                 end textoCabeceraComprobante
          into   vResp
          from   (
                  select nvl((select e.desc_comp
                               from vta_tip_comp e
                              where e.tip_comp = v.tip_comp_benef
                                and e.cod_grupo_cia = cCodGrupoCia_in),
                             'X') comp_benef,
                         nvl((select e.desc_comp
                               from vta_tip_comp e
                              where e.tip_comp = v.tip_comp_empresa
                                and e.cod_grupo_cia = cCodGrupoCia_in),
                             'X') comp_empresa
                    from (select m.cod_tipdoc_cliente,
                                 (select a.tip_comp_pago
                                    from mae_tipo_comp_pago_btlmf a
                                   where m.cod_tipdoc_cliente = a.cod_tipodoc) tip_comp_empresa,
                                 m.cod_tipdoc_beneficiario,
                                 (select a.tip_comp_pago
                                    from mae_tipo_comp_pago_btlmf a
                                   where m.cod_tipdoc_beneficiario = a.cod_tipodoc) tip_comp_benef
                            from mae_convenio m
                           where m.cod_convenio = cCodConvenio) v
                ) res;

    RETURN vResp;
END;

  FUNCTION getDetallePedido(cCodGrupoCia_in in varchar2,
                            cCodLocal_in    in varchar2,
                            cNumPedVta_in   in varchar2,
                            cIndConIGV_in   in varchar2,
                            -- KMONCADA 22.04.2016 PERCEPCION
                            cAplicaPercepcion_in        IN CHAR DEFAULT 'N',
                            vContieneProdPercep_in      IN CHAR DEFAULT 'N')
    RETURN FarmaCursor IS
    curDetalle FarmaCursor;
  BEGIN

    IF cIndConIGV_in = 'S' AND cAplicaPercepcion_in = 'N' THEN
      OPEN curDetalle FOR
        SELECT VTA_DET.SEC_PED_VTA_DET,
               VTA_DET.COD_PROD,
               VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA "VALOR_BRUTO",
               VTA_DET.VAL_PREC_TOTAL "VALOR_NETO",
               (VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA) -
               (VTA_DET.VAL_PREC_TOTAL) "VALOR_DESCUENTO",
               (VTA_DET.VAL_PREC_TOTAL / (1 + (VTA_DET.VAL_IGV / 100))) "VALOR_AFECTO",
                VTA_DET.VAL_IGV "VALOR_IGV",
               I.PORC_IGV/*VTA_DET.VAL_IGV*/ "PORC_IGV",
               NVL(VTA_DET.VAL_PCT_PERCEPCION,0) "PCT_PERCEPCION",
               NVL(VTA_DET.VAL_MONTO_PERCEPCION,0) "VALOR_PERCEPCION"
          FROM VTA_PEDIDO_VTA_DET VTA_DET, LGT_PROD P, PBL_IGV I
         WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VTA_DET.COD_LOCAL = cCodLocal_in
           AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
           AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
           AND VTA_DET.COD_PROD = P.COD_PROD
           AND P.COD_IGV = I.COD_IGV
           AND I.PORC_IGV > 0
         ORDER BY VTA_DET.SEC_PED_VTA_DET;
    ELSIF cIndConIGV_in = 'S' AND cAplicaPercepcion_in = 'S' AND vContieneProdPercep_in = 'N' THEN
      OPEN curDetalle FOR
        SELECT VTA_DET.SEC_PED_VTA_DET,
               VTA_DET.COD_PROD,
               VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA "VALOR_BRUTO",
               VTA_DET.VAL_PREC_TOTAL "VALOR_NETO",
               (VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA) -
               (VTA_DET.VAL_PREC_TOTAL) "VALOR_DESCUENTO",
               (VTA_DET.VAL_PREC_TOTAL / (1 + (VTA_DET.VAL_IGV / 100))) "VALOR_AFECTO",
                VTA_DET.VAL_IGV "VALOR_IGV",
               I.PORC_IGV/*VTA_DET.VAL_IGV*/ "PORC_IGV",
               NVL(VTA_DET.VAL_PCT_PERCEPCION,0) "PCT_PERCEPCION",
               NVL(VTA_DET.VAL_MONTO_PERCEPCION,0) "VALOR_PERCEPCION"
          FROM VTA_PEDIDO_VTA_DET VTA_DET, LGT_PROD P, PBL_IGV I
         WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VTA_DET.COD_LOCAL = cCodLocal_in
           AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
           AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
           AND VTA_DET.COD_PROD = P.COD_PROD
           AND P.COD_IGV = I.COD_IGV
           AND I.PORC_IGV > 0
           AND NVL(VTA_DET.VAL_MONTO_PERCEPCION,0)=0
         ORDER BY VTA_DET.SEC_PED_VTA_DET;
    ELSIF cIndConIGV_in = 'S' AND cAplicaPercepcion_in = 'S' AND vContieneProdPercep_in = 'S' THEN
      OPEN curDetalle FOR
        SELECT VTA_DET.SEC_PED_VTA_DET,
               VTA_DET.COD_PROD,
               VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA "VALOR_BRUTO",
               VTA_DET.VAL_PREC_TOTAL "VALOR_NETO",
               (VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA) -
               (VTA_DET.VAL_PREC_TOTAL) "VALOR_DESCUENTO",
               (VTA_DET.VAL_PREC_TOTAL / (1 + (VTA_DET.VAL_IGV / 100))) "VALOR_AFECTO",
                VTA_DET.VAL_IGV "VALOR_IGV",
               I.PORC_IGV/*VTA_DET.VAL_IGV*/ "PORC_IGV",
               NVL(VTA_DET.VAL_PCT_PERCEPCION,0) "PCT_PERCEPCION",
               NVL(VTA_DET.VAL_MONTO_PERCEPCION,0) "VALOR_PERCEPCION"
          FROM VTA_PEDIDO_VTA_DET VTA_DET, LGT_PROD P, PBL_IGV I
         WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VTA_DET.COD_LOCAL = cCodLocal_in
           AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
           AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
           AND VTA_DET.COD_PROD = P.COD_PROD
           AND P.COD_IGV = I.COD_IGV
           AND I.PORC_IGV > 0
           AND NVL(VTA_DET.VAL_MONTO_PERCEPCION,0) != 0
         ORDER BY VTA_DET.SEC_PED_VTA_DET;
    ELSE
      open curDetalle for
        SELECT VTA_DET.SEC_PED_VTA_DET,
               VTA_DET.COD_PROD,
               VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA "VALOR_BRUTO",
               VTA_DET.VAL_PREC_TOTAL "VALOR_NETO",
               (VTA_DET.VAL_PREC_LISTA * VTA_DET.CANT_ATENDIDA) -
               (VTA_DET.VAL_PREC_TOTAL) "VALOR_DESCUENTO",
               (VTA_DET.VAL_PREC_TOTAL / (1 + (VTA_DET.VAL_IGV / 100))) "VALOR_AFECTO",
                VTA_DET.VAL_IGV "VALOR_IGV",
               I.PORC_IGV/*VTA_DET.VAL_IGV*/ "PORC_IGV",
               NVL(VTA_DET.VAL_PCT_PERCEPCION,0) "PCT_PERCEPCION",
               NVL(VTA_DET.VAL_MONTO_PERCEPCION,0) "VALOR_PERCEPCION"
          FROM VTA_PEDIDO_VTA_DET VTA_DET, LGT_PROD P, PBL_IGV I
         WHERE VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
           AND VTA_DET.COD_LOCAL = cCodLocal_in
           AND VTA_DET.NUM_PED_VTA = cNumPedVta_in
           AND VTA_DET.COD_GRUPO_CIA = P.COD_GRUPO_CIA
           AND VTA_DET.COD_PROD = P.COD_PROD
           AND P.COD_IGV = I.COD_IGV
           AND I.PORC_IGV = 0
         ORDER BY VTA_DET.SEC_PED_VTA_DET;
    end if;
    RETURN curDetalle;
end;


  FUNCTION BTLMF_F_CHAR_OBT_MSJ_COMP_IMPR(cCodGrupoCia_in in CHAR,
                                     cCodLocal_in    in CHAR,
                                     cTipoComp IN CHAR,
                                     cNroPedido IN CHAR
                                     )
  RETURN FarmaCursor IS
   curComp FarmaCursor;
    vMsjeImpresionUno       VARCHAR2(4000);
    vMsjeImpresionDos       VARCHAR2(4000);
    vMsjeImpresionTres      VARCHAR2(4000);
    vMsjeImpresionCuaro     VARCHAR2(4000);



    desComprobantePago  VARCHAR2(400):= ' ';
    vNroComp    INTEGER := 1;
  BEGIN




           IF   cTipoComp  = COD_TIP_COMP_BOLETA THEN
                 desComprobantePago := 'BOLETA';
                 SELECT COUNT(*)
                   INTO vNroComp
                   FROM VTA_COMP_PAGO COMP
                  WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND COMP.COD_LOCAL     = cCodLocal_in
                    AND COMP.TIP_COMP_PAGO = cTipoComp
                    AND COMP.NUM_PED_VTA   = cNroPedido;
           ELSIF cTipoComp = COD_TIP_COMP_FACTURA THEN
                 desComprobantePago := 'FACTURA';
                 SELECT COUNT(*)
                   INTO vNroComp
                   FROM VTA_COMP_PAGO COMP
                  WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND COMP.COD_LOCAL     = cCodLocal_in
                    AND COMP.TIP_COMP_PAGO = cTipoComp
                    AND COMP.NUM_PED_VTA   = cNroPedido;

           ELSIF cTipoComp = COD_TIP_COMP_GUIA THEN
                 desComprobantePago := 'GUIA';
                 SELECT COUNT(*)
                   INTO vNroComp
                   FROM VTA_COMP_PAGO COMP
                  WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND COMP.COD_LOCAL     = cCodLocal_in
                    AND COMP.TIP_COMP_PAGO = cTipoComp
                    AND COMP.NUM_PED_VTA   = cNroPedido;

           ELSIF cTipoComp = COD_TIP_COMP_TICKET THEN
                 desComprobantePago := 'TICKET';
                 SELECT COUNT(*)
                   INTO vNroComp
                   FROM VTA_COMP_PAGO COMP
                  WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND COMP.COD_LOCAL     = cCodLocal_in
                    AND COMP.TIP_COMP_PAGO = cTipoComp
                    AND COMP.NUM_PED_VTA   = cNroPedido;

           END IF;


           vMsjeImpresionUno := '  <table cellpadding=0 cellspacing=0 align=left> '||
                                '   <tr> '||
                                '    <td></td> '||
                                '    <td width=638 height=383 bgcolor=white style="border:.75pt solid black; '||
                                '    vertical-align:top;background:white"><span '||
                                '    style="position:absolute;mso-ignore:vglayout;z-index:1"> '||
                                '    <table cellpadding=0 cellspacing=0 width=100%> '||
                                '     <tr> '||
                                '     <td> '||
                                '      <div v:shape=_x0000_s1026 style="padding:4.35pt 7.95pt 4.35pt 7.95pt" '||
                                '      class=shape> '||
                                '      <p class=MsoNormal align=center style="text-align:center"><b><span lang=ES '||
                                '      style="font-family:Arial,sans-serif">*****************************************************************************************<o:p></o:p></span></b></p>'||
                                '      <p class=MsoNormal align=center style="text-align:center"><span lang=ES '||
                                '      style="font-size:48.0pt;font-family:Arial,sans-serif;color:#C00000">' ||
                                '      <font color=#C00000 size =8 face=Arial>¡¡ ';
           vMsjeImpresionDos := '      ALERTA !!</font><o:p></o:p></span></p> '||
                                '      <p class=MsoNormal align=center style="text-align:center"><b><span lang=ES '||
                                '      style="font-size:18;font-family:Arial,sans-serif;color:#C00000">' ||
                                '      <font color=#C00000 size =6 face=Arial>LEER '||
                               '      LAS INDICACIONES</font><o:p></o:p></span></b></p>'||
                              '      <p class=MsoNormal align=left style="text-align:center"><b><span lang=ES '||
                              '      style="font-size:26.0pt;font-family:Arial,sans-serif"><o:p><font color=#139128 size =8 face=Arial> '||desComprobantePago||'- '||vNroComp||' Imprimir.</font></o:p></span></b></p> '||
                              '      <p class=MsoNormal><b><span lang=ES style="font-family:Arial,sans-serif; '||
                              '      color:#139128"> </span></b><b><span lang=ES style="font-size:18.0pt; '||
                              '      font-family:Arial,sans-serif;color:#139128">' ||
                              '     <font color=#139128 size =5 face=Arial>Revisar:</font>' ||
                              '      <o:p></o:p></span></b></p> '||
                              '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                              '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                              '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                              '      style="mso-list:Ignore">·<span style="font:7.0pt Times New Roman">;;;;;; '||
                              '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                              '      font-family:Arial,sans-serif;color:#139128">'||
                              '     <font color=#139128 size =5 face=Arial>Si esta prendida la ';
           vMsjeImpresionTres := '      impresora de '||desComprobantePago||'.</font>' ||
                                '  <o:p></o:p></span></b></p> '||
                              '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                              '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                              '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                              '      style="mso-list:Ignore">·<span style="font:7.0pt Times New Roman">;;;;;; '||
                              '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                              '      font-family:Arial,sans-serif;color:#139128"><font color=#139128 size =5 face=Arial>Si cuenta con papel para '||
                              '      imprimir '||desComprobantePago||'.</font><o:p></o:p></span></b></p> '||
                              '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                              '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                              '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                              '      style="mso-list:Ignore">·<span style="font:7.0pt Times New Roman">;;;;;; ';
          vMsjeImpresionCuaro :=   '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                              '      font-family:Arial,sans-serif;color:#139128">' ||
                              '  <font color=#139128 size =5 face=Arial>Si el Correlativo del '||
                              '      comprobante sea el mismo con el sistema.</font><o:p></o:p></span></b></p> '||
                              '      <p class=MsoNormal><span lang=ES style="font-family:Arial,sans-serif">*****************************************************************************************<o:p></o:p></span>' ||
                              '      <H3>'||
                              '          ' ||
                              '          ' ||
                              '          ; Presione [Enter] para continuar...</H3></o:p></p> '||
                              '      </div> '||
                              '      <![if !mso]></td> '||
                              '     </tr> '||
                              '    </table> '||
                              '    </span></td> '||
                              '   </tr> '||
                              '  </table> ';


      open curComp for
      SELECT
             vMsjeImpresionUno  AS UNO ,
             vMsjeImpresionDos  AS DOS,
             vMsjeImpresionTres AS TRES  ,
             vMsjeImpresionCuaro AS CUATRO
        FROM DUAL;


       RETURN curComp;
   END;


FUNCTION BTLMF_F_GRABA_DATOS_TMP( cCodGrupoCia_in CHAR,
                                  cCodLocal_in    CHAR,
                                  cNumPedVta_in   CHAR,
                                  curComPagoTemp FarmaCursor,
                                  curFormPagoTemp FarmaCursor,
                                  curPedidoDetTemp FarmaCursor
                                 ) RETURN CHAR
IS
PRAGMA AUTONOMOUS_TRANSACTION;

 pCOD_GRUPO_CIA VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE;                    pCOD_LOCAL  VTA_COMP_PAGO.COD_LOCAL%TYPE;
 pNUM_PED_VTA   VTA_COMP_PAGO.NUM_PED_VTA%TYPE;                      pSEC_COMP_PAGO  VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
 pTIP_COMP_PAGO  VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE;                   pNUM_COMP_PAGO  VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
 pSEC_MOV_CAJA   VTA_COMP_PAGO.SEC_MOV_CAJA%TYPE;                    pSEC_MOV_CAJA_ANUL  VTA_COMP_PAGO.SEC_MOV_CAJA_ANUL%TYPE;
 pCANT_ITEM      VTA_COMP_PAGO.CANT_ITEM%TYPE;                       pCOD_CLI_LOCAL  VTA_COMP_PAGO.COD_CLI_LOCAL%TYPE;
 pNOM_IMPR_COMP  VTA_COMP_PAGO.NOM_IMPR_COMP%TYPE;                   pDIREC_IMPR_COMP  VTA_COMP_PAGO.DIREC_IMPR_COMP%TYPE;
 pNUM_DOC_IMPR  VTA_COMP_PAGO.NUM_DOC_IMPR%TYPE;                     pVAL_BRUTO_COMP_PAGO VTA_COMP_PAGO.VAL_BRUTO_COMP_PAGO%TYPE;
 pVAL_NETO_COMP_PAGO  VTA_COMP_PAGO.VAL_NETO_COMP_PAGO%TYPE;         pVAL_DCTO_COMP_PAGO VTA_COMP_PAGO.VAL_DCTO_COMP_PAGO%TYPE;
 pVAL_AFECTO_COMP_PAGO  VTA_COMP_PAGO.VAL_AFECTO_COMP_PAGO%TYPE;     pVAL_IGV_COMP_PAGO  VTA_COMP_PAGO.VAL_IGV_COMP_PAGO%TYPE;
 pVAL_REDONDEO_COMP_PAGO  VTA_COMP_PAGO.VAL_REDONDEO_COMP_PAGO%TYPE; pPORC_IGV_COMP_PAGO VTA_COMP_PAGO.PORC_IGV_COMP_PAGO%TYPE;
 pUSU_CREA_COMP_PAGO  VTA_COMP_PAGO.USU_CREA_COMP_PAGO%TYPE;         pFEC_CREA_COMP_PAGO VTA_COMP_PAGO.FEC_CREA_COMP_PAGO%TYPE;
 pUSU_MOD_COMP_PAGO  VTA_COMP_PAGO.USU_MOD_COMP_PAGO%TYPE;           pFEC_MOD_COMP_PAGO VTA_COMP_PAGO.FEC_MOD_COMP_PAGO%TYPE;
 pFEC_ANUL_COMP_PAGO  VTA_COMP_PAGO.FEC_ANUL_COMP_PAGO%TYPE;         pIND_COMP_ANUL VTA_COMP_PAGO.IND_COMP_ANUL%TYPE; pNUM_PEDIDO_ANUL VTA_COMP_PAGO.NUM_PEDIDO_ANUL%TYPE;
 pNUM_SEC_DOC_SAP  VTA_COMP_PAGO.NUM_SEC_DOC_SAP%TYPE;               pFEC_PROCESO_SAP VTA_COMP_PAGO.FEC_PROCESO_SAP%TYPE;
 pNUM_SEC_DOC_SAP_ANUL  VTA_COMP_PAGO.NUM_SEC_DOC_SAP_ANUL%TYPE;     pFEC_PROCESO_SAP_ANUL VTA_COMP_PAGO.FEC_PROCESO_SAP_ANUL%TYPE;
 pIND_RECLAMO_NAVSAT  VTA_COMP_PAGO.IND_RECLAMO_NAVSAT%TYPE;         pVAL_DCTO_COMP VTA_COMP_PAGO.VAL_DCTO_COMP%TYPE;
 pMOTIVO_ANULACION  VTA_COMP_PAGO.MOTIVO_ANULACION%TYPE;             pFECHA_COBRO VTA_COMP_PAGO.FECHA_COBRO%TYPE;     pFECHA_ANULACION VTA_COMP_PAGO.FECHA_ANULACION%TYPE;
 pFECH_IMP_COBRO  VTA_COMP_PAGO.FECH_IMP_COBRO%TYPE;                 pFECH_IMP_ANUL VTA_COMP_PAGO.FECH_IMP_ANUL%TYPE;
 pTIP_CLIEN_CONVENIO  VTA_COMP_PAGO.TIP_CLIEN_CONVENIO%TYPE;         pVAL_COPAGO_COMP_PAGO VTA_COMP_PAGO.VAL_COPAGO_COMP_PAGO%TYPE;
 pVAL_IGV_COMP_COPAGO  VTA_COMP_PAGO.VAL_IGV_COMP_COPAGO%TYPE;       pNUM_COMP_COPAGO_REF VTA_COMP_PAGO.NUM_COMP_COPAGO_REF%TYPE;
 pIND_AFECTA_KARDEX  VTA_COMP_PAGO.IND_AFECTA_KARDEX%TYPE;           pPCT_BENEFICIARIO  VTA_COMP_PAGO.PCT_BENEFICIARIO%TYPE;
 pPCT_EMPRESA  VTA_COMP_PAGO.PCT_EMPRESA%TYPE;                       pIND_COMP_CREDITO   VTA_COMP_PAGO.IND_COMP_CREDITO%TYPE;
 pTIP_COMP_PAGO_REF  VTA_COMP_PAGO.TIP_COMP_PAGO_REF%TYPE;
-- KMONCADA 02.10.2014 SE AGREGA COMPOS PARA COMP.ELECTRONICOS
 pNum_Comp_Pago_E VTA_COMP_PAGO.NUM_COMP_PAGO_E%TYPE;
 pCod_Tipo_Proc_Pago VTA_COMP_PAGO.COD_TIP_PROC_PAGO%TYPE;


 ppCOD_GRUPO_CIA VTA_FORMA_PAGO_PEDIDO_TEMP.COD_GRUPO_CIA%TYPE;
 ppCOD_LOCAL VTA_FORMA_PAGO_PEDIDO_TEMP.COD_LOCAL%TYPE;
 ppCOD_FORMA_PAGO VTA_FORMA_PAGO_PEDIDO_TEMP.COD_FORMA_PAGO%TYPE;
 ppNUM_PED_VTA VTA_FORMA_PAGO_PEDIDO_TEMP.NUM_PED_VTA%TYPE;
 ppIM_PAGO VTA_FORMA_PAGO_PEDIDO_TEMP.IM_PAGO%TYPE;
 ppTIP_MONEDA VTA_FORMA_PAGO_PEDIDO_TEMP.TIP_MONEDA%TYPE;
 ppVAL_TIP_CAMBIO VTA_FORMA_PAGO_PEDIDO_TEMP.VAL_TIP_CAMBIO%TYPE;
 ppVAL_VUELTO VTA_FORMA_PAGO_PEDIDO_TEMP.VAL_VUELTO%TYPE;
 ppIM_TOTAL_PAGO VTA_FORMA_PAGO_PEDIDO_TEMP.IM_TOTAL_PAGO%TYPE;
 ppNUM_TARJ VTA_FORMA_PAGO_PEDIDO_TEMP.NUM_TARJ%TYPE;
 ppFEC_VENC_TARJ VTA_FORMA_PAGO_PEDIDO_TEMP.FEC_VENC_TARJ%TYPE;
 ppNOM_TARJ VTA_FORMA_PAGO_PEDIDO_TEMP.NOM_TARJ%TYPE;
 ppFEC_CREA_FORMA_PAGO_PED VTA_FORMA_PAGO_PEDIDO_TEMP.FEC_CREA_FORMA_PAGO_PED%TYPE;
 ppUSU_CREA_FORMA_PAGO_PED VTA_FORMA_PAGO_PEDIDO_TEMP.USU_CREA_FORMA_PAGO_PED%TYPE;
 ppFEC_MOD_FORMA_PAGO_PED VTA_FORMA_PAGO_PEDIDO_TEMP.FEC_MOD_FORMA_PAGO_PED%TYPE;
 ppUSU_MOD_FORMA_PAGO_PED VTA_FORMA_PAGO_PEDIDO_TEMP.USU_MOD_FORMA_PAGO_PED%TYPE;
 ppCANT_CUPON VTA_FORMA_PAGO_PEDIDO_TEMP.CANT_CUPON%TYPE;
 ppTIPO_AUTORIZACION VTA_FORMA_PAGO_PEDIDO_TEMP.TIPO_AUTORIZACION%TYPE;
 ppCOD_LOTE VTA_FORMA_PAGO_PEDIDO_TEMP.COD_LOTE%TYPE;
 ppCOD_AUTORIZACION VTA_FORMA_PAGO_PEDIDO_TEMP.COD_AUTORIZACION%TYPE;
 ppDNI_CLI_TARJ VTA_FORMA_PAGO_PEDIDO_TEMP.DNI_CLI_TARJ%TYPE;


  --pCOD_GRUPO_CIA        VTA_PEDIDO_VTA_DET.cod_grupo_cia%TYPE;
  --pCOD_LOCAL        VTA_PEDIDO_VTA_DET.cod_local%TYPE;
  --pNUM_PED_VTA        VTA_PEDIDO_VTA_DET.num_ped_vta%TYPE;
  pSEC_PED_VTA_DET        VTA_PEDIDO_VTA_DET.sec_ped_vta_det%TYPE;
  pCOD_PROD        VTA_PEDIDO_VTA_DET.cod_prod%TYPE;
  pCANT_ATENDIDA        VTA_PEDIDO_VTA_DET.cant_atendida%TYPE;
  pVAL_PREC_VTA        VTA_PEDIDO_VTA_DET.val_prec_vta%TYPE;
  pVAL_PREC_TOTAL        VTA_PEDIDO_VTA_DET.val_prec_total%TYPE;
  pPORC_DCTO_1        VTA_PEDIDO_VTA_DET.porc_dcto_1%TYPE;
  pPORC_DCTO_2        VTA_PEDIDO_VTA_DET.porc_dcto_2%TYPE;
  pPORC_DCTO_3        VTA_PEDIDO_VTA_DET.porc_dcto_3%TYPE;
  pPORC_DCTO_TOTAL        VTA_PEDIDO_VTA_DET.porc_dcto_total%TYPE;
  pEST_PED_VTA_DET        VTA_PEDIDO_VTA_DET.est_ped_vta_det%TYPE;
  pVAL_TOTAL_BONO        VTA_PEDIDO_VTA_DET.val_total_bono%TYPE;
  pVAL_FRAC        VTA_PEDIDO_VTA_DET.val_frac%TYPE;
  --pSEC_COMP_PAGO        VTA_PEDIDO_VTA_DET.sec_comp_pago%TYPE;
  pSEC_USU_LOCAL        VTA_PEDIDO_VTA_DET.sec_usu_local%TYPE;
  pUSU_CREA_PED_VTA_DET        VTA_PEDIDO_VTA_DET.usu_crea_ped_vta_det%TYPE;
  pFEC_CREA_PED_VTA_DET        VTA_PEDIDO_VTA_DET.fec_crea_ped_vta_det%TYPE;
  pUSU_MOD_PED_VTA_DET        VTA_PEDIDO_VTA_DET.usu_mod_ped_vta_det%TYPE;
  pFEC_MOD_PED_VTA_DET        VTA_PEDIDO_VTA_DET.fec_mod_ped_vta_det%TYPE;
  pVAL_PREC_LISTAVAL_IGV        VTA_PEDIDO_VTA_DET.val_prec_lista%TYPE;
  Pval_igv                            VTA_PEDIDO_VTA_DET.val_igv%TYPE;
  pUNID_VTA        VTA_PEDIDO_VTA_DET.unid_vta%TYPE;
  pIND_EXONERADO_IGV        VTA_PEDIDO_VTA_DET.ind_exonerado_igv%TYPE;
  pSEC_GRUPO_IMPR        VTA_PEDIDO_VTA_DET.sec_grupo_impr%TYPE;
  pCANT_USADA_NC        VTA_PEDIDO_VTA_DET.cant_usada_nc%TYPE;
  pSEC_COMP_PAGO_ORIGEN        VTA_PEDIDO_VTA_DET.sec_comp_pago_origen%TYPE;
  pNUM_LOTE_PROD        VTA_PEDIDO_VTA_DET.num_lote_prod%TYPE;
  pFEC_PROCESO_GUIA_RD        VTA_PEDIDO_VTA_DET.fec_proceso_guia_rd%TYPE;
  pDESC_NUM_TEL_REC        VTA_PEDIDO_VTA_DET.desc_num_tel_rec%TYPE;
  pVAL_NUM_TRACE        VTA_PEDIDO_VTA_DET.val_num_trace%TYPE;
  pVAL_COD_APROBACION        VTA_PEDIDO_VTA_DET.val_cod_aprobacion%TYPE;
  pDESC_NUM_TARJ_VIRTUAL        VTA_PEDIDO_VTA_DET.desc_num_tarj_virtual%TYPE;
  pVAL_NUM_PIN        VTA_PEDIDO_VTA_DET.val_num_pin%TYPE;
  pFEC_VENCIMIENTO_LOTE        VTA_PEDIDO_VTA_DET.fec_vencimiento_lote%TYPE;
  pVAL_PREC_PUBLIC        VTA_PEDIDO_VTA_DET.val_prec_public%TYPE;
  pIND_CALCULO_MAX_MIN        VTA_PEDIDO_VTA_DET.ind_calculo_max_min%TYPE;
  pFEC_EXCLUSION        VTA_PEDIDO_VTA_DET.fec_exclusion%TYPE;
  pFECHA_TX        VTA_PEDIDO_VTA_DET.fecha_tx%TYPE;
  pHORA_TX        VTA_PEDIDO_VTA_DET.hora_tx%TYPE;
  pCOD_PROM        VTA_PEDIDO_VTA_DET.cod_prom%TYPE;
  pIND_ORIGEN_PROD        VTA_PEDIDO_VTA_DET.ind_origen_prod%TYPE;
  pVAL_FRAC_LOCAL        VTA_PEDIDO_VTA_DET.val_frac_local%TYPE;
  pCANT_FRAC_LOCAL        VTA_PEDIDO_VTA_DET.cant_frac_local%TYPE;
  pCANT_XDIA_TRA        VTA_PEDIDO_VTA_DET.cant_xdia_tra%TYPE;
  pCANT_DIAS_TRA        VTA_PEDIDO_VTA_DET.cant_dias_tra%TYPE;
  pIND_ZAN        VTA_PEDIDO_VTA_DET.ind_zan%TYPE;
  pVAL_PREC_PROM        VTA_PEDIDO_VTA_DET.val_prec_prom%TYPE;
  pDATOS_IMP_VIRTUAL        VTA_PEDIDO_VTA_DET.datos_imp_virtual%TYPE;
  pCOD_CAMP_CUPON        VTA_PEDIDO_VTA_DET.cod_camp_cupon%TYPE;
  pAHORRO        VTA_PEDIDO_VTA_DET.ahorro%TYPE;
  pPORC_DCTO_CALC        VTA_PEDIDO_VTA_DET.porc_dcto_calc%TYPE;
  pPORC_ZAN        VTA_PEDIDO_VTA_DET.porc_zan%TYPE;
  pIND_PROM_AUTOMATICO        VTA_PEDIDO_VTA_DET.ind_prom_automatico%TYPE;
  pAHORRO_PACK        VTA_PEDIDO_VTA_DET.ahorro_pack%TYPE;
  pPORC_DCTO_CALC_PACK        VTA_PEDIDO_VTA_DET.porc_dcto_calc_pack%TYPE;
  pCOD_GRUPO_REP        VTA_PEDIDO_VTA_DET.cod_grupo_rep%TYPE;
  pCOD_GRUPO_REP_EDMUNDO        VTA_PEDIDO_VTA_DET.cod_grupo_rep_edmundo%TYPE;
  pSEC_RESPALDO_STK        VTA_PEDIDO_VTA_DET.sec_respaldo_stk%TYPE;
  --pNUM_COMP_PAGO        VTA_PEDIDO_VTA_DET.num_comp_pago%TYPE;
  pSEC_COMP_PAGO_BENEF        VTA_PEDIDO_VTA_DET.sec_comp_pago_benef%TYPE;
  pSEC_COMP_PAGO_EMPRE        VTA_PEDIDO_VTA_DET.SEC_COMP_PAGO_EMPRE%TYPE;
  pVAL_PREC_TOTAL_EMPRE       VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL_EMPRE%TYPE;
  pVAL_PREC_TOTAL_BENEF       VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL_BENEF%TYPE;

 existeNroPedido VTA_COMP_PAGO_TEMP.NUM_PED_VTA%TYPE;

BEGIN


       BEGIN

       SELECT
        DISTINCT V.NUM_PED_VTA
         INTO existeNroPedido
       FROM (
             SELECT DISTINCT COMP.NUM_PED_VTA
               FROM VTA_COMP_PAGO_TEMP COMP
              WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                AND COMP.COD_LOCAL     = cCodLocal_in
                AND COMP.NUM_PED_VTA   = cNumPedVta_in
           UNION
            SELECT DISTINCT DET.NUM_PED_VTA
               FROM VTA_PEDIDO_VTA_DET_TEMP DET
              WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                AND DET.COD_LOCAL     = cCodLocal_in
                AND DET.NUM_PED_VTA   = cNumPedVta_in
           UNION
             SELECT DISTINCT FPAG.NUM_PED_VTA
                FROM VTA_FORMA_PAGO_PEDIDO_TEMP FPAG
              WHERE FPAG.COD_GRUPO_CIA = cCodGrupoCia_in
                AND FPAG.COD_LOCAL     = cCodLocal_in
                AND FPAG.NUM_PED_VTA   = cNumPedVta_in

             ) V;

             EXCEPTION
              WHEN NO_DATA_FOUND THEN
              existeNroPedido := NULL;
       END;

       IF    existeNroPedido IS NOT NULL THEN

           DELETE FROM VTA_COMP_PAGO_TEMP COMP  WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
                                                  AND COMP.COD_LOCAL     = cCodLocal_in
                                                  AND COMP.NUM_PED_VTA   = cNumPedVta_in;

           DELETE FROM VTA_PEDIDO_VTA_DET_TEMP DET  WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                                      AND DET.COD_LOCAL     = cCodLocal_in
                                                      AND DET.NUM_PED_VTA   = cNumPedVta_in;

           DELETE FROM VTA_FORMA_PAGO_PEDIDO_TEMP FPAG  WHERE FPAG.COD_GRUPO_CIA = cCodGrupoCia_in
                                                          AND FPAG.COD_LOCAL     = cCodLocal_in
                                                          AND FPAG.NUM_PED_VTA   = cNumPedVta_in;
       END IF;

       LOOP
               FETCH curComPagoTemp
               INTO
                    pCOD_GRUPO_CIA,          pCOD_LOCAL,
                    pNUM_PED_VTA,            pSEC_COMP_PAGO,
                    pTIP_COMP_PAGO,          pNUM_COMP_PAGO,
                    pSEC_MOV_CAJA,           pSEC_MOV_CAJA_ANUL,
                    pCANT_ITEM,              pCOD_CLI_LOCAL,
                    pNOM_IMPR_COMP,          pDIREC_IMPR_COMP,
                    pNUM_DOC_IMPR,           pVAL_BRUTO_COMP_PAGO,
                    pVAL_NETO_COMP_PAGO,     pVAL_DCTO_COMP_PAGO,
                    pVAL_AFECTO_COMP_PAGO,   pVAL_IGV_COMP_PAGO,
                    pVAL_REDONDEO_COMP_PAGO, pPORC_IGV_COMP_PAGO,
                    pUSU_CREA_COMP_PAGO,     pFEC_CREA_COMP_PAGO,
                    pUSU_MOD_COMP_PAGO,      pFEC_MOD_COMP_PAGO,
                    pFEC_ANUL_COMP_PAGO,     pIND_COMP_ANUL, pNUM_PEDIDO_ANUL,
                    pNUM_SEC_DOC_SAP,        pFEC_PROCESO_SAP,
                    pNUM_SEC_DOC_SAP_ANUL,   pFEC_PROCESO_SAP_ANUL,
                    pIND_RECLAMO_NAVSAT,     pVAL_DCTO_COMP,
                    pMOTIVO_ANULACION,       pFECHA_COBRO, pFECHA_ANULACION,
                    pFECH_IMP_COBRO,         pFECH_IMP_ANUL,
                    pTIP_CLIEN_CONVENIO,     pVAL_COPAGO_COMP_PAGO,
                    pVAL_IGV_COMP_COPAGO,    pNUM_COMP_COPAGO_REF,
                    pIND_AFECTA_KARDEX,      pPCT_BENEFICIARIO,
                    pPCT_EMPRESA,            pIND_COMP_CREDITO,
                    pTIP_COMP_PAGO_REF,      pNum_Comp_Pago_E,
                    pCod_Tipo_Proc_Pago;

               EXIT WHEN curComPagoTemp%NOTFOUND;
            INSERT INTO VTA_COMP_PAGO_TEMP(
                                            COD_GRUPO_CIA,          COD_LOCAL,
                                            NUM_PED_VTA,            SEC_COMP_PAGO,
                                            TIP_COMP_PAGO,          NUM_COMP_PAGO,
                                            SEC_MOV_CAJA,           SEC_MOV_CAJA_ANUL,
                                            CANT_ITEM,              COD_CLI_LOCAL,
                                            NOM_IMPR_COMP,          DIREC_IMPR_COMP,
                                            NUM_DOC_IMPR,           VAL_BRUTO_COMP_PAGO,
                                            VAL_NETO_COMP_PAGO,     VAL_DCTO_COMP_PAGO,
                                            VAL_AFECTO_COMP_PAGO,   VAL_IGV_COMP_PAGO,
                                            VAL_REDONDEO_COMP_PAGO, PORC_IGV_COMP_PAGO,
                                            USU_CREA_COMP_PAGO,     FEC_CREA_COMP_PAGO,
                                            USU_MOD_COMP_PAGO,      FEC_MOD_COMP_PAGO,
                                            FEC_ANUL_COMP_PAGO,     IND_COMP_ANUL,NUM_PEDIDO_ANUL,
                                            NUM_SEC_DOC_SAP,        FEC_PROCESO_SAP,
                                            NUM_SEC_DOC_SAP_ANUL,   FEC_PROCESO_SAP_ANUL,
                                            IND_RECLAMO_NAVSAT,     VAL_DCTO_COMP,
                                            MOTIVO_ANULACION,       FECHA_COBRO,FECHA_ANULACION,
                                            FECH_IMP_COBRO,         FECH_IMP_ANUL,
                                            TIP_CLIEN_CONVENIO,     VAL_COPAGO_COMP_PAGO,
                                            VAL_IGV_COMP_COPAGO,    NUM_COMP_COPAGO_REF,
                                            IND_AFECTA_KARDEX,      PCT_BENEFICIARIO,
                                            PCT_EMPRESA,            IND_COMP_CREDITO,
                                            TIP_COMP_PAGO_REF,
-- KMONCADA 02.10.2014 SE AGREGA COMPOS PARA COMP.ELECTRONICOS
                                            NUM_COMP_PAGO_E,        COD_TIP_PROC_PAGO
                                           )
                   VALUES(
                          pCOD_GRUPO_CIA,          pCOD_LOCAL,
                          pNUM_PED_VTA,            pSEC_COMP_PAGO,
                          pTIP_COMP_PAGO,          pNUM_COMP_PAGO,
                          pSEC_MOV_CAJA,           pSEC_MOV_CAJA_ANUL,
                          pCANT_ITEM,              pCOD_CLI_LOCAL,
                          pNOM_IMPR_COMP,          pDIREC_IMPR_COMP,
                          pNUM_DOC_IMPR,           pVAL_BRUTO_COMP_PAGO,
                          pVAL_NETO_COMP_PAGO,     pVAL_DCTO_COMP_PAGO,
                          pVAL_AFECTO_COMP_PAGO,   pVAL_IGV_COMP_PAGO,
                          pVAL_REDONDEO_COMP_PAGO, pPORC_IGV_COMP_PAGO,
                          pUSU_CREA_COMP_PAGO,     pFEC_CREA_COMP_PAGO,
                          pUSU_MOD_COMP_PAGO,      pFEC_MOD_COMP_PAGO,
                          pFEC_ANUL_COMP_PAGO,     pIND_COMP_ANUL,pNUM_PEDIDO_ANUL,
                          pNUM_SEC_DOC_SAP,        pFEC_PROCESO_SAP,
                          pNUM_SEC_DOC_SAP_ANUL,   pFEC_PROCESO_SAP_ANUL,
                          pIND_RECLAMO_NAVSAT,     pVAL_DCTO_COMP,
                          pMOTIVO_ANULACION,       pFECHA_COBRO,pFECHA_ANULACION,
                          pFECH_IMP_COBRO,         pFECH_IMP_ANUL,
                          pTIP_CLIEN_CONVENIO,     pVAL_COPAGO_COMP_PAGO,
                          pVAL_IGV_COMP_COPAGO,    pNUM_COMP_COPAGO_REF,
                          pIND_AFECTA_KARDEX,      pPCT_BENEFICIARIO,
                          pPCT_EMPRESA,            pIND_COMP_CREDITO,
                          pTIP_COMP_PAGO_REF,
-- KMONCADA 02.10.2014 SE AGREGA COMPOS PARA COMP.ELECTRONICOS
                          pNUM_COMP_PAGO,          pCod_Tipo_Proc_Pago
                       );

       END LOOP;
       CLOSE curComPagoTemp;

       LOOP

               FETCH curFormPagoTemp
               INTO
                    ppCOD_GRUPO_CIA,
                    ppCOD_LOCAL,
                    ppCOD_FORMA_PAGO,
                    ppNUM_PED_VTA,
                    ppIM_PAGO,
                    ppTIP_MONEDA,
                    ppVAL_TIP_CAMBIO,
                    ppVAL_VUELTO,
                    ppIM_TOTAL_PAGO,
                    ppNUM_TARJ,
                    ppFEC_VENC_TARJ,
                    ppNOM_TARJ,
                    ppFEC_CREA_FORMA_PAGO_PED,
                    ppUSU_CREA_FORMA_PAGO_PED,
                    ppFEC_MOD_FORMA_PAGO_PED,
                    ppUSU_MOD_FORMA_PAGO_PED,
                    ppCANT_CUPON,
                    ppTIPO_AUTORIZACION,
                    ppCOD_LOTE,
                    ppCOD_AUTORIZACION,
                    ppDNI_CLI_TARJ;
               EXIT WHEN curFormPagoTemp%NOTFOUND;
             INSERT INTO VTA_FORMA_PAGO_PEDIDO_TEMP(  COD_GRUPO_CIA,
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
                                                      DNI_CLI_TARJ
                                                    )
                   VALUES ( ppCOD_GRUPO_CIA,
                            ppCOD_LOCAL,
                            ppCOD_FORMA_PAGO,
                            ppNUM_PED_VTA,
                            ppIM_PAGO,
                            ppTIP_MONEDA,
                            ppVAL_TIP_CAMBIO,
                            ppVAL_VUELTO,
                            ppIM_TOTAL_PAGO,
                            ppNUM_TARJ,
                            ppFEC_VENC_TARJ,
                            ppNOM_TARJ,
                            ppFEC_CREA_FORMA_PAGO_PED,
                            ppUSU_CREA_FORMA_PAGO_PED,
                            ppFEC_MOD_FORMA_PAGO_PED,
                            ppUSU_MOD_FORMA_PAGO_PED,
                            ppCANT_CUPON,
                            ppTIPO_AUTORIZACION,
                            ppCOD_LOTE,
                            ppCOD_AUTORIZACION,
                            ppDNI_CLI_TARJ
                          );
           END LOOP;
           CLOSE curFormPagoTemp;

            LOOP

               FETCH curPedidoDetTemp
               INTO
                      pCOD_GRUPO_CIA      ,
                      pCOD_LOCAL      ,
                      pNUM_PED_VTA      ,
                      pSEC_PED_VTA_DET      ,
                      pCOD_PROD      ,
                      pCANT_ATENDIDA      ,
                      pVAL_PREC_VTA      ,
                      pVAL_PREC_TOTAL      ,
                      pPORC_DCTO_1      ,
                      pPORC_DCTO_2      ,
                      pPORC_DCTO_3      ,
                      pPORC_DCTO_TOTAL      ,
                      pEST_PED_VTA_DET      ,
                      pVAL_TOTAL_BONO      ,
                      pVAL_FRAC      ,
                      pSEC_COMP_PAGO      ,
                      pSEC_USU_LOCAL      ,
                      pUSU_CREA_PED_VTA_DET      ,
                      pFEC_CREA_PED_VTA_DET      ,
                      pUSU_MOD_PED_VTA_DET      ,
                      pFEC_MOD_PED_VTA_DET      ,
                      pVAL_PREC_LISTAVAL_IGV      ,
                      Pval_igv,
                      pUNID_VTA      ,
                      pIND_EXONERADO_IGV      ,
                      pSEC_GRUPO_IMPR      ,
                      pCANT_USADA_NC      ,
                      pSEC_COMP_PAGO_ORIGEN      ,
                      pNUM_LOTE_PROD      ,
                      pFEC_PROCESO_GUIA_RD      ,
                      pDESC_NUM_TEL_REC      ,
                      pVAL_NUM_TRACE      ,
                      pVAL_COD_APROBACION      ,
                      pDESC_NUM_TARJ_VIRTUAL      ,
                      pVAL_NUM_PIN      ,
                      pFEC_VENCIMIENTO_LOTE      ,
                      pVAL_PREC_PUBLIC      ,
                      pIND_CALCULO_MAX_MIN      ,
                      pFEC_EXCLUSION      ,
                      pFECHA_TX      ,
                      pHORA_TX      ,
                      pCOD_PROM      ,
                      pIND_ORIGEN_PROD      ,
                      pVAL_FRAC_LOCAL      ,
                      pCANT_FRAC_LOCAL      ,
                      pCANT_XDIA_TRA      ,
                      pCANT_DIAS_TRA      ,
                      pIND_ZAN      ,
                      pVAL_PREC_PROM      ,
                      pDATOS_IMP_VIRTUAL      ,
                      pCOD_CAMP_CUPON      ,
                      pAHORRO      ,
                      pPORC_DCTO_CALC      ,
                      pPORC_ZAN      ,
                      pIND_PROM_AUTOMATICO      ,
                      pAHORRO_PACK      ,
                      pPORC_DCTO_CALC_PACK      ,
                      pCOD_GRUPO_REP      ,
                      pCOD_GRUPO_REP_EDMUNDO      ,
                      pSEC_RESPALDO_STK      ,
                      pNUM_COMP_PAGO      ,
                      pSEC_COMP_PAGO_BENEF,
                      pSEC_COMP_PAGO_EMPRE,
                      pVAL_PREC_TOTAL_EMPRE,
                      pVAL_PREC_TOTAL_BENEF;
                  EXIT WHEN curPedidoDetTemp%NOTFOUND;
                  INSERT INTO VTA_PEDIDO_VTA_DET_TEMP(
                             cod_grupo_cia,
                             cod_local,
                             num_ped_vta,
                             sec_ped_vta_det,
                             cod_prod,
                             cant_atendida,
                             val_prec_vta,
                             val_prec_total,
                             porc_dcto_1,
                             porc_dcto_2,
                             porc_dcto_3,
                             porc_dcto_total,
                             est_ped_vta_det,
                             val_total_bono,
                             val_frac,
                             sec_comp_pago,
                             sec_usu_local,
                             usu_crea_ped_vta_det,
                             fec_crea_ped_vta_det,
                             usu_mod_ped_vta_det,
                             fec_mod_ped_vta_det,
                             val_prec_lista,val_igv,
                             unid_vta,
                             ind_exonerado_igv,
                             sec_grupo_impr,
                             cant_usada_nc,
                             sec_comp_pago_origen,
                             num_lote_prod,
                             fec_proceso_guia_rd,
                             desc_num_tel_rec,
                             val_num_trace,
                             val_cod_aprobacion,
                             desc_num_tarj_virtual,
                             val_num_pin,
                             fec_vencimiento_lote,
                             val_prec_public,
                             ind_calculo_max_min,
                             fec_exclusion,
                             fecha_tx,
                             hora_tx,
                             cod_prom,
                             ind_origen_prod,
                             val_frac_local,
                             cant_frac_local,
                             cant_xdia_tra,
                             cant_dias_tra,
                             ind_zan,
                             val_prec_prom,
                             datos_imp_virtual,
                             cod_camp_cupon,
                             ahorro,
                             porc_dcto_calc,
                             porc_zan,
                             ind_prom_automatico,
                             ahorro_pack,
                             porc_dcto_calc_pack,
                             cod_grupo_rep,
                             cod_grupo_rep_edmundo,
                             sec_respaldo_stk,
                             num_comp_pago,
                             sec_comp_pago_benef,
                             SEC_COMP_PAGO_EMPRE,
                             VAL_PREC_TOTAL_EMPRE,
                             VAL_PREC_TOTAL_BENEF
                             )
                   VALUES(
                          pCOD_GRUPO_CIA      ,
                          pCOD_LOCAL      ,
                          pNUM_PED_VTA      ,
                          pSEC_PED_VTA_DET      ,
                          pCOD_PROD      ,
                          pCANT_ATENDIDA      ,
                          pVAL_PREC_VTA      ,
                          pVAL_PREC_TOTAL      ,
                          pPORC_DCTO_1      ,
                          pPORC_DCTO_2      ,
                          pPORC_DCTO_3      ,
                          pPORC_DCTO_TOTAL      ,
                          pEST_PED_VTA_DET      ,
                          pVAL_TOTAL_BONO      ,
                          pVAL_FRAC      ,
                          pSEC_COMP_PAGO      ,
                          pSEC_USU_LOCAL      ,
                          pUSU_CREA_PED_VTA_DET      ,
                          pFEC_CREA_PED_VTA_DET      ,
                          pUSU_MOD_PED_VTA_DET      ,
                          pFEC_MOD_PED_VTA_DET      ,
                          pVAL_PREC_LISTAVAL_IGV      ,
                          Pval_igv,
                          pUNID_VTA      ,
                          pIND_EXONERADO_IGV      ,
                          pSEC_GRUPO_IMPR      ,
                          pCANT_USADA_NC      ,
                          pSEC_COMP_PAGO_ORIGEN      ,
                          pNUM_LOTE_PROD      ,
                          pFEC_PROCESO_GUIA_RD      ,
                          pDESC_NUM_TEL_REC      ,
                          pVAL_NUM_TRACE      ,
                          pVAL_COD_APROBACION      ,
                          pDESC_NUM_TARJ_VIRTUAL      ,
                          pVAL_NUM_PIN      ,
                          pFEC_VENCIMIENTO_LOTE      ,
                          pVAL_PREC_PUBLIC      ,
                          pIND_CALCULO_MAX_MIN      ,
                          pFEC_EXCLUSION      ,
                          pFECHA_TX      ,
                          pHORA_TX      ,
                          pCOD_PROM      ,
                          pIND_ORIGEN_PROD      ,
                          pVAL_FRAC_LOCAL      ,
                          pCANT_FRAC_LOCAL      ,
                          pCANT_XDIA_TRA      ,
                          pCANT_DIAS_TRA      ,
                          pIND_ZAN      ,
                          pVAL_PREC_PROM      ,
                          pDATOS_IMP_VIRTUAL      ,
                          pCOD_CAMP_CUPON      ,
                          pAHORRO      ,
                          pPORC_DCTO_CALC      ,
                          pPORC_ZAN      ,
                          pIND_PROM_AUTOMATICO      ,
                          pAHORRO_PACK      ,
                          pPORC_DCTO_CALC_PACK      ,
                          pCOD_GRUPO_REP      ,
                          pCOD_GRUPO_REP_EDMUNDO      ,
                          pSEC_RESPALDO_STK      ,
                          pNUM_COMP_PAGO      ,
                          pSEC_COMP_PAGO_BENEF,
                          pSEC_COMP_PAGO_EMPRE,
                          pVAL_PREC_TOTAL_EMPRE,
                          pVAL_PREC_TOTAL_BENEF
                        );

           END LOOP;
           CLOSE curPedidoDetTemp;

        COMMIT;
       RETURN 'S';

    EXCEPTION
       WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20032, 'ERROR NO ENCONTRA DATO EN LAS TABLAS TEMPORALES' ||SQLERRM);
          RETURN   'N';
       WHEN OTHERS THEN
          ROLLBACK;
          RAISE_APPLICATION_ERROR(-20030,'ERROR AL GRABAR TABLAS TEMPORALES' ||SQLERRM);
          RETURN   'N';

END;

  FUNCTION  CAJ_F_VERIFICA_PED_FOR_PAG(cCodGrupoCia_in IN CHAR,
                                             cCodLocal_in    IN CHAR,
                                             cNumPedVta_in   IN CHAR)
  RETURN CHAR
  IS

    v_cIndValidarMonto    PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='N';
    v_cEmailErrorPtoVenta PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE:='joliva';
    v_nValNetoPedVta      VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nValRedondeo        VTA_PEDIDO_VTA_CAB.VAL_REDONDEO_PED_VTA%TYPE;
    v_nSumaTotDet       NUMBER:=0;
    v_nSumaValorDet     NUMBER:=0;
    v_nSumaFormaPago       NUMBER:=0;

    v_vDescLocal VARCHAR2(120);

    mesg_body   VARCHAR2(4000);

  BEGIN

        SELECT TRIM(G.LLAVE_TAB_GRAL) INTO v_cIndValidarMonto
        FROM PBL_TAB_GRAL G
        WHERE G.ID_TAB_GRAL = 238;

        SELECT G.LLAVE_TAB_GRAL INTO v_cEmailErrorPtoVenta
        FROM PBL_TAB_GRAL  G
        WHERE G.ID_TAB_GRAL = 241;

        SELECT C.VAL_NETO_PED_VTA, C.VAL_REDONDEO_PED_VTA
        INTO v_nValNetoPedVta, v_nValRedondeo
        FROM  VTA_PEDIDO_VTA_CAB C
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   C.COD_LOCAL     = cCodLocal_in
        AND   C.NUM_PED_VTA   = cNumPedVta_in;

        SELECT SUM(D.VAL_PREC_TOTAL) into v_nSumaValorDet
        FROM VTA_PEDIDO_VTA_DET D
        WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   D.COD_LOCAL     = cCodLocal_in
        AND   D.NUM_PED_VTA   = cNumPedVta_in;

        --dubilluz 14.10.2011
        SELECT nvl(SUM(IM_TOTAL_PAGO - VAL_VUELTO),0) INTO v_nSumaFormaPago
        FROM   VTA_FORMA_PAGO_PEDIDO
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in
        AND    NUM_PED_VTA = cNumPedVta_in;

        v_nSumaTotDet:= v_nSumaValorDet+v_nValRedondeo;

        IF (v_nSumaFormaPago = v_nSumaTotDet) AND (v_nSumaFormaPago = v_nValNetoPedVta ) THEN
            RETURN 'EXITO';
        ELSE

            --DESCRIPCION DE LOCAL
            SELECT COD_LOCAL ||' - '|| DESC_LOCAL
            INTO   v_vDescLocal
            FROM   PBL_LOCAL
            WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND    COD_LOCAL = cCodLocal_in;

            --GENERANDO EL CONTENIDO DEL CORREO A ENVIAR
            mesg_body := '<H1>ERROR AL COBRAR PEDIDO DE VENTA</H1><BR>'||
                         '<i>inconsistencia de montos entre cabecera , detalle de pedido y suma de forma de pago</i><BR>'||
                         '<br>CABECERA PEDIDO ; ; : <b>'||to_char(v_nValNetoPedVta,'999,990.00')||
                         '</b> ;;;=====>; VAL_NETO_PED_VTA '||
                         '<br>DETALLE ; PEDIDO ; ; ;: <b>'||to_char(v_nSumaTotDet,'999,990.00')||
                         '</b> ;;;=====>; SUM(D.VAL_PREC_TOTAL):<b>'||
                         to_char(v_nSumaValorDet,'999,990.00')||'</b> + VAL_REDONDEO_PED_VTA: <B>'||
                         to_char(v_nValRedondeo,'999,990.00')||'</B> )'||
                         '<br>TOTAL FORMA PAGO : <b>'||to_char(v_nSumaFormaPago,'999,990.00')||
                         '</b> ;;;=====>; SUM(IM_TOTAL_PAGO - VAL_VUELTO):<b>'||
                         '<BR><br> NUM_PEDIDO : <B>'||cNumPedVta_in||'</B>'||
                         '<BR> LOCAL : <B>'||v_vDescLocal||'</B>'||
                         '<BR><BR> FECHA : <B>'||to_char(SYSDATE,'dd/MM/yyyy HH24:MI:SS')||'</B>';

            --ENVIANDO EL CORREO
            FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                           v_cEmailErrorPtoVenta,
                           'ERROR AL COBRAR PEDIDO: DIFER. TOTAL CABECERA , DETALLE, FORMA PAGO : '||v_vDescLocal,
                           'ALERTA',
                           mesg_body,
                           '',
                           FARMA_EMAIL.GET_EMAIL_SERVER,
                           TRUE);

            dbms_output.put_line('v_cIndValidarMonto--> '||v_cIndValidarMonto);

            IF v_cIndValidarMonto = 'S' THEN
               RETURN 'ERROR';
            ELSE
               RETURN 'EXITO';
            END IF;


        END IF;

  END;

FUNCTION BTLMF_F_DEV_SALUDO RETURN VARCHAR2
IS
C_HORA    INTEGER:= TO_NUMBER(TO_CHAR(SYSDATE,'HH24'));
C_SALUDO  VARCHAR2(100);
BEGIN
     SELECT
       CASE
        WHEN C_HORA  >=0 AND C_HORA <12 THEN   'Buenos dias'
        WHEN C_HORA  >=12 AND C_HORA <19 THEN  'Buenas tardes'
        WHEN C_HORA  >=19 AND C_HORA <=24 THEN  'Buenas noches'
      END
      INTO C_SALUDO
     FROM DUAl;
RETURN C_SALUDO;
END;


FUNCTION BTLMF_F_ES_COMP_CREDITO(cCodCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                                 cCodLocal_in  IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                                 cNroPedido_in IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                 cTipComp_in   IN VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE,
                                 cNumComp_in   IN VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE)
    RETURN CHAR IS

    vIndCompCredito VARCHAR2(1);

BEGIN
  --ERIOS 2.4.3 Determina si es comp de credito si tiene la forma pago 00080
           SELECT DECODE( COUNT(1),0,'N','S') INTO vIndCompCredito
      FROM VTA_FORMA_PAGO_PEDIDO
      WHERE COD_GRUPO_CIA = cCodCia_in
              AND COD_LOCAL     = cCodLocal_in
              AND NUM_PED_VTA   = cNroPedido_in
              AND COD_FORMA_PAGO IN ('00080','00050') -- KMONCADA 25.06.2014 PARA VENTA INSTITUCIONAL
              AND IM_TOTAL_PAGO > 0;
           --vIndCompCredito := 'N';

    RETURN vIndCompCredito;
END;


FUNCTION CAMP_F_VAR_MSJ_ANULACION(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in   IN CHAR,
                                   cajero_in      IN CHAR,
                                   turno_in     IN CHAR,
                                   numpedido_in IN CHAR,
                                   cod_igv_in IN CHAR ,
                                   cIndReimpresion_in in CHAR,
                                   numComprobante in CHAR)

  RETURN VARCHAR2
  IS

  vMsg_out varchar2(32767);

  vMonto       NUMBER(9,3);
  vUsuario     varchar2(28);
  vLocalDes     varchar2(2800);
  vNroTicket     varchar2(20);
  vMoneda     varchar2(2);
  vFechaVenta varchar2(100);
  vNumComPago char(10);--JCORTEZ 17.07.09
  vMotivoAnulacion varchar2(2000);--JQUISPE 25.03.2010

  BEGIN

  begin
  -- se obtiene el monto afcecto o inafecto segun sea el caso
  --ERIOS 2.3.2 Solo comprobantes TICKET
  select SUM(D.VAL_PREC_VTA * d.cant_atendida),
         c.usu_crea_ped_vta_cab,
         C.TIP_PED_VTA,
         c.fec_ped_vta,
         A.NUM_COMP_PAGO,
         c.motivo_anulacion--JQUISPE 25.03.2010
    into vMonto, vUsuario, vMoneda, vFechaVenta,vNumComPago,vMotivoAnulacion
    from vta_pedido_vta_cab c, lgt_prod P, vta_pedido_vta_det D,VTA_COMP_PAGO A
   where c.cod_grupo_cia = cCodGrupoCia_in
     and c.cod_local = cCodLocal_in
     and c.num_ped_vta = numpedido_in
     and a.num_comp_pago = numComprobante
     and c.est_ped_vta = 'C'
     and c.cod_grupo_cia = d.cod_grupo_cia
     and c.cod_local = d.cod_local
     and c.num_ped_vta = d.num_ped_vta
     and d.cod_grupo_cia = p.cod_grupo_cia
     and D.COD_PROD = P.COD_PROD
     and A.tip_comp_pago IN ('05','06')
     and P.COD_IGV = cod_igv_in
     --JCORTEZ 17.07.09 Se obtiene numero de comprobante
     AND C.COD_GRUPO_CIA=A.COD_GRUPO_CIA
     AND C.NUM_PED_VTA=A.NUM_PED_VTA
     AND C.COD_LOCAL=A.COD_LOCAL
     AND D.SEC_COMP_PAGO_benef=A.SEC_COMP_PAGO
   group by c.usu_crea_ped_vta_cab, C.TIP_PED_VTA, c.fec_ped_vta,A.NUM_COMP_PAGO,C.MOTIVO_ANULACION;

  --se obtiene la descripcion del local

  select l.desc_local
  into  vLocalDes
  from   pbl_local l
  where  cod_local = cCodLocal_in;


  --se obtiene el numero de ticket asociado al comprobante de pago

  select DISTINCT(SUBSTR(c.num_comp_pago,1,3) || '-' ||  SUBSTR(c.num_comp_pago,4,10))
  into  vNroTicket
  from  vta_comp_pago c,
        VTA_PEDIDO_VTA_DET D,
        LGT_PROD A
  where D.cod_grupo_cia = cCodGrupoCia_in
  and   D.cod_local = cCodLocal_in
  and   D.num_ped_vta = numpedido_in
  and   c.num_comp_pago = numComprobante
  AND   C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
  AND   C.COD_LOCAL = D.COD_LOCAL
  AND   C.NUM_PED_VTA = D.NUM_PED_VTA
 -- AND   C.SEC_COMP_PAGO = D.SEC_COMP_PAGO
  AND   A.COD_GRUPO_CIA = D.COD_GRUPO_CIA
  AND   A.COD_PROD = D.COD_PROD;
 -- AND   A.COD_IGV=cod_igv_in;
  --and  c.val_neto_comp_pago = vMonto ;



  if vMoneda='01' then
     vMoneda:= 's/' ;
  else
     vMoneda:= '$/' ;
  end if;

              IF(vMonto>0) THEN

                    vMsg_out :=

                       cCodLocal_in  || ' ' ||  vLocalDes   || 'Ã'

                      ||  vNroTicket || 'Ã'

                      ||  to_char(sysdate, 'dd/mm/yyyy hh24:mi:ss')  || 'Ã'

                      ||  cajero_in  || 'Ã'

                      || turno_in    || 'Ã'

                      ||  vUsuario   || 'Ã'

                      ||  vMoneda || to_char(vMonto,'999990.00')  || 'Ã'

                      ||  vFechaVenta|| 'Ã' --to_char(vFechaVenta, 'dd/mm/rrrr hh24:mi:ss') ;

                      || vNumComPago || 'Ã' --JCORTEZ 17.07.09

                      || vMotivoAnulacion;  --JQUISPE 25.03.2010
               ELSE
                      vMsg_out:='N';
               END IF;

               if cIndReimpresion_in = 'S' then

               PTOVENTA_TICKETERA.INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,
                                            cCodLocal_in,
                                            'REIMPRESION TICKET ANULADO',
                                            'AVISO DE CONTROL',
                                            'Aviso se esta reimprimiendo la anulacion del ticket.<br>'||
                                            cCodLocal_in  || ' ' ||  vLocalDes || '<br>'
                                            ||  vNroTicket || '<br>'
                                            ||  to_char(sysdate, 'dd/mm/yyyy hh24:mi:ss')  || '<br>'
                                            ||  cajero_in  || '<br>'
                                            || turno_in    || '<br>'
                                            ||  vUsuario   || '<br>'
                                            ||  vMoneda || to_char(vMonto,'999990.00')  || '<br>'
                                            ||  vFechaVenta || '<br>'
                                            ||  vMotivoAnulacion
                                            );
                end if;


     exception
     when no_data_found then
      vMsg_out:='N';
     end;

     RETURN vMsg_out;

  END;



   FUNCTION BTLMF_F_CHAR_PIDE_COPAGO_CONV(vCodConvenio_in IN VARCHAR2)
    RETURN CHAR AS
    vResultado CHAR(1);
    vCantidad  integer;

  BEGIN

    SELECT M.FLG_POLITICA
      INTO vResultado
      FROM MAE_CONVENIO m
     WHERE m.cod_convenio = vCodConvenio_in;

       IF vResultado = '0'  THEN
          vResultado := 'S';
       ELSE
           vResultado := 'N';
       END IF;

       IF vResultado='S' THEN
          SELECT count(1) into vCantidad FROM PTOVENTA.aux_copago_sel
          WHERE COD_CONVENIO=vCodConvenio_in;

          IF vCantidad>0 THEN
             vResultado := 'S';
          ELSE
              vResultado := 'N';
          END IF;

       END IF;

    RETURN vResultado;

  END;


   FUNCTION VTA_LISTA_FILTRO_COPAGO(cCodConvenio IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR

    SELECT dATO
    FROM  (
     SELECT PCT_COPAGO||' %'|| 'Ã' ||PCT_COPAGO  DATO
     FROM PTOVENTA.aux_copago_sel
        WHERE COD_CONVENIO=cCodConvenio
        ORDER BY PCT_COPAGO ASC
        );

    RETURN curVta;
  END;
   /* *************************************************************** */
procedure BTLMF_P_AUX_PRECION_DET_CONV (cCodGrupoCia_in IN CHAR,
                                           cCodLocal_in    IN CHAR,
                                           cNumPedVta_in   IN CHAR
                                           )is
   BEGIN

     delete TMP_VTA_DET_CONV_AUX;

      insert into TMP_VTA_DET_CONV_AUX
      (sec_ped_vta_det, cod_prod, val_prec_total, sec_comp_pago_empre, sec_comp_pago_benef, neto_empresa,
       pct_empresa, neto_benefi, pct_beneficiario, prev_n_empresa, prev_n_benefi)
      SELECT DE.SEC_PED_VTA_DET,DE.COD_PROD,DE.VAL_PREC_TOTAL,DE.SEC_COMP_PAGO_EMPRE,DE.SEC_COMP_PAGO_BENEF,
             CPE.VAL_NETO_COMP_PAGO NETO_EMPRESA,CPE.PCT_EMPRESA,
             CPB.VAL_NETO_COMP_PAGO NETO_BENEFI,CPB.PCT_BENEFICIARIO,
             ROUND(DE.VAL_PREC_TOTAL*CPE.PCT_EMPRESA/100,2) PREV_N_EMPRESA,
             ROUND(DE.VAL_PREC_TOTAL*CPB.PCT_BENEFICIARIO/100,2) PREV_N_BENEFI
      FROM   VTA_PEDIDO_VTA_cab ca,
             VTA_PEDIDO_VTA_DET DE,
             VTA_COMP_PAGO CPE,
             VTA_COMP_PAGO CPB
      where  ca.ind_conv_btl_mf = 'S'
      AND    DE.SEC_COMP_PAGO_EMPRE IS NOT NULL
      AND    DE.Sec_Comp_Pago_Benef IS NOT NULL
      and    ca.cod_grupo_cia = cCodGrupoCia_in
      and    ca.cod_local = cCodLocal_in
      --and    ca.est_ped_vta = 'C'
      and    ca.num_ped_vta = cNumPedVta_in
      AND    CA.COD_GRUPO_CIA = DE.COD_GRUPO_CIA
      AND    CA.COD_LOCAL = DE.COD_LOCAL
      AND    CA.NUM_PED_VTA = DE.NUM_PED_VTA
      AND    DE.COD_GRUPO_CIA = CPE.COD_GRUPO_CIA
      AND    DE.COD_LOCAL = CPE.COD_LOCAL
      AND    DE.NUM_PED_VTA = CPE.NUM_PED_VTA
      AND    DE.SEC_COMP_PAGO_EMPRE = CPE.SEC_COMP_PAGO
      AND    DE.COD_GRUPO_CIA = CPB.COD_GRUPO_CIA
      AND    DE.COD_LOCAL = CPB.COD_LOCAL
      AND    DE.NUM_PED_VTA = CPB.NUM_PED_VTA
      AND    DE.SEC_COMP_PAGO_BENEF = CPB.SEC_COMP_PAGO;

      UPDATE VTA_PEDIDO_VTA_DET DET
      SET    DET.VAL_PREC_TOTAL_EMPRE = (
                                          SELECT  PRECIO_FINAL
                                          FROM    (
                                              SELECT VDE.SEC_PED_VTA_DET,VDE.COD_PROD,VDE.SEC_COMP_PAGO_EMPRE,
                                                     VDE.PREV_N_EMPRESA,VDE.DIF,VDE.FILA,
                                                     CASE
                                                       WHEN VDE.FILA = 1 THEN VDE.PREV_N_EMPRESA + VDE.DIF
                                                       ELSE VDE.PREV_N_EMPRESA
                                                     END PRECIO_FINAL
                                              FROM   (
                                                      SELECT UE.SEC_PED_VTA_DET,UE.COD_PROD,UE.SEC_COMP_PAGO_EMPRE,UE.PREV_N_EMPRESA,TT.DIF,
                                                             ROWNUM FILA
                                                      FROM   TMP_VTA_DET_CONV_AUX UE,
                                                             (
                                                              SELECT VE.SEC_COMP_PAGO_EMPRE,VE.NETO_EMPRESA - VE.NETO_NVO DIF
                                                              FROM  (
                                                                    SELECT T.SEC_COMP_PAGO_EMPRE,T.NETO_EMPRESA,SUM(T.PREV_N_EMPRESA) NETO_NVO
                                                                    FROM   TMP_VTA_DET_CONV_AUX T
                                                                    GROUP BY T.SEC_COMP_PAGO_EMPRE,T.NETO_EMPRESA
                                                                    ) VE
                                                             )   TT
                                                      WHERE  UE.SEC_COMP_PAGO_EMPRE =  TT.SEC_COMP_PAGO_EMPRE
                                                      ) VDE
                                                   ) TF
                                           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND    DET.COD_LOCAL = cCodLocal_in
                                           AND    DET.NUM_PED_VTA = cNumPedVta_in
                                           AND    DET.SEC_PED_VTA_DET = TF.SEC_PED_VTA_DET
                                          )
      WHERE  EXISTS  (
                            SELECT  1
                            FROM    (
                                SELECT VDE.SEC_PED_VTA_DET,VDE.COD_PROD,VDE.SEC_COMP_PAGO_EMPRE,
                                       VDE.PREV_N_EMPRESA,VDE.DIF,VDE.FILA,
                                       CASE
                                         WHEN VDE.FILA = 1 THEN VDE.PREV_N_EMPRESA + VDE.DIF
                                         ELSE VDE.PREV_N_EMPRESA
                                       END PRECIO_FINAL
                                FROM   (
                                        SELECT UE.SEC_PED_VTA_DET,UE.COD_PROD,UE.SEC_COMP_PAGO_EMPRE,UE.PREV_N_EMPRESA,TT.DIF,
                                               ROWNUM FILA
                                        FROM   TMP_VTA_DET_CONV_AUX UE,
                                               (
                                                SELECT VE.SEC_COMP_PAGO_EMPRE,VE.NETO_EMPRESA - VE.NETO_NVO DIF
                                                FROM  (
                                                      SELECT T.SEC_COMP_PAGO_EMPRE,T.NETO_EMPRESA,SUM(T.PREV_N_EMPRESA) NETO_NVO
                                                      FROM   TMP_VTA_DET_CONV_AUX T
                                                      GROUP BY T.SEC_COMP_PAGO_EMPRE,T.NETO_EMPRESA
                                                      ) VE
                                               )   TT
                                        WHERE  UE.SEC_COMP_PAGO_EMPRE =  TT.SEC_COMP_PAGO_EMPRE
                                        ) VDE
                                     ) TF
                             WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                             AND    DET.COD_LOCAL = cCodLocal_in
                             AND    DET.NUM_PED_VTA = cNumPedVta_in
                             AND    DET.SEC_PED_VTA_DET = TF.SEC_PED_VTA_DET
                            )
      AND    DET.COD_GRUPO_CIA =  cCodGrupoCia_in
      AND    DET.COD_LOCAL = cCodLocal_in
      AND    DET.NUM_PED_VTA = cNumPedVta_in;

      UPDATE VTA_PEDIDO_VTA_DET DET
      SET    DET.VAL_PREC_TOTAL_BENEF = (
                                          SELECT  PRECIO_FINAL
                                          FROM    (
                                                     SELECT VDE.SEC_PED_VTA_DET,VDE.COD_PROD,VDE.SEC_COMP_PAGO_BENEF,
                                                     VDE.PREV_N_BENEFI,VDE.DIF,VDE.FILA,
                                                     CASE
                                                       WHEN VDE.FILA = 1 THEN VDE.PREV_N_BENEFI + VDE.DIF
                                                       ELSE VDE.PREV_N_BENEFI
                                                     END PRECIO_FINAL
                                              FROM   (
                                                      SELECT UE.SEC_PED_VTA_DET,UE.COD_PROD,UE.SEC_COMP_PAGO_BENEF,UE.PREV_N_BENEFI,TT.DIF,
                                                             ROWNUM FILA
                                                      FROM   TMP_VTA_DET_CONV_AUX UE,
                                                             (
                                                              SELECT VE.SEC_COMP_PAGO_BENEF,VE.NETO_BENEFI - VE.NETO_NVO DIF
                                                              FROM  (
                                                                    SELECT T.SEC_COMP_PAGO_BENEF,T.NETO_BENEFI,SUM(T.PREV_N_BENEFI) NETO_NVO
                                                                    FROM   TMP_VTA_DET_CONV_AUX T
                                                                    GROUP BY T.SEC_COMP_PAGO_BENEF,T.NETO_BENEFI
                                                                    ) VE
                                                             )   TT
                                                      WHERE  UE.SEC_COMP_PAGO_BENEF =  TT.SEC_COMP_PAGO_BENEF
                                                      ) VDE
                                                   ) TF
                                           WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND    DET.COD_LOCAL =cCodLocal_in
                                           AND    DET.NUM_PED_VTA = cNumPedVta_in
                                           AND    DET.SEC_PED_VTA_DET = TF.SEC_PED_VTA_DET
                                          )
      WHERE  EXISTS  (
                            SELECT  1
                            FROM    (
                                SELECT VDE.SEC_PED_VTA_DET,VDE.COD_PROD,VDE.SEC_COMP_PAGO_BENEF,
                                       VDE.PREV_N_BENEFI,VDE.DIF,VDE.FILA,
                                       CASE
                                         WHEN VDE.FILA = 1 THEN VDE.PREV_N_BENEFI + VDE.DIF
                                         ELSE VDE.PREV_N_BENEFI
                                       END PRECIO_FINAL
                                FROM   (
                                        SELECT UE.SEC_PED_VTA_DET,UE.COD_PROD,UE.SEC_COMP_PAGO_BENEF,UE.PREV_N_BENEFI,TT.DIF,
                                               ROWNUM FILA
                                        FROM   TMP_VTA_DET_CONV_AUX UE,
                                               (
                                                SELECT VE.SEC_COMP_PAGO_BENEF,VE.NETO_BENEFI - VE.NETO_NVO DIF
                                                FROM  (
                                                      SELECT T.SEC_COMP_PAGO_BENEF,T.NETO_BENEFI,SUM(T.PREV_N_BENEFI) NETO_NVO
                                                      FROM   TMP_VTA_DET_CONV_AUX T
                                                      GROUP BY T.SEC_COMP_PAGO_BENEF,T.NETO_BENEFI
                                                      ) VE
                                               )   TT
                                        WHERE  UE.SEC_COMP_PAGO_BENEF =  TT.SEC_COMP_PAGO_BENEF
                                        ) VDE
                                     ) TF
                             WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
                             AND    DET.COD_LOCAL = cCodLocal_in
                             AND    DET.NUM_PED_VTA = cNumPedVta_in
                             AND    DET.SEC_PED_VTA_DET = TF.SEC_PED_VTA_DET
                            )
      AND    DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    DET.COD_LOCAL = cCodLocal_in
      AND    DET.NUM_PED_VTA = cNumPedVta_in;

   END;
   /* *************************************************************** */
    FUNCTION MIFARMA_IMPR_MSG_GRAL(cCodGrupoCia_in in CHAR,
                                     cCodLocal_in    in CHAR,
                                     cTipoComp IN CHAR,
                                     cNumComp IN CHAR,
                   vNumeroDoc IN VARCHAR2,
                   vNumPedVta_in in varchar2,
                   -- KMONCADA 02.12.2014 INDICADOR DE MENSAJE DE REIMPRESION
                   vIsReimpresion IN CHAR DEFAULT 'N'
                                     )
  RETURN FarmaCursor IS
    curComp FarmaCursor;
    vMsjeImpresionUno       VARCHAR2(4000);
    vMsjeImpresionDos       VARCHAR2(4000);
    vMsjeImpresionTres      VARCHAR2(4000);
    vMsjeImpresionCuaro     VARCHAR2(4000);
    vTipProcPago            VARCHAR2(10);
    vNumCompElec            VARCHAR2(15);
    vNumComprobante         VARCHAR2(15);

    desComprobantePago  VARCHAR2(400):= ' ';
   -- vNroComp    INTEGER := 1;
   vCadena VARCHAR2(500);
   vEnvioContigencia VARCHAR2(100);
  BEGIN

    -- DUBILLUZ 15.10.2014
    SELECT case
           when NVL(CA.IND_COMP_MANUAL,'N') = 'S' then 'REGULARIZACION VENTA MANUAL<BR>'
           ELSE
             ''
           END
    INTO   vCadena
    FROM   VTA_PEDIDO_VTA_CAB CA
    WHERE  CA.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    CA.COD_LOCAL = cCodLocal_in
    AND    CA.NUM_PED_VTA = vNumPedVta_in;

    --CHUANES
    --04/09/2014
    --CONSULTAMOS SI ES ELECTRONICO
      SELECT NVL(X.COD_TIP_PROC_PAGO,'0'),
             FARMA_UTILITY.GET_T_COMPROBANTE_2(X.COD_TIP_PROC_PAGO,
                                               X.NUM_COMP_PAGO_E,
                                               X.NUM_COMP_PAGO)

      --,NVL(X.NUM_COMP_PAGO_E,'0') ,NVL(X.NUM_COMP_PAGO,'0')
      INTO vTipProcPago--,vNumCompElec
           ,vNumComprobante
      FROM  VTA_COMP_PAGO X
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in AND
      X.COD_LOCAL=cCodLocal_in AND
      X.SEC_COMP_PAGO=vNumeroDoc AND  --NUM_COMP_PAGO -- CESAR HUANES 18.09.2014
     --DECODE(X.COD_TIP_PROC_PAGO,'1',X.NUM_COMP_PAGO_E,X.NUM_COMP_PAGO)=vNumeroDoc AND -- LTAVARA 18.09.2014 VALIDACION
      X.TIP_COMP_PAGO=cTipoComp and
      x.num_ped_vta = vNumPedVta_in;
      --FIN OBTENER DATOS ELECTRONICOS


           IF   cTipoComp  = COD_TIP_COMP_BOLETA THEN
                IF(vTipProcPago='1') THEN
                desComprobantePago := 'BOLETA ELECTRONICA';
                ELSE  desComprobantePago := 'BOLETA';
                END IF;

           ELSIF cTipoComp = COD_TIP_COMP_FACTURA THEN
            IF(vTipProcPago='1') THEN
                desComprobantePago := 'FACTURA ELECTRONICA';
                ELSE
                 desComprobantePago := 'FACTURA';
                 END IF;

           ELSIF cTipoComp = COD_TIP_COMP_GUIA THEN
                 desComprobantePago := 'GUIA';

           ELSIF cTipoComp = COD_TIP_COMP_TICKET THEN
            IF(vTipProcPago='1') THEN
                desComprobantePago := 'BOLETA ELECTRONICA';
                ELSE
                 desComprobantePago := 'TICKET';
                END IF;

           END IF;

           -- SE MOSTRARA MENSAJE DE REIMPRESION EN CASO DE CONTIGENCIA.
           IF vTipProcPago='0' AND vIsReimpresion = 'N' THEN
             vEnvioContigencia := '<td align="right" width="500px"><h3>; Presione [F6] Envio a Re-Impresion...</h3></td>';
           END IF;

         --CHUANES
         --04/092014
            /*IF(vTipProcPago='1') THEN

            vNumComprobante:=vNumCompElec;
            ELSE
            vNumComprobante:=vNumComprobante;
            END IF;*/

           vMsjeImpresionUno := '  <table cellpadding=0 cellspacing=0 align=left> '||
                                '   <tr> '||
                                '    <td></td> '||
                                '    <td width=638 height=383 bgcolor=white style="border:.75pt solid black; '||
                                '    vertical-align:top;background:white"><span '||
                                '    style="position:absolute;mso-ignore:vglayout;z-index:1"> '||
                                '    <table cellpadding=0 cellspacing=0 width=100%> '||
                                '     <tr> '||
                                '     <td> '||
                                '      <div v:shape=_x0000_s1026 style="padding:4.35pt 7.95pt 4.35pt 7.95pt" '||
                                '      class=shape> '||
                                '      <p class=MsoNormal align=center style="text-align:center"><b><span lang=ES '||
                                '      style="font-family:Arial,sans-serif">*****************************************************************************************<o:p></o:p></span></b></p>'||
                                '      <p class=MsoNormal align=center style="text-align:center"><span lang=ES '||
                                '      style="font-size:48.0pt;font-family:Arial,sans-serif;color:#C00000">' ||
                                '      <font color=#C00000 size =8 face=Arial>¡¡ ';
           vMsjeImpresionDos := '      ALERTA !!</font><o:p></o:p></span></p> '||
                                '      <p class=MsoNormal align=center style="text-align:center"><b><span lang=ES '||
                                '      style="font-size:18;font-family:Arial,sans-serif;color:#C00000">' ||
                                '      <font color=#C00000 size =6 face=Arial>LEER '||
                               '      LAS INDICACIONES</font><o:p></o:p></span></b></p>'||
                              '      <p class=MsoNormal align=left style="text-align:center"><b><span lang=ES '||
                              '      style="font-size:26.0pt;font-family:Arial,sans-serif"><o:p>'||
                              vCadena||
                              '<font color=#139128 size =8 face=Arial> '||desComprobantePago||' - No. '||vNumComprobante||' ('||cNumComp||') Imprimir.</font></o:p></span></b></p> '||
                              '      <p class=MsoNormal><b><span lang=ES style="font-family:Arial,sans-serif; '||
                              '      color:#139128"> </span></b><b><span lang=ES style="font-size:18.0pt; '||
                              '      font-family:Arial,sans-serif;color:#139128">' ||
                              '     <font color=#139128 size =5 face=Arial>Revisar:</font>' ||
                              '      <o:p></o:p></span></b></p> '||
                              '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                              '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                              '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                              '      style="mso-list:Ignore">·<span style="font:7.0pt Times New Roman">;;;;;; '||
                              '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                              '      font-family:Arial,sans-serif;color:#139128">'||
                              '     <font color=#139128 size =5 face=Arial>Si esta prendida la ';
           vMsjeImpresionTres := '      impresora de '||desComprobantePago||'.</font>' ||
                                '  <o:p></o:p></span></b></p> '||
                              '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                              '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                              '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                              '      style="mso-list:Ignore">·<span style="font:7.0pt Times New Roman">;;;;;; '||
                              '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                              '      font-family:Arial,sans-serif;color:#139128"><font color=#139128 size =5 face=Arial>Si cuenta con papel para '||
                              '      imprimir '||desComprobantePago||'.</font><o:p></o:p></span></b></p> '||
                              '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                              '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                              '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                              '      style="mso-list:Ignore">·<span style="font:7.0pt Times New Roman">;;;;;; ';
          vMsjeImpresionCuaro :=   '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                              '      font-family:Arial,sans-serif;color:#139128">' ||
                              '  <font color=#139128 size =5 face=Arial>Si el Correlativo del '||
                              '      comprobante sea el mismo con el sistema.</font><o:p></o:p></span></b></p> '||
                              '      <p class=MsoNormal><span lang=ES style="font-family:Arial,sans-serif">*****************************************************************************************<o:p></o:p></span>' ||
                              /*'      <H3>'||
                              '          ' ||
                              '          ' ||
                              '          ; Presione [Enter] para continuar...</H3>'*/
                              '        <table>'||
                              '<tr>'||
                              '  <td width="400px">		'||
                              '    <h3>; Presione [Enter] para continuar...</h3>'||
                              '  </td>'||
                              vEnvioContigencia||
                              '</tr>'||
                              '</table>'||
                              '</o:p></p> '||
                              '      </div> '||
                              '      <![if !mso]></td> '||
                              '     </tr> '||
                              '    </table> '||
                              '    </span></td> '||
                              '   </tr> '||
                              '  </table> ';


      open curComp for
      SELECT
             vMsjeImpresionUno  AS UNO ,
             vMsjeImpresionDos  AS DOS,
             vMsjeImpresionTres AS TRES  ,
             vMsjeImpresionCuaro AS CUATRO
        FROM DUAL;


       RETURN curComp;
   END;


   FUNCTION BTLMF_OBT_NU_COM_CONV_GUIA(       cCodGrupoCia_in   IN CHAR,
                                              cCodLocal_in      IN CHAR,
                                              cfecha_in         IN DATE,
                                              cTipComp_in       IN CHAR,
                                              cNumComPago_in    IN CHAR
                                     )

  RETURN CHAR
  IS


   ano_actual   VARCHAR2(4);
   mes_actual   VARCHAR2(2);
   ano_fec      VARCHAR2(4);
   mes_fec      VARCHAR2(2);
   vCant        NUMBER;
   -- dubilluz 23.04.2014
   cfecha_in_NEW date;
      BEGIN
      -- dubilluz 23.04.2014
      select distinct ca.fec_ped_vta
      into   cfecha_in_NEW
      from   vta_pedido_vta_cab ca,
             vta_comp_pago cp
      where  cp.cod_grupo_cia = cCodGrupoCia_in
      and    cp.cod_local = cCodLocal_in
      and    cp.tip_comp_pago = cTipComp_in
      and    cp.num_comp_pago = cNumComPago_in
      and    ca.cod_grupo_cia = cp.cod_grupo_cia
      and    ca.cod_local = cp.cod_local
      and    ca.num_ped_vta = cp.num_ped_vta;


           SELECT to_char(sysdate, 'YYYY') INTO ano_actual FROM dual ;
           SELECT to_char(sysdate, 'MM') INTO mes_actual  FROM dual ;
           SELECT TO_CHAR(cfecha_in_NEW,'YYYY') INTO ano_fec   FROM DUAL;
           SELECT TO_CHAR(cfecha_in_NEW,'MM') INTO mes_fec FROM DUAL;

          SELECT   COUNT(*) INTO vCant
          FROM
                      mae_convenio t INNER JOIN mae_tipo_comp_pago_btlmf mae
                                ON( mae.COD_TIPODOC = nvl(t.COD_TIPDOC_CLIENTE,t.cod_tipdoc_beneficiario)
                                  )
            WHERE
                      nvl(t.COD_TIPDOC_CLIENTE,t.cod_tipdoc_beneficiario)='GRL' AND
                      t.cod_convenio =

                            (SELECT VP.cod_convenio FROM VTA_PEDIDO_VTA_CAB VP INNER JOIN
                                          VTA_COMP_PAGO  VC
                                          ON(
                                            VC.COD_GRUPO_CIA  = VP.COD_GRUPO_CIA  AND
                                            VC.COD_LOCAL      = VP.COD_LOCAL      AND
                                            VC.NUM_PED_VTA    = VP.NUM_PED_VTA
                                            )
                                      WHERE  VP.COD_GRUPO_CIA = cCodGrupoCia_in
                                      AND   VP.COD_LOCAL = cCodLocal_in
                                      AND   VP.TIP_COMP_PAGO = cTipComp_in
                                      AND   vc.num_comp_pago = cNumComPago_in
                            )  ;


      IF  vCant <> 0 THEN
              IF ano_actual = ano_fec and mes_actual = mes_fec THEN
                RETURN 'FALSE';

               ELSE
                 RETURN 'TRUE';

               END IF;

      ELSE

          RETURN 'FALSE';

      END IF;

       EXCEPTION
                WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20000, 'No se encontro datos ' );

      END;

  --Descripcion:  Retorna comprobantes del convenio
  --Fecha         Usuario        Comentario.
  --23/04/2014    ERIOS          Creacion
  FUNCTION GET_COMP_CONVENIO(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,cCodCovenio_in IN CHAR)
  RETURN VARCHAR2
  IS
  vRetorno VARCHAR2(100);
  BEGIN
  select nvl((select e.TIP_COMP
         from vta_tip_comp e
        where e.tip_comp = v.tip_comp_benef
        and e.cod_grupo_cia = cCodGrupoCia_in),
       '') || '@' ||
     nvl((select e.TIP_COMP
         from vta_tip_comp e
        where e.tip_comp = v.tip_comp_empresa
        and e.cod_grupo_cia = cCodGrupoCia_in),
       '') INTO vRetorno
  from (select m.cod_tipdoc_cliente,
         (select a.tip_comp_pago
          from mae_tipo_comp_pago_btlmf a
           where m.cod_tipdoc_cliente = a.cod_tipodoc) tip_comp_empresa,
         m.cod_tipdoc_beneficiario,
         (select a.tip_comp_pago
          from mae_tipo_comp_pago_btlmf a
           where m.cod_tipdoc_beneficiario = a.cod_tipodoc) tip_comp_benef
      from mae_convenio m
       where m.cod_convenio = cCodCovenio_in) v;
  RETURN vRetorno;
  END;

 FUNCTION GET_INDICADOR_BENEF_LINEA(VCODCONVENIO_IN IN CHAR)
 RETURN VARCHAR2 IS
  FLGBENEFONLINE VARCHAR2(10);
 BEGIN
  SELECT C.FLG_DATA_RIMAC||'@'||C.FLG_BENEF_ONLINE
        INTO FLGBENEFONLINE
        FROM MAE_CONVENIO C
       WHERE C.COD_CONVENIO = VCODCONVENIO_IN;

    RETURN FLGBENEFONLINE;
 END;

 FUNCTION OBT_DATO_VTA_CLIE(cGrupoCia in char,
                            cCodLocal in char,
                            cNumPedVta in char)
   RETURN FarmaCursor
   AS
   vCURSOR FarmaCursor;

 BEGIN

 OPEN vCURSOR FOR
  SELECT  vp.cod_cli_conv                       || 'Ã' ||--0
          NVL(vp.nom_cli_ped_vta ,' ')          || 'Ã' ||--1
          NVL(vp.dir_cli_ped_vta ,' ')          || 'Ã' ||--2
          NVL(vp.ruc_cli_ped_vta ,' ')          || 'Ã' ||--3
          NVL(VP.PUNTO_LLEGADA,' ')             || 'Ã' ||--4
          VP.tip_ped_vta         || 'Ã' ||  --5
          NVL(vp.obs_ped_vta, ' ') --6   --CAMBIAR POR LA ORDEN DE COMPRA
   FROM   VTA_PEDIDO_VTA_CAB VP
   WHERE  vp.cod_grupo_cia  = cGrupoCia
   AND    vp.cod_local      = cCodLocal
   AND    vp.num_ped_vta    = cNumPedVta;


 END;
/* ************************************************************************* */
  --Descripcion:  Calcula precio tratamiento por convenio
  --Fecha         Usuario        Comentario.
  --18/07/2014    ERIOS          Creacion
  FUNCTION VTA_OBTIENE_PROD_SUG(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                cCodProd_in     IN CHAR,
                                cCantVta_in     IN NUMBER,
                cCodConvenio_in IN CHAR)
     RETURN FarmaCursor
  IS
    curProd FarmaCursor;
    curVta FarmaCursor;
    totalventa  NUMBER(8,3);
    totalventaSug  NUMBER(8,3);
    total  NUMBER(8,3);
    stockUni  NUMBER(6);
    stockFrac NUMBER;
    vValPrecLocal      LGT_PROD_LOCAL.VAL_PREC_VTA%TYPE;
    vValFracSug        LGT_PROD.VAL_FRAC_VTA_SUG%TYPE;
    vPrecVtaSug        LGT_PROD_LOCAL.VAL_PREC_VTA_SUG%TYPE;
    vCodProd           LGT_PROD.COD_PROD%TYPE;
    vDescUnidSug       LGT_PROD.DESC_UNID_VTA_SUG%TYPE;
    vDescProd          LGT_PROD.DESC_PROD%TYPE;
    vPrecVtaListaSug   LGT_PROD_LOCAL.VAL_PREC_LISTA_SUG%TYPE;
    vValFracLocal      LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    cantSug  NUMBER;
    cantSug2  NUMBER;
    indDev  CHAR(1);

    v_nCantSug VTA_PEDIDO_VTA_DET.CANT_ATENDIDA%TYPE;
    v_nTotalSug VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL%TYPE;
    v_nCantSugFrac LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nCantRes LGT_PROD_LOCAL.STK_FISICO%TYPE;
    cTotalVta_in VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL%TYPE;
    v_nCantPorcSug NUMBER;

    v_nFracSug LGT_PROD.VAL_FRAC_VTA_SUG%TYPE;
    v_nFracLocal LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
    v_nPrecSug LGT_PROD_LOCAL.VAL_PREC_VTA_SUG%TYPE;
    v_nStkSug LGT_PROD_LOCAL.STK_FISICO%TYPE;
    v_nPrecVta LGT_PROD_LOCAL.VAL_PREC_VTA%TYPE;
    v_nPorcSug PBL_LOCAL.PORC_MIN_SUG%TYPE;
  BEGIN

    SELECT P.VAL_FRAC_VTA_SUG,L.VAL_FRAC_LOCAL,
           round(pkg_producto.fn_precio('10',
                               l.cod_local,
                               l.cod_prod,
                               '002',
                               cCodConvenio_in)/P.VAL_FRAC_VTA_SUG,3) PRECIO_CONV,
           TRUNC(((L.STK_FISICO)*P.VAL_FRAC_VTA_SUG)/L.VAL_FRAC_LOCAL),
           round(pkg_producto.fn_precio('10',
                               l.cod_local,
                               l.cod_prod,
                               '002',
                               cCodConvenio_in)/L.VAL_FRAC_LOCAL,3) PRECIO_CONC_TRAT,
           LOC.PORC_MIN_SUG
      INTO v_nFracSug,v_nFracLocal,
           v_nPrecSug,
           v_nStkSug,
           v_nPrecVta,
           v_nPorcSug
    FROM LGT_PROD P,
         LGT_PROD_LOCAL L,
         PBL_LOCAL LOC
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND L.COD_PROD = cCodProd_in
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
          AND L.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
          AND L.COD_LOCAL = LOC.COD_LOCAL;

    --ERIOS 03/06/2008 Calcula total de vta fraccionada
    v_nCantSugFrac := FLOOR((cCantVta_in*v_nFracSug)/v_nFracLocal);
    v_nCantRes := cCantVta_in - (v_nCantSugFrac*v_nFracLocal)/v_nFracSug;
    cTotalVta_in := (v_nCantSugFrac*v_nPrecSug)+(v_nCantRes*v_nPrecVta);
    cTotalVta_in := CEIL(cTotalVta_in*10)/10;

    v_nCantSug := CEIL((cCantVta_in*v_nFracSug)/v_nFracLocal);
    v_nTotalSug := v_nCantSug*v_nPrecSug;
    v_nCantPorcSug := ((v_nCantSug*v_nFracLocal)/v_nFracSug)*(v_nPorcSug/100);

      --ERIOS 05.11.2014 Cuando no hay prec_lista_sug, se muestra prec_vta_sug
      OPEN curProd FOR
      SELECT v_nCantSug || 'Ã' ||
             TO_CHAR(v_nTotalSug,'999,9990.000') || 'Ã' ||
             P.DESC_UNID_VTA_SUG || 'Ã' ||
             TRUNC(((L.STK_FISICO)*P.VAL_FRAC_VTA_SUG)/L.VAL_FRAC_LOCAL) || 'Ã' ||
             ' ' || 'Ã' ||
             P.DESC_PROD || 'Ã' ||
             TO_CHAR( v_nPrecSug ,'999,990.000') || 'Ã' ||
             ( (L.STK_FISICO) - ((TRUNC(((L.STK_FISICO)*P.VAL_FRAC_VTA_SUG)/L.VAL_FRAC_LOCAL)*L.VAL_FRAC_LOCAL)/P.VAL_FRAC_VTA_SUG ) ) || 'Ã' ||
             P.VAL_FRAC_VTA_SUG || 'Ã' ||
             TO_CHAR(NVL(L.VAL_PREC_LISTA_SUG,L.VAL_PREC_VTA_SUG),'999,9990.00') || 'Ã' ||
             L.VAL_FRAC_LOCAL || 'Ã' ||
             TO_CHAR( cTotalVta_in,'999,9990.00') || 'Ã' ||
             CASE WHEN --(cTotalVta_in > v_nTotalSug) AND
                       (cCantVta_in >= v_nCantPorcSug) AND
                       (v_nStkSug >= v_nCantSug) THEN 'S'
                  ELSE 'N' END
      FROM LGT_PROD P,
           LGT_PROD_LOCAL L
      WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
            AND L.COD_LOCAL = cCodLocal_in
            AND L.COD_PROD = cCodProd_in
            AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
            AND P.COD_PROD = L.COD_PROD
            --AND cTotalVta_in > v_nTotalSug
            --AND v_nStkSug >= v_nCantSug
            ;
      RETURN curProd;
  END;
  /***************************************************************************/

      -- Author  : LTAVARA
    -- Created : 25/07/2014 06:50:35 p.m.
    -- Purpose : Validar si el convenio puede emitir comprobante electrónico

FUNCTION FN_VALIDAR_CONV_ELECT( cCodConvenio VARCHAR2)
        RETURN VARCHAR2 AS

        v_vIndicador number := 'S';--SI EMITE COMP. ELECTRONICO

    BEGIN
        BEGIN

                 SELECT DECODE(CONV.COD_TIPO_CONVENIO,1,'N','S')
                        INTO v_vIndicador
                 FROM PTOVENTA.MAE_CONVENIO CONV
                 WHERE CONV.COD_CONVENIO=cCodConvenio;


        EXCEPTION
            WHEN OTHERS THEN
                v_vIndicador:= 'S';
        END;
        RETURN v_vIndicador;
    END FN_VALIDAR_CONV_ELECT;

--Autor : Juan Arturo Escate Espichan
--Fecha : 05/11/2008
--Proposito: Esto recalculo el copago variable segun las escalas
--22/08/2014 ERIOS Se agrega parametros
-------------------------------------------------------------------
    FUNCTION FN_CALCULA_COPAGO(
            A_COD_CONVENIO     MAE_CONVENIO.COD_CONVENIO%TYPE,
            A_IMP_TOTAL        FLOAT,
      vPct_beneficiario IN OUT MAE_CONVENIO.PCT_BENEFICIARIO%TYPE,
      vPct_empresa IN OUT MAE_CONVENIO.PCT_EMPRESA%TYPE
      ) RETURN FLOAT
        IS
            CURSOR C_RANGO IS
            select *
            from rel_convenio_escala
            WHERE COD_CONVENIO=A_COD_CONVENIO;
            C_IMP_TOTAL FLOAT;
            V_REG_CONVENIO MAE_CONVENIO%ROWTYPE;
            V_INDICA       FLOAT:=-1;
    BEGIN
         /*SELECT *
         INTO V_REG_CONVENIO
         FROM MAE_CONVENIO
         WHERE COD_CONVENIO = A_COD_CONVENIO;
         IF V_REG_CONVENIO.FLG_POLITICA='1' and  NOT V_REG_CONVENIO.FLG_TIPO_CONVENIO='1' THEN
            RAISE_APPLICATION_ERROR(-20000,'Esto no se aplica para convenios que no sean co-pago variable');
         END IF;*/

         FOR REG IN C_RANGO LOOP
             IF A_IMP_TOTAL >= REG.IMP_MINIMO AND  A_IMP_TOTAL<= REG.IMP_MAXIMO THEN

        IF REG.FLG_PORCENTAJE = '1' THEN
                   C_IMP_TOTAL := ROUND(A_IMP_TOTAL*(REG.IMP_MONTO/100),2);

           vPct_beneficiario   := REG.IMP_MONTO;
                ELSE
                   C_IMP_TOTAL := REG.IMP_MONTO;

           vPct_beneficiario   :=  ROUND((REG.IMP_MONTO*100)/A_IMP_TOTAL,2);
                END IF;

                IF REG.FLG_COASEGURO=1 THEN
                  C_IMP_TOTAL:= ROUND((REG.CTD_COASEGURO+(C_IMP_TOTAL-REG.CTD_COASEGURO)*REG.CTD_COASEGURO/100),2);

           vPct_beneficiario   :=  ROUND((C_IMP_TOTAL*100)/A_IMP_TOTAL,2);
                END IF;

        vPct_empresa        :=  100 - vPct_beneficiario;
        V_INDICA := V_INDICA + 1;
             END IF;
         END LOOP;
         IF C_IMP_TOTAL IS NULL THEN
            C_IMP_TOTAL:= A_IMP_TOTAL;
         END IF;
         IF V_INDICA =-1 THEN
            RETURN V_INDICA;
         ELSE
           RETURN C_IMP_TOTAL;
         END IF;
    END;

    FUNCTION COMP_CREDITO_VALIDA_RAC( cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cNroPedido_in   IN CHAR
                                    )RETURN FarmaCursor
    AS
    vCURSOR FarmaCursor;
    BEGIN
	  --ERIOS 16.09.2016 Se agrega campos para validacion de receta
      OPEN vCURSOR FOR
        SELECT
          CAB.COD_LOCAL COD_LOCAL,
          CAB.NUM_PED_VTA NUM_PED_VTA,
          CAB.COD_CONVENIO COD_CONVENIO,
          NVL(CAB.COD_CLI_CONV, (SELECT COD_CLIENTE FROM MAE_CONVENIO WHERE COD_CONVENIO=CAB.COD_CONVENIO)) COD_CLIENTE,
          TDOC.COD_TIPODOC TIPO_DOC,
          TO_CHAR(SUM(COMP.VAL_NETO_COMP_PAGO),'999,999,999.90') MONTO,
          CASE
            WHEN CAB.TIP_PED_VTA='03' THEN '1'
            WHEN COMP.TIP_CLIEN_CONVENIO='1'
              AND EXISTS(
                  SELECT Z.NUM_PED_VTA
                  FROM VTA_FORMA_PAGO_PEDIDO Z
                  WHERE Z.NUM_PED_VTA = CAB.NUM_PED_VTA
                  AND   Z.COD_FORMA_PAGO = '00050') THEN '1'
            ELSE '2' END VTAFIN,
			(SELECT C.DESCRIPCION_CAMPO
                  FROM PTOVENTA.CON_BTL_MF_PED_VTA C
                  WHERE C.NUM_PED_VTA = CAB.NUM_PED_VTA
                  AND   C.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                  AND   C.COD_LOCAL = CAB.COD_LOCAL
                  AND c.cod_campo = 'D_003') FCH_RECETA ,
            (SELECT C.COD_VALOR_IN
                  FROM PTOVENTA.CON_BTL_MF_PED_VTA C
                  WHERE C.NUM_PED_VTA = CAB.NUM_PED_VTA
                  AND C.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                  AND C.COD_LOCAL = CAB.COD_LOCAL
                  AND C.COD_CAMPO = 'D_005') COD_MEDICO,
            (SELECT C.Cod_Valor_In
                    FROM PTOVENTA.CON_BTL_MF_PED_VTA C
                   WHERE C.NUM_PED_VTA  = CAB.NUM_PED_VTA
                     AND C.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                     AND C.COD_LOCAL  = CAB.COD_LOCAL
                     AND C.COD_CAMPO = 'D_002') COD_LOCAL_RECETA,
             (SELECT C.DESCRIPCION_CAMPO FROM PTOVENTA.CON_BTL_MF_PED_VTA C
                          WHERE C.NUM_PED_VTA = CAB.NUM_PED_VTA
                          AND C.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                          and C.COD_LOCAL = CAB.COD_LOCAL
                          and C.COD_CAMPO ='D_004'
                          AND ROWNUM = 1) CAD_COD_DIAGNOSTICO,
	         (SELECT C.DESCRIPCION_CAMPO
                    FROM PTOVENTA.CON_BTL_MF_PED_VTA C
                   WHERE C.NUM_PED_VTA = CAB.NUM_PED_VTA
                     AND C.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                     AND C.COD_LOCAL = CAB.COD_LOCAL
                     AND C.COD_CAMPO IN ('0000000001','0000000023', '0000000031')
                     AND ROWNUM = 1) NUM_RECETA
          FROM  VTA_COMP_PAGO COMP,
                MAE_TIPO_COMP_PAGO_BTLMF TDOC,
                VTA_PEDIDO_VTA_CAB CAB
          WHERE CAB.COD_GRUPO_CIA = COMP.COD_GRUPO_CIA
          AND   CAB.COD_LOCAL = COMP.COD_LOCAL
          AND   CAB.NUM_PED_VTA = COMP.NUM_PED_VTA
          AND   COMP.TIP_COMP_PAGO = TDOC.TIP_COMP_PAGO
          AND   COMP.IND_COMP_CREDITO = 'S'
          AND   CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND   CAB.COD_LOCAL = cCodLocal_in
          AND   CAB.NUM_PED_VTA = cNroPedido_in
          GROUP BY CAB.COD_LOCAL,
                   CAB.NUM_PED_VTA,
                   CAB.COD_CONVENIO,
                   CAB.COD_CLI_CONV,
                   TDOC.COD_TIPODOC,
                   CAB.TIP_PED_VTA,
                   COMP.TIP_CLIEN_CONVENIO,
                   CAB.NUM_PED_VTA,
                   CAB.COD_GRUPO_CIA
          ORDER BY CAB.NUM_PED_VTA DESC;
        RETURN vCURSOR;
    END;

    /***********************************************************************************/

 -- KMONCADA 17.11.2014 LISTA LOS COMPROBANTES A IMPRIMIR O REIMPRIMIR
  FUNCTION BTLMF_F_CUR_LST_COMP_PAGO_IMPR(cCodGrupoCia_in  IN CHAR,
                                         cCodLocal_in     IN CHAR,
                                         pNroPedido       IN VARCHAR2,
                                         pTipoClienteConv IN CHAR
                                         )
        RETURN FarmaCursor IS
        curComp FarmaCursor;
		v_vTipoDocCliente mae_convenio.cod_tipdoc_cliente%type;
		v_cFlgConvMixto mae_convenio.FLG_CONV_MIXTO%type;
      BEGIN
		--ERIOS 2.4.7 Se imprime datos adicionales en los ticket de Conv. Mixto.
		select NVL(cod_tipdoc_cliente,'TKB'),FLG_CONV_MIXTO
			INTO v_vTipoDocCliente,v_cFlgConvMixto
		from mae_convenio
		WHERE COD_CONVENIO =
		(SELECT COD_CONVENIO FROM VTA_PEDIDO_VTA_CAB
		WHERE NUM_PED_VTA = pNroPedido);

        --ERIOS 2.4.3 Se agrega el porcentaje
        OPEN curComp FOR
          SELECT P.NUM_COMP_PAGO || 'Ã' ||
                 P.SEC_COMP_PAGO || 'Ã' ||
                 P.TIP_COMP_PAGO || 'Ã' ||
                 NVL(TO_CHAR(P.VAL_IGV_COMP_PAGO,'999,990.00'),0)    || 'Ã' ||
                 NVL(TO_CHAR(P.VAL_NETO_COMP_PAGO,'999,990.00'),0)   || 'Ã' ||
                 NVL(TO_CHAR(P.VAL_COPAGO_COMP_PAGO,'999,990.00'),0) || 'Ã' ||
                 NVL(TO_CHAR(P.Val_Igv_Comp_Copago,'999,990.00'),0)  || 'Ã' ||
                 -- KMONCADA 18.11.2014 EN CASO DE ELECTRONICO SE MOSTRARA COMO REFERENCIA EL NUMERO
                 NVL((CASE
                   WHEN P.TIP_COMP_PAGO = COD_TIP_COMP_GUIA THEN
                     (SELECT DECODE(NVL(A.COD_TIP_PROC_PAGO,'0'),'1', FARMA_UTILITY.GET_T_COMPROBANTE_2(A.COD_TIP_PROC_PAGO,A.NUM_COMP_PAGO_E,A.NUM_COMP_PAGO),A.NUM_COMP_PAGO)
                        FROM VTA_COMP_PAGO A
                        WHERE A.COD_GRUPO_CIA=P.COD_GRUPO_CIA
                        AND   A.COD_LOCAL = P.COD_LOCAL
                        AND   A.NUM_PED_VTA = P.NUM_PED_VTA
                        AND   A.NUM_COMP_PAGO = P.NUM_COMP_COPAGO_REF)
                      ELSE
                          NVL(p.num_comp_copago_ref,' ')
                 END),' ') || 'Ã' ||
                 --NVL(p.num_comp_copago_ref,' ') || 'Ã' ||
                 P.TIP_CLIEN_CONVENIO || 'Ã' ||
                 DECODE(v_cFlgConvMixto,'1','1',NVL(btl.flg_imp_dato_adic,'0'))|| 'Ã' ||
                 P.COD_TIPO_CONVENIO || 'Ã' ||
                 TO_CHAR(p.FEC_CREA_COMP_PAGO,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
                 nvl(p.tip_comp_pago_ref,' ')|| 'Ã' ||
                 NVL(TO_CHAR(P.VAL_REDONDEO_COMP_PAGO,'999,990.00'),0) || 'Ã' || --VAL_REDONDEO_COMP_PAGO
                 TO_CHAR(P.PCT_BENEFICIARIO,'999,990.00') || 'Ã' ||
                 TO_CHAR(P.PCT_EMPRESA,'999,990.00') || 'Ã' ||
         decode(v_vTipoDocCliente,btl.cod_tipodoc,'1','0')|| 'Ã' ||
                 NVL(P.NUM_COMP_PAGO_E,0) -- LTAVARA 18.09.2014 SE ENVIA EL NUMERO DE COMPROBANTE ELECTRONICO
            FROM VTA_COMP_PAGO P,
                 mae_tipo_comp_pago_btlmf btl
           WHERE P.NUM_PED_VTA = pNroPedido
             AND btl.tip_comp_pago = p.tip_comp_pago
             AND  P.FECH_IMP_COBRO IS NULL -- rherrera indicador impreso
            ORDER BY  P.TIP_CLIEN_CONVENIO,P.NUM_COMP_PAGO ASC ;
             --AND P.TIP_CLIEN_CONVENIO = pTipoClienteConv;
        RETURN curComp;
    END;

  FUNCTION F_CHAR_EMITE_BENEFICIARIO(cCodConvenio_in      IN CHAR)
  RETURN CHAR IS
    vIndicador CHAR(3);
  BEGIN

    SELECT
      CASE
        -- SI MIXTO TOMARA OTRO CAMPO
        WHEN CONV.FLG_CONV_MIXTO='1' AND CONV.COD_TIPDOC_BENEFICIARIO IN ('BOL','TKB') THEN
          'M'
        -- SI EMITE BOLETA O TKB ASUMIRA COMO BOLETA PARA EL CLIENTE
        WHEN CONV.COD_TIPDOC_BENEFICIARIO IN ('BOL','TKB') THEN
          'BOL'
        -- SI EMITE BOLETA O TKB ASUMIRA COMO FACTURA PARA EL CLIENTE
        WHEN CONV.COD_TIPDOC_BENEFICIARIO IN ('FAC','TKF') THEN
          'FAC'
        ELSE
          'N'
      END
      INTO vIndicador
    FROM MAE_CONVENIO CONV
    WHERE COD_CONVENIO = cCodConvenio_in;

    RETURN vIndicador;
  END;

  FUNCTION F_CHAR_SALTO_LINEAS( vTexto_in            IN VARCHAR2,
                                  vCantCaractLinea1_in  IN INTEGER,
                                  vCantCaractLinea2_in  IN INTEGER,
                                  vSeparador_in         IN VARCHAR2)
  RETURN VARCHAR2 IS
    vTResultado VARCHAR2(2000) := '';
    vContinua BOOLEAN := true;
    vContLinea INTEGER := 0;
    vTexto VARCHAR2(2000);
  BEGIN
    vTexto := vTexto_in;
    WHILE vContinua LOOP
      IF LENGTH(vTexto) <= vCantCaractLinea1_in AND vContLinea = 0 THEN
         vContinua := false;
         vTResultado := vTResultado || vTexto;
      else
        IF LENGTH(vTexto) <= vCantCaractLinea2_in THEN
          vContinua := false;
          vTResultado := vTResultado || vTexto;
        else
          IF vContLinea = 0 THEN
             vTResultado := vTResultado || substr(vTexto,0,vCantCaractLinea1_in) || vSeparador_in;
             vTexto := substr(vTexto,vCantCaractLinea1_in + 1);
          ELSE
            vTResultado := vTResultado || substr(vTexto,0,vCantCaractLinea2_in) || vSeparador_in;
            vTexto := substr(vTexto,vCantCaractLinea2_in + 1);
          END IF;

        END IF;
      END IF;
    END LOOP;

    RETURN vTResultado;
  END;

  FUNCTION F_C_IND_PEDIDO_CONVENIO(pCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                   pCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                   pNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN CHAR IS
    vIndicador CHAR(1);
  BEGIN
    SELECT NVL(IND_CONV_BTL_MF,'N')
    INTO vIndicador
    FROM VTA_PEDIDO_VTA_CAB
    WHERE COD_GRUPO_CIA = pCodGrupoCia_in
    AND COD_LOCAL = pCodLocal_in
    AND NUM_PED_VTA = pNumPedVta_in;
    RETURN vIndicador;
  END;

  PROCEDURE BTLMF_P_INSERT_BENIF_NEW(pCod_convenio_in     IN VARCHAR2,
                                            pCodCliente_in IN CHAR,
                                            pDesCliente_in IN CHAR,
                                            pNomCliente_in IN CHAR,
                                            pFlagActivo_in IN CHAR,
                                            pNum_documentoId_in IN VARCHAR2,
                                            vImpLineaCredito_in IN MAE_CONVENIO.IMP_LINEA_CREDITO%TYPE,
                                            pCodBarra_in     IN REL_CONVENIO_BARRA.COD_BARRA%TYPE
                                        )

   AS

  BEGIN


    insert into CON_BENEFICIARIO
      (cod_convenio,
       cod_cliente,
       DES_CLIENTE,
       FLG_ACTIVO,
       DNI,
       IMP_LINEA_CREDITO_OLD,
       ESTADO,
       FCH_REGISTRA,
       IMP_LINEA_CREDITO)
    values
      (pCod_convenio_in,
       pCodCliente_in,
       pDesCliente_in||', '||pNomCliente_in,
       '1',
       pNum_documentoId_in,
       vImpLineaCredito_in,
       '1',
       SYSDATE,
       vImpLineaCredito_in);

       COMMIT;

    UPDATE REL_CONVENIO_BARRA A
    SET A.COD_CLIENTE = pCodCliente_in
    WHERE A.COD_BARRA = pCodBarra_in
    AND A.COD_CONVENIO = pCod_convenio_in;

    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20999,
                              'ERROR AL INSERTAR NUEVO BENEFICIARIO.' ||
                              SQLERRM);
      ROLLBACK;

  END BTLMF_P_INSERT_BENIF_NEW;

  FUNCTION F_IMPR_VOUCHER(cCodGrupoCia_in IN CHAR,
									cCodCia_in    IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNroPedido_in   IN CHAR,
									cIndCopiaGuia_in IN CHAR DEFAULT 'N',
									cSecCompPago_in IN CHAR DEFAULT NULL
                                    )
    RETURN FarmaCursor IS

    vCodConvenio    VTA_PEDIDO_VTA_CAB.COD_CONVENIO%TYPE;
    vCodCliente     VTA_PEDIDO_VTA_CAB.COD_CLI_CONV%TYPE;
    vNombreConvenio MAE_CONVENIO.DES_CONVENIO%TYPE;

    vNombreBenif    varchar2(300) := '';
    vDni            varchar2(20) := '';

    vTerminal varchar2(300);
    vVendedor varchar2(300);

    vDocBenif varchar(1000) := '';
    vDocBenifTemp varchar(1000) := '';

    vDocEmpre varchar(1000) := '';
    vDocEmpreTemp varchar(1000) := '';


    curDocBenif FarmaCursor;
    curDocEmpre FarmaCursor;

    vTipDocBenif MAE_CONVENIO.Cod_Tipdoc_Beneficiario%TYPE;
    vTipDocEmpre MAE_CONVENIO.COD_TIPDOC_CLIENTE%TYPE;
    vFechaEmision varchar2(400);

    vCodTipoConvenio     VTA_COMP_PAGO.Cod_Tipo_Convenio%TYPE;

    flaImprimeVoucher NUMBER;

    vFlgConvMixto MAE_CONVENIO.FLG_CONV_MIXTO%TYPE;
    vCantCopagoCredito   INTEGER := '0';

    vCantidadCopago INTEGER :=0;
    vMensajePie VARCHAR2(100);

	cursorComprobante    FarmaCursor;
	vIdDoc               VARCHAR2(30);
    vIpPc                VARCHAR2(30);

  CURSOR curDetalleComprobante IS
    SELECT
       -- COD_PROD
       RPAD(DETALLE.COD_PROD, 8, ' ') ||
       -- DESCRIPCION 38 - 20
       CASE
         WHEN LENGTH(DETALLE.DESCRIPCION) + LENGTH(DETALLE.UNID_PRESENT) >= 38 THEN
           SUBSTR(DETALLE.DESCRIPCION, 0, (37 - LENGTH(DETALLE.UNID_PRESENT))) || ' ' || DETALLE.UNID_PRESENT
         ELSE
           DETALLE.DESCRIPCION ||RPAD(' ',(38 - (LENGTH(DETALLE.DESCRIPCION) + LENGTH(DETALLE.UNID_PRESENT))), ' ') || DETALLE.UNID_PRESENT
       END || '  ' ||
       -- CANTIDAD
       LPAD(DETALLE.CANTIDAD, 10, ' ') ITEM

    FROM (
      SELECT NVL(DET.COD_PROD, ' ') COD_PROD,
             SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,
                          '31',
                          'PROM-' || TRIM(PROD.DESC_PROD),
                          NVL(TRIM(PROD.DESC_PROD), ' ')),
                   0,34) DESCRIPCION,
             SUBSTR(DECODE('vTip_Ped_vta', '03', --Si el Tipo de venta es Mayorista
                                DECODE(MOD(ABS(DET.CANT_ATENDIDA),
                                           ABS(DET.VAL_FRAC)),
                                       0,
                                       TRIM(PROD.DESC_UNID_PRESENT),
                                       TRIM(DET.UNID_VTA)), --sin fraccion
                                DECODE(DET.VAL_FRAC,
                                       1,
                                       TRIM(PROD.DESC_UNID_PRESENT),
                                       TRIM(DET.UNID_VTA))),
                    0,20) UNID_PRESENT,
             DECODE('vTip_Ped_vta', '03', --Si el Tipo de venta es Mayorista
                    DECODE(MOD(ABS(DET.CANT_ATENDIDA), ABS(DET.VAL_FRAC)),
                            0,
                            (ABS(DET.CANT_ATENDIDA) / ABS(DET.VAL_FRAC)) || '', --Imprime las cantidad
                            ABS(DET.CANT_ATENDIDA)), --sin fraccion
                   ABS(DET.CANT_ATENDIDA)) CANTIDAD

      FROM VTA_PEDIDO_VTA_DET DET, LGT_PROD PROD, PTOVENTA.VTA_COMP_PAGO CP
      WHERE DET.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   DET.COD_LOCAL     = cCodLocal_in
      AND   DET.NUM_PED_VTA   = cNroPedido_in
      AND  (CASE
              WHEN CP.TIP_CLIEN_CONVENIO = COMP_BENEF_CAMPO AND CP.TIP_COMP_PAGO != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
              WHEN CP.TIP_CLIEN_CONVENIO = COMP_EMPRE_CAMPO AND CP.TIP_COMP_PAGO != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
              ELSE DET.SEC_COMP_PAGO
            END) = cSecCompPago_in

	  AND   CP.SEC_COMP_PAGO = cSecCompPago_in

      AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
      AND   DET.COD_PROD      = PROD.COD_PROD
      AND   DET.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
	  AND DET.COD_LOCAL = CP.COD_LOCAL
	  AND DET.NUM_PED_VTA = CP.NUM_PED_VTA
      ORDER BY DET.SEC_PED_VTA_DET ASC) DETALLE;

    filaCurDetalle curDetalleComprobante%ROWTYPE;

	v_cIndCopiaGuia CHAR(1);
  BEGIN
    --Valida indicador
	IF cIndCopiaGuia_in = 'S' THEN
	BEGIN
		SELECT DESC_CORTA
			INTO v_cIndCopiaGuia
		FROM PTOVENTA.PBL_TAB_GRAL
		WHERE COD_TAB_GRAL = 'IND_COPIA_GUIA'
		AND LLAVE_TAB_GRAL = '01'
		AND EST_TAB_GRAL = 'A'
		--AND COD_CIA = cCodCia_in
		;
	EXCEPTION
		WHEN NO_DATA_FOUND THEN
		    v_cIndCopiaGuia := 'N';
	END;
	IF v_cIndCopiaGuia = 'N' THEN
		GOTO retorno;
	END IF;
	END IF;
    -- Obtenemos el codConvenio y codCliente

     BEGIN
        SELECT PED.COD_CONVENIO, PED.COD_CLI_CONV, TO_CHAR(PED.FEC_PED_VTA,'dd/MM/yyyy hh:mi')
          INTO vCodConvenio, vCodCliente, vFechaEmision
          FROM VTA_PEDIDO_VTA_CAB PED
         WHERE PED.cod_grupo_cia = cCodGrupoCia_in
           and PED.cod_local = cCodLocal_in
           and PED.NUM_PED_VTA = cNroPedido_in;

         EXCEPTION
          WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20000, 'No encontro datos del pedido. ' || cNroPedido_in);
     END;


    -- Obtenemos el nombre del convenio
    BEGIN
      SELECT CONV.DES_CONVENIO,
             Cod_Tipdoc_Beneficiario,
             COD_TIPDOC_CLIENTE,
             NVL(CONV.FLG_CONV_MIXTO,'0')
        INTO vNombreConvenio,vTipDocBenif,vTipDocEmpre,
             vFlgConvMixto
        FROM MAE_CONVENIO CONV
       WHERE CONV.COD_CONVENIO = vCodConvenio;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20000, 'No encontro datos del convenio. ' || cNroPedido_in);
    END;

    -- Obtenemos datos del cliente normal.
    BEGIN
      select tt.descripcion_campo,tt.cod_valor_in
      into   vNombreBenif, vDni
      from   con_btl_mf_ped_vta tt
      where  cod_campo = 'D_000'
      and    tt.cod_grupo_cia = cCodGrupoCia_in
      and    tt.cod_local = cCodLocal_in
      and    tt.num_ped_vta = cNroPedido_in;
    EXCEPTION
       WHEN NO_DATA_FOUND THEN
        vNombreBenif := '';
                vDni := '';
    END;

     --Obtenemos el tipo de convenio
         SELECT distinct P.COD_TIPO_CONVENIO
            INTO vCodTipoConvenio
            FROM VTA_COMP_PAGO P
           WHERE P.num_ped_vta = cNroPedido_in;

     --Obtenemos el nombre de la PC
     SELECT SYS_CONTEXT('USERENV','HOST')
       INTO vterminal
       FROM dual;

     -- KMONCADA 17.11.2014 SE MOSTRARA NOMBRE DEL VENDEDOR DEL PEDIDO
     --Obtenemos el nombre del vendedor.
     BEGIN
     SELECT USUARIO.NOM_USU||' '||USUARIO.APE_PAT||' '||USUARIO.APE_MAT
     INTO   vVendedor
     FROM VTA_PEDIDO_VTA_CAB CAB,
          PBL_USU_LOCAL USUARIO
     WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
     AND   CAB.COD_LOCAL     = cCodLocal_in
     AND   CAB.NUM_PED_VTA   = cNroPedido_in
     AND   CAB.COD_GRUPO_CIA = USUARIO.COD_GRUPO_CIA
     AND   CAB.COD_LOCAL     = USUARIO.COD_LOCAL
     AND   CAB.SEC_USU_LOCAL = USUARIO.SEC_USU_LOCAL;
     EXCEPTION
       WHEN NO_DATA_FOUND THEN
         SELECT USUARIO.NOM_USU || ' ' || USUARIO.APE_PAT || ' ' || USUARIO.APE_MAT
           INTO vVendedor
           FROM VTA_PEDIDO_VTA_CAB CAB, PBL_USU_LOCAL USUARIO
          WHERE CAB.COD_GRUPO_CIA        = cCodGrupoCia_in
            AND CAB.COD_LOCAL            = cCodLocal_in
            AND CAB.NUM_PED_VTA          = cNroPedido_in
            AND CAB.COD_GRUPO_CIA        = USUARIO.COD_GRUPO_CIA
            AND CAB.COD_LOCAL            = USUARIO.COD_LOCAL
            AND CAB.USU_CREA_PED_VTA_CAB = USUARIO.LOGIN_USU;
     END;

     SELECT count(1)
       into flaImprimeVoucher
       FROM MAE_CONVENIO MC
      WHERE MC.COD_TIPDOC_BENEFICIARIO = 'TKB'
        AND MC.COD_TIPDOC_CLIENTE IS NULL
        AND MC.PCT_BENEFICIARIO = 0
        AND MC.PCT_EMPRESA = 100
        AND MC.COD_CONVENIO = vCodConvenio;
    -- Creamos el formato del mensaje

    vMensajePie := 'Acepto pagar al emisor el importe de la atencion anotado en este titulo';

    IF vFlgConvMixto = '1' AND flaImprimeVoucher = 0 THEN
       SELECT COUNT(1)
       INTO   vCantCopagoCredito
       FROM VTA_FORMA_PAGO_PEDIDO A
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND   A.COD_LOCAL     = cCodLocal_in
       AND   A.NUM_PED_VTA   = cNroPedido_in
       AND   A.COD_FORMA_PAGO = '00050';

       IF vCantCopagoCredito > 0 THEN
         flaImprimeVoucher := 1;
         BEGIN
           SELECT tt.descripcion_campo,' '
           INTO   vNombreBenif, vDni
           from   con_btl_mf_ped_vta tt
           where  cod_campo = '0000000011'
           and    tt.cod_grupo_cia = cCodGrupoCia_in
           and    tt.cod_local = cCodLocal_in
           and    tt.num_ped_vta = cNroPedido_in;
         EXCEPTION
           WHEN NO_DATA_FOUND THEN
             vNombreBenif := '';
             vDni := '';
         END;
       END IF;
    END IF;

    IF flaImprimeVoucher = 0 THEN
      SELECT COUNT(DISTINCT(A.TIP_COMP_PAGO))
      INTO vCantidadCopago
      FROM VTA_COMP_PAGO A
      WHERE A.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND A.COD_LOCAL        = cCodLocal_in
      AND A.NUM_PED_VTA      = cNroPedido_in
      AND A.TIP_COMP_PAGO   != COD_TIP_COMP_GUIA
      AND A.PCT_BENEFICIARIO > 0
      AND A.PCT_EMPRESA      > 0;

      IF vCantidadCopago > 1 OR cIndCopiaGuia_in  = 'S' THEN
        flaImprimeVoucher := 1;
        vMensajePie := 'Acepto haber recibido los productos indicados en los comprobantes';
      END IF;

    END IF;

    -- KMONCADA 21.09.2015 HAY CASOS DE QUE NO MUESTRA EL NOMBRE DEL BENEFICIARIO
    IF cIndCopiaGuia_in = 'S' AND (LENGTH(TRIM(vNombreBenif)) = 0 OR vNombreBenif IS NULL) THEN
       BEGIN
         SELECT TT.DESCRIPCION_CAMPO
         INTO   vNombreBenif
         from   CON_BTL_MF_PED_VTA TT
         WHERE  TT.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    TT.COD_LOCAL = cCodLocal_in
         AND    TT.NUM_PED_VTA = cNroPedido_in
         AND    TT.COD_CAMPO = '0000000019';
       EXCEPTION
         WHEN OTHERS THEN
           vNombreBenif := '';
       END;
    END IF;

    IF flaImprimeVoucher > 0 OR cIndCopiaGuia_in  = 'S' THEN

	vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;

    -- LOGO
	IF cIndCopiaGuia_in  <> 'S' THEN
	FARMA_PRINTER.P_AGREGA_LOGO_MARCA(vIdDoc_in => vIdDoc,
                                           vIpPc_in => vIpPc,
                                           vCodGrupoCia => cCodGrupoCia_in,
                                           vCodLocal_in => cCodLocal_in);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
	END IF;
	-- TITULO
	FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => 'VENTA CONVENIO',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                              vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
	FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
    -- TERMINAL
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Terminal:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vterminal,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
	-- VENDEDOR
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Vendedor:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vVendedor,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
	-- CONVENIO
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Convenio:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vNombreConvenio,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
	-- BENEFICIARIO
	IF cIndCopiaGuia_in  = 'S' THEN
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Beneficia.:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vNombreBenif,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
	END IF;
	--FECHA
	FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('F. Emision:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
	FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vFechaEmision,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
    -- CODIGO DE BARRA
	FARMA_PRINTER.P_AGREGA_BARCODE_CODE39(vIdDoc_in => vIdDoc,
                                               vIpPc_in => vIpPc,
                                               vValor_in => cCodLocal_in||cNroPedido_in); --BTLMF_F_CHAR_OBT_COD_BARRA_CON(cCodLocal_in,15));
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);

	IF cIndCopiaGuia_in  <> 'S' THEN

	-- COMPROBANTE
    IF vCodTipoConvenio = '1' OR vCodTipoConvenio = '3' THEN
     -- Obtenemos los numeros de comprobantes del benificiario
     OPEN curDocBenif FOR
         SELECT  T.COD_TIPODOC||' '|| DECODE(P.COD_TIP_PROC_PAGO,'1',FARMA_UTILITY.GET_T_COMPROBANTE_2(P.COD_TIP_PROC_PAGO,P.NUM_COMP_PAGO_E,P.NUM_COMP_PAGO),P.NUM_COMP_PAGO)||
		 ' S/. '||TRIM(TO_CHAR(P.VAL_NETO_COMP_PAGO,'999,999,999.00')) AS  DOCBENIF
            FROM VTA_COMP_PAGO P,
                 MAE_TIPO_COMP_PAGO_BTLMF T
           WHERE P.num_ped_vta = cNroPedido_in
             AND T.TIP_COMP_PAGO = P.TIP_COMP_PAGO
             AND T.COD_TIPODOC = (CASE
                                   WHEN NVL(P.COD_TIP_PROC_PAGO,'0')='1' AND vTipDocBenif='TKB' THEN
                                     'BOL'
                                   ELSE vTipDocBenif
                                  END);
     LOOP
      FETCH curDocBenif
        INTO vDocBenifTemp;
        EXIT WHEN curDocBenif%NOTFOUND;
        vDocBenif := vDocBenifTemp;
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vDocBenif,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
     END LOOP;
     CLOSE curDocBenif;

	--ERIOS 05.12.2014 Truco para que los convenios mixto, no imprima documentos de empresa.
	IF vFlgConvMixto = '0' THEN
     -- Obtenemos los numeros de comprobantes de la empresa
     OPEN curDocEmpre FOR
         SELECT  T.COD_TIPODOC||' '|| DECODE(P.COD_TIP_PROC_PAGO,'1',FARMA_UTILITY.GET_T_COMPROBANTE_2(P.COD_TIP_PROC_PAGO,P.NUM_COMP_PAGO_E,P.NUM_COMP_PAGO),P.NUM_COMP_PAGO)||
		 ' S/. '||TRIM(TO_CHAR(P.VAL_NETO_COMP_PAGO,'999,999,999.00')) AS  DOCBENIF
            FROM VTA_COMP_PAGO P,
                 MAE_TIPO_COMP_PAGO_BTLMF T
           WHERE P.num_ped_vta = cNroPedido_in
             AND T.TIP_COMP_PAGO = P.TIP_COMP_PAGO
             AND T.COD_TIPODOC = (CASE
                                   WHEN NVL(P.COD_TIP_PROC_PAGO,'0')='1' AND vTipDocEmpre='TKB' THEN
                                     'BOL'
                                   ELSE vTipDocEmpre
                                  END);
     LOOP
           FETCH curDocEmpre
            INTO vDocEmpreTemp;
            EXIT WHEN curDocEmpre%NOTFOUND;
            vDocEmpre := vDocEmpreTemp;
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vDocEmpre,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
     END LOOP;
     CLOSE curDocEmpre;

    END IF;
    END IF;

	ELSE
	-- COMPROBANTE COPIA DE GUIA
	-- Obtenemos los numeros de comprobantes de la empresa
     OPEN curDocEmpre FOR
         SELECT  T.COD_TIPODOC||' '||
				DECODE(P.COD_TIP_PROC_PAGO,'1',FARMA_UTILITY.GET_T_COMPROBANTE_2(P.COD_TIP_PROC_PAGO,P.NUM_COMP_PAGO_E,P.NUM_COMP_PAGO),
											P.NUM_COMP_PAGO)||
				' S/. '||TRIM(TO_CHAR(P.VAL_NETO_COMP_PAGO,'999,999,990.00')) AS  DOCBENIF
            FROM VTA_COMP_PAGO P,
                 MAE_TIPO_COMP_PAGO_BTLMF T
           WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
		   AND P.COD_LOCAL = cCodLocal_in
		   AND P.num_ped_vta = cNroPedido_in
		   AND P.SEC_COMP_PAGO = cSecCompPago_in
             AND T.TIP_COMP_PAGO = P.TIP_COMP_PAGO;
     LOOP
           FETCH curDocEmpre
            INTO vDocEmpreTemp;
            EXIT WHEN curDocEmpre%NOTFOUND;
            vDocEmpre := vDocEmpreTemp;
     END LOOP;
     CLOSE curDocEmpre;
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vDocEmpre,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);

	/** DETALLE DE COMPROBANTE **/
    /** ENCABEZADO**/
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc,
                                                      vIpPc_in => vIpPc,
                                                      vCaracter => '-');
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => RPAD('CODIGO',8)|| RPAD('DESCRIPCION',35)|| LPAD('CANT.',10),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc,
                                                      vIpPc_in => vIpPc,
                                                      vCaracter => '-');
      OPEN curDetalleComprobante;
        LOOP
          FETCH curDetalleComprobante INTO filaCurDetalle;
          EXIT WHEN curDetalleComprobante%NOTFOUND;
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => filaCurDetalle.ITEM,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                              vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                              vJustifica_in => 'N');
        END LOOP;
      CLOSE curDetalleComprobante;
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc,
                                                      vIpPc_in => vIpPc,
                                                      vCaracter => '-');
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
	END IF;
	IF cIndCopiaGuia_in  <> 'S' THEN
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
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
	-- BENEFICIARIO
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('Nombre:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vNombreBenif,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
	-- DNI
    FARMA_PRINTER.P_AGREGA_TEXTO_SEGUIDO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => RPAD('DNI:',12),
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => vDni,
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);

    -- MENSAJE
	FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => '*** Documentos Sin Valor Fiscal ***',
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vAlineado_in => FARMA_PRINTER.ALING_CEN);
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                             vIpPc_in => vIpPc);
    END IF;
	-- MENSAJE
	FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => vMensajePie,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vAlineado_in => FARMA_PRINTER.ALING_CEN);

  END IF;
    <<retorno>>
    cursorComprobante := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                                       vIpPc_in => vIpPc);

	RETURN cursorComprobante;
  END;
  
  FUNCTION F_VTA_CONVENIO_GRATUITA(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                   cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                   cNumPedVta_in   IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                                   cCodProd_in     IN LGT_PROD.COD_PROD%TYPE,
                                   cCodConvenio_in IN MAE_CONVENIO.COD_CONVENIO%TYPE
                                   )
  RETURN VARCHAR2 IS
    vRspta VARCHAR2(10) := '-1';
  BEGIN
    BEGIN
      IF TRIM(cNumPedVta_in) IS NOT NULL THEN
        SELECT DECODE(COUNT(1), 0, 'N', 'S')
        INTO vRspta
        FROM VTA_PEDIDO_VTA_CAB CAB,
             VTA_PEDIDO_VTA_DET DET,
             VTA_PROD_CONV_GRATUITO D
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CAB.COD_LOCAL = cCodLocal_in
        AND CAB.NUM_PED_VTA = cNumPedVta_in
        AND CAB.COD_CONVENIO = cCodConvenio_in
        AND CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
        AND CAB.COD_LOCAL = DET.COD_LOCAL
        AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
        AND D.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
        AND D.COD_PROD = DET.COD_PROD
        AND D.COD_CONVENIO = cCodConvenio_in;
      ELSE
        SELECT DECODE(COUNT(1), 0, 'N', 'S')
        INTO vRspta
        FROM VTA_PROD_CONV_GRATUITO A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_PROD = cCodProd_in
        AND A.COD_CONVENIO = cCodConvenio_in;
      END IF;
    EXCEPTION
      WHEN OTHERS THEN
        vRspta := 'N';
    END;

    RETURN vRspta;
  END;
  
  FUNCTION F_GET_FP_CONV_PROD_GRATUITO(cCodGrupoCia_in IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
  		   						   	               cCodLocal_in    IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
									                     cNumPedVta_in 	 IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE)
    RETURN FARMACURSOR IS
    curFP FarmaCursor;
  BEGIN
    OPEN curFP FOR
      SELECT FP.COD_FORMA_PAGO || 'Ã' ||
             FP.DESC_CORTA_FORMA_PAGO || 'Ã' ||
             '0' || 'Ã' ||
             'SOLES' || 'Ã' ||
             '0.04' || 'Ã' ||
             '0.04' || 'Ã' ||
             '01' || 'Ã' ||
             '0.00' || 'Ã' ||
             ' ' || 'Ã' ||
             ' ' || 'Ã' ||
             ' ' || 'Ã' ||
             ' ' || 'Ã' ||
             ' ' || 'Ã' ||
             ' ' || 'Ã' ||
             ' ' || 'Ã' ||
             ' '
      FROM   
             VTA_FORMA_PAGO FP
      WHERE  FP.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    FP.COD_FORMA_PAGO = '00094';
    RETURN curFP;  
  END;

end PTOVENTA_CONV_BTLMF;
/
