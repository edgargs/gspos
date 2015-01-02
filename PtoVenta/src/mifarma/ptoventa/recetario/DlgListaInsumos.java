package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaInsumos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      12.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaInsumos extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgListaInsumos.class);
    private FacadeRecetario facadeRecetario;

    private Frame myParentFrame;
    private FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JPanel pnlHeader = new JPanel();
    private JButton btnListaProducto = new JButton();
    private JPanel pnlTitle = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JButton btnProducto = new JButton();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    transient UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    ArrayList<ArrayList<Object>> arrayInsumosSeleccionados = new ArrayList<ArrayList<Object>>();
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    /**
     * Constructor
     */
    public DlgListaInsumos() {
        this(null, "", false);
    }

    /**
     * Constructor
     * @param parent Objeto Frame de la Aplicación.
     * @param title Título de la Ventana.
     * @param modal Tipo de Ventana.
     */
    public DlgListaInsumos(Frame parent, String title, boolean modal) {
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

    /**
     * Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(720, 441));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Insumos");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(594, 405));
        jContentPane.setForeground(Color.white);
        lblEnter.setText("[ ENTER ] Seleccionar/Deseleccionar");
        lblEnter.setBounds(new Rectangle(10, 365, 240, 20));
        lblEnter.setFocusable(false);
        scrListaProductos.setBounds(new Rectangle(10, 90, 695, 270));
        scrListaProductos.setBackground(new Color(255, 130, 14));
        scrListaProductos.setFocusable(false);
        pnlHeader.setBounds(new Rectangle(10, 65, 695, 25));
        pnlHeader.setBackground(new Color(255, 130, 14));
        pnlHeader.setLayout(null);
        pnlHeader.setForeground(new Color(255, 130, 14));
        pnlHeader.setFocusable(false);
        btnListaProducto.setText("Lista de Productos");
        btnListaProducto.setBounds(new Rectangle(10, 0, 145, 25));
        btnListaProducto.setBackground(new Color(255, 130, 14));
        btnListaProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnListaProducto.setBorderPainted(false);
        btnListaProducto.setContentAreaFilled(false);
        btnListaProducto.setDefaultCapable(false);
        btnListaProducto.setFocusPainted(false);
        btnListaProducto.setFont(new Font("SansSerif", 1, 11));
        btnListaProducto.setMnemonic('L');
        btnListaProducto.setForeground(Color.white);
        btnListaProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnListaProducto.setRequestFocusEnabled(false);
        btnListaProducto.setActionCommand("Lista de Insumos");
        btnListaProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaProducto_actionPerformed(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(10, 20, 695, 40));
        pnlTitle.setBackground(new Color(43, 141, 39));
        pnlTitle.setLayout(null);
        pnlTitle.setForeground(new Color(43, 141, 39));
        pnlTitle.setFocusable(false);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(425, 10, 115, 25));
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setMnemonic('b');
        btnBuscar.setRequestFocusEnabled(false);
        txtProducto.setBounds(new Rectangle(110, 10, 295, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }
        });
        txtProducto.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtProducto_focusLost(e);
            }
        });
        btnProducto.setText("Producto :");
        btnProducto.setBounds(new Rectangle(25, 10, 70, 20));
        btnProducto.setFont(new Font("SansSerif", 1, 12));
        btnProducto.setForeground(Color.white);
        btnProducto.setBackground(new Color(43, 141, 39));
        btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnProducto.setBorderPainted(false);
        btnProducto.setContentAreaFilled(false);
        btnProducto.setDefaultCapable(false);
        btnProducto.setFocusPainted(false);
        btnProducto.setMnemonic('p');
        btnProducto.setRequestFocusEnabled(false);
        btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnProducto.setFocusable(false);
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        tblListaProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }
        });
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(500, 365, 105, 20));
        lblF11.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(620, 365, 85, 20));
        lblEsc.setFocusable(false);
        scrListaProductos.getViewport();
        pnlHeader.add(btnListaProducto, null);
        pnlTitle.add(btnBuscar, null);
        pnlTitle.add(txtProducto, null);
        pnlTitle.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEnter, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlTitle, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        setJTable(tblListaProductos);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsRecetario.columnsListaProductos, ConstantsRecetario.defaultListaProductos,
                                    0);
        FarmaUtility.initSelectList(tblListaProductos, tableModel, ConstantsRecetario.columnsListaProductos);
        txtProducto.grabFocus();
    }

    private void setJTable(JTable pJTable) {
        txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
        }
        FarmaUtility.moveFocus(txtProducto);
    }

    private void cargaLista() {
        try {
            //DBRecetario.getListaInsumos(tableModel);
            facadeRecetario.listarInsumosFarma(tableModel);
            if (tblListaProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaProductos, tableModel, 2, "asc");
            log.debug("se cargo la lista de insumos");
            cargar_seleccion();
        } catch (SQLException ex) {
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al listar los insumos. \n " + ex.getMessage(), tblListaProductos);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProducto);
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtProducto, 2);
        chkKeyPressed(e);
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtProducto, 2);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnListaProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
        }
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            cerrarVentana(true);
            VariablesRecetario.vArrayInsumosSeleccionados = arrayInsumosSeleccionados;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            FarmaUtility.setCheckValue(tblListaProductos, false);
            ingresarCantidadInsumo();
        }
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        //Vacio
    }

    private void UpdateReleaseProd(KeyEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }

    private void txtProducto_focusLost(FocusEvent e) {
        txtProducto.grabFocus();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void ingresarCantidadInsumo() {
        ArrayList<Object> tmpArrayRowSelec = new ArrayList<Object>();
        int tmpNumRow = tblListaProductos.getSelectedRow();
        String tmpCodInsumo = tblListaProductos.getValueAt(tmpNumRow, 1).toString();
        Boolean valor = (Boolean)tblListaProductos.getValueAt(tmpNumRow, 0);
        //Seleccionar insumo
        if (valor) {
            DlgIngresoCantidadInsumo dlgIngresoCantidadInsumo = new DlgIngresoCantidadInsumo(myParentFrame, "", true);
            dlgIngresoCantidadInsumo.setFacade(facadeRecetario);
            dlgIngresoCantidadInsumo.setCodInsumo(tmpCodInsumo);
            dlgIngresoCantidadInsumo.setVisible(true);
            if (FarmaVariables.vAceptar) {
                tblListaProductos.setValueAt(VariablesRecetario.vCantInsumosCodSelec, tmpNumRow, 5);
                tblListaProductos.setValueAt(dlgIngresoCantidadInsumo.getCantidadIngresada(), tmpNumRow, 6);
                tblListaProductos.setValueAt(dlgIngresoCantidadInsumo.getPorcentaje(), tmpNumRow, 7);
                tblListaProductos.setValueAt(dlgIngresoCantidadInsumo.getCodUnidMedidaElegida(), tmpNumRow, 8);
                tblListaProductos.setValueAt(dlgIngresoCantidadInsumo.getDescUnidMedidaElegida(), tmpNumRow, 9);
            } else { //Cancelar el ventana de ingresar la cantidad
                tblListaProductos.setValueAt(false, tmpNumRow, 0);
            }
        }
        //Quitar selección insumo
        else {
            VariablesRecetario.vCantInsumosCodSelec = "";
            tblListaProductos.setValueAt(false, tmpNumRow, 0);
            tblListaProductos.setValueAt(VariablesRecetario.vCantInsumosCodSelec, tmpNumRow, 5);
            tblListaProductos.setValueAt(VariablesRecetario.vCantInsumosCodSelec, tmpNumRow, 6);
            tblListaProductos.setValueAt(VariablesRecetario.vCantInsumosCodSelec, tmpNumRow, 7);

            // LLEIVA: se cambio la verificacion de arrayInsumosSeleccionados a tblListaProductos
            for (int i = 0; i < arrayInsumosSeleccionados.size(); i++) {
                ArrayList<Object> arrayTemp = arrayInsumosSeleccionados.get(i);
                Integer num_fila = (Integer)arrayTemp.get(10);
                Boolean flag = (Boolean)tblListaProductos.getValueAt(num_fila, 0);
                if (flag.equals(false)) {
                    arrayInsumosSeleccionados.remove(i);
                }
            }
        }
        tmpArrayRowSelec = (ArrayList<Object>)tableModel.data.get(tmpNumRow);
        // LLEIVA: se añade al final el numero de fila, para ubicarlo mas rapidamente al hacer la recarga
        tmpArrayRowSelec.add(tmpNumRow);
        if (tmpArrayRowSelec.get(0).equals(true)) {
            arrayInsumosSeleccionados.add(tmpArrayRowSelec);
        }
        tblListaProductos.repaint();
    }

    public void setFacade(FacadeRecetario facadeRecetario) {
        this.facadeRecetario = facadeRecetario;
        cargaLista();
    }

    /**
     * Carga la seleccion de insumos anteriormente guardada
     * @author LLEIVA
     * @since 10.06.2013
     */
    public void cargar_seleccion() {
        if (VariablesRecetario.vArrayInsumosSeleccionados != null) {
            for (int i = 0; i < VariablesRecetario.vArrayInsumosSeleccionados.size(); i++) {
                ArrayList<Object> arrayTemp = VariablesRecetario.vArrayInsumosSeleccionados.get(i);
                Integer num_fila = (Integer)arrayTemp.get(10);

                arrayTemp = (ArrayList<Object>)arrayTemp.clone();
                arrayTemp.remove(9);
                tableModel.updateRow(arrayTemp, num_fila);
            }
            arrayInsumosSeleccionados = VariablesRecetario.vArrayInsumosSeleccionados;
        }
    }
}
