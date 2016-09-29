CREATE OR REPLACE TRIGGER PTOVENTA.TR_U_VTA_COMP_PAGO_NUM_COMP
    BEFORE UPDATE OF NUM_COMP_PAGO ON VTA_COMP_PAGO
    FOR EACH ROW
DECLARE
    C_NUM_DOCUMENTO char(10) := :NEW.NUM_COMP_PAGO;
    N_CANT          INTEGER;
begin
    -- DJARA 23/09/2014 Replica para comprobante electronico
    IF nvl(:NEW.cod_tip_proc_pago, '0') != '1' THEN
        :NEW.NUM_COMP_PAGO_E := :NEW.NUM_COMP_PAGO;
    END IF;

    --ERIOS 2.2.8 Valida que el numero de guia, no se repita
    IF :NEW.tip_comp_pago = '03' THEN

        SELECT COUNT(1)
          INTO N_CANT
          FROM (SELECT NUM_GUIA_REM
                  FROM LGT_GUIA_REM
                 WHERE COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
                   AND COD_LOCAL = :NEW.COD_LOCAL
                   AND NUM_GUIA_REM = C_NUM_DOCUMENTO
                --AND ind_guia_impresa = 'S'
                --AND est_guia_rem = 'A'
                );

        IF N_CANT > 0 THEN
            raise_application_error(-20150,
                                    'EL NUMERO: ' || C_NUM_DOCUMENTO ||
                                    ' YA EXISTE COMO GUIA DE REMISION. CORRIJA EL NUMERO DE GUIA.');
        END IF;
    END IF;

end TR_U_VTA_COMP_PAGO_NUM_COMP;
/

