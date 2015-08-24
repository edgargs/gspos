package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;

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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
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


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDetalleProducto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      29.12.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgDetalleProducto extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgDetalleProducto.class);

    /** Objeto Frame de la Aplicación */
    private Frame myParentFrame;
    private FarmaTableModel tableModelPrincActProd;
    private FarmaTableModel tableModelAcciTerap;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    JPanel pnlStock = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    JLabel lblUnidades = new JLabel();
    JLabel lblStock = new JLabel();
    JLabel lblFechaHora = new JLabel();
    JLabel lblStockTexto = new JLabel();
    JPanel pnlDetalleProducto = new JPanel();
    private JSeparator jSeparator1 = new JSeparator();
    private JLabel lblBono = new JLabel();
    private JLabel lblBono_T = new JLabel();
    JLabel lblUnidadT = new JLabel();
    JLabel lblDescripcionT = new JLabel();
    JLabel lblCodigoT = new JLabel();
    JLabel lblLaboratorio = new JLabel();
    JLabel lblDcto = new JLabel();
    JLabel lblLaboratorioT = new JLabel();
    JLabel lblDscto = new JLabel();
    JLabel lblPrecio = new JLabel();
    JLabel lblPrecioT = new JLabel();
    JLabel lblUnidad = new JLabel();
    JLabel lblDescripcion = new JLabel();
    JLabel lblCodigo = new JLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaPrincipioActivo = new JScrollPane();
    private JTable tblListaPrincipiosActivos = new JTable();
    private JButtonLabel btnPrincAct = new JButtonLabel();
    private JSeparator jSeparator2 = new JSeparator();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JButtonLabel btnPrincAct1 = new JButtonLabel();
    private JScrollPane scrListaAccionTerapeutica = new JScrollPane();
    private JPanelTitle pnlTitle3 = new JPanelTitle();
    private JButtonLabel btnAccTerap = new JButtonLabel();
    private JTable tblListaAccionTerapeutica = new JTable();
    private JLabel lblZan_T = new JLabel();
    private JLabel lblZan = new JLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     *Constructor
     */
    public DlgDetalleProducto() {
        this(null, "", false);
    }

    /**
     *Constructor
     *@param parent Objeto Frame de la Aplicación.
     *@param title Título de la Ventana.
     *@param modal Tipo de Ventana.
     */
    public DlgDetalleProducto(Frame parent, String title, boolean modal) {
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

    /**
     *Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(409, 523));
        this.getContentPane().setLayout(null);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Detalle de Producto");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(400, 428));
        jContentPane.setBackground(Color.white);
        pnlStock.setBounds(new Rectangle(15, 10, 370, 55));
        pnlStock.setFont(new Font("SansSerif", 0, 11));
        pnlStock.setBackground(new Color(255, 130, 14));
        pnlStock.setLayout(xYLayout2);
        pnlStock.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlStock.setForeground(Color.white);
        lblUnidades.setText("unidades");
        lblUnidades.setFont(new Font("SansSerif", 1, 14));
        lblUnidades.setForeground(Color.white);
        lblStock.setFont(new Font("SansSerif", 1, 15));
        lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStock.setForeground(Color.white);
        lblFechaHora.setFont(new Font("SansSerif", 0, 12));
        lblFechaHora.setForeground(Color.white);
        lblStockTexto.setText("Stock del Producto al");
        lblStockTexto.setFont(new Font("SansSerif", 0, 12));
        lblStockTexto.setForeground(Color.white);
        pnlDetalleProducto.setBounds(new Rectangle(15, 70, 370, 385));
        pnlDetalleProducto.setLayout(null);
        pnlDetalleProducto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlDetalleProducto.setFont(new Font("SansSerif", 0, 11));
        pnlDetalleProducto.setBackground(Color.white);
        jSeparator1.setBounds(new Rectangle(5, 140, 355, 10));
        lblBono.setFont(new Font("SansSerif", 0, 11));
        lblBono.setBounds(new Rectangle(335, 5, 25, 20));
        lblBono_T.setText("Bono :");
        lblBono_T.setFont(new Font("SansSerif", 1, 11));
        lblBono_T.setBounds(new Rectangle(295, 5, 40, 20));
        lblUnidadT.setText("Unidad :");
        lblUnidadT.setFont(new Font("SansSerif", 1, 11));
        lblUnidadT.setBounds(new Rectangle(10, 85, 50, 20));
        lblDescripcionT.setText("Descripcion");
        lblDescripcionT.setFont(new Font("SansSerif", 1, 11));
        lblDescripcionT.setBounds(new Rectangle(10, 35, 80, 20));
        lblCodigoT.setText("Codigo");
        lblCodigoT.setFont(new Font("SansSerif", 1, 11));
        lblCodigoT.setBounds(new Rectangle(10, 10, 55, 20));
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorio.setBounds(new Rectangle(95, 60, 260, 20));
        lblDcto.setHorizontalAlignment(SwingConstants.LEFT);
        lblDcto.setFont(new Font("SansSerif", 0, 11));
        lblDcto.setBounds(new Rectangle(255, 110, 40, 20));
        lblLaboratorioT.setText("Laboratorio :");
        lblLaboratorioT.setFont(new Font("SansSerif", 1, 11));
        lblLaboratorioT.setBounds(new Rectangle(10, 60, 80, 20));
        lblDscto.setText("% Dcto. :");
        lblDscto.setFont(new Font("SansSerif", 1, 11));
        lblDscto.setBounds(new Rectangle(195, 110, 50, 20));
        lblPrecio.setHorizontalAlignment(SwingConstants.LEFT);
        lblPrecio.setFont(new Font("SansSerif", 0, 11));
        lblPrecio.setBounds(new Rectangle(120, 110, 55, 20));
        lblPrecioT.setText("Precio Venta : S/.");
        lblPrecioT.setFont(new Font("SansSerif", 1, 11));
        lblPrecioT.setBounds(new Rectangle(10, 110, 100, 20));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblUnidad.setBounds(new Rectangle(95, 85, 215, 20));
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setBounds(new Rectangle(95, 35, 260, 20));
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        lblCodigo.setBounds(new Rectangle(95, 10, 55, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(300, 465, 85, 20));
        pnlTitle1.setBounds(new Rectangle(10, 145, 345, 15));
        scrListaPrincipioActivo.setBounds(new Rectangle(10, 160, 345, 90));
        tblListaPrincipiosActivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaPrincipiosActivos_keyPressed(e);
            }
        });
        btnPrincAct.setText("Principio Activo :");
        btnPrincAct.setBounds(new Rectangle(5, 0, 110, 15));
        btnPrincAct.setMnemonic('p');
        btnPrincAct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPrincAct_actionPerformed(e);
            }
        });
        jSeparator2.setBounds(new Rectangle(5, 255, 355, 10));
        pnlTitle2.setBounds(new Rectangle(10, 145, 345, 15));
        btnPrincAct1.setText("Principio Activo :");
        btnPrincAct1.setBounds(new Rectangle(5, 0, 110, 15));
        btnPrincAct1.setMnemonic('p');
        btnPrincAct1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPrincAct_actionPerformed(e);
            }
        });
        scrListaAccionTerapeutica.setBounds(new Rectangle(10, 280, 345, 90));
        pnlTitle3.setBounds(new Rectangle(10, 265, 345, 15));
        btnAccTerap.setText("Acción Terapéutica");
        btnAccTerap.setBounds(new Rectangle(5, 0, 110, 15));
        btnAccTerap.setMnemonic('a');
        btnAccTerap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnAccTerap_actionPerformed(e);
            }
        });
        tblListaAccionTerapeutica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaAccionTerapeutica_keyPressed(e);
            }
        });
        lblZan_T.setText("Zan :");
        lblZan_T.setFont(new Font("SansSerif", 1, 11));
        lblZan_T.setBounds(new Rectangle(200, 10, 30, 20));
        lblZan.setFont(new Font("SansSerif", 0, 11));
        lblZan.setBounds(new Rectangle(230, 10, 50, 20));
        scrListaAccionTerapeutica.getViewport();
        pnlStock.add(lblUnidades, new XYConstraints(260, 15, 75, 20));
        pnlStock.add(lblStock, new XYConstraints(170, 10, 80, 30));
        pnlStock.add(lblFechaHora, new XYConstraints(20, 25, 130, 20));
        pnlStock.add(lblStockTexto, new XYConstraints(20, 5, 125, 20));
        pnlTitle3.add(btnAccTerap, null);
        pnlDetalleProducto.add(lblZan, null);
        pnlDetalleProducto.add(lblZan_T, null);
        pnlDetalleProducto.add(pnlTitle3, null);
        scrListaAccionTerapeutica.getViewport().add(tblListaAccionTerapeutica, null);
        pnlDetalleProducto.add(scrListaAccionTerapeutica, null);
        pnlTitle2.add(btnPrincAct1, null);
        pnlDetalleProducto.add(pnlTitle2, null);
        pnlDetalleProducto.add(jSeparator2, null);
        scrListaPrincipioActivo.getViewport().add(tblListaPrincipiosActivos, null);
        pnlDetalleProducto.add(scrListaPrincipioActivo, null);
        pnlTitle1.add(btnPrincAct, null);
        pnlDetalleProducto.add(pnlTitle1, null);
        pnlDetalleProducto.add(jSeparator1, null);
        pnlDetalleProducto.add(lblBono, null);
        pnlDetalleProducto.add(lblBono_T, null);
        pnlDetalleProducto.add(lblUnidadT, null);
        pnlDetalleProducto.add(lblDescripcionT, null);
        pnlDetalleProducto.add(lblCodigoT, null);
        pnlDetalleProducto.add(lblLaboratorio, null);
        pnlDetalleProducto.add(lblDcto, null);
        pnlDetalleProducto.add(lblLaboratorioT, null);
        pnlDetalleProducto.add(lblDscto, null);
        pnlDetalleProducto.add(lblPrecio, null);
        pnlDetalleProducto.add(lblPrecioT, null);
        pnlDetalleProducto.add(lblUnidad, null);
        pnlDetalleProducto.add(lblDescripcion, null);
        pnlDetalleProducto.add(lblCodigo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlStock, null);
        jContentPane.add(pnlDetalleProducto, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTablePrincActProd();
        initTableAccTerapProd();
        FarmaVariables.vAceptar = false;
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTablePrincActProd() {
        tableModelPrincActProd =
                new FarmaTableModel(ConstantsVentas.columnsListaPrincipiosActivos, ConstantsVentas.defaultValuesListaPrincipiosActivos,
                                    0);
        FarmaUtility.initSimpleList(tblListaPrincipiosActivos, tableModelPrincActProd,
                                    ConstantsVentas.columnsListaPrincipiosActivos);
    }

    private void initTableAccTerapProd() {
        tableModelAcciTerap =
                new FarmaTableModel(ConstantsVentas.columnsListaAccionesTerapeuticas, ConstantsVentas.defaultValuesListaAccionesTerapeuticas,
                                    0);
        FarmaUtility.initSimpleList(tblListaAccionTerapeutica, tableModelAcciTerap,
                                    ConstantsVentas.columnsListaAccionesTerapeuticas);
    }


    private void cargaListaPrincipiosActivos() {
        try {
            DBVentas.obtienePrincActXProducto(tableModelPrincActProd, VariablesVentas.vCod_Prod);
            FarmaUtility.ordenar(tblListaPrincipiosActivos, tableModelPrincActProd, 1,
                                 FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargtar lista de principios activos. \n" +
                    sql.getMessage(), tblListaAccionTerapeutica);
        }
    }

    private void cargaListaAccionesTerapeuticas() {
        try {
            DBVentas.obtieneAccionesTerapXProducto(tableModelAcciTerap, VariablesVentas.vCod_Prod);
            FarmaUtility.ordenar(tblListaAccionTerapeutica, tableModelAcciTerap, 1, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargtar lista de acciones terapeuticas. \n" +
                    sql.getMessage(), tblListaAccionTerapeutica);
        }
    }

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        try {
            //ERIOS 06.06.2008 Solución temporal para evitar la venta sugerida por convenio
            if (VariablesVentas.vEsPedidoConvenio) {
                DBVentas.obtieneInfoDetalleProducto(pArrayList, VariablesVentas.vCod_Prod);
            } else {
                DBVentas.obtieneInfoDetalleProductoVta(pArrayList, VariablesVentas.vCod_Prod);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargtar lista de principios activos. \n" +
                    sql.getMessage(), tblListaAccionTerapeutica);
        }
    }

    private void muestraInfoDetalleProd() {
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        if (myArray.size() == 1) {
            VariablesVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesVentas.vDesc_Acc_Terap = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesVentas.vStk_Prod_Fecha_Actual = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            VariablesVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            VariablesVentas.vUnid_Vta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesVentas.vVal_Bono = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            VariablesVentas.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
            VariablesVentas.vIndZan = ((String)((ArrayList)myArray.get(0)).get(10)).trim();
        } else {
            VariablesVentas.vStk_Prod = "";
            VariablesVentas.vDesc_Acc_Terap = "";
            VariablesVentas.vStk_Prod_Fecha_Actual = "";
            VariablesVentas.vVal_Prec_Vta = "";
            VariablesVentas.vUnid_Vta = "";
            VariablesVentas.vVal_Bono = "";
            VariablesVentas.vPorc_Dcto_1 = "";
            VariablesVentas.vNom_Lab = "";
            VariablesVentas.vDesc_Prod = "";
            VariablesVentas.vCod_Prod = "";
            VariablesVentas.vVal_Prec_Lista = "";
            VariablesVentas.vIndZan = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", null);
            cerrarVentana();
        }
        lblFechaHora.setText(VariablesVentas.vStk_Prod_Fecha_Actual);
        lblStock.setText(VariablesVentas.vStk_Prod);
        lblCodigo.setText(VariablesVentas.vCod_Prod);
        lblBono.setText(VariablesVentas.vVal_Bono);
        lblDescripcion.setText(VariablesVentas.vDesc_Prod);
        lblLaboratorio.setText(VariablesVentas.vNom_Lab);
        lblUnidad.setText(VariablesVentas.vUnid_Vta);
        lblPrecio.setText(VariablesVentas.vVal_Prec_Vta);
        lblDcto.setText(VariablesVentas.vPorc_Dcto_1);
        lblZan.setText(VariablesVentas.vIndZan);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        muestraInfoDetalleProd();
        cargaListaPrincipiosActivos();
        cargaListaAccionesTerapeuticas();
        FarmaUtility.moveFocus(tblListaPrincipiosActivos);
        lblDcto.setVisible(false);
        lblDscto.setVisible(false);
        lblBono_T.setVisible(false);
        lblBono.setVisible(false);
    }

    private void tblListaPrincipiosActivos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaAccionTerapeutica_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnPrincAct_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPrincipiosActivos);
    }

    private void btnAccTerap_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaAccionTerapeutica);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana();
        }
    }

    private void cerrarVentana() {
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

}
