package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgPrincAct.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      29.09.2010   Creación
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */

public class DlgPrincAct extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgPrincAct.class);

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JScrollPane srcLista = new JScrollPane();
    private JTable tblLista = new JTable();

    private Frame myParentFrame;
    private FarmaTableModel tableModelPrincAct;
    private String codprod;

    public DlgPrincAct() {
        this(null, "", false);
    }

    public DlgPrincAct(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgPrincAct(Frame parent, String title, boolean modal, String codprod) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.codprod = codprod;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(345, 176));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setBounds(new Rectangle(0, 0, 340, 150));
        jPanelWhite1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jPanelWhite1_keyPressed(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(15, 15, 300, 25));
        jPanelHeader1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jPanelHeader1_keyPressed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(20, 10, 300, 25));
        jPanelTitle1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jPanelTitle1_keyPressed(e);
            }
        });
        jLabelWhite1.setText("Principio activo");
        jLabelWhite1.setBounds(new Rectangle(40, 5, 100, 15));
        jLabelWhite1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelWhite1_keyPressed(e);
            }
        });
        srcLista.setBounds(new Rectangle(15, 45, 310, 90));
        srcLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                srcLista_keyPressed(e);
            }
        });
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        jPanelHeader1.add(jLabelWhite1, null);
        srcLista.getViewport().add(tblLista, null);
        jPanelWhite1.add(srcLista, null);
        jPanelWhite1.add(jPanelHeader1, null);
        jPanelWhite1.add(jPanelTitle1, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        initTableListaPrincAct();
    }

    private void initTableListaPrincAct() {
        tableModelPrincAct =
                new FarmaTableModel(ConstantsVentas.columnsListPrincAct, ConstantsVentas.defaultValuesListPrincAct, 0);
        FarmaUtility.initSimpleList(tblLista, tableModelPrincAct, ConstantsVentas.columnsListPrincAct);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(tblLista);
        FarmaUtility.centrarVentana(this);
        listaPrincAct();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void listaPrincAct() {
        try {
            DBVentas.getListaPrincAct(tableModelPrincAct, this.codprod);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "ERROR en listar Principios Activos: \n" +
                    e.getMessage(), tblLista);
        }
        if (tableModelPrincAct.getRowCount() <= 0) {
            FarmaUtility.showMessage(this, "No se encontraron resultado para la búsqueda", tblLista);
        }
    }

    private void srcLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jPanelHeader1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jLabelWhite1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jPanelTitle1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jPanelWhite1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
