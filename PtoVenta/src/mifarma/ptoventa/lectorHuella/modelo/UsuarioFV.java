package mifarma.ptoventa.lectorHuella.modelo;

import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class UsuarioFV {
    
    private String secUsuLocal;
    private String nroDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private byte[] izquierdaMenique;
    private byte[] izquierdaAnular;
    private byte[] izquierdaMedio;
    private byte[] izquierdaIndice;
    private byte[] izquierdaPulgar;
    private byte[] derechaPulgar;
    private byte[] derechaIndice;
    private byte[] derechaMedio;
    private byte[] derechaAnular;
    private byte[] derechaMenique;
    
    private EnumMap<DPFPFingerIndex, DPFPTemplate> templateHuellas;
    
    public UsuarioFV() {
        super();
        templateHuellas = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
    }
    
    
    /*
    public UsuarioFV(final List<Object> ctorArgs){
        this.secUsuLocal = (String)ctorArgs.get(0);
        this.nroDocumento = (String)ctorArgs.get(1);
        this.apellidoPaterno = (String)ctorArgs.get(2);
        this.apellidoMaterno = (String)ctorArgs.get(3);
        this.nombres = (String)ctorArgs.get(4);/*
        templateHuellas = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
        for (int k=0; k<DPFPFingerIndex.values().length; k++){
            if(ctorArgs.get(5+k) != null){
                DPFPFingerIndex finger = DPFPFingerIndex.values()[k];
                DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate((byte[])ctorArgs.get(5+k));
                templateHuellas.put(finger, template);
            }
        }
    }*/

    public void setSecUsuLocal(String secUsuLocal) {
        this.secUsuLocal = secUsuLocal;
    }

    public String getSecUsuLocal() {
        return secUsuLocal;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombres() {
        return nombres;
    }

    private void registrarTemplateHuella(int indiceDedo, byte[] huellaDactilar){
        DPFPFingerIndex finger = DPFPFingerIndex.values()[indiceDedo];
        DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate(huellaDactilar);
        templateHuellas.put(finger, template);
    }
    public void setIzquierdaMenique(byte[] izquierdaMenique) {
        this.izquierdaMenique = izquierdaMenique;
        registrarTemplateHuella(0, izquierdaMenique);
    }

    public void setIzquierdaAnular(byte[] izquierdaAnular) {
        this.izquierdaAnular = izquierdaAnular;
        registrarTemplateHuella(1, izquierdaAnular);
    }

    public void setIzquierdaMedio(byte[] izquierdaMedio) {
        this.izquierdaMedio = izquierdaMedio;
        registrarTemplateHuella(2, izquierdaMedio);
    }

    public void setIzquierdaIndice(byte[] izquierdaIndice) {
        this.izquierdaIndice = izquierdaIndice;
        registrarTemplateHuella(3, izquierdaIndice);
    }

    public void setIzquierdaPulgar(byte[] izquierdaPulgar) {
        this.izquierdaPulgar = izquierdaPulgar;
        registrarTemplateHuella(4, izquierdaPulgar);
    }

    public void setDerechaPulgar(byte[] derechaPulgar) {
        this.derechaPulgar = derechaPulgar;
        registrarTemplateHuella(5, derechaPulgar);
    }

    public void setDerechaIndice(byte[] derechaIndice) {
        this.derechaIndice = derechaIndice;
        registrarTemplateHuella(6, derechaIndice);
    }

    public void setDerechaMedio(byte[] derechaMedio) {
        this.derechaMedio = derechaMedio;
        registrarTemplateHuella(7, derechaMedio);
    }

    public void setDerechaAnular(byte[] derechaAnular) {
        this.derechaAnular = derechaAnular;
        registrarTemplateHuella(8, derechaAnular);
    }

    public void setDerechaMenique(byte[] derechaMenique) {
        this.derechaMenique = derechaMenique;
        registrarTemplateHuella(9, derechaMenique);
    }

    public void setTemplateHuellas(EnumMap<DPFPFingerIndex, DPFPTemplate> templateHuellas) {
        this.templateHuellas = templateHuellas;
    }
    
    public DPFPTemplate getTemplate(DPFPFingerIndex finger) {
        return templateHuellas.get(finger);
    }
}
