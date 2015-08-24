package mifarma.ptoventa.cliente;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.cliente.reference.VariablesCliente;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgBuscaClienteJuridico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      23.02.2006   Creación<br>
 * PAULO       03.03.2006   Modificacion
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgBuscaClienteJuridico extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgBuscaClienteJuridico.class);

    private Frame myParentFrame;
    FarmaTableModel tableModel;
    public FarmaTableModel tableModelListaClienteJuridico;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JPanelTitle pnlRelacionCliente = new JPanelTitle();
    private JScrollPane scrClienteJuridico = new JScrollPane();
    public JTable tblClienteJuridico = new JTable();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacion = new JButtonLabel();
    private JButtonLabel btnClienteJuridico = new JButtonLabel();
    private JTextFieldSanSerif txtClienteJuridico = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JPanel jPanel1 = new JPanel();
    private JRadioButton rbtJuridico = new JRadioButton();
    private JRadioButton rbtNatural = new JRadioButton();
    // private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgBuscaClienteJuridico() {
        this(null, "", false);
    }

    public DlgBuscaClienteJuridico(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(697, 439));
        this.getContentPane().setLayout(null);
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Relacion de Clientes Jurídicos");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlCliente.setBounds(new Rectangle(10, 70, 670, 40));
        pnlRelacionCliente.setBounds(new Rectangle(10, 115, 670, 25));
        scrClienteJuridico.setBounds(new Rectangle(10, 140, 670, 235));
        tblClienteJuridico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblClienteJuridico_keyPressed(e);
            }
        });
        lblF3.setBounds(new Rectangle(15, 380, 115, 20));
        lblF3.setText("[ F3 ] Crear");
        lblF4.setBounds(new Rectangle(145, 380, 125, 20));
        lblF4.setText("[ F4 ] Modificar");
        lblEsc.setBounds(new Rectangle(595, 380, 85, 20));
        lblEsc.setText("[ Esc ] Cerrar");
        btnRelacion.setText("Relacion de Clientes ");
        btnRelacion.setBounds(new Rectangle(10, 5, 180, 15));
        btnRelacion.setMnemonic('r');
        btnRelacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacion_actionPerformed(e);
            }
        });
        btnClienteJuridico.setText("Cliente :");
        btnClienteJuridico.setBounds(new Rectangle(20, 10, 105, 25));
        btnClienteJuridico.setMnemonic('c');
        btnClienteJuridico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnClienteJuridico_actionPerformed(e);
            }
        });
        txtClienteJuridico.setBounds(new Rectangle(140, 10, 255, 20));
        txtClienteJuridico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtClienteJuridico_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtClienteJuridico_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(430, 10, 110, 25));
        btnBuscar.setBackground(SystemColor.control);
        btnBuscar.setMnemonic('b');
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        jPanel1.setBounds(new Rectangle(175, 15, 375, 35));
        jPanel1.setBorder(BorderFactory.createTitledBorder(""));
        jPanel1.setBackground(Color.white);
        jPanel1.setLayout(null);
        rbtJuridico.setText("JURIDICO");
        rbtJuridico.setBounds(new Rectangle(205, 5, 115, 25));
        rbtJuridico.setBackground(Color.white);
        rbtJuridico.setFont(new Font("SansSerif", 1, 14));
        rbtJuridico.setForeground(new Color(43, 141, 39));
        rbtJuridico.setFocusPainted(false);
        rbtJuridico.setRequestFocusEnabled(false);
        rbtJuridico.setFocusable(false);
        rbtNatural.setText("NATURAL");
        rbtNatural.setBounds(new Rectangle(90, 5, 95, 25));
        rbtNatural.setBackground(Color.white);
        rbtNatural.setFont(new Font("SansSerif", 1, 14));
        rbtNatural.setForeground(new Color(43, 141, 39));
        rbtNatural.setFocusPainted(false);
        rbtNatural.setRequestFocusEnabled(false);
        rbtNatural.setFocusable(false);
        rbtNatural.setSelected(true);
        /*
    lblF5.setBounds(new Rectangle(420, 415, 185, 20));
    lblF5.setText("[ F2 ] Ver Tarjeta Cliente");
    */
        lblF6.setBounds(new Rectangle(285, 380, 175, 20));
        lblF6.setText("[ F8 ] Cambia Tipo Cliente");
        lblF8.setBounds(new Rectangle(475, 380, 110, 20));
        lblF8.setText("[ F11 ] Aceptar");
        jPanel1.add(rbtJuridico, null);
        jPanel1.add(rbtNatural, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblF6, null);
        //  jContentPane.add(lblF5, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF3, null);
        scrClienteJuridico.getViewport().add(tblClienteJuridico, null);
        jContentPane.add(scrClienteJuridico, null);
        pnlRelacionCliente.add(btnRelacion, null);
        jContentPane.add(pnlRelacionCliente, null);
        jContentPane.add(pnlCliente, null);
        pnlCliente.add(btnBuscar, null);
        pnlCliente.add(txtClienteJuridico, null);
        pnlCliente.add(btnClienteJuridico, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTableListaClienteJuridico();
        seleccionTipoCliente();
        FarmaVariables.vAceptar = false;
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaClienteJuridico() {
        tableModelListaClienteJuridico =
                new FarmaTableModel(ConstantsCliente.columnsListaClientesJuridicos, ConstantsCliente.defaultValuesListaClientesJuridicos,
                                    0);
        FarmaUtility.initSimpleList(tblClienteJuridico, tableModelListaClienteJuridico,
                                    ConstantsCliente.columnsListaClientesJuridicos);
    }

    private void seleccionTipoCliente() {
        if (VariablesCliente.vTipoBusqueda.equalsIgnoreCase(ConstantsCliente.TIPO_JURIDICO)) {
            rbtNatural.setSelected(false);
            rbtJuridico.setSelected(true);
        } else {
            rbtNatural.setSelected(true);
            rbtJuridico.setSelected(false);
        }
    }

    private void cambiaTipoCliente() {
        if (rbtJuridico.isSelected()) {
            rbtNatural.setSelected(true);
            rbtJuridico.setSelected(false);
            VariablesCliente.vTipoBusqueda = ConstantsCliente.TIPO_JURIDICO;
        } else if (rbtNatural.isSelected()) {
            rbtNatural.setSelected(false);
            rbtJuridico.setSelected(true);
            VariablesCliente.vTipoBusqueda = ConstantsCliente.TIPO_NATURAL;
        }
    }

    public void cargaClienteJuridico() {
        try {
            log.debug(VariablesCliente.vRuc_RazSoc_Busqueda);
            log.debug(VariablesCliente.vTipoBusqueda);
            DBCliente.cargaListaClienteJuridico(tableModelListaClienteJuridico, VariablesCliente.vRuc_RazSoc_Busqueda,
                                                VariablesCliente.vTipoBusqueda);
            FarmaUtility.ordenar(tblClienteJuridico, tableModelListaClienteJuridico, 2,
                                 FarmaConstants.ORDEN_ASCENDENTE);
            if (tblClienteJuridico.getRowCount() > 0)
                FarmaUtility.setearPrimerRegistro(tblClienteJuridico, txtClienteJuridico, 2);
            else
                FarmaUtility.showMessage(this, "No se encontro ningun Cliente para esta Busqueda", txtClienteJuridico);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al listar Clientes Juridicos", null);
            cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtClienteJuridico);
        FarmaUtility.centrarVentana(this);
        if (VariablesCliente.vIndicadorCargaCliente.equals(FarmaConstants.INDICADOR_S)) {
            cargaClienteJuridico();
            rbtJuridico.setSelected(true);
            rbtNatural.setSelected(false);
        }
    }

    private void txtClienteJuridico_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblClienteJuridico, txtClienteJuridico, 2);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtClienteJuridico.setText(txtClienteJuridico.getText().trim().toUpperCase());
            String textoBusqueda = txtClienteJuridico.getText().trim();

            if (textoBusqueda.length() >= 3) {
                char primerkeyChar = textoBusqueda.charAt(0);
                char ultimokeyChar = textoBusqueda.charAt(textoBusqueda.length() - 1);
                // Si el primer y último character son numeros asumimos que es RUC
                if (rbtJuridico.isSelected()) {
                    if (!Character.isLetter(primerkeyChar) && !Character.isLetter(ultimokeyChar) &&
                        textoBusqueda.length() > 10)
                        buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_RUC, textoBusqueda);
                    else
                        buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_RAZSOC, textoBusqueda);
                } else {
                    if (!Character.isLetter(primerkeyChar) && !Character.isLetter(ultimokeyChar) &&
                        textoBusqueda.length() > 7)
                        buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_DNI, textoBusqueda);
                    else
                        buscaClienteJuridico(ConstantsCliente.TIPO_BUSQUEDA_NOMBRE, textoBusqueda);
                }
            } else
                FarmaUtility.showMessage(this, "Ingrese 3 caracteres como minimo para realizar la busqueda",
                                         txtClienteJuridico);
        }

        if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (VariablesCliente.vIndicadorCargaCliente.equals(FarmaConstants.INDICADOR_N))
                cambiaTipoCliente();
        }
        chkKeyPressed(e);
    }

    private void chkKeyReleased(KeyEvent e) {
    }

    private void buscaClienteJuridico(String pTipoBusqueda, String pBusqueda) {
        VariablesCliente.vTipoBusqueda = pTipoBusqueda;
        VariablesCliente.vRuc_RazSoc_Busqueda = pBusqueda;
        cargaClienteJuridico();
    }

    private void txtClienteJuridico_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblClienteJuridico, txtClienteJuridico, 2);
    }

    private void mostrarDatoCliente() {
        String nombre = tableModel.getValueAt(tblClienteJuridico.getSelectedRow(), 2).toString();
        txtClienteJuridico.setText(nombre);
        log.debug(nombre);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtClienteJuridico);
            else {
                if (rbtJuridico.isSelected()) {
                    mantenimientoClienteJuridico(ConstantsCliente.ACCION_INSERTAR);
                } else
                    mantenimientoClienteNatural(ConstantsCliente.ACCION_INSERTAR);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtClienteJuridico);
            else {
                if (tblClienteJuridico.getRowCount() <= 0)
                    return;
                if (rbtJuridico.isSelected()) {
                    guardaRegistroCliente();
                    mantenimientoClienteJuridico(ConstantsCliente.ACCION_MODIFICAR);
                } else {
                    VariablesCliente.vCodigo =
                            ((String)tblClienteJuridico.getValueAt(tblClienteJuridico.getSelectedRow(), 0)).trim();
                    guardaRegistroCliente();
                    mantenimientoClienteNatural(ConstantsCliente.ACCION_MODIFICAR);
                }
            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            guardaRegistroCliente();
            if (VariablesCliente.vIndicadorCargaCliente.equals(FarmaConstants.INDICADOR_S))
                cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void guardaRegistroCliente() {
        if (tblClienteJuridico.getRowCount() > 0) {
            VariablesCliente.vArrayList_Cliente_Juridico.clear();
            VariablesCliente.vArrayList_Cliente_Juridico.add(tableModelListaClienteJuridico.data.get(tblClienteJuridico.getSelectedRow()));
        }
    }

    private void mantenimientoClienteJuridico(String pTipoAccion) {
        VariablesCliente.vTipo_Accion = pTipoAccion;
        DlgMantClienteJuridico dlgMantClienteJuridico = new DlgMantClienteJuridico(myParentFrame, "", true);
        dlgMantClienteJuridico.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cargaClienteJuridico();

            if (VariablesCliente.vIndicadorCargaCliente.equals(FarmaConstants.INDICADOR_S))
                cerrarVentana(true);
        }
    }

    private void listadoTarjetasClientes(String pTipoAccion) {
        VariablesCliente.vTipo_Accion = pTipoAccion;
        DlgMantTarjetaCliente dlgMantTarjetaCliente = new DlgMantTarjetaCliente(myParentFrame, "", true);
        dlgMantTarjetaCliente.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        }
    }

    private void mantenimientoClienteNatural(String pTipoAccion) {
        VariablesCliente.vTipo_Accion = pTipoAccion;
        DlgMantClienteNatural dlgMantClienteNatural = new DlgMantClienteNatural(myParentFrame, "", true);
        dlgMantClienteNatural.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cargaClienteJuridico();
        }
    }

    private void tblClienteJuridico_keyPressed(KeyEvent e) {
        txtClienteJuridico_keyPressed(e);
    }

    private void btnClienteJuridico_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtClienteJuridico);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnRelacion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblClienteJuridico);
    }

}
