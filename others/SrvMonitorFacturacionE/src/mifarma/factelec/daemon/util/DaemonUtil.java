package mifarma.factelec.daemon.util;

import java.io.IOException;

import java.util.Date;


public class DaemonUtil {
    public DaemonUtil() {
        super();
    }
    
    public static boolean ping(String host) throws IOException, InterruptedException {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows? "-n" : "-c", "1", host);
        Process proc = processBuilder.start();

        int returnVal = proc.waitFor();
        return returnVal == 0;
    }
        
    public static void main(String[] args){
        try {
            System.out.println(new Date());
            System.out.println(ping("10.100.1.2"));
            System.out.println(new Date());
        } catch (InterruptedException e) {
        } catch (IOException e) {
        }
    }
}
