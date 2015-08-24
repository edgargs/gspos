package mifarma.ptoventa.reference;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeanResultado implements Serializable {

    static final Logger log = LoggerFactory.getLogger(BeanResultado.class);

    @SuppressWarnings("compatibility:-7370347038733457699")
    private static final long serialVersionUID = 1L;

    private String strResultado;
    private String strIndicador;

    public void setStrResultado(String strResultado) {
        this.strResultado = strResultado;
    }

    public String getStrResultado() {
        return strResultado;
    }

    public void setStrIndicador(String strIndicador) {
        this.strIndicador = strIndicador;
    }

    public String getStrIndicador() {
        return strIndicador;
    }
}
