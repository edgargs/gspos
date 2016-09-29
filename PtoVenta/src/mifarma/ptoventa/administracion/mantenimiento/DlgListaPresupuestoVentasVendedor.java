package mifarma.ptoventa.administracion.mantenimiento;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
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

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.mantenimiento.reference.ConstantsMantenimiento;
import mifarma.ptoventa.administracion.mantenimiento.reference.DBMantenimiento;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2016 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaPresupuestoVentasVendedor.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      18.07.2016   Creación<br>
 * <br>
 *
 */

public class DlgListaPresupuestoVentasVendedor extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaPresupuestoVentasVendedor.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane scrListado = new JScrollPane();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JLabelWhite lblTotVolumen = new JLabelWhite();
    private JLabelFunction lblFCrear = new JLabelFunction();
    private JLabelFunction lnlFModificar = new JLabelFunction();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JTable tblLista = new JTable();    
    private JLabelWhite lbProg_T = new JLabelWhite();
    private JLabelWhite lblDescProg = new JLabelWhite();
    private String pCodProg,pSecUsu,pNomUsu;
    private JLabelFunction lblF11 = new JLabelFunction();
    private boolean isModify;
    private JLabelWhite lblTotLLEE = new JLabelWhite();
    private JLabelWhite lblVolumen_T = new JLabelWhite();
    private JLabelWhite lblLLEE_T = new JLabelWhite();
    private JLabelWhite lblVolumen = new JLabelWhite();
    private JLabelWhite lblLLEE = new JLabelWhite();
    private String pVolumen;
    private String pLLEE;
    private JLabelWhite lblAsignado_T = new JLabelWhite();

    public DlgListaPresupuestoVentasVendedor(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;

        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(540, 354));
        this.setTitle("Listado de Presupuesto por Vendedor");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader.setBounds(new Rectangle(10, 10, 515, 50));
        pnlTitle1.setBounds(new Rectangle(10, 70, 515, 20));
        btnListado.setText("Listado");
        btnListado.setBounds(new Rectangle(15, 0, 80, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        scrListado.setBounds(new Rectangle(10, 90, 515, 175));
        scrListado.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle2.setBounds(new Rectangle(10, 265, 515, 20));
        lblTotVolumen.setText("999,999.00");
        lblTotVolumen.setHorizontalAlignment(JLabelWhite.RIGHT);
        lblTotVolumen.setBounds(new Rectangle(320, 0, 75, 20));
        lblTotVolumen.setForeground(new Color(43, 141, 39));
        lblFCrear.setBounds(new Rectangle(15, 295, 90, 20));
        lblFCrear.setText("[ F2 ] Crear");
        lnlFModificar.setBounds(new Rectangle(110, 295, 100, 20));
        lnlFModificar.setText("[ F3 ] Modificar");
        lblFCerrar.setBounds(new Rectangle(425, 295, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        pnlBlanco.add(lblF11, null);
        pnlBlanco.add(lblFCerrar, null);
        pnlBlanco.add(lnlFModificar, null);
        pnlBlanco.add(lblFCrear, null);
        pnlTitle2.add(lblAsignado_T, null);
        pnlTitle2.add(lblTotLLEE, null);
        pnlTitle2.add(lblTotVolumen, null);
        pnlBlanco.add(pnlTitle2, null);
        scrListado.getViewport().add(tblLista, null);
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaControles_keyPressed(e);
            }
        });
        lbProg_T.setBounds(new Rectangle(10, 15, 70, 20));
        lbProg_T.setText("Programa:");
        lblDescProg.setBounds(new Rectangle(85, 15, 190, 20));
        lblF11.setBounds(new Rectangle(315, 295, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblTotLLEE.setText("9,999.00");
        lblTotLLEE.setHorizontalAlignment(JLabelWhite.RIGHT);
        lblTotLLEE.setBounds(new Rectangle(395, 0, 75, 20));
        lblTotLLEE.setForeground(new Color(43, 141, 39));
        lblVolumen_T.setText("Meta Volumen:");
        lblVolumen_T.setBounds(new Rectangle(255, 10, 100, 15));
        lblLLEE_T.setText("Meta LLEE:");
        lblLLEE_T.setBounds(new Rectangle(255, 30, 100, 15));
        lblVolumen.setText("9,990.00");
        lblVolumen.setBounds(new Rectangle(375, 10, 70, 15));
        lblVolumen.setHorizontalAlignment(SwingConstants.RIGHT);
        lblVolumen.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblLLEE.setText("990.00");
        lblLLEE.setBounds(new Rectangle(375, 30, 70, 15));
        lblLLEE.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAsignado_T.setText("Total Presupuesto ASIGNADO:");
        lblAsignado_T.setBounds(new Rectangle(85, 0, 180, 20));
        pnlBlanco.add(scrListado, null);
        pnlTitle1.add(btnListado, null);
        pnlBlanco.add(pnlTitle1, null);
        pnlHeader.add(lblLLEE, null);
        pnlHeader.add(lblVolumen, null);
        pnlHeader.add(lblLLEE_T, null);
        pnlHeader.add(lblVolumen_T, null);
        pnlHeader.add(lblDescProg, null);
        pnlHeader.add(lbProg_T, null);
        pnlBlanco.add(pnlHeader, null);
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTableLista();
        
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableLista() {
        tableModel =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaPresupuestoVentasVendedor, ConstantsMantenimiento.defaultValuesListaPresupuestoVentasVendedor,
                                    0);
        FarmaUtility.initSimpleList(tblLista, tableModel,
                                    ConstantsMantenimiento.columnsListaPresupuestoVentasVendedor);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocusJTable(tblLista);
        cargaLista();
        validaDiasRegistrar();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblLista);
    }

    private void tblListaControles_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(isModify){
                if (!JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro de salir? Se perderán los cambios realizados.")) {
                    return;
                }   
            }
            cerrarVentana(false);            
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblLista);
            else {
                
                DlgDatosPresupuestoVentasVendedor dlgDatosPresupuestoVentasVendedor = new DlgDatosPresupuestoVentasVendedor(myParentFrame, "", true);
                dlgDatosPresupuestoVentasVendedor.setCodProg(pCodProg);
                dlgDatosPresupuestoVentasVendedor.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    ArrayList<String> newrow = dlgDatosPresupuestoVentasVendedor.getRegistro();
                    tableModel.insertRow(newrow);
                    isModify = true;
                    mostrarTotales();
                    FarmaUtility.moveFocusJTable(tblLista);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblLista);
            else {
                int filaSelect = tblLista.getSelectedRow();
                if (filaSelect >= 0) {
                    cargarVariables(filaSelect);
                    
                    DlgDatosPresupuestoVentasVendedor dlgDatosPresupuestoVentasVendedor = new DlgDatosPresupuestoVentasVendedor(myParentFrame, "", true);
                    dlgDatosPresupuestoVentasVendedor.setCodProg(pCodProg);
                    dlgDatosPresupuestoVentasVendedor.setSecUsu(pSecUsu,pNomUsu);
                    dlgDatosPresupuestoVentasVendedor.setPpto(pVolumen,pLLEE);
                    dlgDatosPresupuestoVentasVendedor.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        ArrayList<String> newrow = dlgDatosPresupuestoVentasVendedor.getRegistro();
                        tableModel.updateRow(newrow,filaSelect);
                        isModify = true;
                        mostrarTotales();
                        FarmaUtility.moveFocusJTable(tblLista);
                    }
                } else
                    FarmaUtility.showMessage(this, "Debe de seleccionar un registro.", tblLista);
            }
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            if(isModify){
                if (JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro que desea grabar los datos?")) {
                    grabarPresupuestoVendedor();
                }
            }else{
                FarmaUtility.showMessage(this, "No ha realizado modificaciones a los registros.", tblLista);
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaLista() {
        try {
            DBMantenimiento.cargaListaPresupuestoVendedor(tableModel,pCodProg);
            if (tblLista.getRowCount() > 0) {
                FarmaUtility.ordenar(tblLista, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocusJTable(tblLista);
            }
            mostrarTotales();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar: " + sql.getMessage(), tblLista);
        }
    }

    private void cargarVariables(int filaSelect) {       

        pSecUsu = FarmaUtility.getValueFieldJTable(tblLista, filaSelect, 0);
        pNomUsu = FarmaUtility.getValueFieldJTable(tblLista, filaSelect, 1);
        pVolumen = FarmaUtility.getValueFieldJTable(tblLista, filaSelect, 2);
        pLLEE = FarmaUtility.getValueFieldJTable(tblLista, filaSelect, 3);
        
    }

    public void setCodProg(String pCodProg, String pDescProg, String pVolumen, String pLLEE) {
        this.pCodProg = pCodProg;
        lblDescProg.setText(pCodProg+" / "+pDescProg);
        lblVolumen.setText(pVolumen);
        lblLLEE.setText(pLLEE);
    }

    private void grabarPresupuestoVendedor() {
        try{
            DBMantenimiento.limpiarPresupuestoVendedor(pCodProg);
            Iterator<ArrayList<String>> it = tableModel.data.iterator();
            for(;it.hasNext();){
                ArrayList<String> fila = it.next();
                DBMantenimiento.grabarPresupuestoVendedor(pCodProg,fila);
            }
            String strIsOperador = (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS))?"S":"N";
            DBMantenimiento.validarPresupuestoVendedor(pCodProg,strIsOperador);
            FarmaUtility.aceptarTransaccion();
            cerrarVentana(true);
        }catch(SQLException sql){
            log.error("",sql);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, sql.getMessage().substring(11, sql.getMessage().indexOf("ORA-06512")), null);
        }
    }

    private void mostrarTotales() {
        double totVolumen = FarmaUtility.sumColumTable(tblLista, 2);
        double totLLEE = FarmaUtility.sumColumTable(tblLista, 3);
        lblTotVolumen.setText(FarmaUtility.formatNumber(totVolumen));
        lblTotLLEE.setText(FarmaUtility.formatNumber(totLLEE));
        double metaVolumen = FarmaUtility.getDecimalNumber(lblVolumen.getText());
        double metaLLEE = FarmaUtility.getDecimalNumber(lblLLEE.getText());
        if(totVolumen == metaVolumen){
            lblTotVolumen.setForeground(new Color(43, 141, 39));
        }else{
            lblTotVolumen.setForeground(Color.RED);
        }
        if(totLLEE == metaLLEE){
            lblTotLLEE.setForeground(new Color(43, 141, 39));
        }else{
            lblTotLLEE.setForeground(Color.RED);
        }
    }

    private void validaDiasRegistrar() {
        String strIndValida;
        try {
            strIndValida = DBMantenimiento.getIndValidaDiasRegistrar(pCodProg);
            if(!"S".equals(strIndValida)){
                FarmaUtility.showMessage(this, strIndValida,
                                         null);
                if(!cargaLogin()){
                    cerrarVentana(false);
                }
            }
        } catch (SQLException e) {
            log.error("",e);
            FarmaUtility.showMessage(this, e.getMessage(),
                                     null);
            cerrarVentana(false);
        }
    }
    
    private boolean cargaLogin() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            
            dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);

            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrió un error al validar rol de usuariario \n : " + e.getMessage(),
                                     null);
        }
        return FarmaVariables.vAceptar;
    }
}
