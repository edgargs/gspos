package mifarma.ptoventa.inventario.precioCompetencia.reference;

import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : MultipartFileUploadApp.java<br>
 * fuente web: http://www.veereshr.com/Java/Upload
 * <br>
 * Histórico de Creación/Modificación<br>
 * clarico      16.12.2014   Creación
 * <br>
 * @author Celso Larico Mullisaca<br>
 * @version 1.0<br>
 *
 */ 
public class MultipartFileUploadApp {
  private static final Logger log = LoggerFactory.getLogger(MultipartFileUploadApp.class);  
  public static boolean upload(File pFile) throws Exception {
        boolean exito = false;
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(ConstantsPrecioCompetencia.URL_UPLOAD_PHP);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody contentFile = new FileBody(pFile);
        mpEntity.addPart("fileGPrecios", contentFile);
        httppost.setEntity(mpEntity);
        log.info("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity(); 
 
        if((response.getStatusLine().toString()).equals("HTTP/1.1 200 OK")){
            // Successfully Uploaded
            exito = true;
        }
        else{
            // Did not upload. Add your logic here. Maybe you want to retry.
            exito = false;
        }
        log.info(response.getStatusLine().toString());
        if (resEntity != null) {
            log.info(EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
            resEntity.consumeContent();
        }
        httpclient.getConnectionManager().shutdown();
        
        return exito;
    }
}