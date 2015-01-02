package mifarma.ptoventa.recepcionCiega;


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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaProductos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaProductos.class);

    Frame myParentFrame;
    private FarmaTableModel tableModelListaProductos;
    private final int COL_TIP = 1;

    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JScrollPane srcListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private BorderLayout borderLayout1 = new BorderLayout();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaProductos() {
        this(null, "", false);
    }

    public DlgListaProductos(Frame parent, String title, boolean modal) {
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

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(620, 438));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(5, 10, 605, 45));
        btnBuscar.setText("Buscar:");
        btnBuscar.setBounds(new Rectangle(15, 15, 55, 15));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtProducto.setBounds(new Rectangle(75, 15, 320, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(5, 60, 605, 25));
        srcListaProductos.setBounds(new Rectangle(5, 85, 605, 285));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(365, 385, 140, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(515, 385, 95, 20));
        btnRelacionProductos.setText("Relacion de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 5, 135, 15));
        btnRelacionProductos.setMnemonic('R');

        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        pnlHeader1.add(txtProducto, null);
        pnlHeader1.add(btnBuscar, null);
        srcListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(srcListaProductos, null);
        pnlTitle.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {

        initTableListaProductos();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaProductos() {
        tableModelListaProductos =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaProductos, ConstantsRecepCiega.defaultcolumnsListaProductos,
                                    0);
        /**
           * JMIRANDA 07.01.10
           * Filtrar Búsqueda de producto solo los que esten en la entrega y hayan sido contados.
           * */
        cargarListaProductos(VariablesRecepCiega.vNumIngreso.trim());
        //clonarListadoProductos();

        FarmaUtility.initSelectList(tblListaProductos, tableModelListaProductos,
                                    ConstantsRecepCiega.columnsListaProductos);
        if (this.tableModelListaProductos.getRowCount() > 0)
            FarmaUtility.ordenar(this.tblListaProductos, tableModelListaProductos, 2, FarmaConstants.ORDEN_ASCENDENTE);

        FarmaUtility.moveFocus(txtProducto);
        if (tblListaProductos.getSelectedRow() > -1)
            FarmaUtility.setearPrimerRegistro(tblListaProductos, txtProducto, 2);

    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(this.txtProducto);
        FarmaUtility.centrarVentana(this);

    }

    private void this_windowClosing(WindowEvent e) {
        cerrarVentana(false);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.txtProducto);
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, this.tblListaProductos, this.txtProducto, 2);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtProducto.getText().trim(), 1, 2))) {
                    FarmaUtility.showMessage(this, "Producto no encontrado según criterio de búsqueda !!!",
                                             txtProducto);
                    return;
                } else {
                    log.debug("primero key press");
                }
            }
        }
        chkKeyPressed(e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, this.tblListaProductos, this.txtProducto, 2);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            log.debug("entro al enter");
            if (this.tblListaProductos.getRowCount() > 0) {
                if (this.txtProducto.getText().length() > 0) {
                    log.debug("antes de SeleccionarProducto()");
                    SeleccionarProducto();

                } else {
                    FarmaUtility.showMessage(this, "Debe seleccionar un Producto", txtProducto);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }

    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void clonarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; i < VariablesVentas.tableModelListaGlobalProductos.data.size(); i++) {
            ArrayList aux = (ArrayList)((ArrayList)VariablesVentas.tableModelListaGlobalProductos.data.get(i)).clone();
            arrayClone.add(aux);
        }
        tableModelListaProductos.data = arrayClone;
    }

    private void SeleccionarProducto() {
        boolean seleccion =
            ((Boolean)tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0)).booleanValue();
        log.debug("Seleccion :" + seleccion + "producto: " +
                  (String)this.tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1));
        if (!seleccion) {
            FarmaUtility.setCheckValue(tblListaProductos, true);
            VariablesRecepCiega.vCodProd =
                    tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString();
            VariablesRecepCiega.vDesc_Producto =
                    tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 2).toString();
            VariablesRecepCiega.vUnidad =
                    tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 3).toString();
            VariablesRecepCiega.vValFrac =
                    FarmaUtility.getValueFieldArrayList(tableModelListaProductos.data, tblListaProductos.getSelectedRow(),
                                                        9).trim();
            VariablesRecepCiega.vValPrecVta =
                    FarmaUtility.getValueFieldArrayList(tableModelListaProductos.data, tblListaProductos.getSelectedRow(),
                                                        6).trim();
            log.debug("VariablesRecepCiega.vCodProd :" + VariablesRecepCiega.vCodProd);
            log.debug("VariablesRecepCiega.vDesc_Producto :" + VariablesRecepCiega.vDesc_Producto);
            log.debug(" VariablesRecepCiega.vUnidad  :" + VariablesRecepCiega.vUnidad);
            log.debug(" VariablesRecepCiega.vValFrac :" + VariablesRecepCiega.vValFrac);
            log.debug(" VariablesRecepCiega.vValPrecVta  :" + VariablesRecepCiega.vValPrecVta);

            VariablesRecepCiega.vIndBuscaProducto = true;
            DlgIngresoProdTransferencia vIngresoCantidad =
                new DlgIngresoProdTransferencia(this.myParentFrame, "", true);
            vIngresoCantidad.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cerrarVentana(true);
            }
            VariablesRecepCiega.vIndBuscaProducto = false;
        } else {

            boolean encontrado = false;
            for (int i = 0; i < this.tableModelListaProductos.getRowCount(); i++) {
                if (((Boolean)this.tblListaProductos.getValueAt(i, 0)).booleanValue()) {
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                FarmaUtility.showMessage(this, "Sólo puede seleccionar un producto", txtProducto);
            }
            FarmaUtility.setCheckValue(tblListaProductos, false);


        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void cargarListaProductos(String pNumRecepcion) {
        try {
            DBRecepCiega.getListaProductosTransf(tableModelListaProductos, pNumRecepcion.trim());
            setJTable(tblListaProductos, txtProducto);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al Listar Productos : \n" +
                    sql.getMessage(), txtProducto);
        }
    }

    private void setJTable(JTable pJTable, JTextFieldSanSerif pText) {
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, null, 0);
        }
        FarmaUtility.moveFocus(pText);
    }
}
