CREATE OR REPLACE PACKAGE PTOVENTA_IMPRESION_MATRICIAL is

  TYPE FARMACURSOR IS REF CURSOR;
  
  FUNCTION F_GET_COMPROBANTE_PAGO(cCodGrupoCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in       IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                                  cNumPedVta_in      IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                  cSecCompPago_in    IN VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE)
  RETURN FARMACURSOR;
    
  FUNCTION F_GET_BOLETA_ARCANGEL(cCodGrupoCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                                 cCodLocal_in       IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                                 cNumPedVta_in      IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                 cSecCompPago_in    IN VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE)
  RETURN FARMACURSOR;
  
  FUNCTION F_GET_FACTURA_ARCANGEL(cCodGrupoCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in       IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                                  cNumPedVta_in      IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                  cSecCompPago_in    IN VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE)
  RETURN FARMACURSOR;
  
  FUNCTION F_GET_GUIA_ARCANGEL(cCodGrupoCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                               cCodLocal_in       IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                               cNumPedVta_in      IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                               cSecCompPago_in    IN VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE)
  RETURN FARMACURSOR;
  
END PTOVENTA_IMPRESION_MATRICIAL;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA_IMPRESION_MATRICIAL is

  FUNCTION F_GET_COMPROBANTE_PAGO(cCodGrupoCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in       IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                                  cNumPedVta_in      IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                  cSecCompPago_in    IN VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE)
    RETURN FARMACURSOR IS
    vCodCia PBL_LOCAL.COD_CIA%TYPE;
    vTipDocumento VTA_COMP_PAGO.TIP_COMP_PAGO%TYPE;
  BEGIN
    SELECT L.COD_CIA
    INTO vCodCia
    FROM PBL_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
    AND L.COD_LOCAL = cCodLocal_in;
    
    SELECT TIP_COMP_PAGO
    INTO vTipDocumento
    FROM VTA_COMP_PAGO 
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_LOCAL = cCodLocal_in
    AND NUM_PED_VTA = cNumPedVta_in
    AND SEC_COMP_PAGO = cSecCompPago_in;
    
    IF vTipDocumento = '01' AND vCodCia = '007' THEN
      RETURN F_GET_BOLETA_ARCANGEL(cCodGrupoCia_in => cCodGrupoCia_in,
                                   cCodLocal_in => cCodLocal_in,
                                   cNumPedVta_in => cNumPedVta_in,
                                   cSecCompPago_in => cSecCompPago_in);
    ELSIF vTipDocumento = '02' AND vCodCia = '007' THEN
      RETURN F_GET_FACTURA_ARCANGEL(cCodGrupoCia_in => cCodGrupoCia_in,
                                   cCodLocal_in => cCodLocal_in,
                                   cNumPedVta_in => cNumPedVta_in,
                                   cSecCompPago_in => cSecCompPago_in);
    ELSIF vTipDocumento = '03' AND vCodCia = '007' THEN
      RETURN F_GET_GUIA_ARCANGEL(cCodGrupoCia_in => cCodGrupoCia_in,
                                 cCodLocal_in => cCodLocal_in,
                                 cNumPedVta_in => cNumPedVta_in,
                                 cSecCompPago_in => cSecCompPago_in);
    
    END IF;
    RETURN NULL;
  END;
  
  FUNCTION F_GET_BOLETA_ARCANGEL(cCodGrupoCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                                 cCodLocal_in       IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                                 cNumPedVta_in      IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                 cSecCompPago_in    IN VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE)
    RETURN FARMACURSOR IS
    
    CURSOR curCabecera IS
      SELECT CABECERA.VAL VALOR
      FROM (
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL --, '9','I','N','N'
                FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                   REPLACE( REPLACE((REPLACE(( 
        SELECT ' ' ||'@@'||
               ' ' ||'@@'||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 6)||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(CP.NOM_IMPR_COMP,62) ||'@@' ||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 6)||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(CP.DIREC_IMPR_COMP,42)||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 31)||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(CP.USU_CREA_COMP_PAGO,16) ||'@@' ||
               
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 1)||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(CP.NUM_DOC_IMPR,29)||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 6)||
               FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(TO_CHAR(CAB.FEC_PED_VTA,'DD/MM/YYYY'),38)
        FROM VTA_PEDIDO_VTA_CAB CAB,
             VTA_COMP_PAGO CP
        WHERE CAB.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
        AND CAB.COD_LOCAL = CP.COD_LOCAL
        AND CAB.NUM_PED_VTA = CP.NUM_PED_VTA
        AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
        AND CP.COD_LOCAL = cCodLocal_in
        AND CP.NUM_PED_VTA = cNumPedVta_in
        AND CP.SEC_COMP_PAGO = cSecCompPago_in

        ),'&','Ã')),'<','Ë'),'@@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt
      ) CABECERA ;
    filaCabecera curCabecera%ROWTYPE;
    
    CURSOR curDetalle IS
      SELECT
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.CANTIDAD, 10, pAlineacion_in => 'C')||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(TRIM(SUBSTR(X.PRODUCTO,0,(58-(LENGTH(X.UNID_VTA)+LENGTH(X.LAB)))))||X.UNID_VTA||X.LAB,58)||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.PREC_UNIT, 13, pAlineacion_in => 'D')||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.PREC_TOTAL, 13, pAlineacion_in => 'D') VALOR
      FROM (
          SELECT 
                 CASE 
                   WHEN DET.VAL_FRAC = 1 THEN DET.CANT_ATENDIDA||''
                   ELSE DET.CANT_ATENDIDA||'/'|| DET.VAL_FRAC
                 END CANTIDAD,
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL((' '||SUBSTR(TRIM(PROD.DESC_PROD),0,27)),27) PRODUCTO, 
                 ('/'||SUBSTR(TRIM(DET.UNID_VTA),0,14)) UNID_VTA, 
                 ('/'||SUBSTR(TRIM(LA.NOM_LAB),0,14)) LAB, 
                 TRIM(TO_CHAR((DET.VAL_PREC_TOTAL / DET.CANT_ATENDIDA),'999,990.00')) PREC_UNIT,
                 TRIM(TO_CHAR(DET.VAL_PREC_TOTAL, '999,990.00')) PREC_TOTAL
            FROM VTA_PEDIDO_VTA_DET DET, 
                 VTA_COMP_PAGO CP,
                 LGT_PROD PROD, 
                 LGT_LAB LA
           WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND CP.COD_LOCAL  = cCodLocal_in
           AND CP.NUM_PED_VTA = cNumPedVta_in
           AND CP.SEC_COMP_PAGO = cSecCompPago_in
           AND DET.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
           AND DET.COD_LOCAL = CP.COD_LOCAL
           AND DET.NUM_PED_VTA = CP.NUM_PED_VTA
             AND (CASE
                   WHEN CP.TIP_CLIEN_CONVENIO = '1' THEN
                    DET.SEC_COMP_PAGO_BENEF
                   WHEN CP.TIP_CLIEN_CONVENIO = '2' THEN
                    DET.SEC_COMP_PAGO_EMPRE
                   ELSE
                    DET.SEC_COMP_PAGO
                 END) = CP.SEC_COMP_PAGO
             AND DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
             AND DET.COD_PROD = PROD.COD_PROD
             AND PROD.COD_LAB = LA.COD_LAB
           ORDER BY DET.SEC_PED_VTA_DET ASC
        ) X;
    filaDetalle curDetalle%ROWTYPE;
    
    vIpPc  IMPRESION_TERMICA.IP_PC%TYPE;
    vIdDoc IMPRESION_TERMICA.ID_DOC%TYPE;
    
    vTipoClienteConvenio VTA_COMP_PAGO.TIP_CLIEN_CONVENIO%TYPE;
    vContDetalle NUMBER;
    vTotalAhorro VTA_PEDIDO_VTA_DET.AHORRO%TYPE;
    vMsjAhorro VARCHAR2(200);
    vMtoPercepcion VTA_COMP_PAGO.VAL_PERCEP_COMP_PAGO%TYPE;
    vMtoRedondeoPercep VTA_COMP_PAGO.VAL_REDONDEO_PERCEPCION%TYPE;
    vPctPercepcion VTA_COMP_PAGO.VAL_PCT_PERCEPCION%TYPE;
    vNetoComprobante VTA_COMP_PAGO.VAL_NETO_COMP_PAGO%TYPE;
    vDscto VTA_COMP_PAGO.VAL_DCTO_COMP_PAGO%TYPE;
    vLineaTotales VARCHAR2(150);
  BEGIN
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vContDetalle := 0;
    
    OPEN curCabecera;
    LOOP
      FETCH curCabecera INTO filaCabecera;
      EXIT WHEN curCabecera%NOTFOUND;
        FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => filaCabecera.VALOR);
    END LOOP;
    CLOSE curCabecera;
    FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                    vIpPc_in => vIpPc, 
                                    vValor_in => ' ');
    
    OPEN curDetalle;
    LOOP
      FETCH curDetalle INTO filaDetalle;
      EXIT WHEN curDetalle%NOTFOUND;
        FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => filaDetalle.VALOR);
        vContDetalle := vContDetalle + 1;
    END LOOP;
    CLOSE curDetalle;
    
    WHILE vContDetalle < 10 LOOP
      FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => ' ');
      vContDetalle := vContDetalle + 1;
    END LOOP;
    
    SELECT SUM(NVL(DET.AHORRO,0)), 
           NVL(CP.VAL_PERCEP_COMP_PAGO,0), 
           NVL(CP.VAL_REDONDEO_PERCEPCION,0),
           CP.VAL_NETO_COMP_PAGO,
           NVL(CP.VAL_DCTO_COMP_PAGO,0)
    INTO vTotalAhorro,
         vMtoPercepcion,
         vMtoRedondeoPercep,
         vNetoComprobante,
         vDscto
    FROM VTA_PEDIDO_VTA_DET DET,
         VTA_COMP_PAGO CP
    WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CP.COD_LOCAL = cCodLocal_in
    AND CP.NUM_PED_VTA = cNumPedVta_in
    AND CP.SEC_COMP_PAGO = cSecCompPago_in
    AND DET.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
    AND DET.COD_LOCAL = CP.COD_LOCAL
    AND DET.NUM_PED_VTA = CP.NUM_PED_VTA
    AND DET.NUM_PED_VTA = CP.NUM_PED_VTA
    AND (CASE
         WHEN CP.TIP_CLIEN_CONVENIO = '1' THEN
          DET.SEC_COMP_PAGO_BENEF
         WHEN CP.TIP_CLIEN_CONVENIO = '2' THEN
          DET.SEC_COMP_PAGO_EMPRE
         ELSE
          DET.SEC_COMP_PAGO
         END) = CP.SEC_COMP_PAGO
  GROUP BY CP.VAL_PERCEP_COMP_PAGO, CP.VAL_REDONDEO_PERCEPCION, CP.VAL_NETO_COMP_PAGO, CP.VAL_DCTO_COMP_PAGO;
         
    IF vTotalAhorro != 0 THEN
      vMsjAhorro := PTOVENTA_VTA.OBTIENE_MENSAJE_AHORRO(cCodGrupoCia_in, cCodLocal_in,'N');
      vMsjAhorro := vMsjAhorro||' '||TRIM(TO_CHAR(vTotalAhorro,'999,999,990.00'));
    ELSE
      vMsjAhorro := ' ';
    END IF;
    
    vMsjAhorro := FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(vMsjAhorro,43);
    
    IF vMtoPercepcion != 0 THEN
      vMsjAhorro := vMsjAhorro ||'PERCEPCION';
    ELSE
      vMsjAhorro := vMsjAhorro ||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ',10);
    END IF;
    
    FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                    vIpPc_in => vIpPc, 
                                    vValor_in => vMsjAhorro);
    
    vLineaTotales := FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ',41);
    
    IF vMtoPercepcion != 0 THEN
      vLineaTotales := vLineaTotales ||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(TRIM(TO_CHAR(vMtoPercepcion,'999,990.00')),13);
    ELSE
      vLineaTotales := vLineaTotales ||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ',13);
    END IF;
    
    vLineaTotales := vLineaTotales ||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ',14);
    
    vDscto := vDscto + vMtoRedondeoPercep;
    
    vLineaTotales := vLineaTotales ||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(TRIM(TO_CHAR(vDscto,'999,990.00')),13);
    vLineaTotales := vLineaTotales ||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(TRIM(TO_CHAR(vNetoComprobante,'999,990.00')),13);
    
    FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                    vIpPc_in => vIpPc, 
                                    vValor_in => vLineaTotales);
                                    
    RETURN FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                    vIpPc_in => vIpPc);
  END; 
  
  FUNCTION F_GET_FACTURA_ARCANGEL(cCodGrupoCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                                  cCodLocal_in       IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                                  cNumPedVta_in      IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                                  cSecCompPago_in    IN VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE)
    RETURN FARMACURSOR IS
    CURSOR curCabecera IS
      SELECT CABECERA.VAL VALOR
        FROM (
          SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL --, '9','I','N','N'
                  FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                     REPLACE( REPLACE((REPLACE(( 
            SELECT ' ' ||'@@'||
                   ' ' ||'@@'||
                   ' ' ||'@@'||
                   ' ' ||'@@'||
                   ' ' ||'@@'||
                   ' ' ||'@@'||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 12)||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(CP.NOM_IMPR_COMP,78) ||'@@' ||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 12)||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(CP.DIREC_IMPR_COMP,62)||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 46)||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(CP.USU_CREA_COMP_PAGO,17) ||'@@' ||
                           
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 12)||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(CP.NUM_DOC_IMPR,26)||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 7)||
                           FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(TO_CHAR(CAB.FEC_PED_VTA,'DD/MM/YYYY'),15)
                    FROM VTA_PEDIDO_VTA_CAB CAB,
                         VTA_COMP_PAGO CP
                    WHERE CAB.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                    AND CAB.COD_LOCAL = CP.COD_LOCAL
                    AND CAB.NUM_PED_VTA = CP.NUM_PED_VTA
                    AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
                    AND CP.COD_LOCAL = cCodLocal_in
                    AND CP.NUM_PED_VTA = cNumPedVta_in
                    AND CP.SEC_COMP_PAGO = cSecCompPago_in
            ),'&','Ã')),'<','Ë'),'@@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt
        ) CABECERA ;   
    
    CURSOR curDetalle IS
      SELECT
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 3) ||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.CANT, 10, pAlineacion_in => 'C')||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.FRAC, 8, pAlineacion_in => 'C')||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(TRIM(SUBSTR(X.PRODUCTO,0,(90-(LENGTH(X.UNID_VTA)+LENGTH(X.LAB)))))||X.UNID_VTA||X.LAB,90)||
        ' ' ||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.PREC_UNIT, 12, pAlineacion_in => 'D')||
        ' ' ||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.PREC_TOTAL, 12, pAlineacion_in => 'D') VALOR
      FROM (
          SELECT 
                 DET.CANT_ATENDIDA CANT,
                 CASE 
                   WHEN DET.VAL_FRAC = 1 THEN ' '
                   ELSE DET.VAL_FRAC||''
                 END FRAC,
                 PROD.DESC_PROD PRODUCTO, 
                 ('/'||SUBSTR(TRIM(DET.UNID_VTA),0,16)) UNID_VTA, 
                 ('/'||SUBSTR(TRIM(LA.NOM_LAB),0,16)) LAB, 
                 TRIM(TO_CHAR((DET.VAL_PREC_TOTAL / DET.CANT_ATENDIDA),'999,990.00')) PREC_UNIT,
                 TRIM(TO_CHAR(DET.VAL_PREC_TOTAL, '999,990.00')) PREC_TOTAL
            FROM VTA_PEDIDO_VTA_DET DET, 
                 VTA_COMP_PAGO CP,
                 LGT_PROD PROD, 
                 LGT_LAB LA
           WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND CP.COD_LOCAL  = cCodLocal_in
           AND CP.NUM_PED_VTA = cNumPedVta_in
           AND CP.SEC_COMP_PAGO = cSecCompPago_in
           AND DET.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
           AND DET.COD_LOCAL = CP.COD_LOCAL
           AND DET.NUM_PED_VTA = CP.NUM_PED_VTA
             AND (CASE
                   WHEN CP.TIP_CLIEN_CONVENIO = '1' THEN
                    DET.SEC_COMP_PAGO_BENEF
                   WHEN CP.TIP_CLIEN_CONVENIO = '2' THEN
                    DET.SEC_COMP_PAGO_EMPRE
                   ELSE
                    DET.SEC_COMP_PAGO
                 END) = CP.SEC_COMP_PAGO
             AND DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
             AND DET.COD_PROD = PROD.COD_PROD
             AND PROD.COD_LAB = LA.COD_LAB
           ORDER BY DET.SEC_PED_VTA_DET ASC
        ) X;   
    
    CURSOR curTotales IS
      
      SELECT 
        TOTALES.VAL VALOR
      FROM (
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL --, '9','I','N','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE(( 
          SELECT 
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 8) || COMP.MSJ_AHORRO ||'@@'||
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 8) || COMP.LETRAS ||'@@'||
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 47) || COMP.LBL_PERCEP ||'@@'||
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 47, pAlineacion_in => 'D') || 
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(COMP.TXT_PERCEP, 10, pAlineacion_in => 'D') ||
            ' '||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(COMP.VAL_BRUTO, 16, pAlineacion_in => 'D') ||
            ' '||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(COMP.VAL_DSCTO, 16, pAlineacion_in => 'D') ||
            ' '||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(COMP.VAL_AFECTO, 17, pAlineacion_in => 'D') ||
            ' '||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(COMP.VAL_IGV, 11, pAlineacion_in => 'D') ||
            ' '||FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(COMP.VAL_NETO, 12, pAlineacion_in => 'D') 
          FROM (
            SELECT        
              TRIM(UPPER(FARMA_UTILITY.LETRAS((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO)))||' NUEVOS SOLES.') LETRAS,
              CASE 
                WHEN NVL(CP.VAL_PERCEP_COMP_PAGO,0) != 0 THEN 'PERCEPCION'
                ELSE FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 10)
              END LBL_PERCEP,
              CASE 
                WHEN NVL(CP.VAL_PERCEP_COMP_PAGO,0) != 0 THEN TRIM(TO_CHAR(CP.VAL_PERCEP_COMP_PAGO,'999,990.00'))
                ELSE ' '
              END TXT_PERCEP,
              TRIM(TO_CHAR(CP.VAL_BRUTO_COMP_PAGO,'999,990.00')) VAL_BRUTO,
              TRIM(TO_CHAR(CP.VAL_DCTO_COMP_PAGO,'999,990.00')) VAL_DSCTO,
              TRIM(TO_CHAR(CP.VAL_AFECTO_COMP_PAGO,'999,990.00')) VAL_AFECTO,
              TRIM(TO_CHAR(CP.VAL_IGV_COMP_PAGO,'999,990.00')) VAL_IGV,
              TRIM(TO_CHAR(CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO,'999,990.00')) VAL_NETO,
              CASE 
                WHEN NVL(CP.VAL_DCTO_COMP,0) != 0 THEN
                  PTOVENTA_VTA.OBTIENE_MENSAJE_AHORRO(CP.COD_GRUPO_CIA, CP.COD_LOCAL,'N')||' S/.'||
                  TRIM(TO_CHAR(CP.VAL_DCTO_COMP,'999,990.00'))
                ELSE ' '
              END MSJ_AHORRO
            FROM VTA_COMP_PAGO CP
            WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
            AND CP.COD_LOCAL = cCodLocal_in
            AND CP.NUM_PED_VTA = cNumPedVta_in
            AND CP.SEC_COMP_PAGO = cSecCompPago_in
          ) COMP

        ),'&','Ã')),'<','Ë'),'@@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt
      ) TOTALES ;

    filaCabecera    curCabecera%ROWTYPE;
    filaDetalle     curDetalle%ROWTYPE;
    filaTotales     curTotales%ROWTYPE;
    vIpPc           IMPRESION_TERMICA.IP_PC%TYPE;
    vIdDoc          IMPRESION_TERMICA.ID_DOC%TYPE;
    vContDetalle    NUMBER;
    
    valNetoCP       VTA_COMP_PAGO.VAL_NETO_COMP_PAGO%TYPE;
    valIgvCP        VTA_COMP_PAGO.VAL_IGV_COMP_PAGO%TYPE;
    valPercepCP     VTA_COMP_PAGO.VAL_PERCEP_COMP_PAGO%TYPE;
    valRedPercp     VTA_COMP_PAGO.VAL_REDONDEO_PERCEPCION%TYPE;
    valDsctoCP      VTA_COMP_PAGO.VAL_DCTO_COMP_PAGO%TYPE;
    
  BEGIN
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vContDetalle := 0;
    
    OPEN curCabecera;
    LOOP
      FETCH curCabecera INTO filaCabecera;
      EXIT WHEN curCabecera%NOTFOUND;
        FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => filaCabecera.VALOR);
    END LOOP;
    CLOSE curCabecera;
    FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                    vIpPc_in => vIpPc, 
                                    vValor_in => ' ');
    FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                    vIpPc_in => vIpPc, 
                                    vValor_in => ' ');
    
    OPEN curDetalle;
    LOOP
      FETCH curDetalle INTO filaDetalle;
      EXIT WHEN curDetalle%NOTFOUND;
        FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => filaDetalle.VALOR);
        vContDetalle := vContDetalle + 1;
    END LOOP;
    CLOSE curDetalle;
    
    WHILE vContDetalle < 14 LOOP
      FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => ' ');
      vContDetalle := vContDetalle + 1;
    END LOOP;
    
    OPEN curTotales;
    LOOP
      FETCH curTotales INTO filaTotales;
      EXIT WHEN curTotales%NOTFOUND;
        FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => filaTotales.VALOR);
    END LOOP;
    CLOSE curTotales;
    
    RETURN FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                    vIpPc_in => vIpPc);
  END;
  
  FUNCTION F_GET_GUIA_ARCANGEL(cCodGrupoCia_in    IN VTA_COMP_PAGO.COD_GRUPO_CIA%TYPE,
                               cCodLocal_in       IN VTA_COMP_PAGO.COD_LOCAL%TYPE,
                               cNumPedVta_in      IN VTA_COMP_PAGO.NUM_PED_VTA%TYPE,
                               cSecCompPago_in    IN VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE)
    RETURN FARMACURSOR IS
    
    CURSOR curCabecera IS
      SELECT CABECERA.VAL VALOR
      FROM (
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL --, '9','I','N','N'
                FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' ||
                                   REPLACE( REPLACE((REPLACE(( 
        
          SELECT ' ' ||'@@'||
                 ' ' ||'@@'||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 17) || 
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.DIR_LOCAL, 73) ||'@@'||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 18) ||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.DIR_CLIENTE, 72) ||'@@'||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 17) ||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.NOM_CLIENTE, 73) ||'@@'||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 17) ||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.DOC_CLIENTE, 73) ||'@@'||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 23) ||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.FECHA, 60) ||'@@'||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 17) ||
                 FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.DOC_REFERENCIA, 60) 
          FROM (
            SELECT 
               SUBSTR(C.DIREC_LOCAL,0,73) DIR_LOCAL,
               SUBSTR(CP.DIREC_IMPR_COMP,0,72) DIR_CLIENTE,
               SUBSTR(CP.NOM_IMPR_COMP,0,73) NOM_CLIENTE,
               SUBSTR(CP.NUM_DOC_IMPR,0,73) DOC_CLIENTE,
               TO_CHAR(CP.FEC_CREA_COMP_PAGO, 'DD/MM/YYYY HH24:MI:SS') FECHA,
               NVL(( SELECT 'DOC.REF.: '||TCP.COD_TIPODOC||' '||DECODE(REF.COD_TIP_PROC_PAGO,'1', REF.NUM_COMP_PAGO_E, REF.NUM_COMP_PAGO)
                     FROM VTA_COMP_PAGO REF,
                          MAE_TIPO_COMP_PAGO_BTLMF TCP
                     WHERE REF.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                     AND REF.COD_LOCAL = CP.COD_LOCAL
                     AND REF.NUM_PED_VTA = CP.NUM_PED_VTA
                     AND REF.NUM_COMP_PAGO = CP.NUM_COMP_COPAGO_REF
                     AND REF.TIP_COMP_PAGO = TCP.TIP_COMP_PAGO),' ') DOC_REFERENCIA
            FROM VTA_PEDIDO_VTA_CAB CAB,
                 VTA_COMP_PAGO CP,
                 PBL_LOCAL C
            WHERE CAB.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
            AND CAB.COD_LOCAL = CP.COD_LOCAL
            AND CAB.NUM_PED_VTA = CP.NUM_PED_VTA
            AND CAB.COD_GRUPO_CIA = C.COD_GRUPO_CIA
            AND CAB.COD_LOCAL = C.COD_LOCAL
            AND CP.COD_GRUPO_CIA = cCodGrupoCia_in
            AND CP.COD_LOCAL = cCodLocal_in
            AND CP.NUM_PED_VTA = cNumPedVta_in
            AND CP.SEC_COMP_PAGO = cSecCompPago_in
          ) X
        ),'&','Ã')),'<','Ë'),'@@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt
      ) CABECERA ;
    
    CURSOR curDetalle IS
      SELECT
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.COD_PROD, 11, pAlineacion_in => 'C') || ' '||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.CANTIDAD, 10, pAlineacion_in => 'C') || ' '||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.DESCRIPCION,61) || ' ' ||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.UNID_VTA, 20) || ' ' ||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.PREC_UNIT, 15, pAlineacion_in => 'D') || ' ' ||
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(X.SUB_TOTAL, 15, pAlineacion_in => 'D') VALOR,
        MTO_SUB_TOTAL
      FROM (
          SELECT 
             DET.COD_PROD, 
             DECODE(MOD(DET.CANT_ATENDIDA, DET.VAL_FRAC),0,
                    (DET.CANT_ATENDIDA / DET.VAL_FRAC) || '',
                    DET.CANT_ATENDIDA ||DECODE(DET.VAL_FRAC, 1, '', '/' || DET.VAL_FRAC)) CANTIDAD,
             SUBSTR(PROD.DESC_PROD,0,61) DESCRIPCION, 
             SUBSTR(DECODE(MOD(DET.CANT_ATENDIDA, DET.VAL_FRAC),0,
                    PROD.DESC_UNID_PRESENT,
                    DET.UNID_VTA),0,20) UNID_VTA,
             CASE 
               WHEN C.FLG_IMPRIME_IMPORTES = '1' THEN
                 TRIM(TO_CHAR(round(
                   CASE
                     WHEN MOD(DET.CANT_ATENDIDA, DET.VAL_FRAC) = 0 THEN
                       (DET.val_prec_total * 100 / (DET.VAL_IGV + 100)) / (DET.CANT_ATENDIDA / DET.VAL_FRAC)
                     ELSE
                       DET.val_prec_vta * 100 / (DET.VAL_IGV + 100)
                   END,3),'999,990.00'))
               ELSE ' '
             END PREC_UNIT,
             CASE 
               WHEN C.FLG_IMPRIME_IMPORTES = '1' THEN
                 TRIM(TO_CHAR(
                   CASE
                     WHEN DET.VAL_IGV=0 THEN
                       ROUND(DET.VAL_PREC_TOTAL, 3)
                     ELSE
                       ROUND(DET.VAL_PREC_TOTAL * 100 / (DET.VAL_IGV + 100), 3)
                   END 
                 , '999,990.00')) 
               ELSE ' '
             END SUB_TOTAL,
             CASE
               WHEN DET.VAL_IGV=0 THEN
                 ROUND(DET.VAL_PREC_TOTAL, 3)
               ELSE
                 ROUND(DET.VAL_PREC_TOTAL * 100 / (DET.VAL_IGV + 100), 3)
             END MTO_SUB_TOTAL
            FROM VTA_PEDIDO_VTA_CAB CAB,
                 VTA_PEDIDO_VTA_DET DET, 
                 VTA_COMP_PAGO CP,
                 LGT_PROD PROD, 
                 LGT_LAB LA,
                 MAE_CONVENIO C
           WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
           AND CP.COD_LOCAL  = cCodLocal_in
           AND CP.NUM_PED_VTA = cNumPedVta_in
           AND CP.SEC_COMP_PAGO = cSecCompPago_in
           AND CAB.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
           AND CAB.COD_LOCAL = CP.COD_LOCAL
           AND CAB.NUM_PED_VTA = CP.NUM_PED_VTA
           AND CAB.COD_CONVENIO = C.COD_CONVENIO
           AND CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
           AND CAB.COD_LOCAL = DET.COD_LOCAL
           AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
           AND DET.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
           AND DET.COD_LOCAL = CP.COD_LOCAL
           AND DET.NUM_PED_VTA = CP.NUM_PED_VTA
           AND (CASE
                 WHEN CP.TIP_CLIEN_CONVENIO = '1' THEN
                  DET.SEC_COMP_PAGO_BENEF
                 WHEN CP.TIP_CLIEN_CONVENIO = '2' THEN
                  DET.SEC_COMP_PAGO_EMPRE
                 ELSE
                  DET.SEC_COMP_PAGO
               END) = CP.SEC_COMP_PAGO
             AND DET.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
             AND DET.COD_PROD = PROD.COD_PROD
             AND PROD.COD_LAB = LA.COD_LAB
           ORDER BY DET.SEC_PED_VTA_DET ASC
        ) X;
    
    CURSOR curDatosConvenio IS
      SELECT 
        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 12)||
        CONVENIO.VAL VALOR
      FROM (
        SELECT REPLACE((REPLACE((EXTRACTVALUE(xt.column_value, 'e')),'Ã','&')),'Ë','<') VAL --, '9','I','N','N'
        FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE( REPLACE((REPLACE(( 
        
          SELECT
            CASE 
              WHEN W.COASEGURO IS NOT NULL THEN
                FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(COASEGURO,120) || '@@'
              ELSE ''
            END ||
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(W.INSTITUCION,80)||NVL(W.SUB_TOTAL,'')|| '@@' ||
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(W.CONVENIO,80)||NVL(W.IGV,'')|| '@@' ||
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(W.BENEFICIARIO,80)||NVL(W.LINEA,'')|| 
            DECODE(W.DOC_REFERENCIA, NULL,'','@@')||
            FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(W.DOC_REFERENCIA,80)||NVL(W.TOTAL,'')
          FROM (
              SELECT
                    CASE 
                      WHEN DA.TIP_CONV IN ('1','3') AND DA.IMPR_IMPORT = '1' THEN
                        'SON: '||UPPER(TRIM(FARMA_UTILITY.LETRAS((DA.MTO_TOTAL_NETO_RED))))||
                        ' COASEGURO'||DA.PCT_EMPRESA||'S/. '||DA.VAL_COPAGO_S_IGV
                      ELSE NULL
                    END COASEGURO,
                    DA.INSTITUCION,
                    DA.CONVENIO,
                    DA.BENEFICIARIO,
                    CASE
                      WHEN DA.TIP_CONV IN ('1','3') THEN
                        DA.DOC_REF||DECODE(DA.IMPR_IMPORT,'1',DA.PCT_EMPRESA||' S/. '||DA.VAL_COPAGO,'')
                      ELSE NULL
                    END DOC_REFERENCIA,
                    CASE 
                      WHEN DA.TIP_CONV IN ('1','3') AND DA.IMPR_IMPORT = '1' THEN
                        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL('S/.', 14, pAlineacion_in => 'D')||
                        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(DA.VAL_CP_S_IGV,11,pAlineacion_in => 'D')
                      ELSE NULL
                    END SUB_TOTAL,
                    CASE 
                      WHEN DA.IMPR_IMPORT = '1' THEN
                        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL('IGV S/.', 14, pAlineacion_in => 'D')||
                        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(DA.VAL_IGV_CP,11,pAlineacion_in => 'D')
                      ELSE NULL
                    END IGV,
                    CASE 
                      WHEN DA.IMPR_IMPORT = '1' THEN
                        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL('------------', 25, pAlineacion_in => 'D')
                      ELSE NULL
                    END LINEA,
                    CASE 
                      WHEN DA.IMPR_IMPORT = '1' THEN
                        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL('TOTAL S/.', 14, pAlineacion_in => 'D')||
                        FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(DA.VAL_TOTAL_NETO_RED,11,pAlineacion_in => 'D')
                      ELSE NULL
                    END TOTAL
              FROM (
                SELECT 
                    M.FLG_IMPRIME_IMPORTES IMPR_IMPORT,
                    M.COD_TIPO_CONVENIO TIP_CONV,
                    CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO MTO_TOTAL_NETO_RED,
                    TRIM(TO_CHAR((CP.VAL_NETO_COMP_PAGO + CP.VAL_REDONDEO_COMP_PAGO),'999,990.00')) VAL_TOTAL_NETO_RED,
                    '('||CP.PCT_BENEFICIARIO||'%)' PCT_BENEFICIARIO,
                    DECODE(NVL(CP.PCT_EMPRESA,0),0,'',' ('||CP.PCT_EMPRESA||'%) ') PCT_EMPRESA,
                    TRIM(TO_CHAR((CP.VAL_COPAGO_COMP_PAGO - CP.VAL_IGV_COMP_COPAGO),'999,990.00')) VAL_COPAGO_S_IGV,
                    'INSTITUCION: '||M.INSTITUCION||DECODE(NVL(CP.PCT_EMPRESA,0),0,'',' ('||CP.PCT_BENEFICIARIO||'%)') INSTITUCION,
                    'CONVENIO: '|| M.DES_CONVENIO CONVENIO,
                    'BENEFICIARIO: '||NVL(CONV.DESCRIPCION_CAMPO, '') BENEFICIARIO,
                    NVL(( SELECT '#REF.: '||TCP.COD_TIPODOC||' '||
                                 DECODE(REF.COD_TIP_PROC_PAGO,'1', REF.NUM_COMP_PAGO_E, REF.NUM_COMP_PAGO)
                           FROM VTA_COMP_PAGO REF,
                                MAE_TIPO_COMP_PAGO_BTLMF TCP
                           WHERE REF.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                           AND REF.COD_LOCAL = CP.COD_LOCAL
                           AND REF.NUM_PED_VTA = CP.NUM_PED_VTA
                           AND REF.NUM_COMP_PAGO = CP.NUM_COMP_COPAGO_REF
                           AND REF.TIP_COMP_PAGO = TCP.TIP_COMP_PAGO),'') DOC_REF,
                    TRIM(TO_CHAR(NVL(CP.VAL_COPAGO_COMP_PAGO,0), '999,990.00')) VAL_COPAGO,
                    TRIM(TO_CHAR(NVL(CP.VAL_IGV_COMP_PAGO, 0), '999,990.00')) VAL_IGV_CP,
                    TRIM(TO_CHAR(((CP.VAL_NETO_COMP_PAGO + NVL(CP.VAL_REDONDEO_COMP_PAGO,0)) - CP.VAL_IGV_COMP_PAGO), '999,990.00')) VAL_CP_S_IGV
              
                FROM VTA_PEDIDO_VTA_CAB CAB,
                     MAE_CONVENIO M,
                     VTA_COMP_PAGO CP,
                     CON_BTL_MF_PED_VTA CONV
                WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                AND CP.COD_LOCAL = cCodLocal_in
                AND CP.NUM_PED_VTA = cNumPedVta_in
                AND CP.SEC_COMP_PAGO = cSecCompPago_in
                AND CAB.COD_CONVENIO = M.COD_CONVENIO
                AND CAB.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                AND CAB.COD_LOCAL = CP.COD_LOCAL
                AND CAB.NUM_PED_VTA = CP.NUM_PED_VTA
                AND CAB.COD_GRUPO_CIA = CONV.COD_GRUPO_CIA(+)
                AND CAB.COD_LOCAL = CONV.COD_LOCAL(+)
                AND CAB.NUM_PED_VTA = CONV.NUM_PED_VTA(+)
                AND 'D_000' = CONV.COD_CAMPO(+)
              ) DA
          ) W
        ),'&','Ã')),'<','Ë'),'@@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt
      ) CONVENIO;

    filaCabecera   curCabecera%ROWTYPE;
    filaDetalle    curDetalle%ROWTYPE;
    filaConvenio   curDatosConvenio%ROWTYPE;
    vIpPc          IMPRESION_TERMICA.IP_PC%TYPE;
    vIdDoc         IMPRESION_TERMICA.ID_DOC%TYPE;
    vContDetalle   NUMBER; 
    vNumLinDetalle NUMBER;
    vFormatoGuia   MAE_COMP_CONV_LINEAS.COD_FORMATO%TYPE;
    vMtoSubTotal   NUMBER(9,2) := 0;
    vFlgConvImprImporte MAE_CONVENIO.FLG_IMPRIME_IMPORTES%TYPE;
    vTipoClienteConv     VTA_COMP_PAGO.TIP_CLIEN_CONVENIO%TYPE;
    vValRedondeoCP       VARCHAR2(20);
    vUsuarioCaja         CE_MOV_CAJA.USU_CREA_MOV_CAJA%TYPE;
    vNumCaja             CE_MOV_CAJA.NUM_CAJA_PAGO%TYPE;
    vNumTurno            CE_MOV_CAJA.NUM_TURNO_CAJA%TYPE;
  BEGIN
    
    SELECT M.FLG_IMPRIME_IMPORTES,
           CP.TIP_CLIEN_CONVENIO,
           TO_CHAR(NVL(CP.VAL_REDONDEO_COMP_PAGO,0),'999,990.00'),
           MOV.USU_CREA_MOV_CAJA,
           MOV.NUM_CAJA_PAGO,
           MOV.NUM_TURNO_CAJA
    INTO vFlgConvImprImporte,
         vTipoClienteConv,
         vValRedondeoCP,
         vUsuarioCaja,
         vNumCaja,
         vNumTurno
    FROM VTA_PEDIDO_VTA_CAB CAB,
         MAE_CONVENIO M,
         VTA_COMP_PAGO CP,
         CE_MOV_CAJA MOV
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in
    AND CAB.COD_CONVENIO = M.COD_CONVENIO
    AND CP.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
    AND CP.COD_LOCAL = CAB.COD_LOCAL
    AND CP.NUM_PED_VTA = CAB.NUM_PED_VTA
    AND CP.SEC_COMP_PAGO = cSecCompPago_in
    AND MOV.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
    AND MOV.COD_LOCAL = CAB.COD_LOCAL
    AND MOV.SEC_MOV_CAJA = CAB.SEC_MOV_CAJA;
    
    BEGIN
      SELECT DECODE(ID_TAB_GRAL ,'280','0',379,'2','1') 
      INTO vFormatoGuia
      FROM PBL_TAB_GRAL 
      WHERE ID_TAB_GRAL IN (279,280,379) AND EST_TAB_GRAL='A';
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20021,'Falta Configurar formato de Guia');
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20021,'Error en la Configuracion de Tamaño de Guia');
    END;

    BEGIN
      SELECT T.NUM_PRODUCTOS
      INTO vNumLinDetalle
      FROM MAE_COMP_CONV_LINEAS T
      WHERE T.COD_GRUPO_CIA = cCodGrupoCia_in
      AND T.TIP_COMP = '03'
      AND T.COD_FORMATO = vFormatoGuia;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20021, 'Falta Configurar Tamaño de Productos que soporta el Documento');
    END;
                 
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vContDetalle := 0;
    
    OPEN curCabecera;
    LOOP
      FETCH curCabecera INTO filaCabecera;
      EXIT WHEN curCabecera%NOTFOUND;
        FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => filaCabecera.VALOR);
    END LOOP;
    CLOSE curCabecera;
    vContDetalle := 0;
    WHILE vContDetalle < 6 LOOP
      FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                      vIpPc_in => vIpPc, 
                                      vValor_in => ' ');
      vContDetalle := vContDetalle + 1;
    END LOOP;
    vContDetalle := 0;
    OPEN curDetalle;
    LOOP
      FETCH curDetalle INTO filaDetalle;
      EXIT WHEN curDetalle%NOTFOUND;
        FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => filaDetalle.VALOR);
        vMtoSubTotal := vMtoSubTotal + filaDetalle.MTO_SUB_TOTAL;
        vContDetalle := vContDetalle + 1;
    END LOOP;
    CLOSE curDetalle;
    IF vFlgConvImprImporte = '1' THEN
      FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ', 106)||
                                                     ' SUB TOTAL S/.  '||
                                                     FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(TRIM(TO_CHAR(vMtoSubTotal,'999,990.00')), 15, pAlineacion_in => 'D'));
    END IF;
    OPEN curDatosConvenio;
    LOOP
      FETCH curDatosConvenio INTO filaConvenio;
      EXIT WHEN curDatosConvenio%NOTFOUND;
        FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => filaConvenio.VALOR);
    END LOOP;
    CLOSE curDatosConvenio;
    
    FARMA_PRINTER.P_GRABA_LINEA_DOC(vIdDoc_in => vIdDoc,
                                    vIpPc_in => vIpPc,
                                    vValor_in => FARMA_PRINTER.F_GET_TEXTO_MATRICIAL(' ',12)||
                                                 'REDO: '||vValRedondeoCP|| ' CAJERO: '||vUsuarioCaja||' CAJA: '||vNumCaja||' TURNO: '||vNumTurno);
                                    
    RETURN FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc,
                                                    vIpPc_in => vIpPc);
  END;
  
END PTOVENTA_IMPRESION_MATRICIAL;
                                
/
