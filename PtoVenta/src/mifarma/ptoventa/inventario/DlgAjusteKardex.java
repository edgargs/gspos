package mifarma.ptoventa.inventario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import mifarma.common.*;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import java.awt.event.KeyListener;
import com.gs.mifarma.componentes.JLabelOrange;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgAjusteKardex extends JDialog {
	// **************************************************************************
	// Constructores
	// **************************************************************************
	private static final Logger log = LoggerFactory.getLogger(DlgAjusteKardex.class);

	Frame myParentFrame;

	FarmaTableModel tableModel;

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanelWhite jContentPane = new JPanelWhite();

	private JPanelHeader pnlHeader1 = new JPanelHeader();

	private JPanelTitle pnlTitle1 = new JPanelTitle();

	private JLabelWhite lblProducto_T = new JLabelWhite();

	private JLabelWhite lblUnidadActual_T = new JLabelWhite();

	private JLabelWhite lblLaboratorio_T = new JLabelWhite();

	private JLabelWhite lblProducto = new JLabelWhite();

	private JLabelWhite lblUnidad = new JLabelWhite();

	private JLabelWhite lblLaboratorio = new JLabelWhite();

	private JButtonLabel btnMotivoAjuste = new JButtonLabel();

	private JLabelWhite lblStockModif_T = new JLabelWhite();

	private JTextFieldSanSerif txtStockModifEntero = new JTextFieldSanSerif();

	private JComboBox cmbMotivoAjuste = new JComboBox();

	private JLabelFunction lblEsc = new JLabelFunction();

	private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelWhite lblGlosa_T = new JLabelWhite();
  private JTextFieldSanSerif txtGlosa = new JTextFieldSanSerif();
  private JLabelWhite lblStock_T = new JLabelWhite();
  private JLabelWhite lblStock = new JLabelWhite();
    private JLabelWhite lblUnidadVenta = new JLabelWhite();
  private JLabelWhite jLabelWhite1 = new JLabelWhite();
  private JLabelWhite jLabelWhite2 = new JLabelWhite();
  private JTextFieldSanSerif txtStockFraccion = new JTextFieldSanSerif();

	// **************************************************************************
	// Método "jbInit()"
	// **************************************************************************

	public DlgAjusteKardex() {
		this(null, "", false);
	}

	public DlgAjusteKardex(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		myParentFrame = parent;
		try {
			jbInit();
			initialize();
			mifarma.common.FarmaUtility.centrarVentana(this);
		} catch (Exception e) {
			log.error("",e);
		}
	}

	// **************************************************************************
	// Método "initialize()"
	// **************************************************************************

	private void jbInit() throws Exception {
		this.setSize(new Dimension(467, 298));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Ajuste de Producto");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
			}

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
		});
		pnlHeader1.setBounds(new Rectangle(15, 15, 425, 115));
		pnlTitle1.setBounds(new Rectangle(15, 135, 425, 105));
		lblProducto_T.setText("Producto:");
		lblProducto_T.setBounds(new Rectangle(20, 15, 90, 15));
		lblUnidadActual_T.setText("Unidad Actual:");
		lblUnidadActual_T.setBounds(new Rectangle(20, 40, 90, 15));
		lblLaboratorio_T.setText("Laboratorio:");
		lblLaboratorio_T.setBounds(new Rectangle(20, 65, 90, 15));
		lblProducto.setBounds(new Rectangle(120, 15, 300, 15));
		lblProducto.setFont(new Font("SansSerif", 0, 11));
		lblUnidad.setBounds(new Rectangle(120, 40, 295, 15));
		lblUnidad.setFont(new Font("SansSerif", 0, 11));
		lblLaboratorio.setBounds(new Rectangle(120, 65, 300, 15));
		lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
		btnMotivoAjuste.setText("Motivo de Ajuste:");
		btnMotivoAjuste.setBounds(new Rectangle(15, 15, 105, 20));
		btnMotivoAjuste.setMnemonic('M');
		btnMotivoAjuste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMotivoAjuste_actionPerformed(e);
			}
		});
		lblStockModif_T.setText("Stock Mod :");
		lblStockModif_T.setBounds(new Rectangle(15, 45, 75, 20));
		txtStockModifEntero.setBounds(new Rectangle(220, 45, 45, 20));
		txtStockModifEntero.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				txtStockModif_keyPressed(e);
			}
			public void keyTyped(KeyEvent e) {
				txtStockModif_keyTyped(e);
			}
		});
		cmbMotivoAjuste.setBounds(new Rectangle(130, 15, 275, 20));
		cmbMotivoAjuste.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				cmbMotivoAjuste_keyPressed(e);
			}
		});
		lblEsc.setText("[ ESC ] Cerrar");
		lblEsc.setBounds(new Rectangle(350, 245, 90, 20));
		lblF11.setText("[ F11 ] Aceptar");
		lblF11.setBounds(new Rectangle(235, 245, 90, 20));
    lblGlosa_T.setText("Glosa :");
    lblGlosa_T.setBounds(new Rectangle(15, 70, 90, 20));
    txtGlosa.setBounds(new Rectangle(130, 70, 280, 20));
    txtGlosa.setDocument(new FarmaLengthText(120));
    txtGlosa.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtGlosa_keyPressed(e);
        }
      });
	
    lblStock_T.setText("Stock:");
    lblStock_T.setBounds(new Rectangle(20, 90, 90, 15));
    lblStock.setBounds(new Rectangle(120, 90, 40, 15));
    lblStock.setFont(new Font("SansSerif", 0, 11));
        lblUnidadVenta.setBounds(new Rectangle(185, 90, 140, 15));
        lblUnidadVenta.setFont(new Font("SansSerif", 0, 11));
    jLabelWhite1.setText("Entero :");
    jLabelWhite1.setBounds(new Rectangle(165, 45, 55, 20));
    jLabelWhite2.setText("Fraccion");
    jLabelWhite2.setBounds(new Rectangle(285, 45, 55, 20));
    txtStockFraccion.setBounds(new Rectangle(345, 45, 45, 20));
    txtStockFraccion.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtStockFraccion_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
          txtStockFraccion_keyTyped(e);
        }
      });
    pnlTitle1.add(txtStockFraccion, null);
    pnlTitle1.add(jLabelWhite2, null);
    pnlTitle1.add(jLabelWhite1, null);
    pnlTitle1.add(txtGlosa, null);
    pnlTitle1.add(lblGlosa_T, null);
		pnlTitle1.add(cmbMotivoAjuste, null);
    pnlTitle1.add(txtStockModifEntero, null);
		pnlTitle1.add(lblStockModif_T, null);
		pnlTitle1.add(btnMotivoAjuste, null);
		jContentPane.add(lblF11, null);
		jContentPane.add(lblEsc, null);
		jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(lblUnidadVenta, null);
    pnlHeader1.add(lblStock, null);
    pnlHeader1.add(lblStock_T, null);
		pnlHeader1.add(lblLaboratorio, null);
		pnlHeader1.add(lblUnidad, null);
		pnlHeader1.add(lblProducto, null);
		pnlHeader1.add(lblLaboratorio_T, null);
		pnlHeader1.add(lblUnidadActual_T, null);
		pnlHeader1.add(lblProducto_T, null);
		jContentPane.add(pnlHeader1, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
    	}

	// **************************************************************************
	// Métodos de inicialización
	// **************************************************************************

	private void initialize() {
		initCmbMotivoAjuste();
	};

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void btnMotivoAjuste_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(cmbMotivoAjuste);
	}

	private void btnCancelar_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void txtFracModif_keyPressed(KeyEvent e) {
		chkKeyPressed(e);
	}

	private void txtStockModif_keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if(txtStockFraccion.isEnabled())
      FarmaUtility.moveFocus(txtStockFraccion);
      else
			FarmaUtility.moveFocus(txtGlosa);
		} else {
			chkKeyPressed(e);
		}
	}

	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(cmbMotivoAjuste);
     if(VariablesInventario.vCFraccion.equalsIgnoreCase("1")){
      txtStockFraccion.setEnabled(false);
      txtStockFraccion.setText("0");
     }
		mostrarDatos();
	}

	private void cmbMotivoAjuste_keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			FarmaUtility.moveFocus(txtStockModifEntero);
		} else {
			chkKeyPressed(e);
		}
	}

	private void txtStockModif_keyTyped(KeyEvent e) {
		FarmaUtility.admitirDigitos(txtStockModifEntero, e);
	}
   private void this_windowClosing(WindowEvent e)
  { FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
			if (datosValidados())
				if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
						"¿Está seguro que desea grabar el ajuste?")) {
					try {
						insertarAjusteKardex();
						FarmaUtility.aceptarTransaccion();
            VariablesInventario.vStk_ModEntero =txtStockModifEntero.getText().trim();
            VariablesInventario.vStk_ModFrac = txtStockFraccion.getText().trim();
						FarmaUtility.showMessage(this,"El ajuste se guardó correctamente",txtStockModifEntero);
					} catch (SQLException sql) {
						FarmaUtility.liberarTransaccion();
						FarmaUtility.showMessage(this,"Ocurrió un error al guardar el ajuste: \n"+ sql.getMessage(), txtStockModifEntero);
						log.error("",sql);
					}
					cerrarVentana(true);
				}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.cerrarVentana(false);
		}
	}

	// **************************************************************************
	// Metodos de lógica de negocio
	// **************************************************************************
	private void initCmbMotivoAjuste() {
		ArrayList parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		FarmaLoadCVL.loadCVLFromSP(cmbMotivoAjuste, "cmbMotivoAjuste","PTOVENTA_INV.INV_LISTA_MOT_AJUST_KRDX(?)", parametros, false);
	}

	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

	private void mostrarDatos() {
		lblProducto.setText(VariablesInventario.vDescProd);
		lblLaboratorio.setText(VariablesInventario.vNomLab);
		lblUnidad.setText(VariablesInventario.vDescUnidPresent);
        lblUnidadVenta.setText(VariablesInventario.vDescUnidFrac);
    lblStock.setText("" + VariablesInventario.vStock);
	}

	private boolean datosValidados() {
		boolean rpta = true;
    
    	if (cmbMotivoAjuste.getSelectedIndex()==0) {
			FarmaUtility.showMessage(this, "Seleccione una opcion válida",
					cmbMotivoAjuste);
			return false;
		}
		if (txtStockModifEntero.getText().trim().length() == 0) {
			FarmaUtility.showMessage(this, "Ingrese una cantidad válida",
					txtStockModifEntero);
			return false;
		}
		if (!FarmaUtility.isInteger(txtStockModifEntero.getText().trim())) {
			FarmaUtility.showMessage(this, "Ingrese una cantidad válida",
					txtStockModifEntero);
			return false;
		}
    if (txtStockFraccion.getText().trim().length() == 0) {
			FarmaUtility.showMessage(this, "Ingrese una cantidad válida",
					txtStockFraccion);
			return false;
		}
		if (!FarmaUtility.isInteger(txtStockFraccion.getText().trim())) {
			FarmaUtility.showMessage(this, "Ingrese una cantidad válida",
					txtStockFraccion);
			return false;
		}
     if(FarmaUtility.getDecimalNumber(txtStockModifEntero.getText().trim()) * Integer.parseInt(VariablesInventario.vFrac) + FarmaUtility.getDecimalNumber(txtStockFraccion.getText().trim()) == FarmaUtility.getDecimalNumber(lblStock.getText().trim()))
    {
      FarmaUtility.showMessage(this, "La cantidad ingresada debe ser distinto del Stock Actual.",
					txtStockModifEntero);
			return false;
    }
    return rpta;
	}

	private void insertarAjusteKardex() throws SQLException {
    log.debug("Integer.parseInt(VariablesInventario.vFrac) : " + Integer.parseInt(VariablesInventario.vFrac) );
    txtGlosa.setText(txtGlosa.getText().toUpperCase());
    int entero = Integer.parseInt(txtStockModifEntero.getText().trim()) ;
    int fraccion = Integer.parseInt(txtStockFraccion.getText().trim()) ;
    log.debug("entero : " + entero);
    log.debug("fraccion : " + fraccion);
    int cantidad =   entero * Integer.parseInt(VariablesInventario.vFrac) + fraccion  ;
    String cantidadCompleta = "" + cantidad ;
    log.debug("cantidad : " + cantidadCompleta);
		DBInventario.ingresaAjusteKardex(FarmaLoadCVL.getCVLCode(
				"cmbMotivoAjuste", cmbMotivoAjuste.getSelectedIndex()),
        cantidadCompleta,
        txtGlosa.getText().trim().toUpperCase());
	} 

  private void txtGlosa_keyPressed(KeyEvent e)
  {if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			FarmaUtility.moveFocus(cmbMotivoAjuste);
      txtGlosa.setText(txtGlosa.getText().toUpperCase());
		} else {
			chkKeyPressed(e);
		}
	} 

  private void txtStockFraccion_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			FarmaUtility.moveFocus(txtGlosa);
		} else {
			chkKeyPressed(e);
		}
  }

  private void txtStockFraccion_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtStockFraccion, e);
  }
}