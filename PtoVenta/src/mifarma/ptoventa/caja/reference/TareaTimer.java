package mifarma.ptoventa.caja.reference;

import java.io.File;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TareaTimer extends TimerTask {

    private static final Logger log = LoggerFactory.getLogger(TareaTimer.class);

    int cant = 1;
    String nombreArchivo = "";
    String indicador = "I"; //I:inicio proceso; D: imagen Disponible , T: termino Tarea
    int cantidadIntentos = 3;

    long tmpT1, tmpT2;

    public String getIndicador() {
        return indicador;
    }

    public void setCantidadIntentos(int cantItentos) {
        if (cantItentos > 0) {
            cantidadIntentos = cantItentos;
        } else {
            cantidadIntentos = 1;
        }
    }

    public void setNombreArchivo(String nameFile) {
        nombreArchivo = nameFile;
    }

    public void run() {
        log.info("intento lectura imagen cupon :" + nombreArchivo + " - intento nro" + cant);
        if (cant++ > cantidadIntentos) {
            log.info("termino el timer de intento de lectura de archivo");
            indicador = "T";
            cancel();
        }

        log.debug("Inicio de instanciar file");
        tmpT1 = System.currentTimeMillis();
        File archivo3 = new File(nombreArchivo);
        tmpT2 = System.currentTimeMillis();
        log.debug("Instanciar File:" + (tmpT2 - tmpT1) + " milisegundos");
        log.debug("Fin de instanciar file");

        if (archivo3.exists() && archivo3.canRead()) {
            log.info("archivo imagen disponible !");
            indicador = "D";
            cancel();
        }
    }


}
