package mifarma.ptoventa.administracion.fondoSencillo.reference;


import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : UtilityFondoSencillo.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JMIRANDA      25.02.2010   Creación<br>
 * <br>
 * @author John Miranda Bonifaz<br>
 * @version 1.0<br>
 *
 */
public class UtilityFondoSencillo {
    private static final Logger log = LoggerFactory.getLogger(UtilityFondoSencillo.class);

    /** constructor */
    public UtilityFondoSencillo() {
    }

    public static boolean validaMonto(String pMonto) {
        double monto = 0.00;
        boolean flag = false;
        try {
            monto = Double.parseDouble(pMonto.trim());
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean validaPrimerDigitoDifeCero(String pMonto) {
        boolean flag = true;
        if (validaMonto(pMonto)) {
            /* log.debug(pMonto.trim().substring(0,1));
            if(pMonto.trim().length()>1 && pMonto.trim().length()<=2){
                log.debug(pMonto.trim().substring(0,2));
                log.debug(pMonto.trim().substring(1,2));
                log.debug("+ 1");
                if(pMonto.trim().substring(0,1).equalsIgnoreCase("0")){
                    flag = false;
                }
            }*/
            /*if(pMonto.trim().length()>2){
                if(pMonto.trim().substring(0,1).equalsIgnoreCase("0")){
                    flag = false;
                    log.debug("+ 2");
                }
            } */
        }
        return flag;
    }

    public static String getIndTieneFondoSencillo(JDialog pDialog, String pSecUsuCajero,
                                                  JTextFieldSanSerif pText) throws SQLException {
        String rpta = "";

        try {
            rpta = DBFondoSencillo.getIndFondoSencilloCajero(pSecUsuCajero);
            /*if(rpta.trim().equalsIgnoreCase("S")){
                flag = true;
            }*/
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialog, "Error al Obtener el indicador de Asignación de Fondo de Sencillo.",
                                     pText);

        }
        return rpta;
    }

    public static int rptaConfirmDialogDefaultNo(JDialog pJDialog, String pMensaje) {
        String strSI = "Si";
        String strNO = "No";
        Object[] options = { strSI, strNO };
        int rptaDialogo =
            JOptionPane.showOptionDialog(pJDialog, pMensaje, "Mensaje del Sistema", JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, null, options, strNO);
        if (rptaDialogo == JOptionPane.YES_OPTION)
            return 1;
        else if (rptaDialogo == JOptionPane.CLOSED_OPTION)
            return -1;
        else
            return 0;
    }

    public static String getMontoAsignado(JDialog pDialog, String pSecUsuCajero, Object pText) {
        String rpta = "-1";

        try {
            rpta = DBFondoSencillo.getMontoAsignado(pSecUsuCajero);
            /*if(rpta.trim().equalsIgnoreCase("S")){
                flag = true;
            }*/
        } catch (SQLException sql) {
            FarmaUtility.showMessage(pDialog, "Error al Obtener el Monto Asignado de Fondo de Sencillo.", pText);
            log.error("", sql);
            rpta = "-1";
        }
        return rpta;
    }

    public static boolean validaDecimal(String pNum, int pMaxDigitosEnteros) {
        boolean flag = true;
        log.debug("Length: " + pNum.trim().length());
        log.debug("IndexOf: " + pNum.indexOf("."));
        int punto = pNum.indexOf(".");
        if (pNum.indexOf(".") > pMaxDigitosEnteros) {
            log.error("Error");
            flag = false;
        }
        return flag;
    }

    public static boolean validaMaximoDigitosEnteros(String pNum, int pMaxDigitosEnteros) {
        boolean flag = true;
        log.debug("Length: " + pNum.trim().length());
        log.debug("IndexOf: " + pNum.indexOf("."));
        int punto = pNum.indexOf(".");
        if (punto == -1) {
            if (pNum.trim().length() > pMaxDigitosEnteros) {
                flag = false;
            }
        }
        return flag;
    }


    public static String getIndTieneDevFondo(String pSecUsu, String pSecMov, JDialog pDialog, Object pText) {
        String rpta = "X";
        try {
            //FarmaVariables.vNuSecUsu
            rpta = DBFondoSencillo.getIndTieneDevPendiente(pSecUsu, pSecMov);
        } catch (SQLException sql) {
            rpta = "X";
            FarmaUtility.showMessage(pDialog, "Error al Obtener el Monto Asignado de Fondo de Sencillo.", pText);
        }
        return rpta;
    }

    public static String getMontoAsignado(JDialog pDialog, String pSecUsuCajero, String pMovCajaCierre, Object pText) {
        String rpta = "-1";
        try {
            rpta = DBFondoSencillo.getMontoDevolver(pSecUsuCajero, pMovCajaCierre);
        } catch (SQLException sql) {
            //    log.error("",sql);
            FarmaUtility.showMessage(pDialog, "Error al Obtener el Monto a Devolver de Fondo de Sencillo.", pText);
            log.error("Error al Obtener el Monto a Devolver de Fondo de Sencillo.");
            rpta = "-1";
        }
        return rpta;
    }

    /**
     * Se imprime VOUCHER de Asignaciones y devolucions
     * @author JMIRANDA
     * @since 04.03.10
     */
    public static void imprimeVoucherDiferencias(JDialog pDialogo, String pSecFondoSen,
                                                 Object obj) throws SQLException {
        //try
        //{
        String vIndImpre = DBCaja.obtieneIndImpresion();
        log.debug("vIndImpreVoucher :" + vIndImpre);
        if (!vIndImpre.trim().equalsIgnoreCase("N")) {
            String htmlVoucher = DBFondoSencillo.getDatosVoucherDevolucion(pSecFondoSen);
            log.debug("htmlVoucher:" + htmlVoucher);
            PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                      VariablesPtoVenta.vTipoImpTermicaxIp);
            FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", obj);
        } else {
            FarmaUtility.showMessage(pDialogo,
                                     "No puede imprimir." + "No tiene asignado Impresoras Térmicas para su local.\n",
                                     obj);
        }
        /*
        }catch(SQLException sqlException)
        {  log.error(null,sqlException);
           FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", obj);
        }
        */
    }

    public static boolean validaUsuAdmLocal(JDialog pDialog, String pSecUsu, Object pText) {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBFondoSencillo.getIndValidaAdmLocal(pSecUsu.trim());
            log.debug("rpta: " + rpta);
            if (rpta.trim().equalsIgnoreCase("S")) {
                flag = true;
            } else if (rpta.trim().equalsIgnoreCase("N")) {
                flag = false;
            } else {
                FarmaUtility.showMessage(pDialog, "Error al Validar el Usuario.", pText);
            }
        } catch (SQLException sql) {
            //log.error("",sql);
            FarmaUtility.showMessage(pDialog, "Error al Validar el Usuario.", pText);
        }
        return flag;
    }

    public static boolean indActivoFondo() {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBFondoSencillo.getIndHabilitadoFondo();
            log.debug("IndFondoSenc rpta: " + rpta);
            if (rpta.trim().equalsIgnoreCase("S")) {
                flag = true;
            }
        } catch (SQLException sql) {
            log.error("", sql);
            //log.error("",sql);
            //FarmaUtility.showMessage(,"Error al Validar el Usuario.",null);
        }
        return flag;
    }

    public static void imprimeVoucherDevoluciones(JDialog pDialogo, String pSecMovCajaCierre,
                                                  Object obj) throws SQLException {
        //try dubilluz 20.07.2010
        //{
        String vIndImpre = DBCaja.obtieneIndImpresion();
        log.debug("vIndImpreVoucher :" + vIndImpre);
        if (!vIndImpre.trim().equalsIgnoreCase("N")) {
            String htmlVoucher = DBFondoSencillo.getDatosVoucherDevolucion2(pSecMovCajaCierre);
            log.debug("htmlVoucher:" + htmlVoucher);
            if (!htmlVoucher.trim().equalsIgnoreCase("N")) {
                PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", obj);
            }
        } else {
            FarmaUtility.showMessage(pDialogo,
                                     "No puede imprimir." + "No tiene asignado Impresoras Térmicas para su local.\n",
                                     obj);
        }
        /*
       }catch(SQLException sqlException)
       {  log.error(null,sqlException);
          FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", obj);
       }
       */
    }

    public static void imprimeVoucherDiferencias(JDialog pDialogo, String pSecFondoSen, Object obj,
                                                 boolean pDoble) throws SQLException {
        VariablesFondoSencillo.vImprimeVoucherFondoSencillo = false;
        //try dubilluz 20.07.2010
        //{
        String vIndImpre = DBCaja.obtieneIndImpresion();
        log.debug("vIndImpreVoucher :" + vIndImpre);
        if (!vIndImpre.trim().equalsIgnoreCase("N")) {
            String htmlVoucher = DBFondoSencillo.getDatosVoucherDevolucion(pSecFondoSen);
            log.debug("htmlVoucher:" + htmlVoucher);
            if (pDoble) {
                PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", obj);
            } else {
                PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", obj);
            }
        } else {
            FarmaUtility.showMessage(pDialogo,
                                     "No puede imprimir." + "No tiene asignado Impresoras Térmicas para su local.\n",
                                     obj);
        }

        /*
       }catch(SQLException sqlException)
       {  log.error(null,sqlException);
          FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", obj);
       }
       */
    }

    public static boolean imprimeVoucherDiferencias_DU(JDialog pDialogo, String pSecFondoSen, Object obj,
                                                       boolean pDoble) throws SQLException {
        VariablesFondoSencillo.vImprimeVoucherFondoSencillo = false;
        //try dubilluz 20.07.2010
        //{
        String vIndImpre = DBCaja.obtieneIndImpresion();
        log.debug("vIndImpreVoucher :" + vIndImpre);
        if (!vIndImpre.trim().equalsIgnoreCase("N")) {
            String htmlVoucher = DBFondoSencillo.getDatosVoucherDevolucion(pSecFondoSen);
            log.debug("htmlVoucher:" + htmlVoucher);
            if (pDoble) {
                PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                //FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", obj);
                VariablesFondoSencillo.vImprimeVoucherFondoSencillo = true;
            } else {
                PrintConsejo.imprimirHtml(htmlVoucher, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                //FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", obj);
                VariablesFondoSencillo.vImprimeVoucherFondoSencillo = true;
            }
        } else {
            FarmaUtility.showMessage(pDialogo,
                                     "No puede imprimir." + "No tiene asignado Impresoras Térmicas para su local.\n",
                                     obj);
        }

        /*
       }catch(SQLException sqlException)
       {  log.error(null,sqlException);
          FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", obj);
       }
       */
        return VariablesFondoSencillo.vImprimeVoucherFondoSencillo;
    }

}
