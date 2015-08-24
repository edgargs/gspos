CREATE OR REPLACE PACKAGE "FARMA_EPOS" as

  TYPE DocumentoCursor IS REF CURSOR;

  
  -- Author  : LTAVARA
  -- Created : 14/07/2014 06:37:35 p.m.
  -- Purpose : GENERAR DOCUMENTO ELECTRONICO
  DOC_BENEFICIARIO       char(1) := '1';
  DOC_EMPRESA            char(1) := '2';
  COD_GRUPO_TIP_MOT_ANUL VARCHAR2(2) := '14';
  
 TYPE FarmaCursor IS REF CURSOR;
 
 COD_FORMA_PAGO_SOLES    VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE := '00001';
 COD_FORMA_PAGO_DOLARES  VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE := '00002';
 COD_FORMA_PAGO_CONVENIO VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE := '00080';
 COD_FORMA_PAGO_CREDITO  VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE := '00050';
 COD_FORMA_PAGO_PUNTOS   VTA_FORMA_PAGO.COD_FORMA_PAGO%TYPE := '00090';
 
 TIPO_VTA_INSTITUCIONAL VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE := '03';
 TIPO_VTA_DELIVERY      VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE := '02';
 TIPO_VTA_MESON         VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE := '01';

 COD_TIP_COMP_BOLETA    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '01';
 COD_TIP_COMP_FACTURA   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '02';
 COD_TIP_COMP_GUIA      VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '03';
 COD_TIP_COMP_NOTA_CRED VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '04';
 COD_TIP_COMP_TICKET    VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '05';
 COD_TIP_COMP_TICKET_FA VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE := '06';

 -- KMONCADA 02.12.2014 INDICADOR DE VALIDACION DE COLA DE IMPRESION DE PRE-IMPRESOS
 COD_IND_VALIDA_IMPRESION PBL_TAB_GRAL.ID_TAB_GRAL%TYPE :='619';  
 
 COD_IND_EMI_CUPON NUMBER(3) := 679;                    --ASOSA - 10/04/2015 - NECUPYAYAYAYA

  COD_CONVENIO_COMPETENCIA MAE_CONVENIO.COD_CONVENIO%TYPE := '0000000834';
 
  ESTADO_SIN_TRX_ORBIS VTA_PEDIDO_VTA_CAB.EST_TRX_ORBIS%TYPE := 'N';
  V_SEPARADOR varchar2(10) := '�';
  V_BOLD_I varchar2(10) := '�i�';
  V_BOLD_F varchar2(10) := '�f�';     
  V_SEPARADOR_2 varchar2(10) := '�';
  V_BOLD_I_2 varchar2(10) := '�i�';
  V_BOLD_F_2 varchar2(10) := '�f�';
  
  EST_ORBIS_ENVIADO       CHAR(1) := 'E';
  EST_ORBIS_PENDIENTE     CHAR(1) := 'P';
  EST_ORBIS_DESCARTADO    CHAR(1) := 'D';
  EST_ORBIS_NO_APLICA     CHAR(1) := 'N';
  
  TAB_GRAL_PTOS_REDIME     INTEGER := 482;
  
 
  FUNCTION F_VAR_TRM_GET_CAB(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2;

  FUNCTION F_VAR_TRM_GET_DOC(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0')
  
   RETURN VARCHAR2;
  FUNCTION F_VAR_TRM_GET_NOTAS(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2;
  FUNCTION F_VAR_TRM_GET_DET(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0')
    --RETURN VARCHAR2;
    RETURN FARMACURSOR;

  FUNCTION F_VAR_TRM_GET_RG(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0')
    RETURN VARCHAR2;

  FUNCTION F_VAR_TRM_GET_IG(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2;
  FUNCTION F_VAR_TRM_GET_REF(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2;
                        
FUNCTION F_VAR_TRM_GET_PP(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2 ;  
  
  FUNCTION F_VAR_TRM_GET_MSJ_BF(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0')
    RETURN VARCHAR2;
  FUNCTION F_VAR_TRM_GET_MSJ_AF(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2;

 
  FUNCTION F_VAR_COD_SERV_PED(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   in char,
                              cSecComPago_in  in char) return varchar2;

  FUNCTION F_CUR_SENT_COMP(cCodGrupoCia_in IN CHAR,
                           cCodCia_in      IN CHAR,
                           cCodLocal_in    IN CHAR,
                           cTipCompE_in    IN CHAR,
                           cSerieCompE_in  IN CHAR,
                           vNumCompE_in    IN VARCHAR2)
    RETURN DocumentoCursor;

  FUNCTION F_VAR_DSC_PROD_SERV(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR) return varchar2;
  -- Author  : LTAVARA
  -- Created : 15/10/2014 09:30:35 p.m.
  -- Proposito : Obtener equivalente1                           
  FUNCTION F_VAR_GET_EQV(cCodMaestro_in IN VARCHAR2, cValor1 IN VARCHAR2)
    return varchar2;

   FUNCTION F_VAR_GET_FORMAT_COMP_E(NUMERO_COMP IN VARCHAR2) RETURN VARCHAR2;    
   
FUNCTION F_NUM_INST_TRM_MSJ_NUM_E(
                            cGrupoCia VARCHAR2,
                            cCodLocal VARCHAR2,
                            cNumPedidoVta VARCHAR2,
                            cSecCompPago VARCHAR2,
                            cMsjNumE VARCHAR2)
        RETURN NUMBER;   

   
FUNCTION F_NUM_INST_TRM_MSJ_CONF_E(
                            cGrupoCia VARCHAR2,
                            cCodLocal VARCHAR2,
                            cNumPedidoVta VARCHAR2,
                            cSecCompPago VARCHAR2,
                            cMsjConfE VARCHAR2)
        RETURN NUMBER;  

FUNCTION F_VAR_GET_NUM_COMP_E(cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2
                                  )
   RETURN VARCHAR2;

 FUNCTION    F_VAR_IS_ACT_FUNC_E(cGrupoCia VARCHAR2,
                                  cCodCia VARCHAR2,
                                  cCodLocal VARCHAR2
                                  )
   RETURN VARCHAR2;

 FUNCTION    F_VAR_UPD_FLAG_COMP_E(cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedVta VARCHAR2
                                  )
   RETURN VARCHAR2 ; 
   

  FUNCTION  F_VAR_GET_TIP_PROC_CPAGO(cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedVta VARCHAR2,
                                  cNumCompPago VARCHAR2
                                  )
   RETURN VARCHAR2 ;
                 

      procedure FN_MODIFICAR_DOCUMENTO_E(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cNumCompPagoE VARCHAR2,
                                  pdf417 VARCHAR2,
                                  pTipCompPago varchar2);
                                  
              procedure FN_MODIFICAR_DOC_NUM_E(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cNumCompPagoE VARCHAR2,
                                  pdf417 VARCHAR2,
                                  pTipCompPago varchar2);
procedure sp_upd_comp_pago_e(p_cod_grupo_cia      varchar2,
                                                       p_cod_local          varchar2,
                                                       p_num_ped_vta        varchar2,
                                                       p_sec_com_pago       varchar2,
                                                       p_tip_clien_convenio varchar2 default null
                                                       ) ;

FUNCTION FN_INDICADOR_ELECTRONICO(cCodGrupoCia VARCHAR2,cCodLocal VARCHAR2,cNumPedidoVta VARCHAR2, cSecComPago VARCHAR2)
   RETURN CHAR;
   
   FUNCTION FN_CONTAR_DOCELECTRONICO (cCodGrupoCia VARCHAR2,cCodLocal VARCHAR2,cNumPedidoVta VARCHAR2 )
   RETURN INTEGER;

 -- KMONCADA
 -- IMPRESION DE COMPROBANTE ELECTRONICO
 FUNCTION IMP_COMP_ELECT ( vCodGrupoCia_in    IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                           vCodLocal_in       IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                           vNumPedVta_in      IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                           vSecCompPago_in    IN VTA_PEDIDO_VTA_CAB.SEC_COMP_PAGO%TYPE,
                           vVersion_in        IN REL_APLICACION_VERSION.NUM_VERSION%TYPE,
                           vReimpresion       IN CHAR DEFAULT 'N',
                           valorAhorro_in     IN NUMBER, --ASOSA - 24/03/2015 - PTOSYAYAYAYA - SE DEFINIO QUE ESTUVIERA EN JAVA X ESO ES PARAMETRO
                           cDocTarjetaPtos_in IN VARCHAR2 DEFAULT NULL
                                           )
 RETURN FARMACURSOR; 

FUNCTION GET_MARCA_LOCAL(vCodGrupoCia_in PBL_LOCAL.COD_GRUPO_CIA%TYPE,
                         vCodLocal_in    PBL_LOCAL.COD_LOCAL%TYPE)
RETURN PBL_LOCAL.COD_MARCA%TYPE;

FUNCTION GET_TIP_COMP_SUNAT(vTipCompPago_Fv varchar2)
RETURN varchar2;


 FUNCTION F_VAR_TRM_GET_MSJ_PERSONALIZA(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0'
                                ) RETURN VARCHAR2;
                                
  -- KMONCADA 02.12.2014
  -- MENSAJE DE CONTIGENCIA                               
  FUNCTION F_MSJ_CONTIGENCIA( vCodGrupoCia_in IN CHAR,
                              vCodLocal_in    IN CHAR,
                              vNumPedVta_in   IN CHAR,
                              vCodTipoDoc_in  IN CHAR) 
  RETURN FARMACURSOR;                               
  
  -- KMONCADA 02.12.2014
  -- VALIDARA SI HAY DOCUMENTOS PENDIENTES DE IMPRESION PARA EL CASO DE CONTIGENCIA
  FUNCTION VALIDA_IMPRESION_PENDIENTE (cCodGrupoCia_in    IN CHAR,
                                     cCodLocal_in         IN CHAR,
                                     cNumPedVta_in        IN CHAR, 
                                     cSecCompPago_in      IN CHAR,
                                     cCodTipoDocumento_in IN CHAR)
  RETURN FARMACURSOR;
  
  -- ACTUALIZA FLAG DE ELECTRONICO DEL COMPROBANTE A 0, PARA INDICAR QUE SE GENERO COMO PRE-IMPRESO
  -- KMONCADA 02.12.2014
  PROCEDURE COMP_PAGO_CAMBIA_FLAG_E(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumPedVta_in    IN CHAR,
                                  cSecCompPago_in  IN CHAR);

  -- REEMPLAZA CARACTERES ENTER Y SALTOS DE LINEA EN EL TEXTO
  -- KMONCADA 2015.03.19
  FUNCTION REEMPLAZA_CARACTERES (cTexto IN VARCHAR2)
    RETURN VARCHAR2;

  -- Author  : ASOSA
  -- Created : 15/05/2015
  -- Description : Insertar un texto mas de ahorro.
/*  PROCEDURE INS_AHORRO_INMED(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumPedVta_in    IN CHAR,
                                  cSecCompPago_in  IN CHAR);*/
                                  
  -- Author  : ASOSA
  -- Created : 19/05/2015
  -- Description : Determinar si se trata de una nota de credito.
  FUNCTION FN_IS_NOTA_CREDITO(cCodGrupoCia_in    IN CHAR,
                                     cCodLocal_in         IN CHAR,
                                     cNumPedVta_in        IN CHAR)
  RETURN CHAR;
  
  -- Author  : ASOSA
  -- Created : 21/05/2015
  -- Description : D.
  FUNCTION FN_VAL_DSCTO_AHORRO_INMED(cCodGrupoCia_in    IN CHAR,
                                     cCodLocal_in         IN CHAR,
                                     cNumPedVta_in        IN CHAR,
                                    cSecCompPago_in  IN CHAR)
  RETURN VARCHAR2;
END FARMA_EPOS;
/
CREATE OR REPLACE PACKAGE BODY "FARMA_EPOS" as

  /***************************************************************************/  
  FUNCTION F_VAR_TRM_GET_CAB(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2 AS
    v_vCabecera VARCHAR2(3276) := NULL;
  
  BEGIN
  
    BEGIN
    
      SELECT 'EN|' || -- ENCABEZADO
              CASE COMP.TIP_COMP_PAGO
                WHEN '02' THEN
                 '01'
                WHEN '06' THEN
                 '01'
                WHEN '01' THEN
                 '03'
                WHEN '05' THEN
                 '03'
                WHEN '04' THEN
                 '07'
                WHEN '99' THEN
                 (CASE (PED.TIP_COMP_PAGO)
                   WHEN '02' THEN
                    '01'
                   WHEN '06' THEN
                    '01'
                   WHEN '01' THEN
                    '03'
                   WHEN '05' THEN
                    '03'
                 END)
              END || '|' || -- 01 Tipo de Documento
              NVL2(TRIM(COMP.NUM_COMP_PAGO_E),
                   TRIM(SUBSTR(COMP.NUM_COMP_PAGO_E, 1, 4) || '-' ||
                        SUBSTR(COMP.NUM_COMP_PAGO_E, -8)),
                   '0') || '|' || -- 02 Serie y Correlativo Documento
              NVL(F_VAR_GET_EQV(COD_GRUPO_TIP_MOT_ANUL,
                                COMP.COD_TIP_MOTIVO_NOTA_E),
                  '') || '|' || -- 03 Tipo de Nota de cr�dito/Nota de D�bito (Motivo de NC/ND)
              COMP.NUM_COMP_PAGO_EREF || '|' || -- 04 Factura/Boleta  que referencia la Nota de Cr�dito/Nota de D�bito
              DECODE(COMP.NUM_COMP_PAGO_EREF,
                     null,
                     '',
                     REEMPLAZA_CARACTERES(TRIM(PED.MOTIVO_ANULACION))) || '|' || -- 05 Sustento
              NVL(TO_CHAR(COMP.FEC_CREA_COMP_PAGO, 'yyyy-MM-dd'), ' ') || '|' || -- 06 Fecha Emision
              DECODE(COMP.COD_TIP_MONEDA, '01', 'PEN', '02', 'USD', 'PEN') || '|' || -- 07 Tipo de Moneda
              CIA.NUM_RUC_CIA || -- 08 RUC Emisor
              '|6|' || -- 09 Tipo de Identificaci�n Emisor
              --CIA.NOM_CIA --LTAVARA 07.10.2015
              REEMPLAZA_CARACTERES((select m.nom_marca from pbl_marca_grupo_cia m
              where m.cod_grupo_cia=local.cod_grupo_cia and m.cod_marca=local.cod_marca))|| '|' || -- 10 Nombre Comercial Emisor
              REEMPLAZA_CARACTERES(CIA.RAZ_SOC_CIA) || '|' || -- 11 Razon Social Emisor
              UBI.UBDEP || UBI.UBPRV || UBI.UBDIS || '|' || -- 12 Codigo UBIGEO Emisor
              CIA.DIR_CIA || '|' || -- 13 Direccion Emisor
              UBI.NODEP || '|' || -- 14 Departamento Emisor
              UBI.NOPRV || '|' || -- 15 Provincia Emisor (Comuna)
              UBI.NODIS || '|' || -- 16 Distrito Emisor
              NVL(TRIM(COMP.NUM_DOC_IMPR), 0) || '|' || -- 17 RUC Receptor
              COMP.COD_TIP_IDENT_RECEP_E || '|' || -- 18 Tipo de Identificacion Receptor
              REEMPLAZA_CARACTERES(TRIM(NVL(TRIM(COMP.NOM_IMPR_COMP), 'SIN NOMBRE'))) || '|' || -- 19 Razon Social Receptor
              REEMPLAZA_CARACTERES(COMP.DIREC_IMPR_COMP) || '|' || -- 20 Direccion Receptor
             --DECODE(COMP.TIP_COMP_PAGO,'04',TRIM(TO_CHAR(NVL(COMP.VAL_NETO_COMP_PAGO*-1,0),'999999990.00')),TRIM(TO_CHAR(NVL(COMP.VAL_NETO_COMP_PAGO,0),'999999990.00')))||'|'||-- 21 Monto Neto
              TRIM(TO_CHAR(NVL(ABS(COMP.VAL_TOTAL_E), 0), '999999990.00')) || '|' || -- 25 Monto Total
              TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_IGV_E), 0), '999999990.00')) || '|' || -- 22 Monto Impuesto
             /* TRIM(TO_CHAR(NVL(ABS((CASE -- SOLO PARA CONVENIO COPAGO PARA LA EMPRESA EL MONTO TOTAL AL 100% -- LTAVARA 17.10.2014
                                              WHEN NVL(COMP.TIP_CLIEN_CONVENIO, 0) = DOC_EMPRESA AND
                                                   nvl(COMP.PCT_BENEFICIARIO, 0) > 0 AND
                                                   nvl(COMP.PCT_EMPRESA, 0) > 0 THEN
                                               DECODE(COMP.TOTAL_GRAV_E,0,0,(COMP.TOTAL_GRAV_E - COMP.VAL_COPAGO_E)*0.18)
                                              ELSE
                                               COMP.TOTAL_IGV_E
                                            END)), 0), '999999990.00')) || '|' || -- 22 Monto Impuesto*/
              TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_DESC_E), 0), '999999990.00')) || '|' || -- 23 Monto Descuentos
              0 || '|' || -- 24 Monto Recargos
              DECODE(COMP.TIP_COMP_PAGO,
                     '04',
                     TRIM(TO_CHAR(NVL((COMP.VAL_NETO_COMP_PAGO +COMP.VAL_REDONDEO_COMP_PAGO) * -1, 0),
                                  '999999990.00')),
                     TRIM(TO_CHAR(NVL(trunc(COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO/*+ (CASE -- SOLO PARA CONVENIO COPAGO PARA LA EMPRESA EL MONTO TOTAL AL 100% -- LTAVARA 17.10.2014
                                              WHEN NVL(COMP.TIP_CLIEN_CONVENIO, 0) = DOC_EMPRESA AND
                                                   nvl(COMP.PCT_BENEFICIARIO, 0) > 0 AND
                                                   nvl(COMP.PCT_EMPRESA, 0) > 0 THEN
                                               ABS(COMP.VAL_COPAGO_COMP_PAGO)
                                              ELSE
                                               0
                                            END)*/,
                                            2),
                                      0),
                                  '999999990.00'))) || '|' || -- -- 25 Monto Total
              '' || '|' || -- 26 C�digos de otros conceptos tributarios o comerciales recomendados
              '' || '|' || -- 27 Total de Valor Venta Neto
              NVL(TRIM(COMP.NUM_DOC_IMPR), 0) || '|' || -- 28 n�mero de documento de identidad del adquirente o usuario
              COMP.COD_TIP_IDENT_RECEP_E || '|' || -- 29 Tipo de documento de identidad del adquirente o usuario
              'PE' || '|' || -- 30 C�digo Pa�s Emisor
              '' || '|' || -- 31 Urbanizaci�n Emisor
             /*CASE PED.TIP_PED_VTA WHEN '02' --PEDIDO DELIVERY
                   THEN
             ( SELECT
                   LOCAL.UBDEP||LOCAL.UBPRV||LOCAL.UBDIS||'|'||-- 32 Direcci�n del Punto de Partida, C�digo de Ubigeo
                   LOCAL.DIREC_LOCAL||'|'||-- 33 Direcci�n del Punto de Partida, Direcci�n completa y detallada
                   ''||'|'||-- 34 Direcci�n del Punto de Partida, Urbanizaci�n
                   UBI_LOCAL.NOPRV||'|'||-- 35 Direcci�n del Punto de Partida, Provincia
                   UBI_LOCAL.NODEP||'|'||-- 36 Direcci�n del Punto de Partida, Departamento
                   UBI_LOCAL.NODIS||'|'||-- 37 Direcci�n del Punto de Partida, Distrito
                   'PE'||'|'||-- 38 Direcci�n del Punto de Partida, C�digo de Pa�s
                   DIR.UBDEP||DIR.UBPRV||DIR.UBDIS||'|'||-- 39 Direcci�n del Punto de Llegada, C�digo de Ubigeo
                   (SELECT COM.DIR_ENVIO FROM  TMP_CE_CAMPOS_COMANDA COM WHERE COM.NUM_PED_VTA=PED.NUM_PEDIDO_DELIVERY)||'|'||-- 40 Direcci�n del Punto de Llegada, Direcci�n completa y detallada
                   DIR.COD_URBANIZACION||'|'||-- 41 Direcci�n del Punto de Llegada, Urbanizaci�n
                   UBI_DIR.NOPRV||'|'||-- 42 Direcci�n del Punto de Llegada, Provincia
                   UBI_DIR.NODEP||'|'||-- 43 Direcci�n del Punto de Llegada, Departamento
                   UBI_DIR.NODIS||'|'||-- 44 Direcci�n del Punto de Llegada, Distrito
                   'PE'||'|'||-- 45 Direcci�n del Punto de Llegada, C�digo de Pa�s
                   ''||'|'||-- 46 Placa Veh�culo
                   ''||'|'||-- 47 N� constancia de inscripci�n del veh�culo o certificado de habilitacion vehicular
                   ''||'|'||-- 48 Marca Veh�culo
                   ''||'|'||-- 49 N� de licencia de conducir
                   ''||'|'||-- 50 Ruc transportista
                   ''||'|'||-- 51 Ruc transportista -Tipo Documento
                   ''||'|'-- 52 Raz�n social del transportista
               FROM VTA_MAE_DIR DIR, UBIGEO UBI_DIR
               WHERE DIR.COD_DIR=PED.COD_DIR
               AND  UBI_DIR.UBDEP=DIR.UBDEP
               AND  UBI_DIR.UBPRV=DIR.UBPRV
               AND  UBI_DIR.UBDIS=DIR.UBDIS )
             
             ELSE
                     '||||||||||||||||||||||' END ||
                   DECODE(COMP.IND_COMP_CREDITO,'S','02','N','01','01')||'|'||-- 53 Condiciones de pago
                   ''||'|'||-- 54 Forma de Pago
                   DECODE(COMP.IND_COMP_CREDITO,'S',TO_CHAR (COMP.FEC_CREA_COMP_PAGO +30,'YYYY-MM-DD'),'N',TO_CHAR (COMP.FEC_CREA_COMP_PAGO,'YYYY-MM-DD'),TO_CHAR (COMP.FEC_CREA_COMP_PAGO,'YYYY-MM-DD'))||-- 55 Fecha de Vencimiento de Pago
                CHR(13)*/
              CASE PED.TIP_PED_VTA
                WHEN '02' --PEDIDO DELIVERY
                 THEN
                 LOCAL.UBDEP || LOCAL.UBPRV || LOCAL.UBDIS || '|' || -- 32 Direcci�n del Punto de Partida, C�digo de Ubigeo
                 REEMPLAZA_CARACTERES(LOCAL.DIREC_LOCAL_CORTA) || '|' || -- 33 Direcci�n del Punto de Partida, Direcci�n completa y detallada
                 '' || '|' || -- 34 Direcci�n del Punto de Partida, Urbanizaci�n
                 UBI_LOCAL.NOPRV || '|' || -- 35 Direcci�n del Punto de Partida, Provincia
                 UBI_LOCAL.NODEP || '|' || -- 36 Direcci�n del Punto de Partida, Departamento
                 UBI_LOCAL.NODIS || '|' || -- 37 Direcci�n del Punto de Partida, Distrito
                 'PE' || '|' || -- 38 Direcci�n del Punto de Partida, C�digo de Pa�s
                 (SELECT
                  
                   TMP_PED.UBDEP || TMP_PED.UBPRV || TMP_PED.UBDIS || '|' || -- 39 Direcci�n del Punto de Llegada, C�digo de Ubigeo
                   REEMPLAZA_CARACTERES(COMA.DIRECCION) || '|' || -- 40 Direcci�n del Punto de Llegada, Direcci�n completa y detallada
                   '' || '|' || -- 41 Direcci�n del Punto de Llegada, Urbanizaci�n
                   UBI_DIR.NOPRV || '|' || -- 42 Direcci�n del Punto de Llegada, Provincia
                   UBI_DIR.NODEP || '|' || -- 43 Direcci�n del Punto de Llegada, Departamento
                   UBI_DIR.NODIS || '|' || -- 44 Direcci�n del Punto de Llegada, Distrito
                   'PE' || '|' || -- 45 Direcci�n del Punto de Llegada, C�digo de Pa�s
                   '' || '|' || -- 46 Placa Veh�culo
                   '' || '|' || -- 47 N� constancia de inscripci�n del veh�culo o certificado de habilitacion vehicular
                   '' || '|' || -- 48 Marca Veh�culo
                   '' || '|' || -- 49 N� de licencia de conducir
                   '' || '|' || -- 50 Ruc transportista
                   '' || '|' || -- 51 Ruc transportista -Tipo Documento
                   '' || '|' -- 52 Raz�n social del transportista
                    FROM TMP_VTA_PEDIDO_VTA_CAB TMP_PED,
                         TMP_CE_CAMPOS_COMANDA  COMA,
                         UBIGEO                 UBI_DIR
                   WHERE TMP_PED.NUM_PED_VTA = PED.NUM_PEDIDO_DELIVERY
                        
                     AND COMA.NUM_PED_VTA = PED.NUM_PEDIDO_DELIVERY
                     AND UBI_DIR.UBDEP = TMP_PED.UBDEP
                     AND UBI_DIR.UBPRV = TMP_PED.UBPRV
                     AND UBI_DIR.UBDIS = TMP_PED.UBDIS)
              
                ELSE
                 '|||||||||||||||||||||'
              END ||
              --DECODE(COMP.IND_COMP_CREDITO, 'S', '02', 'N', '01', '01') || '|' || -- 53 Condiciones de pago
              -- 53 Condiciones de pago
              -- LTAVARA 14.11.2014 EN CASO DE NOTA DE CREDITO NO ENVIAR DATO
              CASE 
                WHEN COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
                  ''
                ELSE 
                  DECODE(COMP.IND_COMP_CREDITO, 'S', '02', 'N', '01', '01') 
                END|| '|' || 
              -- 12.11.2014
              NVL((
                  SELECT REEMPLAZA_CARACTERES(EMP.NUM_OC)
                  FROM   VTA_PEDIDO_VTA_CAB_EMP EMP
                  WHERE  EMP.COD_GRUPO_CIA = PED.COD_GRUPO_CIA
                  AND    EMP.COD_LOCAL = PED.COD_LOCAL
                  AND    EMP.NUM_PED_VTA = PED.NUM_PED_VTA),''
                  )||'|'||-- Orden de Compra	54
              ''||'|'||-- Numero de contrato	55
              ''||'|'||-- Nota general del documento	56
              ''||'|'||-- Valor del seguro	57
              ''||'|'||-- Valor del flete	58
              ''||'|'||-- Valor FOB	59
              trim(TO_CHAR(NVL(COMP.VAL_REDONDEO_COMP_PAGO,0), '999999990.00'))||'|'||-- Redondeo	60
              ''||-- Codigo del Proveedor	61
              /*
              '' || '|' || -- 54 Forma de Pago
              DECODE(COMP.IND_COMP_CREDITO,
                     'S',
                     TO_CHAR(COMP.FEC_CREA_COMP_PAGO + 30, 'YYYY-MM-DD'),
                     'N',
                     TO_CHAR(COMP.FEC_CREA_COMP_PAGO, 'YYYY-MM-DD'),
                     TO_CHAR(COMP.FEC_CREA_COMP_PAGO, 'YYYY-MM-DD')) || -- 55 Fecha de Vencimiento de Pago
              */
              CHR(13)
        INTO v_vCabecera
      
        FROM VTA_PEDIDO_VTA_CAB PED,
             vta_COMP_PAGO      COMP,
             PBL_LOCAL          LOCAL,
             PBL_CIA            CIA,
             UBIGEO             UBI,
             UBIGEO             UBI_LOCAL
       WHERE PED.NUM_PED_VTA = COMP.NUM_PED_VTA
         AND COMP.COD_LOCAL = LOCAL.COD_LOCAL
         AND LOCAL.COD_CIA = CIA.COD_CIA
         AND CIA.UBDEP = UBI.UBDEP
         AND CIA.UBPRV = UBI.UBPRV
         AND CIA.UBDIS = UBI.UBDIS
         AND LOCAL.UBDEP = UBI_LOCAL.UBDEP
         AND LOCAL.UBPRV = UBI_LOCAL.UBPRV
         AND LOCAL.UBDIS = UBI_LOCAL.UBDIS
         AND COMP.COD_GRUPO_CIA = cGrupoCia
         AND COMP.COD_LOCAL = cCodLocal
         AND COMP.NUM_PED_VTA = cNumPedidoVta
         AND COMP.SEC_COMP_PAGO = cSecCompPago;
         --AND COMP.TIP_COMP_PAGO = cTipoDocumento;
    END;
  
    RETURN v_vCabecera;
  END;

  -- Author  : LTAVARA
  -- Proposito : Obtener los datos de otros conceptos del comprobante electr�nico
  /***************************************************************************/
  FUNCTION F_VAR_TRM_GET_DOC(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0')
  
   RETURN VARCHAR2 AS
    v_vDOC VARCHAR2(5555) := NULL;
  BEGIN
    BEGIN
      -- DETALLE DE OTROS CONCEPTOS
    
      FOR REG IN (
                  -- 1001 Total valor de venta - operaciones gravadas
                  select 'DOC|' || '1001|' ||
                   TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_GRAV_E), 0), '999999990.00')) || '|'||
                          /*TRIM(TO_CHAR(NVL(ABS(DECODE(cTipoClienteConvenio,DOC_EMPRESA,--SI ES EMPRESA
                                DECODE(COMP.TOTAL_GRAV_E,'0.00',-- SI EL TOTAL GRAVADA ES 0 se SUMA EL COPAGO
                                COMP.TOTAL_GRAV_E ,
                                COMP.TOTAL_GRAV_E-COMP.VAL_COPAGO_E
                                )--OTRO CASE
                                       ,COMP.TOTAL_GRAV_E)), 0),
                                       '999999990.00')) || '|'||*/-- 22/12/2014 ltavara
                                       ''||'|'|| -- 3.* Monto Total Documento Incluida la percepcion
                                       ''|| -- 4.* Este campo puede informar:
                                                  --   -. Base imponible percepcion.
                                                  --   -. Valor referencial del servicio de transporte 
                                                  -- de bienes realizado por v�a terrestre.
                                       CHR(13) VALOR
                    FROM VTA_COMP_PAGO COMP
                   WHERE COMP.COD_GRUPO_CIA = cGrupoCia
                     AND COMP.COD_LOCAL = cCodLocal
                     AND COMP.NUM_PED_VTA = cNumPedidoVta
                     AND COMP.SEC_COMP_PAGO = cSecCompPago
                     --AND COMP.TIP_COMP_PAGO = cTipoDocumento
                     AND COMP.TOTAL_GRAV_E != 0
                  UNION ALL
                  
                  -- 1002 Total valor de venta - operaciones inafectas
                  select 'DOC|' || '1002|' ||
                          /*TRIM(TO_CHAR(NVL(ABS(DECODE(cTipoClienteConvenio,DOC_EMPRESA,
                           DECODE(COMP.TOTAL_INAF_E,0,
                           COMP.TOTAL_INAF_E,
                          COMP.TOTAL_INAF_E- COMP.VAL_COPAGO_E),
                          COMP.TOTAL_INAF_E )), 0),*/ --22/12/2014
                        TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_INAF_E), 0),'999999990.00')) || '|'||
                                       ''||'|'|| -- 3.* Monto Total Documento Incluida la percepcion
                                       ''|| -- 4.* Este campo puede informar:
                                                  --   -. Base imponible percepcion.
                                                  --   -. Valor referencial del servicio de transporte 
                                                  -- de bienes realizado por v�a terrestre.
                                       CHR(13) VALOR
                    FROM VTA_COMP_PAGO COMP
                   WHERE COMP.COD_GRUPO_CIA = cGrupoCia
                     AND COMP.COD_LOCAL = cCodLocal
                     AND COMP.NUM_PED_VTA = cNumPedidoVta
                     AND COMP.SEC_COMP_PAGO = cSecCompPago
                     --AND COMP.TIP_COMP_PAGO = cTipoDocumento
                     AND COMP.TOTAL_INAF_E != 0
                  UNION ALL
                  
                  -- 1003 Total valor de venta - operaciones exoneradas
                  select 'DOC|' || '1003|' ||
                          TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_EXON_E), 0),
                                       '999999990.00')) || '|'||
                                       ''||'|'|| -- 3.* Monto Total Documento Incluida la percepcion
                                       ''|| -- 4.* Este campo puede informar:
                                                  --   -. Base imponible percepcion.
                                                  --   -. Valor referencial del servicio de transporte 
                                                  -- de bienes realizado por v�a terrestre.
                                       CHR(13) VALOR
                    FROM VTA_COMP_PAGO COMP
                   WHERE COMP.COD_GRUPO_CIA = cGrupoCia
                     AND COMP.COD_LOCAL = cCodLocal
                     AND COMP.NUM_PED_VTA = cNumPedidoVta
                     AND COMP.SEC_COMP_PAGO = cSecCompPago
                     --AND COMP.TIP_COMP_PAGO = cTipoDocumento
                     AND COMP.TOTAL_EXON_E != 0
                  UNION ALL
                  
                  -- 1004 Total valor de venta  Operaciones gratuitas
                  select 'DOC|' || '1004|' ||
                          TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_GRATU_E), 0),
                                       '999999990.00')) || '|'||
                                       ''||'|'|| -- Monto Total Documento Incluida la percepcion
                                       ''|| -- Este campo puede informar:
                                                  -- 1. Base imponible percepcion.
                                                  -- 2. Valor referencial del servicio de transporte 
                                                  -- de bienes realizado por v�a terrestre.
                                       CHR(13) VALOR
                    FROM VTA_COMP_PAGO COMP
                   WHERE COMP.COD_GRUPO_CIA = cGrupoCia
                     AND COMP.COD_LOCAL = cCodLocal
                     AND COMP.NUM_PED_VTA = cNumPedidoVta
                     AND COMP.SEC_COMP_PAGO = cSecCompPago
                     --chuanes 16/01/2015
                     AND COMP.TIP_COMP_PAGO != '04'
                     AND COMP.TOTAL_GRATU_E != 0
                  UNION ALL
                  
                  -- 2005 Total descuentos
                   select 'DOC|' || '2005|' ||
                          --TRIM(TO_CHAR(NVL(ABS( DECODE(cTipoClienteConvenio,DOC_EMPRESA,(COMP.VAL_COPAGO_E+COMP.TOTAL_DESC_E),COMP.TOTAL_DESC_E)), 0),
                            TRIM(TO_CHAR(CASE WHEN NVL(COMP.PCT_BENEFICIARIO,0)>0 
                                                       AND NVL(COMP.PCT_EMPRESA,0)>0
                                                       AND COMP.TIP_CLIEN_CONVENIO=DOC_EMPRESA  THEN
                                                       COMP.VAL_COPAGO_E+COMP.TOTAL_DESC_E
                                                       ELSE 
                                                         COMP.TOTAL_DESC_E --23.12.2014 LTAVARA
                                                       END,'999999990.00')) || '|'||
                                       ''||'|'|| -- 3.* Monto Total Documento Incluida la percepcion
                                       ''|| -- 4.* Este campo puede informar:
                                                  --   -. Base imponible percepcion.
                                                  --   -. Valor referencial del servicio de transporte 
                                                  -- de bienes realizado por v�a terrestre.
                                       CHR(13) VALOR
                    FROM VTA_COMP_PAGO COMP
                   WHERE COMP.COD_GRUPO_CIA = cGrupoCia
                     AND COMP.COD_LOCAL = cCodLocal
                     AND COMP.NUM_PED_VTA = cNumPedidoVta
                     AND COMP.SEC_COMP_PAGO = cSecCompPago
                     --AND COMP.TIP_COMP_PAGO = cTipoDocumento
                    -- AND COMP.TOTAL_DESC_E != 0
                     ) LOOP
        v_vDOC := v_vDOC || REG.VALOR;
      END LOOP;
    EXCEPTION
      WHEN OTHERS THEN
        NULL;
    END;
    RETURN v_vDOC;
  
  END;

  -- Author  : LTAVARA
  -- Created : 14/07/2014 06:50:35 p.m.
  -- Proposito : Obtener la leyenda del comprobante electr�nico
  /***************************************************************************/
  FUNCTION F_VAR_TRM_GET_NOTAS(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2 AS
  
    v_vDN VARCHAR2(3276) := NULL;
  
  BEGIN
    /*
      N�mero de L�nea de Nota	1
      C�digo de la leyenda	2
      Glosa de la leyenda	3
      Descripcion del tramo o viaje	4
    */ 
    BEGIN
      SELECT 'DN|'||
             '1|'||--N�mero de L�nea de Nota	1
             '1000|' ||--C�digo de la leyenda	2
             --- Glosa de la leyenda	3
           'SON: ' ||  TRIM(UPPER(TRIM(FARMA_UTILITY.LETRAS(ABS(COMP.VAL_NETO_COMP_PAGO +COMP.VAL_REDONDEO_COMP_PAGO)))) || ' ' ||
             DECODE(COMP.COD_TIP_MONEDA,
                    '01',
                    'NUEVOS SOLES',
                    '02',
                    'DOLARES',
                    'NUEVOS SOLES')) ||'|'||
             -------------------------------       
             ''||-- Descripcion del tramo o viaje	4
             CHR(13) -- Tipo de Moneda
        INTO v_vDN
        FROM VTA_COMP_PAGO COMP
       WHERE COMP.COD_GRUPO_CIA = cGrupoCia
         AND COMP.COD_LOCAL = cCodLocal
         AND COMP.NUM_PED_VTA = cNumPedidoVta
         AND COMP.SEC_COMP_PAGO = cSecCompPago;
         --AND COMP.TIP_COMP_PAGO = cTipoDocumento;
    EXCEPTION
      WHEN OTHERS THEN
        NULL;
    END;
    RETURN v_vDN;
  END;

  -- Author  : LTAVARA
  -- Created : 14/07/2014 06:50:35 p.m.
  -- Proposito : Obtener los datos del detalle del comprobante electr�nico
  /***************************************************************************/  
  FUNCTION F_VAR_TRM_GET_DET(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0')
--   RETURN VARCHAR2 AS
  RETURN FARMACURSOR AS

    v_vDETALLE      VARCHAR2(32767) := NULL;
    v_vCOPAGO_BENEF CHAR(1) := 'N';
    v_vCOD_PROD     LGT_PROD.COD_PROD%type := '0';
    cursorDetalle   FarmaCursor;
    vTipoCompPago   VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE;
  BEGIN
    BEGIN

      IF cTipoClienteConvenio = DOC_BENEFICIARIO THEN
        SELECT (CASE -- SOLO PARA CONVENIO COPAGO PARA BENEFICIARIO -- LTAVARA 17.10.2014
                 WHEN NVL(COMP.TIP_CLIEN_CONVENIO, 0) = DOC_BENEFICIARIO AND
                      NVL(COMP.PCT_BENEFICIARIO, 0) > 0 AND
                      NVL(COMP.PCT_EMPRESA, 0) > 0 THEN
                  'S'
                 ELSE
                  'N'
               END)
          INTO v_vCOPAGO_BENEF
          FROM VTA_COMP_PAGO COMP
         WHERE COMP.COD_GRUPO_CIA = cGrupoCia
           AND COMP.COD_LOCAL = cCodLocal
           AND COMP.NUM_PED_VTA = cNumPedidoVta
           AND COMP.SEC_COMP_PAGO = cSecCompPago
           AND COMP.TIP_CLIEN_CONVENIO = cTipoClienteConvenio;
      END IF;
      
      SELECT CP.TIP_COMP_PAGO
        INTO vTipoCompPago
        FROM VTA_COMP_PAGO CP
       WHERE CP.COD_GRUPO_CIA = cGrupoCia
         AND CP.COD_LOCAL = cCodLocal
         AND CP.NUM_PED_VTA = cNumPedidoVta
         AND CP.SEC_COMP_PAGO = cSecCompPago;
      
      -- VALIDAR
      IF v_vCOPAGO_BENEF = 'N' THEN
        
        --  NO ES COPAGO BENEFICIARIO
        -- for REG in (SELECT
        OPEN cursorDetalle FOR
             SELECT
                    --DETALLE DE DOCUMENTO
                     'DE|' || -- LINEA DE DETALLE
                      DET.SEC_PED_VTA_DET || '|' || -- 1 Correlativo de L�nea de Detalle
                      TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E, 0),
                                   '999999990.00')) || '|' || -- 2 Precio de venta unitario por item
                      'ZZ' || '|' || -- 3  Unidad de Medida
                      TRIM(TO_CHAR(NVL(ABS(DET.CANT_UNID_VDD_E), 0),
                                   '999999990.000')) || '|' || -- 4 Cantidad de unidades vendidas pot item (Q)
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_ITEM_E), 0),
                                   '999999990.00')) || '|' || -- 5 Monto del Item
                      DET.COD_PROD || '|' || -- 6 Codigo de Producto
                      DET.COD_TIP_PREC_VTA_E || '|' || -- 7 Tipo de Precio de Venta
                     -- TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_UNIT_ITEM_E),0),'999999990.00'))||'|'||-- Valor de venta unitario por �tem
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_UNIT_ITEM_E),
                                       0),
                                   '999999990.000')) || '|' || -- 8 Valor de venta unitario por �tem
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_ITEM_E), 0),
                                   '999999990.00')) ||'|'|| -- 9 Valor de venta por item
                      ''||'|'||-- 10 N�mero de lote	10
                      ''||'|'||-- 11 Marca	11
                      ''||'|'||-- 12 Pais de origen	12
                      ''||     -- 13 N� de Posicion que el Item comprado tiene en la Orden de Compra	13
                      CHR(13) ||
                      /* *********************************************************************** */
                      /* ***********               Descripcion del Item	DEDI     *************** */
                      /* *********************************************************************** */
                     --DESCRIPCION DEL ITEM
                      'DEDI|' || -- LINEA DE DESCRIPCION
                     --REPLACE(REPLACE((ABS(DET.CANT_ATENDIDA)||'/'||ABS(DET.VAL_FRAC)
                      REPLACE(REPLACE((
                                      -- DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0, (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Caso contrario las imprime en fracciones
                                      -- ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','/'||ABS(DET.VAL_FRAC)))||' '|| --YA NO SE DEBE ENVIAR
                                       SUBSTR((DECODE(DET.COD_TIP_AFEC_IGV_E,
                                              '31',
                                              'PROM.-',
                                              '') || PROD.DESC_PROD ||' '||
                                              SUBSTR(DECODE(DET.VAL_FRAC,1,prod.desc_unid_present,DET.UNID_VTA),0,20)||' '
                                              || LAB.NOM_LAB ),0,250)||
                                      -- dubilluz 06.11.2014
                                       '@#@' ||
                                       ABS(DET.CANT_ATENDIDA)
                                      /* DECODE(cab.tip_ped_vta,
                                              '03', --Si el Tipo de venta es Mayorista
                                              DECODE(MOD(ABS(DET.CANT_ATENDIDA),
                                                         ABS(DET.VAL_FRAC)),
                                                     0,
                                                     (ABS(DET.CANT_ATENDIDA) /
                                                     ABS(DET.VAL_FRAC)) || '', --Imprime las cantidad
                                                     ABS(DET.CANT_ATENDIDA) ||
                                                     DECODE(ABS(DET.VAL_FRAC),
                                                            1,
                                                            '',
                                                            '')), --sin fraccion
                                              DECODE(MOD(ABS(DET.CANT_ATENDIDA),
                                                         ABS(DET.VAL_FRAC)),
                                                     0,
                                                     (ABS(DET.CANT_ATENDIDA) /
                                                     ABS(DET.VAL_FRAC)) || '', --Caso contrario las imprime en fracciones
                                                     ABS(DET.CANT_ATENDIDA) ||
                                                     DECODE(ABS(DET.VAL_FRAC),
                                                            1,
                                                            '',
                                                            '/' ||
                                                            ABS(DET.VAL_FRAC)))) */
                                      -- dubilluz 06.11.2014
                                      ),
                                      CHR(10),
                                      '@@'),
                              CHR(13),
                              '@@') || '|'||
                              -- 2.Nota complementarias a descripci�n del �tem	2
                              ''||
                              CHR(13) || --Descripcion
                      /* *********************************************************************** */
                      /* ***********  Descuentos y Recargos del Item	DEDR       *************** */
                      /* *********************************************************************** */
                     --DETALLE DESCUENTO O RECARGO
                      'DEDR|' || -- LINEA DE DESCUENTO
                      DECODE(DET.AHORRO, 0, '0', 'false') || '|' || -- Indicador de Tipo
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_TOTAL_DESC_ITEM_E), 0),
                                   '999999990.00')) || CHR(13) || -- Monto Descuento o Recargo
                     -- DETALLE DEIM
                      'DEIM|' || -- LINEA DE IMPUESTOS
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_TOTAL_IGV_ITEM_E), ''),
                                   '999999990.00')) || '|' || -- 1 Importe total de un tributo para este item
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_ITEM_E), ''),
                                   '999999990.00')) || '|' || -- 2 Base Imponible (IGV, IVAP, Otros = Q x VU - Descuentos + ISC  )
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_TOTAL_IGV_ITEM_E), ''),
                                   '999999990.00')) || '|' || -- 3 Importe expl�cito a tributar ( = Tasa Porcentaje * Base Imponible)
                      TRIM(TO_CHAR(NVL(DET.VAL_IGV, 0), '00.00')) || '|' || -- 4 Tasa Impuesto
                      DECODE(DET.COD_TIP_AFEC_IGV_E, 10, 'VAT') || '|' || -- 5 Tipo de Impuesto
                      DET.COD_TIP_AFEC_IGV_E || '|' || -- 6 Afectaci�n del IGV
                      '00' || '|' || -- 7 Sistema de ISC
                      '1000' || '|' || -- 8 Identificaci�n del tributo
                      'IGV' || '|' || -- 9 Nombre del Tributo
                      'VAT' AS VALOR -- 10 C�digo del Tipo de Tributo
                      FROM VTA_PEDIDO_VTA_DET DET,
                           LGT_PROD           PROD,
                           LGT_LAB            LAB,
                           vta_pedido_vta_cab cab
                     WHERE DET.COD_PROD = PROD.COD_PROD
                       AND PROD.COD_GRUPO_CIA= cGrupoCia
                       AND PROD.COD_LAB=LAB.COD_LAB
                       AND DET.COD_GRUPO_CIA = cGrupoCia
                       AND DET.COD_LOCAL = cCodLocal
                       AND DET.NUM_PED_VTA = cNumPedidoVta
                       AND CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                       AND CAB.COD_LOCAL = DET.COD_LOCAL
                       AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                       AND (CASE
                             WHEN cTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoCompPago != COD_TIP_COMP_NOTA_CRED THEN
                              DET.SEC_COMP_PAGO_BENEF
                             WHEN cTipoClienteConvenio = DOC_EMPRESA AND vTipoCompPago != COD_TIP_COMP_NOTA_CRED THEN
                              DET.SEC_COMP_PAGO_EMPRE
                             ELSE
                              DET.SEC_COMP_PAGO
                           END) = cSecCompPago
                    ORDER BY DET.SEC_PED_VTA_DET ASC;/*)
                      LOOP*/

          -- v_vDETALLE := v_vDETALLE || REG.VALOR || CHR(13);

--        END LOOP;

      ELSE
        -- COPAGO BENEFICIARIO

        v_vCOD_PROD := F_VAR_COD_SERV_PED(cGrupoCia,
                                          cCodLocal,
                                          cNumPedidoVta,
                                          cSecCompPago);
      OPEN cursorDetalle FOR
        SELECT
        --DETALLE DE DOCUMENTO
         'DE|' || -- LINEA DE DETALLE
          '1' || '|' || -- Correlativo de L�nea de Detalle
          TRIM(TO_CHAR(NVL(PAGO.VAL_NETO_COMP_PAGO, 0),'999999990.00')) || '|' || -- Precio de venta unitario por item
          'NIU' || '|' || -- Unidad de Medida
          '1' || '|' || -- Cantidad de unidades vendidas pot item (Q)
          TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO), 0),'999999990.00')) || '|' || -- Monto del Item
          NVL(v_vCOD_PROD, '0000') || '|' || -- Codigo de Producto
          --TRIM(TO_CHAR(DECODE(PAGO.PORC_IGV_COMP_PAGO, 18.00, '01', ''),'999999990.00')) || '|' || -- Tipo de Precio de Venta
          '01' || '|' || -- Tipo de Precio de Venta
          TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO - PAGO.VAL_IGV_COMP_PAGO), 0),'999999990.00')) || '|' || -- Valor de venta unitario por �tem
          TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO - PAGO.VAL_IGV_COMP_PAGO), 0),'999999990.00'))  || '|'||-- 9 Valor de venta por item
          ''||'|'||-- 10 N�mero de lote	10
          ''||'|'||-- 11 Marca	11
          ''||'|'||-- 12 Pais de origen	12
          ''||     -- 13 N� de Posicion que el Item comprado tiene en la Orden de Compra	13
          CHR(13) ||
          /* *********************************************************************** */
          /* ***********               Descripcion del Item	DEDI     *************** */
          /* *********************************************************************** */
         --DESCRIPCION DEL ITEM
          'DEDI|' || -- LINEA DE DESCRIPCION
          REPLACE(REPLACE(F_VAR_DSC_PROD_SERV(cGrupoCia, cCodLocal),
                          CHR(10),
                          '@@'),
                  CHR(13),
                  '@@')--Descripcion
                    || '|'||
                              -- 2.Nota complementarias a descripci�n del �tem	2
                              ''||
                              CHR(13) ||
         --DETALLE DESCUENTO O RECARGO
          'DEDR|' || -- LINEA DE DESCUENTO
          '0.00' || '|' || -- Indicador de Tipo
          '0.00' || CHR(13) || -- Monto Descuento o Recargo
         -- DETALLE DEIM
          'DEIM|' || -- LINEA DE IMPUESTOS
          TRIM(TO_CHAR(ABS(PAGO.VAL_IGV_COMP_PAGO),'999999990.00')) || '|' || -- 1 Importe total de un tributo para este item
          TRIM(TO_CHAR(ABS(PAGO.VAL_IGV_COMP_PAGO),'999999990.00')) || '|' || -- 2 Base Imponible (IGV, IVAP, Otros = Q x VU - Descuentos + ISC  )
          TRIM(TO_CHAR(ABS(PAGO.VAL_IGV_COMP_PAGO),'999999990.00')) || '|' || -- 3 Importe expl�cito a tributar ( = Tasa Porcentaje * Base Imponible)
          TRIM(TO_CHAR(NVL(PAGO.PORC_IGV_COMP_PAGO, 0),'999999990.00')) || '|' || -- 4 Tasa Impuesto
          DECODE(PAGO.PORC_IGV_COMP_PAGO, 18.00, 'VAT', '') || '|' || -- 5 Tipo de Impuesto
          DECODE(PAGO.PORC_IGV_COMP_PAGO, 18.00, '10', '30') || '|' || -- 6 Afectaci�n del IGV
          '00' || '|' || -- 7 Sistema de ISC
          '1000' || '|' || -- 8 Identificaci�n del tributo
          'IGV' || '|' || -- 9 Nombre del Tributo
          'VAT' AS VALOR -- || CHR(13) -- 10 C�digo del Tipo de Tributo
--          INTO v_vDETALLE
          FROM VTA_COMP_PAGO PAGO
         WHERE PAGO.COD_GRUPO_CIA = cGrupoCia
           AND PAGO.COD_LOCAL = cCodLocal
           AND PAGO.NUM_PED_VTA = cNumPedidoVta
           AND PAGO.SEC_COMP_PAGO = cSecCompPago
           AND PAGO.TIP_CLIEN_CONVENIO = cTipoClienteConvenio;

      END IF;
    END;
--    RETURN v_vDETALLE;
    RETURN cursorDetalle;

  END;

  -- Author  : LTAVARA
  -- Created : 28/10/2014 05:20:35 p.m.
  -- Proposito : Obtener los datos descuentos y recargos globales
  /***************************************************************************/
  FUNCTION F_VAR_TRM_GET_RG(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0')
    RETURN VARCHAR2 AS
  
    v_vDESC_RECARGO_GLOBAL VARCHAR2(1000) := NULL;
  
  BEGIN
    BEGIN
      IF cTipoClienteConvenio = DOC_EMPRESA THEN
        SELECT
        --IMPUESTO GLOBAL POR DOCUMENTO
         'DR|' || -- LINEA DE IMPUESTO GLOBAL
         'false' || '|' || --1 Tipo de Movimiento D/R
         '' || '|' || --2 C�digo Motivo D/R
         'ANTICIPO' || '|' || --3 Glosa D/R
         'PEN' || '|' || --4 Moneda
         TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_COPAGO_COMP_PAGO), 0),
                      '999999990.00')) || '|' || --5 Monto D/R
         '9999' || '|' || --6 Identificaci�n Tributo
         'OTROS CONCEPTOS DE PAGO' || '|' || --7 Nombre del Tributo
         'OTH' || '|' || --8 C�digo Tipo de Tributo
         TRIM(TO_CHAR(PAGO.PCT_BENEFICIARIO / 100, '999999990.00')) || '|' || --9 Factor
         TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_BRUTO_COMP_PAGO), 0), '999999990.00')) ||
         CHR(13) --10 Monto Base
          INTO v_vDESC_RECARGO_GLOBAL
          FROM VTA_COMP_PAGO PAGO
         WHERE PAGO.COD_GRUPO_CIA = cGrupoCia
           AND PAGO.COD_LOCAL = cCodLocal
           AND PAGO.NUM_PED_VTA = cNumPedidoVta
           AND PAGO.SEC_COMP_PAGO = cSecCompPago;
      END IF;
    
    END;
    RETURN v_vDESC_RECARGO_GLOBAL;
  
  END;

  -- Author  : LTAVARA
  -- Created : 13/08/2014 03:20:35 p.m.
  -- Proposito : Obtener los datos del impuestos globales
  /***************************************************************************/
  FUNCTION F_VAR_TRM_GET_IG(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2 AS
  
    v_vIMP_GLOBAL VARCHAR2(500) := NULL;
    v_vIndCopago VARCHAR2(1) := 'N';
  
  BEGIN
    BEGIN
    /*  SELECT (CASE -- SOLO PARA CONVENIO COPAGO PARA BENEFICIARIO -- LTAVARA 17.10.2014
                 WHEN
                 NVL(PAGO.TIP_CLIEN_CONVENIO, 0) = DOC_EMPRESA AND  
                  NVL(PAGO.PCT_BENEFICIARIO, 0) > 0 AND
                      NVL(PAGO.PCT_EMPRESA, 0) > 0 THEN
                  'S'
                 ELSE
                  'N'
               END)
       INTO v_vIndCopago
        FROM VTA_COMP_PAGO PAGO
       WHERE PAGO.COD_GRUPO_CIA = cGrupoCia
         AND PAGO.COD_LOCAL = cCodLocal
         AND PAGO.NUM_PED_VTA = cNumPedidoVta
         AND PAGO.SEC_COMP_PAGO = cSecCompPago;
    
   IF cTipoClienteConvenio = DOC_EMPRESA AND v_vIndCopago='S' THEN

      SELECT
      --IMPUESTO GLOBAL POR DOCUMENTO
       'DI|' || -- LINEA DE IMPUESTO GLOBAL
       TRIM(TO_CHAR(NVL(ABS(DECODE(TRIM(PAGO.TOTAL_GRAV_E),0,0,(PAGO.TOTAL_GRAV_E - PAGO.VAL_COPAGO_E)*0.18)), 0), '999999990.00')) || '|' || -- 1 Sumatoria Tributo (IGV,ISC, Otros)
       TRIM(TO_CHAR(NVL(ABS(DECODE(TRIM(PAGO.TOTAL_GRAV_E),0,0,(PAGO.TOTAL_GRAV_E - PAGO.VAL_COPAGO_E)*0.18)), 0), '999999990.00')) || '|' || -- 2 Sumatoria Tributo (IGV,ISC, Otros)
       DECODE(DECODE(PAGO.TOTAL_GRAV_E,0,0,(PAGO.TOTAL_GRAV_E - PAGO.VAL_COPAGO_E)*0.18), NULL, '', 1000) || '|' || -- 3 Identificaci�n del tributo
       DECODE(DECODE(PAGO.TOTAL_GRAV_E,0,0,(PAGO.TOTAL_GRAV_E - PAGO.VAL_COPAGO_E)*0.18), NULL, '', 'IGV') || '|' || -- 4 Nombre del Tributo
       DECODE(DECODE(PAGO.TOTAL_GRAV_E,0,0,(PAGO.TOTAL_GRAV_E - PAGO.VAL_COPAGO_E)*0.18), NULL, '', 'VAT') || CHR(13) -- 5 C�digo del Tipo de Tributo
      
        INTO v_vIMP_GLOBAL
        FROM VTA_COMP_PAGO PAGO
       WHERE PAGO.COD_GRUPO_CIA = cGrupoCia
         AND PAGO.COD_LOCAL = cCodLocal
         AND PAGO.NUM_PED_VTA = cNumPedidoVta
         AND PAGO.SEC_COMP_PAGO = cSecCompPago;
         
    ELSE
        
       SELECT
      --IMPUESTO GLOBAL POR DOCUMENTO
       'DI|' || -- LINEA DE IMPUESTO GLOBAL
       TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E), 0), '999999990.00')) || '|' || -- 1 Sumatoria Tributo (IGV,ISC, Otros)
       TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E), 0), '999999990.00')) || '|' || -- 2 Sumatoria Tributo (IGV,ISC, Otros)
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 1000) || '|' || -- 3 Identificaci�n del tributo
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 'IGV') || '|' || -- 4 Nombre del Tributo
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 'VAT') || CHR(13) -- 5 C�digo del Tipo de Tributo
      
        INTO v_vIMP_GLOBAL
        FROM VTA_COMP_PAGO PAGO
       WHERE PAGO.COD_GRUPO_CIA = cGrupoCia
         AND PAGO.COD_LOCAL = cCodLocal
         AND PAGO.NUM_PED_VTA = cNumPedidoVta
         AND PAGO.SEC_COMP_PAGO = cSecCompPago;
    END IF;*/
 
  SELECT
      --IMPUESTO GLOBAL POR DOCUMENTO
       'DI|' || -- LINEA DE IMPUESTO GLOBAL
       TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E), 0), '999999990.00')) || '|' || -- 1 Sumatoria Tributo (IGV,ISC, Otros)
       TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E), 0), '999999990.00')) || '|' || -- 2 Sumatoria Tributo (IGV,ISC, Otros)
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 1000) || '|' || -- 3 Identificaci�n del tributo
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 'IGV') || '|' || -- 4 Nombre del Tributo
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 'VAT') || CHR(13) -- 5 C�digo del Tipo de Tributo
      
        INTO v_vIMP_GLOBAL
        FROM VTA_COMP_PAGO PAGO
       WHERE PAGO.COD_GRUPO_CIA = cGrupoCia
         AND PAGO.COD_LOCAL = cCodLocal
         AND PAGO.NUM_PED_VTA = cNumPedidoVta
         AND PAGO.SEC_COMP_PAGO = cSecCompPago;
            
      END;
    RETURN v_vIMP_GLOBAL;
  END;

  -- Author  : LTAVARA
  -- Created : 05/08/2014 03:20:35 p.m.
  -- Proposito : Obtener los datos de la referencia
  /***************************************************************************/
  FUNCTION F_VAR_TRM_GET_REF(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2 AS
  
    v_vREFERENCIA          VARCHAR2(2000) := NULL;
    v_vNUM_COMP_PAGO_EREF  VTA_COMP_PAGO.NUM_COMP_PAGO_EREF%TYPE := '0';
    v_vNUM_COMP_COPAGO_REF VTA_COMP_PAGO.NUM_COMP_COPAGO_REF%TYPE := '0';
  
  BEGIN
  
    BEGIN
    
      --VALIDAR PARA SI TIENE DOCUMENTO DE REFERENCIA ENVIA TRAMA
      --VALIDAR SI REFERANCIA NC O GUIA
      SELECT NVL(PAGO.NUM_COMP_PAGO_EREF, 0),
             NVL(PAGO.NUM_COMP_COPAGO_REF, 0)
        INTO v_vNUM_COMP_PAGO_EREF, v_vNUM_COMP_COPAGO_REF
        FROM VTA_COMP_PAGO PAGO
       WHERE PAGO.COD_GRUPO_CIA = cGrupoCia
         AND PAGO.COD_LOCAL = cCodLocal
         AND PAGO.NUM_PED_VTA = cNumPedidoVta
         AND PAGO.SEC_COMP_PAGO = cSecCompPago;
    
      IF v_vNUM_COMP_PAGO_EREF != '0' THEN
        --REFERENCIA NC
        SELECT
        --REFERENCIA POR DOCUMENTO
         'RE|' || -- LINEA DE REFERENCIA
         NVL(PAGO.NUM_COMP_PAGO_EREF, '0') || '|' || -- 1 Serie y n�mero del documento que modifica (Factura)
         '' || '|' || -- 2 Fecha de emisi�n
         '0'||PAGO.COD_TIP_COMP_PAGO_EREF || '|' || -- 3 Tipo de documento del documento que modifica (Factura)
         -- DECODE(PAGO.COD_TIP_COMP_PAGO_EREF, '1', '380', '3', '346', '383') || '|' || -- 4 Descripci�n  del tipo de Documento  UN 1001-Document Name
         -- LTAVARA 14.11.2014 NO ENVIAR CODIGO DE DOCUMENTO DE REFERENCIA SEGUN SUNAT
         '0' || '|' || -- 4 Descripci�n  del tipo de Documento  UN 1001-Document Name
         '' || '|' || -- 5 En el caso de Gu�as de Remisi�n N�mero de gu�a: serie - n�mero de documento
         '' || '|' || -- 6 En el caso de Gu�as de Remisi�n Tipo de Documento
         '' || '|' || -- 7 En el caso de otros tipos de Documentos N�mero de documento relacionado
         '' || '|' || -- 8 En el caso de otros Tipo de Documento (no factura no guia de remisi�n)
         CHR(13)
          INTO v_vREFERENCIA
          FROM VTA_COMP_PAGO PAGO
         WHERE PAGO.COD_GRUPO_CIA = cGrupoCia
           AND PAGO.COD_LOCAL = cCodLocal
           AND PAGO.NUM_PED_VTA = cNumPedidoVta
           AND PAGO.SEC_COMP_PAGO = cSecCompPago;
           ---AND PAGO.TIP_COMP_PAGO = cTipoDocumento;
      
      ELSE
        --REFERENCIA GUIA
        SELECT
        --REFERENCIA POR DOCUMENTO
         'RE|' || -- LINEA DE REFERENCIA
        --11.11.2014 
         --NVL(PAGOAUX.NUM_COMP_PAGO, '0')|| '|' || -- 1 Serie y n�mero del documento que modifica (Factura)
         /*
         lpad(SUBSTR(PAGOAUX.NUM_COMP_PAGO,1,3),4,'0')||'-'||lpad(SUBSTR(PAGOAUX.NUM_COMP_PAGO,6,13),8,'0')|| '|' ||
         NVL(TO_CHAR(PAGOAUX.FEC_CREA_COMP_PAGO, 'yyyy-MM-dd'), ' ') || '|' || -- 2 Fecha de emisi�n
         '09' || '|' || -- 3 Tipo de documento del documento que modifica (Factura)
         '383' || '|' || -- 4 Descripci�n  del tipo de Documento  UN 1001-Document Name         
         */
          '|' ||
          '|' || -- 2 Fecha de emisi�n
          '|' || -- 3 Tipo de documento del documento que modifica (Factura)
          '|' || -- 4 Descripci�n  del tipo de Documento  UN 1001-Document Name         
         
        --11.11.2014 
         --PAGOAUX.NUM_COMP_PAGO || '|' || -- 5 En el caso de Gu�as de Remisi�n N�mero de gu�a: serie - n�mero de documento
         lpad(SUBSTR(PAGOAUX.NUM_COMP_PAGO,1,3),4,'0')||'-'||lpad(SUBSTR(PAGOAUX.NUM_COMP_PAGO,6,13),8,'0')|| '|' ||
         '09' || '|' || -- 6 En el caso de Gu�as de Remisi�n Tipo de Documento
         '' || '|' || -- 7 En el caso de otros tipos de Documentos N�mero de documento relacionado
         '' || '|' || -- 8 En el caso de otros Tipo de Documento (no factura no guia de remisi�n)
         CHR(13)
          INTO v_vREFERENCIA
          FROM VTA_COMP_PAGO PAGOAUX
         WHERE PAGOAUX.COD_GRUPO_CIA = cGrupoCia
           AND PAGOAUX.COD_LOCAL = cCodLocal
           AND PAGOAUX.NUM_PED_VTA = cNumPedidoVta
           AND PAGOAUX.NUM_COMP_PAGO = v_vNUM_COMP_COPAGO_REF
           AND PAGOAUX.TIP_COMP_PAGO = '03'; --CODIGO GUIA
      END IF;
    
    EXCEPTION
      WHEN OTHERS THEN
        NULL;
    END;
    RETURN v_vREFERENCIA;
  END;
  /* ************************************************************************* */ 
FUNCTION F_VAR_TRM_GET_PP(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') 
                        RETURN VARCHAR2  AS 
vCadena varchar2(4000);
vExiste char(1);
vTipCompSunat varchar2(5);
begin
        --  Anticipos y PrePagos	PP
        -- ID del anticipo	1
        -- Monto del PrePago	2
        -- Fecha de recepci�n	3
        -- Fecha de Pago	4
        -- Hora de Pago	5
        -- Serie - Correlativo del documento que realiz� el pago	6
      /*  SELECT CASE --comentado todo porque ya no se envia el tag
                 WHEN NVL(CP.PCT_BENEFICIARIO, 0) > 0 AND NVL(CP.PCT_EMPRESA, 0) > 0 THEN
                  'S'
                 ELSE
                  'N'
               END,
               GET_TIP_COMP_SUNAT(CP.TIP_COMP_PAGO_REF)
         INTO  vExiste,vTipCompSunat
          FROM VTA_COMP_PAGO CP
         WHERE CP.COD_GRUPO_CIA = cGrupoCia
           AND CP.COD_LOCAL = cCodLocal
           AND CP.NUM_PED_VTA = cNumPedidoVta
           AND CP.SEC_COMP_PAGO = cSecCompPago;
        
     
     if vExiste = 'S' 
         --and (vTipCompSunat = '01'or vTipCompSunat = '03')
        and cTipoClienteConvenio = DOC_EMPRESA  
     then
        SELECT 
               'PP' || '|'|| 
               vTipCompSunat ||'|'|| -- 1 ID del anticipo
                TRIM(TO_CHAR(NVL(ABS(RE.Val_Neto_Comp_Pago), 0), '999999990.00')) ||'|'|| -- 2 Monto del PrePago
               TO_CHAR(RE.Fec_Crea_Comp_Pago,'YYYY-MM-DD') ||'|'|| -- 3 Fecha de recepci�n
               TO_CHAR(RE.Fec_Crea_Comp_Pago,'YYYY-MM-DD') ||'|'|| -- 4 Fecha de Pago
               TO_CHAR(RE.Fec_Crea_Comp_Pago,'hh:mm:ss') ||'|'|| -- 5 Hora de Pago
               trim(Substrb(RE.NUM_COMP_PAGO_E, 0, 4)||'-'||Substrb(RE.NUM_COMP_PAGO_E, 5))|| -- 6 Serie - Correlativo del documento que realiz� el pago	               
               CHR(13)
         INTO  vCadena
          FROM VTA_COMP_PAGO CP,
               VTA_COMP_PAGO RE
         WHERE CP.COD_GRUPO_CIA = cGrupoCia
           AND CP.COD_LOCAL = cCodLocal
           AND CP.NUM_PED_VTA = cNumPedidoVta
           AND CP.SEC_COMP_PAGO = cSecCompPago
           AND RE.FEC_CREA_COMP_PAGO > TRUNC(CP.FEC_CREA_COMP_PAGO)
           AND CP.COD_GRUPO_CIA = RE.COD_GRUPO_CIA
           AND CP.COD_LOCAL = RE.COD_LOCAL
           AND CP.NUM_COMP_COPAGO_REF = RE.NUM_COMP_PAGO;
     else vCadena := '';
     end if;   */

return vCadena ;
end;    
/* ************************************************************* */                      
  -- Author  : LTAVARA
  -- Created : 14/07/2014 06:50:35 p.m.
  -- Update : 15/09/2014 14:20:35 p.m.
  -- Proposito : Obtener los datos del mensaje antes del timbre del comprobante electr�nico
  /***************************************************************************/
  FUNCTION F_VAR_TRM_GET_MSJ_BF(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0')
    RETURN VARCHAR2 AS
    v_vPES_AT            VARCHAR2(1000) := 'PES|MensajesAt' || CHR(13);
    v_vDATO_COMP_PAGO    VARCHAR2(1000) := NULL;
    v_vCOD_CONVENIO      CHAR(10);
    v_vDATOS_ADICIONALES VARCHAR(555) := NULL;
    v_vCONTADOR          NUMBER(10) := 1;
    v_vDES_CONVENIO      VARCHAR(200) := NULL;
    v_vCOD_TIPO_CONVENIO NUMBER(1);
    v_vDATOS_FORMA_PAGO  VARCHAR(200);
    v_vREDONDEO          VARCHAR(200);
    v_vTIP_PED_VTA       CHAR(2);
    v_vIND_COPAGO        CHAR(1) := 'N';
    v_vCADENA_FORMA_PAGO VARCHAR2(200);
    v_vAhorroTotal       VARCHAR2(100); --LTAVARA 05.11.2014
    vCodProdVirtual       VARCHAR2(50);--LTAVARA 03.12.2014
    curdesc                FarmaCursor;--LTAVARA 03.12.2014
    vLinea               VARCHAR2(4000);--LTAVARA 03.12.2014
  
    CURSOR FORMA_PAGO_LIST is
      SELECT A.COD_FORMA_PAGO "COD_FORMA_PAGO",
             C.DESC_FORMA_PAGO "DESCRIPCION",
             TRIM(TO_CHAR(NVL(ABS(A.IM_PAGO), 0), '999999990.00')) "MONTO"
        FROM VTA_FORMA_PAGO_PEDIDO A,
             VTA_FORMA_PAGO_LOCAL  B,
             VTA_FORMA_PAGO        C
       WHERE A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.COD_FORMA_PAGO = B.COD_FORMA_PAGO
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_GRUPO_CIA = cGrupoCia
         AND A.COD_LOCAL = cCodLocal
         AND A.NUM_PED_VTA = cNumPedidoVta
       ORDER BY A.COD_FORMA_PAGO DESC;
  
  BEGIN
    -- ENVIAR REDONDEO
    SELECT nvl(COMP.TOTAL_DESC_E, 0),
           'PESD|' || v_vCONTADOR || '|REDONDEO' ||
           TO_CHAR(COMP.VAL_REDONDEO_COMP_PAGO, '999999990.00') || CHR(13)
      INTO v_vAhorroTotal, v_vREDONDEO
      FROM VTA_COMP_PAGO COMP
     WHERE COMP.COD_GRUPO_CIA = cGrupoCia
       AND COMP.COD_LOCAL = cCodLocal
       AND COMP.NUM_PED_VTA = cNumPedidoVta
       AND COMP.SEC_COMP_PAGO = cSecCompPago;
  
    IF v_vAhorroTotal > 0 AND cTipoDocumento !='04' THEN
      v_vPES_AT := v_vPES_AT || 'PESD|' || v_vCONTADOR ||
                   '|EN LA COMPRA USTED AHORRO S/.' ||
                   TO_CHAR(v_vAhorroTotal*1.18, '999999990.00') || CHR(13);
    END IF;
  
    v_vPES_AT := v_vPES_AT || v_vREDONDEO;
  
    v_vCONTADOR := v_vCONTADOR + 1;
  
    --OBTENER SI EL COMPROBANTE ES COPAGO
    IF cTipoClienteConvenio = DOC_BENEFICIARIO OR
       cTipoClienteConvenio = DOC_EMPRESA THEN
      SELECT (CASE -- SOLO PARA CONVENIO COPAGO PARA BENEFICIARIO -- LTAVARA 17.10.2014
               WHEN NVL(COMP.TIP_CLIEN_CONVENIO, 0) = DOC_BENEFICIARIO AND
                    NVL(COMP.PCT_BENEFICIARIO, 0) > 0 AND
                    NVL(COMP.PCT_EMPRESA, 0) > 0 THEN
                DOC_BENEFICIARIO
               WHEN NVL(COMP.TIP_CLIEN_CONVENIO, 0) = DOC_EMPRESA AND
                    NVL(COMP.PCT_BENEFICIARIO, 0) > 0 AND
                    NVL(COMP.PCT_EMPRESA, 0) > 0 THEN
                DOC_EMPRESA
               ELSE
                'N'
             END)
        INTO v_vIND_COPAGO
        FROM VTA_COMP_PAGO COMP
       WHERE COMP.COD_GRUPO_CIA = cGrupoCia
         AND COMP.COD_LOCAL = cCodLocal
         AND COMP.NUM_PED_VTA = cNumPedidoVta
         AND COMP.SEC_COMP_PAGO = cSecCompPago
         AND COMP.TIP_CLIEN_CONVENIO = cTipoClienteConvenio;
    END IF;
  
    -- ENVIAR FORMA DE PAGO -- LTAVARA 10.14.2014
  
    FOR forma_pago IN FORMA_PAGO_LIST LOOP
    
      v_vCADENA_FORMA_PAGO := 'PESD|' || v_vCONTADOR || '|' ||
                              forma_pago.descripcion || ' ' ||
                              forma_pago.monto;
    
      IF v_vIND_COPAGO != 'N' THEN
        -- COPAGO
        IF v_vIND_COPAGO = DOC_BENEFICIARIO THEN
          -- ES BENEFICIARIO
          IF forma_pago.COD_FORMA_PAGO != '00080' THEN
            v_vDATOS_FORMA_PAGO := v_vDATOS_FORMA_PAGO ||
                                   v_vCADENA_FORMA_PAGO || CHR(13);
            v_vCONTADOR         := v_vCONTADOR + 1;
          END IF;
        ELSE
          -- ES EMPRESA
          IF forma_pago.COD_FORMA_PAGO = '00080' THEN
            v_vDATOS_FORMA_PAGO := v_vDATOS_FORMA_PAGO ||
                                   v_vCADENA_FORMA_PAGO || CHR(13);
            v_vCONTADOR         := v_vCONTADOR + 1;
          END IF;
        
        END IF;
      ELSE
        -- NO ES COPAGO
      
        v_vDATOS_FORMA_PAGO := v_vDATOS_FORMA_PAGO || v_vCADENA_FORMA_PAGO ||
                               CHR(13);
        v_vCONTADOR         := v_vCONTADOR + 1;
      END IF;
    
    END LOOP;
  
    v_vPES_AT   := v_vPES_AT || v_vDATOS_FORMA_PAGO;
    v_vCONTADOR := v_vCONTADOR + 1;
  
    IF cTipoClienteConvenio = DOC_BENEFICIARIO or
       cTipoClienteConvenio = DOC_EMPRESA THEN
    
      --OBTENER CODIGO DE CONVENIO
      SELECT PEDIDO_C.COD_CONVENIO, PEDIDO_C.TIP_PED_VTA
        INTO v_vCOD_CONVENIO, v_vTIP_PED_VTA
        FROM VTA_PEDIDO_VTA_CAB PEDIDO_C
       WHERE PEDIDO_C.COD_GRUPO_CIA = cGrupoCia
         AND PEDIDO_C.COD_LOCAL = cCodLocal
         AND PEDIDO_C.NUM_PED_VTA = cNumPedidoVta;
    
      -- OBTENER DESCRIPCION DEL CONVENIO
    
      SELECT 'PESD|' || v_vCONTADOR || '|Convenio: ' || CONV.DES_CONVENIO ||
             CHR(13),
             CONV.COD_TIPO_CONVENIO
        INTO v_vDES_CONVENIO, v_vCOD_TIPO_CONVENIO
        FROM MAE_CONVENIO CONV
       WHERE CONV.COD_CONVENIO = v_vCOD_CONVENIO;
      -- CONCATENAR LOS DATOS
      v_vPES_AT := v_vPES_AT || v_vDES_CONVENIO;
    
      ---- DATOS ADICIONALES
      for REG in (
                  
                  select 'PESD|' || v_vCONTADOR || '|' || BEN.NOMBRE_CAMPO || ': ' ||
                          BEN.DESCRIPCION_CAMPO || CHR(13) AS VALOR
                  
                    from con_btl_mf_ped_vta BEN
                   WHERE BEN.COD_GRUPO_CIA = cGrupoCia
                     AND BEN.COD_LOCAL = cCodLocal
                     AND BEN.NUM_PED_VTA = cNumPedidoVta
                     AND BEN.COD_CONVENIO = v_vCOD_CONVENIO) LOOP
        v_vDATOS_ADICIONALES := v_vDATOS_ADICIONALES || REG.VALOR;
      
        v_vCONTADOR := v_vCONTADOR + 1;
      
      END LOOP;
    
      ----PARA EMPRESA
      IF cTipoClienteConvenio = DOC_EMPRESA THEN
        IF v_vTIP_PED_VTA = '03' THEN
          --VENTA MAYORISTA
        
          SELECT 'PESD|' || v_vCONTADOR || '|Direccion: ' ||
                 TRIM(COMP.DIREC_IMPR_COMP) || CHR(13) || 'PESD|' ||
                 v_vCONTADOR || '|Poliza: ' || '|' ||
                 (SELECT POL.COD_POLIZA || '-' || POL.NOMBRE_CLIENTE_POLIZA
                    FROM VTA_PEDIDO_VTA_CAB_EMP POL
                   WHERE POL.COD_GRUPO_CIA = cGrupoCia
                     AND POL.COD_LOCAL = cCodLocal
                     AND POL.NUM_PED_VTA = cNumPedidoVta) || CHR(13) --OBTENER DATOS DE LA POLIZA
            INTO v_vDATO_COMP_PAGO
            FROM VTA_COMP_PAGO COMP
           WHERE COMP.COD_GRUPO_CIA = cGrupoCia
             AND COMP.COD_LOCAL = cCodLocal
             AND COMP.NUM_PED_VTA = cNumPedidoVta
             AND COMP.SEC_COMP_PAGO = cSecCompPago;
             --AND COMP.TIP_COMP_PAGO = cTipoDocumento;
        
        ELSE
          --OTROS
          SELECT DECODE(NVL(COMP.VAL_BRUTO_COMP_PAGO, 0),0,'',('PESD|' || v_vCONTADOR || '|Venta Total: S/.' ||
                 TRIM(TO_CHAR(NVL(COMP.VAL_BRUTO_COMP_PAGO, 0),
                              '999999990.00'))|| CHR(13))) || 'PESD|' ||
                 v_vCONTADOR || '|INSTITUCION-' || COMP.PCT_EMPRESA || '%' ||
                 CHR(13) || DECODE(NVL(COMP.PCT_BENEFICIARIO,0),0,'',('PESD|' || v_vCONTADOR || '|DOC. REF.- ' ||
                 COMP.PCT_BENEFICIARIO || '%: ' ||
                 (SELECT NVL(COMPE.NUM_COMP_PAGO_E, 0)
                    FROM VTA_COMP_PAGO COMPE
                   WHERE COMPE.COD_GRUPO_CIA = COMP.COD_GRUPO_CIA
                     AND COMPE.COD_LOCAL = COMP.COD_LOCAL
                     AND COMPE.NUM_PED_VTA = COMP.NUM_PED_VTA
                     AND COMPE.NUM_COMP_PAGO = COMP.NUM_COMP_COPAGO_REF) ||
                 '- S/.' || TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO, 0),
                                         '999999990.00')) || CHR(13)))
          
            INTO v_vDATO_COMP_PAGO
            FROM VTA_COMP_PAGO COMP
           WHERE COMP.COD_GRUPO_CIA = cGrupoCia
             AND COMP.COD_LOCAL = cCodLocal
             AND COMP.NUM_PED_VTA = cNumPedidoVta
             AND COMP.SEC_COMP_PAGO = cSecCompPago;
            -- AND COMP.TIP_COMP_PAGO = cTipoDocumento;
        END IF;
      ELSE
        ----PARA BENEFICIARIO
        IF v_vCOD_TIPO_CONVENIO != 3 THEN
          SELECT DECODE(COMP.PCT_BENEFICIARIO,0,'',('PESD|' || v_vCONTADOR || '|BENEFICIARIO -' ||
                 COMP.PCT_BENEFICIARIO || '%' || CHR(13) || 'PESD|' ||
                 v_vCONTADOR || '|DOC. REF. - ' || COMP.PCT_EMPRESA ||
                 '%: ' ||
                 (SELECT NVL(COMPE.NUM_COMP_PAGO_E, 0)
                    FROM VTA_COMP_PAGO COMPE
                   WHERE COMPE.COD_GRUPO_CIA = COMP.COD_GRUPO_CIA
                     AND COMPE.COD_LOCAL = COMP.COD_LOCAL
                     AND COMPE.NUM_PED_VTA = COMP.NUM_PED_VTA
                     AND COMPE.NUM_COMP_PAGO = COMP.NUM_COMP_COPAGO_REF) ||
                 '- S/.' || TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO, 0),
                                         '999999990.00')) || CHR(13)))
            INTO v_vDATO_COMP_PAGO
            FROM VTA_COMP_PAGO COMP
           WHERE COMP.COD_GRUPO_CIA = cGrupoCia
             AND COMP.COD_LOCAL = cCodLocal
             AND COMP.NUM_PED_VTA = cNumPedidoVta
             AND COMP.SEC_COMP_PAGO = cSecCompPago;
            -- AND COMP.TIP_COMP_PAGO = cTipoDocumento;
        
        ELSE
          -- SOLO SE EMITE UN DOCUMENTO PARA EL BENEFICIARIO
          v_vDATO_COMP_PAGO := v_vDATO_COMP_PAGO || 'PESD|' || v_vCONTADOR ||
                               '|BENEFICIARIO - 100 %' || CHR(13);
        
        END IF;
      END IF;
      v_vPES_AT := v_vPES_AT || v_vDATOS_ADICIONALES || v_vDATO_COMP_PAGO;
    END IF;

      /**IMPRESION DEL MENSAJE DE REGARGA O PRODUCTO VIRTUAL*/
      BEGIN 
        SELECT DET.COD_PROD 
        INTO vCodProdVirtual
        FROM   VTA_PEDIDO_VTA_DET DET
        WHERE  DET.COD_GRUPO_CIA = cGrupoCia
        AND    DET.COD_LOCAL = cCodLocal
        AND    DET.NUM_PED_VTA = cNumPedidoVta
        AND EXISTS  (
                          SELECT COD_PROD
                          FROM LGT_PROD_VIRTUAL A
                          WHERE A.TIP_PROD_VIRTUAL = 'R'
                          AND A.COD_GRUPO_CIA= DET.COD_GRUPO_CIA
                          AND A.COD_PROD = DET.COD_PROD
                        )
          AND NOT EXISTS (
                          SELECT COD_PROD
                          FROM LGT_PROD_RIMAC A
                          WHERE A.COD_GRUPO_CIA= DET.COD_GRUPO_CIA
                          AND A.COD_PROD = DET.COD_PROD
                          );
      EXCEPTION 
        WHEN OTHERS THEN
          vCodProdVirtual := NULL;
      END;
      IF NVL(vCodProdVirtual,'0') != '0' THEN 
        curdesc := FARMA_GRAL.CAJ_OBTIENE_MSJ_PROD_VIRT(cGrupoCia, cCodLocal, cNumPedidoVta, vCodProdVirtual);
       LOOP
        FETCH curdesc INTO
              vLinea;
        EXIT WHEN curdesc%NOTFOUND;
        END LOOP; 
      
       vLinea := REPLACE(vLinea,'�','@-');
        vLinea := REPLACE(vLinea,'|','-');
        FOR LISTA IN (
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL 
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE((
                           vLinea
                           ),'&','�')),'<','�'),'@-','</e><e>') ||'</e></coll>'),'/coll/e'))) xt ) LOOP
           v_vCONTADOR := v_vCONTADOR +1;
           v_vPES_AT := v_vPES_AT ||'PESD|'|| v_vCONTADOR||'|'||LISTA.VAL|| CHR(13);
           
        END LOOP;       
        END IF;
 
    v_vPES_AT := v_vPES_AT || 'PESD|' || v_vCONTADOR ||
                 '|Guarda tu voucher,' || CHR(13);
    v_vPES_AT := v_vPES_AT || 'PESD|' || v_vCONTADOR ||
                 '|Es el sustento para validar tu compra.' || CHR(13);
  
    RETURN v_vPES_AT;
  
  END;

  -- Author  : LTAVARA
  -- Created : 14/07/2014 06:50:35 p.m.
  -- Proposito : Obtener los datos del mensaje depues del timbre del comprobante electr�nico
  /***************************************************************************/
  FUNCTION F_VAR_TRM_GET_MSJ_AF(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0') RETURN VARCHAR2 AS
    v_vPES_DT VARCHAR2(1000) := 'PES|MensajesDt' || CHR(13);
  
  BEGIN
  
    v_vPES_DT := v_vPES_DT ||
                 'PESD|1|No se aceptan devoluciones de dinero.' || CHR(13);
    v_vPES_DT := v_vPES_DT ||
                 'PESD|2|Cambio de mercaderia unicamente dentro' || CHR(13);
    v_vPES_DT := v_vPES_DT ||
                 'PESD|3|de las 48 horas siguientes a la compra.' ||
                 CHR(13);
    v_vPES_DT := v_vPES_DT || 'PESD|4|Indispensable presentar comprobante.' ||
                 CHR(13);
    v_vPES_DT := v_vPES_DT || 'PESD|5|DELIVERY 612-5000 LAS 24 HORAS' ||
                 CHR(13);
  
    RETURN v_vPES_DT;
  
  END;

  /***************************************************************************/
  FUNCTION F_VAR_COD_SERV_PED(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cNumPedVta_in   in char,
                              cSecComPago_in  in char) return varchar2 is
    vCodServicio_in VARCHAR2(100); --btlprod.mae_conveniocod_material_sap@btlrac%type;
    nCodConvenio    vta_pedido_vta_cab.cod_convenio%type;
    nIgvComp        vta_comp_pago.val_igv_comp_pago%type;
  begin
    select C.COD_CONVENIO, cp.val_igv_comp_pago
      into nCodConvenio, nIgvComp
      from vta_pedido_vta_cab c, vta_comp_pago cp
     where cp.cod_grupo_cia = cCodGrupoCia_in
       and cp.cod_local = cCodLocal_in
       and cp.num_ped_vta = cNumPedVta_in
       and cp.sec_comp_pago = cSecComPago_in
       and c.cod_grupo_cia = cp.cod_grupo_cia
       and c.cod_local = cp.cod_local
       and c.num_ped_vta = cp.num_ped_vta;
  
    begin
      select case
               when nIgvComp = 0 then
                m.cod_material_sap_inaf
               else
                m.cod_material_sap
             end
        into vCodServicio_in
        from mae_convenio m
       where m.cod_convenio = nCodConvenio;
    exception
      when no_data_found then
        vCodServicio_in := '.';
    end;
  
    return vCodServicio_in;
  end;
  /***************************************************************************/
  FUNCTION F_CUR_SENT_COMP(cCodGrupoCia_in IN CHAR,
                           cCodCia_in      IN CHAR,
                           cCodLocal_in    IN CHAR,
                           cTipCompE_in    IN CHAR,
                           cSerieCompE_in  IN CHAR,
                           vNumCompE_in    IN VARCHAR2)
    RETURN DocumentoCursor IS
    cur DocumentoCursor;
  BEGIN
    OPEN cur FOR
      SELECT P.TIP_COMP_PAGO || '�' || P.NUM_PED_VTA || '�' ||
             P.SEC_COMP_PAGO || '�' || nvl(P.TIP_CLIEN_CONVENIO, ' ') || '�' ||
             C.TIP_COMP_PAGO || '�' || R.IP_SERVIDOR || '�' || R.PUERTO_BD || '�' ||
             R.MODO || '�' || N.NUM_RUC_CIA || '�' || NUM_COMP_PAGO_E
        FROM VTA_COMP_PAGO P
        JOIN VTA_PEDIDO_VTA_CAB C
          ON (C.COD_GRUPO_CIA = P.COD_GRUPO_CIA AND
             C.COD_LOCAL = P.COD_LOCAL AND C.NUM_PED_VTA = P.NUM_PED_VTA),
       (SELECT IP_SERVIDOR, PUERTO_BD, TIME_OUT AS MODO
                FROM PBL_CNX_REMOTO
               WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND COD_CIA = cCodCia_in
                 AND COD_LOCAL = cCodLocal_in
                 AND SERVIDOR = 'EPOS') R,
       (SELECT NUM_RUC_CIA
                FROM PBL_CIA
               WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND COD_CIA = cCodCia_in) N
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_LOCAL = cCodLocal_in
         AND P.FEC_CREA_COMP_PAGO > TRUNC(SYSDATE)
         AND P.NUM_COMP_PAGO_E IS NOT NULL
         AND P.TIP_COMP_PAGO = cTipCompE_in
         AND substr(P.NUM_COMP_PAGO_E, 1, 4) = cSerieCompE_in
         AND substr(P.NUM_COMP_PAGO_E, -8) > vNumCompE_in
       ORDER BY P.NUM_COMP_PAGO_E;
    RETURN cur;
  END;
  /* ************************************************************************ */
  FUNCTION F_VAR_DSC_PROD_SERV(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR) return varchar2 is
  
    vCadena pbl_tab_gral.LLAVE_TAB_GRAL%type;
  begin
    select C.LLAVE_TAB_GRAL
      into vCadena
      from pbl_tab_gral c
     where c.id_tab_gral = 615;
     DBMS_OUTPUT.put_line(cCodGrupoCia_in);
     DBMS_OUTPUT.put_line(cCodLocal_in);     
    return vCadena;
  end;
  /***************************************************************************/
  FUNCTION F_VAR_GET_EQV(cCodMaestro_in IN VARCHAR2, cValor1 IN VARCHAR2)
    return varchar2 is
  
    v_vEquivalente MAESTRO_DETALLE.EQUIVALENTE1%type;
  begin
    BEGIN
      SELECT EQUIVALENTE1
        INTO v_vEquivalente
        FROM MAESTRO_DETALLE
       WHERE COD_MAESTRO = cCodMaestro_in
         AND VALOR1 = cValor1;
    
    EXCEPTION
      WHEN OTHERS THEN
        v_vEquivalente := '0';
    END;
    RETURN v_vEquivalente;
  end;
/* ************************************************************************ */
FUNCTION F_VAR_GET_FORMAT_COMP_E(NUMERO_COMP IN VARCHAR2) RETURN VARCHAR2 IS
  NUM_COMP_E VARCHAR2(20);
BEGIN
  SELECT TRIM(SUBSTR(NUMERO_COMP, 1, INSTR(NUMERO_COMP, '-') - 1)) || --||'-'||-- sin "-"
         TRIM(TO_CHAR(SUBSTR(NUMERO_COMP, 6, 13), '00000000'))
    INTO NUM_COMP_E
    FROM DUAL;
  RETURN NUM_COMP_E;
END;
/* ************************************************************************ */

FUNCTION F_NUM_INST_TRM_MSJ_NUM_E(
                            cGrupoCia VARCHAR2,
                            cCodLocal VARCHAR2,
                            cNumPedidoVta VARCHAR2,
                            cSecCompPago VARCHAR2,
                            cMsjNumE VARCHAR2)
        RETURN NUMBER AS
       PRAGMA AUTONOMOUS_TRANSACTION;
        v_vRespuesta number := 0;

    BEGIN
        BEGIN

               INSERT INTO PTOVENTA.VTA_COMP_MSJ_EPOS 
               (cod_grupo_cia, cod_local, num_ped_vta, sec_comp_pago, msj_num_e, msj_conf_e,
                fech_msj_num_e ,fech_msj_conf_e )
                VALUES 
                (cGrupoCia,cCodLocal,cNumPedidoVta,cSecCompPago,cMsjNumE,null,systimestamp,null);
                v_vRespuesta := 1;
                COMMIT;
      EXCEPTION
     WHEN OTHERS THEN
           ROLLBACK;
       v_vRespuesta:= 0;
        END;
        RETURN v_vRespuesta;
END ;


FUNCTION F_NUM_INST_TRM_MSJ_CONF_E(
                            cGrupoCia VARCHAR2,
                            cCodLocal VARCHAR2,
                            cNumPedidoVta VARCHAR2,
                            cSecCompPago VARCHAR2,
                            cMsjConfE VARCHAR2)
        RETURN NUMBER AS
       PRAGMA AUTONOMOUS_TRANSACTION;
        v_vRespuesta number := 0;

    BEGIN
        BEGIN

               update PTOVENTA.VTA_COMP_MSJ_EPOS 
               set  msj_conf_e = cMsjConfE,
                    fech_msj_conf_e = systimestamp
               where cod_grupo_cia =cGrupoCia
               and   cod_local = cCodLocal
               and   num_ped_vta = cNumPedidoVta
               and   sec_comp_pago = cSecCompPago;
               
                v_vRespuesta := 1;
                COMMIT;
      EXCEPTION
     WHEN OTHERS THEN
           ROLLBACK;
       v_vRespuesta:= 0;
        END;
        RETURN v_vRespuesta;
END ;

/* ************************************************************************* */
FUNCTION F_VAR_GET_NUM_COMP_E(cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2
                                  )
   RETURN VARCHAR2 AS
        v_vNumCompE VARCHAR2(20) := NULL;

    BEGIN

        BEGIN


         SELECT PAGO.NUM_COMP_PAGO_E
                into v_vNumCompE
         FROM VTA_COMP_PAGO PAGO
         WHERE
                PAGO.COD_GRUPO_CIA=cGrupoCia
            AND
                PAGO.COD_LOCAL=cCodLocal
            AND
                PAGO.NUM_PED_VTA=cNumPedidoVta
            AND
                PAGO.SEC_COMP_PAGO= cSecCompPago;
         END;
            RETURN v_vNumCompE;

    END ;
/* **************************************************************************** */
 FUNCTION    F_VAR_IS_ACT_FUNC_E(cGrupoCia VARCHAR2,
                                  cCodCia VARCHAR2,
                                  cCodLocal VARCHAR2
                                  )
   RETURN VARCHAR2 AS
        v_vIndicador CHAR(1) := 'N';

    BEGIN

        BEGIN

         SELECT LOCAL.IND_ELECTRONICO
         INTO v_vIndicador
         FROM PBL_LOCAL LOCAL
         WHERE
                LOCAL.COD_GRUPO_CIA=cGrupoCia
            AND
                LOCAL.COD_CIA=cCodCia
            AND
                LOCAL.COD_LOCAL=cCodLocal ;


           EXCEPTION
            WHEN OTHERS THEN
               v_vIndicador := 'N';
            END;
            RETURN v_vIndicador;

    END     ;

/* ************************************************************** */
 FUNCTION    F_VAR_UPD_FLAG_COMP_E(cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedVta VARCHAR2
                                  )
   RETURN VARCHAR2 AS
        v_vOk CHAR(1) := '1';
    -- DUBILLUZ 14.05.2014 --
        vIndCompManual vta_pedido_vta_cab.ind_comp_manual%type;

    BEGIN

    -- DUBILLUZ 14.05.2014 --
    select nvl(ca.ind_comp_manual,'N')
    into   vIndCompManual
    from   vta_pedido_vta_cab ca
    where  ca.cod_grupo_cia = cGrupoCia
    and    ca.cod_local = cCodLocal
    and    ca.num_ped_vta = cNumPedVta;

    -- DUBILLUZ 14.05.2014 --
    if vIndCompManual = 'N' then
        BEGIN

         UPDATE VTA_COMP_PAGO PAGO
         SET PAGO.COD_TIP_PROC_PAGO='1'
         WHERE
                PAGO.TIP_COMP_PAGO !='03' -- GUIA NO SE ACTUALIZA
           AND
                PAGO.COD_GRUPO_CIA=cGrupoCia
            AND
                PAGO.COD_LOCAL=cCodLocal
            AND
                PAGO.NUM_PED_VTA=cNumPedVta;
           EXCEPTION
            WHEN OTHERS THEN
               v_vOk := '0';
            END;
     end if;
            RETURN v_vOk;

    END;
/* **********************************************************************/

  FUNCTION  F_VAR_GET_TIP_PROC_CPAGO(cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedVta VARCHAR2,
                                  cNumCompPago VARCHAR2
                                  )
   RETURN VARCHAR2 AS
        v_vCodTipProcPago VARCHAR2(1) := '0';

    BEGIN

        BEGIN


         SELECT PAGO.COD_TIP_PROC_PAGO
                into v_vCodTipProcPago
         FROM VTA_COMP_PAGO PAGO
         WHERE
                PAGO.COD_GRUPO_CIA=cGrupoCia
            AND
                PAGO.COD_LOCAL=cCodLocal
            AND
                PAGO.NUM_PED_VTA=cNumPedVta
            AND
                PAGO.NUM_COMP_PAGO_E=cNumCompPago;
           EXCEPTION
            WHEN OTHERS THEN
               v_vCodTipProcPago := '0';
            END;
            RETURN v_vCodTipProcPago;

    END ;
    
    
      procedure FN_MODIFICAR_DOCUMENTO_E(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cNumCompPagoE VARCHAR2,
                                  pdf417 VARCHAR2,
                                  pTipCompPago varchar2) is
       PRAGMA AUTONOMOUS_TRANSACTION;
        v_vRespuesta number := 1;--exito

    BEGIN
                 -- KMONCADA 02.10.2014 ACTUALIZA NUM COMP ELECTROINCOS EN TEMP
                 UPDATE PTOVENTA.VTA_COMP_PAGO_TEMP A
                 SET    A.NUM_COMP_PAGO_E = REPLACE(cNumCompPagoE,'-',''),
                        A.COD_TIP_PROC_PAGO = '1'
                 WHERE  A.COD_GRUPO_CIA=cGrupoCia
                 AND    A.COD_LOCAL=cCodLocal
                 AND    A.NUM_PED_VTA=cNumPedidoVta
                 AND    A.SEC_COMP_PAGO= cSecCompPago;
                 
                 update PTOVENTA.VTA_COMP_MSJ_EPOS  PAGO
                  SET PAGO.NUM_COMP_PAGO_E= cNumCompPagoE,
                            PAGO.COD_PDF417_E= pdf417,
                            PAGO.COD_TIP_PROC_PAGO='1' ,
                            -- DUBILLUZ 03.10.2014
                            PAGO.TIP_COMP_PAGO = (CASE
                                                   WHEN pTipCompPago = '05' THEN '01'
                                                   WHEN pTipCompPago = '06' THEN '02'
                                                   ELSE pTipCompPago
                                                  END
                                                  ),
                            pago.fech_upd_conf_e = systimestamp
                        WHERE PAGO.COD_GRUPO_CIA=cGrupoCia
                        AND   PAGO.COD_LOCAL=cCodLocal
                        AND   PAGO.NUM_PED_VTA=cNumPedidoVta
                        AND   PAGO.SEC_COMP_PAGO= cSecCompPago;
                        
                 COMMIT;
      EXCEPTION
     WHEN OTHERS THEN
           ROLLBACK;
           RAISE_APPLICATION_ERROR(-20005,'ERRPR '||'/'||SQLERRM);
    END ;

    
    
      procedure FN_MODIFICAR_DOC_NUM_E(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cNumCompPagoE VARCHAR2,
                                  pdf417 VARCHAR2,
                                  pTipCompPago varchar2) is
        v_vRespuesta number := 1;--exito

    BEGIN
                /*
                01  BOLETA
                02  FACTURA
                03  GUIA
                04  NOTA CREDITO
                05  TICKET BOLETA
                06  TICKET FACTURA
                */

                
                 UPDATE PTOVENTA.VTA_COMP_PAGO PAGO
                        SET PAGO.NUM_COMP_PAGO_E= cNumCompPagoE,
                            PAGO.COD_PDF417_E= pdf417,
                            PAGO.COD_TIP_PROC_PAGO='1' ,
                            -- DUBILLUZ 03.10.2014
                            PAGO.TIP_COMP_PAGO = (CASE
                                                   WHEN pTipCompPago = '05' THEN '01'
                                                   WHEN pTipCompPago = '06' THEN '02'
                                                   ELSE pTipCompPago
                                                  END
                                                  )
                        WHERE
                            PAGO.COD_GRUPO_CIA=cGrupoCia
                        AND
                            PAGO.COD_LOCAL=cCodLocal
                        AND
                            PAGO.NUM_PED_VTA=cNumPedidoVta
                        AND
                            PAGO.SEC_COMP_PAGO= cSecCompPago;
    END ;

    
    
    procedure sp_upd_comp_pago_e(p_cod_grupo_cia      varchar2,
                                                       p_cod_local          varchar2,
                                                       p_num_ped_vta        varchar2,
                                                       p_sec_com_pago       varchar2,
                                                       p_tip_clien_convenio varchar2 default null
                                                       ) is
   vIsCompEmpresa_COPAGO char(1) := 'N';
   nExiste int;
   vCtdTipoClienteCompPAgo number;
   vSegundaActualiza CHAR(1);
   vTipCompPagoSeg VTA_PEDIDO_VTA_CAB.TIP_COMP_PAGO%TYPE;
  begin

             --RAISE_APPLICATION_ERROR(-20999,p_cod_grupo_cia||'-'||p_cod_local||'-'||p_num_ped_vta||'-'||p_sec_com_pago);

      
      
      /* **************************************************************************** */
      update vta_pedido_vta_det D
      SET   (val_vta_unit_item_e,
            val_total_desc_item_e,
            val_vta_item_e) =
                             (
                             -- LTAVARA 26.12.2014 PROMOCIONES
                              SELECT /*DECODE(D.COD_TIP_AFEC_IGV_E, '31', 
                                            (CASE 
                                              WHEN NVL(D.VAL_PREC_PUBLIC,0)=0 THEN 0.01
                                              ELSE D.VAL_PREC_PUBLIC
                                            END)
                                            ,GG.UNIT_NUEVO)*/
                                -- KMONCADA 08.04.2015 NO REALIZARA VALIDACION DE PROMOCIONES
                                GG.UNIT_NUEVO,
                                GG.AHORRO_NUEVO,
                                -- KMONCADA 08.04.2015 CASO DE PROMOCIONES CUADRE DE MONTOS
                                CASE 
                                  WHEN GG.TOTAL_NUEVO <0 THEN 
                                    0
                                  ELSE 
                                    GG.TOTAL_NUEVO
                                END
                                  FROM   (
                                  select SEC_PED_VTA_DET,
                                         COD_PROD,
                                         -------------------
                                         NVL((CASE
                                           WHEN TOT_S_IGV < VAL_AFECTO_COMP_PAGO -
                                                NVL(LAG(D) OVER(ORDER BY SEC_PED_VTA_DET), 0) THEN
                                            TOT_S_IGV
                                           ELSE
                                            VAL_AFECTO_COMP_PAGO - LAG(D) OVER(ORDER BY SEC_PED_VTA_DET)
                                         END),VAL_AFECTO_COMP_PAGO) TOTAL_NUEVO,
                                         ROUND(AHORRO / (1 + VAL_IGV / 100), 2) AHORRO_NUEVO,
                                         ((NVL((CASE
                                           WHEN TOT_S_IGV < VAL_AFECTO_COMP_PAGO -
                                                NVL(LAG(D) OVER(ORDER BY SEC_PED_VTA_DET), 0) THEN
                                            TOT_S_IGV
                                           ELSE
                                            VAL_AFECTO_COMP_PAGO - LAG(D) OVER(ORDER BY SEC_PED_VTA_DET)
                                         END),VAL_AFECTO_COMP_PAGO) + ROUND(AHORRO / (1 + VAL_IGV / 100), 2)) / CANT_ATENDIDA) UNIT_NUEVO
                                    from (SELECT CP.VAL_AFECTO_COMP_PAGO,
                                                 D.SEC_PED_VTA_DET,
                                                 D.COD_PROD,
                                                 --D.CANT_ATENDIDA,
                                                 DECODE(CA.TIP_PED_VTA, TIPO_VTA_INSTITUCIONAL,
                                                 DECODE(
                                                  MOD(ABS(D.CANT_ATENDIDA),ABS(D.VAL_FRAC)),0,
                                                  ABS(D.CANT_ATENDIDA)/ABS(D.VAL_FRAC),
                                                  ABS(D.CANT_ATENDIDA)
                                                       ),D.CANT_ATENDIDA )AS "CANT_ATENDIDA",
                                                 D.VAL_PREC_VTA,
                                                 D.VAL_PREC_TOTAL,
                                                 D.AHORRO,
                                                 D.VAL_IGV,
                                                 ROUND(D.VAL_PREC_TOTAL / (1 + D.VAL_IGV / 100), 2) TOT_S_IGV,
                                                 SUM(ROUND(D.VAL_PREC_TOTAL / (1 + D.VAL_IGV / 100), 2)) OVER(ORDER BY D.SEC_PED_VTA_DET asc) d
                                            FROM vta_pedido_vta_det D, Vta_Comp_Pago CP,
                                                 VTA_PEDIDO_VTA_CAB CA
                                           WHERE D.cod_grupo_cia = p_cod_grupo_cia
                                             and D.cod_local = p_cod_local
                                             and D.num_ped_vta = p_num_ped_vta
                                             AND D.COD_GRUPO_CIA = CA.COD_GRUPO_CIA
                                             AND D.cod_local = CA.cod_local
                                             AND D.num_ped_vta = CA.num_ped_vta                                                                                          
                                             and decode(p_tip_clien_convenio, '1', D.sec_comp_pago_benef,
                                                                    '2',  D.sec_comp_pago_empre,
                                                                    D.sec_comp_pago) =p_sec_com_pago
                                             AND D.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                                             AND D.COD_LOCAL = CP.COD_LOCAL
                                             AND D.NUM_PED_VTA = CP.NUM_PED_VTA
                                             AND CP.SEC_COMP_PAGO = p_sec_com_pago
                                             ) 
                                    )gg
                                  WHERE  GG.SEC_PED_VTA_DET = D.SEC_PED_VTA_DET
                                  AND    GG.COD_PROD = D.COD_PROD
                                 )
      where cod_grupo_cia = p_cod_grupo_cia
               and cod_local = p_cod_local
               and num_ped_vta = p_num_ped_vta
               and decode(p_tip_clien_convenio, '1', sec_comp_pago_benef,
                                                '2',  sec_comp_pago_empre,
                                                sec_comp_pago) =p_sec_com_pago;

      /* **************************************************************************** */
      
      update vta_pedido_vta_det
         set -- val_prec_vta_unit_e = decode(cod_tip_afec_igv_e, '31', val_prec_public, (((val_prec_total + ahorro) / cant_atendida) * val_frac)),--23.12.2014
             val_prec_vta_unit_e =   
                                    decode(cod_tip_afec_igv_e, '31', 
                                          
                                          (CASE 
                                              WHEN NVL(VAL_PREC_PUBLIC,0)=0 THEN 0.01
                                              ELSE VAL_PREC_PUBLIC
                                            END),
                                          
                                          --val_prec_vta
                                          round((val_vta_unit_item_e * (1+(val_igv/100))),2)
                                          ),
             cant_unid_vdd_e = cant_atendida,--trunc((cant_atendida / val_frac), 3),--23.12.2014
             val_total_igv_item_e = round(val_prec_total / (1 + (val_igv / 100)) * (val_igv / 100), 2)
             -- Estos eran UNitario, Descuento y Sub TOTAL
             -- DUBILLUZ 23.12.2014
             /*
             ,val_vta_unit_item_e = 
                                 round(decode(cod_tip_afec_igv_e, '31', val_prec_public, 
                                              (((val_prec_total + ahorro) / cant_atendida) * val_frac)-- precio entero
                                              ) /
                                              decode(val_igv, 0, 1, (1 + (val_igv / 100)))
                                       , 3),
             val_total_desc_item_e = round((ahorro / (1 + (val_igv / 100))), 2),
             val_vta_item_e = round(val_prec_total / (1 + (val_igv / 100)), 2)*/
             -- Estos eran UNitario, Descuento y Sub TOTAL
             -- DUBILLUZ 23.12.2014             
       where cod_grupo_cia = p_cod_grupo_cia
         and cod_local = p_cod_local
         and num_ped_vta = p_num_ped_vta
         and decode(p_tip_clien_convenio, '1', sec_comp_pago_benef,
                                          '2',  sec_comp_pago_empre,
                                          sec_comp_pago) =p_sec_com_pago;
      
      
      /****/
      
                                                                 

      update vta_comp_pago a
         set (val_total_e,
              total_grav_e,
              total_inaf_e,
              total_gratu_e,
              total_exon_e,
              total_desc_e,
              total_igv_e) =
             (select sum(val_vta_item_e) val_total_e,
                      sum(decode(cod_tip_afec_igv_e, '10', val_vta_item_e, 0)) total_grav_e,
                     sum(decode(cod_tip_afec_igv_e, '30', val_vta_item_e, 0)) total_inaf_e,
                     --sum(decode(cod_tip_afec_igv_e, '31', val_vta_item_e, 0)) total_gratu_e,
                     -- KMONCADA 08.04.2015 CASO DE PROMOCIONES VALIDA POR VALOR VTA DEL PROD.
                     --sum(decode(cod_tip_afec_igv_e, '31', round(val_vta_unit_item_e * cant_unid_vdd_e, 2), 0)) total_gratu_e,
                     sum(decode(cod_tip_afec_igv_e, '31', round(VAL_PREC_VTA_UNIT_E * cant_unid_vdd_e, 2), 0)) total_gratu_e,
                     sum(decode(cod_tip_afec_igv_e, '20', val_vta_item_e, 0)) total_exon_e,
                     sum(val_total_desc_item_e) total_desc_e,
                     sum(val_total_igv_item_e) total_igv_e
                from vta_pedido_vta_det x
               where x.cod_grupo_cia = a.cod_grupo_cia
                 and x.cod_local = a.cod_local
                 and x.num_ped_vta = a.num_ped_vta
                 and decode(a.tip_clien_convenio,
                            '1', x.sec_comp_pago_benef,
                            '2', x.sec_comp_pago_empre,
                            x.sec_comp_pago) = a.sec_comp_pago)
       where a.cod_grupo_cia = p_cod_grupo_cia
         and a.cod_local = p_cod_local
         and a.num_ped_vta = p_num_ped_vta
         and a.sec_comp_pago = trim(p_sec_com_pago);
         
         -- dubilluz 07.10.2014
         -- Verifica si Existen 2 Tipos de Comprobantes para EMPRESA y BENEFICIARIO.
         -- PARA ESTO TIENE QUE HABERSE GRABADO TODOS LOS COMPROBANTES del PEDIDO
         SELECT count(distinct nvl(pago.tip_clien_convenio,'1'))
         INTO   vCtdTipoClienteCompPago
         FROM   vta_comp_pago pago
       where cod_grupo_cia = p_cod_grupo_cia
         and cod_local = p_cod_local
         and num_ped_vta = p_num_ped_vta;

         -- dubilluz 03.10.2014
         SELECT (CASE
                   WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND
                        nvl(PAGO.PCT_BENEFICIARIO,0) > 0  AND nvl(PAGO.PCT_EMPRESA,0) > 0
                        THEN 'S'
                   ELSE
                        'N'
                  END)
         INTO   vIsCompEmpresa_COPAGO
         FROM   vta_comp_pago pago
       where cod_grupo_cia = p_cod_grupo_cia
         and cod_local = p_cod_local
         and num_ped_vta = p_num_ped_vta
         and sec_comp_pago =p_sec_com_pago;


         -- dubilluz 03.10.2014
    /*if  vIsCompEmpresa_COPAGO = 'S' then
       update vta_comp_pago
               set cod_tip_ident_recep_e = decode(length(trim(num_doc_impr)),
                                                    11,
                                                    '6',
                                                    8,
                                                    '1',
                                                    '0'),
                   val_copago_e = nvl((val_copago_comp_pago/(1+(porc_igv_comp_pago/100))), 0)
             where cod_grupo_cia = p_cod_grupo_cia
               and cod_local = p_cod_local
               and num_ped_vta = p_num_ped_vta
               and sec_comp_pago =p_sec_com_pago;
    else*/
      update vta_comp_pago
         set cod_tip_ident_recep_e = decode(length(trim(num_doc_impr)),
                                              11,
                                              '6',
                                              8,
                                              '1',
                                              '0'),
             val_total_e = decode(vCtdTipoClienteCompPago,1,1,decode(tip_clien_convenio  , '1',(pct_beneficiario/100), '2', (pct_empresa/100), 1)) * val_total_e,
             total_grav_e = decode(vCtdTipoClienteCompPago,1,1,decode(tip_clien_convenio , '1',(pct_beneficiario/100), '2', (pct_empresa/100), 1)) * total_grav_e,
             total_inaf_e = decode(vCtdTipoClienteCompPago,1,1,decode(tip_clien_convenio , '1',(pct_beneficiario/100), '2', (pct_empresa/100), 1)) * total_inaf_e,
             total_gratu_e = decode(vCtdTipoClienteCompPago,1,1,decode(tip_clien_convenio, '1',(pct_beneficiario/100), '2', (pct_empresa/100), 1)) * total_gratu_e,
             total_exon_e = decode(vCtdTipoClienteCompPago,1,1,decode(tip_clien_convenio , '1',(pct_beneficiario/100), '2', (pct_empresa/100), 1)) * total_exon_e,
             total_desc_e = decode(vCtdTipoClienteCompPago,1,1,(decode(tip_clien_convenio, '1',(pct_beneficiario/100), '2', (pct_empresa/100), 1))) * total_desc_e ,
             val_copago_e = nvl((val_copago_comp_pago/(1+(porc_igv_comp_pago/100))), 0),
             total_igv_e = decode(vCtdTipoClienteCompPago,1,1,decode(tip_clien_convenio, '1', (pct_beneficiario/100), '2', (pct_empresa/100), 1)) * total_igv_e
       where cod_grupo_cia = p_cod_grupo_cia
         and cod_local = p_cod_local
         and num_ped_vta = p_num_ped_vta
         and sec_comp_pago =p_sec_com_pago;
		 -- KMONCADA 09.04.2015 PARA EL CASO DE CP. CON TIPO 99 Y NRO DE DOC RUC.
     SELECT 
       CASE 
         WHEN TIP_COMP_PAGO = '99' AND length(trim(num_doc_impr)) = 11 THEN
           'S'
         ELSE
           'N'
       END
     INTO vSegundaActualiza
     FROM VTA_COMP_PAGO
     WHERE COD_GRUPO_CIA = p_cod_grupo_cia
     AND COD_LOCAL = p_cod_local
     AND NUM_PED_VTA = p_num_ped_vta
     AND SEC_COMP_PAGO =p_sec_com_pago; 
     
     IF vSegundaActualiza = 'S' THEN
       SELECT TIP_COMP_PAGO
       INTO vTipCompPagoSeg
       FROM VTA_PEDIDO_VTA_CAB
       WHERE COD_GRUPO_CIA = p_cod_grupo_cia
       AND COD_LOCAL = p_cod_local
       AND NUM_PED_VTA = p_num_ped_vta; 
       
       IF vTipCompPagoSeg = '01' THEN
         UPDATE VTA_COMP_PAGO
         SET cod_tip_ident_recep_e = '1'
         WHERE COD_GRUPO_CIA = p_cod_grupo_cia
         AND COD_LOCAL = p_cod_local
         AND NUM_PED_VTA = p_num_ped_vta
         AND SEC_COMP_PAGO =p_sec_com_pago;   
       END IF;
     END IF;
         
         
	  --ERIOS 03.03.2015 El modifica el calculo de subtotal
    -- KMONCADA 2015.03.19 SE DETERMINA A QUE CAMPO AFECTO/INAFECTO EFECTUA LA OPERACION
    update vta_comp_pago
       set total_grav_e = CASE
                            WHEN VAL_IGV_COMP_PAGO = 0 THEN
                             0
                            ELSE
                             (VAL_NETO_COMP_PAGO - VAL_IGV_COMP_PAGO)
                          END,
           total_inaf_e = CASE
                            WHEN VAL_IGV_COMP_PAGO = 0 THEN
                             (VAL_NETO_COMP_PAGO - VAL_IGV_COMP_PAGO)
                            ELSE
                             0
                          END,
           total_igv_e  = VAL_IGV_COMP_PAGO
     where cod_grupo_cia = p_cod_grupo_cia
       and cod_local = p_cod_local
       and num_ped_vta = p_num_ped_vta
       and sec_comp_pago = p_sec_com_pago;
      /*
      update vta_comp_pago
         set cod_tip_ident_recep_e = decode(length(trim(num_doc_impr)),
                                              11,
                                              '6',
                                              8,
                                              '1',
                                              '0'),
             val_total_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * val_total_e,
             total_grav_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_grav_e,
             total_inaf_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_inaf_e,
             total_gratu_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_gratu_e,
             total_exon_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_exon_e,
             -- DUBILLUZ 03.10.2014
             --total_desc_e = (decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_desc_e) + nvl((val_copago_comp_pago/(1+(porc_igv_comp_pago/100))), 0),
             total_desc_e = (decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_desc_e) ,
             val_copago_e = nvl((val_copago_comp_pago/(1+(porc_igv_comp_pago/100))), 0),
             -- DUBILLUZ 03.10.2014
             total_igv_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_igv_e
       where cod_grupo_cia = p_cod_grupo_cia
         and cod_local = p_cod_local
         and num_ped_vta = p_num_ped_vta
         and sec_comp_pago =p_sec_com_pago;
       */  
     --end if;
  end sp_upd_comp_pago_e;


FUNCTION FN_INDICADOR_ELECTRONICO(cCodGrupoCia VARCHAR2,cCodLocal VARCHAR2,cNumPedidoVta VARCHAR2, cSecComPago VARCHAR2)

   RETURN CHAR
   IS

   v_Indicador VARCHAR2(10);
   v_CodTipoProcPago VTA_COMP_PAGO.COD_TIP_PROC_PAGO%TYPE;

   BEGIN
   --v_Indicador:='FALSE';
   SELECT NVL(X.COD_TIP_PROC_PAGO,0) INTO  v_CodTipoProcPago FROM  VTA_COMP_PAGO  X WHERE
   X.COD_GRUPO_CIA=cCodGrupoCia  AND
   X.COD_LOCAL=cCodLocal  AND
   X.NUM_PED_VTA=cNumPedidoVta AND
   X.SEC_COMP_PAGO=cSecComPago;

   IF v_CodTipoProcPago='1' THEN
   v_Indicador:='TRUE';
   ELSE
   v_Indicador:='FALSE';

   END IF;


   RETURN v_Indicador;

   EXCEPTION
          WHEN NO_DATA_FOUND THEN
   v_Indicador:='FALSE';

  RETURN v_Indicador;

   END;
    -- Author  : CESAR HUANES
    -- Created : 20/08/2014 03:09:37 p.m.
    -- Proposito : Cuenta la Cantidad de Documento electronicos por pedido

  FUNCTION FN_CONTAR_DOCELECTRONICO (cCodGrupoCia VARCHAR2,cCodLocal VARCHAR2,cNumPedidoVta VARCHAR2 )
   RETURN INTEGER
   IS
   v_Contador INTEGER;
   BEGIN
   SELECT COUNT(X.COD_TIP_PROC_PAGO) INTO v_Contador  FROM VTA_COMP_PAGO X
   WHERE X.COD_GRUPO_CIA=cCodGrupoCia
    AND X.COD_LOCAL=cCodLocal AND
     X.NUM_PED_VTA=cNumPedidoVta ;
  RETURN  v_Contador;
  EXCEPTION
          WHEN NO_DATA_FOUND THEN
  v_Contador:=0;
  RETURN  v_Contador;
   END;
  
  /* **************************************************************************** */
  /* ***************************************************************************** */


  /*FUNCTION IMP_COMP_ELECT ( vCodGrupoCia_in VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                            vCodLocal_in    VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                            vNumPedVta_in   VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                            vSecCompPago_in VTA_PEDIDO_VTA_CAB.SEC_COMP_PAGO%TYPE,
                            vVersion_in     REL_APLICACION_VERSION.NUM_VERSION%TYPE,
                            vReimpresion    CHAR DEFAULT 'N',
                            valorAhorro_in in number --ASOSA - 24/03/2015 - PTOSYAYAYAYA - SE DEFINIO QUE ESTUVIERA EN JAVA X ESO ES PARAMETRO
                           )
  RETURN FARMACURSOR
  IS 
                                             
   vCopagoEmpresa       CHAR(1);
   vCopagoCliente       CHAR(1);
   vTipoDocumento       VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE;
   vTipoDocRefer        CHAR(1);
   vTip_Ped_vta         VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE;
   vTipoClienteConvenio VTA_COMP_PAGO.TIP_CLIEN_CONVENIO%TYPE;
   vMontoAhorro         CHAR(20);
   vPedidoConvenio      VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE;
   vCodProdVirtual      VTA_PEDIDO_VTA_DET.COD_PROD%TYPE;
   curdesc              FARMACURSOR;
      curdesc02              FARMACURSOR;                --ASOSA - 13/01/2015 - RIMAC
      vIndRimac              char(1) := 'N';               --ASOSA - 13/01/2015 - RIMAC
   vLinea               VARCHAR2(4000);
   vPDF417              VTA_COMP_PAGO.COD_PDF417_E%TYPE;
   cCantidadCupon       INTEGER DEFAULT 0;
   cursorComprobante    FARMACURSOR;
   vSecMovCaja          VTA_PEDIDO_VTA_CAB.SEC_MOV_CAJA%TYPE;
   vDescuentoNC         VTA_COMP_PAGO.VAL_COPAGO_E%TYPE;
   
   vAuxNumPedVta        VTA_COMP_PAGO.NUM_PED_VTA%TYPE;
   vAuxSecCompPago      VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
   
   --INI ASOSA - 09/02/2015 - PTOSYAYAYAYA
   
   indPermitImip CHAR(1) := '';   

   --FIN ASOSA - 18/02/2015 - PTOSYAYAYAYA
   
   --INI ASOSA - 11/05/2015 - PTOSYAYAYAYA
   cantAgregar01 number(3) := 20;
   cantAgregar02 number(3) := 10;
   cantAgregar03 number(3) := 13;
   textoAgregar varchar2(10) := ' ';   
   --FIN ASOSA - 11/05/2015 - PTOSYAYAYAYA
   
   -- dubilluz 13.01.2015
   nPermiteImp char(1);
   vReimprime CHAR(1) := 'N';
   cursorExperto    FARMACURSOR;   --ASOSA - 23/03/2015 - PTOSYAYAYAYA - CONSECUENCIA DE ESTAR CAMBIANDO VARIAS VECES
   ahorroF VARCHAR2(20) := '';
   ahorroPtoSoles       VTA_PEDIDO_VTA_DET.AHORRO_PUNTOS%TYPE;
   ahorroPtos           VTA_PEDIDO_VTA_DET.AHORRO_PUNTOS%TYPE;
   vEstadoOrbis         VTA_PEDIDO_VTA_CAB.EST_TRX_ORBIS%TYPE;
   vNroTarjetaPuntos    VTA_PEDIDO_VTA_CAB.NUM_TARJ_PUNTOS%TYPE;
   vNroDNIPto           VTA_PEDIDO_VTA_CAB.DNI_CLI%TYPE;
   vNombreClientePto    VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE;
   vPtoAcumulado        VTA_PEDIDO_VTA_CAB.PT_ACUMULADO%TYPE;
   vPtoRedimido         VTA_PEDIDO_VTA_CAB.PT_REDIMIDO%TYPE;
   vPtoTotal            VTA_PEDIDO_VTA_CAB.PT_TOTAL%TYPE;
   vPtoInicial          VTA_PEDIDO_VTA_CAB.PT_INICIAL%TYPE;    --ASOSA - 08/05/2015 - PTOSYAYAYAYA
   vSufijoAhorroSoles   VARCHAR2(200);
   vSufijoPtoAcumula    VARCHAR2(200);
   vSufijoPtoTotal      VARCHAR2(200);
   vTextoPtoInicial     VARCHAR2(200);  --ASOSA - 08/05/2015 - PTOSYAYAYAYA
   vIndProgPuntos       CHAR(3);
   vValorAhorro     NUMBER(12,3);
   v_cIndReimp CHAR(1);
  BEGIN 
	
      --ASOSA - 16/04/2015 - PTOSYAYAYAYA
      ahorroF := PTOVENTA_FIDELIZACION.FID_F_GET_AHORRO_F(vCodGrupoCia_in,
                                                          vCodLocal_in,
                                                          vNumPedVta_in,
                                                          vSecCompPago_in);  
    -- KMONCADA
    -- SELECT SUM(NVL(DET.AHORRO_PUNTOS, 0))
    SELECT SUM(NVL(DET.AHORRO, 0))
      INTO ahorroPtoSoles
      FROM VTA_PEDIDO_VTA_DET DET
     WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
       AND DET.COD_LOCAL = vCodLocal_in
       AND DET.NUM_PED_VTA = vNumPedVta_in
       AND DET.SEC_COMP_PAGO = vSecCompPago_in;
  
   -- dubilluz 13.01.2015    
    select case
           when c.tip_comp_pago = '02' and 
                (trim(c.num_doc_impr)  is null  or
                trim(c.nom_impr_comp)  is null)
           then
            'N'
           else
              'S'
           end 
    into   nPermiteImp
    from   vta_comp_pago c
    where c.cod_grupo_cia = vCodGrupoCia_in
    and   c.cod_local = vCodLocal_in
    and   c.num_ped_vta = vNumPedVta_in
    and   c.sec_comp_pago = vSecCompPago_in;
  
    
    if nPermiteImp = 'N' then
       RAISE_APPLICATION_ERROR(-20999,'Error al IMPRIMIR !\n No tiene RUC/RAZON SOCIAL la factura a IMPRIMIR!');      
    end if;
    -- dubilluz 13.01.2015  
    
    SELECT CASE 
             WHEN CP.FECH_IMP_COBRO IS NOT NULL THEN
               'S'
             ELSE
               'N'
           END,
		   NVL((SELECT DESC_CORTA FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 511),'N')
           INTO vReimprime, v_cIndReimp
    FROM VTA_COMP_PAGO CP
    WHERE CP.COD_GRUPO_CIA = vCodGrupoCia_in
    AND CP.COD_LOCAL = vCodLocal_in
    AND CP.NUM_PED_VTA = vNumPedVta_in
    AND CP.SEC_COMP_PAGO = vSecCompPago_in;
	
	IF vReimpresion = 'S' AND vReimprime = 'S' AND v_cIndReimp = 'N' THEN
	    --ERIOS 15.06.2015 Se deshabilita esta opcion por pedido de JUAN MANUEL PONCE
		RAISE_APPLICATION_ERROR(-20990,'ERROR: LA OPCION DE REIMPRESION DE COMPROBANTE ELECTRONICO, ESTA BLOQUEADO.'||
                                      CHR(13)||'COMUNIQUESE CON MESA DE AYUDA.');
	END IF;	
   
    SELECT CASE 
             WHEN CP.NUM_COMP_PAGO_E IS NOT NULL THEN
               'S'
             ELSE
               'N'
           END
           INTO nPermiteImp
    FROM VTA_COMP_PAGO CP
    WHERE CP.COD_GRUPO_CIA = vCodGrupoCia_in
    AND CP.COD_LOCAL = vCodLocal_in
    AND CP.NUM_PED_VTA = vNumPedVta_in
    AND CP.SEC_COMP_PAGO = vSecCompPago_in;
    
    if nPermiteImp = 'N' then
       RAISE_APPLICATION_ERROR(-20989,'ERROR: COMPROBANTE ELECTRONICO NO CUENTA CON NRO DE CORRELATIVO.'||
                                      CHR(13)||'COMUNIQUESE CON MESA DE AYUDA.');
    end if;
     
    delete TMP_DOCUMENTO_ELECTRONICOS;  
	-- KMONCADA 02.06.2015 EL VALOR DE AHORRO TOTAL SE 
	-- OPERARA COMO MONTO EN SOLES Y NO COMO PTOS
    SELECT  (valorAhorro_in * TO_NUMBER(TAB.DESC_CORTA,'99990.00')) / TO_NUMBER(TAB.LLAVE_TAB_GRAL,'99990.00')
    INTO vValorAhorro
    FROM PBL_TAB_GRAL TAB
    WHERE ID_TAB_GRAL = 482;
          
    IF PTOVENTA_FIDELIZACION.FID_F_GET_IND_IMPR(vCodGrupoCia_in,
                          vCodLocal_in,
                          vNumPedVta_in,
                          vSecCompPago_in,
                          vValorAhorro) = 'S' THEN
    
          cursorExperto := PTOVENTA_FIDELIZACION.FID_F_GET_TEXT_EXPERT(vCodGrupoCia_in,
                                                        vCodLocal_in,
                                                        vNumPedVta_in,
                                                        vSecCompPago_in,
                                                        vValorAhorro);
    END IF;
    
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( GET_MARCA_LOCAL(vCodGrupoCia_in , vCodLocal_in),'9','L','S','N');
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( ' ','9','D','S','N');
    
    SELECT 
      -- SI ES COPAGO EMPRESA
      CASE
        WHEN PAGO.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
          ( SELECT 
               CASE
                 WHEN B.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND nvl(B.PCT_BENEFICIARIO, 0) > 0 AND NVL(B.PCT_EMPRESA, 0) > 0 THEN
                   'S'
                 ELSE
                   'N'
                END
            FROM VTA_PEDIDO_VTA_CAB A, 
                 VTA_PEDIDO_VTA_CAB AX, 
                 VTA_COMP_PAGO B 
            WHERE A.COD_GRUPO_CIA   = PAGO.COD_GRUPO_CIA
            AND A.COD_LOCAL         = PAGO.COD_LOCAL
            AND A.NUM_PED_VTA       = PAGO.NUM_PED_VTA
            AND A.COD_GRUPO_CIA     = AX.COD_GRUPO_CIA
            AND A.COD_LOCAL         = AX.COD_LOCAL
            AND AX.NUM_PED_VTA      = A.NUM_PED_VTA_ORIGEN
            AND AX.COD_GRUPO_CIA    = B.COD_GRUPO_CIA
            AND AX.COD_LOCAL        = B.COD_LOCAL
            AND AX.NUM_PED_VTA      = B.NUM_PED_VTA
            AND B.SEC_COMP_PAGO     = A.SEC_COMP_PAGO)
        WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND nvl(PAGO.PCT_BENEFICIARIO, 0) > 0 AND NVL(PAGO.PCT_EMPRESA, 0) > 0 THEN
          'S' 
        ELSE
          'N' 
      END, 
      CASE
        WHEN PAGO.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
          ( SELECT 
               CASE
                 WHEN B.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND nvl(B.PCT_BENEFICIARIO, 0) > 0 AND NVL(B.PCT_EMPRESA, 0) > 0 THEN
                   B.VAL_COPAGO_E
                 ELSE
                   0
                END
            FROM VTA_PEDIDO_VTA_CAB A, 
                 VTA_PEDIDO_VTA_CAB AX, 
                 VTA_COMP_PAGO B 
            WHERE A.COD_GRUPO_CIA   = PAGO.COD_GRUPO_CIA
            AND A.COD_LOCAL         = PAGO.COD_LOCAL
            AND A.NUM_PED_VTA       = PAGO.NUM_PED_VTA
            AND A.COD_GRUPO_CIA     = AX.COD_GRUPO_CIA
            AND A.COD_LOCAL         = AX.COD_LOCAL
            AND AX.NUM_PED_VTA      = A.NUM_PED_VTA_ORIGEN
            AND AX.COD_GRUPO_CIA    = B.COD_GRUPO_CIA
            AND AX.COD_LOCAL        = B.COD_LOCAL
            AND AX.NUM_PED_VTA      = B.NUM_PED_VTA
            AND B.SEC_COMP_PAGO     = A.SEC_COMP_PAGO)
        ELSE
         0
      END,
      -- SI ES COPAGO CLIENTE
      CASE
        WHEN PAGO.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
          ( SELECT 
               CASE
                 WHEN B.TIP_CLIEN_CONVENIO = DOC_BENEFICIARIO AND nvl(B.PCT_BENEFICIARIO, 0) > 0 AND NVL(B.PCT_EMPRESA, 0) > 0 THEN
                   'S'
                 ELSE
                   'N'
                END
            FROM VTA_PEDIDO_VTA_CAB A, 
                 VTA_PEDIDO_VTA_CAB AX, 
                 VTA_COMP_PAGO B 
            WHERE A.COD_GRUPO_CIA   = PAGO.COD_GRUPO_CIA
            AND A.COD_LOCAL         = PAGO.COD_LOCAL
            AND A.NUM_PED_VTA       = PAGO.NUM_PED_VTA
            AND A.COD_GRUPO_CIA     = AX.COD_GRUPO_CIA
            AND A.COD_LOCAL         = AX.COD_LOCAL
            AND AX.NUM_PED_VTA      = A.NUM_PED_VTA_ORIGEN
            AND AX.COD_GRUPO_CIA    = B.COD_GRUPO_CIA
            AND AX.COD_LOCAL        = B.COD_LOCAL
            AND AX.NUM_PED_VTA      = B.NUM_PED_VTA
            AND B.SEC_COMP_PAGO     = A.SEC_COMP_PAGO)
        WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_BENEFICIARIO AND NVL(PAGO.PCT_BENEFICIARIO, 0) > 0 AND NVL(PAGO.PCT_EMPRESA, 0) > 0 THEN
          'S'
        ELSE
          'N'
      END,
      PAGO.TIP_COMP_PAGO,
      NVL(PAGO.COD_TIP_COMP_PAGO_EREF, '0'),
      PAGO.TIP_CLIEN_CONVENIO,
      -- AHORRO
      TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_DESC_E*(1+(PAGO.PORC_IGV_COMP_PAGO/100))),0),'999999990.00')),
      -- PDF417
      NVL(PAGO.COD_PDF417_E, '')
    INTO vCopagoEmpresa, 
         vDescuentoNC,
      vCopagoCliente, 
      vTipoDocumento, 
      vTipoDocRefer, 
      vTipoClienteConvenio, 
      vMontoAhorro,
      vPDF417
    FROM  VTA_COMP_PAGO PAGO
    WHERE PAGO.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   PAGO.COD_LOCAL     = vCodLocal_in
      AND   PAGO.NUM_PED_VTA   = vNumPedVta_in
      AND   PAGO.SEC_COMP_PAGO = vSecCompPago_in;
     
      \**TIPO DE PEDIDO DE VENTA*\
    SELECT  CAB.TIP_PED_VTA, 
            CASE 
              WHEN COD_CONVENIO IN (COD_CONVENIO_COMPETENCIA) THEN
                'N'
              ELSE
                NVL(CAB.IND_CONV_BTL_MF,'N')
            END,
            CAB.SEC_MOV_CAJA,
            NVL(CAB.EST_TRX_ORBIS,'N'),
            (SELECT SUBSTR(CAB.NUM_TARJ_PUNTOS,1,4) || 
                    REPLACE(RPAD(' ',(LENGTH(CAB.NUM_TARJ_PUNTOS)-7),'*'),' ','') || 
                    SUBSTR(CAB.NUM_TARJ_PUNTOS,LENGTH(CAB.NUM_TARJ_PUNTOS)-3,4)
             FROM DUAL),
             CAB.DNI_CLI,
             CAB.NOM_CLI_PED_VTA,
             NVL(CAB.PT_ACUMULADO,0),
             NVL(CAB.PT_REDIMIDO,0),
             CASE
               WHEN NVL(CAB.PT_TOTAL,0) < 0 THEN
                 0
               ELSE 
                 NVL(CAB.PT_TOTAL,0) 
             END,
             NVL(CAB.PT_INICIAL,0)--ASOSA - 08/05/2015 - PTOSYAYAYAYA
      INTO  vTip_Ped_vta, 
            vPedidoConvenio,
            vSecMovCaja,
            vEstadoOrbis,
            vNroTarjetaPuntos,
            vNroDNIPto,
            vNombreClientePto,
            vPtoAcumulado,
            vPtoRedimido,
            vPtoTotal,
            vPtoInicial --ASOSA - 08/05/2015 - PTOSYAYAYAYA
      FROM  VTA_PEDIDO_VTA_CAB CAB
      WHERE CAB.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   CAB.COD_LOCAL     = vCodLocal_in
      AND   CAB.NUM_PED_VTA   = vNumPedVta_in;
      
    \** CABECERA DE PEDIDO **\
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','C','N','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE((
        SELECT 
          NVL(CIA.RAZ_SOC_CIA, ' ')||' - RUC: '||NVL(CIA.NUM_RUC_CIA, ' ') ||'@'||
          TRIM(SUBSTR(TRIM(CIA.DIR_CIA),0,INSTR(TRIM(CIA.DIR_CIA), ' - '||UBI.NODIS ||' - '|| UBI.NOPRV)))||'@'||
          (UBI.NODIS ||' - '|| UBI.NOPRV)||--'@'||
          ' TELF.: '||NVL(CIA.TELF_CIA, ' ')||'@'||
          NVL(PED.COD_LOCAL,' ')||' - '||NVL(LOC.DIREC_LOCAL_CORTA, ' ') ||'@'
        FROM VTA_PEDIDO_VTA_CAB  PED,
           PBL_CIA             CIA,
           UBIGEO              UBI,
           PBL_LOCAL           LOC
        WHERE CIA.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   CIA.COD_CIA       = PED.COD_CIA
        AND   UBI.UBDEP       = CIA.UBDEP
        AND   UBI.UBPRV       = CIA.UBPRV
        AND   UBI.UBDIS       = CIA.UBDIS
        AND   LOC.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   LOC.COD_LOCAL     = PED.COD_LOCAL
        AND   PED.COD_GRUPO_CIA   = vCodGrupoCia_in
        AND   PED.COD_LOCAL     = vCodLocal_in
        AND   PED.NUM_PED_VTA     = vNumPedVta_in
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
    
    \** NOMBRE Y NRO DE COMPROBANTE **\
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','C','S','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE((      
        SELECT 
          CASE 
            WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN 'BOLETA ELECTRONICA'
            WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) THEN 'FACTURA ELECTRONICA'
            WHEN COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN 'NOTA DE CREDITO ELECTRONICA'
          END || ' ' || --'@'||
          FARMA_UTILITY.GET_T_COMPROBANTE_2(COMP.COD_TIP_PROC_PAGO, COMP.NUM_COMP_PAGO_E, COMP.NUM_COMP_PAGO) ||'@'||
          CASE 
            WHEN vReimprime = 'S' THEN
              'COPIA DE COMPROBANTE'||'@'
          END
        FROM VTA_COMP_PAGO       COMP
        WHERE COMP.COD_GRUPO_CIA   = vCodGrupoCia_in
        AND   COMP.COD_LOCAL     = vCodLocal_in
        AND   COMP.NUM_PED_VTA     = vNumPedVta_in
        AND   COMP.SEC_COMP_PAGO   = vSecCompPago_in
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;   
     
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE(( 
        SELECT
          'FECHA DE EMISION: '||NVL(TO_CHAR(COMP.FEC_CREA_COMP_PAGO, 'dd/mm/yyyy HH24:MI:SS'), ' ') ||'@'||
          -- CONVENIOS CON GUIA
          CASE 
            WHEN COMP.TIP_COMP_PAGO_REF = COD_TIP_COMP_GUIA THEN 'NUM.GUIA: '||COMP.NUM_COMP_COPAGO_REF ||'@'
            ELSE ''
          END ||
          CASE 
            -- DATOS DE VENTA EMPRESA
            WHEN PED.TIP_PED_VTA = TIPO_VTA_INSTITUCIONAL THEN
              (SELECT 
                CASE 
                  WHEN LENGTH(TRIM(EMP.NUM_OC)) != NULL OR LENGTH(TRIM(EMP.NUM_OC)) != 0  THEN 
                    'ORD.COMPRA: '||TRIM(EMP.NUM_OC)||'@'
                END ||
                  CASE 
                    WHEN LENGTH(TRIM(EMP.COD_POLIZA)) != NULL OR LENGTH(TRIM(EMP.COD_POLIZA)) != 0  THEN 
                      'NUM.POLIZA: '||TRIM(EMP.COD_POLIZA)||' - '||TRIM(EMP.NOMBRE_CLIENTE_POLIZA)||'@'
                  END
              FROM  VTA_PEDIDO_VTA_CAB_EMP EMP 
              WHERE EMP.COD_GRUPO_CIA=PED.COD_GRUPO_CIA
              AND   EMP.COD_LOCAL = PED.COD_LOCAL
              AND   EMP.NUM_PED_VTA = PED.NUM_PED_VTA)
          END ||
          CASE 
            -- MOTIVO EN NOTAS DE CREDITO GENERADAS
            WHEN COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN 
              'MOTIVO ANULACION: '|| 
              NVL(PED.MOTIVO_ANULACION,'-')||'@'||
              (CASE 
                 WHEN NVL(COMP.NUM_COMP_PAGO_EREF, '*')!='*' THEN
                   'DOC.REFERENCIA: '||
                   FARMA_UTILITY.GET_DESC_COMPROBANTE( COMP.COD_GRUPO_CIA, 
                                                       (SELECT A.TIP_COMP_PAGO 
                                                        FROM VTA_COMP_PAGO A 
                                                        WHERE A.COD_GRUPO_CIA=COMP.COD_GRUPO_CIA 
                                                        AND A.COD_LOCAL=COMP.COD_LOCAL 
                                                        AND A.NUM_PED_VTA=PED.NUM_PED_VTA_ORIGEN
                                                        AND A.SEC_COMP_PAGO=PED.SEC_COMP_PAGO)
                                                     )||
                   ' '||
                   NVL(COMP.NUM_COMP_PAGO_EREF, '-')||'@'
               END)
          END || 
          '-'
        FROM VTA_PEDIDO_VTA_CAB  PED,
             VTA_COMP_PAGO       COMP,
             PBL_CIA             CIA,
             UBIGEO              UBI,
             PBL_LOCAL           LOC
        WHERE PED.COD_GRUPO_CIA   = COMP.COD_GRUPO_CIA
        AND   PED.COD_LOCAL       = COMP.COD_LOCAL
        AND   PED.NUM_PED_VTA     = COMP.NUM_PED_VTA
        AND   CIA.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   CIA.COD_CIA         = PED.COD_CIA
        AND   UBI.UBDEP           = CIA.UBDEP
        AND   UBI.UBPRV           = CIA.UBPRV
        AND   UBI.UBDIS           = CIA.UBDIS
        AND   LOC.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   LOC.COD_LOCAL       = PED.COD_LOCAL
        AND   COMP.COD_GRUPO_CIA  = vCodGrupoCia_in
        AND   COMP.COD_LOCAL      = vCodLocal_in
        AND   COMP.NUM_PED_VTA    = vNumPedVta_in
        AND   COMP.SEC_COMP_PAGO  = vSecCompPago_in
     
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;

    \** CABECERA DE DETALLE DE PEDIDO **\
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES(RPAD('CODIGO',8)|| RPAD('DESCRIPCION',18)|| LPAD('CANT.',10) || LPAD('P.UNIT.',9) || LPAD('DSCTO.',8)|| LPAD('IMPORTE',11),'9','D','S','N');
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES('-','9','I','N','N');

    \*** DETALLE DE PEDIDO **\
    IF vCopagoCliente = 'S' THEN
      \** CODIGO DE PRODUCTOS PARA COPAGO **\
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
        SELECT 
          RPAD(NVL(FARMA_EPOS.F_VAR_COD_SERV_PED(PAGO.COD_GRUPO_CIA, PAGO.COD_LOCAL, PAGO.NUM_PED_VTA, PAGO.SEC_COMP_PAGO), ' '),8)||
          RPAD(SUBSTR(FARMA_EPOS.F_VAR_DSC_PROD_SERV(PAGO.COD_GRUPO_CIA,PAGO.COD_LOCAL),0,17),18) ||
          LPAD('1',10) ||
          LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                                  WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND PAGO.PCT_BENEFICIARIO > 0 AND PAGO.PCT_EMPRESA > 0 THEN
                                                                    ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                                  ELSE
                                                                    0
                                                                END),
                                0),
                            '999,999,990.000')
                    )
              ,9)||
          --LPAD('0.000',8) ||  
          LPAD('     ',8) ||    --ASOSA - 15/05/2015 - PTOSYAYAYAYA - QUIEREN QUE NO SE VEAN LOS CEROS
          LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                                  WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND PAGO.PCT_BENEFICIARIO > 0 AND PAGO.PCT_EMPRESA > 0 THEN
                                                                    ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                                  ELSE
                                                                    0
                                                                END),
                                0),
                            '999,999,990.00')
                    )
              ,11)
          ,'9','D','N','N'
        FROM  VTA_COMP_PAGO PAGO
        WHERE PAGO.COD_GRUPO_CIA   = vCodGrupoCia_in
        AND   PAGO.COD_LOCAL       = vCodLocal_in
        AND   PAGO.NUM_PED_VTA     = vNumPedVta_in
        AND   PAGO.SEC_COMP_PAGO   = vSecCompPago_in;
        
        vAuxNumPedVta := vNumPedVta_in;
    ELSE
      --  KMONCADA 09.01.2014 EN CASO DE NOTAS DE CREDITO TOMARA EL DETALLE DEL DOCUMENTO ORIGEN
      IF vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN 
        SELECT  CAB.SEC_COMP_PAGO, CAB.NUM_PED_VTA_ORIGEN 
        INTO vAuxSecCompPago, vAuxNumPedVta
        FROM VTA_COMP_PAGO cp,
        VTA_PEDIDO_VTA_CAB CAB
        WHERE 
        CAB.COD_GRUPO_CIA=CP.COD_GRUPO_CIA
        AND CAB.COD_LOCAL  = CP.COD_LOCAL
        AND CAB.NUM_PED_VTA = CP.NUM_PED_VTA
        AND CP.COD_GRUPO_CIA = vCodGrupoCia_in
        AND CP.COD_LOCAL = vCodLocal_in
        AND CP.NUM_PED_VTA = vNumPedVta_in
        AND CP.SEC_COMP_PAGO = vSecCompPago_in; 
      ELSE
        vAuxSecCompPago := vSecCompPago_in;
        vAuxNumPedVta := vNumPedVta_in;
      END IF;
      
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
      -- KMONCADA 04.06.2015 SE MODIFICA PARA QUE DESCRIPCION NO TAPE AL MOMTO DE DESCUENTO
      SELECT
       -- COD_PROD
       RPAD(DETALLE.COD_PROD, 8, ' ') ||
       -- DESCRIPCION 38 - 20
       CASE
         WHEN LENGTH(DETALLE.DESCRIPCION) + LENGTH(DETALLE.UNID_PRESENT) >= 38 THEN
           SUBSTR(DETALLE.DESCRIPCION, 0, (37 - LENGTH(DETALLE.UNID_PRESENT))) || ' ' || DETALLE.UNID_PRESENT
         ELSE
           DETALLE.DESCRIPCION ||RPAD(' ',(38 - (LENGTH(DETALLE.DESCRIPCION) + LENGTH(DETALLE.UNID_PRESENT))), ' ') || DETALLE.UNID_PRESENT
       END || RPAD(' ', 18, ' ') ||
       -- LABORATORIO
       '       ' || RPAD(DETALLE.LAB, 19, ' ') ||
       -- CANTIDAD 
       LPAD(DETALLE.CANTIDAD, 10, ' ') ||
       -- PRECIO UNITARIO
       LPAD(DETALLE.PREC_UNIT, 9, ' ') ||
       -- DSCTO
       LPAD(DETALLE.DSCTO, 8, ' ') ||
       -- IMPORTE
       LPAD(DETALLE.SUBTOTAL, 11, ' '),
       '9','D','N','N'

       FROM (
          SELECT NVL(DET.COD_PROD, ' ') COD_PROD,
                 SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,
                              '31',
                              'PROM-' || TRIM(PROD.DESC_PROD),
                              NVL(TRIM(PROD.DESC_PROD), ' ')),
                       0,34) DESCRIPCION,
                 SUBSTR(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL, --Si el Tipo de venta es Mayorista
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
                 SUBSTR(TRIM(LA.NOM_LAB), 0, 20) LAB,
                 DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL, --Si el Tipo de venta es Mayorista
                        DECODE(MOD(ABS(DET.CANT_ATENDIDA), ABS(DET.VAL_FRAC)),
                                0,
                                (ABS(DET.CANT_ATENDIDA) / ABS(DET.VAL_FRAC)) || '', --Imprime las cantidad
                                ABS(DET.CANT_ATENDIDA)), --sin fraccion
                       ABS(DET.CANT_ATENDIDA)) CANTIDAD,
                 TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E, 0),'999,999,990.000')) PREC_UNIT,
                  CASE
                    WHEN FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in, vCodLocal_in, vAuxNumPedVta) = 'S' THEN
                      CASE
                        WHEN ABS(NVL(DET.AHORRO_CAMP, 0)) = 0 THEN
                          ' '
                        WHEN ABS(NVL(DET.AHORRO_CAMP, 0)) > 0 THEN
                          TRIM(TO_CHAR((ABS(NVL(DET.AHORRO_CAMP, 0)) * (-1)), '999,999,990.000'))
                      END
                    ELSE
                      CASE
                        WHEN ABS(NVL(DET.AHORRO, 0)) = 0 THEN
                          ' '
                        WHEN ABS(NVL(DET.AHORRO, 0)) > 0 THEN
                          TRIM(TO_CHAR((ABS(NVL(DET.AHORRO, 0)) * (-1)),'999,999,990.000'))
                      END
                  END DSCTO,
                  CASE
                    WHEN FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in, vCodLocal_in, vAuxNumPedVta) = 'S' THEN
                      TRIM(TO_CHAR(ABS(NVL(DET.VAL_PREC_TOTAL, 0) + NVL(DET.AHORRO_PUNTOS, 0)), '999,999,990.00'))
                    ELSE
                      TRIM(TO_CHAR(ABS(NVL(DET.VAL_PREC_TOTAL, 0)), '999,999,990.00'))
                  END SUBTOTAL
                
          FROM VTA_PEDIDO_VTA_DET DET, LGT_PROD PROD, LGT_LAB LA
          WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   DET.COD_LOCAL     = vCodLocal_in
          AND   DET.NUM_PED_VTA   = vAuxNumPedVta -- vNumPedVta_in
          AND  (CASE
                  WHEN vTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
                  WHEN vTipoClienteConvenio = DOC_EMPRESA AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
                  ELSE DET.SEC_COMP_PAGO
                END) = vAuxSecCompPago -- vSecCompPago_in
          AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND   DET.COD_PROD      = PROD.COD_PROD
          AND   PROD.COD_LAB      = LA.COD_LAB
          ORDER BY DET.SEC_PED_VTA_DET ASC) DETALLE;

      
      \*SELECT 
        -- Codigo de Producto
        RPAD(NVL(DET.COD_PROD,' '),8)  ||
        --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
        RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31',
                           'PROM-'||PROD.DESC_PROD,
                           NVL(PROD.DESC_PROD,' ')
                          )
                    ,0,34)||'',34,' ') ||
        RPAD(SUBSTR(
                    DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                           DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                                  PROD.DESC_UNID_PRESENT,
                                  DET.UNID_VTA
                                  ),--sin fraccion                    
                           DECODE(DET.VAL_FRAC,1,
                                  PROD.DESC_UNID_PRESENT,
                                  DET.UNID_VTA)
                           )
                    ,0,20)||'',22,' ') ||
        '       '||
        RPAD(''||TRIM(SUBSTR(LA.NOM_LAB,0,20))||'',19,' ') ||
        --Cantidad vendida en fracciones
        LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                    DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                           (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Imprime las cantidad
                           ABS(DET.CANT_ATENDIDA)) ,--sin fraccion
                    ABS(DET.CANT_ATENDIDA))
               ,10) || 
        -- Valor de venta unitario por �tem(no incluye igv)
        LPAD(TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E,0) ,'999,999,990.000')) ,9)||
        
        LPAD(
          CASE
            WHEN ABS(NVL(DET.AHORRO_CAMP,0)) = 0 THEN 
              ' '
            WHEN ABS(NVL(DET.AHORRO_CAMP,0)) > 0 THEN
              NVL(TRIM(TO_CHAR(NVL(ABS(DET.AHORRO_CAMP),0),'999,999,990.000')),0)
          END,8) ||
        LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL+NVL(DET.AHORRO_PUNTOS,0)),0),'999,999,990.00')),0),11)
        \*
        CASE WHEN FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in,vCodLocal_in,vAuxNumPedVta) = 'S' THEN 
          -- ERIOS 23.03.2015 Muestra montos sin descontar ahorro por puntos redimidos
          --ASOSA - 15/05/2015 - PTOSYAYAYAYA - QUIEREN QUE NO SE VEAN LOS CEROS EN LOS DESCUENTOS
          LPAD(
             DECODE(
                  NVL(TRIM(TO_CHAR((NVL(ABS(DET.AHORRO),0)-NVL(ABS(DET.AHORRO_PUNTOS),0)),'999,999,990.000')),0),
                  '0.000',
                  '     ',
                  NVL(TRIM(TO_CHAR((NVL(ABS(DET.AHORRO),0)-NVL(ABS(DET.AHORRO_PUNTOS),0)),'999,999,990.000')),0)
                  ),
             8) ||
          LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL+DET.AHORRO_PUNTOS),0),'999,999,990.00')),0),11)
        ELSE
          -- Descuento del Item(no incluye igv)
          --ASOSA - 15/05/2015 - PTOSYAYAYAYA - QUIEREN QUE NO SE VEAN LOS CEROS EN LOS DESCUENTOS
          LPAD(
             DECODE(
                  NVL(
                  --TRIM(TO_CHAR(NVL(ABS(ROUND((DET.VAL_TOTAL_DESC_ITEM_E*(1+(DET.VAL_IGV/100))),2)),0),'999,999,990.000'))
                  TRIM(TO_CHAR(NVL(ABS(DET.AHORRO),0),'999,999,990.000')) --DESA3
                  ,0),
                  '0.000',
                  '     ',
                  NVL(
                  --TRIM(TO_CHAR(NVL(ABS(ROUND((DET.VAL_TOTAL_DESC_ITEM_E*(1+(DET.VAL_IGV/100))),2)),0),'999,999,990.000'))
                  TRIM(TO_CHAR(NVL(ABS(DET.AHORRO),0),'999,999,990.000')) --DESA3
                  ,0)
                  ),
             8) ||
          LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL),0),'999,999,990.00')),0),11)
        END*\
        ,'9','D','N','N'
      FROM  VTA_PEDIDO_VTA_DET DET, 
              LGT_PROD PROD,
              lgt_lab la
      WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   DET.COD_LOCAL     = vCodLocal_in
      AND   DET.NUM_PED_VTA   = vAuxNumPedVta -- vNumPedVta_in
      AND  (CASE
              WHEN vTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
              WHEN vTipoClienteConvenio = DOC_EMPRESA AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
              ELSE DET.SEC_COMP_PAGO
            END) = vAuxSecCompPago -- vSecCompPago_in
      AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
      AND   DET.COD_PROD      = PROD.COD_PROD
      AND   PROD.COD_LAB      = LA.COD_LAB
      ORDER BY DET.SEC_PED_VTA_DET ASC ; *\
         
    END IF;

    --INI ASOSA - 21/05/2015 - PTOSYAYAYAYA - REACOMODAR ESTA SECCION
    IF FARMA_PUNTOS.F_VAR_IND_ACT_PUNTOS(vCodGrupoCia_in, vCodLocal_in) = 'S' --ASOSA - 21/05/2015 - PTOSYAYAYAYA
       AND vEstadoOrbis IN (EST_ORBIS_ENVIADO,EST_ORBIS_PENDIENTE) THEN   -- KMONCADA 28.05.2015 SOLO PARA PEDIDOS QUE SON DEL PROGRAMA PUNTOS
      --ASOSA - 19/05/2015 - PTOSYAYAYAYA - VALIDACION PARA QUE NO SE IMPRIMA ESTO CON NOTA DE CREDITO
      IF FN_IS_NOTA_CREDITO(vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in) = 'N' THEN
          IF FN_VAL_DSCTO_AHORRO_INMED(vCodGrupoCia_in,
                                          vCodLocal_in,
                                          vNumPedVta_in,
                                          vSecCompPago_in) <> '0.00' OR 
           FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in,
                                            vCodLocal_in,
                                            vAuxNumPedVta) = 'S' THEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE) VALUES('-','9','I','N','N');
          END IF;
          --INI ASOSA - 15/05/2015 - PTOSYAYAYAYA
          INS_AHORRO_INMED(vCodGrupoCia_in,
                     vCodLocal_in,
                     vNumPedVta_in,
                     vSecCompPago_in);
       --FIN ASOSA - 15/05/2015 - PTOSYAYAYAYA
       
        IF FN_VAL_DSCTO_AHORRO_INMED(vCodGrupoCia_in,
                                          vCodLocal_in,
                                          vNumPedVta_in,
                                          vSecCompPago_in) <> '0.00' OR 
           FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in,
                                            vCodLocal_in,
                                            vAuxNumPedVta) = 'S' THEN
                                          
           INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE)        
           SELECT LPAD('IMPORTE: S/.',25)||--ASOSA - 15/05/2015 - PTOSYAYAYAYA - SE CAMBIA POR INDICACION DE EDMUNDO
                 LPAD(TRIM(TO_CHAR(SUM(NVL(ABS(DET.VAL_PREC_TOTAL+NVL(DET.AHORRO_PUNTOS,0)),0)),'999,999,990.00')),15)
                  ,'9','D','N','N'
           FROM VTA_PEDIDO_VTA_DET DET
           WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
            AND   DET.COD_LOCAL     = vCodLocal_in
            AND   DET.NUM_PED_VTA   = vAuxNumPedVta
            AND DET.SEC_COMP_PAGO = vAuxSecCompPago;
            
        END IF;

       --ERIOS 23.03.2015 Imprime descuento por puntos redimidos
       IF FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in,
                                            vCodLocal_in,
                                            vAuxNumPedVta) = 'S' THEN                                                                                      
       
           INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE)
           WITH 
           CAB AS (SELECT NVL(PT_REDIMIDO,0) AS PT_REDIMIDO FROM VTA_PEDIDO_VTA_CAB 
                   WHERE COD_GRUPO_CIA = vCodGrupoCia_in 
                         AND COD_LOCAL = vCodLocal_in 
                         AND NUM_PED_VTA = vAuxNumPedVta),
           DET AS (SELECT SUM(NVL(DET.AHORRO_PUNTOS,0)) AHORRO_PUNTOS,
                          SUM(NVL(DET.FACTOR_PUNTOS,0)) FACTOR_PUNTOS       
                   FROM VTA_PEDIDO_VTA_DET DET
                   WHERE COD_GRUPO_CIA = vCodGrupoCia_in 
                         AND COD_LOCAL = vCodLocal_in 
                         AND NUM_PED_VTA = vAuxNumPedVta
                         AND SEC_COMP_PAGO = vAuxSecCompPago)      
           SELECT LPAD(FARMA_LEALTAD.MSJ_IMPR_PUNTOS_REDI||
                 '(' || PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(ROUND(CAB.PT_REDIMIDO*DET.FACTOR_PUNTOS)) || 
                 'PTOS):',40,' ')||
                 ' S/.' ||
                 LPAD('-' || TRIM(TO_CHAR(DET.AHORRO_PUNTOS,'999,999,990.00')),15)
                  ,'9','D','N','N'
           FROM CAB, DET;  
        
        
     END IF;
    
    END IF;

    END IF;
    --FIN ASOSA - 21/05/2015 - PTOSYAYAYAYA - REACOMODAR ESTA SECCION  
       
       
    \*** MONTOS TOTALES **\
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','D','N','N'
      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((

        SELECT  
          
          -- KMONCADA 22.12.2014 NO SE MUESTRA LA GLOSA TOTAL A PAGAR 
          
          -- KMONCADA 22.12.2014 SE MODIFICA EL ORDEN DE LA GLOSA COPAGO/DESCUENTO(NOTA DE CREDITO)
          
          
          --Redondeo
          
          
          --total a pagar si es copago
          -- CASE WHEN vCopagoEmpresa != 'S' THEN 
            '-'||'@'||
            CASE 
\*            WHEN vCopagoEmpresa = 'S' AND vDescuentoNC > 0 THEN
              LPAD('DESCUENTO:  S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(vDescuentoNC),0),'999,999,990.00')),15) || '@'*\
              WHEN vCopagoEmpresa = 'S' THEN
                --'-' || '@' ||LPAD('COPAGO '||TRIM(TO_CHAR(NVL(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),0),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR(NVL((ABS(PAGO.VAL_COPAGO_E*(-1))),0),'999,999,990.00')),15) || '@' 
                LPAD('COPAGO '||TRIM(TO_CHAR(NVL(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),0),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR(NVL((ABS(PAGO.VAL_COPAGO_COMP_PAGO)*(-1)),0),'999,999,990.00')),15) || '@'
            END ||
          
            CASE 
              WHEN \*vCopagoEmpresa != 'S' AND*\ NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0)!=0 THEN
                 LPAD('REDONDEO: S/.',13)||LPAD(TRIM(TO_CHAR(NVL(((ABS(PAGO.VAL_REDONDEO_COMP_PAGO)*(-1))),0),'999,999,990.00')),7)
              ELSE
                ' '   
            END ||
            LPAD('TOTAL A PAGAR: S/.',25)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15) || '@' ||
          -- END ||
          
          
          -- Operaciones grabagas
          '-' || '@' ||
          CASE WHEN NVL(PAGO.TOTAL_GRAV_E,0)!=0 THEN
            LPAD('OP.GRAVADAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_GRAV_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Operaciones inafectas
          CASE WHEN NVL(PAGO.TOTAL_INAF_E,0)!=0 THEN
            LPAD('OP.INAFECTAS: S/.',20) ||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_INAF_E),0),'999,999,990.00')),15) || '@' 
          END ||
          --Operaciones exoneradas
          CASE WHEN NVL(PAGO.TOTAL_EXON_E,0)!=0 THEN
            LPAD('OP.EXONERADAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_EXON_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Operaciones Gratuitas
          CASE WHEN NVL(PAGO.TOTAL_GRATU_E,0)!=0 THEN
            LPAD('OP.GRATUITAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_GRATU_E),0),'999,999,990.00')),15) || '@' 
          END ||
              
          -- Monto Impuesto
          CASE WHEN NVL(PAGO.TOTAL_IGV_E,0)!=0 THEN
            LPAD('IGV-'||PAGO.PORC_IGV_COMP_PAGO||'%: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Monto Descuentos
          \*CASE WHEN NVL(PAGO.TOTAL_DESC_E,0)!=0 THEN
            LPAD('DESCUENTO: S/.',20)||LPAD(TRIM(TO_CHAR(NVL((ABS(PAGO.TOTAL_DESC_E)),0),'999,999,990.00')),15) || '@' 
          END ||*\
          CASE
            WHEN vCopagoEmpresa != 'S' AND NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0)!=0 THEN
              LPAD('REDONDEO: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(((ABS(PAGO.VAL_REDONDEO_COMP_PAGO)*(-1))),0),'999,999,990.00')),15) || '@'
          END ||
          
          --INI ASOSA - 16/04/2015 - PTOSYAYAYAYA
          CASE 
            -- KMONCADA SE MOSTRARA EL AHORRO CALCULADO ANTERIORMENTE
            \*
              WHEN ahorroF <> 'N' THEN    
              LPAD('TOTAL DSCTO: S/.',16)||LPAD(ahorroF,13)
            *\
            WHEN ahorroPtoSoles > 0 AND PAGO.TIP_COMP_PAGO != COD_TIP_COMP_NOTA_CRED THEN
              --LPAD('TOTAL DSCTO: S/.',16)||LPAD('-' || TRIM(TO_CHAR(ahorroPtoSoles,'999,999,990.00')),13) 
              LPAD('TOTAL DESCUENTOS: S/.',21)||LPAD('-' || TRIM(TO_CHAR(ahorroPtoSoles,'999,999,990.00')),8)--ASOSA - 15/05/2015 - PTOSYAYAYAY - ASI LO QUIERE EDMUNDO
          END ||
          --FIN ASOSA - 16/04/2015 - PTOSYAYAYAYA
          
          -- Monto Total (lo que debe  pagar el cliente)
          CASE 
            WHEN NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                  WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA 
                                      AND PAGO.PCT_BENEFICIARIO > 0  
                                      AND PAGO.PCT_EMPRESA > 0 THEN 
                                    ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                  ELSE 0 
                                  END), 0) != 0 THEN
              
              LPAD('IMPORTE TOTAL: S/.',20)|| LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15)|| '@'
              -- KMONCADA 22.12.2014 MONTOS YA NO SE MOSTRARAN AL 100%
              \*CASE WHEN vCopagoEmpresa = 'S' THEN
                              LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                          WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA
                                                            AND PAGO.PCT_BENEFICIARIO > 0  
                                                            AND PAGO.PCT_EMPRESA > 0 THEN 
                                                           ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                          ELSE 0
                                                          END)
                                        ,0),'999999990.00')),15) || '@-@'
                            ELSE
                              --LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15) || '@-@'
                            -- KMONCADA 14.11.2014 NO CONSIDERAR REDONDEO EN INFORMACION DE SUNAT
                             LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO),0),'999,999,990.00')),15) || '@-@'
                          END*\
            END || '-' || '@'
            -- KMONCADA 22.12.2014 YA NO SE MOSTRARAN LOS MONTOS DE TOTAL A PAGAR 
           \*
           CASE WHEN vCopagoEmpresa = 'S' THEN 
                         '-' || '@' ||
                         LPAD('COPAGO '||TRIM(TO_CHAR(NVL(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),0),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_COPAGO_COMP_PAGO),0),'999,999,990.00')),15) || '@' ||
                         LPAD('TOTAL A PAGAR: S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO),0),'999,999,990.00')),15)|| '@' ||
                         '-' || '@'
                         *\
         -- END 
              FROM VTA_COMP_PAGO PAGO
              WHERE PAGO.COD_GRUPO_CIA = vCodGrupoCia_in
              AND PAGO.COD_LOCAL = vCodLocal_in
              AND PAGO.NUM_PED_VTA= vNumPedVta_in
              AND PAGO.SEC_COMP_PAGO = vSecCompPago_in
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;   
         
    \*** FORMAS DE PAGO **\       
    -- NOTA DE CREDITO NO MOSTRARA FORMA DE PAGO
      IF vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN
          FOR FORMAPAGO IN (
          SELECT
            NVL(A.COD_FORMA_PAGO,'-') COD_FORMA_PAGO,
            NVL(A.TIP_MONEDA,'-') TIP_MONEDA,
            TRIM(TO_CHAR(NVL(ABS(A.IM_PAGO),0),'999,999,990.00')) MONTO,
            TRIM(TO_CHAR(NVL(ABS(A.VAL_VUELTO),0),'999,999,990.00')) VUELTO, 
            TRIM(TO_CHAR(NVL(ABS(A.VAL_TIP_CAMBIO),0),'999,999,990.00')) TIPO_CAMBIO,
            C.DESC_FORMA_PAGO DESCRIPCION,
			TRIM(TO_CHAR(NVL(ABS(A.IM_TOTAL_PAGO),0),'999,999,990.00')) TOTAL_PAGO
          FROM VTA_FORMA_PAGO_PEDIDO A,
             VTA_FORMA_PAGO C
          WHERE A.COD_GRUPO_CIA  = C.COD_GRUPO_CIA 
          AND   A.COD_FORMA_PAGO = C.COD_FORMA_PAGO
          AND   A.COD_FORMA_PAGO = C.COD_FORMA_PAGO 
          AND   A.COD_GRUPO_CIA  = vCodGrupoCia_in
          AND   A.COD_LOCAL      = vCodLocal_in
          AND   A.NUM_PED_VTA    = vNumPedVta_in
          ORDER BY A.COD_FORMA_PAGO DESC) LOOP
            
        IF vCopagoEmpresa = 'N' AND vCopagoCliente = 'N' THEN
		  --ERIOS 25.02.2015 Se muestra la cantidad de puntos soles.
          IF FORMAPAGO.COD_FORMA_PAGO  = COD_FORMA_PAGO_DOLARES THEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD('TIPO DE CAMBIO: '||FORMAPAGO.TIPO_CAMBIO||' - '||FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'9','D','N','N');
          ELSIF FORMAPAGO.COD_FORMA_PAGO  = COD_FORMA_PAGO_PUNTOS THEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
			      VALUES(LPAD(FORMAPAGO.DESCRIPCION||': '||PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(TO_NUMBER(FORMAPAGO.MONTO,'999,999,990.00'))||'  S/.',40)||LPAD(FORMAPAGO.TOTAL_PAGO,15)
                    ,'9','D','N','N');
          ELSE
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD(FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'9','D','N','N');
          END IF;
                    
          IF FORMAPAGO.VUELTO != '0.00' THEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD('VUELTO: S/.',40)||LPAD(FORMAPAGO.VUELTO,15),'9','D','N','N');
          END IF;
        ELSE
          IF vCopagoEmpresa = 'N' AND vCopagoCliente = 'S' THEN
            IF FORMAPAGO.COD_FORMA_PAGO  = COD_FORMA_PAGO_DOLARES THEN
              INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD('TIPO DE CAMBIO: '||FORMAPAGO.TIPO_CAMBIO||' - '||FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'9','D','N','N');
            ELSE
              IF FORMAPAGO.COD_FORMA_PAGO  = COD_FORMA_PAGO_SOLES THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD(FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'9','D','N','N');
              ELSE
                IF FORMAPAGO.COD_FORMA_PAGO != COD_FORMA_PAGO_CONVENIO THEN
                  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD(FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'9','D','N','N');
                END IF;
              END IF;
            END IF;
                      
            IF FORMAPAGO.VUELTO != '0.00' THEN
              INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD('VUELTO: S/.',40)||LPAD(FORMAPAGO.VUELTO,15),'9','D','N','N');
            END IF;
          ELSE
            IF FORMAPAGO.COD_FORMA_PAGO = COD_FORMA_PAGO_CREDITO THEN
              INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD(FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'9','D','N','N');
            END IF;
          END IF;
        END IF;
      END LOOP;
      END IF;
    
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','9','D','N','N');
      
      --ASOSA - 15/05/2015 - PTOSYAYAYAYA - SE CAMBIO DE UBICACION XQ ASI LO QUIERE EDMUNDO
            -- KMONCADA 22.04.2015 SE MODIFICA ESTADO DE NO PROCESADO EN ORBIS
      --IF vEstadoOrbis = ESTADO_SIN_TRX_ORBIS THEN --NTOA: PIDIERON QUITAR ESTA VALIDACION TAMBIEN, NADA MAS.
        IF vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN
          IF vMontoAhorro != '0.00' THEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','9','D','N','N');
          	--INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('EN LA COMPRA USTED AHORRO: S/.'||TRIM(vMontoAhorro),'0','C','S','N');
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('!!!FELICITACIONES!!!','0','C','S','N'); --SE CAMBIO LA VARIABLE TAMBIEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('EN ESTA COMPRA AHORRASTE S/.'||RPAD(TRIM(TO_CHAR(ahorroPtoSoles,'999,999,990.00')),6,' '),'0','C','S','N'); --SE CAMBIO LA VARIABLE TAMBIEN
          	INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','9','D','N','N');
          END IF;
        END IF;
      --END IF;
      
    \*** MONTO EN LETRAS ***\
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
      SELECT 
        'SON : ' || 
        UPPER(TRIM(FARMA_UTILITY.LETRAS((COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO) \*+ (CASE WHEN COMP.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND COMP.PCT_BENEFICIARIO > 0 AND COMP.PCT_EMPRESA > 0 THEN
                                                          ABS(COMP.VAL_COPAGO_COMP_PAGO)
                                                        ELSE
                                                          0
                                                      END)*\
                        ))) ||
        ' NUEVOS SOLES.'
        ,'9','I','N','N'
      FROM VTA_COMP_PAGO COMP
      WHERE COMP.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   COMP.COD_LOCAL     = vCodLocal_in
          AND   COMP.NUM_PED_VTA   = vNumPedVta_in
          AND   COMP.SEC_COMP_PAGO = vSecCompPago_in;

      \*** AHORRO ***\
\*      IF PTOVENTA_FIDELIZACION.FID_F_GET_IND_UN_COMP(vCodGrupoCia_in,
                                              		     vCodLocal_in,
                                                       vNumPedVta_in) <> 'S' THEN --ASOSA - 23/03/2015 -PTOSYAYAYAYA - DERREPENTE LO CAMBIAN UNA VZ MAS
*\

      
      IF vTip_Ped_vta = TIPO_VTA_DELIVERY THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
          SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
              SELECT
                CASE 
                  WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) OR (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '3') THEN
                    CASE 
                      WHEN COMP.VAL_NETO_COMP_PAGO >= 700.00 THEN
                        CASE 
                          WHEN NVL(TRIM(DEL.NOMBRE_DE),'*') != '*' THEN
                            'NOMBRE DE CLIENTE: ' || DEL.NOMBRE_DE || '@'
                        END||
                        CASE
                          WHEN NVL(TRIM(COMP.NUM_DOC_IMPR),'*') != '*' THEN
                            'DNI: '|| NVL(COMP.NUM_DOC_IMPR,' ') || '@'
                        END||
                        CASE
                          WHEN NVL(TRIM(COMP.DIREC_IMPR_COMP), '*') != '*' THEN
                            'DIRECCION: '|| TRIM(COMP.DIREC_IMPR_COMP) || '@'
                        END
                      ELSE
                        CASE 
                          WHEN NVL(TRIM(DEL.DATOS_CLI),'*') != '*' THEN
                            'NOMBRE DE CLIENTE: '|| TRIM(DEL.DATOS_CLI) || '@'
                        END||
                        CASE
                          WHEN NVL(TRIM(DEL.DIRECCION),'*') != '*' THEN
                            'DIRECCION: '|| TRIM(DEL.DIRECCION) || '@'
                        END
                    END 
                  WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) OR (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '1') THEN
                    CASE 
                      WHEN NVL(TRIM(COMP.NOM_IMPR_COMP),'*') != '*' THEN 
                        'RAZON SOCIAL: '|| COMP.NOM_IMPR_COMP || '@'
                    END||
                    CASE
                      WHEN NVL(TRIM(COMP.NUM_DOC_IMPR),'*') != '*' THEN
                        'RUC CLIENTE: ' || TRIM(COMP.NUM_DOC_IMPR) || '@'
                    END||
                    CASE
                      WHEN NVL(TRIM(COMP.DIREC_IMPR_COMP),'*') != '*' THEN
                        'DIRECCION: '|| TRIM(COMP.DIREC_IMPR_COMP) || '@'
                    END
                END 
              FROM VTA_COMP_PAGO COMP, 
                   VTA_PEDIDO_VTA_CAB CAB,
                   TMP_CE_CAMPOS_COMANDA DEL
              WHERE COMP.COD_GRUPO_CIA   = vCodGrupoCia_in
              AND   COMP.COD_LOCAL       = vCodLocal_in
              AND   COMP.NUM_PED_VTA     = vNumPedVta_in
              AND   COMP.SEC_COMP_PAGO   = vSecCompPago_in
              AND   COMP.COD_GRUPO_CIA   = CAB.COD_GRUPO_CIA
              AND   COMP.COD_LOCAL       = CAB.COD_LOCAL
              AND   COMP.NUM_PED_VTA     = CAB.NUM_PED_VTA
              AND   DEL.COD_GRUPO_CIA    = CAB.COD_GRUPO_CIA
              AND   DEL.COD_LOCAL        = CAB.COD_LOCAL
              AND   DEL.NUM_PED_VTA      = CAB.NUM_PEDIDO_DELIVERY
          ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
      ELSE
        -- KMONCADA 26.05.2015 TIENE QUE MOSTRAR EN CASO DE FACTURA
        vIndProgPuntos := PTOVENTA_FIDELIZACION.FID_F_GET_IND_UN_COMP(vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in);
       \*IF PTOVENTA_FIDELIZACION.FID_F_GET_IND_UN_COMP(vCodGrupoCia_in,
                                              		     vCodLocal_in,
                                                       vNumPedVta_in) <> 'S' THEN --ASOSA - 23/03/2015 -PTOSYAYAYAYA - DERREPENTE LO CAMBIAN UNA VZ MAS
         *\ 
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
            SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
              SELECT   
                CASE 
                  WHEN NVL(TRIM(COMP.NOM_IMPR_COMP), '*') != '*' THEN
                    CASE 
                      WHEN (COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) OR 
                            (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '3') ) AND
                           vIndProgPuntos <> 'S' 
                           THEN
                        'NOMBRE DE CLIENTE: '|| COMP.NOM_IMPR_COMP || '@'
                      WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) OR 
                           (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '1') THEN
                        'RAZON SOCIAL: ' || COMP.NOM_IMPR_COMP || '@'
                    END
                END ||
                CASE 
                  WHEN NVL(TRIM(COMP.NUM_DOC_IMPR), '*') != '*' THEN
                    CASE 
                      WHEN (COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) OR 
                            (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '3') ) AND
                            vIndProgPuntos <> 'S'
                           THEN
                        'DNI CLIENTE: '|| COMP.NUM_DOC_IMPR || '@'
                      WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) OR 
                           (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '1') THEN
                        'RUC CLIENTE: ' || COMP.NUM_DOC_IMPR || '@'  
                    END
                END ||
                CASE 
                  WHEN NVL(TRIM(COMP.DIREC_IMPR_COMP), '*') != '*' THEN
                    'DIRECCION: '||COMP.DIREC_IMPR_COMP || '@'  
                END
              FROM VTA_COMP_PAGO COMP 
              WHERE COMP.COD_GRUPO_CIA = vCodGrupoCia_in
              AND   COMP.COD_LOCAL     = vCodLocal_in
              AND   COMP.NUM_PED_VTA   = vNumPedVta_in
              AND   COMP.SEC_COMP_PAGO = vSecCompPago_in
          ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
       -- END IF;
      END IF;
        
      \**  DATOS DEL CONVENIO **\
      \** DATOS ADICIONALES CONVENIO **\
      IF vPedidoConvenio='S' AND vTip_Ped_vta != TIPO_VTA_INSTITUCIONAL THEN
        IF vTipoClienteConvenio= '1' or vTipoClienteConvenio= '2' THEN
          -- DATOS CONVENIO
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
            SELECT 'CONVENIO: '|| CONV.DES_CONVENIO ,'9','I','N','N'
            FROM   VTA_PEDIDO_VTA_CAB CAB, 
                 MAE_CONVENIO CONV
            WHERE CAB.COD_GRUPO_CIA = vCodGrupoCia_in
            AND   CAB.COD_LOCAL     = vCodLocal_in
            AND   CAB.NUM_PED_VTA   = vNumPedVta_in
            AND   CAB.COD_CONVENIO  = CONV.COD_CONVENIO;
             
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
            SELECT UPPER(DATOS.NOMBRE_CAMPO||': '||DATOS.DESCRIPCION_CAMPO)  ,'9','I','N','N'
            FROM   CON_BTL_MF_PED_VTA  DATOS 
            WHERE  DATOS.COD_GRUPO_CIA = vCodGrupoCia_in
            AND    DATOS.COD_LOCAL     = vCodLocal_in
            AND    DATOS.NUM_PED_VTA   = vNumPedVta_in;
             
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
            SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
              SELECT
                CASE 
                  --PARA EMPRESA
                  WHEN COMP.TIP_CLIEN_CONVENIO = '2' THEN
                    --venta total para la empresa
                    -- KMONCADA 28.04.2015 MOSTRAR EN VALOR POSITIVO EL MONTO DE LA VENTA
                    'VENTA TOTAL: S/.' || TRIM(TO_CHAR(ABS(NVL(COMP.VAL_BRUTO_COMP_PAGO, 0)), '999,999,990.00')) || '@' ||
                    'INSTITUCION: ' || TRIM(CONV.INSTITUCION) || ' - ' || COMP.PCT_EMPRESA || '%' || '@' || --Nombre de laInstitucion
                    CASE
                      WHEN NVL(COMP.NUM_COMP_COPAGO_REF, '*') != '*' THEN
                        'DOC.REF.: (' || COMP.PCT_BENEFICIARIO || '%) - ' || --Nombre del Beneficiario
                        (SELECT NVL(COMPE.NUM_COMP_PAGO_E, NVL(COMPE.NUM_COMP_PAGO, 0))
                        FROM VTA_COMP_PAGO COMPE
                        WHERE COMPE.COD_GRUPO_CIA   = COMP.COD_GRUPO_CIA
                        AND   COMPE.COD_LOCAL     = COMP.COD_LOCAL
                        AND   COMPE.NUM_PED_VTA   = COMP.NUM_PED_VTA
                        AND  COMPE.NUM_COMP_PAGO   = COMP.NUM_COMP_COPAGO_REF) || 
                        ' - S/. ' ||TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO, 0), '999,999,990.00'))
                    END
                  --PARA BENEFICIARIO
                  WHEN CONV.COD_TIPO_CONVENIO != '2' THEN
                    --Numero de Documento Referencial
                    'BENEFICIARIO -' || COMP.PCT_BENEFICIARIO || '%' || '@' || 
                    CASE 
                      WHEN NVL(COMP.NUM_COMP_COPAGO_REF, '*') != '*' THEN
                        'DOC.REF.: (' || COMP.PCT_EMPRESA || '%) - ' || --Nombre del beneficiario
                        --Numero de Comprobante de Pago
                        (SELECT NVL(COMPE.NUM_COMP_PAGO_E, NVL(COMPE.NUM_COMP_PAGO, 0))
                        FROM VTA_COMP_PAGO COMPE
                        WHERE COMPE.COD_GRUPO_CIA   = COMP.COD_GRUPO_CIA
                        AND   COMPE.COD_LOCAL     = COMP.COD_LOCAL
                        AND   COMPE.NUM_PED_VTA   = COMP.NUM_PED_VTA
                        AND   COMPE.NUM_COMP_PAGO   = COMP.NUM_COMP_COPAGO_REF) || 
                        ' - S/. ' ||TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO, 0), '999,999,990.00'))
                    END
                  ELSE
                    'BENEFICIARIO - 100 %'
                END
              FROM VTA_PEDIDO_VTA_CAB CAB, 
                 MAE_CONVENIO CONV, 
                 VTA_COMP_PAGO COMP
              WHERE CAB.COD_GRUPO_CIA   = vCodGrupoCia_in
              AND   CAB.COD_LOCAL       = vCodLocal_in
              AND   CAB.NUM_PED_VTA     = vNumPedVta_in
              AND   CAB.COD_CONVENIO    = CONV.COD_CONVENIO
              AND   COMP.COD_GRUPO_CIA  = CAB.COD_GRUPO_CIA
              AND   COMP.COD_LOCAL      = CAB.COD_LOCAL
              AND   COMP.NUM_PED_VTA    = CAB.NUM_PED_VTA
              AND   COMP.SEC_COMP_PAGO  = vSecCompPago_in
            ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
        END IF;
      END IF;

      \**IMPRESION DEL MENSAJE DE REGARGA O PRODUCTO VIRTUAL*\
      BEGIN 
        SELECT DET.COD_PROD 
        INTO vCodProdVirtual
        FROM   VTA_PEDIDO_VTA_DET DET
        WHERE  DET.COD_GRUPO_CIA = vCodGrupoCia_in
        AND    DET.COD_LOCAL = vCodLocal_in
        AND    DET.NUM_PED_VTA = vNumPedVta_in
        AND EXISTS  (
                SELECT COD_PROD
                FROM LGT_PROD_VIRTUAL A
                WHERE A.TIP_PROD_VIRTUAL = 'R'
                AND A.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                AND A.COD_PROD = DET.COD_PROD
        )
        --INI ASOSA - 16/01/2015 - RIMAC
          AND NOT EXISTS (
                          SELECT COD_PROD
                          FROM LGT_PROD_RIMAC A
                          WHERE A.COD_GRUPO_CIA= DET.COD_GRUPO_CIA
                          AND A.COD_PROD = DET.COD_PROD
                          );
        --FIN ASOSA - 16/01/2015 - RIMAC
      EXCEPTION 
        WHEN OTHERS THEN
          vCodProdVirtual := NULL;
      END;
      IF NVL(vCodProdVirtual,'*') != '*' THEN 
        curdesc := FARMA_GRAL.CAJ_OBTIENE_MSJ_PROD_VIRT(vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in, vCodProdVirtual);
        LOOP
        FETCH curdesc INTO
              vLinea;
        EXIT WHEN curdesc%NOTFOUND;
          IF LENGTH(TRIM(vLinea)) != 0 THEN
            vLinea := REPLACE(vLinea,'�','@');
          --ELSE
            --vLinea := vLinea ||'@'|| REPLACE(vLinea,'�','@');
          END IF;
        END LOOP;
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
            vLinea
        ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
      END IF;
      
      --INI ASOSA - 13/01/2015 - RIMAC
            vIndRimac := PTOVENTA_RIMAC.GET_IND_EXISTE_rimac_ped(vCodGrupoCia_in,
                                                                                             NULL,
                                                                           vCodLocal_in ,
                                                                           vNumPedVta_in );
                   
                   IF vIndRimac = 'S' THEN
                                curdesc02 := PTOVENTA_RIMAC.CAJ_OBTIENE_MSJ_PROD_RIMAC(vCodGrupoCia_in, 
                                                                                                                                              vCodLocal_in, 
                                                                                                                                              vNumPedVta_in);
                                  LOOP
                                  FETCH curdesc02 INTO
                                        vLinea;
                                  EXIT WHEN curdesc02%NOTFOUND;
                                    IF LENGTH(TRIM(vLinea)) != 0 THEN
                                      vLinea := REPLACE(vLinea,'�','$$$');                                   
                                    END IF;
                                  END LOOP;
                                  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
                                  SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
                                      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
                                      vLinea
                                  ),'&','�')),'<','�'),'$$$','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
                   END IF;
      --FIN ASOSA - 13/01/2015 - RIMAC
      
      --INI ASOSA - 09/02/2015 - PTOSYAYAYAYA
        indPermitImip := PTOVENTA_FIDELIZACION.FID_F_GET_IND_UN_COMP(vCodGrupoCia_in,
                                                    vCodLocal_in,
                                                    vNumPedVta_in);

      IF indPermitImip = 'S' THEN

                PTOVENTA_FIDELIZACION.INS_IMP_PTOS(vCodGrupoCia_in,
                                              vCodLocal_in,
                                              vNumPedVta_in,
                                              vSecCompPago_in,
                                              'N',
                                              valorAhorro_in);
          
      END IF;
      
      --FIN ASOSA - 18/02/2015 - PTOSYAYAYAYA      
      
      \***   PDF417   ****\
      IF NVL(vPDF417, '*') != '*' THEN
         INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (vPDF417, '9','P','N','N');
      END IF; 
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
      
      \** PIE DE PAGINA **\    
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
        SELECT
       'Guarda tu voucher. '||--'@'||
       'Es el sustento para validar tu compra. '||--'@'||
       'Representacion impresa de la '||
       (CASE 
         WHEN vTipoDocumento IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN 'BOLETA ELECTRONICA '
         WHEN vTipoDocumento IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) THEN 'FACTURA ELECTRONICA '
         WHEN vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN 'NOTA DE CREDITO ELECTRONICA '
        END)
       ||--'@'||
       'puede ser consultado en www.mifarma.com.pe. '||--'@'||
       (SELECT  DESC_LARGA FROM PTOVENTA.PBL_TAB_GRAL  WHERE ID_TAB_GRAl='604')||--'@'||
       ' No se aceptan devoluciones de dinero. '||--'@'||
       'Cambio de mercaderia unicamente dentro '||--'@'||
       'de las 48 horas siguientes a la compra. '||--'@'||
       'Indispensable presentar comprobante.' ||'@'\*||
       '-'||'@'*\
      FROM DUAL
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
      
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE) VALUES('-','9','I','N','N');
      
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS  
      SELECT vVersion_in||' - USUARIO: ' ||USU.LOGIN_USU|| ' - CAJA: ' || MOV.NUM_CAJA_PAGO , '9','C','N','N'
      FROM CE_MOV_CAJA MOV,
           PBL_USU_LOCAL USU
      WHERE USU.COD_GRUPO_CIA = MOV.COD_GRUPO_CIA
      AND   USU.COD_LOCAL     = MOV.COD_LOCAL
      AND   USU.SEC_USU_LOCAL = MOV.SEC_USU_LOCAL
      AND   MOV.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   MOV.COD_LOCAL     = vCodLocal_in
      AND   MOV.SEC_MOV_CAJA  = vSecMovCaja;    
            
--      IF PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(COD_IND_EMI_CUPON) = 'S' THEN       --ASOSA - 10/04/2015 - NECUPYAYAYAYA      
      -- KMONCADA 27.04.2015 SE MODIFICA SELECT PARA DETERMINAR CANTIDAD DE CUPONES EMITIDOS
      SELECT COUNT(1)
      INTO cCantidadCupon
      FROM VTA_CAMP_PEDIDO_CUPON CUPON
      WHERE CUPON.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   CUPON.COD_LOCAL     = vCodLocal_in 
      AND   CUPON.NUM_PED_VTA   = vNumPedVta_in
      AND   CUPON.ESTADO        = 'E' 
      AND   CUPON.IND_IMPR      = 'S';   

      IF cCantidadCupon > 0 THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '9','I','N','N');
        --KMONCADA 02.06.2015 SOLO CUANDO LA TRXS SE HALLA REALIZADO CON TARJ.PUNTOS NO SE MOSTRARA
        IF vEstadoOrbis NOT IN (EST_ORBIS_ENVIADO,EST_ORBIS_PENDIENTE) THEN
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('POR SU COMPRA USTED GANO: ' || cCantidadCupon ||
                                                        (CASE
                                                           WHEN cCantidadCupon>1 THEN ' CUPONES DESCUENTO.'--ASOSA - 15/05/2015 - PTOSYAYAYAYA - SE AGREGO LA PALABRA "DESCUENTO"
                                                           ELSE ' CUPON DESCUENTO.'
                                                         END), '9','C','N','N');
        END IF;
      END IF;  
  --    END IF;                                                 
      
      \**IMPRIME LOS DATOS PARA LA NOTA DE CREDITO*\
      IF vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('DATOS DEL CLIENTE', '9','C','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('NOMBRE:_____________________________________________', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('DNI:________________________________________________', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('FIRMA:______________________________________________', '9','I','N','N');
      END IF;
      
      IF vCopagoCliente = 'S' THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '9','I','N','N');

        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
          SELECT 
            -- Codigo de Producto
            RPAD(NVL(DET.COD_PROD,' '),8)  ||
            --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
            RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,' ')),0,30),30) ||
            --Cantidad vendida en fracciones
            LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                        DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                               (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Imprime las cantidad
                               ABS(DET.CANT_ATENDIDA)) ,--sin fraccion
                        ABS(DET.CANT_ATENDIDA)),8) || 
            -- Descuento del Item(no incluye igv)
            LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL_BENEF),0),'999999990.00')),0),12)
            ,'9','I','N','N'
          FROM  VTA_PEDIDO_VTA_DET DET, 
              LGT_PROD PROD\*, LGT_PROD_VIRTUAL VIRT*\
          WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   DET.COD_LOCAL   = vCodLocal_in
          AND   DET.NUM_PED_VTA   = vNumPedVta_in
          AND  (CASE
              WHEN vTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
              WHEN vTipoClienteConvenio = DOC_EMPRESA AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
              ELSE DET.SEC_COMP_PAGO
              END) = vSecCompPago_in
          AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND   DET.COD_PROD     = PROD.COD_PROD;
        \*
            IF ( vTipoDocumento = COD_TIP_COMP_FACTURA  OR vTipoDocumento = COD_TIP_COMP_TICKET_FA  OR( vTipoDocumento = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer='1'))  THEN
               INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
                  SELECT 
                    -- Codigo de Producto
                    RPAD(NVL(DET.COD_PROD,' '),8)  ||
                    --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
                    RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,' ')),0,30),30) ||
                    --Cantidad vendida en fracciones
                    LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                       DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                        (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Imprime las cantidad
                                      ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','')) ,--sin fraccion
                       DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                                      (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Caso contrario las imprime en fracciones
                                      ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','/'||ABS(DET.VAL_FRAC)))),8) || 
                    -- Descuento del Item(no incluye igv)
                    LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_TOTAL_DESC_ITEM_E),0),'999999990.00')),0),12)
                    ,'1','I','N','N'
                  FROM  VTA_PEDIDO_VTA_DET DET, 
                      LGT_PROD PROD\*, LGT_PROD_VIRTUAL VIRT*\
                  WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
                  AND   DET.COD_LOCAL   = vCodLocal_in
                  AND   DET.NUM_PED_VTA   = vNumPedVta_in
                  AND  (CASE
                      WHEN vTipoClienteConvenio = '1' THEN DET.SEC_COMP_PAGO_BENEF
                      WHEN vTipoClienteConvenio = '2' THEN DET.SEC_COMP_PAGO_EMPRE
                      ELSE DET.SEC_COMP_PAGO
                      END) = vSecCompPago_in
                  AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                  AND   DET.COD_PROD     = PROD.COD_PROD;
                  
              ELSIF(vTipoDocumento = COD_TIP_COMP_BOLETA  OR   vTipoDocumento = COD_TIP_COMP_TICKET OR( vTipoDocumento = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer='3') ) THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
                  SELECT 
                    -- Codigo de Producto
                    RPAD(NVL(DET.COD_PROD,'-'),8) ||
                    --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
                    RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,'-')),0,30),30) ||
                    --Cantidad vendida en fracciones
                    LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                       DECODE(MOD(DET.CANT_ATENDIDA,DET.VAL_FRAC),0,
                                      (DET.CANT_ATENDIDA/DET.VAL_FRAC)||'',--Imprime las cantidad
                                      DET.CANT_ATENDIDA||DECODE(DET.VAL_FRAC,1,'','')) ,--sin fraccion
                       DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                                      (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Caso contrario las imprime en fracciones
                                      ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','/'||ABS(DET.VAL_FRAC)))),8)  || 
                    -- Descuento del Item(incluye IGV)
                    LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.AHORRO),0),'999,999,990.00')),0),12)
                    ,'1','I','N','N'
                  FROM  VTA_PEDIDO_VTA_DET DET, 
                      LGT_PROD PROD\*, LGT_PROD_VIRTUAL VIRT*\
                  WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
                  AND   DET.COD_LOCAL = vCodLocal_in
                  AND   DET.NUM_PED_VTA = vNumPedVta_in
                  AND   (CASE
                        WHEN vTipoClienteConvenio = '1' THEN DET.SEC_COMP_PAGO_BENEF
                        WHEN vTipoClienteConvenio = '2' THEN DET.SEC_COMP_PAGO_EMPRE
                        ELSE DET.SEC_COMP_PAGO
                      END) = vSecCompPago_in
                  AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                  AND   DET.COD_PROD     = PROD.COD_PROD;
              END IF;  *\
      END IF;
      
      -- KMONCADA 15.12.2014 REGISTRA EN CASO DE REIMPRESION DE DOCUMENTOS
      IF('S' = vReimpresion) THEN
        INSERT INTO REIMPRESION_VTA_COMP_PAGO_E 
        SELECT vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in, vSecCompPago_in, SEQ_REIMPR_VTA_COMP_PAGO_E.nextval, SYS_CONTEXT('USERENV', 'IP_ADDRESS'), SYSDATE
        FROM DUAL;
      END IF;
      
      OPEN cursorComprobante FOR
        SELECT A.VALOR, A.TAMANIO, A.ALINEACION, A.BOLD, A.AJUSTE
        FROM TMP_DOCUMENTO_ELECTRONICOS A
        WHERE A.VALOR IS NOT NULL;
      RETURN cursorComprobante;
  END;*/

FUNCTION GET_MARCA_LOCAL(vCodGrupoCia_in PBL_LOCAL.COD_GRUPO_CIA%TYPE,
                         vCodLocal_in    PBL_LOCAL.COD_LOCAL%TYPE)
RETURN PBL_LOCAL.COD_MARCA%TYPE
IS
 vCodMarca PBL_LOCAL.COD_MARCA%TYPE;
BEGIN
 
  SELECT COD_MARCA 
  INTO vCodMarca
  FROM PBL_LOCAL
  WHERE COD_GRUPO_CIA = vCodGrupoCia_in
  AND COD_LOCAL       = vCodLocal_in;
  
  RETURN vCodMarca;
EXCEPTION 
  WHEN OTHERS THEN
    vCodMarca := NULL;

END;
  

FUNCTION GET_TIP_COMP_SUNAT(vTipCompPago_Fv varchar2)
RETURN varchar2
IS
 vTipSunat varchar2(10);
BEGIN

  if vTipCompPago_Fv = '99' then
     RAISE_APPLICATION_ERROR(-20000,'Tip Comp Pago no es V�lido 99');
  end if; 

  SELECT CASE vTipCompPago_Fv
                WHEN '02' THEN
                 '01' -- factura
                WHEN '06' THEN
                 '01' -- ticket factura
                WHEN '01' THEN
                 '03' -- boleta
                WHEN '05' THEN
                 '03' -- ticket boleta
                WHEN '04' THEN
                 '07' -- nota de credito
          end 
  into vTipSunat               
  from dual;                                  
  
  RETURN vTipSunat;

END;

  -- Author  : LTAVARA
  -- Created : 17/11/2014 06:20:30 pm
  -- Proposito : TAG PERSONALIZADOS
  /***************************************************************************/
  FUNCTION F_VAR_TRM_GET_MSJ_PERSONALIZA(cGrupoCia      VARCHAR2,
                         cCodLocal      VARCHAR2,
                         cNumPedidoVta  VARCHAR2,
                         cSecCompPago   VARCHAR2,
                         cTipoDocumento VARCHAR2,
                        cTipoClienteConvenio VARCHAR2 DEFAULT '0'
                                ) RETURN VARCHAR2 AS
    v_vPES_DT VARCHAR2(1000) ;
    
  BEGIN
  
 v_vPES_DT := v_vPES_DT || 'PE|CODLOCAL|'||cCodLocal || CHR(13);
    
     SELECT v_vPES_DT || 'PE|DIRLOCAL|' || TRIM(A.DIREC_LOCAL_CORTA) || CHR(13)
           || 'PE|Telefono|' || TRIM(C.TELF_CIA) || CHR(13)
    INTO   v_vPES_DT
    FROM PBL_LOCAL A , PBL_CIA C
    WHERE 
    C.COD_CIA=A.COD_CIA
    AND A.COD_GRUPO_CIA = cGrupoCia 
    AND A.COD_LOCAL       = cCodLocal;
    
 -- 23.12.2014 LTAVARA 


    BEGIN
      SELECT v_vPES_DT || 'PE|DesCopago|COPAGO ' || TRIM(PAGO.PCT_EMPRESA) || '%' ||
             CHR(13) --23.12.2014 LTAVARA
             || 'PE|MtoCopago|' || TO_CHAR(TRIM(PAGO.Val_Copago_e),'99999999.00') || CHR(13) --23.12.2014 LTAVARA
      
        INTO v_vPES_DT
      
        FROM VTA_COMP_PAGO PAGO
      
       WHERE PAGO.PCT_BENEFICIARIO > 0
            
         AND PAGO.PCT_EMPRESA > 0
         AND PAGO.COD_GRUPO_CIA = cGrupoCia
         AND PAGO.COD_LOCAL = cCodLocal
         AND PAGO.NUM_PED_VTA = cNumPedidoVta
         AND PAGO.SEC_COMP_PAGO = cSecCompPago
         AND PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_vPES_DT := v_vPES_DT || 'PE|DesCopago|' || CHR(13) --23.12.2014 LTAVARA
                     || 'PE|MtoCopago|0' || CHR(13); --23.12.2014 LTAVARA
    END;


   -- FIN 23.12.2014 LTAVARA
  
 
 
    RETURN v_vPES_DT;
  
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
  
  FUNCTION F_MSJ_CONTIGENCIA( vCodGrupoCia_in IN CHAR,
                              vCodLocal_in    IN CHAR,
                              vNumPedVta_in   IN CHAR,
                              vCodTipoDoc_in  IN CHAR) 
  RETURN FARMACURSOR IS
    
    
    vComprobante              VARCHAR2(50);
    vResultado                VARCHAR2(200);
    vIp                       VTA_PEDIDO_VTA_CAB.IP_PC%TYPE;
    vCodTipoDocClienteMF      VARCHAR2(20);
    vCodTipoDocBeneficiarioMF VARCHAR2(20);
    vSecImpresora             VTA_IMPR_LOCAL.SEC_IMPR_LOCAL%TYPE;
    curComp                   FarmaCursor;
    vMsjeImpresionUno         VARCHAR2(4000);
    vMsjeImpresionDos         VARCHAR2(4000);
    vMsjeImpresionTres        VARCHAR2(4000);
    vMsjeImpresionCuaro       VARCHAR2(4000);
    vCodConvenio              VTA_PEDIDO_VTA_CAB.COD_CONVENIO%TYPE;
  
  BEGIN
  
    SELECT NVL(COD_CONVENIO,'*'), CAB.IP_PC
    INTO   vCodConvenio, vIp
    FROM VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = vCodGrupoCia_in
    AND   CAB.COD_LOCAL     = vCodLocal_in
    AND   CAB.NUM_PED_VTA   = vNumPedVta_in;
    
    IF vCodConvenio != '*' THEN
      SELECT 
        CASE 
          WHEN NVL(TRIM(CONV.COD_TIPDOC_CLIENTE),'*')!='*' THEN 
               (SELECT TIP_COMP_PAGO FROM MAE_TIPO_COMP_PAGO_BTLMF
                WHERE COD_TIPODOC=CONV.COD_TIPDOC_CLIENTE)
          ELSE
            ''
        END CLIENTE, 
        CASE 
          WHEN NVL(TRIM(CONV.COD_TIPDOC_BENEFICIARIO),'*')!='*' THEN 
               (SELECT TIP_COMP_PAGO FROM MAE_TIPO_COMP_PAGO_BTLMF
                WHERE COD_TIPODOC=CONV.COD_TIPDOC_BENEFICIARIO)
          ELSE
            ''
        END BENEFICIARIO
        INTO vCodTipoDocClienteMF, vCodTipoDocBeneficiarioMF
        FROM MAE_CONVENIO CONV
       WHERE CONV.COD_CONVENIO = vCodConvenio;
       
       vCodTipoDocBeneficiarioMF := CASE 
                                   WHEN vCodTipoDocBeneficiarioMF = COD_TIP_COMP_TICKET THEN COD_TIP_COMP_BOLETA
                                   WHEN vCodTipoDocBeneficiarioMF = COD_TIP_COMP_TICKET_FA THEN COD_TIP_COMP_FACTURA
                                   ELSE vCodTipoDocBeneficiarioMF END;
                                   
       vCodTipoDocClienteMF :=     CASE 
                                   WHEN vCodTipoDocClienteMF = COD_TIP_COMP_TICKET THEN COD_TIP_COMP_BOLETA
                                   WHEN vCodTipoDocClienteMF = COD_TIP_COMP_TICKET_FA THEN COD_TIP_COMP_FACTURA
                                   ELSE vCodTipoDocClienteMF END;
    
    ELSE
      vCodTipoDocBeneficiarioMF := vCodTipoDoc_in;
    END IF;
    
    -- VALIDA QUE NO SE EMITA TICKETS BOLETA EN MODO CONTINGENCIA
    IF NVL(vCodTipoDocBeneficiarioMF,'00') = COD_TIP_COMP_TICKET OR NVL(vCodTipoDocClienteMF,'00')= COD_TIP_COMP_TICKET THEN
      RAISE_APPLICATION_ERROR(-20001, 'NO SE PERMITE EMITIR TICKET BOLETA, VERIFIQUE LA CONFIGURACION DE IMPRESORAS.');
    END IF;

    IF vCodTipoDocBeneficiarioMF IS NOT NULL THEN
      
      BEGIN 
        SELECT 
         CASE
           WHEN A.TIP_COMP = vCodTipoDocBeneficiarioMF THEN
             A.SEC_IMPR_LOCAL
           WHEN A.TIP_COMP_FAC = vCodTipoDocBeneficiarioMF THEN
             A.SEC_IMPR_LOCAL_FAC
           ELSE
             -1
         END
        INTO vSecImpresora
        FROM VTA_IMPR_IP A
        WHERE A.IP = vIp;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20002,'ERROR AL OBTENER COMPROBANTES DEL PUNTO DE VENTA');
      END;
      
      IF vCodTipoDocBeneficiarioMF = COD_TIP_COMP_GUIA THEN
        SELECT A.SEC_IMPR_LOCAL
        INTO vSecImpresora
        FROM VTA_IMPR_LOCAL A
        WHERE A.COD_GRUPO_CIA = vCodGrupoCia_in
        AND   A.COD_LOCAL     = vCodLocal_in
        AND   A.TIP_COMP      = vCodTipoDocBeneficiarioMF
        AND   ROWNUM          = 1;
      END IF;
      
      IF vSecImpresora != -1 THEN
        SELECT IMP_LOCAL.DESC_IMPR_LOCAL||': '||IMP_LOCAL.NUM_SERIE_LOCAL||'-'||IMP_LOCAL.NUM_COMP
        INTO   vComprobante
        FROM VTA_IMPR_LOCAL IMP_LOCAL
        WHERE IMP_LOCAL.COD_GRUPO_CIA  = vCodGrupoCia_in
        AND   IMP_LOCAL.COD_LOCAL      = vCodLocal_in
        AND   IMP_LOCAL.SEC_IMPR_LOCAL = vSecImpresora;
      ELSE
        RAISE_APPLICATION_ERROR(-20003,'ERROR AL OBTENER EL NRO DEL COMPROBANTE -->'||vCodTipoDocBeneficiarioMF);
      END IF;
      vResultado := vComprobante;
    END IF;

    IF vCodTipoDocClienteMF IS NOT NULL THEN
      BEGIN 
        SELECT 
         CASE
           WHEN A.TIP_COMP = vCodTipoDocClienteMF THEN
             A.SEC_IMPR_LOCAL
           WHEN A.TIP_COMP_FAC = vCodTipoDocClienteMF THEN
             A.SEC_IMPR_LOCAL_FAC
           ELSE
             -1
         END
        INTO vSecImpresora
        FROM VTA_IMPR_IP A
        WHERE A.IP = vIp;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20004,'ERROR AL OBTENER COMPROBANTES DEL PUNTO DE VENTA');
      END;
      
      IF vCodTipoDocClienteMF = COD_TIP_COMP_GUIA THEN
        SELECT A.SEC_IMPR_LOCAL
        INTO vSecImpresora
        FROM VTA_IMPR_LOCAL A
        WHERE A.COD_GRUPO_CIA = vCodGrupoCia_in
        AND   A.COD_LOCAL     = vCodLocal_in
        AND   A.TIP_COMP      = vCodTipoDocClienteMF
        AND   ROWNUM          = 1;
      END IF;
      
      IF vSecImpresora != -1 THEN
        
        SELECT IMP_LOCAL.DESC_IMPR_LOCAL||': '||IMP_LOCAL.NUM_SERIE_LOCAL||'-'||IMP_LOCAL.NUM_COMP
        INTO   vComprobante
        FROM VTA_IMPR_LOCAL IMP_LOCAL
        WHERE IMP_LOCAL.COD_GRUPO_CIA  = vCodGrupoCia_in
        AND   IMP_LOCAL.COD_LOCAL      = vCodLocal_in
        AND   IMP_LOCAL.SEC_IMPR_LOCAL = vSecImpresora;
        IF vResultado IS NOT NULL THEN
          vResultado := vResultado||'<br>';
        END IF;
        vResultado := vComprobante;
      ELSE
        RAISE_APPLICATION_ERROR(-20005,'ERROR AL OBTENER EL NRO DEL COMPROBANTE -->'||vCodTipoDocClienteMF);
      END IF;
    END IF;
    
    -- SE CREA EL MENSAJE
     vMsjeImpresionUno := '  <table cellpadding=0 cellspacing=0 align=left> '||
                          '   <tr> '||
                          '    <td></td> '||
                          '    <td width=638 height=383 bgcolor=white style="border:.75pt solid black; vertical-align:top;background:white">'||
                          '        <span style="position:absolute;mso-ignore:vglayout;z-index:1"> '||
                          '         <table cellpadding=0 cellspacing=0 width=100%> '||
                          '          <tr> '||
                          '           <td> '||
                          '             <div v:shape=_x0000_s1026 style="padding:4.35pt 7.95pt 4.35pt 7.95pt" class=shape> '||
                          '                <p class=MsoNormal align=center style="text-align:center">'||
                          '                  <span lang=ES style="font-size:48.0pt;font-family:Arial,sans-serif;color:#C00000">' ||
                          '                    <font color=#C00000 size =8 face=Arial>�� ALERTA !!</font>'||
                          '                  </span>'||
                          '                </p>'/*||
                          '                <p></p> '*/;
                                
     vMsjeImpresionDos := '                <p class=MsoNormal align=center style="text-align:center">'||
                          '                  <b>'||
                          '                    <span lang=ES style="font-size:26;font-family:Arial,sans-serif;color:#C00000">' ||
                          '                      <font color=#139128 size =6 face=Arial>POR EL MOMENTO NO SE PODRA GENERAR COMPROBANTES ELECTRONICOS</font><o:p></o:p>'||
                          '                    </span>'||
                          '                  </b>'||
                          '                </p>'||
                          '                <p class=MsoNormal align=center style="text-align:center">'||
                          '                  <b>'||
                          '                    <span lang=ES style="font-size:26.0pt;font-family:Arial,sans-serif"><o:p>'||
                          '                      <font color=#FF0000 size =5 face=Arial>POR FAVOR, VERIFIQUE QUE LOS DOCUMENTOS DISPONIBLES PARA IMPRESION SON:'||
                          '                      </font></o:p>'||
                          '                    </span>'||
                          '                  </b>'||
                          '                </p> '||
                          '                <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:0.0pt; mso-list:l0 level1 lfo1"> '||
                          '                  <b>'||
                          '                    <span lang=ES style="font-size:18.0pt; font-family:Arial,sans-serif;color:#139128">'||
                          '                      <font color=#139128 size =5 face=Arial>'||vResultado||'</font>'||
                          '                    </span>'|| 
                          '                  </b>'||
                          '                </p> '||

                          '                <p class=MsoNormal>'||
                          '                  <b>'||
                          '                    <span lang=ES style="font-family:Arial,sans-serif; color:#139128">�</span>'||
                          '                  </b>'||
                          '                  <b>'||
                          '                    <span lang=ES style="font-size:18.0pt; font-family:Arial,sans-serif;color:#139128">' ||
                          '                      <font color=#139128 size =4 face=Arial>Revisar:</font>' ||
                          '                      <o:p></o:p>'||
                          '                    </span>'||
                          '                  </b>'||
                          '                </p> '||
                          '                <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; mso-list:l0 level1 lfo1">'||
                          '                  <![if !supportLists]>'||
                          '                    <span lang=ES style="font-size:18.0pt;font-family:Symbol;color:#139128">'||
                          '                      <span style="mso-list:Ignore">�'||
                          '                       <span style="font:7.0pt Times New Roman">;;;;;; </span>'||
                          '                      </span>'||
                          '                    </span>'||
                          '                  <![endif]>'||
                          '                  <b>'||
                          '                    <span lang=ES style="font-size:18.0pt; font-family:Arial,sans-serif;color:#139128">'||
                          '                      <font color=#139128 size =4 face=Arial>Si esta prendida la ';
     vMsjeImpresionTres := '      impresora.</font>' ||
                          '  <o:p></o:p></span></b></p> '||
                        '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                        '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                        '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                        '      style="mso-list:Ignore">�<span style="font:7.0pt Times New Roman">;;;;;; '||
                        '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                        '      font-family:Arial,sans-serif;color:#139128"><font color=#139128 size =4 face=Arial>Si cuenta con papel para '||
                        '      imprimir .</font><o:p></o:p></span></b></p> '||
                        '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                        '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                        '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                        '      style="mso-list:Ignore">�<span style="font:7.0pt Times New Roman">;;;;;; ';
    vMsjeImpresionCuaro :=   '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                        '      font-family:Arial,sans-serif;color:#139128">' ||
                        '  <font color=#139128 size =4 face=Arial>Si el Correlativo del '||
                        '      comprobante es el mismo que el sistema.</font><o:p></o:p></span></b></p> '||
                        '      <p class=MsoNormal><span lang=ES style="font-family:Arial,sans-serif">*****************************************************************************************<o:p></o:p></span>' ||
                        '      <table  >'||
                        '       <tr >'||
                        '         <td  valign="middle" >'||
                        '           <b>presione [Enter] para Continuar...</b>'||
                        '         </td>'||
                        '         <td width="250px" valign="middle" align="right" >'||
                        '           <b>presione [F5] para Corregir Correlativo...</b>'||
                        '         </td>'||
                        '       </tr>'||
                        '      </table>'||
                        '      </o:p></p> '||
                        '      </div> '||
                        '      <![if !mso]></td> '||
                        '     </tr> '||
                        '    </table> '||
                        '    </span></td> '||
                        '   </tr> '||
                        '  </table> ';

      open curComp for
      SELECT
             vMsjeImpresionUno   AS UNO ,
             vMsjeImpresionDos   AS DOS,
             vMsjeImpresionTres  AS TRES  ,
             vMsjeImpresionCuaro AS CUATRO
        FROM DUAL;
       RETURN curComp;
  END;
  
  FUNCTION VALIDA_IMPRESION_PENDIENTE (cCodGrupoCia_in    IN CHAR,
                                     cCodLocal_in         IN CHAR,
                                     cNumPedVta_in        IN CHAR,           
                                     cSecCompPago_in      IN CHAR,
                                     cCodTipoDocumento_in IN CHAR)
  RETURN FARMACURSOR IS
 
    vIndicadorImpr CHAR(1);
    curComp        FarmaCursor;
    vIndCompElectr CHAR(1);
  BEGIN
  
    BEGIN  
      SELECT LLAVE_TAB_GRAL
      INTO   vIndicadorImpr
      FROM PBL_TAB_GRAL
      WHERE ID_TAB_GRAL = COD_IND_VALIDA_IMPRESION;
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        vIndicadorImpr := 'N';
    END;
    
    SELECT NVL(CP.COD_TIP_PROC_PAGO,'0')
    INTO   vIndCompElectr
    FROM VTA_COMP_PAGO CP
    WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   CP.COD_LOCAL     = cCodLocal_in
    AND   CP.NUM_PED_VTA   = cNumPedVta_in
    AND   CP.SEC_COMP_PAGO = cSecCompPago_in
    AND   CP.TIP_COMP_PAGO = cCodTipoDocumento_in ;
    
    IF (vIndicadorImpr = 'S' AND vIndCompElectr = '0') THEN
      OPEN curComp FOR
        SELECT A.NUM_COMP_PAGO COMPROBANTE
        FROM VTA_COMP_PAGO A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND   A.COD_LOCAL     = cCodLocal_in
        AND   A.TIP_COMP_PAGO = cCodTipoDocumento_in
        AND   NVL(A.COD_TIP_PROC_PAGO,'0') != '1'
        AND   A.FEC_CREA_COMP_PAGO >= TRUNC(SYSDATE)
        AND   A.SEC_COMP_PAGO != cSecCompPago_in
        AND   A.FECH_IMP_COBRO IS NULL
        ORDER BY A.FEC_CREA_COMP_PAGO DESC;
    ELSE
      open curComp for
        SELECT
          'N' COMPROBANTE
        FROM DUAL;
    END IF;
    
    RETURN curComp;
  END;
  
  PROCEDURE COMP_PAGO_CAMBIA_FLAG_E(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumPedVta_in    IN CHAR,
                                  cSecCompPago_in  IN CHAR)
  IS
  BEGIN
    UPDATE VTA_COMP_PAGO CP
    SET CP.COD_TIP_PROC_PAGO='0'
    WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
    AND   CP.COD_LOCAL     = cCodLocal_in
    AND   CP.NUM_PED_VTA   = cNumPedVta_in
    AND   CP.SEC_COMP_PAGO = cSecCompPago_in;
  END;


  FUNCTION REEMPLAZA_CARACTERES (cTexto IN VARCHAR2)
    RETURN VARCHAR2 IS
    vResultado VARCHAR2(2000);
  BEGIN
    
    vResultado := cTexto;
    vResultado := REPLACE(vResultado,CHR(10)||CHR(13), ' ');
    vResultado := REPLACE(vResultado,CHR(13), ' ');
    vResultado := REPLACE(vResultado,CHR(9), ' ');
    vResultado := REPLACE(vResultado,CHR(10), ' ');
    vResultado := REPLACE(vResultado,'|', ' ');
   
    vResultado := TRIM(vResultado);
    return vResultado;
  END;
  
  /***************************************************************************/
  
  /*PROCEDURE INS_AHORRO_INMED(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumPedVta_in    IN CHAR,
                                  cSecCompPago_in  IN CHAR)
  IS
    dscto VARCHAR2(30) := '';
    msg varchar2(100) := '';
    codMsg number(3) := '497';
    cantPedAnulado number(3) := 0;
  BEGIN
    
     SELECT COUNT(1)
     INTO cantPedAnulado
     FROM VTA_PEDIDO_VTA_CAB C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
     AND C.COD_LOCAL = cCodLocal_in
     AND C.NUM_PED_VTA = cNumPedVta_in
     AND C.NUM_PED_VTA_ORIGEN IS NOT NULL;
     
     IF cantPedAnulado = 0 THEN
    
        dscto := FN_VAL_DSCTO_AHORRO_INMED(cCodGrupoCia_in,
                                          cCodLocal_in,
                                          cNumPedVta_in,
                                          cSecCompPago_in);
        
        IF dscto <> '0.00' THEN
        
            select decode(dscto,'0.00','    ',dscto)
            into dscto
            from dual;
            
            msg := PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(codMsg) || ':';
            
            msg := msg || ' S/.' || RPAD('-' || dscto,15,' ');
          
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE) 
            VALUES(msg,'9','D','N','N');
        
        END IF;
    END IF;
  
  END;*/
  
  /***************************************************************************/
  
  FUNCTION FN_IS_NOTA_CREDITO(cCodGrupoCia_in    IN CHAR,
                                     cCodLocal_in         IN CHAR,
                                     cNumPedVta_in        IN CHAR)
  RETURN CHAR IS
 
    vIndicador CHAR(1) := 'S';
    cantPedAnulado number(3) := 0;
  BEGIN
    
     SELECT COUNT(1)
     INTO cantPedAnulado
     FROM VTA_PEDIDO_VTA_CAB C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
     AND C.COD_LOCAL = cCodLocal_in
     AND C.NUM_PED_VTA = cNumPedVta_in
     AND C.NUM_PED_VTA_ORIGEN IS NOT NULL;

     IF cantPedAnulado = 0 THEN
        vIndicador := 'N';
     END IF;
    
    RETURN vIndicador;
  END;
  
  /***************************************************************************/
  
  FUNCTION FN_VAL_DSCTO_AHORRO_INMED(cCodGrupoCia_in    IN CHAR,
                                     cCodLocal_in         IN CHAR,
                                     cNumPedVta_in        IN CHAR,
                                    cSecCompPago_in  IN CHAR)
  RETURN VARCHAR2 IS
 
   dscto VARCHAR2(30) := '';
  BEGIN
    
     SELECT TO_CHAR(ROUND(SUM(NVL(D.AHORRO_CAMP,0)),2),
                    '999,999.99')
        INTO dscto
        FROM VTA_PEDIDO_VTA_DET D
        WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL = cCodLocal_in
        AND D.NUM_PED_VTA = cNumPedVta_in
        AND D.SEC_COMP_PAGO = cSecCompPago_in;
        
        dscto := trim(dscto);
        
        dscto := PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED_02(to_number(dscto,'999,999.99'));
    
    RETURN dscto;
  END;
  
  /***************************************************************************/
  
  FUNCTION IMP_COMP_ELECT( vCodGrupoCia_in    IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                           vCodLocal_in       IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                           vNumPedVta_in      IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                           vSecCompPago_in    IN VTA_PEDIDO_VTA_CAB.SEC_COMP_PAGO%TYPE,
                           vVersion_in        IN REL_APLICACION_VERSION.NUM_VERSION%TYPE,
                           vReimpresion       IN CHAR DEFAULT 'N',
                           valorAhorro_in     IN NUMBER, --ASOSA - 24/03/2015 - PTOSYAYAYAYA - SE DEFINIO QUE ESTUVIERA EN JAVA X ESO ES PARAMETRO
                           -- KMONCADA 23.06.2015 NUMERO DE DOCUMENTO DE LA TARJETA DE PUNTOS
                           cDocTarjetaPtos_in IN VARCHAR2 DEFAULT NULL
                           )
  RETURN FARMACURSOR
  IS 
                                             
   vCopagoEmpresa       CHAR(1);
   vCopagoCliente       CHAR(1);
   vTipoDocumento       VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE;
   vTipoDocRefer        CHAR(1);
   vTip_Ped_vta         VTA_PEDIDO_VTA_CAB.TIP_PED_VTA%TYPE;
   vTipoClienteConvenio VTA_COMP_PAGO.TIP_CLIEN_CONVENIO%TYPE;
   vMontoAhorro         CHAR(20);
   vPedidoConvenio      VTA_PEDIDO_VTA_CAB.IND_PED_CONVENIO%TYPE;
   vCodProdVirtual      VTA_PEDIDO_VTA_DET.COD_PROD%TYPE;
   curdesc              FARMACURSOR;
      curdesc02              FARMACURSOR;                --ASOSA - 13/01/2015 - RIMAC
      vIndRimac              char(1) := 'N';               --ASOSA - 13/01/2015 - RIMAC
   vLinea               VARCHAR2(4000);
   vPDF417              VTA_COMP_PAGO.COD_PDF417_E%TYPE;
   cCantidadCupon       INTEGER DEFAULT 0;
   cursorComprobante    FARMACURSOR;
   vSecMovCaja          VTA_PEDIDO_VTA_CAB.SEC_MOV_CAJA%TYPE;
   vDescuentoNC         VTA_COMP_PAGO.VAL_COPAGO_E%TYPE;
   
   vAuxNumPedVta        VTA_COMP_PAGO.NUM_PED_VTA%TYPE;
   vAuxSecCompPago      VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
   
   --INI ASOSA - 09/02/2015 - PTOSYAYAYAYA
   
   indPermitImip CHAR(1) := '';   

   --FIN ASOSA - 18/02/2015 - PTOSYAYAYAYA
   
   -- dubilluz 13.01.2015
   nPermiteImp char(1);
   vReimprime CHAR(1) := 'N';
--   cursorExperto    FARMACURSOR;   --ASOSA - 23/03/2015 - PTOSYAYAYAYA - CONSECUENCIA DE ESTAR CAMBIANDO VARIAS VECES
   ahorroF VARCHAR2(20) := '';
   vAhorroTotalPedido   VTA_PEDIDO_VTA_DET.AHORRO_PUNTOS%TYPE;
--   ahorroPtos           VTA_PEDIDO_VTA_DET.AHORRO_PUNTOS%TYPE;
   vEstadoOrbis         VTA_PEDIDO_VTA_CAB.EST_TRX_ORBIS%TYPE;
   vNroTarjetaPuntos    VTA_PEDIDO_VTA_CAB.NUM_TARJ_PUNTOS%TYPE;
   vNroDNIPto           VTA_PEDIDO_VTA_CAB.DNI_CLI%TYPE;
   vNombreClientePto    VTA_PEDIDO_VTA_CAB.NOM_CLI_PED_VTA%TYPE;
   vPtoAcumulado        VTA_PEDIDO_VTA_CAB.PT_ACUMULADO%TYPE;
   vPtoRedimido         VTA_PEDIDO_VTA_CAB.PT_REDIMIDO%TYPE;
   vPtoTotal            VTA_PEDIDO_VTA_CAB.PT_TOTAL%TYPE;
   vPtoInicial          VTA_PEDIDO_VTA_CAB.PT_INICIAL%TYPE;    --ASOSA - 08/05/2015 - PTOSYAYAYAYA
--   vSufijoAhorroSoles   VARCHAR2(200);
--   vSufijoPtoAcumula    VARCHAR2(200);
--   vSufijoPtoTotal      VARCHAR2(200);
--   vTextoPtoInicial     VARCHAR2(200);  --ASOSA - 08/05/2015 - PTOSYAYAYAYA
   vIndProgPuntos       CHAR(3);
   vValorAhorroSoles    NUMBER(12,3);
   vAhorroInmediato NUMBER(12,3);
   
   vIdDoc IMPRESION_TERMICA.ID_DOC%type;
   vIpPc  IMPRESION_TERMICA.IP_PC%type;    
   
   
  CURSOR curCabecera is
    SELECT CABECERA.VAL VALOR ,
       CASE 
         WHEN ROWNUM = 5 THEN FARMA_PRINTER.BOLD_ACT
         WHEN ROWNUM = 6 AND vReimprime = 'S' THEN FARMA_PRINTER.BOLD_ACT
         ELSE FARMA_PRINTER.BOLD_DESACT
       END NEGRITA,
       CASE 
         WHEN ROWNUM <= 5 THEN FARMA_PRINTER.ALING_CEN
         WHEN ROWNUM = 6 AND vReimprime = 'S' THEN FARMA_PRINTER.ALING_CEN
         ELSE FARMA_PRINTER.ALING_IZQ
       END ALINEACION
    FROM (
       SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL --, '9','I','N','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE(( 
           
           SELECT
              NVL(CIA.RAZ_SOC_CIA, ' ')||' - RUC: '||NVL(CIA.NUM_RUC_CIA, ' ')  ||'@'||
              TRIM(SUBSTR(TRIM(CIA.DIR_CIA),0,INSTR(TRIM(CIA.DIR_CIA), ' - '||UBI.NODIS ||' - '|| UBI.NOPRV)))||'@'||
              (UBI.NODIS ||' - '|| UBI.NOPRV)||' TELF.: '||NVL(CIA.TELF_CIA, ' ') ||'@'||
              NVL(PED.COD_LOCAL,' ')||' - '||NVL(LOC.DIREC_LOCAL_CORTA, ' ') ||'@' ||
              CASE 
               WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN 'BOLETA ELECTRONICA'
               WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) THEN 'FACTURA ELECTRONICA'
               WHEN COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN 'NOTA DE CREDITO ELECTRONICA'
              END || ' ' || 
              FARMA_UTILITY.GET_T_COMPROBANTE_2(COMP.COD_TIP_PROC_PAGO, COMP.NUM_COMP_PAGO_E, COMP.NUM_COMP_PAGO) ||
              CASE 
               WHEN vReimprime = 'S' THEN '@'||'COPIA DE COMPROBANTE'
              END||'@'||
              'FECHA DE EMISION: '||NVL(TO_CHAR(COMP.FEC_CREA_COMP_PAGO, 'dd/mm/yyyy HH24:MI:SS'), ' ') ||
              -- CONVENIOS CON GUIA
              CASE 
               WHEN COMP.TIP_COMP_PAGO_REF = COD_TIP_COMP_GUIA THEN '@'||'NUM.GUIA: '||COMP.NUM_COMP_COPAGO_REF 
               --ELSE '@'||''
              END ||
              -- DATOS DE VENTA EMPRESA
              CASE 
               WHEN PED.TIP_PED_VTA = TIPO_VTA_INSTITUCIONAL THEN
                  (SELECT 
                    CASE 
                      WHEN LENGTH(TRIM(EMP.NUM_OC)) != NULL OR LENGTH(TRIM(EMP.NUM_OC)) != 0  THEN 
                        '@'||'ORD.COMPRA: '||TRIM(EMP.NUM_OC)
                    END ||
                      CASE 
                        WHEN LENGTH(TRIM(EMP.COD_POLIZA)) != NULL OR LENGTH(TRIM(EMP.COD_POLIZA)) != 0  THEN 
                          '@'||'NUM.POLIZA: '||TRIM(EMP.COD_POLIZA)||' - '||TRIM(EMP.NOMBRE_CLIENTE_POLIZA)
                      END
                  FROM  VTA_PEDIDO_VTA_CAB_EMP EMP 
                  WHERE EMP.COD_GRUPO_CIA=PED.COD_GRUPO_CIA
                  AND   EMP.COD_LOCAL = PED.COD_LOCAL
                  AND   EMP.NUM_PED_VTA = PED.NUM_PED_VTA)
              END ||
              -- MOTIVO EN NOTAS DE CREDITO GENERADAS
              CASE 
                WHEN COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN 
                  '@'||'MOTIVO ANULACION: '|| 
                  NVL(PED.MOTIVO_ANULACION,'-')||'@'||
                  (CASE 
                     WHEN NVL(COMP.NUM_COMP_PAGO_EREF, '*')!='*' THEN
                       'DOC.REFERENCIA: '||
                       FARMA_UTILITY.GET_DESC_COMPROBANTE( COMP.COD_GRUPO_CIA, 
                                                             (SELECT A.TIP_COMP_PAGO 
                                                              FROM VTA_COMP_PAGO A 
                                                              WHERE A.COD_GRUPO_CIA=COMP.COD_GRUPO_CIA 
                                                              AND A.COD_LOCAL=COMP.COD_LOCAL 
                                                              AND A.NUM_PED_VTA=PED.NUM_PED_VTA_ORIGEN
                                                              AND A.SEC_COMP_PAGO=PED.SEC_COMP_PAGO)
                                                           )||
                       ' '|| NVL(COMP.NUM_COMP_PAGO_EREF, '-')--||'@'
                   END)
              END 
        FROM VTA_PEDIDO_VTA_CAB  PED,
             VTA_COMP_PAGO       COMP,
             PBL_CIA             CIA,
             UBIGEO              UBI,
             PBL_LOCAL           LOC
        WHERE PED.COD_GRUPO_CIA   = COMP.COD_GRUPO_CIA
        AND   PED.COD_LOCAL       = COMP.COD_LOCAL
        AND   PED.NUM_PED_VTA     = COMP.NUM_PED_VTA
        AND   CIA.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   CIA.COD_CIA         = PED.COD_CIA
        AND   UBI.UBDEP           = CIA.UBDEP
        AND   UBI.UBPRV           = CIA.UBPRV
        AND   UBI.UBDIS           = CIA.UBDIS
        AND   LOC.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   LOC.COD_LOCAL       = PED.COD_LOCAL
        AND   COMP.COD_GRUPO_CIA  = vCodGrupoCia_in
        AND   COMP.COD_LOCAL      = vCodLocal_in
        AND   COMP.NUM_PED_VTA    = vNumPedVta_in
        AND   COMP.SEC_COMP_PAGO  = vSecCompPago_in
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt
    ) CABECERA ;
  
  filaCurCabecera curCabecera%ROWTYPE;
  
  CURSOR curDetalleCopago IS
    SELECT 
      RPAD(NVL(FARMA_EPOS.F_VAR_COD_SERV_PED(PAGO.COD_GRUPO_CIA, PAGO.COD_LOCAL, PAGO.NUM_PED_VTA, PAGO.SEC_COMP_PAGO), ' '),8)||
      RPAD(SUBSTR(FARMA_EPOS.F_VAR_DSC_PROD_SERV(PAGO.COD_GRUPO_CIA,PAGO.COD_LOCAL),0,17),18) ||
      LPAD('1',10) ||
      LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                              WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND PAGO.PCT_BENEFICIARIO > 0 AND PAGO.PCT_EMPRESA > 0 THEN
                                                                ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                              ELSE
                                                                0
                                                             END),
                             0),
                        '999,999,990.000')
                )
           ,9)||
       --LPAD('0.000',8) ||  
       LPAD('     ',8) ||    --ASOSA - 15/05/2015 - PTOSYAYAYAYA - QUIEREN QUE NO SE VEAN LOS CEROS
       LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                               WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND PAGO.PCT_BENEFICIARIO > 0 AND PAGO.PCT_EMPRESA > 0 THEN
                                                                 ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                               ELSE
                                                                 0
                                                             END),
                             0),
                         '999,999,990.00')
                 )
           ,11) ITEM
       FROM  VTA_COMP_PAGO PAGO
       WHERE PAGO.COD_GRUPO_CIA   = vCodGrupoCia_in
       AND   PAGO.COD_LOCAL       = vCodLocal_in
       AND   PAGO.NUM_PED_VTA     = vNumPedVta_in
       AND   PAGO.SEC_COMP_PAGO   = vSecCompPago_in;
  
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
       END || RPAD(' ', 18, ' ') ||
       -- LABORATORIO
       '       ' || RPAD(DETALLE.LAB, 19, ' ') ||
       -- CANTIDAD 
       LPAD(DETALLE.CANTIDAD, 10, ' ') ||
       -- PRECIO UNITARIO
       LPAD(DETALLE.PREC_UNIT, 9, ' ') ||
       -- DSCTO
       LPAD(DETALLE.DSCTO, 8, ' ') ||
       -- IMPORTE
       LPAD(DETALLE.SUBTOTAL, 11, ' ') ITEM

    FROM (
      SELECT NVL(DET.COD_PROD, ' ') COD_PROD,
             SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,
                          '31',
                          'PROM-' || TRIM(PROD.DESC_PROD),
                          NVL(TRIM(PROD.DESC_PROD), ' ')),
                   0,34) DESCRIPCION,
             SUBSTR(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL, --Si el Tipo de venta es Mayorista
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
             SUBSTR(TRIM(LA.NOM_LAB), 0, 20) LAB,
             DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL, --Si el Tipo de venta es Mayorista
                    DECODE(MOD(ABS(DET.CANT_ATENDIDA), ABS(DET.VAL_FRAC)),
                            0,
                            (ABS(DET.CANT_ATENDIDA) / ABS(DET.VAL_FRAC)) || '', --Imprime las cantidad
                            ABS(DET.CANT_ATENDIDA)), --sin fraccion
                   ABS(DET.CANT_ATENDIDA)) CANTIDAD,
             TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E, 0),'999,999,990.000')) PREC_UNIT,
              CASE
                WHEN FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in, vCodLocal_in, vAuxNumPedVta) = 'S' THEN
                  CASE
                    WHEN ABS(NVL(DET.AHORRO_CAMP, 0)) = 0 THEN
                      ' '
                    WHEN ABS(NVL(DET.AHORRO_CAMP, 0)) > 0 THEN
                      TRIM(TO_CHAR((ABS(NVL(DET.AHORRO_CAMP, 0)) * (-1)), '999,999,990.000'))
                  END
                ELSE
                  CASE
                    WHEN ABS(NVL(DET.AHORRO, 0)) = 0 THEN
                      ' '
                    WHEN ABS(NVL(DET.AHORRO, 0)) > 0 THEN
                      TRIM(TO_CHAR((ABS(NVL(DET.AHORRO, 0)) * (-1)),'999,999,990.000'))
                  END
              END DSCTO,
              CASE
                WHEN FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in, vCodLocal_in, vAuxNumPedVta) = 'S' THEN
                  TRIM(TO_CHAR(ABS(NVL(DET.VAL_PREC_TOTAL, 0) + NVL(DET.AHORRO_PUNTOS, 0)), '999,999,990.00'))
                ELSE
                  TRIM(TO_CHAR(ABS(NVL(DET.VAL_PREC_TOTAL, 0)), '999,999,990.00'))
              END SUBTOTAL
                
      FROM VTA_PEDIDO_VTA_DET DET, LGT_PROD PROD, LGT_LAB LA
      WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   DET.COD_LOCAL     = vCodLocal_in
      AND   DET.NUM_PED_VTA   = vAuxNumPedVta -- vNumPedVta_in
      AND  (CASE
              WHEN vTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
              WHEN vTipoClienteConvenio = DOC_EMPRESA AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
              ELSE DET.SEC_COMP_PAGO
            END) = vAuxSecCompPago -- vSecCompPago_in
      AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
      AND   DET.COD_PROD      = PROD.COD_PROD
      AND   PROD.COD_LAB      = LA.COD_LAB
      ORDER BY DET.SEC_PED_VTA_DET ASC) DETALLE;
  
  filaCurDetalle curDetalleComprobante%ROWTYPE;
  
  CURSOR curMontoTotal IS
    SELECT REPLACE((REPLACE((EXTRACTVALUE(MONTOS_TOTAL.column_value, 'e')),'�','&')),'�','<') VAL 
      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((

        SELECT  
          -- MONTO TOTALES INFO CLIENTE
            CASE 
              WHEN vCopagoEmpresa = 'S' THEN
                LPAD('COPAGO '||TRIM(TO_CHAR(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR((ABS(NVL(PAGO.VAL_COPAGO_COMP_PAGO,0))*(-1)),'999,999,990.00')),15) || '@'
            END ||
          
            CASE 
              WHEN NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0)!=0 THEN
                 LPAD('REDONDEO: S/.',13)||LPAD(TRIM(TO_CHAR((ABS(NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0))*(-1)),'999,999,990.00')),7)
              ELSE
                ' '   
            END ||
            
            LPAD('TOTAL A PAGAR: S/.',25)||LPAD(TRIM(TO_CHAR(ABS(NVL(PAGO.VAL_NETO_COMP_PAGO,0)+NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0)),'999,999,990.00')),15) || '@' ||
          
          -- MONTO TOTALES INFO SUNAT
          -- Operaciones grabagas
          '-' || '@' ||
          CASE WHEN NVL(PAGO.TOTAL_GRAV_E,0)!=0 THEN
            LPAD('OP.GRAVADAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_GRAV_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Operaciones inafectas
          CASE WHEN NVL(PAGO.TOTAL_INAF_E,0)!=0 THEN
            LPAD('OP.INAFECTAS: S/.',20) ||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_INAF_E),0),'999,999,990.00')),15) || '@' 
          END ||
          --Operaciones exoneradas
          CASE WHEN NVL(PAGO.TOTAL_EXON_E,0)!=0 THEN
            LPAD('OP.EXONERADAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_EXON_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Operaciones Gratuitas
          CASE WHEN NVL(PAGO.TOTAL_GRATU_E,0)!=0 THEN
            LPAD('OP.GRATUITAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_GRATU_E),0),'999,999,990.00')),15) || '@' 
          END ||
              
          -- Monto Impuesto
          CASE WHEN NVL(PAGO.TOTAL_IGV_E,0)!=0 THEN
            LPAD('IGV-'||PAGO.PORC_IGV_COMP_PAGO||'%: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E),0),'999,999,990.00')),15) || '@' 
          END ||

          CASE
            WHEN vCopagoEmpresa != 'S' AND NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0)!=0 THEN
              LPAD('REDONDEO: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(((ABS(PAGO.VAL_REDONDEO_COMP_PAGO)*(-1))),0),'999,999,990.00')),15) || '@'
          END ||
          
          --INI ASOSA - 16/04/2015 - PTOSYAYAYAYA
          CASE 
            -- KMONCADA SE MOSTRARA EL AHORRO CALCULADO ANTERIORMENTE
            WHEN vAhorroTotalPedido > 0 AND PAGO.TIP_COMP_PAGO != COD_TIP_COMP_NOTA_CRED THEN
              --LPAD('TOTAL DSCTO: S/.',16)||LPAD('-' || TRIM(TO_CHAR(ahorroPtoSoles,'999,999,990.00')),13) 
              LPAD('TOTAL DESCUENTOS: S/.',21)||LPAD('-' || TRIM(TO_CHAR(vAhorroTotalPedido,'999,999,990.00')),8)--ASOSA - 15/05/2015 - PTOSYAYAYAY - ASI LO QUIERE EDMUNDO
          END ||
          --FIN ASOSA - 16/04/2015 - PTOSYAYAYAYA
          
          -- Monto Total (lo que debe  pagar el cliente)
          CASE 
            WHEN NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                  WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA 
                                      AND PAGO.PCT_BENEFICIARIO > 0  
                                      AND PAGO.PCT_EMPRESA > 0 THEN 
                                    ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                  ELSE 0 
                                  END), 0) != 0 THEN
              
              LPAD('IMPORTE TOTAL: S/.',20)|| LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15)|| '@'
              -- KMONCADA 22.12.2014 MONTOS YA NO SE MOSTRARAN AL 100%
              /*CASE WHEN vCopagoEmpresa = 'S' THEN
                              LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                          WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA
                                                            AND PAGO.PCT_BENEFICIARIO > 0  
                                                            AND PAGO.PCT_EMPRESA > 0 THEN 
                                                           ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                          ELSE 0
                                                          END)
                                        ,0),'999999990.00')),15) || '@-@'
                            ELSE
                              --LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15) || '@-@'
                            -- KMONCADA 14.11.2014 NO CONSIDERAR REDONDEO EN INFORMACION DE SUNAT
                             LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO),0),'999,999,990.00')),15) || '@-@'
                          END*/
            END || '-' --|| '@'
            -- KMONCADA 22.12.2014 YA NO SE MOSTRARAN LOS MONTOS DE TOTAL A PAGAR 
           /*
           CASE WHEN vCopagoEmpresa = 'S' THEN 
                         '-' || '@' ||
                         LPAD('COPAGO '||TRIM(TO_CHAR(NVL(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),0),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_COPAGO_COMP_PAGO),0),'999,999,990.00')),15) || '@' ||
                         LPAD('TOTAL A PAGAR: S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO),0),'999,999,990.00')),15)|| '@' ||
                         '-' || '@'
                         */
         -- END 
              FROM VTA_COMP_PAGO PAGO
              WHERE PAGO.COD_GRUPO_CIA = vCodGrupoCia_in
              AND PAGO.COD_LOCAL = vCodLocal_in
              AND PAGO.NUM_PED_VTA= vNumPedVta_in
              AND PAGO.SEC_COMP_PAGO = vSecCompPago_in
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) MONTOS_TOTAL;  
  
  filaCurMontoTotal  curMontoTotal%ROWTYPE;
  
  CURSOR curEncabezadoPtos(valAhorroTotal NUMBER, valDsctoPedido NUMBER) IS
    
      SELECT  X.VAL,
              CASE WHEN ROWNUM = 2 THEN 'N'
              ELSE 'S' END BOLD
      FROM(
          SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL 
                  FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                     REPLACE( REPLACE((REPLACE((            
          SELECT 
            CLI.NOM_CLI ||'@'||
            CASE 
              WHEN CLI.SEXO_CLI = 'F' THEN 'Experta del Ahorro'
              WHEN CLI.SEXO_CLI = 'M' THEN 'Experto del Ahorro'
              ELSE 'Experto(a) del Ahorro'
            END ||'@'||
            CASE 
              WHEN NVL(A.LLAVE_TAB_GRAL,'A') = 'A' THEN
                'Ahorraste S/.' || trim(TO_CHAR(ROUND(valAhorroTotal + valDsctoPedido,2),'999,990.00')) || ' en los ultimos 12 meses'
              ELSE
                'Mira has ahorrado en esta compra: S/.' || PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(valDsctoPedido)
            END
          FROM PBL_TAB_GRAL A,
               VTA_PEDIDO_VTA_CAB CAB,
               PBL_CLIENTE CLI
          WHERE CAB.COD_GRUPO_CIA = vCodGrupoCia_in
          AND CAB.COD_LOCAL = vCodLocal_in
          AND CAB.NUM_PED_VTA = vNumPedVta_in
          AND CAB.DNI_CLI = CLI.DNI_CLI
          AND A.ID_TAB_GRAL = 491
          ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt   
      ) X;
  
  filaCurEncabezadoPtos curEncabezadoPtos%ROWTYPE;
  
  CURSOR curFormaPago IS
    SELECT
      NVL(A.COD_FORMA_PAGO,'-') COD_FORMA_PAGO,
      NVL(A.TIP_MONEDA,'-') TIP_MONEDA,
      TRIM(TO_CHAR(NVL(ABS(A.IM_PAGO),0),'999,999,990.00')) MONTO,
      TRIM(TO_CHAR(NVL(ABS(A.VAL_VUELTO),0),'999,999,990.00')) VUELTO, 
      TRIM(TO_CHAR(NVL(ABS(A.VAL_TIP_CAMBIO),0),'999,999,990.00')) TIPO_CAMBIO,
      C.DESC_FORMA_PAGO DESCRIPCION,
      TRIM(TO_CHAR(NVL(ABS(A.IM_TOTAL_PAGO),0),'999,999,990.00')) TOTAL_PAGO
    FROM VTA_FORMA_PAGO_PEDIDO A,
         VTA_FORMA_PAGO C
    WHERE A.COD_GRUPO_CIA  = C.COD_GRUPO_CIA 
    AND   A.COD_FORMA_PAGO = C.COD_FORMA_PAGO
    AND   A.COD_FORMA_PAGO = C.COD_FORMA_PAGO 
    AND   A.COD_GRUPO_CIA  = vCodGrupoCia_in
    AND   A.COD_LOCAL      = vCodLocal_in
    AND   A.NUM_PED_VTA    = vNumPedVta_in
    ORDER BY A.COD_FORMA_PAGO DESC;
  filaCurFormaPago curFormaPago%ROWTYPE;
  
  CURSOR curInfoCliDelivery IS
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL 
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
                SELECT
                  CASE 
                    WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) OR (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '3') THEN
                      CASE 
                        WHEN COMP.VAL_NETO_COMP_PAGO >= 700.00 THEN
                          CASE 
                            WHEN NVL(TRIM(DEL.NOMBRE_DE),'*') != '*' THEN
                              'NOMBRE DE CLIENTE: ' || DEL.NOMBRE_DE || '@'
                          END||
                          CASE
                            WHEN NVL(TRIM(COMP.NUM_DOC_IMPR),'*') != '*' THEN
                              'DNI: '|| NVL(COMP.NUM_DOC_IMPR,' ') || '@'
                          END||
                          CASE
                            WHEN NVL(TRIM(COMP.DIREC_IMPR_COMP), '*') != '*' THEN
                              'DIRECCION: '|| TRIM(COMP.DIREC_IMPR_COMP) || '@'
                          END
                        ELSE
                          CASE 
                            WHEN NVL(TRIM(DEL.DATOS_CLI),'*') != '*' THEN
                              'NOMBRE DE CLIENTE: '|| TRIM(DEL.DATOS_CLI) || '@'
                          END||
                          CASE
                            WHEN NVL(TRIM(DEL.DIRECCION),'*') != '*' THEN
                              'DIRECCION: '|| TRIM(DEL.DIRECCION) || '@'
                          END
                      END 
                    WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) OR (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '1') THEN
                      CASE 
                        WHEN NVL(TRIM(COMP.NOM_IMPR_COMP),'*') != '*' THEN 
                          'RAZON SOCIAL: '|| COMP.NOM_IMPR_COMP || '@'
                      END||
                      CASE
                        WHEN NVL(TRIM(COMP.NUM_DOC_IMPR),'*') != '*' THEN
                          'RUC CLIENTE: ' || TRIM(COMP.NUM_DOC_IMPR) || '@'
                      END||
                      CASE
                        WHEN NVL(TRIM(COMP.DIREC_IMPR_COMP),'*') != '*' THEN
                          'DIRECCION: '|| TRIM(COMP.DIREC_IMPR_COMP) || '@'
                      END
                  END 
                FROM VTA_COMP_PAGO COMP, 
                     VTA_PEDIDO_VTA_CAB CAB,
                     TMP_CE_CAMPOS_COMANDA DEL
                WHERE COMP.COD_GRUPO_CIA   = vCodGrupoCia_in
                AND   COMP.COD_LOCAL       = vCodLocal_in
                AND   COMP.NUM_PED_VTA     = vNumPedVta_in
                AND   COMP.SEC_COMP_PAGO   = vSecCompPago_in
                AND   COMP.COD_GRUPO_CIA   = CAB.COD_GRUPO_CIA
                AND   COMP.COD_LOCAL       = CAB.COD_LOCAL
                AND   COMP.NUM_PED_VTA     = CAB.NUM_PED_VTA
                AND   DEL.COD_GRUPO_CIA    = CAB.COD_GRUPO_CIA
                AND   DEL.COD_LOCAL        = CAB.COD_LOCAL
                AND   DEL.NUM_PED_VTA      = CAB.NUM_PEDIDO_DELIVERY
            ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
  
  filaCurInfoCliDelivery curInfoCliDelivery%ROWTYPE;
  
  CURSOR curInfoCliente(indPtos CHAR) IS
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL
                FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
                  SELECT   
                    CASE 
                      WHEN NVL(TRIM(COMP.NOM_IMPR_COMP), '*') != '*' THEN
                        CASE 
                          WHEN (COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) OR 
                                (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '3') ) AND
                               indPtos <> 'S' 
                               THEN
                            'NOMBRE DE CLIENTE: '|| COMP.NOM_IMPR_COMP || '@'
                          WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) OR 
                               (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '1') THEN
                            'RAZON SOCIAL: ' || COMP.NOM_IMPR_COMP || '@'
                        END
                    END ||
                    CASE 
                      WHEN NVL(TRIM(COMP.NUM_DOC_IMPR), '*') != '*' THEN
                        CASE 
                          WHEN (COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) OR 
                                (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '3') ) AND
                                indPtos <> 'S'
                               THEN
                            'DNI CLIENTE: '|| COMP.NUM_DOC_IMPR || '@'
                          WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) OR 
                               (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '1') THEN
                            'RUC CLIENTE: ' || COMP.NUM_DOC_IMPR || '@'  
                        END
                    END ||
                    CASE 
                      WHEN NVL(TRIM(COMP.DIREC_IMPR_COMP), '*') != '*' THEN
                        'DIRECCION: '||COMP.DIREC_IMPR_COMP || '@'  
                    END
                  FROM VTA_COMP_PAGO COMP 
                  WHERE COMP.COD_GRUPO_CIA = vCodGrupoCia_in
                  AND   COMP.COD_LOCAL     = vCodLocal_in
                  AND   COMP.NUM_PED_VTA   = vNumPedVta_in
                  AND   COMP.SEC_COMP_PAGO = vSecCompPago_in
              ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
              
  filaCurInfoCliente curInfoCliente%ROWTYPE;
  
  CURSOR curDatosAdicionalesConv IS
    SELECT UPPER(DATOS.NOMBRE_CAMPO||': '||DATOS.DESCRIPCION_CAMPO) DATO 
    FROM   CON_BTL_MF_PED_VTA  DATOS 
    WHERE  DATOS.COD_GRUPO_CIA = vCodGrupoCia_in
    AND    DATOS.COD_LOCAL     = vCodLocal_in
    AND    DATOS.NUM_PED_VTA   = vNumPedVta_in;
  filaCurDatosAdicConv curDatosAdicionalesConv%ROWTYPE;
  
  CURSOR curDatosConvenio IS
    SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL
              FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
                SELECT
                  CASE 
                    --PARA EMPRESA
                    WHEN COMP.TIP_CLIEN_CONVENIO = '2' THEN
                      --venta total para la empresa
                      -- KMONCADA 28.04.2015 MOSTRAR EN VALOR POSITIVO EL MONTO DE LA VENTA
                      'VENTA TOTAL: S/.' || TRIM(TO_CHAR(ABS(NVL(COMP.VAL_BRUTO_COMP_PAGO, 0)), '999,999,990.00')) || '@' ||
                      'INSTITUCION: ' || TRIM(CONV.INSTITUCION) || ' - ' || COMP.PCT_EMPRESA || '%' || '@' || --Nombre de laInstitucion
                      CASE
                        WHEN NVL(COMP.NUM_COMP_COPAGO_REF, '*') != '*' THEN
                          'DOC.REF.: (' || COMP.PCT_BENEFICIARIO || '%) - ' || --Nombre del Beneficiario
                          (SELECT NVL(COMPE.NUM_COMP_PAGO_E, NVL(COMPE.NUM_COMP_PAGO, 0))
                          FROM VTA_COMP_PAGO COMPE
                          WHERE COMPE.COD_GRUPO_CIA   = COMP.COD_GRUPO_CIA
                          AND   COMPE.COD_LOCAL     = COMP.COD_LOCAL
                          AND   COMPE.NUM_PED_VTA   = COMP.NUM_PED_VTA
                          AND  COMPE.NUM_COMP_PAGO   = COMP.NUM_COMP_COPAGO_REF) || 
                          ' - S/. ' ||TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO, 0), '999,999,990.00'))
                      END
                    --PARA BENEFICIARIO
                    WHEN CONV.COD_TIPO_CONVENIO != '2' THEN
                      --Numero de Documento Referencial
                      'BENEFICIARIO -' || COMP.PCT_BENEFICIARIO || '%' || '@' || 
                      CASE 
                        WHEN NVL(COMP.NUM_COMP_COPAGO_REF, '*') != '*' THEN
                          'DOC.REF.: (' || COMP.PCT_EMPRESA || '%) - ' || --Nombre del beneficiario
                          --Numero de Comprobante de Pago
                          (SELECT NVL(COMPE.NUM_COMP_PAGO_E, NVL(COMPE.NUM_COMP_PAGO, 0))
                          FROM VTA_COMP_PAGO COMPE
                          WHERE COMPE.COD_GRUPO_CIA   = COMP.COD_GRUPO_CIA
                          AND   COMPE.COD_LOCAL     = COMP.COD_LOCAL
                          AND   COMPE.NUM_PED_VTA   = COMP.NUM_PED_VTA
                          AND   COMPE.NUM_COMP_PAGO   = COMP.NUM_COMP_COPAGO_REF) || 
                          ' - S/. ' ||TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO, 0), '999,999,990.00'))
                      END
                    ELSE
                      'BENEFICIARIO - 100 %'
                  END
                FROM VTA_PEDIDO_VTA_CAB CAB, 
                   MAE_CONVENIO CONV, 
                   VTA_COMP_PAGO COMP
                WHERE CAB.COD_GRUPO_CIA   = vCodGrupoCia_in
                AND   CAB.COD_LOCAL       = vCodLocal_in
                AND   CAB.NUM_PED_VTA     = vNumPedVta_in
                AND   CAB.COD_CONVENIO    = CONV.COD_CONVENIO
                AND   COMP.COD_GRUPO_CIA  = CAB.COD_GRUPO_CIA
                AND   COMP.COD_LOCAL      = CAB.COD_LOCAL
                AND   COMP.NUM_PED_VTA    = CAB.NUM_PED_VTA
                AND   COMP.SEC_COMP_PAGO  = vSecCompPago_in
              ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
  
  filaCurDatosConvenio curDatosConvenio%ROWTYPE;
  
  CURSOR curMensaje(vTexto VARCHAR2,vSeparacion VARCHAR2) IS
     SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL 
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
            vTexto
        ),'&','�')),'<','�'),vSeparacion,'</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
  
  filaCurMensaje curMensaje%ROWTYPE;
  
  CURSOR curCopagoCliente IS
    SELECT 
      -- Codigo de Producto
      RPAD(NVL(DET.COD_PROD,' '),8)  ||
      --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
      RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,' ')),0,30),30) ||
      --Cantidad vendida en fracciones
      LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                  DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                         (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Imprime las cantidad
                         ABS(DET.CANT_ATENDIDA)) ,--sin fraccion
                  ABS(DET.CANT_ATENDIDA)),8) || 
      -- Descuento del Item(no incluye igv)
      LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL_BENEF),0),'999999990.00')),0),12) VAL
      --,'9','I','N','N'
    FROM  VTA_PEDIDO_VTA_DET DET, 
        LGT_PROD PROD/*, LGT_PROD_VIRTUAL VIRT*/
    WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
    AND   DET.COD_LOCAL   = vCodLocal_in
    AND   DET.NUM_PED_VTA   = vNumPedVta_in
    AND  (CASE
        WHEN vTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
        WHEN vTipoClienteConvenio = DOC_EMPRESA AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
        ELSE DET.SEC_COMP_PAGO
        END) = vSecCompPago_in
    AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
    AND   DET.COD_PROD     = PROD.COD_PROD;
    
  filaCurCopagoCliente curCopagoCliente%ROWTYPE;
  
  vIdPtosRedimidos CHAR(1) := 'N';
  vValor           VARCHAR2(5000);
  vImporteSinRedimir VTA_PEDIDO_VTA_DET.VAL_PREC_TOTAL%TYPE;
  
   v_cIndReimp CHAR(1);
  BEGIN 
    
    /** VALIDACIONES DE EMISION DE COMPROBANTE **/
    -- dubilluz 13.01.2015    
    select case
           when c.tip_comp_pago = '02' and 
                (trim(c.num_doc_impr)  is null  or
                trim(c.nom_impr_comp)  is null)
           then
            'N'
           else
              'S'
           end 
    into   nPermiteImp
    from   vta_comp_pago c
    where c.cod_grupo_cia = vCodGrupoCia_in
    and   c.cod_local = vCodLocal_in
    and   c.num_ped_vta = vNumPedVta_in
    and   c.sec_comp_pago = vSecCompPago_in;
    
    if nPermiteImp = 'N' then
       RAISE_APPLICATION_ERROR(-20999,'Error al IMPRIMIR !\n No tiene RUC/RAZON SOCIAL la factura a IMPRIMIR!');      
    end if;
    -- dubilluz 13.01.2015  
    
    SELECT CASE 
             WHEN CP.NUM_COMP_PAGO_E IS NOT NULL THEN
               'S'
             ELSE
               'N'
           END
           INTO nPermiteImp
    FROM VTA_COMP_PAGO CP
    WHERE CP.COD_GRUPO_CIA = vCodGrupoCia_in
    AND CP.COD_LOCAL = vCodLocal_in
    AND CP.NUM_PED_VTA = vNumPedVta_in
    AND CP.SEC_COMP_PAGO = vSecCompPago_in;
    
    if nPermiteImp = 'N' then
       RAISE_APPLICATION_ERROR(-20989,'ERROR: COMPROBANTE ELECTRONICO NO CUENTA CON NRO DE CORRELATIVO.'||
                                      CHR(13)||'COMUNIQUESE CON MESA DE AYUDA.');
    end if;
    
    /** FIN DE VALIDACIONES **/
  
    --ASOSA - 16/04/2015 - PTOSYAYAYAYA
    ahorroF := PTOVENTA_FIDELIZACION.FID_F_GET_AHORRO_F(vCodGrupoCia_in,
                                                          vCodLocal_in,
                                                          vNumPedVta_in,
                                                          vSecCompPago_in);  
    
    --ERIOS 15.06.2015 Se deshabilita esta opcion por pedido de JUAN MANUEL PONCE
	SELECT CASE 
             WHEN CP.FECH_IMP_COBRO IS NOT NULL THEN
               'S'
             ELSE
               'N'
           END,
		   NVL((SELECT DESC_CORTA FROM PBL_TAB_GRAL WHERE ID_TAB_GRAL = 511),'N')
           INTO vReimprime, v_cIndReimp
    FROM VTA_COMP_PAGO CP
    WHERE CP.COD_GRUPO_CIA = vCodGrupoCia_in
    AND CP.COD_LOCAL = vCodLocal_in
    AND CP.NUM_PED_VTA = vNumPedVta_in
    AND CP.SEC_COMP_PAGO = vSecCompPago_in;
	
	IF vReimpresion = 'S' AND vReimprime = 'S' AND v_cIndReimp = 'N' THEN
		RAISE_APPLICATION_ERROR(-20990,'ERROR: LA OPCION DE REIMPRESION DE COMPROBANTE ELECTRONICO, ESTA BLOQUEADO.'||
                                      CHR(13)||'COMUNIQUESE CON MESA DE AYUDA.');
	END IF;
   
--    delete TMP_DOCUMENTO_ELECTRONICOS;  
	-- KMONCADA 02.06.2015 CONVIERTE MONTO DE AHORRO EN PTOS A SOLES
    SELECT  (valorAhorro_in * TO_NUMBER(TAB.DESC_CORTA,'99990.00')) / TO_NUMBER(TAB.LLAVE_TAB_GRAL,'99990.00')
    INTO vValorAhorroSoles
    FROM PBL_TAB_GRAL TAB
    WHERE ID_TAB_GRAL = TAB_GRAL_PTOS_REDIME;
    
    SELECT 
      -- SI ES COPAGO EMPRESA
      CASE
        WHEN PAGO.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
          ( SELECT 
               CASE
                 WHEN B.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND nvl(B.PCT_BENEFICIARIO, 0) > 0 AND NVL(B.PCT_EMPRESA, 0) > 0 THEN
                   'S'
                 ELSE
                   'N'
                END
            FROM VTA_PEDIDO_VTA_CAB A, 
                 VTA_PEDIDO_VTA_CAB AX, 
                 VTA_COMP_PAGO B 
            WHERE A.COD_GRUPO_CIA   = PAGO.COD_GRUPO_CIA
            AND A.COD_LOCAL         = PAGO.COD_LOCAL
            AND A.NUM_PED_VTA       = PAGO.NUM_PED_VTA
            AND A.COD_GRUPO_CIA     = AX.COD_GRUPO_CIA
            AND A.COD_LOCAL         = AX.COD_LOCAL
            AND AX.NUM_PED_VTA      = A.NUM_PED_VTA_ORIGEN
            AND AX.COD_GRUPO_CIA    = B.COD_GRUPO_CIA
            AND AX.COD_LOCAL        = B.COD_LOCAL
            AND AX.NUM_PED_VTA      = B.NUM_PED_VTA
            AND B.SEC_COMP_PAGO     = A.SEC_COMP_PAGO)
        WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND nvl(PAGO.PCT_BENEFICIARIO, 0) > 0 AND NVL(PAGO.PCT_EMPRESA, 0) > 0 THEN
          'S' 
        ELSE
          'N' 
      END, 
      CASE
        WHEN PAGO.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
          ( SELECT 
               CASE
                 WHEN B.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND nvl(B.PCT_BENEFICIARIO, 0) > 0 AND NVL(B.PCT_EMPRESA, 0) > 0 THEN
                   B.VAL_COPAGO_E
                 ELSE
                   0
                END
            FROM VTA_PEDIDO_VTA_CAB A, 
                 VTA_PEDIDO_VTA_CAB AX, 
                 VTA_COMP_PAGO B 
            WHERE A.COD_GRUPO_CIA   = PAGO.COD_GRUPO_CIA
            AND A.COD_LOCAL         = PAGO.COD_LOCAL
            AND A.NUM_PED_VTA       = PAGO.NUM_PED_VTA
            AND A.COD_GRUPO_CIA     = AX.COD_GRUPO_CIA
            AND A.COD_LOCAL         = AX.COD_LOCAL
            AND AX.NUM_PED_VTA      = A.NUM_PED_VTA_ORIGEN
            AND AX.COD_GRUPO_CIA    = B.COD_GRUPO_CIA
            AND AX.COD_LOCAL        = B.COD_LOCAL
            AND AX.NUM_PED_VTA      = B.NUM_PED_VTA
            AND B.SEC_COMP_PAGO     = A.SEC_COMP_PAGO)
        ELSE
         0
      END,
      -- SI ES COPAGO CLIENTE
      CASE
        WHEN PAGO.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN
          ( SELECT 
               CASE
                 WHEN B.TIP_CLIEN_CONVENIO = DOC_BENEFICIARIO AND nvl(B.PCT_BENEFICIARIO, 0) > 0 AND NVL(B.PCT_EMPRESA, 0) > 0 THEN
                   'S'
                 ELSE
                   'N'
                END
            FROM VTA_PEDIDO_VTA_CAB A, 
                 VTA_PEDIDO_VTA_CAB AX, 
                 VTA_COMP_PAGO B 
            WHERE A.COD_GRUPO_CIA   = PAGO.COD_GRUPO_CIA
            AND A.COD_LOCAL         = PAGO.COD_LOCAL
            AND A.NUM_PED_VTA       = PAGO.NUM_PED_VTA
            AND A.COD_GRUPO_CIA     = AX.COD_GRUPO_CIA
            AND A.COD_LOCAL         = AX.COD_LOCAL
            AND AX.NUM_PED_VTA      = A.NUM_PED_VTA_ORIGEN
            AND AX.COD_GRUPO_CIA    = B.COD_GRUPO_CIA
            AND AX.COD_LOCAL        = B.COD_LOCAL
            AND AX.NUM_PED_VTA      = B.NUM_PED_VTA
            AND B.SEC_COMP_PAGO     = A.SEC_COMP_PAGO)
        WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_BENEFICIARIO AND NVL(PAGO.PCT_BENEFICIARIO, 0) > 0 AND NVL(PAGO.PCT_EMPRESA, 0) > 0 THEN
          'S'
        ELSE
          'N'
      END,
      PAGO.TIP_COMP_PAGO,
      NVL(PAGO.COD_TIP_COMP_PAGO_EREF, '0'),
      PAGO.TIP_CLIEN_CONVENIO,
      -- AHORRO
      TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_DESC_E*(1+(PAGO.PORC_IGV_COMP_PAGO/100))),0),'999999990.00')),
      -- PDF417
      NVL(PAGO.COD_PDF417_E, ''),
      -- INDICADOR DE REIMPRESION DE COMPROBANTE
      CASE
        WHEN PAGO.FECH_IMP_COBRO IS NOT NULL THEN
          'S'
        ELSE
          'N'
      END
    INTO vCopagoEmpresa, 
         vDescuentoNC,
         vCopagoCliente, 
         vTipoDocumento, 
         vTipoDocRefer, 
         vTipoClienteConvenio, 
         vMontoAhorro,
         vPDF417,
         vReimprime
    FROM  VTA_COMP_PAGO PAGO
    WHERE PAGO.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   PAGO.COD_LOCAL     = vCodLocal_in
      AND   PAGO.NUM_PED_VTA   = vNumPedVta_in
      AND   PAGO.SEC_COMP_PAGO = vSecCompPago_in;
    
    -- KMONCADA AHORRO TOTAL DEL PEDIDO (CAMPA�AS + PTOS) Y CAMPA�AS POR COMPROBANTE
    SELECT SUM(NVL(DET.AHORRO, 0)), 
           SUM(NVL(DET.AHORRO_CAMP, 0)),
           SUM(ABS(NVL(DET.VAL_PREC_TOTAL,0)+NVL(DET.AHORRO_PUNTOS,0)))
      INTO vAhorroTotalPedido, 
           vAhorroInmediato,
           vImporteSinRedimir
      FROM VTA_PEDIDO_VTA_DET DET
     WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
       AND DET.COD_LOCAL = vCodLocal_in
       AND DET.NUM_PED_VTA = vNumPedVta_in
       AND DET.SEC_COMP_PAGO = vSecCompPago_in;
       
      /**TIPO DE PEDIDO DE VENTA*/
    SELECT  CAB.TIP_PED_VTA, 
            CASE 
              WHEN COD_CONVENIO IN (COD_CONVENIO_COMPETENCIA) THEN
                'N'
              ELSE
                NVL(CAB.IND_CONV_BTL_MF,'N')
            END,
            CAB.SEC_MOV_CAJA,
            NVL(CAB.EST_TRX_ORBIS,'N'),
            (SELECT SUBSTR(CAB.NUM_TARJ_PUNTOS,1,4) || 
                    REPLACE(RPAD(' ',(LENGTH(CAB.NUM_TARJ_PUNTOS)-7),'*'),' ','') || 
                    SUBSTR(CAB.NUM_TARJ_PUNTOS,LENGTH(CAB.NUM_TARJ_PUNTOS)-3,4)
             FROM DUAL),
             CAB.DNI_CLI,
             CAB.NOM_CLI_PED_VTA,
             NVL(CAB.PT_ACUMULADO,0),
             NVL(CAB.PT_REDIMIDO,0),
             CASE
               WHEN NVL(CAB.PT_TOTAL,0) < 0 THEN
                 0
               ELSE 
                 NVL(CAB.PT_TOTAL,0) 
             END,
             NVL(CAB.PT_INICIAL,0)--ASOSA - 08/05/2015 - PTOSYAYAYAYA
      INTO  vTip_Ped_vta, 
            vPedidoConvenio,
            vSecMovCaja,
            vEstadoOrbis,
            vNroTarjetaPuntos,
            vNroDNIPto,
            vNombreClientePto,
            vPtoAcumulado,
            vPtoRedimido,
            vPtoTotal,
            vPtoInicial --ASOSA - 08/05/2015 - PTOSYAYAYAYA
      FROM  VTA_PEDIDO_VTA_CAB CAB
      WHERE CAB.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   CAB.COD_LOCAL     = vCodLocal_in
      AND   CAB.NUM_PED_VTA   = vNumPedVta_in;
    
    
/** INICIO DE COMPROBANTE **/    
    
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
    
    /** ENCABEZADO DE PTOS **/
    IF PTOVENTA_FIDELIZACION.FID_F_GET_IND_IMPR(vCodGrupoCia_in,
                                                vCodLocal_in, 
                                                vNumPedVta_in, 
                                                vSecCompPago_in,
                                                vValorAhorroSoles) = 'S' THEN
      OPEN curEncabezadoPtos(vValorAhorroSoles, vAhorroTotalPedido);
        LOOP
          FETCH curEncabezadoPtos INTO filaCurEncabezadoPtos;
          EXIT WHEN curEncabezadoPtos%NOTFOUND;
            IF curEncabezadoPtos%ROWCOUNT != 3 THEN
              FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => filaCurEncabezadoPtos.VAL,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_2,
                                              vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                              vNegrita_in => filaCurEncabezadoPtos.BOLD);
            ELSE
              FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => filaCurEncabezadoPtos.VAL,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                              vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                              vNegrita_in => filaCurEncabezadoPtos.BOLD);
            END IF;
        END LOOP;
      CLOSE curEncabezadoPtos;
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc,
                                               vIpPc_in => vIpPc);
      
      /*cursorExperto := PTOVENTA_FIDELIZACION.FID_F_GET_TEXT_EXPERT(vCodGrupoCia_in,
                                                        vCodLocal_in,
                                                        vNumPedVta_in,
                                                        vSecCompPago_in,
                                                        vValorAhorroSoles);*/
    END IF;
    /** FIN ENCABEZADO DE PTOS **/
    
    FARMA_PRINTER.P_AGREGA_LOGO_MARCA(vIdDoc_in => vIdDoc,
                                           vIpPc_in => vIpPc,
                                           vCodGrupoCia => vCodGrupoCia_in,
                                           vCodLocal_in => vCodLocal_in);
--    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( GET_MARCA_LOCAL(vCodGrupoCia_in , vCodLocal_in),'9','L','S','N');
    
    FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
--    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES( ' ','9','D','S','N');
    
      
    /** CABECERA DE COMPROBANTE **/
    OPEN curCabecera;
      LOOP
      FETCH curCabecera INTO filaCurCabecera;
        EXIT WHEN curCabecera%NOTFOUND;
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                            vIpPc_in => vIpPc,
                                            vValor_in => filaCurCabecera.VALOR,
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                            vAlineado_in => filaCurCabecera.ALINEACION,
                                            vNegrita_in => filaCurCabecera.NEGRITA);
    
      END LOOP;
    CLOSE curCabecera;
    /** FIN DE CABECERA DE COMPROBANTE **/
    
    /*
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','C','N','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE((
        SELECT 
          NVL(CIA.RAZ_SOC_CIA, ' ')||' - RUC: '||NVL(CIA.NUM_RUC_CIA, ' ') ||'@'||
          TRIM(SUBSTR(TRIM(CIA.DIR_CIA),0,INSTR(TRIM(CIA.DIR_CIA), ' - '||UBI.NODIS ||' - '|| UBI.NOPRV)))||'@'||
          (UBI.NODIS ||' - '|| UBI.NOPRV)||--'@'||
          ' TELF.: '||NVL(CIA.TELF_CIA, ' ')||'@'||
          NVL(PED.COD_LOCAL,' ')||' - '||NVL(LOC.DIREC_LOCAL_CORTA, ' ') ||'@'
        FROM VTA_PEDIDO_VTA_CAB  PED,
           PBL_CIA             CIA,
           UBIGEO              UBI,
           PBL_LOCAL           LOC
        WHERE CIA.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   CIA.COD_CIA       = PED.COD_CIA
        AND   UBI.UBDEP       = CIA.UBDEP
        AND   UBI.UBPRV       = CIA.UBPRV
        AND   UBI.UBDIS       = CIA.UBDIS
        AND   LOC.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   LOC.COD_LOCAL     = PED.COD_LOCAL
        AND   PED.COD_GRUPO_CIA   = vCodGrupoCia_in
        AND   PED.COD_LOCAL     = vCodLocal_in
        AND   PED.NUM_PED_VTA     = vNumPedVta_in
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
    
    \** NOMBRE Y NRO DE COMPROBANTE **\
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','C','S','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE((      
        SELECT 
          CASE 
            WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN 'BOLETA ELECTRONICA'
            WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) THEN 'FACTURA ELECTRONICA'
            WHEN COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN 'NOTA DE CREDITO ELECTRONICA'
          END || ' ' || --'@'||
          FARMA_UTILITY.GET_T_COMPROBANTE_2(COMP.COD_TIP_PROC_PAGO, COMP.NUM_COMP_PAGO_E, COMP.NUM_COMP_PAGO) ||'@'||
          CASE 
            WHEN vReimprime = 'S' THEN
              'COPIA DE COMPROBANTE'||'@'
          END
        FROM VTA_COMP_PAGO       COMP
        WHERE COMP.COD_GRUPO_CIA   = vCodGrupoCia_in
        AND   COMP.COD_LOCAL     = vCodLocal_in
        AND   COMP.NUM_PED_VTA     = vNumPedVta_in
        AND   COMP.SEC_COMP_PAGO   = vSecCompPago_in
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;   
     
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE(( 
        SELECT
          'FECHA DE EMISION: '||NVL(TO_CHAR(COMP.FEC_CREA_COMP_PAGO, 'dd/mm/yyyy HH24:MI:SS'), ' ') ||'@'||
          -- CONVENIOS CON GUIA
          CASE 
            WHEN COMP.TIP_COMP_PAGO_REF = COD_TIP_COMP_GUIA THEN 'NUM.GUIA: '||COMP.NUM_COMP_COPAGO_REF ||'@'
            ELSE ''
          END ||
          CASE 
            -- DATOS DE VENTA EMPRESA
            WHEN PED.TIP_PED_VTA = TIPO_VTA_INSTITUCIONAL THEN
              (SELECT 
                CASE 
                  WHEN LENGTH(TRIM(EMP.NUM_OC)) != NULL OR LENGTH(TRIM(EMP.NUM_OC)) != 0  THEN 
                    'ORD.COMPRA: '||TRIM(EMP.NUM_OC)||'@'
                END ||
                  CASE 
                    WHEN LENGTH(TRIM(EMP.COD_POLIZA)) != NULL OR LENGTH(TRIM(EMP.COD_POLIZA)) != 0  THEN 
                      'NUM.POLIZA: '||TRIM(EMP.COD_POLIZA)||' - '||TRIM(EMP.NOMBRE_CLIENTE_POLIZA)||'@'
                  END
              FROM  VTA_PEDIDO_VTA_CAB_EMP EMP 
              WHERE EMP.COD_GRUPO_CIA=PED.COD_GRUPO_CIA
              AND   EMP.COD_LOCAL = PED.COD_LOCAL
              AND   EMP.NUM_PED_VTA = PED.NUM_PED_VTA)
          END ||
          CASE 
            -- MOTIVO EN NOTAS DE CREDITO GENERADAS
            WHEN COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN 
              'MOTIVO ANULACION: '|| 
              NVL(PED.MOTIVO_ANULACION,'-')||'@'||
              (CASE 
                 WHEN NVL(COMP.NUM_COMP_PAGO_EREF, '*')!='*' THEN
                   'DOC.REFERENCIA: '||
                   FARMA_UTILITY.GET_DESC_COMPROBANTE( COMP.COD_GRUPO_CIA, 
                                                       (SELECT A.TIP_COMP_PAGO 
                                                        FROM VTA_COMP_PAGO A 
                                                        WHERE A.COD_GRUPO_CIA=COMP.COD_GRUPO_CIA 
                                                        AND A.COD_LOCAL=COMP.COD_LOCAL 
                                                        AND A.NUM_PED_VTA=PED.NUM_PED_VTA_ORIGEN
                                                        AND A.SEC_COMP_PAGO=PED.SEC_COMP_PAGO)
                                                     )||
                   ' '||
                   NVL(COMP.NUM_COMP_PAGO_EREF, '-')||'@'
               END)
          END || 
          '-'
        FROM VTA_PEDIDO_VTA_CAB  PED,
             VTA_COMP_PAGO       COMP,
             PBL_CIA             CIA,
             UBIGEO              UBI,
             PBL_LOCAL           LOC
        WHERE PED.COD_GRUPO_CIA   = COMP.COD_GRUPO_CIA
        AND   PED.COD_LOCAL       = COMP.COD_LOCAL
        AND   PED.NUM_PED_VTA     = COMP.NUM_PED_VTA
        AND   CIA.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   CIA.COD_CIA         = PED.COD_CIA
        AND   UBI.UBDEP           = CIA.UBDEP
        AND   UBI.UBPRV           = CIA.UBPRV
        AND   UBI.UBDIS           = CIA.UBDIS
        AND   LOC.COD_GRUPO_CIA   = PED.COD_GRUPO_CIA
        AND   LOC.COD_LOCAL       = PED.COD_LOCAL
        AND   COMP.COD_GRUPO_CIA  = vCodGrupoCia_in
        AND   COMP.COD_LOCAL      = vCodLocal_in
        AND   COMP.NUM_PED_VTA    = vNumPedVta_in
        AND   COMP.SEC_COMP_PAGO  = vSecCompPago_in
     
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
*/
    
    /** DETALLE DE COMPROBANTE **/
    /** ENCABEZADO**/
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc,
                                                      vIpPc_in => vIpPc,
                                                      vCaracter => '-');
    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                      vIpPc_in => vIpPc,
                                      vValor_in => RPAD('CODIGO',8)|| RPAD('DESCRIPCION',18)|| LPAD('CANT.',10) || LPAD('P.UNIT.',9) || LPAD('DSCTO.',8)|| LPAD('IMPORTE',11),
                                      vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                      vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                      vNegrita_in => FARMA_PRINTER.BOLD_ACT);
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc,
                                                      vIpPc_in => vIpPc,
                                                      vCaracter => '-');
    --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES(RPAD('CODIGO',8)|| RPAD('DESCRIPCION',18)|| LPAD('CANT.',10) || LPAD('P.UNIT.',9) || LPAD('DSCTO.',8)|| LPAD('IMPORTE',11),'9','D','S','N');
    --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES('-','9','I','N','N');

    /*** DETALLE DE PEDIDO **/
    IF vCopagoCliente = 'S' THEN
      /** CODIGO DE PRODUCTOS PARA COPAGO **/
      OPEN curDetalleCopago;
        LOOP
          FETCH curDetalleCopago INTO filaCurDetalle;
          EXIT WHEN curDetalleCopago%NOTFOUND;
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => filaCurDetalle.ITEM,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                              vAlineado_in => FARMA_PRINTER.ALING_DER,
                                              vJustifica_in => 'N');
        END LOOP;
      CLOSE curDetalleCopago;
      /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
        SELECT 
          RPAD(NVL(FARMA_EPOS.F_VAR_COD_SERV_PED(PAGO.COD_GRUPO_CIA, PAGO.COD_LOCAL, PAGO.NUM_PED_VTA, PAGO.SEC_COMP_PAGO), ' '),8)||
          RPAD(SUBSTR(FARMA_EPOS.F_VAR_DSC_PROD_SERV(PAGO.COD_GRUPO_CIA,PAGO.COD_LOCAL),0,17),18) ||
          LPAD('1',10) ||
          LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                                  WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND PAGO.PCT_BENEFICIARIO > 0 AND PAGO.PCT_EMPRESA > 0 THEN
                                                                    ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                                  ELSE
                                                                    0
                                                                END),
                                0),
                            '999,999,990.000')
                    )
              ,9)||
          --LPAD('0.000',8) ||  
          LPAD('     ',8) ||    --ASOSA - 15/05/2015 - PTOSYAYAYAYA - QUIEREN QUE NO SE VEAN LOS CEROS
          LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                                  WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND PAGO.PCT_BENEFICIARIO > 0 AND PAGO.PCT_EMPRESA > 0 THEN
                                                                    ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                                  ELSE
                                                                    0
                                                                END),
                                0),
                            '999,999,990.00')
                    )
              ,11)
          ,'9','D','N','N'
        FROM  VTA_COMP_PAGO PAGO
        WHERE PAGO.COD_GRUPO_CIA   = vCodGrupoCia_in
        AND   PAGO.COD_LOCAL       = vCodLocal_in
        AND   PAGO.NUM_PED_VTA     = vNumPedVta_in
        AND   PAGO.SEC_COMP_PAGO   = vSecCompPago_in;*/
        
        vAuxNumPedVta := vNumPedVta_in;
    ELSE
      --  KMONCADA 09.01.2014 EN CASO DE NOTAS DE CREDITO TOMARA EL DETALLE DEL DOCUMENTO ORIGEN
      IF vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN 
        SELECT  CAB.SEC_COMP_PAGO, CAB.NUM_PED_VTA_ORIGEN 
        INTO vAuxSecCompPago, vAuxNumPedVta
        FROM VTA_COMP_PAGO cp,
        VTA_PEDIDO_VTA_CAB CAB
        WHERE 
        CAB.COD_GRUPO_CIA=CP.COD_GRUPO_CIA
        AND CAB.COD_LOCAL  = CP.COD_LOCAL
        AND CAB.NUM_PED_VTA = CP.NUM_PED_VTA
        AND CP.COD_GRUPO_CIA = vCodGrupoCia_in
        AND CP.COD_LOCAL = vCodLocal_in
        AND CP.NUM_PED_VTA = vNumPedVta_in
        AND CP.SEC_COMP_PAGO = vSecCompPago_in; 
      ELSE
        vAuxSecCompPago := vSecCompPago_in;
        vAuxNumPedVta := vNumPedVta_in;
      END IF;
      
      OPEN curDetalleComprobante;
        LOOP
          FETCH curDetalleComprobante INTO filaCurDetalle;
          EXIT WHEN curDetalleComprobante%NOTFOUND;
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => filaCurDetalle.ITEM,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                              vAlineado_in => FARMA_PRINTER.ALING_DER,
                                              vJustifica_in => 'N');
        END LOOP;
      CLOSE curDetalleComprobante;
      /*
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
      -- KMONCADA 04.06.2015 SE MODIFICA PARA QUE DESCRIPCION NO TAPE AL MOMTO DE DESCUENTO
      SELECT
       -- COD_PROD
       RPAD(DETALLE.COD_PROD, 8, ' ') ||
       -- DESCRIPCION 38 - 20
       CASE
         WHEN LENGTH(DETALLE.DESCRIPCION) + LENGTH(DETALLE.UNID_PRESENT) >= 38 THEN
           SUBSTR(DETALLE.DESCRIPCION, 0, (37 - LENGTH(DETALLE.UNID_PRESENT))) || ' ' || DETALLE.UNID_PRESENT
         ELSE
           DETALLE.DESCRIPCION ||RPAD(' ',(38 - (LENGTH(DETALLE.DESCRIPCION) + LENGTH(DETALLE.UNID_PRESENT))), ' ') || DETALLE.UNID_PRESENT
       END || RPAD(' ', 18, ' ') ||
       -- LABORATORIO
       '       ' || RPAD(DETALLE.LAB, 19, ' ') ||
       -- CANTIDAD 
       LPAD(DETALLE.CANTIDAD, 10, ' ') ||
       -- PRECIO UNITARIO
       LPAD(DETALLE.PREC_UNIT, 9, ' ') ||
       -- DSCTO
       LPAD(DETALLE.DSCTO, 8, ' ') ||
       -- IMPORTE
       LPAD(DETALLE.SUBTOTAL, 11, ' '),
       '9','D','N','N'

       FROM (
          SELECT NVL(DET.COD_PROD, ' ') COD_PROD,
                 SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,
                              '31',
                              'PROM-' || TRIM(PROD.DESC_PROD),
                              NVL(TRIM(PROD.DESC_PROD), ' ')),
                       0,34) DESCRIPCION,
                 SUBSTR(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL, --Si el Tipo de venta es Mayorista
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
                 SUBSTR(TRIM(LA.NOM_LAB), 0, 20) LAB,
                 DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL, --Si el Tipo de venta es Mayorista
                        DECODE(MOD(ABS(DET.CANT_ATENDIDA), ABS(DET.VAL_FRAC)),
                                0,
                                (ABS(DET.CANT_ATENDIDA) / ABS(DET.VAL_FRAC)) || '', --Imprime las cantidad
                                ABS(DET.CANT_ATENDIDA)), --sin fraccion
                       ABS(DET.CANT_ATENDIDA)) CANTIDAD,
                 TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E, 0),'999,999,990.000')) PREC_UNIT,
                  CASE
                    WHEN FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in, vCodLocal_in, vAuxNumPedVta) = 'S' THEN
                      CASE
                        WHEN ABS(NVL(DET.AHORRO_CAMP, 0)) = 0 THEN
                          ' '
                        WHEN ABS(NVL(DET.AHORRO_CAMP, 0)) > 0 THEN
                          TRIM(TO_CHAR((ABS(NVL(DET.AHORRO_CAMP, 0)) * (-1)), '999,999,990.000'))
                      END
                    ELSE
                      CASE
                        WHEN ABS(NVL(DET.AHORRO, 0)) = 0 THEN
                          ' '
                        WHEN ABS(NVL(DET.AHORRO, 0)) > 0 THEN
                          TRIM(TO_CHAR((ABS(NVL(DET.AHORRO, 0)) * (-1)),'999,999,990.000'))
                      END
                  END DSCTO,
                  CASE
                    WHEN FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in, vCodLocal_in, vAuxNumPedVta) = 'S' THEN
                      TRIM(TO_CHAR(ABS(NVL(DET.VAL_PREC_TOTAL, 0) + NVL(DET.AHORRO_PUNTOS, 0)), '999,999,990.00'))
                    ELSE
                      TRIM(TO_CHAR(ABS(NVL(DET.VAL_PREC_TOTAL, 0)), '999,999,990.00'))
                  END SUBTOTAL
                
          FROM VTA_PEDIDO_VTA_DET DET, LGT_PROD PROD, LGT_LAB LA
          WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   DET.COD_LOCAL     = vCodLocal_in
          AND   DET.NUM_PED_VTA   = vAuxNumPedVta -- vNumPedVta_in
          AND  (CASE
                  WHEN vTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
                  WHEN vTipoClienteConvenio = DOC_EMPRESA AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
                  ELSE DET.SEC_COMP_PAGO
                END) = vAuxSecCompPago -- vSecCompPago_in
          AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND   DET.COD_PROD      = PROD.COD_PROD
          AND   PROD.COD_LAB      = LA.COD_LAB
          ORDER BY DET.SEC_PED_VTA_DET ASC) DETALLE;

      */
      /*SELECT 
        -- Codigo de Producto
        RPAD(NVL(DET.COD_PROD,' '),8)  ||
        --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
        RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31',
                           'PROM-'||PROD.DESC_PROD,
                           NVL(PROD.DESC_PROD,' ')
                          )
                    ,0,34)||'',34,' ') ||
        RPAD(SUBSTR(
                    DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                           DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                                  PROD.DESC_UNID_PRESENT,
                                  DET.UNID_VTA
                                  ),--sin fraccion                    
                           DECODE(DET.VAL_FRAC,1,
                                  PROD.DESC_UNID_PRESENT,
                                  DET.UNID_VTA)
                           )
                    ,0,20)||'',22,' ') ||
        '       '||
        RPAD(''||TRIM(SUBSTR(LA.NOM_LAB,0,20))||'',19,' ') ||
        --Cantidad vendida en fracciones
        LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                    DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                           (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Imprime las cantidad
                           ABS(DET.CANT_ATENDIDA)) ,--sin fraccion
                    ABS(DET.CANT_ATENDIDA))
               ,10) || 
        -- Valor de venta unitario por �tem(no incluye igv)
        LPAD(TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E,0) ,'999,999,990.000')) ,9)||
        
        LPAD(
          CASE
            WHEN ABS(NVL(DET.AHORRO_CAMP,0)) = 0 THEN 
              ' '
            WHEN ABS(NVL(DET.AHORRO_CAMP,0)) > 0 THEN
              NVL(TRIM(TO_CHAR(NVL(ABS(DET.AHORRO_CAMP),0),'999,999,990.000')),0)
          END,8) ||
        LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL+NVL(DET.AHORRO_PUNTOS,0)),0),'999,999,990.00')),0),11)
        \*
        CASE WHEN FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in,vCodLocal_in,vAuxNumPedVta) = 'S' THEN 
          -- ERIOS 23.03.2015 Muestra montos sin descontar ahorro por puntos redimidos
          --ASOSA - 15/05/2015 - PTOSYAYAYAYA - QUIEREN QUE NO SE VEAN LOS CEROS EN LOS DESCUENTOS
          LPAD(
             DECODE(
                  NVL(TRIM(TO_CHAR((NVL(ABS(DET.AHORRO),0)-NVL(ABS(DET.AHORRO_PUNTOS),0)),'999,999,990.000')),0),
                  '0.000',
                  '     ',
                  NVL(TRIM(TO_CHAR((NVL(ABS(DET.AHORRO),0)-NVL(ABS(DET.AHORRO_PUNTOS),0)),'999,999,990.000')),0)
                  ),
             8) ||
          LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL+DET.AHORRO_PUNTOS),0),'999,999,990.00')),0),11)
        ELSE
          -- Descuento del Item(no incluye igv)
          --ASOSA - 15/05/2015 - PTOSYAYAYAYA - QUIEREN QUE NO SE VEAN LOS CEROS EN LOS DESCUENTOS
          LPAD(
             DECODE(
                  NVL(
                  --TRIM(TO_CHAR(NVL(ABS(ROUND((DET.VAL_TOTAL_DESC_ITEM_E*(1+(DET.VAL_IGV/100))),2)),0),'999,999,990.000'))
                  TRIM(TO_CHAR(NVL(ABS(DET.AHORRO),0),'999,999,990.000')) --DESA3
                  ,0),
                  '0.000',
                  '     ',
                  NVL(
                  --TRIM(TO_CHAR(NVL(ABS(ROUND((DET.VAL_TOTAL_DESC_ITEM_E*(1+(DET.VAL_IGV/100))),2)),0),'999,999,990.000'))
                  TRIM(TO_CHAR(NVL(ABS(DET.AHORRO),0),'999,999,990.000')) --DESA3
                  ,0)
                  ),
             8) ||
          LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL),0),'999,999,990.00')),0),11)
        END*\
        ,'9','D','N','N'
      FROM  VTA_PEDIDO_VTA_DET DET, 
              LGT_PROD PROD,
              lgt_lab la
      WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   DET.COD_LOCAL     = vCodLocal_in
      AND   DET.NUM_PED_VTA   = vAuxNumPedVta -- vNumPedVta_in
      AND  (CASE
              WHEN vTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
              WHEN vTipoClienteConvenio = DOC_EMPRESA AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
              ELSE DET.SEC_COMP_PAGO
            END) = vAuxSecCompPago -- vSecCompPago_in
      AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
      AND   DET.COD_PROD      = PROD.COD_PROD
      AND   PROD.COD_LAB      = LA.COD_LAB
      ORDER BY DET.SEC_PED_VTA_DET ASC ; */
         
    END IF;
    
    FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                      vIpPc_in => vIpPc, 
                                                      vCaracter => '-');

    /** DETALLE DE PTOS **/
    --INI ASOSA - 21/05/2015 - PTOSYAYAYAYA - REACOMODAR ESTA SECCION
    --ASOSA - 21/05/2015 - PTOSYAYAYAYA
    -- KMONCADA 28.05.2015 SOLO PARA PEDIDOS QUE SON DEL PROGRAMA PUNTOS
    IF FARMA_PUNTOS.F_VAR_IND_ACT_PUNTOS(vCodGrupoCia_in, vCodLocal_in) = 'S'  AND vEstadoOrbis IN (EST_ORBIS_ENVIADO,EST_ORBIS_PENDIENTE) THEN
      --ASOSA - 19/05/2015 - PTOSYAYAYAYA - VALIDACION PARA QUE NO SE IMPRIMA ESTO CON NOTA DE CREDITO
      -- IF FN_IS_NOTA_CREDITO(vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in) = 'N' THEN
      IF vTipoDocumento  NOT IN (COD_TIP_COMP_NOTA_CRED, COD_TIP_COMP_GUIA) THEN
        -- IMPRESION DE DETALLE DE PTOS REDIMIDOS
        vIdPtosRedimidos := FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in, vCodLocal_in, vAuxNumPedVta);

        IF vAhorroInmediato > 0 THEN
          SELECT (A.LLAVE_TAB_GRAL || ' S/.' || RPAD('-' || TRIM(TO_CHAR(ROUND(vAhorroInmediato,2),'999,990.00')),15,' ')) VAL
          INTO vValor
          FROM PBL_TAB_GRAL A 
          WHERE A.ID_TAB_GRAL = 497;
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                            vIpPc_in => vIpPc,
                                            vValor_in => vValor,
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                            vAlineado_in => FARMA_PRINTER.ALING_DER);
            
            
            
          
          -- INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE) 
          -- VALUES(msg,'9','D','N','N');
        END IF;
        IF vAhorroInmediato > 0 OR vIdPtosRedimidos = 'S' THEN
          --ASOSA - 15/05/2015 - PTOSYAYAYAYA - SE CAMBIA POR INDICACION DE EDMUNDO
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                            vIpPc_in => vIpPc,
                                            vValor_in => LPAD('IMPORTE: S/.',25)||LPAD(TRIM(TO_CHAR(vImporteSinRedimir,'999,999,990.00')),15),
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                            vAlineado_in => FARMA_PRINTER.ALING_DER);
          --ERIOS 23.03.2015 Imprime descuento por puntos redimidos
          IF vIdPtosRedimidos = 'S' THEN 
            WITH 
              CAB AS (SELECT NVL(PT_REDIMIDO,0) AS PT_REDIMIDO FROM VTA_PEDIDO_VTA_CAB 
                 WHERE COD_GRUPO_CIA = vCodGrupoCia_in 
                       AND COD_LOCAL = vCodLocal_in 
                       AND NUM_PED_VTA = vAuxNumPedVta),
              DET AS (SELECT SUM(NVL(DET.AHORRO_PUNTOS,0)) AHORRO_PUNTOS,
                        SUM(NVL(DET.FACTOR_PUNTOS,0)) FACTOR_PUNTOS       
                 FROM VTA_PEDIDO_VTA_DET DET
                 WHERE COD_GRUPO_CIA = vCodGrupoCia_in 
                       AND COD_LOCAL = vCodLocal_in 
                       AND NUM_PED_VTA = vAuxNumPedVta
                       AND SEC_COMP_PAGO = vAuxSecCompPago)      
            SELECT LPAD(FARMA_LEALTAD.MSJ_IMPR_PUNTOS_REDI||
                   '(' || PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(ROUND(CAB.PT_REDIMIDO*DET.FACTOR_PUNTOS)) || 
                   'PTOS):',40,' ')||
                   ' S/.' ||
                   LPAD('-' || TRIM(TO_CHAR(DET.AHORRO_PUNTOS,'999,999,990.00')),15)
            INTO vValor
            FROM CAB, DET;
            
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => vValor,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                              vAlineado_in => FARMA_PRINTER.ALING_DER);
          END IF;
        
          FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, 
                                                      vIpPc_in => vIpPc, 
                                                      vCaracter => '-');
        END IF;
         
        
      /*
          \*IF FN_VAL_DSCTO_AHORRO_INMED(vCodGrupoCia_in,
                                          vCodLocal_in,
                                          vNumPedVta_in,
                                          vSecCompPago_in) <> '0.00' OR *\
           \*IF vAhorroInmediato > 0 AND FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in, vCodLocal_in, vAuxNumPedVta) = 'S' THEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE) VALUES('-','9','I','N','N');
           END IF;*\
          --INI ASOSA - 15/05/2015 - PTOSYAYAYAYA
          INS_AHORRO_INMED(vCodGrupoCia_in,
                     vCodLocal_in,
                     vNumPedVta_in,
                     vSecCompPago_in);
       --FIN ASOSA - 15/05/2015 - PTOSYAYAYAYA
       
        IF FN_VAL_DSCTO_AHORRO_INMED(vCodGrupoCia_in,
                                          vCodLocal_in,
                                          vNumPedVta_in,
                                          vSecCompPago_in) <> '0.00' OR 
           FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in,
                                            vCodLocal_in,
                                            vAuxNumPedVta) = 'S' THEN
                                          
           INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE)        
           SELECT LPAD('IMPORTE: S/.',25)||--ASOSA - 15/05/2015 - PTOSYAYAYAYA - SE CAMBIA POR INDICACION DE EDMUNDO
                 LPAD(TRIM(TO_CHAR(SUM(NVL(ABS(DET.VAL_PREC_TOTAL+NVL(DET.AHORRO_PUNTOS,0)),0)),'999,999,990.00')),15)
                  ,'9','D','N','N'
           FROM VTA_PEDIDO_VTA_DET DET
           WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
            AND   DET.COD_LOCAL     = vCodLocal_in
            AND   DET.NUM_PED_VTA   = vAuxNumPedVta
            AND DET.SEC_COMP_PAGO = vAuxSecCompPago;
            
        END IF;

       --ERIOS 23.03.2015 Imprime descuento por puntos redimidos
       IF FARMA_LEALTAD.IND_IMPR_PUNTOS_REDI(vCodGrupoCia_in,
                                            vCodLocal_in,
                                            vAuxNumPedVta) = 'S' THEN                                                                                      
       
           INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE)
           WITH 
           CAB AS (SELECT NVL(PT_REDIMIDO,0) AS PT_REDIMIDO FROM VTA_PEDIDO_VTA_CAB 
                   WHERE COD_GRUPO_CIA = vCodGrupoCia_in 
                         AND COD_LOCAL = vCodLocal_in 
                         AND NUM_PED_VTA = vAuxNumPedVta),
           DET AS (SELECT SUM(NVL(DET.AHORRO_PUNTOS,0)) AHORRO_PUNTOS,
                          SUM(NVL(DET.FACTOR_PUNTOS,0)) FACTOR_PUNTOS       
                   FROM VTA_PEDIDO_VTA_DET DET
                   WHERE COD_GRUPO_CIA = vCodGrupoCia_in 
                         AND COD_LOCAL = vCodLocal_in 
                         AND NUM_PED_VTA = vAuxNumPedVta
                         AND SEC_COMP_PAGO = vAuxSecCompPago)      
           SELECT LPAD(FARMA_LEALTAD.MSJ_IMPR_PUNTOS_REDI||
                 '(' || PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(ROUND(CAB.PT_REDIMIDO*DET.FACTOR_PUNTOS)) || 
                 'PTOS):',40,' ')||
                 ' S/.' ||
                 LPAD('-' || TRIM(TO_CHAR(DET.AHORRO_PUNTOS,'999,999,990.00')),15)
                  ,'9','D','N','N'
           FROM CAB, DET;  
        
        
     END IF;*/
    
      END IF;

    END IF;
    --FIN ASOSA - 21/05/2015 - PTOSYAYAYAYA - REACOMODAR ESTA SECCION  
    /** DETALLE DE PTOS **/
       
    /*** MONTOS TOTALES **/
    OPEN curMontoTotal;
        LOOP
          FETCH curMontoTotal INTO filaCurMontoTotal;
          EXIT WHEN curMontoTotal%NOTFOUND;
            IF filaCurMontoTotal.VAL = '-' THEN
              FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc,
                                                                vIpPc_in => vIpPc,
                                                                vCaracter => '-');
            ELSE
              FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => filaCurMontoTotal.VAL,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                              vAlineado_in => FARMA_PRINTER.ALING_DER);
            END IF;
            
        END LOOP;
      CLOSE curMontoTotal;
      /*
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','D','N','N'
      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((

        SELECT  
          
          -- KMONCADA 22.12.2014 NO SE MUESTRA LA GLOSA TOTAL A PAGAR 
          
          -- KMONCADA 22.12.2014 SE MODIFICA EL ORDEN DE LA GLOSA COPAGO/DESCUENTO(NOTA DE CREDITO)
          
          
          --Redondeo
          
          
          --total a pagar si es copago
          -- CASE WHEN vCopagoEmpresa != 'S' THEN 
            '-'||'@'||
            CASE 
\*            WHEN vCopagoEmpresa = 'S' AND vDescuentoNC > 0 THEN
              LPAD('DESCUENTO:  S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(vDescuentoNC),0),'999,999,990.00')),15) || '@'*\
              WHEN vCopagoEmpresa = 'S' THEN
                --'-' || '@' ||LPAD('COPAGO '||TRIM(TO_CHAR(NVL(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),0),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR(NVL((ABS(PAGO.VAL_COPAGO_E*(-1))),0),'999,999,990.00')),15) || '@' 
                LPAD('COPAGO '||TRIM(TO_CHAR(NVL(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),0),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR(NVL((ABS(PAGO.VAL_COPAGO_COMP_PAGO)*(-1)),0),'999,999,990.00')),15) || '@'
            END ||
          
            CASE 
              WHEN \*vCopagoEmpresa != 'S' AND*\ NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0)!=0 THEN
                 LPAD('REDONDEO: S/.',13)||LPAD(TRIM(TO_CHAR(NVL(((ABS(PAGO.VAL_REDONDEO_COMP_PAGO)*(-1))),0),'999,999,990.00')),7)
              ELSE
                ' '   
            END ||
            LPAD('TOTAL A PAGAR: S/.',25)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15) || '@' ||
          -- END ||
          
          
          -- Operaciones grabagas
          '-' || '@' ||
          CASE WHEN NVL(PAGO.TOTAL_GRAV_E,0)!=0 THEN
            LPAD('OP.GRAVADAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_GRAV_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Operaciones inafectas
          CASE WHEN NVL(PAGO.TOTAL_INAF_E,0)!=0 THEN
            LPAD('OP.INAFECTAS: S/.',20) ||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_INAF_E),0),'999,999,990.00')),15) || '@' 
          END ||
          --Operaciones exoneradas
          CASE WHEN NVL(PAGO.TOTAL_EXON_E,0)!=0 THEN
            LPAD('OP.EXONERADAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_EXON_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Operaciones Gratuitas
          CASE WHEN NVL(PAGO.TOTAL_GRATU_E,0)!=0 THEN
            LPAD('OP.GRATUITAS: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_GRATU_E),0),'999,999,990.00')),15) || '@' 
          END ||
              
          -- Monto Impuesto
          CASE WHEN NVL(PAGO.TOTAL_IGV_E,0)!=0 THEN
            LPAD('IGV-'||PAGO.PORC_IGV_COMP_PAGO||'%: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Monto Descuentos
          \*CASE WHEN NVL(PAGO.TOTAL_DESC_E,0)!=0 THEN
            LPAD('DESCUENTO: S/.',20)||LPAD(TRIM(TO_CHAR(NVL((ABS(PAGO.TOTAL_DESC_E)),0),'999,999,990.00')),15) || '@' 
          END ||*\
          CASE
            WHEN vCopagoEmpresa != 'S' AND NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0)!=0 THEN
              LPAD('REDONDEO: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(((ABS(PAGO.VAL_REDONDEO_COMP_PAGO)*(-1))),0),'999,999,990.00')),15) || '@'
          END ||
          
          --INI ASOSA - 16/04/2015 - PTOSYAYAYAYA
          CASE 
            -- KMONCADA SE MOSTRARA EL AHORRO CALCULADO ANTERIORMENTE
            \*
              WHEN ahorroF <> 'N' THEN    
              LPAD('TOTAL DSCTO: S/.',16)||LPAD(ahorroF,13)
            *\
            WHEN ahorroPtoSoles > 0 AND PAGO.TIP_COMP_PAGO != COD_TIP_COMP_NOTA_CRED THEN
              --LPAD('TOTAL DSCTO: S/.',16)||LPAD('-' || TRIM(TO_CHAR(ahorroPtoSoles,'999,999,990.00')),13) 
              LPAD('TOTAL DESCUENTOS: S/.',21)||LPAD('-' || TRIM(TO_CHAR(ahorroPtoSoles,'999,999,990.00')),8)--ASOSA - 15/05/2015 - PTOSYAYAYAY - ASI LO QUIERE EDMUNDO
          END ||
          --FIN ASOSA - 16/04/2015 - PTOSYAYAYAYA
          
          -- Monto Total (lo que debe  pagar el cliente)
          CASE 
            WHEN NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                  WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA 
                                      AND PAGO.PCT_BENEFICIARIO > 0  
                                      AND PAGO.PCT_EMPRESA > 0 THEN 
                                    ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                  ELSE 0 
                                  END), 0) != 0 THEN
              
              LPAD('IMPORTE TOTAL: S/.',20)|| LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15)|| '@'
              -- KMONCADA 22.12.2014 MONTOS YA NO SE MOSTRARAN AL 100%
              \*CASE WHEN vCopagoEmpresa = 'S' THEN
                              LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                                          WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA
                                                            AND PAGO.PCT_BENEFICIARIO > 0  
                                                            AND PAGO.PCT_EMPRESA > 0 THEN 
                                                           ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                                          ELSE 0
                                                          END)
                                        ,0),'999999990.00')),15) || '@-@'
                            ELSE
                              --LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15) || '@-@'
                            -- KMONCADA 14.11.2014 NO CONSIDERAR REDONDEO EN INFORMACION DE SUNAT
                             LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO),0),'999,999,990.00')),15) || '@-@'
                          END*\
            END || '-' || '@'
            -- KMONCADA 22.12.2014 YA NO SE MOSTRARAN LOS MONTOS DE TOTAL A PAGAR 
           \*
           CASE WHEN vCopagoEmpresa = 'S' THEN 
                         '-' || '@' ||
                         LPAD('COPAGO '||TRIM(TO_CHAR(NVL(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),0),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_COPAGO_COMP_PAGO),0),'999,999,990.00')),15) || '@' ||
                         LPAD('TOTAL A PAGAR: S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO),0),'999,999,990.00')),15)|| '@' ||
                         '-' || '@'
                         *\
         -- END 
              FROM VTA_COMP_PAGO PAGO
              WHERE PAGO.COD_GRUPO_CIA = vCodGrupoCia_in
              AND PAGO.COD_LOCAL = vCodLocal_in
              AND PAGO.NUM_PED_VTA= vNumPedVta_in
              AND PAGO.SEC_COMP_PAGO = vSecCompPago_in
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;   
         */
    
    /*** FORMAS DE PAGO **/       
    -- NOTA DE CREDITO NO MOSTRARA FORMA DE PAGO
      IF vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN
        OPEN curFormaPago;
          LOOP
            FETCH curFormaPago INTO filaCurFormaPago;
            EXIT WHEN curFormaPago%NOTFOUND;
            
            IF vCopagoEmpresa = 'N' AND vCopagoCliente = 'N' THEN
              --ERIOS 25.02.2015 Se muestra la cantidad de puntos soles.
              IF filaCurFormaPago.COD_FORMA_PAGO  = COD_FORMA_PAGO_DOLARES THEN
                FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                  vIpPc_in => vIpPc,
                                                  vValor_in => LPAD('TIPO DE CAMBIO: '||filaCurFormaPago.TIPO_CAMBIO||' - '||filaCurFormaPago.DESCRIPCION,40)||LPAD(filaCurFormaPago.MONTO,15),
                                                  vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                  vAlineado_in => FARMA_PRINTER.ALING_DER);
              ELSIF filaCurFormaPago.COD_FORMA_PAGO  = COD_FORMA_PAGO_PUNTOS THEN
                FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                  vIpPc_in => vIpPc,
                                                  vValor_in => LPAD(filaCurFormaPago.DESCRIPCION||': '||PTOVENTA_FIDELIZACION.FID_F_GET_NUM_FORMATED(TO_NUMBER(filaCurFormaPago.MONTO,'999,999,990.00'))||'  S/.',40)||LPAD(filaCurFormaPago.TOTAL_PAGO,15),
                                                  vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                  vAlineado_in => FARMA_PRINTER.ALING_DER);
              ELSE
                FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                  vIpPc_in => vIpPc,
                                                  vValor_in => LPAD(filaCurFormaPago.DESCRIPCION,40)||LPAD(filaCurFormaPago.MONTO,15),
                                                  vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                  vAlineado_in => FARMA_PRINTER.ALING_DER);
              END IF;
                        
              IF filaCurFormaPago.VUELTO != '0.00' THEN
                FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                  vIpPc_in => vIpPc,
                                                  vValor_in => LPAD('VUELTO: S/.',40)||LPAD(filaCurFormaPago.VUELTO,15),
                                                  vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                  vAlineado_in => FARMA_PRINTER.ALING_DER);
              END IF;
            ELSE
              IF vCopagoEmpresa = 'N' AND vCopagoCliente = 'S' THEN
                IF filaCurFormaPago.COD_FORMA_PAGO  = COD_FORMA_PAGO_DOLARES THEN
                  FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                    vIpPc_in => vIpPc,
                                                    vValor_in => LPAD('TIPO DE CAMBIO: '||filaCurFormaPago.TIPO_CAMBIO||' - '||filaCurFormaPago.DESCRIPCION,40)||LPAD(filaCurFormaPago.MONTO,15),
                                                    vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                    vAlineado_in => FARMA_PRINTER.ALING_DER);
                ELSE
                  IF filaCurFormaPago.COD_FORMA_PAGO  = COD_FORMA_PAGO_SOLES THEN
                    FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                      vIpPc_in => vIpPc,
                                                      vValor_in => LPAD(filaCurFormaPago.DESCRIPCION,40)||LPAD(filaCurFormaPago.MONTO,15),
                                                      vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                      vAlineado_in => FARMA_PRINTER.ALING_DER);
                  ELSE
                    IF filaCurFormaPago.COD_FORMA_PAGO != COD_FORMA_PAGO_CONVENIO THEN
                      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                        vIpPc_in => vIpPc,
                                                        vValor_in => LPAD(filaCurFormaPago.DESCRIPCION,40)||LPAD(filaCurFormaPago.MONTO,15),
                                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                        vAlineado_in => FARMA_PRINTER.ALING_DER);
                    END IF;
                  END IF;
                END IF;
                          
                IF filaCurFormaPago.VUELTO != '0.00' THEN
                  FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                    vIpPc_in => vIpPc,
                                                    vValor_in => LPAD('VUELTO: S/.',40)||LPAD(filaCurFormaPago.VUELTO,15),
                                                    vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                    vAlineado_in => FARMA_PRINTER.ALING_DER);
                END IF;
              ELSE
                IF filaCurFormaPago.COD_FORMA_PAGO = COD_FORMA_PAGO_CREDITO THEN
                  FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                    vIpPc_in => vIpPc,
                                                    vValor_in => LPAD(filaCurFormaPago.DESCRIPCION,40)||LPAD(filaCurFormaPago.MONTO,15),
                                                    vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                    vAlineado_in => FARMA_PRINTER.ALING_DER);
                END IF;
              END IF;
            END IF;
          END LOOP;
        CLOSE curFormaPago;
      END IF;
    
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','9','D','N','N');
      
      --ASOSA - 15/05/2015 - PTOSYAYAYAYA - SE CAMBIO DE UBICACION XQ ASI LO QUIERE EDMUNDO
            -- KMONCADA 22.04.2015 SE MODIFICA ESTADO DE NO PROCESADO EN ORBIS
      --IF vEstadoOrbis = ESTADO_SIN_TRX_ORBIS THEN --NTOA: PIDIERON QUITAR ESTA VALIDACION TAMBIEN, NADA MAS.
      /** MENSAJE DE AHORRO **/
      IF vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN
        IF vMontoAhorro != '0.00' THEN
          FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                            vIpPc_in => vIpPc,
                                            vValor_in => '!!!FELICITACIONES!!!',
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                            vNegrita_in => FARMA_PRINTER.BOLD_ACT);
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                            vIpPc_in => vIpPc,
                                            vValor_in => 'EN ESTA COMPRA AHORRASTE S/.'||RPAD(TRIM(TO_CHAR(vAhorroTotalPedido,'999,999,990.00')),6,' '),
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_1,
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                            vNegrita_in => FARMA_PRINTER.BOLD_ACT);
          FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
          --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','9','D','N','N');
          --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('EN LA COMPRA USTED AHORRO: S/.'||TRIM(vMontoAhorro),'0','C','S','N');
          --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('!!!FELICITACIONES!!!','0','C','S','N'); --SE CAMBIO LA VARIABLE TAMBIEN
          --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('EN ESTA COMPRA AHORRASTE S/.'||RPAD(TRIM(TO_CHAR(vAhorroTotalPedido,'999,999,990.00')),6,' '),'0','C','S','N'); --SE CAMBIO LA VARIABLE TAMBIEN
          --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','9','D','N','N');
        END IF;
      END IF;
      --END IF;
      /** FIN MENSAJE DE AHORRO **/
      
      /*** MONTO EN LETRAS ***/
      SELECT 
        'SON : ' || 
        UPPER(TRIM(FARMA_UTILITY.LETRAS((COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO)))) ||
        ' NUEVOS SOLES.'
      INTO vValor
      FROM VTA_COMP_PAGO COMP
      WHERE COMP.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   COMP.COD_LOCAL     = vCodLocal_in
      AND   COMP.NUM_PED_VTA   = vNumPedVta_in
      AND   COMP.SEC_COMP_PAGO = vSecCompPago_in;
      
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                        vIpPc_in => vIpPc,
                                        vValor_in => vValor,
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ);
      
      /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
      SELECT 
        'SON : ' || 
        UPPER(TRIM(FARMA_UTILITY.LETRAS((COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO)))) ||
        ' NUEVOS SOLES.'
        ,'9','I','N','N'
      FROM VTA_COMP_PAGO COMP
      WHERE COMP.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   COMP.COD_LOCAL     = vCodLocal_in
          AND   COMP.NUM_PED_VTA   = vNumPedVta_in
          AND   COMP.SEC_COMP_PAGO = vSecCompPago_in;
*/
      /*** FIN MONTO EN LETRAS ***/
      
/*      IF PTOVENTA_FIDELIZACION.FID_F_GET_IND_UN_COMP(vCodGrupoCia_in,
                                              		     vCodLocal_in,
                                                       vNumPedVta_in) <> 'S' THEN --ASOSA - 23/03/2015 -PTOSYAYAYAYA - DERREPENTE LO CAMBIAN UNA VZ MAS
*/

      /** DATOS DE CLIENTE **/
      IF vTip_Ped_vta = TIPO_VTA_DELIVERY THEN
        OPEN curInfoCliDelivery;
          LOOP
            FETCH curInfoCliDelivery INTO filaCurInfoCliDelivery;
            EXIT WHEN curInfoCliDelivery%NOTFOUND;
            
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => filaCurInfoCliDelivery.VAL,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                              vAlineado_in => FARMA_PRINTER.ALING_IZQ);
          END LOOP;
        CLOSE curInfoCliDelivery;
          
      ELSE
        -- KMONCADA 26.05.2015 TIENE QUE MOSTRAR EN CASO DE FACTURA
        vIndProgPuntos := PTOVENTA_FIDELIZACION.FID_F_GET_IND_UN_COMP(vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in);
       /*IF PTOVENTA_FIDELIZACION.FID_F_GET_IND_UN_COMP(vCodGrupoCia_in,
                                              		     vCodLocal_in,
                                                       vNumPedVta_in) <> 'S' THEN --ASOSA - 23/03/2015 -PTOSYAYAYAYA - DERREPENTE LO CAMBIAN UNA VZ MAS
         */ 
          OPEN curInfoCliente(vIndProgPuntos);
          LOOP
            FETCH curInfoCliente INTO filaCurInfoCliente;
            EXIT WHEN curInfoCliente%NOTFOUND;
            
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                              vIpPc_in => vIpPc,
                                              vValor_in => filaCurInfoCliente.VAL,
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                              vAlineado_in => FARMA_PRINTER.ALING_IZQ);
          END LOOP;
        CLOSE curInfoCliente;
            
       -- END IF;
      END IF;
      /** FIN DATOS DE CLIENTE **/ 
      
      /**  DATOS DE CONVENIO **/
      IF vPedidoConvenio='S' AND vTip_Ped_vta != TIPO_VTA_INSTITUCIONAL THEN
        IF vTipoClienteConvenio= '1' or vTipoClienteConvenio= '2' THEN
          -- NOMBRE DE CONVENIO
          SELECT 'CONVENIO: '|| CONV.DES_CONVENIO 
          INTO vValor
          FROM VTA_PEDIDO_VTA_CAB CAB, 
               MAE_CONVENIO CONV
          WHERE CAB.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   CAB.COD_LOCAL     = vCodLocal_in
          AND   CAB.NUM_PED_VTA   = vNumPedVta_in
          AND   CAB.COD_CONVENIO  = CONV.COD_CONVENIO;
            
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                            vIpPc_in => vIpPc,
                                            vValor_in => vValor,
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                            vAlineado_in => FARMA_PRINTER.ALING_IZQ);
          -- DATOS ADICIONALES    
          OPEN curDatosAdicionalesConv;
            LOOP
              FETCH curDatosAdicionalesConv INTO filaCurDatosAdicConv;
              EXIT WHEN curDatosAdicionalesConv%NOTFOUND;
                FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                  vIpPc_in => vIpPc,
                                                  vValor_in => filaCurDatosAdicConv.DATO,
                                                  vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                  vAlineado_in => FARMA_PRINTER.ALING_IZQ);
            END LOOP;
          CLOSE curDatosAdicionalesConv;
          
          -- DATOS DE CONVENIO
          OPEN curDatosConvenio;
            LOOP
              FETCH curDatosConvenio INTO filaCurDatosConvenio;
              EXIT WHEN curDatosConvenio%NOTFOUND;
                FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                  vIpPc_in => vIpPc,
                                                  vValor_in => filaCurDatosConvenio.VAL,
                                                  vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                  vAlineado_in => FARMA_PRINTER.ALING_IZQ);
            END LOOP;
          CLOSE curDatosConvenio;
            
        END IF;
      END IF;
      /** FIN DATOS DE CONVENIO **/

      /**IMPRESION DEL MENSAJE DE REGARGA O PRODUCTO VIRTUAL*/
      BEGIN 
        SELECT DET.COD_PROD 
        INTO vCodProdVirtual
        FROM   VTA_PEDIDO_VTA_DET DET
        WHERE  DET.COD_GRUPO_CIA = vCodGrupoCia_in
        AND    DET.COD_LOCAL = vCodLocal_in
        AND    DET.NUM_PED_VTA = vNumPedVta_in
        AND EXISTS  (
                SELECT COD_PROD
                FROM LGT_PROD_VIRTUAL A
                WHERE A.TIP_PROD_VIRTUAL = 'R'
                AND A.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                AND A.COD_PROD = DET.COD_PROD
        )
        --INI ASOSA - 16/01/2015 - RIMAC
          AND NOT EXISTS (
                          SELECT COD_PROD
                          FROM LGT_PROD_RIMAC A
                          WHERE A.COD_GRUPO_CIA= DET.COD_GRUPO_CIA
                          AND A.COD_PROD = DET.COD_PROD
                          );
        --FIN ASOSA - 16/01/2015 - RIMAC
      EXCEPTION 
        WHEN OTHERS THEN
          vCodProdVirtual := NULL;
      END;
      IF NVL(vCodProdVirtual,'*') != '*' THEN 
        curdesc := FARMA_GRAL.CAJ_OBTIENE_MSJ_PROD_VIRT(vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in, vCodProdVirtual);
        LOOP
        FETCH curdesc INTO vLinea;
        EXIT WHEN curdesc%NOTFOUND;
          IF LENGTH(TRIM(vLinea)) != 0 THEN
            vLinea := REPLACE(vLinea,'�','@');
          --ELSE
            --vLinea := vLinea ||'@'|| REPLACE(vLinea,'�','@');
          END IF;
        END LOOP;
        
        OPEN curMensaje(vLinea, '@');
          LOOP
            FETCH curMensaje INTO filaCurMensaje;
            EXIT WHEN curMensaje%NOTFOUND;
            
              FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                vIpPc_in => vIpPc,
                                                vValor_in => filaCurMensaje.VAL,
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                vAlineado_in => FARMA_PRINTER.ALING_IZQ);
          END LOOP;
        CLOSE curMensaje;
        
        /*
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
            vLinea
        ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  */
      END IF;
      
      --INI ASOSA - 13/01/2015 - RIMAC
      vIndRimac := PTOVENTA_RIMAC.GET_IND_EXISTE_rimac_ped(vCodGrupoCia_in, NULL, vCodLocal_in, vNumPedVta_in );
      
      IF vIndRimac = 'S' THEN
        curdesc02 := PTOVENTA_RIMAC.CAJ_OBTIENE_MSJ_PROD_RIMAC(vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in);
        LOOP
        FETCH curdesc02 INTO vLinea;
        EXIT WHEN curdesc02%NOTFOUND;
          IF LENGTH(TRIM(vLinea)) != 0 THEN
            vLinea := REPLACE(vLinea,'�','$$$');                                   
          END IF;
        END LOOP;
        
        OPEN curMensaje(vLinea, '$$$');
          LOOP
            FETCH curMensaje INTO filaCurMensaje;
            EXIT WHEN curMensaje%NOTFOUND;
            
              FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                                vIpPc_in => vIpPc,
                                                vValor_in => filaCurMensaje.VAL,
                                                vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                                vAlineado_in => FARMA_PRINTER.ALING_IZQ);
          END LOOP;
        CLOSE curMensaje;
        
        /*
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
            vLinea
        ),'&','�')),'<','�'),'$$$','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  */
      END IF;
      --FIN ASOSA - 13/01/2015 - RIMAC
      
      --INI ASOSA - 09/02/2015 - PTOSYAYAYAYA
      indPermitImip := PTOVENTA_FIDELIZACION.FID_F_GET_IND_UN_COMP(vCodGrupoCia_in,
                                                    vCodLocal_in,
                                                    vNumPedVta_in);
                                                    
      IF indPermitImip = 'S' THEN
        PTOVENTA_FIDELIZACION.IMP_VOUCHER_PTOS(cCodGrupoCia_in => vCodGrupoCia_in,
                                               cCodLocal_in => vCodLocal_in,
                                               cNumPedVta_in => vNumPedVta_in,
                                               cSecCompPago_in => vSecCompPago_in,
                                               cIndVarios_in => 'N',
                                               valorAhorro_in => valorAhorro_in,
                                               vIdDoc_in => vIdDoc,
                                               vIpPc_in => vIpPc,
                                               cDocTarjetaPtos_in => cDocTarjetaPtos_in);
/*        
        PTOVENTA_FIDELIZACION.INS_IMP_PTOS(vCodGrupoCia_in,
                                           vCodLocal_in,
                                           vNumPedVta_in,
                                           vSecCompPago_in,
                                           'N',
                                           valorAhorro_in);*/
      END IF;
      
      --FIN ASOSA - 18/02/2015 - PTOSYAYAYAYA      
      
      /***   PDF417   ****/
      IF NVL(vPDF417, '*') != '*' THEN
        FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
        FARMA_PRINTER.P_AGREGA_PDF417(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc, vValor_in => vPDF417);
--         INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (vPDF417, '9','P','N','N');
      END IF; 
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
      
      /** PIE DE PAGINA **/  
      
      SELECT 'Guarda tu voucher. Es el sustento para validar tu compra. '||
             'Representaci�n impresa de la '||
                   (CASE 
                     WHEN vTipoDocumento IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN 'BOLETA ELECTRONICA '
                     WHEN vTipoDocumento IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) THEN 'FACTURA ELECTRONICA '
                     WHEN vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN 'NOTA DE CREDITO ELECTRONICA '
                    END)||'puede ser consultado en www.mifarma.com.pe. '||
             DESC_LARGA ||'. '||
             'No se aceptan devoluciones de dinero. '||
             'Cambio de mercader�a �nicamente dentro de las 48 horas siguientes a la compra. Indispensable presentar comprobante.'
      INTO vValor
      FROM PTOVENTA.PBL_TAB_GRAL 
      WHERE ID_TAB_GRAl=604;
     
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => vValor, 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0, 
                                        vAlineado_in => FARMA_PRINTER.ALING_JUSTI);
        
      /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'�','&')),'�','<') VAL ,'9','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
        SELECT
       'Guarda tu voucher. '||--'@'||
       'Es el sustento para validar tu compra. '||--'@'||
       'Representaci�n impresa de la '||
       (CASE 
         WHEN vTipoDocumento IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN 'BOLETA ELECTRONICA '
         WHEN vTipoDocumento IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) THEN 'FACTURA ELECTRONICA '
         WHEN vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN 'NOTA DE CREDITO ELECTRONICA '
        END)
       ||--'@'||
       'puede ser consultado en www.mifarma.com.pe. '||--'@'||
       (SELECT  DESC_LARGA FROM PTOVENTA.PBL_TAB_GRAL  WHERE ID_TAB_GRAl='604')||--'@'||
       ' No se aceptan devoluciones de dinero. '||--'@'||
       'Cambio de mercader�a �nicamente dentro '||--'@'||
       'de las 48 horas siguientes a la compra. '||--'@'||
       'Indispensable presentar comprobante.' ||'@'\*||
       '-'||'@'*\
      FROM DUAL
      ),'&','�')),'<','�'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
      */
      --INSERT INTO TMP_DOCUMENTO_ELECTRONICOS(VALOR,TAMANIO,ALINEACION,BOLD,AJUSTE) VALUES('-','9','I','N','N');
      FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc, vCaracter => '-');
      
      SELECT vVersion_in||' - USUARIO: ' ||USU.LOGIN_USU|| ' - CAJA: ' || MOV.NUM_CAJA_PAGO --, '9','C','N','N'
      INTO vValor
      FROM CE_MOV_CAJA MOV,
           PBL_USU_LOCAL USU
      WHERE USU.COD_GRUPO_CIA = MOV.COD_GRUPO_CIA
      AND   USU.COD_LOCAL     = MOV.COD_LOCAL
      AND   USU.SEC_USU_LOCAL = MOV.SEC_USU_LOCAL
      AND   MOV.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   MOV.COD_LOCAL     = vCodLocal_in
      AND   MOV.SEC_MOV_CAJA  = vSecMovCaja;    
      
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => vValor, 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0, 
                                        vAlineado_in => FARMA_PRINTER.ALING_CEN);
      /*
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS  
      SELECT vVersion_in||' - USUARIO: ' ||USU.LOGIN_USU|| ' - CAJA: ' || MOV.NUM_CAJA_PAGO , '9','C','N','N'
      FROM CE_MOV_CAJA MOV,
           PBL_USU_LOCAL USU
      WHERE USU.COD_GRUPO_CIA = MOV.COD_GRUPO_CIA
      AND   USU.COD_LOCAL     = MOV.COD_LOCAL
      AND   USU.SEC_USU_LOCAL = MOV.SEC_USU_LOCAL
      AND   MOV.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   MOV.COD_LOCAL     = vCodLocal_in
      AND   MOV.SEC_MOV_CAJA  = vSecMovCaja;   */ 
            
--      IF PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(COD_IND_EMI_CUPON) = 'S' THEN       --ASOSA - 10/04/2015 - NECUPYAYAYAYA      
      -- KMONCADA 27.04.2015 SE MODIFICA SELECT PARA DETERMINAR CANTIDAD DE CUPONES EMITIDOS
      SELECT COUNT(1)
      INTO cCantidadCupon
      FROM VTA_CAMP_PEDIDO_CUPON CUPON
      WHERE CUPON.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   CUPON.COD_LOCAL     = vCodLocal_in 
      AND   CUPON.NUM_PED_VTA   = vNumPedVta_in
      AND   CUPON.ESTADO        = 'E' 
      AND   CUPON.IND_IMPR      = 'S';   

      IF cCantidadCupon > 0 THEN
        IF vEstadoOrbis NOT IN (EST_ORBIS_ENVIADO,EST_ORBIS_PENDIENTE) THEN
          FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc, vCaracter => '-');
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc,
                                            vIpPc_in => vIpPc,
                                            vValor_in => 'POR SU COMPRA USTED GANO: ' || cCantidadCupon ||
                                                         (CASE
                                                           WHEN cCantidadCupon>1 THEN ' CUPONES DESCUENTO.'--ASOSA - 15/05/2015 - PTOSYAYAYAYA - SE AGREGO LA PALABRA "DESCUENTO"
                                                           ELSE ' CUPON DESCUENTO.'
                                                         END),
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN);
        END IF;
      
/*        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '9','I','N','N');
        --KMONCADA 02.06.2015 SOLO CUANDO LA TRXS SE HALLA REALIZADO CON TARJ.PUNTOS NO SE MOSTRARA
        IF vEstadoOrbis NOT IN (EST_ORBIS_ENVIADO,EST_ORBIS_PENDIENTE) THEN
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('POR SU COMPRA USTED GANO: ' || cCantidadCupon ||
                                                        (CASE
                                                           WHEN cCantidadCupon>1 THEN ' CUPONES DESCUENTO.'--ASOSA - 15/05/2015 - PTOSYAYAYAYA - SE AGREGO LA PALABRA "DESCUENTO"
                                                           ELSE ' CUPON DESCUENTO.'
                                                         END), '9','C','N','N');
        END IF;*/
      END IF;  
  --    END IF;                                                 
      
      /**IMPRIME LOS DATOS PARA LA NOTA DE CREDITO*/
      IF vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN
        FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc, vCaracter => '-');
        FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                          vIpPc_in => vIpPc, 
                                          vValor_in => 'DATOS DEL CLIENTE', 
                                          vTamanio_in => FARMA_PRINTER.TAMANIO_0, 
                                          vAlineado_in => FARMA_PRINTER.ALING_CEN);
        FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
        FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                          vIpPc_in => vIpPc, 
                                          vValor_in => 'NOMBRE:_____________________________________________', 
                                          vTamanio_in => FARMA_PRINTER.TAMANIO_0, 
                                          vAlineado_in => FARMA_PRINTER.ALING_IZQ);
        FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
        FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                          vIpPc_in => vIpPc, 
                                          vValor_in => 'DNI:________________________________________________', 
                                          vTamanio_in => FARMA_PRINTER.TAMANIO_0, 
                                          vAlineado_in => FARMA_PRINTER.ALING_IZQ);
        FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
        FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
        FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
        FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                          vIpPc_in => vIpPc, 
                                          vValor_in => 'FIRMA:______________________________________________', 
                                          vTamanio_in => FARMA_PRINTER.TAMANIO_0, 
                                          vAlineado_in => FARMA_PRINTER.ALING_IZQ);
        
        /*INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('DATOS DEL CLIENTE', '9','C','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('NOMBRE:_____________________________________________', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('DNI:________________________________________________', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '9','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('FIRMA:______________________________________________', '9','I','N','N');*/
      END IF;
      
      IF vCopagoCliente = 'S' THEN
        FARMA_PRINTER.P_AGREGA_LINEA_CARACTER_REPITE(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc, vCaracter => '-');
        
        OPEN curCopagoCliente;
          LOOP
            FETCH curCopagoCliente INTO filaCurCopagoCliente;
            EXIT WHEN curCopagoCliente%NOTFOUND;
              FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                               vIpPc_in => vIpPc, 
                                               vValor_in => filaCurCopagoCliente.VAL, 
                                               vTamanio_in => FARMA_PRINTER.TAMANIO_0,
                                               vAlineado_in => FARMA_PRINTER.ALING_IZQ);
            
          END LOOP;
        CLOSE curCopagoCliente;
--        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '9','I','N','N');

  /*      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
          SELECT 
            -- Codigo de Producto
            RPAD(NVL(DET.COD_PROD,' '),8)  ||
            --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
            RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,' ')),0,30),30) ||
            --Cantidad vendida en fracciones
            LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                        DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                               (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Imprime las cantidad
                               ABS(DET.CANT_ATENDIDA)) ,--sin fraccion
                        ABS(DET.CANT_ATENDIDA)),8) || 
            -- Descuento del Item(no incluye igv)
            LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL_BENEF),0),'999999990.00')),0),12)
            ,'9','I','N','N'
          FROM  VTA_PEDIDO_VTA_DET DET, 
              LGT_PROD PROD\*, LGT_PROD_VIRTUAL VIRT*\
          WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   DET.COD_LOCAL   = vCodLocal_in
          AND   DET.NUM_PED_VTA   = vNumPedVta_in
          AND  (CASE
              WHEN vTipoClienteConvenio = DOC_BENEFICIARIO AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_BENEF
              WHEN vTipoClienteConvenio = DOC_EMPRESA AND vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN DET.SEC_COMP_PAGO_EMPRE
              ELSE DET.SEC_COMP_PAGO
              END) = vSecCompPago_in
          AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND   DET.COD_PROD     = PROD.COD_PROD;*/
        /*
            IF ( vTipoDocumento = COD_TIP_COMP_FACTURA  OR vTipoDocumento = COD_TIP_COMP_TICKET_FA  OR( vTipoDocumento = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer='1'))  THEN
               INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
                  SELECT 
                    -- Codigo de Producto
                    RPAD(NVL(DET.COD_PROD,' '),8)  ||
                    --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
                    RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,' ')),0,30),30) ||
                    --Cantidad vendida en fracciones
                    LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                       DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                        (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Imprime las cantidad
                                      ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','')) ,--sin fraccion
                       DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                                      (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Caso contrario las imprime en fracciones
                                      ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','/'||ABS(DET.VAL_FRAC)))),8) || 
                    -- Descuento del Item(no incluye igv)
                    LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_TOTAL_DESC_ITEM_E),0),'999999990.00')),0),12)
                    ,'1','I','N','N'
                  FROM  VTA_PEDIDO_VTA_DET DET, 
                      LGT_PROD PROD\*, LGT_PROD_VIRTUAL VIRT*\
                  WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
                  AND   DET.COD_LOCAL   = vCodLocal_in
                  AND   DET.NUM_PED_VTA   = vNumPedVta_in
                  AND  (CASE
                      WHEN vTipoClienteConvenio = '1' THEN DET.SEC_COMP_PAGO_BENEF
                      WHEN vTipoClienteConvenio = '2' THEN DET.SEC_COMP_PAGO_EMPRE
                      ELSE DET.SEC_COMP_PAGO
                      END) = vSecCompPago_in
                  AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                  AND   DET.COD_PROD     = PROD.COD_PROD;
                  
              ELSIF(vTipoDocumento = COD_TIP_COMP_BOLETA  OR   vTipoDocumento = COD_TIP_COMP_TICKET OR( vTipoDocumento = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer='3') ) THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
                  SELECT 
                    -- Codigo de Producto
                    RPAD(NVL(DET.COD_PROD,'-'),8) ||
                    --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
                    RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,'-')),0,30),30) ||
                    --Cantidad vendida en fracciones
                    LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
                       DECODE(MOD(DET.CANT_ATENDIDA,DET.VAL_FRAC),0,
                                      (DET.CANT_ATENDIDA/DET.VAL_FRAC)||'',--Imprime las cantidad
                                      DET.CANT_ATENDIDA||DECODE(DET.VAL_FRAC,1,'','')) ,--sin fraccion
                       DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                                      (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Caso contrario las imprime en fracciones
                                      ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','/'||ABS(DET.VAL_FRAC)))),8)  || 
                    -- Descuento del Item(incluye IGV)
         filaCurCopagoCliente           LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.AHORRO),0),'999,999,990.00')),0),12)
                    ,'1','I','N','N'
                  FROM  VTA_PEDIDO_VTA_DET DET, 
                      LGT_PROD PROD\*, LGT_PROD_VIRTUAL VIRT*\
                  WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
                  AND   DET.COD_LOCAL = vCodLocal_in
                  AND   DET.NUM_PED_VTA = vNumPedVta_in
                  AND   (CASE
                        WHEN vTipoClienteConvenio = '1' THEN DET.SEC_COMP_PAGO_BENEF
                        WHEN vTipoClienteConvenio = '2' THEN DET.SEC_COMP_PAGO_EMPRE
                        ELSE DET.SEC_COMP_PAGO
                      END) = vSecCompPago_in
                  AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
                  AND   DET.COD_PROD     = PROD.COD_PROD;
              END IF;  */
      END IF;
      
      -- KMONCADA 15.12.2014 REGISTRA EN CASO DE REIMPRESION DE DOCUMENTOS
      IF('S' = vReimpresion) THEN
        INSERT INTO REIMPRESION_VTA_COMP_PAGO_E 
        SELECT vCodGrupoCia_in, vCodLocal_in, vNumPedVta_in, vSecCompPago_in, SEQ_REIMPR_VTA_COMP_PAGO_E.nextval, SYS_CONTEXT('USERENV', 'IP_ADDRESS'), SYSDATE
        FROM DUAL;
      END IF;
      
      /*OPEN cursorComprobante FOR
        SELECT A.VALOR, A.TAMANIO, A.ALINEACION, A.BOLD, A.AJUSTE
        FROM TMP_DOCUMENTO_ELECTRONICOS A
        WHERE A.VALOR IS NOT NULL;*/
      cursorComprobante := FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      RETURN cursorComprobante;
  END;
  
END FARMA_EPOS;
/
