package mifarma.ptoventa.recepcionCiega;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.*;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

 
 

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDatosTransportista.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 *   16.11.2009 Creación<br>
 *   16.03.2010 Modificación<br>
 * <br>
 * 
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 * 
 */
public class DlgIngresoTransportista extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */

    private JPanel jContentPane = new JPanel();

    Frame myParentFrame;
    private int vEstVigencia;
    private String FechaInicio = "";

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JTextFieldSanSerif txtPrecintos = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtBultos = new JTextFieldSanSerif();

    private JLabelWhite lblValor_T = new JLabelWhite();

    private JButtonLabel btnFechaInicial = new JButtonLabel();

    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();

    private JLabelWhite lblFechaFinal_T = new JLabelWhite();


    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JLabelWhite lblCodPromocion = new JLabelWhite();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();
    private JTextFieldSanSerif txtPlaca = new JTextFieldSanSerif();
    private JButtonLabel lblGlosa = new JButtonLabel();
    private JTextFieldSanSerif txtGlosa = new JTextFieldSanSerif();

    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgIngresoTransportista() {
        this(null, "", false);
    }

    public DlgIngresoTransportista(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* ********************************************************************** */
    /* METODO JBINIT */
    /* ********************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(493, 230));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso Datos Transportista");
        this.setFont(new Font("SansSerif", 0, 11));
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jContentPane.setBackground(Color.white);
        jContentPane.setFont(new Font("SansSerif", 0, 11));
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(470, 236));
        pnlTitle1.setBounds(new Rectangle(5, 5, 475, 170));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setLayout(null);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        txtPrecintos.setLengthText(6);
        txtPrecintos.setBounds(new Rectangle(145, 100, 135, 20));
        txtPrecintos.addKeyListener(new KeyAdapter() {

                    public void keyTyped(KeyEvent e) {
                        txtPrecintos_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtPrecintos_keyPressed(e);
                    }
                });
        txtBultos.setLengthText(6);
        txtBultos.setBounds(new Rectangle(145, 70, 120, 20));
        txtBultos.addKeyListener(new KeyAdapter() {


                    public void keyPressed(KeyEvent e) {
                        txtBultos_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtBultos_keyTyped(e);
                    }
                });
        lblValor_T.setText("Cantidad Precintos :");
        lblValor_T.setForeground(new Color(255, 130, 14));
        lblValor_T.setBounds(new Rectangle(10, 100, 115, 20));
        btnFechaInicial.setText("Nombre completo :");

        btnFechaInicial.setForeground(new Color(255, 130, 14));
        btnFechaInicial.setMnemonic('N');
        btnFechaInicial.setBounds(new Rectangle(10, 10, 105, 20));
        btnFechaInicial.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnFechaInicial_actionPerformed(e);
                    }
                });
        txtNombre.setLengthText(99);
        txtNombre.setBounds(new Rectangle(145, 10, 265, 20));
        txtNombre.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtNombre_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtNombre_keyTyped(e);
                    }
                });
        lblFechaFinal_T.setText("Cantidad Bultos :");
        lblFechaFinal_T.setForeground(new Color(255, 130, 14));
        lblFechaFinal_T.setBounds(new Rectangle(10, 70, 110, 20));
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(395, 180, 85, 20));
        lblF11.setText("[F11] Aceptar");
        lblF11.setBounds(new Rectangle(295, 180, 95, 20));
        lblCodPromocion.setText("[CodPromocion]");
        lblCodPromocion.setForeground(new Color(255, 130, 14));
        lblCodPromocion.setRequestFocusEnabled(false);
        lblCodPromocion.setFocusable(false);
        lblCodPromocion.setVisible(false);
        lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        //--Se cambio el tamaño de digitos
        //  12.09.2008  
        jLabelOrange2.setText("Placa Unidad :");
        jLabelOrange2.setBounds(new Rectangle(10, 40, 125, 20));
        txtPlaca.setBounds(new Rectangle(145, 40, 175, 20));
        txtPlaca.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtPlaca_keyPressed(e);
                    }
                });
        txtPlaca.setLengthText(8);
        lblGlosa.setText("Glosa :");
        lblGlosa.setBounds(new Rectangle(10, 130, 75, 15));
        lblGlosa.setForeground(new Color(255, 130, 14));
        txtGlosa.setBounds(new Rectangle(145, 130, 320, 20));
        txtGlosa.setLengthText(2000);
        txtGlosa.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtGlosa_keyPressed(e);
                    }
                });
        pnlTitle1.add(txtGlosa, null);
        pnlTitle1.add(lblGlosa, null);
        pnlTitle1.add(txtPlaca, null);
        pnlTitle1.add(jLabelOrange2, null);
        pnlTitle1.add(lblCodPromocion);
        pnlTitle1.add(txtPrecintos);
        pnlTitle1.add(txtBultos);
        pnlTitle1.add(lblValor_T);
        pnlTitle1.add(btnFechaInicial);
        pnlTitle1.add(txtNombre);
        pnlTitle1.add(lblFechaFinal_T);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ********************************************************************** */
    /* METODO INITIALIZE */
    /* ********************************************************************** */

    private void initialize() {
        //lblDescLocal.setText(FarmaVariables.vCodLocal.trim()+" - "+FarmaVariables.vDescCortaLocal.trim());
        //JQUISPE 05.05.2010 CAMBIO PARA REIMPRIMIR UN NUMERO DE IMPRESIONES DEFINIDA.
        VariablesRecepCiega.vNumImpresiones = UtilityRecepCiega.getNumImpresiones();
        cargarFecha();
    }

    /* ********************************************************************** */
    /* METODO DE INICIALIZACION */
    /* ********************************************************************** */

    /* ********************************************************************** */
    /* METODOS DE EVENTOS */
    /* ********************************************************************** */

    private void txtFechaInicio_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnFechaInicial_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNombre);
    }

    private void txtVTa_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtNombre, e);
    }

    private void txtAlmc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtBultos, e);
    }

    private void txtFechainicial_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBultos);
        }
        chkKeyPressed(e);
    }

    private void txtFechaFinal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPrecintos);
        }
        chkKeyPressed(e);
    }

    private void txtValor_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNombre);
        txtBultos.setText("0");
        txtPrecintos.setText("0");
        cargaLogin();
    }


    private void txtFechaFinal_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtBultos, e);
    }

    private void txtValor_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }

    private void txtFecFacturacion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }

    private void txtFecFacturacion_keyReleased(KeyEvent e) {
        //FarmaUtility.dateComplete(txtFecFacturacion,e);
    }

    private void txtValorProv_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // FarmaUtility.moveFocus(txtFecFacturacion);
        }
        chkKeyPressed(e);
    }

    private void txtValorProv_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    /* ********************************************************************** */
    /* METODOS AUXILIARES */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            //obtiene datos
            if (datosValidados()) {
                if (FarmaUtility.rptaConfirmDialog(this, 
                                                   "Está seguro que desea grabar el transportista ?")) {
                    //obtiene datos transportista
                    getDatosTransportista();
                    try{
                            String vNroRecep = DBRecepCiega.ingresaDatosTrans("0").trim();
                            FarmaUtility.aceptarTransaccion();                    
                            UtilityRecepCiega.imprimeVoucherTransportista(this,vNroRecep,txtNombre);                            
                            cerrarVentana(true);
                        }catch(SQLException sql){
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this,"Error al ingresar los Datos del Transportista.",txtNombre);
                        }
                    //FarmaUtility.showMessage(this,"Datos guardados correctamente.",txtPrecintos);
                    //cerrarVentana(true);
                    //mostrarGuias();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void getDatosTransportista() {

        VariablesRecepCiega.vNombreTrans = txtNombre.getText().trim();
        
        VariablesRecepCiega.vPlacaUnidTrans = txtPlaca.getText().trim();
        VariablesRecepCiega.vCantBultos = txtBultos.getText().trim();
        VariablesRecepCiega.vCantPrecintos = txtPrecintos.getText().trim();

        //  05.03.10
        VariablesRecepCiega.vGlosa = txtGlosa.getText().trim();

        System.out.println("VariablesRecepCiega.vNombreTrans " + 
                           VariablesRecepCiega.vNombreTrans);
        System.out.println("VariablesRecepCiega.vHoraTrans " + 
                           VariablesRecepCiega.vHoraTrans);
        System.out.println("VariablesRecepCiega.vPlacaUnidTrans " + 
                           VariablesRecepCiega.vPlacaUnidTrans);
        System.out.println("VariablesRecepCiega.vCantBultos " + 
                           VariablesRecepCiega.vCantBultos);
        System.out.println("VariablesRecepCiega.vCantPrecintos " + 
                           VariablesRecepCiega.vCantPrecintos);

    }


    /**
     * Se valida ingreso de quimico (Administrador Local)
     */
    private void cargaLogin() {
        //se guarda datos
        VariablesRecepCiega.vNuSecUsu = FarmaVariables.vNuSecUsu;
        VariablesRecepCiega.vIdUsu = FarmaVariables.vIdUsu;
        VariablesRecepCiega.vNomUsu = FarmaVariables.vNomUsu;
        VariablesRecepCiega.vPatUsu = FarmaVariables.vPatUsu;
        VariablesRecepCiega.vMatUsu = FarmaVariables.vMatUsu;

        DlgLogin dlgLogin = 
            new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);

        if (FarmaVariables.vAceptar) {

            if (!FarmaVariables.vNuSecUsu.equalsIgnoreCase(VariablesRecepCiega.vNuSecUsu)) {
                FarmaUtility.showMessage(this, 
                                         "El usuario no es el mismo al logueado anteriormente. Verificar!!!", 
                                         txtNombre);
                cerrarVentana(false);
            }

            // se sete datos originales
            FarmaVariables.vNuSecUsu = VariablesRecepCiega.vNuSecUsu;
            FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
            FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
            FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
            FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;

            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        } else
            cerrarVentana(false);
    }


    private void mostrarGuias() {

        System.out.println("MOSTRANDO GUIAS");

        DlgGuiasPendientes dlgGuias = 
            new DlgGuiasPendientes(myParentFrame, "", true);
        dlgGuias.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cerrarVentana(true);
            //FarmaVariables.vAceptar = false;
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ********************************************************************** */
    /* METODOS DE LOGICA DE NEGOCIO */
    /* *********************************************************************** */


    private boolean datosValidados() {
        boolean retorno = true;
        if (txtNombre.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese nombre del transportista.", 
                                     txtNombre);
            retorno = false;
        }  else if (txtPlaca.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese Placa.", txtPlaca);
            retorno = false;
        } else if (txtBultos.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese cantidad de bultos.", 
                                     txtBultos);
            retorno = false;
        } else if (txtPrecintos.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese cantidad de precintos.", 
                                     txtPrecintos);
            retorno = false;
        } else if (!validaBultos()) {
            //FarmaUtility.showMessage(this, "Debe ingresar bultos con valor mayor a cero.", txtBultos);
            retorno = false;
        } else if (!validaPrecintos()) {
            //FarmaUtility.showMessage(this, "Debe ingresar precintos con valor mayor a cero.", txtPrecintos);
            retorno = false;
        } /*else if(!txtPlaca.getText().matches("[0-9]+[-]+?[A-Z]*")){
          FarmaUtility.showMessage(this, "Formato de Placa no valido.", txtPlaca);
          retorno=false;
      }*/

   

        /*  if(!validarPlaca(txtPlaca.getText().trim())&&txtPlaca.getText().trim().length()>0){
          FarmaUtility.showMessage(this, "Formato de Placa no valido.", txtPlaca);
          retorno=false;
          }*/

        return retorno;
    }


    /*public static boolean validarHora(String hora ) {
        System.out.println("hora...........");
           boolean b = Pattern.matches("[0-9:]", hora);
        System.out.println("b."+b);
           return b;
    }*/

    /*public static boolean validarPlaca( String placa ) {
    System.out.println("placa...........");
           boolean b = Pattern.matches("^[a-zA-Z0-9%-]$", placa);
              System.out.println("b."+b);
           return b;
          }*/

    //boolean b = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);

  /*  private boolean validarHora() {
        boolean retorno = true;
        if (!FarmaUtility.isHoraMinutoValida(this, txtHora.getText(), 
                                             "La hora ingresada no tiene el formato correcto")) {
            FarmaUtility.moveFocus(txtHora);
            retorno = false;
        }
        if (FarmaUtility.validarHora(txtHora.getText().trim())) {
            FarmaUtility.showMessage(this, 
                                     "La hora ingresada no tiene el es formato.", 
                                     txtHora);
            retorno = false;
        }
        return retorno;
    }*/

    private boolean validaBultos() {
        boolean valor = false;
        String cantIngreso = txtBultos.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso)) {
            if (Integer.parseInt(cantIngreso) > 0) {
                valor = true;
            } else {
                valor = false;
                FarmaUtility.showMessage(this, 
                                         "Ingrese Cantidad de Bultos mayor a cero.", 
                                         txtBultos);
            }
        } else {
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar cantidad entera.", 
                                     txtBultos);
        }

        return valor;
    }

    private boolean validaPrecintos() {
        boolean valor = false;
        String cantIngreso = txtPrecintos.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso)) {
            if (Integer.parseInt(cantIngreso) >= 0) {
                valor = true;
            } else {
                valor = false;
                FarmaUtility.showMessage(this, 
                                         "Ingrese Cantidad de Precintos mayor o igual a cero.", 
                                         txtPrecintos);
            }
        } else {
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar cantidad entera.", 
                                     txtPrecintos);
        }
        return valor;
    }


    private void cargarFecha() {
        try {
            FechaInicio = FarmaSearch.getFechaHoraBD(1);
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }


    private void txtNombre_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPlaca);
        }
        chkKeyPressed(e);
    }

    private void txtPlaca_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBultos);
        }
        chkKeyPressed(e);
    }

    private void txtBultos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPrecintos);
        }
        chkKeyPressed(e);
    }

    private void txtBultos_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBultos, e);
    }

    private void txtPrecintos_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBultos, e);
        //FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }

    private void txtPrecintos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtGlosa);
        }
        chkKeyPressed(e);
    }

    private void txtNombre_keyTyped(KeyEvent e) {

        FarmaUtility.admitirLetras(txtNombre, e);
    }

    private void txtGlosa_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }
    
   
}
