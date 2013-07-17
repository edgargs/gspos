package mifarma.ptoventa.inventariodiario;

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

import mifarma.common.*;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.inventariodiario.reference.*;
import mifarma.ptoventa.reference.*;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DlgProductosPorTomaDiario extends JDialog {
	FarmaTableModel tableModelProductosTomaDiario;

	Frame myParentFrame;

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanelWhite jContentPane = new JPanelWhite();

	private JPanelHeader jPanelHeader1 = new JPanelHeader();

	private JPanelTitle jPanelTitle1 = new JPanelTitle();

	private JScrollPane jScrollPane1 = new JScrollPane();

	private JTable tblRelacionProductos = new JTable();

	private JButtonLabel btnRelacionProductos = new JButtonLabel();

	private JButtonLabel btnProductos = new JButtonLabel();

	private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();

	private JLabelFunction lblEnter = new JLabelFunction();

	private JLabelFunction lblF1 = new JLabelFunction();

        private JLabelFunction lblContinuar = new JLabelFunction();

	private JLabelFunction lblEscape = new JLabelFunction();

	//private JLabelWhite lblLaboratorio_T = new JLabelWhite();

	//private JLabelWhite lblLaboratorio = new JLabelWhite();

	private JLabelFunction lblRellenar = new JLabelFunction();

	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgProductosPorTomaDiario() {
		this(null, "", false);
	}

	public DlgProductosPorTomaDiario(Frame parent, String title, boolean modal) {
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
		this.setSize(new Dimension(683, 404));
		this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Productos por Toma de Inventario : Primer Conteo");
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
		});
		jPanelHeader1.setBounds(new Rectangle(15, 10, 650, 50));
		jPanelHeader1.setLayout(null);
		jPanelTitle1.setBounds(new Rectangle(15, 65, 650, 25));
		jPanelTitle1.setLayout(null);
		jScrollPane1.setBounds(new Rectangle(15, 90, 650, 260));
		btnRelacionProductos.setText("Relacion de Productos");
		btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 25));
		btnRelacionProductos.setMnemonic('r');
		btnProductos.setText("Productos :");
		btnProductos.setMnemonic('p');
		btnProductos.setBounds(new Rectangle(30, 15, 65, 20));
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnProductos_actionPerformed(e);
			}
		});
		txtProductos.setBounds(new Rectangle(115, 15, 305, 20));
		txtProductos.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				txtProductos_keyPressed(e);
			}

			public void keyReleased(KeyEvent e) {
				txtProductos_keyReleased(e);
			}
		});
		lblEnter.setBounds(new Rectangle(15, 355, 135, 20));
		lblEnter.setText("[ ENTER ] Seleccionar");
		lblF1.setBounds(new Rectangle(315, 355, 115, 20));
		lblF1.setText("[ F1 ] Imprimir");
		lblEscape.setBounds(new Rectangle(555, 355, 110, 20));
		lblEscape.setText("[ ESCAPE ] Cerrar");
	        lblContinuar.setBounds(new Rectangle(435, 355, 115, 20));
	        lblContinuar.setText("[ F3 ] Continuar");
	    
                //lblLaboratorio_T.setText("Laboratorio :");
		//lblLaboratorio_T.setBounds(new Rectangle(30, 5, 80, 15));
		//lblLaboratorio.setText("Laboratorios Unidos S. A.");
		//lblLaboratorio.setBounds(new Rectangle(120, 5, 410, 15));
		lblRellenar.setBounds(new Rectangle(155, 355, 155, 20));
		lblRellenar.setText("[ F2 ] Completar con ceros");
        jContentPane.add(lblRellenar, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(lblEnter, null);
		jScrollPane1.getViewport().add(tblRelacionProductos, null);
		jContentPane.add(jScrollPane1, null);
		jPanelTitle1.add(btnRelacionProductos, null);
		jContentPane.add(jPanelTitle1, null);
		//jPanelHeader1.add(lblLaboratorio, null);
		//jPanelHeader1.add(lblLaboratorio_T, null);
        jPanelHeader1.add(txtProductos, null);
        jPanelHeader1.add(btnProductos, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblContinuar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
	}

	// **************************************************************************
	// Método "initialize()"
	// **************************************************************************

	private void initialize() {
        FarmaVariables.vAceptar = false;
		initTableListaProductosXLaboratorio();
	}

	// **************************************************************************
	// Métodos de inicialización
	// **************************************************************************

	private void initTableListaProductosXLaboratorio() {
		tableModelProductosTomaDiario = new FarmaTableModel(ConstantsInvDiario.columnsListaProductosXLaboratorio,ConstantsInvDiario.defaultValuesListaProductosXLaboratorio, 0);
		FarmaUtility.initSimpleList(tblRelacionProductos,tableModelProductosTomaDiario,ConstantsInvDiario.columnsListaProductosXLaboratorio);
		cargaListaProductosXLaboratorio();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(txtProductos);
		mostrarDatos();
	}

	private void btnProductos_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(txtProductos);
	}

	private void txtProductos_keyPressed(KeyEvent e) {
		FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProductos,txtProductos, 1);
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      e.consume();
			if (tblRelacionProductos.getSelectedRow() >= 0) {
				if (!(FarmaUtility.findTextInJTable(tblRelacionProductos,txtProductos.getText().trim(), 0, 1))) {
					FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!",txtProductos);
					return;
				}   
   /* else 
        if (tieneRegistroSeleccionado(tblRelacionProductos)) {
          //cargarRegistroSeleccionado();
          //DlgIngresoCantidadToma dlgIngresoCantidadToma = new DlgIngresoCantidadToma(this.myParentFrame, "", true);
          //dlgIngresoCantidadToma.setVisible(true);
          if (FarmaVariables.vAceptar) {
            cargaListaProductosXLaboratorio();
            FarmaVariables.vAceptar = false;
            FarmaGridUtils.showCell(tblRelacionProductos, VariablesInvDiario.vRegActual,0);
          }
          txtProductos.selectAll();
          }  */
        }
			}
		chkKeyPressed(e);
	}

	private void txtProductos_keyReleased(KeyEvent e) {
		chkKeyReleased(e);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	
    private void chkKeyPressed(KeyEvent e) {
	
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
        {
			this.setVisible(false);
        } 
    
        else if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
                  if(FarmaVariables.vEconoFar_Matriz)
                   FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtProductos);
                  else 
                  {
			//if (!VariablesTomaInv.vTipOp.equals(ConstantsTomaInv.TIPO_OPERACION_CONSULTA_HIST))
				if (tieneRegistroSeleccionado(tblRelacionProductos)) 
                                {
					cargarRegistroSeleccionado();
					DlgIngresoCantidadInvDiario dlgIngresoCantidadInvDiario = new DlgIngresoCantidadInvDiario(this.myParentFrame, "", true);
					dlgIngresoCantidadInvDiario.setVisible(true);
					if (FarmaVariables.vAceptar) 
                                        {
						cargaListaProductosXLaboratorio();
						FarmaVariables.vAceptar = false;
						FarmaGridUtils.showCell(tblRelacionProductos, VariablesInvDiario.vRegActual,0);
					}
                                   txtProductos.selectAll();
				}
                   }
        }
        else if (e.getKeyCode() == KeyEvent.VK_F1)
        {
         //if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) ) {
         //DlgListaDiferenciasProdDiario dlgListaDiferenciasProdDiario = new DlgListaDiferenciasProdDiario(myParentFrame, "", true);
         //dlgListaDiferenciasProdDiario.setVisible(true);
             if(FarmaVariables.vEconoFar_Matriz)
               FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtProductos);
             else
             {
                 imprimir();    
                 System.out.println("MARCO : MANDO A IMPRIMIR");
             }
         /*}
        
         else
         {
	    FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",txtProductos);
	 } */
        } 
        
        else if (e.getKeyCode() == KeyEvent.VK_F2) 
        {
          if(lblRellenar.isVisible())
          {
           
            if(FarmaVariables.vEconoFar_Matriz)
            FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtProductos);
         
          else 
          {
           
           if (tieneRegistros(this.tblRelacionProductos)) 
           {
                 
            if (FarmaUtility.rptaConfirmDialog(this,"¿Esta seguro de completar los registros no ingresados con ceros?")) 
            {
              try {
                rellenarConCeros();
                FarmaUtility.aceptarTransaccion();
                cargaListaProductosXLaboratorio();
                FarmaUtility.showMessage(this,"La operación se realizó correctamente",txtProductos);
              } catch (SQLException sql) {
                FarmaUtility.liberarTransaccion();
                sql.printStackTrace();
                FarmaUtility.showMessage(this,"Ocurrió un error al completar con ceros: \n"+ sql.getMessage(), txtProductos);
              }
            }
           }
          }
            
          }
      } 
      //funcionalidad continuar
        
      else if (e.getKeyCode() == KeyEvent.VK_F3) 
      {
          //mfajardo 05.06.09 :Control de tiempo para realizar conteo  
          if (esTomaValida())
          {                
            java.util.Date fecha = new java.util.Date(); 
            VariablesInvDiario.vFechaInicial = fecha.getTime();               
            
            this.dispose(); 
            /*
            //JMIRANDA 03/08/09
            try{
             DBInvDiario.modificaSegConteo();
             FarmaUtility.aceptarTransaccion();   
                  System.out.println("Modifica 2do conteo.");
            } catch(SQLException sql){
               sql.printStackTrace();
               FarmaUtility.showMessage(this,"Ocurrió error al actualizar IndSegConteo\n"+sql.getMessage(),  txtProductos);
               FarmaUtility.liberarTransaccion();
            }
            */
            DlgProductosPorTomaDiarioReConteo dlgProductosPorTomaDiarioReConteo = new DlgProductosPorTomaDiarioReConteo(myParentFrame," ",true);
            dlgProductosPorTomaDiarioReConteo.setVisible(true);
            
            
            //MFAJARDO :  TIMER TOMA INVENTARIO DIARIO
            int tiempo=0;
            try
            {                        
              tiempo = Integer.parseInt(DBInvDiario.obtenerTiempoModifcacionToma().trim());  
            } 
            catch (SQLException sql) 
            {                            
                    sql.printStackTrace();
                    FarmaUtility.showMessage(this,"Ocurrió un error al obtener el tiempo de modificaciòn de toma : \n"+sql.getMessage(),      txtProductos);
            }
            
            if(VariablesInvDiario.vMinutosTranscurridos >=tiempo)
            {              
                VariablesInvDiario.vFlagTimer=true;                            
            }
            //this.setVisible(false);
            
          }
          
          else 
          {              
          FarmaUtility.showMessage(this,"Por favor complete los datos de la toma ",      txtProductos);
          }
          //mfajardo 05.06.09 :Control de tiempo para realizar conteo          
          
      }
      //  
	}

	private void chkKeyReleased(KeyEvent e) {
		FarmaGridUtils.buscarDescripcion(e, tblRelacionProductos, txtProductos,1);
	}

	// **************************************************************************
	// Metodos de lógica de negocio
	// **************************************************************************

	private boolean esTomaValida() 
        {
	        boolean rpta = true;
	        String indTomaInc = "";
	        ArrayList listaLabs;
	        String sListaLabs = "";
	        try
                {
	                indTomaInc = DBInvDiario.obtenerIndTomaIncompleta().trim();
                        if(indTomaInc.equalsIgnoreCase("1"))
                        { 
                            System.out.println("ENTRO A VALIDAR DATOS DE TOMA: TOMA VALIDADA" + indTomaInc );
                            rpta = false;
                        }                    
	        } 
                catch (SQLException sql) 
                {
	                rpta = false;
	                sql.printStackTrace();
	                FarmaUtility.showMessage(this,"Ocurrió un error al determinar el estado de la toma : \n"+sql.getMessage(),      txtProductos);
	        }
	        return rpta;
	}

       
	private void imprimir() {
            
    
              System.out.println("ENTRO A IMPRIMIR TOMA DIARIA - " + FarmaVariables.vImprReporte);
              //FarmaVariables.vImprReporte  = "c:\\reporte_TOMA_INVENTARIO_DIARIO.txt";
              //FarmaVariables.vImprReporte  = "\\\\10.11.1.61\\reportes";
	              if (!FarmaUtility.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
	                      return;
	               //FarmaPrintService vPrint = new FarmaPrintService(45,FarmaVariables.vImprReporte, true);
	               FarmaPrintService vPrint = new FarmaPrintService(66,FarmaVariables.vImprReporte, true);
	              if (!vPrint.startPrintService()) {
	                      FarmaUtility.showMessage(this,"No se pudo inicializar el proceso de impresión",txtProductos);
	                      System.out.println("ENTRO A IMPRIMIR TOMA DIARIA - ERROR EN LA RUTA DE IMPRESION" + FarmaVariables.vImprReporte);
	                      return;
	              }
	              try {
	                      String fechaActual = FarmaSearch
	                                      .getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
	                      vPrint.setStartHeader();
	                      vPrint.activateCondensed();
	                      vPrint.printBold(FarmaPRNUtility.llenarBlancos(27)
                                           + " REPORTE DE PRODUCTOS : PRIMER CONTEO", true);
	    vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);            
	    vPrint.printBold("Nombre Local : " + FarmaVariables.vDescLocal.trim(), true);            
	    vPrint.printBold("Toma #          : " + VariablesInvDiario.vSecToma.trim(), true);
	    vPrint.printBold("Tipo            : " + "Diaria", true);
	    vPrint.printBold("Estado          : " + "Emitida", true);  
	    //vPrint.printBold("Laboratorio     : " + VariablesInvDiario.vCodLab + " - " + VariablesInvDiario.vNomLab.trim(), true);  
	    vPrint.printBold("Fecha Actual    : " + fechaActual, true);
	        
	              
	                      vPrint
	                                      .printLine(
	                                                      "=======================================================================================================",
	                                                      true);
	                      vPrint
	                                      .printBold(
	                                                      "Código  Descripcion                                 Unidad     C. Ent   C. Frac    Laboratorio   Hora   ",
	                                                      true);
	                      vPrint
	                                      .printLine(
	                                                      "=======================================================================================================",
	                                                      true);
	                      vPrint.deactivateCondensed();
	                      vPrint.setEndHeader();
	                      for (int i = 0; i < tblRelacionProductos.getRowCount(); i++) {

	                              vPrint.printCondensed(
	      FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 0), 8)+ 
	      FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 1), 44)+ 
	      FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 2), 10)+"  _____   ______     "+ 
	      //FarmaPRNUtility.alinearDerecha((String) tblRelacionProductos.getValueAt(i, 3), 0)+ 
	      //FarmaPRNUtility.alinearDerecha((String) tblRelacionProductos.getValueAt(i, 4), 4)+  
              FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 5), 10)+ "   ___:___ ", true) ;
	                      }
	                      vPrint.activateCondensed();
	                      vPrint
	                                      .printLine(
	                                                      "=======================================================================================================",
	                                                      true);
	                      vPrint.printBold("Registros Impresos: "
	                                      + FarmaUtility.formatNumber(tblRelacionProductos.getRowCount(),
	                                                      ",##0") + FarmaPRNUtility.llenarBlancos(11), true);
	                      vPrint.deactivateCondensed();
	                      vPrint.endPrintService();
	              } catch (Exception sqlerr) {
	                      sqlerr.printStackTrace();
	    FarmaUtility.showMessage(this,"Ocurrió un error al imprimir : \n" + sqlerr.getMessage(),txtProductos);
	              }
	      }



	private void cargaListaProductosXLaboratorio() {
		try {
			DBInvDiario.getListaProdsXLabXToma(tableModelProductosTomaDiario);
			if (tblRelacionProductos.getRowCount() > 0)
			        FarmaUtility.ordenar(tblRelacionProductos,tableModelProductosTomaDiario, 5,FarmaConstants.ORDEN_ASCENDENTE);
				//FarmaUtility.ordenar(tblRelacionProductos,tableModelProductosTomaDiario, 1,FarmaConstants.ORDEN_ASCENDENTE);
			System.out.println("se cargo la lista de de prods");
		} catch (SQLException sql) {
			sql.printStackTrace();
                        FarmaUtility.showMessage(this,"Ocurrió un error al obtener la lista de productos : \n" + sql.getMessage(),txtProductos);
		}
	}

	private void mostrarDatos() {
		//lblLaboratorio.setText(VariablesInvDiario.vNomLab.trim());
		/*if (VariablesTomaInv.vTipOp.equals(ConstantsTomaInv.TIPO_OPERACION_CONSULTA_HIST)) {
			lblEnter.setVisible(false);
			lblRellenar.setVisible(false);
		}*/
	}

	private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;
		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}

	private void cargarRegistroSeleccionado() {
            
            
                VariablesInvDiario.vCodProd = tblRelacionProductos.getValueAt(tblRelacionProductos.getSelectedRow(), 0).toString().trim();
		VariablesInvDiario.vDesProd = tblRelacionProductos.getValueAt(tblRelacionProductos.getSelectedRow(), 1).toString().trim();
		VariablesInvDiario.vUnidPresentacion = tblRelacionProductos.getValueAt(tblRelacionProductos.getSelectedRow(), 2).toString().trim();                
                VariablesInvDiario.vCantEntera = tblRelacionProductos.getValueAt(tblRelacionProductos.getSelectedRow(), 3).toString().trim();
                VariablesInvDiario.vCantFraccion = tblRelacionProductos.getValueAt(tblRelacionProductos.getSelectedRow(), 4).toString().trim();
                VariablesInvDiario.vNomLab = tblRelacionProductos.getValueAt(tblRelacionProductos.getSelectedRow(), 5).toString().trim();
	        VariablesInvDiario.vFraccion = tblRelacionProductos.getValueAt(tblRelacionProductos.getSelectedRow(), 6).toString().trim();
                VariablesInvDiario.vUnidVta = tblRelacionProductos.getValueAt(tblRelacionProductos.getSelectedRow(), 7).toString().trim();
	        
                VariablesInvDiario.vRegActual = tblRelacionProductos.getSelectedRow();
	}

	private boolean tieneRegistros(JTable tbl) {
		if (tbl.getRowCount() > 0) {
			return true;
		} else
			return false;
	}

  
  private void rellenarConCeros() throws SQLException {
		DBInvDiario.rellenaCantNoIngresadas();
                //rellenar con ceros
	}


}