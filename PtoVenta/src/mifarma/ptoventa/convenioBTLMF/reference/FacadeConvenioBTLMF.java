package mifarma.ptoventa.convenioBTLMF.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.convenioBTLMF.dao.DAOConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.dao.DAORACConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.dao.FactoryConvenioBTLMF;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;

import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;

import org.apache.ibatis.exceptions.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeConvenioBTLMF {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeConvenioBTLMF.class);
    
    private DAOConvenioBTLMF daoConvenioBTLMF;
    
    public FacadeConvenioBTLMF(){
        super();
        daoConvenioBTLMF = FactoryConvenioBTLMF.getDAOConvenioBTLMF(FactoryConvenioBTLMF.Tipo.MYBATIS);
    }
    
    public ArrayList<ArrayList<String>> listarBeneficRemoto(FarmaTableModel tableModelListaDatos)
    {   ArrayList<ArrayList<String>> lstListado = null;
        try
        {   lstListado = daoConvenioBTLMF.listaBenefRemoto();
            
            tableModelListaDatos.clearTable();
            tableModelListaDatos.data = lstListado;
        }
        catch (Exception e)
        {   log.error("",e);
        }
        return lstListado;
    }
    
    public String grabarTemporalesRAC(String pNumPedVta){
        return grabarTemporalesRAC(pNumPedVta,FarmaConstants.INDICADOR_N);
    }
    
	/**
	 * Graba tablas temporales en RAC
	 * @author ERIOS
	 * @since 2.4.4
	 */
    public String grabarTemporalesRAC(String pNumPedVta, String pIndicadorNC){
        String vRetorno = "N";
        RacVtaPedidoVtaCab vtaPedidoVtaCabLocal = null;
        List<RacVtaPedidoVtaDet> lstPedidoVtaDetLocal = null;
        List<RacVtaCompPago> lstCompPagoLocal = null;
        List<RacVtaFormaPagoPedido> lstFormaPagoPedidoLocal = null;
        List<RacConPedVta> lstConPedVtaLocal = null;
        
        try{
            //1.0 Abre conexion local
            daoConvenioBTLMF.openConnection();
            //1.1 Lee cabecera
            vtaPedidoVtaCabLocal = daoConvenioBTLMF.getPedidoCabLocal(pNumPedVta,pIndicadorNC);
            //1.2 Lee detalle
            lstPedidoVtaDetLocal = daoConvenioBTLMF.getPedidoDetLocal(pNumPedVta,pIndicadorNC);
            //1.3 Lee comprobantes
            lstCompPagoLocal = daoConvenioBTLMF.getCompPagoLocal(pNumPedVta,pIndicadorNC);
            //1.4 Lee formas de pago
            lstFormaPagoPedidoLocal = daoConvenioBTLMF.getFormaPagoPedidoLocal(pNumPedVta,pIndicadorNC);
            //1.5 Lee info convenio
            lstConPedVtaLocal = daoConvenioBTLMF.getConPedVtaLocal(pNumPedVta,pIndicadorNC);
            //1.6 Cierre conexion local
            daoConvenioBTLMF.commit();
        }catch(Exception e){
            daoConvenioBTLMF.rollback();
            log.error("",e);
            return vRetorno;
        }
        
        DAORACConvenioBTLMF daoRACConvenioBTLMF = null;
        try{
            //2.0 Abre conexion RAC
            daoRACConvenioBTLMF = FactoryConvenioBTLMF.getDAORACConvenioBTLMF(FactoryConvenioBTLMF.Tipo.MYBATIS);
            daoRACConvenioBTLMF.openConnection();
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
        }catch(Exception e){
            // kmoncada 14.08.2014 controla el error de duplicidad de indice.
            if(e instanceof PersistenceException){
                SQLException sqlExcep = (SQLException)e.getCause();
                if(sqlExcep.getErrorCode()==1){
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
            
            if(vRetorno.equals("N")){
                log.error("",e);
                return vRetorno;
            }
        }
        
        vRetorno = "S";
        return vRetorno;
    }
}
