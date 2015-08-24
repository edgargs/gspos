package mifarma.ptoventa.convenioBTLMF;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.JTextField;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.ValidaDatoIngreso;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;

import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.UtilityPtoVenta;

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


public class DlgDatoBenificiarioBTLMF extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgDatoBenificiarioBTLMF.class);

    Frame myParentFrame;

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle pnlDatosLaboratorio = new JPanelTitle();

    private JButtonLabel lblNombre = new JButtonLabel();
    private JButtonLabel lblApePat = new JButtonLabel();
    private JButtonLabel lblApeMat = new JButtonLabel();
    private JButtonLabel lblDNI = new JButtonLabel();
    private JButtonLabel lblFecNacimiento = new JButtonLabel();


    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApePat = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApeMat = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecNacimiento = new JTextFieldSanSerif();

    private JLabelFunction btnEsc = new JLabelFunction();
    private JLabelFunction btnF1 = new JLabelFunction();
    private JButtonLabel lblTelefono =  new JButtonLabel();
    private JButtonLabel lblEmail = new JButtonLabel();
    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtEmail = new JTextFieldSanSerif();
    
    private String nroTarjeta;

    public DlgDatoBenificiarioBTLMF() {
        this(null, "", false);
    }

    public DlgDatoBenificiarioBTLMF(JDialog myParentFrame2, String title, boolean modal) {
        super(myParentFrame2, title, modal);

        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(369, 263));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Crear Nuevo Beneficiario");
        this.addWindowListener(new WindowAdapter() {

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        jPanelWhite1.setLayout(null);

        pnlDatosLaboratorio.setBounds(new Rectangle(15, 10, 340, 195));
        pnlDatosLaboratorio.setBackground(Color.white);
        pnlDatosLaboratorio.setFont(new Font("SansSerif", 0, 11));
        pnlDatosLaboratorio.setLayout(null);
        pnlDatosLaboratorio.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDatosLaboratorio.setBackground(Color.white);

        
        
        lblDNI.setText("DNI : ");
        lblDNI.setForeground(new Color(255, 130, 14));
        lblDNI.setSize(new Dimension(55, 15));
        lblDNI.setMnemonic('d');
        lblDNI.setBounds(new Rectangle(10, 10, 55, 15));
        
        txtDni.setFont(new Font("SansSerif", 0, 11));
        txtDni.setSize(new Dimension(135, 19));
        txtDni.setDocument(new ValidaDatoIngreso(this, ConstantsConvenioBTLMF.CLASE_DNI, 11));
        txtDni.setBounds(new Rectangle(120, 10, 185, 20));
        txtDni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtKeyPressed(e, txtDni, txtApePat);
                //txtDni_keyPressed(e);
            }
        });

        lblApePat.setText("Apellido Paterno: ");
        //lblApePat.setBackground(Color.orange);
        //lblApePat.setLocation(new Point(11, 40));
        lblApePat.setSize(new Dimension(105, 15));
        lblApePat.setForeground(new Color(255, 130, 14));
        lblApePat.setBounds(new Rectangle(11, 35, 105, 15));
        
        txtApePat.setFont(new Font("SansSerif", 0, 11));
        //txtApePat.setLocation(new Point(121, 37));
        txtApePat.setSize(new Dimension(135, 20));
        txtApePat.setBounds(new Rectangle(120, 35, 205, 20));
        txtApePat.setDocument(new FarmaLengthText(30));
        txtApePat.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtKeyPressed(e, txtApePat, txtApeMat);
                //txtApePat_keyPressed(e);
            }
        });
        
        lblApeMat.setText("Apellido Materno: ");
        lblApeMat.setForeground(new Color(255, 130, 14));
        lblApeMat.setLocation(new Point(11, 65));
        lblApeMat.setSize(new Dimension(105, 15));
        lblApeMat.setBounds(new Rectangle(11, 60, 105, 15));
        
        txtApeMat.setFont(new Font("SansSerif", 0, 11));
        txtApeMat.setLocation(new Point(121, 64));
        txtApeMat.setSize(new Dimension(135, 20));
        txtApeMat.setBounds(new Rectangle(120, 60, 205, 20));
        txtApeMat.setDocument(new FarmaLengthText(30));
        txtApeMat.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtKeyPressed(e, txtApeMat, txtNombre);
                //txtApeMat_keyPressed(e);
            }
        });
        
        lblNombre.setText("Nombre : ");
        lblNombre.setBackground(Color.orange);
        lblNombre.setLocation(new Point(11, 15));
        lblNombre.setSize(new Dimension(55, 15));
        lblNombre.setForeground(new Color(255, 130, 14));
        lblNombre.setMnemonic('n');
        lblNombre.setBounds(new Rectangle(10, 85, 55, 15));
        lblNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNombre_actionPerformed(e);
            }
        });
        
        txtNombre.setFont(new Font("SansSerif", 0, 11));
        txtNombre.setLocation(new Point(121, 10));
        txtNombre.setSize(new Dimension(108, 20));
        txtNombre.setBounds(new Rectangle(120, 85, 205, 20));
        txtNombre.setDocument(new FarmaLengthText(30));
        txtNombre.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtKeyPressed(e, txtNombre, txtFecNacimiento);
                //txtNombre_keyPressed(e);
            }
        });
        
        lblFecNacimiento.setText("Fecha Nacimiento : ");
        lblFecNacimiento.setForeground(new Color(255, 130, 14));
        lblFecNacimiento.setLocation(new Point(11, 119));
        lblFecNacimiento.setSize(new Dimension(110, 15));
        lblFecNacimiento.setBounds(new Rectangle(11, 110, 110, 15));
        
        txtFecNacimiento.setBounds(new Rectangle(121, 110, 106, 20));
        txtFecNacimiento.setFont(new Font("SansSerif", 0, 11));
        txtFecNacimiento.setDocument(new ValidaDatoIngreso(this, ConstantsConvenioBTLMF.CLASE_FECHA_NAC, 10));
        txtFecNacimiento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtKeyPressed(e, txtFecNacimiento, txtTelefono);
                //txtFecNacimiento_keyPressed(e);
            }
        });
        
        lblTelefono.setForeground(new Color(255, 130, 14));
        lblTelefono.setSize(new Dimension(105, 15));
        lblTelefono.setLocation(new Point(11, 146));
        lblTelefono.setText("Telefono : ");
        lblTelefono.setBounds(new Rectangle(11, 135, 105, 15));
        
        txtTelefono.setDocument(new FarmaLengthText(120));
        txtTelefono.setSize(new Dimension(124, 20));
        txtTelefono.setLocation(new Point(121, 145));
        txtTelefono.setFont(new Font("SansSerif", 0, 11));
        txtTelefono.setBounds(new Rectangle(121, 135, 124, 20));
        txtTelefono.setDocument(new ValidaDatoIngreso(this, ConstantsConvenioBTLMF.CLASE_TELEFONO, 49));
        txtTelefono.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtKeyPressed(e, txtTelefono, txtEmail);
                //txtTelefono_keyPressed(e);
            }
        });
        
        lblEmail.setSize(new Dimension(58, 15));
        lblEmail.setLocation(new Point(11, 173));
        lblEmail.setText("Email : ");
        lblEmail.setForeground(new Color(255, 130, 14));
        lblEmail.setBounds(new Rectangle(11, 160, 58, 15));
        
        txtEmail.setSize(new Dimension(174, 20));
        txtEmail.setBounds(new Rectangle(120, 160, 174, 20));
        txtEmail.setLocation(new Point(121, 172));
        txtEmail.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtKeyPressed(e, txtEmail, txtDni);
            }
        });
        
                                           
        btnEsc.setText("[ESC] Cerrar");
        btnEsc.setLocation(new Point(314, 252));
        btnEsc.setSize(new Dimension(85, 19));

        btnEsc.setBounds(new Rectangle(260, 210, 95, 20));
        btnF1.setBounds(new Rectangle(155, 210, 95, 20));

        btnF1.setText("[ F11 ] Aceptar");
        pnlDatosLaboratorio.add(txtApeMat, null);
        pnlDatosLaboratorio.add(txtApePat, null);
        pnlDatosLaboratorio.add(txtNombre, null);
        pnlDatosLaboratorio.add(lblApeMat, null);
        pnlDatosLaboratorio.add(lblApePat, null);
        pnlDatosLaboratorio.add(lblNombre, null);
        pnlDatosLaboratorio.add(lblFecNacimiento, null);
        pnlDatosLaboratorio.add(txtDni, null);
        pnlDatosLaboratorio.add(txtFecNacimiento, null);
        pnlDatosLaboratorio.add(lblDNI, null);
        pnlDatosLaboratorio.add(lblTelefono, null);
        pnlDatosLaboratorio.add(lblEmail, null);
        pnlDatosLaboratorio.add(txtTelefono, null);
        pnlDatosLaboratorio.add(txtEmail, null);
        jPanelWhite1.add(btnF1, null);
        jPanelWhite1.add(btnEsc, null);
        jPanelWhite1.add(pnlDatosLaboratorio, null);
        this.getContentPane().add(jPanelWhite1);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni);
    }

    private void crearBenificiario() {
        log.debug("<<<<<<<<<Metodo:crearBenificiario>>>>>>>>>>>>>>");
        try {
            Map convenio = DBConvenioBTLMF.obtenerConvenio(VariablesConvenioBTLMF.vCodConvenio);
            String lineaCredito = (String)convenio.get(ConstantsConvenioBTLMF.COL_MTO_LINEA_CRED_BASE);

            if (VariablesConvenioBTLMF.vFlgCreacionCliente.equals(ConstantsConvenioBTLMF.FLG_SOLICITUD_BENIF)) {

                DBConvenioBTLMF.solicitarBenificiario(VariablesConvenioBTLMF.vNombre,
                                                      VariablesConvenioBTLMF.vApellidoPat,
                                                      VariablesConvenioBTLMF.vApellidoMat, VariablesConvenioBTLMF.vDni,
                                                      VariablesConvenioBTLMF.vTelefono, VariablesConvenioBTLMF.vEmail,
                                                      VariablesConvenioBTLMF.vFechaNac);

                FarmaUtility.showMessage(this,
                                         "Se ha solicitado la creacion del Benificiario con Exito. Pendiente de Aprobación",
                                         null);
                VariablesConvenioBTLMF.vCodigo = VariablesConvenioBTLMF.vDni;
                VariablesConvenioBTLMF.vDescripcion =
                        txtNombre.getText().trim().toUpperCase() + " " + txtApePat.getText().trim().toUpperCase() +
                        " " + txtApeMat.getText().trim().toUpperCase();
                // Map benificiario = (Map)UtilityConvenioBTLMF.obtieneBenificiario(VariablesConvenioBTLMF.vCodConvenio,VariablesConvenioBTLMF.vDni, this);
                //VariablesConvenioBTLMF.vCodCliente  = (String)benificiario.get(ConstantsConvenioBTLMF.COL_COD_CLIENTE);
                cerrarVentana(false);
            } else {
                
                String indConexionRac = "N";
                try {
                    FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
                    indConexionRac = (facadeRecaudacion.validarConexionRAC()) ? "S" : "N";
                } catch (Exception e) {
                    ;
                }
                
                if("S".equalsIgnoreCase(indConexionRac)){
                    // GRABA EN EL RAC.
                    String codCliente =
                        DBConvenioBTLMF.crearBenificiario(VariablesConvenioBTLMF.vNombre, VariablesConvenioBTLMF.vApellidoPat,
                                                          VariablesConvenioBTLMF.vApellidoMat, VariablesConvenioBTLMF.vDni,
                                                          VariablesConvenioBTLMF.vTelefono, VariablesConvenioBTLMF.vEmail,
                                                          VariablesConvenioBTLMF.vFechaNac, " ", lineaCredito);
                    
                    log.info("CODIGO DE CLIENTE OBTENIDO: "+codCliente);
                    
                    DBConvenioBTLMF.crearBenificiarioNew(VariablesConvenioBTLMF.vNombre,
                                                          VariablesConvenioBTLMF.vApellidoPat,
                                                          VariablesConvenioBTLMF.vApellidoMat, VariablesConvenioBTLMF.vDni,
                                                          VariablesConvenioBTLMF.vTelefono, VariablesConvenioBTLMF.vEmail,
                                                          VariablesConvenioBTLMF.vFechaNac, codCliente, lineaCredito, nroTarjeta);


                    FarmaUtility.showMessage(this, "Se grabo el nuevo Benificiario con Exito", null);
                    VariablesConvenioBTLMF.vCodigo = VariablesConvenioBTLMF.vDni;
                    VariablesConvenioBTLMF.vDescripcion =
                            txtNombre.getText().trim().toUpperCase() + " " + txtApePat.getText().trim().toUpperCase() +
                            " " + txtApeMat.getText().trim().toUpperCase();
                    Map benificiario =
                        (Map)UtilityConvenioBTLMF.obtieneBenificiario(VariablesConvenioBTLMF.vCodConvenio, VariablesConvenioBTLMF.vDni,
                                                                      this);
                    VariablesConvenioBTLMF.vCodCliente = (String)benificiario.get(ConstantsConvenioBTLMF.COL_COD_CLIENTE);
                    log.debug("vCodCliente:" + VariablesConvenioBTLMF.vCodCliente);
                    cerrarVentana(true);
                }else{
                    FarmaUtility.showMessage(this, "No se obtuvo conexion con RAC, para registro de beneficiario.\nIntentelo nuevamente mas tarde.", null);
                    cerrarVentana(false);
                }
                
            }

        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al grabar nuevo Benificiario.\nIntentelo nuevamente mas tarde.", txtNombre);
            cerrarVentana(false);
        }
    }

    public boolean existeDniBenificiario(String pCodConvenio, String pDni, JDialog pDialogo) {
        boolean retorno = false;

        if (UtilityConvenioBTLMF.existeBenificiario(pCodConvenio, pDni, pDialogo).equals("S")) {
            retorno = true;
            FarmaUtility.showMessage(pDialogo, "Existe DNI del Benificiario", txtDni);
        }

        return retorno;
    }

    private void guardaValoresBenificiario() {

        log.debug("<<<<<<<<<Metodo:guardaValoresBenificiario>>>>>>>>>>>>>>");

        VariablesConvenioBTLMF.vNombre = txtNombre.getText().trim().toUpperCase();
        VariablesConvenioBTLMF.vApellidoPat = txtApePat.getText().trim().toUpperCase();
        VariablesConvenioBTLMF.vApellidoMat = txtApeMat.getText().trim().toUpperCase();
        VariablesConvenioBTLMF.vDni = txtDni.getText().trim();
        VariablesConvenioBTLMF.vTelefono = txtTelefono.getText().trim();
        VariablesConvenioBTLMF.vEmail = txtEmail.getText().trim();
        VariablesConvenioBTLMF.vFechaNac = txtFecNacimiento.getText().trim();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {

            guardaValoresBenificiario();
            if (!validarDato())
                return;
            if (existeDniBenificiario(VariablesConvenioBTLMF.vCodConvenio, txtDni.getText().trim(), this))
                return;

            crearBenificiario();
            //jquispe 30.12.2011 cambio para guardar variables del nuevo cliente convenio.
            guardarRegistroSeleccionado();

        }

    }

    private void lblNombre_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNombre);
    }

    private void cerrarVentana(boolean pAceptar) {
        VariablesConvenioBTLMF.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtNombre_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApePat);
            txtNombre.setText(txtNombre.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtApePat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApeMat);
            txtApePat.setText(txtApePat.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtApeMat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDni);
            txtApeMat.setText(txtApeMat.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }
    
    private void txtKeyPressed(KeyEvent e, JTextField objOrigen, JTextField objDestino){
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(objDestino);
            objOrigen.setText(objOrigen.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }
    
    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFecNacimiento);
            txtDni.setText(txtDni.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtFecNacimiento_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtTelefono);
            txtFecNacimiento.setText(txtFecNacimiento.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtEmail);
            txtTelefono.setText(txtTelefono.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void txtEmail_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtEmail);
            txtEmail.setText(txtEmail.getText().trim().toUpperCase());
        }
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    /**
     * This method initializes lblTelefono
     *
     * @return com.gs.mifarma.componentes.JButtonLabel
     */
    private JButtonLabel getLblTelefono() {
        if (lblTelefono == null) {
            lblTelefono = new JButtonLabel();
            lblTelefono.setForeground(new Color(255, 130, 14));
            lblTelefono.setSize(new Dimension(105, 15));
            lblTelefono.setLocation(new Point(11, 146));
            lblTelefono.setText("Telefono : ");
        }
        return lblTelefono;
    }

    /**
     * This method initializes lblEmail
     *
     * @return com.gs.mifarma.componentes.JButtonLabel
     */
    private JButtonLabel getLblEmail() {
        if (lblEmail == null) {
            lblEmail = new JButtonLabel();
            lblEmail.setSize(new Dimension(58, 15));
            lblEmail.setLocation(new Point(11, 173));
            lblEmail.setText("Email : ");
            lblEmail.setForeground(new Color(255, 130, 14));
        }
        return lblEmail;
    }

    /**
     * This method initializes txtTelefono
     *
     * @return com.gs.mifarma.componentes.JTextFieldSanSerif
     */
    private JTextFieldSanSerif getTxtTelefono() {
        if (txtTelefono == null) {
            txtTelefono = new JTextFieldSanSerif();
            txtTelefono.setDocument(new FarmaLengthText(120));
            txtTelefono.setSize(new Dimension(124, 20));
            txtTelefono.setLocation(new Point(121, 145));
            txtTelefono.setFont(new Font("SansSerif", 0, 11));
            txtTelefono.setDocument(new ValidaDatoIngreso(this, ConstantsConvenioBTLMF.CLASE_TELEFONO, 49));
            txtTelefono.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtTelefono_keyPressed(e);
                }
            });
        }
        return txtTelefono;
    }

    /**
     * This method initializes txtEmail
     *
     * @return com.gs.mifarma.componentes.JTextFieldSanSerif
     */
    private JTextFieldSanSerif getTxtEmail() {
        if (txtEmail == null) {
            txtEmail = new JTextFieldSanSerif();
            txtEmail.setSize(new Dimension(174, 20));
            txtEmail.setLocation(new Point(121, 172));
            txtEmail.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtEmail_keyPressed(e);
                }
            });
        }
        return txtEmail;
    }

    public boolean validarDato() {
        boolean result = true;
        Pattern p = Pattern.compile(".+@.+\\.[a-zA-Z]+");
        Matcher m = p.matcher(txtEmail.getText().toString());
        boolean matchFound = m.matches();
        if (txtNombre.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Es obligatorio el ingreso del Nombre", txtNombre);
            result = false;

        } else if (txtApePat.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Es obligatorio el ingreso del Apellido Paterno", txtApePat);
            result = false;

        } else if (txtApeMat.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Es obligatorio el ingreso del Apellido Materno", txtApeMat);
            FarmaUtility.moveFocus(txtApeMat);

            result = false;
        } else if (txtDni.getText() != null && txtDni.getText().trim().length() < 8 ||
                   txtDni.getText().trim().length() > 8) {
            FarmaUtility.showMessage(this, "Es invalido el DNI ", txtDni);
            FarmaUtility.moveFocus(txtDni);

            result = false;
        /*} else if (!FarmaUtility.validaFecha(txtFecNacimiento.getText().trim(), "dd/MM/yyyy")) {
            FarmaUtility.showMessage(this, "Es invalido la Fecha Nacimiento", txtFecNacimiento);
            FarmaUtility.moveFocus(txtFecNacimiento);

            result = false;
        } else if (txtEmail.getText().trim().length() > 0 && !matchFound) {
            FarmaUtility.showMessage(this, "Es invalido el Email", txtEmail);
            result = false;*/
        }

        return result;
    }

    private void guardarRegistroSeleccionado() {
        VariablesConvenio.vArrayList_DatosConvenio.clear();
        VariablesConvenio.vArrayList_DatosConvenio.add(VariablesConvenioBTLMF.vCodConvenio); //0
        VariablesConvenio.vArrayList_DatosConvenio.add(VariablesConvenioBTLMF.vCodCliente); //1
        VariablesConvenio.vArrayList_DatosConvenio.add(VariablesConvenioBTLMF.vNombre); //2
        VariablesConvenio.vArrayList_DatosConvenio.add(" "); //3
        VariablesConvenio.vArrayList_DatosConvenio.add(" "); //4
        VariablesConvenio.vArrayList_DatosConvenio.add(VariablesConvenioBTLMF.vDni) /*txtNumeroDocumento.getText()*/; //5
        VariablesConvenio.vArrayList_DatosConvenio.add(" "); //6
        VariablesConvenio.vArrayList_DatosConvenio.add(" "); //7
        VariablesConvenio.vArrayList_DatosConvenio.add(" "); //8
        VariablesConvenio.vArrayList_DatosConvenio.add(" "); //9
        VariablesConvenio.vArrayList_DatosConvenio.add(" "); //10
        //            VariablesConvenioBTLMF.listaDatos = new ArrayList();
        //
        //
        //            int vFila = tblDatos.getSelectedRow();
        //            if(vFila > -1)
        //            {
        //
        //              VariablesConvenioBTLMF.listaDatos.clear();
        //              VariablesConvenioBTLMF.listaDatos.add(tableModelListaDatos.data.get(vFila));
        //              VariablesConvenioBTLMF.vCodigo      = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_CODIGO);
        //              VariablesConvenioBTLMF.vDescripcion = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_DESCRIPCION);
        //
        //              if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
        //              {
        //                      String dni = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_CODIGO);
        //                      String nombreCliente = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NOMBRE_CLIENTE);
        //                      String numPoliza     = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NUM_POLIZA);
        //                      String numPlan       = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NUM_PLAN);
        //                      String codAsegurado  = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_COD_ASEGURADO);
        //                      String numItem       = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NUM_ITEM);
        //                      String prt           = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_PRT);
        //                      String numContrado   = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NUM_CONTRATO);
        //                      String tipoAsegurado = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_TIPO_ASEGURADO);
        //                      String codCliente    = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_COD_CLIENTE);
        //
        //
        //                      VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_POLIZA,numPoliza);
        //                      VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_PLAN,numPlan);
        //                      VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_COD_ASEGURADO,codAsegurado);
        //                      VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_IEM,numItem);
        //                      VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NOMB_CLIENTE,nombreCliente);
        //                      VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_PRT,prt);
        //                      VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_CONTRATO,numContrado);
        //                      VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_TIPO_ASEGURADO,tipoAsegurado);
        //                      VariablesConvenioBTLMF.vCodCliente = codCliente;
        //                      log.debug("VariablesConvenio.vCodCliente:  "+VariablesConvenioBTLMF.vCodCliente);
        //
        //                  //
        //
        //                  VariablesConvenio.vArrayList_DatosConvenio.clear();
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(VariablesConvenioBTLMF.vCodConvenio);//0
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(codCliente);//1
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(nombreCliente);//2
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//3
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//4
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(dni/*txtNumeroDocumento.getText()*/);//5
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//6
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//7
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//8
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//9
        //                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//10
        //
        //                  //VariablesConvenio.vTextoCliente = " ";
        //              }
        //
        //            }
        //            else
        //            if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
        //            {
        //              obtieneBenificiario();
        //            }
        //
        //           log.debug("CREACION CLIENTE:"+VariablesConvenioBTLMF.vCreacionCliente);
        //           if(VariablesConvenioBTLMF.vCreacionCliente != null && VariablesConvenioBTLMF.vCreacionCliente.equals(ConstantsConvenioBTLMF.FLG_SOLICITUD_BENIF))
        //           {
        //                   FarmaUtility.showMessage(this,
        //                       "Benificiario Pendiente de Aprobación \n" +
        //                       VariablesConvenioBTLMF.vNombre +" " +
        //                       VariablesConvenioBTLMF.vApellidoPat +" \n"+
        //                       "DNI: " + VariablesConvenioBTLMF.vDni +""
        //                       ,
        //                       null);
        //           }
        //           else
        //           {
        //              cerrarVentana(true);
        //           }

        cerrarVentana(true);
    }
    
    public void setNroTarjeta(String nroTarjeta){
        this.nroTarjeta = nroTarjeta;
    }
}
