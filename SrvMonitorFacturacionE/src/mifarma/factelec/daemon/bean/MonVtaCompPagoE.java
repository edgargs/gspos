package mifarma.factelec.daemon.bean;

import java.util.Date;

public class MonVtaCompPagoE {
    
    private String cod_grupo_cia = "";
    private String cod_cia = "";
    private String cod_local = "";
    private String num_ped_vta = "";
    private String sec_comp_pago = "";
    private String tip_doc_sunat = "";
    private String num_comp_pago_e = "";
    private String estado = "";
    private String codigo = "";
    private String mensaje = "";
    private String usu_crea = "";
    private Date fec_crea;
    private String usu_mod = "";
    private Date fec_mod;
    private String ind_valida = "";
        
    public MonVtaCompPagoE() {
        super();
    }

    public void setCod_grupo_cia(String cod_grupo_cia) {
        this.cod_grupo_cia = cod_grupo_cia;
    }

    public String getCod_grupo_cia() {
        return cod_grupo_cia;
    }

    public void setCod_cia(String cod__cia) {
        this.cod_cia = cod__cia;
    }

    public String getCod_cia() {
        return cod_cia;
    }

    public void setCod_local(String cod_local) {
        this.cod_local = cod_local;
    }

    public String getCod_local() {
        return cod_local;
    }

    public void setSec_comp_pago(String sec_comp_pago) {
        this.sec_comp_pago = sec_comp_pago;
    }

    public String getSec_comp_pago() {
        return sec_comp_pago;
    }

    public void setTip_doc_sunat(String tip_doc_sunat) {
        this.tip_doc_sunat = tip_doc_sunat;
    }

    public String getTip_doc_sunat() {
        return tip_doc_sunat;
    }

    public void setNum_comp_pago_e(String num_comp_pago_e) {
        this.num_comp_pago_e = num_comp_pago_e;
    }

    public String getNum_comp_pago_e() {
        return num_comp_pago_e;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setNum_ped_vta(String num_ped_vta) {
        this.num_ped_vta = num_ped_vta;
    }

    public String getNum_ped_vta() {
        return num_ped_vta;
    }

    public void setUsu_crea(String usu_crea) {
        this.usu_crea = usu_crea;
    }

    public String getUsu_crea() {
        return usu_crea;
    }

    public void setFec_crea(Date fec_crea) {
        this.fec_crea = fec_crea;
    }

    public Date getFec_crea() {
        return fec_crea;
    }

    public void setUsu_mod(String usu_mod) {
        this.usu_mod = usu_mod;
    }

    public String getUsu_mod() {
        return usu_mod;
    }

    public void setFec_mod(Date fec_mod) {
        this.fec_mod = fec_mod;
    }

    public Date getFec_mod() {
        return fec_mod;
    }

    public void setInd_valida(String ind_valida) {
        this.ind_valida = ind_valida;
    }

    public String getInd_valida() {
        return ind_valida;
    }
}
