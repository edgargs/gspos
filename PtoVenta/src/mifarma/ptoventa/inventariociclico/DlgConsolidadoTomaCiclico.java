package mifarma.ptoventa.inventariociclico;

import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import java.awt.Rectangle;
import com.gs.mifarma.componentes.JPanelWhite;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import com.gs.mifarma.componentes.JButtonLabel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import com.gs.mifarma.componentes.JLabelOrange;
import mifarma.common.*;
import java.sql.*;
import java.util.*;
import mifarma.ptoventa.inventariociclico.reference.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import com.gs.mifarma.componentes.JLabelFunction;
import java.awt.event.KeyListener;
import com.gs.mifarma.componentes.JButtonFunction;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgConsolidadoTomaCiclico extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgConsolidadoTomaCiclico.class);

  Frame myParentFrame;
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelWhite jPanelWhite2 = new JPanelWhite();
  private JLabelOrange lblTotalItemsTomaT = new JLabelOrange();
  private JLabelOrange lblTotalItemsInventaT = new JLabelOrange();
  private JLabelOrange lblInforamcion = new JLabelOrange();
  private JLabelOrange lblSobranteT = new JLabelOrange();
  private JLabelOrange lblFaltanteT = new JLabelOrange();
  private JLabelOrange lblDiferenciaT = new JLabelOrange();
  private JLabelOrange lblTotalItemsToma = new JLabelOrange();
  private JLabelOrange lblTotalItemsInventa = new JLabelOrange();
  private JLabelOrange lblDiferencia = new JLabelOrange();
  private JLabelOrange lblFaltante = new JLabelOrange();
  private JLabelOrange lblSobrante = new JLabelOrange();
  private JLabelFunction lblEscape = new JLabelFunction();
  private JLabelOrange lblNumeroToma = new JLabelOrange();
  private JButtonLabel btnNumeroToma = new JButtonLabel();

  public DlgConsolidadoTomaCiclico()
  {
    this(null, "", false);
  }

  public DlgConsolidadoTomaCiclico(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(357, 310));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Consolidado de la Toma");
    this.addWindowListener(new java.awt.event.WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    jPanelWhite2.setBounds(new Rectangle(10, 35, 325, 205));
    jPanelWhite2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanelWhite2.setLayout(null);
    lblTotalItemsTomaT.setText("Total Items de la Toma :");
    lblTotalItemsTomaT.setBounds(new Rectangle(20, 20, 150, 20));
    lblTotalItemsTomaT.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          lblTotalItemsTomaT_keyPressed(e);
        }
      });
    lblTotalItemsInventaT.setText("Total Items Inventariados :");
    lblTotalItemsInventaT.setBounds(new Rectangle(20, 55, 150, 20));
    lblInforamcion.setText("Informacion Valorizada :");
    lblInforamcion.setBounds(new Rectangle(20, 95, 150, 20));
    lblSobranteT.setText("Sobrante :    S/.");
    lblSobranteT.setBounds(new Rectangle(80, 125, 90, 15));
    lblFaltanteT.setText("Faltante :       S/.");
    lblFaltanteT.setBounds(new Rectangle(80, 150, 90, 15));
    lblDiferenciaT.setText("Diferencia :   S/.");
    lblDiferenciaT.setBounds(new Rectangle(80, 175, 90, 15));
    lblTotalItemsToma.setBounds(new Rectangle(180, 20, 70, 20));
    lblTotalItemsToma.setForeground(new Color(43, 141, 39));
    lblTotalItemsToma.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotalItemsInventa.setBounds(new Rectangle(180, 55, 70, 20));
    lblTotalItemsInventa.setForeground(new Color(43, 141, 39));
    lblTotalItemsInventa.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDiferencia.setBounds(new Rectangle(180, 175, 70, 15));
    lblDiferencia.setForeground(new Color(43, 141, 39));
    lblDiferencia.setHorizontalAlignment(SwingConstants.RIGHT);
    lblFaltante.setBounds(new Rectangle(180, 150, 70, 15));
    lblFaltante.setForeground(new Color(43, 141, 39));
    lblFaltante.setHorizontalAlignment(SwingConstants.RIGHT);
    lblSobrante.setBounds(new Rectangle(180, 125, 70, 15));
    lblSobrante.setForeground(new Color(43, 141, 39));
    lblSobrante.setHorizontalAlignment(SwingConstants.RIGHT);
    lblEscape.setBounds(new Rectangle(220, 255, 115, 20));
    lblEscape.setText("[ ESCAPE ] Cerrar");
    lblNumeroToma.setBounds(new Rectangle(95, 10, 145, 15));
    lblNumeroToma.setForeground(new Color(43, 141, 39));
    btnNumeroToma.setText("Toma Nº :");
    btnNumeroToma.setBounds(new Rectangle(20, 10, 75, 15));
    btnNumeroToma.setForeground(new Color(255, 130, 14));
    btnNumeroToma.setMnemonic('t');
    btnNumeroToma.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnNumeroToma_keyPressed(e);
        }
      });
    btnNumeroToma.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNumeroToma_actionPerformed(e);
        }
      });
    jPanelWhite2.add(lblSobrante, null);
    jPanelWhite2.add(lblFaltante, null);
    jPanelWhite2.add(lblDiferencia, null);
    jPanelWhite2.add(lblTotalItemsInventa, null);
    jPanelWhite2.add(lblTotalItemsToma, null);
    jPanelWhite2.add(lblDiferenciaT, null);
    jPanelWhite2.add(lblFaltanteT, null);
    jPanelWhite2.add(lblSobranteT, null);
    jPanelWhite2.add(lblInforamcion, null);
    jPanelWhite2.add(lblTotalItemsInventaT, null);
    jPanelWhite2.add(lblTotalItemsTomaT, null);
    jPanelWhite1.add(btnNumeroToma, null);
    jPanelWhite1.add(lblNumeroToma, null);
    jPanelWhite1.add(lblEscape, null);
    jPanelWhite1.add(jPanelWhite2, null);
    this.getContentPane().add(jPanelWhite1, null);
  }
  
  // **************************************************************************
	// Método "initialize()"
	// **************************************************************************

	private void initialize() {
		FarmaVariables.vAceptar = false;
	}

  private void jButtonLabel1_actionPerformed(ActionEvent e)
  {
  }

  private void txtCantidad_keyTyped(KeyEvent e)
  {
  }

  private void txtFraccion_keyPressed(KeyEvent e)
  {
  }

  private void txtEntero_keyPressed(KeyEvent e)
  {
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    obtieneTotalesToma();
    obtieneInformacionValorizada();
    calculaDiferencia();
    lblNumeroToma.setText(VariablesInvCiclico.vSecToma);
    FarmaUtility.moveFocus(btnNumeroToma);
  }
  
  private void obtieneTotalesToma()
  {
    try
    {
      VariablesInvCiclico.vObtieneTotales.clear();
      VariablesInvCiclico.vObtieneTotales = DBInvCiclico.obtieneTotalItemsToma(VariablesInvCiclico.vSecToma);
      VariablesInvCiclico.vTotalItems = ((String)((ArrayList)VariablesInvCiclico.vObtieneTotales.get(0)).get(0)).trim();
      VariablesInvCiclico.vTotalTomados = ((String)((ArrayList)VariablesInvCiclico.vObtieneTotales.get(0)).get(1)).trim();
      lblTotalItemsToma.setText(VariablesInvCiclico.vTotalItems);
      lblTotalItemsInventa.setText(VariablesInvCiclico.vTotalTomados);
    } catch(SQLException sql)
    {
      VariablesInvCiclico.vObtieneTotales = new ArrayList();
			log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener información de los totales : \n" + sql.getMessage(),null);
    }
  }
  
  private void obtieneInformacionValorizada()
  {
    try
      {
        VariablesInvCiclico.vObtieneInformacionValorizada.clear();
        VariablesInvCiclico.vObtieneInformacionValorizada = DBInvCiclico.obtieneInformacionValorizada(VariablesInvCiclico.vSecToma);
        VariablesInvCiclico.vFaltante = ((String)((ArrayList)VariablesInvCiclico.vObtieneInformacionValorizada.get(0)).get(0)).trim();
        VariablesInvCiclico.vSobrante = ((String)((ArrayList)VariablesInvCiclico.vObtieneInformacionValorizada.get(0)).get(1)).trim();
        lblSobrante.setText(VariablesInvCiclico.vSobrante);
        lblFaltante.setText(VariablesInvCiclico.vFaltante);
      } catch(SQLException sql)
      {
        VariablesInvCiclico.vObtieneInformacionValorizada = new ArrayList();
        log.error("",sql);
        FarmaUtility.showMessage(this,"Error al obtener información valorizada : \n" + sql.getMessage(),null);
      }
    
  }
  
  private void calculaDiferencia()
  {
    double diferencia = 0;
    double sobrante = 0;
    double faltante = 0;
    sobrante = FarmaUtility.getDecimalNumber(VariablesInvCiclico.vSobrante);
    faltante = FarmaUtility.getDecimalNumber(VariablesInvCiclico.vFaltante);
    diferencia = sobrante - faltante ;
    lblDiferencia.setText("" + FarmaUtility.formatNumber(diferencia));
                 
  }
  
  private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

  private void lblTotalItemsTomaT_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
    {
      cerrarVentana(false);  
    }
    
  }

  private void btnNumeroToma_actionPerformed(ActionEvent e)
  { 
    FarmaUtility.moveFocus(btnNumeroToma);   
  }

  private void btnNumeroToma_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
    {
      cerrarVentana(false);  
    }
  }


}