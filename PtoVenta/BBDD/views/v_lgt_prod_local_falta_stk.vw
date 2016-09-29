CREATE OR REPLACE FORCE VIEW PTOVENTA.V_LGT_PROD_LOCAL_FALTA_STK AS
SELECT cod_grupo_cia, cod_local, cod_prod, sec_usu_local, usu_crea_prod_local_falta_stk, fec_crea_prod_local_falta_stk,
fec_envio_mail, fec_genera_ped_rep
FROM PTOVENTA.LGT_PROD_LOCAL_FALTA_STK
WHERE FEC_CREA_PROD_LOCAL_FALTA_STK>to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60;

