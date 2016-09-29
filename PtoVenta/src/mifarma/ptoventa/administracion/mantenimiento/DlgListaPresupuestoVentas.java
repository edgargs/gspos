package mifarma.ptoventa.administracion.mantenimiento;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
 * Nombre de la Aplicación : DlgListaPresupuestoVentas.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      18.07.2016   Creación<br>
 * <br>
 *
 */

public class DlgListaPresupuestoVentas extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaPresupuestoVentas.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelControles;
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane scrListado = new JScrollPane();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JLabelWhite lblCantReg = new JLabelWhite();
    private JLabelFunction lnlFModificar = new JLabelFunction();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JTable tblListaControles = new JTable();

    public DlgListaPresupuestoVentas(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(540, 300));
        this.setTitle("Listado de Programas");
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
        pnlTitle1.setBounds(new Rectangle(10, 10, 515, 20));
        btnListado.setText("Listado");
        btnListado.setBounds(new Rectangle(15, 0, 80, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        scrListado.setBounds(new Rectangle(10, 30, 515, 175));
        scrListado.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle2.setBounds(new Rectangle(10, 205, 515, 20));
        lblCantReg.setText("0 registros.");
        lblCantReg.setHorizontalAlignment(JLabelWhite.RIGHT);
        lblCantReg.setBounds(new Rectangle(395, 265, 110, 20));
        lnlFModificar.setBounds(new Rectangle(25, 235, 100, 20));
        lnlFModificar.setText("[ F2 ] Ver Detalle");
        lblFCerrar.setBounds(new Rectangle(425, 235, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        pnlBlanco.add(lblFCerrar, null);
        pnlBlanco.add(lnlFModificar, null);
        pnlBlanco.add(lblCantReg, null);
        pnlBlanco.add(pnlTitle2, null);
        scrListado.getViewport().add(tblListaControles, null);
        pnlBlanco.add(scrListado, null);
        pnlTitle1.add(btnListado, null);
        pnlBlanco.add(pnlTitle1, null);
        tblListaControles.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblListaControles_keyPressed(e);
                }
            });
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTableLista();
        cargaLista();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableLista() {
        tableModelControles =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaPresupuestoVentas, ConstantsMantenimiento.defaultValuesListaPresupuestoVentas,
                                    0);
        FarmaUtility.initSimpleList(tblListaControles, tableModelControles,
                                    ConstantsMantenimiento.columnsListaPresupuestoVentas);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocusJTable(tblListaControles);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaControles);
    }

    private void tblListaControles_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblListaControles);
            else {
                int fila = tblListaControles.getSelectedRow();
                if(fila >= 0){
                    DlgListaPresupuestoVentasVendedor dlgListaPresupuestoVentasVendedor = new DlgListaPresupuestoVentasVendedor(myParentFrame, "", true);
                    String strCodProg = FarmaUtility.getValueFieldJTable(tblListaControles, fila, 0);
                    String strDescProg = FarmaUtility.getValueFieldJTable(tblListaControles, fila, 1);
                    String strVolumen = FarmaUtility.getValueFieldJTable(tblListaControles, fila, 2);
                    String strLLEE = FarmaUtility.getValueFieldJTable(tblListaControles, fila, 3);
                    dlgListaPresupuestoVentasVendedor.setCodProg(strCodProg,strDescProg,strVolumen,strLLEE);
                    dlgListaPresupuestoVentasVendedor.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        cargaLista();
                    }
                }else{
                    FarmaUtility.showMessage(this, "Debe seleccionar un registro.", tblListaControles);
                }
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
            DBMantenimiento.cargaListaProgramaRentables(tableModelControles);
            if (tblListaControles.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaControles, tableModelControles, 0, FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocusJTable(tblListaControles);
            }
            lblCantReg.setText(tblListaControles.getRowCount() + " registros.");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar controles: " + sql.getMessage(), tblListaControles);
        }
    }
}
