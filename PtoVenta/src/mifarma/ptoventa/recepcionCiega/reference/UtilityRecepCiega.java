package mifarma.ptoventa.recepcionCiega.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityInventario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      22.03.2005   Creación<br>
 * ASOSA      06.04.2010   Modificación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 * RECEP_BULTOS
 */

public class UtilityRecepCiega {

    private static final Logger log = LoggerFactory.getLogger(UtilityRecepCiega.class);
    private ArrayList myDatos = new ArrayList();
    private int pagComprobante = 1;

    private ArrayList exoneradosIGV = new ArrayList();

    private String nombreTitular = "";
    private String nombrePaciente = "";

    /**
     * Constructor
     */
    public UtilityRecepCiega() {
    }


    public static String verificaRucValido(String ruc) {
        String resultado = "";
        try {
            resultado = DBCliente.verificaRucValido(ruc);
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsCliente.RESULTADO_RUC_INVALIDO;
        }
    }

    public static String pEstadoRecepcion(String pNumRecepcion) {
        log.debug("pEstadoRecepcion()" + pNumRecepcion);
        String pEstado = "X";
        try {
            pEstado = DBRecepCiega.obtieneEstadoRecepCiega(pNumRecepcion.trim());
        } catch (SQLException e) {
            log.debug("ERROR al obtener el Estado");
            log.error("", e);
        }
        log.debug("Estado:" + pEstado);
        return pEstado.trim();
    }

    /**
     * @author DUBILLUZ
     * @since  07.12.2009
     * @return
     */
    public static boolean pPermiteIpConteo() {
        boolean pResultado = false;
        try {
            if (DBRecepCiega.isValidoIpConteo().trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                pResultado = true;
            }
        } catch (SQLException e) {
            //DEJARA CONTAR A TODOS LOS IP
            pResultado = true;
        }
        log.debug("El ip es Valido para el conteo...(" + pResultado + ")");
        return pResultado;
    }

    public static void pBloqueoRecepcion(String pNumRecep) {
        try {
            DBRecepCiega.bloqueoEstado(pNumRecep.trim());
        } catch (SQLException e) {
            log.error("", e);
        }

    }

    //public static void updateEstadoRecep(String pEstado,String pNumRecep){

    public static boolean updateEstadoRecep(String pEstado, String pNumRecep, JDialog pDialog, Object pObject) {
        //Utiliza el Secuencial de la recepcion: VariablesRecepCiega.vSecRecepGuia
        boolean flag = false;
        try {
            DBRecepCiega.actualizaEstadoRecep(pNumRecep, pEstado);
            log.debug("Estado Cabecera, sec:" + pNumRecep + " --- " + pEstado);
            flag = true;
        } catch (SQLException sql) {
            log.error("", sql);
            log.error("", sql);
            FarmaUtility.liberarTransaccion();
            /*FarmaUtility.showMessage(pDialog, "No se pudo modificar el estado en la Recepción.\n" +
                                     "Vuelva a Intentarlo.\n" +
                                              "Error: "+sql.getMessage(),pObject);*/
        }
        return flag;
    }

    public static String obtenerIndSegConteo() {
        String pInd = "";
        try {
            pInd = DBRecepCiega.obtenerSegConteo();
        } catch (SQLException e) {
            pInd = FarmaConstants.INDICADOR_N;
        }
        return pInd;
    }

    public static String obtenerIndSegConteo(String pNumRececiega) {
        String pInd = "";
        try {
            pInd = DBRecepCiega.obtenerSegConteo(pNumRececiega.trim());
        } catch (SQLException e) {
            pInd = FarmaConstants.INDICADOR_N;
        }
        return pInd;
    }

    public static void actualizaSegundoConteo(String pNumRecepCiega, String pIndicador) {
        try {
            DBRecepCiega.actualizaIndSegundoConteoParametro(pNumRecepCiega.trim(), pIndicador.trim());
        } catch (SQLException e) {
            log.debug("Actualiza ind de Segundo Conteo...");
            log.error("", e);
            FarmaUtility.liberarTransaccion();
        }
    }
    //JMIRANDA 02.02.10

    public static boolean indLimiteTransf(String pNroRecepcion) {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBRecepCiega.getIndLimiteTransf(pNroRecepcion.trim());
            if (rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else
                flag = false;
        } catch (SQLException e) {
            log.error("", e);
            flag = false;
        }
        return flag;
    }
    //JMIRANDA 11.02.2010 VALIDA SI LA FECHA DE VENCIMIENTO ESTA DENTRO DE POLITICA CANJE

    public static boolean indFechaVencTransf(String pCodProd, String pFechaVenc) {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBRecepCiega.getIndFechaVencTransf(pCodProd.trim(), pFechaVenc.trim());
            if (rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else
                flag = false;
        } catch (SQLException e) {
            log.error("", e);
            flag = false;
        }
        return flag;
    }

    public static boolean validarFecha(String pFecha) {
        boolean b = Pattern.matches("^([0][1-9]|[12][0-9]|3[01])(/|-)(0[1-9]|1[012])\\2(\\d{4})$", pFecha);
        return b;
    }

    /**
     * Se imprime VOUCHER de Confirmación Transportista
     * @author JMIRANDA
     * @since 17.03.10
     */
    public static void imprimeVoucherTransportista(JDialog pDialogo, String pNroRecepcion, Object obj) {
        try {
            VariablesRecepCiega.vDestEmailIngresoTransportista = getDestEmailIngresoTransportista();
            String vIndImpre = DBCaja.obtieneIndImpresion();
            String htmlVoucher = "";
            log.debug("vIndImpreVoucher :" + vIndImpre);
            if (!vIndImpre.trim().equalsIgnoreCase("N")) {
                htmlVoucher = DBRecepCiega.getDatosVoucherTransportista(pNroRecepcion);
                //log.debug("htmlVoucher:"+htmlVoucher);
                //JQuispe 05.05.2010 Se modifico la veces que  imprimira voucher
                for (int i = 0; i < VariablesRecepCiega.vNumImpresiones; i++) {
                    PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                              VariablesPtoVenta.vTipoImpTermicaxIp);
                }
                FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", obj);
            } else {
                FarmaUtility.showMessage(pDialogo,
                                         "No puede imprimir." + "No tiene asignado Impresoras Térmicas para su local.\n",
                                         obj);
            }

            //Envia correo
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                          VariablesRecepCiega.vDestEmailIngresoTransportista, //destinatario
                    "Recepcion de Productos ", //titulo
                    "Confirmacion ", htmlVoucher,
                    //"Usuario : "+FarmaVariables.vIdUsu +"<br>"+
                    //"IP : " +FarmaVariables.vIpPc +"<br>",
                    "");
        } catch (SQLException sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", obj);
        }
    }

    //JMIRANDA 21.03.2010 VALIDA SI TIENE LOTE

    public static boolean indLoteValido(String pNroRecepcion, String pCodProd, String pLote) {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBRecepCiega.getIndLoteValido(pNroRecepcion, pCodProd, pLote.toUpperCase().trim());
            if (rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else
                flag = false;
        } catch (SQLException e) {
            log.error("", e);
            flag = false;
        }
        return flag;
    }

    public static boolean indNoTieneFechaSap(String pNroRecepcion, String pCodProd) {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBRecepCiega.getIndNoTieneFechaSap(pNroRecepcion, pCodProd);
            if (rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else
                flag = false;
        } catch (SQLException e) {
            log.error("", e);
            flag = false;
        }
        return flag;
    }

    public static boolean indFechaCanjeProd(String pCodProd, String pFecha, String pLote) {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBRecepCiega.getIndFechaCanjeProd(pCodProd, pFecha, pLote);
            if (rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else
                flag = false;
        } catch (SQLException e) {
            log.error("", e);
            flag = false;
        }
        return flag;
    }

    public static boolean indHabDatosTransp() {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBRecepCiega.getIndHabDatosTransp();
            if (rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else
                flag = false;
        } catch (SQLException e) {
            //log.error("",e);
            flag = false;
        }
        return flag;
    }


    /**
     * Imprime voucher de transportista
     * @autho ASOSA
     * @since 06.04.2010
     * @param pDialogo
     * @param pNroRecepcion
     * @param obj
     */
    public static void imprimeVoucherTransportista_02(JDialog pDialogo, String pNroRecepcion, Object obj) {
        try {
            VariablesRecepCiega.vDestEmailIngresoTransportista = getDestEmailIngresoTransportista();
            String vIndImpre = DBCaja.obtieneIndImpresion();
            String htmlVoucher = "";
            log.debug("vIndImpreVoucher :" + vIndImpre);
            if (!vIndImpre.trim().equalsIgnoreCase("N")) {
                htmlVoucher = DBRecepCiega.getDatosVoucherTransportista_02(pNroRecepcion);
                //log.debug("htmlVoucher:"+htmlVoucher);
                PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", obj);
            } else {
                FarmaUtility.showMessage(pDialogo,
                                         "No puede imprimir." + "No tiene asignado Impresoras Térmicas para su local.\n",
                                         obj);
            }

            //ERIOS 2.4.6 Envia correo
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                          VariablesRecepCiega.vDestEmailIngresoTransportista, //destinatario
                    "Recepcion de Productos ", //titulo
                    "Confirmacion ", htmlVoucher,
                    //"Usuario : "+FarmaVariables.vIdUsu +"<br>"+
                    //"IP : " +FarmaVariables.vIpPc +"<br>",
                    "");
        } catch (SQLException sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", obj);
        }
    }


    //JQUISPE 05.05.2010 Se lee el numero de imptresiones del voucher de transportista.

    public static int getNumImpresiones() {
        int numImpres = 0;

        try {
            numImpres = Integer.parseInt(DBRecepCiega.getNumeroImpresiones());
        } catch (SQLException sql) {
            log.error("", sql);
        }
        return numImpres;
    }

    /**
     * @author DIEGO UBILLUZ
     * @return
     */
    public static boolean permiteIngresarConteoVerificacion(String pNumrecep) {
        boolean vResutlado = false;
        String pInd = "";
        try {
            pInd = DBRecepCiega.getValidaPermiteIngresar(pNumrecep);
        } catch (SQLException sqle) {
            pInd = "N";
            log.error("", sqle);
        }

        if (pInd.trim().equalsIgnoreCase("S"))
            vResutlado = true;


        return vResutlado;
    }

    /**
     * Destinatario ingreso transportista
     * @author ERIOS
     * @since 2.3.3
     * @return
     */
    private static String getDestEmailIngresoTransportista() {
        String email = "";
        try {
            email = DBRecepCiega.getDestinatarioIngresoTransportista();
        } catch (SQLException sql) {
            log.error("", sql);
        }
        return email;
    }


    /**
     * Llena tabla de 1 resgistro de la hoja de resumen
     * @author ASOSA
     * @since 21/07/2014
     * @param pTableModel
     * @param pHojaResumen
     * @throws SQLException
     */
    public static void obtenerDocTransporte(FarmaTableModel pTableModel, String pHojaResumen) throws SQLException {
        try {
            DBRecepCiega.obtenerDocTransporte(pTableModel, pHojaResumen);
        } catch (SQLException e) {
            log.error("obtenerDocTransporte: " + e.getMessage());
        }
    }

    /**
     * Determina si una bandeja existe o no en una hoja de resumen.
     * @author ASOSA
     * @since 21/07/2014
     * @param pHojaResumen
     * @param pNroBandeja
     * @return
     * @throws SQLException
     */
    public static boolean buscarBandejaHojaResumen(String pHojaResumen, String pNroBandeja) throws SQLException {
        boolean flag = false;
        String rpta = "N";
        try {
            rpta = DBRecepCiega.buscarBandejaHojaResumen(pHojaResumen, pNroBandeja);
            if (rpta.equals("S")) {
                flag = true;
            }
        } catch (SQLException e) {
            log.error("buscarBandejaHojaResumen: " + e.getMessage());
        }
        return flag;
    }

    /**
     * Determina si existe grabada una hoja de resumen.
     * @author ASOSA
     * @since 13/08/2014
     * @param pHojaResumen
     * @return
     * @throws SQLException
     */
    public static boolean buscarHojaResumenGrabada(String pHojaResumen) throws SQLException {
        boolean flag = false;
        String rpta = "N";
        try {
            rpta = DBRecepCiega.buscarHojaResumenGrabada(pHojaResumen);
            if (rpta.equals("S")) {
                flag = true;
            }
        } catch (SQLException e) {
            log.error("buscarHojaResumenGrabada: " + e.getMessage());
        }
        return flag;
    }
}


