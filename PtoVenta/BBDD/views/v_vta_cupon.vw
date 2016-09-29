create or replace force view ptoventa.v_vta_cupon as
select cod_grupo_cia,cod_cupon,estado,usu_crea_cup_cab,fec_crea_cup_cab,
usu_mod_cup_cab,fec_mod_cup_cab,cod_campana,cod_local,sec_cupon,
fec_procesa_matriz,usu_procesa_matriz,fec_ini,fec_fin,fecha_activacion,
usu_activacion,ip,num_doc_ident
from ptoventa.VTA_CUPON c
where (FEC_CREA_CUP_CAB>to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60)
or    (FEC_MOD_CUP_CAB >to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60 );

