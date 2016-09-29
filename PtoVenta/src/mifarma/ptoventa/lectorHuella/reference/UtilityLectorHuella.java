package mifarma.ptoventa.lectorHuella.reference;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;

import com.digitalpersona.onetouch.verification.DPFPVerification;

import com.gs.mifarma.worker.JDialogProgress;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.lectorHuella.modelo.UsuarioFV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityLectorHuella {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityLectorHuella.class);
    
    public static UsuarioFV obtenerUsuario(JDialog pJDialog, String pSecUsuario){
        UsuarioFV usuario = null;
        try{
            FacadeLectorHuella facade = new FacadeLectorHuella();
            usuario = facade.obtenerUsuario(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pSecUsuario);
        }catch(Exception ex){
            FarmaUtility.showMessage(pJDialog, "Lector Huella:\nError al obtener datos del usuario.", null);
            usuario = null;
        }
        return usuario;
    }
    
    public static void guardarHuella(DPFPTemplate pTemplate, String pSecUsuLocal, int posicionHuella)throws Exception{
        ByteArrayInputStream datosHuella = new ByteArrayInputStream(pTemplate.serialize());
        //Integer tamañoHuella = pTemplate.serialize().length;
        byte[] bHuella = new byte[datosHuella.available()];
        datosHuella.read(bHuella);
        FacadeLectorHuella facade = new FacadeLectorHuella();
        facade.registrarHuellaDactilar(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pSecUsuLocal, bHuella, posicionHuella);
    }
    
    public static List<UsuarioFV> obtenerListaUsuario(){
        List<UsuarioFV> lista = null;
        try{
            FacadeLectorHuella facade = new FacadeLectorHuella();
            lista = facade.obtenerListaUsuario(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal);
        
        }catch(Exception ex){
            lista = new ArrayList<UsuarioFV>();
        }
        return lista;
    }
    
    public static int obtenerCantidadRepiteHuella(){
        FacadeLectorHuella facade = new FacadeLectorHuella();
        return facade.obtenerCantidadRepiteHuella();
    }
    
    public static List<Boolean[]> obtieneHuellasSolicitar(JDialog pJDialog) {
        ArrayList<Boolean[]> lstSolicitaHuellas = new ArrayList<Boolean[]>();
        Boolean[] isRequiereDedoIzq;
        Boolean[] isRequiereDedoDer;
        try {
            FacadeLectorHuella facade = new FacadeLectorHuella();
            String resultado = facade.obtieneHuellasSolicitar();
            String[] array = resultado.split("\\|");
            String[] manoIzq = array[0].split(",");
            isRequiereDedoIzq = new Boolean[manoIzq.length];
            for (int i = 0; i < manoIzq.length; i++) {
                isRequiereDedoIzq[i] = ("1".equalsIgnoreCase(manoIzq[i]));
            }

            String[] manoDer = array[1].split(",");
            isRequiereDedoDer = new Boolean[manoDer.length];
            for (int i = 0; i < manoDer.length; i++) {
                //isRequiereDedoDer[manoDer.length - (i + 1)] = ("1".equalsIgnoreCase(manoDer[i]));
                isRequiereDedoDer[i] = ("1".equalsIgnoreCase(manoDer[i]));
            }
        } catch (Exception ex) {
            log.error("", ex);
            isRequiereDedoIzq = new Boolean[]{false, false, false, false, false};
            isRequiereDedoDer = new Boolean[]{false, true, false, false, false};
        }
        lstSolicitaHuellas.add(isRequiereDedoIzq);
        lstSolicitaHuellas.add(isRequiereDedoDer);
        return lstSolicitaHuellas;
    }
    
    

}
