package mifarma.ptoventa.inventario.reference;

import java.sql.SQLException;

import javax.swing.JDialog;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityInventarioWS {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityInventarioWS.class);
    
    /**
     * Constructor
     */
    public UtilityInventarioWS() {
    }
    
    /*public static boolean confirmarTransferencia(String pNumNota,String pCodLocalDestino,
                                              String pTipoOrigen,
                                                JDialog vDlg,Object obj){
        try {
            //actualizar a estado confirmado el pedido de transferencia
            String numTransf = pNumNota;
            String codLocalDestino = pCodLocalDestino;
            String tipoOrigenTransf = pTipoOrigen;
            log.debug("tipoOrigenTransf:" + tipoOrigenTransf + "***");
            log.debug("codLocalDestino:" + codLocalDestino + "***");
            log.debug("numTransf:" + numTransf + "***");
            DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "I",
                                                                 "N"); //graba inicio de confimacion de transferencia en local origen
            DBInventario.confirmarTransferencia(numTransf);
            DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "F",
                                                                 "N");//graba fin de confimacion de transferencia en local origen
            FarmaUtility.aceptarTransaccion();
            if (tipoOrigenTransf.equals("01")) { //si es TIPO LOCAL
                log.debug("jcallo: verificando si hay linea con matriz");
                VariablesInventario.vIndLineaMatriz =
                        FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                       FarmaConstants.INDICADOR_S);
                log.debug("jcallo: VariablesInventario.vIndLineaMatriz:" +
                          VariablesInventario.vIndLineaMatriz);
                //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
                if (VariablesInventario.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                    
                    log.debug("jcallo: tratando de realizar la transferencia a local destino y matriz con estado L");
                    DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "I",
                                                                         "S");

                    //try-catch para evitar bloqueo de la tablas de transferencia
                    //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
                    //si ocurriera algun error, se realizara solo la confirmacion en local origen
                    String resultado = "N";
                    try {
                        resultado =
                                DBInventario.realizarTransfDestino(numTransf, codLocalDestino, FarmaConstants.INDICADOR_N).trim();
                        if (resultado.equals(FarmaConstants.INDICADOR_S)) {
                            //si la respuesta es afirmativa S, realiza el commit remoto
                            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                  FarmaConstants.INDICADOR_S);
                            log.debug("actualizando el estado de la transferencia en local");
                            DBInventario.actualizarEstadoTransf(numTransf, "L");
                        }
                    } catch (Exception e) {
                        log.error("", e);
                        FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_S);
                    } finally {
                        log.debug("cerrando toda conexion remota");
                        FarmaConnectionRemoto.closeConnection();
                    }
                    //graba fin de confimacion remota de transferencia de local origen a matriz
                    DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "F", "S");
                    FarmaUtility.aceptarTransaccion();
                }
            }
            FarmaUtility.showMessage(vDlg, "Transferencia Confirmada.", obj);
            return true;
            
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            FarmaUtility.showMessage(vDlg, "Ha ocurrido un error al confirmar.\n" +
                    e, obj);
            return false;
        }
    }*/
    
    public static boolean confirmarTransferencia(String pNumNota,String pCodLocalDestino,
                                              String pTipoOrigen,
                                                JDialog vDlg,Object obj){
        try {
            //actualizar a estado confirmado el pedido de transferencia
            String numTransf = pNumNota;
            String codLocalDestino = pCodLocalDestino;
            String tipoOrigenTransf = pTipoOrigen;
            log.debug("tipoOrigenTransf:" + tipoOrigenTransf + "***");
            log.debug("codLocalDestino:" + codLocalDestino + "***");
            log.debug("numTransf:" + numTransf + "***");
            DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "I",
                                                                 "N"); //graba inicio de confimacion de transferencia en local origen
            DBInventario.confirmarTransferencia(numTransf);
            DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "F",
                                                                 "N");//graba fin de confimacion de transferencia en local origen
            FarmaUtility.aceptarTransaccion();
            if (tipoOrigenTransf.equals("01")) { //si es TIPO LOCAL
                log.debug("jcallo: verificando si hay linea con matriz");
                VariablesInventario.vIndLineaMatriz =
                        FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                       FarmaConstants.INDICADOR_S);
                log.debug("jcallo: VariablesInventario.vIndLineaMatriz:" +
                          VariablesInventario.vIndLineaMatriz);
                //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
                if (VariablesInventario.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                    
                    log.debug("jcallo: tratando de realizar la transferencia a local destino y matriz con estado L");
                    DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "I",
                                                                         "S");

                    //try-catch para evitar bloqueo de la tablas de transferencia
                    //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
                    //si ocurriera algun error, se realizara solo la confirmacion en local origen
                    String resultado = "N";
                    try {
                        resultado =
                                DBInventario.realizarTransfDestino(numTransf, codLocalDestino, FarmaConstants.INDICADOR_N).trim();
                        if (resultado.equals(FarmaConstants.INDICADOR_S)) {
                            //si la respuesta es afirmativa S, realiza el commit remoto
                            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                  FarmaConstants.INDICADOR_S);
                            log.debug("actualizando el estado de la transferencia en local");
                            DBInventario.actualizarEstadoTransf(numTransf, "L");
                        }
                    } catch (Exception e) {
                        log.error("", e);
                        FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_S);
                    } finally {
                        log.debug("cerrando toda conexion remota");
                        FarmaConnectionRemoto.closeConnection();
                    }
                    //graba fin de confimacion remota de transferencia de local origen a matriz
                    DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf, "F", "S");
                    FarmaUtility.aceptarTransaccion();
                }
            }
            FarmaUtility.showMessage(vDlg, "Transferencia Confirmada.", obj);
            return true;
            
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            FarmaUtility.showMessage(vDlg, "Ha ocurrido un error al confirmar.\n" +
                    e, obj);
            return false;
        }
    }
}
