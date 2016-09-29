package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.modelo.ConcursoGigante.BeanPeriodoReporteGigante;
import mifarma.ptoventa.reportes.modelo.ConcursoGigante.BeanResumenReporteGigante;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.FacadeReporte;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.jfree.data.category.DefaultCategoryDataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgReporteGigante extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgReporteGigante.class);

    
    private Frame myParentFrame;
    
    private FarmaTableModel modelTblComisionGigante;
    private JTable tblComisionGigante = new JTable();

    private final int COL_COD_VEND = 0;
    private final int COL_NOM_VEND = 1;
    private final int COL_TOT_VTA_CIGV = 2;
    private final int COL_TOT_VTA_SIGV = 3;
    private final int COL_GG = 4;
    private final int COL_G = 5;

    private final int COL_GP = 6;
    private final int COL_OTROS = 7;
    private final int COL_SERVICIOS = 8;
    private final int COL_PORCENTAJE = 9;
    private final int COL_CALCULADO = 10;

    private final int COL_TIPO_FILA = 15;
    private final int COL_SEC_USU = 16;

    private final int COL_LOGIN = 18;


    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlInformacion = new JPanelTitle();
    private JPanelTitle pnlDetalleReporte = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JButton btnBuscar = new JButton();
    
    private JLabel lblPeriodoT = new JLabel();
    private JLabel lblPeriodo = new JLabel();
    private JLabel lblCategoriaT = new JLabel();
    private JLabel lblCategoria = new JLabel();
    private JLabel lblMetaT = new JLabel();
    private JLabel lblMeta = new JLabel();
    private JLabel lblAvanceT = new JLabel();
    private JLabel lblAvance = new JLabel();
    private JLabel lblCumplimientoT = new JLabel();
    private JLabel lblCumplimiento = new JLabel();
    
    private JLabel lblComisionA = new JLabel();
    private JLabel lblComisionB = new JLabel();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JLabelFunction lblF10 = new JLabelFunction();
    private JComboBox cmbPeriodo = new JComboBox();
    private JLabel lblMsjEnter = new JLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgReporteGigante() {
        this(null, "", false);
    }

    public DlgReporteGigante(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(607, 523));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Concurso Gigantes");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        
        jContentPane.setFocusable(false);
        
        /* ************************************ */
        /*                                      */
        /* ************************************ */
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(100, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        
        cmbPeriodo.setBounds(new Rectangle(170, 5, 170, 20));
        cmbPeriodo.setName("CMBPERIODO");
        cmbPeriodo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                eventoKeyPressed(e);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(360, 5, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 580, 30));
        pnlCriterioBusqueda.setFocusable(false);

        pnlCriterioBusqueda.add(cmbPeriodo, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        
        /**************************************/
        /**                                   */
        /**************************************/
        lblPeriodoT.setText("Periodo :");
        lblPeriodoT.setBounds(new Rectangle(5, 0, 90, 20));
        lblPeriodoT.setFont(new Font("SansSerif", 1, 13));
        lblPeriodoT.setForeground(Color.white);
        lblPeriodoT.setFocusable(false);
        
        lblPeriodo.setText("[PERIODO]");
        lblPeriodo.setBounds(new Rectangle(120, 0, 130, 20));
        lblPeriodo.setFont(new Font("SansSerif", 1, 13));
        lblPeriodo.setForeground(Color.white);
        lblPeriodo.setFocusable(false);
        
        lblCategoriaT.setText("Categoria :");
        lblCategoriaT.setBounds(new Rectangle(265, 0, 80, 20));
        lblCategoriaT.setFont(new Font("SansSerif", 1, 13));
        lblCategoriaT.setForeground(Color.white);
        lblCategoriaT.setFocusable(false);
        
        lblCategoria.setText("[CATEGORIA]");
        lblCategoria.setBounds(new Rectangle(350, 0, 200, 20));
        lblCategoria.setFont(new Font("SansSerif", 1, 13));
        lblCategoria.setForeground(Color.white);
        lblCategoria.setFocusable(false);
        
        lblMetaT.setText("Meta :");
        lblMetaT.setBounds(new Rectangle(5, 20, 90, 20));
        lblMetaT.setFont(new Font("SansSerif", 1, 13));
        lblMetaT.setForeground(Color.white);
        lblMetaT.setFocusable(false);
        
        lblMeta.setText("[META]");
        lblMeta.setBounds(new Rectangle(120, 20, 130, 20));
        lblMeta.setFont(new Font("SansSerif", 1, 13));
        lblMeta.setForeground(Color.white);
        lblMeta.setFocusable(false);
        
        lblAvanceT.setText("Avance :");
        lblAvanceT.setBounds(new Rectangle(265, 20, 80, 20));
        lblAvanceT.setFont(new Font("SansSerif", 1, 13));
        lblAvanceT.setForeground(Color.white);
        lblAvanceT.setFocusable(false);
        
        lblAvance.setText("[AVANCE]");
        lblAvance.setBounds(new Rectangle(350, 20, 215, 20));
        lblAvance.setFont(new Font("SansSerif", 1, 13));
        lblAvance.setForeground(Color.white);
        lblAvance.setFocusable(false);
        
        lblCumplimientoT.setText("Cumplimiento :");
        lblCumplimientoT.setBounds(new Rectangle(5, 40, 105, 20));
        lblCumplimientoT.setFont(new Font("SansSerif", 1, 13));
        lblCumplimientoT.setForeground(Color.white);
        lblCumplimientoT.setFocusable(false);
        
        lblCumplimiento.setText("[CUMPLIMIENTO]");
        lblCumplimiento.setBounds(new Rectangle(120, 40, 130, 20));
        lblCumplimiento.setFont(new Font("SansSerif", 1, 13));
        lblCumplimiento.setForeground(Color.white);
        lblCumplimiento.setFocusable(false);
        
        pnlDetalleReporte.setBounds(new Rectangle(10, 55, 580, 70));
        pnlDetalleReporte.setFocusable(false);

        pnlDetalleReporte.add(lblPeriodoT, null);
        pnlDetalleReporte.add(lblPeriodo, null);
        pnlDetalleReporte.add(lblCategoriaT, null);
        pnlDetalleReporte.add(lblCategoria, null);
        pnlDetalleReporte.add(lblMetaT, null);
        pnlDetalleReporte.add(lblMeta, null);
        pnlDetalleReporte.add(lblAvanceT, null);
        pnlDetalleReporte.add(lblAvance, null);
        pnlDetalleReporte.add(lblCumplimientoT, null);

        /**************************************/
        /**                                   */
        /**************************************/
        pnlDetalleReporte.add(lblCumplimiento, null);
        jScrollPane1.setBounds(new Rectangle(10, 135, 580, 270));
        jScrollPane1.setFocusable(false);
        
        //tblComisionGigante.setFocusable(false);
        tblComisionGigante.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblVentasVendedor_keyPressed(e);
            }
        });
        
        jScrollPane1.getViewport().add(tblComisionGigante, null);
        
        /**************************************/
        /**                                   */
        /**************************************/
        lblComisionA.setText("[Mensaje Comision A]");
        lblComisionA.setBounds(new Rectangle(5, 0, 570, 20));
        lblComisionA.setFont(new Font("SansSerif", 1, 11));
        lblComisionA.setForeground(Color.white);
        lblComisionA.setFocusable(false);
        
        lblComisionB.setText("[Mensaje Comision B]");
        lblComisionB.setBounds(new Rectangle(5, 20, 570, 20));
        lblComisionB.setFont(new Font("SansSerif", 1, 11));
        lblComisionB.setForeground(Color.white);
        lblComisionB.setFocusable(false);
        
        pnlInformacion.setBounds(new Rectangle(10, 410, 580, 45));
        pnlInformacion.setFocusable(false);

        pnlInformacion.setBackground(new Color(43, 141, 39));
        pnlInformacion.add(lblComisionA, null);
        pnlInformacion.add(lblComisionB, null);
        
        /**************************************/
        /**                                   */
        /**************************************/
        lblEsc.setBounds(new Rectangle(500, 465, 90, 25));
        lblEsc.setText("[ ESC ] Cerrar");
        
        lblMsjEnter.setText("Presione [Enter] para realizar otra búsqueda");
        lblMsjEnter.setBounds(new Rectangle(10, 465, 340, 15));
        lblMsjEnter.setFont(new Font("SansSerif", 1, 14));
        lblMsjEnter.setForeground(new Color(43, 141, 39));
        lblMsjEnter.setVisible(false);

        jContentPane.add(lblMsjEnter, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jContentPane.add(pnlDetalleReporte, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlInformacion, null);
        jContentPane.add(lblEsc, null);
        
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        // CREA TABLA 
        FarmaColumnData[] cabeceraTabla = new FarmaColumnData[]{new FarmaColumnData("Codigo", 70, JLabel.CENTER),
                                                                              new FarmaColumnData("Vendedor", 220, JLabel.LEFT),
                                                                              new FarmaColumnData("Com.Gigante (A)", 130, JLabel.RIGHT),
                                                                              new FarmaColumnData("Com.Gigante (B)", 130, JLabel.RIGHT)};
        modelTblComisionGigante = new FarmaTableModel( cabeceraTabla,new Object[]{ " ", " ", " ", " "},0);
        
        FarmaUtility.initSimpleList(tblComisionGigante, modelTblComisionGigante, cabeceraTabla);
        
        cargarComboPeriodos();
        busqueda();

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */
    
    private void eventoKeyPressed(KeyEvent e){
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblComisionGigante, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        }else{
            chkKeyPressed(e);
        }
    }

    /*private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        }

        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }*/

    private void tblVentasVendedor_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            FarmaUtility.moveFocus(cmbPeriodo);
            lblMsjEnter.setVisible(false);
        }else{
            chkKeyPressed(e);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbPeriodo);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbPeriodo);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblComisionGigante.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblComisionGigante);
        }
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        //FarmaUtility.dateComplete(cmbAnio, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        //FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        /*if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);*/
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
        }
        /* FarmaGridUtils.aceptarTeclaPresionada(e, tblComisionGigante, null, 0);
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            int vFila = tblComisionGigante.getSelectedRow();
            if (tblComisionGigante.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtAnio);
                FarmaUtility.moveFocus(txtAnio);
                return;
            } else {
                VariablesReporte.vCodigoUsuario = ((String)tblComisionGigante.getValueAt(vFila, COL_SEC_USU)).trim();
                VariablesReporte.vNombreUsuario = ((String)tblComisionGigante.getValueAt(vFila, COL_NOM_VEND)).trim();
                muestraDetalleVentasVendedor();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (tblComisionGigante.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtAnio);
            else
                muestraVentaOrdenar();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (tblComisionGigante.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de guardar", txtAnio);
            //else ;
            //exportarDatos();
        } else if (UtilityPtoVenta.verificaVK_F12(e)) {
            if (tblComisionGigante.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de imprmir", txtAnio);
            else
                imprimir();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (tblComisionGigante.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ver el gráfico.", txtAnio);
            else
                muestraGrafico();
        } */
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void buscaRegistroVentasVendedor(String pFechaInicio, String pFechaFin) {
        /* VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;

        //TOTALES
        if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_TOTALES)) {

            btnListado.setText("Listado de Ventas por Vendedor");
            cargaRegistroVentasVendedor();

        }
        //    TIPO : MEZON
        else if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_MEZON)) {
            btnListado.setText("Listado de Ventas por Vendedor Mezon");
            cargaRegistroVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Mezon);
        }

        //    TIPO : delivery
        else if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_DELIVERY)) {
            btnListado.setText("Listado de Ventas por Vendedor Delivery");
            cargaRegistroVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Delivery);
        }

        else {
            btnListado.setText("Listado de Ventas por Vendedor Institucional");
            cargaRegistroVentasVendedorTipo(ConstantsReporte.Tipo_Venta_Institucional);
        } */
    }

    private void cargaRegistroVentasVendedor() {
        /* try {
            DBReportes.cargaListaVentasporVendedor(modelTblComisionGigante, VariablesReporte.vFechaInicio,
                                                   VariablesReporte.vFechaFin);

            if (tblComisionGigante.getRowCount() > 0) {
                lblRegistros.setText("" + tblComisionGigante.getRowCount());
                double T_GG = 0.0;
                double T_G = 0.0;
                double T_GP = 0.0;
                for (int i = 0; i < tblComisionGigante.getRowCount(); i++) {
                    if (FarmaUtility.getValueFieldJTable(tblComisionGigante, i,
                                                         COL_TIPO_FILA).toString().trim().equalsIgnoreCase("PORCENTAJE")) {
                        T_GG =
FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblComisionGigante, i, COL_GG).toString().trim());
                        T_G =
FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblComisionGigante, i, COL_G).toString().trim());
                        T_GP =
FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblComisionGigante, i, COL_GP).toString().trim());
                        lbl_G.setText("" + (FarmaUtility.formatNumber((T_GG + T_G + T_GP))));
                        break;
                    }
                }
                FarmaUtility.ordenar(tblComisionGigante, modelTblComisionGigante,
                        // COL_ORDEN,
                        0, FarmaConstants.ORDEN_DESCENDENTE);
                muestraObservacion(VariablesReporte.vFechaInicio);
                FarmaUtility.moveFocusJTable(tblComisionGigante);
            } else {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                lblRegistros.setText("0");
                lbl_G.setText("0");
                lblObservacion.setText("");
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar Ventas por Vendedor :\n" +
                    sql.getMessage(), txtFechaIni);
        } */
    }

    private void cargaRegistroVentasVendedorTipo(String pTipo) {
        /* try {

            DBReportes.cargaListaVentasporVendedorTipo(modelTblComisionGigante, VariablesReporte.vFechaInicio,
                                                       VariablesReporte.vFechaFin, pTipo);

            if (tblComisionGigante.getRowCount() > 0) {
                lblRegistros.setText("" + tblComisionGigante.getRowCount());
                double T_GG = 0.0;
                double T_G = 0.0;
                double T_GP = 0.0;
                for (int i = 0; i < tblComisionGigante.getRowCount(); i++) {
                    if (FarmaUtility.getValueFieldJTable(tblComisionGigante, i,
                                                         COL_TIPO_FILA).toString().trim().equalsIgnoreCase("PORCENTAJE")) {
                        T_GG =
FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblComisionGigante, i, COL_GG).toString().trim());
                        T_G =
FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblComisionGigante, i, COL_G).toString().trim());
                        T_GP =
FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblComisionGigante, i, COL_GP).toString().trim());
                        lbl_G.setText("" + (FarmaUtility.formatNumber((T_GG + T_G + T_GP))));
                        break;
                    }
                }
                FarmaUtility.ordenar(tblComisionGigante, modelTblComisionGigante, 0, FarmaConstants.ORDEN_DESCENDENTE);

                muestraObservacion(VariablesReporte.vFechaInicio);
                FarmaUtility.moveFocusJTable(tblComisionGigante);
            } else {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                lblRegistros.setText("0");
                lbl_G.setText("0");
                lblObservacion.setText("");
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar Ventas por Vendedor :\n" +
                    sql.getMessage(), txtFechaIni);
        } */
    }

    private boolean validarCampos() {
       /*  boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno; */
        return true;
    }

    private void busqueda() {
        String codigoSeleccionado = FarmaLoadCVL.getCVLCode(cmbPeriodo.getName(), cmbPeriodo.getSelectedIndex());
        if(codigoSeleccionado!=null && codigoSeleccionado.trim().length()>0){
            FacadeReporte facade = new FacadeReporte();
            facade.procesarVentasComisionGigante(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, codigoSeleccionado);
            List listaResumen = facade.obtenerResumenComisionGigante(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, codigoSeleccionado);
            lblPeriodo.setText((String)cmbPeriodo.getSelectedItem());
            modelTblComisionGigante.clearTable();
            if(listaResumen.size()==0){
                lblCategoria.setText("");
                lblMeta.setText("");
                lblAvance.setText("");
                lblCumplimiento.setText("");
            }else{
                BeanResumenReporteGigante bean = (BeanResumenReporteGigante)listaResumen.get(0);
                lblCategoria.setText(bean.getCategoria());
                lblMeta.setText(bean.getValMeta());
                lblAvance.setText(bean.getValAvance());
                lblCumplimiento.setText(bean.getValCumplimiento());
                try{
                    DBReportes.obtenerReporteComisionGigante(modelTblComisionGigante, FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, codigoSeleccionado);
                }catch(Exception ex){
                    log.error("", ex);
                    FarmaUtility.showMessage(this, "Concurso Gigante:\nError al cargar los datos de comision de ventas", cmbPeriodo);
                }
                FarmaUtility.moveFocusJTable(tblComisionGigante);
                lblMsjEnter.setVisible(true);
                //FarmaUtility.moveFocus(tblComisionGigante);
            }
        }
    }

    private void muestraDetalleVentasVendedor() {
        DlgDetalleVentasPorVendedor dlgDetalleVentasPorVendedor =
            new DlgDetalleVentasPorVendedor(myParentFrame, "", true);

        //TOTALES
        if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_TOTALES))

            dlgDetalleVentasPorVendedor.setTitle("Listado de Ventas por Vendedor");

        //    TIPO : MEZON
        else if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_MEZON))

            dlgDetalleVentasPorVendedor.setTitle("Listado de Ventas por Vendedor Meson ");

        //    TIPO : delivery
        else if (VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA.equalsIgnoreCase(ConstantsReporte.ACCION_MOSTRAR_DELIVERY))
            dlgDetalleVentasPorVendedor.setTitle("Listado de Ventas por Vendedor Delivery");


        else
            dlgDetalleVentasPorVendedor.setTitle("Listado de Ventas por Vendedor Institucional");


        dlgDetalleVentasPorVendedor.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }

    void muestraVentaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_DESCRIP_CAMPO =
        { "Codigo", "Vendedor", "Tot.C.IGV", "Tot.C.IGV", "GG", "G", "Otros", "Servicios", "%", "Calculado" };
        String[] IND_CAMPO = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEVENDEDOR;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblComisionGigante, modelTblComisionGigante, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblComisionGigante.repaint();
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

        FarmaUtility.saveFileRuta(ruta, ConstantsReporte.columnsListaVentasVendedor, tblComisionGigante,
                                  new int[] { 15, 40, 20, 20, 20, 20, 20, 20, 20, 0 });

        //FarmaUtility.showMessage(this, "Los datos se exportaron correctamente", txtAnio);

    }

    private void imprimir() {

        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;

        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);

        if (!vPrint.startPrintService()) {
            //FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtAnio);
            return;
        }

        //  RESPORTE ACTUAL
        imprimir_modelo_3(vPrint);
    }


    private void imprimir_modelo_2(FarmaPrintService vPrint) {
        ArrayList listaImprimir = new ArrayList();
        String fechaActual = "";
        try {
            listaImprimir = DBReportes.cargaListaVV_Impr(VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin);
            fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException e) {
            log.error("", e);
        }

        if (listaImprimir.size() > 0) {

            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE DE VENTAS POR VENDEDOR", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("=========================================================================================",
                             true);
            vPrint.printBold("Codigo  Vendedor                              % Garantizados      Total S.IGV     Orden",
                             true);
            vPrint.printLine("=========================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();

            for (int i = 0; i < listaImprimir.size(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                           i, 0), 7) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                           i, 1), 33) +
                                      " " +
                                      FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                         i, 2), 16) +
                                      " " +
                                      FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                         i, 3), 19) +
                                      " " +
                                      FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                         i, 4), 6),
                                      true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("=========================================================================================",
                             true);

            vPrint.printBold("Registros Impresos: " + FarmaUtility.formatNumber(listaImprimir.size(), ",##0") + " " +
                             FarmaPRNUtility.llenarBlancos(32) + "", true);


            vPrint.deactivateCondensed();
            vPrint.endPrintService();

        }


    }

    private void imprimir_modelo_1(FarmaPrintService vPrint) {

        try {

            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE DE VENTAS POR VENDEDOR", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("=========================================================================================================================================",
                             true);
            vPrint.printBold( //"Codigo  Vendedor              Total Venta   Total Bono    V.Grupo A     Venta PP      No Farma    Grupo No A       ",
                    "Codigo  Vendedor              Total C.IGV   Total S.IGV   Venta  GG     Venta G       V. Otros    V.Servicios     Porcentaje    Calculado",
                    true);
            vPrint.printLine("=========================================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();

            for (int i = 0; i < tblComisionGigante.getRowCount(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblComisionGigante.getValueAt(i,
                                                                                                            COL_COD_VEND),
                                                                       7) + " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblComisionGigante.getValueAt(i,
                                                                                                            COL_NOM_VEND),
                                                                       20) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblComisionGigante.getValueAt(i,
                                                                                                          COL_TOT_VTA_CIGV),
                                                                     12) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblComisionGigante.getValueAt(i,
                                                                                                          COL_TOT_VTA_SIGV),
                                                                     12) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblComisionGigante.getValueAt(i, COL_GG),
                                                                     12) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblComisionGigante.getValueAt(i, COL_G),
                                                                     12) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblComisionGigante.getValueAt(i,
                                                                                                          COL_OTROS),
                                                                     13) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblComisionGigante.getValueAt(i,
                                                                                                          COL_SERVICIOS),
                                                                     13) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblComisionGigante.getValueAt(i,
                                                                                                          COL_PORCENTAJE),
                                                                     13) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblComisionGigante.getValueAt(i,
                                                                                                          COL_CALCULADO),
                                                                     13), true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("=========================================================================================================================================",
                             true);
            /*vPrint.printBold("Registros Impresos: "+
                           FarmaUtility.formatNumber(tblVentasVendedor.getRowCount(),",##0"),
                           false);*/
            /*vPrint.printBold("Registros Impresos: " +
                             FarmaUtility.formatNumber(tblComisionGigante.getRowCount(), ",##0") + " " +
                             FarmaPRNUtility.llenarBlancos(32) + "Total Garantizado: " + lbl_G.getText() + "", true);*/


            vPrint.deactivateCondensed();
            vPrint.endPrintService();

            FarmaUtility.showMessage(this, "..fin impresion ", null);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sql.getMessage(), null);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    private void muestraGrafico() {
        VariablesReporte.vDataSet_Reporte = load_data_xy();
        if (VariablesReporte.vDataSet_Reporte != null) {
            DlgGrafico dlgGrafico = new DlgGrafico(myParentFrame, "Grafico", true);
            dlgGrafico.setVisible(true);

            if (FarmaVariables.vAceptar) {
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private DefaultCategoryDataset load_data_xy() {
        DefaultCategoryDataset dataset_return = new DefaultCategoryDataset();
        for (int i = 0; i < tblComisionGigante.getRowCount(); i++) {
            if (FarmaUtility.getValueFieldJTable(tblComisionGigante, i,
                                                 COL_TIPO_FILA).toString().trim().equalsIgnoreCase("VENDEDOR")) {
                dataset_return.setValue(FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblComisionGigante,
                                                                                                       i,
                                                                                                       COL_PORCENTAJE).toString().trim()),
                                        "Vendedores",
                                        FarmaUtility.getValueFieldJTable(tblComisionGigante, i, COL_LOGIN).toString().trim());
            }
        }
        return dataset_return;
    }


    private void imprimir_modelo_3(FarmaPrintService vPrint) {
        ArrayList listaImprimir = new ArrayList();
        String fechaActual = "";
        try {
            listaImprimir = DBReportes.cargaListaVV_Impr(VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin);
            fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException e) {
            log.error("", e);
        }

        if (listaImprimir.size() > 0) {
            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE DE VENTAS POR VENDEDOR", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("=========================================================================================================================================",
                             true);
            vPrint.printBold("Codigo  Vendedor                              % Garantizados      Total S.IGV     Orden     NumPed      Farma     No.Farma         %Part.",
                             true);
            vPrint.printLine("=========================================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();

            for (int i = 0; i < listaImprimir.size(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                           i, 0), 7) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                           i, 1), 33) +
                                      " " +
                                      FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                         i, 2), 16) +
                                      " " +
                                      FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                         i, 3), 19) +
                                      " " +
                                      FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir,
                                                                                                         i, 8), 6) +
                                      " " + FarmaPRNUtility.

                        alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir, i, 4), 10) + " " +
                        FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir, i, 5), 12) +
                        " " +
                        FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir, i, 6), 12) +
                        " " +
                        FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(listaImprimir, i, 7), 13),

                        true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("=========================================================================================================================================",
                             true);

            vPrint.printBold("Registros Impresos: " + FarmaUtility.formatNumber(listaImprimir.size(), ",##0") + " " +
                             FarmaPRNUtility.llenarBlancos(32) + "", true);


            vPrint.deactivateCondensed();
            vPrint.endPrintService();

        }


    }

    /**
     * Muestra observaciones
     * @author ERIOS
     * @since 02.04.2014
     * @param pFecha
     */
    private void muestraObservacion(String pFecha) {
        /*String[] periodo = pFecha.split("/");
        lblObservacion.setText("(*) Los indicadores se han calculado en base al periodo: " + periodo[1] + "-" +
                               periodo[2]);*/
    }
    
    private void cargarComboPeriodos(){
        FacadeReporte facade = new FacadeReporte();
        List lista = facade.obtenerPeriodoReporteGigantes(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal);
        String msjComisionA = facade.obtenerMsjInfoComisionGiganteA();
        String msjComisionB = facade.obtenerMsjInfoComisionGiganteB();
        if(lista==null){
            FarmaUtility.showMessage(this, "Concurso Gigante:\nError al cargar los periodos de busqueda del reporte.\n" +
                                           "Reintente, si problema persiste comuniquese con Mesa de Ayuda!!!", null);
            cerrarVentana(false);
        }else{
            if(lista.size()==0){
                FarmaUtility.showMessage(this, "Concurso Gigante:\nNo hay periodos que mostrar en el reporte.",null);
                cerrarVentana(false);
            }else{
                ArrayList listaPeriodo = new ArrayList();
                for(int i=0;i<lista.size();i++){
                    BeanPeriodoReporteGigante periodo = (BeanPeriodoReporteGigante)lista.get(i);
                    ArrayList fila = new ArrayList();
                    fila.add(periodo.getMesId());
                    fila.add(periodo.getPeriodo());
                    listaPeriodo.add(fila);
                }
                FarmaLoadCVL.loadCVLFromArrayList(cmbPeriodo, cmbPeriodo.getName(), listaPeriodo, true);
                lblComisionA.setText(msjComisionA);
                lblComisionB.setText(msjComisionB);
            }
        }
    }
}