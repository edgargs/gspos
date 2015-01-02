package mifarma.ptoventa.convenioBTLMF.domain;

import java.util.Date;

/**
 * Entidad RAC_CON_PED_VTA
 * @author ERIOS
 * @since 2.4.4
 */
public class RacConPedVta {

    private String cod_grupo_cia;
    private String cod_local;
    private String num_ped_vta;
    private String cod_campo;
    private String cod_convenio;
    private String cod_cliente;
    private Date fec_crea_ped_vta_cli;
    private String usu_crea_ped_vta_cli;
    private Date fec_mod_ped_vta_cli;
    private String usu_mod_ped_vta_cli;
    private String descripcion_campo;
    private String nombre_campo;
    private String flg_imprime;
    private String cod_valor_in;

    public RacConPedVta() {
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

    public void setCod_campo(String cod_campo) {
        this.cod_campo = cod_campo;
    }

    public String getCod_campo() {
        return cod_campo;
    }

    public void setCod_convenio(String cod_convenio) {
        this.cod_convenio = cod_convenio;
    }

    public String getCod_convenio() {
        return cod_convenio;
    }

    public void setCod_cliente(String cod_cliente) {
        this.cod_cliente = cod_cliente;
    }

    public String getCod_cliente() {
        return cod_cliente;
    }

    public void setFec_crea_ped_vta_cli(Date fec_crea_ped_vta_cli) {
        this.fec_crea_ped_vta_cli = fec_crea_ped_vta_cli;
    }

    public Date getFec_crea_ped_vta_cli() {
        return fec_crea_ped_vta_cli;
    }

    public void setUsu_crea_ped_vta_cli(String usu_crea_ped_vta_cli) {
        this.usu_crea_ped_vta_cli = usu_crea_ped_vta_cli;
    }

    public String getUsu_crea_ped_vta_cli() {
        return usu_crea_ped_vta_cli;
    }

    public void setFec_mod_ped_vta_cli(Date fec_mod_ped_vta_cli) {
        this.fec_mod_ped_vta_cli = fec_mod_ped_vta_cli;
    }

    public Date getFec_mod_ped_vta_cli() {
        return fec_mod_ped_vta_cli;
    }

    public void setUsu_mod_ped_vta_cli(String usu_mod_ped_vta_cli) {
        this.usu_mod_ped_vta_cli = usu_mod_ped_vta_cli;
    }

    public String getUsu_mod_ped_vta_cli() {
        return usu_mod_ped_vta_cli;
    }

    public void setDescripcion_campo(String descripcion_campo) {
        this.descripcion_campo = descripcion_campo;
    }

    public String getDescripcion_campo() {
        return descripcion_campo;
    }

    public void setNombre_campo(String nombre_campo) {
        this.nombre_campo = nombre_campo;
    }

    public String getNombre_campo() {
        return nombre_campo;
    }

    public void setFlg_imprime(String flg_imprime) {
        this.flg_imprime = flg_imprime;
    }

    public String getFlg_imprime() {
        return flg_imprime;
    }

    public void setCod_valor_in(String cod_valor_in) {
        this.cod_valor_in = cod_valor_in;
    }

    public String getCod_valor_in() {
        return cod_valor_in;
    }
}
