package mifarma.ptoventa.convenioBTLMF;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgOpcionConvenio.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez Calderon<br>
 * @version 1.0<br>
 *
 */

public class DlgTipoConvenio extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgTipoConvenio.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout(); //  @jve:decl-index=0:

    private JPanelTitle pnlDatosConvenio = new JPanelTitle();


    private JLabel jLabel2 = new JLabel();

    private JLabelFunction btnContado = null;

    private JLabelFunction btnCredito = null;

    private String vTipoConvenio = "";

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgTipoConvenio() {
        this(null, "", false);
    }

    public DlgTipoConvenio(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            log.debug("VariablesConvenioBTLMF.vCodConvenio :" + VariablesConvenioBTLMF.vCodConvenio);

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

        btnCredito = new JLabelFunction();
        btnCredito.setBounds(new Rectangle(151, 46, 71, 20));
        btnCredito.setText("Crédito");
        btnCredito.setFont(new Font("SansSerif", 1, 12));

        btnContado = new JLabelFunction();
        btnContado.setBounds(new Rectangle(58, 46, 74, 20));
        btnContado.setText("Contado");
        btnContado.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
        btnContado.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnContado.setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.BLACK));

        this.setSize(new Dimension(279, 137));
        this.setContentPane(jContentPane);
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Convenio Relacionado");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                inicializaFocus();
            }
        });


        jContentPane.setLayout(null);
        pnlDatosConvenio.setBounds(new Rectangle(6, 8, 258, 97));
        pnlDatosConvenio.setBackground(Color.white);
        pnlDatosConvenio.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDatosConvenio.setFocusable(false);
        jLabel2.setText("¿Como va PAGAR?");
        jLabel2.setBounds(new Rectangle(23, 18, 185, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 14));
        pnlDatosConvenio.add(jLabel2, null);
        pnlDatosConvenio.add(btnContado, null);
        pnlDatosConvenio.add(btnCredito, null);
        jContentPane.add(pnlDatosConvenio, null);
        //this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                log.debug("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
                chkKeyPressed(e);
            }
        });
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        VariablesConvenioBTLMF.vAceptar = false;
        vTipoConvenio = ConstantsConvenioBTLMF.FLG_TIP_CONV_CONTADO;
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {

        log.debug("Evento:" + e.getKeyCode());

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            log.debug("Cerramos la ventana");
            VariablesConvenioBTLMF.limpiarVariablesGlobales();
            cerrarVentana(false);
        }

        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            btnContado.setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.BLACK));
            btnCredito.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
            vTipoConvenio = ConstantsConvenioBTLMF.FLG_TIP_CONV_CONTADO;

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            btnCredito.setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.BLACK));
            btnContado.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
            vTipoConvenio = ConstantsConvenioBTLMF.FLG_TIP_CONV_CREDITO;
        }

        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            log.debug("Ingresamos a la patantalla de datos");

            VariablesConvenioBTLMF.vFlgTipoConvenio =
                    UtilityConvenioBTLMF.obtieneTipoConvenio(this, null, VariablesConvenioBTLMF.vCodConvenio);
            log.debug("VariablesConvenioBTLMF.vFlgTipoConvenio :" + VariablesConvenioBTLMF.vFlgTipoConvenio);
            VariablesConvenioBTLMF.vFlgTipoConvenioRel =
                    UtilityConvenioBTLMF.obtieneTipoConvenio(this, null, VariablesConvenioBTLMF.vCodConvenioRel);
            log.debug("VariablesConvenioBTLMF.vFlgTipoConvenioRel :" + VariablesConvenioBTLMF.vFlgTipoConvenioRel);
            log.debug("vTipoConvenio :" + vTipoConvenio);

            if (vTipoConvenio.equals(ConstantsConvenioBTLMF.FLG_TIP_CONV_CONTADO)) {
                if (VariablesConvenioBTLMF.vFlgTipoConvenio.equals(ConstantsConvenioBTLMF.FLG_TIP_CONV_CONTADO)) {
                } else if (VariablesConvenioBTLMF.vFlgTipoConvenioRel.equals(ConstantsConvenioBTLMF.FLG_TIP_CONV_CONTADO)) {
                    log.debug("SELECCIONAMOS AL CONTADO");

                    VariablesConvenioBTLMF.vCodConvenio = VariablesConvenioBTLMF.vCodConvenioRel;
                    VariablesConvenioBTLMF.vNomConvenio = VariablesConvenioBTLMF.vNomConvenioRel;
                    VariablesConvenioBTLMF.vFlgCreacionCliente = VariablesConvenioBTLMF.vFlgCreacionClienteRel;
                    VariablesConvenioBTLMF.vFlgTipoConvenio = VariablesConvenioBTLMF.vFlgTipoConvenioRel;
                }

            }

            if (vTipoConvenio.equals(ConstantsConvenioBTLMF.FLG_TIP_CONV_CREDITO)) {
                if (VariablesConvenioBTLMF.vFlgTipoConvenio.equals(ConstantsConvenioBTLMF.FLG_TIP_CONV_CREDITO)) {
                } else if (VariablesConvenioBTLMF.vFlgTipoConvenioRel.equals(ConstantsConvenioBTLMF.FLG_TIP_CONV_CREDITO)) {
                    log.debug("SELECCIONAMOS AL CREDITO");

                    VariablesConvenioBTLMF.vCodConvenio = VariablesConvenioBTLMF.vCodConvenioRel;
                    VariablesConvenioBTLMF.vNomConvenio = VariablesConvenioBTLMF.vNomConvenioRel;
                    VariablesConvenioBTLMF.vFlgCreacionCliente = VariablesConvenioBTLMF.vFlgCreacionClienteRel;
                    VariablesConvenioBTLMF.vFlgTipoConvenio = VariablesConvenioBTLMF.vFlgTipoConvenioRel;
                }
            }

            cerrarVentana(true);

        }

    }

    private void inicializaFocus() {

        VariablesConvenioBTLMF.vFlgTipoConvenio =
                UtilityConvenioBTLMF.obtieneTipoConvenio(this, null, VariablesConvenioBTLMF.vCodConvenio);
        log.debug("metodo:inicializaFocus" + VariablesConvenioBTLMF.vFlgTipoConvenio);
        if (VariablesConvenioBTLMF.vFlgTipoConvenio.equals(ConstantsConvenioBTLMF.FLG_TIP_CONV_CREDITO)) {

            btnCredito.setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.BLACK));
            btnContado.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
            vTipoConvenio = ConstantsConvenioBTLMF.FLG_TIP_CONV_CREDITO;

        } else {
            btnContado.setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.BLACK));
            btnCredito.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
            vTipoConvenio = ConstantsConvenioBTLMF.FLG_TIP_CONV_CONTADO;

        }

    }

    private void cerrarVentana(boolean pAceptar) {
        VariablesConvenioBTLMF.vAceptar = pAceptar;
        this.setVisible(pAceptar);
        this.dispose();
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"