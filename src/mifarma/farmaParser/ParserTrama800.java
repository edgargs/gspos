package mifarma.farmaParser;


import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.TramaBean;

import mifarma.farmaUtil.Constantes;
import mifarma.farmaUtil.Funciones;


public class ParserTrama800 extends FarmaParserTramas{

    public ParserTrama800(){
        super();
    }
 
    public ParserTrama800(EntradaBean entrada){
        super(entrada);
    }

    private TramaBean crearTrama800(){ 
        String auditoria  = Funciones.formatoCeroIzq(6,getEntrada().getAuditoria());
        TramaBean trama = new TramaBean();
        trama.setAuditoria_DE11(auditoria);
        trama.setCodRedReenvio_DE33(Constantes.COD_ID_ADQUIRIENTE);  
        trama.setCodInfoAdminRed_DE70(Constantes.ECHO_TEST);
        return trama; 
    }
    
    public IsoMessage generarTramaEcho800() throws Exception{
        leerConfiguracion();
        TramaBean trama = crearTrama800();
        IsoMessage solic = mfact.newMessage(Constantes.MSG_BYTE_800);
        //El campo 7 se carga en automatico
        solic.setValue(11,
                       trama.getAuditoria_DE11(),
                       IsoType.NUMERIC, 6);
        solic.setValue(33, 
                       trama.getCodRedReenvio_DE33(), 
                       IsoType.LLVAR, 0);        
        solic.setValue(70,
                       trama.getCodInfoAdminRed_DE70(), 
                       IsoType.NUMERIC, 3);
        return solic;
    }

}
