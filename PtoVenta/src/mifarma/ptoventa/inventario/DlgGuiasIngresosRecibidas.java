package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.precioCompetencia.DlgProductosPorCotizar;
import mifarma.ptoventa.inventario.precioCompetencia.DlgResumenCotizaciones;
import mifarma.ptoventa.inventario.precioCompetencia.reference.VariablesPrecioCompetencia;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reportes.DlgFiltroDetalleVentas;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgGuiasIngresosRecibidas.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.02.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgGuiasIngresosRecibidas extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgGuiasIngresosRecibidas.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private String fechaIni;
    private String fechaFin;
    private String filtro;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaTransferencias = new JScrollPane();
    private JTable tblListaTransferencias = new JTable();
    private JButtonLabel btnRelacionTransferencias = new JButtonLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JButtonLabel btnBuscarDesc = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JLabelFunction lblFiltrarProds = new JLabelFunction();
    private JLabelFunction lblQuitarFiltro = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JButton jButton1 = new JButton();
    private JButtonFunction jButtonFunction1 = new JButtonFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JButton jButton2 = new JButton();
    private JLabelFunction lblCotizarProductos = new JLabelFunction();
    private JLabelFunction lblVerProductosCotizar = new JLabelFunction();    

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgGuiasIngresosRecibidas() {
        this(null, "", false);
    }

    public DlgGuiasIngresosRecibidas(Frame parent, String title, boolean modal) {
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

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(735, 484));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Guias de Ingreso");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 105, 700, 25));
        scrListaTransferencias.setBounds(new Rectangle(10, 130, 700, 260));
        btnRelacionTransferencias.setText("Relacion de Guias de Ingreso Recibidas");
        btnRelacionTransferencias.setBounds(new Rectangle(5, 5, 265, 15));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(610, 430, 90, 20));
        lblF2.setText("[ F2 ] Ver Detalle Guia Ingreso");
        lblF2.setBounds(new Rectangle(195, 400, 185, 20));
        lblF1.setText("[ F1 ] Nueva Guia de Ingreso");
        lblF1.setBounds(new Rectangle(10, 400, 175, 20));
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 700, 85));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(475, 15, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(345, 15, 101, 19));
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaIni.setBounds(new Rectangle(220, 15, 101, 19));
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaIni.setDocument(new FarmaLengthText(10));
        btnRandoFec.setText("Rango de Fechas");
        btnRandoFec.setBounds(new Rectangle(110, 15, 100, 20));
        btnRandoFec.setMnemonic('f');
        btnRandoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRandoFec_actionPerformed(e);
            }
        });
        btnBuscarDesc.setText("Buscar :");
        btnBuscarDesc.setBounds(new Rectangle(110, 50, 55, 15));
        btnBuscarDesc.setMnemonic('u');
        btnBuscarDesc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscarDesc_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(165, 50, 295, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        txtBuscar.setDocument(new FarmaLengthText(10));
        lblFiltrarProds.setBounds(new Rectangle(390, 400, 100, 20));
        lblFiltrarProds.setText("[F6] Filtrar");
        lblQuitarFiltro.setBounds(new Rectangle(500, 400, 105, 20));
        lblQuitarFiltro.setText("[F7] Quitar Filtro");
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setVisible(false);
        lblF8.setBounds(new Rectangle(400, 400, 170, 20));
        jButton1.setText("jButton1");
        jButtonFunction1.setText("jButtonFunction1");
        jButtonLabel1.setText("jButtonLabel1");
        jButton2.setText("jButton2");
        lblCotizarProductos.setBounds(new Rectangle(270, 430, 140, 20));
        lblCotizarProductos.setText(" [F9] Cotizar Productos");
        lblVerProductosCotizar.setBounds(new Rectangle(415, 430, 190, 20));
        lblVerProductosCotizar.setText("[ F10 ] Ver Productos por Cotizar");        
        pnlCriterioBusqueda.add(txtBuscar, null);
        pnlCriterioBusqueda.add(btnBuscarDesc, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnRandoFec, null);
        jContentPane.add(lblVerProductosCotizar, null);
        jContentPane.add(lblCotizarProductos, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblQuitarFiltro, null);
        jContentPane.add(lblFiltrarProds, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        scrListaTransferencias.getViewport().add(tblListaTransferencias, null);
        jContentPane.add(scrListaTransferencias, null);
        pnlTitle1.add(btnRelacionTransferencias, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && FarmaVariables.vEconoFar_Matriz)
            lblF8.setVisible(true);
        
        //Celso. L. 14012014
        lblCotizarProductos.setVisible(true);
        lblVerProductosCotizar.setVisible(true);
                             
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaTransferencias, ConstantsInventario.defaultValuesListaTransferencias,
                                    0);
        FarmaUtility.initSimpleList(tblListaTransferencias, tableModel,
                                    ConstantsInventario.columnsListaTransferencias);
    }

    private void cargaListaTransferencias() {
        try {
            DBInventario.cargaListaGuiaIngresos(tableModel, fechaIni, fechaFin, filtro);
            FarmaUtility.ordenar(tblListaTransferencias, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar las transferencias : \n" +
                    sql.getMessage(), btnRelacionTransferencias);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String fechaAtrasada = FarmaSearch.getFechaHoraAtrasadaBD(FarmaConstants.FORMATO_FECHA, 30);
            txtFechaIni.setText(fechaAtrasada);
            txtFechaFin.setText(fechaActual);
            btnBuscar.doClick();
        } catch (SQLException sql) {
            ;
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.",
                                 btnRelacionTransferencias);
    }

    private void btnRandoFec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else
            chkKeyPressed(e);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        } else
            chkKeyPressed(e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaTransferencias, txtBuscar, 2);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (validarCampos()) {
            fechaIni = txtFechaIni.getText().trim();
            fechaFin = txtFechaFin.getText().trim();
            filtro = "%";
            cargaListaTransferencias();
            FarmaUtility.moveFocus(txtBuscar);
        }
    }

    private void btnBuscarDesc_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            //
            //txtBuscar.setText(FarmaUtility.completeWithSymbol(txtBuscar.getText().trim(),10,"0","I"));
            //
            if (tblListaTransferencias.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaTransferencias, txtBuscar.getText().trim(), 8, 2))) {
                    FarmaUtility.showMessage(this, "Descripción No Encontrado según Criterio de Búsqueda !!!",
                                             txtBuscar);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaTransferencias, txtBuscar, 2);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                if (FarmaVariables.vEconoFar_Matriz)
                    FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnRelacionTransferencias);
                else {
                    VariablesInventario.vArrayGuiaIngresoProductos = new ArrayList();
                    DlgGuiaIngresoResumen dlgGuiaIngresoResumen = new DlgGuiaIngresoResumen(myParentFrame, "", true);
                    dlgGuiaIngresoResumen.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        cargaListaTransferencias();
                        FarmaVariables.vAceptar = false;
                    }
                }
            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            VariablesInventario.vNumNota =
                    tblListaTransferencias.getValueAt(tblListaTransferencias.getSelectedRow(), 0).toString();
            VariablesInventario.vNumDoc =
                    tblListaTransferencias.getValueAt(tblListaTransferencias.getSelectedRow(), 2).toString();
            VariablesInventario.vTipoNota =
                    tblListaTransferencias.getValueAt(tblListaTransferencias.getSelectedRow(), 9).toString();
            VariablesInventario.vTipoNotaOrigen =
                    tblListaTransferencias.getValueAt(tblListaTransferencias.getSelectedRow(), 10).toString();
            DlgGuiaIngresoResumen dlgGuiaIngresoResumen = new DlgGuiaIngresoResumen(myParentFrame, "", true, false);
            dlgGuiaIngresoResumen.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cargaListaTransferencias();
                FarmaVariables.vAceptar = false;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            funcionF6();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            funcionF7();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (lblF8.isVisible()) {
                int[] ancho = { 30, 30, 30, 30, 30, 30, 30, 30, 30 };
                FarmaUtility.saveFile(myParentFrame, ConstantsInventario.columnsListaTransferencias,
                                      tblListaTransferencias, ancho);
            }
        } else if(e.getKeyCode() == KeyEvent.VK_F9) {//Celso L.
            funcionF9();
        } else if(e.getKeyCode() == KeyEvent.VK_F10) {//Celso L.
            funcionF10();            
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

    private void funcionF6() {
        if (tblListaTransferencias.getRowCount() > 0) {
            DlgFiltroDetalleVentas dlgFiltroDetalleVentas =
                new DlgFiltroDetalleVentas(myParentFrame, "", true, ConstantsInventario.NOM_HASTABLE_CMBFILTRO_GUIA,
                                           ConstantsInventario.COD_TIPO_GUIA, ConstantsInventario.DESC_TIPO_GUIA);
            dlgFiltroDetalleVentas.setVisible(true);
            if (FarmaVariables.vAceptar) {
                filtro = VariablesReporte.vCampoFiltro;
                cargaListaTransferencias();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private void funcionF7() {
        btnBuscar.doClick();
    }

    private void funcionF4() {

    }
        
    //Celso L.
    private void funcionF9(){
        VariablesPrecioCompetencia.vArrayProductosCotizados = new ArrayList();
        DlgResumenCotizaciones dlgResumenCotizaciones = new DlgResumenCotizaciones(myParentFrame,"",true);
        dlgResumenCotizaciones.setVisible(true);
    }
    
    private void funcionF10(){
        DlgProductosPorCotizar dlgProductosPorCotizar = new DlgProductosPorCotizar(myParentFrame,"", true);      
        dlgProductosPorCotizar.setVisible(true);
    }
}
