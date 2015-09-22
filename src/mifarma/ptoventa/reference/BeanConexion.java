package mifarma.ptoventa.reference;

import mifarma.common.FarmaConstants;


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
    private String ServiceName;
    private int TimeOut;
    private String cadenaConexion;
    
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

    public void setServiceName(String ServiceName) {
        this.ServiceName = ServiceName;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public String getCadenaConexionW() {
        
        if(ServiceName != null && !ServiceName.equals("")){
            cadenaConexion = String.format(FarmaConstants.CONNECT_STRING_SERVICENAME_W, getIPBD() , getPORT() , getServiceName());
        }else{
            cadenaConexion = String.format(FarmaConstants.CONNECT_STRING_SID_W, getIPBD() , getPORT() , getSID());
        }
        
        return cadenaConexion;
    }
    
    public String getCadenaConexion() {
        
        if(ServiceName != null && !ServiceName.equals("")){
            cadenaConexion = String.format(FarmaConstants.CONNECT_STRING_SERVICENAME, getUsuarioBD(), getClaveBD(), getIPBD() , getPORT() , getServiceName());
        }else{
            cadenaConexion = String.format(FarmaConstants.CONNECT_STRING_SID, getUsuarioBD(), getClaveBD(), getIPBD() , getPORT() , getSID());
        }
        
        return cadenaConexion;
    }

    public void setTimeOut(int TimeOut) {
        this.TimeOut = TimeOut;
    }

    public int getTimeOut() {
        return TimeOut;
    }
}
