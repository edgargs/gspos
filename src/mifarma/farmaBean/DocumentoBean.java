package mifarma.farmaBean;

import java.math.BigDecimal;

public class DocumentoBean {

    private String tipDocPago; //3
    private String numDocPago; //16 
    private String referDeuda; //16
    private String fechaVenc; //8
    private BigDecimal importeMinPago;
    private BigDecimal saldoTotalPagar;

    
    public DocumentoBean() {
        
    }

    public String getTipDocPago() {
        return tipDocPago;
    }

    public void setTipDocPago(String tipDocPago) {
        this.tipDocPago = tipDocPago;
    }

    public String getNumDocPago() {
        return numDocPago;
    }

    public void setNumDocPago(String numDocPago) {
        this.numDocPago = numDocPago;
    }

    public String getReferDeuda() {
        return referDeuda;
    }

    public void setReferDeuda(String referDeuda) {
        this.referDeuda = referDeuda;
    }

    public String getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(String fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public BigDecimal getImporteMinPago() {
        return importeMinPago;
    }

    public void setImporteMinPago(BigDecimal importeMinPago) {
        this.importeMinPago = importeMinPago;
    }

    public BigDecimal getSaldoTotalPagar() {
        return saldoTotalPagar;
    }

    public void setSaldoTotalPagar(BigDecimal saldoTotalPagar) {
        this.saldoTotalPagar = saldoTotalPagar;
    }
}
