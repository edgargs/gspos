package mifarma.farmaUtil;


import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;

import java.io.IOException;

import java.math.BigInteger;

import java.text.ParseException;

import mifarma.farmaCTL.Despachador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LectorBitmaps { 
    
    private static final Logger log = LoggerFactory.getLogger(LectorBitmaps.class);
    
    public LectorBitmaps() {
        super();
    }
    static int hexToBin2(String s) {
      return Integer.parseInt(new BigInteger(s, 16).toString(2));
    }
    
    public static void verBitmaps(String cadena) {
        LectorBitmaps lectorBitmaps = new LectorBitmaps();
        //String cadena;// = "F23880010E8180000000000000000001";
        //       cadena = "F2208481A8E080000000004000000018";
        char[] arr = cadena.toCharArray();
        StringBuilder sb =new StringBuilder();
        for(char letra:arr){
            sb.append(String.format("%04d",hexToBin2(letra+"")));
        }
        arr = sb.toString().toCharArray();
        int contador=0;
        for(char campo:arr){
            contador++;
            if(campo == '1'){
                log.debug("Campo "+contador);
            }
        }
    }
    
    static void verTrama(String trama){
        MessageFactory mfact;
        byte[] byteTramaOut = trama.getBytes();
        IsoMessage im;
        try {
            //mfact = ConfigParser.createDefault();
            mfact = ConfigParser.createFromClasspathConfig(Constantes.FILE_CONFIG);
            im = mfact.parseMessage(byteTramaOut ,0);
            Despachador.imprimirVariablesParseadas(im);
        } catch (IOException e) {
        //} catch (UnsupportedEncodingException ex) {
        } catch (ParseException ex1) {
        } catch (Exception ex2) {
        }
    }
    
    public static void main(String[] arg){
        //verTrama("0210F27880010A808000000000000000001F1622222200000000000000000000000005000920105916000000001112221059160920092006500010111222      140254841204900000000000002hanv       999");        
        verBitmaps("F238C481A8E180000000000000000001");
    }
}
