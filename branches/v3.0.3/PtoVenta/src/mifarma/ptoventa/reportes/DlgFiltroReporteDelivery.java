package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.reference.DBReportes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltroReporteDelivery.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * KMONCADA      17.07.2014   Creación<br>
 * <br>
 * @author Kenny Moncada<br>
 * @version 1.0<br>
 *
 */

public class DlgFiltroReporteDelivery extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgFiltroReporteDelivery.class);
    private String tipoFiltro;
    private Frame myParentFrame;
    private FarmaTableModel tableModelListaFiltro;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JPanel pnlIngresarFiltro = new JPanel();
    private JComboBox cmbCampoFiltro = new JComboBox();
    private JButton btnCampo = new JButton();
    private JTextField txtFiltro = new JTextField();
    private JButton btnFiltro = new JButton();
    private JLabelFunction lblEsc = new JLabelFunction();
    private String HASHTABLE_CAMPOS_TABLA = "V_REPOR_VENTA_DELIVERY";
    private ArrayList camposFiltro = new ArrayList();
    public String campoFiltro = "";
    public String valCampoFiltro = "";

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgFiltroReporteDelivery() {
        this(null, "", false);
    }

    public DlgFiltroReporteDelivery(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(453, 153));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Filtro");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 335));
        jContentPane.setForeground(Color.white);
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 95, 130, 20));
        pnlIngresarFiltro.setBounds(new Rectangle(15, 10, 415, 75));
        pnlIngresarFiltro.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 14), 1));
        pnlIngresarFiltro.setBackground(Color.white);
        pnlIngresarFiltro.setLayout(null);
        pnlIngresarFiltro.setForeground(Color.orange);
        cmbCampoFiltro.setBounds(new Rectangle(115, 10, 220, 20));
        cmbCampoFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbCampoFiltroKeyPressed(e);
            }
        });
        btnCampo.setText("Campo :");
        btnCampo.setBounds(new Rectangle(10, 10, 75, 20));
        btnCampo.setMnemonic('c');
        btnCampo.setRequestFocusEnabled(false);
        btnCampo.setFocusPainted(false);
        btnCampo.setDefaultCapable(false);
        btnCampo.setBorderPainted(false);
        btnCampo.setBackground(new Color(43, 141, 39));
        btnCampo.setHorizontalAlignment(SwingConstants.LEFT);
        btnCampo.setFont(new Font("SansSerif", 1, 11));
        btnCampo.setForeground(new Color(255, 140, 14));
        btnCampo.setHorizontalTextPosition(SwingConstants.LEADING);
        btnCampo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCampo.setContentAreaFilled(false);
        btnCampo.setActionCommand("Campo :");
        btnCampo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCampoActionPerformed(e);
            }
        });
        txtFiltro.setBounds(new Rectangle(115, 40, 220, 20));
        txtFiltro.setFont(new Font("SansSerif", 1, 11));
        txtFiltro.setForeground(new Color(32, 105, 29));
        txtFiltro.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFiltroKeyPressed(e);
            }
        });
        btnFiltro.setText("Filtro :");
        btnFiltro.setBounds(new Rectangle(10, 40, 60, 25));
        btnFiltro.setMnemonic('f');
        btnFiltro.setFont(new Font("SansSerif", 1, 11));
        btnFiltro.setDefaultCapable(false);
        btnFiltro.setRequestFocusEnabled(false);
        btnFiltro.setBackground(new Color(50, 162, 65));
        btnFiltro.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnFiltro.setFocusPainted(false);
        btnFiltro.setHorizontalAlignment(SwingConstants.LEFT);
        btnFiltro.setContentAreaFilled(false);
        btnFiltro.setBorderPainted(false);
        btnFiltro.setForeground(new Color(255, 140, 14));
        btnFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFiltroActionPerformed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(345, 95, 85, 20));
        pnlIngresarFiltro.add(cmbCampoFiltro, null);
        pnlIngresarFiltro.add(btnCampo, null);
        pnlIngresarFiltro.add(txtFiltro, null);
        pnlIngresarFiltro.add(btnFiltro, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(pnlIngresarFiltro, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        cargaCombo();
        FarmaVariables.vAceptar = false;
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void cargaCombo() {
        camposFiltro = new ArrayList();

        try {
            DBReportes.listaCamposFiltroReporteVtaDelivery(camposFiltro, HASHTABLE_CAMPOS_TABLA);
        } catch (Exception ex) {
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al obtener los campos para filtro.\n " + ex.getMessage(), txtFiltro);

        }
        if (camposFiltro.size() > 0) {
            FarmaLoadCVL.loadCVLFromArrayList(cmbCampoFiltro, HASHTABLE_CAMPOS_TABLA, camposFiltro, true);
        }

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFiltro);
    }

    private void txtFiltroKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (cmbCampoFiltro.getSelectedIndex() == -1 && txtFiltro.getText().trim().length() == 0) {
                FarmaUtility.showMessage(this, "Ingrese Criterios de Búsqueda !!!", txtFiltro);
                return;
            } else {
                guardaValoresFiltro();
                cerrarVentana(true);
            }
        }
        chkKeyPressed(e);
    }

    private void cmbCampoFiltroKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFiltro);
        }
    }

    private void btnFiltroActionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFiltro);
    }

    private void btnCampoActionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbCampoFiltro);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            guardaValoresFiltro();
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

    private void guardaValoresFiltro() {
        Object obj = cmbCampoFiltro.getSelectedItem();
        log.info((String)obj);
        campoFiltro = FarmaLoadCVL.getCVLCode(HASHTABLE_CAMPOS_TABLA, cmbCampoFiltro.getSelectedIndex());
        valCampoFiltro = txtFiltro.getText().trim().toUpperCase();
        /* VariablesPtoVenta.vCodFiltro = ((String)tblFiltro.getValueAt(tblFiltro.getSelectedRow(), 0)).trim();
        VariablesPtoVenta.vDescFiltro = ((String)tblFiltro.getValueAt(tblFiltro.getSelectedRow(), 1)).trim();
        VariablesPtoVenta.vTipoFiltro = tipoFiltro;
        VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
        VariablesPtoVenta.vDesc_Cat_Filtro = (String)cmbCampoFiltro.getSelectedItem(); */
    }

}
