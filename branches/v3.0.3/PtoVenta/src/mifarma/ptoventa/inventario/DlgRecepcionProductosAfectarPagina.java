package mifarma.ptoventa.inventario;


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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRecepcionProductosAfectarPagina extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRecepcionProductosAfectarPagina.class);

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();

    private JButtonLabel btnNumeroPagina = new JButtonLabel();

    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JComboBox cmbPagina = new JComboBox();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgRecepcionProductosAfectarPagina() {
        this(null, "", false);
    }

    public DlgRecepcionProductosAfectarPagina(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(305, 135));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Afectar Pagina");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        btnNumeroPagina.setText("Número de Página:");
        btnNumeroPagina.setBounds(new Rectangle(20, 20, 105, 15));
        btnNumeroPagina.setMnemonic('N');
        btnNumeroPagina.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNumeroPagina_actionPerformed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(200, 80, 90, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(90, 80, 100, 20));
        pnlTitle1.setBounds(new Rectangle(10, 10, 280, 65));
        cmbPagina.setBounds(new Rectangle(140, 20, 105, 20));
        cmbPagina.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbPagina_keyPressed(e);
            }
        });
        pnlTitle1.add(cmbPagina, null);
        pnlTitle1.add(btnNumeroPagina, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;
        initCombo();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initCombo() {
        cmbPagina.removeAllItems();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(VariablesInventario.vNumNotaEs.trim());
        FarmaLoadCVL.loadCVLFromSP(this.cmbPagina, "cmbPagina", "PTOVENTA_INV.INV_ONTIENE_PAGINAS_GUIA(?,?,?)",
                                   parametros, false, true);

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void btnNumeroPagina_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbPagina);
    }

    private void txtNumeroPagina_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbPagina);
    }

    private void cmbPagina_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidados())
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Esta seguro que desea afectar la página?")) {
                    VariablesInventario.vNumPag = cmbPagina.getSelectedItem().toString().trim();
                    cerrarVentana(true);
                }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean datosValidados() {
        boolean rpta = true;
        if (cmbPagina.getSelectedItem().toString().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "Debe seleccionar una página", cmbPagina);
        }
        return rpta;
    }

}
