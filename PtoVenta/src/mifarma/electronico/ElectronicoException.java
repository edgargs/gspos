package mifarma.electronico;

public class ElectronicoException extends Exception {
    public ElectronicoException() {
        super();
    }
    
    public ElectronicoException(String mensaje) {
        super(mensaje);
    }
}
