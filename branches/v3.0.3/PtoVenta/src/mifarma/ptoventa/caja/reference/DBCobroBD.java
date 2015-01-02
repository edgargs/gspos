package mifarma.ptoventa.caja.reference;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdbc.driver.OracleTypes;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBCobroBD {

    private static ArrayList parametros;
    private static final Logger log = LoggerFactory.getLogger(DBCobroBD.class);


    public DBCobroBD() {
    }


    public static Map grabarNuevoCobro(String pNumPedVta, ArrayList a_CodFormaPago, ArrayList a_monto,
                                       ArrayList a_CodMoneda, ArrayList a_XXX, ArrayList a_ImpTotal,
                                       ArrayList a_NumTarjeta, ArrayList a_FecVecTarjeta,
        //--vuelto---//falta
        ArrayList a_NomCliTarjeta, ArrayList a_CantCupon, ArrayList a_DniTarjeta,
        //-- noc q es //
        ArrayList a_CodBouch, ArrayList a_CodLote) throws SQLException {


        Map<String, Object> mapParametros = new HashMap<String, Object>();
        // ArrayList<ArrayList<String>> lstListado = null;
        // String mensaje_out="";
        // int v_resultado;
        mapParametros.put(getMayuscula("c_cod_grupocia_in"), FarmaVariables.vCodGrupoCia);
        mapParametros.put(getMayuscula("c_cod_local_in"), FarmaVariables.vCodLocal);
        mapParametros.put(getMayuscula("c_num_pto_vta_in"), pNumPedVta);
        mapParametros.put(getMayuscula("c_tip_com_ped_in"), VariablesVentas.vTip_Comp_Ped);
        mapParametros.put(getMayuscula("c_usu_caja"), FarmaVariables.vIdUsu);
        mapParametros.put(getMayuscula("vindpedidoseleccionado"), VariablesCaja.vIndPedidoSeleccionado);
        mapParametros.put(getMayuscula("vnusecusu"), FarmaVariables.vNuSecUsu);
        mapParametros.put(getMayuscula("vruc_cli_ped"), VariablesVentas.vRuc_Cli_Ped);
        mapParametros.put(getMayuscula("vcod_cli_local"), VariablesVentas.vCod_Cli_Local);
        mapParametros.put(getMayuscula("vnom_cli_ped"), VariablesVentas.vNom_Cli_Ped);
        mapParametros.put(getMayuscula("vdir_cli_ped"), VariablesVentas.vDir_Cli_Ped);
        mapParametros.put(getMayuscula("vdni_fid"), VariablesFidelizacion.vDniCliente);
        mapParametros.put(getMayuscula("vnumtarjeta_fidel"), VariablesFidelizacion.vNumTarjeta);
        mapParametros.put(getMayuscula("ptipconsulta"), ConstantsCaja.CONSULTA_VALIDA_CUPONES);
        mapParametros.put(getMayuscula("ccodnumera_in"), FarmaConstants.COD_NUMERA_SEC_COMP_PAGO);

        if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON))
            mapParametros.put(getMayuscula("ccodmotkardex_in"), ConstantsPtoVenta.MOT_KARDEX_VENTA_NORMAL);
        else if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_DELIVERY))
            mapParametros.put(getMayuscula("ccodmotkardex_in"), ConstantsPtoVenta.MOT_KARDEX_VENTA_DELIVERY);
        else if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL))
            mapParametros.put(getMayuscula("ccodmotkardex_in"), ConstantsPtoVenta.MOT_KARDEX_VENTA_ESPECIAL);
        else
            mapParametros.put(getMayuscula("ccodmotkardex_in"), "001");

        mapParametros.put(getMayuscula("ctipdockardex_in"), ConstantsPtoVenta.TIP_DOC_KARDEX_VENTA);
        mapParametros.put(getMayuscula("ccodnumerakardex_in"), FarmaConstants.COD_NUMERA_SEC_KARDEX);
        mapParametros.put(getMayuscula("cdescdetalleforpago_in"), VariablesCaja.vDescripcionDetalleFormasPago);
        mapParametros.put(getMayuscula("cpermitecampana"), VariablesCaja.vPermiteCampaña);
        mapParametros.put(getMayuscula("cvalvueltopedido"),
                          new Double(FarmaUtility.getDecimalNumber(VariablesCaja.vValVueltoPedido)));
        mapParametros.put(getMayuscula("ctipocambio"),
                          new Double(FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido)));
        mapParametros.put(getMayuscula("C_PORCOPAGO"),
                          new Double(FarmaUtility.getDecimalNumber(VariablesConvenio.vPorcCoPago)));


        //************************************ARRAY*******************************************//
        Connection conn = FarmaConnection.getConnection();
        ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);

        //------------------------------------------------------------------------------------------------------//
        ARRAY CodFormaPago = new ARRAY(desc, conn, a_CodFormaPago.toArray());
        mapParametros.put(getMayuscula("a_codformapago"), CodFormaPago);
        //************************************************************************************//
        ARRAY monto = new ARRAY(desc, conn, a_monto.toArray());
        mapParametros.put(getMayuscula("a_monto"), monto);
        //************************************************************************************//
        ARRAY CodMoneda = new ARRAY(desc, conn, a_CodMoneda.toArray());
        mapParametros.put(getMayuscula("a_codmoneda"), CodMoneda);
        //************************************************************************************//
        ARRAY XXX = new ARRAY(desc, conn, a_XXX.toArray());
        mapParametros.put(getMayuscula("a_xxx"), XXX);
        //************************************************************************************//
        ARRAY ImpTotal = new ARRAY(desc, conn, a_ImpTotal.toArray());
        mapParametros.put(getMayuscula("a_imptotal"), ImpTotal);
        //************************************************************************************//
        ARRAY NumTarjeta = new ARRAY(desc, conn, a_NumTarjeta.toArray());
        mapParametros.put(getMayuscula("a_numtarjeta"), NumTarjeta);
        //************************************************************************************//
        ARRAY FecVecTarjeta = new ARRAY(desc, conn, a_FecVecTarjeta.toArray());
        mapParametros.put(getMayuscula("a_fecvectarjeta"), FecVecTarjeta);
        //************************************************************************************//
        ARRAY NomCliTarjeta = new ARRAY(desc, conn, a_NomCliTarjeta.toArray());
        mapParametros.put(getMayuscula("a_nomclitarjeta"), NomCliTarjeta);
        //************************************************************************************//
        ARRAY CantCupon = new ARRAY(desc, conn, a_CantCupon.toArray());
        mapParametros.put(getMayuscula("a_cantcupon"), CantCupon);
        //************************************************************************************//
        ARRAY DniTarjeta = new ARRAY(desc, conn, a_DniTarjeta.toArray());
        mapParametros.put(getMayuscula("a_dnitarjeta"), DniTarjeta);
        //************************************************************************************//
        ARRAY CodBouch = new ARRAY(desc, conn, a_CodBouch.toArray());
        mapParametros.put(getMayuscula("a_codbouch"), CodBouch);
        //************************************************************************************//
        ARRAY CodLote = new ARRAY(desc, conn, a_CodLote.toArray());
        mapParametros.put(getMayuscula("a_codlote"), CodLote);
        //************************************************************************************//


        //************************************************************************************//
        //---- PRUEBA ENVIO DE ARRAYS    --------//


        //mapper.grabarNuevoCobro_m(mapParametros);

        executeSQLStoredProcedureMap("ptoventa_caj_cobro.caj_proc_cobro_ped(?,?,?,?," + "?,?,?,?,?,?,?,?,?,?,?,?," +
                                     "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", mapParametros);

        return mapParametros;
    }


    public static void executeSQLStoredProcedureMap(String pStoredProcedure,
                                                    Map<String, Object> pParameters) throws SQLException {
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", "FarmaError", 9002);

        CallableStatement stmt;
        Connection cnx = FarmaConnection.getConnection();
        stmt = cnx.prepareCall("{ call ? := " + pStoredProcedure + " }");

        stmt.registerOutParameter(1, OracleTypes.NUMBER);
        stmt.registerOutParameter(2, OracleTypes.VARCHAR);
        stmt.registerOutParameter(3, OracleTypes.NUMBER);
        stmt.registerOutParameter(38, OracleTypes.CHAR);

        stmt.setArray(4, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_codformapago")));
        stmt.setArray(5, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_monto")));
        stmt.setArray(6, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_codmoneda")));
        stmt.setArray(7, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_xxx")));
        stmt.setArray(8, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_imptotal")));
        stmt.setArray(9, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_numtarjeta")));
        stmt.setArray(10, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_fecvectarjeta")));
        stmt.setArray(11, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_codformapago")));
        stmt.setArray(12, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_cantcupon")));
        stmt.setArray(13, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_dnitarjeta")));
        stmt.setArray(14, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_codbouch")));
        stmt.setArray(15, (oracle.sql.ARRAY)pParameters.get(getMayuscula("a_codlote")));
        stmt.setString(16, (String)pParameters.get(getMayuscula("c_cod_grupocia_in")));
        stmt.setString(17, (String)pParameters.get(getMayuscula("c_cod_local_in")));
        stmt.setString(18, (String)pParameters.get(getMayuscula("c_num_pto_vta_in")));
        stmt.setString(19, (String)pParameters.get(getMayuscula("c_tip_com_ped_in")));
        stmt.setString(20, (String)pParameters.get(getMayuscula("c_usu_caja")));
        stmt.setString(21, (String)pParameters.get(getMayuscula("vindpedidoseleccionado")));
        stmt.setString(22, (String)pParameters.get(getMayuscula("vnusecusu")));
        stmt.setString(23, (String)pParameters.get(getMayuscula("vruc_cli_ped")));
        stmt.setString(24, (String)pParameters.get(getMayuscula("vcod_cli_local")));
        stmt.setString(25, (String)pParameters.get(getMayuscula("vnom_cli_ped")));
        stmt.setString(26, (String)pParameters.get(getMayuscula("vdir_cli_ped")));
        stmt.setString(27, (String)pParameters.get(getMayuscula("vdni_fid")));
        stmt.setString(28, (String)pParameters.get(getMayuscula("vnumtarjeta_fidel")));
        stmt.setString(29, (String)pParameters.get(getMayuscula("ptipconsulta")));
        stmt.setString(30, (String)pParameters.get(getMayuscula("ccodnumera_in")));
        stmt.setString(31, (String)pParameters.get(getMayuscula("ccodmotkardex_in")));
        stmt.setString(32, (String)pParameters.get(getMayuscula("ctipdockardex_in")));
        stmt.setString(33, (String)pParameters.get(getMayuscula("ccodnumerakardex_in")));
        stmt.setString(34, (String)pParameters.get(getMayuscula("cdescdetalleforpago_in")));
        stmt.setString(35, (String)pParameters.get(getMayuscula("cpermitecampana")));
        stmt.setDouble(36, (Double)(pParameters.get(getMayuscula("cvalvueltopedido"))));
        stmt.setDouble(37, (Double)(pParameters.get(getMayuscula("ctipocambio"))));
        stmt.setDouble(39, (Double)(pParameters.get(getMayuscula("C_PORCOPAGO"))));
        //ejecutando el estoredProcedure
        stmt.execute();
        //Obteniendo el cursor con el resultado
        int pUNO = stmt.getInt(1);
        String pDos = stmt.getString(2);
        int pTres = stmt.getInt(3);
        String pCuatro = stmt.getString(38);

        log.info("valor_out>>" + pUNO);
        log.info("v_error_mensaje_out>>" + pDos);
        log.info("v_nuc_sec_out>>" + pTres);
        log.info("v_nuc_sec_out>>" + pCuatro);

        pParameters.put(getMayuscula("valor_out"), pUNO);
        pParameters.put(getMayuscula("v_error_mensaje_out"), pDos);
        pParameters.put(getMayuscula("v_nuc_sec_out"), pTres);
        pParameters.put(getMayuscula("C_ESPEDIDOCONVENIO"), pCuatro);

        stmt.close();
    }


    public static String getMayuscula(String pCadena) {
        return pCadena.toUpperCase();
    }

    public static String getIndCobroBD() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_CAJ_COBRO.F_IND_NVO_COBRO(?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_COBRO.F_IND_NVO_COBRO(?,?)", parametros);
    }

}
