package mifarma.ptoventa.pinpad.reference;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.pinpad.visa.VariablesPinpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DummyPinpad {

    private static final Logger log = LoggerFactory.getLogger(DummyPinpad.class);

    private Double monto = 0.0;
    private String montoStr = "";
    private Double propina = 0.0;
    private String propinaStr = "";
    private String tipoMoneda = "";
    private String codTienda = "";
    private String codCaja = "";

    public DummyPinpad() {
        super();
    }

    public Integer fiOpenPort(String configFileName) {
        if (configFileName.length() > 0)
            return VariablesPinpad.RET_OK;
        else
            return VariablesPinpad.RET_NOK;
    }

    public Integer fiClosePort() {
        return VariablesPinpad.RET_OK;
    }

    public Integer fiStartOperation(String pucTipoOperation, int iTimeOut, StringBuffer pucResponse) {
        String tipoOper = "";
        String tramaTemp = pucTipoOperation;
        String[] segmentos;
        try {
            String inicioTrama = tramaTemp.substring(0, 1);
            tramaTemp = tramaTemp.substring(1, tramaTemp.length());

            Integer longTramaDatos = Integer.parseInt(tramaTemp.substring(0, 3));
            tramaTemp = tramaTemp.substring(3, tramaTemp.length());

            String tipoMensaje = tramaTemp.substring(0, 1);
            tramaTemp = tramaTemp.substring(1, tramaTemp.length());

            String indInformacionPend = tramaTemp.substring(0, 1);
            tramaTemp = tramaTemp.substring(1, tramaTemp.length());

            String tramaDatos = tramaTemp.substring(0, longTramaDatos);
            tramaTemp = tramaTemp.substring(longTramaDatos, tramaTemp.length());

            String finTrama = tramaTemp.substring(0, 1);
            tramaTemp = tramaTemp.substring(1, tramaTemp.length());

            String digitoChequeo = tramaTemp.substring(0, 1);
            tramaTemp = tramaTemp.substring(1, tramaTemp.length());

            //se valida el ingreso
            String tipoOperacion = tramaDatos.substring(0, 2);
            tramaDatos = tramaDatos.substring(2, tramaDatos.length());

            //se traduce la tramaDatos
            String[] tramaDividida = tramaDatos.split(VariablesPinpad.SEPARADOR);
            for (int i = 0; i < tramaDividida.length; i++) {
                String temp = tramaDividida[i];
                String prefijo = temp.substring(0, 1);
                String dato = temp.substring(1, temp.length());

                //se valida la información obtenida anteriormente
                Integer longDato = VariablesPinpad.longSubCamposEnvio.get(prefijo);
                if (VariablesPinpad.PREF_ENVIO_MONTO.equals(prefijo)) {
                    montoStr = dato;
                    monto = Double.parseDouble(dato) / 100;
                }
                if (VariablesPinpad.PREF_ENVIO_PROPINA.equals(prefijo)) {
                    propinaStr = dato;
                    propina = Double.parseDouble(dato) / 100;
                }
                if (VariablesPinpad.PREF_ENVIO_TIPO_MONEDA.equals(prefijo)) {
                    tipoMoneda = dato; //.substring(0, longDato);
                }
                if (VariablesPinpad.PREF_ENVIO_COD_TIENDA.equals(prefijo)) {
                    codTienda = dato; //.substring(0, longDato);
                }
                if (VariablesPinpad.PREF_ENVIO_COD_CAJA.equals(prefijo)) {
                    codCaja = dato; //.substring(0, longDato);
                }
            }

            //se arma la trama de respuesta
            //cambiar esto. Ejemplo Transacción Financiera
            StringBuffer sbt = new StringBuffer();
            sbt.append("A00");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("BFIN DE OPERACION");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("CLUIS ENRIQUE LEIVA BAZAN");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("D000001");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("E0001");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("F511842******2251");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("G0416");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("H280213");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("I1111");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("J01");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("K1");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append(VariablesPinpad.PREF_RETOR_PROPINA);
            sbt.append(propinaStr);
            //sbt.append("L000000000000");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("M00");
            sbt.append(VariablesPinpad.SEPARADOR);
            //sbt.append("N1111");      //??? no indica en el documento
            //sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("O12345678");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("P01");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("Q00");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append("R00");
            sbt.append(VariablesPinpad.SEPARADOR);
            //se genera el idUnico
            String idTemp1 = String.valueOf(new Double(Math.floor(Math.random() * 100000)).intValue());
            sbt.append(VariablesPinpad.PREF_RETOR_ID_UNICO);
            sbt.append(FarmaUtility.caracterIzquierda(idTemp1, 5, "0"));
            sbt.append(FarmaUtility.caracterIzquierda(idTemp1, 5, "0"));
            sbt.append(FarmaUtility.caracterIzquierda(idTemp1, 5, "0"));
            //sbt.append("S123456789012345");
            sbt.append(VariablesPinpad.SEPARADOR);
            sbt.append(VariablesPinpad.PREF_RETOR_MONTO_CUOTA);
            sbt.append(montoStr);
            //sbt.append("T000000010000");
            sbt.append(VariablesPinpad.SEPARADOR);

            StringBuffer sb = new StringBuffer();
            sb.append(VariablesPinpad.INICIO_TRAMA);
            sb.append(FarmaUtility.caracterIzquierda(sbt.toString().length(), 3, "0"));
            sb.append(VariablesPinpad.TIPO_MENSJ_PINPAD_SERV);
            sb.append(VariablesPinpad.IND_ULTIMO_MENSAJE);
            sb.append(sbt.toString());
            sb.append(VariablesPinpad.FIN_TRAMA);
            sb.append(" ");

            pucResponse.append(sb.toString());
            log.debug("Trama: " + pucResponse.toString());

            //se espera los segundos indicados en "iTimeOut"
            Thread.sleep(iTimeOut * 1000);
        } catch (Exception e) {
            log.error("", e);
        }

        return VariablesPinpad.RET_OK;
    }

    public Integer fiGetStatus(String bufferRet_out, String maxSizeBuffer_in) {
        return VariablesPinpad.RET_OK;
    }
}
