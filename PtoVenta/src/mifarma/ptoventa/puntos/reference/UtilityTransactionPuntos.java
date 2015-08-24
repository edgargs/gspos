package mifarma.ptoventa.puntos.reference;

import farmapuntos.bean.TarjetaBean;

import farmapuntos.util.FarmaPuntosConstante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityTransactionPuntos {

    private final Logger log = LoggerFactory.getLogger(UtilityTransactionPuntos.class);

    public String vNumPedVta = "";
    public String vTarjTransaccOrbis = "";
    public String vDniPedido = "";
    public String vTarjetaPedido = "";

    boolean vIndAsocProdMasUNO = false;
    // SE VERIFICA SI PROCESO EN ORBIS - EL PEDIDO ORIGINAL QUE SE ANULA
    boolean vIndProcOrbisCobro = false;
    boolean vIndPuntosAcum = false;
    boolean vIndRedimio = false;
    boolean vAnulaOnline = false;
    boolean vAnulaEnOrbis = false;
    boolean vIndBonificadoPedido = false;
    
    boolean vIndOperoConOrbis = false;
    
    String vIdTrxAnula_Orbis = "";
    String vNroAutoriza_Anula_Orbis = "";
    
    boolean vIndDescartaPedidoOrbis = false;
    
    String RPT_ORBIS_TRJ_INVALIDA = "41";

    public UtilityTransactionPuntos(String pNumPedido) {
        setVNumPedVta(pNumPedido);
    }

    public void reset() {
        vNumPedVta = "";
        vTarjTransaccOrbis = "";
        vDniPedido = "";
        vTarjetaPedido = "";
        vIndAsocProdMasUNO = false;
        vIndProcOrbisCobro = false;
        vIndPuntosAcum = false;
        vIndRedimio = false;
        vAnulaOnline = false;
        vAnulaEnOrbis = false;
        vIndBonificadoPedido = false;
    }

    public void imprimeVariables() {
        log.info("**** IMPRIME VARIABLES UtilityTransactionPuntos *****");
        log.info("vNumPedVta " + vNumPedVta + "");
        log.info("vTarjTransaccOrbis " + vTarjTransaccOrbis + "");
        log.info("vDniPedido " + vDniPedido + "");
        log.info("vTarjetaPedido " + vTarjetaPedido + "");
        log.info("vIndAsocProdMasUNO " + vIndAsocProdMasUNO + "");
        log.info("vIndProcOrbisCobro " + vIndProcOrbisCobro + "");
        log.info("vIndPuntosAcum " + vIndPuntosAcum + "");
        log.info("vIndRedimio " + vIndRedimio + "");
        log.info("vAnulaOnline " + vAnulaOnline + "");
        log.info("vAnulaOnline " + vAnulaEnOrbis + "");
        log.info("vIndBonificadoPedido " + vIndBonificadoPedido + "");
        log.info("vIndOperoConOrbis " + vIndOperoConOrbis + "");
        log.info("**** IMPRIME VARIABLES UtilityTransactionPuntos *****");
    }

    /**
     * Validaciones Previas para ANULAR en ORBIS
     * @return
     * @throws Exception
     */
    public boolean previoAnulaPuntos() throws Exception {
        String vNumPedVta = getVNumPedVta();
        boolean bResultado = false;
        if (DBPuntos.getIndPedActuaPuntos(vNumPedVta)) {
            log.info("Pedido " + vNumPedVta + " Si interactua con ORBIS PUNTOS ");
            /*
        T.NUM_PEDIDO,
        C.NUM_TARJ_PUNTOS ,
        T.DNI_CLI,
        T.COD_TARJETA
         * */
            List pListTarjPedido = DBPuntos.getPedAsocTarjeta(vNumPedVta);
            if (pListTarjPedido.size() == 0) {
                bResultado = false;
                log.info("Pedido " + vNumPedVta + " No tiene ASOCIADO UNA TARJETA DE OPERACION CON ORBIS - ERROR ");
                throw new Exception(
                        //"Error al validar pedido en puntos\n" +
                        "El pedido no tiene asociado una tarjeta de operación con puntos.\n" +
                        "Por favor llame a mesa de ayuda");
            } else {
                if (pListTarjPedido.size() == 1) {
                    log.info("Pedido " + vNumPedVta + " SI tiene ASOCIADO UNA TARJETA ");
                    Map mapFila = new HashMap();
                    mapFila = (HashMap)pListTarjPedido.get(0);
                    // Este campo DNI_CLI puede ser el DNI o Carnet Extranjeria
                    String pTarjetaEnvOrbis = mapFila.get("NUM_TARJ_PUNTOS").toString();
                    String pDocCli = mapFila.get("DNI_CLI").toString();
                    String pTarjetaPedido = mapFila.get("COD_TARJETA").toString();

                    setVTarjTransaccOrbis(pTarjetaEnvOrbis.trim());
                    setVDniPedido(pDocCli.trim());
                    setVTarjetaPedido(pTarjetaPedido.trim());

                    // SE VERIFICA SI PARTICIPA ALGUN PRODUCTO X + 1
                    setVIndAsocProdMasUNO(DBPuntos.getIndPedidoAsocProdMasUno(vNumPedVta));
                    // SE VERIFICA SI PROCESO EN ORBIS - EL PEDIDO ORIGINAL QUE SE ANULA
                    setVIndProcOrbisCobro(DBPuntos.getIndCobroProcesoOrbis(vNumPedVta));
                    setVIndPuntosAcum(DBPuntos.getIndPuntosAcum(vNumPedVta));
                    setVIndRedimio(DBPuntos.getIndPedidoRedimio(vNumPedVta));
                    setVIndBonificadoPedido(DBPuntos.isTieneBonificado(vNumPedVta));
                    // SI HAY PRODUCCION ASOCIADO A X+1 ó REMIDIO
                    // SE VALIDARA SI SE DEBE HACER ONLINE O OFFLINE

                    List vDataOpera = DBPuntos.getDataTrnxAnula(getVNumPedVta());
                    Map mapTrnx = new HashMap();
                    mapTrnx = (HashMap)vDataOpera.get(0);
                    String vIdTrx = mapTrnx.get("ID_TRANSACCION").toString();
                    String vNumAut = mapTrnx.get("NUMERO_AUTORIZACION").toString();
                    double vCtPIni = Double.parseDouble(mapTrnx.get("CTD_PUNTO_INI").toString().trim());
                    double vCtPAcum = Double.parseDouble(mapTrnx.get("CTD_PUNTO_ACUM").toString().trim());
                    double vCtPRedi = Double.parseDouble(mapTrnx.get("CTD_PUNTO_REDI").toString().trim());
                    double vCtPTot = Double.parseDouble(mapTrnx.get("CTD_PUNTO_TOT").toString().trim());
                    String vFecPed = mapTrnx.get("FEC_PED_VTA").toString();
                    ArrayList vListaDet = vListDetPedAnula(getVNumPedVta());
                    log.info("¿se necesita anular el pedido en Orbis ?");

                    ///////////////////////////////////////////////////////////////////////////////////////////////
                    //  VALIDACION DE PEDIDO EN ORBIS
                    //if (isVIndRedimio() || isVIndBonificadoPedido()) {
                    // TODOS LOS PEDIDOS QUE TIENEN TEMAS CON ORBIS VAN VALIDAR PEDIDO
                    
                        log.info("El Pedido " + vNumPedVta + " Tiene Bonificado ó Redimio puntos isVIndRedimio " +
                                 isVIndRedimio() + " isVIndBonificadoPedido " + isVIndBonificadoPedido());
                    TarjetaBean trnxValidaAnula = null;
                    //////////////////////////////////////////////////////////////////////////////////////////////
                        if (isVIndProcOrbisCobro()) {
                            log.info("El Pedido " + vNumPedVta +
                                     " Si se proceso en Orbis por lo tanto VALIDA ANULA EN ORBIS ");
                            
                            if(VariablesPuntos.frmPuntos==null)
                                throw new Exception("No se puede validar la anulación .\n" +
                                        "Porque el servicio de puntos no ha sido iniciado:" +
                                        "\n" +
                                        "Por favor llame a mesa de ayuda");
                            
                             trnxValidaAnula =
                                VariablesPuntos.frmPuntos.validarAnulacion(
                                                                           getVTarjTransaccOrbis(), 
                                                                           vIdTrx, 
                                                                           vNumAut,
                                                                           vListaDet, 
                                                                           UtilityPuntos.getDNI_USU());
                            
                            if (trnxValidaAnula == null) {
                                //FarmaUtility.showMessage(pDialog, "No se pudo Recuperar puntos por:\n" +
                                //        "El estado de la Respuesta No es Válido.", objeto);
                                throw new Exception("Se presentó un error en servicio de puntos,al validar la anulación.\n" +
                                        "La respuesta de la operación es vacía ó nula." +"\n" +
                                        "Por favor llame a mesa de ayuda");                        
                            }
                            else
                            if (FarmaPuntosConstante.EXITO.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                log.info("El Pedido " + vNumPedVta +
                                         " Si se proceso en Orbis por lo tanto VALIDA ANULA EN ORBIS ");
                            } else {
                                if (FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                    //indLinea = "N";
                                    log.info("El Pedido " + vNumPedVta +
                                             " NO hay conexion con ORBIS se obvia este paso debido a conexion");
                                } else if (FarmaPuntosConstante.DEVOLUCION_YA_APLICADA.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                    //indLinea = "N";
                                    log.info("El Pedido " + vNumPedVta +
                                             " ya se ANULO en orbis, por lo tanto todo OK continua con el resto.");
                                } 
                                else if (RPT_ORBIS_TRJ_INVALIDA.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                    setVIndDescartaPedidoOrbis(true);
                                    //La tarjeta es INVALIDA 
                                    log.info("La tarjeta esta Invalida del pedido " + vNumPedVta +
                                             " , por lo tanto todo OK continua con el resto. pero se Descarta la anulacion");
                                } 
                                else {
                                    log.info("El Pedido " + vNumPedVta + " ERROR LA VALIDACION NO PERMITE PROCEDER");
                                    throw new Exception("No se puede anular el pedido.\n" +
                                            "Porque el servicio de puntos indica que :" +
                                            trnxValidaAnula.getEstadoTarjeta() + "-" + 
                                    UtilityPuntos.obtenerMensajeErrorLealtad(trnxValidaAnula.getEstadoOperacion(),trnxValidaAnula.getMensaje())                                                        
                                                        +
                                            "\n" +
                                            "Por favor llame a mesa de ayuda");
                                }
                            }
                            log.info("El Pedido " + vNumPedVta + " Se termino de Validar ");
                        } else {
                            log.info("El Pedido " + vNumPedVta +
                                     " NO SE PROCESO EN ORBIS por lo TANTO NO VALIDA EN ORBIS porque NO SE ENVIO");
                            log.info("NO ES MANDATORIO");
                        }
                    
                    if(!isVIndDescartaPedidoOrbis())
                    {   
                        // SI LA TARJETA NO ESTA INACTIVA Y POR LO TANTO NO SE DESCARTA LA ANULACION
                        // DEBE DE ANULAR NORMAL CON TODAS LAS VALIDACIONES
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                        if (isVIndAsocProdMasUNO() || isVIndRedimio()) {
                            setVAnulaEnOrbis(true);
                            log.info("Pedido " + vNumPedVta + " Se tiene participacion en X + 1 -- Se DEBE ANULAR ");
                            //SI PROCESO EL PEDIDO ORIGINAL EN ORBIS
                            if (isVIndProcOrbisCobro()) {
                                if (FarmaPuntosConstante.EXITO.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion()) ||
                                    FarmaPuntosConstante.DEVOLUCION_YA_APLICADA.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                    log.info("El Pedido " + vNumPedVta +
                                             " Se Cobro de modo ONLINE es decir se proceso en ORBIS");
                                    log.info("El Pedido " + vNumPedVta +
                                             " Esto confirma que el pedido se hizo la validacion , para que valide de modo online");
                                    log.info("El Pedido " + vNumPedVta + " Por lo tanto se debe anular de modo ONLINE");
                                    setVAnulaOnline(true);
                                } else {
                                    log.info("El Pedido " + vNumPedVta +
                                             " el pedido NO HIZO la validacion y en este es OBLIGATORIO");
                                    if (FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion()))
                                        throw new Exception("No se Puede Anular el Pedido.\n" +
                                                "Porque no hubo conexión al intentar validar la anulación con sistema de puntos." +
                                                "\n" +
                                                "Por favor llame a mesa de ayuda");
                                    else
                                        throw new Exception("No se puede anular el pedido.\n" +
                                                "Porque el servicio de puntos indica que :" +
                                                trnxValidaAnula.getEstadoTarjeta() + "-" + 
                                    UtilityPuntos.obtenerMensajeErrorLealtad(trnxValidaAnula.getEstadoOperacion(),trnxValidaAnula.getMensaje())                                                        
                                                            
                                                            +
                                                "\n" +
                                                "Por favor llame a mesa de ayuda");
                                }
                            } else {
                                //El pedido ORIGINAL no se envio a ORBIS
                                //No se envia a ORBIS
                                setVAnulaOnline(false);
                            }
                        } else {
                            //VER SI ACUMULO PUNTOS
                            // OFFILNE
                            if (isVIndPuntosAcum()&&isVIndProcOrbisCobro()) {
                                setVAnulaEnOrbis(true);
                                if (FarmaPuntosConstante.EXITO.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion()) ||
                                    FarmaPuntosConstante.DEVOLUCION_YA_APLICADA.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                    log.info("El Pedido " + vNumPedVta +
                                             " Se Cobro de modo ONLINE es decir se proceso en ORBIS");
                                    log.info("El Pedido " + vNumPedVta +
                                             " Esto confirma que el pedido se hizo la validacion , para que valide de modo online");
                                    log.info("El Pedido " + vNumPedVta + " Por lo tanto se debe anular de modo ONLINE");
                                    setVAnulaOnline(true);
                                } else {
                                    log.info("El Pedido " + vNumPedVta +
                                             " el pedido NO HIZO la validacion y en este es OBLIGATORIO");
                                    if (FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())){
                                        log.info("El Pedido " + vNumPedVta +
                                                 " SOLO ACUMULA PUNTOS y NO HAY CONEXION ORBIS en Valida, entonces VALIDA DE MODO OFFLINE");
                                        setVAnulaOnline(false);
                                    }
                                    else
                                        throw new Exception("No se Puede Anular el Pedido.\n" +
                                                "Porque el servicio de puntos indica que :" +
                                                trnxValidaAnula.getEstadoTarjeta() + "-" + 
                                                            
                                    UtilityPuntos.obtenerMensajeErrorLealtad(trnxValidaAnula.getEstadoOperacion(),trnxValidaAnula.getMensaje())                                                        
                                                            +
                                                "\n" +
                                                "Por favor llame a mesa de ayuda");
                                }
                                log.info("Pedido " + vNumPedVta + " Solo acumula puntos se anula de modo offline");
                            } else {
                                setVAnulaEnOrbis(false);
                                log.info("Pedido " + vNumPedVta +
                                         " NO PARTICIPO EN ORBIS termina proceso NO ANULA EN NADA");
                            }
                        }
                        bResultado = true;
                   }
                    else{
                        // LA TARJETA ESTA INACTIVA Y POR LO TANTO SE DESCARTA LA ANULACION
                        // DEBE DE ANULAR NORMAL SIN TODAS LAS VALIDACIONES y NO ANULA EN ORBIS
                        setVAnulaEnOrbis(false);
                        setVAnulaOnline(false);
                        bResultado = true;
                    }
                } else {
                    bResultado = false;
                    log.info("El Pedido " + vNumPedVta +
                             " Tiene mas de UNA TARJETA asociada ERROR EN LA GENERACION DE PEDIDO");
                    log.info("NO SE PUEDE ANULAR " + vNumPedVta);
                    throw new Exception("Error al validar Pedido en Puntos\n" +
                            "El pedido tiene asociado mas de una tarjeta de operación con puntos.\n" +
                            "Por favor llame a mesa de ayuda");
                }
            }
        } else {
            log.info("Pedido " + vNumPedVta + " NO interactua con ORBIS PUNTOS ");
            bResultado = true;
            log.info("Pedido " + vNumPedVta + " Se puede anular y NO anula en ORBIS");
        }
        
        
        if (isVAnulaOnline()) 
            log.info("SI DEBE ANULAR ONLINE en orbis");
        else    
            log.info("NO DEBE ANULAR ONLINE en orbis");
        
        imprimeVariables();
        
        return bResultado;
    }

    public boolean anulaOrbis() throws Exception {
        boolean pResultado = false;
        if (getVNumPedVta().trim().length() > 0) {
            if (isVAnulaEnOrbis()) {
                List vDataOpera = DBPuntos.getDataTrnxAnula(getVNumPedVta());
                Map mapFila = new HashMap();
                mapFila = (HashMap)vDataOpera.get(0);
                String vIdTrx = mapFila.get("ID_TRANSACCION").toString();
                String vNumAut = mapFila.get("NUMERO_AUTORIZACION").toString();
                double vCtPIni = Double.parseDouble(mapFila.get("CTD_PUNTO_INI").toString().trim());
                double vCtPAcum = Double.parseDouble(mapFila.get("CTD_PUNTO_ACUM").toString().trim());
                double vCtPRedi = Double.parseDouble(mapFila.get("CTD_PUNTO_REDI").toString().trim());
                double vCtPTot = Double.parseDouble(mapFila.get("CTD_PUNTO_TOT").toString().trim());
                double vNetoPed = Double.parseDouble(mapFila.get("NETO_PED_VTA").toString().trim());
                String vFecPed = mapFila.get("FEC_PED_VTA").toString();
                ArrayList vListaDet = vListDetPedAnula(getVNumPedVta());
                log.info("¿se necesita anular el pedido en Orbis?");

                // SE VERIFICA SI PARTICIPA ALGUN PRODUCTO X + 1
                setVIndAsocProdMasUNO(DBPuntos.getIndPedidoAsocProdMasUno(vNumPedVta));
                // SE VERIFICA SI PROCESO EN ORBIS - EL PEDIDO ORIGINAL QUE SE ANULA
                setVIndProcOrbisCobro(DBPuntos.getIndCobroProcesoOrbis(vNumPedVta));
                setVIndPuntosAcum(DBPuntos.getIndPuntosAcum(vNumPedVta));
                setVIndRedimio(DBPuntos.getIndPedidoRedimio(vNumPedVta));
                setVIndBonificadoPedido(DBPuntos.isTieneBonificado(vNumPedVta));
                // SI HAY PRODUCCION ASOCIADO A X+1 ó REMIDIO
                

                if (isVAnulaOnline()) {
                    log.info("SI ANULA ONLINE en orbis");
                    // // // // // // // // // //
                    // ANULA VENTA EN ORBIS // //
                    // // // // // // // // // //
                    if(VariablesPuntos.frmPuntos==null)
                        throw new Exception("No se puede anular el pedido.\n" +
                                "Porque el servicio de puntos no ha sido iniciado:" +
                                "\n" +
                                "Por favor llame a mesa de ayuda");                    
                    
                    
                    TarjetaBean trnxValidaAnula =
                        /*
                        VariablesPuntos.frmPuntos.anularPedido(getVTarjTransaccOrbis(), vIdTrx, vNumAut, vListaDet,
                                                               getVNumPedVta(), vCtPRedi, vFecPed, vNetoPed,
                                                               UtilityPuntos.getDNI_USU());*/
                        VariablesPuntos.frmPuntos.anularPedido(getVTarjTransaccOrbis(),  vListaDet,
                                                               getVNumPedVta(), vCtPRedi, vFecPed, vNetoPed,
                                                               UtilityPuntos.getDNI_USU());                    

                    if (trnxValidaAnula == null) {
                        //FarmaUtility.showMessage(pDialog, "No se pudo Recuperar puntos por:\n" +
                        //        "El estado de la Respuesta No es Válido.", objeto);
                        throw new Exception("Se presentó un error en servicio de puntos,al intentar anular.\n" +
                                "La respuesta de la operación es vacía ó nula." +"\n" +
                                "Por favor llame a mesa de ayuda");                        
                    } else {
                        log.info("Anular Pedido ORBIS");
                        if (FarmaPuntosConstante.EXITO.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                            log.info("Anular Pedido ORBIS");
                            pResultado = true;
                            // CAMPOS SOLICITADOS POR LAIS PARA GUARDAR
                            setVIdTrxAnula_Orbis(trnxValidaAnula.getIdTransaccion());
                            setVNroAutoriza_Anula_Orbis(trnxValidaAnula.getNumeroAutororizacion());
                            setVIndOperoConOrbisAnulacion(true);
                        } else {
                            if (FarmaPuntosConstante.DEVOLUCION_YA_APLICADA.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                pResultado = true;
                                // CAMPOS SOLICITADOS POR LAIS PARA GUARDAR
                                setVIdTrxAnula_Orbis(trnxValidaAnula.getIdTransaccion());
                                setVNroAutoriza_Anula_Orbis(trnxValidaAnula.getNumeroAutororizacion());                                
                                setVIndOperoConOrbisAnulacion(true);
                            } 
                            else 
                            if (RPT_ORBIS_TRJ_INVALIDA.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                pResultado = true;
                                // LA tarjeta es Invalida se 
                                setVIndDescartaPedidoOrbis(true);
                                setVIndOperoConOrbisAnulacion(true);
                            } 
                            else {
                                if (FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(trnxValidaAnula.getEstadoOperacion())) {
                                    if(isVIndProcOrbisCobro()){
                                        log.info("Pedido ANULACION PEDIDO ORBIS "+getVNumPedVta()+ " NO HAY CONEXION" );
                                        if(isVIndAsocProdMasUNO()||isVIndRedimio()){
                                            log.info("Pedido ANULACION PEDIDO ORBIS "+getVNumPedVta()+ " Prod +1 o redime .. anulara online NO PERMITE ,.. error" );
                                            throw new Exception("Se presentó un error en servicio de puntos,al momento de anular el pedido.\n" +
                                                                "No hay conexión y no pudo ser procesada."+
                                                    //"Esta es la razón porque no se pudo anular :" +
                                                    
                                                    "\n" +
                                                    "Por favor llame a Mesa de Ayuda");
                                        }
                                        else{
                                            if(isVIndPuntosAcum()){
                                                log.info("Pedido ANULACION PEDIDO ORBIS "+getVNumPedVta()+ " Solo acumula puntos .. anulara offline" );
                                                pResultado = true;
                                                setVIndOperoConOrbisAnulacion(false);
                                            }
                                            else{
                                                pResultado = true;
                                                setVIndOperoConOrbisAnulacion(false);
                                            }
                                        }
                                    }
                                    else{
                                        pResultado = true;
                                        setVIndOperoConOrbisAnulacion(false);
                                    }
                                } else {
                                    pResultado = false;
                                    setVIndOperoConOrbisAnulacion(false);
                                    throw new Exception("Se presentó un error en servicio de puntos.\n" +
                                            "Esta es la razón porque no se pudo anular :" +
                                            trnxValidaAnula.getEstadoTarjeta() + "-" + 
                                    UtilityPuntos.obtenerMensajeErrorLealtad(trnxValidaAnula.getEstadoOperacion(),trnxValidaAnula.getMensaje())                                                        
                                                        +
                                            "\n" +
                                            "Por favor llame a mesa de ayuda");
                                }
                            }
                        }
                    }
                    // // // // // // // // // //
                    // // // // // // // // // //
                } else {
                    pResultado = true;
                    log.info("NO ANULA ONLINE en orbis");
                }
            } else {
                log.info("NO SE NECESITA ANULAR en orbis");
                pResultado = true;
            }
        } else {
            throw new Exception("Error al procesar la anulación\n" +
                    "No se sabe que pedido desea anular.\n" +
                    "Por favor llame a mesa de ayuda");
        }
        return pResultado;
    }
    
    public void saveTrxAnulOrbis()throws Exception {
        DBPuntos.pGrabaProcesoAnulaOrbis(getVNumPedVta(),isVIndOperoConOrbis(),
                                         getVIdTrxAnula_Orbis(),getVNroAutoriza_Anula_Orbis());
    }
    

    public void setVNumPedVta(String vNumPedVta) {
        this.vNumPedVta = vNumPedVta;
    }

    public String getVNumPedVta() {
        return vNumPedVta;
    }

    public void setVTarjTransaccOrbis(String vTarjTransaccOrbis) {
        this.vTarjTransaccOrbis = vTarjTransaccOrbis;
    }

    public String getVTarjTransaccOrbis() {
        return vTarjTransaccOrbis;
    }

    public void setVDniPedido(String vDniPedido) {
        this.vDniPedido = vDniPedido;
    }

    public String getVDniPedido() {
        return vDniPedido;
    }

    public void setVTarjetaPedido(String vTarjetaPedido) {
        this.vTarjetaPedido = vTarjetaPedido;
    }

    public String getVTarjetaPedido() {
        return vTarjetaPedido;
    }

    public void setVIndAsocProdMasUNO(boolean vIndAsocProdMasUNO) {
        this.vIndAsocProdMasUNO = vIndAsocProdMasUNO;
    }

    public boolean isVIndAsocProdMasUNO() {
        return vIndAsocProdMasUNO;
    }

    public void setVIndProcOrbisCobro(boolean vIndProcOrbisCobro) {
        this.vIndProcOrbisCobro = vIndProcOrbisCobro;
    }

    public boolean isVIndProcOrbisCobro() {
        return vIndProcOrbisCobro;
    }

    public void setVIndPuntosAcum(boolean vIndPuntosAcum) {
        this.vIndPuntosAcum = vIndPuntosAcum;
    }

    public boolean isVIndPuntosAcum() {
        return vIndPuntosAcum;
    }

    public void setVIndRedimio(boolean vIndRedimio) {
        this.vIndRedimio = vIndRedimio;
    }

    public boolean isVIndRedimio() {
        return vIndRedimio;
    }

    public void setVAnulaOnline(boolean vAnulaOnline) {
        this.vAnulaOnline = vAnulaOnline;
    }

    public boolean isVAnulaOnline() {
        return vAnulaOnline;
    }

    public void setVAnulaEnOrbis(boolean vAnulaEnOrbis) {
        this.vAnulaEnOrbis = vAnulaEnOrbis;
    }

    public boolean isVAnulaEnOrbis() {
        return vAnulaEnOrbis;
    }

    public void setVIndBonificadoPedido(boolean vIndBonificadoPedido) {
        this.vIndBonificadoPedido = vIndBonificadoPedido;
    }

    public boolean isVIndBonificadoPedido() {
        return vIndBonificadoPedido;
    }

    public ArrayList vListDetPedAnula(String pNumPed) throws Exception {
        return DBPuntos.getListProdPuntosAnula(pNumPed);
    }

    public void setVIndOperoConOrbisAnulacion(boolean vIndOperoConOrbis) {
        this.vIndOperoConOrbis = vIndOperoConOrbis;
    }

    public boolean isVIndOperoConOrbis() {
        return vIndOperoConOrbis;
    }

    public void setVIdTrxAnula_Orbis(String vIdTrxAnula_Orbis) {
        this.vIdTrxAnula_Orbis = vIdTrxAnula_Orbis;
    }

    public String getVIdTrxAnula_Orbis() {
        return vIdTrxAnula_Orbis;
    }

    public void setVNroAutoriza_Anula_Orbis(String vNroAutoriza_Anula_Orbis) {
        this.vNroAutoriza_Anula_Orbis = vNroAutoriza_Anula_Orbis;
    }

    public String getVNroAutoriza_Anula_Orbis() {
        return vNroAutoriza_Anula_Orbis;
    }

    public void setVIndDescartaPedidoOrbis(boolean vIndDescartaPedidoOrbis) {
        this.vIndDescartaPedidoOrbis = vIndDescartaPedidoOrbis;
    }

    public boolean isVIndDescartaPedidoOrbis() {
        return vIndDescartaPedidoOrbis;
    }

    public void descartaAnulacionOrbis() throws Exception {
        DBPuntos.pDescartaAnulaOrbis(getVNumPedVta(),isVIndDescartaPedidoOrbis());
    }
}
