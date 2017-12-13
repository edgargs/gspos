package mifarma.farmaBean;

import java.math.BigDecimal;

import java.util.List;


public class ProductoBean {
    
    private String codProd; //3
    private String descrProd; //15
    private BigDecimal imporeTotal;
    private String mensaje1; //40 
    private String mensaje2; //40
    private Integer nroDocumentos;
    private List<DetalleProductoBean> detalleProductos;
    
    public ProductoBean() {
        
    }

    public List<DetalleProductoBean> getDetalleProductos() {
        return detalleProductos;
    }

    public void setDetalleProductos(List<DetalleProductoBean> detalleProductos) {
        this.detalleProductos = detalleProductos;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getDescrProd() {
        return descrProd;
    }

    public void setDescrProd(String descrProd) {
        this.descrProd = descrProd;
    }

    public BigDecimal getImporeTotal() {
        return imporeTotal;
    }

    public void setImporeTotal(BigDecimal imporeTotal) {
        this.imporeTotal = imporeTotal;
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

    public Integer getNroDocumentos() {
        return nroDocumentos;
    }

    public void setNroDocumentos(Integer nroDocumentos) {
        this.nroDocumentos = nroDocumentos;
    }
}
