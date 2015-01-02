package mifarma.ptoventa.convenioBTLMF.domain;

import java.util.Date;

/**
 * Entidad RAC_VTA_COMP_PAGO
 * @author ERIOS
 * @since 2.4.4
 */
public class RacVtaCompPago {

    private String cod_grupo_cia;
    private String cod_local;
    private String num_ped_vta;
    private String sec_comp_pago;
    private String tip_comp_pago;
    private String num_comp_pago;
    private String sec_mov_caja;
    private String sec_mov_caja_anul;
    private Integer cant_item;
    private String cod_cli_local;
    private String nom_impr_comp;
    private String direc_impr_comp;
    private String num_doc_impr;
    private Double val_bruto_comp_pago;
    private Double val_neto_comp_pago;
    private Double val_dcto_comp_pago;
    private Double val_afecto_comp_pago;
    private Double val_igv_comp_pago;
    private Double val_redondeo_comp_pago;
    private Double porc_igv_comp_pago;
    private String usu_crea_comp_pago;
    private Date fec_crea_comp_pago;
    private String usu_mod_comp_pago;
    private Date fec_mod_comp_pago;
    private Date fec_anul_comp_pago;
    private String ind_comp_anul;
    private String num_pedido_anul;
    private Integer num_sec_doc_sap;
    private Date fec_proceso_sap;
    private Integer num_sec_doc_sap_anul;
    private Date fec_proceso_sap_anul;
    private String ind_reclamo_navsat;
    private Double val_dcto_comp;
    private String motivo_anulacion;
    private Date fecha_cobro;
    private Date fecha_anulacion;
    private Date fech_imp_cobro;
    private Date fech_imp_anul;
    private String tip_clien_convenio;
    private Double val_copago_comp_pago;
    private Double val_igv_comp_copago;
    private String num_comp_copago_ref;
    private String ind_afecta_kardex;
    private Double pct_beneficiario;
    private Double pct_empresa;
    private String ind_comp_credito;
    private String tip_comp_pago_ref;
    private String num_comp_pago_e;
    private String cod_tip_proc_pago;

    public RacVtaCompPago() {
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

    public void setSec_comp_pago(String sec_comp_pago) {
        this.sec_comp_pago = sec_comp_pago;
    }

    public String getSec_comp_pago() {
        return sec_comp_pago;
    }

    public void setTip_comp_pago(String tip_comp_pago) {
        this.tip_comp_pago = tip_comp_pago;
    }

    public String getTip_comp_pago() {
        return tip_comp_pago;
    }

    public void setNum_comp_pago(String num_comp_pago) {
        this.num_comp_pago = num_comp_pago;
    }

    public String getNum_comp_pago() {
        return num_comp_pago;
    }

    public void setSec_mov_caja(String sec_mov_caja) {
        this.sec_mov_caja = sec_mov_caja;
    }

    public String getSec_mov_caja() {
        return sec_mov_caja;
    }

    public void setSec_mov_caja_anul(String sec_mov_caja_anul) {
        this.sec_mov_caja_anul = sec_mov_caja_anul;
    }

    public String getSec_mov_caja_anul() {
        return sec_mov_caja_anul;
    }

    public void setCant_item(Integer cant_item) {
        this.cant_item = cant_item;
    }

    public Integer getCant_item() {
        return cant_item;
    }

    public void setCod_cli_local(String cod_cli_local) {
        this.cod_cli_local = cod_cli_local;
    }

    public String getCod_cli_local() {
        return cod_cli_local;
    }

    public void setNom_impr_comp(String nom_impr_comp) {
        this.nom_impr_comp = nom_impr_comp;
    }

    public String getNom_impr_comp() {
        return nom_impr_comp;
    }

    public void setDirec_impr_comp(String direc_impr_comp) {
        this.direc_impr_comp = direc_impr_comp;
    }

    public String getDirec_impr_comp() {
        return direc_impr_comp;
    }

    public void setNum_doc_impr(String num_doc_impr) {
        this.num_doc_impr = num_doc_impr;
    }

    public String getNum_doc_impr() {
        return num_doc_impr;
    }

    public void setVal_bruto_comp_pago(Double val_bruto_comp_pago) {
        this.val_bruto_comp_pago = val_bruto_comp_pago;
    }

    public Double getVal_bruto_comp_pago() {
        return val_bruto_comp_pago;
    }

    public void setVal_neto_comp_pago(Double val_neto_comp_pago) {
        this.val_neto_comp_pago = val_neto_comp_pago;
    }

    public Double getVal_neto_comp_pago() {
        return val_neto_comp_pago;
    }

    public void setVal_dcto_comp_pago(Double val_dcto_comp_pago) {
        this.val_dcto_comp_pago = val_dcto_comp_pago;
    }

    public Double getVal_dcto_comp_pago() {
        return val_dcto_comp_pago;
    }

    public void setVal_afecto_comp_pago(Double val_afecto_comp_pago) {
        this.val_afecto_comp_pago = val_afecto_comp_pago;
    }

    public Double getVal_afecto_comp_pago() {
        return val_afecto_comp_pago;
    }

    public void setVal_igv_comp_pago(Double val_igv_comp_pago) {
        this.val_igv_comp_pago = val_igv_comp_pago;
    }

    public Double getVal_igv_comp_pago() {
        return val_igv_comp_pago;
    }

    public void setVal_redondeo_comp_pago(Double val_redondeo_comp_pago) {
        this.val_redondeo_comp_pago = val_redondeo_comp_pago;
    }

    public Double getVal_redondeo_comp_pago() {
        return val_redondeo_comp_pago;
    }

    public void setPorc_igv_comp_pago(Double porc_igv_comp_pago) {
        this.porc_igv_comp_pago = porc_igv_comp_pago;
    }

    public Double getPorc_igv_comp_pago() {
        return porc_igv_comp_pago;
    }

    public void setUsu_crea_comp_pago(String usu_crea_comp_pago) {
        this.usu_crea_comp_pago = usu_crea_comp_pago;
    }

    public String getUsu_crea_comp_pago() {
        return usu_crea_comp_pago;
    }

    public void setFec_crea_comp_pago(Date fec_crea_comp_pago) {
        this.fec_crea_comp_pago = fec_crea_comp_pago;
    }

    public Date getFec_crea_comp_pago() {
        return fec_crea_comp_pago;
    }

    public void setUsu_mod_comp_pago(String usu_mod_comp_pago) {
        this.usu_mod_comp_pago = usu_mod_comp_pago;
    }

    public String getUsu_mod_comp_pago() {
        return usu_mod_comp_pago;
    }

    public void setFec_mod_comp_pago(Date fec_mod_comp_pago) {
        this.fec_mod_comp_pago = fec_mod_comp_pago;
    }

    public Date getFec_mod_comp_pago() {
        return fec_mod_comp_pago;
    }

    public void setFec_anul_comp_pago(Date fec_anul_comp_pago) {
        this.fec_anul_comp_pago = fec_anul_comp_pago;
    }

    public Date getFec_anul_comp_pago() {
        return fec_anul_comp_pago;
    }

    public void setInd_comp_anul(String ind_comp_anul) {
        this.ind_comp_anul = ind_comp_anul;
    }

    public String getInd_comp_anul() {
        return ind_comp_anul;
    }

    public void setNum_pedido_anul(String num_pedido_anul) {
        this.num_pedido_anul = num_pedido_anul;
    }

    public String getNum_pedido_anul() {
        return num_pedido_anul;
    }

    public void setNum_sec_doc_sap(Integer num_sec_doc_sap) {
        this.num_sec_doc_sap = num_sec_doc_sap;
    }

    public Integer getNum_sec_doc_sap() {
        return num_sec_doc_sap;
    }

    public void setFec_proceso_sap(Date fec_proceso_sap) {
        this.fec_proceso_sap = fec_proceso_sap;
    }

    public Date getFec_proceso_sap() {
        return fec_proceso_sap;
    }

    public void setNum_sec_doc_sap_anul(Integer num_sec_doc_sap_anul) {
        this.num_sec_doc_sap_anul = num_sec_doc_sap_anul;
    }

    public Integer getNum_sec_doc_sap_anul() {
        return num_sec_doc_sap_anul;
    }

    public void setFec_proceso_sap_anul(Date fec_proceso_sap_anul) {
        this.fec_proceso_sap_anul = fec_proceso_sap_anul;
    }

    public Date getFec_proceso_sap_anul() {
        return fec_proceso_sap_anul;
    }

    public void setInd_reclamo_navsat(String ind_reclamo_navsat) {
        this.ind_reclamo_navsat = ind_reclamo_navsat;
    }

    public String getInd_reclamo_navsat() {
        return ind_reclamo_navsat;
    }

    public void setVal_dcto_comp(Double val_dcto_comp) {
        this.val_dcto_comp = val_dcto_comp;
    }

    public Double getVal_dcto_comp() {
        return val_dcto_comp;
    }

    public void setMotivo_anulacion(String motivo_anulacion) {
        this.motivo_anulacion = motivo_anulacion;
    }

    public String getMotivo_anulacion() {
        return motivo_anulacion;
    }

    public void setFecha_cobro(Date fecha_cobro) {
        this.fecha_cobro = fecha_cobro;
    }

    public Date getFecha_cobro() {
        return fecha_cobro;
    }

    public void setFecha_anulacion(Date fecha_anulacion) {
        this.fecha_anulacion = fecha_anulacion;
    }

    public Date getFecha_anulacion() {
        return fecha_anulacion;
    }

    public void setFech_imp_cobro(Date fech_imp_cobro) {
        this.fech_imp_cobro = fech_imp_cobro;
    }

    public Date getFech_imp_cobro() {
        return fech_imp_cobro;
    }

    public void setFech_imp_anul(Date fech_imp_anul) {
        this.fech_imp_anul = fech_imp_anul;
    }

    public Date getFech_imp_anul() {
        return fech_imp_anul;
    }

    public void setTip_clien_convenio(String tip_clien_convenio) {
        this.tip_clien_convenio = tip_clien_convenio;
    }

    public String getTip_clien_convenio() {
        return tip_clien_convenio;
    }

    public void setVal_copago_comp_pago(Double val_copago_comp_pago) {
        this.val_copago_comp_pago = val_copago_comp_pago;
    }

    public Double getVal_copago_comp_pago() {
        return val_copago_comp_pago;
    }

    public void setVal_igv_comp_copago(Double val_igv_comp_copago) {
        this.val_igv_comp_copago = val_igv_comp_copago;
    }

    public Double getVal_igv_comp_copago() {
        return val_igv_comp_copago;
    }

    public void setNum_comp_copago_ref(String num_comp_copago_ref) {
        this.num_comp_copago_ref = num_comp_copago_ref;
    }

    public String getNum_comp_copago_ref() {
        return num_comp_copago_ref;
    }

    public void setInd_afecta_kardex(String ind_afecta_kardex) {
        this.ind_afecta_kardex = ind_afecta_kardex;
    }

    public String getInd_afecta_kardex() {
        return ind_afecta_kardex;
    }

    public void setPct_beneficiario(Double pct_beneficiario) {
        this.pct_beneficiario = pct_beneficiario;
    }

    public Double getPct_beneficiario() {
        return pct_beneficiario;
    }

    public void setPct_empresa(Double pct_empresa) {
        this.pct_empresa = pct_empresa;
    }

    public Double getPct_empresa() {
        return pct_empresa;
    }

    public void setInd_comp_credito(String ind_comp_credito) {
        this.ind_comp_credito = ind_comp_credito;
    }

    public String getInd_comp_credito() {
        return ind_comp_credito;
    }

    public void setTip_comp_pago_ref(String tip_comp_pago_ref) {
        this.tip_comp_pago_ref = tip_comp_pago_ref;
    }

    public String getTip_comp_pago_ref() {
        return tip_comp_pago_ref;
    }

    public void setNum_comp_pago_e(String num_comp_pago_e) {
        this.num_comp_pago_e = num_comp_pago_e;
    }

    public String getNum_comp_pago_e() {
        return this.num_comp_pago_e;
    }

    public void setCod_tip_proc_pago(String cod_tip_proc_pago) {
        this.cod_tip_proc_pago = cod_tip_proc_pago;
    }

    public String getCod_tip_proc_pago() {
        return this.cod_tip_proc_pago;
    }
}
