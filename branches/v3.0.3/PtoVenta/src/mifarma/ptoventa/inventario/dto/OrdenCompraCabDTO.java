package mifarma.ptoventa.inventario.dto;

import java.util.ArrayList;
import java.util.Date;

public class OrdenCompraCabDTO {

    private String codGrupoCia;
    private String codCia;
    private String codLocal;
    private String codOrdenCompra;
    private String codProv;
    private String codFormaPago;
    private String descFormaPago;
    private Date fecVencOc;
    private Integer cantDias;
    private Date fecProgEntre;
    private Date fecRecepcion;
    private String tipMoneda;
    private Integer cantItem;
    private Integer porcIgvCompPago;
    private String estaOcCab;
    private Integer valorTotalOcCab;
    private String tipoOrigenOc;
    private Date fecProceSab;
    private Date fecProceCe;
    private Integer ImpAfectado;
    private Integer ImpInaAfectado;
    private Date fecIni;
    private String procOcCab;
    private String tipCompCab;
    private String numDocCab;
    private String serDocCab;
    private Integer valParTotalOcCab;
    private String usuaCreado;
    private Date fechaCreado;
    private String usuaModifi;
    private Date fechaModifi;
    private ProveedorDTO provedorDTO;
    private ArrayList<OrdenCompraDetDTO> listOrdenCompr;


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

    public void setCodProv(String codProv) {
        this.codProv = codProv;
    }

    public String getCodProv() {
        return codProv;
    }

    public void setCodFormaPago(String codFormaPago) {
        this.codFormaPago = codFormaPago;
    }

    public String getCodFormaPago() {
        return codFormaPago;
    }

    public void setDescFormaPago(String descFormaPago) {
        this.descFormaPago = descFormaPago;
    }

    public String getDescFormaPago() {
        return descFormaPago;
    }

    public void setFecVencOc(Date fecVencOc) {
        this.fecVencOc = fecVencOc;
    }

    public Date getFecVencOc() {
        return fecVencOc;
    }

    public void setCantDias(Integer cantDias) {
        this.cantDias = cantDias;
    }

    public Integer getCantDias() {
        return cantDias;
    }

    public void setFecProgEntre(Date fecProgEntre) {
        this.fecProgEntre = fecProgEntre;
    }

    public Date getFecProgEntre() {
        return fecProgEntre;
    }

    public void setFecRecepcion(Date fecRecepcion) {
        this.fecRecepcion = fecRecepcion;
    }

    public Date getFecRecepcion() {
        return fecRecepcion;
    }

    public void setTipMoneda(String tipMoneda) {
        this.tipMoneda = tipMoneda;
    }

    public String getTipMoneda() {
        return tipMoneda;
    }

    public void setCantItem(Integer cantItem) {
        this.cantItem = cantItem;
    }

    public Integer getCantItem() {
        return cantItem;
    }

    public void setPorcIgvCompPago(Integer porcIgvCompPago) {
        this.porcIgvCompPago = porcIgvCompPago;
    }

    public Integer getPorcIgvCompPago() {
        return porcIgvCompPago;
    }

    public void setEstaOcCab(String estaOcCab) {
        this.estaOcCab = estaOcCab;
    }

    public String getEstaOcCab() {
        return estaOcCab;
    }

    public void setValorTotalOcCab(Integer valorTotalOcCab) {
        this.valorTotalOcCab = valorTotalOcCab;
    }

    public Integer getValorTotalOcCab() {
        return valorTotalOcCab;
    }

    public void setTipoOrigenOc(String tipoOrigenOc) {
        this.tipoOrigenOc = tipoOrigenOc;
    }

    public String getTipoOrigenOc() {
        return tipoOrigenOc;
    }

    public void setFecProceSab(Date fecProceSab) {
        this.fecProceSab = fecProceSab;
    }

    public Date getFecProceSab() {
        return fecProceSab;
    }

    public void setFecProceCe(Date fecProceCe) {
        this.fecProceCe = fecProceCe;
    }

    public Date getFecProceCe() {
        return fecProceCe;
    }

    public void setImpAfectado(Integer ImpAfectado) {
        this.ImpAfectado = ImpAfectado;
    }

    public Integer getImpAfectado() {
        return ImpAfectado;
    }

    public void setImpInaAfectado(Integer ImpInaAfectado) {
        this.ImpInaAfectado = ImpInaAfectado;
    }

    public Integer getImpInaAfectado() {
        return ImpInaAfectado;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setProcOcCab(String procOcCab) {
        this.procOcCab = procOcCab;
    }

    public String getProcOcCab() {
        return procOcCab;
    }

    public void setTipCompCab(String tipCompCab) {
        this.tipCompCab = tipCompCab;
    }

    public String getTipCompCab() {
        return tipCompCab;
    }

    public void setNumDocCab(String numDocCab) {
        this.numDocCab = numDocCab;
    }

    public String getNumDocCab() {
        return numDocCab;
    }

    public void setSerDocCab(String serDocCab) {
        this.serDocCab = serDocCab;
    }

    public String getSerDocCab() {
        return serDocCab;
    }

    public void setValParTotalOcCab(Integer valParTotalOcCab) {
        this.valParTotalOcCab = valParTotalOcCab;
    }

    public Integer getValParTotalOcCab() {
        return valParTotalOcCab;
    }

    public void setUsuaCreado(String usuaCreado) {
        this.usuaCreado = usuaCreado;
    }

    public String getUsuaCreado() {
        return usuaCreado;
    }

    public void setUsuaModifi(String usuaModifi) {
        this.usuaModifi = usuaModifi;
    }

    public String getUsuaModifi() {
        return usuaModifi;
    }

    public void setProvedorDTO(ProveedorDTO provedorDTO) {
        this.provedorDTO = provedorDTO;
    }

    public ProveedorDTO getProvedorDTO() {
        return provedorDTO;
    }

    public void setListOrdenCompr(ArrayList<OrdenCompraDetDTO> listOrdenCompr) {
        this.listOrdenCompr = listOrdenCompr;
    }

    public ArrayList<OrdenCompraDetDTO> getListOrdenCompr() {
        return listOrdenCompr;
    }


    public void setCodOrdenCompra(String codOrdenCompra) {
        this.codOrdenCompra = codOrdenCompra;
    }

    public String getCodOrdenCompra() {
        return codOrdenCompra;
    }


    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaModifi(Date fechaModifi) {
        this.fechaModifi = fechaModifi;
    }

    public Date getFechaModifi() {
        return fechaModifi;
    }
}
