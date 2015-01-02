--------------------------------------------------------
--  DDL for Package Body PKG_UTIL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PKG_UTIL" IS

    /******************************************/

    PROCEDURE SP_ARREGLO
    (
        P_ARREGLO     VARCHAR2,
        P_SALIDA      OUT TIPO_ARREGLO,
        P_COMPONENTES OUT INT,
        P_SEPARADOR   CHAR DEFAULT '|'
    ) AS
        W_TEXTO           VARCHAR2(32000);
        W_SEPARADOR       CHAR(1) := P_SEPARADOR;
        W_POSICION_INICIO INT;
        W_POSICION_FIN    INT;
        W_CONTADOR        INT;
--        W_PALABRA         VARCHAR2(50);
        W_PALABRA         VARCHAR2(1500);
    BEGIN
        W_TEXTO    := P_ARREGLO;
        W_CONTADOR := 0;
        WHILE (W_CONTADOR <= 5000 AND P_ARREGLO IS NOT NULL)
        LOOP
            IF W_CONTADOR = 0 THEN
                W_POSICION_INICIO := 1;
            ELSE
                W_POSICION_INICIO := INSTR(W_TEXTO,
                                           W_SEPARADOR,
                                           1,
                                           W_CONTADOR) + 1;
            END IF;
            IF W_POSICION_INICIO = 1 AND W_CONTADOR > 0 THEN
                EXIT;
            END IF;
            W_POSICION_FIN := INSTR(W_TEXTO, W_SEPARADOR, 1, W_CONTADOR + 1) - 1;
            IF W_POSICION_FIN = -1 THEN
                W_POSICION_FIN := LENGTH(P_ARREGLO);
            END IF;
            W_PALABRA  := SUBSTR(W_TEXTO,
                                 W_POSICION_INICIO,
                                 W_POSICION_FIN - W_POSICION_INICIO + 1);
            W_CONTADOR := W_CONTADOR + 1;

            P_SALIDA(W_CONTADOR) := W_PALABRA;
            --   dbms_output.put_line(w_palabra);
        END LOOP;
        IF SUBSTR(TRIM(P_ARREGLO), LENGTH(TRIM(P_ARREGLO)), 1) = '|' THEN
            P_COMPONENTES := W_CONTADOR - 1;
        ELSE
            P_COMPONENTES := W_CONTADOR;
        END IF;
    END;
    /*******************************************************************/
    FUNCTION FN_SEGMENTO
    (
        A_CADENA    VARCHAR2,
        A_ITEM      INT,
        A_SEPARADOR CHAR DEFAULT '|'
    ) RETURN VARCHAR2 AS
        W_POSICION_INI INT;
        W_POSICION_FIN INT;
    BEGIN
        IF A_ITEM = 1 THEN
            W_POSICION_INI := 1;
            W_POSICION_FIN := INSTR(A_CADENA, A_SEPARADOR, 1, 1) - 1;
            IF W_POSICION_FIN = -1 THEN
                W_POSICION_FIN := 1;
            END IF;
        ELSE
            W_POSICION_INI := INSTR(A_CADENA, A_SEPARADOR, 1, A_ITEM - 1) + 1;
            IF W_POSICION_INI = 1 THEN
                RETURN '';
            END IF;
            W_POSICION_FIN := INSTR(A_CADENA, A_SEPARADOR, 1, A_ITEM) - 1;
            IF W_POSICION_FIN = -1 THEN
                W_POSICION_FIN := LENGTH(A_CADENA);
            END IF;
        END IF;
        RETURN SUBSTR(A_CADENA,
                      W_POSICION_INI,
                      W_POSICION_FIN - W_POSICION_INI + 1);
    END FN_SEGMENTO;
end;

/
