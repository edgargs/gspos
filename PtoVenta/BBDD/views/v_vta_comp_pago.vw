create or replace force view ptoventa.v_vta_comp_pago as
select cod_grupo_cia, cod_local, num_ped_vta, sec_comp_pago, tip_comp_pago, num_comp_pago, sec_mov_caja, sec_mov_caja_anul, cant_item, cod_cli_local, nom_impr_comp, direc_impr_comp, num_doc_impr, val_bruto_comp_pago, val_neto_comp_pago, val_dcto_comp_pago, val_afecto_comp_pago, val_igv_comp_pago, val_redondeo_comp_pago, porc_igv_comp_pago, usu_crea_comp_pago, fec_crea_comp_pago, usu_mod_comp_pago, fec_mod_comp_pago, fec_anul_comp_pago, ind_comp_anul, num_pedido_anul, num_sec_doc_sap, fec_proceso_sap, num_sec_doc_sap_anul, fec_proceso_sap_anul, ind_reclamo_navsat, val_dcto_comp, motivo_anulacion, fecha_cobro, fecha_anulacion, tip_clien_convenio, val_copago_comp_pago, val_igv_comp_copago, num_comp_copago_ref, ind_afecta_kardex, pct_beneficiario, pct_empresa, ind_comp_credito, tip_comp_pago_ref, cod_tipo_convenio, ind_afecto_igv, cod_cliente_sap, old_num_sec_doc_sap, old_num_sec_doc_sap_anul,cod_tip_proc_pago,num_comp_pago_e from ptoventa.VTA_COMP_PAGO;

