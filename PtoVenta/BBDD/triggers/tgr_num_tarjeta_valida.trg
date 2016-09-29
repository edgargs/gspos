CREATE OR REPLACE TRIGGER PTOVENTA.TGR_NUM_TARJETA_VALIDA
    BEFORE UPDATE OR INSERT OF NUM_TARJ_PUNTOS ON PTOVENTA.VTA_PEDIDO_VTA_CAB
    FOR EACH ROW
when (NVL(NEW.EST_TRX_ORBIS,'N') IN ('P', 'E', 'D') AND NEW.NUM_PED_VTA_ORIGEN IS NULL AND LENGTH(NEW.NUM_TARJ_PUNTOS)>0)
DECLARE
  vIndicador CHAR(1) := 'N';
  vCantDocIdent INTEGER;
  vCorrecto CHAR(1) := 'N';
BEGIN

    SELECT NVL(LLAVE_TAB_GRAL,'N')
    INTO vIndicador
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL=542
    AND EST_TAB_GRAL='A';

    IF vIndicador = 'S' THEN
      SELECT COUNT(1)
      INTO vCantDocIdent
      FROM (SELECT EXTRACTVALUE(xt.column_value, 'e') VAL
              FROM TABLE(XMLSEQUENCE(EXTRACT(XMLTYPE('<coll><e>' || REPLACE((
                 SELECT REPLACE(LLAVE_TAB_GRAL,',','@')
                 FROM PBL_TAB_GRAL TG
                 WHERE TG.ID_TAB_GRAL = 221
              ),'@','</e><e>') ||'</e></coll>'),'/coll/e'))) xt
           ) W
     WHERE W.VAL = LENGTH(:NEW.NUM_TARJ_PUNTOS);
     IF vCantDocIdent > 0 THEN
       SELECT COUNT(1)
       INTO vCantDocIdent
       FROM PTOVENTA.PBL_CLIENTE
       WHERE DNI_CLI=:NEW.NUM_TARJ_PUNTOS;
       IF vCantDocIdent > 0 THEN
         vCorrecto := 'S';
       ELSE
         vCorrecto := 'N';
       END IF;
     ELSE
       IF LENGTH(:NEW.NUM_TARJ_PUNTOS)=13 THEN
         vCorrecto := 'S';
       ELSE
         vCorrecto := 'N';
       END IF;
     END IF;
    ELSE
      vCorrecto := 'S';
    END IF;

    IF vCorrecto = 'N' THEN
      RAISE_APPLICATION_ERROR(-20000, 'FORMATO DE NRO TARJETA/DOC.IDENTIDAD INCORRECTO -->'||:NEW.NUM_TARJ_PUNTOS);
    END IF;

END;
/

