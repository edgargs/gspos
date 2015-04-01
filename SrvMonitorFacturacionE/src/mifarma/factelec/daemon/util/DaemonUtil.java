package mifarma.factelec.daemon.util;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.Date;


public class DaemonUtil {
    public DaemonUtil() {
        super();
    }
    
    public static boolean ping(String host) throws IOException, InterruptedException {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        String s = null;
        CharSequence csWindows = "bytes=32";
        CharSequence csLinux = "64 bytes";
        boolean bytes = false;
        
        ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows? "-n" : "-c", "1", host);
        Process process;

        process = processBuilder.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((s = stdInput.readLine()) != null) {
            if (!bytes) {
                bytes = s.contains(isWindows?csWindows:csLinux);
            }
        }
        //int returnVal = process.waitFor();
        return bytes;
    }
        
    public static void main(String[] args){
        try {
        System.out.println(new Date());
            System.out.println(ping("172.20.37.2"));
        System.out.println(new Date());        
        } catch (InterruptedException e) {
        } catch (IOException e) {
        }
    }
}
