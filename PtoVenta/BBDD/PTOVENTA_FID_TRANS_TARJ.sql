--------------------------------------------------------
--  DDL for Package PTOVENTA_FID_TRANS_TARJ
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_FID_TRANS_TARJ" IS
--autor jluna 20080930
 procedure EJECT_TRANS_TARJ_CLI(cCodGrupoCia_in IN CHAR);
 PROCEDURE GET_ORIGEN_TRANS_TARJ(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);
 PROCEDURE set_destino_TRANS_TARJ(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);
 procedure merge_fid_tarjeta;
 procedure merge_pbl_cliente;
END;

/
