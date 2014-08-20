--------------------------------------------------------
--  DDL for Package Body PKG_CONEXION_POS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PKG_CONEXION_POS" as

-- Author  : LTAVARA
-- 14/07/2014 Paquete creado 
-- 06/08/2014 FN_OBTENER_CABECERA - modificado




     -- Author  : LTAVARA
    -- Created : 14/07/2014 06:50:35 p.m.
    -- Proposito : Obtener la cabecera del comprobante electrónico
  FUNCTION FN_OBTENER_CABECERA(   cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2
                                  ) 
   RETURN VARCHAR2 AS
        v_vCabecera VARCHAR2(3276) := NULL;
      
    BEGIN
  
        BEGIN
         SELECT 'EN|' || -- ENCABEZADO
         CASE COMP.TIP_COMP_PAGO WHEN '02' THEN '01' WHEN '06' THEN '01' WHEN '01' THEN '03' WHEN '05' THEN '03' WHEN '04' THEN '07' END||'|'|| -- 01 Tipo de Documento
         NVL(TRIM(COMP.NUM_COMP_PAGO_E),0)||'|'|| -- 02 Serie y Correlativo Documento
         COMP.COD_TIP_MOTIVO_NOTA_E || '|' || -- 03 Tipo de Nota de crédito/Nota de Débito (Motivo de NC/ND)
         COMP.NUM_COMP_PAGO_EREF || '|' || -- 04 Factura/Boleta  que referencia la Nota de Crédito/Nota de Débito
         DECODE(COMP.NUM_COMP_PAGO_EREF,null,'','pruebaaa PED.MOTIVO_ANULACION') || '|' || -- 05 Sustento
        --  NVL(TO_CHAR(COMP.FEC_CREA_COMP_PAGO,'yyyy-MM-dd'),' ')||'|'||-- 06 Fecha Emision
        NVL(TO_CHAR(sysdate,'yyyy-MM-dd'),' ')||'|'||-- 06 Fecha Emision
         DECODE(COMP.COD_TIP_MONEDA,'01','PEN','02','USD','PEN')||'|'||-- 07 Tipo de Moneda
         CIA.NUM_RUC_CIA|| -- 08 RUC Emisor
         '|6|'|| -- 09 Tipo de Identificación Emisor
         CIA.NOM_CIA||'|'||-- 10 Nombre Comercial Emisor
         CIA.RAZ_SOC_CIA||'|'||-- 11 Razon Social Emisor
         UBI.UBDEP||UBI.UBPRV||UBI.UBDIS||'|'|| -- 12 Codigo UBIGEO Emisor
         CIA.DIR_CIA||'|'||-- 13 Direccion Emisor
         UBI.NODEP||'|'||-- 14 Departamento Emisor 
         UBI.NOPRV||'|'||-- 15 Provincia Emisor (Comuna)
         UBI.NODIS||'|'||-- 16 Distrito Emisor
         NVL(TRIM(COMP.NUM_DOC_IMPR),0)||'|'||-- 17 RUC Receptor
         COMP.COD_TIP_IDENT_RECEP_E||'|'||-- 18 Tipo de Identificacion Receptor
         NVL(TRIM(COMP.NOM_IMPR_COMP),'-')||'|'|| -- 19 Razon Social Receptor
         COMP.DIREC_IMPR_COMP||'|'||-- 20 Direccion Receptor
         DECODE(COMP.TIP_COMP_PAGO,'04',TRIM(TO_CHAR(NVL(COMP.VAL_NETO_COMP_PAGO*-1,0),'999,999,990.00')),TRIM(TO_CHAR(NVL(COMP.VAL_NETO_COMP_PAGO,0),'999,999,990.00')))||'|'||-- 21 Monto Neto
         TRIM(TO_CHAR(NVL(COMP.TOTAL_IGV_E,0),'999,999,990.00'))||'|'||-- 22 Monto Impuesto
         TRIM(TO_CHAR(NVL(COMP.TOTAL_DESC_E,0),'999,999,990.00'))||'|'||-- 23 Monto Descuentos
          0|| '|' || -- 24 Monto Recargos
         TRIM(TO_CHAR(NVL(COMP.VAL_TOTAL_E,0),'999,999,990.00'))||'|'||-- 25 Monto Total
         '' || '|' || -- 26 Códigos de otros conceptos tributarios o comerciales recomendados
         '' || '|' || -- 27 Total de Valor Venta Neto
          NVL(TRIM(COMP.NUM_DOC_IMPR),0)||'|'|| -- 28 número de documento de identidad del adquirente o usuario
         COMP.COD_TIP_IDENT_RECEP_E||'|'||-- 29 Tipo de documento de identidad del adquirente o usuario
         'PE'||'|'||-- 30 Código País Emisor
          ''||'|'||-- 31 Urbanización Emisor
         CASE PED.TIP_PED_VTA WHEN '02' --PEDIDO DELIVERY
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
         CHR(13)
         INTO   v_vCabecera
   
  FROM VTA_PEDIDO_VTA_CAB PED, vta_COMP_PAGO COMP,PBL_LOCAL LOCAL, PBL_CIA CIA, UBIGEO UBI, UBIGEO UBI_LOCAL
  WHERE 
       PED.NUM_PED_VTA= COMP.NUM_PED_VTA
  AND 
       COMP.COD_LOCAL=LOCAL.COD_LOCAL
  AND 
       LOCAL.COD_CIA= CIA.COD_CIA
  AND 
       CIA.UBDEP=UBI.UBDEP
  AND 
       CIA.UBPRV=UBI.UBPRV
  AND 
       CIA.UBDIS=UBI.UBDIS 
  AND 
       LOCAL.UBDEP=UBI_LOCAL.UBDEP
  AND 
       LOCAL.UBPRV=UBI_LOCAL.UBPRV
  AND 
      LOCAL.UBDIS=UBI_LOCAL.UBDIS
  AND
       COMP.COD_GRUPO_CIA=cGrupoCia
  AND
       COMP.COD_LOCAL=cCodLocal      
  AND
       COMP.NUM_PED_VTA=cNumPedidoVta  
  AND 
       COMP.SEC_COMP_PAGO=cSecCompPago
  AND
       COMP.TIP_COMP_PAGO=cTipoDocumento;
   END;

   
   RETURN v_vCabecera;
   END FN_OBTENER_CABECERA;


    -- Author  : LTAVARA
    -- Created : 14/07/2014 06:50:35 p.m.
    -- Proposito : Obtener los datos de otros conceptos del comprobante electrónico
  FUNCTION FN_OBTENER_DOC( 
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2
                                  ) 

   RETURN VARCHAR2 AS
        v_vDOC VARCHAR2(5555) := NULL;
    BEGIN
      BEGIN
          -- DETALLE DE OTROS CONCEPTOS
     
             FOR REG IN (
          -- 1001 Total valor de venta - operaciones gravadas
        select 'DOC|' || '1001|' ||  TRIM(TO_CHAR(NVL(COMP.TOTAL_GRAV_E,0),'999,999,990.00'))  ||CHR(13) VALOR 
         FROM         VTA_COMP_PAGO COMP
         WHERE 
                       COMP.COD_GRUPO_CIA=cGrupoCia
                  AND
                       COMP.COD_LOCAL=cCodLocal      
                  AND
                       COMP.NUM_PED_VTA=cNumPedidoVta  
                  AND 
                       COMP.SEC_COMP_PAGO=cSecCompPago
                  AND
                      COMP.TIP_COMP_PAGO=cTipoDocumento
                 AND
                 COMP.TOTAL_GRAV_E != 0
         UNION ALL
        
        -- 1002 Total valor de venta - operaciones inafectas
        select 'DOC|' || '1002|' ||  TRIM(TO_CHAR(NVL(COMP.TOTAL_INAF_E,0),'999,999,990.00')) ||CHR(13) VALOR
          FROM VTA_COMP_PAGO COMP
          WHERE      
                       COMP.COD_GRUPO_CIA=cGrupoCia
                  AND
                       COMP.COD_LOCAL=cCodLocal      
                  AND
                       COMP.NUM_PED_VTA=cNumPedidoVta  
                  AND 
                       COMP.SEC_COMP_PAGO=cSecCompPago
                  AND
                      COMP.TIP_COMP_PAGO=cTipoDocumento
                AND 
                      COMP.TOTAL_INAF_E!= 0
         UNION ALL
       
       -- 1003 Total valor de venta - operaciones exoneradas
        select 'DOC|' || '1003|' ||  TRIM(TO_CHAR(NVL(COMP.TOTAL_EXON_E,0),'999,999,990.00')) ||CHR(13) VALOR
          FROM VTA_COMP_PAGO COMP
          WHERE
                   COMP.COD_GRUPO_CIA=cGrupoCia
              AND
                   COMP.COD_LOCAL=cCodLocal      
              AND
                   COMP.NUM_PED_VTA=cNumPedidoVta  
              AND 
                   COMP.SEC_COMP_PAGO=cSecCompPago
              AND
                  COMP.TIP_COMP_PAGO=cTipoDocumento
             AND
                  COMP.TOTAL_EXON_E!= 0
         UNION ALL
       
        -- 1004 Total valor de venta  Operaciones gratuitas
        select 'DOC|' || '1004|' ||  TRIM(TO_CHAR(NVL(COMP.TOTAL_GRATU_E,0),'999,999,990.00')) ||CHR(13) VALOR
        FROM VTA_COMP_PAGO COMP
        WHERE 
                  COMP.COD_GRUPO_CIA=cGrupoCia
              AND
                   COMP.COD_LOCAL=cCodLocal      
              AND
                   COMP.NUM_PED_VTA=cNumPedidoVta  
              AND 
                   COMP.SEC_COMP_PAGO=cSecCompPago
              AND
                  COMP.TIP_COMP_PAGO=cTipoDocumento
             AND  COMP.TOTAL_GRATU_E!= 0
        UNION ALL
      
        -- 2005 Total descuentos
        select 'DOC|' || '2005|' ||  TRIM(TO_CHAR(NVL(COMP.TOTAL_DESC_E,0),'999,999,990.00')) ||CHR(13) VALOR 
         FROM VTA_COMP_PAGO COMP
         WHERE         
                 COMP.COD_GRUPO_CIA=cGrupoCia
            AND
                 COMP.COD_LOCAL=cCodLocal      
            AND
                 COMP.NUM_PED_VTA=cNumPedidoVta  
            AND 
                 COMP.SEC_COMP_PAGO=cSecCompPago
            AND
                COMP.TIP_COMP_PAGO=cTipoDocumento
            AND 
             COMP.TOTAL_DESC_E!= 0
                                 ) LOOP
                                 v_vDOC := v_vDOC || REG.VALOR;
         END LOOP;
        EXCEPTION
            WHEN OTHERS THEN
                NULL;
        END;
        RETURN v_vDOC;
    
    END FN_OBTENER_DOC;
    
    -- Author  : LTAVARA
    -- Created : 14/07/2014 06:50:35 p.m.
    -- Proposito : Obtener la leyenda del comprobante electrónico
    FUNCTION FN_OBTENER_NOTAS(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2) 
        RETURN VARCHAR2 AS
        
        v_vDN VARCHAR2(3276) := NULL;
        
    BEGIN
        BEGIN
            SELECT 'DN|1|1000|' ||
                   UPPER(TRIM(FARMA_UTILITY.LETRAS(COMP.VAL_TOTAL_E + COMP.TOTAL_IGV_E)))|| ' ' ||   
                   DECODE(COMP.COD_TIP_MONEDA,'01','NUEVOS SOLES','02','DOLARES',NULL)|| CHR(13)-- Tipo de Moneda
              INTO v_vDN
              FROM VTA_COMP_PAGO COMP
             WHERE 
                   COMP.COD_GRUPO_CIA=cGrupoCia
              AND
                   COMP.COD_LOCAL=cCodLocal      
              AND
                   COMP.NUM_PED_VTA=cNumPedidoVta  
              AND 
                   COMP.SEC_COMP_PAGO=cSecCompPago
              AND
                   COMP.TIP_COMP_PAGO=cTipoDocumento;
        EXCEPTION
            WHEN OTHERS THEN
                NULL;
        END;
        RETURN v_vDN;
    END FN_OBTENER_NOTAS;


    -- Author  : LTAVARA
    -- Created : 14/07/2014 06:50:35 p.m.
    -- Proposito : Obtener los datos del detalle del comprobante electrónico
    FUNCTION FN_OBTENER_DETALLE(
                                 cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoClienteConvenio VARCHAR2) 
        RETURN VARCHAR2 AS
        
        v_vDETALLE VARCHAR2(32767) := NULL;
        
    BEGIN
        BEGIN
        for REG in (
          SELECT      
          --DETALLE DE DOCUMENTO
        'DE|'||-- LINEA DE DETALLE
        DET.SEC_PED_VTA_DET||'|'||-- Correlativo de Línea de Detalle
        TRIM(TO_CHAR(NVL(DET.VAL_PREC_VTA_UNIT_E,0),'999,999,990.00'))||'|'|| -- Precio de venta unitario por item
        'NIU'||'|'||-- Unidad de Medida
        TRIM(TO_CHAR(NVL(DET.CANT_UNID_VDD_E,0),'999,999,990.00'))||'|'|| -- Cantidad de unidades vendidas pot item (Q)
        TRIM(TO_CHAR(NVL(DET.VAL_VTA_ITEM_E,0),'999,999,990.00'))||'|'||-- Monto del Item
        DET.COD_PROD||'|'||-- Codigo de Producto
        DET.COD_TIP_PREC_VTA_E||'|'||-- Tipo de Precio de Venta 
        TRIM(TO_CHAR(NVL(DET.VAL_VTA_UNIT_ITEM_E,0),'999,999,990.00'))||'|'||-- Valor de venta unitario por ítem
        TRIM(TO_CHAR(NVL(DET.VAL_VTA_ITEM_E,0),'999,999,990.00'))|| CHR(13) || -- Valor de venta por item
          --DESCRIPCION DEL ITEM
        'DEDI|'|| -- LINEA DE DESCRIPCION
        REPLACE(REPLACE((PROD.DESC_PROD||' '|| DET.CANT_ATENDIDA||'/'||DET.CANT_FRAC_LOCAL), CHR(10), '@@'), CHR(13), '@@') || CHR(13) ||--Descripcion
         -- DETALLE DEIM
        'DEIM|'||-- LINEA DE IMPUESTOS
        TRIM(TO_CHAR(NVL(DET.VAL_TOTAL_IGV_ITEM_E,0),'999,999,990.00'))||'|'||-- 1 Importe total de un tributo para este item
        TRIM(TO_CHAR(NVL(DET.VAL_VTA_ITEM_E,0),'999,999,990.00'))||'|'||-- 2 Base Imponible (IGV, IVAP, Otros = Q x VU - Descuentos + ISC  ) 
        TRIM(TO_CHAR(NVL(DET.VAL_TOTAL_IGV_ITEM_E,0),'999,999,990.00'))||'|'||-- 3 Importe explícito a tributar ( = Tasa Porcentaje * Base Imponible)
        TRIM(TO_CHAR(NVL(DET.VAL_IGV,0),'00.00'))||'|'||-- 4 Tasa Impuesto
        DECODE(DET.COD_TIP_AFEC_IGV_E,10,1000)||'|'||-- 5 Tipo de Impuesto
        DET.COD_TIP_AFEC_IGV_E||'|'|| -- 6 Afectación del IGV
         NULL || '|' || -- 7 Sistema de ISC
        DECODE(DET.COD_TIP_AFEC_IGV_E,10,1000,0)||'|'||-- 8 Identificación del tributo
        DECODE(DET.COD_TIP_AFEC_IGV_E,10,'IGV',0)||'|'||-- 9 Nombre del Tributo
        DECODE(DET.COD_TIP_AFEC_IGV_E,10,'VAT',0) AS VALOR-- 10 Código del Tipo de Tributo
        FROM VTA_PEDIDO_VTA_DET DET, LGT_PROD PROD
        WHERE  
                 DET.COD_PROD=PROD.COD_PROD
            AND
                 DET.COD_GRUPO_CIA=cGrupoCia
            AND
                 DET.COD_LOCAL=cCodLocal      
            AND
                 DET.NUM_PED_VTA=cNumPedidoVta
            AND 
                  (CASE  
                   WHEN  cTipoClienteConvenio ='1' THEN
                         DET.SEC_COMP_PAGO_BENEF
                   WHEN  cTipoClienteConvenio ='2' THEN
                         DET.SEC_COMP_PAGO_EMPRE
                   ELSE
                         DET.SEC_COMP_PAGO
                   END ) =cSecCompPago
             ) LOOP
            v_vDETALLE := v_vDETALLE || REG.VALOR || CHR(13);
            
        END LOOP;
       END;
         RETURN v_vDETALLE;
    
    END FN_OBTENER_DETALLE;
    
    
      -- Author  : LTAVARA
    -- Created : 05/08/2014 03:20:35 p.m.
    -- Proposito : Obtener los datos del referencia
    FUNCTION FN_OBTENER_REFERENCIA(
                                 cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2) 
        RETURN VARCHAR2 AS
        
        v_vREFERENCIA VARCHAR2(32767) := NULL;
        
    BEGIN
        BEGIN
        
          SELECT      
          --REFERENCIA POR DOCUMENTO
        'RE|'||-- LINEA DE REFERENCIA
        PAGO.NUM_COMP_PAGO_EREF||'|'||-- 1 Serie y número del documento que modifica (Factura)
        ''||'|'||-- 2 Fecha de emisión
        PAGO.COD_TIP_COMP_PAGO_EREF||'|'||-- 3 Tipo de documento del documento que modifica (Factura)
        DECODE(PAGO.COD_TIP_COMP_PAGO_EREF,NULL,' ','381')||'|'||-- 4 Descripción  del tipo de Documento  UN 1001-Document Name
       (SELECT PAGOAUX.NUM_COMP_PAGO||'|'|| -- 5 En el caso de Guías de Remisión Número de guía: serie - número de documento
        '383'-- 6 En el caso de Guías de Remisión Tipo de Documento
          FROM VTA_COMP_PAGO PAGOAUX 
          WHERE 
               PAGOAUX.COD_GRUPO_CIA=PAGO.COD_GRUPO_CIA
          AND  PAGOAUX.COD_LOCAL=PAGO.COD_LOCAL      
          AND  PAGOAUX.NUM_PED_VTA=PAGO.NUM_PED_VTA
          AND  PAGOAUX.NUM_COMP_PAGO=PAGO.NUM_COMP_COPAGO_REF
          AND  PAGOAUX.TIP_COMP_PAGO='03')||'|'||
          ''||'|'||-- 7 En el caso de otros tipos de Documentos Número de documento relacionado
          ''||'|'||-- 8 En el caso de otros Tipo de Documento (no factura no guia de remisión)
          CHR(13)
        INTO  v_vREFERENCIA
        FROM VTA_COMP_PAGO PAGO
        WHERE  
               PAGO.COD_GRUPO_CIA=cGrupoCia
          AND
               PAGO.COD_LOCAL=cCodLocal      
          AND
               PAGO.NUM_PED_VTA=cNumPedidoVta  
          AND 
               PAGO.SEC_COMP_PAGO=cSecCompPago
          AND
               PAGO.TIP_COMP_PAGO=cTipoDocumento;

       END;
         RETURN v_vREFERENCIA;
    
    END FN_OBTENER_REFERENCIA;

    
    -- Author  : LTAVARA
    -- Created : 14/07/2014 06:50:35 p.m.
    -- Proposito : Obtener los datos del mensaje antes del timbre del comprobante electrónico
    FUNCTION FN_OBTENER_MENSAJE_ANTES(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                 cTipoDocumento VARCHAR2,
                                   cTipoClienteConvenio VARCHAR2) 
        RETURN VARCHAR2 AS
        v_vPES_AT                  VARCHAR2(1000) :='PES|MensajesAt'|| CHR(13);
        v_vDATO_COMP_PAGO          VARCHAR2(1000) := NULL;
        v_vCOD_CONVENIO            CHAR(10);
        v_vDATOS_ADICIONALES       VARCHAR(555) := NULL;
        v_vCONTADOR                NUMBER(10):=1;
        v_vDES_CONVENIO            VARCHAR(200) := NULL;
        v_vCOD_TIPO_CONVENIO       NUMBER(1);
        
    BEGIN           
     
        IF cTipoClienteConvenio= '1' or cTipoClienteConvenio= '2' THEN 
          
             --OBTENER CODIGO DE CONVENIO 
             SELECT PEDIDO_C.COD_CONVENIO
                    INTO v_vCOD_CONVENIO
             FROM VTA_PEDIDO_VTA_CAB PEDIDO_C
             WHERE
                  PEDIDO_C.COD_GRUPO_CIA=cGrupoCia
             AND PEDIDO_C.COD_LOCAL=cCodLocal
             AND PEDIDO_C.NUM_PED_VTA=cNumPedidoVta;   
             
             
             -- OBTENER DESCRIPCION DEL CONVENIO
             
              SELECT 'PESD|'||v_vCONTADOR||'|Convenio: '||CONV.DES_CONVENIO|| CHR(13), CONV.COD_TIPO_CONVENIO
                    INTO v_vDES_CONVENIO, v_vCOD_TIPO_CONVENIO
             FROM MAE_CONVENIO CONV
             WHERE CONV.COD_CONVENIO=v_vCOD_CONVENIO;
             -- CONCATENAR LOS DATOS
             v_vPES_AT := v_vPES_AT || v_vDES_CONVENIO;

     ---- DATOS ADICIONALES
        for REG in (
     
                select 'PESD|'||v_vCONTADOR||'|'||BEN.NOMBRE_CAMPO||': '||BEN.DESCRIPCION_CAMPO || CHR(13) AS VALOR

                 from con_btl_mf_ped_vta  BEN
                WHERE
                      BEN.COD_GRUPO_CIA=cGrupoCia
                  AND BEN.COD_LOCAL=cCodLocal
                  AND BEN.NUM_PED_VTA=cNumPedidoVta
                  AND BEN.COD_CONVENIO=v_vCOD_CONVENIO
                    ) LOOP
            v_vDATOS_ADICIONALES := v_vDATOS_ADICIONALES || REG.VALOR;
             
            v_vCONTADOR := v_vCONTADOR +1;
            
            END LOOP;
            
            ----PARA EMPRESA
       IF cTipoClienteConvenio= '2' THEN 
         SELECT 'PESD|'||v_vCONTADOR||'|Venta Total: S/.'||TRIM(TO_CHAR(NVL(COMP.VAL_BRUTO_COMP_PAGO,0),'999,999,990.00'))|| CHR(13) ||
         'PESD|'||v_vCONTADOR||'|INSTITUCION-'||COMP.PCT_EMPRESA||'%'|| CHR(13) ||
         'PESD|'||v_vCONTADOR||'|DOC. REF.- '||COMP.PCT_BENEFICIARIO||'%: '||
         (SELECT NVL(COMPE.NUM_COMP_PAGO_E,0)
         FROM VTA_COMP_PAGO COMPE WHERE 
            COMPE.COD_GRUPO_CIA=COMP.COD_GRUPO_CIA
         AND COMPE.COD_LOCAL=COMP.COD_LOCAL
         AND COMPE.NUM_PED_VTA=COMP.NUM_PED_VTA
         AND COMPE.NUM_COMP_PAGO=COMP.NUM_COMP_COPAGO_REF )||'- S/.'||TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO,0),'999,999,990.00'))|| CHR(13) 
            INTO v_vDATO_COMP_PAGO
         FROM VTA_COMP_PAGO  COMP 
         WHERE
              COMP.COD_GRUPO_CIA=cGrupoCia
          AND COMP.COD_LOCAL=cCodLocal
          AND COMP.NUM_PED_VTA=cNumPedidoVta
          AND COMP.SEC_COMP_PAGO=cSecCompPago
          AND COMP.TIP_COMP_PAGO=cTipoDocumento;

            ELSE
            ----PARA BENEFICIARIO
              IF v_vCOD_TIPO_CONVENIO != 3 THEN
                 SELECT 
                 'PESD|'||v_vCONTADOR||'|BENEFICIARIO -'||COMP.PCT_BENEFICIARIO||'%'|| CHR(13) ||
                 'PESD|'|| v_vCONTADOR ||'|DOC. REF. - '||COMP.PCT_EMPRESA||'%: '||
                 (SELECT NVL(COMPE.NUM_COMP_PAGO_E,0)
                 FROM VTA_COMP_PAGO COMPE WHERE 
                    COMPE.COD_GRUPO_CIA=COMP.COD_GRUPO_CIA
                 AND COMPE.COD_LOCAL=COMP.COD_LOCAL
                 AND COMPE.NUM_PED_VTA=COMP.NUM_PED_VTA
                 AND COMPE.NUM_COMP_PAGO=COMP.NUM_COMP_COPAGO_REF )||'- S/.'||TRIM(TO_CHAR(NVL(COMP.VAL_COPAGO_COMP_PAGO,0),'999,999,990.00'))|| CHR(13) 
                   INTO v_vDATO_COMP_PAGO
                 FROM VTA_COMP_PAGO  COMP 
                 WHERE
                      COMP.COD_GRUPO_CIA=cGrupoCia
                  AND COMP.COD_LOCAL=cCodLocal
                  AND COMP.NUM_PED_VTA=cNumPedidoVta
                  AND COMP.SEC_COMP_PAGO=cSecCompPago
                  AND COMP.TIP_COMP_PAGO=cTipoDocumento;
                  
               ELSE 
                 -- SOLO SE EMITE UN DOCUMENTO PARA EL BENEFICIARIO
                 v_vDATO_COMP_PAGO :=  v_vDATO_COMP_PAGO || 'PESD|'||v_vCONTADOR||'|BENEFICIARIO - 100 %'|| CHR(13);
              
               END IF;
             END IF;
              v_vPES_AT := v_vPES_AT || v_vDATOS_ADICIONALES || v_vDATO_COMP_PAGO;
          END IF;
          
              v_vPES_AT := v_vPES_AT || 'PESD|'||v_vCONTADOR||'|Guarda tu voucher,'|| CHR(13);
              v_vPES_AT := v_vPES_AT || 'PESD|'||v_vCONTADOR||'|Es el sustento para validar tu compra.'|| CHR(13);
   
        
          RETURN v_vPES_AT;
  
    END FN_OBTENER_MENSAJE_ANTES;    
    
    -- Author  : LTAVARA
    -- Created : 14/07/2014 06:50:35 p.m.
    -- Proposito : Obtener los datos del mensaje depues del timbre del comprobante electrónico
    FUNCTION FN_OBTENER_MENSAJE_DESPUES
        RETURN VARCHAR2 AS
        v_vPES_DT  VARCHAR2(1000) :='PES|MensajesDt'|| CHR(13);
       
        
    BEGIN           
       
     v_vPES_DT := v_vPES_DT || 'PESD|1|No se aceptan devoluciones de dinero.'|| CHR(13);
     v_vPES_DT := v_vPES_DT || 'PESD|2|Cambio de mercaderia unicamente dentro'|| CHR(13);
     v_vPES_DT := v_vPES_DT || 'PESD|3|de las 48 horas siguientes a la compra.'|| CHR(13);
     v_vPES_DT := v_vPES_DT || 'PESD|4|Indispensable presentar comprobante.'|| CHR(13);
     v_vPES_DT := v_vPES_DT || 'PESD|5|DELIVERY 612-5000 LAS 24 HORAS'|| CHR(13);

        
          RETURN v_vPES_DT;
  
    END FN_OBTENER_MENSAJE_DESPUES;    
   



    -- Author  : LTAVARA
    -- Created : 14/07/2014 06:50:35 p.m.
    -- Proposito : Obtener la estructura del comprobante electrónico
    FUNCTION FN_GENERAR_DOCUMENTO_ELEC(
                                 cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoDocumento VARCHAR2,
                                  cTipoClienteConvenio VARCHAR2
                                  ) 
        RETURN VARCHAR2 AS
        
        v_vTrama VARCHAR2(5555) := NULL;
        
    BEGIN
        BEGIN
              v_vTrama := v_vTrama || FN_OBTENER_CABECERA(cGrupoCia, cCodLocal,cNumPedidoVta, cSecCompPago,cTipoDocumento);
              v_vTrama := v_vTrama || FN_OBTENER_DOC(cGrupoCia, cCodLocal,cNumPedidoVta, cSecCompPago,cTipoDocumento);
              v_vTrama := v_vTrama || FN_OBTENER_NOTAS(cGrupoCia, cCodLocal,cNumPedidoVta, cSecCompPago,cTipoDocumento);
              v_vTrama := v_vTrama || FN_OBTENER_DETALLE(cGrupoCia, cCodLocal,cNumPedidoVta, cSecCompPago,cTipoClienteConvenio);           
              IF cTipoDocumento='04' THEN v_vTrama := v_vTrama || FN_OBTENER_REFERENCIA(cGrupoCia, cCodLocal,cNumPedidoVta, cSecCompPago,cTipoDocumento); END IF ;--SOLO NC
              v_vTrama := v_vTrama || FN_OBTENER_MENSAJE_ANTES(cGrupoCia, cCodLocal,cNumPedidoVta, cSecCompPago,cTipoDocumento,cTipoClienteConvenio) ;
              v_vTrama := v_vTrama ||  FN_OBTENER_MENSAJE_DESPUES;
        DBMS_OUTPUT.PUT_LINE(v_vTrama);
        EXCEPTION
        --WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20015, 'El pedido no existe. ' || cNumPedidoVta);
        WHEN OTHERS THEN
                NULL;
        END;
        RETURN v_vTrama;
    END FN_GENERAR_DOCUMENTO_ELEC;

    -- Author  : LTAVARA
    -- Created : 23/07/2014 06:50:35 p.m.
    -- Proposito : Inserta la trama se que envia a PPL

    FUNCTION FN_INSERTAR_TRAMA(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoTrama VARCHAR2,
                                  cTrama VARCHAR2) 
        RETURN NUMBER AS
        
        v_vRespuesta number := 0;
        
    BEGIN
        BEGIN

               INSERT INTO PTOVENTA.TRAMA_PPL (COD_TRAMA,COD_GRUPO_CIA,COD_LOCAL,
                                         NUM_PED_VTA,SEC_COMP_PAGO, 
                                         TIP_TRAMA, TRAMA_IN,FECHA_IN)
                VALUES (SECUENCIA_TRAMA.NEXTVAL, cGrupoCia,cCodLocal,
                        cNumPedidoVta,cSecCompPago,
                        cTipoTrama,cTrama, sysdate);  
                         
                        
               SELECT MAX(COD_TRAMA) 
               INTO v_vRespuesta
               FROM  PTOVENTA.TRAMA_PPL;
        
        EXCEPTION
            WHEN OTHERS THEN
                v_vRespuesta:= 0;
        END;
        RETURN v_vRespuesta;
    END FN_INSERTAR_TRAMA;
    
    -- Author  : LTAVARA
    -- Created : 23/07/2014 06:50:35 p.m.
    -- Proposito : Modificar la trama para insertar la respuesta de PPL
FUNCTION FN_MODIFICAR_TRAMA(
                                  cGrupoCia VARCHAR2,
                                  cCodLocal VARCHAR2,
                                  cNumPedidoVta VARCHAR2,
                                  cSecCompPago VARCHAR2,
                                  cTipoTrama VARCHAR2,
                                  cTrama VARCHAR2,
                                  cCodTrama VARCHAR2) 
        RETURN NUMBER AS
        
        v_vRespuesta number := 1;--exito
        
    BEGIN
        BEGIN                        
                        
                 UPDATE PTOVENTA.TRAMA_PPL 
                        SET TRAMA_OUT= cTrama,
                            FECHA_OUT= sysdate 
                        WHERE COD_GRUPO_CIA=cGrupoCia 
                        AND COD_LOCAL=cCodLocal 
                        AND NUM_PED_VTA=cNumPedidoVta 
                        AND SEC_COMP_PAGO= cSecCompPago 
                        AND TIP_TRAMA=cTipoTrama
                        AND COD_TRAMA=cCodTrama;   
        
        EXCEPTION
            WHEN OTHERS THEN
                v_vRespuesta:= 0;
        END;
        RETURN v_vRespuesta;
    END FN_MODIFICAR_TRAMA;

       


    -- Author  : LTAVARA
    -- Created : 25/07/2014 06:50:35 p.m.
    -- Proposito : Actualiza los campos nuevos de las tablas comprobante de pago y pedido para el comprobante electrónico
 
 procedure sp_upd_comp_pago_e(p_cod_grupo_cia      varchar2,
                                                       p_cod_local          varchar2,
                                                       p_num_ped_vta        varchar2,
                                                       p_sec_com_pago       varchar2,
                                                       p_tip_clien_convenio varchar2 default null
                                                       ) is
  begin

      update vta_pedido_vta_det
         set val_prec_vta_unit_e = decode(cod_tip_afec_igv_e, '31', val_prec_public, (((val_prec_total + ahorro) / cant_atendida) * val_frac)),
             val_vta_unit_item_e = trunc(decode(cod_tip_afec_igv_e, '31', val_prec_public, (((val_prec_total + ahorro) / cant_atendida) * val_frac)) / 
                                         decode(val_igv, 0, 1, (1 + (val_igv / 100))), 2),
             cant_unid_vdd_e = trunc((cant_atendida / val_frac), 2),
             val_vta_item_e = trunc(val_prec_total / (1 + (val_igv / 100)), 2),
             val_total_igv_item_e = trunc(trunc(val_prec_total / (1 + (val_igv / 100)), 2) * (val_igv / 100), 2),
             val_total_desc_item_e = trunc((ahorro / (1 + (val_igv / 100))), 2)
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
                     sum(decode(cod_tip_afec_igv_e, '31', trunc(val_vta_unit_item_e * cant_unid_vdd_e, 2), 0)) total_gratu_e,
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

      update vta_comp_pago
         set val_total_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * val_total_e,
             total_grav_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_grav_e,
             total_inaf_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_inaf_e,
             total_gratu_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_gratu_e,
             total_exon_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_exon_e,
             total_desc_e = (decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_desc_e) + nvl((val_copago_comp_pago/(1+(porc_igv_comp_pago/100))), 0),
             total_igv_e = decode(tip_clien_convenio, '1', decode(ind_comp_credito, 'S', 1, (pct_beneficiario/100)), '2', (pct_empresa/100), 1) * total_igv_e
       where cod_grupo_cia = p_cod_grupo_cia
         and cod_local = p_cod_local
         and num_ped_vta = p_num_ped_vta
         and sec_comp_pago =p_sec_com_pago;

  end sp_upd_comp_pago_e;
    
END PKG_CONEXION_POS;

/
