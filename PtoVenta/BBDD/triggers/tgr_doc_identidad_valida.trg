CREATE OR REPLACE TRIGGER PTOVENTA.TGR_DOC_IDENTIDAD_VALIDA
    BEFORE UPDATE OR INSERT ON PTOVENTA.PBL_CLIENTE
    FOR EACH ROW

DECLARE
  vIndicador CHAR(1) := 'N';
  vCantDocIdent INTEGER;
  vCorrecto CHAR(1) := 'N';
  vValor NUMBER;
BEGIN

    SELECT NVL(LLAVE_TAB_GRAL,'N')
    INTO vIndicador
    FROM PBL_TAB_GRAL
    WHERE ID_TAB_GRAL=543
    AND EST_TAB_GRAL='A';
    DBMS_OUTPUT.PUT_LINE(:NEW.DNI_CLI);
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
     WHERE W.VAL = LENGTH(:NEW.DNI_CLI);
     DBMS_OUTPUT.PUT_LINE(vCantDocIdent);
     IF vCantDocIdent > 0 THEN
       BEGIN
         vValor := TO_NUMBER(:NEW.DNI_CLI,'9999999999990.00')*1;
         vCorrecto := 'S';
       EXCEPTION
         WHEN OTHERS THEN
           vCorrecto := 'N';
       END;
--       vCorrecto := 'S';
     END IF;
    ELSE
      vCorrecto := 'S';
    END IF;

    IF vCorrecto = 'N' THEN
      RAISE_APPLICATION_ERROR(-20000, 'NRO DOC.IDENTIDAD INCORRECTO -->'||:NEW.DNI_CLI);
    END IF;

END;
/

