package mifarma.ptoventa.fidelizacion.reference;

import com.gs.mifarma.worker.JDialogProgress;

import farmapuntos.bean.TarjetaBean;

import farmapuntos.principal.FarmaPuntos;

import farmapuntos.util.FarmaPuntosConstante;

import java.awt.Frame;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.DlgBusquedaMedicoCamp;
import mifarma.ptoventa.fidelizacion.DlgFidelizacionBuscarTarjetas;
import mifarma.ptoventa.hilos.Fidelizacion;
import mifarma.ptoventa.puntos.DlgMensajeBienvenida;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuxiliarFidelizacion {

    private static String DLG_RESUMEN_PEDIDO = "R";
    private static String DLG_LISTA_PRODUCTOS = "L";

    private static final Logger log = LoggerFactory.getLogger(AuxiliarFidelizacion.class);

    public AuxiliarFidelizacion() {
    }

    public static void funcionF12(String pCodCampanaCupon, JTextField txtObj, Frame myFrame, JLabel lblObj,
                                  JLabel lblCli, JDialog dlgObj, String pTipo, JLabel lblDniSinComision,
                                  boolean vTeclaF12, boolean isPasoTarjeta, boolean isVtaManual
                                  ) {
        log.debug("Funcion F12");
        VariablesFidelizacion.tmpCodCampanaCupon = pCodCampanaCupon;
        
        if(UtilityPuntos.isBloqueaVtaConvenio()){
            if (VariablesVentas.vEsPedidoConvenio ||
                (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 1)) {
                    FarmaUtility.showMessage(dlgObj, "No puede agregar una tarjeta a un " + "pedido por convenio.", txtObj);
                    return;
            }
        }
        mostrarBuscarTarjetaPorDNI(myFrame, lblObj, lblCli, dlgObj, pTipo, txtObj,vTeclaF12, isPasoTarjeta, isVtaManual);
        VariablesFidelizacion.tmpCodCampanaCupon = "";

        if (UtilityFidelizacion.getIndComisionnew()) {

            /// LEVANTA EL HILO DE FIDELIZACION
            /////////////////////////////////////////////////////////////////
            // INICIO dubilluz 18.05.2012
            Fidelizacion subFidelacion =
                new Fidelizacion(VariablesFidelizacion.vDniCliente, dlgObj, txtObj, lblDniSinComision);
            subFidelacion.start();
            // FIN    dubilluz 18.05.2012
            /////////////////////////////////////////////////////////////////
        }
    }
    
    
    
    private static void mostrarBuscarTarjetaPorDNI(Frame myParentFrame, JLabel lblObj, JLabel lblCli, JDialog dlgObj,
                                                   String pTipo, JTextField txtObj,boolean vTeclaF12, boolean isPasoTarjeta,
                                                   boolean isVtaManual) {

        if(isPasoTarjeta){
            DlgDatosProceso a = new DlgDatosProceso(myParentFrame," ",true);
            a.mostrar();
        }
        
        DlgFidelizacionBuscarTarjetas dlgBuscar = new DlgFidelizacionBuscarTarjetas(myParentFrame, "", true,vTeclaF12);
        dlgBuscar.setIsVtaManual(isVtaManual);
        if(vTeclaF12)
            dlgBuscar.setVisible(true);
        else{
            //porque fue x Lista de Productos o Resumen de Pedido
            dlgBuscar.setVisible(false);
            dlgBuscar.procesaFuncionalidadFid();
            dlgBuscar.procesaFuncionPuntos();
        }
        
        
        log.debug("vv DIEGO:" + FarmaVariables.vAceptar);
        log.debug("dat_1:" + VariablesFidelizacion.vDataCliente);
        log.debug(" VariablesFidelizacion.vNomCliente_1:" + VariablesFidelizacion.vNomCliente);
        log.debug(" VariablesFidelizacion.vDniCliente_1:" + VariablesFidelizacion.vDniCliente);
        if (FarmaVariables.vAceptar) {
            log.debug("en aceptar");
            log.debug("dat:" + VariablesFidelizacion.vDataCliente);
            ArrayList array = (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            log.debug("des 1");
            //JCALLO 02.10.2008
            //VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
            //seteando los datos del cliente en las variables con los datos del array
            UtilityFidelizacion.setVariablesDatos(array);
            log.debug("des 2");
            /*FarmaUtility.showMessage(this,
                                     "Cliente encontrado con DNI " + VariablesFidelizacion.vDniCliente,
                                     null);*/
            log.debug(" VariablesFidelizacion.vNomCliente:" + VariablesFidelizacion.vNomCliente);
            log.debug(" VariablesFidelizacion.vDniCliente:" + VariablesFidelizacion.vDniCliente);
            
            if(UtilityPuntos.isActivoFuncionalidad()){
                // KMONCADA 09.02.2015 CANCELA LA TRANSACCION DE VENTA CON PUNTOS
                TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
                if(tarjetaPuntos != null){
                    boolean deslizaTarjeta = tarjetaPuntos.getDeslizaTarjeta();
                    String nroTarjeta = tarjetaPuntos.getNumeroTarjeta();
                    txtObj.setText(nroTarjeta);
                    // SOLO SE INGRESA ACA CUANDO ES UNA TARJETA O REGISTRO NUEVO EN ORBIS
                    if(FarmaPuntosConstante.EXITO.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion()) &&
                       FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                        //REALIZAR CONSULTA EN EL CASO DE QUE LA TRANSACCION ANTERIOR DE REGISTRO O CONSULTA FUE EXITO
                        //ADEMAS DE QUE EL ESTADO DE LA TARJETA SE HA INACTIVA DE NUEVO REGISTRO
                        UtilityPuntos.consultarTarjetaOrbis(myParentFrame, dlgObj, txtObj, deslizaTarjeta, false);
                        //INI ASOSA - 22/04/2015 - PTOSYAYAYAYA
                        //log.info("getAfiliado().getDni(): " + VariablesFidelizacion.vDniCliente);
                        //UtilityFidelizacion.imprimirVoucherFid(VariablesFidelizacion.vDniCliente);
                        //UtilityFidelizacion.imprimirVoucherFid(tarjetaPuntos.getDni(), tarjetaPuntos.getNumeroTarjeta());
                        //FarmaUtility.showMessage(dlgObj, "Recoger voucher de verificación de datos", null);
                        //FIN ASOSA - 22/04/2015 - PTOSYAYAYAYA
                    }else{
                        if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())){
                            try{
                                String nroDocumento = DBFidelizacion.getDniClienteFidelizado(nroTarjeta);
                                if(nroDocumento.trim().length()>0){
                                    tarjetaPuntos.setDni(nroDocumento);
                                }
                            }catch(Exception ex){
                                log.info("ERROR EN FIDELIZACION CON TARJETAS ORBIS");
                                log.error(" ", ex);
                                
                            }
                        }
                    }
                    txtObj.setText("");
                }
            }
            
            if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                DlgMensajeBienvenida.showMensajeBienvenida(myParentFrame);
            }else{
                FarmaUtility.showMessage(dlgObj, "Bienvenido \n" +
                    VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                    VariablesFidelizacion.vApeMatCliente + "\n" +
                    "DNI: " + VariablesFidelizacion.vDniCliente, null);
            }
            
            //dubilluz 19.07.2011 - inicio
            if (VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length() > 0) {
                UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(),
                                                      VariablesFidelizacion.vDniCliente);
            }
            //dubilluz 19.07.2011 - fin
            //jcallo 02.10.2008
            String msjPto = "";
            if(VariablesPuntos.frmPuntos != null && VariablesPuntos.frmPuntos.getTarjetaBean() != null && 
               VariablesPuntos.frmPuntos.getTarjetaBean().getPuntosTotalAcumulados()!= null){
                msjPto = FarmaUtility.formatNumber(VariablesPuntos.frmPuntos.getTarjetaBean().getPuntosTotalAcumulados())+" - ";
            }
            boolean mostrarNombreClientePtos = true;
            if (VariablesConvenioBTLMF.vCodConvenio != null ){
                if(VariablesConvenioBTLMF.vCodConvenio.trim().length() > 1){
                    mostrarNombreClientePtos = false; 
                }
            }
            if(mostrarNombreClientePtos){
                lblCli.setText(msjPto + VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                           VariablesFidelizacion.vApeMatCliente);
            }
            
            //fin jcallo 02.10.2008
            //DAUBILLUZ -- Filtra los DNI anulados
            //25.05.2009
            VariablesFidelizacion.vDNI_Anulado = UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
            VariablesFidelizacion.vAhorroDNI_x_Periodo =
                    UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,
                                                                   VariablesFidelizacion.vNumTarjeta);
            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo =
                    UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,
                                                                   VariablesFidelizacion.vNumTarjeta);
            log.info("Variable de DNI_ANULADO: " + VariablesFidelizacion.vDNI_Anulado);
            log.info("Variable de vAhorroDNI_x_Periodo: " + VariablesFidelizacion.vAhorroDNI_x_Periodo);
            log.info("Variable de vMaximoAhorroDNIxPeriodo: " + VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
            setMensajeDNIFidelizado(lblObj, pTipo, txtObj, dlgObj);
            if (VariablesFidelizacion.vDNI_Anulado) {
                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                    UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);

                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
                log.debug("**************************************");
                //VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
                VariablesFidelizacion.vIndConexion = FarmaConstants.INDICADOR_N;
                log.debug("**************************************");
                //if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ   //VER SI HAY LINEA CON MATRIZ  JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
                log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + VariablesFidelizacion.vDniCliente);
                VariablesVentas.vArrayList_CampLimitUsadosMatriz =
                        UtilityFidelizacion.CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);

                log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" +
                          VariablesVentas.vArrayList_CampLimitUsadosMatriz);
                // } // JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            } else {
                log.info("Cliente esta invalidado para descuento...");
            }
        }else{
            // KMONCADA 09.02.2015 CANCELA LA TRANSACCION DE VENTA CON PUNTOS
            VariablesPuntos.frmPuntos.eliminarTarjetaBean();
            VariablesFidelizacion.limpiaVariables();
            lblCli.setText("");
        }
    }


    public static void setMensajeDNIFidelizado(JLabel lblObj, String pTipo, JTextField txtObj, JDialog dlgObj) {
        if (VariablesFidelizacion.vDniCliente.trim().length() > 7 &&
            VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            if (!VariablesFidelizacion.vDNI_Anulado) {
                lblObj.setText("  DNI no afecto a Descuento.");
                lblObj.setVisible(true);
            } else {
                lblObj.setText("");
                lblObj.setVisible(false);
                ////////////////////////////////////////////
                if (pTipo.trim().equalsIgnoreCase(DLG_RESUMEN_PEDIDO)) {
                    //Se evalua si ya esta en el limite de ahorro diario
                    //DUBILLUZ 28.05.2009
                    log.info("VariablesFidelizacion.vAhorroDNI_Pedido:" + VariablesFidelizacion.vAhorroDNI_Pedido);
                    log.info("VariablesFidelizacion.vAhorroDNI_x_Periodo:" +
                             VariablesFidelizacion.vAhorroDNI_x_Periodo);
                    log.info("VariablesFidelizacion.vMaximoAhorroDNIxPeriodo:" +
                             VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
                    log.info("VariablesFidelizacion.vIndComprarSinDcto:" + VariablesFidelizacion.vIndComprarSinDcto);
                    if (VariablesFidelizacion.vAhorroDNI_Pedido + VariablesFidelizacion.vAhorroDNI_x_Periodo >=
                        VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) {
                        if (!VariablesFidelizacion.vIndComprarSinDcto) {
                            FarmaUtility.showMessage(dlgObj,
                                                     "El tope de descuento por persona es de S/ " + FarmaUtility.formatNumber(VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) +
                                                     "\n" +
                                    "El cliente ya llegó a su tope.", txtObj);
                            VariablesFidelizacion.vIndComprarSinDcto = true;
                        }

                    } else {
                        VariablesFidelizacion.vIndComprarSinDcto = false;
                    }
                }
                ////////////////////////////////////////////
            }

        }
    }

    public static void ingresoMedico(Frame myParentFrame, JLabel lbMed, JLabel lblMsj, JLabel lblCli, JDialog dlgObj,
                                     String pTipo_in, JLabel lblSinComision, JTextField txtObj) {
        String pPermiteIngresoMedido = UtilityFidelizacion.getPermiteIngresoMedido();

        if (pPermiteIngresoMedido.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (VariablesVentas.vEsPedidoConvenio ||
                (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() >
                 1)) {
                FarmaUtility.showMessage(dlgObj, "No puede ingresar el Médido porque tiene" + "seleccionado convenio.",
                                         txtObj);
                return;
            }
            DlgBusquedaMedicoCamp dlgLista =
                new DlgBusquedaMedicoCamp(myParentFrame, "", true, lbMed, lblMsj, lblCli, dlgObj, pTipo_in,
                                          lblSinComision);
            dlgLista.setVisible(true);
            /*
            if(FarmaVariables.vAceptar){
                pExiste = "S";
            }
            else{
                pExiste = "NO_SELECCIONO";
            }
            */
        } else
            FarmaUtility.showMessage(dlgObj, "Por el momento no existen promociones por Receta.", txtObj);
        log.debug("****** ====VARIABLES DE MEDICO ==========================******");
        log.debug("VariablesFidelizacion.V_NUM_CMP:" + VariablesFidelizacion.V_NUM_CMP);
        log.debug("VariablesFidelizacion.V_NOMBRE:" + VariablesFidelizacion.V_NOMBRE);
        log.debug("VariablesFidelizacion.V_DESC_TIP_COLEGIO:" + VariablesFidelizacion.V_DESC_TIP_COLEGIO);
        log.debug("VariablesFidelizacion.V_TIPO_COLEGIO:" + VariablesFidelizacion.V_TIPO_COLEGIO);
        log.debug("VariablesFidelizacion.V_COD_MEDICO:" + VariablesFidelizacion.V_COD_MEDICO);
        log.debug("****** ====VARIABLES DE MEDICO ==========================******");
    }
    
    static class DlgDatosProceso extends JDialogProgress{
        
        public DlgDatosProceso() {
            super();
        }
        
        public DlgDatosProceso(Frame frame, String string, boolean b) {
            super(frame, string, b);
        }
    
        
        public void ejecutaProceso() {
            realizaProceso();
        }
        
        private void realizaProceso() {
            for(int i=0;i<5000;i++){
                
            }
        }
    
    
    }

}
