package mifarma.electronico.epos.reference;

/**
 * Esta clase permite crear constantes.
 * @author: Lais Tavara
 * @version: 16/07/2014
 **/
public class EposConstante {

    public static final String MSJ_EXITO = "EXITO";
    public static final String MSJ_ERROR = "ERROR";

    public static final class tipoClienteConvenio {

        public static final String BENEFICIARIO = "1";
        public static final String EMPRESA = "2";

    }

    public static final class tipoDocumentoFarma {

        public static final String BOLETA = "01";
        public static final String FACTURA = "02";
        public static final String NOTA_CREDITO = "04";
        public static final String TICKET_BOLETA = "05";
        public static final String TICKET_FACTURA = "06";
        public static final String GRL = "03";
    }


    public static final class tipoDocumentoSunat {

        public static final String BOLETA = "3";
        public static final String FACTURA = "1";
        public static final String NOTA_CREDITO = "7";

    }

    public static final class tipoMensaje {

        public static final String CONFIGURACION = "1";
        public static final String GENERA_DE = "2";
        public static final String CONFIRMAR_DE = "3";

    }


}
