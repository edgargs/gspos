--------------------------------------------------------
--  DDL for Package Body FARMA_RECARGA_VIRTUAL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."FARMA_RECARGA_VIRTUAL" IS

 /* *********************************************************************** */
  FUNCTION F_NUM_INTENTOS_RESPUESTA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR) RETURN NUMBER IS
    nValor number;
  begin

    SELECT to_number(valor, '99999990')
      into nValor
      FROM PBL_PARAM_REC_VIRTUAL
     WHERE tipo_parametro = 'INTENTO_RPTA';
    RETURN nValor;
  END;
 /* *********************************************************************** */
  FUNCTION F_LEAD_TIEMPO_RPTA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR) RETURN NUMBER IS
    nValor number;
  begin

    SELECT to_number(valor, '99999990')
      into nValor
      FROM PBL_PARAM_REC_VIRTUAL
     WHERE tipo_parametro = 'LEAD_ESPERA';

    RETURN nValor;
  END;
 /* *********************************************************************** */
  FUNCTION F_TIME_OUT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
    RETURN NUMBER IS
    nValor number;
  begin

    SELECT to_number(valor, '99999990')
      into nValor
      FROM PBL_PARAM_REC_VIRTUAL
     WHERE tipo_parametro = 'TIME_OUT';

    RETURN nValor;
  END;
 /* *********************************************************************** */
END FARMA_RECARGA_VIRTUAL;

/
