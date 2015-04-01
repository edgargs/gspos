package mifarma.transf.daemon.bean;


public class BeanLocal {
    
    private String codGrupoCia;
    private String codCia;
    private String codLocal;
    private String ipServidorLocal;
    

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

    public void setIpServidorLocal(String ipServidorLocal) {
        this.ipServidorLocal = ipServidorLocal;
    }

    public String getIpServidorLocal() {
        return ipServidorLocal;
    }
}
