package mifarma.ptoventa.puntos.reference;

import com.gs.mifarma.componentes.JConfirmDialog;

import farmapuntos.bean.Afiliado;
import farmapuntos.bean.TarjetaBean;
import farmapuntos.principal.FarmaPuntos;
import farmapuntos.util.FarmaPuntosConstante;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JTextField;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.puntos.DlgBonificados;
import mifarma.ptoventa.puntos.DlgValidaDocumento;
import mifarma.ptoventa.puntos.DlgVerificaDocRedencionBonifica;
import mifarma.ptoventa.puntos.modelo.BeanAfiliado;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import mifarma.ptoventa.puntos.DlgBonificados;


public class UtilityPuntos {

    private static final Logger log = LoggerFactory.getLogger(UtilityPuntos.class);

    
    public UtilityPuntos() {
    }
    
    public static String obtenerMensajeErrorLealtad(String estadoOperacion,String pMsj) 
    {
      String mensaje=  "";
      try {
            mensaje = DBPuntos.getMensajeErrorLealtad(estadoOperacion);
          if(mensaje.trim().length()==0)
              mensaje = pMsj;
        } catch (Exception e) {
            // TODO: Add catch code
            mensaje= pMsj;
            e.printStackTrace();
        }
      return mensaje;
        }
    
    public static String enmascararTarjeta(String numTarj) {
        String res = "";
        int tam = numTarj.length();
        if (tam > 8) {
            String prim = numTarj.substring(0, 4);
            String ult = numTarj.substring(tam - 4, tam);
            String centro = FarmaUtility.caracterIzquierda("", tam - 8, "*");
            res = prim + centro + ult;
        }else{
            return numTarj;
        }
        return res;
    }

    public static void consultaSaldo(String vNumTarjeta,JTextField objeto,JDialog pDialog, Frame myParentFrame) {
        /*UtilityImprPuntos.imprimeSaldo(
                                      pDialog,objeto,
                                      "DIEGO",
                                      "123*123*123",
                                      20.15
                                      );*/
        if(isTarjetaValida(vNumTarjeta)){
            String pDNI = getDNI_USU();
            
            if(pDNI.trim().length()>0&&!pDNI.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                TarjetaBean beanSaldo = VariablesPuntos.frmPuntos.consultarSaldo(vNumTarjeta, pDNI);
                
                if(beanSaldo.getEstadoOperacion().equalsIgnoreCase(FarmaPuntosConstante.NO_CONEXION_ORBIS))
                {
                    FarmaUtility.showMessage(pDialog, "No hay conexión con el sistema de puntos.", objeto);     
                    return ;
                }
                else
                if(beanSaldo.getEstadoOperacion().equalsIgnoreCase(FarmaPuntosConstante.EXITO)||
                   beanSaldo.getEstadoOperacion().equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA)||
                    beanSaldo.getEstadoOperacion().equalsIgnoreCase(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR)
                  ){
                
                if(beanSaldo==null){
                    FarmaUtility.showMessage(pDialog, "Hay un problema de comunicación con el sistema de puntos.", objeto);     
                    return ;
                }
                
                if (beanSaldo.getPuntosTotalAcumulados()==null) {
                FarmaUtility.showMessage(pDialog, "No se pudo obtener el saldo actual de puntos.", objeto);     
                return ;
                }
                
                
                if(VariablesPuntos.frmPuntos.getTarjetaBean()!=null){
                imprimeSaldo(pDialog ,objeto,beanSaldo,vNumTarjeta,enmascararTarjeta(vNumTarjeta));
                //INVOCAR AL METODO DE KENNY PARA CREAR EL CLIENTE SI NO EXISTE EN PBL_CLIENTE
                               
                //ESTE IRA A ORBIS OBTENDRA LOS DATOS DEL CLIENTE Y LO CREARA EN BD DE LOCAL
                /* *********************************************************************************/
                validaCliente(beanSaldo.getNumeroTarjeta(),beanSaldo.getDni());
                /* *********************************************************************************/
                FarmaUtility.showMessage(pDialog, "Por favor de recojer su constancia de saldos de la impresora.", objeto);     
                }
                else{
                    FarmaUtility.showMessage(pDialog, "No se pudo obtener el saldo de la tarjeta. ", objeto);
                    }
                }
                else{
                    FarmaUtility.showMessage(pDialog, //beanSaldo.getMensaje()
                    UtilityPuntos.obtenerMensajeErrorLealtad(beanSaldo.getEstadoOperacion(),beanSaldo.getMensaje())
                                             , objeto);
                    return ;
                }
            }
            else             
            FarmaUtility.showMessage(pDialog, "No se tiene el DNI , no se puede hacer la consulta de saldos.. ", objeto);   
        }
        else
          FarmaUtility.showMessage(pDialog, "Tarjeta inválida , verique e ingrese nuevamente la tarjeta. ", objeto);
    }
    
    public static void getRecuperaPuntos(String vNumTarjeta, JTextField objeto, JDialog pDialog, String pNumPedVta,
                                         String pFechaPedido,String vNetoPedido) throws Exception {

        try {
            if (isTarjetaValida(vNumTarjeta)) {
                //DNI DEL VENDEDOR o PERSONA LOGIN
                String pDNI = getDNI_USU();
                if (pDNI.trim().length() > 0 && !pDNI.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    if(!DBPuntos.getIndPedActuaPuntos(pNumPedVta))
                    {
                        //calculaPuntosRecuperar(pNumPedVta);
                    calculaPuntos(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPedVta, false);
                        // si la suma de puntos
                        // es CERO
                        // ELL METODO D KENNY HIZO OK
                        // Este no SE ENVIA A ORBIS
                        // es NULO
                        // algo fallo en tu metodo y se mostrara mensaje
                        // hubo error en el calculo
                        boolean pPuntosCero = DBPuntos.isPuntosReloadCero(pNumPedVta);
                        boolean pPuntosNulo = DBPuntos.isPuntosReloadNulo(pNumPedVta);
                        boolean pPuntosMayorCero = DBPuntos.isPuntosReloadMayorCero(pNumPedVta);
                        if (!pPuntosCero && !pPuntosNulo && pPuntosMayorCero) {
                            ArrayList listaProducto = listaProdPuntos(pNumPedVta);
                            double pCtdPuntosAcum =
                                FarmaUtility.getDecimalNumber(DBPuntos.getCtdPuntosAcumVta(pNumPedVta));
                            double vNetPedido = FarmaUtility.getDecimalNumber(vNetoPedido);
                            TarjetaBean beanSaldo =
                                VariablesPuntos.frmPuntos.recuperarPuntos(vNumTarjeta, listaProducto, pNumPedVta,
                                                                          //FarmaUtility.getStringToDate(pFechaPedido,"DD/mm/yyyy")
                                                                          pFechaPedido.trim()
                                                                          , vNetPedido, pDNI);
                            
                            log.info("***************"+beanSaldo.getMensaje());
                            log.info("***************"+UtilityPuntos.obtenerMensajeErrorLealtad(beanSaldo.getEstadoOperacion(),beanSaldo.getMensaje()));
                            // SOLO PARA PROBAR
                            //beanSaldo.setEstadoOperacion(FarmaPuntosConstante.NO_CONEXION_ORBIS);
                            if (beanSaldo == null) {
                                FarmaUtility.showMessage(pDialog, "No se pudo recuperar puntos por:\n" +
                                        "El estado de la respuesta no es válido.", objeto);
                            } else if (beanSaldo.getEstadoOperacion().equalsIgnoreCase(FarmaPuntosConstante.EXITO) ){
                                /* *********************************************************************************/
                                validaCliente(vNumTarjeta, beanSaldo.getDni());
                                /* *********************************************************************************/
                                // ES ONLINE x eso va el TRUE
                                DBPuntos.pRecuperaPuntos(vNumTarjeta, pNumPedVta, true,beanSaldo.getIdTransaccion(),beanSaldo.getNumeroAutororizacion());
                                FarmaUtility.aceptarTransaccion();
                                
                                TarjetaBean beanConsultaSaldo =null;
                                try {
                                    beanConsultaSaldo = VariablesPuntos.frmPuntos.consultarSaldo(vNumTarjeta, pDNI);
                                } catch (Exception e) {
                                    // TODO: Add catch code
                                    log.info("Fallo VariablesPuntos.frmPuntos.consultarSaldo");
                                    log.info(e.getMessage());
                                    e.printStackTrace();
                                }
                                
                                // INI - AGREGADO DUBILLUZ 07.05.2015
                                // NO SE EVALUO NUNCA SI HUBO O NO CONEXION 
                                // SE INDICO ANTERIORMENTE QUE ESTE METODO DE CONSULTAR SALDO SIEMBRE DEVUELVE UN BEAN NO NULO
                                // Y DATOS VACIOS y/o CERO:
                                if (beanSaldo == null) {
                                    imprimePuntosAcumulados(pCtdPuntosAcum, vNumTarjeta, "",
                                                            "", true, pDialog, objeto,0,
                                                            pNumPedVta);
                                    FarmaUtility.showMessage(pDialog, "Exito,se hizo la recuperación de puntos. ", objeto);                                
                                } else {
                                    
                                    try {
                                        imprimePuntosAcumulados(pCtdPuntosAcum, vNumTarjeta,
                                                                beanConsultaSaldo.getDni(),
                                                                beanConsultaSaldo.getNombreCompleto(), true, pDialog,
                                                                objeto, beanConsultaSaldo.getPuntosTotalAcumulados(),
                                                                pNumPedVta);
                                        FarmaUtility.showMessage(pDialog, "Exito,se hizo la recuperación de puntos. ",
                                                                 objeto);
                                    } catch (Exception e) {
                                        // TODO: Add catch code
                                        e.printStackTrace();
                                        imprimePuntosAcumulados(pCtdPuntosAcum, vNumTarjeta, "",
                                                                "", false, pDialog, objeto,0,
                                                                pNumPedVta);
                                        FarmaUtility.showMessage(pDialog, "Exito,se hizo la recuperación de puntos. ", objeto);
                                        
                                    }                                
                                }
                                // FIN - AGREGADO DUBILLUZ 07.05.2015                                
                                /*
                                imprimePuntosAcumulados(pCtdPuntosAcum, vNumTarjeta, beanConsultaSaldo.getDni(),
                                                        beanConsultaSaldo.getNombreCompleto(), true, pDialog, objeto,beanConsultaSaldo.getPuntosTotalAcumulados(),
                                                        pNumPedVta);
                                FarmaUtility.showMessage(pDialog, "Exito,se hizo la recuperación de puntos. ", objeto);
                                */
                                    
                            } else {
                                if (beanSaldo.getEstadoOperacion().equalsIgnoreCase(FarmaPuntosConstante.NO_CONEXION_ORBIS)) {
                                    //El metodo de acumula puntos se registra el ind en cabecera.
                                    //pero como no va estar el fecha proceso se va hacer con el JOB de modo OFF LINE
                                    // ES OFF LINE x eso va el false
                                    DBPuntos.pRecuperaPuntos(vNumTarjeta, pNumPedVta, false,beanSaldo.getIdTransaccion(),beanSaldo.getNumeroAutororizacion());
                                    FarmaUtility.aceptarTransaccion();
                                    
                                    
                                    TarjetaBean beanConsultaSaldo =null;
                                    try {
                                        beanConsultaSaldo = VariablesPuntos.frmPuntos.consultarSaldo(vNumTarjeta, pDNI);
                                        imprimePuntosAcumulados(pCtdPuntosAcum, vNumTarjeta, beanConsultaSaldo.getDni(),
                                                                beanConsultaSaldo.getNombreCompleto(), false, pDialog, objeto,beanConsultaSaldo.getPuntosTotalAcumulados(),
                                                                pNumPedVta);                                        
                                        FarmaUtility.showMessage(pDialog, "Exito,se hizo la recuperación de puntos. ",
                                                                 objeto);
                                    } catch (Exception e) {
                                        // TODO: Add catch code
                                        e.printStackTrace();
                                        imprimePuntosAcumulados(pCtdPuntosAcum, vNumTarjeta, "",
                                                                "", false, pDialog, objeto,1,
                                                                pNumPedVta);                                                                                
                                        FarmaUtility.showMessage(pDialog,
                                                                 "La recuperación se verá reflejada en el transcurso del día.\n" +
                                                "Porque no hay conexión con servicio de puntos", objeto);                                                                            
                                    }
                                    
                                    
                                    
                                } else {
                                    DBPuntos.pRevierteAcumulaPuntos(pNumPedVta);
                                    FarmaUtility.aceptarTransaccion();
                                    FarmaUtility.showMessage(pDialog, "No se pudo recuperar puntos por :\n" +
                                            UtilityPuntos.obtenerMensajeErrorLealtad(beanSaldo.getEstadoOperacion(),beanSaldo.getMensaje()), objeto);
                                }
                            }
                        } else {
                            if (pPuntosCero) {
                                // Se muestra mensaje de que ya recupero CERO PUNTOS y se marca que no intente lo mismo
                                FarmaUtility.showMessage(pDialog, "Se registraron los puntos correctamente.\n" +
                                        "Puntos acumulados para el pedido es cero", objeto);
                            }
                            if (pPuntosNulo) {
                                // Se muestra mensaje de que ya recupero CERO PUNTOS y se marca que no intente lo mismo
                                /*   FarmaUtility.showMessage(pDialog,
                                                         "No se pudo calcular cuantos puntos acumula la venta.\n" +
                                        "Por favor de volver a intentarlo", objeto);*/
                                
                                FarmaUtility.showMessage(pDialog, "No se pudo recuperar los puntos del pedido.\n" +
                                        //"Por favor vuelva a intentarlo." +
                                        "Gracias.", objeto);
                            }
                            
                            actualizarEstTrxOrbis(pNumPedVta, ConstantsPuntos.TRSX_ORBIS_NO_APLICA);
                            FarmaUtility.aceptarTransaccion();

                        }
                    }
                    else{
                    FarmaUtility.showMessage(pDialog, 
                                             "No se puede recuperar, porque el pedido ya acumulo Puntos", objeto);
                    }

                } else
                    FarmaUtility.showMessage(pDialog, "No se tiene el DNI del usuario del sistema ,\n" +
                            "No se puede hacer la recuperacion de puntos ", objeto);
                
            } else
                FarmaUtility.showMessage(pDialog, "Tarjeta invalida , verique e ingrese nuevamente la tarjeta. ",
                                         objeto);
        } catch (SQLException e) {
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
            if (e.getErrorCode() == 20200) {
                throw new Exception(e.getMessage().substring(11, e.getMessage().indexOf("ORA-06512")));
                /*FarmaUtility.showMessage(pDialog,
                                         e.getMessage().substring(11, e.getMessage().indexOf("ORA-06512")),
                                         objeto);*/
            }            
            else
            throw new Exception(e.getMessage());
            /*FarmaUtility.showMessage(pDialog, "Error al momento de Recuperar los Puntos ,\n" +
                    "" + e.getMessage(), objeto);*/
        }
        catch(Exception a){
            a.printStackTrace();
            /*
            FarmaUtility.showMessage(pDialog, "Error al momento de Recuperar los Puntos ,\n" +
                    "" + a.getMessage(), objeto);*/
            throw new Exception(a.getMessage());
        }
    }
    
    private static void actualizarEstTrxOrbis(String pNumPedVta, String pEstadoTrxOrbis){
        try{
            DBPuntos.actualizaEstadoTrx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPedVta, pEstadoTrxOrbis);
        }catch(Exception ex){
            log.error(" ", ex);
            
        }
    }

    public static String getCodAutorizacion() {
        String pKey = "";
        try {
            pKey = DBPuntos.getCodAutorizacion();
        } catch (Exception e) {
            e.printStackTrace();
            pKey = "";
            log.info("ERROR getCodAutorizacion FARMA PUNTOS");
            log.info(e.getMessage());
        }
        return pKey;
    }
    
    public static String getWsOrbis() {
        String pKey = "";
        try {
            pKey = DBPuntos.getWsOrbis();
        } catch (Exception e) {
            e.printStackTrace();
            pKey = "";
            log.info("ERROR getCodAutorizacion FARMA PUNTOS");
            log.info(e.getMessage());
        }
        return pKey;
    }    
    

    /**
     * INSTANCIA LA CLASE FARMA PUNTOS
     */
    public static boolean cargaFarmaPuntos() {
        if (isActivoFuncionalidad()) {
            // SE INICIA LA CLASE FARMA PUNTOS
            log.info(" Antes de iniciar VariablesPtoVenta.frmPuntos");
            log.info("Variables");
            String pIdIPPOS = getIdEpos();
            String pkey = getCodAutorizacion();
            String pWsOrbis = getWsOrbis();
            String pTimeOut = pTimeOutOrbis();
            log.info(" " + FarmaVariables.vCodLocal);
            log.info(" " + pIdIPPOS);
            log.info(" " + pkey);
            log.info(" "+pWsOrbis);
            log.info(" "+pTimeOut);
            //if(pCodAutorizacion.)

            if (FarmaVariables.vCodLocal.trim().length() > 0 && pIdIPPOS.trim().length() > 0 &&
                pkey.trim().length() > 0 &&
                pWsOrbis.trim().length() > 0 ) {
                try {
                    VariablesPuntos.frmPuntos = new FarmaPuntos(FarmaVariables.vCodLocal, pIdIPPOS, pWsOrbis, pkey,
                                                                Integer.parseInt(pTimeOut.trim())
                                                                );
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                    log.info(e.getMessage());
                    //FarmaUtility.showMessage(myfrm,"No se pudo iniciar Servicio Monedero", null);
                }log.info("Finalizo VariablesPtoVenta.frmPuntos");
                return true;
            } else {
                return false;
            }
        }
        else{
           log.info(" NO ESTA ACTIVO Funcionalidad Farma Puntos");
            return true;
        }
        
    }
    
    public static String getIdEpos() {
        String[] cadena = FarmaUtility.getHostAddress().split("\\.");
        return cadena[3].toString();
    }

    public static boolean isActivoFuncionalidad() {
        String pInd = "";
        try {
            pInd = DBPuntos.getIndActPuntos();
        } catch (Exception e) {
            e.printStackTrace();
            pInd = "N";
            log.info("ERROR isActivoFuncionalidad FARMA PUNTOS");
            log.info(e.getMessage());
        }
        if(pInd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }
    
    /**
     * @since 2015.02 VALIDA TARJETAS EN ORBIS
     * @param pTarjeta
     * @return
     */
    public static boolean isTarjetaValida(String pTarjeta) {
        String pInd = "";
        try {
            pInd = DBPuntos.isTarjetaValida(pTarjeta);
        } catch (Exception e) {
            e.printStackTrace();
            pInd = "N";
            log.info("ERROR isTarjetaValida FARMA PUNTOS");
            log.info(e.getMessage());
        }
        if(pInd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }    
    
    //getDniUsuario
    public static String getDNI_USU() {
        String pDni = "";
        try {
            pDni = DBPuntos.getDniUsuario();
        } catch (Exception e) {
            e.printStackTrace();
            pDni = "N";
            log.info("ERROR getDNI_USU FARMA PUNTOS");
            log.info(e.getMessage());
        }
        
        return pDni;
    }
    
    /**
     *
     * @param rpsDatoOrbis
     */
    public static void imprimeSaldo(JDialog pDialog, Object pFoco, TarjetaBean rpsDatoOrbis, String pTarjeta,
                                    String pTarjEnmascarada) {
        
            try {
                UtilityImprPuntos.imprimeSaldo(pDialog, pFoco, rpsDatoOrbis.getNombreCompleto(), pTarjEnmascarada,
                                               rpsDatoOrbis.getPuntosTotalAcumulados());

            } catch (Exception sqle) {
                sqle.printStackTrace();
                log.info(sqle.getMessage());
            }
        
    }
    
    /**
     * INIT DE ORBIS - REALIZA LA CONSULTA DEL NRO DE TARJETA 
     * @author KMONCADA
     * @since 2015.03.06
     * @param pJDialog
     * @param campoTexto
     * @param isLectorTarjeta
     * @param pTarjetaAnterior
     * @return
     */
    public static boolean consultarTarjetaOrbis(Frame myParentFrame, JDialog pJDialog, JTextField campoTexto, boolean isLectorTarjeta, boolean pTarjetaAnterior){
        
        boolean consulto = true;
        String campoTextoField = campoTexto.getText();
        try{
            if(isActivoFuncionalidad()){
                log.info("FUNCIONALIDAD DE LEALTAD ACTIVA");
                String nroTarjetaPuntos, nroDni, nroTarjetaFidelizado;
                nroTarjetaPuntos = "";
                nroDni = "";
                nroTarjetaFidelizado = "";
                if(pTarjetaAnterior){
                    nroTarjetaPuntos = campoTextoField;
                    nroTarjetaFidelizado = nroTarjetaPuntos;
                    nroDni = DBFidelizacion.getDniClienteFidelizado(nroTarjetaPuntos);
                    if(!isTarjetaValida(nroTarjetaPuntos)){
                        nroTarjetaPuntos = nroDni;
                    }
                    campoTextoField = nroTarjetaPuntos;
                }
                // KMONCADA 28.04.2015 VALIDA SI REQUIERE TARJETA DE PUNTOS PARA REALIZAR VENTA
                if(DBPuntos.requerieTarjetaAsociada()){
                    log.info("PROGRAMA PUNTOS [CONSULTA TARJETA] : SOLICITA TARJETA OBLIGATORIO");
                    //BENEFICIOS DE PUNTOS SOLO CUANDO TIENE ALGUNA TARJETA
                    if(!isTarjetaValida(campoTextoField)){
                        log.info("PROGRAMA PUNTOS [CONSULTA TARJETA] : NO ES UNA TARJETA DE PUNTOS --> "+campoTextoField);
                        String nroDocumentoAux = campoTextoField;
                        try{
                            int cantidadTarjetaAdicional = cantidadTieneTarjetasAdicionales(myParentFrame, pJDialog, nroDocumentoAux, false, false);
                            log.info("PROGRAMA PUNTOS [CONSULTA TARJETA] : CANTIDAD DE TARJETAS ASOCIADAS --> "+campoTextoField+" -- "+cantidadTarjetaAdicional);
                            if(cantidadTarjetaAdicional == 0){
                                String mensajeAviso = DBPuntos.getMensajeSinTarjeta();
                                if(JConfirmDialog.rptaConfirmDialog(pJDialog, mensajeAviso)){
                                    DlgVerificaDocRedencionBonifica dlgVerifica = new DlgVerificaDocRedencionBonifica(myParentFrame, "", true, ConstantsPuntos.VALIDA_TARJETA_ASOCIADA);
                                    dlgVerifica.setNroDocumento(campoTextoField);
                                    dlgVerifica.setIsRequiereDni(false);
                                    dlgVerifica.setIsRequiereTarjeta(true);
                                    dlgVerifica.setVisible(true);
                                    
                                    if(FarmaVariables.vAceptar){
                                        campoTextoField = dlgVerifica.getTextTarjeta().trim();
                                        isLectorTarjeta = true;
                                    }else
                                        return false;
                                }else{
                                    VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                                    return false;
                                }
                            } else{
                                if(cantidadTarjetaAdicional == -1){
                                    String mensaje = "Para poder acceder a los beneficios por la Tarjeta Monedero, debe escanearla\n" + 
                                                     "¿Cliente cuenta con Tarjeta Monedero o desea afiliarse?";
                                    
                                    if(!isLectorTarjeta){
                                        log.info("CONSULTA TARJETA ORBIS [OFFLINE] :\nNRO DOCUMENTO --> "+nroDni+"\n"+
                                                 "SOLICITARA NRO TARJETA DE PUNTOS");
                                        
                                        if(JConfirmDialog.rptaConfirmDialog(pJDialog, mensaje)){
                                            DlgVerificaDocRedencionBonifica dlgVerifica = new DlgVerificaDocRedencionBonifica(myParentFrame, "", true, ConstantsPuntos.SOLICITA_TARJ_OFFLINE);
                                            dlgVerifica.setNroDocumento(campoTextoField);
                                            dlgVerifica.setIsRequiereDni(false);
                                            dlgVerifica.setIsRequiereTarjeta(true);
                                            dlgVerifica.setVisible(true);
                                            boolean resultado = FarmaVariables.vAceptar;
                                            if(resultado){
                                                campoTextoField = dlgVerifica.getTextTarjeta().trim();
                                                isLectorTarjeta = true;
                                            }else{
                                                VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                                                return false;
                                            }
                                        }else{
                                            VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                                            return false;
                                        }
                                    }
                                    return false;
                                }
                            } 
                        }catch(Exception ex){
                            
                        }
                    }
                }
                log.info("PROGRAMA PUNTOS [CONSULTA TARJETA] : NRO DE TARJETA --> "+campoTextoField);
                TarjetaBean tarjetaPuntos =  VariablesPuntos.frmPuntos.validarTarjetaAsociada(campoTextoField.trim(),  getDNI_USU());
                tarjetaPuntos.setDeslizaTarjeta(isLectorTarjeta); // indicador de desliza tarjeta
                tarjetaPuntos.setEscaneaDNI(false);
                /* INICIO DE DATOS DE PRUEBA */
                /* tarjetaPuntos.setNumeroTarjeta(campoTexto.getText());
                List list = DBPuntos.consultaTarje(campoTexto.getText());
                Map mapa = (Map)list.get(0);
                tarjetaPuntos.setDni((String)mapa.get("NRO_DOCUMENTO"));
                tarjetaPuntos.setEstadoTarjeta((String)mapa.get("ESTADO"));
                tarjetaPuntos.setEstadoOperacion(FarmaPuntosConstante.EXITO);
                VariablesPuntos.frmPuntos.getTarjetaBean().setNumeroTarjeta(campoTexto.getText()); */
                /* FIN DE DATOS DE PRUEBA */
                /*JOptionPane.showMessageDialog(pJDialog, "NRO TARJETA --> " +tarjetaPuntos.getNumeroTarjeta()+"\n"+
                                                    "NRO DNI --> "+tarjetaPuntos.getDni()+"\n"+
                                                    "DESLIZO TARJETA --> "+tarjetaPuntos.getDeslizaTarjeta()+"\n"+
                                                    "ESTADO DE TARJETA --> "+tarjetaPuntos.getEstadoTarjeta()+"\n"+
                                                    "ESTADO DE TRANSACCION--> "+tarjetaPuntos.getEstadoOperacion()+"\n"+
                                                    "NOMBRE --> "+tarjetaPuntos.getNombreCompleto());*/
                log.info("PROGRAMA PUNTOS [CONSULTA TARJETA] :\nNRO TARJETA --> " +tarjetaPuntos.getNumeroTarjeta()+"\n"+
                                                    "NRO DNI --> "+tarjetaPuntos.getDni()+"\n"+
                                                    "DESLIZO TARJETA --> "+tarjetaPuntos.getDeslizaTarjeta()+"\n"+
                                                    "ESTADO DE TARJETA --> "+tarjetaPuntos.getEstadoTarjeta()+"\n"+
                                                    "ESTADO DE TRANSACCION--> "+tarjetaPuntos.getEstadoOperacion()+"\n"+
                                                    "NOMBRE --> "+tarjetaPuntos.getNombreCompleto());
                
                
                // VALIDACIONES DE ESTADO DE TARJETA
                if(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                    VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                    VariablesFidelizacion.limpiaVariables();
                    FarmaUtility.showMessage(pJDialog, "Programa Monedero:\n TARJETA BLOQUEDA.", campoTexto);
                    return false;
                }
                if(FarmaPuntosConstante.EstadoTarjeta.INVALIDA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                    VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                    VariablesFidelizacion.limpiaVariables();
                    FarmaUtility.showMessage(pJDialog, "Programa Monedero:\n TARJETA INVALIDA.", campoTexto);
                    return false;
                }
                if(FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                    log.info("PROGRAMA MONEDERO TARJETA INACTIVA SE REGISTRARA CLIENTE NUEVO");
                    FarmaUtility.showMessage(pJDialog, "Programa Monedero:\n TARJETA NUEVA.", campoTexto);
                    if(pTarjetaAnterior){
                        // KMONCADA 05.05.2015 REGISTRO AUTOMATICO DE CLIENTES 
                        // construir bean de afiliado en base al nro de dni;
                        registroAfiliadoAutomatico(nroDni, campoTextoField, nroTarjetaFidelizado);
                        // KMONCADA VOUCHER DE AFILIADO
                        log.info("PROGRAMA PUNTOS [CLIENTES ANTIGUOS] " + nroDni);
                        FarmaUtility.showMessage(pJDialog, "Recoger voucher de verificación de datos", null);
                        UtilityFidelizacion.imprimirVoucherFid(nroDni);                    
                        
                        return true;
                    }
                    return false;
                }
                if(FarmaPuntosConstante.EXITO.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())
                || FarmaPuntosConstante.EstadoTarjeta.ACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                || FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                    
                    
                    validaCliente(tarjetaPuntos.getNumeroTarjeta(), tarjetaPuntos.getDni());
                    /*FarmaUtility.showMessage(pJDialog, "BIENVENIDO AL PROGRAMA PUNTOS, \n trasaccion: "+tarjetaPuntos.getIdTransaccion()+"\n"+
                                                       "tarjeta : "+tarjetaPuntos.getNumeroTarjeta()+"\n"+
                                                       "dni: "+tarjetaPuntos.getDni(), campoTexto);*/
                    log.info("BIENVENIDO AL PROGRAMA MONEDERO");
                    return true;
                }
                
                if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())){
                    log.info("CONSULTA DE CLIENTE OFFLINE --> VENTA OFFLINE "+campoTextoField);
                  //  FarmaUtility.showMessage(pJDialog, "PROGRAMA DE PUNTOS:\nVenta en modo OFFLINE", campoTexto);
                    String mensaje = "Para poder acceder a los beneficios por la Tarjeta Monedero, debe escanearla\n" + 
                                     "¿Cliente cuenta con Tarjeta Monedero?";
                    
                    if(!isLectorTarjeta){
                        log.info("CONSULTA TARJETA ORBIS [OFFLINE] :\nNRO DOCUMENTO --> "+nroDni+"\n"+
                                 "SOLICITARA NRO TARJETA DE PUNTOS");
                        
                        if(JConfirmDialog.rptaConfirmDialog(pJDialog, mensaje)){
                            DlgVerificaDocRedencionBonifica dlgVerifica = new DlgVerificaDocRedencionBonifica(myParentFrame, "", true, ConstantsPuntos.SOLICITA_TARJ_OFFLINE);
                            dlgVerifica.setNroDocumento(campoTextoField);
                            dlgVerifica.setIsRequiereDni(false);
                            dlgVerifica.setIsRequiereTarjeta(true);
                            dlgVerifica.setVisible(true);
                            boolean resultado = FarmaVariables.vAceptar;
                            if(resultado){
                                VariablesPuntos.frmPuntos.getTarjetaBean().setNumeroTarjeta(dlgVerifica.getTextTarjeta().trim());
                            }else{
                                VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                            }
                        }else{
                            VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                        }
                    }
                    return false;
                }else{
                    // ERROR DE ORBIS
                    String estadoOperacion = tarjetaPuntos.getEstadoOperacion();
                    String mensaje = UtilityPuntos.obtenerMensajeErrorLealtad(tarjetaPuntos.getEstadoOperacion(),tarjetaPuntos.getMensaje());
                    if(VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                        VariablesPuntos.frmPuntos.eliminarTarjetaBean();    
                    }
                    
                    log.info("ERROR EN INIT DE ORBIS --> \n CODIGO DE ERROR "+estadoOperacion+"\n MENSAJE "+mensaje+
                             "NRO TARJETA "+campoTextoField+
                             "PASO POR LECTORA "+isLectorTarjeta);
                    
                    throw new Exception("Programa Monedero:\n" +
                                        "Error en consulta de tarjeta, se continuara como una venta normal,"+
                                        "estado de operacion ("+estadoOperacion+")\n" +
                                        mensaje);
                }
                
                
            }else{
                log.info("FUNCIONALIDAD DE LEALTAD INACTIVA");
                consulto = false;
            }
        }catch(Exception ex){
            log.error("", ex);
            if(VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                VariablesPuntos.frmPuntos.eliminarTarjetaBean();    
            }
            FarmaUtility.showMessage(pJDialog, "PROGRAMA MONEDERO:\n"+
                                               "Error en consulta de tarjeta de puntos.\n"+
                                               ex.getMessage(), campoTexto);
        }

        return consulto;
    }

    /**
     * @author KMONCADA
     * @since 2015.02.09
     * @param pTarjeta
     * @param pDni
     */
    private static void validaCliente(String pTarjeta, String pDni) {
        try{
            ArrayList array = new ArrayList();
            if(pTarjeta!=null)
                pTarjeta = pTarjeta.trim();
            DBFidelizacion.buscarTarjetasXDNI(array, pDni, pTarjeta);
            log.info("tamanio de array : "+array.size()+"  -  "+array);
            if(!isTarjetaValida(pTarjeta)){
                pTarjeta = null;
            }
            if(array.size()==0){
                // SI NO SE CREO CLIENTE ENTONCES SE CREA LA TARJETA
                String nroTarjeta = UtilityFidelizacion.generaNuevaTarjeta(ConstantsFidelizacion.PREFIJO_TARJETA_FIDELIZACION, FarmaVariables.vCodLocal, pTarjeta);
                //en caso el cliente no exista en el local se consultan datos a orbis
                 consultarAfiliado(null, nroTarjeta);
            }else{
                pTarjeta = (String)(((ArrayList)array.get(0)).get(0));
                String indExisteLocal = DBFidelizacion.validaClienteLocal(pTarjeta);
                log.info("utilitypuntos.validacliente "+ indExisteLocal);
                //if("0".equalsIgnoreCase(indExisteLocal)){
                consultarAfiliado(null, pTarjeta);
                //}
            }
        }catch(Exception ex){
            log.error("",ex);
        }
        
    }
    
    /**
     * Registro de afiliado en ORBIS
     * @author KMONCADA
     * @since 2015.02.09
     * @param pNroTarjetaLealtad
     * @param afiliado
     * @return
     */
    public static boolean registrarCliente(String pNroTarjetaLealtad, Afiliado afiliado)throws Exception{
        
        boolean registroExitoso = true;
        
        TarjetaBean tarjetaPtos = VariablesPuntos.frmPuntos.registrarAfiliado(pNroTarjetaLealtad, afiliado, getDNI_USU());
        // DATOS DE PRUEBA
     /*    DBPuntos.registroCliente(pNroTarjetaLealtad, afiliado.getDni(), afiliado.getNombre()); */
        // DATOS DE PRUEBA
        if (FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaPtos.getEstadoOperacion())) {
            registroExitoso = false;
        }
        if (FarmaPuntosConstante.PARAMETRO_INCOMPLETO.equalsIgnoreCase(tarjetaPtos.getEstadoOperacion())) {
            throw new Exception("Programa Monedero:\n" +
                                "Datos incompletos, "+
            UtilityPuntos.obtenerMensajeErrorLealtad(tarjetaPtos.getEstadoOperacion(),tarjetaPtos.getMensaje())+
                                "\nVerifique!!!");
        }
        if (FarmaPuntosConstante.PARAMETRO_INCORRECTO.equalsIgnoreCase(tarjetaPtos.getEstadoOperacion())) {
            throw new Exception("Programa Monedero:\n" +
                                "Datos erroneos, verifique!!!");
        }
        if (FarmaPuntosConstante.YA_EXISTE_REGISTRO_TARJETA.equalsIgnoreCase(tarjetaPtos.getEstadoOperacion())) {
            registroExitoso = true;
        }
        if (!FarmaPuntosConstante.EXITO.equalsIgnoreCase(tarjetaPtos.getEstadoOperacion())) {
            log.info("Programa Monedero:\nError en el registro de afiliado.\nError ("+tarjetaPtos.getEstadoOperacion()+
                     ")\nMensaje: "+UtilityPuntos.obtenerMensajeErrorLealtad(tarjetaPtos.getEstadoOperacion(),tarjetaPtos.getMensaje()));
            return false;
        }
        
        
        // DATOS DE PRUEBA
        /*JOptionPane.showMessageDialog(null, "SE REGISTRO EN ORBIS\n TarjetaRegistro:"+pNroTarjetaLealtad+
                                            "\nAfiliado"+afiliado.getDni()+
                                            "\nNombre: "+afiliado.getApellidos()+" "+afiliado.getNombre());*/
        
        log.info("SE REGISTRO EN ORBIS\n TarjetaRegistro:"+pNroTarjetaLealtad+
                                            "\nAfiliado"+afiliado.getDni()+
                                            "\nNombre: "+afiliado.getApParterno()+" "+afiliado.getApMarterno()+" "+afiliado.getNombre());
        // DATOS DE PRUEBA
        
        return registroExitoso;
    }

    public static void calculaPuntos(String pCodGrupoCia, String pCodLocal, String pNumPedVta, boolean isRecalculo)throws Exception{
        calculaPuntos(pCodGrupoCia, pCodLocal, pNumPedVta, isRecalculo, false);
    }
    
    /**
     * @author DESAROLLO3
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @param isRecalculo
     * @throws Exception
     */
    public static void calculaPuntos(String pCodGrupoCia, String pCodLocal, String pNumPedVta, boolean isRecalculo, boolean isRedime)throws Exception{
        DBPuntos.calculoPuntos(pCodGrupoCia, pCodLocal, pNumPedVta, isRecalculo, isRedime);
    }
    
    /**
     * @author KMONCADA
     * @since 2015.03.12
     * @param pNroDni
     * @param pNroTarjetaPuntos
     * @throws Exception
     */
    public static void registroAfiliadoAutomatico(String pNroDni, String pNroTarjetaPuntos, String pNroTarjetaFidelizado){
        try{
            boolean procesoRegistro = false;
            BeanAfiliado afiliado = null;
            try{
                FacadeAfiliado facadeAfiliado = new FacadeAfiliado(); 
                afiliado = facadeAfiliado.obtenerClienteFidelizado(pNroDni);
                procesoRegistro = registrarCliente(pNroTarjetaPuntos, afiliado);
            }catch(Exception ex){
                procesoRegistro = false;
                log.error("",ex);
                
            }
            if(afiliado !=null ){
                DBFidelizacion.setIndEnvioRegistroPuntos(pNroTarjetaPuntos, afiliado.getDni(), procesoRegistro, pNroTarjetaFidelizado);
            }else{
                log.info("Programa Monedero: no cargo datos de afiliado");
            }
        }catch(Exception e){
            log.error("",e);
        }
    }
    
    /**
     * CONSULTA POR DATOS DE AFILIADO
     * @author KMONCADA
     * @since 2015.02.06
     * @param pParent
     * @param pNroTarjeta
     * @return
     * @throws Exception
     */
    private static Afiliado consultarAfiliado(JDialog pParent, String pNroTarjeta)throws Exception{
        Afiliado afiliado = null;
        TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
        if(tarjetaPuntos != null){
            String nroDni = tarjetaPuntos.getDni();
            if(nroDni == null) {
                log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] NRO DE DNI NULO");
                FarmaUtility.showMessage(pParent, "Nro Documento de Identidad invalido, \n"+
                                                          "No se pudo consultar datos de Afiliado", null);
            }else{
                if(FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                    log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] NO SE CONSULTA AFILIADO POR ESTAR TARJETA INACTIVA");
                    afiliado = new Afiliado();
                }else{
                    if(FarmaPuntosConstante.EstadoTarjeta.INVALIDA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                        log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] TARJETA INVALIDA");
                        FarmaUtility.showMessage(pParent, "Tarjeta de Lealtad:\nTarjeta invalida.", null);
                        afiliado = null;
                    }else{
                        if(FarmaPuntosConstante.EXITO.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())){
                            log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] CONSULTANDO EN ORBIS \n NRO DOCUMENTO --> "+nroDni+"\nNRO TARJETA --> "+pNroTarjeta);
                            // CONSULTA A PROGRAMA DE PUNTOS DE AFILIADO
                            afiliado = VariablesPuntos.frmPuntos.obtenerDatosAfiliado(nroDni, getDNI_USU());
                            if(afiliado != null){
                                log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] CONSULTA CON EXITO\n"+
                                         "NOMBRE.AFILIADO : "+afiliado.getNombre()+"\n"+
                                         "APE.PAT.AFILIADO : "+afiliado.getApParterno()+"\n"+
                                         "APE.MAT.AFILIADO : "+afiliado.getApMarterno()+"\n"+
                                         "NRO DOCUMENTO : "+afiliado.getDni());
                                VariablesFidelizacion.vNumTarjeta = pNroTarjeta;
                                VariablesFidelizacion.vIndEstado = "A";
                                DBFidelizacion.insertarClienteFidelizacion(ConstantsPuntos.IND_PROCESO_ORBIS, 
                                                                           ConstantsPuntos.TRSX_ORBIS_ENVIADA, 
                                                                           afiliado);    
                            }else{
                                log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] CONSULTA DE AFILIADO ERROR");
                            }
                        }
                        
                    }
                }
            }
        }else{
            FarmaUtility.showMessage(pParent, "Tarjeta Monedero:\nError al obtener Tarjeta.", null);
        }
        return afiliado;
    }


    public static ArrayList listaProdPuntos(String vNumPedVta) throws Exception{
        // Calculara los puntos del sistema pendientes para acumular y grabar
        // DEBE DE HACER 2 COSAS.
        // DEBE DE GRABAR en Cabecera de Pedido el PUNTO ACUMULADO
        // DEBE DE GRABAR en Detalle el Punto Acumulado por Producto        
        return DBPuntos.getListProdPuntosRecupera(vNumPedVta);
        
    }

    private static void imprimePuntosAcumulados(double pPuntosAcum,
                                                String pTarjeta,
                                                String pDNI,
                                                String pNombre,
                                                boolean pOnline,
                                                JDialog pDialog,JTextField objeto,
                                                double pPtoSaldoAct,
                                                String pNumPedVta
                                                ) {
        if (pPuntosAcum > 0) {
            try {
                if(pPtoSaldoAct<0)
                    pPtoSaldoAct = 0;
                UtilityImprPuntos.imprimeRecuperaPuntos(pDialog, objeto, pNombre,pDNI, enmascararTarjeta(pTarjeta),
                                                        pPuntosAcum,pOnline,pPtoSaldoAct,pNumPedVta);

            } catch (Exception sqle) {
                sqle.printStackTrace();
                log.info(sqle.getMessage());
                FarmaUtility.showMessage(pDialog, "Error en la Impresion de Recuperación de Puntos\n"+
                                                  sqle.getMessage(), objeto);
            }
        }
    }
    
    /**
     * REALIZA LA OPERACION DE QUOTE CON PROVEEDOR NORMAL INCLUYENDO LOS PRODUCTOS MODIFICADOS.
     * @author DESARROLLO3
     * @since 2015.03.26
     * @param tarjetaPuntos
     * @param pNumPedVta
     * @return
     * @throws Exception
     */
    public static boolean procesarQuote(TarjetaBean tarjetaPuntos, String pNumPedVta)throws Exception{
        log.info("PROGRAMA DE PUNTOS [QUOTE] : QUOTE CON BONIFICADOS");
    // return procesarQuote(tarjetaPuntos, pNumPedVta, true);

     HiloFarmaPuntos hilo = new HiloFarmaPuntos("HILO_QUOTE", tarjetaPuntos,  pNumPedVta,  true);
      hilo.start();
    
     return true;
    }
    
    /**
     * REALIZA LA OPERACION DE QUOTE CON PROVEEDOR, PERO SIN INCLUIR LOS PRODUCTOS BONIFICADOS
     * @author DESARROLLO3 
     * @since 2015.03.26 
     * @param tarjetaPuntos
     * @param pNumPedVta
     * @return
     * @throws Exception
     */
    public static boolean procesarQuoteSinBonificados(TarjetaBean tarjetaPuntos, String pNumPedVta)throws Exception{
        log.info("PROGRAMA DE PUNTOS [QUOTE] : QUOTE SIN BONIFICADOS");
        // return procesarQuote(tarjetaPuntos, pNumPedVta, false);
        HiloFarmaPuntos hilo = new HiloFarmaPuntos("HILO_QUOTE", tarjetaPuntos,  pNumPedVta,  false);
        hilo.start();
        
        return true;
    }
    
    /**
     * REALIZA LA OPERACION DEL QUOTE CON PROVEEDOR
     * @author DESARROLLO3 
     * @since 2015.03.26
     * @param tarjetaPuntos
     * @param pNumPedVta
     * @param isAdicionarBonificado indica si lista de envio incluira productos bonificados
     * @return
     * @throws Exception
     */
    public static boolean procesarQuote(TarjetaBean tarjetaPuntos, String pNumPedVta, boolean isAdicionarBonificado)throws Exception{
        boolean resultado = true;
        // LISTA DE PRODUCTOS PARA QUOTE
        Long t1 = System.currentTimeMillis();
        ArrayList listaProducto = DBPuntos.obtenerListaProducto(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPedVta, isAdicionarBonificado);
        Long t2 = System.currentTimeMillis();
        log.info("PROGRAMA DE PUNTOS [QUOTE] : \nTIEMPO PARA OBTENER LISTA DESDE DB PARA ENVIO QUOTE "+(t2-t1)+"\nTotal de productos -->"+listaProducto.size()+"\nLista de Productos -->"+listaProducto);
        t1 = System.currentTimeMillis();
        // REALIZA EL QUOTE
        log.info("PROGRAMA DE PUNTOS [QUOTE] : ENVIO DE QUOTE A PROVEEDOR \nLista de Productos -->"+listaProducto);
        VariablesPuntos.frmPuntos.obtenerCotizacion(listaProducto, getDNI_USU());
        t2 = System.currentTimeMillis();
        log.info("PROGRAMA DE PUNTOS [QUOTE] : TIEMPO DE RESPUESTA DE QUOTE CON PROVEEDOR --> "+(t2-t1)+"\nLista de Producto Respuesta --> "+tarjetaPuntos.getListaBonificados());
        tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
        log.info("PROGRAMA DE PUNTOS [QUOTE] : Estado de tarjeta --> "+tarjetaPuntos.getEstadoTarjeta()
                 +" ESTADO --> "+tarjetaPuntos.getEstadoOperacion()+" \n MENSAJE: --> "+
                 UtilityPuntos.obtenerMensajeErrorLealtad(tarjetaPuntos.getEstadoOperacion(),tarjetaPuntos.getMensaje()));
        
        if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())){
            resultado = false;
            log.info("PROGRAMA DE PUNTOS [QUOTE] : MODO OFFLINE");
        }else{
            if(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                || FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                || FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                || FarmaPuntosConstante.EstadoTarjeta.INVALIDA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
            ){
                resultado = false;
                log.info("PROGRAMA DE PUNTOS [QUOTE] : ESTADO DE TARJETA--> "+tarjetaPuntos.getEstadoTarjeta()+"\n"+
                         "mensaje --> "+ UtilityPuntos.obtenerMensajeErrorLealtad(tarjetaPuntos.getEstadoOperacion(),tarjetaPuntos.getMensaje()));
                if(FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                   || FarmaPuntosConstante.EstadoTarjeta.INVALIDA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                   || FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                ){
                    VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                    log.info("PROGRAMA DE PUNTOS [QUOTE] : se cancelo proceso de venta por puntos");
                }
            }else{
                if(!FarmaPuntosConstante.EXITO.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())){
                    resultado = false;
                    log.info("PROGRAMA DE PUNTOS [QUOTE] : ERROR EN ORBIS \n CODIGO DE ERROR--> "+tarjetaPuntos.getEstadoTarjeta()+"\n"+
                             "mensaje --> "+ UtilityPuntos.obtenerMensajeErrorLealtad(tarjetaPuntos.getEstadoOperacion(),tarjetaPuntos.getMensaje()));    
                    VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                }
            }
            
        }
        return resultado;
    }
    
    /**
     *
     * @param pJDialog
     * @param pNumPedVta
     * @param myParentFrame
     * @throws Exception
     */
    public static void enviarPedidoPuntos(JDialog pJDialog, String pNumPedVta, Frame myParentFrame, boolean isLlevaPromociones)throws Exception{
        
        
        TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
        boolean isMuestraPantalla = true;
        FarmaVariables.vAceptar = true;
        
        if(tarjetaPuntos!=null){
            if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())
            || FarmaPuntosConstante.EstadoTarjeta.SIN_ESTADO.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                log.info("PROGRAMA DE PUNTOS [QUOTE] : Proceso en Offline o tarjeta sin estado, cancela operacion de quote.");
                return;
            }
            
            isMuestraPantalla = procesarQuote(tarjetaPuntos, pNumPedVta);
            
            String pIndPedConvenio = DBConvenioBTLMF.getIndPedidoConvenioBTLMF(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPedVta);
            if("S".equalsIgnoreCase(pIndPedConvenio)){
                isMuestraPantalla = false;
                log.info("PROGRAMA DE PUNTOS [QUOTE] : venta convenio");
            }
            
            // SE MUESTRA PANTALLA DE BONIFICADOS
            if(isMuestraPantalla && DBPuntos.indicadorMuestrPantallaBonifica()){

                List lstProducto = tarjetaPuntos.getListaBonificados();
                String auxLista = "";
                for(int i=0;i<lstProducto.size();i++){
                    String aux = (String)lstProducto.get(i);
                    if(auxLista.length()>0){
                        auxLista = auxLista + "@";
                    }
                    auxLista = auxLista + aux;
                }
                log.info("PROGRAMA DE PUNTOS [QUOTE] : LISTA DE PRODUCTOS, \nRESPUESTA DE PROVEEDOR "+auxLista);
                ArrayList lstBonificados = new ArrayList();
                Long t1 = System.currentTimeMillis();
                DBPuntos.verificaBonificados(lstBonificados,FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPedVta, auxLista);
                Long t2 = System.currentTimeMillis();
                log.info("PROGRAMA DE PUNTOS [QUOTE] : tiempo de metodo DB verificaBonificados "+(t2-t1)+"\ntotal de productos -->"+lstProducto.size()+"\n"+lstProducto+
                         "\ntotal de bonificados --> "+lstBonificados.size()+"\nlista "+lstBonificados);
                if(lstBonificados.size()>0){
                
                    isMuestraPantalla = validaDocumentoRedimirBonificar(myParentFrame, pJDialog, ConstantsPuntos.BONIFICACION_PRODUCTOS);    
                    if(isMuestraPantalla){
                        log.info("PROGRAMA DE PUNTOS [QUOTE] : ENCONTRO PRODUCTOS BONIFICADOS "+lstBonificados);
                        DlgBonificados dlg = new DlgBonificados(myParentFrame, "", true);
                        dlg.setLstProductoBonificados(lstBonificados);
                        dlg.setVisible(true);
                        if(FarmaVariables.vAceptar){
                            isLlevaPromociones = dlg.isIsLLevaPromociones();
                            ArrayList lstProdBonif = dlg.getTbModelLstBonificado().data;
                            ArrayList lstProdBonifRechazados = new ArrayList();
                            
                            String textLstProd = "";
                            boolean isElijio = false;
                            for(int i=0;i<lstProdBonif.size();i++){
                                // valida si escogio algun producto bonificado
                                if(!isElijio && ((Boolean)((ArrayList)lstProdBonif.get(i)).get(0)).booleanValue()){
                                    isElijio = true;
                                }
                                if(textLstProd.length()>0){
                                    textLstProd = textLstProd + "@";
                                }
                                textLstProd = textLstProd + ((ArrayList)lstProdBonif.get(i)).get(11)+","+((ArrayList)lstProdBonif.get(i)).get(10);
                                int cantidadAceptada = (int)Integer.parseInt((String)((ArrayList)lstProdBonif.get(i)).get(10));
                                int cantidadOfrecida = (int)Integer.parseInt((String)((ArrayList)lstProdBonif.get(i)).get(5));
                                
                                if(cantidadAceptada == 0 || cantidadAceptada < cantidadOfrecida){
                                    int valFrac = (int)Integer.parseInt((String)((ArrayList)lstProdBonif.get(i)).get(8));
                                    int valFracLocal = (int)Integer.parseInt((String)((ArrayList)lstProdBonif.get(i)).get(9));
                                    int cantidad = cantidadOfrecida - cantidadAceptada;
                                    cantidad = (cantidad * valFrac)/valFracLocal;
                                    lstProdBonifRechazados.add(((ArrayList)lstProdBonif.get(i)).get(12)+","+cantidad);
                                }
                            }
                            //if(isElijio){
                                //log.info("PROGRAMA DE PUNTOS [QUOTE] : ELIJIO BONIFICADOS "+lstProdBonif+"\n Rechazados --> "+lstProdBonifRechazados);
                                
                                if(lstProdBonifRechazados.size()>0){
                                    log.info("PROGRAMA DE PUNTOS [QUOTE] : PROCESA RECHAZADOS BONIFICADOS "+lstProdBonif+"\n Rechazados --> "+lstProdBonifRechazados);
                                    VariablesPuntos.frmPuntos.rechazarBonificado(lstProdBonifRechazados, getDNI_USU());
                                    tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
                                    if(tarjetaPuntos != null){
                                        log.info("PROGRAMA DE PUNTOS [QUOTE] : RECHAZO DE BONIFICADOS \n"+
                                                 "Estado Operacion: "+tarjetaPuntos.getEstadoOperacion()+
                                                 "\n Estado Tarjeta: "+ tarjetaPuntos.getEstadoTarjeta()+
                                                 "\n Mensaje: "+tarjetaPuntos.getMensaje()+
                                                 "\n RESPUESTA DE RECHAZO -->"+tarjetaPuntos.getListaBonificados());
                                        
                                        if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())){
                                            FarmaUtility.showMessage(pJDialog, "Programa Monedero: Por el momento no se podra bonificar productos.\n" +
                                                                                "Solo se podra acumular puntos.", null);
                                            return ;
                                        }else{
                                            if(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta()) 
                                            ){
                                                FarmaUtility.showMessage(pJDialog, "Programa Monedero:\nTarjeta Bloqueda para redencion.\n" +
                                                                                    "Se cancela la bonificación de Productos y solo se podra acumular puntos.", null);
                                                return ;
                                            }else{
                                                if(FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta()) ||
                                                    FarmaPuntosConstante.EstadoTarjeta.INVALIDA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta()) ||
                                                    FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                                                ){
                                                    String texto = obtenerMensajeErrorLealtad(tarjetaPuntos.getEstadoOperacion(), tarjetaPuntos.getMensaje());
                                                    VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                                                    FarmaUtility.showMessage(pJDialog, "Programa Monedero:\n" + texto + 
                                                                                       "Se cancelara la bonificación de Productos y " +
                                                                                       "los puntos acumulados en esta venta." , null);
                                                    return ;
                                                }else{
                                                    if(!FarmaPuntosConstante.EXITO.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())){
                                                        log.info("PROGRAMA DE PUNTOS [QUOTE] : ERROR INESPERADO ");
                                                        FarmaUtility.showMessage(pJDialog, "Programa Monedero:\n"+
                                                                                           "Error inesperado: se cancela bonificación de productos.", null);
                                                        procesarQuoteSinBonificados(tarjetaPuntos, pNumPedVta);
                                                    }else{
                                                        List lstProductoQ = tarjetaPuntos.getListaBonificados();
                                                        String auxLista2 = "";
                                                        for(int i=0;i<lstProductoQ.size();i++){
                                                            String aux = (String)lstProductoQ.get(i);
                                                            if(auxLista2.length()>0){
                                                                auxLista2 = auxLista2 + "@";
                                                            }
                                                            auxLista2 = auxLista2 + aux;
                                                        }
                                                        DBPuntos.ejecutarQuote(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPedVta, auxLista2);
                                                        return ;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    log.info("PROGRAMA DE PUNTOS [QUOTE] : ELIJIO TODOS BONIFICADOS " + lstProdBonif + "\n AUXILIAR --> " + auxLista);
                                    DBPuntos.ejecutarQuote(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPedVta, auxLista);
                                    return ;
                                }
                            /*}else{
                                log.info("PROGRAMA DE PUNTOS [QUOTE] : NO ELIJIO BONIFICADOS "+ lstBonificados);
                            }*/
                        }else{
                            log.info("PROGRAMA DE PUNTOS [QUOTE] : CERRO LA VENTANA DE PRODUCTOS BONIFICADOS, PRESIONO ESC");
                        }
                    }else{
                        log.info("PROGRAMA DE PUNTOS [QUOTE] : NO SE MUESTRA PANTALLA DE BONIFICACION");
                        procesarQuoteSinBonificados(tarjetaPuntos, pNumPedVta);
                        log.info("PROGRAMA DE PUNTOS [QUOTE] : PROCESO QUOTE SIN BONIFICADOS");
                    }
                }else{
                    log.info("PROGRAMA DE PUNTOS [QUOTE] : NO ENCONTRO PRODUCTOS BONIFICADOS ");
                }
                
            }else{
                log.info("PROGRAMA DE PUNTOS: [QUOTE] MUESTRA PANTALLA : variable --> "+isMuestraPantalla+" INDICADOR DB  --> "+DBPuntos.indicadorMuestrPantallaBonifica());
            }
        }else{
            log.info("PROGRAMA DE PUNTOS: [QUOTE] TARJETA NO INICIALIZADA");
        }
        
    }
    
    public static String pTimeOutOrbis(){
        String vValor="";
        
        try {
            vValor = DBPuntos.getTimeOutOrbis();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            //Si ocurre cualquier error se tiene 1 Segundo x Defecto.
            vValor="1000";
        }
        
        return vValor.trim();
    }
    
    public static int obtieneTiempoMaximoLectora(){
        try{
            return DBPuntos.getTiempoMaxLectora();
        }catch(Exception ex){
            log.error("PROGRAMA PUNTOS: ERROR AL OBTENER MAXIMO TIEMPO DE LECTORA DE BARRAS");
            log.error("",ex);
            return 200;
        }
    }
    
    /**
     *
     * @author KMONCADA
     * @param tarjetaPuntos
     * @param myParentFrame
     * @param pJDialog
     * @param nroDocumento
     * @return
     * @throws Exception
     */
    public static boolean validaTarjetaAdicional(TarjetaBean tarjetaPuntos, Frame myParentFrame, JDialog pJDialog, String nroDocumento)throws Exception{
        boolean resultado = true;
        
        if(FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta()) &&
            tarjetaPuntos.getDeslizaTarjeta()){
            
            int cantidadAdicional = cantidadTieneTarjetasAdicionales(myParentFrame, pJDialog, nroDocumento, true, true);
            if(cantidadAdicional==-1){
                resultado = false;
            }else{
                if(cantidadAdicional >= 0){
                    resultado = true;
                }
            }
            /* 
            Afiliado afiliado = afiliado = VariablesPuntos.frmPuntos.obtenerDatosAfiliado(nroDocumento, getDNI_USU());
            if(afiliado != null){
                List<String> lstTarjeta = afiliado.getTarjetas();
                log.info("PROGRAMA DE PUNTOS : [CONSULTA DE AFILIADO - ADICIONAL] TARJETAS REGISTRADAS "+lstTarjeta);
                int contador = 0;
                for(int i=0;i<lstTarjeta.size();i++){
                    String nroDocAux = lstTarjeta.get(i).trim();
                    if(nroDocAux.trim().length()>0 && !afiliado.getDni().trim().equalsIgnoreCase(nroDocAux)){
                        contador++;
                    }
                }
                log.info("PROGRAMA DE PUNTOS : [CONSULTA DE AFILIADO - ADICIONAL] TOTAL DE TARJETA EXISTENTES -->"+contador);
                if(contador>0){
                    DlgValidaDocumento dlg = new DlgValidaDocumento(myParentFrame, "",true);
                    dlg.setNroDocumento(nroDocumento);
                    dlg.setVisible(true);
                    String nroDocumentoDeslizo = dlg.getTextNroDocumento().trim();
                    if(nroDocumentoDeslizo.trim().length() == 0){
                        FarmaUtility.showMessage(pJDialog, "Debe ingresar el Nro Documento de Identidad.", null);
                        resultado = false;
                    }
                }else{
                    resultado = true;
                    log.info("PROGRAMA DE PUNTOS : [CONSULTA DE AFILIADO - ADICIONAL] NO ES TARJETA ADICIONAL");
                }
            }else{
                resultado = false;
                log.info("PROGRAMA DE PUNTOS : [CONSULTA DE AFILIADO - ADICIONAL] NO SE PUDO OBTENER AFILIADO");
                //throw new Exception("PROGRAMA PUNTOS [CONSULTA DE AFILIADO - ADICIONAL] NO SE PUDO OBTENER DATOS DE AFILIADO");
            } */
        }else{
            log.info("PROGRAMA DE PUNTOS : [CONSULTA DE AFILIADO - ADICIONAL] NO CUMPLE CON REQUERIMIENTOS DE VALIDACION DE TARJETA ADICIONAL. \n"+
                     "DELIZO TARJETA --> "+tarjetaPuntos.getDeslizaTarjeta()+"\n"+
                     "ESTADO DE TARJETA --> "+tarjetaPuntos.getEstadoTarjeta());
        }
        return resultado;
    }
    
    /**
     *
     * @param myParentFrame
     * @param pJDialog
     * @param tipoOperacion
     * @return
     * @throws Exception
     */
    public static boolean validaDocumentoRedimirBonificar(Frame myParentFrame, JDialog pJDialog, String tipoOperacion)throws Exception{
        boolean resultado = true;
        if(VariablesPuntos.frmPuntos != null && VariablesPuntos.frmPuntos.getTarjetaBean() != null){
            TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
            String docBonifica = DBPuntos.obtenerIndicadorDocBonifica();
            // T: DESLIZA TARJETA, D:INGRESO DE DNI, A: AMBOS
            boolean isRequiereTarjeta = false;
            boolean isRequiereDni = false;
            
            if(ConstantsPuntos.BONIFICA_CON_TARJETA.equalsIgnoreCase(docBonifica)){
                resultado = tarjetaPuntos.getDeslizaTarjeta();
                isRequiereTarjeta = true;
                isRequiereDni = false;
            }else{
                if(ConstantsPuntos.BONIFICA_CON_DNI.equalsIgnoreCase(docBonifica)){
                    resultado = tarjetaPuntos.getDeslizaTarjeta() || tarjetaPuntos.getEscaneaDNI();
                    isRequiereTarjeta = false;
                    isRequiereDni = true;
                }else{
                    if(ConstantsPuntos.BONIFICA_CON_AMBOS.equalsIgnoreCase(docBonifica)){
                        resultado = tarjetaPuntos.getDeslizaTarjeta() && tarjetaPuntos.getEscaneaDNI();
                        isRequiereTarjeta = true;
                        isRequiereDni = true; 
                    }
                }
            }
            if(!resultado){
                String descMsjOperacion = "";
                
                if(ConstantsPuntos.REDENCION_PTOS.equalsIgnoreCase(tipoOperacion)){
                    descMsjOperacion = "Redención";
                }else if(ConstantsPuntos.BONIFICACION_PRODUCTOS.equalsIgnoreCase(tipoOperacion)){
                    descMsjOperacion = "Bonificación";
                }
                String mensaje = "Para realizar la operación de "+descMsjOperacion+", solicitar:\n\n";
                if(isRequiereTarjeta && !tarjetaPuntos.getDeslizaTarjeta()){
                    mensaje = mensaje + "--> TARJETA DE PROGRAMA PUNTOS";
                }
                if(isRequiereDni && !tarjetaPuntos.getEscaneaDNI()){
                    mensaje = mensaje + "\n--> DOCUMENTO DE IDENTIDAD";
                }
                
                if(ConstantsPuntos.REDENCION_PTOS.equalsIgnoreCase(tipoOperacion)){
                    mensaje = mensaje+"\n\n"+"¿Desea redimir?";
                }else if(ConstantsPuntos.BONIFICACION_PRODUCTOS.equalsIgnoreCase(tipoOperacion)){
                    mensaje = mensaje+"\n\n"+"¿Desea bonificar?";
                }
                
                boolean rspta = JConfirmDialog.rptaConfirmDialog(pJDialog, mensaje);
                if(rspta){
                    DlgVerificaDocRedencionBonifica dlgVerifica = new DlgVerificaDocRedencionBonifica(myParentFrame, "", true, tipoOperacion);
                    dlgVerifica.setIsRequiereDni(isRequiereDni);
                    dlgVerifica.setIsRequiereTarjeta(isRequiereTarjeta);
                    dlgVerifica.setNroDocumento(tarjetaPuntos.getDni());
                    if(isRequiereTarjeta && tarjetaPuntos.getDeslizaTarjeta() && !tarjetaPuntos.getDni().equalsIgnoreCase(tarjetaPuntos.getNumeroTarjeta())){
                        dlgVerifica.setTextTarjeta(tarjetaPuntos.getNumeroTarjeta());
                    }
                    dlgVerifica.setVisible(true);
                    resultado = FarmaVariables.vAceptar;
                }else{
                    resultado = false;
                }
            }else{
                resultado = true;
            }
            
        }else{
            resultado = false;
        }
        return resultado;
    }
    
    /**
     *
     * @author KMONCADA
     * @since 
     * @param myParentFrame
     * @param nroTarjeta
     * @param tipoOperacion
     * @param isDeslizaTarjeta
     * @param nroDocIdentidad
     * @return
     * @throws Exception
     */
    public static String obtenerEstadoTarjeta(Frame myParentFrame, String nroTarjeta, String tipoOperacion, boolean isDeslizaTarjeta, String nroDocIdentidad)throws Exception{
        if(VariablesPuntos.frmPuntos != null){
            if(!UtilityPuntos.isTarjetaValida(nroTarjeta)){
                log.info("PROGRAMA DE PUNTOS [CONSULTA ESTADO TARJETA] : TARJETA INGRESADA INVALIDA --> " + nroTarjeta);
                throw new Exception("PROGRAMA DE PUNTOS: \nTARJETA INVALIDA, VERIFIQUE!!!");
            }
            String rsptaTarjeta = VariablesPuntos.frmPuntos.getEstadoTarjeta(nroTarjeta, getDNI_USU());
            String[] array = rsptaTarjeta.split("@");
            String estadoTarjeta = array[0].trim();
            if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(estadoTarjeta)){
                log.info("PROGRAMA DE PUNTOS : [CONSULTA TARJETA] OPERACION MODO OFFLINE");
                if(ConstantsPuntos.REDENCION_PTOS.equalsIgnoreCase(tipoOperacion))
                    return nroDocIdentidad;
                else{
                    throw new Exception("PROGRAMA DE PUNTOS: \n"+obtenerMensajeErrorLealtad(estadoTarjeta, ""));
                }
            }else if(FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(estadoTarjeta)){
                TarjetaBean tarjetaPuntos = new TarjetaBean();
                tarjetaPuntos.setDeslizaTarjeta(isDeslizaTarjeta);
                tarjetaPuntos.setEstadoTarjeta(array[0].trim());
                
                boolean rsptaValida = validaTarjetaAdicional(tarjetaPuntos, myParentFrame, new JDialog(), nroDocIdentidad);
                if(rsptaValida){
                    registroAfiliadoAutomatico(nroDocIdentidad, nroTarjeta, nroTarjeta);
                    return nroDocIdentidad;
                }
                log.info("PROGRAMA DE PUNTOS [CONSULTA ESTADO TARJETA] : VALIDACION DE TARJETA ADICIONAL NO EXITOSA.");
                throw new Exception("PROGRAMA DE PUNTOS: \n"+obtenerMensajeErrorLealtad(estadoTarjeta, ""));
                
            }else if(FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA.equalsIgnoreCase(estadoTarjeta) ||
                     FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR.equalsIgnoreCase(estadoTarjeta) ||
                     FarmaPuntosConstante.EstadoTarjeta.INVALIDA.equalsIgnoreCase(estadoTarjeta)){
                log.info("PROGRAMA DE PUNTOS [CONSULTA ESTADO TARJETA] : ESTADO DE TARJETA --> "+estadoTarjeta);
                throw new Exception("PROGRAMA DE PUNTOS: \n"+obtenerMensajeErrorLealtad(estadoTarjeta, "TARJETA BLOQUEDA/BLOQUEDA REDIMIR/INVALIDA"));
            }
            log.info("PROGRAMA DE PUNTOS [CONSULTA ESTADO TARJETA] : OK - ESTADO DE TARJETA --> "+estadoTarjeta);
            return array[1].trim();
        }else{
            log.info("PROGRAMA DE PUNTOS [CONSULTA ESTADO TARJETA] : VARIABLES NO INICIALIZADAS.");
            throw new Exception("PROGRAMA DE PUNTOS: \nVARIABLES NO INICIALIZADAS.");
        }
    }
    
    /**
     *
     * @param myParentFrame
     * @param pJDialog
     * @param tarjetaPuntosOld
     */
    public static void transferirInscripcionBonificados(Frame myParentFrame, JDialog pJDialog, TarjetaBean tarjetaPuntosOld){
        if(VariablesPuntos.frmPuntos != null && VariablesPuntos.frmPuntos.getTarjetaBean() != null){
            log.info("PROGRAMA DE PUNTOS : [TRANSFERENCIA DE INSCRITOS] INICIO");
            TarjetaBean tarjetaPuntosActual = VariablesPuntos.frmPuntos.getTarjetaBean();
            log.info("PROGRAMA DE PUNTOS : [TRANSFERENCIA DE INSCRITOS] TARJETA ANTIGUA "+tarjetaPuntosOld.getListaInscritos());
            log.info("PROGRAMA DE PUNTOS : [TRANSFERENCIA DE INSCRITOS] TARJETA NUEVA "+tarjetaPuntosActual.getListaInscritos());
            if(tarjetaPuntosActual.getDni().trim().length() > 0 && tarjetaPuntosOld.getDni().trim().length() > 0){
                if(tarjetaPuntosActual.getDni().trim().equalsIgnoreCase(tarjetaPuntosOld.getDni().trim())){
                    log.info("PROGRAMA DE PUNTOS : [TRANSFERENCIA DE INSCRITOS] MISMO CLIENTE "+tarjetaPuntosActual.getDni() +" - "+tarjetaPuntosOld.getDni());
                    tarjetaPuntosActual.setListaInscritos(tarjetaPuntosOld.getListaInscritos());
                    log.info("PROGRAMA DE PUNTOS : [TRANSFERENCIA DE INSCRITOS] TARJETA ANTIGUA "+tarjetaPuntosOld.getListaInscritos());
                    log.info("PROGRAMA DE PUNTOS : [TRANSFERENCIA DE INSCRITOS] TARJETA NUEVA "+tarjetaPuntosActual.getListaInscritos());
                }
                String codProd = "";
                String descProd = "";
                for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
                    codProd = (String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(0);
                    descProd = codProd+" - "+(String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(1);
                    log.info("PROGRAMA DE PUNTOS : [TRANSFERENCIA DE INSCRITOS] CODIGO DE PRODUCTO "+codProd);
                    UtilityVentas.verificaInscripcionX1(pJDialog,myParentFrame,codProd, descProd);
                }
        
                for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
                    codProd = (String)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i)).get(0);
                    descProd = codProd+" - "+(String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(1);
                    log.info("PROGRAMA DE PUNTOS : [TRANSFERENCIA DE INSCRITOS] CODIGO DE PRODUCTO "+codProd);
                    UtilityVentas.verificaInscripcionX1(pJDialog,myParentFrame,codProd,descProd);
                }
            }
        }
    }
    
    public static int cantidadTieneTarjetasAdicionales2(Frame myParentFrame, JDialog pJDialog, String nroDocumento, boolean solicitaValidaDocumento)throws Exception{
        int cantidadTarjeta = -1;
        Afiliado afiliado = afiliado = VariablesPuntos.frmPuntos.obtenerDatosAfiliado(nroDocumento, getDNI_USU());
        if(FarmaPuntosConstante.EXITO.equalsIgnoreCase(VariablesPuntos.frmPuntos.getTarjetaBean().getEstadoOperacion())){
            if(afiliado != null){
                List<String> lstTarjeta = afiliado.getTarjetas();
                log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] TARJETAS ASOCIADAS A DOCUMENTO --> "+nroDocumento+"\n"+
                         "TARJETAS --> "+lstTarjeta);
                int contador = 0;
                for(int i=0;i<lstTarjeta.size();i++){
                    String nroDocAux = lstTarjeta.get(i).trim();
                    if(nroDocAux.trim().length()>0 && !afiliado.getDni().trim().equalsIgnoreCase(nroDocAux)){
                        contador++;
                    }
                }
                log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] TOTAL DE TARJETA EXISTENTES ASOCIADAS -->"+contador);
                if(contador>0){
                    cantidadTarjeta = contador;
                    if(solicitaValidaDocumento){
                        log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] SOLICITA VALIDA DOCUMENTO DE IDENTIDAD");
                        DlgValidaDocumento dlg = new DlgValidaDocumento(myParentFrame, "",true);
                        dlg.setNroDocumento(nroDocumento);
                        dlg.setVisible(true);
                        String nroDocumentoDeslizo = dlg.getTextNroDocumento().trim();
                        if(nroDocumentoDeslizo.trim().length() == 0){
                            FarmaUtility.showMessage(pJDialog, "Debe ingresar el Nro Documento de Identidad.", null);
                            cantidadTarjeta = -1;
                            //resultado = false;
                        }
                    }
                }else{
                    cantidadTarjeta = 0;
                    //cantidadTarjeta = true;
                    log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] NO ES TARJETA ADICIONAL");
                }
            }else{
                //m cantidadTarjeta = false;
                log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] : NO SE PUDO OBTENER AFILIADO --> "+nroDocumento);
                //throw new Exception("PROGRAMA PUNTOS: ERROR AL OBTENER DATOS DE AFILIADO");
            }
        }
        return cantidadTarjeta;
        
    }
    
    private static int cantidadTieneTarjetasAdicionales(Frame myParentFrame, JDialog pJDialog, String nroDocumento, boolean solicitaValidaDocumento, boolean actualizaAfiliado)throws Exception{
        int cantidadTarjeta = -1;
        Afiliado afiliado = afiliado = VariablesPuntos.frmPuntos.obtenerDatosAfiliadoSinTarjeta(nroDocumento, getDNI_USU());

        if(afiliado != null){
            if(afiliado.getDni().trim().length()==0){
                log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] CLIENTE NUEVO NO REGISTRO EN ORBIS --> "+nroDocumento+"\n");
                cantidadTarjeta = 0;
            }else{
                List<String> lstTarjeta = afiliado.getTarjetas();
                log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] TARJETAS ASOCIADAS A DOCUMENTO --> "+nroDocumento+"\n"+
                         "TARJETAS --> "+lstTarjeta);
                int contador = 0;
                for(int i=0;i<lstTarjeta.size();i++){
                    String nroDocAux = lstTarjeta.get(i).trim();
                    if(nroDocAux.trim().length()>0 && !afiliado.getDni().trim().equalsIgnoreCase(nroDocAux)){
                        contador++;
                    }
                }
                log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] TOTAL DE TARJETA EXISTENTES ASOCIADAS -->"+contador);
                if(contador>0){
                    cantidadTarjeta = contador;
                    if(solicitaValidaDocumento){
                        log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] SOLICITA VALIDA DOCUMENTO DE IDENTIDAD");
                        FarmaUtility.showMessage(pJDialog, "PROGRAMA DE PUNTOS: TARJETA ADICIONAL:\nSOLICITE DOCUMENTO DE IDENTIDAD E INGRESELO MEDIANTE EL LECTOR DE BARRAS.", null);
                        DlgVerificaDocRedencionBonifica dlgVerifica = new DlgVerificaDocRedencionBonifica(myParentFrame, "", true, ConstantsPuntos.MNTO_FIDELIZACION);
                        dlgVerifica.setNroDocumento(nroDocumento);
                        dlgVerifica.setIsRequiereDni(true);
                        dlgVerifica.setIsRequiereTarjeta(false);
                        dlgVerifica.setVisible(true);
                        if(!FarmaVariables.vAceptar){
                            FarmaUtility.showMessage(pJDialog, "Debe ingresar el Nro Documento de Identidad.", null);
                            cantidadTarjeta = -1;
                        }
                        /*
                        DlgValidaDocumento dlg = new DlgValidaDocumento(myParentFrame, "",true);
                        dlg.setNroDocumento(nroDocumento);
                        dlg.setVisible(true);
                        String nroDocumentoDeslizo = dlg.getTextNroDocumento().trim();
                        if(nroDocumentoDeslizo.trim().length() == 0){
                            FarmaUtility.showMessage(pJDialog, "Debe ingresar el Nro Documento de Identidad.", null);
                            cantidadTarjeta = -1;
                            //resultado = false;
                        }*/
                    }
                }else{
                    cantidadTarjeta = 0;
                    //cantidadTarjeta = true;
                    log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] NO TIENE OTRAS TARJETAS");
                }
                if(actualizaAfiliado){
                    if(afiliado != null){
                        log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] CONSULTA CON EXITO\n"+
                                 "NOMBRE.AFILIADO : "+afiliado.getNombre()+"\n"+
                                 "APE.PAT.AFILIADO : "+afiliado.getApParterno()+"\n"+
                                 "APE.MAT.AFILIADO : "+afiliado.getApMarterno()+"\n"+
                                 "NRO DOCUMENTO : "+afiliado.getDni());
                        String aux = VariablesFidelizacion.vNumTarjeta;
                        VariablesFidelizacion.vNumTarjeta = "";
                        VariablesFidelizacion.vIndEstado = "A";
                        DBFidelizacion.insertarClienteFidelizacion(ConstantsPuntos.IND_PROCESO_ORBIS, 
                                                                   ConstantsPuntos.TRSX_ORBIS_ENVIADA, 
                                                                   afiliado);    
                        VariablesFidelizacion.vNumTarjeta = aux;
                    }else{
                        log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] CONSULTA DE AFILIADO ERROR");
                    }
                }
            }
            
        }else{
            //m cantidadTarjeta = false;
            log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] : NO SE PUDO OBTENER AFILIADO --> "+nroDocumento);
            //throw new Exception("PROGRAMA PUNTOS: ERROR AL OBTENER DATOS DE AFILIADO");
            
            if(solicitaValidaDocumento){
                log.info("PROGRAMA DE PUNTOS [VALIDA TARJETAS ADICIONALES] SOLICITA VALIDA DOCUMENTO DE IDENTIDAD");
                FarmaUtility.showMessage(pJDialog, "PROGRAMA DE PUNTOS: \nSOLICITE DOCUMENTO DE IDENTIDAD E INGRESELO MEDIANTE EL LECTOR DE BARRAS.", null);
                DlgVerificaDocRedencionBonifica dlgVerifica = new DlgVerificaDocRedencionBonifica(myParentFrame, "", true, ConstantsPuntos.MNTO_FIDELIZACION);
                dlgVerifica.setNroDocumento(nroDocumento);
                dlgVerifica.setIsRequiereDni(true);
                dlgVerifica.setIsRequiereTarjeta(false);
                dlgVerifica.setVisible(true);
                cantidadTarjeta = 0;
                if(!FarmaVariables.vAceptar){
                    FarmaUtility.showMessage(pJDialog, "Debe ingresar el Nro Documento de Identidad.", null);
                    cantidadTarjeta = -1;
                }
                /*
                DlgValidaDocumento dlg = new DlgValidaDocumento(myParentFrame, "",true);
                dlg.setNroDocumento(nroDocumento);
                dlg.setVisible(true);
                String nroDocumentoDeslizo = dlg.getTextNroDocumento().trim();
                if(nroDocumentoDeslizo.trim().length() == 0){
                    FarmaUtility.showMessage(pJDialog, "Debe ingresar el Nro Documento de Identidad.", null);
                    cantidadTarjeta = -1;
                    //resultado = false;
                }*/
            }
        }
        
        return cantidadTarjeta;
        
    }
}
