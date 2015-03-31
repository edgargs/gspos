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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRegistroVentasConvenio extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroVentasConvenio.class);

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
    private JButtonLabel btnConvenio = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblRegistros = new JLabel();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JTextFieldSanSerif txtConvenio = new JTextFieldSanSerif();
    private ArrayList listConvenios = new ArrayList();

    public DlgRegistroVentasConvenio() {
        this(null, "", false);
    }

    public DlgRegistroVentasConvenio(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(736, 472));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Registro de Ventas");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 710, 70));
        pnlCriterioBusqueda.setFocusable(false);
        pnlTitulo.setBounds(new Rectangle(10, 95, 710, 20));
        pnlTitulo.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 395, 710, 20));
        pnlResultados.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 115, 710, 270));
        jScrollPane1.setFocusable(false);
        tblRegistroVentas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRegistroVentas_keyPressed(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(80, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pasarFoco(e, txtFechaIni);
            }
        });

        btnConvenio.setText("Convenio :");
        btnConvenio.setBounds(new Rectangle(80, 30, 60, 20));
        btnConvenio.setMnemonic('c');
        btnConvenio.setFocusable(false);
        btnConvenio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pasarFoco(e, txtConvenio);
            }
        });

        txtConvenio.setBounds(new Rectangle(150, 30, 400, 20));
        txtConvenio.setFont(new Font("SansSerif", 1, 11));
        txtConvenio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pasarFoco(e, null);
            }
        });

        txtFechaIni.setBounds(new Rectangle(150, 5, 101, 19));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pasarFoco(e, txtFechaFin);
            }

            public void keyReleased(KeyEvent e) {
                completarFecha(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(275, 5, 101, 19));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pasarFoco(e, txtConvenio);
            }

            public void keyReleased(KeyEvent e) {
                completarFecha(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(570, 30, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
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
        lblF12.setBounds(new Rectangle(490, 415, 100, 20));
        lblF12.setText("[ F12 ] Imprimir");
        lblF12.setFocusable(false);
        lblEsc.setBounds(new Rectangle(610, 415, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF12, null);
        jScrollPane1.getViewport().add(tblRegistroVentas, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        tblRegistroVentas.setFocusable(false);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        pnlCriterioBusqueda.add(btnConvenio, null);
        pnlCriterioBusqueda.add(txtConvenio, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        FarmaColumnData columnsListaRegistroVentas[] =
        { new FarmaColumnData("Convenio", 360, JLabel.LEFT), new FarmaColumnData("T", 80, JLabel.CENTER),
          new FarmaColumnData("No Comprobante", 100, JLabel.CENTER),
          new FarmaColumnData("Fecha", 120, JLabel.CENTER) };

        Object[] defaultValuesListaRegistroVentas = { " ", " ", " ", " " };

        tableModelRegistroVentas =
                new FarmaTableModel(columnsListaRegistroVentas, defaultValuesListaRegistroVentas, 0);
        FarmaUtility.initSimpleList(tblRegistroVentas, tableModelRegistroVentas, columnsListaRegistroVentas);
    };

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void pasarFoco(ActionEvent e, Object nextObject) {
        FarmaUtility.moveFocus(nextObject);
    }

    private void pasarFoco(KeyEvent e, Object nextObject) {
        if (nextObject != null) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                FarmaUtility.moveFocus(nextObject);
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                cerrarVentana(false);
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                cargarBusquedaConvenio();
            }
        }
        chkKeyPressed(e);
    }

    private void tblRegistroVentas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
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
        if (UtilityPtoVenta.verificaVK_F12(e)) {
            if (tblRegistroVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de imprmir", txtFechaIni);
            else
                imprimir();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void completarFecha(KeyEvent e) {
        FarmaUtility.dateComplete((JTextFieldSanSerif)e.getSource(), e);
    }

    private void buscaRegistroVentas(String pFechaInicio, String pFechaFin, String codConvs) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaRegistroVentas(codConvs);
    }

    private void cargaRegistroVentas(String codConvs) {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);
            DBReportes.cargaListaRegistroVentasConvenio(tableModelRegistroVentas, VariablesReporte.vFechaInicio,
                                                        VariablesReporte.vFechaFin, codConvs);
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                lblRegistros.setText("" + tblRegistroVentas.getRowCount());
                return;
            } else {
                FarmaUtility.moveFocus(tblRegistroVentas);
            }

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

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    private void busqueda() {
        if (validarCampos()) {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (listConvenios.size() > 0) {
                String codConv = "";
                for (int i = 0; i < listConvenios.size(); i++) {
                    if (i == 0) {
                        codConv = Integer.parseInt((String)listConvenios.get(i)) + "";
                    } else {
                        codConv = codConv + "@" + Integer.parseInt((String)listConvenios.get(i));
                    }
                }
                if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                    char primerkeyCharFI = FechaInicio.charAt(0);
                    char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                    char primerkeyCharFF = FechaFin.charAt(0);
                    char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                    if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                        !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                        buscaRegistroVentas(FechaInicio, FechaFin, codConv);
                    } else
                        FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
                } else
                    FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);
            } else {
                FarmaUtility.showMessage(this, "No ha seleccionado convenios", txtConvenio);
            }

        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cargarBusquedaConvenio() {
        DlgBusquedaConveniosBTLMF bus = new DlgBusquedaConveniosBTLMF(myParentFrame, "", true);
        bus.setVisible(true);
        listConvenios = new ArrayList();
        listConvenios = bus.getCodConvSelec();
        if (listConvenios.size() > 0) {
            txtConvenio.setText(listConvenios.size() + " seleccionados");
            log.info("tama --> " + listConvenios.size());
            btnBuscar.doClick();
        }
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
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE  DE ENVIO DE DOCUMENTOS CONVENIO", true);
            vPrint.printBold("LOCAL DE ORIGEN: " + FarmaVariables.vDescLocal, true);
            vPrint.printBold("RANGO DE FECHAS: " + VariablesReporte.vFechaInicio + " AL " + VariablesReporte.vFechaFin,
                             true);
            vPrint.printBold("CANTIDAD DE ATENCIONES: " + tblRegistroVentas.getRowCount(), true);

            vPrint.printLine("========================================================================================================================",
                             true);
            /*vPrint.printBold("NroPedido   Tipo  NroComprobante  Fecha                Cliente          Estado             Monto   Ruc       ",
                             true);*/
            vPrint.printBold(FarmaPRNUtility.alinearIzquierda("CONVENIO", 64) + " " +
                             FarmaPRNUtility.alinearIzquierda("TD", 5) + " " +
                             FarmaPRNUtility.alinearIzquierda("DOCUMENTO", 15) + " " +
                             FarmaPRNUtility.alinearIzquierda("FECHA DOC.", 20), true);
            vPrint.printLine("=========================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblRegistroVentas.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 0),
                                                                       64) + " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 1), 5) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 2),
                                                                       15) + " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRegistroVentas.getValueAt(i, 3), 20),
                                      true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("==========================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblRegistroVentas.getRowCount(),
                                                                                      ",##0"), 10) +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (SQLException ex) {
            log.error("", ex);
            //showMessage(this,"Ocurrió un error al imprimir : \n"+ex.getMessage(),null);
        }
    }
}
