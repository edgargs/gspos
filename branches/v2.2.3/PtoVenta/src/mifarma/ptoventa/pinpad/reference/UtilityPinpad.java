package mifarma.ptoventa.pinpad.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityPinpad
{
    private static final Logger log = LoggerFactory.getLogger(UtilityPinpad.class);

    static public String convCadNumDec(String numSinForm)
    {   String resultado = "";
        try
        {   if(numSinForm.length()>2)
                resultado = numSinForm.substring(0,numSinForm.length()-2) + "." +
                            numSinForm.substring(numSinForm.length()-2);
            else
                resultado = "0."+numSinForm;
        }
        catch(Exception e)
        {   resultado = "";
            log.error("",e);
        }
        return resultado;
    }
}
