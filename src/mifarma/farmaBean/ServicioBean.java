package mifarma.farmaBean;

import java.util.List;

public class ServicioBean {
    
    private String codServicio; //3
    private String monedaServ; //3
    private String estadoDeudor; //2
    private String mensaje1;  //40
    private String mensaje2 ;     //40              
    private Integer indiCronolPago ; // 
    private Integer permitePagVenc;
    private Integer restricPago ;                
    private Integer nroDocsServicio;
    private List<DocumentoBean> documentos;

    public ServicioBean() {
        
    }

    public String getCodServicio() {
        return codServicio;
    }

    public void setCodServicio(String codServicio) {
        this.codServicio = codServicio;
    }

    public String getMonedaServ() {
        return monedaServ;
    }

    public void setMonedaServ(String monedaServ) {
        this.monedaServ = monedaServ;
    }

    public String getEstadoDeudor() {
        return estadoDeudor;
    }

    public void setEstadoDeudor(String estadoDeudor) {
        this.estadoDeudor = estadoDeudor;
    }

    public String getMensaje1() {
        return mensaje1;
    }

    public void setMensaje1(String mensaje1) {
        this.mensaje1 = mensaje1;
    }

    public String getMensaje2() {
        return mensaje2;
    }

    public void setMensaje2(String mensaje2) {
        this.mensaje2 = mensaje2;
    }

    public Integer getIndiCronolPago() {
        return indiCronolPago;
    }

    public void setIndiCronolPago(Integer indiCronolPago) {
        this.indiCronolPago = indiCronolPago;
    }

    public Integer getPermitePagVenc() {
        return permitePagVenc;
    }

    public void setPermitePagVenc(Integer permitePagVenc) {
        this.permitePagVenc = permitePagVenc;
    }

    public Integer getRestricPago() {
        return restricPago;
    }

    public void setRestricPago(Integer restricPago) {
        this.restricPago = restricPago;
    }

    public Integer getNroDocsServicio() {
        return nroDocsServicio;
    }

    public void setNroDocsServicio(Integer nroDocsServicio) {
        this.nroDocsServicio = nroDocsServicio;
    }

    public List<DocumentoBean> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoBean> documentos) {
        this.documentos = documentos;
    }
}
