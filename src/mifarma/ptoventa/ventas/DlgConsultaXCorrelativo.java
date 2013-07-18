package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;

import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgConsultaXCorrelativo extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgConsultaXCorrelativo.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();    
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblTipoComprobante = new JButtonLabel();
    private JButtonLabel lblNroComprobante = new JButtonLabel();    
    private JTextFieldSanSerif txtSerie = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNroComprobante = new JTextFieldSanSerif();
    private JButtonLabel lblMonto = new JButtonLabel();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JComboBox cmbTipoComp = new JComboBox();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;

    public DlgConsultaXCorrelativo() {
        this(null, "", false);
    }

    public DlgConsultaXCorrelativo(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            MyParentFrame = parent;
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(419, 206));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Consulta de Correlativo Comprobante");
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        pnlTitle.setBounds(new Rectangle(5, 10, 400, 135));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblTipoComprobante.setText("Tipo Comprobante:");
        lblTipoComprobante.setMnemonic('T');
        lblTipoComprobante.setBounds(new Rectangle(20, 20, 110, 15));
        lblTipoComprobante.setBackground(Color.white);
        lblTipoComprobante.setForeground(new Color(255, 90, 33));
        lblTipoComprobante.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblTipoComprobante_actionPerformed(e);
                    }
                });
        txtNroComprobante.setBounds(new Rectangle(215, 55, 130, 20));
        lblNroComprobante.setText("Nro. Comprobante:");
        lblNroComprobante.setMnemonic('N');
        lblNroComprobante.setBounds(new Rectangle(20, 55, 105, 15));
        lblNroComprobante.setForeground(new Color(255, 90, 33));
        lblNroComprobante.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblNroComprobante_actionPerformed(e);
                    }
                });
        txtSerie.setBounds(new Rectangle(160, 55, 60, 20));
        txtSerie.setLengthText(3);
        txtSerie.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtSerie_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtSerie_keyReleased(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtSerie_keyTyped(e);
                    }
                });
        txtNroComprobante.setBounds(new Rectangle(235, 55, 145, 20));
        txtNroComprobante.setLengthText(7);        
        txtNroComprobante.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtNroComprobante_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtNroComprobante_keyReleased(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtNroComprobante_keyTyped(e);
                    }
                });
        lblMonto.setText("Monto:");
        lblMonto.setMnemonic('M');
        lblMonto.setBounds(new Rectangle(20, 95, 75, 15));
        lblMonto.setForeground(new Color(255, 90, 33));
        lblMonto.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblMonto_actionPerformed(e);
                    }
                });
        txtMonto.setBounds(new Rectangle(160, 95, 145, 19));
        txtMonto.setLengthText(10);
        txtMonto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtMonto_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtMonto_keyTyped(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtMonto_keyReleased(e);
                    }
                });
        cmbTipoComp.setBounds(new Rectangle(160, 20, 220, 20));
        cmbTipoComp.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cmbTipoComp_actionPerformed(e);
                    }
                });
        cmbTipoComp.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbTipoComp_keyPressed(e);
                    }
                });
        btnF11.setBounds(new Rectangle(5, 155, 117, 20));
        btnEsc.setBounds(new Rectangle(290, 155, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnEsc.setText("[Esc] Salir");
        pnlTitle.add(cmbTipoComp, null);
        pnlTitle.add(lblMonto, null);
        pnlTitle.add(txtMonto, null);
        pnlTitle.add(txtNroComprobante, null);
        pnlTitle.add(txtSerie, null);
        pnlTitle.add(lblNroComprobante, null);
        pnlTitle.add(lblTipoComprobante, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    private void initialize(){
        cargaCombo();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", cmbTipoComp);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbTipoComp);        
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_F11)
      {          
        if(validarDatos())
            getObtenerCorrelativo();
        else
            FarmaUtility.showMessage(this,"Falta Ingresar Información.",cmbTipoComp);
        
      } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
        cerrarVentana(false);
      }
    }

    private void cerrarVentana(boolean pAceptar){
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }
    
    private boolean validarDatos(){
        boolean flag = true;
        if(txtSerie.getText().trim().length()==0)
            return flag = false;
        
        if(txtNroComprobante.getText().trim().length()==0)
            return flag = false;
        
        if(txtMonto.getText().trim().length()==0)
            return flag = false;
        
        if(cmbTipoComp.getSelectedObjects().length==0)
            return flag = false;
        
        return flag;
    }

    private void cmbTipoComp_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(txtSerie);
    }

    private void txtSerie_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            txtSerie.setText(FarmaUtility.caracterIzquierda(txtSerie.getText(), 
                                                                  3, "0"));
            FarmaUtility.moveFocus(txtNroComprobante);
        }
        
        chkKeyPressed(e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText(), 
                                                                  7, "0"));
            FarmaUtility.moveFocus(txtMonto);
        }
        chkKeyPressed(e);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(cmbTipoComp);
        }
        chkKeyPressed(e);
    }
    
    private void getObtenerCorrelativo(){
        System.out.println("Obtiene Correlativo");
        String vTipoComp = "";
        String vMontoNeto = "";
        String vNumCompPago = "";
        String vRpta = "";
        VariablesVentas.vNumPedVta_new = "";
        VariablesVentas.vMontoNeto_new = "";
        
        //vTipoComp = String.valueOf(cmbTipoComp.hashCode());
        vTipoComp = FarmaLoadCVL.getCVLCode(ConstantsVentas.HASHTABLE_TIPOS_COMPROBANTES, cmbTipoComp.getSelectedIndex());
        vMontoNeto = txtMonto.getText().trim();
        vNumCompPago = txtSerie.getText().trim()+txtNroComprobante.getText().trim();
        System.out.println("vTipoComp"+vTipoComp);
        try{
        vRpta = DBVentas.getIndImprimirCorrelativo(vTipoComp,vMontoNeto,vNumCompPago);
        }
        catch(SQLException sql){            
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"La información ingresada no es correcta verifique.",cmbTipoComp);
        }
        getDatos(vRpta);
    }
    
    private void getDatos(String pDato){
        String pSeparador = ";";
        pDato.trim();        
        String[] arrayLetra = pDato.split(pSeparador);
        if(arrayLetra.length > 0 && arrayLetra.length == 2){
            /*for (int i = 0; i < arrayLetra.length; i++) {
                    System.out.println(arrayLetra[i]);                
            }*/
            VariablesVentas.vNumPedVta_new = arrayLetra[0].trim();
            VariablesVentas.vMontoNeto_new = arrayLetra[1].trim();
            System.out.println("VariablesVentas.vNumPedVta_new: "+VariablesVentas.vNumPedVta_new +" - "+ VariablesVentas.vMontoNeto_new);
            cerrarVentana(true);
        }
        
    }

    private void lblTipoComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipoComp);
    }

    private void lblNroComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSerie);
    }

    private void lblMonto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMonto);
    }
    
    private void cargaCombo(){
          FarmaLoadCVL.loadCVLfromArrays(cmbTipoComp,
                                         ConstantsVentas.HASHTABLE_TIPOS_COMPROBANTES,
                                         ConstantsVentas.TIPOS_COMPROBANTES_CODIGO,
                                         ConstantsVentas.TIPOS_COMPROBANTES_DESCRIPCION,true);
    }

    private void cmbTipoComp_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtSerie);
        }
        chkKeyPressed(e);
    }

    private void txtSerie_keyReleased(KeyEvent e) {        
    }

    private void txtNroComprobante_keyReleased(KeyEvent e) {        
    }

    private void txtMonto_keyTyped(KeyEvent e) { 
        FarmaUtility.admitirDigitosDecimales(txtMonto,e);
    }

    private void txtSerie_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtSerie,e);
    }

    private void txtNroComprobante_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNroComprobante,e);
    }

    private void txtMonto_keyReleased(KeyEvent e) {
//        FarmaUtility.admitirDigitosDecimales(txtMonto,e);
    }
}
