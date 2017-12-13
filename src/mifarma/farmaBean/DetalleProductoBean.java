package mifarma.farmaBean;

import java.math.BigDecimal;

public class DetalleProductoBean {
    
    private String tipoDocumento; //3 
    private String descrDocumento; //15
    private String nroDocPago; //16
    private String periodoCotiz; //6
    private String tipDocIdDeudor; //2
    private String nroDocIdDeudor;//15
    private String fechaEmision;//8
    private String fechaVenc;//8
    private BigDecimal importeDocum;
    private String codConcepto1;//2
    private BigDecimal importeConcepto1;
    private String codConcepto2;//2
    private BigDecimal importeConcepto2;
    private String codConcepto3;//2
    private BigDecimal importeConcepto3;
    private String codConcepto4;//2
    private BigDecimal importeConcepto4;
    private String codConcepto5;//2
    private BigDecimal importeConcepto5;
    private Integer indicFacturacion;
    private String nroFactura;
    
    public DetalleProductoBean() {
        
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescrDocumento() {
        return descrDocumento;
    }

    public void setDescrDocumento(String descrDocumento) {
        this.descrDocumento = descrDocumento;
    }

    public String getNroDocPago() {
        return nroDocPago;
    }

    public void setNroDocPago(String nroDocPago) {
        this.nroDocPago = nroDocPago;
    }

    public String getPeriodoCotiz() {
        return periodoCotiz;
    }

    public void setPeriodoCotiz(String periodoCotiz) {
        this.periodoCotiz = periodoCotiz;
    }

    public String getTipDocIdDeudor() {
        return tipDocIdDeudor;
    }

    public void setTipDocIdDeudor(String tipDocIdDeudor) {
        this.tipDocIdDeudor = tipDocIdDeudor;
    }

    public String getNroDocIdDeudor() {
        return nroDocIdDeudor;
    }

    public void setNroDocIdDeudor(String nroDocIdDeudor) {
        this.nroDocIdDeudor = nroDocIdDeudor;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(String fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public BigDecimal getImporteDocum() {
        return importeDocum;
    }

    public void setImporteDocum(BigDecimal importeDocum) {
        this.importeDocum = importeDocum;
    }

    public String getCodConcepto1() {
        return codConcepto1;
    }

    public void setCodConcepto1(String codConcepto1) {
        this.codConcepto1 = codConcepto1;
    }

    public BigDecimal getImporteConcepto1() {
        return importeConcepto1;
    }

    public void setImporteConcepto1(BigDecimal importeConcepto1) {
        this.importeConcepto1 = importeConcepto1;
    }

    public String getCodConcepto2() {
        return codConcepto2;
    }

    public void setCodConcepto2(String codConcepto2) {
        this.codConcepto2 = codConcepto2;
    }

    public BigDecimal getImporteConcepto2() {
        return importeConcepto2;
    }

    public void setImporteConcepto2(BigDecimal importeConcepto2) {
        this.importeConcepto2 = importeConcepto2;
    }

    public String getCodConcepto3() {
        return codConcepto3;
    }

    public void setCodConcepto3(String codConcepto3) {
        this.codConcepto3 = codConcepto3;
    }

    public BigDecimal getImporteConcepto3() {
        return importeConcepto3;
    }

    public void setImporteConcepto3(BigDecimal importeConcepto3) {
        this.importeConcepto3 = importeConcepto3;
    }

    public String getCodConcepto4() {
        return codConcepto4;
    }

    public void setCodConcepto4(String codConcepto4) {
        this.codConcepto4 = codConcepto4;
    }

    public BigDecimal getImporteConcepto4() {
        return importeConcepto4;
    }

    public void setImporteConcepto4(BigDecimal importeConcepto4) {
        this.importeConcepto4 = importeConcepto4;
    }

    public String getCodConcepto5() {
        return codConcepto5;
    }

    public void setCodConcepto5(String codConcepto5) {
        this.codConcepto5 = codConcepto5;
    }

    public BigDecimal getImporteConcepto5() {
        return importeConcepto5;
    }

    public void setImporteConcepto5(BigDecimal importeConcepto5) {
        this.importeConcepto5 = importeConcepto5;
    }

    public Integer getIndicFacturacion() {
        return indicFacturacion;
    }

    public void setIndicFacturacion(Integer indicFacturacion) {
        this.indicFacturacion = indicFacturacion;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }
}
