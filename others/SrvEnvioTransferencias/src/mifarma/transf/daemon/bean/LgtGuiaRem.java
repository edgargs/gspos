package mifarma.transf.daemon.bean;

import java.util.Date;

public class LgtGuiaRem {
    
    private String cod_grupo_cia = "";
    private String cod_local = "";
    private String num_nota_es = "";
    private int sec_guia_rem;
    private String num_guia_rem = "";
    private Date fec_crea_guia_rem;
    private String usu_crea_guia_rem = "";
    private Date fec_mod_guia_rem;
    private String usu_mod_guia_rem = "";
    private String est_guia_rem = "";
    private String num_entrega = "";
    private String ind_guia_cerrada = "";
    private String ind_guia_impresa = "";

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

    public void setNum_nota_es(String num_nota_es) {
        this.num_nota_es = num_nota_es;
    }

    public String getNum_nota_es() {
        return num_nota_es;
    }

    public void setSec_guia_rem(int sec_guia_rem) {
        this.sec_guia_rem = sec_guia_rem;
    }

    public int getSec_guia_rem() {
        return sec_guia_rem;
    }

    public void setNum_guia_rem(String num_guia_rem) {
        this.num_guia_rem = num_guia_rem;
    }

    public String getNum_guia_rem() {
        return num_guia_rem;
    }

    public void setFec_crea_guia_rem(Date fec_crea_guia_rem) {
        this.fec_crea_guia_rem = fec_crea_guia_rem;
    }

    public Date getFec_crea_guia_rem() {
        return fec_crea_guia_rem;
    }

    public void setUsu_crea_guia_rem(String usu_crea_guia_rem) {
        this.usu_crea_guia_rem = usu_crea_guia_rem;
    }

    public String getUsu_crea_guia_rem() {
        return usu_crea_guia_rem;
    }

    public void setFec_mod_guia_rem(Date fec_mod_guia_rem) {
        this.fec_mod_guia_rem = fec_mod_guia_rem;
    }

    public Date getFec_mod_guia_rem() {
        return fec_mod_guia_rem;
    }

    public void setUsu_mod_guia_rem(String usu_mod_guia_rem) {
        this.usu_mod_guia_rem = usu_mod_guia_rem;
    }

    public String getUsu_mod_guia_rem() {
        return usu_mod_guia_rem;
    }

    public void setEst_guia_rem(String est_guia_rem) {
        this.est_guia_rem = est_guia_rem;
    }

    public String getEst_guia_rem() {
        return est_guia_rem;
    }

    public void setNum_entrega(String num_entrega) {
        this.num_entrega = num_entrega;
    }

    public String getNum_entrega() {
        return num_entrega;
    }

    public void setInd_guia_cerrada(String ind_guia_cerrada) {
        this.ind_guia_cerrada = ind_guia_cerrada;
    }

    public String getInd_guia_cerrada() {
        return ind_guia_cerrada;
    }

    public void setInd_guia_impresa(String ind_guia_impresa) {
        this.ind_guia_impresa = ind_guia_impresa;
    }

    public String getInd_guia_impresa() {
        return ind_guia_impresa;
    }
}
