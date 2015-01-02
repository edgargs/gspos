package mifarma.ptoventa.ce;


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

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaTrabajadores.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ     22.11.2007   Creación<br>
 * <br>
 * @author Diego Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */

public class DlgListaTrabajadores extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaTrabajadores.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelListaMaestro;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel pnlRelacionFiltros = new JPanel();
    private JPanel pnlIngresarProductos = new JPanel();
    private JTextField txtDescripcion = new JTextField();
    private JButton btnDescripcion = new JButton();
    private JTable tblMaestro = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionMaestros = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaTrabajadores() {
        this(null, "", false);
    }

    public DlgListaTrabajadores(Frame parent, String title, boolean modal) {
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
        this.setTitle("Lista de ");
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
        pnlRelacionFiltros.setBounds(new Rectangle(15, 60, 405, 20));
        pnlRelacionFiltros.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setLayout(null);
        pnlIngresarProductos.setBounds(new Rectangle(15, 10, 405, 40));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 14), 1));
        pnlIngresarProductos.setBackground(Color.white);
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
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
        tblMaestro.setFont(new Font("SansSerif", 0, 12));
        tblMaestro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblMaestro_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(335, 310, 85, 20));
        btnRelacionMaestros.setText("Relacion de Maestros");
        btnRelacionMaestros.setBounds(new Rectangle(10, 0, 150, 20));
        btnRelacionMaestros.setMnemonic('r');
        jScrollPane1.getViewport();
        pnlIngresarProductos.add(txtDescripcion, null);
        pnlIngresarProductos.add(btnDescripcion, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jScrollPane1.getViewport().add(tblMaestro, null);
        jContentPane.add(jScrollPane1, null);
        pnlRelacionFiltros.add(btnRelacionMaestros, null);
        jContentPane.add(pnlRelacionFiltros, null);
        jContentPane.add(pnlIngresarProductos, null);
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

        tableModelListaMaestro =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaTrabajadore, ConstantsCajaElectronica.defaultValuesListaTrabajadore,
                                    0);
        FarmaUtility.initSimpleList(tblMaestro, tableModelListaMaestro,
                                    ConstantsCajaElectronica.columnsListaTrabajadore);
        cargaListado();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblMaestro_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        this.setTitle(VariablesPtoVenta.vTituloListaMaestros);
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDescripcion);
    }

    private void txtDescripcion_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblMaestro, txtDescripcion, 1);
        chkKeyPressed(e);
    }

    private void txtDescripcion_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblMaestro, txtDescripcion, 1);
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
            guardaValoresTrabajadores();
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

    private void cargaListado() {
        String tipoMaestro = VariablesPtoVenta.vTipoMaestro;
        try {
            DBCajaElectronica.cargaListaTrabajadores(tableModelListaMaestro, tipoMaestro);
            //agregando la primera linea en blanco
            ArrayList myArray = new ArrayList();
            myArray.add("");
            myArray.add("");
            myArray.add("");
            tableModelListaMaestro.insertRow(myArray);
            FarmaUtility.ordenar(tblMaestro, tableModelListaMaestro, 1, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException e) {
            log.debug("error al cargar lista de maestros Trabajadores: " + e);
        }
    }

    private void guardaValoresTrabajadores() {
        VariablesPtoVenta.vCodMaestro = ((String)tblMaestro.getValueAt(tblMaestro.getSelectedRow(), 0)).trim();
        VariablesPtoVenta.vDescMaestro = ((String)tblMaestro.getValueAt(tblMaestro.getSelectedRow(), 1)).trim();
        log.debug("*/**/*/*/*/*/*/*");
        log.debug("", tblMaestro.getSelectedRow());
        log.debug(VariablesPtoVenta.vCodMaestro);
        log.debug(VariablesPtoVenta.vDescMaestro);
    }

}

