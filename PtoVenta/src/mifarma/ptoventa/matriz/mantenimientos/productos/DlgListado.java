package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListado.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      12.11.2007   Creación<br>
 * <br>
 * @author  Jorge Cortez Alvarez <br>
 * @v
 * private static final Logger log = LoggerFactory.getLogger(.class);ersion 1.0<br>
 *
 */

public class DlgListado extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListado.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private final int COL_COD = 0;
    private final int COL_NOMB = 1;
    private final int DIG_COD = 3;
    private final int DIG_MOD = 10;
    private String Fil_Autoc = "";

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnllHeader1 = new JPanelHeader();
    private JTextFieldSanSerif txtLaboratorio = new JTextFieldSanSerif();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionLocales = new JButtonLabel();
    private JScrollPane scrListaLaboratorios = new JScrollPane();
    private JTable tblListaLaboratorios = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JButtonLabel btnSeleccionar = new JButtonLabel();


    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgListado() {
        this(null, "", false);
    }

    public DlgListado(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(408, 365));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Busqueda Por Filtro");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnllHeader1.setBounds(new Rectangle(15, 10, 370, 40));
        txtLaboratorio.setBounds(new Rectangle(90, 10, 270, 20));
        txtLaboratorio.setPreferredSize(new Dimension(99, 23));
        txtLaboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLaboratorio_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtLaboratorio_keyReleased(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(15, 60, 370, 25));
        btnRelacionLocales.setText("Relacion ");
        btnRelacionLocales.setBounds(new Rectangle(10, 5, 125, 15));
        btnRelacionLocales.setMnemonic('R');
        scrListaLaboratorios.setBounds(new Rectangle(15, 85, 370, 205));
        tblListaLaboratorios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLocales_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(290, 300, 95, 20));
        lblEnter.setText("[ ENTER ] Seleccionar ");
        lblEnter.setBounds(new Rectangle(15, 300, 170, 20));
        btnSeleccionar.setText("Seleccionar :");
        btnSeleccionar.setBounds(new Rectangle(10, 10, 70, 15));
        btnSeleccionar.setMnemonic('S');
        btnSeleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSeleccionar_actionPerformed(e);
            }
        });
        pnllHeader1.add(btnSeleccionar, null);
        pnllHeader1.add(txtLaboratorio, null);
        pnlTitle1.add(btnRelacionLocales, null);
        scrListaLaboratorios.getViewport().add(tblListaLaboratorios, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaLaboratorios, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnllHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }


    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        cargaLista();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaLaboratorios, ConstantsProducto.defaultValuesListaLaboratorios,
                                    0);
        FarmaUtility.initSimpleList(tblListaLaboratorios, tableModel, ConstantsProducto.columnsListaLaboratorios);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.setearPrimerRegistro(tblListaLaboratorios, txtLaboratorio, 1);
        FarmaUtility.moveFocus(txtLaboratorio);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblListaLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesProducto.cCodLab = " ";
            VariablesProducto.cDesLab = " ";
            cerrarVentana(false);
        }
    }


    /**
     * Valida si existe en la lista ó selecciona
     * */
    private void seleccion() {

        if (!(FarmaUtility.findTextInJTable(tblListaLaboratorios, txtLaboratorio.getText().trim(), COL_COD,
                                            COL_NOMB))) {
            FarmaUtility.showMessage(this, "Elemento no encontrado según criterio de búsqueda !!!", txtLaboratorio);
        } else {
            seleccionarElemento();
        }
    }

    private void btnSeleccionar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtLaboratorio);
    }

    private void txtLaboratorio_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaLaboratorios, txtLaboratorio, COL_NOMB);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            //todos los demas filtros
            if (txtLaboratorio.getText().trim().length() < DIG_COD) {
                if (FarmaUtility.isInteger(txtLaboratorio.getText().trim())) {
                    txtLaboratorio.setText(FarmaUtility.completeWithSymbol(txtLaboratorio.getText(), DIG_COD, "0",
                                                                           "I"));
                }
                FarmaUtility.findTextInJTable(tblListaLaboratorios, txtLaboratorio.getText().trim(), COL_COD, COL_COD);
            } else {
                seleccion();
            }

        }
        chkKeyPressed(e);
    }

    private void txtLaboratorio_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaLaboratorios, txtLaboratorio, COL_COD);
        FarmaGridUtils.buscarDescripcion(e, tblListaLaboratorios, txtLaboratorio, COL_NOMB);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */


    /**
     *  Validamos y setemos valores de Retorno al formulario base
     */
    private void seleccionarElemento() {
        if (tblListaLaboratorios.getRowCount() > 0) {
            if (!(FarmaUtility.findTextInJTable(tblListaLaboratorios, txtLaboratorio.getText().trim(), COL_COD,
                                                COL_NOMB))) {
                FarmaUtility.showMessage(this, "Elemento no encontrado según criterio de búsqueda !!!",
                                         txtLaboratorio);
                return;
            } else {
                int row = tblListaLaboratorios.getSelectedRow();
                if (row > -1) {
                    VariablesProducto.cCodLab = FarmaUtility.getValueFieldJTable(tblListaLaboratorios, row, COL_COD);
                    VariablesProducto.cDesLab = FarmaUtility.getValueFieldJTable(tblListaLaboratorios, row, COL_NOMB);

                }
                cerrarVentana(true);
            }
        }
    }

    /**
     *  Cargamos el listado segun el filtro escogido
     */
    private void cargaLista() {
        try {
            tableModel.clearTable();

            if (VariablesProducto.cFiltroListado.equalsIgnoreCase(ConstantsProducto.LABORATORIO)) {
                DBProducto.cargaListaLaboratorios(tableModel);
            } else {
                log.debug("No se realizo ningun tipo de busqueda");
            }
            if (tblListaLaboratorios.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaLaboratorios, tableModel, COL_COD + 1, FarmaConstants.ORDEN_ASCENDENTE);
            }
            VariablesProducto.cFiltroListado = "";
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener el listado de locales: \n" +
                    sql.getMessage(), txtLaboratorio);

        }
    }

    private void limpiar() {

        VariablesProducto.cCodLab = "";
        VariablesProducto.cDesLab = "";
        VariablesProducto.cFiltroListado = "";

    }


}
