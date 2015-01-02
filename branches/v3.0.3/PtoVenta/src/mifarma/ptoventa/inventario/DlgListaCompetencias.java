package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;

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
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaCompetencias.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ     12.11.2007   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */

public class DlgListaCompetencias extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaCompetencias.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelListaCompetencias;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel pnlRelacion = new JPanel();
    private JPanel pnlIngresarCompetencia = new JPanel();
    private JTextField txtDescripcion = new JTextField();
    private JButton btnDescripcion = new JButton();
    private JTable tblCompetencia = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionCompetencias = new JButtonLabel();

    private int COLUMNA_RUC = 0;
    private int COLUMNA_DESCRIPCION = 1;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaCompetencias() {
        this(null, "", false);
    }

    public DlgListaCompetencias(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(447, 366));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Competencias");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 335));
        jContentPane.setForeground(Color.white);
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(200, 310, 130, 20));
        jScrollPane1.setBounds(new Rectangle(15, 80, 405, 220));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        pnlRelacion.setBounds(new Rectangle(15, 60, 405, 20));
        pnlRelacion.setBackground(new Color(255, 130, 14));
        pnlRelacion.setLayout(null);
        pnlIngresarCompetencia.setBounds(new Rectangle(15, 10, 405, 40));
        pnlIngresarCompetencia.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 14), 1));
        pnlIngresarCompetencia.setBackground(Color.white);
        pnlIngresarCompetencia.setLayout(null);
        pnlIngresarCompetencia.setForeground(Color.orange);
        txtDescripcion.setBounds(new Rectangle(105, 10, 240, 20));
        txtDescripcion.setFont(new Font("SansSerif", 1, 11));
        txtDescripcion.setForeground(new Color(32, 105, 29));
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDescripcion_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtDescripcion_keyReleased(e);
            }
        });
        btnDescripcion.setText("Descripcion :");
        btnDescripcion.setBounds(new Rectangle(15, 10, 85, 20));
        btnDescripcion.setMnemonic('d');
        btnDescripcion.setFont(new Font("SansSerif", 1, 11));
        btnDescripcion.setDefaultCapable(false);
        btnDescripcion.setRequestFocusEnabled(false);
        btnDescripcion.setBackground(new Color(50, 162, 65));
        btnDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDescripcion.setFocusPainted(false);
        btnDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
        btnDescripcion.setContentAreaFilled(false);
        btnDescripcion.setBorderPainted(false);
        btnDescripcion.setForeground(new Color(255, 140, 14));
        btnDescripcion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDescripcion_actionPerformed(e);
            }
        });
        tblCompetencia.setFont(new Font("SansSerif", 0, 12));
        tblCompetencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCompetencia_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(335, 310, 85, 20));
        btnRelacionCompetencias.setText("Relacion de Competencias");
        btnRelacionCompetencias.setBounds(new Rectangle(10, 0, 150, 20));
        btnRelacionCompetencias.setMnemonic('r');
        jScrollPane1.getViewport();
        pnlIngresarCompetencia.add(txtDescripcion, null);
        pnlIngresarCompetencia.add(btnDescripcion, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jScrollPane1.getViewport().add(tblCompetencia, null);
        jContentPane.add(jScrollPane1, null);
        pnlRelacion.add(btnRelacionCompetencias, null);
        jContentPane.add(pnlRelacion, null);
        jContentPane.add(pnlIngresarCompetencia, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModelListaCompetencias =
                new FarmaTableModel(ConstantsInventario.columnsListaCompetencias, ConstantsInventario.defaultValuesListaCompetencias,
                                    0);
        FarmaUtility.initSimpleList(tblCompetencia, tableModelListaCompetencias,
                                    ConstantsInventario.columnsListaCompetencias);
        cargaListaCompetencias();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblCompetencia_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDescripcion);
        VariablesInventario.vDescripcionCompetencia = "";
        VariablesInventario.vRucCompetencia = "";

    }

    private void txtDescripcion_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCompetencia, txtDescripcion, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblCompetencia.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblCompetencia, txtDescripcion.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "No se encontro el RUC en la lista de empresas!!!", txtDescripcion);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void txtDescripcion_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblCompetencia, txtDescripcion, 1);
    }

    private void btnDescripcion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDescripcion);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            guardaValores();
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

    private void cargaListaCompetencias() {
        try {
            DBInventario.cargaListaCompetencias(tableModelListaCompetencias);
            ArrayList myArray = new ArrayList();
            myArray.add("");
            myArray.add("");
            myArray.add("");
            tableModelListaCompetencias.insertRow(myArray);
            FarmaUtility.ordenar(tblCompetencia, tableModelListaCompetencias, 1, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException e) {
            log.debug("error al cargar lista de competencias : " + e);
        }
    }

    private void guardaValores() {
        VariablesInventario.vDescripcionCompetencia =
                ((String)tblCompetencia.getValueAt(tblCompetencia.getSelectedRow(), COLUMNA_DESCRIPCION)).trim();
        VariablesInventario.vRucCompetencia =
                ((String)tblCompetencia.getValueAt(tblCompetencia.getSelectedRow(), COLUMNA_RUC)).trim();

        log.debug("Competencia Seleccionada : ");
        log.debug("" + VariablesInventario.vDescripcionCompetencia + " ");
        log.debug("" + VariablesInventario.vRucCompetencia);
    }


}
