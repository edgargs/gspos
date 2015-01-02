package mifarma.ptoventa.administracion.usuarios;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
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

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.VariablesUsuarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMantCarne extends JDialog {

    /*** @Author Asolis
     * @Since  20.02.09
     * @description Mant. Carne Sanidad*/
    private static final Logger log = LoggerFactory.getLogger(DlgMantCarne.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlIdentUsuario = new JPanelTitle();

    private JPanelTitle pnlDatosUsuario = new JPanelTitle();

    private JLabelFunction lblAceptar = new JLabelFunction();

    private JLabelFunction lblSalir = new JLabelFunction();

    private JLabelWhite lblNumSecUsuario = new JLabelWhite();

    private JTextFieldSanSerif txtNroEmpleado = new JTextFieldSanSerif();


    private JTextFieldSanSerif txtApePat = new JTextFieldSanSerif();

    private JLabelWhite lblApeMat_T = new JLabelWhite();

    private JTextFieldSanSerif txtApeMat = new JTextFieldSanSerif();

    private JLabelWhite lblNombres_T = new JLabelWhite();

    private JTextFieldSanSerif txtNombres = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtFecNacimiento = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtCarne = new JTextFieldSanSerif();

    private JLabelWhite lblFecNac_T = new JLabelWhite();
    private JLabelWhite lblCarne = new JLabelWhite();

    private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();

    private JLabelWhite lblDireccion_T = new JLabelWhite();

    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();

    private JLabelWhite lblTelefono_T = new JLabelWhite();

    private JButtonLabel btlNumEmpleado = new JButtonLabel();

    private JButtonLabel btnApePat = new JButtonLabel();

    private JLabelWhite lblNumSec = new JLabelWhite();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JLabelWhite lblFecNac_T1 = new JLabelWhite();

    private JLabelWhite lblFech_Exp = new JLabelWhite();
    private JLabelWhite lblFech_Venc = new JLabelWhite();

    private JTextFieldSanSerif txtFech_Exp = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFech_Venc = new JTextFieldSanSerif();

    private JLabel lblFech_ExpM = new JLabel();
    private JLabel lblFech_VencM = new JLabel();
    private JLabel lblCarneM = new JLabel();


    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMantCarne() {
        this(null, "", false);
    }

    public DlgMantCarne(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(670, 433));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento de Carné de Sanidad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlIdentUsuario.setBounds(new Rectangle(10, 10, 645, 60));
        pnlIdentUsuario.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDatosUsuario.setBounds(new Rectangle(10, 75, 645, 290));
        pnlDatosUsuario.setBackground(Color.white);
        pnlDatosUsuario.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDatosUsuario.setFocusable(false);
        lblAceptar.setBounds(new Rectangle(450, 370, 90, 20));
        lblAceptar.setText("[F11] Aceptar");
        lblSalir.setBounds(new Rectangle(560, 370, 95, 20));
        lblSalir.setText("[ESC] Cerrar");
        lblNumSecUsuario.setText("Num. Sec. Usuario");
        lblNumSecUsuario.setBounds(new Rectangle(10, 5, 135, 20));
        txtNroEmpleado.setBounds(new Rectangle(155, 30, 135, 20));
        txtNroEmpleado.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                txtNroEmpleado_keyPressed(e);

            }

            public void keyTyped(KeyEvent e) {
                txtNroEmpleado_keyTyped(e);
            }


        });


        txtApePat.setBounds(new Rectangle(225, 10, 220, 20));
        txtApePat.setDocument(new FarmaLengthText(30));
        txtApePat.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtApePat_focusLost(e);
            }
        });
        txtApePat.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtApePat_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtApePat_keyPressed(e);
            }
        });
        lblApeMat_T.setText("Apellido Materno");
        lblApeMat_T.setBounds(new Rectangle(115, 35, 100, 20));
        lblApeMat_T.setForeground(new Color(255, 130, 14));
        txtApeMat.setBounds(new Rectangle(225, 35, 220, 20));
        txtApeMat.setDocument(new FarmaLengthText(30));
        txtApeMat.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtApeMat_focusLost(e);
            }
        });
        txtApeMat.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtApeMat_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtApeMat_keyPressed(e);
            }
        });
        lblNombres_T.setText("Nombres");
        lblNombres_T.setBounds(new Rectangle(115, 60, 100, 20));
        lblNombres_T.setForeground(new Color(255, 130, 14));
        txtNombres.setBounds(new Rectangle(225, 60, 255, 20));
        txtNombres.setDocument(new FarmaLengthText(30));
        txtNombres.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtNombres_focusLost(e);
            }
        });
        txtNombres.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtNombres_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtNombres_keyPressed(e);
            }
        });
        txtFecNacimiento.setBounds(new Rectangle(225, 160, 110, 20));
        txtFecNacimiento.setDocument(new FarmaLengthText(10));
        txtFecNacimiento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtFecNacimiento_actionPerformed(e);
            }
        });
        txtFecNacimiento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecNacimiento_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecNacimiento_keyReleased(e);
            }

        });


        lblFecNac_T.setText("Fecha Nacimiento");
        lblFecNac_T.setBounds(new Rectangle(115, 160, 100, 20));
        lblFecNac_T.setForeground(new Color(255, 130, 14));

        lblCarne.setText("Nro. Carné Sanidad");
        lblCarne.setBounds(new Rectangle(115, 185, 110, 20));
        lblCarne.setForeground(new Color(255, 130, 14));


        txtCarne.setBounds(new Rectangle(225, 185, 165, 20));
        txtCarne.setDocument(new FarmaLengthText(20));

        txtCarne.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCarne_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtCarne_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCarne_keyTyped(e);
            }

        });

        lblFech_Exp.setText("Fecha Expedición");
        lblFech_Exp.setBounds(new Rectangle(115, 210, 110, 20));
        lblFech_Exp.setForeground(new Color(255, 130, 14));


        txtFech_Exp.setBounds(new Rectangle(225, 210, 110, 20));
        txtFech_Exp.setDocument(new FarmaLengthText(10));
        txtFech_Exp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtFech_Exp_actionPerformed(e);
            }


        });
        txtFech_Exp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFech_Exp_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFech_Exp_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFech_Exp_keyTyped(e);
            }


        });


        lblFech_Venc.setText("Fecha Vencimiento");
        lblFech_Venc.setBounds(new Rectangle(115, 235, 110, 20));
        lblFech_Venc.setForeground(new Color(255, 130, 14));


        txtFech_Venc.setBounds(new Rectangle(225, 235, 110, 20));
        txtFech_Venc.setDocument(new FarmaLengthText(10));
        txtFech_Venc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtFech_Venc_actionPerformed(e);
            }


        });
        txtFech_Venc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFech_Venc_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFech_Venc_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFech_Venc_keyTyped(e);
            }


        });

        //lblCarneM.setText("aaaaaaaaaaaaaaaaaaaaaaaa");
        lblCarneM.setBounds(new Rectangle(405, 185, 110, 20));
        lblCarneM.setForeground(Color.LIGHT_GRAY);
        lblCarneM.setFont(new Font("SansSerif", 1, 11));

        // lblFech_VencM.setText("lblFech_VencM");
        //lblFech_VencM.setBounds(new Rectangle(405, 235, 110, 20));
        lblFech_VencM.setBounds(new Rectangle(350, 235, 110, 20));
        lblFech_VencM.setForeground(Color.LIGHT_GRAY);
        lblFech_VencM.setFont(new Font("SansSerif", 1, 11));

        // lblFech_ExpM.setText("lblFech_ExpM");
        //lblFech_ExpM.setBounds(new Rectangle(405, 210, 110, 20));
        lblFech_ExpM.setBounds(new Rectangle(350, 210, 110, 20));
        lblFech_ExpM.setForeground(Color.LIGHT_GRAY);
        lblFech_ExpM.setFont(new Font("SansSerif", 1, 11));


        txtDireccion.setBounds(new Rectangle(225, 85, 350, 20));
        txtDireccion.setDocument(new FarmaLengthText(120));
        txtDireccion.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtDireccion_focusLost(e);
            }
        });
        txtDireccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDireccion_keyPressed(e);
            }
        });
        lblDireccion_T.setText("Dirección");
        lblDireccion_T.setBounds(new Rectangle(115, 85, 100, 20));
        lblDireccion_T.setForeground(new Color(255, 130, 14));
        txtTelefono.setBounds(new Rectangle(225, 110, 220, 20));
        txtTelefono.setDocument(new FarmaLengthText(30));
        txtTelefono.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtTelefono_focusLost(e);
            }


        });
        txtTelefono.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtTelefono_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtTelefono_keyTyped(e);
            }
        });
        lblTelefono_T.setText("Teléfono");
        lblTelefono_T.setBounds(new Rectangle(115, 110, 100, 20));
        lblTelefono_T.setForeground(new Color(255, 130, 14));
        btlNumEmpleado.setText("Nro. Empleado");
        btlNumEmpleado.setBounds(new Rectangle(155, 5, 135, 20));
        btlNumEmpleado.setMnemonic('n');
        btlNumEmpleado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btlNumEmpleado_actionPerformed(e);
            }
        });
        btnApePat.setText("Apellido Paterno");
        btnApePat.setBounds(new Rectangle(115, 10, 100, 20));
        btnApePat.setMnemonic('a');
        btnApePat.setForeground(new Color(255, 130, 14));
        btnApePat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnApePat_actionPerformed(e);
            }
        });
        lblNumSec.setBounds(new Rectangle(10, 30, 120, 20));
        txtDni.setBounds(new Rectangle(225, 135, 110, 20));
        txtDni.setDocument(new FarmaLengthText(8));
        txtDni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDni_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtDni_keyTyped(e);
            }

        });
        lblFecNac_T1.setText("DNI");
        lblFecNac_T1.setBounds(new Rectangle(115, 135, 100, 20));
        lblFecNac_T1.setForeground(new Color(255, 130, 14));
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblAceptar, null);
        jContentPane.add(pnlDatosUsuario, null);

        jContentPane.add(pnlIdentUsuario, null);
        pnlDatosUsuario.add(lblFecNac_T1, null);
        pnlDatosUsuario.add(lblCarne, null);
        pnlDatosUsuario.add(txtDni, null);
        pnlDatosUsuario.add(btnApePat, null);
        pnlDatosUsuario.add(lblTelefono_T, null);
        pnlDatosUsuario.add(lblDireccion_T, null);
        pnlDatosUsuario.add(txtDireccion, null);
        pnlDatosUsuario.add(lblFecNac_T, null);
        pnlDatosUsuario.add(txtFecNacimiento, null);
        pnlDatosUsuario.add(txtFech_Exp, null);
        pnlDatosUsuario.add(txtFech_Venc, null);
        pnlDatosUsuario.add(txtNombres, null);
        pnlDatosUsuario.add(lblNombres_T, null);
        pnlDatosUsuario.add(txtApeMat, null);
        pnlDatosUsuario.add(lblApeMat_T, null);
        pnlDatosUsuario.add(txtApePat, null);
        pnlDatosUsuario.add(txtTelefono, null);

        pnlDatosUsuario.add(txtCarne, null);

        pnlDatosUsuario.add(lblFech_ExpM, null);
        pnlDatosUsuario.add(lblFech_VencM, null);
        pnlDatosUsuario.add(lblCarneM, null);
        pnlDatosUsuario.add(lblFech_Exp, null);
        pnlDatosUsuario.add(lblFech_Venc, null);
        pnlIdentUsuario.add(lblNumSec, null);
        pnlIdentUsuario.add(btlNumEmpleado, null);
        pnlIdentUsuario.add(txtNroEmpleado, null);
        pnlIdentUsuario.add(lblNumSecUsuario, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        txtNroEmpleado.setText(VariablesUsuarios.vCodTrab_RRHH);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtNroEmpleado_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (txtNroEmpleado.getText().length() == 0) {

                limpiarCajas();
                FarmaUtility.showMessage(this, " Ingrese el Nro de Empleado", txtNroEmpleado);
            }

            else {
                if (txtNroEmpleado.getText().length() > 0) {
                    VariablesUsuarios.vSecTrab = txtNroEmpleado.getText();

                    if (existeUsuario(VariablesUsuarios.vSecTrab)) {
                        log.info("El usuario existe en maestros");
                        //metodo que verifica que el usuario no tiene carne
                        if (!ExistenciaUsuarioCarne()) { //si no tiene carne
                            //si el trabajador del local   cuenta con carne sanidad
                            obtenerDatosUsuarioLocal();

                            habilitaTexto(false);
                            // FarmaUtility.moveFocus(txtIdUsuario);

                        } else { // si tiene carne

                            //Puede ingresar otro nro carne ,ya que está proximo a vencer o está vencido.
                            if (VariablesUsuarios.vModificarCarne.equals("S")) {


                                obtenerDatosUsuarioLocal();
                                habilitaTexto(false);
                                FarmaUtility.moveFocus(txtCarne);

                            }
                            //SI no puede modificar
                            else {
                                FarmaUtility.showMessage(this, "El usuario ya tiene  registrado Carné de Sanidad",
                                                         txtNroEmpleado);
                                habilitaTexto(false);

                                FarmaUtility.moveFocus(txtNroEmpleado);

                            }
                        }

                    } else {
                        log.debug("El usuario no existe en maestros");
                        log.debug("Verifica si se validara al usuario");

                        if (!isValidarUsuario()) {
                            String codigo = txtNroEmpleado.getText();
                            limpiarCajas();
                            habilitaTexto(true);
                            txtNroEmpleado.setText(codigo.trim());
                            // FarmaUtility.moveFocus(txtIdUsuario);
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "El Nro de Empleado ingresado no existe en la lista de RR.HH.",
                                                     txtNroEmpleado);
                            limpiarCajas();
                            habilitaTexto(false);
                            FarmaUtility.moveFocus(txtNroEmpleado);

                        }
                    }
                }
            }


        }

        chkKeyPressed(e);
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroEmpleado);


    }

    private void txtFech_Exp_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFech_Exp, e);
    }

    private void txtFech_Venc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFech_Venc, e);
    }

    private void txtCarne_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCarne, e);
    }

    private void txtApePat_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApePat, e);

    }

    private void txtApeMat_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApeMat, e);
    }

    private void txtNombres_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtNombres, e);
    }

    /**
     * Solo se permitira numeros en telefono y DNI
     * @author dubilluz
     * @since 28.11.2007
     */
    private void txtDni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtDni, e);
    }

    private void txtTelefono_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
        //FarmaUtility.admitirSoloDigitos(e,txtTelefono,txtTelefono.getText().length()-1,this);
    }


    private void txtApePat_keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtApePat.setText(txtApePat.getText().toUpperCase());
            FarmaUtility.moveFocus(txtApeMat);

        }

        chkKeyPressed(e);

    }

    private void txtApeMat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtApeMat.setText(txtApeMat.getText().toUpperCase());
            FarmaUtility.moveFocus(txtNombres);
        }
        chkKeyPressed(e);
    }

    private void txtNombres_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtNombres.setText(txtNombres.getText().toUpperCase());
            FarmaUtility.moveFocus(txtDireccion);
        }
        chkKeyPressed(e);
    }

    private void txtFecNacimiento_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtNroEmpleado.getText().equalsIgnoreCase(" ")) {
                //FarmaUtility.moveFocus(txtApeMat);
                FarmaUtility.moveFocus(txtCarne);
            } else {
                //FarmaUtility.moveFocus(txtApeMat);
                FarmaUtility.moveFocus(txtCarne);
            }
        }
        chkKeyPressed(e);
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDireccion.setText(txtDireccion.getText().toUpperCase());
            FarmaUtility.moveFocus(txtTelefono);

        }
        chkKeyPressed(e);
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtTelefono.setText(txtTelefono.getText().toUpperCase());
            FarmaUtility.moveFocus(txtDni);

        }
        chkKeyPressed(e);
    }


    private void txtFecNacimiento_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecNacimiento, e);
    }


    private void txtApePat_focusLost(FocusEvent e) {
        txtApePat.setText(txtApePat.getText().toUpperCase());
    }

    private void txtApeMat_focusLost(FocusEvent e) {
        txtApeMat.setText(txtApeMat.getText().toUpperCase());
    }

    private void txtNombres_focusLost(FocusEvent e) {
        txtNombres.setText(txtNombres.getText().toUpperCase());
    }

    private void txtDireccion_focusLost(FocusEvent e) {
        txtDireccion.setText(txtDireccion.getText().toUpperCase());
    }

    private void txtTelefono_focusLost(FocusEvent e) {
        txtTelefono.setText(txtTelefono.getText().toUpperCase());
    }

    private void txtNroEmpleado_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNroEmpleado, e);
    }


    private void btlNumEmpleado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroEmpleado);
    }


    private void txtFecNacimiento_actionPerformed(ActionEvent e) {
        if (VariablesUsuarios.vActiCod) //si esta vacio
        {
            // FarmaUtility.moveFocus(txtApePat);
            FarmaUtility.moveFocus(txtCarne);
        } else {
            FarmaUtility.moveFocus(txtCarne);
        }

    }

    private void txtCarne_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCarne.getText().length() > 0)
                FarmaUtility.moveFocus(txtFech_Exp);

            else {
                FarmaUtility.showMessage(this, "Ingrese el Nro de Carné de Sanidad", null);
                FarmaUtility.moveFocus(txtCarne);
            }
        } else
            chkKeyPressed(e);


    }

    private void txtCarne_keyReleased(KeyEvent e) {
    }

    private void txtFech_Venc_keyReleased(KeyEvent e) {

        FarmaUtility.dateComplete(txtFech_Venc, e);
    }

    private void txtFech_Exp_keyReleased(KeyEvent e) {

        FarmaUtility.dateComplete(txtFech_Exp, e);
    }


    private void txtFech_Exp_actionPerformed(ActionEvent e) {
    }

    private void txtFech_Venc_actionPerformed(ActionEvent e) {
    }

    private void txtFech_Exp_keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFech_Exp.getText().length() > 9)
                FarmaUtility.moveFocus(txtFech_Venc);

            else {
                FarmaUtility.showMessage(this, "Ingrese Fecha de Expedición", null);
                FarmaUtility.moveFocus(txtFech_Exp);
            }
        } else
            chkKeyPressed(e);

    }

    private void txtFech_Venc_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFech_Venc.getText().length() > 9)
                FarmaUtility.moveFocus(txtNroEmpleado);

            else {
                FarmaUtility.showMessage(this, "Ingrese Fecha de Vencimiento", null);
                FarmaUtility.moveFocus(txtFech_Venc);
            }
        } else
            chkKeyPressed(e);

    }


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {

            if (ValidarIngresoTextBox()) {

                if (validarCampos()) {
                    //Si no hay carne duplicado en BD
                    if (!ExistenciaUsuarioCarneDuplicado()) {
                        //Si ingreso el mismo carne
                        if (lblCarneM.getText().equals(txtCarne.getText())) {

                            FarmaUtility.showMessage(this, "Nro Carné existente ,ingrese nuevo Número",
                                                     txtNroEmpleado);
                            habilitaTexto(false);
                            //limpiarCajasInsert();
                            FarmaUtility.moveFocus(txtCarne);

                        }

                        else {
                            if (IngresarCarneSanidad()) {
                                FarmaUtility.showMessage(this, "La operación se realizó correctamente", null);
                                cerrarVentana(true);
                                // Envia alerta de Registro
                                if (EnviaAlertaRegistro())
                                    log.info("Alerta Satisfactoria");
                                else
                                    log.info("No se envió la alerta ");

                            }

                        }

                    } //Si es carné duplicado ,es decir ,el nro carné esta siendo usado por otro usuario.

                    else {

                        FarmaUtility.showMessage(this, "Carné de Sanidad Duplicado,ingrese otro Nro Carné.",
                                                 txtNroEmpleado);
                        habilitaTexto(false);
                        FarmaUtility.moveFocus(txtCarne);

                    }


                }
            } else {
                FarmaUtility.showMessage(this, "Es obligatorio el ingreso de \n" +
                        "Nro Carné Sanidad\n" +
                        "Fecha  Expedición \n" +
                        "Fecha  Vencimiento \n", txtCarne);
            }

        }

        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

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


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnApePat_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtApePat);
    }

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFecNacimiento);
        }
        chkKeyPressed(e);
    }

    /**
     * Valida el codigo de trabajador de RRHH
     *
     */
    public boolean isValidoCodTrabRRHH(String codigo) {
        boolean valor = true;
        codigo = codigo.trim();
        if (codigo.length() != 6)
            valor = false;
        else {
            log.debug("Primeras dos cifras : " + codigo.substring(0, 2));
            if (!codigo.substring(0, 2).equals("10"))
                valor = false;
        }
        return valor;
    }


    private void obtenerDatosUsuarioLocal() {

        ArrayList datos = new ArrayList();
        log.info("Obteniendo datos de usuario");
        try {
            DBUsuarios.getDatosTrabLocal(datos);
        } catch (SQLException ex) {
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            String mensaje = ex.getMessage();
            FarmaUtility.showMessage(this, "No se pudo obtener información del usuario: \n" +
                    mensaje, txtApePat);
        }


        mostrarArray(datos);
    }


    private void mostrarArray(ArrayList myArray) {

        if (myArray.size() > 0) {
            ArrayList myArray2 = new ArrayList();

            myArray2 = (ArrayList)myArray.get(0);
            //VariablesUsuarios.vCodTrab_RRHH = myArray2.get(0).toString().trim();
            log.debug("Dato vCodTrab_RRHH: " + VariablesUsuarios.vCodTrab_RRHH);
            //txtNroEmpleado.setText(VariablesUsuarios.vCodTrab);
            txtApePat.setText(myArray2.get(1).toString().trim());
            txtApeMat.setText(myArray2.get(2).toString().trim());
            txtNombres.setText(myArray2.get(3).toString().trim());
            txtTelefono.setText(myArray2.get(4).toString().trim());
            txtDireccion.setText(myArray2.get(5).toString().trim());
            txtFecNacimiento.setText(myArray2.get(6).toString().trim());
            //Se coloca el DNI del trabajador
            txtDni.setText(myArray2.get(7).toString().trim());

            log.info(" Nro elementos del arreglo :" + myArray2.size());
            log.info(" VariablesUsuarios.vNroCarne :" + VariablesUsuarios.vNroCarne);
            log.info(" VariablesUsuarios.vFechaExpedicion :" + VariablesUsuarios.vFechaExpedicion);
            log.info("VariablesUsuarios.vFechaVencimiento : " + VariablesUsuarios.vFechaVencimiento);

            if (VariablesUsuarios.vCarne.length() != 0) {

                VariablesUsuarios.vNroCarne = myArray2.get(8).toString().trim();
                VariablesUsuarios.vFechaExpedicion = myArray2.get(9).toString().trim();
                VariablesUsuarios.vFechaVencimiento = myArray2.get(10).toString().trim();

                lblFech_ExpM.setText(VariablesUsuarios.vFechaExpedicion);
                lblFech_VencM.setText(VariablesUsuarios.vFechaVencimiento);
                lblCarneM.setText(VariablesUsuarios.vNroCarne);

            }


            FarmaUtility.moveFocus(txtCarne);

        } else {
            FarmaUtility.showMessage(this, "Usuario no encontrado", txtNroEmpleado);
            //limpiarCajas();
            FarmaUtility.moveFocus(txtNroEmpleado);
        }

    }


    /**
     * Verifica si el usuario existe en la tabla de local
     */
    public boolean existeUsuario(String secUsu) {

        boolean valor = false;
        String res = "";
        try {
            res = DBUsuarios.getExisteUsuario(secUsu.trim());
        } catch (SQLException ex) {
            String mensaje = ex.getMessage();
            FarmaUtility.showMessage(this, "No se pudo obtener información para validar usuario : \n" +
                    mensaje, txtNroEmpleado);
        }
        if (res.equals("S"))
            valor = true;
        return valor;

    }


    /**
     * Obtendra el indicador si validara la existencia de un usuario para su registro
     */
    public boolean isValidarUsuario() {

        boolean valor = false;

        String res = "";
        try {
            res = DBUsuarios.getIndValidarUsuario();
        } catch (SQLException ex) {
            String mensaje = ex.getMessage();
            FarmaUtility.showMessage(this, "No se pudo obtener el indicador para validar usuario : \n" +
                    mensaje, txtNroEmpleado);
        }
        if (res.equals("S"))
            valor = true;

        return valor;

    }

    private void limpiarDatosTrabajador() {
        VariablesUsuarios.vCodTrab = "";
        VariablesUsuarios.vApePat = "";
        VariablesUsuarios.vApeMat = "";
        VariablesUsuarios.vNombres = "";
        VariablesUsuarios.vDNI = "";
        VariablesUsuarios.vDireccion = "";
        VariablesUsuarios.vTelefono = "";
        VariablesUsuarios.vFecNac = "";

    }


    private boolean ExistenciaUsuarioCarne() {

        String rpta = "0";

        if (txtNroEmpleado.getText().trim().length() == 0) {
            return false;
        }

        try {
            rpta = DBUsuarios.verificaExistenciaUsuarioCarne(txtNroEmpleado.getText());
        } catch (SQLException ex) {
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            String mensaje = ex.getMessage();

            FarmaUtility.showMessage(this, "No se pudo determinar la duplicidad del usuario: \n" +
                    mensaje, txtCarne);
        }

        if (rpta.trim().equals("0")) {
            return false;
        } else {
            return true;
        }

    }


    private boolean ExistenciaUsuarioCarneDuplicado() {

        String rpta = "0";

        if (txtNroEmpleado.getText().trim().length() == 0) {
            return false;
        }

        try {
            rpta = DBUsuarios.verificaUsuarioCarnedUPLICADO(txtNroEmpleado.getText(), txtCarne.getText());

        } catch (SQLException ex) {
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            String mensaje = ex.getMessage();

            FarmaUtility.showMessage(this, "No se pudo determinar la duplicidad del usuario: \n" +
                    mensaje, txtCarne);
        }

        if (rpta.trim().equals("0")) {
            return false;
        } else {
            return true;
        }

    }


    /**
     * Habilita los campos de textos para ingreso
     */
    private void habilitaTexto(boolean valor) {
        txtApePat.setEnabled(valor);
        txtApeMat.setEnabled(valor);
        txtNombres.setEnabled(valor);
        txtDireccion.setEnabled(valor);
        txtTelefono.setEnabled(valor);
        txtDni.setEnabled(valor);
        txtFecNacimiento.setEnabled(valor);
    }

    private void limpiarCajas() {
        txtNroEmpleado.setText("");
        txtApePat.setText("");
        txtApeMat.setText("");
        txtNombres.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtDni.setText("");
        txtFecNacimiento.setText("");
        txtCarne.setText("");
        txtFech_Exp.setText("");
        txtFech_Venc.setText("");
    }


    private boolean EnviaAlertaRegistro() {

        boolean retorno = false;
        log.debug("EnviaAlerta");

        try {
            DBUsuarios.enviaAlertaRegistroCarneUsuario();
            retorno = true;

        }

        catch (SQLException sql) {
            log.error("", sql);
            retorno = false;
        }


        return retorno;
    }
    // private void IngresarCarneSanidad() throws SQLException {

    private boolean IngresarCarneSanidad() {
        boolean retorno = false;

        VariablesUsuarios.vNroCarne = txtCarne.getText().trim();
        VariablesUsuarios.vFechaExpedicion = txtFech_Exp.getText().trim();
        VariablesUsuarios.vFechaVencimiento = txtFech_Venc.getText().trim();
        log.debug("insertarCarne");

        try {

            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Está seguro que desea grabar los datos ?")) {
                DBUsuarios.insertarCarneUsuario();
                FarmaUtility.aceptarTransaccion();
                retorno = true;
                //
                //Envia alerta de Registro
                //DBUsuarios.enviaAlertaRegistroCarneUsuario();

            }

        }

        catch (SQLException e) {

            e.getMessage();
            FarmaUtility.liberarTransaccion();
            retorno = false;

            switch (e.getErrorCode()) {
            case 20001:
                FarmaUtility.showMessage(this, "Fecha Expedición no puede ser Mayor a la Fecha de hoy. Verifique !",
                                         txtFech_Exp);
                break;


            default:
                FarmaUtility.showMessage(this, "Error en la Aplicación ingresar Carné Sanidad.\n" +
                        e.getMessage(), txtCarne);
                break;


            }


        }

        return retorno;
    }


    private boolean ValidarIngresoTextBox() {

        if (txtCarne.getText().trim().length() > 0 && txtFech_Exp.getText().trim().length() > 0 &&
            txtFech_Venc.getText().trim().length() > 0)
            return true;

        else
            return false;


    }

    private boolean validarCampos() {
        boolean retorno = true;
        //if(!FarmaUtility.validarRangoFechas(this,txtFech_Exp,txtFech_Venc,false,true,true,true))

        if (!validarRangoFechasSanidad(this, txtFech_Exp, txtFech_Venc, false, true, false, false))


            retorno = false;

        return retorno;
    }

    private boolean validarRangoFechasSanidad(JDialog dialogo, JTextField txtFechaInicial, JTextField txtFechaFinal,
                                              boolean aceptaRangoAbierto, boolean aceptaFechasIguales,
                                              boolean aceptaMismoDia, boolean aceptaFechaAnterior) {

        if (!FarmaUtility.validaFecha(txtFechaInicial.getText(), "dd/MM/yyyy") ||
            txtFechaInicial.getText().length() != 10) {
            FarmaUtility.showMessage(dialogo, "Ingrese la Fecha Expedición correctamente", txtFechaInicial);
            return false;
        }

        if (!aceptaRangoAbierto || txtFechaFinal.getText().length() != 0)
            if (!FarmaUtility.validaFecha(txtFechaFinal.getText(), "dd/MM/yyyy") ||
                txtFechaFinal.getText().length() != 10) {
                FarmaUtility.showMessage(dialogo, "Ingrese la Fecha Vencimiento correctamente", txtFechaFinal);
                return false;
            }

        Date fecIni = FarmaUtility.getStringToDate(txtFechaInicial.getText().trim(), "dd/MM/yyyy");
        Date fecSys = fecIni;

        try {
            fecSys = FarmaUtility.getStringToDate(FarmaSearch.getFechaHoraBD(1), "dd/MM/yyyy");
        } catch (Exception e) {
            log.debug("" + e.getStackTrace());
        }

        /*
    //Puede ocurrir que algún colaborador ya cuente con un carné sanidad que aún no está vencido y que lo tramito antes de la fecha actual
        if (!aceptaFechaAnterior) {
            if (fecSys.after(fecIni)) {
                FarmaUtility.showMessage(dialogo,
                                         "La Fecha Inicial no puede ser anterior a la fecha actual",
                                         txtFechaInicial);
                return false;
            }
        }
      */
        if (!aceptaRangoAbierto || txtFechaFinal.getText().length() != 0) {
            Date fecFin = FarmaUtility.getStringToDate(txtFechaFinal.getText().trim(), "dd/MM/yyyy");

            if (!aceptaFechaAnterior) {
                if (fecSys.after(fecFin)) {
                    FarmaUtility.showMessage(dialogo, "La Fecha Vencimiento no puede ser anterior a la fecha actual",
                                             txtFechaFinal);
                    return false;
                }
            }

            if (fecIni.after(fecFin)) {
                FarmaUtility.showMessage(dialogo, "La Fecha Expedición no puede ser posterior a la Fecha Vencimiento",
                                         txtFechaInicial);
                return false;
            }

            if (!aceptaFechasIguales) {
                if (fecIni.equals(fecFin)) {
                    FarmaUtility.showMessage(dialogo, "La Fecha Vencimiento no puede ser igual a la Fecha Expedición",
                                             txtFechaFinal);
                    return false;
                }
            }


            if (!aceptaMismoDia) {
                if (fecFin.equals(fecSys)) {
                    FarmaUtility.showMessage(dialogo, "La Fecha Vencimiento no puede ser igual a la Fecha de Sistema",
                                             txtFechaInicial);
                    return false;
                }
            }

        }
        return true;
    }
}
