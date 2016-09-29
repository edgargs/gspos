package mifarma.ptoventa.controlAsistencia.reference;

import com.gs.mifarma.componentes.JConfirmDialog;

import java.awt.Frame;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JDialog;

import javax.swing.JFrame;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.controlAsistencia.DlgControlAsistencia;
import mifarma.ptoventa.controlAsistencia.DlgMantenimientoMarcacion;
import mifarma.ptoventa.controlAsistencia.util.UtilFtp;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;

import object.ObjRowTime;

import org.com.du.JTimeTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : UtilityControlAsistencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA   15.10.2015    Modificacion<br>
 * ASOSA      21.10.2015    Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class UtilityControlAsistencia {
    private static final Logger log = LoggerFactory.getLogger(UtilityControlAsistencia.class);

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    public UtilityControlAsistencia() {
    }

    public static double getMaxHorasSemana() {
        double vMaxHoraSemanal_MFA = 0;
        try {
            vMaxHoraSemanal_MFA = DBControlAsistencia.maxCantHorasSemanaGestionPlantilla();
        } catch (Exception sqle) {
            log.error("",sqle);
        }
        
        log.info("vMaxHoraSemanal_MFA "+vMaxHoraSemanal_MFA);
        return vMaxHoraSemanal_MFA;
    }
    
    public static void lstCabeceraHorario(FarmaTableModel lstGrilla){
        try {
            DBControlAsistencia.lstHorariohorariolocal(lstGrilla);
        }catch(Exception e){
            log.error("",e);
        }
        
    }

    public static void getFechasNuevoHorario(ArrayList lstFechas){
        try {        
            DBControlAsistencia.getRangoNuevoHorarioLocal(lstFechas);
        }catch(Exception e){
            log.error("",e);
        }        
    }
    
    public static void listarSeleccionRoles(ArrayList lstRoles){
        try {
            DBControlAsistencia.getListaRoleshorariolocal(lstRoles);
        }catch(Exception e){
            log.error("",e);
        }              
    }
    
    public static void listarSeleccionUsuarios(ArrayList lstUsuRol ){
        try { 
            DBControlAsistencia.listUsuariosRolHorarios(lstUsuRol);
        }catch(Exception e){
            log.error("",e);
        }  
    }
    
    public static void lstDataModifyHorario(String codHorario,ArrayList lstDataModify){
        try { 
            DBControlAsistencia.getLoadDataModifyhorariolocal(lstDataModify, codHorario);
        }catch(Exception e){
            log.error("",e);
        }  
    }
    
    public static void lstDataDefaultHorario(String codPlantilla,ArrayList lstDataModify){
        try { 
            DBControlAsistencia.getLoadDataDefaulthorariolocal(lstDataModify, codPlantilla);
        }catch(Exception e){
            log.error("",e);
        }  
    }
    
    public static boolean isEditableHorario(String codHorario) {
        String resultado = "";
        boolean flag = false;
        try {
            resultado = DBControlAsistencia.isEditableHorario(codHorario).trim();
        } catch (SQLException e) {
            log.error("",e);
            flag = true;
        }
        if (resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            flag = true;
        }
        else {
            flag = false;
        }
        
        return flag;
    }

    public static void enviaHorarioJefes(String pCodHorario){
        try { 
            DBControlAsistencia.enviaEmailJefeshorariolocal(pCodHorario);
        }catch(Exception e){
            log.error("",e);
        }  
    }
    public static boolean isUsuaTieneSolitudHorario(String pSecUsuLocal, String pFechaInicio, String pFechaFinal,
                                             String pCodDia1, String pCodDia2, String pCodDia3, String pCodDia4,
                                             String pCodDia5, String pCodDia6, String pCodDia7) {
        String resultado = "";
        boolean flag = true;
        try {
            resultado =
                    DBControlAsistencia.isUsuaTieneSolitudhorariolocal(pSecUsuLocal, pFechaInicio, pFechaFinal, pCodDia1,
                                                                       pCodDia2, pCodDia3, pCodDia4, pCodDia5,
                                                                       pCodDia6, pCodDia7);
        } catch (Exception e) {
            log.error("",e);
            FarmaUtility.showMessage(new JDialog(), "No puede grabar el horario.\n"+
                                           e.getMessage().substring(10, e.getMessage().indexOf("ORA-06512")),
                                     null);            
            
            flag = false;
        }
        return flag;
    }


    public static void listarPlantillas(FarmaTableModel lstGrilla){
        try{
            DBControlAsistencia.listarPlantillasLocal(lstGrilla);
        }catch(Exception e){
            log.error("",e);
        }  
    }

    public static void listaPlantillaDefecto(ArrayList lstPlantillaModel ){
        try{
            DBControlAsistencia.listaPlantillaDefecto(lstPlantillaModel);
        }catch(Exception e){
            log.error("",e);
        }  
    }

    public static void listarDetallePlantilla(String codPlantilla, ArrayList lstPlantillaModel){
        try{
            DBControlAsistencia.listarPlantillaDetalle(lstPlantillaModel, codPlantilla);
        }catch(Exception e){
            log.error("",e);
        }  
    }

    public static void listarSeleccionRangoHoras(ArrayList lstRangoHoras){
        try{
            DBControlAsistencia.listarRangoHorasPlantilla(lstRangoHoras);
        }catch(Exception e){
            log.error("",e);
        }  
    }
    
    public static void listarSeleccionRangoHorasRefrigerio(ArrayList lstRangoHoras) throws SQLException {
        DBControlAsistencia.listarRangoHorasRefrigerio(lstRangoHoras);
    }    

    public static String grabarNuevaPlantilla(String desCorta,
                                              ArrayList<ObjRowTime> vDetalle) throws Exception {
        // 01 Paso
        String pCodPlantilla = DBControlAsistencia.grabaPlantCabhorariolocal(desCorta).trim();
        // 02 Paso
        ArrayList<ObjRowTime> vListaFilas = vDetalle;
        for (int i = 0; i < vListaFilas.size(); i++) {
            ObjRowTime vFila = vListaFilas.get(i);
            String idFila = vFila.getIdRowTime();
            String codRol = vFila.getVRol().getPCodRol();
            String codDiaRefrigerio = vFila.getVDiaRefrigerio().getPCodhora();
            String codDia1 = vFila.getVDiaD1().getPCodhora();
            String codDia2 = vFila.getVDiaD2().getPCodhora();
            String codDia3 = vFila.getVDiaD3().getPCodhora();
            String codDia4 = vFila.getVDiaD4().getPCodhora();
            String codDia5 = vFila.getVDiaD5().getPCodhora();
            String codDia6 = vFila.getVDiaD6().getPCodhora();
            String codDia7 = vFila.getVDiaD7().getPCodhora();
            double cantHoras  = Double.parseDouble(vFila.getHoraTrabajada().substring(0, vFila.getHoraTrabajada().length()-2));
            String pIndUltimaFila = "N";
            if(i+1>=vListaFilas.size())
                pIndUltimaFila = "S";
            DBControlAsistencia.grabaPlantillaDethorariolocal(pCodPlantilla, idFila, codRol,codDiaRefrigerio, codDia1, codDia2, codDia3,
                                                              codDia4, codDia5,codDia6, codDia7,pIndUltimaFila, cantHoras);
        }
        return pCodPlantilla;
    }
    
    public static String modificarPlantilla(String codPlantilla,String desCorta,
                                                  ArrayList<ObjRowTime> vDetalle) throws Exception {
        // 01 Paso
        String pCodPlantilla = DBControlAsistencia.modificarPlantCabhorariolocal(codPlantilla,desCorta).trim();
        // 02 Paso
        ArrayList<ObjRowTime> vListaFilas = vDetalle;
        for (int i = 0; i < vListaFilas.size(); i++) {
            ObjRowTime vFila = vListaFilas.get(i);
            String idFila = vFila.getIdRowTime();
            String codRol = vFila.getVRol().getPCodRol();
            String codDiaRefrigerio = vFila.getVDiaRefrigerio().getPCodhora();
            String codDia1 = vFila.getVDiaD1().getPCodhora();
            String codDia2 = vFila.getVDiaD2().getPCodhora();
            String codDia3 = vFila.getVDiaD3().getPCodhora();
            String codDia4 = vFila.getVDiaD4().getPCodhora();
            String codDia5 = vFila.getVDiaD5().getPCodhora();
            String codDia6 = vFila.getVDiaD6().getPCodhora();
            String codDia7 = vFila.getVDiaD7().getPCodhora();
            String pIndUltimaFila = "N";
            double cantHoras  = Double.parseDouble(vFila.getHoraTrabajada().substring(0, vFila.getHoraTrabajada().length()-2));
            if(i+1>=vListaFilas.size())
                pIndUltimaFila = "S";
            DBControlAsistencia.modificarPlantillaDethorariolocal(pCodPlantilla, idFila, codRol,codDiaRefrigerio, codDia1, codDia2, codDia3,
                                                              codDia4, codDia5,codDia6, codDia7,pIndUltimaFila, cantHoras);
        }
        return pCodPlantilla;
    }

    
    public ArrayList lstTurnoLocalTurno() {
        ArrayList lstMaestroTurno = new ArrayList();
        try {
            lstMaestroTurno = DBControlAsistencia.lstMaestroTurnoGestionTurno();
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(new JDialog(), "Error al cargar el maestro de turnos  \n " + e.getMessage(),
                                     null);
        }
        return lstMaestroTurno;
    }
    public ArrayList lstTurnoTurno() {
        ArrayList lstMaestroTurno = new ArrayList();
        try {
            lstMaestroTurno = DBControlAsistencia.lstMaestroTurnoLocalGestionTurno();
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(new JDialog(), "Error al cargar el maestro de turnos local \n " + e.getMessage(),
                                     null);
        }
        return lstMaestroTurno;
    }
    public boolean isTurnoGeneroPlantillaTurno(String idTurno, String descHorario) {
        boolean flag = true;
        String resultado = "";

        try {
            resultado = DBControlAsistencia.isTurnoGeneroPlantillaGestionTurno(idTurno).trim();
            if (resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) { //ES TRUE CUANDO YA GENERO PLANTILLA
                FarmaUtility.showMessage(new JDialog(),
                                         "No se puede eliminar el turno " + descHorario + " ya que este  genero una plantilla",
                                         null);
                flag = false;
            }
        } catch (SQLException e) {
            log.error("",e);
            FarmaUtility.showMessage(new JDialog(), "Error al validar turno con la tabla plantilla!", null);
            flag = false;
        }
        return flag;
    }

    public boolean isTurnoGeneroHorarioTurno(String idTurno, String descHorario) {
        boolean flag = true;
        String resultado = "";

        try {
            resultado = DBControlAsistencia.isTurnoGeneroHorarioGestionTurno(idTurno).trim();
            if (resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) { //ES TRUE CUANDO YA GENERO PLANTILLA
                FarmaUtility.showMessage(new JDialog(),
                                         "No se puede eliminar el turno " + descHorario + " ya que este  genero un horario",
                                         null);
                flag = false;
            }
        } catch (SQLException e) {
            log.error("",e);
            FarmaUtility.showMessage(new JDialog(), "Error al validar turno con la tabla horario!", null);
            flag = false;
        }
        return flag;
    }

    public boolean isDesasignarTurnoTurno(String idTurno) {
        boolean flag = true;
        String resultado = "";
        try {
            resultado = DBControlAsistencia.isDesasignarTurnoGestionTurno(idTurno).trim();
            if (resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(new JDialog(), "Se eliminó el turno seleccionado.", null);
            } else {
                flag = false;
                FarmaUtility.showMessage(new JDialog(), "No se puede eliminar el turno del local.!", null);
            }
        } catch (SQLException e) {
            log.error("",e);
            flag = false;
            FarmaUtility.showMessage(new JDialog(), "Error al eliminar el turno.!", null);
        }
        return flag;
    }

    public static String asignarTurnoLocalTurno(String codigo){
      String resultado="";

        try {
            resultado=DBControlAsistencia.asignarTurnoLocalGestionTurno(codigo).trim();

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(new JDialog(), "Error al asignar turno!", null);
        }
        return resultado;
    }

    public static String getNomColumnaValidaHorario(int indexCol) {
        String nomCol = "";
        switch (indexCol) {
        case 0:
            nomCol = "LUNES";
            break;
        case 1:
            nomCol = "MARTES";
            break;
        case 2:
            nomCol = "MIERCOLES";
            break;
        case 3:
            nomCol = "JUEVES";
            break;
        case 4:
            nomCol = "VIERNES";
            break;
        case 5:
            nomCol = "SABADO";
            break;
        case 6:
            nomCol = "DOMINGO";
            break;
        }
        return nomCol;
    }


    public static boolean isValidaListaRowTime(ArrayList<ObjRowTime> vListaFila) {
        boolean vRes = true;
        for (int i = 0; i < vListaFila.size(); i++) {
            ObjRowTime vFila = vListaFila.get(i);
            if (!isValidaRowTime(vFila)) {
                FarmaUtility.showMessage(new JDialog(), "Debe tener por lo menos 1 dia de descanso por perfil.", null);
                vRes = false;
                break;
            }
        }
        return vRes;
    }

    public static boolean isValidaRowTime(ObjRowTime vFila) {
        boolean vRes = true;
        int vFilaCompleta =
            vExisteValorDiaValidaHorario(vFila.getVDiaD1().getPCodhora()) + vExisteValorDiaValidaHorario(vFila.getVDiaD2().getPCodhora()) +
            vExisteValorDiaValidaHorario(vFila.getVDiaD3().getPCodhora()) +
            vExisteValorDiaValidaHorario(vFila.getVDiaD4().getPCodhora()) +
            vExisteValorDiaValidaHorario(vFila.getVDiaD5().getPCodhora()) +
            vExisteValorDiaValidaHorario(vFila.getVDiaD6().getPCodhora()) +
            vExisteValorDiaValidaHorario(vFila.getVDiaD7().getPCodhora());

        if (vFilaCompleta == 7) { // DEBE TENER COMPLETO TODOS LOS DIAS DE LA SEMANA CON UN CODIGO DE HORARIO
            // PARA VALIDAR LO SIGUIENTE DE DESCANSO
            int vExisteVSC =
                vExisteVSCDiaValidaHorario(vFila.getVDiaD1().getPCodhora()) + vExisteVSCDiaValidaHorario(vFila.getVDiaD2().getPCodhora()) +
                vExisteVSCDiaValidaHorario(vFila.getVDiaD3().getPCodhora()) +
                vExisteVSCDiaValidaHorario(vFila.getVDiaD4().getPCodhora()) +
                vExisteVSCDiaValidaHorario(vFila.getVDiaD5().getPCodhora()) +
                vExisteVSCDiaValidaHorario(vFila.getVDiaD6().getPCodhora()) +
                vExisteVSCDiaValidaHorario(vFila.getVDiaD7().getPCodhora());

            if (vExisteVSC > 0) { // TIENE Si en una fila existe Vacaciones, Subsidios , Cesados
                //NO VALIDA DESCANSO ALGUNO Por defento return true
                vRes = true;
            } else {
                //se validara que exista como minimo 1 descanso.
                int vExisteDescanso =
                    vExisteDescansoDiaValidaHorario(vFila.getVDiaD1().getPCodhora()) + vExisteDescansoDiaValidaHorario(vFila.getVDiaD2().getPCodhora()) +
                    vExisteDescansoDiaValidaHorario(vFila.getVDiaD3().getPCodhora()) +
                    vExisteDescansoDiaValidaHorario(vFila.getVDiaD4().getPCodhora()) +
                    vExisteDescansoDiaValidaHorario(vFila.getVDiaD5().getPCodhora()) +
                    vExisteDescansoDiaValidaHorario(vFila.getVDiaD6().getPCodhora()) +
                    vExisteDescansoDiaValidaHorario(vFila.getVDiaD7().getPCodhora());

                if (vExisteDescanso == 0) {
                    // no existe descanso en ninguna de los dias de la fila enviada
                    vRes = false;
                } else {
                    vRes = true;
                }
            }
        }
        return vRes;
    }

    public static int vExisteVSCDiaValidaHorario(String pCodHoraDia) {
        if (pCodHoraDia.trim().equalsIgnoreCase(ConstantsControlAsistencia.COD_VACACIONES) ||
            pCodHoraDia.trim().equalsIgnoreCase(ConstantsControlAsistencia.COD_SUBSIDIO) ||
            pCodHoraDia.trim().equalsIgnoreCase(ConstantsControlAsistencia.COD_CESADO)) {
            return 1;
        } else
            return 0;
    }

    public static int vExisteValorDiaValidaHorario(String pCodHoraDia) {
        try {
            if (Integer.parseInt(pCodHoraDia.trim()) > 0)
                return 1;
            else
                return 0;
        } catch (Exception nfe) {
            log.error("",nfe);
            return 0;
        }
    }

    public static int vExisteDescansoDiaValidaHorario(String pCodHoraDia) {
        if (pCodHoraDia.trim().equalsIgnoreCase(ConstantsControlAsistencia.COD_DESCANSO)) {
            return 1;
        } else
            return 0;
    }

    public static boolean isValidaMaxHorasHorarioValidaHorario(double vMaxHS_MFA,double vMaxHS_MFALegal,
                                                               ArrayList<ObjRowTime> vListaFila) {

        double cantHoraUsu = 0;
        double cantHoraUsuAux = 0;
        String nomUsu = "",idFila="",vDesRol="";
        int cantVSCDia = 0;
        String codUsu = "";
        String pUsuario = "";
        int pCantidad = 0;
        boolean resultado = true;
        for (int i = 0; i < vListaFila.size(); i++) {
            ObjRowTime vFila = vListaFila.get(i);
            idFila = vFila.getIdRowTime();
            nomUsu = vFila.getVUsu().getPNombre();
            vDesRol =vFila.getVRol().getPDescRol();
            cantHoraUsu = Double.parseDouble(vFila.getHoraTrabajada().toString().trim().substring(0, 4)); //queda
            codUsu = vFila.getVUsu().getPCodUsu();
            cantVSCDia =
                    vExisteVSCDiaValidaHorario(vFila.getVDiaD1().getPCodhora().trim()) + vExisteVSCDiaValidaHorario(vFila.getVDiaD2().getPCodhora().trim()) +
                    vExisteVSCDiaValidaHorario(vFila.getVDiaD3().getPCodhora().trim()) +
                    vExisteVSCDiaValidaHorario(vFila.getVDiaD4().getPCodhora().trim()) +
                    vExisteVSCDiaValidaHorario(vFila.getVDiaD5().getPCodhora().trim()) +
                    vExisteVSCDiaValidaHorario(vFila.getVDiaD6().getPCodhora().trim()) +
                    vExisteVSCDiaValidaHorario(vFila.getVDiaD7().getPCodhora().trim());

            for (int j = 0; j < vListaFila.size(); j++) {
                ObjRowTime vFilaAux = vListaFila.get(j);
                String codUsuAux = vFilaAux.getVUsu().getPCodUsu();
                if (i != j) {
                    if (codUsu.equalsIgnoreCase(codUsuAux)) {
                        cantVSCDia =
                                vExisteVSCDiaValidaHorario(vFilaAux.getVDiaD1().getPCodhora().trim()) + vExisteVSCDiaValidaHorario(vFilaAux.getVDiaD2().getPCodhora().trim()) +
                                vExisteVSCDiaValidaHorario(vFilaAux.getVDiaD3().getPCodhora().trim()) +
                                vExisteVSCDiaValidaHorario(vFilaAux.getVDiaD4().getPCodhora().trim()) +
                                vExisteVSCDiaValidaHorario(vFilaAux.getVDiaD5().getPCodhora().trim()) +
                                vExisteVSCDiaValidaHorario(vFilaAux.getVDiaD6().getPCodhora().trim()) +
                                vExisteVSCDiaValidaHorario(vFilaAux.getVDiaD7().getPCodhora().trim());

                        cantHoraUsuAux =
                                Double.parseDouble(vFilaAux.getHoraTrabajada().toString().trim().substring(0, 4)); //queda
                        cantHoraUsu = cantHoraUsu + cantHoraUsuAux; //acumulamos las horas si se repite el usuario
                    }

                }

            }
            if (!isCantHrsValidas(cantHoraUsu, vMaxHS_MFA, codUsu)) {
                FarmaUtility.showMessage(new JDialog(),
                                         "Cantidad de horas no permitidas para el Usuario " + 
                                         nomUsu + 
                                         " N° Hrs " +
                                         cantHoraUsu, null);

                //return false;
                resultado = false;
            }else {
                if (cantHoraUsu < vMaxHS_MFALegal) {
                    pCantidad++;
                    String pDatosUsu="",pValidaCargo="";
                    try {
                        pValidaCargo = DBControlAsistencia.isValidaHoraSemanalCargo(codUsu);
                        pDatosUsu = DBControlAsistencia.getDatosUsuarioLocal(codUsu);
                        
                    } catch (SQLException e) {
                        log.error("",e);
                        pDatosUsu = nomUsu;
                        pValidaCargo = "S";
                    }
                    if(cantVSCDia==0&&pValidaCargo.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                    pUsuario +=
                            /*""+idFila+" - "+ FarmaUtility.completeWithSymbol(vDesRol, 15, " ", "D") +
                            " "+
                            FarmaUtility.completeWithSymbol(nomUsu, 10, " ", "D")*/
                            
                    "El "+FarmaUtility.completeWithSymbol(vDesRol, 15, " ", "D")+" - " +
                    pDatosUsu+ "\n"+
                    " tiene acumuladas "+cantHoraUsu+" horas semanales, debe llegar a "+
                    vMaxHS_MFALegal
                    +
                    " minimas.("+
                    idFila
                    +")"+"\n";
                }
            }
        }
        
        if(pUsuario.trim().length()>0){
            String pMsj = "";
            if(pCantidad==1)
                pMsj = ""+
                                                                pUsuario+
                                                               "¿Desea grabar el horario?";
                else
                    pMsj = ""+
                                                                pUsuario+
                                                               "¿Desea grabar el horario?";
                
            if(JConfirmDialog.rptaConfirmDialog(new JDialog(), pMsj)) {
                resultado = true;
            }
            else
               resultado = false;
        }
        
        return resultado;
    }
    
    public static boolean isCantHrsValidas(double cantHoraUsu,
                                        double vMaxHS_MFA,
                                        String codUsu) {
        boolean flag = true;
        if(cantHoraUsu > vMaxHS_MFA){
            flag = false;
            if (codUsu.equals("")) {
                flag = false;
            }else {
                int cant = 0;
                for (int c = 0; c < JTimeTable.gListaHePreAprob.size(); c++) {
                    ArrayList list = (ArrayList)JTimeTable.gListaHePreAprob.get(c);
                    String secUsu = list.get(0).toString();
                    String sCantHoras = list.get(1).toString();
                    Double dCantHoras = Double.parseDouble(sCantHoras);
                    if (((cantHoraUsu == (double)dCantHoras) &&
                          (codUsu.equals(secUsu)))) {
                        cant = cant + 1;
                    }
                }
                if (cant == 1) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public static boolean isCompletoUsuariosHorario(ArrayList<ObjRowTime> vListaFila) {

        for (int i = 0; i < vListaFila.size(); i++) {
            ObjRowTime vFila = vListaFila.get(i);
            String codUsu = vFila.getVUsu().getPCodUsu().trim();
            if (codUsu.equalsIgnoreCase(ConstantsControlAsistencia.COD_USU) || codUsu.equalsIgnoreCase("")) {
                FarmaUtility.showMessage(new JDialog(), "Debe tener todos los usuarios seleccionados.", null);
                return false;
            }

        }

        return true;
    }
    
    public static boolean isCompletoRolesHorario(ArrayList<ObjRowTime> vListaFilas) {
        for (int i = 0; i < vListaFilas.size(); i++) {
            ObjRowTime vFila = vListaFilas.get(i);
            if ((vFila.getVRol().getPCodRol()).equalsIgnoreCase(ConstantsControlAsistencia.COD_ROL) ||
                (vFila.getVRol().getPCodRol()).equalsIgnoreCase("")) {
                FarmaUtility.showMessage(new JDialog(), "Debe tener todos los roles seleccionados.", null);
                return false;
            }
        }
        return true;
    }    


    public static boolean isCeldaNullaValidaHorario(ArrayList<ObjRowTime> vListaFila, int vOpcion) {

        for (int i = 0; i < vListaFila.size(); i++) {
            ObjRowTime vFila = vListaFila.get(i);

            int cantCeldaNulla =
                isCeldaNulaValidaHorario(vFila.getVDiaD1().getPCodhora().trim()) + isCeldaNulaValidaHorario(vFila.getVDiaD2().getPCodhora().trim()) +
                isCeldaNulaValidaHorario(vFila.getVDiaD3().getPCodhora().trim()) +
                isCeldaNulaValidaHorario(vFila.getVDiaD4().getPCodhora().trim()) +
                isCeldaNulaValidaHorario(vFila.getVDiaD5().getPCodhora().trim()) +
                isCeldaNulaValidaHorario(vFila.getVDiaD6().getPCodhora().trim()) +
                isCeldaNulaValidaHorario(vFila.getVDiaD7().getPCodhora().trim());

            if (cantCeldaNulla > 0) {
                if (vOpcion == 0) {
                    FarmaUtility.showMessage(new JDialog(), "Completar el horario Nº " + vFila.getIdRowTime(), null);

                } else {
                    String nomUsu = vFila.getNombre();
                    FarmaUtility.showMessage(new JDialog(), "Completar horario para el usuario " + nomUsu, null);

                }
                return false;

            }


        }

        return true;

    }


    public static int isCeldaNulaValidaHorario(String pCodHoraDia) {
        try {
            if (pCodHoraDia.equalsIgnoreCase(ConstantsControlAsistencia.COD_HORA) || pCodHoraDia.equalsIgnoreCase(""))
                return 1;
            else
                return 0;
        } catch (Exception nfe) {
            log.error("",nfe);
            return 0;
        }
    }

    public static boolean isUSuarioSolitudValidaHorario(String pFecInicio, String pFecfin,
                                                        ArrayList<ObjRowTime> vListaFila) {
        boolean vRes = true;
        for (int i = 0; i < vListaFila.size(); i++) {
            ObjRowTime vFila = vListaFila.get(i);

            String codUsu = vFila.getVUsu().getPCodUsu();
            String codDia1 = vFila.getVDiaD1().getPCodhora();
            String codDia2 = vFila.getVDiaD2().getPCodhora();
            String codDia3 = vFila.getVDiaD3().getPCodhora();
            String codDia4 = vFila.getVDiaD4().getPCodhora();
            String codDia5 = vFila.getVDiaD5().getPCodhora();
            String codDia6 = vFila.getVDiaD6().getPCodhora();
            String codDia7 = vFila.getVDiaD7().getPCodhora();
            if (!UtilityControlAsistencia.isUsuaTieneSolitudHorario(codUsu, pFecInicio, pFecfin, codDia1, codDia2, codDia3, codDia4,
                                                  codDia5, codDia6, codDia7)) {
                vRes = false;
                break;
            }
        }
        return vRes;
    }
    public static String comparaFechaFMarca(String pFechaEntrada, String pFechaSalida) {
        String resultado = "";
        try {
            resultado = DBControlAsistencia.getComparaFechasMarca(pFechaEntrada, pFechaSalida).trim();
        } catch (SQLException e) {
            log.error("",e);
        }
        return resultado;
    }

    public static String validaFormatoFechaFMarca(String pFechaSalida) {
        String resultado = "";
        try {
            resultado = DBControlAsistencia.validaFormatoFechaMarca(pFechaSalida).trim();
        } catch (SQLException e) {
            log.error("",e);
        }
        return resultado;
    }

    public static String validaRangoHorasFMarca(String pFechaEntrada, String pFechaSalida, int cantMarcacion) {
        String resultado = "";
        try {
            resultado = DBControlAsistencia.validaRangoHorasMarca(pFechaEntrada, pFechaSalida, cantMarcacion).trim();
        } catch (SQLException e) {
            log.error("",e);
        }
        return resultado;
    }

    public static String msgRangoHorasFMarca(int cantMarcacion) {
        String resultado = "";
        try {
            resultado = DBControlAsistencia.msgRangoHorasMarca(cantMarcacion);
        } catch (SQLException e) {
            log.error("",e);
        }
        return resultado;
    }


    public static boolean grabaMarcacionFMarca(String pDni,
                                               String pFechaRegistro,
                                               String pFechaSalida,
                                               String pHoraSalida,
                                               int pCodMotivo,
                                               String pObservacion,
                                               String fechaSalida){
        boolean flag = true;
        try{
             DBControlAsistencia.actualizarMarcacionSalida(pDni, 
                                                           pFechaRegistro,                                                                        
                                                           pFechaSalida,
                                                           pHoraSalida,
                                                           pCodMotivo,
                                                           pObservacion,
                                                           fechaSalida);
            FarmaUtility.aceptarTransaccion();
            flag=true;            
        }catch(Exception e){
            FarmaUtility.liberarTransaccion();
            flag=false;
            log.error("",e);
        }
        return flag;
    }


    public static String maxRangoHorasFMarca(String pFechaEntrada, String pFechaSalida, int cantMarcacion) {
        String resultado = "";
        try {
            resultado = DBControlAsistencia.maxRangoHorasMarca(pFechaEntrada, pFechaSalida, cantMarcacion).trim();
        } catch (SQLException e) {
            log.error("",e);
        }
        return resultado;
    }

    public static String grabaHorarioLocal(String vDesde,String vHasta,String pCodPlantilla,
                                           ArrayList<ObjRowTime> vDetalle) throws SQLException {
        // 01 Paso
        String pCodHorario = DBControlAsistencia.grabaHorarioCab(vDesde,vHasta,pCodPlantilla).trim();
        // 02 Paso
        ArrayList<ObjRowTime> vListaFilas = vDetalle;
        for (int i = 0; i < vListaFilas.size(); i++) {
            ObjRowTime vFila = vListaFilas.get(i);
            String idFila = vFila.getIdRowTime();
            String codRol = vFila.getVRol().getPCodRol();
            String codUsu = vFila.getVUsu().getPCodUsu();
            String codRefrigerio = vFila.getVDiaRefrigerio().getPCodhora();
            String codDia1 = vFila.getVDiaD1().getPCodhora();
            String codDia2 = vFila.getVDiaD2().getPCodhora();
            String codDia3 = vFila.getVDiaD3().getPCodhora();
            String codDia4 = vFila.getVDiaD4().getPCodhora();
            String codDia5 = vFila.getVDiaD5().getPCodhora();
            String codDia6 = vFila.getVDiaD6().getPCodhora();
            String codDia7 = vFila.getVDiaD7().getPCodhora();
            double cantHoras  = Double.parseDouble(vFila.getHoraTrabajada().substring(0, vFila.getHoraTrabajada().length()-2));
            String pIndUltimaFila = "N";
            if(i+1>=vListaFilas.size())
                pIndUltimaFila = "S";
            DBControlAsistencia.grabaHorarioDet(pCodHorario, idFila, codRol,codUsu, codRefrigerio,codDia1, codDia2, codDia3,
                                                              codDia4, codDia5,codDia6, codDia7,pIndUltimaFila, cantHoras);
        }
        try {
            enviaHorarioJefes(pCodHorario);
        } catch (Exception e) {
            log.error("",e);
        }
        return pCodPlantilla;
    }
    

    public static void modificarHorarioLocal(String codHorario,String codPlantilla,
                                                  ArrayList<ObjRowTime> vDetalle){
        try{
            // 01 Paso
            String pCodHorario = DBControlAsistencia.modificarHorarioCab(codHorario,codPlantilla).trim();
            // 02 Paso
            ArrayList<ObjRowTime> vListaFilas = vDetalle;
            for (int i = 0; i < vListaFilas.size(); i++) {
                ObjRowTime vFila = vListaFilas.get(i);
                String idFila = vFila.getIdRowTime();
                String codRol = vFila.getVRol().getPCodRol();
                String codUsu = vFila.getVUsu().getPCodUsu();    
                String codRefrigerio = vFila.getVDiaRefrigerio().getPCodhora();
                String codDia1 = vFila.getVDiaD1().getPCodhora();
                String codDia2 = vFila.getVDiaD2().getPCodhora();
                String codDia3 = vFila.getVDiaD3().getPCodhora();
                String codDia4 = vFila.getVDiaD4().getPCodhora();
                String codDia5 = vFila.getVDiaD5().getPCodhora();
                String codDia6 = vFila.getVDiaD6().getPCodhora();
                String codDia7 = vFila.getVDiaD7().getPCodhora();
                double cantHoras  = Double.parseDouble(vFila.getHoraTrabajada().substring(0, vFila.getHoraTrabajada().length()-2));
                String pIndUltimaFila = "N";
                if(i+1>=vListaFilas.size())
                    pIndUltimaFila = "S";
                DBControlAsistencia.modificarHorarioDet(codHorario, idFila, codRol,codUsu,codRefrigerio, codDia1, codDia2, codDia3,
                                                       codDia4, codDia5,codDia6, codDia7,pIndUltimaFila, cantHoras);
            }
            enviaHorarioJefes(pCodHorario);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * Devuelve un indicador para ver si debe mostrar la marcacion de entrada, salida o regularizar la salida.
     * @author ASOSA
     * @since 14/09/2015
     * @type CTRLASIST
     * @return
     * @throws SQLException
     */
    public static String getIndMarcEntrSalida(String pIdentificador) {
        String ind = "";
        try {
            ind = DBControlAsistencia.getIndMarcEntrSalida(pIdentificador);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ind;
    }

    public static String getParametrosFTP(){
      String cadenaConexion="N";
        try {
            cadenaConexion=DBControlAsistencia.getParametrosFtp();
        } catch (SQLException e) {
            log.error("Error al obtener parametros de conexion FTP ",e);
        }
        return cadenaConexion;  
    }

    /**
     * Devuelve un DATA del usuario que va a regularizar su hora de salida.
     * @author ASOSA
     * @since 10/09/2015
     * @kind CTRLASIST
     * @param list
     * @return
     * @throws SQLException
     */
    public static void getDataUsuario(ArrayList list, String pDni) {
        try {
            DBControlAsistencia.getDataUsuario(list,pDni);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    /**
     * Listado de registro de solicitudes
     * @author:Cesar Huanes
     * @since19/09/2015
     * */  
    public static  ArrayList listarSolicitudes(int pDias){
       ArrayList lstSolitudes=null;
        try {
            lstSolitudes=DBControlAsistencia.listarSolicitudes(pDias);
        } catch (Exception e) {
            log.error("",e);
        }
        return  lstSolitudes;
    }
    /**
     * Obtiene los datos del solicitante mendiante el dni
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static  ArrayList getDatosUsuario(String pDni){
       ArrayList lstDatosUsuario=null;
        try {
            lstDatosUsuario=DBControlAsistencia.getDatosUsuario(pDni);
        } catch (Exception e) {
            log.error("",e);
        }
        return  lstDatosUsuario;
    }
    /**
     * Lista los tipos de solicitud en el combobox
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static ArrayList listarTipoSolic(){
        ArrayList lstTipoSolic=null;
        try {
            lstTipoSolic=DBControlAsistencia.listarTipoSolicitud();
        } catch (Exception e) {
            log.error("",e);
        }
        return  lstTipoSolic; 
    }
    /**
     * Lista los subtipos de solicitud en el combobox
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static ArrayList listarSubTipoSolic(String pCodTipoSolic){
        ArrayList lstSubTipoSolic=null;
        try {
            lstSubTipoSolic=DBControlAsistencia.listarSubTipoSolic(pCodTipoSolic);
        } catch (Exception e) {
            log.error("",e);
        }
        return  lstSubTipoSolic; 
    }
    /**
     * Graba las solicitudes
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static String  grabarSolicitud(String pDni, String pFecInicio, String pFecFin, String pCodMaeTipo, 
                                          String pCodMaeTipo2, String pNomArchivo,String pObservacion,
                                          String pFechaMarcacionNueva)throws Exception {
       String resultado="";
       
            resultado=DBControlAsistencia.grabarSolicitud(pDni, 
                                                          pFecInicio, 
                                                          pFecFin, 
                                                          pCodMaeTipo, 
                                                          pCodMaeTipo2, 
                                                          pNomArchivo,
                                                          pObservacion,
                                                          pFechaMarcacionNueva).trim();
           
       
     return resultado;
    }
    /**
     *Verifica si existe cruce de fechas entre el mismo
     * tipo se solicitud por usuario
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static boolean existeCruceFecha(String  pDni,
    String   pFecInicio,String  pFecFin,String  pCodMaeTipo){
        String resultado="";
         boolean flag=false;
         try {
             resultado=DBControlAsistencia.existeCruceFecha(pDni, pFecInicio, pFecFin, pCodMaeTipo).trim();
             if(resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){//CUANDO ES S SI HAY CRUCE DE FECHAS
                flag=true; 
             }
         } catch (SQLException e) {
             log.error("Error al validar cruce de fechas");
            flag=false;
          }
         return flag;
        
    }
    
    /**
     * Busca el registro de solicitudes por rango de fechas
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static  ArrayList lstSolicRangoFecha(String pFechaInicio,String pFechaFin, String pEstSol){
       ArrayList lstSolRangoFecha=null;
        try {
            lstSolRangoFecha=DBControlAsistencia.lstSolicRangoFecha(pFechaInicio, pFechaFin, pEstSol);
        } catch (Exception e) {
            log.error("",e);
        }
        return  lstSolRangoFecha;
    }
    /**
     * Verifica si la fecha de inicio es hoy o futuro
     * @author:Cesar Huanes
     * @since 19/09/2015
     * */

    public static boolean isFechaIncioFuturo(String pFechaInicio){
    boolean flag=false;
    String resultado="";
        try {
            resultado=DBControlAsistencia.isFechaInicioFuturo(pFechaInicio).trim();
            if(resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){//S ES FECHA PASADO
                flag=true;
            }
        } catch (SQLException e) {
            log.error("Error al verificar fecha de inicio");
            flag=false;
        }
        return flag;
    }
    
    /**
     * Verifica si la fecha de correcion es menor igual a la fecha actual
     * @author:EMAQUERA
     * @since 03/12/2015
     * */

    public static boolean isFechaCorrecion(String pFechaInicio){
    boolean flag=false;
    String resultado="";
        try {
            resultado=DBControlAsistencia.isFechaCorrecion(pFechaInicio).trim();
            if(resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){//S ES FECHA PASADO
                flag=true;
            }
        } catch (SQLException e) {
            log.error("Error al verificar fecha de inicio");
            flag=false;
        }
        return flag;
    }    
    
    /**
     * Envia el correo electronico de solicitud
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static void enviarCorreo(String pNumRegistroSol){


        try {
            DBControlAsistencia.enviarCorreoSolicitud( pNumRegistroSol);
        } catch (SQLException e) {
            log.error("Error al enviar correo de solicitud");
        }
    }
    /**
     * Actualiza nombre de archivo
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static void actualizaNomArchivo(String pNumRegistroSol,String nomArchivo){

            try {
                String nuevoNomArchivo=UtilFtp.nuevoNombreArchivo(nomArchivo, pNumRegistroSol);
                log.info("Actualiza nombre de arhivo "+nuevoNomArchivo );
                DBControlAsistencia.actualizaNomArchivo( pNumRegistroSol,nuevoNomArchivo);
            } catch (SQLException e) {
                log.error("Error al actualizar nombre de archivo");
            }

    }

    /**
     * Actualiza nombre de archivo
     * @author: Ever Maquera 
     * @since : 23/09/2015
     * */
    public static void solicitudIncompleta(String pNumRegistroSol){

            try {
                log.info("Error al grabar adjunto de Solicitud  :"+pNumRegistroSol );
                DBControlAsistencia.solicitudIncompleta(pNumRegistroSol);
            } catch (SQLException e) {
                log.error("Error al grabar adjunto de Solicitud y/o Base de Datos ");
            }

    }

    public   String isDebeMarcarSalida(){
      String indicador="";
        try {
            indicador=DBControlAsistencia.isDebeMarcarSalida().trim();
        } catch (SQLException e) {
            log.info("Error al valida marcación de salida"+e.getMessage());
        }
        log.info("Marcacione de salida"+indicador);
      return indicador;  
    }

    //CHUANES 17/08/2015 - SE OBTIENE EL  SEC-USU-LOCAL POR DNI
    public String getSecUsuLocal(String pDni){
     String secUsuLocal="";
        try {
            secUsuLocal=DBControlAsistencia.getSecUsuLocal(pDni);
        } catch (SQLException e) {
            log.info("Error a obtener el sec usu local por dni"+e.getMessage());
        }
        return secUsuLocal;
    }

    //CHUANES 28/08/2015 - SE OBTIENE LA HORA DE MARCACION DEL USUARIO
    public static String getHoraMarcacion(){
        String horaMarcacion="";
        try {
            horaMarcacion=DBControlAsistencia.getHoraMarcacion();
        } catch (SQLException e) {
            log.info("Error al obtener hora de marcación");
        }
        return horaMarcacion;
    }

    //CHUANES 28/08/2015 - INDICA SI UN USUARIO DEBE MARCAR ENTRADA
    public String isDebeMarcarEntrada(){
       String indicador=""; 
        try {
            indicador=DBControlAsistencia.isDebeMarcarEntrada().trim();
        } catch (SQLException e) {
            log.info("Error al obtener el indicador de entrada");
        }
        return indicador;
    }

    //CHUANES 28/08/2015 - VALLIDA SI EL USUARIO LOGEADO ES EL QUE ESTA MARCADO SU ASISTENCIA
    public static boolean  isUsuarioLogeado(String pDni){
        boolean flag=false;
        String resultado="";
        
        try {
            resultado=DBControlAsistencia.isUsuarioLogeado(pDni).trim();
            if (resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                flag=true;
            }
        } catch (SQLException e) {
            log.info("Error al obtener el indicador del usuario logeado");
        }
        return flag; 
    }
    
    public String indActivaMarcacion(){
       String resultado="";
        try {
            resultado=DBControlAsistencia.indActivaMarcacion().trim();
        } catch (SQLException e) {
            log.info("Error al obtener el indicador del usuario logeado");
        }
        return resultado;
    }

    /**
     * Determinar a que DLG llamar dependiendo de lo configurado
     * @author ASOSA
     * @since 14/09/2015
     * @kind CTRLASIST
     */
     public static boolean determinarMarcacionGeneral (Frame parent,
                                                    String indES) {
         boolean flag = true;

         if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR)||
             FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)||
             FarmaVariables.dlgLogin.verificaRol(ConstantsControlAsistencia.ROL_CONTROL_INTERNO)||
             FarmaVariables.dlgLogin.verificaRol(ConstantsControlAsistencia.ROL_DERMOCONSULTOR)||
             FarmaVariables.dlgLogin.verificaRol(ConstantsControlAsistencia.ROL_GNC)) { 
             String indMarcEntrSalida = UtilityControlAsistencia.getIndMarcEntrSalida(FarmaVariables.vNuSecUsu);
             String indActRegMarcSal = UtilityInventario.obtenerParametroString(ConstantsControlAsistencia.ID_TAB_IND_ACTIVO_REG_MARC_SALIDA);
             String indNewCtrlAsist = UtilityInventario.obtenerParametroString(ConstantsControlAsistencia.ID_TAB_IND_ACTIVO_NEW_CTRL_ASIST);
             if (indMarcEntrSalida.equals(ConstantsControlAsistencia.IND_REGULARIZAR)) {
                 if (indActRegMarcSal.equals(FarmaConstants.INDICADOR_S)) {
                     flag = false;
                     FarmaUtility.showMessage((JFrame)parent, 
                                              "El turno anterior usted no marcó su salida, por favor regularizar.", 
                                              null);                   
                 }
             } else if (indMarcEntrSalida.equals(indES)) {
                 flag = llamarMarcacionES(indNewCtrlAsist,
                                 indMarcEntrSalida,
                                   parent,
                                   indES);
             }
             if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
                 if (indNewCtrlAsist.equals(FarmaConstants.INDICADOR_S)) {
                     UtilityControlAsistencia.getAvisoFinHorario();
                 }                 
             }
         } else {
             flag = true;
         }

         return flag;
     }
    
    /**
     * llamar a la ventana de marcacion de E/S
     * @author ASOSA
     * @since 14/09/2015
     * @kind CTRLASIST
     */
    public static boolean llamarMarcacionES (String indNewCtrlAsist,
                                    String indMarcEntrSalida,
                                    Frame parent,
                                    String indES) {
        if (indNewCtrlAsist.equals(FarmaConstants.INDICADOR_S)) {            
            FarmaVariables.vAceptar = false;
            FarmaUtility.showMessage((JFrame)parent, 
                                     "Favor de realizar su marcación para iniciar sus labores.", 
                                     null);            
        }
        return FarmaVariables.vAceptar;
    }
    
    public static void salirSistema(Frame parent) {
        UtilityVentas.eliminaCodBarra();
        UtilityVentas.eliminaBoletaTxt();
        if (FarmaVariables.vEconoFar_Matriz)
            parent.dispose();
        else
            System.exit(0);
    }

    
    public static void getTurnoRangoFechaSolicitudAprobada(ArrayList lstGrilla,
                                                           String pFechaInicio,
                                                           String pFechaFin) {
        try{
            DBControlAsistencia.getTurnoRangoFechaSolicitudAprobada(lstGrilla,pFechaInicio,pFechaFin);
        }catch(Exception e){
            log.error("",e);
        }  
    }

    /**
     * obtiene indicador si falta marcar temperatura para un determinado dia
     * @author ASOSA
     * @since 24/09/2015
     * @type CTRLASIST
     * @param pFecha
     * @return
     * @throws SQLException
     */
    public static boolean isFaltaTemperatura(String pFecha) {
        boolean flag = false;
        String indTemp = "";
        String result = "";
        try {
            indTemp = DBControlAsistencia.getIndFaltaTemperatura(pFecha);
            result = DBControlAsistencia.verificaRolUsuario(FarmaVariables.vNuSecUsu, 
                                                            ConstantsControlAsistencia.ROL_QF_ADMINLOCAL);
            if (indTemp.equals("S") && result.equals("S")) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;   //por si hay algun error que no bloquee la marcacion de salida.
            log.error(e.getMessage());
        } finally {
            return flag;
        }
    }
    
    /**
     * Obtener la diferencia en horas de dos fechas.
     * @author ASOSA
     * @since 02/10/2015
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    public static double obtenerDiferenciaHoras(Calendar fechaInicial ,Calendar fechaFinal){ 
        double diferenciaHoras = 0;
        //HALLAR LAS HORAS DEL DIA DE CADA FECHA
        int horaFin = fechaFinal.get(Calendar.HOUR_OF_DAY);
        int horaIni = fechaInicial.get(Calendar.HOUR_OF_DAY);
        //HALLAR LOS DIAS DEL AÑO DE CADA FECHA
        int diaAnioFin = fechaFinal.get(Calendar.DAY_OF_YEAR);
        int diaAnioIni = fechaInicial.get(Calendar.DAY_OF_YEAR);
        //HALLAR LOS MINUTOS DE CADA FECHA
        double minFin = fechaFinal.get(Calendar.MINUTE);
        double minIni = fechaInicial.get(Calendar.MINUTE);
        //SE AUMENTA LA CANTIDAD DE HORAS DE ACUERDO A CUANTOS DIAS HAY DE DIFERENCIA
        horaFin = horaFin + ((diaAnioFin - diaAnioIni) * 24);
        
        double minFinToHours = minFin / 60;
        double minIniToHours = minIni / 60;
        
        diferenciaHoras = (horaFin + minFinToHours) - (horaIni + minIniToHours);
        diferenciaHoras = obtenerNumeroRedondeado(diferenciaHoras, 2);
        
        return diferenciaHoras; 
    } 
    
    /**
     * OBtener numero redondeado a la cantidad de decimales deseados CTRLASIST
     * @author ASOSA
     * @since 02/10/2015
     * @param val
     * @param places
     * @return
     */
    public static double obtenerNumeroRedondeado(double val, int places) {
        long factor = (long)Math.pow(10,places);
        val = val * factor;
        long tmp = Math.round(val);
        return (double)tmp / factor;
    }
    
    /**
     * Convertir String a Date
     * @author ASOSA
     * @since 02/10/2015
     * @param fecha
     * @param caracter
     * @param op
     * @return
     */
    public static Date obtenerStringToDate(String fecha,String caracter,int op){ 
        String formatoHora=" HH:mm:ss"; 
        String formato="yyyy"+caracter+"MM"+caracter+"dd"+formatoHora; 
        if(op==1)   
            formato="yyyy"+caracter+"dd"+caracter+"MM"+formatoHora; 
        else if(op==2) 
            formato="MM"+caracter+"yyyy"+caracter+"dd"+formatoHora; 
        else if(op==3) 
            formato="MM"+caracter+"dd"+caracter+"yyyy"+formatoHora; 
        else if(op==4) 
            formato="dd"+caracter+"yyyy"+caracter+"MM"+formatoHora; 
        else if(op==5) 
            formato="dd"+caracter+"MM"+caracter+"yyyy"+formatoHora; 
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault()); 
        Date fechaFormato=null; 
        try { 
            sdf.setLenient(false); 
            fechaFormato=sdf.parse(fecha); 
        } catch (Exception ex) { 
            log.info("" + ex.getMessage());
            } 
        return fechaFormato; 
    } 
    
    /**
     * Indica si se puede ingresar DNI digitando
     * @author ASOSA
     * @since 03/12/2015
     * @return
     */
    public static String indDniIngreso() {
        String ind = "";
        try {
            ind = DBControlAsistencia.indDniIngreso();
        } catch(Exception e) {
            ind = "N";
            log.error(e.getMessage());
        } finally {
            return ind;
        }
    }
    
    /**
     * Indica si esta activa la marcacion de huella
     * @author ASOSA
     * @since 03/10/2015
     * @return
     */
    public static String indHuella() {
        String ind = "";
        try {
            ind = DBControlAsistencia.indHuella();
        } catch(Exception e) {
            ind = "N";
            log.error(e.getMessage());
        } finally {
            return ind;
        }
    }
    
    /**
     * obtener mensaje de bienvenida
     * @author ASOSA
     * @since 19/10/2015
     * @param pIndFormMarcacion
     * @return
     * @throws SQLException
     */
    public static String msgBienvenida(String pDni) {
        String msg = "";
        try {
            msg = DBControlAsistencia.msgBienvenida(pDni);
        } catch(Exception e) {
            log.error(e.getMessage());
            msg = "";
        } finally {
            return msg;
        }
    }
   

    public static void copiaPlantilla(String pCodPlantillaOrigen) throws SQLException {
        DBControlAsistencia.saveCopiaPlantilla(pCodPlantillaOrigen);
    }

    public static void getAvisoFinHorario() {        
        String[] cadena = null;
        String pMsj ="";
        try
        {
            cadena = DBControlAsistencia.getAvisoFinHorario().trim().split("@");
            for(int i=0;i<cadena.length;i++){
                pMsj += cadena[i].trim()+"\n";
            }
            if (pMsj.trim().equalsIgnoreCase("N")) {
                FarmaUtility.showMessage(new JDialog(), 
                                         "Error al obtener mensaje de validación", 
                                         null);
            } else if(!pMsj.trim().equalsIgnoreCase("S")) {
                FarmaUtility.showMessage(new JDialog(), pMsj, null);
            }
            
        } catch (Exception e) {
            log.error("",e);
            FarmaUtility.showMessage(new JDialog(), "Error al obtener mensaje de validación\n" +
                                     e.getMessage(), null);
        }
    }
    
    public static boolean isRepiteValidaHorarioPorTerminar() {
        boolean flag = true;
        String resultado = "";
        try
        {
            resultado = DBControlAsistencia.getIndRepiteValidaAvisoFinHorario().trim();
            if (resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) { //ES TRUE CUANDO YA GENERO PLANTILLA
                flag = false;
            }
        } catch (Exception e) {
            log.error("",e);
            FarmaUtility.showMessage(new JDialog(), "Error al validar Si Repite Validacion !\n" +
                                     e.getMessage(), null);
            flag = false;
        } finally {
            return flag;
        }
    }

    /**
     * Obtiene la fecha de salida de la persona
     * @author EMAQUERA
     * @since 21.10.2015
     * @param pDni
     * @return
     */
    public static String obtieneFecSalidaSug(String pDni) {
        String FecSalida="";
        try {
            FecSalida = DBControlAsistencia.obtieneFecSalidaSug(pDni);
            return FecSalida;
        } catch (Exception e) {
            log.error("",e);            
            return FecSalida;
        }
    } 
    
    /**
     * Obtiene el indicador del mensaje a mostrar
     * @author EMAQUERA
     * @since 22.10.2015
     * @param pDni
     * @return
     */
    public static String obtieneIndMsje(String pDni,
                                        String pFechaSalida,
                                        String pMsg){
        String mensajeTotal="";
        try {
            mensajeTotal = DBControlAsistencia.obtieneIndMsje(pDni,
                                                         pFechaSalida,
                                                         pMsg);
            return mensajeTotal;
        } catch (Exception e) {
            log.error("",e);            
            return mensajeTotal;
        }        
    }
    
    
    /**
     * Obtiene descripcion del mensaje para cada marcacion
     * @author Dubilluz
     * @since 27.10.2015
     * @param pValor
     * @return
     */
    public static void obtieneDescMsjeAsistencia(){
        String[] descMsje= null;
        try {
            descMsje = DBControlAsistencia.getMensajeFrecuenteIngreso().split("@");
            String pMsj ="";
            for(int i=0;i<descMsje.length;i++)
                pMsj += descMsje[i].trim()+"\n";
            FarmaUtility.showMessage(new JDialog(), pMsj, null);
        } catch (Exception e) {
            log.error("",e);            
            
        }
    }
    
    public static void muestraTrabajadoresCesados() {
        ArrayList vLista = new ArrayList();
        try {
            DBControlAsistencia.getListaPorCese(vLista);
            if(vLista.size()>0){
                String vMsj = "";
                for(int i=0;i<vLista.size();i++){
                    if(i+1==vLista.size())
                      vMsj = ""+FarmaUtility.getValueFieldArrayList(vLista,i,0)+"-"+FarmaUtility.getValueFieldArrayList(vLista,i,1);
                    else
                      vMsj = ""+FarmaUtility.getValueFieldArrayList(vLista,i,0)+"-"+FarmaUtility.getValueFieldArrayList(vLista,i,1)+"\n";
                }
                FarmaUtility.showMessage(new JDialog(),"Se le recuerda que estos trabajadores deben ser Cesados:\n"+
                                                       vMsj, null);
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * Listado de registro de Inasistencia
     * @author EMAQUERA
     * @since 28.10.2015
     * @param pDias
     * @return
     */
    public static  ArrayList listarInasistencias(){
       ArrayList lstInasistencias=null;
        try {
            lstInasistencias=DBControlAsistencia.lstInasistencias();
        } catch (Exception e) {
            log.error("",e);
        }
        return  lstInasistencias;
    }
    
    /**
     * Inserta o Actualiza Registro de Ingreso y/o Salida
     * @author EMAQUERA
     * @since 26.10.2015
     * @param pDni
     * @param vTipo
     * @param vIndicador
     */
    public static void grabaRegularizacionSalida(String pDni, 
                                        String pFechaHoraSalida) {
        try{
            DBControlAsistencia.grabaRegularizacionSalida(pDni,
                                                          pFechaHoraSalida);
            FarmaUtility.aceptarTransaccion();
        }catch(Exception e){
            FarmaUtility.liberarTransaccion();
            log.error("",e);
        }  
    }   
    
    public static void getDatosReporteHorario(String pCodHorario,
                                              ArrayList vListaL,
                                              ArrayList vListaM_V,
                                              ArrayList vListaS,
                                              ArrayList vListaD
                                              ){
        try{
            DBControlAsistencia.procesaReporteHorario(pCodHorario);
            DBControlAsistencia.getReporteDia(ConstantsControlAsistencia.REP_LUNES,vListaL,pCodHorario);
            DBControlAsistencia.getReporteDia(ConstantsControlAsistencia.REP_MARTES_VIERNES,vListaM_V,pCodHorario);
            DBControlAsistencia.getReporteDia(ConstantsControlAsistencia.REP_SABADO,vListaS,pCodHorario);
            DBControlAsistencia.getReporteDia(ConstantsControlAsistencia.REP_DOMINGO,vListaD,pCodHorario);
            FarmaUtility.aceptarTransaccion();
        }catch(Exception e){
            FarmaUtility.liberarTransaccion();
            log.error("",e);
            FarmaUtility.showMessage(new JDialog(),"Ocurrió un error al intentar procesar el reporte.", null);
        } 
    }
    
    /**
     * listar personas que tienen aprobadas para trabajar cierta cantidad de horas superiores de 48hrs durante una semana especifica.
     * @author ASOSA
     * @since 08/02/2016
     * @return
     * @throws SQLException
     */
    public static ArrayList listarHePreAprob(String fechaIni,
                                             String fechaFin) {
        ArrayList listaHePreAprob = new ArrayList();
        try {
            listaHePreAprob = DBControlAsistencia.listarHePreAprob(fechaIni, 
                                                 fechaFin);
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return listaHePreAprob;
    }
    
    /**
     * Listar colaboradores del local, solo vendedores, QF, GNC, dermoconsultores y control interno.
     * @author ASOSA
     * @since 15/02/2016
     * @return
     * @throws SQLException
     */
    public static ArrayList listarColaboradoresLocal() {
        ArrayList list = new ArrayList();
        try {
            list = DBControlAsistencia.listarColaboradoresLocal();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

}
