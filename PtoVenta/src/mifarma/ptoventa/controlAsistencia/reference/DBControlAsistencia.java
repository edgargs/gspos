package mifarma.ptoventa.controlAsistencia.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import object.ObjRole;
import object.ObjTimes;
import object.ObjUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DBControlAsistencia.java<br>
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
public class DBControlAsistencia {
    private static final Logger log = LoggerFactory.getLogger(DBControlAsistencia.class);
    static ArrayList parametros = null;

    /**
     * Listar el maestro de turnos MENOS los turnos ya asignados.
     * @author ASOSA
     * @since 09.09.2015
     * @throws SQLException
     * @return
     */
    public static ArrayList lstMaestroTurnoGestionTurno() throws SQLException {

        parametros = new ArrayList();

        ArrayList lstMaestroTurno = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstMaestroTurno,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LIST_MAE_TURNO(?,?,?)",
                                                          parametros);        
        return lstMaestroTurno;
    }

    /**
     * Listar turnos asignados al local.
     * @author ASOSA
     * @since 09.09.2015
     * @throws SQLException
     * @return
     */
    public static ArrayList lstMaestroTurnoLocalGestionTurno() throws SQLException {
        ArrayList lstMaestroTurno = new ArrayList();

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //ASOSA - 09/09/2015 - CTRLASIST (cambio de metodo y agregar grupoCia)
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstMaestroTurno,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LIST_TURNO_LOCAL(?,?)",
                                                          parametros);
        return lstMaestroTurno;
    }

    /**
     * GRABA EN LA TABLA MAESTRO DE TURNOS
     * @author CHUANES
     * @since 04.05.2015
     * @param horaInicio
     * @param horaFin
     * @param estado
     * @throws SQLException
     * @return
     */
    public static String grabaTurnoGestionTurno(String horaInicio, String horaFin, String estado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(horaInicio);
        parametros.add(horaFin);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(estado);
        log.debug("Inicio " + horaInicio + "Hora Fin :" + horaFin + "Usuario :" + FarmaVariables.vIdUsu + "Estado :" +
                  estado);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_GRABA_MAE_TURNO(?,?,?,?)",
                                                           parametros);
    }

    /**
     * ACTUALIZA EL MAESTRO DE TURNOS
     * @author CHUANES
     * @since 04.05.2015
     * @param idTurno
     * @param horaInicio
     * @param horaFin
     * @param estado
     * @throws SQLException
     * @return
     */
    public static String actualizaTurnoGestionTurno(String idTurno, String horaInicio, String horaFin,
                                                    String estado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(idTurno);
        parametros.add(horaInicio);
        parametros.add(horaFin);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(estado);
        log.debug("Inicio " + horaInicio + "Hora Fin :" + horaFin + "Usuario :" + FarmaVariables.vIdUsu + "Estado :" +
                  estado);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_UPD_MAE_TURNO(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * VERIFICA LA NO DUPLICDAD CUANDO DE GRABA UN NUEVO REGISTRO
     * @author CHUANES
     * @since 04.05.2015
     * @param horaInicio
     * @param horaFin
     * @throws SQLException
     * @return
     */
    public static String verfiDuplicGrabaGestionTurno(String horaInicio, String horaFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(horaInicio);
        parametros.add(horaFin);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_VERIF_DUPLICADO_GRABA(?,?)",
                                                           parametros);
    }

    /**
     * VERIFICA LA NO DUPLICDAD CUANDO SE ACTUALIZA UN REGISTRO
     * @author
     * @since 04.05.2015
     * @param idTurno
     * @param horaInicio
     * @param horaFin
     * @throws SQLException
     * @return
     */
    public static String verfiDuplicActualGestionTurno(String idTurno, String horaInicio,
                                                       String horaFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(idTurno);
        parametros.add(horaInicio);
        parametros.add(horaFin);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_VERIF_DUPLICADO_UPD(?,?,?)",
                                                           parametros);
    }

    /**
     * VERIFICA SI UN TURNO YA GENERO UNA PLANTILLA , DE SER ASI, NO SE DEBE PERMITIR EDITAR
     * @author CHUANES
     * @since 04.05.2015
     * @param idTurno
     * @throws SQLException
     * @return
     */
    public static String isTurnoGeneroPlantillaGestionTurno(String idTurno) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(idTurno);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_VERIF_TURNO_PLANTILLA(?,?,?)",
                                                           parametros);
    }

    /**
     * VERIFICA SI UN TURNO YA GENERO UNA HORARIO , DE SER ASI, NO SE DEBE PERMITIR EDITAR
     * @author CHUANES
     * @since 04.05.2015
     * @param idTurno
     * @throws SQLException
     * @return
     */
    public static String isTurnoGeneroHorarioGestionTurno(String idTurno) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(idTurno);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_VERIF_TURNO_HORARIO(?,?,?)",
                                                           parametros);
    }

    /**
     * VERIFICA SI UN TURNO YA SE ASIGNO A UN LOCAL
     * @author CHUANES
     * @since 04.05.2015
     * @param idTurno
     * @throws SQLException
     * @return
     */
    public static String isAsignaLocalGestionTurno(String idTurno) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(idTurno);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_VERIF_TURNO_ASIGNA(?,?,?)",
                                                           parametros);
    }

    /**
     * ELIMINA UN TURNO DE LA TABLA PBL_TURNO
     * @author 04.05.2015
     * @since 04.05.2015
     * @param idTurno
     * @throws SQLException
     * @return
     */
    public static String isEliminarGestionTurno(String idTurno) throws SQLException {
        parametros = new ArrayList();
        parametros.add(idTurno);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_BORRA_TURNO(?)", parametros);
    }

    /**
     * VALIDA EL MAXIMO  DE HORAS TRABAJAS POR DIA
     * @author CHUANES
     * @since 04.05.2015
     * @param horaInicio
     * @param horaFin
     * @throws SQLException
     * @return
     */
    public static String isMaximoTurnoGestionTurno(String horaInicio, String horaFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(horaInicio);
        parametros.add(horaFin);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_VALIDA_MAX_HORA(?,?)",
                                                           parametros);
    }

    /**
     * VALIDA EL MINIMO  DE HORAS TRABAJAS POR DIA
     * @author CHUANES
     * @since 04.05.2015
     * @param horaInicio
     * @param horaFin
     * @throws SQLException
     * @return
     */
    public static String isMinimoTurnoGestionTurno(String horaInicio, String horaFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(horaInicio);
        parametros.add(horaFin);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_VALIDA_MIN_HORA(?,?)",
                                                           parametros);
    }

    /**
     * Asignar turno al local
     * @author ASOSA
     * @since 10.09.2015
     * @param idTurno
     * @throws SQLException
     * @return
     */
    public static String asignarTurnoLocalGestionTurno(String idTurno) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(idTurno);
        parametros.add(FarmaVariables.vIdUsu);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_INS_ASIG_TURNO_LOCAL(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * DESASIGNAR TURNO AL LOCAL
     * @author CHUANES
     * @since 04.05.2015
     * @param idTurno
     * @throws SQLException
     * @return
     */
    public static String isDesasignarTurnoGestionTurno(String idTurno) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(idTurno);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_DESASIGNAR_TURNO(?,?)",
                                                           parametros);
    }


    public static void listarSolicitudesXRangoFechaSolicitud(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_VTA_LISTA.VTA_LISTA_PROD(?,?)");
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_VTA_LISTA.VTA_LISTA_PROD(?,?)", parametros,
                                                 true);
    }

    /**
     * Lista los rangos de horas que se seleccionaran en el objeto de JTIMETABLE
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vLista
     * @throws SQLException
     */
    public static void listarRangoHorasPlantilla(ArrayList vLista) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        ArrayList vDatos = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_SELEC_RANGO_HORAS(?,?)",
                                                          parametros);
        //System.out.println(">> vDatos <<"+vDatos);
        for (int i = 0; i < vDatos.size(); i++) {
            ObjTimes bean = new ObjTimes();
            //System.out. println(">> v <<"+FarmaUtility.getValueFieldArrayList(vDatos, i, 2));
            bean.setPCodhora(FarmaUtility.getValueFieldArrayList(vDatos, i, 0));
            bean.setPDesHora(FarmaUtility.getValueFieldArrayList(vDatos, i, 1));
            bean.setPMinTrans(Double.parseDouble(FarmaUtility.getValueFieldArrayList(vDatos, i, 2)));
            bean.setPIndLista((FarmaUtility.getValueFieldArrayList(vDatos, i, 3).trim()));
            vLista.add(bean);
        }
    }

    /**
     * Lista los rangos de horas que se seleccionaran en el objeto de JTIMETABLE
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vLista
     * @throws SQLException
     */
    public static void listarRangoHorasRefrigerio(ArrayList vLista) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        ArrayList vDatos = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LIST_REFRIGERIO(?,?)",
                                                          parametros);
        for (int i = 0; i < vDatos.size(); i++) {
            ObjTimes bean = new ObjTimes();
            bean.setPCodhora(FarmaUtility.getValueFieldArrayList(vDatos, i, 0));
            bean.setPDesHora(FarmaUtility.getValueFieldArrayList(vDatos, i, 1));
                bean.setPMinTrans(Double.parseDouble(FarmaUtility.getValueFieldArrayList(vDatos, i, 4)));
            vLista.add(bean);
        }
        log.info(vDatos.size()+" - "+vDatos);
    }    

    /**
     * LISTADO DE ROLES EN EL COMBO
     * @author CHUANES
     * @since 16.05.2015
     * @throws Exception
     * @return
     */
    public static ArrayList lstCmbRoleshorariolocal() throws Exception{
        ArrayList lstRol = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstRol, "PTOVENTA_CONTROL_HORARIO.CTRL_F_LISTA_ROLES(?,?)",
                                                          parametros);

        return lstRol;
    }

    /**
     * Lista filas por defecto para crear una plantilla
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vListaPlantilla
     * @throws SQLException
     */
    public static void listaPlantillaDefecto(ArrayList vListaPlantilla) throws SQLException {
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vListaPlantilla,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_DEFAULT_PLANTILLA",
                                                          parametros);
    }

    /**
     * Graba datos de plantilla cabecera.
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param desCorta
     * @throws SQLException
     * @return
     */
    public static String grabaPlantCabhorariolocal(String desCorta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(desCorta);
        parametros.add(FarmaVariables.vIdUsu);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_SAVE_PLANTILLA_CAB(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Graba datos de horario cabecera.
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param cFecInicio
     * @param cFecFin
     * @param refCodPlantilla
     * @throws SQLException
     * @return
     */
    public static String grabaHorarioCab(String cFecInicio, String cFecFin,
                                         String refCodPlantilla) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cFecInicio);
        parametros.add(cFecFin);
        parametros.add(refCodPlantilla);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_SAVE_HORARIO_CAB(?,?,?,?,?,?" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_SAVE_HORARIO_CAB(?,?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Modifica datos de plantilla cabecera.
     * @author DUBILLUZ
     * @since 16.09.2015R
     * @param pCodPlantilla
     * @param desCorta
     * @throws SQLException
     * @return
     */
    public static String modificarPlantCabhorariolocal(String pCodPlantilla, String desCorta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodPlantilla.trim());
        parametros.add(desCorta.trim());
        parametros.add(FarmaVariables.vIdUsu);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_MODIFY_PLANTILLA_CAB(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Modifica datos de horario cabecera.
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param pCodHorario
     * @param pCodPlantilla
     * @throws SQLException
     * @return
     */
    public static String modificarHorarioCab(String pCodHorario, String pCodPlantilla) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodHorario.trim());
        parametros.add(pCodPlantilla.trim());
        parametros.add(FarmaVariables.vIdUsu);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_MODIFY_HORARIO_CAB(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Graba datos de plantilla Detalle
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param codPlant
     * @param pId
     * @param codRol
     * @param col_3
     * @param col_4
     * @param col_5
     * @param col_6
     * @param col_7
     * @param col_8
     * @param col_9
     * @param pIndUltimaFila
     * @throws SQLException
     */
    public static void grabaPlantillaDethorariolocal(String codPlant, String pId, String codRol,
                                                     String cDiaRefrigerio,
                                                     String col_3,
                                                     String col_4, String col_5, String col_6, String col_7,
                                                     String col_8, String col_9,
                                                     String pIndUltimaFila,
                                                     double cantHoras) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia); //2
        parametros.add(FarmaVariables.vCodLocal); //3
        parametros.add(codPlant); //1
        parametros.add(pId); //1
        parametros.add(codRol); //6
        parametros.add(cDiaRefrigerio);
        parametros.add(col_3); //8
        parametros.add(col_4); //9
        parametros.add(col_5); //10
        parametros.add(col_6); //11
        parametros.add(col_7); //12
        parametros.add(col_8); //13
        parametros.add(col_9); //14
        parametros.add(FarmaVariables.vIdUsu); //15
        parametros.add(pIndUltimaFila);
        parametros.add(cantHoras);
        parametros.add(FarmaVariables.vNuSecUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONTROL_ASISTENCIA.CTRL_P_SAVE_PLANTILLA_DET(?,?,?,?,?," +
                                                                                                         "?,?,?,?,?," +
                                                                                                         "?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Graba datos de horario Detalle
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param cCodHorario
     * @param secHorario
     * @param codRol
     * @param codUsu
     * @param col_4
     * @param col_5
     * @param col_6
     * @param col_7
     * @param col_8
     * @param col_9
     * @param col_10
     * @param vIdUltimaFila
     * @throws SQLException
     */
    public static void grabaHorarioDet(String cCodHorario, String secHorario, String codRol, String codUsu,
                                       String cCodRefrigerio,
                                       String col_4, String col_5, String col_6, String col_7, String col_8,
                                       String col_9, String col_10, String vIdUltimaFila, double cantHoras) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia); //1
        parametros.add(FarmaVariables.vCodLocal); //2
        parametros.add(cCodHorario); //3
        parametros.add(secHorario); //4
        parametros.add(codRol); //5

        parametros.add(codUsu);
        parametros.add(cCodRefrigerio);
        parametros.add(col_4); //6
        parametros.add(col_5); //7
        parametros.add(col_6); //8
        parametros.add(col_7); //9

        parametros.add(col_8); //10
        parametros.add(col_9); //11
        parametros.add(col_10); //12
        parametros.add(FarmaVariables.vIdUsu); //14
        parametros.add(vIdUltimaFila);
        parametros.add(cantHoras);
        
        log.info("PTOVENTA_CONTROL_ASISTENCIA.CTRL_P_SAVE_HORARIO_DET(" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONTROL_ASISTENCIA.CTRL_P_SAVE_HORARIO_DET(" + "?,?,?,?,?,?," +
                                                 "?,?,?,?,?," + "?,?,?,?,?,?)", parametros, false);
    }
    
    /**
     * Modifica datos de plantilla Detalle
     * @author DUBILLUZ
     * @since 16.09.2016
     * @param codPlant
     * @param pId
     * @param codRol
     * @param col_3
     * @param col_4
     * @param col_5
     * @param col_6
     * @param col_7
     * @param col_8
     * @param col_9
     * @param pIndUltimaFila
     * @throws SQLException
     */
    public static void modificarPlantillaDethorariolocal(String codPlant, String pId, String codRol,
                                                         String cCodRefrigerio,
                                                         String col_3,
                                                         String col_4, String col_5, String col_6, String col_7,
                                                         String col_8, String col_9,
                                                         String pIndUltimaFila,
                                                         double cantHoras) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia); //2
        parametros.add(FarmaVariables.vCodLocal); //3
        parametros.add(codPlant); //1
        parametros.add(pId); //1
        parametros.add(codRol); //6
        parametros.add(cCodRefrigerio);
        parametros.add(col_3); //8
        parametros.add(col_4); //9
        parametros.add(col_5); //10
        parametros.add(col_6); //11
        parametros.add(col_7); //12
        parametros.add(col_8); //13
        parametros.add(col_9); //14
        parametros.add(FarmaVariables.vIdUsu); //15
        parametros.add(pIndUltimaFila);
        parametros.add(cantHoras);
        parametros.add(FarmaVariables.vNuSecUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONTROL_ASISTENCIA.CTRL_P_MODIFY_PLANTILLA_DET(?,?,?,?,?," +
                                                                                        "?,?,?,?,?," +
                                                                                        "?,?,?,?,?," +
                                                                                        "?,?)",
                                                 parametros, false);
    }

    /**
     * Modifica datos de horario Detalle
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param codHorario
     * @param pId
     * @param codRol
     * @param codUsu
     * @param col_3
     * @param col_4
     * @param col_5
     * @param col_6
     * @param col_7
     * @param col_8
     * @param col_9
     * @param pIndUltimaFila
     * @throws SQLException
     */
    public static void modificarHorarioDet(String codHorario, String pId, String codRol, String codUsu,String pCodRefrigerio, String col_3,
                                           String col_4, String col_5, String col_6, String col_7, String col_8,
                                           String col_9, String pIndUltimaFila, double cantHoras) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia); //2
        parametros.add(FarmaVariables.vCodLocal); //3
        parametros.add(codHorario); //1
        parametros.add(pId); //1
        parametros.add(codRol); //6
        parametros.add(codUsu);
        parametros.add(pCodRefrigerio);
        parametros.add(col_3); //8
        parametros.add(col_4); //9
        parametros.add(col_5); //10
        parametros.add(col_6); //11
        parametros.add(col_7); //12
        parametros.add(col_8); //13
        parametros.add(col_9); //14
        parametros.add(FarmaVariables.vIdUsu); //15
        parametros.add(pIndUltimaFila);
        parametros.add(cantHoras);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONTROL_ASISTENCIA.CTRL_P_MODIFY_HORARIO_DET(" + "?,?,?,?,?,?," +
                                                 "?,?,?,?,?," + "?,?,?,?,?,?)", parametros, false);
    }

    /**
     * Lista todas las plantilla grabadas
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param lstCabPlantilla
     * @throws SQLException
     */
    public static void listarPlantillasLocal(FarmaTableModel lstCabPlantilla) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(lstCabPlantilla,
                                                 "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LISTA_PLANTILLA_CAB(?,?)",
                                                 parametros, false);
    }

    /**
     * Lista las filas definidas para una plantilla.
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vLista
     * @param codPlantilla
     * @throws SQLException
     */
    public static void listarPlantillaDetalle(ArrayList vLista, String codPlantilla) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codPlantilla);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LISTA_PLANTILLA_DET(?,?,?)",
                                                          parametros);
    }

    /**
     * Lista todos los horarios grabadas
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param lstCabPlantilla
     * @throws SQLException
     */
    public static void lstHorariohorariolocal(FarmaTableModel lstCabPlantilla) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(lstCabPlantilla,
                                                 "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LISTA_HORARIO_CAB(?,?)", parametros,
                                                 false);
    }

    /**
     * Obtiene la fecha de lunes a viernes , para la semana actual
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param lstFechas
     * @throws Exception
     */
    public static void getRangoNuevoHorarioLocal(ArrayList lstFechas) throws Exception {
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstFechas, "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_FECHA_INI_FIN",
                                                          parametros);
    }

    /**
     * Valida si el horario ya esta terminado para dejar grabar
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param cCodPlantilla
     * @throws SQLException
     * @return
     */
    public static String verificaEstadoHorariohorariolocal(String cCodPlantilla) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia); //1
        parametros.add(FarmaVariables.vCodLocal); //2
        parametros.add(cCodPlantilla); //3
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VALIDA_EST_HORARIO(?,?,?)",
                                                           parametros);

    }

    /**
     * ENVIAR CORREO A SUGERENCIA SI EXCEDE MAS DE 48 SEMANANES EN LOS LOCALES
     * @author CHUANES
     * @since 14.08.2015
     * @param pCodHorario
     * @throws SQLException
     */
    public static void enviaEmailJefeshorariolocal(String pCodHorario) throws SQLException {
        FarmaTableModel pTableModel = null;
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia); //1
        parametros.add(FarmaVariables.vCodLocal); //2
        parametros.add(pCodHorario); //3
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CONTROL_HORARIO.CTRL_P_ENVIA_MAIL(?,?,?)", parametros,
                                                 false);
    }

    /**
     * IMPRESION DE HORARIO
     * @author CHUANES
     * @since 02.06.2015
     * @param codPlantilla
     * @throws SQLException
     * @return
     */
    public static ArrayList lstImprimeHorariohorariolocal(String codPlantilla) throws SQLException {

        ArrayList lstPlantilla = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codPlantilla);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstPlantilla,
                                                          "PTOVENTA_CONTROL_HORARIO.CTRL_F_PRINT_HORARIO(?,?,?)",
                                                          parametros);
        return lstPlantilla;
    }

    /**
     * Lista los usuarios del local con los roles que tiene 
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vLista
     * @throws SQLException
     */
    public static void listUsuariosRolHorarios(ArrayList vLista) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CUR_LISTA_USU_ROL(?,?)");
        ArrayList vDatos = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_CUR_LISTA_USU_ROL(?,?)",
                                                          parametros);
        for (int i = 0; i < vDatos.size(); i++) {
            ObjUser bean = new ObjUser();
            bean.setPCodRol(FarmaUtility.getValueFieldArrayList(vDatos, i, 0));
            bean.setPCodUsu(FarmaUtility.getValueFieldArrayList(vDatos, i, 1));
            bean.setPNombre(FarmaUtility.getValueFieldArrayList(vDatos, i, 2));
            vLista.add(bean);
        }
    }

    /**
     * Lista los roles que se seleccionaran en el objeto de JTIMETABLE
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vLista
     * @throws SQLException
     */
    public static void getListaRoleshorariolocal(ArrayList vLista) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        ArrayList vDatos = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_SELEC_LISTA_ROL(?,?)",
                                                          parametros);
        for (int i = 0; i < vDatos.size(); i++) {
            ObjRole bean = new ObjRole();
            bean.setPCodRol(FarmaUtility.getValueFieldArrayList(vDatos, i, 0));
            bean.setPDescRol(FarmaUtility.getValueFieldArrayList(vDatos, i, 1));
            vLista.add(bean);
        }
    }

    /**
     * LISTA  LAS HORAS DE HORARIOS
     * @author CHUANES
     * @since 03.06.2015
     * @param vLista
     * @throws SQLException
     */
    public static void getListaRangoHorashorariolocal(ArrayList vLista) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_CONTROL_HORARIO.CTRL_F_CUR_LISTA_HORA(?,?)");
        ArrayList vDatos = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos, "PTOVENTA_CONTROL_HORARIO.CTRL_F_CUR_LISTA_HORA(?,?)",
                                                          parametros);
        for (int i = 0; i < vDatos.size(); i++) {
            ObjTimes bean = new ObjTimes();
            bean.setPCodhora(FarmaUtility.getValueFieldArrayList(vDatos, i, 0));
            bean.setPDesHora(FarmaUtility.getValueFieldArrayList(vDatos, i, 1));
            bean.setPMinTrans(Double.parseDouble(FarmaUtility.getValueFieldArrayList(vDatos, i, 2)));
            vLista.add(bean);
        }
    }

    /**
     * Lista las filas definidas para un horario
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vLista
     * @param codHorario
     * @throws SQLException
     */
    public static void getLoadDataModifyhorariolocal(ArrayList vLista, String codHorario) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codHorario);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LISTA_HORARIO_DET(?,?,?)");
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LISTA_HORARIO_DET(?,?,?)",
                                                          parametros);


    }

    /**
     * Lista filas por defecto para crear un horario
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vLista
     * @param codPlantilla
     * @throws SQLException
     */
    public static void getLoadDataDefaulthorariolocal(ArrayList vLista, String codPlantilla) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codPlantilla);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_DEFAULT_HORARIO(?,?,?)");
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_DEFAULT_HORARIO(?,?,?)",
                                                          parametros);
    }

    /**
     * INDICA SI UN HORARIO SE DEBE EDITAR
     * @author CHUANES
     * @since 28.06.2015
     * @param codHorario
     * @throws SQLException
     * @return
     */
    public static String isEditableHorario(String codHorario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia); //1
        parametros.add(FarmaVariables.vCodLocal); //2
        parametros.add(codHorario); //3
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_EDITAR_HORARIO(?,?,?)",
                                                           parametros);
    }
    
    /**
     * Obtiene usuario solicitud
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param pSecUsuLocal
     * @param pFechaInicio
     * @param pFechaFinal
     * @param pCodDia1
     * @param pCodDia2
     * @param pCodDia3
     * @param pCodDia4
     * @param pCodDia5
     * @param pCodDia6
     * @param pCodDia7
     * @throws SQLException
     * @return
     */
    public static String isUsuaTieneSolitudhorariolocal(String pSecUsuLocal, String pFechaInicio, String pFechaFinal,
                                                        String pCodDia1, String pCodDia2, String pCodDia3,
                                                        String pCodDia4, String pCodDia5, String pCodDia6,
                                                        String pCodDia7) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia); //1
        parametros.add(FarmaVariables.vCodLocal); //2
        parametros.add(pSecUsuLocal); //2
        parametros.add(pFechaInicio); //3
        parametros.add(pFechaFinal); //4
        parametros.add(pCodDia1); //5
        parametros.add(pCodDia2); //6
        parametros.add(pCodDia3); //7
        parametros.add(pCodDia4); //8
        parametros.add(pCodDia5); //9
        parametros.add(pCodDia6); //10
        parametros.add(pCodDia7); //11
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VALIDA_USU_SOLICITUD(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Obtiene el máximo de horas por semana según MIFARMA
     * @author DUBILLUZ
     * @since 16.09.2015
     * @throws SQLException
     * @return
     */
    public static double maxCantHorasSemanaGestionPlantilla() throws SQLException {
        parametros = new ArrayList();
        return Double.valueOf(FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_MAX_HORA_SEM",
                                                                          parametros));
    }

    /**
     * Obtiene horas legal semanal de trabajo
     * @author DUBILLUZ
     * @since 16.09.2015
     * @throws SQLException
     * @return
     */
    public static double maxCantHorasSemanaLegal() throws SQLException {
        parametros = new ArrayList();
        return Double.valueOf(FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_HORAS_SEM_LEGAL",
                                                                          parametros));
    }
    
    /**
     * Valida el formato de la hora ingresada ingresada en marcación
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param pFechaEntrada
     * @param pFechaSalida
     * @throws SQLException
     * @return
     */
    public static String getComparaFechasMarca(String pFechaEntrada, String pFechaSalida) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pFechaEntrada);
        parametros.add(pFechaSalida);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VALIDA_HORA(?,?)", parametros);
    }
    
    /**
     * Valida el forma de fecha ingresada en marcación
     * @author DUBILLUZ
     * @since 16.05.2015
     * @param pFechaSalida
     * @throws SQLException
     * @return
     */
    public static String validaFormatoFechaMarca(String pFechaSalida) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pFechaSalida);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VALIDA_FORMAT_DATE(?)",
                                                           parametros);
    }

    /**
     * Valida el forma de rango hora ingresada en marcación
     * @author DUBILLUZ 
     * @since 16.09.2015
     * @param pFechaEntrada
     * @param pFechaSalida
     * @param cantMarcacion
     * @throws SQLException
     * @return
     */
    public static String validaRangoHorasMarca(String pFechaEntrada, String pFechaSalida,
                                               int cantMarcacion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pFechaEntrada);
        parametros.add(pFechaSalida);
        parametros.add(cantMarcacion);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VALIDA_RANGO_HORAS(?,?,?)",
                                                           parametros);
    }

    /**
     * Valida si la marcación ingresada y muestra mensaje si este no es valido.
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param cantMarcacion
     * @throws SQLException
     * @return
     */
    public static String msgRangoHorasMarca(int cantMarcacion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(cantMarcacion);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_MSJ_RANGO_HORA(?)",
                                                           parametros);
    }

    /**
     * Rango maximo de 48 horas
     * @author DUBILLUZ
     * @since 09.08.2015
     * @param pFechaEntrada
     * @param pFechaSalida
     * @param cantMarcacion
     * @throws SQLException
     * @return
     */
    public static String maxRangoHorasMarca(String pFechaEntrada, String pFechaSalida,
                                            int cantMarcacion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pFechaEntrada);
        parametros.add(pFechaSalida);
        parametros.add(cantMarcacion);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_MAX_RANGO_48HRS(?,?,?)",
                                                           parametros);
    }

   
    /**
     * Devuelve un indicador para ver si debe mostrar la marcacion de entrada, salida o regularizar la salida.
     * @author ASOSA
     * @since 14/09/2015
     * @return
     * @throws SQLException
     */
    public static String getIndMarcEntrSalida(String pIdentificador) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pIdentificador);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_IND_MARC_ENTR_SALIDA(?,?,?)",
                                                           parametros);
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
    public static void getDataUsuario(ArrayList list, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        //parametros.add(FarmaVariables.vNuSecUsu);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(list, "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_DATA_USUARIO(?,?,?)",
                                                          parametros);
    }

    /**
     * Actualiza la forma fecha de salida del trabajador
     * @author ASOSA
     * @since 10/09/2015
     * @kind CTRLASIST
     * @param pDni
     * @param pFechaRegistro
     * @param pFechaSalida
     * @param pCodMotivo
     * @param pObservacion
     * @return
     * @throws SQLException
     */
    public static void actualizarMarcacionSalida(String pDni, String pFechaRegistro, String pFechaSalida, 
                                                 String pHoraSalida, int pCodMotivo, String pObservacion,
                                                 String fechaSalida) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pFechaRegistro);
        parametros.add(pFechaSalida);
        parametros.add(pHoraSalida.trim());
        parametros.add(pCodMotivo);
        parametros.add(pObservacion);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(fechaSalida);//FECHA A GRABAR
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_UPD_MARC_SALIDA(?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros,false);
    }
   
    /**
     * Lista solicitudes por rango de fecha
     * @author ASOSA
     * @since 07/09/2015
     * @param pDias
     * @throws Exception
     * @return
     */
    public static ArrayList listarSolicitudes(int pDias) throws Exception {
        ArrayList lstSolicitudes = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDias);

            FarmaDBUtility.executeSQLStoredProcedureArrayList(lstSolicitudes,
                                                              "PTOVENTA_GEST_SOLICITUD.GEST_F_LIST_SOLICITUD(?,?,?)",
                                                              parametros);
        return lstSolicitudes;
    }

    /**
     * Obtiene los datos del solicitante mendiante el dni
     * @author:Cesar Huanes
     * @since19/09/2015
     * */

    public static ArrayList getDatosUsuario(String pDni) throws Exception{
        ArrayList lstUsuario = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstUsuario,
                                                          "PTOVENTA_GEST_SOLICITUD.GEST_F_VALIDA_USU(?,?,?)",
                                                          parametros);

        return lstUsuario;
    }

    /**
     * Lista los tipos de solicitud en el combobox
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static ArrayList listarTipoSolicitud() throws Exception{
        ArrayList lstTipoSolicitud = new ArrayList();
        parametros = new ArrayList();
        parametros.add(ConstantsControlAsistencia.COD_TIPO_SOLICITUD);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstTipoSolicitud,
                                                          "FARMA_GRAL.GET_MAESTRO_DETALLE_LISTA(?)", parametros);
        return lstTipoSolicitud;
    }

    /**
     * Lista los subtipos de solicitud en el combobox
     * @author CHUANES
     * @since 19/09/2015
     * @param pCodTipoSolic
     * @throws Exception
     * @return
     */
    public static ArrayList listarSubTipoSolic(String pCodTipoSolic) throws Exception{
        ArrayList lstSubTipoSolic = new ArrayList();
        parametros = new ArrayList();
        parametros.add(ConstantsControlAsistencia.COD_SUBTIPO_SOLICITUD);
        parametros.add(pCodTipoSolic);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstSubTipoSolic,
                                                          "PTOVENTA_GEST_SOLICITUD.GEST_F_LISTA_SUBTIPOS(?,?)",
                                                          parametros);

        return lstSubTipoSolic;
    }

    /**
     * Graba el registro de solicitudes
     * @author CHUANES
     * @since 19/09/2015
     * @param pDni
     * @param pFecInicio
     * @param pFecFin
     * @param pCodMaeTipo
     * @param pCodMaeTipo2
     * @param pNomArchivo
     * @param pObservacion
     * @throws SQLException
     * @return
     */
    public static String grabarSolicitud(String pDni, String pFecInicio, String pFecFin, String pCodMaeTipo,
                                         String pCodMaeTipo2,  String pNomArchivo,String pObservacion,
                                         String pFechaMarcacionNueva) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pFecInicio);
        parametros.add(pFecFin);
        
        parametros.add(pCodMaeTipo);
        parametros.add(pCodMaeTipo2);
        parametros.add(pNomArchivo);
        parametros.add(pFechaMarcacionNueva.trim());
        parametros.add(pObservacion);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.info("PTOVENTA_GEST_SOLICITUD.GEST_F_GRABA_SOLICITUD("+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GEST_SOLICITUD.GEST_F_GRABA_SOLICITUD(" +
                                                           "?,?,?,?,?," +
                                                           "?,?,?,?,?," +
                                                           "?,?)",
                                                           parametros);

    }

    /**
     * Verifica si existe cruce de fechas entre el mismo
     * @auhor CHUANES
     * @since 19/09/2015
     * @param pDni
     * @param pFecInicio
     * @param pFecFin
     * @param pCodMaeTipo
     * @throws SQLException
     * @return
     */
    public static String existeCruceFecha(String pDni, String pFecInicio, String pFecFin,
                                          String pCodMaeTipo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pFecInicio);
        parametros.add(pFecFin);
        parametros.add(pCodMaeTipo);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GEST_SOLICITUD.GEST_F_VALIDA_CRUCE_FECHA(?,?,?,?,?,?)",
                                                           parametros);
    }
    
    /**
     * Busca el registro de solicitudes por rango de fechas
     * @author CHUANES
     * @since 19.09.2015
     * @param pFechaInicio
     * @param pFechaFin
     * @param pEstSol
     * @throws Exception
     * @return
     */
    public static ArrayList lstSolicRangoFecha(String pFechaInicio, String pFechaFin, String pEstSol) throws Exception{
        ArrayList lstSolRangoFecha = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaInicio);
        parametros.add(pFechaFin);
        parametros.add(pEstSol);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(lstSolRangoFecha,
                                                          "PTOVENTA_GEST_SOLICITUD.GEST_F_LIST_SOLICITUD(?,?,?,?,?)",
                                                          parametros);

        return lstSolRangoFecha;
    }

    /**
     * Verifica si la fecha de inicio es hoy o futuro
     * @author:Cesar Huanes
     * @since19/09/2015
     * */

    /**
     * Verifica si la fecha de inicio es hoy o futuro
     * @author CHUANES
     * @since 19.09.2015
     * @param pFecInicio
     * @throws SQLException
     * @return
     */
    public static String isFechaInicioFuturo(String pFecInicio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pFecInicio);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GEST_SOLICITUD.GEST_F_VALIDA_FEC_INICIO(?)",
                                                           parametros);
    }

    /**
     * Verifica si la fecha de inicio es hoy o futuro
     * @author EMAQUERA
     * @since 03.12.2015
     * @param pFecInicio
     * @throws SQLException
     * @return
     */
    public static String isFechaCorrecion(String pFecInicio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pFecInicio);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GEST_SOLICITUD.GEST_F_VALIDA_FEC_CORRECION(?)",
                                                           parametros);
    }

    /**
     * Envia email de solicitud desde el local al supervisor de de zona.
     * @author:CHUANES
     * @since 19.09.2015
     * @param pNumRegistroSol
     * @throws SQLException
     */
    public static void enviarCorreoSolicitud(String pNumRegistroSol) throws SQLException {
        FarmaTableModel pTableModel = null;
        parametros = new ArrayList();
        parametros.add(pNumRegistroSol);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_AUTORIZA.AUT_P_ENVIA_MAIL_SOL(?)", parametros,
                                                 false);
    }

    /**
     * Obtiene los parametros de conexion para el envio
     * de archivo mendiante Ftp
     * @author:Cesar Huanes
     * @since19/09/2015
     * */
    public static String getParametrosFtp() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);

        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GEST_SOLICITUD.GEST_F_CNX_FTP(?,?,?)", parametros);

    }
    
    /**
     * Actualiza nombre del archivo
     * @author CHUANES
     * @since 19.09.2015
     * @param pNumRegistroSol
     * @param nomArchivo
     * @throws SQLException
     */
    public static void actualizaNomArchivo(String pNumRegistroSol, String nomArchivo) throws SQLException {
        FarmaTableModel pTableModel = null;
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRegistroSol);
        parametros.add(nomArchivo);      

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_GEST_SOLICITUD.GEST_P_UPD_FILENAME(?,?,?,?)",
                                                 parametros, false);


    }

    /**
     * Actualiza nombre del archivo
     * @author: Ever Maquera
     * @since : 23/09/2015
     * */
    
    /**
     * Envia las solicitudes creadas y que no se enviaron
     * @author: Ever Maquera
     * @since : 23/09/2015
     * @param pNumRegistroSol
     * @throws SQLException
     */
    public static void solicitudIncompleta(String pNumRegistroSol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);        
        parametros.add(pNumRegistroSol);  
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_GEST_SOLICITUD.GEST_P_ERROR_SOL(?,?,?)",
                                                 parametros, false);
    }
        
    public static void cargaListaFiltro(FarmaTableModel pTableModel, String pTipoFiltro) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pTipoFiltro);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_GRAL.LISTA_FILTROS(?)", parametros, false);
    }

    public static void cargaListaMaestros(FarmaTableModel pTableModel, String pTipoMaestro) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pTipoMaestro);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_GRAL.LISTA_MAESTROS(?)", parametros, false);
    }

    public static ArrayList cargaListaMaestrosArray(String pTipoMaestro) throws SQLException {
        parametros = new ArrayList();
        ArrayList rpta = new ArrayList();
        parametros.add(pTipoMaestro);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(rpta, "PTOVENTA_GRAL.LISTA_MAESTROS(?)", parametros);
        return rpta;
    }

    public static void buscaCodigoListaMaestro(ArrayList pArrayList, String pTipoMaestro,
                                               String pCodBusqueda) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pTipoMaestro);
        parametros.add(pCodBusqueda);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_GRAL.BUSCA_REGISTRO_LISTA_MAESTROS(?,?)",
                                                          parametros);
    }

    /**
     * Suguiere el tipo de marcación en control de ingreso
     * @author CHUANES
     * @since 30.07.2015
     * @param pArray
     * @param pDni
     * @throws SQLException
     */
    public static void getPersonal(ArrayList pArray, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.INGR_F_GET_PERSONAL(?,?,?)",
                                                          parametros);
    }

    /**
     * CHUANES
     * SE ADICIONA EL PARAMETRO INDICADOR EL CUAL INDICA LA FORMA DE MARCACION
     * 03.03.2015
     * **/
    public static void grabarRegistro(String pDni, String pTipo, String pCodCia, String pCodTrab, String pCodHora,
                                      String indicador) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pTipo);
        parametros.add(pCodCia);
        parametros.add(pCodTrab);
        parametros.add(pCodHora);
        parametros.add(indicador);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.GRABA_REG_PERSONAL(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaRegistros(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.GET_LISTA_REGISTROS(?,?)",
                                                 parametros, false);
    }

    /**
     * Lista la marcación  solo del usuario que marco su ingreso o salida
     * @author CHUANES
     * @since 20.07.2015
     * @param pTableModel
     * @param pDni
     * @throws SQLException
     */
    public static void cargaListaRegistrosDni(FarmaTableModel pTableModel, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.INGR_F_GET_LISTA_REG_DNI(?,?,?)",
                                                 parametros, false);
    }

    /**
     *
     * @param pDni
     * @throws SQLException
     * @since
     */
    public static void validarIngreso(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.GET_VALIDA_TARDANZA(?)", parametros, false);
    }


    //ACTUALIZA_DATOS_TRABAJADOR

    public static void ActualizarDatosIngreso(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.ACTUALIZA_DATOS_TRABAJADOR(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Verificamos la existencia del registro del trabajador en el dia
     * @author JCORTEZ
     * @since 03.10.2007
     * */
    public static void getRegistro(ArrayList pArray, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.TRA_EXIST_REGISTRO(?,?)",
                                                          parametros);
    }

    /**
     * valida que se registre la salida cuando sea necesaria
     * @author JCORTEZ
     * @since 03.10.2007
     * */
    public static void validaSalida(ArrayList pArray, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.TRA_VALIDA_SALIDA(?,?)",
                                                          parametros);
    }

    /**
     * Se lista el historico de temperaturas
     * @author JCORTEZ
     * @since 11.02.2009
     * */
    public static void cargaListaHistoricoTemp(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.LISTA_HISTORICO_TEMP(?,?)",
                                                 parametros, false);
    }


    /**
     * Se Ingresa temperatura
     * @throws SQLException
     * @author JCORTEZ
     * @since 12.02.2009
     */
    public static void ingresaTemperatura(String ValVta, String ValAlm, String ValRefrig) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(ValVta)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(ValAlm)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(ValRefrig)));
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.IND_INGRESA_TEMP(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * validar rol usuario
     * @param SecUsu
     * @param CodRol
     * @throws SQLException
     * @author Jorge Cortez Alvarez
     * @since 12.02.2009
     */
    public static String verificaRolUsuario(String SecUsu, String CodRol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRol);
        log.debug("verifica que el usuario tenga el rol adecuado: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_ROL_USU(?,?,?,?)", parametros);
    }

    /**
     * validar ingreso quimico en el dia
     * @param SecUsu
     * @param CodRol
     * @throws SQLException
     * @author Jorge Cortez Alvarez
     * @since 12.02.2009
     */
    public static String verificaIngrTemperatura(String SecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        log.debug("verifica ingreso de temperatura en el dia: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_INGR_TEMP_USU(?,?,?)",
                                                           parametros);
    }

    /**
     * validar ingreso quimico en el dia
     * @throws SQLException
     * @author Jorge Cortez Alvarez
     * @since 12.02.2009
     */
    public static String getSecUsuLocal(String Dni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Dni);
        log.debug("Obtiene sec_usu_local por dni " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.GET_SEC_USU_X_DNI(?,?,?)", parametros);
    }

    public static void ListarHistoricoTempFiltro(FarmaTableModel pTableModel, String cFecIni_in,
                                                 String cFecFin_in) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cFecIni_in);
        parametros.add(cFecFin_in);
        log.debug("HISTORICO DE TEMPERATURAS");
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.LISTA_HIST_FILTRO(?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * validar rol usuario
     * @param SecUsu
     * @param CodRol
     * @throws SQLException
     * @author Asolis
     * @since 25.02.2009
     */
    public static String ValidaRolTrabLocal(String SecUsu, String CodRolCajero, String CodRolVendedor,
                                            String CodRolAdministrador) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRolCajero);
        parametros.add(CodRolVendedor);
        parametros.add(CodRolAdministrador);
        log.debug("verifica que el usuario tenga el rol  de trabajador de local : " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_ROL_TRAB_LOCAL(?,?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Genera cupones de regalo
     * @throws SQLException
     * @author JCORTEZ
     * @since 18.08.2009
     */
    public static void generaCuponesRegalo(String NumDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumDni);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CUPON.CAJ_P_GENERA_CUPON_REGALO(?,?,?,?)", parametros,
                                                 false);
    }


    /**
     * Se obtiene los cupones emitidos por QF
     * @AUTHOR JCORTEZ
     * @AINCE  18.08.09
     */
    public static void obtieneCuponesRegalo(ArrayList cuponesRegalo, String vDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vDni);
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_CUR_CUP_REGALOS(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(cuponesRegalo, "PTOVENTA_CUPON.CUP_F_CUR_CUP_REGALOS(?,?,?)",
                                                          parametros);

    }


    /**
     * validar rol usuario
     * @author Asolis
     * @since 25.02.2009
     */
    public static String existCuponRegalo(String Dni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Dni);
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_VERI_EXIST_CUP(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CUPON.CUP_F_VERI_EXIST_CUP(?,?,?)", parametros);
    }

    /**
     * Indica si la marcacion de control de ingreso permite manual o electronico.
     * @author CHUANES
     * @since 03.03.2015
     * @throws SQLException
     * @return
     */
    public static String indicadorIngreso() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_IND_MARCACION",
                                                           parametros);
    }
    
    /**
     * Evalua si es administrador del local
     * @author CHUANES
     * @since 30.07.2015
     * @param pDni
     * @throws SQLException
     * @return
     */
    public static String validaRolAdministrador(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        log.debug("invocando a PTOVENTA_INGR_PERS.INGR_F_ADMINISTRADOR_LOCAL(?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_ADMINISTRADOR_LOCAL(?,?,?,?)",
                                                           parametros);

    }

    /**
     * Valida la marcacion de salida
     * @author DUBILLUZ
     * @since 09.08.2015
     * @throws SQLException
     * @return
     */
    public static String isDebeMarcarSalida() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VALIDA_MARC_SALIDA",
                                                           parametros);

    }
    
    /**
     * Mensaje de bienvenida de la nueva pantalla de marcacion de asistencia.
     * @author CHUANES
     * @since 30.07.2015
     * @param pIndFormMarcacion
     * @throws SQLException
     * @return
     */
    public static String msgBienvenida(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_MSG_BIENVENIDA(?,?,?)",
                                                           parametros);
    }

    /**
     * Indica si se puede ingresar DNI digitando
     * @author ASOSA
     * @since 03/12/2015
     * @throws SQLException
     * @return
     */
    public static String indDniIngreso() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_GET_IND_DNI_ING",
                                                                           parametros);
    }
    
    /**
     * Indica si esta activa la marcacion de huella
     * @author ASOSA
     * @since 03/12/2015
     * @throws SQLException
     * @return
     */
    public static String indHuella() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_GET_IND_HUELLA",
                                                                           parametros);
    }
    
    
    
    /**
     * Obtener hora de sistema
     * @author CHUANES
     * @since 27.08.2015
     * @throws SQLException
     * @return
     */
    public static String getHoraMarcacion() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_GET_HORA_SIST", parametros).trim();

    }
    
    /**
     * Cuando debe mostrar la pantalla de marcacion de entrada.
     * @author CHUANES
     * @since 27.08.2015
     * @throws SQLException
     * @return
     */
    public static String isDebeMarcarEntrada() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_MARCAR_ENTRADA(?,?,?)",
                                                           parametros).trim();
    }

    /**
     * VERIFICA SI EL USUARIO LOGEADO ES EL QUE ESTA REALIZANDO LA MARCACION
     * @author CHUANES
     * @since 27.08.2015
     * @param pDni
     * @throws SQLException
     * @return
     */
    public static String isUsuarioLogeado(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pDni);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_USU_LOGEADO(?,?,?,?)",
                                                           parametros).trim();
    }

    /**
     * VERIFICA SI EL USUARIO LOGEADO ES EL QUE ESTA REALIZANDO LA MARCACION
     * @author CHUANES
     * @since 09.09.2015
     * @throws SQLException
     * @return
     */
    public static String indActivaMarcacion() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_IND_ACT_MARCACION", parametros);
    }

    /**
     * Obtiene los turnos que tiene solicitudes aprobadas
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param vLista
     * @param pFechaInicio
     * @param pFechaFin
     * @throws SQLException
     */
    public static void getTurnoRangoFechaSolicitudAprobada(ArrayList vLista,
                                                           String pFechaInicio,
                                                           String pFechaFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaInicio.trim());
        parametros.add(pFechaFin.trim());
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_TURNO_X_SOL_APR(?,?,?,?)",
                                                          parametros);
    }

    /**
     * Verifica si se validara la marcación de usuario
     * @author DUBILLUZ
     * @since 16.09.2015
     * @throws SQLException
     * @return
     */
    public static String validaSolicitud(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VALIDA_MARC_USU(?,?,?)", parametros);
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
    public static String getIndFaltaTemperatura(String pFecha) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecha);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_IND_FALTA_TEMP(?,?,?)",
                                                           parametros);
    }

    /**
     * Obtiene los datos completos de un usuario de local
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param pSecUsu
     * @throws SQLException
     * @return
     */
    public static String getDatosUsuarioLocal(String pSecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_DATOS_USU(?,?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_DATOS_USU(?,?,?)",
                                                           parametros);
    }

    /**
     * Verifica si se validara la asistencia si el usuario tiene cargo a validar.
     * @author DUBILLUZ
     * @since 16.09.2015
     * @param pSecUsu
     * @throws SQLException
     * @return
     */
    public static String isValidaHoraSemanalCargo(String pSecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_IS_CARGO_VALIDA(?,?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_VAR_IS_CARGO_VALIDA(?,?,?)",
                                                           parametros);
    }

    /**
     * Duplica una plantilla
     * @author DUBILLUZ
     * @since 16.09.2015
     * @throws SQLException
     */
    public static void saveCopiaPlantilla(String pCodPlantilla) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodPlantilla.trim());
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONTROL_ASISTENCIA.CTRL_P_SAVE_COPIA_PLANTILLA(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String isValidaAvisoFinHorario() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_P_AVISO_FIN_HORARIO(?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_P_AVISO_FIN_HORARIO(?,?)",
                                                           parametros);
    }    
    
    public static String isRepiteValidaAvisoFinHorario() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_IND_REPITE_VALIDACION(?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_IND_REPITE_VALIDACION(?,?)",
                                                           parametros);
    }    
     

    public static String obtieneFecSalidaSug(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_HORARIO.CTRL_F_VERIF_DUPLICADO_UPD(?,?,?)",
                                                           parametros);
    }
    
    /**
     * Obtiene el indicador del mensaje a mostrar
     * @author EMAQUERA
     * @since 22.10.2015
     * @param pDni
     * @throws SQLException
     * @return
     */
    public static String obtieneIndMsje(String pDni,
                                        String pFechaSalida,
                                        String pMsg) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INGR_F_GET_IND_MSJE(?,?,?)",
                                                           parametros);        
    }
    
    
    public static String getMensajeFrecuenteIngreso() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_MSJ_FRECUENTE_MARCACION(?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_MSJ_FRECUENTE_MARCACION(?,?)",
                                                           parametros);
    }
    
    public static void getListaPorCese(ArrayList vLista) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,
                                                          "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LIST_CESE(?,?)",
                                                          parametros);
    }

    /**
     * Inserta o Actualiza Registro de Ingreso y/o Salida
     * @author EMAQUERA
     * @since 26.10.2015
     * @param pDni
     * @param pTipo
     * @param indicador
     * @throws SQLException
     */
    public static void grabaRegularizacionSalida(String pDni, 
                                        String pFechaHoraSalida) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pFechaHoraSalida);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.INGR_P_REGULARIZAR_SALIDA(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Listar el registro de inasistencias
     * @auhor EMAQUERA
     * @since 28.10.2015
     * @throws Exception
     * @return
     */
    public static ArrayList lstInasistencias() throws Exception {
        ArrayList lstInasistencias = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

            FarmaDBUtility.executeSQLStoredProcedureArrayList(lstInasistencias,
                                                              "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_LIST_INASISTENCIA(?,?)",
                                                              parametros);
        return lstInasistencias;
    }    
    
    public static void procesaReporteHorario(String pCodHorario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodHorario.trim());
        log.debug("Farma_Gral.P_CUR_DATOS_REP_ASISTENCIA(?,?,?)>> "+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "Farma_Gral.P_CUR_DATOS_REP_ASISTENCIA(?,?,?)",
                                                 parametros, false);
    }
    
    public static void getReporteDia(String pCadenaDia,ArrayList vListaL,String pCodHorario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodHorario.trim());
        parametros.add(pCadenaDia.trim());
        log.debug("Farma_Gral.F_CUR_RPT_ASISTENCIA_x_DIA(?,?,?,?)>> "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vListaL, "Farma_Gral.F_CUR_RPT_ASISTENCIA_x_DIA(?,?,?,?)",
                                                 parametros);
    }   

}
