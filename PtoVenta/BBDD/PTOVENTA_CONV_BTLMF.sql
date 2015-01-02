--------------------------------------------------------
--  DDL for Package PTOVENTA_CONV_BTLMF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CONV_BTLMF" is

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

  FLG_RETENCION CHAR(1) := '1';

  ---VARIABLES JQUISPE 23.12.2011
  C_ESTADO_ACTIVO CHAR(1) := 'A';

  TYPE TYP_ARR_VARCHAR is table of VARCHAR2(100) index by PLS_INTEGER;

  -----DECLARANDO UN ARRAY VACIO
  W_ARRAY_VACIO TYP_ARR_VARCHAR;
  
    --INI ASOSA - 17/10/2014 - PANHD
  TIPO_PROD_FINAL CONSTANT CHAR(1) := 'F';
  	BAJA_ANUL_VTA_PROD CONSTANT CHAR(3) := '114';
	CONSUMO_MATERIAL CONSTANT CHAR(3) := '115';
  	ALTA_VTA_PROD CONSTANT CHAR(3) := '113';
    --FIN ASOSA - 17/10/2014 - PANHD


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
                                   cDescDetalleForPago_in IN CHAR DEFAULT ' ')

RETURN CHAR ;

  FUNCTION BTLMF_F_CUR_OBT_CONV_PEDIDO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecUsuLocal_in IN CHAR,
                                      pNroPedido      IN VARCHAR2)
    RETURN FarmaCursor;

  FUNCTION BTLMF_F_CHAR_OBT_COD_BARRA_CON(vCodLocal IN CHAR) RETURN VARCHAR2;

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
                                     PIND_AFECTO_IGV    VTA_COMP_PAGO.IND_AFECTO_IGV%TYPE

                                     );
  PROCEDURE BTLMF_P_GRAB_COMP_PAGO_CON_IGV(cCodGrupoCia_in     IN CHAR,
                                           cCodLocal_in        IN CHAR,
                                           cUsuCreaCompPago_in IN CHAR,
                                           cNumPedVta_in       VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                                           V_NUM_PRODUCTOS     MAE_COMP_CONV_LINEAS.NUM_PRODUCTOS%TYPE,
                                           pTIP_COMP_PAGO      VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE,
                                           pSEC_MOV_CAJA       VTA_COMP_PAGO.SEC_MOV_CAJA%TYPE,
                                           cCodNumera_in       IN CHAR,
                                           pTipoClienteConvenio IN CHAR,
                                           pFlgTipoConvenio IN MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE,
                                           pIndAfectaKardex    IN CHAR,
                                           vPCT_BENEFICIARIO VTA_COMP_PAGO.PCT_BENEFICIARIO%TYPE,
                                           vPCT_EMPRESA      VTA_COMP_PAGO.PCT_EMPRESA%TYPE,
                                          vSecImprLocal_in vta_impr_local.sec_impr_local%type,
                                          vIND_CON_IGV_in char,
                                          vIndGeneraUnSoloComproBenif CHAR
                                           );
   /*PROCEDURE BTLMF_P_GRAB_COMP_PAGO_SIN_IGV( cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cUsuCreaCompPago_in IN CHAR,
                                          cNumPedVta_in   VTA_PEDIDO_VTA_DET.NUM_PED_VTA%TYPE,
                                          V_NUM_PRODUCTOS MAE_COMP_CONV_LINEAS.NUM_PRODUCTOS%TYPE,
                                          pTIP_COMP_PAGO  VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE,
                                          pSEC_MOV_CAJA   VTA_COMP_PAGO.SEC_MOV_CAJA%TYPE,
                                          cCodNumera_in 	IN CHAR,
                                          pTipoClienteConvenio IN CHAR,
                                          pFlgTipoConvenio IN MAE_CONVENIO.COD_TIPO_CONVENIO%TYPE,
                                          pIndAfectaKardex CHAR,
                                          vPCT_BENEFICIARIO VTA_COMP_PAGO.PCT_BENEFICIARIO%TYPE,
                                          vPCT_EMPRESA      VTA_COMP_PAGO.PCT_EMPRESA%TYPE

                                         );*/

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
									                    cTipCompPago_in IN NUMBER)

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
                            cIndConIGV_in   in varchar2) RETURN FarmaCursor;


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
									 vNumeroDoc IN VARCHAR2
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

  --Descripcion:  Calcula precio tratamiento por convenio
  --Fecha                 Usuario        Comentario.
  --17/10/2014      ASOSA          Creacion
      PROCEDURE BTLMF_P_UPD_STK_PROD_COMP(cCodGrupoCia_in      IN CHAR,
                                       cCodLocal_in         IN CHAR,
                                      cNumPedVta_in        IN CHAR,
                                     cCodMotKardex_in    IN CHAR,
                                      cTipDocKardex_in    IN CHAR,
                                     cCodNumeraKardex_in IN CHAR,
                                      cUsuModProdLocal_in IN CHAR,
                                     cTipComp_in         IN CHAR,
                                     cNumComp_in         IN CHAR
                                    );
      
end PTOVENTA_CONV_BTLMF;

/
