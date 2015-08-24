package mifarma.ptoventa.convenio;


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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

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

import mifarma.ptoventa.convenio.reference.ConstantsConvenio;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaDependientesClientesConv.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ    30.01.2008   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */
public class DlgListaDependientesClientesConv extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaDependientesClientesConv.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelLista;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel pnlRelacionFiltros = new JPanel();
    private JPanel pnlIngresarProductos = new JPanel();
    private JTextField txtDescripcion = new JTextField();
    private JButton btnDescripcion = new JButton();
    private JTable tblDependientes = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionMaestros = new JButtonLabel();

    // constantes de listado de clientes
    // 31.01.2008 dubilluz  creacion
    private int COLUMN_COD_CLI_DEP = 0;
    private int COLUMN_COD_TRAB_DEP = 1;
    private int COLUMN_DESC_CLI_DEP = 2;
    private int COLUMN_DOC_CLI_DEP = 3;
    private int COLUMN_COD_TRAB_NUM_DEP = 4;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaDependientesClientesConv() {
        this(null, "", false);
    }

    public DlgListaDependientesClientesConv(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(626, 366));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Dependientes");
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
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 335));
        jContentPane.setForeground(Color.white);
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(375, 305, 130, 20));
        jScrollPane1.setBounds(new Rectangle(15, 80, 585, 220));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setBounds(new Rectangle(15, 60, 585, 20));
        pnlRelacionFiltros.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setLayout(null);
        pnlIngresarProductos.setBounds(new Rectangle(15, 10, 585, 40));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlIngresarProductos.setBackground(Color.white);
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(new Color(255, 130, 14));
        txtDescripcion.setBounds(new Rectangle(105, 10, 465, 20));
        txtDescripcion.setFont(new Font("SansSerif", 1, 11));
        txtDescripcion.setForeground(new Color(32, 105, 29));
        txtDescripcion.addKeyListener(new KeyAdapter() {
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
        btnDescripcion.setForeground(new Color(255, 130, 14));
        btnDescripcion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDescripcion_actionPerformed(e);
            }
        });
        tblDependientes.setFont(new Font("SansSerif", 0, 12));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(515, 305, 85, 20));
        btnRelacionMaestros.setText("Relacion de Clientes");
        btnRelacionMaestros.setBounds(new Rectangle(10, 0, 150, 20));
        jScrollPane1.getViewport();
        pnlIngresarProductos.add(txtDescripcion, null);
        pnlIngresarProductos.add(btnDescripcion, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jScrollPane1.getViewport().add(tblDependientes, null);
        jContentPane.add(jScrollPane1, null);
        pnlRelacionFiltros.add(btnRelacionMaestros, null);
        jContentPane.add(pnlRelacionFiltros, null);
        jContentPane.add(pnlIngresarProductos, null);

    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModelLista =
                new FarmaTableModel(ConstantsConvenio.columnsListaDepClientesConv, ConstantsConvenio.defaultValuesListaDepClientesConv,
                                    0);
        FarmaUtility.initSimpleList(tblDependientes, tableModelLista, ConstantsConvenio.columnsListaDepClientesConv);
        cargaListaClientesDependientes();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDescripcion);
        // Se colocara el Nombre del Trabajador Seleccionado
        this.setTitle("Lista de Dependientes del Cliente : " + VariablesConvenio.vDatosClienteBusqueda.trim());
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtDescripcion_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblDependientes, txtDescripcion, COLUMN_DESC_CLI_DEP);
        // e.consume();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //if (tblDependientes.getSelectedRow() >= 0)
            e.consume();
            if (tblDependientes.getRowCount() > 0) {
                /**
      * Se filtra por tipo de campo
      * @author : JCORTEZ
      * @since  : 18.08.2008
      */
                log.debug("TIPO FILTRO CONSULTA ::::::" + VariablesConvenio.vTipoFiltro);

                if (FarmaUtility.isInteger(txtDescripcion.getText().trim()) &&
                    txtDescripcion.getText().trim().length() < 7) {

                    if (!(FarmaUtility.findTextInJTable(tblDependientes, txtDescripcion.getText().trim(),
                                                        COLUMN_COD_TRAB_DEP, COLUMN_DESC_CLI_DEP))) {
                        FarmaUtility.showMessage(this, " Maestro No Encontrado según Criterio de Búsqueda !!!",
                                                 txtDescripcion);
                        return;
                    }
                } else if (FarmaUtility.isInteger(txtDescripcion.getText().trim()) &&
                           txtDescripcion.getText().trim().length() > 6) {

                    if (!(FarmaUtility.findTextInJTable(tblDependientes, txtDescripcion.getText().trim(),
                                                        COLUMN_DOC_CLI_DEP, COLUMN_DESC_CLI_DEP))) {
                        FarmaUtility.showMessage(this, " Maestro No Encontrado según Criterio de Búsqueda !!!",
                                                 txtDescripcion);
                        return;
                    }
                } else {

                    if (!(FarmaUtility.findTextInJTable(tblDependientes, txtDescripcion.getText().trim(),
                                                        COLUMN_DESC_CLI_DEP, COLUMN_DESC_CLI_DEP))) {
                        FarmaUtility.showMessage(this, " Maestro No Encontrado según Criterio de Búsqueda !!!",
                                                 txtDescripcion);
                        return;
                    }
                }

                /* if(VariablesConvenio.vTipoFiltro.equalsIgnoreCase(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR)){
          if (!(FarmaUtility.findTextInJTable(tblDependientes,txtDescripcion.getText().trim(),COLUMN_COD_TRAB_DEP,COLUMN_DESC_CLI_DEP)))
          {
            FarmaUtility.showMessage(this," Maestro No Encontrado según Criterio de Búsqueda !!!",txtDescripcion);
            return;
          }
        }else if((VariablesConvenio.vTipoFiltro.equalsIgnoreCase(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO))){
         if (!(FarmaUtility.findTextInJTable(tblDependientes, txtDescripcion.getText().trim(), COLUMN_DOC_CLI_DEP, COLUMN_DESC_CLI_DEP)))
          {
            FarmaUtility.showMessage(this," Maestro No Encontrado según Criterio de Búsqueda !!!",txtDescripcion);
            return;
          }
         }else{
         if (!(FarmaUtility.findTextInJTable(tblDependientes, txtDescripcion.getText().trim(), COLUMN_DOC_CLI_DEP, COLUMN_DESC_CLI_DEP)))
          {
            FarmaUtility.showMessage(this," Maestro No Encontrado según Criterio de Búsqueda !!!",txtDescripcion);
            return;
          }
         }*/
            }
        }
        chkKeyPressed(e);
    }

    private void txtDescripcion_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblDependientes, txtDescripcion, COLUMN_DESC_CLI_DEP);
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
            guardaValoresDependiente();
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        VariablesConvenio.vCodClienteBusqueda = "";
        VariablesConvenio.vDatosClienteBusqueda = "";
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaClientesDependientes() {
        try {
            DBConvenio.cargaListaDepClienteConv(tableModelLista);
            FarmaUtility.ordenar(tblDependientes, tableModelLista, COLUMN_DESC_CLI_DEP,
                                 FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al listar dependientes del cliente.\n" +
                    e.getMessage(), txtDescripcion);
        }
    }

    private void guardaValoresDependiente() {
        VariablesConvenio.vCodClienteDependiente =
                FarmaUtility.getValueFieldJTable(tblDependientes, tblDependientes.getSelectedRow(),
                                                 COLUMN_COD_CLI_DEP);
        VariablesConvenio.vCodTrabDependiente =
                FarmaUtility.getValueFieldJTable(tblDependientes, tblDependientes.getSelectedRow(),
                                                 COLUMN_COD_TRAB_DEP);
        /**
     * se obtiene el nombre del dependiente del titular
     * @author : JCORTEZ
     * @since  : 13.03.2008
     */
        VariablesConvenio.vNomTrabDependiente =
                FarmaUtility.getValueFieldJTable(tblDependientes, tblDependientes.getSelectedRow(),
                                                 COLUMN_DESC_CLI_DEP);

        log.debug("VariablesConvenio.vCodClienteDependiente " + VariablesConvenio.vCodClienteDependiente);

        log.debug("VariablesConvenio.vCodTrabDependiente " + VariablesConvenio.vCodClienteDependiente);
        log.debug("VariablesConvenio.vNomTrabDependiente " + VariablesConvenio.vNomTrabDependiente);

    }
}
