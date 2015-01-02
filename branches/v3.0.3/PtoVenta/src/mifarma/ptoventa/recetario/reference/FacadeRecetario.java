package mifarma.ptoventa.recetario.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.recetario.dao.DAORecetario;
import mifarma.ptoventa.recetario.dao.FactoryRecetario;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FacadeRecetario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      15.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FacadeRecetario {

    private static final Logger log = LoggerFactory.getLogger(FacadeRecetario.class);

    private DAORecetario daoRecetario;

    public FacadeRecetario() {
        super();
        daoRecetario = FactoryRecetario.getDAORecetario(FactoryRecetario.Tipo.MYBATIS);
    }

    public ArrayList<ArrayList<String>> listaFormaFarmac() {
        ArrayList<ArrayList<String>> lstListado = null;

        try {
            lstListado = daoRecetario.listarFormaFarmaceutica();
        } catch (SQLException e) {
            log.error("", e);
        }

        return lstListado;
    }

    public void listarInsumosFarma(FarmaTableModel pTableModel) throws SQLException {
        daoRecetario.listarInsumosFarma(pTableModel);
    }

    public ArrayList<ArrayList<String>> obtenerInformacionInsumo(String pCodInsumo) {
        ArrayList<ArrayList<String>> tmpArrayRcm = null;
        try {
            tmpArrayRcm = daoRecetario.obtenerInfInsumo(pCodInsumo);
        } catch (SQLException sqlE) {
            log.error("No se pudo obtener la informacion del insumo." + sqlE.getMessage());
        }

        return tmpArrayRcm;
    }

    public String grabarCabeceraRM(ArrayList<String> arrayCabeRM, ArrayList<ArrayList<String>> arrayDetalle) {
        String tmpCodCab = null;
        try {
            tmpCodCab =
                    daoRecetario.grabarCabeRM(arrayCabeRM.get(0), arrayCabeRM.get(1), arrayCabeRM.get(2), arrayCabeRM.get(3),
                                              arrayCabeRM.get(4), arrayCabeRM.get(5), arrayCabeRM.get(6),
                                              arrayCabeRM.get(7), arrayCabeRM.get(8), arrayCabeRM.get(9),
                                              arrayCabeRM.get(10), arrayCabeRM.get(11), arrayCabeRM.get(12),
                                              arrayCabeRM.get(13), arrayCabeRM.get(14), arrayCabeRM.get(15),
                                              arrayCabeRM.get(16), arrayCabeRM.get(17), arrayCabeRM.get(18),
                                              arrayCabeRM.get(19), FarmaUtility.getDecimalNumber(arrayCabeRM.get(20)),
                                              FarmaUtility.getDecimalNumber(arrayCabeRM.get(21)),
                                              FarmaUtility.getDecimalNumber(arrayCabeRM.get(22)));
            for (int i = 0; i < arrayDetalle.size(); i++) {
                String temp = ((ArrayList)arrayDetalle.get(i)).get(10).toString();
                if (temp == null || "".equals(temp) || temp.equals("0"))
                    temp = "100";
                daoRecetario.grabarDetaRM(((ArrayList)arrayDetalle.get(i)).get(0).toString(),
                                          ((ArrayList)arrayDetalle.get(i)).get(1).toString(),
                                          ((ArrayList)arrayDetalle.get(i)).get(2).toString(), tmpCodCab,
                                          ((ArrayList)arrayDetalle.get(i)).get(3).toString(), new

                        Float(((ArrayList)arrayDetalle.get(i)).get(4).toString().replace(",", "")),
                        new Float(((ArrayList)arrayDetalle.get(i)).get(5).toString().replace(",", "")),
                        new Float(((ArrayList)arrayDetalle.get(i)).get(6).toString().replace(",", "")), (

                        (ArrayList)arrayDetalle.get(i)).get(7).toString(),
                        ((ArrayList)arrayDetalle.get(i)).get(8).toString(),
                        ((ArrayList)arrayDetalle.get(i)).get(9).toString(), new Float(temp.replace(",", "")));
            }
            daoRecetario.commit();
        } catch (Exception sqlE) {
            log.error("", sqlE);
            daoRecetario.rollback();
            tmpCodCab = null;
        }

        return tmpCodCab;
    }

    public ArrayList<ArrayList<String>> listarGuiasRM() {
        ArrayList<ArrayList<String>> lstListado = null;

        try {
            lstListado = daoRecetario.listarGuiasRM();
        } catch (SQLException e) {
            log.error("", e);
        }

        return lstListado;
    }

    /**
     * Obtiene html de constrasenia
     * @author ERIOS
     * @since 01.06.2013
     */
    public void imprimirContrasenia(String pNumeroRecetario) {
        String htmlConstrasenia;
        try {
            htmlConstrasenia =
                    daoRecetario.htmlContrasenia(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal,
                                                 pNumeroRecetario);
            log.debug("Constrasenia Recetario:");
            log.debug(htmlConstrasenia);
            //Original
            PrintConsejo.imprimirHtml(htmlConstrasenia, VariablesPtoVenta.vImpresoraActual,
                                      VariablesPtoVenta.vTipoImpTermicaxIp);
            //Copia
            PrintConsejo.imprimirHtml(htmlConstrasenia, VariablesPtoVenta.vImpresoraActual,
                                      VariablesPtoVenta.vTipoImpTermicaxIp);
        } catch (SQLException e) {
            log.error("Error al imprimir contrasenias", e);
        }
    }

    /**
     * 0.
     */
    public String grabarGuiaRM(ArrayList<String> arrayGuia) {
        String codigoRM = "";
        try {
            codigoRM =
                    daoRecetario.grabarGuiaRM(arrayGuia.get(0), arrayGuia.get(1), arrayGuia.get(2), arrayGuia.get(3),
                                              arrayGuia.get(4));
        } catch (Exception e) {
            log.error("", e);
        }
        return codigoRM;
    }

    /**
     * Calcula totales del pedido recetario
     * @author ERIOS
     * @since 28.05.2013
     */
    public double calcularTotales(ArrayList<ArrayList<Object>> detalle, String tmpCantPreparado,
                                  String tmpCostoFormFarm) {
        double tmpSumaSubTotal = 0.0;
        double tmpMontoTotal = 0.0;
        double tmpCantEnvases = 0.0;
        double tmpCostoFF = 0.0;

        if (detalle.size() > 0) {
            tmpCantEnvases = FarmaUtility.getDecimalNumber(tmpCantPreparado);
            tmpCostoFF = FarmaUtility.getDecimalNumber(tmpCostoFormFarm);
        }
        //se halla el costo total sumando los subtotales de los insumos y estandarizados
        if (detalle.size() > 0) {
            for (int i = 0; i < detalle.size(); i++) {
                double tmpSubTotalCalc = 0.0;
                ArrayList<Object> temp = detalle.get(i);
                try {
                    tmpSubTotalCalc = FarmaUtility.getDecimalNumber(temp.get(7).toString());
                    tmpSumaSubTotal = tmpSumaSubTotal + tmpSubTotalCalc;
                } catch (Exception ex) {
                    log.error("", ex);
                }
            }
        }

        //se aplica la formula del modulo heredado
        // TOTAL = CANT_FORM_FARM * CANT_ENV * (PREC_FORM_FARM + SUMA_SUB_TOT)
        tmpMontoTotal = 1 * tmpCantEnvases * (tmpCostoFF + tmpSumaSubTotal);

        //se aplica IGV al total
        double auxPorcIgv;
        double valVenta = -1;
        try {
            auxPorcIgv = daoRecetario.getPorcIgv(ConstantsRecetario.COD_IGV_VIGENTE);

            valVenta = FarmaUtility.getDecimalNumberRedondeado(tmpMontoTotal);
            valVenta = UtilityVentas.ajustarMonto(valVenta, 2);
            VariablesRecetario.vValVenta = FarmaUtility.formatNumber(valVenta);
            VariablesRecetario.vValVentaSinRed = FarmaUtility.formatNumber(tmpMontoTotal, 2);

            double valIgv = FarmaUtility.getDecimalNumberRedondeado(valVenta * (auxPorcIgv / 100));
            VariablesRecetario.vValIgv = FarmaUtility.formatNumber(valIgv);
        } catch (Exception e) {
            log.error("No recupero porcentaje de Igv.\n" +
                    e);
        }

        return valVenta;
    }

    public void resetearVariablesRecetario() {
        VariablesRecetario.vCantInsumosCodSelec = "";
        VariablesRecetario.vPosOld = 0;
        VariablesRecetario.vPosNew = 0;
        VariablesRecetario.vArrayInsumosSeleccionados = null;
        VariablesRecetario.vMapDatosPacienteMedico = null;
        VariablesRecetario.vArrayEsteril = null;
        //LLEIVA: 21/Mayo/2013 - Variables para enlazar producto virtual Preparado magistral
        //VariablesRecetario.strCodigoRecetario = "";
        VariablesRecetario.strPrecioTotal = "";
        VariablesRecetario.strCant_Recetario = "";
        VariablesRecetario.vValIgv = "";
        VariablesRecetario.vValVenta = "";
        VariablesRecetario.strAccion = "";
    }

    public String getDate() {
        String dateSys = "";
        try {
            dateSys = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException sqlE) {
            log.debug("Error al obtener la fecha.");
        }
        return dateSys;
    }

    public Map<String, String> listaProdVirtual() {
        ArrayList<ArrayList<String>> lstListadoTemp = null;
        Map<String, String> listado = new HashMap<String, String>();
        try {
            lstListadoTemp = daoRecetario.listarProdVirtual();
            for (int i = 0; i < lstListadoTemp.size(); i++) {
                ArrayList<String> temp = lstListadoTemp.get(i);
                listado.put(temp.get(0), null);
            }
        } catch (SQLException e) {
            log.error("", e);
        }

        return listado;
    }

    /**
     * Obtiene unidades relacionadas a la unidad enviada
     * @author LLEIVA
     * @since 04.Junio.2013
     */
    public ArrayList<ArrayList<String>> listadoUnidadesRelacionadas(String pCodUnidad) {
        ArrayList<ArrayList<String>> tmpArrayUR = null;
        try {
            tmpArrayUR = daoRecetario.listadoUnidadesRelacionadas(pCodUnidad);
        } catch (SQLException sqlE) {
            log.error("No se pudo obtener el listado de unidades relacionadas." + sqlE.getMessage());
        }

        return tmpArrayUR;
    }

    /**
     * Obtiene unidades relacionadas a la unidad enviada
     * @author LLEIVA
     * @since 04.Junio.2013
     */
    public HashMap<String, String> getNumeroRecetario(String numPedido) { //ArrayList tmpArrayUR = new ArrayList();
        HashMap<String, String> hRecetario = new HashMap<String, String>();
        try {
            hRecetario = daoRecetario.getNumeroRecetario(numPedido);
        } catch (SQLException sqlE) {
            log.error("No se pudo obtener el listado de unidades relacionadas." + sqlE.getMessage());
            //si ocurre algun error, devolver nulo
            hRecetario = null;
        }

        return hRecetario;
    }

    /**
     * Obtiene una trama formateada de informacion de recetario
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public String getTramaRecetario(String numRecetario) {
        String resultado = "";
        try {
            resultado = daoRecetario.getTramaRecetario(numRecetario);
        } catch (SQLException sqlE) {
            log.error("No se pudo obtener el listado de unidades relacionadas." + sqlE.getMessage());
            //Si existe alguna error, devolver la cadena "ERROR"
            resultado = "ERROR";
        }

        return resultado;
    }

    /**
     * Obtiene una trama formateada de informacion de recetario
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public String enviaTramaRecetario(String trama) {
        String resultado = "";
        try {
            resultado = daoRecetario.enviaTramaRecetario(trama);
        } catch (SQLException sqlE) {
            log.error("No se pudo obtener el listado de unidades relacionadas." + sqlE.getMessage());
            //Si existe alguna error, devolver la cadena "ERROR"
            resultado = "ERROR";
        }

        return resultado;
    }

    /**
     * Se valida si el pedido puede anularse, dependiendo del estado del recetario adjunto
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public boolean getValidadAnulacionRecetarioPedido(String numPedido) {
        String est = "";
        try {
            est = daoRecetario.getEstadoRecetarioPedido(numPedido);
            //si el pedido no posee recetario, se puede anular
            if ("".equals(est) || est == null)
                return true;
            //if("ERROR".equals(est))
            //    return false;
            else { //verifico si el estado del recetario es valido para realizar la anulación
                return VariablesRecetario.vEstadosValidosAnulacion.containsKey(est);
            }
        } catch (SQLException sqlE) {
            log.error("ERROR." + sqlE.getMessage());
            return false;
        }
    }

    /**
     * Anular el recetario magistral (si es que existe) de un pedido indicado
     * @author LLEIVA
     * @since 12.Junio.2013
     */
    public void anularRecetarioPedido(String numPedido) {
        try {
            daoRecetario.anularRecetarioPedido(numPedido);
        } catch (SQLException sqlE) {
            log.error("ERROR." + sqlE.getMessage());
        }
    }

    /**
     * Retorna el factor de conversion de unidades de un producto convertido a otra unidad
     * @author LLEIVA
     * @since 18.Junio.2013
     */
    public double getFactorConversion(String codProducto, String codUnidadVenta) {
        double resultado = 0.0;
        try {
            String res = daoRecetario.getFactorConversion(codProducto, codUnidadVenta);
            resultado = FarmaUtility.getDecimalNumber(res);
        } catch (Exception sqlE) {
            log.error("ERROR." + sqlE.getMessage());
        }
        return resultado;
    }

    /**
     * Envia la información para actualizar los datos de Ordenes de pedido de FASA cuando
     * se realiza un pago de pedido de esta
     * @author LLEIVA
     * @since 28.Junio.2013
     */
    public boolean enviaTramaActualizaRecetFASA(String numPedido, String accion) {
        boolean flag = false;
        try { //se obtiene la trama para envio de envio (siendo la accion actualizar=A o anular=N)
            String trama = daoRecetario.getTramaActualizaRecetFasa(numPedido, accion);
            if (trama != null && trama != "" && trama != "ERROR") { //si la accion es actualizar
                String resultado = daoRecetario.enviaTramaActualizaRecetFasa(trama);
                if ("2OKActualizacion Exitosa".equals(resultado))
                    flag = true;
                else
                    flag = false;
            } else
                flag = false;
        } catch (SQLException sqlE) {
            log.error("ERROR." + sqlE.getMessage());
            flag = false;
        } finally {
            return flag;
        }
    }


    public ArrayList<ArrayList<String>> reporteRecetario(String fecha_inicial, String fecha_final,
                                                         String nombre_paciente, String num_recetario,
                                                         String num_pedido) {
        ArrayList<ArrayList<String>> lstListado = null;

        try {
            lstListado =
                    daoRecetario.reporteRecetario(fecha_inicial, fecha_final, nombre_paciente, num_recetario, num_pedido);
        } catch (Exception e) {
            log.error("", e);
        }
        return lstListado;
    }

    public ArrayList<ArrayList<String>> listadoDetalleRecetario(String num_recetario) {
        ArrayList<ArrayList<String>> lstListado = null;

        try {
            lstListado = daoRecetario.listadoDetalleRecetario(num_recetario);
        } catch (Exception e) {
            log.error("", e);
        }
        return lstListado;
    }

    public ArrayList<ArrayList<String>> detalleReporteRecetario(String num_recetario) {
        ArrayList<ArrayList<String>> lstListado = null;

        try {
            lstListado = daoRecetario.detalleReporteRecetario(num_recetario);
        } catch (Exception e) {
            log.error("", e);
        }
        return lstListado;
    }

}
