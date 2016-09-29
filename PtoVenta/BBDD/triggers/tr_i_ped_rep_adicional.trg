CREATE OR REPLACE TRIGGER PTOVENTA.TR_I_PED_REP_ADICIONAL
BEFORE INSERT OR UPDATE ON LGT_PARAM_PROD_LOCAL
FOR EACH ROW
DECLARE
vCount      NUMBER;
BEGIN
  IF INSERTING THEN

    SELECT COUNT(*) + 1 INTO vCount
    FROM LGT_PARAM_PROD_LOCAL_BK
    WHERE COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
    AND COD_LOCAL = :NEW.COD_LOCAL
    AND COD_PROD = :NEW.COD_PROD;

    BEGIN
      IF (:NEW.IND_AUTORIZADO = 'N') THEN
          INSERT INTO LGT_PARAM_PROD_LOCAL_BK
                     (INDICE,
                      COD_GRUPO_CIA,
                      COD_LOCAL,
                      COD_PROD,
                      TOT_UNID_VTA_QS,
                      TOT_UNID_VTA_RDM,
                      IND_AUTORIZADO,
                      USU_CREA_PARAM_PROD_LOCAL,
                      FEC_CREA_PARAM_PROD_LOCAL,
                      USU_MOD_PARAM_PROD_LOCAL,
                      FEC_MOD_PARAM_PROD_LOCAL,
                      FEC_PROCESA_MATRIZ,
                      USU_PROCESA_MATRIZ,
                      IND_ACTIVO)
          VALUES (vCount,
                  :NEW.COD_GRUPO_CIA,
                  :NEW.COD_LOCAL,
                  :NEW.COD_PROD,
                  :NEW.TOT_UNID_VTA_QS,
                  :NEW.TOT_UNID_VTA_RDM,
                  :NEW.IND_AUTORIZADO,
                  :NEW.USU_CREA_PARAM_PROD_LOCAL,
                  :NEW.FEC_CREA_PARAM_PROD_LOCAL,
                  NULL,
                  NULL,
                  NULL,
                  NULL,
                  NULL);
      ELSIF (:NEW.IND_AUTORIZADO = 'S') THEN
          INSERT INTO LGT_PARAM_PROD_LOCAL_BK
                     (INDICE,
                      COD_GRUPO_CIA,
                      COD_LOCAL,
                      COD_PROD,
                      TOT_UNID_VTA_QS,
                      TOT_UNID_VTA_RDM,
                      IND_AUTORIZADO,
                      USU_CREA_PARAM_PROD_LOCAL,
                      FEC_CREA_PARAM_PROD_LOCAL,
                      USU_MOD_PARAM_PROD_LOCAL,
                      FEC_MOD_PARAM_PROD_LOCAL,
                      FEC_PROCESA_MATRIZ,
                      USU_PROCESA_MATRIZ,
                      IND_ACTIVO)
          VALUES (vCount,
                  :NEW.COD_GRUPO_CIA,
                  :NEW.COD_LOCAL,
                  :NEW.COD_PROD,
                  :NEW.TOT_UNID_VTA_QS,
                  :NEW.TOT_UNID_VTA_RDM,
                  :NEW.IND_AUTORIZADO,
                  NULL,
                  NULL,
                  NULL,
                  NULL,
                  :NEW.FEC_PROCESA_MATRIZ,
                  :NEW.USU_PROCESA_MATRIZ,
                  :NEW.IND_ACTIVO);
      END IF;

    END;
  ELSIF UPDATING THEN

    SELECT COUNT(*) + 1 INTO vCount
    FROM LGT_PARAM_PROD_LOCAL_BK
    WHERE COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
    AND COD_LOCAL = :NEW.COD_LOCAL
    AND COD_PROD = :NEW.COD_PROD;

    BEGIN
      IF (:NEW.IND_AUTORIZADO = 'S') THEN
          IF(:NEW.TOT_UNID_VTA_RDM != :OLD.TOT_UNID_VTA_RDM) THEN
              INSERT INTO LGT_PARAM_PROD_LOCAL_BK
                         (INDICE,
                          COD_GRUPO_CIA,
                          COD_LOCAL,
                          COD_PROD,
                          TOT_UNID_VTA_QS,
                          TOT_UNID_VTA_RDM,
                          IND_AUTORIZADO,
                          USU_CREA_PARAM_PROD_LOCAL,
                          FEC_CREA_PARAM_PROD_LOCAL,
                          USU_MOD_PARAM_PROD_LOCAL,
                          FEC_MOD_PARAM_PROD_LOCAL,
                          FEC_PROCESA_MATRIZ,
                          USU_PROCESA_MATRIZ,
                          IND_ACTIVO)
              VALUES (vCount,
                      :NEW.COD_GRUPO_CIA,
                      :NEW.COD_LOCAL,
                      :NEW.COD_PROD,
                      :NEW.TOT_UNID_VTA_QS,
                      :NEW.TOT_UNID_VTA_RDM,
                      :NEW.IND_AUTORIZADO,
                      NULL,
                      NULL,
                      NULL,
                      NULL,
                      :NEW.FEC_PROCESA_MATRIZ,
                      :NEW.USU_PROCESA_MATRIZ,
                      :NEW.IND_ACTIVO);
          END IF;

      ELSE
        IF(:NEW.TOT_UNID_VTA_QS != :OLD.TOT_UNID_VTA_QS) THEN
            INSERT INTO LGT_PARAM_PROD_LOCAL_BK
                         (INDICE,
                          COD_GRUPO_CIA,
                          COD_LOCAL,
                          COD_PROD,
                          TOT_UNID_VTA_QS,
                          TOT_UNID_VTA_RDM,
                          IND_AUTORIZADO,
                          USU_CREA_PARAM_PROD_LOCAL,
                          FEC_CREA_PARAM_PROD_LOCAL,
                          USU_MOD_PARAM_PROD_LOCAL,
                          FEC_MOD_PARAM_PROD_LOCAL,
                          FEC_PROCESA_MATRIZ,
                          USU_PROCESA_MATRIZ,
                          IND_ACTIVO)
              VALUES (vCount,
                      :NEW.COD_GRUPO_CIA,
                      :NEW.COD_LOCAL,
                      :NEW.COD_PROD,
                      :NEW.TOT_UNID_VTA_QS,
                      :NEW.TOT_UNID_VTA_RDM,
                      :NEW.IND_AUTORIZADO,
                      NULL,
                      NULL,
                      :NEW.USU_MOD_PARAM_PROD_LOCAL,
                      :NEW.FEC_MOD_PARAM_PROD_LOCAL,
                      NULL,
                      NULL,
                      NULL);
          END IF;
      END IF;
    END;
  END IF;
END;
/

