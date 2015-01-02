package mifarma.ptoventa.encuesta.reference;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JInputDialog;

import java.awt.Frame;

import java.util.List;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.encuesta.dao.DAOEncuesta;
import mifarma.ptoventa.encuesta.dao.MBEncuesta;
import mifarma.ptoventa.encuesta.modelo.BeanEncuesta;
import mifarma.ptoventa.encuesta.modelo.BeanPreguntaEncuesta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Control de encuestas
 * @author ERIOS
 * @since 3.0.0
 */
public class FacadeEncuesta {

    private static final Logger log = LoggerFactory.getLogger(FacadeEncuesta.class);

    private DAOEncuesta daoEncuesta;

    private Frame myParentFrame;

    public FacadeEncuesta() {
        super();
        daoEncuesta = new MBEncuesta();
    }

    public FacadeEncuesta(Frame myParentFrame) {
        super();
        daoEncuesta = new MBEncuesta();
        this.myParentFrame = myParentFrame;
    }

    public void verificaEncuesta(String pCodProd) {

        List<BeanEncuesta> lstEncuesta;
        BeanEncuesta beanEncuesta;
        List<BeanPreguntaEncuesta> lstPreguntas = null;
        try {
            daoEncuesta.openConnection();
            lstEncuesta = daoEncuesta.verificaEncuesta(pCodProd);
            if (lstEncuesta.size() > 0) {
                beanEncuesta = lstEncuesta.get(0);
                lstPreguntas = daoEncuesta.obtenerPreguntas(beanEncuesta.getCodigoEncuesta());
            }
            daoEncuesta.commit();
        } catch (Exception e) {
            daoEncuesta.rollback();
            log.error("", e);
        }

        //2.0 Realiza encuesta
        if (lstPreguntas != null && lstPreguntas.size() > 0) {
            boolean graba = true;
            for (BeanPreguntaEncuesta rpta : lstPreguntas) {
                pregunta(rpta);
                if (!validacion(rpta)) {
                    //graba = false; //ERIOS 26.11.2014 Siempre graba las respuestas.
                    break;
                }
            }
            if (graba) {
                grabarEncuesta(lstPreguntas, pCodProd);
            }
        }
    }

    private void grabarEncuesta(List<BeanPreguntaEncuesta> lstPreguntas, String pCodProd) {

        BeanPreguntaEncuesta BeanPrguntaEncuesta;
        int vencuesta, vsecpregunta;
        int secuencial;

        String vrespuesta = "";

        try {
            daoEncuesta.openConnection();
            BeanPrguntaEncuesta = lstPreguntas.get(0);
            secuencial =
                    daoEncuesta.grabaCabEncuesta(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal,
                                                 FarmaVariables.vIdUsu, pCodProd,
                                                 BeanPrguntaEncuesta.getCodigoEncuesta());

            for (BeanPreguntaEncuesta Enc : lstPreguntas) {
                vencuesta = Enc.getCodigoEncuesta();
                vsecpregunta = Enc.getSecPregunta();
                vrespuesta = Enc.getRespuesta();

                daoEncuesta.grabaDetalleEncuesta(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia,
                                                 FarmaVariables.vCodLocal, secuencial, pCodProd, vencuesta,
                                                 vsecpregunta, vrespuesta);

            }
            daoEncuesta.commit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void pregunta(BeanPreguntaEncuesta rpta) {
        switch (rpta.getTipoPregunta()) {
        case "SN":
            muestraSN(rpta);
            break;
        case "DT":
            muestraDT(rpta);
            break;
        }
    }

    private void muestraSN(BeanPreguntaEncuesta rpta) {
        boolean b = JConfirmDialog.rptaConfirmDialog(myParentFrame, rpta.getDescPegunta());
        if (b) {
            rpta.setRespuesta("SI");
        } else {
            rpta.setRespuesta("NO");
        }
    }

    private void muestraDT(BeanPreguntaEncuesta rpta) {
        String s = JInputDialog.showInputDialog(myParentFrame, rpta.getDescPegunta(), rpta.getTipoValidacion());
        rpta.setRespuesta(s);
    }

    private boolean validacion(BeanPreguntaEncuesta rpta) {
        boolean bValida = true;
        if (!(rpta.getTipoValidacion() == null || rpta.getTipoValidacion().trim().equals(""))) {
            switch (rpta.getTipoPregunta()) {
            case "SN":
                bValida = validaSN(rpta);
                break;
                //case "DT": bValida = validaDT(rpta); break; //ERIOS 26.11.2014 Se valida en la ventana de dialogo.
            }
        }
        return bValida;
    }

    private boolean validaSN(BeanPreguntaEncuesta rpta) {
        boolean bValida = true;
        try {
            bValida = rpta.getTipoValidacion().trim().equals(rpta.getRespuesta());
        } catch (Exception e) {
            log.error("", e);
        }
        return bValida;
    }

    private boolean validaDT(BeanPreguntaEncuesta rpta) {
        boolean bValida = true;
        try {
            switch (rpta.getTipoValidacion().trim()) {
            case "NM":
                bValida = FarmaUtility.isInteger(rpta.getRespuesta().trim());
                /*if(bValida){
                    FarmaUtility.showMessage(myParentFrame, "El dato que ingrese debe ser un número.", null);
                }*/
                break;
            case "AF":
                break;
            }
        } catch (Exception e) {
            bValida = false;
            log.error("", e);
        }
        return bValida;
    }
}
