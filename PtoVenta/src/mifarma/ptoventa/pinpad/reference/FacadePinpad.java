package mifarma.ptoventa.pinpad.reference;

import java.sql.SQLException;

import java.util.HashMap;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;

import mifarma.ptoventa.lealtad.dao.DAOLealtad;
import mifarma.ptoventa.lealtad.dao.MBLealtad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadePinpad {
    
    private static final Logger log = LoggerFactory.getLogger(FacadePinpad.class);
        
    public FacadePinpad() {
        super();
    }
    
    public boolean verificaAnulacionPinpad(JDialog pJDialog, String pNumPedVta){
        HashMap<String, String> resultado = new HashMap<String, String>();
        boolean bRetorno = true;
        DBPinpad.consFormaPagoPedido(pNumPedVta, resultado, "ANU");

        if (resultado.size() == 4) {
            //Marca las NCR generadas como usadas
            try {
                DBCaja.marcaUsoNCR(pNumPedVta,pNumPedVta);
                //FarmaUtility.aceptarTransaccion();
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                log.error("",e);
                bRetorno = false;
                FarmaUtility.showMessage(pJDialog, "Ha ocurrido un error al validar la anulacion de voucher pinpad.\n"+e.getMessage(), null);
            }
        }
        return bRetorno;
    }
    
    public boolean verificaUsoNCR(JDialog pJDialog, String pNumPedVta){
        boolean bRetorno = false;
        DAOLealtad daoLealtad = new MBLealtad();
        try {
            daoLealtad.openConnection();            
            String uso;
            uso = daoLealtad.verificaUsoNCR(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal,pNumPedVta,"ORI");
            if(uso.equals(FarmaConstants.INDICADOR_S)){
                FarmaUtility.showMessage(pJDialog, "La NCR ya fue usada. No puede anular el voucher.", null);
            }else{
                bRetorno = true;
            }
        } catch (Exception ex) {            
            log.error("", ex);
            FarmaUtility.showMessage(pJDialog, "Ha ocurrido un error al consultar la NCR.\n"+ex.getMessage(), null);
        }finally{
            daoLealtad.rollback();
        }
        return bRetorno;
    }
    
}
