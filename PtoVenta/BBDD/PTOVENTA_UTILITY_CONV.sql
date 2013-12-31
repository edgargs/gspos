--------------------------------------------------------
--  DDL for Package PTOVENTA_UTILITY_CONV
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_UTILITY_CONV" AS

  C_C_TIP_DOC_NATURAL VTA_CLI_LOCAL.TIP_DOC_IDENT%TYPE :='01';
  C_C_TIP_DOC_JURIDICO VTA_CLI_LOCAL.TIP_DOC_IDENT%TYPE :='02';
  C_C_ESTADO_ACTIVO VTA_CLI_LOCAL.EST_CLI_LOCAL%TYPE :='A';
  C_C_INDICADOR_NO  VTA_CLI_LOCAL.IND_CLI_JUR%TYPE :='N';

  TYPE FarmaCursor IS REF CURSOR;

    FUNCTION GET_IS_PED_VALIDA_RAC(
                  cCodGrupoCia_in	  IN CHAR,
  		   					cCodLocal_in	  	IN CHAR,
									cNumPedVta_in	  	IN CHAR)
	RETURN CHAR;



	END;

/
