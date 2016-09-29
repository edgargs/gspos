package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import java.util.ArrayList;
import javax.swing.JDialog;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.controlAsistencia.reference.DBControlAsistencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgImprimeHorarioProgress.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgImprimeHorarioProgress extends JDialogProgress{
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgImprimeHorarioProgress.class);
    private String pCodHorario;
    private String pFecInicio;
    private String pFecFin;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public DlgImprimeHorarioProgress(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;   
    }

    /* ********************************************************************** */
    /*                   Métodos de lógica de negocio                         */
    /* ********************************************************************** */
    
    public void imprimeReporte(String codHorario,String  cFecInicio,String cFecFin){
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);
        if (!vPrint.startPrintService() ){
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión",new JDialog());
            return;
        }else{
            try{
                vPrint.activateCondensed();
               
                vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(25) + "REPORTE: HORARIO SEMANAL", true);
                vPrint.printLine(" ", true);
                vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8)+"SEMANA :"+cFecInicio +" al "+ cFecFin,true);
                ArrayList lstPlantilla=new ArrayList();
                lstPlantilla = DBControlAsistencia.lstImprimeHorariohorariolocal(codHorario);
    
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(0)+"_______________________________________________________"+
                                 "________________________________________________________________________________",true);
                vPrint.printDoubleWidthMode("PERFIL"+FarmaPRNUtility.llenarBlancos(1)+"COLABORADOR"+FarmaPRNUtility.llenarBlancos(1)+ 
                                            "LUN"+FarmaPRNUtility.llenarBlancos(4)+"MAR"+FarmaPRNUtility.llenarBlancos(4)+
                                            "MIE"+FarmaPRNUtility.llenarBlancos(4)+"JUE"+FarmaPRNUtility.llenarBlancos(4)+
                                            "VIE"+FarmaPRNUtility.llenarBlancos(4)+"SAB"+FarmaPRNUtility.llenarBlancos(4)+"DOM", true);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(0)+"________________________________________________________"+
                                 "_______________________________________________________________________________",true);
                
               for(int i=0; i<lstPlantilla.size();i++){
                String perfil=((ArrayList)lstPlantilla.get(i)).get(0).toString();
                String nomUsuario=((ArrayList)lstPlantilla.get(i)).get(1).toString(); 
                String lunes=((ArrayList)lstPlantilla.get(i)).get(2).toString(); 
                String martes=((ArrayList)lstPlantilla.get(i)).get(3).toString();
                String miercoles=((ArrayList)lstPlantilla.get(i)).get(4).toString();
                String jueves=((ArrayList)lstPlantilla.get(i)).get(5).toString();
                String viernes=((ArrayList)lstPlantilla.get(i)).get(6).toString();
                String sabado=((ArrayList)lstPlantilla.get(i)).get(7).toString();
                String domingo=((ArrayList)lstPlantilla.get(i)).get(8).toString();
                vPrint.printLine(perfil+FarmaPRNUtility.llenarBlancos(4)+nomUsuario+
                                           FarmaPRNUtility.llenarBlancos(2)+lunes+
                                           FarmaPRNUtility.llenarBlancos(1)+martes+
                                           FarmaPRNUtility.llenarBlancos(1)+miercoles+
                                           FarmaPRNUtility.llenarBlancos(1)+jueves+
                                           FarmaPRNUtility.llenarBlancos(1)+viernes+
                                           FarmaPRNUtility.llenarBlancos(1)+sabado+
                                           FarmaPRNUtility.llenarBlancos(1)+domingo, true);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(0)+"----------------------------------------------------------"+
                                 "------------------------------------------------------------------------------",true);
                }
              
                vPrint.deactivateCondensed();
                vPrint.endPrintService();
                Thread.sleep(15000);
                FarmaUtility.showMessage(this, "Horario impreso con exito!",new JDialog()); 
            }catch(Exception e){
                log.error("", e);
                FarmaUtility.showMessage(this, "Error al generar la Impresión",new JDialog()); 
            }
         }
    }

    @Override
    public void ejecutaProceso() {
        imprimeReporte(pCodHorario,pFecInicio,pFecFin);
    }
    
    public void setImprimeReporte(String pCodHorario, String pFecInicio, String pFecFin){
        this.pCodHorario = pCodHorario; 
            this.pFecInicio  = pFecInicio;
                this.pFecFin = pFecFin;
        
    }
}
