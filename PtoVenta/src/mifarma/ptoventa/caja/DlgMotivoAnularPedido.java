package mifarma.ptoventa.caja;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;

import com.gs.mifarma.componentes.JLabelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import java.net.UnknownHostException;

import java.sql.*;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import mifarma.common.*;
import mifarma.ptoventa.*;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.ventas.reference.*;
import mifarma.ptoventa.caja.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgMotivoAnularPedido extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgMotivoAnularPedido.class);
    
    Frame myParentFrame;
    

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private JButton btnMotivoAnulacion = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    
    private JButtonLabel lblTipoMotivo = new JButtonLabel();
    private JComboBox cmbTipoMotivo = new JComboBox();
    private JTextArea txtMotivo = new JTextArea();
    private JLabelWhite lblMensaje = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMotivoAnularPedido() {
        this(null, "", false);
    }

    public DlgMotivoAnularPedido(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }

    }

   
    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(434, 270));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Detalle Anulacion de Pedido");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(435, 208));
        jContentPane.setLayout(null);
        jPanel1.setBounds(new Rectangle(15, 55, 390, 30));
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        //LTAVARA 03.09.2014 INICIO
        lblTipoMotivo.setText("Tipo Motivo:");
        lblTipoMotivo.setMnemonic('T');
        lblTipoMotivo.setBounds(new Rectangle(50, 20, 85, 15));
        lblTipoMotivo.setBackground(Color.black);
        lblTipoMotivo.setForeground(new Color(255, 90, 33));
        lblTipoMotivo.setFocusable(false);
        lblTipoMotivo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblTipoMotivo_actionPerformed(e);
                    }
        });
        cmbTipoMotivo.setBounds(new Rectangle(140, 20, 235, 20));
        cmbTipoMotivo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbTipoMotivo_keyPressed(e);
                    }
            });
        cmbTipoMotivo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cmbTipoMotivo_actionPerformed(e);
                }
            });
        //LTAVARA FIN
        btnMotivoAnulacion.setText("Motivo de Anulación");
        btnMotivoAnulacion.setBounds(new Rectangle(5, 5, 225, 20));
        btnMotivoAnulacion.setMnemonic('l');
        btnMotivoAnulacion.setBackground(new Color(255, 130, 14));
        btnMotivoAnulacion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 
                                                                      0));
        btnMotivoAnulacion.setBorderPainted(false);
        btnMotivoAnulacion.setContentAreaFilled(false);
        btnMotivoAnulacion.setDefaultCapable(false);
        btnMotivoAnulacion.setFocusPainted(false);
        btnMotivoAnulacion.setHorizontalAlignment(SwingConstants.LEFT);
        btnMotivoAnulacion.setRequestFocusEnabled(false);
        btnMotivoAnulacion.setForeground(Color.white);
        btnMotivoAnulacion.setFont(new Font("SansSerif", 1, 11));
        btnMotivoAnulacion.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        btnMotivoAnulacion_keyPressed(e);
                    }
                });
        btnMotivoAnulacion.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnMotivoAnulacion_actionPerformed(e);
                    }
                });

        txtMotivo.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtMotivo_keyPressed(e);
                    }

                    
                });
        lblMensaje.setBounds(new Rectangle(10, 180, 330, 25));
        lblMensaje.setForeground(Color.red);
        lblMensaje.setFont(new Font("Arial", 1, 15));
        jScrollPane1.setBounds(new Rectangle(15, 85, 390, 95));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(320, 210, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(210, 210, 100, 20));

        jScrollPane1.getViewport();
        jScrollPane1.getViewport();
        jContentPane.add(lblMensaje, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jPanel1.add(btnMotivoAnulacion, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(lblTipoMotivo, null);
        jScrollPane1.getViewport().add(txtMotivo, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(cmbTipoMotivo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        cargaCombo();
        FarmaVariables.vAceptar = false;
     
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void txtMotivo_keyPressed(KeyEvent e) {
        
          chkKeyPressed(e);
          
    }
  
    private void btnMotivoAnulacion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnMotivoAnulacion);
    }

    private void btnMotivoAnulacion_keyPressed(KeyEvent e) {
   
        chkKeyPressed(e);
    }



    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbTipoMotivo);
        lblMensaje.setText("");
        //JCORTEZ 16.06.09 imprime mensaje para recoger comprobante de anulacion
         if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
            if(!VariablesCaja.vDescripImpr.equalsIgnoreCase("X")||VariablesCaja.vDescripImpr.equalsIgnoreCase(""))
            lblMensaje.setText("Recoger comprobante en : "+VariablesCaja.vDescripImpr);
         }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

  
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

   
    
    private void chkKeyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
        VariablesCaja.vMotivoAnulacion="";
        cerrarVentana(false);
      }
      else if(UtilityPtoVenta.verificaVK_F11(e))
      {
          log.debug(" Aparece el metodo :");
                   
          ValidarCampo();
      }
    }
    private void ValidarCampo(){
       
    log.debug(" FarmaVariables.vAceptar " +  FarmaVariables.vAceptar);
             
          String  vTipoMotivo = FarmaLoadCVL.getCVLCode("cmbTipoMotivo",
                                                cmbTipoMotivo.getSelectedIndex());
            if (!vTipoMotivo.equals("0")){
                VariablesCaja.vTipoMotivoAnulacion =vTipoMotivo;
            }else{
                
                    FarmaUtility.showMessage(this, "Debe seleccionar Tipo Motivo de la anulacion.", null);
                    FarmaUtility.moveFocus(cmbTipoMotivo);
                    FarmaVariables.vAceptar =false;
                }
                 
         
          if (txtMotivo.getText().trim().length() >= 3 && txtMotivo.getText().trim().length() <100){
                log.debug("ingreso el motivo");
               
            
            VariablesCaja.vMotivoAnulacion =txtMotivo.getText();
                log.debug("DlgMotivoAnularVariablesCaja.vMotivoAnulacion:" + VariablesCaja.vMotivoAnulacion);
               // FarmaVariables.vAceptar =true;
            }
        
          else 
          {
            FarmaUtility.showMessage(this, "Debe ingresar el motivo de la anulacion. Minimo 3 y maximo 100 caracteres", null);
            FarmaVariables.vAceptar =false;
            
          }
          
          if(!vTipoMotivo.equals("0") && (txtMotivo.getText().trim().length() >= 3 && txtMotivo.getText().trim().length() <100)){
                  cerrarVentana(true);
              }
          
        }
    /**
     * Se agrego para el tipo motivo de anulación.
     * LTAVARA  05.09.2014
     * */
    
    private void cmbTipoMotivo_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtMotivo);
        }
        chkKeyPressed(e);
    }
    private void lblTipoMotivo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipoMotivo);
    }
    private void cmbTipoMotivo_actionPerformed(ActionEvent actionEvent) {
    }
    
    private void cargaCombo()
    {

        //LTAVARA 09.09.2014 Se realiza la carga desde la BD
        ArrayList parametros = new ArrayList();
        parametros.add(ConstantsCaja.COD_MAESTRO.TIPO_MOTIVO_NC);
        FarmaLoadCVL.loadCVLFromSP(cmbTipoMotivo, 
                                   "cmbTipoMotivo",
                                   "PTOVENTA_CAJ_ANUL.CAJ_LISTA_MOTIVO_NC(?)", 
                                   parametros, 
                                   true);
    }
}
