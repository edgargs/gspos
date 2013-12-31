package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.UtilityLectorTarjeta;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.DBPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDatosTarjeta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      18.03.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDatosTarjeta extends JDialog {
    //Variables para el pago con tarjeta
    private static final Logger log = LoggerFactory.getLogger(DlgDatosTarjeta.class);

    private String bin="";
    private String desc_prod="";
    private String strCodFormaPago="";
    private String strNombrePropTarjeta = "";
    private String strFechaVencTarjeta = "";
        
    private String strDescFormaPago="";
    private String nroTarjeta="";
    
    private String infoTarj="";
    private int cont=0;
    private String mestarj="";
    private String aniotarj="";
    
    private Frame myParentFrame;
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanel pnlInfo = new JPanel();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelOrange lblMonto_T = new JLabelOrange();
    private JTextFieldSanSerif txtNroTarjeta = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDocIdentidad = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JLabelOrange lblTipoTarjeta_T = new JLabelOrange();
    private JLabelOrange lblTipoTarjeta = new JLabelOrange();
    private JLabelOrange lblmon = new JLabelOrange();
    private JLabelOrange lblmoneda = new JLabelOrange();
    private JButtonLabel lblNroTarjeta = new JButtonLabel();
    private JButtonLabel lblDocIdentidad = new JButtonLabel();
    private JButtonLabel lblLote = new JButtonLabel();
    private JLabelOrange lblmensaje = new JLabelOrange();
    private JButtonLabel lblCodVoucher = new JButtonLabel();
    private JTextFieldSanSerif txtCodVoucher = new JTextFieldSanSerif();
    private JLabelFunction lblEscape = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    ArrayList listInforTarje = new ArrayList();
    private ArrayList arrayCodFormaPago = new ArrayList();
    
    UtilityLectorTarjeta utilityLectorTarjeta = new UtilityLectorTarjeta();
    private int intCountEnter = 0;
    private String strIndLectorBanda = "N";
    
    public DlgDatosTarjeta() {
        this(null, "", false);
    }

    public DlgDatosTarjeta(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame=parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(416, 277));
        this.getContentPane().setLayout( null );
        this.setTitle("Pago con Tarjeta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        pnlFondo.setBounds(new Rectangle(0, 0, 415, 255));
        pnlInfo.setBounds(new Rectangle(10, 25, 390, 185));
        pnlInfo.setBackground(Color.white);
        pnlInfo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlInfo.setLayout(null);
        pnlTitle.setBounds(new Rectangle(10, 5, 390, 20));
        jLabelWhite1.setText("Pago con tarjeta");
        jLabelWhite1.setBounds(new Rectangle(5, 5, 100, 15));
        lblMonto_T.setText("Monto");
        lblMonto_T.setBounds(new Rectangle(15, 150, 80, 20));
        lblMonto_T.setFont(new Font("SansSerif", 1, 18));
        txtNroTarjeta.setBounds(new Rectangle(115, 10, 135, 20));
        txtNroTarjeta.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtnrotarj_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtnrotarj_keyPressed(e);
                    }
                });
        //txtNroTarjeta.setDocument(new FarmaLengthText(22));
        txtDocIdentidad.setBounds(new Rectangle(115, 60, 95, 20));
        txtDocIdentidad.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtdni_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                    txtdni_keyPressed(e);
                }
                });
        txtDocIdentidad.setDocument(new FarmaLengthText(9));
        txtLote.setBounds(new Rectangle(115, 120, 95, 20));
        txtLote.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtlote_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                    txtLote_keyPressed(e);
                }
                });
        txtLote.setDocument(new FarmaLengthText(3));
        lblTipoTarjeta_T.setText("Tipo Tarjeta");
        lblTipoTarjeta_T.setBounds(new Rectangle(15, 40, 80, 15));
        lblTipoTarjeta.setText("[TIPO]");
        lblTipoTarjeta.setBounds(new Rectangle(115, 35, 265, 15));
        lblTipoTarjeta.setForeground(new Color(43, 141, 39));
        lblmon.setText("[MONTO]");
        lblmon.setBounds(new Rectangle(195, 150, 90, 20));
        lblmon.setForeground(new Color(49, 141, 43));
        lblmon.setFont(new Font("SansSerif", 1, 18));
        lblmoneda.setText("S/.");
        lblmoneda.setBounds(new Rectangle(160, 150, 30, 20));
        lblmoneda.setForeground(new Color(49, 141, 43));
        lblmoneda.setFont(new Font("SansSerif", 1, 18));
        lblNroTarjeta.setText("Nro. Tarjeta");
        lblNroTarjeta.setBounds(new Rectangle(15, 15, 75, 15));
        lblNroTarjeta.setForeground(new Color(255, 130, 14));
        lblNroTarjeta.setMnemonic('n');
        lblNroTarjeta.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblNroTarjeta_actionPerformed(e);
                    }
                });
        lblDocIdentidad.setText("Doc. Identidad");
        lblDocIdentidad.setBounds(new Rectangle(15, 65, 90, 15));
        lblDocIdentidad.setForeground(new Color(255, 130, 14));
        lblDocIdentidad.setMnemonic('d');
        lblDocIdentidad.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblDocIdentidad_actionPerformed(e);
                    }
                });
        lblLote.setText("Lote");
        lblLote.setBounds(new Rectangle(15, 125, 95, 15));
        lblLote.setForeground(new Color(255, 130, 14));
        lblLote.setMnemonic('l');
        lblLote.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblLote_actionPerformed(e);
                    }
                });
        lblmensaje.setText("[MENSAJE]");
        lblmensaje.setBounds(new Rectangle(255, 15, 125, 15));
        lblmensaje.setForeground(new Color(49, 141, 43));
        lblCodVoucher.setText("Codigo Voucher");
        lblCodVoucher.setBounds(new Rectangle(15, 95, 95, 15));
        lblCodVoucher.setForeground(new Color(255, 130, 14));
        lblCodVoucher.setMnemonic('c');
        lblCodVoucher.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblCodVoucher_actionPerformed(e);
                    }
                });
        txtCodVoucher.setBounds(new Rectangle(115, 90, 135, 20));
        txtCodVoucher.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtcodvou_keyTyped(e);
                    }
                    
                    public void keyPressed(KeyEvent e) {
                        txtcodvou_keyPressed(e);
                    }
                });
        pnlTitle.add(jLabelWhite1, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEscape, null);
        pnlFondo.add(pnlTitle, null);
        pnlInfo.add(txtCodVoucher, null);
        pnlInfo.add(lblCodVoucher, null);
        pnlInfo.add(lblmensaje, null);
        pnlInfo.add(lblLote, null);
        pnlInfo.add(lblDocIdentidad, null);
        pnlInfo.add(lblNroTarjeta, null);
        pnlInfo.add(lblmon, null);
        pnlInfo.add(lblmoneda, null);
        pnlInfo.add(lblTipoTarjeta, null);
        pnlInfo.add(lblTipoTarjeta_T, null);
        pnlInfo.add(txtLote, null);
        pnlInfo.add(txtDocIdentidad, null);
        pnlInfo.add(txtNroTarjeta, null);
        pnlInfo.add(lblMonto_T, null);
        pnlFondo.add(pnlInfo, null);
        this.getContentPane().add(pnlFondo, null);
        txtCodVoucher.setDocument(new FarmaLengthText(20));
        lblEscape.setBounds(new Rectangle(315, 220, 85, 25));
        lblEscape.setText("[ ESC ] Escape");
        lblF11.setBounds(new Rectangle(210, 220, 100, 25));
        lblF11.setText("[ F11 ] Aceptar");
    }
    
    private void inicializarVariables(){
        
        bin="";
        desc_prod="";
        strCodFormaPago="";
        strDescFormaPago="";
        nroTarjeta="";
        txtNroTarjeta.setText("");
        txtDocIdentidad.setText("");
        txtLote.setText("");
        //txtlote.setText("");
        lblTipoTarjeta.setText("");
        infoTarj="";
        aniotarj="";
        mestarj="";
        
        intCountEnter = 0;
        strIndLectorBanda = "N";
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroTarjeta);
        lblmon.setText(VariablesCaja.vValMontoPagado);
        inicializarVariables();        
        lblmensaje.setText("");
    }
    
    private void chkkeyPressed(KeyEvent e){
        
        if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)){
            if(validaCampos()){
                txtLote.setText(FarmaUtility.completeWithSymbol(txtLote.getText(),3,"0","I"));
                
                VariablesCaja.vCodFormaPago = strCodFormaPago;
                VariablesCaja.vDescFormaPago = strDescFormaPago;
                VariablesCaja.vNumTarjCred = txtNroTarjeta.getText();
                
                VariablesCaja.vFecVencTarjCred = strFechaVencTarjeta;
                VariablesCaja.vNomCliTarjCred = strNombrePropTarjeta;
               
                //20.03.2013 LTERRAZOS Se agrega 3 parametros de pagos con tarjeta
                VariablesCaja.vDNITarj = txtDocIdentidad.getText();
                VariablesCaja.vCodVoucher = txtCodVoucher.getText();
                VariablesCaja.vCodLote = txtLote.getText();
                
                cerrarVentana(true);   
            }
        }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            
            cerrarVentana(false);
        }
    }

    private void txtnrotarj_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirDigitos(txtnrotarj,e);
    }

    private void txtdni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtDocIdentidad,e);
    }

    private void txtcodvou_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCodVoucher,e);
    }
    
    private void txtlote_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtLote,e);
    }
    
    private void txtnrotarj_keyPressed(KeyEvent e) {
        String tmpNro = "";
        String tmpNomb = "";
        String tmpFecha = "";
        if(e.getKeyCode()==KeyEvent.VK_ENTER) intCountEnter++;
        if(e.getKeyCode()==KeyEvent.VK_B) strIndLectorBanda = "S";
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(strIndLectorBanda.equals("S") && intCountEnter==1){
                    listInforTarje = utilityLectorTarjeta.capturaTeclasLector(e,txtNroTarjeta.getText());
                    tmpNro = listInforTarje.get(0).toString();
                    tmpNomb = listInforTarje.get(1).toString(); 
                    tmpFecha = listInforTarje.get(2).toString();
                    strNombrePropTarjeta = tmpNomb;
                    strFechaVencTarjeta = tmpFecha;
                    txtNroTarjeta.setText(tmpNro);
                    txtNroTarjeta.setEditable(false);
            }
            if((strIndLectorBanda.equals("S") && intCountEnter==2) || (strIndLectorBanda.equals("N") && intCountEnter==1) ){
                if(buscarInfotarjeta()){   
                    if(hablitarTipoTarjeta()){
                        lblTipoTarjeta.setText(desc_prod+"/"+strDescFormaPago);
                        FarmaUtility.moveFocus(txtDocIdentidad);  
                    }else{
                        FarmaUtility.showMessage(this,"Tipo de tarjeta no admitida.",null);        
                        inicializarVariables();
                        cont=0;
                        infoTarj="";
                        txtNroTarjeta.setEditable(true);
                        txtNroTarjeta.setText("");          
                    }
                }else{
                    inicializarVariables();
                    cont=0;
                    infoTarj="";
                    txtNroTarjeta.setEditable(true);
                    txtNroTarjeta.setText("");
                } 
            }
        }else{
            chkkeyPressed(e);
        }                    
    }
    
    private boolean buscarInfotarjeta(){
        boolean flag=true;
        ArrayList lista=new ArrayList();
        try{
            DBPtoVenta.obtenerInfoTarjeta(lista,txtNroTarjeta.getText().trim(),
                                          ConstantsCaja.TIPO_ORIGEN_PAGO_POS);
        }catch(SQLException e){
            log.debug("ERROR en buscarInfotarjeta: "+e.getMessage());
            log.error("",e);
        }
        if(lista.size()>0){
            ArrayList reg=(ArrayList)lista.get(0);   
            bin=(String)reg.get(0);
            desc_prod=(String)reg.get(1);
            strCodFormaPago=(String)reg.get(2);
            strDescFormaPago=(String)reg.get(3);
            nroTarjeta=txtNroTarjeta.getText();
        }else{
            bin="";
            desc_prod="";
            strCodFormaPago="";
            strDescFormaPago="";
            nroTarjeta="";
            flag=false;
            FarmaUtility.showMessage(this,"Tipo de tarjeta desconocido",txtNroTarjeta);
        }
        return flag;
    }

    private void txtdni_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtCodVoucher);     
        }else
            chkkeyPressed(e);
    }

    private void txtcodvou_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtLote);
        }else
            chkkeyPressed(e);        
    }
    
    private boolean validaCampos(){
        boolean flag=true;
        if(txtNroTarjeta.getText().trim().equalsIgnoreCase("")){ 
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Nro. de Tarjeta",txtNroTarjeta);            
        }else if(Double.parseDouble(txtNroTarjeta.getText().trim())==0){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Nro. de Tarjeta valido",txtNroTarjeta);
            txtNroTarjeta.setText("");
        }else if(!(txtNroTarjeta.getText().trim()).equalsIgnoreCase(nroTarjeta)){
            flag=false;
            FarmaUtility.showMessage(this,"No debio cambiar el Nro. de Tarjeta",txtNroTarjeta);
            inicializarVariables();
        }else if(bin.equalsIgnoreCase("")){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Nro. de Tarjeta valido",txtNroTarjeta);
            txtNroTarjeta.setText("");
        }else if(txtDocIdentidad.getText().trim().length() == 0 ){
            flag=false;
            FarmaUtility.showMessage(this,"Si va a ingresar DNI debe ser valido",txtDocIdentidad);
            txtDocIdentidad.setText("");
        }else if(txtCodVoucher.getText().trim().equalsIgnoreCase("")){ 
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Cod. Voucher",txtCodVoucher);     
            txtCodVoucher.setText("");
        }else if(Double.parseDouble(txtCodVoucher.getText().trim())==0){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Cod. Voucher valido",txtCodVoucher);            
            txtCodVoucher.setText("");
        }else if(txtLote.getText().trim().equalsIgnoreCase("")){ 
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Lote",txtLote);   
            txtLote.setText("");
        }else if(Double.parseDouble(txtLote.getText().trim())==0){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Lote valido",txtLote);
            txtLote.setText("");
        }
        return flag;
    }

    private void lblNroTarjeta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroTarjeta);
    }

    private void lblDocIdentidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDocIdentidad);
    }

    private void lblLote_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtLote);
    }

    private void txtLote_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            txtLote.setText(FarmaUtility.completeWithSymbol(txtLote.getText(),3,"0","I"));
            FarmaUtility.moveFocus(txtDocIdentidad);
        }else
            chkkeyPressed(e);        
    }
    
    private void lblCodVoucher_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodVoucher);
    }
    
    private boolean hablitarTipoTarjeta(){
        boolean contFormTrj = arrayCodFormaPago.contains(strCodFormaPago);
        return contFormTrj;
    }
    
    public void setArrayFormPago(ArrayList codFormaPago) {
        this.arrayCodFormaPago = codFormaPago;
    }
}
