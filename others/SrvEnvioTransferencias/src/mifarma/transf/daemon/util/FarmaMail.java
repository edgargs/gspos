package mifarma.transf.daemon.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FarmaMail {
    private static final Logger log = LoggerFactory.getLogger(FarmaMail.class);
    
    private static String vHostName;
    private static String vFromEmail;
    private static String vFromName;
    private static String vTos;

    public FarmaMail() {
    }

    public static void enviaMail(String mensaje) {
        SimpleEmail email = new SimpleEmail();
        try {
            email.setHostName("10.18.0.17");
            email.addTo("erios@mifarma.com.pe");
            email.setFrom("srvenviotransferencias@mifarma.com.pe", "SrvEnvioTransferencias");
            email.setSubject("Alerta SrvEnvioTransferencias");
            email.setMsg((new StringBuilder()).append("").append(mensaje).toString());
            email.send();
        } catch (EmailException e) {
            log.error((new StringBuilder()).append("Fallo al enviar mail: ").append(mensaje).toString());
        }
    }

    public static void enviaMail(String mensaje, Properties servermail) {
        SimpleEmail email = new SimpleEmail();
        try {
            iniciaConfiguracion(servermail);
            email.setHostName(vHostName);
            String vDest[] = vTos.split(";");
            for (int i = 0; i < vDest.length; i++)
                email.addTo(vDest[i]);

            email.setFrom(vFromEmail, vFromName);
            email.setSubject("Alerta SrvEnvioTransferencias");
            email.setMsg((new StringBuilder()).append("").append(mensaje).toString());
            email.send();
        } catch (Exception e) {
            log.warn((new StringBuilder()).append("Fallo al enviar mail: ").append(mensaje).toString());
            log.error("",e);
        } 
    }

    public static void iniciaConfiguracion(Properties servermail) throws FileNotFoundException, IOException {
        vHostName = servermail.getProperty("HostName");
        vFromEmail = servermail.getProperty("FromEmail");
        vFromName = servermail.getProperty("FromName");
        vTos = servermail.getProperty("Tos");
    }

    public static void main(String args[]) {
        enviaMail("hello from javamail without properties");
    }


}
