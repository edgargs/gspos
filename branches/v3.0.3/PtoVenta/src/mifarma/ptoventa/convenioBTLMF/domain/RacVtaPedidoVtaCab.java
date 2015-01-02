package mifarma.ptoventa.convenioBTLMF.domain;

import java.util.Date;

/**
 * Entidad RAC_VTA_PEDIDO_VTA_CAB
 * @author ERIOS
 * @since 2.4.4
 */
public class RacVtaPedidoVtaCab {

    private String cod_grupo_cia = "";
    private String cod_local = "";
    private String num_ped_vta = "";
    private String cod_cli_local = "";
    private String sec_mov_caja = "";
    private Date fec_ped_vta;
    private Double val_bruto_ped_vta;
    private Double val_neto_ped_vta;
    private Double val_redondeo_ped_vta;
    private Double val_igv_ped_vta;
    private Double val_dcto_ped_vta;
    private String tip_ped_vta = "";
    private Double val_tip_cambio_ped_vta;
    private String num_ped_diario = "";
    private Integer cant_items_ped_vta;
    private String est_ped_vta = "";
    private String tip_comp_pago = "";
    private String nom_cli_ped_vta = "";
    private String dir_cli_ped_vta = "";
    private String ruc_cli_ped_vta = "";
    private String usu_crea_ped_vta_cab = "";
    private Date fec_crea_ped_vta_cab;
    private String usu_mod_ped_vta_cab = "";
    private Date fec_mod_ped_vta_cab;
    private String ind_pedido_anul = "";
    private String ind_distr_gratuita = "";
    private String cod_local_atencion = "";
    private String num_ped_vta_origen = "";
    private String obs_forma_pago = "";
    private String obs_ped_vta = "";
    private String cod_dir = "";
    private String num_telefono = "";
    private Date fec_ruteo_ped_vta_cab;
    private Date fec_salida_local;
    private Date fec_entrega_ped_vta_cab;
    private Date fec_retorno_local;
    private String cod_ruteador = "";
    private String cod_motorizado = "";
    private String ind_deliv_automatico = "";
    private String num_ped_rec = "";
    private String ind_conv_enteros = "";
    private String ind_ped_convenio = "";
    private String cod_convenio = "";
    private String num_pedido_delivery = "";
    private String cod_local_procedencia = "";
    private String ip_pc = "";
    private String cod_rpta_recarga = "";
    private String ind_fid = "";
    private String motivo_anulacion = "";
    private String dni_cli = "";
    private String ind_camp_acumulada = "";
    private Date fec_ini_cobro;
    private Date fec_fin_cobro;
    private String sec_usu_local = "";
    private String punto_llegada = "";
    private String ind_fp_fid_efectivo = "";
    private String ind_fp_fid_tarjeta = "";
    private String cod_fp_fid_tarjeta = "";
    private String cod_cli_conv = "";
    private String cod_barra_conv = "";
    private String ind_conv_btl_mf = "";
    private String name_pc_cob_ped = "";
    private String ip_cob_ped = "";
    private String dni_usu_local = "";
    private Date fec_proceso_rac;
    private Date fecha_proceso_anula_rac;

    public RacVtaPedidoVtaCab() {
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

    public void setCod_cli_local(String cod_cli_local) {
        this.cod_cli_local = cod_cli_local;
    }

    public String getCod_cli_local() {
        return cod_cli_local;
    }

    public void setSec_mov_caja(String sec_mov_caja) {
        this.sec_mov_caja = sec_mov_caja;
    }

    public String getSec_mov_caja() {
        return sec_mov_caja;
    }

    public void setFec_ped_vta(Date fec_ped_vta) {
        this.fec_ped_vta = fec_ped_vta;
    }

    public Date getFec_ped_vta() {
        return fec_ped_vta;
    }

    public void setVal_bruto_ped_vta(Double val_bruto_ped_vta) {
        this.val_bruto_ped_vta = val_bruto_ped_vta;
    }

    public Double getVal_bruto_ped_vta() {
        return val_bruto_ped_vta;
    }

    public void setVal_neto_ped_vta(Double val_neto_ped_vta) {
        this.val_neto_ped_vta = val_neto_ped_vta;
    }

    public Double getVal_neto_ped_vta() {
        return val_neto_ped_vta;
    }

    public void setVal_redondeo_ped_vta(Double val_redondeo_ped_vta) {
        this.val_redondeo_ped_vta = val_redondeo_ped_vta;
    }

    public Double getVal_redondeo_ped_vta() {
        return val_redondeo_ped_vta;
    }

    public void setVal_igv_ped_vta(Double val_igv_ped_vta) {
        this.val_igv_ped_vta = val_igv_ped_vta;
    }

    public Double getVal_igv_ped_vta() {
        return val_igv_ped_vta;
    }

    public void setVal_dcto_ped_vta(Double val_dcto_ped_vta) {
        this.val_dcto_ped_vta = val_dcto_ped_vta;
    }

    public Double getVal_dcto_ped_vta() {
        return val_dcto_ped_vta;
    }

    public void setTip_ped_vta(String tip_ped_vta) {
        this.tip_ped_vta = tip_ped_vta;
    }

    public String getTip_ped_vta() {
        return tip_ped_vta;
    }

    public void setVal_tip_cambio_ped_vta(Double val_tip_cambio_ped_vta) {
        this.val_tip_cambio_ped_vta = val_tip_cambio_ped_vta;
    }

    public Double getVal_tip_cambio_ped_vta() {
        return val_tip_cambio_ped_vta;
    }

    public void setNum_ped_diario(String num_ped_diario) {
        this.num_ped_diario = num_ped_diario;
    }

    public String getNum_ped_diario() {
        return num_ped_diario;
    }

    public void setCant_items_ped_vta(Integer cant_items_ped_vta) {
        this.cant_items_ped_vta = cant_items_ped_vta;
    }

    public Integer getCant_items_ped_vta() {
        return cant_items_ped_vta;
    }

    public void setEst_ped_vta(String est_ped_vta) {
        this.est_ped_vta = est_ped_vta;
    }

    public String getEst_ped_vta() {
        return est_ped_vta;
    }

    public void setTip_comp_pago(String tip_comp_pago) {
        this.tip_comp_pago = tip_comp_pago;
    }

    public String getTip_comp_pago() {
        return tip_comp_pago;
    }

    public void setNom_cli_ped_vta(String nom_cli_ped_vta) {
        this.nom_cli_ped_vta = nom_cli_ped_vta;
    }

    public String getNom_cli_ped_vta() {
        return nom_cli_ped_vta;
    }

    public void setDir_cli_ped_vta(String dir_cli_ped_vta) {
        this.dir_cli_ped_vta = dir_cli_ped_vta;
    }

    public String getDir_cli_ped_vta() {
        return dir_cli_ped_vta;
    }

    public void setRuc_cli_ped_vta(String ruc_cli_ped_vta) {
        this.ruc_cli_ped_vta = ruc_cli_ped_vta;
    }

    public String getRuc_cli_ped_vta() {
        return ruc_cli_ped_vta;
    }

    public void setUsu_crea_ped_vta_cab(String usu_crea_ped_vta_cab) {
        this.usu_crea_ped_vta_cab = usu_crea_ped_vta_cab;
    }

    public String getUsu_crea_ped_vta_cab() {
        return usu_crea_ped_vta_cab;
    }

    public void setFec_crea_ped_vta_cab(Date fec_crea_ped_vta_cab) {
        this.fec_crea_ped_vta_cab = fec_crea_ped_vta_cab;
    }

    public Date getFec_crea_ped_vta_cab() {
        return fec_crea_ped_vta_cab;
    }

    public void setUsu_mod_ped_vta_cab(String usu_mod_ped_vta_cab) {
        this.usu_mod_ped_vta_cab = usu_mod_ped_vta_cab;
    }

    public String getUsu_mod_ped_vta_cab() {
        return usu_mod_ped_vta_cab;
    }

    public void setFec_mod_ped_vta_cab(Date fec_mod_ped_vta_cab) {
        this.fec_mod_ped_vta_cab = fec_mod_ped_vta_cab;
    }

    public Date getFec_mod_ped_vta_cab() {
        return fec_mod_ped_vta_cab;
    }

    public void setInd_pedido_anul(String ind_pedido_anul) {
        this.ind_pedido_anul = ind_pedido_anul;
    }

    public String getInd_pedido_anul() {
        return ind_pedido_anul;
    }

    public void setInd_distr_gratuita(String ind_distr_gratuita) {
        this.ind_distr_gratuita = ind_distr_gratuita;
    }

    public String getInd_distr_gratuita() {
        return ind_distr_gratuita;
    }

    public void setCod_local_atencion(String cod_local_atencion) {
        this.cod_local_atencion = cod_local_atencion;
    }

    public String getCod_local_atencion() {
        return cod_local_atencion;
    }

    public void setNum_ped_vta_origen(String num_ped_vta_origen) {
        this.num_ped_vta_origen = num_ped_vta_origen;
    }

    public String getNum_ped_vta_origen() {
        return num_ped_vta_origen;
    }

    public void setObs_forma_pago(String obs_forma_pago) {
        this.obs_forma_pago = obs_forma_pago;
    }

    public String getObs_forma_pago() {
        return obs_forma_pago;
    }

    public void setObs_ped_vta(String obs_ped_vta) {
        this.obs_ped_vta = obs_ped_vta;
    }

    public String getObs_ped_vta() {
        return obs_ped_vta;
    }

    public void setCod_dir(String cod_dir) {
        this.cod_dir = cod_dir;
    }

    public String getCod_dir() {
        return cod_dir;
    }

    public void setNum_telefono(String num_telefono) {
        this.num_telefono = num_telefono;
    }

    public String getNum_telefono() {
        return num_telefono;
    }

    public void setFec_ruteo_ped_vta_cab(Date fec_ruteo_ped_vta_cab) {
        this.fec_ruteo_ped_vta_cab = fec_ruteo_ped_vta_cab;
    }

    public Date getFec_ruteo_ped_vta_cab() {
        return fec_ruteo_ped_vta_cab;
    }

    public void setFec_salida_local(Date fec_salida_local) {
        this.fec_salida_local = fec_salida_local;
    }

    public Date getFec_salida_local() {
        return fec_salida_local;
    }

    public void setFec_entrega_ped_vta_cab(Date fec_entrega_ped_vta_cab) {
        this.fec_entrega_ped_vta_cab = fec_entrega_ped_vta_cab;
    }

    public Date getFec_entrega_ped_vta_cab() {
        return fec_entrega_ped_vta_cab;
    }

    public void setFec_retorno_local(Date fec_retorno_local) {
        this.fec_retorno_local = fec_retorno_local;
    }

    public Date getFec_retorno_local() {
        return fec_retorno_local;
    }

    public void setCod_ruteador(String cod_ruteador) {
        this.cod_ruteador = cod_ruteador;
    }

    public String getCod_ruteador() {
        return cod_ruteador;
    }

    public void setCod_motorizado(String cod_motorizado) {
        this.cod_motorizado = cod_motorizado;
    }

    public String getCod_motorizado() {
        return cod_motorizado;
    }

    public void setInd_deliv_automatico(String ind_deliv_automatico) {
        this.ind_deliv_automatico = ind_deliv_automatico;
    }

    public String getInd_deliv_automatico() {
        return ind_deliv_automatico;
    }

    public void setNum_ped_rec(String num_ped_rec) {
        this.num_ped_rec = num_ped_rec;
    }

    public String getNum_ped_rec() {
        return num_ped_rec;
    }

    public void setInd_conv_enteros(String ind_conv_enteros) {
        this.ind_conv_enteros = ind_conv_enteros;
    }

    public String getInd_conv_enteros() {
        return ind_conv_enteros;
    }

    public void setInd_ped_convenio(String ind_ped_convenio) {
        this.ind_ped_convenio = ind_ped_convenio;
    }

    public String getInd_ped_convenio() {
        return ind_ped_convenio;
    }

    public void setCod_convenio(String cod_convenio) {
        this.cod_convenio = cod_convenio;
    }

    public String getCod_convenio() {
        return cod_convenio;
    }

    public void setNum_pedido_delivery(String num_pedido_delivery) {
        this.num_pedido_delivery = num_pedido_delivery;
    }

    public String getNum_pedido_delivery() {
        return num_pedido_delivery;
    }

    public void setCod_local_procedencia(String cod_local_procedencia) {
        this.cod_local_procedencia = cod_local_procedencia;
    }

    public String getCod_local_procedencia() {
        return cod_local_procedencia;
    }

    public void setIp_pc(String ip_pc) {
        this.ip_pc = ip_pc;
    }

    public String getIp_pc() {
        return ip_pc;
    }

    public void setCod_rpta_recarga(String cod_rpta_recarga) {
        this.cod_rpta_recarga = cod_rpta_recarga;
    }

    public String getCod_rpta_recarga() {
        return cod_rpta_recarga;
    }

    public void setInd_fid(String ind_fid) {
        this.ind_fid = ind_fid;
    }

    public String getInd_fid() {
        return ind_fid;
    }

    public void setMotivo_anulacion(String motivo_anulacion) {
        this.motivo_anulacion = motivo_anulacion;
    }

    public String getMotivo_anulacion() {
        return motivo_anulacion;
    }

    public void setDni_cli(String dni_cli) {
        this.dni_cli = dni_cli;
    }

    public String getDni_cli() {
        return dni_cli;
    }

    public void setInd_camp_acumulada(String ind_camp_acumulada) {
        this.ind_camp_acumulada = ind_camp_acumulada;
    }

    public String getInd_camp_acumulada() {
        return ind_camp_acumulada;
    }

    public void setFec_ini_cobro(Date fec_ini_cobro) {
        this.fec_ini_cobro = fec_ini_cobro;
    }

    public Date getFec_ini_cobro() {
        return fec_ini_cobro;
    }

    public void setFec_fin_cobro(Date fec_fin_cobro) {
        this.fec_fin_cobro = fec_fin_cobro;
    }

    public Date getFec_fin_cobro() {
        return fec_fin_cobro;
    }

    public void setSec_usu_local(String sec_usu_local) {
        this.sec_usu_local = sec_usu_local;
    }

    public String getSec_usu_local() {
        return sec_usu_local;
    }

    public void setPunto_llegada(String punto_llegada) {
        this.punto_llegada = punto_llegada;
    }

    public String getPunto_llegada() {
        return punto_llegada;
    }

    public void setInd_fp_fid_efectivo(String ind_fp_fid_efectivo) {
        this.ind_fp_fid_efectivo = ind_fp_fid_efectivo;
    }

    public String getInd_fp_fid_efectivo() {
        return ind_fp_fid_efectivo;
    }

    public void setInd_fp_fid_tarjeta(String ind_fp_fid_tarjeta) {
        this.ind_fp_fid_tarjeta = ind_fp_fid_tarjeta;
    }

    public String getInd_fp_fid_tarjeta() {
        return ind_fp_fid_tarjeta;
    }

    public void setCod_fp_fid_tarjeta(String cod_fp_fid_tarjeta) {
        this.cod_fp_fid_tarjeta = cod_fp_fid_tarjeta;
    }

    public String getCod_fp_fid_tarjeta() {
        return cod_fp_fid_tarjeta;
    }

    public void setCod_cli_conv(String cod_cli_conv) {
        this.cod_cli_conv = cod_cli_conv;
    }

    public String getCod_cli_conv() {
        return cod_cli_conv;
    }

    public void setCod_barra_conv(String cod_barra_conv) {
        this.cod_barra_conv = cod_barra_conv;
    }

    public String getCod_barra_conv() {
        return cod_barra_conv;
    }

    public void setInd_conv_btl_mf(String ind_conv_btl_mf) {
        this.ind_conv_btl_mf = ind_conv_btl_mf;
    }

    public String getInd_conv_btl_mf() {
        return ind_conv_btl_mf;
    }

    public void setName_pc_cob_ped(String name_pc_cob_ped) {
        this.name_pc_cob_ped = name_pc_cob_ped;
    }

    public String getName_pc_cob_ped() {
        return name_pc_cob_ped;
    }

    public void setIp_cob_ped(String ip_cob_ped) {
        this.ip_cob_ped = ip_cob_ped;
    }

    public String getIp_cob_ped() {
        return ip_cob_ped;
    }

    public void setDni_usu_local(String dni_usu_local) {
        this.dni_usu_local = dni_usu_local;
    }

    public String getDni_usu_local() {
        return dni_usu_local;
    }

    public void setFec_proceso_rac(Date fec_proceso_rac) {
        this.fec_proceso_rac = fec_proceso_rac;
    }

    public Date getFec_proceso_rac() {
        return fec_proceso_rac;
    }

    public void setFecha_proceso_anula_rac(Date fecha_proceso_anula_rac) {
        this.fecha_proceso_anula_rac = fecha_proceso_anula_rac;
    }

    public Date getFecha_proceso_anula_rac() {
        return fecha_proceso_anula_rac;
    }
}
