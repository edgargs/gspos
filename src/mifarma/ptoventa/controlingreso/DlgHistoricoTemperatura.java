package mifarma.ptoventa.controlingreso;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import java.io.File;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import mifarma.ptoventa.controlingreso.reference.*;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgHistoricoTemperatura.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 *   11.02.2009 Creación<br>
 * <br>
 *
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */
public class DlgHistoricoTemperatura extends JDialog
{
  /* ********************************************************************** */
  /* DECLARACION PROPIEDADES */
  /* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel;

  private JPanelWhite jContentPane = new JPanelWhite();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaHistorico = new JScrollPane();
  private JLabelFunction lblF8 = new JLabelFunction();
  private JLabelFunction lblF4 = new JLabelFunction();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JButtonLabel btnRelacionPromociones = new JButtonLabel();
  private JTable tblHistorico = new JTable();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
  private JLabelWhite lblDetalle_T = new JLabelWhite();
  private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JTextField txtFecInicio = new JTextField();
    private JTextField txtFecFin = new JTextField();
    private JButtonFunction btnBuscar = new JButtonFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();

    /* ********************************************************************** */
  /* CONSTRUCTORES */
  /* ********************************************************************** */

  public DlgHistoricoTemperatura()
  {
    this(null, "", false);
  }

  public DlgHistoricoTemperatura(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /* ********************************************************************** */
  /* METODO JBINIT */
  /* ********************************************************************** */

  private void jbInit()
    throws Exception
  {
    this.setSize(new Dimension(616, 399));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingreso Temperatura");
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
    pnlTitle1.setBounds(new Rectangle(5, 40, 600, 25));
    scrListaHistorico.setBounds(new Rectangle(5, 65, 600, 275));
    scrListaHistorico.setBackground(new Color(255, 130, 14));
    scrListaHistorico.setFont(new Font("SansSerif", 0, 11));
    lblF8.setText("[ F8 ] Borrar");
    lblF8.setBounds(new Rectangle(210, 345, 95, 20));
    lblF4.setText("[ F4 ] Cambiar");
    lblF4.setBounds(new Rectangle(95, 345, 110, 20));
    lblF3.setText("[ F3 ] Insertar");
    lblF3.setBounds(new Rectangle(5, 345, 85, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(510, 345, 90, 20));
    btnRelacionPromociones.setText("Relación Temperaturas :");
    btnRelacionPromociones.setBounds(new Rectangle(10, 5, 170, 15));
    btnRelacionPromociones.setMnemonic('r');
    btnRelacionPromociones.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
                        btnRelacionPromociones_actionPerformed(e);
                    }
        });
    tblHistorico.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            tblPromociones_keyPressed(e);
          }
        });
    pnlHeader1.setBounds(new Rectangle(65, 5, 540, 30));
        pnlTitle2.setBounds(new Rectangle(5, 5, 60, 30));
    lblDetalle_T.setText("Fechas :");
    lblDetalle_T.setBounds(new Rectangle(5, 5, 45, 20));
    lblF9.setBounds(new Rectangle(375, 345, 105, 20));
    lblF9.setText("[ F9 ] Exportar");
        jLabelWhite2.setText("Fecha Fin :");
        jLabelWhite2.setBounds(new Rectangle(215, 5, 65, 20));
        txtFecInicio.setBounds(new Rectangle(95, 5, 110, 20));
        txtFecInicio.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtFecInicio_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtFecInicio_keyReleased(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtFecInicio_keyTyped(e);
                    }
                });
        txtFecFin.setBounds(new Rectangle(285, 5, 115, 20));
        txtFecFin.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtFecFin_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtFecFin_keyTyped(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtFecFin_keyReleased(e);
                    }
                });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(435, 5, 75, 20));
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        jButtonLabel1.setText("Fecha Inicio :");
        jButtonLabel1.setBounds(new Rectangle(15, 5, 70, 20));
        jButtonLabel1.setMnemonic('I');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        scrListaHistorico.getViewport();
        pnlTitle2.add(lblDetalle_T, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(pnlTitle2, null);
        pnlHeader1.add(jButtonLabel1, null);
        pnlHeader1.add(btnBuscar, null);
        pnlHeader1.add(txtFecFin, null);
        pnlHeader1.add(jLabelWhite2, null);
        pnlHeader1.add(txtFecInicio, null);
        jContentPane.add(pnlHeader1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF8, null);
        scrListaHistorico.getViewport().add(tblHistorico, null);
    jContentPane.add(scrListaHistorico, null);
        pnlTitle1.add(btnRelacionPromociones, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  }

  /* ********************************************************************** */
  /* METODO INITIALIZE */
  /* ********************************************************************** */

  private void initialize()
  {
    mostrarDatos();
    initTable();
  }

  /* ********************************************************************** */
  /* METODO DE INICIALIZACION */
  /* ********************************************************************** */

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsControlIngreso.columnsListaHistTemp,ConstantsControlIngreso.defaultValuesListaHistTemp,0);
    FarmaUtility.initSimpleList(tblHistorico, tableModel,ConstantsControlIngreso.columnsListaHistTemp);
    cargaListaHistorico();
    
      lblF8.setVisible(false);
      lblF4.setVisible(false);
  }

  /* ********************************************************************** */
  /* METODOS DE EVENTOS */
  /* ********************************************************************** */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtFecInicio);
  }

  private void btnRelacionPromociones_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblHistorico);
  }

  private void tblPromociones_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }
  /* ********************************************************************** */
  /* METODOS AUXILIARES */
  /* ********************************************************************** */

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_F3)
    {
     if(!ValidaRolQF()){
       FarmaUtility.showMessage(this,ConstantsControlIngreso.MENSAJE_ROL,tblHistorico);
     }else{
      VariablesControlIngreso.vTipoAccionTemp = FarmaConstants.ACCION_INSERT;
      DlgIngresoTemperatura dlgingreso=new DlgIngresoTemperatura(myParentFrame, "", true);
      dlgingreso.setVisible(true);
      
        if(FarmaVariables.vAceptar){
            cargaListaHistorico();
           tblHistorico.repaint();
        }
    }
    }
    else if (e.getKeyCode() == KeyEvent.VK_F4)
    {
    }
    else if (e.getKeyCode() == KeyEvent.VK_F8)
    {
    
    }
    else if (e.getKeyCode() == KeyEvent.VK_F9)
    {
      //cerrarZan();
       if(tblHistorico.getRowCount()>0){
         if(FarmaUtility.rptaConfirmDialog(this,"Esta seguro de exportar el histórico?")){
         exportar();
         }
       }else{
       FarmaUtility.showMessage(this,"No existen datos para exportar",tblHistorico);
       }
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

    private void txtFecInicio_keyPressed(KeyEvent e) {
    
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
           if(txtFecInicio.getText().trim().length()>1){
           FarmaUtility.moveFocus(txtFecFin);
           }
        }
        chkKeyPressed(e);
    }

    private void txtFecInicio_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecInicio,e);
    }

    private void txtFecFin_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
           if(txtFecFin.getText().trim().length()>1){
            //FarmaUtility.moveFocus(btnBuscar);
            btnBuscar.doClick();
           }
        }
        chkKeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if(validarCampos()){
         mostrarhistoricoFiltro();
         FarmaUtility.moveFocus(txtFecInicio);
        }
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
      FarmaUtility.moveFocus(txtFecInicio);
    }

    private void txtFecInicio_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFecInicio, e);
    }

    private void txtFecFin_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFecFin, e);
    }

    private void txtFecFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin,e);
    }
  /* ********************************************************************** */
  /* METODOS DE LOGICA DE NEGOCIO */
  /* *********************************************************************** */
   
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

  private void mostrarDatos()
  {

  }
  
  private void cargaListaHistorico()
  {
      try
      {
      //System.out.println("VariablesCompras.vCod_Prod "+VariablesCompras.vCod_Prod);
        DBControlIngreso.cargaListaHistoricoTemp(tableModel);
        if (tableModel.getRowCount() > 0)
          FarmaUtility.ordenar(tblHistorico, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
      }
      catch (SQLException e)
      {
        System.out.println("error al cargar el historico : " + e);
      }
  }

  private boolean tieneRegistroSeleccionado(JTable pTabla)
  {
    boolean rpta = false;

    if (pTabla.getSelectedRow() != -1)
    {
      rpta = true;
    }
    return rpta;
  }

  private void eliminarSeleccionado()
    throws SQLException
  {
    // DBCompras.eliminaZan(VariablesCompras.vCod_Prod,tblHistorico.getValueAt(tblHistorico.getSelectedRow(),0).toString());
  }
  
  /**
  * Se muestra el dialogo para exportar el archivo.
  */
  private void exportar()
  {
   File lfFile = new File("C:\\");
   JFileChooser filechooser = new JFileChooser(lfFile);
   filechooser.setDialogTitle("Seleccione el directorio destino");
   filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
   if (filechooser.showSaveDialog(this.myParentFrame) != JFileChooser.APPROVE_OPTION)
     return;
   File fileChoosen = filechooser.getSelectedFile();
   String ruta = fileChoosen.getAbsolutePath();
   FarmaUtility.saveFileRuta(ruta,ConstantsControlIngreso.columnsListaHistTemp, 
                             tblHistorico, new int[]{ 8,25,25,24,24,24});
   FarmaUtility.showMessage(this, "Los datos se exportaron correctamente",tblHistorico);
  }
  
  
    /**
     * Se valida el rol del usuario
     * @author  
     * @since 12.02.2009
     * */
    private boolean ValidaRolQF(){
    
    boolean valor=true;
    String result="";
    System.out.println("FarmaVariables.vNuSecUsu : "+FarmaVariables.vNuSecUsu);
    try
        {
         result=DBControlIngreso.verificaRolUsuario(FarmaVariables.vNuSecUsu,ConstantsControlIngreso.ROL_QF_ADMINLOCAL);
         if(result.equalsIgnoreCase("N"))
         valor=false;
        }
        catch (SQLException e)
        {
          FarmaUtility.liberarTransaccion();
          e.printStackTrace();
          FarmaUtility.showMessage(this,"Ha ocurrido un error al validar el rol de usuario .\n" + e.getMessage(), tblHistorico);
        }
        return valor;
    }

  

    /**
     *  Valida los campos ingresados()Cod,fecini,fecfin)
     */
   private boolean validarCampos(){
   
     boolean retorno=true;
     String fechaini=txtFecInicio.getText().trim();
     String fechafin=txtFecFin.getText().trim();
         if(txtFecInicio.getText().length()<1||txtFecInicio.getText().length()<10){
         retorno = false;
         FarmaUtility.showMessage(this,"Formato de fecha inicio invalido",txtFecInicio);
         }
         else if(txtFecFin.getText().length()<1||txtFecFin.getText().length()<10){
         retorno = false;
         FarmaUtility.showMessage(this,"Formato de fecha fin  invalido",txtFecFin);
         }else if(!FarmaUtility.isFechaValida(this,fechaini,"Ingrese una fecha correcta en fecha de inicio")){
         retorno = false;
         FarmaUtility.moveFocus(txtFecInicio);
         }else if(!FarmaUtility.isFechaValida(this,fechafin,"Ingrese una fecha correcta en fecha fin")){
         retorno = false;
         FarmaUtility.moveFocus(txtFecFin);
         }else if(!FarmaUtility.validarRangoFechas(this,txtFecInicio,txtFecFin,false,true,true,true)){
         retorno = false;
         FarmaUtility.moveFocus(txtFecInicio);
         }
     return retorno;
   }
       
    /**
     *  Cargar historico
     */
    private void mostrarhistoricoFiltro(){

      String fecini=txtFecInicio.getText().trim();
      String fecfin=txtFecFin.getText().trim();
          try{
              DBControlIngreso.ListarHistoricoTempFiltro(tableModel,fecini,fecfin);
                  if(tblHistorico.getRowCount()>0){
                  FarmaUtility.ordenar(tblHistorico,tableModel,0,FarmaConstants.ORDEN_DESCENDENTE);
                  FarmaUtility.moveFocus(tblHistorico);
                  }else{
                  FarmaUtility.showMessage(this,"No existen datos.",txtFecInicio);
                  FarmaUtility.moveFocus(txtFecInicio);
                  }
          }catch(SQLException sql){
              sql.printStackTrace();
              FarmaUtility.showMessage(this,"Ocurrio un error al mostrar historico.\n"+sql.getMessage(),txtFecInicio); 
          }
    }


}
