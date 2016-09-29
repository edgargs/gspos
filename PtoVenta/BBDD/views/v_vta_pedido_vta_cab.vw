create or replace force view ptoventa.v_vta_pedido_vta_cab as
select cod_grupo_cia, cod_local, num_ped_vta, cod_cli_local, sec_mov_caja, fec_ped_vta, val_bruto_ped_vta, val_neto_ped_vta,
val_redondeo_ped_vta, val_igv_ped_vta, val_dcto_ped_vta, tip_ped_vta, val_tip_cambio_ped_vta, num_ped_diario, cant_items_ped_vta,
est_ped_vta, tip_comp_pago, nom_cli_ped_vta, dir_cli_ped_vta, ruc_cli_ped_vta, usu_crea_ped_vta_cab, fec_crea_ped_vta_cab,
usu_mod_ped_vta_cab, fec_mod_ped_vta_cab, ind_pedido_anul, ind_distr_gratuita, cod_local_atencion, num_ped_vta_origen,
obs_forma_pago, obs_ped_vta, cod_dir, num_telefono, fec_ruteo_ped_vta_cab, fec_salida_local, fec_entrega_ped_vta_cab,
fec_retorno_local, cod_ruteador, cod_motorizado, ind_deliv_automatico, num_ped_rec, ind_conv_enteros, ind_ped_convenio,
cod_convenio, num_pedido_delivery, cod_local_procedencia, ip_pc, cod_rpta_recarga, ind_fid, motivo_anulacion, dni_cli,
ind_camp_acumulada, sec_usu_local, punto_llegada, ind_fp_fid_efectivo, ind_fp_fid_tarjeta,
cod_fp_fid_tarjeta, num_cmp, cod_cli_conv, cod_barra_conv, ind_conv_btl_mf, nombre, desc_tip_colegio, tipo_colegio,
cod_medico, comision_vta, name_pc_cob_ped, ip_cob_ped, dni_usu_local, fec_proceso_rac, fecha_proceso_nc_rac, fecha_proceso_anula_rac,
cod_cia, sec_comp_pago, tip_clien_convenio, referencia_ped_dlv, pct_beneficiario, con_pago_e, num_oc, desc_global_e,
ind_comp_manual, tip_comp_manual, serie_comp_manual, num_comp_manual, ind_envio_rimac, id_transaccion, numero_autorizacion,
pt_inicial, pt_acumulado, pt_redimido, pt_total, fec_proc_puntos, est_trx_orbis, num_tarj_puntos, num_ped_vta_uso_nc,
ahorro_camp ,ptos_ahorro_camp ,ahorro_puntos,ahorro_total,ahorro_pack, ptos_ahorro_pack
from ptoventa.vta_pedido_vta_cab c
where c.cod_grupo_cia='001'
and c.fec_ped_vta>= (case when to_char(sysdate,'hh24') <'01' then trunc(sysdate-1) else trunc(sysdate) end)
and (c.Fec_Crea_Ped_Vta_Cab>to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60
or
nvl(c.fec_mod_ped_vta_cab,to_date('20140101','yyyymmdd'))>to_date((select llave_tab_gral from ptoventa.pbl_tab_gral where id_tab_gral=377 ),'yyyymmdd hh24miss')-2/24/60 );

