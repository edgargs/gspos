package mifarma.ptoventa.inventario.dto;

import java.util.Date;


public class OrdenCompraDetDTO {

    private String codGrupoCia;
    private String codCia;
    private String codLocal;
    private String codOrdenCompra;
    private String setDetNotaEs;
    private String codProd;
    private String cantRecep;
    private String cantPedido;
    private String usuaCreado;
    private Date fechaCreado;
    private String usuaModifi;
    private Date fechaModifi;
    private Date fecProceSab;
    private Date fecProceCe;
    private Double precTotal;
    private Double precProduc;
    private Double detIvProd;


    public void setCodGrupoCia(String codGrupoCia) {
        this.codGrupoCia = codGrupoCia;
    }

    public String getCodGrupoCia() {
        return codGrupoCia;
    }

    public void setCodCia(String codCia) {
        this.codCia = codCia;
    }

    public String getCodCia() {
        return codCia;
    }

    public void setCodLocal(String codLocal) {
        this.codLocal = codLocal;
    }

    public String getCodLocal() {
        return codLocal;
    }

    public void setCodOrdenCompra(String codOrdenCompra) {
        this.codOrdenCompra = codOrdenCompra;
    }

    public String getCodOrdenCompra() {
        return codOrdenCompra;
    }

    public void setSetDetNotaEs(String setDetNotaEs) {
        this.setDetNotaEs = setDetNotaEs;
    }

    public String getSetDetNotaEs() {
        return setDetNotaEs;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCantRecep(String cantRecep) {
        this.cantRecep = cantRecep;
    }

    public String getCantRecep() {
        return cantRecep;
    }

    public void setCantPedido(String cantPedido) {
        this.cantPedido = cantPedido;
    }

    public String getCantPedido() {
        return cantPedido;
    }

    public void setUsuaCreado(String usuaCreado) {
        this.usuaCreado = usuaCreado;
    }

    public String getUsuaCreado() {
        return usuaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setUsuaModifi(String usuaModifi) {
        this.usuaModifi = usuaModifi;
    }

    public String getUsuaModifi() {
        return usuaModifi;
    }

    public void setFechaModifi(Date fechaModifi) {
        this.fechaModifi = fechaModifi;
    }

    public Date getFechaModifi() {
        return fechaModifi;
    }

    public void setFecProceSab(Date fecProceSab) {
        this.fecProceSab = fecProceSab;
    }

    public Date getFecProceSab() {
        return fecProceSab;
    }

    public void setFecProceCe(Date fecProceCe) {
        this.fecProceCe = fecProceCe;
    }

    public Date getFecProceCe() {
        return fecProceCe;
    }

    public void setPrecTotal(Double precTotal) {
        this.precTotal = precTotal;
    }

    public Double getPrecTotal() {
        return precTotal;
    }

    public void setPrecProduc(Double precProduc) {
        this.precProduc = precProduc;
    }

    public Double getPrecProduc() {
        return precProduc;
    }

    public void setDetIvProd(Double detIvProd) {
        this.detIvProd = detIvProd;
    }

    public Double getDetIvProd() {
        return detIvProd;
    }
}
