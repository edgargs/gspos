package mifarma.farmaBean;

public class EntradaBean { 

    private String tipoMensaje;                       //x
    private String tipoTrnscc;                        //x
    private String tipoRecaudacion;                   //x
 
    private String tipoCuentaFrom;
    private String tipoCuentaTo;
    private String monto; //11,2 Formato. 000000000.00  x
    private String auditoria; //ID TABLA                x
    private String terminal; //8                        x
    private String comercio;//15                        x
    private String ubicacion;//40                       x
    private String moneda;
    private String codSucursal; //4                     x
    private String nroCaja; //4                         X

    //CMR
    private String tipDocId;//2
    private String nroDocId;//12
    private String modoPago;//1    0:pago normal;1 pago diferido
    private String nroCuotas; //2
    private String tipDocVenta; //1     1:boleta; 2 :factura
    private String nroDocVenta; //8
    private String idCajero;//8
    private String codAutorizTrans;//5
    private String nroTarjeta;//16
    private String modoIngrDatos;//3

    //claro
    private String telefono;                         //10x
    private String nombreRazSoc;
    private String codGenAdqPpv;
    private String nroDocAutoriz;
    private String numRecargaOrig;
    
    private String codTransExtend;
    private String codPzaRecaudador; //4            x
    private String idAcreedor;// 11                 x   
    private String binProcCobranza; // 11           x
    private String motivoExtorno;    //2             X
    
    private String idAnular;  // 12 de la transacción a anular
    
    /*26092013 Claro servicios*/
    private String productoServicio;
    private String tipoProdServ;    // 1            x  móvil 1; móvilLDI 2; dni 5; fijo 6
    private String prefijo;         // 3
    private String numRecibo;        //16           x
    /*1 campo claro anulacion 18102013*/
    private String auditoriaOrig;      //12             X
    /*14102013 se añade 1 campo: la fecha original de una transaccion para extorno CMR*/
    private String fechaOrig;        //15           x
    //GFonseca. 04/10/2013. Indicador para identificar el ambiente de produccion o desarrollo por medio del SID
    private String indAmbienteSID = "N";
    private String numeroRecarga;
    
    private String codTipoProducto; //ASOSA - 14/07/2014
    
    //INI ASOSA - 28/10/2014 - RECAR - NEX
    private String codComerXCia;
    private String nomComerXCia;
    //FIN ASOSA - 28/10/2014 - RECAR - NEX
    
    //INI ASOSA - 29/10/2014- RECAR - NEX
    private String numPedVenta;
    private String codLocal;
    //FIN ASOSA - 29/10/2014- RECAR - NEX
    
    //INI ASOSA - 06/11/2014- RECAR - NEX
    private String nroOperacionPpv;
    //FIN ASOSA - 06/11/2014- RECAR - NEX


    public EntradaBean() {
       
    }

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public String getTipoTrnscc() {
        return tipoTrnscc;
    }

    public void setTipoTrnscc(String tipoTrnscc) {
        this.tipoTrnscc = tipoTrnscc;
    }

    public String getTipoCuentaFrom() {
        return tipoCuentaFrom;
    }

    public void setTipoCuentaFrom(String tipoCuentaFrom) {
        this.tipoCuentaFrom = tipoCuentaFrom;
    }

    public String getTipoCuentaTo() {
        return tipoCuentaTo;
    }

    public void setTipoCuentaTo(String tipoCuentaTo) {
        this.tipoCuentaTo = tipoCuentaTo;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(String auditoria) {
        this.auditoria = auditoria;
    }

    public String getCodAutorizTrans() {
        return codAutorizTrans;
    }

    public void setCodAutorizTrans(String codAutorizTrans) {
        this.codAutorizTrans = codAutorizTrans;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getNroCaja() {
        return nroCaja;
    }

    public void setNroCaja(String nroCaja) {
        this.nroCaja = nroCaja;
    }

    public String getNroDocId() {
        return nroDocId;
    }

    public void setNroDocId(String nroDocId) {
        this.nroDocId = nroDocId;
    }

    public String getModoPago() {
        return modoPago;
    }

    public void setModoPago(String modoPago) {
        this.modoPago = modoPago;
    }

    public String getNroCuotas() {
        return nroCuotas;
    }

    public void setNroCuotas(String nroCuotas) {
        this.nroCuotas = nroCuotas;
    }

    public String getTipDocVenta() {
        return tipDocVenta;
    }

    public void setTipDocVenta(String tipDocVenta) {
        this.tipDocVenta = tipDocVenta;
    }

    public String getNroDocVenta() {
        return nroDocVenta;
    }

    public void setNroDocVenta(String nroDocVenta) {
        this.nroDocVenta = nroDocVenta;
    }

    public String getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(String idCajero) {
        this.idCajero = idCajero;
    }

    public String getTipDocId() {
        return tipDocId;
    }

    public void setTipDocId(String tipDocId) {
        this.tipDocId = tipDocId;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreRazSoc() {
        return nombreRazSoc;
    }

    public void setNombreRazSoc(String nombreRazSoc) {
        this.nombreRazSoc = nombreRazSoc;
    }

    public String getCodGenAdqPpv() {
        return codGenAdqPpv;
    }

    public void setCodGenAdqPpv(String codGenAdqPpv) {
        this.codGenAdqPpv = codGenAdqPpv;
    }

    public String getNroDocAutoriz() {
        return nroDocAutoriz;
    }

    public void setNroDocAutoriz(String nroDocAutoriz) {
        this.nroDocAutoriz = nroDocAutoriz;
    }

    public String getNumRecargaOrig() {
        return numRecargaOrig;
    }

    public void setNumRecargaOrig(String numRecargaOrig) {
        this.numRecargaOrig = numRecargaOrig;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getCodTransExtend() {
        return codTransExtend;
    }

    public void setCodTransExtend(String codTransExtend) {
        this.codTransExtend = codTransExtend;
    }

    public String getTipoRecaudacion() {
        return tipoRecaudacion;
    }

    public void setTipoRecaudacion(String tipoRecaudacion) {
        this.tipoRecaudacion = tipoRecaudacion;
    }

    public String getModoIngrDatos() {
        return modoIngrDatos;
    }

    public void setModoIngrDatos(String modoIngrDatos) {
        this.modoIngrDatos = modoIngrDatos;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public String getComercio() {
        return comercio;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getCodSucursal() {
        return codSucursal;
    }

    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }

    public String getCodPzaRecaudador() {
        return codPzaRecaudador;
    }

    public void setCodPzaRecaudador(String codPzaRecaudador) {
        this.codPzaRecaudador = codPzaRecaudador;
    }

    public String getIdAcreedor() {
        return idAcreedor;
    }

    public void setIdAcreedor(String idAcreedor) {
        this.idAcreedor = idAcreedor;
    }

    public String getBinProcCobranza() {
        return binProcCobranza;
    }

    public void setBinProcCobranza(String binProcCobranza) {
        this.binProcCobranza = binProcCobranza;
    }

    public String getMotivoExtorno() {
        return motivoExtorno;
    }

    public void setMotivoExtorno(String motivoExtorno) {
        this.motivoExtorno = motivoExtorno;
    }

    public String getIdAnular() {
        return idAnular;
    }

    public void setIdAnular(String idAnular) {
        this.idAnular = idAnular;
    }

    public String getProductoServicio() {
        return productoServicio;
    }

    public void setProductoServicio(String productoServicio) {
        this.productoServicio = productoServicio;
    }

    public String getTipoProdServ() {
        return tipoProdServ;
    }

    public void setTipoProdServ(String tipoProdServ) {
        this.tipoProdServ = tipoProdServ;
    }

    public String getNumRecibo() {
        return numRecibo;
    }

    public void setNumRecibo(String numRecibo) {
        this.numRecibo = numRecibo;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getIndAmbienteSID() {
        return indAmbienteSID;
    }

    public void setIndAmbienteSID(String strIndSID) {
        this.indAmbienteSID = strIndSID;
    }

    public String getFechaOrig() {
        return fechaOrig;
    }

    public void setFechaOrig(String fechaOrig) {
        this.fechaOrig = fechaOrig;
    }

    public String getAuditoriaOrig() {
        return auditoriaOrig;
    }

    public void setAuditoriaOrig(String auditoriaOrig) {
        this.auditoriaOrig = auditoriaOrig;
    }

    public String getNumeroRecargaOrig(){
        return numeroRecarga;
    }
    
    public void setNumeroRecargaOrig(String numeroRecarga) {
        this.numeroRecarga = numeroRecarga;
    }


    //INI ASOSA - 14/07/2014
    public String getCodTipoProducto() {
        return codTipoProducto;
    }

    public void setCodTipoProducto(String codTipoProducto) {
        this.codTipoProducto = codTipoProducto;
    }
    //FIN ASOSA - 14/07/2014


    public String getCodComerXCia() {
        return codComerXCia;
    }

    public void setCodComerXCia(String codComerXCia) {
        this.codComerXCia = codComerXCia;
    }

    public String getNomComerXCia() {
        return nomComerXCia;
    }

    public void setNomComerXCia(String nomComerXCia) {
        this.nomComerXCia = nomComerXCia;
    }

    public String getNumPedVenta() {
        return numPedVenta;
    }

    public void setNumPedVenta(String numPedVenta) {
        this.numPedVenta = numPedVenta;
    }

    public String getCodLocal() {
        return codLocal;
    }

    public void setCodLocal(String codLocal) {
        this.codLocal = codLocal;
    }

    public String getNroOperacionPpv() {
        return nroOperacionPpv;
    }

    public void setNroOperacionPpv(String nroOperacionPpv) {
        this.nroOperacionPpv = nroOperacionPpv;
    }
}
