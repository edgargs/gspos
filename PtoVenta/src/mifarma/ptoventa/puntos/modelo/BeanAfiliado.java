package mifarma.ptoventa.puntos.modelo;

import farmapuntos.bean.Afiliado;

public class BeanAfiliado extends Afiliado {
    
    private String numTarjetaLocal;
    private String codTipoDocumento;
    private BeanTarjetaMonedero tarjetaMonedero;

    
    public BeanAfiliado() {
        super();
    }
    
    public BeanAfiliado(Afiliado afiliado) {
        super();
        this.nombre = afiliado.getNombre();
        this.apParterno = afiliado.getApParterno();
        this.apMarterno = afiliado.getApMarterno();
        this.fechaNacimiento = afiliado.getFechaNacimiento();
        this.genero = afiliado.getGenero();
        this.email = afiliado.getEmail();
        this.telefono = afiliado.getTelefono();  
        this.direccion = afiliado.getDireccion();
        this.departamento = afiliado.getDepartamento();
        this.provincia = afiliado.getProvincia();
        this.distrito = afiliado.getDistrito();
        this.tipoDireccion = afiliado.getTipoDireccion();
        this.referencias = afiliado.getReferencias();
        this.tipoLugar = afiliado.getTipoLugar();
        this.celular = afiliado.getCelular();
        this.informacionAdicional = afiliado.getInformacionAdicional();
        this.direccionNormalizada = afiliado.getDireccionNormalizada();
        this.dni = afiliado.getDni();
        this.tarjetas = afiliado.getTarjetas();
    }

    public void setCodTipoDocumento(String codTipoDocumento) {
        this.codTipoDocumento = codTipoDocumento;
    }

    public String getCodTipoDocumento() {
        return codTipoDocumento;
    }

    public void setTarjetaMonedero(BeanTarjetaMonedero tarjetaMonedero) {
        this.tarjetaMonedero = tarjetaMonedero;
    }

    public BeanTarjetaMonedero getTarjetaMonedero() {
        return tarjetaMonedero;
    }

    public void setNumTarjetaLocal(String numTarjetaLocal) {
        this.numTarjetaLocal = numTarjetaLocal;
    }

    public String getNumTarjetaLocal() {
        return numTarjetaLocal;
    }
}
