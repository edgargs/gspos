package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaPromocion extends JDialog {

    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgListaPromocion.class);

    Frame myParentFrame;
    private FarmaTableModel tableModelListaPromociones;
    private final int COL_COD = 0;
    private final int COL_DESC = 1;
    private final int COL_PREC_PACK = 2;
    private final int COL_STK_PACK = 3;
    private final int COL_IND_PERMITIDO = 5;
    private final int COL_DESL = 6;
    private final int COL_CODP1 = 7;
    private final int COL_CODP2 = 8;
    private final int COL_DESC_LARGA = 9;

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader pnlCliente1 = new JPanelHeader();
    private JTextFieldSanSerif txtPromocion = new JTextFieldSanSerif();
    private JButtonLabel btnPromocion = new JButtonLabel();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelFunction lblAceptar = new JLabelFunction();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblPromociones = new JTable();
    private JLabelFunction lblAceptar1 = new JLabelFunction();
    private JLabelFunction lblVerTodos = new JLabelFunction();
    private JTextArea txtDescPromocion = new JTextArea();
    private JScrollPane jScrollPane2 = new JScrollPane();
    JPanel pnlStock2 = new JPanel();
    XYLayout xYLayout4 = new XYLayout();
    private JButtonLabel btnDescripcion = new JButtonLabel();


    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgListaPromocion() {
        this(null, "", false);
    }

    public DlgListaPromocion(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(628, 498));
        this.setTitle("Listado de Packs");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCliente1.setBounds(new Rectangle(10, 10, 600, 40));
        txtPromocion.setBounds(new Rectangle(75, 10, 405, 20));
        txtPromocion.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtPromocion_keyReleased(e);

            }

            public void keyPressed(KeyEvent e) {
                txtPromocion_keyPressed(e);
            }
        });
        tblPromociones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblPromociones_keyPressed(e);
            }
        });
        lblAceptar1.setBounds(new Rectangle(505, 440, 95, 20));
        lblAceptar1.setText("[ESC] Cerrar ");
        lblVerTodos.setBounds(new Rectangle(240, 440, 135, 20));
        lblVerTodos.setText("[F1] Ver todas");
        txtDescPromocion.setEnabled(false);
        txtDescPromocion.setFont(new Font("Arial", 0, 21));
        jScrollPane2.setBounds(new Rectangle(10, 275, 600, 155));
        pnlStock2.setBounds(new Rectangle(10, 250, 600, 25));
        pnlStock2.setFont(new Font("SansSerif", 0, 11));
        pnlStock2.setBackground(new Color(255, 130, 14));
        pnlStock2.setLayout(xYLayout4);
        pnlStock2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlStock2.setForeground(Color.white);
        btnDescripcion.setText("Descripción");
        btnDescripcion.setMnemonic('1');

        btnPromocion.setText("Pack");
        btnPromocion.setBounds(new Rectangle(20, 20, 65, 20));
        btnPromocion.setMnemonic('P');
        btnPromocion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPromocion_actionPerformed(e);
                // btnEmpresa_actionPerformed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 60, 600, 25));
        jPanelTitle1.setLayout(null);
        lblAceptar.setBounds(new Rectangle(385, 440, 110, 20));
        lblAceptar.setText("[ENTER] Aceptar");
        btnListado.setText("Listado de Packs");
        btnListado.setBounds(new Rectangle(5, 0, 145, 25));
        btnListado.setMnemonic('L');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 85, 600, 155));
        pnlStock2.add(btnDescripcion, new XYConstraints(5, 0, 70, 20));
        jPanelWhite1.add(pnlStock2, null);
        jScrollPane2.getViewport().add(txtDescPromocion, null);
        jPanelWhite1.add(jScrollPane2, null);
        jPanelWhite1.add(lblVerTodos, null);
        jPanelWhite1.add(lblAceptar1, null);
        jScrollPane1.getViewport().add(tblPromociones, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(lblAceptar, null);
        jPanelTitle1.add(btnListado, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(btnPromocion, null);
        pnlCliente1.add(txtPromocion, null);
        jPanelWhite1.add(pnlCliente1, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        VariablesVentas.ACCION = VariablesVentas.ACCION_CREAR;
        FarmaVariables.vAceptar = false;
        initTableListaPromociones();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaPromociones() {
        tableModelListaPromociones =
                new FarmaTableModel(ConstantsVentas.columnsListaPromociones, ConstantsVentas.defaultValuesListaPromociones,
                                    COL_COD);
        FarmaUtility.initSimpleList(tblPromociones, tableModelListaPromociones,
                                    ConstantsVentas.columnsListaPromociones);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtPromocion);
        ConstantsVentas.ESTADO_LISTADO = "P";
        listarPromociones();
        FarmaUtility.setearPrimerRegistro(tblPromociones, txtPromocion, COL_DESC);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
        ConstantsVentas.ESTADO_LISTADO = "P";
    }


    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            ConstantsVentas.ESTADO_LISTADO = "P";
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            listarPromociones();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            //mostrarDetallePromocion();
        }

    }

    private void txtPromocion_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblPromociones, txtPromocion, COL_DESC);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            mostrarDetallePromociones();
        } else
            chkKeyPressed(e);
    }

    private void txtPromocion_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblPromociones, txtPromocion, COL_DESC);
        mostrarDetallePromocion();
    }

    private void btnPromocion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPromocion);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblPromociones);
    }

    private void tblPromociones_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
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
     * Se lista todas las Promociones en las que participa es un producto
     */
    private void listarPromociones() {
        try {
            //DBVentas.listaPromociones(tableModelListaPromociones);
            if (ConstantsVentas.ESTADO_LISTADO.equalsIgnoreCase("T")) {
                DBVentas.listaPromociones(tableModelListaPromociones);
                lblVerTodos.setText("[F1] Ver Por Producto");
                txtPromocion.setText("");
                ConstantsVentas.ESTADO_LISTADO = "P";
            } else {
                DBVentas.listaPromocionesPorProducto(tableModelListaPromociones, VariablesVentas.vCodProdFiltro);
                lblVerTodos.setText("[F1] Ver todas");
                ConstantsVentas.ESTADO_LISTADO = "T";
            }
            if (tblPromociones.getRowCount() > 0) {
                FarmaUtility.ordenar(tblPromociones, tableModelListaPromociones, COL_COD,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar las Promociones.\n" +
                    sql.getMessage(), txtPromocion);
        }
    }

    /**
     * Se incova al formulario Detalle de promociones, que contiene el detalle de los paquetes
     */
    private void mostrarDetallePromociones() {

        int row = tblPromociones.getSelectedRow();
        String codprom = FarmaUtility.getValueFieldJTable(tblPromociones, row, COL_COD);
        VariablesVentas.vCod_Prom = codprom;
        VariablesVentas.vDesc_Prom = FarmaUtility.getValueFieldJTable(tblPromociones, row, COL_DESL);

        if (row > -1) {
            String indAplicable = FarmaUtility.getValueFieldJTable(tblPromociones, row, COL_IND_PERMITIDO);

            if (indAplicable.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                VariablesVentas.vCodProm = codprom;
                log.debug("<<TCT 12>> Antes del detalle Promocion");
                DlgDetallePromocion dlgdetalleprom = new DlgDetallePromocion(myParentFrame, "", true);
                dlgdetalleprom.setVisible(true);
                log.debug("<<TCT 12>> Despues del detalle Promocion");
                if (FarmaVariables.vAceptar) {
                    cerrarVentana(true);
                }
            } else
                FarmaUtility.showMessage(this,
                                         "La promocion " + VariablesVentas.vDesc_Prom + " no se puede seleccionar.",
                                         txtPromocion);

        }
    }


    /**
     * Se muestra el detalle de la promocion
     * @author JCORTEZ
     * @since 08.04.2008
     */
    private void mostrarDetallePromocion() {
        if (tblPromociones.getRowCount() > 0) {
            txtDescPromocion.setLineWrap(true);
            txtDescPromocion.setWrapStyleWord(true);
            int vFila = tblPromociones.getSelectedRow();
            //log.debug("<<TCT 12>> Detalle Promocion: "+tblPromociones.getValueAt(vFila,COL_DESC_LARGA).toString());
            txtDescPromocion.setText(tblPromociones.getValueAt(vFila, COL_DESC_LARGA).toString());
            VariablesVentas.vDescProm = tblPromociones.getValueAt(vFila, COL_DESC_LARGA).toString();
            /* String msg ="<html>";
    String[] cadena = VariablesVentas.vDescProm.split("&");
    //txtDescPromocion.setText("<html>sdsd<br>sssssdsd<br></html>");
      for(int i=0;i<cadena.length;i++)
      {
        msg += cadena[i] + "<br>";
      }
    msg += "</html>";
    lblDescProm.setText(msg);*/
        }
    }


}
