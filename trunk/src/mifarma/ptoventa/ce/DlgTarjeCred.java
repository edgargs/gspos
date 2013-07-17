package mifarma.ptoventa.ce;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

//import com.jgoodies.forms.layout.FormLayout;

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
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.BeanDetaPago;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityNewCobro;
import mifarma.ptoventa.ce.reference.VariablesNewCobro;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;

/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgTarjeCred.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      29.01.2010   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class DlgTarjeCred extends JDialog {
    //Variables para el pago con tarjeta
    private String bin="";
    private String desc_prod="";
    private String codfp="";
    private String descfp="";
    private String nroTarjeta="";
    
    private String infoTarj="";
    private int cont=0;
    private String mestarj="";
    private String aniotarj="";
    
    private String monedita=""; //ASOSA, 11.06.2010
    
    private Frame myParentFrame;
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanel pnlInfo = new JPanel();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelOrange jLabelOrange5 = new JLabelOrange();
    private JTextFieldSanSerif txtnrotarj = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtcodvou = new JTextFieldSanSerif();
    private JLabelOrange jLabelOrange6 = new JLabelOrange();
    private JLabelOrange lbltip = new JLabelOrange();
    private JLabelOrange lblmon = new JLabelOrange();
    private JLabelOrange lblmoneda = new JLabelOrange();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JButtonLabel jButtonLabel3 = new JButtonLabel();
    private JLabelOrange lblmensaje = new JLabelOrange();

    public DlgTarjeCred() {
        this(null, "", false);
    }

    public DlgTarjeCred(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame=parent;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DlgTarjeCred(Frame parent, String title, boolean modal,String monedita) { //ASOSA, 11.06.2010
        super(parent, title, modal);
        this.myParentFrame=parent;
        this.monedita=monedita;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(416, 248));
        this.getContentPane().setLayout( null );
        this.setTitle("Pago con Tarjeta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        pnlFondo.setBounds(new Rectangle(0, 0, 415, 225));
        pnlFondo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlFondo_keyPressed(e);
                    }
                });
        pnlInfo.setBounds(new Rectangle(10, 25, 390, 185));
        pnlInfo.setBackground(Color.white);
        pnlInfo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlInfo.setLayout(null);
        pnlInfo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlInfo_keyPressed(e);
                    }
                });
        pnlTitle.setBounds(new Rectangle(10, 5, 390, 20));
        pnlTitle.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlTitle_keyPressed(e);
                    }
                });
        jLabelWhite1.setText("Pago con tarjeta");
        jLabelWhite1.setBounds(new Rectangle(5, 5, 100, 15));
        jLabelWhite1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jLabelWhite1_keyPressed(e);
                    }
                });
        jLabelOrange5.setText("Monto");
        jLabelOrange5.setBounds(new Rectangle(15, 140, 80, 20));
        jLabelOrange5.setFont(new Font("SansSerif", 1, 18));
        jLabelOrange5.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jLabelOrange5_keyPressed(e);
                    }
                });
        txtnrotarj.setBounds(new Rectangle(115, 10, 135, 20));
        txtnrotarj.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtnrotarj_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtnrotarj_keyPressed(e);
                    }
                });
        
        txtnrotarj.setLengthText(20);
        txtnrotarj.setDocument(new FarmaLengthText(20));       
        txtcodvou.setBounds(new Rectangle(115, 70, 135, 20));
        txtcodvou.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtcodvou_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtcodvou_keyPressed(e);
                    }
                });
        txtcodvou.setDocument(new FarmaLengthText(20));
        jLabelOrange6.setText("Tipo Tarjeta");
        jLabelOrange6.setBounds(new Rectangle(15, 40, 80, 15));
        jLabelOrange6.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jLabelOrange6_keyPressed(e);
                    }
                });
        lbltip.setText("[TIPO]");
        lbltip.setBounds(new Rectangle(115, 35, 265, 15));
        lbltip.setForeground(new Color(43, 141, 39));
        lbltip.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lbltip_keyPressed(e);
                    }
                });
        lblmon.setText("[MONTO]");
        lblmon.setBounds(new Rectangle(190, 135, 90, 20));
        lblmon.setForeground(new Color(49, 141, 43));
        lblmon.setFont(new Font("SansSerif", 1, 18));
        
        lblmoneda.setText("Monto S/.");
        lblmoneda.setBounds(new Rectangle(100, 135, 90, 20));
        lblmoneda.setForeground(new Color(49, 141, 43));
        lblmoneda.setFont(new Font("SansSerif", 1, 18));
        
        
        lblmon.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lblmon_keyPressed(e);
                    }
                });
        jButtonLabel1.setText("Nro. Tarjeta");
        jButtonLabel1.setBounds(new Rectangle(15, 15, 75, 15));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('n');
        jButtonLabel1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jButtonLabel1_keyPressed(e);
                    }
                });
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        jButtonLabel3.setText("Codigo Voucher");
        jButtonLabel3.setBounds(new Rectangle(15, 70, 95, 15));
        jButtonLabel3.setForeground(new Color(255, 130, 14));
        jButtonLabel3.setMnemonic('c');
        jButtonLabel3.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jButtonLabel3_keyPressed(e);
                    }
                });
        jButtonLabel3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel3_actionPerformed(e);
                    }
                });
        lblmensaje.setText("[MENSAJE]");
        lblmensaje.setBounds(new Rectangle(255, 15, 125, 15));
        lblmensaje.setForeground(new Color(49, 141, 43));
        pnlTitle.add(jLabelWhite1, null);
        pnlFondo.add(pnlTitle, null);
        pnlInfo.add(lblmensaje, null);
        pnlInfo.add(jButtonLabel3, null);
        pnlInfo.add(jButtonLabel1, null);
        pnlInfo.add(lblmon, null);
        pnlInfo.add(lblmoneda, null);
        pnlInfo.add(lbltip, null);
        pnlInfo.add(jLabelOrange6, null);
        pnlInfo.add(txtcodvou, null);
        pnlInfo.add(txtnrotarj, null);
        pnlInfo.add(jLabelOrange5, null);
        pnlFondo.add(pnlInfo, null);
        this.getContentPane().add(pnlFondo, null);
    }
    
    private void inicializarVariables(){
        VariablesNewCobro.ind=0;
        bin="";
        desc_prod="";
        codfp="";
        descfp="";
        nroTarjeta="";
        txtnrotarj.setText("");
        //txtdni.setText("");
        txtcodvou.setText("");
        //txtlote.setText("");
        lbltip.setText("");
        infoTarj="";
        aniotarj="";
        mestarj="";
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtnrotarj);
        lblmon.setText(FarmaUtility.formatNumber(VariablesNewCobro.montoTarj));
        inicializarVariables();
        
        lblmensaje.setText("");
    }
    
    private void chkkeyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            VariablesNewCobro.ind=0;
            cerrarVentana(false);
        }
    }

    private void txtnrotarj_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirDigitos(txtnrotarj,e);
    }

    
    private void txtcodvou_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtcodvou,e);
    }

    private void jLabelOrange6_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelOrange5_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void txtnrotarj_keyPressed(KeyEvent e) {
        infoTarj=infoTarj+e.getKeyChar();
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            lblmensaje.setText("");
            if(infoTarj.indexOf("%")>=0 && cont==0){
                cont=1;
                infoTarj=infoTarj.substring(infoTarj.indexOf("%"),infoTarj.length()-1);
                txtnrotarj.setText(infoTarj.substring(2,18));
                //JOptionPane.showMessageDialog(null,"hola: "+cont);
                //if(validarCamposSimilares(txtnrotarj.getText(),txtnrotarj,txtdni,"Nro. de Tarjeta")){
                if (UtilityCajaElectronica.validarLongitudTarj(txtnrotarj.getText().trim())) {
                    if(buscarInfotarjeta()){
                        lbltip.setText(desc_prod+"/"+descfp);
                        int ini=infoTarj.indexOf("&",infoTarj.indexOf("&")+1);
                        aniotarj=infoTarj.substring(ini+1,ini+3);
                        mestarj=infoTarj.substring(ini+3,ini+5);
                    }else{
                        inicializarVariables();
                        cont=0;
                        infoTarj="";
                        txtnrotarj.setText("");
                        FarmaUtility.moveFocus(txtnrotarj);
                        lblmensaje.setText("Tarjeta desconocida");
                    }
                }else
                {
                    FarmaUtility.showMessage(this,"La cantidad de digitos del numero de tarjeta es incorrecto",null);
                } 
                //}else{
                    cont=0;
                    infoTarj="";
                    txtnrotarj.setText("");
                    FarmaUtility.moveFocus(txtnrotarj);
                //}
            }else if(!(infoTarj.indexOf("%")>=0)){
                    
                if (UtilityCajaElectronica.validarLongitudTarj(txtnrotarj.getText().trim())) {
                    if(buscarInfotarjeta()){
                        lbltip.setText(desc_prod+"/"+descfp);
                        FarmaUtility.moveFocus(txtcodvou);
                        //validarCamposSimilares(txtnrotarj.getText(),txtnrotarj,txtdni,"Nro. de Tarjeta");
                    }else{
                        inicializarVariables();
                    }
                    }else{
                        FarmaUtility.showMessage(this,"La cantidad de digitos del numero de tarjeta es incorrecto",null);
                    }
                
                cont=0;
                infoTarj="";
            }
        }else
            chkkeyPressed(e);
    }
    
    private boolean buscarInfotarjeta(){
        boolean flag=true;
        ArrayList lista=new ArrayList();
        try{
            DBCaja.obtenerInfoTarjeta(lista,txtnrotarj.getText().trim());
        }catch(SQLException e){
            System.out.println("ERROR en buscarInfotarjeta: "+e.getMessage());
            e.printStackTrace();
        }
        if(lista.size()>0){
            System.out.println("HOLAHOLAHOLAHOLAHOLAHOLA");
            ArrayList reg=(ArrayList)lista.get(0);   
            bin=(String)reg.get(0);
            desc_prod=(String)reg.get(1);
            codfp=(String)reg.get(2);
            descfp=(String)reg.get(3);
            nroTarjeta=txtnrotarj.getText();
            System.out.println("bin: "+bin);
            System.out.println("desc_prod: "+desc_prod);
            System.out.println("codfp: "+codfp);
            System.out.println("descfp: "+descfp);
        }else{
            bin="";
            desc_prod="";
            codfp="";
            descfp="";
            nroTarjeta="";
            flag=false;
            FarmaUtility.showMessage(this,"Tipo de tarjeta desconocido",txtnrotarj);
        }
        return flag;
    }

    private void lbltip_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

   

    private void txtcodvou_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(validarCamposSimilares(txtcodvou.getText(),txtcodvou,null,"Cod. Voucher")){
                if(validaCampos()){
                    System.out.println("3333333333333333333");
                    String descMon="SOLES"; //INI ASOSA, 11.06.2010
                    String codMon="01";
                    if(monedita.equalsIgnoreCase("D")){
                        descMon="DOLARES";
                        codMon="02";
                    }               
                    agregarDetalle(codfp,
                                   descfp,
                                   descMon,
                                   VariablesNewCobro.montoTarj_02,
                                   VariablesNewCobro.montoTarj,
                                   codMon,
                                   txtnrotarj.getText().trim(),
                                   " ",
                                   txtcodvou.getText().trim());//FIN ASOSA, 11.06.2010
                }
            }
        }else
            chkkeyPressed(e);
    }
    
    private boolean validarCamposSimilares(String contenido,Object obj01,Object obj,String msg){
        boolean flag=true;
        if(!contenido.trim().equalsIgnoreCase("")){
            if(Double.parseDouble(contenido.trim())>0){
                if(FarmaUtility.isDouble(contenido)){
                    FarmaUtility.moveFocus(obj);
                }else{
                    flag=false;
                    FarmaUtility.showMessage(this,"Ingrese "+msg+" valido",obj01);
                }
            }else{
                flag=false;
                FarmaUtility.showMessage(this,"Ingrese "+msg+" valido",obj01);
            }
        }else{
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese "+msg,obj01);
        }
        return flag;
    }

   /* private void txtlote_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!txtlote.getText().trim().equalsIgnoreCase("")){
                if(Double.parseDouble(txtlote.getText().trim())!=0){                    
                    if(validaCampos()){
                        System.out.println("3333333333333333333");
                        agregarDetalle(codfp,
                                       descfp,
                                       "SOLES",
                                       VariablesNewCobro.montoTarj,
                                       VariablesNewCobro.montoTarj,
                                       "01",
                                       txtnrotarj.getText().trim(),
                                       txtdni.getText().trim(),
                                       txtcodvou.getText().trim());
                    }
                }else{
                    FarmaUtility.showMessage(this,"Ingrese Lote valido",txtlote);
                    txtlote.setText("");
                }
            }else{
                FarmaUtility.showMessage(this,"Ingrese Lote",txtlote);
            }
        }else
            chkkeyPressed(e);
    }*/
    
    private void agregarDetalle(String codFP, 
                                String descFP,
                                String moneda,
                                double monto,
                                double total,
                                String codmoneda,
                                String nrotarj,
                                String dnix,
                                String codvou){
        UtilityNewCobro.reemplazarObjeto(codFP);
        BeanDetaPago objDPB=new BeanDetaPago();
        objDPB.setCod_fp(codFP);
        objDPB.setDesc_fp(descFP);
        objDPB.setCant("0");
        objDPB.setMoneda(moneda);
        objDPB.setMonto(FarmaUtility.formatNumber(monto));
        objDPB.setTotal(FarmaUtility.formatNumber(total));
        objDPB.setCod_moneda(codmoneda);
        objDPB.setComodin("0.00");
        objDPB.setNrotarj(nrotarj);
        objDPB.setFecventarj("");
        objDPB.setNomclitarj("");
        objDPB.setCodconv("");
        objDPB.setDnix(dnix);
        objDPB.setCodvou(codvou);
        objDPB.setLote("");
        VariablesNewCobro.listDeta.add(objDPB);
        VariablesNewCobro.montoIngreso=Double.parseDouble(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(""+(VariablesNewCobro.montoSoles+VariablesNewCobro.montoDolares+VariablesNewCobro.montoTarj))));
        VariablesNewCobro.saldo=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoIngreso));
        VariablesNewCobro.codfpHOLA=codFP;
        if(VariablesNewCobro.montoTotal<=VariablesNewCobro.montoIngreso){
            VariablesNewCobro.flagPedCubierto=true;
            VariablesNewCobro.vuelto=FarmaUtility.formatNumber(VariablesNewCobro.montoIngreso-VariablesNewCobro.montoTotal);
        }else{
            VariablesNewCobro.flagPedCubierto=false;
            VariablesNewCobro.vuelto="0.00";
        }
        VariablesNewCobro.ind=1;
        cerrarVentana(true);
    }
    
    private boolean validaCampos(){
        boolean flag=true;
        if(txtnrotarj.getText().trim().equalsIgnoreCase("")){ 
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Nro. de Tarjeta",txtnrotarj);            
        }else if(Double.parseDouble(txtnrotarj.getText().trim())==0){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Nro. de Tarjeta valido",txtnrotarj);
            txtnrotarj.setText("");
        }else if(!(txtnrotarj.getText().trim()).equalsIgnoreCase(nroTarjeta)){
            flag=false;
            FarmaUtility.showMessage(this,"No debio cambiar el Nro. de Tarjeta",txtnrotarj);
            inicializarVariables();
        }else if(bin.equalsIgnoreCase("")){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Nro. de Tarjeta valido",txtnrotarj);
            txtnrotarj.setText("");
        }else if(txtcodvou.getText().trim().equalsIgnoreCase("")){ 
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Cod. Voucher",txtcodvou);            
        }else if(Double.parseDouble(txtcodvou.getText().trim())==0){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Cod. Voucher valido",txtcodvou);            
            txtcodvou.setText("");
        /*}else if(txtlote.getText().trim().equalsIgnoreCase("")){ 
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Lote",txtlote);            
        }else if(Double.parseDouble(txtlote.getText().trim())==0){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Lote valido",txtlote);
            txtlote.setText("");*/
        }
        return flag;
    }
    
    private void lblmon_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlInfo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlTitle_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelWhite1_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel1_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel2_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel3_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtnrotarj);
    }
    
    private void jButtonLabel3_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcodvou);
    }
   
}
