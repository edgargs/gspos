CREATE OR REPLACE TRIGGER PTOVENTA."TR_U_CE_CIERRE_DIA_VENTA_VB" AFTER UPDATE OF IND_VB_CIERRE_DIA ON CE_CIERRE_DIA_VENTA
FOR EACH ROW
DECLARE
BEGIN
     PTOVENTA_CE_LMR.CE_REGISTRA_HIST_VB_CIERRE(:NEW.COD_GRUPO_CIA,
                                                :NEW.COD_LOCAL,
                                                TO_CHAR(:NEW.FEC_CIERRE_DIA_VTA,'dd/MM/yyyy'),
                                                :NEW.IND_VB_CIERRE_DIA,
                                                :NEW.SEC_USU_LOCAL_CREA,
                                                :NEW.SEC_USU_LOCAL_VB,
                                                :NEW.DESC_OBSV_CIERRE_DIA,
                                                :NEW.TIP_CAMBIO_CIERRE_DIA,
                                                :NEW.USU_MOD_CIERRE_DIA);
END;
/

