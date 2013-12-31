package mifarma.ptoventa.convenioBTLMF;

import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.Map;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgProcesarDatosConvenio extends JDialogProgress {
    
    private static final Logger log = LoggerFactory.getLogger(DlgProcesarDatosConvenio.class);
    
    private Map medico = null;
    private String pCodConvenio;
    private JDialog pDialogo;
    
    public DlgProcesarDatosConvenio(Frame frame, String string, boolean b) {
        super(frame, string, b);
    }

    public DlgProcesarDatosConvenio() {
        super();
    }

    @Override
    public void ejecutaProceso() {
        try {
           medico = DBConvenioBTLMF.obtenerConvenio(pCodConvenio);

        } catch (SQLException sqlException) {
            log.error("",sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener convenio"+sqlException.getMessage(),
                                     null);


        }

        log.debug("msg:" +
                               medico.get(ConstantsConvenioBTLMF.COL_COD_MEDICO));
    }

    public void setMedico(Map medico) {
        this.medico = medico;
    }

    public Map getMedico() {
        return medico;
    }

    public void setPCodConvenio(String pCodConvenio) {
        this.pCodConvenio = pCodConvenio;
    }

    public String getPCodConvenio() {
        return pCodConvenio;
    }

    public void setPDialogo(JDialog pDialogo) {
        this.pDialogo = pDialogo;
    }

    public JDialog getPDialogo() {
        return pDialogo;
    }
}
