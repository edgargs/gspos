package mifarma.ptoventa.administracion.usuarios;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JPasswordField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSecurity;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgCambioClave extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgCambioClave.class);

    FarmaSecurity vSecurityLogin;

    Frame myParentFrame;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlDatosUsuario = new JPanelTitle();

    private JLabelWhite lblUsuario_T = new JLabelWhite();

    private JLabelWhite lblUsuario = new JLabelWhite();

    private JPanelTitle pnlCambioClave = new JPanelTitle();

    private JPasswordField txtContActual = new JPasswordField();

    private JPasswordField txtContNueva1 = new JPasswordField();

    private JPasswordField txtContNueva2 = new JPasswordField();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelFunction lblAceptar = new JLabelFunction();
    private JButtonLabel btnContrasenaActual = new JButtonLabel();
    private JButtonLabel btnNuevaContrasena = new JButtonLabel();
    private JButtonLabel btnConfirmeContrasena = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgCambioClave() {
        this(null, "", false);
    }

    public DlgCambioClave(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(433, 189));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Cambio de Clave");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlDatosUsuario.setBounds(new Rectangle(10, 10, 410, 40));
        lblUsuario_T.setText("Usuario :");
        lblUsuario_T.setBounds(new Rectangle(10, 10, 60, 15));
        lblUsuario.setText("VERA VEGA, JULIO CESAR");
        lblUsuario.setBounds(new Rectangle(80, 10, 325, 15));
        pnlCambioClave.setBounds(new Rectangle(10, 55, 410, 75));
        txtContActual.setBounds(new Rectangle(170, 5, 225, 20));
        txtContActual.setDocument(new FarmaLengthText(30));
        txtContActual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtContActual_keyPressed(e);
            }
        });
        txtContNueva1.setBounds(new Rectangle(170, 25, 225, 20));
        txtContNueva1.setDocument(new FarmaLengthText(30));
        txtContNueva1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtContNueva1_keyPressed(e);
            }
        });
        txtContNueva2.setBounds(new Rectangle(170, 45, 225, 20));
        txtContNueva2.setDocument(new FarmaLengthText(30));
        txtContNueva2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtContNueva2_keyPressed(e);
            }
        });
        lblSalir.setBounds(new Rectangle(310, 135, 110, 20));
        lblSalir.setText("[ESC] Cerrar");
        lblAceptar.setBounds(new Rectangle(190, 135, 110, 20));
        lblAceptar.setText("[F11] Aceptar");
        btnContrasenaActual.setText("Contraseña actual");
        btnContrasenaActual.setBounds(new Rectangle(10, 5, 115, 20));
        btnContrasenaActual.setMnemonic('a');
        btnContrasenaActual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnContrasenaActual_actionPerformed(e);
            }
        });
        btnNuevaContrasena.setText("Nueva contraseña");
        btnNuevaContrasena.setBounds(new Rectangle(10, 25, 115, 20));
        btnNuevaContrasena.setMnemonic('n');
        btnNuevaContrasena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNuevaContrasena_actionPerformed(e);
            }
        });
        btnConfirmeContrasena.setText("Confirme nueva contraseña");
        btnConfirmeContrasena.setBounds(new Rectangle(10, 45, 160, 20));
        btnConfirmeContrasena.setMnemonic('c');
        btnConfirmeContrasena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnConfirmeContrasena_actionPerformed(e);
            }
        });
        pnlDatosUsuario.add(lblUsuario, null);
        pnlDatosUsuario.add(lblUsuario_T, null);
        pnlCambioClave.add(btnConfirmeContrasena, null);
        pnlCambioClave.add(btnNuevaContrasena, null);
        pnlCambioClave.add(btnContrasenaActual, null);
        pnlCambioClave.add(txtContNueva2, null);
        pnlCambioClave.add(txtContNueva1, null);
        pnlCambioClave.add(txtContActual, null);
        jContentPane.add(lblAceptar, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(pnlCambioClave, null);
        jContentPane.add(pnlDatosUsuario, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;
        lblUsuario.setText(FarmaVariables.vPatUsu + " " + FarmaVariables.vMatUsu + ", " + FarmaVariables.vNomUsu);
    }

    // **************************************************************************
    // Métodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtContActual);
    }

    private void txtContActual_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtContNueva1);
        } else
            chkKeyPressed(e);
    }

    private void txtContNueva1_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtContNueva2);
        } else
            chkKeyPressed(e);
    }

    private void txtContNueva2_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtContActual);
        } else
            chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidos()) {

                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "¿Esta seguro de cambiar los datos?")) {
                    String clave = "";
                    String neoClave = "";
                    clave = new String(txtContActual.getPassword());
                    neoClave = new String(txtContNueva1.getPassword());
                    vSecurityLogin = new FarmaSecurity(FarmaVariables.vIdUsu, clave, neoClave);

                    if (!vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_OK)) {
                        if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_INACTIVO)) {
                            FarmaUtility.showMessage(this, "El usuario se encuentra Inactivo !!!", txtContActual);
                        } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_NO_REGISTRADO_LOCAL)) {
                            FarmaUtility.showMessage(this, "El usuario no se encuentra registrado en el Local !!!",
                                                     txtContActual);
                        } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_CLAVE_ERRADA)) {
                            FarmaUtility.showMessage(this, "La clave ingresada no es correcta !!!", txtContActual);
                        } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_NO_EXISTE)) {
                            FarmaUtility.showMessage(this, "El usuario ingresado no se encuentra registrado !!!",
                                                     txtContActual);
                        } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.ERROR_CONEXION_BD)) {
                            FarmaUtility.showMessage(this, "Error de conexión a la Base de Datos !!!", txtContActual);
                        } else if (vSecurityLogin.getLoginStatus().equalsIgnoreCase(FarmaConstants.LOGIN_CLAVE_IGUAL)) {
                            FarmaUtility.showMessage(this, "Debe ingresar una clave distinta a la actual.",
                                                     txtContActual);
                        }

                        cerrarVentana(false);
                    } else {
                        FarmaUtility.showMessage(this, "Operacion Realizada Existosamente", txtContActual);
                        cerrarVentana(true);
                    }
                }
            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

    private boolean datosValidos() {
        String strContActual = new String(txtContActual.getPassword());
        String strContNueva1 = new String(txtContNueva1.getPassword());
        String strContNueva2 = new String(txtContNueva2.getPassword());
        if (strContActual.trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese la contraseña actual", txtContActual);
            return false;
        }
        if (strContNueva1.trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese la nueva contraseña", txtContNueva1);
            return false;
        }

        if (strContNueva2.trim().length() == 0) {
            FarmaUtility.showMessage(this, "Confirme la nueva contraseña", txtContNueva2);
            return false;
        }

        if (!strContNueva1.trim().equals(strContNueva2.trim())) {
            FarmaUtility.showMessage(this, "La nueva contraseña deben no coincide con su confirmación", txtContNueva2);
            return false;
        }

        return true;
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnContrasenaActual_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtContActual);
    }

    private void btnNuevaContrasena_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtContNueva1);
    }

    private void btnConfirmeContrasena_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtContNueva2);
    }

}
