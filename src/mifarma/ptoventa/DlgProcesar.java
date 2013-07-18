package mifarma.ptoventa;

import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Frame;
import java.sql.SQLException;

import java.util.ArrayList;

import  mifarma.ptoventa.ventas.reference.UtilityVentas;
import java.util.Collections;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;

import javax.swing.JLabel;

import javax.swing.JOptionPane;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableComparator;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;


import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesar extends JDialog 
{
  public String vQuery = "";
  FarmaTableModel tableModelProcesar;
  private BorderLayout borderLayout1 = new BorderLayout();
  private static JPanelWhite jContentPane = new JPanelWhite();
  
  //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
  private static final Logger log = LoggerFactory.getLogger(UtilityCaja.class);
  
    private static Frame myParentFrame;
    private static JLabel lbloculto = new JLabel();


    public DlgProcesar()
  {
    this(null, "", false);
  }

  public DlgProcesar(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
      myParentFrame= parent;
    try
    {
      jbInit();
        //initialize();
      
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(238, 104));
    this.getContentPane().setLayout(null);
    this.setTitle("Procesando Información . . .");
    this.getContentPane().setLayout(borderLayout1);
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
        lbloculto.setText("");
        lbloculto.setVisible(false);
        lbloculto.setBounds(new Rectangle(120, 40, 34, 15));
        jContentPane.add(lbloculto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    //lapaz   17.09.2010
    //VariablesVentas.tableModelListaGlobalProductos = new FarmaTableModel(ConstantsVentas.columnsListaProductos,ConstantsVentas.defaultValuesListaProductos,0);
     try{
      /**
       * Cargara Variables de Titulos Dinamicos
       * @author :  
       * @since  : 21.08.2007
       */
      VariablesPtoVenta.vNumeroDiasSinVentas = DBVentas.obtieneNumeroDiasSinVentas();
      System.out.println("VariablesPtoVenta.vNumeroDiasSinVentas : " + VariablesPtoVenta.vNumeroDiasSinVentas);
      /*lapaz   17.09.2010       
      DBVentas.cargaListaProductosVenta(VariablesVentas.tableModelListaGlobalProductos);    
      Collections.sort(VariablesVentas.tableModelListaGlobalProductos.data,new FarmaTableComparator(2,true));
      
      /**
       * Carga los productos para Pedido Especial
       * @author :  
       * @since  : 18.10.08
       * /
      DBInventario.cargaListaProductosEspeciales(VariablesInventario.tableModelEspecial);
      Collections.sort(VariablesInventario.tableModelEspecial.data,new FarmaTableComparator(2,true));
      */
      /*
      //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
      //carga_impresoras();
        //  19.08.2010
        if(!FarmaVariables.vEconoFar_Matriz)  {
        UtilityVentas.carga_impresoras(myParentFrame);
        cargaIndImpresionRojoTicket();
        }
	   */
      cerrarVentana(true);
      
      //  04/08/09
      cargaDestinatarioEmailErrorCobro();
      cargaDestinatarioEmailErrorAnulacion();
      cargaDestinatarioEmailErrorImpresion();
      cargaIndVerStockLocales();
        
    } catch (SQLException err) {
      err.printStackTrace();
      FarmaUtility.showMessage(this, "Error al obtener informacion relevante de la aplicacion.", null);
      cerrarVentana(false);
    }
  }
  /*
    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void carga_impresoras()
       {
           PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
           boolean pEncontroImp = false;
                 if(servicio != null)
                 {
                   try
                   {
                     String pNombreImpresora = "";
                     //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                     VariablesPtoVenta.vIndExisteImpresoraConsejo = DBCaja.obtieneNameImpConsejos();
                     VariablesPtoVenta.vTipoImpTermicaxIp=DBCaja.obtieneTipoImprConsejoXIp();//  03.07.2009 obtiene tipo de imopresora por IP
                       System.out.println("Tipo Impresora :" + VariablesPtoVenta.vTipoImpTermicaxIp);  
                     System.out.println("Buscando impresora :"+VariablesPtoVenta.vIndExisteImpresoraConsejo);
                     System.out.println("impresoras..encontradas...");
                     for (int i = 0; i < servicio.length; i++)
                     {
                       PrintService impresora = servicio[i];
                       String pNameImp = impresora.getName().toString().trim();
                       pNombreImpresora = retornaUltimaPalabra(pNameImp,"\\").trim();
                       //if (pNameImp.toUpperCase().indexOf(VariablesPtoVenta.vIndExisteImpresoraConsejo.toUpperCase()) != -1)
                       //Buscara el nombre.
                       System.out.println(i+") pNameImp:"+pNameImp);
                       System.out.println(i+") pNombreImpresora:"+pNombreImpresora);
                       System.out.println("**************************************");
                       if (pNombreImpresora.trim().toUpperCase().equalsIgnoreCase(VariablesPtoVenta.vIndExisteImpresoraConsejo.toUpperCase()))
                       {
                         System.err.println("Encotró impresora térmica");
                         pEncontroImp = true;
                         VariablesPtoVenta.vImpresoraActual =  impresora;
                         break;
                       }
                     }
                     
                     /**0
                      * 03/07/2009 
                      *   
                      * se genero error en produccion
                      * String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                     
                     for (int i = 0; i < servicio.length; i++)
                     {
                       PrintService impresora = servicio[i];
                       String pNameImp = impresora.getName().toString().trim();
                       
                       if (pNameImp.indexOf(vIndExisteImpresora) != -1)
                       {
                         VariablesPtoVenta.vImpresoraActual =  impresora;
                         break;
                       }
                     } */
                   /*    
                   }
                   catch (SQLException sqlException)
                   {
                     log.error(null,sqlException);
                   }
                 }
                 
                 if(!pEncontroImp){
                     JOptionPane.showMessageDialog(myParentFrame, "No se encontró la impresora de térmica :"+
                                                   VariablesPtoVenta.vIndExisteImpresoraConsejo+
                                                   "\nVerifique que tenga instalada la impresora.", 
                                                   "Mensaje del Sistema", 
                                                   JOptionPane.WARNING_MESSAGE);
                 }
       }*/
  
  
                   /**
                    * Asigna codigo y descripcion Moneda.
                    * @author  JSANTIVANEZ
                    * @since   02.07.2010
                    */  
                   private void initialize()
                   {   
                    // jquispe se valida el indicador verificador de cajero y su caja. 
                       VariablesCaja.vVerificaCajero = true;// DBCaja.cargaVar_verifCajero(); 
                    
                   }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
    private void cargaIndImpresionRojoTicket(){
        String pResultado = "";
     try
     {
        pResultado  =   DBVentas.getIndImprimeRojo();
         System.err.println("pResultado:"+pResultado);    
         if(pResultado.trim().equalsIgnoreCase("S"))
            VariablesPtoVenta.vIndImprimeRojo = true;
         else
            VariablesPtoVenta.vIndImprimeRojo = false;
     }
     catch(SQLException err){
        err.printStackTrace();
        VariablesPtoVenta.vIndImprimeRojo = false;
     }
     
     System.err.println("VariablesPtoVenta.vIndImprimeRojo:"+VariablesPtoVenta.vIndImprimeRojo);
    }
    
    //  04/08/09 
    //OBTIENE EL DESTINATARIO PARA ENVIAR EMAIL ERROR COBRO
    private void cargaDestinatarioEmailErrorCobro(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getDestinatarioErrorCobro();
           VariablesPtoVenta.vDestEmailErrorCobro = pResultado; 
           System.err.println("VarialesPtoVenta.vDestEmailErrorCobro:"+pResultado);               
        }
        catch(SQLException err){
           err.printStackTrace();           
        }             
    }
    
    //  04/08/09 
    //OBTIENE EL DESTINATARIO PARA ENVIAR EMAIL ERROR ANULACION
    private void cargaDestinatarioEmailErrorAnulacion(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getDestinatarioErrorAnulacion();
           VariablesPtoVenta.vDestEmailErrorAnulacion = pResultado;
           System.err.println("VarialesPtoVenta.vDestEmailErrorAnulacion:"+pResultado);                
        }
        catch(SQLException err){
           err.printStackTrace();
        }                
    }
    
    //  04/08/09 
    //OBTIENE EL DESTINATARIO PARA ENVIAR EMAIL ERROR IMPRESION
    private void cargaDestinatarioEmailErrorImpresion(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getDestinatarioErrorImpresion();
           VariablesPtoVenta.vDestEmailErrorImpresion = pResultado;
           System.err.println("VarialesPtoVenta.vDestEmailErrorImpresion:"+pResultado);                
        }
        catch(SQLException err){
           err.printStackTrace();
        }                      
    }
    
    
    
    private void cargaIndVerStockLocales(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getIndVerStockLocales();
           VariablesPtoVenta.vIndVerStockLocales = pResultado;
           System.err.println("VariablesPtoVenta.vIndVerStockLocales:"+pResultado);                
        }
        catch(SQLException err){
           VariablesPtoVenta.vIndVerStockLocales = "N";
           err.printStackTrace();
        }                      
    }
 
  
}