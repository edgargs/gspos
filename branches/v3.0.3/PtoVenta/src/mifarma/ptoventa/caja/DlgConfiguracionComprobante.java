package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;

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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgConfiguracionComprobante extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgConfiguracionComprobante.class);

    /** Almacena el Objeto Frame de la Aplicación - Ventana Principal */
    private Frame myParentFrame;

    /** Almacena el Objeto TableModel */
    private FarmaTableModel tableModelImpresoras;

    private JPanel jContentPane = new JPanel();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel pnlEncabezado = new JPanel();

    private JPanel pnlTitulos = new JPanel();

    private JScrollPane scrListaImpresoras = new JScrollPane();

    private JPanelTitle pnlFooter = new JPanelTitle();

    private JLabel lblEtiquetaCajero = new JLabel();

    private XYLayout xYLayout1 = new XYLayout();

    private JLabel lblCajero = new JLabel();

    private JLabel lblTituloCaja = new JLabel();

    private JLabel lblCaja = new JLabel();

    private JButton btnRelacionImpresoras = new JButton();

    private XYLayout xYLayout2 = new XYLayout();

    private JLabel lblComprobante = new JLabel();

    private XYLayout xYLayout3 = new XYLayout();

    private JLabel lblGuion = new JLabel();

    private JTextField txtNumeroComprobante = new JTextField();

    private JTextField txtComprobante = new JTextField();

    private JComboBox cmbSerie = new JComboBox();

    private JButton btnNumeroComprobante = new JButton();

    private JTable tblListaImpresoras = new JTable();

    private JLabelFunction lblCerrar = new JLabelFunction();

    private JLabelFunction lblAceptar = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgConfiguracionComprobante() {
        this(null, "", false);
    }

    public DlgConfiguracionComprobante(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(658, 268));
        this.getContentPane().setLayout(borderLayout1);
        this.setBackground(Color.white);
        this.setTitle("Configurar Serie - Comprobante");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        pnlEncabezado.setBounds(new Rectangle(10, 10, 635, 30));
        pnlEncabezado.setBackground(new Color(43, 141, 39));
        pnlEncabezado.setLayout(xYLayout1);
        pnlEncabezado.setBorder(BorderFactory.createTitledBorder(""));
        pnlTitulos.setBounds(new Rectangle(10, 45, 635, 25));
        pnlTitulos.setBackground(new Color(255, 130, 14));
        pnlTitulos.setLayout(xYLayout2);
        scrListaImpresoras.setBounds(new Rectangle(10, 70, 635, 110));
        scrListaImpresoras.setBackground(new Color(255, 130, 14));
        pnlFooter.setBounds(new Rectangle(10, 180, 635, 30));
        pnlFooter.setLayout(xYLayout3);
        lblEtiquetaCajero.setText("Cajero:");
        lblEtiquetaCajero.setFont(new Font("SansSerif", 1, 12));
        lblEtiquetaCajero.setForeground(Color.white);
        lblCajero.setText(" ");
        lblCajero.setFont(new Font("SansSerif", 1, 12));
        lblCajero.setForeground(Color.white);
        lblTituloCaja.setText("Caja:");
        lblTituloCaja.setFont(new Font("SansSerif", 1, 12));
        lblTituloCaja.setForeground(Color.white);
        lblCaja.setText(" ");
        lblCaja.setFont(new Font("SansSerif", 1, 12));
        lblCaja.setForeground(Color.white);
        btnRelacionImpresoras.setText("Relacion de Impresoras:");
        btnRelacionImpresoras.setFont(new Font("SansSerif", 1, 11));
        btnRelacionImpresoras.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRelacionImpresoras.setBorderPainted(false);
        btnRelacionImpresoras.setContentAreaFilled(false);
        btnRelacionImpresoras.setDefaultCapable(false);
        btnRelacionImpresoras.setFocusPainted(false);
        btnRelacionImpresoras.setForeground(Color.white);
        btnRelacionImpresoras.setHorizontalAlignment(SwingConstants.LEFT);
        btnRelacionImpresoras.setRequestFocusEnabled(false);
        btnRelacionImpresoras.setMnemonic('R');
        btnRelacionImpresoras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionImpresoras_actionPerformed(e);
            }
        });
        lblComprobante.setText("Comprobante:");
        lblComprobante.setFont(new Font("SansSerif", 1, 12));
        lblComprobante.setForeground(Color.white);
        lblGuion.setText("-");
        lblGuion.setFont(new Font("SansSerif", 1, 12));
        lblGuion.setForeground(Color.white);
        txtNumeroComprobante.setFont(new Font("SansSerif", 1, 12));
        txtNumeroComprobante.setDocument(new FarmaLengthText(7));
        txtNumeroComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumeroComprobante_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNumeroComprobante_keyTyped(e);
            }
        });
        txtComprobante.setFont(new Font("SansSerif", 1, 12));
        txtComprobante.setEditable(false);
        txtComprobante.setFocusable(false);
        txtComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtComprobante_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtComprobante_keyReleased(e);
            }
        });
        cmbSerie.setFont(new Font("SansSerif", 1, 12));
        cmbSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbSerie_keyPressed(e);
            }

        });
        btnNumeroComprobante.setText("No. Comprobante:");
        btnNumeroComprobante.setMnemonic('C');
        btnNumeroComprobante.setFont(new Font("SansSerif", 1, 12));
        btnNumeroComprobante.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnNumeroComprobante.setHorizontalAlignment(SwingConstants.LEFT);
        btnNumeroComprobante.setBorderPainted(false);
        btnNumeroComprobante.setContentAreaFilled(false);
        btnNumeroComprobante.setDefaultCapable(false);
        btnNumeroComprobante.setFocusPainted(false);
        btnNumeroComprobante.setRequestFocusEnabled(false);
        btnNumeroComprobante.setForeground(Color.white);
        btnNumeroComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNumeroComprobante_actionPerformed(e);
            }
        });
        tblListaImpresoras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaImpresoras_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblListaImpresoras_keyReleased(e);
            }
        });
        lblCerrar.setText("[ ESC ] Cerrar");
        lblCerrar.setBounds(new Rectangle(545, 215, 95, 20));
        lblAceptar.setText("[ F11 ] Aceptar");
        lblAceptar.setBounds(new Rectangle(430, 215, 105, 20));
        jContentPane.add(lblAceptar, null);
        jContentPane.add(lblCerrar, null);
        jContentPane.add(pnlFooter, null);
        scrListaImpresoras.getViewport().add(tblListaImpresoras, null);
        jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(pnlTitulos, null);
        pnlFooter.add(btnNumeroComprobante, new XYConstraints(250, 5, 115, 20));
        pnlFooter.add(cmbSerie, new XYConstraints(370, 5, 75, 20));
        pnlFooter.add(txtComprobante, new XYConstraints(100, 5, 135, 20));
        pnlFooter.add(txtNumeroComprobante, new XYConstraints(480, 5, 85, 20));
        pnlFooter.add(lblGuion, new XYConstraints(460, 5, 10, 20));
        pnlFooter.add(lblComprobante, new XYConstraints(10, 5, 85, 20));
        pnlTitulos.add(btnRelacionImpresoras, new XYConstraints(5, 0, 155, 25));
        pnlEncabezado.add(lblCaja, new XYConstraints(370, -5, 60, 25));
        pnlEncabezado.add(lblTituloCaja, new XYConstraints(325, -5, 30, 25));
        pnlEncabezado.add(lblCajero, new XYConstraints(65, 0, 240, 20));
        pnlEncabezado.add(lblEtiquetaCajero, new XYConstraints(0, -5, 45, 25));
        jContentPane.add(pnlEncabezado, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        log.debug("initialize");
        initCombo();
        initTableImpresoras();
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableImpresoras() {
        tableModelImpresoras =
                new FarmaTableModel(ConstantsCaja.columnsListaImpresoras, ConstantsCaja.defaultValuesListaImpresoras,
                                    0);
        FarmaUtility.initSimpleList(tblListaImpresoras, tableModelImpresoras, ConstantsCaja.columnsListaImpresoras);
        cargaListaImpresoras();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(tblListaImpresoras);
        FarmaUtility.centrarVentana(this);
        mostrarDatosCabecera();
        if (tieneRegistroSeleccionado(tblListaImpresoras))
            mostrarRegSeleccionado();
    }

    private void tblListaImpresoras_keyPressed(KeyEvent e) {
        // / if(tieneRegistroSeleccionado(tblListaImpresoras))
        // mostrarRegSeleccionado();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            FarmaUtility.moveFocus(cmbSerie);
        } else {
            chkkeyPressed(e);
        }
    }

    private void txtNumeroComprobante_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            completarNumero();
            //FarmaUtility.moveFocus(tblListaImpresoras);

        } else {
            chkkeyPressed(e);
        }
    }

    private void btnRelacionImpresoras_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaImpresoras);
    }

    private void txtComprobante_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbSerie);
        }
        chkkeyPressed(e);
    }

    private void cmbSerie_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumeroComprobante);
        }
        chkkeyPressed(e);
    }

    private void txtNumeroComprobante_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroComprobante, e);
    }

    private void btnNumeroComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbSerie);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkkeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            completarNumero();
            if (tieneRegistroSeleccionado(tblListaImpresoras))
                if (datosValidados()) {
                    String mensaje = "";
                    mensaje = "El número de comprobante ingresado es ";
                    int serieOrig = 0;
                    int serieNuev = 0;
                    int dif = 0;
                    obtieneMaximoConfigCaja();
                    //serieOrig = Integer.parseInt(tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 4).toString().trim());
                    serieNuev = Integer.parseInt(txtNumeroComprobante.getText().trim());

                    if (serieNuev > Integer.parseInt(VariablesCaja.vValorMax.trim())) {
                        dif = serieNuev - Integer.parseInt(VariablesCaja.vValorMax.trim());
                        mensaje = mensaje + " MAYOR. Existe una diferencia de " + dif + " comprobante(s). ";
                        VariablesCaja.vTipoAccion = FarmaConstants.INDICADOR_S;
                    }
                    if (serieNuev < Integer.parseInt(VariablesCaja.vValorMax.trim())) {
                        dif = Integer.parseInt(VariablesCaja.vValorMax.trim()) - serieNuev;
                        mensaje = mensaje + " MENOR. Existe una diferencia de " + dif + " comprobante(s). ";
                        VariablesCaja.vTipoAccion = FarmaConstants.INDICADOR_N;
                    }
                    if (serieNuev == Integer.parseInt(VariablesCaja.vValorMax.trim())) {
                        mensaje = mensaje + " IGUAL. Existe una diferencia de " + dif + " comprobante(s). ";
                        VariablesCaja.vTipoAccion = FarmaConstants.INDICADOR_N;
                    }
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, mensaje + "\n" +
                            FarmaPRNUtility.llenarBlancos(30) + "Esta seguro que desea efectuar los cambios?")) {
                        if (VariablesCaja.vTipoAccion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            cargaLogin();
                        } else {
                            efectuaTransaccion();
                        }
                    }
                }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaImpresoras() {
        try {
            DBCaja.getListaImpresorasUsuario(tableModelImpresoras);

            if (tableModelImpresoras.getRowCount() == 0) {
                if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS) ||
                    FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
                    DBCaja.getListaImpresorasLocal(tableModelImpresoras);
                }
            }

            if (tblListaImpresoras.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaImpresoras, tableModelImpresoras, 0, FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de Impresoras");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al cargar la lista de impresoras  - \n" +
                    e.getMessage(), txtNumeroComprobante);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtComprobante_keyReleased(KeyEvent e) {
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void mostrarRegSeleccionado() {
        txtComprobante.setText(tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(),
                                                             2).toString().trim());
        txtNumeroComprobante.setText(tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(),
                                                                   4).toString().trim());
        VariablesCaja.vTipComp =
                tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 7).toString().trim();
        initCombo();
        cmbSerie.setSelectedItem(tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(),
                                                               3).toString().trim());
        VariablesCaja.vTipComp =
                tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 7).toString().trim();
        log.debug("VariablesCaja.vTipComp : " + VariablesCaja.vTipComp);
    }

    private void initCombo() {
        cmbSerie.removeAllItems();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(FarmaVariables.vNuSecUsu.trim());
        parametros.add(VariablesCaja.vTipComp.trim());
        FarmaLoadCVL.loadCVLFromSP(this.cmbSerie, "cmbSerie", "PTOVENTA_CAJ.CAJ_LISTA_SERIES_IMPRESORA_CAJ(?,?,?,?)",
                                   parametros, false, true);
    }

    private void tblListaImpresoras_keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ENTER) {
            if (tieneRegistroSeleccionado(tblListaImpresoras)) {
                mostrarRegSeleccionado();
            }
        }
    }

    private void mostrarDatosCabecera() {
        lblCajero.setText(FarmaVariables.vNomUsu + " " + FarmaVariables.vPatUsu + " " + FarmaVariables.vMatUsu);
        try {
            VariablesCaja.vNumCaja = DBCaja.obtenerCajaUsuario();
        } catch (Exception ex) {
            VariablesCaja.vNumCaja = "1";
            log.error("", ex);
            FarmaUtility.showMessage(this, "Ocurrio un error al mostrar los datos - \n" +
                    ex.getMessage(), txtNumeroComprobante);
        }
        lblCaja.setText("" + VariablesCaja.vNumCaja);
    }

    private boolean datosValidados() {
        boolean rpta = true;
        if (txtNumeroComprobante.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese el Número de comprobante", txtNumeroComprobante);
            return false;
        }
        if (cmbSerie.getSelectedItem().toString().trim().equals("")) {
            FarmaUtility.showMessage(this, "Seleccione el Número de serie", cmbSerie);
            return false;
        }
        return rpta;
    }

    private void guardarCambios() throws SQLException {
        String secImpr = "";
        secImpr = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 0).toString().trim();
        DBCaja.configuraCaja(secImpr, cmbSerie.getSelectedItem().toString().trim(),
                             txtNumeroComprobante.getText().trim());
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void completarNumero() {
        boolean afirma;
        afirma = FarmaUtility.isInteger(txtNumeroComprobante.getText().trim());
        if (afirma) {
            txtNumeroComprobante.setText(FarmaUtility.caracterIzquierda(txtNumeroComprobante.getText().trim(), 7,
                                                                        "0"));
        }

    }

    private String obtieneMaximoConfigCaja() {
        try {
            VariablesCaja.vValorMax = DBCaja.obtenerValorMaximoConfigCaja(VariablesCaja.vTipComp);
            log.debug("valorRetorno : " + VariablesCaja.vValorMax.trim());
            return VariablesCaja.vValorMax.trim();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al obtener el valor \n" +
                    sql.getMessage(), tblListaImpresoras);
            return null;
        }
    }

    private void cargaLogin() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, "Validacion de Usuario", true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            if (FarmaVariables.vAceptar) {
                efectuaTransaccion();
                FarmaVariables.vNuSecUsu = numsec;
                FarmaVariables.vIdUsu = idusu;
                FarmaVariables.vNomUsu = nomusu;
                FarmaVariables.vPatUsu = apepatusu;
                FarmaVariables.vMatUsu = apematusu;
                FarmaVariables.vAceptar = false;
            } else
                cerrarVentana(false);
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),
                                     tableModelImpresoras);
        }
    }

    private void efectuaTransaccion() {
        try {
            guardarCambios();
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "La operación se realizó correctamente", tblListaImpresoras);
            cargaListaImpresoras();
        } catch (SQLException ex) {
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            FarmaUtility.showMessage(this, "Ocurrió un error al grabar : \n" +
                    ex.getMessage(), tblListaImpresoras);
        }

    }
}
