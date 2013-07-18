package mifarma.ptoventa.ce;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JPanelHeader;
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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;

import mifarma.common.*;

/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoMonto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 *        26.02.2010   Creación<br>
 * <br>
 * @AUTHOR JORGE CORTEZ ALVAREZ<br>
 * @VERSION 1.0<br>
 * 
 */

public class DlgIngresoMonto extends JDialog 
{
  Frame myParentFrame;
    double TotalAcumulado=0;
    private FarmaTableModel tableModelFormaPago;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel9 = new JLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTextFieldSanSerif txtSoles = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDolares = new JTextFieldSanSerif();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelOrange lblSaldo = new JLabelOrange();
    private JLabelOrange jLabelOrange3 = new JLabelOrange();
    private JLabelOrange lblVuelto = new JLabelOrange();
    private JButtonLabel lblSoles_t = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblFormaPago = new JTable();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JButtonLabel jButtonLabel2 = new JButtonLabel();
    private JButtonLabel lblTotalPago = new JButtonLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JButtonLabel lblTipCambio = new JButtonLabel();
    private JButtonLabel lblFecha = new JButtonLabel();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JButtonLabel jButtonLabel3 = new JButtonLabel();

    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */
    public DlgIngresoMonto()
  {
    this(null, "", false);
  }

  public DlgIngresoMonto(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(426, 334));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingreso Monto");
    this.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        this_keyPressed(e);
                    }
      });
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(382, 194));
    jContentPane.setBackground(Color.white);
    jPanel2.setBounds(new Rectangle(5, 35, 410, 240));
    jPanel2.setBackground(Color.white);
    jPanel2.setBorder(BorderFactory.createTitledBorder(""));
    jPanel2.setLayout(null);
        jLabel4.setBounds(new Rectangle(230, 65, 95, 15));
    jLabel4.setFont(new Font("SansSerif", 1, 12));
    jLabel4.setForeground(new Color(255, 130, 14));
        jLabel4.setText("TIpo de Cambio :");
        jLabel3.setText("Fecha :");
    jLabel3.setBounds(new Rectangle(10, 10, 55, 15));
    jLabel3.setFont(new Font("SansSerif", 1, 12));
    jLabel3.setForeground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(5, 5, 410, 30));
    jPanel1.setBackground(new Color(255, 130, 14));
    jPanel1.setLayout(null);
    jLabel9.setText("Cambio de Efectivo :");
    jLabel9.setBounds(new Rectangle(10, 5, 160, 20));
    jLabel9.setFont(new Font("SansSerif", 1, 11));
    jLabel9.setForeground(Color.white);
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(330, 280, 85, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(225, 280, 100, 20));
        txtSoles.setBounds(new Rectangle(60, 35, 105, 20));
        txtSoles.setLengthText(9);
        txtSoles.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtSoles_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtSoles_keyPressed(e);
                    }
                });
        txtDolares.setBounds(new Rectangle(60, 60, 105, 20));
        txtDolares.setLengthText(9);
        txtDolares.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtDolares_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtDolares_keyPressed(e);
                    }
                });
        jLabelOrange1.setText("Monto Pago Total : S/.");
        jLabelOrange1.setBounds(new Rectangle(10, 210, 120, 20));
        lblSaldo.setText("0.00");
        lblSaldo.setBounds(new Rectangle(135, 210, 60, 25));
        lblSaldo.setForeground(new Color(43, 141, 39));
        jLabelOrange3.setText("Vuelto : S/.");
        jLabelOrange3.setBounds(new Rectangle(265, 210, 65, 20));
        lblVuelto.setText("0.00");
        lblVuelto.setBounds(new Rectangle(335, 210, 60, 20));
        lblVuelto.setForeground(new Color(43, 141, 39));
        lblSoles_t.setText("Soles :");
        lblSoles_t.setBounds(new Rectangle(10, 35, 55, 20));
        lblSoles_t.setForeground(new Color(254, 130, 14));
        lblSoles_t.setMnemonic('S');
        lblSoles_t.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblSoles_t_actionPerformed(e);
                    }
                });
        jScrollPane1.setBounds(new Rectangle(5, 125, 400, 85));
        tblFormaPago.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblFormaPago_keyPressed(e);
                    }
                });
        jButtonLabel1.setText("Dolares :");
        jButtonLabel1.setBounds(new Rectangle(10, 60, 55, 20));
        jButtonLabel1.setForeground(new Color(254, 130, 14));
        jButtonLabel1.setMnemonic('D');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        jButtonLabel2.setText("Total Pago : S/.");
        jButtonLabel2.setBounds(new Rectangle(230, 25, 80, 20));
        jButtonLabel2.setBackground(new Color(255, 130, 14));
        jButtonLabel2.setForeground(new Color(255, 130, 14));
        lblTotalPago.setText("0.00");
        lblTotalPago.setBounds(new Rectangle(315, 25, 75, 20));
        lblTotalPago.setForeground(new Color(43, 141, 39));
        lblTotalPago.setFont(new Font("SansSerif", 1, 12));
        jLabelFunction1.setBounds(new Rectangle(5, 280, 145, 20));
        jLabelFunction1.setText("[F4] Corregir Forma Pago");
        lblTipCambio.setText("0.0");
        lblTipCambio.setBounds(new Rectangle(330, 60, 70, 20));
        lblTipCambio.setForeground(new Color(43, 141, 39));
        lblFecha.setText("01/03/2010 00:00:00");
        lblFecha.setBounds(new Rectangle(60, 5, 110, 25));
        lblFecha.setForeground(new Color(43, 141, 39));
        jPanelHeader1.setBounds(new Rectangle(5, 100, 400, 25));
        jButtonLabel3.setText("Detalle de Pago :");
        jButtonLabel3.setBounds(new Rectangle(5, 5, 135, 15));
        jButtonLabel3.setMnemonic('P');
        jButtonLabel3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel3_actionPerformed(e);
                    }
                });
        jPanelHeader1.add(jButtonLabel3, null);
        jPanel2.add(jPanelHeader1, null);
        jPanel2.add(lblFecha, null);
        jPanel2.add(lblTipCambio, null);
        jPanel2.add(lblTotalPago, null);
        jPanel2.add(jButtonLabel2, null);
        jPanel2.add(jButtonLabel1, null);
        jScrollPane1.getViewport().add(tblFormaPago, null);
        jPanel2.add(jScrollPane1, null);
        jPanel2.add(lblSoles_t, null);
        jPanel2.add(lblVuelto, null);
        jPanel2.add(lblSaldo, null);
        jPanel2.add(jLabelOrange1, null);
        jPanel2.add(txtDolares, null);
        jPanel2.add(txtSoles, null);
        jPanel2.add(jLabel4, null);
        jPanel2.add(jLabel3, null);
        jPanel2.add(jLabelOrange3, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(jPanel2, null);
        jPanel1.add(jLabel9, null);
        jContentPane.add(jPanel1, null);
        //this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
  private void initialize(){
      
      tblFormaPago.getTableHeader().setReorderingAllowed(false);
      tblFormaPago.getTableHeader().setResizingAllowed(false);
      tableModelFormaPago = new FarmaTableModel(ConstantsCajaElectronica.columnsListaFormasPago2,ConstantsCajaElectronica.defaultValuesListaFormasPago2,0);
      FarmaUtility.initSimpleList(tblFormaPago,tableModelFormaPago,ConstantsCajaElectronica.columnsListaFormasPago2);
  };
  
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtSoles);
      obtieneTipoCambio();
      txtSoles.setText("0.00");
      txtDolares.setText("0.00"); 
      lblVuelto.setText("0.00");
      lblFecha.setText(VariablesCajaElectronica.vFechaPed);
      lblTotalPago.setText(VariablesCajaElectronica.vMontPago);
      //lblTipCambio.setText(VariablesCajaElectronica.vTipCambio);
  }

  private void this_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
     this.setVisible(false);
    }else if(e.getKeyCode() == KeyEvent.VK_F4){
        txtSoles.setEditable(true);
        txtDolares.setEditable(true);
        tableModelFormaPago.clearTable();
        lblVuelto.setText("0.00");
        FarmaUtility.moveFocus(txtSoles);
    }else if(e.getKeyCode() == KeyEvent.VK_F11){
        if (FarmaUtility.rptaConfirmDialog(this, "Está seguro de cambiar la forma de pago?"))
        {
        if(VariablesCajaElectronica.vIndTotalCubierto){
           ProcesarFormaPago();//crea backup y nueva forma de pago;
            cerrarVentana(true);
        }else
        FarmaUtility.showMessage(this, "El monto aún no esta completo.", tblFormaPago); 
        }
    }
  }
  
  
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }
    
    private  void cargarFecha(){
      try{
        String FechaInicio=FarmaSearch.getFechaHoraBD(1);
        lblFecha.setText(FechaInicio);
      }catch(SQLException sql){
        sql.printStackTrace();
      }
    }
  
    private boolean datosValidados()
    {   
    boolean retorno = true;
       if(!validaSoles()&& txtSoles.getText().trim().length()>0){
          retorno=false;
        }if(!validaDolares()&&txtDolares.getText().trim().length()>0){
          retorno=false;
        }
      return retorno;
    }
    
    private boolean validaSoles()
    {
      boolean valor = true;
        String monto = txtSoles.getText().trim();
        if(monto.equalsIgnoreCase("") || monto.length() <= 0)
        {
          FarmaUtility.showMessage(this, "Ingrese monto a pagar.", txtSoles);
          return false;
        }
        if(!FarmaUtility.isDouble(monto))
        {
          FarmaUtility.showMessage(this, "Ingrese monto soles vâlido.", txtSoles);
          return false;
        }
        if(FarmaUtility.getDecimalNumber(monto) <= 0)
        {
          FarmaUtility.showMessage(this, "Ingrese monto soles mayor a 0.", txtSoles);
          return false;
        }   
        if(!FarmaUtility.isDouble(monto))
          {
            FarmaUtility.showMessage(this, "Ingrese monto soles correcto.", txtSoles);
            return false;
          } 
      return valor;
    }
    
    private boolean validaDolares()
    {
        boolean valor = true;
          String monto = txtDolares.getText().trim();
          if(monto.equalsIgnoreCase("") || monto.length() <= 0)
          {
            FarmaUtility.showMessage(this, "Ingrese monto a pagar.", txtDolares);
            return false;
          }
          if(!FarmaUtility.isDouble(monto))
          {
            FarmaUtility.showMessage(this, "Ingrese monto dolares vâlido.", txtDolares);
            return false;
          }
          if(FarmaUtility.getDecimalNumber(monto) <= 0)
          {
            FarmaUtility.showMessage(this, "Ingrese monto dolares mayor a 0.", txtDolares);
            return false;
          }   
          if(!FarmaUtility.isDouble(monto))
            {
              FarmaUtility.showMessage(this, "Ingrese monto dolares correcto.", txtDolares);
              return false;
            } 
        return valor;
    }


    private void txtSoles_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtSoles,e);
    }

    private void txtDolares_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtDolares,e);
    }

    private void txtSoles_keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(txtSoles.getText().length()>0){
                
                if(!FarmaUtility.isDouble((String.valueOf((txtSoles.getText().trim())))))
                    FarmaUtility.showMessage(this, "Ingrese un monto soles correcto.", txtSoles);  
                else {
                txtSoles.setText(String.valueOf(FarmaUtility.getDecimalNumber(txtSoles.getText().trim()))); 
                String codFormaPago="00001";
                String Descrip="EFECTIVO SOLES";
                String Monto=FarmaUtility.getDecimalNumber(txtSoles.getText().trim())+"";
                String Total=FarmaUtility.getDecimalNumber(txtSoles.getText().trim())+"";
                operaListaDetallePago(codFormaPago,Descrip,Monto,Total);
                verificaMontoPagadoPedido();
                FarmaUtility.moveFocus(txtDolares);
                txtSoles.setText("0.00");
                txtSoles.setEditable(false);
                System.out.println("Monto cubierto:::"+ VariablesCajaElectronica.vIndTotalCubierto);
                }
               
            }
        }
        else
          chkKeyPressed(e);
    }
    
    private void operaListaDetallePago(String Cod,String Desc,String Monto,String Total)
    {
      ArrayList myArray = new ArrayList();
      myArray.add(Cod);
      myArray.add(Desc);
      myArray.add(Monto);
      myArray.add(Total);
      tableModelFormaPago.data.add(myArray);
      tableModelFormaPago.fireTableDataChanged();
    }
    
    private void obtieneTipoCambio()
    {
        double vTipCambio=0;
        try
      {
         vTipCambio = DBCajaElectronica.getTipoCambio(VariablesCajaElectronica.vFechaPed.substring(0,10).trim());
        System.out.println("fecha->"+VariablesCajaElectronica.vFechaPed.substring(0,10).trim());
          System.out.println("vTipCambio->"+vTipCambio);
         lblTipCambio.setText(vTipCambio+"");
      } catch(SQLException sql)
      {
        sql.printStackTrace();
        FarmaUtility.showMessage(this, "Error al obtener Tipo de Cambio del Dia . \n " + sql.getMessage(), null);
      }
    }

    private void txtDolares_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(txtDolares.getText().length()>0){
                
                if(!FarmaUtility.isDouble((String.valueOf((txtDolares.getText().trim())))))
                    FarmaUtility.showMessage(this, "Ingrese un monto dolar correcto.", txtDolares);  
                else {
                txtDolares.setText(String.valueOf(FarmaUtility.getDecimalNumber(txtDolares.getText().trim()))); 

                String codFormaPago="00002";
                String Descrip="EFECTIVO DOLARES";
                String Monto=FarmaUtility.getDecimalNumber(txtDolares.getText().trim())+"";
                String Total= FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(Monto) * FarmaUtility.getDecimalNumber(lblTipCambio.getText().trim()));
                operaListaDetallePago(codFormaPago,Descrip,Monto,Total);
                verificaMontoPagadoPedido();
                FarmaUtility.moveFocus(tblFormaPago);
                txtDolares.setText("0.00");
                txtDolares.setEditable(false);
                    }
            }
        }
        else
          chkKeyPressed(e);
    }

    private void lblSoles_t_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSoles);
    }
    
    private void verificaMontoPagadoPedido(){
        double montoTotal = 0;
        double montoFormaPago = 0;
        
        for(int i=0; i<tblFormaPago.getRowCount(); i++)
        {
          montoFormaPago = FarmaUtility.getDecimalNumber(((String)tblFormaPago.getValueAt(i,3)).trim());
          montoTotal = montoTotal + montoFormaPago;
        }
        if( FarmaUtility.getDecimalNumber(VariablesCajaElectronica.vMontPago) > montoTotal ){
          System.out.println("No Cubierto");
          VariablesCajaElectronica.vIndTotalCubierto = false;
          VariablesCajaElectronica.vSaldoPago = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCajaElectronica.vMontPago) - montoTotal);
          VariablesCajaElectronica.vValVuelto = "0.00";
        } else{
          System.out.println("Cubierto");
          VariablesCajaElectronica.vIndTotalCubierto = true;
          VariablesCajaElectronica.vSaldoPago = "0.00";
          VariablesCajaElectronica.vValVuelto = FarmaUtility.formatNumber(montoTotal - FarmaUtility.getDecimalNumber(VariablesCajaElectronica.vMontPago));
        }
        System.out.println("VariablesCajaElectronica.vSaldoPago :"+VariablesCajaElectronica.vSaldoPago);
        System.out.println("VariablesCajaElectronica.vValVuelto :"+VariablesCajaElectronica.vValVuelto);
        lblSaldo.setText(VariablesCajaElectronica.vSaldoPago);
        lblVuelto.setText(VariablesCajaElectronica.vValVuelto);
        //montoTotal = FarmaUtility.getDecimalNumber(VariablesCajaElectronica.vMontPago.trim());*/
    }

    private void tblFormaPago_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jButtonLabel3_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblFormaPago);
    }
    
    
    private void ProcesarFormaPago(){
        String cod="",monto="",total="",tipMoneda="",vuelto="";
        
        grabarFormaPagoPedidoBackup(VariablesCajaElectronica.vCodFPago,VariablesCajaElectronica.vNumPedVta);
        System.out.println("grabarFormaPagoPedidoBackup:"+VariablesCajaElectronica.vNumPedVta);
        for(int i=0; i<tblFormaPago.getRowCount(); i++)
        {
            cod=(String)tblFormaPago.getValueAt(i,0);
            monto=FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber((String)tblFormaPago.getValueAt(i,2)),3);
            total=FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber((String)tblFormaPago.getValueAt(i,3)),3);
            
                if(cod.trim().equalsIgnoreCase("00001"))
                    tipMoneda="01";
                else if(cod.trim().equalsIgnoreCase("00002"))
                    tipMoneda="02";
            
            vuelto=lblVuelto.getText().trim();
            System.out.println("FOR I: "+i);
            System.out.println("ultimo: "+tblFormaPago.getRowCount());
            if(i!=tblFormaPago.getRowCount()-1)//el vuelto solo debe estar en la ultima forma de pago
                vuelto="0.00";
            
          /*montoFormaPago = FarmaUtility.getDecimalNumber(((String)tblFormaPago.getValueAt(i,3)).trim());
          montoTotal = montoTotal + montoFormaPago;*/
          System.out.println("grabarFormaPagoPedido:"+VariablesCajaElectronica.vNumPedVta+"/"+tipMoneda);
          grabarFormaPagoPedido(cod,
                                VariablesCajaElectronica.vNumPedVta,
                                monto,
                                tipMoneda,
                                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCajaElectronica.vTipCambio),3),
                                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(vuelto),3),
                                total,"","","","","","","0");  
        }
    }
    
    private void grabarFormaPagoPedidoBackup(String pCodFormaPago,
                                             String pNumPedVta)
    {
      try
      {
        DBCajaElectronica.grabaFormaPagoPedidoBackup(pCodFormaPago,pNumPedVta);
          //FarmaUtility.aceptarTransaccion();
      }catch(SQLException e)
      {
        e.printStackTrace();
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(this,"Error al grabar backup de forma de pago del pedido." + e.getMessage(),tblFormaPago);
        //FarmaUtility.moveFocusJTable(tblProductos);
        FarmaUtility.moveFocus(tblFormaPago);
      }
    }
    
    private void grabarFormaPagoPedido(String pCodFormaPago,
                                      String pNumPedVta,
                                      String pImPago,
                                      String pTipMoneda,
                                      String pTipoCambio,
                                      String pVuelto,
                                      String pImTotalPago,
                                      String pNumTarj,
                                      String pFecVencTarj,
                                      String pNomCliTarj,
                                      String pdnix,
                                      String pCodvou,
                                      String pLote,
                                      String pCantCupon)
    {
      try{
        DBCajaElectronica.grabaNuevaFormaPagoPedido(pCodFormaPago,
                                                  pNumPedVta,
                                                  pImPago,
                                                  pTipMoneda,
                                                  pTipoCambio,
                                                  pVuelto,
                                                  pImTotalPago,
                                                  pNumTarj,
                                                  pFecVencTarj,
                                                  pNomCliTarj,
                                                  pdnix,
                                                  pCodvou,
                                                  pLote,
                                                  pCantCupon);
          FarmaUtility.aceptarTransaccion();
      }catch(SQLException e)
      {
        e.printStackTrace();
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(this,"Error al grabar nueva forma de pago del pedido." + e.getMessage(),tblFormaPago);
        //FarmaUtility.moveFocusJTable(tblProductos);
        FarmaUtility.moveFocus(tblFormaPago);
      }
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDolares);
    }
}
