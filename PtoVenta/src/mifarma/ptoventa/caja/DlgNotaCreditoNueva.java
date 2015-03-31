package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;
import mifarma.electronico.epos.reference.EposConstante;
import mifarma.electronico.impresion.dao.ConstantesDocElectronico;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.FacadeConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgNotaCreditoNueva extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private static final Logger log = LoggerFactory.getLogger(DlgAnularPedidoComprobante.class);
    private final static int COL_COD_PROD = 0;
    private final static int COL_DESC_PROD = 1;
    private final static int COL_UND_MED = 2;
    private final static int COL_NOM_LAB = 3;
    private final static int COL_CANT_DISP = 4;
    private final static int COL_PREC_VTA = 5;
    private final static int COL_CANT_NC = 6;
    private final static int COL_FRAC_VTA = 7;
    private final static int COL_PREC_LTA = 8;
    private final static int COL_VAL_IGV = 9;
    private final static int COL_SEC_DET = 10;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabelFunction lblF11 = new JLabelFunction();

    private FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
    private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();

    private String[] pListaPedidos; //kmoncada 26.06.2014 almacena las notas de credito generadas en la anulacion

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgNotaCreditoNueva() {
        this(null, "", false);
    }

    public DlgNotaCreditoNueva(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(705, 346));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Nueva Nota Credito");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 15, 680, 25));
        scrListaProductos.setEnabled(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(590, 290, 85, 20));
        btnRelacionProductos.setText("Relacion de Productos de Nota Credito");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 235, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.setActionCommand("Relacion de Productos de Nota Credito");
        btnRelacionProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionProductos_keyPressed(e);
            }
        });
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(445, 290, 135, 20));
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.NORTH);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        this.getContentPane().add(scrListaProductos, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsCaja.columnsListaProductosNotaCreditoNueva, ConstantsCaja.defaultValuesListaProductosNotaCreditoNueva,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                    ConstantsCaja.columnsListaProductosNotaCreditoNueva);
        cargaListaProductos();
    }

    public void cargaListaProductos() {
        try {
            DBCaja.getListaDetalleNotaCredito(tableModel, VariablesCaja.vNumPedVta_Anul, VariablesCaja.vTipComp_Anul,
                                              VariablesCaja.vNumComp_Anul);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al listar detalle nota credito :\n" +
                    e.getMessage(), null);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnRelacionProductos);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if (FarmaVariables.vTipCambio == 0) {
            FarmaUtility.showMessage(this,
                                     "ATENCI�N: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acci�n",
                                     null);
            cerrarVentana(false);
        } else {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(btnRelacionProductos);
            tblListaProductos.setEnabled(false);
            presionarF11();
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void presionarF11() {
        if (validaDatos()) {
            //Modificado por FRAMIREZ 29.02.2012 se agrego para generar la nota credito del convenio BTLMF
            //if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "�Est� seguro de generar la Nota Credito?"))
            //{

            boolean estaGrabado = false;

            //ini-Agregado Por FRAMIREZ 28.02.2012 metodo que genera nota credito convenio BTLMF
            Map vtaPedido = (Map)UtilityConvenioBTLMF.obtieneConvenioXpedido(VariablesCaja.vNumPedVta_Anul, this);
            String indConvenioBTLMF = (String)vtaPedido.get("IND_CONV_BTL_MF");
            boolean esConvenioBTLMF = UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null);

            if (esConvenioBTLMF && indConvenioBTLMF != null && indConvenioBTLMF.equals("S")) {
                boolean esCompCredito = UtilityConvenioBTLMF.esCompCredito(this);
                log.debug("Es comprobante Cr�dito " + esCompCredito);

                if (esCompCredito) {

                    try {
                        String indConexionRac = "N";
                        try {
                            indConexionRac = (facadeRecaudacion.validarConexionRAC()) ? "S" : "N";
                        } catch (Exception e) {
                            ;
                        }
                        log.debug("indConexionRac :" + indConexionRac);
                        if (indConexionRac.equals("S")) {
                            //String pedidoAnuladoRac = FarmaConstants.INDICADOR_S;
                            VariablesCaja.vNumPedVta = VariablesCaja.vNumPedVta_Anul;
                            String tipCompPagoAnul = "";
                            String numCompPagoAnul = "";
                            if (UtilityConvenioBTLMF.obtieneCompPago(new JDialog(), "", null)) {

                                for (int j = 0; j < VariablesConvenioBTLMF.vArray_ListaComprobante.size(); j++) {
                                    VariablesConvenioBTLMF.vTipoCompPago =
                                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(2)).trim();

                                    VariablesConvenioBTLMF.vNumCompPago =
                                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(0)).trim();

                                    //anula el pedido en el RAC solo la guia
                                    // KMONCADA 23.10.2014 REALIZARA LA ANULACION DE LAS GUIAS DE AL FINAL DEL PROCESO
                                    /*if (VariablesConvenioBTLMF.vTipoCompPago.equals(Constante.tipoDocumentoFarma.GRL)){//LTAVARA 11.09.2014 SOLO SE DEBE ANULAR LAS GUIAS
                                           pedidoAnuladoRac = DBConvenioBTLMF.anularPedidoRac(FarmaConstants.INDICADOR_S, FarmaConstants.INDICADOR_N);
                                        }*/

                                }
                                // KMONCADA 30.10.2014 GRABA TEMPORALMENTE LOS COMP A ANULAR
                                tipCompPagoAnul = VariablesConvenioBTLMF.vTipoCompPago;
                                numCompPagoAnul = VariablesConvenioBTLMF.vNumCompPago;
                            }
                            // KMONCADA 10.12.2014 ANULARA LOS DOCUMENTOS EN RAC
                            String pedidoAnuladoRac = "";
                            // KMONCADA 30.10.2014 ASIGNA LOS COMP. A ANULAR
                            VariablesConvenioBTLMF.vTipoCompPago = tipCompPagoAnul;
                            VariablesConvenioBTLMF.vNumCompPago = numCompPagoAnul;
                            pedidoAnuladoRac =
                                    DBConvenioBTLMF.anularPedidoRac(FarmaConstants.INDICADOR_S, FarmaConstants.INDICADOR_N);
                            log.info("anula las Guias en RAC");


                            //if (pedidoAnuladoRac.equals(FarmaConstants.INDICADOR_S)) {
                            estaGrabado = grabarBTLMF(true); // Grabar en PtoVenta
                            log.debug("graboo?" + estaGrabado);
                            String resMatriz = "";
                            if (estaGrabado) {
                                log.info("Grabo en RAC las NC");

                                //KMONCADA 26.06.2014 GRABA CONEXION DIRECTA PERO DE LOS NUM_PEDIDOS DE NOTA DE CREDITO GENERADOS
                                for (int k = 0; k < pListaPedidos.length; k++) { //kmoncada
                                    String numPedVtaNC = pListaPedidos[k].toString();
                                    resMatriz =
                                            facadeConvenioBTLMF.grabarTemporalesRAC(numPedVtaNC, FarmaConstants.INDICADOR_S);
                                    if (resMatriz.equals("S")) {
                                        log.debug("-------------------GRABO EXITOSAMENTE MATRIZ-------- ");
                                        String rspta =
                                            DBConvenioBTLMF.grabarCobrarPedidoRacNC2(numPedVtaNC, FarmaConstants.INDICADOR_S);
                                        if ("S".equalsIgnoreCase(rspta)) {
                                            DBConvenioBTLMF.actualizaFechaProcesoRac2(numPedVtaNC);
                                        } else {
                                            FarmaUtility.showMessage(this,
                                                                     "No se pudo anular el pediddo Convenio en el RAC",
                                                                     tblListaProductos);
                                        }
                                    }
                                }
                            } else {
                                FarmaUtility.liberarTransaccion();
                                throw new Exception("Error al procesar la NC en el local.");
                            }
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "No puede anular el pedido, porque no existe una conexion con el RAC",
                                                     tblListaProductos);
                        }
                    } catch (Exception sqle) {
                        log.error("", sqle);
                        estaGrabado = false;
                        FarmaUtility.showMessage(this, "Error al procesar NC Convenio.\n" +
                                sqle.getMessage(), tblListaProductos);
                    } finally {
                        FarmaConnectionRemoto.closeConnection();
                    }
                } else {
                    estaGrabado = grabarBTLMF(false);
                }
            } else {
                estaGrabado = grabar();
            }
            log.debug("Genero Nota credito satisfactoriamente:" + estaGrabado);

            //Fin
            if (estaGrabado) {
                // 09.10.2014 kmoncada, confirmamos toda la transaccion de anulacion
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "�Nota Credito generada satisfactoriamente!", btnRelacionProductos);
                //JCHAVEZ 10.07.2009.sn
                try {
                    if (esConvenioBTLMF && indConvenioBTLMF != null && indConvenioBTLMF.equals("S")) {
                        if (UtilityConvenioBTLMF.imprimeTicketBTLMF(this, VariablesCaja.vNumPedVta_Anul,
                                                                    VariablesCaja.vNumCaja,
                                                                    VariablesCaja.vNumTurnoCaja)) {
                            FarmaUtility.showMessage(this, "El ticket se ha reimpreso con �xito .", null);
                        }
                    } else {
                        if (UtilityCaja.getImpresionTicketAnulado(this, VariablesCaja.vNumPedVta_Anul, "",
                                                                  VariablesCaja.vNumCaja,
                                                                  VariablesCaja.vNumTurnoCaja)) {
                            FarmaUtility.showMessage(this, "El ticket se ha reimpreso con �xito .", null);
                        }
                    }
                    FarmaUtility.aceptarTransaccion();
                } catch (Exception e) {
                    log.error("", e);
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                            //"dubilluz",
                            VariablesPtoVenta.vDestEmailErrorAnulacion,
                            "Error de Impresi�n Ticket Anulado Nota de Credito",
                            "Error de Impresi�n Nota de Credito Anulado",
                            "Error al imprimir ticket Anulado :<br>" + "Correlativo : " +
                            VariablesCaja.vNumPedVta_Anul + "<br>" + "Error: " + e,
                            //"joliva;operador;daubilluz@gmail.com");
                            "");
                    FarmaUtility.showMessage(this, "Error .\n" +
                            e.getMessage(), null);
                }
                //JCHAVEZ 10.07.2009.en
                cerrarVentana(true);
            } else {
                // KMONCADA 09.10.2014 en caso de presentarse algun tipo de error
                FarmaUtility.liberarTransaccion();
                cerrarVentana(true); // LTAVARA 15.09.2014
            }
        }
    }

    private void presionarEnter() {
        if (tblListaProductos.getSelectedRow() > -1) {
            cargarCabecera();
            DlgNotaCreditoIngresoCantidad DlgNotaCreditoIngresoCantidad =
                new DlgNotaCreditoIngresoCantidad(myParentFrame, "", true);
            DlgNotaCreditoIngresoCantidad.setVisible(true);
            if (FarmaVariables.vAceptar) {
                actualizarProducto();
                FarmaVariables.vAceptar = false;
            }
        } else
            FarmaUtility.showMessage(this, "Debe seleccionar un producto", btnRelacionProductos);
    }

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
            presionarF11();
        //  else if(e.getKeyCode() == KeyEvent.VK_ENTER && (VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA)))
        //     presionarEnter();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cargarCabecera() {
        int vFila = tblListaProductos.getSelectedRow();
        VariablesCaja.vCodProd_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos, vFila, COL_COD_PROD);
        VariablesCaja.vNomProd_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos, vFila, COL_DESC_PROD);
        VariablesCaja.vUnidMed_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos, vFila, COL_UND_MED);
        VariablesCaja.vNomLab_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos, vFila, COL_NOM_LAB);
        VariablesCaja.vCant_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos, vFila, COL_CANT_DISP);
        VariablesCaja.vValFrac_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos, vFila, COL_FRAC_VTA);
        VariablesCaja.vCantIng_Nota = FarmaUtility.getValueFieldJTable(tblListaProductos, vFila, COL_CANT_NC);
    }

    private void actualizarProducto() {
        int vFila = tblListaProductos.getSelectedRow();
        tblListaProductos.setValueAt(VariablesCaja.vCantIng_Nota, vFila, COL_CANT_NC);
        tblListaProductos.repaint();
    }

    private boolean validaDatos() {
        boolean retorno = true;
        if (tblListaProductos.getRowCount() == 0) {
            FarmaUtility.showMessage(this, "No ha ingresado productos a esta Nota Credito.", btnRelacionProductos);
            retorno = false;
        } else if (FarmaUtility.sumColumTable(tblListaProductos, COL_CANT_NC) == 0 &&
                   (VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA))) {
            FarmaUtility.showMessage(this, "No ha ingresado productos a esta Nota Credito.", btnRelacionProductos);
            retorno = false;
        }
        return retorno;
    }


    private boolean grabarBTLMF(boolean conexionRAC) {
        boolean retorno = true;
        //Valores totales
        double totalBruto = 0.00;
        double totalDscto = 0.00;
        double totalIGV = 0.00;
        double totalNeto = 0.00;
        double totalNetoAux = 0.00;
        //double redondeo = 0.00;
        int cantidad = 0;
        String pDatosDel = FarmaConstants.INDICADOR_N;
        try {
            pDatosDel = DBCaja.getDatosPedDelivery(VariablesCaja.vNumPedVta_Anul);
            //Esta decision fue tomada por gerencia
            //dubilluz 02.12.2008
            DBCaja.activarCuponesUsados(VariablesCaja.vNumPedVta_Anul);
            log.debug("-----");
            DlgDetalleAnularPedido dlgDetalleAnularPedido = new DlgDetalleAnularPedido(myParentFrame, "", true, true);
            dlgDetalleAnularPedido.setVisible(true);

            if (FarmaVariables.vAceptar) {
                /**Proceso de anulacion de Pedido Recarga Virtual
                 * LTAVARA 10.09.2014  inicio
                 * Copia de dlgDetalleAnulacionPedido - metodo de anulaci�n
                * */

                //INI ASOSA - 11/12/2014 - RECAR
                boolean isAnulOk = dlgDetalleAnularPedido.isAnul();

                if (!isAnulOk) {
                    return false;
                }
                //FIN ASOSA - 11/12/2014 - RECAR

                if (!dlgDetalleAnularPedido.anularTransaccionCMR()) {
                    retorno = false;
                }

                /**Proceso de anulacion de Pedido Recarga Virtual
                * LTAVARA 10.09.2014  FIN
                * Copia de dlgDetalleAnulacionPedido - metodo de anulaci�n
                * */

                /** Anular lo acumulado si es fidelizado
                 * LTAVARA 10.09.2014  inicio
                * */
                if (dlgDetalleAnularPedido.anularPedidofidelizado(FarmaConstants.INDICADOR_S)) { // LTAVARA 10.09.2014
                    log.debug("Valor de vMotivoAnulacion dentro del metodo grabar: " + VariablesCaja.vMotivoAnulacion);

                    (new FacadeRecaudacion()).obtenerTipoCambio();
                    String tipoCambio = FarmaVariables.vTipCambio + "";

                    // NUEVO METODO DE GENERACION DE NOTA DE CREDITO
                    // REVISAR LA GENERACION COMPLETA DE LOS PEDIDOS CORRECTAMENTE
                    // DUBILLUZ 27.09.2013
                    /********************* INICIO DE CAMBIO ********************************/
                    //dubilluz 27.09.2013 retorna el conjunto de pedidos generados por la NOTA DE CREDITO
                    pListaPedidos =
                            DBCaja.agregarCabeceraNotaCredito(VariablesCaja.vNumPedVta_Anul, tipoCambio, VariablesCaja.vMotivoAnulacion).split("@");
                    //CHUANES
                    //SE OBTIENE LA LISTA QUE ALAMACENA LOS NUMEROS DE PEDIDOS
                    ConstantesDocElectronico.lstPedidos = pListaPedidos;
                    log.debug(">> cantidad de pedidos generados de NC>>" + pListaPedidos.length);
                    String numera = "";
                    boolean nroCorrecto = true;
                    for (int i = 0; i < pListaPedidos.length; i++) {
                        numera = pListaPedidos[i].toString();
                        // los parametros de monto,cantidad,total,items NO NECESITA porque se va sacar todo la copia del ORIGINAL de La NC
                        String nroNC =
                            DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul, numera, "0", "0", "0.0",
                                                             i + ""); //ERIOS 2.4.3 Indica el secuencial de NC
                        if ("N".equalsIgnoreCase(nroNC)) {
                            nroCorrecto = nroCorrecto && false;
                            log.error("ERROR AL GENERAR LA NOTA DE CREDITO DEL PEDIDO " +
                                      VariablesCaja.vNumPedVta_Anul);
                        } else {
                            // KMONCADA 18.11.2014 NO SOLICITARA NRO NC ELECTRONICA EN CASO DE GUIAS
                            if (!"G".equalsIgnoreCase(nroNC)) {
                                /* * @author LTAVARA
                                     * Validar si la variable COMPROBANTE ELECTRONICO esta activo, para genera el Numero de comprobante e
                                     * @since 04/09/2014
                                     * */
                                if (UtilityEposTrx.validacionEmiteElectronico()) {
                                    VariablesCaja.vNumPedVta =
                                            numera; //LTAVARA 04.09.2014 SET AL NUMERO DE COMPROBANTE
                                    //obtiene los comprobantes del pedido
                                    if (UtilityCaja.obtieneCompPago()) {
                                        for (int j = 0; j < VariablesVentas.vArray_ListaComprobante.size(); j++) {
                                            //obtener  secComprobante por documento
                                            VariablesConvenioBTLMF.vSecCompPago =
                                                    ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(1)).trim();
                                            VariablesConvenioBTLMF.vTipoCompPago =
                                                    ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(2)).trim();
                                            // obtener el numero del comprobante electr�nico
                                            VariablesCaja.vNumCompImprimir =
                                                    UtilityEposTrx.getNumCompPagoE(VariablesConvenioBTLMF.vTipoCompPago,
                                                                                   VariablesCaja.vNumPedVta,
                                                                                   VariablesConvenioBTLMF.vSecCompPago,
                                                                                   "", conexionRAC);
                                            // KMONCADA 20.10.2014 VALIDACION DE DUPLICIDAD DE COMPROBANTES ELECTRONICOS
                                            // DUPLICADO EN LOCAL
                                            /*if ("L".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)) {
                                                throw new Exception("El n�mero de comprobante electronico " +
                                                                    VariablesCaja.vNumCompImprimir.substring(1) +
                                                                    " \n ya fue asignado en el local, comuniquese con Sistemas...!!");
                                            }
                                            // DUPLICADO EN RAC
                                            if ("R".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)) {
                                                throw new Exception("El n�mero de comprobante electronico " +
                                                                    VariablesCaja.vNumCompImprimir.substring(1) +
                                                                    " \n ya se encuentra registrado en RAC, comuniquese con Sistemas...!!");
                                            }
                                            //mensaje de error
                                            if (VariablesCaja.vNumCompImprimir.startsWith("E")) {
                                                throw new Exception(VariablesCaja.vNumCompImprimir);
                                            }
                                            if ("".equals(VariablesCaja.vNumCompImprimir) || "0".equals(VariablesCaja.vNumCompImprimir)) {
                                                throw new Exception("Error al cobrar pedido !\nEl numero de comprobante electronico no se encontro !");
                                            }*/
                                        }

                                    }
                                } else { //fin validacion electronica
                                    nroCorrecto =
                                            nroCorrecto && !DBConvenioBTLMF.isExisteComprobanteEnRAC(FarmaVariables.vCodGrupoCia,
                                                                                                     FarmaVariables.vCodLocal,
                                                                                                     ConstantsVentas.TIPO_COMP_NOTA_CREDITO,
                                                                                                     nroNC, "N");
                                }
                            } else {
                                nroCorrecto = nroCorrecto && true;
                            }
                        }
                        // SI UN NUMERO DE NC SE ENCUENTRA DUPLICADO SE CANCELA TODO PROCESO.
                        if (!nroCorrecto) {
                            i = pListaPedidos.length + 1;
                        }
                    }

                    //cobrarPedido(numera);
                    // KMONCADA 22.10.2014 ASIGNA EL RESULTADO DE LA VALIDACION DE NRO NC.
                    //retorno = true;
                    retorno = nroCorrecto;
                    if (retorno) {
                        FarmaUtility.aceptarTransaccion();
                    } else {
                        throw new Exception("Error al anular el pedido");
                    }

                    /**Actualiza el pedido virtual con el trace de anulaci�n
                      * LTAVARA  11.09.2014 INICIO
                      * Copia de dlgDetalleAnulacionPedido - metodo de anulaci�n
                      * **/
                    if (VariablesCaja.vIndPedidoConProdVirtual && !VariablesCaja.vIndAnulacionConReclamoNavsat) {
                        UtilityCaja.actualizaInfoPedidoVirtualAnulado(VariablesCaja.vNumPedVta_Anul);
                        /*try {
                            UtilityCaja.actualizaInfoPedidoVirtualAnulado(VariablesCaja.vNumPedVta_Anul);
                            FarmaUtility.aceptarTransaccion();
                        } catch (SQLException sql) {
                            FarmaUtility.liberarTransaccion();
                            //FarmaUtility.showMessage(this,"Error al actualizar informacion del pedido virtual anulado.\n" +sql.getMessage(), this);
                            log.error("Error al actualizar informacion del pedido virtual anulado.\n" +sql.getMessage());
                        }*/
                    }
                    /**
                      * LTAVARA 11.09.2014 FIN
                      * Copia de dlgDetalleAnulacionPedido - metodo de anulaci�n
                      * **/


                    /********************* FIN   DE CAMBIO ********************************/

                    /*
                  * metodo antiguo de generacion de nota de credito
                  * se comento por dubilluz 27.09.2013
                  *
                   String numera = DBCaja.agregarCabeceraNotaCredito(VariablesCaja.vNumPedVta_Anul,tipoCambio,VariablesCaja.vMotivoAnulacion);
                   xxx("Numera JCORTEZ: " + numera);

                    for(int i=0;i<tblListaProductos.getRowCount();i++)
                    {

                      String codProd = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_COD_PROD);
                      String secPedDet = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_SEC_DET);
                      String precLta = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_PREC_LTA);
                      String precVta = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_PREC_VTA);
                      String valIgv = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_VAL_IGV);
                      String cantDisp = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_CANT_DISP);
                      String cantNC = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_CANT_NC);

                        //JCORTEZ 23.04.2009
                        //Se considera grabar del igual forma tipo de comprobante TICKET al generar el detalle de la nota de credito
                      if(VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA)||
                          VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
                      {
                        //ERIOS 29.05.2008 Se devuelve todos el detalle del comprobante
                        cantidad = Integer.parseInt(cantDisp.trim());

                        totalBruto += FarmaUtility.getDecimalNumber(precLta) * cantidad;
                        totalNetoAux = FarmaUtility.getDecimalNumber(precVta) * cantidad;
                        totalNeto += FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNetoAux,2));
                        totalIGV   += (FarmaUtility.getDecimalNumber(precVta) - (FarmaUtility.getDecimalNumber(precVta) / ( 1 + (FarmaUtility.getDecimalNumber(valIgv) / 100)))) * cantidad;

                        DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul,numera,codProd,cantDisp,FarmaUtility.formatNumber(totalNetoAux),secPedDet);
                      }else if(!cantNC.equals("0"))
                      {
                        //ERIOS 29.05.2008 Solo se devuelve aquellos registros se trabajo.
                        cantidad = Integer.parseInt(cantNC);

                        totalBruto += FarmaUtility.getDecimalNumber(precLta) * cantidad;
                        totalNetoAux = FarmaUtility.getDecimalNumber(precVta) * cantidad;
                        totalNeto += FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNetoAux,2));
                        totalIGV   += (FarmaUtility.getDecimalNumber(precVta) - (FarmaUtility.getDecimalNumber(precVta) / ( 1 + (FarmaUtility.getDecimalNumber(valIgv) / 100)))) * cantidad;

                        DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul,numera,codProd,cantNC,FarmaUtility.formatNumber(totalNetoAux),secPedDet);
                      }
                    }

                    //Actualiza Montos Cabecera
                    totalBruto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto,2));
                    totalNeto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto,2));
                    totalIGV = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV,2));
                    totalDscto = (totalBruto - totalNeto);
                    totalDscto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto,2));

                    DBCaja.actualizaMontosCabeceraNC(numera,totalBruto,totalNeto,totalIGV,totalDscto);
                            //COBRA NOTA DE CREDITO
                            cobrarPedido(numera);


                        FarmaUtility.aceptarTransaccion();

                        retorno = true;
                             */
                } //fin if (dlgDetalleAnularPedido.anularPedidofidelizado(FarmaConstants.INDICADOR_S))
            } else {
                retorno = false;
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al grabar nota de credito :\n" +
                    e.getMessage(), btnRelacionProductos);
            retorno = false;
        } catch (Exception ex) {
            log.error("", ex);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, ex.getMessage(), btnRelacionProductos);
            retorno = false;
        } finally {
            FarmaConnectionRemoto.closeConnection();
        }

        return retorno;
    }


    private boolean grabar() {
        boolean retorno = true;
        //Valores totales
        double totalBruto = 0.00;
        double totalDscto = 0.00;
        double totalIGV = 0.00;
        double totalNeto = 0.00;
        double totalNetoAux = 0.00;
        //double redondeo = 0.00;
        int cantidad = 0;
        String pDatosDel = FarmaConstants.INDICADOR_N;
        try {
            pDatosDel = DBCaja.getDatosPedDelivery(VariablesCaja.vNumPedVta_Anul);
            //Esta decision fue tomada por gerencia
            //dubilluz 02.12.2008
            DBCaja.activarCuponesUsados(VariablesCaja.vNumPedVta_Anul);

            log.debug("-----");
            DlgDetalleAnularPedido dlgDetalleAnularPedido;
            dlgDetalleAnularPedido = new DlgDetalleAnularPedido(myParentFrame, "", true, true);
            dlgDetalleAnularPedido.setVisible(true);


            if (FarmaVariables.vAceptar) {
                /**Proceso de anulacion de Pedido Recarga Virtual
           * LTAVARA 10.09.2014  inicio
           * Copia de dlgDetalleAnulacionPedido - metodo de anulaci�n
           * */

                //INI ASOSA - 11/12/2014 - RECAR
                boolean isAnulOk = FarmaVariables.vAceptar;//dlgDetalleAnularPedido.isAnul();

                if (!isAnulOk) {
                    return false;
                }
                //FIN ASOSA - 11/12/2014 - RECAR

                if (!dlgDetalleAnularPedido.anularTransaccionCMR()) {
                    retorno = false;

                }

                /**Proceso de anulacion de Pedido Recarga Virtual
           * LTAVARA 10.09.2014  FIN
           * Copia de dlgDetalleAnulacionPedido - metodo de anulaci�n
           * */

                /** Anular lo acumulado si es fidelizado
           * LTAVARA 10.09.2014  inicio
           * */

                if (dlgDetalleAnularPedido.anularPedidofidelizado(FarmaConstants.INDICADOR_S)) { // LTAVARA 10.09.2014
                    log.debug("Valor de vMotivoAnulacion dentro del metodo grabar: " + VariablesCaja.vMotivoAnulacion);

                    (new FacadeRecaudacion()).obtenerTipoCambio();
                    String tipoCambio = FarmaVariables.vTipCambio + "";

                    // NUEVO METODO DE GENERACION DE NOTA DE CREDITO
                    // REVISAR LA GENERACION COMPLETA DE LOS PEDIDOS CORRECTAMENTE
                    // DUBILLUZ 27.09.2013
                    /********************* INICIO DE CAMBIO ********************************/
                    //dubilluz 27.09.2013 retorna el conjunto de pedidos generados por la NOTA DE CREDITO
                    pListaPedidos =
                            DBCaja.agregarCabeceraNotaCredito(VariablesCaja.vNumPedVta_Anul, tipoCambio, VariablesCaja.vMotivoAnulacion).split("@");
                    //CHUANES
                    //SE OBTIENE LA LISTA QUE ALAMACENA LOS NUMEROS DE PEDIDOS
                    ConstantesDocElectronico.lstPedidos = pListaPedidos;
                    log.debug(">> cantidad de pedidos generados de NC>>" + pListaPedidos.length);
                    log.debug(">> >>" + pListaPedidos.toString());
                    String numera = "";
                    for (int i = 0; i < pListaPedidos.length; i++) {
                        numera = pListaPedidos[i].toString();
                        log.debug("numera>>" + numera);
                        // los parametros de monto,cantidad,total,items NO NECESITA porque se va sacar todo la copia del ORIGINAL de La NC
                        //dubilluz 27.09.2013
                        DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul, numera, "0", "0", "0.0",
                                                         i + "");

                        /* * @author LTAVARA
                         * Validar si la variable COMPROBANTE ELECTRONICO esta activo, para genera el Numero de comprobante e
                         * @since 04/09/2014
                         * */
                        if (UtilityEposTrx.validacionEmiteElectronico()) {
                            VariablesCaja.vNumPedVta = numera; //LTAVARA 04.09.2014 SET AL NUMERO DE COMPROBANTE

                            //obtiene los comprobantes del pedido
                            if (UtilityCaja.obtieneCompPago()) {
                                for (int j = 0; j < VariablesVentas.vArray_ListaComprobante.size(); j++) {
                                    //obtener  secComprobante por documento
                                    VariablesConvenioBTLMF.vSecCompPago =
                                            ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(1)).trim();
                                    VariablesConvenioBTLMF.vTipoCompPago =
                                            ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(2)).trim();
                                    // obtener el numero del comprobante electr�nico
                                    VariablesCaja.vNumCompImprimir =
                                            UtilityEposTrx.getNumCompPagoE(VariablesConvenioBTLMF.vTipoCompPago,
                                                                           VariablesCaja.vNumPedVta,
                                                                           VariablesConvenioBTLMF.vSecCompPago, "",
                                                                           false);
                                    // KMONCADA 20.10.2014 VALIDACION DE DUPLICIDAD DE COMPROBANTES ELECTRONICOS
                                    // DUPLICADO EN LOCAL
                                    /*if("L".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                            throw new Exception("El n�mero de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya fue asignado en el local, comuniquese con Sistemas...!!");
                                        }
                                        // DUPLICADO EN RAC
                                        if("R".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                            throw new Exception("El n�mero de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya se encuentra registrado en RAC, comuniquese con Sistemas...!!");
                                        }*/
                                    //mensaje de error
                                    if (VariablesCaja.vNumCompImprimir.startsWith(EposConstante.MSJ_ERROR)) {
                                        throw new SQLException(VariablesCaja.vNumCompImprimir);
                                    }
                                    if ("".equals(VariablesCaja.vNumCompImprimir) ||
                                        "0".equals(VariablesCaja.vNumCompImprimir)) {

                                        throw new SQLException("Error Generar Nota de Cerdito - El numero de comprobante electronico no se encontro !");
                                    }
                                }

                            }
                        } //fin validacion electronica

                    }
                    //cobrarPedido(numera);

                    /**Actualiza el pedido virtual con el trace de anulaci�n
                     * LTAVARA  11.09.2014 INICIO
                     * Copia de dlgDetalleAnulacionPedido - metodo de anulaci�n
                     * **/

                    //GFONSECA 08/11/2013 Conciliacion anulacion venta CMR
                    /* if(dlgProcesarVentaCMR != null)
                    {   if(dlgProcesarVentaCMR.isBRptTrsscAnul())
                        {
                            String numPedNeg = facadeRecaudacion.getNumPedidoNegativo(VariablesCaja.vNumPedVta_Anul);
                            dlgProcesarVentaCMR.setStrNumPedNegativo(numPedNeg);
                            dlgProcesarVentaCMR.procesarConciliacionAnul();
                        }*/
                    if (VariablesCaja.vIndPedidoConProdVirtual && !VariablesCaja.vIndAnulacionConReclamoNavsat) {
                        try {
                            UtilityCaja.actualizaInfoPedidoVirtualAnulado(VariablesCaja.vNumPedVta_Anul);
                        } catch (SQLException sql) {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this,
                                                     "Error al actualizar informacion del pedido virtual anulado.\n" +
                                    sql.getMessage(), this);
                        }
                    }

                    /**
                     * LTAVARA 11.09.2014 FIN
                     * Copia de dlgDetalleAnulacionPedido - metodo de anulaci�n
                     * **/

                    FarmaUtility.aceptarTransaccion();
                    retorno = true;
                    /********************* FIN   DE CAMBIO ********************************/

                    /* String numera = DBCaja.agregarCabeceraNotaCredito(VariablesCaja.vNumPedVta_Anul,tipoCambio,VariablesCaja.vMotivoAnulacion);
                    xxx("Numera JCORTEZ: " + numera);

        for(int i=0;i<tblListaProductos.getRowCount();i++)
        {

          String codProd = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_COD_PROD);
          String secPedDet = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_SEC_DET);
          String precLta = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_PREC_LTA);
          String precVta = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_PREC_VTA);
          String valIgv = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_VAL_IGV);
          String cantDisp = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_CANT_DISP);
          String cantNC = FarmaUtility.getValueFieldJTable(tblListaProductos,i,COL_CANT_NC);

            //JCORTEZ 23.04.2009
            //Se considera grabar del igual forma tipo de comprobante TICKET al generar el detalle de la nota de credito
          if(VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA)||
              VariablesCaja.vTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
          {
            //ERIOS 29.05.2008 Se devuelve todos el detalle del comprobante
            cantidad = Integer.parseInt(cantDisp.trim());

            totalBruto += FarmaUtility.getDecimalNumber(precLta) * cantidad;
            totalNetoAux = FarmaUtility.getDecimalNumber(precVta) * cantidad;
            totalNeto += FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNetoAux,2));
            totalIGV   += (FarmaUtility.getDecimalNumber(precVta) - (FarmaUtility.getDecimalNumber(precVta) / ( 1 + (FarmaUtility.getDecimalNumber(valIgv) / 100)))) * cantidad;

            DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul,numera,codProd,cantDisp,FarmaUtility.formatNumber(totalNetoAux),secPedDet);
          }else if(!cantNC.equals("0"))
          {
            //ERIOS 29.05.2008 Solo se devuelve aquellos registros se trabajo.
            cantidad = Integer.parseInt(cantNC);

            totalBruto += FarmaUtility.getDecimalNumber(precLta) * cantidad;
            totalNetoAux = FarmaUtility.getDecimalNumber(precVta) * cantidad;
            totalNeto += FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNetoAux,2));
            totalIGV   += (FarmaUtility.getDecimalNumber(precVta) - (FarmaUtility.getDecimalNumber(precVta) / ( 1 + (FarmaUtility.getDecimalNumber(valIgv) / 100)))) * cantidad;

            DBCaja.agregarDetalleNotaCredito(VariablesCaja.vNumPedVta_Anul,numera,codProd,cantNC,FarmaUtility.formatNumber(totalNetoAux),secPedDet);
          }
        }

        //Actualiza Montos Cabecera
        totalBruto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto,2));
        totalNeto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto,2));
        totalIGV = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV,2));
        totalDscto = (totalBruto - totalNeto);
        totalDscto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto,2));
        DBCaja.actualizaMontosCabeceraNC(numera,totalBruto,totalNeto,totalIGV,totalDscto);
	
	//COBRA NOTA DE CREDITO
	cobrarPedido(numera);
                  */

                    //--Se anula los cupones en Matriz
                    //  04.09.2008 Dubilluz
                    UtilityCaja.anulaCuponesPedido(VariablesCaja.vNumPedVta_Anul, this, btnRelacionProductos);

                    //Activa los cupones en matriz
                    //03.12.2008 dubilluz
                    UtilityCaja.activaCuponesMatriz(VariablesCaja.vNumPedVta_Anul, this, btnRelacionProductos);

                    UtilityCaja.alertaPedidoDelivery(pDatosDel.trim());
                    retorno = true;

                } //LTAVARA 10.09.2014 FIN


            } else {
                retorno = false;
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.liberarTransaccion();
            UtilityConvenioBTLMF.liberarTransaccionRempota(null, null, FarmaConstants.INDICADOR_S);
            FarmaUtility.showMessage(this, "Error al grabar nota de credito :\n" +
                    e.getMessage(), btnRelacionProductos);
            retorno = false;
        } catch (Exception ex) {
            log.error("", ex);
            FarmaUtility.liberarTransaccion();
            UtilityConvenioBTLMF.liberarTransaccionRempota(null, null, FarmaConstants.INDICADOR_S);
            FarmaUtility.showMessage(this, ex.getMessage(), btnRelacionProductos);
            retorno = false;
        }

        /*catch(Exception exc)
    {
      FarmaUtility.liberarTransaccion();
        log.error("",exc);
      FarmaUtility.showMessage(this, "Error en la aplicacion al grabar.\n"+exc.getMessage(),btnRelacionProductos);
      retorno = false;
    }*/
        return retorno;
    }

    private void cobrarPedido(String numera) throws Exception {

        VariablesCaja.vNumPedVta = numera;
        VariablesVentas.vTip_Comp_Ped = ConstantsVentas.TIPO_COMP_NOTA_CREDITO;
        if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_NOTA_CREDITO))
            VariablesCaja.vNumSecImpresionComprobantes =
                    DBCaja.agrupaImpresionDetallePedido(ConstantsCaja.ITEMS_TOTAL_POR_NOTA_CREDITO);
        DBCaja.cobraNotaCredito(VariablesVentas.vTip_Comp_Ped);
        if (!UtilityCaja.validaAgrupacionComprobante(this, tblListaProductos)) {
            FarmaUtility.liberarTransaccion();
            VariablesCaja.vIndPedidoCobrado = false;
            return;
        }
        VariablesCaja.vIndPedidoCobrado = true;
        //FarmaUtility.aceptarTransaccion();
        //FarmaUtility.showMessage(this, "Pedido Cobrado con Exito",tblListaProductos);
        UtilityCaja.procesoAsignacionComprobante(this, tblListaProductos);

    }

}