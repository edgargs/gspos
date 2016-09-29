package mifarma.ptoventa.controlAsistencia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import mifarma.common.FarmaVariables;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilFtp.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author Cesar Huanes<br>
 * @version 1.0<br>
 *
 */
public class UtilFtp {
    private static final Logger log = LoggerFactory.getLogger(UtilFtp.class);
   
    public static boolean conectarFtp(String directorioLocal, String directorioFtp,
                                      String numRegistroSol, String usuarioFtp,
                                      String passwordFtp, int puertoFtp,
                                      String servidorFtp){
       
             boolean flag=false;            
             FTPClient ftpClient = new FTPClient();
             try {
                ftpClient.connect(servidorFtp, puertoFtp);
                ftpClient.login(usuarioFtp, passwordFtp);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                
                File file = new File(directorioLocal);
                String fileRemote =nuevoNombreArchivo(directorioLocal,numRegistroSol);
                ftpClient.changeWorkingDirectory(directorioFtp);
                InputStream inputStream = new FileInputStream(file);    
                 
                log.info("COMIENZA A CARGAR");
                OutputStream outputStream = ftpClient.storeFileStream(fileRemote);            
                if(outputStream!=null){
                log.info("LOG OUT OK");
                byte[] bytesIn = new byte[4096];
                int read = 0;
                 
                 while ((read = inputStream.read(bytesIn)) != -1) {
                     outputStream.write(bytesIn, 0, read);
                 }
                 inputStream.close();
                 outputStream.close();
                 
                 boolean completed = ftpClient.completePendingCommand();
                 if (completed) {
                     log.info("carga exitosa");  
                     flag=true;                    
                 }
                 
                }else{
                    log.info("LOG OUT ERROR");
                   flag=false; 
                }
                    
             } catch (IOException ex) {                 
                 flag=false;
                 log.error("Error : "+ex.getMessage());

             } finally {
                 try {
                     if (ftpClient.isConnected()) {
                         ftpClient.logout();
                         ftpClient.disconnect();
                     }
                 } catch (IOException ex) {
                     log.error("Error : "+ex.getMessage());
                 }
             }
             return flag;
         }
    
    public static  String nuevoNombreArchivo(String nomArchivo,String numRegistroSol){
        String nomFile="";
        String extension = "";
        log.info("Nombre completo del archivo "+nomArchivo);
        int i = nomArchivo.lastIndexOf('.');
        if (i > 0) {
            extension = nomArchivo.substring(i+1,nomArchivo.length());
        }
        nomFile=FarmaVariables.vCodGrupoCia+FarmaVariables.vCodLocal+numRegistroSol+"."+extension;
        log.info("Nuevo Nombre con extensión " +extension);
        return nomFile;
    }
   
}
