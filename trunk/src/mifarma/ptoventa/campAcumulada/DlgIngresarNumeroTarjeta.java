package mifarma.ptoventa.campAcumulada;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import mifarma.ptoventa.ventas.DlgListaProductos;
import mifarma.ptoventa.fidelizacion.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaLocalesMot.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 *        16.12.2008   Creación<br>
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */
public class DlgIngresarNumeroTarjeta extends JDialog 
{
	/* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
	private static final Logger log = LoggerFactory.getLogger(DlgIngresarNumeroTarjeta.class);
	private Frame myParentFrame;
	FarmaTableModel tableModel;
	  
	private BorderLayout borderLayout1 = new BorderLayout();
	
	private JPanelWhite jContentPane = new JPanelWhite();
	private JPanelHeader pnlHeader1 = new JPanelHeader();
		  
        private JButtonLabel btnTarjeta = new JButtonLabel();
        private JTextFieldSanSerif txtTarjeta = new JTextFieldSanSerif();
    
	private JLabelFunction lblEsc = new JLabelFunction();
	private JLabelFunction lblENTER = new JLabelFunction();
	
	/* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */
	public DlgIngresarNumeroTarjeta()
	{
		this(null, "", false);
	}

  
	public DlgIngresarNumeroTarjeta(Frame parent, String title, boolean modal)
	{
		super(parent, title, modal);
		myParentFrame = parent;
		try {
			jbInit();
			initialize();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/* ************************************************************************ */
	/*                                  METODO jbInit                           */
	/* ************************************************************************ */
  
  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(297, 112));
    this.setTitle("Ingresar Tarjeta de Fidelizacion");
    this.setDefaultCloseOperation(0);
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
    
    btnTarjeta.setBounds(new Rectangle(5, 5, 80, 20));
    btnTarjeta.setText("Nro Tarjeta :");
    btnTarjeta.setMnemonic('T');
    btnTarjeta.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
            btnTarjeta_actionPerformed(e);
        }
      });
    
    
    txtTarjeta.setText("");
    txtTarjeta.setBounds(new Rectangle(90, 5, 170, 20));
    txtTarjeta.setLengthText(20);
    txtTarjeta.addKeyListener(new KeyAdapter() {
    	public void keyTyped(KeyEvent e) {
    		txtTarjeta_keyTyped(e);
    	}
    	
    	public void keyPressed(KeyEvent e)
        {
    		txtTarjeta_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
        	txtTarjeta_keyReleased(e);
        }
    });
    
    pnlHeader1.setBounds(new Rectangle(10, 10, 275, 35));
    
    pnlHeader1.add(btnTarjeta, null);

        pnlHeader1.add(txtTarjeta, null);
        lblENTER.setBounds(new Rectangle(10, 55, 135, 20));
    lblENTER.setText("[ ENTER ] Aceptar");
    
    lblEsc.setBounds(new Rectangle(165, 55, 120, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    jContentPane.add(pnlHeader1, null);
    jContentPane.add(lblENTER, null);
        jContentPane.add(lblEsc, null);
        jContentPane.setBounds(new Rectangle(0, 0, 170, 50));
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    initTable();
    
    /*try{        
        VariablesOtros.vDocValidos = DBOtros.obtenerParamDocIden();
    }catch(SQLException e){    
        FarmaUtility.showMessage(this, "Error al obtener Parametro de Doc validos !", txtTarjeta);
    }*/
    
  }
  
  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */
  
  public void initTable()
  {
	  /*VariablesOtros.vTableModelHist = new FarmaTableModel(ConstantsOtros.columnsListaHistMedPresion,
			  ConstantsOtros.defaultListaListaHistMedPresion,0);
	  //FarmaUtility.initSimpleList(tblListaPedidos,tableModel,ConstantsOtros.columnsListaMedPresion);
	  tableModel = new FarmaTableModel(ConstantsOtros.columnsListaHistMedPresion,
			  ConstantsOtros.defaultListaListaHistMedPresion,0);
	  cargarDatosCliente();*/
  }
  
  private void cargarDatosCliente() {
	  
	 /* System.out.println("seteando valores:"+VariablesOtros.vDniCliente);
	  txtTarjeta.setText(VariablesOtros.vDniCliente);*/
	  
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
	
  private void this_windowOpened(WindowEvent e)
  {
      //lblDNI_T.setText(VariablesCtrlMotorizado.vAlias_Motorizado);
      //lblNombMotorizado_T.setText(VariablesCtrlMotorizado.vNombre_Motorizado);
      FarmaUtility.centrarVentana(this);
      FarmaUtility.moveFocus(txtTarjeta);
      //   15.05.2009
      VariablesCampAcumulada.vNroTarjeta = creaTarjeta().trim();
      cerrarVentana(true);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
	  FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  private void btnTarjeta_actionPerformed(ActionEvent e)
  {
	  FarmaUtility.moveFocus(txtTarjeta);
  }
  
  private void txtTarjeta_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtTarjeta, e);
  }
  
  
  private void txtTarjeta_keyPressed(KeyEvent e)
  {
	  chkKeyPressed(e);
  }
  
   
  private void txtTarjeta_keyReleased(KeyEvent e)
  {
   //FarmaGridUtils.buscarDescripcion(e, tblListaLocales,txtMaxSistolica, COL_DESC);
	  //chkKeyPressed(e);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */
  
  private void chkKeyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaLocales,null,0);
    if(e.getKeyCode() == KeyEvent.VK_ENTER){
        System.err.println("validando si existe TARJETA YA USADA Y SI ES VALIDA");
        txtTarjeta.setText(txtTarjeta.getText().trim());
        if(txtTarjeta.getText().length()>0){            
            //valida el numero de tarjeta sea numerico y 13 digitos
            //valida que no es codigo de barra
            //valida que el numero de tarjeta este dispoble para el uso
            if (UtilityFidelizacion.esTarjetaFidelizacionDisponible(txtTarjeta.getText())) {
                /*validarClienteTarjeta(txtTarjeta.getText());
                if(VariablesFidelizacion.vNumTarjeta.trim().length()>0)
                  UtilityFidelizacion.operaCampañasFidelizacion(txtTarjeta.getText());*/
                System.err.println("Tarjeta es valido !");
                
                VariablesCampAcumulada.vNroTarjeta = txtTarjeta.getText();
                
                
                cerrarVentana(true);
                
            } else {
                FarmaUtility.showMessage(this, 
                                         "La tarjeta no es valida\no ya esta asignado a otro cliente !", 
                                         null);
                //txtTarjeta.setText("");
                FarmaUtility.moveFocus(txtTarjeta);
                //VariablesPtoVenta.vEjecutaAccionTecla = false;                        
                //return;
            }
            
            
        }else {
            FarmaUtility.showMessage(this, "Debe Especificar el Numero de Tarjeta", txtTarjeta);
        }
        
    }
    else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar){
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }


  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */
  
  private String creaTarjeta()
  {
      long tmpIni = System.currentTimeMillis();
      log.debug(" : INICIO GENRAR TARJ Campaña acumulada");
       String vNuevaTarjetaFidelizacion = 
              UtilityFidelizacion.generaNuevaTarjeta(ConstantsFidelizacion.PREFIJO_TARJETA_FIDELIZACION,
                                                     FarmaVariables.vCodLocal);
      long tmpFin = System.currentTimeMillis();                                                               
      log.debug(" : FIN GENRAR TARJ"+ "DURACION: "+(tmpFin - tmpIni)+" milisegundos Campaña acumulada");
      // setNumeroTarjeta(vNuevaTarjetaFidelizacion);      
      log.debug("Tarjeta Nueva " + vNuevaTarjetaFidelizacion);
      
      return vNuevaTarjetaFidelizacion;
  }
  	  
}  