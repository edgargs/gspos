package mifarma.ptoventa.controlingreso;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;


import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

 
 
import java.awt.event.KeyListener;


import mifarma.ptoventa.controlingreso.reference.*;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoTemperatura.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 *   11.02.2009 Creación<br>
 * <br>
 *
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */
public class DlgIngresoTemperatura
  extends JDialog
{
  /* ********************************************************************** */
  /* DECLARACION PROPIEDADES */
  /* ********************************************************************** */

  private JPanel jContentPane = new JPanel();

  Frame myParentFrame;
  private int vEstVigencia;

  private JPanelTitle pnlTitle1 = new JPanelTitle();

  private JTextFieldSanSerif txtRefrig = new JTextFieldSanSerif();

  private JTextFieldSanSerif txtAlmc = new JTextFieldSanSerif();

  private JLabelWhite lblValor_T = new JLabelWhite();

  private JButtonLabel btnFechaInicial = new JButtonLabel();

  private JTextFieldSanSerif txtVTa = new JTextFieldSanSerif();

  private JLabelWhite lblFechaFinal_T = new JLabelWhite();

  private JPanelHeader pnlHeader1 = new JPanelHeader();

  private JLabelWhite lblDescLocal = new JLabelWhite();

  private JLabelWhite lblLaboratorio_T = new JLabelWhite();


    private JLabelWhite lblFecha = new JLabelWhite();

  private JLabelWhite lblCodigo_T = new JLabelWhite();


  private JLabelFunction lblEsc = new JLabelFunction();

  private JLabelFunction lblF11 = new JLabelFunction();

  private BorderLayout borderLayout1 = new BorderLayout();

  private JLabelWhite lblCodPromocion = new JLabelWhite();

    /* ********************************************************************** */
  /* CONSTRUCTORES */
  /* ********************************************************************** */

  public DlgIngresoTemperatura()
  {
    this(null, "", false);
  }

  public DlgIngresoTemperatura(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

  /* ********************************************************************** */
  /* METODO JBINIT */
  /* ********************************************************************** */

  private void jbInit()
    throws Exception
  {
    this.setSize(new Dimension(457, 215));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingreso Temperatura");
    this.setFont(new Font("SansSerif", 0, 11));
    this.addWindowListener(new WindowAdapter()
        {
          public void windowOpened(WindowEvent e)
          {
            this_windowOpened(e);
          }

          public void windowClosing(WindowEvent e)
          {
            this_windowClosing(e);
          }
        });
    jContentPane.setBackground(Color.white);
    jContentPane.setFont(new Font("SansSerif", 0, 11));
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(470, 236));
    pnlTitle1.setBounds(new Rectangle(5, 55, 440, 100));
    pnlTitle1.setBackground(Color.white);
    pnlTitle1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlTitle1.setLayout(null);
    txtRefrig.setLengthText(6);
    txtRefrig.setBounds(new Rectangle(145, 65, 135, 20));
    txtRefrig.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
                        txtValor_keyPressed(e);
                    }

          public void keyTyped(KeyEvent e)
          {
                        txtValor_keyTyped(e);
                    }
        });
    txtAlmc.setLengthText(10);
    txtAlmc.setBounds(new Rectangle(145, 40, 135, 20));
    txtAlmc.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtFechaFinal_keyPressed(e);
          }

                    public void keyTyped(KeyEvent e) {
                        txtAlmc_keyTyped(e);
                    }
                });
    lblValor_T.setText("Refrigerador C° :");
    lblValor_T.setForeground(new Color(255, 130, 14));
    lblValor_T.setBounds(new Rectangle(35, 65, 105, 20));
    btnFechaInicial.setText("Area Venta C° :");

    btnFechaInicial.setForeground(new Color(255, 130, 14));
    btnFechaInicial.setMnemonic('A');
    btnFechaInicial.setBounds(new Rectangle(35, 15, 90, 20));
    btnFechaInicial.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
                        btnFechaInicial_actionPerformed(e);
                    }
        });
    txtVTa.setLengthText(10);
    txtVTa.setBounds(new Rectangle(145, 15, 135, 20));
    txtVTa.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtFechainicial_keyPressed(e);
          }

                    public void keyTyped(KeyEvent e) {
                        txtVTa_keyTyped(e);
                    }
                });
    lblFechaFinal_T.setText("Almacen C° :");
    lblFechaFinal_T.setForeground(new Color(255, 130, 14));
    lblFechaFinal_T.setBounds(new Rectangle(35, 40, 90, 20));
    pnlHeader1.setBounds(new Rectangle(5, 10, 440, 40));
    pnlHeader1.setBackground(Color.white);
    pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 
                                                                  14), 1));
    lblDescLocal.setText("017 - HABICH");
    lblDescLocal.setBounds(new Rectangle(235, 10, 175, 20));
    lblDescLocal.setForeground(new Color(255, 140, 14));
    lblLaboratorio_T.setText("Local :");
    lblLaboratorio_T.setBounds(new Rectangle(170, 10, 55, 20));
    lblLaboratorio_T.setForeground(new Color(255, 140, 14));
        lblFecha.setText("11/02/2009");
    lblFecha.setBounds(new Rectangle(75, 10, 75, 20));
    lblFecha.setForeground(new Color(255, 140, 14));
    lblCodigo_T.setText("Fecha :");
    lblCodigo_T.setBounds(new Rectangle(15, 10, 50, 20));
    lblCodigo_T.setForeground(new Color(255, 140, 14));
    lblEsc.setText("[ESC] Cerrar");
    lblEsc.setBounds(new Rectangle(360, 160, 85, 20));
    lblF11.setText("[F11] Aceptar");
    lblF11.setBounds(new Rectangle(250, 160, 95, 20));
    lblCodPromocion.setText("[CodPromocion]");
    lblCodPromocion.setForeground(new Color(255, 130, 14));
    lblCodPromocion.setRequestFocusEnabled(false);
    lblCodPromocion.setFocusable(false);
    lblCodPromocion.setVisible(false);
    lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        //--Se cambio el tamaño de digitos
    //  12.09.2008  
        pnlTitle1.add(lblCodPromocion);
        pnlTitle1.add(txtRefrig);
        pnlTitle1.add(txtAlmc);
        pnlTitle1.add(lblValor_T);
        pnlTitle1.add(btnFechaInicial);
        pnlTitle1.add(txtVTa);
        pnlTitle1.add(lblFechaFinal_T);
        pnlHeader1.add(lblFecha, null);
        pnlHeader1.add(lblCodigo_T, null);
        pnlHeader1.add(lblDescLocal, null);
        pnlHeader1.add(lblLaboratorio_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

  /* ********************************************************************** */
  /* METODO INITIALIZE */
  /* ********************************************************************** */

  private void initialize()
  {
      lblDescLocal.setText(FarmaVariables.vCodLocal.trim()+" - "+FarmaVariables.vDescCortaLocal.trim());
      cargarFecha();
  }

  /* ********************************************************************** */
  /* METODO DE INICIALIZACION */
  /* ********************************************************************** */

  /* ********************************************************************** */
  /* METODOS DE EVENTOS */
  /* ********************************************************************** */

  private void txtFechaInicio_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void btnFechaInicial_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtVTa);
  }
  
  private void txtVTa_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtVTa, e);
  }
    
  private void txtAlmc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtAlmc, e);
  }

  private void txtFechainicial_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtAlmc);
    }
    chkKeyPressed(e);
  }

  private void txtFechaFinal_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtRefrig);
    }
    chkKeyPressed(e);
  }

  private void txtValor_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtVTa);
    }
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtVTa);
  }


    private void txtFechaFinal_keyReleased(KeyEvent e)
  {
    FarmaUtility.dateComplete(txtAlmc, e);
  }

  private void txtValor_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitosDecimales(txtRefrig, e);
  }

  private void txtFecFacturacion_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtVTa);
    }
    chkKeyPressed(e);
  }

  private void txtFecFacturacion_keyReleased(KeyEvent e)
  {
    //FarmaUtility.dateComplete(txtFecFacturacion,e);
  }
  
  private void txtValorProv_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
     // FarmaUtility.moveFocus(txtFecFacturacion);
    }
    chkKeyPressed(e);
  }

  private void txtValorProv_keyTyped(KeyEvent e)
  {
  FarmaUtility.admitirDigitosDecimales(txtRefrig, e);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }

  /* ********************************************************************** */
  /* METODOS AUXILIARES */
  /* ********************************************************************** */

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_F11)
    {
      if (datosValidados())
      {
        if (FarmaUtility.rptaConfirmDialog(this, 
                                           "Está seguro que desea grabar ?"))
        {
          try
          {
            if(VariablesControlIngreso.vTipoAccionTemp== FarmaConstants.ACCION_INSERT)
                insertarTemp();
                
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,"La operación se realizó correctamente",txtRefrig);
            cerrarVentana(true);
          }
          catch (SQLException ex)
          { 
          if(ex.getErrorCode() == 20001){
                       FarmaUtility.showMessage(this,"Usted no cuenta con el rol adecuado.",txtRefrig);  
          }else if(ex.getErrorCode() == 20002){
                       FarmaUtility.showMessage(this,"Usted ya registro 2 ingresos para el dia de hoy.",txtRefrig);  
          }else if(ex.getErrorCode() == 20003){
                       FarmaUtility.showMessage(this,"Usted no esta dentro del rango de horas permitido.",txtRefrig);  
          }else{
            FarmaUtility.liberarTransaccion();
            ex.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error en la transacción:\n " + ex.getMessage(), txtRefrig);
           }
          }
        }
      }
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  /* ********************************************************************** */
  /* METODOS DE LOGICA DE NEGOCIO */
  /* *********************************************************************** */


  private boolean datosValidados()
  {   
  boolean retorno = true;
       if (!FarmaUtility.isDouble(txtVTa.getText()))
      {
        FarmaUtility.showMessage(this, "Ingrese temperatura del área correctamente", txtVTa);
        retorno = false;
      }else if(!FarmaUtility.isDouble(txtAlmc.getText())){
          FarmaUtility.showMessage(this, "Ingrese temperatura de almacen correctamente", txtAlmc);
          retorno = false;
      }else if(!FarmaUtility.isDouble(txtRefrig.getText())){
          FarmaUtility.showMessage(this, "Ingrese temperatura del refrigerador correctamente", txtRefrig);
          retorno = false;
      }
    
       /*if(!validaGrados())
        retorno=false;*/
        
    return retorno;
  }

    
  private  boolean  validaGrados(){
    
    boolean valor=true;
      if(!validaMontoIngresado(txtVTa,"1"))
          valor=false;
          
      if(!validaMontoIngresado(txtAlmc,"3"))
          valor=false;
          
      if(!validaMontoIngresado(txtRefrig,"2"))
          valor=false;
          
      return  valor;
  }


  private void insertarTemp()
    throws SQLException
  {
    DBControlIngreso.ingresaTemperatura(txtVTa.getText().trim(),txtAlmc.getText().trim(),txtRefrig.getText().trim());
  }

  private void actualizaFact()
    throws SQLException
  {
   /* DBCompras.actualizaFact(VariablesCompras.vCod_Prod,VariablesCompras.vSecIndFact,
                            txtVTa.getText().trim(),txtAlmc.getText().trim(),txtRefrig.getText().trim());*/

  }
  
    public boolean validaMontoIngresado(Object pObjeto,String Ind){
    
        JTextFieldSanSerif jtext = (JTextFieldSanSerif)pObjeto;
        if(jtext.getText().trim().length() > 0){
            String vValorProv = jtext.getText().trim();
            int vPosPuntoDecimal = vValorProv.indexOf(".");
            System.out.println("vPosPuntoDecimal " + vPosPuntoDecimal);
                String vNumeroEntero = "";
            if(vPosPuntoDecimal!=-1){
                vNumeroEntero = vValorProv.substring(0,vPosPuntoDecimal);
            }
            else
                vNumeroEntero = vValorProv.trim();
            
                System.out.println("vNumeroEntero " + vNumeroEntero);
                int vEntero = 0;
                try {
                    vEntero = Integer.parseInt(vNumeroEntero.trim());
                } catch (Exception e) {
                    vEntero = 0;
                }
                if(Ind.equalsIgnoreCase("1")){
                if(vEntero>31){
                    FarmaUtility.showMessage(this, "Area Venta: La cantidad ingresada excede el valor permitido (31°).", pObjeto);
                   FarmaUtility.moveFocus(pObjeto);
                   return false;
                }else if(vEntero>25){
                    FarmaUtility.showMessage(this, "Alerta Area Venta: Se esta ingresando valor mayor a 25C°.", pObjeto);
                    FarmaUtility.moveFocus(pObjeto);
                }
                }else if(Ind.equalsIgnoreCase("3")){
                if(vEntero>31){
                    FarmaUtility.showMessage(this, "Almacen: La cantidad ingresada excede el valor permitido (31°).", pObjeto);
                   FarmaUtility.moveFocus(pObjeto);
                   return false;
                }else if(vEntero>25){
                    FarmaUtility.showMessage(this, "Alerta Almacen: Se esta ingresando valor mayor a 25C°.", pObjeto);
                    FarmaUtility.moveFocus(pObjeto);
                }
                }else if(Ind.equalsIgnoreCase("2")){
                    if(vEntero>11){
                        FarmaUtility.showMessage(this, "Refrigerador: La cantidad ingresada excede el valor permitido (11°).", pObjeto);
                       FarmaUtility.moveFocus(pObjeto);
                       return false;
                    }else if(vEntero>8){
                        FarmaUtility.showMessage(this, "Alerta Refrigerador: Se esta ingresando valor mayor a 8C°.", pObjeto);
                        FarmaUtility.moveFocus(pObjeto);
                    }
                }
                
        }
        return true;
    }
    
    
    private  void cargarFecha(){
      try{
        String FechaInicio=FarmaSearch.getFechaHoraBD(1);
        lblFecha.setText(FechaInicio);
      }catch(SQLException sql){
        sql.printStackTrace();
      }
    }



}
