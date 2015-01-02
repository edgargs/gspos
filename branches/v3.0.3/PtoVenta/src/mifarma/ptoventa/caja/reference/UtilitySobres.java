package mifarma.ptoventa.caja.reference;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.DlgControlSobres;
import mifarma.ptoventa.caja.DlgIngresoSobreParcial;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilitySobres.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ  07.06.2010   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */
public class UtilitySobres {

    private static final Logger log = LoggerFactory.getLogger(UtilitySobres.class);

    private static int numeroCorrel = 1;

    /**
     * Constructor
     */

    public UtilitySobres() {
    }

    /**
     * Metodo que realiza la accion del sobre
     * @author dubilluz
     * @since  07.06.2010
     * @param pTipoAccion
     * @param pSec
     * @param pCodigoSobre
     * @param pSecMovCaja
     * @param pCodFormaPago
     * @param pTipMoneda
     * @param pMontEntrega
     * @param pMontEntregaTot
     * @param jfoco
     */
    public static void accionSobre(String pTipoAccion, String pSec, String pCodigoSobre, String pSecMovCaja,
                                   JTable jfoco, DlgControlSobres pDialogo, Frame parent) {

        String pEstadoSobre = "X";
        String pResultadoAccion = "X";
        String pMensaje = "";
        boolean pDefinido = true;
        boolean pNuevo = false;
        boolean pRealizaAccion = false;
        if (pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_INGRESO)) {
            pMensaje = "agregar";
            pNuevo = true;
        } else {
            if (pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_MODIFICA)) {
                pMensaje = "modificar";
            } else {
                if (pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_ELIMINA)) {
                    pMensaje = "eliminar";
                } else {
                    if (pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_APRUEBA)) {
                        pMensaje = "aprobar";
                    } else {
                        pMensaje = "Acción no definida verifique.";
                        pDefinido = false;
                    }
                }
            }
        }

        if (pDefinido) {

            if (pNuevo) {
                if (getSecMovCaja()) {
                    pRealizaAccion = mostrarIngresoSobresParciales(parent);
                } else {
                    pRealizaAccion = false;
                    FarmaUtility.showMessage(pDialogo, "No puede agregar sobres por no tener caja abierta.", jfoco);
                }
            } else {
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(pDialogo,
                                                                                "¿Esta seguro de " + pMensaje +
                                                                                " sobre?")) {
                    VariablesSobre.vSecMovCaja = pSecMovCaja.trim();
                    pRealizaAccion = true;
                } else
                    pRealizaAccion = false;
            }

            // inicio de realizar la accion seleccionada
            if (pRealizaAccion) {
                try {

                    if (!pNuevo) {
                        pEstadoSobre =
                                DBCaja.getEstadoBloqueo(ConstantsSobres.TIPO_TEMPORAL, pSec, pCodigoSobre, pSecMovCaja);
                    }

                    log.info("VariablesSobre.vSecMovCaja" + ":" + VariablesSobre.vSecMovCaja);
                    log.info("VariablesSobre.vCodFormaPagoTmp" + ":" + VariablesSobre.vCodFormaPagoTmp);
                    log.info("VariablesSobre.vCodTipoMon" + ":" + VariablesSobre.vCodTipoMon);
                    log.info("VariablesSobre.pMontEntrega" + ":" + VariablesSobre.vValMontoPagadoTmp);
                    log.info("VariablesSobre.pMontEntregaTot" + ":" + VariablesSobre.vValTotalPagadoTmp);

                    pResultadoAccion =
                            DBCaja.getRealizaAccionSobreTMP_02(pTipoAccion, pSec, pCodigoSobre, VariablesSobre.vSecMovCaja,
                                                               VariablesSobre.vCodFormaPagoTmp,
                                                               VariablesSobre.vCodTipoMon,
                                                               VariablesSobre.vValMontoPagadoTmp,
                                                               VariablesSobre.vValTotalPagadoTmp,
                                                               VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE);
                    //-----@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-----//
                    //RHERRERA 16.09.2014
                    if (VariablesPtoVenta.vIndTico.equals("S")) {
                        obtenerSobres(); ////rherrera 28.08.2014
                    }
                    //-----@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-----//
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(pDialogo, "Se pudo " + pMensaje + " sobre con éxito", jfoco);
                    /*INICION DE IMPRESION DE SOBRES EN VOUCHER ASOSA, 26.07.2010*/
                    imprimeSobresDeclarados(null, VariablesSobre.vSecMovCaja, pResultadoAccion);
                    /*FIN DE IMPRESION DE SOBE4S EN VOUCHER*/

                } catch (SQLException e) {
                    FarmaUtility.liberarTransaccion();
                    log.error("", e);
                    if (e.getErrorCode() > 20000) {
                        FarmaUtility.showMessage(pDialogo,
                                                 e.getMessage().substring(10, e.getMessage().indexOf("ORA-06512")),
                                                 jfoco);
                    } else {
                        FarmaUtility.showMessage(pDialogo, "Ocurrió un error al " + pMensaje + " sobre.\n" +
                                e, jfoco);
                    }

                } catch (Exception q) {
                    FarmaUtility.liberarTransaccion();
                    log.error("", q);
                    FarmaUtility.showMessage(pDialogo, "Ocurrió un error al " + pMensaje + " sobre.\n" +
                            q, jfoco);
                } finally {
                    log.debug("Fin de Metodo UtilitySobres.accionSobre");
                }
            }
            // fin de realizar la accion seleccionada

        } else {
            FarmaUtility.showMessage(pDialogo, pMensaje, jfoco);
        }
    }


    private static boolean mostrarIngresoSobresParciales(Frame parent) {
        DlgIngresoSobreParcial dlgsobreparciales = new DlgIngresoSobreParcial(parent, "", true);
        dlgsobreparciales.setVisible(true);
        return FarmaVariables.vAceptar;
    }

    private static boolean getSecMovCaja() {

        try {
            VariablesSobre.vSecMovCaja = DBCaja.getSecMovCaja();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static double getTipodeCambio() {
        double ptipoCambio = 0.0;
        try {
            ptipoCambio = DBCaja.getTipoCambioDU();
        } catch (SQLException e) {
            ptipoCambio = 0.0;
        }

        log.debug("ptipoCambio:" + ptipoCambio);
        return ptipoCambio;
    }

    /**
     * @author ASOSA
     * @since 26.07.2010
     */
    public static void imprimeSobresDeclarados(JDialog pDialogo, String pSecMovCaja, String pSecSobre) {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        String pIndProsegur = FarmaConstants.INDICADOR_N;
        boolean indImp = false;
        //if(servicio != null)
        //{
        try {
            //SEG_F_CHAR_IND_PROSEGUR
            pIndProsegur = FarmaConstants.INDICADOR_S;
            if (pIndProsegur.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                String pCodSobre = "";
                String vIndImpre = FarmaConstants.INDICADOR_S;
                log.debug("vIndImpre :" + vIndImpre);
                if (!vIndImpre.equals("N")) {
                    ArrayList pLista = new ArrayList();
                    DBCajaElectronica.getSobreDeclarados_02(pSecMovCaja, pLista, pSecSobre);
                    for (int f = 0; f < pLista.size(); f++) {
                        //pCodSobre = FarmaUtility.getValueFieldArrayList(pLista,f,0);
                        String html = DBCajaElectronica.getHtmlSobreDeclarados_02(pSecMovCaja, pSecSobre);
                        PrintConsejo.imprimirHtml(html.trim(), VariablesPtoVenta.vImpresoraActual,
                                                  VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                        PrintConsejo.imprimirHtml(html.trim(), VariablesPtoVenta.vImpresoraActual,
                                                  VariablesPtoVenta.vTipoImpTermicaxIp);
                        indImp = true;
                    }
                    if (indImp)
                        FarmaUtility.showMessage(pDialogo, "Recoger Voucher de sobres declarados.", null);
                    //break;
                }
                //}
                //}
            }

        } catch (SQLException sqlException) {
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de los sobres a imprimir.", null);
        }

        //}
    }

    /**
     * Reimprime un voucher en control de sobres
     * @author ASOSA
     * @since 04.08.2010
     * @param pSecMovCaja
     * @param pSecSobre
     */
    public static void reimprimirSobre(JDialog pDialogo, String pSecMovCaja, String pSecSobre) {
        boolean indImp = false;
        try {
            String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
            String vIndImpre = FarmaConstants.INDICADOR_S;
            if (!vIndExisteImpresora.trim().equalsIgnoreCase("")) {
                String html = DBCajaElectronica.getHtmlSobreDeclarados_03(pSecMovCaja, pSecSobre);
                PrintConsejo.imprimirHtml(html.trim(), VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                indImp = true;
                if (indImp)
                    FarmaUtility.showMessage(pDialogo, "Recoger Voucher de sobres declarados.", null);
            }
        } catch (SQLException sqlException) {
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de los sobres a imprimir.", null);
        }
    }

    /**
     * Reimprime un voucher en forma de pago entrega
     * @author ASOSA
     * @since 09.08.2010
     * @param pSecMovCaja
     * @param pSecSobre
     */
    public static void reimprimirSobre_02(JDialog pDialogo, String pSecMovCaja, String pSecSobre) {
        boolean indImp = false;
        try {
            String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
            String vIndImpre = FarmaConstants.INDICADOR_S;
            if (!vIndExisteImpresora.trim().equalsIgnoreCase("")) {
                String html = DBCajaElectronica.getHtmlSobreDeclarados_04(pSecMovCaja, pSecSobre);
                PrintConsejo.imprimirHtml(html.trim(), VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                indImp = true;
                if (indImp)
                    FarmaUtility.showMessage(pDialogo, "Recoger Voucher de sobres declarados.", null);
            }
        } catch (SQLException sqlException) {
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de los sobres a imprimir.", null);
        }
    }

    /**
     * METODOS REMITOS MARKET obtenerSobres()
     * @author RHERRERA
     * @return lista sobres e inserta al local farma
     */

    public static void obtenerSobres() {
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        String indConexionTico = "N";
        ArrayList pLista = new ArrayList();
        try {
            indConexionTico = (facadeRecaudacion.validarConexionTICO()) ? "S" : "N";
            //indConexionTico="N";

            if (indConexionTico.equals("S")) {
                DBCajaElectronica.obtieneSobrePendientes(pLista);
                DBCajaElectronica.insertSobresTICO(FarmaConstants.INDICADOR_S, pLista);
            }

        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * METODOS REMITOS MARKET eliminarSobre(String codSobre)
     * @author RHERRERA
     * @return deshabilita sobre en local Farma
     */
    public static String eliminarSobre(String codSobre) {
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        String indConexionTico = "N";
        String indelim = "N";
        String cFecVta;
        try {
            indConexionTico = (facadeRecaudacion.validarConexionTICO()) ? "S" : "N";


            if (indConexionTico.equals("S")) {
                cFecVta = DBCajaElectronica.obtenerFecVta(codSobre);
                indelim = DBCajaElectronica.eliminarSobresTICO(FarmaConstants.INDICADOR_S, codSobre, cFecVta);


            }

        } catch (Exception e) {
            log.error("", e);
            indelim = "N";
        }
        return indelim;
    }

    /**
     * METODOS REMITOS MARKET insertCierreDiaMarket(String fecCierre,
                                             String usuLogin )
     * @author RHERRERA
     * @return Envia el dato de cierre de dia TICO al Market.
     */
    public static void insertCierreDiaMarket(String fecCierre, String usuLogin) {
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        String indConexionTico = "N";
        try {
            indConexionTico = (facadeRecaudacion.validarConexionTICO()) ? "S" : "N";

            if (indConexionTico.equals("S")) {
                DBCajaElectronica.insertCierreDiaMarket(FarmaConstants.INDICADOR_S, fecCierre, usuLogin);

            }
        } catch (Exception e) {
            log.error("", e);
            //FarmaUtility.liberarTransaccion();
        }
    }

    public static void traerSobresTico() {
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        String indConexionTico = "N";

        ArrayList pListaSobres = new ArrayList();

        ArrayList localMarket = VariablesPtoVenta.vLocarMarketH;
        for (int i = 0; i < localMarket.size(); i++) {
            try {

                String cCodLocal = ((String)((ArrayList)localMarket.get(i)).get(0)).trim();
                FarmaVariables.vIpServidorTico = ((String)((ArrayList)localMarket.get(i)).get(1)).trim();

                indConexionTico = (facadeRecaudacion.validarConexionTICO()) ? "S" : "N";

                if (indConexionTico.equals("S")) {
                    DBCajaElectronica.obtieneSobreTicoPendiente(FarmaConstants.INDICADOR_S, pListaSobres, cCodLocal);
                    DBCajaElectronica.insertSobresTICOPendiente(pListaSobres, cCodLocal);
                    FarmaUtility.aceptarTransaccion();
                }

            } catch (Exception e) {
                log.error("", e);
                FarmaUtility.liberarTransaccion();
            }


        }

    }

    public static void updateRemitoTico(ArrayList pLista, String remito, String precinto) {
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        String indConexionTico = "N";
        try {
            indConexionTico = (facadeRecaudacion.validarConexionTICO()) ? "S" : "N";

            if (indConexionTico.equals("S")) {
                ArrayList localMarket = VariablesPtoVenta.vLocarMarketH;
                for (int i = 0; i < localMarket.size(); i++) {
                    String cCodLocal = ((String)((ArrayList)localMarket.get(i)).get(0)).trim();
                    DBCajaElectronica.updateSobresTico(FarmaConstants.INDICADOR_S, pLista, remito, precinto,
                                                       cCodLocal);
                }
                FarmaUtility.aceptarTransaccion();
            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.liberarTransaccion();
        }
    }

    public static void actualizarProceso(String indProceso) {

        try {
            DBCajaElectronica.actualizarProce(indProceso);
        } catch (SQLException e) {
            log.error("", e);
        }
    }

}
