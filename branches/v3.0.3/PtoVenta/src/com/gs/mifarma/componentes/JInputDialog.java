package com.gs.mifarma.componentes;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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

import mifarma.common.FarmaUtility;

import oracle.jdeveloper.layout.VerticalFlowLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Dialogo de ingreso de datos
 * @author ERIOS
 * @since 26.11.2014
 */
public class JInputDialog extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(JInputDialog.class);

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
    private JTextFieldSanSerif jTextField1 = new JTextFieldSanSerif();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

    private String tipoValidacion = "";

    public JInputDialog(Dialog dialog, String string, boolean b) {
        super(dialog, string, b);
        mensaje = string;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public JInputDialog(Frame dialog, String string, boolean b) {
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
        jButton1.setText("Aceptar");
        jButton1.setMnemonic('A');
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
        jButton2.setText("Cancelar");
        jButton2.setMnemonic('C');
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
        jPanel3.setLayout(verticalFlowLayout1);
        jTextPane1.setFont(new Font("Dialog", 1, 12));
        jTextPane1.setOpaque(false);
        jTextPane1.setEditable(false);
        jTextPane1.setFocusable(false);
        jTextPane1.setText(mensaje);
        jTextField1.setLengthText(100);
        jTextField1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jTextField1_keyPressed(e);
            }
        });
        jLabel3.setText(" ");
        jLabel3.setFont(new Font("Dialog", 1, 4));
        jPanel3.add(jTextPane1,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                           new Insets(0, 0, 0, 0), 129, 58));
        jPanel3.add(jTextField1,
                    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 1), 103, 61));
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

    String getValue() {
        this.pack();
        this.setLocationRelativeTo(null);

        jTextField1.requestFocusInWindow();
        this.setVisible(true);

        String rspta = null;
        if (vRetorno == JOptionPane.YES_OPTION) {
            rspta = jTextField1.getText();
        }
        return rspta;
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        opcionNO();
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        opcionSI();
    }

    /*public static String showInputDialog(Object pJDialog,  String pMensaje){
        return showInputDialog(pJDialog ,  pMensaje, "");
    }*/

    public static String showInputDialog(Object pJDialog, String pMensaje, String pTipoValidacion) {
        log.warn(pMensaje);
        JInputDialog optioPane = null;
        if (pJDialog instanceof JDialog) {
            JDialog myJDialog = (JDialog)pJDialog;
            optioPane = new JInputDialog(myJDialog, pMensaje, true);
        } else if (pJDialog instanceof Frame) {
            Frame myFrame = (Frame)pJDialog;
            optioPane = new JInputDialog(myFrame, pMensaje, true);
        } else {
            optioPane = new JInputDialog(new JDialog(), pMensaje, true);
        }
        optioPane.setTipoValidacion(pTipoValidacion);

        String bRetorno = optioPane.getValue();

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
        if (!validaDT()) {
            return;
        }
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
        //JInputDialog.showInputDialog(null,"¿Cuál es su edad?");
        JInputDialog.showInputDialog(null, "¿Cuál es su edad?", "NM");
    }

    private void jTextField1_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            opcionSI();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            opcionNO();
        }
    }

    private boolean validaDT() {
        boolean bValida = true;
        try {
            switch (tipoValidacion) {
            case "NM":
                bValida = FarmaUtility.isInteger(jTextField1.getText().trim());
                if (!bValida) {
                    FarmaUtility.showMessage(this, "El dato que ingrese debe ser un número.", jTextField1);
                }
                break;
            default: //case "AF":
                break;
            }
        } catch (Exception e) {
            bValida = false;
            log.error("", e);
        }
        return bValida;
    }

    private void setTipoValidacion(String tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }

}
