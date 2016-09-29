package mifarma.ptoventa.fidelizacion.reference;


import farmapuntos.bean.TarjetaBean;

import farmapuntos.util.FarmaPuntosConstante;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JDialog;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityLectorTarjeta;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.DlgFidelizacionClientes;
import mifarma.ptoventa.fidelizacion.modelo.BeanTipoDocIdentidad;
import mifarma.ptoventa.puntos.reference.ConstantsPuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.DlgMedicoCampana;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityFidelizacion {

    private static final Logger log = LoggerFactory.getLogger(UtilityFidelizacion.class);

    public UtilityFidelizacion() {
    }


    public static void validaLecturaTarjeta(String vCodTarjeta, Frame pParent) {
        validaLecturaTarjeta(null, vCodTarjeta, pParent);
    }

    /**
     * Valida la tarjeta en el momento mismo de la lectura de esta
     * @author DVELIZ
     * @since 30.09.08
     * @param vCodTarjeta
     * @param pParent
     */
    public static void validaLecturaTarjeta(JDialog pJDialog, String vCodTarjeta, Frame pParent) {
        String indExisteLocal = "";

        try {
            //Verifica si existe cliente asociado a la tarjeta en el local
            //Si es 0: no existe
            //Si es 1: existe pero le faltan datos
            //Si es 2: existe con datos completos
            indExisteLocal = DBFidelizacion.validaClienteLocal(vCodTarjeta);

            VariablesFidelizacion.vIndConexion = "N";

            if (!indExisteLocal.equals("0")) {
                cargoProcesoSegunIndExiste(indExisteLocal, vCodTarjeta, pParent, pJDialog);
                if (FarmaVariables.vAceptar) {
                    VariablesFidelizacion.vIndAgregoDNI = indExisteLocal.trim();
                }
                return;
            }

            muestraInterfazDatosCliente(vCodTarjeta.trim(), pParent);

            VariablesFidelizacion.vIndAgregoDNI = indExisteLocal.trim();


        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (VariablesFidelizacion.vIndConexion.trim().length() > 0)
                FarmaConnectionRemoto.closeConnection();
            VariablesFidelizacion.vIndConexion = "";
        }
    }

    /**
     * Metodo de validacion general de la tarjeta de fidelizacion
     * @author DVELIZ
     * @since 30.09.08
     * @param pCadena
     * @return
     */
    public static boolean EsTarjetaFidelizacion(String pCadena) {
        boolean retorno = false;
        int pTamano = pCadena.length();
        if (pTamano > 1) {
            //se valida el codigo de barra a nivel de local
            if (isNumerico(pCadena) && pTamano == 13 && validaCodBarraLocal(pCadena) && validaTarjetaLocal(pCadena)) {
                retorno = true;
            }
        }

        return retorno;
    }


    /**
     * Valida que si el numero leido es numerico
     * @author DVELIZ
     * @since 30.09.08
     * @param pcadena
     * @return
     */
    public static boolean isNumerico(String pcadena) {
        char vCaracter;
        if (pcadena.length() == 0)
            return false;
        for (int i = 0; i < pcadena.length(); i++) {
            vCaracter = pcadena.charAt(i);
            if (Character.isLetter(vCaracter))
                return false;
        }
        return true;
    }

    public static boolean isNumericoBest(String pcadena) {
        int numero = 0;
        boolean retorno = true;
        if (pcadena.length() == 0)
            return false;
        try {
            numero = Integer.parseInt(pcadena);
        } catch (Exception e) {
            retorno = false;
        }

        return retorno;
    }

    /**
     * Valida si el numero leido es codigo de barra
     * @author DVELIZ
     * @since 30.09.08
     * @param cadena
     * @return
     */
    private static boolean validaCodBarraLocal(String cadena) {

        boolean retorno = true;
        String valida = "";

        try {
            valida = DBFidelizacion.verificaCodBarraLocal(cadena);
            if (valida.equalsIgnoreCase("S")) {
                retorno = false;
                log.debug("El codigo de barra " + cadena + " existe en local");
            } else {
                log.debug("El codigo de barra " + cadena + " No existe en local");
            }
        } catch (SQLException e) {
            log.error("", e);
        }
        return retorno;
    }

    /**
     * Valida tarjeta en local
     * @author DVELIZ
     * @since 30.09.08
     * @param cadena
     * @return
     */
    public static boolean validaTarjetaLocal(String cadena) {
        boolean retorno = false;
        String valida = "";

        try {
            valida = DBFidelizacion.validaTarjetaLocal(cadena);
            if (valida.equals("1")) {
                retorno = true;
                log.debug("La tarjeta " + cadena + " existe en local");
            } else {
                log.debug("La tarjeta " + cadena + " No existe en local");
            }
        } catch (SQLException e) {
            log.error("", e);
        }

        return retorno;
    }

    /**
     * Valida la carga de interfaces segun el indiccador de existencia
     * @author DVELIZ
     * @since 30.09.08
     * @param vIndExiste
     * @param pTarjeta
     * @param pParent
     */
    public static void cargoProcesoSegunIndExiste(String vIndExiste, String pTarjeta, Frame pParent,
                                                  JDialog pJDialog) {
        log.debug("indicador de existe cliente :" + vIndExiste);
        if (vIndExiste.equals("2")) {
            //muestra la informacion en DlgListaProductos
            log.debug("PASO DE FRENTE A MOSTRAR DATOS EN LISTADO");
            VariablesFidelizacion.vDataCliente = new ArrayList();
            try {
                DBFidelizacion.obtieneInfoCliente(pTarjeta, VariablesFidelizacion.vDataCliente);
                VariablesFidelizacion.vNumTarjeta = pTarjeta.trim();
                log.debug("", VariablesFidelizacion.vDataCliente);

                // KMONCADA 2015.02.16 EN EL CASO DE REGISTRO DE PROGRAMA DE PUNTOS.
                if (UtilityPuntos.isActivoFuncionalidad()) {
                    if (VariablesPuntos.frmPuntos != null && VariablesPuntos.frmPuntos.getTarjetaBean() != null) {
                        TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
                        if (FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta()) ||
                            FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaPuntos.getEstadoOperacion())) {

                            String nroDocumento =
                                FarmaUtility.getValueFieldArrayList(VariablesFidelizacion.vDataCliente, 0, 0);
                            boolean rsptaValida =
                                UtilityPuntos.impresionVoucherAfiliacion(pParent, pJDialog, VariablesFidelizacion.vNumTarjeta,
                                                                         nroDocumento);
                            if (rsptaValida) {
                                boolean rsptaAfialiacion =
                                    UtilityPuntos.registroAfiliadoAutomatico(nroDocumento, tarjetaPuntos.getNumeroTarjeta(),
                                                                             VariablesFidelizacion.vNumTarjeta);
                                String indicadorLinea = ConstantsPuntos.TRSX_ORBIS_PENDIENTE;
                                if (rsptaAfialiacion) {
                                    indicadorLinea = ConstantsPuntos.TRSX_ORBIS_ENVIADA;
                                } else { //KMONCADA 15.05.2015
                                    indicadorLinea = ConstantsPuntos.TRSX_ORBIS_PENDIENTE;
                                }
                                UtilityPuntos.actualizarEstadoEnvioAfiliacion(VariablesFidelizacion.vNumTarjeta,
                                                                              nroDocumento, indicadorLinea);
                            } else {
                                FarmaVariables.vAceptar = false;
                                return;
                            }
                            /*
                            int cantidad = UtilityPuntos.cantidadTieneTarjetasAdicionales(pParent, pJDialog, FarmaUtility.getValueFieldArrayList(VariablesFidelizacion.vDataCliente, 0, 0), false, false);
                            if(cantidad == 0){

                            }else{
                                UtilityPuntos.registroAfiliadoAutomatico(FarmaUtility.getValueFieldArrayList(VariablesFidelizacion.vDataCliente, 0, 0), tarjetaPuntos.getNumeroTarjeta(), VariablesFidelizacion.vNumTarjeta);
                            }
                            */
                        } else {
                            //KMONCADA 20.07.2015 EN CASO DE QUE LA ESTE ACTIVA SE ACTUALIZA AUTOMATICAMENTE
                            if (UtilityPuntos.isTarjetaValida(tarjetaPuntos.getNumeroTarjeta()) &&
                                (FarmaPuntosConstante.EstadoTarjeta.ACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta()) ||
                                 FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta()))) {
                                UtilityPuntos.actualizarEstadoEnvioAfiliacion(tarjetaPuntos.getNumeroTarjeta(),
                                                                              tarjetaPuntos.getDni(),
                                                                              ConstantsPuntos.TRSX_ORBIS_ENVIADA);
                            }
                        }

                    }

                }
            } catch (Exception e) {
                log.error("", e);
            }
            FarmaVariables.vAceptar = true;
        } else if (vIndExiste.equals("1")) {
            //muestra la interfaz DlgFidelizacionClientes
            log.debug("FALTA DATOS, INGRESALOS");
            muestraInterfazDatosCliente(pTarjeta.trim(), pParent);


        }
    }

    public static void cargoProcesoSegunIndExisteMatriz(String vIndExiste, String pTarjeta, Frame pParent) {
        if (vIndExiste.equals("2")) {
            //muestra la informacion en DlgListaProductos
            log.debug("PASO DE FRENTE A MOSTRAR DATOS EN LISTADO");
            VariablesFidelizacion.vDataCliente = new ArrayList();
            try {
                DBFidelizacion.obtieneInfoClienteMatriz(pTarjeta, VariablesFidelizacion.vDataCliente,
                                                        VariablesFidelizacion.vIndConexion);

                VariablesFidelizacion.vNumTarjeta = pTarjeta.trim();
                log.debug("cargando datos de matriz a local" + VariablesFidelizacion.vDataCliente);

                ArrayList array = (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
                VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
                VariablesFidelizacion.vIndEstado = "A";
                //                DBFidelizacion.insertarClienteFidelizacion();
            } catch (SQLException e) {
                log.error("", e);
            }
        } else if (vIndExiste.equals("1")) {
            //muestra la interfaz DlgFidelizacionClientes
            log.debug("FALTA DATOS, INGRESALOS");
            muestraInterfazDatosCliente(pTarjeta.trim(), pParent);

        }
    }

    /**
     * Metodo que muestra la interfaz de ingreso de datos de cliente
     * @author DVELIZ
     * @since 30.09.08
     * @param pCadena
     * @param pParent
     */
    public static void muestraInterfazDatosCliente(String pCadena, Frame pParent) {

        try {
            DlgFidelizacionClientes dlgFormulario = new DlgFidelizacionClientes(pParent, "", true, pCadena);
            dlgFormulario.setVisible(true);
            //VariablesFidelizacion.vNumTarjeta = cadena.trim();
            if (FarmaVariables.vAceptar) {
                VariablesFidelizacion.vNumTarjeta = pCadena.trim();
                try {
                    DBFidelizacion.obtieneInfoCliente(pCadena, VariablesFidelizacion.vDataCliente);
                    VariablesFidelizacion.vNumTarjeta = pCadena.trim();
                } catch (SQLException e) {
                    log.error("", e);
                }

                VariablesFidelizacion.vNumTarjeta = pCadena.trim();
                VariablesFidelizacion.vDniCliente =
                        FarmaUtility.getValueFieldArrayList(VariablesFidelizacion.vDataCliente, 0, 0);


            }
            log.info("VariablesFidelizacion.vDataCliente >>>:" + VariablesFidelizacion.vDataCliente);

        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * Procedimiento encargado de obtener el listado de campañas automaticas
     * @author JCALLO
     * @since  03.03.2009
     * @param  List listaCampaTarj
     * @param pNumTarjeta
     */
    public static List obtenerCampaniasxFidelizacion(String pNumTarjeta) {
        List listaCampaTarj = new ArrayList();
        try {
            listaCampaTarj = DBFidelizacion.obtenerCampaniasXFidelizacion(pNumTarjeta);
        } catch (SQLException e) {
            listaCampaTarj = new ArrayList();
            log.error("", e);
        }
        return listaCampaTarj;
    }

    public static boolean isNumero(String cadena) {
        double num;
        boolean res;
        try {
            num = Double.parseDouble(cadena.trim());
            res = true;
        } catch (NumberFormatException e) {
            res = false;
        }

        return res;
    }

    /**
     * METODO ENCARGADO DE AGREGAR LAS CAMPAÑAS AUTOMATICAS A LAS CAMPAÑAS CUPON NORMALES
     * en resumen agregar a la lista VariablesVentas.vArrayList_Cupones las campañas automaticas
     * por fidelizacion
     * @param nroTarjetaCliente
     */
    public static void operaCampañasFidelizacion(String nroTarjetaCliente) {
        //KMONCADA 01.07.2015
        if (UtilityPuntos.isActivoFuncionalidad()) {
            if (VariablesVentas.vEsPedidoConvenio ||
                (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() >
                 1)) {

                VariablesVentas.vArrayList_Cupones = new ArrayList();
                return;
            }
        }
        //ERIOS 21.01.2014 Se muestra mensaje de error
        try {
            VariablesFidelizacion.vListCampañasFidelizacion = new ArrayList();
            //Obteniendo la lista de campañas de fidelizacion
            //el resultado es un LIST con una coleccion de HASHMAP
            VariablesFidelizacion.vListCampañasFidelizacion = obtenerCampaniasxFidelizacion(nroTarjetaCliente);

            //BORRA las AUTOMATICAS que ya EXISTAN
            ArrayList cuponesValidos = new ArrayList();
            Map mapAux = new HashMap(); //mapa de la campania del listado de cupones
            String campAux = "";
            String codCampCupon = "", nroCupon = "";
            log.debug("Limpiando VAriables");
            //ESTO ES PARA ELIMINAR LAS CAMPAÑAS AUTOMATIACAS YA CARGADAS
            //PORQUE AHORA SE PUEDE HACER F12 VARIAS VECES.
            for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size(); i++) {
                log.debug("i:" + i);
                mapAux = (HashMap)VariablesVentas.vArrayList_Cupones.get(i);
                campAux = ((String)mapAux.get("COD_CAMP_CUPON")).trim() + "";
                if (isNumero(campAux)) {
                    log.debug("campAux:" + campAux);
                    //cuponesValidos.add(mapAux);
                    log.debug("ANTES");
                    nroCupon = ((String)mapAux.get("COD_CUPON")).trim() + "";
                    log.debug("despues");
                    // KMONCADA 22.02.2016 NUEVO FORMATO DE CUPON
                    codCampCupon = UtilityVentas.obtenerCampaniaDeCupon(nroCupon);
                    //codCampCupon = nroCupon.substring(0, 5);
                    try {
                        mapAux = new HashMap();
                        mapAux = DBVentas.getDatosCupon(codCampCupon, nroCupon, VariablesFidelizacion.vDniCliente);
                        mapAux.put("COD_CUPON", nroCupon);
                    } catch (SQLException e) {
                        //cuponesValidos.add(mapAux);
                    }
                    log.debug("agrega " + i);
                    cuponesValidos.add(mapAux);
                } else
                    log.debug("no es cupon");
            }

            log.debug("XXX cuponesValidos:" + cuponesValidos);

            VariablesVentas.vArrayList_Cupones = new ArrayList();
            VariablesVentas.vArrayList_Cupones = (ArrayList)cuponesValidos.clone();

            log.debug("Cuantas Campañas Fideizadas Auto:" + VariablesFidelizacion.vListCampañasFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones:" + VariablesVentas.vArrayList_Cupones);
            log.debug("Cuantas Campañas Ya Cargadas:" + VariablesVentas.vArrayList_Cupones.size());
            log.debug("agregando las campañas automaticas al listado de cupones");
            if (VariablesFidelizacion.vListCampañasFidelizacion.size() > 0) {

                Map mapCampFid = new HashMap(); //mapa de la campania de fidelizacion
                Map mapCampCup = new HashMap(); //mapa de la campania del listado de cupones

                String codCampFid = ""; //codigo de campania de fidelizacion
                String CodCampCup = ""; //codigo de campania de cupon de la lista general

                boolean existe = false;
                log.debug("VariablesFidelizacion.vListCampañasFidelizacion.size():" +
                          VariablesFidelizacion.vListCampañasFidelizacion.size());
                for (int i = 0; i < VariablesFidelizacion.vListCampañasFidelizacion.size(); i++) {

                    mapCampFid = (HashMap)VariablesFidelizacion.vListCampañasFidelizacion.get(i);
                    codCampFid = ((String)mapCampFid.get("COD_CAMP_CUPON")).trim();
                    //log.debug("mapCampFid:"+mapCampFid);
                    for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                        mapCampCup = (HashMap)VariablesVentas.vArrayList_Cupones.get(j);
                        CodCampCup = ((String)mapCampCup.get("COD_CAMP_CUPON")).trim();
                        if (codCampFid.equalsIgnoreCase(CodCampCup)) { //ver si ya existe
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        //agregando campania fidelizacion a la lista de cupones
                        VariablesVentas.vArrayList_Cupones.add(mapCampFid);
                        //log.debug("despues de agregar VariablesVentas.vArrayList_Cupones:"+VariablesVentas.vArrayList_Cupones);
                    }
                }
                log.debug("VariablesVentas.vArrayList_Cupones.size():" + VariablesVentas.vArrayList_Cupones.size());
            }
        } catch (Exception e) {
            log.error("", e);
            //Envia correo
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                          VariablesPtoVenta.vDestEmailErrorCobro, "Error de Fidelizacion",
                                          "Error de Fidelizacion",
                                          "Error al operar campañas de fidelizacion" + "<br>" + "IP PC: " +
                                          FarmaVariables.vIpPc + "<br>" + "Error: " + e, "");
            FarmaUtility.showMessage((new JDialog()),
                                     "Ha ocurrido un error al recuperar las Campañas de Fidelización.\n" +
                    "Comuníquese con Mesa de Ayuda.\n" +
                    e.toString(), null);
        }
    }

    public static void setVariables() {
        VariablesFidelizacion.vDniCliente = "";
        VariablesFidelizacion.vNumTarjeta = "";
        VariablesFidelizacion.vApePatCliente = "";
        VariablesFidelizacion.vApeMatCliente = "";
        VariablesFidelizacion.vNomCliente = "";
        VariablesFidelizacion.vFecNacimiento = "";
        VariablesFidelizacion.vSexo = "";
        VariablesFidelizacion.vDireccion = "";
        VariablesFidelizacion.vTelefono = "";
        VariablesFidelizacion.vEmail = "";
        VariablesFidelizacion.vIndExisteCliente = false;
    }


    /**
     * metodo encargado de setear los datos
     * del arreglo en las variables del cliente
     * @autor jcallo
     * @since 02.10.2008
     * */
    public static void setVariablesDatos(ArrayList lDatosCliente) {
        int tam = lDatosCliente.size();
        int ind = 0;
        if (ind < tam) {
            VariablesFidelizacion.vDniCliente = lDatosCliente.get(ind).toString();
            ind++;
        } else {
            VariablesFidelizacion.vDniCliente = "";
            ind++;
        }
        /*if(ind<tam){
            VariablesFidelizacion.vNumTarjeta = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vNumTarjeta = "";
            ind++;
        }*/
        if (ind < tam) {
            VariablesFidelizacion.vApePatCliente = lDatosCliente.get(ind).toString();
            ind++;
        } else {
            VariablesFidelizacion.vApePatCliente = "";
            ind++;
        }
        if (ind < tam) {
            VariablesFidelizacion.vApeMatCliente = lDatosCliente.get(ind).toString();
            ind++;
        } else {
            VariablesFidelizacion.vApeMatCliente = "";
            ind++;
        }
        if (ind < tam) {
            VariablesFidelizacion.vNomCliente = lDatosCliente.get(ind).toString();
            ind++;
        } else {
            VariablesFidelizacion.vNomCliente = "";
            ind++;
        }
        if (ind < tam) {
            VariablesFidelizacion.vFecNacimiento = lDatosCliente.get(ind).toString();
            ind++;
        } else {
            VariablesFidelizacion.vFecNacimiento = "";
            ind++;
        }
        if (ind < tam) {
            VariablesFidelizacion.vSexo = lDatosCliente.get(ind).toString();
            ind++;
        } else {
            VariablesFidelizacion.vSexo = "";
            ind++;
        }
        if (ind < tam) {
            VariablesFidelizacion.vDireccion = lDatosCliente.get(ind).toString();
            ind++;
        } else {
            VariablesFidelizacion.vDireccion = "";
            ind++;
        }
        if (ind < tam) {
            VariablesFidelizacion.vTelefono = lDatosCliente.get(ind).toString();
            ind++;
        } else {
            VariablesFidelizacion.vTelefono = "";
            ind++;
        }

    }

    /**
     * Metodo encargado de validar formato de correo.
     * @author jcallo
     * @since 02.10.2008
     */
    public static boolean validarEmail(String email) {

        boolean b = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);

        return b;
    }

    /**
     * Metodo encargado de validacion del documento de identificacion ( DNI, CARNE DE EXTRANJERIA)     *
     * @author jcallo
     * @since 06.10.2008
     */
    public static boolean validarDocIndentificacion(String docIden) {
         log.info("doc a validar :" + docIden);
         log.info("validar con :" + VariablesFidelizacion.vDocValidos);
         boolean flag = false;
         String paramDocVal = VariablesFidelizacion.vDocValidos;
         if (paramDocVal != null && paramDocVal.length() != 0) {
             String valores[] = paramDocVal.split(",");
             log.info("valores :  " + valores.toString());
             for (int i = 0; i < valores.length; i++) {
                 //log.debug("izq : "+Integer.parseInt( valores[i].trim() )+" doc: "+docIden+", docIden length: "+docIden.length());
                 if (Integer.parseInt(valores[i].trim()) == docIden.length()) {
                     log.info("ok");
                     flag = true;
                     break;
                 }
             }
         }
         return flag;
     }
    
    /**
     * NUEVO METODO DE VALIDACION DE DOCUMENTO DE IDENTIFICACION
     * @since 28.08.2015
     * @param docIden
     * @param codTipoDocumento
     * @return
     */
    public static boolean validarDocIndentificacion(String docIden, String codTipoDocumento) {
        log.info("doc a validar :" + docIden);
        log.info("tipo de documento :" + codTipoDocumento);
        FacadeFidelizacion facade = new FacadeFidelizacion();
        List lstTipDocumento = facade.obtenerListadoTipoDocumento();
        boolean flag = false;
        for(int i = 0; i < lstTipDocumento.size(); i++){
            BeanTipoDocIdentidad tipoDocumento = (BeanTipoDocIdentidad)lstTipDocumento.get(i);
            if(tipoDocumento.getCodigo().equalsIgnoreCase(codTipoDocumento)){
                List lstMascara = tipoDocumento.getLstMascaras();
                log.info("mascaras del tipo documento :" + lstMascara);
                for(int k = 0; k < lstMascara.size(); k++){
                    String mascara = (String)lstMascara.get(k);
                    if(docIden.matches(mascara)){
                        flag = true;
                        k = lstMascara.size() + 2;
                    }
                }
                if(flag){
                    i = lstTipDocumento.size() + 2;
                }
            }
        }
        return flag;
    }
    
    public static String determinarTipoDocIndentidad(String nroDocIdentidad) {
        String codigo = determinarTipoDocIndentidad(nroDocIdentidad, ConstantsFidelizacion.COD_TIPO_DOC_DNI, ConstantsFidelizacion.COD_TIPO_DOC_CARNET_EXTRANJERIA, ConstantsFidelizacion.COD_TIPO_DOC_DNI_EXTRANJERO);
        return codigo;
    }
    
    public static String determinarTipoDocIndentidad(String nroDocIdentidad, String... codTipoPermitidos) {
        log.info("[FIDELIZACION] NRO DE DOCUMENTO A DETERMINAR SU TIPO :" + nroDocIdentidad);
        FacadeFidelizacion facade = new FacadeFidelizacion();
        List lstTipDocumento = facade.obtenerListadoTipoDocumento();
        String codTipoDocumento = null;
        boolean flag = false;
        for(int i = 0; i < lstTipDocumento.size(); i++){
            BeanTipoDocIdentidad tipoDocumento = (BeanTipoDocIdentidad)lstTipDocumento.get(i);
            boolean evalua = false;
            for(int j=0; j<codTipoPermitidos.length; j++){
                if(tipoDocumento.getCodigo().equalsIgnoreCase(codTipoPermitidos[j])){
                    evalua = true;
                    break;
                }
            }
            if(evalua){
                List lstMascara = tipoDocumento.getLstMascaras();
                log.info("mascaras del tipo documento :" + lstMascara);
                for(int k = 0; k < lstMascara.size(); k++){
                    String mascara = (String)lstMascara.get(k);
                    if(nroDocIdentidad.matches(mascara)){
                        flag = true;
                        k = lstMascara.size() + 2;
                    }
                }
                if(flag){
                    i = lstTipDocumento.size() + 2;
                    codTipoDocumento = tipoDocumento.getCodigo();
                }
            }
        }
        log.info("[FIDELIZACION] TIPO DE DOCUMENTO DETERMINADO " + codTipoDocumento);
        return codTipoDocumento;
    }
    
    public static boolean validarDocIdentidad(String codIde) {
        return validarDocIndentificacion(codIde);
    }
    
    public static boolean validarDocIdentidad(String codIde, String codTipoDocumento) {
        //LLEIVA 01-Abr-2014 Se utilizara la funcion de FV 1.0
        return validarDocIndentificacion(codIde, codTipoDocumento);
    }

    /**
     * Metodo encargado de validar que el parametro, es numero de 13 digitos
     * ademas que no es un codigo de barra
     * ademas que el numero de tarjeta este disponible
     * @author JCALLO
     * @since 30.09.08
     * @param pCadena
     * @return
     */
    public static boolean esTarjetaFidelizacionDisponible(String pCadena) {
        boolean retorno = false;
        int pTamano = pCadena.length();
        if (pTamano > 1) {
            //se valida el codigo de barra a nivel de local
            if (isNumerico(pCadena) && //es numerico
                pTamano == 13 && //es de 13 digitos
                validaCodBarraLocal(pCadena) && //no es codigo de barra
                tarjetaDisponibleLocal(pCadena)) //y la tarjeta esta disponible
            {
                retorno = true;
            }
        }

        return retorno;
    }

    /**
     * verifica si la tarjeta es valida y esta dispoble para ser usado
     * @author JCALLO
     * @since 18.12.08
     * @param cadena
     * @return
     */
    private static boolean tarjetaDisponibleLocal(String cadena) {
        boolean retorno = false;
        String valida = "";

        try {
            valida = DBFidelizacion.isTarjetaDisponibleLocal(cadena).trim();
            if (valida.equals("S")) {
                log.debug("La tarjeta " + cadena + " esta disponible para ser usado");
                retorno = true;
            } else {
                log.debug("La tarjeta " + cadena + " ya esta asigando a un cliente en local");
            }
        } catch (SQLException e) {
            log.error("", e);
        }

        return retorno;
    }

    /**
     * Genera el ean13 de la nueva tarjeta del local.
     * @param vPrefijo
     * @param vCodLocal
     * @param pNroTarjetaLealtad nro de tarjeta del programa de puntos (KMONCADA 11.02.2015)
     * @return
     */
    public static String generaNuevaTarjeta(String vPrefijo, String vCodLocal, String pNroTarjetaLealtad) {

        String vConcatenado = vPrefijo.trim() + vCodLocal.trim();
        String vNuevaTarjeta = "";
        try {
            vNuevaTarjeta = DBFidelizacion.generaNuevaTarjetaFidelizacion(vConcatenado, pNroTarjetaLealtad);
            //DUBILLUZ 04.05.2015
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            //DUBILLUZ 04.05.2015
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            vNuevaTarjeta = "-1";
        }

        return vNuevaTarjeta;

    }

    /**
     * Trae la tarjeta de matriz a Local
     * @author DUBILLUZ
     * @since  17.02.2009
     */
    public static void creaTarjetaLocal(String vCodTarjeta, String vIndConexion) {

        ArrayList vDatos = new ArrayList();
        String pRespuesta = "S";
        try {
            pRespuesta = DBFidelizacion.getExisteTarjeta(vCodTarjeta.trim()).trim();

            if (pRespuesta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                DBFidelizacion.getDatosTarjeta(vDatos, vCodTarjeta);

                if (vDatos.size() > 0) {

                    DBFidelizacion.insertTarjeta(vCodTarjeta, FarmaUtility.getValueFieldArrayList(vDatos, 0, 1), //DNI
                            FarmaUtility.getValueFieldArrayList(vDatos, 0, 2)); //local
                    FarmaUtility.aceptarTransaccion();
                }
                log.debug("Se inserto la tarjeta en Local");
            }


        } catch (SQLException e) {
            FarmaUtility.aceptarTransaccion();
            log.error("", e);
        }

    }

    /**
     * Obtiene el DNI valido o NO
     * @author DUBILLUZ
     * @since  28.05.2009
     * @param pDni
     * @return
     */
    public static boolean isDniValido(String pDni) {
        boolean pResultado = true;
        String pStrRes;
        try {
            pStrRes = DBFidelizacion.isDNI_Anulado(pDni.trim()).trim();

            if (pStrRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                pResultado = false;
                log.info("Cliente no esta permitido para descuento");
            } else {
                pResultado = true;
                log.info("Cliente SI aplica DESCUENTO");
            }

        } catch (Exception e) {
            log.error("Error al obtener Cliente SI aplica DESCUENTO", e);
            pResultado = true;
        }
        return pResultado;
    }

    public static boolean isRUCValido(String pRUC) {
        boolean pResultado = true;
        String pStrRes;
        try {
            pStrRes = DBFidelizacion.isRUC_Anulado(pRUC.trim()).trim();

            if (pStrRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                pResultado = false;
                log.info("RUC no esta permitido para descuento");
            } else {
                pResultado = true;
                log.info("RUC SI aplica DESCUENTO");
            }

        } catch (Exception e) {
            log.error("Error al obtener RUC SI aplica DESCUENTO", e);
            pResultado = true;
        }
        return pResultado;
    }

    /**
     * Retorna Ahorro DNI x Periodo
     * @author DUBILLUZ
     * @since  28.05.2009
     * @param pDni
     * @return
     */
    public static double getAhorroDNIxPeriodoActual(String pDni, String ptarj) {
        double pResultado = 0;
        
        try {
            pResultado = DBFidelizacion.getAhorroDNIxPeriodo(pDni.trim(), ptarj.trim());
        } catch (Exception e) {
            log.error("Error al obtener ahorro actual al periodo x DNI", e);
            pResultado = 0;
        }
        return pResultado;
    }

    /**
     * Retorna el maximo ahorro DNI x Periodo
     * @author DUBILLUZ
     * @since  28.05.2009
     * @param pDni
     * @return
     */
    // DUBILLUZ 01.06.2012
    public static double getMaximoAhorroDnixPeriodo(String pDni, String pTarjeta) {
        double pResultado = 0;
        
        try {
            // getMaximoAhorroDNIxPeriodo
            pResultado = DBFidelizacion.getMaximoAhorroDNI_NEW(pDni, pTarjeta);
        } catch (Exception e) {
            log.error("Error al obtener Maximo Ahorro x DNI", e);
            pResultado = 0;
        }
        return pResultado;
    }

    public static double getMaxUnidDctoProdCampana(String pCodCampana, String pCodProd, String pDNI) {
        double pResultado = 0;
        
        try {
            pResultado = DBFidelizacion.getMaxUnidDctoProdCampana(pCodCampana, pCodProd, pDNI);
        } catch (Exception e) {
            log.error("Error al obtener Maximo Unidades de Dcto Producto", e);
            pResultado = -1;
        }
        log.info("Maximo de Dcto Unid Prod Campana: pCodProd: " + pCodProd + " -pCodCampana: " + pCodCampana +
                 " -MaxUnid: " + pResultado);
        return pResultado;
    }

    /**
     * Este procedimiento valida el pedido Fidelizado
     * como el DNi valido, RUC valido o Maximo DCTO x DNI
     * @author DUBILLUZ
     * @since  29.05.2009
     * @param pCodCampana
     * @param pCodProd
     * @return
     */
    public static boolean validaPedidoFidelizado(String pNumPed, String pRuc, Object pDialogo, Object pObjeto,
                                                 String ptarj) {
        boolean pResultado = false;
        String pRes = "";
        String pMensaje = "";
        String pValidaMatriz = "";
        String ipLinea = "";
        try {

            pValidaMatriz = DBFidelizacion.isValidaMatrizDescuento(pNumPed);
            if (pValidaMatriz.trim().length() > 1) {
                //Se obtuvo un codigo de campana q valida  la tarjeta unica.
                ipLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);

                if (ipLinea.trim().equalsIgnoreCase("S")) {
                    pRes =
DBFidelizacion.isValidaPedidoFidelizadoMatriz(pNumPed, pRuc, VariablesFidelizacion.vDniCliente);
                } else {
                    //no hay linea SOLO LOCALMENTE
                    pRes = DBFidelizacion.isValidaPedidoFidelizado(pNumPed, pRuc, ptarj);
                }
            } else {
                //VALIDA LOCALMENTE
                pRes = DBFidelizacion.isValidaPedidoFidelizado(pNumPed, pRuc, ptarj);
            }

            if (pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                pResultado = true;
            } else {
                if (pRes.trim().equalsIgnoreCase("N_DNI")) {
                    pMensaje =
                            "DNI no afecto a Descuento"; // "No puede cobrar el pedido con descuentos para el DNI ingresado.";
                } else {
                    if (pRes.trim().equalsIgnoreCase("N_RUC")) {
                        pMensaje = "Los descuentos son para venta con\n" +
                                "boleta de venta y para consumo\n" +
                                "personal. El RUC ingresado queda\n" +
                                "fuera de la promoción de descuento."; //"No puede cobrar el pedido con descuentos para el RUC ingresado.";

                    } else {
                        if (pRes.trim().equalsIgnoreCase("N_DCTO"))
                            pMensaje = "DNI excedió el máximo\n" +
                                    "de descuento permitido.\n" +
                                    "Debe recalcular la venta."; //"No puede cobrar el pedido porque el Cliente excedió el máximo descuento Diario.";
                        VariablesFidelizacion.vRecalculaAhorroPedido = true;
                    }
                }
                pResultado = false;
                log.error(pMensaje);
                FarmaUtility.showMessage((JDialog)pDialogo, pMensaje.trim(), pObjeto);
            }

        } catch (Exception e) {
            log.error("Error al validar el Pedido Fidelizado", e);
            pResultado = true;
        }
        return pResultado;
    }

    public static double getDescuentoPersonalizadoProdCampana(String pCodCampana, String pCodProd, String pDNI) {
        double pResultado = 0;
        
        try {
            pResultado = DBFidelizacion.getDctoPersonalizadoCampanaProd(pCodCampana, pCodProd, pDNI);
        } catch (Exception e) {
            log.error("Error al obtener el descuento personalizado por Campana y producto", e);
            pResultado = -1;
        }
        log.info("Descuento PersonalizadoProd Campana: pCodProd: " + pCodProd + " -pCodCampana: " + pCodCampana +
                 " - Descuento: " + pResultado);
        return pResultado;
    }

    /**
     * Consulta para saber si la tarjeta ingresada esta en una campaña automatica
     * @param cadena
     * @return
     */
    public static boolean isTarjetaPagoInCampAutomatica(String cadena) {

        boolean retorno = true;
        String valida = "";

        if (isNumericoTarjeta(cadena)) {
            try {
                valida = DBFidelizacion.isTarjetaFp_CampAutomatica(cadena);
                if (valida.equalsIgnoreCase("N")) {
                    retorno = false;
                    log.debug("la tarjeta no esta asociada a ninguna forma de pago de campana automatica");
                } else {
                    log.debug("Tarjeta asociada a una campana automatica con esa forma de pago");
                }
            } catch (SQLException e) {
                log.error("", e);
            }
        } else
            retorno = false;
        return retorno;
    }

    public static boolean isNumericoTarjeta(String pCadena) {
        int numero = 0;
        boolean pRes = false;
        try {
            for (int i = 0; i < pCadena.length(); i++) {
                numero = Integer.parseInt(pCadena.charAt(i) + "");
                pRes = true;
            }

        } catch (NumberFormatException e) {
            pRes = false;
        }
        return pRes;
    }

    /**
     *
     */
    public static String getDatosTarjetaFormaPago(String pTarjetaIngresada) {
        //getDatosTarjetaIngresada
        String datosFormaPago = "N";
        try {
            datosFormaPago = DBFidelizacion.getDatosTarjetaIngresada(pTarjetaIngresada);
        } catch (SQLException e) {
            log.error("", e);
            datosFormaPago = "N";
        } finally {

        }
        return datosFormaPago.trim();
    }

    /**
     * DUBILLUZ
     * 19.07.2011
     */
    public static void grabaTarjetaUnica(String pTarjetaPago, String pdni) {
        log.debug("graba tarjeta y dni a la campana" + pTarjetaPago + "-" + pdni);
        try {
            DBFidelizacion.grabaTarjetaUnica(pTarjetaPago, pdni);
            FarmaUtility.aceptarTransaccion();
            VariablesFidelizacion.vNumTarjeta = pTarjetaPago;
            //log.debug("(pdni.trim().indexOf(\"TU\")"+pdni.trim().indexOf("TU"));
            if (isContieneLetra(pdni)) {
                //todos son NUMEROS ahora se validará la campaña para invertir de acuerdo al
                //flag y la cadena INICIAL
                String pCadenaInvertir = DBFidelizacion.getCadenaInvierteDNI("N", pTarjetaPago.trim()).trim();
                boolean cambioDNI = false;
                if (pCadenaInvertir != null) {
                    if (!pCadenaInvertir.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                        log.debug("cambiando DNI a invertir y coloca el FLAG:<<" + pCadenaInvertir + ">>");
                        VariablesFidelizacion.vDniCliente = pCadenaInvertir.trim() + invertirLetra(pdni);
                        cambioDNI = true;
                    }
                }

                if (!cambioDNI) {
                    VariablesFidelizacion.vDniCliente = pdni.trim();
                    log.debug("no ha cambiado el DNI ..se coloca el mismo DNI");
                }
            } else
                VariablesFidelizacion.vDniCliente = pdni.trim();
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);            
        }
    }

    public static boolean isContieneLetra(String pCadena) {
        
        char letra = ' ';

        for (int i = 0; i < pCadena.length(); i++) {
            letra = pCadena.charAt(i);
            if (!Character.isDigit(letra)) {
                log.debug("exite letra");
                return false;

            }
        }

        log.debug("todos son numeros");
        return true;
    }

    public static String invertirLetra(String pCadena) {
        int tamano = pCadena.length() - 1;
        char letra = ' ';
        String CadenaInversa = "";
        for (int i = tamano; i >= 0; i--) {
            letra = pCadena.charAt(i);
            log.debug("letra:" + letra);
            CadenaInversa = CadenaInversa + "" + letra;
        }
        log.debug("CadenaInversa:" + CadenaInversa);
        return CadenaInversa;
    }

    /**
     * Recibe la primera Trama
     * @author DUBILLUZ
     * @since  21.07.2011
     * @return
     */
    public static String pIsTarjetaVisaRetornaNumero(String pCadena) {
        /*
         * Trama de Ejemplo
         *
            %B4919148070794606&JULIO OLIVA J      -     &1509101137250000000000742000000_
            Ñ4919148070794606¿15091011372574200000_
         * */

        try {
            log.debug("pCadena:" + pCadena);
            log.debug("pCadena.indexOf(\"%\"):" + pCadena.indexOf("%"));
            //String pResultado  = "N";
            String pCadenNueva = "N";
            if (pCadena.length() > 10) {
                ArrayList infTarjeta = new ArrayList();
                UtilityLectorTarjeta utilityLectorTarjeta = new UtilityLectorTarjeta();
                infTarjeta = utilityLectorTarjeta.capturaTeclasLector(null, pCadena);
                if (infTarjeta.size() > 0) {
                    pCadenNueva = infTarjeta.get(0).toString();
                    if (pCadenNueva.equals("")) {
                        pCadenNueva = "N";
                        return pCadenNueva;
                    }
                    // kmoncada 28.08.2014 verifica que el nro de tarjeta contenga solo digitos
                    char[] cCad = pCadenNueva.toCharArray();
                    boolean contieneCaract = false;
                    int pos = 0;
                    while (!contieneCaract && pos < cCad.length) {
                        if (!Character.isDigit(cCad[pos])) {
                            contieneCaract = true;
                        }
                        pos++;
                    }
                    if (contieneCaract) {
                        pCadenNueva = "N";
                    }

                }
            }
            return pCadenNueva;
        } catch (Exception e) {
            log.error("error al leer tarjeta.", e);
            //pCadenNueva = "N";
            return "N";
        }

        //return pCadenNueva;
    }


    //jquispe 26.07.2011
    //Devuelve el indicador de fidelizacion de una campaña

    public static String getIndfidelizadoUso(String pCodCampania) {
        try {
            return DBFidelizacion.obtenerIndFidUso(pCodCampania);
        } catch (SQLException e) {
            return "N";
        }
    }

    /**
     * DUBILLUZ 06.12.2011
     * @return
     */
    public static String getPermiteIngresoMedido() {
        try {
            return DBFidelizacion.permiteIngresoMedico();
        } catch (SQLException e) {
            return "N";
        }
    }

    /**
     * DUBILLUZ 06.12.2011
     * @return
     */
    public static String getExisteMedido(JDialog pJDialog, Frame pParent, String pCodMedido) {
        String pExiste = "N";
        List listaMed = new ArrayList();
        String pPrimeraFila = "";
        try {
            listaMed = DBFidelizacion.busquedaMedicos(pCodMedido.trim());
            if (listaMed.size() > 0) {
                pPrimeraFila = (((HashMap)listaMed.get(0)).get("NUM_CMP")).toString().trim();
                if (pPrimeraFila.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                    // NO EXISTEN MEDICOS CON EL NUMERO INDICADO
                    pExiste = "N";
                else {
                    ///////////////////////////////////
                    if (listaMed.size() == 1) {
                        pExiste = "S";
                        // Se va seleccionar automáticamente el medico Ingresado
                        HashMap map = (HashMap)listaMed.get(0);
                        VariablesFidelizacion.V_NUM_CMP = ((String)map.get("NUM_CMP")).trim();
                        VariablesFidelizacion.V_NOMBRE = ((String)map.get("NOMBRE")).trim();
                        VariablesFidelizacion.V_DESC_TIP_COLEGIO = ((String)map.get("DESC_TIP_COLEGIO")).trim();
                        VariablesFidelizacion.V_OLD_TIPO_COLEGIO = ((String)map.get("DESC_TIP_COLEGIO")).trim();
                        VariablesFidelizacion.V_TIPO_COLEGIO = ((String)map.get("TIPO_COLEGIO")).trim();
                        VariablesFidelizacion.V_COD_MEDICO = ((String)map.get("COD_MEDICO")).trim();

                        FarmaUtility.showMessage(pJDialog, "Médico Seleccionado:\n" +
                                "CMP:" + VariablesFidelizacion.V_NUM_CMP + "\n" +
                                VariablesFidelizacion.V_DESC_TIP_COLEGIO + "\n" +
                                VariablesFidelizacion.V_NOMBRE, null);

                    }
                    ///////////////////////////////////
                    else {
                        DlgMedicoCampana dlgLista = new DlgMedicoCampana(pParent, "", true, (ArrayList)listaMed);
                        dlgLista.setVisible(true);
                        if (FarmaVariables.vAceptar) {
                            pExiste = "S";
                            FarmaUtility.showMessage(pJDialog, "Médico Seleccionado:\n" +
                                    "CMP:" + VariablesFidelizacion.V_NUM_CMP + "\n" +
                                    VariablesFidelizacion.V_DESC_TIP_COLEGIO + "\n" +
                                    VariablesFidelizacion.V_NOMBRE, null);
                        } else {
                            pExiste = "NO_SELECCIONO";
                        }
                    }
                }

            }
        } catch (SQLException e) {
            log.error("", e);
            pExiste = "N";
        }
        return pExiste;
    }

    /**
     * DUBILLUZ 06.12.2011
     * @param pNumTarjeta
     * @return
     */
    public static void agregaCampanaMedicoAuto(String pNumTarjeta, String pMedico, String pMedicoOld) {
        List listaCampaTarj = new ArrayList();
        List listaCampaTarjOld = new ArrayList();
        try {
            listaCampaTarj = DBFidelizacion.getCampanaMedicoAuto(pNumTarjeta, pMedico);
            listaCampaTarjOld = DBFidelizacion.getCampanaMedicoAuto(pNumTarjeta, pMedicoOld);
        } catch (SQLException e) {
            listaCampaTarj = new ArrayList();
            log.error("", e);
        }

        //quita las campnas del anterior medico ingresado
        log.debug("** inicio quitar antiguas *** ");
        if (listaCampaTarjOld.size() > 0) {
            log.debug("VariablesFidelizacion.vListCampañasFidelizacion..." +
                      VariablesFidelizacion.vListCampañasFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones ..." + VariablesVentas.vArrayList_Cupones.size());

            Map map = new HashMap();
            Map mapAux = new HashMap();
            String codCamp = "", codCampAux = "";
            for (int i = 0; i < listaCampaTarjOld.size(); i++) {
                mapAux = (HashMap)listaCampaTarjOld.get(i);
                codCampAux = ((String)mapAux.get("COD_CAMP_CUPON")).trim();
                // elimina del VariablesFidelizacion.vListCampañasFidelizacion
                for (int j = 0; j < VariablesFidelizacion.vListCampañasFidelizacion.size(); j++) {
                    map = new HashMap();
                    map = (HashMap)VariablesFidelizacion.vListCampañasFidelizacion.get(j);
                    codCamp = ((String)map.get("COD_CAMP_CUPON")).trim();
                    if (codCampAux.trim().equalsIgnoreCase(codCamp)) {
                        VariablesFidelizacion.vListCampañasFidelizacion.remove(j);
                        log.debug("ELIMINANDO::DE ARREGLO::: " + codCampAux);
                    }
                }
                // elimina del VariablesVentas.vArrayList_Cupones
                for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                    map = new HashMap();
                    map = (HashMap)VariablesVentas.vArrayList_Cupones.get(j);
                    codCamp = ((String)map.get("COD_CAMP_CUPON")).trim();
                    if (codCampAux.equalsIgnoreCase(codCamp)) {
                        VariablesVentas.vArrayList_Cupones.remove(j);
                        log.debug("ELIMINANDO::DE ARREGLO::: " + codCampAux);
                    }
                }
            }

            log.debug("VariablesFidelizacion.vListCampañasFidelizacion..." +
                      VariablesFidelizacion.vListCampañasFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones ..." + VariablesVentas.vArrayList_Cupones.size());
        }
        log.debug("** FIN quitar antiguas *** ");
        // agrega las campanas dl nuevo medico ingresado
        if (listaCampaTarj.size() > 0) {
            log.debug("Agrega las campanas del medico ingresado..." + listaCampaTarj.size());
            log.debug("VariablesFidelizacion.vListCampañasFidelizacion..." +
                      VariablesFidelizacion.vListCampañasFidelizacion.size());
            for (int i = 0; i < listaCampaTarj.size(); i++) {
                VariablesFidelizacion.vListCampañasFidelizacion.add(listaCampaTarj.get(i));
            }
            log.debug("Despues  ..." + VariablesFidelizacion.vListCampañasFidelizacion.size());


            Map mapCampFid = new HashMap(); //mapa de la campania de fidelizacion
            Map mapCampCup = new HashMap(); //mapa de la campania del listado de cupones

            String codCampFid = ""; //codigo de campania de fidelizacion
            String CodCampCup = ""; //codigo de campania de cupon de la lista general

            log.debug("VariablesVentas.vArrayList_Cupones.size():" + VariablesVentas.vArrayList_Cupones.size());
            boolean existe = false;
            for (int i = 0; i < listaCampaTarj.size(); i++) {
                mapCampFid = (HashMap)listaCampaTarj.get(i);
                codCampFid = ((String)mapCampFid.get("COD_CAMP_CUPON")).trim();
                for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                    mapCampCup = (HashMap)VariablesVentas.vArrayList_Cupones.get(j);
                    CodCampCup = ((String)mapCampCup.get("COD_CAMP_CUPON")).trim();
                    if (codCampFid.equalsIgnoreCase(CodCampCup)) { //ver si ya existe
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    log.debug("agregando  mapCampFid:" + mapCampFid + ", a VariablesVentas.vArrayList_Cupones:" +
                              VariablesVentas.vArrayList_Cupones);
                    VariablesVentas.vArrayList_Cupones.add(mapCampFid);
                }
            }
            log.debug("VariablesVentas.vArrayList_Cupones.size():" + VariablesVentas.vArrayList_Cupones.size());


        }
    }


    public static void quitarCampanaMedico(String pNumTarjeta, String pMedicoOld) {
        List listaCampaTarj = new ArrayList();
        List listaCampaTarjOld = new ArrayList();
        try {
            listaCampaTarjOld = DBFidelizacion.getCampanaMedicoAuto(pNumTarjeta, pMedicoOld);
        } catch (SQLException e) {
            listaCampaTarj = new ArrayList();
            log.error("", e);
        }

        //quita las campnas del anterior medico ingresado
        log.debug("** inicio quitar antiguas *** ");
        if (listaCampaTarjOld.size() > 0) {
            log.debug("VariablesFidelizacion.vListCampañasFidelizacion..." +
                      VariablesFidelizacion.vListCampañasFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones ..." + VariablesVentas.vArrayList_Cupones.size());

            Map map = new HashMap();
            Map mapAux = new HashMap();
            String codCamp = "", codCampAux = "";
            for (int i = 0; i < listaCampaTarjOld.size(); i++) {
                mapAux = (HashMap)listaCampaTarjOld.get(i);
                codCampAux = ((String)mapAux.get("COD_CAMP_CUPON")).trim();
                // elimina del VariablesFidelizacion.vListCampañasFidelizacion
                for (int j = 0; j < VariablesFidelizacion.vListCampañasFidelizacion.size(); j++) {
                    map = new HashMap();
                    map = (HashMap)VariablesFidelizacion.vListCampañasFidelizacion.get(j);
                    codCamp = ((String)map.get("COD_CAMP_CUPON")).trim();
                    if (codCampAux.trim().equalsIgnoreCase(codCamp)) {
                        VariablesFidelizacion.vListCampañasFidelizacion.remove(j);
                        log.debug("ELIMINANDO::DE ARREGLO::: " + codCampAux);
                    }
                }
                // elimina del VariablesVentas.vArrayList_Cupones
                for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                    map = new HashMap();
                    map = (HashMap)VariablesVentas.vArrayList_Cupones.get(j);
                    codCamp = ((String)map.get("COD_CAMP_CUPON")).trim();
                    if (codCampAux.equalsIgnoreCase(codCamp)) {
                        VariablesVentas.vArrayList_Cupones.remove(j);
                        log.debug("ELIMINANDO::DE ARREGLO::: " + codCampAux);
                    }
                }
            }

            log.debug("VariablesFidelizacion.vListCampañasFidelizacion..." +
                      VariablesFidelizacion.vListCampañasFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones ..." + VariablesVentas.vArrayList_Cupones.size());
        }
        log.debug("** FIN quitar antiguas *** ");
        // agrega las campanas dl nuevo medico ingresado
    }

    /**
     * DUBILLUZ - 01.06.2012
     * @param pCodCampana
     * @param pDni
     * @param pTarj
     * @return
     */
    public static boolean getPermiteCampanaTarj(String pCodCampana, String pDni, String pTarj) {
        boolean pRes = false;
        ArrayList lista = new ArrayList();
        try {
            DBFidelizacion.getCampTarjetaEspecial(lista, pDni, pTarj);
        } catch (SQLException e) {
            log.error("", e);
        }
        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                String pCadena = ((ArrayList)lista.get(i)).get(0).toString().trim();
                log.info("pCadena>>" + pCadena);
                if (!pCadena.equalsIgnoreCase("N")) {
                    if (pCodCampana.trim().equalsIgnoreCase(pCadena)) {
                        pRes = true;
                    }
                }
            }
        } else
            pRes = true;

        return pRes;
    }

    /**
     * Verifica si existe el DNI ingresado en la BD de RENIEC
     * @author DUBILLUZ
     * @param pDNI
     * @return
     */
    public static boolean existeDNIenRENIEC(String pDNI) {
        boolean pValor = false;
        try {
            if (DBFidelizacion.getDatoDNIReniec(pDNI).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                pValor = false;
            else
                pValor = true;
        } catch (SQLException e) {
            pValor = false;
            log.error("", e);
        }
        return pValor;
    }

    public static void limpiaVariablesMedico() {
        ///////////////////////////////////////////////
        VariablesFidelizacion.V_NUM_CMP = "";
        VariablesFidelizacion.V_NOMBRE = "";
        VariablesFidelizacion.V_DESC_TIP_COLEGIO = "";
        VariablesFidelizacion.V_TIPO_COLEGIO = "";
        VariablesFidelizacion.V_COD_MEDICO = "";
        VariablesFidelizacion.vColegioMedico = "";
        ///////////////////////////////////////////////
    }

    public static ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosMatriz = new ArrayList();
        try {
            listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosLocalXCliente(dniCliente);
            if (listaCampLimitUsadosMatriz.size() > 0) {
                listaCampLimitUsadosMatriz = (ArrayList)listaCampLimitUsadosMatriz.get(0);
            }
            log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> " + listaCampLimitUsadosMatriz);
        } catch (Exception e) {
            log.error("", e);
        }
        return listaCampLimitUsadosMatriz;
    }


    public static boolean getIndComisionnew() {
        boolean bFlag = false;
        String vValor = "";
        try {

            vValor = DBFidelizacion.getIndicadorComision();
            if (vValor.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                bFlag = true;
            } else {
                bFlag = false;
            }

        } catch (SQLException e) {
            log.error("", e);
        }

        return bFlag;
    }

    /**
     * get apellidos juntos
     * @author ASOSA
     * @since 24.02.2015
     *  
     * @param nombreCompleto
     * @return
     */
    public static String getApellidos(String nombreCompleto) {
        String apellidos = "";
        try {
            apellidos = DBFidelizacion.getApellidos(nombreCompleto);
            if (apellidos == null || apellidos.equals("")) {
                apellidos = " ";
            }
        } catch (Exception e) {
            log.error("", e);
            apellidos = "ERROR";
        } finally {
            return apellidos;
        }
    }

    /**
     * get parte del nombre
     * @author ASOSA
     * @since 24.02.2015
     *  
     * @param nombreCompleto
     * @return
     */
    public static String getParteNombre(String nombreCompleto, int tipo) {
        String parte = "";
        try {
            parte = DBFidelizacion.getParteNombre(nombreCompleto, tipo);
            if (parte == null || parte.equals("")) {
                parte = " ";
            }
        } catch (Exception e) {
            log.error("", e);
            parte = "ERROR";
        } finally {
            return parte;
        }
    }

    /**
     * Insertar determinados procesos del fv para puntos.
     * @author ASOSA
     * @since 27.02.2015
     *  
     * @param operacionDTO
     * @throws SQLException
     */
    public static void insertarLogOperacion(OperacionDTO operacionDTO) {
        try {
            DBFidelizacion.insertarLogOperacion(operacionDTO);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * Determina si se imprimira o no el mensajaso de experto de ahorro
     * @author ASOSA
     * @since 10.03.2015
     *  
     * @param numPedVta
     * @param secCompPago
     * @return
     */
    public static boolean getIndImpresionTextExpert(String numPedVta, String secCompPago) {
        boolean flag = false;
        String ind = "N";
        try {
            ind = DBFidelizacion.getIndImpresionTextExpert(numPedVta, secCompPago);
            if (ind != null) {
                if (ind.equals("S")) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            return flag;
        }
    }

    /**
     * Determinar texto para mostrar al mensajaso de xperto de ahorro
     * @author ASOSA
     * @since 10.03.2015
     *  
     * @param numPedVta
     * @param secCompPago
     */
    public static void getTextExpertImpresion(String numPedVta, String secCompPago) {
        String texto = "";
        VariablesFidelizacion.texto01Expert = "";
        VariablesFidelizacion.texto02Expert = "";
        try {
            texto = DBFidelizacion.getTextExpertImpresion(numPedVta, secCompPago);
            String[] datos = texto.split("Ã");
            if (datos != null) {
                if (datos.length == 3) {
                    VariablesFidelizacion.texto01Expert = datos[0];
                    VariablesFidelizacion.texto02Expert = datos[1];
                    VariablesFidelizacion.texto03Expert = datos[2];
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * Obtiene datos de impresion de texto experto
     * @author ASOSA
     * @since 24.03.2015
     *  
     * @param numPedVta
     * @param secCompPago
     * @return
     * @throws Exception
     */
    public static List getDatosImpresionExpert(String numPedVta, String secCompPago) {
        List listComanda = new ArrayList();
        try {
            listComanda = DBFidelizacion.getDatosImpresionExpert(numPedVta, secCompPago);

        } catch (Exception e) {
            log.error("", e);
        } finally {
            return listComanda;
        }
    }

    /**
     * imprimirVoucherFid
     * @author ASOSA
     * @since 17/04/2015
     * @type 
     * @param pConsejos
     * @param pImpresora
     * @param pTipoImprConsejo
     * @param pCodigoBarraConv
     */
    public static void imprimirVoucherFid(String dniCli, String pNroTarjetaFidelizado) {
        try {
            // KMONCADA 01.06.2015 METODO DE IMPRESION POR TERMICA SIN HTML
            List lista = DBFidelizacion.imprimeVoucherFidelizacion(dniCli);
            if (lista != null) {
                new UtilityImpCompElectronico().impresionTermica(lista, null);
            } else {
                log.info("IMPRESION DE VOUCHER DE FIDELIZACION: ERROR AL OBTENER COMPROBANTE DE BD");
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static boolean getExisteProdListaLocked(String vCodProd) {
        boolean flag = false;
        String ind = "N";
        try {
            ind = DBFidelizacion.getIndListaProdLocked(vCodProd);
            if (ind != null) {
                if (ind.equals("S")) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            return flag;
        }
    }


    public static boolean getExisteProdListaLockedDia(String vCodProd) {
        boolean flag = false;
        String ind = "N";
        try {
            ind = DBFidelizacion.getIndListaProdLockedDia(vCodProd);
            if (ind != null) {
                if (ind.equals("S")) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            return flag;
        }
    }


    public static boolean getExisteProdListaLockedSemana(String vCodProd) {
        boolean flag = false;
        String ind = "N";
        try {
            ind = DBFidelizacion.getIndListaProdLockedSemana(vCodProd);
            if (ind != null) {
                if (ind.equals("S")) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            return flag;
        }
    }


    public static double getUnidMaxDctoDia(String vCodProd) {
        double flag = 0.0;
        
        try {
            flag = DBFidelizacion.getUnidMaxProdDia(vCodProd);
        } catch (Exception e) {
            flag = 0.0;
            log.error("", e);
        } finally {
            return flag;
        }
    }


    public static double getUnidMaxDctoSemana(String vCodProd) {
        double flag = 0.0;
        
        try {
            flag = DBFidelizacion.getUnidMaxProdSemana(vCodProd);
        } catch (Exception e) {
            flag = 0.0;
            log.error("", e);
        } finally {
            return flag;
        }
    }


    public static double getHistUnidDctoDia(String vCodProd, String pDNI) {
        double flag = 0.0;
        
        try {
            flag = DBFidelizacion.getHistUnidProdDia(vCodProd, pDNI);
        } catch (Exception e) {
            flag = 0.0;
            log.error("", e);
        } finally {
            return flag;
        }
    }


    public static double getHistUnidDctoSemana(String vCodProd, String pDNI) {
        double flag = 0.0;
        
        try {
            flag = DBFidelizacion.getHistUnidProdSemana(vCodProd, pDNI);
        } catch (Exception e) {
            flag = 0.0;
            log.error("", e);
        } finally {
            return flag;
        }
    }


    public static boolean getIndRedondeaPrecioCampana(String vCodCampana) {
        boolean flag = false;
        try {
            flag = DBFidelizacion.getIndRedondeaPrecioCampana(vCodCampana);
        } catch (Exception e) {
            flag = false;
            log.error("", e);
        } finally {
            return flag;
        }
    }
    
    public static String obtenerMensajeReemplazoClientePedido(){
        return "Programa Monedero:\nYa registro un cliente, ¿desea cambiarlo?";
    }

}
