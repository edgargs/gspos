package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgLoteKardex extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgLoteKardex.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    private JPanelHeader pnlHeader1 = new JPanelHeader();

    private JLabelWhite lblProducto_T = new JLabelWhite();

    private JLabelWhite lblProducto = new JLabelWhite();

    private JLabelWhite lblUnidad_T = new JLabelWhite();

    private JLabelWhite lblStock_T = new JLabelWhite();

    private JLabelWhite lblUnidad = new JLabelWhite();

    private JLabelWhite lblStockEntero = new JLabelWhite();


    private JPanelTitle pnllTitle1 = new JPanelTitle();

    private JButtonLabel btnRelacionMovimiento = new JButtonLabel();

    private JScrollPane scrListaProductos = new JScrollPane();

    private JTable tblListaMovs = new JTable();


    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelWhite lblLaboratorio_T = new JLabelWhite();

    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JLabelWhite lblUnidadFraccion = new JLabelWhite();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JPanelTitle pnllTitle2 = new JPanelTitle();
    private JButtonLabel btnRelacionMovimiento1 = new JButtonLabel();
    private JLabel lblExcluido = new JLabel();
    
    private String tipoInventario;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgLoteKardex() {
        this(null, "", false);
    }

    public DlgLoteKardex(Frame parent, String tipoInv, boolean modal) {
        super(parent, tipoInv, modal);
        myParentFrame = parent;
        try {
            log.debug("producto: "+tipoInventario);
            tipoInventario = tipoInv;
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
        this.setSize(new Dimension(408, 378));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Movimiento de Lote por Producto");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 385, 65));
        lblProducto_T.setText("Producto:");
        lblProducto_T.setBounds(new Rectangle(10, 10, 65, 20));
        lblProducto.setText("TREUPEL-NF NIÑOS ");
        lblProducto.setBounds(new Rectangle(70, 10, 305, 20));
        lblProducto.setFont(new Font("SansSerif", 0, 11));
        lblUnidad_T.setText("Unidad:");
        lblUnidad_T.setBounds(new Rectangle(130, 35, 45, 20));
        lblStock_T.setText("Stock:");
        lblStock_T.setBounds(new Rectangle(10, 35, 35, 20));
        lblUnidad.setText("CJA/5 SUPOS ");
        lblUnidad.setBounds(new Rectangle(180, 35, 100, 20));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblStockEntero.setText("10");
        lblStockEntero.setBounds(new Rectangle(70, 35, 55, 20));
        lblStockEntero.setFont(new Font("SansSerif", 0, 11));
        pnllTitle1.setBounds(new Rectangle(0, 0, 385, 25));
        btnRelacionMovimiento.setText("Relación de Lotes");
        btnRelacionMovimiento.setBounds(new Rectangle(15, 5, 215, 15));
        btnRelacionMovimiento.setMnemonic('R');
        btnRelacionMovimiento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionMovimiento_keyPressed(e);
            }
        });
        btnRelacionMovimiento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionMovimiento_actionPerformed(e);
            }
        });
        scrListaProductos.setBounds(new Rectangle(10, 100, 385, 205));
        tblListaMovs.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblListaMovs_keyReleased(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(300, 315, 85, 20));
        lblEsc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblEsc_keyPressed(e);
            }
        });
        lblLaboratorio_T.setText("Laboratorio:");
        lblLaboratorio_T.setBounds(new Rectangle(440, 10, 70, 20));
        lblLaboratorio.setText("PERUANO GERMANO (EDMUNDO STAHL)");
        lblLaboratorio.setBounds(new Rectangle(510, 10, 215, 20));
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblUnidadFraccion.setBounds(new Rectangle(575, 35, 135, 20));
        lblUnidadFraccion.setFont(new Font("SansSerif", 0, 11));
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setBounds(new Rectangle(250, 340, 135, 20));
        lblF8.setVisible(false);
        pnllTitle2.setBounds(new Rectangle(10, 75, 385, 25));
        btnRelacionMovimiento1.setText("Relación de Lotes");
        btnRelacionMovimiento1.setMnemonic('R');
        btnRelacionMovimiento1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionMovimiento_actionPerformed(e);
            }
        });
        lblExcluido.setBounds(new Rectangle(550, 0, 135, 25));
        lblExcluido.setFont(new Font("SansSerif", 1, 12));
        lblExcluido.setText("Excluido de Reposición");
        lblExcluido.setBackground(new Color(44, 146, 24));
        lblExcluido.setOpaque(true);
        lblExcluido.setHorizontalAlignment(SwingConstants.CENTER);
        lblExcluido.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblExcluido.setForeground(Color.white);
        lblExcluido.setVisible(false);
        pnllTitle2.add(lblExcluido, null);
        btnRelacionMovimiento.add(btnRelacionMovimiento1, null);
        pnllTitle1.add(btnRelacionMovimiento, null);
        pnllTitle2.add(pnllTitle1, null);
        pnlHeader1.add(lblUnidadFraccion, null);
        pnlHeader1.add(lblLaboratorio, null);
        pnlHeader1.add(lblLaboratorio_T, null);
        pnlHeader1.add(lblStockEntero, null);
        pnlHeader1.add(lblUnidad, null);
        pnlHeader1.add(lblStock_T, null);
        pnlHeader1.add(lblProducto, null);
        pnlHeader1.add(lblProducto_T, null);
        pnlHeader1.add(lblUnidad_T, null);
        jPanelWhite1.add(pnllTitle2, null);
        jPanelWhite1.add(lblF8, null);
        jPanelWhite1.add(lblEsc, null);
        scrListaProductos.getViewport().add(tblListaMovs, null);
        jPanelWhite1.add(scrListaProductos, null);
        jPanelWhite1.add(pnlHeader1, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && FarmaVariables.vEconoFar_Matriz){
            lblF8.setVisible(true);
        }
        //ERIOS 06.10.2015 Muestra impresion Kardex
        FacadeInventario facadeInventario = new FacadeInventario();
        String strIndicador = facadeInventario.getIndImprimirKardex();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaLoteProd, ConstantsInventario.defaultListaLoteProd,
                                    0);
        FarmaUtility.initSimpleList(tblListaMovs, tableModel, ConstantsInventario.columnsListaLoteProd);
        //cargarFechas();
        cargaListaLotes();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        if (tblListaMovs.getRowCount() > 0) {
            FarmaUtility.moveFocusJTable(tblListaMovs);
        }else{
            FarmaUtility.moveFocus(lblProducto);    
        }
        
        mostrarDatos();
    }
    
    private void lblEsc_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        /*if (datosValidados()) {
            cargarFechas();
            cargaListaMovimientos();
        }*/
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F12(e)) {
        }
        if (e.getKeyCode() == KeyEvent.VK_F6) {
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            cargaListaLotes();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (lblF8.isVisible()) {
                int[] ancho = { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
                FarmaUtility.saveFile(myParentFrame, ConstantsInventario.columnsListaLoteProd, tblListaMovs, ancho);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                          "Esta seguro de excluir la venta para el calculo del pedido de reposicion"))
                actualizaIndCalMaxMin();
            else
                FarmaUtility.showMessage(this, "Se cancelo la operacion", tblListaMovs);
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

    private void mostrarDatos() {        
        if(tipoInventario.equals("transf")){
            log.debug("vCodProd_Transf: "+VariablesInventario.vCodProd_Transf);
            log.debug("vStkFisico_Transf: "+VariablesInventario.vStkFisico_Transf);
            log.debug("vUnidMed_Transf: "+VariablesInventario.vUnidMed_Transf);
            lblProducto.setText(VariablesInventario.vNomProd_Transf);
            lblUnidad.setText(VariablesInventario.vUnidMed_Transf);
            lblStockEntero.setText("" + VariablesInventario.vStkFisico_Transf);
        }else{
            lblProducto.setText(VariablesInventario.vCodProd + " " + VariablesInventario.vDescProd);
            lblLaboratorio.setText(VariablesInventario.vNomLab);
            lblUnidad.setText(VariablesInventario.vDescUnidPresent);
            lblStockEntero.setText("" + VariablesInventario.vStock);
            lblUnidadFraccion.setText(VariablesInventario.vDescUnidFrac);
        }
    }

    private void cargaListaLotes() {
        try {
            DBInventario.getListaLoteProd(tableModel, tipoInventario);
            if (tblListaMovs.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaMovs, tableModel, 2, FarmaConstants.ORDEN_DESCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de movimientos \n" +
                    sql.getMessage(), "");
        }
        if (tblListaMovs.getRowCount() == 0) {
            FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", "");
            return;
        }
        FarmaUtility.moveFocusJTable(tblListaMovs);

    }

    private void btnRelacionMovimiento_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionMovimiento_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnRelacionMovimiento);
    }

    private void obtieneDatosProducto() {
        VariablesInventario.vCantAtendida =
                "" + (Integer.parseInt(FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(),
                                                                        5)) * -1);
        VariablesInventario.vNumPedido =
                FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(), 12);
        VariablesInventario.vIndExclusion =
                FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(), 13);
        VariablesInventario.vCodMotKardex =
                FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(), 14);

        log.debug("VariablesInventario.vCantAtendida : " + VariablesInventario.vCantAtendida);
        log.debug("VariablesInventario.vNumPedido : " + VariablesInventario.vNumPedido);
    }

    private void actualizaIndCalMaxMin() {
        try {
            obtieneDatosProducto();
            if (VariablesInventario.vCodMotKardex.equalsIgnoreCase(ConstantsInventario.COD_MOT_VENTA_NORMAL) ||
                VariablesInventario.vCodMotKardex.equalsIgnoreCase(ConstantsInventario.COD_MOT_VENTA_DELIVERY) ||
                VariablesInventario.vCodMotKardex.equalsIgnoreCase(ConstantsInventario.COD_MOT_VENTA_ESPECIAL)) {
                if (VariablesInventario.vIndExclusion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    DBInventario.actualizaIndCalMaxMin(VariablesInventario.vCodProd, VariablesInventario.vCantAtendida,
                                                       VariablesInventario.vNumPedido);
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "La venta se excluyó para el cálculo del pedido de reposición",
                                             tblListaMovs);
                    tblListaMovs.setValueAt(FarmaConstants.INDICADOR_N, tblListaMovs.getSelectedRow(), 13);
                } else
                    FarmaUtility.showMessage(this, "El producto ya fue excluido de la venta.", tblListaMovs);
            } else
                FarmaUtility.showMessage(this,
                                         "El producto no pertenece a una venta. No se puede excluir del calculo de pedido de reposicion",
                                         tblListaMovs);

        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al actualizar el indicador de Calculo de Pedido Reposcion",
                                     tblListaMovs);
        }
    }

    private void tblListaMovs_keyReleased(KeyEvent e) {
        muestraIndicadorExcluido();
    }

    private void muestraIndicadorExcluido() {
        if (tblListaMovs.getRowCount() > 0) {
            String indExcluido = FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(), 13);
            if (indExcluido.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                lblExcluido.setVisible(true);
            } else
                lblExcluido.setVisible(false);
        }
    }

}
