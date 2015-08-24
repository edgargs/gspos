package mifarma.ptoventa.inventario.precioCompetencia.reference;

import java.awt.Frame;

import java.io.File;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JTable;

import javax.swing.filechooser.FileNameExtensionFilter;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cnx.FarmaVentaCnxUtility;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import oracle.jdbc.OracleTypes;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityPrecioCompetencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CLARICO       11.12.2014   Creación<br>
 * <br>
 * @author Celso Ever Larico Mullisaca<br>
 * @version 1.0<br>
 *
 */

public class UtilityPrecioCompetencia {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityPrecioCompetencia.class);
    private FarmaVentaCnxUtility cnxUtil = new FarmaVentaCnxUtility();

    public UtilityPrecioCompetencia() {
    }
    
    public void cargaListaProductosPorCotizarDAO(FarmaTableModel pTableModel, JTable tblLista)
    {
        pTableModel.clearTable();
        tblLista.repaint();
        Connection conn= cnxUtil.getConexionGestionPrecio();
        ArrayList localAsignado = new ArrayList();
        
        try
        {
            CallableStatement stmt = conn.prepareCall("{ ? = call ADMCENTRAL_GP_COTIZACION.PTOVENTA_LISTA_PRODS_X_COTIZAR(?)}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, FarmaVariables.vCodLocal);
            stmt.execute();
            ResultSet results = (ResultSet)stmt.getObject(1);              
            while(results.next())
            {
                localAsignado = new ArrayList();
                localAsignado.add(results.getString(1));
                localAsignado.add(results.getString(2));
                localAsignado.add(results.getString(3));
                localAsignado.add(results.getString(4));
                localAsignado.add(results.getString(5));
                
                pTableModel.insertRow(localAsignado);               
            }              
            results.close();
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException cargaListaProductosPorCotizarDAO  <> "+e.getMessage());
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception cargaListaProductosPorCotizarDAO <> "+eq.getMessage());
        }
    }
    
    public void cargaSeleccionProductosPorCotizarDAO(FarmaTableModel pTableModel, JTable tblLista)
    {
        pTableModel.clearTable();
        tblLista.repaint();
        Connection conn= cnxUtil.getConexionGestionPrecio();
        ArrayList localAsignado = new ArrayList();
        
        try
        {
            CallableStatement stmt = conn.prepareCall("{ ? = call ADMCENTRAL_GP_COTIZACION.PTOVENTA_LISTA_PRODS_X_COTIZAR(?)}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, FarmaVariables.vCodLocal);
            stmt.execute();
            ResultSet results = (ResultSet)stmt.getObject(1);              
            while(results.next())
            {
                localAsignado = new ArrayList();
                localAsignado.add(false);
                localAsignado.add(results.getString(1));
                localAsignado.add(results.getString(2));
                localAsignado.add(results.getString(3));
                localAsignado.add(results.getString(4));
                localAsignado.add(results.getString(5));
                
                pTableModel.insertRow(localAsignado);               
            }              
            results.close();
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException cargaSeleccionProductosPorCotizarDAO  <> "+e.getMessage());
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception cargaSeleccionProductosPorCotizarDAO <> "+eq.getMessage());
        }
    }    

    public void cargaComboTipoPrecioDAO(ArrayList pCodigos,ArrayList pDesripciones)
    {   
        Connection conn= cnxUtil.getConexionGestionPrecio();
       
        try
        {
            CallableStatement stmt = conn.prepareCall("{ ? = call ADMCENTRAL_GP_COTIZACION.FN_LISTA_TIPO_PRECIO}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet results = (ResultSet)stmt.getObject(1);              
            while(results.next())
            {
                pCodigos.add(results.getString(1).split("Ã")[0]);
                pDesripciones.add(results.getString(1).split("Ã")[1]);
            }              
            results.close();
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException cargaComboTipoPrecioDAO  <> "+e.getMessage());
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception cargaComboTipoPrecioDAO <> "+eq.getMessage());
        }
    }    
    
    
    public void cargaComboCompetidorDAO(ArrayList pCodigos,ArrayList pDesripciones)
    {   
        Connection conn= cnxUtil.getConexionGestionPrecio();
        try
        {
            CallableStatement stmt = conn.prepareCall("{ ? = call ADMCENTRAL_GP_COTIZACION.FN_LISTA_COMPETENCIA}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet results = (ResultSet)stmt.getObject(1);              
            while(results.next())
            {
                pCodigos.add(results.getString(1).split("Ã")[0]);
                pDesripciones.add(results.getString(1).split("Ã")[1]);
            }              
            results.close();
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException cargaComboCompetidorDAO  <> "+e.getMessage());
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception cargaComboCompetidorDAO <> "+eq.getMessage());
        }
    }
    
    public long registraProductoCotizadoDAO(String pCodProd, 
                                            String pCodLocal, 
                                            String pIdUsu, 
                                            String pCodTipoCotizacion,
                                            String pValFrac,
                                            String pCantidad,
                                            String pPrecUnitario,
                                            String pCodCompetidor,
                                            String pCodDocSustento,
                                            String pNumDoc,
                                            String pFechaDoc,
                                            String pImagen,
                                            String pCondicion,
                                            String pNumNota,
                                            String pMotivoNoImagen)
    {
        Connection conn= cnxUtil.getConexionGestionPrecio();
        long secuencialSet = 0;
        double totalEntero = 0;
        if(pCondicion.equals("COTIZAR")){
            totalEntero = FarmaUtility.getDecimalNumber(pPrecUnitario);
        }else{
            totalEntero = FarmaUtility.getDecimalNumber(pValFrac)*FarmaUtility.getDecimalNumber(pPrecUnitario);
        }
        
        try
        {
            CallableStatement stmt = conn.prepareCall("{ call ADMCENTRAL_GP_COTIZACION.PTOVENTA_AGREGA_PROD_COTIZADO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            stmt.setString(1, pCodProd);
            stmt.setString(2, pCodLocal);
            stmt.setString(3, pIdUsu);
            stmt.setString(4, pCodTipoCotizacion);
            stmt.setInt(5, Integer.valueOf(pCantidad));
            stmt.setDouble(6, Double.valueOf(pPrecUnitario));
            stmt.setDouble(7, FarmaUtility.getDecimalNumberRedondeado(totalEntero));
            stmt.setString(8, pCodCompetidor);
            stmt.setString(9, pCodDocSustento);
            stmt.setString(10, pNumDoc);
            stmt.setString(11, pFechaDoc);
            stmt.setString(12, pImagen);
            stmt.setString(13, pCondicion);
            stmt.setString(14, pNumNota);
            stmt.setString(15, pMotivoNoImagen);
            stmt.registerOutParameter(16, OracleTypes.NUMBER);
            stmt.execute();
            secuencialSet = stmt.getLong(16);
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
               DBPrecioCompetencia.registrarPrecioCompetencia(pCodProd, pCodLocal, pIdUsu, 
                                                              pCodTipoCotizacion, pValFrac, pCantidad, 
                                                              pPrecUnitario, pCodCompetidor, pCodDocSustento, 
                                                              pNumDoc, pFechaDoc, pImagen, pCondicion,pNumNota,pMotivoNoImagen);
               FarmaUtility.aceptarTransaccion(); 
                
            } catch (Exception sqle) {
                 log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException registraProductoCotizadoDAO  <> "+e.getMessage());
            VariablesPrecioCompetencia.vImagen = "" ; 
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception registraProductoCotizadoDAO <> "+eq.getMessage());
            VariablesPrecioCompetencia.vImagen = "" ; 
        }
        return secuencialSet;
    }    

    public static void grabarCompraPrecioCompetenciaDAO(JTable pTblResumenPedido, String pNumNota)
    {                        
         try
         {  UtilityPrecioCompetencia utilityPrecioCompetencia = new UtilityPrecioCompetencia();
             if(VariablesInventario.vFileImagen != null){
                VariablesInventario.vImagen = FarmaVariables.vCodLocal+"-"+VariablesInventario.vRucCompetencia+"-"+VariablesInventario.vNumDocCotizado+"."+FilenameUtils.getExtension(VariablesInventario.vFileImagen.getName());
             }
            for(int i=0;i<pTblResumenPedido.getRowCount();i++)
            { long secuencial = utilityPrecioCompetencia.registraProductoCotizadoDAO(pTblResumenPedido.getValueAt(i,0).toString(), 
                                                                         FarmaVariables.vCodLocal, 
                                                                         FarmaVariables.vIdUsu, 
                                                                         ConstantsPrecioCompetencia.TIPO_COTIZACION_CONPRECIO, 
                                                                         pTblResumenPedido.getValueAt(i,9).toString(), 
                                                                         pTblResumenPedido.getValueAt(i,4).toString(), 
                                                                         pTblResumenPedido.getValueAt(i,5).toString(), 
                                                                         VariablesInventario.vRucCompetencia, 
                                                                         VariablesInventario.vTipoDocCotizado, 
                                                                         VariablesInventario.vNumDocCotizado, 
                                                                         VariablesInventario.vFecGuiaCotizado, 
                                                                         VariablesInventario.vImagen, 
                                                                         ConstantsPrecioCompetencia.CONDICION_COMPRAR,
                                                                         pNumNota,
                                                                         VariablesInventario.vMotivoNoImagen);
            }            
            File archivo = VariablesInventario.vFileImagen;
            if(archivo!=null){
                File archivoNuevo = new File(VariablesInventario.vImagen);
                try {
                    FileUtils.copyFile(archivo,archivoNuevo);
                    boolean b = MultipartFileUploadApp.upload(archivoNuevo);//Envia a upload PHP
                    if(!b){
                        b = MultipartFileUploadApp.upload(archivoNuevo);//Reenvia a upload PHP
                        if(!b){
                            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                    FarmaVariables.vCodLocal,
                                    ConstantsPrecioCompetencia.CORREO_NOTIFICACION_TI,
                                    "Error en envio imagen a GPrecios ",
                                    "Error en envio imagen a GPrecios - Ingreso Competencia",
                                    "Error en envio de imagen a GPrecios desde "+ ConstantsPrecioCompetencia.URL_UPLOAD_PHP + " <br>"+ 
                                    "Archivo: "+ archivoNuevo.getName() + "<br>"+ 
                                    "IP PC: " + FarmaVariables.vIpPc + "<br>",
                                    "");   
                           File archivoLocal = new File("\\\\" + FarmaVariables.vIPBD+ConstantsPrecioCompetencia.RUTA_IMAGEN_SUSTENTO+VariablesInventario.vImagen);
                           FileUtils.moveFile(archivoNuevo, archivoLocal);
                        }
                    }
                } catch (Exception e) {
                    log.error("",e);
                    FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                              FarmaVariables.vCodLocal,
                              ConstantsPrecioCompetencia.CORREO_NOTIFICACION_TI,
                              "Error en envio imagen a GPrecios ",
                              "Error en envio imagen a GPrecios - Ingreso Competencia",
                              "Error en envio de imagen a GPrecios desde "+ ConstantsPrecioCompetencia.URL_UPLOAD_PHP + " <br>"+ 
                              "Archivo: "+ archivoNuevo.getName() + "<br>"+ 
                              "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                              "Error: " + ""+e.getMessage() ,
                              "");
                    File archivoLocal = new File("\\\\" + FarmaVariables.vIPBD+ConstantsPrecioCompetencia.RUTA_IMAGEN_SUSTENTO+VariablesInventario.vImagen);
                    FileUtils.moveFile(archivoNuevo, archivoLocal);
                }
                archivoNuevo.delete();
                VariablesInventario.vFileImagen = null;
                VariablesInventario.vImagen = "";
            }             
        }catch(Exception sql){log.error(sql.getStackTrace()[0].toString());
           log.error("ERROR Exception grabarCompraPrecioCompetenciaDAO <> ",sql.getMessage());
        }
    }    

    public static void anularCompraPrecioCompetenciaDAO(String pNumNota)
    {   FarmaVentaCnxUtility cnxUtil = new FarmaVentaCnxUtility();                     
        Connection conn= cnxUtil.getConexionGestionPrecio();
        try
        {
            CallableStatement stmt = conn.prepareCall("{ call ADMCENTRAL_GP_COTIZACION.PTOVENTA_ANULA_PRECIO_COMPET(?)}");
            stmt.setString(1, pNumNota);
            stmt.execute();
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException anularCompraPrecioCompetenciaDAO  <> "+e.getMessage());
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception anularCompraPrecioCompetenciaDAO <> "+eq.getMessage());
        }
    }    
    
    public static String existeProductosPorCotizar(JTable pTblResumenPedido){
        FarmaVentaCnxUtility cnxUtil = new FarmaVentaCnxUtility();
        Connection conn= cnxUtil.getConexionGestionPrecio();
        ArrayList<String> productosAsignados = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        
        try
        {
            CallableStatement stmt = conn.prepareCall("{ ? = call ADMCENTRAL_GP_COTIZACION.PTOVENTA_LISTA_PRODS_X_COTIZAR(?)}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, FarmaVariables.vCodLocal);
            stmt.execute();
            ResultSet results = (ResultSet)stmt.getObject(1);              
            while(results.next())
            {
                productosAsignados.add(results.getString(1));
            }              
            results.close();
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
            
            if(productosAsignados.size() > 0){
                for(int i=0;i<pTblResumenPedido.getRowCount();i++){
                    for (String codProd: productosAsignados){
                        if(pTblResumenPedido.getValueAt(i,0).toString().equals(codProd)){
                            sb.append(",").append(codProd);
                        }
                    }
                }
                if(sb.length() > 0){
                    sb.deleteCharAt(0);
                }
            }
            
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException existeProductosPorCotizar  <> "+e.getMessage());
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception existeProductosPorCotizar <> "+eq.getMessage());
        }finally{
            return sb.toString();
            }    
    }    
    
    /**
     * Se muestra el dialogo para seleccionar el archivo.
     */
    public static File cargarArchivo(Frame parent) {
        File lfFile = new File("C:\\");
        JFileChooser filechooser = new JFileChooser(lfFile);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes JPG, JPEG, GIF, BMP y PNG", "jpeg", "jpg","gif","bmp","png");
        filechooser.setFileFilter(filter);
        filechooser.setDialogTitle("Seleccione imagen");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (filechooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION)
            return null;
        File fileChoosen = filechooser.getSelectedFile();
        if(fileChoosen.length() <= 512000){//500KB
            VariablesPrecioCompetencia.vFileImagen = fileChoosen;
            String ruta = fileChoosen.getAbsolutePath();
        }    
        return fileChoosen;
    }    
   
    public static String RPad(String str, Integer length, char car) {
      return str
             + 
             String.format("%" + (length - str.length()) + "s", "")
                         .replace(" ", String.valueOf(car));
    }

    public static String LPad(String str, Integer length, char car) {
      return String.format("%" + (length - str.length()) + "s", "")
                   .replace(" ", String.valueOf(car)) 
             +
             str;
    } 
    
    public static List formatoImpresionListaProductosPorCotizarDAO()
    {
        FarmaVentaCnxUtility cnxUtil = new FarmaVentaCnxUtility();
        Connection conn= cnxUtil.getConexionGestionPrecio();
        List listaResultado = new  ArrayList();
        try
        {
            CallableStatement stmt = conn.prepareCall("{ ? = call ADMCENTRAL_GP_COTIZACION.PTOVENTA_IMPR_PRODS_X_COTIZAR(?)}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, FarmaVariables.vCodLocal);
            stmt.execute();
            ResultSet results = (ResultSet)stmt.getObject(1);              
            
            //obteniendo el metadato del resulset
            ResultSetMetaData metaDatos = results.getMetaData();
            // Se obtiene el número de columnas.
            int numeroColumnas = metaDatos.getColumnCount();
            // Se crea un array de etiquetas para rellenar
            Object[] etiquetas = new Object[numeroColumnas];
            // Se obtiene cada una de las etiquetas para cada columna
            for (int i = 0; i < numeroColumnas; i++)
            {
               // Nuevamente, para ResultSetMetaData la primera columna es la 1.
               etiquetas[i] = metaDatos.getColumnLabel(i + 1);
            }
            
            Map mapaFila;       
            while (results.next()) {
                    mapaFila = new HashMap();
                    for(int i=0 ; i < numeroColumnas; i++){
                            mapaFila.put(etiquetas[i], results.getString(etiquetas[i].toString()));
                    }
                    listaResultado.add(mapaFila);
            }
                       
            results.close();
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException formatoImpresionListaProductosPorCotizarDAO  <> "+e.getMessage());
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception formatoImpresionListaProductosPorCotizarDAO <> "+eq.getMessage());
        }
        
        return listaResultado;
    }    
    
    public static boolean cumpleMargenMinimoCotizacion(double pPrecioCotizacion,String vCodProd){
        FarmaVentaCnxUtility cnxUtil = new FarmaVentaCnxUtility();
        Connection conn= cnxUtil.getConexionGestionPrecio();
        boolean cumple = false;
        
        try
        {
            CallableStatement stmt = conn.prepareCall("{ ? = call ADMCENTRAL_GP_COTIZACION.COT_VALIDA_MARGEN_MINIMO_ZONA(?,?,?,?)}");
            stmt.registerOutParameter(1, OracleTypes.NUMBER);
            stmt.setInt(2, -1);
            stmt.setDouble(3, pPrecioCotizacion);
            stmt.setString(4, vCodProd);
            stmt.setString(5, FarmaVariables.vCodLocal);
            stmt.execute();
            Integer results = stmt.getInt(1);              
            if (results==1)
                cumple = true;
            stmt.close();
            if(!conn.isClosed())
                conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;                        
            log.info("ERROR SQLException cumpleMargenMinimoCotizacion  <> "+e.getMessage());
        }
        catch (Exception eq)
        {
            try {
                if (!conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.info("",sqle);
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            conn = null;            
            log.info("ERROR Exception cumpleMargenMinimoCotizacion <> "+eq.getMessage());
        }finally{
            return cumple;
            }    
    }    

}
