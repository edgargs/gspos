package mifarma.ptoventa.caja;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
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
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.util.ArrayList;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.*;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;

public class DlgNuevoRemito extends JDialog 
{
  private FarmaTableModel tableModelLista;
    private FarmaTableModel tableModeSobres;
  private Frame myParentFrame;
    private boolean todos=true;

  private final int COL_COD_VEND = 0;
  private final int COL_FEC_VTA = 1;
  private final int COL_TOT_VTA_CIGV = 2;
  private final int COL_TOT_VTA_SIGV = 3;
  private final int COL_GG = 4;
  private final int COL_G = 5;
  private final int COL_OTROS= 6;
  private final int COL_SERVICIOS = 7;
  private final int COL_PORCENTAJE = 8;
  private final int COL_CALCULADO = 9;
  private final int COL_TIPO_FILA = 11;
  private final int COL_SEC_USU = 12;
  private final int COL_ORDEN = 13;
  private final int COL_LOGIN = 14;
  
                                
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
  private JPanelTitle pnlTitulo = new JPanelTitle();
  private JPanelTitle pnlResultados = new JPanelTitle();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblLista = new JTable();
  private JButtonLabel lbl1 = new JButtonLabel();
  private JTextFieldSanSerif txtNumRemito = new JTextFieldSanSerif();
    private JButtonLabel btnListado = new JButtonLabel();
  private JLabel lblRegsitros_T = new JLabel();
  private JLabel lblTotalDolares = new JLabel();


    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel lblTotalSoles = new JLabel();
    private JLabel lblRegsitros_T1 = new JLabel();
    private JLabel lblRegsitros_T2 = new JLabel();
    private JLabel lblCantD = new JLabel();
    private JLabel lblRegsitros_T3 = new JLabel();
    private JLabel lblRegistros1 = new JLabel();
    private JScrollPane scrSobres = new JScrollPane();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JTable tblSobres = new JTable();
    private JButtonLabel btnSobres = new JButtonLabel();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblCantS = new JLabelWhite();
    private JLabel lblPrecinto = new JLabel();
    private JTextField txtPrecinto = new JTextField();

    public DlgNuevoRemito()
  {
    this(null, "", false);
  }

  public DlgNuevoRemito(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(734, 513));
    this.getContentPane().setLayout(borderLayout1);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Nuevo Remito");
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
        pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 715, 40));
    pnlTitulo.setBounds(new Rectangle(5, 525, 715, 10));
    pnlResultados.setBounds(new Rectangle(5, 405, 715, 35));
        pnlResultados.setBackground(Color.white);
        pnlResultados.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jScrollPane1.setBounds(new Rectangle(5, 535, 715, 10));
        tblLista.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        tblLista_keyReleased(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        tblLista_keyPressed(e);
                    }
                });
        lbl1.setText("Número Remito :");
    lbl1.setBounds(new Rectangle(30, 10, 105, 20));
    lbl1.setMnemonic('N');
    lbl1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPeriodo_actionPerformed(e);
        }
      });
    txtNumRemito.setBounds(new Rectangle(130, 10, 250, 20));
      txtNumRemito.setLengthText(10);
        txtNumRemito.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtNumRemito_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtNumRemito_keyTyped(e);
                    }
                });
        btnListado.setText("Listado Dias No Asociados");
    btnListado.setBounds(new Rectangle(5, 5, 345, 15));
        btnListado.setToolTipText("null");
        btnListado.setMnemonic('L');
        btnListado.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnListado_actionPerformed(e);
                    }
                });
        lblRegsitros_T.setText("Total Dolares : $");
    lblRegsitros_T.setBounds(new Rectangle(550, 0, 90, 30));
    lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblTotalDolares.setText("0");
    lblTotalDolares.setBounds(new Rectangle(645, 0, 65, 30));
    lblTotalDolares.setFont(new Font("SansSerif", 1, 13));
        lblTotalDolares.setHorizontalAlignment(SwingConstants.RIGHT);


        lblF11.setBounds(new Rectangle(460, 460, 130, 20));
      lblF11.setText("[ F11 ] Aceptar");

        lblEsc.setBounds(new Rectangle(615, 460, 105, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    //lblF10.setBounds(new Rectangle(150, 370, 135, 20));
    //lblF10.setText("[ F8 ]Guardar Archivo");
    lblRegistros1.setText("0");
    lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
    lblRegistros1.setFont(new Font("SansSerif", 1, 11));
    lblRegistros1.setForeground(Color.white);
    lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
        scrSobres.setBounds(new Rectangle(5, 75, 715, 330));
        jPanelTitle1.setBounds(new Rectangle(5, 50, 715, 25));
        tblSobres.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblSobres_keyPressed(e);
                    }
                });
        btnSobres.setText("Lista de sobres que se asignarán al número de remito ingresado :");
        btnSobres.setBounds(new Rectangle(5, 0, 400, 25));
        btnSobres.setMnemonic('S');
        btnSobres.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnSobres_actionPerformed(e);
                    }
                });
        jLabelWhite1.setText("Nro Sobres Soles :");
        jLabelWhite1.setBounds(new Rectangle(5, 0, 105, 30));
        jLabelWhite1.setForeground(Color.black);
        lblCantS.setBounds(new Rectangle(100, 0, 60, 30));
        lblCantS.setText("0");
        lblCantS.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantS.setForeground(Color.black);
        lblCantS.setFont(new Font("SansSerif", 1, 13));
        lblPrecinto.setText("Precinto :");
        lblPrecinto.setVisible(false);
        lblPrecinto.setBounds(new Rectangle(400, 10, 55, 20));
        lblPrecinto.setForeground(Color.white);
        lblPrecinto.setFont(new Font("Dialog", 1, 11));
        txtPrecinto.setBounds(new Rectangle(465, 10, 165, 20));
        txtPrecinto.setVisible(false);
        txtPrecinto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtPrecinto_keyPressed(e);
                    }
                });
        lblCantD.setText("0");
        lblCantD.setBounds(new Rectangle(480, 0, 45, 30));
        lblCantD.setFont(new Font("SansSerif", 1, 13));
        lblCantD.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegsitros_T3.setText("Nro Sobres Dólares :");
        lblRegsitros_T3.setBounds(new Rectangle(360, 0, 115, 30));
        lblRegsitros_T3.setFont(new Font("SansSerif", 1, 11));
        lblTotalSoles.setText("0");
        lblTotalSoles.setBounds(new Rectangle(270, 0, 70, 30));
        lblTotalSoles.setFont(new Font("SansSerif", 1, 13));
        lblTotalSoles.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegsitros_T1.setText("Total Soles : S/.");
        lblRegsitros_T1.setBounds(new Rectangle(180, 0, 90, 30));
        lblRegsitros_T1.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T2.setText("Total Dolares : $");
        lblRegsitros_T2.setBounds(new Rectangle(-225, 0, 90, 20));
        lblRegsitros_T2.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T2.setForeground(Color.white);
        //jContentPane.add(lblF10, null);
        scrSobres.getViewport().add(tblSobres, null);
        jContentPane.add(scrSobres, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jScrollPane1.getViewport().add(tblLista, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        pnlResultados.add(lblCantS, null);
        pnlResultados.add(jLabelWhite1, null);
        pnlResultados.add(lblRegsitros_T3, null);
        pnlResultados.add(lblCantD, null);
        pnlResultados.add(lblRegsitros_T2, null);
        pnlResultados.add(lblRegistros1, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlResultados.add(lblTotalDolares, null);
        pnlResultados.add(lblRegsitros_T1, null);
        pnlResultados.add(lblTotalSoles, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jPanelTitle1.add(btnSobres, null);
        jContentPane.add(jPanelTitle1, null);
        pnlCriterioBusqueda.add(txtPrecinto, null);
        pnlCriterioBusqueda.add(lblPrecinto, null);
        pnlCriterioBusqueda.add(txtNumRemito, null);
        pnlCriterioBusqueda.add(lbl1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
  
  /* ********************************************************************** */
  /*                            METODO INITIALIZE                           */
  /* ********************************************************************** */
  private void initialize()
  {
    initTableListaVentasVendedor();
      pnlResultados.setVisible(true);
      jScrollPane1.setVisible(false);
      pnlTitulo.setVisible(false);
  }

  /* ********************************************************************** */
  /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
  /* ********************************************************************** */
  private void initTableListaVentasVendedor()
  {
    tableModelLista = new FarmaTableModel(ConstantsCaja.columnsListaFechas,ConstantsCaja.defaultListaFechas,0);
    // FarmaUtility.initSelectList(tblLista,tableModelLista,ConstantsCaja.columnsListaFechas);
    FarmaUtility.initSimpleList(tblLista,tableModelLista,ConstantsCaja.columnsListaFechas);
     
    tableModeSobres = new FarmaTableModel(ConstantsCaja.columnsListaSobresDet,ConstantsCaja.defaultListaSobresDet,0);
    // FarmaUtility.initSelectList(tblLista,tableModelLista,ConstantsCaja.columnsListaFechas);
    FarmaUtility.initSimpleList(tblSobres,tableModeSobres,ConstantsCaja.columnsListaSobresDet);
    //listarFechaVentas();
    
    if(listar()) listaSobres(""); //ASOSA, 22.04.2010
    
    if(tblLista.getRowCount()>-1){
    lblTotalDolares.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,8), 2));
      lblTotalSoles.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,7), 2));
      lblCantD.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,10), 0));
      lblCantS.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,9), 0));
    }
    
    // dubilluz 09.05.2012
    if(UtilityCajaElectronica.getIndImpreRemito_Matricial()){
        lblPrecinto.setVisible(true);
        txtPrecinto.setVisible(true);
    }
          
    
  }
  
  /* ********************************************************************** */
  /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
  /* ********************************************************************** */
  


  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtNumRemito);
    VariablesCaja.vArrayFechasSeleccinadas.clear();
    VariablesCaja.NumRemito="";
  }


    private void btnPeriodo_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtNumRemito);
  }
  
  private void btnListado_actionPerformed(ActionEvent e)
  {
    if(tblLista.getRowCount() > 0)
    {
      FarmaUtility.moveFocus(tblLista);
    }
  }
  
    private void txtNumRemito_keyPressed(KeyEvent e) {
    
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
           if(txtNumRemito.getText().trim().length()>0){
              // if(FarmaUtility.isInteger(txtNumRemito.getText().trim())){
                   if(validaMontoIngresado(txtNumRemito)){
                    txtNumRemito.setText(FarmaUtility.completeWithSymbol(txtNumRemito.getText(), 10, "0", "I")); 
                    if(UtilityCajaElectronica.getIndImpreRemito_Matricial())
                        FarmaUtility.moveFocus(txtPrecinto);
                    else
                        FarmaUtility.moveFocus(txtNumRemito);
                  // }else
                   }else FarmaUtility.showMessage(this,"Valor ingresado invalido!!!", txtNumRemito);  
           }
        }
        chkKeyPressed(e);
    }

  
     
    private void this_windowClosing(WindowEvent e)
    {
      FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
  /* ********************************************************************** */
  /*                            METODOS AUXILIARES                          */
  /* ********************************************************************** */
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_F2){
      //todos=!todos;
      //seleccionarTodosLocales(todos);
    }else if (e.getKeyCode() == KeyEvent.VK_F11)
    {
            if (validarCampos()) {
                //if(FarmaUtility.rptaConfirmDialog(this,"Esta seguro de asignar el nuevo remito a las fechas selecionadas?"))
                //    {
                if (todos) {
                    cargarLocales(); //todos seleccionados
                }
                System.out.println("FECHAS ALMACENADAS: " + 
                                   VariablesCaja.vArrayFechasSeleccinadas);
                System.out.println("CANTIDAD : " + 
                                   VariablesCaja.vArrayFechasSeleccinadas.size());
                //cargarLocalesInactivos();//no seleccionados
                asignarLocales();
                cerrarVentana(true);
                //}
            }
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
          cerrarVentana(false);  
        }/*else if (e.getKeyCode() == KeyEvent.VK_INSERT)    
        {
            UtilityCaja.imprimeVoucherRemito(this,"0009513969");
        }*/
  }

  private boolean validarCampos(){
  boolean result=true;
  
  if(tblSobres.getRowCount()==0){
      FarmaUtility.showMessage(this,"No existen días pendientes para poder crear un Remito.",txtNumRemito);
      return false;
  }
  if(txtPrecinto.isVisible()==true && txtPrecinto.getText().trim().length()==0){
      FarmaUtility.showMessage(this,"Debe ingresar el Precinto",txtPrecinto);
   result= false;
  }
  else
  if(txtNumRemito.getText().trim().length()<10){
      FarmaUtility.showMessage(this,"Debe ingresar un número de remito",txtNumRemito);
   result= false;
  }/*else if (VariablesCaja.vArrayFechasSeleccinadas.size()<1){
      FarmaUtility.showMessage(this,"Debe seleccionar fechas para asignar al remito",txtBuscar);
   result=false;
  }*/else if(!existeCodigo(txtNumRemito.getText().trim())){
      FarmaUtility.showMessage(this,"Código de remito ya existe",txtNumRemito);
     result=false; 
  }
  return result;
  
  }
  
  private boolean existeCodigo(String CodRemito){
  
  boolean result=false;
  String valor="";
      try
           {
           VariablesCaja.NumRemito=txtNumRemito.getText().trim();
           valor=DBCaja.validarCodigo(CodRemito);
           if(valor.equalsIgnoreCase("N"))
            result=true;
            
           }catch(SQLException sql)
           {
             if(sql.getErrorCode() == 20001){
             FarmaUtility.showMessage(this,"Ya existe código de remito ingresado¡¡¡",txtNumRemito);  
             }else {
             sql.printStackTrace();
             FarmaUtility.showMessage(this,"Ocurrió un error al validar remito.\n"+sql.getMessage(),txtNumRemito);
             }
           }   
    return result;
  }
  
    /**
     * Se selecciona una fecha venta sin visto bueno
     */
    private void SeleccionarLocal()
    {
      System.out.println("2");
      boolean seleccion = ((Boolean) tblLista.getValueAt(tblLista.getSelectedRow(),0)).booleanValue();
       if (!seleccion)
      {
        borrarLocal();
        cargarLocal();
        
        //System.out.println("Locales Agregados :"+VariablesAdministracion.vArrayListaLocales);
        System.out.println("Fechas Relacionadas :"+VariablesCaja.vArrayFechasSeleccinadas);
        FarmaUtility.setCheckValue(tblLista,false);
        tblLista.setRowSelectionInterval(VariablesCaja.cPos,VariablesCaja.cPos);
      }
      else
      {
        borrarLocal();
        //VariablesAdministracion.cPos=tblListaLocales.getSelectedRow();
        //actualizaAsignacion(VariablesAdministracion.cPos);///
        // System.out.println("POSICION  :"+VariablesAdministracion.cPos);
        System.out.println("Fechas RESTANTES :"+VariablesCaja.vArrayFechasSeleccinadas);
        FarmaUtility.setCheckValue(tblLista, true);
      }
    }
    
    /**
     * Elimina el elemento del conjunto
     * */
    private void borrarLocal()
    {
      String cod = tblLista.getValueAt(tblLista.getSelectedRow(),COL_FEC_VTA).toString();
      
      for(int i=0;i< VariablesCaja.vArrayFechasSeleccinadas.size();i++)
      {
        if(((ArrayList) VariablesCaja.vArrayFechasSeleccinadas.get(i)).contains(cod))
        {
          VariablesCaja.vArrayFechasSeleccinadas.remove(i);
          break;
        }
      }
    }
    
    /**
     *  Se carga fecha relacionada al remito
     */
    private void cargarLocal()
    {
    //cambio de posicion
      VariablesCaja.FechaVta=tblLista.getValueAt(tblLista.getSelectedRow(),1).toString();//chek
      //VariablesCaja.CodLocal=tblLista.getValueAt(tblLista.getSelectedRow(),2).toString();
      VariablesCaja.cPos= tblLista.getSelectedRow();
      ArrayList array=new ArrayList();
      array.add(VariablesCaja.FechaVta);
      // System.out.println("EN ARREGLO"+VariablesAdministracion.cEstRel);
      array.add("A");
      array.add(tblLista.getValueAt(tblLista.getSelectedRow(),1).toString());
      VariablesCaja.vArrayFechasSeleccinadas.add(array);
    }
    
    
    /**
     * Almacenamos todos los locales para asignarlos a la forma de pago.
     * */
      private void cargarLocales()
      {
        VariablesCaja.vArrayFechasSeleccinadas = new ArrayList();
        boolean valor;
        for (int i = 0; i < tblSobres.getRowCount(); i++)
        {
          //valor = ((Boolean) tableModelLista.getValueAt(i, 0)).booleanValue();
          //if (valor){
          ArrayList array=new ArrayList();
          array.add(tblSobres.getValueAt(i,0).toString());//1
          array.add("A");
          array.add(tblSobres.getValueAt(i,1).toString());//Codigo de Sobre
          VariablesCaja.vArrayFechasSeleccinadas.add(array);
          //}
        }
        
        System.out.println("FECHAS ALMACENADAS: "+VariablesCaja.vArrayFechasSeleccinadas);
        System.out.println("CANTIDAD :"+VariablesCaja.vArrayFechasSeleccinadas.size());
      }
    
    
    /**
     * Se asigna el remito a las fechas seleccionadas
     * */
    private void asignarLocales(){
     try
          {
             VariablesCaja.NumRemito = txtNumRemito.getText().trim();
             String vPrecinto = txtPrecinto.getText().trim();
          //DBCaja.AsignarRemito(VariablesCaja.vArrayFechasSeleccinadas);
         DBCajaElectronica.agregaRemito_AS(VariablesCaja.NumRemito,VariablesCaja.vArrayFechasSeleccinadas,vPrecinto); //INI ASOSA, 22.04.2010
         DBCajaElectronica.saveHistorialRemito(VariablesCaja.NumRemito); //FIN ASOSA, 22.04.2010
          //DBAdministracion.AsignarLocales(VariablesAdministracion.vArrayLocalesRelacionadosInactivos);
          FarmaUtility.aceptarTransaccion();
          //FarmaUtility.showMessage(this, "Se asignó las fechas al nuevo remito con éxito", txtNumRemito);
              System.out.println("VariablesCaja.NumRemito: "+VariablesCaja.NumRemito);
              UtilityCaja.imprimeVoucherRemito(this,VariablesCaja.NumRemito);
              FarmaUtility.moveFocus(txtNumRemito);
              VariablesCaja.vArrayFechasSeleccinadas.clear();
              VariablesCaja.NumRemito="";
          cerrarVentana(true);
          }catch(SQLException sql)
          {
              FarmaUtility.liberarTransaccion();
            //sql.printStackTrace();
             if(sql.getErrorCode() == 20002){
            FarmaUtility.showMessage(this,"Ya existe asignacion de remito¡¡¡",txtNumRemito);  
            }else if(sql.getErrorCode() == 20001){
            FarmaUtility.showMessage(this,"Ya existe código de remito ingresado¡¡¡",txtNumRemito);  
            }else {
            
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrio un error al asignar el remito.\n"+sql.getMessage(),txtNumRemito);
            }
            
          }   
    }
    
  /**
   * se obtiene las fechas sobres no relacionado a remitos
   * */
   private void listarFechaVentas(){
       try
       {
        DBCaja.getFecSinRemito(tableModelLista);
           //if(tblLista.getRowCount()>0)
            // FarmaUtility.ordenar(tblLista,tableModelLista,0,FarmaConstants.ORDEN_ASCENDENTE);
       }catch(SQLException sql)
           {
             sql.printStackTrace();
             FarmaUtility.showMessage(this, "Error al listar pendientes remito :\n" + sql.getMessage(),txtNumRemito);
           }
   }
  
    
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }


    private void txtNumRemito_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumRemito, e);
    }
    
    public boolean validaMontoIngresado(Object pObjeto){
    
        JTextFieldSanSerif jtext = (JTextFieldSanSerif)pObjeto;
        if(jtext.getText().trim().length() > 0){
            String vValorProv = jtext.getText().trim();
            int vPosPuntoDecimal = vValorProv.indexOf(".");
            System.out.println("vPosPuntoDecimal " + vPosPuntoDecimal);
                String vNumeroEntero = "";
            if(vPosPuntoDecimal!=-1){
                vNumeroEntero = vValorProv.substring(0,vPosPuntoDecimal);
            }
            else
                vNumeroEntero = vValorProv.trim();
                System.out.println("vNumeroEntero " + vNumeroEntero);
                int vEntero = 0;
                try {
                    vEntero = Integer.parseInt(vNumeroEntero.trim());
                } catch (Exception e) {
                    vEntero = 0;
                }
               /* if(vEntero>=1000000000){
                    FarmaUtility.showMessage(this, "La cantidad ingresada excede el valor permitido.", txtNumRemito);
                   FarmaUtility.moveFocus(pObjeto);
                   return false;
                }*/
        }
        return true;
    }

    private void tblSobres_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblLista_keyReleased(KeyEvent e) {
    
        if(tblLista.getSelectedRow()>-1){
            System.out.println("Fecha.."+FarmaUtility.getValueFieldJTable(tblLista,tblLista.getSelectedRow(),0));
           listaSobres(FarmaUtility.getValueFieldJTable(tblLista,tblLista.getSelectedRow(),0));
        }
    }
    
    private void listaSobres(String Fecha){
    
        try
        {
         //DBCaja.getSobresFecNuevoRemito(tableModeSobres,Fecha);
            DBCajaElectronica.getLISTA_SOBRE_AS(tableModeSobres,Fecha); //ASOSA, 22.04.2010
            if(tblSobres.getRowCount()>0)
            FarmaUtility.ordenar(tblSobres,tableModeSobres,0,FarmaConstants.ORDEN_ASCENDENTE);
        }catch(SQLException sql)
            {
              sql.printStackTrace();
              FarmaUtility.showMessage(this, "Error al listar Sobres :\n" + sql.getMessage(),tblLista);
            }
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnSobres_actionPerformed(ActionEvent e) {
    if(tblSobres.getRowCount()>0)
      FarmaUtility.moveFocus(tblSobres);
    }
    
    /**
     * determina si se debe o no listar los sobres.
     * @author ASOSA
     * @since 22.04.2010
     * @return
     */
    private boolean listar(){
        boolean flag=false;
        String ind="";
        try{
            ind=DBCajaElectronica.getIND_PROSEGUR();
        }catch(SQLException e){
            e.printStackTrace();
            FarmaUtility.showMessage(this,"ERROR AL OBTENER INDICADOR PROSEGUR: "+e.getMessage(),txtNumRemito);
        }
        if(ind.equalsIgnoreCase("S")) flag=true;
        return flag;
    }

    private void txtPrecinto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
           if(txtPrecinto.getText().trim().length()>0){
                   if(txtPrecinto.getText().trim().length()<100){
                        FarmaUtility.moveFocus(txtNumRemito);
                  
                   }else FarmaUtility.showMessage(this,"Valor ingresado invalido!!!", txtNumRemito);  
           }
        }
        chkKeyPressed(e);        
    }
}
