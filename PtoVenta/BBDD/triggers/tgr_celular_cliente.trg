CREATE OR REPLACE TRIGGER PTOVENTA.TGR_CELULAR_CLIENTE
    BEFORE UPDATE OR INSERT OF CELL_CLI ON PTOVENTA.PBL_CLIENTE
    FOR EACH ROW
when (NEW.CELL_CLI IS NULL AND NVL(NEW.IND_ENVIADO_ORBIS,'N') IN ('E','P'))
DECLARE
BEGIN
  :NEW.CELL_CLI := NVL(:NEW.CELL_CLI,'0');
END;
/

