package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgHistoricoCierreDia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      17.08.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgHistoricoCierreDia extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgHistoricoCierreDia.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelHistCierreDia;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JTable tblListaCierreDia = new JTable();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblF3 = new JLabelFunction();

    /**
     * Constantes para la ubicacion de las Columnas de las Tablas
     * @author : dubilluz
     */
    //Constantes para las columnas
    int COL_DIA_CIERRE = 0;
    int COL_USUARIO = 1;
    int COL_FECH_VB = 2;
    int COL_FECH_VB_CONT = 3;
    int COL_FECH_PROCESO = 4;
    int COL_FECH_ARCHIVO = 5;
    int COL_SEC_USU_LOCAL = 6;
    int COL_IND_VB_CIERRE_DIA = 7;
    int COL_TIPO_CAMBIO = 8;
    int COL_IND_VB_CONTABLE = 10;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgHistoricoCierreDia() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgHistoricoCierreDia(Frame parent, String title, boolean modal) {
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
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Histórico de Cierre de Día");
        this.setSize(new Dimension(780, 349));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(670, 280, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF5.setBounds(new Rectangle(10, 280, 130, 20));
        lblF5.setText("[ F2 ] Ver Cierre Dia");
        scrLista.setBounds(new Rectangle(10, 30, 755, 235));
        tblListaCierreDia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaCierreDia_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 755, 20));
        btnLista.setText("Lista Cierre Dia");
        btnLista.setBounds(new Rectangle(10, 0, 110, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblF3.setBounds(new Rectangle(150, 280, 105, 20));
        lblF3.setText("[ F3 ] Insertar");
        scrLista.getViewport().add(tblListaCierreDia, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModelHistCierreDia =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaHistCierreDia, ConstantsCajaElectronica.defaultValuesListaHistCierreDia,
                                    0);
        FarmaUtility.initSimpleList(tblListaCierreDia, tableModelHistCierreDia,
                                    ConstantsCajaElectronica.columnsListaHistCierreDia);
        cargaListaCierreDia();
    }

    private void cargaListaCierreDia() {
        try {
            DBCajaElectronica.cargaListaHistoricoCierreDia(tableModelHistCierreDia);
            if (tblListaCierreDia.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaCierreDia, tableModelHistCierreDia, 9, FarmaConstants.ORDEN_DESCENDENTE);
            FarmaUtility.moveFocusJTable(tblListaCierreDia);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar lista de cierres de dia de venta.\n" +
                    sql, tblListaCierreDia);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocusJTable(tblListaCierreDia);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaCierreDia);
    }

    private void tblListaCierreDia_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                if (FarmaVariables.vEconoFar_Matriz) {
                    FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblListaCierreDia);
                    return;
                }
                insertarCierreDia();
            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
            }
            guardaInfoCierreDia(tblListaCierreDia, tblListaCierreDia.getSelectedRow());
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (tblListaCierreDia.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Debe seleccionar un Cierre de Dia.", tblListaCierreDia);
                return;
            }
            verDetalle();
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

    private void insertarCierreDia() {
        VariablesCajaElectronica.vRegistrarCierreDia = true;
        guardaInfoCierreDia(null, 0);
        DlgCierreDia dlgCierreDia = new DlgCierreDia(myParentFrame, "", true);
        dlgCierreDia.setVisible(true);
        cargaListaCierreDia();
    }

    private void verDetalle() {
        VariablesCajaElectronica.vRegistrarCierreDia = false;
        String fecha = "" + tblListaCierreDia.getValueAt(tblListaCierreDia.getSelectedRow(), 0);
        log.debug("Fecha: " + fecha);
        guardaInfoCierreDia(tblListaCierreDia, tblListaCierreDia.getSelectedRow());
        DlgCierreDia dlgCierreDia = new DlgCierreDia(myParentFrame, "", true);
        dlgCierreDia.setVisible(true);
        cargaListaCierreDia();
        log.debug("Fecha: " + fecha);
        FarmaUtility.findTextInJTable(tblListaCierreDia, fecha, 0, 0);
    }

    private void guardaInfoCierreDia(JTable pJTable, int pRow) {
        VariablesCajaElectronica.vFechaCierreDia =
                FarmaUtility.getValueFieldJTable(pJTable, pRow, COL_DIA_CIERRE); // 0);
        VariablesCajaElectronica.vNombreUsuarioLogueado =
                FarmaUtility.getValueFieldJTable(pJTable, pRow, COL_USUARIO); // 1);
        VariablesCajaElectronica.vSecUsuLocalCierreDia =
                FarmaUtility.getValueFieldJTable(pJTable, pRow, COL_SEC_USU_LOCAL); // 6);
        VariablesCajaElectronica.vIndVBCierreDia =
                FarmaUtility.getValueFieldJTable(pJTable, pRow, COL_IND_VB_CIERRE_DIA); // 7);
        VariablesCajaElectronica.vTipoCambio = FarmaUtility.getValueFieldJTable(pJTable, pRow, COL_TIPO_CAMBIO); // 8);
        //VariablesCajaElectronica.vIndVBContable = FarmaUtility.getValueFieldJTable(pJTable, pRow, 3);
        /**
     * OBTIENE EL INDICADOR DE VB-CONTABLE
     * @author : dubilluz
     * @since  : 06.09.2007
     */
        VariablesCajaElectronica.vIndVBContable =
                FarmaUtility.getValueFieldJTable(pJTable, pRow, COL_IND_VB_CONTABLE); //10);
        log.debug("VariablesCajaElectronica.vIndVBContable" + VariablesCajaElectronica.vIndVBContable);
    }


}
