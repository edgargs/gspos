package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
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
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : FrmLocales_Matriz.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DVELIZ      19.08.2008   Creación<br>
 * <br>
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 *
 */
public class DlgListaLocalesAdicionales extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaLocalesAdicionales.class);

    FarmaTableModel tableModel;
    private Frame myParentFrame;
    private final int COL_COD = 0;
    private final int COL_DESC = 1;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrLocales = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnListaLocales = new JButtonLabel();
    private JTable tblListaLocales = new JTable();
    private JLabelFunction lblFuncion1 = new JLabelFunction();
    private JTextFieldSanSerif txtDescLocal = new JTextFieldSanSerif();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();

    public boolean flag_pedido = true;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaLocalesAdicionales() {
        this(null, "", false);
    }


    public DlgListaLocalesAdicionales(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
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
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(369, 319));
        this.setTitle("Locales");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 350, 30));
        scrLocales.setBounds(new Rectangle(10, 40, 350, 215));
        tblListaLocales.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLocales_keyPressed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(250, 260, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        btnListaLocales.setText("Seleccione Local :");
        btnListaLocales.setBounds(new Rectangle(5, 5, 105, 20));
        btnListaLocales.setMnemonic('S');
        btnListaLocales.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnListaLocales_keyPressed(e);
            }
        });
        btnListaLocales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaLocales_actionPerformed(e);
            }
        });
        lblFuncion1.setBounds(new Rectangle(10, 260, 110, 20));
        lblFuncion1.setText("[ F1] Ver Pedidos");
        txtDescLocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDescLocal_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtDescLocal_keyReleased(e);
            }
        });
        txtDescLocal.setBounds(new Rectangle(115, 5, 230, 20));
        lblF3.setBounds(new Rectangle(125, 260, 120, 20));
        lblF3.setText("[ F3 ] Filtrar Todos");
        lblF3.setVisible(false);

        lblF4.setBounds(new Rectangle(125, 260, 120, 20));
        lblF4.setText("[ F4 ] Filtrar X PVM");


        jContentPane.add(lblF4, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblFuncion1, null);
        jContentPane.add(lblEsc, null);
        scrLocales.getViewport().add(tblListaLocales, null);
        jContentPane.add(scrLocales, null);
        pnlTitle1.add(txtDescLocal, null);
        pnlTitle1.add(btnListaLocales, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    public void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaLocales, ConstantsProducto.defaultValuesListaLocales,
                                    0);
        FarmaUtility.initSimpleList(tblListaLocales, tableModel, ConstantsProducto.columnsListaLocales);
        VariablesProducto.filtroListaLocalesPVM = "0";
        cargaListaLocales();
    }

    private void cargaListaLocales() {
        try {
            DBProducto.listarLocalesPVM(tableModel, VariablesProducto.filtroListaLocalesPVM);
            FarmaUtility.ordenar(tblListaLocales, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar los locales : " + "\n" +
                    sql.getMessage(), btnListaLocales);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDescLocal);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblListaLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnListaLocales_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDescLocal);
    }

    private void btnListaLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtDescLocal_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaLocales, txtDescLocal, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            seleccionarLocal();
        }
        chkKeyPressed(e);
    }

    private void txtDescLocal_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaLocales, txtDescLocal, COL_DESC);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaLocales,null,0);
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            log.debug("booleano ant=" + flag_pedido);
            if (tblListaLocales.getRowCount() > 0 && flag_pedido) {
                log.debug("Entro! \n");
                flag_pedido = false;
                log.debug("booleano des=" + flag_pedido);
                cargaCodigoLocal();
                funcionF1();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_F3) {
            mostrarTodos();
        }

        if (e.getKeyCode() == KeyEvent.VK_F4) {
            mostrarConPVM();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cargaCodigoLocal() {
        int pos = tblListaLocales.getSelectedRow();
        VariablesProducto.vCodLocal_PedAdic = tblListaLocales.getValueAt(pos, 0).toString();
        log.debug("VariablesProducto.vCodLocal :" + VariablesProducto.vCodLocal_PedAdic);

    }

    private void mostrarTodos() {
        lblF3.setVisible(false);
        lblF4.setVisible(true);
        VariablesProducto.filtroListaLocalesPVM = "0";
        cargaListaLocales();
    }

    private void mostrarConPVM() {
        lblF3.setVisible(true);
        lblF4.setVisible(false);
        VariablesProducto.filtroListaLocalesPVM = "1";
        cargaListaLocales();
    }

    private void funcionF1() {

        DlgPedidoAdicional dlgPedido = new DlgPedidoAdicional(myParentFrame, "", true);
        dlgPedido.setVisible(true);
        if (!FarmaVariables.vAceptar) {
            flag_pedido = true;
            log.debug("se volvio a volver true");
        }

    }

    private void seleccionarLocal() {
        if (tblListaLocales.getRowCount() > 0) {
            if (!(FarmaUtility.findTextInJTable(tblListaLocales, txtDescLocal.getText().trim(), COL_COD, COL_DESC))) {
                FarmaUtility.showMessage(this, "Elemento no encontrado según criterio de búsqueda !!!", txtDescLocal);
                return;
            }
        }
    }

}
