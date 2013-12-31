package mifarma.ptoventa.inventario;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
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

import java.sql.*;

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.*;
import mifarma.ptoventa.cliente.reference.*;
import mifarma.ptoventa.inventario.reference.*;
import mifarma.ptoventa.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgMercaderiaDirectaCabecera extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgMercaderiaDirectaCabecera.class);

  Frame myParentFrame;
  
  private BorderLayout borderLayout1        = new BorderLayout();
  private JPanel jContentPane               = new JPanel();
  private JPanel pnlHeader                  = new JPanel();
  private JLabel lblFecha                   = new JLabel();
  private JLabel lblFecha_T                 = new JLabel();
  private JPanel pnlWhite                   = new JPanel();
  private JTextFieldSanSerif txtNroGuia     = new JTextFieldSanSerif();
  private JComboBox cmbTipo                 = new JComboBox();
  private JTextFieldSanSerif txtFechaDocum  = new JTextFieldSanSerif();
  private JButton btnFechaGuia              = new JButton();
  private JLabelFunction lblF11             = new JLabelFunction();
  private JLabelFunction lblEsc             = new JLabelFunction();
  private JLabelOrange lblNombre            = new JLabelOrange();
  private JButton btnRuc                    = new JButton();
  private JTextFieldSanSerif txtNombre      = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtRuc         = new JTextFieldSanSerif();
  private JLabel lblNroDoc_T                = new JLabel();
  private JTextFieldSanSerif txtSerieDoc    = new JTextFieldSanSerif();
  private JLabelOrange jLabelOrange1        = new JLabelOrange();
  private JLabelOrange lblTotalMonto        = new JLabelOrange();
  private JTextFieldSanSerif txtMontoTotal  = new JTextFieldSanSerif();
  private JLabelFunction jLabelFunction1    = new JLabelFunction();
  private FacadeInventario facadeInventario = new FacadeInventario();

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgMercaderiaDirectaCabecera() {
    this(null, "", false);
  }

  public DlgMercaderiaDirectaCabecera(Frame parent, String title, boolean modal) {
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
    this.setSize(new Dimension(411, 306));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Cabecera Ord. Comp. - Mercaderia Directa");
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
    pnlWhite.setBounds(new Rectangle(10, 40, 385, 185));
    pnlWhite.setBackground(Color.white);
    pnlWhite.setLayout(null);
    pnlWhite.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    txtNroGuia.setBounds(new Rectangle(320, 45, 55, 20));
    txtNroGuia.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtNroGuia_keyPressed(e);
        }

          public void keyTyped(KeyEvent e)
          {
            txtNroGuia_keyTyped(e);
          }
      });
    cmbTipo.setBounds(new Rectangle(140, 45, 105, 20));
    cmbTipo.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    cmbTipo_keyPressed(e);
                }
      });
    txtFechaDocum.setBounds(new Rectangle(140, 15, 105, 20));
    txtFechaDocum.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaGuia_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaGuia_keyReleased(e);
        }

      });
    btnFechaGuia.setText("Fecha  :");
    btnFechaGuia.setBounds(new Rectangle(5, 15, 100, 20));
    btnFechaGuia.setBackground(new Color(255, 130, 14));
    btnFechaGuia.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnFechaGuia.setBorderPainted(false);
    btnFechaGuia.setContentAreaFilled(false);
    btnFechaGuia.setDefaultCapable(false);
    btnFechaGuia.setFocusPainted(false);
    btnFechaGuia.setFont(new Font("SansSerif", 1, 11));
    btnFechaGuia.setForeground(new Color(255, 130, 14));
    btnFechaGuia.setHorizontalAlignment(SwingConstants.LEFT);
    btnFechaGuia.setMnemonic('f');
    btnFechaGuia.setRequestFocusEnabled(false);
    btnFechaGuia.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnFechaGuia_actionPerformed(e);
        }
      });
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(10, 240, 90, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(305, 240, 90, 20));
        lblNombre.setText("Proveedor :");
    lblNombre.setBounds(new Rectangle(5, 110, 100, 20));
    btnRuc.setText("C\u00f3digo :");
    btnRuc.setBounds(new Rectangle(5, 80, 100, 20));
    btnRuc.setBackground(new Color(255, 130, 14));
    btnRuc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnRuc.setBorderPainted(false);
    btnRuc.setContentAreaFilled(false);
    btnRuc.setDefaultCapable(false);
    btnRuc.setFocusPainted(false);
    btnRuc.setFont(new Font("SansSerif", 1, 11));
    btnRuc.setForeground(new Color(255, 130, 14));
    btnRuc.setHorizontalAlignment(SwingConstants.LEFT);
    btnRuc.setRequestFocusEnabled(false);
    btnRuc.setMnemonic('R');
    btnRuc.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
            btnNombre_actionPerformed(e);
          }
      });
    txtNombre.setBounds(new Rectangle(140, 110, 235, 20));
    txtNombre.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtNombre_keyPressed(e);
        }
      });
        txtRuc.setBounds(new Rectangle(140, 80, 120, 20));
    txtRuc.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtRuc_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
          txtRuc_keyTyped(e);
        }
      });
        pnlHeader.add(lblFecha, null);
    pnlHeader.add(lblFecha_T, null);
        pnlWhite.add(txtMontoTotal, null);
        pnlWhite.add(lblTotalMonto, null);
        pnlWhite.add(jLabelOrange1, null);
    pnlWhite.add(txtSerieDoc, null);
    pnlWhite.add(lblNroDoc_T, null);
        pnlWhite.add(txtNroGuia, null);
        pnlWhite.add(cmbTipo, null);
        pnlWhite.add(txtFechaDocum, null);
        pnlWhite.add(btnFechaGuia, null);
        pnlWhite.add(lblNombre, null);
        pnlWhite.add(txtNombre, null);
        pnlWhite.add(btnRuc, null);
        pnlWhite.add(txtRuc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlWhite, null);
        //
        txtFechaDocum.setLengthText(10);
    txtSerieDoc.setLengthText(3);;
    txtNroGuia.setLengthText(7);

        txtNombre.setLengthText(20);
        txtNombre.setEditable(false);
        txtRuc.setLengthText(11);
        txtRuc.setEditable(false);
        lblNroDoc_T.setText("Nro documento :");
    lblNroDoc_T.setBounds(new Rectangle(5, 45, 130, 20));
    lblNroDoc_T.setFont(new Font("SansSerif", 1, 11));
    lblNroDoc_T.setForeground(new Color(255, 130, 14));
    txtSerieDoc.setBounds(new Rectangle(275, 45, 30, 20));
    txtSerieDoc.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtSerieDoc_keyPressed(e);
          }

          public void keyTyped(KeyEvent e)
          {
            txtSerieDoc_keyTyped(e);
          }
        });
    jLabelOrange1.setText("-");
    jLabelOrange1.setBounds(new Rectangle(305, 45, 15, 20));
    jLabelOrange1.setFont(new Font("SansSerif", 1, 25));
    jLabelOrange1.setHorizontalAlignment(SwingConstants.CENTER);
    lblTotalMonto.setText("Monto Orden de Compra :");
    lblTotalMonto.setBounds(new Rectangle(5, 150, 150, 20));
    txtMontoTotal.setBounds(new Rectangle(165, 150, 95, 20));
    txtMontoTotal.setLengthText(11);
    txtMontoTotal.setEditable(false);
        jLabelFunction1.setBounds(new Rectangle(130, 240, 140, 20));
        jLabelFunction1.setText("[ F4 ] Aceptar Cambio");


    }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize() {
    initCmbTipo();
    initDatos();
    
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initCmbTipo() {
    ArrayList parametros = new ArrayList(); 
    parametros.add(FarmaVariables.vCodGrupoCia);
    FarmaLoadCVL.loadCVLFromSP(cmbTipo,ConstantsInventario.NOM_HASHTABLE_CMBTIPO,"PTOVENTA_INV.INV_GET_TIPO_DOCUMENTO(?)",parametros,false);  
    parametros = null;
  }
  
   
  private void initDatos() {
    try {
      lblFecha.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA));
    }catch(SQLException sql) {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener la fecha del sistema :\n"+sql.getMessage(),null);
    }
    if(VariablesInventario.vFecGuia.trim().equals(""))
      txtFechaDocum.setText(lblFecha.getText().substring(0,10));
    else
      txtFechaDocum.setText(VariablesInventario.vFecGuia);
    FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo,ConstantsInventario.NOM_HASHTABLE_CMBTIPO,VariablesInventario.vTipoDoc);
    txtRuc.setText(VariablesInventario.vRUCProveedor);
    txtNombre.setText(VariablesInventario.vDescProveedor); 
    txtMontoTotal.setText(VariablesInventario.vImporteTotal);
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e) {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtFechaDocum);
  }

  private void btnFechaGuia_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(txtFechaDocum);
  }

  private void txtFechaGuia_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
      FarmaUtility.moveFocus(cmbTipo);
    else
      chkKeyPressed(e);
  }

  private void txtFechaGuia_keyReleased(KeyEvent e) {
    FarmaUtility.dateComplete(txtFechaDocum,e);
  }

  private void cmbTipo_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
      FarmaUtility.moveFocus(txtSerieDoc);
    else
      chkKeyPressed(e);
  }
  
  private void txtSerieDoc_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      FarmaUtility.moveFocus(txtNroGuia);
      txtSerieDoc.setText(FarmaUtility.completeWithSymbol(txtSerieDoc.getText(),3,"0","I"));
    }
    else
      chkKeyPressed(e);
  }
  
  private void txtSerieDoc_keyTyped(KeyEvent e) {
    FarmaUtility.admitirDigitos(txtSerieDoc,e);
  }
  
  private void txtNroGuia_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      FarmaUtility.moveFocus(txtNroGuia);
      txtNroGuia.setText(FarmaUtility.completeWithSymbol(txtNroGuia.getText(),7,"0","I"));
    }
    else
      chkKeyPressed(e);
  }

  private void txtNroGuia_keyTyped(KeyEvent e) {
    FarmaUtility.admitirDigitos(txtNroGuia,e);
  }
  
  private void btnNombre_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(txtRuc);  
  }
  
  private void txtNombre_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      FarmaUtility.moveFocus(txtFechaDocum);
    }
    else
      chkKeyPressed(e);
    }

  private void txtRuc_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      if(txtNombre.getText().trim().length()>0){
            if(txtRuc.getText().trim().length()>0)
                FarmaUtility.moveFocus(txtNombre);
        }else
            chkKeyPressed(e);
    }
  }

  private void txtRuc_keyTyped(KeyEvent e) {
    FarmaUtility.admitirDigitos(txtRuc,e);
  }  

  private void this_windowClosing(WindowEvent e){
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
    private void cerrarVentana(boolean pAceptar) {
      this.setVisible(false);
      this.dispose();
    }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e) {
    if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
      if(validarCampos()) {
         VariablesInventario.vDescripDocum   = String.valueOf(cmbTipo.getSelectedItem()).trim();
         VariablesInventario.vFechIngreso    = txtFechaDocum.getText().trim();
         VariablesInventario.vSerieDocument  = txtSerieDoc.getText().trim();
         VariablesInventario.vNumeroDocument = txtNroGuia.getText().trim();
         VariablesInventario.vIdeDocumento   = String.valueOf(cmbTipo.getSelectedIndex()).trim();
         cerrarVentana(true);
         DlgMercaderiaDirectaProducto dlgMercaderiaDirectaProducto = new DlgMercaderiaDirectaProducto(myParentFrame, "", true);
          dlgMercaderiaDirectaProducto.setFacade(facadeInventario);
         dlgMercaderiaDirectaProducto.setVisible(true);
         if(FarmaVariables.vAceptar) {
             FarmaVariables.vAceptar = false;
         }      
      }
    }else if(e.getKeyCode() == KeyEvent.VK_F4){
        VariablesInventario.vDescripDocum   = String.valueOf(cmbTipo.getSelectedItem()).trim();
        VariablesInventario.vFechIngreso    = txtFechaDocum.getText().trim();
        VariablesInventario.vSerieDocument  = txtSerieDoc.getText().trim();
        VariablesInventario.vNumeroDocument = txtNroGuia.getText().trim();
        VariablesInventario.vIdeDocumento   = String.valueOf(cmbTipo.getSelectedIndex()).trim();
        cerrarVentana(true);       
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        cerrarVentana(true);       
    }
           
  }  
  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */

  private void procesoMDirecta() {
      ArrayList arrayMDirecta = new ArrayList();
      VariablesInventario.vFechIngreso  = txtFechaDocum.getText();
      VariablesInventario.vDescripDocum = FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBGUIA,cmbTipo.getSelectedIndex());
  }
  private boolean validarCampos() {
    boolean retorno = true;
    txtSerieDoc.setText(FarmaUtility.completeWithSymbol(txtSerieDoc.getText(),3,"0","I"));
    txtNroGuia.setText(FarmaUtility.completeWithSymbol(txtNroGuia.getText(),7,"0","I"));
       
    if(txtFechaDocum.getText().trim().length()==0) {
      FarmaUtility.showMessage(this,"Debe ingresar la Fecha del Documento.",txtFechaDocum);
      retorno = false;
    }else if(!FarmaUtility.validaFecha(txtFechaDocum.getText(),"dd/MM/yyyy")) {
      FarmaUtility.showMessage(this,"Ingrese la fecha correctamente.",txtFechaDocum);
      retorno = false;
    }else if(cmbTipo.getSelectedIndex() == 0 || cmbTipo.getSelectedIndex() == -1) {
      FarmaUtility.showMessage(this,"Debe indicar el Tipo de Documento.",cmbTipo);
      retorno = false;
    }else if(txtSerieDoc.getText().trim().length()==0) {
      FarmaUtility.showMessage(this,"Debe ingresar la Serie del Documento.",txtSerieDoc);
      retorno = false;
    }else if(txtSerieDoc.getText().equals("000") || txtSerieDoc.getText().trim().length()!=3) {
      FarmaUtility.showMessage(this,"Debe ingresar la Serie del Documento, correctamente.",txtSerieDoc);
      retorno = false;
    }else if(txtNroGuia.getText().trim().length()==0) {
      FarmaUtility.showMessage(this,"Debe ingresar el Numero del Documento.",txtNroGuia);
      retorno = false;
    }else if(txtNroGuia.getText().trim().equals("0000000") || txtNroGuia.getText().trim().length()!=7) {
      FarmaUtility.showMessage(this,"Debe ingresar el Numero del Documento, correctamente.",txtNroGuia);
      retorno = false;
    }else if(txtNombre.getText().trim().length()==0) {
        FarmaUtility.showMessage(this,"Debe ingresar el Nombre de la Tienda.",txtRuc);
        retorno = false;
      }else if(txtRuc.getText().trim().length()==0 || txtRuc.getText().trim().length()<11) {
        FarmaUtility.showMessage(this,"Debe ingresar el Ruc, correctamente.",txtRuc);
        retorno = false;
      }else if(UtilityInventario.verificaRucValido(txtRuc.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)) {
        FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRuc);
        retorno = false;
     /* }else if(validaFechaLimiteCC()){
        FarmaUtility.showMessage(this,"La fecha de ingreso es menor a la fecha permitida. Verifique",txtFechaDocum);
        retorno = false;   */     
      }else if(!validaFechaIngresoCC()) retorno = false ; 
      return retorno;
  }
  
  private void cargarDatos() {
    VariablesInventario.vFechIngreso    = txtFechaDocum.getText();
    VariablesInventario.vTipoDocument   = FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO,cmbTipo.getSelectedIndex()).trim();
    VariablesInventario.vDescDoc        = FarmaLoadCVL.getCVLDescription(ConstantsInventario.NOM_HASHTABLE_CMBTIPO,VariablesInventario.vTipoDoc);
    VariablesInventario.vNumDoc         = txtSerieDoc.getText() + " - " + txtNroGuia.getText();
    VariablesInventario.vSerieDocument  = txtSerieDoc.getText();
    VariablesInventario.vNumeroDocument = txtNroGuia.getText();
    VariablesInventario.vNombreTienda   = txtNombre.getText().trim();
    VariablesInventario.vRucTienda      = txtRuc.getText().trim();
  }
  private boolean validaFechaIngresoCC() {
    String diaVenta = txtFechaDocum.getText().trim();
    if( !FarmaUtility.isFechaValida(this, diaVenta, "Ingrese una Fecha correcta para la generación de la guia") ) {
      FarmaUtility.moveFocus(txtFechaDocum);
      return false;
    }
    return true;
  } 
  
   public void modifyHead(KeyEvent e){
        cmbTipo.setSelectedItem(VariablesInventario.vDescripDocum);
        txtFechaDocum.setText(VariablesInventario.vFechIngreso);
        txtSerieDoc.setText(VariablesInventario.vSerieDocument);
        txtNroGuia.setText(VariablesInventario.vNumeroDocument);
        cmbTipo.setSelectedIndex(Integer.parseInt(VariablesInventario.vIdeDocumento));
        
        lblF11.setVisible(false);
    }

    void setFacade(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }
}

