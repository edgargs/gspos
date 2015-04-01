package mifarma.transf.daemon.bean;

import java.util.Date;

public class LgtNotaEsDet {
    
    private String cod_grupo_cia = "";
    private String cod_local = "";
    private String num_nota_es = "";
    private int sec_det_nota_es;
    private String cod_prod = "";
    private int sec_guia_rem;
    private double val_prec_unit;
    private double val_prec_total;
    private int cant_mov;
    private int val_frac;
    private String est_nota_es_det = "";
    private Date fec_nota_es_det;
    private String desc_unid_vta = "";
    private Date fec_vcto_prod;
    private String num_lote_prod = "";
    private int cant_enviada_matr;
    private int num_pag_recep;
    private String ind_prod_afec = "";
    private String usu_crea_nota_es_det = "";
    private Date fec_crea_nota_es_det;
    private String usu_mod_nota_es_det = "";
    private Date fec_mod_nota_es_det;
    private Date fec_proceso_sap;
    private int posicion;
    private String num_entrega = "";

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

    public void setSec_det_nota_es(int sec_det_nota_es) {
        this.sec_det_nota_es = sec_det_nota_es;
    }

    public int getSec_det_nota_es() {
        return sec_det_nota_es;
    }

    public void setCod_prod(String cod_prod) {
        this.cod_prod = cod_prod;
    }

    public String getCod_prod() {
        return cod_prod;
    }

    public void setSec_guia_rem(int sec_guia_rem) {
        this.sec_guia_rem = sec_guia_rem;
    }

    public int getSec_guia_rem() {
        return sec_guia_rem;
    }

    public void setVal_prec_unit(double val_prec_unit) {
        this.val_prec_unit = val_prec_unit;
    }

    public double getVal_prec_unit() {
        return val_prec_unit;
    }

    public void setVal_prec_total(double val_prec_total) {
        this.val_prec_total = val_prec_total;
    }

    public double getVal_prec_total() {
        return val_prec_total;
    }

    public void setCant_mov(int cant_mov) {
        this.cant_mov = cant_mov;
    }

    public int getCant_mov() {
        return cant_mov;
    }

    public void setVal_frac(int val_frac) {
        this.val_frac = val_frac;
    }

    public int getVal_frac() {
        return val_frac;
    }

    public void setEst_nota_es_det(String est_nota_es_det) {
        this.est_nota_es_det = est_nota_es_det;
    }

    public String getEst_nota_es_det() {
        return est_nota_es_det;
    }

    public void setFec_nota_es_det(Date fec_nota_es_det) {
        this.fec_nota_es_det = fec_nota_es_det;
    }

    public Date getFec_nota_es_det() {
        return fec_nota_es_det;
    }

    public void setDesc_unid_vta(String desc_unid_vta) {
        this.desc_unid_vta = desc_unid_vta;
    }

    public String getDesc_unid_vta() {
        return desc_unid_vta;
    }

    public void setFec_vcto_prod(Date fec_vcto_prod) {
        this.fec_vcto_prod = fec_vcto_prod;
    }

    public Date getFec_vcto_prod() {
        return fec_vcto_prod;
    }

    public void setNum_lote_prod(String num_lote_prod) {
        this.num_lote_prod = num_lote_prod;
    }

    public String getNum_lote_prod() {
        return num_lote_prod;
    }

    public void setCant_enviada_matr(int cant_enviada_matr) {
        this.cant_enviada_matr = cant_enviada_matr;
    }

    public int getCant_enviada_matr() {
        return cant_enviada_matr;
    }

    public void setNum_pag_recep(int num_pag_recep) {
        this.num_pag_recep = num_pag_recep;
    }

    public int getNum_pag_recep() {
        return num_pag_recep;
    }

    public void setInd_prod_afec(String ind_prod_afec) {
        this.ind_prod_afec = ind_prod_afec;
    }

    public String getInd_prod_afec() {
        return ind_prod_afec;
    }

    public void setUsu_crea_nota_es_det(String usu_crea_nota_es_det) {
        this.usu_crea_nota_es_det = usu_crea_nota_es_det;
    }

    public String getUsu_crea_nota_es_det() {
        return usu_crea_nota_es_det;
    }

    public void setFec_crea_nota_es_det(Date fec_crea_nota_es_det) {
        this.fec_crea_nota_es_det = fec_crea_nota_es_det;
    }

    public Date getFec_crea_nota_es_det() {
        return fec_crea_nota_es_det;
    }

    public void setUsu_mod_nota_es_det(String usu_mod_nota_es_det) {
        this.usu_mod_nota_es_det = usu_mod_nota_es_det;
    }

    public String getUsu_mod_nota_es_det() {
        return usu_mod_nota_es_det;
    }

    public void setFec_mod_nota_es_det(Date fec_mod_nota_es_det) {
        this.fec_mod_nota_es_det = fec_mod_nota_es_det;
    }

    public Date getFec_mod_nota_es_det() {
        return fec_mod_nota_es_det;
    }

    public void setFec_proceso_sap(Date fec_proceso_sap) {
        this.fec_proceso_sap = fec_proceso_sap;
    }

    public Date getFec_proceso_sap() {
        return fec_proceso_sap;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setNum_entrega(String num_entrega) {
        this.num_entrega = num_entrega;
    }

    public String getNum_entrega() {
        return num_entrega;
    }
}
