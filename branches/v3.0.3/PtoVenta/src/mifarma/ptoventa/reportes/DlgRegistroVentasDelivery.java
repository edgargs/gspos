package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.UtilityDelivery;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRegistroVentasDelivery extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroVentasDelivery.class);

    private FarmaTableModel tableModelRegistroVentas;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblRegistroVentas = new JTable();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JButtonLabel btnCliente = new JButtonLabel();
    private JButtonLabel btnTelefono = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCliente = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblRegistros = new JLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabel lblTotalMonto = new JLabel();
    private String qWhere = "";
    private JComboBox cmbEstado = new JComboBox();


    Map<String, String> mapCondicionales = new TreeMap<String, String>();
    private JButtonLabel btnEstado = new JButtonLabel();

    public DlgRegistroVentasDelivery() {
        this(null, "", false);
    }

    public DlgRegistroVentasDelivery(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(788, 525));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Registro de Ventas Delivery");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 750, 95));
        pnlCriterioBusqueda.setFocusable(false);
        pnlTitulo.setBounds(new Rectangle(10, 120, 750, 20));
        pnlTitulo.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 415, 750, 20));
        pnlResultados.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 140, 750, 275));
        jScrollPane1.setFocusable(false);
        tblRegistroVentas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRegistroVentas_keyPressed(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(105, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pasarFocoActionPerformedBtn(e, txtFechaIni);
            }
        });
        cmbEstado.setBounds(new Rectangle(370, 65, 150, 20));
        cmbEstado.setFont(new Font("SansSerif", 1, 11));
        cmbEstado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbEstado_keyPressed(e);
            }
        });
        btnEstado.setText("Estado :");
        btnEstado.setMnemonic('E');
        btnEstado.setBounds(new Rectangle(300, 65, 55, 20));
        btnEstado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEstado_actionPerformed(e);
            }
        });
        btnCliente.setText("Cliente :");
        btnCliente.setBounds(new Rectangle(105, 35, 60, 20));
        btnCliente.setMnemonic('c');
        btnCliente.setFocusable(false);
        btnCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pasarFocoActionPerformedBtn(e, txtCliente);
            }
        });

        btnTelefono.setText("Teléfono :");
        btnTelefono.setBounds(new Rectangle(105, 65, 60, 20));
        btnTelefono.setMnemonic('t');
        btnTelefono.setFocusable(false);
        btnTelefono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pasarFocoActionPerformedBtn(e, txtTelefono);
            }
        });

        txtFechaIni.setBounds(new Rectangle(175, 5, 101, 19));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pasarFocoKeyPressedTxt(e, txtFechaFin);
            }

            public void keyReleased(KeyEvent e) {
                fechaKeyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(300, 5, 101, 19));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pasarFocoKeyPressedTxt(e, txtCliente);
            }

            public void keyReleased(KeyEvent e) {
                fechaKeyReleased(e);
            }
        });
        txtCliente.setBounds(new Rectangle(175, 35, 350, 20));
        txtCliente.setLengthText(100);
        txtCliente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pasarFocoKeyPressedTxt(e, txtTelefono);
            }
        });
        txtCliente.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
            }
        });
        txtTelefono.setBounds(new Rectangle(175, 65, 101, 19));
        txtTelefono.setLengthText(10);
        txtTelefono.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pasarFocoKeyPressedTxt(e, cmbEstado);
            }
        });
        txtTelefono.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(575, 65, 95, 19));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscarActionPerformed(e);
            }
        });
        btnListado.setText("Listado de Registro de Ventas");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.setFocusable(false);
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(15, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblRegsitros_T.setFocusable(false);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(90, 0, 35, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegistros.setFocusable(false);
        lblF1.setBounds(new Rectangle(80, 440, 105, 20));
        lblF1.setText("[ F1 ] Ver Detalle");
        lblF1.setFocusable(false);

        lblF6.setBounds(new Rectangle(190, 440, 120, 20));
        lblF6.setText("[ F6 ] Agregar Filtro");
        lblF6.setFocusable(false);

        lblF7.setBounds(new Rectangle(315, 440, 110, 20));
        lblF7.setText("[ F7 ] Quitar Filtro");
        lblF7.setFocusable(false);

        lblF9.setBounds(new Rectangle(430, 440, 100, 20));
        lblF9.setText("[ F9 ] Ordenar");
        lblF9.setFocusable(false);
        lblF12.setBounds(new Rectangle(535, 440, 110, 20));
        lblF12.setText("[ F12 ] Imprimir");
        lblF12.setFocusable(false);
        lblEsc.setBounds(new Rectangle(650, 440, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
        //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        lblTotalMonto.setText(""); //("0");
        lblTotalMonto.setBounds(new Rectangle(675, 0, 65, 20));
        lblTotalMonto.setFont(new Font("SansSerif", 1, 11));
        lblTotalMonto.setForeground(Color.white);
        lblTotalMonto.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalMonto.setFocusable(false);
        //jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblEsc, null);


        //jContentPane.add(lblF12, null);
        jContentPane.add(lblF9, null);
        //jContentPane.add(lblF6, null);
        //jContentPane.add(lblF7, null);
        jContentPane.add(lblF1, null);
        tblRegistroVentas.setFocusable(false);
        jScrollPane1.getViewport().add(tblRegistroVentas, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlResultados.add(lblTotalMonto, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(btnEstado, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);

        pnlCriterioBusqueda.add(txtCliente, null);
        pnlCriterioBusqueda.add(txtTelefono, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);

        pnlCriterioBusqueda.add(btnCliente, null);
        pnlCriterioBusqueda.add(cmbEstado, null);
        pnlCriterioBusqueda.add(btnTelefono, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaRegistroVentas();
        initComboEstado();
        try {
            txtFechaIni.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA));
            txtFechaFin.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA));
        } catch (Exception ex) {
            log.info("Error al obtener la fecha de la base de datos" + ex.getMessage());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = format.format(Calendar.getInstance().getTime());
            txtFechaIni.setText(fecha);
            txtFechaFin.setText(fecha);
        }
        btnBuscar.doClick();

    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaRegistroVentas() {
        tableModelRegistroVentas =
                new FarmaTableModel(ConstantsReporte.columnsListaRegistroVentasDeliv, ConstantsReporte.defaultValuesListaRegistroVentasDeliv,
                                    0);
        FarmaUtility.initSimpleList(tblRegistroVentas, tableModelRegistroVentas,
                                    ConstantsReporte.columnsListaRegistroVentasDeliv);
    }

    private void initComboEstado() {

        String codigo[] = { "%", "P", "C", "N" };
        String valor[] = { "TODOS", "PROFORMADO", "COBRADO", "ANULADO" };
        FarmaLoadCVL.loadCVLfromArrays(cmbEstado, "EstadoVentaDelivery", codigo, valor, true);

    }

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void pasarFocoActionPerformedBtn(ActionEvent e, Object nextObject) {
        FarmaUtility.moveFocus(nextObject);

    }

    private void pasarFocoKeyPressedTxt(KeyEvent e, Object nextObject) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (nextObject == null) {
                btnBuscar.doClick();
            } else {
                FarmaUtility.moveFocus(nextObject);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {


        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void tblRegistroVentas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnBuscarActionPerformed(ActionEvent e) {

        //generarConsulta("TIPO_PROF_VENTA", txtCliente.getText().trim(), false);
        generarConsulta("NOMBRE_CLIENTE", txtCliente.getText().trim(), false);
        generarConsulta("TELEF_CLIENTE", txtTelefono.getText().trim(), false);
        generarConsulta("ESTADO_PEDIDO", FarmaLoadCVL.getCVLCode("EstadoVentaDelivery", cmbEstado.getSelectedIndex()),
                        false);
        FarmaUtility.moveFocus(txtFechaIni);
        busqueda(qWhere);
    }


    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblRegistroVentas.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblRegistroVentas);
        }
    }

    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRegistroVentas, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        }
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {

                log.debug(VariablesReporte.vCorrelativo + VariablesReporte.vCliente + VariablesReporte.vDireccion +
                          VariablesReporte.vRuc + VariablesReporte.vFecha + VariablesReporte.vHora +
                          VariablesReporte.vUsuario + VariablesReporte.vEstado);
                listadoDetalleVentas();
            }
        } /* else if (UtilityPtoVenta.verificaVK_F2(e)) {
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {
                txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
                txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
                String FechaInicio = txtFechaIni.getText().trim();
                String FechaFin = txtFechaFin.getText().trim();
                log.debug(FechaFin + FechaInicio);
                resumenVentas();
            }
        }*/
        else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (tableModelRegistroVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
            else {
                DlgFiltroReporteDelivery dlgFiltro = new DlgFiltroReporteDelivery(myParentFrame, "", true);
                dlgFiltro.setVisible(true);
                log.info(dlgFiltro.campoFiltro);
                log.info(dlgFiltro.valCampoFiltro);
                generarConsulta(dlgFiltro.campoFiltro, dlgFiltro.valCampoFiltro, false);
                btnBuscar.doClick();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (tableModelRegistroVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
            else
                muestraVentaOrdenar();
        } else if (UtilityPtoVenta.verificaVK_F12(e)) {
            if (tableModelRegistroVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de imprmir", txtFechaIni);
            else
                imprimir();

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void fechaKeyReleased(KeyEvent e) {
        FarmaUtility.dateComplete((JTextField)e.getSource(), e);
    }


    private void buscaRegistroVentas(String pFechaInicio, String pFechaFin, String pWhere) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaRegistroVentas(pWhere);
    }

    private void cargaRegistroVentas(String pWhere) {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);
            DBReportes.cargaListaRegistroVentasDelivery(tableModelRegistroVentas, VariablesReporte.vFechaInicio,
                                                        VariablesReporte.vFechaFin, pWhere);
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                // lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 7)));//6
                lblRegistros.setText("" + tblRegistroVentas.getRowCount());
                return;
            } else {
                FarmaUtility.moveFocus(tblRegistroVentas);
            }
            FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 4, FarmaConstants.ORDEN_ASCENDENTE); //4
            // lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 7)));//6
            lblRegistros.setText("" + tblRegistroVentas.getRowCount());
            FarmaUtility.moveFocusJTable(tblRegistroVentas);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void listadoDetalleVentas() {
        int rowSelec = tblRegistroVentas.getSelectedRow();
        String vCorrelativo = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 1);
        String vCliente = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 18);
        String vRuc = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 13);
        String vFecha = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 4);
        String vHora = vFecha.substring(10).trim();
        String vUsuario = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 11);
        String vEstado = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 9);
        VariablesReporte.vNComprobante =
                FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 3);
        VariablesReporte.vDireccion = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 14);

        VariablesDelivery.vNombreCliente =
                FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 5);
        VariablesDelivery.vDireccion =
                FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 19);
        VariablesDelivery.vReferencia =
                FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 15);

        String vNomMot = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 16);
        String vObserv = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, rowSelec, 17);

        ArrayList<ArrayList<String>> formasPagoDelivery = new ArrayList<>();
        try {
            log.debug(VariablesReporte.vCorrelativo);
            DBReportes.cargaListaFormasPagoDel(formasPagoDelivery, VariablesReporte.vCorrelativo);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar las Formas de Pago:\n " + sql.getMessage(), null);
            cerrarVentana(false);
        }

        UtilityDelivery.resumenPedidoVentas(myParentFrame, vCorrelativo, vCliente, vRuc, vFecha, vHora, vUsuario,
                                            vEstado, formasPagoDelivery, "", vNomMot, true, vObserv);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }

    private void resumenVentas() {
        DlgResumenVenta dlgResumenVenta = new DlgResumenVenta(myParentFrame, "", true);
        dlgResumenVenta.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    /**
     * Fecha Modificación: 05/01/2007
     * Usuario: Luis Reque
     * Descripción: Se realiza el ordenamiento por Tipo y Nro. de Comprobante a la vez, no por separados.
     * */
    private void muestraVentaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        //String [] IND_DESCRIP_CAMPO = {"Correlativo", "Tipo", "No.Comprob", "Fecha","Cliente", "Monto"};
        //String [] IND_CAMPO = {"0","1","2","11","4","5"};

        String[] IND_DESCRIP_CAMPO = { "Correlativo", "Tipo y No.Comprob", "Fecha", "Cliente", "Monto" };
        String[] IND_CAMPO = { "1", "13", "12", "5", "7" }; //"0", "12", "11", "4", "6"

        log.debug("Campo " + IND_DESCRIP_CAMPO[1]);
        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEREGISTROVENTAS;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblRegistroVentas.repaint();
        }

    }

    private void exportarDatos() {

        File lfFile = new File("C:\\");
        JFileChooser filechooser = new JFileChooser(lfFile);
        filechooser.setDialogTitle("Seleccione el directorio destino");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (filechooser.showSaveDialog(this.myParentFrame) != JFileChooser.APPROVE_OPTION)
            return;
        File fileChoosen = filechooser.getSelectedFile();
        String ruta = fileChoosen.getAbsolutePath();

        FarmaUtility.saveFileRuta(ruta, ConstantsReporte.columnsListaRegistroVentas, tblRegistroVentas,
                                  new int[] { 20, 10, 20, 20, 40, 25, 60, 20, 0, 0, 0, 0 });

        FarmaUtility.showMessage(this, "Los datos se exportaron correctamente", txtFechaIni);

    }

    private void imprimir() {
        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;

        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtFechaIni);
            return;
        }

        try {

            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE  DE REGISTRO DE VENTAS", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("========================================================================================================================",
                             true);
            vPrint.printBold("NroPedido   Tipo  NroComprobante  Fecha                Cliente          Estado             Monto   Ruc       ",
                             true);
            vPrint.printLine("=========================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblRegistroVentas.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 0), //0
                            11) + " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 2), 5) + //1
                        " " + FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 3), //2
                            15) + " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 4), //3
                            20) + " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 5), //4
                            16) + " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 6), //5
                            12) + " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 7), //6
                            13) + " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 8), 12), //7
                        true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("==========================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblRegistroVentas.getRowCount(),
                                                                                      ",##0"), 11) + //10
                    FarmaPRNUtility.alinearDerecha(lblTotalMonto.getText(), 66) + FarmaPRNUtility.llenarBlancos(11),
                    true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (SQLException ex) {
            log.error("", ex);
            //showMessage(this,"Ocurrió un error al imprimir : \n"+ex.getMessage(),null);			
        }
    }

    private void busqueda(String pWhere) {
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
                    buscaRegistroVentas(FechaInicio, FechaFin, pWhere);
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);

        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void generarConsulta(String campo, String valCampo, boolean quitar) {
        boolean agrego = false;
        if (!quitar) {
            mapCondicionales.put(campo, valCampo);

            Iterator listKey = mapCondicionales.keySet().iterator();
            qWhere = "";
            String tipoDatoCampo = "";
            while (listKey.hasNext()) {
                agrego = false;
                tipoDatoCampo = "";
                String key = (String)listKey.next();
                String valor = mapCondicionales.get(key);
                if (valor.trim().length() > 0) {
                    try {
                        tipoDatoCampo = DBReportes.obtenerTipoDatoColumn("V_REPOR_VENTA_DELIVERY", key);
                    } catch (Exception ex) {
                        log.info("generaraConsulta: " + ex.getMessage());
                        FarmaUtility.showMessage(this, "Error al obtener el tipo de dato : \n" +
                                ex.getMessage(), txtFechaIni);
                    }

                    if (!tipoDatoCampo.equals("N")) {
                        if (qWhere.trim().length() > 0) {
                            qWhere = qWhere + " AND ";
                        }
                        if (tipoDatoCampo.equalsIgnoreCase("DATE") || tipoDatoCampo.equalsIgnoreCase("TIMESTAMP")) {
                            qWhere =
                                    qWhere + "TO_DATE('" + key + "' || ' 00:00:00', 'dd/MM/yyyy HH24:MI:SS')=" + "TO_DATE('" +
                                    valor + "' || ' 00:00:00', 'dd/MM/yyyy HH24:MI:SS')";
                            agrego = true;
                        }
                        if (tipoDatoCampo.equalsIgnoreCase("NUMBER") || tipoDatoCampo.equalsIgnoreCase("INTEGER")) {
                            qWhere = qWhere + key + " = " + valor;
                            agrego = true;
                        }
                        if (!agrego) {
                            qWhere = qWhere + key + " LIKE '%" + valor + "%'";
                        }

                    } else {

                    }
                }
            }
        }
    }

    private void btnEstado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbEstado);
    }

    private void cmbEstado_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pasarFocoKeyPressedTxt(e, null);
        }
    }
}
