package mifarma.transf.daemon.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Date;


public class DaemonUtil {
    public DaemonUtil() {
        super();
    }
    
    public static boolean ping(String host){
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        String s = null;
        CharSequence csWindows = "bytes=32";
        CharSequence csLinux = "64 bytes";
        boolean bytes = false;
        
        ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows? "-n" : "-c", "1", host);
        Process process;

        try {
            process = processBuilder.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                if (!bytes) {
                    bytes = s.contains(isWindows?csWindows:csLinux);
                }
            }

            /*
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            // read any errors from the attempted command
            System.err.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null)
            {
              System.err.println(s);
            }*/

            int returnVal = process.waitFor();

        } catch (Exception e) {
            bytes = false;
        }

        return bytes;//returnVal == 0;
    }
        
    public static void main(String[] args){
        System.out.println(new Date());
        System.out.println(ping("172.20.147.2"));
        System.out.println(new Date());        
    }
}
