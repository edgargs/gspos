package mifarma.ptoventa.fidelizacion.modelo;

import java.util.List;

public class BeanTipoDocIdentidad {
    private String codigo;
    private String descripcion;
    private String mascara;
    private String separadorMascara;
    private List lstMascaras;
    
    public BeanTipoDocIdentidad(){
        super();
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public String getMascara() {
        return mascara;
    }

    public void setSeparadorMascara(String separadorMascara) {
        this.separadorMascara = separadorMascara;
    }

    public String getSeparadorMascara() {
        return separadorMascara;
    }

    public void setLstMascaras(List lstMascaras) {
        this.lstMascaras = lstMascaras;
    }

    public List getLstMascaras() {
        return lstMascaras;
    }
}
