package mifarma.ptoventa.convenioBTLMF.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cnx.FarmaVentaCnxUtility;
import mifarma.ptoventa.convenioBTLMF.dao.DAOConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.dao.DAORACConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.dao.FactoryConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
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
        daoConvenioBTLMF = FactoryConvenioBTLMF.getDAOConvenioBTLMF(FactoryConvenioBTLMF.Tipo.MYBATIS);
    }

    public ArrayList<ArrayList<String>> listarBeneficRemoto(FarmaTableModel tableModelListaDatos) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoConvenioBTLMF.listaBenefRemoto();

            tableModelListaDatos.clearTable();
            tableModelListaDatos.data = lstListado;
        } catch (Exception e) {
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
        try {
            //2.0 Abre conexion RAC
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(FactoryConvenioBTLMF.Tipo.MYBATIS);
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
                                       String indNotaCredito) {
        String resultado = "N";
        daoRACConvenioBTLMF = null;
        try {
            //Abre conexion RAC
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(FactoryConvenioBTLMF.Tipo.MYBATIS);
            daoRACConvenioBTLMF.openConnection();

            //Cobra Pedido en RAC
            resultado = daoRACConvenioBTLMF.cobrarPedidoRAC(pCodLocal, pCodGrupoCia, pNumPedVta, indNotaCredito);
            daoRACConvenioBTLMF.commit();
        } catch (Exception ex) {
            try {
                log.error("Error en el cobro pedido RAC--> \n" +
                        ex);
                daoRACConvenioBTLMF.rollback();
            } catch (NullPointerException x) {
                ;
            } catch (Exception c) {
                log.error("Error en el cobro pedido RAC(2)--> \n" +
                        c);
            }
            resultado = "N";

            String emailEnvio = "";
            try {
                emailEnvio = DBVentas.getDestinatarioFarmaEmail(ConstantsPtoVenta.FARMA_EMAIL_COBRO_ELECTONICO);
            } catch (Exception exc) {
                log.error("" + exc.getMessage());
            }

            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodCia, FarmaVariables.vCodLocal, emailEnvio,
                                                   "ERROR DE COBRO EN RAC", "ERORR DE COBRO CONVENIOS EN RAC",
                                                   "Mensaje:<br>" +
                                                   "Se produjo un error al grabar un pedido en RAC :<br>" + "IP PC: " +
                                                   FarmaVariables.vIpPc + "<br>" + "Correlativo : " + pNumPedVta +
                                                   "<br>" + "Error: " + ex.getMessage(),
                    //"daubilluz@gmail.com");
                    "");
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
            try {
                daoConvenioBTLMF.rollback();
                log.info("Error al actualizar fecha del pedido del proceso de cobro en RAC \n" +
                        ex);
            } catch (NullPointerException x) {
                ;
            } catch (Exception e) {
                log.info("Error al actualizar fecha del pedido del proceso de cobro en RAC(2) \n" +
                        ex);
            }
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
        try {
            lstListado = daoConvenioBTLMF.obtieneBenefRemoto(pCodCliConv);

            benef = lstListado.get(0);
        } catch (Exception e) {
            log.error("", e);
        }
        return benef;
    }

    public void actualizaProformaRAC(String pCodCia, String pCodLocal, String pNumProforma, String pCodLocalSap,
                                     String pNumComprobantes,
                                     String fechaEnvio) { //String pNumPedVta, String pCodLocal, String pCodGrupoCia){
        DAORACConvenioBTLMF daoRACConvenioBTLMF = null;
        try {
            //Abre conexion RAC
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(FactoryConvenioBTLMF.Tipo.MYBATIS);
            daoRACConvenioBTLMF.openConnection();
            daoRACConvenioBTLMF.actualizaProformaRAC(pCodCia, pCodLocal, pNumProforma, pCodLocalSap, pNumComprobantes,
                                                     fechaEnvio);

            daoRACConvenioBTLMF.commit();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
