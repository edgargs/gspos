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
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.DlgFiltroProductos;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgVentasPorProducto extends JDialog {
    private FarmaTableModel tableModelVentasProducto;
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgVentasPorProducto.class);

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane scrProductos = new JScrollPane();
    private JTable tblVentasProducto = new JTable();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblCantReg = new JLabel();
    private JLabelFunction lblVerVendedor = new JLabelFunction();
    private JLabelFunction lblFiltrarProds = new JLabelFunction();
    private JLabelFunction lblQuitarFiltro = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();

    public DlgVentasPorProducto() {
        this(null, "", false);
    }

    public DlgVentasPorProducto(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(780, 473));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ventas por Producto");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 755, 55));
        pnlTitulo.setBounds(new Rectangle(10, 70, 755, 20));
        pnlResultados.setBounds(new Rectangle(10, 360, 755, 20));
        scrProductos.setBounds(new Rectangle(10, 90, 755, 270));
        tblVentasProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblVentasProducto_keyPressed(e);
            }
        });
        btnRandoFec.setText("Rango de Fechas");
        btnRandoFec.setBounds(new Rectangle(80, 5, 100, 20));
        btnRandoFec.setMnemonic('f');
        btnRandoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRandoFec_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(190, 5, 101, 19));
        txtFechaIni.setDocument(new FarmaLengthText(10));
        txtFechaIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(315, 5, 101, 19));
        txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(445, 5, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        btnListado.setText("Listado de Ventas por Producto");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(35, 0, 75, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblCantReg.setText("0");
        lblCantReg.setBounds(new Rectangle(120, 0, 30, 20));
        lblCantReg.setFont(new Font("SansSerif", 1, 11));
        lblCantReg.setForeground(Color.white);
        lblCantReg.setHorizontalAlignment(SwingConstants.RIGHT);
        lblVerVendedor.setBounds(new Rectangle(10, 390, 115, 20));
        lblVerVendedor.setText("[F2] Ver Vendedor");
        lblFiltrarProds.setBounds(new Rectangle(365, 390, 130, 20));
        lblFiltrarProds.setText("[F6] Filtrar Productos");
        lblQuitarFiltro.setBounds(new Rectangle(505, 390, 105, 20));
        lblQuitarFiltro.setText("[F7] Quitar Filtro");
        lblSalir.setBounds(new Rectangle(680, 420, 85, 20));
        lblSalir.setText("[ ESC ] Cerrar");
        txtProducto.setBounds(new Rectangle(190, 30, 315, 20));
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }
        });
        btnProducto.setText("Producto :");
        btnProducto.setBounds(new Rectangle(80, 30, 100, 20));
        btnProducto.setMnemonic('p');
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        lblTotCantidad.setText("0");
        lblTotCantidad.setBounds(new Rectangle(545, 0, 65, 20));
        lblTotCantidad.setFont(new Font("SansSerif", 1, 11));
        lblTotCantidad.setForeground(Color.white);
        lblTotCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotVenta.setText("0");
        lblTotVenta.setBounds(new Rectangle(615, 0, 60, 20));
        lblTotVenta.setFont(new Font("SansSerif", 1, 11));
        lblTotVenta.setForeground(Color.white);
        lblTotVenta.setHorizontalAlignment(SwingConstants.RIGHT);
        lblVerVendedor1.setBounds(new Rectangle(135, 390, 120, 20));
        lblVerVendedor1.setText("[F3] Ver Laboratorio");
        lblFiltrarProds1.setBounds(new Rectangle(265, 390, 90, 20));
        lblFiltrarProds1.setText("[F5] Ordenar");
        lblQuitarFiltro1.setBounds(new Rectangle(620, 390, 140, 20));
        lblQuitarFiltro1.setText("[F8] Ver Consolidado");
        lblQuitarFiltro2.setBounds(new Rectangle(10, 420, 145, 20));
        lblQuitarFiltro2.setText("[F9] Productos Virtuales");
        jContentPane.add(lblQuitarFiltro2, null);
        jContentPane.add(lblQuitarFiltro1, null);
        jContentPane.add(lblFiltrarProds1, null);
        jContentPane.add(lblVerVendedor1, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblQuitarFiltro, null);
        jContentPane.add(lblFiltrarProds, null);
        jContentPane.add(lblVerVendedor, null);
        scrProductos.getViewport().add(tblVentasProducto, null);
        jContentPane.add(scrProductos, null);
        jContentPane.add(pnlResultados, null);
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlResultados.add(lblTotVenta, null);
        pnlResultados.add(lblTotCantidad, null);
        pnlResultados.add(lblCantReg, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(btnProducto, null);
        pnlCriterioBusqueda.add(txtProducto, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnRandoFec, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaVentasProducto();
        VariablesReporte.vInd_Filtro = FarmaConstants.INDICADOR_N;
        FarmaVariables.vAceptar = false;

    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaVentasProducto() {
        tableModelVentasProducto =
                new FarmaTableModel(ConstantsReporte.columnsListaVentasProducto, ConstantsReporte.defaultValuesListaVentasProducto,
                                    0);
        FarmaUtility.initSimpleList(tblVentasProducto, tableModelVentasProducto,
                                    ConstantsReporte.columnsListaVentasProducto);
    }


    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JButtonLabel btnProducto = new JButtonLabel();
    private JLabel lblTotCantidad = new JLabel();
    private JLabel lblTotVenta = new JLabel();
    private JLabelFunction lblVerVendedor1 = new JLabelFunction();
    private JLabelFunction lblFiltrarProds1 = new JLabelFunction();
    private JLabelFunction lblQuitarFiltro1 = new JLabelFunction();
    private JLabelFunction lblQuitarFiltro2 = new JLabelFunction();

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */


    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //FarmaUtility.moveFocus(btnBuscar);
            btnBuscar.doClick();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        //chkKeyPressed(e);
    }

    private void tblVentasProducto_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (tblVentasProducto.getRowCount() == 0 || tblVentasProducto.getRowCount() > 0) {
            busqueda();
        }
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblVentasProducto.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblVentasProducto);
        }
    }

    private void btnRandoFec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }
    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            // DlgDetalleVentasPorProducto dlgDetalleVentasPorProducto = new DlgDetalleVentasPorProducto(myParentFrame,"",true);
            // dlgDetalleVentasPorProducto.setVisible(true);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            DlgListaLaboratoriosProdVta dlgListaLaboratoriosProdVta =
                new DlgListaLaboratoriosProdVta(myParentFrame, "", true);
            dlgListaLaboratoriosProdVta.setVisible(true);
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            cargaListaFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (tblVentasProducto.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
            else
                muestraVentaOrdenar();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            if (VariablesReporte.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                //lblDescFiltro.setText("");
                VariablesReporte.vInd_Filtro = FarmaConstants.INDICADOR_N;
                btnBuscar.doClick();
                FarmaUtility.moveFocus(txtProducto);
            } else if (!FarmaVariables.vAceptar) {
                btnBuscar.doClick();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            mostrarConsolidado();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (tblVentasProducto.getRowCount() > 0)
                cargaRegistroVentasProductoVirtuales();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void buscaRegistroVentasProducto(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaRegistroVentasProducto();
    }

    private void cargaRegistroVentasProducto() {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);

            DBReportes.cargaListaVentasporProducto(tableModelVentasProducto, VariablesReporte.vFechaInicio,
                                                   VariablesReporte.vFechaFin);
            if (tblVentasProducto.getRowCount() > 0) {
                FarmaUtility.ordenar(tblVentasProducto, tableModelVentasProducto, 1, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocus(txtProducto);
            } else {
                txtProducto.setText("");
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar Ventas por Producto : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
        mostrarTotales();
    }

    private void cargaRegistroVentasProductoVirtuales() {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);

            DBReportes.cargaListaVentasporProductoVirtuales(tableModelVentasProducto, VariablesReporte.vFechaInicio,
                                                            VariablesReporte.vFechaFin);
            if (tblVentasProducto.getRowCount() > 0) {
                FarmaUtility.ordenar(tblVentasProducto, tableModelVentasProducto, 1, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocus(txtProducto);
            } else {
                txtProducto.setText("");
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar Ventas por Producto : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
        mostrarTotales();
    }


    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargaListaFiltro() {
        VariablesReporte.vInd_Filtro = FarmaConstants.INDICADOR_S;
        DlgFiltroProductos dlgFiltroProductos = new DlgFiltroProductos(myParentFrame, "", true);
        dlgFiltroProductos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            tableModelVentasProducto.clearTable();
            txtProducto.setText("");
            filtrarTablaProductos(VariablesPtoVenta.vTipoFiltro, VariablesPtoVenta.vCodFiltro);

            FarmaVariables.vAceptar = false;
        }
    }


    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
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
                    buscaRegistroVentasProducto(FechaInicio, FechaFin);

                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);

        }


    }

    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblVentasProducto, txtProducto, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblVentasProducto.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblVentasProducto, txtProducto.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                             txtProducto);
                    return;
                }
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            cargaRegistroSeleccionado();
            listaReumenDetalleVentasDetallado();
        }
        chkKeyPressed(e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblVentasProducto, txtProducto, 1);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void mostrarTotales() {
        if (tblVentasProducto.getRowCount() > 0) {
            double totCant = 0;
            double totVtas = 0;

            for (int i = 0; i < tblVentasProducto.getRowCount(); i++) {
                totCant =
                        totCant + FarmaUtility.getDecimalNumberRedondeado(FarmaUtility.getDecimalNumber(tblVentasProducto.getValueAt(i,
                                                                                                                                     4).toString().trim()));
                totVtas =
                        totVtas + FarmaUtility.getDecimalNumberRedondeado(FarmaUtility.getDecimalNumber(tblVentasProducto.getValueAt(i,
                                                                                                                                     5).toString().trim()));
            }

            totCant = FarmaUtility.getDecimalNumberRedondeado(totCant);
            totVtas = FarmaUtility.getDecimalNumberRedondeado(totVtas);
            lblCantReg.setText("" + tblVentasProducto.getRowCount());
            lblTotCantidad.setText("" + totCant);
            lblTotVenta.setText("" + totVtas);
        } else {
            lblCantReg.setText("0");
            lblTotCantidad.setText("0");
            lblTotVenta.setText("0");
        }
    }

    private void cargarRegistroSeleccionado() {
        VariablesReporte.vCodProd =
                tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 0).toString().trim();
        VariablesReporte.vDesProd =
                tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 1).toString().trim();
        VariablesReporte.vDescUnidPresent =
                tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 2).toString().trim();
        VariablesReporte.vNomLab =
                tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 3).toString().trim();
        VariablesReporte.vFechaInicio = txtFechaIni.getText().trim();
        VariablesReporte.vFechaFin = txtFechaFin.getText().trim();

    }

    private void filtrarTablaProductos(String pTipoFiltro, String pCodFiltro) {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);

            DBReportes.cargaListaVentasporProductoFiltro(tableModelVentasProducto, VariablesReporte.vFechaInicio,
                                                         VariablesReporte.vFechaFin, pTipoFiltro, pCodFiltro);
            if (tblVentasProducto.getRowCount() > 0)
                FarmaUtility.ordenar(tblVentasProducto, tableModelVentasProducto, 1, FarmaConstants.ORDEN_ASCENDENTE);

            if (tblVentasProducto.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al filtrar la tabla de Productos : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
        mostrarTotales();
    }

    private void cargaRegistroSeleccionado() {
        if (tblVentasProducto.getRowCount() <= 0) {
            FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", null);
            FarmaUtility.moveFocus(btnListado);
        } else
            VariablesReporte.vCodProd =
                    ((String)tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 0)).trim();
        VariablesReporte.vDesProd =
                ((String)tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 1)).trim();
        VariablesReporte.vUnidadPresentacion =
                ((String)tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 2)).trim();
        VariablesReporte.vCantidad =
                ((String)tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 4)).trim();
        VariablesReporte.vVentas =
                ((String)tblVentasProducto.getValueAt(tblVentasProducto.getSelectedRow(), 5)).trim();
    }

    private void listaReumenDetalleVentasDetallado() {
        DlgResumenDetalleVentasDetallado dlgResumenDetalleVentasDetallado =
            new DlgResumenDetalleVentasDetallado(myParentFrame, "", true);
        dlgResumenDetalleVentasDetallado.setVisible(true);
    }

    private void muestraVentaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_DESCRIP_CAMPO =
        { "Codigo", "Descripcion", "Unidad", "Laboratorio", "Cantidad", "Venta", "Stock" };
        String[] IND_CAMPO = { "0", "1", "2", "3", "4", "5", "6" };
        log.debug("Campo " + IND_DESCRIP_CAMPO[1]);
        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEVTASPRODUCTO;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblVentasProducto, tableModelVentasProducto, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblVentasProducto.repaint();
        }
    }

    private void mostrarConsolidado() {
        DlgConsolidadoVentasProducto dlgConsolidado = new DlgConsolidadoVentasProducto(myParentFrame, "", true);
        dlgConsolidado.setVisible(true);
    }
}
