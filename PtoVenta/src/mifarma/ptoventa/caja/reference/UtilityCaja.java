package mifarma.ptoventa.caja.reference;


import java.awt.Frame;

import java.io.FileOutputStream;
import java.io.PrintStream;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaPrintServiceTicket;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;
import mifarma.electronico.UtilityImpCompElectronico;
import mifarma.electronico.epos.reference.DBEpos;
import mifarma.electronico.impresion.dao.ConstantesDocElectronico;

import mifarma.ptoventa.administracion.impresoras.reference.ContextImpresion;
import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.FormatoImpresionA;
import mifarma.ptoventa.administracion.impresoras.reference.FormatoImpresionB;
import mifarma.ptoventa.administracion.impresoras.reference.FormatoImpresionC;
import mifarma.ptoventa.administracion.impresoras.reference.Impresora;
import mifarma.ptoventa.administracion.impresoras.reference.ImpresoraTicket;
import mifarma.ptoventa.caja.DlgFormaPago;
import mifarma.ptoventa.caja.DlgNuevoCobro;
import mifarma.ptoventa.caja.DlgProcesarNuevoCobro;
import mifarma.ptoventa.caja.DlgProcesarNuevoCobroBTLMF;
import mifarma.ptoventa.caja.DlgProcesarProductoVirtual;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.FacadeConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.despacho.reference.UtilityDespacho;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.main.DlgProcesar;
import mifarma.ptoventa.proforma.reference.DBProforma;
import mifarma.ptoventa.puntos.reference.DBPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerUtil.FarmaPrinterConstants;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      06.03.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class UtilityCaja {

    private static final Logger log = LoggerFactory.getLogger(UtilityCaja.class);

    private static int numeroCorrel = 1;
    public static int acumuCorre = 0;
    
    /**
     * Constructor
     */
    public UtilityCaja() {
    }

    /**
     * Metodo que sirve para validar que existe conexion en matriz
     * @Author DVELIZ
     * @Since 30.09.08
     * @param pCadena
     * @param pParent
     */
    public static void validarConexionMatriz() {


        VariablesCaja.vIndConexion =
                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);


        if (VariablesCaja.vIndConexion.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            log.debug("No existe linea se cerrara la conexion ...");
            FarmaConnectionRemoto.closeConnection();

        }


    }

    public static String obtieneEstadoPedido(JDialog pDialog, String pNumPedVta) {
        String estadoPed = "";
        try {
            estadoPed = DBCaja.obtieneEstadoPedido(pNumPedVta);
            return estadoPed;
        } catch (SQLException sqlException) {
            log.error("",sqlException);
            //FarmaUtility.showMessage(pDialog, "Error al obtener Estado del Pedido !!! - " + sqlException.getErrorCode(), pObjectFocus);
            return estadoPed;
        }
    }

    /**
     * Valida si existe impresora asociada al cajero que realiza el cobro
     * @param pDialog
     * @param pObjectFocus
     * @return
     */
    public static boolean existeCajaUsuarioImpresora(JDialog pDialog, Object pObjectFocus){
        return existeCajaUsuarioImpresora(pDialog, pObjectFocus, false);
    }
    
    public static boolean existeCajaUsuarioImpresora(JDialog pDialog, Object pObjectFocus, boolean bEntregaProforma) {
        return existeCajaUsuarioImpresora(pDialog, pObjectFocus, bEntregaProforma, false);
    }
    
    public static boolean existeCajaUsuarioImpresora(JDialog pDialog, Object pObjectFocus, boolean bEntregaProforma, boolean isCobrandoProforma) {
        try {
            boolean existeCajaUsuarioImpresora = true;
            int cajaUsuario;
            String vNuSecUsu;
            //ERIOS 12.01.2016 Se valida el cajero de la proforma
            if(bEntregaProforma){
                vNuSecUsu = DBProforma.obtieneSecCajeroProforma(VariablesVentas.vNum_Ped_Vta);
            }else if(isCobrandoProforma){
                vNuSecUsu = FarmaVariables.vNuSecUsu;
            }else{
                vNuSecUsu = FarmaVariables.vNuSecUsu;
            }
            
            cajaUsuario = DBCaja.obtieneNumeroCajaUsuario(vNuSecUsu);
            
            if (cajaUsuario != 0) {
                VariablesCaja.vNumCaja = new Integer(cajaUsuario).toString();
                int cajaAbierta = DBCaja.verificaCajaAbierta(vNuSecUsu);
                
                if (cajaAbierta == 0) {
                    VariablesCaja.vNumCaja = "";
                    existeCajaUsuarioImpresora = false;
                    FarmaUtility.showMessage(pDialog,
                                             "La Caja relacionada al Usuario no ha sido Aperturada. Verifique !!!",
                                             pObjectFocus);
                } else {
                    
                    VariablesCaja.vSecMovCaja = DBCaja.obtieneSecuenciaMovCaja(VariablesCaja.vNumCaja);
                    
                    if (VariablesCaja.vSecMovCaja.equals("0")) {
                        existeCajaUsuarioImpresora = false;
                        VariablesCaja.vSecMovCaja = "";
                        FarmaUtility.showMessage(pDialog, "No se pudo determinar el Movimiento de Caja. Verifique !!!",
                                                 pObjectFocus);
                    } else {
                        if (!existeImpresorasVenta(pDialog, pObjectFocus))
                            existeCajaUsuarioImpresora = false;
                    }
                }
            } else {
                existeCajaUsuarioImpresora = false;
                FarmaUtility.showMessage(pDialog, "El usuario No tiene caja relacionada. Verifique !!!", pObjectFocus);
            }
            return existeCajaUsuarioImpresora;
        } catch (SQLException sqlException) {
            log.error("",sqlException);
            FarmaUtility.showMessage(pDialog,
                                     "Error al obtener Datos de la Relacion Caja - Usuario - Impresora !!! - " +
                                     sqlException.getErrorCode(), pObjectFocus);
            return false;
        }
    }

    public static boolean existeImpresorasVenta(JDialog pDialog, Object pObjectFocus) throws SQLException {
        boolean existeImpresorasVenta = true;
        //JCORTEZ 24.03.09 No se valida por ahora la relacion de caja impresora obligatoria
        //String tipoComprobanteImpresora = DBCaja.verificaRelacionCajaImpresoras();
        /* if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Boletas. Verifique !!!", pObjectFocus);
    } else if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Facturas. Verifique !!!", pObjectFocus);
    } else if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_GUIA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Guias. Verifique !!!", pObjectFocus);
    } else
    {*/
        ArrayList myArray = new ArrayList();
        DBCaja.obtieneSecuenciaImpresorasVenta(myArray, VariablesCaja.vNumCaja);
        if (myArray.size() <= 0) {
            VariablesCaja.vSecImprLocalBoleta = "";
            VariablesCaja.vSecImprLocalFactura = "";
            VariablesCaja.vSecImprLocalGuia = "";
            VariablesCaja.vSecImprLocalTicket = "";
            VariablesCaja.vSerieImprLocalTicket = "";
            VariablesCaja.vSecImprLocalNotaCredito = "";
            existeImpresorasVenta = false;
            FarmaUtility.showMessage(pDialog, "Error al leer informacion de las impresoras.", pObjectFocus);
        } else {
            VariablesCaja.vSecImprLocalBoleta = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesCaja.vSecImprLocalFactura = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesCaja.vSecImprLocalGuia = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            VariablesCaja.vSecImprLocalTicket = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            VariablesCaja.vSerieImprLocalTicket = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesCaja.vSecImprLocalNotaCredito = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            existeImpresorasVenta = true;
        }
        //}
        return existeImpresorasVenta;
    }

    public static void imprimeComprobantePago(JDialog pJDialog, ArrayList pDetalleComprobante,
                                              ArrayList pTotalesComprobante, String pTipCompPago,
                                              String pNumComprobante, boolean indAnulado, int valor,
                                              String pSecCompPago, boolean isReimpresion,
                                              boolean isComprobanteElectronico) throws Exception {

        String ruta = UtilityPtoVenta.obtieneDirectorioComprobantes();

        Date vFecImpr = new Date();
        String fechaImpresion;

        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        fechaImpresion = sdf.format(vFecImpr);
            
        if(isComprobanteElectronico){
            log.info("INGRESO AL  COMPROBANTE ELECTRONICO");
            if( pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) || 
                pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) ||
                pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_NOTA_CREDITO)){
                
                if(pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)){
                    ruta = ruta + fechaImpresion + "_" + "B_E_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                }
                if(pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)){
                    ruta = ruta + fechaImpresion + "_" + "F_E_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                }
                
                if(pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_NOTA_CREDITO)){
                    ruta = ruta + fechaImpresion + "_" + "NC_E_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                }
                
                UtilityImpCompElectronico impresionElectronico = new UtilityImpCompElectronico();
                //String indicElectronico = DBImpresoras.getIndicCompElectronico(VariablesCaja.vNumPedVta, VariablesCaja.vSecComprobante); //indicadorElectronico
                impresionElectronico.setRutaFileTestigo(ruta);
                FarmaVariables.vAceptar = impresionElectronico.imprimirComprobantePago(VariablesCaja.vNumPedVta, VariablesCaja.vSecComprobante, false);
            }else{
                FarmaUtility.showMessage(pJDialog, "ERROR AL IMPRIMIR DOCUMENTO ELECTRONICO, TIPO DE COMPROBANTE: 05, VERIFIQUE!!!", null);
            }
        }else{
            String pValTotalBruto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(0)).trim();
            String pValTotalNeto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(1)).trim();
            String pValTotalDescuento = ((String)((ArrayList)pTotalesComprobante.get(0)).get(2)).trim();
            String pValTotalImpuesto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(3)).trim();
            String pValTotalAfecto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(4)).trim();
            String pValRedondeo = ((String)((ArrayList)pTotalesComprobante.get(0)).get(5)).trim();
            String pPorcIgv = ((String)((ArrayList)pTotalesComprobante.get(0)).get(6)).trim();
            String pNomImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(7)).trim();
            String pNumDocImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(8)).trim();
            String pDirImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(9)).trim();
            String fechaBD = ((String)((ArrayList)pTotalesComprobante.get(0)).get(10)).trim();
            String pValTotalAhorro = ((String)((ArrayList)pTotalesComprobante.get(0)).get(11)).trim();

            pValTotalBruto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalBruto), 2);
            pValTotalNeto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalNeto), 2);
            pValTotalDescuento = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalDescuento), 2);
            pValTotalImpuesto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalImpuesto), 2);
            pValTotalAfecto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalAfecto), 2);
            pValRedondeo = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValRedondeo), 2);
            pPorcIgv = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pPorcIgv), 2);

            //ERIOS 2.4.3 margen izquierdo
            int margen = UtilityPtoVenta.getMargenImpresionComp();
            Impresora impresora = new Impresora();
            ContextImpresion context = null;
            int vLineasComp = 0;

            if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)) {
                log.debug("*******imprimir BOLETA **********");
                UtilityConvenioBTLMF.muestraMensajeImpresion(pJDialog, pTipCompPago, ++acumuCorre, pSecCompPago,
                                                             VariablesCaja.vNumPedVta, false, isReimpresion);
                if (FarmaVariables.vAceptar) {
                    ruta = ruta + fechaImpresion + "_" + "B_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                        
                    if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MIFARMA) ||
                        FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MAYORISTA) ||
                        FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_FASA)) {
                        context = new ContextImpresion(new FormatoImpresionA());
                        vLineasComp = 24;
                    } else if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_BTL) ||
                            FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_BTL_AMAZONIA)) { //ASOSA - 20/07/2015 - IGVAZNIA

                        context = new ContextImpresion(new FormatoImpresionC());
                        vLineasComp = 24;
                    }
                    //KMONCADA 06.05.2016 IMPRESION DE COMPROBANTES MANUALES
                    if(FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_ARCANGEL) || 
                            FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_JORSA)){
                        imprimirComprobantePago(VariablesCaja.vNumPedVta, pSecCompPago,  ruta);
                    }else{

                        ArrayList<String> vPrint =
                            context.formatoBoleta(pJDialog, fechaBD, pDetalleComprobante, pValTotalNeto, pValRedondeo,
                                         pNumComprobante, pNomImpreso, pDirImpreso, pValTotalAhorro, ruta, true,
                                margen,false,
                        "pImprDatAdic", "pTipoClienteConvenio", "pCodTipoConvenio", 
                        "pValRedondeoCompPago", "pValIgvComPago", "pValCopagoCompPago", 
                        "pValIgvCompCoPago", "pNumCompRef");
                        
                        impresora.imprimir(vPrint, VariablesCaja.vRutaImpresora, true, VariablesCaja.vNumPedVta, pNumComprobante, "C",
                                           ruta, vLineasComp);
                    }
                }
            } else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) { //JCORTEZ  25.03.09
                
                ruta = ruta + fechaImpresion + "_" + "T_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                
                // KMONCADA 24.07.2015 DESCRIPCION DE PUNTOS TICKETS
                List lstPuntos = null;
                try{
                    lstPuntos = DBPuntos.imprVouPtosTicket(VariablesCaja.vNumPedVta, pSecCompPago);
                }catch(Exception ex){
                    lstPuntos = new ArrayList();
                }
                imprimeBoletaTicket(pJDialog, fechaBD, pDetalleComprobante, pValTotalNeto, pValRedondeo,
                                    pNumComprobante, pNomImpreso, pNumDocImpreso, pValTotalAhorro, ruta, indAnulado,
                                    pTipCompPago, valor, lstPuntos);
                
            }
            //LLEIVA 24-Ene-2014 Se añadio el tipo de comprobante Ticket Factura
            else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT)) { //JCORTEZ  25.03.09
                
                ruta = ruta + fechaImpresion + "_" + "T_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                
                // KMONCADA 24.07.2015 DESCRIPCION DE PUNTOS TICKETS
                List lstPuntos = null;
                try{
                    lstPuntos = DBPuntos.imprVouPtosTicket(VariablesCaja.vNumPedVta, pSecCompPago);
                }catch(Exception ex){
                    lstPuntos = new ArrayList();
                }
                imprimeFactTicket(pJDialog, fechaBD, pDetalleComprobante, pValTotalNeto, pValRedondeo, pNumComprobante,
                                  pNomImpreso, pNumDocImpreso, pValTotalAhorro, ruta, indAnulado, pTipCompPago, valor, lstPuntos);

            } else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)) {

                UtilityConvenioBTLMF.muestraMensajeImpresion(pJDialog, pTipCompPago, ++acumuCorre,
                                                             VariablesCaja.vSecComprobante, VariablesCaja.vNumPedVta,
                                                             false, isReimpresion);
                if (FarmaVariables.vAceptar) {
                    ruta = ruta + fechaImpresion + "_" + "F_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                    
                    if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MIFARMA) ||
                        FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MAYORISTA)) {
                        context = new ContextImpresion(new FormatoImpresionA());
                        vLineasComp = 36;
                    } else if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_FASA)) {
                        context = new ContextImpresion(new FormatoImpresionB());
                        vLineasComp = 66;
                    } else if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_BTL) ||
                            FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_BTL_AMAZONIA)) { //ASOSA - 20/07/2015 - IGVAZNIA
                        context = new ContextImpresion(new FormatoImpresionC());
                        vLineasComp = 36; //rherrera
                    }
                    //KMONCADA 06.05.2016 IMPRESION DE COMPROBANTES MANUALES
                    if(FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_ARCANGEL) || 
                            FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_JORSA)){
                        imprimirComprobantePago(VariablesCaja.vNumPedVta, pSecCompPago,  ruta);
                    }else{
                        ArrayList<String> vPrint =
                            context.formatoFactura(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante,
                                                               pValTotalImpuesto, "", //pValCopagoCompPago
                                pPorcIgv, "", //pNumCompCoPago
                                pValTotalAhorro, false, //vEsPedidoConvenio
                                "", //pImprDatAdic
                                "", //pTipoClienteConvenio
                                "", //pCodTipoConvenio
                                fechaBD, "", //pRefTipComp
                                pValRedondeo, pNomImpreso, pDirImpreso, pNumDocImpreso, "", //vPrctBeneficiario
                                "", //vPrctEmpresa
                                margen,
                                pValTotalBruto, pValTotalAfecto, pValTotalDescuento,
                                valor);
                        
                        impresora.imprimir(vPrint, VariablesCaja.vRutaImpresora, true, VariablesCaja.vNumPedVta, pNumComprobante, "C",
                                           ruta, vLineasComp);    
                    
                    }
                }
            } else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_NOTA_CREDITO)) {

                UtilityConvenioBTLMF.muestraMensajeImpresion(pJDialog, pTipCompPago, ++acumuCorre,
                                                             VariablesCaja.vSecComprobante, VariablesCaja.vNumPedVta,
                                                             false, isReimpresion);
                if (FarmaVariables.vAceptar) {
                    ruta = ruta + fechaImpresion + "_" + "NC_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                    
                    if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MAYORISTA)) {
                        context = new ContextImpresion(new FormatoImpresionC());
                        vLineasComp = 70;
                    } 
                        ArrayList<String> vPrint =
                            context.formatoNotaCredito(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante,
                                                               pValTotalImpuesto, "", //pValCopagoCompPago
                                pPorcIgv, "", //pNumCompCoPago
                                pValTotalAhorro, false, //vEsPedidoConvenio
                                "", //pImprDatAdic
                                "", //pTipoClienteConvenio
                                "", //pCodTipoConvenio
                                fechaBD, "", //pRefTipComp
                                pValRedondeo, pNomImpreso, pDirImpreso, pNumDocImpreso, "", //vPrctBeneficiario
                                "", //vPrctEmpresa
                                margen,
                                pValTotalBruto, pValTotalAfecto, pValTotalDescuento,
                                valor);
                        
                        impresora.imprimir(vPrint, VariablesCaja.vRutaImpresora, true, VariablesCaja.vNumPedVta, pNumComprobante, "C",
                                           ruta, vLineasComp);    
                    
                }

            }
        }
        
        //GFonseca 27.12.2013 Se añade nuevo método de abrir gabeta.
        UtilityCaja.abrirGabeta(null, false, VariablesCaja.vNumPedVta);
    }

    public static void obtieneInfoVendedor() {
        ArrayList myArray = new ArrayList();
        try {
            DBCaja.obtenerInfoVendedor(myArray);
            if (myArray.size() == 0)
                return;
            VariablesCaja.vNomVendedorImpreso = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesCaja.vApePatVendedorImpreso = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
        } catch (SQLException sql) {
            log.error("",sql);
        }
    }

    // RHERRERA : OBTIENE LOS SEC COMPROBANTE DE PAGO

    public static void obtieneAgrupaComprobante()

    {
        ArrayList myArray = new ArrayList();
        String secCompPago = "";
        VariablesCaja.vArrayList_SecCompPago = new ArrayList();


        try {
            DBCaja.obtieneInfoDetalleAgrupacion(myArray);
        } catch (SQLException e) {
            log.error("", e);
        }
        for (int j = 0; j < myArray.size(); j++) {
            secCompPago = ((String)((ArrayList)myArray.get(j)).get(1)).trim();

            VariablesCaja.vArrayList_SecCompPago.add(secCompPago);
        }

    }


    public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus, boolean isReimpresion,
                                                   boolean bCobroProforma) {

        procesoImpresionComprobante(pJDialog, pObjectFocus, false, isReimpresion, bCobroProforma);
        acumuCorre = 0; //Incializamos el correlativo de numero de impresion

    }

    public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus, boolean vIndImpresionAnulado,
                                                   boolean isReimpresion, boolean bCobroProforma) {
        String mensCons = "";
        String pIsCompManual = "N";
        String pTipCompManual = "";
        String pSerieCompManual = "";
        String pNumCompManual = "";
        String[] pCadenaCompManual = new String[0];
        try {
            //dubilluz 14.10.2014
            pIsCompManual = DBCaja.getIndIngresoCompManual(VariablesCaja.vNumPedVta);

            String secImprLocal = "";
            String resultado = "";

            VariablesCaja.vImprimeFideicomizo = false;
            VariablesCaja.vCadenaFideicomizo = getMensajeFideicomizo();

            if (VariablesCaja.vCadenaFideicomizo != null)
                VariablesCaja.vCadenaFideicomizo = VariablesCaja.vCadenaFideicomizo.trim();
            else
                VariablesCaja.vCadenaFideicomizo = "";

            if (VariablesCaja.vCadenaFideicomizo.length() > 0) {
                VariablesCaja.vImprimeFideicomizo = true;
            }

            if (VariablesVentas.vEsPedidoDelivery) {
                actualizarDatosDelivery(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
            }

            VariablesCaja.vNumCuponesImpresos = 0;
            int cantidadCupones = imprimeCupones(pJDialog);
            //CHUANES
            //CANTIDAD DE CUPONES PARA COMPROBANTE ELECTRONICO.
            ConstantesDocElectronico.CANTCUPON = cantidadCupones;
            if (cantidadCupones > 0) {
                VariablesCaja.vNumCuponesImpresos = cantidadCupones;
            }

        //ERIOS 22.01.2016 Por tipo de comprobante
        for(HashMap<String,Object> mapSecCompPago : VariablesCaja.vListMap_SecCompPago){
            VariablesVentas.vTip_Comp_Ped = (String)mapSecCompPago.get("TIP_COMP_PAGO");
            VariablesCaja.vNumSecImpresionComprobantes = (Integer)mapSecCompPago.get("NUM_SEC_IMP_COMP");
            VariablesCaja.vArrayList_SecCompPago = (ArrayList<String>)mapSecCompPago.get("LIST_SEC_COMP");
                
            if (ConstantsVentas.TIPO_COMP_BOLETA.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped) ||
                ConstantsVentas.TIPO_COMP_TICKET.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped)) {
                secImprLocal =
                        DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, VariablesVentas.vTip_Comp_Ped, pIsCompManual);
            } else if (ConstantsVentas.TIPO_COMP_FACTURA.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped) ||
                       ConstantsVentas.TIPO_COMP_TICKET_FACT.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped)) {
                if (ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL.equalsIgnoreCase(VariablesVentas.vTipoPedido)) {
                    secImprLocal = obtieneSecImprVtaInstitucional(ConstantsVentas.TIPO_COMP_FACTURA);
                    if ("".equalsIgnoreCase(secImprLocal)) {
                        secImprLocal = VariablesCaja.vSecImprLocalFactura;
                    } else {
                        FarmaUtility.showMessage(pJDialog,
                                                 "Se va a imprimir un documento del tipo Venta Institucional \n " +
                                                 "Cambie de hoja en la impresora de Reportes para proseguir",
                                                 pObjectFocus);
                    }
                } else {
                    secImprLocal = DBCaja.getObtieneSecFacImpPorIP(FarmaVariables.vIpPc);
                    //VariablesCaja.vSecImprLocalFactura;
                }
            } else if (ConstantsVentas.TIPO_COMP_GUIA.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped)) {
                secImprLocal = VariablesCaja.vSecImprLocalGuia;
            } else if (ConstantsVentas.TIPO_COMP_NOTA_CREDITO.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped)){
                secImprLocal = VariablesCaja.vSecImprLocalNotaCredito;
            }

            //DUBILLUZ 14.10.2014
            if (pIsCompManual.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                if (!UtilityEposTrx.validacionEmiteElectronico()) {
                    //KMONCADA 22.01.2015
                    if (UtilityEposTrx.isActFuncElectronica()) {
                        resultado = ConstantsCaja.RESULTADO_COMPROBANTE_EXITOSO;
                    } else {
                        resultado = DBCaja.verificaComprobantePago(secImprLocal, VariablesCaja.vNumSecImpresionComprobantes, VariablesCaja.vNumPedVta);
                    }
                } else {
                    resultado = ConstantsCaja.RESULTADO_COMPROBANTE_EXITOSO;
                }

            } else {
                resultado = ConstantsCaja.RESULTADO_COMPROBANTE_EXITOSO;
                pCadenaCompManual = DBCaja.getCadenaCompManual(VariablesCaja.vNumPedVta).split("@");
                if (pCadenaCompManual.length == 3) {
                    pTipCompManual = pCadenaCompManual[0];
                    pSerieCompManual = pCadenaCompManual[1];
                    pNumCompManual = pCadenaCompManual[2];
                } else {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog, "El pedido fue Cobrado pero no se imprimió Comprobante(s).\n" +
                            "Se ha Ingresado  " + DBCaja.obtieneDescripcionComprobante(VariablesVentas.vTip_Comp_Ped) +
                            " " + "\nIngrese a la opcion \"Mantenimiento de Impresoras\",\nLuego " +
                            "a la opción de \"Reimpresión de Pedido.\"", pObjectFocus);

                    return;
                }
            }

            if (ConstantsCaja.RESULTADO_COMPROBANTE_NO_EXITOSO.equalsIgnoreCase(resultado)) {
                FarmaUtility.liberarTransaccion();
                // KMONCADA 18.09.2014 MODIFICA EL MENSAJE DE DUPLICIDAD DE DOCUMENTOS.
                FarmaUtility.showMessage(pJDialog, "El pedido fue Cobrado pero no se imprimió Comprobante(s).\n" +
                        "Verifique el correlativo del tipo de comprobante " +
                        DBCaja.obtieneDescripcionComprobante(VariablesVentas.vTip_Comp_Ped) + " " +
                        "\nIngrese a la opcion \"Mantenimiento de Impresoras\",\nLuego " +
                        "a la opción de \"Reimpresión de Pedido.\"", pObjectFocus);

                return;
            }

            
            ArrayList listaNumCompro = new ArrayList();
            boolean isNuevoNumeroComprobante = false;
            for (int i = 1; i <= VariablesCaja.vNumSecImpresionComprobantes; i++) {
                String secCompPago = VariablesCaja.vArrayList_SecCompPago.get(i - 1).trim();

                // KMONCADA 22.01.2015 METODOS PARA OBTENER INDICADOR DE ELECTRONICO Y NRO DE COMPROBANTE
                boolean isComprobanteElectronico = DBEpos.isComprobanteElectronico(FarmaVariables.vCodGrupoCia, 
                                                                                   FarmaVariables.vCodLocal, 
                                                                                   VariablesCaja.vNumPedVta, 
                                                                                   secCompPago);
                
                VariablesCaja.vNumCompImprimir = DBCaja.getNumeroComprobante(FarmaVariables.vCodGrupoCia,
                                                                             FarmaVariables.vCodLocal,
                                                                             VariablesCaja.vNumPedVta,
                                                                             secCompPago);
                
                if(isComprobanteElectronico){
                    //SE OBTIENE EL NUMERO DE COMPROBANTE ELECTRONICO DE LA BD
                    //VariablesCaja.vNumCompImprimir = UtilityEposTrx.obtieneNumCompPagoE(VariablesCaja.vNumPedVta, secCompPago);

                    if (VariablesCaja.vNumCompImprimir == null) {

                        // valida conexion con EPOS.
                        if (!UtilityEposTrx.vPermiteCobro(pJDialog))
                            return;

                        if (UtilityEposTrx.validacionEmiteElectronico()) {
                            //LLAMAR AL PROCESO DE GENERACION DE COMPROBANTE PARA OBTENER EL NUMERO DE COMPROBANTE
                            //EL DOCUMENTO SIGUE CON E
                            //KMONCADA 26.10.2015 CARGA MAL LA VARIABLE CUANDO EL PEDIDO TIENE MAS DE UN COMPROBANTE
                            VariablesCaja.vNumCompImprimir = UtilityEposTrx.getNumCompPagoE(VariablesVentas.vTip_Comp_Ped,
                                                                                            VariablesCaja.vNumPedVta,
                                                                                            //VariablesCaja.vSecComprobante,
                                                                                            secCompPago,
                                                                                            "", 
                                                                                            false);
                            // KMONCADA 20.10.2014 VALIDACION DE DUPLICIDAD DE COMPROBANTES ELECTRONICOS
                            // DUPLICADO EN LOCAL
                            /*if("L".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                throw new Exception("El número de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya fue asignado en el local, comuniquese con Sistemas...!!");
                            }
                            // DUPLICADO EN RAC
                            if("R".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                throw new Exception("El número de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya se encuentra registrado en RAC, comuniquese con Sistemas...!!");
                            }

                            //mensaje de error
                            if ( VariablesCaja.vNumCompImprimir.startsWith("E")){
                                    FarmaUtility.liberarTransaccion();
                                    FarmaUtility.showMessage(pJDialog, "Error - Proceso Impresion Comprobante \n"+
                                                                        VariablesCaja.vNumCompImprimir
                                                                        ,
                                                                                    pObjectFocus);
                                    return;
                                }*/

                            if (VariablesCaja.vNumCompImprimir != "" || VariablesCaja.vNumCompImprimir != "0") {
                                //KMONCADA 26.10.2015 CARGA MAL LA VARIABLE CUANDO EL PEDIDO TIENE MAS DE UN COMPROBANTE
                                String tipoCompPago = UtilityEposTrx.modificarTipCompPago(VariablesVentas.vTip_Comp_Ped, 
                                                                                          secCompPago);
                                                                                    //VariablesCaja.vSecComprobante);

                            } else {
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.showMessage(pJDialog,
                                                         "Error al cobrar pedido !\n El numero de comprobante electronico no se encontro !",
                                                         pObjectFocus);
                                return;
                            }


                        } else {
                            /*FarmaUtility.liberarTransaccion();
                                    FarmaUtility.showMessage(pJDialog, "El comprobante fue generar en el Proceso Electronico, \n"+
                                                                       "Por favor active el proceso Electronico para genera el numero de comprobante",
                                                                                    pObjectFocus);
                                   return;*/
                            // KMONCADA 02.12.2014 EN CASO DE REIMPRESION ANTE UN ERROR SE EMITIRA COMO CONTIGENCIA
                            //KMONCADA 26.10.2015 CARGA MAL LA VARIABLE CUANDO EL PEDIDO TIENE MAS DE UN COMPROBANTE
                            DBEpos.deshabilitarFlagEComprobante(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                                                VariablesCaja.vNumPedVta,
                                                                secCompPago);
                                                                //VariablesCaja.vSecComprobante);
                            VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus); // ya existia
                            VariablesCaja.vNumCompImprimir =
                                    obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus); // ya existia
                            //indicElectronico = "FALSE";
                            isComprobanteElectronico = false;
                        }
                    }

                } else {
                    isNuevoNumeroComprobante = false;
                    
                    // El comprobante no es electronico
                    if (pCadenaCompManual.length > 0) {
                        pTipCompManual = pCadenaCompManual[0];
                        if (pIsCompManual.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                            VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus); // ya existia
                        } else {
                            pSerieCompManual = pCadenaCompManual[1];
                            pNumCompManual = pCadenaCompManual[2];
                            
                            // KMONCADA 14.09.2015 REINTENTOS PARA OBTENER NUMERO DE COMPROBANTE EN CASO DE QUE PEDIDO GENERE MAS DE UNO
                            int cantidadIntentos = DBCaja.obtieneCantidadIntentoComprobante();
                            int contadorIntentos = 0;
                            boolean pExiste = true;
                            do{
                                //int nroCp = Integer.parseInt(pNumCompManual)+(i-1);
                                int nroCp = Integer.parseInt(pNumCompManual)+contadorIntentos;
                                String auxPNumComp= FarmaUtility.completeWithSymbol(nroCp+"", 7, "0", "I");
                                VariablesCaja.vNumCompImprimir = pSerieCompManual + auxPNumComp;
                                String pResultado = DBCaja.getValidaCompManual(pTipCompManual, pSerieCompManual, auxPNumComp, VariablesCaja.vNumPedVta);
                                pExiste = true;
                                if (pResultado.trim().equalsIgnoreCase("S")){
                                    pExiste = false;
                                    nroCp++;
                                    auxPNumComp= FarmaUtility.completeWithSymbol(nroCp+"", 7, "0", "I");
                                    pCadenaCompManual[2] = auxPNumComp;
                                    isNuevoNumeroComprobante = true;
                                }
                                log.info("IMPRESION DE COMPROBANTE MANUAL : "+VariablesCaja.vNumCompImprimir+", INTENTO "+contadorIntentos+ " DE "+cantidadIntentos);
                                contadorIntentos++;
                            }while(pExiste && contadorIntentos < cantidadIntentos);
                            
                            if(pExiste){
                                /*VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus); // ya existia
                                resultado = DBCaja.verificaComprobantePago(secImprLocal, 1, VariablesCaja.vNumPedVta);
                                
                                if (ConstantsCaja.RESULTADO_COMPROBANTE_NO_EXITOSO.equalsIgnoreCase(resultado)) {*/
                                    FarmaUtility.liberarTransaccion();
                                    // KMONCADA 18.09.2014 MODIFICA EL MENSAJE DE DUPLICIDAD DE DOCUMENTOS.
                                    FarmaUtility.showMessage(pJDialog, "Comprobante no pude emitirse.\n" +
                                            "Verifique el correlativo del tipo de comprobante \n" +
                                            DBCaja.obtieneDescripcionComprobante(VariablesVentas.vTip_Comp_Ped) + " " +
                                            "Cantidad de intentos para obtener número de comprobante agotado."+
                                            "\nIngrese a la opcion \"Mantenimiento de Impresoras\",\nLuego " +
                                            "a la opción de \"Reimpresión de Pedido.\"", pObjectFocus);

                                    return;
                                /*}
                                isNuevoNumeroComprobante = true;*/
                            }
                            
                            // KMONCADA 26.01.2015 EN CASO DE COMP.MANUAL GENERA MAS DE COMPROBANTES CONSULTA POR EL SGTE DISPONIBLE
                            /* if(i==1){
                                VariablesCaja.vNumCompImprimir = pSerieCompManual + pNumCompManual;
                                //KMONCADA 27.01.2015 VALIDARA SI EL COMPROBANTE MANUAL YA FUE UTILIZADO
                                String pResultado = DBCaja.getValidaCompManual(pTipCompManual, pSerieCompManual, pNumCompManual);
                                                                
                                boolean pExiste = true;
                                if (pResultado.trim().equalsIgnoreCase("S"))
                                    pExiste = false;
                                
                                if(pExiste){
                                    FarmaUtility.showMessage(pJDialog, "Comprobante "+pSerieCompManual+" - "+pNumCompManual+"\n"+
                                                                        "Ya fue asignado a otro pedido, se asignara nuevo comprobante!!!."
                                                             , pObjectFocus);
                                    VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus); // ya existia
                                }
                                
                            }else{
                                //KMONCADA 27.01.2015 VALIDA SI EL NUMERO DE COMPROBANTE A ASIGNAR NO ESTA REGISTRADO.
                                resultado = DBCaja.verificaComprobantePago(secImprLocal, 1);
                                if (ConstantsCaja.RESULTADO_COMPROBANTE_NO_EXITOSO.equalsIgnoreCase(resultado)) {
                                    FarmaUtility.liberarTransaccion();
                                    // KMONCADA 18.09.2014 MODIFICA EL MENSAJE DE DUPLICIDAD DE DOCUMENTOS.
                                    FarmaUtility.showMessage(pJDialog, "Pedido ha generado mas de un comprobante.\n" +
                                            "No se pudo asignar Nro.Comprobante a los siguiente comprobantes.\n" +
                                            "Verifique el correlativo del tipo de comprobante " +
                                            DBCaja.obtieneDescripcionComprobante(VariablesVentas.vTip_Comp_Ped) + " " +
                                            "\nIngrese a la opcion \"Mantenimiento de Impresoras\",\nLuego " +
                                            "a la opción de \"Reimpresión de Pedido.\"", pObjectFocus);

                                    return;
                                }
                                
                                VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus); // ya existia
                            } */
                        }
                    } else {
                        if(VariablesCaja.vNumCompImprimir == null){
                            resultado = DBCaja.verificaComprobantePago(secImprLocal, 1, VariablesCaja.vNumPedVta);
                            
                            if (ConstantsCaja.RESULTADO_COMPROBANTE_NO_EXITOSO.equalsIgnoreCase(resultado)) {
                                FarmaUtility.liberarTransaccion();
                                // KMONCADA 18.09.2014 MODIFICA EL MENSAJE DE DUPLICIDAD DE DOCUMENTOS.
                                FarmaUtility.showMessage(pJDialog, "Comprobante no pude emitirse.\n" +
                                        "Verifique el correlativo del tipo de comprobante " +
                                        DBCaja.obtieneDescripcionComprobante(VariablesVentas.vTip_Comp_Ped) + " " +
                                        "\nIngrese a la opcion \"Mantenimiento de Impresoras\",\nLuego " +
                                        "a la opción de \"Reimpresión de Pedido.\"", pObjectFocus);

                                return;
                            }
                            
                            VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus); // ya existia
                            isNuevoNumeroComprobante = true;
                        }
                    }
                }
                //FIN LTAVARA

                if (VariablesCaja.vNumCompImprimir == null || "".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog,
                                             "El pedido fue Cobrado pero no se pudo determinar el Numero de Comprobante. Verifique!!!",
                                             pObjectFocus);
                    return;
                }

                //obteniendo el detalle del comprobante a imprimir
                //el mensaje de error se encuentra dentro del mismo metodo, tb se hace rollback dentro del mismo metodo
                if (!obtieneDetalleImprComp(pJDialog, secCompPago, pObjectFocus)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog,
                                             "El pedido fue Cobrado pero no se pudo obtener el detalle del comprobante a imprimir. Verifique!!!",
                                             pObjectFocus);
                    return;
                }


                //obtiene el total del comprobante a imprimir
                //muestra el mensaje de error dentro del mismo metodo, tb hace rollback dentro del mismo metodo
                if (!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog,
                                             "El pedido fue Cobrado pero no se pudo determinar los Totales del Comprobante. Verifique!!!.",
                                             pObjectFocus);
                    return;
                }
                /*
                * @author ltavara
                * Cuando el documento es electronico el NUM_COMP_PAGO no incrementa
                * @since 22/08/2014
                * */

                if (pIsCompManual.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    //if (indicElectronico.equalsIgnoreCase(ConstantesDocElectronico.INDELECTRONICO)){ //SI ES ELECTRONICO
                    if(isComprobanteElectronico){
                        //EL TERCER PARA METRO ES EL NUM_COMP_PAGO PERO EN EL ELECTRONICO ES EL MISMO QUE EL SEC_COMP_PAGO
                        actualizaComprobanteImpreso(secCompPago, VariablesVentas.vTip_Comp_Ped, secCompPago);
                    } else {
                        if(!ConstantsVentas.TIPO_COMP_NOTA_CREDITO.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped)){
                            actualizaComprobanteImpreso(secCompPago, VariablesVentas.vTip_Comp_Ped,
                                                        VariablesCaja.vNumCompImprimir);                        
                            actualizaNumComp_Impresora(secImprLocal); //INCREMENTA EL NUM_COMP_PAGO
                        }
                    }
                } else {
                    actualizaComprobanteImpreso(secCompPago, VariablesVentas.vTip_Comp_Ped, VariablesCaja.vNumCompImprimir);
                    int pNumComp = Integer.parseInt(pNumCompManual);
                    pNumCompManual = FarmaUtility.completeWithSymbol((pNumComp + 1) + "", 7, "0", "I");
                    if(isNuevoNumeroComprobante){
                        actualizaNumComp_Impresora(secImprLocal); //INCREMENTA EL NUM_COMP_PAGO
                    }
                }
                //agregando los numero de comprobantes
                listaNumCompro.add(VariablesCaja.vNumCompImprimir);
            }
            
            FarmaUtility.aceptarTransaccion();

            for (int i = 0; i < listaNumCompro.size(); i++) {

                String secCompPago = VariablesCaja.vArrayList_SecCompPago.get(i).trim();
                
                if (!obtieneDetalleImprComp(pJDialog, secCompPago, pObjectFocus)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog,
                                             "No se pudo obtener el detalle del comprobante a imprimir. Verifique!!!",
                                             pObjectFocus);
                    return;
                }

                if (!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog,
                                             "No se pudo determinar los Totales del Comprobante. Verifique!!!.",
                                             pObjectFocus);
                    return;
                }

                if(!UtilityPtoVenta.isLocalVentaMayorista()){
                    // KMONCADA 22.01.2015 NO VERIFICARA RUTA DE IMPRESION EN CASO DE IMPRESION DE NOTA DE CREDITO
                    if (!VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_NOTA_CREDITO)){
                        VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal.trim());
                    }
                }else{
                    VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal.trim());
                }

                if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)) {
                    VariablesCaja.vModeloImpresora = DBImpresoras.getModeloImpresora(secImprLocal);
                }

                if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET) ||
                    VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_BOLETA)) {
                    if (pIsCompManual.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                        if (!validaImpresioPorIP(FarmaVariables.vIpPc, VariablesVentas.vTip_Comp_Ped, pJDialog,
                                                 pObjectFocus)) {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(pJDialog,
                                                     "La IP no cuenta con una impresora asignada. Verifique!!!.",
                                                     pObjectFocus);
                            return;
                        }
                }
                int valor = i;

                // LTAVARA 18.09.2014 ENVIAR EL SEC_COMP_PAGO PARA EL PROCESO DE IMPRESION
                VariablesCaja.vSecComprobante = secCompPago; 
               
                boolean isComprobanteElectronico = DBEpos.isComprobanteElectronico(FarmaVariables.vCodGrupoCia, 
                                                                                   FarmaVariables.vCodLocal, 
                                                                                   VariablesCaja.vNumPedVta, 
                                                                                   secCompPago);
                
                imprimeComprobantePago(pJDialog, VariablesCaja.vArrayList_DetalleImpr,
                                       VariablesCaja.vArrayList_TotalesComp, VariablesVentas.vTip_Comp_Ped,
                                       listaNumCompro.get(i).toString(), vIndImpresionAnulado, valor, secCompPago,
                                       isReimpresion, isComprobanteElectronico);

                if (FarmaVariables.vAceptar) {
                    actualizarFechaImpresion(secCompPago, VariablesVentas.vTip_Comp_Ped);
                }
                FarmaUtility.aceptarTransaccion();
            }
        }
            
            actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
            FarmaUtility.aceptarTransaccion();
            
            //ERIOS 12.02.2016 Para impresion de Nota Credito, termina el proceso.
            if (UtilityPtoVenta.isLocalVentaMayorista() &&
                VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_NOTA_CREDITO)){
                return;
            }
            
            //ERIOS 19.01.2016 Imprime voucher de devolucion
            (new FacadeCaja()).impresionVoucher(VariablesCaja.vNumPedVta);
            
            // /////////////////////////////////////////////////////////////
            // IMPRESION DE PEDIDOS DELIVERY PROVINCIA
            // /////////////////////////////////////////////////////////////
            // 30.06.2014 DUBILLUZ
            if (VariablesVentas.vEsPedidoDelivery) {
                log.info("SI IMPRIME imprimeDatosDeliveryLocal PROVINCIA");
                imprimeDatosDeliveryLocal(pJDialog, VariablesCaja.vNumPedVta);
            } else {
                log.info("NO IMPRIME imprimeDatosDeliveryLocal PROVINCIA");
            }
            ////////////////////////////////////////////////////////////////

            boolean consejo = imprimeConsejos(pJDialog);
            if (consejo) {
                mensCons = "\nRecoger Consejo.";
            }
            imprimeMensajeCampana(pJDialog, VariablesVentas.vNum_Ped_Vta);

            // /////////////////////////////////////////////////////////////
            // IMPRESION DE PEDIDOS DELIVERY AUTOMATICO
            // /////////////////////////////////////////////////////////////
            // 30.06.2014 DUBILLUZ
            if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S") && !bCobroProforma) {
                log.info("SI IMPRIME imprimeDatosDeliveryLocal AUTOMTICO " + VariablesCaja.vNumPedVta);
                String vNumPedDely = DBCaja.obtieneNumPedDelivery(VariablesCaja.vNumPedVta);
                log.info("SI IMPRIME imprimeDatosDeliveryLocal AUTOMTICO " + vNumPedDely);
                imprimeDatosDelivery(pJDialog, vNumPedDely);
            } else {
                log.info("NO IMPRIME imprimeDatosDeliveryLocal AUTOMTICO");
            }
            ////////////////////////////////////////////////////////////////

            String DniClienteImpRecord = obtenerDniPedidoAcumulaVenta(VariablesCaja.vNumPedVta);

            if (DniClienteImpRecord.trim().length() > 0) {
                VariablesCaja.vIndLineaMatriz = FarmaConstants.INDICADOR_N;
                //??? VariablesCaja.vIndLineaMatriz siempre va a tener el valor de FarmaConstants.INDICADOR_N en este punto
                if (FarmaConstants.INDICADOR_S.equalsIgnoreCase(VariablesCaja.vIndLineaMatriz)) {
                    imprimeUnidRestCampXCliente(pJDialog, DniClienteImpRecord.trim(), VariablesCaja.vNumPedVta);
                } else {
                    imprimirUnidAcumCampXCliente(pJDialog, DniClienteImpRecord.trim(), VariablesCaja.vNumPedVta);
                }
            }

            //ERIOS 15.10.2013 Impresion de ticket anulado
            if (vIndImpresionAnulado) {
                FarmaUtility.showMessage(pJDialog, "¡Pedido Anulado!", pObjectFocus);
                return;
            }

            //ERIOS 2.4.4 Intenta actualizar la fecha de respuesta de recarga
            if (VariablesCaja.vIndPedidoConProdVirtual &&
                !UtilityCaja.get_ind_ped_con_rimac(VariablesCaja.vNumPedVta)
                ) {
                DlgProcesarProductoVirtual dlgProcesarProductoVirtual =
                    new DlgProcesarProductoVirtual(null, "Virtual", true);
                dlgProcesarProductoVirtual.actualizaFechaRespuesta();
            }

            //INI ASOSA - 10/02/2015 - 
            boolean flagImpVouPtos = false;
            String msgVouPtos = "";
            if (VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean()!=null) {
                flagImpVouPtos = getIndVariosCompPedido(VariablesCaja.vNumPedVta);
                if (flagImpVouPtos) {
                    imprVouPtosCliente(VariablesCaja.vNumPedVta);
                    msgVouPtos = "Recoja su voucher de puntos";
                }
            }
            
            //FIN ASOSA - 24/02/2015 - 
            
            // KMONCADA 08.06.2016 [PROYECTO M] NO IMPRIME AL FINAL PARA EL CASO DE MAYORISTA
            if(!UtilityPtoVenta.isLocalVentaMayorista()){
                //KMONCADA 12.05.2016 IMPRESION DE COMANDA DE DESPACHO
                try{
                    (new UtilityDespacho()).imprimirComandaDespachoVenta(pJDialog, VariablesCaja.vNumPedVta);
                }catch(Exception ex){
                    log.info("[IMPRESION DE COMANDA DESPACHO] ERROR EN IMPRESION: "+ex.getMessage());
                }
            }
            
            if (!VariablesCaja.vIndPedidoConProdVirtual) {
                if (VariablesCaja.vEstadoSinComprobanteImpreso.equalsIgnoreCase("N")) {
                    if (cantidadCupones > 0) {
                        FarmaUtility.showMessage(pJDialog, "Pedido Cobrado con éxito. \n" +
                                "Comprobantes Impresos con éxito\n" +
                                "Favor de recoger " + cantidadCupones + " cupones" + mensCons
                                                 + "\n" + msgVouPtos,   //ASOSA - 24/02/2015 - 
                                                 pObjectFocus);
                    } else {
                        FarmaUtility.showMessage(pJDialog, "Pedido Cobrado con éxito. \n" +
                                "Comprobantes Impresos con éxito " + mensCons
                                                 + "\n" + msgVouPtos,   //ASOSA - 24/02/2015 - 
                                                                         pObjectFocus);
                    }
                } else {
                    if (cantidadCupones > 0) {
                        FarmaUtility.showMessage(pJDialog,
                                                 "Pedido Cobrado con éxito." + "\nFavor de recoger " + cantidadCupones +
                                                 " cupones" + mensCons +
                                                 "\nCOMPROBANTES NO IMPRESOS, Verifique Impresora " +
                                //VariablesCaja.vRutaImpresora +
                                "\nReimprima Comprobante, Correlativo :" + VariablesCaja.vNumPedVta
                                                 + "\n" + msgVouPtos,   //ASOSA - 24/02/2015 - 
                                                                        pObjectFocus);
                    } else {
                        FarmaUtility.showMessage(pJDialog,
                                                 "Pedido Cobrado con éxito." + "\nCOMPROBANTES NO IMPRESOS, Verifique Impresora " +
                                //VariablesCaja.vRutaImpresora +
                                "\nReimprima Comprobante, Correlativo :" + VariablesCaja.vNumPedVta
                                                 + "\n" + msgVouPtos,   //ASOSA - 24/02/2015 - 
                                                                         pObjectFocus);
                    }
                }
            }

            if (!"".equalsIgnoreCase(VariablesRecetario.strCodigoRecetario)) {
                imprimeContraseniaRecetario(VariablesCaja.vNumPedVta);
                VariablesRecetario.strCodigoRecetario = "";
            }

            VariablesCaja.vNumCuponesImpresos = 0;
            mensCons = "";
            
            

        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error en BD al Imprimir los Comprobantes del Pedido.\n" +
                    sql, pObjectFocus);
            enviaErrorCorreoPorDB(sql.toString(), VariablesCaja.vNumPedVta_Anul);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            // DUPLICADO EN LOCAL
            if ("L".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)) {
                FarmaUtility.showMessage(pJDialog,
                                         "El número de comprobante electronico " + VariablesCaja.vNumCompImprimir.substring(1) +
                                         " \n ya fue asignado en el local, comuniquese con Sistemas...!!",
                                         pObjectFocus);
            } else {
                // DUPLICADO EN RAC
                if ("R".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)) {
                    FarmaUtility.showMessage(pJDialog,
                                             "El número de comprobante electronico " + VariablesCaja.vNumCompImprimir.substring(1) +
                                             " \n ya se encuentra registrado en RAC, comuniquese con Sistemas...!!",
                                             pObjectFocus);
                } else {
                    FarmaUtility.showMessage(pJDialog,
                                             "Error en la Aplicacion al Imprimir los Comprobantes del Pedido.\n" +
                            e, pObjectFocus);
                }
            }
            enviaErrorCorreoPorDB(e.toString(), VariablesCaja.vNumPedVta_Anul);
        }
    }

    private static String obtieneSecImprVtaInstitucional(String pTipComp) {

        String secImprVtaInst = "";
        ArrayList myArray = new ArrayList();

        try {
            DBCaja.obtieneSecuenciaImprVtaInstitucional(myArray, pTipComp);
            if (myArray.size() == 0 || myArray.size() > 1) {
                return secImprVtaInst;
            }
            secImprVtaInst = FarmaUtility.getValueFieldArrayList(myArray, 0, 0);
            log.debug("secImprVtaInst : " + secImprVtaInst);
            return secImprVtaInst;

        } catch (SQLException sql) {
            secImprVtaInst = "";
            log.error("",sql);

            //JMIRANDA 23/07/09 ENVIA ERROR X CORREO
            enviaErrorCorreoPorDB(sql.toString(), null);
            return secImprVtaInst;

        }

    }

    private static String obtieneNumCompPago_ForUpdate(JDialog pJDialog, String pSecImprLocal, Object pObjectFocus) {
        String numCompPago = "";
        ArrayList myArray = new ArrayList();
        try {
            /*if(VariablesCaja.vNumPedVta_Anul.trim().length()>1)
      DBCaja.obtieneNumCompTip_ForUpdate(myArray, pSecImprLocal,VariablesCaja.vNumPedVta_Anul);
      else*/
            DBCaja.obtieneNumComp_ForUpdate(myArray, pSecImprLocal);

            log.debug("VariablesVentas.vTip_Comp_Ped JCORTEZ : " + VariablesVentas.vTip_Comp_Ped);
            log.debug("VariablesCaja.vNumPedVta JCORTEZ : " + VariablesCaja.vNumPedVta);

            if (myArray.size() == 0) {
                return numCompPago;
            }
            numCompPago =
                    ((String)((ArrayList)myArray.get(0)).get(0)).trim() + "" + ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            log.debug("numCompPago : " + numCompPago);
            return numCompPago;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("",sql);
            numCompPago = "";
            FarmaUtility.showMessage(pJDialog, "Error al validar Agrupacion de Comprobante.", pObjectFocus);
            
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sql.toString().toString(), null);
            return numCompPago;
        }
    }

    private static boolean obtieneDetalleImprComp(JDialog pJDialog, String pSecCompPago, Object pObjectFocus) {
        VariablesCaja.vArrayList_DetalleImpr = new ArrayList();
        boolean valor = true;
        long tmpT1, tmpT2;
        tmpT1 = System.currentTimeMillis();
        try {
            DBCaja.obtieneInfoDetalleImpresion(VariablesCaja.vArrayList_DetalleImpr, pSecCompPago);
            if (VariablesCaja.vArrayList_DetalleImpr.size() == 0) {
                //JCALLO 19.12.2008 , debido a que no se hacia rollback si no encontraba el detalle del pedido
                FarmaUtility.liberarTransaccion(); //JCALLO 19.12.2008
                FarmaUtility.showMessage(pJDialog, "No se pudo determinar el detalle del Pedido. Verifique!!!.",
                                         pObjectFocus);
                valor = false;
            }
            log.info("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr.size());
            valor = true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error al obtener Detalle de Impresion de Comprobante.", pObjectFocus);
            
            valor = false;

            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sql.toString(), null);
        }

        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 4: Det.Comp Pago:" + (tmpT2 - tmpT1) + " milisegundos");


        return valor;
    }

    public static boolean obtieneTotalesComprobante(JDialog pJDialog, String pSecCompPago, Object pObjectFocus) {
        VariablesCaja.vArrayList_TotalesComp = new ArrayList();
        boolean valor = false;
        long tmpT1, tmpT2;
        tmpT1 = System.currentTimeMillis();
        try {
            DBCaja.obtieneInfoTotalesComprobante(VariablesCaja.vArrayList_TotalesComp, pSecCompPago);
            if (VariablesCaja.vArrayList_TotalesComp.size() == 0) {
                //JCALLO 19.12.2008 , debido a que no se hacia rollback si no encontraba el detalle del pedido
                FarmaUtility.liberarTransaccion(); //JCALLO 19.12.2008
                FarmaUtility.showMessage(pJDialog, "No se pudo determinar los Totales del Comprobante. Verifique!!!.",
                                         pObjectFocus);
                valor = false;
            }
            log.debug("VariablesCaja.vArrayList_TotalesComp : " + VariablesCaja.vArrayList_TotalesComp.size());
            valor = true;
        } catch (SQLException sql) {            
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error al obtener Totales de Comprobante.", pObjectFocus);
            
            valor = false;
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sql.toString(), null);

        }

        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 5:Calculo de Total de los Comprobantes:" + (tmpT2 - tmpT1) + " milisegundos");

        return valor;

    }

    public static void actualizaComprobanteImpreso(String pSecCompPago, String pTipCompPago,
                                                   String pNumCompPago) throws Exception {
        long tmpT1, tmpT2;
        boolean lanzaError = false;
        tmpT1 = System.currentTimeMillis();
        try {
            DBCaja.actualizaComprobanteImpreso(pSecCompPago, pTipCompPago, pNumCompPago);
        } catch (SQLException sql) {

            lanzaError = true;
            
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumCompPago);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("ERROR DE ACTUALIZAR COMPROBANTE IMPRESO : ");
            log.error("", sql);

            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sql.toString(), VariablesCaja.vNumPedVta);

        } catch (Exception e) {
            lanzaError = true;
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumCompPago);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.error("ERROR DE ACTUALIZAR COMPROBANTE IMPRESO : ",e);

            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(e.toString(), VariablesCaja.vNumPedVta);
        }
        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 6: Actualiza Comprobante Impreso:" + (tmpT2 - tmpT1) + " milisegundos");

        // No pudo hacer el UPDATE y Se va Forzar que Salga ERROR
        // dubilluz 21.01.2014
        if (lanzaError) {
            throw new Exception("Error Al Actualizar el Comprobante Impreso");
        }


    }

    private static void actualizaNumComp_Impresora(String pSecImprLocal) throws SQLException {
        long tmpT1, tmpT2;
        tmpT1 = System.currentTimeMillis();
        DBCaja.actualizaNumComp_Impresora(pSecImprLocal);
        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 7: Actualiza Comprobante Impreso:" + (tmpT2 - tmpT1) + " milisegundos");
    }

    private static String obtieneRutaImpresora(String pSecImprLocal) throws SQLException {
        return DBCaja.obtieneRutaImpresoraVenta(pSecImprLocal);
    }

    public static void actualizaEstadoPedido(String pNumPedVta, String pEstPedVta) throws SQLException {
        DBCaja.actualizaEstadoPedido(pNumPedVta, pEstPedVta);
    }

    /**
     * @AUTHOR  JCORTEZ
     * @SINCE  07.08.09
     * */
    private static void actualizarDatosDelivery(String pNumPedVta, String pEstPedVta) throws SQLException {

        DBCaja.actualizaDatosDelivery(pNumPedVta, pEstPedVta, VariablesDelivery.vCodCli,
                                      VariablesDelivery.vNombreCliente, VariablesDelivery.vNumeroTelefonoCabecera,
                                      VariablesDelivery.vDireccion, VariablesDelivery.vNumeroDocumento);
    }

    
    public static boolean verificaEstadoPedido(JDialog pJDialog, String pNumPedVta, String estadoAEvaluar,
                                               Object pObjectFocus) {
        String estadoPed = "";
        estadoPed = obtieneEstadoPedido(pJDialog, pNumPedVta);
        log.debug("Estado de Pedido:" + estadoPed);
        if (estadoAEvaluar.equalsIgnoreCase(estadoPed))
            return true;
        //dubilluz 13.10.2011 bloqueo NO SE DEBE LIBERAR DEBIDO A Q YA EXISTE UN BLOQUEO DE STOCK DE PRODUCTOS.
        //FarmaUtility.liberarTransaccion();
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_COBRADO)) {
            FarmaUtility.showMessage(pJDialog, "El pedido No se encuentra pendiente de cobro.Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_SIN_COMPROBANTE_IMPRESO)) {
            FarmaUtility.showMessage(pJDialog,
                                     "El pedido fue Cobrado pero no imprimio Comprobante(s).\nIngrese a la opcion de Reimpresion de Pedido.",
                                     pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_ANULADO)) {
            FarmaUtility.showMessage(pJDialog, "El pedido se encuentra Anulado. Verifique!!!", pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_PENDIENTE)) {
            FarmaUtility.showMessage(pJDialog, "El pedido se encuentra pendiente de cobro. Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(pJDialog, "No se pudo determinar el estado del pedido. Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        return true;
    }
    
    public static boolean verificaEstadoProforma(JDialog pJDialog, String pNumPedVta, String estadoAEvaluar, Object pObjectFocus, String estadoPedido) {
        String estadoPed = estadoPedido;
        log.debug("Estado de Pedido:" + estadoPed);
        if (estadoAEvaluar.equalsIgnoreCase(estadoPed))
            return true;
        //dubilluz 13.10.2011 bloqueo NO SE DEBE LIBERAR DEBIDO A Q YA EXISTE UN BLOQUEO DE STOCK DE PRODUCTOS.
        //FarmaUtility.liberarTransaccion();
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_COBRADO)) {
            FarmaUtility.showMessage(pJDialog, "El pedido No se encuentra pendiente de cobro.Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_SIN_COMPROBANTE_IMPRESO)) {
            FarmaUtility.showMessage(pJDialog,
                                     "El pedido fue Cobrado pero no imprimio Comprobante(s).\nIngrese a la opcion de Reimpresion de Pedido.",
                                     pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_ANULADO)) {
            FarmaUtility.showMessage(pJDialog, "El pedido se encuentra Anulado. Verifique!!!", pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_PENDIENTE)) {
            FarmaUtility.showMessage(pJDialog, "El pedido se encuentra pendiente de cobro. Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(pJDialog, "No se pudo determinar el estado del pedido. Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        return true;
    }

    public static void obtieneInfoCajero(String pSecMovCaja) {
        ArrayList myArray = new ArrayList();
        try {
            DBCaja.obtenerInfoCajero(myArray, pSecMovCaja);
            if (myArray.size() == 0)
                return;
            VariablesCaja.vNumCajaImpreso = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesCaja.vNumTurnoCajaImpreso = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesCaja.vNomCajeroImpreso = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            VariablesCaja.vApePatCajeroImpreso = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
        } catch (SQLException sql) {
            log.error("",sql);
        }
    }

    public static boolean validaAgrupacionComprobante(JDialog pJDialog, Object pObjectFocus) {
        ArrayList myArray = new ArrayList();
        String secCompPago = "";
        VariablesCaja.vArrayList_SecCompPago = new ArrayList<String>();
        
        VariablesCaja.vListMap_SecCompPago = new ArrayList<HashMap<String,Object>>();
        try {
            DBCaja.obtieneInfoDetalleAgrupacion(myArray);
            if (myArray.size() == 0) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, "No se pudo determinar la agrupacion de Impresion", pObjectFocus);
                return false;
            }
            
            //ERIOS 22.01.2016 Se agrupa por tipo de comprobante
            for (int j = 0; j < myArray.size(); j++) {
                String tipCompPago = ((String)((ArrayList)myArray.get(j)).get(2)).trim();
                
                boolean existe = false;
                for(HashMap<String,Object> temp : VariablesCaja.vListMap_SecCompPago){
                    if(temp.containsValue(tipCompPago)){
                        existe = true;
                        break;
                    }
                }                    
                if(existe){
                    continue;
                }
                
                int contCompPago = 0;
                ArrayList<String> vArrayList_SecCompPago = new ArrayList<String>();
                for (int i = 0; i < myArray.size(); i++) {                    
                    String tmpTipCompPago = ((String)((ArrayList)myArray.get(i)).get(2)).trim();
                    if(tmpTipCompPago.equals(tipCompPago)){
                        contCompPago++;
                        secCompPago = ((String)((ArrayList)myArray.get(i)).get(1)).trim();
                        if (secCompPago.equalsIgnoreCase("")) {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(pJDialog, "Hubo error al obtener el Secuencial del Comprobante.",
                                                     pObjectFocus);
                            return false;
                        }
                        vArrayList_SecCompPago.add(secCompPago);
                    }
                }
                HashMap<String,Object> mapSecCompPago = new HashMap<>();
                mapSecCompPago.put("TIP_COMP_PAGO",tipCompPago);
                mapSecCompPago.put("NUM_SEC_IMP_COMP",contCompPago);
                mapSecCompPago.put("LIST_SEC_COMP",vArrayList_SecCompPago);
                                    
                VariablesCaja.vListMap_SecCompPago.add(mapSecCompPago);
            }
            log.debug("VariablesCaja.vArrayList_SecCompPago : " + VariablesCaja.vArrayList_SecCompPago);
            return true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("",sql);
            FarmaUtility.showMessage(pJDialog, "Error al validar Agrupacion de Comprobante.", pObjectFocus);
            
            return false;
        }
    }

    //procesoAsignacionComprobante

    public static void procesoAsignacionComprobante(JDialog pJDialog, Object pObjectFocus) throws Exception {
        String secImprLocal = "";
        secImprLocal = ConstantsCaja.SEC_IMPR_LOCAL_NOTA_CREDITO;
        log.debug("secImprLocal procesoAsignacionComprobante: " + secImprLocal);

        for (int i = 1; i <= VariablesCaja.vNumSecImpresionComprobantes; i++) {
            VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus);
            if ("".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, "No se pudo determinar el Numero de Comprobante. Verifique!!!",
                                         pObjectFocus);
                return;
            }

            //if(!obtieneDetalleImprComp(pJDialog, String.valueOf(i), pObjectFocus)) return;
            String secCompPago = ((String)(VariablesCaja.vArrayList_SecCompPago.get(i - 1))).trim();
            log.debug("secCompPago : " + secCompPago);
            //if(!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus)) return;
            //VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal);
            actualizaComprobanteImpreso(secCompPago, VariablesVentas.vTip_Comp_Ped, VariablesCaja.vNumCompImprimir);
            actualizaNumComp_Impresora(secImprLocal);
        }
        actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
        //FarmaUtility.aceptarTransaccion();
        //FarmaUtility.showMessage(pJDialog, "Comprobantes Impresos con Exito",pObjectFocus);
    }

    public static void verificaPedidosPendientes(JDialog pJDialog) {
        int cantPedidosPendientes = 0;
        try {
            cantPedidosPendientes = DBCaja.cantidadPedidosPendAnulMasivo();
            if (cantPedidosPendientes == 0)
                return;
            FarmaUtility.showMessage(pJDialog,
                                     "Existen Pedidos Pendientes de cobro con mas de " + FarmaVariables.vMinutosPedidosPendientes +
                                     " minutos.\nEstos pedidos seran anulados.", null);
            //DBCaja.anulaPedidosPendientesMasivo(); --antes - ASOSA, 12.07.2010
            DBCaja.anulaPedidosPendientesMasivo_02(); //ASOSA, 12.07.2010
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("",sql);
        }
    }

    public static void imprimePrueba() throws Exception {
        FarmaPrintService vPrint = new FarmaPrintService(10, VariablesCaja.vRutaImpresora, false);
        //FarmaPrintService vPrint = new FarmaPrintService(10,"C:\\ImpresoraReporte.txt",false);
        if (!vPrint.startPrintService())
            throw new Exception("Error en Impresora. Verifique !!!");

        String pFechaBaseDatos = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
        vPrint.activateCondensed();
        String dia = pFechaBaseDatos.substring(0, 2);
        String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3, 5)));
        String ano = pFechaBaseDatos.substring(6, 10);
        String hora = pFechaBaseDatos.substring(11, 19);

        vPrint.activateCondensed();
        vPrint.printLine("", true);
        vPrint.printLine("LOCAL: " + FarmaVariables.vDescCortaLocal, true);
        vPrint.printLine("FECHA: " + dia + " " + mesLetra + " " + ano + "  " + hora, true);
        vPrint.printLine("IMPRESORA - SERIE: " + VariablesCaja.vTipComp, true);
        vPrint.printLine("USUARIO: " + FarmaVariables.vPatUsu + " " + FarmaVariables.vMatUsu + ", " +
                         FarmaVariables.vNomUsu, true);
        vPrint.printLine("CAJA - TURNO: " + VariablesCaja.vNumCaja + " - " + VariablesCaja.vNumTurnoCaja, true);
        vPrint.printLine(" ", true);
        vPrint.deactivateCondensed();

        vPrint.printCondensed("        PRUEBA DE IMPRESORA        ", true);

        vPrint.endPrintService();
    }

    /**
     * Metodo que procesa la venta de tarjetas virtuales.
     * Este metodo se conectara con el proveedor de servicios a traves de su interfase.
     * Creado x LMesia 12/01/2007
     */
    public static void procesaVentaProductoVirtual(JDialog pDialog, ArrayList vArrayList_InfoProdVirtual) throws SQLException,
                                                                                                Exception {

        colocaVariablesVirtualesBprepaid();
        if (VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
            log.debug("entro a venta de recarga virtual");
            //ERIOS 16.09.2015 Recupera CodComercio y NomComercio
            String strCodComercioCia = FarmaUtility.getValueFieldArrayList(vArrayList_InfoProdVirtual, 0, 12);
            String strNomComercioCia = FarmaUtility.getValueFieldArrayList(vArrayList_InfoProdVirtual, 0, 13);
            //ERIOS 25.05.2016 Determinar el proveedor de recargas
            String strIndGestorRecargas = FarmaUtility.getValueFieldArrayList(vArrayList_InfoProdVirtual, 0, 15);
            String strProveedorRecargas = FarmaUtility.getValueFieldArrayList(vArrayList_InfoProdVirtual, 0, 16);
            
            ventaRecargaVirtualTX(strCodComercioCia, strNomComercioCia, strIndGestorRecargas, strProveedorRecargas);
            //captura el error de conexion cuando los valores son nulos
            //16.11.2007  dubilluz modificado
            if (VariablesVirtual.respuestaTXBean.getCodigoRespuesta() == null) {
                throw new Exception("@"+"Hubo un error con la conexion. Intentarlo mas tarde.");
            }else if (VariablesVirtual.respuestaTXBean.getCodigoRespuesta().equals("666")){
                throw new Exception("@"+VariablesVirtual.respuestaTXBean.getDescripcion());
            }

            colocaInfoRecargaVirtualBprepaid();

            //Mostramos el mensaje de respuesta del proveedor
            //05.12.2007  dubilluz  modificacion
            if (!validaCodigoRespuestaTransaccion()) {
                String strRango="";
                if (VariablesVirtual.vCodigoRespuesta.equals("99")) { //ASOSA - 24/07/2014
                    strRango =  "@" + "Rango permitido para el plan: de S/." + VariablesVirtual.vValorMinimo +
                                        " a S/." + VariablesVirtual.vValorMaximo;
                }
                throw new FarmaRecargaException(//"Error al realizar la transacción con el proveedor." + 
                                                "@" +
                                    VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + " - " +
                                    VariablesVirtual.respuestaTXBean.getDescripcion() +
                                                strRango);
            }

        } else if (VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
            log.debug("entro a venta de tarjeta virtual");
            obtieneInfoRecargaVirtualBprepaid();

            if (!validaHostPuertoProveedor()) {
                limpiaInfoTransaccionTarjVirtuales();
                throw new Exception("Error al obtener host y puerto de Navsat para tarjeta virtual");
            }

            ventaTarjetaVirtualTX();
            colocaInfoTransaccionVirtualBprepaid();
        }

        actualizaInfoPedidoVirtual(pDialog);
    }


    /**
     * Valida el codigo de respuesta
     * @author dubilluz
     * @since  05.12.2007
     */
    private static boolean validaCodigoRespuestaTransaccion() {
        boolean result = false;
        if (VariablesVirtual.vCodigoRespuesta != null) {
            log.debug("VariablesVirtual.vCodigoRespuesta - " + VariablesVirtual.vCodigoRespuesta);
            if (VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL) ||
                VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_COBRA_REVISE_EST_TAR_VIRTUAL))
                result = true;

            log.debug("validaCodigoRespuestaTransaccion result - " + result);
        }
        return result;
    }

    /**
     * Guarda los variables para la comunicacion con el proveedor (Brightstar).
     * @throws SQLException
     * @author ERIOS
     * @since 27.09.2007
     */
    public static void colocaVariablesVirtualesBprepaid() throws SQLException {

        //VariablesVirtual.vCodigoComercio = "MFARMATEST";
        VariablesVirtual.vTipoTarjeta = VariablesCaja.vTipoTarjeta;
        VariablesVirtual.vMonto = VariablesCaja.vPrecioProdVirtual;
        VariablesVirtual.vNumTerminal = FarmaUtility.completeWithSymbol(FarmaVariables.vCodLocal, 13, " ", "D");
        //VariablesVirtual.vNumSerie = "LIM";
        //VariablesVirtual.vNumTrace = obtieneNumeroTraceBprepaid();
        VariablesVirtual.vNumeroCelular = VariablesCaja.vNumeroCelular;
        VariablesVirtual.vCodigoProv = FarmaUtility.completeWithSymbol(VariablesCaja.vCodigoProv, 10, " ", "D");
        VariablesVirtual.vNumTraceOriginal = VariablesCaja.vNumeroTraceOriginal;
        VariablesVirtual.vCodAprobacionOriginal = VariablesCaja.vCodAprobacionOriginal;
        VariablesVirtual.vFechaTX = VariablesCaja.vFechaTX;
        VariablesVirtual.vHoraTX = VariablesCaja.vHoraTX;
        //INI ASOSA - 13/07/2014
        VariablesVirtual.vTipoRcd = VariablesCaja.vTipoRcd;
        VariablesVirtual.vCodTipoProducto = VariablesCaja.vCodTipoProducto;
        //FIN ASOSA - 13/07/2014

        //log.debug("VariablesVirtual.vCodigoComercio : " + VariablesVirtual.vCodigoComercio);
        log.debug("VariablesVirtual.vTipoTarjeta : " + VariablesVirtual.vTipoTarjeta);
        log.debug("VariablesVirtual.vMonto : " + VariablesVirtual.vMonto);
        log.debug("VariablesVirtual.vNumTerminal : " + VariablesVirtual.vNumTerminal);
        //log.debug("VariablesVirtual.vNumSerie : " + VariablesVirtual.vNumSerie);
        log.debug("VariablesVirtual.vNumTrace : " + VariablesVirtual.vNumTrace);
        log.debug("VariablesVirtual.vNumeroCelular : " + VariablesVirtual.vNumeroCelular);
        log.debug("VariablesVirtual.vCodigoProv : " + VariablesVirtual.vCodigoProv);
        log.debug("VariablesVirtual.vNumTraceOriginal : " + VariablesVirtual.vNumTraceOriginal);
        log.debug("VariablesVirtual.vCodAprobacionOriginal : " + VariablesVirtual.vCodAprobacionOriginal);
        log.debug("VariablesVirtual.vFechaTX : " + VariablesVirtual.vFechaTX);
        log.debug("VariablesVirtual.vHoraTX : " + VariablesVirtual.vHoraTX);
        log.debug("VariablesVirtual.vTipoRcd : " + VariablesVirtual.vTipoRcd);
        log.debug("VariablesVirtual.vCodTipoProducto : " + VariablesVirtual.vCodTipoProducto);
    }

    /**
     * Guarda la respuesta desde el TXBean
     * @throws Exception
     * @author ERIOS
     * @since 27.09.2007
     */
    private static void colocaInfoTransaccionVirtualBprepaid() throws Exception {
        VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta();
        VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaTXBean.getDescripcion();
        VariablesVirtual.vNumTrace = VariablesVirtual.respuestaTXBean.getNumeroTrace();
        VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaTXBean.getCodigoAprobacion();
        VariablesVirtual.vNumeroTarjeta = VariablesVirtual.respuestaTXBean.getNumeroTarjeta();
        VariablesVirtual.vNumeroPin = VariablesVirtual.respuestaTXBean.getCodigoPIN();
        VariablesVirtual.vFechaTX = VariablesVirtual.respuestaTXBean.getFechaTX();
        VariablesVirtual.vHoraTX = VariablesVirtual.respuestaTXBean.getHoraTX();
    }

    /**
     * Guarda la respuesta desde el TXBean de Recarga.
     * @author ERIOS
     * @since 28.09.2007
     * @since 02.10.2015 ERIOS Control para valores nulos.
     * @throws Exception
     */
    private static void colocaInfoRecargaVirtualBprepaid() throws Exception {
        VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta();
        VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaTXBean.getDescripcion();
        String numTrace = VariablesVirtual.respuestaTXBean.getNumeroTrace();
        VariablesVirtual.vNumTrace = (numTrace == null)?"":numTrace;
        String codigoAprobacion = VariablesVirtual.respuestaTXBean.getCodigoAprobacion();                                                         
        VariablesVirtual.vCodigoAprobacion = (codigoAprobacion == null)?"":codigoAprobacion;
        VariablesVirtual.vNumeroTarjeta = "";
        VariablesVirtual.vNumeroPin = "";
        VariablesVirtual.vFechaTX = VariablesVirtual.respuestaTXBean.getFechaTX();
        VariablesVirtual.vHoraTX = VariablesVirtual.respuestaTXBean.getHoraTX();
        VariablesVirtual.vDatosImprimir = VariablesVirtual.respuestaTXBean.getDatosImprimir();
        
    }

    public static void actualizaInfoPedidoVirtual(JDialog pDialog) throws Exception {
        try {
            DBCaja.actualizaInfoPedidoVirtual();
        } catch (Exception ex) {
            log.error("", ex);
            throw new Exception("Hubo un error con la conexion. Intentarlo mas tarde." + ex);
        }

    }

    /**
     * Recupera los valores de conexion.
     * @author ERIOS
     * @since 27.09.2007
     */
    private static void obtieneInfoRecargaVirtualBprepaid() {
        try {
            ArrayList infoTXRecarga = DBCaja.obtieneDatostRecargaBprepaid();
            log.debug("infoTXRecarga : " + infoTXRecarga);
            VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoTXRecarga, 0, 0);
            VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoTXRecarga, 1, 0);
            //Identificador de canal provisto por Bprepaid
            String vIdentificador = FarmaUtility.getValueFieldArrayList(infoTXRecarga, 2, 0);
            VariablesVirtual.vCodigoComercio = FarmaUtility.completeWithSymbol(vIdentificador, 10, " ", "D");
            //Provincia (Terminal State) del local.
            VariablesVirtual.vNumSerie = FarmaUtility.getValueFieldArrayList(infoTXRecarga, 3, 0);

            log.debug("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
            log.debug("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
            log.debug("VariablesVirtual.vCodigoComercio : " + VariablesVirtual.vCodigoComercio);
            log.debug("VariablesVirtual.vNumSerie : " + VariablesVirtual.vNumSerie);
        } catch (SQLException sqlException) {
            VariablesVirtual.vCodigoComercio = "";
            VariablesVirtual.vNumSerie = "";
            VariablesVirtual.vIPHost = "";
            VariablesVirtual.vPuertoHost = "";
            log.error("",sqlException);
        }
    }

    private static boolean validaHostPuertoProveedor() {
        boolean result = true;
        if (VariablesVirtual.vIPHost.trim().equalsIgnoreCase("") ||
            VariablesVirtual.vPuertoHost.trim().equalsIgnoreCase(""))
            result = false;
        return result;
    }

    private static void impresionInfoVirtual(FarmaPrintService pVPrint, FarmaPrintServiceTicket pVPrintArchivo,
                                             String pTipoProdVirtual, String pCodigoAprobacion, String pNumeroTarjeta,
                                             String pNumeroPin, String pNumeroTelefono, String pMonto,
                                             String pNumPedido, String pCodProd) {
        if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
            pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
            pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion,
                                     true);
            pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Tarjeta  : " + pNumeroTarjeta, true);
            pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion,
                                     true);
            pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Pin      : " + pNumeroPin, true);
            pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion,
                                     true);


        } else if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
            /*pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Telefono : " + pNumeroTelefono, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Monto Recarga   : " + pMonto, true);*/
            /**
       * Imprime los datos de Impresion de Recarga
       * 02.11.2007 dubilluz creacion
       */
            obtieneDescImpresion(pNumPedido, pCodProd);

            ArrayList array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
            for (int i = 0; i < array.size(); i++) {
                log.debug("" + array.get(i));
                if (((String)array.get(i)).trim().length() > 0) {
                    pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + ((String)(array.get(i))).trim(), true);
                    pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + ((String)(array.get(i))).trim(), true);
                }
            }

        }
    }

    /**
     * Carga los datos del producto Virtual
     * @author ERIOS
     * @since 13.06.2013
     * @param pVPrint
     * @param pTipoProdVirtual
     * @param pCodigoAprobacion
     * @param pNumeroTarjeta
     * @param pNumeroPin
     * @param pNumeroTelefono
     * @param pMonto
     * @param pNumPedido
     * @param pCodProd
     */
    public static void impresionInfoVirtualTicket(ArrayList<String> pVPrint, String pTipoProdVirtual,
                                                  String pCodigoAprobacion, String pNumeroTarjeta, String pNumeroPin,
                                                  String pNumeroTelefono, String pMonto, String pNumPedido,
                                                  String pCodProd) {

        if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion);
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Numero Tarjeta  : " + pNumeroTarjeta);
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Numero Pin      : " + pNumeroPin);
        } else if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA) &&
                VariablesVentas.vDniRimac.equals("")    //ASOSA - 16/01/2015 - RIMAC
                ) {
            obtieneDescImpresion(pNumPedido, pCodProd);
            ArrayList array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
            for (int i = 0; i < array.size(); i++) {
                if (((String)array.get(i)).trim().length() > 0) {
                    pVPrint.add(FarmaPRNUtility.llenarBlancos(2) + ((String)(array.get(i))).trim());
                }
            }
        }
    }

    /**
     * Carga los datos del producto Virtual
     * @author ERIOS
     * @since 13.06.2014
     * @param pVPrint
     * @param pTipoProdVirtual
     * @param pCodigoAprobacion
     * @param pNumeroTarjeta
     * @param pNumeroPin
     * @param pNumeroTelefono
     * @param pMonto
     * @param pNumPedido
     * @param pCodProd
     */
    public static void impresionInfoVirtual(ArrayList<String> pVPrint, String pTipoProdVirtual,
                                            String pCodigoAprobacion, String pNumeroTarjeta, String pNumeroPin,
                                            String pNumeroTelefono, String pMonto, String pNumPedido,
                                            String pCodProd) {
        if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion);
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Numero Tarjeta  : " + pNumeroTarjeta);
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Numero Pin      : " + pNumeroPin);
        } else if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
            obtieneDescImpresion(pNumPedido, pCodProd);
            ArrayList array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
            for (int i = 0; i < array.size(); i++) {
                if (((String)array.get(i)).trim().length() > 0) {
                    pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + ((String)(array.get(i))).trim());
                }
            }
        }
    }

    private static void limpiaInfoTransaccionTarjVirtuales() throws Exception {
        VariablesVirtual.vCodigoRespuesta = "";
        VariablesVirtual.vDescripcionRespuesta = "";
        VariablesVirtual.vNumTrace = "";
        VariablesVirtual.vCodigoAprobacion = "";
        VariablesVirtual.vNumeroTarjeta = "";
        VariablesVirtual.vNumeroPin = "";
        VariablesVirtual.vFechaTX = "";
        VariablesVirtual.vHoraTX = "";
    }


    public static void actualizaInfoPedidoVirtualAnulado(String pNumPedOrigen) throws SQLException {
        DBCaja.actualizaInfoPedidoAnuladoVirtual(pNumPedOrigen);
    }

    /**
     * Coloca la descripcion de la impresion que el proveedor requiere en la boleta
     * @author dubilluz
     * @since  02.11.2007
     */
    public static void obtieneDescImpresion(String pNumped, String pCodProd) {
        try {
            DBCaja.obtieneInfImpresionRecarga(VariablesVirtual.vArrayList_InfoProvRecarga, pNumped, pCodProd);
            log.debug("xxxx : " + VariablesVirtual.vArrayList_InfoProvRecarga);
        } catch (SQLException e) {
            log.error("", e);
        }
    }
    
    /**
     * obtieneDescImprRimac
     * @author ASOSA
     * @since 12/01/2015
     * @type RIMAC
     * @param pNumped
     * @param pCodProd
     */
    public static void obtieneDescImprRimac(String pNumped, String pCodProd) {
        try {
            DBCaja.obtieneInfImpresionRimac(VariablesVirtual.vArrayList_InfoProvRecarga, pNumped, pCodProd);
            log.debug("xxxx : " + VariablesVirtual.vArrayList_InfoProvRecarga);
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    /**
     * Se encarga de realizar la venta de una Tarjeta Virtual.
     * @throws Exception
     * @author ERIOS
     * @since 14.12.2007
     */
    private static void ventaTarjetaVirtualTX() throws Exception {

        /*proveedorTarjetaVirtual = MetodosTXFactory.getMetodosTXVirtual(MetodosTXFactory.METODO_BPCLIENTWS);
        VariablesVirtual.respuestaTXBean =
                proveedorTarjetaVirtual.VentaTarjetaVirtual(VariablesVirtual.vCodigoComercio,
                                                            VariablesVirtual.vTipoTarjeta, VariablesVirtual.vMonto,
                                                            VariablesVirtual.vNumTerminal, VariablesVirtual.vNumSerie,
                                                            VariablesVirtual.vNumTrace, VariablesVirtual.vIPHost,
                                                            VariablesVirtual.vPuertoHost);
        log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
        log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
        log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
        log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
        log.debug("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
        log.debug("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
        log.debug("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
        log.debug("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());*/
    }

    /**
     * Se encarga de realizar la venta de una Recarga Virtual.
     * @throws Exception
     */
    private static void ventaRecargaVirtualTX(String pCodComercioCia,
                                                     String pNomComercioCia,
                                              String pIndGestorRecargas,
                                              String pProveedorRecargas) throws Exception {

        //ERIOS 25.05.2016 Determina si va por Gestor o Demonio. Falta implementar en GTX.
        boolean recGestor = pIndGestorRecargas.equals("S");
        
        UtilityRecargaVirtual utilRecaVirtual = new UtilityRecargaVirtual();
        if(recGestor && "S".equals(DlgProcesar.getIndGestorTx(false))){
            //AW 27.05.2015 Start Realizar recarga mediante servicios web
            utilRecaVirtual.gestionaSolicitudRecargaWS(VariablesVirtual.vNumeroCelular, VariablesVirtual.vCodigoProv,
                                                     VariablesVirtual.vMonto, VariablesVirtual.vNumTerminal,
                                                     VariablesVirtual.vNumSerie, FarmaVariables.vCodLocal,
                                                     VariablesCaja.vNumPedVta, pCodComercioCia, pNomComercioCia);        
            //AW 27.05.2015 End Realizar recarga mediante servicios web
        }else{
            utilRecaVirtual.gestionaSolicitudRecarga(VariablesVirtual.vNumeroCelular, VariablesVirtual.vCodigoProv,
                    VariablesVirtual.vMonto, pProveedorRecargas,
                    VariablesVirtual.vNumSerie, FarmaVariables.vCodLocal,
                    VariablesCaja.vNumPedVta);
        }
        log.info("acaba metodo UtilityRecargaVirtual.gestionaSolicitudRecarga");
    }

    /**
     * Anular recarga virtual
     * @author ASOSA
     * @since 09/07/2014
     * @throws Exception
     */
    private static void anularRecargaVirtualTX() throws Exception {

        UtilityRecargaVirtual utilRecaVirtual = new UtilityRecargaVirtual();
        if("S".equals(DlgProcesar.getIndGestorTx())){
            //AW 27.05.2015 Start Anular recarga mediante servicios web
            utilRecaVirtual.gestionaSolAnulRecargaWS(VariablesVirtual.vNumeroCelular, VariablesVirtual.vCodigoProv,
                                                   VariablesVirtual.vMonto, VariablesVirtual.vNumTerminal,
                                                   VariablesVirtual.vNumSerie, FarmaVariables.vCodLocal,
                                                   VariablesCaja.vNumPedVta_Anul);
            //AW 27.05.2015 End Anular recarga mediante servicios web
        }else{
            utilRecaVirtual.gestionaSolAnulRecarga(VariablesVirtual.vNumeroCelular, VariablesVirtual.vCodigoProv,
                                                   VariablesVirtual.vMonto, VariablesVirtual.vNumTerminal,
                                                   VariablesVirtual.vNumSerie, FarmaVariables.vCodLocal,
                                                   VariablesCaja.vNumPedVta_Anul);
        }
        log.info("acaba metodo UtilityRecargaVirtual.gestionaSolicitudRecarga");
    }

    /**
     * Se evalua la fecha de movimiento de la caja.
     * @param pDialogo
     * @param pObject
     * @return
     * @author ERIOS
     * @since 07.01.2007
     */
    public static boolean validaFechaMovimientoCaja(JDialog pDialogo, Object pObject) {
        try {
            String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String fechaMovCaja = DBCaja.obtieneFechaMovCaja();
            if (fechaMovCaja.trim().length() > 0 &&
                !(fechaMovCaja.substring(0, 5).equalsIgnoreCase(fechaSistema.substring(0, 5)))) {
                FarmaUtility.showMessage(pDialogo, "Debe CERRAR su caja para empezar un NUEVO DIA.\n" +
                        "La Fecha actual no coincide con la Fecha de Apertura de Caja.", pObject);
                return false;
            }
            return true;
        } catch (SQLException sqlException) {
            log.error("",sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener la fecha de movimiento de caja.", pObject);
            return false;
        }
    }

    /**
     * Se imprime los consejos asociados al pedido.
     * @author ERIOS
     * @since 09.05.2008
     */
    private static boolean imprimeConsejos(JDialog pDialogo) {
        boolean consejo = false;

        try {
            String htmlConsejo = DBCaja.obtieneConsejos(VariablesCaja.vNumPedVta, FarmaVariables.vIPBD);

            if (!htmlConsejo.equals("N")) {
                PrintConsejo.imprimirHtml(htmlConsejo, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                FarmaUtility.aceptarTransaccion();
                consejo = true;
            }
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los consejos.", null);
            enviaErrorCorreoPorDB(sqlException.toString(), "\nError al imprimir CONSEJO");

        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            enviaErrorCorreoPorDB(e.toString(), "\nError al imprimir CONSEJO");

        }
        return consejo;
    }

    /**
     * Se imprime los cierto datos de comanda al cobrar algun pedido de delivery
     * @author Jorge Cortez Alvarez
     * @since 13.06.2008
     */
    //MARCO FAJARDO 08/04/09 MEJORA TIEMPO DE RESPUESTA DE IMPRESORA TERMICA
    public static void imprimeDatosDelivery(JDialog pDialogo, String NumPed) {

        try {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
            // String pTipoImp = DBCaja.obtieneTipoImprConsejo();JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
            log.info("nombre de impresora" + VariablesPtoVenta.vImpresoraActual.getName());
            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :" + vIndImpre);
            if (!vIndImpre.equals("N")) {
                //String htmlDelivery = DBCaja.obtieneDatosDelivery(NumPed,FarmaVariables.vIPBD);
                List listComanda = DBCaja.obtieneDatosDeliveryComanda(NumPed);
                if (listComanda.size() > 0) {

                    UtilityImpCompElectronico impresion = new UtilityImpCompElectronico();
                    boolean rest = impresion.impresionTermica(listComanda, null);
                        //impresion.impresionDocumentoEnTermica(listComanda, false, null, false);

                    /*UtilityImpresionDelivery impresora = new UtilityImpresionDelivery();//);
                    boolean rest = impresora.impresionComanda(listComanda,VariablesPtoVenta.vTipoImpTermicaxIp, VariablesPtoVenta.vImpresoraActual.getName() );
                    */
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(pDialogo, "Se imprimo comanda delivery.", null);
                }
                //log.debug(datos);
                //if(htmlDelivery.trim().length()>0)
                //PrintConsejo.imprimirHtml(htmlDelivery,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                //break;
            }

        } catch (Exception sqlException) {
            log.error("",sqlException);
            FarmaUtility.showMessage(pDialogo, "Error en impresión de Comanda Delivery:\n" +
                    sqlException.getMessage(), null);
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sqlException.toString(), NumPed);
        }


    }

    /**
     * Metodo que verifica si el pedido tiene cupones para imprimir
     * @author Diego Ubilluz Carrillo
     * @since  03.07.2008
     * @param pDialogo
     */
    public static int imprimeCupones(JDialog pDialogo) {
        ArrayList listaCupones = new ArrayList();
        int cant_cupones_impresos = 0;
        try {
            DBCaja.obtieneCuponesPedidoImpr(listaCupones, VariablesCaja.vNumPedVta);
            log.debug("Lista cupones .... " + listaCupones);

            if (listaCupones.size() > 0) {
                String cod_cupon = "";
                for (int i = 0; i < listaCupones.size(); i++) {
                    cod_cupon = ((ArrayList)(listaCupones.get(i))).get(0).toString();
                    if (cod_cupon.trim().length() > 0)
                        cant_cupones_impresos = cant_cupones_impresos + imprimeCupon(pDialogo, cod_cupon);
                }
            }
        } catch (SQLException sqlException) {
            log.error("",sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al verificar si tiene cupones el pedido.\n" +
                    sqlException.getMessage(), null);
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sqlException.toString(), null);
        }

        /*if(cant_cupones_impresos>0){
        FarmaUtility.showMessage(pDialogo,
                                "Favor de recoger "+cant_cupones_impresos+" cupones.",
                                 null);
    }*/

        return cant_cupones_impresos;

    }


    /**
     * Se imprime cupones que tenga el pedido cobrado
     * @author Diego Ubilluz
     * @since 03.07.2008
     */
    private static int imprimeCupon(JDialog pDialogo, String vCodeCupon) {
        int cant_cupones_impresos = 0;

        try {
            
            String vCupon = DBCaja.obtieneImprCupon(VariablesCaja.vNumPedVta, FarmaVariables.vIPBD, vCodeCupon);
            if (!vCupon.equals("N")) {
                
                imprimeCuponTermico(vCupon,vCodeCupon);
                cant_cupones_impresos++;
            }
            
        } catch (Exception sqlException) {
            log.error("",sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los consejos.", null);
        }

        return cant_cupones_impresos;
    }

    /**
     * Metodo encargado de anular los cupones en Matriz si estos no fueron
     * enviados aun por el JOB ó crea los cupones con el estado Anulado
     * si estos aun no fueron enviados a Matriz
     * @param pNumPed
     * @author Diego Ubilluz
     * @since  21.08.2008
     */
    public static void anulaCuponesPedido(String pNumPed, JDialog pDialogos, Object pObject) {
        log.debug("Anular Cupones en Matriz");
        log.debug("**INICIO**");
        VariablesCaja.vIndLinea = "";
        ArrayList listaCuponesAnulados = new ArrayList();
        int COL_COD_CUPON = 0;
        int COL_COD_FECHA_INI = 1;
        int COL_COD_FECHA_FIN = 2;
        String vEstCuponMatriz = "";
        String vCodCupon = "";
        String vRetorno = "";
        String vFechIni = "";
        String vFechFin = "";
        String indMultiUso = "";
        boolean vIndModify = false;
        try {

            DBCaja.getcuponesPedido(pNumPed, FarmaConstants.INDICADOR_N, listaCuponesAnulados,
                                    ConstantsCaja.CONSULTA_CUPONES_ANUL_SIN_PROC_MATRIZ);
            log.debug("**listaCuponesAnulados00**" + listaCuponesAnulados);
            if (listaCuponesAnulados.size() > 0) {
                String vValor = "";
                vValor = FarmaUtility.getValueFieldArrayList(listaCuponesAnulados, 0, COL_COD_CUPON);

                if (!vValor.equalsIgnoreCase("NULO")) {
                    VariablesCaja.vIndLinea =
                            FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);


                    //-- Si no hay linea se cierra la conexion con Matriz
                    if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                        log.debug("No existe linea se cerrara la conexion ...");
                        FarmaConnectionRemoto.closeConnection();
                        VariablesCaja.vIndLinea = "";
                    } else {
                        //--Si hay conexion se procedera anular los cupones en Matriz
                        //--Y crearlos si no existe pero con estado Anulado
                        for (int i = 0; i < listaCuponesAnulados.size(); i++) {
                            vCodCupon = FarmaUtility.getValueFieldArrayList(listaCuponesAnulados, i, COL_COD_CUPON);
                            vFechIni = FarmaUtility.getValueFieldArrayList(listaCuponesAnulados, i, COL_COD_FECHA_INI);

                            vFechFin = FarmaUtility.getValueFieldArrayList(listaCuponesAnulados, i, COL_COD_FECHA_FIN);

                            indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPed, vCodCupon).trim();

                            log.debug("**indMultiUso**" + indMultiUso);
                            if (indMultiUso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                                vIndModify = true;
                                vEstCuponMatriz =
                                        DBCaja.getEstadoCuponEnMatriz(vCodCupon, FarmaConstants.INDICADOR_S).trim();

                                log.debug("vEstCuponMatriz " + vEstCuponMatriz);
                                //--Si valor de retorno es "0" es porque el cupon
                                //  no existe asi que se creara en Matriz
                                if (vEstCuponMatriz.equalsIgnoreCase("0")) {
                                    vRetorno =
                                            DBCaja.grabaCuponEnMatriz(vCodCupon, vFechIni, vFechFin, FarmaConstants.INDICADOR_N).trim();
                                }
                                vRetorno =
                                        DBCaja.actualizaEstadoCuponEnMatriz(vCodCupon, FarmaConstants.INDICADOR_N, FarmaConstants.INDICADOR_N).trim();

                                //--Si la actualizacion se realizo con exito se actualiza
                                //  en el local que el cupon ya se proceso en Matriz
                                if (vRetorno.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                    DBCaja.actualizaCuponGeneral(vCodCupon.trim(),
                                                                 ConstantsCaja.CONSULTA_ACTUALIZA_MATRIZ);
                                }
                            }

                        }
                        //--fin de FOR
                        //--Se acepta la transaccion en la Conexion a Matriz y Local
                        if (vIndModify) {
                            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                  FarmaConstants.INDICADOR_N);
                            FarmaUtility.aceptarTransaccion();
                        }
                    }
                }

            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
            log.error("", e);
            FarmaUtility.showMessage(pDialogos, "Error al momento de anular cupones en Matriz.\n" +
                    e.getMessage(), pObject);
        } finally {
            //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
            if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                log.debug("Se cierrara la conexion..");
                FarmaConnectionRemoto.closeConnection();
            }
            VariablesCaja.vIndLinea = "";
        }

        log.debug("**FIN**");
    }

    /**
     * Retorna el numero de cupones emitidos
     * @author DUbilluz
     * @param pNumPed
     * @return
     */
    public static int getCuponesEmitidosUsados(String pNumPed, JDialog pDialogo, Object pObject) {
        String vCodCupon = "";
        int vNumCuponesEmitidos = 0;
        ArrayList listCuponesEmitidos = new ArrayList();
        try {
            //-- inicio validacion cupones
            //Se consulta para obtener los cupones usados en el pedido

            DBCaja.getcuponesPedido(pNumPed, FarmaConstants.INDICADOR_N, listCuponesEmitidos,
                                    ConstantsCaja.CONSULTA_CUPONES_EMITIDOS_USADOS);

            if (listCuponesEmitidos.size() > 0) {

                String vValor = "";
                vValor = FarmaUtility.getValueFieldArrayList(listCuponesEmitidos, 0, 0);
                if (vValor.equalsIgnoreCase("NULO")) {
                    return 0;
                }
                vNumCuponesEmitidos = listCuponesEmitidos.size();
            }

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(pDialogo, "Error al obtener cupones emitidos usados.\n" +
                    e.getMessage(), pObject);
        }

        return vNumCuponesEmitidos;
    }

    /**
     *
     * @param pSecNumIni
     * @param pSecNumFin
     * @return
     */
    public static boolean isExistsCompProcesoSAP(String pSecNumIni, String pSecNumFin) {
        boolean pResultado = false;
        String pCadena = "";
        try {
            pCadena = DBCaja.isExistCompProcesoSAP(pSecNumIni, pSecNumFin);
            if (pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pResultado = true;
            else
                pResultado = false;

        } catch (SQLException e) {
            log.error("", e);
        }

        return pResultado;
    }

    /**
     * @author dubilluz
     * @param secIni
     * @param secFin
     * @return
     */
    public static boolean pValidaComprobantesProcesoSAP(String secIni, String secFin, Object pDialogo, Object pObjeto,
                                                        Frame pFrame) {


        if (isExistsCompProcesoSAP(secIni.trim(), secFin.trim())) {
            FarmaUtility.showMessage((JDialog)pDialogo, "Atención:\n" +
                    "No podrá corregir los correlativos de los comprobantes\n" +
                    "porque alguno de ellos ya han sido transferidos a contabilidad.\n" +
                    "Debe comunicarse con su contador para que justifique\n" +
                    "el motivo para regularizar los comprobantes.", pObjeto);

            if (!cargaLoginOper(pFrame, pDialogo)) {
                return false;
            }
        }

        return true;
    }

    private static boolean cargaLoginOper(Frame pFrame, Object pDialog) {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(pFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage((JDialog)pDialog,
                                     "Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(), null);
        }

        return FarmaVariables.vAceptar;
    }

    /**
     * Revisa si el pedido es de Delivery para enviar un alerta de Anulacion     *
     * @author Dubilluz
     * @since  26.11.2008
     * @param pCadena
     */
    public static void alertaPedidoDelivery(String pCadena) {
        if (!pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            String pCodCia = "", pCodLocalDel = "", pNumPedDel = "";
            String[] pDatosDel = pCadena.trim().split("%");
            if (pDatosDel.length == 5) {
                pCodCia = pDatosDel[0].trim();
                pCodLocalDel = pDatosDel[1].trim();
                pNumPedDel = pDatosDel[2].trim();

                try {
                    DBCaja.enviaAlertaDelivery(pCodCia, pCodLocalDel, pNumPedDel);
                } catch (SQLException e) {
                    log.error("",e);
                }
            }

        }
    }

    /**
     * Validar si existen comprobantes desfasados
     * @author Dubilluz
     * @since  27.11.2008
     * @param pFechaDia
     * @return
     */
    public static boolean validaCompDesfase(String pFechaDia) {
        String pRes = "";
        try {
            pRes = DBCaja.validaCompDesfase(pFechaDia).trim();
        } catch (SQLException sql) {
            log.error("", sql);
            pRes = "N";
        }

        if (pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        return false;
    }

    /**
     *
     * @author dubilluz
     * @since  28.11.2008
     * @return
     */
    public static boolean validaDelPendSinReg(String pFechaDia) {
        String pRes = "";
        try {
            pRes = DBCaja.validaDelPendSinReg(pFechaDia).trim();
        } catch (SQLException sql) {
            log.error("", sql);
            pRes = "N";
        }

        if (pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        return false;
    }

    public static boolean validaAnulPedSinReg(String pFechaDia) {
        String pRes = "";
        try {
            pRes = DBCaja.validaAnulPeddSinReg(pFechaDia).trim();
        } catch (SQLException sql) {
            log.error("", sql);
            pRes = "N";
        }

        if (pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        return false;
    }

    /**
     *
     * @author dubilluz
     * @since  28.11.2008
     * @return
     */
    public static boolean validaRegPedManual(String pFechaDia) {
        String pRes = "";
        try {
            pRes = DBCaja.validaPedidosManualesSinReg(pFechaDia).trim();
        } catch (SQLException sql) {
            log.error("", sql);
            pRes = "N";
        }

        if (pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        return false;
    }

    /**
     * Prueba Impresora Termica
     * @author Diego Ubilluz
     * @since  01.12.2008
     */
    public static void pruebaImpresoraTermica(JDialog pDialogo, Object pObject) {
        
        numeroCorrel++;
        String numAux = "000" + numeroCorrel;
        String pCodCupon = "9999999999" + numAux.substring(numAux.length() - 3, numAux.length());
        
        try {        
            
            pruebaImpresoraTermica(pDialogo, pObject, VariablesPtoVenta.vImpresoraActual,
                                               VariablesPtoVenta.vTipoImpTermicaxIp, "");
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al realizar prueba de impresion.", pObject);

        }

    }
    
    private static void pruebaImpresoraTermica(JDialog pDialogo, Object pObject, PrintService impresora,
                                                     String pTipo, String pNombreImpTermica) throws SQLException {
        String vCupon = DBCaja.pruebaImpresoraTermica("");

        PrintConsejo.imprimirTermicoCode39(pTipo, pNombreImpTermica);
        PrintConsejo.imprimirHtml(vCupon, impresora, pTipo);
        FarmaUtility.showMessage(pDialogo, "Se realizó la prueba de impresión, recoja la impresión.", pObject);
    }

    public static void activaCuponesMatriz(String pNumPed, JDialog pDialogos, Object pObject) {
        log.debug("activa cupones usados en Matriz");
        log.debug("**INICIO**");
        VariablesCaja.vIndLinea = "";
        ArrayList listaCuponesUsados = new ArrayList();
        int COL_COD_CUPON = 0;
        try {
            VariablesCaja.vIndLinea =
                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);

            if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {


                DBCaja.getcuponesUsadosPedido(pNumPed, listaCuponesUsados);
                log.debug("**listaCuponesUsados**" + listaCuponesUsados);
                if (listaCuponesUsados.size() > 0) {
                    String vValor = "";
                    for (int i = 0; i < listaCuponesUsados.size(); i++) {
                        vValor = FarmaUtility.getValueFieldArrayList(listaCuponesUsados, i, COL_COD_CUPON);

                        DBCaja.activaCuponenMatriz(vValor.trim(), FarmaConstants.INDICADOR_N);
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_N);
                    }

                }
            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
            log.error("", e);
            FarmaUtility.showMessage(pDialogos, "Error al momento de activar cupones en Matriz.\n" +
                    e.getMessage(), pObject);
        } finally {
            //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
            if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                log.debug("Se cierrara la conexion..");
                FarmaConnectionRemoto.closeConnection();
            }
            VariablesCaja.vIndLinea = "";
        }

        log.debug("**FIN**");
    }

    /**
     * obtener DNI del cliente si se trata de una venta que acumula ventas
     * @author JCALLO
     * @param  pNumPed
     * @return
     */
    public static String obtenerDniPedidoAcumulaVenta(String pNumPed) {
        log.debug("ver si el pedido acumula ventas");
        log.debug("**INICIO**");

        String Dni = "";

        try {
            Dni = DBCaja.getDniPedidoAcumulaVenta(pNumPed);
            return Dni.trim();
        } catch (SQLException e) {
            log.error("", e);
            return "";
        }
    }

    /**
     * Analiza si el pedido es de camapana acumulada
     * y revertira todo o confirmara segun sea el caso
     * @author dubilluz
     * @since  19.12.2008
     * @param  pNumPed
     * @param  pDialogos
     * @param  pObject
     */
    public static boolean realizaAccionCampanaAcumulada(String pIndLinea, String pNumPed, JDialog pDialogos,
                                                        String pAccion, Object pObject, String pIndEliminaRespaldo) {
        String sDNI = "";
        String existeRegalo = "";
        try {
            sDNI = DBCaja.getDniFidPedidoCampana(pNumPed).trim();
            log.debug("DNI realizaAccionCAmpanaAcu: " + sDNI);
            if (sDNI.length() > 0) {
                existeRegalo = DBCaja.getExistRegaloCampanaAcumulada(sDNI, pNumPed);
                log.debug("Existe REGALO: " + existeRegalo);
                /* //Se comento para evitar insertOrigenMatriz e insertCanjMatriz
                 * //que se encuentra dentro de enviaRegaloMatriz. Todo el Proceso
                 * //de acumulado se hace en local.
                if(existeRegalo.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                {
                    //Si no hay linea no deja cobrar
                    if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                    {
                        if(pAccion.trim().equalsIgnoreCase(ConstantsCaja.ACCION_COBRO))
                            return false;
                    }
                    else
                    if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                        if(pAccion.trim().equalsIgnoreCase(ConstantsCaja.ACCION_COBRO))
                           enviaRegaloMatriz(sDNI,pNumPed);
                }
                */
                //JMIRANDA 17/07/09
                DBCaja.analizaCanjeLocal(sDNI, pNumPed, pAccion, pIndEliminaRespaldo);
                /* if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                {
                DBCaja.analizaCanjeMatriz(sDNI,pNumPed,pAccion);
                }
                */
                //JMIRANDA 16/07/09
                DBCaja.analizaCanjeMatriz(sDNI, pNumPed, pAccion);
                log.debug("TRUE 1 ANALIZACANJEMATRIZ");
                return true;

            } else
                log.debug("TRUE 2 ANALIZACANJEMATRIZ");
            return true;

        } catch (SQLException e) {
            log.error("", e);
            log.debug("Envia error ANALIZACANJEMATRIZ");
            return false;

        }
    }

    /**
     * Se imprime el las campañas acumuladas del cliente
     * @author JAVIER CALLO QUISPE
     * @since 12.10.2008
     */
    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    private static void imprimeUnidRestCampXCliente(JDialog pDialogo, String pDni, String pNumPedVta) {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        //if(servicio != null)
        //{
        try {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();

            //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP

            //for (int i = 0; i < servicio.length; i++)
            //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();

            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

            StringBuffer html = new StringBuffer("");

            ArrayList listaMatriz = new ArrayList();
            //obteniendo la suma de las unidades acumuladas en las campañas
            //en la compra
            DBCaja.getListCampRestPremioXCliente(listaMatriz, pDni, pNumPedVta);

            //obteniendo los premios que gano en el pedido
            ArrayList listaPremios = new ArrayList();
            DBCaja.getListCampPremiosPedidoCliente(listaPremios, pDni, pNumPedVta);

            //obteniendo la cabecera del html
            String cab_html = DBCaja.getCabHtmlCampAcumXCliente(pDni).trim();

            //obteniendo la pie del html
            String pie_html = DBCaja.getPieHtmlCampAcumXCliente(pNumPedVta).trim();

            /**generando el html**/
            html.append(cab_html);
            String auxCodCamp = "";
            boolean flag = false;
            for (int k = 0; k < listaMatriz.size(); k++) { //recorriendo por cada campaña del local

                auxCodCamp = ((ArrayList)listaMatriz.get(k)).get(0).toString().trim();
                //buscando si dicha campaña
                flag = false;
                for (int p = 0; p < listaPremios.size(); p++) {
                    if (auxCodCamp.equals(((ArrayList)listaPremios.get(p)).get(0).toString())) {
                        flag = true;
                        listaPremios.remove(p); //quitanto la campaña
                        break;
                    }
                }
                if (flag) {
                    html.append("<tr><td><b>Ud. Gan&oacute premio de la campaña</b><br>");
                } else {
                    html.append("<tr><td>");
                }

                int cant = 0;

                try {
                    cant = Integer.parseInt(((ArrayList)listaMatriz.get(k)).get(10).toString().trim());
                } catch (Exception e) {
                    cant = 0;
                }

                if (cant > 0) {

                    html.append(((ArrayList)listaMatriz.get(k)).get(7).toString().trim()). //mensaje
                        append("<br>").append("Le faltan ").append(((ArrayList)listaMatriz.get(k)).get(10).toString().trim()).append("&nbsp;&nbsp;").append(((ArrayList)listaMatriz.get(k)).get(6).toString().trim()).append(" de compra para ganar el premio</td></tr> ");


                } else {
                    html.append(((ArrayList)listaMatriz.get(k)).get(7).toString().trim()).append("<br>").append("Acumul&oacute; ").append(((ArrayList)listaMatriz.get(k)).get(9).toString().trim()).append("&nbsp;&nbsp;").append(((ArrayList)listaMatriz.get(k)).get(6).toString().trim()).append(" en total de sus compras </td></tr> ");

                }


            }

            html.append("</table>").append(pie_html).append("</td></tr></table></body></html>");


            if (html.toString().length() > 0) {
                log.info("htmlImprimir:" + html.toString());
                PrintConsejo.imprimirHtml(html.toString(), VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
            }
            // break;
            //}
            //}

        } catch (SQLException sqlException) {
            log.error("",sqlException);
        } catch (Exception e) {
            log.error("",e);
        }
        //}
    }

    /**
     * imprime la cantidad de unidades que acumulo para las campañas
     * en la compra
     * @author JAVIER CALLO QUISPE
     * @since 22.10.2008
     */
    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    private static void imprimirUnidAcumCampXCliente(JDialog pDialogo, String pDni, String pNumPedVta) {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        //if(servicio != null)
        //{
        try {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();

            //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP

            //for (int i = 0; i < servicio.length; i++)
            //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();

            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

            StringBuffer html = new StringBuffer("");

            ArrayList listaLocal = new ArrayList();
            //obteniendo la suma de las unidades acumuladas en las campañas
            //en la compra
            DBCaja.getListCampAcumuladaXCliente(listaLocal, pDni, pNumPedVta);

            //obteniendo los premios que gano en el pedido
            ArrayList listaPremios = new ArrayList();
            DBCaja.getListCampPremiosPedidoCliente(listaPremios, pDni, pNumPedVta);

            //obteniendo la cabecera del html
            String cab_html = DBCaja.getCabHtmlCampAcumXCliente(pDni).trim();

            //obteniendo la pie del html
            String pie_html = DBCaja.getPieHtmlCampAcumXCliente(pNumPedVta).trim();

            /**generando el html**/
            html.append(cab_html);
            String auxCodCamp = "";
            boolean flag = false;
            for (int k = 0; k < listaLocal.size(); k++) { //recorriendo por cada campaña del local

                auxCodCamp = ((ArrayList)listaLocal.get(k)).get(0).toString().trim();
                //buscando si dicha campaña
                flag = false;
                for (int p = 0; p < listaPremios.size(); p++) {
                    if (auxCodCamp.equals(((ArrayList)listaPremios.get(p)).get(0).toString())) {
                        flag = true;
                        listaPremios.remove(p); //quitanto la campaña
                        break;
                    }
                }
                if (flag) {
                    html.append("<tr><td><b>Ud. Gan&oacute premio de la campaña</b><br>");
                } else {
                    html.append("<tr><td>");
                }
                html.append(((ArrayList)listaLocal.get(k)).get(7).toString().trim()).append("<br>").append("Acumul&oacute; ").append(((ArrayList)listaLocal.get(k)).get(9).toString().trim()).append("&nbsp;&nbsp;").append(((ArrayList)listaLocal.get(k)).get(6).toString().trim()).append(" en su compra</td></tr> ");
            }

            html.append("</table>").append(pie_html).append("</td></tr></table></body></html>");


            if (html.toString().length() > 0) {
                log.info("htmlImprimir:" + html.toString());
                PrintConsejo.imprimirHtml(html.toString(), VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
            }
            // break;
            //}
            //}

        } catch (SQLException sqlException) {
            log.error("",sqlException);
        } catch (Exception e) {
            log.error("",e);
        }
        //}
    }

    private static void enviaRegaloMatriz(String pDNI, String pNumPed) throws SQLException {


        ArrayList listaCanjes = new ArrayList();
        DBCaja.getPedidosCanj(pDNI, pNumPed, listaCanjes);

        if (listaCanjes.size() > 0) {
            String codCampana, fechaPedVta, secPedVta, codProd, cantAtendia, valFrac;

            //envia pedidos de regalo
            for (int i = 0; i < listaCanjes.size(); i++) {
                codCampana = FarmaUtility.getValueFieldArrayList(listaCanjes, i, 0);
                fechaPedVta = FarmaUtility.getValueFieldArrayList(listaCanjes, i, 1);
                secPedVta = FarmaUtility.getValueFieldArrayList(listaCanjes, i, 2);
                codProd = FarmaUtility.getValueFieldArrayList(listaCanjes, i, 3);
                cantAtendia = FarmaUtility.getValueFieldArrayList(listaCanjes, i, 4);
                valFrac = FarmaUtility.getValueFieldArrayList(listaCanjes, i, 5);

                DBCaja.insertCanjMatriz(pDNI, pNumPed, codCampana, secPedVta, codProd, cantAtendia, valFrac, "A",
                                        fechaPedVta);

            }


            ArrayList listaOrigenCanjes = new ArrayList();

            DBCaja.getOrigenPedCanj(pDNI, pNumPed, listaOrigenCanjes);

            if (listaOrigenCanjes.size() > 0) {
                //envia pedidos origen
                String codCamp, fechaPed, secPed, codProdCanj, codLocalOrigen, numPedOrigen, SecOrigen, codProdOrigen, cantUso, valFracMin;
                for (int i = 0; i < listaOrigenCanjes.size(); i++) {
                    codCamp = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 0);
                    fechaPed = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 1);
                    secPed = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 2);
                    codProdCanj = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 3);
                    codLocalOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 4);
                    numPedOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 5);
                    SecOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 6);
                    codProdOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 7);
                    cantUso = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 8);
                    valFracMin = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes, i, 9);


                    DBCaja.insertOrigenMatriz(pDNI, pNumPed, codCamp, secPed, codProdCanj, valFracMin, "A",
                                              codLocalOrigen, numPedOrigen, SecOrigen, codProdOrigen, cantUso,
                                              fechaPed);

                }

            }

        }

    }

    /**
     * Se imprime VOUCHER para el remito
     * @author JCORTEZ
     * @since 14.01.09
     */
    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void imprimeVoucherRemito(JDialog pDialogo, String NumRemito) {

        //Impresion en la matricial tal como es en Mifarma
        if (UtilityCajaElectronica.getIndImpreRemito_Matricial())
            imprimeMatricialRemito(pDialogo, NumRemito);
        //impresion en papael Termico
        imprimeTermicaRemito(pDialogo, NumRemito);
    }

    /**
     * @author Dubilluz
     * @since  02.05.2012
     * @param pDialogo
     * @param NumRemito
     */
    public static void imprimeMatricialRemito(JDialog pDialogo, String NumRemito) {
        VariablesCaja.vRutaImpresora = FarmaVariables.vImprReporte;
        FarmaPrintService vPrint = new FarmaPrintService(26, VariablesCaja.vRutaImpresora, false);
        if (!vPrint.startPrintService()) {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("ERROR DE IMPRESORA : No se pudo imprimir el remito");
        } else {
            try {
                String[] pDatos = DBCajaElectronica.getDatosRemitoMatricial(NumRemito).split("@");
                /*
                Remito - 028 - 0010099676
                <<Razon Social y Nombre Local>> Mifarma S.A.C 028-LA MOLINA-PRE_PROD
                <<   Direccion del Local     >> AV. LA MOLINA MZA. J LOTE. 21 URB. RINCONADA DEL LAGO (NO INDICA) LIMA LIMA LA MOLINA
                << El envio se hace para     >> Boveda Prosegur - Banco Citibank
                <<        NUmero de Sobres   >> 61
                <<        Monto Soles        >> 20,486.75
                <<        Monto Dolares      >> 390.00
                */
                if (pDatos.length == 10) {
                    String pNombreEmpresaLocal = pDatos[0].trim();
                    String pDirecLocal = pDatos[1].trim();
                    String pParaBanco = pDatos[2].trim();
                    String pNumeroSobres = pDatos[3].trim();
                    String pMontSoles = pDatos[4].trim();
                    String pMontDolares = pDatos[5].trim();
                    String pCliente = pDatos[6].trim();
                    String pFecha = pDatos[7].trim();
                    String pPrecinto = pDatos[8].trim();
                    String pCuenta = pDatos[9].trim();
                    log.debug("<<<<  pNombreEmpresaLocal >>>>> " + pNombreEmpresaLocal);
                    log.debug("<<<<  pDirecLocal >>>>> " + pDirecLocal);
                    log.debug("<<<<  pParaBanco >>>>> " + pParaBanco);
                    log.debug("<<<<  pNumeroSobres >>>>> " + pNumeroSobres);
                    log.debug("<<<<  pMontSoles >>>>> " + pMontSoles);
                    log.debug("<<<<  pMontDolares >>>>> " + pMontDolares);
                    log.debug("<<<<  pCliente >>>>> " + pCliente);
                    log.debug("<<<<  pFecha >>>>> " + pFecha);
                    log.debug("<<<<  pPrecinto >>>>> " + pPrecinto);
                    log.debug("<<<<  pCuenta >>>>> " + pCuenta);
                    vPrint.activateCondensed();
                    /*
                    vPrint.printLine("linea 1",true);
                    vPrint.printLine("linea 2",true);
                    vPrint.printLine("",true);
                    vPrint.printLine("",true);
                    vPrint.printLine("linea final",true);
*/
                    //for(int i=0;i<=6;i++)
                    /*for(int i=0;i<=25;i++)
                       vPrint.printLine("----------------------"+i,true);*/

                    if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MIFARMA)) {
                        //pCuenta;
                    } else { //if(FarmaVariables.vCodCia.equals(ConstantsRecaudacion.CODGRUPOCIA_FASA)){
                        pCuenta = "";
                    }
                    String efectivoSoles = "";
                    if (FarmaUtility.getDecimalNumber(pMontSoles) > 0) {
                        efectivoSoles =
                                FarmaPRNUtility.alinearIzquierda(valorEnLetras(pMontSoles).trim() + " NUEVOS SOLES",
                                                                 65) + "" + FarmaPRNUtility.llenarBlancos(15) +
                                "S/. " + FarmaPRNUtility.alinearIzquierda(pMontSoles, 15);
                    } else {
                        efectivoSoles = "";
                    }
                    String efectivoDolares = "";
                    if (FarmaUtility.getDecimalNumber(pMontDolares) > 0) {
                        efectivoDolares =
                                FarmaPRNUtility.alinearIzquierda(valorEnLetras(pMontDolares).trim() + " DOLARES AMERICANOS",
                                                                 65) + "" + FarmaPRNUtility.llenarBlancos(15) +
                                "$/. " + FarmaPRNUtility.alinearIzquierda(pMontDolares, 15);
                    } else {
                        efectivoDolares = "";
                    }

                    //vPrint.printLine(FarmaPRNUtility.llenarBlancos(52)+pFecha,true);// -- 1
                    vPrint.printLine("", true); // -- 1
                    vPrint.printLine("", true); // -- 2
                    vPrint.printLine("", true); // -- 3
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(52) + "Recaudacion", true); // -- 4
                    vPrint.printLine("", true); // -- 5
                    vPrint.printLine("", true); // -- 6
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(52) + pCuenta, true); // -- 7
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(51) + pCliente, true); // -- 8
                    vPrint.printLine("", true); // -- 9
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(55) + pNombreEmpresaLocal, true); // -- 10
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(49) + pParaBanco, true); // -- 11
                    vPrint.printLine("", true); // -- 12
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(51) + pDirecLocal, true); // -- 13
                    vPrint.printLine("", true); // -- 14
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(46) + "Cantidad Sobres => " + pNumeroSobres,
                                     true); // -- 15
                    vPrint.printLine("", true); // -- 16
                    vPrint.printLine("", true); // -- 17
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) + efectivoSoles, true); // -- 18
                    vPrint.printLine("", true); // -- 19
                    vPrint.printLine("", true); // -- 20
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) + efectivoDolares, true); // -- 21
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) + pPrecinto, true); // -- 22

                    vPrint.endPrintService();
                }

            } catch (Exception e) {
                log.error("", e);
            }

        }
    }

    private static String valorEnLetras(String pCadena) {

        Double valor = new Double(FarmaUtility.getDecimalNumber(pCadena));

        String centavos = "00";
        double doubleValor = Double.parseDouble(valor.toString());
        int numero = valor.intValue();
        int posPunto = String.valueOf(valor).indexOf(".");
        int posComa = String.valueOf(valor).indexOf(",");
        double doubleNumero = Double.parseDouble(String.valueOf(numero));
        if (posPunto > 0 || posComa > 0) {
            if (posPunto > 0)
                centavos = String.valueOf(valor).substring(posPunto + 1);
            if (posComa > 0)
                centavos = String.valueOf(valor).substring(posComa + 1);
        } else
            centavos = "00";

        String cadena = "";
        int millon = 0;
        int cienMil = 0;

        if (numero < 1000000000) {

            if (numero > 999999) {
                millon = (new Double(numero / 1000000)).intValue();
                numero = numero - millon * 1000000;
                cadena += base(millon, true) + (millon > 1 ? " MILLONES " : " MILLON ");
            }
            if (numero > 999) {
                cienMil = (new Double(numero / 1000)).intValue();
                numero = numero - cienMil * 1000;
                cadena += base(cienMil, false) + " MIL ";
            }

            cadena += base(numero, true);

            if (cadena != null && cadena.trim().length() > 0) {
                cadena += " CON ";
            }

            if (centavos.trim().length() == 1)
                centavos += "0";
            cadena += String.valueOf(centavos) + "/100";

        }

        return cadena.trim() + "";

    }

    public static void imprimeTermicaRemito(JDialog pDialogo, String NumRemito) {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

        //if(servicio != null)
        //{
        try {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();

            //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP

            //for (int i = 0; i < servicio.length; i++)
            //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :" + vIndImpre);
            if (!vIndImpre.equals("N")) {
                //String htmlRemitos = DBCaja.obtieneDatosVoucherRem(NumRemito,FarmaVariables.vIPBD);
                String htmlRemitos = DBCajaElectronica.getHTML_VOUCHER_REMITO(NumRemito); //ASOSA, 22.04.2010
                log.debug("htmlRemitos:" + htmlRemitos);
                PrintConsejo.imprimirHtml(htmlRemitos, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                FarmaUtility.showMessage(pDialogo, "Se asignó las fechas al nuevo remito con éxito \n" +
                        "Voucher impreso con éxito.", null);
                //FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito.",null);
                //break;
            }
            //}
            //}
        } catch (SQLException sqlException) {
            log.error("",sqlException);
            /* if(sqlException.getErrorCode() == 06502){
            FarmaUtility.showMessage(pDialogo,"error: Existen demasiados valores para impresion.",null);
            }else */ {
                FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", null);
            }

        }
        //}
    }


    /**
     * Se imprime ticket al consultar recarga virtual
     * @author Asolis
     * @since 11.02.2009
     */

    // public static void imprimeTicket(JDialog pDialogo,String pNumPedVta,String pFechaVenta,String pProveedor,String pTelefono,int pMonto,String pRespRecarga ,String pComunicado)

    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void imprimeTicket(JDialog pDialogo, String pNumPedVta, int pMonto)
    {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

        //if(servicio != null)
        //{
        try {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();

            //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP

            //for (int i = 0; i < servicio.length; i++)
            //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :" + vIndImpre);
            if (!vIndImpre.equals("N")) {

                String htmlTicket = DBCaja.obtieneDatosTicket(pNumPedVta, pMonto);

                log.debug("htmlRemitos:" + htmlTicket);
                PrintConsejo.imprimirHtml(htmlTicket, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                FarmaUtility.showMessage(pDialogo, "Ticket impreso con éxito.", null);
                //break;
            }
            //}
            //}
        } catch (SQLException sqlException) {
            {
                log.error("", sqlException);
                FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de Ticket.", null);
            }

        }
        //}
    }

    public static void bloqueoCajaApertura(String pSecCaja) {

        try {
            log.debug("Inicio de bloqueo para el cobro...");
            DBCaja.bloqueoCaja(pSecCaja.trim());
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("",e);
        }
        log.debug("Fin de bloqueo continua bloqueado y s");
    }

    /**
     * Imprime prueba en nueva clase.
     * @author ERIOS
     * @since 14.06.2013
     * @param pJDialog
     * @param ruta
     * @param nombreTicket
     * @param pSecImpr
     */
    public static void imprimePruebaTermicaPorIP(JDialog pJDialog, String ruta, String nombreTicket, String pSecImpr) {

        String vModelo;

        try {
            vModelo = DBImpresoras.getModeloImpresora(pSecImpr);
            ArrayList<String> vPrint = new ArrayList<String>();
            vPrint.add("***************************************");
            vPrint.add("PRUEBA DE IMPRESION TICKETERA");
            vPrint.add("NOMBRE : " + nombreTicket);
            vPrint.add("MODELO : " + vModelo);
            vPrint.add("***************************************");

            ImpresoraTicket ticketera = new ImpresoraTicket();

            ticketera.imprimir(vPrint, vModelo, ruta, false, "", "", "", "");

            FarmaUtility.showMessage(pJDialog,
                                     "Se realizó la prueba de impresión a " + nombreTicket.trim() + " , recoja la impresión. ",
                                     null);

        }

        catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Error al realizar prueba de impresion." + e.getMessage() + "", null);

        }


    }

    /**
     * CENTRA LA CADENA SEGUN EL TAMAÑO QUE MANDEN
     * @param pCadena
     * @param pLongitud
     * @param pCaracter
     * @return
     */
    public static String pFormatoLetra(String pCadena, int pLongitud, String pCaracter) {
        int pTamaño = pCadena.trim().length();
        int numeroPos = (int)Math.floor((pLongitud - pTamaño) / 2);
        String pCadenaNew = "";
        //(pLongitud - pTamaño)/2
        //Math.floor(nD * Math.pow(10,nDec))/Math.pow(10,nDec);
        //log.debug(Math.floor(7/2));
        for (int i = 0; i < numeroPos; i++) {
            pCadenaNew += pCaracter;
        }
        pCadenaNew += pCadena.trim();
        pTamaño = pLongitud - pCadenaNew.length();

        for (int i = 0; i < pTamaño; i++) {
            pCadenaNew += pCaracter;
        }

        //log.debug("cadena:"+pCadena);
        //log.debug("numeroPos:"+numeroPos);
        //log.debug("pCadenaNew:"+pCadenaNew);
        return pCadenaNew;
    }

    private static void imprimeBoletaTicket(JDialog pJDialog, String pFechaBD, ArrayList pDetalleComprobante,
                                            String pValTotalNeto, String pValRedondeo, String pNumComprobante,
                                            String pNomImpreso, String pDirImpreso, String pValTotalAhorro,
                                            String pruta, boolean indAnulado, String tipoComp,
                                            int valor, List lstPuntos) throws Exception {
        //ERIOS 13.06.2013 Imprime en clase generica, que determina el tipo de ticketera.
        ArrayList<String> vPrint = new ArrayList<String>();
        String indProdVirtual = "";
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;

        FarmaVariables.vNroPedidoNoImp = VariablesCaja.vNumPedVta;

        try {

            ImpresoraTicket ticketera = new ImpresoraTicket();
            ticketera.generarDocumento(pJDialog, vPrint, pNomImpreso, pDirImpreso, pFechaBD, pNumComprobante,
                                       pDetalleComprobante, pValTotalNeto, pValTotalAhorro, pValRedondeo,
                                       VariablesCaja.vModeloImpresora, indAnulado, tipoComp, valor, lstPuntos);
            boolean bImprimio =
                ticketera.imprimir(vPrint, VariablesCaja.vModeloImpresora, VariablesCaja.vRutaImpresora, true,
                                   pNumComprobante, "C", VariablesCaja.vNumPedVta, tipoComp);
            //ticketera.abrirGabeta(VariablesCaja.vModeloImpresora,VariablesCaja.vRutaImpresora);
            if (!bImprimio) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                FarmaVariables.vAceptar = false;
            } else {
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
                FarmaVariables.vAceptar = true;
            }

        } catch (SQLException sql) {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";

            log.info("**** Fecha :" + pFechaBD);
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            FarmaVariables.vAceptar = false;
            log.error("", sql);

            enviaErrorCorreoPorDB(sql.toString(), VariablesCaja.vNumPedVta);
        } catch (Exception e) {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            log.info("**** Fecha :" + pFechaBD);
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            FarmaVariables.vAceptar = false;
            log.error("Error al imprimir la boleta 5: ",e);

            enviaErrorCorreoPorDB(e.toString(), VariablesCaja.vNumPedVta);
        }

    }

    /**
     * LLEIVA 24-Ene-2014
     * @param pJDialog
     * @param pNumPedVta
     */
    private static void imprimeFactTicket(JDialog pJDialog, String pFechaBD, ArrayList pDetalleComprobante,
                                          String pValTotalNeto, String pValRedondeo, String pNumComprobante,
                                          String pNomImpreso, String pRUCImpreso, String pValTotalAhorro, String pruta,
                                          boolean indAnulado, String tipoCompr, int valor, List lstPuntos) throws Exception {
        //ERIOS 13.06.2013 Imprime en clase generica, que determina el tipo de ticketera.
        ArrayList<String> vPrint = new ArrayList<String>();
        String indProdVirtual = "";
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        FarmaVariables.vNroPedidoNoImp = VariablesCaja.vNumPedVta;

        try {
            ImpresoraTicket ticketera = new ImpresoraTicket();
            ticketera.generarDocumento(pJDialog, vPrint, pNomImpreso, pRUCImpreso, pFechaBD, pNumComprobante,
                                       pDetalleComprobante, pValTotalNeto, pValTotalAhorro, pValRedondeo,
                                       VariablesCaja.vModeloImpresora, indAnulado, tipoCompr, valor, lstPuntos);
            boolean bImprimio =
                ticketera.imprimir(vPrint, VariablesCaja.vModeloImpresora, VariablesCaja.vRutaImpresora, true,
                                   pNumComprobante, "C", VariablesCaja.vNumPedVta, tipoCompr);
            log.debug("¿¿IMPRIMIRO?? : " + bImprimio);
            ticketera.abrirGabeta(VariablesCaja.vModeloImpresora, VariablesCaja.vRutaImpresora);
            if (!bImprimio) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            } else {
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            }
        } catch (SQLException sql) {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            log.info("**** Fecha :" + pFechaBD);
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.error("", sql);
            enviaErrorCorreoPorDB(sql.toString(), VariablesCaja.vNumPedVta);
        } catch (Exception e) {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            log.info("**** Fecha :" + pFechaBD);
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.error("Error al imprimir la boleta 5: ",e);
            enviaErrorCorreoPorDB(e.toString(), VariablesCaja.vNumPedVta);
        }
    }

    /**
     * mfajardo
     * @param pJDialog
     * @param pNumPedVta
     */
    //mfajardo -imprime mensaje campana- 13.04.2009
    private static void imprimeMensajeCampana(JDialog pJDialog, String pNumPedVta) {

        try {
            // String pTipoImp = DBCaja.obtieneTipoImprConsejo();
            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :" + vIndImpre);
            if (!vIndImpre.equals("N")) {
                String htmlTicket = DBCaja.ObtieneCampanas(pNumPedVta);
                log.debug("htmlRemitos:" + htmlTicket);
                if (!htmlTicket.equals("N")) {
                    PrintConsejo.imprimirHtml(htmlTicket, VariablesPtoVenta.vImpresoraActual,
                                              VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                }
            }
        } catch (Exception sqlException) {
            {
                log.error("", sqlException);
                FarmaUtility.showMessage(pJDialog, "Error al obtener los datos de Ticket.", null);
                //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                enviaErrorCorreoPorDB(sqlException.toString(), "<br>Error Al Obtener Datos de Ticket");
            }

        }
    }

    /**
     * Imprime Motivo Anulacion de Ticket
     * @param cajero
     * @param turno
     * @param numpedido
     * @param cod_igv
     * @param pIndReimpresion
     * @return
     * @throws Exception
     */
    public static boolean imprimeMensajeTicketAnulacion(String cajero, String turno, String numpedido, String cod_igv,
                                                        String pIndReimpresion, String numComprob) throws Exception {
        //ERIOS 14.06.2013 Imprime anulacion en nueva clase
        String vIndImpre = "S";
        boolean vResultado = false;
        if (!vIndImpre.equals("N")) {
            String htmlTicket = "N";
            if (numComprob.equals("")) {
                htmlTicket = DBCaja.ImprimeMensajeAnulacion(cajero, turno, numpedido, cod_igv, pIndReimpresion);
            } else {
                htmlTicket =
                        DBConvenioBTLMF.ImprimeMensajeAnulacion(cajero, turno, numpedido, cod_igv, pIndReimpresion,
                                                                numComprob);
            }
            if (!htmlTicket.equals("N")) {
                ArrayList myArray = null;
                StringTokenizer st = null;
                myArray = new ArrayList();
                st = new StringTokenizer(htmlTicket, "Ã");

                while (st.hasMoreTokens()) {
                    myArray.add(st.nextToken());
                }

                int cajaUsuario;
                cajaUsuario = DBCaja.obtieneNumeroCajaUsuarioAux();
                VariablesCaja.vNumCaja = new Integer(cajaUsuario).toString();

                String secImprLocal = VariablesCaja.vSecImprLocalTicket;

                try {
                    VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal);
                    VariablesCaja.vModeloImpresora = DBImpresoras.getModeloImpresora(secImprLocal);
                } catch (Exception e) {
                    log.debug("No se obtuvo una impresora valida", e);
                    return false;
                }

                ArrayList<String> vPrint = new ArrayList<String>();

                Calendar fechaJava = new GregorianCalendar();
                int dia = fechaJava.get(Calendar.DAY_OF_MONTH);
                int resto = dia % 2;

                if (resto == 0 && VariablesPtoVenta.vIndImprimeRojo) {
                    vPrint.add((char)27 + "4"); //rojo
                } else {
                    vPrint.add((char)27 + "5"); //negro
                }

                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "----------Anulación de Pedido----------");
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Local:  " + FarmaVariables.vCodLocal + " - " +
                           FarmaVariables.vDescCortaLocal);
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Fecha de creación: " + myArray.get(7));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Numero de Ticket: " + myArray.get(1));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Fecha de Anulación: " + myArray.get(2));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Caja: " + myArray.get(3) + " Turno: " + myArray.get(4));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Usuario: " + myArray.get(5));
                String vMonto = "";
                //if(numComprob.equals("")){
                vMonto = myArray.get(6).toString();
                //}else{
                //    vMonto = VariablesConvenioBTLMF.vValNetoCompPago;
                //}
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Monto: " + vMonto);
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Motivo: " + myArray.get(9));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "----------Anulación de Pedido----------");

                ImpresoraTicket ticketera = new ImpresoraTicket();
                String pNumComprobante = myArray.get(8).toString();
                vResultado =
                        ticketera.imprimir(vPrint, VariablesCaja.vModeloImpresora, VariablesCaja.vRutaImpresora, true,
                                           pNumComprobante, "A", numpedido, "");
                log.debug("¿¿IMPRIMIRO?? : " + vResultado);
                //DBCaja.actualizaFechaImpr(numpedido,pNumComprobante,"A");
            }
        }
        return vResultado;
    }

    /**
     * Se valida ip de la maquina para la emision de ticket
     * @author  JCORTEZ
     * @since  09.06.09
     * */
    private static boolean validaImpresioPorIP(String IP, String TipComp, JDialog pJDialog, Object pObjectFocus) {
        boolean valor = false;
        String resp = "";
        //chuanes
        //27/10/2014 validamos si es electronico.
        try {
            //if( VariablesCaja.INDELECTRONICO.equalsIgnoreCase(ConstantesDocElectronico.INDELECTRONICO)){
            if (UtilityEposTrx.validacionEmiteElectronico()) {
                if (!accesoImprTermica(new JDialog())) {
                    FarmaUtility.liberarTransaccion();
                } else {
                    valor = true;
                }
            } else {
                resp = DBCaja.validaImpresioPorIP(IP, TipComp);
                if (resp.trim().equalsIgnoreCase("S"))
                    valor = true;
                log.debug("ip: impresora:   " + resp);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al cargar el reporte.\n" +
                    sql.getMessage(), pObjectFocus);

            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sql.toString(), null);
        }
        return valor;
    }


    /**
     * Valida si existe impresora asociada a la IP desde donde se realizara el cobro
     * @AUTHOR JCORTEZ
     * @SINCE 09.06.09
     */
    public static boolean existeIpImpresora(JDialog pDialog, Object pObjectFocus) {
        boolean existeImpresoraIP = true;
        String Sec = "";
        try {
            log.info("******* FarmaVariables.vIpPc : " + FarmaVariables.vIpPc);
            Sec = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, "01", "N");
            log.info("******* Secuencial de impresora : " + Sec);
            if (Sec.trim().equalsIgnoreCase("N")) {
                existeImpresoraIP = false;
                FarmaUtility.showMessage(pDialog, "La IP actual no tiene asignada ninguna impresora. Verifique !!!",
                                         pObjectFocus);
            }
        } catch (SQLException sqlException) {
            log.error("",sqlException);
            FarmaUtility.showMessage(pDialog,
                                     "Error al obtener relacion impresora - IP " + sqlException.getErrorCode(),
                                     pObjectFocus);
        }
        log.info("******* existeImpresoraIP: " + existeImpresoraIP);
        return existeImpresoraIP;
    }


    public static void pruebaImpTermicaPersonalizada(JDialog pDialogo, Object pObject, String pNombreImpresora,
                                                     String pTipo) {
        PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null, null);
        numeroCorrel++;
        boolean vIndImpresion = false;
                
        if (servicio != null) {
            try {
                for (int i = 0; i < servicio.length; i++) {
                    PrintService impresora = servicio[i];
                    String pNameImp = impresora.getName().toString().trim();
                    String pNombre = retornaUltimaPalabra(pNameImp, "\\");
                    
                    if (pNombre.trim().toUpperCase().equalsIgnoreCase(pNombreImpresora.trim().toUpperCase())) {
                        vIndImpresion = true;
                        
                        pruebaImpresoraTermica(pDialogo, pObject, impresora, pTipo, pNombreImpresora);
                        
                        break;
                    }
                }

                if (!vIndImpresion) {
                    FarmaUtility.showMessage(pDialogo,
                                             "No existe la impresora térmica " + pNombreImpresora.trim() + "\nverificar que se encuentre instalada en la PC.",
                                             pObject);
                }
            } catch (SQLException sqlException) {
                log.error("", sqlException);
                FarmaUtility.showMessage(pDialogo, "Error al realizar prueba de impresion.", pObject);

            }

        }
    }

    /**
     * Imprimir voucher de verificacion de recarga para el cliente
     * @author ASOSA
     * @since 03.07.2014
     * @param pDialogo
     * @param pObject
     * @param descProducto
     * @param numTelefono
     * @param monto
     * @param pImpresora
     * @param pTipo
     */
    public void imprVouVerifRecarga(JDialog pDialogo, Object pObject, String descProducto, String numTelefono,
                                    String monto, PrintService pImpresora, String pTipo) {
        try {
            List lstImpresionTicket = new ArrayList();
            lstImpresionTicket = DBCaja.imprVouVerifRecarga(descProducto, numTelefono, monto);
            if (lstImpresionTicket.size() > 0) {

                /*UtilityImpCompElectronico impresion = new UtilityImpCompElectronico();
                boolean rest = impresion.impresionDocumentoEnTermica(lstImpresionTicket,VariablesPtoVenta.vTipoImpTermicaxIp, VariablesPtoVenta.vImpresoraActual.getName(), false, null);
                */
                // KMONCADA IMPRESION DE TICKET DE RECARGAS
                HiloImpresion hilo = new HiloImpresion(lstImpresionTicket);
                hilo.start();
            }
            //log.debug("Impresion de imagen"+vCupon);
            //log.debug(" prueba de impresion termica a : "+ pImpresora.getName());
            log.debug(" pTipo:" + pTipo);
            log.debug(" descProducto: " + descProducto);
            log.debug(" numTelefono: " + numTelefono);
            log.debug(" monto: " + monto);

            //PrintConsejo.imprimirHtml(vCupon,pImpresora,pTipo);
            FarmaUtility.showMessage(pDialogo,
                                     "POR FAVOR SOLICITE LA CONFIRMACION DEL NUMERO DE TELEFONO Y EL MONTO DE LA RECARGA",
                                     pObject);


        } catch (Exception sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo,
                                     "Error al realizar la impresion del voucher de verificación de recarga.",
                                     pObject);

        }


    }
    
    /**
     * Imprimir voucher de verificacion de recarga
     * @author ASOSA
     * @since 12/01/2015
     * @kind RIMAC
     * @param pDialogo
     * @param pObject
     * @param descProducto
     * @param numDni
     * @param cantMeses
     * @param monto
     * @param pImpresora
     * @param pTipo
     */
    public void imprVouVerifRimac(JDialog pDialogo, 
                                  Object pObject, 
                                  String descProducto, 
                                  String numDni,
                                  String cantMeses,
                                  String monto, 
                                  PrintService pImpresora, 
                                  String pTipo) {
        try {
            List lstImpresionTicket = new ArrayList();
            lstImpresionTicket = DBCaja.imprVouVerifRimac(descProducto, 
                                                            numDni, 
                                                          cantMeses,
                                                            monto);
            if (lstImpresionTicket.size() > 0) {

                HiloImpresion hilo = new HiloImpresion(lstImpresionTicket);
                hilo.start();
            }
            //log.debug("Impresion de imagen"+vCupon);
            //log.debug(" prueba de impresion termica a : "+ pImpresora.getName());
            log.debug(" pTipo:" + pTipo);
            log.debug(" descProducto: " + descProducto);
            log.debug(" numdni: " + numDni);
            log.debug(" cantidad meses: " + cantMeses);
            log.debug(" monto: " + monto);

            //PrintConsejo.imprimirHtml(vCupon,pImpresora,pTipo);
            FarmaUtility.showMessage(pDialogo,
                                     "POR FAVOR SOLICITE LA CONFIRMACION DEL DOC. DE INDENTIDAD Y DATOS DEL PRODUCTO RIMAC",
                                     pObject);


        } catch (Exception sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo,
                                     "Error al realizar la impresion del voucher de verificación de recarga.",
                                     pObject);

        }


    }


    public static void printDefault() {

        // tu impresora por default
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        log.debug("Tu impresora por default es: " + service.getName());

    }

    public static String retornaUltimaPalabra(String pCadena, String pSeparador) {
        log.debug(pCadena);
        log.debug(pSeparador);


        String pLetra = "";
        String pPalabraOut = "";
        for (int i = pCadena.length() - 1; i >= 0; i--) {
            pLetra = pCadena.charAt(i) + "";
            if (pLetra.trim().equalsIgnoreCase(pSeparador.trim())) {
                break;
            } else {
                pPalabraOut = pLetra + pPalabraOut;
            }
        }
        return pPalabraOut.trim();
    }

    public static void enviaErrorCorreoPorDB(String message, String vCorrelativo) {
        //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime
        log.info(" VariablesPtoVenta.vDestEmailErrorImpresion >> " + VariablesPtoVenta.vDestEmailErrorImpresion);
        log.info(" VariablesPtoVenta.vCorrelativo >> " + vCorrelativo);
        FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                //ConstantsCaja.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                VariablesPtoVenta.vDestEmailErrorImpresion, "Error al Imprimir Pedido Completo ",
                "Error de Impresión StartPrintService", "Se produjo un error al imprimir un pedido :<br>" +
                //"Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                "Correlativo : " + vCorrelativo + "<br>" + "IP : " + FarmaVariables.vIpPc + "<br>" + "Error: " +
                message,
                //ConstantsCaja.EMAIL_DESTINATARIO_CC_ERROR_IMPRESION
                "");
        log.info("Error en BD al Imprimir los Comprobantes del Pedido.\n" +
                message);

    }


    /**
     * Se imprime la comanda al cobrar un pedido tipo delivery
     * @AUTHOR JCORTEZ
     * @SINCE 07.08.09
     */
    public static void imprimeDatosDeliveryLocal(JDialog pDialogo, String NumPed) {
        try {
            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :" + vIndImpre);
            if (!vIndImpre.equals("N")) {
                String htmlDelivery = DBCaja.obtieneDatosDeliveryLocal(NumPed, FarmaVariables.vIPBD);
                if (htmlDelivery.trim().length() > 0)
                    PrintConsejo.imprimirHtml(htmlDelivery, VariablesPtoVenta.vImpresoraActual,
                                              VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                //break;
            }
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener datos del pedido delivery Local.", null);
        }
    }


    /**
     * Se elimina productos regalo de encarte vigente dentro del pedido.
     * @author JCORTEZ
     * @since  13.08.2009
     */
    public static void liberaProdRegalo(String pNumPed, String pAccion,
                                        String pIndEliminaRespaldo) throws SQLException { //ASOSA,13.07.2010 - agregue el throws y quit el try-catch para que no este cun try-catch dentro de otro
        //try{

        //JCORTEZ 13.08.09  Se elimina producto regalo
        log.debug("****************JCORTEZ********************");
        log.debug("pAccion-->" + pAccion);
        log.debug("pIndEliminaRespaldo-->" + pIndEliminaRespaldo);
        //DBCaja.eliminaProdRegalo(pNumPed,pAccion,pIndEliminaRespaldo); antes
        DBCaja.eliminaProdRegalo_02(pNumPed, pAccion, pIndEliminaRespaldo); //ASOSA, 13.07.2010
        //return true;
        /*}catch (SQLException e){
            log.error("",e);
            log.debug("Error al eliminar productos regalo encarte.");
            //return false;
        }*/
    }


    /**
     * Se imprime cupones regalo
     * @AUTHOR JCORTEZ
     * @SINCE 18.07.09
     */
    public static void imprimeCuponRegalo(JDialog pDialogo, String vCodeCupon, String Dni) {
        
        try {
            
            String vCupon = DBCaja.obtieneImprCuponRegalo(FarmaVariables.vIPBD, vCodeCupon, Dni);
            if (!vCupon.equals("N")) {
                
                imprimeCuponTermico(vCupon,vCodeCupon);
                
            }
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los consejos.", null);
        }

    }

    /**
     * Se obtiene mensaje de ahorro en comprobantes
     * @AUTHOR JCORTEZ
     * @SINCE  03.09.2009
     * */
    public static String obtenerMensaAhorro(JDialog pDialogo, String indFid) {

        String mensaje = "";
        try {
            mensaje = DBCaja.obtieneMensajeAhorro(indFid);
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            ///FarmaUtility.showMessage(pDialogo,"Error al obtener mensaje de descuento.", null);
        }
        return mensaje;

    }

    /**
     * Se valida guias pendientes
     * @AUTHOR JCORTEZ
     * @SINCE  27.10.2009
     */
    public static boolean validaGuiasPendAlmc() {
        String pRes = "";
        try {
            pRes = DBCaja.ExistsGuiasPendAlmc().trim();
        } catch (SQLException sql) {
            log.error("", sql);
            pRes = "N";
        }

        if (pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        return false;
    }

    /**
     * Se imprime VOUCHER para diferencias
     * @author JCHAVEZ
     * @since 23.11.09
     */
    public static void imprimeVoucherDiferencias(JDialog pDialogo) {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

        //if(servicio != null)
        //{
        try {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();

            //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP

            //for (int i = 0; i < servicio.length; i++)
            //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :" + vIndImpre);
            if (!vIndImpre.equals("N")) {
                String htmlDiferencias = DBRecepCiega.obtieneDatosVoucherDiferencias();
                log.debug("htmlDiferencias:" + htmlDiferencias);
                PrintConsejo.imprimirHtml(htmlDiferencias, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", null);
                //FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito.",null);
                //break;
            }
            //}
            //}
        } catch (SQLException sqlException) {
            //log.error("",sqlException);
            /* if(sqlException.getErrorCode() == 06502){
         FarmaUtility.showMessage(pDialogo,"error: Existen demasiados valores para impresion.",null);
         }else */ {
                log.error("", sqlException);
                FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", null);
            }

        }
        //}

    }

    /**
     * Se valida guias pendientes de confirmar de locales
     * @AUTHOR JMIRANDA
     * @SINCE  15.12.2009
     */
    public static boolean validaGuiasXConfirmarLocal() {
        String pRes = "";
        try {
            pRes = DBCaja.ExisteGuiasXConfirmarLocal().trim();
            log.debug("pRes ConfirmarLocal: " + pRes);
        } catch (SQLException sql) {
            log.error("", sql);
            pRes = "N";
        }

        if (pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        return false;
    }

    /**
     * Se imprime VOUCHER por pedido convenio
     * @AUTHOR Jorge Cortez Alvarez
     * @SINCE 07.03.2010
     */
    public static void imprimeDatoConvenio(JDialog pDialogo, String NumPed, String CodConvenio, String CodCli) {
        try {
            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :" + vIndImpre);
            if (!vIndImpre.equals("N")) {
                log.debug("NumPed :" + NumPed);
                log.debug("CodConvenio :" + CodConvenio);
                log.debug("CodCli :" + CodCli);
                String htmlDelivery = DBCaja.obtieneDatosConvenio(NumPed, CodConvenio, CodCli, FarmaVariables.vIPBD);
                log.debug(htmlDelivery);
                PrintConsejo.imprimirHtml(htmlDelivery, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);
            }
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de convenio.", null);
        }
    }


    /**
     * Prueba Impresora Termica de Stickers
     * @author Juan Quispe
     * @since  28.12.2010
     */
    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static boolean pruebaImpresoraTermicaStk(JDialog pDialogo, Object pObject, double pCantidadFilas,
                                                    int r_incio, int r_fin) {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        numeroCorrel++;
        String numAux = "000" + numeroCorrel;
        String pCodCupon = "9999999999" + numAux.substring(numAux.length() - 3, numAux.length());
        int cant_cupones_impresos = 0;
        String vCupon = null;
        //if(servicio != null)
        //{
        try {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();

            //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP

            int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());

            //for (int i = 0; i < servicio.length; i++)
            //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();

            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

            vCupon = DBCaja.pruebaImpresoraTermStick(pCodCupon, r_incio, r_fin);
            log.debug(vCupon);
            log.debug(" prueba de impresion termica...");
            //String vCupon = "";

            //JSANTIVANEZ 10.01.2011
            //metodo para obtener la ruta de la impresora...
            //si es 'n' no se ha declarado la ip(no hay impresora)
            PrintService temporalNomImpCupon;

            temporalNomImpCupon = VariablesPtoVenta.vImpresoraActual;

            boolean flag = nombreImpSticker();

            if (flag == true)

                PrintConsejo.imprimirCuponStick(vCupon, VariablesPtoVenta.vImpresoraSticker,
                                                VariablesPtoVenta.vTipoImpTermicaxIp, pCodCupon, cantIntentosLectura,
                                                pCantidadFilas); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
            //break;
            //}
            //}


            //JS
            /*FarmaUtility.showMessage(pDialogo,
                                  "Se realizó la prueba de impresión, recoja la impresión.", pObject);*/
            else
                FarmaUtility.showMessage(pDialogo,
                                         "No se encontró la impresora sticker :" + "\nVerifique que tenga instalada la impresora.",
                                         pObject);

            //VariablesPtoVenta.vImpresoraActual=temporalNomImpCupon;

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al realizar prueba de impresión.", pObject);

        }
        if (vCupon == null)
            return false; //NO hay datos
        else
            return true; //hay datos
        //}
    }

    //JSANTIVANEZ 10.01.2011

    public static boolean nombreImpSticker() {
        String pNombreImpresora = "";
        String nameImpSticker = ""; //nombre impresora

        PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null, null);
        boolean pEncontroImp = false;
        if (servicio != null) {
            try {

                //devuelve nombre impresora sticker "esticker01"
                nameImpSticker = DBCaja.obtieneNameImpSticker();
                //VariablesPtoVenta.vTipoImpTermicaxIp = "01"; //tipo epson
                log.debug("Tipo Impresora :" + VariablesPtoVenta.vTipoImpTermicaxIp);
                log.debug("Buscando impresora :" + nameImpSticker);
                log.debug("impresoras..encontradas...");
                for (int i = 0; i < servicio.length; i++) {
                    PrintService impresora = servicio[i];
                    String pNameImp = impresora.getName().toString().trim();
                    pNombreImpresora = retornaUltimaPalabra(pNameImp, "\\").trim();

                    //Buscara el nombre.
                    log.debug(i + ") pNameImp:" + pNameImp);
                    log.debug(i + ") pNombreImpresora:" + pNombreImpresora);
                    log.debug(i + ") nameImpSticker:" + nameImpSticker);
                    log.debug("**************************************");
                    if (pNombreImpresora.trim().toUpperCase().equalsIgnoreCase(nameImpSticker.toUpperCase())) {
                        log.info("Encotró impresora térmica");
                        pEncontroImp = true;
                        VariablesPtoVenta.vImpresoraSticker = impresora;
                        break;
                    }
                }
            } catch (SQLException sqlException) {
                log.error("", sqlException);
            }
        }

        return pEncontroImp;
        /*if(!pEncontroImp){
          FarmaUtility.showMessage(null, "No se encontró la impresora sticker :"+
                                        nameImpSticker+
                                        "\nVerifique que tenga instalada la impresora.",
                                        null);
        }*/
    }

    public static String getMensajeFideicomizo() {
        String pCadena = "";
        try {
            pCadena = DBCaja.getMensajeFideicomizo();
        } catch (SQLException e) {
            log.error("", e);
            pCadena = "";
        }
        return pCadena.trim();
    }

    public static String base(int numero, boolean fin) {

        String cadena = "";
        int unidad = 0;
        int decena = 0;
        int centena = 0;

        if (numero < 1000) {

            if (numero > 99) {
                centena = (new Double(numero / 100)).intValue();
                numero = numero - centena * 100;
                if (centena == 1 && numero == 0)
                    cadena += "CIEN ";
                else
                    cadena += centenas(centena) + " ";
            }

            if (numero > 29) {
                decena = (new Double(numero / 10)).intValue();
                numero = numero - decena * 10;
                if (numero > 0)
                    cadena += decenas(decena) + " Y " + unidad(numero, false) + " ";
                else
                    cadena += decenas(decena) + " ";
            } else {
                cadena += unidad(numero, fin);
            }
        }

        return cadena.trim();

    }

    public static String centenas(int numero) {

        String[] aCentenas =
        { "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS",
          "OCHOCIENTOS", "NOVECIENTOS" };

        return (numero == 0 ? "" : aCentenas[numero - 1]);

    }

    public static String decenas(int numero) {

        String[] aDecenas =
        { "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA", "NOVENTA" };

        return (numero == 0 ? "" : aDecenas[numero - 1]);

    }

    public static String unidad(int numero, boolean fin) {
        String[] aUnidades =
        { "UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE",
          "CATORCE", "QUINCE", "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUNO",
          "VEINTIDOS", "VEINTITRES", "VEINTICUATRO", "VEINTICINCO", "VEINTISEIS", "VEINTISIETE", "VEINTIOCHO",
          "VEINTINUEVE" };
        String cadena = "";

        if (numero > 0) {
            if (numero == 1 && fin)
                cadena = "UNO";
            else
                cadena = aUnidades[numero - 1];
        }

        return cadena.trim();
    }

    public static boolean isPedidoConvenioMFBTL(String pNumPedVta) {
        String pCadena = "";
        try {
            pCadena = DBCaja.getIndPedConvMFBTL(pNumPedVta);
        } catch (SQLException e) {
            log.error("", e);
            pCadena = "N";
        }
        if (pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    /**
     * Imprime constrasenia de Recetario
     * @author ERIOS
     * @since 18.06.2013
     * @param string
     */
    private static void imprimeContraseniaRecetario(String string) throws SQLException {
        HashMap<String, String> hRecetario = new HashMap<String, String>();

        DBRecetario.getNumeroRecetario(string, hRecetario);

        String numRecetario = hRecetario.get("NUM_RECETARIO");
        String estRecetario = hRecetario.get("EST_RECETARIO");
        if (!numRecetario.equals("") && estRecetario.equals("E")) {
            FacadeRecetario facadeRecetario = new FacadeRecetario();
            facadeRecetario.imprimirContrasenia(numRecetario);
        }
    }

    public static void abrirGabeta(Frame pFrame, boolean pLogin) {
        try {
            if (pLogin && !cargaLoginAdministrador(pFrame, null)) {
                return;
            }
            // KMONCADA 13.01.2015 CAMBIO PARA PRUEBA DE GABETA EN FACTURACION ELECTRONICA.
            if(UtilityEposTrx.isActFuncElectronica()){
                try{
                    UtilityImpCompElectronico impresion = new UtilityImpCompElectronico();
                    impresion.abrirGabeta();
                }catch(Exception ex){
                    log.error("", ex);
                    FarmaUtility.showMessage(new JDialog(), ex.getMessage(), null);
                }
            }else{
                String secImprLocal = "";
                secImprLocal = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, ConstantsVentas.TIPO_COMP_TICKET, "N");
    
                VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal.trim());
                VariablesCaja.vModeloImpresora = DBImpresoras.getModeloImpresora(secImprLocal);
                log.debug("Abrir gabeta");
                log.debug("Impresora: " + VariablesCaja.vRutaImpresora);
                log.debug("Modelo   : " + VariablesCaja.vModeloImpresora);
                ImpresoraTicket ticketera = new ImpresoraTicket();
                ticketera.abrirGabeta(VariablesCaja.vModeloImpresora, VariablesCaja.vRutaImpresora);
            }
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    /**
     * Abre gabeta en los casos de la forma de pago efectivo
     * @author GFONSECA
     * @since 27.12.2013
     * @param pFrame
     * @param pLogin
     * @param strNumPedido
     */
    public static void abrirGabeta(Frame pFrame, boolean pLogin, String strNumPedido) {
        ArrayList listaFormaPago = null;
        String strFpago = "";
        boolean fpagoEfectivo = false;
        try {
            //obtener formas de pago del pedido            
            listaFormaPago = (new FacadeCaja()).getFormasPagoPedido(strNumPedido);

            for (int i = 0; i < listaFormaPago.size(); i++) {
                strFpago = FarmaUtility.getValueFieldArrayList(listaFormaPago, i, 0);
                if (strFpago.equals(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES) ||
                    strFpago.equals(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES))
                    fpagoEfectivo = true;
            }

            if (fpagoEfectivo) {
                log.info("Se abre la gabeta!");
                abrirGabeta(pFrame, pLogin);
            } else {
                log.info("No se abre la gabeta!");
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private static boolean cargaLoginAdministrador(Frame pFrame, Object pDialog) {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(pFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage((JDialog)pDialog,
                                     "Ocurrio un error al validar rol de usuario \n : " + e.getMessage(), null);
        }

        return FarmaVariables.vAceptar;
    }

    /**
     * Realiza la impresión masiva de tickets de precios de productos.
     * @AUTHOR Christian Vilca Quiros
     * @SINCE 20.09.2013
     */
    public static boolean impresionEnLote(JDialog pDialogo, JTable jTable) {
        boolean impresionOk = true;
        ArrayList listaTemp = new ArrayList();
        ArrayList listaFinal = new ArrayList();
        FarmaTableModel model = (FarmaTableModel)jTable.getModel();
        StringBuilder codigos = new StringBuilder();

        try {
            log.debug("generando codigos de barra.....");
            for (int i = 0; i < model.data.size(); i++) {
                String codProd = (String)jTable.getValueAt(i, 1);
                codigos.append(codProd);
                codigos.append(",");

                //LLEIVA 01-Abr-2014 Si es multiplo de 20, realizar la consulta
                if (i % 20 == 0) { //Envío la impresion
                    String vCodigos = codigos.toString();
                    listaTemp = new ArrayList();
                    DBCaja.imprimirStickerPorLote(listaTemp, vCodigos.substring(0, vCodigos.length() - 1));
                    listaFinal.addAll(listaTemp);
                    codigos = new StringBuilder();
                }
            }
            //LLEIVA 01-Abr-2014 Si todavia quedan registros, añadirlos a la lista final
            if (codigos.toString().length() > 0) { //Envío la impresion
                String vCodigos = codigos.toString();
                listaTemp = new ArrayList();
                DBCaja.imprimirStickerPorLote(listaTemp, vCodigos.substring(0, vCodigos.length() - 1));
                listaFinal.addAll(listaTemp);
            }

            String html = "";
            //Agregamos el salto de línea para separa los registros con los códigos de los tickets.
            for (int i = 0; i < listaFinal.size(); i++) {
                String record = listaFinal.get(i).toString();
                html = html + "\n" +
                        record;
            }

            boolean flag = nombreImpSticker();
            if (flag == true)
                PrintConsejo.imprimirStickerEnLote(html.replace("]", "").replace("[", "").replace("?", "\n"),
                                                   VariablesPtoVenta.vImpresoraSticker);
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(pDialogo, "Ocurrió el siguiente error al imprimir los tickets :\n" +
                    e.getMessage(), jTable);
        }
        return impresionOk;
    }

    /**
     *
     * @author ERIOS
     * @since 10.10.2013
     * @param pJDialog
     * @param pNumPedVta
     * @param estadoAEvaluar
     * @param pObjectFocus
     * @return
     */
    public static boolean verificaEstadoPedidoNuevoCobro(JDialog pJDialog, String pNumPedVta, String estadoAEvaluar,
                                                         Object pObjectFocus) {
        String estadoPed = "";
        estadoPed = obtieneEstadoPedido(pJDialog, pNumPedVta);
        log.debug("Estado de Pedido:" + estadoPed);
        if (estadoAEvaluar.equalsIgnoreCase(estadoPed))
            return true;
        //dubilluz 13.10.2011 bloqueo NO SE DEBE LIBERAR DEBIDO A Q YA EXISTE UN BLOQUEO DE STOCK DE PRODUCTOS.
        //FarmaUtility.liberarTransaccion();
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_COBRADO)) {
            FarmaUtility.showMessage(pJDialog, "El pedido No se encuentra pendiente de cobro.Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_ANULADO)) {
            FarmaUtility.showMessage(pJDialog, "El pedido se encuentra Anulado. Verifique!!!", pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_PENDIENTE)) {
            FarmaUtility.showMessage(pJDialog, "El pedido se encuentra pendiente de cobro. Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        if (estadoPed.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(pJDialog, "No se pudo determinar el estado del pedido. Verifique!!!",
                                     pObjectFocus);
            return false;
        }
        return true;
    }

    /**
     * Se obtiene impresora disponible
     * @AUTHOR JCORTEZ
     * @SINCE 30.06.09
     * */
    public static void obtieneImpresTicket(JDialog pDialog, Object pObject) {
        //JCORTEZ 09.06.09  Se valida relacion maquina - impresora
        if (VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) {
            try { //String result2 = "";
                //ArrayList res = new ArrayList();
                String DescImpr = "";
                String result =
                    DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, ConstantsVentas.TIPO_COMP_TICKET, "N");
                //String temp = DBCaja.getObtieneTipoCompPorIP(FarmaVariables.vIpPc,VariablesCaja.vTipComp);

                /*if(res!=null && res.size()>0)
                {
                    if(res.get(0) != null)
                        result2 = res.get(0).toString();
                }*/

                //verifica que el secuencial exista, caso contrario, se validara la asignada y se mostrara
                //que no tiene niguna asignacion si es el caso. 16.06.09
                String exist = "";
                exist = DBCaja.getExistSecImp(VariablesCaja.vSecImprLocalTicket, FarmaVariables.vIpPc);

                //if(exist.equalsIgnoreCase("S")){    --existe en local para usar.

                if (exist.trim().equalsIgnoreCase("2")) {
                    /*if(VariablesCaja.vTipComp.trim().equalsIgnoreCase(result2.trim()))
                     VariablesCaja.vSecImprLocalTicket=result;
                    else{
                     FarmaUtility.showMessage(this,"El tipo de comprobante es distinto al de la impresora asignada. Verifique!!!",btnListaUsuarioCaja);
                     return;
                    }*/
                    VariablesCaja.vSecImprLocalTicket = result;
                } else if (exist.equalsIgnoreCase("X")) {
                    FarmaUtility.showMessage(pDialog,
                                             "No existen impresoras disponibles. No se imprimira comprobante.!!!",
                                             pObject);
                    VariablesCaja.vSecImprLocalTicket = exist.trim();
                    //return;
                } else if (exist.equalsIgnoreCase("1")) {

                } else {
                    VariablesCaja.vSecImprLocalTicket = exist.trim();
                }


                if (!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")) {
                    DescImpr = DBCaja.getNombreImpresora(VariablesCaja.vSecImprLocalTicket);
                    VariablesCaja.vDescripImpr = DescImpr;
                } else {
                    VariablesCaja.vDescripImpr = "X";
                }


            } catch (Exception e) {
                log.error("",e);
                FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                        //"DUBILLUZ",
                        VariablesPtoVenta.vDestEmailErrorAnulacion, "Error Anular Get Ruta Ip", "Error Anular Pedido",
                        "Se obtuvo un errror al obtener la ruta de Ticketera Metodo obtieneImpresTicket() :<br>" +
                        "IP : " + FarmaVariables.vIpPc + "<br>" + "Error: " + e,
                        //"joliva;operador;daubilluz@gmail.com"
                        "");
                //

            }

        }

    }

    public static boolean getImpresionTicketAnulado(JDialog pJDialog, String pNumeroPedido, String pSecuencia,
                                                    String pCajero, String pTurno) throws Exception {
        /*ERIOS 2.3.2 Se valida por BBDD
            if( tipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET) ||
               tipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT))
           {*/
        boolean vResultado = false, bRes1 = true, bRes2 = true;

        VariablesCaja.vSecuenciaUsoUsuario = pSecuencia;
        /**
        *CHUANES
        * 09/09/2014
        * IMPRESION DE LA NOTA DE CREDITO ELECTRONICO
        **/

        if (ConstantesDocElectronico.lstPedidos != null) {
            for (int i = 0; i < ConstantesDocElectronico.lstPedidos.length; i++) {
                VariablesCaja.vNumPedVta = ConstantesDocElectronico.lstPedidos[i].toString();

                UtilityImpCompElectronico impresionElectronico = new UtilityImpCompElectronico();

                if (UtilityCaja.obtieneCompPago()) {
                    for (int j = 0; j < VariablesVentas.vArray_ListaComprobante.size(); j++) {

                        VariablesCaja.vNumComp =
                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(4)).trim(); //LTAVAR 23.09.2014 OBTENER ELECTRONICO
                        VariablesCaja.vSecComprobante =
                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(1)).trim();
                        VariablesCaja.vTipComp =
                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(2)).trim();
                        VariablesConvenioBTLMF.vTipClienConvenio =
                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(3)).trim();
                        String indicElectronico =
                            DBImpresoras.getIndicCompElectronico(VariablesCaja.vNumPedVta, VariablesCaja.vSecComprobante); //indicadorElectronico
                        if (indicElectronico.equalsIgnoreCase(ConstantesDocElectronico.INDELECTRONICO)) {
                            //vResultado=impresionElectronico.printDocumento(pJDialog,VariablesCaja.vNumPedVta ,VariablesCaja.vSecComprobante,  VariablesCaja.vTipComp,  VariablesConvenioBTLMF.vTipClienConvenio,   VariablesCaja.vNumComp);
                            vResultado =
                                    impresionElectronico.imprimirComprobantePago(VariablesCaja.vNumPedVta, VariablesCaja.vSecComprobante,
                                                                        false);
                            if (vResultado) { //LTAVARA 17.10.2014
                                UtilityCaja.actualizarFechaImpresion(VariablesCaja.vSecComprobante,
                                                                     VariablesCaja.vTipComp);
                                UtilityCaja.actualizaEstadoPedido(VariablesCaja.vNumPedVta,
                                                                  ConstantsCaja.ESTADO_COBRADO);
                            }
                        }

                    }
                }
            } //fin del for

        } else {
            //Solo se emitira comprobante de anulacion si existe secuencial.
            if (!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")) {


                //para montos afectos
                bRes1 = UtilityCaja.imprimeMensajeTicketAnulacion(pCajero, pTurno, pNumeroPedido, "00", "N", "");
                //para montos inafectos
                bRes2 = UtilityCaja.imprimeMensajeTicketAnulacion(pCajero, pTurno, pNumeroPedido, "01", "N", "");

            }
        }
        if (bRes1 || bRes2)
            vResultado = true;
        else
            vResultado = false;
        return vResultado;
    }

    public static boolean existeStockPedido(JDialog pJDialog, String pNumPedido){
        return existeStockPedido(pJDialog, pNumPedido, false);
    }
    
    public static boolean existeStockPedido(JDialog pJDialog, String pNumPedido, boolean bCobroProforma) {
        boolean pRes = false;
        String pCadena = "N";
        //ERIOS 12.01.2016 Para local-mayorista, se separa stock en el cobro.
        if ( bCobroProforma ||
             (VariablesVentas.vEsPedidoDelivery &&
               !(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S")))
             ) {
            pRes = true;
        } else {
            try {
                pCadena = DBCaja.getPermiteCobrarPedido(pNumPedido);
            } catch (SQLException e) {
                pCadena = "N";
                log.error("", e);
            }

            if (pCadena != null && "S".equalsIgnoreCase(pCadena.trim())) {
                pRes = true;
            } else {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, "No puede cobrar el pedido\n" +
                        "Porque no hay stock suficiente para poder generarlo ó\n" +
                        "Existe un Problema en la fracción de productos.", null);
            }
        }
        return pRes;
    }

    /**
     * Procesa pedido Nuevo Cobro
     * @author ERIOS
     * @since 14.10.2013
     * @param myParentFrame
     * @param lblVuelto
     * @param tblDetallePago
     * @param txtMontoPagado
     */
    public static void procesarPedidoNuevoCobro(Frame myParentFrame, JDialog pJDialog, JLabel lblVuelto,
                                                JTable tblDetallePago, JTextField txtMontoPagado,
                                                boolean bCobroProforma) {
        log.debug("##################" + "   INICIO DEL COBRO DEL PEDIDO " + "##################" +
                  VariablesVentas.vNum_Ped_Vta);
        log.debug("1.1   valida Tipo de NUEVO COBRO  ");
        //kmoncada asigna a la variable para que realice el Nuevo cobro convenio.
        if (ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL.equalsIgnoreCase(VariablesVentas.vTipoPedido)) {
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
        }
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) &&
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
            log.debug("1.1.1.Convenio---Ingresar Nuevo Cobro Convenio");
            DlgProcesarNuevoCobroBTLMF dlgProcesarCobro =
                new DlgProcesarNuevoCobroBTLMF(myParentFrame, "", true, null, lblVuelto, tblDetallePago,
                                               txtMontoPagado);
            dlgProcesarCobro.setCobroProforma(bCobroProforma);
            dlgProcesarCobro.setVisible(true);
            log.debug("Finaliza Cobro Convenio");
        } else {
            log.debug("1.1.2.Generar---Ingresa Nuevo Cobro General");
            DlgProcesarNuevoCobro dlgProcesarCobro =
                new DlgProcesarNuevoCobro(myParentFrame, "", true, null, lblVuelto, tblDetallePago, txtMontoPagado);

            dlgProcesarCobro.setCobroProforma(bCobroProforma);
            dlgProcesarCobro.mostrar();
            log.debug("Finaliza Cobro General");
        }
    }

    /**
     * Anula pedido Nuevo Cobro
     * @author ERIOS
     * @since 14.10.2013
     * @param myParentFrame
     * @param lblVuelto
     * @param tblDetallePago
     * @param txtMontoPagado
     */
    public static void anularPedidoNuevoCobro(Frame myParentFrame, JDialog pJDialog, JLabel lblVuelto,
                                              JTable tblDetallePago, JTextField txtMontoPagado) {
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) &&
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
            DlgProcesarNuevoCobroBTLMF dlgProcesarCobro =
                new DlgProcesarNuevoCobroBTLMF(myParentFrame, "", true, null, lblVuelto, tblDetallePago,
                                               txtMontoPagado);
            dlgProcesarCobro.setIndAnularPedido(true);
            dlgProcesarCobro.setVisible(true);
        } else {
            DlgProcesarNuevoCobro dlgProcesarCobro =
                new DlgProcesarNuevoCobro(myParentFrame, "", true, null, lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setIndAnularPedido(true);
            dlgProcesarCobro.setVisible(true);
        }
    }

    /**
     * Finaliza pedido Nuevo Cobro
     * @author ERIOS
     * @since 14.10.2013
     * @param myParentFrame
     * @param lblVuelto
     * @param tblDetallePago
     * @param txtMontoPagado
     */
    public static void finalizarPedidoNuevoCobro(Frame myParentFrame, JDialog pJDialog, JLabel lblVuelto,
                                                 JTable tblDetallePago, JTextField txtMontoPagado, boolean bCobroProforma) {
        log.debug("inicia metodo finalizarPedidoNuevoCobro");
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) &&
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
            log.debug("finaliza proceso cobro convenio");
            DlgProcesarNuevoCobroBTLMF dlgProcesarCobro =
                new DlgProcesarNuevoCobroBTLMF(myParentFrame, "", true, null, lblVuelto, tblDetallePago,
                                               txtMontoPagado);
            dlgProcesarCobro.setIndFinalizaCobro(true);
            dlgProcesarCobro.setCobroProforma(bCobroProforma);
            dlgProcesarCobro.setVisible(true);
        } else {
            log.debug("finaliza proceso cobro general");
            DlgProcesarNuevoCobro dlgProcesarCobro =
                new DlgProcesarNuevoCobro(myParentFrame, "", true, null, lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setIndFinalizaCobro(true);
            dlgProcesarCobro.setCobroProforma(bCobroProforma);
            dlgProcesarCobro.mostrar();
            //dlgProcesarCobro.setVisible(true);
        }
    }

    public static void finalizarPedidoCobroBD(Frame myParentFrame, JDialog pJDialog, JLabel lblVuelto,
                                              JTable tblDetallePago, JTextField txtMontoPagado) {
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) &&
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
            log.debug("finaliza proceso cobro convenio");
            DlgProcesarNuevoCobroBTLMF dlgProcesarCobro =
                new DlgProcesarNuevoCobroBTLMF(myParentFrame, "", true, null, lblVuelto, tblDetallePago,
                                               txtMontoPagado);
            dlgProcesarCobro.setIndCobroConvBD(true);
            dlgProcesarCobro.setVisible(true);
        } else {
            log.debug("finaliza proceso cobro general");
            DlgProcesarNuevoCobro dlgProcesarCobro =
                new DlgProcesarNuevoCobro(myParentFrame, "", true, null, lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setIndCobroBD(true);
            dlgProcesarCobro.setVisible(true);
        }
    }

    public static boolean validacionesCobroPedido(boolean validaTotalCubierto, JDialog pJDialog,
                                                  Object tblFormasPago) {
        return validacionesCobroPedido(validaTotalCubierto, pJDialog, tblFormasPago, false);
    }
    
    public static boolean validacionesCobroPedido(boolean validaTotalCubierto, JDialog pJDialog,
                                                  Object tblFormasPago, boolean bCobroProforma) {
        try { //valida que haya sido seleccionado un pedido.
            if (FarmaConstants.INDICADOR_N.equalsIgnoreCase(VariablesCaja.vIndPedidoSeleccionado))
                return false;

            //validando que se haya cubierta el total del monto del pedido
            if (validaTotalCubierto && !VariablesCaja.vIndTotalPedidoCubierto) {
                FarmaUtility.showMessage(pJDialog, "El Pedido tiene saldo pendiente de cobro.Verifique!!!",
                                         tblFormasPago);
                return false;
            }

            //validando que el pedido este en esta PENDIENTE DE COBRO
            if (!UtilityCaja.verificaEstadoPedidoNuevoCobro(pJDialog, VariablesCaja.vNumPedVta,
                                                            ConstantsCaja.ESTADO_PENDIENTE, null)) {
                return false;
            }

            //Validacion para cajeros
            if (!UtilityCaja.existeCajaUsuarioImpresora(pJDialog, null, bCobroProforma)) { //cerrarVentana(false);
                return false;
            }

            /*
            * Validacion de Fecha actual del sistema contra
            * la fecha del cajero que cobrara
            */
            if (!UtilityCaja.validaFechaMovimientoCaja(pJDialog, tblFormasPago)) {
                FarmaUtility.liberarTransaccion();
                return false;
            }

            //valida que exista RUC si es FACTURA
            if (ConstantsVentas.TIPO_COMP_FACTURA.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped) &&
                (null == VariablesConvenioBTLMF.vCodConvenio ||
                 VariablesConvenioBTLMF.vCodConvenio.trim().length() == 0)) {
                if (VariablesVentas.vRuc_Cli_Ped != null && "".equalsIgnoreCase(VariablesVentas.vRuc_Cli_Ped.trim())) {
                    FarmaUtility.liberarTransaccion();
                    //FarmaUtility.showMessage(pJDialog, "Debe ingresar el numero de RUC!!!", tblFormasPago);
                    FarmaUtility.showMessage(pJDialog, "No se ha ingresado el RUC del cliente correctamente", tblFormasPago);    
                    return false;
                }
            }

            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) &&
                VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
                if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1")) {
                    if (FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) >
                        FarmaUtility.getDecimalNumber(VariablesConvenioBTLMF.vMontoSaldo)) {
                        FarmaUtility.showMessage(pJDialog, "Saldo insuficiente del Benificiario!!!", tblFormasPago);
                        return false;
                    }
                }
            } /*else{
               //   RHERRERA : ESTA OBCCION ESTA DESHABILITADO - SEGUN LO CONVERSADO CON DUBILLUZ
               //  ESTE METODO VALIDA DNI INVALIDO, RUC INVALIDO, MAXIMO AHORRO POR DIA EN MATIZ
               if(!UtilityFidelizacion.validaPedidoFidelizado(VariablesCaja.vNumPedVta,
                                                                 VariablesVentas.vRuc_Cli_Ped,
                                                                 pJDialog,
                                                                 tblFormasPago,
                                                                 VariablesFidelizacion.vNumTarjeta))
                {    return false;
                }

                }*/
            /*
            * Bloqueo de caja
            */
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    public static boolean validaCajaAbierta(JDialog pJDialog) {

        boolean result = true;
        String Indicador = "";
        try {
            //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            log.debug("VariablesCaja.vNumCaja ===> " + VariablesCaja.vNumCaja);
            Indicador = DBCaja.obtieneEstadoCaja();
            if (Indicador.trim().equalsIgnoreCase("N")) {
                FarmaUtility.showMessage(pJDialog, "La caja no se encuentra aperturada. Verifique!!!", null);
                result = false;
            }
            log.debug("Se valida apertura de caja para el cobro ===> " + Indicador);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            result = false;
            log.error("",e);
        }

        //bloque de caja
        return result;
    }

    /**
     * VERIFICA LA RUTA A IMPRIMIR
     * @author CHUANES
     * @since 14.03.2014
     * @param pDialog
     * @param pObjectFocus
     * @param tipoDocumento
     */
    public static boolean verificaImpresora(JDialog pDialog, Object pObjectFocus, String tipoDocumento) {
        boolean existeImpresora = true;
        String secImprLocal = "";
        String ruta = "";

        try {


            if (ConstantsVentas.TIPO_COMP_BOLETA.equalsIgnoreCase(tipoDocumento)) {
                secImprLocal = DBCaja.getSecImprBoletaGuia(tipoDocumento.trim());
                ruta = DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                if (ruta != null) {
                    if (verificaAccesoImpresora(ruta))
                        existeImpresora = true;
                    else {
                        existeImpresora = false;
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pDialog,
                                                 "No se puede acceder a la impresora.\n Ruta : " + ruta + "  Verifique !",
                                                 pObjectFocus);

                    }
                } else {
                    existeImpresora = false;
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pDialog,
                                             "No se pudo determinar la  ruta  de la Impresora para Boletas. Verifique !!!",
                                             pObjectFocus);

                }

            } else if (ConstantsVentas.TIPO_COMP_TICKET.equalsIgnoreCase(tipoDocumento)) {
                secImprLocal =
                        DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, ConstantsVentas.TIPO_COMP_TICKET, "N");
                ruta = DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                if (ruta != null) {
                    if (verificaAccesoImpresora(ruta))
                        existeImpresora = true;
                    else {
                        existeImpresora = false;
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pDialog,
                                                 "No se puede acceder a la impresora.\n Ruta : " + ruta + "  Verifique !",
                                                 pObjectFocus);

                    }
                } else {
                    existeImpresora = false;
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pDialog,
                                             "No se pudo determinar la  ruta  de la Impresora para Ticket. Verifique !!!",
                                             pObjectFocus);

                }
            } else if (ConstantsVentas.TIPO_COMP_TICKET_FACT.equalsIgnoreCase(tipoDocumento)) {
                secImprLocal = DBCaja.getObtieneSecFacImpPorIP(FarmaVariables.vIpPc);
                ruta = DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                if (ruta != null) {
                    if (verificaAccesoImpresora(ruta))
                        existeImpresora = true;
                    else {
                        existeImpresora = false;
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pDialog,
                                                 "No se puede acceder a la impresora.\n Ruta : " + ruta + "  Verifique !",
                                                 pObjectFocus);

                    }
                } else {
                    existeImpresora = false;
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pDialog,
                                             "No se pudo determinar la  ruta  de la Impresora para Ticket Factura. Verifique !!!",
                                             pObjectFocus);

                }


            } else if (ConstantsVentas.TIPO_COMP_GUIA.equalsIgnoreCase(tipoDocumento)) {
                secImprLocal = DBCaja.getSecImprBoletaGuia(tipoDocumento.trim());
                ruta = DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                if (ruta != null) {
                    if (verificaAccesoImpresora(ruta))
                        existeImpresora = true;
                    else {
                        existeImpresora = false;
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pDialog,
                                                 "No se puede acceder a la impresora.\n Ruta : " + ruta + "  Verifique !",
                                                 pObjectFocus);

                    }
                } else {
                    existeImpresora = false;
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pDialog,
                                             "No se pudo determinar la ruta de la Impresora para Guias. Verifique !!!",
                                             pObjectFocus);

                }
            } else if (ConstantsVentas.TIPO_COMP_FACTURA.equalsIgnoreCase(tipoDocumento)) {
                secImprLocal = DBCaja.getObtieneSecFacImpPorIP(FarmaVariables.vIpPc);
                ruta = DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                if (ruta != null) {
                    if (verificaAccesoImpresora(ruta))
                        existeImpresora = true;
                    else {
                        existeImpresora = false;
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pDialog,
                                                 "No se puede acceder a la impresora.\n Ruta : " + ruta + "  Verifique !",
                                                 pObjectFocus);

                    }
                } else {
                    existeImpresora = false;
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pDialog,
                                             "No se pudo determinar la  ruta  de la Impresora para  Factura. Verifique !!!",
                                             pObjectFocus);

                }
            }

        } catch (Exception e) {
            existeImpresora = false;
            FarmaUtility.liberarTransaccion();
            log.error("", e);
        }

        return existeImpresora;
    }

    /**
     * VERIFICA EL ACCESO A LA IMPRESORA A IMPRIMIR
     * @author CHUANES
     * @since 14.03.2014
     * @param ruta
     */
    private static boolean verificaAccesoImpresora(String ruta) {

        boolean valorRetorno = false;
        PrintStream ps = null;
        try {
            FileOutputStream fos = new FileOutputStream(ruta);
            ps = new PrintStream(fos);
            if (ps != null) {
                valorRetorno = true;
            } else {
                valorRetorno = false;
            }
            ps.close();
        } catch (Exception err) {
            valorRetorno = false;
            log.error("", err);
        }
        return valorRetorno;
    }

    public static void actualizaPedidoCupon(JDialog pJDialog, String codCamp, String vtaNumPed, String estado,
                                            String indtodos) {

        try {
            DBCaja.actualizaIndImpre(codCamp, vtaNumPed, estado, indtodos);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();

            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al validar la forma de pago del pedido.\n" +
                    e.getMessage(), null);
        }
    }

    /**
     * Se valida los montos por productos que esten en una campaña para llevarse los cupones ganados
     * @author JCORTEZ
     * @since 03/07/08
     * */
    public static boolean validarFormasPagoCupones(JDialog pJDialog, String numPedVta, JTable tblDetallePago,
                                                   JLabel lblSaldo, JLabel lblVuelto) {

        boolean valor = true;
        String codSel, codobtenido = "";
        String monto1, monto2, descrip = "", desccamp = "", indAcep = "", codCamp = "", numPed = "";
        ArrayList array = new ArrayList();
        int maxform = 0, cant = 0;

        if (tblDetallePago.getRowCount() > 0 && Double.parseDouble(lblSaldo.getText().trim()) == 0) {

            obtieneFormaPagoCampaña(pJDialog, array, VariablesCaja.vNumPedVta);
            log.debug("array::: " + array);
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    numPed = ((String)((ArrayList)array.get(j)).get(0)).trim();
                    codobtenido = ((String)((ArrayList)array.get(j)).get(1)).trim();
                    monto1 = ((String)((ArrayList)array.get(j)).get(2)).trim();
                    desccamp = ((String)((ArrayList)array.get(j)).get(3)).trim();
                    indAcep = ((String)((ArrayList)array.get(j)).get(4)).trim();
                    codCamp = ((String)((ArrayList)array.get(j)).get(5)).trim();

                    for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
                        maxform = tblDetallePago.getRowCount(); //ultima forma de pago restar vuelto
                        codSel = ((String)tblDetallePago.getValueAt(i, 0)).trim();
                        descrip = ((String)tblDetallePago.getValueAt(i, 1)).trim();

                        if (tblDetallePago.getRowCount() > 0) {

                            if (codSel.trim().equalsIgnoreCase(codobtenido.trim())) {
                                monto2 = ((String)tblDetallePago.getValueAt(i, 5)).trim();

                                log.debug("monto pagado :" + Double.parseDouble(monto2));
                                log.debug("monto exacto :" + Double.parseDouble(monto1));

                                if (maxform == i + 1) {
                                    log.debug("leyendo ultima forma de pago");
                                    monto2 =
                                            FarmaUtility.formatNumber(Double.parseDouble(monto2) - Double.parseDouble(lblVuelto.getText().trim()));
                                    log.debug("supuesto pago sin vuelto: " + monto2);
                                }

                                cant = cant + 1;
                                if (cant == 1)
                                    actualizaPedidoCupon(pJDialog, codCamp, numPed, "S", "N");
                                else if (cant > 1)
                                    actualizaPedidoCupon(pJDialog, codCamp, numPed, "N", "N");

                            } else {
                                log.debug("forma pago diferente");
                                valor = true;
                            }
                        }
                    }
                }
            }
            procesaCampSinFormaPago(pJDialog, VariablesCaja.vNumPedVta);
        } else {
            valor = true;
        }
        return valor;
    }

    /**
     * Se valida la impresion de las campañas que no tengan forma de de pago relacionadas
     * @author JCORTEZ
     * @since  10.07.08
     * */
    private static void procesaCampSinFormaPago(JDialog pJDialog, String vtaNumPed) {
        try {
            DBCaja.procesaCampSinFormaPago(vtaNumPed);
            //se comento para evitar problemas de bloqueos anteriores.
            //dubilluz 13.10.2011
            //FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al procesar campañas sin forma de pago.\n" +
                    e.getMessage(), null);
        }
    }

    /**
     * Se obtiene la informacion de campaña por pedido
     * @author JCORTEZ
     * @since 03/07/08
     * */
    private static void obtieneFormaPagoCampaña(JDialog pJDialog, ArrayList array, String vtaNumPed) {

        String result = "";
        array.clear();
        try {
            DBCaja.getFormaPagoCampaña(array, vtaNumPed);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al validar la forma de pago del pedido.\n" +
                    e.getMessage(), null);
        }
    }

    /**
     * Se valida que el pedido tenga productos de campaña
     * @author JCORTEZ
     * @since  03.07.2008
     */
    public static String verificaPedidoCamp(JDialog pJDialog, String numPed) {
        String resp = "";
        try {
            resp = DBCaja.verificaPedidoCamp(numPed);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al validar pedido por campaña.\n" +
                    e.getMessage(), null);
        }
        return resp;
    }


    // RHERRERA:  HACE LLAMADO AL METODO DE ORACLE - MYBATIS
    // DUBILLUZ: USA JDBC para usar la misma conexion en toda la transaccion del cobro.
    // EN RECARGAS usa JDBC
    // COBRO BD    usa JDBC
    // PINPAD      usa IBATIS

    /**
     * Procesa nuevo cobro
     * @author RHERRERA
     * @since 20.04.2014
     */
    public static boolean nvoCobroBD(JDialog pJDialog, String pNumPedido, JTable tblDetallePago) {

        boolean m;
        int prueba;
        ///// EJEMPLO 1 ////
        ArrayList a_CodFormaPago = new ArrayList();
        ArrayList a_monto = new ArrayList();
        ArrayList a_CodMoneda = new ArrayList();
        ArrayList a_XXX = new ArrayList();
        ArrayList a_ImpTotal = new ArrayList();
        ArrayList a_NumTarjeta = new ArrayList();
        ArrayList a_FecVecTarjeta = new ArrayList();
        ArrayList a_NomCliTarjeta = new ArrayList();
        ArrayList a_CantCupon = new ArrayList();
        ArrayList a_DniTarjeta = new ArrayList();
        ArrayList a_CodBouch = new ArrayList();
        ArrayList a_CodLote = new ArrayList();

        int v_resultado = 0;

        //////////////////////////////////
        try {
            for (int i = 0; i < tblDetallePago.getRowCount(); i++) {

                a_CodFormaPago.add(i, tblDetallePago.getValueAt(i, 0));
                a_monto.add(i, tblDetallePago.getValueAt(i, 4));
                a_CodMoneda.add(i, tblDetallePago.getValueAt(i, 6));
                a_XXX.add(i, tblDetallePago.getValueAt(i, 7));
                a_ImpTotal.add(i, tblDetallePago.getValueAt(i, 5));
                a_NumTarjeta.add(i, tblDetallePago.getValueAt(i, 8));
                a_FecVecTarjeta.add(i, tblDetallePago.getValueAt(i, 9));
                a_NomCliTarjeta.add(i, tblDetallePago.getValueAt(i, 10));
                a_CantCupon.add(i, tblDetallePago.getValueAt(i, 2));
                a_DniTarjeta.add(i, tblDetallePago.getValueAt(i, 12));
                a_CodBouch.add(i, tblDetallePago.getValueAt(i, 13));
                a_CodLote.add(i, tblDetallePago.getValueAt(i, 14));


                VariablesCaja.vDescripcionDetalleFormasPago =
                        VariablesCaja.vDescripcionDetalleFormasPago + FarmaUtility.getValueFieldJTable(tblDetallePago,
                                                                                                       i, 0) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 1) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 3) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 4) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 5) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 7) + "<BR>";
            }

            /* FacadeCaja facadeCaja = new FacadeCaja();

            prueba = facadeCaja.pruebaCobro(pNumPedido,
            a_CodFormaPago,
            a_monto,
            a_CodMoneda,
            a_XXX,
            a_ImpTotal,
            a_NumTarjeta,
            a_FecVecTarjeta,
            a_NomCliTarjeta,
            a_CantCupon,
            a_DniTarjeta,
            a_CodBouch,
            a_CodLote
             ); */
            Map mapParametros =
                DBCobroBD.grabarNuevoCobro(pNumPedido, a_CodFormaPago, a_monto, a_CodMoneda, a_XXX, a_ImpTotal,
                                           a_NumTarjeta, a_FecVecTarjeta, a_NomCliTarjeta, a_CantCupon, a_DniTarjeta,
                                           a_CodBouch, a_CodLote);

            v_resultado = new Integer(mapParametros.get(getMayuscula("valor_out")).toString());
            VariablesCaja.vmensaje_out = mapParametros.get(getMayuscula("v_error_mensaje_out")).toString();
            VariablesCaja.vNumSecImpresionComprobantes =
                    new Integer(mapParametros.get(getMayuscula("v_nuc_sec_out")).toString());
            prueba = v_resultado;
            String pIndPedConvenio = (mapParametros.get("C_ESPEDIDOCONVENIO").toString()).substring(0, 1);


            if (prueba == 0) {
                m = false;
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, "El pedido no puede ser cobrado. \n" +
                        VariablesCaja.vmensaje_out + ". \n" +
                        "NO CIERRE LA VENTANA.", null);
            } else {

                if (pIndPedConvenio.equals("S")) {
                    boolean res = cobroConveni_Conexion();
                    if (res) {
                        FarmaUtility.aceptarTransaccion();
                        m = true;
                    } else {
                        m = false;
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pJDialog, "El pedido no puede ser cobrado. \n" +
                                "Error en la conexión para el COBRO CONVENIO. \n", null);
                    }
                } else {
                    m = true;
                    obtieneAgrupaComprobante();
                    VariablesVentas.vEsPedidoConvenio = false;
                    FarmaUtility.aceptarTransaccion();
                }


            }
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.info("", e);
            FarmaUtility.showMessage(pJDialog, e.getMessage(), null);
            // if(e.getErrorCode()==20000)
            //   FarmaUtility.showMessage(pJDialog ,e.getMessage(), null);
            m = false;

        }
        return m;
    }

    /**
     * Procesa nuevo cobro
     * @author RHERRERA
     * @since 02.05.2014
     */
    private static boolean cobroConveni_Conexion() {
        FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();

        VariablesVentas.vEsPedidoConvenio = true;
        boolean resultado = true;
        String resMatriz = "";
        boolean esComprobanteCredito = false;
        //if (Double.parseDouble(VariablesCaja.vValMontoPagado)>0)
        if (VariablesCaja.vValMontoCredito_2 > 0) {
            esComprobanteCredito = true;
        }
        try {
            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1") || esComprobanteCredito) {
                
                    //ERIOS 2.4.3 Graba conexion directa
                    resMatriz = facadeConvenioBTLMF.grabarTemporalesRAC(VariablesCaja.vNumPedVta);
                    if (resMatriz.equals("S")) {
                        DBConvenioBTLMF.grabarCobrarPedidoRac(FarmaConstants.INDICADOR_S);

                        DBConvenioBTLMF.actualizaFechaProcesoRac();
                        resultado = true;
                    }
            }

            else
                resultado = true;


        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            resultado = false;
        }
        return resultado;
    }

    public static String getMayuscula(String pCadena) {
        return pCadena.toUpperCase();
    }
    
    /**
     *  Setear variables para imprimir partida arancelaria
     *  @author ASOSA
     *  @since 17/07/2015
     *  @type IGVAZNIA
     */
    public static void setearVariableImprPA() {
        String ind = UtilityInventario.obtenerParametroString(ConstantsCaja.ID_TAB_IND_IMPR_PA);
        if (ind.equals("S")) {
            VariablesCaja.isImprPA = true;
        } else {
            VariablesCaja.isImprPA = false;
        }
    }

    public static void grabarNuevoCobro(DlgNuevoCobro pDlg, DlgFormaPago pDlgFP, Frame pFramePadre, boolean bCobroProforma) {
        pDlg.indCobroBD = pDlg.getIndCobraBD();
        
        setearVariableImprPA(); //ASOSA - 17/07/2015 - IGVAZNIA

        //ERIOS 19.11.2013 Nuevo "nuevo cobro"
        //1. Valida exito en pago con tarjeta
        if (VariablesCaja.vIndDatosTarjeta && VariablesCaja.vCodVoucher.equals("") && !pDlg.pEjecutaOldCobro) {
            FarmaUtility.showMessage(pDlg,
                                     "Verifique el pago con tarjeta.\n No puede continuar con el cobro.\n Llame a Mesa de ayuda para verificar el caso.",
                                     null);
            return;
        }

        //2. Valida exito en recarga virtual
        if (VariablesCaja.vIndPedidoConProdVirtual &&
            !UtilityCaja.get_ind_ped_con_rimac(VariablesCaja.vNumPedVta)   //ASOSA - 15/01/2015 - RIMAC
            ) {
            DlgProcesarProductoVirtual dlgProcesarProductoVirtual =
                new DlgProcesarProductoVirtual(pDlg.myParentFrame, "Virtual", true);
            dlgProcesarProductoVirtual.setTblFormasPago(pDlg.tblFormasPago);
            dlgProcesarProductoVirtual.setTxtNroPedido(pDlg.txtMontoPagado);
            dlgProcesarProductoVirtual.mostrar();
            if (!dlgProcesarProductoVirtual.isVProcesoRecarga()) {
                //ERIOS 21.11.2013 Se comenta porque se hace el control en DlgProcesarProductoVirtual
                //FarmaUtility.showMessage(this, "Hubo un error al procesar.\n No puede continuar con el cobro.\n Llame a Mesa de ayuda para verificar el caso.", null);
                if (!pDlg.pEjecutaOldCobro)
                    pDlg.eventoEscape();
                else {
                    /*FarmaUtility.showMessage(pDlgFP, "Se cierra la pantalla de cobro1. "+VariablesCaja.vNumPedVta+"-"+
                                                         VariablesVirtual.vCodigoRespuesta.trim(), null);*/
                    //String pNumPedido = VariablesCaja.vNumPedVta;
                    if (VariablesVirtual.vCodigoRespuesta.trim().length() > 0 &&
                        !VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase("00"))
                        pDlgFP.eventoEscape(true);
                }

                return;
            }
        }

        //----   RHERRERA 04/04/2014 : Se comenta para obviar todo el paso del cobro en Java
        //  if ( (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) ||
        //       !indCobroBD ) {

        if (!pDlg.indCobroBD) { // quitar si falla

            if (!VariablesCaja.vIndPedidoCobrado) {
                log.debug("1. Si vIndPedidoCobrado=False, INGRESA METODO procesarNuevoCobro() ");
                pDlg.procesarNuevoCobro();
            }
            //se guarda valores
            VariablesCaja.vVuelto = pDlg.lblVuelto.getText().trim();
            if (VariablesCaja.vIndPedidoCobrado) {
                log.debug("2. Si vIndPedidoCobrado=TRUE, INGRESA METODO finalizarPedidoNuevoCobro ");
                /* if(pDlgFP.getMyParent()==null)
                    log.debug("null pDlgFP.myParentFrame");
                if(pDlg==null)
                    log.debug("null pDlg");
                if(pDlg.lblVuelto==null)
                    log.debug("null pDlgFP.lblVuelto");
                if(pDlg.tblDetallePago==null)
                    log.debug("null pDlgFP.tblDetallePago");
                if(pDlg.txtMontoPagado==null)
                    log.debug("null pDlgFP.txtMontoPagado");
                 */
                UtilityCaja.finalizarPedidoNuevoCobro(pFramePadre, pDlg, pDlg.lblVuelto, pDlg.tblDetallePago,
                                                      pDlg.txtMontoPagado, bCobroProforma);
            }
        } else {
            //RHERRERA: Se Asume que el vIndPedidoCobrado = False
            VariablesCaja.vVuelto = pDlg.lblVuelto.getText().trim();

            if (!VariablesCaja.vIndPedidoCobrado) {
                log.debug("2. Si vIndPedidoCobrado=TRUE, INGRESA METODO finalizarPedidoNuevoCobro ");
                pDlg.procesarCobroBD();
            }
        }

        if (VariablesCaja.vIndPedidoCobrado && !pDlg.pEjecutaOldCobro) {
            //ERIOS 21.01.2014 Validacion de error en cobro
            if (FarmaVariables.vAceptar) {
                FarmaVariables.vAceptar = false;
                pDlg.pedidoCobrado(null);
            }
            //Si la variable indica que de escape y recalcule  el ahorro del cliente
            //if(VariablesFidelizacion.vRecalculaAhorroPedido){
            //  eventoEscape();
            //}
            VariablesVentas.vProductoVirtual = false;
        }
    }

    /**
     * Proceso para anular una recarga virtual
     * @author ASOSA
     * @since 08/07/2014
     * @param pDialog
     * @param pObjectFocus
     * @throws SQLException
     * @throws Exception
     */
    public static void procAnulacionRecargaVirtual(JDialog pDialog, Object pObjectFocus) throws SQLException,
                                                                                                Exception {

        colocaVariablesVirtualesBprepaid();
        if (VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
            log.debug("entro a anulacion de recarga virtual");

            anularRecargaVirtualTX();
            //captura el error de conexion cuando los valores son nulos
            //16.11.2007  dubilluz modificado
            if (VariablesVirtual.respuestaTXBean.getCodigoRespuesta() == null) {
                throw new Exception("Hubo un error con la conexion. Intentarlo mas tarde.");
            }

            colocaInfoRecargaVirtualBprepaid();

            //Mostramos el mensaje de respuesta del proveedor
            //05.12.2007  dubilluz  modificacion
            if (!validaCodigoRespuestaTransaccion()) {
                throw new Exception("Error al realizar la transacción con el proveedor." + "@" +
                                    VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + " - " +
                                    VariablesVirtual.respuestaTXBean.getDescripcion());
            }

        }

        //actualizaInfoPedidoVirtual(pDialog);
    }

    /**
     * @author LTAVARA
     * @since 25.08.2014
     * Obtiene lista de comprobantes de pago del pedido.
     * @return
     */
    public static boolean obtieneCompPago() {
        VariablesVentas.vArray_ListaComprobante = new ArrayList();
        boolean valor = true;
        long tmpT1, tmpT2;
        tmpT1 = System.currentTimeMillis();
        try {
            valor = true;

            DBCaja.obtieneCompPagos(VariablesVentas.vArray_ListaComprobante);
            if (VariablesVentas.vArray_ListaComprobante.size() == 0) {
                // FarmaUtility.liberarTransaccion();
                // FarmaUtility.showMessage(this,"No se pudo determinar los datos del comprobante. Verifique!!!.",pObjectFocus);
                valor = false;
            }
            log.info("VariablesVentas.vArray_ListaComprobante : " + VariablesVentas.vArray_ListaComprobante.size());

        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            // FarmaUtility.showMessage(pJDialog,"Error al obtener los datos de Impresion del Comprobante.",pObjectFocus);
            valor = false;
            UtilityCaja.enviaErrorCorreoPorDB(sql.toString(), null);
        }
        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 4: Det.Comp Pago:" + (tmpT2 - tmpT1) + " milisegundos");
        return valor;
    }

    /**
     * @author CHUANES
     * @since 04.09.2014
     * Verifica el acceso  de la Impresora Termica
     * asignada al computador.
     * */
    public static boolean accesoImprTermica(JDialog pJDialog) {
        boolean flag = true;
        
        String ruta = null;
        String modelo = null;
        String nombre = null;
        try {
            UtilityImpCompElectronico electronico = new UtilityImpCompElectronico();
            ruta = electronico.getRuta();
            modelo = electronico.getModelo();
            nombre = electronico.getNombreTermica();
            if (ruta != null) {
                if (verificaAccesoImpresora(ruta)) {
                    if (modelo.trim().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR) ||
                        modelo.trim().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)) {
                        flag = true;
                    } else {
                        flag = false;
                        FarmaUtility.showMessage(pJDialog, ConstantesDocElectronico.ERRORNOM, null);
                    }

                } else {
                    flag = false;
                    FarmaUtility.showMessage(pJDialog, ConstantesDocElectronico.ERRORACCESO + ruta, null);
                }

            } else {
                flag = false;
                FarmaUtility.showMessage(pJDialog, ConstantesDocElectronico.ERRORASIGNA + nombre, null);
            }
        } catch (Exception e) {
            flag = false;
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, ConstantesDocElectronico.ERRDATOSIMP + ruta, null);
        }
        return flag;
    }

    public static void actualizarFechaImpresion(String cSecComPago, String cTipComPago) {
        try {
            DBCaja.actualizaFechaImpresion(cSecComPago, cTipComPago);
        } catch (SQLException e) {
            log.error("", e);
        }

    }

    /**
     * @author RHERRERA
     * @since 18.11.2014
     * Obtiene el tipo de comprobante del pedido.
     * */
    public static boolean obtieneCompPago_GUI() {

        VariablesVentas.vArray_ListaComprobante = new ArrayList();
        boolean valor = true;

        try {
            valor = true;

            //RHERRERA 18.11.2014
            DBCaja.obtieneCompPagos_gui(VariablesVentas.vArray_ListaComprobante);
            if (VariablesVentas.vArray_ListaComprobante.size() == 0) {
                // FarmaUtility.liberarTransaccion();
                // FarmaUtility.showMessage(this,"No se pudo determinar los datos del comprobante. Verifique!!!.",pObjectFocus);
                valor = false;
            }
            log.info("VariablesVentas.vArray_ListaComprobante : " + VariablesVentas.vArray_ListaComprobante.size());

        } catch (SQLException sql) {
            log.error("",sql);
            valor = false;
            UtilityCaja.enviaErrorCorreoPorDB(sql.toString(), null);
        }
        return valor;
    }
    
    /**
     * Listar pedido con producto rimac.
     * @author ASOSA
     * @since 08/01/2015
     * @kind RIMAC
     * @param pNumPedVenta
     * @return
     */
    public static ArrayList getDetaVentaRimac(String pNumPedVenta){
        ArrayList list = new ArrayList();
        try{
            DBCaja.getDetaVentaRimac(list, pNumPedVenta);
            if (list == null || list.size() == 0) {
                list = null;
            }
        }catch (Exception e) {
            log.error("",e);
            list = null;
        } finally {
            return list;
        }        
    }
    
    /**
     * Insertar pedido con un producto RIMAC.
     * @author ASOSA
     * @since 08/01/2015
     * @kind RIMAC
     * @param pedVtaRimacDTO
     * @param pIndCloseConecction
     * @return
     */
    public static String insertVentaRimacMatriz(PedVtaRimacDTO pedVtaRimacDTO,
                                                String pIndCloseConecction) {
        String rpta = "N";
        try {
            rpta = DBCaja.insertVentaRimacMatriz(pedVtaRimacDTO, pIndCloseConecction);
        } catch(Exception e) {
            log.error("",e);
            rpta = "N";
        } finally {
            return rpta;
        }        
    }

   
    public static String getNombreElectronico(String pCodGrupoCia, String pCodLocal, String pNumPedVta,
                                              String pSecCompPago) throws Exception {
        String nombreFile = DBCaja.getNombreFileComprobante(pCodGrupoCia, pCodLocal, pNumPedVta, pSecCompPago);
        return nombreFile.trim();
    }
    
    /**
     * obtener indicador si el prod rimac esta ya activo
     * @author ASOSA
     * @since 08/01/2015
     * @kind RIMAC
     * 
     */
    public static boolean get_ind_prod_rimac_activo(String num_ped_vta){
        boolean flag = false;
        String rpta = "N";
        try {
            rpta = DBCaja.getInd_prod_rimac_activado(num_ped_vta, FarmaConstants.INDICADOR_N);
            if (rpta.trim().equals(FarmaConstants.INDICADOR_S)) {
                flag = true;
            }   
        } catch(Exception e) {
            log.error("",e);
            flag = true;   //para que entre a la validacion, sino permitira anular y eso no seria correcto.
        } finally {
            return flag;
        }        
    }     
    
    /**
     * obtener indicador si el pedido tiene un producto rimac
     * @author ASOSA
     * @since 08/01/2015
     * @kind RIMAC
     * 
     */
    public static boolean get_ind_ped_con_rimac(String num_ped_vta){
        boolean flag = false;
        String rpta = "N";
        try {
            rpta = DBCaja.getInd_prod_rimac_in_ped(num_ped_vta);
            if (rpta.trim().equals(FarmaConstants.INDICADOR_S)) {
                flag = true;
            }   
        } catch(Exception e) {
            log.error("",e);
            flag = true;    //para que entre a la validacion, sino permitira anular y eso no seria correcto.
        } finally {
            return flag;
        }        
    }  
    
    /**
     * Insertar producto rimac como regalo
     * @author ASOSA
     * @since 15/01/2015
     * @kind RIMAC
     * @param num_ped_vta
     * @return
     */
        public static boolean insProdRegaloRimac(String num_ped_vta){
            boolean flag = false;
            String rpta = "N";
            try {
                rpta = DBCaja.insProdRegaloRimac(num_ped_vta);
                if (rpta.trim().equals(FarmaConstants.INDICADOR_S)) {
                    flag = true;
                }   
            } catch(Exception e) {
                log.error("",e);
                flag = false;
            } finally {
                return flag;
            }        
        }  
    
    /**
     * 
     * @author ASOSA
     * @since 19/01/2015
     * @kind RIMAC
     * 
     */
    public static String getStkProdRegalo(String codProd){       
        String rpta = " ";
        try {
            rpta = DBCaja.getStkProdRegalo(codProd);            
        } catch(Exception e) {
            log.error("",e);
        } finally {
            return rpta;
        }        
    } 
    
    /**
     * Determinar si un pedido tiene varios comprobantes
     * @author ASOSA
     * @since 10/02/2015
     * @kind 
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static boolean getIndVariosCompPedido(String pNumPedVta) throws SQLException {
        boolean flag = false;
        String rpta = "";
        try {
            rpta = DBCaja.getIndVariosCompPedido(pNumPedVta);            
            if (rpta.equals("S")) {
                flag = true;
            }
        } catch(Exception e) {
            log.error("",e);
        } finally {
            return flag;
        }   
    }
    
    /**
     * Imprimir voucher de verificacion de recarga
     * @author ASOSA
     * @since 10/02/2015
     * @kind 
     * @param pDialogo
     * @param pObject
     * @param descProducto
     * @param numDni
     * @param cantMeses
     * @param monto
     * @param pImpresora
     * @param pTipo
     */
    public static void imprVouPtosCliente(String pNumPedVta) {
        try {
            List lstImpresionTicket = new ArrayList();
            lstImpresionTicket = DBCaja.imprVouPtosCliente(pNumPedVta);
            /*
            if (lstImpresionTicket.size() > 0) {
                HiloImpresion hilo = new HiloImpresion(lstImpresionTicket);
                hilo.start();
            }
*/
            UtilityImpCompElectronico impresion = new UtilityImpCompElectronico();
            boolean rest = impresion.impresionTermica(lstImpresionTicket, null);
                //impresion.impresionDocumentoEnTermica(lstImpresionTicket, false, null, false);
            
            log.info("pNumPedVta: " + pNumPedVta);

            //PrintConsejo.imprimirHtml(vCupon,pImpresora,pTipo);
            /*
            FarmaUtility.showMessage(pDialogo,
                                     "POR FAVOR SOLICITE LA CONFIRMACION DEL NUMERO DE DNI Y DATOS DEL PRODUCTO RIMAC",
                                     pObject);
*/

        } catch (Exception sqlException) {
            log.error("",sqlException);
            /*
            FarmaUtility.showMessage(pDialogo,
                                     "Error al realizar la impresion del voucher de verificación de recarga.",
                                     pObject);
*/

        }
    }
    
    /**
     * Metodo que valida si un pedido que genero cupon se pueden anular a pesar de haber utilizado el cupon.
     * @author kmoncada
     * @since 13.04.2015
     * @return
     */
    public static boolean getPermiteAnulaConCuponesUsados(){
        try{
            return DBCaja.getIndAnularConCuponUsado();
        }catch(Exception ex){
            log.error("",ex);
            return true;
        }
    }
    
    /**
     * Obtener partida arancelaria
     * @author ASOSA
     * @since 20/07/2015
     * @type IGVAZNIA
     * @param codProd
     * @return
     * @throws SQLException
     */
    public static String obtenerPartidaArancelaria(String codProd) {
        String partAranc = "";
        try {
            partAranc = DBCaja.obtenerPartidaArancelaria(codProd);
        }catch(Exception e) {
            log.error("",e);
        }
        return partAranc;
    }

    private static void imprimeCuponTermico(String vCupon, String vCodeCupon) throws SQLException {
        (new FacadeCaja()).impresionEncabezadoCupon(vCodeCupon);
        
        PrintConsejo.imprimirHtml(vCupon, VariablesPtoVenta.vImpresoraActual,
                                   VariablesPtoVenta.vTipoImpTermicaxIp);
        
        DBCaja.cambiaIndImpresionCupon(VariablesCaja.vNumPedVta, vCodeCupon);
    }
    
    /**
     * @author KMONCADA
     * @param pNumPedVta
     * @param pSecCompPago
     * @param pRutaTestigo
     * @return
     * @throws Exception
     */
    public static boolean imprimirComprobantePago(String pNumPedVta, String pSecCompPago, String pRutaTestigo)throws Exception{
        boolean isImprimo = false;
        List lstComprobante = new ArrayList();
        lstComprobante = DBPtoVenta.getComprobantePago(FarmaVariables.vCodGrupoCia,
                                                       FarmaVariables.vCodLocal,
                                                       pNumPedVta,
                                                       pSecCompPago);
        if (lstComprobante.size() > 0) {
            isImprimo = (new UtilityImpCompElectronico()).impresionMatricial(lstComprobante,pRutaTestigo);
            if (!isImprimo) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            } else {
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            }
        }else{
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
        }
        return isImprimo;
    }
}
