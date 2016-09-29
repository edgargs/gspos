CREATE OR REPLACE TRIGGER PTOVENTA.TR_U_PBL_USU_LOCAL BEFORE UPDATE OF CLAVE_USU ON PBL_USU_LOCAL FOR EACH ROW
DECLARE BEGIN
   INSERT INTO pbl_usu_local_hist(cod_grupo_cia,cod_local,sec_usu_local,
   login_usu,cod_trab,cod_cia,nom_usu,
   ape_pat,ape_mat,clave_usu,telef_usu,
   direcc_usu,fec_nac,est_usu,fec_crea_usu_local,
   usu_crea_usu_local,fec_mod_usu_local,usu_mod_usu_local,
   ind_distr_gratuita,dni_usu,cod_trab_rrhh)
   VALUES(:OLD.cod_grupo_cia,:OLD.cod_local,:OLD.sec_usu_local,
   :OLD.login_usu,:OLD.cod_trab,:OLD.cod_cia,:OLD.nom_usu,
   :OLD.ape_pat,:OLD.ape_mat,:OLD.clave_usu,:OLD.telef_usu,
   :OLD.direcc_usu,:OLD.fec_nac,:OLD.est_usu,:OLD.fec_crea_usu_local,
   :OLD.usu_crea_usu_local,sysdate,:OLD.usu_mod_usu_local,
   :OLD.ind_distr_gratuita,:OLD.dni_usu,:OLD.cod_trab_rrhh);
   :NEW.fec_mod_usu_local:=sysdate;
END TR_U_PBL_USU_LOCAL ;
/

