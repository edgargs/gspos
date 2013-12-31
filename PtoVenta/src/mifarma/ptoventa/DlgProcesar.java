package mifarma.ptoventa;


import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.worker.JDialogProgress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesar extends JDialogProgress
{
  
  FarmaTableModel tableModelProcesar;
  private BorderLayout borderLayout1 = new BorderLayout();
  private static JPanelWhite jContentPane = new JPanelWhite();
  
  private static final Logger log = LoggerFactory.getLogger(DlgProcesar.class);
  
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
      //jbInit();
    }
    catch(Exception e)
    {
        log.error("",e);
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

    void this_windowOpened(WindowEvent e) {
        //ERIOS 16.07.2013 Leer indicador de recargas FarmaSix
        FarmaUtility.centrarVentana(this);
        try {
            VariablesPtoVenta.vNumeroDiasSinVentas = DBVentas.obtieneNumeroDiasSinVentas();


            cargaDestinatarioEmailErrorCobro();
            cargaDestinatarioEmailErrorAnulacion();
            cargaDestinatarioEmailErrorImpresion();
            cargaIndVerStockLocales();

            cargaIndVerRecetarioMagistral();
            grabarImagenesDisco();
            cargaIndServicioFarmaSix();
            cargaIndPinpad();
            cargaIndImprWeb();
            
            cerrarVentana(true);
        } catch (SQLException err) {
            log.error("",err);
            FarmaUtility.showMessage(this, "Error al obtener informacion relevante de la aplicacion.", null);
            cerrarVentana(false);
        }
    }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
    public void cargaIndImpresionRojoTicket(){
        String pResultado = "";
     try
     {
        pResultado  =   DBVentas.getIndImprimeRojo();
         log.info("pResultado:"+pResultado);    
         if(pResultado.trim().equalsIgnoreCase("S"))
            VariablesPtoVenta.vIndImprimeRojo = true;
         else
            VariablesPtoVenta.vIndImprimeRojo = false;
     }
     catch(SQLException err){
         log.error("",err);
        VariablesPtoVenta.vIndImprimeRojo = false;
     }
     
     log.info("VariablesPtoVenta.vIndImprimeRojo:"+VariablesPtoVenta.vIndImprimeRojo);
    }
    
    private void cargaDestinatarioEmailErrorCobro(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getDestinatarioErrorCobro();
           VariablesPtoVenta.vDestEmailErrorCobro = pResultado; 
        }
        catch(SQLException err){
            log.error("",err);
        }             
    }
    
    private void cargaDestinatarioEmailErrorAnulacion(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getDestinatarioErrorAnulacion();
           VariablesPtoVenta.vDestEmailErrorAnulacion = pResultado;
        }
        catch(SQLException err){
            log.error("",err);
        }                
    }
    
    private void cargaDestinatarioEmailErrorImpresion(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getDestinatarioErrorImpresion();
           VariablesPtoVenta.vDestEmailErrorImpresion = pResultado;
        }
        catch(SQLException err){
            log.error("",err);
        }                      
    }
    
    private void cargaIndVerStockLocales(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getIndVerStockLocales();
           VariablesPtoVenta.vIndVerStockLocales = pResultado;
        }
        catch(SQLException err){
           VariablesPtoVenta.vIndVerStockLocales = "N";
            log.error("",err);
        }                      
    }
 
    private void cargaIndVerRecetarioMagistral(){
        String pResultado = "";
        try
        {
           pResultado  =   DBVentas.getIndVerRecetarioMagis();
           VariablesPtoVenta.vIndVerReceMagis = pResultado;
        }
        catch(SQLException err){
            log.error("",err);
           VariablesPtoVenta.vIndVerReceMagis = "N";
        }                      
    }

    /**
     * Grabas la imagenes del programa en el disco duro
     * @author ERIOS
     * @since 24.06.2013
     */
    private void grabarImagenesDisco() {        
        try {
            String sufijoEmpresa = DBPtoVenta.obtieneRutaImagen();
            String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
            String carpetaImagenes = DBPtoVenta.getDirectorioImagenes();
            String carpetaComprobantes = DBPtoVenta.getDirectorioComprobantes();
            
            //Crear carpeta raiz
            Path dir = Paths.get(carpetaRaiz);
            if(Files.notExists(dir)){
                Files.createDirectory(dir);
            }

            //Crear carpeta comprobantes
            dir = Paths.get(carpetaRaiz,carpetaComprobantes);
            if(Files.notExists(dir)){
                Files.createDirectory(dir);
            }
            
            //Crear carpeta imagenes
            dir = Paths.get(carpetaRaiz,carpetaImagenes);
            if(Files.notExists(dir)){
                Files.createDirectory(dir);
            }
            //Elimina el contenido del directorio
            //Verificar el metodo que borra codigos de barra
            /*if(Files.exists(dir)){
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir) ){
                    for (Path p : ds) {
                        Files.delete(p);
                    }
                } catch (IOException e) {
                    log.error("",e);
                }
            }*/
            //Copiar imagenes
            Path archivo = Paths.get(carpetaRaiz,carpetaImagenes,"Logo"+sufijoEmpresa);            
            URL u = FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/Logo"+sufijoEmpresa);            
            try (InputStream in = u.openStream()) {
                     Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
                 }
            //consejos
            archivo = Paths.get(carpetaRaiz,carpetaImagenes,"consejo.jpg");            
            u = FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/consejo.jpg");            
            try (InputStream in = u.openStream()) {
                     Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
                 }
        } catch (IOException e) {
            log.error("Error al grabar imagenes al disco",e);
        } catch (SQLException e) {
            log.error("Error al recuperar informacion de la BBDD",e);
        }
    }

    /**
     * Indicador de servicios FarmaSix
     * @author ERIOS
     * @since 16.07.2013
     */
    private void cargaIndServicioFarmaSix() {
        String pResultado = "";
        try
        {
           pResultado = DBPtoVenta.getIndServicioFarmaSix();
           VariablesPtoVenta.vIndFarmaSix = pResultado;
        }
        catch(SQLException err){
            log.error("Error al ",err);
           VariablesPtoVenta.vIndFarmaSix = "N";
        }     
    }

    /**
     * Indicador de Pinpad
     * @author ERIOS
     * @since 16.08.2013
     */
    private void cargaIndPinpad() {
        String pResultado = "";
        try
        {
           pResultado = DBPtoVenta.getIndPinpad();
           VariablesPtoVenta.vIndPinpad = pResultado;
        }
        catch(SQLException err){
            log.error("Error al ",err);
           VariablesPtoVenta.vIndPinpad = "N";
        }     
    }   
    
    /**
     * Indicador de Impresion url web
     * @author ERIOS
     * @since 16.08.2013
     */
    private void cargaIndImprWeb() {
        String pResultado = "";
        try
        {
           pResultado = DBPtoVenta.getIndImprWeb();
           VariablesPtoVenta.vIndImprWeb = pResultado;
        }
        catch(SQLException err){
            log.error("Error al ",err);
           VariablesPtoVenta.vIndImprWeb = "S";
        }     
    }

    @Override
    public void ejecutaProceso() {

        try {
            VariablesPtoVenta.vNumeroDiasSinVentas = DBVentas.obtieneNumeroDiasSinVentas();
            cargaDestinatarioEmailErrorCobro();
            cargaDestinatarioEmailErrorAnulacion();
            cargaDestinatarioEmailErrorImpresion();
            cargaIndVerStockLocales();

            cargaIndVerRecetarioMagistral();
            grabarImagenesDisco();
            cargaIndServicioFarmaSix();
            cargaIndPinpad();
            cargaIndImprWeb();
            Thread.sleep(1000);
            Thread.sleep(1000);
        } catch (SQLException|InterruptedException  err) {
            log.error("", err);
            FarmaUtility.showMessage(this, "Error al obtener informacion relevante de la aplicacion.", null);
        }
    }

    /**
     * Indicador de Conciliacion En Linea
     * @author ERIOS
     * @since 29.11.2013
     */
    public static String cargaIndConciliaconOnline() {
        String pResultado = "";
        try
        {
           pResultado = DBPtoVenta.getIndConciliaconOnline();
        }
        catch(SQLException err){            
            log.error("Error al ",err);
            pResultado = "N";
        }    
        return pResultado;
    }
}
