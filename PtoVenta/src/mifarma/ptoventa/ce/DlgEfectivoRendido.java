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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgEfectivoRendido.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      31.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgEfectivoRendido extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgEfectivoRendido.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JTable tblLista = new JTable();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblF10 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgEfectivoRendido() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgEfectivoRendido(Frame parent, String title, boolean modal) {
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
        this.setTitle("Efectivo Rendido");
        this.setSize(new Dimension(495, 328));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(385, 275, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        scrLista.setBounds(new Rectangle(10, 30, 470, 235));
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 470, 20));
        btnLista.setText("Lista");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblF10.setBounds(new Rectangle(15, 275, 125, 20));
        lblF10.setText("[ Enter ] Ver Detalle");
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(lblF10, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
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
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaEfectivoRendido, ConstantsCajaElectronica.defaultValuesListaEfectivoRendido,
                                    0);
        FarmaUtility.initSimpleList(tblLista, tableModel, ConstantsCajaElectronica.columnsListaEfectivoRendido);
        cargaLista();
    }

    private void cargaLista() {
        try {
            //DBCajaElectronica.listaCuadraturasCierreDia(tableModel,VariablesCajaElectronica.vFechaCierreDia);
            DBCajaElectronica.listaCuadraturasCierreDia_02(tableModel,
                                                           VariablesCajaElectronica.vFechaCierreDia); //ASOSA, 29.04.2010
            FarmaUtility.ordenar(tblLista, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "", btnLista);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(tblLista);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            //UtilityCajaElectronica.getIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia);
            //if(VariablesCajaElectronica.vIndVBCierreDia.equals(FarmaConstants.INDICADOR_N))
            verDetalle();
            //else
            //FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblLista);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            //verDetalle();
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

    private void ingrearDatos() {
        int row = tblLista.getSelectedRow();
        if (row > -1) {
            String tipCuadratura = tblLista.getValueAt(row, 3).toString().trim();
            if (tipCuadratura.equals("03")) {
                VariablesCajaElectronica.vCodCuadratura = tblLista.getValueAt(row, 0).toString().trim();
                VariablesCajaElectronica.vDescCuadratura = tblLista.getValueAt(row, 1).toString().trim();
                DlgDatosEfectivoRendido dlgDatosEfectivoRendido = new DlgDatosEfectivoRendido(myParentFrame, "", true);
                dlgDatosEfectivoRendido.setVisible(true);
            } else if (tipCuadratura.equals("04")) {
                VariablesCajaElectronica.vCodCuadratura = tblLista.getValueAt(row, 0).toString().trim();
                VariablesCajaElectronica.vDescCuadratura = tblLista.getValueAt(row, 1).toString().trim();
                DlgCotizacionCompetencia dlgCotizacionCompetencia =
                    new DlgCotizacionCompetencia(myParentFrame, "", true);
                dlgCotizacionCompetencia.setVisible(true);
            }
        }
    }

    private void verDetalle() {
        int row = tblLista.getSelectedRow();
        if (row > -1) {
            VariablesCajaElectronica.vTipCuadratura = tblLista.getValueAt(row, 3).toString().trim();
            VariablesCajaElectronica.vCodCuadratura = tblLista.getValueAt(row, 0).toString().trim();
            VariablesCajaElectronica.vDescCuadratura = tblLista.getValueAt(row, 1).toString().trim();
            DlgListaEliminacionEfectivoRendido dlgListaEliminacionEfectivoRendido =
                new DlgListaEliminacionEfectivoRendido(myParentFrame, "", true);
            dlgListaEliminacionEfectivoRendido.setVisible(true);
            cargaLista();
        }
    }
}
