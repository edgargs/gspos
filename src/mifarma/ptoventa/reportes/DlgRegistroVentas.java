package mifarma.ptoventa.reportes;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.reportes.reference.*;

public class DlgRegistroVentas extends JDialog 
{
  private FarmaTableModel tableModelRegistroVentas;
  private Frame myParentFrame;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
  private JPanelTitle pnlTitulo = new JPanelTitle();
  private JPanelTitle pnlResultados = new JPanelTitle();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblRegistroVentas = new JTable();
  private JButtonLabel btnPeriodo = new JButtonLabel();
  private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
  private JButton btnBuscar = new JButton();
  private JButtonLabel btnListado = new JButtonLabel();
  private JLabel lblRegsitros_T = new JLabel();
  private JLabel lblRegistros = new JLabel();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction lblF9 = new JLabelFunction();
  private JLabelFunction lblF12 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JLabel lblTotalMonto = new JLabel();
  private JLabelFunction lblVerAnulNoCob = new JLabelFunction();

  public DlgRegistroVentas()
  {
    this(null, "", false);
  }

  public DlgRegistroVentas(Frame parent, String title, boolean modal)
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
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(736, 428));
    this.getContentPane().setLayout(borderLayout1);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Registro de Ventas");
    this.addWindowListener(new java.awt.event.WindowAdapter()
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
    pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 710, 30));
    pnlTitulo.setBounds(new Rectangle(10, 50, 710, 20));
    pnlResultados.setBounds(new Rectangle(10, 340, 710, 20));
    jScrollPane1.setBounds(new Rectangle(10, 70, 710, 270));
    tblRegistroVentas.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblRegistroVentas_keyPressed(e);
        }
      });
    btnPeriodo.setText("Periodo :");
    btnPeriodo.setBounds(new Rectangle(150, 5, 60, 20));
    btnPeriodo.setMnemonic('p');
    btnPeriodo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPeriodo_actionPerformed(e);
        }
      });
    txtFechaIni.setBounds(new Rectangle(220, 5, 101, 19));
    txtFechaIni.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaIni_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaIni_keyReleased(e);
        }
      });
    txtFechaFin.setBounds(new Rectangle(345, 5, 101, 19));
    txtFechaFin.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaFin_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaFin_keyReleased(e);
        }
      });
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(475, 5, 95, 20));
    btnBuscar.setMnemonic('b');
    btnBuscar.setFont(new Font("SansSerif", 1, 11));
    btnBuscar.setFocusPainted(false);
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnBuscar_actionPerformed(e);
                    }
      });
    btnListado.setText("Listado de Registro de Ventas");
    btnListado.setBounds(new Rectangle(10, 0, 200, 20));
    btnListado.setMnemonic('l');
    btnListado.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnListado_actionPerformed(e);
        }
      });
    lblRegsitros_T.setText("Registros :");
    lblRegsitros_T.setBounds(new Rectangle(15, 0, 70, 20));
    lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
    lblRegsitros_T.setForeground(Color.white);
    lblRegistros.setText("0");
    lblRegistros.setBounds(new Rectangle(90, 0, 35, 20));
    lblRegistros.setFont(new Font("SansSerif", 1, 11));
    lblRegistros.setForeground(Color.white);
    lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
    lblF1.setBounds(new Rectangle(10, 370, 105, 20));
    lblF1.setText("[ F1 ] Ver Detalle");
    lblF9.setBounds(new Rectangle(390, 370, 100, 20));
    lblF9.setText("[ F9 ] Ordenar");
    lblF12.setBounds(new Rectangle(495, 370, 110, 20));
    lblF12.setText("[ F12 ] Imprimir");
    lblEsc.setBounds(new Rectangle(610, 370, 110, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF2.setBounds(new Rectangle(120, 370, 120, 20));
    lblF2.setText("[ F2 ] Ver Resumen");
   // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
    //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
    lblTotalMonto.setText("0");
    lblTotalMonto.setBounds(new Rectangle(625, 0, 65, 20));
    lblTotalMonto.setFont(new Font("SansSerif", 1, 11));
    lblTotalMonto.setForeground(Color.white);
    lblTotalMonto.setHorizontalAlignment(SwingConstants.RIGHT);
    lblVerAnulNoCob.setBounds(new Rectangle(245, 370, 140, 20));
    lblVerAnulNoCob.setText("[ F6 ] Ver Anul. No Cob.");
    //jContentPane.add(jLabelFunction1, null);
    jContentPane.add(lblVerAnulNoCob, null);
    jContentPane.add(lblF2, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(lblF12, null);
    jContentPane.add(lblF9, null);
    jContentPane.add(lblF1, null);
    jScrollPane1.getViewport().add(tblRegistroVentas, null);
    jContentPane.add(jScrollPane1, null);
    jContentPane.add(pnlResultados, null);
        pnlResultados.add(lblTotalMonto, null);
    pnlResultados.add(lblRegistros, null);
    pnlResultados.add(lblRegsitros_T, null);
    pnlTitulo.add(btnListado, null);
    jContentPane.add(pnlTitulo, null);
    jContentPane.add(pnlCriterioBusqueda, null);
    pnlCriterioBusqueda.add(btnBuscar, null);
    pnlCriterioBusqueda.add(txtFechaFin, null);
    pnlCriterioBusqueda.add(txtFechaIni, null);
    pnlCriterioBusqueda.add(btnPeriodo, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ********************************************************************** */
  /*                            METODO INITIALIZE                           */
  /* ********************************************************************** */
  private void initialize()
  {
    initTableListaRegistroVentas();
  };

  /* ********************************************************************** */
  /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
  /* ********************************************************************** */
  private void initTableListaRegistroVentas()
  {
    tableModelRegistroVentas = new FarmaTableModel(ConstantsReporte.columnsListaRegistroVentas,ConstantsReporte.defaultValuesListaRegistroVentas,0);
    FarmaUtility.initSimpleList(tblRegistroVentas,tableModelRegistroVentas,ConstantsReporte.columnsListaRegistroVentas);
  }
    
  /* ********************************************************************** */
  /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
  /* ********************************************************************** */
  
  private void txtFechaIni_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    FarmaUtility.moveFocus(txtFechaFin);
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    cerrarVentana(false);    
    chkKeyPressed(e);
  }

  private void txtFechaFin_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    btnBuscar.doClick();
    
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    cerrarVentana(false);    
    chkKeyPressed(e);
  }

  private void tblRegistroVentas_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtFechaIni);
  }
  
  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    busqueda();
  }
  
  private void btnPeriodo_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtFechaIni);
  }
  
  private void btnListado_actionPerformed(ActionEvent e)
  {
    if(tblRegistroVentas.getRowCount() > 0)
    {
      FarmaUtility.moveFocus(tblRegistroVentas);
    }
  }
  
  /* ********************************************************************** */
  /*                            METODOS AUXILIARES                          */
  /* ********************************************************************** */
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
    }
    if(e.getKeyCode() == KeyEvent.VK_F1)
    {
    if(tblRegistroVentas.getRowCount() <= 0){
    FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
    FarmaUtility.moveFocus(txtFechaIni);
    } else 
    {
    VariablesReporte.vCorrelativo = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),0)).trim();
    VariablesReporte.vCliente = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),4)).trim();
    //VariablesReporte.vDireccion = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),7)).trim();
    VariablesReporte.vRuc = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),7)).trim();
    VariablesReporte.vFecha = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),3)).trim();
    VariablesReporte.vHora = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),8)).trim();
    VariablesReporte.vUsuario = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),9)).trim();
    VariablesReporte.vEstado = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),10)).trim();
    VariablesReporte.vNComprobante = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),2)).trim();
    System.out.println(VariablesReporte.vCorrelativo +  VariablesReporte.vCliente + VariablesReporte.vDireccion +
    VariablesReporte.vRuc + VariablesReporte.vFecha + VariablesReporte.vHora +  VariablesReporte.vUsuario+
    VariablesReporte.vEstado);
    listadoDetalleVentas();
    }
             
    } else if(e.getKeyCode() == KeyEvent.VK_F2)
    {
      if(tblRegistroVentas.getRowCount() <= 0){
      FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
      FarmaUtility.moveFocus(txtFechaIni);
    } else {
      txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
      txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
      String FechaInicio = txtFechaIni.getText().trim();
      String FechaFin = txtFechaFin.getText().trim();
      System.out.println(FechaFin+FechaInicio);
      resumenVentas();
    }
    } 
    /**NUEVO!
     * @Autor:   
     * @Fecha:  23/04/2007
     * */
    else if(e.getKeyCode() == KeyEvent.VK_F6)
    {
      DlgListaPedAnulNoCob dlgListaPedAnul = new DlgListaPedAnulNoCob(myParentFrame,"",true);
      dlgListaPedAnul.setVisible(true);
    }
    else if(e.getKeyCode() == KeyEvent.VK_F9)
    {
    if(tblRegistroVentas.getRowCount() <= 0)
    FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de ordenar",txtFechaIni);
    else
    muestraVentaOrdenar();
    } 
    else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      if(tblRegistroVentas.getRowCount() <= 0)
      FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de guardar",txtFechaIni);
      else;
      //exportarDatos();
    } else if(e.getKeyCode() == KeyEvent.VK_F12)
    {
      if(tblRegistroVentas.getRowCount() <= 0)
      FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de imprmir",txtFechaIni);
      else
      imprimir();
      
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(true);
    }
  }

  private void txtFechaIni_keyReleased(KeyEvent e)
  {
  FarmaUtility.dateComplete(txtFechaIni,e);
  }

  private void txtFechaFin_keyReleased(KeyEvent e)
  {
  FarmaUtility.dateComplete(txtFechaFin,e);
  }
  
   private void buscaRegistroVentas(String pFechaInicio, String pFechaFin)
  {
    VariablesReporte.vFechaInicio = pFechaInicio;
    VariablesReporte.vFechaFin = pFechaFin;
    cargaRegistroVentas();
  }
  
   private void cargaRegistroVentas()
  {
    try
    {
      System.out.println(VariablesReporte.vFechaInicio);
      System.out.println(VariablesReporte.vFechaFin);
      DBReportes.cargaListaRegistroVentas(tableModelRegistroVentas,VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin);
      if(tblRegistroVentas.getRowCount() <= 0){
        FarmaUtility.showMessage(this,"No se encontro resultados para la busqueda", txtFechaIni);
        lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
        lblRegistros.setText("" + tblRegistroVentas.getRowCount());
        return ;
      }else{
      FarmaUtility.moveFocus(tblRegistroVentas);
      }
      FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
      lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
      lblRegistros.setText("" + tblRegistroVentas.getRowCount());
      FarmaUtility.moveFocusJTable(tblRegistroVentas);
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n"+sql.getMessage(),txtFechaIni);
    }
  }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  private void listadoDetalleVentas()
  {
    DlgDetalleRegistroVentas dlgDetalleRegistroVentas = new DlgDetalleRegistroVentas(myParentFrame, "", true);
    dlgDetalleRegistroVentas.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
    }
  }
  
  private void resumenVentas()
  {
    DlgResumenVenta dlgResumenVenta = new DlgResumenVenta(myParentFrame,"",true);
    dlgResumenVenta.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
    }
  }
  
  private boolean validarCampos()
  {
    boolean retorno = true;
     if(!FarmaUtility.validarRangoFechas(this,txtFechaIni,txtFechaFin,false,true,true,true))
      retorno = false;
          
    return retorno;
  }
  
    /**
     * Fecha Modificación: 05/01/2007
     * Usuario: Luis Reque
     * Descripción: Se realiza el ordenamiento por Tipo y Nro. de Comprobante a la vez, no por separados.
     * */
  private void muestraVentaOrdenar()
  {
    DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame,"Ordenar",true);
    //String [] IND_DESCRIP_CAMPO = {"Correlativo", "Tipo", "No.Comprob", "Fecha","Cliente", "Monto"};
    //String [] IND_CAMPO = {"0","1","2","11","4","5"};
    
    String [] IND_DESCRIP_CAMPO = {"Correlativo", "Tipo y No.Comprob", "Fecha","Cliente", "Monto"};
    String [] IND_CAMPO = {"0","12","11","4","6"};
    
    System.out.println("Campo " + IND_DESCRIP_CAMPO[1] );
    VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEREGISTROVENTAS ;
    FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(),
                                   VariablesReporte.vNombreInHashtable,
                                   IND_CAMPO,
                                   IND_DESCRIP_CAMPO,
                                   true);
    dlgOrdenar.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
      FarmaUtility.ordenar(tblRegistroVentas,tableModelRegistroVentas,VariablesReporte.vCampo,VariablesReporte.vOrden);
      tblRegistroVentas.repaint();
    }
    
  }
  
  private void exportarDatos() {

		File lfFile = new File("C:\\");
		JFileChooser filechooser = new JFileChooser(lfFile);
		filechooser.setDialogTitle("Seleccione el directorio destino");
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showSaveDialog(this.myParentFrame) != JFileChooser.APPROVE_OPTION)
			return;
		File fileChoosen = filechooser.getSelectedFile();
		String ruta = fileChoosen.getAbsolutePath();

		FarmaUtility.saveFileRuta(ruta,
				ConstantsReporte.columnsListaRegistroVentas,
				tblRegistroVentas, new int[] { 20, 10, 20, 20,40,25,60,20,0,0,0,0 });

		FarmaUtility.showMessage(this, "Los datos se exportaron correctamente",
				txtFechaIni);

	}
  
  private void imprimir() {
		if (!FarmaUtility.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
			return;

		//FarmaPrintService vPrint = new FarmaPrintService(45,
		FarmaPrintService vPrint = new FarmaPrintService(66,
				FarmaVariables.vImprReporte, true);
               System.out.println(FarmaVariables.vImprReporte);
		if (!vPrint.startPrintService()) {
			FarmaUtility.showMessage(this,
					"No se pudo inicializar el proceso de impresión",
					txtFechaIni);
			return;
		}

		try {

			String fechaActual = FarmaSearch
					.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
			String campoAlt = "________";

			vPrint.setStartHeader();
			vPrint.activateCondensed();
			vPrint.printBold(FarmaPRNUtility.llenarBlancos(40)
					+ " REPORTE  DE REGISTRO DE VENTAS", true);
          vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);  
			vPrint.printBold("Fecha: " + fechaActual, true);
      vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
      vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
      vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
			vPrint
					.printLine(
							"========================================================================================================================",
							true);
			vPrint
					.printBold(
							"NroPedido   Tipo  NroComprobante  Fecha                Cliente          Estado             Monto   Ruc       ",
							true);
			vPrint
					.printLine(
							"=========================================================================================================================",
							true);
			vPrint.deactivateCondensed();
			vPrint.setEndHeader();
			for (int i = 0; i < tblRegistroVentas.getRowCount(); i++) {

				vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i, 0), 11)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,1), 5)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,2), 15)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,3), 20)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,4), 16)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,5), 12)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,6), 13)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,7), 12) , true);
			}

			vPrint.activateCondensed();
			vPrint
					.printLine(
							"==========================================================================================================================",
							true);
			vPrint.printBold("Registros Impresos: "
					+ FarmaPRNUtility.alinearDerecha(
              FarmaUtility.formatNumber(tblRegistroVentas
							.getRowCount(), ",##0"),10)
          + FarmaPRNUtility.alinearDerecha(lblTotalMonto.getText(),66)
					+ FarmaPRNUtility.llenarBlancos(11), true);
			vPrint.deactivateCondensed();
			vPrint.endPrintService();
		} catch (SQLException ex) {
			ex.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al imprimir : \n"+ex.getMessage(),null);			
		}
	}
  
  private void busqueda()
  {
    if(validarCampos()){
      txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
      txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
      String FechaInicio = txtFechaIni.getText().trim();
      String FechaFin = txtFechaFin.getText().trim();
      if (FechaInicio.length() > 0 || FechaFin.length() > 0 )
      {
      char primerkeyCharFI = FechaInicio.charAt(0);
      char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length()-1);
      char primerkeyCharFF = FechaFin.charAt(0);
      char ultimokeyCharFF = FechaFin.charAt(FechaFin.length()-1);
      
        if ( !Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
             !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)){
              buscaRegistroVentas(FechaInicio,FechaFin);
        }
        else
          FarmaUtility.showMessage(this,"Ingrese un formato valido de fechas",txtFechaIni); 
      }
      else
      FarmaUtility.showMessage(this,"Ingrese datos para la busqueda",txtFechaIni);
    
    }
  }

  private void this_windowClosing(WindowEvent e)
  {
  FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  /**NUEVO!
   * @Autor:   
   * @Fecha:  23/04/2007
   * */
  private void cargaListaPedAnulNoCob()
  {
    try
    {
      DBReportes.cargaListaPedidosAnulNoCob(tableModelRegistroVentas,VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin);
      if(tblRegistroVentas.getRowCount() <= 0){
        FarmaUtility.showMessage(this,"No se encontro resultados para la busqueda", txtFechaIni);
        lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
        lblRegistros.setText("" + tblRegistroVentas.getRowCount());
        return ;
      }else{
        FarmaUtility.moveFocus(tblRegistroVentas);
      }
      FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
      lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
      lblRegistros.setText("" + tblRegistroVentas.getRowCount());
      FarmaUtility.moveFocusJTable(tblRegistroVentas);
    } catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n"+sql.getMessage(),txtFechaIni);
    }
  }
}