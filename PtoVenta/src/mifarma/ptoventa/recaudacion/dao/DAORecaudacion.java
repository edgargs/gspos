package mifarma.ptoventa.recaudacion.dao;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.ptoventa.reference.DAOTransaccion;


public interface DAORecaudacion extends DAOTransaccion {

    public String grabaCabRecaudacion(String strCodGrupoCia, String strCodCia, String strCodLocal, String pNroTarjeta,
                                      String pTipoRcd, String pTipoPago, String pEstRcd, String pEstCuenta,
                                      String pCodCliente, String pTipMoneda, String pImTotal, String pImTotalPago,
                                      String pImMinPago, String pValTipCamb, String pFechaVencRecau,
                                      String pNomCliente, String pFecVenTrj, String pFecCreaRecauPago,
                                      String pUsuCreaRecauPago, String pFecModRecauPago, String pUsuModRecauPago,
                                      String pCodAutorizacion, String pSecMovCaja, String pNumPedido,
                                      String pTipoProdServ, String pNumRecibo) throws SQLException;

    public ArrayList obtenerRcdPendiente(String strCodGrupoCia, String strCodCia, String strCodLocal,
                                         String strCodRecau) throws SQLException;

    public ArrayList obtenerCodFormsPagoRCD() throws SQLException;

    public void grabaFormPagoRCD(String strCodGrupoCia, String strCodCia, String strCodLocal, String pCodRecau,
                                 String pCodFormaPago, String pImpTotal, String pTipMoneda, String pValTipCambio,
                                 String pValVuelto, String pImTotalPago, String pFecCreaRecauPago,
                                 String pUsuCreaRecauPago, String pFecModRecauPago,
                                 String pUsuModRecauPago) throws Exception;

    public void cambiarIndicadorRCD(String strCodGrupoCia, String strCodCia, String strCodLocal, String pCodRecau,
                                    String pIndEstado, String pUsuModRcd, String pFecModRcd, String pEstImpresion,
                                    Long codTrssc, String estTrsscSix, String codAutorizacion, String secMovCaja,
                                    String strFechaOrigen, String nroCuotas, String fechaVencCuota,
                                    String strMontoPagarCuota) throws Exception;

    public ArrayList obtenerRcdCanceladas(String strCodGrupoCia, String strCodCia, String strCodLocal, String pCodRcd,
                                          String pMonRcd, String pTipocobro) throws Exception;

    public ArrayList obtenerMontoPagarCitiPres(String pCodCliCiti) throws SQLException;

    /**
     * Obtiene una trama de respuesta desde el servidor central en caso de prestamos Citibank
     * @author GFonseca
     * @since 17.Junio.2013
     */
    public String getTramaPresCiti(String opConsulta, String codConsulta, String tipoMoneda, String indTransaccion,
                                   String codLocal) throws SQLException;

    /**
     * Envia una trama de consulta hacia el servidor central en caso de prestamos Citibank y retorna una trama de respuesta
     * @author GFonseca
     * @since 17.Junio.2013
     */
    public String setTramaPresCiti(String trama) throws SQLException;

    /**
     * Procesa la trama recibida desde el servidor central en caso de prestamos Citibank
     * @author GFonseca
     * @since 17.Junio.2013
     */
    public ArrayList obtenerDatosPagarCitiPres(String trama) throws SQLException;

    /**
     * Obtiene el formato html para imprimir la boleta de recaudación
     * @author RLLANTOY
     * @since 19.06.2013
     */
    public String impCompPagoRecaudacion(String pCodGrupoCia, String strCodGrupoCia, String strCodLocal,
                                         String pNumeroRecaudacion) throws Exception;


    /**
     * Obtiene el estado de impresión de una recaudación
     * @author RLLANTOY
     * @since 24.06.2013
     */
    public String getEstadoImpRecaudacion(String pCodGrupoCia, String strCodGrupoCia, String strCodLocal,
                                          String pNumeroRecaudacion) throws SQLException;

    /**
     * Obtiene informacion de tarjeta Recaudacion Citibank
     * @author RLLANTOY
     * @since 14.06.2013
     */
    public ArrayList getInfoTarjetaRecaudacion(String pCodGrupoCia, String pNroTarjeta,
                                               String pTipOrigenPago) throws SQLException;

    /**
     * Actualiza el estado de Impresión de Recaudación
     * @author RLLANTOY
     * @since 24.06.2013
     */
    public void updateEstadoImpRecaudacion(String pCodGrupoCia, String strCodGrupoCia, String strCodLocal,
                                           String pNumeroRecaudacion, String pEstImpRecaudacion) throws SQLException;

    public String anularRecaudacion(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                    String pNumeroRecaudacion, String vNumCaja, String pUsuModRecau, Long codTrsscAnul,
                                    String strCodRecauAnul) throws Exception;

    /**
     * Obtiene el estado de una recaudación
     * @author RLLANTOY
     * @since 24.06.2013
     */
    public String getEstadoRecaudacion(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                       String pNumeroRecaudacion);

    /**
     * Obtiene el tiempo de anulación de una recaudación
     * @author RLLantoy
     * @since 11.07.2013
     */
    public String valTiempoAnulacion(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                     String pNumeroRecaudacion);

    /**
     * Obtiene el tiempo máximo de anulación de una recaudación
     * @author RLLantoy
     * @param pTipoLlave  llave para el "TIEMPO_ANULACION" de cualquier recaudación = "RCD"
     * @since 11.07.2013
     */
    public String tiempoMaxAnulacionRecau(String pTipoLlave);

    public String actualizarMontoCobradoPresCiti(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                                 String pNumeroRecaudacion) throws SQLException;


    /**
     * Registra una transaccion de recaudacion en el servidor ADM para la comunicacion con el SIX
     * @author GFonseca
     * @since 04.Julio.2013
     */
    public Long registrarTrsscConsultaDeudaClaro(String codGrupoCia, String strCodCia, String strCodLocal,
                                                 String strEstTrsscRecau, String strTipMsjRecau, String tipoTrssc,
                                                 String tipoRcd, String terminal, String comercio, String sucursal,
                                                 String ubicacion, String telefono, String tipoProdServ,
                                                 String strUsuario) throws Exception;

    /**
     * Obtiene el estado de una transaccion de recaudacion en el servidor ADM para la comunicacion con el SIX
     * @author GFonseca
     * @since 09.Julio.2013
     */
    public String obtenerEstTrsscReacudacion(Long strCodRecauTrssc, String strModoRecau) throws Exception;

    /**
     * Obtiene una transaccion de respuesta de recaudacion en el servidor ADM para la comunicacion con el SIX
     * @author GFonseca
     * @since 11.Julio.2013
     */
    public ArrayList obtenerDatosTrsscSix(Long strCodRecauTrssc, String strModoRecau) throws Exception;


    /**
     * Registra una transaccion de recaudacion CMR en el servidor ADM para la comunicacion con el SIX
     * @author RLLANTOY
     * @since 24.07.2013
     */
    public Long registrarTrsscConsultaDeudaCMR(String codGrupoCia, String strCodCia, String strCodLocal,
                                               String strEstTrsscRecau, String strTipMsjRecau, String tipoTrssc,
                                               String tipoRcd, String strNroTarjeta,
                                               int numCuotas) throws SQLException;


    /**
     * Registra un pago de recaudacion CMR en el servidor ADM para la comunicacion con el SIX
     * @author GFonseca
     * @since 31.07.2013
     */
    public Long registrarTrsscPagoDeudaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                           String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                           String strTipoRcd, String strNroTarjeta, String strMonto,
                                           String strTerminal, String strComercio, String strUbicacion,
                                           String strNroCuotas, String strNroCaja, String strIdCajero,
                                           String strUsuario, String strCodRecau, String strDniUsu,
                                           double dblTipoCambio, String strTipoMoneda, double dblTotalPagar,
                                           int pRecaudOnline) throws Exception;

    /**
     * Registra un pago de recaudacion Ripley en el servidor ADM para la comunicacion con el SIX
     * @author CVILCA
     * @since 10.10.2013
     */
    public Long registrarTrsscPagoDeudaRipley(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                              String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                              String strTipoRcd, String strNroTarjeta, String strMonto,
                                              String strTerminal, String strComercio, String strUbicacion,
                                              String strNroCuotas, String strNroCaja, String strIdCajero,
                                              String strUsuario, String strCodRecau, String strDniUsu,
                                              double dblTipoCambio, String strTipoMoneda, double dblTotalPagar,
                                              int pRecaudOnline) throws Exception;

    /**
     * Registra un pago de recaudacion CLARO en el servidor ADM para la comunicacion con el SIX
     * @author GFonseca
     * @since 31.07.2013
     */
    public Long registrarTrsscPagoDeudaClaro(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                             String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                             String strTipoRcd, String strMonto, String strTerminal,
                                             String strComercio, String strUbicacion, String strTelefono,
                                             String tipoProdServ, String numRecibo, String strUsuario,
                                             String strCodRecau, String strDniUsu, double dblTipoCambio,
                                             String strTipoMoneda, double dblTotalPagar,
                                             int pRecaudOnline) throws Exception;

    /**
     * Registra una transaccion de anulacion CMR en ADM.
     * @author RLLANTOY
     * @since 24.07.2013
     */
    public Long anularPagoTarjetaCMR(String codGrupoCia, String strCodCia, String strCodLocal, String strTipMsjRecau,
                                     String strEstTrsscRecau, String tipoTrssc, String tipoRcd, String strNroTarjeta,
                                     String numCuotas, String strMonto, String strNumCaja, String strIdCajero,
                                     String strMotExtorno, String strCodTrsscSix, String terminal, String comercio,
                                     String ubicacion, String strUsuario, String strCodRecau, String strDniUsu,
                                     double dblTipoCambio, String strTipoMoneda, String strCodRecauAnular,
                                     String strFechaRecauAnular, String strCodAutorizRecauAnular, double dblTotalPagar,
                                     int pRecaudOnline) throws Exception;


    /**
     * Registra una transaccion de anulacion Ripley en ADM.
     * @author GFONSECA
     * @since 28.10.2013
     */
    public Long anularPagoTarjetaRipley(String codGrupoCia, String strCodCia, String strCodLocal,
                                        String strTipMsjRecau, String strEstTrsscRecau, String tipoTrssc,
                                        String tipoRcd, String strNroTarjeta, String numCuotas, String strMonto,
                                        String strNumCaja, String strIdCajero,
        //String strMotExtorno,
        String strCodTrsscSix, String fechaOrigen, String codAuditoria, String terminal, String comercio,
        String ubicacion, String strUsuario, String strCodRecau, String strDniUsu, double dblTipoCambio,
        String strTipoMoneda, String strCodRecauAnular, String strFechaRecauAnular, String strCodAutorizRecauAnular,
        double dblTotalPagar, int pRecaudOnline) throws Exception;

    /**
     * Anula una transaccion de recaudacion CLARO en el servidor ADM para la comunicacion con el SIX
     * @author RLLANTOY
     * @since 24.07.2013
     */
    public Long anularPagoServicioCLARO(String codAuditoriaPago, String codGrupoCia, String strCodCia,
                                        String strCodLocal, String strTipMsjRecau, String strEstTrsscRecau,
                                        String tipoTrssc, String tipoRcd, String strMonto, String strTerminal,
                                        String strComercio, String strUbicacion, String strCodSucursal,
                                        String strUsuCrea, String strCodRecau, String strDniUsu, double dblTipoCambio,
                                        String strTipoMoneda, String strCodRecauAnular, String strFechaRecauAnular,
                                        String strCodAutorizRecauAnular, double dblTotalPagar,
                                        int pRecaudOnline) throws Exception;

    /**
     * OBTENER EL ESTADO DE LA TRANSACCION PROCESADA POR EL DEMONIO
     * @author GFonseca
     * @since 08.08.2013
     */
    public String obtenerEstadoTrssc(Long strCodTrssc, String strModoRecau) throws Exception;

    /**
     * Permite actualizar el estado una recaudacion con el six
     * @author GFonseca
     * @since 09.08.2013
     */
    public void actualizarEstRecauTrsscSix(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                           String codRecaudacion, String estRecauTrsscSix,
                                           String strCodAutoriz) throws SQLException;


    /**
     * Obtener el DNI de un usuario
     * @author GFonseca
     * @since 19.08.2013
     */
    public String obtenerDniUsuario(String numSecUsu) throws SQLException;

    /**
     * Registrar datos de recaudacion en Fasa, para la conciliación
     * @author GFonseca
     * @since 19.Agosto.2013
     */
    public String setDatosRecauConciliacion(String PLOCAL, String PID_VENDEDOR, String PFECHA_VENTA,
                                            double PMONTO_VENTA, double PNUM_CUOTAS, String PCOD_AUTORIZACION,
                                            String PTRACK2, String PCOD_AUTORIZACION_PRE, double PFPA_VALORXCUOTA,
                                            int PCAJA, String PTIPO_TRANSACCION, String PCODISERV,
                                            String PFPA_NUM_OBJ_PAGO, String PNOMBCLIE, long PVOUCHER,
                                            long PNRO_COMP_ANU, String PFECH_COMP_ANU, String PCODIAUTOORIG,
                                            double PFPA_TIPOCAMBIO, long PFPA_NROTRACE, int PCOD_ALIANZA,
                                            int PCOD_MONEDA_TRX, String PMON_ESTPAGO) throws SQLException;


    /**
     * Registrar datos de recaudacion en Fasa, para la conciliación de recargas
     * @author GFonseca
     * @since 27.Agosto.2013
     */
    public String setDatosRecargaConciliacion(String PCL_COD_ID_CONCENTRADOR, String PCL_NUMERO_TELEFONO,
                                              String PCL_COD_AUTORIZACION, String PCL_COD_VENDEDOR,
                                              String PCL_FECHA_VENTA, String PCL_HORA_VENTA,
                                              String PCL_NUMERO_DOCUMENTO, String PCL_COD_COMERCIO,
                                              String PCL_COD_TERMINAL, String PCL_MONTO_VENTA,
                                              String PCL_ID_TRANSACCION) throws SQLException;

    public String getCodLocalMigra(String strCodGrupoCia, String strCodCia, String strCodLocal) throws SQLException;


    /**
     * Impresión para el caso de Venta CMR
     * @author GFonseca
     * @since 20.08.2013
     */
    public String getCompPagoRecauVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                           String pNumeroRecaudacion, String strNumCuotas, String strMontoPagarCuota,
                                           String strFechaVencCuota, String strCodAutorizacion, String pNroCaja,
                                           String pTipCopia) throws SQLException;


    /**
     * Retorna el formato HTML para realizar la impresión anulacion venta CMR (06)
     * @author GFonseca
     * @since 25.10.2013
     */
    public String getCompAnulRecauVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                           String strCodRecau, String pNroCaja, String pTipCopia) throws SQLException;


    /**
     * Impresión para anulacion de recaudación
     * @author GFonseca
     * @since 27.10.2013
     */
    public String imprimirComprobanteAnulRecaudacion(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                                     String pNroCaja, String strCodRecau,
                                                     String pTipCopia) throws SQLException;


    /**
     * Retorna el monto total de recaudaciones asociadas a un movimiento de caja
     * @author GFonseca
     * @since 21.Agosto.2013
     */
    public String getMontoTotalRecaudacionByMovCaja(String strCodGrupoCia, String strCodCia, String strCodLocal,
                                                    String strSecMovCaja) throws SQLException;

    /**
     * RETORNA DATOS DE UNA RECAUDACION VENTA CMR, PARA SU ANULACION
     * @author GFonseca
     * @since 04.09.2013
     */
    public ArrayList getDatosAnulacionVentaCMR(String strCodGrupoCia, String strCodCia, String strCodLocal,
                                               String strNumPedido) throws SQLException;

    /**
     * RETORNA DATOS DE UNA RECAUDACION VENTA CMR DESDE ADM, PARA SU ANULACION
     * @author GFonseca
     * @since 04.09.2013
     */
    public ArrayList getDatosAnulacionVentaCMR_SIX(String strCodTrsscSix) throws SQLException;

    /**
     * REALIZAR UNA ANULACION DE VENTA CMR
     * @author GFONSECA
     * @since 05.09.2013
     * @param mapParametros
     */
    public Long anularPagoTarjetaVentaCMR(String codGrupoCia, String strCodCia, String strCodLocal,
                                          String strTipMsjRecau, String strEstTrsscRecau, String tipoTrssc,
                                          String tipoRcd,

        String strNroTarjeta, String strMonto, String strTerminal, String strComercio, String strUbicacion,
        String strNumCuotas, String strIdCajero, String strNumDoc, String strIdAnular,
        String strUsuario) throws SQLException;

    /**
     * REALIZA LA ANULACION DE UNA RECAUDACION DE VENTA CMR (NO TIENE FORMAS DE PAGO)
     * @author GFonseca
     * @since 25.06.2013
     * @param mapParametros
     */
    public String anularRecaudacionVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                            String pNumeroRecaudacion, String vNumCaja, String pUsuModRecau,
                                            Long codTrsscAnul) throws SQLException;

    /**
     * Obtiene el monto total de recaudacion por cierre de dia
     * @author ERIOS
     * @since 30.09.2013
     * @param pCodGrupoCia
     * @param strCodCia
     * @param strCodLocal
     * @param strFechaCierreDia
     * @return
     * @throws Exception
     */
    public String getMontoTotalRecaudacionByCierreDia(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                                      String strFechaCierreDia) throws Exception;

    /**
     * RETORNA UN LISTADO CONSOLIDADO DE LAS RECAUDACIONES PERTENECIENTES A UNA SECUENCIA DE MOVIMIENTO DE CAJA
     * @author LLEIVA
     * @since 25.Sep.2013
     */
    public ArrayList listarReporteCierreTurno(String strSecMovCaja);

    public void guardarLogConciliacion(String codProceso, String descProceso, String estConciliacion);

    public void guardarLogConciliacion2(String dniVendedor, String fechaVenta, double monto, double numCuotas,
                                        String codAutoriz, String track2, String codAutorizPre, double valorPorCuota,
                                        int caja, String tipoTrans, String codServ, String numObjPago,
                                        String nomCliente, long codVoucher, long numComprobAnulado,
                                        String fechaComprobAnulado, String codAutorizOrig, double tipoCambio,
                                        long numTrace, int codAlianza, int codMoneda, String monEstPago,
                                        String descProceso, String estConciliacion,
                                        String codLocalMigra) throws Exception;

    public ArrayList<ArrayList<String>> getListaConciliacionNOK();

    public void actualizaRegConciliacion(String codConciliacion);

    public ArrayList obtenerDetallePedidoFomasPago(String strNumPedVta);

    public String getCodigoAlianzaConciliacion(String formaPago);

    public ArrayList getNumTarjCodAutoriz(String numPedido);

    public String getNumPedidoNegativo(String numPedido);

    public double getTipoCambio(String pFecha, String pTipoRcd);

    public void actualizarEstLogConciliacion(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                             String strCodAutoriz, String strFechaVenta, String strTipTrssc,
                                             String strPvoucher, String strEstConcili);

    public void guardarLogRecargas(String pNumPedVta, String pCodIdConcetrador, String pNumeroTelefono,
                                   String pCodAutorizacion, String pCodVendedor, String pFechaVenta, String pHoraVenta,
                                   String pNumeroDocumento, String pCodComercio, String pCodTerminal,
                                   double pMontoVenta, String pIdTransaccion, String pUsuCrea);

    public void actualizarEstadoLogRecargas(String pNumPedVta, String pEstConciliacion, String pUsuMod);

    /**
     * Conciliacion pago Citibank
     * @author ERIOS
     * @since 2.4.0
     * @param pCodGrupoCia
     * @param strCodCia
     * @param strCodLocal
     * @param strTipoRecau
     * @param dblTotalPagar
     * @param strCodRecau
     * @param strCodCliente
     * @param strNroTarjeta
     * @param strCodAutorizacion
     * @param vIdUsu
     * @param strDniUsu
     * @param dblTipoCambio
     * @param strTipoMoneda
     * @return
     * @throws Exception
     */
    public Long registrarTrsscPagoCitibank(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                           String strTipoRecau, double dblTotalPagar, String strCodRecau,
                                           String strCodCliente, String strNroTarjeta, String strCodAutorizacion,
                                           String vIdUsu, String strDniUsu, double dblTipoCambio, String strTipoMoneda,
                                           int pRecaudOnline) throws Exception;

    public Long anularTrsscPagoCitibank(String pCodGrupoCia, String strCodCia, String strCodLocal, String strTipoRecau,
                                        double dblTotalPagar, String strCodRecau, String strCodCliente,
                                        String strNroTarjeta, String strCodAutorizacion, String vIdUsu,
                                        String strDniUsu, double dblTipoCambio, String strTipoMoneda,
                                        String strCodRecauAnular, String strFechaRecauAnular,
                                        String strCodAutorizRecauAnular, int pRecaudOnline) throws Exception;

    public String getCodRecauAnul(String pCodGrupoCia, String strCodCia, String strCodLocal) throws Exception;

    /** Anula recaudaciones.
     * @author kmoncada
     * @since 11.06.2014
     * @param pCodGrupoCia
     * @param strCodLocal
     * @param codTrssc
     * @throws Exception
     */
    public void anulaTrsccPagoRecaudacion(String pCodGrupoCia, String strCodLocal, Long codTrssc) throws Exception;
}
