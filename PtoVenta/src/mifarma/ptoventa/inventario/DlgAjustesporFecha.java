package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgAjustesporFecha extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgAjustesporFecha.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    private JPanelHeader pnlHeader1 = new JPanelHeader();


    private JTextFieldSanSerif txtFechaInicio = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();

    private JButton btnBuscar = new JButton();

    private JButtonLabel btnRangoFechas = new JButtonLabel();

    private JPanelTitle pnllTitle1 = new JPanelTitle();

    private JButtonLabel btnRelacionMovimiento = new JButtonLabel();

    private JScrollPane scrListaProductos = new JScrollPane();

    private JTable tblListaMovs = new JTable();


    private JLabelFunction lblEsc = new JLabelFunction();


    private JLabelWhite lblUsuario = new JLabelWhite();
    private JButtonLabel lbllabT = new JButtonLabel();
    private JButtonLabel lbllab = new JButtonLabel();
    private JButtonLabel lblGlosaT = new JButtonLabel();
    private JButtonLabel lblGlosa = new JButtonLabel();
    private JButtonLabel lblGlosaT1 = new JButtonLabel();
    private JLabelFunction lblEsc1 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgAjustesporFecha() {
        this(null, "", false);
    }

    public DlgAjustesporFecha(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
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
        this.setSize(new Dimension(791, 506));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ajustes de Kardex");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 765, 95));
        txtFechaInicio.setBounds(new Rectangle(125, 10, 85, 20));
        txtFechaInicio.setDocument(new FarmaLengthText(10));
        txtFechaInicio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaInicio_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaInicio_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(225, 10, 85, 20));
        txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(330, 10, 90, 20));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        btnRangoFechas.setText("Rango de Fechas:");
        btnRangoFechas.setBounds(new Rectangle(10, 10, 110, 20));
        btnRangoFechas.setMnemonic('F');
        btnRangoFechas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFechas_actionPerformed(e);
            }
        });
        pnllTitle1.setBounds(new Rectangle(10, 115, 765, 25));
        btnRelacionMovimiento.setText("Relación de Movimientos del Producto");
        btnRelacionMovimiento.setBounds(new Rectangle(15, 5, 215, 15));
        btnRelacionMovimiento.setMnemonic('R');
        btnRelacionMovimiento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionMovimiento_actionPerformed(e);
            }
        });
        scrListaProductos.setBounds(new Rectangle(10, 140, 765, 300));
        tblListaMovs.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblListaMovs_keyReleased(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(685, 445, 85, 20));
        lblUsuario.setBounds(new Rectangle(430, 45, 115, 20));
        lbllabT.setText("Laboratorio");
        lbllabT.setBounds(new Rectangle(10, 45, 80, 20));
        lbllabT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFechas_actionPerformed(e);
            }
        });
        lbllab.setBounds(new Rectangle(85, 45, 265, 20));
        lbllab.setMnemonic('F');
        lbllab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFechas_actionPerformed(e);
            }
        });
        lblGlosaT.setText("Glosa");
        lblGlosaT.setBounds(new Rectangle(10, 70, 45, 20));
        lblGlosaT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFechas_actionPerformed(e);
            }
        });
        lblGlosa.setBounds(new Rectangle(65, 70, 685, 20));
        lblGlosa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFechas_actionPerformed(e);
            }
        });
        lblGlosaT1.setText("Usuario");
        lblGlosaT1.setBounds(new Rectangle(375, 45, 50, 20));
        lblGlosaT1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFechas_actionPerformed(e);
            }
        });
        lblEsc1.setText("[ F12 ] Imprimir");
        lblEsc1.setBounds(new Rectangle(525, 445, 105, 20));
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setBounds(new Rectangle(345, 445, 170, 20));
        lblF8.setVisible(false);
        pnlHeader1.add(lblGlosaT1, null);
        pnlHeader1.add(lblGlosa, null);
        pnlHeader1.add(lblGlosaT, null);
        pnlHeader1.add(lbllab, null);
        pnlHeader1.add(lbllabT, null);
        pnlHeader1.add(lblUsuario, null);
        pnlHeader1.add(btnRangoFechas, null);
        pnlHeader1.add(btnBuscar, null);
        pnlHeader1.add(txtFechaFin, null);
        pnlHeader1.add(txtFechaInicio, null);
        jPanelWhite1.add(lblF8, null);
        jPanelWhite1.add(lblEsc1, null);
        jPanelWhite1.add(lblEsc, null);
        scrListaProductos.getViewport().add(tblListaMovs, null);
        jPanelWhite1.add(scrListaProductos, null);
        pnllTitle1.add(btnRelacionMovimiento, null);
        jPanelWhite1.add(pnllTitle1, null);
        jPanelWhite1.add(pnlHeader1, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && FarmaVariables.vEconoFar_Matriz)
            lblF8.setVisible(true);
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaAjustesporFecha, ConstantsInventario.defaultListaAjustesporFecha,
                                    0);
        FarmaUtility.initSimpleList(tblListaMovs, tableModel, ConstantsInventario.columnsListaAjustesporFecha);
        cargarFechas();

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void btnRangoFechas_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaInicio);
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();

        } else {
            chkKeyPressed(e);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaInicio);
    }

    private void txtFechaInicio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFechaFin);
        } else {
            chkKeyPressed(e);
        }
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (datosValidados()) {
            cargarFechas();
            cargaListaAjustes();
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtFechaInicio_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaInicio, e);
        FarmaGridUtils.buscarDescripcion(e, tblListaMovs, txtFechaInicio, 0);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            if (tblListaMovs.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "No existe informacion a imprimir", txtFechaInicio);
            else
                imprimir();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (tblListaMovs.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Debe listar Movimientos de Kardex para poder filtrar", txtFechaInicio);
            } else {
                listaFiltroKardex();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            cargaListaAjustes();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (lblF8.isVisible()) {
                int[] ancho = { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
                FarmaUtility.saveFile(myParentFrame, ConstantsInventario.columnsListaAjustesporFecha, tblListaMovs,
                                      ancho);
            }
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

    private boolean datosValidados() {
        if (!FarmaUtility.validaFecha(txtFechaInicio.getText(), "dd/MM/yyyy") ||
            txtFechaInicio.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha Inicial correctamente", txtFechaInicio);
            return false;
        }
        if (!FarmaUtility.validaFecha(txtFechaFin.getText(), "dd/MM/yyyy") || txtFechaFin.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha Final correctamente", txtFechaFin);
            return false;
        }
        Date fecIni = FarmaUtility.getStringToDate(txtFechaInicio.getText().trim(), "dd/MM/yyyy");
        Date fecFin = FarmaUtility.getStringToDate(txtFechaFin.getText().trim(), "dd/MM/yyyy");

        if (fecIni.after(fecFin)) {
            FarmaUtility.showMessage(this, "La Fecha Inicial no puede ser posterior a la Fecha Final", txtFechaInicio);
            return false;
        }
        return true;
    }

    private void cargaListaAjustes() {
        try {
            DBInventario.listaAjusteporFecha(tableModel);
            if (tblListaMovs.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaMovs, tableModel, 8, FarmaConstants.ORDEN_ASCENDENTE);
                mostrarDatos();
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de movimientos : \n" +
                    sql.getMessage(), txtFechaInicio);
        }
        if (tblListaMovs.getRowCount() == 0) {
            FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtFechaInicio);
            return;
        }
        FarmaUtility.moveFocusJTable(tblListaMovs);

    }

    private void cargarFechas() {
        VariablesInventario.vFecIniMovKardex = txtFechaInicio.getText().trim();
        VariablesInventario.vFecFinMovKardex = txtFechaFin.getText().trim();
    }

    private void iniciarValoresFecha() {
        String fecActual = "";
        try {
            fecActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener la fecha del sistema : \n" +
                    sql.getMessage(), txtFechaInicio);
            Date fec = new Date();
            fecActual = fec.toString();
        }
        txtFechaInicio.setText(fecActual);
        txtFechaFin.setText(fecActual);
    }

    private void mostrarDatos() {
        String laboratorio, glosa, usuario;
        int total = tblListaMovs.getRowCount();
        if (total > 0) {
            laboratorio = ((String)tblListaMovs.getValueAt(tblListaMovs.getSelectedRow(), 9)).trim();
            lbllab.setText(laboratorio);

            usuario = ((String)tblListaMovs.getValueAt(tblListaMovs.getSelectedRow(), 10)).trim();
            lblUsuario.setText(usuario);

            glosa = ((String)tblListaMovs.getValueAt(tblListaMovs.getSelectedRow(), 11)).trim();
            lblGlosa.setText(glosa);
        } else {
            laboratorio = "";
            glosa = "";
            usuario = "";
            lbllab.setText(laboratorio);
            lblUsuario.setText(usuario);
            lblGlosa.setText(glosa);
        }
    }

    private void btnRelacionMovimiento_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaMovs);
    }

    private void listaFiltroKardex() {
        DlgFiltroDescripcionMovKardex dlgFiltroDescripcionMovKardex =
            new DlgFiltroDescripcionMovKardex(myParentFrame, "", true);
        dlgFiltroDescripcionMovKardex.setVisible(true);
        if (FarmaVariables.vAceptar) {
            try {
                DBInventario.listaFiltroMvsKardex(tableModel);
                if (tblListaMovs.getRowCount() <= 0) {
                    FarmaUtility.showMessage(this, "El filtro no arrojó resultados.", txtFechaInicio);
                }
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al obtener la lista de filtrado del kardex : \n" +
                        sql.getMessage(), txtFechaInicio);
            }
        }
    }

    private void tblListaMovs_keyReleased(KeyEvent e) {
        mostrarDatos();
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtFechaInicio);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE DE AJUSTES POR FECHA", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesInventario.vFecIniMovKardex, true);
            vPrint.printBold("Fecha Final: " + VariablesInventario.vFecFinMovKardex, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("===========================================================================================================================",
                             true);
            vPrint.printBold("Codigo  Descripcion                              Fecha              Motivo               StkAnt Mov    StkAnt Frac Usuario",
                             true);
            vPrint.printLine("===========================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblListaMovs.getRowCount(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 0), 6) +
                                      "  " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 1), 40) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 2), 18) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 3), 20) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 4), 6) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 5), 6) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 6), 6) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 7), 4) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 10), 20),
                                      true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("===========================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " + FarmaUtility.formatNumber(tblListaMovs.getRowCount(), ",##0"),
                             false);
            //vPrint.printBold(FarmaPRNUtility.llenarBlancos(40)+ lblTotal, true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sql.getMessage(), null);
        }
    }
}
