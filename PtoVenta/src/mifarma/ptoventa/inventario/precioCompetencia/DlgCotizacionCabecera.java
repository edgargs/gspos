package mifarma.ptoventa.inventario.precioCompetencia;

import com.gs.mifarma.componentes.JLabelFunction;
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

import java.sql.*;

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.inventario.reference.*;
import mifarma.ptoventa.inventario.precioCompetencia.reference.*;
import mifarma.ptoventa.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgCotizacionCabecera extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgCotizacionCabecera.class);

  Frame myParentFrame;
  UtilityPrecioCompetencia utilityPrecioCompetencia = new UtilityPrecioCompetencia();
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JPanel pnlHeader = new JPanel();
  private JLabel lblFecha = new JLabel();
  private JLabel lblFecha_T = new JLabel();
  private JPanel pnlWhite = new JPanel();
    private JComboBox cmbCompetidor = new JComboBox();
  private JButton btnCompetidor = new JButton();
    private JComboBox cmbTipoCotizacion = new JComboBox();
  private JTextFieldSanSerif txtFecha = new JTextFieldSanSerif();
  private JButton btnFecha = new JButton();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel lblTipoCotizacion_T = new JLabel();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgCotizacionCabecera()
  {
    this(null, "", false);
  }

  public DlgCotizacionCabecera(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(411, 237));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Registro de Cotizaci\u00f3n - Encuesta");
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
    jContentPane.setBackground(Color.white);
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(405, 229));
    pnlHeader.setBounds(new Rectangle(10, 10, 385, 30));
    pnlHeader.setBackground(new Color(43, 141, 39));
    pnlHeader.setLayout(null);
    lblFecha.setBounds(new Rectangle(65, 5, 150, 20));
    lblFecha.setFont(new Font("SansSerif", 1, 11));
    lblFecha.setForeground(Color.white);
    lblFecha_T.setText("Fecha :");
    lblFecha_T.setBounds(new Rectangle(10, 5, 55, 20));
    lblFecha_T.setFont(new Font("SansSerif", 1, 11));
    lblFecha_T.setForeground(Color.white);
    pnlWhite.setBounds(new Rectangle(10, 40, 385, 115));
    pnlWhite.setBackground(Color.white);
    pnlWhite.setLayout(null);
    pnlWhite.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    cmbCompetidor.setBounds(new Rectangle(160, 75, 170, 20));
    cmbCompetidor.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          cmbCompetidor_keyPressed(e);
        }
      });
    btnCompetidor.setText("Competidor :");
    btnCompetidor.setBounds(new Rectangle(5, 75, 125, 20));
    btnCompetidor.setBackground(new Color(255, 130, 14));
    btnCompetidor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnCompetidor.setBorderPainted(false);
    btnCompetidor.setContentAreaFilled(false);
    btnCompetidor.setDefaultCapable(false);
    btnCompetidor.setFocusPainted(false);
    btnCompetidor.setFont(new Font("SansSerif", 1, 11));
    btnCompetidor.setHorizontalAlignment(SwingConstants.LEFT);
    btnCompetidor.setRequestFocusEnabled(false);
    btnCompetidor.setForeground(new Color(255, 130, 14));
    cmbTipoCotizacion.setBounds(new Rectangle(160, 45, 170, 20));
    cmbTipoCotizacion.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    cmbTipoCotizacion_keyPressed(e);
                }
      });
    txtFecha.setBounds(new Rectangle(160, 15, 105, 20));
    txtFecha.setEditable(false);
    btnFecha.setText("Fecha  :");
    btnFecha.setBounds(new Rectangle(5, 15, 100, 20));
    btnFecha.setBackground(new Color(255, 130, 14));
    btnFecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnFecha.setBorderPainted(false);
    btnFecha.setContentAreaFilled(false);
    btnFecha.setDefaultCapable(false);
    btnFecha.setFocusPainted(false);
    btnFecha.setFont(new Font("SansSerif", 1, 11));
    btnFecha.setForeground(new Color(255, 130, 14));
    btnFecha.setHorizontalAlignment(SwingConstants.LEFT);
    btnFecha.setMnemonic('f');
    btnFecha.setRequestFocusEnabled(false);
    btnFecha.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnFecha_actionPerformed(e);
        }
      });
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(200, 170, 90, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(305, 170, 90, 20));
    pnlHeader.add(lblFecha, null);
    pnlHeader.add(lblFecha_T, null);
    pnlWhite.add(lblTipoCotizacion_T, null);
    pnlWhite.add(cmbCompetidor, null);
    pnlWhite.add(btnCompetidor, null);
    pnlWhite.add(cmbTipoCotizacion, null);
    pnlWhite.add(txtFecha, null);
    pnlWhite.add(btnFecha, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    jContentPane.add(lblEsc, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(pnlHeader, null);
    jContentPane.add(pnlWhite, null);
    txtFecha.setLengthText(10);
    lblTipoCotizacion_T.setText("Observaci\u00f3n :");
    lblTipoCotizacion_T.setBounds(new Rectangle(5, 45, 145, 20));
    lblTipoCotizacion_T.setFont(new Font("SansSerif", 1, 11));
    lblTipoCotizacion_T.setForeground(new Color(255, 130, 14));
    }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    initCmbTipoCotizacion();
    initCmbCompetidor();
    initDatos();
    
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initCmbTipoCotizacion()
  {
    ArrayList<String> codigos = new ArrayList<String>(); 
    ArrayList<String> descripciones = new ArrayList<String>();
    utilityPrecioCompetencia.cargaComboTipoPrecioDAO(codigos, descripciones);
    FarmaLoadCVL.loadCVLfromArrays(cmbTipoCotizacion, ConstantsPrecioCompetencia.NOM_HASHTABLE_CMBTIPOCOTIZACION, codigos.toArray(new String[]{}), descripciones.toArray(new String[]{}), true);
  }
  
  private void initCmbCompetidor()
  {
    ArrayList<String> codigos = new ArrayList<String>(); 
    ArrayList<String> descripciones = new ArrayList<String>(); 
    utilityPrecioCompetencia.cargaComboCompetidorDAO(codigos, descripciones);
    FarmaLoadCVL.loadCVLfromArrays(cmbCompetidor,ConstantsPrecioCompetencia.NOM_HASHTABLE_CMBCOMPETIDOR, codigos.toArray(new String[]{}), descripciones.toArray(new String[]{}), true);
  }
  
  private void initDatos()
  {
    try
    { 
      lblFecha.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA));
      txtFecha.setText(lblFecha.getText().substring(0,10));  
    }catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener la fecha del sistema :\n"+sql.getMessage(),null);
    }
    if(VariablesInventario.vFecGuia.trim().equals(""))
      txtFecha.setText(lblFecha.getText().substring(0,10));
    else
      txtFecha.setText(VariablesInventario.vFecGuia);
    FarmaLoadCVL.setSelectedValueInComboBox(cmbTipoCotizacion,ConstantsInventario.NOM_HASHTABLE_CMBTIPO,VariablesInventario.vTipoDoc);
    FarmaLoadCVL.setSelectedValueInComboBox(cmbCompetidor,ConstantsInventario.NOM_HASHTABLE_CMBGUIA,VariablesInventario.vTipoOrigen);
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(cmbTipoCotizacion);
  }

  private void btnFecha_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbTipoCotizacion);
  }

  private void cmbTipoCotizacion_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
      FarmaUtility.moveFocus(cmbCompetidor);
    else
      chkKeyPressed(e);
  }
    
  private void cmbCompetidor_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
        FarmaUtility.moveFocus(cmbCompetidor);
    else
      chkKeyPressed(e);
  }
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(UtilityPtoVenta.verificaVK_F11(e))
    {
          cargarDatos();
          cerrarVentana(true);
      
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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
  
  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */ 
  private void cargarDatos()
  {
    //cabecera
    VariablesPrecioCompetencia.vFec = txtFecha.getText();
    VariablesPrecioCompetencia.vCodTipoCotizacion = FarmaLoadCVL.getCVLCode(ConstantsPrecioCompetencia.NOM_HASHTABLE_CMBTIPOCOTIZACION,cmbTipoCotizacion.getSelectedIndex());
    VariablesPrecioCompetencia.vCodCompetidor = FarmaLoadCVL.getCVLCode(ConstantsPrecioCompetencia.NOM_HASHTABLE_CMBCOMPETIDOR,cmbCompetidor.getSelectedIndex());
    VariablesPrecioCompetencia.vDescTipoCotizacion = FarmaLoadCVL.getCVLDescription(ConstantsPrecioCompetencia.NOM_HASHTABLE_CMBTIPOCOTIZACION,VariablesPrecioCompetencia.vCodTipoCotizacion);
    VariablesPrecioCompetencia.vDescCompetidor = FarmaLoadCVL.getCVLDescription(ConstantsPrecioCompetencia.NOM_HASHTABLE_CMBCOMPETIDOR,VariablesPrecioCompetencia.vCodCompetidor);  
  }
  
}