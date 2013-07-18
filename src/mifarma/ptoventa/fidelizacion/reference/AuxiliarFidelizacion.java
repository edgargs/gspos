package mifarma.ptoventa.fidelizacion.reference;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JTextField;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.fidelizacion.DlgBusquedaMedicoCamp;
import mifarma.ptoventa.fidelizacion.DlgFidelizacionBuscarTarjetas;
import mifarma.ptoventa.fidelizacion.DlgFidelizacionClientes;
import mifarma.ptoventa.hilos.Fidelizacion;
import mifarma.ptoventa.ventas.DlgMedicoCampana;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuxiliarFidelizacion {
    
    private static String DLG_RESUMEN_PEDIDO  = "R";
    private static String DLG_LISTA_PRODUCTOS = "L";
    
    private static final Logger log = 
        LoggerFactory.getLogger(AuxiliarFidelizacion.class);

    public AuxiliarFidelizacion() {
    }

    public static void funcionF12(String pCodCampanaCupon,JTextField txtObj,Frame  myFrame,JLabel lblObj,
                                  JLabel lblCli,JDialog dlgObj,String pTipo,JLabel lblDniSinComision) {
        System.out.println("Funcion F12");
        VariablesFidelizacion.tmpCodCampanaCupon = pCodCampanaCupon;
        if (VariablesVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(dlgObj, 
                                     "No puede agregar una tarjeta a un " + 
                                     "pedido por convenio.", txtObj);
            return;
        }
        mostrarBuscarTarjetaPorDNI(myFrame,lblObj,lblCli,dlgObj,pTipo,txtObj);
        VariablesFidelizacion.tmpCodCampanaCupon = "";
        
        if(UtilityFidelizacion.getIndComisionnew())
        {
        
        /// LEVANTA EL HILO DE FIDELIZACION
        /////////////////////////////////////////////////////////////////
        // INICIO   18.05.2012
        Fidelizacion subFidelacion = new Fidelizacion(VariablesFidelizacion.vDniCliente,dlgObj,txtObj,lblDniSinComision);
        subFidelacion.start();
        // FIN      18.05.2012
        /////////////////////////////////////////////////////////////////
        }
    }

    private static void mostrarBuscarTarjetaPorDNI(Frame myParentFrame,JLabel lblObj,JLabel lblCli,
                                                   JDialog dlgObj,String pTipo,JTextField txtObj
                                                   ) {

        DlgFidelizacionBuscarTarjetas dlgBuscar = 
            new DlgFidelizacionBuscarTarjetas(myParentFrame, "", true);
        dlgBuscar.setVisible(true);
        System.out.println("vv DIEGO:" + FarmaVariables.vAceptar);
        System.out.println("dat_1:" + VariablesFidelizacion.vDataCliente);
        System.out.println(" VariablesFidelizacion.vNomCliente_1:" + 
                           VariablesFidelizacion.vNomCliente);
        System.out.println(" VariablesFidelizacion.vDniCliente_1:" + 
                           VariablesFidelizacion.vDniCliente);
        if (FarmaVariables.vAceptar) {
            System.out.println("en aceptar");
            System.out.println("dat:" + VariablesFidelizacion.vDataCliente);
            ArrayList array = 
                (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            System.out.println("des 1");
            //  02.10.2008
            //VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
            //seteando los datos del cliente en las variables con los datos del array
            UtilityFidelizacion.setVariablesDatos(array);
            System.out.println("des 2");
            /*FarmaUtility.showMessage(this,
                                     "Cliente encontrado con DNI " + VariablesFidelizacion.vDniCliente,
                                     null);*/
            System.out.println(" VariablesFidelizacion.vNomCliente:" + 
                               VariablesFidelizacion.vNomCliente);
            System.out.println(" VariablesFidelizacion.vDniCliente:" + 
                               VariablesFidelizacion.vDniCliente);
            FarmaUtility.showMessage(dlgObj, "Bienvenido \n" +
                    VariablesFidelizacion.vNomCliente + " " + 
                    VariablesFidelizacion.vApePatCliente + " " + 
                    VariablesFidelizacion.vApeMatCliente + "\n" +
                    "DNI: " + VariablesFidelizacion.vDniCliente, null);
            //  19.07.2011 - inicio
            if (VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length() > 
                0) {
                UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(), 
                                                      VariablesFidelizacion.vDniCliente);
            }
            //  19.07.2011 - fin 
            //  02.10.2008
            lblCli.setText(VariablesFidelizacion.vNomCliente + " " + 
                               VariablesFidelizacion.vApePatCliente + " " + 
                               VariablesFidelizacion.vApeMatCliente);
            //fin   02.10.2008
            //DAUBILLUZ -- Filtra los DNI anulados
            //25.05.2009
            VariablesFidelizacion.vDNI_Anulado = 
                    UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
            VariablesFidelizacion.vAhorroDNI_x_Periodo = 
                    UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo = 
                    UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
            log.info("Variable de DNI_ANULADO: " + 
                     VariablesFidelizacion.vDNI_Anulado);
            log.info("Variable de vAhorroDNI_x_Periodo: " + 
                     VariablesFidelizacion.vAhorroDNI_x_Periodo);
            log.info("Variable de vMaximoAhorroDNIxPeriodo: " + 
                     VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
            setMensajeDNIFidelizado(lblObj,pTipo,txtObj,dlgObj);
            if (VariablesFidelizacion.vDNI_Anulado) {
                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                    UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);

                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
                log.debug("**************************************");
                //VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
                VariablesFidelizacion.vIndConexion = 
                        FarmaConstants.INDICADOR_N;
                log.debug("**************************************");
                //if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ   //VER SI HAY LINEA CON MATRIZ    27092009. se comentó pues no es necesario que valide ya que se consultará al local
                log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + 
                          VariablesFidelizacion.vDniCliente);
                VariablesVentas.vArrayList_CampLimitUsadosMatriz = 
                       UtilityFidelizacion.CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);

                log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" + 
                          VariablesVentas.vArrayList_CampLimitUsadosMatriz);
                // } //   27092009. se comentó pues no es necesario que valide ya que se consultará al local
                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            } else {
                log.info("Cliente esta invalidado para descuento...");
            }
        }
    }


    public static void setMensajeDNIFidelizado(JLabel lblObj,String pTipo,JTextField txtObj,JDialog dlgObj) {
        if (VariablesFidelizacion.vDniCliente.trim().length() > 7 && 
            VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            if (!VariablesFidelizacion.vDNI_Anulado) {
                lblObj.setText("  DNI no afecto a Descuento.");
                lblObj.setVisible(true);
            } else {
                lblObj.setText("");
                lblObj.setVisible(false);
                ////////////////////////////////////////////
                if(pTipo.trim().equalsIgnoreCase(DLG_RESUMEN_PEDIDO))
                {
                    //Se evalua si ya esta en el limite de ahorro diario
                    //  28.05.2009
                    System.err.println("VariablesFidelizacion.vAhorroDNI_Pedido:" + 
                                       VariablesFidelizacion.vAhorroDNI_Pedido);
                    System.err.println("VariablesFidelizacion.vAhorroDNI_x_Periodo:" + 
                                       VariablesFidelizacion.vAhorroDNI_x_Periodo);
                    System.err.println("VariablesFidelizacion.vMaximoAhorroDNIxPeriodo:" + 
                                       VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
                    System.err.println("VariablesFidelizacion.vIndComprarSinDcto:" + 
                                       VariablesFidelizacion.vIndComprarSinDcto);
                    if (VariablesFidelizacion.vAhorroDNI_Pedido + 
                        VariablesFidelizacion.vAhorroDNI_x_Periodo >= 
                        VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) {
                        if (!VariablesFidelizacion.vIndComprarSinDcto) {
                            FarmaUtility.showMessage(dlgObj, 
                                                     "El tope de descuento por persona es de S/ " + 
                                                     FarmaUtility.formatNumber(VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) + 
                                                     "\n" +
                                    "El cliente ya llegó a su tope.", 
                                    txtObj);
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
    
    public static void ingresoMedico(Frame myParentFrame,JLabel lbMed,JLabel lblMsj,JLabel lblCli,
                                     JDialog dlgObj,String pTipo_in,JLabel lblSinComision,JTextField txtObj){
        String pPermiteIngresoMedido = 
            UtilityFidelizacion.getPermiteIngresoMedido();

        if (pPermiteIngresoMedido.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (VariablesVentas.vEsPedidoConvenio) {
                FarmaUtility.showMessage(dlgObj, 
                                         "No puede ingresar el Médido porque tiene" + 
                                         "seleccionado convenio.", 
                                         txtObj);
                return;
            }
            DlgBusquedaMedicoCamp dlgLista = new DlgBusquedaMedicoCamp(myParentFrame,"",true,lbMed,
                                                                       lblMsj,lblCli,dlgObj,pTipo_in,lblSinComision);
            dlgLista.setVisible(true);
            /*
            if(FarmaVariables.vAceptar){
                pExiste = "S";
            }
            else{
                pExiste = "NO_SELECCIONO";
            }
            */
        }
        else
            FarmaUtility.showMessage(dlgObj,"Por el momento no existen promociones por Receta.",txtObj);
        System.out.println("****** ====VARIABLES DE MEDICO ==========================******");
        System.out.println("VariablesFidelizacion.V_NUM_CMP:"+VariablesFidelizacion.V_NUM_CMP);
        System.out.println("VariablesFidelizacion.V_NOMBRE:"+VariablesFidelizacion.V_NOMBRE);
        System.out.println("VariablesFidelizacion.V_DESC_TIP_COLEGIO:"+VariablesFidelizacion.V_DESC_TIP_COLEGIO);
        System.out.println("VariablesFidelizacion.V_TIPO_COLEGIO:"+VariablesFidelizacion.V_TIPO_COLEGIO);
        System.out.println("VariablesFidelizacion.V_COD_MEDICO:"+VariablesFidelizacion.V_COD_MEDICO);        
        System.out.println("****** ====VARIABLES DE MEDICO ==========================******");        
    }

}
