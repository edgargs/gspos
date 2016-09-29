CREATE OR REPLACE TRIGGER PTOVENTA.TGR_VALIDA_AHORRO_NEGATIVO
    BEFORE UPDATE OR INSERT OF AHORRO_CAMP ON PTOVENTA.VTA_PEDIDO_VTA_DET
    FOR EACH ROW
when (NVL(NEW.AHORRO_CAMP,0) < 0 OR NVL(OLD.AHORRO_CAMP,0) < 0)
DECLARE
  isAnulacion CHAR(1) := 'N';
  indActivo CHAR(1) := 'N';
BEGIN
  BEGIN
    SELECT TAB.LLAVE_TAB_GRAL
    INTO indActivo
    FROM PBL_TAB_GRAL TAB
    WHERE TAB.ID_TAB_GRAL=548;
  EXCEPTION
    WHEN OTHERS THEN
      indActivo := 'S';
  END;

  SELECT DECODE(COUNT(1),1,'N','S')
  INTO isAnulacion
  FROM VTA_PEDIDO_VTA_CAB CAB
  WHERE CAB.COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
  AND CAB.COD_LOCAL = :NEW.COD_LOCAL
  AND CAB.NUM_PED_VTA = :NEW.NUM_PED_VTA
  AND CAB.NUM_PED_VTA_ORIGEN IS NULL;

  IF isAnulacion = 'N' AND indActivo = 'S' THEN
    :NEW.AHORRO := NVL(:NEW.AHORRO_PACK,0) + NVL(:NEW.AHORRO_PUNTOS,0);
    :NEW.COD_CAMP_CUPON := NULL;
    :NEW.PTOS_AHORRO := 0;
    :NEW.AHORRO_CAMP := 0;
  END IF;
END;
/

