package mifarma.ptoventa.convenioBTLMF.reference;

import java.awt.Frame;
import java.awt.event.KeyEvent;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaPrintServiceTicket;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;
import mifarma.electronico.UtilityImpCompElectronico;
import mifarma.electronico.epos.reference.DBEpos;
import mifarma.electronico.epos.reference.EposVariables;
import mifarma.electronico.impresion.dao.ConstantesDocElectronico;

import mifarma.ptoventa.administracion.impresoras.reference.ContextImpresion;
import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.FormatoImpresionA;
import mifarma.ptoventa.administracion.impresoras.reference.FormatoImpresionB;
import mifarma.ptoventa.administracion.impresoras.reference.FormatoImpresionC;
import mifarma.ptoventa.administracion.impresoras.reference.Impresora;
import mifarma.ptoventa.administracion.impresoras.reference.ImpresoraTicket;
import mifarma.ptoventa.caja.DlgProcesarNuevoCobro;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.DocumentRendererConsejo;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.DlgProcesarDatosConvenio;
import mifarma.ptoventa.despacho.reference.UtilityDespacho;
import mifarma.ptoventa.hilos.SubProcesosConvenios;
import mifarma.ptoventa.puntos.reference.DBPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.DlgMensajeImpresion;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityConvenioBTLMF {

    private static final Logger log = LoggerFactory.getLogger(UtilityConvenioBTLMF.class);

    private static int acumuCorreGuia = 0;
    private static int acumuCorreFac = 0;
    private static int acumuCorreTik = 0;
    private static int acumuCorreBol = 0;

    public UtilityConvenioBTLMF() {
        super();

    }

    public UtilityConvenioBTLMF(int pAnchoLinea) {
        super();

    }

    public static boolean indDatoConvenio(String pCodigoConvenio, JDialog pDialogo, Object pObjeto) {
        boolean resul = false;
        String indConv = "";
        String indProdConv = "";

        try {
            indConv = DBConvenioBTLMF.pideDatoConvenio(pCodigoConvenio);
            log.debug("INDICADOR PIDE DATO CONV = " + indConv);
            if (indConv.equalsIgnoreCase("S")) {
                resul = true;
            } else if (indConv.equalsIgnoreCase("T")) {
                //indProdConv =  DBConvenioBTLMF.existeProdConvenio();

                //            	  if (indProdConv.equalsIgnoreCase("N"))
                //            	  {
                //            		   FarmaUtility.showMessage(pDialogo, "No hay productos cubiertos para el Convenio. e\n", pObjeto);
                //            		   resul = false;
                //                   	   VariablesConvenioBTLMF.vAceptar = false;
                //
                //            	  }
                //            	  else
                // {
                resul = false;
                VariablesConvenioBTLMF.vAceptar = true;

                //}
            } else if (indConv.equalsIgnoreCase("P")) {
                VariablesConvenioBTLMF.vAceptar = true;
                resul = false;
                //FarmaUtility.showMessage(pDialogo, "El convenio no tiene datos registrados. e\n", pObjeto);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo, "Error en buscar si debe mostrase datos convenios\n" +
                    sql.getMessage(), pObjeto);
            resul = true;
        }
        return resul;

    }

    public static List listaDatosConvenio(String pCodConvenio, JDialog pDialogo, Object pObjeto) {
        List lista = null;
        try {
            lista = DBConvenioBTLMF.listaDatosConvenio(pCodConvenio);
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener datos del convenio!!!", pObjeto);
        }
        log.debug("ListaDatConv" + lista);
        return lista;
    }

    public static Map obtienePantallaMensaje(String pNroResolucion, String pPosicion, JDialog pDialogo,
                                             Object pObjeto) {
        Map map = null;
        try {
            map = DBConvenioBTLMF.obtienePantallaMensaje(pNroResolucion, pPosicion);
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener datos de la pantalla!!!", pObjeto);
        }
        log.debug("Map Pantalla:" + map);
        return map;
    }

    public static String obtieneDocVerificacion(String pCodConvenio, String pFlgRetencion, String pNomBenificiario,
                                                JDialog pDialogo, Object pObjeto) {
        String msg = "";

        try {
            msg = DBConvenioBTLMF.ObtieneDocVerificacion(pCodConvenio, pFlgRetencion, pNomBenificiario);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener datos de Documentos de verificación!!!", "");
        }

        log.debug("msg:" + msg);
        return msg;
    }


    public static void listaMensaje(ArrayList lista, String pCodConvenio, String pFlgRetencion, JDialog pDialogo,
                                    Object pObjeto) {

        try {
            DBConvenioBTLMF.listaMensajes(lista, pCodConvenio, pFlgRetencion);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener datos de Documentos de verificación!!!", "");
        }


    }


    public static Map obtieneBenificiario(String pCodConvenio, String pDni, JDialog pDialogo) {
        Map benif = null;

        try {
            benif = DBConvenioBTLMF.obtieneBenificiario(pCodConvenio, pDni);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al buscar Benificiario", "");
        }


        log.debug("benif:" + benif);
        return benif;
    }

    public static String existeBenificiario(String pCodConvenio, String pDni, JDialog pDialogo) {
        String benif = null;

        try {
            benif = DBConvenioBTLMF.existeBenificiario(pCodConvenio, pDni);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al buscar Benificiario", "");
        }


        log.debug("benif:" + benif);
        return benif;
    }


    public static Map obtenerTarjeta(String pCodBarra, JDialog pDialogo) {
        Map benif = null;

        try {
            benif = DBConvenioBTLMF.obtenerTarjeta(pCodBarra);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al buscar Benificiario con Tarjeta", "");
        }


        log.debug("msg:" + benif.get(ConstantsConvenioBTLMF.COL_DNI));
        return benif;
    }


    public static Map obtenerCliente(String pCodCliente, JDialog pDialogo) {
        Map cliente = null;

        try {
            cliente = DBConvenioBTLMF.obtenerCliente(pCodCliente);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener cliente", "");
        }

        log.debug("msg:" + cliente.get(ConstantsConvenioBTLMF.COL_DNI));
        return cliente;
    }


    public static Map obtieneDiagnostico(String pCodConvenio, String pCODCIE10, JDialog pDialogo) {
        Map benif = null;

        try {
            benif = DBConvenioBTLMF.obtieneDiagnostico(pCODCIE10);
            VariablesConvenioBTLMF.codDiagnostico =
                    (String)benif.get(ConstantsConvenioBTLMF.COD_DIAGNOSTICO); //CHUANES 28.03.2014 EXTRAEMOS EL CODIGO DE DIAGNOSTICO
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al buscar Diagnòstico", "");
        }

        log.debug("msg:" + benif.get(ConstantsConvenioBTLMF.COL_COD_CIE_10));
        return benif;
    }


    public static Map obtieneMedico(String pCodConvenio, String pCodMedico, JDialog pDialogo) {
        Map medico = null;

        try {
            medico = DBConvenioBTLMF.obtieneMedico(pCodConvenio, pCodMedico);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al buscar Médico", "");
        }

        log.debug("msg:" + medico.get(ConstantsConvenioBTLMF.COL_NUM_CMP));
        return medico;
    }

    public static Map obtenerConvenio(String pCodConvenio, JDialog pDialogo, Frame myParentFrame) {
        Map medico = null;
        DlgProcesarDatosConvenio dlgProcesarDatosConvenio = new DlgProcesarDatosConvenio(myParentFrame, "", true);
        dlgProcesarDatosConvenio.setPDialogo(pDialogo);
        dlgProcesarDatosConvenio.setPCodConvenio(pCodConvenio);
        dlgProcesarDatosConvenio.mostrar();
        medico = dlgProcesarDatosConvenio.getMedico();
        return medico;

    }

    public static void filtraDescripcion2(KeyEvent evento, FarmaTableModel tableModelo, ArrayList listaDatos,
                                          JTextField jtext, int columna) {

        ArrayList listaConvenio = filtraListaDato2(evento, listaDatos, jtext, columna);

        copiaTablaModelo(tableModelo, listaConvenio, false);

    }


    public static void filtraCampoString(KeyEvent evento, FarmaTableModel tableModelo, ArrayList listaDatos,
                                         JTextField jtext, int columna) {

        ArrayList listaConvenio = filtraListaDatoString(evento, listaDatos, jtext, columna);

        copiaTablaModelo(tableModelo, listaConvenio, false);

    }


    private static void copiaTablaModelo(FarmaTableModel pTableModel, ArrayList lista, boolean pWithCheck) {
        log.debug("<<<<<<<<<<<<<Metdo: copiaTablaModelo >>>>>>>>> " + lista);

        if (pTableModel != null) {

            //ERIOS 2.4.6 Seleccion multi-diagnostico
            ArrayList seleccionados = new ArrayList();
            if (VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_DIAGNOSTICO_UIE)) {
                for (int i = 0; i < pTableModel.data.size(); i++) {
                    ArrayList row = (ArrayList)pTableModel.data.get(i);
                    if (((Boolean)row.get(0)).booleanValue()) {
                        seleccionados.add(row);
                    }
                }
            }

            pTableModel.clearTable();

            ArrayList myArray = null;
            pTableModel.clearTable();

            for (int i = 0; i < seleccionados.size(); i++) {
                ArrayList row = (ArrayList)seleccionados.get(i);
                pTableModel.insertRow(row);
            }

            for (int i = 0; i < lista.size(); i++) {
                //String[] arg = (String[])lista.get(i);
                Object[] arg = (Object[])lista.get(i);

                boolean existe = false;
                for (int j = 0; j < seleccionados.size(); j++) {
                    ArrayList row = (ArrayList)seleccionados.get(j);
                    if (arg[0].toString().equals(row.get(1).toString())) {
                        seleccionados.remove(j);
                        existe = true;
                        break;
                    }
                }

                if (arg.length > 0 && !existe) {
                    myArray = new ArrayList();
                    //ERIOS 2.4.6 Seleccion multi-diagnostico
                    if (VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_DIAGNOSTICO_UIE)) {
                        myArray.add(new Boolean(false));
                    }
                    for (int y = 0; y < arg.length; y++) {
                        myArray.add(arg[y]);
                    }
                    pTableModel.insertRow(myArray);
                }
            }
        }
    }

    private static ArrayList filtraListaDato2(KeyEvent e, ArrayList listaTodo, JTextField pTextoDeBusqueda,
                                              int pColumna) {
        log.debug("<<<<<<<<<<<<Metodo: filtraListaDato2  >>>>>>>>>>>>>>>" + pTextoDeBusqueda.getText().trim());
        log.debug("<<<<<<<<<<<<Tamano::::  >>>>>>>>>>>>>>>" + listaTodo.size());

        ArrayList lista = new ArrayList();


        if ((e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) && ((e.getKeyCode() != KeyEvent.VK_ESCAPE))) {


            String vFindText = pTextoDeBusqueda.getText().toUpperCase();


            String vCodigo = "";
            String vDescrip = "";
            String vDescripcion = "";
            for (int k = 0; k < listaTodo.size(); k++) {


                vCodigo = ((String[])listaTodo.get(k))[0];
                vDescrip = ((String[])listaTodo.get(k))[1];
                vDescripcion = vDescrip;

                if (VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_DIAGNOSTICO_UIE)) {
                    //DUBILLUZ 04.06.2014
                    if (vCodigo.toUpperCase().indexOf(vFindText.toUpperCase()) == 0) {
                        
                        String[] dato = (String[])listaTodo.get(k);

                        lista.add(dato);
                    } else
                    //DUBILLUZ 04.06.2014
                    if (vDescrip.toUpperCase().indexOf(vFindText.toUpperCase()) == 0) {


                        String[] dato = (String[])listaTodo.get(k);

                        lista.add(dato);
                    }
                } else {
                    //DUBILLUZ 04.06.2014
                    if (vCodigo.toUpperCase().indexOf(vFindText.toUpperCase()) != -1) {
                       
                        String[] dato = (String[])listaTodo.get(k);

                        lista.add(dato);
                    } else
                    //DUBILLUZ 04.06.2014
                    if (vDescrip.toUpperCase().indexOf(vFindText.toUpperCase()) != -1) {


                        String[] dato = (String[])listaTodo.get(k);

                        lista.add(dato);
                    }
                }
                
            }
        }

        return lista;

    }


    private static ArrayList filtraListaDatoString(KeyEvent e, ArrayList listaTodo, JTextField pTextoDeBusqueda,
                                                   int pColumna) {
        log.debug("<<<<<<<<<<<<Metodo: filtraListaDatoString  >>>>>>>>>>>>>>>" + pTextoDeBusqueda.getText().trim());
        log.debug("<<<<<<<<<<<<Tamano::::  >>>>>>>>>>>>>>>" + listaTodo.size());
        ArrayList lista = new ArrayList();

        if ((e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) && ((e.getKeyCode() != KeyEvent.VK_ESCAPE))) {

            String vFindText = pTextoDeBusqueda.getText().toUpperCase();
            String vCodigo = "";
            for (int k = 0; k < listaTodo.size(); k++) {
                if (((Object[])listaTodo.get(k))[pColumna] instanceof String) {
                    vCodigo = (String)((Object[])listaTodo.get(k))[pColumna];
                    if (vCodigo.toUpperCase().indexOf(vFindText.toUpperCase()) != -1) {
                        Object[] dato = (Object[])listaTodo.get(k);
                        lista.add(dato);
                    }
                }
            }
        }
        return lista;
    }

    public static boolean esTarjetaConvenio(String dato) {

        return existeTarjeta(dato, null);
    }


    public static boolean existeTarjeta(String dato, JDialog dialog) {
        log.debug("<<<<<<<<<<<<Metodo: existeTarjeta>>>>>>>>>>>>");
        Map tarjeta = null;
        boolean retorno = false;
        tarjeta = UtilityConvenioBTLMF.obtenerTarjeta(dato.trim(), dialog);
        if (tarjeta.get(ConstantsConvenioBTLMF.COL_COD_BARRA) != null) {
            retorno = true;
            VariablesConvenioBTLMF.vCodCliente = (String)tarjeta.get(ConstantsConvenioBTLMF.COL_COD_CLIENTE);
            VariablesConvenioBTLMF.vCodConvenioAux = (String)tarjeta.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO);
            log.debug("vCreacionCliente:::>" + VariablesConvenioBTLMF.vCreacionCliente);
        }

        return retorno;
    }

    public static boolean existeCliente(String pCodCliente, JDialog dialog) {
        log.debug("<<<<<<<<<<<<Metodo: existeCliente>>>>>>>>>>>>");
        Map benif = null;
        boolean retorno = false;
        benif = UtilityConvenioBTLMF.obtenerCliente(pCodCliente, dialog);

        if (benif.get(ConstantsConvenioBTLMF.COL_COD_CLIENTE) != null) {
            retorno = true;
            VariablesConvenioBTLMF.vDni = (String)benif.get(ConstantsConvenioBTLMF.COL_DNI);
            VariablesConvenioBTLMF.vNombre = (String)benif.get(ConstantsConvenioBTLMF.COL_DES_NOM_CLIENTE);
            VariablesConvenioBTLMF.vNomCliente = (String)benif.get(ConstantsConvenioBTLMF.COL_DES_APE_CLIENTE);
            VariablesConvenioBTLMF.vDescripcion = VariablesConvenioBTLMF.vNombre;
            VariablesConvenioBTLMF.vApellidoPat = (String)benif.get(ConstantsConvenioBTLMF.COL_DES_APE_CLIENTE);
            VariablesConvenioBTLMF.vLineaCredito = (String)benif.get(ConstantsConvenioBTLMF.COL_LCREDITO);
            VariablesConvenioBTLMF.vEstado = (String)benif.get(ConstantsConvenioBTLMF.COL_ESTADO);


            String numPoliza = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_POLIZA);
            String numPlan = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_PLAN);
            String codAsegurado = (String)benif.get(ConstantsConvenioBTLMF.COL_COD_ASEGURADO);
            String numItem = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_IEM);
            String prt = (String)benif.get(ConstantsConvenioBTLMF.COL_PRT);
            String numContrado = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_CONTRATO);
            String tipoAsegurado = (String)benif.get(ConstantsConvenioBTLMF.COL_TIPO_ASEGURADO);

            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_POLIZA, numPoliza);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_PLAN, numPlan);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_COD_ASEGURADO,
                                                             codAsegurado);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_IEM, numItem);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NOMB_CLIENTE,
                                                             VariablesConvenioBTLMF.vNombre);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_PRT, prt);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_CONTRATO, numContrado);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_TIPO_ASEGURADO,
                                                             tipoAsegurado);


        }

        return retorno;
    }


    public static boolean existeConvenio(String pCodConvenio, JDialog dialog, Frame myParentFrame) {
        log.debug("<<<<<<<<<<<<Metodo: existeConvenio>>>>>>>>>>>>");
        Map convenio = null;
        boolean retorno = false;
        convenio = UtilityConvenioBTLMF.obtenerConvenio(pCodConvenio, dialog, myParentFrame);

        if (convenio.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO) != null) {
            retorno = true;

            VariablesConvenioBTLMF.vCodConvenio = (String)convenio.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO);
            VariablesConvenioBTLMF.vNomConvenio = (String)convenio.get(ConstantsConvenioBTLMF.COL_DES_CONVENIO);
            VariablesConvenioBTLMF.vCodConvenioRel = (String)convenio.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO_REL);
            VariablesConvenioBTLMF.vFlgCreacionCliente =
                    (String)convenio.get(ConstantsConvenioBTLMF.COL_FLG_CREACION_CLIENTE);
            VariablesConvenioBTLMF.vFlgTipoConvenio =
                    (String)convenio.get(ConstantsConvenioBTLMF.COL_FLG_TIPO_CONVENIO);


        }

        log.debug("vCodConvenio=" + VariablesConvenioBTLMF.vCodConvenio);
        log.debug("vNomConvenio=" + VariablesConvenioBTLMF.vNomConvenio);
        log.debug("vCodConvenioRel=" + VariablesConvenioBTLMF.vCodConvenioRel);
        log.debug("vFlgTipoConvenio=" + VariablesConvenioBTLMF.vFlgTipoConvenio);
        log.debug("vFlgCreacionCliente=" + VariablesConvenioBTLMF.vFlgCreacionCliente);


        return retorno;
    }


    /**
     * Imprimir Mensaje
     * @author Fredy Ramirez
     * @since  09/11/2011
     */

    public static void imprimirMensaje(String pDni, JDialog pDialogo, Object pObject) {
        try {
            String vMensaje = DBConvenioBTLMF.imprimirMensaje(pDni);
            imprimirMensaje(vMensaje, VariablesPtoVenta.vImpresoraActual, VariablesPtoVenta.vTipoImpTermicaxIp);
            FarmaUtility.showMessage(pDialogo, "No existe Beneficiario para este convenio.", pObject);
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al imprimir el mensaje", pObject);
        }
    }

    /**
     * metodo encargado de imprimirMensaje
     * @param pConsejos
     * @param pImpresora
     * @param pTipoImprConsejo
     */
    private static void imprimirMensaje(String pConsejos, PrintService pImpresora, String pTipoImprConsejo) {
        DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
        JEditorPane editor = new JEditorPane();

        try {
            // Marcamos el editor para que use HTML
            editor.setContentType("text/html");
            editor.setText(pConsejos);
            dr.print(editor, pTipoImprConsejo);

        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static boolean esActivoConvenioBTLMF(JDialog pDialogo, Object pObjeto) {
        boolean resul = false;
        String esActivoConv = "";
        try {
            esActivoConv = DBConvenioBTLMF.esActivoConvenioBTLMF();
            log.debug("esActivoConv " + esActivoConv);
            if (esActivoConv.equalsIgnoreCase("S")) {
                resul = true;
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo,
                                     "Error al obtener de la base de datos el estado convenio BTLMF" + sql.getMessage(),
                                     pObjeto);
            resul = true;
        }
        return resul;

    }


    public static String obtieneFormaPago(JDialog pDialogo, Object pObjeto, String pCodFormaPago) {
        boolean resul = false;
        String descripcion = "";
        try {
            descripcion = DBConvenioBTLMF.obtieneFormaPago(pCodFormaPago);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo,
                                     "Error al obtener de la base de datos el estado convenio BTLMF" + sql.getMessage(),
                                     pObjeto);
            resul = true;
        }
        return descripcion;

    }

    public static double obtieneMontoCredito(JDialog pDialogo, Object pObjeto, Double monto, String nroPedido,
                                             String codConvenio, double vValorSelCopago) {
        double montoCredito = 0.00;
        try {
            if (vValorSelCopago == -1) {
                montoCredito = DBConvenioBTLMF.obtieneMontoCredito(monto, nroPedido, codConvenio);
            } else {
                montoCredito = ((100 - vValorSelCopago) / 100) * monto.doubleValue();
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo,
                                     "Error al obtener de la base de el monto credito convenio BTLMF" + sql.getMessage(),
                                     pObjeto);
        }
        return montoCredito;

    }

    public static void Busca_Estado_ProdConv(JDialog pJDialog) {
        
        try {
            log.debug("VariablesVentas.vCod_Prod:" + VariablesVentas.vCod_Prod);
            VariablesVentas.vEstadoProdConvenio = DBConvenioBTLMF.getEstadoProdConv(VariablesVentas.vCod_Prod);
        } catch (Exception sql) {
            VariablesVentas.vEstadoProdConvenio = "N";
            log.error("Error al obtener estado del producto " + VariablesVentas.vCod_Prod + "para el convenio " +
                      VariablesConvenioBTLMF.vCodConvenio, sql);
            FarmaUtility.showMessage(pJDialog, "Error al verificar estado del producto convenio.\n" +
                    sql.getMessage(), null);
        }

        log.debug("VariablesVentas.vEstadoProdConvenio:" + VariablesVentas.vEstadoProdConvenio);
    }


    public static String Conv_Buscar_Precio() {
        
        String sValPrecio = "0.00";
        try {
            log.debug("VariablesVentas.vCod_Prod:" + VariablesVentas.vCod_Prod);
            VariablesVentas.vEstadoProdConvenio = DBConvenioBTLMF.getEstadoProdConv(VariablesVentas.vCod_Prod);
            log.debug("VariablesVentas.vEstadoProdConvenio:" + VariablesVentas.vEstadoProdConvenio);
            sValPrecio = DBConvenioBTLMF.getPrecioProdConv(VariablesVentas.vCod_Prod);
        } catch (Exception sql) {
            VariablesVentas.vEstadoProdConvenio = "N";
            log.error("Error al obtener estado del producto " + VariablesVentas.vCod_Prod + "para el convenio " +
                      VariablesConvenioBTLMF.vCodConvenio, sql);
        }
        return sValPrecio;
    }
    
    public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus, boolean isReimpresion, boolean bCobroProforma) {

        procesoImpresionComprobante(pJDialog, pObjectFocus, false, isReimpresion,bCobroProforma);

    }

    public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus, boolean vIndImpresionAnulado,
                                                   boolean isReimpresion, boolean bCobroProforma) {
        long tmpT1, tmpT2;
        long tmpInicio, tmpFinal;
        log.debug("******PROCESO IMPRESION COMPROBANTES DEL CONVENIO********");
        tmpInicio = System.currentTimeMillis();

        try {
            UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
            //cambiando el estado de pedido al estado C -- que es estado IMPRESO y COBRADO
            tmpT1 = System.currentTimeMillis();
            //JMIRANDA 23/07/09 posee Throws SQLException

            //UtilityCaja.actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
            //rherrera 10.11.2014 actualiza estaod cobrado al final de imprimir comprobantes.

            boolean solicitaDatos =
                UtilityConvenioBTLMF.indDatoConvenio(VariablesConvenioBTLMF.vCodConvenio, null, null);
            if (solicitaDatos && !listaDatosConvenioAdic(pJDialog, pObjectFocus)) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog,
                                         "No se pudo determinar los datos adicionales del convenio. Verifique!!!.",
                                         pObjectFocus);
                return;
            }
            log.debug("Imprimiendo comprobantes ... ");
            tmpT1 = System.currentTimeMillis();
            String fechaCreacionComp = "";
            String RefTipComp = "";
            String vPrctBeneficiario = "0";
            String vPrctEmpresa = "0";
            String vIndImprimeEmpresa = "0";

            //
            acumuCorreFac = 0;
            acumuCorreGuia = 0;
            acumuCorreTik = 0;
            acumuCorreBol = 0;

            //KMONCADA 17.11.2014 SE CREA METODO PARA LISTAR LOS COMPROBANTES PARA IMPRESION Y REIMPRESION
            if (obtieneCompPagoImpresion(pJDialog, "", null)) {
                for (int j = 0; j < VariablesConvenioBTLMF.vArray_ListaComprobante.size(); j++) {
                    VariablesConvenioBTLMF.vNumCompPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(0)).trim();
                    VariablesConvenioBTLMF.vSecCompPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(1)).trim();
                    VariablesConvenioBTLMF.vTipoCompPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(2)).trim();
                    VariablesConvenioBTLMF.vValIgvCompPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(3)).trim();
                    VariablesConvenioBTLMF.vValNetoCompPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(4)).trim();
                    VariablesConvenioBTLMF.vValCopagoCompPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(5)).trim();
                    VariablesConvenioBTLMF.vValIgvCompCoPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(6)).trim();
                    VariablesConvenioBTLMF.vNumCompPagoRef =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(7)).trim();
                    VariablesConvenioBTLMF.vTipClienConvenio =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(8)).trim();
                    VariablesConvenioBTLMF.vFlgImprDatAdic =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(9)).trim();
                    VariablesConvenioBTLMF.vCodTipoConvenio =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(10)).trim();
                    fechaCreacionComp =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(11)).trim();
                    RefTipComp =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(12)).trim();
                    VariablesConvenioBTLMF.vValRedondeoCompPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(13)).trim();
                    vPrctBeneficiario =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(14)).trim();
                    vPrctEmpresa =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(15)).trim();
                    vIndImprimeEmpresa =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(16)).trim();
                    int valor = j;

                    UtilityCaja.actualizaComprobanteImpreso(VariablesConvenioBTLMF.vSecCompPago,
                                                            VariablesConvenioBTLMF.vTipoCompPago,
                                                            VariablesConvenioBTLMF.vNumCompPago);

                    //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
                    if (!obtieneDetalleComp(pJDialog, VariablesConvenioBTLMF.vSecCompPago,
                                            VariablesConvenioBTLMF.vTipoCompPago,
                                            VariablesConvenioBTLMF.vTipClienConvenio, pObjectFocus)) {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pJDialog,
                                                 "No se pudo obtener el detalle del comprobante a imprimir. Verifique!!!",
                                                 pObjectFocus);
                        return;
                    }

                    log.debug("VariablesConvenioBTLMF.vSecCompPago : " + VariablesConvenioBTLMF.vSecCompPago);
                    //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
                    if (!UtilityCaja.obtieneTotalesComprobante(pJDialog, VariablesConvenioBTLMF.vSecCompPago,
                                                               pObjectFocus)) {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pJDialog,
                                                 "No se pudo determinar los Totales del Comprobante. Verifique!!!.",
                                                 pObjectFocus);
                        return;
                    }

                    tmpT1 = System.currentTimeMillis();
                    //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
                    //Comentado//VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal.trim());
                    tmpT2 = System.currentTimeMillis();
                    log.debug("Tiempo 9: Obtiene Ruta Impresora:" + (tmpT2 - tmpT1) + " milisegundos");
                    tmpT1 = System.currentTimeMillis();


                    /*
                    * @author LTAVARA
                    * Obtener el numero de comprobante sea electronico
                    * @since 19.09.2014
                    * */
                    //VALIDAR SI EL COMPROBANTE ES ELECTRONICO
                    //String indicElectronico = DBImpresoras.getIndicCompElectronico(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago); //indicadorElectronico
                    
                    boolean isComprobanteElectronico = DBEpos.isComprobanteElectronico(FarmaVariables.vCodGrupoCia, 
                                                                                       FarmaVariables.vCodLocal, 
                                                                                       VariablesCaja.vNumPedVta, 
                                                                                       VariablesConvenioBTLMF.vSecCompPago);

                    //if (indicElectronico.equalsIgnoreCase(ConstantesDocElectronico.INDELECTRONICO)) { //COMPROBANTE ELECTRONICO
                    if(isComprobanteElectronico){
                        //SE OBTIENE EL NUMERO DE COMPROBANTE ELECTRONICO DE LA BD
                        VariablesConvenioBTLMF.vNumCompPago = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(17)).trim();

                        if (("0").equals(VariablesConvenioBTLMF.vNumCompPago)) {

                            if (UtilityEposTrx.validacionEmiteElectronico()) {
                                //LLAMAR AL PROCESO DE GENERACION DE COMPROBANTE PARA OBTENER EL NUMERO DE COMPROBANTE
                                //EL DOCUMENTO SIGUE CON E
                                FarmaUtility.showMessage(pJDialog, "obtiene num Electronico", pObjectFocus);
                                VariablesConvenioBTLMF.vNumCompPago = UtilityEposTrx.getNumCompPagoE(VariablesConvenioBTLMF.vTipoCompPago,
                                                                       VariablesCaja.vNumPedVta,
                                                                       VariablesConvenioBTLMF.vSecCompPago,
                                                                       VariablesConvenioBTLMF.vTipClienConvenio, true);
                                // KMONCADA 20.10.2014 VALIDACION DE DUPLICIDAD DE COMPROBANTES ELECTRONICOS
                                // DUPLICADO EN LOCAL
                                /* if("L".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                    throw new Exception("El número de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya fue asignado en el local, comuniquese con Sistemas...!!");
                                }
                                // DUPLICADO EN RAC
                                if("R".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                    throw new Exception("El número de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya se encuentra registrado en RAC, comuniquese con Sistemas...!!");
                                }

                                //mensaje de error
                                if ( VariablesConvenioBTLMF.vNumCompPago.startsWith("E")){
                                        FarmaUtility.liberarTransaccion();
                                        FarmaUtility.showMessage(pJDialog,  VariablesConvenioBTLMF.vNumCompPago+" \n " +
                                                                                        "El pedido esta en estado Pendiente de Impresion",
                                                                                        pObjectFocus);
                                        return;
                                }*/

                            } else {
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.showMessage(pJDialog,
                                                         "El comprobante fue generar en el Proceso Electronico, \n" +
                                        "Por favor active el proceso Electronico para genera el numero de comprobante",
                                        pObjectFocus);
                                return;
                            }
                        }

                    }
                    //FIN LTAVARA


                    //ERIOS 2.4.3 Envia el porcentajes
                    UtilityConvenioBTLMF.imprimeComprobantePago(pJDialog,
                                                                VariablesConvenioBTLMF.vArray_ListaDetComprobante,
                                                                VariablesCaja.vArrayList_TotalesComp,
                                                                VariablesConvenioBTLMF.vTipoCompPago,
                                                                VariablesConvenioBTLMF.vNumCompPago,
                                                                VariablesConvenioBTLMF.vValNetoCompPago,
                                                                VariablesConvenioBTLMF.vValIgvCompPago,
                                                                VariablesConvenioBTLMF.vValCopagoCompPago,
                                                                VariablesConvenioBTLMF.vValIgvCompCoPago,
                                                                VariablesConvenioBTLMF.vNumCompPagoRef,
                                                                VariablesConvenioBTLMF.vFlgImprDatAdic,
                                                                VariablesConvenioBTLMF.vTipClienConvenio,
                                                                VariablesConvenioBTLMF.vCodTipoConvenio,
                                                                fechaCreacionComp, RefTipComp,
                                                                VariablesConvenioBTLMF.vValRedondeoCompPago,
                                                                vIndImpresionAnulado, valor, vPrctBeneficiario,
                                                                vPrctEmpresa, vIndImprimeEmpresa, isReimpresion,
                                                                isComprobanteElectronico);

                    tmpT2 = System.currentTimeMillis();
                    log.debug("Tiempo 10: Imprime Comprobante:" + (tmpT2 - tmpT1) + " milisegundos");

                    //rherrera 10.11.2014 actualiza fecha de impresion.
                    if (FarmaVariables.vAceptar) {
                        UtilityCaja.actualizarFechaImpresion(VariablesConvenioBTLMF.vSecCompPago,
                                                             VariablesConvenioBTLMF.vTipoCompPago);
                    }
                    FarmaUtility.aceptarTransaccion();
                    log.debug("imprimiendo comprobantes ... " + valor);
                }
                // KMONCADA 06.05.2016 [ALBIS] IMPRESION DE CUPONES PARA CASOS DE CONVENIOS
                int cantidadCupones = UtilityCaja.imprimeCupones(pJDialog);
                
                //FIN DE QUE SE HAYA COBRADO EXITOSAMENTE
                //imprimirVoucher(pJDialog, null, VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vCodConvenio);
                FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
                facadeConvenioBTLMF.impresionVoucher(VariablesCaja.vNumPedVta);
                 
                //rherrera 10.11.2014 CAMBIO ESTADO IMPRESION AL FINAL
                log.debug("Actualiza estado de pedido a cobrado");
                UtilityCaja.actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);

                FarmaUtility.aceptarTransaccion();
                log.debug("FIN imprimiendo comprobantes ... ");
            }

            /*//ERIOS 20.11.2014 Cambio necesario para la importacion de ventas en linea. (JLUNA)
            UtilityCaja.actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
            FarmaUtility.aceptarTransaccion();
            log.debug("FIN imprimiendo comprobantes ... ");*/


            tmpT2 = System.currentTimeMillis();
            log.debug("Tiempo 11: Fin de Impresion de Comprobantes:" + (tmpT2 - tmpT1) + " milisegundos");

            //ERIOS 15.10.2013 Impresion de ticket anulado
            if (vIndImpresionAnulado) {
                FarmaUtility.showMessage(pJDialog, "¡Pedido Anulado!", pObjectFocus);
                return;
            }

            tmpFinal = System.currentTimeMillis();
            // /////////////////////////////////////////////////////////////
            // IMPRESION DE PEDIDOS DELIVERY PROVINCIA
            // /////////////////////////////////////////////////////////////
            // 30.06.2014 DUBILLUZ
            if (VariablesVentas.vEsPedidoDelivery) {
                log.info("SI IMPRIME imprimeDatosDeliveryLocal PROVINCIA");
                UtilityCaja.imprimeDatosDeliveryLocal(pJDialog, VariablesCaja.vNumPedVta);
            } else {
                log.info("NO IMPRIME imprimeDatosDeliveryLocal PROVINCIA");
            }
            ////////////////////////////////////////////////////////////////

            // /////////////////////////////////////////////////////////////
            // IMPRESION DE PEDIDOS DELIVERY AUTOMATICO
            // /////////////////////////////////////////////////////////////
            // 30.06.2014 DUBILLUZ
            if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S") && !bCobroProforma &&
                !VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                log.info("SI IMPRIME imprimeDatosDeliveryLocal AUTOMTICO " + VariablesCaja.vNumPedVta);
                String vNumPedDely = DBCaja.obtieneNumPedDelivery(VariablesCaja.vNumPedVta);
                log.info("SI IMPRIME imprimeDatosDeliveryLocal AUTOMTICO " + vNumPedDely);
                UtilityCaja.imprimeDatosDelivery(pJDialog, vNumPedDely);
            } else {
                log.info("NO IMPRIME imprimeDatosDeliveryLocal AUTOMTICO");
            }
            ////////////////////////////////////////////////////////////////
            
            // KMONCADA 08.06.2016 [PROYECTO M] NO IMPRIME AL FINAL PARA EL CASO DE MAYORISTA
            if(!UtilityPtoVenta.isLocalVentaMayorista()){
                //KMONCADA 12.05.2016 - IMPRESION DE COMANDA DE DESPACHO
                try{
                    (new UtilityDespacho()).imprimirComandaDespachoVenta(pJDialog, VariablesCaja.vNumPedVta);
                }catch(Exception ex){
                    log.info("[IMPRESION DE COMANDA DESPACHO] ERROR EN IMPRESION: "+ex.getMessage());
                }
            }

            if (VariablesCaja.vEstadoSinComprobanteImpreso.equals("N")) {
                log.debug("T18-Tiempo Final de Metodo de Impresion: Obtiene unidades Camp.xCliente localmente:" +
                          (tmpFinal - tmpInicio) + " milisegundos");
                FarmaUtility.showMessage(pJDialog, "Pedido Cobrado con éxito. \n" +
                        "Comprobantes Impresos con éxito " + "", pObjectFocus);
            } else {
                log.debug("T18-Tiempo Final de Metodo de Impresion: Obtiene unidades Camp.xCliente localmente:" +
                          (tmpFinal - tmpInicio) + " milisegundos");
                FarmaUtility.showMessage(pJDialog,
                                         "Pedido Cobrado con éxito." + "\nCOMPROBANTES NO IMPRESOS, Verifique Impresora: " +
                                         VariablesCaja.vRutaImpresora + "\nReimprima Comprobante, Correlativo :" +
                                         VariablesCaja.vNumPedVta, pObjectFocus);
            }
            
            //INI ASOSA - 10/02/2015 - 
            
            //if (UtilityPuntos.isActivoFuncionalidad()) {
            if (VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean()!=null) {
                boolean flag = UtilityCaja.getIndVariosCompPedido(VariablesCaja.vNumPedVta);
                if (flag) {
                    UtilityCaja.imprVouPtosCliente(VariablesCaja.vNumPedVta);
                }
            }
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);            
            FarmaUtility.showMessage(pJDialog, "Error en BD al Imprimir los Comprobantes del Pedido.\n" +
                    sql, pObjectFocus);
            //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime
            UtilityCaja.enviaErrorCorreoPorDB(sql.toString(), VariablesCaja.vNumPedVta_Anul);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            FarmaUtility.showMessage(pJDialog, "Error en la Aplicacion al Imprimir los Comprobantes del Pedido.\n" +
                    e, pObjectFocus);
            //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime
            UtilityCaja.enviaErrorCorreoPorDB(e.toString(), VariablesCaja.vNumPedVta_Anul);
        }
    }

    private static boolean obtieneDetalleComp(JDialog pJDialog, String pSecCompPago, String pTipoCompPago,
                                              String pTipCliConv, Object pObjectFocus) {
        VariablesConvenioBTLMF.vArray_ListaDetComprobante = new ArrayList();
        boolean valor = true;
        long tmpT1, tmpT2;
        tmpT1 = System.currentTimeMillis();
        try {
            DBConvenioBTLMF.obtieneDetalleCompPagos(VariablesConvenioBTLMF.vArray_ListaDetComprobante, pSecCompPago,
                                                    pTipoCompPago, pTipCliConv);
            if (VariablesConvenioBTLMF.vArray_ListaDetComprobante.size() == 0) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, "No se pudo determinar el detalle del Pedido. Verifique!!!.",
                                         pObjectFocus);
                valor = false;
            }
            log.info("VariablesConvenioBTLMF.vArray_ListaDetComprobante : " +
                     VariablesConvenioBTLMF.vArray_ListaDetComprobante.size());
            //valor = true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error al obtener Detalle de Impresion de Comprobante.", pObjectFocus);
            
            valor = false;
            UtilityCaja.enviaErrorCorreoPorDB(sql.toString(), null);
        }

        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 4: Det.Comp Pago:" + (tmpT2 - tmpT1) + " milisegundos");


        return valor;
    }

    public static boolean obtieneCompPago(JDialog pJDialog, String pTipClienteConv, Object pObjectFocus) {
        VariablesConvenioBTLMF.vArray_ListaComprobante = new ArrayList();
        boolean valor = true;
        long tmpT1, tmpT2;
        tmpT1 = System.currentTimeMillis();
        try {
            DBConvenioBTLMF.obtieneCompPagos(VariablesConvenioBTLMF.vArray_ListaComprobante, pTipClienteConv);
            if (VariablesConvenioBTLMF.vArray_ListaComprobante.size() == 0) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, "No se pudo determinar los datos del comprobante. Verifique!!!.",
                                         pObjectFocus);
                valor = false;
            }
            log.info("VariablesConvenioBTLMF.vArray_ListaComprobante : " +
                     VariablesConvenioBTLMF.vArray_ListaComprobante.size());
            valor = true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error al obtener los datos de Impresion del Comprobante.",
                                     pObjectFocus);
            
            valor = false;
            UtilityCaja.enviaErrorCorreoPorDB(sql.toString(), null);
        }
        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 4: Det.Comp Pago:" + (tmpT2 - tmpT1) + " milisegundos");
        return valor;
    }


    public static boolean obtieneCompPagoImpresion(JDialog pJDialog, String pTipClienteConv, Object pObjectFocus) {
        VariablesConvenioBTLMF.vArray_ListaComprobante = new ArrayList();
        boolean valor = true;
        long tmpT1, tmpT2;
        tmpT1 = System.currentTimeMillis();
        try {
            DBConvenioBTLMF.obtieneCompPagosImpresion(VariablesConvenioBTLMF.vArray_ListaComprobante, pTipClienteConv);
            if (VariablesConvenioBTLMF.vArray_ListaComprobante.size() == 0) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, "No se pudo determinar los datos del comprobante. Verifique!!!.",
                                         pObjectFocus);
                valor = false;
            }
            log.info("VariablesConvenioBTLMF.vArray_ListaComprobante : " +
                     VariablesConvenioBTLMF.vArray_ListaComprobante.size());
            valor = true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error al obtener los datos de Impresion del Comprobante.",
                                     pObjectFocus);
            
            valor = false;
            UtilityCaja.enviaErrorCorreoPorDB(sql.toString(), null);
        }
        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 4: Det.Comp Pago:" + (tmpT2 - tmpT1) + " milisegundos");
        return valor;
    }


    public static void imprimeComprobantePago(JDialog pJDialog, ArrayList pDetalleComprobante,
                                              ArrayList pTotalesComprobante, String pTipCompPago,
                                              String pNumComprobante, String pValTotalNeto, String pValIgvComPago,
                                              String pValCopagoCompPago, String pValIgvCompCoPago, String pNumCompRef,
                                              String pImprDatAdic, String pTipoClienteConvenio,
                                              String pCodTipoConvenio, String pFechaCreacionComp, String pRefTipComp,
                                              String pValRedondeoCompPago, boolean vIndImpresionAnulado, int valor,
                                              String vPrctBeneficiario, String vPrctEmpresa, String vIndImprimeEmpresa,
                                              boolean isReimpresion, boolean isComprobanteElectronico) throws Exception {
        String pPorcIgv = ((String)((ArrayList)pTotalesComprobante.get(0)).get(6)).trim();
        String pValTotalAhorro = ((String)((ArrayList)pTotalesComprobante.get(0)).get(11)).trim();

        String ruta = "";
        ruta = UtilityPtoVenta.obtieneDirectorioComprobantes();
        Date vFecImpr = new Date();
        String fechaImpresion;
        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        fechaImpresion = sdf.format(vFecImpr);
        //String secImprLocal = "";
        log.debug("fecha : " + fechaImpresion);
        
        if(isComprobanteElectronico){
            if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) ||
                pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) ||
                pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_NOTA_CREDITO)
            ){
                if(pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)){
                    ruta = ruta + fechaImpresion + "_" + "B_E_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                }
                if(pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)){
                    ruta = ruta + fechaImpresion + "_" + "F_E_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                }
                
                if(pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_NOTA_CREDITO)){
                    ruta = ruta + fechaImpresion + "_" + "NC_E_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                }
                
                UtilityImpCompElectronico impresionElectronico = new UtilityImpCompElectronico();
                impresionElectronico.setRutaFileTestigo(ruta);
                FarmaVariables.vAceptar = impresionElectronico.imprimirComprobantePago(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago, false);    
                //ERIOS 25.06.2015 Se solicita imprimir doble
                if(VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) && !isReimpresion &&
                    (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) ||
                    pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)) ){
                    try{
                        impresionElectronico.imprimirComprobantePago(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago, true);    
                    }catch(Exception e){
                        log.error("Imprimir copia de Venta Empresa",e);
                    }
                }
            }else{
                FarmaUtility.showMessage(pJDialog, "ERROR AL IMPRIMIR DOCUMENTO ELECTRONICO, TIPO DE COMPROBANTE: "+pTipCompPago+", VERIFIQUE!!!", null);
            }
        }else{
            //ERIOS 2.4.3 margen derecho
            int margen = UtilityPtoVenta.getMargenImpresionComp();
            Impresora impresora = new Impresora();
            ContextImpresion context = null;
            int vLineasComp = 0;
            
            VariablesConvenioBTLMF.vTipoCompPagoAux = pTipCompPago;
            if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)) {
                //Imprimiendo mensajes
                muestraMensajeImpresion(pJDialog, ConstantsVentas.TIPO_COMP_BOLETA, ++acumuCorreBol,
                                        VariablesConvenioBTLMF.vSecCompPago, VariablesCaja.vNumPedVta, false,
                                        isReimpresion); 
              
                ruta = ruta + fechaImpresion + "_" + "B_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                //secImprLocal = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, ConstantsVentas.TIPO_COMP_TICKET, "N");
                VariablesCaja.vRutaImpresora = UtilityConvenioBTLMF.obtieneRutaImpresora(pTipCompPago);

                if (FarmaVariables.vAceptar) {
                    //KMONCADA 06.05.2016 IMPRESION DE COMPROBANTES MANUALES
                    if(FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_ARCANGEL) || 
                            FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_JORSA)){
                        UtilityCaja.imprimirComprobantePago(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago,  ruta);
                    }else{
                        context = new ContextImpresion(new FormatoImpresionC());
                        vLineasComp = 24;
                        
                        ArrayList<String> vPrint =
                        context.formatoBoleta(pJDialog, pFechaCreacionComp, pDetalleComprobante, pValTotalNeto, "pValRedondeo",
                                     pNumComprobante, "pNomImpreso", "pDirImpreso", "pValTotalAhorro", ruta, true,
                            margen,true,
                        pImprDatAdic, pTipoClienteConvenio, pCodTipoConvenio, 
                        pValRedondeoCompPago, pValIgvComPago, pValCopagoCompPago, 
                        pValIgvCompCoPago, pNumCompRef);
                    
                        impresora.imprimir(vPrint, VariablesCaja.vRutaImpresora, true, VariablesCaja.vNumPedVta, pNumComprobante, "C",
                                       ruta, vLineasComp);
                    }
                }

            } else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) { //JCORTEZ  25.03.09
                muestraMensajeImpresion(pJDialog, ConstantsVentas.TIPO_COMP_TICKET, ++acumuCorreTik,
                                        VariablesConvenioBTLMF.vSecCompPago, VariablesCaja.vNumPedVta, false,
                                        isReimpresion);

                ruta = ruta + fechaImpresion + "_" + "TB_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                //secImprLocal = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, ConstantsVentas.TIPO_COMP_TICKET, "N");
                VariablesCaja.vRutaImpresora = UtilityConvenioBTLMF.obtieneRutaImpresora(pTipCompPago);

                //impresion
                if (FarmaVariables.vAceptar) {
                    // KMONCADA 24.07.2015 OBTIENE DATOS DE PUNTOS OBTENIDOS
                    List lstPuntos = null;
                    try{
                        lstPuntos = DBPuntos.imprVouPtosTicket(VariablesCaja.vNumPedVta, "N");
                    }catch(Exception ex){
                        lstPuntos = new ArrayList();
                    }
                    
                    imprimeTicket(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante, pValIgvComPago,
                                  pValCopagoCompPago, pValIgvCompCoPago, pNumCompRef, ruta, vIndImpresionAnulado,
                                  pImprDatAdic, pTipoClienteConvenio, pCodTipoConvenio, pFechaCreacionComp,
                                  pValRedondeoCompPago, valor, pRefTipComp, pTipCompPago, vPrctBeneficiario,
                                  vPrctEmpresa, vIndImprimeEmpresa, lstPuntos);
                }


            } else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_GUIA)) {
                muestraMensajeImpresion(pJDialog, ConstantsVentas.TIPO_COMP_GUIA, ++acumuCorreGuia,
                                        VariablesConvenioBTLMF.vSecCompPago, VariablesCaja.vNumPedVta, false,
                                        isReimpresion);
                
                if (FarmaVariables.vAceptar) {
                    ruta = ruta + fechaImpresion + "_" + "G_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                    //secImprLocal = VariablesCaja.vSecImprLocalGuia;
                    VariablesCaja.vRutaImpresora = UtilityConvenioBTLMF.obtieneRutaImpresora(pTipCompPago);
                        
                    //impresion
                    //KMONCADA 06.05.2016 IMPRESION DE COMPROBANTES MANUALES
                    if(FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_ARCANGEL) || 
                            FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_JORSA)){
                        UtilityCaja.imprimirComprobantePago(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago,  ruta);
                    } else {
                        if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MIFARMA) ||
                              FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MAYORISTA)) {
                            log.info("****************************************IMPRIMIENDO FORMATO MIFARMA*********************************");
                            context = new ContextImpresion(new FormatoImpresionA());
                            vLineasComp = 36;
                        } else if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_FASA)) {
                            log.info("****************************************IMPRIMIENDO FORMATO FASA*********************************");
                            context = new ContextImpresion(new FormatoImpresionB());
                            vLineasComp= 66;
                        }else if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_BTL) ||
                                   FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_BTL_AMAZONIA)) {
                            log.info("****************************************IMPRIMIENDO FORMATO BTL*********************************");                        
                            context = new ContextImpresion(new FormatoImpresionC());
                            vLineasComp= 48; //rherrera
                        }
                        
                        ArrayList<String> vPrint =
                            context.formatoGuia(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante,
                                                            pValIgvComPago, pValCopagoCompPago, pValIgvCompCoPago, pNumCompRef,
                                                            ruta, true, pImprDatAdic, pTipoClienteConvenio, pCodTipoConvenio, pFechaCreacionComp,
                                                            pRefTipComp, pValRedondeoCompPago,
                                                            VariablesConvenioBTLMF.vInstitucion.trim(),
                                                            VariablesConvenioBTLMF.vDireccion.trim(),
                                                            VariablesConvenioBTLMF.vRuc.trim(), vPrctBeneficiario, vPrctEmpresa,
                                                            margen);

                        
                        impresora.imprimir(vPrint, VariablesCaja.vRutaImpresora, true, VariablesCaja.vNumPedVta, pNumComprobante, "C",
                                           ruta, vLineasComp);
                    }
                    if(!UtilityPtoVenta.isLocalVentaMayorista()){
                        //ERIOS 21.07.2015 Imprime voucher copia guia
                        FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
                        facadeConvenioBTLMF.impresionVoucherCopiaGuia(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago);
                    }
                }
            } else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)) {
                muestraMensajeImpresion(pJDialog, ConstantsVentas.TIPO_COMP_FACTURA, ++acumuCorreFac,
                                        VariablesConvenioBTLMF.vSecCompPago, VariablesCaja.vNumPedVta, false,
                                        isReimpresion);
                
                if (FarmaVariables.vAceptar) {
                    ruta = ruta + fechaImpresion + "_" + "F_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
                    //secImprLocal = VariablesCaja.vSecImprLocalFactura;
                    VariablesCaja.vRutaImpresora = UtilityConvenioBTLMF.obtieneRutaImpresora(pTipCompPago);
                    
                    //impresion
                    if(FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_ARCANGEL)){
                        UtilityCaja.imprimirComprobantePago(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago,  ruta);
                    } else {
                        if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MIFARMA)||
                              FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_MAYORISTA)) {
                            log.info("****************************************IMPRIMIENDO FACTURA MIFARMA*********************************");
                            context = new ContextImpresion(new FormatoImpresionA());
                            vLineasComp = 36;
                        } else if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_FASA)) {
                            log.info("****************************************IMPRIMIENDO FACTURA FASA*********************************");                        
                            context = new ContextImpresion(new FormatoImpresionB());
                            vLineasComp = 66;    
                        } else if (FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_BTL)||
                                    FarmaVariables.vCodCia.equals(ConstantsPtoVenta.CODCIA_BTL_AMAZONIA)) {
                            context = new ContextImpresion(new FormatoImpresionC());
                            vLineasComp = 36; //rherrera
                        }

                        ArrayList<String> vPrint =
                            context.formatoFactura(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante,
                                               pValIgvComPago, pValCopagoCompPago, pPorcIgv, pNumCompRef,
                                               pValTotalAhorro, true, pImprDatAdic, pTipoClienteConvenio,
                                               pCodTipoConvenio, pFechaCreacionComp, pRefTipComp, pValRedondeoCompPago,
                                               VariablesConvenioBTLMF.vInstitucion.trim(),
                                               VariablesConvenioBTLMF.vDireccion.trim(),
                                               VariablesConvenioBTLMF.vRuc.trim(), vPrctBeneficiario, vPrctEmpresa,
                                               margen,
                                               "pValTotalBruto", "pValTotalAfecto", "pValTotalDescuento",
                                                   valor);
                        
                        impresora.imprimir(vPrint, VariablesCaja.vRutaImpresora, true, VariablesCaja.vNumPedVta, pNumComprobante, "C",
                                           ruta, vLineasComp); 
                    }
                }
            }
        }
        
        // KMONCADA 18.12.2014 SOLO EN EL CASO DE NO ELECTRONICO
        if (!EposVariables.vFlagComprobanteE) {
            UtilityCaja.abrirGabeta(null, false, VariablesCaja.vNumPedVta);
        }
    }

    private static void imprimeTicket(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                      String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                      String pValIgvComCoPago, String pNumCompCoPago, String pRuta,
                                      boolean vIndImpresionAnulado, String pImprDatAdic, String pTipoClienteConvenio,
                                      String pCodTipoConvenio, String pFechaBD, String pValRedondeoComPago, int valor,
                                      String pRefTipComp, String pTipCompPago, String vPrctBeneficiario,
                                      String vPrctEmpresa, String vIndImprimeEmpresa, List lstPuntos) throws Exception {

        String secImprLocal = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc, ConstantsVentas.TIPO_COMP_TICKET, "N");
        VariablesCaja.vModeloImpresora = DBImpresoras.getModeloImpresora(secImprLocal);

        //*****
        ArrayList<String> vPrint = new ArrayList<String>();
        String indProdVirtual = "";
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;

        FarmaVariables.vNroPedidoNoImp = VariablesCaja.vNumPedVta;

        try {

            ImpresoraTicket ticketera = new ImpresoraTicket();
            ticketera.generarDocumentoConvenio(pJDialog, vPrint, "", "", pFechaBD, pNumComprobante,
                                               pDetalleComprobante, pValTotalNeto, "0.0", "0.0",
                                               VariablesCaja.vModeloImpresora, vIndImpresionAnulado, pTipCompPago,
                                               valor, pValCopagoCompPago, pCodTipoConvenio, pTipoClienteConvenio,
                                               pValRedondeoComPago, pRefTipComp, pNumCompCoPago, pImprDatAdic,
                                               vPrctBeneficiario, vPrctEmpresa, vIndImprimeEmpresa, lstPuntos);
            boolean bImprimio =
                ticketera.imprimir(vPrint, VariablesCaja.vModeloImpresora, VariablesCaja.vRutaImpresora, true,
                                   pNumComprobante, "C", VariablesCaja.vNumPedVta, pTipCompPago);

            if (!bImprimio) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            } else {
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            }
        } catch (SQLException sql) {
            log.error("", sql);
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            
            log.info("**** Fecha :" + pFechaBD);
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            // enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
        } catch (Exception e) {
            log.error("", e);
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            log.info("**** Fecha :" + pFechaBD);
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("Error al imprimir la Ticket: " + e);
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            //enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
        }

    }

    private static String obtieneRutaImpresora(String pTipCompPag) throws SQLException {
        return DBConvenioBTLMF.obtieneRutaImpresoraVenta(pTipCompPag);
    }


    public static boolean listaDatosConvenioAdic(JDialog pJDialog, Object pObjectFocus) {
        VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic = new ArrayList();
        boolean valor = true;
        long tmpT1, tmpT2;
        tmpT1 = System.currentTimeMillis();
        List lista = new ArrayList();
        try {
            lista = DBConvenioBTLMF.listaDatosConvenioAdicXpedido();
            VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic = lista;
            VariablesConvenioBTLMF.vNomClienteDigitado = "";
            //CHUANES 31.03.2014
            //EXTRAEMOS DE LOS DATOS ADICIONALES EL NOMBRE DEL BENEFICIARIO
            for (int i = 0; i < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); i++) {
                Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(i);

                String pNombCampo = (String)datosAdicConv.get("NOMBRE_CAMPO");
                String pDesCampo = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                //ERIOS 2.4.7 Busca nombre de beneficiario
                String vCodCampo = (String)datosAdicConv.get("COD_CAMPO");
                if (vCodCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO)) {
                    if (VariablesConvenioBTLMF.vNomCliente.trim().equals("")) {
                        VariablesConvenioBTLMF.vNomCliente = pDesCampo;
                    }
                }
                //SI LA VARIABLE CONTIENE pNombCampo BENEFICIARIO ENTONCES pDesCampo CONTIENE EL NOMBRE DEL BENEFICIARIO
                if (pNombCampo.contains("Beneficiario")) {
                    VariablesConvenioBTLMF.vNomClienteDigitado = pDesCampo;
                }
            }
            if (lista.size() == 0) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog,
                                         "No se pudo determinar el listado de datos adicionales del convenio. Verifique!!!.",
                                         pObjectFocus);
                valor = false;
            } else {
                log.info("VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic : " +
                         VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size());
                valor = true;
            }
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog, "Error al obtener los datos adicionales del convenio.", pObjectFocus);
            
            valor = false;
            UtilityCaja.enviaErrorCorreoPorDB(sql.toString(), null);
        }

        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 4: Det.Comp Pago:" + (tmpT2 - tmpT1) + " milisegundos");

        return valor;
    }


    public static Map obtieneConvenioXpedido(String nroPedido, JDialog pDialogo) {
        Map pedidoConvenio = null;

        try {
            pedidoConvenio = DBConvenioBTLMF.obtenerConvenioXPedido(VariablesCaja.vNumPedVta_Anul);

        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos del pedido convenio", pDialogo);
        }
        return pedidoConvenio;
    }

    public static String esDiaVencimientoReceta(JDialog pDialogo, Object pObjeto, String fechVencimietnoRecta) {
        String res = null;

        try {
            res = DBConvenioBTLMF.esDiaVigenciaReceta(fechVencimietnoRecta);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo, "Error al validar la fecha de vigencia de la receta" + sql.getMessage(),
                                     pObjeto);
        }

        if (res.equals("P")) {
            FarmaUtility.showMessage(pDialogo, "La Fecha de Vigencia de Receta es posterior al día de hoy.", pObjeto);
        } else if (res.equals("N")) {
            FarmaUtility.showMessage(pDialogo, "La Fecha de Vigencia de Receta está caducado.", pObjeto);
        }
        return res;
    }


    public static String obtieneTipoConvenio(JDialog pDialogo, Object pObjeto, String pCodConvenio) {
        String resTipoConvenio = null;

        try {
            resTipoConvenio = DBConvenioBTLMF.geTipoConvenio(pCodConvenio);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo, "Error al obtener el tipo Convenio" + sql.getMessage(), pObjeto);
        }

        return resTipoConvenio;
    }

    public static Map obtieneMsgCompPagoImpr(JDialog pDialogo, Object pObjeto) {
        Map resTipoConvenio = null;

        try {
            resTipoConvenio = DBConvenioBTLMF.obtieneMsgCompPagoImpr();

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo, "Error al obtener el mensaje de Impresion" + sql.getMessage(), pObjeto);
        }

        return resTipoConvenio;
    }

    public static void aceptarTransaccionRempota(FarmaTableModel pTableModel, Object pObjeto,
                                                 String pIndCloseConecction) {

        try {
            DBConvenioBTLMF.aceptarTransaccionRempota(pTableModel, pIndCloseConecction);
        } catch (SQLException sql) {
            log.error("", sql);
            //				//FarmaUtility.showMessage(pDialogo,
            //				"Error al verificar la existencia de productos en convenio" +
            //				sql.getMessage(), pObjeto);
        }


    }


    public static void liberarTransaccionRempota(FarmaTableModel pTableModel, Object pObjeto,
                                                 String pIndCloseConecction) {

        try {
            DBConvenioBTLMF.liberarTransaccionRempota(pTableModel, pIndCloseConecction);
        } catch (SQLException sql) {
            log.error("", sql);
            //				//FarmaUtility.showMessage(pDialogo,
            //				"Error al verificar la existencia de productos en convenio" +
            //				sql.getMessage(), pObjeto);
        }


    }


    public static boolean esMontoPrecioCero(String monto, JDialog pDialogo) {
        boolean result = false;

        if (FarmaUtility.getDecimalNumber(monto) == 0) {
            FarmaUtility.showMessage(pDialogo, "El precio venta del producto convenio es cero", null);
            result = true;
        }

        return result;
    }

    public static boolean esCompCredito(JDialog pDialogo) {

        String result = "";
        boolean resp = true;
        try {
            result = DBConvenioBTLMF.esCompCredito();

            if (result.equals("N")) {
                resp = false;
            }

        } catch (SQLException sql) {
            log.error("", sql);
        }

        return resp;
    }

    public static String indConvenioBTLMF(JDialog pDialogo) {
        String indConvenioBTLMF = "";
        try {
            Map vtaPedido = (Map)DBConvenioBTLMF.obtenerConvenioXPedido(VariablesCaja.vNumPedVta_Anul);
            indConvenioBTLMF = (String)vtaPedido.get("IND_CONV_BTL_MF");


        } catch (SQLException sql) {
            log.error("", sql);
        }


        return indConvenioBTLMF;
    }


    public static boolean indCopagoConvenio(String pCodigoConvenio, JDialog pDialogo, Object pObjeto) {
        boolean resul = false;
        String indConv = "";
        String indProdConv = "";

        try {
            indConv = DBConvenioBTLMF.pideCopagoConvenio(pCodigoConvenio);
            log.debug("INDICADOR PIDE COPAGO CONV = " + indConv);
            if (indConv.equalsIgnoreCase("S")) {
                resul = true;
            } else {
                resul = false;
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo, "Error en buscar si debe mostrase datos convenios\n" +
                    sql.getMessage(), pObjeto);
            resul = false;
        }
        return resul;
    }

    /**
     * Se recupera los comprobantes que emite el convenio
     * @author ERIOS
     * @since 23.04.2014
     * @param pJDialog
     * @return
     */
    public static String[] getComprobantesConvenio(JDialog pJDialog) {
        String comprobantes = "";
        try {
            comprobantes = DBConvenioBTLMF.getCompConvenio();
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(pJDialog,
                                     "Error al recuperar comprobantes del convenio. Se continua con el cobro.", null);
        }
        String[] lineas = comprobantes.trim().split("@");
        return lineas;
    }

    public static boolean imprimeTicketBTLMF(JDialog pJDialog, String pNumeroPedido, String pCajero,
                                             String pTurno) throws Exception {

        boolean vResultado = false, bRes1 = true, bRes2 = true;

        if (ConstantesDocElectronico.lstPedidos != null) {
            //GENERA NOTA DE CREDITO
            for (int i = 0; i < ConstantesDocElectronico.lstPedidos.length; i++) {
                VariablesCaja.vNumPedVta = ConstantesDocElectronico.lstPedidos[i].toString();

                UtilityImpCompElectronico impresionElectronico = new UtilityImpCompElectronico();
                if (UtilityCaja.obtieneCompPago()) {
                    for (int k = 0; k < VariablesVentas.vArray_ListaComprobante.size(); k++) {
                        VariablesConvenioBTLMF.vNumCompPago =
                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(k)).get(4)).trim(); //LTAVARA 23.09.2014 OBTENER NUMERO ELECTRONICO
                        VariablesConvenioBTLMF.vSecCompPago =
                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(k)).get(1)).trim();
                        VariablesConvenioBTLMF.vTipoCompPago =
                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(k)).get(2)).trim();
                        VariablesConvenioBTLMF.vTipClienConvenio =
                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(k)).get(3)).trim();
                        String indicElectronico =
                            DBImpresoras.getIndicCompElectronico(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago); //indicadorElectronico

                        if (indicElectronico.equalsIgnoreCase(ConstantesDocElectronico.INDELECTRONICO)) {
                            //vResultado=impresionElectronico.printDocumento(pJDialog, VariablesCaja.vNumPedVta ,  VariablesConvenioBTLMF.vSecCompPago,  VariablesConvenioBTLMF.vTipoCompPago,  VariablesConvenioBTLMF.vTipClienConvenio ,  VariablesConvenioBTLMF.vNumCompPago );
                            vResultado =
                                    impresionElectronico.imprimirComprobantePago(VariablesCaja.vNumPedVta, VariablesConvenioBTLMF.vSecCompPago,
                                                                        false);
                            if (vResultado) { //LTAVARA 17.10.2014
                                // KMONCADA 02.12.2014 ACTUALIZA FECHA DE IMPRESION
                                UtilityCaja.actualizarFechaImpresion(VariablesConvenioBTLMF.vSecCompPago,
                                                                     VariablesConvenioBTLMF.vTipoCompPago);
                                UtilityCaja.actualizaEstadoPedido(VariablesCaja.vNumPedVta,
                                                                  ConstantsCaja.ESTADO_COBRADO);
                            }
                        }

                    }
                } else {
                    if (UtilityCaja.obtieneCompPago_GUI()) { //rherrera 18.11.2014

                        for (int k = 0; k < VariablesVentas.vArray_ListaComprobante.size(); k++) {
                            VariablesConvenioBTLMF.vTipoCompPago =
                                    ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(k)).get(0)).trim();
                            if (VariablesConvenioBTLMF.vTipoCompPago.equals("03"))
                                UtilityCaja.actualizaEstadoPedido(VariablesCaja.vNumPedVta,
                                                                  ConstantsCaja.ESTADO_COBRADO);
                        }

                    } //fin
                }
            }

        } else {
            // ANULA PEDIDO
            VariablesCaja.vNumPedVta = pNumeroPedido;
            if (UtilityConvenioBTLMF.obtieneCompPago(pJDialog, "", null)) {
                for (int j = 0; j < VariablesConvenioBTLMF.vArray_ListaComprobante.size(); j++) {
                    VariablesConvenioBTLMF.vNumCompPago =
                            ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(0)).trim();
                    bRes1 =
                            UtilityCaja.imprimeMensajeTicketAnulacion(pCajero, pTurno, pNumeroPedido, "00", "N", VariablesConvenioBTLMF.vNumCompPago);
                    //para montos inafectos
                    bRes2 =
                            UtilityCaja.imprimeMensajeTicketAnulacion(pCajero, pTurno, pNumeroPedido, "01", "N", VariablesConvenioBTLMF.vNumCompPago);
                }
            }
        }

        if (bRes1 || bRes2)
            vResultado = true;
        else
            vResultado = false;
        return vResultado;
    }

    public static void cargarVariablesConvenio(String pCodConvenio, JDialog pDialogo, Frame myParentFrame) {
        Map map = UtilityConvenioBTLMF.obtenerConvenio(pCodConvenio, pDialogo, myParentFrame);
        String nombConvenio = (String)map.get(ConstantsConvenioBTLMF.COL_DES_CONVENIO);

        VariablesConvenioBTLMF.vFlgImprimeImportes = (String)map.get(ConstantsConvenioBTLMF.COL_FLG_IMPRIME_IMPORTES);
        VariablesConvenioBTLMF.vIndVtaComplentaria =
                (String)map.get(ConstantsConvenioBTLMF.COL_IND_VTA_COMPLEMENTARIA);
        VariablesConvenioBTLMF.vFlgValidaLincreBenef =
                (String)map.get(ConstantsConvenioBTLMF.COL_FLG_VALIDA_LINCRE_BENEF);
        VariablesConvenioBTLMF.vRuc = (String)map.get(ConstantsConvenioBTLMF.COL_RUC);
        VariablesConvenioBTLMF.vDireccion = (String)map.get(ConstantsConvenioBTLMF.COL_DIRECCION);
        VariablesConvenioBTLMF.vInstitucion = (String)map.get(ConstantsConvenioBTLMF.COL_INSTITUCION);

        VariablesConvenioBTLMF.vNomConvenio = nombConvenio;
    }

    //25.07.2014 muestra mensaje de impresión

    /**
     *
     * @param pJDialog
     * @param pTipoComprobante
     * @param pCantDoc
     * @param pNumDocImpresion
     * @param vNumPedVta
     * @param isMsjContingencia INDICA SI MENSAJE ES PARA CASOS DE CONTIGENCIA DE FACTURACION ELECTRONICA
     */
    public static boolean muestraMensajeImpresion(JDialog pJDialog, String pTipoComprobante, int pCantDoc,
                                                  String pNumDocImpresion, String vNumPedVta,
                                                  boolean isMsjContingencia, boolean isReimpresion) {
        
        log.info("Inicio captura de msj de impresion: "+vNumPedVta+" - "+pNumDocImpresion);
        Map map = obtieneMsgImpresion(pJDialog, null,isMsjContingencia,pTipoComprobante,vNumPedVta,pCantDoc,pNumDocImpresion,isReimpresion);
        log.info("Fin captura de msj de impresion: "+vNumPedVta+" - "+pNumDocImpresion);
        if(map!=null){
            DlgMensajeImpresion dlgMensajeImpresion = new DlgMensajeImpresion(pJDialog, "", true,map);
            dlgMensajeImpresion.setVTipoComprobante(pTipoComprobante);
            dlgMensajeImpresion.setVNumDocImpreso(pCantDoc);
            dlgMensajeImpresion.setVNumeroDocumento(pNumDocImpresion);
            dlgMensajeImpresion.setVNumPedVta(vNumPedVta);
            // KMONCADA 02.12.2014 INDICADOR DE MENSAJE DE CONTIGENCIA
            dlgMensajeImpresion.setIsMsjContingencia(isMsjContingencia);
            dlgMensajeImpresion.setIsReimpresion(isReimpresion);
            dlgMensajeImpresion.setVisible(true);
            log.info("Termina msj de impresion: "+vNumPedVta+" - "+pNumDocImpresion);
            return dlgMensajeImpresion.getMostrarMensaje();
        }
        return false;
        
    }
    
    public static Map obtieneMsgImpresion(JDialog pDialogo, Object pObjeto,boolean isMsjContingencia,
                                    String vTipoComprobante,String vNumPedVta,
                                    int vNumDocImpreso,String vNumeroDocumento,boolean isReimpresion
                                    ) {
        Map resTipo = null;

        try {
            // KMONCADA 02.12.2014 EVALUA SI EL MENSAJE A MOSTRAR EN POR CONTIGENCIA O IMPRESION
            if (isMsjContingencia) {
                resTipo = DBEpos.obtieneMsgContingencia(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, vNumPedVta, vTipoComprobante);
            } else {
                resTipo = DBVentas.obtieneMsgImpFactura(vTipoComprobante, vNumDocImpreso, vNumeroDocumento, vNumPedVta, isReimpresion);
            }
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo, "Error al obtener el mensaje de impresión:\n" +
                    sql.getMessage(), pObjeto);
            resTipo = null;
        }

        return resTipo;
    }

    public static void obtienePrecioConvenio(JDialog pJDialog, Object pObjectFocus) {
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) &&
            VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 &&
            VariablesVentas.vCod_Prod.trim().length() == 6 && VariablesVentas.vEstadoProdConvenio.equals("I")) {

            VariablesConvenioBTLMF.vValidaPrecio = true;
            SubProcesosConvenios precConv = new SubProcesosConvenios();
            precConv.start();
        } else {

            UtilityVentas.guardaInfoProdVariables(pJDialog,pObjectFocus);
        }
    }
}
