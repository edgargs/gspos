package mifarma.ptoventa.pinpad.visa;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import mifarma.common.FarmaUtility;


public class GeneradorTramaEnvio {
    private String tipoOperacion = "";
    private Double monto = 0.0;
    private Double propina = 0.0;
    private String tipoMoneda = "";
    private String codTienda = "";
    private String codCaja = "";

    public GeneradorTramaEnvio() {
        super();
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getPropina() {
        return propina;
    }

    public void setPropina(Double propina) {
        this.propina = propina;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getCodTienda() {
        return codTienda;
    }

    public void setCodTienda(String codTienda) {
        this.codTienda = codTienda;
    }

    public String getCodCaja() {
        return codCaja;
    }

    public void setCodCaja(String codCaja) {
        this.codCaja = codCaja;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String generarTrama() {
        StringBuffer tramaDatos = new StringBuffer();

        if (tipoOperacion != null && tipoOperacion != "")
            tramaDatos.append(FarmaUtility.caracterIzquierda(tipoOperacion, 2, "0"));
        else
            tramaDatos.append(VariablesPinpad.OP_FINANCIERA);

        if (monto != null && monto != 0) {
            tramaDatos.append(VariablesPinpad.PREF_ENVIO_MONTO);
            tramaDatos.append(FarmaUtility.caracterIzquierda(devolverNumSinPuntoDecimal(monto), 12, "0"));
            tramaDatos.append(VariablesPinpad.SEPARADOR);
        }
        if (propina != null && propina != 0) {
            tramaDatos.append(VariablesPinpad.PREF_ENVIO_PROPINA);
            tramaDatos.append(FarmaUtility.caracterIzquierda(devolverNumSinPuntoDecimal(propina), 12, "0"));
            tramaDatos.append(VariablesPinpad.SEPARADOR);
        }
        if (tipoMoneda != null && tipoMoneda != "") {
            tramaDatos.append(VariablesPinpad.PREF_ENVIO_TIPO_MONEDA);
            tramaDatos.append(FarmaUtility.caracterIzquierda(tipoMoneda,
                                                             VariablesPinpad.longSubCamposEnvio.get(VariablesPinpad.PREF_ENVIO_TIPO_MONEDA),
                                                             "0"));
            tramaDatos.append(VariablesPinpad.SEPARADOR);
        }
        if (codTienda != null && codTienda != "") {
            tramaDatos.append(VariablesPinpad.PREF_ENVIO_COD_TIENDA);
            tramaDatos.append(FarmaUtility.caracterIzquierda(codTienda,
                                                             VariablesPinpad.longSubCamposEnvio.get(VariablesPinpad.PREF_ENVIO_COD_TIENDA),
                                                             "0"));
            tramaDatos.append(VariablesPinpad.SEPARADOR);
        }
        if (codCaja != null && codCaja != "") {
            tramaDatos.append(VariablesPinpad.PREF_ENVIO_COD_CAJA);
            tramaDatos.append(FarmaUtility.caracterIzquierda(codCaja,
                                                             VariablesPinpad.longSubCamposEnvio.get(VariablesPinpad.PREF_ENVIO_COD_CAJA),
                                                             "0"));
            tramaDatos.append(VariablesPinpad.SEPARADOR);
        }
        return tramaDatos.toString();
    }

    private String devolverNumSinPuntoDecimal(Double m) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("####.00", simbolos);
        return formateador.format(m).replace(".", "");
    }
}
