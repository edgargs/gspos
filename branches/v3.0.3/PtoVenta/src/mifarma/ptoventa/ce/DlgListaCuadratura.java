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

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaCuadratura.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      31.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaCuadratura extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private static final Logger log = LoggerFactory.getLogger(DlgListaCuadratura.class);

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JTable tblLista = new JTable();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();

    private JLabelFunction lblEnter = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaCuadratura() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgListaCuadratura(Frame parent, String title, boolean modal) {
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
        this.setTitle("Pendientes");
        this.setSize(new Dimension(560, 306));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(445, 245, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(330, 245, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");

        lblEnter.setBounds(new Rectangle(10, 245, 155, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");

        scrLista.setBounds(new Rectangle(10, 30, 530, 205));
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 530, 20));
        btnLista.setText("Lista Pendientes");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        btnLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnLista_keyPressed(e);
            }
        });
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        VariablesCajaElectronica.vArrayListInsertar.clear();
        this.setTitle("Lista Ingreso - " + VariablesCajaElectronica.vDescCuadratura);
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaCuadraturasIngreso, ConstantsCajaElectronica.defaultListaCuadraturasIngreso,
                                    0);
        FarmaUtility.initSelectList(tblLista, tableModel, ConstantsCajaElectronica.columsListaCuadraturasIngreso);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        if (tblLista.getRowCount() > 0)
            FarmaUtility.moveFocus(tblLista);
        FarmaUtility.centrarVentana(this);
        cargaLista();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblLista);
    }

    private void btnLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            verificaCheckJTable();
        }
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblLista);
                return;
            }
            UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,
                                                  ConstantsCajaElectronica.TIPO_VB_CAJERO);
            if (VariablesCajaElectronica.vUsuarioCajero &&
                VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N))
                funcionInsertar();
            else
                FarmaUtility.showMessage(this, "No es posible realizar esta operación.", tblLista);
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

    private void funcionInsertar() {
        boolean funcionAgrega = false;
        try {
            for (int i = 0; i < VariablesCajaElectronica.vArrayListInsertar.size(); i++) {
                VariablesCajaElectronica.vNumSerie =
                        (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertar.get(i)).get(0));
                VariablesCajaElectronica.vTipComp =
                        (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertar.get(i)).get(1));
                VariablesCajaElectronica.vNumComp =
                        (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertar.get(i)).get(2));
                VariablesCajaElectronica.vMontMoneda =
                        (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertar.get(i)).get(3));
                VariablesCajaElectronica.vMontTotal =
                        (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertar.get(i)).get(4));
                VariablesCajaElectronica.vMontVuelto =
                        (String)(((ArrayList)VariablesCajaElectronica.vArrayListInsertar.get(i)).get(5));
                insertaCuadraturaLista();
                funcionAgrega = true;
            }
            if (funcionAgrega) {
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Comprobante(s) pendiente(s) registrado(s) correctamente", tblLista);
                cargaLista();
            }
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al grabar la información \n" +
                    sql.getMessage(), null);
            return;
        }
    }

    private void cargaLista() {
        try {
            DBCajaElectronica.cargaListaIngresoCuadraturas(tableModel, VariablesCajaElectronica.vSecMovCaja,
                                                           VariablesCajaElectronica.vCodCuadratura);
            VariablesCajaElectronica.vArrayListInsertar.clear();
            if (tblLista.getRowCount() > 0) {
                FarmaUtility.ordenar(tblLista, tableModel, 1, FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocusJTable(tblLista);
            } else {
                FarmaUtility.showMessage(this, "No existen Datos a Mostrar", tblLista);
                cerrarVentana(true);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar \n" +
                    sql.getMessage(), null);
        }
    }

    private void insertaCuadraturaLista() throws SQLException {
        DBCajaElectronica.agregaCuadraturaCajaLista(VariablesCajaElectronica.vSecMovCaja,
                                                    VariablesCajaElectronica.vCodCuadratura,
                                                    VariablesCajaElectronica.vNumSerie,
                                                    VariablesCajaElectronica.vTipComp,
                                                    VariablesCajaElectronica.vNumComp,
                                                    VariablesCajaElectronica.vMontMoneda,
                                                    VariablesCajaElectronica.vMontTotal,
                                                    VariablesCajaElectronica.vMontVuelto);
    }

    private void cargaVariablesInsertar() {
        VariablesCajaElectronica.vNumSerie = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 8)).trim();
        VariablesCajaElectronica.vTipComp = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 7)).trim();
        VariablesCajaElectronica.vNumComp = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 9)).trim();
        VariablesCajaElectronica.vMontMoneda = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 4)).trim();
        VariablesCajaElectronica.vMontTotal = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 5)).trim();
        VariablesCajaElectronica.vMontVuelto = ((String)tblLista.getValueAt(tblLista.getSelectedRow(), 6)).trim();
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void verificaCheckJTable() {
        cargaVariablesInsertar();
        Boolean valor = (Boolean)(tblLista.getValueAt(tblLista.getSelectedRow(), 0));
        seleccionaDeseleccionaRegistro();
    }

    private void seleccionaDeseleccionaRegistro() {
        Boolean valorTmp = (Boolean)(tblLista.getValueAt(tblLista.getSelectedRow(), 0));
        FarmaUtility.setCheckValue(tblLista, false);
        Boolean valor = (Boolean)(tblLista.getValueAt(tblLista.getSelectedRow(), 0));
        operaRegistroSeleccionadoEnArrayList(valor);
    }

    private void operaRegistroSeleccionadoEnArrayList(Boolean valor) {
        ArrayList myArray = new ArrayList();
        myArray.add(VariablesCajaElectronica.vNumSerie);
        myArray.add(VariablesCajaElectronica.vTipComp);
        myArray.add(VariablesCajaElectronica.vNumComp);
        myArray.add(VariablesCajaElectronica.vMontMoneda);
        myArray.add(VariablesCajaElectronica.vMontTotal);
        myArray.add(VariablesCajaElectronica.vMontVuelto);
        myArray.add(VariablesCajaElectronica.vNumSerie + VariablesCajaElectronica.vNumComp +
                    VariablesCajaElectronica.vTipComp);
        FarmaUtility.operaListaProd(VariablesCajaElectronica.vArrayListInsertar, myArray, valor, 5);
        log.debug("size : " + VariablesCajaElectronica.vArrayListInsertar.size());
        log.debug("array : " + VariablesCajaElectronica.vArrayListInsertar);
    }
}
