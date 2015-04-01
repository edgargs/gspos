--------------------------------------------------------
--  DDL for Package PTOVENTA_MATRIZ_TRANSF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_MATRIZ_TRANSF" is

  -- Author  : JCALLO
  -- Created : 23/01/2009
  -- Purpose : TRANSFERENCIAS DE LOCAL A LOCAL EN LINEA

  TYPE FarmaCursor IS REF CURSOR;


  FUNCTION TRANSF_F_CHAR_LLEVAR_DESTINO(cCodGrupoCia_in     IN CHAR,
                                       cCodLocalOrigen_in  IN CHAR,
                                       cCodLocalDestino_in IN CHAR,
                                       cNumNotaEs_in       IN CHAR,
                                       vIdUsu_in           IN CHAR)
  RETURN CHAR;


 PROCEDURE TRANS_P_ANULAR_TRANSFERENCIA(cCodGrupoCia_in  IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        cNumNota_in      IN CHAR,
                                        cCodMotKardex_in IN CHAR,
                                        cTipDocKardex_in IN CHAR,
                                        vIdUsu_in        IN VARCHAR2);
 FUNCTION TRANSF_F_BOOL_VER_TRANS_ACEP(cGrupoCia_in      IN CHAR,
                                       cCodLocal_origen  IN CHAR,
                                       cNumNotaEs_in     IN CHAR,
                                       cCodLocal_destino IN CHAR)
   RETURN BOOLEAN;

 PROCEDURE TRANSF_P_ELIM_TRANSF_ENLOCDEST(cGrupoCia_in      IN CHAR,
                                          cCodLocal_in      IN CHAR,
                                          cNumNotaEs_in     IN CHAR,
                                          cCodLocal_destino IN CHAR);

 PROCEDURE TRANSF_P_ELIM_TRANSF_EN_MATRIZ(cGrupoCia_in      IN CHAR,
                                          cCodLocal_in      IN CHAR,
                                          cNumNotaEs_in     IN CHAR,
                                          cCodLocal_destino IN CHAR);

PROCEDURE ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cEstNotaEs_in IN CHAR);
  
end PTOVENTA_MATRIZ_TRANSF;



/
