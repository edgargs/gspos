package mifarma.ptoventa.puntos.modelo;

public class BeanListaProductos {
    
    private String articulo;
    private String cantidad;
    private String precioUnitario;
    private String importe;
    private String puntos;
    private String puntosAhorro;
                  
    public BeanListaProductos() {
        super();
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getImporte() {
        return importe;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntosAhorro(String puntosAhorro) {
        this.puntosAhorro = puntosAhorro;
    }

    public String getPuntosAhorro() {
        return puntosAhorro;
    }
}
