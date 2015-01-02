package mifarma.ptoventa.administracion.usuarios;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPasswordField;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.usuarios.reference.ConstantsUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.VariablesUsuarios;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMantUsuarios extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMantUsuarios.class);

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

    private JTextFieldSanSerif txtIdUsuario = new JTextFieldSanSerif();


    private JPasswordField txtClaveUsuario = new JPasswordField();

    private JTextFieldSanSerif txtApePat = new JTextFieldSanSerif();

    private JLabelWhite lblApeMat_T = new JLabelWhite();

    private JTextFieldSanSerif txtApeMat = new JTextFieldSanSerif();

    private JLabelWhite lblNombres_T = new JLabelWhite();

    private JTextFieldSanSerif txtNombres = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtFecNacimiento = new JTextFieldSanSerif();

    private JLabelWhite lblFecNac_T = new JLabelWhite();

    private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();

    private JLabelWhite lblDireccion_T = new JLabelWhite();

    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();

    private JLabelWhite lblTelefono_T = new JLabelWhite();

    private JButtonLabel btlNumEmpleado = new JButtonLabel();

    private JButtonLabel btnApePat = new JButtonLabel();

    private JButtonLabel btnIdUsuario = new JButtonLabel();
    private JLabelWhite lblNumSec = new JLabelWhite();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JLabelWhite lblFecNac_T1 = new JLabelWhite();
    private JButtonLabel btnClaveUsuario = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMantUsuarios() {
        this(null, "", false);
    }

    public DlgMantUsuarios(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(670, 327));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento de Usuarios");
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
        pnlDatosUsuario.setBounds(new Rectangle(10, 75, 645, 195));
        pnlDatosUsuario.setBackground(Color.white);
        pnlDatosUsuario.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDatosUsuario.setFocusable(false);
        lblAceptar.setBounds(new Rectangle(455, 275, 90, 20));
        lblAceptar.setText("[F11] Aceptar");
        lblSalir.setBounds(new Rectangle(560, 275, 95, 20));
        lblSalir.setText("[ESC] Cerrar");
        lblNumSecUsuario.setText("Num. Sec. Usuario");
        lblNumSecUsuario.setBounds(new Rectangle(10, 5, 135, 20));
        txtNroEmpleado.setBounds(new Rectangle(155, 30, 135, 20));
        txtNroEmpleado.setEditable(false);
        txtNroEmpleado.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                txtNroEmpleado_keyPressed(e);

            }

            public void keyTyped(KeyEvent e) {
                txtNroEmpleado_keyTyped(e);
            }
        });
        txtIdUsuario.setBounds(new Rectangle(300, 30, 135, 20));
        txtIdUsuario.setDocument(new FarmaLengthText(30));
        txtIdUsuario.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtIdUsuario_focusLost(e);
            }
        });
        txtIdUsuario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtIdUsuario_keyPressed(e);
            }

            /**
             * SOLO LETRAS
             * @author : dubilluz
             * @since  : 20.08.2007
             */
            public void keyTyped(KeyEvent e) {
                txtIdUsuario_keyTyped(e);
            }

        });
        txtClaveUsuario.setBounds(new Rectangle(445, 30, 135, 20));
        txtClaveUsuario.setDocument(new FarmaLengthText(30));
        txtClaveUsuario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtClaveUsuario_keyPressed(e);
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
        btnIdUsuario.setText("Id. Usuario");
        btnIdUsuario.setBounds(new Rectangle(305, 5, 120, 20));
        btnIdUsuario.setMnemonic('u');
        btnIdUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnIdUsuario_actionPerformed(e);
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
        btnClaveUsuario.setText("Clave Usuario");
        btnClaveUsuario.setBounds(new Rectangle(445, 5, 120, 20));
        btnClaveUsuario.setMnemonic('C');
        btnClaveUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnClaveUsuario_actionPerformed(e);
            }
        });
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblAceptar, null);
        jContentPane.add(pnlDatosUsuario, null);
        pnlDatosUsuario.add(lblFecNac_T1, null);
        pnlDatosUsuario.add(txtDni, null);
        pnlDatosUsuario.add(btnApePat, null);
        pnlDatosUsuario.add(lblTelefono_T, null);
        pnlDatosUsuario.add(lblDireccion_T, null);
        pnlDatosUsuario.add(txtDireccion, null);
        pnlDatosUsuario.add(lblFecNac_T, null);
        pnlDatosUsuario.add(txtFecNacimiento, null);
        pnlDatosUsuario.add(txtNombres, null);
        pnlDatosUsuario.add(lblNombres_T, null);
        pnlDatosUsuario.add(txtApeMat, null);
        pnlDatosUsuario.add(lblApeMat_T, null);
        pnlDatosUsuario.add(txtApePat, null);
        pnlDatosUsuario.add(txtTelefono, null);
        pnlIdentUsuario.add(btnClaveUsuario, null);
        pnlIdentUsuario.add(lblNumSec, null);
        pnlIdentUsuario.add(btnIdUsuario, null);
        pnlIdentUsuario.add(btlNumEmpleado, null);
        pnlIdentUsuario.add(txtClaveUsuario, null);
        pnlIdentUsuario.add(txtIdUsuario, null);
        pnlIdentUsuario.add(txtNroEmpleado, null);
        pnlIdentUsuario.add(lblNumSecUsuario, null);
        jContentPane.add(pnlIdentUsuario, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

    private void txtNroEmpleado_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (txtNroEmpleado.getText().length() == 0) {
                DlgBuscaTrabajador dlgBuscaTrab = new DlgBuscaTrabajador(myParentFrame, "Buscar Trabajador", true);
                dlgBuscaTrab.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    limpiarCajas();
                    //Modificado para visualizar
                    //txtNroEmpleado.setText(VariablesUsuarios.vCodTrab);
                    txtNroEmpleado.setText(VariablesUsuarios.vCodTrab_RRHH);
                } else {
                    VariablesUsuarios.vCodTrab = "";
                }
                log.debug("Cod Trab: " + VariablesUsuarios.vCodTrab);
            } else {
                if (txtNroEmpleado.getText().length() > 0) {
                    VariablesUsuarios.vSecTrab = txtNroEmpleado.getText();
                    //se verifica si existe el usuario
                    //27.11.2007  dubilluz  modificacion
                    if (existeUsuario(VariablesUsuarios.vSecTrab)) {
                        log.debug("El usuario existe en maestros");
                        if (!esUsuarioDuplicado()) {
                            obtenerDatosUsuario();
                            //SE MODIFICO AHORA SOLO EL IDUSUARIO Y CLAVE
                            //27.11.2007 DUBILLUZ MODIFICACION
                            habilitaTexto(false);
                            FarmaUtility.moveFocus(txtIdUsuario);
                            //FarmaUtility.moveFocus(txtApePat);
                        } else {
                            FarmaUtility.showMessage(this, "El usuario ya está registrado", txtNroEmpleado);
                            limpiarCajas();
                            txtIdUsuario.setText("");
                            txtClaveUsuario.setText("");
                            habilitaTexto(false);
                            FarmaUtility.moveFocus(txtNroEmpleado);
                        }
                    } else {
                        log.debug("El usuario no existe en maestros");
                        log.debug("Verificara si se validara al usuario");
                        txtIdUsuario.setText("");
                        txtClaveUsuario.setText("");
                        if (!isValidarUsuario()) {
                            String codigo = txtNroEmpleado.getText();
                            limpiarCajas();
                            habilitaTexto(true);
                            txtNroEmpleado.setText(codigo.trim());
                            FarmaUtility.moveFocus(txtIdUsuario);
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "No puede ingresar al usuario porque no esta registrado en la lista de RR.HH.",
                                                     txtNroEmpleado);
                            limpiarCajas();
                            habilitaTexto(false);
                            FarmaUtility.moveFocus(txtNroEmpleado);

                        }
                    }
                } else {
                    FarmaUtility.moveFocus(txtIdUsuario);
                }
            }

        }

        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);

        cargarDatos();
        /**
     * Valida accion al modificar datos de usuario
     * @author : JCORTEZ
     * @since  : 20.07.2007
     */
        log.debug("VariablesUsuarios.vF3 " + VariablesUsuarios.vF3);
        txtNroEmpleado.setEnabled(true);
        if (VariablesUsuarios.vF3) {
            habilitarCodigo();
            txtNroEmpleado.setEnabled(false);
            if (VariablesUsuarios.vModificarUsuario.length() > 0) {
                if (VariablesUsuarios.vModificarUsuario.equalsIgnoreCase("S")) {
                    habilitaTexto(true);
                    FarmaUtility.moveFocus(txtApePat);
                } else {
                    habilitaTexto(false);
                    FarmaUtility.showMessage(this,
                                             "No puede modificar los datos del trabajador " + "porque se encuentra registrado en RR.HH.\n" +
                            "Si desea modificar sus datos comuniquese con RR.HH.", txtClaveUsuario);
                }
            }
        } else {
            habilitaTexto(false);
            // KMONCADA 10.11.2014 INVOCA EL EVENTO ENTER PARA CARGAR PANTALLA DE BUSQUEDA DE USUARIOS
            KeyEvent ke = new KeyEvent(txtNroEmpleado, KeyEvent.KEY_PRESSED, 0, // When timeStamp
                    0, // Modifier
                    KeyEvent.VK_ENTER, // Key Code
                    KeyEvent.CHAR_UNDEFINED); // Key Char

            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(ke);
        }
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
     * @since  28.11.2007
     */
    private void txtDni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtDni, e);
    }

    private void txtTelefono_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
        //FarmaUtility.admitirSoloDigitos(e,txtTelefono,txtTelefono.getText().length()-1,this);
    }


    private void txtIdUsuario_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtIdUsuario.setText(txtIdUsuario.getText().toUpperCase());
            FarmaUtility.moveFocus(txtClaveUsuario);

        }
        chkKeyPressed(e);
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
                FarmaUtility.moveFocus(txtClaveUsuario);
            } else {
                FarmaUtility.moveFocus(txtIdUsuario);
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

    private void txtClaveUsuario_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtApePat.isEnabled())
                FarmaUtility.moveFocus(txtApePat);
            else {
                if (txtIdUsuario.isEnabled())
                    FarmaUtility.moveFocus(txtIdUsuario);
            }

        }
        chkKeyPressed(e);
    }

    private void txtFecNacimiento_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecNacimiento, e);
    }

    private void txtIdUsuario_focusLost(FocusEvent e) {
        txtIdUsuario.setText(txtIdUsuario.getText().toUpperCase());
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

    /**
     * PARA EL IDUSUARIO
     * @author : dubilluz
     * @since  : 20.08.2007
     */
    private void txtIdUsuario_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirSoloLetras(e,txtIdUsuario,txtIdUsuario.getText().length()-1,this);
        FarmaUtility.admitirLetras(txtIdUsuario, e);
    }

    private void btnIdUsuario_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtIdUsuario);
    }

    private void btlNumEmpleado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroEmpleado);
    }


    private void txtFecNacimiento_actionPerformed(ActionEvent e) {
        if (VariablesUsuarios.vActiCod) //si esta vacio
        {
            FarmaUtility.moveFocus(txtApePat); //txtNroEmpleado
        } else {
            FarmaUtility.moveFocus(txtApePat);
        }

    }


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            log.debug("CodTrab a insertar: " + VariablesUsuarios.vCodTrab);
            if (datosValidados()) {
                boolean haInsertado = false;

                if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que desea grabar los datos del usuario?")) {

                    try {

                        if (existenDatosUsuario()) {
                            actualizaUsuario();
                        } else {
                            VariablesUsuarios.vCodTrab_RRHH = txtNroEmpleado.getText();
                            haInsertado = true;
                            insertarUsuario();
                        }
                        if (haInsertado)
                            actualizaNumeracionUsuario();

                        FarmaUtility.aceptarTransaccion();
                        FarmaUtility.showMessage(this, "La operación se realizó correctamente", txtApePat);
                        cerrarVentana(true);
                        VariablesUsuarios.vCodTrab = "";

                    } catch (SQLException ex) {
                        FarmaUtility.liberarTransaccion();
                        log.error("", ex);
                        String mensaje = ex.getMessage();
                        if (ex.getErrorCode() == 20014) {
                            mensaje = "El login especificado ya existe";
                        }
                        FarmaUtility.showMessage(this, "Error al grabar Usuario: \n" +
                                mensaje, txtApePat);
                    }
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            limpiarDatosTrabajador();
            cerrarVentana(false);
            VariablesUsuarios.vCodTrab = "";
            VariablesUsuarios.vfocus = false;
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

    private boolean datosValidados() {

        if (!VariablesUsuarios.vAccionCrear) {
            if (!FarmaVariables.vNuSecUsu.trim().equalsIgnoreCase(lblNumSec.getText().trim())) {
                FarmaUtility.showMessage(this, "Solo el mismo usuario puede cambiar su clave.", txtClaveUsuario);
                return false;
            }
        }


        if (!existenDatosUsuario()) {
            if (tieneLoginDuplicado()) {
                FarmaUtility.showMessage(this, "El login del usuario ya está registrado", txtIdUsuario);
                return false;
            }

            if (esUsuarioDuplicado()) {
                FarmaUtility.showMessage(this, "El numero del usuario ya está registrado", txtNroEmpleado);
                return false;
            }
        }
        /*if (VariablesUsuarios.vCodTrab.trim().length()==0) {
			FarmaUtility.showMessage(this, "Ingrese el Nro. del Empleado",
					txtNroEmpleado);
			return false;
		}*/
        //Valida que el Nro De Trab.RRHH
        //28.11.2007  dubilluz modificacion
        //ERIOS 19.11.2014 Se dehabilita la validacion
        /*if(!isValidoCodTrabRRHH(txtNroEmpleado.getText().trim()))
    {
			FarmaUtility.showMessage(this, "Ingrese el numero de Trabajador en el formato correcto.",
					txtNroEmpleado);
			return false;
    }*/
        if (txtIdUsuario.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese el Id de Usuario", txtIdUsuario);
            return false;
        }
        String strClaveUsuario = new String(txtClaveUsuario.getPassword());
        if (strClaveUsuario.length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese la clave de Usuario", txtClaveUsuario);
            return false;
        }

        if (txtApePat.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese el Apellido Paterno", txtApePat);
            return false;
        }

        if (txtApeMat.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese el Apellido Materno", txtApeMat);
            return false;
        }

        if (txtNombres.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese los nombres", txtNombres);
            return false;
        }

        if (txtDireccion.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese la dirección", txtDireccion);
            return false;
        }

        if (txtTelefono.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese el teléfono", txtTelefono);
            return false;
        }

        if (txtDni.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese el Numero de DNI", txtDni);
            return false;
        }

        if (!FarmaUtility.isInteger(txtDni.getText().trim())) {
            FarmaUtility.showMessage(this, "Solo se debe admitir números en el DNI", txtDni);
            return false;
        }

        if (txtDni.getText().trim().length() < 8) {
            FarmaUtility.showMessage(this, "El número de DNI debe contener ocho digitos. Verifique!!", txtDni);
            return false;
        }

        if (txtFecNacimiento.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese la fecha de nacimiento", txtFecNacimiento);
            return false;
        }

        if (!FarmaUtility.validaFecha(txtFecNacimiento.getText(), "dd/MM/yyyy") ||
            txtFecNacimiento.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha de Nacimiento correctamente", txtFecNacimiento);
            return false;
        }

        return true;
    }

    private void cargarDatos() {

        if (!VariablesUsuarios.vSecUsuLocal.equals("")) {
            //txtNroEmpleado.setText(VariablesUsuarios.vSecUsuLocal.toUpperCase().trim());
            //Modificado
            //28.11.2007 dubilluz modificacion
            txtNroEmpleado.setText(VariablesUsuarios.vCodTrab_RRHH.toUpperCase().trim());
            txtIdUsuario.setText(VariablesUsuarios.vLoginUsu.toUpperCase().trim());
            txtClaveUsuario.setText(VariablesUsuarios.vClaveUsu.trim());
            txtApePat.setText(VariablesUsuarios.vApePat.toUpperCase().trim());
            txtApeMat.setText(VariablesUsuarios.vApeMat.toUpperCase().trim());
            txtNombres.setText(VariablesUsuarios.vNombres.toUpperCase().trim());
            txtDireccion.setText(VariablesUsuarios.vDireccion.toUpperCase().trim());
            txtTelefono.setText(VariablesUsuarios.vTelefono.toUpperCase().trim());
            txtFecNacimiento.setText(VariablesUsuarios.vFecNac.toUpperCase().trim());
            txtDni.setText(VariablesUsuarios.vDNI.trim());
            lblNumSec.setText(VariablesUsuarios.vSecUsuLocal.toUpperCase());
            txtNroEmpleado.setEnabled(false);
            txtIdUsuario.setEnabled(false);
            //txtClaveUsuario.setEnabled(false);
            //txtNroEmpleado.setText(VariablesUsuarios.vCodTrab);
            //FarmaUtility.moveFocus(txtNroEmpleado);
            FarmaUtility.moveFocus(txtApePat);
        } else {
            FarmaUtility.moveFocus(txtNroEmpleado);
        }

    }

    private boolean existenDatosUsuario() {

        if (VariablesUsuarios.vLoginUsu.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean esUsuarioDuplicado() {

        String rpta = "0";

        if (txtNroEmpleado.getText().trim().length() == 0) {
            return false;
        }

        try {
            rpta = DBUsuarios.verificaDuplicidadNumUsuario(txtNroEmpleado.getText());
        } catch (SQLException ex) {
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            String mensaje = ex.getMessage();

            FarmaUtility.showMessage(this, "No se pudo determinar la duplicidad del usuario: \n" +
                    mensaje, txtApePat);
        }

        if (rpta.trim().equals("0")) {
            return false;
        } else {
            return true;
        }

    }

    private boolean tieneLoginDuplicado() {

        String rpta = "0";

        try {

            rpta = DBUsuarios.verificaDuplicidadLoginUsuario(txtIdUsuario.getText());
        } catch (SQLException ex) {
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            String mensaje = ex.getMessage();
            FarmaUtility.showMessage(this, "No se pudo determinar la duplicidad del login del usuario: \n" +
                    mensaje, txtApePat);
        }

        if (rpta.trim().equals("0")) {
            return false;
        } else {
            return true;
        }

    }

    private void insertarUsuario() throws SQLException {
        log.debug("insertarUsuario");
        DBUsuarios.ingresaUsuario(VariablesUsuarios.vCodTrab,
                                  /*txtNroEmpleado.getText().trim()*/txtNombres.getText().trim().toUpperCase(),
                                  txtApePat.getText().trim().toUpperCase(), txtApeMat.getText().trim().toUpperCase(),
                                  txtIdUsuario.getText().trim().toUpperCase(),
                                  new String(txtClaveUsuario.getPassword()).trim(),
                                  txtTelefono.getText().trim().trim(), txtDireccion.getText().trim().toUpperCase(),
                                  txtFecNacimiento.getText().trim(), txtDni.getText().trim(),
                                  VariablesUsuarios.vCodTrab_RRHH.trim());

    }

    private void actualizaUsuario() throws SQLException {
        log.debug("actualizaUsuario");
        DBUsuarios.actualizaUsuario(VariablesUsuarios.vSecUsuLocal, VariablesUsuarios.vCodTrab,
                //txtNroEmpleado.getText().trim(),
                txtNombres.getText().trim(), txtApePat.getText().trim().toUpperCase(),
                txtApeMat.getText().trim().toUpperCase(), txtIdUsuario.getText().trim().toUpperCase(),
                new String(txtClaveUsuario.getPassword()).trim(), txtTelefono.getText().trim(),
                txtDireccion.getText().trim().toUpperCase(), txtFecNacimiento.getText().trim(),
                txtDni.getText().trim());
    }

    private void actualizaNumeracionUsuario() {

        try {
            FarmaSearch.updateNumera(ConstantsUsuarios.TIP_NUMERA_USUARIO);
        } catch (SQLException ex) {
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            String mensaje = ex.getMessage();
            FarmaUtility.showMessage(this, "Ocurrió un error en la transacción: \n" +
                    mensaje, txtApePat);
        }

    }

    private void obtenerDatosUsuario() {

        ArrayList datos = new ArrayList();
        log.debug("Obteniendo datos de usuario");
        try {
            DBUsuarios.getDatosTrab(datos);
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
            VariablesUsuarios.vCodTrab = myArray2.get(0).toString().trim();
            log.debug("Dato codTrab: " + VariablesUsuarios.vCodTrab);
            //txtNroEmpleado.setText(VariablesUsuarios.vCodTrab);
            txtApePat.setText(myArray2.get(1).toString().trim());
            txtApeMat.setText(myArray2.get(2).toString().trim());
            txtNombres.setText(myArray2.get(3).toString().trim());
            txtTelefono.setText(myArray2.get(4).toString().trim());
            txtDireccion.setText(myArray2.get(5).toString().trim());
            txtFecNacimiento.setText(myArray2.get(6).toString().trim());
            //Se coloca el DNI del trabajador
            //29.11.2007  dubilluz  modificacion
            txtDni.setText(myArray2.get(7).toString().trim());
            String strClaveUsuario = new String(txtClaveUsuario.getPassword());
            if (txtIdUsuario.getText().trim().equalsIgnoreCase("") && strClaveUsuario.trim().equalsIgnoreCase("")) {
                txtIdUsuario.setText(txtNombres.getText().trim().substring(0, 1) + txtApePat.getText().trim());
                txtIdUsuario.setText(txtIdUsuario.getText().trim().toUpperCase());
                txtClaveUsuario.setText(txtIdUsuario.getText().trim());
            }

            FarmaUtility.moveFocus(txtApePat);

        } else {
            FarmaUtility.showMessage(this, "Usuario no encontrado", txtNroEmpleado);
            limpiarCajas();
            FarmaUtility.moveFocus(txtNroEmpleado);
        }

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
        if (!VariablesUsuarios.vF3) {
            txtIdUsuario.setText("");
            txtClaveUsuario.setText("");
        }


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

    /** Se habilita el campo de Codigo de trabajador
     * @author:  JCortez
     * @since:   03.07.07
     */
    private void habilitarCodigo() {
        boolean focus = VariablesUsuarios.vfocus;
        if (focus) {
            if (VariablesUsuarios.vActiCod) // si esta vacio
            {
                txtNroEmpleado.setEnabled(true);
                FarmaUtility.moveFocus(txtNroEmpleado);
            } else {
                txtNroEmpleado.setEnabled(false);
                FarmaUtility.moveFocus(txtApePat);
            }
        } else {
            FarmaUtility.moveFocus(txtIdUsuario);
        }

    }

    private void btnClaveUsuario_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtClaveUsuario);
    }

    /**
     * Habilita los campos de textos para ingreso de usuario
     * @author dubilluz
     * @since  27.11.2007
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

    /**
     * Verifica si el usuario existe en la tabla de maestros
     * @author dubilluz
     * @since  27.11.2007
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
     * @author dubilluz
     * @since  27.11.2007
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

    /**
     * Valida el codigo de trabajador de RRHH
     * @author dubilluz
     * @since  28.11.2007
     */
    public boolean isValidoCodTrabRRHH(String codigo) {
        boolean valor = true;
        codigo = codigo.trim();
        if (codigo.length() != 6)
            valor = false;
        /* else
    {
      log.debug("Primeras dos cifras : " + codigo.substring(0,2) );
      if(!codigo.substring(0,2).equals("10"))
         valor = false;
    } */
        return valor;
    }

}
