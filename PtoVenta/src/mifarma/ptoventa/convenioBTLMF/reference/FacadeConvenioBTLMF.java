package mifarma.ptoventa.convenioBTLMF.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;
import mifarma.electronico.epos.reference.EposVariables;

import mifarma.ptoventa.cnx.FarmaVentaCnxUtility;
import mifarma.ptoventa.convenioBTLMF.dao.DAOConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.dao.DAORACConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.dao.FactoryConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.main.DlgProcesar;
import mifarma.ptoventa.reference.BeanImpresion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.TipoImplementacionDAO;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.apache.ibatis.exceptions.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeConvenioBTLMF {

    private static final Logger log = LoggerFactory.getLogger(FacadeConvenioBTLMF.class);

    private DAOConvenioBTLMF daoConvenioBTLMF;
    private DAORACConvenioBTLMF daoRACConvenioBTLMF;

    public FacadeConvenioBTLMF() {
        super();
        daoConvenioBTLMF = FactoryConvenioBTLMF.getDAOConvenioBTLMF(TipoImplementacionDAO.MYBATIS);
    }

    public ArrayList<ArrayList<String>> listarBeneficRemoto(FarmaTableModel tableModelListaDatos) {
        ArrayList<ArrayList<String>> lstListado = null;
        daoRACConvenioBTLMF = null;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.MYBATIS;
        //ERIOS 07.10.2015 Determina si esta activo el Gestor
        if(DlgProcesar.getIndGestorTx().equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }
        try{
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(tipo);
            daoRACConvenioBTLMF.openConnection();
            lstListado = daoRACConvenioBTLMF.listaBenefRemoto();
            daoRACConvenioBTLMF.commit();
            tableModelListaDatos.clearTable();
            tableModelListaDatos.data = lstListado;
        } catch (Exception e) {
            daoRACConvenioBTLMF.rollback();
            log.error("", e);
        }
        return lstListado;
    }

    public String grabarTemporalesRAC(String pNumPedVta) {
        return grabarTemporalesRAC(pNumPedVta, FarmaConstants.INDICADOR_N);
    }

    /**
     * Graba tablas temporales en RAC
     * @author ERIOS
     * @since 2.4.4
     */
    public String grabarTemporalesRAC(String pNumPedVta, String pIndicadorNC) {
        String vRetorno = "N";
        RacVtaPedidoVtaCab vtaPedidoVtaCabLocal = null;
        List<RacVtaPedidoVtaDet> lstPedidoVtaDetLocal = null;
        List<RacVtaCompPago> lstCompPagoLocal = null;
        List<RacVtaFormaPagoPedido> lstFormaPagoPedidoLocal = null;
        List<RacConPedVta> lstConPedVtaLocal = null;

        try {
            //1.0 Abre conexion local
            daoConvenioBTLMF.openConnection();
            //1.1 Lee cabecera
            vtaPedidoVtaCabLocal = daoConvenioBTLMF.getPedidoCabLocal(pNumPedVta, pIndicadorNC);
            //1.2 Lee detalle
            lstPedidoVtaDetLocal = daoConvenioBTLMF.getPedidoDetLocal(pNumPedVta, pIndicadorNC);
            //1.3 Lee comprobantes
            lstCompPagoLocal = daoConvenioBTLMF.getCompPagoLocal(pNumPedVta, pIndicadorNC);
            //1.4 Lee formas de pago
            lstFormaPagoPedidoLocal = daoConvenioBTLMF.getFormaPagoPedidoLocal(pNumPedVta, pIndicadorNC);
            //1.5 Lee info convenio
            lstConPedVtaLocal = daoConvenioBTLMF.getConPedVtaLocal(pNumPedVta, pIndicadorNC);
            //1.6 Cierre conexion local
            daoConvenioBTLMF.commit();
        } catch (Exception e) {
            daoConvenioBTLMF.rollback();
            log.error("", e);
            return vRetorno;
        }

        //DAORACConvenioBTLMF daoRACConvenioBTLMF = null;
        daoRACConvenioBTLMF = null;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.MYBATIS;
        //ERIOS 07.10.2015 Determina si esta activo el Gestor
        if(DlgProcesar.getIndGestorTx().equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }
        try {
            //2.0 Abre conexion RAC
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(tipo);
            daoRACConvenioBTLMF.openConnection();
            //2.0 Borra anteriores
            daoRACConvenioBTLMF.deletePedidoCabRAC(pNumPedVta);
            //2.1 Graba cabecera
            daoRACConvenioBTLMF.savePedidoCabRAC(vtaPedidoVtaCabLocal);
            //2.2 Graba detalle
            daoRACConvenioBTLMF.savePedidoDetRAC(lstPedidoVtaDetLocal);
            //2.3 Graba comprobantes
            daoRACConvenioBTLMF.saveCompPagoRAC(lstCompPagoLocal);
            //2.4 Graba info convenio
            daoRACConvenioBTLMF.saveFormaPagoPedidoRAC(lstFormaPagoPedidoLocal);
            //2.5 Graba info convenio
            daoRACConvenioBTLMF.saveConPedVtaRAC(lstConPedVtaLocal);
            //2.6 Cierra conexion RAC
            daoRACConvenioBTLMF.commit();
        } catch (Exception e) {
            // kmoncada 14.08.2014 controla el error de duplicidad de indice.
            if (e instanceof PersistenceException) {
                SQLException sqlExcep = (SQLException)e.getCause();
                if (sqlExcep.getErrorCode() == 1) {
                    vRetorno = "S";
                }
            }

            try {
                daoRACConvenioBTLMF.rollback();
            } catch (NullPointerException x) {
                ;
            } catch (Exception c) {
                log.error("", c);
            }

            if (vRetorno.equals("N")) {
                log.error("", e);
                return vRetorno;
            }
        }

        vRetorno = "S";
        return vRetorno;
    }

    public String grabarCobroPedidoRac(String pNumPedVta, String pCodLocal, String pCodGrupoCia,
                                       String indNotaCredito) throws Exception{
        String resultado = "N";
        daoRACConvenioBTLMF = null;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.MYBATIS;
        //ERIOS 07.10.2015 Determina si esta activo el Gestor
        if(DlgProcesar.getIndGestorTx(false).equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }
        try {
            //Abre conexion RAC
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(tipo);
            daoRACConvenioBTLMF.openConnection();

            //Cobra Pedido en RAC
            resultado = daoRACConvenioBTLMF.cobrarPedidoRAC(pCodLocal, pCodGrupoCia, pNumPedVta, indNotaCredito);
            daoRACConvenioBTLMF.commit();
        } catch (Exception ex) {
            daoRACConvenioBTLMF.rollback();
            //log.error("",ex);            
            resultado = "N";

            String emailEnvio = "";
            try {
                emailEnvio = DBVentas.getDestinatarioFarmaEmail(ConstantsPtoVenta.FARMA_EMAIL_COBRO_ELECTONICO);
            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, emailEnvio,
                                                   "ERROR DE COBRO EN RAC ", "ERROR DE COBRO CONVENIOS EN RAC",
                                                   "Mensaje "+tipo+":<br>" +
                                                   "Se produjo un error al grabar un pedido en RAC :<br>" + "IP PC: " +
                                                   FarmaVariables.vIpPc + "<br>" + "Correlativo : " + pNumPedVta +
                                                   "<br>" + "Error: " + ex.getMessage(),
                    "");
            } catch (Exception exc) {
                log.error("",exc);
            }
            
            //throw ex;
        }
        return resultado;
    }

    public String actualizaFechaProcesoRac(String pCodGrupoCia, String pCodLocal, String pNumPedVta) {
        String resultado = "N";
        try {
            //1.0 Abre conexion local
            daoConvenioBTLMF.openConnection();
            resultado = daoConvenioBTLMF.actualizaFechaProcesoRac(pCodLocal, pCodGrupoCia, pNumPedVta);
            //1.6 Cierre conexion local
            daoConvenioBTLMF.commit();
        } catch (Exception ex) {
            daoConvenioBTLMF.rollback();
            log.error("",ex);

            resultado = "N";
        }
        return resultado;
    }

    /**
     * Se obtiene beneficiario remoto
     * @author ERIOS
     * @since 2.4.8
     * @param pCodCliConv
     * @return
     */
    public ArrayList<String> obtieneBeneficRemoto(String pCodCliConv) {
        ArrayList<ArrayList<String>> lstListado = null;
        ArrayList<String> benef = null;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.MYBATIS;
        //ERIOS 07.10.2015 Determina si esta activo el Gestor
        if(DlgProcesar.getIndGestorTx(false).equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }
        try {
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(tipo);
            daoRACConvenioBTLMF.openConnection();
            lstListado = daoRACConvenioBTLMF.obtieneBenefRemoto(pCodCliConv);
            daoRACConvenioBTLMF.commit();
            benef = lstListado.get(0);
        } catch (Exception e) {
            daoRACConvenioBTLMF.rollback();
            log.error("", e);
        }
        return benef;
    }
    
    /**
     * Imprime voucher de copia de guia
     * @author ERIOS
     * @since 21.07.2015
     * @param pNumPedVta
     * @return
     */
    public boolean impresionVoucherCopiaGuia(String pNumPedVta, String pSecCompPago){
        boolean resultado = true;
        try{
            daoConvenioBTLMF.openConnection();
            List<BeanImpresion> lista = daoConvenioBTLMF.getVoucherCopiaGuia(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, pNumPedVta, pSecCompPago);
            daoConvenioBTLMF.commit();
            
            if(lista!=null){
                new UtilityImpCompElectronico().impresionTermica(lista, null);
            }else{
                resultado = false;
            }            
        }catch(Exception ex){
            daoConvenioBTLMF.rollback();
            log.error("", ex);            
            resultado = false;
        }
        
        return resultado;
    }    
    
    public boolean impresionVoucher(String pNumPedVta){
        boolean resultado = true;
        try{
            daoConvenioBTLMF.openConnection();
            List<BeanImpresion> lista = daoConvenioBTLMF.getVoucher(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, pNumPedVta);
            daoConvenioBTLMF.commit();
            
            if(lista!=null){
                new UtilityImpCompElectronico().impresionTermica(lista, null);
            }else{
                resultado = false;
            }            
        }catch(Exception ex){
            daoConvenioBTLMF.rollback();
            log.error("", ex);            
            resultado = false;
        }
        
        return resultado;
    } 
    
    public String consultarSaldCreditoBenif(JDialog pDialogo) {

        log.debug("Metodo: consultarSaldCreditoBenif");
        String resp = "N";
        //String montoCosumo = "";
        double montoConsumo = 0;
        double LineCredito = 0;
        double montoSaldo = 0;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.FRAMEWORK;
        //ERIOS 07.10.2015 Determina si esta activo el Gestor
        if(DlgProcesar.getIndGestorTx(false).equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }
        try {
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(tipo);
            daoRACConvenioBTLMF.openConnection();
            montoConsumo = daoRACConvenioBTLMF.obtieneComsumoBenif();
            daoRACConvenioBTLMF.commit();
            
            log.debug("montoCosumo>>>>>>>>>>>>>>>>>>>><" + montoConsumo);
            //montoConsumo =  FarmaUtility.getDecimalNumber(montoCosumo);
            LineCredito = FarmaUtility.getDecimalNumber(VariablesConvenioBTLMF.vLineaCredito);
            montoSaldo = LineCredito - montoConsumo;

            VariablesConvenioBTLMF.vMontoSaldo = FarmaUtility.formatNumber(montoSaldo);

            log.debug("LCré:S/." + FarmaUtility.formatNumber(LineCredito));
            log.debug("Sald:S/." + FarmaUtility.formatNumber(montoSaldo));
            log.debug("Cons:S/." + FarmaUtility.formatNumber(montoConsumo));

            VariablesConvenioBTLMF.vDatoLCredSaldConsumo =
                    "LCrédito:S/. " + FarmaUtility.formatNumber(LineCredito) + "    Sald:S/. " +
                    FarmaUtility.formatNumber(montoSaldo) + "    Cons:S/. " + FarmaUtility.formatNumber(montoConsumo);


            log.debug("VariablesConvenioBTLMF.vDatoLCredSaldConsumo:" + VariablesConvenioBTLMF.vDatoLCredSaldConsumo);


        } catch (Exception sqlException) {
            daoRACConvenioBTLMF.rollback();
            log.error("", sqlException);

            FarmaUtility.showMessage(pDialogo, sqlException.getMessage(), null);
        }
        return resp;
    }

    /**
     * @author ERIOS
     * @since 09.10.2015
     * @param pIndNotaCred
     * @return
     */
    public String anularPedidoRac(String pIndNotaCred) {
        String strRetorno;
        // KMONCADA 09.12.2014 ANULA CON NOTA DE CREDITO EN CASO DE ELECTRONICO ACTIVO
        if (EposVariables.vFlagComprobanteE) {
            pIndNotaCred = "S";
        }
        TipoImplementacionDAO tipo = TipoImplementacionDAO.FRAMEWORK;
        //ERIOS 07.10.2015 Determina si esta activo el Gestor
        if(DlgProcesar.getIndGestorTx().equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }
        try {
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(tipo);
            daoRACConvenioBTLMF.openConnection();
            strRetorno = daoRACConvenioBTLMF.anularPedidoRac(pIndNotaCred);
            daoRACConvenioBTLMF.commit();
        } catch (Exception sqlException) {
            daoRACConvenioBTLMF.rollback();
            log.error("", sqlException);
            strRetorno = "N";
        }
        return strRetorno;
    }

    public boolean isExisteComprobanteEnRAC(String pCodGrupoCia, String pCodLocal, String pTipoComprobante, String pNumComprobante,
                                         String pIndComprobanteElectronico) {
        int resultado;
        boolean bRetorno;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.FRAMEWORK;
        //ERIOS 07.10.2015 Determina si esta activo el Gestor
        if(DlgProcesar.getIndGestorTx().equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }
        try {
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(tipo);
            daoRACConvenioBTLMF.openConnection();
            resultado = daoRACConvenioBTLMF.isExisteComprobanteEnRAC(pCodGrupoCia,pCodLocal,pTipoComprobante,pNumComprobante,pIndComprobanteElectronico);
            daoRACConvenioBTLMF.commit();
            
            if (resultado == 0){
                bRetorno = false;
            }else{
                bRetorno = true;
            }
        } catch (Exception sqlException) {
            daoRACConvenioBTLMF.rollback();
            log.error("", sqlException);
            bRetorno = false;
        }
        return bRetorno;
    }

    public boolean validacionPedidoRAC(String pCodLocal, String pNumPedVta, String pCodConvenio,
                                              String pCodCliente, String pTipoDoc, double pMonto,
                                              String pVtaFin) throws Exception {
        String resultado;
        boolean bRetorno;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.FRAMEWORK;
        //ERIOS 07.10.2015 Determina si esta activo el Gestor
        if(DlgProcesar.getIndGestorTx().equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }
        try {
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(tipo);
            daoRACConvenioBTLMF.openConnection();
            resultado = daoRACConvenioBTLMF.validacionPedidoRAC(pCodLocal, pNumPedVta, pCodConvenio,
                                              pCodCliente, pTipoDoc, pMonto, pVtaFin);
            daoRACConvenioBTLMF.commit();
            
            if ("S".equalsIgnoreCase(resultado)){
                bRetorno = true;
            }else{
                bRetorno = false;
            }
        } catch (Exception e) {
            daoRACConvenioBTLMF.rollback();
            //log.error("", sqlException);
            //bRetorno = false;
            throw e;
        }
        return bRetorno;
    }
}
