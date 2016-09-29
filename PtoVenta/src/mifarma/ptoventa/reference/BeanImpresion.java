package mifarma.ptoventa.reference;

import java.io.Serializable;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanImpresion extends HashMap implements Serializable {
    
    
    static final Logger log = LoggerFactory.getLogger(BeanImpresion.class);

    @SuppressWarnings("compatibility:-7370347038733457699")
    private static final long serialVersionUID = 1L;
    
    private String IP_PC;
    private String ID_DOC;
    private String ORDEN;
    private String VALOR;
    private String TIPO_DATO;
    private String TAMANIO;
    private String ALINEACION;
    private String NEGRITA;
    private String SUBRAYADO;
    private String INTERLINEADO;
    private String COLOR_INVERSO;
    private String SALTO_LINEA;
    /*** CCASTILLO 13/07/2016 ***/
    private String LON_PTERMICO;
    /*** CCASTILLO 13/07/2016 ***/


    public void setIP_PC(String IP_PC) {
        this.IP_PC = IP_PC;
    }

    public String getIP_PC() {
        return IP_PC;
    }

    public void setID_DOC(String ID_DOC) {
        this.ID_DOC = ID_DOC;
    }

    public String getID_DOC() {
        return ID_DOC;
    }

    public void setORDEN(String ORDEN) {
        this.ORDEN = ORDEN;
    }

    public String getORDEN() {
        return ORDEN;
    }

    public void setVALOR(String VALOR) {
        this.VALOR = VALOR;
    }

    public String getVALOR() {
        return VALOR;
    }

    public void setTIPO_DATO(String TIPO_DATO) {
        this.TIPO_DATO = TIPO_DATO;
    }

    public String getTIPO_DATO() {
        return TIPO_DATO;
    }

    public void setTAMANIO(String TAMANIO) {
        this.TAMANIO = TAMANIO;
    }

    public String getTAMANIO() {
        return TAMANIO;
    }

    public void setALINEACION(String ALINEACION) {
        this.ALINEACION = ALINEACION;
    }

    public String getALINEACION() {
        return ALINEACION;
    }

    public void setNEGRITA(String NEGRITA) {
        this.NEGRITA = NEGRITA;
    }

    public String getNEGRITA() {
        return NEGRITA;
    }

    public void setSUBRAYADO(String SUBRAYADO) {
        this.SUBRAYADO = SUBRAYADO;
    }

    public String getSUBRAYADO() {
        return SUBRAYADO;
    }

    public void setINTERLINEADO(String INTERLINEADO) {
        this.INTERLINEADO = INTERLINEADO;
    }

    public String getINTERLINEADO() {
        return INTERLINEADO;
    }

    public void setCOLOR_INVERSO(String COLOR_INVERSO) {
        this.COLOR_INVERSO = COLOR_INVERSO;
    }

    public String getCOLOR_INVERSO() {
        return COLOR_INVERSO;
    }

    public void setSALTO_LINEA(String SALTO_LINEA) {
        this.SALTO_LINEA = SALTO_LINEA;
    }

    public String getSALTO_LINEA() {
        return SALTO_LINEA;
    }
    /*** CCASTILLO 13/07/2016 ***/
    public void setLON_PTERMICO(String LON_PTERMICO) {
        this.LON_PTERMICO = LON_PTERMICO;
    }

    public String getLON_PTERMICO() {
        return LON_PTERMICO;
    }
    /*** CCASTILLO 13/07/2016 ***/
    
}
