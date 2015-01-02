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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDatoBenificiarioBTLMF.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez Calderon<br>
 * @version 1.0<br>
 *
 */

public class DlgMensajeCobertura extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMensajeCobertura.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout(); //  @jve:decl-index=0:

    private JPanelTitle pnlDatosConvenio = new JPanelTitle();


    private JLabel jLabel2 = new JLabel();

    private JLabelFunction btnContado = null;

    private JLabelFunction btnCredito = null;

    private JLabel jLabel = null;

    private String opcion = " ";

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMensajeCobertura() {
        this(null, "", false);
    }

    public DlgMensajeCobertura(Frame parent, String title, boolean modal) {
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

        jLabel = new JLabel();
        jLabel.setBounds(new Rectangle(8, 30, 317, 21));
        jLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        jLabel.setBackground(Color.orange);
        jLabel.setForeground(Color.orange);
        jLabel.setText("Producto no esta en cobertura.");
        btnCredito = new JLabelFunction();
        btnCredito.setBounds(new Rectangle(206, 114, 53, 20));
        btnCredito.setText("NO");
        btnCredito.setFont(new Font("SansSerif", 1, 12));

        btnContado = new JLabelFunction();
        btnContado.setBounds(new Rectangle(123, 116, 59, 20));
        btnContado.setText("SI");
        btnContado.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
        btnContado.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnContado.setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.BLACK));

        this.setSize(new Dimension(353, 204));
        this.setContentPane(jContentPane);
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mensaje Cobertura Convenio");

        jContentPane.setLayout(null);
        pnlDatosConvenio.setBounds(new Rectangle(7, 10, 332, 155));
        pnlDatosConvenio.setForeground(Color.orange);
        pnlDatosConvenio.setBackground(Color.white);
        pnlDatosConvenio.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDatosConvenio.setFocusable(false);

        jLabel2.setText("");

        jLabel2.setForeground(Color.orange);
        jLabel2.setBounds(new Rectangle(27, 79, 245, 15));
        jLabel2.setFont(new Font("SansSerif", Font.BOLD, 12));


        pnlDatosConvenio.add(jLabel2, null);
        pnlDatosConvenio.add(btnContado, null);
        pnlDatosConvenio.add(btnCredito, null);
        pnlDatosConvenio.add(jLabel, null);
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
        FarmaVariables.vAceptar = false;

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
            cerrarVentana(false);
        }


        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            log.debug("Ejecutamos la opción");

            if (opcion.equals("N")) {
                cerrarVentana(false);
            } else {
                cerrarVentana(true);
            }

        }
    }

    private void cerrarVentana(boolean pAceptar) {
        VariablesConvenioBTLMF.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"