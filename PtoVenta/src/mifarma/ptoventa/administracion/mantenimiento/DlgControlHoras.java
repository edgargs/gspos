package mifarma.ptoventa.administracion.mantenimiento;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
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
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.mantenimiento.reference.ConstantsMantenimiento;
import mifarma.ptoventa.administracion.mantenimiento.reference.DBMantenimiento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaConvenios.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LREQUE      30.04.2007   Creación
 * <br>
 * @author Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class DlgControlHoras extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgControlHoras.class);
    Frame myParentFrame;
    FarmaTableModel tableModel;

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanel pnlDatos = new JPanel();
    private JButton btnIndicador = new JButton();
    private JComboBox cmbIndicador = new JComboBox();
    private JPanel pnlDatos_T = new JPanel();
    private JLabel lblIngresoDatos_T = new JLabel();
    private JButton btnIndicador1 = new JButton();
    private JTextFieldSanSerif txtObservacion = new JTextFieldSanSerif();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel pnlDatos_T1 = new JPanel();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JLabelFunction lblFAceptar = new JLabelFunction();
    private JTable tblHorarios = new JTable();
    private JButton btnIndicador2 = new JButton();
    private JPanelHeader pnlObservac = new JPanelHeader();
    private JLabelWhite lblObs_t = new JLabelWhite();
    private JLabelWhite lblObs = new JLabelWhite();
    private JLabelWhite lblUsuario = new JLabelWhite();
    private JLabelOrange lblFecha_t = new JLabelOrange();
    private JLabelOrange lblFecha = new JLabelOrange();
    private JPanelWhite pnlUsuario = new JPanelWhite();
    private JLabelWhite lblUsuario_t = new JLabelWhite();

    // **************************************************************************
    // Constructor
    // **************************************************************************

    public DlgControlHoras() {
        this(null, "", false);
    }

    public DlgControlHoras(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(394, 442));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Control de Horas");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlDatos.setBounds(new Rectangle(10, 285, 370, 95));
        pnlDatos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlDatos.setBackground(Color.white);
        pnlDatos.setLayout(null);
        btnIndicador.setText("Motivo:");
        btnIndicador.setDefaultCapable(false);
        btnIndicador.setRequestFocusEnabled(false);
        btnIndicador.setBorderPainted(false);
        btnIndicador.setFocusPainted(false);
        btnIndicador.setContentAreaFilled(false);
        btnIndicador.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnIndicador.setHorizontalAlignment(SwingConstants.LEFT);
        btnIndicador.setMnemonic('m');
        btnIndicador.setFont(new Font("SansSerif", 1, 11));
        btnIndicador.setBounds(new Rectangle(10, 15, 50, 20));
        btnIndicador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnIndicador_actionPerformed(e);
            }
        });
        cmbIndicador.setBounds(new Rectangle(105, 15, 255, 20));
        cmbIndicador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbIndicador_keyPressed(e);
            }
        });
        pnlDatos_T.setBounds(new Rectangle(10, 265, 370, 20));
        pnlDatos_T.setBackground(new Color(255, 130, 14));
        pnlDatos_T.setBorder(BorderFactory.createTitledBorder(""));
        pnlDatos_T.setLayout(null);
        lblIngresoDatos_T.setText("Ingreso de Datos");
        lblIngresoDatos_T.setBounds(new Rectangle(10, 0, 120, 20));
        lblIngresoDatos_T.setFont(new Font("SansSerif", 1, 11));
        lblIngresoDatos_T.setForeground(Color.white);
        btnIndicador1.setText("Observaciones :");
        btnIndicador1.setDefaultCapable(false);
        btnIndicador1.setRequestFocusEnabled(false);
        btnIndicador1.setBorderPainted(false);
        btnIndicador1.setFocusPainted(false);
        btnIndicador1.setContentAreaFilled(false);
        btnIndicador1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnIndicador1.setHorizontalAlignment(SwingConstants.LEFT);
        btnIndicador1.setMnemonic('O');
        btnIndicador1.setFont(new Font("SansSerif", 1, 11));
        btnIndicador1.setBounds(new Rectangle(10, 40, 110, 20));
        btnIndicador1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnIndicador1_actionPerformed(e);
            }
        });
        txtObservacion.setBounds(new Rectangle(10, 65, 350, 20));
        txtObservacion.setLengthText(50);
        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtObservacion_keyPressed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 60, 370, 150));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlDatos_T1.setBounds(new Rectangle(10, 40, 370, 20));
        pnlDatos_T1.setBackground(new Color(255, 130, 14));
        pnlDatos_T1.setBorder(BorderFactory.createTitledBorder(""));
        pnlDatos_T1.setLayout(null);
        lblFCerrar.setBounds(new Rectangle(280, 390, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        lblFAceptar.setBounds(new Rectangle(160, 390, 115, 20));
        lblFAceptar.setText("[ F11 ] Aceptar");
        tblHorarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblHorarios_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblHorarios_keyReleased(e);
            }
        });
        btnIndicador2.setText("Control de Horas");
        btnIndicador2.setDefaultCapable(false);
        btnIndicador2.setRequestFocusEnabled(false);
        btnIndicador2.setBorderPainted(false);
        btnIndicador2.setFocusPainted(false);
        btnIndicador2.setContentAreaFilled(false);
        btnIndicador2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnIndicador2.setHorizontalAlignment(SwingConstants.LEFT);
        btnIndicador2.setMnemonic('c');
        btnIndicador2.setFont(new Font("SansSerif", 1, 11));
        btnIndicador2.setBounds(new Rectangle(10, 0, 140, 20));
        btnIndicador2.setForeground(Color.white);
        btnIndicador2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnIndicador2_actionPerformed(e);
            }
        });
        pnlObservac.setBounds(new Rectangle(10, 210, 370, 45));
        lblObs_t.setText("Observaciones:");
        lblObs_t.setBounds(new Rectangle(10, 5, 115, 15));
        lblObs.setBounds(new Rectangle(10, 20, 355, 20));
        lblObs.setFont(new Font("SansSerif", 0, 11));
        lblUsuario.setBounds(new Rectangle(65, 0, 130, 20));
        lblUsuario.setForeground(Color.black);
        lblFecha_t.setText("Fecha:");
        lblFecha_t.setBounds(new Rectangle(215, 0, 45, 20));
        lblFecha.setBounds(new Rectangle(255, 0, 110, 20));
        lblFecha.setForeground(Color.black);
        pnlUsuario.setBounds(new Rectangle(10, 10, 370, 20));
        pnlUsuario.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblUsuario_t.setText("Usuario:");
        lblUsuario_t.setBounds(new Rectangle(10, 0, 65, 20));
        lblUsuario_t.setForeground(new Color(255, 130, 14));
        pnlDatos.add(txtObservacion, null);
        pnlDatos.add(btnIndicador1, null);
        pnlDatos.add(btnIndicador, null);
        pnlDatos.add(cmbIndicador, null);
        pnlObservac.add(lblObs, null);
        pnlObservac.add(lblObs_t, null);
        pnlUsuario.add(lblUsuario_t, null);
        pnlUsuario.add(lblFecha, null);
        pnlUsuario.add(lblFecha_t, null);
        pnlUsuario.add(lblUsuario, null);
        pnlBlanco.add(pnlUsuario, null);
        pnlBlanco.add(pnlObservac, null);
        pnlBlanco.add(lblFAceptar, null);
        pnlBlanco.add(lblFCerrar, null);
        pnlBlanco.add(pnlDatos_T1, null);
        jScrollPane1.getViewport().add(tblHorarios, null);
        pnlBlanco.add(jScrollPane1, null);
        pnlDatos_T.add(lblIngresoDatos_T, null);
        pnlBlanco.add(pnlDatos_T, null);
        pnlBlanco.add(pnlDatos, null);
        pnlDatos_T1.add(btnIndicador2, null);
        this.getContentPane().add(pnlBlanco, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        colocaFecha();
    }

    private void initTableControlHoras() {
        tableModel =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaControlHoras, ConstantsMantenimiento.defaultValuesListaControlHoras,
                                    0);
        FarmaUtility.initSimpleList(tblHorarios, tableModel, ConstantsMantenimiento.columnsListaControlHoras);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbIndicador);
        cargaLogin();
        cargaComboIndicadores();
        initTableControlHoras();
        cargaListaHorarios();
        muestraObservaciones();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cmbIndicador_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtObservacion);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (cmbIndicador.isPopupVisible())
                cmbIndicador.setPopupVisible(false);
            else
                cerrarVentana(true);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtObservacion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbIndicador);
        } else
            chkKeyPressed(e);
    }

    private void btnIndicador2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblHorarios);
    }

    private void btnIndicador_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbIndicador);
    }

    private void btnIndicador1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtObservacion);
    }

    private void tblHorarios_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblHorarios_keyReleased(KeyEvent e) {
        muestraObservaciones();
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            funcion_F11(); //cargaLogin();
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************


    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void funcion_F11() {
        String codMotivo = FarmaLoadCVL.getCVLCode("CMB_INDICADORES", cmbIndicador.getSelectedIndex());
        if (verificaIngreso(codMotivo)) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Está seguro que desea ingresar este motivo?")) {
                try {
                    String vObs = txtObservacion.getText().trim();
                    DBMantenimiento.grabaControlHoras(codMotivo, vObs);
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "Se realizo la operación satisfactoriamente.", cmbIndicador);
                    cargaListaHorarios();
                    muestraObservaciones();
                    cmbIndicador.setSelectedIndex(0);
                    txtObservacion.setText("");
                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    log.error("", sql);
                    FarmaUtility.showMessage(this, "Error en BD el efectuar la operación: \n" +
                            sql.getMessage(), cmbIndicador);
                }
            } else {
                FarmaUtility.showMessage(this, "Se canceló la operación.", cmbIndicador);
            }
        }
    }

    private void cargaLogin() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, "Validación de Usuario", true);
            //14.11.2007 DUBILLUZ  MODIFICACION
            //dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_SUPERVISOR_VENTAS);
            dlgLogin.setVisible(true);

            if (FarmaVariables.vAceptar) {
                lblUsuario.setText(FarmaVariables.vIdUsu);
                log.debug("FarmaVariables.vIdUsu: " + FarmaVariables.vIdUsu);
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
                                     cmbIndicador);
        }
    }

    private void cargaComboIndicadores() {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbIndicador, "CMB_INDICADORES", "PTOVENTA_ADMIN_MANT.ADMMANT_OBTIENE_MOTIVOS_CTRL",
                                   parametros, true, true);
    }

    private void cargaListaHorarios() {
        try {
            DBMantenimiento.cargaListaControlHoras(tableModel);
            if (tblHorarios.getRowCount() > 0) {
                FarmaUtility.ordenar(tblHorarios, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar los horarios " + sql.getMessage(), cmbIndicador);
        }
    }

    private boolean verificaIngreso(String pCodMotivo) {
        try {
            String rpta = DBMantenimiento.verificaIngresoControlHoras(pCodMotivo);
            log.debug("rpta " + rpta);
            if (rpta.equalsIgnoreCase(ConstantsMantenimiento.RETORNO_ERROR_1)) {
                FarmaUtility.showMessage(this, "No puede volver a registrar este motivo en el control.", cmbIndicador);
                return false;
            } else if (rpta.equalsIgnoreCase(ConstantsMantenimiento.RETORNO_ERROR_2)) {
                FarmaUtility.showMessage(this, "Ingreso incorrecto.", cmbIndicador);
                return false;
            } else {
                return true;
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error en BD: " + sql.getMessage(), cmbIndicador);
            return false;
        }
    }

    private void muestraObservaciones() {
        if (tblHorarios.getRowCount() > 0)
            lblObs.setText("" + tblHorarios.getValueAt(tblHorarios.getSelectedRow(), 2));
    }

    private void colocaFecha() {
        try {
            String fecha = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
            lblFecha.setText(fecha);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la fecha actual: " + sql.getMessage(), cmbIndicador);
        }
    }
}
