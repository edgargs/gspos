CREATE OR REPLACE TRIGGER PTOVENTA.TR_U_LGT_PROD_LOCAL
  BEFORE UPDATE ON  LGT_PROD_LOCAL
  FOR EACH ROW
DECLARE
  -- local variables here
BEGIN
     IF (ABS(:OLD.VAL_FRAC_LOCAL * :OLD.VAL_PREC_VTA - :NEW.VAL_FRAC_LOCAL * :NEW.VAL_PREC_VTA) >= 0.05) THEN
        INSERT INTO LGT_PROD_LOCAL_BK (COD_GRUPO_CIA,COD_LOCAL, COD_PROD,
                                      VAL_FRAC_LOCAL_OLD, VAL_PREC_VTA_OLD, UNID_VTA_OLD,
                                      VAL_FRAC_LOCAL_NVO, VAL_PREC_VTA_NVO, UNID_VTA_NVO
                                      )
        VALUES(:OLD.COD_GRUPO_CIA,
               :OLD.COD_LOCAL,
               :OLD.COD_PROD,
               :OLD.VAL_FRAC_LOCAL,
               :OLD.VAL_PREC_VTA,
               :OLD.UNID_VTA,
               :NEW.VAL_FRAC_LOCAL,
               :NEW.VAL_PREC_VTA,
               :NEW.UNID_VTA
        );

     END IF;

END TR_U_LGT_PROD_LOCAL;
/

