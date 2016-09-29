package mifarma.ptoventa.cliente.modelo;

/**
 * @author KMONCADA 
 * @since 13.04.2016
 */
public class BeanClienteJuridico {
    
    private String codCliente = "";
    private String numDocumento = "";
    private String razonSocial = "";
    private String direccion = "";
    private String agenteRetencion;
    private String agentePercepcion;
    private String clienteDigemid;
    private boolean isAgenteRetencion = false;
    private boolean isAgentePercepcion = false;
    private boolean isClienteDigemid = false;
    private boolean isClienteMayorista = false;
    
    
    public BeanClienteJuridico() {
        super();
        setAgentePercepcion("N");
        setAgenteRetencion("N");
        setClienteDigemid("N");
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setAgenteRetencion(String agenteRetencion) {
        this.agenteRetencion = agenteRetencion;
        if("S".equalsIgnoreCase(agenteRetencion)){
            isAgenteRetencion = true;
        }else{
            isAgenteRetencion = false;
        }
    }
    
    public String getAgenteRetencion(){
        return this.agenteRetencion;
    }

    public void setAgentePercepcion(String agentePercepcion) {
        this.agentePercepcion = agentePercepcion;
        if("S".equalsIgnoreCase(agentePercepcion)){
            isAgentePercepcion = true;
        }else{
            isAgentePercepcion = false;
        }
    }
    
    public String getAgentePercepcion(){
        return this.agentePercepcion;
    }

    public void setIsAgenteRetencion(boolean isAgenteRetencion) {
        this.isAgenteRetencion = isAgenteRetencion;
        if(isAgenteRetencion){
            agenteRetencion = "S";
        }else{
            agenteRetencion = "N";
        }
    }

    public boolean isIsAgenteRetencion() {
        return isAgenteRetencion;
    }

    public void setIsAgentePercepcion(boolean isAgentePercepcion) {
        this.isAgentePercepcion = isAgentePercepcion;
        if(this.isAgentePercepcion){
            agentePercepcion = "S";
        }else{
            agentePercepcion = "N";
        }
    }

    public boolean isIsAgentePercepcion() {
        return isAgentePercepcion;
    }

    public String getClienteDigemid() {
        return this.clienteDigemid;
    }
    
    public void setClienteDigemid(String clienteDigemid) {
        this.clienteDigemid = clienteDigemid;
        if("S".equalsIgnoreCase(clienteDigemid)){
            isClienteDigemid = true;
        }else{
            isClienteDigemid = false;
        }
    }

    public void setIsClienteDigemid(boolean isClienteDigemid) {
        this.isClienteDigemid = isClienteDigemid;
    }

    public boolean isIsClienteDigemid() {
        return isClienteDigemid;
    }

    public void setIsClienteMayorista(boolean isClienteMayorista) {
        this.isClienteMayorista = isClienteMayorista;
    }

    public boolean isIsClienteMayorista() {
        return isClienteMayorista;
    }
}
