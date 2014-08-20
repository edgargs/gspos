package mifarma.ptoventa.administracion.fondoSencillo;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;

import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.JLabel;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.fondoSencillo.reference.ConstantsFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.DBFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.UtilityFondoSencillo;

import mifarma.ptoventa.administracion.fondoSencillo.reference.VariablesFondoSencillo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgAsignarMontoFondoSencillo extends JDialog {
    //Declarando Variables Globales 
    Frame myParentFrame;
    
    private static final Logger log = 
        LoggerFactory.getLogger(DlgAsignarMontoFondoSencillo.class);
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    
    private JButtonLabel btnCajero = new JButtonLabel();
    private JTextFieldSanSerif txtCajero = new JTextFieldSanSerif();
    private JButtonLabel btnMonto = new JButtonLabel();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JLabelFunction btnAceptar = new JLabelFunction();
    private JLabelFunction btnSalir = new JLabelFunction();
    private JLabel lblMensaje = new JLabel();

    public DlgAsignarMontoFondoSencillo() {
        this(null, "", false);
    }

    public DlgAsignarMontoFondoSencillo(Frame parent, String title, 
                                        boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        //***Valores Básicos Pantalla ***
        this.setSize(new Dimension(382, 139));
        this.getContentPane().setLayout(borderLayout1);

        jContentPane.add(lblMensaje, null);
        jContentPane.add(btnSalir, null);
        jContentPane.add(btnAceptar, null);
        jContentPane.add(txtMonto, null);
        jContentPane.add(btnMonto, null);
        jContentPane.add(txtCajero, null);
        jContentPane.add(btnCajero, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Asignar Monto de Fondo de Sencillo");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });       
        
        //***fin ***

        btnCajero.setText("Cajero:");
        btnCajero.setBounds(new Rectangle(15, 20, 50, 20));
        btnCajero.setForeground(new Color(255, 132, 0));
        //btnCajero.setMnemonic('c');
        txtCajero.setBounds(new Rectangle(75, 20, 290, 20));
        txtCajero.setEditable(false);
        btnMonto.setText("Monto:");
        btnMonto.setBounds(new Rectangle(15, 50, 50, 15));
        btnMonto.setForeground(new Color(255, 132, 0));
        btnMonto.setMnemonic('m');
        btnMonto.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnMonto_actionPerformed(e);
                    }
                });
        txtMonto.setBounds(new Rectangle(75, 50, 80, 20));
        txtMonto.setLengthText(7);
        txtMonto.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        txtMonto_keyReleased(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtMonto_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtMonto_keyTyped(e);
                    }
                });
        btnAceptar.setBounds(new Rectangle(75, 80, 117, 20));
        btnAceptar.setText("[Enter] Aceptar");
        btnSalir.setBounds(new Rectangle(200, 80, 95, 20));
        btnSalir.setText("[ESC] Cerrar");
        lblMensaje.setText("Nuevos Soles");
        lblMensaje.setBounds(new Rectangle(160, 55, 95, 15));
        lblMensaje.setForeground(new Color(255, 132, 0));
        lblMensaje.setFont(new Font("SansSerif", 0, 11));

    }
    
    private void initialize() {
        cargarDatosCajero();
    }


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 txtMonto);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtMonto);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            cerrarVentana(false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER){
            e.consume();
            if(validaDatos()){
                
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro que desea efectuar la operación?")) {
                    log.debug("paso");
                    //hacer insert con los datos que se envía cajero                
                    log.debug("Monto: "+txtMonto.getText().trim()+"\n"+
                                       "Usu Cajero:"+VariablesFondoSencillo.vSecUsuCajero+"\n"+
                    "Usu Químico: "+FarmaVariables.vNuSecUsu+"\n"+
                                       "Estado: "+ConstantsFondoSencillo.EstadoEmitido+"\n"+
                                       "Tipo Fondo: "+ ConstantsFondoSencillo.TipoFondoAsigna+"\n"+
                                       "Usu: "+FarmaVariables.vIdUsu+"\n"+
                                       "Ip: "+FarmaVariables.vIpPc);
                    if(ingresaMonto(txtMonto.getText().trim())){
                        limpiarVariables();
                        cerrarVentana(true);                       
                    }              
                }
            }
        }
    }
    
    private void chkKeyReleased(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto,e);
    }

    private void txtMonto_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
        
    }

    private void txtMonto_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto,e);
    }

    private void btnMonto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMonto);
    }
    
    private boolean validaDatos(){
        boolean flag = true;
        if(txtMonto.getText().trim().length()<0){
            flag = false;
            FarmaUtility.showMessage(this,"No ha ingresado Monto.\n¡Verifique¡",txtMonto);
        } 
        else if(!UtilityFondoSencillo.validaMonto(txtMonto.getText().trim())){
            flag = false;    
            FarmaUtility.showMessage(this,"Ingrese Monto Correcto.",txtMonto);
        }
        else if(!UtilityFondoSencillo.validaDecimal(txtMonto.getText().trim(),4)){
            flag = false;
            FarmaUtility.showMessage(this,"Ingrese Máximo 4 enteros.",txtMonto);
        } /*else if(!UtilityFondoSencillo.validaMaximoDigitosEnteros(txtMonto.getText().trim(),4)){
            flag = false;    
            FarmaUtility.showMessage(this,"Ingrese Máximo 4 dígitos enteros.",txtMonto);
        } */
        else if (Double.parseDouble(txtMonto.getText().trim()) <= 0 ){
            flag = false;
            FarmaUtility.showMessage(this,"Ingrese Monto Mayor a Cero.",txtMonto);
        }
            
        return flag;
    }
    
    private void cargarDatosCajero(){
        txtCajero.setText(VariablesFondoSencillo.vNombresCajero+" "+VariablesFondoSencillo.vApePaterno+" "+VariablesFondoSencillo.vApeMaterno);
    }
    
    private boolean ingresaMonto(String pMonto){
        boolean flag = false;
        try{
            String Rpta = DBFondoSencillo.getIndTieneCajAbierta(VariablesFondoSencillo.vSecUsuCajero);
            if(!Rpta.trim().equalsIgnoreCase("S")
               && !UtilityFondoSencillo.getIndTieneFondoSencillo(this,VariablesFondoSencillo.vSecUsuCajero,
                                                           txtMonto).trim().equalsIgnoreCase("S")
              ){
                //DBFondoSencillo.insAsignacionSencillo(pMonto,
             VariablesFondoSencillo.vHisSecFondoSen = DBFondoSencillo.insAsignacionSencillo2(pMonto,
                                                      VariablesFondoSencillo.vSecUsuCajero, // DESTINO
                                                      FarmaVariables.vNuSecUsu, // ORIGEN
                                                      ConstantsFondoSencillo.EstadoEmitido,
                                                      ConstantsFondoSencillo.TipoFondoAsigna,
                                                      FarmaVariables.vIdUsu,
                                                      FarmaVariables.vIpPc).trim();   
                FarmaUtility.aceptarTransaccion();
                flag = true;
                }else{
                    flag = false;
                    FarmaUtility.showMessage(this,"La caja fue abierta desde otro punto o ya tiene fondo Asignado." +
                        "\nNo se le puede asignar monto hasta que Cierre la caja.",txtMonto);
                    cerrarVentana(false);
                }
           }
        catch(SQLException sql){
            log.error("",sql);
            FarmaUtility.liberarTransaccion();
            //FarmaUtility.showMessage(this,"Error al asignar Monto a Cajero.",txtMonto);
            if (sql.getErrorCode() > 20000) {
                FarmaUtility.showMessage(this, 
                                         sql.getMessage().substring(10, 
                                                                    sql.getMessage().indexOf("ORA-06512")), 
                                         txtMonto);
            } else {
                FarmaUtility.showMessage(this, 
                                         "Error al asignar Monto a Cajero.\n" +
                                        sql.getMessage(), txtMonto);
            }

            
            
            
        }
        return flag;            
    }
    
    private void limpiarVariables(){
        //log.debug("Monto: "+txtMonto.getText().trim()+"\n"+
        VariablesFondoSencillo.vSecUsuCajero = "";        
    }
}
