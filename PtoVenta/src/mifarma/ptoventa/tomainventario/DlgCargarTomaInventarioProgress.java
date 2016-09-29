package mifarma.ptoventa.tomainventario;

import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import java.sql.SQLException;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;

import mifarma.ptoventa.controlAsistencia.DlgImprimeHorarioProgress;
import mifarma.ptoventa.tomainventario.reference.DBTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgCargarTomaInventarioProgress extends JDialogProgress {
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgCargarTomaInventarioProgress.class);

    public DlgCargarTomaInventarioProgress(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;   
    }

    @Override
    public void ejecutaProceso() {
        cargarToma();
    }
    private void cargarToma()  {
        
        try{
            DBTomaInv.cargarToma();
            FarmaUtility.aceptarTransaccion();
        
        } catch (SQLException sql) {
        FarmaUtility.liberarTransaccion();
        log.error("", sql);
        FarmaUtility.showMessage(this, "Ocurrió un error al anular la toma :\n" +
                sql.getMessage(), null);
        }
        
    }

}
