package mifarma.ptoventa.administracion.usuarios;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.administracion.usuarios.reference.ConstantsUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.VariablesUsuarios;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JButtonLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaRoles extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaRoles.class);

	Frame myParentFrame;

	FarmaTableModel tableModel;

	private JPanelWhite jContentPane = new JPanelWhite();

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanelTitle pnlHeaderRoles = new JPanelTitle();

	private JScrollPane scrListaRoles = new JScrollPane();

	private JTable tblListaRoles = new JTable();

	private JLabelFunction lblEnter = new JLabelFunction();

	private JLabelFunction lblAceptar = new JLabelFunction();

	private JLabelFunction lblSalir = new JLabelFunction();

  private JButtonLabel btnListaRoles = new JButtonLabel();

	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgListaRoles() {
		this(null, "", false);
	}

	public DlgListaRoles(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		myParentFrame = parent;
		try {
			jbInit();
			initialize();
		} catch (Exception e) {
		    log.error("",e);
		}

	}

	// **************************************************************************
	// Método "jbInit()"
	// **************************************************************************

	private void jbInit() throws Exception {
		this.setSize(new Dimension(400, 272));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Lista de Roles");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
		});
		jContentPane.setLayout(null);
		pnlHeaderRoles.setBounds(new Rectangle(10, 10, 375, 30));
		scrListaRoles.setBounds(new Rectangle(10, 40, 375, 175));
		tblListaRoles.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				tblListaRoles_keyPressed(e);
			}
		});
		lblEnter.setBounds(new Rectangle(40, 220, 155, 20));
		lblEnter.setText("[Enter] Selecciona Rol");
		lblAceptar.setBounds(new Rectangle(205, 220, 90, 20));
		lblAceptar.setText("[F11] Aceptar");
		lblSalir.setBounds(new Rectangle(305, 220, 75, 20));
		lblSalir.setText("[ESC] Cerrar");
    btnListaRoles.setText("Lista de Roles");
    btnListaRoles.setBounds(new Rectangle(15, 5, 100, 20));
    btnListaRoles.setMnemonic('l');
    btnListaRoles.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnListaRoles_actionPerformed(e);
        }
      });
		jContentPane.add(lblSalir, null);
		jContentPane.add(lblAceptar, null);
		jContentPane.add(lblEnter, null);
		scrListaRoles.getViewport().add(tblListaRoles, null);
		jContentPane.add(scrListaRoles, null);
    pnlHeaderRoles.add(btnListaRoles, null);
		jContentPane.add(pnlHeaderRoles, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
	 this.setDefaultCloseOperation( javax.swing.JFrame.DO_NOTHING_ON_CLOSE  );
  }

	// **************************************************************************
	// Método "initialize()"
	// **************************************************************************

	private void initialize() {
		mifarma.common.FarmaVariables.vAceptar = false;
		initTable();
		marcarRoles();

	};

	// **************************************************************************
	// Métodos de inicialización
	// **************************************************************************

	private void initTable() {
		tableModel = new FarmaTableModel(ConstantsUsuarios.columnsListaRoles,
				ConstantsUsuarios.defaultValuesListaRoles, 0);
		FarmaUtility.initSelectList(tblListaRoles, tableModel,
				ConstantsUsuarios.columnsListaRoles);
		cargaLista();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************

	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(tblListaRoles);
	}

	private void tblListaRoles_keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();

			FarmaUtility.setCheckValue(tblListaRoles, false);

		}

		chkKeyPressed(e);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************

	private void chkKeyPressed(KeyEvent e) {

		if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
		{
		} else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {

			if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
					"¿Esta seguro de asignar los roles?")) {

				actualizarlistaRolesSeleccion();

				try {
					this.eliminarAsignacionesUsuario();
					this.insertarAsignaciones();
					FarmaUtility.aceptarTransaccion();
					FarmaUtility.showMessage(this,
							"La operación se realizó correctamente",
							tblListaRoles);
				} catch (SQLException ex) {
					FarmaUtility.liberarTransaccion();
                                        log.error("",ex);
					FarmaUtility.showMessage(this,"Error al grabar o eliminar un rol de usuario: \n"+ ex.getMessage(), tblListaRoles);
				}
			}

			cerrarVentana(true);

		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			FarmaVariables.vAceptar = false;
			this.setVisible(false);
			this.dispose();
		}

	}

	// **************************************************************************
	// Metodos de lógica de negocio
	// **************************************************************************

	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

	private void cargaLista() {
		try {
			DBUsuarios.getListaRoles(tableModel);

			if (tblListaRoles.getRowCount() > 0)
				FarmaUtility.ordenar(tblListaRoles, tableModel, 2, "asc");
			log.debug("se cargo la lista de roles");
		} catch (SQLException ex) {
		    log.error("",ex);
        FarmaUtility.showMessage(this,"Error al listar los roles. \n " + ex.getMessage(),tblListaRoles);
		}

	}

	private void actualizarlistaRolesSeleccion() {
		Boolean valor;
		VariablesUsuarios.listaRoles = new ArrayList();

		for (int i = 0; i < tblListaRoles.getRowCount(); i++) {

			valor = (Boolean) tblListaRoles.getValueAt(i, 0);

			if (valor.booleanValue()) {
				// Si se ha marcado
				VariablesUsuarios.listaRoles.add(new String[] {
						tblListaRoles.getValueAt(i, 1).toString(),
						tblListaRoles.getValueAt(i, 2).toString() });
			}
		}

	}

	private void eliminarAsignacionesUsuario() throws SQLException {

		DBUsuarios.limpiaAsignacionUsuario(VariablesUsuarios.vSecUsuLocal);

	}

	private void insertarAsignaciones() throws SQLException {
		String[] tmp2 = new String[2];
		for (int i2 = 0; i2 < VariablesUsuarios.listaRoles.size(); i2++) {
			tmp2 = (String[]) VariablesUsuarios.listaRoles.get(i2);
			DBUsuarios.agregaAsignacionUsuario(VariablesUsuarios.vSecUsuLocal,
					tmp2[0]);
		}
	}

	private void marcarRoles() {
		String[] tmp2 = new String[2];
		ArrayList myArray;
		ArrayList myArray2;
		String tmp = "";

		if (VariablesUsuarios.listaRoles.size() > 0) {
			myArray = new ArrayList();
			myArray2 = new ArrayList();

			for (int i = 0; i < VariablesUsuarios.listaRoles.size(); i++) {
				myArray2 = new ArrayList();
				tmp2 = (String[]) VariablesUsuarios.listaRoles.get(i);
				tmp = tmp2[0];
				myArray2.add(tmp);
				myArray.add(myArray2);

			}

			FarmaUtility.ponerCheckJTable(tblListaRoles, 1, myArray, 0);

		}

	}

  private void this_windowClosing(WindowEvent e)
  { FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void btnListaRoles_actionPerformed(ActionEvent e)
  {FarmaUtility.moveFocus(tblListaRoles);
  }

}