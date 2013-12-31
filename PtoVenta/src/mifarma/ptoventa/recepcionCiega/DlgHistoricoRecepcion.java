package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.*;

import mifarma.ptoventa.caja.DlgFormaPago;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.recepcionCiega.DlgConteoRecepMercaderia.*;
import mifarma.ptoventa.recepcionCiega.reference.*;
import mifarma.ptoventa.reference.ConstantsPtoVenta;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgHistoricoRecepcion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ 16.11.2009 Creación<br>
 * <br>
 *
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */
public class DlgHistoricoRecepcion extends JDialog 
{
  private static final Logger log = LoggerFactory.getLogger(DlgHistoricoRecepcion.class);
  private FarmaTableModel tableModelRecepcion;
  private Frame myParentFrame;
  
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
  private JButton btnBuscar = new JButton();
  private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
  private JButtonLabel btnPeriodo = new JButtonLabel();
  private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblRecepcion = new JTable();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction jLabelFunction3 = new JLabelFunction();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JPanelTitle pnlResultados = new JPanelTitle();
  private JLabel lblRegistros1 = new JLabel();
  private JLabel lblRegistros = new JLabel();
  private JLabel lblRegsitros_T = new JLabel();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    
    //JMIRANDA 19.03.2010 COLUMNAS
    private static final int COL_NRO_RECEP = 0;
    private static final int COL_FECHA = 1;
    private static final int COL_USU_CREA = 2;
    private static final int COL_CANT_GUIA = 3;
    private static final int COL_ESTADO = 4;
    private static final int COL_COD_ESTADO = 5;    
    private static final int COL_ORDEN = 6;
    private static final int COL_CANT_BULTOS = 7;
    private static final int COL_CANT_PRECIN = 8;

    public DlgHistoricoRecepcion()
  {
    this(null, "", false);
  }

  public DlgHistoricoRecepcion(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(585, 458));
    this.getContentPane().setLayout(gridLayout1);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Ingreso de Recepción de Mercadería");
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
    pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 570, 40));
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(435, 10, 95, 20));
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
    txtFechaFin.setBounds(new Rectangle(295, 10, 101, 19));
      txtFechaFin.setLengthText(10);
    txtFechaFin.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaFin_keyPressed(e);
        }
        public void keyReleased(KeyEvent e)
        {
          txtFechaFin_keyReleased(e);
        }

                    public void keyTyped(KeyEvent e) {
                        txtFechaFin_keyTyped(e);
                    }
                });
    txtFechaIni.setBounds(new Rectangle(115, 10, 101, 19));
      txtFechaIni.setLengthText(10);
    txtFechaIni.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
            txtFechaIni_keyPressed(e);
                    }
        public void keyReleased(KeyEvent e)
        {
            txtFechaIni_keyReleased(e);
        }

        public void keyTyped(KeyEvent e) {
            txtFechaIni_keyTyped(e);
        }
     });
    btnPeriodo.setText("Desde :");
    btnPeriodo.setBounds(new Rectangle(55, 10, 55, 20));
    btnPeriodo.setMnemonic('D');
    btnPeriodo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnPeriodo_actionPerformed(e);
                    }
      });
    pnlTitulo.setBounds(new Rectangle(5, 50, 570, 20));
        btnListado.setText("Relación de Recepciones :");
    btnListado.setBounds(new Rectangle(10, 0, 200, 20));
    btnListado.setMnemonic('R');
    btnListado.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnListado_actionPerformed(e);
                    }
      });
    jScrollPane1.setBounds(new Rectangle(5, 70, 570, 285));
        tblRecepcion.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblRecepcion_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        tblRecepcion_keyReleased(e);
                    }
                });
        lblF1.setBounds(new Rectangle(5, 380, 125, 20));
    lblF1.setText("[ F1 ] Nuevo Ingreso");
    lblF1.setVisible(false);
    jLabelFunction3.setBounds(new Rectangle(490, 405, 85, 20));
    jLabelFunction3.setText("[ ESC ] Salir");
    lblF3.setBounds(new Rectangle(165, 380, 145, 20));
    //lblF3.setText("[ F3 ] Ver Entregas");
    lblF3.setText("[ F3 ] Asociar Entregas");
    pnlResultados.setBounds(new Rectangle(5, 355, 570, 20));
    lblRegistros1.setText("0");
    lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
    lblRegistros1.setFont(new Font("SansSerif", 1, 11));
    lblRegistros1.setForeground(Color.white);
    lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
    lblRegistros.setText("0");
    lblRegistros.setBounds(new Rectangle(60, 0, 35, 20));
    lblRegistros.setFont(new Font("SansSerif", 1, 11));
    lblRegistros.setForeground(Color.white);
    lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
    lblRegsitros_T.setText("Registros :");
    lblRegsitros_T.setBounds(new Rectangle(5, 0, 70, 20));
    lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
    lblRegsitros_T.setForeground(Color.white);
        jLabelWhite1.setText("Hasta :");
        jLabelWhite1.setBounds(new Rectangle(240, 10, 50, 20));
        lblF5.setBounds(new Rectangle(315, 380, 140, 20));
        lblF5.setText("[ F5 ] Conteo Productos");
        lblF8.setBounds(new Rectangle(460, 380, 115, 20));
        lblF8.setText("[ F8 ] Ver Reportes");
        pnlResultados.add(lblRegistros1, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(jLabelWhite1, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        jPanelWhite1.add(lblF8, null);
        jPanelWhite1.add(lblF5, null);
        jPanelWhite1.add(pnlResultados, null);
        jPanelWhite1.add(lblF3, null);
        jPanelWhite1.add(jLabelFunction3, null);
        jPanelWhite1.add(lblF1, null);
        jScrollPane1.getViewport().add(tblRecepcion, null);
        jPanelWhite1.add(jScrollPane1, null);
        pnlTitulo.add(btnListado, null);
        jPanelWhite1.add(pnlTitulo, null);
        jPanelWhite1.add(pnlCriterioBusqueda, null);
        this.getContentPane().add(jPanelWhite1, null);
  }


    private void initialize()
  {
    initTableListaRecepcion();
    limpiarVariables();
    cargaRecepciones();
    
  };
  
   private void initTableListaRecepcion()
  {
      tblRecepcion.getTableHeader().setReorderingAllowed(false);
      tblRecepcion.getTableHeader().setResizingAllowed(false);
      
    tableModelRecepcion = new FarmaTableModel(ConstantsRecepCiega.columnsListaRecepcion,ConstantsRecepCiega.defaultValuesListaRecepcion,0);
    FarmaUtility.initSimpleList(tblRecepcion,tableModelRecepcion,ConstantsRecepCiega.columnsListaRecepcion);
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
              buscaRegistroRecepcion(FechaInicio,FechaFin);
        }
        else
          FarmaUtility.showMessage(this,"Ingrese un formato valido de fechas",txtFechaIni); 
      }
      else
      FarmaUtility.showMessage(this,"Ingrese datos para la busqueda",txtFechaIni);
    
    }
  }
  
  private void limpiarVariables(){
    
    VariablesRecepCiega.vNombreTrans = "";
    VariablesRecepCiega.vHoraTrans = "";
    VariablesRecepCiega.vPlacaUnidTrans = "";
    VariablesRecepCiega.vCantBultos = "";
    VariablesRecepCiega.vCantPrecintos = "";
    
    VariablesRecepCiega.vNumIngreso= "";
    VariablesRecepCiega.vNumNotaEst= "";
    VariablesRecepCiega.vNumGuia= "";
    VariablesRecepCiega.vFecCreaNota= "";
    VariablesRecepCiega.vCantItems= "";
    VariablesRecepCiega.vCantProds= "";
    VariablesRecepCiega.vEstado= "";
  }
  
  private void buscaRegistroRecepcion(String pFechaInicio, String pFechaFin)
  {
     cargaRecepciones(pFechaInicio,pFechaFin);
  }
  
    /**
     *  Valida los campos ingresados()Cod,fecini,fecfin)
     */
    private boolean validarCampos(){
    
      boolean retorno=true, flag1 = true, flag2 = true;      
      String fechaini=txtFechaIni.getText().trim();
      String fechafin=txtFechaFin.getText().trim();
        try {

            /*if (txtFechaIni.getText().length() < 1 || 
                txtFechaIni.getText().length() < 10) {
                retorno = false;
                FarmaUtility.showMessage(this, 
                                         "Formato de fecha inicio invalido", 
                                         txtFechaIni);
            }
            if (txtFechaFin.getText().length() < 1 || 
                txtFechaFin.getText().length() < 10) {
                retorno = false;
                FarmaUtility.showMessage(this, 
                                         "Formato de fecha fin  invalido", 
                                         txtFechaFin);
            } *//*else if(!FarmaUtility.isFechaValida(this,fechaini,"Ingrese una fecha correcta en fecha de inicio")){
              retorno = false;
              FarmaUtility.moveFocus(txtFechaIni);
              }else if(!FarmaUtility.isFechaValida(this,fechafin,"Ingrese una fecha correcta en fecha fin")){
              retorno = false;
              FarmaUtility.moveFocus(txtFechaFin);
              }/*else if(!FarmaUtility.validarRangoFechas(this,txtFechaIni,txtFechaFin,false,true,true,true)){
              retorno = false;
              FarmaUtility.moveFocus(txtFechaIni);
              } */
            
            /*if (!validaFecha(txtFechaIni.getText().trim(), "")) {
                flag1 = false;                
                retorno = false;
                FarmaUtility.showMessage(this, "Formato de fecha inicial inválido", 
                                         txtFechaIni);  
                return retorno;
            }            
            else if (!validaFecha(txtFechaFin.getText().trim(), "")) {
                  flag2 = false; 
                  retorno = false;
                  FarmaUtility.showMessage(this, "Formato de fecha fin inválido", 
                                           txtFechaFin);                              
                return retorno;
            }*/
            if(!UtilityRecepCiega.validarFecha(txtFechaIni.getText().trim()) ||
                                               !validaFecha(txtFechaIni.getText().trim(), "")){
                     flag1 = false;
                     retorno = false;
                     FarmaUtility.showMessage(this, "Formato de fecha inicial inválido", 
                                                  txtFechaIni);  
                         return retorno;
            }
            else if(!UtilityRecepCiega.validarFecha(txtFechaFin.getText().trim()) || 
                                                    !validaFecha(txtFechaFin.getText().trim(), "")){
                       flag1 = false;
                       retorno = false;
                       FarmaUtility.showMessage(this, "Formato de fecha final inválido", 
                                                    txtFechaFin);  
                           return retorno;
            }
            else if(!flag1 || !flag2){
                retorno = false;
                FarmaUtility.showMessage(this, 
                                         "Error", 
                                         txtFechaIni);                
            }
            else if(flag1 && flag2){
                if (!FarmaUtility.valida_fecha_Inicial_Final(txtFechaIni.getText().trim(), 
                                                             txtFechaFin.getText().trim())) {
                    retorno = false;
                    FarmaUtility.showMessage(this, 
                                             "La fecha inicial es mayor a la fecha final", 
                                             txtFechaIni);
                    FarmaUtility.moveFocus(txtFechaIni);
                  }   
            }            
          }
          catch (Exception e) {
              retorno = false;
              log.error("",e);
          }
      return retorno;
    }

  private void cargaRecepciones(String FechIni,String FechFin)
  {
    try
    {
      log.debug(FechIni);
      log.debug(FechFin);
      DBRecepCiega.getListaRecepcionMercaderiaRango(tableModelRecepcion,FechIni, FechFin);
        FarmaUtility.ordenar(tblRecepcion,tableModelRecepcion,COL_ORDEN,FarmaConstants.ORDEN_DESCENDENTE);
        //JMIRANDA 18.03.2010
        //**
        ArrayList rowsWithOtherColor = new ArrayList();
        for(int i = 0; i < tableModelRecepcion.data.size(); i++){
            if ( ((ArrayList)tableModelRecepcion.data.get(i)).get(COL_CANT_GUIA).toString().trim().equalsIgnoreCase("0") )
            { //cantguias 8 es 0
              rowsWithOtherColor.add(String.valueOf(i));
            }
        }
        FarmaUtility.initSimpleListCleanColumns(tblRecepcion, tableModelRecepcion, 
            ConstantsRecepCiega.columnsListaRecepcion,rowsWithOtherColor,Color.white,Color.red,false);
        //**
      //FarmaUtility.moveFocus(tblRecepcion);
        lblRegistros.setText(""+tblRecepcion.getRowCount());
      setJTable(tblRecepcion,txtFechaIni);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al listar Recepciones : \n"+sql.getMessage(),null);
      //cerrarVentana(false);
    }
  }
  
  
   private void cargaRecepciones()
   {
     try
     {
       DBRecepCiega.getListaRecepcionMercaderia(tableModelRecepcion);
       //FarmaUtility.moveFocus(tblRecepcion);
       FarmaUtility.ordenar(tblRecepcion,tableModelRecepcion,COL_ORDEN,FarmaConstants.ORDEN_DESCENDENTE);
       ////**         
        ArrayList rowsWithOtherColor = new ArrayList();
        for(int i = 0; i < tableModelRecepcion.data.size(); i++){
            if ( ((ArrayList)tableModelRecepcion.data.get(i)).get(COL_CANT_GUIA).toString().trim().equalsIgnoreCase("0") )
            { //cantguias 8 es 0
              rowsWithOtherColor.add(String.valueOf(i));
            }
        }
        FarmaUtility.initSimpleListCleanColumns(tblRecepcion, tableModelRecepcion, 
            ConstantsRecepCiega.columnsListaRecepcion,rowsWithOtherColor,Color.white,Color.red,false);
        //**
        tblRecepcion.getTableHeader().setReorderingAllowed(false);
        tblRecepcion.getTableHeader().setResizingAllowed(false);

       FarmaUtility.moveFocus(txtFechaIni);
       lblRegistros.setText(""+tblRecepcion.getRowCount());
       setJTable(tblRecepcion,txtFechaIni);
     } catch(SQLException sql)
     {
       log.error("",sql);
       FarmaUtility.showMessage(this, "Error al listar Recepciones del dia : \n"+sql.getMessage(),null);
     }
   }
   
   private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

  private void txtFechaIni_keyPressed(KeyEvent e)
  {
    //DUBILLUZ - 03.12.2009
    FarmaGridUtils.aceptarTeclaPresionada(e,tblRecepcion,null,0);
    
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
        FarmaUtility.moveFocus(txtFechaFin);   
    //JMIRANDA 02.12.09
    /*else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){    
        FarmaUtility.moveFocus(tblRecepcion);
    }*/
      chkKeyPressed(e);
  }

  private void txtFechaIni_keyReleased(KeyEvent e)
  {
    FarmaUtility.dateComplete(txtFechaIni,e);
      cambiarMensajeLabel();
  }

  private void txtFechaFin_keyPressed(KeyEvent e)
  {
      //DUBILLUZ - 03.12.2009
      FarmaGridUtils.aceptarTeclaPresionada(e,tblRecepcion,null,0);    
       if(e.getKeyCode() == KeyEvent.VK_ENTER){
         btnBuscar.doClick();
      }
   //JMIRANDA 02.12.09
   /*
   else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){    
          FarmaUtility.moveFocus(tblRecepcion);
   }
   */
    chkKeyPressed(e);
  }

  private void txtFechaFin_keyReleased(KeyEvent e)
  {
    FarmaUtility.dateComplete(txtFechaFin,e);
      cambiarMensajeLabel();
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtFechaIni);
    //lbllocal.setText(FarmaVariables.vDescLocal);
     // cargaLogin();
    //JMIRANDA 02.12.09 VISUALIZAR BOTONES
    visualizarBotones();
/*     lblF1.setVisible(false);
     lblF3.setVisible(false);
     lblF5.setVisible(false);
     lblF8.setVisible(false);
     if(validaRolUsu(FarmaConstants.ROL_ADMLOCAL)){
          lblF1.setVisible(true);
          lblF3.setVisible(true);
          lblF5.setVisible(true);
          lblF8.setVisible(true);
     }else if(validaRolUsu(FarmaConstants.ROL_VENDEDOR)){
          lblF5.setVisible(true);
     }else if(validaRolUsu(FarmaConstants.ROL_OPERADOR_SISTEMAS)){
          lblF1.setVisible(true);
          lblF3.setVisible(true);
          lblF5.setVisible(true);
          lblF8.setVisible(true);
      }
*/     
  }

  private void btnBuscar_actionPerformed(ActionEvent e)
  {
  if(tblRecepcion.getRowCount() == 0 || tblRecepcion.getRowCount() > 0)
    {
      busqueda();
    }
  }

  private void tblVentasPorDIa_keyReleased(KeyEvent e)
  {
       chkKeyPressed(e);
  }
  
  private void chkKeyPressed(KeyEvent e)
  {
  if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e))
  {
      
   //if (!ConstantsRecepCiega.error_du) {
      if (!UtilityRecepCiega.indHabDatosTransp()){
       if(lblF1.isVisible()){
        if(validaIngreso())
         nuevoIngreso();
        else
         FarmaUtility.showMessage(this,"Opcion no habilitada para el IP actual",txtFechaIni);
        }
   }
  }else if (e.getKeyCode() == KeyEvent.VK_F3)
  {
      funcionF3();
        
  }else if (e.getKeyCode() == KeyEvent.VK_F5)
  {
      funcionF5();
      
  }else if(e.getKeyCode() == KeyEvent.VK_F8)
  {
      funcionF8();
      
  } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
      cerrarVentana(false);
  }
  }
  
    private void this_windowClosing(WindowEvent e)
    {
      FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnPeriodo_actionPerformed(ActionEvent e)
    {
      FarmaUtility.moveFocus(txtFechaIni);
    }


  private void btnListado_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(tblRecepcion);
  }

  private void tblRecepcion_keyPressed(KeyEvent e) {
      
      //JMIRANDA 02.12.09
      if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT){    
          FarmaUtility.moveFocus(txtFechaIni);
      }
      chkKeyPressed(e);
  }
  
  private void txtFechaIni_keyTyped(KeyEvent e) {
    
        FarmaUtility.admitirDigitos(txtFechaIni, e);
  }
    
  private void txtFechaFin_keyTyped(KeyEvent e) {
    
        FarmaUtility.admitirDigitos(txtFechaFin, e);
  }
  
  
  private void verConteo(String pEstado){
    VariablesRecepCiega.vNro_Recepcion = VariablesRecepCiega.vNumIngreso;
      String pIndSegundoConteo = "";
        if (obtieneIndicadorAcceso().equalsIgnoreCase("C")) {
            
            
           // if (obtenerIndSegConteo().equalsIgnoreCase("N")) {
                //Se bloquea el registro
                try {
                    UtilityRecepCiega.pBloqueoRecepcion(VariablesRecepCiega.vNro_Recepcion.trim());
                    log.info("Bloquea.");
                    pEstado = 
                        UtilityRecepCiega.pEstadoRecepcion(VariablesRecepCiega.vNro_Recepcion.trim());
                    
                    log.info("pEstado :" + 
                                       pEstado);

                    if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoRevisado)) {

                        if (
                            UtilityRecepCiega.updateEstadoRecep(
                                                                ConstantsRecepCiega.EstadoVerificacion,
                                                                VariablesRecepCiega.vNro_Recepcion.trim(),
                                                                this,txtFechaIni
                                                                )) {
                            FarmaUtility.aceptarTransaccion();          
                            log.info("Se quito bloqueo y actualizo Estado");
                            
                            DlgVerificacionConteo dlgVerifica = 
                                new DlgVerificacionConteo(myParentFrame, "", true);
                            dlgVerifica.setVisible(true);
                        }
                        else {
                            FarmaUtility.showMessage(this,"No se pudo modificar el estado en la Recepción.\n" + 
                                                                                                          "Vuelva a Intentarlo.\n",txtFechaIni);
                        }
                        
                    }
                    else{
                        FarmaUtility.liberarTransaccion();
                        log.info("Para liberar el bloqueo...");
                        
                        if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoVerificacion)) {
                                FarmaUtility.showMessage(this, 
                                                         "No puede ingresar a esta Opción.\n"+
                                                         "Ya existe un usuario que esta verificando.\n"+
                                                         "Sólo puede ingresar uno a la vez.\nGracias.", 
                                                         null);
                        }
                        else{//COLOCAR MENSAJES
                            /*
                             * FarmaUtility.showMessage(this, 
                                                     "La recepción ya fue verificada.",
                                                     null);
                            */
                            if(pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoTotal)||
                               pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoParcial)
                              ) 
                            {
                                DlgDiferenciasFinales dlgDifFinal = new DlgDiferenciasFinales(myParentFrame,"",true);
                                dlgDifFinal.setVisible(true);
                            } 
                            else{
                                FarmaUtility.showMessage(this, 
                                                         "La recepción ya fue verificada.",
                                                         null);
                            }
                            
                        }
                    }
                } catch (Exception e) {
                        log.error("",e);
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this, 
                                             "Ocurrió un error al ingresar a Verificación de Conteo.\n"+e.getMessage(), 
                                             null);
                }
                finally {
                    FarmaUtility.liberarTransaccion();
                    log.info("Para liberar el bloqueo...");
                }
            /*
            }
            else {
                FarmaUtility.showMessage(this, 
                                        "No puede ingresar a esta Opción.\n"+
                                        "Ya existe un usuario que esta verificando.\n"+
                                        "Sólo puede ingresar uno a la vez.\nGracias.", 
                                         null);

                
            }
            */
            

        } else if (obtieneIndicadorAcceso().equalsIgnoreCase("V")) {
            DlgDiferenciasFinales dlgDifFinal = 
                new DlgDiferenciasFinales(myParentFrame, "", true);
            dlgDifFinal.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cargaRecepciones();
            }
        } else
            FarmaUtility.showMessage(this, 
                                     "Ud. no tiene el rol necesario para acceder a esta opción \n", 
                                     null);
        FarmaVariables.vAceptar = false;
   
  }
  
  private void verConteoRecepcion(){
     DlgConteoRecepMercaderia dlgConteo = new DlgConteoRecepMercaderia(myParentFrame,"",true);
    dlgConteo.setVisible(true);  
      if (FarmaVariables.vAceptar) {
          cargaRecepciones();
      }
  }
  
    private void verEntrega(int row){
        log.debug("VER INGRESO");
   
        VariablesRecepCiega.vTipoIngrEntrega="02";
        /*        
        VariablesRecepCiega.vNumIngreso=FarmaUtility.getValueFieldJTable(tblRecepcion,row,0);
        VariablesRecepCiega.vFechIngreso=FarmaUtility.getValueFieldJTable(tblRecepcion,row,1);
        VariablesRecepCiega.vCantGuias=FarmaUtility.getValueFieldJTable(tblRecepcion,row,3);
        VariablesRecepCiega.vCantBultos=FarmaUtility.getValueFieldJTable(tblRecepcion,row,7);
        VariablesRecepCiega.vCantPrecintos=FarmaUtility.getValueFieldJTable(tblRecepcion,row,8);*/
   //**       
   VariablesRecepCiega.vNumIngreso = FarmaUtility.getValueFieldArrayList(tableModelRecepcion.data,row,COL_NRO_RECEP);
   VariablesRecepCiega.vFechIngreso = FarmaUtility.getValueFieldArrayList(tableModelRecepcion.data,row,COL_FECHA);
   VariablesRecepCiega.vCantGuias = FarmaUtility.getValueFieldArrayList(tableModelRecepcion.data,row,COL_CANT_GUIA);
   VariablesRecepCiega.vCantBultos = FarmaUtility.getValueFieldArrayList(tableModelRecepcion.data,row,COL_CANT_BULTOS);
   VariablesRecepCiega.vCantPrecintos = FarmaUtility.getValueFieldArrayList(tableModelRecepcion.data,row,COL_CANT_PRECIN);
   VariablesRecepCiega.vCod_Estado = FarmaUtility.getValueFieldArrayList(tableModelRecepcion.data,row,COL_COD_ESTADO);
   //**
        log.debug("VariablesRecepCiega.vNumIngreso :"+VariablesRecepCiega.vNumIngreso);
        log.debug("VariablesRecepCiega.vFechIngreso :"+VariablesRecepCiega.vFechIngreso);
        log.debug("VariablesRecepCiega.vCantGuias :"+VariablesRecepCiega.vCantGuias);
        log.debug("VariablesRecepCiega.vTipoIngrEntrega :"+VariablesRecepCiega.vTipoIngrEntrega);        
        log.debug("VariablesRecepCiega.vCantBultos :"+VariablesRecepCiega.vCantBultos);  
        log.debug("VariablesRecepCiega.vCantPrecintos :"+VariablesRecepCiega.vCantPrecintos);
        log.debug("VariablesRecepCiega.vCod_Estado: "+VariablesRecepCiega.vCod_Estado);

    }
    private void nuevoIngreso(){
    
        log.debug("NUEVO INGRESO");
        VariablesRecepCiega.vTipoIngrEntrega="01";
        DlgDatosTransportista dlgDatos = new DlgDatosTransportista(myParentFrame,"",true);
        dlgDatos.setVisible(true);
        if(FarmaVariables.vAceptar){
              cargaRecepciones();
          }
    }

    private  boolean validaIngreso(){
    String ind="";
    boolean valor=false;
        try
           {
           ind=DBRecepCiega.permiteIngreso();
           if(ind.trim().equalsIgnoreCase("S")) valor=true;
           }catch (SQLException sql)
           {
             log.error("",sql);
             FarmaUtility.showMessage(this,"Ocurrio un error al validar IP.\n"+sql.getMessage(),txtFechaIni);
           }
           
    return valor;
    
    }
  
    private String obtieneIndicadorAcceso()
    {
        String result="";
        log.debug("FarmaVariables.vNuSecUsu : "+FarmaVariables.vNuSecUsu);
        
        try
            {
             result=DBRecepCiega.verificaRolUsuario(FarmaVariables.vNuSecUsu,FarmaConstants.ROL_ADMLOCAL);
              return result;             
            }
            catch (SQLException e)
            {
              log.error("",e);
              FarmaUtility.showMessage(this,"Ha ocurrido un error al validar el rol de usuario .\n" + e.getMessage(), null);
              result = "N";
              return result;
            }
          
         
    }
    private String  validaIPParaVerificarConteo(){
        String vIP="";
        try{
            vIP=DBRecepCiega.verificaIPVeriricarConteo();
            return vIP;           
        }
        catch(SQLException sql){
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurrió un error al validar el IP del usuario. \n",null);   
            vIP="N";
            return vIP;
        }       
    }
    

    /**
     * Se valida rol para mostrar opciones
     * @AUTHOR JCORTEZ
     * @SINCE 27.11.2009
     * */
    private boolean validaRolUsu(String Rol)
    {
        String result="";
        log.debug("FarmaVariables.vNuSecUsu : "+FarmaVariables.vNuSecUsu);
        
        try
            {
             result=DBRecepCiega.verificaRolUsuarioRecep(FarmaVariables.vNuSecUsu,Rol);
                log.debug("result : "+result);
             if(result.trim().equalsIgnoreCase("S"))
              return true;
             else 
             return false;
            }
            catch (SQLException e)
            {
              log.error("",e);
              FarmaUtility.showMessage(this,"Ha ocurrido un error al validar el rol de usuario .\n" + e.getMessage(), null);
              result = "N";
              return false;
            }
    }
    
    //JMIRANDA 01.12.09
    private void validaRol(){
        //se guarda datos
        VariablesRecepCiega.vNuSecUsu=FarmaVariables.vNuSecUsu;
         VariablesRecepCiega.vIdUsu=FarmaVariables.vIdUsu;
         VariablesRecepCiega.vNomUsu=FarmaVariables.vNomUsu;
         VariablesRecepCiega.vPatUsu=FarmaVariables.vPatUsu;
         VariablesRecepCiega.vMatUsu=FarmaVariables.vMatUsu; 

        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
        //dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);
        
        if ( FarmaVariables.vAceptar ) {
        
           if(!FarmaVariables.vNuSecUsu.equalsIgnoreCase(VariablesRecepCiega.vNuSecUsu)){
               FarmaUtility.showMessage(this, "El usuario no es el mismo al logueado anteriormente. ¡Verificar!", tblRecepcion);
               FarmaVariables.vAceptar =false;
           }
           else{
               //JMIRANDA 04.12.09
               if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR) ) {
                   FarmaVariables.vAceptar = true;
               }   
               else if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
                   FarmaVariables.vAceptar = true;
               }
               else{
                   FarmaVariables.vAceptar = false;
                   FarmaUtility.showMessage(this, "El usuario no tiene permiso. ¡Verificar!", tblRecepcion);
               }
           }
        // se sete datos originales
           FarmaVariables.vNuSecUsu =VariablesRecepCiega.vNuSecUsu;
           FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
           FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
           FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
           FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;
           
        }
         /*
          * dubilluz - 04.12.2009
          * else
          cerrarVentana(false);
            */
     }

    //JMIRANDA 01.12.09
    private void verificaRolUsuario(){
        if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR) ) {
            FarmaVariables.vAceptar = true;
        }   
        else if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
            FarmaVariables.vAceptar = true;
        }
        else {
            //no debe ingresar
            FarmaVariables.vAceptar = false;
            FarmaUtility.showMessage(this,"Ud. no tiene Permiso para ingresar a contar." +
                "\nIngrese Usuario Correcto.",tblRecepcion);
            return;
        }
    }

    private String obtenerIndSegConteo(){
        String resultado="";
        try{
            resultado = DBRecepCiega.obtenerSegConteo(); 
           }
        catch(SQLException e){
            resultado="";
            log.error("",e);
            FarmaUtility.showMessage(this,"Ha ocurrido un error al obtener el indicador de verificación de conteo .\n" + e.getMessage(), null);
        }
        return resultado;
    }
    
    private void visualizarBotones(){
        lblF1.setVisible(false);
        lblF3.setVisible(false);
        lblF5.setVisible(false);
        lblF8.setVisible(false);
        if(validaRolUsu(FarmaConstants.ROL_ADMLOCAL) && VariablesCaja.vVerificaCajero){
            if (!UtilityRecepCiega.indHabDatosTransp()) {
             lblF1.setVisible(true);
            }
             lblF3.setVisible(true);
             lblF5.setVisible(true);
             lblF8.setVisible(true);
        }else if(validaRolUsu(FarmaConstants.ROL_VENDEDOR)){
             lblF5.setVisible(true);
        }else if(validaRolUsu(FarmaConstants.ROL_OPERADOR_SISTEMAS)){
             if (!UtilityRecepCiega.indHabDatosTransp()) {
             lblF1.setVisible(true);
             }
             lblF3.setVisible(true);
             lblF5.setVisible(true);
             lblF8.setVisible(true);
         }

    }
    
    private void setJTable(JTable pJTable,JTextFieldSanSerif pText) {
      if(pJTable.getRowCount() > 0){
        FarmaGridUtils.showCell(pJTable, 0, 0);
        FarmaUtility.setearActualRegistro(pJTable,null,0);
      }
      FarmaUtility.moveFocus(pText);
    }

    /**
     * @author Jmiranda
     * @since  07.12.2009
     */
    private void funcionF5() {
      //if(UtilityRecepCiega.pPermiteIpConteo())
      //  {
            String pEstado = "";
            if (lblF5.isVisible()) {
                int row = tblRecepcion.getSelectedRow();
                if (row > -1) {
                    
                    
                    pEstado = 
                            UtilityRecepCiega.pEstadoRecepcion(FarmaUtility.getValueFieldJTable(tblRecepcion, 
                                                                                                row, 
                                                                                                0).trim());

                    if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoEspera) || 
                        pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoEmitido)) {

                        VariablesRecepCiega.vNumIngreso = 
                                FarmaUtility.getValueFieldJTable(tblRecepcion, 
                                                                 row, 0);
                        VariablesRecepCiega.vFechIngreso = 
                                FarmaUtility.getValueFieldJTable(tblRecepcion, 
                                                                 row, 1);
                        VariablesRecepCiega.vCantGuias = 
                                FarmaUtility.getValueFieldJTable(tblRecepcion, 
                                                                 row, 3);
                        /*VariablesRecepCiega.vEstado = 
                                FarmaUtility.getValueFieldJTable(tblRecepcion, 
                                                                 row, 5);*/
                        VariablesRecepCiega.vEstado=FarmaUtility.getValueFieldArrayList(tableModelRecepcion.data,tblRecepcion.getSelectedRow(),5);
                        log.debug("VariablesRecepCiega.vCantGuias: "+VariablesRecepCiega.vCantGuias);
                        if (!VariablesRecepCiega.vCantGuias.trim().equalsIgnoreCase("0")) {
                            validaRol();
                            
                            if (FarmaVariables.vAceptar) {
                                //Se bloquea el registro
                                UtilityRecepCiega.pBloqueoRecepcion(FarmaUtility.getValueFieldJTable(tblRecepcion, 
                                                                                                            row, 
                                                                                                            0).trim());
                                log.info("Bloquea.");
                                pEstado = 
                                        UtilityRecepCiega.pEstadoRecepcion(FarmaUtility.getValueFieldJTable(tblRecepcion, 
                                                                                                            row, 
                                                                                                            0).trim());
                                log.info("Consulta Estado :"+pEstado);
                               
                                    if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoEspera) || 
                                        pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoEmitido)) {
                                    
                                        if (UtilityRecepCiega.updateEstadoRecep(ConstantsRecepCiega.EstadoConteo,FarmaUtility.getValueFieldJTable(tblRecepcion, 
                                                                                                            row, 
                                                                                                            0).trim(),
                                                                            this,txtFechaIni
                                                                            ))
                                            {
                                            FarmaUtility.aceptarTransaccion();          
                                            log.info("Se quito bloqueo y actualizo Estado");
                                            verConteoRecepcion();
                                            }
                                            else{
                                                FarmaUtility.showMessage(this,"No se pudo modificar el estado en la Recepción.\n" + 
                                                                              "Vuelva a Intentarlo.\n",txtFechaIni);
                                            }
                                        }
                                    else {
                                        log.info("Esto es para liberar el bloqueo...de Estado.");
                                        FarmaUtility.liberarTransaccion();
                                        if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoRevisado))
                                            FarmaUtility.showMessage(this, 
                                                                     "Ya se finalizó el primer conteo", 
                                                                     tblRecepcion);
                                        else if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoConteo)) {
                                            FarmaUtility.showMessage(this, 
                                                                     "Se encuentra en proceso de conteo", 
                                                                     tblRecepcion);
                                            FarmaUtility.moveFocus(txtFechaIni);
                                        }
                                        //JMIRANDA 02.12.09
                                        else if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoTotal) || 
                                                 pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoParcial)) {
                                            FarmaUtility.showMessage(this, 
                                                                     "La Recepción ya fue Afectada.", 
                                                                     tblRecepcion);
                                            FarmaUtility.moveFocus(txtFechaIni);
                                        }
                                    }
                            }
                        }
                        else {
                            FarmaUtility.showMessage(this,"No tiene asociada ninguna Entrega.\n" +
                                "Asocie Entregas para poder Realizar el primer Conteo.",txtFechaIni);
                        }

                    } else {
                        if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoRevisado))
                            FarmaUtility.showMessage(this, 
                                                     "Ya se finalizó el primer conteo", 
                                                     tblRecepcion);
                        else if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoConteo)) {
                            FarmaUtility.showMessage(this, 
                                                     "Se encuentra en proceso de conteo", 
                                                     tblRecepcion);
                            FarmaUtility.moveFocus(txtFechaIni);
                        }
                        //JMIRANDA 02.12.09
                        else if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoTotal) || 
                                 pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoParcial)) {
                            FarmaUtility.showMessage(this, 
                                                     "La Recepción ya fue Afectada.", 
                                                     tblRecepcion);
                            FarmaUtility.moveFocus(txtFechaIni);
                        }
                    }


                } else {
                    if (tblRecepcion.getRowCount() < 1)
                        FarmaUtility.moveFocus(txtFechaIni);
                    else
                        FarmaUtility.showMessage(this, 
                                                 "Debe seleccionar un registro", 
                                                 tblRecepcion);
                }
            }
      /*
        }
      else{
          FarmaUtility.showMessage(this,
                                   "En esta máquina no puede realizar conteos.",
                                   tblRecepcion);
      }
      */
     
        //JMIRANDA 01.12.09
        //ACTUALIZO TABLE
        actualizaListado();
    }
    public void actualizaListado() {
                
            if ((txtFechaIni.getText().trim().length() == 10 && 
                txtFechaFin.getText().trim().length() == 10))  {
                if(validarCampos()){
                buscaRegistroRecepcion(txtFechaIni.getText().trim(), 
                                       txtFechaFin.getText().trim());
                }
            } else {
                cargaRecepciones();
            }
                
        visualizarBotones();
    }

    public void funcionF8() {

        if (lblF8.isVisible()) {
            int row = tblRecepcion.getSelectedRow();
            if (row > -1) {
                VariablesRecepCiega.vNumIngreso = 
                        FarmaUtility.getValueFieldJTable(tblRecepcion, row, 0);
                VariablesRecepCiega.vFechIngreso = 
                        FarmaUtility.getValueFieldJTable(tblRecepcion, row, 1);
                VariablesRecepCiega.vCantGuias = 
                        FarmaUtility.getValueFieldJTable(tblRecepcion, row, 3);
                VariablesRecepCiega.vEstado = 
                        //FarmaUtility.getValueFieldJTable(tblRecepcion, row, 5);
                        UtilityRecepCiega.pEstadoRecepcion(VariablesRecepCiega.vNumIngreso);
                
                if(VariablesRecepCiega.vEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoVerificacion)){
                    FarmaUtility.showMessage(this, 
                                             "No puede ingresar a esta Opción.\n"+
                                             "Ya existe un usuario que esta verificando.\n"+
                                             "Sólo puede ingresar uno a la vez.\nGracias.", 
                                             tblRecepcion);                    
                }
                else
                if (VariablesRecepCiega.vEstado.trim().equalsIgnoreCase("P") || 
                    VariablesRecepCiega.vEstado.trim().equalsIgnoreCase("C") || 
                    VariablesRecepCiega.vEstado.trim().equalsIgnoreCase("E"))
                    FarmaUtility.showMessage(this, 
                                             "Debe finalizar el primer conteo", 
                                             tblRecepcion);
                else if (VariablesRecepCiega.vEstado.trim().equalsIgnoreCase("R") || 
                         VariablesRecepCiega.vEstado.trim().equalsIgnoreCase("T"))
                    verConteo(VariablesRecepCiega.vEstado);
                //JMIRANDA 01.12.09
                //ACTUALIZA LA TABLA
                actualizaListado();


            } else {
                if (tblRecepcion.getRowCount() < 1)
                    FarmaUtility.moveFocus(txtFechaIni);
                else
                    FarmaUtility.showMessage(this, 
                                             "Debe seleccionar un registro", 
                                             tblRecepcion);
            }
        }
    }

    //JMIRANDA VALIDA FECHA
    private boolean validaFecha(String pFecha, String pHora){
        //pFecha.trim().equalsIgnoreCase("");
        boolean flag = false;    
        Date fec = null;
        if(pHora.trim().equalsIgnoreCase("")){
            pHora = "00:00:00";    
        }
        try{
            if(FarmaUtility.isFechaValida(pFecha)){
                fec = FarmaUtility.obtiene_fecha(pFecha, pHora);   
            flag = true;
            }
            else{flag = false;}
           }catch(Exception e){
                flag = false;
           }
        return flag; 
    }
    private void funcionF3(){
        if(lblF3.isVisible()){
            if(tblRecepcion.getSelectedRowCount() == -1)  {
                FarmaUtility.showMessage(this,"No existen registros.",txtFechaIni);
                return;
            }
          int row = tblRecepcion.getSelectedRow();                    
          if (row > -1) {
              if(validaIngreso()){              
              verEntrega(row); 
                  if(VariablesRecepCiega.vCantGuias.trim().equalsIgnoreCase("0") &&
                     !VariablesRecepCiega.vCod_Estado.equalsIgnoreCase("T")){
                      VariablesRecepCiega.vTipoIngrEntrega="01";
                      if(cargaLogin()){
                          mostrarGuias();
                          //if(FarmaVariables.vAceptar){                          
                            cargaRecepciones();
                          //}
                      }
                  }else{
                      VariablesRecepCiega.vTipoIngrEntrega="02";
                      DlgDetalleEntregas dlgDet = new DlgDetalleEntregas(myParentFrame,"", true);
                      dlgDet.setVisible(true);
                      //if(FarmaVariables.vAceptar){
                        cargaRecepciones();
                      //}
                  }
              }else
                  FarmaUtility.showMessage(this,"Opcion no habilitada para el IP actual",txtFechaIni);
          }else{
              if(tblRecepcion.getRowCount()<1)
               FarmaUtility.moveFocus(txtFechaIni);
               else
               FarmaUtility.showMessage(this,"Debe seleccionar un registro",tblRecepcion);
          }
        }
    }

    private void tblRecepcion_keyReleased(KeyEvent e) {
        cambiarMensajeLabel();
    }
    
    private void cambiarMensajeLabel(){
        if(tblRecepcion.getSelectedRowCount() == -1) return;
        int vFila = tblRecepcion.getSelectedRow();
        int vCantGuias = Integer.parseInt(FarmaUtility.getValueFieldArrayList(tableModelRecepcion.data,vFila,3).trim());
        //log.debug("vCantGuias: "+vCantGuias);
        
        if(vCantGuias == 0){
            lblF3.setText("[ F3 ] Asociar Entregas");
            }else{
                lblF3.setText("[ F3 ] Ver Entregas");
            }
    }
    
    private void mostrarGuias(){
        DlgGuiasPendientes dlgGuias = new DlgGuiasPendientes(myParentFrame,"",true);
        dlgGuias.setVisible(true);        
    }
  
    private boolean cargaLogin()
    {
       boolean flag = true;
       //se guarda datos
       VariablesRecepCiega.vNuSecUsu=FarmaVariables.vNuSecUsu;
        VariablesRecepCiega.vIdUsu=FarmaVariables.vIdUsu;
        VariablesRecepCiega.vNomUsu=FarmaVariables.vNomUsu;
        VariablesRecepCiega.vPatUsu=FarmaVariables.vPatUsu;
        VariablesRecepCiega.vMatUsu=FarmaVariables.vMatUsu; 

      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
      dlgLogin.setVisible(true);
    
      if ( FarmaVariables.vAceptar ) {
      flag = true;         
      
      // se sete datos originales
          FarmaVariables.vNuSecUsu =VariablesRecepCiega.vNuSecUsu;
          FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
          FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
          FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
          FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;
          
        FarmaVariables.dlgLogin = dlgLogin;
        FarmaVariables.vAceptar = false;
     } else{       
         flag = false;
        }
         //cerrarVentana(false);
      return flag;
    }
}
