package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.cajas.reference.ConstantsCajas;
import mifarma.ptoventa.ce.reference.FacadeCajaElectronica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaCajasAperturadas.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      06.02.2014   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @since 2.2.8<br>
 *
 */
public class DlgListaCajasAperturadas extends JDialog {

    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */

    private static final Logger log = LoggerFactory.getLogger(DlgListaCajasAperturadas.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlHeaderListaCajas = new JPanelTitle();
    private JScrollPane scrListaCajas = new JScrollPane();
    private JTable tblListaCajas = new JTable();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblMensaje = new JLabelWhite();

    private FacadeCajaElectronica facadeCajaElectronica = new FacadeCajaElectronica();
    private String vFechaCierreDia = "";
    private String vMensaje;

    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */

    public DlgListaCajasAperturadas() {
        this(null, "", false);
    }

    public DlgListaCajasAperturadas(Frame parent, String title, boolean modal) {
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
    /*                              METODO jbInit                               */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(453, 332));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Cajas Aperturadas");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlHeaderListaCajas.setBounds(new Rectangle(10, 70, 420, 25));
        scrListaCajas.setBounds(new Rectangle(10, 95, 420, 150));
        scrListaCajas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                scrListaCajas_keyPressed(e);
            }
        });
        tblListaCajas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaCajas_keyPressed(e);
            }
        });
        lblSalir.setBounds(new Rectangle(325, 255, 95, 20));
        lblSalir.setText("[ESC] Cerrar");
        jButtonLabel1.setText("Lista de Cajas");
        jButtonLabel1.setBounds(new Rectangle(5, 5, 110, 15));
        jPanelHeader1.setBounds(new Rectangle(10, 10, 420, 55));
        lblMensaje.setText("[Mensaje]");
        lblMensaje.setBounds(new Rectangle(10, 10, 400, 35));
        jPanelHeader1.add(lblMensaje, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblSalir, null);
        scrListaCajas.getViewport().add(tblListaCajas, null);
        jContentPane.add(scrListaCajas, null);
        pnlHeaderListaCajas.add(jButtonLabel1, null);
        jContentPane.add(pnlHeaderListaCajas, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsCajas.columnsListaCajasAperturadas, ConstantsCajas.defaultValuesListaCajasAperturadas,
                                    0);
        FarmaUtility.initSimpleList(tblListaCajas, tableModel, ConstantsCajas.columnsListaCajasAperturadas);
    }

    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        cargaListaCajas();
        muestraMensaje();
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaCajas);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void scrListaCajas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaCajas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */

    private void cargaListaCajas() {
        tableModel.clearTable();
        ArrayList<ArrayList<String>> listaCajas;
        listaCajas = facadeCajaElectronica.getListaCajasAperturadas(vFechaCierreDia);
        tableModel.data = listaCajas;
    }

    void setFechaCierreDia(String pFechaCierreDia) {
        this.vFechaCierreDia = pFechaCierreDia;
    }

    void setMensaje(String pMensaje) {
        this.vMensaje = pMensaje;
    }

    private void muestraMensaje() {
        lblMensaje.setText("<html>" + vMensaje + "</html>");
    }
}
