package mifarma.ptoventa.mayorista;

import com.gs.mifarma.componentes.JButtonLabel;

import com.gs.mifarma.componentes.JLabelFunction;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JDialog;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JSeparator;
import javax.swing.JTable;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.mayorista.reference.ConstantsVtaMayorista;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaPrecioDscto extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaPrecioDscto.class);
    @SuppressWarnings("compatibility:-6467760288251474040")
    private static final long serialVersionUID = 1L;

    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabel lblDescripcion = new JLabel();
    private JTable tblPreciosDscto = new JTable();
    private FarmaTableModel tblModelPreciosDscto;
    private JPanel pnlTituloTabla = new JPanel();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtCantidad = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JList jList1 = new JList();
    private JLabel jLabel3 = new JLabel();
    private JTextField txtCantMinimaCompra = new JTextField();
    private JTextField txtPrecioUnitAcordado = new JTextField();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    
    private int stockProducto;

    public DlgListaPrecioDscto() {
        this(null, "", false);
    }
    
    public DlgListaPrecioDscto(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(255, 305));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Seleccione precio unitario");
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
        
        lblDescripcion.setText("Seleccione el precio unitario del producto");
        lblDescripcion.setFont(new Font("SansSerif", 1, 13));
        lblDescripcion.setBounds(new Rectangle(15, 10, 280, 15));
        
        pnlTituloTabla.setBounds(new Rectangle(25, 10, 204, 20));
        pnlTituloTabla.setBackground(new Color(255, 130, 14));
        pnlTituloTabla.setLayout(null);
        
        btnRelacionProductos.setText("Relación de Precios");
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 20));
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
            });


        jScrollPane1.setBounds(new Rectangle(25, 30, 204, 130));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jLabel1.setText("Cantidad :");
        jLabel1.setBounds(new Rectangle(35, 220, 110, 15));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        txtCantidad.setBounds(new Rectangle(155, 215, 65, 20));
        txtCantidad.setName("txtCantidad");
        txtCantidad.setFont(new Font("Tahoma", 1, 11));
        txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
        txtCantidad.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    FarmaUtility.admitirDigitos(txtCantidad, e);
                }
                public void keyReleased(KeyEvent e){
                }
                public void keyPressed(KeyEvent e){
                    chkKeyPressed(e);
                }
            });

        jLabel2.setText("Cant.Minima :");
        jLabel2.setBounds(new Rectangle(35, 170, 110, 15));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("Precio Unit.Acordado :");
        jLabel3.setBounds(new Rectangle(10, 195, 135, 15));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        txtCantMinimaCompra.setBounds(new Rectangle(155, 165, 65, 20));
        txtCantMinimaCompra.setEditable(false);
        txtCantMinimaCompra.setFont(new Font("Tahoma", 1, 11));
        txtCantMinimaCompra.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecioUnitAcordado.setBounds(new Rectangle(155, 190, 65, 20));
        txtPrecioUnitAcordado.setEditable(false);
        txtPrecioUnitAcordado.setFont(new Font("Tahoma", 1, 11));
        txtPrecioUnitAcordado.setHorizontalAlignment(JTextField.RIGHT);
        jScrollPane1.getViewport();
        
        tblPreciosDscto.setFont(new Font("SansSerif", 0, 12));
        tblPreciosDscto.setName("tblPreciosDscto");
        tblPreciosDscto.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    tblPreciosDscto_keyTyped(e);
                }
                public void keyReleased(KeyEvent e){
                    tblPreciosDscto_keyReleased(e);
                }
                public void keyPressed(KeyEvent e){
                    chkKeyPressed(e);
                }
            });

        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(25, 250, 100, 20));
        
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(145, 250, 85, 20));
        
        //jContentPane.add(lblDescripcion, null);
        jContentPane.add(txtPrecioUnitAcordado, null);
        jContentPane.add(txtCantMinimaCompra, null);
        jContentPane.add(jLabel3, null);
        jContentPane.add(jLabel2, null);
        jContentPane.add(txtCantidad, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(lblEsc, null);
        jScrollPane1.getViewport().add(tblPreciosDscto, null);
        jContentPane.add(jScrollPane1, null);
        pnlTituloTabla.add(btnRelacionProductos, null);
        
        jContentPane.add(pnlTituloTabla, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.getContentPane().add(jList1, BorderLayout.NORTH);
    }
    
    private void initialize() throws Exception {
        tblModelPreciosDscto = new FarmaTableModel(ConstantsVtaMayorista.columnsListaPreciosDscto, ConstantsVtaMayorista.defaultValueListaPreciosDscto, 1);
        FarmaUtility.initSimpleList(tblPreciosDscto, tblModelPreciosDscto, ConstantsVtaMayorista.columnsListaPreciosDscto);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocusJTable(tblPreciosDscto);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void tblPreciosDscto_keyTyped(KeyEvent keyEvent) {
    }

    private void tblPreciosDscto_keyReleased(KeyEvent keyEvent) {
    }
    
    private void btnRelacionProductos_actionPerformed(ActionEvent actionEvent) {
        FarmaUtility.moveFocusJTable(tblPreciosDscto);
        txtCantidad.setText("");
        txtCantMinimaCompra.setText("");
        txtPrecioUnitAcordado.setText("");
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }else if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(e.getSource() instanceof JTable && tblPreciosDscto.getName().equalsIgnoreCase(((JTable)e.getSource()).getName())){
                e.consume();
                int seleccion = tblPreciosDscto.getSelectedRow();
                if(seleccion > -1){
                    FarmaUtility.moveFocus(txtCantidad);
                    txtCantMinimaCompra.setText(FarmaUtility.getValueFieldArrayList(tblModelPreciosDscto.data, seleccion, 0));
                    txtPrecioUnitAcordado.setText(FarmaUtility.getValueFieldArrayList(tblModelPreciosDscto.data, seleccion, 2));
                }
            }
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            if(e.getSource() instanceof JTextField && txtCantidad.getName().equalsIgnoreCase(((JTextField)e.getSource()).getName())){
                btnRelacionProductos.doClick();
            }
        }else if(UtilityPtoVenta.verificaVK_F1(e) && txtPrecioUnitAcordado.getText().trim().length()>0){
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            if (FarmaVariables.vAceptar) {
                DlgNuevoPrecioDscto dlgNuevoPrecioVolumen = new DlgNuevoPrecioDscto(myParentFrame, "", true);
                dlgNuevoPrecioVolumen.setCodigoProducto(VariablesVentas.vCod_Prod);
                dlgNuevoPrecioVolumen.setPrecioAntiguo(txtPrecioUnitAcordado.getText());
                dlgNuevoPrecioVolumen.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    txtPrecioUnitAcordado.setText(FarmaUtility.formatNumber(dlgNuevoPrecioVolumen.getPrecioNuevoIngresado()));
                    FarmaVariables.vAceptar = false;
                }
            }else{
                FarmaUtility.showMessage(this, "Opcion habilitada solo para Administrador del Local", txtCantidad);
            }
        }else if(UtilityPtoVenta.verificaVK_F11(e)){
            if(e.getSource() instanceof JTextField && txtCantidad.getName().equalsIgnoreCase(((JTextField)e.getSource()).getName())){
                if(txtCantidad.getText() == null || (txtCantidad.getText() != null && txtCantidad.getText().trim().length() == 0)){
                    FarmaUtility.showMessage(this, "Debe Ingresar Cantidad", txtCantidad);
                    return;
                }else{
                    int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                    int cantidadMinima = Integer.parseInt(txtCantMinimaCompra.getText().trim());
                    if(cantidad == 0){
                        FarmaUtility.showMessage(this, "Debe Ingresar Cantidad", txtCantidad);
                        return;
                    }else if(cantidad < 0){
                        FarmaUtility.showMessage(this, "No puede ingresar cantidad negativa.", txtCantidad);
                        return;
                    }else if(cantidad > stockProducto){
                        FarmaUtility.showMessage(this, "Cantidad ingresado mayor al stock actual.", txtCantidad);
                        return;
                    }else if(cantidad < cantidadMinima){
                        FarmaUtility.showMessage(this, "Cantidad ingresada menor a la requerida para el precio unitario.", txtCantidad);
                        return;
                    }
                    cerrarVentana(true);
                }
            }
        }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    public void cargarListaProducto(ArrayList array){
        tblModelPreciosDscto.data = array;
    }
    
    public void setStockProducto(int stockProducto){
        this.stockProducto = stockProducto;
    }
    
    public String obtenerNuevoPrecioAcordado(){
        return txtPrecioUnitAcordado.getText();
    }
    
    public String obtenerCantidadAcordada(){
        return txtCantidad.getText();
    }
}
