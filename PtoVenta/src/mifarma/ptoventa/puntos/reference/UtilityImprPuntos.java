package mifarma.ptoventa.puntos.reference;

import farmapuntos.bean.TarjetaBean;

import farmapuntos.principal.*;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.imptermica.UtilPrinterPtoventa;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import printerUtil.FrmServicePrinter;

public class UtilityImprPuntos {

    private static final Logger log = LoggerFactory.getLogger(UtilityImprPuntos.class);

    
    public UtilityImprPuntos() {
    }
    
    /**
     * Impresion de Saldo de Puntos
     * @param pDialog
     * @param pObjFoco
     * @param numTarjEnmascarada
     * @param nombreCompleto
     * @param puntosAcumulados
     */
    public static void imprimeSaldo(
                                    JDialog pDialog,
                                    Object  pObjFoco,
                                    String numTarjEnmascarada,
                                    String nombreCompleto,
                                    double puntosAcumulados)
    {
       try
       {
          log.info("Instancia la clase Impresora UtilPrinterPtoventa");
          boolean vExistoImpresora  = false;
          UtilPrinterPtoventa vPrintTermica =  null;
          try {
                vPrintTermica = new UtilPrinterPtoventa();
                vExistoImpresora  = true;
            } catch (Exception e) {
                vExistoImpresora  = false;
                // TODO: Add catch code
                e.printStackTrace();
            }
           if(vExistoImpresora){ 
          log.info("Finaliza de crear Impresora UtilPrinterPtoventa ");
          if(puntosAcumulados<0)
          puntosAcumulados = 0.00;
          List vListaDatos = DBPuntos.getDatosImprSaldo(numTarjEnmascarada, nombreCompleto,puntosAcumulados);
          log.info("Obtuvo los datos de IMPRESION "+vListaDatos); 
          vPrintTermica.printList(vListaDatos,false);
          log.info("Finaliza Impresion"); 
           }
           else{
               FarmaUtility.showMessage(pDialog, "No esta asignada o instalada la impresora."+"\n"+
                                                 "Por favor de Comunicarse con Mesa de Ayuda.", pObjFoco);
               
               throw new Exception("No esta asignada o instalada la impresora");
           }
           
       } catch (Exception e) {
            e.printStackTrace();
            log.info("ERROR - imprimeSaldo - "+e.getMessage());
            FarmaUtility.showMessage(pDialog, "Error en la Impresión de Saldo"+"\n"+
                                              "*****************************************"+"\n"+
                                              e.getMessage()+"\n"+
                                              "*****************************************"+"\n"+                                              
                                              "Por favor de Comunicarse con Mesa de Ayuda.", pObjFoco);
            log.info(e.getMessage());
        }
    }


    public static void imprimeRecuperaPuntos(
                                    JDialog pDialog,
                                    Object  pObjFoco,
                                    String numTarjEnmascarada,
                                    String nombreCompleto,
                                    String pDNI,
                                    double puntosAcumulados,
                                    boolean pIndOnline,
                                    double pPtoSaldoAct,
                                    String pNumPedVta)
    {
       try
       {
          log.info("Instancia la clase Impresora UtilPrinterPtoventa");
          UtilPrinterPtoventa vPrintTermica =  new UtilPrinterPtoventa();
          log.info("Finaliza de crear Impresora UtilPrinterPtoventa ");
          List vListaDatos = DBPuntos.getDatosImprRecuperaPuntos(numTarjEnmascarada, nombreCompleto,pDNI,puntosAcumulados,pIndOnline,pPtoSaldoAct,pNumPedVta);
          log.info("Obtuvo los datos de IMPRESION "+vListaDatos); 
          vPrintTermica.printList(vListaDatos,false);
          log.info("Finaliza Impresion"); 
       } catch (Exception e) {
            e.printStackTrace();
            log.info("ERROR - imprimeRecuperaPuntos - ");
            FarmaUtility.showMessage(pDialog, "Error en la Impresión de Recuperación de Puntos"+"\n"+
                                              "*****************************************"+"\n"+
                                              e.getMessage()+"\n"+
                                              "*****************************************"+"\n"+                                              
                                              "Por favor de Comunicarse con Mesa de Ayuda.", pObjFoco);
            log.info(e.getMessage());
        }
    }
    
}
