--------------------------------------------------------
--  DDL for Package DBA_TAB_SUS_AUX
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."DBA_TAB_SUS_AUX" is
--autor jluna 20101021 por solicitud de desarrollo
procedure fill_aux_sustitutos(cCodGrupoCia_in in char,cCodLocal_in in char) ;
procedure fill_aux_complementarios(cCodGrupoCia_in in char,cCodLocal_in in char) ;
procedure fill_tablas_aux_nocturna(cCodGrupoCia_in in char);

end;

/
