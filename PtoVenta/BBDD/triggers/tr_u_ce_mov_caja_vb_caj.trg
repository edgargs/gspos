CREATE OR REPLACE TRIGGER PTOVENTA."TR_U_CE_MOV_CAJA_VB_CAJ" AFTER UPDATE OF IND_VB_CAJERO ON CE_MOV_CAJA
FOR EACH ROW
DECLARE
BEGIN
     PTOVENTA_CE_LMR.CE_REGISTRA_HIST_VB_CAJ(:NEW.COD_GRUPO_CIA,
                                             :NEW.COD_LOCAL,
                                             :NEW.SEC_MOV_CAJA,
                                             :NEW.IND_VB_CAJERO,
                                             :NEW.DESC_OBS_CIERRE_TURNO,
                                             :NEW.USU_MOD_MOV_CAJA);
END;
/

