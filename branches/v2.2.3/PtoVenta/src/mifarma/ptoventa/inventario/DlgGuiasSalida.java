package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionNueva.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES      12.12.2013   Creación<br>
 * <br>
 * @author Cesar Huanes<br>
 * @version 2.0<br>
 *
 */
public class DlgGuiasSalida extends JDialog  implements KeyListener {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    
    private static final Logger log = LoggerFactory.getLogger(DlgGuiasSalida.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlHeader = new JPanelTitle();//Panel de cabecera
    private JPanelTitle pnlDesti = new JPanelTitle();//Panel de destinatario
    private JPanelTitle pnlTransp = new JPanelTitle();//panel de Transportista
    //Labels
    private JLabelWhite lblFecha = new JLabelWhite();
    private JLabelWhite lblLocal = new JLabelWhite();
    private JLabelWhite lblDirLocal = new JLabelWhite();
    private JLabelWhite lblRucDesti = new JLabelWhite();
    private JLabelWhite lblDirDesti = new JLabelWhite();
    private JLabelWhite lblNomTransp = new JLabelWhite();
    private JLabelWhite lblDirTransp = new JLabelWhite();
    private JLabelWhite lblPlaTransp = new JLabelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    //Texts
    private JTextFieldSanSerif txtFecha=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtLocal=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDirLocal=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSenores=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtRucDestino=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDirDestino=new JTextFieldSanSerif();  
    private JTextFieldSanSerif txtRucTransp=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNomTransp=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDirTransp=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtPlacTransp=new JTextFieldSanSerif();
    private JButtonLabel lblSenores_T = new JButtonLabel();
    private JButtonLabel lblRuc_T = new JButtonLabel();
    
    private FacadeInventario facadeInventario  = new FacadeInventario();
    
    public DlgGuiasSalida(){
        this(null, "", false);
    }
    public DlgGuiasSalida(Frame parent, String title, boolean modal) {
        super(parent,title,modal);
        myParentFrame = parent;
        try
        {
         this.addKeyListener(this);

          setFocusable(true);
          jbInit();
          initialize();
          FarmaUtility.centrarVentana(this);
        }
        catch(Exception e){
          log.error("",e);
        }
    }
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
    
    private void initialize(){
      
      FarmaVariables.vAceptar = false;
      getFecha();
      getDatosLocal();
    }
    
    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(443, 513));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Guias de Salida");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter(){
            public void windowOpened(WindowEvent e)
            {
              this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e)
            {
            this_windowClosing(e);
            }
        });
        
        pnlHeader.setBounds(new Rectangle(10, 10, 415, 140));
        pnlHeader.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlHeader.setBackground(Color.white);
        pnlHeader.setFocusable(false);
        
        lblFecha.setText("Fecha:");
        lblFecha.setBounds(new Rectangle(20, 10, 60, 15));
        lblFecha.setForeground(new Color(255, 130, 14));
        lblFecha.setFocusable(false);
        
        lblLocal.setText("Local:");
        lblLocal.setBounds(new Rectangle(20, 40, 60, 15));
        lblLocal.setForeground(new Color(255, 130, 14));
        lblLocal.setFocusable(false);
    
        lblDirLocal.setText("Direccion:");
        lblDirLocal.setBounds(new Rectangle(20, 75, 60, 15));
        lblDirLocal.setForeground(new Color(255, 130, 14));
        lblDirLocal.setFocusable(false);
        
        txtFecha.setBounds(new Rectangle(90, 10, 75, 20));
        txtFecha.setEnabled(false);
        txtLocal.setBounds(new Rectangle(90, 40, 265, 20));
        txtLocal.setEnabled(false);
        txtDirLocal.setBounds(new Rectangle(90, 75,265, 20));
        txtDirLocal.setEnabled(false);


        pnlHeader.add(lblFecha, null);
        pnlHeader.add(lblLocal, null);
        pnlHeader.add(lblDirLocal, null);
        pnlHeader.add(txtFecha, null);
        pnlHeader.add(txtLocal, null);
        pnlHeader.add(txtDirLocal, null);
        
        
        
        
        pnlDesti.setBounds(new Rectangle(10, 160, 415, 115));
        pnlDesti.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDesti.setBackground(Color.white);
        pnlDesti.setFocusable(false);


        lblRucDesti.setText("Ruc:");
        lblRucDesti.setBounds(new Rectangle(20, 48, 70, 15));
        lblRucDesti.setForeground(new Color(255, 130, 14));
        lblRucDesti.setFocusable(false);
        
        lblDirDesti.setText("Dirección:");
        lblDirDesti.setBounds(new Rectangle(20, 80, 70, 15));
        lblDirDesti.setForeground(new Color(255, 130, 14));
        lblDirDesti.setFocusable(false);
        
        txtSenores.setBounds(new Rectangle(95, 15, 265, 20));
        txtSenores.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtSenores_keyPressed(e);
        }
      });
        txtRucDestino.setBounds(new Rectangle(95, 48, 99, 20));  
        txtRucDestino.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtRuc_keyPressed(e);
        }
      });
        txtDirDestino.setBounds(new Rectangle(95, 80, 265, 20));
       txtDirDestino.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtDireccion_keyPressed(e);
        }
      });

        pnlDesti.add(lblSenores_T, null);
        pnlDesti.add(txtDirDestino, null);
        pnlDesti.add(txtRucDestino, null);
        pnlDesti.add(txtSenores, null);
        pnlDesti.add(lblDirDesti, null);
        pnlDesti.add(lblRucDesti,null);


        pnlTransp.setBounds(new Rectangle(10, 285, 415, 160));   
        pnlTransp.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlTransp.setBackground(Color.white);
        pnlTransp.setFocusable(false);
        
        lblNomTransp.setText("Transportista:"); 
        lblNomTransp.setBounds(new Rectangle(20, 45, 85, 15));
        lblNomTransp.setForeground(new Color(255, 130, 14));
        lblNomTransp.setFocusable(false);


        lblDirTransp.setText("Direccion:");
        lblDirTransp.setBounds(new Rectangle(20, 80, 70, 15)); 
        lblDirTransp.setForeground(new Color(255, 130, 14));
        lblDirTransp.setFocusable(false);
        
        lblPlaTransp.setText("Placa"); 
        lblPlaTransp.setBounds(new Rectangle(20, 110, 70, 15));
        lblPlaTransp.setForeground(new Color(255, 130, 14));
        lblPlaTransp.setFocusable(false);
        
        txtRucTransp.setBounds(new Rectangle(115, 15, 100, 20));
        txtRucTransp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtRucTransportista_keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
        txtRucTransportista_keyRelease(e);
        }
      });
        txtNomTransp.setBounds(new Rectangle(115, 45, 265, 20));
        txtNomTransp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtTransportista_keyPressed(e);
        }
      });
        txtDirTransp.setBounds(new Rectangle(115, 80, 265, 20));
        txtDirTransp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtDireccionTransportista_keyPressed(e);
        }
      });
        txtPlacTransp.setBounds(new Rectangle(115, 110, 100, 20));
        txtPlacTransp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtPlaca_keyPressed(e);
        }
      });

        pnlTransp.add(lblRuc_T, null);
        pnlTransp.add(lblNomTransp, null);
        pnlTransp.add(lblDirTransp, null);
        pnlTransp.add(lblPlaTransp, null);
        pnlTransp.add(lblNomTransp, null);
        pnlTransp.add(txtRucTransp, null);
        pnlTransp.add(txtNomTransp, null);
        pnlTransp.add(txtDirTransp, null);
        pnlTransp.add(txtPlacTransp, null);
        
            
        
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(335, 455, 85, 20));
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(205, 455, 115, 20));
        lblF11.setFocusable(false);
        
        
        jContentPane.add(pnlHeader,null);
        jContentPane.add(pnlDesti,null);
        jContentPane.add(pnlTransp,null);
        jContentPane.add(lblF11,null);
        jContentPane.add(lblEsc,null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        
        txtRucDestino.setLengthText(11);
        txtSenores.setLengthText(30);
        txtDirDestino.setLengthText(120);
        txtRucTransp.setLengthText(11);
        txtNomTransp.setLengthText(30);
        txtDirTransp.setLengthText(120);
        txtPlacTransp.setLengthText(15);
        lblSenores_T.setText("Se\u00f1ores:");
        lblSenores_T.setBounds(new Rectangle(20, 15, 65, 20));
        lblSenores_T.setForeground(new Color(255, 130, 14));
        lblSenores_T.setMnemonic('S');
        lblSenores_T.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblSenores_actionPerformed(e);
                }
            });
        lblRuc_T.setText("RUC:");
        lblRuc_T.setBounds(new Rectangle(20, 15, 65, 20));
        lblRuc_T.setForeground(new Color(255, 130, 14));
        lblRuc_T.setMnemonic('R');
        lblRuc_T.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblRuc_actionPerformed(e);
                }
            });

    }
    
    
    
    /* ************************************************************************ */
          /*                            METODOS DE EVENTOS                            */
          /* ************************************************************************ */
          @Override
          public void keyTyped(KeyEvent e) {
             // chkKeyPressed(e);
          }

        @Override
          public void keyPressed(KeyEvent e) {
              chkKeyPressed(e);
          }

          @Override
          public void keyReleased(KeyEvent e) {
          }
    
    
    private void txtSenores_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtRucDestino);
        txtSenores.setText(txtSenores.getText().toUpperCase().trim());
      }
      else
        chkKeyPressed(e);
    }

    private void txtRuc_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtDirDestino);
      }
      else
        chkKeyPressed(e);
    }

    private void txtDireccion_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtRucTransp);
      }
      else
        chkKeyPressed(e);
    }
    
    private void txtRucTransportista_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_TRANSPORTISTA;
      validaCodigo(txtRucTransp, txtNomTransp, VariablesPtoVenta.vTipoMaestro);
       
      }
      else
        chkKeyPressed(e);
    }
    
    private void txtRucTransportista_keyRelease(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER && FarmaVariables.vAceptar)
        {   cargarTransportista();
            FarmaUtility.moveFocus(txtNomTransp);
            FarmaVariables.vAceptar = false;
         
        }
    }
    
    private void txtTransportista_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtDirTransp);
      }
      else
        chkKeyPressed(e);
    }
    private void txtDireccionTransportista_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtPlacTransp);
      }
      else
        chkKeyPressed(e);
    }

    private void txtPlaca_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtSenores);
      }
      else
        chkKeyPressed(e);
    }
    
    private void this_windowOpened(WindowEvent e)
    {
      FarmaUtility.centrarVentana(this);
      FarmaUtility.moveFocus(txtSenores);  
     
    }
    
    private void this_windowClosing(WindowEvent e)
    {
      FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void lblSenores_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSenores);
    }

    private void lblRuc_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtRucTransp);
    }
    
    /* ************************************************************************ */
          /*                     METODOS AUXILIARES DE EVENTOS                        */
          /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e){
      if(UtilityPtoVenta.verificaVK_F11(e)){
          if(validarCampos()){
             abrirModal();
              if(FarmaVariables.vAceptar){
                  generaGuiaSalida(); 
              }          
          }
      }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        cerrarVentana(false);
      }
    }
      
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void abrirModal(){
        DlgGuiaModal dlgGuiaModal=new DlgGuiaModal(this.myParentFrame,"",true);
        dlgGuiaModal.setVisible(true);
    
    }

    private void getFecha(){
        String vfecha=null;
        try
        {
            vfecha=FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            if(vfecha!=null){
                txtFecha.setText(vfecha.trim().substring(0, 10));
            }
       
        }catch(SQLException sql)
        {
          log.error("",sql);
          FarmaUtility.showMessage(this,"Error al obtener la fecha del sistema :\n"+sql.getMessage(),null);
        }
       
      
    }
    
    private void getDatosLocal(){
        txtLocal.setText(FarmaVariables.vDescLocal);
        txtDirLocal.setText(FarmaVariables.vDescCortaDirLocal);
    }
   
    private void  getDatosVista(){
        
       VariablesInventario.vFecha=txtFecha.getText().trim();
       VariablesInventario.vDescLocal=txtLocal.getText().trim();
       VariablesInventario.vDireLocal=txtDirLocal.getText().trim();
       VariablesInventario.vRucDestina=txtRucDestino.getText().trim();
       VariablesInventario.vNomDesctina=txtSenores.getText().trim();
       VariablesInventario.vDireDestina=txtDirDestino.getText().trim();
       VariablesInventario.vRucTransp=txtRucTransp.getText().trim();
       VariablesInventario.vNomTransp=txtNomTransp.getText().trim();
       VariablesInventario.vDireTransp=txtDirTransp.getText().trim();
       VariablesInventario.vPlacTransp=txtPlacTransp.getText().trim();
        
    }
    
    private void  saveDatos() {
        boolean vFlag;

          try {
              vFlag = facadeInventario.grabarGuiaRemision(this);
              if(!vFlag){
                  FarmaUtility.showMessage(this, "Registro grabado." + VariablesInventario.vNumGuia.trim(), null);
                  //4. Imprimir guia                    
                  UtilityInventario.cargaCabecera(this,null,VariablesInventario.vNumNotaEs);
                  UtilityInventario.procesoImpresionGuias(this ,null , VariablesInventario.vTipoFormatoImpresion, true);
                  facadeInventario.confirmarDevolucion(VariablesInventario.vNumNotaEs);
                  cerrarVentana(true);
              }else{  
                  FarmaUtility.showMessage(this, "Error Fallo la Transaccíon " + VariablesInventario.vNumGuia.trim(), null);                 
              }        
          }catch(Exception ex){
              log.error("",ex);
          }         
    }
    
    

    private void generaGuiaSalida() {
        if (JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro de generar la transferencia?")) {
            FarmaVariables.vAceptar = false;
            DlgListaImpresorasInv dlgListaImpresorasInv = new DlgListaImpresorasInv(this.myParentFrame, "", true);
            dlgListaImpresorasInv.setVisible(true);
            if (!FarmaVariables.vAceptar) {
                return;
            }
            if (grabar()) {
               
                cerrarVentana(true);
            }
        }
    }

    private boolean grabar() {   
        getDatosVista();
        saveDatos();        
        return true;
    }
    
    private void cargarTransportista(){
        txtDirTransp.setText("");
        txtPlacTransp.setText("-");
        try
        {
          ArrayList array = new ArrayList();
        
           DBInventario.getDatosTranspRecepCiega(array,txtRucTransp.getText()); 
          if(array.size()>0)
          {
            array = (ArrayList)array.get(0);
            String codTransp=array.get(0).toString();
            String rucTransp=array.get(1).toString();
            if(rucTransp.trim()==null ||rucTransp.trim().equals(0) || rucTransp.trim().length()!=11){
                txtRucTransp.setText("");          
            }
                String direccionTransp=array.get(3).toString();
                txtDirTransp.setText(direccionTransp);
            
            if(codTransp.trim()!=null &&(rucTransp.trim()!=null )){
               txtRucTransp.setText("");
               txtRucTransp.setText(rucTransp); 
               txtNomTransp.setText(array.get(2).toString());
               txtDirTransp.setText(array.get(3).toString());
             
            }
           
             
          }
        }
        catch (SQLException e)
        {
          log.error("",e);
          FarmaUtility.showMessage(this, 
                                   "Ha ocurrido un error al obtener datos del transportista.\n" + 
                                   e.getMessage(), txtRucTransp);
        }    
        
    }
    private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro)
    {
      if(pJTextField_Cod.getText().trim().length() > 0)
      {
        VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
        ArrayList myArray = new ArrayList();
        buscaCodigoListaMaestro(myArray);
        
        if(myArray.size() == 0)
        {
       FarmaUtility.showMessage(this,"Ruc No Encontrado",pJTextField_Cod);
          FarmaVariables.vAceptar = false;
        } else if(myArray.size() == 1)
        {
          String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
          String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
          pJTextField_Cod.setText(codigo);
          pJTextField_Desc.setText(descripcion);
          VariablesPtoVenta.vCodMaestro = codigo;
          FarmaVariables.vAceptar = true;
        } else
        {
          FarmaUtility.showMessage(this,"Se encontro mas de un registro.Verificar!!!",pJTextField_Cod);
          FarmaVariables.vAceptar = false;
        }
      } else
      {
        cargaListaMaestros(pTipoMaestro,pJTextField_Cod, pJTextField_Desc);
      }
    }
    
    private void buscaCodigoListaMaestro(ArrayList pArrayList)
    {
      try
      {
        DBPtoVenta.buscaCodigoListaMaestro(pArrayList,VariablesPtoVenta.vTipoMaestro, VariablesPtoVenta.vCodMaestro);
      } catch(SQLException sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ocurrió un error al buscar ruc en maestros :\n" +sql.getMessage(),null);
      }
    }
    private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc)
    {
      VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
      DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
      dlgListaMaestros.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
        pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
        pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
      }
    }
    
    private boolean validarCampos()
    {
      boolean retorno = true;
     
   if(txtSenores.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar la Razon Social",txtSenores);
        retorno = false;
      }
      else if(UtilityInventario.verificaRucValido(txtRucDestino.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO))
      {
        FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRucDestino);
        retorno = false;
      }
      else if(txtRucDestino.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar el Ruc del Destinatario",txtRucDestino);
        retorno = false;
      }else if(txtDirDestino.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar la Direccion de Destino",txtDirDestino);
        retorno = false;
      }else if(txtNomTransp.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar el Transportista",txtNomTransp);
        retorno = false;
      }else if(txtRucTransp.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar el Ruc del Transportista",txtRucTransp);
        retorno = false;
      }
      else if(UtilityInventario.verificaRucValido(txtRucTransp.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO))
      {
        FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRucTransp);
        retorno = false;
      }
      else if(txtDirTransp.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar la Direccion del Transportista",txtDirTransp);
        retorno = false;
      }else if(txtPlacTransp.getText().trim().equals("-"))
      {
        FarmaUtility.showMessage(this,"Debe ingresar la Placa del Transportista",txtPlacTransp);
        retorno = false;
      }
      
      return retorno;
    }

}
