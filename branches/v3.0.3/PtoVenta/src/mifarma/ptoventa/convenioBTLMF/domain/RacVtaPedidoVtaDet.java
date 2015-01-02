package mifarma.ptoventa.convenioBTLMF.domain;

import java.util.Date;

/**
 * Entidad RAC_VTA_PEDIDO_VTA_DET
 * @author ERIOS
 * @since 2.4.4
 */
public class RacVtaPedidoVtaDet {

    private String cod_grupo_cia;
    private String cod_local;
    private String num_ped_vta;
    private Integer sec_ped_vta_det;
    private String cod_prod;
    private Integer cant_atendida;
    private Double val_prec_vta;
    private Double val_prec_total;
    private Double porc_dcto_1;
    private Double porc_dcto_2;
    private Double porc_dcto_3;
    private Double porc_dcto_total;
    private String est_ped_vta_det;
    private Double val_total_bono;
    private Integer val_frac;
    private String sec_comp_pago;
    private String sec_usu_local;
    private String usu_crea_ped_vta_det;
    private Date fec_crea_ped_vta_det;
    private String usu_mod_ped_vta_det;
    private Date fec_mod_ped_vta_det;
    private Double val_prec_lista;
    private Double val_igv;
    private String unid_vta;
    private String ind_exonerado_igv;
    private Integer sec_grupo_impr;
    private Integer cant_usada_nc;
    private String sec_comp_pago_origen;
    private String num_lote_prod;
    private Date fec_proceso_guia_rd;
    private String desc_num_tel_rec;
    private String val_num_trace;
    private String val_cod_aprobacion;
    private String desc_num_tarj_virtual;
    private String val_num_pin;
    private Date fec_vencimiento_lote;
    private Double val_prec_public;
    private String ind_calculo_max_min;
    private Date fec_exclusion;
    private String fecha_tx;
    private String hora_tx;
    private String cod_prom;
    private String ind_origen_prod;
    private Integer val_frac_local;
    private Integer cant_frac_local;
    private Integer cant_xdia_tra;
    private Integer cant_dias_tra;
    private String ind_zan;
    private Double val_prec_prom;
    private String datos_imp_virtual;
    private String cod_camp_cupon;
    private Double ahorro;
    private Double porc_dcto_calc;
    private Double porc_zan;
    private String ind_prom_automatico;
    private Double ahorro_pack;
    private Double porc_dcto_calc_pack;
    private String cod_grupo_rep;
    private String cod_grupo_rep_edmundo;
    private Integer sec_respaldo_stk;
    private String num_comp_pago;
    private String sec_comp_pago_benef;
    private String sec_comp_pago_empre;

    public RacVtaPedidoVtaDet() {
        super();
    }

    public void setCod_grupo_cia(String cod_grupo_cia) {
        this.cod_grupo_cia = cod_grupo_cia;
    }

    public String getCod_grupo_cia() {
        return cod_grupo_cia;
    }

    public void setCod_local(String cod_local) {
        this.cod_local = cod_local;
    }

    public String getCod_local() {
        return cod_local;
    }

    public void setNum_ped_vta(String num_ped_vta) {
        this.num_ped_vta = num_ped_vta;
    }

    public String getNum_ped_vta() {
        return num_ped_vta;
    }

    public void setSec_ped_vta_det(Integer sec_ped_vta_det) {
        this.sec_ped_vta_det = sec_ped_vta_det;
    }

    public Integer getSec_ped_vta_det() {
        return sec_ped_vta_det;
    }

    public void setCod_prod(String cod_prod) {
        this.cod_prod = cod_prod;
    }

    public String getCod_prod() {
        return cod_prod;
    }

    public void setCant_atendida(Integer cant_atendida) {
        this.cant_atendida = cant_atendida;
    }

    public Integer getCant_atendida() {
        return cant_atendida;
    }

    public void setVal_prec_vta(Double val_prec_vta) {
        this.val_prec_vta = val_prec_vta;
    }

    public Double getVal_prec_vta() {
        return val_prec_vta;
    }

    public void setVal_prec_total(Double val_prec_total) {
        this.val_prec_total = val_prec_total;
    }

    public Double getVal_prec_total() {
        return val_prec_total;
    }

    public void setPorc_dcto_1(Double porc_dcto_1) {
        this.porc_dcto_1 = porc_dcto_1;
    }

    public Double getPorc_dcto_1() {
        return porc_dcto_1;
    }

    public void setPorc_dcto_2(Double porc_dcto_2) {
        this.porc_dcto_2 = porc_dcto_2;
    }

    public Double getPorc_dcto_2() {
        return porc_dcto_2;
    }

    public void setPorc_dcto_3(Double porc_dcto_3) {
        this.porc_dcto_3 = porc_dcto_3;
    }

    public Double getPorc_dcto_3() {
        return porc_dcto_3;
    }

    public void setPorc_dcto_total(Double porc_dcto_total) {
        this.porc_dcto_total = porc_dcto_total;
    }

    public Double getPorc_dcto_total() {
        return porc_dcto_total;
    }

    public void setEst_ped_vta_det(String est_ped_vta_det) {
        this.est_ped_vta_det = est_ped_vta_det;
    }

    public String getEst_ped_vta_det() {
        return est_ped_vta_det;
    }

    public void setVal_total_bono(Double val_total_bono) {
        this.val_total_bono = val_total_bono;
    }

    public Double getVal_total_bono() {
        return val_total_bono;
    }

    public void setVal_frac(Integer val_frac) {
        this.val_frac = val_frac;
    }

    public Integer getVal_frac() {
        return val_frac;
    }

    public void setSec_comp_pago(String sec_comp_pago) {
        this.sec_comp_pago = sec_comp_pago;
    }

    public String getSec_comp_pago() {
        return sec_comp_pago;
    }

    public void setSec_usu_local(String sec_usu_local) {
        this.sec_usu_local = sec_usu_local;
    }

    public String getSec_usu_local() {
        return sec_usu_local;
    }

    public void setUsu_crea_ped_vta_det(String usu_crea_ped_vta_det) {
        this.usu_crea_ped_vta_det = usu_crea_ped_vta_det;
    }

    public String getUsu_crea_ped_vta_det() {
        return usu_crea_ped_vta_det;
    }

    public void setFec_crea_ped_vta_det(Date fec_crea_ped_vta_det) {
        this.fec_crea_ped_vta_det = fec_crea_ped_vta_det;
    }

    public Date getFec_crea_ped_vta_det() {
        return fec_crea_ped_vta_det;
    }

    public void setUsu_mod_ped_vta_det(String usu_mod_ped_vta_det) {
        this.usu_mod_ped_vta_det = usu_mod_ped_vta_det;
    }

    public String getUsu_mod_ped_vta_det() {
        return usu_mod_ped_vta_det;
    }

    public void setFec_mod_ped_vta_det(Date fec_mod_ped_vta_det) {
        this.fec_mod_ped_vta_det = fec_mod_ped_vta_det;
    }

    public Date getFec_mod_ped_vta_det() {
        return fec_mod_ped_vta_det;
    }

    public void setVal_prec_lista(Double val_prec_lista) {
        this.val_prec_lista = val_prec_lista;
    }

    public Double getVal_prec_lista() {
        return val_prec_lista;
    }

    public void setVal_igv(Double val_igv) {
        this.val_igv = val_igv;
    }

    public Double getVal_igv() {
        return val_igv;
    }

    public void setUnid_vta(String unid_vta) {
        this.unid_vta = unid_vta;
    }

    public String getUnid_vta() {
        return unid_vta;
    }

    public void setInd_exonerado_igv(String ind_exonerado_igv) {
        this.ind_exonerado_igv = ind_exonerado_igv;
    }

    public String getInd_exonerado_igv() {
        return ind_exonerado_igv;
    }

    public void setSec_grupo_impr(Integer sec_grupo_impr) {
        this.sec_grupo_impr = sec_grupo_impr;
    }

    public Integer getSec_grupo_impr() {
        return sec_grupo_impr;
    }

    public void setCant_usada_nc(Integer cant_usada_nc) {
        this.cant_usada_nc = cant_usada_nc;
    }

    public Integer getCant_usada_nc() {
        return cant_usada_nc;
    }

    public void setSec_comp_pago_origen(String sec_comp_pago_origen) {
        this.sec_comp_pago_origen = sec_comp_pago_origen;
    }

    public String getSec_comp_pago_origen() {
        return sec_comp_pago_origen;
    }

    public void setNum_lote_prod(String num_lote_prod) {
        this.num_lote_prod = num_lote_prod;
    }

    public String getNum_lote_prod() {
        return num_lote_prod;
    }

    public void setFec_proceso_guia_rd(Date fec_proceso_guia_rd) {
        this.fec_proceso_guia_rd = fec_proceso_guia_rd;
    }

    public Date getFec_proceso_guia_rd() {
        return fec_proceso_guia_rd;
    }

    public void setDesc_num_tel_rec(String desc_num_tel_rec) {
        this.desc_num_tel_rec = desc_num_tel_rec;
    }

    public String getDesc_num_tel_rec() {
        return desc_num_tel_rec;
    }

    public void setVal_num_trace(String val_num_trace) {
        this.val_num_trace = val_num_trace;
    }

    public String getVal_num_trace() {
        return val_num_trace;
    }

    public void setVal_cod_aprobacion(String val_cod_aprobacion) {
        this.val_cod_aprobacion = val_cod_aprobacion;
    }

    public String getVal_cod_aprobacion() {
        return val_cod_aprobacion;
    }

    public void setDesc_num_tarj_virtual(String desc_num_tarj_virtual) {
        this.desc_num_tarj_virtual = desc_num_tarj_virtual;
    }

    public String getDesc_num_tarj_virtual() {
        return desc_num_tarj_virtual;
    }

    public void setVal_num_pin(String val_num_pin) {
        this.val_num_pin = val_num_pin;
    }

    public String getVal_num_pin() {
        return val_num_pin;
    }

    public void setFec_vencimiento_lote(Date fec_vencimiento_lote) {
        this.fec_vencimiento_lote = fec_vencimiento_lote;
    }

    public Date getFec_vencimiento_lote() {
        return fec_vencimiento_lote;
    }

    public void setVal_prec_public(Double val_prec_public) {
        this.val_prec_public = val_prec_public;
    }

    public Double getVal_prec_public() {
        return val_prec_public;
    }

    public void setInd_calculo_max_min(String ind_calculo_max_min) {
        this.ind_calculo_max_min = ind_calculo_max_min;
    }

    public String getInd_calculo_max_min() {
        return ind_calculo_max_min;
    }

    public void setFec_exclusion(Date fec_exclusion) {
        this.fec_exclusion = fec_exclusion;
    }

    public Date getFec_exclusion() {
        return fec_exclusion;
    }

    public void setFecha_tx(String fecha_tx) {
        this.fecha_tx = fecha_tx;
    }

    public String getFecha_tx() {
        return fecha_tx;
    }

    public void setHora_tx(String hora_tx) {
        this.hora_tx = hora_tx;
    }

    public String getHora_tx() {
        return hora_tx;
    }

    public void setCod_prom(String cod_prom) {
        this.cod_prom = cod_prom;
    }

    public String getCod_prom() {
        return cod_prom;
    }

    public void setInd_origen_prod(String ind_origen_prod) {
        this.ind_origen_prod = ind_origen_prod;
    }

    public String getInd_origen_prod() {
        return ind_origen_prod;
    }

    public void setVal_frac_local(Integer val_frac_local) {
        this.val_frac_local = val_frac_local;
    }

    public Integer getVal_frac_local() {
        return val_frac_local;
    }

    public void setCant_frac_local(Integer cant_frac_local) {
        this.cant_frac_local = cant_frac_local;
    }

    public Integer getCant_frac_local() {
        return cant_frac_local;
    }

    public void setCant_xdia_tra(Integer cant_xdia_tra) {
        this.cant_xdia_tra = cant_xdia_tra;
    }

    public Integer getCant_xdia_tra() {
        return cant_xdia_tra;
    }

    public void setCant_dias_tra(Integer cant_dias_tra) {
        this.cant_dias_tra = cant_dias_tra;
    }

    public Integer getCant_dias_tra() {
        return cant_dias_tra;
    }

    public void setInd_zan(String ind_zan) {
        this.ind_zan = ind_zan;
    }

    public String getInd_zan() {
        return ind_zan;
    }

    public void setVal_prec_prom(Double val_prec_prom) {
        this.val_prec_prom = val_prec_prom;
    }

    public Double getVal_prec_prom() {
        return val_prec_prom;
    }

    public void setDatos_imp_virtual(String datos_imp_virtual) {
        this.datos_imp_virtual = datos_imp_virtual;
    }

    public String getDatos_imp_virtual() {
        return datos_imp_virtual;
    }

    public void setCod_camp_cupon(String cod_camp_cupon) {
        this.cod_camp_cupon = cod_camp_cupon;
    }

    public String getCod_camp_cupon() {
        return cod_camp_cupon;
    }

    public void setAhorro(Double ahorro) {
        this.ahorro = ahorro;
    }

    public Double getAhorro() {
        return ahorro;
    }

    public void setPorc_dcto_calc(Double porc_dcto_calc) {
        this.porc_dcto_calc = porc_dcto_calc;
    }

    public Double getPorc_dcto_calc() {
        return porc_dcto_calc;
    }

    public void setPorc_zan(Double porc_zan) {
        this.porc_zan = porc_zan;
    }

    public Double getPorc_zan() {
        return porc_zan;
    }

    public void setInd_prom_automatico(String ind_prom_automatico) {
        this.ind_prom_automatico = ind_prom_automatico;
    }

    public String getInd_prom_automatico() {
        return ind_prom_automatico;
    }

    public void setAhorro_pack(Double ahorro_pack) {
        this.ahorro_pack = ahorro_pack;
    }

    public Double getAhorro_pack() {
        return ahorro_pack;
    }

    public void setPorc_dcto_calc_pack(Double porc_dcto_calc_pack) {
        this.porc_dcto_calc_pack = porc_dcto_calc_pack;
    }

    public Double getPorc_dcto_calc_pack() {
        return porc_dcto_calc_pack;
    }

    public void setCod_grupo_rep(String cod_grupo_rep) {
        this.cod_grupo_rep = cod_grupo_rep;
    }

    public String getCod_grupo_rep() {
        return cod_grupo_rep;
    }

    public void setCod_grupo_rep_edmundo(String cod_grupo_rep_edmundo) {
        this.cod_grupo_rep_edmundo = cod_grupo_rep_edmundo;
    }

    public String getCod_grupo_rep_edmundo() {
        return cod_grupo_rep_edmundo;
    }

    public void setSec_respaldo_stk(Integer sec_respaldo_stk) {
        this.sec_respaldo_stk = sec_respaldo_stk;
    }

    public Integer getSec_respaldo_stk() {
        return sec_respaldo_stk;
    }

    public void setNum_comp_pago(String num_comp_pago) {
        this.num_comp_pago = num_comp_pago;
    }

    public String getNum_comp_pago() {
        return num_comp_pago;
    }

    public void setSec_comp_pago_benef(String sec_comp_pago_benef) {
        this.sec_comp_pago_benef = sec_comp_pago_benef;
    }

    public String getSec_comp_pago_benef() {
        return sec_comp_pago_benef;
    }

    public void setSec_comp_pago_empre(String sec_comp_pago_empre) {
        this.sec_comp_pago_empre = sec_comp_pago_empre;
    }

    public String getSec_comp_pago_empre() {
        return sec_comp_pago_empre;
    }
}
