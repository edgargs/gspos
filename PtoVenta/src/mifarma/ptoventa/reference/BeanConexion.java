package mifarma.ptoventa.reference;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : BeanConexion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      19.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class BeanConexion {
    
    private String IPBD;
    private String SID;
    private String UsuarioBD;
    private String ClaveBD;
    private String PortBD;
    
    public BeanConexion() {
        super();
    }

    public void setIPBD(String IPBD) {
        this.IPBD = IPBD;
    }

    public String getIPBD() {
        return IPBD;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getSID() {
        return SID;
    }

    public void setUsuarioBD(String UsuarioBD) {
        this.UsuarioBD = UsuarioBD;
    }

    public String getUsuarioBD() {
        return UsuarioBD;
    }

    public void setClaveBD(String ClaveBD) {
        this.ClaveBD = ClaveBD;
    }

    public String getClaveBD() {
        return ClaveBD;
    }

    public void setPORT(String PortBD) {
        this.PortBD = PortBD;
    }

    public String getPORT() {
        return PortBD;
    }
}
