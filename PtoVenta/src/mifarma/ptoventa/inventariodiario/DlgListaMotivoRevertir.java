package mifarma.ptoventa.inventariodiario;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventariodiario.reference.VariablesInvDiario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaMotivoRevertir extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaMotivoRevertir.class);

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel jContentPane = new JPanel();

    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();

    private JPanel jPanel2 = new JPanel();


    private JPanel pnlHeaderDatos = new JPanel();

    private JLabel lblMotivo = new JLabel();


    private JLabel lblTitulo = new JLabel();

    private JComboBox cmbMotivo = new JComboBox();

    //JCORTEZ 15.04.09
    private boolean bValidaCompr = false;

    //JCORTEZ 18.05.09
    private static String vTipMovCajaAux = "";

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaMotivoRevertir() {
        this(null, "", false);
    }

    public DlgListaMotivoRevertir(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(385, 167));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Motivo Revertir");
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBounds(new Rectangle(5, 5, 380, 255));
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(382, 195));
        jContentPane.setLayout(null);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(285, 110, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(170, 110, 100, 20));
        jPanel2.setBounds(new Rectangle(5, 45, 365, 60));
        jPanel2.setBackground(Color.white);
        jPanel2.setBorder(BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(null);
        pnlHeaderDatos.setBounds(new Rectangle(5, 10, 365, 35));
        pnlHeaderDatos.setBackground(new Color(255, 130, 14));
        pnlHeaderDatos.setLayout(null);
        lblTitulo.setText("Lista Motivo Revertir");
        lblTitulo.setBounds(new Rectangle(10, 5, 160, 20));
        lblTitulo.setFont(new Font("SansSerif", 1, 11));
        lblTitulo.setForeground(Color.white);

        // lblTurno_T.setForeground(new Color(255, 130, 14));


        cmbMotivo.setBounds(new Rectangle(155, 215, 95, 20));
        lblMotivo.setText("Motivo");
        lblMotivo.setBounds(new Rectangle(35, 15, 45, 20));
        lblMotivo.setFont(new Font("SansSerif", 1, 12));
        lblMotivo.setForeground(new Color(255, 130, 14));


        cmbMotivo.setFont(new Font("SansSerif", 1, 12));


        cmbMotivo.setBounds(new Rectangle(90, 15, 205, 20));


        //lblCaja_TT.setForeground(Color.black);


        cmbMotivo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbMotivo_keyPressed(e);
            }
        });
        jPanel2.add(cmbMotivo, null);
        jPanel2.add(lblMotivo, null);
        pnlHeaderDatos.add(lblTitulo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jPanel2, null);
        jContentPane.add(pnlHeaderDatos, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initComboBoleta();
        FarmaVariables.vAceptar = false;
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initComboBoleta() {
        cmbMotivo.removeAllItems();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(VariablesInvDiario.vAccion);
        FarmaLoadCVL.loadCVLFromSP(this.cmbMotivo, "cmbMotivo", "PTOVENTA_TOMA_DIA.TI_F_CUR_LITA_MOTIVOS(?,?,?)",
                                   parametros, true, true);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        if (VariablesInvDiario.vAccion.trim().equalsIgnoreCase("A")) {
            lblTitulo.setText("Lista Motivo Ajuste");
            this.setTitle("Motivo Ajuste");
        } else if (VariablesInvDiario.vAccion.trim().equalsIgnoreCase("R")) {
            lblTitulo.setText("Lista Motivo Revertir");
            this.setTitle("Motivo Revertir");
        }

        lblMotivo.setVisible(true);
        cmbMotivo.setEditable(false);
        cmbMotivo.setVisible(true);
        FarmaUtility.moveFocus(cmbMotivo);
    }

    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFactura_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        } else
            chkKeyPressed(e);


    }
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            log.debug("F11");
            obtieneMotivo();
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

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void obtieneMotivo() {

        //Revertir
        VariablesInvDiario.vCodMotivoRevertir = //cmbMotivo.getSelectedItem().toString().trim();
                FarmaLoadCVL.getCVLCode("cmbMotivo", cmbMotivo.getSelectedIndex()) + "";
        VariablesInvDiario.vCodMotivoRevertir = VariablesInvDiario.vCodMotivoRevertir.trim();

        //Ajuste
        VariablesInvDiario.vCodMotivoAjuste = FarmaLoadCVL.getCVLCode("cmbMotivo", cmbMotivo.getSelectedIndex()) + "";
        VariablesInvDiario.vCodMotivoAjuste = VariablesInvDiario.vCodMotivoAjuste.trim();
        VariablesInvDiario.vDescMotivoAjuste =
                FarmaLoadCVL.getCVLDescription("cmbMotivo", VariablesInvDiario.vCodMotivoAjuste);

        log.debug("VariablesInvDiario.vCodMotivoRevertir: " + VariablesInvDiario.vCodMotivoRevertir);
        log.debug("VariablesInvDiario.vCodMotivoAjuste: " + VariablesInvDiario.vCodMotivoAjuste);
        cerrarVentana(true);
    }

    private void cmbMotivo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
