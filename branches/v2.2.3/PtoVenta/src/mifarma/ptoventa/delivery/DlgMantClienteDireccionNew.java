package mifarma.ptoventa.delivery;
import java.awt.*;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import com.gs.mifarma.componentes.JPanelWhite;
import java.awt.Dimension;
import com.gs.mifarma.componentes.JPanelTitle;
import java.awt.Rectangle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import mifarma.common.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import java.awt.Color;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelOrange;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import mifarma.ptoventa.delivery.reference.*;
import mifarma.common.FarmaLengthText;
import java.sql.*;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMantClienteDireccion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.04.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgMantClienteDireccionNew extends JDialog
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
	private static final Logger log = LoggerFactory.getLogger(DlgMantClienteDireccionNew.class);

  Frame myParentFrame;
  FarmaTableModel tableModel;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JPanelWhite pnlWhite1 = new JPanelWhite();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JButtonLabel btnCliente = new JButtonLabel();
  private JTextFieldSanSerif txtNomCli = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtNomCalle = new JTextFieldSanSerif();
  private JButtonLabel btnNombre = new JButtonLabel();
  private JLabelOrange lbltelefono = new JLabelOrange();
  private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();
  private JLabelOrange lblNomCalle_T1 = new JLabelOrange();

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgMantClienteDireccionNew()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  public DlgMantClienteDireccionNew(Frame parent, String title, boolean modal)
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
      log.error("",e);
    }
  }

  /* ************************************************************************ */
	/*                                  METODO jbInit                           */
	/* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Datos del Cliente");
    this.setSize(new Dimension(563, 223));
    this.setDefaultCloseOperation(0);
    this.addWindowListener(new java.awt.event.WindowAdapter()
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
    lblEsc.setBounds(new Rectangle(450, 165, 95, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF11.setBounds(new Rectangle(325, 165, 105, 20));
    lblF11.setText("[ F11 ] Aceptar");
    pnlWhite1.setBounds(new Rectangle(10, 30, 540, 125));
    pnlWhite1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    pnlTitle1.setBounds(new Rectangle(10, 10, 540, 20));
    btnCliente.setText("Cliente");
    btnCliente.setBounds(new Rectangle(5, 0, 75, 20));
    txtNomCli.setBounds(new Rectangle(80, 45, 435, 20));
    txtNomCli.addFocusListener(new java.awt.event.FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
                        txtNomCli_focusLost(e);
                    }
      });
    txtNomCli.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtNomCli_keyPressed(e);
        }
      });
    txtNomCalle.setBounds(new Rectangle(80, 80, 440, 20));
    txtNomCalle.addFocusListener(new java.awt.event.FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          txtNomCalle_focusLost(e);
        }
      });
    txtNomCalle.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtNomCalle_keyPressed(e);
        }
      });
    btnNombre.setText("Nombre :");
    btnNombre.setBounds(new Rectangle(10, 45, 65, 15));
    btnNombre.setForeground(new Color(255, 130, 14));
    btnNombre.setMnemonic('N');
    btnNombre.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNombre_actionPerformed(e);
        }
      });
    btnNombre.addKeyListener(new java.awt.event.KeyAdapter()
      {
        
      });
    lbltelefono.setText("Telefono :");
    lbltelefono.setBounds(new Rectangle(10, 10, 65, 15));
    txtTelefono.setBounds(new Rectangle(80, 10, 110, 20));
    txtTelefono.addKeyListener(new java.awt.event.KeyAdapter()
      {
        
      });
    txtTelefono.setDocument(new FarmaLengthText(10));
    lblNomCalle_T1.setText("Direccion : ");
    lblNomCalle_T1.setBounds(new Rectangle(10, 80, 60, 15));
    pnlTitle1.add(btnCliente, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(pnlWhite1, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    pnlWhite1.add(lblNomCalle_T1, null);
    pnlWhite1.add(btnNombre, null);
    pnlWhite1.add(txtNomCli, null);
    pnlWhite1.add(lbltelefono, null);
    pnlWhite1.add(txtTelefono, null);
    pnlWhite1.add(txtNomCalle, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    txtNomCli.setDocument(new FarmaLengthText(100));
    txtNomCalle.setDocument(new FarmaLengthText(110));
  }

  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initCmbEstCli();
    initCmbCliJur();

    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initCmbEstCli()
  {
    //FarmaLoadCVL.loadCVLfromArrays(cmbEstCli,"cmbEstCli",FarmaConstants.ESTADOS_CODIGO,FarmaConstants.ESTADOS_DESCRIPCION,false);
  }

  private void initCmbCliJur()
  {
    //FarmaLoadCVL.loadCVLfromArrays(cmbCliJur,"cmbCliJur",FarmaConstants.INDICADORES_CODIGO,FarmaConstants.INDICADORES_DESCRIPCION,false);
  }

  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

	private void this_windowOpened(WindowEvent e)
  {

    FarmaUtility.centrarVentana(this);
    txtTelefono.setEnabled(false);
    txtTelefono.setText(VariablesDelivery.vNumeroTelefono);

    log.debug("VariablesDelivery.vTipoAccion = "+VariablesDelivery.vTipoAccion);
    log.debug("VariablesDelivery.vTipoAccionInsertarSoloCliente = "+VariablesDelivery.vTipoAccionInsertarSoloCliente);
    log.debug("VariablesDelivery.vTipoAccionDireccion = "+VariablesDelivery.vTipoAccionDireccion);

    if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR)){
      //cargaDatosDireccionCliente();
      FarmaUtility.moveFocus(txtNomCli);
    }
    else if (VariablesDelivery.vTipoAccion .equals(ConstantsDelivery.ACCION_MODIFICAR))
    {
      cargaDatosDireccionCliente();
      cargaDatosCliente();
      FarmaUtility.moveFocus(txtNomCli);
    }
    else if (VariablesDelivery.vTipoAccionDireccion .equals(ConstantsDelivery.ACCION_MODIFICAR_DIRECCION))
    {
      cargaDatosDireccionClienteModificacion();
      cargaDatosClienteModificacionCliente();
      FarmaUtility.moveFocus(txtNomCalle);
    }
    else if (VariablesDelivery.vTipoAccionDireccion .equals(ConstantsDelivery.ACCION_INSERTAR_DIRECCION))
    {
      cargaDatosClienteModificacionCliente();
      FarmaUtility.moveFocus(txtNomCalle);
    }
    else if (VariablesDelivery.vTipoAccionInsertarSoloCliente.equals(ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE))
    {
      cargaDatosDireccionCliente();
      FarmaUtility.moveFocus(txtNomCli);
    }else if (VariablesDelivery.vTipoAccion.equals(ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE))
    {
      cargaDatosDireccionCliente();
      FarmaUtility.moveFocus(txtNomCli);
    }
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void txtNomCli_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtNomCalle);
      txtNomCli.setText(txtNomCli.getText().toUpperCase());
    }
    else
      chkKeyPressed(e);
  }
  
  private void txtNomCalle_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtNomCli);
      txtNomCalle.setText(txtNomCalle.getText().toUpperCase());
    }
    else
      chkKeyPressed(e);
  }

  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
    {

      guardaValoresCliente();
      log.debug("VariablesDelivery.vTipoAccionInsertarSoloCliente : " + VariablesDelivery.vTipoAccionInsertarSoloCliente);
      
        if(!validaDatosClientes()) return;
           String resultadoAgregaCliente = "";
           String resultadoAgregaDireccion = "";
           String resultadoAgregaTelefono = "";
           String resultadoActualizaCliente = "";
           String resultadoActualizaDireccion = "";
           String resultadoAgregaDireccionNueva = "" ;
           String resultadoAgregaSoloCliente = "" ;

        if(VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR)){
           FarmaUtility.moveFocus(txtNomCli);
           resultadoAgregaCliente = agregaCliente();
           resultadoAgregaDireccion = agregaDireccion();
           resultadoAgregaTelefono = agregaTelefono();
           if(!resultadoAgregaCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)
            || !resultadoAgregaDireccion.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)
            || !resultadoAgregaTelefono.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)){
              FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
              FarmaUtility.aceptarTransaccion();
              cerrarVentana(true);
            } else {
                FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNomCli);
                FarmaUtility.liberarTransaccion();
              }
        } else if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_MODIFICAR)){
            resultadoActualizaCliente = actualizaCliente();
            if(!resultadoActualizaCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)){
              FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
              FarmaUtility.aceptarTransaccion();
              cerrarVentana(true);
            } else {
                FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNomCli);
                FarmaUtility.liberarTransaccion();
              }
        } else if (VariablesDelivery.vTipoAccionDireccion.equalsIgnoreCase(ConstantsDelivery.ACCION_MODIFICAR_DIRECCION)){
            resultadoActualizaDireccion = actualizaDireccion();
            if(!resultadoActualizaDireccion.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)){
              FarmaUtility.showMessage(this, "Se grabo la direccion con Exito", null);
              FarmaUtility.aceptarTransaccion();
              cerrarVentana(true);
            } else {
                FarmaUtility.showMessage(this, "Error al grabar la direccion", txtNomCli);
                FarmaUtility.liberarTransaccion();
              }
        } else if (VariablesDelivery.vTipoAccionDireccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR_DIRECCION)){
            resultadoAgregaDireccionNueva = agregaDireccion();
            agregaTelefono();
            if(!resultadoAgregaDireccionNueva.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)){
              FarmaUtility.showMessage(this, "Se grabo la direccion con Exito", null);
              FarmaUtility.aceptarTransaccion();
              cerrarVentana(true);
            } else {
                FarmaUtility.showMessage(this, "Error al grabar la direccion", txtNomCli);
                FarmaUtility.liberarTransaccion();
            }
        } else if (VariablesDelivery.vTipoAccionInsertarSoloCliente.equals(ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE)){
          log.debug("VariablesDelivery.vTipoAccionInsertarSoloCliente : " + VariablesDelivery.vTipoAccionInsertarSoloCliente);
            resultadoAgregaSoloCliente = agregaCliente();
          log.debug("paso");
            if(!resultadoAgregaSoloCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)){
              FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
              FarmaUtility.aceptarTransaccion();
              cerrarVentana(true);
            } else {
                FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNomCli);
                FarmaUtility.liberarTransaccion();
            }
        }

      }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
       VariablesDelivery.vTipoAccion="";
       VariablesDelivery.vTipoAccionInsertarSoloCliente="";
       VariablesDelivery.vTipoAccionDireccion="";

      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar)
	{
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
    this.dispose();
    //VariablesDelivery.vTipoAccionDireccion="";
    VariablesDelivery.vTipoAccion = "";
    log.debug("Variable nula : " + VariablesDelivery.vTipoAccionDireccion);
    log.debug("Variable nula : " + VariablesDelivery.vTipoAccion);
  }

  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */
  
  private void btnNombre_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtNomCli);
  }

  private void guardaValoresCliente()
  {
    VariablesDelivery.vNombreCliente = txtNomCli.getText().trim().toUpperCase();
    VariablesDelivery.vApellidoPaterno = "";
    VariablesDelivery.vApellidoMaterno = "";
    VariablesDelivery.vTipoDocumento = ConstantsDelivery.TIPO_COMP_BOLETA;
    VariablesDelivery.vTipoCliente = ConstantsDelivery.TIPO_CLIENTE_NATURAL;
    VariablesDelivery.vNumeroDocumento = "";
    VariablesDelivery.vNombreCalle = txtNomCalle.getText().trim().toUpperCase();
    VariablesDelivery.vNumeroCalle = "";
    VariablesDelivery.vNombreInterior = "";
    VariablesDelivery.vNombreUrbanizacion = "";
    VariablesDelivery.vNombreDistrito = "";
    VariablesDelivery.vReferencia = "";
    

    log.debug(VariablesDelivery.vNombreCliente);
    log.debug(VariablesDelivery.vApellidoPaterno);
    log.debug(VariablesDelivery.vApellidoMaterno);
    log.debug(VariablesDelivery.vTipoDocumento);
    log.debug(VariablesDelivery.vTipoCliente);
    log.debug(VariablesDelivery.vNumeroDocumento);
    log.debug(VariablesDelivery.vNombreCalle);
    log.debug(VariablesDelivery.vNumeroCalle);
    log.debug(VariablesDelivery.vNombreInterior);
    log.debug(VariablesDelivery.vNombreUrbanizacion);
    log.debug(VariablesDelivery.vNombreDistrito);
    log.debug(VariablesDelivery.vReferencia);

  }

  private boolean validaDatosClientes()
  {
    if(VariablesDelivery.vNombreCliente.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(this, "Ingrese el Nombre del Cliente", txtNomCli);
      return false;
    }
    if(VariablesDelivery.vNombreCalle.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(this, "Ingrese el Nombre de la Calle para la direccion", txtNomCalle);
      return false;
    }
    
    return true;
  }

  private String agregaCliente()
  {
    String resultadoAgregaCliente = "";
    try
    {
      resultadoAgregaCliente = DBDelivery.agregaCliente(VariablesDelivery.vNombreCliente,
                                         VariablesDelivery.vApellidoPaterno,
                                         VariablesDelivery.vApellidoMaterno,
                                         VariablesDelivery.vTipoDocumento,
                                         VariablesDelivery.vTipoCliente,
                                         VariablesDelivery.vNumeroDocumento);
      VariablesDelivery.vCodCli = resultadoAgregaCliente;
      return resultadoAgregaCliente;
    }
    catch(SQLException sql)
    {
      log.error("",sql);
      //FarmaUtility.showMessage(this,"Error",null);
      if(sql.getErrorCode() == 00001)
      {
        FarmaUtility.showMessage(this, "Ya existe el Dni. Verifique!", txtNomCli);  
      }
      return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }

  private String agregaDireccion()
  {
    String resultadoAgregaDireccion = "" ;
    try
    {
      resultadoAgregaDireccion = DBDelivery.agregaDireccion(".",
                                         VariablesDelivery.vNombreCalle,
                                         VariablesDelivery.vNumeroCalle,
                                         VariablesDelivery.vNombreUrbanizacion,
                                         VariablesDelivery.vNombreDistrito,
                                         VariablesDelivery.vNombreInterior,
                                         VariablesDelivery.vReferencia);
      VariablesDelivery.vCodigoDireccion = resultadoAgregaDireccion;
      return resultadoAgregaDireccion;
    }
    catch(SQLException sql)
    {
      log.error("",sql);
      //FarmaUtility.showMessage(this,"Error",null);
      return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }

  private String agregaTelefono()
  {
    String resultadoAgregaTelefono = "" ;
    try
    {
      resultadoAgregaTelefono = DBDelivery.agregaTelefono();
      VariablesDelivery.vCodTelefono = resultadoAgregaTelefono;
      return resultadoAgregaTelefono;
    }
    catch(SQLException sql)
    {
      log.error("",sql);
      //FarmaUtility.showMessage(this,"Error",null);
      return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }

  private void cargaDatosDireccionCliente()
  {
    txtNomCalle.setText(VariablesDelivery.vDireccion);
    txtNomCalle.setEnabled(false);
  }

   private void cargaDatosDireccionClienteModificacion()
  {
    txtNomCalle.setText(VariablesDelivery.vDireccion);
    
  }

  private void cargaDatosCliente()
  {
    txtNomCli.setText(VariablesDelivery.vNombreCliente);
  }

  private void cargaDatosClienteModificacionCliente()
  {
    txtNomCli.setText(VariablesDelivery.vNombreCliente);
    txtNomCli.setEnabled(false);
    
  }

  private String actualizaCliente()
  {
    String resultado = "";
    try
    {
      resultado = DBDelivery.actualizaCliente(VariablesDelivery.vCodCli,
                                            VariablesDelivery.vNombreCliente,
                                            VariablesDelivery.vApellidoPaterno,
                                            VariablesDelivery.vApellidoMaterno,
                                            VariablesDelivery.vNumeroDocumento);
      return resultado;
    }
    catch(SQLException sql)
    {
      log.error("",sql);
      return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }

  private String actualizaDireccion()
  {
    String resultado = "";
    try
    {
      resultado = DBDelivery.actualizaDireccion(VariablesDelivery.vCodigoDireccion,
                                              ".",
                                              VariablesDelivery.vNombreCalle,
                                              VariablesDelivery.vNumeroCalle,
                                              VariablesDelivery.vNombreUrbanizacion,
                                              VariablesDelivery.vNombreDistrito,
                                              VariablesDelivery.vNombreInterior,
                                              VariablesDelivery.vReferencia);
      return resultado;
    }
    catch(SQLException sql)
    {
      log.error("",sql);
      return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }
  private void txtNomCli_focusLost(FocusEvent e)
  {
   txtNomCli.setText(txtNomCli.getText().toUpperCase());
  }

  private void txtNomCalle_focusLost(FocusEvent e)
  {
    txtNomCalle.setText(txtNomCalle.getText().toUpperCase());
  }



}