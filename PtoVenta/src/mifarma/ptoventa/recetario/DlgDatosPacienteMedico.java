package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.cliente.DlgBuscarDni;
import mifarma.ptoventa.recetario.reference.UtilityRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.DlgBuscaMedico;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDatosPacienteMedico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      15.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDatosPacienteMedico extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDatosPacienteMedico.class);

    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JButton btnDni = new JButton();
    private JButton btnPaciente = new JButton();
    private JButton btnTelefono = new JButton();
    private JButton btnCmp = new JButton();
    private JButton btnMedico = new JButton();
    private JTextField txtDni = new JTextField();
    private JTextField txtPaciente = new JTextField();
    private JTextField txtTelefono = new JTextField();
    private JTextField txtCmp = new JTextField();
    private JTextField txtMedico = new JTextField();
    private JPanel jPanel3 = new JPanel();
    private JButton btnFecha = new JButton();
    private JButton btnHora = new JButton();
    private JButton btnLocal = new JButton();
    private JTextFieldSanSerif txtFecha = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtHora = new JTextFieldSanSerif();
    private JTextField txtLocal = new JTextField();
    private JTextField txtDescLocal = new JTextField();
    private JButton btnObservaciones = new JButton();
    private JTextArea txtObservaciones = new JTextArea();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDatosPacienteMedico() {
        this(null, "", false);
    }

    public DlgDatosPacienteMedico(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initVariables();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(498, 485));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setForeground(Color.white);
        this.setTitle("Ingresa Datos");
        this.addWindowListener(new WindowAdapter() {

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanel1.setLayout(null);
        jPanel1.setForeground(Color.white);
        jPanel1.setBackground(Color.white);
        jPanel2.setBounds(new Rectangle(15, 10, 465, 185));
        jPanel2.setBackground(new Color(43, 141, 39));
        jPanel2.setLayout(null);

        jPanel2.setForeground(new Color(0, 132, 0));
        jPanel2.setFocusable(false);
        btnDni.setText("* DNI :");
        btnDni.setBackground(Color.white);
        btnDni.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDni.setBorderPainted(false);
        btnDni.setContentAreaFilled(false);
        btnDni.setDefaultCapable(false);
        btnDni.setFocusPainted(false);
        btnDni.setFont(new Font("SansSerif", 1, 11));
        btnDni.setForeground(Color.white);
        btnDni.setHorizontalAlignment(SwingConstants.LEFT);
        btnDni.setMnemonic('D');
        btnDni.setRequestFocusEnabled(false);
        btnDni.setBounds(new Rectangle(10, 13, 65, 25));

        btnDni.setFocusable(false);
        btnDni.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDni_actionPerformed(e);
            }
        });
        btnPaciente.setText("* Paciente :");
        btnPaciente.setBackground(Color.white);
        btnPaciente.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnPaciente.setBorderPainted(false);
        btnPaciente.setContentAreaFilled(false);
        btnPaciente.setDefaultCapable(false);
        btnPaciente.setFocusPainted(false);
        btnPaciente.setFont(new Font("SansSerif", 1, 11));
        btnPaciente.setForeground(Color.white);
        btnPaciente.setHorizontalAlignment(SwingConstants.LEFT);
        btnPaciente.setMnemonic('P');
        btnPaciente.setRequestFocusEnabled(false);
        btnPaciente.setBounds(new Rectangle(10, 40, 65, 25));

        btnPaciente.setFocusable(false);
        btnPaciente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPaciente_actionPerformed(e);
            }
        });
        btnTelefono.setText("Telefono :");
        btnTelefono.setBackground(Color.white);
        btnTelefono.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnTelefono.setBorderPainted(false);
        btnTelefono.setContentAreaFilled(false);
        btnTelefono.setDefaultCapable(false);
        btnTelefono.setFocusPainted(false);
        btnTelefono.setFont(new Font("SansSerif", 1, 11));
        btnTelefono.setForeground(Color.white);
        btnTelefono.setHorizontalAlignment(SwingConstants.LEFT);
        btnTelefono.setMnemonic('T');
        btnTelefono.setRequestFocusEnabled(false);
        btnTelefono.setBounds(new Rectangle(10, 75, 65, 25));

        btnTelefono.setFocusable(false);
        btnTelefono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTelefono_actionPerformed(e);
            }
        });
        btnCmp.setText("* CMP :");
        btnCmp.setBackground(Color.white);
        btnCmp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCmp.setBorderPainted(false);
        btnCmp.setContentAreaFilled(false);
        btnCmp.setDefaultCapable(false);
        btnCmp.setFocusPainted(false);
        btnCmp.setFont(new Font("SansSerif", 1, 11));
        btnCmp.setForeground(Color.white);
        btnCmp.setHorizontalAlignment(SwingConstants.LEFT);
        btnCmp.setMnemonic('C');
        btnCmp.setRequestFocusEnabled(false);
        btnCmp.setBounds(new Rectangle(10, 105, 65, 25));

        btnCmp.setFocusable(false);
        btnCmp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCmp_actionPerformed(e);
            }
        });
        btnMedico.setText("* Medico :");
        btnMedico.setBackground(Color.white);
        btnMedico.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnMedico.setBorderPainted(false);
        btnMedico.setContentAreaFilled(false);
        btnMedico.setDefaultCapable(false);
        btnMedico.setFocusPainted(false);
        btnMedico.setFont(new Font("SansSerif", 1, 11));
        btnMedico.setForeground(Color.white);
        btnMedico.setHorizontalAlignment(SwingConstants.LEFT);
        btnMedico.setMnemonic('M');
        btnMedico.setRequestFocusEnabled(false);
        btnMedico.setBounds(new Rectangle(10, 130, 65, 25));

        btnMedico.setFocusable(false);
        btnMedico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMedico_actionPerformed(e);
            }
        });
        txtDni.setBounds(new Rectangle(115, 15, 100, 20));
        txtDni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDni_keyPressed(e);
            }
        });
        txtDni.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtDni_focusLost(e);
            }
        });
        txtPaciente.setBounds(new Rectangle(115, 45, 315, 20));
        txtPaciente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPaciente_keyPressed(e);
            }
        });
        txtTelefono.setBounds(new Rectangle(115, 75, 130, 20));
        txtTelefono.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtTelefono_keyPressed(e);
            }
        });
        txtCmp.setBounds(new Rectangle(115, 105, 80, 20));
        txtCmp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCmp_keyPressed(e);
            }
        });
        txtMedico.setBounds(new Rectangle(115, 135, 315, 20));
        txtMedico.setFocusable(false);
        txtMedico.setEditable(false);
        txtMedico.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMedico_keyPressed(e);
            }
        });
        jPanel3.setBounds(new Rectangle(15, 205, 465, 190));
        jPanel3.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jPanel3.setLayout(null);

        jPanel3.setBackground(Color.white);
        jPanel3.setForeground(Color.white);
        jPanel3.setFocusable(false);
        btnFecha.setText("* Fecha:");
        btnFecha.setBackground(Color.white);
        btnFecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnFecha.setBorderPainted(false);
        btnFecha.setContentAreaFilled(false);
        btnFecha.setDefaultCapable(false);
        btnFecha.setFocusPainted(false);
        btnFecha.setFont(new Font("SansSerif", 1, 11));
        btnFecha.setForeground(new Color(255, 115, 66));
        btnFecha.setHorizontalAlignment(SwingConstants.LEFT);
        btnFecha.setMnemonic('F');
        btnFecha.setRequestFocusEnabled(false);
        btnFecha.setBounds(new Rectangle(10, 10, 50, 20));
        btnFecha.setSize(new Dimension(50, 20));

        btnFecha.setFocusable(false);
        btnFecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFecha_actionPerformed(e);
            }
        });
        btnHora.setText("* Hora:");
        btnHora.setBackground(Color.white);
        btnHora.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnHora.setBorderPainted(false);
        btnHora.setContentAreaFilled(false);
        btnHora.setDefaultCapable(false);
        btnHora.setFocusPainted(false);
        btnHora.setFont(new Font("SansSerif", 1, 11));
        btnHora.setForeground(new Color(255, 115, 66));
        btnHora.setHorizontalAlignment(SwingConstants.LEFT);
        btnHora.setMnemonic('H');
        btnHora.setRequestFocusEnabled(false);
        btnHora.setBounds(new Rectangle(115, 10, 35, 20));
        btnHora.setSize(new Dimension(35, 20));

        btnHora.setFocusable(false);
        btnHora.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnHora_actionPerformed(e);
            }
        });
        btnLocal.setText("* Local:");
        btnLocal.setBackground(Color.white);
        btnLocal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLocal.setBorderPainted(false);
        btnLocal.setContentAreaFilled(false);
        btnLocal.setDefaultCapable(false);
        btnLocal.setFocusPainted(false);
        btnLocal.setFont(new Font("SansSerif", 1, 11));
        btnLocal.setForeground(new Color(255, 115, 66));
        btnLocal.setHorizontalAlignment(SwingConstants.LEFT);
        btnLocal.setMnemonic('L');
        btnLocal.setRequestFocusEnabled(false);
        btnLocal.setBounds(new Rectangle(185, 10, 71, 20));
        btnLocal.setSize(new Dimension(71, 20));

        btnLocal.setFocusable(false);
        btnLocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLocal_actionPerformed(e);
            }
        });
        txtFecha.setBounds(new Rectangle(10, 35, 95, 20));
        txtFecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecha_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecha_keyReleased(e);
            }
        });
        txtFecha.setLengthText(10);
        txtHora.setBounds(new Rectangle(115, 35, 60, 20));
        txtHora.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtHora_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtHora_keyReleased(e);
            }
        });
        txtHora.setLengthText(5);
        txtLocal.setBounds(new Rectangle(185, 35, 55, 20));
        txtLocal.setEditable(false);
        txtLocal.setFocusable(false);
        txtLocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLocal_keyPressed(e);
            }
        });
        txtDescLocal.setBounds(new Rectangle(250, 35, 205, 20));

        txtDescLocal.setEditable(false);
        txtDescLocal.setFocusable(false);
        txtDescLocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jTextArea1_keyPressed(e);
            }
        });
        btnObservaciones.setText("Observaciones/Etiqueta");
        btnObservaciones.setBackground(Color.white);
        btnObservaciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnObservaciones.setBorderPainted(false);
        btnObservaciones.setContentAreaFilled(false);
        btnObservaciones.setDefaultCapable(false);
        btnObservaciones.setFocusPainted(false);
        btnObservaciones.setFont(new Font("SansSerif", 1, 11));
        btnObservaciones.setForeground(new Color(255, 115, 66));
        btnObservaciones.setHorizontalAlignment(SwingConstants.LEFT);
        btnObservaciones.setMnemonic('O');
        btnObservaciones.setRequestFocusEnabled(false);
        btnObservaciones.setBounds(new Rectangle(10, 70, 140, 25));

        btnObservaciones.setFocusable(false);
        btnObservaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObservaciones_actionPerformed(e);
            }
        });
        txtObservaciones.setBounds(new Rectangle(10, 100, 440, 80));

        txtObservaciones.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        txtObservaciones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtObservaciones_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(390, 405, 90, 20));
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(285, 405, 90, 20));

        lblF11.setFocusable(false);
        jPanel2.add(txtMedico, null);
        jPanel2.add(txtCmp, null);
        jPanel2.add(txtTelefono, null);
        jPanel2.add(txtPaciente, null);
        jPanel2.add(txtDni, null);
        jPanel2.add(btnMedico, null);
        jPanel2.add(btnCmp, null);
        jPanel2.add(btnTelefono, null);
        jPanel2.add(btnPaciente, null);
        jPanel2.add(btnDni, null);
        jPanel3.add(txtObservaciones, null);
        jPanel3.add(btnObservaciones, null);
        jPanel3.add(txtDescLocal, null);
        jPanel3.add(txtLocal, null);
        jPanel3.add(txtHora, null);
        jPanel3.add(txtFecha, null);
        jPanel3.add(btnLocal, null);
        jPanel3.add(btnHora, null);
        jPanel3.add(btnFecha, null);
        jPanel1.add(jPanel3, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(lblEsc, null);
        jPanel1.add(lblF11, null);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    }

    private void initVariables() { //txtDni.setEditable(false);
        //txtPaciente.setEnabled(false);
        //txtCmp.setEditable(false);
        //txtMedico.setEnabled(false);
        //txtLocal.setEditable(false);
        //txtDescLocal.setEnabled(false);
        txtObservaciones.setDocument(new FarmaLengthText(90));
        if (VariablesRecetario.vMapDatosPacienteMedico != null) {
            txtDni.setText(VariablesRecetario.vMapDatosPacienteMedico.get("DNI"));
            txtPaciente.setText(VariablesRecetario.vMapDatosPacienteMedico.get("PACIENTE"));
            txtTelefono.setText(VariablesRecetario.vMapDatosPacienteMedico.get("TELEFONO"));
            txtCmp.setText(VariablesRecetario.vMapDatosPacienteMedico.get("CMP"));
            txtMedico.setText(VariablesRecetario.vMapDatosPacienteMedico.get("MEDICO"));
            txtFecha.setText(VariablesRecetario.vMapDatosPacienteMedico.get("FECHA"));
            txtHora.setText(VariablesRecetario.vMapDatosPacienteMedico.get("HORA"));
            txtLocal.setText(VariablesRecetario.vMapDatosPacienteMedico.get("LOCAL"));
            txtDescLocal.setText(VariablesRecetario.vMapDatosPacienteMedico.get("DESC_LOCAL"));
            txtObservaciones.setText(VariablesRecetario.vMapDatosPacienteMedico.get("OBSERVACION"));
        } else {
            txtLocal.setText(FarmaVariables.vCodLocal);

            //try
            //{   //ArrayList local = DBPtoVenta.obtieneDatosLocal();
            //String desc = ((ArrayList)local.get(0)).get(0).toString();
            //txtDescLocal.setText(desc);
            txtDescLocal.setText(FarmaVariables.vDescLocal);
            //}
            //catch (SQLException e)
            //{   log.error("",e);
            //}
        }
        txtDni.grabFocus();
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnDni_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(txtDni);
    }

    private void btnPaciente_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPaciente);
    }

    private void btnTelefono_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtTelefono);
    }

    private void btnCmp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCmp);
    }

    private void btnMedico_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMedico);
    }

    private void btnFecha_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecha);
    }

    private void btnHora_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtHora);
    }

    private void btnLocal_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtLocal);
    }

    private void btnObservaciones_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtObservaciones);
    }

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            mostrarBusquedaPaciente();
            FarmaUtility.moveFocus(txtTelefono);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtPaciente_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtTelefono);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCmp);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtCmp_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            muestraBuscaMedico();
            FarmaUtility.moveFocus(txtFecha);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtMedico_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFecha);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtHora_keyPressed(KeyEvent e) {
        FarmaUtility.completarHora(txtHora, e, false);
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtObservaciones);
        else
            chkKeyPressed(e);
    }

    private void txtHora_keyReleased(KeyEvent e) {
        FarmaUtility.completarHora(txtHora, e, false);
    }

    private void txtLocal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesPtoVenta.vTipoMaestro = "1";
            DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
            dlgListaMaestros.setVisible(true);
            if (FarmaVariables.vAceptar) {
                txtLocal.setText(VariablesPtoVenta.vCodMaestro);
                txtDescLocal.setText(VariablesPtoVenta.vDescMaestro);
            }
            FarmaUtility.moveFocus(txtObservaciones);
        } else {
            chkKeyPressed(e);
        }
    }

    private void jTextArea1_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtObservaciones);
        else {
            chkKeyPressed(e);
        }
    }

    private void txtObservaciones_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFecha_keyPressed(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtHora);
        else
            chkKeyPressed(e);
    }

    private void txtFecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (!txtDni.getText().trim().equals("") && !txtPaciente.getText().trim().equals("") &&
                !txtCmp.getText().trim().equals("") && !txtMedico.getText().trim().equals("") &&
                !txtFecha.getText().trim().equals("") && !txtHora.getText().trim().equals("") &&
                !txtLocal.getText().trim().equals("")) {
                boolean flag = true;
                Calendar fecha_actual = Calendar.getInstance();
                Calendar fecha_ingresada = (Calendar)fecha_actual.clone();
                try {
                    fecha_ingresada.setTime(FarmaUtility.getStringToDate(txtFecha.getText().trim(), "dd/MM/yyyy"));
                } catch (Exception ex) { //si no se puede obtener un Date, la fecha es incorrecta
                    FarmaUtility.showMessage(this, "ERROR: La fecha ingresada no es valida", null);
                    flag = false;
                }

                if (flag) {
                    if (FarmaUtility.diferenciaEnDias(fecha_actual, fecha_ingresada) > 0) {
                        flag = false;
                        FarmaUtility.showMessage(this, "ERROR: La fecha ingresada debe ser mayor a la fecha actual",
                                                 null);
                    }
                }
                if (flag) {
                    if (!UtilityRecetario.validarHoraMin24(txtHora.getText().trim())) {
                        flag = false;
                        FarmaUtility.showMessage(this, "ERROR: La hora ingresada no es valida", null);
                    }
                }
                if (flag) {
                    Map<String, String> tmpMapDatos = new HashMap<String, String>();
                    tmpMapDatos.put("DNI", txtDni.getText().trim());
                    tmpMapDatos.put("PACIENTE", txtPaciente.getText().trim());
                    tmpMapDatos.put("TELEFONO", txtTelefono.getText().trim());
                    tmpMapDatos.put("CMP", txtCmp.getText().trim());
                    tmpMapDatos.put("MEDICO", txtMedico.getText().trim());
                    tmpMapDatos.put("FECHA", txtFecha.getText().trim());
                    tmpMapDatos.put("HORA", txtHora.getText().trim());
                    tmpMapDatos.put("LOCAL", txtLocal.getText().trim());
                    tmpMapDatos.put("DESC_LOCAL", txtDescLocal.getText().trim());
                    tmpMapDatos.put("OBSERVACION", txtObservaciones.getText().trim());

                    VariablesRecetario.vMapDatosPacienteMedico = tmpMapDatos;
                    cerrarVentana(true);
                }
            } else
                FarmaUtility.showMessage(this, "ERROR: Alguno de los campos se encuentra vacio", null);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
            //VariablesRecetario.vMapDatosPacienteMedico = null;
        } else if (e.getKeyCode() == KeyEvent.VK_TAB && e.getSource().equals(txtObservaciones)) {
            if (e.isShiftDown())
                txtObservaciones.transferFocusBackward();
            else
                txtObservaciones.transferFocus();
            e.consume();
        }
    }


    private void txtDni_focusLost(FocusEvent e) {
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void mostrarBusquedaPaciente() {
        DlgBuscarDni dlgBuscarDni = new DlgBuscarDni(myParentFrame, "", true);
        dlgBuscarDni.setVisible(true);
        if (FarmaVariables.vAceptar) {
            ArrayList<Object> array = (ArrayList<Object>)dlgBuscarDni.retornarResultado().get(0);

            String strDni = array.get(0).toString();
            String strPaciente =
                array.get(1).toString() + " " + array.get(2).toString() + " " + array.get(3).toString();

            txtDni.setText(strDni);
            txtPaciente.setText(strPaciente.toUpperCase().trim());
        }
    }

    private void muestraBuscaMedico() {
        DlgBuscaMedico dlgBuscaMedico = new DlgBuscaMedico(myParentFrame, "", true);
        dlgBuscaMedico.setVisible(true);
        String strCMP = VariablesVentas.vMatriListaMed;
        String strNombMed = VariablesVentas.vNombreListaMed;

        txtCmp.setText(strCMP);
        txtMedico.setText(strNombMed.toUpperCase());
    }
}
