-- No se ha podido presentar el DDL TRIGGER para el objeto BTLCADENA.TG_CAB_DOCUMENTO_FINANZAS mientras que DBMS_METADATA intenta utilizar el generador interno.
CREATE trigger btlcadena.TG_CAB_DOCUMENTO_FINANZAS 
/*
  Autor: Pablo Herrera 
  Fecha: 18/01/2007
  Proposito:
            Este trigger tien por finalidad mantener sincronizados los documentos
            emitidos en el punto de venta que son al credito con el sistema financiero
            contable.
  Bitacora: 
        09/04/07 - Pherrera - Se modifico para que pase la base imponible cuando es exonerado
                    se agrego para jalar el DNI del beneficiario de convenio BTL
        07/01/08 - pherrera - se agrego los documentos TKB y TKF
        13/02/08 - pherrera - se comento el return que habia en reactivacion de documentos
        05/05/08 - pherrera - se agrego filtro en la eliminación para que no se anulen los docs con la SINCRO
        18/06/08 - pherrera - Se modifico condición para convenio BTL y agarre tambien amazonia
*/
  before update or delete on BTLPROD.CAB_DOCUMENTO
  for each row 
when ( NEW.COD_TIPO_VTAFIN IN ('1', '2', '3') OR OLD.COD_TIPO_VTAFIN IN ('1', '2', '3'))
DECLARE
    -- local variables here
    V_DESDE        INT;
    V_TIPVTA       CHAR(1);
    V_RUC          CMR.MAE_CLIENTE.NUM_DOCUMENTO_ID%TYPE;
    V_PORIGV       NUMERIC(20, 2) := 0;
    V_BENEFICIARIO VARCHAR2(10);
    V_MOTIVO       VARCHAR2(5);
    V_PEDIDO       VARCHAR2(15);
    V_TIPDOCREF    VARCHAR2(20);--MEDCO.FACTURAC.TIP_DOC%TYPE;
    V_NRODOCREF    BTLPROD.CAB_DOCUMENTO.NUM_DOCUMENTO_REFER%TYPE;
    V_GLOSA        VARCHAR2(50);
    V_IMPUESTO     BTLPROD.CAB_DOCUMENTO.MTO_IMPUESTO%TYPE;
    V_BASE         BTLPROD.CAB_DOCUMENTO.MTO_BASE_IMP%TYPE;
    V_NUMFOR       BTLPROD.DET_ESPECIE_VALORADA.NUM_ESPECIE_VAL%TYPE;
    V_CONVENIO_BTL BTLPROD.CAB_DOCUMENTO.COD_CONVENIO%TYPE := '0000000011';
    V_CONVENIO_AMA BTLPROD.CAB_DOCUMENTO.COD_CONVENIO%TYPE := '0000000040';
BEGIN




--RAISE_APPLICATIOn_ERROR(-20000,'Temporalmente no se puede realizar este tipo de venta');
    /* captura el tipo de venta
        0=Venta normal
        1=Credito a terceros
        2=Convenios
        3=Cobro por responsabilidad
        4=Ventas del CDI
    */
    IF DELETING AND USER != 'SINCRONIZACION' THEN

        MEDCO.P_ANULA_FACTURA(A_DESDE   => CASE :OLD.COD_TIPO_DOCUMENTO WHEN 'NCR' THEN '2' ELSE '0' END,
                                    A_CENCOS  => :OLD.COD_LOCAL,
                                    A_TIP_DOC => :OLD.COD_TIPO_DOCUMENTO,
                                    A_NRO_DOC => :OLD.NUM_DOCUMENTO);
    ELSE   
        --validando que no sea una de las actualizaciones del sistema
        IF :NEW.NUM_DOCUMENTO = :OLD.NUM_DOCUMENTO AND
           :NEW.COD_ESTADO = :OLD.COD_ESTADO AND
           :NEW.COD_TIPO_VTAFIN = :OLD.COD_TIPO_VTAFIN THEN
            RETURN;
        END IF;
    
        --raise_application_error(-20000, 'ESTE VALOR-->'||:NEW.COD_TIPO_VTAFIN);
        V_TIPVTA := :NEW.COD_TIPO_VTAFIN;
    
        --cuando se anula
        IF :NEW.COD_ESTADO = 'ANU' THEN
            --hay que anular el documento

            MEDCO.P_ANULA_FACTURA(A_DESDE   => CASE
                                                      :NEW.COD_TIPO_DOCUMENTO
                                                         WHEN 'NCR' THEN
                                                          '2'
                                                         ELSE
                                                          '0'
                                                     END,
                                        A_CENCOS  => :NEW.COD_LOCAL,
                                        A_TIP_DOC => :NEW.COD_TIPO_DOCUMENTO,
                                        A_NRO_DOC => :NEW.NUM_DOCUMENTO);
        END IF;
    
        --para el caso de reactivacion de documentos
        /*    IF :OLD.COD_ESTADO = 'ANU' AND :NEW.COD_ESTADO = 'EMI' THEN
            RAISE_APPLICATION_ERROR(-20000, 'REACTIVACION DE DOCUMENTOS');
            RETURN;
        END IF;*/
    
        --para el caso de correccion de correlativos
        IF :OLD.COD_LOCAL = :NEW.COD_LOCAL AND
           :OLD.COD_TIPO_DOCUMENTO = :NEW.COD_TIPO_DOCUMENTO AND
           :OLD.NUM_DOCUMENTO <> :NEW.NUM_DOCUMENTO THEN
            RAISE_APPLICATION_ERROR(-20000, 'CORRECCION DE CORRELATIVOS');
        END IF;
    
        --cuando se inserta
        IF :NEW.COD_ESTADO = 'EMI' THEN
            IF :NEW.COD_TIPO_DOCUMENTO IN ('BOL', 'FAC', 'TKB', 'TKF') THEN
                V_DESDE    := 0;
                V_GLOSA    := 'PUNTO DE VENTA';
                V_IMPUESTO := :NEW.MTO_IMPUESTO;
            
                --hay que cargar los datos segun el tipo de venta
                IF V_TIPVTA = '1' THEN
                    --MAYORISTA
                    V_MOTIVO := '28';
                    V_RUC    := BTLPROD.PKG_CLIENTE.FN_LISTA_DOCUMENTO(:NEW.COD_CLIENTE);
                
                ELSIF V_TIPVTA = '2' THEN
                    --CONVENIO
                    --motivo segun tabla MOTIVOS de MEDCO
                    V_MOTIVO       := '25';
                    V_BENEFICIARIO := :NEW.COD_CLIENTE;
                
                    BEGIN
                        SELECT C.NUM_DOCUMENTO_ID
                          INTO V_RUC
                          FROM CMR.MAE_CONVENIO CO, CMR.MAE_CLIENTE C
                         WHERE CO.COD_CLIENTE = C.COD_CLIENTE
                           AND CO.COD_CONVENIO = :NEW.COD_CONVENIO;
                    
                    EXCEPTION
                        WHEN NO_DATA_FOUND THEN
                            RAISE_APPLICATION_ERROR(-20000,
                                                    'NO SE CONTRO LOS DATOS DEL CONVENIO');
                    END;
                    --2do ver si es convenio BTL, sacar el dni de beneficiario
                    IF :NEW.COD_CONVENIO IN
                       (V_CONVENIO_BTL, V_CONVENIO_AMA) THEN
                        V_MOTIVO := '23';
                        V_RUC    := BTLPROD.PKG_CLIENTE.FN_LISTA_DOCUMENTO(:NEW.COD_CLIENTE);
                    END IF;
                
                ELSIF V_TIPVTA = '3' THEN
                    --cobro por resp
                    V_MOTIVO := '27';
                    V_RUC    := '20302629219';
                    V_GLOSA  := 'COBRO RESPONSABILIDAD ';
                END IF;
            
            ELSIF :NEW.COD_TIPO_DOCUMENTO = 'NCR' THEN
                BEGIN
                    V_DESDE     := 2;
                    V_GLOSA     := 'PUNTO DE VENTA';
                    V_TIPDOCREF := CASE :NEW.COD_TIPO_DOCUMENTO_REFER
                                       WHEN 'FAC' THEN
                                        'FA'
                                       WHEN 'BOL' THEN
                                        'BV'
                                       WHEN 'TKB' THEN
                                        'TB'
                                       WHEN 'TKF' THEN
                                        'TF'
                                       ELSE
                                        'BV'
                                   END;
                    V_NRODOCREF := :NEW.NUM_DOCUMENTO_REFER;
                    V_IMPUESTO  := :NEW.MTO_IMPUESTO * -1;
                
                    IF V_TIPVTA = '3' THEN
                        --cobro por resp
                        V_MOTIVO := '27';
                        V_RUC    := '20302629219';
                        V_GLOSA  := 'COBRO RESPONSABILIDAD ';
                    ELSE
                        IF :NEW.COD_CONVENIO IS NOT NULL THEN
                            V_RUC := BTLPROD.PKG_CLIENTE.FN_LISTA_DOCUMENTO(:NEW.COD_EMPRESA);
                        ELSE
                            V_RUC := BTLPROD.PKG_CLIENTE.FN_LISTA_DOCUMENTO(NVL(:NEW.COD_CLIENTE,
                                                                                :NEW.COD_EMPRESA));
                        END IF;
                    END IF;
                
                    --verificar que exista el documento al credito y capturar el motivo de venta que tiene

                    SELECT MOTIVO, BENEFICIARIO, POR_IGV
                      INTO V_MOTIVO, V_BENEFICIARIO, V_PORIGV
                      FROM MEDCO.FACTURAC F
                     WHERE F.CIA = :NEW.CIA
                       AND F.TIP_DOC = V_TIPDOCREF
                       AND F.NRO_DOC = V_NRODOCREF
                       AND F.ESTADO = 'T';

                --RETURN;
                EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                        BEGIN
                            --saca los datos de la misma nota de credito
                            V_MOTIVO       := CASE :NEW.COD_TIPO_VTAFIN
                                                  WHEN '1' THEN
                                                   '28'
                                                  WHEN '2' THEN
                                                   '25'
                                                  WHEN '3' THEN
                                                   '27'
                                              END;
                            V_BENEFICIARIO := :NEW.COD_CLIENTE;
                        END;
                END;
                --para el caso de SOAT por convenio BTL
            ELSIF :NEW.COD_TIPO_DOCUMENTO = 'EVA' AND
                  :NEW.COD_CONVENIO IN (V_CONVENIO_BTL, V_CONVENIO_AMA) THEN
                BEGIN
                    --para cargar el nro de formulario
                    BEGIN
                        SELECT DE.NUM_ESPECIE_VAL
                          INTO V_NUMFOR
                          FROM BTLPROD.DET_ESPECIE_VALORADA DE
                         WHERE DE.CIA = :NEW.CIA
                           AND DE.COD_TIPO_DOCUMENTO =
                               :NEW.COD_TIPO_DOCUMENTO
                           AND DE.NUM_DOCUMENTO = :NEW.NUM_DOCUMENTO
                           AND DE.COD_MODALIDAD = :NEW.COD_MODALIDAD_VENTA;
                    EXCEPTION
                        WHEN NO_DATA_FOUND THEN
                            RAISE_APPLICATION_ERROR(-20000,
                                                    'NO SE ENCONTRARON LOS DATOS DE LA ESPECIE VALORADA');
                    END;
                
                    V_RUC := BTLPROD.PKG_CLIENTE.FN_LISTA_DOCUMENTO(:NEW.COD_CLIENTE);

                    MEDCO.P_GENERA_ASIENTO_SOAT(A_CENCOS  => :NEW.COD_LOCAL,
                                                      A_FCH_MOV => :NEW.FCH_EMISION,
                                                      A_NUM_FOR => V_NUMFOR, --'13050107012', 
                                                      A_IMP_VTA => :NEW.MTO_TOTAL,
                                                      A_CTA_CTE => V_RUC);
                
                    RETURN;
                END;
            END IF;
            --para calcular la base imponible
            V_BASE := NVL(:NEW.MTO_TOTAL, 0) - NVL(ABS(V_IMPUESTO), 0);
            --porcentaje del IGV
            IF V_IMPUESTO <> 0 THEN
                IF NVL(V_PORIGV, 0) = 0 THEN
                    V_PORIGV := NUEVO.PKG_PARAMETRO.FN_VALOR('CJE_PORIGV');
                END IF;
            END IF;
        
            --A_DESDE = de donde se esta intentando grabar 0=Punto de venta, 1=BTL0, 2=NC de punto de venta

            MEDCO.P_GRABA_FACTURA(A_DESDE        => V_DESDE,
                                        A_TIP_DOC      => :NEW.COD_TIPO_DOCUMENTO,
                                        A_NRO_DOC      => :NEW.NUM_DOCUMENTO,
                                        A_FEC_EMI      => :NEW.FCH_REGISTRA,
                                        A_RUC          => V_RUC,
                                        A_GUIA         => NULL,
                                        A_MONEDA       => 'S',
                                        A_TIP_CAM      => :NEW.IMP_TIP_CAMBIO,
                                        A_IMP_BRUTO    => V_BASE,
                                        A_IMP_IGV      => V_IMPUESTO,
                                        A_IMP_TOTAL    => :NEW.MTO_TOTAL,
                                        A_CENCOS       => TRIM(:NEW.COD_LOCAL),
                                        A_USUARIO      => :NEW.COD_USUARIO_DEPENDIENTE,
                                        A_GLOSA1       => V_GLOSA,
                                        A_DOC_REF      => V_NRODOCREF,
                                        A_TIP_REF      => V_TIPDOCREF,
                                        A_POR_IGV      => V_PORIGV,
                                        A_INAFECTO     => :NEW.MTO_EXONERADO,
                                        A_PERIODO      => TO_CHAR(:NEW.FCH_REGISTRA,
                                                                  'YYMM'),
                                        A_UBIGEO       => :NEW.COD_UBIGEO,
                                        A_BENEFICIARIO => V_BENEFICIARIO,
                                        A_CONVENIO     => :NEW.COD_CONVENIO,
                                        A_MOTIVO       => V_MOTIVO,
                                        A_PEDIDO       => V_PEDIDO);

                                       
        
        END IF;    
    END IF;
  /*
  IF :NEW.COD_CONVENIO='0000001041' THEN
    UPDATE BTLPROD.CAB_DOCUMENTO
    SET COD_TIPO_VTAFIN=3
    WHERE CIA = :NEW.CIA
    AND COD_TIPO_DOCUMENTO=:NEW.COD_TIPO_DOCUMENTO_REFER
    AND NUM_DOCUMENTO =:NEW.NUM_DOCUMENTO_REFER;
   END IF;
   */
END;
