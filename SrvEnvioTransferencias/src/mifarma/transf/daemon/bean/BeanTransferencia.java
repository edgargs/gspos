package mifarma.transf.daemon.bean;


public class BeanTransferencia {
    
    private String codGrupoCia;
    private String codCia;
    private String codLocal;
    private String numNotaEs;
    private String nomSistema;
    private String codLocalDestino;

    public void setCodGrupoCia(String codGrupoCia) {
        this.codGrupoCia = codGrupoCia;
    }

    public String getCodGrupoCia() {
        return codGrupoCia;
    }

    public void setCodCia(String codCia) {
        this.codCia = codCia;
    }

    public String getCodCia() {
        return codCia;
    }

    public void setCodLocal(String codLocal) {
        this.codLocal = codLocal;
    }

    public String getCodLocal() {
        return codLocal;
    }

    public void setNumNotaEs(String numNotaEs) {
        this.numNotaEs = numNotaEs;
    }

    public String getNumNotaEs() {
        return numNotaEs;
    }

    public void setNomSistema(String nomSistema) {
        this.nomSistema = nomSistema;
    }

    public String getNomSistema() {
        return nomSistema;
    }

    public void setCodLocalDestino(String codLocalDestino) {
        this.codLocalDestino = codLocalDestino;
    }

    public String getCodLocalDestino() {
        return codLocalDestino;
    }
    
    @Override
    public String toString(){
        return codCia+"-"+codLocal+"-"+numNotaEs+"-"+codLocalDestino+"-"+nomSistema;
    }
}
