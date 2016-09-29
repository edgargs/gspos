package mifarma.ptoventa.recepcionCiega.reference;


import com.pyx4j.jni.Beep;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.JDialog;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.reference.DBPtoVenta;
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

    public static boolean updateEstadoRecep(String pEstado, String pNumRecep, JDialog pDialog, Object pObject) {
        //Utiliza el Secuencial de la recepcion: VariablesRecepCiega.vSecRecepGuia
        boolean flag = false;
        try {
            DBRecepCiega.actualizaEstadoRecep(pNumRecep, pEstado);
            log.debug("Estado Cabecera, sec:" + pNumRecep + " --- " + pEstado);
            flag = true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);            
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
            FarmaUtility.liberarTransaccion();
            log.debug("Actualiza ind de Segundo Conteo...");
            log.error("", e);            
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
            log.error("", sqlException);
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
    public static void imprimeVoucherTransportista_02(JDialog pDialogo, String pNroRecepcion, Object obj,
                                                      boolean soloImprime) {
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
                if(soloImprime)
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
            log.error("", sqlException);
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
     * @author DUBILLUZ
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
     * @since 21.07.2014
     * @param pTableModel
     * @param pHojaResumen
     * @throws SQLException
     */
    public static void obtenerDocTransporte(FarmaTableModel pTableModel, String pHojaResumen) throws SQLException {
        try {
            DBRecepCiega.obtenerDocTransporte(pTableModel, pHojaResumen);
        } catch (SQLException e) {
            log.error("",e);
        }
    }

    /**
     * Determina si una bandeja existe o no en una hoja de resumen.
     * @author ASOSA
     * @since 21.07.2014
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
            log.error("",e);
        }
        return flag;
    }

    /**
     * Determina si existe grabada una hoja de resumen.
     * @author ASOSA
     * @since 13.08.2014
     * @param pHojaResumen
     * @return
     * @throws SQLException
     */
    public static boolean buscarHojaResumenGrabada(String pHojaResumen,boolean vIndNvo) throws SQLException {
        boolean flag = false;
        String rpta = "N";
        try {
            rpta = DBRecepCiega.buscarHojaResumenGrabada(pHojaResumen,vIndNvo);
            if (rpta.equals("S")) {
                flag = true;
            }
        } catch (SQLException e) {
            log.error("",e);
        }
        return flag;
    }
    // DUBILLUZ 27.05.2015
    public static boolean getIndActivoNuevaRecepMercaderia() throws SQLException {
        boolean flag = DBRecepCiega.getIndActivoNuevoRecepMercaderia();
        return flag;
    }    

    // DUBILLUZ 27.05.2015
    public static boolean getIndActivoPermiteFaltanteBandeja() throws SQLException {
        boolean flag = DBRecepCiega.getIndPermiteFaltanteBandeja();
        return flag;
    }    

    // DUBILLUZ 27.05.2015
    public static boolean getIndActivoPermiteSobranteBandeja() throws SQLException {
        boolean flag = DBRecepCiega.getIndPermiteSobranteBandeja();
        return flag;
    }        

    public static boolean isExisteHojaMaestro(String pHojaResumen) throws SQLException {
        boolean flag = false;
        String rpta = "N";
        try {
            rpta = DBRecepCiega.isExisteHojaMaestro(pHojaResumen);
            if (rpta.equals("S")) {
                flag = true;
            }
        } catch (SQLException e) {
            log.error("",e);
        }
        return flag;
    }    
    
    public static String getAccionHojaExistente(String pHojaResumen) throws SQLException {
        String rpta = "";
        try {
            rpta = DBRecepCiega.getMsjAccionHojaExistente(pHojaResumen);
        } catch (SQLException e) {
            log.error("",e);
        }
        return rpta;
    }    
    
    public static boolean isExisteBandejaHojaResumen(String pHojaResumen, String pNroBandeja) throws SQLException {
        boolean flag = false;
        String rpta = "N";
        try {
            rpta = DBRecepCiega.existeBandejaHoja(pHojaResumen, pNroBandeja);
            if (rpta.equals("S")) {
                flag = true;
            }
        } catch (SQLException e) {
            log.error("",e);
        }
        return flag;
    }    
    
    
    
    public static boolean existePorDevolver() {
        boolean flag = false;
        
        try {
            flag = DBRecepCiega.existePorDevolver();
        } catch (Exception e) {
            log.error("",e);
        }
        return flag;
    }    
    
    public static boolean isValidoIngresarHojaNoExiste(String pHojaResumen) {
        boolean flag = false;
        String rpta = "N";
        try {
            rpta = DBRecepCiega.visValidoHojaRes(pHojaResumen);
            if (rpta.equals("S")) {
                flag = true;
            }
        } catch (SQLException e) {
            log.error("",e);
        }
        return flag;
    }    
    
    public void loadTableHorizontal(ArrayList vListaTotal, int pCtdColumns, JTable pTabla, FarmaTableModel pTbmoldel) {
        ArrayList vListGrabar = new ArrayList();
        ArrayList vFila = new ArrayList();
        int pos = 0;
        for (int i = 0; i < vListaTotal.size(); i++) {
            if (pos == pCtdColumns) {
                vListGrabar.add(vFila);
                vFila = new ArrayList();
                pos = 0;
            }
            vFila.add(((ArrayList)(vListaTotal.get(i))).get(0).toString());
            pos++;
            if ((i + 1) == vListaTotal.size()) {
                for (int Vpos = pos; Vpos < (pCtdColumns + 1); Vpos++)
                    vFila.add(" ");
                vListGrabar.add(vFila);
            }
        }
        pTbmoldel.data.clear();
        for (int g = 0; g < vListGrabar.size(); g++)
            pTbmoldel.insertRow((ArrayList)vListGrabar.get(g));
        pTabla.repaint();
    }    
    
    
    public static boolean isValidoBandeja(String pBandeja) {
        boolean flag = false;
        
        try {
            flag = DBRecepCiega.isValidoBandeja(pBandeja);
        } catch (Exception e) {
            log.error("",e);
        }
        return flag;
    }

    public static void newBandejaExiste(String pBandeja) {
        try {
            DBRecepCiega.creaBandejaExistePorDevolver(pBandeja);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("",e);
        }
    }

    public static void newBandejaNoExiste(String pBandeja) {
        try {
            DBRecepCiega.creaBandejaNoExistePorDevolver(pBandeja);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("",e);
        }
    }
    
    
    public static boolean isValidoBandejaDevol(String pBandeja) {
        boolean flag = false;
        
        try {
            flag = DBRecepCiega.isTipoCorrectoBandejaDevol(pBandeja);
        } catch (Exception e) {
            log.error("",e);
        }
        return flag;
    }
    
    //INI ASOSA - 05.06.2015 - RCIEGAM
    /**
     * Actualiza y devolver correlativo de bulto.
     * @author ASOSA
     * @since 05.06.2015
     * @return
     * @throws SQLException
     */
    public static String getCorrBulto(String codBulto,
                                      String nroRecep) {
        String corrBulto = "";
        try {
            corrBulto = DBRecepCiega.getCorrBulto(nroRecep);
            FarmaUtility.aceptarTransaccion();
            VariablesRecepCiega.vCodBulto = codBulto;
            VariablesRecepCiega.vCorrBulto = corrBulto;
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("",e);
        }
        return corrBulto;
    }
    
    /**
     * Descripcion: Insertar el auxiliar de conteo por cada producto escaneado
     * @author ASOSA
     * @since 05.06.2015
     * @param pSecRecepGuia
     * @param pSecAuxConteo
     * @param pCodBarra
     * @param pCant
     * @param pIndDeteriorado
     * @param pIndFueraLote
     * @param pIndNoFound
     * @param codBulto
     * @param corrBulto
     * @throws SQLException
     */
    public static void insertAuxConteo02(String pSecRecepGuia, int pSecAuxConteo, String pCodBarra, String pCant,
                                       String pIndDeteriorado, String pIndFueraLote,
                                       String pIndNoFound,
                                       String codBulto, 
                                       String corrBulto,
                                       String indLectoraBulto) {
        try {
            DBRecepCiega.insertAuxConteo02(pSecRecepGuia, 
                                           pSecAuxConteo, 
                                           pCodBarra, 
                                           pCant, 
                                           pIndDeteriorado, 
                                           pIndFueraLote, 
                                           pIndNoFound, 
                                           codBulto, 
                                           corrBulto,
                                           indLectoraBulto);
        } catch (Exception e) {
            log.error("",e);
        }
    }
    
    /**
     * Listar productos que voy escaneando
     * @author ASOSA
     * @since 05.06.2015
     * @param pTableModel
     * @param pNroRecep
     * @param pNroBloque
     * @throws SQLException
     */
    public static void obtieneListaPrimerConteo02(FarmaTableModel pTableModel, 
                                                  String pNroRecep,
                                                  String pNroBloque) {
        try {
            DBRecepCiega.obtieneListaPrimerConteo02(pTableModel,
                                                    pNroRecep,
                                                    pNroBloque);
        } catch (SQLException e) {
            log.error("",e);
        }
    }
    
    /**
     * Finalizar conteo insertando los conteos de la tabla auxiliar a la final.
     * @author ASOSA
     * @since 08.06.2015
     * 
     * @param pNroRecep
     * @throws SQLException
     */
    public static void insertConteo02(String pNroRecep) {
        try {
            DBRecepCiega.insertConteo02(pNroRecep);
        } catch(Exception e) {
            log.error("",e);
        }        
    }
    
    /**
     * Determinar si un bulto existe en una recepcion.
     * @author ASOSA
     * @since 08.06.2015
     * 
     * @param pNroRecep
     * @param pNroBulto
     * @return
     */
    public static boolean isBultoExists(String pNroRecep,
                                      String pNroBulto){
        boolean flag = false;
        String ind = "";
        try {
            ind = DBRecepCiega.getIndBultoExists(pNroRecep, 
                                           pNroBulto);
            if (ind.equals("S")) {
                flag = true;
            }
        } catch(Exception e) {
            log.error("",e);
        }
        return flag;
    }
    
    /**
     * Determinar si un bulto existe en una recepcion.
     * @author ASOSA
     * @since 09.06.2015
     * 
     * @param corrBulto
     */
    public static void imprVouBultoCerrado(String corrBulto) {
        try {
            List lstImpresionTicket = new ArrayList();
            lstImpresionTicket = DBRecepCiega.imprVouBultoCerrado(corrBulto);
    
            UtilityImpCompElectronico impresion = new UtilityImpCompElectronico();
            boolean rest =
                impresion.impresionDocumentoEnTermica(lstImpresionTicket, false, null, false);
            
            log.info("corrBulto: " + corrBulto);

        } catch (Exception sqlException) {
            log.error("",sqlException);
        }
    }
    
    /**
     * ejecutarSonido
     * @author ASOSA
     * @since 10.06.2015
     * 
     * @param archivo
     */
    public static void ejecutarSonido(String archivo) {
        try {
            
            if (VariablesRecepCiega.isLocalBeepBios) {
                ejecutarSonidoBios(archivo);
            } else {
                ejecutarSonidoArchivo(archivo);
            }
            
        } catch(Exception e) {
            log.error("",e);
        }
    }
    
    /**
     * ejecutarSonidoArchivo
     * @param archivo
     * @author ASOSA
     * @since 22.06.2015
     * @throws Exception
     */
    public static void ejecutarSonidoArchivo(String archivo) throws Exception{
        // Se obtiene un Clip de sonido
        Clip sonido = AudioSystem.getClip();
        
        //String ruta = UtilityInventario.obtenerParametroString(ConstantsRecepCiega.COD_RUTA_PLAY_AUDIO);
        //ruta = ruta + archivo;
        String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
        String carpetaSonidos = UtilityInventario.obtenerParametroString(ConstantsRecepCiega.COD_RUTA_SAVE_AUDIO);  //ASOSA - 11.06.2015 - RCIEGAM
        Path archivoP = Paths.get(carpetaRaiz, carpetaSonidos, archivo);
        String ruta = archivoP.toString();
        log.info("RUTA -> " + ruta);
        
        File file = new File(ruta);        
        // Se carga con un fichero wav
        
        sonido.open(AudioSystem.getAudioInputStream(file));
        log.debug(file.getName() + " -> " + file.toPath());
        // Comienza la reproducción
        sonido.start();
        // Espera mientras se esté reproduciendo.
        Thread.sleep(1500);
        // Se cierra el clip.
        sonido.close();
    }
    
    /**
     * ejecutarSonidoBios
     * @author ASOSA
     * @since 22.06.2015
     * @param archivo
     */
    public static void ejecutarSonidoBios(String archivo) {
        String[] valor = new String[2];
        if (archivo.equals(ConstantsRecepCiega.ARCHIVO_ATENDIDO)) {
            Beep clase = new Beep();             
            valor[0] = "1000";
            valor[1] = "300";
            clase.main(valor);
            valor[0] = "900";
            valor[1] = "200";
            clase.main(valor);             
        } else {
            Beep clase = new Beep();             
            valor[0] = "340";
            valor[1] = "300";
            clase.main(valor);
            valor[0] = "300";
            valor[1] = "200";
            clase.main(valor); 
        }
    }
    
    public static void imprVouBultoCerrado02(String corrBulto) {
        try {
            String textoTotal = DBRecepCiega.imprVouBultoCerrado02(corrBulto);
            /*PrintConsejo.imprimirCode128(textoTotal, 
                                         VariablesPtoVenta.vImpresoraActual, 
                                         VariablesPtoVenta.vTipoImpTermicaxIp, 
                                         dniCli,
                                         "PTOS"); */
            log.info(textoTotal);
            PrintConsejo.imprimirHtml(textoTotal, 
                                      VariablesPtoVenta.vImpresoraActual, 
                                      VariablesPtoVenta.vTipoImpTermicaxIp);
        } catch (Exception e) {
            log.error("",e);
        }        
    }
    
    /**
     * Obtener el indicador del local si se utilizara el beep de la placa o no.
     * @author ASOSA
     * @since 22.06.2015
     * @return
     */
    public static boolean isLocalBeepBios(){
        boolean flag = false;
        String ind = "";
        try {
            ind = DBRecepCiega.getIndLocalBeepBios();
            if (ind.equals("S")) {
                flag = true;
            }
        } catch(Exception e) {
            log.error("",e);
        }
        return flag;
    }
    
    //FIN ASOSA - 22.06.2015 - RCIEGAM
    
    /*** INICIO CCASTILLO 16/08/2016 ***/
    /**
     * Imprimir voucher de diferencia en termico
     * @author CCASTILLO
     * @since 16.08.2016
     * @return
     */
    public static void imprimeVoucherDiferenciasTermico() {
        try{
        
        List listComanda = new ArrayList();
        listComanda = DBRecepCiega.getDatosDiferencias();

        UtilityImpCompElectronico impr = new UtilityImpCompElectronico();
        boolean rest =  impr.impresionTermica(listComanda, null);
            
        }catch(Exception ex){
                log.error("", ex);
        }
    
    }
    
    /*** FIN CCASTILLO 16/08/2016 ***/
    
}


