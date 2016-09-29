package mifarma.ptoventa.controlAsistencia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilFtp.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES       04.09.2015   Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author Cesar Huanes<br>
 * @version 1.0<br>
 *
 */
public class ZipFile {
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    public static void zipFile(File inputFile,String parentName,ZipOutputStream zipOutputStream) throws IOException{

        ZipEntry zipEntry = new ZipEntry(parentName+inputFile.getName());
        zipOutputStream.putNextEntry(zipEntry);

        FileInputStream fileInputStream = new FileInputStream(inputFile);
        byte[] buf = new byte[1024];
        int bytesRead;

        // Read the input file by chucks of 1024 bytes
        // and write the read bytes to the zip stream
        while ((bytesRead = fileInputStream.read(buf)) > 0) {
            zipOutputStream.write(buf, 0, bytesRead);
        }

        // close ZipEntry to store the stream to the file
        zipOutputStream.closeEntry();
    } 

    /* ********************************************************************** */
    /*                   Métodos de lógica                                    */
    /* ********************************************************************** */
    public static void zip(String inputFolder,String targetZippedFolder)  throws IOException {

        FileOutputStream fileOutputStream = null;

        fileOutputStream = new FileOutputStream(targetZippedFolder);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        File inputFile = new File(inputFolder);

        if (inputFile.isFile())
            zipFile(inputFile,"",zipOutputStream);
        else if (inputFile.isDirectory())
            zipFolder(zipOutputStream,inputFile,"");

        zipOutputStream.close();
    }

    public static void zipFolder(ZipOutputStream zipOutputStream,File inputFolder, String parentName)  throws IOException {

        String myname = parentName +inputFolder.getName()+"\\";

        ZipEntry folderZipEntry = new ZipEntry(myname);
        zipOutputStream.putNextEntry(folderZipEntry);

        File[] contents = inputFolder.listFiles();

        for (File f : contents){
            if (f.isFile())
                zipFile(f,myname,zipOutputStream);
            else if(f.isDirectory())
                zipFolder(zipOutputStream,f, myname);
        }
        zipOutputStream.closeEntry();
    }
   
}
