--------------------------------------------------------
--  DDL for Package PKG_CONVENIO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "BTLPROD"."PKG_CONVENIO" IS

   -- Author  : JLOPEZ
   -- Created : 05/09/2006 PM 02:28:47
   -- Purpose : Procedimientos y funciones para la clase convenio

   TYPE CURSOR_TYPE IS REF CURSOR;

   TYPE TYP_ARR_VARCHAR IS TABLE OF VARCHAR2(1000) INDEX BY PLS_INTEGER;

   FUNCTION FN_LISTA_PETITORIO_CONVENIO(A_COD_CONVENIO CHAR)
      RETURN CURSOR_TYPE;

   FUNCTION FN_LISTA_DOC_VER(A_COD_DOCUMENTO_VERIFICACION CMR.MAE_DOCUMENTO_VERIFICACION.COD_DOCUMENTO_VERIFICACION%TYPE DEFAULT NULL)
      RETURN CURSOR_TYPE;

   FUNCTION FN_LISTA_CLASE(A_COD_CLASE_CONVENIO CMR.MAE_CLASE_CONVENIO.COD_CLASE_CONVENIO%TYPE DEFAULT NULL)
      RETURN CURSOR_TYPE;

   PROCEDURE SP_GRABA_CLASE(A_COD_CLASE_CONVENIO CMR.MAE_CLASE_CONVENIO.COD_CLASE_CONVENIO%TYPE DEFAULT NULL,
                            A_DES_CLASE_CONVENIO CMR.MAE_CLASE_CONVENIO.DES_CLASE_CONVENIO%TYPE,
                            A_FLG_ACTIVO         CMR.MAE_CLASE_CONVENIO.FLG_ACTIVO%TYPE,
                            A_COD_USUARIO        CMR.MAE_CLASE_CONVENIO.COD_USUARIO%TYPE);

   FUNCTION FN_LISTA(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE DEFAULT NULL,
                     A_ORDEN        NUMBER DEFAULT 0) RETURN CURSOR_TYPE;

   FUNCTION FN_LISTA_CLIENTE_CLASE(A_COD_CLASE_CONVENIO CMR.MAE_CONVENIO.COD_CLASE_CONVENIO%TYPE)
      RETURN CURSOR_TYPE;

   PROCEDURE SP_GRABA
   /*1*/
   (A_CIA CMR.MAE_CONVENIO.CIA%TYPE,
    /*2*/
    A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
    /*3*/
    A_COD_CLIENTE CMR.MAE_CONVENIO.COD_CLIENTE%TYPE,
    /*4*/
    A_COD_CLASE_CONVENIO CMR.MAE_CONVENIO.COD_CLASE_CONVENIO%TYPE,
    /*5*/
    A_COD_PETITORIO CMR.MAE_CONVENIO.COD_PETITORIO%TYPE,
    /*6*/
    A_DES_CONVENIO CMR.MAE_CONVENIO.DES_CONVENIO%TYPE,
    /*7*/
    A_FLG_TIPO_CONVENIO CMR.MAE_CONVENIO.FLG_TIPO_CONVENIO%TYPE,
    /*8*/
    A_PCT_BENEFICIARIO CMR.MAE_CONVENIO.PCT_BENEFICIARIO%TYPE,
    /*9*/
    A_PCT_EMPRESA CMR.MAE_CONVENIO.PCT_EMPRESA%TYPE,
    /*10*/
    A_IMP_LINEA_CREDITO CMR.MAE_CONVENIO.IMP_LINEA_CREDITO%TYPE,
    /*11*/
    A_FLG_POLITICA CMR.MAE_CONVENIO.FLG_POLITICA%TYPE,
    /*12*/
    A_FLG_PETITORIO CMR.MAE_CONVENIO.FLG_PETITORIO%TYPE,
    /*13*/
    A_FLG_PERIODO_VALIDEZ CMR.MAE_CONVENIO.FLG_PERIODO_VALIDEZ%TYPE,
    /*14*/
    A_FCH_INICIO CMR.MAE_CONVENIO.FCH_INICIO%TYPE,
    /*15*/
    A_FCH_FIN CMR.MAE_CONVENIO.FCH_FIN%TYPE,
    /*16*/
    A_FLG_RENOVACION_AUTO CMR.MAE_CONVENIO.FLG_RENOVACION_AUTO%TYPE,
    /*17*/
    A_FLG_ATENCION_LOCAL CMR.MAE_CONVENIO.FLG_ATENCION_LOCAL%TYPE,
    /*18*/
    A_FLG_ATENCION_DELIVERY CMR.MAE_CONVENIO.FLG_ATENCION_DELIVERY%TYPE,
    /*19*/
    A_IMP_MINIMO_DELIVERY CMR.MAE_CONVENIO.IMP_MINIMO_DELIVERY%TYPE,
    /*20*/
    A_FLG_TIPO_PERIODO CMR.MAE_CONVENIO.FLG_TIPO_PERIODO%TYPE,
    /*21*/
    A_DIA_CORTE CMR.MAE_CONVENIO.DIA_CORTE%TYPE,
    /*22*/
    A_CTD_CANCELACION CMR.MAE_CONVENIO.CTD_CANCELACION%TYPE,
    /*23*/
    A_FLG_BENEFICIARIOS CMR.MAE_CONVENIO.FLG_BENEFICIARIOS%TYPE,
    /*24*/
    A_COD_TIPDOC_CLIENTE CMR.MAE_CONVENIO.COD_TIPDOC_CLIENTE%TYPE,
    /*25*/
    A_COD_TIPDOC_BENEFICIARIO CMR.MAE_CONVENIO.COD_TIPDOC_BENEFICIARIO%TYPE,
    /*26*/
    A_FLG_NOTACREDITO CMR.MAE_CONVENIO.FLG_NOTACREDITO%TYPE,
    /*27*/
    A_FLG_FACTURA_LOCAL CMR.MAE_CONVENIO.FLG_FACTURA_LOCAL%TYPE,
    /*28*/
    A_IMP_MINIMO CMR.MAE_CONVENIO.IMP_MINIMO%TYPE,
    /*29*/
    A_IMP_PRIMERA_COMPRA CMR.MAE_CONVENIO.IMP_PRIMERA_COMPRA%TYPE,
    /*30*/
    A_FLG_TARJETA CMR.MAE_CONVENIO.FLG_TARJETA%TYPE,
    /*31*/
    A_FLG_TIPO_VALOR CMR.MAE_CONVENIO.FLG_TIPO_VALOR%TYPE,
    /*32*/
    A_IMP_VALOR CMR.MAE_CONVENIO.IMP_VALOR%TYPE,
    /*33*/
    A_FLG_TIPO_PRECIO CMR.MAE_CONVENIO.FLG_TIPO_PRECIO%TYPE,
    /*34*/
    A_FLG_TIPO_PRECIO_LOCAL CMR.MAE_CONVENIO.FLG_TIPO_PRECIO_LOCAL%TYPE,
    /*35*/
    A_COD_LOCAL_REF CMR.MAE_CONVENIO.COD_LOCAL_REF%TYPE,
    /*36*/
    A_DES_OBSERVACION CMR.MAE_CONVENIO.DES_OBSERVACION%TYPE,
    /*37*/
    A_FLG_ATENCION_TODOS_LOCALES CMR.MAE_CONVENIO.FLG_ATENCION_TODOS_LOCALES%TYPE,
    /*38*/
    A_FLG_COBRO_TODOS_LOCALES CMR.MAE_CONVENIO.FLG_COBRO_TODOS_LOCALES%TYPE,
    /*39*/
    A_FLG_ACTIVO CMR.MAE_CONVENIO.FLG_ACTIVO%TYPE,
    /*40*/
    A_COD_USUARIO CMR.MAE_CONVENIO.COD_USUARIO%TYPE,
    /*41*/
    A_FLG_TIPO_PAGO CMR.MAE_CONVENIO.FLG_TIPO_PAGO%TYPE,
    -----------------------------------------------------------------------------------------------
    /*42*/
    A_CAD_COD_VERIF CHAR, /*43*/
    A_CAD_BTL       CHAR,
    /*44*/
    A_CAD_FORMA_PAGO      CHAR, /*45*/
    A_CAD_FORMA_PAGO_HIJO CHAR,
    /*46*/
    A_CAD_TIPO_VENTA        CHAR, /*47*/
    A_CAD_FLG_RETENCION_DOC CHAR,
    ------------------------------------------------------------------------------------------------
    /*48*/
    A_FLG_MEDICO     CHAR, /*49*/
    A_CAD_COD_MEDICO CHAR,
    /*50*/
    A_NUM_DOC_FLG_KDX       CHAR, /*51*/
    A_CAD_PCT_BENEF_X_LOCAL CHAR,
    /*52*/
    A_CAD_PCT_EMPRE_X_LOCAL CHAR, /*53*/
    A_FLG_REPARTIDOR        CHAR,
    /*54*/
    A_CAD_TIPO_DATO_ADIC CHAR,
    /*54*/
    A_CAD_PETITORIO CHAR,
    /*55*/
    A_FLG_VALIDA_LINCRE_BENEF CHAR,
    /*57*/
    A_CAD_LOCAL_RECETA CHAR,
    /*58*/
    A_FLG_RECETA       CHAR,
    A_COD_CONVENIO_GLM CNV.M_CONVENIOS.COD_CONVENIO%TYPE DEFAULT NULL,
    
    A_CAD_ESCALA1      VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA2      VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA3      VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA4      VARCHAR2 DEFAULT NULL,
    A_CAD_ESCALA5      VARCHAR2 DEFAULT NULL,
    A_FLG_IMPRIME_IMP  VARCHAR2 DEFAULT NULL,
    A_FLG_PRECIO_MENOR VARCHAR2 DEFAULT NULL,
    A_FLG_PRECIO_DEDUCIBLE VARCHAR2 DEFAULT NULL,
    A_DES_IMPRESION        VARCHAR2 DEFAULT NULL,
    A_FCH_IMPRESION_INI    VARCHAR2 DEFAULT NULL,
    A_FCH_IMPRESION_FIN    VARCHAR2 DEFAULT NULL,
    A_FLG_IMPRESION        VARCHAR2 DEFAULT 1,
    --PARA CLUB BTL
    A_NUM_NIVELES	             CMR.MAE_CONVENIO.NUM_NIVELES%TYPE DEFAULT NULL,
    A_NUM_MAX_BENEF	           CMR.MAE_CONVENIO.NUM_MAX_BENEF%TYPE DEFAULT NULL,
    A_MTO_MAX_LINEA_COMPRA	   CMR.MAE_CONVENIO.MTO_MAX_LINEA_COMPRA%TYPE DEFAULT NULL,
    A_FLG_PER_ANU_DOC	         CMR.MAE_CONVENIO.FLG_PER_ANU_DOC%TYPE DEFAULT NULL,
    A_FLG_PER_NOT_CRE	         CMR.MAE_CONVENIO.FLG_PER_NOT_CRE%TYPE DEFAULT NULL,
    A_FLG_AFILIACION_ACT	     CMR.MAE_CONVENIO.FLG_AFILIACION_ACT%TYPE DEFAULT NULL,
    A_CAD_NIVEL                VARCHAR2 DEFAULT NULL,
    A_CAD_imp_minimo           VARCHAR2 DEFAULT NULL,
    A_CAD_imp_maximo           VARCHAR2 DEFAULT NULL,
    A_CAD_flg_porcentaje       VARCHAR2 DEFAULT NULL,
    A_CAD_imp_monto            VARCHAR2 DEFAULT NULL,
    A_FLG_ESCALA_UNICA         INTEGER DEFAULT 0,
    --PARA MAYOR CONTROL DEL CONVENIO 
    A_COD_EJECUTIVO            VARCHAR2 DEFAULT NULL,
    A_COD_TRAMA                VARCHAR2 DEFAULT NULL,
    A_DESTINATARIO_TRAMA       VARCHAR2 DEFAULT NULL,
    -- INDICADOR CARGAR ARCHIVO PLAN VITAL 28/01/2010 CRUEDA
    A_FLG_PLAN_VITAL           VARCHAR2 DEFAULT NULL,
    -- MIGUEL LAGUNA PARAMETROS AFILIACION MAS SALUD 23/03/2010 MLAGUNA
    A_MAX_UND_PRODUCTO          INTEGER DEFAULT NULL,
    A_COD_CON_COMISION          CMR.MAE_CONVENIO.COD_CONCEPTO%TYPE DEFAULT NULL,
    A_MTO_LINEA_CRED_BASE       CMR.MAE_CONVENIO.MTO_LINEA_CRED_BASE%TYPE DEFAULT 0,
    A_FLG_ING_BENEFICIARIO      CMR.MAE_CONVENIO.FLG_ING_BENEFICIARIO%TYPE DEFAULT 0,
    ---CCIEZA PARAMENTROS PARA EL ENVIO DE EMAIL DE PRODUCTOS AGOTADOS  25/08/2010
     A_CAD_ENVIO1               VARCHAR2 DEFAULT NULL,
     A_CAD_ENVIO2               VARCHAR2 DEFAULT NULL,
     A_CAD_ENVIO3               VARCHAR2 DEFAULT NULL,
     A_CAD_ENVIO4               VARCHAR2 DEFAULT NULL,
     A_CAD_ESCALA6              VARCHAR2 DEFAULT NULL,
     A_CAD_ESCALA7              VARCHAR2 DEFAULT NULL,
    A_cod_cliente_sap_bolsa     VARCHAR2 DEFAULT NULL
    );

   PROCEDURE SP_ACTUALIZA_DATOS_CONTROL(
                                        /*1*/A_CIA CMR.MAE_CONVENIO.CIA%TYPE,
                                        /*2*/
                                        A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
                                        /*3*/
                                        A_IMP_LINEA_CREDITO CMR.MAE_CONVENIO.IMP_LINEA_CREDITO%TYPE,
                                        /*4*/
                                        A_FLG_PETITORIO CMR.MAE_CONVENIO.FLG_PETITORIO%TYPE,
                                        /*5*/
                                        A_FLG_RENOVACION_AUTO CMR.MAE_CONVENIO.FLG_RENOVACION_AUTO%TYPE,
                                        /*6*/
                                        A_FLG_TIPO_PERIODO CMR.MAE_CONVENIO.FLG_TIPO_PERIODO%TYPE,
                                        /*7*/
                                        A_DIA_CORTE CMR.MAE_CONVENIO.DIA_CORTE%TYPE,
                                        /*8*/
                                        A_CTD_CANCELACION CMR.MAE_CONVENIO.CTD_CANCELACION%TYPE,
                                        /*9*/
                                        A_COD_TIPDOC_CLIENTE CMR.MAE_CONVENIO.COD_TIPDOC_CLIENTE%TYPE,
                                        /*10*/
                                        A_COD_TIPDOC_BENEFICIARIO CMR.MAE_CONVENIO.COD_TIPDOC_BENEFICIARIO%TYPE,
                                        /*11*/
                                        A_FLG_NOTACREDITO CMR.MAE_CONVENIO.FLG_NOTACREDITO%TYPE,
                                        /*12*/
                                        A_FLG_FACTURA_LOCAL CMR.MAE_CONVENIO.FLG_FACTURA_LOCAL%TYPE,
                                        /*13*/
                                        A_IMP_MINIMO CMR.MAE_CONVENIO.IMP_MINIMO%TYPE,
                                        /*14*/
                                        A_IMP_PRIMERA_COMPRA CMR.MAE_CONVENIO.IMP_PRIMERA_COMPRA%TYPE,
                                        /*15*/
                                        A_FLG_TARJETA CMR.MAE_CONVENIO.FLG_TARJETA%TYPE,
                                        /*16*/
                                        A_FLG_TIPO_VALOR CMR.MAE_CONVENIO.FLG_TIPO_VALOR%TYPE,
                                        /*17*/
                                        A_FLG_TIPO_PRECIO CMR.MAE_CONVENIO.FLG_TIPO_PRECIO%TYPE,
                                        /*18*/
                                        A_FLG_TIPO_PRECIO_LOCAL CMR.MAE_CONVENIO.FLG_TIPO_PRECIO_LOCAL%TYPE,
                                        /*19*/
                                        A_COD_LOCAL_REF CMR.MAE_CONVENIO.COD_LOCAL_REF%TYPE,
                                        /*20*/
                                        A_COD_USUARIO CMR.MAE_CONVENIO.COD_USUARIO%TYPE,
                                        /*21*/
                                        A_NUM_DOC_FLG_KDX CHAR);

   FUNCTION FN_LISTA_DOC_VERIF(A_COD_CONVENIO CHAR) RETURN CURSOR_TYPE;

   ------------------------------------------------------------------------------------------
   ------------------------------------------------------------------------------------------
   -- 2 --
   FUNCTION FN_LISTA_BENEFICIARIO(A_CIA          CMR.MAE_BENEFICIARIO.CIA%TYPE,
                                  A_COD_CONVENIO CMR.MAE_BENEFICIARIO.COD_CONVENIO%TYPE,
                                  A_BENEFICIARIO CHAR,
                                  A_FLG_CODIGO   CHAR,
                                  A_FLG_ESTADO   VARCHAR2 DEFAULT NULL) RETURN CURSOR_TYPE;
   -- 1 --  
   FUNCTION FN_LISTA_BENEFICIARIO(A_CIA          CMR.MAE_BENEFICIARIO.CIA%TYPE,
                                  A_COD_CONVENIO CMR.MAE_BENEFICIARIO.COD_CONVENIO%TYPE,
                                  A_BENEFICIARIO CHAR,
                                  A_FLG_ESTADO   VARCHAR2 DEFAULT NULL) RETURN CURSOR_TYPE;
   ------------------------------------------------------------------------------------------
   ------------------------------------------------------------------------------------------
   FUNCTION FN_LISTA_REPARTIDOR(A_COD_CONVENIO CHAR) RETURN CURSOR_TYPE;

   FUNCTION FN_LISTA_MEDICO(A_COD_CONVENIO CHAR) RETURN CURSOR_TYPE;

   FUNCTION FN_LISTA_TIPO_CAMPO(A_COD_CONVENIO CHAR) RETURN CURSOR_TYPE;

   FUNCTION FN_LISTA_X_LOCAL(A_CIA       BTLPROD.REL_CONVENIO_LOCAL.CIA%TYPE,
                             A_COD_LOCAL BTLPROD.REL_CONVENIO_LOCAL.COD_LOCAL%TYPE,
                             A_CRITERIO  CHAR DEFAULT NULL) RETURN CURSOR_TYPE;

   FUNCTION FN_LISTA_DOCUMENTOS(A_CIA       BTLPROD.REL_DOCUMENTO_LOCAL.CIA%TYPE,
                                A_COD_LOCAL BTLPROD.REL_DOCUMENTO_LOCAL.COD_LOCAL%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LISTA_PETITORIO(A_COD_PETITORIO BTLPROD.CAB_PETITORIO.COD_PETITORIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LISTA_MEDICO RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   --- LISTA DE PARAMETROS PARA UN CONVENIO NUEVO ---    
   FUNCTION FN_LISTA_DOCUM_VERIF(A_CIA          BTLPROD.REL_CONVENIO_DOCUM_VERIF.CIA%TYPE,
                                 A_COD_CONVENIO BTLPROD.REL_CONVENIO_DOCUM_VERIF.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LISTA_CONVENIO_FORMAPAGOS(A_CIA          BTLPROD.REL_FORMA_PAGO_CONVENIO.CIA%TYPE,
                                         A_COD_CONVENIO BTLPROD.REL_FORMA_PAGO_CONVENIO.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LISTA_CONVENIO_LOCALES(A_CIA          BTLPROD.REL_CONVENIO_LOCAL.CIA%TYPE,
                                      A_COD_CONVENIO BTLPROD.REL_CONVENIO_LOCAL.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LISTA_MEDICO_CONVENIO(A_CIA          BTLPROD.REL_CONVENIO_MEDICO.CIA%TYPE,
                                     A_COD_CONVENIO BTLPROD.REL_CONVENIO_MEDICO.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   --- LISTA DE PARAMETROS PARA UN CONVENIO A EDITAR ---    
   FUNCTION FN_LST_EDICION_DOCUM_VERIF(A_CIA          BTLPROD.REL_CONVENIO_DOCUM_VERIF.CIA%TYPE,
                                       A_COD_CONVENIO BTLPROD.REL_CONVENIO_DOCUM_VERIF.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LST_EDICION_FORMAPAGOS_CNV(A_CIA          BTLPROD.REL_FORMA_PAGO_CONVENIO.CIA%TYPE,
                                          A_COD_CONVENIO BTLPROD.REL_FORMA_PAGO_CONVENIO.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LST_EDICION_LOCALES_CNV(A_CIA          BTLPROD.REL_CONVENIO_LOCAL.CIA%TYPE,
                                       A_COD_CONVENIO BTLPROD.REL_CONVENIO_LOCAL.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LST_EDICION_MEDICO_CNV(A_CIA          BTLPROD.REL_CONVENIO_MEDICO.CIA%TYPE,
                                      A_COD_CONVENIO BTLPROD.REL_CONVENIO_MEDICO.COD_CONVENIO%TYPE)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   FUNCTION FN_LST_VALORES_MOV_KARDEX RETURN CMR.PKG_UTIL.CURSOR_TYPE;

   PROCEDURE SP_GRABA_BENEFICIARIO_CNV(A_CIA                         CMR.MAE_BENEFICIARIO.CIA%TYPE,
                                       A_COD_CONVENIO                CHAR,
                                       A_COD_BENEFICIARIO            CHAR,
                                       A_IMP_LINEA_CREDITO           CHAR,
                                       A_FLG_CAMB_TEMP_LIN_CRE       CHAR,
                                       A_IMP_LINEA_CREDITO_ORI       CHAR,
                                       A_COD_USUARIO_AUTORIZA_CAMBIO CHAR,
                                       A_DES_OBSERVACION             CHAR,
                                       A_COD_REFERENCIA              CHAR,
                                       A_FLG_ACTIVO                  CHAR,
                                       A_COD_USUARIO                 CHAR,
                                       A_FCH_INI_NVA_LINCRED         CMR.MAE_BENEFICIARIO.FCH_INI_NVA_LINCRED%TYPE,
                                       A_FCH_FIN_NVA_LINCRED         CMR.MAE_BENEFICIARIO.FCH_FIN_NVA_LINCRED%TYPE,
                                       A_IMP_CONSUMO                 CMR.MAE_BENEFICIARIO.IMP_CONSUMO%TYPE,
                                       A_COD_ZONAL                   CMR.MAE_BENEFICIARIO.COD_ZONAL%TYPE DEFAULT NULL);
   /*
   Autor: Pablo Herrera
   Fecha: 18/01/07
   Motivo: Esta funcion devuelve el codigo del convenio BTL para efectos de operaciones en el 
       sistema financiero contable
   */
   FUNCTION FN_LISTA_CONVENIO_BTL RETURN VARCHAR2;

   -- Autor => Cristhian Rueda
   -- Fecha => 03/04/2007
   --- LISTA DATOS ADICIONALES DEL CONVENIO ---
   FUNCTION FN_LISTA_DATOS_ADICIONALES(A_DATO_CAMPO CHAR) RETURN SYS_REFCURSOR;

   FUNCTION FN_MAX_COD RETURN VARCHAR2;

   ---- GRABACION DE DATOS ADICIONALES ----
   PROCEDURE SP_GRABA_DATOS_ADICIONALES(A_COD_CAMPO    CMR.MAE_TIPO_CAMPO.COD_TIPO_CAMPO%TYPE,
                                        A_DES_CAMPO    CMR.MAE_TIPO_CAMPO.DES_TIPO_CAMPO%TYPE,
                                        A_FLG_ESTADO   CMR.MAE_TIPO_CAMPO.FLG_ACTIVO%TYPE,
                                        A_FLG_EDITABLE CMR.MAE_TIPO_CAMPO.FLG_EDITABLE%TYPE,
                                        A_COD_USUARIO  CMR.MAE_TIPO_CAMPO.COD_USUARIO%TYPE);

   FUNCTION FN_LISTA_DATOS_ADIC_CNV(A_COD_CONVENIO BTLPROD.REL_CONVENIO_TIPO_CAMPO.COD_CONVENIO%TYPE)
      RETURN SYS_REFCURSOR;

   FUNCTION FN_LST_EDICION_DATOS_ADIC(A_COD_CONVENIO BTLPROD.REL_CONVENIO_TIPO_CAMPO.COD_CONVENIO%TYPE)
      RETURN SYS_REFCURSOR;

   FUNCTION FN_LISTA_DIAGNOSTICO(A_DES_DIAGNOSTICO CMR.MAE_DIAGNOSTICO.DES_DIAGNOSTICO%TYPE DEFAULT NULL)
      RETURN CURSOR_TYPE;
   FUNCTION SP_LISTA_PACIENTE_UM( A_CRITERIO CHAR,
                                  A_COD_lOCAL VARCHAR2 DEFAULT NULL
   ) RETURN CURSOR_TYPE;
   PROCEDURE SP_GRABA_RECETA(A_NUM_DOCUMENTO       BTLPROD.CAB_RECETA.NUM_DOCUMENTO%TYPE,
                             A_COD_TIPO_DOCUMENTO  BTLPROD.CAB_RECETA.COD_TIPO_DOCUMENTO%TYPE,
                             A_COD_LOCAL           BTLPROD.CAB_RECETA.COD_LOCAL%TYPE,
                             A_FCH_RECETA          BTLPROD.CAB_RECETA.FCH_RECETA%TYPE,
                             A_COD_MEDICO          BTLPROD.CAB_RECETA.COD_MEDICO%TYPE,
                             A_COD_USUARIO         BTLPROD.CAB_RECETA.COD_USUARIO%TYPE,
                             A_COD_LOCAL_EMISION   BTLPROD.CAB_RECETA.COD_LOCAL_EMISION%TYPE,
                             A_CAD_COD_DIAGNOSTICO CHAR,
                             A_COD_CLIENTE         CMR.MAE_BENEFICIARIO.COD_CLIENTE%TYPE DEFAULT NULL,
                             A_NUM_RECETA          BTLPROD.CAB_RECETA.NUM_RECETA%TYPE DEFAULT NULL,
                             A_COD_CONVENIO        BTLPROD.CAB_DOCUMENTO.COD_CONVENIO%TYPE DEFAULT NULL);
   FUNCTION FN_LISTA_CAB_RECETA(A_COD_TIPO_DOCUMENTO BTLPROD.CAB_DOCUMENTO.COD_TIPO_DOCUMENTO%TYPE,
                                A_NUM_DOCUMENTO      BTLPROD.CAB_DOCUMENTO.NUM_DOCUMENTO%TYPE)
      RETURN CURSOR_TYPE;
   FUNCTION FN_LISTA_DET_RECETA(A_COD_TIPO_DOCUMENTO BTLPROD.CAB_DOCUMENTO.COD_TIPO_DOCUMENTO%TYPE,
                                A_NUM_DOCUMENTO      BTLPROD.CAB_DOCUMENTO.NUM_DOCUMENTO%TYPE)
      RETURN CURSOR_TYPE;

   ---- GRABACION DE DOCUMENTO DE VERIFICACION ----
   ----        HECHO EL 15/05/2007

   PROCEDURE SP_GRABA_DOCUM_VERIF(A_COD_DOCUMENTO_VERIFICACION CMR.MAE_DOCUMENTO_VERIFICACION.COD_DOCUMENTO_VERIFICACION%TYPE,
                                  A_DES_DOCUMENTO_VERIFICACION CMR.MAE_DOCUMENTO_VERIFICACION.DES_DOCUMENTO_VERIFICACION%TYPE,
                                  A_DES_ABREVIATURA            CMR.MAE_DOCUMENTO_VERIFICACION.DES_ABREVIATURA%TYPE,
                                  A_FLG_ESTADO                 CMR.MAE_DOCUMENTO_VERIFICACION.FLG_ESTADO%TYPE,
                                  A_COD_USUARIO                CMR.MAE_DOCUMENTO_VERIFICACION.COD_USUARIO%TYPE);

   --- Para saber si venta es por Convenio y se vende por Delivery 
   --- Determinar si es un 100% asume todo la empresa
   FUNCTION FN_VTA_CNV_X_DLV_EMPRESA_ASUME(A_CIA          CMR.MAE_CONVENIO.CIA%TYPE,
                                           A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
                                           A_FLG_ACTIVO   CMR.MAE_CONVENIO.FLG_ACTIVO%TYPE, -- 1  ACTIVO
                                           A_FLG_TIPO_CNV CMR.MAE_CONVENIO.FLG_TIPO_CONVENIO%TYPE -- 2  100% ASUME LA EMPRESA
                                           ) RETURN FLOAT;

   FUNCTION FN_LISTA_ZONAL(A_COD_ZONAL CMR.MAE_ZONAL.DES_ZONAL%TYPE DEFAULT NULL)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE;
   PROCEDURE SP_GRABA_ZONAL(A_COD_ZONAL   CMR.MAE_ZONAL.COD_ZONAL%TYPE DEFAULT NULL,
                            A_DES_ZONAL   CMR.MAE_ZONAL.DES_ZONAL%TYPE,
                            A_FLG_ACTIVO  CMR.MAE_ZONAL.FLG_ACTIVO%TYPE,
                            A_COD_USUARIO CMR.MAE_ZONAL.COD_USUARIO%TYPE);

   FUNCTION FN_LISTA_C_PETITORIO RETURN CURSOR_TYPE;

   FUNCTION FN_LISTA_X_CLIENTE(A_COD_CLIENTE CMR.MAE_CLIENTE.COD_CLIENTE%TYPE)
      RETURN SYS_REFCURSOR;

   FUNCTION FN_LISTA_DIAGNOSTICO_X_CIE10(A_CADENA VARCHAR2) RETURN CURSOR_TYPE;

   --Autor : Juan Arturo Escate Espichan
   --Fecha : 05/11/2008
   --Proposito: Esto recalculo el copago variable segun las escalas
   -------------------------------------------------------------------
   FUNCTION FN_CALCULA_COPAGO(
            A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
            A_IMP_TOTAL        FLOAT) RETURN FLOAT;

  --Autor : Carlos CIeza
  --Fecha : 26/11/2008
  --Proposito: Devuelve las Escalas que existen segun el convenio
  -------------------------------------------------------------------
   FUNCTION FN_LISTA_ESCALAS(A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN CURSOR_TYPE;
   
   FUNCTION FN_FLG_IMPRIME_IMP(A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN VARCHAR2;
   
   FUNCTION FN_FLG_PRECIO_MENOR(A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN VARCHAR2;
   
   FUNCTION FN_FLG_PRECIO_DEDUCIBLE(A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN VARCHAR2;

--Autor      : Juan Arturo Escate Espichan
--Fecha      : 09/02/2009
--Proposito  : Verifica si va a la tabla de RIMAC
FUNCTION FN_FLG_RIMAC(
                      A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE
                      ) RETURN INTEGER;   
 FUNCTION SP_LISTA_PACIENTE_UM( A_CRITERIO CHAR,
                                A_COD_lOCAL VARCHAR2 DEFAULT NULL ,
                                A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE) RETURN CURSOR_TYPE;                      
FUNCTION FN_FLG_DATA_RIMAC(
                      A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE
                      ) RETURN INTEGER;
                                
FUNCTION  FN_TRANS_PAC_RIMAC(
       A_CIA                                  CMR.MAE_CLIENTE.CIA%TYPE,
       A_COD_USUARIO                          CMR.MAE_CLIENTE.COD_USUARIO%TYPE,
       A_COD_CONVENIO                         CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
       A_DATO                                 VARCHAR2,
       A_NOMBRE                               VARCHAR2
       ) RETURN CURSOR_TYPE;                                

FUNCTION FN_MENSAJE_TICKET(
                           A_COD_CONVENIO  VARCHAR2
                           ) RETURN VARCHAR2;       
FUNCTION FN_TIPO_CONVENIO(
         A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE
)RETURN INTEGER;

---------------------------------------------------------------------------------
--Autor : Juan Arturo Escate Espichan
--Fecha : 20/07/2009
--Motivo: Recargamos la funcion para validar posteriormente en la grabacion
   FUNCTION FN_LOTE(A_COD_MODALIDAD_VENTA BTLPROD.REL_MODALIDAD_LOTE.COD_MODALIDAD_VENTA%TYPE,
                    A_COD_CONVENIO        BTLPROD.REL_MODALIDAD_LOTE.COD_CONVENIO%TYPE DEFAULT NULL,
                    A_MTO_TOTAL           FLOAT DEFAULT NULL)
      RETURN VARCHAR2 ;

FUNCTION FN_REG_LOTE(A_COD_MODALIDAD_VENTA BTLPROD.REL_MODALIDAD_LOTE.COD_MODALIDAD_VENTA%TYPE,
                    A_COD_CONVENIO        BTLPROD.REL_MODALIDAD_LOTE.COD_CONVENIO%TYPE DEFAULT NULL,
                    A_MTO_TOTAL           FLOAT DEFAULT NULL)
      RETURN CMR.PKG_UTIL.CURSOR_TYPE ;
PROCEDURE SP_GRABA_ESCALA_NIVEL(
          A_COD_CONVENIO        BTLPROD.REL_ESCALA_NIVEL.COD_CONVENIO%TYPE,
          A_CAD_NIVEL           VARCHAR2,
          A_CAD_imp_minimo      VARCHAR2,
          A_CAD_imp_maximo      VARCHAR2,
          A_CAD_flg_porcentaje  VARCHAR2,
          A_CAD_imp_monto       VARCHAR2,
          A_FLG_UNICO_NIVEL     INTEGER,
          A_NUM_NIVELES         INTEGER
)                              ;

FUNCTION FN_LISTA_ESCALAXNIVEL(
                A_COD_CONVENIO        BTLPROD.REL_MODALIDAD_LOTE.COD_CONVENIO%TYPE 
                )
      RETURN CMR.PKG_UTIL.CURSOR_TYPE ;

FUNCTION FN_DEV_CON_AFILIACION( A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)
      RETURN INTEGER;

   FUNCTION FN_LISTA_TRAMA(A_FLG_NINGUNO   INTEGER DEFAULT 0) RETURN CURSOR_TYPE ;
   FUNCTION FN_LISTA_EJECUTIVO(A_FLG_NINGUNO   INTEGER DEFAULT 0)   RETURN CURSOR_TYPE;
   FUNCTION FN_CNV_TRAMA RETURN CURSOR_TYPE ;

   FUNCTION FN_EVAL_RECETA(
                           A_COD_CONVENIO  CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
                           A_FCH_INICIO    VARCHAR2,
                           A_FCH_FIN       VARCHAR2,
                           A_FLG_OBSERVACION  VARCHAR2
                           )
     RETURN CURSOR_TYPE ;

   PROCEDURE SP_ACTULIZA_RECETA(A_NUM_RECETA               BTLPROD.CAB_RECETA.NUM_RECETA%TYPE,
                                A_NUM_RECETA_NEW           BTLPROD.CAB_RECETA.NUM_RECETA%TYPE,
                                A_NUM_DOCUMENTO            BTLPROD.CAB_RECETA.NUM_DOCUMENTO%TYPE,
                                A_COD_TIPO_DOCUMENTO       BTLPROD.CAB_RECETA.COD_TIPO_DOCUMENTO%TYPE,
                                A_COD_LOCAL                BTLPROD.CAB_RECETA.COD_LOCAL%TYPE,
                                A_cod_usuario              BTLPROD.CAB_RECETA.COD_USUARIO_ACTUALIZA%TYPE
                                );

   FUNCTION FN_VALIDA_CONVENIO_BTL(
       A_COD_CONVENIO       CMR.MAE_CONVENIO.COD_CONVENIO%TYPE,
       A_RUC_EMPRESA        VARCHAR2,
       A_COD_CLIENTE        CMR.MAE_CLIENTE.COD_CLIENTE%TYPE,
       A_COD_USUARIO        NUEVO.MAE_USUARIO_BTL.COD_USUARIO%TYPE
   ) RETURN VARCHAR2;
   
   --LISTA LAS CONCEPTOS DE COMISION
   --MLAGUNA 
   --23/03/2010
    FUNCTION FN_COMISION_VENTA(A_COD_CIA NUEVO.MAE_EMPRESA.COD_EMPRESA%TYPE)
    RETURN CURSOR_TYPE;
    
    
    
   --LISTA LOS BENEFICIARIOS QUE RECIBIRAN EL PAGO DE COMISIONES POR VENTA
   --MLAGUNA 
   --05/04/2010
   FUNCTION FN_CARGA_PAGO_COMISION (A_ANIO BTLPROD.AUX_PAGO_COMISION_CNV.ANIO%TYPE,
                                    A_MES BTLPROD.AUX_PAGO_COMISION_CNV.MES%TYPE) 
   RETURN CURSOR_TYPE; 
   
   --ARCHIVO PARA PAGO DE COMISIONES
   --MLAGUNA 
   --FECHA 07/04/2010
   FUNCTION FN_ARCHIVO_COMISION    (A_ANIO BTLPROD.AUX_PAGO_COMISION_CNV.ANIO%TYPE,
                                    A_MES BTLPROD.AUX_PAGO_COMISION_CNV.MES%TYPE) 
   RETURN CURSOR_TYPE;
   
   FUNCTION FN_TOTAL_PAGO_COMISION (A_ANIO BTLPROD.AUX_PAGO_COMISION_CNV.ANIO%TYPE,
                                    A_MES BTLPROD.AUX_PAGO_COMISION_CNV.MES%TYPE) 
   RETURN CURSOR_TYPE;

   FUNCTION FN_DEV_REFERENCIAS (A_CIA                BTLPROD.AUX_SEC_VENTA.CIA%TYPE,
                                A_COD_LOCAL          NUEVO.MAE_LOCAL.COD_LOCAL%TYPE,
                                A_COD_TIPO_DOCUMENTO BTLPROD.AUX_SEC_VENTA.COD_TIPO_DOCUMENTO%TYPE,
                                A_NUM_DOCUMENTO      BTLPROD.AUX_SEC_VENTA.NUM_DOCUMENTO%TYPE) RETURN CURSOR_TYPE;
   
   FUNCTION FN_DEV_LINEA_BASE(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)RETURN FLOAT;   

   FUNCTION FN_LISTA_ADD_BENEF RETURN SYS_REFCURSOR;

   /* AUTOR :  CARLOS CIEZA   CCIEZA
      MOTIVO: PROYECTO CONVENIO LISTA LOS PETITORIOS MARCADOS O NO PARA EL ENVIO DEL EMAIL DE LOS AGOTADOS 
      FECHA:  25/08/2010
   */
   FUNCTION FN_LISTA_ENVIO_RUPTURAS (A_CIA              NUEVO.MAE_EMPRESA.COD_EMPRESA%TYPE,                               
                                     A_COD_CONVENIO     CMR.MAE_CONVENIO.COD_CONVENIO%TYPE
                                    ) RETURN SYS_REFCURSOR;

   FUNCTION FN_LISTAS_FORMATOS_ENVIOS  (
               A_COD_FORMATO CMR.MAE_FORMATO_ENVIO.COD_FORMATO%TYPE DEFAULT NULL,
               A_TEXTO       VARCHAR2 DEFAULT NULL
                  )RETURN SYS_REFCURSOR;

 FUNCTION FN_DEV_lISTA_DA(A_COD_TIPO_CAMPO CMR.MAE_TIPO_CAMPO.COD_TIPO_CAMPO%TYPE) RETURN INTEGER;
  FUNCTION FN_lISTA_DATOS_DA(A_COD_TIPO_CAMPO CMR.MAE_TIPO_CAMPO.COD_TIPO_CAMPO%TYPE) RETURN SYS_REFCURSOR;
  FUNCTION FN_LISTA_BARRA(A_COD_BARRA BTLPROD.REL_CONVENIO_BARRA.COD_BARRA%TYPE) RETURN SYS_REFCURSOR;
  FUNCTION FN_LISTA_CONVENIO_COMPETENCIA(A_COD_CONVENIO CMR.MAE_CONVENIO.COD_CONVENIO%TYPE)RETURN INTEGER;
END PKG_CONVENIO;

/
