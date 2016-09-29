create or replace trigger ptoventa.TGR_IU_VTA_PEDIDO_VTA_CAB_02
    before UPDATE OR INSERT OF NUM_TARJ_PUNTOS ON PTOVENTA.VTA_PEDIDO_VTA_CAB
    FOR EACH ROW
when (nvl(length(NEW.num_tarj_puntos),0) > 0 and nvl(length(NEW.num_tarj_puntos),0) <= 11 AND NEW.dni_cli is null)
DECLARE
--trigger creado temporalmente mientras solucionan el bug detectado por JAYALA
BEGIN
:NEW.dni_cli:= :NEW.NUM_TARJ_PUNTOS;
END;
/

