package mifarma.ptoventa.delivery;

import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;

import java.util.ArrayList;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.delivery.reference.*;
import mifarma.common.FarmaLengthText;

import mifarma.ptoventa.retiro.DlgListaLocales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMantMotorizados.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LREQUE      21.12.2006   Creación
 * <br>
 * @author Luis Reque Orellana<br>
 * @version 1.0<br>
 */

public class DlgMantMotorizados extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgMantMotorizados.class);
  private Frame myParentFrame;
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jPanelWhite2 = new JPanelWhite();
  private JLabelOrange jLabelOrange2 = new JLabelOrange();
  private JLabelOrange jLabelOrange3 = new JLabelOrange();
  private JLabelOrange jLabelOrange4 = new JLabelOrange();
  private JLabelOrange jLabelOrange5 = new JLabelOrange();
  private JLabelOrange jLabelOrange6 = new JLabelOrange();
  private JLabelOrange jLabelOrange7 = new JLabelOrange();
  private JLabelOrange jLabelOrange8 = new JLabelOrange();
  private JTextFieldSanSerif txtNomMot = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtApePMot = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtApeMMot = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtDniMot = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtPlaca = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtNextel = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtFecNac = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtDirecMot = new JTextFieldSanSerif();
  private JLabelFunction lblAceptar = new JLabelFunction();
  private JLabelFunction lblCerrar = new JLabelFunction();
  private JButtonLabel btnNomMot = new JButtonLabel();
  private JTextFieldSanSerif txtAlias = new JTextFieldSanSerif();
    private JLabelOrange lblAlias = new JLabelOrange();


    public DlgMantMotorizados()
  {
    this(null, "", false);
  }

  public DlgMantMotorizados(Frame parent, String title, boolean modal)
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

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(542, 325));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Mantenimiento de Motorizados");
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
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    jPanelWhite2.setBounds(new Rectangle(10, 10, 515, 255));
    jPanelWhite2.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    jLabelOrange2.setText("Apellido Paterno:");
    jLabelOrange2.setBounds(new Rectangle(20, 40, 105, 30));
    jLabelOrange3.setText("Apellido Materno:");
    jLabelOrange3.setBounds(new Rectangle(20, 65, 105, 30));
    jLabelOrange4.setText("DNI:");
    jLabelOrange4.setBounds(new Rectangle(20, 120, 35, 20));
    jLabelOrange5.setText("Núm. Nextel:");
    jLabelOrange5.setBounds(new Rectangle(20, 170, 80, 20));
    jLabelOrange6.setText("Placa:");
    jLabelOrange6.setBounds(new Rectangle(20, 145, 40, 20));
    jLabelOrange7.setText("Fecha Nacimiento:");
    jLabelOrange7.setBounds(new Rectangle(20, 195, 105, 20));
    jLabelOrange8.setText("Dirección:");
    jLabelOrange8.setBounds(new Rectangle(20, 90, 65, 30));
    txtNomMot.setBounds(new Rectangle(130, 20, 145, 20));
    txtNomMot.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        txtNomMot_keyPressed(e);
      }

      public void keyTyped(KeyEvent e)
      {
        txtNomMot_keyTyped(e);
      }
    });
    txtApePMot.setBounds(new Rectangle(130, 45, 240, 20));
    txtApePMot.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        txtApePMot_keyPressed(e);
      }

      public void keyTyped(KeyEvent e)
      {
        txtApePMot_keyTyped(e);
      }
    });
    txtApeMMot.setBounds(new Rectangle(130, 70, 240, 20));
    txtApeMMot.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        txtApeMMot_keyPressed(e);
      }

      public void keyTyped(KeyEvent e)
      {
        txtApeMMot_keyTyped(e);
      }
    });
    txtDniMot.setBounds(new Rectangle(130, 120, 85, 20));
    txtDniMot.setLengthText(8);
    txtDniMot.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        txtDniMot_keyPressed(e);
      }

      public void keyTyped(KeyEvent e)
      {
        txtDniMot_keyTyped(e);
      }
    });
    txtPlaca.setBounds(new Rectangle(130, 145, 85, 20));
    txtPlaca.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        txtPlaca_keyPressed(e);
      }
    });
    txtNextel.setBounds(new Rectangle(130, 170, 85, 20));
    txtNextel.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        txtNextel_keyPressed(e);
      }
    });
    txtFecNac.setBounds(new Rectangle(130, 195, 85, 20));
    txtFecNac.setLengthText(10);
    txtFecNac.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        txtFecNac_keyPressed(e);
      }

      public void keyTyped(KeyEvent e)
      {
        txtFecNac_keyTyped(e);
      }

      public void keyReleased(KeyEvent e)
      {
        txtFecNac_keyReleased(e);
      }
    });
    txtDirecMot.setBounds(new Rectangle(130, 95, 355, 20));
    txtDirecMot.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        txtDirecMot_keyPressed(e);
      }
    });
    lblAceptar.setBounds(new Rectangle(305, 270, 110, 20));
    lblAceptar.setText("[ F11 ] Aceptar");
    lblCerrar.setBounds(new Rectangle(425, 270, 100, 20));
    lblCerrar.setText("[ ESC ] Cerrar");
    btnNomMot.setText("Nombre:");
    btnNomMot.setMnemonic('n');
    btnNomMot.setBounds(new Rectangle(20, 20, 90, 20));
    btnNomMot.setForeground(new Color(255, 130, 14));
    btnNomMot.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btnNomMot_actionPerformed(e);
      }
    });
    txtAlias.setBounds(new Rectangle(130, 220, 145, 20));
    txtAlias.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtAlias_keyPressed(e);
        }

      });
        lblAlias.setText("Alias :");
    lblAlias.setBounds(new Rectangle(20, 220, 105, 20));
        jPanelWhite2.add(lblAlias, null);
        jPanelWhite2.add(txtAlias, null);
    jPanelWhite2.add(btnNomMot, null);
    jPanelWhite2.add(txtFecNac, null);
    jPanelWhite2.add(txtNextel, null);
    jPanelWhite2.add(txtPlaca, null);
    jPanelWhite2.add(txtDniMot, null);
    jPanelWhite2.add(txtDirecMot, null);
    jPanelWhite2.add(txtApeMMot, null);
    jPanelWhite2.add(txtApePMot, null);
    jPanelWhite2.add(txtNomMot, null);
    jPanelWhite2.add(jLabelOrange8, null);
    jPanelWhite2.add(jLabelOrange7, null);
    jPanelWhite2.add(jLabelOrange6, null);
    jPanelWhite2.add(jLabelOrange5, null);
    jPanelWhite2.add(jLabelOrange4, null);
    jPanelWhite2.add(jLabelOrange3, null);
    jPanelWhite2.add(jLabelOrange2, null);
        jPanelWhite1.add(lblCerrar, null);
        jPanelWhite1.add(lblAceptar, null);
        jPanelWhite1.add(jPanelWhite2, null);
    this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
  }
  
  /*METODOS DE INICIALIZACION*/
  private void initialize()
  {
    if(VariablesMotorizados.vAccion.equalsIgnoreCase(ConstantsMotorizados.ACCION_MODIFICAR))
      mostrarDatosMot();
    
  }
  
  /*METODOS DE EVENTOS*/
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtNomMot);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.", txtApePMot);
  }

  private void btnNomMot_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtNomMot);
  }
  
  private void txtNomMot_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			txtNomMot.setText(txtNomMot.getText().toUpperCase());
			FarmaUtility.moveFocus(txtApePMot);
		}else
      chkKeyPressed(e);
  }

  private void txtApePMot_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			txtApePMot.setText(txtApePMot.getText().toUpperCase());
			FarmaUtility.moveFocus(txtApeMMot);
		}else
      chkKeyPressed(e);
  }

  private void txtApeMMot_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			txtApeMMot.setText(txtApeMMot.getText().toUpperCase());
			FarmaUtility.moveFocus(txtDirecMot);
		}else
      chkKeyPressed(e);
  }

  private void txtDirecMot_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			txtDirecMot.setText(txtDirecMot.getText().toUpperCase());
			FarmaUtility.moveFocus(txtDniMot);
		}else
      chkKeyPressed(e);
  }

  private void txtDniMot_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) 
			FarmaUtility.moveFocus(txtPlaca);
		else
      chkKeyPressed(e);
  }

  private void txtPlaca_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			txtPlaca.setText(txtPlaca.getText().toUpperCase());
			FarmaUtility.moveFocus(txtNextel);
		}else
      chkKeyPressed(e);
  }

  private void txtNextel_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
			FarmaUtility.moveFocus(txtFecNac);
		else
      chkKeyPressed(e);
  }

  private void txtFecNac_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
			FarmaUtility.moveFocus(txtAlias);
		else
      chkKeyPressed(e);
  }

  private void txtNomMot_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirLetras(txtNomMot,e);
  }

  private void txtApePMot_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirLetras(txtApePMot,e);
  }

  private void txtApeMMot_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirLetras(txtApeMMot,e);
  }

  private void txtDniMot_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtDniMot,e);
  }

  private void txtFecNac_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtFecNac,e);
  } 
  
  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_F11)
    {
      guardaDatos();
      if(!validaDatos())
        return;
      
      

      if (JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro que desea grabar los datos del motorizado?"))
      {
        if(VariablesMotorizados.vAccion.equalsIgnoreCase(ConstantsMotorizados.ACCION_INSERTAR))
          insertarMotorizado();
        else if(VariablesMotorizados.vAccion.equalsIgnoreCase(ConstantsMotorizados.ACCION_MODIFICAR))
          modificarMotorizado();        
      }else
      {
        FarmaUtility.showMessage(this,"Se canceló la operación.",txtNomMot);
      }
      
    }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
      cerrarVentana(false);
  }
  
  /*METODOS DE LOGICA DE NEGOCIO*/
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }  
  
  private void mostrarDatosMot()
  {
    txtApePMot.setText(VariablesMotorizados.vApePatMot);
    txtApeMMot.setText(VariablesMotorizados.vApeMatMot);
    txtNomMot.setText(VariablesMotorizados.vNomMot);
    txtDniMot.setText(VariablesMotorizados.vDNI);
    txtPlaca.setText(VariablesMotorizados.vPlaca);
    txtNextel.setText(VariablesMotorizados.vNumNextel);
    txtFecNac.setText(VariablesMotorizados.vFecNac);
    txtDirecMot.setText(VariablesMotorizados.vDireccion);
    /**
     * Para el Alias Y Codigo de Local
     * @author : dubilluz
     * @since  : 09.08.2007
     */
    txtAlias.setText(VariablesMotorizados.vAlias);
  }
  
  private void insertarMotorizado()
  {
    
    try
    {
      String vNom       = txtNomMot.getText();
      String vApePat    = txtApePMot.getText();
      String vApeMat    = txtApeMMot.getText();
      String vDNI       = txtDniMot.getText();
      String vPlaca     = txtPlaca.getText();
      String vNumNextel = txtNextel.getText();
      String vFecNac    = txtFecNac.getText();
      String vDireccion = txtDirecMot.getText();
      /**
       * Para el Alias y Local de Referencia
       * @author : dubilluz
       * @since  : 09.08.2007
       */
      String vAlias    = txtAlias.getText();
      String vCodLocal_referencia = FarmaVariables.vCodLocal;

      VariablesMotorizados.vCodMot = DBMotorizados.ingresaMotorizado(vNom,vApePat,vApeMat,vDNI,vPlaca,
                                                                      vNumNextel,vFecNac,vDireccion,
                                                                      //
                                                                      vAlias,vCodLocal_referencia); 
      
      FarmaUtility.aceptarTransaccion();
      FarmaUtility.showMessage(this,"Se registró satisfactoriamente al motorizado.",txtNomMot);
      
      cerrarVentana(true);
      
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
        log.error("",sql);
      FarmaUtility.showMessage(this,"No se pudo realizar la operación.",txtNomMot);      
    }
  }
  
  private void modificarMotorizado()
  {
    
    try
    {
      String vNom       = txtNomMot.getText();
      String vApePat    = txtApePMot.getText();
      String vApeMat    = txtApeMMot.getText();
      String vDNI       = txtDniMot.getText();
      String vPlaca     = txtPlaca.getText();
      String vNumNextel = txtNextel.getText();
      String vFecNac    = txtFecNac.getText();
      String vDireccion = txtDirecMot.getText();
      /**
       * Para el Alias y Local de Referencia
       * @author : dubilluz
       * @since  : 09.08.2007
       */
      String vAlias    = txtAlias.getText();
      String vCodLocal_referencia = FarmaVariables.vCodLocal;
      
      DBMotorizados.modificaMotorizado(VariablesMotorizados.vCodMot,vNom,vApePat,vApeMat,vDNI,vPlaca,
                                      vNumNextel,vFecNac,vDireccion,
                                      //
                                      vAlias,vCodLocal_referencia); 
      
      FarmaUtility.aceptarTransaccion();
      FarmaUtility.showMessage(this,"Se modificó satisfactoriamente al motorizado.",txtNomMot);
      
      cerrarVentana(true);
      VariablesMotorizados.vCodMot="";
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
        log.error("",sql);
      FarmaUtility.showMessage(this,"No se pudo realizar la operación.",txtNomMot);
    }
  }
  
  private void guardaDatos()
  {
    VariablesMotorizados.vNomMot    = txtNomMot.getText().trim();
    VariablesMotorizados.vApePatMot = txtApePMot.getText().trim();
    VariablesMotorizados.vApeMatMot = txtApeMMot.getText().trim();
    VariablesMotorizados.vDNI       = txtDniMot.getText().trim();
    VariablesMotorizados.vPlaca     = txtPlaca.getText().trim();
    VariablesMotorizados.vNumNextel = txtNextel.getText().trim();
    VariablesMotorizados.vFecNac    = txtFecNac.getText().trim();
    VariablesMotorizados.vDireccion = txtDirecMot.getText().trim();
    /**
     * Variables PAra el Alias y CodLocal
     * @author : dubilluz
     * @since  : 09.08.2007
     */
   VariablesMotorizados.vAlias    = txtAlias.getText().trim();
   VariablesMotorizados.vCodLocalAtencion = FarmaVariables.vCodLocal;
  }
  
  private boolean validaDatos()
  {
    if(VariablesMotorizados.vNomMot.length()==0)
    {
      FarmaUtility.showMessage(this, "Ingrese el nombre del motorizado.", txtNomMot);
      return false;
    }
    else if(VariablesMotorizados.vApePatMot.length()==0)
    {
      FarmaUtility.showMessage(this, "Ingrese el apellido paterno del motorizado.", txtApePMot);
      return false;
    }
    else if(VariablesMotorizados.vDNI.length()!=8)
    {
      FarmaUtility.showMessage(this, "Ingrese un DNI correcto.", txtDniMot);
      return false;
    }
    else if(VariablesMotorizados.vAlias.length()==0)
    {
      FarmaUtility.showMessage(this, "Ingrese un Alias al Mototizado.", txtAlias);
      return false;
    }   
    return true;
  }

  private void txtFecNac_keyReleased(KeyEvent e)
  {
    FarmaUtility.dateComplete(txtFecNac,e);
  }
  
  private void txtAlias_keyPressed(KeyEvent e)
  { 
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
     {
       FarmaUtility.moveFocus(txtNomMot);
     } 
     chkKeyPressed(e);
  }
  
 
}