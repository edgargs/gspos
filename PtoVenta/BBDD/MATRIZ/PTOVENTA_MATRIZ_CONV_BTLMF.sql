--------------------------------------------------------
--  DDL for Package PTOVENTA_MATRIZ_CONV_BTLMF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_MATRIZ_CONV_BTLMF" is

  TYPE FarmaCursor IS REF CURSOR;

  FUNCTION BTLMF_F_CUR_OBT_BENIFICIARIO(cCodGrupoCia_in  CHAR,
                                          cCodLocal_in     CHAR,
                                          cSecUsuLocal_in  CHAR,
                                          vCodConvenio_in  CHAR,
                                          vCodBenif_in CHAR)
  RETURN FarmaCursor;

  --Descripcion: Cantidad Lista Beneficiario
  --Fecha        Usuario        Comentario
  --30/04/2014   ERIOS          Creacion
  FUNCTION GET_CANT_LISTA_BENEFICIARIO(CCODGRUPOCIA_IN  CHAR,
                                            CCODLOCAL_IN     CHAR,
                                            CSECUSULOCAL_IN  CHAR,
                                            VCODCONVENIO_IN  CHAR,
                                            VBENIFICIARIO_IN VARCHAR2)
	RETURN INTEGER;
	
  --Descripcion: Lista Beneficiario
  --Fecha        Usuario        Comentario
  --30/04/2014   ERIOS          Creacion
  FUNCTION BTLMF_F_CUR_LISTA_BENIFICIARIO(cCodGrupoCia_in  CHAR,
                                          cCodLocal_in     CHAR,
                                          cSecUsuLocal_in  CHAR,
                                          vCodConvenio_in  CHAR,
                                          vBenificiario_in VARCHAR2

                                          )

   RETURN FarmaCursor;
   
end PTOVENTA_MATRIZ_CONV_BTLMF;

/
