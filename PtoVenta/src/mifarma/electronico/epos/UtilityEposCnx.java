package mifarma.electronico.epos;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JDialog;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.ElectronicoException;
import mifarma.electronico.UtilityEposTrx;
import mifarma.electronico.epos.reference.DBEpos;
import mifarma.electronico.epos.reference.EposConstante;
import mifarma.electronico.epos.reference.EposVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityEposCnx {
    static private final Logger log = LoggerFactory.getLogger(UtilityEposCnx.class);
    boolean muestraTiempos = false; // cambiarla a TRUE para que muestre el LOG.
    Properties properties;
    Socket socketServidor;
    PrintStream escribir;
    BufferedReader leer;
    String tipodocumentoSunat;

    String mensaje;
    //String respuesta;


    /**
     * Método que genera el mensaje de configuración para que el punto de venta pueda emitir comprobantes electrónicos
     * @return respuesta Define repuesta que da el EPOS cuando se envia el mensaje de configuración.
     */
    public boolean sentEchoEpos(String pIdPos) throws Exception {
        boolean pResultado = false;
        String pMensajeEcho = "";
        String respuesta;
        String msj = "";
        JDialog dialog = new JDialog();
        long tmpT1, tmpT2;
        try {
            //Crear conexion con el socket servidor
            tmpT1 = System.currentTimeMillis();
            try {
                socketServidor = new Socket(EposVariables.vIpSocketServidor, EposVariables.vPuertoEPOS);
            } catch (Exception ex) {
                log.info(ex.getMessage());
                throw new Exception("NO SE ESTABLECIO CONEXION CON SERVIDOR EPOS");
            }


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
                pMensajeEcho = "@**@" + EposConstante.tipoMensaje.CONFIGURACION + "\t" +
                        //Indentificador mensaje
                        EposVariables.vModo + "\t" + //modo ejecucion: 1- prueba, <> 1 -produccion
                        FarmaVariables.vNuRucCia + "\t" + //ruc del emisor
                        pIdPos + "\t" + //identificadorPos - identificador pos
                        FarmaVariables.vCodLocal + "*@@*"; //FarmaVariables.vCodLocal

            } else {
                throw new Exception(EposConstante.MSJ_ERROR + "|El Parametro de MODO esta Vacío!!");
            }

            try {
                //Envia el mensaje por el canal de escritura
                tmpT1 = System.currentTimeMillis();
                escribir.append(pMensajeEcho);
                tmpT2 = System.currentTimeMillis();

                msj = msj + "\n" +
                        "A||Envia el mensaje por el canal de escritura      ||Tiempo : ||" + (tmpT2 - tmpT1) +
                        "||milisegundos";
                log.info("Enviar - MensajeConfiguracion:" + pMensajeEcho);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw new Exception("ERROR DE COMUNICACION I/O CON SERVIDOR EPOS");
            }

            String msg = "";
            try {

                //Espero la respuesta por el canal de lectura
                tmpT1 = System.currentTimeMillis();
                //DUBILLUZ >> LECTURA DE BYTE
                InputStream in = socketServidor.getInputStream();
                byte[] b = new byte[1024];
                int data_size = in.read(b);
                for (int i = 0; i < data_size; i++) {
                    String pParte = String.valueOf((char)b[i]);
                    msg += pParte;
                    //System.out.println(i + "-" + pParte);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw new Exception("NO SE PUDO OBTENER RESPUESTA DEL SERVIDOR EPOS.");
            }
            //msg;
            respuesta = msg;
            log.info("************************");
            //DUBILLUZ
            tmpT2 = System.currentTimeMillis();
            msj = msj + "\n" +
                    "A||Espero la respuesta por el canal de lectura      ||Tiempo : ||" + (tmpT2 - tmpT1) +
                    "||milisegundos";
            //Valida si es vacion la repuesta
            if (respuesta == null) {
                throw new Exception(EposConstante.MSJ_ERROR + "|Mensaje de Respuesta Epos es NULO!!");
            }
            log.info("Respuesta MensajeConfiguracion:" + "EposConstante.tipoMensaje.CONFIGURACION :" + respuesta);
            //Trasforma repuesta
            tmpT1 = System.currentTimeMillis();
            respuesta = getFormatRpta(respuesta, EposConstante.tipoMensaje.CONFIGURACION);
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

            if (muestraTiempos)
                FarmaUtility.showMessage(dialog, "ALERTA DE TIEMPOS VERIFICA SI HAY CNX POS:\nsentEchoEpos" + "\n" +
                        "En Verifica SI HAY CONEX CON EL POS : " + "\n" +
                        msj, null);
        } finally {
            try {
                escribir.close();
                leer.close();
                socketServidor.close();
            } catch (Exception ioe) {
                // TODO: Add catch code
                ioe.printStackTrace();
            }
        }

        // "0" es el VALOR DE EXITO QUE DAN SEGUN LO INDICADO POR EPOS
        if (respuesta.startsWith(EposConstante.MSJ_EXITO))
            pResultado = true;
        else
            throw new Exception(respuesta); //Mostrar error del E-POS //LTAVARA 01/12/2014

        return pResultado;
    }

    /**
     * Método que genera el mensaje generarDE para enviar los datos del documento electronico al EPOS y genere el xml del comprobante.
     * @param tipodocumento       Define tipo de documento del comprobante;
     * @param cNumPedidoVta       Define el número del pedido del comprobante;
     * @param cSecCompPago        Define el codigo secuencial del comprobante;
     * @param cTipoClienteConvenio  Define el tipo del cliente convenio 1 - Beneficiario, 2 - Empresa;
     * @return respuesta          Define repuesta que da el EPOS cuando se envia el mensaje de GenerarDE.
     */
    public String getNumCompEpos(String tipodocumento, String cNumPedidoVta, String cSecCompPago,
        //datos complementarios para la consulta
        String cTipoClienteConvenio, String pIdPos) //tipo convenicio 1-Beneficiario, 2-Empresa
        throws Exception {
        String respuesta;
        String msj = "";
        JDialog dialog = new JDialog();
        long tmpT1, tmpT2;
        String tipodocumentoSunat = "";
        //DBEpos cnx = new DBEpos();

        //validar datos
        if (!FarmaVariables.vCodGrupoCia.equals("") && !pIdPos.equals("") && //identificador
            !cSecCompPago.equals("")) {

            if (tipodocumento.equals(EposConstante.tipoDocumentoFarma.BOLETA) ||
                tipodocumento.equals(EposConstante.tipoDocumentoFarma.TICKET_BOLETA)) { //Boleta
                tipodocumentoSunat = EposConstante.tipoDocumentoSunat.BOLETA;
            } else if (tipodocumento.equals(EposConstante.tipoDocumentoFarma.FACTURA) ||
                       tipodocumento.equals(EposConstante.tipoDocumentoFarma.TICKET_FACTURA)) { //Factura
                tipodocumentoSunat = EposConstante.tipoDocumentoSunat.FACTURA;
            } else if (tipodocumento.equals(EposConstante.tipoDocumentoFarma.NOTA_CREDITO)) { //Nota de credito
                tipodocumentoSunat = EposConstante.tipoDocumentoSunat.NOTA_CREDITO;
            }

            if (tipodocumentoSunat.trim().length() == 0)
                throw new ElectronicoException(EposConstante.MSJ_ERROR + "|El Tipo Documento SUNAT es NULO.");


            tmpT1 = System.currentTimeMillis();
            //obtener los datos del documento electronico
            String documento = getTramaDatos(cNumPedidoVta, cSecCompPago, tipodocumento, cTipoClienteConvenio);
            //GENERA LA TRAMA DE DATOS//
            tmpT2 = System.currentTimeMillis();
            msj = "A||obtener los datos del documento electronico  ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
            //log.debug("A||cnx.getDocumentoElectronico||Tiempo : ||"+(tmpT2 - tmpT1)+"||milisegundos");
            // System.out.println("A||cnx.getDocumentoElectronico||Tiempo : ||"+(tmpT2 - tmpT1)+"||milisegundos");
            //JOptionPane.showMessageDialog(null, "A||cnx.getDocumentoElectronico||Tiempo : ||"+(tmpT2 - tmpT1)+"||milisegundos", null);
            if (documento == null || documento == "") {
                throw new ElectronicoException(EposConstante.MSJ_ERROR + "|La trama esta sin datos");
            }

            tmpT1 = System.currentTimeMillis();
            //Crear conexion con el socket servidor
            socketServidor = new Socket(EposVariables.vIpSocketServidor, EposVariables.vPuertoEPOS);

            //Crear referencias al canal de escritura
            escribir = new PrintStream(socketServidor.getOutputStream());

            //Crear referencias al canal de lectura
            leer = new BufferedReader(new InputStreamReader(socketServidor.getInputStream()));

            //crear mensaje de GenerarDE
            mensaje = "@**@" + EposConstante.tipoMensaje.GENERA_DE + "\t" + //Indentificador mensaje
                    EposVariables.vModo + "\t" + //modo ejecucion: 1- prueba, <> 1 -produccion
                    FarmaVariables.vNuRucCia + "\t" + //ruc del emisor
                    pIdPos + "\t" + //identificador pos
                    tipodocumentoSunat + "\t" + //tipo documento
                    documento + "*@@*";

            tmpT2 = System.currentTimeMillis();
            msj = msj + "\n" +
                    "A||Carga Variables||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";

        } else {
            log.debug("FarmaVariables.vCodGrupoCia:" + FarmaVariables.vCodGrupoCia);
            log.debug("EposVariables.vIdPos:" + UtilityEposTrx.getIdEpos());
            log.debug("cSecCompPago:" + cSecCompPago);
            throw new ElectronicoException(EposConstante.MSJ_ERROR + "|Los parametros estan sin datos");
        }

        //Envia el mensaje por el canal de escritura

        log.debug("mensajeGenerarDE:" + mensaje);
        tmpT1 = System.currentTimeMillis();
        escribir.append(mensaje);
        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Envia el mensaje por el canal de escritura ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";


        log.debug("mensajeGenerarDE:" + mensaje);
        msj = msj + "\n" +
                "A||registrar trama de envio en la tabla TRAMA_PPL ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";

        //Espero la respuesta por el canal de lectura
        tmpT1 = System.currentTimeMillis();
        //respuesta = leer.readLine();
        //DUBILLUZ >> LECTURA DE BYTE
        InputStream in = socketServidor.getInputStream();
        byte[] b = new byte[1024];
        int data_size = in.read(b);
        String msg = "";
        for (int i = 0; i < data_size; i++) {
            String pParte = String.valueOf((char)b[i]);
            msg += pParte;
            //System.out.println(i + "-" + pParte);
        }
        //msg;
        respuesta = msg;
        System.out.println("************************");
        //DUBILLUZ

        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Espero la respuesta por el canal de lectura ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
        //Valida si es vacion la repuesta
        if (respuesta == null) {
            throw new ElectronicoException(EposConstante.MSJ_ERROR + "|Mensaje de respuesta vacio");
        }
        log.debug("Respuesta mensajeGenerarDE:" + respuesta);


        //registrar trama de repuesta en la tabla TRAMA_PPL
        tmpT1 = System.currentTimeMillis();
        DBEpos.updateMsjNum_E(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, cNumPedidoVta, cSecCompPago,
                              respuesta);

        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||cnx.updateTrama ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
        //Trasforma repuesta
        tmpT1 = System.currentTimeMillis();
        respuesta = getFormatRpta(respuesta, mifarma.electronico.epos.reference.EposConstante.tipoMensaje.GENERA_DE);
        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Trasforma repuesta ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";

        tmpT1 = System.currentTimeMillis();
        //Cerrar las conexiones
        escribir.close();
        leer.close();
        socketServidor.close();
        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Cierra Socket  ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
        if (muestraTiempos)
            FarmaUtility.showMessage(dialog, "ALERTA DE TIEMPOS DE GENERACION DE FACT. ELECTRONICA\n" +
                    "En Procesar Cobro PASO 1 : OBTIENE COMPROBANTE ELECTRONICO " + "\n" +
                    msj, null);

        return respuesta;
    }

    /**
     * Método que genera el mensaje ConfirmacionDE para enviar los datos del documento electronico al EPOS y  obtener las serie-correlativo y PDF417
     * @param cNumPedidoVta       Define el número del pedido del comprobante;
     * @param cSecCompPago        Define el código secuencial del comprobante;
     * @param tipoDocumento       Define el tipo de documento;
     * @param numeroCompPagoE     Define el número de comprobante electrónico;
     * @return respuesta          Define repuesta que da el EPOS cuando se envia el mensaje de ConfirmacionDE.
     */

    public String sentMsjConfirmacion(String cNumPedidoVta, String cSecCompPago, String tipoDocumento,
                                      String numeroCompPagoE, String pIdPos) throws Exception {
        String msj = "";
        String respuesta;
        JDialog dialog = new JDialog();
        long tmpT1, tmpT2;
        DBEpos cnx = new DBEpos();


        //Crear conexion con el socket servidor
        tmpT1 = System.currentTimeMillis();
        socketServidor = new Socket(EposVariables.vIpSocketServidor, EposVariables.vPuertoEPOS);

        //Crear referencias al canal de escritura
        escribir = new PrintStream(socketServidor.getOutputStream());

        //Crear referencias al canal de lectura
        leer = new BufferedReader(new InputStreamReader(socketServidor.getInputStream()));
        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Carga Variables  ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";

        //validar datos
        if ((EposVariables.vPuertoEPOS != null || EposVariables.vPuertoEPOS != 0) && !pIdPos.equals("") &&
            !numeroCompPagoE.equals("")) {
            if (tipoDocumento.equals(EposConstante.tipoDocumentoFarma.BOLETA) ||
                tipoDocumento.equals(EposConstante.tipoDocumentoFarma.TICKET_BOLETA)) { //Boleta
                tipodocumentoSunat = EposConstante.tipoDocumentoSunat.BOLETA;
            } else if (tipoDocumento.equals(EposConstante.tipoDocumentoFarma.FACTURA) ||
                       tipoDocumento.equals(EposConstante.tipoDocumentoFarma.TICKET_FACTURA)) { //Factura
                tipodocumentoSunat = EposConstante.tipoDocumentoSunat.FACTURA;
            } else if (tipoDocumento.equals(EposConstante.tipoDocumentoFarma.NOTA_CREDITO)) { //Nota de credito
                tipodocumentoSunat = EposConstante.tipoDocumentoSunat.NOTA_CREDITO;
            }

            mensaje = "@**@" + EposConstante.tipoMensaje.CONFIRMAR_DE + "\t" + //Indentificador mensaje
                    EposVariables.vModo + "\t" + //modo ejecucion: 1- prueba, <> 1 -produccion
                    FarmaVariables.vNuRucCia + "\t" + //ruc del emisor
                    pIdPos + "\t" + //identificador pos
                    tipodocumentoSunat + "\t" + //identificador pos
                    numeroCompPagoE + "*@@*";

        } else {
            throw new ElectronicoException(EposConstante.MSJ_ERROR + "|Los parametros sin datos");
        }
        //Envia el mensaje por el canal de escritura
        tmpT1 = System.currentTimeMillis();
        escribir.append(mensaje);
        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Envia el mensaje por el canal de escritura   ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";

        //registrar trama de envio en la tabla TRAMA_PPL
        msj = msj + "\n" +
                "A||registrar trama de envio en la tabla TRAMA_PPL  ||Tiempo : ||" + (tmpT2 - tmpT1) +
                "||milisegundos";
        //Espero la respuesta por el canal de lectura
        tmpT1 = System.currentTimeMillis();
        //respuesta = leer.readLine();
        //DUBILLUZ >> LECTURA DE BYTE
        InputStream in = socketServidor.getInputStream();
        byte[] b = new byte[1024];
        int data_size = in.read(b);
        String msg = "";
        for (int i = 0; i < data_size; i++) {
            String pParte = String.valueOf((char)b[i]);
            msg += pParte;
            //System.out.println(i + "-" + pParte);
        }
        //msg;
        respuesta = msg;
        System.out.println("************************");
        //DUBILLUZ
        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Espero la respuesta por el canal de lectura  ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
        //Valida si es vacion la repuesta
        if (respuesta == null) {
            throw new ElectronicoException(EposConstante.MSJ_ERROR + "|Mensaje de respuesta vacio");
        }
        //registrar trama de repuesta en la tabla TRAMA_PPL
        tmpT1 = System.currentTimeMillis();
        cnx.updateMsjConf_E(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, cNumPedidoVta, cSecCompPago,
                            respuesta);
        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||updateTrama  ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
        //Trasforma repuesta
        tmpT1 = System.currentTimeMillis();
        respuesta =
                getFormatRpta(respuesta, mifarma.electronico.epos.reference.EposConstante.tipoMensaje.CONFIRMAR_DE);
        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Trasforma repuesta ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
        //Cerrar las conexiones
        tmpT1 = System.currentTimeMillis();
        escribir.close();
        leer.close();
        socketServidor.close();

        tmpT2 = System.currentTimeMillis();
        msj = msj + "\n" +
                "A||Cierra Socket  ||Tiempo : ||" + (tmpT2 - tmpT1) + "||milisegundos";
        if (muestraTiempos)
            FarmaUtility.showMessage(dialog, "ALERTA DE Confirmacion Envio Documento\n" +
                    "En Procesar Cobro PASO 2 : mensajeConfirmacion (Confirma al EPOS envio de Documento) : " + "\n" +
                    msj, null);

        return respuesta;
    }


    /**
     * Método que transforma la cadena a la repuesta depende del tipo de mensaje.
     * @param cadena         Define cadena a transformar;
     * @param tipoMensaje    Define el tipo de mensaje;
     * @return respuesta     Define repuesta transformada.
     */
    private String getFormatRpta(String cadena, String tipoMensaje) throws Exception {
        Boolean tieneCaracterInicio = cadena.startsWith("\u0002");
        String[] listaRespuesta;

        log.info("getFormatRpta resultado inicio :" + cadena.startsWith("\u0002"));
        if (tieneCaracterInicio) {
            String repuestaFormatado;
            repuestaFormatado = cadena.substring(1, cadena.indexOf("\u0003"));
            log.info(" getFormatRpta resultado sub cadena:" + repuestaFormatado);
            listaRespuesta = repuestaFormatado.split("\t");
        } else {
            listaRespuesta = cadena.split("\t");
        }

        // MENSAJE DE EXITO //
        if (listaRespuesta[0].toString().equals("0")) { //Mensaje de exito
            switch (tipoMensaje) {
            case EposConstante.tipoMensaje.CONFIGURACION:
                return EposConstante.MSJ_EXITO + "|Exito - Configuración con el EPOS activa.";
            case EposConstante.tipoMensaje.GENERA_DE:
                String numeroComprobanteE = getNumCompE(listaRespuesta[2].toString());
                return EposConstante.MSJ_EXITO + "|Exito - La generación del DE con num:" + numeroComprobanteE +
                    " se ejecuto correctamente.|" + numeroComprobanteE + "|" + listaRespuesta[3].toString() + "|" +
                    listaRespuesta[4].toString();
            case EposConstante.tipoMensaje.CONFIRMAR_DE:
                return EposConstante.MSJ_EXITO + "|Exito - Se envio el DE correctamente a SUNAT.";
            }
        } else { //Mensaje de error
            return EposConstante.MSJ_ERROR + "|E" + listaRespuesta[0].toString() + "|" + listaRespuesta[1].toString();
        }

        return null;
    }

    private String getNumCompE(String numCompE) throws Exception {
        String numero;
        numero = DBEpos.getNumCompEFormato(numCompE);
        return numero;
    }


    /**
     * Obtiene conexion al EPOS del punto de venta
     * @author LTAVARA
     * @since 26.09.2014
     * @return
     */
    public String getCnxRemotoEPOS() throws Exception {
        ArrayList parametros = new ArrayList();
        String pResultado = "N";
        //try {
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        pResultado =
                FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA.FARMA_CNX_REMOTO.F_CNX_LOCAL_EPOS(?,?,?)", parametros);

        String[] pDatosCnx = pResultado.split("@");
        //lee datos del EPOS
        EposVariables.vIpSocketServidor = pDatosCnx[0].trim();
        EposVariables.vPuertoEPOS = Integer.parseInt(pDatosCnx[1].trim());
        EposVariables.vModo = pDatosCnx[2].trim();

        String mensaje = "";
        if (EposVariables.vIpSocketServidor.trim().length() == 0) {
            mensaje = "IP--> NO REGISTRADO\n" +
                    mensaje;
        }
        if (EposVariables.vPuertoEPOS == 0) {
            mensaje = "Puerto--> NO REGISTRADO\n" +
                    mensaje;
        }
        if (EposVariables.vModo.trim().length() == 0) {
            mensaje = "Modo--> NO REGISTRADO\n" +
                    mensaje;
        }

        if (mensaje.trim().length() > 0) {
            throw new Exception("ERROR AL OBTENER CADENA DE CONEXION A SERVIDOR EPOS:\n" +
                    mensaje);
        }
        pResultado = "S";
        /*} catch (Exception sqle) {
            log.info("", sqle);
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                          VariablesPtoVenta.vDestEmailErrorCobro,
                                          "Error de Obtener Cadena Conexion getCnxRemotoEPOS",
                                          "Error de Comunicacion getCnxRemotoEPOS",
                                          "Error en Obtener Cadena Conexion" + "<br>" + "Inténtelo nuevamente." +
                                          "<br>" + "Si persiste el error, llame a Mesa de Ayuda." + "<br>" +
                                          "IP PC: " + FarmaVariables.vIpPc + "<br>" + "Error: " + "Conexion :" +
                                          sqle.getMessage(), "");
            pResultado = "N";
        }*/
        return pResultado;

    }


    private String getTramaDatos(String pNumPed, String pSecCompPago, String pTipoDoc,
                                 String pTipCliConv) throws Exception {
        String pTrama = "";
        //return null;
        pTrama = DBEpos.getDocumentoElectronico(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPed, pSecCompPago,
                                               pTipoDoc, pTipCliConv);

        return pTrama;
    }
    
    public String getMensaje(){
        return this.mensaje;
    }
}
