package mifarma.ptoventa.psicotropicos;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.psicotropicos.reference.ConstantsPsicotropicos;
import mifarma.ptoventa.psicotropicos.reference.FacadePsicotropicos;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgReportePsicotropicos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LLEIVA      23.Sep.2013   Creación<br>
 * <br>
 * @author Luis Leiva Bazán<br>
 * @version 1.0<br>
 *
 */
public class DlgReportePsicotropicos extends JDialog {

    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgReportePsicotropicos.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelHeader pnlBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelWhite lblCantidad = new JLabelWhite();
    private JLabelWhite lblPeriodo = new JLabelWhite();
    private JLabelWhite lblProducto = new JLabelWhite();

    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodProducto = new JTextFieldSanSerif();


    private FacadePsicotropicos facade = new FacadePsicotropicos();
    private FarmaTableModel ftm_psicotropico;
    private JTable tbl_psicotropico = new JTable();
    private JLabelWhite lblDatoCantidad = new JLabelWhite();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelOrange lblMensaje = new JLabelOrange();

    java.util.Date fecSys;

    public DlgReportePsicotropicos() {
        this(null, "", false);
    }

    public DlgReportePsicotropicos(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(800, 514));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Registro de Psicotropicos");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pnlFondo.setFocusable(false);
        pnlBusqueda.setBounds(new Rectangle(10, 15, 775, 60));
        pnlTitle.setBounds(new Rectangle(10, 80, 775, 20));
        pnlTitle.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 390, 775, 25));
        pnlResultados.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 100, 775, 290));
        jScrollPane1.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(645, 445, 120, 25));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblTitle.setText("Listado de Registros de Psicotropicos");
        lblTitle.setBounds(new Rectangle(15, 0, 240, 20));
        lblTitle.setFocusable(false);
        lblCantidad.setText("Registros:");
        lblCantidad.setBounds(new Rectangle(20, 0, 70, 25));
        lblCantidad.setFocusable(false);
        lblPeriodo.setText("* Periodo:");
        lblPeriodo.setBounds(new Rectangle(250, 0, 65, 30));
        lblPeriodo.setFocusable(false);
        txtFechaIni.setBounds(new Rectangle(320, 5, 100, 20));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(440, 5, 100, 20));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }
        });
        lblDatoCantidad.setText("0");
        lblDatoCantidad.setBounds(new Rectangle(85, 0, 55, 25));
        lblF12.setBounds(new Rectangle(525, 445, 115, 25));
        lblF12.setText("[ F12 ] Imprimir");
        lblMensaje.setText("Se muestra stock de productos sin movimientos.");
        lblMensaje.setBounds(new Rectangle(20, 420, 525, 20));
        lblMensaje.setForeground(Color.red);
        lblProducto.setText("Producto:");
        lblProducto.setBounds(new Rectangle(250, 35, 70, 20));
        lblProducto.setFocusable(false);
        txtCodProducto.setBounds(new Rectangle(320, 35, 100, 20));
        txtCodProducto.setLengthText(6);
        txtCodProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodProducto_keyPressed(e);
            }
        });
        lblF11.setText("[ F11 ] Busqueda");
        lblF11.setBounds(new Rectangle(410, 445, 110, 25));
        lblF11.setHorizontalAlignment(SwingConstants.CENTER);
        lblF11.setFocusable(false);
        pnlFondo.add(lblMensaje, null);
        pnlFondo.add(lblF12, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        tbl_psicotropico.setFocusable(false);
        jScrollPane1.getViewport().add(tbl_psicotropico, null);
        pnlFondo.add(jScrollPane1, null);
        pnlResultados.add(lblDatoCantidad, null);
        pnlResultados.add(lblCantidad, null);
        pnlFondo.add(pnlResultados, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        pnlBusqueda.add(txtCodProducto, null);
        pnlBusqueda.add(lblProducto, null);
        pnlBusqueda.add(txtFechaFin, null);
        pnlBusqueda.add(txtFechaIni, null);
        pnlBusqueda.add(lblPeriodo, null);
        pnlFondo.add(pnlBusqueda, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void initialize() {
        ftm_psicotropico =
                new FarmaTableModel(ConstantsPsicotropicos.columnsListaPsicotropicos, ConstantsPsicotropicos.defaultListaPsicotropicos,
                                    0);
        FarmaUtility.initSimpleList(tbl_psicotropico, ftm_psicotropico,
                                    ConstantsPsicotropicos.columnsListaPsicotropicos);
        //llenarTablaGuia();
        txtFechaIni.grabFocus();
        
        try {
            fecSys = FarmaUtility.getStringToDate(FarmaSearch.getFechaHoraBD(1),
                                             "dd/MM/yyyy");
        } catch (SQLException e) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            fecSys = cal.getTime();
        }
        lblMensaje.setVisible(false);
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
        FarmaGridUtils.aceptarTeclaPresionada(e, tbl_psicotropico, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ((JComponent)e.getSource()).transferFocus();
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (validacion())
                busqueda();
        } else if (UtilityPtoVenta.verificaVK_F12(e)) {
            if (tbl_psicotropico.getRowCount() <= 0){
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de imprmir", txtFechaIni);
            }else{
                imprimir();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana();
        }
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtCodProducto_keyPressed(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCodProducto, e);
        chkKeyPressed(e);
    }

    private void cerrarVentana() {
        this.setVisible(false);
        this.dispose();
    }

    private void busqueda() {
        ftm_psicotropico.clearTable();
        lblMensaje.setVisible(false);
        ArrayList<ArrayList<String>> resultado =
            facade.listaPsicotropicos(txtFechaIni.getText(), txtFechaFin.getText(), txtCodProducto.getText());

        if (resultado != null && resultado.size() > 0) {
            ftm_psicotropico.data = resultado;
            lblDatoCantidad.setText(String.valueOf(resultado.size()));
            mostrarMensaje();
        } else {
            lblDatoCantidad.setText("0");
            FarmaUtility.showMessage(this, "No se encontraron registros para los parametros de busqueda", null);
        }
    }

    private boolean validacion() {
        boolean flag = true;

        if (!txtFechaIni.getText().trim().equals("") && !txtFechaFin.getText().trim().equals("")) //&&
            //!txtCodProducto.getText().trim().equals("")
        {
            Calendar fecha_inicial = Calendar.getInstance();
            Calendar fecha_final = (Calendar)fecha_inicial.clone();
            try {
                fecha_inicial.setTime(FarmaUtility.getStringToDate(txtFechaIni.getText().trim(), "dd/MM/yyyy"));
            } catch (Exception ex) { //si no se puede obtener un Date, la fecha es incorrecta
                FarmaUtility.showMessage(this, "ERROR: La fecha inicial ingresada no es valida", null);
                flag = false;
            }

            if (flag) {
                try {
                    fecha_final.setTime(FarmaUtility.getStringToDate(txtFechaFin.getText().trim(), "dd/MM/yyyy"));
                } catch (Exception ex) { //si no se puede obtener un Date, la fecha es incorrecta
                    FarmaUtility.showMessage(this, "ERROR: La fecha final ingresada no es valida", null);
                    flag = false;
                }
            }

            if (flag) {
                if (FarmaUtility.diferenciaEnDias(fecha_inicial, fecha_final) > 0) {
                    flag = false;
                    FarmaUtility.showMessage(this, "ERROR: La fecha final debe ser mayor a la fecha inicial", null);
                }
            }

            /*if(flag)
            {   if(!facade.verificaProdPsicotropicos(txtCodProducto.getText().trim()))
                {   flag=false;
                    FarmaUtility.showMessage(this,
                                            "El producto no corresponde a un producto psicotrópico",
                                            null);
                }
            }*/

            if (flag) {
                if (!txtCodProducto.getText().trim().equals("") && txtCodProducto.getText().trim().length() != 6) {
                    flag = false;
                    FarmaUtility.showMessage(this, "Ingrese un código válido.", null);
                }
            }
        } else {
            flag = false;
            FarmaUtility.showMessage(this, "Alguno de los campos se encuentra vacio", null);
        }
        return flag;
    }
    
    private void imprimir() {
        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;

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
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(53) + " REPORTE DE PSICOTROPICOS", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + txtFechaIni.getText(), true);
            vPrint.printBold("Fecha Final: " + txtFechaFin.getText(), true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("===============================================================================================================================",
                             true);
            imprimeColumnas(vPrint);            
            vPrint.printLine("===============================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tbl_psicotropico.getRowCount()*0+20; i++) {                
                imprimeFilas(vPrint,i);                
            }

            vPrint.activateCondensed();
            vPrint.printLine("===============================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tbl_psicotropico.getRowCount(),
                                                                                      ",##0"), 10) +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            
        } catch (SQLException ex) {
            log.error("", ex);
            //showMessage(this,"Ocurrió un error al imprimir : \n"+ex.getMessage(),null);                       
        } finally{
            vPrint.endPrintService();
        }
    }

    private void imprimeColumnas(FarmaPrintService vPrint) {
        String columnas = "";
        int acuColum = 0;
        for(Object grupos : ConstantsPsicotropicos.columnPrintablePsicotropicos){
            columnas = "";
            int[] grupo = (int[])grupos;
            int tamColum = grupo.length;
            for(int j = 0; j < tamColum ; j++){                    
                int ancho = grupo[j];
                if(ancho > 0){
                    columnas += FarmaPRNUtility.alinearIzquierda( ConstantsPsicotropicos.columnsListaPsicotropicos[j+acuColum].m_title ,ancho) + " ";
                }
            }
            vPrint.printBold(columnas,true);
            acuColum += tamColum;
        }
    }

    private void imprimeFilas(FarmaPrintService vPrint, int i) {
        String columnas = "";
        int acuColum = 0;
        for(Object grupos : ConstantsPsicotropicos.columnPrintablePsicotropicos){
            columnas = "";
            int[] grupo = (int[])grupos;
            int tamColum = grupo.length;
            for(int j = 0; j < tamColum ; j++){                    
                int ancho = grupo[j];
                if(ancho > 0){
                    String dato = (String)tbl_psicotropico.getValueAt(i, j+acuColum);
                    if(ConstantsPsicotropicos.columnsListaPsicotropicos[j+acuColum].getAligmment() == JLabel.RIGHT){
                        columnas += FarmaPRNUtility.alinearDerecha(  dato, ancho)+ " ";
                    }else{
                        columnas += FarmaPRNUtility.alinearIzquierda( dato, ancho)+ " ";
                    }
                }
            }
            vPrint.printCondensed(columnas,true);
            acuColum += tamColum;
        }
    }
    
    private void mostrarMensaje(){        
        
        Date fecIni = 
            FarmaUtility.getStringToDate(txtFechaIni.getText().trim(), 
                                         "dd/MM/yyyy");
        Date fecFin = 
            FarmaUtility.getStringToDate(txtFechaFin.getText().trim(), 
                                         "dd/MM/yyyy");
        if(fecSys.equals(fecIni) || fecSys.equals(fecFin)){
            lblMensaje.setVisible(true);
        }else if(fecIni.before(fecSys) && fecFin.after(fecSys)){
            lblMensaje.setVisible(true);
        }
    }
}
