package mifarma.common;

import java.io.Serializable;

import java.util.ArrayList;

public class FarmaBResult implements Serializable {

    private Object object;
    private int code;
    private Exception exception = null;
    private String message;

    /**
     * Constructor for BResult.
     */
    public FarmaBResult() {
        this(0, null);
    }

    /**
     * Inicializa un objeto BResult con un codigo y objeto determinados
     * @param code
     * @param object
     */
    public FarmaBResult(int code, Object object) {
        super();
        this.code = code;
        this.object = object;
    }

    /**
     * Inicializa un objeto BResult con un objeto determinado
     * @param object
     */
    public FarmaBResult(Object object) {
        this(0, object);
    }

    /**
     * Inicializa un objeto BResult con un objeto de valor booleano
     * @param booleanValue
     */
    public FarmaBResult(boolean booleanValue) {
        this(new Boolean(booleanValue));
    }

    /**
     * Inicializa un objeto BResult con un objeto de valor Integer
     * @param intValue
     */
    public FarmaBResult(int intValue) {
        this(new Integer(intValue));
    }

    /**
     * Inicializa un objeto BResult con un código y una excepcion.
     * Utilizado para devolver resultados de errores
     * @param codigo
     * @param exceptionThrown
     */
    public FarmaBResult(Exception exceptionThrown) {
        super();
        this.code = FarmaConstants.CODIGO_SATISFACTORIO;
        this.object = null;
        this.exception = exceptionThrown;
    }

    /**
     * Devuelve el objeto contenido como ArrayList
     * @author Carlos Carrasco
     * @return
     */
    public ArrayList getArrayList() throws ClassCastException {
        if (this.exception != null)
            return new ArrayList();
        else
            return (ArrayList)object;
    }

    /**
     * @Devuelve el objeto contenido commo boolean
     * @return
     * @throws ClassCastException
     */
    public boolean getBooleanValue() throws ClassCastException {
        return ((Boolean)object).booleanValue();
    }

    /**
     * @Devuelve el objeto contenido como integer
     * @return
     * @throws ClassCastException
     */
    public int getIntegerValue() throws ClassCastException {
        return ((Integer)object).intValue();
    }

    /**
     * Returns the code.
     * @return byte
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the message.
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the object.
     * @return Object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets the code.
     * @param code The code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Sets the message.
     * @param message The message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the object.
     * @param object The object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @return
     */
    public Exception getException() {
        return exception;
    }

    /**
     * @param exception
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

}
