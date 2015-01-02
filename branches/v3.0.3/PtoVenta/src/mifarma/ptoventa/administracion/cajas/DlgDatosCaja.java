package mifarma.ptoventa.administracion.cajas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.cajas.reference.DBCajas;
import mifarma.ptoventa.administracion.cajas.reference.VariablesCajas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDatosCaja extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDatosCaja.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlDatosCaja = new JPanelTitle();

    private JLabelFunction lblSalir = new JLabelFunction();

    private JLabelFunction lblAceptar = new JLabelFunction();

    private JLabelWhite lblNroCaja_T = new JLabelWhite();

    private JTextFieldSanSerif txtNroCaja = new JTextFieldSanSerif();

    private JLabelWhite lblDescripCaja_T = new JLabelWhite();

    private JTextFieldSanSerif txtDescripCaja = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtDesUsu = new JTextFieldSanSerif();


    private JTextFieldSanSerif txtSecUsu = new JTextFieldSanSerif();
    private JButtonLabel btnCajero = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgDatosCaja() {
        this(null, "", false);
    }

    public DlgDatosCaja(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(487, 170));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Caja de Pago");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlDatosCaja.setBounds(new Rectangle(10, 10, 460, 95));
        lblSalir.setBounds(new Rectangle(370, 115, 100, 20));
        lblSalir.setText("[ESC] Cerrar");
        lblAceptar.setBounds(new Rectangle(260, 115, 100, 20));
        lblAceptar.setText("[F11] Aceptar");
        lblNroCaja_T.setText("Nro. Caja :");
        lblNroCaja_T.setBounds(new Rectangle(10, 10, 65, 20));
        txtNroCaja.setBounds(new Rectangle(85, 10, 75, 20));
        txtNroCaja.setFocusable(false);
        txtNroCaja.setEditable(false);
        lblDescripCaja_T.setText("Descripción Caja :");
        lblDescripCaja_T.setBounds(new Rectangle(10, 40, 105, 20));
        txtDescripCaja.setBounds(new Rectangle(120, 40, 305, 20));
        txtDescripCaja.setEditable(false);
        txtDescripCaja.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtDescripCaja_focusLost(e);
            }
        });
        txtDescripCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDescripCaja_keyPressed(e);
            }
        });
        txtDesUsu.setBounds(new Rectangle(200, 65, 225, 20));
        txtDesUsu.setFocusable(false);
        txtDesUsu.setEditable(false);
        txtSecUsu.setBounds(new Rectangle(120, 65, 75, 20));
        txtSecUsu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSecUsu_keyPressed(e);
            }
        });
        btnCajero.setText("Cajero Asignado :");
        btnCajero.setBounds(new Rectangle(10, 65, 105, 20));
        btnCajero.setMnemonic('c');
        btnCajero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        jContentPane.add(lblAceptar, null);
        jContentPane.add(lblSalir, null);
        pnlDatosCaja.add(btnCajero, null);
        pnlDatosCaja.add(txtSecUsu, null);
        pnlDatosCaja.add(txtDesUsu, null);
        pnlDatosCaja.add(txtDescripCaja, null);
        pnlDatosCaja.add(lblDescripCaja_T, null);
        pnlDatosCaja.add(txtNroCaja, null);
        pnlDatosCaja.add(lblNroCaja_T, null);
        jContentPane.add(pnlDatosCaja, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;

    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtSecUsu);
        cargarDatos();
    }

    private void txtDescripCaja_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtSecUsu);
        }
        chkKeyPressed(e);
    }

    private void txtSecUsu_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCodigo();

        }

        chkKeyPressed(e);
    }

    private void txtDescripCaja_focusLost(FocusEvent e) {
        txtDescripCaja.setText(txtDescripCaja.getText().toUpperCase());
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSecUsu);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidados())
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "¿Está seguro que desea grabar los datos ?")) {
                    try {
                        log.debug("existe caja>>> " + VariablesCajas.vNumCaja);
                        if (existenDatosCaja()) {
                            actualizaCaja();
                        } else {

                            insertarCaja();
                            actualizaNumeracionCajas();
                        }

                        FarmaUtility.aceptarTransaccion();
                        FarmaUtility.showMessage(this, "La operación se realizó correctamente", txtDescripCaja);

                    } catch (SQLException ex) {
                        String mensaje = "";
                        if (ex.getErrorCode() == 20006) {
                            mensaje = "El usuario ya ha sido asignado a una caja";
                        } else if (ex.getErrorCode() == 20007) {
                            mensaje = "No se encontraron impresoras suficientes para asignar a la caja";
                        } else if (ex.getErrorCode() == 20008) {
                            mensaje = "No se puede modificar una caja aperturada";
                        } else if (ex.getErrorCode() == 20009) {
                            mensaje = "No se puede inactivar una caja aperturada";
                        } else {
                            mensaje = ex.getMessage();
                        }
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this, "Ocurrió un error en la transacción: \n" +
                                mensaje, txtDescripCaja);
                        log.error("", ex);
                    }
                    cerrarVentana(true);
                }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }

    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaCajeros() {
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargarDatos() {

        if (!VariablesCajas.vNumCaja.equals("")) {
            txtNroCaja.setText(VariablesCajas.vNumCaja.toUpperCase());
            txtDescripCaja.setText(VariablesCajas.vDesCaja.toUpperCase());
            txtSecUsu.setText(VariablesCajas.vSecUsuAsig.toUpperCase());
            txtDesUsu.setText(VariablesCajas.vDesUsuAsig.toUpperCase());

            FarmaUtility.moveFocus(txtDescripCaja);

        } else {
            txtDescripCaja.setText("<<Autogenerado>>");
        }
    }

    private boolean existenDatosCaja() {
        if (VariablesCajas.vNumCaja.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void insertarCaja() throws SQLException {

        DBCajas.ingresaCaja(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, txtSecUsu.getText().trim(),
                            txtDescripCaja.getText().trim());

    }

    private void actualizaCaja() throws SQLException {
        DBCajas.actualizaCaja(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesCajas.vNumCaja.trim(),
                              txtSecUsu.getText().trim(), txtDescripCaja.getText().trim());

    }

    private void actualizaNumeracionCajas() throws SQLException {
        FarmaSearch.updateNumera(FarmaConstants.COD_NUMERA_CAJA);
    }

    private boolean datosValidados() {

        if (txtDescripCaja.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese la descripción de la caja", txtDescripCaja);
            return false;
        }

        if (txtSecUsu.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe ingresar el usuario", txtSecUsu);
            return false;
        }

        if (txtSecUsu.getText().trim().length() > 0 && txtDesUsu.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe validar el usuario", txtSecUsu);
            return false;
        }

        return true;
    }

    private void validarCodigo() {
        // getListaUsuariosArray
        if (txtSecUsu.getText().trim().length() == 0) {
            DlgListaUsuAsig dlgListaUsuAsig = new DlgListaUsuAsig(myParentFrame, "", true);
            dlgListaUsuAsig.setVisible(true);

            if (FarmaVariables.vAceptar) {
                txtSecUsu.setText(VariablesCajas.vSecUsuAsig);
                txtDesUsu.setText(VariablesCajas.vDesUsuAsig);
                mifarma.common.FarmaVariables.vAceptar = false;
            }
        } else {
            // se ha ingresado un codigo
            ArrayList listaUser = new ArrayList();
            ArrayList tmpElement = new ArrayList();

            try {
                listaUser = DBCajas.getListaUsuariosArray(txtSecUsu.getText().trim());
            } catch (SQLException e) {
                log.error("", e);
                listaUser = new ArrayList();
                FarmaUtility.showMessage(this, "Error al obtener usuarios. \n " + e.getMessage(), txtDescripCaja);
            }

            if (listaUser.size() == 0) {
                txtSecUsu.setText("");
                FarmaUtility.showMessage(this, "Usuario no encontrado o no disponible", txtSecUsu);
            }
            if (listaUser.size() > 1) {
                FarmaUtility.showMessage(this, "El criterio de búsqueda ha devuelto más de un resultado", txtSecUsu);
            }
            if (listaUser.size() == 1) {

                tmpElement = (ArrayList)listaUser.get(0);

                txtSecUsu.setText(tmpElement.get(0).toString().trim());
                txtDesUsu.setText(tmpElement.get(1).toString().trim());
            }

        }

    }


}
