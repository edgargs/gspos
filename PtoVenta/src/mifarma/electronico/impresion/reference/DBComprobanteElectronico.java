package mifarma.electronico.impresion.reference;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.puntos.reference.VariablesPuntos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBComprobanteElectronico {

    private static final Logger log = LoggerFactory.getLogger(DBComprobanteElectronico.class);

    public static List getDatosImpresion(String pVersionFV, String numPedidoVta, String secCompPago,
                                         boolean isReimpresion) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedidoVta);
        parametros.add(secCompPago);
        parametros.add(pVersionFV);
        if (isReimpresion)
            parametros.add("S");
        else
            parametros.add("N");
        if(VariablesPuntos.frmPuntos!=null){ //validacion cuando el objete no fue construido ltavara 01/04/2015
            parametros.add((VariablesPuntos.frmPuntos.getTarjetaBean() == null) ? 0 : VariablesPuntos.frmPuntos.getTarjetaBean().getAhorroTotal());    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        }else{
            parametros.add(0);    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        }
        //KMONCADA 23.06.2015 NUMERO DE DOCUMENTO DE LA TARJETA DE PUNTOS
        if(VariablesPuntos.frmPuntos != null){
            if(VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                if(VariablesPuntos.frmPuntos.getTarjetaBean().getDni()!=null){
                    parametros.add(VariablesPuntos.frmPuntos.getTarjetaBean().getDni());
                }else{
                    parametros.add(" ");
                }
            }else{
                parametros.add(" ");
            }
        }else{
            parametros.add(" ");
        }
        log.info("FARMA_EPOS.IMP_COMP_ELECT(?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_EPOS.IMP_COMP_ELECT(?,?,?,?,?,?,?,?)", parametros);

    }

    public static String getMarcaLocal(String pCodGrupoCia, String pCodLocal) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        log.info("FARMA_EPOS.GET_MARCA_LOCAL(?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.GET_MARCA_LOCAL(?,?)", parametros);
    }
}
