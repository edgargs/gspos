package mifarma.ptoventa.inventario.dto;

import java.util.Date;

public class ProveedorDTO {

    private String codProve;
    private String nomProve;
    private String estado;
    private String dirProve;
    private String telProve;
    private String rucProve;
    private String giroNegocio;
    private String cuentaAsocia;
    private String grupoTesore;
    private String condiPago;
    private String viaPago;
    private String montPedido;
    private String plazoEntrega;
    private Date fecCreacion;
    private String usuCreacion;
    private Date fecModificacion;
    private String usuModificacion;


    public void setCodProve(String codProve) {
        this.codProve = codProve;
    }

    public String getCodProve() {
        return codProve;
    }

    public void setNomProve(String nomProve) {
        this.nomProve = nomProve;
    }

    public String getNomProve() {
        return nomProve;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setDirProve(String dirProve) {
        this.dirProve = dirProve;
    }

    public String getDirProve() {
        return dirProve;
    }

    public void setTelProve(String telProve) {
        this.telProve = telProve;
    }

    public String getTelProve() {
        return telProve;
    }

    public void setRucProve(String rucProve) {
        this.rucProve = rucProve;
    }

    public String getRucProve() {
        return rucProve;
    }

    public void setGiroNegocio(String giroNegocio) {
        this.giroNegocio = giroNegocio;
    }

    public String getGiroNegocio() {
        return giroNegocio;
    }

    public void setCuentaAsocia(String cuentaAsocia) {
        this.cuentaAsocia = cuentaAsocia;
    }

    public String getCuentaAsocia() {
        return cuentaAsocia;
    }

    public void setGrupoTesore(String grupoTesore) {
        this.grupoTesore = grupoTesore;
    }

    public String getGrupoTesore() {
        return grupoTesore;
    }

    public void setCondiPago(String condiPago) {
        this.condiPago = condiPago;
    }

    public String getCondiPago() {
        return condiPago;
    }

    public void setViaPago(String viaPago) {
        this.viaPago = viaPago;
    }

    public String getViaPago() {
        return viaPago;
    }

    public void setMontPedido(String montPedido) {
        this.montPedido = montPedido;
    }

    public String getMontPedido() {
        return montPedido;
    }

    public void setPlazoEntrega(String plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }

    public String getPlazoEntrega() {
        return plazoEntrega;
    }

    public void setFecCreacion(Date fecCreacion) {
        this.fecCreacion = fecCreacion;
    }

    public Date getFecCreacion() {
        return fecCreacion;
    }

    public void setUsuCreacion(String usuCreacion) {
        this.usuCreacion = usuCreacion;
    }

    public String getUsuCreacion() {
        return usuCreacion;
    }

    public void setFecModificacion(Date fecModificacion) {
        this.fecModificacion = fecModificacion;
    }

    public Date getFecModificacion() {
        return fecModificacion;
    }

    public void setUsuModificacion(String usuModificacion) {
        this.usuModificacion = usuModificacion;
    }

    public String getUsuModificacion() {
        return usuModificacion;
    }
}
