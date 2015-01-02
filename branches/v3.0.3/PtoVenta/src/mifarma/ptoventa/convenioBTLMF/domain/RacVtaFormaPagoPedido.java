package mifarma.ptoventa.convenioBTLMF.domain;

import java.util.Date;

/**
 * Entidad RAC_VTA_FORMA_PAGO_PEDIDO
 * @author ERIOS
 * @since 2.4.4
 */
public class RacVtaFormaPagoPedido {

    private String cod_grupo_cia;
    private String cod_local;
    private String cod_forma_pago;
    private String num_ped_vta;
    private Double im_pago;
    private String tip_moneda;
    private Double val_tip_cambio;
    private Double val_vuelto;
    private Double im_total_pago;
    private String num_tarj;
    private Date fec_venc_tarj;
    private String nom_tarj;
    private Date fec_crea_forma_pago_ped;
    private String usu_crea_forma_pago_ped;
    private Date fec_mod_forma_pago_ped;
    private String usu_mod_forma_pago_ped;
    private Integer cant_cupon;
    private String tipo_autorizacion;
    private String cod_lote;
    private String cod_autorizacion;
    private String dni_cli_tarj;

    public RacVtaFormaPagoPedido() {
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

    public void setCod_forma_pago(String cod_forma_pago) {
        this.cod_forma_pago = cod_forma_pago;
    }

    public String getCod_forma_pago() {
        return cod_forma_pago;
    }

    public void setNum_ped_vta(String num_ped_vta) {
        this.num_ped_vta = num_ped_vta;
    }

    public String getNum_ped_vta() {
        return num_ped_vta;
    }

    public void setIm_pago(Double im_pago) {
        this.im_pago = im_pago;
    }

    public Double getIm_pago() {
        return im_pago;
    }

    public void setTip_moneda(String tip_moneda) {
        this.tip_moneda = tip_moneda;
    }

    public String getTip_moneda() {
        return tip_moneda;
    }

    public void setVal_tip_cambio(Double val_tip_cambio) {
        this.val_tip_cambio = val_tip_cambio;
    }

    public Double getVal_tip_cambio() {
        return val_tip_cambio;
    }

    public void setVal_vuelto(Double val_vuelto) {
        this.val_vuelto = val_vuelto;
    }

    public Double getVal_vuelto() {
        return val_vuelto;
    }

    public void setIm_total_pago(Double im_total_pago) {
        this.im_total_pago = im_total_pago;
    }

    public Double getIm_total_pago() {
        return im_total_pago;
    }

    public void setNum_tarj(String num_tarj) {
        this.num_tarj = num_tarj;
    }

    public String getNum_tarj() {
        return num_tarj;
    }

    public void setFec_venc_tarj(Date fec_venc_tarj) {
        this.fec_venc_tarj = fec_venc_tarj;
    }

    public Date getFec_venc_tarj() {
        return fec_venc_tarj;
    }

    public void setNom_tarj(String nom_tarj) {
        this.nom_tarj = nom_tarj;
    }

    public String getNom_tarj() {
        return nom_tarj;
    }

    public void setFec_crea_forma_pago_ped(Date fec_crea_forma_pago_ped) {
        this.fec_crea_forma_pago_ped = fec_crea_forma_pago_ped;
    }

    public Date getFec_crea_forma_pago_ped() {
        return fec_crea_forma_pago_ped;
    }

    public void setUsu_crea_forma_pago_ped(String usu_crea_forma_pago_ped) {
        this.usu_crea_forma_pago_ped = usu_crea_forma_pago_ped;
    }

    public String getUsu_crea_forma_pago_ped() {
        return usu_crea_forma_pago_ped;
    }

    public void setFec_mod_forma_pago_ped(Date fec_mod_forma_pago_ped) {
        this.fec_mod_forma_pago_ped = fec_mod_forma_pago_ped;
    }

    public Date getFec_mod_forma_pago_ped() {
        return fec_mod_forma_pago_ped;
    }

    public void setUsu_mod_forma_pago_ped(String usu_mod_forma_pago_ped) {
        this.usu_mod_forma_pago_ped = usu_mod_forma_pago_ped;
    }

    public String getUsu_mod_forma_pago_ped() {
        return usu_mod_forma_pago_ped;
    }

    public void setCant_cupon(Integer cant_cupon) {
        this.cant_cupon = cant_cupon;
    }

    public Integer getCant_cupon() {
        return cant_cupon;
    }

    public void setTipo_autorizacion(String tipo_autorizacion) {
        this.tipo_autorizacion = tipo_autorizacion;
    }

    public String getTipo_autorizacion() {
        return tipo_autorizacion;
    }

    public void setCod_lote(String cod_lote) {
        this.cod_lote = cod_lote;
    }

    public String getCod_lote() {
        return cod_lote;
    }

    public void setCod_autorizacion(String cod_autorizacion) {
        this.cod_autorizacion = cod_autorizacion;
    }

    public String getCod_autorizacion() {
        return cod_autorizacion;
    }

    public void setDni_cli_tarj(String dni_cli_tarj) {
        this.dni_cli_tarj = dni_cli_tarj;
    }

    public String getDni_cli_tarj() {
        return dni_cli_tarj;
    }
}
