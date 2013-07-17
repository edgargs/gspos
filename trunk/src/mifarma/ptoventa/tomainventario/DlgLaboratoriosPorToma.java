package mifarma.ptoventa.tomainventario;

import com.gs.mifarma.componentes.JLabelOrange;

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
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.*;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;
import mifarma.ptoventa.tomainventario.reference.DBTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;
import mifarma.ptoventa.reference.*;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

public class DlgLaboratoriosPorToma extends JDialog {
	FarmaTableModel tableModelLaboratorioToma;

	Frame myParentFrame;

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanel jContentPane = new JPanel();

	private JPanelHeader jPanelHeader1 = new JPanelHeader();

	private JPanelTitle jPanelTitle1 = new JPanelTitle();

	private JButtonLabel btnLaboratorio = new JButtonLabel();

	private JTextFieldSanSerif txtLaboratorio = new JTextFieldSanSerif();

	private JScrollPane jScrollPane1 = new JScrollPane();

	private JTable tblRelacionLaboratoriosXToma = new JTable();

	private JButtonLabel btnRelacionLaboratoriosToma = new JButtonLabel();

	private JLabelFunction lblF1 = new JLabelFunction();

	private JLabelFunction lblF12 = new JLabelFunction();

	private JLabelFunction lblEscape = new JLabelFunction();
  
  /**
   * Variable de Filtro Estado
   * @author  dubilluz
   * @since  08.01.2008
   */  
  private String filtroEstado = "";
  private JLabelFunction lblF10Filtro = new JLabelFunction();  
  private JLabelFunction lblF11EliminaFiltro = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();

    // **************************************************************************
	// Constructores
	// **************************************************************************
	public DlgLaboratoriosPorToma() {
		this(null, "", false);
	}

	public DlgLaboratoriosPorToma(Frame parent, String title, boolean modal) {
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
		this.setSize(new Dimension(494, 474));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Laboratorios por Toma de Inventario");
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
		});
		jContentPane.setBackground(Color.white);
		jContentPane.setLayout(null);
		jPanelHeader1.setBounds(new Rectangle(10, 10, 470, 30));
		jPanelTitle1.setBounds(new Rectangle(10, 45, 470, 20));
		btnLaboratorio.setText("Laboratorios :");
		btnLaboratorio.setBounds(new Rectangle(15, 5, 85, 20));
		btnLaboratorio.setMnemonic('l');
		btnLaboratorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLaboratorio_actionPerformed(e);
			}
		});
		txtLaboratorio.setBounds(new Rectangle(105, 5, 320, 20));
		txtLaboratorio.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				txtLaboratorio_keyPressed(e);
			}

			public void keyReleased(KeyEvent e) {
				txtLaboratorio_keyReleased(e);
			}
		});
		jScrollPane1.setBounds(new Rectangle(10, 65, 470, 245));
		btnRelacionLaboratoriosToma.setText("Relacion de Laboratorios por Toma");
		btnRelacionLaboratoriosToma.setBounds(new Rectangle(10, 0, 215, 20));
		btnRelacionLaboratoriosToma.setMnemonic('r');
    btnRelacionLaboratoriosToma.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionLaboratoriosToma_actionPerformed(e);
        }
      });
		lblF1.setBounds(new Rectangle(10, 335, 100, 20));
		lblF1.setText("[ F1 ] Ver Detalle");
		lblF12.setBounds(new Rectangle(370, 335, 110, 20));
		lblF12.setText("[ F12 ] Imprimir");
		lblEscape.setBounds(new Rectangle(355, 415, 120, 20));
		lblEscape.setText("[ ESCAPE ] Cerrar");
    lblF10Filtro.setBounds(new Rectangle(120, 335, 115, 20));
    lblF10Filtro.setText("[ F10 ] Filtro");
    lblF11EliminaFiltro.setBounds(new Rectangle(245, 335, 115, 20));
    lblF11EliminaFiltro.setText("[ F11 ] Quitar Filtro");
    lblF11EliminaFiltro.setVisible(false);
        jLabelFunction1.setBounds(new Rectangle(10, 385, 170, 20));
        jLabelFunction1.setText("[ F5 ] Ingreso Código Barra");

        jLabelFunction2.setBounds(new Rectangle(185, 385, 140, 20));
        jLabelFunction2.setText("[ F7 ] Completar Ceros");
        jLabelFunction3.setBounds(new Rectangle(335, 385, 145, 20));
        jLabelFunction3.setText("[ F8 ] Ver Diferencias");
        jLabelOrange1.setText("Inventario Manual");
        jLabelOrange1.setBounds(new Rectangle(10, 315, 160, 15));
        jLabelOrange1.setFont(new Font("SansSerif", 1, 12));
        jLabelOrange2.setText("Inventario con Lector de Código de barras");
        jLabelOrange2.setBounds(new Rectangle(10, 365, 275, 15));
        jLabelOrange2.setFont(new Font("SansSerif", 1, 12));
        jPanelHeader1.add(txtLaboratorio, null);
		jPanelHeader1.add(btnLaboratorio, null);
		jScrollPane1.getViewport().add(tblRelacionLaboratoriosXToma, null);
        jContentPane.add(jLabelOrange2, null);
        jContentPane.add(jLabelOrange1, null);
        jContentPane.add(jLabelFunction3, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblF10Filtro, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(jScrollPane1, null);
		jPanelTitle1.add(btnRelacionLaboratoriosToma, null);
		jContentPane.add(jPanelTitle1, null);
		jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblF11EliminaFiltro, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
	}

	// **************************************************************************
	// Método "initialize()"
	// **************************************************************************
	private void initialize() {
        FarmaVariables.vAceptar = false;
		initTableLaboratoriosToma();
	}

	// **************************************************************************
	// Métodos de inicialización
	// **************************************************************************

	private void initTableLaboratoriosToma() {
		tableModelLaboratorioToma = new FarmaTableModel(
				ConstantsTomaInv.columnsLaboratoriosToma,
				ConstantsTomaInv.defaultValuesLaboratoriosToma, 0);
		FarmaUtility.initSimpleList(tblRelacionLaboratoriosXToma,
				tableModelLaboratorioToma,
				ConstantsTomaInv.columnsLaboratoriosToma);
		cargaLaboratoriosToma();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(txtLaboratorio);
	}

	private void txtLaboratorio_keyPressed(KeyEvent e) {
		FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionLaboratoriosXToma,
				txtLaboratorio, 1);
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			if (tblRelacionLaboratoriosXToma.getSelectedRow() >= 0) {
				if (!(FarmaUtility.findTextInJTable(
						tblRelacionLaboratoriosXToma, txtLaboratorio.getText()
								.trim(), 0, 1))) {
					FarmaUtility
							.showMessage(
									this,
									"Laboratorio No Encontrado según Criterio de Búsqueda !!!",
									txtLaboratorio);
					return;
				} 
			}
		}
		chkKeyPressed(e);
	}

  private void btnRelacionLaboratoriosToma_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblRelacionLaboratoriosXToma);
  }

	private void btnLaboratorio_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(txtLaboratorio);
	}

	private void txtLaboratorio_keyReleased(KeyEvent e) {
		chkKeyReleased(e);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.setVisible(false);
		} else if (e.getKeyCode() == KeyEvent.VK_F1) {
			if (tieneRegistroSeleccionado(tblRelacionLaboratoriosXToma)) {
				cargarRegistroSeleccionado();
				DlgProductosPorToma dlgProductosPorToma = new DlgProductosPorToma(myParentFrame, "", true);
				dlgProductosPorToma.setVisible(true);
        /**listara lab para actualizar los estados **/
        //08.01.2008 dubilluz modificacion
        cargaLabFiltrados();
			}
		}else if (e.getKeyCode() == KeyEvent.VK_F12){
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtLaboratorio);
      else imprimir();
		}

    /**
     * Añadido : Filtro por : Usuario y Estado
     * @author : dubilluz
     * @since  : 08.01.2008
     */
     else if (e.getKeyCode() == KeyEvent.VK_F10)
      {
         if(tblRelacionLaboratoriosXToma.getRowCount()>0){
            filtrar();         
            lblF11EliminaFiltro.setVisible(true);
         }
      }
     else if (e.getKeyCode() == KeyEvent.VK_F11)
      {
         if(lblF11EliminaFiltro.isVisible()){
            quitarFiltro();
            lblF11EliminaFiltro.setVisible(false);
         }
      }
      else if (e.getKeyCode() == KeyEvent.VK_F5)
      {
         ingresoCodigoBarra();
      }
        else if(e.getKeyCode() == KeyEvent.VK_F7){
    
            completarConCeros();
	}
    else if(e.getKeyCode() == KeyEvent.VK_F8){
         verDiferencias();
        }
      }
	private void chkKeyReleased(KeyEvent e) {
		FarmaGridUtils.buscarDescripcion(e, tblRelacionLaboratoriosXToma,
				txtLaboratorio, 1);
	}

	// **************************************************************************
	// Metodos de lógica de negocio
	// **************************************************************************

	private void cargaLaboratoriosToma() {
		try {
			DBTomaInv.getListaLabsXToma(tableModelLaboratorioToma);
			if (tblRelacionLaboratoriosXToma.getRowCount() > 0)
				FarmaUtility.ordenar(tblRelacionLaboratoriosXToma,tableModelLaboratorioToma, 1,
						FarmaConstants.ORDEN_ASCENDENTE);
			
		} catch (SQLException sql) {
			sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al obtener los laboratorios de la toma :\n"+sql.getMessage(),txtLaboratorio);
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
		VariablesTomaInv.vCodLab = tblRelacionLaboratoriosXToma.getValueAt(
				tblRelacionLaboratoriosXToma.getSelectedRow(), 0).toString()
				.trim();
		VariablesTomaInv.vNomLab = tblRelacionLaboratoriosXToma.getValueAt(
				tblRelacionLaboratoriosXToma.getSelectedRow(), 1).toString()
				.trim();
	}
  
  private void imprimir() {
		if (!FarmaUtility.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
			return;
      System.out.println(FarmaVariables.vImprReporte);
		 //FarmaPrintService vPrint = new FarmaPrintService(45,
		 FarmaPrintService vPrint = new FarmaPrintService(66,
				FarmaVariables.vImprReporte, true);
		if (!vPrint.startPrintService()) {
			FarmaUtility.showMessage(this,
					"No se pudo inicializar el proceso de impresión",
					txtLaboratorio);
			return;
		}
		try {
			String fechaActual = FarmaSearch
					.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
			vPrint.setStartHeader();
			vPrint.activateCondensed();
			vPrint.printBold(FarmaPRNUtility.llenarBlancos(27)
					+ " REPORTE LABORATORIOS POR TOMA DE INVENTARIO ", true);			
      vPrint.printBold(" ",true);
      vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);            
      vPrint.printBold("Nombre Local : " + FarmaVariables.vDescLocal.trim(), true);            
      vPrint.printBold("Toma #: " + VariablesTomaInv.vSecToma, true);
      vPrint.printBold("Tipo         : " + VariablesTomaInv.vDescTipoToma, true);
      vPrint.printBold("Fecha Inicio : " + VariablesTomaInv.vFecIniToma, true);
      vPrint.printBold("Fecha Fin    : " + VariablesTomaInv.vFecFinToma, true);
      vPrint.printBold("Estado       : " + VariablesTomaInv.vDescEstadoToma, true);      
      vPrint.printBold("Fecha Actual : " + fechaActual, true);
			vPrint
					.printLine(
							"==============================================================================================",
							true);
			vPrint
					.printBold(
							"   Código        Laboratorio                                                           Estado        ",
							true);
			vPrint
					.printLine(
							"==============================================================================================",
							true);
			vPrint.deactivateCondensed();
			vPrint.setEndHeader();
			for (int i = 0; i < tblRelacionLaboratoriosXToma.getRowCount(); i++) {

				vPrint.printCondensed("   "+FarmaPRNUtility.alinearIzquierda(
						(String) tblRelacionLaboratoriosXToma.getValueAt(i, 0),14)
						+ FarmaPRNUtility.alinearIzquierda(
								(String) tblRelacionLaboratoriosXToma.getValueAt(i, 1), 70)
						+ FarmaPRNUtility.alinearIzquierda(
								(String) tblRelacionLaboratoriosXToma.getValueAt(i, 2), 22),
		     true);
			}
			vPrint.activateCondensed();
			vPrint
					.printLine(
							"==============================================================================================",
							true);
			vPrint.printBold("Registros Impresos: "
					+ FarmaUtility.formatNumber(tblRelacionLaboratoriosXToma.getRowCount(),
							",##0") + FarmaPRNUtility.llenarBlancos(11), true);
			vPrint.deactivateCondensed();
			vPrint.endPrintService();
		} catch (Exception sqlerr) {
			sqlerr.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al imprimir :\n" + sqlerr.getMessage(),txtLaboratorio);
		}
	}

   /**
   * Muestra  el cuadro de diálogo 
   * donde seleccionara el tipo de Filtro
   * @author : dubilluz
   * @since  : 08.01.2008
   */
  private void filtrar()
  {
    if(tblRelacionLaboratoriosXToma.getRowCount()>0)
    { 
      DlgFiltroLabToma dlgflLab = new DlgFiltroLabToma(this.myParentFrame, "", true);
      dlgflLab.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
       filtroEstado  = VariablesTomaInv.vCodFiltroEstado;
       cargaLabFiltrados();
       FarmaVariables.vAceptar = false;
      }
      else
      cargaLabFiltrados();
       FarmaUtility.moveFocus(txtLaboratorio);
    }
    System.out.println("Colocando el Foco en Tabla");
    FarmaUtility.moveFocus(txtLaboratorio);
  }
  
  /**
   * Cargara la tabla con laboratorios de estado
   * @author : dubilluz
   * @since  : 08.01.2008
   */
  private void cargaLabFiltrados()
  {
    try
    {
      DBTomaInv.cargaLabxTomaFiltro(tableModelLaboratorioToma,filtroEstado);
      if(tblRelacionLaboratoriosXToma.getRowCount()>0)
				FarmaUtility.ordenar(tblRelacionLaboratoriosXToma,tableModelLaboratorioToma, 1,
						FarmaConstants.ORDEN_ASCENDENTE);
      
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al listar los laboratorios : \n" + sql.getMessage(),txtLaboratorio);
    }
  }
  
  /**
   * Quita el filtro 
   * @author : dubilluz
   * @since  : 08.01.2008  
   */
  private void quitarFiltro()
  {
    VariablesTomaInv.vCodFiltroEstado = "X";
    filtroEstado="";
    cargaLaboratoriosToma();
    FarmaUtility.moveFocus(txtLaboratorio);
  }
  
  /**
   * Ingreso de codigos de Barra para los productos
   * @author dubilluz
   * @since  21.12.2009
   */
  private void ingresoCodigoBarra(){
      DlgIngresoCodigoBarra dlgIngresoCodigoBarra =  new DlgIngresoCodigoBarra(this.myParentFrame, "", true);
      dlgIngresoCodigoBarra.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
       //SI SE INGRESO 
       filtroEstado  = VariablesTomaInv.vCodFiltroEstado;
       
       FarmaVariables.vAceptar = false;
      }
      cargaLabFiltrados();
  }
  
  
  private void completarConCeros(){
      if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA)) {
          if (tableModelLaboratorioToma.data.size()>0) {
                if (FarmaUtility.rptaConfirmDialog(this,"¿Está seguro de completar los registros no ingresados con ceros?"))
                   {
                      try {
                             DBTomaInv.rellenaTomaConCeros();
                              FarmaUtility.aceptarTransaccion();
                             cargaLaboratoriosToma();
                              FarmaUtility.showMessage(this,"La operación se realizó correctamente",txtLaboratorio);
                         }
                      catch(SQLException sql){
                             FarmaUtility.liberarTransaccion();
                             sql.printStackTrace();
                             FarmaUtility.showMessage(this,"Ocurrió un error al completar con ceros: \n"+ sql.getMessage(), txtLaboratorio);
                      }
                  }
          }  
      } else {
          FarmaUtility.showMessage(this, 
                                   "No posee privilegios suficientes para acceder a esta opción", 
                                   txtLaboratorio);
      }
            
  }
    
  private void verDiferencias(){
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA)) {
            DlgListaDiferenciasTomaToTal dlgListaDiferenciasProductos = 
                new DlgListaDiferenciasTomaToTal(myParentFrame, "", true);
            dlgListaDiferenciasProductos.setVisible(true);
        } else {
            FarmaUtility.showMessage(this, 
                                     "No posee privilegios suficientes para acceder a esta opción", 
                                     txtLaboratorio);
        }
  }
  
}