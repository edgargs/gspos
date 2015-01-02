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


public class DlgFiltroDescripcionMovKardex extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgFiltroDescripcionMovKardex.class);

    Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel lblTelefono = new JButtonLabel();
    private JComboBox cmbDescripcion = new JComboBox();
    private BorderLayout borderLayout1 = new BorderLayout();

    public DlgFiltroDescripcionMovKardex() {
        this(null, "", false);
    }

    public DlgFiltroDescripcionMovKardex(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(362, 124));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Filtro Movimiento de Kardex");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        lblEsc.setBounds(new Rectangle(260, 70, 85, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(155, 70, 95, 20));
        lblF11.setText("[ F11 ] Aceptar");
        pnlTitle1.setBounds(new Rectangle(10, 10, 335, 50));
        lblTelefono.setText("Descripcion");
        lblTelefono.setBounds(new Rectangle(5, 20, 75, 15));
        lblTelefono.setMnemonic('D');
        lblTelefono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblTelefono_actionPerformed(e);
            }
        });
        cmbDescripcion.setBounds(new Rectangle(80, 15, 250, 20));
        cmbDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbDescripcion_keyPressed(e);
            }
        });
        pnlTitle1.add(lblTelefono, null);
        pnlTitle1.add(cmbDescripcion, null);
        jPanelWhite1.add(pnlTitle1, null);
        jPanelWhite1.add(lblF11, null);
        jPanelWhite1.add(lblEsc, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
    }

    private void initialize() {
        initCmbDescripcion();
    };

    private void lblTelefono_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbDescripcion);
    }


    private void txtTelefono_keyPressed(KeyEvent e) {
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbDescripcion);
    }

    private void initCmbDescripcion() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cmbDescripcion, "cmbDescripcion", "PTOVENTA_INV.INV_LISTA_MOTIVOS_KRDX(?)",
                                   parametros, true);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            VariablesInventario.vCodFiltro =
                    FarmaLoadCVL.getCVLCode("cmbDescripcion", cmbDescripcion.getSelectedIndex());
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cmbDescripcion_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
