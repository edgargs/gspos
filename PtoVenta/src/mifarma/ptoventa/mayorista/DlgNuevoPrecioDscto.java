package mifarma.ptoventa.mayorista;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.mayorista.reference.FacadeMayorista;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgNuevoPrecioDscto extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgNuevoPrecioDscto.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabel lblNuevoPrecio = new JLabel();
    private JTextField txtNuevoPrecio = new JTextField();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private String codigoProducto;
    private double precioAntiguo;
    private double precioNuevo;
    private double precioMinimo;
    private JLabelOrange lblPrecioMinimo_T = new JLabelOrange();
    private JLabelOrange lblPrecioMinimo = new JLabelOrange();

    public DlgNuevoPrecioDscto() {
        this(null, "", false);
    }

    public DlgNuevoPrecioDscto(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(245, 117));
        this.getContentPane().setLayout( null );
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Ingrese precio unitario");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        
        jContentPane.setForeground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(360, 331));
        jContentPane.setBackground(Color.white);
        lblNuevoPrecio.setText("Precio Unitario");
        lblNuevoPrecio.setBounds(new Rectangle(10, 35, 95, 20));
        txtNuevoPrecio.setBounds(new Rectangle(110, 35, 120, 20));
        txtNuevoPrecio.setHorizontalAlignment(JTextField.RIGHT);
        txtNuevoPrecio.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                FarmaUtility.admitirDigitosDecimales(txtNuevoPrecio, e);
            }
            public void keyReleased(KeyEvent e){
            }
            public void keyPressed(KeyEvent e){
                chkKeyPressed(e);
            }
        });
        
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(35, 65, 100, 20));
        
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(145, 65, 85, 20));

        lblPrecioMinimo_T.setText("Precio M\u00ednimo:");
        lblPrecioMinimo_T.setBounds(new Rectangle(10, 10, 95, 20));
        lblPrecioMinimo.setText("99.99");
        lblPrecioMinimo.setBounds(new Rectangle(110, 10, 120, 20));
        lblPrecioMinimo.setHorizontalAlignment(SwingConstants.RIGHT);
        jContentPane.add(lblPrecioMinimo, null);
        jContentPane.add(lblPrecioMinimo_T, null);
        jContentPane.add(txtNuevoPrecio, null);
        jContentPane.add(lblNuevoPrecio, null);

        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    private void initialize() {
        lblPrecioMinimo.setText("");
    }
    
    private void this_windowOpened(WindowEvent windowEvent) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNuevoPrecio);
        // llamar al metodo para obtener el precio minimo y validar
        FacadeMayorista facade = new FacadeMayorista();
        double montoMinimo = facade.obtienePrecioMinimoVenta(this, codigoProducto);
        if(montoMinimo == -1){
            cerrarVentana(false);
        }else{
            precioMinimo = montoMinimo;
            lblPrecioMinimo.setText(FarmaUtility.formatNumber(precioMinimo));
        }
    }
    
    private void this_windowClosing(WindowEvent windowEvent) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }else if(UtilityPtoVenta.verificaVK_F11(e)){
            String precioIngresado = txtNuevoPrecio.getText().trim();
            if(precioIngresado.length() == 0){
                FarmaUtility.showMessage(this, "Ingrese Valor", txtNuevoPrecio);
            }else{
                double nuevoPrecioIngresado = FarmaUtility.getDecimalNumber(precioIngresado);
                if(nuevoPrecioIngresado == 0){
                    FarmaUtility.showMessage(this, "Monto no puede ser 0", txtNuevoPrecio);
                    return;
                }else if(nuevoPrecioIngresado < precioMinimo ){
                    FarmaUtility.showMessage(this, "Monto no permitido, verifique!!!", txtNuevoPrecio);
                    return;
                }
                precioNuevo = nuevoPrecioIngresado;
                cerrarVentana(true);
            }
        }
    }
    
    public void setCodigoProducto(String codigoProducto){
        this.codigoProducto = codigoProducto;
    }
    
    public void setPrecioAntiguo(String antiguoPrecio){
        precioAntiguo = Double.parseDouble(antiguoPrecio);
        txtNuevoPrecio.setText(FarmaUtility.formatNumber(precioAntiguo));
    }
    
    public double getPrecioNuevoIngresado(){
        return precioNuevo;
    }
}
