package mifarma.ptoventa.caja.dao;


import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MBCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class MBCaja implements DAOCaja {

    private static final Logger log = LoggerFactory.getLogger(MBCaja.class);

    private SqlSession sqlSession;
    private MapperCaja mapper;
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

    @Override
    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    @Override
    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperCaja.class);
    }


    /**
     * REGISTRAR UNA RECARGA MOVISTAR PARA SER PROCESADA POR EL SIX
     * @author GFonseca
     * @since 12.08.2013
     * @param mapParametros
     */
    public Long registrarTrsscRecarga(String pCodGrupoCia, String strCodCia, String strCodLocal, String strTipMsjRecau,
                                      String strEstTrsscRecau, String strTipoTrssc, String strTipoRcd, String strMonto,
                                      String strTerminal, String strComercio, String strUbicacion,
                                      String strNroTelefono, String strNumPedido, String strUsuario) throws Exception {
        Long tmpCodigo = null;
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);

        mapParametros.put("cTip_Msj_in", strTipMsjRecau);
        mapParametros.put("cEst_Trscc_in", strEstTrsscRecau);
        mapParametros.put("cTipo_Trssc_in", strTipoTrssc);

        mapParametros.put("cTipo_Rcd_in", strTipoRcd);
        mapParametros.put("cMonto_in", FarmaUtility.getDecimalNumber(strMonto));
        mapParametros.put("cTerminal_in", strTerminal);

        mapParametros.put("cComercio_in", strComercio);
        mapParametros.put("cUbicacion_in", strUbicacion);
        mapParametros.put("cTelefono_in", strNroTelefono);

        mapParametros.put("cNumPedido_in", strNumPedido);
        mapParametros.put("cUsu_Crea_in", strUsuario);

        sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperCaja.class);
        mapper.registrarTrsscRecarga(mapParametros);
        tmpCodigo = (Long)mapParametros.get("tmpCodigo");

        return tmpCodigo;
    }

    /**
     * OBTENER EL ESTADO DE UNA RECARGA PARA MOSTRAR SUS DATOS
     * @author GFonseca
     * @since 14.08.2013
     */
    public String obtenerEstadoTrssc(String pCodGrupoCia, String strCodCia, String strCodLocal, String strNumPedido) {
        String tmpEst = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);
        mapParametros.put("cNum_Pedido_in", strNumPedido);
        try {
            sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperCaja.class);
            mapper.obtenerEstTrsscRecarga(mapParametros);
            tmpEst = (String)mapParametros.get("tmpEst");
        } catch (Exception e) {
            sqlSession.rollback(true);
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return tmpEst;
    }

    /**
     * OBTENER LA DESCRIPCION DE ERRORES DEL SIX
     * @author GFonseca
     * @since 14.08.2013
     */
    public ArrayList obtenerDescErrorSix(String strCodErrorSix) {
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCod_error_six", strCodErrorSix);

        try {
            sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperCaja.class);
            mapper.obtenerDescErrorSix(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        } catch (Exception e) {
            log.error("", e);
            tmpArray = null;
        } finally {
            sqlSession.close();
        }
        return tmpArray;
    }

    public Long registrarTrsscVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                       String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                       String strTipoRcd, String strNroTarjeta, String strMonto, String strTerminal,
                                       String strComercio, String strUbicacion, String strNroCuotas,
                                       String strIdCajero, String strNroDoc, String strUsuario) {
        Long tmpCodigo = null;
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);

        mapParametros.put("cTip_Msj_in", strTipMsjRecau);
        mapParametros.put("cEst_Trscc_in", strEstTrsscRecau);
        mapParametros.put("cTipo_Trssc_in", strTipoTrssc);

        mapParametros.put("cTipo_Rcd_in", strTipoRcd);
        mapParametros.put("cNro_Trjt_in", strNroTarjeta);
        mapParametros.put("cMonto_in", FarmaUtility.getDecimalNumber(strMonto));

        mapParametros.put("cTerminal_in", strTerminal);
        mapParametros.put("cComercio_in", strComercio);
        mapParametros.put("cUbicacion_in", strUbicacion);

        mapParametros.put("cNro_Cuotas_in", strNroCuotas);
        mapParametros.put("cId_cajero_in", strIdCajero);
        mapParametros.put("cNro_Doc", strNroDoc);

        mapParametros.put("cUsu_Crea_in", strUsuario);

        try {
            sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperCaja.class);
            mapper.registrarTrsscVentaCMR(mapParametros);
            tmpCodigo = (Long)mapParametros.get("tmpCodigo");
        } catch (Exception e) {
            sqlSession.rollback(true);
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return tmpCodigo;
    }

    /**
     * OBTIENE LAS OPCIONES BLOQUEADAS DEL SISTEMA
     * @author CVILCA
     * @since 18.10.2013
     */
    public ArrayList obtenerOpcionesBloqueadas() throws SQLException {
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        log.info("" + mapParametros);
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperCaja.class);
            mapper.obtenerOpcionesBloqueadas(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);

        } catch (Exception e) {
            log.error("", e);
            tmpArray = null;
        } finally {
            sqlSession.close();
        }
        return tmpArray;
    }

    /**
     * Obtener las formas de pago de un pedido, para abrir la gabeta en caso sea efectivo.
     * @author GFonseca
     * @since 27.Dic.2013
     */
    public ArrayList getFormasPagoPedido(String strNumPedido) {
        ArrayList<ArrayList<String>> lstListado = null;
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        //VariablesCaja.vArrayList_SecCompPago = new ArrayList();

        //mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        //mapParametros.put("cCodCia_in",FarmaVariables.vCodCia);
        //mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNum_Pedido_in", strNumPedido);

        try {
            openConnection();
            mapper.getFormasPagoPedido(mapParametros);
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
     * Obtener las formas de pago de un pedido, para abrir la gabeta en caso sea efectivo.
     * @author RHERRERA
     * @since 03.ABRIL.2014
     */
    public Map grabarNuevoCobro(String pNumPedVta, ArrayList a_CodFormaPago, ArrayList a_monto, ArrayList a_CodMoneda,
                                ArrayList a_XXX, ArrayList a_ImpTotal, ArrayList a_NumTarjeta,
                                ArrayList a_FecVecTarjeta,
        //--vuelto---//falta
        ArrayList a_NomCliTarjeta, ArrayList a_CantCupon, ArrayList a_DniTarjeta,
        //-- noc q es //
        ArrayList a_CodBouch, ArrayList a_CodLote) throws SQLException {


        Map<String, Object> mapParametros = new HashMap<String, Object>();
        // ArrayList<ArrayList<String>> lstListado = null;
        // String mensaje_out="";
        // int v_resultado;
        mapParametros.put("C_COD_GRUPOCIA_IN", FarmaVariables.vCodGrupoCia);
        mapParametros.put("C_COD_LOCAL_IN", FarmaVariables.vCodLocal);
        mapParametros.put("C_NUM_PTO_VTA_IN", pNumPedVta);
        mapParametros.put("C_TIP_COM_PED_IN", VariablesVentas.vTip_Comp_Ped);
        mapParametros.put("C_USU_CAJA", FarmaVariables.vIdUsu);
        mapParametros.put("vIndPedidoSeleccionado", VariablesCaja.vIndPedidoSeleccionado);
        mapParametros.put("vNuSecUsu", FarmaVariables.vNuSecUsu);
        mapParametros.put("vRuc_Cli_Ped", VariablesVentas.vRuc_Cli_Ped);
        mapParametros.put("vCod_Cli_Local", VariablesVentas.vCod_Cli_Local);
        mapParametros.put("vNom_Cli_Ped", VariablesVentas.vNom_Cli_Ped);
        mapParametros.put("vDir_Cli_Ped", VariablesVentas.vDir_Cli_Ped);
        mapParametros.put("VDni_Fid", VariablesFidelizacion.vDniCliente);
        mapParametros.put("vNumTarjeta_Fidel", VariablesFidelizacion.vNumTarjeta);
        mapParametros.put("pTipConsulta", ConstantsCaja.CONSULTA_VALIDA_CUPONES);
        mapParametros.put("cCodNumera_in", FarmaConstants.COD_NUMERA_SEC_COMP_PAGO);

        if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON))
            mapParametros.put("cCodMotKardex_in", ConstantsPtoVenta.MOT_KARDEX_VENTA_NORMAL);
        else if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_DELIVERY))
            mapParametros.put("cCodMotKardex_in", ConstantsPtoVenta.MOT_KARDEX_VENTA_DELIVERY);
        else if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL))
            mapParametros.put("cCodMotKardex_in", ConstantsPtoVenta.MOT_KARDEX_VENTA_ESPECIAL);
        else
            mapParametros.put("cCodMotKardex_in", "001");

        mapParametros.put("cTipDocKardex_in", ConstantsPtoVenta.TIP_DOC_KARDEX_VENTA);
        mapParametros.put("cCodNumeraKardex_in", FarmaConstants.COD_NUMERA_SEC_KARDEX);
        mapParametros.put("cDescDetalleForPago_in", VariablesCaja.vDescripcionDetalleFormasPago);
        mapParametros.put("cPermiteCampana", VariablesCaja.vPermiteCampaña);
        mapParametros.put("cValVueltoPedido",
                          new Double(FarmaUtility.getDecimalNumber(VariablesCaja.vValVueltoPedido)));
        mapParametros.put("cTipoCambio",
                          new Double(FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido)));

        openConnection();

        //************************************ARRAY*******************************************//
        Connection conn = sqlSession.getConnection();
        ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);


        //------------------------------------------------------------------------------------------------------//
        ARRAY CodFormaPago = new ARRAY(desc, conn, a_CodFormaPago.toArray());
        mapParametros.put("a_CodFormaPago", CodFormaPago);
        //************************************************************************************//
        ARRAY monto = new ARRAY(desc, conn, a_monto.toArray());
        mapParametros.put("a_monto", monto);
        //************************************************************************************//
        ARRAY CodMoneda = new ARRAY(desc, conn, a_CodMoneda.toArray());
        mapParametros.put("a_CodMoneda", CodMoneda);
        //************************************************************************************//
        ARRAY XXX = new ARRAY(desc, conn, a_XXX.toArray());
        mapParametros.put("a_XXX", XXX);
        //************************************************************************************//
        ARRAY ImpTotal = new ARRAY(desc, conn, a_ImpTotal.toArray());
        mapParametros.put("a_ImpTotal", ImpTotal);
        //************************************************************************************//
        ARRAY NumTarjeta = new ARRAY(desc, conn, a_NumTarjeta.toArray());
        mapParametros.put("a_NumTarjeta", NumTarjeta);
        //************************************************************************************//
        ARRAY FecVecTarjeta = new ARRAY(desc, conn, a_FecVecTarjeta.toArray());
        mapParametros.put("a_FecVecTarjeta", FecVecTarjeta);
        //************************************************************************************//
        ARRAY NomCliTarjeta = new ARRAY(desc, conn, a_NomCliTarjeta.toArray());
        mapParametros.put("a_NomCliTarjeta", NomCliTarjeta);
        //************************************************************************************//
        ARRAY CantCupon = new ARRAY(desc, conn, a_CantCupon.toArray());
        mapParametros.put("a_CantCupon", CantCupon);
        //************************************************************************************//
        ARRAY DniTarjeta = new ARRAY(desc, conn, a_DniTarjeta.toArray());
        mapParametros.put("a_DniTarjeta", DniTarjeta);
        //************************************************************************************//
        ARRAY CodBouch = new ARRAY(desc, conn, a_CodBouch.toArray());
        mapParametros.put("a_CodBouch", CodBouch);
        //************************************************************************************//
        ARRAY CodLote = new ARRAY(desc, conn, a_CodLote.toArray());
        mapParametros.put("a_CodLote", CodLote);
        //************************************************************************************//


        //************************************************************************************//
        //---- PRUEBA ENVIO DE ARRAYS    --------//


        mapper.grabarNuevoCobro_m(mapParametros);


        return mapParametros;
    }


}
