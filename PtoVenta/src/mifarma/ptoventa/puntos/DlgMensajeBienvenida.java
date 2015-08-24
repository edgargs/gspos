package mifarma.ptoventa.puntos;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import farmapuntos.bean.TarjetaBean;

import farmapuntos.util.FarmaPuntosConstante;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import oracle.jdeveloper.layout.VerticalFlowLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dialogo de ingreso de datos
 * @author KMONCADA
 * @since 10.03.2015
 */
public class DlgMensajeBienvenida extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMensajeBienvenida.class);

    private JPanel jPanel1 = new JPanel();
    
    private JButton btnAceptar = new JButton();
    private JButton btnF11 = new JButton();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JTextPane lblMsjEncabezado = new JTextPane();
    private Icon optionIcon = UIManager.getIcon("OptionPane.warningIcon");
    private JLabel dialogIcon = new JLabel(optionIcon);
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

    private String tipoValidacion = "";
    private String nombreMensaje;
    private String nroDni;

    public DlgMensajeBienvenida(Dialog dialog, boolean b) {
        super(dialog, b);
        try {
            jbInit();
            btnAceptar.requestFocus();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgMensajeBienvenida(Frame dialog, boolean b) {
        super(dialog, b);
        try {
            jbInit();
            btnAceptar.requestFocus();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(326, 172));
        this.setDefaultCloseOperation(0);
        this.setTitle("Mensaje del Sistema");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanel1.setLayout(borderLayout1);
        
        btnF11.setText("[F11] Imprime Ptos.");
        btnF11.setMnemonic('I');
        btnF11.setFont(new Font("Dialog", 1, 11));
        btnF11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnF11ActionPerformed(e);
            }
        });
        btnF11.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnF11KeyPressed(e);
            }
        });
        btnF11.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                jButton_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                jButton_focusLost(e);
            }
        });
        
        btnAceptar.setText("Aceptar");
        btnAceptar.setMnemonic('A');
        btnAceptar.setFont(new Font("Dialog", 1, 12));
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });
        btnAceptar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButton1_keyPressed(e);
            }
        });
        btnAceptar.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                jButton_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                jButton_focusLost(e);
            }
        });
        jPanel3.setLayout(verticalFlowLayout1);
        
        lblMsjEncabezado.setFont(new Font("Dialog", 1, 12));
        lblMsjEncabezado.setOpaque(false);
        lblMsjEncabezado.setEditable(false);
        lblMsjEncabezado.setFocusable(false);
        lblMsjEncabezado.setText("Bienvenido561");
        lblMsjEncabezado.setBounds(new Rectangle(5, 5, 261, 20));


        jLabel3.setText(" ");
        jLabel3.setFont(new Font("Dialog", 1, 4));
        jPanel3.add(lblMsjEncabezado,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                           new Insets(0, 0, 0, 0), 129, 58));
        jPanel4.add(jLabel4, null);
        jPanel4.add(dialogIcon, null);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel1.add(jPanel4, BorderLayout.WEST);
        jPanel1.add(jLabel3, BorderLayout.NORTH);
        
        jPanel2.add(btnF11, null);
        jPanel2.add(btnAceptar, null);
        
        this.getContentPane().add(jPanel1, null);
        if(VariablesPuntos.frmPuntos!=null){
            TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
            String mensaje = "";
            if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())){
                mensaje = "Bienvenido\n"+VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                            VariablesFidelizacion.vApeMatCliente+"\n"+"DNI/CE: "+VariablesFidelizacion.vDniCliente;
                btnF11.setVisible(false);
            }else{
                if(tarjetaPuntos==null){
                    mensaje = "Bienvenido\n"+VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                                VariablesFidelizacion.vApeMatCliente+"\n"+"DNI/CE: "+VariablesFidelizacion.vDniCliente;
                    btnF11.setVisible(false);
                }else{
                    mensaje = "Bienvenido\n";
                    if(tarjetaPuntos.getNombreCompleto()!=null && tarjetaPuntos.getNombreCompleto().length()>0){
                        mensaje = mensaje + tarjetaPuntos.getNombreCompleto()+"\n";
                    }else{
                        mensaje = mensaje + VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                                    VariablesFidelizacion.vApeMatCliente+"\n";
                    }
                    if(tarjetaPuntos.getDni() != null && tarjetaPuntos.getDni().length()>0){
                        mensaje = mensaje + "DNI/CE: "+tarjetaPuntos.getDni();
                    }else{
                        mensaje = mensaje + "DNI/CE: "+VariablesFidelizacion.vDniCliente;
                    }
                    if(tarjetaPuntos.getPuntosTotalAcumulados()!=null){//LTAVARA 21.04.2015 - Validacion cuando esta vacio 
                        mensaje = mensaje+"\nPunto(s): "+FarmaUtility.formatNumber(tarjetaPuntos.getPuntosTotalAcumulados());
                    }
                    if(!tarjetaPuntos.getDeslizaTarjeta()){
                        btnF11.setVisible(false);
                    }
                }
            }
            // desarrollo3 30.03.2015 muestra el nombre del cliente fidelizado
            lblMsjEncabezado.setText(mensaje);
        }
        
        btnAceptar.requestFocus();
    }

    String getValue() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        String rspta = null;
        return rspta;
    }
    
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnAceptar);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        cerrarVentana();
    }
    
    private void jButton1_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana();
        }else{
            if(UtilityPtoVenta.verificaVK_F11(e)){
                realizarImpresion();
            }else{
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    btnF11.requestFocus();
                }
            }
        }
    }
    
    private void btnF11KeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || UtilityPtoVenta.verificaVK_F11(e)) {
            realizarImpresion();
        }else{
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                cerrarVentana();
            }else{
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    btnAceptar.requestFocus();
                }
            }
        }
    }
    
    private void btnF11ActionPerformed(ActionEvent e) {
        realizarImpresion();
    }

    private void cerrarVentana(){
        this.setVisible(false);
        this.dispose();
    }
    
    private void realizarImpresion(){
        if (btnF11.isVisible()){
            if(VariablesPuntos.frmPuntos!=null){
                TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
                UtilityPuntos.imprimeSaldo(this ,null,tarjetaPuntos,tarjetaPuntos.getNumeroTarjeta(),UtilityPuntos.enmascararTarjeta(tarjetaPuntos.getNumeroTarjeta()));
                FarmaUtility.showMessage(this, "Recoga la impresión.", null);
            }
        }
    }
    
    private void jButton_focusGained(FocusEvent e) {
        ((JButton)e.getSource()).setBackground(new Color(255, 130, 40));
        ((JButton)e.getSource()).setForeground(Color.WHITE);
    }

    private void jButton_focusLost(FocusEvent e) {
        ((JButton)e.getSource()).setBackground(new Color(237, 237, 237));
        ((JButton)e.getSource()).setForeground(Color.BLACK);
    }
    
    public static String showMensajeBienvenida(Object pJDialog) {
        DlgMensajeBienvenida optioPane = null;
        if (pJDialog instanceof JDialog) {
            JDialog myJDialog = (JDialog)pJDialog;
            optioPane = new DlgMensajeBienvenida(myJDialog,  true);
        } else if (pJDialog instanceof Frame) {
            Frame myFrame = (Frame)pJDialog;
            optioPane = new DlgMensajeBienvenida(myFrame,  true);
        } else {
            optioPane = new DlgMensajeBienvenida(new JDialog(), true);
        }
        //optioPane.setTipoValidacion(pTipoValidacion);

        String bRetorno = optioPane.getValue();

        log.warn("rptaDialogo:" + bRetorno);
        return bRetorno;
    }

    public static void main(String[] args) {
        //JInputDialog.showInputDialog(null,"¿Cuál es su edad?");
        DlgMensajeBienvenida.showMensajeBienvenida(null);
    }

    private void setTipoValidacion(String tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }


    public void setNombreMensaje(String nombreMensaje){
        this.nombreMensaje = nombreMensaje;
    }
    
    public void setNroDni(String nroDni){
        this.nroDni = nroDni;
    }
}
