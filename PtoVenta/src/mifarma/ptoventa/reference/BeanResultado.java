package mifarma.ptoventa.reference;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeanResultado implements Serializable {

    static final Logger log = LoggerFactory.getLogger(BeanResultado.class);

    private String strResultado;

    public void setStrResultado(String strResultado) {
        this.strResultado = strResultado;
    }

    public String getStrResultado() {
        return strResultado;
    }
}
