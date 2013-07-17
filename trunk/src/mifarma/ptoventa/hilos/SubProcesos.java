package mifarma.ptoventa.hilos;

import java.sql.SQLException;

import java.util.Collections;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableComparator;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

public class SubProcesos extends Thread {
   private int tiempoInactividad;
   String pNombre = "";
   boolean indTerminoProceso = false;
   // asignar nombre a subproceso, llamando al constructor de la superclase
   public SubProcesos( String nombre )
   {
       pNombre = nombre;
   }        
    
   // el método run es el código a ejecutar por el nuevo subproceso
   public void run()
   {
       
     
     try {
          if(indTerminoProceso){
              Thread.sleep(0);
          }
         
          operaproceso(pNombre);
            
      } 
      // si el subproceso se interrumpió durante su inactividad, imprimir rastreo de la pila
      catch ( InterruptedException excepcion ) {
         excepcion.printStackTrace();
      }
        
      // imprimir nombre del subproceso
      System.err.println( getName() + " termino su inactividad" );
    
   } 
   
   public void operaproceso(String pNombre_2){
       /*
       SubProcesos subproceso1 = new SubProcesos("GET_PROD_VENTA" );
       SubProcesos subproceso2 = new SubProcesos("GET_PROD_ESPECIALES" );
       */
       try {
           
           if(pNombre_2.trim().toUpperCase().equalsIgnoreCase("GET_PROD_VENTA"))
           {
               System.err.println("inicio lista productos ventas");
               DBVentas.cargaListaProductosVenta(VariablesVentas.tableModelListaGlobalProductos);    
               Collections.sort(VariablesVentas.tableModelListaGlobalProductos.data,new FarmaTableComparator(2,true));
               System.err.println("fin lista productos ventas");
               indTerminoProceso = true;
           }
           else{
               if(pNombre_2.trim().toUpperCase().equalsIgnoreCase("GET_PROD_ESPECIALES"))
               {
                   System.err.println("inicio lista productos especiales");
                   DBInventario.cargaListaProductosEspeciales(VariablesInventario.tableModelEspecial);
                   Collections.sort(VariablesInventario.tableModelEspecial.data,new FarmaTableComparator(2,true));
                   System.err.println("fin lista productos especiales");
                   indTerminoProceso = true;
               }
               else{
                   //CARGA_IMP_TERMICA
                   if(pNombre_2.trim().toUpperCase().equalsIgnoreCase("CARGA_IMP_TERMICA")){
                       String vIndImpre = DBCaja.obtieneIndImpresion(); 
                       if(!FarmaVariables.vEconoFar_Matriz)  {
                           // lapaz dubilluz 17.09.2010
                           if (vIndImpre.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                               UtilityVentas.carga_impresoras(null);
                               cargaIndImpresionRojoTicket();
                           }
                           else{
                               System.out.println("NO TIENE TERMICA ACTIVA EN PBL_LOCAL");
                           }
                       }                       
                   }
               }
           }           
       }
       catch (SQLException e) {
           e.printStackTrace();
           indTerminoProceso = true;
       }
       finally {
           indTerminoProceso = true;
       }
       
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
    }
} 