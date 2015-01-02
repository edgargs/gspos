package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProductosComplementarios.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      28.12.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgProductosComplementarios extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProductosComplementarios.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelProductosComplementarios;
    private FarmaTableModel tableModelRelacionAccTerap;
    private String codigoProductoOrigen = "";
    private String descripcionProductoOrigen = "";

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JPanel jPanel2 = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel1 = new JPanel();
    private JPanel pnlIngresarProductos = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextField txtProducto = new JTextField();
    private JButton btnProducto = new JButton();
    private JTable tblPrincAct = new JTable();
    private JTable tblProductosComplementarios = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JButtonLabel btnRelacionPrincAct = new JButtonLabel();
    private JButtonLabel btnRelacionComplementarios = new JButtonLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabel lblProdOrigen = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JLabel lblDescLab_Prod = new JLabel();

    private JTable myJTable;
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgProductosComplementarios() {
        this(null, "", false);
    }

    public DlgProductosComplementarios(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(699, 430));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos y Precios Complementarios");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(532, 374));
        jContentPane.setForeground(Color.white);
        jScrollPane2.setBounds(new Rectangle(10, 100, 670, 95));
        jScrollPane2.setBackground(new Color(255, 130, 14));
        jPanel2.setBounds(new Rectangle(10, 210, 670, 20));
        jPanel2.setBackground(new Color(255, 130, 14));
        jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jPanel2.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(10, 230, 670, 140));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(10, 80, 670, 20));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlIngresarProductos.setBounds(new Rectangle(10, 10, 670, 65));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlIngresarProductos.setBackground(new Color(43, 141, 39));
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(530, 10, 100, 25));
        btnBuscar.setBackground(SystemColor.control);
        btnBuscar.setMnemonic('b');
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtProducto.setBounds(new Rectangle(110, 10, 390, 20));
        txtProducto.setFont(new Font("SansSerif", 1, 11));
        txtProducto.setForeground(new Color(32, 105, 29));
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }
        });
        btnProducto.setText("Producto");
        btnProducto.setBounds(new Rectangle(30, 10, 60, 25));
        btnProducto.setMnemonic('p');
        btnProducto.setFont(new Font("SansSerif", 1, 11));
        btnProducto.setDefaultCapable(false);
        btnProducto.setRequestFocusEnabled(false);
        btnProducto.setBackground(new Color(50, 162, 65));
        btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnProducto.setFocusPainted(false);
        btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnProducto.setContentAreaFilled(false);
        btnProducto.setBorderPainted(false);
        btnProducto.setForeground(Color.white);
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        tblPrincAct.setName("tblPrincAct");
        tblPrincAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblPrincAct_keyReleased(e);
            }
        });
        tblProductosComplementarios.setName("tblProductosComplementarios");
        tblProductosComplementarios.setFont(new Font("SansSerif", 0, 12));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(595, 380, 85, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(450, 380, 135, 20));
        lblF1.setText("[ F1 ] Ver Impulso");
        lblF1.setBounds(new Rectangle(10, 380, 110, 20));
        btnRelacionPrincAct.setText("Relacion de Principios Activos");
        btnRelacionPrincAct.setBounds(new Rectangle(10, 0, 210, 20));
        btnRelacionPrincAct.setMnemonic('A');
        btnRelacionPrincAct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionPrincAct_actionPerformed(e);
            }
        });
        btnRelacionComplementarios.setText("Relacion de Productos Complementarios");
        btnRelacionComplementarios.setBounds(new Rectangle(10, 0, 245, 20));
        btnRelacionComplementarios.setMnemonic('r');
        btnRelacionComplementarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionComplementarios_actionPerformed(e);
            }
        });
        jLabel1.setText("Producto Origen :");
        jLabel1.setBounds(new Rectangle(30, 45, 110, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(Color.white);
        lblProdOrigen.setBounds(new Rectangle(145, 45, 360, 15));
        lblProdOrigen.setFont(new Font("SansSerif", 1, 11));
        lblProdOrigen.setForeground(Color.white);
        jSeparator1.setBounds(new Rectangle(270, 0, 15, 20));
        jSeparator1.setBackground(Color.black);
        jSeparator1.setOrientation(SwingConstants.VERTICAL);
        lblDescLab_Prod.setBounds(new Rectangle(295, 0, 370, 20));
        lblDescLab_Prod.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Prod.setForeground(Color.white);
        jScrollPane2.getViewport();
        jScrollPane1.getViewport();
        pnlIngresarProductos.add(lblProdOrigen, null);
        pnlIngresarProductos.add(jLabel1, null);
        pnlIngresarProductos.add(btnBuscar, null);
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jScrollPane2.getViewport().add(tblPrincAct, null);
        jContentPane.add(jScrollPane2, null);
        jPanel2.add(btnRelacionComplementarios, null);
        jPanel2.add(jSeparator1, null);
        jPanel2.add(lblDescLab_Prod, null);
        jContentPane.add(jPanel2, null);
        jScrollPane1.getViewport().add(tblProductosComplementarios, null);
        jContentPane.add(jScrollPane1, null);
        jPanel1.add(btnRelacionPrincAct, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(pnlIngresarProductos, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        codigoProductoOrigen = VariablesVentas.vCodProdOrigen_Comple;
        descripcionProductoOrigen = VariablesVentas.vDescProdOrigen_Comple;
        lblProdOrigen.setText(codigoProductoOrigen + " - " + descripcionProductoOrigen);
        initTableAccTerap();
        initTableProductosComplementarios();
        FarmaVariables.vAceptar = false;

        if (tblPrincAct.getRowCount() > 0) {
            FarmaGridUtils.showCell(tblPrincAct, 0, 0);
            String codPrincAct = ((String)tblPrincAct.getValueAt(tblPrincAct.getSelectedRow(), 0)).trim();
            cargaListaProdComplementarios(codPrincAct);
            /**NUEVO*/
            setJTable(tblPrincAct);
            muestraNombreLab(3, lblDescLab_Prod);
        } else
            FarmaUtility.moveFocus(txtProducto);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableProductosComplementarios() {
        tableModelProductosComplementarios =
                new FarmaTableModel(ConstantsVentas.columnsListaProductosComplementarios, ConstantsVentas.defaultValuesListaProductosComplementarios,
                                    0);
        FarmaUtility.initSimpleList(tblProductosComplementarios, tableModelProductosComplementarios,
                                    ConstantsVentas.columnsListaProductosComplementarios);
    }

    private void initTableAccTerap() {
        tableModelRelacionAccTerap =
                new FarmaTableModel(ConstantsVentas.columnsListaRelacionAccTerap, ConstantsVentas.defaultValuesListaRelacionAccTerap,
                                    0);
        FarmaUtility.initSimpleList(tblPrincAct, tableModelRelacionAccTerap,
                                    ConstantsVentas.columnsListaRelacionAccTerap);
        cargaListaAccTerap();
    }

    private void cargaListaAccTerap() {
        try {
            DBVentas.cargaListaRelacionAccionTerap(tableModelRelacionAccTerap, codigoProductoOrigen);
            //FarmaUtility.ordenar(tblPrincAct, tableModelRelacionAccTerap, 6, FarmaConstants.ORDEN_DESCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar lista de acciones terapeuticas. \n" +
                    sql.getMessage(), txtProducto);
        }
    }

    private void cargaListaProdComplementarios(String pCodPrincAct) {
        try {
            DBVentas.cargaListaProductosComplementarios(tableModelProductosComplementarios, codigoProductoOrigen,
                                                        pCodPrincAct);
            FarmaUtility.ordenar(tblProductosComplementarios, tableModelProductosComplementarios, 1,
                                 FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener Lista de Productos Complementarios. \n" +
                    sql.getMessage(), txtProducto);
        }
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProducto);

        /*if(tblPrincAct.getRowCount() > 0){
      FarmaGridUtils.showCell(tblPrincAct, 0, 0);
      String codPrincAct = ((String)tblPrincAct.getValueAt(tblPrincAct.getSelectedRow(), 0)).trim();
      cargaListaProdComplementarios(codPrincAct);
      setJTable(tblPrincAct);
      //FarmaUtility.setearRegistro(tblProductosComplementarios,0,txtProducto,1);
      muestraNombreLab(3, lblDescLab_Prod);
    }
    else
        FarmaUtility.moveFocus(txtProducto);*/

    }

    private void tblPrincAct_keyReleased(KeyEvent e) {
        /*if(tblPrincAct.getRowCount() > 0){
      String codAccTerap = ((String)tblPrincAct.getValueAt(tblPrincAct.getSelectedRow(), 0)).trim();
      cargaListaProdComplementarios(codAccTerap);

      muestraNombreLab(3, lblDescLab_Prod);
    }*/
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void btnRelacionPrincAct_actionPerformed(ActionEvent e) {
        /*if(tblPrincAct.getRowCount() > 0) FarmaUtility.setearActualRegistro(tblPrincAct, txtProducto, 1);
    FarmaUtility.moveFocus(tblPrincAct);*/
        /**NUEVO*/
        if (tblPrincAct.getRowCount() > 0)
            setJTable(tblPrincAct);
        //if(tblPrincAct.getRowCount() > 0) FarmaGridUtils.showCell(tblPrincAct, 0, 0);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (tblProductosComplementarios.getRowCount() <= 0)
            return;
        VariablesVentas.vCodProdComplementario =
                ((String)tblProductosComplementarios.getValueAt(tblProductosComplementarios.getSelectedRow(),
                                                                0)).trim();
        cerrarVentana(true);
    }

    private void btnRelacionComplementarios_actionPerformed(ActionEvent e) {
        //if(tblProductosComplementarios.getRowCount() > 0) FarmaUtility.setearActualRegistro(tblProductosComplementarios, txtProducto, 1);
        //FarmaUtility.moveFocus(txtProducto);
        /**NUEVO*/
        if (tblProductosComplementarios.getRowCount() > 0)
            setJTable(tblProductosComplementarios);
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        if (tblProductosComplementarios.getRowCount() > 0) {
            FarmaGridUtils.aceptarTeclaPresionada(e, myJTable, txtProducto, 1);
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                e.consume();
                if (tblProductosComplementarios.getSelectedRow() >= 0) {
                    if (!(FarmaUtility.findTextInJTable(tblProductosComplementarios, txtProducto.getText().trim(), 0,
                                                        1))) {
                        FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                 txtProducto);
                        return;
                    } else {
                        muestraNombreLab(3, lblDescLab_Prod);
                        btnBuscar.doClick();
                    }
                }
            }
            chkKeyPressed(e);
        }
        chkKeyPressed(e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        if (tblProductosComplementarios.getRowCount() > 0) {
            FarmaGridUtils.buscarDescripcion(e, myJTable, txtProducto, 1);
            if (!(e.getKeyCode() == KeyEvent.VK_ESCAPE))
                if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PROD_PRINC_ACT)) {
                    String codAccTerap = ((String)tblPrincAct.getValueAt(tblPrincAct.getSelectedRow(), 0)).trim();
                    cargaListaProdComplementarios(codAccTerap);
                }
        }
        muestraNombreLab(3, lblDescLab_Prod);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            muestraDetalleProducto();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
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

    private void muestraNombreLab(int pColumna, JLabel pLabel) {
        if (tblProductosComplementarios.getRowCount() > 0) {
            String nombreLab =
                ((String)(tblProductosComplementarios.getValueAt(tblProductosComplementarios.getSelectedRow(),
                                                                 pColumna))).trim();
            pLabel.setText(nombreLab);
        }
    }

    private void muestraDetalleProducto() {
        if (tblProductosComplementarios.getRowCount() == 0)
            return;
        VariablesVentas.vCod_Prod =
                ((String)(tblProductosComplementarios.getValueAt(tblProductosComplementarios.getSelectedRow(),
                                                                 1))).trim();
        VariablesVentas.vDesc_Prod =
                ((String)(tblProductosComplementarios.getValueAt(tblProductosComplementarios.getSelectedRow(),
                                                                 2))).trim();
        VariablesVentas.vNom_Lab =
                ((String)(tblProductosComplementarios.getValueAt(tblProductosComplementarios.getSelectedRow(),
                                                                 4))).trim();
        DlgDetalleProducto dlgDetalleProducto = new DlgDetalleProducto(myParentFrame, "", true);
        dlgDetalleProducto.setVisible(true);
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    /**NUEVO
     * @Autor: Luis Reque
     * @Fecha: 30-03-2007
     * */
    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, txtProducto, 1);
        }
        FarmaUtility.moveFocus(txtProducto);
    }
}
