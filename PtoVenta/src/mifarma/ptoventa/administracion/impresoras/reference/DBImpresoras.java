package mifarma.ptoventa.administracion.impresoras.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaIPSImpresora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCHAVEZ 26.06.2009 Modificación<br>
 * <br>
 * @version 1.0<br>
 *
 */
public class DBImpresoras {

    private static final Logger log = LoggerFactory.getLogger(DBImpresoras.class);

    public DBImpresoras() {
    }

    private static ArrayList parametros = new ArrayList();

    public static void getListaImpresoras(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_IMP.IMP_LISTA_IMPRESORAS(?,?)",
                                                 parametros, false);
    }

    //JMIRANDA 25/06/2009 LISTA IMPRESORAS TERMICAS

    public static void getListaImpresorasTermicas(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //cambiar REFer PACKAGE
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_IMP.IMP_F_CUR_LISTA_IMP_TERMICAS(?,?)",
                                                 parametros, false);
    }

    public static void getListaAsigCajasImpresoras(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_IMP.IMP_LISTA_ASIGNACIONES_CAJA_IMPR(?,?)",
                                                 parametros, false);
    }

    public static void ingresaImpresora(String pCodGrupoCia, String pCodLocal, String pNumSerieLocal, String pTipComp,
                                        String pDescImprLocal, String pNumComp, String pRutaImp, String pModelo,
                                        String pSerieImpr) throws SQLException {

        parametros = new ArrayList();

        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumSerieLocal);
        parametros.add(pTipComp);
        parametros.add(pDescImprLocal);
        parametros.add(pNumComp);
        parametros.add(pRutaImp);
        parametros.add(pModelo);
        parametros.add(pSerieImpr);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_ADMIN_IMP.IMP_INGRESA_IMPRESORA(?,?,?,?,?,?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_INGRESA_IMPRESORA(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }

    //JMIRANDA  30/06/2009
    
    public static void ingresaImpresoraTermica(String pCodGrupoCia, String pCodLocal, String pDescImprLocalTerm,
                                               String pTipoImprTermica, String pEstImprLocal) throws Exception {
        ingresaImpresoraTermica(pCodGrupoCia, pCodLocal, pDescImprLocalTerm, pTipoImprTermica, pEstImprLocal, "");
    }
    public static void ingresaImpresoraTermica(String pCodGrupoCia, String pCodLocal, String pDescImprLocalTerm,
                                               String pTipoImprTermica, String pEstImprLocal, String nroPisoDespacho) throws Exception {

        ArrayList parametros = new ArrayList();

        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);

        parametros.add(pDescImprLocalTerm);
        parametros.add(pTipoImprTermica);

        parametros.add(pEstImprLocal);
        parametros.add(FarmaVariables.vIdUsu);
        if(nroPisoDespacho!=null && nroPisoDespacho.trim().length()>=0){
            parametros.add(nroPisoDespacho);
        }else{
            parametros.add(" ");
        }
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_P_INSERT_IMP_TERMICA(?,?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static void modificaImpresora(String pCodGrupoCia, String pCodLocal, String pSecImprLocal,
                                         String pNumSerieLocal, String pTipComp, String pDescImprLocal,
                                         String pNumComp, String pRutaImp, String pModelo,
                                         String pSerieImpr) throws SQLException {

        parametros = new ArrayList();

        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(new Integer(pSecImprLocal));
        parametros.add(pNumSerieLocal);
        parametros.add(pTipComp);
        parametros.add(pDescImprLocal);
        parametros.add(pNumComp);
        parametros.add(pRutaImp);
        parametros.add(pModelo);
        parametros.add(pSerieImpr);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_ADMIN_IMP.IMP_MODIFICA_IMPRESORA(?,?,?,?,?,?,?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_IMP.IMP_MODIFICA_IMPRESORA(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }

    //JMIRANDA 30/06/2009

    public static void modificaImpresoraTermica(String pCodGrupoCia, String pCodLocal, String pSecImprLocTerm,
                                                String pDescImprLocalTerm, String pTipoImprTermica,
                                                String pEstImprLocal)throws Exception{
        modificaImpresoraTermica(pCodGrupoCia, pCodLocal, pSecImprLocTerm, pDescImprLocalTerm, pTipoImprTermica, pEstImprLocal, "");
    }
    
    public static void modificaImpresoraTermica(String pCodGrupoCia, String pCodLocal, String pSecImprLocTerm,
                                                String pDescImprLocalTerm, String pTipoImprTermica,
                                                String pEstImprLocal, String nroPisoDespacho)

        throws Exception {

        ArrayList parametros = new ArrayList();
        /* (cod_grupo_cia, cod_local, sec_impr_loc_term,
              desc_impr_local_term, tipo_impr_termica,
              est_impr_local, fec_crea_impr_local, usu_crea_impr_local,
              fec_mod_impr_local, usu_mod_impr_local) */
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(new Integer(pSecImprLocTerm));
        parametros.add(pDescImprLocalTerm);
        parametros.add(pTipoImprTermica);
        parametros.add(pEstImprLocal);
        parametros.add(FarmaVariables.vIdUsu);
        if(nroPisoDespacho!=null && nroPisoDespacho.trim().length()>=0){
            parametros.add(nroPisoDespacho);
        }else{
            parametros.add(" ");
        }
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_P_UPDATE_IMP_TERMICA(?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static void cambiaEstadoImpresora(String pCodGrupoCia, String pCodLocal,
                                             String pSecImprLocal) throws SQLException {

        parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(new Integer(pSecImprLocal));
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_CAMBIA_ESTADO_IMPRESORA(?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaTipComprobante(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_IMP.IMP_LISTA_IMPRESORAS(?,?)",
                                                 parametros, false);
    }


    public static String getExistRuta(String TipComp, String Ruta, String Secimpr) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(TipComp);
        parametros.add(Ruta);
        parametros.add(new Integer(Secimpr));
        log.debug("" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_RUTA_EXIST(?,?,?,?,?)",
                                                           parametros);
    }
    //JMIRANDA 26/06/2009

    public static String getExistImpTermica(String descImpTerm) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(descImpTerm);

        log.debug("" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_F_VAR2_EXIST_IMP_TERMICA(?,?,?)",
                                                           parametros);
    }

    /**
     * @author JCORTEZ
     * @since 08.06.09
     * Se lista las IP registradas
     * */
    public static void getListaIPs(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_IMP.IMP_LISTA_IP(?,?)", parametros,
                                                 false);
    }

    /**
     * @author JCORTEZ
     * @since 08.06.09
     * Se ingresa una nueva IP para la relacion maquina - Ticket
     * */
    public static void ingresaIp(String IP) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(IP);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_INGRESA_IP(?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * @author JCORTEZ
     * @since 08.06.09
     * Se elimina IP para la relacion maquina - Ticket
     * */
    public static void eliminaIP(String Sec, String IP) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(Sec);
        parametros.add(IP);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_ELIMINA_IP(?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * @author JCORTEZ
     * @since 08.06.09
     * Se lista las impresoras disponibles
     * */
    public static void getListaImpr(FarmaTableModel pTableModel, String SecIP, String tipoComp) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecIP.trim());
        parametros.add(tipoComp);
        log.debug("parametros -->" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_IMP.IMP_LISTA_IMP(?,?,?,?)", parametros,
                                                 false);
        log.debug("pTableModel -->" + pTableModel);
    }


    /**
     * @author JCORTEZ
     * @since 08.06.09
     * Se actualiza la relacion maquina - Ticket
     * */
    public static void actualizaRelacion(String SecIp, String SecImpr, String NumSer, String TipComp,
                                         String indTipoComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(SecIp.trim());
        parametros.add(SecImpr.trim());
        parametros.add(NumSer.trim());
        parametros.add(TipComp.trim());
        parametros.add(indTipoComp.trim());
        log.debug("Parametros -->" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_ACTUALIZA_IP(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * @author JCORTEZ
     * @since 08.06.09
     * Se elimina la relacion maquina - Ticket
     * */
    public static void quitarAsignacion(String SecIp, String indTipoComp) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(SecIp.trim());
        parametros.add(indTipoComp.trim());
        log.debug("Parametros -->" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_QUITAR_IMPR(?,?,?,?,?)", parametros,
                                                 false);

    }

    /**
     * @author JCHAVEZ
     * @since 25.06.09
     * Se lista las impresoras térmicas disponibles
     * */
    public static void getListaImpresoraTermica(FarmaTableModel pTableModel, String pSecIP) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecIP.trim());
        log.debug("parametros -->" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_IMP.IMP_F_CUR_LISTA_IMP_TERMICA(?,?,?)",
                                                 parametros, false);
        log.debug("pTableModel -->" + pTableModel);
    }

    /**
     * @author JCHAVEZ
     * @since 25.06.09
     * Se lista las impresoras térmicas disponibles
     * */
    public static void actualizaRelacionIPImprTermica(String pSecIP, String pSecImpTerm) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pSecIP.trim());
        parametros.add(pSecImpTerm.trim());
        log.debug("ACTUALIZA RELACION IPxIMPTermica PTOVENTA_ADMIN_IMP.IMP_P_UPDATE_IP_IMP_TERMICA(?,?,?,?,?): " +
                  parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.IMP_P_UPDATE_IP_IMP_TERMICA(?,?,?,?,?)",
                                                 parametros, false);

    }

    /**
     * Obtiene el modelo de la impresora (Ticketera)
     * @author ERIOS
     * @since 13.06.2013
     * @param pSecImpr
     * @return
     */
    public static String getModeloImpresora(String pSecImpr) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        if (pSecImpr == null || "".equalsIgnoreCase(pSecImpr))
            parametros.add(0);
        else
            parametros.add(new Integer(pSecImpr.trim()));
        log.debug("PTOVENTA_ADMIN_IMP.IMP_GET_MODELO(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_MODELO(?,?,?,?)", parametros);
    }

    /**
     * Obtiene el las impresoras configuradas para una IP
     * @author LLEIVA
     * @since 26.Feb.2013
     */
    public static void getImpresorasIP(String ip, ArrayList respuesta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(ip);

        log.debug("PTOVENTA_ADMIN_IMP.IMP_IMPRESORAS_IP(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(respuesta, "PTOVENTA_ADMIN_IMP.IMP_IMPRESORAS_IP(?,?,?)",
                                                          parametros);
    }

    /**
     * @author:CESAR HUANES
     * @since 01/09/2014
     * Obtiene la ruta de impresora termica asignada ala maquina .
     * */

    public static String getNombreImpTermica() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIpPc);
        log.debug("PTOVENTA_CAJ.GET_NOMBRE_IMPRES_TERMICA(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.GET_NOMBRE_IMPRES_TERMICA(?,?,?)",
                                                           parametros);
    }

    /**
     * @author:CESAR HUANES
     * @since 01/09/2014
     * Obtiene el modelo de  termica asignada ala maquina .
     * */
    public static String getModeloImpTermica() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIpPc);
        log.debug("PTOVENTA_CAJ.GET_MODELO_IMP_TERMICA(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.GET_MODELO_IMP_TERMICA(?,?,?)", parametros);
    }

    /**
     * @author:CESAR HUANES
     * @since 09/09/2014
     * Indicador si el comprobante es electronico.
     * */

    public static String getIndicCompElectronico(String pNumPedVta, String pSecComPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pSecComPago);
        log.debug("FARMA_EPOS.FN_INDICADOR_ELECTRONICO(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.FN_INDICADOR_ELECTRONICO(?,?,?,?)", parametros);

    }

    /**
     * @author:CESAR HUANES
     * @since 09/09/2014
     * Cuenta la cantidad de Documentos electronicos por Numero de Pedido.
     * */
    public static Integer getCantidadComElectornico(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        return Integer.parseInt(FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.FN_CONTAR_DOCELECTRONICO(?,?,?)",
                                                                            parametros));
    }
    
    /**
     * Obtiene los datos de la impresora termica configurada a la PC.
     * 
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pIpPc
     * @return Tipo y Nombre de Impresora Termica
     * @since 13.01.2015 KMONCADA
     * @throws Exception
     */
    public static List getDatosImpresoraTerminca(String pCodGrupoCia, String pCodLocal, String pIpPc) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pIpPc);
        log.info("PTOVENTA_CAJ.GET_IMPRESORA_TERMICA(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_CAJ.GET_IMPRESORA_TERMICA(?,?,?)", parametros);

    }
    

    

    public static String validaAutentica(String pCodGrupoCia, String pCodLocal, 
                                         String pSecImprLocal,String pNumComp) throws SQLException {                      
        parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(new Integer(pSecImprLocal));
        parametros.add(pNumComp);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("PTOVENTA_ADMIN_IMP.IMP_P_VALIDA_CAMBIO_COMP(?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
                                                 "PTOVENTA_ADMIN_IMP.IMP_P_VALIDA_CAMBIO_COMP(?,?,?,?,?)",
                                                 parametros);

    }

    public static String maxComp(String pCodGrupoCia, String pCodLocal, String pSecImprLocal) throws SQLException {                      
        parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(new Integer(pSecImprLocal));
        log.debug("PTOVENTA_ADMIN_IMP.IMP_F_VAR_MAX_COMP(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
                                                 "PTOVENTA_ADMIN_IMP.IMP_F_VAR_MAX_COMP(?,?,?)",
                                                 parametros);

    }
    
    public static void listarTipoLocalVenta(FarmaTableModel pTableModel, String pCodGrupoCia, String pCodLocal, String pIPpc)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pIPpc);
        log.info("PTOVENTA_ADMIN_IMP.F_GET_TIPO_LOCAL_VENTA(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_IMP.F_GET_TIPO_LOCAL_VENTA(?,?,?)", parametros, false);
    }
    
    public static void actualizarIpTipoLocalVenta(String pCodGrupoCia, String pCodLocal, String pIpPc, String pCodTipoLocalVenta, String pUsuaroMod)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pIpPc);
        parametros.add(pCodTipoLocalVenta);
        parametros.add(pUsuaroMod);
        log.info("PTOVENTA_ADMIN_IMP.F_GET_TIPO_LOCAL_VENTA(?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_IMP.P_ACT_IP_TIPO_LOCAL_VTA(?,?,?,?,?)", parametros, false);
    }
    
}
