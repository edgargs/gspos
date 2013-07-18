package mifarma.ptoventa.tomainventario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.*;
import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;
import mifarma.ptoventa.tomainventario.reference.DBTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import mifarma.ptoventa.reference.*;


public class DlgListaTomasInventario extends JDialog {
	FarmaTableModel tableModelTomasInventario;

	Frame myParentFrame;

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanel jContentPane = new JPanel();

	private JPanelTitle jPanelTitle1 = new JPanelTitle();

	private JScrollPane jScrollPane1 = new JScrollPane();

	private JTable tblRelacionTomas = new JTable();

	private JButtonLabel btnRelacionTomas = new JButtonLabel();

	private JLabelFunction lblF1 = new JLabelFunction();

	private JLabelFunction lblF8 = new JLabelFunction();

	private JLabelFunction lblF9 = new JLabelFunction();

	private JLabelFunction lblEscape = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction lblF3 = new JLabelFunction();

	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgListaTomasInventario() {
		this(null, "", false);
	}

	public DlgListaTomasInventario(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		myParentFrame = parent;
		try {
			jbInit();
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// **************************************************************************
	// Método "jbInit()"
	// **************************************************************************

	private void jbInit() throws Exception {
		this.setSize(new Dimension(550, 374));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Lista de Tomas de Inventario");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
		});
		jContentPane.setBackground(Color.white);
		jContentPane.setLayout(null);
		jPanelTitle1.setBounds(new Rectangle(15, 10, 510, 30));
		jScrollPane1.setBounds(new Rectangle(15, 40, 510, 245));
		tblRelacionTomas.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				tblRelacionTomas_keyPressed(e);
			}
		});
		btnRelacionTomas.setText("Relacion de Tomas de Inventario");
		btnRelacionTomas.setBounds(new Rectangle(10, 5, 195, 20));
		btnRelacionTomas.setMnemonic('r');
		btnRelacionTomas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRelacionTomas_actionPerformed(e);
			}
		});
		lblF1.setBounds(new Rectangle(15, 295, 105, 20));
		lblF1.setText("[ F1 ] Ver Toma");
		lblF8.setBounds(new Rectangle(280, 295, 117, 19));
		lblF8.setText("[ F8 ] Cargar Toma");
		lblF9.setBounds(new Rectangle(405, 295, 117, 19));
		lblF9.setText("[ F9 ] Anular Toma");
		lblEscape.setBounds(new Rectangle(410, 325, 115, 20));
		lblEscape.setText("[ ESCAPE ] Cerrar");
    lblF2.setBounds(new Rectangle(135, 295, 130, 20));
    lblF2.setText("[ F2 ] Ver Consolidado");
    lblF3.setBounds(new Rectangle(15, 320, 130, 20));
    lblF3.setText("[ F3 ] Ver Diferencias");
		jScrollPane1.getViewport().add(tblRelacionTomas, null);
    jContentPane.add(lblF3, null);
    jContentPane.add(lblF2, null);
		jContentPane.add(lblEscape, null);
		jContentPane.add(lblF9, null);
		jContentPane.add(lblF8, null);
		jContentPane.add(lblF1, null);
		jContentPane.add(jScrollPane1, null);
		jPanelTitle1.add(btnRelacionTomas, null);
		jContentPane.add(jPanelTitle1, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
	}

	// **************************************************************************
	// Método "initialize()"
	// **************************************************************************

	private void initialize() {
		mifarma.common.FarmaVariables.vAceptar = false;
		initTableListaTomasInventario();
	}

	// **************************************************************************
	// Métodos de inicialización
	// **************************************************************************

	private void initTableListaTomasInventario() {
		tableModelTomasInventario = new FarmaTableModel(
				ConstantsTomaInv.columnsTomasInventario,
				ConstantsTomaInv.defaultValuesTomasInventario, 0);
		FarmaUtility.initSimpleList(tblRelacionTomas,
				tableModelTomasInventario,
				ConstantsTomaInv.columnsTomasInventario);
		cargaTomasInventario();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(tblRelacionTomas);
	}

	private void tblRelacionTomas_keyPressed(KeyEvent e) {
		chkKeyPressed(e);
	}

	private void btnRelacionTomas_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(tblRelacionTomas);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cerrarVentana(false);
		} else if (e.getKeyCode() == KeyEvent.VK_F1) {
			if (tieneRegistroSeleccionado(tblRelacionTomas)) {
				cargarRegistroSeleccionado();
				VariablesTomaInv.vTipOp = ConstantsTomaInv.TIPO_OPERACION_TOMA_INV;
          DlgLaboratoriosPorToma dlgLaboratoriosPorToma = new DlgLaboratoriosPorToma(myParentFrame, "", true);
				dlgLaboratoriosPorToma.setVisible(true);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_F2) 
    {
      if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA)){
        if (tieneRegistroSeleccionado(tblRelacionTomas)) {
          cargarRegistroSeleccionado();
          DlgConsolidadoToma dlgConsolidadoToma = new DlgConsolidadoToma(myParentFrame," ",true);
          dlgConsolidadoToma.setVisible(true);
        }
      } else FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",tblRelacionTomas); 
       
		} else if (e.getKeyCode() == KeyEvent.VK_F3) 
    {
      if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA)){
        if (tieneRegistroSeleccionado(tblRelacionTomas)) {
          cargarRegistroSeleccionado();
          DlgListaDiferenciasConsolidado dlgListaDiferenciasConsolidado = new DlgListaDiferenciasConsolidado(myParentFrame," ",true);
          dlgListaDiferenciasConsolidado.setVisible(true);
        }
      } else FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",tblRelacionTomas);  
          
		} else if (e.getKeyCode() == KeyEvent.VK_F8) {
        if(FarmaVariables.vEconoFar_Matriz)
            FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblRelacionTomas);
        else 
        {
     
          if (cargaLogin()){
        System.out.println("***Despues de Logueo "+ FarmaVariables.vIdUsu_Toma); 
        String idUsuarioToma_Creacion = tblRelacionTomas.getValueAt(tblRelacionTomas.getSelectedRow(),5).toString().trim();
         System.out.println("***Usuario de Creacion :"+idUsuarioToma_Creacion);
         System.out.println("***usuario de intento FarmaVariables.vIdUsu_Toma "+ VariablesTomaInv.vIdUsu_Toma);
         if (idUsuarioToma_Creacion.equalsIgnoreCase(VariablesTomaInv.vIdUsu_Toma.trim())){System.out.println("esta en el if");
			if (tieneRegistroSeleccionado(this.tblRelacionTomas)) {
				cargarRegistroSeleccionado();
				if (esTomaValida())
              if (FarmaUtility.rptaConfirmDialog(this,"¿Esta seguro de cargar la toma?")) {
						try {
                if(obtieneIndicadorProceso_ForUpdate(VariablesTomaInv.vSecToma,VariablesTomaInv.vIndProceso)){
							cargarToma();
							FarmaUtility.aceptarTransaccion();
                  FarmaUtility.showMessage(this,"La toma se cargó correctamente",tblRelacionTomas);
							cargaTomasInventario();
                } else {
                  FarmaUtility.liberarTransaccion();
                  FarmaUtility.showMessage(this,"No es posible realizar la operacion. La Toma ya se encuentra cargada. Verifique!!!",tblRelacionTomas);
                  cargaTomasInventario();
                  return;
                }
						} catch (SQLException sql) {
							FarmaUtility.liberarTransaccion();
              sql.printStackTrace();
							FarmaUtility.showMessage(this,"Ocurrió un error al cargar la toma :\n"+ sql.getMessage(),tblRelacionTomas);
						}
					}
			}
        }
        else{

        FarmaUtility.showMessage(this,"Solo el usuario "+idUsuarioToma_Creacion+" quien inicio la Toma puede cargar la Toma. Verifique!",tblRelacionTomas);
       }
        }
        }
		} else if (e.getKeyCode() == KeyEvent.VK_F9) {
      if(FarmaVariables.vEconoFar_Matriz)
            FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblRelacionTomas);
      else 
      {
      
        if (cargaLogin()){
        System.out.println("***Despues de Logueo "+ FarmaVariables.vIdUsu_Toma); 
       String idUsuarioToma_Creacion = tblRelacionTomas.getValueAt(tblRelacionTomas.getSelectedRow(),5).toString().trim();
          System.out.println("***Usuario de Creacion :"+idUsuarioToma_Creacion);
          System.out.println("***usuario de intento FarmaVariables.vIdUsu_Toma "+ VariablesTomaInv.vIdUsu_Toma);
         if (idUsuarioToma_Creacion.equalsIgnoreCase(VariablesTomaInv.vIdUsu_Toma.trim())){System.out.println("esta en el if");
			if (tieneRegistroSeleccionado(this.tblRelacionTomas)) {
          if (FarmaUtility.rptaConfirmDialog(this,"¿Esta seguro de anular la toma?")){
					cargarRegistroSeleccionado();
					try {
              if(obtieneIndicadorProceso_ForUpdate(VariablesTomaInv.vSecToma,VariablesTomaInv.vIndProceso)){
						anularToma();
						FarmaUtility.aceptarTransaccion();
						FarmaUtility.showMessage(this,"La toma se anuló correctamente",tblRelacionTomas);
						cargaTomasInventario();
              } else 
              {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"No es posible realizar la operacion. La Toma ya se encuentra anulada. Verifique!!!",tblRelacionTomas);
                cargaTomasInventario();
                return;
              }
					} catch (SQLException sql) {
						FarmaUtility.liberarTransaccion();
						sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al anular la toma :\n"+ sql.getMessage(), tblRelacionTomas);
					}
				}
			}
		}
      else
       FarmaUtility.showMessage(this,"Solo el usuario "+idUsuarioToma_Creacion+" quien inicio la Toma puede eliminar la Toma. Verifique!",tblRelacionTomas);
		}
	}
	}
	}

	// **************************************************************************
	// Metodos de lógica de negocio
	// **************************************************************************

	private void cargaTomasInventario() {
		try {
			DBTomaInv.getListaTomasInv(tableModelTomasInventario);
			if (tblRelacionTomas.getRowCount() > 0)
				FarmaUtility.ordenar(tblRelacionTomas,
						tableModelTomasInventario, 2,
						FarmaConstants.ORDEN_ASCENDENTE);
		
		} catch (SQLException sql) {
			sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al obtener las tomas de inventario : \n" + sql.getMessage(),tblRelacionTomas);
		}
	}

	private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;
		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}

	private void cargarRegistroSeleccionado() {
		VariablesTomaInv.vSecToma = tblRelacionTomas.getValueAt(tblRelacionTomas.getSelectedRow(), 0).toString().trim();
    VariablesTomaInv.vIndProceso = tblRelacionTomas.getValueAt(tblRelacionTomas.getSelectedRow(), 4).toString().trim();
	}

	private void cargarToma() throws SQLException {
		DBTomaInv.cargarToma();
	}

	private void anularToma() throws SQLException {
		DBTomaInv.anularToma();
	}

	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

	private boolean esTomaValida() {
		boolean rpta = true;
		String indTomaInc = "";
		ArrayList listaLabs;
		String sListaLabs = "";
		try {
			indTomaInc = DBTomaInv.obtenerIndTomaIncompleta().trim();
		} catch (SQLException sql) {
			rpta = false;
			sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al determinar el estado de la toma : \n"+sql.getMessage(),	tblRelacionTomas);
		}
		if (indTomaInc.equals("1")) {
			// La toma esta incompleta
			listaLabs = new ArrayList();
			rpta = false;
			try {
				listaLabs = DBTomaInv.getListaLabsTomaIncompleta();
				for (int i = 0; i < listaLabs.size(); i++) {
					sListaLabs = sListaLabs + listaLabs.get(i).toString()
							+ "\n";
				}
				FarmaUtility.showMessage(this,	"No se puede efectuar la operacion ya que los siguientes laboratorios no han sido completados correctamente:\n "+ sListaLabs, tblRelacionTomas);
			} catch (SQLException sql) {
				FarmaUtility.showMessage(this,"Ocurrió un error en la transacción:\n"+ sql.getMessage(), tblRelacionTomas);
				sql.printStackTrace();
			}
		}
		return rpta;
	}
  
  public static boolean obtieneIndicadorProceso_ForUpdate(String pSecTomaInv, String pIndProceso)
  {
    ArrayList myArray = new ArrayList();
    String indProceso = new String();
    try
    {
      DBTomaInv.obtieneIndTomaInvForUpdate(myArray,pSecTomaInv,pIndProceso);
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      return false;
    } finally
    {
      if(myArray.size() > 0)
      {
        indProceso = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
        System.out.println("indProceso : " + indProceso);
        VariablesTomaInv.vIndProceso = indProceso;
        return true;
      } else return false;
    }
  }
  private boolean cargaLogin()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_AUDITORIA);
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      /**
       * @author :  
       * @since  : 19.07.2007
       */
      VariablesTomaInv.vIdUsu_Toma = FarmaVariables.vIdUsu;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
    }
    return FarmaVariables.vAceptar;
  }

}