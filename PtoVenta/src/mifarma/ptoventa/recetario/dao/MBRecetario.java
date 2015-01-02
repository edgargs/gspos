package mifarma.ptoventa.recetario.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MBRecetario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      15.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class MBRecetario implements DAORecetario {

    private static final Logger log = LoggerFactory.getLogger(MBRecetario.class);
    private SqlSession sqlSession = null;
    private MapperRecetario mapper = null;
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

    public MBRecetario() {
        super();
    }

    public ArrayList<ArrayList<String>> listarFormaFarmaceutica() {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        getListaFormaFarmac(mapParametros);
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    public void listarInsumosFarma(FarmaTableModel pTableModel) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        getListaInsumos(mapParametros);
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        utilityPtoVenta.parsearResultado(lstRetorno, pTableModel, true);
    }

    public ArrayList<ArrayList<String>> obtenerInfInsumo(String pCodInsumo) {
        List<BeanResultado> tmpLista = null;
        ArrayList<ArrayList<String>> tmpArray;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCod_Insumo_in", pCodInsumo);
        getInformacionInsumo(mapParametros);
        tmpLista = (List<BeanResultado>)mapParametros.get("listado");
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        return tmpArray;
    }

    public String grabarCabeRM(String strCodGrupoCia, String strCodCia, String strCodLocal, String pIndEsteril,
                               String pCodFormaFarmac, String pCantContenido, String pCantPreparados,
                               String pFecRecetario, String pCodPaciente, String pCodMedico, String pFecEntrega,
                               String pHoraEntrega, String pCodLocalEntrega, String pObsRecetario,
                               String pValBrutoRecetario, String pEstPedidoCab, String pUsuCrea, String pFecCrea,
                               String pNumTelefono, String pNumGuia, double pValIgv, double pValVenta,
                               double pvalVentaSR) {

        String tmpCodigo = "";
        openConnection();

        try {
            Map<String, Object> mapParametros = new HashMap<String, Object>();

            mapParametros.put("cCodGrupoCia_in", strCodGrupoCia);
            mapParametros.put("cCod_Cia_in", strCodCia);
            mapParametros.put("cCod_Local_in", strCodLocal);
            mapParametros.put("cInd_Esteril_in", pIndEsteril);
            mapParametros.put("cCod_Forma_Farmac_in", pCodFormaFarmac);
            mapParametros.put("nCant_Contenido_in", new Float(pCantContenido.replace(",", "")));
            mapParametros.put("nCant_Preparados_in", new Float(pCantPreparados.replace(",", "")));
            mapParametros.put("cFec_Recetario_in", pFecRecetario);
            mapParametros.put("cCod_Paciente_in", pCodPaciente);
            mapParametros.put("cCod_Medico_in", pCodMedico);
            mapParametros.put("cFec_Entrega_in", pFecEntrega);
            mapParametros.put("cHora_Entrega_in", pHoraEntrega);
            mapParametros.put("cCod_Local_Entrega_in", pCodLocalEntrega);
            mapParametros.put("cObs_Recetario_in", pObsRecetario);
            mapParametros.put("nVal_Bruto_Recetario_in", new Float(pValBrutoRecetario.replace(",", "")));
            mapParametros.put("cEst_Pedido_Cab_in", pEstPedidoCab);
            mapParametros.put("cUsu_Crea_in", pUsuCrea);
            mapParametros.put("cFec_Crea_in", pFecCrea);
            mapParametros.put("cNum_Telefono_in", pNumTelefono);
            mapParametros.put("cNum_Guia_in", pNumGuia);
            mapParametros.put("nValIgvRecetario_in", pValIgv);
            mapParametros.put("nValVentaRecetario_in", pValVenta);
            mapParametros.put("vValVentaRecSinRed_in", pvalVentaSR);

            mapper.grabaCabRM(mapParametros);
            tmpCodigo = (String)mapParametros.get("vIndicador");
            log.debug("Codigo Correlativo => " + tmpCodigo);
        } catch (Exception e) {
            log.error("", e);
        }
        return tmpCodigo;
    }

    public void grabarDetaRM(String strCodGrupoCia, String strCodCia, String strCodLocal, String pNumRecetario,
                             String pCodInsumo, Float pCantAtendida, Float pValPrecRec, Float pValPrecTotal,
                             String pUsuCrea, String pFecCrea, String pCodUnidadVenta, Float pCantPorcentaje) {

        Map<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", strCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);
        mapParametros.put("cNum_Recetario_in", pNumRecetario);
        mapParametros.put("nCod_Insumo_in", pCodInsumo);
        mapParametros.put("nCant_Atendida_in", pCantAtendida);
        mapParametros.put("cVal_Prec_Rec_in", pValPrecRec);
        mapParametros.put("cVal_Prec_Total_in", pValPrecTotal);
        mapParametros.put("cUsu_Crea_in", pUsuCrea);
        mapParametros.put("cFec_Crea_in", pFecCrea);
        mapParametros.put("cCodUnidadVenta_in", pCodUnidadVenta);
        mapParametros.put("cCant_Porcentaje_in", pCantPorcentaje);

        mapper.grabaDetaRM(mapParametros);
    }

    public ArrayList<ArrayList<String>> listarGuiasRM() {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            getListaGuiaRM(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        } catch (Exception e) {
            log.error("", e);
        }
        return lstListado;
    }

    public String grabarGuiaRM(String cCodGrupoCia_in, String cCod_Cia_in, String cCod_Local_in, String cnumguiatran,
                               String cusu_crea_in) {
        String tmpCodigo = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", cCodGrupoCia_in);
        mapParametros.put("cCod_Cia_in", cCod_Cia_in);
        mapParametros.put("cCod_Local_in", cCod_Local_in);
        mapParametros.put("cNum_Ord_Prep_in", cnumguiatran);
        mapParametros.put("cUsu_Crea_in", cusu_crea_in);
        try {
            openConnection();
            mapper.grabarGuiaRM(mapParametros);
            tmpCodigo = (String)mapParametros.get("vIndicador");
            commit();
        } catch (Exception e) {
            rollback();
            log.error("", e);
        }
        return tmpCodigo;
    }

    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }

    public String htmlContrasenia(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                  String pNumeroRecetario) throws SQLException {

        String strHtml = "";

        Map<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);
        mapParametros.put("cNum_Recetario_in", pNumeroRecetario);

        getHtmlContrasenia(mapParametros);

        strHtml = (String)mapParametros.get("vIndicador");

        return strHtml;
    }

    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperRecetario.class);
    }

    private void getInformacionInsumo(Map<String, Object> object) {
        openConnection();
        try {
            mapper.getInformacionInsumo(object);
        } finally {
            sqlSession.close();
        }
    }

    private void getListaInsumos(HashMap<String, Object> object) {
        openConnection();
        try {
            mapper.getListaInsumos(object);
        } finally {
            sqlSession.close();
        }
    }

    private void getListaFormaFarmac(HashMap<String, Object> object) {
        openConnection();
        try {
            mapper.getListaFormaFarmac(object);
        } finally {
            sqlSession.close();
        }
    }

    private void getHtmlContrasenia(Map<String, Object> object) {
        openConnection();
        try {
            mapper.getHtmlContrasenia(object);
        } finally {
            sqlSession.close();
        }
    }

    private void getListaGuiaRM(HashMap<String, Object> object) {
        openConnection();
        try {
            mapper.getListaGuiasRM(object);
        } finally {
            sqlSession.close();
        }
    }


    public double getPorcIgv(String pCodIgv) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        openConnection();
        try {
            mapParametros.put("cCodIgv_in", pCodIgv);
            mapper.getPorcIgv(mapParametros);
        } finally {
            sqlSession.close();
        }

        return (Double)mapParametros.get("vIndicador");
    }


    public ArrayList<ArrayList<String>> listarProdVirtual() {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            openConnection();
            mapper.getListarProdVirtual(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return lstListado;
    }

    public ArrayList<ArrayList<String>> listadoUnidadesRelacionadas(String pCodUnidad) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("ccodunidmed_in", pCodUnidad);
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            openConnection();
            mapper.getListarUnidadesRelac(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return lstListado;
    }


    /**
     * Obtiene el numero de recetario de acuerdo a un numero de pedido
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public HashMap<String, String> getNumeroRecetario(String numPedido) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCod_Cia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCod_Local_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedido_in", numPedido);

        HashMap<String, String> resultado = new HashMap<String, String>();
        try {
            openConnection();
            mapper.getNumeroRecetario(mapParametros);
            String vResulta = (String)mapParametros.get("listado");
            String[] dividido = vResulta.split("Ã");
            resultado.put("NUM_RECETARIO", dividido[0].trim());
            resultado.put("EST_RECETARIO", dividido[1].trim());
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return resultado;
    }

    /**
     * Obtiene una trama formateada de informacion de recetario
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public String getTramaRecetario(String numRecetario) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        String resultado = "";
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCod_Cia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCod_Local_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNum_Recetario_in", numRecetario);
        try {
            openConnection();
            mapper.getTramaRecetario(mapParametros);
            resultado = (String)mapParametros.get("trama");
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return resultado;
    }

    /**
     * Envia la trama anteriormente obtenida hacia el servidor de FASA
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public String enviaTramaRecetario(String trama) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        String resultado = "";
        mapParametros.put("Entrada", trama);
        mapParametros.put("Salida", "");
        try {
            sqlSession = MyBatisUtil.getFasaSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperRecetario.class);
            mapper.enviaTramaRecetario(mapParametros);
            resultado = (String)mapParametros.get("Salida");
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return resultado;
    }

    /**
     * Obtiene el estado del recetario adjunto a un pedido
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public String getEstadoRecetarioPedido(String numPedido) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        String resultado = "";
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCod_Cia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCod_Local_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNum_Pedido_in", numPedido);
        try {
            openConnection();
            mapper.getEstadoRecetarioPedido(mapParametros);
            resultado = (String)mapParametros.get("estado");
        } catch (Exception e) {
            log.error("", e);
            resultado = "";
        } finally {
            sqlSession.close();
        }
        return resultado;
    }

    /**
     * Anular el recetario magistral (si es que existe) de un pedido indicado
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public void anularRecetarioPedido(String numPedido) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCod_Cia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCod_Local_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNum_Pedido_in", numPedido);
        try {
            openConnection();
            mapper.anuladoRecetarioPedido(mapParametros);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
    }

    public String getFactorConversion(String codProducto, String codUnidadVenta) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        String resultado = "";
        mapParametros.put("cCod_Producto", codProducto);
        mapParametros.put("cCod_UnidadVenta", codUnidadVenta);
        try {
            openConnection();
            mapper.getFactorConversion(mapParametros);
            resultado = (String)mapParametros.get("vFactor");
        } catch (Exception e) {
            log.error("", e);
            resultado = "";
        } finally {
            sqlSession.close();
        }
        return resultado;
    }

    /**
     * obtiene la trama de actualizacion de Recetarios magistrales para el envio hacia el servidor de FASA
     * @author LLEIVA
     * @since 28.Junio.2013
     */
    public String getTramaActualizaRecetFasa(String numPedido, String accion) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        String resultado = "";
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCod_Cia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCod_Local_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNum_Recetario_in", numPedido);
        mapParametros.put("cAccion_in", accion);
        try {
            openConnection();
            mapper.getTramaActualizaRecetarioFasa(mapParametros);
            resultado = (String)mapParametros.get("resultado");
        } catch (Exception e) {
            log.error("", e);
            resultado = "";
        } finally {
            sqlSession.close();
        }
        return resultado;
    }

    /**
     * Envia la trama anteriormente obtenida hacia el servidor de FASA
     * @author LLEIVA
     * @since 01.Julio.2013
     */
    public String enviaTramaActualizaRecetFasa(String trama) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        String resultado = "";
        mapParametros.put("pmensaje", trama);
        try {
            sqlSession = MyBatisUtil.getFasaSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperRecetario.class);
            mapper.enviaActualizaRecetarioFasa(mapParametros);
            resultado = (String)mapParametros.get("resultado");
        } catch (Exception e) {
            log.error("", e);
            resultado = "";
        } finally {
            sqlSession.close();
        }
        return resultado;
    }


    public ArrayList<ArrayList<String>> reporteRecetario(String fecha_inicial, String fecha_final,
                                                         String nombre_paciente, String num_recetario,
                                                         String num_pedido) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cFecInicial_in", fecha_inicial);
        mapParametros.put("cFecFinal_in", fecha_final);
        mapParametros.put("cPaciente_in", nombre_paciente);
        mapParametros.put("cNumRecetario_in", num_recetario);
        mapParametros.put("cNumPedido_in", num_pedido);

        ArrayList<ArrayList<String>> lstListado = null;
        try {
            openConnection();
            mapper.reporteRecetario(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return lstListado;
    }

    @Override
    public ArrayList<ArrayList<String>> listadoDetalleRecetario(String num_recetario) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumRecetario_in", num_recetario);

        ArrayList<ArrayList<String>> lstListado = null;
        try {
            openConnection();
            mapper.listadoDetalleReporteRecetario(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return lstListado;
    }

    /**
     * Obtiene detalle de reporte de Recetario Magistral
     * @author LLEIVA
     * @since 27.Sep.2013
     */
    public ArrayList<ArrayList<String>> detalleReporteRecetario(String num_recetario) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumRecetario_in", num_recetario);

        ArrayList<ArrayList<String>> lstListado = null;
        try {
            openConnection();
            mapper.detalleReporteRecetario(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return lstListado;
    }
}
