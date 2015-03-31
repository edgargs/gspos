package mifarma.ptoventa.pinpad.reference;

public class FacadePinpad {
    private Integer timeOut = 2; //tiempo en segundos de respuesta

    public FacadePinpad() {
        super();
    }

    public void realizarOpcionFinanciera(Double monto, String codTienda, String caja) {
        DummyPinpad d = new DummyPinpad();
        Integer retorno = null;
        //obtener trama para valores indicados

        //abre el puerto de conexion del pinpad
        //d.openPort();

        //iniciar la operación en el pinpad
        //d.startOperation("", this.timeOut, retorno);

        //cierra el puerto de conexion del pinpad
        //d.closePort();
    }
}
