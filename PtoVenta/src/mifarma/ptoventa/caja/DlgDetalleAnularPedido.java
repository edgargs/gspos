package mifarma.ptoventa.caja;


import com.gs.mifarma.RespuestaTXBean;
import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.epos.reference.EposVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.UtilityRecargaVirtual;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.pinpad.DlgAnularTransPinpad;
import mifarma.ptoventa.pinpad.reference.DBPinpad;
import mifarma.ptoventa.puntos.reference.UtilityTransactionPuntos;
import mifarma.ptoventa.recaudacion.DlgProcesarVentaCMR;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleAnularPedido extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgDetalleAnularPedido.class);

    DlgAnularTransPinpad dlgAnularTransPinpad;
    FarmaTableModel tableModelUsuariosCaja;
    Frame myParentFrame;
    private boolean esNotaCredito = false;
    private boolean esPrepMagistral = false;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private JButton btnListaUsuarioCaja = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private static JTable tblUsuariosCaja = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    //GFonseca 04/09/13. Anular pedido para el caso de Venta CMR (Recaudacion con el SIX)
    FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
    private DlgProcesarVentaCMR dlgProcesarVentaCMR = null;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDetalleAnularPedido() {
        this(null, "", false);
    }

    public DlgDetalleAnularPedido(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgDetalleAnularPedido(Frame parent, String title, boolean modal, boolean esNotaCredito) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.esNotaCredito = esNotaCredito;
        // this.esPrepMagistral=esPrepMagistral;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(446, 241));
        this.setResizable(false);
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Detalle Anulacion de Pedido");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(435, 208));
        jContentPane.setLayout(null);
        jPanel1.setBounds(new Rectangle(20, 20, 390, 30));
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        btnListaUsuarioCaja.setText("Lista de Usuarios y Cajas Disponibles");
        btnListaUsuarioCaja.setBounds(new Rectangle(10, 5, 220, 20));
        btnListaUsuarioCaja.setMnemonic('l');
        btnListaUsuarioCaja.setBackground(new Color(255, 130, 14));
        btnListaUsuarioCaja.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnListaUsuarioCaja.setBorderPainted(false);
        btnListaUsuarioCaja.setContentAreaFilled(false);
        btnListaUsuarioCaja.setDefaultCapable(false);
        btnListaUsuarioCaja.setFocusPainted(false);
        btnListaUsuarioCaja.setHorizontalAlignment(SwingConstants.LEFT);
        btnListaUsuarioCaja.setRequestFocusEnabled(false);
        btnListaUsuarioCaja.setForeground(SystemColor.window);
        btnListaUsuarioCaja.setFont(new Font("SansSerif", 1, 11));
        btnListaUsuarioCaja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnListaUsuarioCaja_keyPressed(e);
            }
        });
        btnListaUsuarioCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaUsuarioCaja_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(20, 50, 390, 125));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(320, 185, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(210, 185, 100, 20));
        jPanel1.add(btnListaUsuarioCaja, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jScrollPane1.getViewport();
        jScrollPane1.getViewport();
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(jPanel1, null);
        jScrollPane1.getViewport().add(tblUsuariosCaja, null);
        jContentPane.add(jScrollPane1, null);
        //this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableUsuariosCaja();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableUsuariosCaja() {
        tableModelUsuariosCaja =
                new FarmaTableModel(ConstantsCaja.columnsUsuariosCaja, ConstantsCaja.defaultUsuariosCaja, 0);
        FarmaUtility.initSimpleList(tblUsuariosCaja, tableModelUsuariosCaja, ConstantsCaja.columnsUsuariosCaja);
        cargaUsuariosCajaDisponibles();
    }

    private void cargaUsuariosCajaDisponibles() {
        try {
            DBCaja.getListaCajaUsuario(tableModelUsuariosCaja);
            FarmaUtility.ordenar(tblUsuariosCaja, tableModelUsuariosCaja, 0, FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("tableModelUsuariosCaja:" + tableModelUsuariosCaja);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al verificar caja disponible - \n" +
                    e.getMessage(), tblUsuariosCaja);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnListaUsuarioCaja_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnListaUsuarioCaja);
    }

    private void btnListaUsuarioCaja_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnListaUsuarioCaja);
        validaCajaOpen(); //ASOSA
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    /**
     * Valida que no halla usuarios con caja abierta
     * @author ASOSA
     * @since  19.01.2010
     */
    private void validaCajaOpen() {
        if (tableModelUsuariosCaja.getRowCount() <= 0) {
            log.debug("validaCajaOpen::::::");
            FarmaUtility.showMessage(this, "No hay cajeros a quien asignar dicha anulación.", null);
            cerrarVentana(false);
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        boolean vIndAnular = true; //JCHAVEZ 23122009
        FarmaGridUtils.aceptarTeclaPresionada(e, tblUsuariosCaja, null, 0);
        log.debug("esNotaCredito:" + esNotaCredito);

        if (UtilityPtoVenta.verificaVK_F11(e)) {
          anularPedidoTradicional(vIndAnular);
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                VariablesCaja.vMotivoAnulacion = "";
                log.debug("Escape :" + "Ventana DlgDetalleAnularPedido");
                cerrarVentana(false);
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {

        //JCORTEZ 30.12.2008
        //ROLLBACK remotamente si es que no se anulo)
        if (VariablesCaja.vIndCommitRemotaAnul &&
            VariablesCaja.vIndLineaPtoventaMatriz.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            log.debug("ROLLBACK REMOTO EN ESC.......................");
        }
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();

    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    /**
     * metodo encargado de anular los pedidos
     * @author dubilluz
     * @since  16.02.2015
     * @param  vFlagValidarMinMatriz
     * @return
     */
    private boolean procesoAnulaPedido_F11(String vFlagValidarMinMatriz) {
        boolean vValor  = false;
        UtilityTransactionPuntos trnxPuntos = new UtilityTransactionPuntos(VariablesCaja.vNumPedVta_Anul);
        try {
           /*
            * previoAnulaPuntos
            * anulaOrbis
            * */
            if (trnxPuntos.previoAnulaPuntos()) {
                //ANULO EL PEDIDO EN LOCAL
                vValor = vAccionAnulaPedidoBD(vFlagValidarMinMatriz);
                FarmaUtility.aceptarTransaccion();
                //ANULO EL PEDIDO EN LOCAL
                if (vValor) {
                    // SI YA GENERO LA NOTA DE CREDITO YA SE DEBE DE ANULAR EN ORBIS
                    // LAIS INDICO QUE SEGUN LO DEFINIDO Y CONVERSADO
                    // DEBE DE HACERSE EL REGISTRO DE ANULACION A ORBIS SI ES QUE SE GRABO Y COMMIT EN EL LOCAL.
                    // dubilluz 16.02.2015
                    try {
                        if (trnxPuntos.isVAnulaEnOrbis()&&!trnxPuntos.isVIndDescartaPedidoOrbis()) {
                            trnxPuntos.anulaOrbis();
                            if(trnxPuntos.isVIndDescartaPedidoOrbis())
                               trnxPuntos.descartaAnulacionOrbis();
                            else
                             trnxPuntos.saveTrxAnulOrbis();
                            FarmaUtility.aceptarTransaccion();
                            trnxPuntos.imprimeVariables();
                            trnxPuntos.reset();
                            FarmaUtility.showMessage(this, "Anulación generada con éxito." + "\n" +
                                    "Se pudo procesar la anulación de Puntos.", tblUsuariosCaja);
                        } else{
                            if(trnxPuntos.isVIndDescartaPedidoOrbis()){
                                                        trnxPuntos.descartaAnulacionOrbis();
                                                        FarmaUtility.aceptarTransaccion();
                                                        trnxPuntos.imprimeVariables();
                                                        trnxPuntos.reset();
                            }                            
                            FarmaUtility.showMessage(this, "¡Anulación generada satisfactoriamente!", tblUsuariosCaja);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        FarmaUtility.liberarTransaccion();
                        log.info(e.getMessage());
                        FarmaUtility.showMessage(this, "Se generó con la éxito la nota de crédito"+"\n"+
                                                       "No se pudo procesar la anulación de Puntos:"+"\n"+
                                                       e.getMessage()+"",
                                                        tblUsuariosCaja);                        
                    }
                    // dubilluz 16.02.2015
                    // SI FUESE NECESARIO
                    //JCHAVEZ 10.07.2009.sn
                }
                
            } else {
                vValor = false;
                FarmaUtility.showMessage(this, "No se puede anular el pedido."+"\n"+
                                               "Porque el servicio de puntos no da permite esta acción."
                                               , tblUsuariosCaja);
            }
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
            log.info(e.getMessage());
            if(vValor)
                FarmaUtility.showMessage(this, "Se generó con éxito Anulación"+"\n"+
                                               e.getMessage()
                                               , tblUsuariosCaja);
            else{
                FarmaUtility.showMessage(this, e.getMessage()
                                               , tblUsuariosCaja);
            }
        }
        return vValor;
    }
    
    
    private boolean vAccionAnulaPedidoBD(String vFlagValidarMinMatriz){
        boolean retorno = false, vResultado = false;
        boolean indCommitLocal = false, indCommitRac = false;
        String pDatosDel = FarmaConstants.INDICADOR_N;
        String ruta = "";

        try {
            //JCORTEZ 19.03.10 Se anula datos de campaña acumulada
            if (anularPedidofidelizado(FarmaConstants.INDICADOR_S)) {
                log.debug("::::::::::::::::::::::::.ANULANDO REGISTROS DE CAMPAÑA ACUMULADA::::::::::::::::::::::::::::::");

                //obtiene datos de pedido delivery si es que lo fuera           
                pDatosDel = DBCaja.getDatosPedDelivery(VariablesCaja.vNumPedVta_Anul);
                log.debug("Datos delivery-->" + pDatosDel);

                //Activa los cupones usados esta decision fue tomada por gerencia dubilluz 02.12.2008
                DBCaja.activarCuponesUsados(VariablesCaja.vNumPedVta_Anul);
                if (log.isDebugEnabled()) { //ver si debug esta activo
                    VariablesCaja.mostrarValoresVariables(); //imprimiendo todos los valores de la clase VariablesCaja
                }
                log.debug("anulando pedido ... 0% ");
                log.debug("VariablesCaja.vNumPedVta_Anul:" + VariablesCaja.vNumPedVta_Anul);
                log.debug("VariablesCaja.vNumComp_Anul:" + VariablesCaja.vNumComp_Anul);
                log.debug("Usuario:" + tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 2).toString());

                //ini - Agregado Por FRAMIREZ 28.02.201 2 para la anulacion de pedido del nuevo convenio BTLMF
                Map vtaPedido = (Map)DBConvenioBTLMF.obtenerConvenioXPedido(VariablesCaja.vNumPedVta_Anul);
                String indConvenioBTLMF = (String)vtaPedido.get("IND_CONV_BTL_MF");

                log.debug("indConvenioBTLMF        :" + indConvenioBTLMF);
                boolean isNuevoConvenioBTLMF = false;
                boolean isConvenioBTLMF = UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null);

                if (isConvenioBTLMF && indConvenioBTLMF != null && indConvenioBTLMF.equals("S")) {
                    isNuevoConvenioBTLMF = true;
                    boolean esCompCredito = UtilityConvenioBTLMF.esCompCredito(this);
                    log.debug("Es comprobante Crédito " + esCompCredito);

                    if (esCompCredito) {
                        String indConexionRac =
                            FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC, FarmaConstants.INDICADOR_S);
                        log.debug("indConexionRac :" + indConexionRac);
                        if (indConexionRac.equals("S")) {
                            VariablesCaja.vNumPedVta = VariablesCaja.vNumPedVta_Anul;
                            if (UtilityConvenioBTLMF.obtieneCompPago(new JDialog(), "", null)) {
                                for (int j = 0; j < VariablesConvenioBTLMF.vArray_ListaComprobante.size(); j++) {
                                    VariablesConvenioBTLMF.vTipoCompPago =
                                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(2)).trim();
                                    VariablesConvenioBTLMF.vNumCompPago =
                                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(0)).trim();
                                }

                            }
                            String pedidoAnuladoRac =
                                DBConvenioBTLMF.anularPedidoRac(FarmaConstants.INDICADOR_N, FarmaConstants.INDICADOR_N); //12.09.2014 ERIOS
                            log.debug("Pedido Anulado en el Rac?" + pedidoAnuladoRac);

                            if ("S".equalsIgnoreCase(pedidoAnuladoRac))
                                indCommitRac = true;

                            if (pedidoAnuladoRac.equals("S") || pedidoAnuladoRac.equals("P")) {
                                DBCaja.anularPedido_BTL_MF(VariablesCaja.vNumPedVta_Anul, VariablesCaja.vTipComp_Anul,
                                                           VariablesCaja.vNumComp_Anul, VariablesCaja.vMonto_Anul,
                                                           tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),
                                                                                      2).toString(),
                                                           VariablesCaja.vMotivoAnulacion, vFlagValidarMinMatriz);
                                indCommitLocal = true;
                                if (pedidoAnuladoRac.equals("S")) {
                                    DBConvenioBTLMF.actualizaFechaProcesoAnulaRac();
                                }
                            } else {
                                FarmaUtility.showMessage(this, "No se pudo anular el pediddo Convenio en el RAC",
                                                         tblUsuariosCaja);
                                indCommitLocal = false;
                                indCommitRac = false; //return false;
                            }
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "No puede anular el pedido, porque no existe una conexion con el RAC",
                                                     tblUsuariosCaja);
                            indCommitLocal = false;
                            indCommitRac = false; //return false;
                        }
                    } else {
                        DBCaja.anularPedido_BTL_MF(VariablesCaja.vNumPedVta_Anul, VariablesCaja.vTipComp_Anul,
                                                   VariablesCaja.vNumComp_Anul, VariablesCaja.vMonto_Anul,
                                                   tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),
                                                                              2).toString(),
                                                   VariablesCaja.vMotivoAnulacion, vFlagValidarMinMatriz);
                        indCommitLocal = true;
                        indCommitRac = false;
                    }
                } else {
                    DBCaja.anularPedido(VariablesCaja.vNumPedVta_Anul, VariablesCaja.vTipComp_Anul,
                                        VariablesCaja.vNumComp_Anul, VariablesCaja.vMonto_Anul,
                                        tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 2).toString(),
                                        VariablesCaja.vMotivoAnulacion, vFlagValidarMinMatriz);
                    indCommitLocal = true;
                    indCommitRac = false;
                }
                //fin
                //RUDY LLANTOY 23.05.13 Verifica el estado logico de vestRCM si es true cambia el estado a aNulado
                if (VariablesVentas.vestRCM)
                    DBRecetario.cambiarEstadoRCM(VariablesCaja.vNumPedVta_Anul, "N");
                ////////////////////////////////////////////////////////////

                log.debug("anulando pedido ... 100% ...haciendo commit");
                UtilityCaja.obtieneImpresTicket(this, tblUsuariosCaja); //Cesar Huanes
                //JMIRANDA 08/09/2009
                //valida si el producto anulado es por convenio y actualiza consumo cliente.
                //Metodo que obtiene todos los datos del Convenio (REMOTO)

                String vConvenioCliAnula = "N";

                if (!isNuevoConvenioBTLMF)
                    vConvenioCliAnula = DBCaja.obtieneConvenioCliAnula(VariablesCaja.vNumPedVta_Anul);

                log.debug("vConvenioCliAnula: " + vConvenioCliAnula);
                boolean vindCommitCredito = false;

                if (!vConvenioCliAnula.equalsIgnoreCase("N")) {
                    // abrir una conexion remota
                    String[] val;
                    val = vConvenioCliAnula.split("Ã");
                    FarmaConnectionRemoto.closeConnection();

                    //JCHAVEZ 231220009 inicio
                    log.debug("VariablesCaja.vIndLineaPtoventaMatriz antes de conestar: " +
                              VariablesCaja.vIndLineaPtoventaMatriz);
                    VariablesCaja.vIndLineaPtoventaMatriz =
                            FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                           FarmaConstants.INDICADOR_S);
                    log.debug("VariablesCaja.vIndLineaPtoventaMatriz despues de conestar: " +
                              VariablesCaja.vIndLineaPtoventaMatriz);

                    if (VariablesCaja.vIndLineaPtoventaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                        if (!isNuevoConvenioBTLMF) {
                            DBConvenio.actualizaConsumoClienteEnMatriz(val[0], val[1], val[2],
                                                                       FarmaConstants.INDICADOR_N, val[4], val[5]);
                            log.debug("vConvenioCliAnula: " + val[0] + " " + val[1] + " " +
                                      FarmaUtility.getDecimalNumber(val[2]) + " " + val[4] + " " + val[5] +
                                      "  val3: " + val[3]);
                        } else {
                            log.debug("Es Nueva Funcionalidad");
                        }
                        vindCommitCredito = true;
                    } else {
                        FarmaUtility.liberarTransaccion();
                        UtilityConvenioBTLMF.liberarTransaccionRempota(null, null, FarmaConstants.INDICADOR_S);
                        FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_N);

                        FarmaConnectionRemoto.closeConnection();
                        FarmaUtility.showMessage(this, "No se puede anular el pedido, no existe conexion con Matriz",
                                                 tblUsuariosCaja);
                        return false;
                    }
                    //JCHAVEZ 231220009 fin
                }

                if (indCommitLocal) { //kmoncada 13.08.2014 anulado en ptoventa.
                    if (indCommitRac) { // kmoncada 13.08.214 anulado en rac.
                        log.info("Commit de anulacion de pedido en el rac");
                        UtilityConvenioBTLMF.aceptarTransaccionRempota(null, null, FarmaConstants.INDICADOR_S);
                    } else {
                        UtilityConvenioBTLMF.liberarTransaccionRempota(null, null, FarmaConstants.INDICADOR_S);
                    }
                    log.info("Commit de anulacion de pedido en el ptoventa");
                    FarmaUtility.aceptarTransaccion();
                    if (vindCommitCredito) {
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_S);
                        FarmaConnectionRemoto.closeConnection();
                        log.error("Anula PEDIDO convenio REMOTO");
                    }

                    String mensaje =
                        (!VariablesCaja.vTipComp_Anul.equals("%")) ? "Comprobante Anulado correctamente." :
                        "Pedido Anulado correctamente";

                    FarmaUtility.showMessage(this, mensaje, btnListaUsuarioCaja);
                    retorno = true;

                    //log.debug("ss"+VariablesVentas.vTip_Comp_Ped);

                    //Mfajardo 24/04/09 metodo imprimir ticket de anulacion
                    if (!esNotaCredito) {
                        String pNumeroPedido = VariablesCaja.vNumPedVta_Anul;
                        String pSecuencia = tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 0).toString();
                        String pCajero = tblUsuariosCaja.getValueAt(0, 2).toString();
                        String pTurno = tblUsuariosCaja.getValueAt(0, 3).toString();

                        if (isConvenioBTLMF && indConvenioBTLMF != null && indConvenioBTLMF.equals("S")) {
                            UtilityConvenioBTLMF.imprimeTicketBTLMF(this, pNumeroPedido, pCajero, pTurno);
                        } else {
                            UtilityCaja.getImpresionTicketAnulado(this, pNumeroPedido, pSecuencia, pCajero, pTurno);
                        }
                        FarmaUtility.aceptarTransaccion();
                        vResultado = false;
                        log.info("2-vResultado:" + vResultado);
                    }

                    //Se vuelve a obtener datos delivery
                    String[] pDatosDely = pDatosDel.trim().split("%");
                    String indAutoma = "", tipVta = "";
                    if (pDatosDely.length == 5) {
                        indAutoma = pDatosDely[3].trim();
                        tipVta = pDatosDely[4].trim();
                        VariablesCaja.vIndDeliveryAutomatico = indAutoma.trim();
                        VariablesVentas.vTip_Ped_Vta = tipVta.trim();
                    }

                    log.info("****************JCORTEZ******************************");
                    log.info("VariablesCaja.vTipComp_Anul-->" + VariablesCaja.vTipComp_Anul);
                    log.info("2-VariablesVentas.vTip_Ped_Vta-->" + VariablesVentas.vTip_Ped_Vta);
                    log.info("VariablesCaja.vIndDeliveryAutomatico-->" + VariablesCaja.vIndDeliveryAutomatico);

                    if (VariablesCaja.vTipComp_Anul.equals("%")) {
                        if (!VariablesVentas.vTip_Ped_Vta.equals(ConstantsVentas.TIPO_PEDIDO_MESON) &&
                            VariablesCaja.vIndDeliveryAutomatico.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            UtilityCaja.alertaPedidoDelivery(pDatosDel.trim());
                            reinicializaPedidoAutomatico(FarmaVariables.vCodLocal, VariablesCaja.vNumPedVta_Anul);
                        }
                    }
                    //21.08.2008 DUBILLUZ Se modifico el procedimiento
                    UtilityCaja.anulaCuponesPedido(VariablesCaja.vNumPedVta_Anul, this, btnListaUsuarioCaja);
                    //Activa los cupones en matriz 03.12.2008 dubilluz
                    UtilityCaja.activaCuponesMatriz(VariablesCaja.vNumPedVta_Anul, this, btnListaUsuarioCaja);

                    //JCORTEZ 22.12.2008
                    //commit remotamente si es que es Campaña acumulada)
                    if (VariablesCaja.vIndCommitRemotaAnul &&
                        VariablesCaja.vIndLineaPtoventaMatriz.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_N);
                        log.debug("haciendo commit remoto a matriz");
                    }
                } else {
                    if (indCommitRac) { // roolback al rac.
                        log.info("Rollback de anulacion de pedido en el rac");
                        UtilityConvenioBTLMF.liberarTransaccionRempota(null, null, FarmaConstants.INDICADOR_S);
                    }
                    //kmoncada 13.08.2014 en caso de no realizar la anulacion realizar rollback al ptoventa
                    log.info("Rollback de anulacion de pedido en el ptoventa");
                    FarmaUtility.liberarTransaccion();
                }

            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                    //"dubilluz",
                    VariablesPtoVenta.vDestEmailErrorAnulacion, "Error al Anular Pedido Completo",
                    "Error de Anulación",
                    "Se produjo un error al anular un pedido :<br>" + "IP PC: " + FarmaVariables.vIpPc + "<br>" +
                    //JMIRANDA 30/07/09
                    "Correlativo : " + VariablesCaja.vNumPedVta_Anul + "<br>" + "Error: " + e,
                    //"daubilluz@gmail.com");
                    "");
            log.info("Error anular pedido : " + e);

            //JCORTEZ 22.12.2008
            //commit remotamente si es que es Campaña acumulada)


            if (!VariablesCaja.vIndCommitRemotaAnul &&
                VariablesCaja.vIndLineaPtoventaMatriz.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                log.debug("haciendo rollback en matriz");
            }
            log.info("vResultado:" + vResultado);
            if (!vResultado) {
                retorno = true;
                FarmaUtility.showMessage(this,
                        //"No se imprimió el ticket anulado.\nIntente por Reimpresión de Ticket Anulado.\nGracias",
                        //LLEIVA 20-Feb-2014 Se cambio el texto para indicar que no se pudo anular el pedido
                        "ERROR: No se pudo anular el pedido indicado\n" +
                        e.getMessage(), btnListaUsuarioCaja);
            } else {
                if (((SQLException)e).getErrorCode() == 20002) {
                    FarmaUtility.showMessage(this, "La Fracción Actual no permite anular esta Venta.\n" +
                            e, btnListaUsuarioCaja);
                } else if (((SQLException)e).getErrorCode() == 20016) {
                    FarmaUtility.showMessage(this, "No puede anular este pedido porque el cupon ya fue utilizado.\n" +
                            e.getMessage(), btnListaUsuarioCaja);
                } else if (((SQLException)e).getErrorCode() ==
                           20028) { //agregado para validar la correcta anulacion de pedido
                    FarmaUtility.showMessage(this,
                                             "No se logró anular el pedido\nFecha actual no pertenece al turno asignado.\n",
                                             btnListaUsuarioCaja);
                } else if (((SQLException)e).getErrorCode() ==
                           20005) { //para mostrar un mensaje mas personalizado
                    FarmaUtility.showMessage(this,
                                             "No se logró anular el pedido\nEl pedido fue cobrado hace mas de 5 minutos.",
                                             btnListaUsuarioCaja);
                } else {
                    log.error("", e);
                    FarmaUtility.showMessage(this, "Ocurrio un error al anular - \n" +
                            e.getMessage(), btnListaUsuarioCaja);
                }
                retorno = false;
            }
        } finally {
            //JCORTEZ 22.12.2008
            log.debug("cierra conexion remota matriz, delivery, adm, etc");
            FarmaConnectionRemoto.closeConnection();
        }
        return retorno;        
    }

    private void reinicializaPedidoAutomatico(String pCod_Local, String pNumPedVta) {
        try {
            DBCaja.reinicializaPedidoAutomatico(pCod_Local, pNumPedVta);
            FarmaUtility.aceptarTransaccion();
            /**
       * MOdificado
       * @author : dubilluz
       * @since  : 24.08.2007
       */
            if (VariablesVentas.vTip_Ped_Vta.equals(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                // KMONCADA 01.09.2014 solo se muestra para venta empresa.
                FarmaUtility.showMessage(this,
                                         "EL Pedido Anulado se Encuentra Pendiente , para su posterior generación y cobro.",
                                         null);
            }

            //FarmaUtility.showMessage(this,"El pedido se reinicializó correctamente. Ingrese a la opción\nDelivery Automático del módulo de Ventas para generar el pedido.",null);
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al reinicializar el pedido automatico - \n" +
                    e.getMessage(), null);
        }
    }

    private void obtieneInfoPedidoVirtual() {
        try {
            DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual,
                                            VariablesCaja.vNumPedVta_Anul);
            log.debug("vArrayList_InfoProdVirtual : " + VariablesVirtual.vArrayList_InfoProdVirtual);
        } catch (SQLException sql) {
            VariablesVirtual.vArrayList_InfoProdVirtual.clear();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion de pedido virtual - \n" +
                    sql.getMessage(), tblUsuariosCaja);
        }
    }

    private void colocaInfoPedidoVirtual() {
        VariablesCaja.vTipoProdVirtual =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 1);
        VariablesCaja.vPrecioProdVirtual =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 2);
        VariablesCaja.vNumeroCelular =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 3);
        VariablesCaja.vCodigoProv =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 4);
        VariablesCaja.vNumeroTraceOriginal =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 5);
        VariablesCaja.vCodAprobacionOriginal =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 6);
        /* 27.09.2007 ERIOS Datos necesarios para Bprepaid */
        VariablesCaja.vFechaTX =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 8);
        VariablesCaja.vHoraTX = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 9);
    }

    private void evaluaPedidoProdVirtual(String pNumPedido) {
        int cantProdVirtualesPed = 0;
        cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
        if (cantProdVirtualesPed <= 0) {
            VariablesCaja.vIndPedidoConProdVirtual = false;
        } else {
            VariablesCaja.vIndPedidoConProdVirtual = true;
        }
        log.debug("VariablesCaja.vIndPedidoConProdVirtual : " + VariablesCaja.vIndPedidoConProdVirtual);
    }

    /**
     * Evalua si el pedido tiene una recarga virtual
     * @author  ASOSA
     * @since   08/07/2014
     * @param pNumPedido
     */
    public void evaluaPedidoRecarVirtual(String pNumPedido) {
        int cantProdVirtualesPed = 0;
        cantProdVirtualesPed = cantidadRecargaVirtualPedido(pNumPedido);
        if (cantProdVirtualesPed == 1) {
            VariablesCaja.vIndPedidoConRecarVirtual = true;
        } else {
            VariablesCaja.vIndPedidoConRecarVirtual = false;
        }
        log.debug("VariablesCaja.vIndPedidoConRecarVirtual : " + VariablesCaja.vIndPedidoConRecarVirtual);
    }

    private int cantidadProductosVirtualesPedido(String pNumPedido) {
        int cant = 0;
        try {
            cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
        } catch (SQLException ex) {
            log.error("", ex);
            cant = 0;
            FarmaUtility.showMessage(this, "Error al obtener cantidad de productos virtuales.\n" +
                    ex.getMessage(), tblUsuariosCaja);
        }
        return cant;
    }

    /**
     * Obtiene la cantidad de recargas vitruales que tiene un pedido
     * @author  ASOSA
     * @since   08/07/2014
     * @param pNumPedido
     * @return
     */
    private int cantidadRecargaVirtualPedido(String pNumPedido) {
        int cant = 0;
        try {
            cant = DBCaja.obtieneCantRecVirtualesPedido(pNumPedido);
        } catch (SQLException ex) {
            log.error("", ex);
            cant = 0;
            FarmaUtility.showMessage(this, "Error al obtener cantidad de productos virtuales.\n" +
                    ex.getMessage(), tblUsuariosCaja);
        }
        return cant;
    }

    private boolean validaCodigoRespuestaTransaccion() {
        boolean result = false;
        if (VariablesVirtual.vCodigoRespuesta == null)
            result = false;
        else if (VariablesVirtual.vCodigoRespuesta.equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL))
            result = true;
        return result;
    }

    private void cargaMotivo() {
        log.debug("DlgMotivoAnuladoPEDIDO");

        DlgMotivoAnularPedido dlgMotivoAnularPedido = new DlgMotivoAnularPedido(myParentFrame, "", true);
        dlgMotivoAnularPedido.setVisible(true);

        if (FarmaVariables.vAceptar)
            FarmaVariables.vAceptar = anularTransaccionPinpad();
    }

    //validaTiempoAnulacion

    private boolean validaTiempoAnulacionRecarga(String pNumPedido) {
        String retorno = "";
        try {
            retorno = DBCaja.validaTiempoAnulacion(pNumPedido);
        } catch (SQLException e) {
            log.error("", e);
        }
        log.debug("RESPUESTA:" + retorno);
        if (retorno.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;

        return true;
    }

    /**
     * Obtiene el tiempo maximo para la anulacion de un pedido recarga virtual
     * @author dubilluz
     * @since  09.11.2007
     */
    private String time_max(String pNum_ped) {
        String valor = "";
        String num_pedido = pNum_ped;
        try {
            valor = DBCaja.getTimeMaxAnulacion(num_pedido);

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al obtener tiempo maximo de anulacion de Producto Recarga Virtual.\n" +
                    e.getMessage(), null);
        }
        return valor;
    }

    /**@author asolis
     * @fecha  05-02-09
     * Busca recarga virtual
     * @throws SQLException
     **/
    private boolean BuscarRecargaVirtual() {

        boolean pResultado = false;
        int cantidad = 0;

        try {
            //cantidad  = DBCaja.ExisteRecargaVirtual();

            if (cantidad > 0)
                pResultado = true;

            else
                pResultado = false;

        } catch (Exception e) {
            log.error(null, e);
            FarmaUtility.showMessage(this, "Error al obtener existencia de Producto Recarga Virtual.\n" +
                    e.getMessage(), null);
            pResultado = false;
        }


        return pResultado;
    }


    /*   private void obtieneImpresTicket(){
        log.debug("JCORTEZ: =VariablesCaja.vTipComp--> "+VariablesCaja.vTipComp);
        if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
          try{
             String DescImpr="";
             String result= DBCaja.getObtieneSecImpPorIP( FarmaVariables.vIpPc);
             log.debug("a");
             String result2= DBCaja.getObtieneTipoCompPorIP( FarmaVariables.vIpPc,VariablesCaja.vTipComp);
             log.debug("b");
             //verifica que el secuencial exista, caso contrario, se validara la asignada y se mostrara
             //que no tiene niguna asignacion si es el caso. 16.06.09
             String exist="";
             exist=DBCaja.getExistSecImp(VariablesCaja.vSecImprLocalTicket,FarmaVariables.vIpPc);
             log.debug("c");
             log.debug("JCORTEZ: Secuencia Impr--> "+result);
             if(exist.trim().equalsIgnoreCase("2")){
                log.debug("JCORTEZ:SecImp por IP--> "+result);
                VariablesCaja.vSecImprLocalTicket=result;
             }
             else if (exist.equalsIgnoreCase("X")){
                VariablesCaja.vSecImprLocalTicket=exist.trim();
             }else if(exist.equalsIgnoreCase("1")){
                log.debug("JCORTEZ: Se encontro impresora origen--> "+exist);
             }else {
                log.debug("JCORTEZ: Sec disponible--> "+exist);
                VariablesCaja.vSecImprLocalTicket=exist.trim();
             }
             if(!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")){
                DescImpr=DBCaja.getNombreImpresora(VariablesCaja.vSecImprLocalTicket);
                VariablesCaja.vDescripImpr=DescImpr;
             }else{
                VariablesCaja.vDescripImpr="X";
             }
            }catch(SQLException sql){
                log.error("",sql);
            }

        }
    }
  */

    /**
     * @AUTHOR: JCORTEZ
     * @SINCE: 13.01.09
     * */
    private boolean validaCajaAbierta() {

        boolean result = true;
        String Indicador = "";
        try {
            log.debug("VariablesCaja.vNumCaja ===> " + VariablesCaja.vNumCaja);
            Indicador = DBCaja.obtieneEstadoCaja();
            if (Indicador.trim().equalsIgnoreCase("N")) {
                FarmaUtility.showMessage(this, "La caja no se encuentra aperturada. Verifique!!!", null);
                result = false;
            }
            FarmaUtility.liberarTransaccion();
            log.debug("Se valida apertura de caja para la anulacion ===> " + Indicador);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.debug("error al obtener indicador de caja abierta : " + e.getMessage());
            result = false;
        }

        //bloque de caja
        return result;
    }

    /**
     * se anula el pedido en Local
     * @author: JCORTEZ
     * @since: 18.12.08
     * */
    public boolean anularPedidofidelizado(String IndLocal) {
        boolean result = false;
        try {
            DBCaja.anulaPedidoFidelizado(VariablesCaja.vNumPedVta_Anul, VariablesCaja.vDniCli, IndLocal);
            result = true;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "No se puede anular el pedido, ya que no hay canjes relacionados.\n",
                                         tblUsuariosCaja);
            } else if (ex.getErrorCode() == 20004) {
                FarmaUtility.showMessage(this,
                                         "Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.\n",
                                         tblUsuariosCaja);
            } else if (ex.getErrorCode() == 20005) {
                FarmaUtility.showMessage(this, "No se puede anular el pedido, ya que es parte de su propio canje.\n",
                                         tblUsuariosCaja);
            } else if (ex.getErrorCode() == 20006) {
                FarmaUtility.showMessage(this, "No se puede anular, ya que hay canjes asociados.\n", tblUsuariosCaja);
            } else if (ex.getErrorCode() == 20007) {
                FarmaUtility.showMessage(this, "No se puede anular el pedido, ya que es parte de un canje.\n",
                                         tblUsuariosCaja);
            } else {
                log.error("", ex);
                FarmaUtility.showMessage(this, "Error al anular pedido.\n" +
                        ex.getMessage(), tblUsuariosCaja);
            }
        }
        return result;
    }

    /**
     * Realiza el proceso de anulación de transacción de operación financiera con Pinpad
     * retorna verdadero si se realiza correctamente la misma, falso en otro caso
     * @author LLEIVA
     * @since 09.Ago.2013
     * @return true si realiza correctamente la anulación de transacción
     */
    private boolean anularTransaccionPinpad() { //se consulta si el pedido fue pagado con transaccion de pinpad Visa o Mastercard
        HashMap<String, String> resultado = new HashMap<String, String>();

        //ERIOS 15.05.2015 Para cambio de productos, no pide anulacion de pagos con tarjeta.
        if(VariablesPtoVenta.vIndAnulacionCambioProducto.equals(FarmaConstants.INDICADOR_S)){
            return true;
        }
        //LLEIVA 19-Dic-2013 Consultar si existe alguna forma de pago con tarjeta para el pedido
        DBPinpad.consFormaPagoPedido(VariablesCaja.vNumPedVta_Anul, resultado);

        //LLEIVA 28-Feb-2014 Si la cantidad de resultados no es 4, entonces no es un pago con Pinpad
        if (resultado.size() == 4) {
            //LLEIVA 26-Nov-2013 Si el dia es diferente del actual, no solicita la anulacion por pinpad
            log.debug("Se iniciara la validacion de la anulación con el pinpad");
            String str_fecha_actual = "";
            try {
                str_fecha_actual = UtilityPtoVenta.fechaActualDDMMYYYY();
            } catch (Exception e) {
                log.debug("", e);
            }

            if (str_fecha_actual.equals(resultado.get("FECHA_TRANS"))) { //si posee transaccion VISA o MC, abrir ventana
                dlgAnularTransPinpad = new DlgAnularTransPinpad(myParentFrame, "", true);
                dlgAnularTransPinpad.setValores(VariablesCaja.vNumPedVta_Anul, resultado.get("MONTO_TARJ"),
                                                resultado.get("FECHA_TRANS"), resultado.get("NUM_REF_TRANS"),
                                                resultado.get("FORMA_PAGO_PED"), true);
                dlgAnularTransPinpad.setVisible(true);
                //si se anulo por el pinpad el pedido el mismo dia y con el lote cerrado
                JLabel temp = dlgAnularTransPinpad.getAnulDiaLoteCerrado();
                if (temp != null && "true".equalsIgnoreCase(temp.getText()))
                    dlgAnularTransPinpad = null;
            }
            return FarmaVariables.vAceptar;
        }
        //si ocurrio una excepcion
        else if ("ERROR".equals(resultado.get("FORMA_PAGO_PED"))) {
            return false;
        }
        //si no se pago con pinpad o POS
        else {
            return true;
        }
    }

    /**
     * Verificamos si la anulacion es Venta CMR para el proceso de anulacion con el SIX.
     * @author ERIOS
     * @since 2.2.8
     */
    public boolean anularTransaccionCMR() {

        ArrayList arrayTmpDatosRCD = facadeRecaudacion.getDatosAnulacionVentaCMR(VariablesCaja.vNumPedVta_Anul);

        //ERIOS 03.03.2015 Uso de NCR, no pide anulacion de pagos con tarjeta.
        if("S".equals(FarmaConstants.INDICADOR_S)){
            return true;
        }
        
        //Si la venta esta registrada en la cabecera de recaudacion
        if (arrayTmpDatosRCD != null && arrayTmpDatosRCD.size() > 0) {
            String fechaSistema;
            try {
                fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            } catch (SQLException f) {
                log.error("", f);
                FarmaUtility.showMessage(this, "Error al recuperar la fecha del sistema", tblUsuariosCaja);
                return false;
            }
            String strFechaRecau = ((ArrayList)arrayTmpDatosRCD.get(0)).get(5).toString();
            if (fechaSistema.equals(strFechaRecau)) {

                //Anulacion de venta CMR con el six, retorna true si anula correctamente.
                dlgProcesarVentaCMR = new DlgProcesarVentaCMR(myParentFrame, "", true);
                dlgProcesarVentaCMR.procesarAnulacionVentaCMR(myParentFrame, arrayTmpDatosRCD);
                dlgProcesarVentaCMR.setStrIndProc(ConstantsRecaudacion.RCD_IND_PROCESO_ANULACION);
                dlgProcesarVentaCMR.mostrar();

                if (!dlgProcesarVentaCMR.isBRptTrsscAnul()) {
                    return false; //Si el proceso de anulacion con el Six falla, no tiene que seguir la rutina de anulacion.
                }
            }
        }
        return true;
    }

    /**
     * Pedir clave de operador para que pueda anular la recarga virtual.
     * @author ASOSA
     * @since 24/07/2014
     * @return
     */
    public boolean cargarLogeoOperador() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            //if(VariablesCaja.vIndPedidoRecargaVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
            dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
            log.debug("Pedido Recarga Virtual..Solo el operador podra anular");
            //}

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
            log.error("ERROR", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuario \n : " + e.getMessage(), null);
        }
        return FarmaVariables.vAceptar;
    }

    public boolean isAnul() {
        boolean isAnulOk = true;

        //INI ASOSA - 0A8/07/2014
        evaluaPedidoRecarVirtual(VariablesCaja.vNumPedVta_Anul);
        if (VariablesCaja.vIndPedidoConRecarVirtual && 
            !UtilityCaja.get_ind_ped_con_rimac(VariablesCaja.vNumPedVta_Anul)
            ) {

            //INI ASOSA - 03/12/2014 - RECAR
            RespuestaTXBean obj = new RespuestaTXBean();
            UtilityRecargaVirtual util = new UtilityRecargaVirtual();
            boolean indIntenta = false;
            String codigo = "";
            String descripcion = "";
            if (util.validarConexionRecarga()) {
                obj = util.respuestaSolicitudRecargaDAO(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesCaja.vNumPedVta_Anul);
                if (obj != null) {
                    if (obj.getCodigoRespuesta() == null) {
                        indIntenta = true;
                        codigo = (obj.getCodigoRespuesta() == null) ? "666" : obj.getCodigoRespuesta();
                        descripcion =
                                (obj.getDescripcion() == null) ? "La recarga aún no tiene respuesta \n Si no puede anular intente en unos minutos más" :
                                obj.getDescripcion();
                        FarmaUtility.showMessage(this, descripcion, null);
                    } else {
                        codigo = (obj.getCodigoRespuesta() == null) ? "666" : obj.getCodigoRespuesta();
                        descripcion =
                                (obj.getDescripcion() == null) ? "La recarga no se realizo \n Se procedera a continuar con la anulación" :
                                obj.getDescripcion();
                        if (codigo.trim().equals("00")) {
                            // KMONCADA 19.01.2015 SE VERIFICA QUE LA RECARGA NO TENGA UN EXTORNO PREVIO PARA REALIZARLO EN EL SIX
                            if (util.getIsRecargaAnulada(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesCaja.vNumPedVta_Anul, obj.getNumeroTrace())) {
                                FarmaUtility.showMessage(this, "La recarga se encuentra extornada y/o anulada, se continuara con la anulación", null);
                            }else{
                                indIntenta = true;                                       
                            }
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "La recarga no se realizo \n Se procedera a continuar con la anulación",
                                                     null);
                        }
                    }
                } else {
                    indIntenta = true;
                    codigo = "666";
                    descripcion =
                            "La recarga aún no tiene respuesta \n Si no puede anular intente en unos minutos más";
                    FarmaUtility.showMessage(this, descripcion, null);
                }
            } else {
                isAnulOk = false;
                indIntenta = false;
                FarmaUtility.showMessage(this, "No hay conexión con matriz. \n No se puede anular el pedido", null);
            }
            //FIN ASOSA - 03/12/2014 - RECAR

            if (indIntenta) {
                if (cargarLogeoOperador()) { //ASOSA - 24/07/2014

                    DlgProcesarAnulacionRecarga dlgProcesarAnulacionRecarga =
                        new DlgProcesarAnulacionRecarga(myParentFrame, "Virtual", true);
                    dlgProcesarAnulacionRecarga.mostrar();

                    isAnulOk = dlgProcesarAnulacionRecarga.isVProcesoRecarga();

                    if (!VariablesVirtual.respuestaTXBean.getCodigoRespuesta().equals("00")) {
                        isAnulOk = false;
                    }

                } else {
                    isAnulOk = false;
                }
            }
        }
        return isAnulOk;
    }

    private void anularPedidoTradicional(boolean vIndAnular) {

        log.debug("jcallo:EVENTO clic en tecla F11");
        //DUBILLUZ - 01.03.2010
        /*
        cargaUsuariosCajaDisponibles(); //ASOSA
        validaCajaOpen(); //ASOSA
        if(tableModelUsuariosCaja.getRowCount()<=0) return;//ASOSA
        */
        if (esNotaCredito) {
            log.debug("jcallo:ES nota de credito");
            VariablesCaja.vNumCaja = tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 2).toString();
            //JCHAVEZ 10.07.2009.n
            VariablesCaja.vNumTurnoCaja =
                    tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 3).toString();
            //JCHAVEZ 10.07.2009.n
            /** Agregado por Asolis 01/12/2008 **/
            //JCHAVEZ 10.07.2009.n
            UtilityCaja.obtieneImpresTicket(this, tblUsuariosCaja);
            //JCHAVEZ 10.07.2009.n
            cargaMotivo(); //cargar descripcion del motivo de la anulacion del pedido

            //JCORTEZ 13.01.2010 Se valida que la caja este abierta y se bloquea al mismo tiempo.
            VariablesCaja.vNumCaja = tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 2).toString();
            log.debug("::::::JCORTEZ: validando caja aperturada:::::");
            if (!validaCajaAbierta())
                return;

            if (FarmaVariables.vAceptar) {

                /**
               * LTAVARA 11.09.2014  INICIO
               * Copia de dlgDetalleAnulacionPedido - metodo de anulación
                * */
                //verifica si es un pedido virtual
                log.debug("JCALLO VariablesCaja.vIndPedidoConProdVirtual :" +
                          VariablesCaja.vIndPedidoConProdVirtual);
                evaluaPedidoProdVirtual(VariablesCaja.vNumPedVta_Anul); //obtiene VariablesCaja.vIndPedidoConProdVirtual
                log.debug("JCALLO VariablesCaja.vIndPedidoConProdVirtual :" +
                          VariablesCaja.vIndPedidoConProdVirtual);

                //jcallo flag que indicara si se validara o no los minutos transcurridos
                String vFlagValidarMinMatriz = FarmaConstants.INDICADOR_S;
                //no quitar esta variable
                log.info("JCALLO VariablesCaja.vIndPedidoConProdVirtual :" +
                         VariablesCaja.vIndPedidoConProdVirtual);
                log.info("JCALLO VariablesCaja.vIndAnulacionConReclamoNavsat :" +
                         VariablesCaja.vIndAnulacionConReclamoNavsat);

                /**************INICIO RECARGA VIRTUALES*****************/
                if (VariablesCaja.vIndPedidoConProdVirtual && !VariablesCaja.vIndAnulacionConReclamoNavsat) {
                    //JCHAVEZ 23122009 inicio
                    log.info("VariablesCaja.vIndLineaADMCentral antes: " + VariablesCaja.vIndLineaADMCentral);

                    if (true) {
                        obtieneInfoPedidoVirtual(); //obtiene  VariablesVirtual.vArrayList_InfoProdVirtual
                        log.info("JCALLO VariablesVirtual.vArrayList_InfoProdVirtual :" +
                                 VariablesVirtual.vArrayList_InfoProdVirtual);
                        if (VariablesVirtual.vArrayList_InfoProdVirtual.size() > 0) {
                            log.info("JCALLO seteando variables ...");
                            colocaInfoPedidoVirtual(); //setea las variables
                        } else {
                            log.info("JCALLO: no encontro informacion del pedido !");
                            return; //sale del metodo
                        }
                    }
                }
                /**
           * LTAVARA 11.09.2014  FIN
           * Copia de dlgDetalleAnulacionPedido - metodo de anulación
            * */
                cerrarVentana(isAnul());
            }

        } else {
            UtilityCaja.obtieneImpresTicket(this,
                                            tblUsuariosCaja); //antes de anular se sabe donde se imprimira el comprobante de anulacion ticket.

            log.debug("jcallo:NO es nota de credito");
            log.debug("JCORTEZ: antes de cargar motivo /VariablesCaja.vSecImprLocalTicket--> " +
                      VariablesCaja.vSecImprLocalTicket);
            cargaMotivo(); //cargar descripcion del motivo de la anulacion del pedido

            //JCORTEZ 13.01.2010 Se valida que la caja este abierta y se bloquea al mismo tiempo.
            VariablesCaja.vNumCaja = tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 2).toString();
            log.debug("::::::JCORTEZ: validando caja aperturada:::::");
            if (!validaCajaAbierta())
                return;

            if (FarmaVariables.vAceptar) {
                //verifica si es un pedido virtual
                log.debug("JCALLO VariablesCaja.vIndPedidoConProdVirtual :" +
                          VariablesCaja.vIndPedidoConProdVirtual);
                evaluaPedidoProdVirtual(VariablesCaja.vNumPedVta_Anul); //obtiene VariablesCaja.vIndPedidoConProdVirtual
                log.debug("JCALLO VariablesCaja.vIndPedidoConProdVirtual :" +
                          VariablesCaja.vIndPedidoConProdVirtual);

                //jcallo flag que indicara si se validara o no los minutos transcurridos
                String vFlagValidarMinMatriz = FarmaConstants.INDICADOR_S;
                //no quitar esta variable
                log.info("JCALLO VariablesCaja.vIndPedidoConProdVirtual :" +
                         VariablesCaja.vIndPedidoConProdVirtual);
                log.info("JCALLO VariablesCaja.vIndAnulacionConReclamoNavsat :" +
                         VariablesCaja.vIndAnulacionConReclamoNavsat);

                /**************INICIO RECARGA VIRTUALES*****************/
                if (VariablesCaja.vIndPedidoConProdVirtual && !VariablesCaja.vIndAnulacionConReclamoNavsat) {
                    //JCHAVEZ 23122009 inicio
                    log.info("VariablesCaja.vIndLineaADMCentral antes: " + VariablesCaja.vIndLineaADMCentral);
                    if (true) {
                        obtieneInfoPedidoVirtual(); //obtiene  VariablesVirtual.vArrayList_InfoProdVirtual
                        log.info("JCALLO VariablesVirtual.vArrayList_InfoProdVirtual :" +
                                 VariablesVirtual.vArrayList_InfoProdVirtual);
                        if (VariablesVirtual.vArrayList_InfoProdVirtual.size() > 0) {
                            log.info("JCALLO seteando variables ...");
                            colocaInfoPedidoVirtual(); //setea las variables
                        } else {
                            log.info("JCALLO: no encontro informacion del pedido !");
                            return; //sale del metodo
                        }
                    }
                }

                boolean isAnulOk = true;

                isAnulOk = isAnul();   
                
                if (vIndAnular && isAnulOk) //ASOSA - 09/07/2014
                {

                    if (!anularTransaccionCMR()) {
                        return;
                    }

                    if (procesoAnulaPedido_F11(vFlagValidarMinMatriz)) { //anulacion de to_do pedido(cebecera y detalle)

                        //LLEIVA - 06-Nov-2013  Se realiza la conciliacion de la anulacion si se realizo el pago mediante un Pinpad.
                        if (dlgAnularTransPinpad != null) { //se obtiene el numero de pedido negativo
                            String numPedNeg =
                                facadeRecaudacion.getNumPedidoNegativo(VariablesCaja.vNumPedVta_Anul);
                            dlgAnularTransPinpad.setPedidoNegativo(numPedNeg);

                            //se invoca a la funcion de conciliacion
                            dlgAnularTransPinpad.conciliacionAnulacionPinpad();
                        }

                        //GFONSECA 08/11/2013 Conciliacion anulacion venta CMR
                        if (dlgProcesarVentaCMR != null) {
                            if (dlgProcesarVentaCMR.isBRptTrsscAnul()) {
                                String numPedNeg =
                                    facadeRecaudacion.getNumPedidoNegativo(VariablesCaja.vNumPedVta_Anul);
                                dlgProcesarVentaCMR.setStrNumPedNegativo(numPedNeg);
                                dlgProcesarVentaCMR.procesarConciliacionAnul();
                            }
                        }
                        /**
                           * Nueva funcionalidad para pedido con producto virtual
                           * agregado x LMesia 22/01/2007
                           */

                        if (VariablesCaja.vIndPedidoConProdVirtual &&
                            !VariablesCaja.vIndAnulacionConReclamoNavsat) {
                            try {
                                UtilityCaja.actualizaInfoPedidoVirtualAnulado(VariablesCaja.vNumPedVta_Anul);
                                FarmaUtility.aceptarTransaccion();
                            } catch (SQLException sql) {
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.showMessage(this,
                                                         "Error al actualizar informacion del pedido virtual anulado.\n" +
                                        sql.getMessage(), tblUsuariosCaja);
                            }
                        }

                        // KMONCADA 18.12.2014 SOLO EN EL CASO DE NO ELECTRONICO
                        if (!EposVariables.vFlagComprobanteE) {
                            //Abrir Gabeta
                            //UtilityCaja.abrirGabeta(myParentFrame, false);
                            //GFonseca 27.12.2013 Se añade nuevo metodo de abrir gabeta.
                            UtilityCaja.abrirGabeta(myParentFrame, false, VariablesCaja.vNumPedVta_Anul);
                        }
                        cerrarVentana(true);
                    } else {
                        cerrarVentana(false); //JCHAVEZ 2312009
                        log.debug("Despues de cerrar en el convenio");
                    }
                }
                //JCHAVEZ 23122009 fin
            }
        }
        log.debug("*** FI evento clic en tecla F11");        
    }
}
