--------------------------------------------------------
--  DDL for Package PTOVENTA_MATRIZ_MON_COMP_E
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_MATRIZ_MON_COMP_E" AS 

  
  PROCEDURE INSERTA_MON_COMP_E(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR);
  
  PROCEDURE EJECUTA_MON_COMP_E(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR);  
  
  PROCEDURE REPROCESO_COMP_E(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedVta_in IN CHAR, cSecCompPago_in IN CHAR);  

  PROCEDURE RECUPERA_COMP_E_PENDIENTE(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR);
	
END PTOVENTA_MATRIZ_MON_COMP_E;

/
