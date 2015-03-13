package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgVerificacionComprobantes extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgVerificacionComprobantes.class);

    Frame myParentFrame;

    FarmaTableModel tableModelListaComprobantes;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel jContentPane = new JPanel();

    private JScrollPane scrListaPedComp = new JScrollPane();

    private JPanel pnlHeaderLista = new JPanel();

    private JButton btnPedComp = new JButton();

    private JPanel pnlDatosUser = new JPanel();

    private JLabel lblTurno = new JLabel();

    private JLabel lblTurno_T = new JLabel();

    private JLabel lblCaja = new JLabel();

    private JLabel lblUsuario = new JLabel();

    private JLabel lblCaja_T = new JLabel();

    private JLabel lblUsuario_T = new JLabel();

    private JTable tblListaComprobantes = new JTable();

    private JLabelFunction lblCerrar = new JLabelFunction();

    private JButtonLabel btnRangoFec = new JButtonLabel();

    private JTextFieldSanSerif txtFechaInicial = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtFechaFinal = new JTextFieldSanSerif();

    private JButton btnBuscar = new JButton();

    private JLabelFunction lblCorrComp = new JLabelFunction();

    private JLabelFunction lblVercompDesf = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblOrdenar = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgVerificacionComprobantes() {
        this(null, "", false);
    }

    public DlgVerificacionComprobantes(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(644, 444));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Relacion Pedidos/Comprobantes");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(494, 346));
        scrListaPedComp.setBounds(new Rectangle(10, 100, 615, 240));
        scrListaPedComp.setBackground(new Color(255, 130, 14));
        pnlHeaderLista.setBounds(new Rectangle(10, 75, 615, 25));
        pnlHeaderLista.setBackground(new Color(255, 130, 14));
        pnlHeaderLista.setLayout(null);
        btnPedComp.setText("Lista Pedidos Comprobantes");
        btnPedComp.setBounds(new Rectangle(10, 0, 180, 25));
        btnPedComp.setBackground(new Color(255, 130, 14));
        btnPedComp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnPedComp.setBorderPainted(false);
        btnPedComp.setContentAreaFilled(false);
        btnPedComp.setFocusPainted(false);
        btnPedComp.setDefaultCapable(false);
        btnPedComp.setFont(new Font("SansSerif", 1, 11));
        btnPedComp.setForeground(Color.white);
        btnPedComp.setHorizontalAlignment(SwingConstants.LEFT);
        btnPedComp.setMnemonic('l');
        btnPedComp.setRequestFocusEnabled(false);
        btnPedComp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedComp_actionPerformed(e);
            }
        });
        btnPedComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButton1_keyPressed(e);
            }
        });
        pnlDatosUser.setBounds(new Rectangle(10, 5, 615, 65));
        pnlDatosUser.setBorder(BorderFactory.createTitledBorder(""));
        pnlDatosUser.setBackground(new Color(43, 141, 39));
        pnlDatosUser.setLayout(null);
        lblTurno.setText("2");
        lblTurno.setBounds(new Rectangle(505, 10, 40, 15));
        lblTurno.setFont(new Font("SansSerif", 1, 11));
        lblTurno.setForeground(Color.white);
        lblTurno_T.setText("Turno :");
        lblTurno_T.setBounds(new Rectangle(440, 10, 55, 15));
        lblTurno_T.setFont(new Font("SansSerif", 1, 11));
        lblTurno_T.setForeground(Color.white);
        lblCaja.setText("1");
        lblCaja.setBounds(new Rectangle(380, 10, 35, 15));
        lblCaja.setFont(new Font("SansSerif", 1, 11));
        lblCaja.setForeground(Color.white);
        lblUsuario.setText("Andres Moreno");
        lblUsuario.setBounds(new Rectangle(95, 10, 200, 15));
        lblUsuario.setFont(new Font("SansSerif", 1, 11));
        lblUsuario.setForeground(Color.white);
        lblCaja_T.setText("Caja :");
        lblCaja_T.setBounds(new Rectangle(315, 10, 50, 15));
        lblCaja_T.setFont(new Font("SansSerif", 1, 11));
        lblCaja_T.setForeground(Color.white);
        lblUsuario_T.setText("Usuario :");
        lblUsuario_T.setBounds(new Rectangle(20, 10, 60, 15));
        lblUsuario_T.setFont(new Font("SansSerif", 1, 11));
        lblUsuario_T.setForeground(Color.white);
        tblListaComprobantes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaComprobantes_keyPressed(e);
            }
        });
        lblCerrar.setText("[ ESC ] Cerrar");
        lblCerrar.setBounds(new Rectangle(510, 385, 115, 20));
        btnRangoFec.setText("Rango de fechas :");
        btnRangoFec.setBounds(new Rectangle(20, 35, 110, 20));
        btnRangoFec.setMnemonic('f');
        btnRangoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFec_actionPerformed(e);
            }
        });
        txtFechaInicial.setBounds(new Rectangle(130, 35, 90, 20));
        txtFechaInicial.setDocument(new FarmaLengthText(10));
        txtFechaInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }
        });
        txtFechaFinal.setBounds(new Rectangle(235, 35, 90, 20));
        txtFechaFinal.setDocument(new FarmaLengthText(10));
        txtFechaFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFinal_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFinal_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(340, 35, 75, 20));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        lblCorrComp.setText("[F4] Corrección de comprobantes");
        lblCorrComp.setBounds(new Rectangle(10, 355, 205, 20));
        lblVercompDesf.setText("[F5] Ver comprobantes desfasados");
        lblVercompDesf.setBounds(new Rectangle(225, 355, 205, 20));
        lblF8.setText("[F8] Intercambiar Comprobante");
        lblF8.setBounds(new Rectangle(440, 355, 185, 20));
        lblOrdenar.setText("[F6] Ordenar");
        lblOrdenar.setBounds(new Rectangle(10, 380, 150, 20));
        scrListaPedComp.getViewport();
        pnlDatosUser.add(btnBuscar, null);
        pnlDatosUser.add(txtFechaFinal, null);
        pnlDatosUser.add(txtFechaInicial, null);
        pnlDatosUser.add(btnRangoFec, null);
        pnlDatosUser.add(lblTurno, null);
        pnlDatosUser.add(lblTurno_T, null);
        pnlDatosUser.add(lblCaja, null);
        pnlDatosUser.add(lblUsuario, null);
        pnlDatosUser.add(lblCaja_T, null);
        pnlDatosUser.add(lblUsuario_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblOrdenar, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblVercompDesf, null);
        jContentPane.add(lblCorrComp, null);
        jContentPane.add(lblCerrar, null);
        scrListaPedComp.getViewport().add(tblListaComprobantes, null);
        jContentPane.add(scrListaPedComp, null);
        pnlHeaderLista.add(btnPedComp, null);
        jContentPane.add(pnlHeaderLista, null);
        jContentPane.add(pnlDatosUser, null);
        // this.getContentPane().add(jContentPane, null);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;
        iniciarValoresFecha();
        initTableListaComprobantes();
        mostrarDatosCabecera();
        FarmaUtility.moveFocus(tblListaComprobantes);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaComprobantes() {
        tableModelListaComprobantes =
                new FarmaTableModel(ConstantsCaja.columnsListaComprobantesRangos, ConstantsCaja.defaultListaComprobantesRangos,
                                    0);
        FarmaUtility.initSimpleList(tblListaComprobantes, tableModelListaComprobantes,
                                    ConstantsCaja.columnsListaComprobantesRangos);
        cargaListaComprobantes();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaInicial);
    }

    private void tblListaComprobantes_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jButton1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnPedComp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaComprobantes);
    }

    private void btnRangoFec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaInicial);
    }

    private void txtFechaInicial_keyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblListaComprobantes,
        //		txtFechaInicial, 0);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFechaFinal);
        }
        chkKeyPressed(e);
    }

    private void txtFechaFinal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        }
        chkKeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (datosValidados()) {
            cargarValoresFec();
            cargaListaComprobantes();

            if (tblListaComprobantes.getRowCount() == 0) {
                FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtFechaInicial);
            } else {
                FarmaUtility.moveFocus(tblListaComprobantes);
            }

        }
    }

    private void txtFechaInicial_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaInicial, e);
    }

    private void txtFechaFinal_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFinal, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFechaInicial);
            else if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                if (tieneRegistroSeleccionado(tblListaComprobantes)) {
                    cargarRegistroSeleccionado();

                    if (VariablesCaja.vTipComp.equalsIgnoreCase("05"))
                        FarmaUtility.showMessage(this, "Esta función no está habilitada para tickets.",
                                                 txtFechaInicial);
                    else {
                        DlgIngresoSecuencias dlgIngresoSecuencias =
                            new DlgIngresoSecuencias(this.myParentFrame, "", true);
                        dlgIngresoSecuencias.setVisible(true);
                    }

                    if (FarmaVariables.vAceptar) {
                        cargaListaComprobantes();
                        FarmaVariables.vAceptar = false;
                    }
                }
            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (tblListaComprobantes.getRowCount() > 1) {
                FarmaUtility.ordenar(tblListaComprobantes, tableModelListaComprobantes, 7,
                                     FarmaConstants.ORDEN_ASCENDENTE);
                if (cargarCompDesfasados()) {
                    DlgListaCompDesfasados dlgListaCompDesfasados =
                        new DlgListaCompDesfasados(this.myParentFrame, "", true);
                    dlgListaCompDesfasados.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        FarmaUtility.findTextInJTable(tblListaComprobantes, VariablesCaja.vNumCompDesf.trim(), 7, 7);
                        FarmaVariables.vAceptar = false;
                    }
                } else {
                    FarmaUtility.showMessage(this, "No se encontraron comprobantes desfasados", txtFechaInicial);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFechaInicial);
            else if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                if (tblListaComprobantes.getRowCount() > 1) {
                    if (tieneRegistroSeleccionado(tblListaComprobantes)) {

                        cargarRegistroSeleccionado();
                        //marco fajardo 15/04/2009
                        if (VariablesCaja.vTipComp.equalsIgnoreCase("05"))
                            FarmaUtility.showMessage(this, "Esta función no está habilitada para tickets.",
                                                     txtFechaInicial);
                        else {
                            if (UtilityCaja.isPedidoConvenioMFBTL(tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(),
                                                                                                  3).toString().trim()))
                                FarmaUtility.showMessage(this,
                                                         "Esta prohibo hacer intercambio de un Pedido de CONVENIO.",
                                                         txtFechaInicial);
                            else {
                                DlgIntercambioComprobante dlgIntercambioComprobante =
                                    new DlgIntercambioComprobante(this.myParentFrame, "", true);
                                dlgIntercambioComprobante.setVisible(true);
                            }
                        }
                        //marco fajardo 15/04/2009

                        if (FarmaVariables.vAceptar) {
                            //FarmaUtility.findTextInJTable(tblListaComprobantes,
                            //		VariablesCaja.vNumCompDesf.trim(), 2, 2);
                            cargaListaComprobantes();
                            FarmaVariables.vAceptar = false;
                        }
                    }
                }
            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (tblListaComprobantes.getRowCount() > 0) {
                muestraComprobantesOrd();
            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaComprobantes() {
        try {
            DBCaja.getListaPedidosCompRangos(tableModelListaComprobantes);

            if (tblListaComprobantes.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaComprobantes, tableModelListaComprobantes, 7,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de ped comp");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener lista de comprobantes. \n " + e.getMessage(),
                                     tblListaComprobantes);
        }


    }

    private void mostrarDatosCabecera() {
        lblUsuario.setText(FarmaVariables.vNomUsu + " " + FarmaVariables.vPatUsu + " " + FarmaVariables.vMatUsu);

        try {
            VariablesCaja.vNumCaja = DBCaja.obtenerCajaUsuario().trim();
        } catch (Exception ex) {

            lblCaja.setVisible(false);
            lblCaja_T.setVisible(false);
            FarmaUtility.showMessage(this, "Error al obtener caja usuario. \n " + ex.getMessage(),
                                     tblListaComprobantes);
        }
        if (VariablesCaja.vNumCaja.trim().equals(".")) {
            lblCaja.setVisible(false);
            lblCaja_T.setVisible(false);
        }

        lblCaja.setText("" + VariablesCaja.vNumCaja);

        try {
            lblTurno.setText(DBCaja.obtenerTurnoActualCaja(VariablesCaja.vNumCaja));
        } catch (Exception ex) {
            //log.error("",ex);
            lblTurno.setVisible(false);
            lblTurno_T.setVisible(false);
            FarmaUtility.showMessage(this, "Error al obtener turno de caja. \n " + ex.getMessage(),
                                     tblListaComprobantes);

        }
    }

    private boolean datosValidados() {

        if (!FarmaUtility.validaFecha(txtFechaInicial.getText(), "dd/MM/yyyy") ||
            txtFechaInicial.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha Inicial correctamente", txtFechaInicial);
            return false;
        }
        if (!FarmaUtility.validaFecha(txtFechaFinal.getText(), "dd/MM/yyyy") ||
            txtFechaFinal.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha Final correctamente", txtFechaFinal);
            return false;
        }
        Date fecIni = FarmaUtility.getStringToDate(txtFechaInicial.getText().trim(), "dd/MM/yyyy");
        Date fecFin = FarmaUtility.getStringToDate(txtFechaFinal.getText().trim(), "dd/MM/yyyy");

        if (fecIni.after(fecFin)) {
            FarmaUtility.showMessage(this, "La Fecha Inicial no puede ser posterior a la Fecha Final",
                                     txtFechaInicial);
            return false;
        }
        return true;
    }

    private void iniciarValoresFecha() {
        String fecActual = "";
        try {
            fecActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (Exception ex) {
            log.error("", ex);
            java.util.Date fec = new java.util.Date();
            fecActual = fec.toString();
            FarmaUtility.showMessage(this, "Error al obtener la fecha actual. \n " + ex.getMessage(),
                                     tblListaComprobantes);
        }
        VariablesCaja.vFecIniVerComp = fecActual;
        VariablesCaja.vFecFinVerComp = fecActual;
        txtFechaInicial.setText(fecActual);
        txtFechaFinal.setText(fecActual);
    }

    private void cargarValoresFec() {
        VariablesCaja.vFecIniVerComp = txtFechaInicial.getText();
        VariablesCaja.vFecFinVerComp = txtFechaFinal.getText();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarRegistroSeleccionado() {
        VariablesCaja.vSecComprobante =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 8).toString().trim();
        VariablesCaja.vDesComp =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 1).toString().trim();
        VariablesCaja.vTipComp =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 9).toString().trim();
        VariablesCaja.vNumComp =
                tblListaComprobantes.getValueAt(tblListaComprobantes.getSelectedRow(), 10).toString().trim();
    }

    private boolean cargarCompDesfasados() {
        boolean rpta = true;
        double valorAct = 0;
        double valorSig = 0;
        String tipAct = "";
        String tipSig = "";
        String tipSerieAct = "";
        String tipSerieSig = "";

        String pCadenaAct = "";
        String pCadenaSig = "";
        int indexBusqueda = 10;

        if (VariablesCaja.vTipOrdComprobantes.trim().equals(ConstantsCaja.TIP_ORD_CORRELATIVO)) {
            indexBusqueda = 11;
        }

        ArrayList elementoLista;
        VariablesCaja.listaCompsDesfasados = new ArrayList();


        for (int i = 0; i < tblListaComprobantes.getRowCount() - 1; i++) {

            pCadenaAct = tblListaComprobantes.getValueAt(i, indexBusqueda).toString();
            pCadenaSig = tblListaComprobantes.getValueAt(i + 1, indexBusqueda).toString();

            valorAct =
                    Double.parseDouble(pCadenaAct.trim().substring(4)); //tblListaComprobantes.getValueAt(i, indexBusqueda).toString());
            valorSig =
                    Double.parseDouble(pCadenaSig.trim().substring(4)); //tblListaComprobantes.getValueAt(i + 1,indexBusqueda).toString());
            tipAct = tblListaComprobantes.getValueAt(i, 9).toString();
            tipSig = tblListaComprobantes.getValueAt(i + 1, 9).toString();

            //log.debug("tblListaComprobantes.size()="+tableModelListaComprobantes.getRow(i));
            tipSerieAct = pCadenaAct.trim().substring(0, 3);
            tipSerieSig = pCadenaSig.substring(0, 3);
            //log.debug("tipSerieAct:"+tipSerieAct+ " - "+"tipSerieSig: " + tipSerieSig);
            //log.debug("valorAct:"+valorAct+ " - "+"valorSig: " + valorSig);
            if (valorAct + 1 != valorSig && tipAct.equals(tipSig) && tipSerieAct.equals(tipSerieSig)) {
                // si no son correlativos

                log.debug("tipSerieAct:" + tipSerieAct + " - " + "tipSerieSig: " + tipSerieSig);
                log.debug("valorAct:" + valorAct + " - " + "valorSig: " + valorSig);

                elementoLista = new ArrayList();
                elementoLista.add(tblListaComprobantes.getValueAt(i, 1).toString().trim());
                elementoLista.add(tblListaComprobantes.getValueAt(i, 2).toString().trim());
                elementoLista.add(tblListaComprobantes.getValueAt(i, 3).toString().trim());
                elementoLista.add(tblListaComprobantes.getValueAt(i, 6).toString().trim());
                elementoLista.add(tblListaComprobantes.getValueAt(i, 7).toString().trim());
                log.debug("Adding:" + tblListaComprobantes.getValueAt(i, 2).toString().trim());
                VariablesCaja.listaCompsDesfasados.add(elementoLista);
            }
        }
        log.debug("listaCompsDesfasados.size()=" + VariablesCaja.listaCompsDesfasados.size());
        if (VariablesCaja.listaCompsDesfasados.size() == 0) {
            rpta = true;
        }
        return rpta;
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void muestraComprobantesOrd() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_DESCRIP_CAMPO = { "Numero Comprobante", "Correlativo" };
        String[] IND_CAMPO = { "7", "11" };

        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLECORRECCIONCOMPROBANTES;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            log.debug("Campo " + VariablesReporte.vCampo);
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblListaComprobantes, tableModelListaComprobantes, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblListaComprobantes.repaint();
        }
    }

}
