package mifarma.ptoventa.reportes.modelo.ConcursoGigante;

public class BeanResumenReporteGigante {
    
    private String categoria;
    private String valMeta;
    private String valAvance;
    private String valCumplimiento;
    
    public BeanResumenReporteGigante() {
        super();
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setValMeta(String valMeta) {
        this.valMeta = valMeta;
    }

    public String getValMeta() {
        return valMeta;
    }

    public void setValAvance(String valAvance) {
        this.valAvance = valAvance;
    }

    public String getValAvance() {
        return valAvance;
    }

    public void setValCumplimiento(String valCumplimiento) {
        this.valCumplimiento = valCumplimiento;
    }

    public String getValCumplimiento() {
        return valCumplimiento;
    }
}
