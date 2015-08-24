package mifarma.ptoventa.puntos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;

import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DlgConsultaSaldoPuntos extends JDialog {
    double vTiempoMaquina = 400; // MILISEGUNDOS
    private static final Logger log = LoggerFactory.getLogger(DlgConsultaSaldoPuntos.class);
    long tmpT1,tmpT2,OldtmpT2;
    boolean isLectoraLazer = false;
    boolean isEnter = false;
    
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblFecha = new JButtonLabel();
    private JLabelFunction btnF11 = new JLabelFunction();
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

    public DlgConsultaSaldoPuntos() {
        this(null, "", false);
    }

    public DlgConsultaSaldoPuntos(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(378, 137));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Consulta de Saldos");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 365, 65));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        //txtSerie.setLengthText(3);
        //LTAVARA 03.09.2014 se incrementa en 4 para la serie del documento electronico
        //txtNroComprobante.setLengthText(7);
        //LTAVARA 03.09.2014 se incrementa en 8 para el correlativo del documento electronico

        lblFecha.setText("Tarjeta :");
        lblFecha.setMnemonic('F');
        lblFecha.setBounds(new Rectangle(15, 20, 90, 15));
        lblFecha.setForeground(new Color(255, 90, 33));
        lblFecha.setFocusable(false);
        lblFecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFecha_actionPerformed(e);
            }
        });

        btnF11.setBounds(new Rectangle(5, 85, 117, 20));
        btnEsc.setBounds(new Rectangle(250, 85, 117, 19));
        btnF11.setText("[F11] Imprimir");
        btnF11.setFocusable(false);


        btnEsc.setText("[ESC] Cerrar");
        btnEsc.setFocusable(false);
        txtTarjeta.setBounds(new Rectangle(365, 25, 20, 10));
        txtTarjeta.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtFecha_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                    txtFecha_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtTarjeta_keyTyped(e);
                }
            });
        //txtTarjeta.setLengthText(10);
        txtTarjeta.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtTarjeta_focusLost(e);
                }
            });
        lblTarjOculta.setBounds(new Rectangle(75, 20, 280, 20));
        lblTarjOculta.setFont(new Font("Century", 1, 11));
        lblTarjOculta.setBorder(BorderFactory.createLineBorder(new Color(0, 99, 0), 2));
        lblTarjOculta.setBackground(SystemColor.window);
        lblTarjetaInput.setBounds(new Rectangle(85, 0, 295, 20));
        pnlTitle.add(lblTarjOculta, null);
        pnlTitle.add(lblTarjetaInput, null);
        pnlTitle.add(lblFecha, null);
        pnlTitle.add(txtTarjeta, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(pnlTitle, null);
        txtTarjeta.setVisible(true);
        lblTarjetaInput.setVisible(false);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    private void initialize() {
        
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
        if (UtilityPtoVenta.verificaVK_F11(e)) {            
            if(lblTarjetaInput.getText().trim().length()>0){
                UtilityPuntos.consultaSaldo(lblTarjetaInput.getText().trim(),txtTarjeta,this, MyParentFrame);            
                lblTarjOculta.setText("");
                lblTarjetaInput.setText("");
                txtTarjeta.setText("");
            }
            else
                FarmaUtility.showMessage(this, "Debe de ingresar una tarjeta.", txtTarjeta);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    private void lblFecha_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtTarjeta);
    }

    private void txtFecha_keyReleased(KeyEvent e) {
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
    
        private void txtFecha_keyPressed(KeyEvent e) {
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
                (e.getKeyCode() == KeyEvent.VK_ESCAPE) || UtilityPtoVenta.verificaVK_F11(e)
            );
    }
    
    public void muestraTarjeta(String pTarjeta){
        lblTarjOculta.setText("");
        lblTarjetaInput.setText("");
        txtTarjeta.setText("");
        if(UtilityPuntos.isTarjetaValida(pTarjeta)){
        lblTarjOculta.setText(UtilityPuntos.enmascararTarjeta(pTarjeta));
        if(lblTarjOculta.getText().trim().length()>0)
           lblTarjetaInput.setText(pTarjeta);
        txtTarjeta.setText("");
        
        if(lblTarjetaInput.getText().length()==0)
            FarmaUtility.showMessage(this,"Tarjeta es Inválida",txtTarjeta);
        }
        else
        FarmaUtility.showMessage(this,"Tarjeta es Inválida",txtTarjeta);
        
        if(lblTarjOculta.getText().trim().length()>0){
            lblTarjetaInput.setText(pTarjeta);
            UtilityPuntos.consultaSaldo(lblTarjetaInput.getText().trim(),txtTarjeta,this, MyParentFrame);            
            lblTarjOculta.setText("");
            lblTarjetaInput.setText("");
            txtTarjeta.setText("");
        }
    }

    private void txtTarjeta_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtTarjeta);
    }
    
    public Frame getMyParentFrame(){
        return this.MyParentFrame;
    }
}
