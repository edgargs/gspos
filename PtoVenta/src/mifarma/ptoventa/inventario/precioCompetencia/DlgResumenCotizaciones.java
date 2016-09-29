package mifarma.ptoventa.inventario.precioCompetencia;

import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;

import com.gs.mifarma.componentes.JPanelTitle;

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

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.cnx.FarmaVentaCnxUtility;
import mifarma.ptoventa.inventario.precioCompetencia.reference.ConstantsPrecioCompetencia;
import mifarma.ptoventa.inventario.precioCompetencia.reference.MultipartFileUploadApp;
import mifarma.ptoventa.inventario.precioCompetencia.reference.UtilityPrecioCompetencia;
import mifarma.ptoventa.inventario.precioCompetencia.reference.VariablesPrecioCompetencia;
import mifarma.ptoventa.reference.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgResumenCotizaciones extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgResumenCotizaciones.class);
  UtilityPrecioCompetencia utilityPrecioCompetencia = new UtilityPrecioCompetencia();
  FarmaTableModel tableModelResumenCotizacion;
  Frame myParentFrame;
  String vEstadoNota;
  public boolean verListaProductos = true;
  private boolean cabecera = false;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JScrollPane scrResumenCotizacion = new JScrollPane();
  private JPanel pnlHeader = new JPanel();
  private JButton btnResumenCotizacion = new JButton();
  private JTable tblResumenCotizacion = new JTable();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JLabel lblFecha = new JLabel();
  private JLabel lblTipoCotizacion_T = new JLabel();
  private JLabel lblFecha_T = new JLabel();
  private JLabel lblTipoCotizacion = new JLabel();
  private JLabel lblCompetidor_T = new JLabel();
  private JLabel lblCompetidor = new JLabel();
    private JLabelFunction lblF8 = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabel lblEstado = new JLabel();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
  private JLabelWhite lblTotal_T = new JLabelWhite();
  private JLabelWhite lblTotal = new JLabelWhite();
  
  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgResumenCotizaciones()
  {
    this(null, "", false);
  }

  public DlgResumenCotizaciones(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
      limpiarDatosCabecera();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  public DlgResumenCotizaciones(Frame parent, String title, boolean modal, boolean ver)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    verListaProductos = ver;
        
    try {
      jbInit();
      initialize();
    }
    catch(Exception e) {
      log.error("",e);
    }

  }
  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(705, 446));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Resumen Cotizaciones");
    this.setDefaultCloseOperation(0);
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
    jContentPane.setLayout(null);
    jContentPane.setBackground(Color.white);
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(678, 395));
    lblF5.setText("[ F5 ] Borrar Producto");
    lblF5.setBounds(new Rectangle(220, 350, 140, 20));
    lblF3.setText("[ F3 ] Insertar Producto Asignado");
    lblF3.setBounds(new Rectangle(10, 350, 200, 20));
    scrResumenCotizacion.setBounds(new Rectangle(10, 85, 660, 235));
    scrResumenCotizacion.setBackground(new Color(255, 130, 14));
    pnlHeader.setBounds(new Rectangle(10, 60, 660, 25));
    pnlHeader.setBackground(new Color(255, 130, 14));
    pnlHeader.setLayout(null);
    btnResumenCotizacion.setText("Resumen de Productos Cotizados");
    btnResumenCotizacion.setBounds(new Rectangle(10, 0, 205, 25));
    btnResumenCotizacion.setBackground(new Color(255, 130, 14));
    btnResumenCotizacion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnResumenCotizacion.setBorderPainted(false);
    btnResumenCotizacion.setContentAreaFilled(false);
    btnResumenCotizacion.setDefaultCapable(false);
    btnResumenCotizacion.setFocusPainted(false);
    btnResumenCotizacion.setFont(new Font("SansSerif", 1, 12));
    btnResumenCotizacion.setForeground(Color.white);
    btnResumenCotizacion.setHorizontalAlignment(SwingConstants.LEFT);
    btnResumenCotizacion.setMnemonic('r');
    btnResumenCotizacion.setRequestFocusEnabled(false);
    btnResumenCotizacion.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnResumenPedido_keyPressed(e);
        }
      });
    btnResumenCotizacion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnResumenPedido_actionPerformed(e);
        }
      });
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(565, 350, 100, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(565, 375, 100, 20));
    pnlHeader1.setBounds(new Rectangle(10, 10, 660, 40));
    lblFecha.setText("-");
    lblFecha.setBounds(new Rectangle(105, 10, 75, 20));
    lblFecha.setFont(new Font("SansSerif", 1, 11));
    lblFecha.setForeground(Color.white);
    lblTipoCotizacion_T.setText("Observaci\u00f3n:");
    lblTipoCotizacion_T.setBounds(new Rectangle(195, 10, 95, 20));
    lblTipoCotizacion_T.setFont(new Font("SansSerif", 1, 11));
    lblTipoCotizacion_T.setForeground(Color.white);
    lblFecha_T.setText("Fecha Creaci\u00f3n:");
    lblFecha_T.setBounds(new Rectangle(10, 10, 95, 20));
    lblFecha_T.setFont(new Font("SansSerif", 1, 11));
    lblFecha_T.setForeground(Color.white);
    lblTipoCotizacion.setText("-");
    lblTipoCotizacion.setBounds(new Rectangle(290, 10, 125, 20));
    lblTipoCotizacion.setFont(new Font("SansSerif", 1, 11));
    lblTipoCotizacion.setForeground(Color.white);
    lblCompetidor_T.setText("Competidor:");
    lblCompetidor_T.setBounds(new Rectangle(425, 10, 75, 20));
    lblCompetidor_T.setFont(new Font("SansSerif", 1, 11));
    lblCompetidor_T.setForeground(Color.white);
    lblCompetidor.setText("-");
    lblCompetidor.setBounds(new Rectangle(505, 10, 145, 20));
    lblCompetidor.setFont(new Font("SansSerif", 1, 11));
    lblCompetidor.setForeground(Color.white);
        lblF8.setText("[ F8 ] Insertar Prod. No Canasta");
    lblF8.setBounds(new Rectangle(370, 350, 190, 20));
    lblF2.setText("[ F2 ] Anular");
    lblF2.setBounds(new Rectangle(15, 405, 85, 20));
    lblEstado.setBounds(new Rectangle(535, 40, 95, 20));
    lblEstado.setFont(new Font("SansSerif", 1, 11));
    lblEstado.setForeground(Color.white);
        jPanelTitle1.setBounds(new Rectangle(10, 320, 660, 20));
    lblTotal_T.setText("TOTAL: S/.");
    lblTotal_T.setBounds(new Rectangle(480, 0, 70, 20));
    lblTotal.setBounds(new Rectangle(555, 0, 85, 20));
    lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotal.setText("0.00");
    scrResumenCotizacion.getViewport();
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    pnlHeader1.add(lblEstado, null);
        pnlHeader1.add(lblCompetidor, null);
        pnlHeader1.add(lblCompetidor_T, null);
        pnlHeader1.add(lblTipoCotizacion, null);
        pnlHeader1.add(lblFecha_T, null);

        pnlHeader1.add(lblTipoCotizacion_T, null);
        pnlHeader1.add(lblFecha, null);
        jPanelTitle1.add(lblTotal, null);
        jPanelTitle1.add(lblTotal_T, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(pnlHeader1, null);
        jContentPane.add(lblEsc, null);
        scrResumenCotizacion.getViewport().add(tblResumenCotizacion, null);
        jContentPane.add(scrResumenCotizacion, null);
        pnlHeader.add(btnResumenCotizacion, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF8, null);

    }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    initTableResumenPedido();  
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initTableResumenPedido() {
    tableModelResumenCotizacion = new FarmaTableModel(ConstantsPrecioCompetencia.columnsListaResumenCotizacion,ConstantsPrecioCompetencia.defaultListaResumenCotizacion,0);
    FarmaUtility.initSimpleList(tblResumenCotizacion,tableModelResumenCotizacion,ConstantsPrecioCompetencia.columnsListaResumenCotizacion);
  }
  
  
  private void cargaListaResumenPedido() {
    tableModelResumenCotizacion.clearTable();
      
    if(VariablesPrecioCompetencia.vArrayProductosCotizados.size()>0) {
      ArrayList prods = VariablesPrecioCompetencia.vArrayProductosCotizados;
      for(int i=0;i<prods.size();i++) {
        tableModelResumenCotizacion.insertRow(((ArrayList)prods.get(i)));    
      }
      prods = null;
    }
    calcularTotal();
  }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

  private void btnResumenPedido_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(btnResumenCotizacion);
  }
  
  private void btnResumenPedido_keyPressed(KeyEvent e) {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e) {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(btnResumenCotizacion);
    //mostrarListadoProductos();
    if(verListaProductos)
    mostrarCabecera();
    
  }
  
  private void tblResumenPedido_keyPressed(KeyEvent e)
  {
   chkKeyPressed(e);
  }
  
        /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblResumenCotizacion,null,0);
    
    if(e.getKeyCode() == KeyEvent.VK_F3)
    {
        if(cabecera){
        
            mostrarListadoProductos();
        }else{
            FarmaUtility.showMessage(this,"Debe seleccionar Tipo Cotizacion y Competidor!!, volver a la ventana principal",btnResumenCotizacion);  
            }
    }else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      if(cabecera)
      mostrarListadoProductosNoCanasta();
    }else if(e.getKeyCode() == KeyEvent.VK_F5)
    {
      if(verListaProductos)
      {
        if(tblResumenCotizacion.getSelectedRow()>-1)
        {  
          if (JConfirmDialog.rptaConfirmDialog(this, "Está seguro de borrar el producto cotizado?"))
            borrarProducto();
        }
        else
          FarmaUtility.showMessage(this,"Debe seleccionar un producto",btnResumenCotizacion);  
      }
    }else if(UtilityPtoVenta.verificaVK_F11(e))
    {
      if(validaDatos() && verListaProductos)
        if (JConfirmDialog.rptaConfirmDialog(this, "Está seguro de enviar Precios Cotizados?"))
        {
          if(grabar())
          {
            
             FarmaUtility.aceptarTransaccion();
             FarmaUtility.showMessage(this, "Precios Cotizados satisfactoriamente!", tblResumenCotizacion);
             cerrarVentana(true);
            
          }
        }
    } 
    else if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(verListaProductos)
      {
        if(tblResumenCotizacion.getSelectedRow()>-1)
        {
          cargarCabecera();
          DlgCotizacionCantidad dlgCotizacionCantidad = new DlgCotizacionCantidad(myParentFrame,"",true);
          dlgCotizacionCantidad.setVisible(true);
          if(FarmaVariables.vAceptar)
          {
            actualizarProducto();
            FarmaVariables.vAceptar = false;
          }
        }else
          FarmaUtility.showMessage(this,"Debe seleccionar un producto",btnResumenCotizacion);
      }
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar) {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", btnResumenCotizacion);
  }
  
  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */

  private void mostrarListadoProductos() 
  {
    if (verListaProductos)
    {
      DlgSeleccionarProductosPorCotizar dlgSeleccionarProductos = new DlgSeleccionarProductosPorCotizar(myParentFrame,"",true);
      dlgSeleccionarProductos.setVisible(true);
      
      if(FarmaVariables.vAceptar)
      {
        cargaListaResumenPedido();
        FarmaVariables.vAceptar = false;
      }
    }
  }
  
    private void mostrarListadoProductosNoCanasta() 
    {
      if (verListaProductos)
      {
        DlgSeleccionarProductosNoCanasta dlgSeleccionarProductos = new DlgSeleccionarProductosNoCanasta(myParentFrame,"",true);
        dlgSeleccionarProductos.setVisible(true);
        
        if(FarmaVariables.vAceptar)
        {
          cargaListaResumenPedido();
          FarmaVariables.vAceptar = false;
        }
      }
    }
  
  private void borrarProducto()
  {
    String cod = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),0).toString();
    
    for(int i=0;i<VariablesPrecioCompetencia.vArrayProductosCotizados.size();i++)
    {
      if(((ArrayList)VariablesPrecioCompetencia.vArrayProductosCotizados.get(i)).contains(cod))
      {
        VariablesPrecioCompetencia.vArrayProductosCotizados.remove(i);
        break;
      }
    }
    cargaListaResumenPedido();
    tblResumenCotizacion.repaint();
  }
  
  private void cargarCabecera()
  {
    VariablesPrecioCompetencia.vCodProd = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),0).toString();
    VariablesPrecioCompetencia.vNomProd = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),1).toString();
    VariablesPrecioCompetencia.vUnidMed = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),2).toString();
    VariablesPrecioCompetencia.vNomLab = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),3).toString();
    
    VariablesPrecioCompetencia.vCant = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),4).toString();
    VariablesPrecioCompetencia.vPrecUnit = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),5).toString();
    VariablesPrecioCompetencia.vImagen = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),7).toString();
    VariablesPrecioCompetencia.vValFrac = tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),8).toString();
  }
  
  private void actualizarProducto()
  {
    ((ArrayList)(VariablesPrecioCompetencia.vArrayProductosCotizados.get(tblResumenCotizacion.getSelectedRow()))).set(4,VariablesPrecioCompetencia.vCant);
    ((ArrayList)(VariablesPrecioCompetencia.vArrayProductosCotizados.get(tblResumenCotizacion.getSelectedRow()))).set(7,VariablesPrecioCompetencia.vImagen);
    ((ArrayList)(VariablesPrecioCompetencia.vArrayProductosCotizados.get(tblResumenCotizacion.getSelectedRow()))).set(8,VariablesPrecioCompetencia.vValFrac);
    ((ArrayList)(VariablesPrecioCompetencia.vArrayProductosCotizados.get(tblResumenCotizacion.getSelectedRow()))).set(5,VariablesPrecioCompetencia.vPrecUnit);
    ((ArrayList)(VariablesPrecioCompetencia.vArrayProductosCotizados.get(tblResumenCotizacion.getSelectedRow()))).set(6,VariablesPrecioCompetencia.vTotal);
    
    cargaListaResumenPedido();
  }
  
  private void mostrarDatos()
  {
    lblFecha.setText(VariablesPrecioCompetencia.vFec);
    lblTipoCotizacion.setText(VariablesPrecioCompetencia.vDescTipoCotizacion);
    lblCompetidor.setText(VariablesPrecioCompetencia.vDescCompetidor);
  }

  private boolean validaDatos()
  {
    boolean retorno = true;
    if(!FarmaUtility.validaFecha(lblFecha.getText().trim(),"dd/MM/yyyy"))
    {
      FarmaUtility.showMessage(this,"Debe ingresar los datos de Cabecera.",btnResumenCotizacion);
      retorno = false;
    }else if(tblResumenCotizacion.getRowCount()==0)
    {
      FarmaUtility.showMessage(this,"No ha ingresado productos a esta guia.",btnResumenCotizacion);
      retorno = false;
    }
    
    return retorno;
  }
  
  private boolean grabar()
  {
    boolean retorno = true;
    try
    {
      for(int i=0;i<tblResumenCotizacion.getRowCount();i++)
      {
          //inserta producto cotizado
          //formato nombre del archivo: secSet-codLocal-codCompetidor-codProducto
          File archivo = (File)((ArrayList)VariablesPrecioCompetencia.vArrayProductosCotizados.get(i)).get(9);    
          seteaVariablesCotizacion(i,archivo);  
          long secuencial = utilityPrecioCompetencia.registraProductoCotizadoDAO(VariablesPrecioCompetencia.vCodProd, 
                                                                                 FarmaVariables.vCodLocal, 
                                                                                 FarmaVariables.vIdUsu, 
                                                                                 VariablesPrecioCompetencia.vCodTipoCotizacion, 
                                                                                 VariablesPrecioCompetencia.vValFrac, 
                                                                                 VariablesPrecioCompetencia.vCant, 
                                                                                 VariablesPrecioCompetencia.vPrecUnit, 
                                                                                 VariablesPrecioCompetencia.vCodCompetidor, 
                                                                                 VariablesPrecioCompetencia.vCodSustento,
                                                                                 "",
                                                                                 "",
                                                                                 VariablesPrecioCompetencia.vImagen, 
                                                                                 ConstantsPrecioCompetencia.CONDICION_COTIZAR,
                                                                                 "",
                                                                                 "");
          if(VariablesPrecioCompetencia.vImagen !=null && archivo!=null){
            File archivoNuevo = new File(String.valueOf(secuencial)+"-"+VariablesPrecioCompetencia.vImagen);  
            FileUtils.copyFile(archivo,archivoNuevo);
            if(secuencial > 0){ //grabó cotización 
                try{  
                    boolean b = MultipartFileUploadApp.upload(archivoNuevo);//Envia a upload PHP 
                    if(!b){
                        b = MultipartFileUploadApp.upload(archivoNuevo);//Reenvia a upload PHP
                        if(!b){
                            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                    FarmaVariables.vCodLocal,
                                    ConstantsPrecioCompetencia.CORREO_NOTIFICACION_TI,
                                    "Error en envio imagen a GPrecios ",
                                    "Error en envio imagen a GPrecios - Ingreso Cotizacion",
                                    "Error en envio de imagen a GPrecios desde "+ ConstantsPrecioCompetencia.URL_UPLOAD_PHP + " <br>"+ 
                                    "Archivo: "+ archivoNuevo.getName() + "<br>"+ 
                                    "IP PC: " + FarmaVariables.vIpPc + "<br>",
                                    ""); 
                            File archivoLocal = new File("\\\\" + FarmaVariables.vIPBD+ConstantsPrecioCompetencia.RUTA_IMAGEN_SUSTENTO+VariablesPrecioCompetencia.vImagen);
                            FileUtils.moveFile(archivoNuevo, archivoLocal);
                        }
                    }  
                }catch(Exception e){      
                    FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                FarmaVariables.vCodLocal,
                                ConstantsPrecioCompetencia.CORREO_NOTIFICACION_TI,
                                "Error en envio imagen a GPrecios ",
                                "Error en envio imagen a GPrecios - Ingreso Cotizacion",
                                "Error en envio de imagen a GPrecios desde "+ ConstantsPrecioCompetencia.URL_UPLOAD_PHP + " <br>"+ 
                                "Archivo: "+ archivoNuevo.getName() + "<br>"+ 
                                "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                                "Error: " + ""+e.getMessage() ,
                                "");
                    File archivoLocal = new File("\\\\" + FarmaVariables.vIPBD+ConstantsPrecioCompetencia.RUTA_IMAGEN_SUSTENTO+VariablesPrecioCompetencia.vImagen);
                    FileUtils.moveFile(archivoNuevo, archivoLocal);
                }
              }
            archivoNuevo.delete();
          }
           VariablesPrecioCompetencia.vImagen = "" ;     
       }
      limpiarDatosCabecera();
      retorno = true;
      limpiaVariables();   
    }catch(Exception ex)
    {
      log.error("",ex);
      VariablesPrecioCompetencia.vImagen = "" ; 
      FarmaUtility.showMessage(this, "Error en la aplicacion al grabar los datos de la cotización : \n"+ex.getMessage(),btnResumenCotizacion);
      retorno = false;
    }
    return retorno;   
  }

  private void limpiarDatosCabecera(){
	  VariablesPrecioCompetencia.vFec = "";
	  VariablesPrecioCompetencia.vCodTipoCotizacion = "";
	  VariablesPrecioCompetencia.vCodCompetidor = "";
          VariablesPrecioCompetencia.vDescTipoCotizacion = "";
          VariablesPrecioCompetencia.vDescCompetidor = "";
  }

  private void mostrarCabecera() {
        DlgCotizacionCabecera dlgCotizacionCabecera = new DlgCotizacionCabecera(myParentFrame,"",true);
    dlgCotizacionCabecera.setVisible(true);
    if(FarmaVariables.vAceptar) {  
      mostrarDatos();
      cabecera = true;
      if(existeProductosxCotizar()){
         mostrarListadoProductos();
      }else{
         mostrarListadoProductosNoCanasta(); 
      }
      
      FarmaVariables.vAceptar = false;
    }
  }
  
  private void calcularTotal()
  {
    double total = 0.00;
    int cantidad = tblResumenCotizacion.getRowCount();
    
    if (cantidad > 0)
    {
      for (int i = 0; i < cantidad; i++)
      {
        total += 
            FarmaUtility.getDecimalNumber(tblResumenCotizacion.getValueAt(i,6).toString().trim());
      }  
    }    
    
    lblTotal.setText(FarmaUtility.formatNumber(total, 2));
  }
  
  private void seteaVariablesCotizacion(int i, File pArchivo){
      //formato nombre del archivo: secSet-codLocal-codCompetidor-codProducto
            
      VariablesPrecioCompetencia.vCodSustento=ConstantsPrecioCompetencia.SUSTENTO_ENCUESTA;      
                  
      double total = 0.00;
      VariablesPrecioCompetencia.vCodProd = tblResumenCotizacion.getValueAt(i,0).toString();
      VariablesPrecioCompetencia.vCant = tblResumenCotizacion.getValueAt(i,4).toString();
      VariablesPrecioCompetencia.vPrecUnit = tblResumenCotizacion.getValueAt(i,5).toString();
      if(pArchivo!=null){
              VariablesPrecioCompetencia.vImagen = FarmaVariables.vCodLocal+"-"+VariablesPrecioCompetencia.vCodCompetidor+"-"+tblResumenCotizacion.getValueAt(i,0).toString()+"."+FilenameUtils.getExtension(pArchivo.getName());//tblResumenCotizacion.getValueAt(tblResumenCotizacion.getSelectedRow(),7).toString();
          }
      VariablesPrecioCompetencia.vValFrac = tblResumenCotizacion.getValueAt(i,8).toString();   
  }
  
  private void limpiaVariables(){
          VariablesPrecioCompetencia.vCodSustento="";      
          VariablesPrecioCompetencia.vCodCompetidor="";
          VariablesPrecioCompetencia.vCodTipoCotizacion="";
          VariablesPrecioCompetencia.vCodProd = "";
          VariablesPrecioCompetencia.vCant = "";
          VariablesPrecioCompetencia.vPrecUnit = "";
          VariablesPrecioCompetencia.vImagen = "";
          VariablesPrecioCompetencia.vValFrac = "";
      }
  
  private boolean existeProductosxCotizar(){
      FarmaTableModel tableModel;
      JTable tblListaProductos = new JTable();
      boolean existe = false;
      tableModel = new FarmaTableModel(ConstantsPrecioCompetencia.columnsSeleccionarListaProductosPorCotizar,ConstantsPrecioCompetencia.defaultcolumnsSeleccionarListaProductosPorCotizar,0);
      try
      {
        utilityPrecioCompetencia.cargaSeleccionProductosPorCotizarDAO(tableModel, tblListaProductos);
        if(tableModel.getRowCount()>0)
            existe = true;
      } catch(Exception sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Error al cargar la lista de productos : \n" + sql.getMessage(),null);
        }finally{
          return existe;
          }
    }
  
}