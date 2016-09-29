package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgFiltroDetalleVentas extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgFiltroDetalleVentas.class);

    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JComboBox cmbCampo = new JComboBox();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();

    public DlgFiltroDetalleVentas() {
        this(null, "", false);
    }

    public DlgFiltroDetalleVentas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgFiltroDetalleVentas(Frame parent, String title, boolean modal, String nameHasTable, String[] codigo,
                                  String[] nombre) {
        super(parent, title, modal);
        myParentFrame = parent;
        VariablesReporte.vNombreInHashtable = nameHasTable;
        try {
            jbInit();
            initializedias(codigo, nombre);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(406, 143));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Filtrar");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(5, 15, 380, 60));
        jPanelTitle1.setBackground(Color.white);
        jPanelTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jButtonLabel1.setText("Seleccione la opcion");
        jButtonLabel1.setBounds(new Rectangle(15, 15, 135, 20));
        jButtonLabel1.setBackground(new Color(255, 130, 14));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('c');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        cmbCampo.setBounds(new Rectangle(145, 15, 170, 20));
        cmbCampo.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                cmbCampo_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                cmbCampo_keyPressed(e);
            }
        });
        jLabelFunction2.setBounds(new Rectangle(280, 85, 105, 25));
        jLabelFunction2.setText("[ ESC ] Escape");
        jLabelFunction3.setBounds(new Rectangle(170, 85, 100, 25));
        jLabelFunction3.setText("[ ENTER ] Elegir");
        jPanelTitle1.add(jButtonLabel1, null);
        jPanelTitle1.add(cmbCampo, null);
        jPanelWhite1.add(jLabelFunction3, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(jLabelFunction2, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        cargacombos();
    }

    private void initializedias(String[] codigo, String[] nombre) {
        FarmaVariables.vAceptar = false;
        cargacombosdias(codigo, nombre);
    }

    private void cargacombos() {
        //FarmaLoadCVL.loadCVLfromArrays(cmbCampo,
        //                                VariablesReporte.vNombreInHashtable,
        //                                VariablesReporte.IND_VALOR_FILTRO,
        //                                VariablesReporte.IND_DESC_FILTRO,
        //                                true);

        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cmbCampo, VariablesReporte.vNombreInHashtable,
                                   "PTOVENTA_INV.INV_GET_TIPO_DOCUMENTO(?)", parametros, true);
        parametros = null;
    }

    private void cargacombosdias(String[] codigo, String[] nombre) {
        FarmaLoadCVL.loadCVLfromArrays(cmbCampo, VariablesReporte.vNombreInHashtable, codigo, nombre, true);
    }

    private void cmbOrden_keyPressed(KeyEvent e) {
    }

    private void cmbOrden_keyReleased(KeyEvent e) {
    }

    private void jButtonLabel2_actionPerformed(ActionEvent e) {
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
    }

    private void cmbCampo_keyPressed(KeyEvent e) {
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbCampo);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesReporte.vCampoFiltro =
                    FarmaLoadCVL.getCVLCode(VariablesReporte.vNombreInHashtable, cmbCampo.getSelectedIndex());
            log.debug(VariablesReporte.vCampoFiltro);
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cmbCampo_keyReleased(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
}
