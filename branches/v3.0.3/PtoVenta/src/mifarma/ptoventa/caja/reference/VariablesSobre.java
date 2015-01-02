package mifarma.ptoventa.caja.reference;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VariablesSobre {
    private static final Logger log = LoggerFactory.getLogger(VariablesSobre.class);

    public VariablesSobre() {

    }
    public static ArrayList listaCompsDesfasados = new ArrayList();
    /*
    VariablesCaja.vCodTipoMon
    VariablesCaja.vCodFormaPagoTmp
    VariablesCaja.vDescFormaPagoTmp
    VariablesCaja.vCodMonedaPagoTmp
    VariablesCaja.vDescMonedaPagoTmp
    VariablesCaja.vValMontoPagadoTmp
    VariablesCaja.vValTotalPagadoTmp
    VariablesCaja.vSecMovCaja
    VariablesCaja.vValTotalPagadoTmp
     * */

    public static String vCodTipoMon = "";
    public static String vCodFormaPagoTmp = "";
    public static String vDescFormaPagoTmp = "";


    public static String vValMontoPagadoTmp = "";
    public static String vValTotalPagadoTmp = "";
    public static String vSecMovCaja = "";
    public static String pTipMoneda = "";
    public static String vDescTipoMon = "";

    public static String vValTipoCambioPedido = "";

}
