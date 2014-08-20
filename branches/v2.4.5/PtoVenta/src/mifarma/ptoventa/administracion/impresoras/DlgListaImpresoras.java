package mifarma.ptoventa.administracion.impresoras;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.ConstantsImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.VariablesImpresoras;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaImpresoras extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaImpresoras.class);
        
        Frame myParentFrame;

	FarmaTableModel tableModel;

	private JPanelWhite jContentPane = new JPanelWhite();

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanelTitle pnlHeaderListaImp = new JPanelTitle();

	private JLabelFunction lblCrear = new JLabelFunction();

	private JLabelFunction lblModificar = new JLabelFunction();

	private JLabelFunction lblsc = new JLabelFunction();

	private JScrollPane scrListaImpresoras = new JScrollPane();

	private JTable tblListaImpresoras = new JTable();

  private JButtonLabel btnRelacionImp = new JButtonLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();

    // **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgListaImpresoras() {
		this(null, "", false);
	}

	public DlgListaImpresoras(Frame parent, String title, boolean modal) {
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
		this.setSize(new Dimension(661, 315));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Mantenimiento de Impresoras");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
		jContentPane.setLayout(null);
		pnlHeaderListaImp.setBounds(new Rectangle(10, 10, 635, 25));
		lblCrear.setBounds(new Rectangle(330, 260, 95, 20));
		lblCrear.setText("[F2] Crear");
		lblModificar.setBounds(new Rectangle(435, 260, 105, 20));
		lblModificar.setText("[F3] Modificar");
		lblsc.setBounds(new Rectangle(550, 260, 95, 20));
		lblsc.setText("[Esc]Salir");
		scrListaImpresoras.setBounds(new Rectangle(10, 35, 635, 215));
		tblListaImpresoras.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				tblListaImpresoras_keyPressed(e);
			}
		});
    btnRelacionImp.setText("Relación de Impresoras");
    btnRelacionImp.setBounds(new Rectangle(10, 5, 140, 15));
    btnRelacionImp.setMnemonic('r');
    btnRelacionImp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionImp_actionPerformed(e);
        }
      });
        jLabelFunction1.setBounds(new Rectangle(10, 260, 135, 20));
        jLabelFunction1.setText("[ F1 ] Prueba Ticket");
        jLabelFunction2.setBounds(new Rectangle(160, 260, 155, 20));
        jLabelFunction2.setText("[ F5 ] Prueba de Consejo");
        scrListaImpresoras.getViewport().add(tblListaImpresoras, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(lblsc, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblCrear, null);
        pnlHeaderListaImp.add(btnRelacionImp, null);
		jContentPane.add(pnlHeaderListaImp, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
	}

	// **************************************************************************
	// Método "initialize()"
	// **************************************************************************
	private void initialize() {
        FarmaVariables.vAceptar = false;
		initTable();

	};

	// **************************************************************************
	// Métodos de inicialización
	// **************************************************************************

	private void initTable() {
		tableModel = new FarmaTableModel(
				ConstantsImpresoras.columnsListaImpresoras,
				ConstantsImpresoras.defaultValuesListaImpresoras, 0);
		FarmaUtility.initSimpleList(tblListaImpresoras, tableModel,
				ConstantsImpresoras.columnsListaImpresoras);
		cargaListaImpresoras();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void this_windowOpened(WindowEvent e) {
	        VariablesPtoVenta.vEjecutaAccionTecla = false;
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(tblListaImpresoras);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
    private void chkKeyPressed(KeyEvent e)
    {
        //ERIOS 14.06.2013 Se envia Secuencial impresora
        if (!VariablesPtoVenta.vEjecutaAccionTecla)
        {
            VariablesPtoVenta.vEjecutaAccionTecla = true;
            if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e))
            {
                String tipComp= tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 7).toString().trim();
                if (tipComp.trim().equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET) ||
                    tipComp.trim().equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET_FACT))
                {   
                     //tipo ticket solamente
                    String ruta= tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 5).toString().trim();
                    String descImpr= tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 1).toString().trim();
                    String secImpr= tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 0).toString().trim();                                
                    UtilityCaja.imprimePruebaTermicaPorIP(this,ruta,descImpr,secImpr);
                }
                else
                    FarmaUtility.showMessage(this, 
                                            "Atención: No se puede realizar la prueba de impresión,\n debido a que la impresora elegida no es una ticketera", 
                                            null);
            }
            else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e))
            {
                if (FarmaVariables.vEconoFar_Matriz)
                {   FarmaUtility.showMessage(this, 
                                             ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                             btnRelacionImp);
                }
                else
                {
                    VariablesImpresoras.limpiar();
                    DlgDatosImpresoras dlgDatosImpresoras = new DlgDatosImpresoras(myParentFrame, "", true);
                    dlgDatosImpresoras.setVisible(true);
                    if (FarmaVariables.vAceptar)
                    {   cargaListaImpresoras();
                        FarmaVariables.vAceptar = false;
                    }
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_F3)
            {
                //JCORTEZ 14.04.09
                VariablesImpresoras.vTipoComp=tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 7).toString().trim();
            
                if (FarmaVariables.vEconoFar_Matriz)
                {   FarmaUtility.showMessage(this, 
                                             ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                             btnRelacionImp);
                }
                else
                {
                    if (tieneRegistroSeleccionado(this.tblListaImpresoras))
                    {
                        cargarImpresoraSeleccionada();
                        DlgDatosImpresoras dlgDatosImpresoras = new DlgDatosImpresoras(this.myParentFrame, "", true);
                        dlgDatosImpresoras.setVisible(true);
                        if (FarmaVariables.vAceptar)
                        {
                            cargaListaImpresoras();
                            FarmaVariables.vAceptar = false;
                        }
                    }
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_F4)
            {
                /*if (tieneRegistroSeleccionado(this.tblListaImpresoras)) {

                                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                    "¿Esta seguro de cambiar el estado a la impresora?")) {
                                            cargarImpresoraSeleccionada();
                                            try {
                                                    cambiarEstadoSeleccionado();
                                                    FarmaUtility.aceptarTransaccion();
                                                    cargaListaImpresoras();
                                                    FarmaUtility.showMessage(this,
                                                                    "La operación se realizó correctamente",
                                                                    tblListaImpresoras);
                                            } catch (SQLException ex) {
                                                    FarmaUtility.liberarTransaccion();
                                                    FarmaUtility.showMessage(this,
                                                                    "Ocurrió un error en la transacción: "
                                                                                    + ex.getMessage(), tblListaImpresoras);
                                                    log.error("",ex);
                                            }
                                    }
                            }*/

            }else if(e.getKeyCode() == KeyEvent.VK_F5)
            {
                UtilityCaja.pruebaImpresoraTermica(this,tblListaImpresoras);
                          
                  }else if (e.getKeyCode() == KeyEvent.VK_F6) {
            
             //JCORTEZ 04.06.09
             //Se mostrara lista de IP relacionadas a la impresora
           /* VariablesImpresoras.vTipoComp = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 7).toString().trim();  
            VariablesImpresoras.vSecImpr = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 0).toString().trim();  
            VariablesImpresoras.vNumSerie = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 3).toString().trim();  

            if ( VariablesImpresoras.vTipoComp.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)){ //solo para tipo ticket
                DlgListaIPSImpresora dlgip =new DlgListaIPSImpresora(this.myParentFrame,"",true);
                dlgip.setVisible(true);
            }else 
                FarmaUtility.showMessage(this,"Opcion no habilitada para esta impresora.",tblListaImpresoras);
            */
            }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            }
            VariablesPtoVenta.vEjecutaAccionTecla = false;
        }

    }
 private void btnRelacionImp_actionPerformed(ActionEvent e)
  {FarmaUtility.moveFocus(tblListaImpresoras);
  }
	// **************************************************************************
	// Metodos de lógica de negocio
	// **************************************************************************

	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

	private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;

		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}

    private void cargarImpresoraSeleccionada() {
        int vSelec = tblListaImpresoras.getSelectedRow();
        VariablesImpresoras.vSecImprLocal = tblListaImpresoras.getValueAt(vSelec, 0).toString().trim();
        VariablesImpresoras.vDescImprLocal = tblListaImpresoras.getValueAt(vSelec, 1).toString().trim();
        VariablesImpresoras.vDescComp = tblListaImpresoras.getValueAt(vSelec, 2).toString().trim();
        VariablesImpresoras.vNumSerie = tblListaImpresoras.getValueAt(vSelec, 3).toString().trim();
        VariablesImpresoras.vNumComp = tblListaImpresoras.getValueAt(vSelec, 4).toString().trim();
        VariablesImpresoras.vRutaImpr = tblListaImpresoras.getValueAt(vSelec, 5).toString().trim();
        VariablesImpresoras.vTipComp = tblListaImpresoras.getValueAt(vSelec, 7).toString().trim();
        VariablesImpresoras.vSerieImpresora = FarmaUtility.getValueFieldJTable(tblListaImpresoras, vSelec, 8);

    }

	private void cambiarEstadoSeleccionado() throws SQLException {
		DBImpresoras.cambiaEstadoImpresora(FarmaVariables.vCodGrupoCia,
				FarmaVariables.vCodLocal, VariablesImpresoras.vSecImprLocal);

	}

	private void cargaListaImpresoras() {

		try {
			DBImpresoras.getListaImpresoras(tableModel);
			if (tblListaImpresoras.getRowCount() > 0)
				FarmaUtility.ordenar(tblListaImpresoras, tableModel, 0, "asc");
			log.debug("se cargo la lista de impresoras");
		} catch (SQLException e) {
		    log.error("",e);
      FarmaUtility.showMessage(this,"Error al obtener lista de impresoras. \n " + e.getMessage(),tblListaImpresoras);
		}
	}

	private void tblListaImpresoras_keyPressed(KeyEvent e) {
		chkKeyPressed(e);
	}

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
}
