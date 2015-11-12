package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
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

import java.io.File;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProductosABC.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      17.07.2005   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgProductosABC extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgProductosABC.class);

    private FarmaTableModel tableModel;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JScrollPane scrProductos = new JScrollPane();
    private JTable tblProductos = new JTable();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabelFunction lblFiltrarProds = new JLabelFunction();
    private JLabelFunction lblQuitarFiltro = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblQuitarFiltro1 = new JLabelFunction();
    private JPanelTitle pnlTitulo1 = new JPanelTitle();
    private JButtonLabel lblMonto = new JButtonLabel();
    private JButtonLabel lblMonto1 = new JButtonLabel();
    private JPanelHeader pnlCriterioBusqueda1 = new JPanelHeader();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JLabelWhite lblItems_T = new JLabelWhite();
    private JLabelWhite lblItems = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgProductosABC() {
        this(null, "", false);
    }

    public DlgProductosABC(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(788, 439));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Productos ABC");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitulo.setBounds(new Rectangle(10, 55, 770, 20));
        scrProductos.setBounds(new Rectangle(10, 75, 770, 270));
        tblProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblProductos_keyPressed(e);
            }
        });
        btnListado.setText("Listado de Productos");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        lblFiltrarProds.setBounds(new Rectangle(145, 380, 165, 20));
        lblFiltrarProds.setText("[F6] Filtrar por Laboratorio");
        lblQuitarFiltro.setBounds(new Rectangle(320, 380, 105, 20));
        lblQuitarFiltro.setText("[F7] Quitar Filtro");
        lblSalir.setBounds(new Rectangle(675, 380, 85, 20));
        lblSalir.setText("[ ESC ] Cerrar");
        lblF4.setBounds(new Rectangle(10, 380, 125, 20));
        lblF4.setText("[F4] Filtrar por Tipo");
        lblQuitarFiltro1.setBounds(new Rectangle(440, 380, 85, 20));
        lblQuitarFiltro1.setText("[F8] Ordenar");
        pnlTitulo1.setBounds(new Rectangle(10, 345, 770, 20));
        lblMonto.setText("0");
        lblMonto.setBounds(new Rectangle(640, 0, 90, 20));
        lblMonto.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMonto1.setText("Total Monto S/.");
        lblMonto1.setBounds(new Rectangle(545, 0, 90, 20));
        pnlCriterioBusqueda1.setBounds(new Rectangle(10, 10, 770, 30));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(495, 5, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(365, 5, 101, 19));
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(240, 5, 101, 19));
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(170, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        lblItems_T.setText("Items:");
        lblItems_T.setBounds(new Rectangle(600, 0, 55, 20));
        lblItems.setText("0");
        lblItems.setBounds(new Rectangle(660, 0, 85, 20));
        lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlCriterioBusqueda1.add(btnBuscar, null);
        pnlCriterioBusqueda1.add(txtFechaFin, null);
        pnlCriterioBusqueda1.add(txtFechaIni, null);
        pnlCriterioBusqueda1.add(btnPeriodo, null);
        pnlTitulo1.add(lblMonto, null);
        pnlTitulo1.add(lblMonto1, null);
        jContentPane.add(pnlCriterioBusqueda1, null);
        jContentPane.add(pnlTitulo1, null);
        jContentPane.add(lblQuitarFiltro1, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblQuitarFiltro, null);
        jContentPane.add(lblFiltrarProds, null);
        scrProductos.getViewport().add(tblProductos, null);
        jContentPane.add(scrProductos, null);
        pnlTitulo.add(lblItems, null);
        pnlTitulo.add(lblItems_T, null);
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setBounds(new Rectangle(10, 10, 790, 439));
        txtFechaIni.setLengthText(10);
        txtFechaFin.setLengthText(10);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaVentas();
        initCabecera();
        btnBuscar.doClick();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaVentas() {
        tableModel =
                new FarmaTableModel(ConstantsReporte.columnsListaProdABC, ConstantsReporte.defaultValuesListaProdABC,
                                    0);
        FarmaUtility.initSimpleList(tblProductos, tableModel, ConstantsReporte.columnsListaProdABC);
    }

    private void initCabecera() {
        try {
            String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String fechaSistemaAtrasada = FarmaSearch.getFechaHoraAtrasadaBD(FarmaConstants.FORMATO_FECHA, 30);
            txtFechaIni.setText(fechaSistemaAtrasada);
            txtFechaFin.setText(fechaSistema);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, sql.getMessage(), txtFechaIni);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        VariablesReporte.vCampoFiltro = "";
        VariablesReporte.vCampoFiltroLab = "";
        VariablesReporte.vInd_Filtro = FarmaConstants.INDICADOR_S;
        busqueda();
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(tblProductos);
            btnBuscar.doClick();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblProductos.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblProductos);
        }
    }

    private void tblProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F4) {
            cargaListaFiltroProductoABC();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            cargaListaFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            //btnBuscar.doClick();
            funcionF7();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            ordenarFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            /*if(tblProductos.getRowCount()>0)
        exportarDatos(); */
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cargaListadoFiltro() {
        try {
            tableModel.clearTable();
            if (VariablesReporte.vCampoFiltro.equals(""))
                VariablesReporte.vCampoFiltro = "%";
            if (VariablesReporte.vCampoFiltroLab.equals(""))
                VariablesReporte.vCampoFiltroLab = "%";

            DBReportes.cargaListaProductosABCFiltro(tableModel, VariablesReporte.vCampoFiltroLab,
                                                    VariablesReporte.vInd_Filtro, VariablesReporte.vCampoFiltro,
                                                    VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin);

            lblMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblProductos, 6)));
            lblItems.setText(tblProductos.getRowCount() + "");

            if (tblProductos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblProductos, tableModel, 6, FarmaConstants.ORDEN_DESCENDENTE);
                VariablesReporte.vInd_Filtro = FarmaConstants.INDICADOR_N;
            } else {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar la lista del productos ABC : \n" +
                    sql.getMessage(), tblProductos);
        }
    }

    private void cargaListaFiltro() {
        if (tblProductos.getRowCount() > 0) {
            VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LABORATORIO;
            DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
            dlgListaMaestros.setVisible(true);
            if (FarmaVariables.vAceptar) {
                VariablesReporte.vCampoFiltroLab = VariablesPtoVenta.vCodMaestro;
                cargaListadoFiltro();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (txtFechaIni.getText().trim().equals("")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha de Inicio.", txtFechaIni);
        } else if (txtFechaFin.getText().trim().equals("")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha de Fin.", txtFechaFin);
        } else if (!FarmaUtility.validaFecha(txtFechaIni.getText(), "dd/MM/yyyy")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha.", txtFechaIni);
        } else if (!FarmaUtility.validaFecha(txtFechaFin.getText(), "dd/MM/yyyy")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha.", txtFechaFin);
        } else if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
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

        FarmaUtility.saveFileRuta(ruta, ConstantsReporte.columnsListaVentasHora, tblProductos,
                                  new int[] { 20, 15, 15, 15, 15, 15 });
        FarmaUtility.showMessage(this, "Los datos se exportaron correctamente", txtFechaIni);
    }

    private void ordenarFiltro() {
        if (tblProductos.getRowCount() > 0) {
            DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
            // los datos de abajo son variables y constantes y tienen q crearlos respectivamente
            VariablesReporte.vNombreInHashtable = ConstantsReporte.vNombreInHashtableABC;
            FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable,
                                           ConstantsReporte.vCodABC, ConstantsReporte.vDescCampoABC, true);
            dlgOrdenar.setVisible(true);
            if (FarmaVariables.vAceptar) {
                FarmaUtility.ordenar(tblProductos, tableModel, VariablesReporte.vCampo, VariablesReporte.vOrden);
                tblProductos.repaint();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private void funcionF7() {
        VariablesReporte.vCampoFiltro = "";
        VariablesReporte.vCampoFiltroLab = "";
        cargaListadoFiltro();
    }

    private void cargaListaFiltroProductoABC() {
        if (tblProductos.getRowCount() > 0) {
            DlgFiltroDetalleVentas dlgFiltroDetalleVentas =
                new DlgFiltroDetalleVentas(myParentFrame, "", true, ConstantsReporte.vProductosABC,
                                           ConstantsReporte.vCodTipo, ConstantsReporte.vDescTipo);
            dlgFiltroDetalleVentas.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cargaListadoFiltro();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private void busqueda() {
        if (validarCampos()) {
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    buscaProductosABC(FechaInicio, FechaFin);

                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);
        }
    }

    private void buscaProductosABC(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaListadoFiltro();
    }

}
