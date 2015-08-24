package mifarma.ptoventa.puntos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import farmapuntos.util.FarmaPuntosConstante;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;

import mifarma.ptoventa.inventario.precioCompetencia.reference.UtilityPrecioCompetencia;
import mifarma.ptoventa.puntos.reference.DBPuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerUtil.FarmaUtil;


public class DlgRecuperarPuntos extends JDialog {
    double vTiempoMaquina = 400; // MILISEGUNDOS
    private static final Logger log = LoggerFactory.getLogger(DlgRecuperarPuntos.class);
    long tmpT1,tmpT2,OldtmpT2;
    boolean isLectoraLazer = false;
    boolean isEnter = false;
    
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblTarjeta = new JButtonLabel();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnF5 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private String eRCM = "";
    private String vRpta = "";
    private boolean estrcm = false;
    private boolean estvta = false;
    //private JTextField txtFecha = new JTextField();
    private JTextFieldSanSerif txtTarjeta = new JTextFieldSanSerif();
    private JLabel lblTarjOculta = new JLabel();
    private JLabel lblTarjetaInput = new JLabel();
    

    private JButtonLabel lblTipoComprobante = new JButtonLabel();
    private JButtonLabel lblNroComprobante = new JButtonLabel();
    private JTextFieldSanSerif txtSerie = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNroComprobante = new JTextFieldSanSerif();
    private JButtonLabel lblMonto = new JButtonLabel();
    private JButtonLabel lblFecha = new JButtonLabel();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JComboBox cmbTipoComp = new JComboBox();
    private JTextField txtFecha = new JTextField();
    
    private String pNumPedVta = "";

    private JLabel jLabel1 = new JLabel();
    

    public DlgRecuperarPuntos() {
        this(null, "", false);
    }

    public DlgRecuperarPuntos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            MyParentFrame = parent;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(420, 278));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Recuperaci\u00f3n de Puntos");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 405, 205));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        //txtSerie.setLengthText(3);
        //LTAVARA 03.09.2014 se incrementa en 4 para la serie del documento electronico
        //txtNroComprobante.setLengthText(7);
        //LTAVARA 03.09.2014 se incrementa en 8 para el correlativo del documento electronico

        lblTarjeta.setText("Tarjeta :");
        lblTarjeta.setMnemonic('J');
        lblTarjeta.setBounds(new Rectangle(15, 20, 90, 15));
        lblTarjeta.setForeground(new Color(255, 90, 33));
        lblTarjeta.setFocusable(false);
        lblTarjeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    lblTarjeta_actionPerformed(e);
                }
        });

        btnF11.setBounds(new Rectangle(5, 220, 117, 20));
        btnEsc.setBounds(new Rectangle(285, 220, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        
        btnF5.setBounds(new Rectangle(140, 220, 117, 20));
        btnF5.setText("[F5] Limpiar");
        btnF5.setFocusable(false);
        
        btnEsc.setText("[ESC] Cerrar");
        btnEsc.setFocusable(false);
        txtTarjeta.setBounds(new Rectangle(405, 25, 20, 10));
        txtTarjeta.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                lblTarjeta_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                    txtTarjeta_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtTarjeta_keyTyped(e);
                }
            });
        //txtTarjeta.setLengthText(10);
        /*txtTarjeta.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtTarjeta_focusLost(e);
                }
            });*/
        lblTarjOculta.setBounds(new Rectangle(75, 20, 280, 20));
        lblTarjOculta.setFont(new Font("Century", 1, 11));
        lblTarjOculta.setBorder(BorderFactory.createLineBorder(new Color(0, 99, 0), 2));
        lblTarjOculta.setBackground(SystemColor.window);
        lblTarjetaInput.setBounds(new Rectangle(85, 0, 295, 20));
        
        lblTipoComprobante.setText("Tipo Comprobante:");
        lblTipoComprobante.setMnemonic('T');
        lblTipoComprobante.setBounds(new Rectangle(20, 55, 110, 15));
        lblTipoComprobante.setBackground(Color.white);
        lblTipoComprobante.setForeground(new Color(255, 90, 33));
        lblTipoComprobante.setFocusable(false);
        lblTipoComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        txtNroComprobante.setBounds(new Rectangle(215, 55, 130, 20));
        lblNroComprobante.setText("Nro. Comprobante:");
        lblNroComprobante.setMnemonic('N');
        lblNroComprobante.setBounds(new Rectangle(20, 90, 105, 15));
        lblNroComprobante.setForeground(new Color(255, 90, 33));
        lblNroComprobante.setFocusable(false);
        lblNroComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        txtSerie.setBounds(new Rectangle(130, 90, 60, 20));
        //txtSerie.setLengthText(3);
        txtSerie.setLengthText(4); //LTAVARA 03.09.2014 se incrementa en 4 para la serie del documento electronico
        txtSerie.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                    txtSerie_keyPressed(e);
                }


            public void keyTyped(KeyEvent e) {

                    txtSerie_keyTyped(e);
                }
        });
        txtSerie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        txtNroComprobante.setBounds(new Rectangle(205, 90, 145, 20));
        //txtNroComprobante.setLengthText(7);
        txtNroComprobante.setLengthText(8); //LTAVARA 03.09.2014 se incrementa en 8 para el correlativo del documento electronico
        txtNroComprobante.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                    txtNroComprobante_keyPressed(e);
                }


            public void keyTyped(KeyEvent e) {

                    txtNroComprobante_keyTyped(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtNroComprobante_keyReleased(e);
                }
            });
        lblMonto.setText("Monto:");
        lblMonto.setMnemonic('M');
        lblMonto.setBounds(new Rectangle(20, 130, 75, 15));
        lblMonto.setForeground(new Color(255, 90, 33));
        lblMonto.setFocusable(false);
        lblMonto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        lblFecha.setText("Fecha:");
        lblFecha.setMnemonic('F');
        lblFecha.setBounds(new Rectangle(20, 165, 110, 15));
        lblFecha.setForeground(new Color(255, 90, 33));
        lblFecha.setFocusable(false);
        lblFecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFecha_actionPerformed(e);
            }
        });

        txtMonto.setBounds(new Rectangle(130, 130, 145, 19));
        txtMonto.setLengthText(10);
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                    txtMonto_keyPressed(e);
                }

            public void keyTyped(KeyEvent e) {

                    txtMonto_keyTyped(e);
                }

        });
        cmbTipoComp.setBounds(new Rectangle(130, 55, 220, 20));
        cmbTipoComp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                    cmbTipoComp_keyPressed(e);
                }
        });
        cmbTipoComp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });        
        /*pnlTitle.add(jLabel2, null);
                pnlTitle.add(jLabel1, null);*/
                //pnlTitle.add(txtFecha, null);
                jLabel1.setText("dd/mm/yyyy");
                jLabel1.setBounds(new Rectangle(285, 165, 85, 20));
                jLabel1.setForeground(new Color(0, 128, 0));
                jLabel1.setFont(new Font("Tahoma", 1, 12));
                jLabel1.setFocusable(false);

        txtFecha.setBounds(new Rectangle(130, 165, 145, 20));
        txtFecha.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                    txtFecha_keyReleased(e);
                }

            public void keyPressed(KeyEvent e) {
                    txtFecha_keyPressed(e);
                }
        });
        txtFecha.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    txtFecha_actionPerformed(e);
                }
            });
        pnlTitle.add(jLabel1, null);
        pnlTitle.add(cmbTipoComp, null);
        pnlTitle.add(lblMonto, null);
        pnlTitle.add(lblFecha, null);
        pnlTitle.add(txtMonto, null);
        pnlTitle.add(txtNroComprobante, null);
        pnlTitle.add(txtSerie, null);
        pnlTitle.add(lblNroComprobante, null);
                pnlTitle.add(lblTipoComprobante, null);        
        
        pnlTitle.add(lblTarjOculta, null);
        pnlTitle.add(lblTarjetaInput, null);
        pnlTitle.add(lblTarjeta, null);
        pnlTitle.add(txtTarjeta, null);
        pnlTitle.add(txtFecha, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnF5, null);
        jContentPane.add(pnlTitle, null);
        txtTarjeta.setVisible(true);
        lblTarjetaInput.setVisible(false);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    private void initialize() {
        cargaCombo();
        actDesInput(false);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtTarjeta);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtTarjeta);
        lblTarjetaInput.setVisible(false);
        lblTarjOculta.setVisible(true);
    }
    private void chkKeyPressed(KeyEvent e) {
        //e.consume();
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (validarDatos()) {
                    if(UtilityPuntos.isTarjetaValida(lblTarjetaInput.getText().trim())){
                    
                    try {
                        if (getObtienePedVta()) {
                            if (pNumPedVta.trim().length() > 0) {
                                log.info("pNumPedVta getRecuperaPuntos " + pNumPedVta);
                                try {
                                    UtilityPuntos.getRecuperaPuntos(lblTarjetaInput.getText().trim(), txtTarjeta, this,
                                                                    pNumPedVta, txtFecha.getText().trim(),
                                                                    txtMonto.getText().trim());
                                } catch (Exception v) {
                                    // TODO: Add catch code
                                    v.printStackTrace();
                                    log.info(v.getMessage() + "");
                                    FarmaUtility.showMessage(this, "Error:" + v.getMessage() + "\n" +
                                            "No se pudo pecuperar los puntos", cmbTipoComp);
                                }
                                limpiar();
                            }
                        } else
                            FarmaUtility.showMessage(this, "No se pudo recuperar los puntos.\n" +
                                    //"Por favor vuelva a intentarlo." +
                                    "Gracias.", cmbTipoComp);
                    } catch (Exception v) {
                        // TODO: Add catch code
                        v.printStackTrace();
                        log.info(v.getMessage() + "");
                        FarmaUtility.showMessage(this, v.getMessage().trim(), cmbTipoComp);
                    }
                }        
            } else
              FarmaUtility.showMessage(this, "Falta Ingresar Información.", cmbTipoComp);
            
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_F5){
            limpiar();
        }
    }
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    private void lblTarjeta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtTarjeta);
        lblTarjOculta.setText("");
        lblTarjetaInput.setText("");
        txtTarjeta.setText("");
        FarmaUtility.showMessage(this, "Por favor de Pasar la Tarjeta", txtTarjeta);
    }

    private void lblTarjeta_keyReleased(KeyEvent e) {
        if (isTeclaPermitida(e)) {
            if (isLectoraLazer && isEnter && (OldtmpT2 != tmpT2)) {
                txtTarjeta.selectAll();
                OldtmpT2 = tmpT2;
                muestraTarjeta(txtTarjeta.getText());
                //FarmaUtility.showMessage(this,"Tarjeta si es Válida", txtTarjeta);
            }
        } else if ((e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
            txtTarjeta.setText("");
            lblTarjOculta.setText("");
            lblTarjetaInput.setText("");
            e.consume();
            FarmaUtility.showMessage(this, "Por favor Ingrese la tarjeta", txtTarjeta);
        } else
            e.consume();
    }
    
        private void txtTarjeta_keyPressed(KeyEvent e) {
        actDesInput(false);
        if (isTeclaPermitida(e)) {
            if (isLectoraLazer) {
                txtTarjeta.setText("");
                isLectoraLazer = false;
            }
            isEnter = false;
            isLectoraLazer = false;
            if (txtTarjeta.getText().length() == 0)
                tmpT1 = System.currentTimeMillis();
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                isLectoraLazer = false;
                tmpT2 = System.currentTimeMillis();
                log.info("Tiem 2 " + (tmpT2));
                log.info("Tiem 1 " + (tmpT1));
                log.info("Tiempo de ingreso y ENTER " + (tmpT2 - tmpT1));
                if ((tmpT2 - tmpT1) <= vTiempoMaquina && txtTarjeta.getText().length() > 0) {
                    isLectoraLazer = true;
                    isEnter = true;
                    tmpT1 = 0;
                } else {
                    isLectoraLazer = false;
                    txtTarjeta.setText("");
                    tmpT1 = 0;
                    FarmaUtility.showMessage(this, 
                            "Por favor escane la tarjeta con el lector de código de barras.\n" +
                            "No se permite el uso del teclado en esta función.", txtTarjeta);

                }
                //FarmaUtility.moveFocus(txtTarjeta);
            } else
                isEnter = false;
            chkKeyPressed(e);
        } else if ((e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
            txtTarjeta.setText("");
            lblTarjOculta.setText("");
            lblTarjetaInput.setText("");
            e.consume();
            FarmaUtility.showMessage(this, "Por favor Ingrese la tarjeta", txtTarjeta);
        } else
            e.consume();
    }

    private boolean isNumero(char ca) {
        int numero  = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            //nfe.printStackTrace();
            return false;
        }
        
        
    }
    
    private boolean isNumero(String ca) {
        int numero  = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            //nfe.printStackTrace();
            return false;
        }
        
        
    }    

    private void txtTarjeta_keyTyped(KeyEvent e) {
        if(!(isTeclaPermitida(e))) {
            if((e.getKeyCode() == KeyEvent.VK_DELETE||e.getKeyCode() == KeyEvent.VK_BACK_SPACE)){
                    txtTarjeta.setText("");
                    lblTarjOculta.setText("");
                    lblTarjetaInput.setText("");
                    e.consume();
                    FarmaUtility.showMessage(this,"Por favor Ingrese la tarjeta",txtTarjeta);
            }
            else
            e.consume();
        }
       
    }

    private boolean isTeclaPermitida(KeyEvent e) {
        return (isNumero(e.getKeyChar())||(e.getKeyCode() == KeyEvent.VK_ENTER)||
                (e.getKeyCode() == KeyEvent.VK_ESCAPE) || UtilityPtoVenta.verificaVK_F11(e)|| (e.getKeyCode() == KeyEvent.VK_F5));
    }
    
    public void muestraTarjeta(String pTarjeta){
        lblTarjOculta.setText("");
        lblTarjetaInput.setText("");
        txtTarjeta.setText("");
        if(UtilityPuntos.isTarjetaValida(pTarjeta)){
        lblTarjOculta.setText(UtilityPuntos.enmascararTarjeta(pTarjeta));
        if(lblTarjOculta.getText().trim().length()>0){
           lblTarjetaInput.setText(pTarjeta);
           actDesInput(true);
           FarmaUtility.moveFocus(cmbTipoComp);
        }
        else
            actDesInput(false);
        txtTarjeta.setText("");
        if(lblTarjetaInput.getText().length()==0)
            FarmaUtility.showMessage(this,"Tarjeta es Inválida",txtTarjeta);
        else{
            if(isTarjBloqueada(pTarjeta)){
                lblTarjOculta.setText("");
                lblTarjetaInput.setText("");
                txtTarjeta.setText("");
            }
        }
        
        }
        else
        FarmaUtility.showMessage(this,"Tarjeta es Inválida",txtTarjeta);
    }
    /*
    private void txtTarjeta_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtTarjeta);
    }*/
    

    private void lblTipoComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipoComp);
    }

    private void lblNroComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSerie);
    }

    private void lblMonto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMonto);
    }

    private void lblFecha_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecha);
    }

    private void cargaCombo() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cmbTipoComp, "CMB_COMP_RECUPERA_PUNTOS", "FARMA_PUNTOS.IMP_LISTA_TIPOS_COMP_RECUPERA(?)",
                                   parametros, true);
    }

    private void cmbTipoComp_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtSerie);
        }
        chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }

    private void txtSerie_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirDigitos(txtSerie,e);
        FarmaUtility.admitirLetrasNumeros(txtSerie, e); //LTAVARA 03.09.2014 - serie alfanumerico para DE
    }

    private void txtNroComprobante_keyTyped(KeyEvent e) {
        
        FarmaUtility.admitirDigitos(txtNroComprobante, e);
    }    


    private void txtSerie_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            if(txtSerie.getText().length()>0)
                if(txtSerie.getText().length()==4){
                    txtNroComprobante.setLengthText(8);
                    FarmaUtility.moveFocus(txtNroComprobante);    
                    if(txtNroComprobante.getText().trim().length()>0){
                        txtNroComprobante.setText("");
                        FarmaUtility.moveFocus(txtNroComprobante);
                    }
                }
            else
                if(txtSerie.getText().length()==3){
                    txtNroComprobante.setLengthText(7);
                    FarmaUtility.moveFocus(txtNroComprobante);    
                    if(txtNroComprobante.getText().trim().length()>0){
                        txtNroComprobante.setText("");
                        FarmaUtility.moveFocus(txtNroComprobante);
                    }
                }
                    else
                        txtNroComprobante.setLengthText(0);
            
        }
        chkKeyPressed(e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            if(txtSerie.getText().trim().length()==4)
                txtNroComprobante.setText(FarmaUtility.completeWithSymbol(txtNroComprobante.getText(), 8,"0", "I")); 
            else
               if(txtSerie.getText().trim().length()==3)
                txtNroComprobante.setText(FarmaUtility.completeWithSymbol(txtNroComprobante.getText(), 7,"0", "I")); 
            FarmaUtility.moveFocus(txtMonto);
        }
        chkKeyPressed(e);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFecha);
        }
        chkKeyPressed(e);
    }
    private void txtSerie_actionPerformed(ActionEvent e) {
    }

    private void cmbTipoComp_actionPerformed(ActionEvent e) {
    }
    
    private void txtFecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
    }

    private void txtFecha_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            /*lblTarjOculta.setText("");
            lblTarjetaInput.setText("");
            txtTarjeta.setText("");*/
            FarmaUtility.moveFocus(cmbTipoComp);
        }
        chkKeyPressed(e);
    }
    
    public void actDesInput(boolean valor){
        cmbTipoComp.setEnabled(valor);
        txtSerie.setEnabled(valor);
        txtNroComprobante.setEnabled(valor);
        txtMonto.setEnabled(valor);
        txtFecha.setEnabled(valor);
        if(valor)
            FarmaUtility.moveFocus(cmbTipoComp);
    }

    private boolean validarDatos() {
        boolean flag = true;
        if (lblTarjetaInput.getText().trim().length() == 0)
            return flag = false;
        
        if (txtSerie.getText().trim().length() == 0)
            return flag = false;

        if (txtNroComprobante.getText().trim().length() == 0)
            return flag = false;

        if (txtMonto.getText().trim().length() == 0)
            return flag = false;

        if (cmbTipoComp.getSelectedObjects().length == 0)
            return flag = false;

        if (txtFecha.getText().trim().length() == 0)
            return flag = false;

        return flag;
    }    
    
    
    private boolean getObtienePedVta() throws Exception{
        
        pNumPedVta = "";
        
        try {
            String vTipoComp = FarmaLoadCVL.getCVLCode("CMB_COMP_RECUPERA_PUNTOS", cmbTipoComp.getSelectedIndex());
            String vMontoNeto = txtMonto.getText().trim();
            String vNumCompPago = txtSerie.getText().trim() + txtNroComprobante.getText().trim();     
            String vFecha = txtFecha.getText().trim();     
            String vTarjeta = lblTarjetaInput.getText().trim();
            String NPVta = DBPuntos.getBuscaValidaPed_Recupera(vTipoComp,vMontoNeto,vNumCompPago,vFecha,vTarjeta);
            if(NPVta.trim().length()==10 && isNumero(NPVta)){
                pNumPedVta = NPVta;
                log.info("numero de pedido "+NPVta);
               return true;
            }
            else
            return false;                
        } catch (SQLException e) {
            // TODO: Add catch code
            e.printStackTrace();
            log.info(e.getMessage());
            if (e.getErrorCode() == 20190||e.getErrorCode() == 20180||
                e.getErrorCode() == 20170||e.getErrorCode() == 20160||
                e.getErrorCode() == 20150||e.getErrorCode() == 20140||
                e.getErrorCode() == 20130
               ) {
                throw new Exception(e.getMessage().substring(11, e.getMessage().indexOf("ORA-06512")));
            }
            else{
                throw new Exception(e.getMessage());
            }
            //log.info(e.getMessage());
            //return false;
        }
        catch(Exception q){
            throw new Exception(q.getMessage());
            //return false;
        }
        //return true;
    }

    private void limpiar() {
        txtFecha.setText("");
        txtMonto.setText("");
        txtSerie.setText("");
        txtNroComprobante.setText("");
        lblTarjOculta.setText("");
        lblTarjetaInput.setText("");
        txtTarjeta.setText("");
        FarmaUtility.moveFocus(txtTarjeta);
        actDesInput(false);
    }

    private void txtFecha_actionPerformed(ActionEvent e) {
    }

    private void txtNroComprobante_keyReleased(KeyEvent e) {
        
    }
    
    public boolean isTarjBloqueada(String vNumTarjeta){
        String pEstadoTarjeta = "X";
        try {
            //Ejemplo [ESTADO]@[DOC_IDENTIDAD]
            String pDNI = UtilityPuntos.getDNI_USU();
            pEstadoTarjeta =(VariablesPuntos.frmPuntos.getEstadoTarjeta(vNumTarjeta, pDNI).trim().split("@"))[0];
        } catch (Exception e) {
            // TODO: Add catch code
            log.info("Fallo al obtener el estado de tarjeta frmPuntosVenta.getEstadoTarjeta "+vNumTarjeta+"-");
            e.printStackTrace();
        }
        
        if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA)){
            log.info("Tarjeta es Bloqueada  "+vNumTarjeta);
            FarmaUtility.showMessage(this,"Tarjeta es Bloqueada",txtTarjeta);
            return true;
        }
        else
            if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.INACTIVA)){
                log.info("Tarjeta es Inactiva  "+vNumTarjeta);
                FarmaUtility.showMessage(this,"Tarjeta es Inactiva",txtTarjeta);
                return true;
            }
            else
                if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.INVALIDA)){
                    log.info("Tarjeta es Inválida  "+vNumTarjeta);
                    FarmaUtility.showMessage(this,"Tarjeta es Inválida",txtTarjeta);
                    return true;
                }
                else
                return false;
        
        /*
        if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.ACTIVA)){
            log.info("Tarjeta es Activa  "+vNumTarjeta+"-"+pDNI+"-"+pEstadoTarjeta);
            pInvocaVtaOffline   = true;
            pCambiaEstadoPuntos = false;
        }
        else{
            if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA)){
                log.info("Tarjeta es Bloqueada  "+vNumTarjeta+"-"+pDNI+"-"+pEstadoTarjeta);                                
                pInvocaVtaOffline   = false;
                pCambiaEstadoPuntos = true;
            }
            else{
                if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR)){
                    log.info("Tarjeta es Bloqueada Redimir  "+vNumTarjeta+"-"+pDNI+"-"+pEstadoTarjeta);                                                                    
                    if(vCtdRedimido>0){
                        log.info("puntos redimido > 0");                                        
                        pInvocaVtaOffline   = false;
                        pCambiaEstadoPuntos = true;
                    }
                    else{
                        log.info("puntos redimido <= 0");
                        pInvocaVtaOffline   = true;
                        pCambiaEstadoPuntos = false;                                            
                    }
                }
                else{
                    if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.INACTIVA)){
                        log.info("Tarjeta es INACTIVA  "+vNumTarjeta+"-"+pDNI+"-"+pEstadoTarjeta);                                                                                                            
                        pInvocaVtaOffline   = false;
                        pCambiaEstadoPuntos = false;
                    }
                    else{
                        if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.INVALIDA)){
                            log.info("Tarjeta es INVALIDA  "+vNumTarjeta+"-"+pDNI+"-"+pEstadoTarjeta);                                                                                                                
                            pInvocaVtaOffline   = false;
                            pCambiaEstadoPuntos = true;
                        }
                        else{
                            if(pEstadoTarjeta.equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.SIN_ESTADO)){
                                log.info("Tarjeta es SIN_ESTADO  "+vNumTarjeta+"-"+pDNI+"-"+pEstadoTarjeta);                                                                                                                                                                
                                pInvocaVtaOffline   = false;
                                pCambiaEstadoPuntos = false;
                            }
                            else{
                                pInvocaVtaOffline   = false;
                                pCambiaEstadoPuntos = false;                                                    
                                log.info("Este estado no es valido NO SE TIENE UNA PASO A SEGUIR "+vNumTarjeta+"-"+pDNI+"-"+pEstadoTarjeta);
                            }                                                
                        }
                    }
                }
            }
        } */       
        
    }
}
