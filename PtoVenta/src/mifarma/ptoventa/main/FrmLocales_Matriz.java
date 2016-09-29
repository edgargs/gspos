package mifarma.ptoventa.main;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : FrmLocales_Matriz.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      04.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FrmLocales_Matriz extends JFrame {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(FrmLocales_Matriz.class);
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrLocales = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JButtonLabel btnListaLocales = new JButtonLabel();
    private JTable tblListaLocales = new JTable();
    private JLabelFunction lblFuncion1 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public FrmLocales_Matriz() {
        try {
            cargaVariablesBD();
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
        this.setSize(new Dimension(346, 348));
        this.setTitle("Locales");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 320, 20));
        scrLocales.setBounds(new Rectangle(10, 30, 320, 225));
        tblListaLocales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLocales_keyPressed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(180, 265, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(60, 265, 110, 20));
        lblF11.setText("[ F11 ] Aceptar");
        btnListaLocales.setText("Lista Locales");
        btnListaLocales.setBounds(new Rectangle(5, 0, 95, 20));
        btnListaLocales.setMnemonic('L');
        btnListaLocales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnListaLocales_keyPressed(e);
            }
        });
        btnListaLocales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaLocales_actionPerformed(e);
            }
        });
        lblFuncion1.setBounds(new Rectangle(115, 295, 110, 20));
        lblFuncion1.setText("[ F1] Pedidos");
        jContentPane.add(lblFuncion1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        scrLocales.getViewport().add(tblListaLocales, null);
        jContentPane.add(scrLocales, null);
        pnlTitle1.add(btnListaLocales, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        FarmaVariables.vEconoFar_Matriz = true;
        muestraLogin();
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsPtoVenta.columnsListaMaestros, ConstantsPtoVenta.defaultValuesListaMaestros,
                                    0);
        FarmaUtility.initSimpleList(tblListaLocales, tableModel, ConstantsPtoVenta.columnsListaMaestros);
        cargaListaLocales();
    }

    private void cargaListaLocales() {
        try {
            DBPtoVenta.cargaListaMaestros(tableModel, ConstantsPtoVenta.LISTA_LOCAL);
            FarmaUtility.ordenar(tblListaLocales, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar los locales : \n" +
                    sql.getMessage(), btnListaLocales);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnListaLocales);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblListaLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnListaLocales_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaLocales);
    }

    private void btnListaLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaLocales, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            funcionF11();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        /**
        * Nueva Función
        * Autor:   Luis Reque Orellana
        * Fecha:   13/02/2007
        * */
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            funcionF1();
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

    void muestraLogin() {
        //if ( readFileProperties() )
        if (!validaIpPc()) {
            FarmaUtility.showMessage(this, "Error al obtener la IP de la PC.", null);
            System.exit(0);
        }
        log.debug("EN MATRIZ");
        DlgLogin dlgLogin = new DlgLogin(this, "Acceso Ptoventa - Matriz", true);
        dlgLogin.setVisible(true);
        if (!FarmaVariables.vAceptar) {
            FarmaConnection.closeConnection();
            System.exit(0);
        }
    }

    private boolean validaIpPc() {
        FarmaVariables.vIpPc = FarmaUtility.getHostAddress();
        if (FarmaVariables.vIpPc.trim().length() == 0)
            return false;
        return true;
    }

    private void cargaVariablesBD() {
        //    FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;
        //FarmaVariables.vClaveBD = ConstantsPtoVenta.CLAVE_BD;
        //FarmaVariables.vSID = ConstantsPtoVenta.SID;
        FarmaVariables.vPUERTO = ConstantsPtoVenta.PUERTO;
    }

    private void funcionF11() {
        cargaLocal();
        //this.setVisible(false);
        FrmEconoFar frame = new FrmEconoFar();
        FarmaUtility.centrarVentana(frame);
        frame.setVisible(true);
        //this.dispose();
    }

    /**Nuevo método para mostrar el Frame
     * Autor:   Luis Reque
     * Fecha:   13/02/2007
     * */
    private void funcionF1() {
        FrmMatriz frame = new FrmMatriz();
        FarmaUtility.centrarVentana(frame);
        frame.setVisible(true);
    }

    private void cargaLocal() {
        FarmaVariables.vCodLocal = tblListaLocales.getValueAt(tblListaLocales.getSelectedRow(), 0).toString().trim();
        log.debug("FarmaVariables.vCodLocal=" + FarmaVariables.vCodLocal);
    }
}
