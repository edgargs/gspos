package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import oracle.jdeveloper.layout.XYConstraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgOCProdDist.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      08.11.2007   Creación<br>
 * <br>
 * @author Jorge Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */

public class DlgOCProdDist extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgOCProdDist.class);

    private JFrame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private FarmaTableModel tableModel1;
    private FarmaTableModel tableModel2;

    private FarmaTableModel tableModelUnidVend;
    private final int COL_UV_MES0 = 1;
    private final int COL_UV_MES1 = 2;
    private final int COL_UV_MES2 = 3;
    private final int COL_UV_MES3 = 4;

    private FarmaTableModel tableModelSaldos;
    private final int COL_SA_STOCK = 1;
    private final int COL_SA_TRAN = 2;
    private ArrayList arrayDetalle = new ArrayList();

    private final int COL_EST = 5;
    private final int COL_NUM = 0;

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JScrollPane scrOC = new JScrollPane();
    private JTable tblListaOC = new JTable();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JPanelTitle lblTitle2 = new JPanelTitle();
    private JLabelWhite lblCantProd_ = new JLabelWhite();
    private JLabelWhite lblCantPedidos = new JLabelWhite();
    private JLabelWhite lblZona = new JLabelWhite();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblLab = new JLabelWhite();
    private JLabelWhite lblLab_T = new JLabelWhite();
    private JLabelWhite lblDescProd = new JLabelWhite();
    private JLabelWhite lblDesc_T = new JLabelWhite();
    private JLabelWhite lblUnidadPresentacion = new JLabelWhite();
    private JLabelWhite lblCodigo = new JLabelWhite();
    private JLabelWhite lblPresent_T = new JLabelWhite();
    private JLabelWhite lblCodigo_T = new JLabelWhite();
    private JPanelTitle pnlTitulo4 = new JPanelTitle();
    private JButtonLabel btnListadoPendiente = new JButtonLabel();
    private JScrollPane srcOCPend = new JScrollPane();
    private JTable tblOCPend = new JTable();
    private JScrollPane srcUnidadesVendidas = new JScrollPane();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabelWhite lblSaldoQS = new JLabelWhite();
    private JLabelOrange lblSaldoQS_T = new JLabelOrange();
    private JPanelTitle pnlTitulo6 = new JPanelTitle();
    private JLabelOrange lblSStock = new JLabelOrange();
    private JLabelOrange lblSTran = new JLabelOrange();
    private JButtonLabel lblSaldos = new JButtonLabel();
    private JScrollPane srcSaldos = new JScrollPane();
    private JPanelTitle pnlTitulo3 = new JPanelTitle();
    private JButtonLabel btnListado3 = new JButtonLabel();
    private JPanelTitle pnlTitulo7 = new JPanelTitle();
    private JLabelOrange lblUVMes3 = new JLabelOrange();
    private JLabelOrange lblUVMes2 = new JLabelOrange();
    private JLabelOrange lblUVMes1 = new JLabelOrange();
    private JLabelOrange lblUVMes0 = new JLabelOrange();
    private JButtonLabel btnListado8 = new JButtonLabel();
    private JPanelTitle pnlTitulo2 = new JPanelTitle();
    private JButtonLabel btnListado4 = new JButtonLabel();
    private JTable tblSaldos = new JTable();
    private JTable tblUnidVend = new JTable();

    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgOCProdDist() {
        this(null, "", false);
    }

    public DlgOCProdDist(JFrame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(794, 534));
        this.setDefaultCloseOperation(0);
        this.setTitle("Orden de Compra del Producto");
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        scrOC.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        tblListaOC.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaOC_keyPressed(e);
            }
        });
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblTitle2.setBounds(new Rectangle(5, 450, 770, 20));
        lblCantProd_.setText("Cantidad Registros :");
        lblCantProd_.setBounds(new Rectangle(600, 0, 120, 20));
        lblCantPedidos.setBounds(new Rectangle(725, 0, 45, 20));
        lblZona.setBounds(new Rectangle(15, 0, 120, 20));


        jPanelHeader1.setBounds(new Rectangle(5, 5, 770, 65));
        lblLab.setBounds(new Rectangle(345, 40, 290, 15));
        lblLab.setFont(new Font("SansSerif", 0, 11));
        lblLab_T.setText("Laboratorio:");
        lblLab_T.setBounds(new Rectangle(260, 40, 70, 15));
        lblDescProd.setBounds(new Rectangle(345, 10, 290, 15));
        lblDescProd.setFont(new Font("SansSerif", 0, 11));
        lblDesc_T.setText("Producto :");
        lblDesc_T.setBounds(new Rectangle(260, 10, 70, 15));
        lblUnidadPresentacion.setBounds(new Rectangle(65, 40, 170, 15));
        lblUnidadPresentacion.setFont(new Font("SansSerif", 0, 11));
        lblCodigo.setBounds(new Rectangle(75, 10, 70, 15));
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        lblPresent_T.setText("Unidad:");
        lblPresent_T.setBounds(new Rectangle(10, 40, 50, 15));
        lblCodigo_T.setText("Codigo :");
        lblCodigo_T.setBounds(new Rectangle(10, 10, 50, 15));
        pnlTitulo4.setBounds(new Rectangle(5, 220, 770, 20));
        btnListadoPendiente.setText("O/C Pendientes");
        btnListadoPendiente.setBounds(new Rectangle(10, 0, 130, 20));
        btnListadoPendiente.setMnemonic('O');
        btnListadoPendiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListadoPendiente_actionPerformed(e);
            }
        });
        srcOCPend.setBounds(new Rectangle(5, 240, 770, 65));
        tblOCPend.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblOCPend_keyPressed(e);
            }
        });
        srcUnidadesVendidas.setBounds(new Rectangle(280, 100, 355, 70));
        jPanelWhite1.setBounds(new Rectangle(10, 190, 245, 20));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblSaldoQS.setText("0");
        lblSaldoQS.setBounds(new Rectangle(100, 0, 70, 20));
        lblSaldoQS.setBackground(new Color(255, 130, 14));
        lblSaldoQS.setOpaque(true);
        lblSaldoQS.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSaldoQS_T.setText("Almacen QS:");
        lblSaldoQS_T.setBounds(new Rectangle(5, 0, 80, 20));
        pnlTitulo6.setBounds(new Rectangle(10, 170, 245, 20));
        lblSStock.setText("0");
        lblSStock.setBounds(new Rectangle(100, 0, 70, 20));
        lblSStock.setBackground(Color.white);
        lblSStock.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblSStock.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSStock.setOpaque(true);
        lblSTran.setText("0");
        lblSTran.setBounds(new Rectangle(170, 0, 70, 20));
        lblSTran.setBackground(Color.white);
        lblSTran.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblSTran.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSTran.setOpaque(true);
        lblSaldos.setText("TOTAL :");
        lblSaldos.setBounds(new Rectangle(5, 0, 55, 20));
        lblSaldos.setMnemonic('S');
        srcSaldos.setBounds(new Rectangle(10, 100, 245, 70));
        pnlTitulo3.setBounds(new Rectangle(10, 80, 245, 20));
        btnListado3.setText("Saldos");
        btnListado3.setBounds(new Rectangle(5, 0, 65, 20));
        pnlTitulo7.setBounds(new Rectangle(280, 170, 355, 20));
        pnlTitulo7.setLayout(null);
        lblUVMes3.setText("0");
        lblUVMes3.setBackground(Color.white);
        lblUVMes3.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblUVMes3.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUVMes3.setOpaque(true);
        lblUVMes3.setBounds(new Rectangle(280, 0, 70, 20));
        lblUVMes2.setText("0");
        lblUVMes2.setBackground(Color.white);
        lblUVMes2.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblUVMes2.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUVMes2.setOpaque(true);
        lblUVMes2.setBounds(new Rectangle(210, 0, 70, 20));
        lblUVMes1.setText("0");
        lblUVMes1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblUVMes1.setOpaque(true);
        lblUVMes1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUVMes1.setBackground(Color.white);
        lblUVMes1.setBounds(new Rectangle(140, 0, 70, 20));
        lblUVMes0.setText("0");
        lblUVMes0.setBackground(Color.white);
        lblUVMes0.setOpaque(true);
        lblUVMes0.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblUVMes0.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUVMes0.setBounds(new Rectangle(70, 0, 70, 20));
        btnListado8.setText("TOTAL:");
        btnListado8.setMnemonic('U');
        btnListado8.setBounds(new Rectangle(10, 0, 55, 20));
        pnlTitulo2.setBounds(new Rectangle(280, 80, 355, 20));
        btnListado4.setText("Unidades Vendidas");
        btnListado4.setBounds(new Rectangle(10, 0, 155, 20));

        srcOCPend.getViewport();
        srcUnidadesVendidas.getViewport();
        pnlTitulo7.add(lblUVMes3, new XYConstraints(210, 0, 70, 20));
        pnlTitulo7.add(lblUVMes2, new XYConstraints(210, 0, 70, 20));
        pnlTitulo7.add(lblUVMes1, new XYConstraints(140, 0, 70, 20));
        pnlTitulo7.add(lblUVMes0, new XYConstraints(70, 0, 70, 20));
        pnlTitulo7.add(btnListado8, new XYConstraints(10, 0, 55, 20));
        jPanelWhite1.add(lblSaldoQS, null);
        jPanelWhite1.add(lblSaldoQS_T, null);
        pnlTitulo6.add(lblSStock, null);
        pnlTitulo6.add(lblSTran, null);
        pnlTitulo6.add(lblSaldos, null);
        srcSaldos.getViewport();
        jPanelHeader1.add(lblLab, null);
        jPanelHeader1.add(lblLab_T, null);
        jPanelHeader1.add(lblDescProd, null);
        jPanelHeader1.add(lblDesc_T, null);
        jPanelHeader1.add(lblUnidadPresentacion, null);
        jPanelHeader1.add(lblCodigo, null);
        jPanelHeader1.add(lblPresent_T, null);
        jPanelHeader1.add(lblCodigo_T, null);
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);

        scrOC.setBounds(new Rectangle(5, 335, 770, 115));
        jPanelTitle1.setBounds(new Rectangle(5, 315, 770, 20));
        btnLista.setText("Listado ");
        btnLista.setBounds(new Rectangle(5, 0, 135, 20));
        btnLista.setMnemonic('L');
        lblFCerrar.setBounds(new Rectangle(675, 475, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        lblTitle2.add(lblZona, null);
        lblTitle2.add(lblCantPedidos, null);
        lblTitle2.add(lblCantProd_, null);
        pnlTitulo2.add(btnListado4, null);
        pnlBlanco.add(pnlTitulo2, null);
        pnlBlanco.add(pnlTitulo7, null);
        pnlTitulo3.add(btnListado3, null);
        pnlBlanco.add(pnlTitulo3, null);
        srcSaldos.getViewport().add(tblSaldos, null);
        pnlBlanco.add(srcSaldos, null);
        pnlBlanco.add(pnlTitulo6, null);
        pnlBlanco.add(jPanelWhite1, null);
        srcUnidadesVendidas.getViewport().add(tblUnidVend, null);
        pnlBlanco.add(srcUnidadesVendidas, null);
        srcOCPend.getViewport().add(tblOCPend, null);
        pnlBlanco.add(srcOCPend, null);
        pnlTitulo4.add(btnListadoPendiente, null);
        pnlBlanco.add(pnlTitulo4, null);
        pnlBlanco.add(jPanelHeader1, null);
        pnlBlanco.add(lblTitle2, null);
        pnlBlanco.add(lblFCerrar, null);
        jPanelTitle1.add(btnLista, null);
        pnlBlanco.add(jPanelTitle1, null);
        scrOC.getViewport().add(tblListaOC, null);
        pnlBlanco.add(scrOC, null);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        verDetalle();
        cargarListaOCPendientes();
        cargarListaOC();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel1 =
                new FarmaTableModel(ConstantsProducto.columnsListaOCPendientes, ConstantsProducto.defaultValuesListaOCPendientes,
                                    0);
        FarmaUtility.initSimpleList(tblOCPend, tableModel1, ConstantsProducto.columnsListaOCPendientes);

        tableModel2 =
                new FarmaTableModel(ConstantsProducto.columnsListaIngresos, ConstantsProducto.defaultValuesListaIngresos,
                                    0);
        FarmaUtility.initSimpleList(tblListaOC, tableModel2, ConstantsProducto.columnsListaIngresos);

        initTableSaldos();
        initTableUnidVend();
    }

    private void initTableUnidVend() {
        resolverMesesUnidVend();
        tableModelUnidVend =
                new FarmaTableModel(VariablesProducto.columnsListaUnidadesVendidas, VariablesProducto.defaultValuesListaUnidadesVendidas,
                                    0);
        FarmaUtility.initSimpleList(tblUnidVend, tableModelUnidVend, VariablesProducto.columnsListaUnidadesVendidas);
        mostrarUnidVend();
    }

    private void initTableSaldos() {
        tableModelSaldos =
                new FarmaTableModel(ConstantsProducto.columnsListaSaldos, ConstantsProducto.defaultValuesListaSaldos,
                                    0);
        FarmaUtility.initSimpleList(tblSaldos, tableModelSaldos, ConstantsProducto.columnsListaSaldos);
        mostrarSaldos();
        mostrarSaldoQS();
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        lblCantPedidos.setText("" + tblListaOC.getRowCount());
        FarmaUtility.moveFocus(tblListaOC);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaOC);
    }

    private void txtProducto_keyReleased(KeyEvent e) {

    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void btnListadoPendiente_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblOCPend);
    }

    private void tblOCPend_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaOC_keyPressed(KeyEvent e) {
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
     * Se lista los pedidos existentes de distribucion
     * */
    private void cargarListaOC() {

        try {
            DBProducto.cargaListaOCIngresos(tableModel2, VariablesProducto.cCodProd);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error en listar las ordenes Ingresadas.\n" +
                    sql.getMessage(), tblListaOC);
        }
    }

    /**
     * Se lista los pedidos existentes de distribucion
     * */
    private void cargarListaOCPendientes() {

        try {
            DBProducto.cargaListaOCPendiente(tableModel1, VariablesProducto.cCodProd);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar las OC pendientes.\n" +
                    sql.getMessage(), tblOCPend);
        }
    }

    /**
     * Se muestra el detalle del producto seleccionado
     * */
    private void verDetalle() {

        lblCodigo.setText(VariablesProducto.cCodProd);
        lblDescProd.setText(VariablesProducto.cDescProd);
        lblUnidadPresentacion.setText(VariablesProducto.cUnidPres);
        lblLab.setText(VariablesProducto.cDesLab);
    }


    /**
     * Muestra el resumen de Saldos.
     */
    private void mostrarSaldos() {
        try {
            DBProducto.listarSaldosProd(tableModelSaldos, VariablesProducto.cCodProd);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al mostrar los saldos.\n" +
                    e.getMessage(), tblSaldos);
        } finally {
            lblSStock.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSaldos, COL_SA_STOCK), 2));
            lblSTran.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSaldos, COL_SA_TRAN), 2));
        }
    }


    /**
     * Muestra el resumen de Unid Vend y totales.
     */
    private void mostrarUnidVend() {
        try {
            DBProducto.listarUnidVendProd(tableModelUnidVend, VariablesProducto.cCodProd);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al mostrar las Unid Vend.\n" +
                    e.getMessage(), tblUnidVend);
        } finally {
            lblUVMes0.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblUnidVend, COL_UV_MES0), 2));
            lblUVMes1.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblUnidVend, COL_UV_MES1), 2));
            lblUVMes2.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblUnidVend, COL_UV_MES2), 2));
            lblUVMes3.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblUnidVend, COL_UV_MES3), 2));
        }
    }

    /**
     * Obtiene los meses de Unid. Vend.
     */
    private void resolverMesesUnidVend() {
        try {
            String[] auxArray = new String[4];
            ArrayList aux = new ArrayList();
            ArrayList auxList;
            int mes;
            DBProducto.getMesesUnidVend(aux);
            if (aux.size() > 0) {
                for (int i = 0; i < aux.size(); i++) {
                    auxList = (ArrayList)aux.get(i);
                    auxArray[i] = auxList.get(1).toString();
                }
                VariablesProducto.mesesUnidVend = auxArray;
                //
                FarmaColumnData auxColumnData[] =
                { new FarmaColumnData("Zona", 70, JLabel.LEFT), new FarmaColumnData(VariablesProducto.mesesUnidVend[0],
                                                                                    70, JLabel.RIGHT),
                  new FarmaColumnData(VariablesProducto.mesesUnidVend[1], 70, JLabel.RIGHT),
                  new FarmaColumnData(VariablesProducto.mesesUnidVend[2], 70, JLabel.RIGHT),
                  new FarmaColumnData(VariablesProducto.mesesUnidVend[3], 70, JLabel.RIGHT) };

                VariablesProducto.columnsListaUnidadesVendidas = auxColumnData;
            }
        } catch (SQLException e) {
            log.error("", e);
        }
    }


    /**
     * Muestra saldo QS.
     */
    private void mostrarSaldoQS() {
        String vSaldoQS = " ";
        try {
            vSaldoQS = DBProducto.listarSaldoQSProd(VariablesProducto.cCodProd);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al mostrar los saldo QS.\n" +
                    e.getMessage(), tblOCPend);
        } finally {
            lblSaldoQS.setText(vSaldoQS);
        }
    }

    private void tblOCPendientes_keyPressed(KeyEvent e) {
    }


}
