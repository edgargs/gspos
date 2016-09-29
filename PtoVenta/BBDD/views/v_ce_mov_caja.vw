create or replace force view ptoventa.v_ce_mov_caja as
select cod_grupo_cia, cod_local, sec_mov_caja, num_caja_pago, sec_usu_local, tip_mov_caja, fec_dia_vta,
num_turno_caja, fec_crea_mov_caja, usu_crea_mov_caja, fec_mod_mov_caja, usu_mod_mov_caja, cant_bol_emi,
mon_bol_emi, cant_fact_emi, mon_fact_emi, cant_guia_emi, mon_guia_emi, cant_bol_anu, mon_bol_anu, cant_fact_anu,
mon_fact_anu, cant_guia_anu, mon_guia_anu, cant_bol_tot, mon_bol_tot, cant_fact_tot, mon_fact_tot, cant_guia_tot,
mon_guia_tot, mon_tot_gen, mon_tot_anu, mon_tot, sec_mov_caja_origen, cant_nc_boletas, mon_nc_boletas, cant_nc_fact,
mon_nc_fact, mon_tot_nc, ip_mov_caja, ind_vb_qf, ind_vb_cajero, fec_cierre_turno_caja, desc_obs_cierre_turno, num_boleta_inicial,
num_boleta_final, num_factura_inicial, num_factura_final, ind_comp_validos, fec_cierre_dia_vta, ind_mov_caja,
cant_tick_emi, mon_tick_emi, cant_tick_anu, mon_tick_anu, cant_nc_tickets, mon_nc_tickets, cant_tick_tot, mon_tick_tot,
cant_tick_fac_emi, mon_tick_fac_emi, cant_tick_fac_anul, mon_tick_fack_anul, cant_nc_tickets_fac, mon_nc_tickets_fac,
cant_tick_fac_tot, mon_tick_fac_tot
from CE_MOV_CAJA mc
where mc.cod_grupo_cia='001'
and mc.fec_crea_mov_caja>= (case when to_char(sysdate,'hh24') <'01' then trunc(sysdate-1) else trunc(sysdate) end)
and (mc.fec_crea_mov_caja>to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60
or
nvl(mc.fec_mod_mov_caja,to_date('20140101','yyyymmdd'))>to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60 );

