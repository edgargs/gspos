package mifarma.ptoventa.recetario.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.reference.DAOTransaccion;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DAORecetario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      15.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public interface DAORecetario extends DAOTransaccion {

    public ArrayList<ArrayList<String>> listarFormaFarmaceutica() throws SQLException;

    public void listarInsumosFarma(FarmaTableModel pTableModel) throws SQLException;

    public ArrayList<ArrayList<String>> obtenerInfInsumo(String pCodInsumo) throws SQLException;

    public String grabarCabeRM(String strCodGrupoCia, String strCodCia, String strCodLocal, String pIndEsteril,
                               String pCodFormaFarmac, String pCantContenido, String pCantPreparados,
                               String pFecRecetario, String pCodPaciente, String pCodMedico, String pFecEntrega,
                               String pHoraEntrega, String pCodLocalEntrega, String pObsRecetario,
                               String pValBrutoRecetario, String pEstPedidoCab, String pUsuCrea, String pFecCrea,
                               String pNumTelefono, String pNumGuia, double pValIgv, double pValVenta,
                               double pvalVentaSR) throws SQLException;

    public void grabarDetaRM(String strCodGrupoCia, String strCodCia, String strCodLocal, String pNumRecetario,
                             String pCodInsumo, Float pCantAtendida, Float pValPrecRec, Float pValPrecTotal,
                             String pUsuCrea, String pFecCrea, String pCodUnidadVenta,
                             Float pCantPorcentaje) throws SQLException;

    public String htmlContrasenia(String pCodGrupoCia, String strCodGrupoCia, String strCodLocal,
                                  String pNumeroRecetario) throws SQLException;

    public ArrayList<ArrayList<String>> listarGuiasRM() throws SQLException;

    public String grabarGuiaRM(String cCodGrupoCia_in, String cCod_Cia_in, String cCod_Local_in, String cnumguiatran,
                               String cusu_crea_in) throws SQLException;

    public double getPorcIgv(String pCodIgv) throws SQLException;

    public ArrayList<ArrayList<String>> listarProdVirtual() throws SQLException;

    public ArrayList<ArrayList<String>> listadoUnidadesRelacionadas(String pCodUnidad) throws SQLException;

    /**
     * Obtiene el numero de recetario de acuerdo a un numero de pedido
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public HashMap<String, String> getNumeroRecetario(String numPedido) throws SQLException;

    /**
     * Obtiene una trama formateada de informacion de recetario
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public String getTramaRecetario(String numRecetario) throws SQLException;

    /**
     * Envia la trama anteriormente obtenida hacia el servidor central
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public String enviaTramaRecetario(String trama) throws SQLException;

    /**
     * Obtiene el estado del recetario deacuerdo al numero de pedido anexo a este
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public String getEstadoRecetarioPedido(String numPedido) throws SQLException;

    /**
     * Anular el recetario magistral (si es que existe) de un pedido indicado
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public void anularRecetarioPedido(String numPedido) throws SQLException;

    /**
     * Retorna el factor de conversion de unidades de un producto convertido a otra unidad
     * @author LLEIVA
     * @since 18.Junio.2013
     */
    public String getFactorConversion(String codProducto, String codUnidadVenta) throws SQLException;

    /**
     * obtiene la trama de actualizacion de Recetarios magistrales para el envio hacia el servidor de FASA
     * @author LLEIVA
     * @since 28.Junio.2013
     */
    public String getTramaActualizaRecetFasa(String numPedido, String accion) throws SQLException;

    /**
     * Envia la trama anteriormente obtenida hacia el servidor de FASA
     * @author LLEIVA
     * @since 28.Junio.2013
     */
    public String enviaTramaActualizaRecetFasa(String trama) throws SQLException;

    /**
     * Obtiene el reporte de Recetario Magistral
     * @author LLEIVA
     * @since 27.Sep.2013
     */
    public ArrayList<ArrayList<String>> reporteRecetario(String fecha_inicial, String fecha_final,
                                                         String nombre_paciente, String num_recetario,
                                                         String num_pedido);

    /**
     * Obtiene el listado de detalle de reporte de Recetario Magistral
     * @author LLEIVA
     * @since 27.Sep.2013
     */
    public ArrayList<ArrayList<String>> listadoDetalleRecetario(String num_recetario);

    /**
     * Obtiene detalle de reporte de Recetario Magistral
     * @author LLEIVA
     * @since 27.Sep.2013
     */
    public ArrayList<ArrayList<String>> detalleReporteRecetario(String num_recetario);
}
