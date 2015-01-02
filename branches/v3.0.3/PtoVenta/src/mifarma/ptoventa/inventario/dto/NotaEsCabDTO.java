package mifarma.ptoventa.inventario.dto;

import java.util.Date;

public class NotaEsCabDTO {

    private String codGrupoCia;
    private String codLocal;
    private String numNotaEs;
    private Date fecNotaEsCab;
    private String estaNotaEsCab;
    private String codEstadoNota;
    private String tipoDoc;
    private String numDoc;
    private String codOrigenNotaEs;
    private Integer cantItem;
    private double valorTotalNotaEsCab;
    private String codDestinoNotaEs;
    private String descEmpresa;
    private String rucEmpresa;
    private String direEmpresa;
    private String descTransp;
    private String rucTransp;
    private String dirTransp;
    private String placaTransp;
    private String tipoNotaEs;
    private String tipoOrigenNotaEs;
    private String tipoMotiNotaEs; // motivo de la devolucion
    private String estaRecep;
    private String indNotaImpre;
    private Date fecProceSap;
    private Date fecProceCe;
    private String codMotInterTrans;
    private String indTransAuto;
    private String codGrupoCiaDel;
    private String codLocalDel;
    private String numPedVtaDel;
    private Integer secGrupoDel;
    private String codCia;

    private String usuaCreado;
    private String fechaCreado;
    private String usuaModifi;
    private String fechaModifi;


    //Adicionales a la tabla
    private String filtro;
    private String motivodescCorta;
    private String motivodescLarga;
    private String fecEmisiForm;
    private String codOrdenCompra;

    public void setCodGrupoCia(String codGrupoCia) {
        this.codGrupoCia = codGrupoCia;
    }

    public String getCodGrupoCia() {
        return codGrupoCia;
    }

    public void setCodLocal(String codLocal) {
        this.codLocal = codLocal;
    }

    public String getCodLocal() {
        return codLocal;
    }

    public void setNumNotaEs(String numNotaEs) {
        this.numNotaEs = numNotaEs;
    }

    public String getNumNotaEs() {
        return numNotaEs;
    }

    public void setFecNotaEsCab(Date fecNotaEsCab) {
        this.fecNotaEsCab = fecNotaEsCab;
    }

    public Date getFecNotaEsCab() {
        return fecNotaEsCab;
    }

    public void setEstaNotaEsCab(String estaNotaEsCab) {
        this.estaNotaEsCab = estaNotaEsCab;
    }

    public String getEstaNotaEsCab() {
        return estaNotaEsCab;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setCodOrigenNotaEs(String codOrigenNotaEs) {
        this.codOrigenNotaEs = codOrigenNotaEs;
    }

    public String getCodOrigenNotaEs() {
        return codOrigenNotaEs;
    }

    public void setCantItem(Integer cantItem) {
        this.cantItem = cantItem;
    }

    public Integer getCantItem() {
        return cantItem;
    }

    public void setCodDestinoNotaEs(String codDestinoNotaEs) {
        this.codDestinoNotaEs = codDestinoNotaEs;
    }

    public String getCodDestinoNotaEs() {
        return codDestinoNotaEs;
    }

    public void setDescEmpresa(String descEmpresa) {
        this.descEmpresa = descEmpresa;
    }

    public String getDescEmpresa() {
        return descEmpresa;
    }

    public void setRucEmpresa(String rucEmpresa) {
        this.rucEmpresa = rucEmpresa;
    }

    public String getRucEmpresa() {
        return rucEmpresa;
    }

    public void setDireEmpresa(String direEmpresa) {
        this.direEmpresa = direEmpresa;
    }

    public String getDireEmpresa() {
        return direEmpresa;
    }

    public void setDescTransp(String descTransp) {
        this.descTransp = descTransp;
    }

    public String getDescTransp() {
        return descTransp;
    }

    public void setRucTransp(String rucTransp) {
        this.rucTransp = rucTransp;
    }

    public String getRucTransp() {
        return rucTransp;
    }

    public void setDirTransp(String dirTransp) {
        this.dirTransp = dirTransp;
    }

    public String getDirTransp() {
        return dirTransp;
    }

    public void setPlacaTransp(String placaTransp) {
        this.placaTransp = placaTransp;
    }

    public String getPlacaTransp() {
        return placaTransp;
    }

    public void setTipoNotaEs(String tipoNotaEs) {
        this.tipoNotaEs = tipoNotaEs;
    }

    public String getTipoNotaEs() {
        return tipoNotaEs;
    }

    public void setTipoOrigenNotaEs(String tipoOrigenNotaEs) {
        this.tipoOrigenNotaEs = tipoOrigenNotaEs;
    }

    public String getTipoOrigenNotaEs() {
        return tipoOrigenNotaEs;
    }

    public void setTipoMotiNotaEs(String tipoMotiNotaEs) {
        this.tipoMotiNotaEs = tipoMotiNotaEs;
    }

    public String getTipoMotiNotaEs() {
        return tipoMotiNotaEs;
    }

    public void setEstaRecep(String estaRecep) {
        this.estaRecep = estaRecep;
    }

    public String getEstaRecep() {
        return estaRecep;
    }

    public void setIndNotaImpre(String indNotaImpre) {
        this.indNotaImpre = indNotaImpre;
    }

    public String getIndNotaImpre() {
        return indNotaImpre;
    }

    public void setFecProceSap(Date fecProceSap) {
        this.fecProceSap = fecProceSap;
    }

    public Date getFecProceSap() {
        return fecProceSap;
    }

    public void setFecProceCe(Date fecProceCe) {
        this.fecProceCe = fecProceCe;
    }

    public Date getFecProceCe() {
        return fecProceCe;
    }

    public void setCodMotInterTrans(String codMotInterTrans) {
        this.codMotInterTrans = codMotInterTrans;
    }

    public String getCodMotInterTrans() {
        return codMotInterTrans;
    }

    public void setIndTransAuto(String indTransAuto) {
        this.indTransAuto = indTransAuto;
    }

    public String getIndTransAuto() {
        return indTransAuto;
    }

    public void setCodGrupoCiaDel(String codGrupoCiaDel) {
        this.codGrupoCiaDel = codGrupoCiaDel;
    }

    public String getCodGrupoCiaDel() {
        return codGrupoCiaDel;
    }

    public void setCodLocalDel(String codLocalDel) {
        this.codLocalDel = codLocalDel;
    }

    public String getCodLocalDel() {
        return codLocalDel;
    }

    public void setNumPedVtaDel(String numPedVtaDel) {
        this.numPedVtaDel = numPedVtaDel;
    }

    public String getNumPedVtaDel() {
        return numPedVtaDel;
    }

    public void setSecGrupoDel(Integer secGrupoDel) {
        this.secGrupoDel = secGrupoDel;
    }

    public Integer getSecGrupoDel() {
        return secGrupoDel;
    }

    public void setCodCia(String codCia) {
        this.codCia = codCia;
    }

    public String getCodCia() {
        return codCia;
    }

    public void setUsuaCreado(String usuaCreado) {
        this.usuaCreado = usuaCreado;
    }

    public String getUsuaCreado() {
        return usuaCreado;
    }

    public void setFechaCreado(String fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getFechaCreado() {
        return fechaCreado;
    }

    public void setUsuaModifi(String usuaModifi) {
        this.usuaModifi = usuaModifi;
    }

    public String getUsuaModifi() {
        return usuaModifi;
    }

    public void setFechaModifi(String fechaModifi) {
        this.fechaModifi = fechaModifi;
    }

    public String getFechaModifi() {
        return fechaModifi;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setMotivodescCorta(String MotivodescCorta) {
        this.motivodescCorta = MotivodescCorta;
    }

    public String getMotivodescCorta() {
        return motivodescCorta;
    }

    public void setMotivodescLarga(String MotivodescLarga) {
        this.motivodescLarga = MotivodescLarga;
    }

    public String getMotivodescLarga() {
        return motivodescLarga;
    }


    public void setFecEmisiForm(String fecEmisiForm) {
        this.fecEmisiForm = fecEmisiForm;
    }

    public String getFecEmisiForm() {
        return fecEmisiForm;
    }

    public void setCodOrdenCompra(String codOrdenCompra) {
        this.codOrdenCompra = codOrdenCompra;
    }

    public String getCodOrdenCompra() {
        return codOrdenCompra;
    }

    public void setValorTotalNotaEsCab(double valorTotalNotaEsCab) {
        this.valorTotalNotaEsCab = valorTotalNotaEsCab;
    }

    public double getValorTotalNotaEsCab() {
        return valorTotalNotaEsCab;
    }

    public void setCodEstadoNota(String codEstadoNota) {
        this.codEstadoNota = codEstadoNota;
    }

    public String getCodEstadoNota() {
        return codEstadoNota;
    }
}
