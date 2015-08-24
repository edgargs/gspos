package mifarma.ptoventa.caja.reference;

public class PedVtaRimacDTO {
    
    private String numPedVta;
    private String numCompPago;
    private String codProd;
    private int cantMeses;
    private String valPrecVta;
    private String valPrecTotal;
    private String dniRimac;
    private String fecPedVta;
    
    public PedVtaRimacDTO() {
        super();
    }

    public String getNumPedVta() {
        return numPedVta;
    }

    public void setNumPedVta(String numPedVta) {
        this.numPedVta = numPedVta;
    }

    public String getNumCompPago() {
        return numCompPago;
    }

    public void setNumCompPago(String numCompPago) {
        this.numCompPago = numCompPago;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public int getCantMeses() {
        return cantMeses;
    }

    public void setCantMeses(int cantMeses) {
        this.cantMeses = cantMeses;
    }

    public String getValPrecVta() {
        return valPrecVta;
    }

    public void setValPrecVta(String valPrecVta) {
        this.valPrecVta = valPrecVta;
    }

    public String getValPrecTotal() {
        return valPrecTotal;
    }

    public void setValPrecTotal(String valPrecTotal) {
        this.valPrecTotal = valPrecTotal;
    }

    public String getDniRimac() {
        return dniRimac;
    }

    public void setDniRimac(String dniRimac) {
        this.dniRimac = dniRimac;
    }

    public String getFecPedVta() {
        return fecPedVta;
    }

    public void setFecPedVta(String fecPedVta) {
        this.fecPedVta = fecPedVta;
    }
}
