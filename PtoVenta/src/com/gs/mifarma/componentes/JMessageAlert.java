package com.gs.mifarma.componentes;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.main.FrmEconoFar;
import mifarma.ptoventa.administracion.impresoras.reference.ImpresoraTicket;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Mensajes de alerta
 * @author ERIOS
 * @since 2.3.3
 */
public class JMessageAlert extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(JMessageAlert.class);
    private boolean pIndClose = false;
    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanel pnlMensaje = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextPane jtaMsjError = new JTextPane();
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JTextPane jTextPane1 = new JTextPane();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    //private Icon optionIcon = UIManager.getIcon("OptionPane.warningIcon");
    private ImageIcon imagen = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/alerta.jpg"));
    private JLabel dialogIcon = new JLabel(imagen);
    private JPanel jPanel4 = new JPanel();
    private BorderLayout borderLayout4 = new BorderLayout();
    
    private static JMessageAlert msg = null;

    private JMessageAlert(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(585, 320));
        this.getContentPane().setLayout(cardLayout1);
        //this.setResizable(false);
        this.setTitle("[Titulo]");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.setFocusable(false);
        pnlFondo.setLayout(borderLayout2);
        lblF11.setText("[ F11 ] Continuar");
        lblF11.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblF11_keyPressed(e);
            }
        });
        pnlMensaje.setLayout(borderLayout1);
        pnlMensaje.setBackground(Color.red);
        pnlMensaje.setFocusable(false);
        jLabel1.setText("[CABECERA]");
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setFont(new Font("Microsoft Sans Serif", 1, 30));
        jLabel1.setForeground(Color.white);


        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jScrollPane1_keyPressed(e);
            }
        });
        jtaMsjError.setFont(new Font("Tahoma", 1, 15));
        jtaMsjError.setEditable(false);
        jtaMsjError.setForeground(Color.white);
        jtaMsjError.setBackground(Color.red);
        jtaMsjError.setText("[MENSAJE]");
        jtaMsjError.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jtaMsjError_keyPressed(e);
            }
        });
        jPanel1.setLayout(borderLayout3);
        jPanel1.setOpaque(false);
        jTextPane1.setEditable(false);
        jTextPane1.setText("[Pie]");
        jTextPane1.setFont(new Font("SansSerif", 1, 14));
        jTextPane1.setForeground(Color.white);
        jTextPane1.setOpaque(false);

        jPanel3.setOpaque(false);
        jPanel4.setLayout(borderLayout4);
        jPanel4.setBackground(Color.red);
        jPanel1.add(jTextPane1, BorderLayout.CENTER);
        pnlMensaje.add(jPanel1, BorderLayout.SOUTH);
        pnlMensaje.add(jtaMsjError, BorderLayout.CENTER);
        jPanel4.add(jLabel1, BorderLayout.CENTER);
        pnlMensaje.add(jPanel4, BorderLayout.NORTH);
        jPanel3.add(dialogIcon, null);
        pnlMensaje.add(jPanel3, BorderLayout.EAST);
        jPanel2.add(lblF11, null);
        pnlFondo.add(jPanel2, BorderLayout.SOUTH);
        pnlFondo.add(pnlMensaje, BorderLayout.CENTER);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void setMensaje(String msje) {
        
        jtaMsjError.setText(msje);

    }


    private void lblF11_keyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e))
            cerrarVentana(true);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean isOpen() {
        return this.isVisible();
    }

    private void this_windowOpened(WindowEvent e) {
        lblF11.grabFocus();
        if (pIndClose) {
            log.debug("Indicador close automatico");
            Thread hilo = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(10 * 1000);
                        if (isOpen()) {
                            cerrarVentana(true);
                        }
                    } catch (InterruptedException e) {
                        log.error("",e);
                    }
                }
            };
            log.debug("Inicia espera");
            hilo.start();
        }
    }

    private void jtaMsjError_keyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e))
            cerrarVentana(true);
    }

    private void jScrollPane1_keyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e))
            cerrarVentana(true);
    }

    private void setTitulo(String pTitulo) {
        jLabel1.setText(pTitulo);
    }

    private void setPie(String pie1) {
        jTextPane1.setText(pie1);
    }

    private void setIndCloseOut(boolean pValor) {
        pIndClose = pValor;
    }

    public static void showMessage(Frame pJDialog, String pTitulo, String pCabecera, String pMensaje, String pPie,
                                   boolean pIndCloseTimeOut){
       showMessage(pJDialog, pTitulo, pCabecera, pMensaje, pPie,
                                          pIndCloseTimeOut, Color.RED);
   }
    
    public static void showMessage(Frame pJDialog, String pTitulo, String pCabecera, String pMensaje, String pPie,
                                   boolean pIndCloseTimeOut, Color pColorPanel) {
        //JMessageAlert msg = new JMessageAlert(pJDialog, "", true);
        JMessageAlert msg = obtieneSingleton(pJDialog);
        msg.setTitle(pTitulo);
        //contiene el @ como separador
        String pMensajeNvo = "";
        if ((pMensaje.indexOf("@")) != -1) {
            String[] lista = pMensaje.split("@");
            for (int i = 0; i < lista.length; i++){
                pMensajeNvo = pMensajeNvo + "     " + lista[i].toString().trim() + "\n";
            }
        } else{
            pMensajeNvo = pMensaje;
        }
        
        msg.setTitulo(pCabecera);
        msg.setMensaje(pMensajeNvo);
        log.warn(pMensajeNvo);
        msg.setPie(pPie);        
        msg.setIndCloseOut(pIndCloseTimeOut);
        msg.setColorPanel(pColorPanel);
        log.debug("Color elegido:"+pColorPanel);
        msg.pack();
        msg.setLocationRelativeTo(null);
        log.info("Muestra alerta.");
        msg.setVisible(true);
    }

    public static void main(String[] args) {
        //JMessageAlert.showMessage(null,"Mensaje","ALERTA","MIRAME","Tu papa");
        String strTitulo = ImpresoraTicket.centrarLinea("MENSAJE DE ENTEL:", " ", 40);
        JMessageAlert.showMessage(null, "Recarga Virtual", //"MENSAJE DE ENTEL:",
                                  strTitulo,
                                  "@aaa@bbb@vv",
                                  "Si se realizo el pago con tarjeta, es obligatorio realizar" + "\n" +
                "la anulación de la transacción", false,Color.GRAY);
    }

    private void setColorPanel(Color pColorPanel) {
        this.pnlMensaje.setBackground(pColorPanel);
        this.jtaMsjError.setBackground(pColorPanel);
        this.jPanel4.setBackground(pColorPanel);
    }

    private static JMessageAlert obtieneSingleton(Frame pJDialog) {
        if(msg == null){
            msg = new JMessageAlert(pJDialog, "", true);
        }
        return msg;
    }
}
