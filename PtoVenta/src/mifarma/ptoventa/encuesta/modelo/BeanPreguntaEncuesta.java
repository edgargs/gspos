package mifarma.ptoventa.encuesta.modelo;

public class BeanPreguntaEncuesta {

    private int codigoEncuesta;
    private int secPregunta;
    private String descPegunta;
    private String tipoPregunta;
    private String tipoValidacion;
    private String respuesta;

    public BeanPreguntaEncuesta() {
        super();
    }

    public void setCodigoEncuesta(int codigoEncuesta) {
        this.codigoEncuesta = codigoEncuesta;
    }

    public int getCodigoEncuesta() {
        return codigoEncuesta;
    }

    public void setSecPregunta(int secPregunta) {
        this.secPregunta = secPregunta;
    }

    public int getSecPregunta() {
        return secPregunta;
    }

    public void setDescPegunta(String descPegunta) {
        this.descPegunta = descPegunta;
    }

    public String getDescPegunta() {
        return descPegunta;
    }

    public void setTipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public String getTipoPregunta() {
        return tipoPregunta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setTipoValidacion(String tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }

    public String getTipoValidacion() {
        return tipoValidacion;
    }
}
