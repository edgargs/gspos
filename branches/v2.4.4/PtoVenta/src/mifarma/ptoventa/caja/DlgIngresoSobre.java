package mifarma.ptoventa.caja;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
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
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.util.ArrayList;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

import mifarma.ptoventa.caja.reference.ConstantsSobres;
import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.caja.reference.VariablesSobre;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoSobre.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ     03.11.2009   Creación<br>
 * JCORTEZ     28.03.2010   Modificado<br>
 * <br>
 * @AUTHOR JORGE LUIS CORTEZ ALVAREZ<br>
 * @VERSION 2.0<br>
 *
 */
public class DlgIngresoSobre extends JDialog 
{
  private static final Logger log = LoggerFactory.getLogger(DlgIngresoSobre.class);

  private FarmaTableModel tableModelLista;
  private Frame myParentFrame;

  private final int COL_FEC_REM = 0;
  private final int COL_NUM_REM = 1;
  
  private final int COL_ORDEN = 8;
                                
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
  private JPanelTitle pnlTitulo = new JPanelTitle();
  private JPanelTitle pnlResultados = new JPanelTitle();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblSobres = new JTable();
  private JButtonLabel btnPeriodo = new JButtonLabel();
  private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JButtonLabel btnListado = new JButtonLabel();
  private JLabel lblRegsitros_T = new JLabel();
  private JLabel lblRegistros = new JLabel();


    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();
    private JLabel lblRegistros1 = new JLabel();
    private JComboBox cmbMoneda = new JComboBox();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblTotal = new JLabelWhite();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JButtonFunction btnAgregar = new JButtonFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelOrange lblNomCajero = new JLabelOrange();
    private JLabelOrange jLabelOrange3 = new JLabelOrange();
    private JLabelOrange lblTipoCambio = new JLabelOrange();
    private JLabelOrange jLabelOrange5 = new JLabelOrange();
    private JLabelOrange lblFecha = new JLabelOrange();
    private JLabelOrange jLabelOrange7 = new JLabelOrange();
    private JLabelOrange lblCaja = new JLabelOrange();
    private JLabelWhite T_lblTotalVenta = new JLabelWhite();
    private JLabelWhite lblTotalVenta = new JLabelWhite();

    public DlgIngresoSobre()
  {
    this(null, "", false);
  }

  public DlgIngresoSobre(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(585, 293));
    this.getContentPane().setLayout(borderLayout1);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Ingreso Sobres");
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
    pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 570, 85));
        pnlCriterioBusqueda.setBackground(SystemColor.activeCaptionText);
        pnlTitulo.setBounds(new Rectangle(5, 95, 570, 20));
    pnlResultados.setBounds(new Rectangle(5, 215, 570, 20));
    jScrollPane1.setBounds(new Rectangle(5, 115, 570, 100));
    tblSobres.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblVentasVendedor_keyPressed(e);
        }
      });
    btnPeriodo.setText("Monto :");
    btnPeriodo.setBounds(new Rectangle(15, 55, 60, 20));
    btnPeriodo.setMnemonic('p');
        btnPeriodo.setForeground(new Color(255, 130, 14));
        btnPeriodo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPeriodo_actionPerformed(e);
        }
      });
    txtMonto.setBounds(new Rectangle(80, 55, 110, 20));
    txtMonto.setLengthText(6);
        txtMonto.setText("0.00");
        txtMonto.addKeyListener(new KeyAdapter()
      {
                    public void keyTyped(KeyEvent e) {
                        txtMonto_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtMonto_keyPressed(e);
                    }
                });
        btnListado.setText("Listado de Sobres :");
    btnListado.setBounds(new Rectangle(5, 0, 345, 20));
    btnListado.setMnemonic('l');
        btnListado.setToolTipText("Listado de Sobres");
        btnListado.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnListado_actionPerformed(e);
                    }
      });
    lblRegsitros_T.setText("Registros :");
    lblRegsitros_T.setBounds(new Rectangle(445, 0, 65, 20));
    lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
    lblRegsitros_T.setForeground(Color.white);
    lblRegistros.setText("0");
    lblRegistros.setBounds(new Rectangle(515, 0, 30, 20));
    lblRegistros.setFont(new Font("SansSerif", 1, 11));
    lblRegistros.setForeground(Color.white);
    lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);


        lblF2.setBounds(new Rectangle(5, 240, 135, 20));
    lblF2.setText("[ F2 ] Eliminar Sobre");
        lblEsc.setBounds(new Rectangle(485, 240, 90, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    //lblF10.setBounds(new Rectangle(150, 370, 135, 20));
    //lblF10.setText("[ F8 ]Guardar Archivo");
    lblRegistros1.setText("0");
    lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
    lblRegistros1.setFont(new Font("SansSerif", 1, 11));
    lblRegistros1.setForeground(Color.white);
    lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
        //jContentPane.add(lblF10, null);
        cmbMoneda.setBounds(new Rectangle(80, 30, 110, 20));
        cmbMoneda.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbMoneda_keyPressed(e);
                    }
                });
        jLabelWhite1.setText("Total Ingreso :  S/.");
        jLabelWhite1.setBounds(new Rectangle(250, 0, 105, 20));
        jLabelWhite1.setToolTipText("Total Ingreso");
        lblTotal.setText("0");
        lblTotal.setBounds(new Rectangle(360, 0, 70, 20));
        jLabelFunction1.setBounds(new Rectangle(350, 240, 130, 20));
        jLabelFunction1.setText("[ F11 ] Aceptar");
        btnAgregar.setText("Agregar");
        btnAgregar.setBounds(new Rectangle(450, 25, 90, 25));
        btnAgregar.setFont(new Font("Arial Black", 0, 11));
        btnAgregar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnAgregar_actionPerformed(e);
                    }
                });
        jButtonLabel1.setText("Moneda :");
        jButtonLabel1.setBounds(new Rectangle(15, 30, 60, 20));
        jButtonLabel1.setMnemonic('M');
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        jLabelOrange1.setText("Cajero :");
        jLabelOrange1.setBounds(new Rectangle(15, 5, 65, 20));
        lblNomCajero.setText("Jorge Luis Cortez Alvarez");
        lblNomCajero.setBounds(new Rectangle(80, 0, 150, 30));
        lblNomCajero.setForeground(new Color(43, 141, 39));
        jLabelOrange3.setText("Tipo Cambio :");
        jLabelOrange3.setBounds(new Rectangle(230, 30, 80, 20));
        lblTipoCambio.setText("2.89");
        lblTipoCambio.setBounds(new Rectangle(320, 30, 60, 20));
        lblTipoCambio.setForeground(new Color(43, 141, 39));
        jLabelOrange5.setText("Fecha :");
        jLabelOrange5.setBounds(new Rectangle(230, 55, 55, 20));
        lblFecha.setText("25/03/2010 18:02:22");
        lblFecha.setBounds(new Rectangle(320, 55, 115, 20));
        lblFecha.setForeground(new Color(43, 141, 39));
        jLabelOrange7.setText("Caja :");
        jLabelOrange7.setBounds(new Rectangle(230, 5, 50, 20));
        lblCaja.setText("1");
        lblCaja.setBounds(new Rectangle(320, 10, 25, 15));
        lblCaja.setForeground(new Color(43, 141, 39));
        T_lblTotalVenta.setText("Total Venta Acumulada : S/.");
        T_lblTotalVenta.setBounds(new Rectangle(5, 0, 155, 20));
        T_lblTotalVenta.setToolTipText("Total Venta Acumulada : S/.");
        lblTotalVenta.setText("0");
        lblTotalVenta.setBounds(new Rectangle(165, 0, 80, 20));
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF2, null);
        jScrollPane1.getViewport().add(tblSobres, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        pnlTitulo.add(btnListado, null);
        pnlTitulo.add(lblRegistros, null);
        pnlTitulo.add(lblRegsitros_T, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlResultados.add(lblTotalVenta, null);
        pnlResultados.add(T_lblTotalVenta, null);
        pnlResultados.add(lblTotal, null);
        pnlResultados.add(jLabelWhite1, null);
        pnlResultados.add(lblRegistros1, null);
        pnlCriterioBusqueda.add(lblCaja, null);
        pnlCriterioBusqueda.add(jLabelOrange7, null);
        pnlCriterioBusqueda.add(lblFecha, null);
        pnlCriterioBusqueda.add(jLabelOrange5, null);
        pnlCriterioBusqueda.add(lblTipoCambio, null);
        pnlCriterioBusqueda.add(jLabelOrange3, null);
        pnlCriterioBusqueda.add(lblNomCajero, null);
        pnlCriterioBusqueda.add(jLabelOrange1, null);
        pnlCriterioBusqueda.add(jButtonLabel1, null);
        pnlCriterioBusqueda.add(btnAgregar, null);
        pnlCriterioBusqueda.add(cmbMoneda, null);
        pnlCriterioBusqueda.add(txtMonto, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ********************************************************************** */
  /*                            METODO INITIALIZE                           */
  /* ********************************************************************** */
  private void initialize()
  {
    initTableListaVentasVendedor();    
  }

  /* ********************************************************************** */
  /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
  /* ********************************************************************** */
  private void initTableListaVentasVendedor()
  {
      

      tblSobres.getTableHeader().setReorderingAllowed(false);
      tblSobres.getTableHeader().setResizingAllowed(false);
      
      tableModelLista = new FarmaTableModel(ConstantsCaja.columnsListaSobresTmp,ConstantsCaja.defaultValuesListaSobresTmp,0);
      FarmaUtility.initSimpleList(tblSobres,tableModelLista,ConstantsCaja.columnsListaSobresTmp);
      cargaCombo();
      
    //dubilluz 20.07.2010 
    // YA NO LISTA LOS SOBRES ANTERIORES REGISTRADOS
    //lista sobres existentes
    ///listarSobres();
        
        if(tblSobres.getRowCount()>0){

        }
  }
  
    private void cargaCombo()
    {
      FarmaLoadCVL.loadCVLfromArrays(cmbMoneda,ConstantsCaja.HASHTABLE_MONEDASOBRES,
                                        ConstantsCaja.MONEDAS_COD,
                                        ConstantsCaja.MONEDAS_DESC,true);
    } 
  
  private void this_windowOpened(WindowEvent e)
  {
      //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
      if(FarmaVariables.vTipCambio==0)
      {   FarmaUtility.showMessage(this, 
                                  "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción", 
                                  null);
          cerrarVentana(false);
      }
      else
      {
          FarmaUtility.centrarVentana(this);
          FarmaUtility.moveFocus(cmbMoneda);
          lblRegistros.setText(tblSobres.getRowCount()+"");
          lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,4)));
          lblNomCajero.setText(FarmaVariables.vIdUsu);
          lblCaja.setText(VariablesCaja.vNumCaja);
          lblTipoCambio.setText(VariablesCaja.vValTipoCambioPedido);
          cargarFecha();
          lblTotalVenta.setVisible(false); //INI ASOSA, 03.06.2010
          T_lblTotalVenta.setVisible(false); //FIN ASOSA, 03.06.2010
      }
  }
  
  
  /* ********************************************************************** */
  /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
  /* ********************************************************************** */
  
  private void txtFechaFin_keyPressed(KeyEvent e)
  {
      FarmaGridUtils.aceptarTeclaPresionada(e, tblSobres,null, 2);
      if(e.getKeyCode()==KeyEvent.VK_ENTER){
        /* if(txtFechaFin.getText().trim().length()>1){
          //FarmaUtility.moveFocus(btnBuscar);
          btnBuscar.doClick();
         }*/
      }
      chkKeyPressed(e);
  }

  private void tblVentasVendedor_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }


    private void btnPeriodo_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtMonto);
  }
  
  private void btnListado_actionPerformed(ActionEvent e)
  {
    if(tblSobres.getRowCount() > 0)
    {
      FarmaGridUtils.showCell(tblSobres, 0, 0);  
      FarmaUtility.moveFocus(tblSobres);
    }
      
    
  }
    
    private void this_windowClosing(WindowEvent e)
    {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

  private void btnBuscar_actionPerformed(ActionEvent e) {
         /* if(validarCampos()){
           listarSobres();
          }*/
 }
  
  /* ********************************************************************** */
  /*                            METODOS AUXILIARES                          */
  /* ********************************************************************** */
  private void chkKeyPressed(KeyEvent e)
  {
    
    if(UtilityPtoVenta.verificaVK_F1(e))
    {
     
    }else if(UtilityPtoVenta.verificaVK_F2(e))
    {
        String ind="";
        ind=tblSobres.getValueAt(tblSobres.getSelectedRow(),5).toString();
        if(ind.trim().equalsIgnoreCase("S")){
                if(JConfirmDialog.rptaConfirmDialog(this, "¿Esta seguro de eliminar el sobre con código?")){
                  borrarSobre();    
                  lblRegistros.setText(tblSobres.getRowCount()+"");
                 }
         }else {
                if(JConfirmDialog.rptaConfirmDialog(this, "¿Esta seguro de eliminar el sobre agregado?")){
                   borrarSobre();    
                    lblRegistros.setText(tblSobres.getRowCount()+"");
               }
         }
    } else if(UtilityPtoVenta.verificaVK_F11(e)){
       
        /* 
         * //daubilluz - 24.05.2010
        if(tblSobres.getSelectedRow()<0){
            FarmaUtility.showMessage(this, "Seleccione sobre agregado",cmbMoneda);
            FarmaUtility.moveFocus(tblSobres);
            return;
        }
        */
        int cant=0;
       if(tblSobres.getRowCount()>0){
            for(int i=0;i<tblSobres.getRowCount(); i++){
                 if(((String)tblSobres.getValueAt(i,5)).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                 {
                   cant++;
                 }
                }
            if(cant>0){
                if(JConfirmDialog.rptaConfirmDialog(this, "¿Esta seguro de registrar los sobres agregados?")){
                    if (cargaValidaLogin()) {
                        guardarCreados();
                    }
                    else {
                        FarmaUtility.showMessage(this, "Necesita autorización del Jefe del Local",cmbMoneda);
                    }
                }
            }else
                FarmaUtility.showMessage(this, "No existen sobres sin codigo",cmbMoneda);
            
        }else{
            FarmaUtility.showMessage(this, "Debe ingresar sobres.",cmbMoneda);
        }
          
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      this.setVisible(false);
      
    }
  }

    
    private  void cargarFecha(){
      try{
        String FechaInicio=FarmaSearch.getFechaHoraBD(2);
        lblFecha.setText(FechaInicio);
      }catch(SQLException sql){
        log.error("",sql);
      }
    }
    
   /**
     * Se ordena el listado de remitos
     * */
    private void ordenar() {

        DlgOrdenar dlgordenar = new DlgOrdenar(myParentFrame, "", true);
        dlgordenar.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaUtility.ordenar(tblSobres, tableModelLista, 
                                 Integer.parseInt(VariablesCaja.vColumna), 
                                 VariablesCaja.vOrden);
            tblSobres.repaint();
            //FarmaUtility.setearPrimerRegistro(tblRemitos,txtFecha,2);
        }

    }

     private void listarSobres(){

    try
    {  // if(FechaIni.length()>0&&FechaFin.length()>0)
        DBCaja.getListaSobres(tableModelLista,VariablesCaja.vSecMovCaja);//solo sobres por caja
        /*if(tblSobres.getRowCount()>0){
           FarmaUtility.ordenar(tblSobres, tableModelLista, COL_ORDEN, FarmaConstants.ORDEN_DESCENDENTE);
           FarmaUtility.moveFocus(txtFechaIni);
        }*/
        lblRegistros.setText(tblSobres.getRowCount()+"");
    }catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al listar sobres",cmbMoneda);
    }
   }

    private void nuevoRemito() {

        DlgNuevoRemito dlgnuevo = new DlgNuevoRemito(myParentFrame, "", true);
        dlgnuevo.setVisible(true);
        if (FarmaVariables.vAceptar) {
            txtMonto.setText("");
            // listarSobres();
        }
    }


    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    
    }


    private void cmbMoneda_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                VariablesCaja.vCodTipoMon = FarmaLoadCVL.getCVLCode(ConstantsCaja.HASHTABLE_MONEDASOBRES,cmbMoneda.getSelectedIndex());
                VariablesCaja.vDescTipoMon = FarmaLoadCVL.getCVLDescription(ConstantsCaja.HASHTABLE_MONEDASOBRES, VariablesCaja.vCodTipoMon);
                FarmaUtility.moveFocus(txtMonto);
                log.debug("COD TIPO MONEDA-->"+VariablesCaja.vCodTipoMon);
                log.debug("DESC TIPO MONEDA-->"+VariablesCaja.vDescTipoMon);
            }
            else
              chkKeyPressed(e);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
    
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(txtMonto.getText().trim().length()>0&&validaMontoIngresado())
            {
               btnAgregar.doClick();
            }
            else
            {
                FarmaUtility.moveFocus(cmbMoneda);
            }
        } else
          chkKeyPressed(e);  
    
    }
    
    private void ingresoSobre(){
    
        if(FarmaVariables.vEconoFar_Matriz){
          FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblSobres);
          return;
        }
       //UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);
       // log.debug("VariablesCajaElectronica.vUsuarioCajero-->"+VariablesCajaElectronica.vUsuarioCajero);
        //if(VariablesCajaElectronica.vUsuarioCajero)
          adicionaDetallePago();
       // else
       //   FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblSobres);
    
    }
    
    private void adicionaDetallePago()
    {
     if(!obtieneDatosFormaPago())return;
      if(!validaMontoIngresado()) return;
      
      operaListaDetallePago();
      FarmaUtility.moveFocus(cmbMoneda);
        //lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,4)));
        // limpiaVariablesFormaPago();
        //btnAdicionar.setEnabled(false);
      
      //cmbMoneda.setEnabled(false);
       lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,4)));
      FarmaLoadCVL.setSelectedValueInComboBox(cmbMoneda, ConstantsCaja.HASHTABLE_MONEDASOBRES, ConstantsCaja.EFECTIVO_SOLES);
      
      //btnFormaPago.doClick();
    }
    
    
    private boolean obtieneDatosFormaPago()
    {
        //ERIOS 04.02.2014 Se lee combo de moneda
        boolean valor=true;
        log.debug("*****OBTIENE DATOS PARA INGRESAR");
        VariablesCaja.vCodTipoMon = 
            FarmaLoadCVL.getCVLCode(ConstantsSobres.HASHTABLE_MONEDASOBRES, 
                                    cmbMoneda.getSelectedIndex());
      if(VariablesCaja.vCodTipoMon.equalsIgnoreCase(ConstantsCaja.EFECTIVO_SOLES)){
          VariablesCaja.vCodFormaPagoTmp = ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES;
          VariablesCaja.vDescFormaPagoTmp = "EFECTIVO SOLES";
      }else if(VariablesCaja.vCodTipoMon.equalsIgnoreCase(ConstantsCaja.EFECTIVO_DOLARES)){
          VariablesCaja.vCodFormaPagoTmp = ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES;
          VariablesCaja.vDescFormaPagoTmp = "EFECTIVO DOLARES";     
      }

      String codMoneda = FarmaLoadCVL.getCVLCode(ConstantsCaja.HASHTABLE_MONEDASOBRES,cmbMoneda.getSelectedIndex());
      VariablesCaja.vCodMonedaPagoTmp = codMoneda;
      VariablesCaja.vDescMonedaPagoTmp = FarmaLoadCVL.getCVLDescription(ConstantsCaja.HASHTABLE_MONEDASOBRES, codMoneda);
      log.debug(" JCORTEZ ::::txtMonto.getText()" + FarmaUtility.getDecimalNumber(txtMonto.getText().trim()));
      //VariablesCaja.vValMontoPagadoTmp = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtMonto.getText().trim()));
      VariablesCaja.vValMontoPagadoTmp = FarmaUtility.getDecimalNumber(txtMonto.getText().trim())+"";
        log.debug("JCORTEZ :::: VariablesCaja.vValMontoPagadoTmp" +VariablesCaja.vValMontoPagadoTmp);
      
      if(codMoneda.equalsIgnoreCase(FarmaConstants.CODIGO_MONEDA_SOLES))
        VariablesCaja.vValTotalPagadoTmp = VariablesCaja.vValMontoPagadoTmp;
      else
        VariablesCaja.vValTotalPagadoTmp = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagadoTmp) * FarmaVariables.vTipCambio);
        
      
        log.debug(" 1.VariablesCaja.vCodFormaPagoTmp" + VariablesCaja.vCodFormaPagoTmp);
        log.debug("2.VariablesCaja.vIndTarjetaSeleccionada" + VariablesCaja.vDescFormaPagoTmp);
        log.debug("3.VariablesCaja.vDescFormaPagoTmp" + VariablesCaja.vCodMonedaPagoTmp);
        log.debug("4.VariablesCaja.vIndTarjetaSeleccionada" + VariablesCaja.vDescMonedaPagoTmp);
        log.debug("5.VariablesCaja.vValMontoPagadoTmp" +VariablesCaja.vValMontoPagadoTmp);
        log.debug("6.VariablesCaja.vValTotalPagadoTmp"+VariablesCaja.vValTotalPagadoTmp);
        
        //validar monto ingresado no mayor al existente en ventas
         try{
                String sTotal=lblTotal.getText().trim();
                String TotalVentas=DBCaja.getMontoVentas(VariablesCaja.vSecMovCaja);
             
               /*T_lblTotalVenta.setVisible(true); //ASOSA, 16.06.2010
               lblTotalVenta.setVisible(true);*/
               lblTotalVenta.setText(TotalVentas);
             
                double dTotal=Double.parseDouble(sTotal);
                //double dIngreso=Double.parseDouble(VariablesCaja.vValTotalPagadoTmp);
                double dIngreso=FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagadoTmp); //ASOSA, 16.06.2010
              log.debug("TotalVentas: "+TotalVentas);
               double dVentas=FarmaUtility.getDecimalNumber(TotalVentas.trim());
              log.debug("Ventas turno: "+dVentas);
              log.debug("Total Acumulado: "+dTotal);
              log.debug("Total Ingreso: "+dIngreso);
              if((dTotal+dIngreso)>(dVentas)){
                    //valor=false;
                  //FarmaUtility.showMessage(this, "El monto total acumulado supera las ventas realizadas. Verifique!!!", txtMonto);  
                  //NO DEBE DE VALIDAR NADA por sospechas de calculo de monto.
                  valor=true;
              }
          }catch(SQLException e ){
                 log.error("",e);
          }
          
        return valor;
    }
    
    private boolean validaMontoIngresado()
    {
      log.debug("VariablesCaja.vIndTarjetaSeleccionada valida monto : " + VariablesCaja.vIndTarjetaSeleccionada);
      String monto = txtMonto.getText().trim();
      
      if(monto.equalsIgnoreCase("") || monto.length() <= 0)
      {
        FarmaUtility.showMessage(this, "Ingrese un monto.", monto);
        return false;
      }
      if(!FarmaUtility.isDouble(monto))
      {
        FarmaUtility.showMessage(this, "Ingrese un monto válido.", monto);
        return false;
      }
      if(FarmaUtility.getDecimalNumber(monto) == 0)
      {
        FarmaUtility.showMessage(this, "Ingrese un monto mayor a 0.", monto);
        return false;
      }
      if(FarmaUtility.getDecimalNumber(monto) < 0)
      {
         FarmaUtility.showMessage(this, "El monto ingresado es negativo.", monto);
          return false;
      }

      return true;
    }
    
    private void operaListaDetallePago()
    {
      ArrayList myArray = new ArrayList();
      myArray.add(VariablesCaja.vCodFormaPagoTmp);
      myArray.add(VariablesCaja.vDescFormaPagoTmp);
      myArray.add(VariablesCaja.vDescMonedaPagoTmp);
      myArray.add(VariablesCaja.vValMontoPagadoTmp);
      myArray.add(VariablesCaja.vValTotalPagadoTmp);
      myArray.add("N");//Ind si ya se asocio a un sobre
      myArray.add(" ");//codigo sobre
      myArray.add(VariablesCaja.vSecMovCaja);
      myArray.add(VariablesCaja.vCodTipoMon);
      myArray.add("");//Sec
      log.debug("ingreso-->"+myArray);
      tableModelLista.data.add(myArray);
      tableModelLista.fireTableDataChanged();
      FarmaUtility.moveFocus(tblSobres);
      txtMonto.setText("0.00");
    }
    
    private void borrarSobre(){
    
        if(FarmaVariables.vEconoFar_Matriz){
          FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblSobres);
          return;
        }
        //UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);
        //if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N))
          //eliminando
          eliminaFormaPagoEntrega();
        /*else
          FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblFormasPago); */
    }
    
    
     private void eliminaFormaPagoEntrega()
     {
     
       String indSobre= tblSobres.getValueAt(tblSobres.getSelectedRow(),5).toString();
         String CodSobre= tblSobres.getValueAt(tblSobres.getSelectedRow(),6).toString();
         String CodFormPago= tblSobres.getValueAt(tblSobres.getSelectedRow(),0).toString();
         
       if(indSobre.trim().equalsIgnoreCase("S")&&CodSobre.trim().length()>0&&
           (CodFormPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)||
           CodFormPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES))){
        
       }
         
         
       if(tieneRegistroSeleccionado(tblSobres)){
         guardaDatosDetalle();
           log.debug("Elimina registro Seleccionado, con sobre?-->"+VariablesCaja.vIndSobreTmp.trim());
         if(VariablesCaja.vIndSobreTmp.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
         {
           //Solo se eliminada registros que no esten asociados a un sobre
           log.debug("Elimina registro Seleccionado");
           eliminaRegistroSeleccionado();
         } else
           {
             try
             {
             //  if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿El sistema eliminará el registro físicamente. Desea Continuar?"))
              // {
                   //Inicio 
                   if (VariablesCaja.vIndSobreTmp.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                      // VariablesCaja.vSecMovCajaTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),7)).trim();
                      VariablesCaja.vSecMovCajaTmp =FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),7);
                       VariablesCaja.vCodFormaPagoTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),0)).trim();
                       //VariablesCaja.vCodTipoMon = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),8)).trim(); 
                       VariablesCaja.vCodTipoMon =FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),8);
                         
                       VariablesCaja.vIndSobreTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),5)).trim();          
                       VariablesCaja.vCodigoSobreTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),6)).trim();
                      //VariablesCaja.vSecTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),9)).trim();
                       VariablesCaja.vSecTmp =FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),9);
                       
                       log.debug("VariablesCaja.vSecMovCajaTmp->"+VariablesCaja.vSecMovCajaTmp);        
                       log.debug("VariablesCaja.vCodFormaPagoTmp->"+VariablesCaja.vCodFormaPagoTmp);
                       log.debug("VariablesCaja.vSecTmp->"+VariablesCaja.vSecTmp);
                       DBCaja.eliminaSobreRegistrado(VariablesCaja.vSecMovCajaTmp,
                                                                VariablesCaja.vCodFormaPagoTmp,
                                                                VariablesCaja.vCodTipoMon,
                                                                VariablesCaja.vCodigoSobreTmp);
                   }
                   eliminaRegistroSeleccionado();//del listado
                   //listarSobres();// solo temporales de sobres futuros
                   lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,4)));
                   FarmaUtility.aceptarTransaccion();
                   //Fin
       
              // }
             } catch (SQLException sql)
             {
               FarmaUtility.liberarTransaccion();
               log.error("",sql);
               if (sql.getErrorCode() == 20001) {
                   FarmaUtility.showMessage(this, "No puede agregar el sobre", null);
               } else if (sql.getErrorCode() == 20002) {
                   FarmaUtility.showMessage(this, "No se puede eliminar el sobre.\n" +"Porque el día ya se asoció a un Remito.",null);
               } else {
                   //dubilluz - 20.07.2010
                   if(sql.getErrorCode() > 20000){
                       FarmaUtility.showMessage(this, 
                                                sql.getMessage().substring(10, 
                                                sql.getMessage().indexOf("ORA-06512")), 
                                                null);

                   }
                   else
                       FarmaUtility.showMessage(this,"Ocurrió un error al eliminar físicamente \n " + sql.getMessage(), null);
               }
             }
           }
           lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,4)));
       }else{
           FarmaUtility.showMessage(this,"Debe seleccionar un registro.", null);
       }
       
     }
     
    private boolean tieneRegistroSeleccionado(JTable pTabla) {
          boolean rpta = false;
          if (pTabla.getSelectedRow() != -1) {
                  rpta = true;
          }
          return rpta;
   }
   
    private void guardaDatosDetalle()
    {
      //VariablesCaja.vSecMovCajaTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),7)).trim();
      VariablesCaja.vSecMovCajaTmp=FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),7);
       
      VariablesCaja.vCodFormaPagoTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),0)).trim();
      //VariablesCaja.vCodTipoMon = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),8)).trim(); 
      VariablesCaja.vCodTipoMon=FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),8);
        
      VariablesCaja.vIndSobreTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),5)).trim();          
      VariablesCaja.vCodigoSobreTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),6)).trim();
      //VariablesCaja.vSecTmp = ((String)tblSobres.getValueAt(tblSobres.getSelectedRow(),9)).trim();
      VariablesCaja.vSecTmp=FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),9);
      
        log.debug("1.VariablesCaja.vSecMovCajaTmp-->" + VariablesCaja.vSecMovCajaTmp);
        log.debug("2.VariablesCaja.vCodFormaPagoTmp-->" + VariablesCaja.vCodFormaPagoTmp);
        log.debug("3.VariablesCaja.vCodTipoMon-->" + VariablesCaja.vCodTipoMon);
        log.debug("4.VariablesCaja.vIndSobreTmp-->" + VariablesCaja.vIndSobreTmp);
        log.debug("5.VariablesCaja.vCodigoSobreTmp-->" +VariablesCaja.vCodigoSobreTmp);
        log.debug("6.VariablesCaja.vSecTmp-->" +VariablesCaja.vSecTmp);
    }
    
    private void eliminaRegistroSeleccionado()
    {
      int seleccion = tblSobres.getSelectedRow();
      tableModelLista.deleteRow(seleccion);
      tableModelLista.fireTableDataChanged();
      if(seleccion == 0)
       FarmaUtility.moveFocus(tblSobres);
      else{
       FarmaGridUtils.showCell(tblSobres,seleccion-1,0);
       FarmaUtility.moveFocus(tblSobres);
      }
    }
    
    private void guardarCreados()
    {
      String pIndSobre = FarmaConstants.INDICADOR_N;
      String pSecSobre = "";
      int cant=0;
      String tip="";
        //daubilluz - 24.05.2010
      String pSecMovCaja = "";
      try 
      {
        for(int i=0;i<tblSobres.getRowCount(); i++)
            {
             //daubilluz - 24.05.2010
             pSecMovCaja = FarmaUtility.getValueFieldArrayList(tableModelLista.data,i,7).trim();
             //        
            
             if(((String)tblSobres.getValueAt(i,5)).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
             {//INICIO 
                 if(((String)tblSobres.getValueAt(i,2)).trim().equalsIgnoreCase("SOLES")) 
                     tip="01";
                 else if    (((String)tblSobres.getValueAt(i,2)).trim().equalsIgnoreCase("DOLARES")) 
                     tip="02";
                     
                 log.debug("Tipo Moneda-->"+tip); 
                 
              log.debug("registro seleccionado-->"+i); 
              /*   
              log.debug("FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),7):"+FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),7)); 
              log.debug("pSecMovCaja:"+pSecMovCaja); 
              */
              pSecSobre = //DBCaja.agregaSobre(FarmaUtility.getValueFieldArrayList(tableModelLista.data,tblSobres.getSelectedRow(),7),
                            /*DBCaja.agregaSobre(pSecMovCaja, ASOSA, 11.06.2010, porque mejor utilizo un metodo deDUBILLUZ q tiene el bloqueo
                                                       ((String)tblSobres.getValueAt(i,0)).trim(),
                                                        tip,
                                                       ((String)tblSobres.getValueAt(i,3)).trim(),
                                                       ((String)tblSobres.getValueAt(i,4)).trim()); */
                            DBCaja.getRealizaAccionSobreTMP_02(ConstantsSobres.ACC_INGRESO, //ASOSA, 11.06.2010 usando metodo mejorado
                                                            " ",
                                                            " ",
                                                            pSecMovCaja,
                                                           ((String)tblSobres.getValueAt(i,0)).trim(),
                                                            tip,
                                                           ((String)tblSobres.getValueAt(i,3)).trim(),
                                                           ((String)tblSobres.getValueAt(i,4)).trim(),
                                                               VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE
                                                               );
              log.debug("Codigo sobre-->"+pSecSobre); 
               pIndSobre = FarmaConstants.INDICADOR_S;
               
                 FarmaUtility.aceptarTransaccion();
                /*INICION DE IMPRESION DE SOBRES EN VOUCHER ASOSA, 26.07.2010*/
                imprimeSobresDeclarados(this,pSecMovCaja,pSecSobre); 
               /*FIN DE IMPRESION DE SOBE4S EN VOUCHER*/
                /*tblSobres.setValueAt(pIndSobre,i,5); ASOSA, 11.06.2010, porque inmediatamente se cierra la pantalla y no tiene sentido que se setee ademas q no es lo mas adecuado
                tblSobres.repaint();
                tblSobres.setValueAt(pSecSobre,i,6);
                tblSobres.repaint();
                
                if(pSecSobre.trim().length()>2)
                 enviaCorreoSobre(pSecSobre);*/
                 
              //FIN
                 cant++;
             }
            }
          FarmaUtility.showMessage(this,
                                   "Recoger Voucher de sobres declarados.",
                                   null);
            log.debug("Cantidad Sobres: "+cant);
            //dubilluz - 20.07.2010
            //FarmaUtility.showMessage(this,"Se registraron correctamente los sobres sin codigo", cmbMoneda);
            //listarSobres();
      } catch (SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        log.error("",sql);

          if (sql.getErrorCode() == 20001) {
              FarmaUtility.showMessage(this, "No puede agregar el sobre.", null);
          } else {
              FarmaUtility.showMessage(this, "Error al grabar sobre \n" +sql.getMessage(), null);
          }
          if(sql.getErrorCode()>20022)
          {
            FarmaUtility.showMessage(this,sql.getMessage().substring(10,sql.getMessage().indexOf("ORA-06512")),null);  
          }else
          {
            FarmaUtility.showMessage(this,"Ocurrió un error al Agregar sobre.\n"+sql,null);
          }
      }
      this.setVisible(false); //ASOSA, 03.06.2010
    }


    private void btnAgregar_actionPerformed(ActionEvent e) {
        int cant=tblSobres.getRowCount();
        if(validaIngresoSobre()||cant<1){
        ingresoSobre();
        lblRegistros.setText(tblSobres.getRowCount()+"");
        } else
        FarmaUtility.showMessage(this, "No puede agregar más sobres.", cmbMoneda); 
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
      FarmaUtility.moveFocus(cmbMoneda);
    }
    
    
    private void enviaCorreoSobre(String codigoSobre){
    
        try{
          DBCaja.enviaCorreoSobre(codigoSobre);
        }catch(SQLException sql){
           log.error("",sql);
           FarmaUtility.showMessage(this,"Ocurrio un error al enviar correo de generacion de sobre\n"+sql.getMessage(),tblSobres);
          }   
    }
    
    /**
     * 
     * Se valida el ingreso de sobre en local
     * @AUTHOR JCORTEZ
     * @SINCE 03.11.09
     * */
    private boolean validaIngresoSobre() {
      boolean valor=false;
      String ind="";
      try
           {
           log.debug("VariablesCaja.vSecMovCaja-->"+VariablesCaja.vSecMovCaja);
            ind=DBCaja.permiteIngreMasSobre();
               log.debug("indPermite mas de un Sobres-->"+ind);
            if(ind.trim().equalsIgnoreCase("S")){
             valor=true;
             }
            
           }catch (SQLException sql){
             valor=false;
             log.error("",sql);
             FarmaUtility.showMessage(this,"Ocurrio un error validar ingreso de sobre.\n"+sql.getMessage(),null);
           }
      return valor;
    }
    

    private boolean cargaValidaLogin()
    {
      VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE = "";  
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      boolean  rpta=false;
      try{
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);
        
        //Dubilluz 27.07.2010
        VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE = FarmaVariables.vNuSecUsu;
          
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
        rpta=FarmaVariables.vAceptar;
         // return rpta;
      } catch (Exception e)
      {
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
        rpta = false;
        log.error("",e);
        FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
      }
      return rpta;
    }
    
    
    /**
     * @author ASOSA
     * @since 26.07.2010
     */
    
    public static void imprimeSobresDeclarados(JDialog pDialogo,String pSecMovCaja, String pSecSobre)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      String pIndProsegur = FarmaConstants.INDICADOR_N;
      boolean indImp = false;
      //if(servicio != null)
      //{
        try
        {
            //SEG_F_CHAR_IND_PROSEGUR
            pIndProsegur = FarmaConstants.INDICADOR_S;
            if(pIndProsegur.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            {
                String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                 String pCodSobre = "";      
                      String vIndImpre = FarmaConstants.INDICADOR_S;
                      log.debug("vIndImpre :"+vIndImpre);
                      if (!vIndImpre.equals("N")) {
                         ArrayList pLista =  new ArrayList();
                         DBCajaElectronica.getSobreDeclarados_02(pSecMovCaja,pLista,pSecSobre);
                         for(int f=0;f<pLista.size();f++){
                             //pCodSobre = FarmaUtility.getValueFieldArrayList(pLista,f,0);
                             String html = DBCajaElectronica.getHtmlSobreDeclarados_02(pSecMovCaja,pSecSobre);                             
                             PrintConsejo.imprimirHtml(html.trim(),VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                             PrintConsejo.imprimirHtml(html.trim(),VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                             indImp = true;
                         }
                         /*
                         if(indImp)
                            FarmaUtility.showMessage(pDialogo,
                                                     "Recoger Voucher de sobres declarados.",
                                                     null);
                            */
                         //break;
                     }
                  //}
                //}              
            }

        }
        catch (SQLException sqlException)
        {          
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al obtener los datos de los sobres a imprimir.", null);
        }
        
      //}
     }
    
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
}
