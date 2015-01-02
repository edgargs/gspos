package mifarma.ptoventa.convenioBTLMF.dao;

import org.apache.ibatis.jdbc.SQL;

/**
 * Proveedor de sentencias
 * @author ERIOS
 * @since 2.4.4
 */
public class SQLConvenioBTLMFProvider {
    public SQLConvenioBTLMFProvider() {
        super();
    }
    
    public String selectPedidoCabLocal(){
        return selectTablaNumPedido("VTA_PEDIDO_VTA_CAB");
    }
    
    public String selectPedidoDetLocalTemp(){
        return selectTablaNumPedido("VTA_PEDIDO_VTA_DET_TEMP");
    }
    
    public String selectCompPagoLocalTemp(){
        return selectTablaNumPedido("VTA_COMP_PAGO_TEMP");
    }
    
    public String selectFormaPagoPedidoLocalTemp(){
        return selectTablaNumPedido("VTA_FORMA_PAGO_PEDIDO_TEMP");
    }
    
    public String selectConPedVtaLocal(){
        return selectTablaNumPedido("CON_BTL_MF_PED_VTA");
    }    
    
    public String insertPedidoCabRAC(){
        String sql = new SQL()
            .INSERT_INTO("BTLPROD.RAC_VTA_PEDIDO_VTA_CAB")
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_local","#{cod_local}")
            .VALUES("num_ped_vta","#{num_ped_vta}")
            .VALUES("cod_cli_local","#{cod_cli_local}")
            .VALUES("sec_mov_caja","#{sec_mov_caja}")
            .VALUES("fec_ped_vta","#{fec_ped_vta}")
            .VALUES("val_bruto_ped_vta","#{val_bruto_ped_vta}")
            .VALUES("val_neto_ped_vta","#{val_neto_ped_vta}")
            .VALUES("val_redondeo_ped_vta","#{val_redondeo_ped_vta}")
            .VALUES("val_igv_ped_vta","#{val_igv_ped_vta}")
            .VALUES("val_dcto_ped_vta","#{val_dcto_ped_vta}")
            .VALUES("tip_ped_vta","#{tip_ped_vta}")
            .VALUES("val_tip_cambio_ped_vta","#{val_tip_cambio_ped_vta}")
            .VALUES("num_ped_diario","#{num_ped_diario}")
            .VALUES("cant_items_ped_vta","#{cant_items_ped_vta}")
            .VALUES("est_ped_vta","#{est_ped_vta}")
            .VALUES("tip_comp_pago","#{tip_comp_pago}")
            .VALUES("nom_cli_ped_vta","#{nom_cli_ped_vta}")
            .VALUES("dir_cli_ped_vta","#{dir_cli_ped_vta}")
            .VALUES("ruc_cli_ped_vta","#{ruc_cli_ped_vta}")
            .VALUES("usu_crea_ped_vta_cab","#{usu_crea_ped_vta_cab}")
            .VALUES("fec_crea_ped_vta_cab","#{fec_crea_ped_vta_cab}")
            .VALUES("usu_mod_ped_vta_cab","#{usu_mod_ped_vta_cab}")
            .VALUES("fec_mod_ped_vta_cab","#{fec_mod_ped_vta_cab}")
            .VALUES("ind_pedido_anul","#{ind_pedido_anul}")
            .VALUES("ind_distr_gratuita","#{ind_distr_gratuita}")
            .VALUES("cod_local_atencion","#{cod_local_atencion}")
            .VALUES("num_ped_vta_origen","#{num_ped_vta_origen}")
            .VALUES("obs_forma_pago","#{obs_forma_pago}")
            .VALUES("obs_ped_vta","#{obs_ped_vta}")
            .VALUES("cod_dir","#{cod_dir}")
            .VALUES("num_telefono","#{num_telefono}")
            .VALUES("fec_ruteo_ped_vta_cab","#{fec_ruteo_ped_vta_cab}")
            .VALUES("fec_salida_local","#{fec_salida_local}")
            .VALUES("fec_entrega_ped_vta_cab","#{fec_entrega_ped_vta_cab}")
            .VALUES("fec_retorno_local","#{fec_retorno_local}")
            .VALUES("cod_ruteador","#{cod_ruteador}")
            .VALUES("cod_motorizado","#{cod_motorizado}")
            .VALUES("ind_deliv_automatico","#{ind_deliv_automatico}")
            .VALUES("num_ped_rec","#{num_ped_rec}")
            .VALUES("ind_conv_enteros","#{ind_conv_enteros}")
            .VALUES("ind_ped_convenio","#{ind_ped_convenio}")
            .VALUES("cod_convenio","#{cod_convenio}")
            .VALUES("num_pedido_delivery","#{num_pedido_delivery}")
            .VALUES("cod_local_procedencia","#{cod_local_procedencia}")
            .VALUES("ip_pc","#{ip_pc}")
            .VALUES("cod_rpta_recarga","#{cod_rpta_recarga}")
            .VALUES("ind_fid","#{ind_fid}")
            .VALUES("motivo_anulacion","#{motivo_anulacion}")
            .VALUES("dni_cli","#{dni_cli}")
            .VALUES("ind_camp_acumulada","#{ind_camp_acumulada}")
            .VALUES("fec_ini_cobro","#{fec_ini_cobro}")
            .VALUES("fec_fin_cobro","#{fec_fin_cobro}")
            .VALUES("sec_usu_local","#{sec_usu_local}")
            .VALUES("punto_llegada","#{punto_llegada}")
            .VALUES("ind_fp_fid_efectivo","#{ind_fp_fid_efectivo}")
            .VALUES("ind_fp_fid_tarjeta","#{ind_fp_fid_tarjeta}")
            .VALUES("cod_fp_fid_tarjeta","#{cod_fp_fid_tarjeta}")
            .VALUES("cod_cli_conv","#{cod_cli_conv}")
            .VALUES("cod_barra_conv","#{cod_barra_conv}")
            .VALUES("ind_conv_btl_mf","#{ind_conv_btl_mf}")
            .VALUES("name_pc_cob_ped","#{name_pc_cob_ped}")
            .VALUES("ip_cob_ped","#{ip_cob_ped}")
            .VALUES("dni_usu_local","#{dni_usu_local}")
            .VALUES("fec_proceso_rac","#{fec_proceso_rac}")
            .VALUES("fecha_proceso_anula_rac","#{fecha_proceso_anula_rac}")
            .toString();
        return sql;
    }

    public String insertPedidoDetRAC(){
        String sql = new SQL()
            .INSERT_INTO("BTLPROD.RAC_VTA_PEDIDO_VTA_DET")    
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_local","#{cod_local}")
            .VALUES("num_ped_vta","#{num_ped_vta}")
            .VALUES("sec_ped_vta_det","#{sec_ped_vta_det}")
            .VALUES("cod_prod","#{cod_prod}")
            .VALUES("cant_atendida","#{cant_atendida}")
            .VALUES("val_prec_vta","#{val_prec_vta}")
            .VALUES("val_prec_total","#{val_prec_total}")
            .VALUES("porc_dcto_1","#{porc_dcto_1}")
            .VALUES("porc_dcto_2","#{porc_dcto_2}")
            .VALUES("porc_dcto_3","#{porc_dcto_3}")
            .VALUES("porc_dcto_total","#{porc_dcto_total}")
            .VALUES("est_ped_vta_det","#{est_ped_vta_det}")
            .VALUES("val_total_bono","#{val_total_bono}")
            .VALUES("val_frac","#{val_frac}")
            .VALUES("sec_comp_pago","#{sec_comp_pago}")
            .VALUES("sec_usu_local","#{sec_usu_local}")
            .VALUES("usu_crea_ped_vta_det","#{usu_crea_ped_vta_det}")
            .VALUES("fec_crea_ped_vta_det","#{fec_crea_ped_vta_det}")
            .VALUES("usu_mod_ped_vta_det","#{usu_mod_ped_vta_det}")
            .VALUES("fec_mod_ped_vta_det","#{fec_mod_ped_vta_det}")
            .VALUES("val_prec_lista","#{val_prec_lista}")
            .VALUES("val_igv","#{val_igv}")
            .VALUES("unid_vta","#{unid_vta}")
            .VALUES("ind_exonerado_igv","#{ind_exonerado_igv}")
            .VALUES("sec_grupo_impr","#{sec_grupo_impr}")
            .VALUES("cant_usada_nc","#{cant_usada_nc}")
            .VALUES("sec_comp_pago_origen","#{sec_comp_pago_origen}")
            .VALUES("num_lote_prod","#{num_lote_prod}")
            .VALUES("fec_proceso_guia_rd","#{fec_proceso_guia_rd}")
            .VALUES("desc_num_tel_rec","#{desc_num_tel_rec}")
            .VALUES("val_num_trace","#{val_num_trace}")
            .VALUES("val_cod_aprobacion","#{val_cod_aprobacion}")
            .VALUES("desc_num_tarj_virtual","#{desc_num_tarj_virtual}")
            .VALUES("val_num_pin","#{val_num_pin}")
            .VALUES("fec_vencimiento_lote","#{fec_vencimiento_lote}")
            .VALUES("val_prec_public","#{val_prec_public}")
            .VALUES("ind_calculo_max_min","#{ind_calculo_max_min}")
            .VALUES("fec_exclusion","#{fec_exclusion}")
            .VALUES("fecha_tx","#{fecha_tx}")
            .VALUES("hora_tx","#{hora_tx}")
            .VALUES("cod_prom","#{cod_prom}")
            .VALUES("ind_origen_prod","#{ind_origen_prod}")
            .VALUES("val_frac_local","#{val_frac_local}")
            .VALUES("cant_frac_local","#{cant_frac_local}")
            .VALUES("cant_xdia_tra","#{cant_xdia_tra}")
            .VALUES("cant_dias_tra","#{cant_dias_tra}")
            .VALUES("ind_zan","#{ind_zan}")
            .VALUES("val_prec_prom","#{val_prec_prom}")
            .VALUES("datos_imp_virtual","#{datos_imp_virtual}")
            .VALUES("cod_camp_cupon","#{cod_camp_cupon}")
            .VALUES("ahorro","#{ahorro}")
            .VALUES("porc_dcto_calc","#{porc_dcto_calc}")
            .VALUES("porc_zan","#{porc_zan}")
            .VALUES("ind_prom_automatico","#{ind_prom_automatico}")
            .VALUES("ahorro_pack","#{ahorro_pack}")
            .VALUES("porc_dcto_calc_pack","#{porc_dcto_calc_pack}")
            .VALUES("cod_grupo_rep","#{cod_grupo_rep}")
            .VALUES("cod_grupo_rep_edmundo","#{cod_grupo_rep_edmundo}")
            .VALUES("sec_respaldo_stk","#{sec_respaldo_stk}")
            .VALUES("num_comp_pago","#{num_comp_pago}")
            .VALUES("sec_comp_pago_benef","#{sec_comp_pago_benef}")
            .VALUES("sec_comp_pago_empre","#{sec_comp_pago_empre}")
            .toString();
        return sql;
    }
    
    public String insertCompPagoRAC(){
        String sql = new SQL()
            .INSERT_INTO("BTLPROD.RAC_VTA_COMP_PAGO")   
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_local","#{cod_local}")
            .VALUES("num_ped_vta","#{num_ped_vta}")
            .VALUES("sec_comp_pago","#{sec_comp_pago}")
            .VALUES("tip_comp_pago","#{tip_comp_pago}")
            .VALUES("num_comp_pago","#{num_comp_pago}")
            .VALUES("sec_mov_caja","#{sec_mov_caja}")
            .VALUES("sec_mov_caja_anul","#{sec_mov_caja_anul}")
            .VALUES("cant_item","#{cant_item}")
            .VALUES("cod_cli_local","#{cod_cli_local}")
            .VALUES("nom_impr_comp","#{nom_impr_comp}")
            .VALUES("direc_impr_comp","#{direc_impr_comp}")
            .VALUES("num_doc_impr","#{num_doc_impr}")
            .VALUES("val_bruto_comp_pago","#{val_bruto_comp_pago}")
            .VALUES("val_neto_comp_pago","#{val_neto_comp_pago}")
            .VALUES("val_dcto_comp_pago","#{val_dcto_comp_pago}")
            .VALUES("val_afecto_comp_pago","#{val_afecto_comp_pago}")
            .VALUES("val_igv_comp_pago","#{val_igv_comp_pago}")
            .VALUES("val_redondeo_comp_pago","#{val_redondeo_comp_pago}")
            .VALUES("porc_igv_comp_pago","#{porc_igv_comp_pago}")
            .VALUES("usu_crea_comp_pago","#{usu_crea_comp_pago}")
            .VALUES("fec_crea_comp_pago","#{fec_crea_comp_pago}")
            .VALUES("usu_mod_comp_pago","#{usu_mod_comp_pago}")
            .VALUES("fec_mod_comp_pago","#{fec_mod_comp_pago}")
            .VALUES("fec_anul_comp_pago","#{fec_anul_comp_pago}")
            .VALUES("ind_comp_anul","#{ind_comp_anul}")
            .VALUES("num_pedido_anul","#{num_pedido_anul}")
            .VALUES("num_sec_doc_sap","#{num_sec_doc_sap}")
            .VALUES("fec_proceso_sap","#{fec_proceso_sap}")
            .VALUES("num_sec_doc_sap_anul","#{num_sec_doc_sap_anul}")
            .VALUES("fec_proceso_sap_anul","#{fec_proceso_sap_anul}")
            .VALUES("ind_reclamo_navsat","#{ind_reclamo_navsat}")
            .VALUES("val_dcto_comp","#{val_dcto_comp}")
            .VALUES("motivo_anulacion","#{motivo_anulacion}")
            .VALUES("fecha_cobro","#{fecha_cobro}")
            .VALUES("fecha_anulacion","#{fecha_anulacion}")
            .VALUES("fech_imp_cobro","#{fech_imp_cobro}")
            .VALUES("fech_imp_anul","#{fech_imp_anul}")
            .VALUES("tip_clien_convenio","#{tip_clien_convenio}")
            .VALUES("val_copago_comp_pago","#{val_copago_comp_pago}")
            .VALUES("val_igv_comp_copago","#{val_igv_comp_copago}")
            .VALUES("num_comp_copago_ref","#{num_comp_copago_ref}")
            .VALUES("ind_afecta_kardex","#{ind_afecta_kardex}")
            .VALUES("pct_beneficiario","#{pct_beneficiario}")
            .VALUES("pct_empresa","#{pct_empresa}")
            .VALUES("ind_comp_credito","#{ind_comp_credito}")
            .VALUES("tip_comp_pago_ref","#{tip_comp_pago_ref}")
            .toString();
        return sql;
    }
    
    public String insertFormaPagoPedidoRAC(){
        String sql = new SQL()
            .INSERT_INTO("BTLPROD.RAC_VTA_FORMA_PAGO_PEDIDO")    
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_local","#{cod_local}")
            .VALUES("cod_forma_pago","#{cod_forma_pago}")
            .VALUES("num_ped_vta","#{num_ped_vta}")
            .VALUES("im_pago","#{im_pago}")
            .VALUES("tip_moneda","#{tip_moneda}")
            .VALUES("val_tip_cambio","#{val_tip_cambio}")
            .VALUES("val_vuelto","#{val_vuelto}")
            .VALUES("im_total_pago","#{im_total_pago}")
            .VALUES("num_tarj","#{num_tarj}")
            .VALUES("fec_venc_tarj","#{fec_venc_tarj}")
            .VALUES("nom_tarj","#{nom_tarj}")
            .VALUES("fec_crea_forma_pago_ped","#{fec_crea_forma_pago_ped}")
            .VALUES("usu_crea_forma_pago_ped","#{usu_crea_forma_pago_ped}")
            .VALUES("fec_mod_forma_pago_ped","#{fec_mod_forma_pago_ped}")
            .VALUES("usu_mod_forma_pago_ped","#{usu_mod_forma_pago_ped}")
            .VALUES("cant_cupon","#{cant_cupon}")
            .VALUES("tipo_autorizacion","#{tipo_autorizacion}")
            .VALUES("cod_lote","#{cod_lote}")
            .VALUES("cod_autorizacion","#{cod_autorizacion}")
            .VALUES("dni_cli_tarj","#{dni_cli_tarj}")
            .toString();
        return sql;
    }
    
    public String insertConPedVtaRAC(){
        String sql = new SQL()
            .INSERT_INTO("BTLPROD.RAC_CON_BTL_MF_PED_VTA")    
            .VALUES("cod_grupo_cia","#{cod_grupo_cia}")
            .VALUES("cod_local","#{cod_local}")
            .VALUES("num_ped_vta","#{num_ped_vta}")
            .VALUES("cod_campo","#{cod_campo}")
            .VALUES("cod_convenio","#{cod_convenio}")
            .VALUES("cod_cliente","#{cod_cliente}")
            .VALUES("fec_crea_ped_vta_cli","#{fec_crea_ped_vta_cli}")
            .VALUES("usu_crea_ped_vta_cli","#{usu_crea_ped_vta_cli}")
            .VALUES("fec_mod_ped_vta_cli","#{fec_mod_ped_vta_cli}")
            .VALUES("usu_mod_ped_vta_cli","#{usu_mod_ped_vta_cli}")
            .VALUES("descripcion_campo","#{descripcion_campo}")
            .VALUES("nombre_campo","#{nombre_campo}")
            .VALUES("flg_imprime","#{flg_imprime}")
            .VALUES("cod_valor_in","#{cod_valor_in}")
            .toString();
        return sql;
    }
    
    public String selectPedidoDetLocalNC(){
        return selectTablaNumPedido("VTA_PEDIDO_VTA_DET_TEMP");
    }
    
    public String selectCompPagoLocalNC(){
        return selectTablaNumPedido("VTA_COMP_PAGO_TEMP");
    }
    
    public String selectFormaPagoPedidoLocalNC(){
        return selectTablaNumPedido("VTA_FORMA_PAGO_PEDIDO_TEMP");
    }
    
    private String selectTablaNumPedido(String pTabla){
        return new SQL()
            .SELECT("*")
            .FROM(pTabla)
            .WHERE("COD_GRUPO_CIA = #{cCodGrupoCia}")
            .WHERE("COD_LOCAL = #{cCodLocal}")
            .WHERE("NUM_PED_VTA = #{cNumPedVta}")
        .toString();
    }    

    public String selectPedidoDetLocal(){
        return selectTablaNumPedido("VTA_PEDIDO_VTA_DET");
    }
    
    public String selectCompPagoLocal(){
        return selectTablaNumPedido("VTA_COMP_PAGO");
    }
    
    public String selectFormaPagoPedidoLocal(){
        return selectTablaNumPedido("VTA_FORMA_PAGO_PEDIDO");
    }
    
    private String deleteTablaPedidoRAC(String pTabla){
        return new SQL()
            .DELETE_FROM(pTabla)
            .WHERE("COD_GRUPO_CIA = #{cCodGrupoCia}")
            .WHERE("COD_LOCAL = #{cCodLocal}")
            .WHERE("NUM_PED_VTA = #{cNumPedVta}")
        .toString();
    }
    
    public String deletePedidoCabRAC(){
        return deleteTablaPedidoRAC("BTLPROD.RAC_VTA_PEDIDO_VTA_CAB");
    }
    
    public String deletePedidoDetRAC(){
        return deleteTablaPedidoRAC("BTLPROD.RAC_VTA_PEDIDO_VTA_DET");
    }
    
    public String deletePedidoCompPagoRAC(){
        return deleteTablaPedidoRAC("BTLPROD.RAC_VTA_COMP_PAGO");
    }
    
    public String deletePedidoFormaPagoRAC(){
        return deleteTablaPedidoRAC("BTLPROD.RAC_VTA_FORMA_PAGO_PEDIDO");
    }
    
    public String deletePedidoDatosConvRAC(){
        return deleteTablaPedidoRAC("BTLPROD.RAC_CON_BTL_MF_PED_VTA");
    }
    
}
