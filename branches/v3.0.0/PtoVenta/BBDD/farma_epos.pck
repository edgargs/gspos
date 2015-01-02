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
 FUNCTION IMP_COMP_ELECT ( vCodGrupoCia_in VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                           vCodLocal_in    VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                           vNumPedVta_in   VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                                           vSecCompPago_in VTA_PEDIDO_VTA_CAB.SEC_COMP_PAGO%TYPE,
                                           vVersion_in     REL_APLICACION_VERSION.NUM_VERSION%TYPE)
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
                                     cSecCompPago_in      IN CHAR,
                                     cCodTipoDocumento_in IN CHAR)
  RETURN FARMACURSOR;
  
  -- ACTUALIZA FLAG DE ELECTRONICO DEL COMPROBANTE A 0, PARA INDICAR QUE SE GENERO COMO PRE-IMPRESO
  -- KMONCADA 02.12.2014
  PROCEDURE COMP_PAGO_CAMBIA_FLAG_E(cCodGrupoCia_in  IN CHAR,
                                  cCodLocal_in     IN CHAR,
                                  cNumPedVta_in    IN CHAR,
                                  cSecCompPago_in  IN CHAR);
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
                  '') || '|' || -- 03 Tipo de Nota de crédito/Nota de Débito (Motivo de NC/ND)
              COMP.NUM_COMP_PAGO_EREF || '|' || -- 04 Factura/Boleta  que referencia la Nota de Crédito/Nota de Débito
              DECODE(COMP.NUM_COMP_PAGO_EREF,
                     null,
                     '',
                     TRIM(PED.MOTIVO_ANULACION)) || '|' || -- 05 Sustento
              NVL(TO_CHAR(COMP.FEC_CREA_COMP_PAGO, 'yyyy-MM-dd'), ' ') || '|' || -- 06 Fecha Emision
              DECODE(COMP.COD_TIP_MONEDA, '01', 'PEN', '02', 'USD', 'PEN') || '|' || -- 07 Tipo de Moneda
              CIA.NUM_RUC_CIA || -- 08 RUC Emisor
              '|6|' || -- 09 Tipo de Identificación Emisor
              CIA.NOM_CIA || '|' || -- 10 Nombre Comercial Emisor
              CIA.RAZ_SOC_CIA || '|' || -- 11 Razon Social Emisor
              UBI.UBDEP || UBI.UBPRV || UBI.UBDIS || '|' || -- 12 Codigo UBIGEO Emisor
              CIA.DIR_CIA || '|' || -- 13 Direccion Emisor
              UBI.NODEP || '|' || -- 14 Departamento Emisor
              UBI.NOPRV || '|' || -- 15 Provincia Emisor (Comuna)
              UBI.NODIS || '|' || -- 16 Distrito Emisor
              NVL(TRIM(COMP.NUM_DOC_IMPR), 0) || '|' || -- 17 RUC Receptor
              COMP.COD_TIP_IDENT_RECEP_E || '|' || -- 18 Tipo de Identificacion Receptor
              TRIM(NVL(TRIM(COMP.NOM_IMPR_COMP), 'SIN NOMBRE')) || '|' || -- 19 Razon Social Receptor
              COMP.DIREC_IMPR_COMP || '|' || -- 20 Direccion Receptor
             --DECODE(COMP.TIP_COMP_PAGO,'04',TRIM(TO_CHAR(NVL(COMP.VAL_NETO_COMP_PAGO*-1,0),'999999990.00')),TRIM(TO_CHAR(NVL(COMP.VAL_NETO_COMP_PAGO,0),'999999990.00')))||'|'||-- 21 Monto Neto
              TRIM(TO_CHAR(NVL(ABS(COMP.VAL_TOTAL_E), 0), '999999990.00')) || '|' || -- 25 Monto Total
              TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_IGV_E), 0), '999999990.00')) || '|' || -- 22 Monto Impuesto
              TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_DESC_E), 0), '999999990.00')) || '|' || -- 23 Monto Descuentos
              0 || '|' || -- 24 Monto Recargos
              DECODE(COMP.TIP_COMP_PAGO,
                     '04',
                     TRIM(TO_CHAR(NVL(COMP.VAL_NETO_COMP_PAGO * -1, 0),
                                  '999999990.00')),
                     TRIM(TO_CHAR(NVL(trunc(COMP.VAL_NETO_COMP_PAGO + (CASE -- SOLO PARA CONVENIO COPAGO PARA LA EMPRESA EL MONTO TOTAL AL 100% -- LTAVARA 17.10.2014
                                              WHEN NVL(COMP.TIP_CLIEN_CONVENIO, 0) = DOC_EMPRESA AND
                                                   nvl(COMP.PCT_BENEFICIARIO, 0) > 0 AND
                                                   nvl(COMP.PCT_EMPRESA, 0) > 0 THEN
                                               ABS(COMP.VAL_COPAGO_COMP_PAGO)
                                              ELSE
                                               0
                                            END),
                                            2),
                                      0),
                                  '999999990.00'))) || '|' || -- -- 25 Monto Total
              '' || '|' || -- 26 Códigos de otros conceptos tributarios o comerciales recomendados
              '' || '|' || -- 27 Total de Valor Venta Neto
              NVL(TRIM(COMP.NUM_DOC_IMPR), 0) || '|' || -- 28 número de documento de identidad del adquirente o usuario
              COMP.COD_TIP_IDENT_RECEP_E || '|' || -- 29 Tipo de documento de identidad del adquirente o usuario
              'PE' || '|' || -- 30 Código País Emisor
              '' || '|' || -- 31 Urbanización Emisor
             /*CASE PED.TIP_PED_VTA WHEN '02' --PEDIDO DELIVERY
                   THEN
             ( SELECT
                   LOCAL.UBDEP||LOCAL.UBPRV||LOCAL.UBDIS||'|'||-- 32 Dirección del Punto de Partida, Código de Ubigeo
                   LOCAL.DIREC_LOCAL||'|'||-- 33 Dirección del Punto de Partida, Dirección completa y detallada
                   ''||'|'||-- 34 Dirección del Punto de Partida, Urbanización
                   UBI_LOCAL.NOPRV||'|'||-- 35 Dirección del Punto de Partida, Provincia
                   UBI_LOCAL.NODEP||'|'||-- 36 Dirección del Punto de Partida, Departamento
                   UBI_LOCAL.NODIS||'|'||-- 37 Dirección del Punto de Partida, Distrito
                   'PE'||'|'||-- 38 Dirección del Punto de Partida, Código de País
                   DIR.UBDEP||DIR.UBPRV||DIR.UBDIS||'|'||-- 39 Dirección del Punto de Llegada, Código de Ubigeo
                   (SELECT COM.DIR_ENVIO FROM  TMP_CE_CAMPOS_COMANDA COM WHERE COM.NUM_PED_VTA=PED.NUM_PEDIDO_DELIVERY)||'|'||-- 40 Dirección del Punto de Llegada, Dirección completa y detallada
                   DIR.COD_URBANIZACION||'|'||-- 41 Dirección del Punto de Llegada, Urbanización
                   UBI_DIR.NOPRV||'|'||-- 42 Dirección del Punto de Llegada, Provincia
                   UBI_DIR.NODEP||'|'||-- 43 Dirección del Punto de Llegada, Departamento
                   UBI_DIR.NODIS||'|'||-- 44 Dirección del Punto de Llegada, Distrito
                   'PE'||'|'||-- 45 Dirección del Punto de Llegada, Código de País
                   ''||'|'||-- 46 Placa Vehículo
                   ''||'|'||-- 47 N° constancia de inscripción del vehículo o certificado de habilitacion vehicular
                   ''||'|'||-- 48 Marca Vehículo
                   ''||'|'||-- 49 N° de licencia de conducir
                   ''||'|'||-- 50 Ruc transportista
                   ''||'|'||-- 51 Ruc transportista -Tipo Documento
                   ''||'|'-- 52 Razón social del transportista
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
                 LOCAL.UBDEP || LOCAL.UBPRV || LOCAL.UBDIS || '|' || -- 32 Dirección del Punto de Partida, Código de Ubigeo
                 LOCAL.DIREC_LOCAL || '|' || -- 33 Dirección del Punto de Partida, Dirección completa y detallada
                 '' || '|' || -- 34 Dirección del Punto de Partida, Urbanización
                 UBI_LOCAL.NOPRV || '|' || -- 35 Dirección del Punto de Partida, Provincia
                 UBI_LOCAL.NODEP || '|' || -- 36 Dirección del Punto de Partida, Departamento
                 UBI_LOCAL.NODIS || '|' || -- 37 Dirección del Punto de Partida, Distrito
                 'PE' || '|' || -- 38 Dirección del Punto de Partida, Código de País
                 (SELECT
                  
                   TMP_PED.UBDEP || TMP_PED.UBPRV || TMP_PED.UBDIS || '|' || -- 39 Dirección del Punto de Llegada, Código de Ubigeo
                   COMA.DIRECCION || '|' || -- 40 Dirección del Punto de Llegada, Dirección completa y detallada
                   '' || '|' || -- 41 Dirección del Punto de Llegada, Urbanización
                   UBI_DIR.NOPRV || '|' || -- 42 Dirección del Punto de Llegada, Provincia
                   UBI_DIR.NODEP || '|' || -- 43 Dirección del Punto de Llegada, Departamento
                   UBI_DIR.NODIS || '|' || -- 44 Dirección del Punto de Llegada, Distrito
                   'PE' || '|' || -- 45 Dirección del Punto de Llegada, Código de País
                   '' || '|' || -- 46 Placa Vehículo
                   '' || '|' || -- 47 N° constancia de inscripción del vehículo o certificado de habilitacion vehicular
                   '' || '|' || -- 48 Marca Vehículo
                   '' || '|' || -- 49 N° de licencia de conducir
                   '' || '|' || -- 50 Ruc transportista
                   '' || '|' || -- 51 Ruc transportista -Tipo Documento
                   '' || '|' -- 52 Razón social del transportista
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
                  SELECT EMP.NUM_OC
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
  -- Proposito : Obtener los datos de otros conceptos del comprobante electrónico
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
                          TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_GRAV_E), 0),
                                       '999999990.00')) || '|'||
                                       ''||'|'|| -- 3.* Monto Total Documento Incluida la percepcion
                                       ''|| -- 4.* Este campo puede informar:
                                                  --   -. Base imponible percepcion.
                                                  --   -. Valor referencial del servicio de transporte 
                                                  -- de bienes realizado por vía terrestre.
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
                          TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_INAF_E), 0),
                                       '999999990.00')) || '|'||
                                       ''||'|'|| -- 3.* Monto Total Documento Incluida la percepcion
                                       ''|| -- 4.* Este campo puede informar:
                                                  --   -. Base imponible percepcion.
                                                  --   -. Valor referencial del servicio de transporte 
                                                  -- de bienes realizado por vía terrestre.
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
                                                  -- de bienes realizado por vía terrestre.
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
                                                  -- de bienes realizado por vía terrestre.
                                       CHR(13) VALOR
                    FROM VTA_COMP_PAGO COMP
                   WHERE COMP.COD_GRUPO_CIA = cGrupoCia
                     AND COMP.COD_LOCAL = cCodLocal
                     AND COMP.NUM_PED_VTA = cNumPedidoVta
                     AND COMP.SEC_COMP_PAGO = cSecCompPago
                     --AND COMP.TIP_COMP_PAGO = cTipoDocumento
                     AND COMP.TOTAL_GRATU_E != 0
                  UNION ALL
                  
                  -- 2005 Total descuentos
                  select 'DOC|' || '2005|' ||
                          TRIM(TO_CHAR(NVL(ABS(COMP.TOTAL_DESC_E), 0),
                                       '999999990.00')) || '|'||
                                       ''||'|'|| -- 3.* Monto Total Documento Incluida la percepcion
                                       ''|| -- 4.* Este campo puede informar:
                                                  --   -. Base imponible percepcion.
                                                  --   -. Valor referencial del servicio de transporte 
                                                  -- de bienes realizado por vía terrestre.
                                       CHR(13) VALOR
                    FROM VTA_COMP_PAGO COMP
                   WHERE COMP.COD_GRUPO_CIA = cGrupoCia
                     AND COMP.COD_LOCAL = cCodLocal
                     AND COMP.NUM_PED_VTA = cNumPedidoVta
                     AND COMP.SEC_COMP_PAGO = cSecCompPago
                     --AND COMP.TIP_COMP_PAGO = cTipoDocumento
                     AND COMP.TOTAL_DESC_E != 0) LOOP
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
  -- Proposito : Obtener la leyenda del comprobante electrónico
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
      Número de Línea de Nota	1
      Código de la leyenda	2
      Glosa de la leyenda	3
      Descripcion del tramo o viaje	4
    */ 
    BEGIN
      SELECT 'DN|'||
             '1|'||--Número de Línea de Nota	1
             '1000|' ||--Código de la leyenda	2
             --- Glosa de la leyenda	3
             TRIM(UPPER(TRIM(FARMA_UTILITY.LETRAS(COMP.VAL_TOTAL_E +
                                             COMP.TOTAL_IGV_E))) || ' ' ||
             DECODE(COMP.COD_TIP_MONEDA,
                    '01',
                    'NUEVOS SOLES',
                    '02',
                    'DOLARES',
                    NULL)) ||'|'||
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
  -- Proposito : Obtener los datos del detalle del comprobante electrónico
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
    
      -- VALIDAR
      IF v_vCOPAGO_BENEF = 'N' THEN
        --  NO ES COPAGO BENEFICIARIO
        -- for REG in (SELECT
        OPEN cursorDetalle FOR 
             SELECT
                    --DETALLE DE DOCUMENTO
                     'DE|' || -- LINEA DE DETALLE
                      DET.SEC_PED_VTA_DET || '|' || -- 1 Correlativo de Línea de Detalle 
                      TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E, 0),
                                   '999999990.00')) || '|' || -- 2 Precio de venta unitario por item
                      'NIU' || '|' || -- 3  Unidad de Medida
                      TRIM(TO_CHAR(NVL(ABS(DET.CANT_UNID_VDD_E), 0),
                                   '999999990.000')) || '|' || -- 4 Cantidad de unidades vendidas pot item (Q)
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_ITEM_E), 0),
                                   '999999990.00')) || '|' || -- 5 Monto del Item
                      DET.COD_PROD || '|' || -- 6 Codigo de Producto
                      DET.COD_TIP_PREC_VTA_E || '|' || -- 7 Tipo de Precio de Venta                     
                     -- TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_UNIT_ITEM_E),0),'999999990.00'))||'|'||-- Valor de venta unitario por ítem
                      TRIM(TO_CHAR(NVL(ABS(DECODE(DET.COD_TIP_AFEC_IGV_E,
                                                  '31',
                                                  0,
                                                  DET.VAL_VTA_UNIT_ITEM_E)),
                                       0),
                                   '999999990.00')) || '|' || -- 8 Valor de venta unitario por ítem
                      TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_ITEM_E), 0),
                                   '999999990.00')) ||'|'|| -- 9 Valor de venta por item
                      ''||'|'||-- 10 Número de lote	10
                      ''||'|'||-- 11 Marca	11
                      ''||'|'||-- 12 Pais de origen	12
                      ''||     -- 13 Nª de Posicion que el Item comprado tiene en la Orden de Compra	13
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
                                       DECODE(DET.COD_TIP_AFEC_IGV_E,
                                              '31',
                                              'PROM.-',
                                              '') || PROD.DESC_PROD ||
                                      -- dubilluz 06.11.2014 
                                       '@#@' ||
                                       DECODE(cab.tip_ped_vta,
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
                                                            ABS(DET.VAL_FRAC)))) 
                                      -- dubilluz 06.11.2014         
                                      ),
                                      CHR(10),
                                      '@@'),
                              CHR(13),
                              '@@') || '|'||
                              -- 2.Nota complementarias a descripción del ítem	2
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
                                   '999999990.00')) || '|' || -- 3 Importe explícito a tributar ( = Tasa Porcentaje * Base Imponible)
                      TRIM(TO_CHAR(NVL(DET.VAL_IGV, 0), '00.00')) || '|' || -- 4 Tasa Impuesto
                      DECODE(DET.COD_TIP_AFEC_IGV_E, 10, 'VAT') || '|' || -- 5 Tipo de Impuesto
                      DET.COD_TIP_AFEC_IGV_E || '|' || -- 6 Afectación del IGV
                      '00' || '|' || -- 7 Sistema de ISC
                      '1000' || '|' || -- 8 Identificación del tributo
                      'IGV' || '|' || -- 9 Nombre del Tributo
                      'VAT' AS VALOR -- 10 Código del Tipo de Tributo
                      FROM VTA_PEDIDO_VTA_DET DET,
                           LGT_PROD           PROD,
                           vta_pedido_vta_cab cab
                     WHERE DET.COD_PROD = PROD.COD_PROD
                       AND DET.COD_GRUPO_CIA = cGrupoCia
                       AND DET.COD_LOCAL = cCodLocal
                       AND DET.NUM_PED_VTA = cNumPedidoVta
                       AND CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                       AND CAB.COD_LOCAL = DET.COD_LOCAL
                       AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
                       AND (CASE
                             WHEN cTipoClienteConvenio = DOC_BENEFICIARIO THEN
                              DET.SEC_COMP_PAGO_BENEF
                             WHEN cTipoClienteConvenio = '2' THEN
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
          '1' || '|' || -- Correlativo de Línea de Detalle
          TRIM(TO_CHAR(NVL(PAGO.VAL_NETO_COMP_PAGO, 0),'999999990.00')) || '|' || -- Precio de venta unitario por item
          'NIU' || '|' || -- Unidad de Medida
          '1' || '|' || -- Cantidad de unidades vendidas pot item (Q)
          TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO), 0),'999999990.00')) || '|' || -- Monto del Item
          NVL(v_vCOD_PROD, '0000') || '|' || -- Codigo de Producto
          --TRIM(TO_CHAR(DECODE(PAGO.PORC_IGV_COMP_PAGO, 18.00, '01', ''),'999999990.00')) || '|' || -- Tipo de Precio de Venta
          '01' || '|' || -- Tipo de Precio de Venta
          TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO - PAGO.VAL_IGV_COMP_PAGO), 0),'999999990.00')) || '|' || -- Valor de venta unitario por ítem
          TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO - PAGO.VAL_IGV_COMP_PAGO), 0),'999999990.00'))  || '|'||-- 9 Valor de venta por item
          ''||'|'||-- 10 Número de lote	10
          ''||'|'||-- 11 Marca	11
          ''||'|'||-- 12 Pais de origen	12
          ''||     -- 13 Nª de Posicion que el Item comprado tiene en la Orden de Compra	13
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
                              -- 2.Nota complementarias a descripción del ítem	2
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
          TRIM(TO_CHAR(ABS(PAGO.VAL_IGV_COMP_PAGO),'999999990.00')) || '|' || -- 3 Importe explícito a tributar ( = Tasa Porcentaje * Base Imponible)
          TRIM(TO_CHAR(NVL(PAGO.PORC_IGV_COMP_PAGO, 0),'999999990.00')) || '|' || -- 4 Tasa Impuesto
          DECODE(PAGO.PORC_IGV_COMP_PAGO, 18.00, 'VAT', '') || '|' || -- 5 Tipo de Impuesto
          DECODE(PAGO.PORC_IGV_COMP_PAGO, 18.00, '10', '30') || '|' || -- 6 Afectación del IGV
          '00' || '|' || -- 7 Sistema de ISC
          '1000' || '|' || -- 8 Identificación del tributo
          'IGV' || '|' || -- 9 Nombre del Tributo
          'VAT' AS VALOR -- || CHR(13) -- 10 Código del Tipo de Tributo
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
         '' || '|' || --2 Código Motivo D/R
         'ANTICIPO' || '|' || --3 Glosa D/R
         'PEN' || '|' || --4 Moneda
         TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_COPAGO_COMP_PAGO), 0),
                      '999999990.00')) || '|' || --5 Monto D/R
         '9999' || '|' || --6 Identificación Tributo
         'OTROS CONCEPTOS DE PAGO' || '|' || --7 Nombre del Tributo
         'OTH' || '|' || --8 Código Tipo de Tributo
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
  
  BEGIN
    BEGIN
    
      SELECT
      --IMPUESTO GLOBAL POR DOCUMENTO
       'DI|' || -- LINEA DE IMPUESTO GLOBAL
       TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E), 0), '999999990.00')) || '|' || -- 1 Sumatoria Tributo (IGV,ISC, Otros)
       TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_IGV_E), 0), '999999990.00')) || '|' || -- 2 Sumatoria Tributo (IGV,ISC, Otros)
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 1000) || '|' || -- 3 Identificación del tributo
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 'IGV') || '|' || -- 4 Nombre del Tributo
       DECODE(PAGO.TOTAL_IGV_E, NULL, '', 'VAT') || CHR(13) -- 5 Código del Tipo de Tributo
      
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
         NVL(PAGO.NUM_COMP_PAGO_EREF, '0') || '|' || -- 1 Serie y número del documento que modifica (Factura)
         '' || '|' || -- 2 Fecha de emisión
         '0'||PAGO.COD_TIP_COMP_PAGO_EREF || '|' || -- 3 Tipo de documento del documento que modifica (Factura)
         -- DECODE(PAGO.COD_TIP_COMP_PAGO_EREF, '1', '380', '3', '346', '383') || '|' || -- 4 Descripción  del tipo de Documento  UN 1001-Document Name
         -- LTAVARA 14.11.2014 NO ENVIAR CODIGO DE DOCUMENTO DE REFERENCIA SEGUN SUNAT
         '0' || '|' || -- 4 Descripción  del tipo de Documento  UN 1001-Document Name
         '' || '|' || -- 5 En el caso de Guías de Remisión Número de guía: serie - número de documento
         '' || '|' || -- 6 En el caso de Guías de Remisión Tipo de Documento
         '' || '|' || -- 7 En el caso de otros tipos de Documentos Número de documento relacionado
         '' || '|' || -- 8 En el caso de otros Tipo de Documento (no factura no guia de remisión)
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
         --NVL(PAGOAUX.NUM_COMP_PAGO, '0')|| '|' || -- 1 Serie y número del documento que modifica (Factura)
         /*
         lpad(SUBSTR(PAGOAUX.NUM_COMP_PAGO,1,3),4,'0')||'-'||lpad(SUBSTR(PAGOAUX.NUM_COMP_PAGO,6,13),8,'0')|| '|' ||
         NVL(TO_CHAR(PAGOAUX.FEC_CREA_COMP_PAGO, 'yyyy-MM-dd'), ' ') || '|' || -- 2 Fecha de emisión
         '09' || '|' || -- 3 Tipo de documento del documento que modifica (Factura)
         '383' || '|' || -- 4 Descripción  del tipo de Documento  UN 1001-Document Name         
         */
          '|' ||
          '|' || -- 2 Fecha de emisión
          '|' || -- 3 Tipo de documento del documento que modifica (Factura)
          '|' || -- 4 Descripción  del tipo de Documento  UN 1001-Document Name         
         
        --11.11.2014 
         --PAGOAUX.NUM_COMP_PAGO || '|' || -- 5 En el caso de Guías de Remisión Número de guía: serie - número de documento
         lpad(SUBSTR(PAGOAUX.NUM_COMP_PAGO,1,3),4,'0')||'-'||lpad(SUBSTR(PAGOAUX.NUM_COMP_PAGO,6,13),8,'0')|| '|' ||
         '09' || '|' || -- 6 En el caso de Guías de Remisión Tipo de Documento
         '' || '|' || -- 7 En el caso de otros tipos de Documentos Número de documento relacionado
         '' || '|' || -- 8 En el caso de otros Tipo de Documento (no factura no guia de remisión)
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
        -- Fecha de recepción	3
        -- Fecha de Pago	4
        -- Hora de Pago	5
        -- Serie - Correlativo del documento que realizó el pago	6
        SELECT CASE
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
               TO_CHAR(RE.Fec_Crea_Comp_Pago,'YYYY-MM-DD') ||'|'|| -- 3 Fecha de recepción
               TO_CHAR(RE.Fec_Crea_Comp_Pago,'YYYY-MM-DD') ||'|'|| -- 4 Fecha de Pago
               TO_CHAR(RE.Fec_Crea_Comp_Pago,'hh:mm:ss') ||'|'|| -- 5 Hora de Pago
               trim(Substrb(RE.NUM_COMP_PAGO_E, 0, 4)||'-'||Substrb(RE.NUM_COMP_PAGO_E, 5))|| -- 6 Serie - Correlativo del documento que realizó el pago	               
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
     end if;   

return vCadena ;
end;    
/* ************************************************************* */                      
  -- Author  : LTAVARA
  -- Created : 14/07/2014 06:50:35 p.m.
  -- Update : 15/09/2014 14:20:35 p.m.
  -- Proposito : Obtener los datos del mensaje antes del timbre del comprobante electrónico
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
  
    IF v_vAhorroTotal > 0 THEN
      v_vPES_AT := v_vPES_AT || 'PESD|' || v_vCONTADOR ||
                   '|EN LA COMPRA USTED AHORRO S/.' ||
                   TO_CHAR(v_vAhorroTotal, '999999990.00') || CHR(13);
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
          SELECT 'PESD|' || v_vCONTADOR || '|Venta Total: S/.' ||
                 TRIM(TO_CHAR(NVL(COMP.VAL_BRUTO_COMP_PAGO, 0),
                              '999999990.00')) || CHR(13) || 'PESD|' ||
                 v_vCONTADOR || '|INSTITUCION-' || COMP.PCT_EMPRESA || '%' ||
                 CHR(13) || 'PESD|' || v_vCONTADOR || '|DOC. REF.- ' ||
                 COMP.PCT_BENEFICIARIO || '%: ' ||
                 (SELECT NVL(COMPE.NUM_COMP_PAGO_E, 0)
                    FROM VTA_COMP_PAGO COMPE
                   WHERE COMPE.COD_GRUPO_CIA = COMP.COD_GRUPO_CIA
                     AND COMPE.COD_LOCAL = COMP.COD_LOCAL
                     AND COMPE.NUM_PED_VTA = COMP.NUM_PED_VTA
                     AND COMPE.NUM_COMP_PAGO = COMP.NUM_COMP_COPAGO_REF) ||
                 '- S/.' || TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO, 0),
                                         '999999990.00')) || CHR(13)
          
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
          SELECT 'PESD|' || v_vCONTADOR || '|BENEFICIARIO -' ||
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
                                         '999999990.00')) || CHR(13)
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
      
       vLinea := REPLACE(vLinea,'Ã','@-');
        vLinea := REPLACE(vLinea,'|','-');
        FOR LISTA IN (
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL 
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE((
                           vLinea
                           ),'&','Ã')),'<','Ë'),'@-','</e><e>') ||'</e></coll>'),'/coll/e'))) xt ) LOOP
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
  -- Proposito : Obtener los datos del mensaje depues del timbre del comprobante electrónico
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
      SELECT P.TIP_COMP_PAGO || 'Ã' || P.NUM_PED_VTA || 'Ã' ||
             P.SEC_COMP_PAGO || 'Ã' || nvl(P.TIP_CLIEN_CONVENIO, ' ') || 'Ã' ||
             C.TIP_COMP_PAGO || 'Ã' || R.IP_SERVIDOR || 'Ã' || R.PUERTO_BD || 'Ã' ||
             R.MODO || 'Ã' || N.NUM_RUC_CIA || 'Ã' || NUM_COMP_PAGO_E
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
  begin

             --RAISE_APPLICATION_ERROR(-20999,p_cod_grupo_cia||'-'||p_cod_local||'-'||p_num_ped_vta||'-'||p_sec_com_pago);

      update vta_pedido_vta_det
         set val_prec_vta_unit_e = decode(cod_tip_afec_igv_e, '31', val_prec_public, (((val_prec_total + ahorro) / cant_atendida) * val_frac)),
             val_vta_unit_item_e = round(decode(cod_tip_afec_igv_e, '31', val_prec_public, (((val_prec_total + ahorro) / cant_atendida) * val_frac)) /
                                         decode(val_igv, 0, 1, (1 + (val_igv / 100))), 2),
             cant_unid_vdd_e = trunc((cant_atendida / val_frac), 3),
             val_vta_item_e = round(val_prec_total / (1 + (val_igv / 100)), 2),
             --val_total_igv_item_e = trunc(trunc(val_prec_total / (1 + (val_igv / 100)), 2) * (val_igv / 100), 2),
             val_total_igv_item_e = round(val_prec_total / (1 + (val_igv / 100)) * (val_igv / 100), 2),
             val_total_desc_item_e = round((ahorro / (1 + (val_igv / 100))), 2)
       where cod_grupo_cia = p_cod_grupo_cia
         and cod_local = p_cod_local
         and num_ped_vta = p_num_ped_vta
         and decode(p_tip_clien_convenio, '1', sec_comp_pago_benef,
                                          '2',  sec_comp_pago_empre,
                                          sec_comp_pago) =p_sec_com_pago;

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
                     sum(decode(cod_tip_afec_igv_e, '31', round(val_vta_unit_item_e * cant_unid_vdd_e, 2), 0)) total_gratu_e,
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
    if  vIsCompEmpresa_COPAGO = 'S' then
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
    else
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
     end if;
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


  FUNCTION IMP_COMP_ELECT ( vCodGrupoCia_in VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                            vCodLocal_in    VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                            vNumPedVta_in   VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                            vSecCompPago_in VTA_PEDIDO_VTA_CAB.SEC_COMP_PAGO%TYPE,
                            vVersion_in     REL_APLICACION_VERSION.NUM_VERSION%TYPE
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
   vLinea               VARCHAR2(4000);
   vPDF417              VTA_COMP_PAGO.COD_PDF417_E%TYPE;
   cCantidadCupon       INTEGER DEFAULT 0;
   cursorComprobante    FARMACURSOR;
   vSecMovCaja          VTA_PEDIDO_VTA_CAB.SEC_MOV_CAJA%TYPE;

  BEGIN 
    delete TMP_DOCUMENTO_ELECTRONICOS;  
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
     
      /**TIPO DE PEDIDO DE VENTA*/
    SELECT  CAB.TIP_PED_VTA, 
            NVL(CAB.IND_PED_CONVENIO,'N'),
            CAB.SEC_MOV_CAJA
      INTO  vTip_Ped_vta, 
            vPedidoConvenio,
            vSecMovCaja
      FROM  VTA_PEDIDO_VTA_CAB CAB
      WHERE CAB.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   CAB.COD_LOCAL     = vCodLocal_in
      AND   CAB.NUM_PED_VTA   = vNumPedVta_in;
      
    /** CABECERA DE PEDIDO **/
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','C','N','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE((
        SELECT 
          NVL(CIA.RAZ_SOC_CIA, ' ')||' - RUC: '||NVL(CIA.NUM_RUC_CIA, ' ') ||'@'||
          TRIM(SUBSTR(TRIM(CIA.DIR_CIA),0,INSTR(TRIM(CIA.DIR_CIA), ' - '||UBI.NODIS ||' - '|| UBI.NOPRV)))||'@'||
          (UBI.NODIS ||' - '|| UBI.NOPRV)||'@'||
          'TELF.: '||NVL(CIA.TELF_CIA, ' ')||'@'||
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
      ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
    
    /** NOMBRE Y NRO DE COMPROBANTE **/
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','C','S','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                           REPLACE( REPLACE((REPLACE((      
        SELECT 
          CASE 
            WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN 'BOLETA ELECTRONICA'
            WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) THEN 'FACTURA ELECTRONICA'
            WHEN COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED THEN 'NOTA DE CREDITO ELECTRONICA'
          END ||'@'||
          FARMA_UTILITY.GET_T_COMPROBANTE_2(COMP.COD_TIP_PROC_PAGO, COMP.NUM_COMP_PAGO_E, COMP.NUM_COMP_PAGO) ||'@'
        FROM VTA_COMP_PAGO       COMP
        WHERE COMP.COD_GRUPO_CIA   = vCodGrupoCia_in
        AND   COMP.COD_LOCAL     = vCodLocal_in
        AND   COMP.NUM_PED_VTA     = vNumPedVta_in
        AND   COMP.SEC_COMP_PAGO   = vSecCompPago_in
      ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;   
     
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','I','N','N'
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
     
      ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;

    /** CABECERA DE DETALLE DE PEDIDO **/
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES(RPAD('CODIGO',8)|| RPAD('DESCRIPCION',18)|| LPAD('CANT.',10) || LPAD('P.UNIT.',9) || LPAD('DSCTO.',8)|| LPAD('IMPORTE',11),'1','D','S','N');
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES('-','1','I','N','N');

    /*** DETALLE DE PEDIDO **/
    IF vCopagoCliente = 'S' THEN
      /** CODIGO DE PRODUCTOS PARA COPAGO **/
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
                            '999,999,990.00')
                    )
              ,9)||
          LPAD('0.00',8) ||
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
          ,'1','D','N','N'
        FROM  VTA_COMP_PAGO PAGO
        WHERE PAGO.COD_GRUPO_CIA   = vCodGrupoCia_in
        AND   PAGO.COD_LOCAL       = vCodLocal_in
        AND   PAGO.NUM_PED_VTA     = vNumPedVta_in
        AND   PAGO.SEC_COMP_PAGO   = vSecCompPago_in;
    ELSE
      IF ( vTipoDocumento = COD_TIP_COMP_FACTURA  OR vTipoDocumento = COD_TIP_COMP_TICKET_FA  OR( vTipoDocumento = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer='1'))  THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
          SELECT 
            -- Codigo de Producto
            RPAD(NVL(DET.COD_PROD,' '),8)  ||
            --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
            RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,' ')),0,17),18) ||
            --Cantidad vendida en fracciones
            LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
               DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Imprime las cantidad
                              ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','')) ,--sin fraccion
               DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                              (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Caso contrario las imprime en fracciones
                              ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','/'||ABS(DET.VAL_FRAC)))),10) || 
            -- Valor de venta unitario por ítem(no incluye igv)
            LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_UNIT_ITEM_E),0),'999,999,990.00')),0),9) ||
            -- Descuento del Item(no incluye igv)
            LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_TOTAL_DESC_ITEM_E),0),'999,999,990.00')),0),8) ||
            LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_VTA_ITEM_E),0),'999,999,990.00')),0),11)
            ,'1','D','N','N'
          FROM  VTA_PEDIDO_VTA_DET DET, 
              LGT_PROD PROD/*, LGT_PROD_VIRTUAL VIRT*/
          WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   DET.COD_LOCAL   = vCodLocal_in
          AND   DET.NUM_PED_VTA   = vNumPedVta_in
          AND  (CASE
              WHEN vTipoClienteConvenio = '1' THEN DET.SEC_COMP_PAGO_BENEF
              WHEN vTipoClienteConvenio = '2' THEN DET.SEC_COMP_PAGO_EMPRE
              ELSE DET.SEC_COMP_PAGO
              END) = vSecCompPago_in
          AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND   DET.COD_PROD     = PROD.COD_PROD
          ORDER BY DET.SEC_PED_VTA_DET ASC;
          
      ELSIF(vTipoDocumento = COD_TIP_COMP_BOLETA  OR   vTipoDocumento = COD_TIP_COMP_TICKET OR( vTipoDocumento = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer='3') ) THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
          SELECT 
            -- Codigo de Producto
            RPAD(NVL(DET.COD_PROD,'-'),8) ||
            --27/10/2014 CUANDO ES UNA PROMOCION DEBE IR ADELANTE LA PALABRA PROMOCION
            RPAD(SUBSTR(DECODE(DET.COD_TIP_AFEC_IGV_E,'31','PROM-'||PROD.DESC_PROD,NVL(PROD.DESC_PROD,'-')),0,17),18) ||
            --Cantidad vendida en fracciones
            LPAD(DECODE(vTip_Ped_vta, TIPO_VTA_INSTITUCIONAL,--Si el Tipo de venta es Mayorista
               DECODE(MOD(DET.CANT_ATENDIDA,DET.VAL_FRAC),0,
                              (DET.CANT_ATENDIDA/DET.VAL_FRAC)||'',--Imprime las cantidad
                              DET.CANT_ATENDIDA||DECODE(DET.VAL_FRAC,1,'','')) ,--sin fraccion
               DECODE(MOD(ABS(DET.CANT_ATENDIDA),ABS(DET.VAL_FRAC)),0,
                              (ABS(DET.CANT_ATENDIDA)/ABS(DET.VAL_FRAC))||'',--Caso contrario las imprime en fracciones
                              ABS(DET.CANT_ATENDIDA)||DECODE(ABS(DET.VAL_FRAC),1,'','/'||ABS(DET.VAL_FRAC)))),10)  || 
            -- Precio de venta unitario por item(incluye igv)
            LPAD(NVL(TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E,0),'999,999,990.00')),0),9) || 
            -- Descuento del Item(incluye IGV)
            LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.AHORRO),0),'999,999,990.00')),0),8) ||
            -- Monto del Item (no incluye IGV)
            LPAD(NVL(TRIM(TO_CHAR(NVL(ABS(DET.VAL_PREC_TOTAL),0),'999,999,990.00')),0),11)
            ,'1','D','N','N'
          FROM  VTA_PEDIDO_VTA_DET DET, 
              LGT_PROD PROD/*, LGT_PROD_VIRTUAL VIRT*/
          WHERE DET.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   DET.COD_LOCAL = vCodLocal_in
          AND   DET.NUM_PED_VTA = vNumPedVta_in
          AND   (CASE
                WHEN vTipoClienteConvenio = '1' THEN DET.SEC_COMP_PAGO_BENEF
                WHEN vTipoClienteConvenio = '2' THEN DET.SEC_COMP_PAGO_EMPRE
                ELSE DET.SEC_COMP_PAGO
              END) = vSecCompPago_in
          AND   DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
          AND   DET.COD_PROD     = PROD.COD_PROD
          ORDER BY DET.SEC_PED_VTA_DET ASC ;
      END IF;  
    END IF;
     
    /*** MONTOS TOTALES **/
    INSERT INTO TMP_DOCUMENTO_ELECTRONICOS 
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','D','N','N'
      FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((

        SELECT  
          CASE WHEN vCopagoEmpresa != 'S' THEN 
            '-' || '@' END ||
          --Redondeo
          CASE WHEN vCopagoEmpresa != 'S' AND NVL(PAGO.VAL_REDONDEO_COMP_PAGO,0)!=0 THEN
            LPAD('REDONDEO: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_REDONDEO_COMP_PAGO)*-1,0),'999,999,990.00')),15) || '@' END ||
          --total a pagar si es copago
          CASE WHEN vCopagoEmpresa != 'S' THEN 
            LPAD('TOTAL A PAGAR: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO+PAGO.VAL_REDONDEO_COMP_PAGO),0),'999,999,990.00')),15) || '@' END ||
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
          CASE WHEN NVL(PAGO.TOTAL_DESC_E,0)!=0 THEN
            LPAD('DESCUENTO: S/.',20)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.TOTAL_DESC_E),0),'999,999,990.00')),15) || '@' 
          END ||
          -- Monto Total (lo que debe  pagar el cliente)
          CASE WHEN NVL(ABS(PAGO.VAL_NETO_COMP_PAGO) + (CASE 
                                  WHEN PAGO.TIP_CLIEN_CONVENIO = DOC_EMPRESA 
                                      AND PAGO.PCT_BENEFICIARIO > 0  
                                      AND PAGO.PCT_EMPRESA > 0 THEN 
                                    ABS(PAGO.VAL_COPAGO_COMP_PAGO)
                                  ELSE 0 
                                  END), 0) != 0 THEN
              
              LPAD('IMPORTE TOTAL: S/.',20)|| CASE WHEN vCopagoEmpresa = 'S' THEN
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
                          END
           END ||
           CASE WHEN vCopagoEmpresa = 'S' THEN 
                         '-' || '@' ||
                         LPAD('COPAGO '||TRIM(TO_CHAR(NVL(ABS(NVL(PAGO.PCT_BENEFICIARIO,0)),0),'999,999,990.00'))||'% S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_COPAGO_COMP_PAGO),0),'999,999,990.00')),15) || '@' ||
                         LPAD('TOTAL A PAGAR: S/.',40)||LPAD(TRIM(TO_CHAR(NVL(ABS(PAGO.VAL_NETO_COMP_PAGO),0),'999,999,990.00')),15)|| '@' ||
                         '-' || '@'
          END 
              FROM VTA_COMP_PAGO PAGO
              WHERE PAGO.COD_GRUPO_CIA = vCodGrupoCia_in
              AND PAGO.COD_LOCAL = vCodLocal_in
              AND PAGO.NUM_PED_VTA= vNumPedVta_in
              AND PAGO.SEC_COMP_PAGO = vSecCompPago_in
      ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;   
         
    /*** FORMAS DE PAGO **/       
    -- NOTA DE CREDITO NO MOSTRARA FORMA DE PAGO
      IF vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN
          FOR FORMAPAGO IN (
          SELECT
            NVL(A.COD_FORMA_PAGO,'-') COD_FORMA_PAGO,
            NVL(A.TIP_MONEDA,'-') TIP_MONEDA,
            TRIM(TO_CHAR(NVL(ABS(A.IM_PAGO),0),'999,999,990.00')) MONTO,
            TRIM(TO_CHAR(NVL(ABS(A.VAL_VUELTO),0),'999,999,990.00')) VUELTO, 
            TRIM(TO_CHAR(NVL(ABS(A.VAL_TIP_CAMBIO),0),'999,999,990.00')) TIPO_CAMBIO,
            C.DESC_FORMA_PAGO DESCRIPCION
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
          IF FORMAPAGO.COD_FORMA_PAGO  = COD_FORMA_PAGO_DOLARES THEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD('TIPO DE CAMBIO: '||FORMAPAGO.TIPO_CAMBIO||' - '||FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'1','D','N','N');
          ELSE
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD(FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'1','D','N','N');
                  END IF;
                    
                  IF FORMAPAGO.VUELTO != '0.00' THEN
            INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD('VUELTO: S/.',40)||LPAD(FORMAPAGO.VUELTO,15),'1','D','N','N');
          END IF;
        ELSE
          IF vCopagoEmpresa = 'N' AND vCopagoCliente = 'S' THEN
            IF FORMAPAGO.COD_FORMA_PAGO  = COD_FORMA_PAGO_DOLARES THEN
              INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD('TIPO DE CAMBIO: '||FORMAPAGO.TIPO_CAMBIO||' - '||FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'1','D','N','N');
            ELSE
              IF FORMAPAGO.COD_FORMA_PAGO  = COD_FORMA_PAGO_SOLES THEN
                INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD(FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'1','D','N','N');
              ELSE
                IF FORMAPAGO.COD_FORMA_PAGO != COD_FORMA_PAGO_CONVENIO THEN
                  INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD(FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'1','D','N','N');
                END IF;
              END IF;
            END IF;
                      
                      IF FORMAPAGO.VUELTO != '0.00' THEN
              INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD('VUELTO: S/.',40)||LPAD(FORMAPAGO.VUELTO,15),'1','D','N','N');
            END IF;
          ELSE
            IF FORMAPAGO.COD_FORMA_PAGO = COD_FORMA_PAGO_CREDITO THEN
              INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (LPAD(FORMAPAGO.DESCRIPCION,40)||LPAD(FORMAPAGO.MONTO,15),'1','D','N','N');
            END IF;
          END IF;
        END IF;
      END LOOP;
      END IF;
    
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ','1','D','N','N');
      
    /*** MONTO EN LETRAS ***/
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
      SELECT 
        'SON : ' || 
        UPPER(TRIM(FARMA_UTILITY.LETRAS((COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO) /*+ (CASE WHEN COMP.TIP_CLIEN_CONVENIO = DOC_EMPRESA AND COMP.PCT_BENEFICIARIO > 0 AND COMP.PCT_EMPRESA > 0 THEN
                                                          ABS(COMP.VAL_COPAGO_COMP_PAGO)
                                                        ELSE
                                                          0
                                                      END)*/
                        ))) ||
        ' NUEVOS SOLES.'
        ,'1','I','N','N'
      FROM VTA_COMP_PAGO COMP
      WHERE COMP.COD_GRUPO_CIA = vCodGrupoCia_in
          AND   COMP.COD_LOCAL     = vCodLocal_in
          AND   COMP.NUM_PED_VTA   = vNumPedVta_in
          AND   COMP.SEC_COMP_PAGO = vSecCompPago_in;

      /*** AHORRO ***/
      IF vTipoDocumento != COD_TIP_COMP_NOTA_CRED THEN
        IF vMontoAhorro != '0.00' THEN
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('EN LA COMPRA USTED AHORRO: S/.'||vMontoAhorro,'1','I','N','N');
        END IF;
      END IF;
      
      IF vTip_Ped_vta = TIPO_VTA_DELIVERY THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
          SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','I','N','N'
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
          ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
      ELSE
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
          SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','I','N','N'
          FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
            SELECT   
              CASE 
                WHEN NVL(TRIM(COMP.NOM_IMPR_COMP), '*') != '*' THEN
                  CASE 
                    WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) OR (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '3') THEN
                      'NOMBRE DE CLIENTE: '|| COMP.NOM_IMPR_COMP || '@'
                    WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) OR (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '1') THEN
                      'RAZON SOCIAL: ' || COMP.NOM_IMPR_COMP || '@'
                  END
              END ||
              CASE 
                WHEN NVL(TRIM(COMP.NUM_DOC_IMPR), '*') != '*' THEN
                  CASE 
                    WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) OR (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '3') THEN
                      'DNI CLIENTE: '|| COMP.NUM_DOC_IMPR || '@'
                    WHEN COMP.TIP_COMP_PAGO IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) OR (COMP.TIP_COMP_PAGO = COD_TIP_COMP_NOTA_CRED AND vTipoDocRefer = '1') THEN
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
        ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
      END IF;
        
      /**  DATOS DEL CONVENIO **/
      /** DATOS ADICIONALES CONVENIO **/
      IF vPedidoConvenio='S' AND vTip_Ped_vta != TIPO_VTA_INSTITUCIONAL THEN
        IF vTipoClienteConvenio= '1' or vTipoClienteConvenio= '2' THEN
          -- DATOS CONVENIO
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
            SELECT 'CONVENIO: '|| CONV.DES_CONVENIO ,'1','I','N','N'
            FROM   VTA_PEDIDO_VTA_CAB CAB, 
                 MAE_CONVENIO CONV
            WHERE CAB.COD_GRUPO_CIA = vCodGrupoCia_in
            AND   CAB.COD_LOCAL     = vCodLocal_in
            AND   CAB.NUM_PED_VTA   = vNumPedVta_in
            AND   CAB.COD_CONVENIO  = CONV.COD_CONVENIO;
             
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
            SELECT UPPER(DATOS.NOMBRE_CAMPO||': '||DATOS.DESCRIPCION_CAMPO)  ,'1','I','N','N'
            FROM   CON_BTL_MF_PED_VTA  DATOS 
            WHERE  DATOS.COD_GRUPO_CIA = vCodGrupoCia_in
            AND    DATOS.COD_LOCAL     = vCodLocal_in
            AND    DATOS.NUM_PED_VTA   = vNumPedVta_in;
             
          INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
            SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
              SELECT
                CASE 
                  --PARA EMPRESA
                  WHEN COMP.TIP_CLIEN_CONVENIO = '2' THEN
                    --venta total para la empresa
                    'VENTA TOTAL: S/.' || TRIM(TO_CHAR(NVL(COMP.VAL_BRUTO_COMP_PAGO, 0), '999,999,990.00')) || '@' ||
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
            ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
        END IF;
      END IF;

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
          AND A.COD_PROD = DET.COD_PROD
        );
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
            vLinea := REPLACE(vLinea,'Ã','@');
          --ELSE
            --vLinea := vLinea ||'@'|| REPLACE(vLinea,'Ã','@');
          END IF;
        END LOOP;
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','I','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
            vLinea
        ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;  
      END IF;
      
      /***   PDF417   ****/
      IF NVL(vPDF417, '*') != '*' THEN
         INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (vPDF417, '1','P','N','N');
      END IF; 
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '1','I','N','N');
      
      /** PIE DE PAGINA **/    
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS
      SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL ,'1','C','N','N'
            FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE((
        SELECT
       'Guarda tu voucher.'||'@'||
       'Es el sustento para validar tu compra.'||'@'||
       'Representacion impresa de la '||
       (CASE 
         WHEN vTipoDocumento IN (COD_TIP_COMP_BOLETA, COD_TIP_COMP_TICKET) THEN 'BOLETA ELECTRONICA'
         WHEN vTipoDocumento IN (COD_TIP_COMP_FACTURA, COD_TIP_COMP_TICKET_FA) THEN 'FACTURA ELECTRONICA'
         WHEN vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN 'NOTA DE CREDITO ELECTRONICA'
        END)
       ||'@'||
       'puede ser consultado en www.mifarma.com.pe .'||'@'||
       (SELECT  DESC_LARGA FROM PTOVENTA.PBL_TAB_GRAL  WHERE ID_TAB_GRAl='604')||'@'||
       'No se aceptan devoluciones de dinero.'||'@'||
       'Cambio de mercaderia unicamente dentro'||'@'||
       'de las 48 horas siguientes a la compra.'||'@'||
       'Indispensable presentar comprobante.' ||'@'||
       '-'||'@'
      FROM DUAL
      ),'&','Ã')),'<','Ë'),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt;
      
      INSERT INTO TMP_DOCUMENTO_ELECTRONICOS  
      SELECT vVersion_in||' - USUARIO: ' ||USU.LOGIN_USU|| ' - CAJA: ' || MOV.NUM_CAJA_PAGO , '1','C','N','N'
      FROM CE_MOV_CAJA MOV,
           PBL_USU_LOCAL USU
      WHERE USU.COD_GRUPO_CIA = MOV.COD_GRUPO_CIA
      AND   USU.COD_LOCAL     = MOV.COD_LOCAL
      AND   USU.SEC_USU_LOCAL = MOV.SEC_USU_LOCAL
      AND   MOV.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   MOV.COD_LOCAL     = vCodLocal_in
      AND   MOV.SEC_MOV_CAJA  = vSecMovCaja;    
            
      
      SELECT SUM(CANTIDAD)
      INTO cCantidadCupon
      FROM VTA_PEDIDO_CUPON CUPON
      WHERE CUPON.COD_GRUPO_CIA = vCodGrupoCia_in
      AND   CUPON.COD_LOCAL     = vCodLocal_in 
      AND   CUPON.NUM_PED_VTA   =  vNumPedVta_in;
      
      IF cCantidadCupon > 0 THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('POR SU COMPRA USTED GANO: '||cCantidadCupon ||
                                                        (CASE
                                                           WHEN cCantidadCupon>1 THEN ' CUPONES.'
                                                           ELSE ' CUPON.'
                                                         END), '1','C','N','N');
      END IF;                                                   
      
      /**IMPRIME LOS DATOS PARA LA NOTA DE CREDITO*/
      IF vTipoDocumento = COD_TIP_COMP_NOTA_CRED THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('DATOS DEL CLIENTE', '1','C','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('NOBRE:______________________________________________', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('DNI:________________________________________________', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES (' ', '1','I','N','N');
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('FIRMA:______________________________________________', '1','I','N','N');
      END IF;
      
      IF vCopagoCliente = 'S' THEN
        INSERT INTO TMP_DOCUMENTO_ELECTRONICOS VALUES ('-', '1','I','N','N');
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
                      LGT_PROD PROD/*, LGT_PROD_VIRTUAL VIRT*/
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
                      LGT_PROD PROD/*, LGT_PROD_VIRTUAL VIRT*/
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
              END IF;  
      END IF;
      
      OPEN cursorComprobante FOR
        SELECT A.VALOR, A.TAMANIO, A.ALINEACION, A.BOLD, A.AJUSTE
        FROM TMP_DOCUMENTO_ELECTRONICOS A
        WHERE A.VALOR IS NOT NULL;
      RETURN cursorComprobante;
  END;

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
     RAISE_APPLICATION_ERROR(-20000,'Tip Comp Pago no es Válido 99');
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

    SELECT v_vPES_DT || 'PE|DIRLOCAL|' || TRIM(A.DIREC_LOCAL) || CHR(13)
    INTO   v_vPES_DT
    FROM PBL_LOCAL A 
    WHERE A.COD_GRUPO_CIA = cGrupoCia 
    AND A.COD_LOCAL       = cCodLocal;
  
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
        AND   A.COD_LOCAL     = vCodGrupoCia_in
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
        AND   A.COD_LOCAL     = vCodGrupoCia_in
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
                          '                    <font color=#C00000 size =8 face=Arial>¡¡ ALERTA !!</font>'||
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
                          '                    <span lang=ES style="font-family:Arial,sans-serif; color:#139128"> </span>'||
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
                          '                      <span style="mso-list:Ignore">·'||
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
                        '      style="mso-list:Ignore">·<span style="font:7.0pt Times New Roman">;;;;;; '||
                        '      </span></span></span><![endif]><b><span lang=ES style="font-size:18.0pt; '||
                        '      font-family:Arial,sans-serif;color:#139128"><font color=#139128 size =4 face=Arial>Si cuenta con papel para '||
                        '      imprimir .</font><o:p></o:p></span></b></p> '||
                        '      <p class=MsoListParagraph style="margin-left:22.5pt;text-indent:-18.0pt; '||
                        '      mso-list:l0 level1 lfo1"><![if !supportLists]><span lang=ES '||
                        '      style="font-size:18.0pt;font-family:Symbol;color:#139128"><span '||
                        '      style="mso-list:Ignore">·<span style="font:7.0pt Times New Roman">;;;;;; ';
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
    AND   CP.TIP_COMP_PAGO = cCodTipoDocumento_in
    AND   CP.SEC_COMP_PAGO = cSecCompPago_in;
    
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

END FARMA_EPOS;
/
