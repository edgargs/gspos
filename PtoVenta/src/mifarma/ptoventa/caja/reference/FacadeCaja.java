package mifarma.ptoventa.caja.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.caja.dao.DAOCaja;
import mifarma.ptoventa.caja.dao.FactoryCaja;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.reference.BeanImpresion;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FacadeCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FacadeCaja {

    private static final Logger log = LoggerFactory.getLogger(FacadeCaja.class);

    private DAOCaja daoCaja;

    public FacadeCaja() {
        super();
        daoCaja = FactoryCaja.getDAOCaja(TipoImplementacionDAO.MYBATIS);
    }

    public Long registrarTrsscVentaCMR(String strNroTarjeta, String strMonto, String strTerminal, String strComercio,
                                       String strUbicacion, String strNroCuotas, String strIdCajero, String strNroDoc,
                                       String strUsuario) {
        Long tmpCodigo = null;
        try {
            tmpCodigo =
                    daoCaja.registrarTrsscVentaCMR(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal,
                                                   ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200,
                                                   ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                   ConstantsRecaudacion.TRNS_CMPRA_VNTA,
                                                   ConstantsRecaudacion.TIPO_REC_VENTA_CMR, strNroTarjeta, strMonto,
                                                   strTerminal, strComercio, strUbicacion, strNroCuotas, strIdCajero,
                                                   strNroDoc, strUsuario);
        } catch (SQLException sqlE) {
            log.error("",sqlE);
        }
        return tmpCodigo;
    }

    public ArrayList<ArrayList<String>> obtenerOpcionesBloqueadas() {
        ArrayList<ArrayList<String>> listaOpciones = null;
        try {
            daoCaja.openConnection();
            listaOpciones = daoCaja.obtenerOpcionesBloqueadas();
            daoCaja.commit();
        } catch (Exception sqlE) {
            daoCaja.rollback();
            log.error("",sqlE);
        }
        return listaOpciones;
    }

    public ArrayList<ArrayList<String>> getFormasPagoPedido(String strNumPedido) {
        ArrayList<ArrayList<String>> listaFormaPago = null;
        try {
            daoCaja.openConnection();
            listaFormaPago = daoCaja.getFormasPagoPedido(strNumPedido);
            daoCaja.commit();
        } catch (Exception e) {
            daoCaja.rollback();
            log.error("", e);
        }
        return listaFormaPago;
    }

    //RHERRERA : MODIFICADO 04/04/2014

    /**
     * @param pnumpedvta
     * @param tblDetallePago
     * @return
     */
    public int pruebaCobro(String pnumpedvta, ArrayList a_CodFormaPago, ArrayList a_monto, ArrayList a_CodMoneda,
                           ArrayList a_XXX, ArrayList a_ImpTotal, ArrayList a_NumTarjeta, ArrayList a_FecVecTarjeta,
        //--vuelto---//falta
        ArrayList a_NomCliTarjeta, ArrayList a_CantCupon, ArrayList a_DniTarjeta,
        //-- noc q es //
        ArrayList a_CodBouch, ArrayList a_CodLote) {

        //ArrayList<ArrayList<String>> lstListado = null;
        // ArrayList myArray = new ArrayList();
        String secCompPago = "";
        int v_resultado = 0;
        try {


            // Map mapParametros = daoCaja.grabarNuevoCobro(pnumpedvta,myArray);
            /*
            Map mapParametros = daoCaja.grabarNuevoCobro(pnumpedvta,
             a_CodFormaPago,
             a_monto ,
             a_CodMoneda,
             a_XXX ,
             a_ImpTotal ,
             a_NumTarjeta ,
             a_FecVecTarjeta ,
            //--vuelto---//falta
             a_NomCliTarjeta,
             a_CantCupon ,
             a_DniTarjeta ,
            //-- noc q es //
             a_CodBouch,
             a_CodLote
           );
            */
            Map mapParametros =
                DBCobroBD.grabarNuevoCobro(pnumpedvta, a_CodFormaPago, a_monto, a_CodMoneda, a_XXX, a_ImpTotal,
                                           a_NumTarjeta, a_FecVecTarjeta,
                    //--vuelto---//falta
                    a_NomCliTarjeta, a_CantCupon, a_DniTarjeta,
                    //-- noc q es //
                    a_CodBouch, a_CodLote);

            v_resultado = new Integer(mapParametros.get("valor_out").toString());
            VariablesCaja.vmensaje_out = mapParametros.get("V_ERROR_MENSAJE_out").toString();
            VariablesCaja.vNumSecImpresionComprobantes = new Integer(mapParametros.get("V_NUC_SEC_out").toString());
            /*
             List<BeanResultado> lstRetorno =(List<BeanResultado>)mapParametros.get("V_SEC_COM_PAGO_out");
             lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
            for(int j=0; j<lstListado.size(); j++)
            {
              secCompPago = ((String)((ArrayList)lstListado.get(j)).get(1)).trim();

              VariablesCaja.vArrayList_SecCompPago.add(secCompPago);
            }

        */
        } catch (Exception e) {
            log.error("", e);

        }
        return v_resultado;
    }

    public void setVersion(){
        try {
            daoCaja.openConnection();
            daoCaja.setVersion();
            daoCaja.commit();
        } catch (Exception e) {
            daoCaja.rollback();
            log.error("", e);
        }        
    }
    
    /**
     * Se imprime el voucher de devolucion por diferencia.
     * @param pNumPedVta
     * @return
     */
    public boolean impresionVoucher(String pNumPedVta){
        boolean resultado = true;
        try{
            daoCaja.openConnection();
            List<BeanImpresion> lista = daoCaja.getVoucherDevolDif(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, pNumPedVta);
            daoCaja.commit();
            
            if(lista!=null){
                new UtilityImpCompElectronico().impresionTermica(lista, null);
            }else{
                resultado = false;
            }            
        }catch(Exception ex){
            daoCaja.rollback();
            log.error("", ex);            
            resultado = false;
        }
        
        return resultado;
    }
    
    /**
     * Imprime encabezado de cupon
     * @author ERIOS
     * @since 16.02.2016
     * @param pCodCupon
     * @return
     */
    public boolean impresionEncabezadoCupon(String pCodCupon){
        boolean resultado = true;
        try{
            daoCaja.openConnection();
            List<BeanImpresion> lista = daoCaja.getEncabezadoCupon(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, pCodCupon);
            daoCaja.commit();
            
            if(lista!=null){
                UtilityImpCompElectronico utilityCompElectronico = new UtilityImpCompElectronico();
                String rutaImpTermica = utilityCompElectronico.getRuta();
                String modelo = utilityCompElectronico.getModelo();
                PrintConsejo.impresionTermica(modelo, rutaImpTermica, lista);
            }else{
                resultado = false;
            }            
        }catch(Exception ex){
            daoCaja.rollback();
            log.error("", ex);            
            resultado = false;
        }
        
        return resultado;
    }
}
