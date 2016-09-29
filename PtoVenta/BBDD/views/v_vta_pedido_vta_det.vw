create or replace force view ptoventa.v_vta_pedido_vta_det as
select cod_grupo_cia, cod_local, num_ped_vta, sec_ped_vta_det, cod_prod, cant_atendida, val_prec_vta, val_prec_total,
porc_dcto_1, porc_dcto_2, porc_dcto_3, porc_dcto_total, est_ped_vta_det, val_total_bono, val_frac, sec_comp_pago,
sec_usu_local, usu_crea_ped_vta_det, fec_crea_ped_vta_det, usu_mod_ped_vta_det, fec_mod_ped_vta_det, val_prec_lista,
val_igv, unid_vta, ind_exonerado_igv, sec_grupo_impr, cant_usada_nc, sec_comp_pago_origen, num_lote_prod, fec_proceso_guia_rd,
desc_num_tel_rec, val_num_trace, val_cod_aprobacion, desc_num_tarj_virtual, val_num_pin, fec_vencimiento_lote,
val_prec_public, ind_calculo_max_min, fec_exclusion, fecha_tx, hora_tx, cod_prom, ind_origen_prod, val_frac_local,
cant_frac_local, cant_xdia_tra, cant_dias_tra, ind_zan, val_prec_prom, datos_imp_virtual, cod_camp_cupon, ahorro, porc_dcto_calc,
porc_zan, ind_prom_automatico, ahorro_pack, porc_dcto_calc_pack, cod_grupo_rep, cod_grupo_rep_edmundo, sec_respaldo_stk,
sec_comp_pago_benef, sec_comp_pago_empre, num_comp_pago, ahorro_conv, val_prec_total_empre, val_prec_total_benef, tip_clien_convenio,
cod_tip_afec_igv_e, cod_tip_prec_vta_e, val_prec_vta_unit_e, cant_unid_vdd_e, val_vta_item_e, val_total_igv_item_e,
val_total_desc_item_e, desc_item_e, val_vta_unit_item_e, dni_rimac, ind_prod_mas_1, ctd_puntos_acum, cod_prod_puntos,
ind_bonificado, factor_puntos, ahorro_puntos, ahorro_camp, ptos_ahorro, porc_com_ate_cli
from ptoventa.vta_pedido_vta_det;

