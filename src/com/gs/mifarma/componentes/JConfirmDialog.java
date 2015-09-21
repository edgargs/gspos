package com.gs.mifarma.componentes;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Dialogo de confirmacion
 * @author ERIOS
 * @since 09.07.2013
 */
public class JConfirmDialog extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(JConfirmDialog.class);

    private JPanel jPanel1 = new JPanel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private String mensaje;
    private int vRetorno;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JTextPane jTextPane1 = new JTextPane();
    private Icon optionIcon = UIManager.getIcon("OptionPane.questionIcon");
    private JLabel dialogIcon = new JLabel(optionIcon);
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();

    public JConfirmDialog(Dialog dialog, String string, boolean b) {
        super(dialog, string, b);
        mensaje = string;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public JConfirmDialog(Frame dialog, String string, boolean b) {
        super(dialog, string, b);
        mensaje = string;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(326, 143));
        this.setDefaultCloseOperation(0);
        this.setTitle("Mensaje del Sistema");
        jPanel1.setLayout(borderLayout1);
        jButton1.setText("S�");
        jButton1.setMnemonic('s');
        jButton1.setFont(new Font("Dialog", 1, 12));
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });
        jButton1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButton1_keyPressed(e);
            }
        });
        jButton1.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                jButton_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                jButton_focusLost(e);
            }
        });
        jButton2.setText("No");
        jButton2.setMnemonic('n');
        jButton2.setFont(new Font("Dialog", 1, 12));
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton2_actionPerformed(e);
            }
        });
        jButton2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButton2_keyPressed(e);
            }
        });
        jButton2.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                jButton_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                jButton_focusLost(e);
            }
        });
        jTextPane1.setFont(new Font("Dialog", 1, 12));
        jTextPane1.setOpaque(false);
        jTextPane1.setEditable(false);
        jTextPane1.setFocusable(false);
        jTextPane1.setText(mensaje);
        jLabel3.setText(" ");
        jLabel3.setFont(new Font("Dialog", 1, 4));
        jPanel3.add(jTextPane1, null);
        jPanel4.add(jLabel4, null);
        jPanel4.add(dialogIcon, null);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel1.add(jPanel4, BorderLayout.WEST);
        jPanel1.add(jLabel3, BorderLayout.NORTH);
        jPanel2.add(jButton1, null);
        jPanel2.add(jButton2, null);
        this.getContentPane().add(jPanel1, null);
    }

    int getValue(boolean defaultButton) {
        this.pack();
        this.setLocationRelativeTo(null);
        if (defaultButton) {
            jButton2.requestFocusInWindow();
        } else {
            jButton1.requestFocusInWindow();
        }
        this.setVisible(true);

        return vRetorno;
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        opcionNO();
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        opcionSI();
    }

    public static boolean rptaConfirmDialogDefaultNo(Object pJDialog, String pMensaje, String... pNombreBotones) {
        return rptaConfirmDialog(pJDialog, pMensaje, true, pNombreBotones);
    }

    public static boolean rptaConfirmDialog(Object pJDialog, String pMensaje, String... pNombreBotones) {
        return rptaConfirmDialog(pJDialog, pMensaje, false, pNombreBotones);
    }

    private static boolean rptaConfirmDialog(Object pJDialog, String pMensaje, boolean pValue, String... pNombreBotones) {
        log.warn(pMensaje);
        JConfirmDialog optioPane = null;
        if (pJDialog instanceof JDialog) {
            JDialog myJDialog = (JDialog)pJDialog;
            optioPane = new JConfirmDialog(myJDialog, pMensaje, true);
        } else if (pJDialog instanceof Frame) {
            Frame myFrame = (Frame)pJDialog;
            optioPane = new JConfirmDialog(myFrame, pMensaje, true);
        } else {
            optioPane = new JConfirmDialog(new JDialog(), pMensaje, true);
        }
        //ERIOS 25.06.2015 Nombre de botones
        if(pNombreBotones != null){
            if(pNombreBotones.length >=1 ){
            optioPane.setBoton1(pNombreBotones[0],pNombreBotones[0].charAt(0));
            }
            if(pNombreBotones.length >=2 ){
            optioPane.setBoton2(pNombreBotones[1],pNombreBotones[1].charAt(0));
            }
        }
            
        int rptaDialogo = optioPane.getValue(pValue);
        boolean bRetorno;
        if (rptaDialogo == JOptionPane.YES_OPTION) {
            bRetorno = true;
        } else {
            bRetorno = false;
        }
        log.warn("rptaDialogo:" + bRetorno);
        return bRetorno;
    }

    private void jButton2_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            opcionNO();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            jButton1.requestFocus();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            opcionNO();

    }

    private void opcionNO() {
        vRetorno = JOptionPane.NO_OPTION;
        this.setVisible(false);
        this.dispose();
    }

    private void jButton1_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            opcionSI();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            jButton2.requestFocus();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            opcionNO();
    }

    private void opcionSI() {
        vRetorno = JOptionPane.YES_OPTION;
        this.setVisible(false);
        this.dispose();
    }

    private void jButton_focusGained(FocusEvent e) {
        ((JButton)e.getSource()).setBackground(new Color(255, 130, 40));
        ((JButton)e.getSource()).setForeground(Color.WHITE);
    }

    private void jButton_focusLost(FocusEvent e) {
        ((JButton)e.getSource()).setBackground(new Color(237, 237, 237));
        ((JButton)e.getSource()).setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        JConfirmDialog.rptaConfirmDialogDefaultNo(null, "�Cu�l es la edad del paciente?","Aceptar","Rechazar");
    }

    private void setNombreBoton(JButton jButton, String string, char c) {
        jButton.setText(string);
        jButton.setMnemonic(c);
    }

    private void setBoton1(String string, char c) {
        setNombreBoton(jButton1,string,c);
    }
    
    private void setBoton2(String string, char c) {
        setNombreBoton(jButton2,string,c);
    }
}