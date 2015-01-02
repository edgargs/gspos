package mifarma.electronico.epos;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.Socket;

import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JTextField;

import mifarma.electronico.epos.reference.EposVariables;


public class UtilityPruebaEpos {

    // static private final Logger log = LoggerFactory.getLogger(Test_EPos.class);
    public static boolean muestraTiempos = true;
    public static Properties properties;
    public static Socket socketServidor;
    public static PrintStream escribir;
    public static BufferedReader leer;
    public static String tipodocumentoSunat;

    public static String mensaje;
    public static String mensaje_confirmacion;
    public static String respuesta;


    public UtilityPruebaEpos() {
        super();
    }


    public static void main(String args[]) {
        System.out.println("Inicio");
        String pMsj = test();
        System.out.println("FIN");
        //System.out.println(""+pMsj);
        /*
    SocketCliente sc = new SocketCliente();
    //  mensaje = sc.mensajeConfiguracion("20512002090","1");

    //factura 0000473711 0000383175
    //   mensaje = sc.mensajeGenerarDE("20512002090", "05", "1", "0000473716", "0000383177", "");

    //mensaje = sc.mensajeGenerarDE( "20512002090", "02","1", "0000473715","0000383176","");//factura
    //mensaje = sc.mensajeGenerarDE( "20512002090", "02","1", "0000473725","0000383183","");//factura
    //mensaje = sc.mensajeGenerarDE( "20512002090", "05","1", "0000473717","0000383178","");//delivery
    // mensaje = sc.mensajeGenerarDE( "20512002090", "05","1", "0000473724","0000383182","");//boleta


    //DESA 2
    // mensaje = sc.mensajeGenerarDE( "20512002090", "05","1", "0000038770","0000034390","");//boleta



    // mensaje = sc.mensajeConfirmacion("20512002090","1","0000473696","0000383160","04","FF99-226");//nc
    //mensaje = sc.mensajeConfirmacion("20512002090","1","0000473725","0000383183","02","FF99-244");

    //NOTA DE CREDITO|
    //mensaje = sc.mensajeGenerarDE("20512002090","04","1", "0000473696","0000383160","");
    //mensaje = sc.mensajeGenerarDE( "20512002090", "05","1", "0000473724","0000383182","");//boleta
     * */
    }

    public static String test() {
        JTextField textField;
        String msj = "";
        JDialog dialog = new JDialog();
        long tmpT1, tmpT2;
        try {
            // CARGA VARIABLES DE PRUEBA
            EposVariables.vIpSocketServidor = "10.11.1.205";
            EposVariables.vPuertoEPOS = 5500;
            EposVariables.vModo = "0";
            //Crear conexion con el socket servidor
            tmpT1 = System.currentTimeMillis();
            socketServidor = new Socket(EposVariables.vIpSocketServidor, EposVariables.vPuertoEPOS);

            //Crear referencias al canal de escritura
            escribir = new PrintStream(socketServidor.getOutputStream());

            //Crear referencias al canal de lectura
            leer = new BufferedReader(new InputStreamReader(socketServidor.getInputStream()));
            tmpT2 = System.currentTimeMillis();
            msj = msj + "\n" +
                    "A||Carga Variables de Socket  ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
            //validar datos que no esten vacios
            if ((!EposVariables.vModo.equals(""))) {

                //Genera estructura del mensaje a enviar
                /*mensaje = "@**@" + Constante.tipoMensaje.CONFIGURACION + "\t" + //Indentificador mensaje
                        FarmaVariables.vModo + "\t" + //modo ejecucion: 1- prueba, <> 1 -produccion
                        FarmaVariables.vNuRucCia + "\t" + //ruc del emisor
                        FarmaVariables.vIdPos + "\t" + //identificadorPos - identificador pos
                        FarmaVariables.vCodLocal + "*@@*"; //FarmaVariables.vCodLocal
                */
                //mensaje = "@**@10178506*@@*";
                mensaje = "@**@" + "E2" + "\t" + // Linea 1
                        "0" + "\t" + // Linea 2
                        "20305354563" + "\t" + // Linea 3
                        "178" + "\t" + // Linea 4
                        "0" + "\t" + // Linea 5
                        "EN|03|0||||2014-09-26|PEN|20305354563|6|FASA|FARMACIAS PERUANAS S.A.|010101|CAL. VICTOR ALZAMORA NRO. 147 URB. SANTA CATALINA - LA VICTORIA - LIMA|AMAZONAS|CHACHAPOYAS|CHACHAPOYAS|0|0|-| |6.53|1.17|0.00|0|7.70|||0|0|PE||||||||||||||||||||||||01||2014-09-26" +
                        "Doc|1001|6.53" + "DN|1|1000|SIETE Y 70/100" + "DE|1|7.70|NIU|1.00|6.53|573594|01|6.53|6.53" +
                        "DEDI|1/1 47 STREET AT SPRAY LOVE" + "DEDR|0|0.00" +
                        "DEIM|1.17|6.53|1.17|18.00|VAT|10|00|1000|IGV|VAT" + "DI|1.17|1.17|1000|IGV|VAT" +
                        "PES|MensajesAt" + "PESD|1|REDONDEO           0.00" + "PESD|2|EFECTIVO SOLES 8.00" +
                        "PESD|4|Guarda tu voucher," + "PESD|4|Es el sustento para validar tu compra." +
                        "PES|MensajesDt" + "PESD|1|No se aceptan devoluciones de dinero." +
                        "PESD|2|Cambio de mercaderia unicamente dentro" +
                        "PESD|3|de las 48 horas siguientes a la compra." +
                        "PESD|4|Indispensable presentar comprobante." + "PESD|5|DELIVERY 612-5000 LAS 24 HORAS" +
                        "*@@*"; // Linea 6;
                //JOptionPane.showMessageDialog(dialog, mensaje, "ALERTA DE TIEMPOS VERIFICA SI HAY CNX POS", JOptionPane.WARNING_MESSAGE);

            } else {
                return "E2|Error - Los parametros sin datos";
            }

            //Envia el mensaje por el canal de escritura
            tmpT1 = System.currentTimeMillis();
            escribir.append(mensaje);
            tmpT2 = System.currentTimeMillis();
            msj = msj + "\n" +
                    "A||Envia el mensaje por el canal de escritura      ||Tiempo : ||" + (tmpT2 - tmpT1) +
                    "||milisegundos";
            System.out.println("Enviar - MensajeConfiguracion:");

            //Espero la respuesta por el canal de lectura
            tmpT1 = System.currentTimeMillis();
            //respuesta = leer.readLine();

            System.out.println("************************");

            System.out.println("recevie msg from server......");
            InputStream in = socketServidor.getInputStream();
            byte[] b = new byte[1024];
            int data_size = in.read(b);
            System.out.println("recevie msg from server End......");
            String msg = "";
            for (int i = 0; i < data_size; i++) {
                String pParte = String.valueOf((char)b[i]);
                msg += pParte;
                System.out.println(i + "-" + pParte);
            }
            //msg;
            System.out.println("************************");
            System.out.println("" + msg);


            tmpT2 = System.currentTimeMillis();
            msj = msj + "\n" +
                    "A||Espero la respuesta por el canal de lectura      ||Tiempo : ||" + (tmpT2 - tmpT1) +
                    "||milisegundos" + "  " + respuesta;
            System.out.println("Espero la respuesta por el canal de lectura  ||Tiempo : ||" + (tmpT2 - tmpT1));
            System.out.println("****");
            System.out.println(respuesta);

            //Valida si es vacion la repuesta
            if (respuesta == null) {

                return "E3|Error - Mensaje de respuesta vacio";

            }
            //System.out.println("Respuesta MensajeConfiguracion:" + respuesta);

            //Trasforma repuesta
            tmpT1 = System.currentTimeMillis();
            //respuesta = getRepuesta(respuesta, Constante.tipoMensaje.CONFIGURACION);
            tmpT2 = System.currentTimeMillis();
            msj = msj + "\n" +
                    "A||Trasforma repuesta      ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
            //Cerrar las conexiones
            tmpT1 = System.currentTimeMillis();
            escribir.close();
            leer.close();
            socketServidor.close();

            tmpT2 = System.currentTimeMillis();
            msj = msj + "\n" +
                    "A||Cierra Socket  ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";

            /*if (muestraTiempos)
                JOptionPane.showMessageDialog(dialog, "En Verifica SI HAY CONEX CON EL POS : " + "\n" +
                        msj, "ALERTA DE TIEMPOS VERIFICA SI HAY CNX POS", JOptionPane.WARNING_MESSAGE);
                */

        } catch (Exception e) {
            e.printStackTrace();
            return "E1|Error - no se puede conectar con " + EposVariables.vIpSocketServidor + ":" +
                EposVariables.vPuertoEPOS;
        }

        return respuesta;
    }

}
