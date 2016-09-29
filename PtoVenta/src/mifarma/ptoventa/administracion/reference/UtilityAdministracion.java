package mifarma.ptoventa.administracion.reference;

import javax.swing.JDialog;

import mifarma.common.FarmaTableModel;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;

import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityAdministracion {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityAdministracion.class);
    
    public static boolean actualizarIPTipoLocalVenta(JDialog pJDialog, String pIpPc, String pCodTipoLocalVenta){
        boolean isActualizo = false;
        try{
            DBImpresoras.actualizarIpTipoLocalVenta(FarmaVariables.vCodGrupoCia, 
                                                    FarmaVariables.vCodLocal, 
                                                    pIpPc, 
                                                    pCodTipoLocalVenta, 
                                                    FarmaVariables.vIdUsu);
            FarmaUtility.aceptarTransaccion();
            isActualizo = true;
            FarmaUtility.showMessage(pJDialog, "Se ha actualizado el tipo de venta correctamente.\n"+
                                               "Reiniciar el aplicativo para cargar el listado de productos.", null);
        }catch(Exception ex){
            FarmaUtility.liberarTransaccion();
            log.error("", ex);
            FarmaUtility.showMessage(pJDialog, "Error al actualizar IP.\nReintente, si problema persiste comuniquese con Mesa de Ayuda.", null);
        }
        return isActualizo;
    }
}
