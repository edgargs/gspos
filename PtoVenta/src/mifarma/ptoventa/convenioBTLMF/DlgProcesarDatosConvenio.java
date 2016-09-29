package mifarma.ptoventa.convenioBTLMF;


import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.Map;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.FacadeConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesarDatosConvenio extends JDialogProgress {

    private static final Logger log = LoggerFactory.getLogger(DlgProcesarDatosConvenio.class);

    private Map medico = null;
    private String pCodConvenio;
    private JDialog pDialogo;
    private String pTipoProceso = "";
    private FarmaTableModel tableModelListaDatos;
    private boolean pConCheck;

    private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
    private FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();

    public DlgProcesarDatosConvenio(Frame frame, String string, boolean b) {
        super(frame, string, b);
    }

    public DlgProcesarDatosConvenio() {
        super();
    }

    @Override
    public void ejecutaProceso() {
        switch (pTipoProceso) {
        case "LISTA":
            lista();
            break;
        default:
            obtenerConvenio();
        }
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

    void setTipoProceso(String pTipoProceso) {
        this.pTipoProceso = pTipoProceso;
    }

    private void obtenerConvenio() {
        try {
            medico = DBConvenioBTLMF.obtenerConvenio(pCodConvenio);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener convenio" + sqlException.getMessage(), null);
        }
        log.debug("msg:" + medico.get(ConstantsConvenioBTLMF.COL_COD_MEDICO));
    }

    private void lista() {
        try {
            try {
                //ERIOS 2.3.3 Logica conexion en linea
                if (VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO)) {

                    //1 Verifica indicador beneficiarios en linea
                    String vRetorno = DBConvenioBTLMF.getIndBeneficiarioLinea();
                    String[] vInds = vRetorno.split("@");
                    String vDataRimac = vInds[0];
                    String vBenefLinea = vInds[1];
                    if (vBenefLinea.equals(FarmaConstants.INDICADOR_S) || vDataRimac.equals("1")) {
                        //2 Verifica conexion con RAC
                        if (true) {
                            //3 Cantidad en BBDD Local
                            //int cantLocal = DBConvenioBTLMF.obtieneCantListaBenefLocal();
                            //4 Cantidad en RAC
                            //int cantRemoto = DBConvenioBTLMF.obtieneCantListaBenefRemoto();
                            //if(cantRemoto > cantLocal){
                            //5 Retorna listado RAC
                            facadeConvenioBTLMF.listarBeneficRemoto(tableModelListaDatos);
                            return;
                            //}
                        }
                    }
                }
            } catch (Exception sql) {
                log.error("", sql);
                FarmaUtility.showMessage(pDialogo, "Ha ocurrido un error al consultar los beneficiarios en línea.\n" +
                        "No puede continuar con el pedido.", null);
                return;
            }

            //6 Retorna listado BBDD Local
            DBConvenioBTLMF.listaDatos(tableModelListaDatos, pConCheck);

        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(pDialogo, "Error al obtener convenio" + e.getMessage(), null);
        }
    }

    void setPtableModelListaDatos(FarmaTableModel tableModelListaDatos) {
        this.tableModelListaDatos = tableModelListaDatos;
    }

    void setPConCheck(boolean pConCheck) {
        this.pConCheck = pConCheck;
    }
}
