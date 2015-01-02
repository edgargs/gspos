package mifarma.ptoventa.reportes;


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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleVentasPorVendedor extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgDetalleVentasPorVendedor.class);

    private FarmaTableModel tableModelDetalleVentasVendedor;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblDetalleVentasVendedor = new JTable();
    private JLabel lblVendedor_T = new JLabel();
    private JLabel lblVendedor = new JLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblRegistros = new JLabel();
    private JButtonLabel btnListado = new JButtonLabel();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction4 = new JLabelFunction();

    public DlgDetalleVentasPorVendedor() {
        this(null, "", false);
    }

    public DlgDetalleVentasPorVendedor(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(621, 418));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Detalle Ventas por Vendedor");

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(15, 15, 585, 50));
        jPanelTitle1.setBounds(new Rectangle(15, 70, 585, 20));
        jPanelTitle2.setBounds(new Rectangle(15, 335, 585, 20));
        jScrollPane1.setBounds(new Rectangle(15, 90, 585, 245));
        jScrollPane1.setForeground(new Color(255, 130, 14));
        tblDetalleVentasVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetalleVentasVendedor_keyPressed(e);
            }
        });
        lblVendedor_T.setText("Vendedor :");
        lblVendedor_T.setBounds(new Rectangle(25, 5, 75, 15));
        lblVendedor_T.setFont(new Font("SansSerif", 1, 11));
        lblVendedor_T.setForeground(Color.white);
        lblVendedor.setText("00000 - Alberto Canales");
        lblVendedor.setBounds(new Rectangle(120, 5, 330, 15));
        lblVendedor.setFont(new Font("SansSerif", 1, 11));
        lblVendedor.setForeground(Color.white);
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(15, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(90, 0, 35, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        btnListado.setText("Listado Detalle de Ventas por Vendedor");
        btnListado.setBounds(new Rectangle(10, 0, 235, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(25, 25, 70, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(115, 25, 101, 19));
        txtFechaIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(230, 25, 101, 19));
        txtFechaFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(360, 25, 85, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(15, 365, 105, 20));
        jLabelFunction1.setText("[ F9 ] Ordenar");
        jLabelFunction4.setBounds(new Rectangle(495, 365, 105, 20));
        jLabelFunction4.setText("[ ESC ] Cerrar");
        jScrollPane1.getViewport().add(tblDetalleVentasVendedor, null);
        jContentPane.add(jLabelFunction4, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle2.add(lblRegistros, null);
        jPanelTitle2.add(lblRegsitros_T, null);
        jContentPane.add(jPanelTitle2, null);
        jPanelTitle1.add(btnListado, null);
        jContentPane.add(jPanelTitle1, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        pnlCriterioBusqueda.add(lblVendedor, null);
        pnlCriterioBusqueda.add(lblVendedor_T, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaDetalleVentasVendedor();
    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaDetalleVentasVendedor() {
        tableModelDetalleVentasVendedor =
                new FarmaTableModel(ConstantsReporte.columnsListaDetalleVentasVendedor, ConstantsReporte.defaultValuesListaDetalleVentasVendedor,
                                    0);
        FarmaUtility.initSimpleList(tblDetalleVentasVendedor, tableModelDetalleVentasVendedor,
                                    ConstantsReporte.columnsListaDetalleVentasVendedor);
    }


    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        lblVendedor.setText(VariablesReporte.vCodigoUsuario + " - " + VariablesReporte.vNombreUsuario);
        txtFechaIni.setText(VariablesReporte.vFechaInicio);
        txtFechaFin.setText(VariablesReporte.vFechaFin);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblDetalleVentasVendedor.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblDetalleVentasVendedor);
        }
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void tblDetalleVentasVendedor_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }
    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (tblDetalleVentasVendedor.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
            else
                VariablesReporte.vOrdenar = " ";
            muestraVentaOrdenar();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F10(e)) {

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        }
    }

    void muestraVentaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_DESCRIP_CAMPO = { "Correlativo", "Codigo", "Descripcion", "Unidad", "Cantidad", "Venta", "Zan" };
        String[] IND_CAMPO = { "0", "1", "2", "3", "4", "5", "6" };
        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEDETALLEVENTASVENDEDOR;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblDetalleVentasVendedor, tableModelDetalleVentasVendedor, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblDetalleVentasVendedor.repaint();
        }
    }

    private void busqueda() {
        if (validarCampos()) {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {

                    buscaRegistroDetalleVentasVendedor(FechaInicio, FechaFin, VariablesReporte.vCodigoUsuario);

                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);
        }
    }


    private void buscaRegistroDetalleVentasVendedor(String pFechaInicio, String pFechaFin, String pUsuario) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        VariablesReporte.vCodigoUsuario = pUsuario;
        // TOTALES
        if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_TOTALES)) {


            cargaRegistroDetalleVentasVendedor();

        }

        //TIPO MEZON
        else if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_MEZON)) {
            this.setTitle("Detalle Ventas por Mezon");
            cargaRegistroDetalleVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Mezon);

        }
        //TIPO DELIVERY
        else if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_DELIVERY)) {
            this.setTitle("Detalle Ventas por Delivery");
            cargaRegistroDetalleVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Delivery);

        }
        //TIPO INSTITUCIONAL
        else {
            this.setTitle("Detalle Ventas por Institucional");
            cargaRegistroDetalleVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Institucional);

        }

    }

    private void cargaRegistroDetalleVentasVendedor() {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);
            log.debug(VariablesReporte.vUsuario);
            DBReportes.cargaListaDetalleVentasporVendedor(tableModelDetalleVentasVendedor,
                                                          VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin,
                                                          VariablesReporte.vCodigoUsuario);
            lblRegistros.setText("" + tblDetalleVentasVendedor.getRowCount());

            if (tblDetalleVentasVendedor.getRowCount() == 0) {

                FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {

                FarmaUtility.ordenar(tblDetalleVentasVendedor, tableModelDetalleVentasVendedor, 0,
                                     FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocusJTable(tblDetalleVentasVendedor);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el Detalle de Ventas por Vendedor : \n" +
                    sql.getMessage(), txtFechaIni);
            cerrarVentana(false);
        }
    }

    private void cargaRegistroDetalleVentasVendedorTipo(String pTipo) {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);
            log.debug(VariablesReporte.vUsuario);
            DBReportes.cargaListaDetalleVentasporVendedorTipo(tableModelDetalleVentasVendedor,
                                                              VariablesReporte.vFechaInicio,
                                                              VariablesReporte.vFechaFin,
                                                              VariablesReporte.vCodigoUsuario, pTipo);
            lblRegistros.setText("" + tblDetalleVentasVendedor.getRowCount());

            if (tblDetalleVentasVendedor.getRowCount() == 0) {

                FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {

                FarmaUtility.ordenar(tblDetalleVentasVendedor, tableModelDetalleVentasVendedor, 0,
                                     FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocusJTable(tblDetalleVentasVendedor);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el Detalle de Ventas por Vendedor : \n" +
                    sql.getMessage(), txtFechaIni);
            cerrarVentana(false);
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


}
