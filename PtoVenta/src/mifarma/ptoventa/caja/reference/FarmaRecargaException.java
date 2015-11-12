package mifarma.ptoventa.caja.reference;

public class FarmaRecargaException extends Exception {
    public FarmaRecargaException(String string, Throwable throwable, boolean b, boolean b1) {
        super(string, throwable, b, b1);
    }

    public FarmaRecargaException(Throwable throwable) {
        super(throwable);
    }

    public FarmaRecargaException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public FarmaRecargaException(String string) {
        super(string);
    }

    public FarmaRecargaException() {
        super();
    }
}
